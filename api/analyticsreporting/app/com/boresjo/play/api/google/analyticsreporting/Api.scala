package com.boresjo.play.api.google.analyticsreporting

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://analyticsreporting.googleapis.com/"

	object reports {
		class batchGet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGetReportsRequest(body: Schema.GetReportsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GetReportsResponse])
		}
		object batchGet {
			def apply()(using auth: AuthToken, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"v4/reports:batchGet").addQueryStringParameters())
		}
	}
	object userActivity {
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSearchUserActivityRequest(body: Schema.SearchUserActivityRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SearchUserActivityResponse])
		}
		object search {
			def apply()(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v4/userActivity:search").addQueryStringParameters())
		}
	}
}
