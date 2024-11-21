package com.boresjo.play.api.google.pollen

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://pollen.googleapis.com/"

	object forecast {
		class lookup(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LookupForecastResponse]) {
			/** Optional. Contains general information about plants, including details on their seasonality, special shapes and colors, information about allergic cross-reactions, and plant photos. The default value is "true". */
			def withPlantsDescription(plantsDescription: Boolean) = new lookup(req.addQueryStringParameters("plantsDescription" -> plantsDescription.toString))
			/** Optional. Allows the client to choose the language for the response. If data cannot be provided for that language, the API uses the closest match. Allowed values rely on the IETF BCP-47 standard. The default value is "en". */
			def withLanguageCode(languageCode: String) = new lookup(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			/** Optional. The maximum number of daily info records to return per page. The default and max value is 5, indicating 5 days of data.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new lookup(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A page token received from a previous daily call. It is used to retrieve the subsequent page. Note that when providing a value for the page token, all other request parameters provided must match the previous call that provided the page token. */
			def withPageToken(pageToken: String) = new lookup(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.LookupForecastResponse])
		}
		object lookup {
			def apply(days: Int, locationLatitude: Number, locationLongitude: Number)(using auth: AuthToken, ec: ExecutionContext): lookup = new lookup(auth(ws.url(BASE_URL + s"v1/forecast:lookup")).addQueryStringParameters("days" -> days.toString, "location.latitude" -> locationLatitude.toString, "location.longitude" -> locationLongitude.toString))
			given Conversion[lookup, Future[Schema.LookupForecastResponse]] = (fun: lookup) => fun.apply()
		}
	}
	object mapTypes {
		object heatmapTiles {
			class lookupHeatmapTile(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
			}
			object lookupHeatmapTile {
				def apply(x: Int, zoom: Int, y: Int, mapType: String)(using auth: AuthToken, ec: ExecutionContext): lookupHeatmapTile = new lookupHeatmapTile(auth(ws.url(BASE_URL + s"v1/mapTypes/${mapType}/heatmapTiles/${zoom}/${x}/${y}")).addQueryStringParameters())
				given Conversion[lookupHeatmapTile, Future[Schema.HttpBody]] = (fun: lookupHeatmapTile) => fun.apply()
			}
		}
	}
}
