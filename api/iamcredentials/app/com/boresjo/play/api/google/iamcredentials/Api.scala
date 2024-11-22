package com.boresjo.play.api.google.iamcredentials

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq(
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://iamcredentials.googleapis.com/"

	object projects {
		object serviceAccounts {
			/** Generates an OpenID Connect ID token for a service account. */
			class generateIdToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGenerateIdTokenRequest(body: Schema.GenerateIdTokenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GenerateIdTokenResponse])
			}
			object generateIdToken {
				def apply(projectsId :PlayApi, serviceAccountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): generateIdToken = new generateIdToken(ws.url(BASE_URL + s"v1/projects/${projectsId}/serviceAccounts/${serviceAccountsId}:generateIdToken").addQueryStringParameters("name" -> name.toString))
			}
			/** Signs a blob using a service account's system-managed private key. */
			class signBlob(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withSignBlobRequest(body: Schema.SignBlobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SignBlobResponse])
			}
			object signBlob {
				def apply(projectsId :PlayApi, serviceAccountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): signBlob = new signBlob(ws.url(BASE_URL + s"v1/projects/${projectsId}/serviceAccounts/${serviceAccountsId}:signBlob").addQueryStringParameters("name" -> name.toString))
			}
			/** Generates an OAuth 2.0 access token for a service account. */
			class generateAccessToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGenerateAccessTokenRequest(body: Schema.GenerateAccessTokenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GenerateAccessTokenResponse])
			}
			object generateAccessToken {
				def apply(projectsId :PlayApi, serviceAccountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): generateAccessToken = new generateAccessToken(ws.url(BASE_URL + s"v1/projects/${projectsId}/serviceAccounts/${serviceAccountsId}:generateAccessToken").addQueryStringParameters("name" -> name.toString))
			}
			/** Returns the trust boundary info for a given service account. */
			class getAllowedLocations(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ServiceAccountAllowedLocations]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ServiceAccountAllowedLocations])
			}
			object getAllowedLocations {
				def apply(projectsId :PlayApi, serviceAccountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getAllowedLocations = new getAllowedLocations(ws.url(BASE_URL + s"v1/projects/${projectsId}/serviceAccounts/${serviceAccountsId}/allowedLocations").addQueryStringParameters("name" -> name.toString))
				given Conversion[getAllowedLocations, Future[Schema.ServiceAccountAllowedLocations]] = (fun: getAllowedLocations) => fun.apply()
			}
			/** Signs a JWT using a service account's system-managed private key. */
			class signJwt(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withSignJwtRequest(body: Schema.SignJwtRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SignJwtResponse])
			}
			object signJwt {
				def apply(projectsId :PlayApi, serviceAccountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): signJwt = new signJwt(ws.url(BASE_URL + s"v1/projects/${projectsId}/serviceAccounts/${serviceAccountsId}:signJwt").addQueryStringParameters("name" -> name.toString))
			}
		}
	}
}
