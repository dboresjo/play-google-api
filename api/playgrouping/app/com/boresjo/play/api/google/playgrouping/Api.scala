package com.boresjo.play.api.google.playgrouping

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq()

	private val BASE_URL = "https://playgrouping.googleapis.com/"

	object apps {
		object tokens {
			/** Verify an API token by asserting the app and persona it belongs to. The verification is a protection against client-side attacks and will fail if the contents of the token don't match the provided values. A token must be verified before it can be used to manipulate user tags. */
			class verify(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Perform the request */
				def withVerifyTokenRequest(body: Schema.VerifyTokenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.VerifyTokenResponse])
			}
			object verify {
				def apply(appsId :PlayApi, tokensId :PlayApi, appPackage: String, token: String)(using signer: RequestSigner, ec: ExecutionContext): verify = new verify(ws.url(BASE_URL + s"v1alpha1/apps/${appsId}/tokens/${tokensId}:verify").addQueryStringParameters("appPackage" -> appPackage.toString, "token" -> token.toString))
			}
			object tags {
				/** Create or update tags for the user and app that are represented by the given token. */
				class createOrUpdate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq()
					/** Perform the request */
					def withCreateOrUpdateTagsRequest(body: Schema.CreateOrUpdateTagsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CreateOrUpdateTagsResponse])
				}
				object createOrUpdate {
					def apply(appsId :PlayApi, tokensId :PlayApi, appPackage: String, token: String)(using signer: RequestSigner, ec: ExecutionContext): createOrUpdate = new createOrUpdate(ws.url(BASE_URL + s"v1alpha1/apps/${appsId}/tokens/${tokensId}/tags:createOrUpdate").addQueryStringParameters("appPackage" -> appPackage.toString, "token" -> token.toString))
				}
			}
		}
	}
}
