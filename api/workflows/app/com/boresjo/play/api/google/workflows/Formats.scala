package com.boresjo.play.api.google.workflows

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtListWorkflowsResponse: Format[Schema.ListWorkflowsResponse] = Json.format[Schema.ListWorkflowsResponse]
	given fmtWorkflow: Format[Schema.Workflow] = Json.format[Schema.Workflow]
	given fmtWorkflowStateEnum: Format[Schema.Workflow.StateEnum] = JsonEnumFormat[Schema.Workflow.StateEnum]
	given fmtStateError: Format[Schema.StateError] = Json.format[Schema.StateError]
	given fmtWorkflowCallLogLevelEnum: Format[Schema.Workflow.CallLogLevelEnum] = JsonEnumFormat[Schema.Workflow.CallLogLevelEnum]
	given fmtWorkflowExecutionHistoryLevelEnum: Format[Schema.Workflow.ExecutionHistoryLevelEnum] = JsonEnumFormat[Schema.Workflow.ExecutionHistoryLevelEnum]
	given fmtStateErrorTypeEnum: Format[Schema.StateError.TypeEnum] = JsonEnumFormat[Schema.StateError.TypeEnum]
	given fmtListWorkflowRevisionsResponse: Format[Schema.ListWorkflowRevisionsResponse] = Json.format[Schema.ListWorkflowRevisionsResponse]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
}
