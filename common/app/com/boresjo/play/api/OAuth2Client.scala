package com.boresjo.play.api

import play.api.libs.json.{JsObject, JsValue, Json, OFormat}
import play.api.libs.ws.WSClient
import play.api.mvc.Results.TemporaryRedirect
import play.api.mvc.{AnyContent, Request, Result}
import play.api.{Configuration, Logging}
import views.html.helper.urlEncode

import java.net.URI
import java.util.concurrent.atomic.AtomicReference
import javax.inject.{Inject, Singleton}
import scala.concurrent.Future.successful
import scala.concurrent.{ExecutionContext, Future}

/**
 * Companion object for OAuth2Client containing utility methods and case classes.
 */
object OAuth2Client {
  /**
   * Case class representing OAuth2 credentials.
   *
   * @param client_id The client ID.
   * @param project_id The project ID.
   * @param auth_uri The authorization URI.
   * @param token_uri The token URI.
   * @param auth_provider_x509_cert_url The auth provider x509 certificate URL.
   * @param client_secret The client secret.
   * @param redirect_uris The list of redirect URIs.
   */
  case class Credentials(
    client_id: String,
    project_id: String,
    auth_uri: String,
    token_uri: String,
    auth_provider_x509_cert_url: String,
    client_secret: String,
    redirect_uris: List[String]
  )

  object Credentials {
    given fmtApplicationCredentials: OFormat[Credentials] = Json.format[Credentials]

    /**
     * Creates Credentials from a JSON string.
     *
     * @param credentials The JSON string representing the credentials.
     * @return The Credentials object.
     */
    def apply(credentials: String): Credentials = apply(Json.parse(credentials))

    /**
     * Creates Credentials from a JsValue.
     *
     * @param json The JsValue representing the credentials.
     * @return The Credentials object.
     */
    def apply(json: JsValue): Credentials = (json \ "installed").asOpt[JsObject] match {
      case Some(json) => json.as[Credentials]
      case None => json.as[Credentials]
    }
  }

  /**
   * Returns a URI on the same host as the current request.
   *
   * @param relativeUri The relative URI.
   * @param req The current request.
   * @return The full URI as a string.
   */
  def localUri(relativeUri: String)(using req: Request[AnyContent]): String = {
    // TODO examine proxy headers to find original protocol (secure vs insecure)
    val baseUri = (if (req.secure || !req.host.startsWith("localhost:")) "https" else "http") + "://" + req.host + req.uri
    URI.create(baseUri).resolve(relativeUri).toString
  }

  val credentialsPathConfigurationKey = OAuth2Client.getClass.getName.stripSuffix("$") + ".credentialsPath"
  val defaultCredentialsPath = "classpath:credentials/google-oauth2.json"
}

/**
 * OAuth2Client class for handling OAuth2 authentication and token management.
 *
 * @param configuration The Play configuration.
 * @param ws The WSClient for making HTTP requests.
 */
@Singleton
class OAuth2Client @Inject()(configuration: Configuration, ws: WSClient) extends Logging {
  import OAuth2Client.*

  private val applicationCredentials: Credentials = {
    val credentialsPath = configuration.getOptional[String](credentialsPathConfigurationKey).getOrElse(defaultCredentialsPath)
    if (credentialsPath.startsWith("classpath:")) {
      getClass.getClassLoader.getResourceAsStream(credentialsPath.substring(10)) match {
        case null => throw new Exception(s"$credentialsPath not found")
        case stream =>
          logger.info(s"Loading OAuth2 credentials from $credentialsPath")
          Credentials(Json.parse(stream))
      }
    } else {
      throw new Exception(s"Only classpath: URIs are supported, not $credentialsPath")
    }
  }

  /**
   * Generates the URI for an OAuth2 access request.
   *
   * @param scope The requested scopes.
   * @param redirectUri The redirect URI.
   * @return The access request URI as a string.
   */
  def accessRequestUri(scope: String*)(redirectUri: String): String = {
    val scopes = scope.mkString(" ")
    val params = Seq(
      "client_id" -> applicationCredentials.client_id,
      "redirect_uri" -> redirectUri,
      "response_type" -> "code",
      "scope" -> scopes
    ).map((k, v) => s"$k=${urlEncode(v)}").mkString("&")
    applicationCredentials.auth_uri + s"?$params"
  }

