package com.boresjo.play.api.google.travelimpactmodel

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtEmissionsGramsPerPax: Format[Schema.EmissionsGramsPerPax] = Json.format[Schema.EmissionsGramsPerPax]
	given fmtFlight: Format[Schema.Flight] = Json.format[Schema.Flight]
	given fmtDate: Format[Schema.Date] = Json.format[Schema.Date]
	given fmtComputeFlightEmissionsResponse: Format[Schema.ComputeFlightEmissionsResponse] = Json.format[Schema.ComputeFlightEmissionsResponse]
	given fmtFlightWithEmissions: Format[Schema.FlightWithEmissions] = Json.format[Schema.FlightWithEmissions]
	given fmtModelVersion: Format[Schema.ModelVersion] = Json.format[Schema.ModelVersion]
	given fmtComputeFlightEmissionsRequest: Format[Schema.ComputeFlightEmissionsRequest] = Json.format[Schema.ComputeFlightEmissionsRequest]
}
