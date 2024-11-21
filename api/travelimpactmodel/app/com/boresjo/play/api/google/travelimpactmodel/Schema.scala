package com.boresjo.play.api.google.travelimpactmodel

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class EmissionsGramsPerPax(
	  /** Emissions for one passenger in first class in grams. This field is always computed and populated, regardless of whether the aircraft has first class seats or not. */
		first: Option[Int] = None,
	  /** Emissions for one passenger in premium economy class in grams. This field is always computed and populated, regardless of whether the aircraft has premium economy class seats or not. */
		premiumEconomy: Option[Int] = None,
	  /** Emissions for one passenger in economy class in grams. This field is always computed and populated, regardless of whether the aircraft has economy class seats or not. */
		economy: Option[Int] = None,
	  /** Emissions for one passenger in business class in grams. This field is always computed and populated, regardless of whether the aircraft has business class seats or not. */
		business: Option[Int] = None
	)
	
	case class Flight(
	  /** Required. Flight number, e.g. 324. */
		flightNumber: Option[Int] = None,
	  /** Required. Date of the flight in the time zone of the origin airport. Must be a date in the present or future. */
		departureDate: Option[Schema.Date] = None,
	  /** Required. IATA carrier code, e.g. "AA". */
		operatingCarrierCode: Option[String] = None,
	  /** Required. IATA airport code for flight origin, e.g. "LHR". */
		origin: Option[String] = None,
	  /** Required. IATA airport code for flight destination, e.g. "JFK". */
		destination: Option[String] = None
	)
	
	case class Date(
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class ComputeFlightEmissionsResponse(
	  /** List of flight legs with emission estimates. */
		flightEmissions: Option[List[Schema.FlightWithEmissions]] = None,
	  /** The model version under which emission estimates for all flights in this response were computed. */
		modelVersion: Option[Schema.ModelVersion] = None
	)
	
	case class FlightWithEmissions(
	  /** Optional. Per-passenger emission estimate numbers. Will not be present if emissions could not be computed. For the list of reasons why emissions could not be computed, see ComputeFlightEmissions. This field uses wtw emissions aka ttw_emissions_grams_per_pax + wtt_emissions_grams_per_pax. */
		emissionsGramsPerPax: Option[Schema.EmissionsGramsPerPax] = None,
	  /** Required. Matches the flight identifiers in the request. Note: all IATA codes are capitalized. */
		flight: Option[Schema.Flight] = None
	)
	
	case class ModelVersion(
	  /** Patch versions: Implementation changes meant to address bugs or inaccuracies in the model implementation. */
		patch: Option[Int] = None,
	  /** Major versions: Major changes to methodology (e.g. adding new data sources to the model that lead to major output changes). Such changes will be infrequent and announced well in advance. Might involve API version changes, which will respect guidelines in https://cloud.google.com/endpoints/docs/openapi/versioning-an-api#backwards-incompatible */
		major: Option[Int] = None,
	  /** Minor versions: Changes to the model that, while being consistent across schema versions, change the model parameters or implementation. */
		minor: Option[Int] = None,
	  /** Dated versions: Model datasets are recreated with refreshed input data but no change to the algorithms regularly. */
		dated: Option[String] = None
	)
	
	case class ComputeFlightEmissionsRequest(
	  /** Required. Direct flights to return emission estimates for. */
		flights: Option[List[Schema.Flight]] = None
	)
}
