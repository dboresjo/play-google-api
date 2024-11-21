package com.boresjo.play.api.google.config

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
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
	
	case class CancelOperationRequest(
	
	)
	
	case class ListDeploymentsResponse(
	  /** List of Deployments. */
		deployments: Option[List[Schema.Deployment]] = None,
	  /** Token to be supplied to the next ListDeployments request via `page_token` to obtain the next set of results. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Deployment {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, UPDATING, DELETING, FAILED, SUSPENDED, DELETED }
		enum ErrorCodeEnum extends Enum[ErrorCodeEnum] { case ERROR_CODE_UNSPECIFIED, REVISION_FAILED, CLOUD_BUILD_PERMISSION_DENIED, DELETE_BUILD_API_FAILED, DELETE_BUILD_RUN_FAILED, BUCKET_CREATION_PERMISSION_DENIED, BUCKET_CREATION_FAILED }
		enum LockStateEnum extends Enum[LockStateEnum] { case LOCK_STATE_UNSPECIFIED, LOCKED, UNLOCKED, LOCKING, UNLOCKING, LOCK_FAILED, UNLOCK_FAILED }
		enum QuotaValidationEnum extends Enum[QuotaValidationEnum] { case QUOTA_VALIDATION_UNSPECIFIED, ENABLED, ENFORCED }
	}
	case class Deployment(
	  /** A blueprint described using Terraform's HashiCorp Configuration Language as a root module. */
		terraformBlueprint: Option[Schema.TerraformBlueprint] = None,
	  /** Resource name of the deployment. Format: `projects/{project}/locations/{location}/deployments/{deployment}` */
		name: Option[String] = None,
	  /** Output only. Time when the deployment was created. */
		createTime: Option[String] = None,
	  /** Output only. Time when the deployment was last modified. */
		updateTime: Option[String] = None,
	  /** User-defined metadata for the deployment. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Current state of the deployment. */
		state: Option[Schema.Deployment.StateEnum] = None,
	  /** Output only. Revision name that was most recently applied. Format: `projects/{project}/locations/{location}/deployments/{deployment}/ revisions/{revision}` */
		latestRevision: Option[String] = None,
	  /** Output only. Additional information regarding the current state. */
		stateDetail: Option[String] = None,
	  /** Output only. Error code describing errors that may have occurred. */
		errorCode: Option[Schema.Deployment.ErrorCodeEnum] = None,
	  /** Output only. Location of artifacts from a DeleteDeployment operation. */
		deleteResults: Option[Schema.ApplyResults] = None,
	  /** Output only. Cloud Build instance UUID associated with deleting this deployment. */
		deleteBuild: Option[String] = None,
	  /** Output only. Location of Cloud Build logs in Google Cloud Storage, populated when deleting this deployment. Format: `gs://{bucket}/{object}`. */
		deleteLogs: Option[String] = None,
	  /** Output only. Errors encountered when deleting this deployment. Errors are truncated to 10 entries, see `delete_results` and `error_logs` for full details. */
		tfErrors: Option[List[Schema.TerraformError]] = None,
	  /** Output only. Location of Terraform error logs in Google Cloud Storage. Format: `gs://{bucket}/{object}`. */
		errorLogs: Option[String] = None,
	  /** Optional. User-defined location of Cloud Build logs and artifacts in Google Cloud Storage. Format: `gs://{bucket}/{folder}` A default bucket will be bootstrapped if the field is not set or empty. Default bucket format: `gs://--blueprint-config` Constraints: - The bucket needs to be in the same project as the deployment - The path cannot be within the path of `gcs_source` - The field cannot be updated, including changing its presence */
		artifactsGcsBucket: Option[String] = None,
	  /** Required. User-specified Service Account (SA) credentials to be used when actuating resources. Format: `projects/{projectID}/serviceAccounts/{serviceAccount}` */
		serviceAccount: Option[String] = None,
	  /** By default, Infra Manager will return a failure when Terraform encounters a 409 code (resource conflict error) during actuation. If this flag is set to true, Infra Manager will instead attempt to automatically import the resource into the Terraform state (for supported resource types) and continue actuation. Not all resource types are supported, refer to documentation. */
		importExistingResources: Option[Boolean] = None,
	  /** Optional. The user-specified Cloud Build worker pool resource in which the Cloud Build job will execute. Format: `projects/{project}/locations/{location}/workerPools/{workerPoolId}`. If this field is unspecified, the default Cloud Build worker pool will be used. */
		workerPool: Option[String] = None,
	  /** Output only. Current lock state of the deployment. */
		lockState: Option[Schema.Deployment.LockStateEnum] = None,
	  /** Optional. The user-specified Terraform version constraint. Example: "=1.3.10". */
		tfVersionConstraint: Option[String] = None,
	  /** Output only. The current Terraform version set on the deployment. It is in the format of "Major.Minor.Patch", for example, "1.3.10". */
		tfVersion: Option[String] = None,
	  /** Optional. Input to control quota checks for resources in terraform configuration files. There are limited resources on which quota validation applies. */
		quotaValidation: Option[Schema.Deployment.QuotaValidationEnum] = None,
	  /** Optional. Arbitrary key-value metadata storage e.g. to help client tools identify deployments during automation. See https://google.aip.dev/148#annotations for details on format and size limitations. */
		annotations: Option[Map[String, String]] = None
	)
	
	case class TerraformBlueprint(
	  /** URI of an object in Google Cloud Storage. Format: `gs://{bucket}/{object}` URI may also specify an object version for zipped objects. Format: `gs://{bucket}/{object}#{version}` */
		gcsSource: Option[String] = None,
	  /** URI of a public Git repo. */
		gitSource: Option[Schema.GitSource] = None,
	  /** Input variable values for the Terraform blueprint. */
		inputValues: Option[Map[String, Schema.TerraformVariable]] = None
	)
	
	case class GitSource(
	  /** Optional. Repository URL. Example: 'https://github.com/kubernetes/examples.git' */
		repo: Option[String] = None,
	  /** Optional. Subdirectory inside the repository. Example: 'staging/my-package' */
		directory: Option[String] = None,
	  /** Optional. Git reference (e.g. branch or tag). */
		ref: Option[String] = None
	)
	
	case class TerraformVariable(
	  /** Input variable value. */
		inputValue: Option[JsValue] = None
	)
	
	case class ApplyResults(
	  /** Location of a blueprint copy and other manifests in Google Cloud Storage. Format: `gs://{bucket}/{object}` */
		content: Option[String] = None,
	  /** Location of artifacts (e.g. logs) in Google Cloud Storage. Format: `gs://{bucket}/{object}` */
		artifacts: Option[String] = None,
	  /** Map of output name to output info. */
		outputs: Option[Map[String, Schema.TerraformOutput]] = None
	)
	
	case class TerraformOutput(
	  /** Identifies whether Terraform has set this output as a potential sensitive value. */
		sensitive: Option[Boolean] = None,
	  /** Value of output. */
		value: Option[JsValue] = None
	)
	
	case class TerraformError(
	  /** Address of the resource associated with the error, e.g. `google_compute_network.vpc_network`. */
		resourceAddress: Option[String] = None,
	  /** HTTP response code returned from Google Cloud Platform APIs when Terraform fails to provision the resource. If unset or 0, no HTTP response code was returned by Terraform. */
		httpResponseCode: Option[Int] = None,
	  /** A human-readable error description. */
		errorDescription: Option[String] = None,
	  /** Original error response from underlying Google API, if available. */
		error: Option[Schema.Status] = None
	)
	
	case class ListRevisionsResponse(
	  /** List of Revisions. */
		revisions: Option[List[Schema.Revision]] = None,
	  /** A token to request the next page of resources from the 'ListRevisions' method. The value of an empty string means that there are no more resources to return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Revision {
		enum ActionEnum extends Enum[ActionEnum] { case ACTION_UNSPECIFIED, CREATE, UPDATE, DELETE }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, APPLYING, APPLIED, FAILED }
		enum ErrorCodeEnum extends Enum[ErrorCodeEnum] { case ERROR_CODE_UNSPECIFIED, CLOUD_BUILD_PERMISSION_DENIED, APPLY_BUILD_API_FAILED, APPLY_BUILD_RUN_FAILED, QUOTA_VALIDATION_FAILED }
		enum QuotaValidationEnum extends Enum[QuotaValidationEnum] { case QUOTA_VALIDATION_UNSPECIFIED, ENABLED, ENFORCED }
	}
	case class Revision(
	  /** Output only. A blueprint described using Terraform's HashiCorp Configuration Language as a root module. */
		terraformBlueprint: Option[Schema.TerraformBlueprint] = None,
	  /** Revision name. Format: `projects/{project}/locations/{location}/deployments/{deployment}/ revisions/{revision}` */
		name: Option[String] = None,
	  /** Output only. Time when the revision was created. */
		createTime: Option[String] = None,
	  /** Output only. Time when the revision was last modified. */
		updateTime: Option[String] = None,
	  /** Output only. The action which created this revision */
		action: Option[Schema.Revision.ActionEnum] = None,
	  /** Output only. Current state of the revision. */
		state: Option[Schema.Revision.StateEnum] = None,
	  /** Output only. Outputs and artifacts from applying a deployment. */
		applyResults: Option[Schema.ApplyResults] = None,
	  /** Output only. Additional info regarding the current state. */
		stateDetail: Option[String] = None,
	  /** Output only. Code describing any errors that may have occurred. */
		errorCode: Option[Schema.Revision.ErrorCodeEnum] = None,
	  /** Output only. Cloud Build instance UUID associated with this revision. */
		build: Option[String] = None,
	  /** Output only. Location of Revision operation logs in `gs://{bucket}/{object}` format. */
		logs: Option[String] = None,
	  /** Output only. Errors encountered when creating or updating this deployment. Errors are truncated to 10 entries, see `delete_results` and `error_logs` for full details. */
		tfErrors: Option[List[Schema.TerraformError]] = None,
	  /** Output only. Location of Terraform error logs in Google Cloud Storage. Format: `gs://{bucket}/{object}`. */
		errorLogs: Option[String] = None,
	  /** Output only. User-specified Service Account (SA) to be used as credential to manage resources. Format: `projects/{projectID}/serviceAccounts/{serviceAccount}` */
		serviceAccount: Option[String] = None,
	  /** Output only. By default, Infra Manager will return a failure when Terraform encounters a 409 code (resource conflict error) during actuation. If this flag is set to true, Infra Manager will instead attempt to automatically import the resource into the Terraform state (for supported resource types) and continue actuation. Not all resource types are supported, refer to documentation. */
		importExistingResources: Option[Boolean] = None,
	  /** Output only. The user-specified Cloud Build worker pool resource in which the Cloud Build job will execute. Format: `projects/{project}/locations/{location}/workerPools/{workerPoolId}`. If this field is unspecified, the default Cloud Build worker pool will be used. */
		workerPool: Option[String] = None,
	  /** Output only. The user-specified Terraform version constraint. Example: "=1.3.10". */
		tfVersionConstraint: Option[String] = None,
	  /** Output only. The version of Terraform used to create the Revision. It is in the format of "Major.Minor.Patch", for example, "1.3.10". */
		tfVersion: Option[String] = None,
	  /** Output only. Cloud Storage path containing quota validation results. This field is set when a user sets Deployment.quota_validation field to ENABLED or ENFORCED. Format: `gs://{bucket}/{object}`. */
		quotaValidationResults: Option[String] = None,
	  /** Optional. Input to control quota checks for resources in terraform configuration files. There are limited resources on which quota validation applies. */
		quotaValidation: Option[Schema.Revision.QuotaValidationEnum] = None
	)
	
	object Resource {
		enum IntentEnum extends Enum[IntentEnum] { case INTENT_UNSPECIFIED, CREATE, UPDATE, DELETE, RECREATE, UNCHANGED }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PLANNED, IN_PROGRESS, RECONCILED, FAILED }
	}
	case class Resource(
	  /** Output only. Resource name. Format: `projects/{project}/locations/{location}/deployments/{deployment}/revisions/{revision}/resources/{resource}` */
		name: Option[String] = None,
	  /** Output only. Terraform-specific info if this resource was created using Terraform. */
		terraformInfo: Option[Schema.ResourceTerraformInfo] = None,
	  /** Output only. Map of Cloud Asset Inventory (CAI) type to CAI info (e.g. CAI ID). CAI type format follows https://cloud.google.com/asset-inventory/docs/supported-asset-types */
		caiAssets: Option[Map[String, Schema.ResourceCAIInfo]] = None,
	  /** Output only. Intent of the resource. */
		intent: Option[Schema.Resource.IntentEnum] = None,
	  /** Output only. Current state of the resource. */
		state: Option[Schema.Resource.StateEnum] = None
	)
	
	case class ResourceTerraformInfo(
	  /** TF resource address that uniquely identifies this resource within this deployment. */
		address: Option[String] = None,
	  /** TF resource type */
		`type`: Option[String] = None,
	  /** ID attribute of the TF resource */
		id: Option[String] = None
	)
	
	case class ResourceCAIInfo(
	  /** CAI resource name in the format following https://cloud.google.com/apis/design/resource_names#full_resource_name */
		fullResourceName: Option[String] = None
	)
	
	case class ListResourcesResponse(
	  /** List of Resourcess. */
		resources: Option[List[Schema.Resource]] = None,
	  /** A token to request the next page of resources from the 'ListResources' method. The value of an empty string means that there are no more resources to return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ExportDeploymentStatefileRequest(
	  /** Optional. If this flag is set to true, the exported deployment state file will be the draft state. This will enable the draft file to be validated before copying it over to the working state on unlock. */
		draft: Option[Boolean] = None
	)
	
	case class Statefile(
	  /** Output only. Cloud Storage signed URI used for downloading or uploading the state file. */
		signedUri: Option[String] = None
	)
	
	case class ExportRevisionStatefileRequest(
	
	)
	
	case class ImportStatefileRequest(
	  /** Required. Lock ID of the lock file to verify that the user who is importing the state file previously locked the Deployment. */
		lockId: Option[String] = None
	)
	
	case class DeleteStatefileRequest(
	  /** Required. Lock ID of the lock file to verify that the user who is deleting the state file previously locked the Deployment. */
		lockId: Option[String] = None
	)
	
	case class LockDeploymentRequest(
	
	)
	
	case class UnlockDeploymentRequest(
	  /** Required. Lock ID of the lock file to be unlocked. */
		lockId: Option[String] = None
	)
	
	case class LockInfo(
	  /** Unique ID for the lock to be overridden with generation ID in the backend. */
		lockId: Option[String] = None,
	  /** Terraform operation, provided by the caller. */
		operation: Option[String] = None,
	  /** Extra information to store with the lock, provided by the caller. */
		info: Option[String] = None,
	  /** user@hostname when available */
		who: Option[String] = None,
	  /** Terraform version */
		version: Option[String] = None,
	  /** Time that the lock was taken. */
		createTime: Option[String] = None
	)
	
	object Preview {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, SUCCEEDED, APPLYING, STALE, DELETING, FAILED, DELETED }
		enum PreviewModeEnum extends Enum[PreviewModeEnum] { case PREVIEW_MODE_UNSPECIFIED, DEFAULT, DELETE }
		enum ErrorCodeEnum extends Enum[ErrorCodeEnum] { case ERROR_CODE_UNSPECIFIED, CLOUD_BUILD_PERMISSION_DENIED, BUCKET_CREATION_PERMISSION_DENIED, BUCKET_CREATION_FAILED, DEPLOYMENT_LOCK_ACQUIRE_FAILED, PREVIEW_BUILD_API_FAILED, PREVIEW_BUILD_RUN_FAILED }
	}
	case class Preview(
	  /** The terraform blueprint to preview. */
		terraformBlueprint: Option[Schema.TerraformBlueprint] = None,
	  /** Identifier. Resource name of the preview. Resource name can be user provided or server generated ID if unspecified. Format: `projects/{project}/locations/{location}/previews/{preview}` */
		name: Option[String] = None,
	  /** Output only. Time the preview was created. */
		createTime: Option[String] = None,
	  /** Optional. User-defined labels for the preview. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Current state of the preview. */
		state: Option[Schema.Preview.StateEnum] = None,
	  /** Optional. Optional deployment reference. If specified, the preview will be performed using the provided deployment's current state and use any relevant fields from the deployment unless explicitly specified in the preview create request. */
		deployment: Option[String] = None,
	  /** Optional. Current mode of preview. */
		previewMode: Option[Schema.Preview.PreviewModeEnum] = None,
	  /** Required. User-specified Service Account (SA) credentials to be used when previewing resources. Format: `projects/{projectID}/serviceAccounts/{serviceAccount}` */
		serviceAccount: Option[String] = None,
	  /** Optional. User-defined location of Cloud Build logs, artifacts, and in Google Cloud Storage. Format: `gs://{bucket}/{folder}` A default bucket will be bootstrapped if the field is not set or empty Default Bucket Format: `gs://--blueprint-config` Constraints: - The bucket needs to be in the same project as the deployment - The path cannot be within the path of `gcs_source` If omitted and deployment resource ref provided has artifacts_gcs_bucket defined, that artifact bucket is used. */
		artifactsGcsBucket: Option[String] = None,
	  /** Optional. The user-specified Worker Pool resource in which the Cloud Build job will execute. Format projects/{project}/locations/{location}/workerPools/{workerPoolId} If this field is unspecified, the default Cloud Build worker pool will be used. If omitted and deployment resource ref provided has worker_pool defined, that worker pool is used. */
		workerPool: Option[String] = None,
	  /** Output only. Code describing any errors that may have occurred. */
		errorCode: Option[Schema.Preview.ErrorCodeEnum] = None,
	  /** Output only. Additional information regarding the current state. */
		errorStatus: Option[Schema.Status] = None,
	  /** Output only. Cloud Build instance UUID associated with this preview. */
		build: Option[String] = None,
	  /** Output only. Summary of errors encountered during Terraform preview. It has a size limit of 10, i.e. only top 10 errors will be summarized here. */
		tfErrors: Option[List[Schema.TerraformError]] = None,
	  /** Output only. Link to tf-error.ndjson file, which contains the full list of the errors encountered during a Terraform preview. Format: `gs://{bucket}/{object}`. */
		errorLogs: Option[String] = None,
	  /** Output only. Artifacts from preview. */
		previewArtifacts: Option[Schema.PreviewArtifacts] = None,
	  /** Output only. Location of preview logs in `gs://{bucket}/{object}` format. */
		logs: Option[String] = None,
	  /** Output only. The current Terraform version set on the preview. It is in the format of "Major.Minor.Patch", for example, "1.3.10". */
		tfVersion: Option[String] = None,
	  /** Optional. The user-specified Terraform version constraint. Example: "=1.3.10". */
		tfVersionConstraint: Option[String] = None,
	  /** Optional. Arbitrary key-value metadata storage e.g. to help client tools identifiy preview during automation. See https://google.aip.dev/148#annotations for details on format and size limitations. */
		annotations: Option[Map[String, String]] = None
	)
	
	case class PreviewArtifacts(
	  /** Output only. Location of a blueprint copy and other content in Google Cloud Storage. Format: `gs://{bucket}/{object}` */
		content: Option[String] = None,
	  /** Output only. Location of artifacts in Google Cloud Storage. Format: `gs://{bucket}/{object}` */
		artifacts: Option[String] = None
	)
	
	case class ListPreviewsResponse(
	  /** List of Previewss. */
		previews: Option[List[Schema.Preview]] = None,
	  /** Token to be supplied to the next ListPreviews request via `page_token` to obtain the next set of results. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ExportPreviewResultRequest(
	
	)
	
	case class ExportPreviewResultResponse(
	  /** Output only. Signed URLs for accessing the plan files. */
		result: Option[Schema.PreviewResult] = None
	)
	
	case class PreviewResult(
	  /** Output only. Plan binary signed URL */
		binarySignedUri: Option[String] = None,
	  /** Output only. Plan JSON signed URL */
		jsonSignedUri: Option[String] = None
	)
	
	case class ListTerraformVersionsResponse(
	  /** List of TerraformVersions. */
		terraformVersions: Option[List[Schema.TerraformVersion]] = None,
	  /** Token to be supplied to the next ListTerraformVersions request via `page_token` to obtain the next set of results. */
		nextPageToken: Option[String] = None,
	  /** Unreachable resources, if any. */
		unreachable: Option[List[String]] = None
	)
	
	object TerraformVersion {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, DEPRECATED, OBSOLETE }
	}
	case class TerraformVersion(
	  /** Identifier. The version name is in the format: 'projects/{project_id}/locations/{location}/terraformVersions/{terraform_version}'. */
		name: Option[String] = None,
	  /** Output only. The state of the version, ACTIVE, DEPRECATED or OBSOLETE. */
		state: Option[Schema.TerraformVersion.StateEnum] = None,
	  /** Output only. When the version is supported. */
		supportTime: Option[String] = None,
	  /** Output only. When the version is deprecated. */
		deprecateTime: Option[String] = None,
	  /** Output only. When the version is obsolete. */
		obsoleteTime: Option[String] = None
	)
	
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
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None,
	  /** OPTIONAL: A FieldMask specifying which fields of the policy to modify. Only the fields in the mask will be modified. If no mask is provided, the following default mask is used: `paths: "bindings, etag"` */
		updateMask: Option[String] = None
	)
	
	case class Policy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.Binding]] = None,
	  /** Specifies cloud audit logging configuration for this policy. */
		auditConfigs: Option[List[Schema.AuditConfig]] = None,
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An `etag` is returned in the response to `getIamPolicy`, and systems are expected to put that etag in the request to `setIamPolicy` to ensure that their change will be applied to the same version of the policy. &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. */
		etag: Option[String] = None
	)
	
	case class Binding(
	  /** Role that is assigned to the list of `members`, or principals. For example, `roles/viewer`, `roles/editor`, or `roles/owner`. For an overview of the IAM roles and permissions, see the [IAM documentation](https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see [here](https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. `members` can have the following values: &#42; `allUsers`: A special identifier that represents anyone who is on the internet; with or without a Google account. &#42; `allAuthenticatedUsers`: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. &#42; `user:{emailid}`: An email address that represents a specific Google account. For example, `alice@example.com` . &#42; `serviceAccount:{emailid}`: An email address that represents a Google service account. For example, `my-other-app@appspot.gserviceaccount.com`. &#42; `serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]`: An identifier for a [Kubernetes service account](https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, `my-project.svc.id.goog[my-namespace/my-kubernetes-sa]`. &#42; `group:{emailid}`: An email address that represents a Google group. For example, `admins@example.com`. &#42; `domain:{domain}`: The G Suite domain (primary) that represents all the users of that domain. For example, `google.com` or `example.com`. &#42; `principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workforce identity pool. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}`: All workforce identities in a group. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All workforce identities with a specific attribute value. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;`: All identities in a workforce identity pool. &#42; `principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workload identity pool. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}`: A workload identity pool group. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All identities in a workload identity pool with a certain attribute. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;`: All identities in a workload identity pool. &#42; `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a user that has been recently deleted. For example, `alice@example.com?uid=123456789012345678901`. If the user is recovered, this value reverts to `user:{emailid}` and the recovered user retains the role in the binding. &#42; `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, `my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901`. If the service account is undeleted, this value reverts to `serviceAccount:{emailid}` and the undeleted service account retains the role in the binding. &#42; `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, `admins@example.com?uid=123456789012345678901`. If the group is recovered, this value reverts to `group:{emailid}` and the recovered group retains the role in the binding. &#42; `deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: Deleted single identity in a workforce identity pool. For example, `deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value`. */
		members: Option[List[String]] = None,
	  /** The condition that is associated with this binding. If the condition evaluates to `true`, then this binding applies to the current request. If the condition evaluates to `false`, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		condition: Option[Schema.Expr] = None
	)
	
	case class Expr(
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None,
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None,
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None
	)
	
	case class AuditConfig(
	  /** Specifies a service that will be enabled for audit logging. For example, `storage.googleapis.com`, `cloudsql.googleapis.com`. `allServices` is a special value that covers all services. */
		service: Option[String] = None,
	  /** The configuration for logging of each type of permission. */
		auditLogConfigs: Option[List[Schema.AuditLogConfig]] = None
	)
	
	object AuditLogConfig {
		enum LogTypeEnum extends Enum[LogTypeEnum] { case LOG_TYPE_UNSPECIFIED, ADMIN_READ, DATA_WRITE, DATA_READ }
	}
	case class AuditLogConfig(
	  /** The log type that this config enables. */
		logType: Option[Schema.AuditLogConfig.LogTypeEnum] = None,
	  /** Specifies the identities that do not cause logging for this type of permission. Follows the same format of Binding.members. */
		exemptedMembers: Option[List[String]] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class OperationMetadata(
	  /** Output only. Metadata about the deployment operation state. */
		deploymentMetadata: Option[Schema.DeploymentOperationMetadata] = None,
	  /** Output only. Metadata about the preview operation state. */
		previewMetadata: Option[Schema.PreviewOperationMetadata] = None,
	  /** Output only. Time when the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. Time when the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusMessage: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	object DeploymentOperationMetadata {
		enum StepEnum extends Enum[StepEnum] { case DEPLOYMENT_STEP_UNSPECIFIED, PREPARING_STORAGE_BUCKET, DOWNLOADING_BLUEPRINT, RUNNING_TF_INIT, RUNNING_TF_PLAN, RUNNING_TF_APPLY, RUNNING_TF_DESTROY, RUNNING_TF_VALIDATE, UNLOCKING_DEPLOYMENT, SUCCEEDED, FAILED, VALIDATING_REPOSITORY, RUNNING_QUOTA_VALIDATION }
	}
	case class DeploymentOperationMetadata(
	  /** The current step the deployment operation is running. */
		step: Option[Schema.DeploymentOperationMetadata.StepEnum] = None,
	  /** Outputs and artifacts from applying a deployment. */
		applyResults: Option[Schema.ApplyResults] = None,
	  /** Output only. Cloud Build instance UUID associated with this operation. */
		build: Option[String] = None,
	  /** Output only. Location of Deployment operations logs in `gs://{bucket}/{object}` format. */
		logs: Option[String] = None
	)
	
	object PreviewOperationMetadata {
		enum StepEnum extends Enum[StepEnum] { case PREVIEW_STEP_UNSPECIFIED, PREPARING_STORAGE_BUCKET, DOWNLOADING_BLUEPRINT, RUNNING_TF_INIT, RUNNING_TF_PLAN, FETCHING_DEPLOYMENT, LOCKING_DEPLOYMENT, UNLOCKING_DEPLOYMENT, SUCCEEDED, FAILED, VALIDATING_REPOSITORY }
	}
	case class PreviewOperationMetadata(
	  /** The current step the preview operation is running. */
		step: Option[Schema.PreviewOperationMetadata.StepEnum] = None,
	  /** Artifacts from preview. */
		previewArtifacts: Option[Schema.PreviewArtifacts] = None,
	  /** Output only. Location of preview logs in `gs://{bucket}/{object}` format. */
		logs: Option[String] = None,
	  /** Output only. Cloud Build instance UUID associated with this preview. */
		build: Option[String] = None
	)
}
