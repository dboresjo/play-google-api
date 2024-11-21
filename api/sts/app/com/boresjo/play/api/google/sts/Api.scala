package com.boresjo.play.api.google.sts

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://sts.googleapis.com/"

	object v1 {
		class token(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleIdentityStsV1ExchangeTokenRequest(body: Schema.GoogleIdentityStsV1ExchangeTokenRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleIdentityStsV1ExchangeTokenResponse])
		}
		object token {
			def apply()(using auth: AuthToken, ec: ExecutionContext): token = new token(auth(ws.url(BASE_URL + s"v1/token")).addQueryStringParameters())
		}
	}
}
