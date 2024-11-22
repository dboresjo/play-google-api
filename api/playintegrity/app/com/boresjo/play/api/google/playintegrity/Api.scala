package com.boresjo.play.api.google.playintegrity

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
		"""https://www.googleapis.com/auth/playintegrity""" /* Private Service: https://www.googleapis.com/auth/playintegrity */
	)

	private val BASE_URL = "https://playintegrity.googleapis.com/"

	object v1 {
		/** Decodes the integrity token and returns the token payload. */
		class decodeIntegrityToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/playintegrity""")
			/** Perform the request */
			def withDecodeIntegrityTokenRequest(body: Schema.DecodeIntegrityTokenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DecodeIntegrityTokenResponse])
		}
		object decodeIntegrityToken {
			def apply(v1Id :PlayApi, packageName: String)(using signer: RequestSigner, ec: ExecutionContext): decodeIntegrityToken = new decodeIntegrityToken(ws.url(BASE_URL + s"v1/${v1Id}:decodeIntegrityToken").addQueryStringParameters("packageName" -> packageName.toString))
		}
	}
	object deviceRecall {
		/** Writes recall bits for the device where Play Integrity API token is obtained. The endpoint is available to select Play partners in an early access program (EAP). */
		class write(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/playintegrity""")
			/** Perform the request */
			def withWriteDeviceRecallRequest(body: Schema.WriteDeviceRecallRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.WriteDeviceRecallResponse])
		}
		object write {
			def apply(v1Id :PlayApi, packageName: String)(using signer: RequestSigner, ec: ExecutionContext): write = new write(ws.url(BASE_URL + s"v1/${v1Id}/deviceRecall:write").addQueryStringParameters("packageName" -> packageName.toString))
		}
	}
}
