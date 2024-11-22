package com.boresjo.play.api.google.mybusinesslodging

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

	private val BASE_URL = "https://mybusinesslodging.googleapis.com/"

	object locations {
		/** Returns the Lodging of a specific location. */
		class getLodging(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Lodging]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Lodging])
		}
		object getLodging {
			def apply(locationsId :PlayApi, name: String, readMask: String)(using signer: RequestSigner, ec: ExecutionContext): getLodging = new getLodging(ws.url(BASE_URL + s"v1/locations/${locationsId}/lodging").addQueryStringParameters("name" -> name.toString, "readMask" -> readMask.toString))
			given Conversion[getLodging, Future[Schema.Lodging]] = (fun: getLodging) => fun.apply()
		}
		/** Updates the Lodging of a specific location. */
		class updateLodging(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq()
			/** Perform the request */
			def withLodging(body: Schema.Lodging) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Lodging])
		}
		object updateLodging {
			def apply(locationsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateLodging = new updateLodging(ws.url(BASE_URL + s"v1/locations/${locationsId}/lodging").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		object lodging {
			/** Returns the Google updated Lodging of a specific location. */
			class getGoogleUpdated(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GetGoogleUpdatedLodgingResponse]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GetGoogleUpdatedLodgingResponse])
			}
			object getGoogleUpdated {
				def apply(locationsId :PlayApi, name: String, readMask: String)(using signer: RequestSigner, ec: ExecutionContext): getGoogleUpdated = new getGoogleUpdated(ws.url(BASE_URL + s"v1/locations/${locationsId}/lodging:getGoogleUpdated").addQueryStringParameters("name" -> name.toString, "readMask" -> readMask.toString))
				given Conversion[getGoogleUpdated, Future[Schema.GetGoogleUpdatedLodgingResponse]] = (fun: getGoogleUpdated) => fun.apply()
			}
		}
	}
}
