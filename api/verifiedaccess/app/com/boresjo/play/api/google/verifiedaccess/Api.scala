package com.boresjo.play.api.google.verifiedaccess

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
		"""https://www.googleapis.com/auth/verifiedaccess""" /* Verify your enterprise credentials */
	)

	private val BASE_URL = "https://verifiedaccess.googleapis.com/"

	object challenge {
		/** Generates a new challenge. */
		class generate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/verifiedaccess""")
			/** Perform the request */
			def withEmpty(body: Schema.Empty) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Challenge])
		}
		object generate {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): generate = new generate(ws.url(BASE_URL + s"v2/challenge:generate").addQueryStringParameters())
		}
		/** Verifies the challenge response. */
		class verify(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/verifiedaccess""")
			/** Perform the request */
			def withVerifyChallengeResponseRequest(body: Schema.VerifyChallengeResponseRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.VerifyChallengeResponseResult])
		}
		object verify {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): verify = new verify(ws.url(BASE_URL + s"v2/challenge:verify").addQueryStringParameters())
		}
	}
}
