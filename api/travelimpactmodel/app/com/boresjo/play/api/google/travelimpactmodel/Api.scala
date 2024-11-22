package com.boresjo.play.api.google.travelimpactmodel

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://travelimpactmodel.googleapis.com/"

	object flights {
		class computeFlightEmissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withComputeFlightEmissionsRequest(body: Schema.ComputeFlightEmissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ComputeFlightEmissionsResponse])
		}
		object computeFlightEmissions {
			def apply()(using auth: AuthToken, ec: ExecutionContext): computeFlightEmissions = new computeFlightEmissions(ws.url(BASE_URL + s"v1/flights:computeFlightEmissions").addQueryStringParameters())
		}
	}
}
