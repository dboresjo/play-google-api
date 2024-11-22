package com.boresjo.play.api.google.playgrouping

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://playgrouping.googleapis.com/"

	object apps {
		object tokens {
			class verify(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withVerifyTokenRequest(body: Schema.VerifyTokenRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.VerifyTokenResponse])
			}
			object verify {
				def apply(appsId :PlayApi, tokensId :PlayApi, appPackage: String, token: String)(using auth: AuthToken, ec: ExecutionContext): verify = new verify(ws.url(BASE_URL + s"v1alpha1/apps/${appsId}/tokens/${tokensId}:verify").addQueryStringParameters("appPackage" -> appPackage.toString, "token" -> token.toString))
			}
			object tags {
				class createOrUpdate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCreateOrUpdateTagsRequest(body: Schema.CreateOrUpdateTagsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CreateOrUpdateTagsResponse])
				}
				object createOrUpdate {
					def apply(appsId :PlayApi, tokensId :PlayApi, appPackage: String, token: String)(using auth: AuthToken, ec: ExecutionContext): createOrUpdate = new createOrUpdate(ws.url(BASE_URL + s"v1alpha1/apps/${appsId}/tokens/${tokensId}/tags:createOrUpdate").addQueryStringParameters("appPackage" -> appPackage.toString, "token" -> token.toString))
				}
			}
		}
	}
}
