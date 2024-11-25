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

object OAuth2Client {
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

    def apply(credentials: String): Credentials = apply(Json.parse(credentials))

    def apply(json: JsValue): Credentials = (json \ "installed").asOpt[JsObject] match {
      case Some(json) => json.as[Credentials]
      case None => json.as[Credentials]
    }
  }

  /** returns a URI on the same host as this request */
  def localUri(relativeUri: String)(using req: Request[AnyContent]): String = {
    // TODO examine proxy headers to find original protocol (secure vs insecure)
    val baseUri = (if (req.secure || !req.host.startsWith("localhost:")) "https" else "http") + "://" + req.host + req.uri
    URI.create(baseUri).resolve(relativeUri).toString
  }

  val credentialsPathConfigurationKey = OAuth2Client.getClass.getName.stripSuffix("$")   + ".credentialsPath"
  val defaultCredentialsPath = "classpath:credentials/google-oauth2.json"
}

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

  def accessRequest(scope: String*)(localPath: String)(using req: Request[AnyContent]): Result = {
    TemporaryRedirect(accessRequestUri(scope:_*)(localUri(localPath)))
  }

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

  def requestToken(code: String)(localPath: String)(using Request[AnyContent], ExecutionContext): Future[OAuth2Token] = {
    requestTokenForUri(code)(localUri(localPath))
  }

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

  class SimpleTokenRepository(private val initialToken: Future[OAuth2Token])(using ExecutionContext) extends TokenRepository[OAuth2Token] {
    val current = new AtomicReference[Future[OAuth2Token]](initialToken)

    def getCurrent(scope: String*): Future[OAuth2Token] = current.get()

    def getNext(oldToken: OAuth2Token): Future[OAuth2Token] = {
      current.updateAndGet(fOldToken =>
        fOldToken.flatMap { oldToken =>
          refreshToken(oldToken)
        }
      )
    }
  }
  object SimpleTokenRepository {
    def apply(initialToken: OAuth2Token)(using ExecutionContext): SimpleTokenRepository = new SimpleTokenRepository(successful(initialToken))
  }
}
