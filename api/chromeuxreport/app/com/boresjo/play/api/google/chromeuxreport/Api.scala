package com.boresjo.play.api.google.chromeuxreport

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

	private val BASE_URL = "https://chromeuxreport.googleapis.com/"

	object records {
		/** Queries the Chrome User Experience for a single `record` for a given site. Returns a `record` that contains one or more `metrics` corresponding to performance data about the requested site. */
		class queryRecord(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq()
			/** Perform the request */
			def withQueryRequest(body: Schema.QueryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.QueryResponse])
		}
		object queryRecord {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): queryRecord = new queryRecord(ws.url(BASE_URL + s"v1/records:queryRecord").addQueryStringParameters())
		}
		/** Queries the Chrome User Experience Report for a timeseries `history record` for a given site. Returns a `history record` that contains one or more `metric timeseries` corresponding to performance data about the requested site. */
		class queryHistoryRecord(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq()
			/** Perform the request */
			def withQueryHistoryRequest(body: Schema.QueryHistoryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.QueryHistoryResponse])
		}
		object queryHistoryRecord {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): queryHistoryRecord = new queryHistoryRecord(ws.url(BASE_URL + s"v1/records:queryHistoryRecord").addQueryStringParameters())
		}
	}
}
