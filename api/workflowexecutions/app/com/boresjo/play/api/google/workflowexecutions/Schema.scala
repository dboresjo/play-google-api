package com.boresjo.play.api.google.workflowexecutions

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListCallbacksResponse(
	  /** The callbacks which match the request. */
		callbacks: Option[List[Schema.Callback]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class Callback(
	  /** Output only. The resource name of the callback. Format: projects/{project}/locations/{location}/workflows/{workflow}/executions/{execution}/callback/{callback} */
		name: Option[String] = None,
	  /** Output only. The method accepted by the callback. For example: GET, POST, PUT. */
		method: Option[String] = None,
	  /** Output only. The payloads received by the callback that have not been processed by a waiting execution step. */
		availablePayloads: Option[List[String]] = None,
	  /** Output only. Number of execution steps waiting on this callback. */
		waiters: Option[String] = None
	)
	
	case class ListStepEntriesResponse(
	  /** The list of entries. */
		stepEntries: Option[List[Schema.StepEntry]] = None,
	  /** A token to retrieve next page of results. Pass this value in the ListStepEntriesRequest.page_token field in the subsequent call to `ListStepEntries` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None,
	  /** Indicates the total number of StepEntries that matched the request filter. For running executions, this number shows the number of StepEntries that are executed thus far. */
		totalSize: Option[Int] = None
	)
	
	object StepEntry {
		enum StepTypeEnum extends Enum[StepTypeEnum] { case STEP_TYPE_UNSPECIFIED, STEP_ASSIGN, STEP_STD_LIB_CALL, STEP_CONNECTOR_CALL, STEP_SUBWORKFLOW_CALL, STEP_CALL, STEP_SWITCH, STEP_CONDITION, STEP_FOR, STEP_FOR_ITERATION, STEP_PARALLEL_FOR, STEP_PARALLEL_BRANCH, STEP_PARALLEL_BRANCH_ENTRY, STEP_TRY_RETRY_EXCEPT, STEP_TRY, STEP_RETRY, STEP_EXCEPT, STEP_RETURN, STEP_RAISE, STEP_GOTO }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STATE_IN_PROGRESS, STATE_SUCCEEDED, STATE_FAILED, STATE_CANCELLED }
	}
	case class StepEntry(
	  /** Output only. The full resource name of the step entry. Each step entry has a unique entry ID, which is a monotonically increasing counter. Step entry names have the format: `projects/{project}/locations/{location}/workflows/{workflow}/executions/{execution}/stepEntries/{step_entry}`. */
		name: Option[String] = None,
	  /** Output only. The creation time of the step entry. */
		createTime: Option[String] = None,
	  /** Output only. The most recently updated time of the step entry. */
		updateTime: Option[String] = None,
	  /** Output only. The name of the routine this step entry belongs to. A routine name is the subworkflow name defined in the YAML source code. The top level routine name is `main`. */
		routine: Option[String] = None,
	  /** Output only. The name of the step this step entry belongs to. */
		step: Option[String] = None,
	  /** Output only. The type of the step this step entry belongs to. */
		stepType: Option[Schema.StepEntry.StepTypeEnum] = None,
	  /** Output only. The state of the step entry. */
		state: Option[Schema.StepEntry.StateEnum] = None,
	  /** Output only. The exception thrown by the step entry. */
		exception: Option[Schema.Exception] = None,
	  /** Output only. The numeric ID of this step entry, used for navigation. */
		entryId: Option[String] = None,
	  /** Output only. The NavigationInfo associated to this step. */
		navigationInfo: Option[Schema.NavigationInfo] = None,
	  /** Output only. The StepEntryMetadata associated to this step. */
		stepEntryMetadata: Option[Schema.StepEntryMetadata] = None,
	  /** Output only. The VariableData associated to this step. */
		variableData: Option[Schema.VariableData] = None
	)
	
	case class Exception(
	  /** Error message represented as a JSON string. */
		payload: Option[String] = None
	)
	
	case class NavigationInfo(
	  /** Step entries that can be reached by "stepping into" e.g. a subworkflow call. */
		children: Option[List[String]] = None,
	  /** The step entry, if any, that can be reached by "stepping out" of the current workflow being executed. */
		parent: Option[String] = None,
	  /** The index of the next step in the current workflow, if any. */
		next: Option[String] = None,
	  /** The index of the previous step in the current workflow, if any. */
		previous: Option[String] = None
	)
	
	object StepEntryMetadata {
		enum ProgressTypeEnum extends Enum[ProgressTypeEnum] { case PROGRESS_TYPE_UNSPECIFIED, PROGRESS_TYPE_FOR, PROGRESS_TYPE_SWITCH, PROGRESS_TYPE_RETRY, PROGRESS_TYPE_PARALLEL_FOR, PROGRESS_TYPE_PARALLEL_BRANCH }
	}
	case class StepEntryMetadata(
	  /** Child thread id that this step entry belongs to. */
		threadId: Option[String] = None,
	  /** Progress type of this step entry. */
		progressType: Option[Schema.StepEntryMetadata.ProgressTypeEnum] = None,
	  /** Progress number represents the current state of the current progress. eg: A step entry represents the 4th iteration in a progress of PROGRESS_TYPE_FOR. Note: This field is only populated when an iteration exists and the starting value is 1. */
		progressNumber: Option[String] = None,
	  /** Expected iteration represents the expected number of iterations in the step's progress. */
		expectedIteration: Option[String] = None
	)
	
	case class VariableData(
	  /** Variables that are associated with this step. */
		variables: Option[Map[String, JsValue]] = None
	)
	
	case class ListExecutionsResponse(
	  /** The executions which match the request. */
		executions: Option[List[Schema.Execution]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object Execution {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, SUCCEEDED, FAILED, CANCELLED, UNAVAILABLE, QUEUED }
		enum CallLogLevelEnum extends Enum[CallLogLevelEnum] { case CALL_LOG_LEVEL_UNSPECIFIED, LOG_ALL_CALLS, LOG_ERRORS_ONLY, LOG_NONE }
		enum ExecutionHistoryLevelEnum extends Enum[ExecutionHistoryLevelEnum] { case EXECUTION_HISTORY_LEVEL_UNSPECIFIED, EXECUTION_HISTORY_BASIC, EXECUTION_HISTORY_DETAILED }
	}
	case class Execution(
	  /** Output only. The resource name of the execution. Format: projects/{project}/locations/{location}/workflows/{workflow}/executions/{execution} */
		name: Option[String] = None,
	  /** Output only. Marks the creation of the execution. */
		createTime: Option[String] = None,
	  /** Output only. Marks the beginning of execution. Note that this will be the same as `createTime` for executions that start immediately. */
		startTime: Option[String] = None,
	  /** Output only. Marks the end of execution, successful or not. */
		endTime: Option[String] = None,
	  /** Output only. Measures the duration of the execution. */
		duration: Option[String] = None,
	  /** Output only. Current state of the execution. */
		state: Option[Schema.Execution.StateEnum] = None,
	  /** Input parameters of the execution represented as a JSON string. The size limit is 32KB. &#42;Note&#42;: If you are using the REST API directly to run your workflow, you must escape any JSON string value of `argument`. Example: `'{"argument":"{\"firstName\":\"FIRST\",\"lastName\":\"LAST\"}"}'` */
		argument: Option[String] = None,
	  /** Output only. Output of the execution represented as a JSON string. The value can only be present if the execution's state is `SUCCEEDED`. */
		result: Option[String] = None,
	  /** Output only. The error which caused the execution to finish prematurely. The value is only present if the execution's state is `FAILED` or `CANCELLED`. */
		error: Option[Schema.Error] = None,
	  /** Output only. Revision of the workflow this execution is using. */
		workflowRevisionId: Option[String] = None,
	  /** The call logging level associated to this execution. */
		callLogLevel: Option[Schema.Execution.CallLogLevelEnum] = None,
	  /** Output only. Status tracks the current steps and progress data of this execution. */
		status: Option[Schema.Status] = None,
	  /** Labels associated with this execution. Labels can contain at most 64 entries. Keys and values can be no longer than 63 characters and can only contain lowercase letters, numeric characters, underscores, and dashes. Label keys must start with a letter. International characters are allowed. By default, labels are inherited from the workflow but are overridden by any labels associated with the execution. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Error regarding the state of the Execution resource. For example, this field will have error details if the execution data is unavailable due to revoked KMS key permissions. */
		stateError: Option[Schema.StateError] = None,
	  /** Optional. If set to true, the execution will not be backlogged when the concurrency quota is exhausted. The backlog execution starts when the concurrency quota becomes available. */
		disableConcurrencyQuotaOverflowBuffering: Option[Boolean] = None,
	  /** Optional. Describes the level of the execution history feature to apply to this execution. If not specified, the level of the execution history feature will be determined by its workflow's execution history level. If the value is different from its workflow's value, it will override the workflow's execution history level for this exeuction. */
		executionHistoryLevel: Option[Schema.Execution.ExecutionHistoryLevelEnum] = None
	)
	
	case class Error(
	  /** Error message and data returned represented as a JSON string. */
		payload: Option[String] = None,
	  /** Human-readable stack trace string. */
		context: Option[String] = None,
	  /** Stack trace with detailed information of where error was generated. */
		stackTrace: Option[Schema.StackTrace] = None
	)
	
	case class StackTrace(
	  /** An array of stack elements. */
		elements: Option[List[Schema.StackTraceElement]] = None
	)
	
	case class StackTraceElement(
	  /** The step the error occurred at. */
		step: Option[String] = None,
	  /** The routine where the error occurred. */
		routine: Option[String] = None,
	  /** The source position information of the stack trace element. */
		position: Option[Schema.Position] = None
	)
	
	case class Position(
	  /** The source code line number the current instruction was generated from. */
		line: Option[String] = None,
	  /** The source code column position (of the line) the current instruction was generated from. */
		column: Option[String] = None,
	  /** The number of bytes of source code making up this stack trace element. */
		length: Option[String] = None
	)
	
	case class Status(
	  /** A list of currently executing or last executed step names for the workflow execution currently running. If the workflow has succeeded or failed, this is the last attempted or executed step. Presently, if the current step is inside a subworkflow, the list only includes that step. In the future, the list will contain items for each step in the call stack, starting with the outermost step in the `main` subworkflow, and ending with the most deeply nested step. */
		currentSteps: Option[List[Schema.Step]] = None
	)
	
	case class Step(
	  /** Name of a routine within the workflow. */
		routine: Option[String] = None,
	  /** Name of a step within the routine. */
		step: Option[String] = None
	)
	
	object StateError {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, KMS_ERROR }
	}
	case class StateError(
	  /** Provides specifics about the error. */
		details: Option[String] = None,
	  /** The type of this state error. */
		`type`: Option[Schema.StateError.TypeEnum] = None
	)
	
	case class CancelExecutionRequest(
	
	)
	
	case class ExportDataResponse(
	  /** The JSON string with customer data and metadata for an execution with the given name */
		data: Option[String] = None
	)
	
	case class TriggerPubsubExecutionRequest(
	  /** Required. The subscription of the Pub/Sub push notification. Format: projects/{project}/subscriptions/{sub} */
		subscription: Option[String] = None,
	  /** Required. The message of the Pub/Sub push notification. */
		message: Option[Schema.PubsubMessage] = None,
	  /** Required. LINT: LEGACY_NAMES The query parameter value for __GCP_CloudEventsMode, set by the Eventarc service when configuring triggers. */
		GCPCloudEventsMode: Option[String] = None,
	  /** The number of attempts that have been made to deliver this message. This is set by Pub/Sub for subscriptions that have the "dead letter" feature enabled, and hence provided here for compatibility, but is ignored by Workflows. */
		deliveryAttempt: Option[Int] = None
	)
	
	case class PubsubMessage(
	  /** Optional. The message data field. If this field is empty, the message must contain at least one attribute. */
		data: Option[String] = None,
	  /** Optional. Attributes for this message. If this field is empty, the message must contain non-empty data. This can be used to filter messages on the subscription. */
		attributes: Option[Map[String, String]] = None,
	  /** ID of this message, assigned by the server when the message is published. Guaranteed to be unique within the topic. This value may be read by a subscriber that receives a `PubsubMessage` via a `Pull` call or a push delivery. It must not be populated by the publisher in a `Publish` call. */
		messageId: Option[String] = None,
	  /** The time at which the message was published, populated by the server when it receives the `Publish` call. It must not be populated by the publisher in a `Publish` call. */
		publishTime: Option[String] = None,
	  /** Optional. If non-empty, identifies related messages for which publish order should be respected. If a `Subscription` has `enable_message_ordering` set to `true`, messages published with the same non-empty `ordering_key` value will be delivered to subscribers in the order in which they are received by the Pub/Sub system. All `PubsubMessage`s published in a given `PublishRequest` must specify the same `ordering_key` value. For more information, see [ordering messages](https://cloud.google.com/pubsub/docs/ordering). */
		orderingKey: Option[String] = None
	)
	
	case class DeleteExecutionHistoryRequest(
	
	)
	
	case class Empty(
	
	)
}
