package com.boresjo.play.api.google.solar

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://solar.googleapis.com/"

	object buildingInsights {
		class findClosest(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BuildingInsights]) {
			/** Optional. Specifies the pre-GA features to enable.<br>Possible values:<br>EXPERIMENT_UNSPECIFIED: No experiments are specified.<br>EXPANDED_COVERAGE: Expands the geographic region available for querying solar data. */
			def withExperiments(experiments: String) = new findClosest(req.addQueryStringParameters("experiments" -> experiments.toString))
			/** Optional. The minimum quality level allowed in the results. No result with lower quality than this will be returned. Not specifying this is equivalent to restricting to HIGH quality only.<br>Possible values:<br>IMAGERY_QUALITY_UNSPECIFIED: No quality is known.<br>HIGH: Solar data is derived from aerial imagery captured at low-altitude and processed at 0.1 m/pixel.<br>MEDIUM: Solar data is derived from enhanced aerial imagery captured at high-altitude and processed at 0.25 m/pixel.<br>LOW: Solar data is derived from enhanced satellite imagery processed at 0.25 m/pixel.<br>BASE: Solar data is derived from enhanced satellite imagery processed at 0.25 m/pixel. */
			def withRequiredQuality(requiredQuality: String) = new findClosest(req.addQueryStringParameters("requiredQuality" -> requiredQuality.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.BuildingInsights])
		}
		object findClosest {
			def apply(locationLongitude: Number, locationLatitude: Number)(using auth: AuthToken, ec: ExecutionContext): findClosest = new findClosest(auth(ws.url(BASE_URL + s"v1/buildingInsights:findClosest")).addQueryStringParameters("location.longitude" -> locationLongitude.toString, "location.latitude" -> locationLatitude.toString))
			given Conversion[findClosest, Future[Schema.BuildingInsights]] = (fun: findClosest) => fun.apply()
		}
	}
	object geoTiff {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.HttpBody])
		}
		object get {
			def apply(id: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/geoTiff:get")).addQueryStringParameters("id" -> id.toString))
			given Conversion[get, Future[Schema.HttpBody]] = (fun: get) => fun.apply()
		}
	}
	object dataLayers {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DataLayers]) {
			/** Optional. The minimum quality level allowed in the results. No result with lower quality than this will be returned. Not specifying this is equivalent to restricting to HIGH quality only.<br>Possible values:<br>IMAGERY_QUALITY_UNSPECIFIED: No quality is known.<br>HIGH: Solar data is derived from aerial imagery captured at low-altitude and processed at 0.1 m/pixel.<br>MEDIUM: Solar data is derived from enhanced aerial imagery captured at high-altitude and processed at 0.25 m/pixel.<br>LOW: Solar data is derived from enhanced satellite imagery processed at 0.25 m/pixel.<br>BASE: Solar data is derived from enhanced satellite imagery processed at 0.25 m/pixel. */
			def withRequiredQuality(requiredQuality: String) = new get(req.addQueryStringParameters("requiredQuality" -> requiredQuality.toString))
			/** Optional. The desired subset of the data to return.<br>Possible values:<br>DATA_LAYER_VIEW_UNSPECIFIED: Equivalent to FULL.<br>DSM_LAYER: Get the DSM only.<br>IMAGERY_LAYERS: Get the DSM, RGB, and mask.<br>IMAGERY_AND_ANNUAL_FLUX_LAYERS: Get the DSM, RGB, mask, and annual flux.<br>IMAGERY_AND_ALL_FLUX_LAYERS: Get the DSM, RGB, mask, annual flux, and monthly flux.<br>FULL_LAYERS: Get all data. */
			def withView(view: String) = new get(req.addQueryStringParameters("view" -> view.toString))
			/** Optional. Whether to require exact quality of the imagery. If set to false, the `required_quality` field is interpreted as the minimum required quality, such that HIGH quality imagery may be returned when `required_quality` is set to MEDIUM. If set to true, `required_quality` is interpreted as the exact required quality and only `MEDIUM` quality imagery is returned if `required_quality` is set to `MEDIUM`. */
			def withExactQualityRequired(exactQualityRequired: Boolean) = new get(req.addQueryStringParameters("exactQualityRequired" -> exactQualityRequired.toString))
			/** Optional. The minimum scale, in meters per pixel, of the data to return. Values of 0.1 (the default, if this field is not set explicitly), 0.25, 0.5, and 1.0 are supported. Imagery components whose normal resolution is less than `pixel_size_meters` will be returned at the resolution specified by `pixel_size_meters`; imagery components whose normal resolution is equal to or greater than `pixel_size_meters` will be returned at that normal resolution.<br>Format: float */
			def withPixelSizeMeters(pixelSizeMeters: Number) = new get(req.addQueryStringParameters("pixelSizeMeters" -> pixelSizeMeters.toString))
			/** Optional. Specifies the pre-GA experiments to enable.<br>Possible values:<br>EXPERIMENT_UNSPECIFIED: No experiments are specified.<br>EXPANDED_COVERAGE: Expands the geographic region available for querying solar data. */
			def withExperiments(experiments: String) = new get(req.addQueryStringParameters("experiments" -> experiments.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.DataLayers])
		}
		object get {
			def apply(radiusMeters: Number, locationLatitude: Number, locationLongitude: Number)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/dataLayers:get")).addQueryStringParameters("radiusMeters" -> radiusMeters.toString, "location.latitude" -> locationLatitude.toString, "location.longitude" -> locationLongitude.toString))
			given Conversion[get, Future[Schema.DataLayers]] = (fun: get) => fun.apply()
		}
	}
}
