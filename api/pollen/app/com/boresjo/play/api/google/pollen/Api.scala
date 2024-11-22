package com.boresjo.play.api.google.pollen

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

	private val BASE_URL = "https://pollen.googleapis.com/"

	object forecast {
		/** Returns up to 5 days of daily pollen information in more than 65 countries, up to 1km resolution. */
		class lookup(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LookupForecastResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. Contains general information about plants, including details on their seasonality, special shapes and colors, information about allergic cross-reactions, and plant photos. The default value is "true". */
			def withPlantsDescription(plantsDescription: Boolean) = new lookup(req.addQueryStringParameters("plantsDescription" -> plantsDescription.toString))
			/** Optional. Allows the client to choose the language for the response. If data cannot be provided for that language, the API uses the closest match. Allowed values rely on the IETF BCP-47 standard. The default value is "en". */
			def withLanguageCode(languageCode: String) = new lookup(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			/** Optional. The maximum number of daily info records to return per page. The default and max value is 5, indicating 5 days of data.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new lookup(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A page token received from a previous daily call. It is used to retrieve the subsequent page. Note that when providing a value for the page token, all other request parameters provided must match the previous call that provided the page token. */
			def withPageToken(pageToken: String) = new lookup(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LookupForecastResponse])
		}
		object lookup {
			def apply(days: Int, locationLatitude: Number, locationLongitude: Number)(using signer: RequestSigner, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"v1/forecast:lookup").addQueryStringParameters("days" -> days.toString, "location.latitude" -> locationLatitude.toString, "location.longitude" -> locationLongitude.toString))
			given Conversion[lookup, Future[Schema.LookupForecastResponse]] = (fun: lookup) => fun.apply()
		}
	}
	object mapTypes {
		object heatmapTiles {
			/** Returns a byte array containing the data of the tile PNG image. */
			class lookupHeatmapTile(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
			}
			object lookupHeatmapTile {
				def apply(x: Int, zoom: Int, y: Int, mapType: String)(using signer: RequestSigner, ec: ExecutionContext): lookupHeatmapTile = new lookupHeatmapTile(ws.url(BASE_URL + s"v1/mapTypes/${mapType}/heatmapTiles/${zoom}/${x}/${y}").addQueryStringParameters())
				given Conversion[lookupHeatmapTile, Future[Schema.HttpBody]] = (fun: lookupHeatmapTile) => fun.apply()
			}
		}
	}
}
