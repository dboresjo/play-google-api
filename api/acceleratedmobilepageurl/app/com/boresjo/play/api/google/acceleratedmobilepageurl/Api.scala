package com.boresjo.play.api.google.acceleratedmobilepageurl

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq()

	private val BASE_URL = "https://acceleratedmobilepageurl.googleapis.com/"

	object ampUrls {
		/** Returns AMP URL(s) and equivalent [AMP Cache URL(s)](/amp/cache/overview#amp-cache-url-format). */
		class batchGet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq()
			/** Perform the request */
			def withBatchGetAmpUrlsRequest(body: Schema.BatchGetAmpUrlsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchGetAmpUrlsResponse])
		}
		object batchGet {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"v1/ampUrls:batchGet").addQueryStringParameters())
		}
	}
}
