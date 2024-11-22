package com.boresjo.play.api.google.travelimpactmodel

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaFlightWithEmissions: Conversion[List[Schema.FlightWithEmissions], Option[List[Schema.FlightWithEmissions]]] = (fun: List[Schema.FlightWithEmissions]) => Option(fun)
		given putSchemaModelVersion: Conversion[Schema.ModelVersion, Option[Schema.ModelVersion]] = (fun: Schema.ModelVersion) => Option(fun)
		given putSchemaEmissionsGramsPerPax: Conversion[Schema.EmissionsGramsPerPax, Option[Schema.EmissionsGramsPerPax]] = (fun: Schema.EmissionsGramsPerPax) => Option(fun)
		given putSchemaFlight: Conversion[Schema.Flight, Option[Schema.Flight]] = (fun: Schema.Flight) => Option(fun)
		given putListSchemaFlight: Conversion[List[Schema.Flight], Option[List[Schema.Flight]]] = (fun: List[Schema.Flight]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
