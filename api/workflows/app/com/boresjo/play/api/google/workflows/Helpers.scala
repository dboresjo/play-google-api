package com.boresjo.play.api.google.workflows

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaWorkflow: Conversion[List[Schema.Workflow], Option[List[Schema.Workflow]]] = (fun: List[Schema.Workflow]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaWorkflowStateEnum: Conversion[Schema.Workflow.StateEnum, Option[Schema.Workflow.StateEnum]] = (fun: Schema.Workflow.StateEnum) => Option(fun)
		given putSchemaStateError: Conversion[Schema.StateError, Option[Schema.StateError]] = (fun: Schema.StateError) => Option(fun)
		given putSchemaWorkflowCallLogLevelEnum: Conversion[Schema.Workflow.CallLogLevelEnum, Option[Schema.Workflow.CallLogLevelEnum]] = (fun: Schema.Workflow.CallLogLevelEnum) => Option(fun)
		given putSchemaWorkflowExecutionHistoryLevelEnum: Conversion[Schema.Workflow.ExecutionHistoryLevelEnum, Option[Schema.Workflow.ExecutionHistoryLevelEnum]] = (fun: Schema.Workflow.ExecutionHistoryLevelEnum) => Option(fun)
		given putSchemaStateErrorTypeEnum: Conversion[Schema.StateError.TypeEnum, Option[Schema.StateError.TypeEnum]] = (fun: Schema.StateError.TypeEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
