package com.boresjo.play.api.google.airquality

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://airquality.googleapis.com/"

	object history {
		/** Returns air quality history for a specific location for a given time range. */
		class lookup(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withLookupHistoryRequest(body: Schema.LookupHistoryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LookupHistoryResponse])
		}
		object lookup {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"v1/history:lookup").addQueryStringParameters())
		}
	}
	object mapTypes {
		object heatmapTiles {
			/** Returns a bytes array containing the data of the tile PNG image. */
			class lookupHeatmapTile(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
			}
			object lookupHeatmapTile {
				def apply(mapType: String, x: Int, zoom: Int, y: Int)(using signer: RequestSigner, ec: ExecutionContext): lookupHeatmapTile = new lookupHeatmapTile(ws.url(BASE_URL + s"v1/mapTypes/${mapType}/heatmapTiles/${zoom}/${x}/${y}").addQueryStringParameters())
				given Conversion[lookupHeatmapTile, Future[Schema.HttpBody]] = (fun: lookupHeatmapTile) => fun.apply()
			}
		}
	}
	object currentConditions {
		/** The Current Conditions endpoint provides hourly air quality information in more than 100 countries, up to a 500 x 500 meters resolution. Includes over 70 local indexes and global air quality index and categories. */
		class lookup(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withLookupCurrentConditionsRequest(body: Schema.LookupCurrentConditionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LookupCurrentConditionsResponse])
		}
		object lookup {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"v1/currentConditions:lookup").addQueryStringParameters())
		}
	}
	object forecast {
		/** Returns air quality forecast for a specific location for a given time range. */
		class lookup(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withLookupForecastRequest(body: Schema.LookupForecastRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LookupForecastResponse])
		}
		object lookup {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"v1/forecast:lookup").addQueryStringParameters())
		}
	}
}
