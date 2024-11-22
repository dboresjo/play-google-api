package com.boresjo.play.api.google.analyticsreporting

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
		"""https://www.googleapis.com/auth/analytics""" /* View and manage your Google Analytics data */,
		"""https://www.googleapis.com/auth/analytics.readonly""" /* See and download your Google Analytics data */
	)

	private val BASE_URL = "https://analyticsreporting.googleapis.com/"

	object reports {
		/** Returns the Analytics data. */
		class batchGet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.readonly""")
			/** Perform the request */
			def withGetReportsRequest(body: Schema.GetReportsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GetReportsResponse])
		}
		object batchGet {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"v4/reports:batchGet").addQueryStringParameters())
		}
	}
	object userActivity {
		/** Returns User Activity data. */
		class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/analytics""", """https://www.googleapis.com/auth/analytics.readonly""")
			/** Perform the request */
			def withSearchUserActivityRequest(body: Schema.SearchUserActivityRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SearchUserActivityResponse])
		}
		object search {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v4/userActivity:search").addQueryStringParameters())
		}
	}
}
