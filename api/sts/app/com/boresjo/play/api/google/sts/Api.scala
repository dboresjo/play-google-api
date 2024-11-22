package com.boresjo.play.api.google.sts

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

	private val BASE_URL = "https://sts.googleapis.com/"

	object v1 {
		/** Exchanges a credential for a Google OAuth 2.0 access token. The token asserts an external identity within an identity pool, or it applies a Credential Access Boundary to a Google access token. Note that workforce pools do not support Credential Access Boundaries. When you call this method, do not send the `Authorization` HTTP header in the request. This method does not require the `Authorization` header, and using the header can cause the request to fail. */
		class token(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq()
			/** Perform the request */
			def withGoogleIdentityStsV1ExchangeTokenRequest(body: Schema.GoogleIdentityStsV1ExchangeTokenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleIdentityStsV1ExchangeTokenResponse])
		}
		object token {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): token = new token(ws.url(BASE_URL + s"v1/token").addQueryStringParameters())
		}
	}
}