  /**
   * Generates a temporary redirect result for an OAuth2 access request.
   *
   * @param scope The requested scopes.
   * @param localPath The local path for the redirect URI.
   * @param req The current request.
   * @return The temporary redirect result.
   */
  def accessRequest(scope: String*)(localPath: String)(using req: Request[AnyContent]): Result = {
    TemporaryRedirect(accessRequestUri(scope:_*)(localUri(localPath)))
  }

  /**
   * Requests an OAuth2 token using an authorization code.
   *
   * @param code The authorization code.
   * @param redirectUri The redirect URI.
   * @param ec The execution context.
   * @return A future containing the OAuth2 token.
   */
  def requestTokenForUri(code: String)(redirectUri: String)(using ExecutionContext): Future[OAuth2Token] = {
    ws.url(applicationCredentials.token_uri).withQueryStringParameters(
      "code" -> code,
      "client_id" -> applicationCredentials.client_id,
      "client_secret" -> applicationCredentials.client_secret,
      "redirect_uri" -> redirectUri,
      "grant_type" -> "authorization_code"
    ).execute("POST").map { response =>
      logger.debug(s"requestToken: ${response.status} ${response.statusText}: ${response.body}")
      response.status match {
        case 200 => OAuth2Token(response.body)
        case other => throw new RuntimeException(response.statusText)
      }
    }
  }

  /**
   * Requests an OAuth2 token using an authorization code and a local path.
   *
   * @param code The authorization code.
   * @param localPath The local path for the redirect URI.
   * @param req The current request.
   * @param ec The execution context.
   * @return A future containing the OAuth2 token.
   */
  def requestToken(code: String)(localPath: String)(using Request[AnyContent], ExecutionContext): Future[OAuth2Token] = {
    requestTokenForUri(code)(localUri(localPath))
  }

  /**
   * Refreshes an OAuth2 token.
   *
   * @param oAuth2Token The current OAuth2 token.
   * @param ec The execution context.
   * @return A future containing the new OAuth2 token.
   */
  def refreshToken(oAuth2Token: OAuth2Token)(using ExecutionContext): Future[OAuth2Token] = {
    ws.url(applicationCredentials.token_uri).withQueryStringParameters(
      "client_id" -> applicationCredentials.client_id,
      "client_secret" -> applicationCredentials.client_secret,
      "refresh_token" -> oAuth2Token.refresh_token,
      "grant_type" -> "refresh_token"
    ).execute("POST").map { response =>
      logger.debug(s"refreshToken: ${response.status} ${response.statusText}: ${response.body}")
      response.status match {
        case 200 => OAuth2Token(response.body)
        case other => throw new RuntimeException(response.statusText)
      }
    }
  }

  /**
   * SimpleTokenRepository class for managing OAuth2 tokens.
   *
   * @param initialToken The initial OAuth2 token.
   * @param ec The execution context.
   */
  class SimpleTokenRepository(private val initialToken: Future[OAuth2Token])(using ExecutionContext) extends TokenRepository[OAuth2Token] {
    val current = new AtomicReference[Future[OAuth2Token]](initialToken)

    /**
     * Gets the current OAuth2 token.
     *
     * @param scope The requested scopes.
     * @return A future containing the current OAuth2 token.
     */
    def getCurrent(scope: String*): Future[OAuth2Token] = current.get()

    /**
     * Gets the next OAuth2 token by refreshing the current token.
     *
     * @param oldToken The current OAuth2 token.
     * @return A future containing the new OAuth2 token.
     */
    def getNext(oldToken: OAuth2Token): Future[OAuth2Token] = {
      current.updateAndGet(fOldToken =>
        fOldToken.flatMap { oldToken =>
          refreshToken(oldToken)
        }
      )
    }
  }

  object SimpleTokenRepository {
    /**
     * Creates a SimpleTokenRepository with an initial OAuth2 token.
     *
     * @param initialToken The initial OAuth2 token.
     * @param ec The execution context.
     * @return The SimpleTokenRepository instance.
     */
    def apply(initialToken: OAuth2Token)(using ExecutionContext): SimpleTokenRepository = new SimpleTokenRepository(successful(initialToken))
  }
}