package com.boresjo.play.api.google.area120tables

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaWorkspace: Conversion[List[Schema.Workspace], Option[List[Schema.Workspace]]] = (fun: List[Schema.Workspace]) => Option(fun)
		given putSchemaRow: Conversion[Schema.Row, Option[Schema.Row]] = (fun: Schema.Row) => Option(fun)
		given putSchemaCreateRowRequestViewEnum: Conversion[Schema.CreateRowRequest.ViewEnum, Option[Schema.CreateRowRequest.ViewEnum]] = (fun: Schema.CreateRowRequest.ViewEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaCreateRowRequest: Conversion[List[Schema.CreateRowRequest], Option[List[Schema.CreateRowRequest]]] = (fun: List[Schema.CreateRowRequest]) => Option(fun)
		given putListSchemaLabeledItem: Conversion[List[Schema.LabeledItem], Option[List[Schema.LabeledItem]]] = (fun: List[Schema.LabeledItem]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaDateDetails: Conversion[Schema.DateDetails, Option[Schema.DateDetails]] = (fun: Schema.DateDetails) => Option(fun)
		given putSchemaRelationshipDetails: Conversion[Schema.RelationshipDetails, Option[Schema.RelationshipDetails]] = (fun: Schema.RelationshipDetails) => Option(fun)
		given putSchemaLookupDetails: Conversion[Schema.LookupDetails, Option[Schema.LookupDetails]] = (fun: Schema.LookupDetails) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaUpdateRowRequestViewEnum: Conversion[Schema.UpdateRowRequest.ViewEnum, Option[Schema.UpdateRowRequest.ViewEnum]] = (fun: Schema.UpdateRowRequest.ViewEnum) => Option(fun)
		given putListSchemaRow: Conversion[List[Schema.Row], Option[List[Schema.Row]]] = (fun: List[Schema.Row]) => Option(fun)
		given putListSchemaSavedView: Conversion[List[Schema.SavedView], Option[List[Schema.SavedView]]] = (fun: List[Schema.SavedView]) => Option(fun)
		given putListSchemaColumnDescription: Conversion[List[Schema.ColumnDescription], Option[List[Schema.ColumnDescription]]] = (fun: List[Schema.ColumnDescription]) => Option(fun)
		given putListSchemaTable: Conversion[List[Schema.Table], Option[List[Schema.Table]]] = (fun: List[Schema.Table]) => Option(fun)
		given putListSchemaUpdateRowRequest: Conversion[List[Schema.UpdateRowRequest], Option[List[Schema.UpdateRowRequest]]] = (fun: List[Schema.UpdateRowRequest]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
