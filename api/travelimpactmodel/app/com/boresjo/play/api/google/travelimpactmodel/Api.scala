package com.boresjo.play.api.google.travelimpactmodel

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

	private val BASE_URL = "https://travelimpactmodel.googleapis.com/"

	object flights {
		/** Stateless method to retrieve emission estimates. Details on how emission estimates are computed: https://github.com/google/travel-impact-model The response will contain all entries that match the input flight legs, in the same order. If there are no estimates available for a certain flight leg, the response will return the flight leg object with empty emission fields. The request will still be considered successful. Reasons for missing emission estimates include: - The flight is unknown to the server. - The input flight leg is missing one or more identifiers. - The flight date is in the past. - The aircraft type is not supported by the model. - Missing seat configuration. The request can contain up to 1000 flight legs. If the request has more than 1000 direct flights, if will fail with an INVALID_ARGUMENT error. */
		class computeFlightEmissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq()
			/** Perform the request */
			def withComputeFlightEmissionsRequest(body: Schema.ComputeFlightEmissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ComputeFlightEmissionsResponse])
		}
		object computeFlightEmissions {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): computeFlightEmissions = new computeFlightEmissions(ws.url(BASE_URL + s"v1/flights:computeFlightEmissions").addQueryStringParameters())
		}
	}
}
