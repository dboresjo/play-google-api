package com.boresjo.play.api.google.chromeuxreport

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://chromeuxreport.googleapis.com/"

	object records {
		class queryRecord(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withQueryRequest(body: Schema.QueryRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.QueryResponse])
		}
		object queryRecord {
			def apply()(using auth: AuthToken, ec: ExecutionContext): queryRecord = new queryRecord(ws.url(BASE_URL + s"v1/records:queryRecord").addQueryStringParameters())
		}
		class queryHistoryRecord(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withQueryHistoryRequest(body: Schema.QueryHistoryRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.QueryHistoryResponse])
		}
		object queryHistoryRecord {
			def apply()(using auth: AuthToken, ec: ExecutionContext): queryHistoryRecord = new queryHistoryRecord(ws.url(BASE_URL + s"v1/records:queryHistoryRecord").addQueryStringParameters())
		}
	}
}
