package com.boresjo.play.api.google.servicecontrol

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://servicecontrol.googleapis.com/"

	object services {
		class check(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCheckRequest(body: Schema.CheckRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CheckResponse])
		}
		object check {
			def apply(serviceName: String)(using auth: AuthToken, ec: ExecutionContext): check = new check(auth(ws.url(BASE_URL + s"v2/services/${serviceName}:check")).addQueryStringParameters())
		}
		class report(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withReportRequest(body: Schema.ReportRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ReportResponse])
		}
		object report {
			def apply(serviceName: String)(using auth: AuthToken, ec: ExecutionContext): report = new report(auth(ws.url(BASE_URL + s"v2/services/${serviceName}:report")).addQueryStringParameters())
		}
	}
}
