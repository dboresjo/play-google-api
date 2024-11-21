package com.boresjo.play.api.google.workflowexecutions

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaCallback: Conversion[List[Schema.Callback], Option[List[Schema.Callback]]] = (fun: List[Schema.Callback]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaStepEntry: Conversion[List[Schema.StepEntry], Option[List[Schema.StepEntry]]] = (fun: List[Schema.StepEntry]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaStepEntryStepTypeEnum: Conversion[Schema.StepEntry.StepTypeEnum, Option[Schema.StepEntry.StepTypeEnum]] = (fun: Schema.StepEntry.StepTypeEnum) => Option(fun)
		given putSchemaStepEntryStateEnum: Conversion[Schema.StepEntry.StateEnum, Option[Schema.StepEntry.StateEnum]] = (fun: Schema.StepEntry.StateEnum) => Option(fun)
		given putSchemaException: Conversion[Schema.Exception, Option[Schema.Exception]] = (fun: Schema.Exception) => Option(fun)
		given putSchemaNavigationInfo: Conversion[Schema.NavigationInfo, Option[Schema.NavigationInfo]] = (fun: Schema.NavigationInfo) => Option(fun)
		given putSchemaStepEntryMetadata: Conversion[Schema.StepEntryMetadata, Option[Schema.StepEntryMetadata]] = (fun: Schema.StepEntryMetadata) => Option(fun)
		given putSchemaVariableData: Conversion[Schema.VariableData, Option[Schema.VariableData]] = (fun: Schema.VariableData) => Option(fun)
		given putSchemaStepEntryMetadataProgressTypeEnum: Conversion[Schema.StepEntryMetadata.ProgressTypeEnum, Option[Schema.StepEntryMetadata.ProgressTypeEnum]] = (fun: Schema.StepEntryMetadata.ProgressTypeEnum) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putListSchemaExecution: Conversion[List[Schema.Execution], Option[List[Schema.Execution]]] = (fun: List[Schema.Execution]) => Option(fun)
		given putSchemaExecutionStateEnum: Conversion[Schema.Execution.StateEnum, Option[Schema.Execution.StateEnum]] = (fun: Schema.Execution.StateEnum) => Option(fun)
		given putSchemaError: Conversion[Schema.Error, Option[Schema.Error]] = (fun: Schema.Error) => Option(fun)
		given putSchemaExecutionCallLogLevelEnum: Conversion[Schema.Execution.CallLogLevelEnum, Option[Schema.Execution.CallLogLevelEnum]] = (fun: Schema.Execution.CallLogLevelEnum) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaStateError: Conversion[Schema.StateError, Option[Schema.StateError]] = (fun: Schema.StateError) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaExecutionExecutionHistoryLevelEnum: Conversion[Schema.Execution.ExecutionHistoryLevelEnum, Option[Schema.Execution.ExecutionHistoryLevelEnum]] = (fun: Schema.Execution.ExecutionHistoryLevelEnum) => Option(fun)
		given putSchemaStackTrace: Conversion[Schema.StackTrace, Option[Schema.StackTrace]] = (fun: Schema.StackTrace) => Option(fun)
		given putListSchemaStackTraceElement: Conversion[List[Schema.StackTraceElement], Option[List[Schema.StackTraceElement]]] = (fun: List[Schema.StackTraceElement]) => Option(fun)
		given putSchemaPosition: Conversion[Schema.Position, Option[Schema.Position]] = (fun: Schema.Position) => Option(fun)
		given putListSchemaStep: Conversion[List[Schema.Step], Option[List[Schema.Step]]] = (fun: List[Schema.Step]) => Option(fun)
		given putSchemaStateErrorTypeEnum: Conversion[Schema.StateError.TypeEnum, Option[Schema.StateError.TypeEnum]] = (fun: Schema.StateError.TypeEnum) => Option(fun)
		given putSchemaPubsubMessage: Conversion[Schema.PubsubMessage, Option[Schema.PubsubMessage]] = (fun: Schema.PubsubMessage) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
