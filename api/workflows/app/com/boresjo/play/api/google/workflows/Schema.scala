package com.boresjo.play.api.google.workflows

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListLocationsResponse(
	  /** A list of locations that matches the specified filter in the request. */
		locations: Option[List[Schema.Location]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Location(
	  /** Resource name for the location, which may vary between implementations. For example: `"projects/example-project/locations/us-east1"` */
		name: Option[String] = None,
	  /** The canonical id for this location. For example: `"us-east1"`. */
		locationId: Option[String] = None,
	  /** The friendly name for this location, typically a nearby city name. For example, "Tokyo". */
		displayName: Option[String] = None,
	  /** Cross-service attributes for the location. For example {"cloud.googleapis.com/region": "us-east1"} */
		labels: Option[Map[String, String]] = None,
	  /** Service-specific metadata. For example the available capacity at the given location. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class ListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.Operation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Operation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class Empty(
	
	)
	
	case class ListWorkflowsResponse(
	  /** The workflows that match the request. */
		workflows: Option[List[Schema.Workflow]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Unreachable resources. */
		unreachable: Option[List[String]] = None
	)
	
	object Workflow {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, UNAVAILABLE }
		enum CallLogLevelEnum extends Enum[CallLogLevelEnum] { case CALL_LOG_LEVEL_UNSPECIFIED, LOG_ALL_CALLS, LOG_ERRORS_ONLY, LOG_NONE }
		enum ExecutionHistoryLevelEnum extends Enum[ExecutionHistoryLevelEnum] { case EXECUTION_HISTORY_LEVEL_UNSPECIFIED, EXECUTION_HISTORY_BASIC, EXECUTION_HISTORY_DETAILED }
	}
	case class Workflow(
	  /** The resource name of the workflow. Format: projects/{project}/locations/{location}/workflows/{workflow}. This is a workflow-wide field and is not tied to a specific revision. */
		name: Option[String] = None,
	  /** Description of the workflow provided by the user. Must be at most 1000 Unicode characters long. This is a workflow-wide field and is not tied to a specific revision. */
		description: Option[String] = None,
	  /** Output only. State of the workflow deployment. */
		state: Option[Schema.Workflow.StateEnum] = None,
	  /** Output only. The revision of the workflow. A new revision of a workflow is created as a result of updating the following properties of a workflow: - Service account - Workflow code to be executed The format is "000001-a4d", where the first six characters define the zero-padded revision ordinal number. They are followed by a hyphen and three hexadecimal random characters. */
		revisionId: Option[String] = None,
	  /** Output only. The timestamp for when the workflow was created. This is a workflow-wide field and is not tied to a specific revision. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp for when the workflow was last updated. This is a workflow-wide field and is not tied to a specific revision. */
		updateTime: Option[String] = None,
	  /** Output only. The timestamp for the latest revision of the workflow's creation. */
		revisionCreateTime: Option[String] = None,
	  /** Labels associated with this workflow. Labels can contain at most 64 entries. Keys and values can be no longer than 63 characters and can only contain lowercase letters, numeric characters, underscores, and dashes. Label keys must start with a letter. International characters are allowed. This is a workflow-wide field and is not tied to a specific revision. */
		labels: Option[Map[String, String]] = None,
	  /** The service account associated with the latest workflow version. This service account represents the identity of the workflow and determines what permissions the workflow has. Format: projects/{project}/serviceAccounts/{account} or {account} Using `-` as a wildcard for the `{project}` or not providing one at all will infer the project from the account. The `{account}` value can be the `email` address or the `unique_id` of the service account. If not provided, workflow will use the project's default service account. Modifying this field for an existing workflow results in a new workflow revision. */
		serviceAccount: Option[String] = None,
	  /** Workflow code to be executed. The size limit is 128KB. */
		sourceContents: Option[String] = None,
	  /** Optional. The resource name of a KMS crypto key used to encrypt or decrypt the data associated with the workflow. Format: projects/{project}/locations/{location}/keyRings/{keyRing}/cryptoKeys/{cryptoKey} Using `-` as a wildcard for the `{project}` or not providing one at all will infer the project from the account. If not provided, data associated with the workflow will not be CMEK-encrypted. */
		cryptoKeyName: Option[String] = None,
	  /** Output only. Error regarding the state of the workflow. For example, this field will have error details if the execution data is unavailable due to revoked KMS key permissions. */
		stateError: Option[Schema.StateError] = None,
	  /** Optional. Describes the level of platform logging to apply to calls and call responses during executions of this workflow. If both the workflow and the execution specify a logging level, the execution level takes precedence. */
		callLogLevel: Option[Schema.Workflow.CallLogLevelEnum] = None,
	  /** Optional. User-defined environment variables associated with this workflow revision. This map has a maximum length of 20. Each string can take up to 4KiB. Keys cannot be empty strings and cannot start with "GOOGLE" or "WORKFLOWS". */
		userEnvVars: Option[Map[String, String]] = None,
	  /** Optional. Describes the level of the execution history feature to apply to this workflow. */
		executionHistoryLevel: Option[Schema.Workflow.ExecutionHistoryLevelEnum] = None,
	  /** Output only. A list of all KMS crypto keys used to encrypt or decrypt the data associated with the workflow. */
		allKmsKeys: Option[List[String]] = None,
	  /** Output only. A list of all KMS crypto key versions used to encrypt or decrypt the data associated with the workflow. */
		allKmsKeysVersions: Option[List[String]] = None,
	  /** Output only. The resource name of a KMS crypto key version used to encrypt or decrypt the data associated with the workflow. Format: projects/{project}/locations/{location}/keyRings/{keyRing}/cryptoKeys/{cryptoKey}/cryptoKeyVersions/{cryptoKeyVersion} */
		cryptoKeyVersion: Option[String] = None
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
	
	case class ListWorkflowRevisionsResponse(
	  /** The revisions of the workflow, ordered in reverse chronological order. */
		workflows: Option[List[Schema.Workflow]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class OperationMetadata(
	  /** The time the operation was created. */
		createTime: Option[String] = None,
	  /** The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** API version used to start the operation. */
		apiVersion: Option[String] = None
	)
}
