package com.boresjo.play.api.google.workflowexecutions

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListCallbacksResponse: Format[Schema.ListCallbacksResponse] = Json.format[Schema.ListCallbacksResponse]
	given fmtCallback: Format[Schema.Callback] = Json.format[Schema.Callback]
	given fmtListStepEntriesResponse: Format[Schema.ListStepEntriesResponse] = Json.format[Schema.ListStepEntriesResponse]
	given fmtStepEntry: Format[Schema.StepEntry] = Json.format[Schema.StepEntry]
	given fmtStepEntryStepTypeEnum: Format[Schema.StepEntry.StepTypeEnum] = JsonEnumFormat[Schema.StepEntry.StepTypeEnum]
	given fmtStepEntryStateEnum: Format[Schema.StepEntry.StateEnum] = JsonEnumFormat[Schema.StepEntry.StateEnum]
	given fmtException: Format[Schema.Exception] = Json.format[Schema.Exception]
	given fmtNavigationInfo: Format[Schema.NavigationInfo] = Json.format[Schema.NavigationInfo]
	given fmtStepEntryMetadata: Format[Schema.StepEntryMetadata] = Json.format[Schema.StepEntryMetadata]
	given fmtVariableData: Format[Schema.VariableData] = Json.format[Schema.VariableData]
	given fmtStepEntryMetadataProgressTypeEnum: Format[Schema.StepEntryMetadata.ProgressTypeEnum] = JsonEnumFormat[Schema.StepEntryMetadata.ProgressTypeEnum]
	given fmtListExecutionsResponse: Format[Schema.ListExecutionsResponse] = Json.format[Schema.ListExecutionsResponse]
	given fmtExecution: Format[Schema.Execution] = Json.format[Schema.Execution]
	given fmtExecutionStateEnum: Format[Schema.Execution.StateEnum] = JsonEnumFormat[Schema.Execution.StateEnum]
	given fmtError: Format[Schema.Error] = Json.format[Schema.Error]
	given fmtExecutionCallLogLevelEnum: Format[Schema.Execution.CallLogLevelEnum] = JsonEnumFormat[Schema.Execution.CallLogLevelEnum]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtStateError: Format[Schema.StateError] = Json.format[Schema.StateError]
	given fmtExecutionExecutionHistoryLevelEnum: Format[Schema.Execution.ExecutionHistoryLevelEnum] = JsonEnumFormat[Schema.Execution.ExecutionHistoryLevelEnum]
	given fmtStackTrace: Format[Schema.StackTrace] = Json.format[Schema.StackTrace]
	given fmtStackTraceElement: Format[Schema.StackTraceElement] = Json.format[Schema.StackTraceElement]
	given fmtPosition: Format[Schema.Position] = Json.format[Schema.Position]
	given fmtStep: Format[Schema.Step] = Json.format[Schema.Step]
	given fmtStateErrorTypeEnum: Format[Schema.StateError.TypeEnum] = JsonEnumFormat[Schema.StateError.TypeEnum]
	given fmtCancelExecutionRequest: Format[Schema.CancelExecutionRequest] = Json.format[Schema.CancelExecutionRequest]
	given fmtExportDataResponse: Format[Schema.ExportDataResponse] = Json.format[Schema.ExportDataResponse]
	given fmtTriggerPubsubExecutionRequest: Format[Schema.TriggerPubsubExecutionRequest] = Json.format[Schema.TriggerPubsubExecutionRequest]
	given fmtPubsubMessage: Format[Schema.PubsubMessage] = Json.format[Schema.PubsubMessage]
	given fmtDeleteExecutionHistoryRequest: Format[Schema.DeleteExecutionHistoryRequest] = Json.format[Schema.DeleteExecutionHistoryRequest]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
}
