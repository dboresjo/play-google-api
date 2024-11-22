package com.boresjo.play.api.google.iamcredentials

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://iamcredentials.googleapis.com/"

	object projects {
		object serviceAccounts {
			class generateIdToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGenerateIdTokenRequest(body: Schema.GenerateIdTokenRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GenerateIdTokenResponse])
			}
			object generateIdToken {
				def apply(projectsId :PlayApi, serviceAccountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): generateIdToken = new generateIdToken(ws.url(BASE_URL + s"v1/projects/${projectsId}/serviceAccounts/${serviceAccountsId}:generateIdToken").addQueryStringParameters("name" -> name.toString))
			}
			class signBlob(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSignBlobRequest(body: Schema.SignBlobRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SignBlobResponse])
			}
			object signBlob {
				def apply(projectsId :PlayApi, serviceAccountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): signBlob = new signBlob(ws.url(BASE_URL + s"v1/projects/${projectsId}/serviceAccounts/${serviceAccountsId}:signBlob").addQueryStringParameters("name" -> name.toString))
			}
			class generateAccessToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGenerateAccessTokenRequest(body: Schema.GenerateAccessTokenRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GenerateAccessTokenResponse])
			}
			object generateAccessToken {
				def apply(projectsId :PlayApi, serviceAccountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): generateAccessToken = new generateAccessToken(ws.url(BASE_URL + s"v1/projects/${projectsId}/serviceAccounts/${serviceAccountsId}:generateAccessToken").addQueryStringParameters("name" -> name.toString))
			}
			class getAllowedLocations(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ServiceAccountAllowedLocations]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ServiceAccountAllowedLocations])
			}
			object getAllowedLocations {
				def apply(projectsId :PlayApi, serviceAccountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getAllowedLocations = new getAllowedLocations(ws.url(BASE_URL + s"v1/projects/${projectsId}/serviceAccounts/${serviceAccountsId}/allowedLocations").addQueryStringParameters("name" -> name.toString))
				given Conversion[getAllowedLocations, Future[Schema.ServiceAccountAllowedLocations]] = (fun: getAllowedLocations) => fun.apply()
			}
			class signJwt(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSignJwtRequest(body: Schema.SignJwtRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SignJwtResponse])
			}
			object signJwt {
				def apply(projectsId :PlayApi, serviceAccountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): signJwt = new signJwt(ws.url(BASE_URL + s"v1/projects/${projectsId}/serviceAccounts/${serviceAccountsId}:signJwt").addQueryStringParameters("name" -> name.toString))
			}
		}
	}
}
