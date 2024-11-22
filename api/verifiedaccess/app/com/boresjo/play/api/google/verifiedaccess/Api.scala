package com.boresjo.play.api.google.verifiedaccess

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://verifiedaccess.googleapis.com/"

	object challenge {
		class generate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEmpty(body: Schema.Empty) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Challenge])
		}
		object generate {
			def apply()(using auth: AuthToken, ec: ExecutionContext): generate = new generate(ws.url(BASE_URL + s"v2/challenge:generate").addQueryStringParameters())
		}
		class verify(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withVerifyChallengeResponseRequest(body: Schema.VerifyChallengeResponseRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.VerifyChallengeResponseResult])
		}
		object verify {
			def apply()(using auth: AuthToken, ec: ExecutionContext): verify = new verify(ws.url(BASE_URL + s"v2/challenge:verify").addQueryStringParameters())
		}
	}
}
