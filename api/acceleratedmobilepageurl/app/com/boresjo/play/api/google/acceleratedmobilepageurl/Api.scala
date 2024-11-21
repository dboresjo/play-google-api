package com.boresjo.play.api.google.acceleratedmobilepageurl

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://acceleratedmobilepageurl.googleapis.com/"

	object ampUrls {
		class batchGet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBatchGetAmpUrlsRequest(body: Schema.BatchGetAmpUrlsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.BatchGetAmpUrlsResponse])
		}
		object batchGet {
			def apply()(using auth: AuthToken, ec: ExecutionContext): batchGet = new batchGet(auth(ws.url(BASE_URL + s"v1/ampUrls:batchGet")).addQueryStringParameters())
		}
	}
}
