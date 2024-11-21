package com.boresjo.play.api.google.rapidmigrationassessment

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaCollectorStateEnum: Conversion[Schema.Collector.StateEnum, Option[Schema.Collector.StateEnum]] = (fun: Schema.Collector.StateEnum) => Option(fun)
		given putSchemaGuestOsScan: Conversion[Schema.GuestOsScan, Option[Schema.GuestOsScan]] = (fun: Schema.GuestOsScan) => Option(fun)
		given putSchemaVSphereScan: Conversion[Schema.VSphereScan, Option[Schema.VSphereScan]] = (fun: Schema.VSphereScan) => Option(fun)
		given putSchemaAnnotationTypeEnum: Conversion[Schema.Annotation.TypeEnum, Option[Schema.Annotation.TypeEnum]] = (fun: Schema.Annotation.TypeEnum) => Option(fun)
		given putListSchemaCollector: Conversion[List[Schema.Collector], Option[List[Schema.Collector]]] = (fun: List[Schema.Collector]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
