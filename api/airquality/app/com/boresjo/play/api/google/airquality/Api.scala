package com.boresjo.play.api.google.airquality

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://airquality.googleapis.com/"

	object history {
		class lookup(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLookupHistoryRequest(body: Schema.LookupHistoryRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LookupHistoryResponse])
		}
		object lookup {
			def apply()(using auth: AuthToken, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"v1/history:lookup").addQueryStringParameters())
		}
	}
	object mapTypes {
		object heatmapTiles {
			class lookupHeatmapTile(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
			}
			object lookupHeatmapTile {
				def apply(mapType: String, x: Int, zoom: Int, y: Int)(using auth: AuthToken, ec: ExecutionContext): lookupHeatmapTile = new lookupHeatmapTile(ws.url(BASE_URL + s"v1/mapTypes/${mapType}/heatmapTiles/${zoom}/${x}/${y}").addQueryStringParameters())
				given Conversion[lookupHeatmapTile, Future[Schema.HttpBody]] = (fun: lookupHeatmapTile) => fun.apply()
			}
		}
	}
	object currentConditions {
		class lookup(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLookupCurrentConditionsRequest(body: Schema.LookupCurrentConditionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LookupCurrentConditionsResponse])
		}
		object lookup {
			def apply()(using auth: AuthToken, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"v1/currentConditions:lookup").addQueryStringParameters())
		}
	}
	object forecast {
		class lookup(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLookupForecastRequest(body: Schema.LookupForecastRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LookupForecastResponse])
		}
		object lookup {
			def apply()(using auth: AuthToken, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"v1/forecast:lookup").addQueryStringParameters())
		}
	}
}
