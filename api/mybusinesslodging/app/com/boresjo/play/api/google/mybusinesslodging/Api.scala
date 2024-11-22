package com.boresjo.play.api.google.mybusinesslodging

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://mybusinesslodging.googleapis.com/"

	object locations {
		class getLodging(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Lodging]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Lodging])
		}
		object getLodging {
			def apply(locationsId :PlayApi, name: String, readMask: String)(using auth: AuthToken, ec: ExecutionContext): getLodging = new getLodging(ws.url(BASE_URL + s"v1/locations/${locationsId}/lodging").addQueryStringParameters("name" -> name.toString, "readMask" -> readMask.toString))
			given Conversion[getLodging, Future[Schema.Lodging]] = (fun: getLodging) => fun.apply()
		}
		class updateLodging(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLodging(body: Schema.Lodging) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Lodging])
		}
		object updateLodging {
			def apply(locationsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateLodging = new updateLodging(ws.url(BASE_URL + s"v1/locations/${locationsId}/lodging").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		object lodging {
			class getGoogleUpdated(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GetGoogleUpdatedLodgingResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GetGoogleUpdatedLodgingResponse])
			}
			object getGoogleUpdated {
				def apply(locationsId :PlayApi, name: String, readMask: String)(using auth: AuthToken, ec: ExecutionContext): getGoogleUpdated = new getGoogleUpdated(ws.url(BASE_URL + s"v1/locations/${locationsId}/lodging:getGoogleUpdated").addQueryStringParameters("name" -> name.toString, "readMask" -> readMask.toString))
				given Conversion[getGoogleUpdated, Future[Schema.GetGoogleUpdatedLodgingResponse]] = (fun: getGoogleUpdated) => fun.apply()
			}
		}
	}
}
