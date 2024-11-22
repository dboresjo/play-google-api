package com.boresjo.play.api.google.trafficdirector

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://trafficdirector.googleapis.com/"

	object discovery {
		class client_status(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withClientStatusRequest(body: Schema.ClientStatusRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ClientStatusResponse])
		}
		object client_status {
			def apply()(using auth: AuthToken, ec: ExecutionContext): client_status = new client_status(ws.url(BASE_URL + s"v3/discovery:client_status").addQueryStringParameters())
		}
	}
}
