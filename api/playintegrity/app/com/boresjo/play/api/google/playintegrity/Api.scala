package com.boresjo.play.api.google.playintegrity

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://playintegrity.googleapis.com/"

	object v1 {
		class decodeIntegrityToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDecodeIntegrityTokenRequest(body: Schema.DecodeIntegrityTokenRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DecodeIntegrityTokenResponse])
		}
		object decodeIntegrityToken {
			def apply(v1Id :PlayApi, packageName: String)(using auth: AuthToken, ec: ExecutionContext): decodeIntegrityToken = new decodeIntegrityToken(ws.url(BASE_URL + s"v1/${v1Id}:decodeIntegrityToken").addQueryStringParameters("packageName" -> packageName.toString))
		}
	}
	object deviceRecall {
		class write(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withWriteDeviceRecallRequest(body: Schema.WriteDeviceRecallRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.WriteDeviceRecallResponse])
		}
		object write {
			def apply(v1Id :PlayApi, packageName: String)(using auth: AuthToken, ec: ExecutionContext): write = new write(ws.url(BASE_URL + s"v1/${v1Id}/deviceRecall:write").addQueryStringParameters("packageName" -> packageName.toString))
		}
	}
}
