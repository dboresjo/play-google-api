package com.boresjo.play.api.google.admin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaActivity: Conversion[List[Schema.Activity], Option[List[Schema.Activity]]] = (fun: List[Schema.Activity]) => Option(fun)
		given putListSchemaActivityEventsItem: Conversion[List[Schema.Activity.EventsItem], Option[List[Schema.Activity.EventsItem]]] = (fun: List[Schema.Activity.EventsItem]) => Option(fun)
		given putSchemaActivityIdItem: Conversion[Schema.Activity.IdItem, Option[Schema.Activity.IdItem]] = (fun: Schema.Activity.IdItem) => Option(fun)
		given putSchemaActivityActorItem: Conversion[Schema.Activity.ActorItem, Option[Schema.Activity.ActorItem]] = (fun: Schema.Activity.ActorItem) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListBoolean: Conversion[List[Boolean], Option[List[Boolean]]] = (fun: List[Boolean]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putListSchemaUsageReportsWarningsItem: Conversion[List[Schema.UsageReports.WarningsItem], Option[List[Schema.UsageReports.WarningsItem]]] = (fun: List[Schema.UsageReports.WarningsItem]) => Option(fun)
		given putListSchemaUsageReport: Conversion[List[Schema.UsageReport], Option[List[Schema.UsageReport]]] = (fun: List[Schema.UsageReport]) => Option(fun)
		given putListSchemaUsageReportParametersItem: Conversion[List[Schema.UsageReport.ParametersItem], Option[List[Schema.UsageReport.ParametersItem]]] = (fun: List[Schema.UsageReport.ParametersItem]) => Option(fun)
		given putSchemaUsageReportEntityItem: Conversion[Schema.UsageReport.EntityItem, Option[Schema.UsageReport.EntityItem]] = (fun: Schema.UsageReport.EntityItem) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
