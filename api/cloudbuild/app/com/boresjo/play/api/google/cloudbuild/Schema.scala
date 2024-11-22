package com.boresjo.play.api.google.cloudbuild

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object InstallationState {
		enum StageEnum extends Enum[StageEnum] { case STAGE_UNSPECIFIED, PENDING_CREATE_APP, PENDING_USER_OAUTH, PENDING_INSTALL_APP, COMPLETE }
	}
	case class InstallationState(
	  /** Output only. Current step of the installation process. */
		stage: Option[Schema.InstallationState.StageEnum] = None,
	  /** Output only. Link to follow for next action. Empty string if the installation is already complete. */
		actionUri: Option[String] = None,
	  /** Output only. Message of what the user should do next to continue the installation. Empty string if the installation is already complete. */
		message: Option[String] = None
	)
	
	object StepRef {
		enum ResolverEnum extends Enum[ResolverEnum] { case RESOLVER_NAME_UNSPECIFIED, BUNDLES, GCB_REPO, GIT, DEVELOPER_CONNECT, DEFAULT }
	}
	case class StepRef(
	  /** Optional. Type of the resolver. */
		resolver: Option[Schema.StepRef.ResolverEnum] = None,
	  /** Optional. Parameters used to control the resolution. */
		params: Option[List[Schema.Param]] = None,
	  /** Optional. Name of the step. */
		name: Option[String] = None
	)
	
	case class Param(
	  /** Name of the parameter. */
		name: Option[String] = None,
	  /** Value of the parameter. */
		value: Option[Schema.ParamValue] = None
	)
	
	case class Worker(
	  /** Optional. Machine type of a worker, default is "e2-standard-2". */
		machineType: Option[String] = None
	)
	
	case class SkippedTask(
	  /** WhenExpressions is the list of checks guarding the execution of the PipelineTask */
		whenExpressions: Option[List[Schema.WhenExpression]] = None,
	  /** Name is the Pipeline Task name */
		name: Option[String] = None,
	  /** Output only. Reason is the cause of the PipelineTask being skipped. */
		reason: Option[String] = None
	)
	
	case class Operation(
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class BitbucketCloudConfig(
	  /** Required. SecretManager resource containing the webhook secret used to verify webhook events, formatted as `projects/&#42;/secrets/&#42;/versions/&#42;`. */
		webhookSecretSecretVersion: Option[String] = None,
	  /** Required. An access token with the `webhook`, `repository`, `repository:admin` and `pullrequest` scope access. It can be either a workspace, project or repository access token. It's recommended to use a system account to generate these credentials. */
		authorizerCredential: Option[Schema.UserCredential] = None,
	  /** Required. An access token with the `repository` access. It can be either a workspace, project or repository access token. It's recommended to use a system account to generate the credentials. */
		readAuthorizerCredential: Option[Schema.UserCredential] = None,
	  /** Required. The Bitbucket Cloud Workspace ID to be connected to Google Cloud Platform. */
		workspace: Option[String] = None
	)
	
	object PipelineRun {
		enum PipelineRunStatusEnum extends Enum[PipelineRunStatusEnum] { case PIPELINE_RUN_STATUS_UNSPECIFIED, PIPELINE_RUN_CANCELLED }
	}
	case class PipelineRun(
	  /** Needed for declarative-friendly resources. */
		etag: Option[String] = None,
	  /** Output only. Time the pipeline is actually started. */
		startTime: Option[String] = None,
	  /** Workspaces is a list of WorkspaceBindings from volumes to workspaces. */
		workspaces: Option[List[Schema.WorkspaceBinding]] = None,
	  /** Output only. The `Record` of this `PipelineRun`. Format: `projects/{project}/locations/{location}/results/{result_id}/records/{record_id}` */
		record: Option[String] = None,
	  /** Output only. FinallyStartTime is when all non-finally tasks have been completed and only finally tasks are being executed. +optional */
		finallyStartTime: Option[String] = None,
	  /** Output only. Time at which the request to create the `PipelineRun` was received. */
		createTime: Option[String] = None,
	  /** Output only. List of TaskRun and Run names and PipelineTask names for children of this PipelineRun. */
		childReferences: Option[List[Schema.ChildStatusReference]] = None,
	  /** Optional. Worker configuration. */
		worker: Option[Schema.Worker] = None,
	  /** Optional. Provenance configuration. */
		provenance: Option[Schema.Provenance] = None,
	  /** Output only. A unique identifier for the `PipelineRun`. */
		uid: Option[String] = None,
	  /** Output only. Kubernetes Conditions convention for PipelineRun status and error. */
		conditions: Option[List[Schema.GoogleDevtoolsCloudbuildV2Condition]] = None,
	  /** Optional. Output only. List of results written out by the pipeline's containers */
		results: Option[List[Schema.PipelineRunResult]] = None,
	  /** Params is a list of parameter names and values. */
		params: Option[List[Schema.Param]] = None,
	  /** Output only. Time the pipeline completed. */
		completionTime: Option[String] = None,
	  /** Output only. The exact PipelineSpec used to instantiate the run. */
		resolvedPipelineSpec: Option[Schema.PipelineSpec] = None,
	  /** Optional. Security configuration. */
		security: Option[Schema.Security] = None,
	  /** Pipelinerun status the user can provide. Used for cancellation. */
		pipelineRunStatus: Option[Schema.PipelineRun.PipelineRunStatusEnum] = None,
	  /** PipelineSpec defines the desired state of Pipeline. */
		pipelineSpec: Option[Schema.PipelineSpec] = None,
	  /** Output only. The `PipelineRun` name with format `projects/{project}/locations/{location}/pipelineRuns/{pipeline_run}` */
		name: Option[String] = None,
	  /** Service account used in the Pipeline. Deprecated; please use security.service_account instead. */
		serviceAccount: Option[String] = None,
	  /** Output only. The Workflow used to create this PipelineRun. */
		workflow: Option[String] = None,
	  /** PipelineRef refer to a specific instance of a Pipeline. */
		pipelineRef: Option[Schema.PipelineRef] = None,
	  /** Output only. Inline pipelineSpec yaml string, used by workflow run requests. */
		pipelineSpecYaml: Option[String] = None,
	  /** Output only. The WorkerPool used to run this PipelineRun. */
		workerPool: Option[String] = None,
	  /** Output only. GCB default params. */
		gcbParams: Option[Map[String, String]] = None,
	  /** Output only. Time at which the request to update the `PipelineRun` was received. */
		updateTime: Option[String] = None,
	  /** Time after which the Pipeline times out. Currently three keys are accepted in the map pipeline, tasks and finally with Timeouts.pipeline >= Timeouts.tasks + Timeouts.finally */
		timeouts: Option[Schema.TimeoutFields] = None,
	  /** Output only. List of tasks that were skipped due to when expressions evaluating to false. */
		skippedTasks: Option[List[Schema.SkippedTask]] = None,
	  /** User annotations. See https://google.aip.dev/128#annotations */
		annotations: Option[Map[String, String]] = None
	)
	
	object PropertySpec {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, STRING }
	}
	case class PropertySpec(
	  /** A type for the object. */
		`type`: Option[Schema.PropertySpec.TypeEnum] = None
	)
	
	case class EnvVar(
	  /** Name of the environment variable. */
		name: Option[String] = None,
	  /** Value of the environment variable. */
		value: Option[String] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None
	)
	
	case class GitHubConfig(
	  /** Optional. OAuth credential of the account that authorized the Cloud Build GitHub App. It is recommended to use a robot account instead of a human user account. The OAuth token must be tied to the Cloud Build GitHub App. */
		authorizerCredential: Option[Schema.OAuthCredential] = None,
	  /** Optional. GitHub App installation id. */
		appInstallationId: Option[String] = None
	)
	
	case class Binding(
	  /** Specifies the principals requesting access for a Google Cloud resource. `members` can have the following values: &#42; `allUsers`: A special identifier that represents anyone who is on the internet; with or without a Google account. &#42; `allAuthenticatedUsers`: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. &#42; `user:{emailid}`: An email address that represents a specific Google account. For example, `alice@example.com` . &#42; `serviceAccount:{emailid}`: An email address that represents a Google service account. For example, `my-other-app@appspot.gserviceaccount.com`. &#42; `serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]`: An identifier for a [Kubernetes service account](https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, `my-project.svc.id.goog[my-namespace/my-kubernetes-sa]`. &#42; `group:{emailid}`: An email address that represents a Google group. For example, `admins@example.com`. &#42; `domain:{domain}`: The G Suite domain (primary) that represents all the users of that domain. For example, `google.com` or `example.com`. &#42; `principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workforce identity pool. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}`: All workforce identities in a group. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All workforce identities with a specific attribute value. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;`: All identities in a workforce identity pool. &#42; `principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workload identity pool. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}`: A workload identity pool group. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All identities in a workload identity pool with a certain attribute. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;`: All identities in a workload identity pool. &#42; `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a user that has been recently deleted. For example, `alice@example.com?uid=123456789012345678901`. If the user is recovered, this value reverts to `user:{emailid}` and the recovered user retains the role in the binding. &#42; `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, `my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901`. If the service account is undeleted, this value reverts to `serviceAccount:{emailid}` and the undeleted service account retains the role in the binding. &#42; `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, `admins@example.com?uid=123456789012345678901`. If the group is recovered, this value reverts to `group:{emailid}` and the recovered group retains the role in the binding. &#42; `deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: Deleted single identity in a workforce identity pool. For example, `deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value`. */
		members: Option[List[String]] = None,
	  /** Role that is assigned to the list of `members`, or principals. For example, `roles/viewer`, `roles/editor`, or `roles/owner`. For an overview of the IAM roles and permissions, see the [IAM documentation](https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see [here](https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** The condition that is associated with this binding. If the condition evaluates to `true`, then this binding applies to the current request. If the condition evaluates to `false`, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		condition: Option[Schema.Expr] = None
	)
	
	case class UserCredential(
	  /** Output only. The username associated to this token. */
		username: Option[String] = None,
	  /** Required. A SecretManager resource containing the user token that authorizes the Cloud Build connection. Format: `projects/&#42;/secrets/&#42;/versions/&#42;`. */
		userTokenSecretVersion: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class CancelOperationRequest(
	
	)
	
	case class GoogleDevtoolsCloudbuildV2GitHubEnterpriseConfig(
	  /** Optional. Configuration for using Service Directory to privately connect to a GitHub Enterprise server. This should only be set if the GitHub Enterprise server is hosted on-premises and not reachable by public internet. If this field is left empty, calls to the GitHub Enterprise server will be made over the public internet. */
		serviceDirectoryConfig: Option[Schema.GoogleDevtoolsCloudbuildV2ServiceDirectoryConfig] = None,
	  /** Optional. ID of the installation of the GitHub App. */
		appInstallationId: Option[String] = None,
	  /** Optional. SSL certificate to use for requests to GitHub Enterprise. */
		sslCa: Option[String] = None,
	  /** Optional. SecretManager resource containing the private key of the GitHub App, formatted as `projects/&#42;/secrets/&#42;/versions/&#42;`. */
		privateKeySecretVersion: Option[String] = None,
	  /** Optional. SecretManager resource containing the webhook secret of the GitHub App, formatted as `projects/&#42;/secrets/&#42;/versions/&#42;`. */
		webhookSecretSecretVersion: Option[String] = None,
	  /** Required. The URI of the GitHub Enterprise host this connection is for. */
		hostUri: Option[String] = None,
	  /** Optional. Id of the GitHub App created from the manifest. */
		appId: Option[String] = None,
	  /** Output only. GitHub Enterprise version installed at the host_uri. */
		serverVersion: Option[String] = None,
	  /** Optional. The URL-friendly name of the GitHub App. */
		appSlug: Option[String] = None,
	  /** Required. API Key used for authentication of webhook events. */
		apiKey: Option[String] = None
	)
	
	case class ListLocationsResponse(
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None,
	  /** A list of locations that matches the specified filter in the request. */
		locations: Option[List[Schema.Location]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	object ParamSpec {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, STRING, ARRAY, OBJECT }
	}
	case class ParamSpec(
	  /** The default value a parameter takes if no input value is supplied */
		default: Option[Schema.ParamValue] = None,
	  /** Description of the ParamSpec */
		description: Option[String] = None,
	  /** Name of the ParamSpec */
		name: Option[String] = None,
	  /** Type of ParamSpec */
		`type`: Option[Schema.ParamSpec.TypeEnum] = None
	)
	
	case class WorkspacePipelineTaskBinding(
	  /** Optional. SubPath is optionally a directory on the volume which should be used for this binding (i.e. the volume will be mounted at this sub directory). +optional */
		subPath: Option[String] = None,
	  /** Name of the workspace as declared by the task. */
		name: Option[String] = None,
	  /** Name of the workspace declared by the pipeline. */
		workspace: Option[String] = None
	)
	
	case class VolumeSource(
	  /** Name of the Volume. Must be a DNS_LABEL and unique within the pod. More info: https://kubernetes.io/docs/concepts/overview/working-with-objects/names/#names */
		name: Option[String] = None,
	  /** A temporary directory that shares a pod's lifetime. */
		emptyDir: Option[Schema.EmptyDirVolumeSource] = None
	)
	
	case class ListConnectionsResponse(
	  /** The list of Connections. */
		connections: Option[List[Schema.Connection]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None
	)
	
	case class StepTemplate(
	  /** Optional. List of environment variables to set in the Step. Cannot be updated. */
		env: Option[List[Schema.EnvVar]] = None,
	  /** Optional. Pod volumes to mount into the container's filesystem. */
		volumeMounts: Option[List[Schema.VolumeMount]] = None
	)
	
	case class FetchReadTokenRequest(
	
	)
	
	object Step {
		enum OnErrorEnum extends Enum[OnErrorEnum] { case ON_ERROR_TYPE_UNSPECIFIED, STOP_AND_FAIL, CONTINUE }
	}
	case class Step(
	  /** Optional. Optional reference to a remote StepAction. */
		ref: Option[Schema.StepRef] = None,
	  /** Arguments to the entrypoint. */
		args: Option[List[String]] = None,
	  /** Pod volumes to mount into the container's filesystem. */
		volumeMounts: Option[List[Schema.VolumeMount]] = None,
	  /** Docker image name. */
		image: Option[String] = None,
	  /** Name of the container specified as a DNS_LABEL. */
		name: Option[String] = None,
	  /** Optional. SecurityContext defines the security options the Step should be run with. If set, the fields of SecurityContext override the equivalent fields of PodSecurityContext. More info: https://kubernetes.io/docs/tasks/configure-pod-container/security-context/ +optional */
		securityContext: Option[Schema.SecurityContext] = None,
	  /** Entrypoint array. */
		command: Option[List[String]] = None,
	  /** Optional. Optional parameters passed to the StepAction. */
		params: Option[List[Schema.Param]] = None,
	  /** The contents of an executable file to execute. */
		script: Option[String] = None,
	  /** List of environment variables to set in the container. */
		env: Option[List[Schema.EnvVar]] = None,
	  /** Optional. OnError defines the exiting behavior on error can be set to [ continue | stopAndFail ] */
		onError: Option[Schema.Step.OnErrorEnum] = None,
	  /** Time after which the Step times out. Defaults to never. */
		timeout: Option[String] = None,
	  /** Container's working directory. */
		workingDir: Option[String] = None
	)
	
	object ParamValue {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, STRING, ARRAY, OBJECT }
	}
	case class ParamValue(
	  /** Value of the parameter if type is string. */
		stringVal: Option[String] = None,
	  /** Type of parameter. */
		`type`: Option[Schema.ParamValue.TypeEnum] = None,
	  /** Value of the parameter if type is array. */
		arrayVal: Option[List[String]] = None,
	  /** Optional. Value of the parameter if type is object. */
		objectVal: Option[Map[String, String]] = None
	)
	
	case class VolumeMount(
	  /** Path within the container at which the volume should be mounted. Must not contain ':'. */
		mountPath: Option[String] = None,
	  /** Path within the volume from which the container's volume should be mounted. Defaults to "" (volume's root). */
		subPath: Option[String] = None,
	  /** Expanded path within the volume from which the container's volume should be mounted. Behaves similarly to SubPath but environment variable references $(VAR_NAME) are expanded using the container's environment. Defaults to "" (volume's root). */
		subPathExpr: Option[String] = None,
	  /** Name of the volume. */
		name: Option[String] = None,
	  /** Mounted read-only if true, read-write otherwise (false or unspecified). */
		readOnly: Option[Boolean] = None
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
	
	case class HttpBody(
	  /** The HTTP Content-Type header value specifying the content type of the body. */
		contentType: Option[String] = None,
	  /** The HTTP request/response body as raw binary. */
		data: Option[String] = None,
	  /** Application specific response metadata. Must be set in the first response for streaming APIs. */
		extensions: Option[List[Map[String, JsValue]]] = None
	)
	
	case class PipelineSpec(
	  /** Output only. auto-generated yaml that is output only for display purpose for workflows using pipeline_spec, used by UI/gcloud cli for Workflows. */
		generatedYaml: Option[String] = None,
	  /** Optional. Output only. List of results written out by the pipeline's containers */
		results: Option[List[Schema.PipelineResult]] = None,
	  /** List of Tasks that execute when this Pipeline is run. */
		tasks: Option[List[Schema.PipelineTask]] = None,
	  /** Workspaces declares a set of named workspaces that are expected to be provided by a PipelineRun. */
		workspaces: Option[List[Schema.PipelineWorkspaceDeclaration]] = None,
	  /** List of Tasks that execute just before leaving the Pipeline i.e. either after all Tasks are finished executing successfully or after a failure which would result in ending the Pipeline. */
		finallyTasks: Option[List[Schema.PipelineTask]] = None,
	  /** List of parameters. */
		params: Option[List[Schema.ParamSpec]] = None
	)
	
	case class TimeoutFields(
	  /** Pipeline sets the maximum allowed duration for execution of the entire pipeline. The sum of individual timeouts for tasks and finally must not exceed this value. */
		pipeline: Option[String] = None,
	  /** Finally sets the maximum allowed duration of this pipeline's finally */
		`finally`: Option[String] = None,
	  /** Tasks sets the maximum allowed duration of this pipeline's tasks */
		tasks: Option[String] = None
	)
	
	case class BatchCreateRepositoriesRequest(
	  /** Required. The request messages specifying the repositories to create. */
		requests: Option[List[Schema.CreateRepositoryRequest]] = None
	)
	
	case class FetchGitRefsResponse(
	  /** Name of the refs fetched. */
		refNames: Option[List[String]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None
	)
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None,
	  /** OPTIONAL: A FieldMask specifying which fields of the policy to modify. Only the fields in the mask will be modified. If no mask is provided, the following default mask is used: `paths: "bindings, etag"` */
		updateMask: Option[String] = None
	)
	
	case class EmbeddedTask(
	  /** User annotations. See https://google.aip.dev/128#annotations */
		annotations: Option[Map[String, String]] = None,
	  /** Spec to instantiate this TaskRun. */
		taskSpec: Option[Schema.TaskSpec] = None
	)
	
	object GoogleDevtoolsCloudbuildV2Condition {
		enum StatusEnum extends Enum[StatusEnum] { case UNKNOWN, TRUE, FALSE }
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, WARNING, INFO }
	}
	case class GoogleDevtoolsCloudbuildV2Condition(
	  /** LastTransitionTime is the last time the condition transitioned from one status to another. */
		lastTransitionTime: Option[String] = None,
	  /** Status of the condition. */
		status: Option[Schema.GoogleDevtoolsCloudbuildV2Condition.StatusEnum] = None,
	  /** A human readable message indicating details about the transition. */
		message: Option[String] = None,
	  /** Type of condition. */
		`type`: Option[String] = None,
	  /** Severity with which to treat failures of this type of condition. */
		severity: Option[Schema.GoogleDevtoolsCloudbuildV2Condition.SeverityEnum] = None,
	  /** The reason for the condition's last transition. */
		reason: Option[String] = None
	)
	
	case class PipelineTask(
	  /** Conditions that need to be true for the task to run. */
		whenExpressions: Option[List[Schema.WhenExpression]] = None,
	  /** Name of the task. */
		name: Option[String] = None,
	  /** Spec to instantiate this TaskRun. */
		taskSpec: Option[Schema.EmbeddedTask] = None,
	  /** Workspaces maps workspaces from the pipeline spec to the workspaces declared in the Task. */
		workspaces: Option[List[Schema.WorkspacePipelineTaskBinding]] = None,
	  /** Time after which the TaskRun times out. Defaults to 1 hour. Specified TaskRun timeout should be less than 24h. */
		timeout: Option[String] = None,
	  /** RunAfter is the list of PipelineTask names that should be executed before this Task executes. (Used to force a specific ordering in graph execution.) */
		runAfter: Option[List[String]] = None,
	  /** Retries represents how many times this task should be retried in case of task failure. */
		retries: Option[Int] = None,
	  /** Params is a list of parameter names and values. */
		params: Option[List[Schema.Param]] = None,
	  /** Reference to a specific instance of a task. */
		taskRef: Option[Schema.TaskRef] = None
	)
	
	case class Expr(
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None,
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None
	)
	
	case class Repository(
	  /** Optional. Allows clients to store small amounts of arbitrary data. */
		annotations: Option[Map[String, String]] = None,
	  /** Output only. Server assigned timestamp for when the connection was updated. */
		updateTime: Option[String] = None,
	  /** Output only. Server assigned timestamp for when the connection was created. */
		createTime: Option[String] = None,
	  /** Required. Git Clone HTTPS URI. */
		remoteUri: Option[String] = None,
	  /** This checksum is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Immutable. Resource name of the repository, in the format `projects/&#42;/locations/&#42;/connections/&#42;/repositories/&#42;`. */
		name: Option[String] = None,
	  /** Output only. External ID of the webhook created for the repository. */
		webhookId: Option[String] = None
	)
	
	case class GoogleDevtoolsCloudbuildV2ServiceDirectoryConfig(
	  /** Required. The Service Directory service name. Format: projects/{project}/locations/{location}/namespaces/{namespace}/services/{service}. */
		service: Option[String] = None
	)
	
	case class FetchReadWriteTokenResponse(
	  /** Expiration timestamp. Can be empty if unknown or non-expiring. */
		expirationTime: Option[String] = None,
	  /** The token content. */
		token: Option[String] = None
	)
	
	case class EmptyDirVolumeSource(
	
	)
	
	case class SecretVolumeSource(
	  /** Name of the secret referenced by the WorkspaceBinding. */
		secretName: Option[String] = None,
	  /** Optional. Resource name of the SecretVersion. In format: projects/&#42;/secrets/&#42;/versions/&#42; */
		secretVersion: Option[String] = None
	)
	
	case class Connection(
	  /** Configuration for connections to Bitbucket Cloud. */
		bitbucketCloudConfig: Option[Schema.BitbucketCloudConfig] = None,
	  /** Output only. Installation state of the Connection. */
		installationState: Option[Schema.InstallationState] = None,
	  /** Configuration for connections to Bitbucket Data Center. */
		bitbucketDataCenterConfig: Option[Schema.BitbucketDataCenterConfig] = None,
	  /** Optional. If disabled is set to true, functionality is disabled for this connection. Repository based API methods and webhooks processing for repositories in this connection will be disabled. */
		disabled: Option[Boolean] = None,
	  /** Configuration for connections to an instance of GitHub Enterprise. */
		githubEnterpriseConfig: Option[Schema.GoogleDevtoolsCloudbuildV2GitHubEnterpriseConfig] = None,
	  /** Immutable. The resource name of the connection, in the format `projects/{project}/locations/{location}/connections/{connection_id}`. */
		name: Option[String] = None,
	  /** Output only. Set to true when the connection is being set up or updated in the background. */
		reconciling: Option[Boolean] = None,
	  /** Output only. Server assigned timestamp for when the connection was created. */
		createTime: Option[String] = None,
	  /** Configuration for connections to github.com. */
		githubConfig: Option[Schema.GitHubConfig] = None,
	  /** Configuration for connections to gitlab.com or an instance of GitLab Enterprise. */
		gitlabConfig: Option[Schema.GoogleDevtoolsCloudbuildV2GitLabConfig] = None,
	  /** This checksum is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Output only. Server assigned timestamp for when the connection was updated. */
		updateTime: Option[String] = None,
	  /** Optional. Allows clients to store small amounts of arbitrary data. */
		annotations: Option[Map[String, String]] = None
	)
	
	case class GoogleDevtoolsCloudbuildV2GitLabConfig(
	  /** Required. Immutable. SecretManager resource containing the webhook secret of a GitLab Enterprise project, formatted as `projects/&#42;/secrets/&#42;/versions/&#42;`. */
		webhookSecretSecretVersion: Option[String] = None,
	  /** Output only. Version of the GitLab Enterprise server running on the `host_uri`. */
		serverVersion: Option[String] = None,
	  /** Optional. The URI of the GitLab Enterprise host this connection is for. If not specified, the default value is https://gitlab.com. */
		hostUri: Option[String] = None,
	  /** Optional. SSL certificate to use for requests to GitLab Enterprise. */
		sslCa: Option[String] = None,
	  /** Optional. Configuration for using Service Directory to privately connect to a GitLab Enterprise server. This should only be set if the GitLab Enterprise server is hosted on-premises and not reachable by public internet. If this field is left empty, calls to the GitLab Enterprise server will be made over the public internet. */
		serviceDirectoryConfig: Option[Schema.GoogleDevtoolsCloudbuildV2ServiceDirectoryConfig] = None,
	  /** Required. A GitLab personal access token with the `api` scope access. */
		authorizerCredential: Option[Schema.UserCredential] = None,
	  /** Required. A GitLab personal access token with the minimum `read_api` scope access. */
		readAuthorizerCredential: Option[Schema.UserCredential] = None
	)
	
	object PipelineRef {
		enum ResolverEnum extends Enum[ResolverEnum] { case RESOLVER_NAME_UNSPECIFIED, BUNDLES, GCB_REPO, GIT, DEVELOPER_CONNECT, DEFAULT }
	}
	case class PipelineRef(
	  /** Resolver is the name of the resolver that should perform resolution of the referenced Tekton resource. */
		resolver: Option[Schema.PipelineRef.ResolverEnum] = None,
	  /** Optional. Name of the Pipeline. */
		name: Option[String] = None,
	  /** Params contains the parameters used to identify the referenced Tekton resource. Example entries might include "repo" or "path" but the set of params ultimately depends on the chosen resolver. */
		params: Option[List[Schema.Param]] = None
	)
	
	case class BatchCreateRepositoriesResponse(
	  /** Repository resources created. */
		repositories: Option[List[Schema.Repository]] = None
	)
	
	case class GoogleDevtoolsCloudbuildV2OperationMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusMessage: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None
	)
	
	case class ListRepositoriesResponse(
	  /** The list of Repositories. */
		repositories: Option[List[Schema.Repository]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None
	)
	
	object PipelineResult {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, STRING, ARRAY, OBJECT }
	}
	case class PipelineResult(
	  /** Output only. Description of the result. */
		description: Option[String] = None,
	  /** Output only. The type of data that the result holds. */
		`type`: Option[Schema.PipelineResult.TypeEnum] = None,
	  /** Output only. Value of the result. */
		value: Option[Schema.ResultValue] = None,
	  /** Output only. Name of the result. */
		name: Option[String] = None
	)
	
	object TaskResult {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, STRING, ARRAY, OBJECT }
	}
	case class TaskResult(
	  /** Description of the result. */
		description: Option[String] = None,
	  /** Optional. Optionally used to initialize a Task's result with a Step's result. */
		value: Option[Schema.ParamValue] = None,
	  /** Name of the result. */
		name: Option[String] = None,
	  /** The type of data that the result holds. */
		`type`: Option[Schema.TaskResult.TypeEnum] = None,
	  /** When type is OBJECT, this map holds the names of fields inside that object along with the type of data each field holds. */
		properties: Option[Map[String, Schema.PropertySpec]] = None
	)
	
	object TaskRef {
		enum ResolverEnum extends Enum[ResolverEnum] { case RESOLVER_NAME_UNSPECIFIED, BUNDLES, GCB_REPO, GIT, DEVELOPER_CONNECT, DEFAULT }
	}
	case class TaskRef(
	  /** Params contains the parameters used to identify the referenced Tekton resource. Example entries might include "repo" or "path" but the set of params ultimately depends on the chosen resolver. */
		params: Option[List[Schema.Param]] = None,
	  /** Resolver is the name of the resolver that should perform resolution of the referenced Tekton resource. */
		resolver: Option[Schema.TaskRef.ResolverEnum] = None,
	  /** Optional. Name of the task. */
		name: Option[String] = None
	)
	
	case class FetchLinkableRepositoriesResponse(
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** repositories ready to be created. */
		repositories: Option[List[Schema.Repository]] = None
	)
	
	case class FetchReadWriteTokenRequest(
	
	)
	
	case class PipelineRunResult(
	  /** Output only. Name of the TaskRun */
		name: Option[String] = None,
	  /** Output only. Value of the result. */
		value: Option[Schema.ResultValue] = None
	)
	
	case class BitbucketDataCenterConfig(
	  /** Optional. SSL certificate to use for requests to the Bitbucket Data Center. */
		sslCa: Option[String] = None,
	  /** Required. A http access token with the `REPO_ADMIN` scope access. */
		authorizerCredential: Option[Schema.UserCredential] = None,
	  /** Required. A http access token with the `REPO_READ` access. */
		readAuthorizerCredential: Option[Schema.UserCredential] = None,
	  /** Output only. Version of the Bitbucket Data Center running on the `host_uri`. */
		serverVersion: Option[String] = None,
	  /** Required. The URI of the Bitbucket Data Center instance or cluster this connection is for. */
		hostUri: Option[String] = None,
	  /** Required. Immutable. SecretManager resource containing the webhook secret used to verify webhook events, formatted as `projects/&#42;/secrets/&#42;/versions/&#42;`. */
		webhookSecretSecretVersion: Option[String] = None,
	  /** Optional. Configuration for using Service Directory to privately connect to a Bitbucket Data Center. This should only be set if the Bitbucket Data Center is hosted on-premises and not reachable by public internet. If this field is left empty, calls to the Bitbucket Data Center will be made over the public internet. */
		serviceDirectoryConfig: Option[Schema.GoogleDevtoolsCloudbuildV2ServiceDirectoryConfig] = None
	)
	
	object WhenExpression {
		enum ExpressionOperatorEnum extends Enum[ExpressionOperatorEnum] { case EXPRESSION_OPERATOR_UNSPECIFIED, IN, NOT_IN }
	}
	case class WhenExpression(
	  /** Operator that represents an Input's relationship to the values */
		expressionOperator: Option[Schema.WhenExpression.ExpressionOperatorEnum] = None,
	  /** Input is the string for guard checking which can be a static input or an output from a parent Task. */
		input: Option[String] = None,
	  /** Values is an array of strings, which is compared against the input, for guard checking. */
		values: Option[List[String]] = None
	)
	
	case class AuditConfig(
	  /** The configuration for logging of each type of permission. */
		auditLogConfigs: Option[List[Schema.AuditLogConfig]] = None,
	  /** Specifies a service that will be enabled for audit logging. For example, `storage.googleapis.com`, `cloudsql.googleapis.com`. `allServices` is a special value that covers all services. */
		service: Option[String] = None
	)
	
	case class CreateRepositoryRequest(
	  /** Required. The repository to create. */
		repository: Option[Schema.Repository] = None,
	  /** Required. The ID to use for the repository, which will become the final component of the repository's resource name. This ID should be unique in the connection. Allows alphanumeric characters and any of -._~%!$&'()&#42;+,;=@. */
		repositoryId: Option[String] = None,
	  /** Required. The connection to contain the repository. If the request is part of a BatchCreateRepositoriesRequest, this field should be empty or match the parent specified there. */
		parent: Option[String] = None
	)
	
	case class PipelineWorkspaceDeclaration(
	  /** Description is a human readable string describing how the workspace will be used in the Pipeline. */
		description: Option[String] = None,
	  /** Optional marks a Workspace as not being required in PipelineRuns. By default this field is false and so declared workspaces are required. */
		optional: Option[Boolean] = None,
	  /** Name is the name of a workspace to be provided by a PipelineRun. */
		name: Option[String] = None
	)
	
	case class RunWorkflowCustomOperationMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. ID of the pipeline run created by RunWorkflow. */
		pipelineRunId: Option[String] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	object Provenance {
		enum EnabledEnum extends Enum[EnabledEnum] { case ENABLED_UNSPECIFIED, REQUIRED, OPTIMISTIC, DISABLED }
		enum StorageEnum extends Enum[StorageEnum] { case STORAGE_UNSPECIFIED, PREFER_ARTIFACT_PROJECT, ARTIFACT_PROJECT_ONLY, BUILD_PROJECT_ONLY }
		enum RegionEnum extends Enum[RegionEnum] { case REGION_UNSPECIFIED, GLOBAL }
	}
	case class Provenance(
	  /** Optional. Provenance push mode. */
		enabled: Option[Schema.Provenance.EnabledEnum] = None,
	  /** Optional. Where provenance is stored. */
		storage: Option[Schema.Provenance.StorageEnum] = None,
	  /** Optional. Provenance region. */
		region: Option[Schema.Provenance.RegionEnum] = None
	)
	
	case class ExecAction(
	  /** Optional. Command is the command line to execute inside the container, the working directory for the command is root ('/') in the container's filesystem. The command is simply exec'd, it is not run inside a shell, so traditional shell instructions ('|', etc) won't work. To use a shell, you need to explicitly call out to that shell. Exit status of 0 is treated as live/healthy and non-zero is unhealthy. +optional */
		command: Option[List[String]] = None
	)
	
	case class WorkspaceDeclaration(
	  /** MountPath overrides the directory that the volume will be made available at. */
		mountPath: Option[String] = None,
	  /** Optional. Optional marks a Workspace as not being required in TaskRuns. By default this field is false and so declared workspaces are required. */
		optional: Option[Boolean] = None,
	  /** Name is the name by which you can bind the volume at runtime. */
		name: Option[String] = None,
	  /** Description is a human readable description of this volume. */
		description: Option[String] = None,
	  /** ReadOnly dictates whether a mounted volume is writable. */
		readOnly: Option[Boolean] = None
	)
	
	case class SecurityContext(
	  /** Optional. Adds and removes POSIX capabilities from running containers. */
		capabilities: Option[Schema.Capabilities] = None,
	  /** Run container in privileged mode. */
		privileged: Option[Boolean] = None,
	  /** Optional. The GID to run the entrypoint of the container process. Uses runtime default if unset. May also be set in PodSecurityContext. If set in both SecurityContext and PodSecurityContext, the value specified in SecurityContext takes precedence. Note that this field cannot be set when spec.os.name is windows. +optional */
		runAsGroup: Option[String] = None,
	  /** Optional. AllowPrivilegeEscalation controls whether a process can gain more privileges than its parent process. This bool directly controls if the no_new_privs flag will be set on the container process. AllowPrivilegeEscalation is true always when the container is: 1) run as Privileged 2) has CAP_SYS_ADMIN Note that this field cannot be set when spec.os.name is windows. +optional */
		allowPrivilegeEscalation: Option[Boolean] = None,
	  /** Optional. Indicates that the container must run as a non-root user. If true, the Kubelet will validate the image at runtime to ensure that it does not run as UID 0 (root) and fail to start the container if it does. If unset or false, no such validation will be performed. May also be set in PodSecurityContext. If set in both SecurityContext and PodSecurityContext, the value specified in SecurityContext takes precedence. +optional */
		runAsNonRoot: Option[Boolean] = None,
	  /** Optional. The UID to run the entrypoint of the container process. Defaults to user specified in image metadata if unspecified. May also be set in PodSecurityContext. If set in both SecurityContext and PodSecurityContext, the value specified in SecurityContext takes precedence. Note that this field cannot be set when spec.os.name is windows. +optional */
		runAsUser: Option[String] = None
	)
	
	object TaskSpec {
		enum ManagedSidecarsEnum extends Enum[ManagedSidecarsEnum] { case MANAGED_SIDECAR_UNSPECIFIED, PRIVILEGED_DOCKER_DAEMON }
	}
	case class TaskSpec(
	  /** The volumes that this Task requires. */
		workspaces: Option[List[Schema.WorkspaceDeclaration]] = None,
	  /** Values that this Task can output. */
		results: Option[List[Schema.TaskResult]] = None,
	  /** List of parameters. */
		params: Option[List[Schema.ParamSpec]] = None,
	  /** A collection of volumes that are available to mount into steps. */
		volumes: Option[List[Schema.VolumeSource]] = None,
	  /** Sidecars that run alongside the Task’s step containers that should be added to this Task. */
		managedSidecars: Option[List[Schema.TaskSpec.ManagedSidecarsEnum]] = None,
	  /** Sidecars that run alongside the Task's step containers. */
		sidecars: Option[List[Schema.Sidecar]] = None,
	  /** Optional. StepTemplate can be used as the basis for all step containers within the Task, so that the steps inherit settings on the base container. */
		stepTemplate: Option[Schema.StepTemplate] = None,
	  /** Steps of the task. */
		steps: Option[List[Schema.Step]] = None,
	  /** Description of the task. */
		description: Option[String] = None
	)
	
	case class OperationMetadata(
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusDetail: Option[String] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have been cancelled successfully have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		cancelRequested: Option[Boolean] = None,
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None
	)
	
	case class Probe(
	  /** Optional. How often (in seconds) to perform the probe. Default to 10 seconds. Minimum value is 1. +optional */
		periodSeconds: Option[Int] = None,
	  /** Optional. Exec specifies the action to take. +optional */
		exec: Option[Schema.ExecAction] = None
	)
	
	object Security {
		enum PrivilegeModeEnum extends Enum[PrivilegeModeEnum] { case PRIVILEGE_MODE_UNSPECIFIED, PRIVILEGED, UNPRIVILEGED }
	}
	case class Security(
	  /** Optional. Privilege mode. */
		privilegeMode: Option[Schema.Security.PrivilegeModeEnum] = None,
	  /** IAM service account whose credentials will be used at runtime. */
		serviceAccount: Option[String] = None
	)
	
	case class OAuthCredential(
	  /** Output only. The username associated to this token. */
		username: Option[String] = None,
	  /** Optional. A SecretManager resource containing the OAuth token that authorizes the Cloud Build connection. Format: `projects/&#42;/secrets/&#42;/versions/&#42;`. */
		oauthTokenSecretVersion: Option[String] = None
	)
	
	case class Location(
	  /** The friendly name for this location, typically a nearby city name. For example, "Tokyo". */
		displayName: Option[String] = None,
	  /** Cross-service attributes for the location. For example {"cloud.googleapis.com/region": "us-east1"} */
		labels: Option[Map[String, String]] = None,
	  /** The canonical id for this location. For example: `"us-east1"`. */
		locationId: Option[String] = None,
	  /** Resource name for the location, which may vary between implementations. For example: `"projects/example-project/locations/us-east1"` */
		name: Option[String] = None,
	  /** Service-specific metadata. For example the available capacity at the given location. */
		metadata: Option[Map[String, JsValue]] = None
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
	
	case class FetchReadTokenResponse(
	  /** Expiration timestamp. Can be empty if unknown or non-expiring. */
		expirationTime: Option[String] = None,
	  /** The token content. */
		token: Option[String] = None
	)
	
	object ChildStatusReference {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, TASK_RUN }
	}
	case class ChildStatusReference(
	  /** PipelineTaskName is the name of the PipelineTask this is referencing. */
		pipelineTaskName: Option[String] = None,
	  /** Output only. Type of the child reference. */
		`type`: Option[Schema.ChildStatusReference.TypeEnum] = None,
	  /** Name is the name of the TaskRun or Run this is referencing. */
		name: Option[String] = None,
	  /** WhenExpressions is the list of checks guarding the execution of the PipelineTask */
		whenExpressions: Option[List[Schema.WhenExpression]] = None
	)
	
	object ResultValue {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, STRING, ARRAY, OBJECT }
	}
	case class ResultValue(
	  /** Value of the result if type is object. */
		objectVal: Option[Map[String, String]] = None,
	  /** Value of the result if type is string. */
		stringVal: Option[String] = None,
	  /** Output only. The type of data that the result holds. */
		`type`: Option[Schema.ResultValue.TypeEnum] = None,
	  /** Value of the result if type is array. */
		arrayVal: Option[List[String]] = None
	)
	
	case class Capabilities(
	  /** Optional. Added capabilities +optional */
		add: Option[List[String]] = None,
	  /** Optional. Removed capabilities +optional */
		drop: Option[List[String]] = None
	)
	
	case class Sidecar(
	  /** Pod volumes to mount into the container's filesystem. */
		volumeMounts: Option[List[Schema.VolumeMount]] = None,
	  /** Container's working directory. */
		workingDir: Option[String] = None,
	  /** Name of the Sidecar. */
		name: Option[String] = None,
	  /** The contents of an executable file to execute. */
		script: Option[String] = None,
	  /** Docker image name. */
		image: Option[String] = None,
	  /** Optional. Security options the container should be run with. */
		securityContext: Option[Schema.SecurityContext] = None,
	  /** List of environment variables to set in the container. */
		env: Option[List[Schema.EnvVar]] = None,
	  /** Optional. Periodic probe of Sidecar service readiness. Container will be removed from service endpoints if the probe fails. Cannot be updated. More info: https://kubernetes.io/docs/concepts/workloads/pods/pod-lifecycle#container-probes +optional */
		readinessProbe: Option[Schema.Probe] = None,
	  /** Entrypoint array. */
		command: Option[List[String]] = None,
	  /** Arguments to the entrypoint. */
		args: Option[List[String]] = None
	)
	
	case class WorkspaceBinding(
	  /** Optional. SubPath is optionally a directory on the volume which should be used for this binding (i.e. the volume will be mounted at this sub directory). +optional */
		subPath: Option[String] = None,
	  /** Name of the workspace. */
		name: Option[String] = None,
	  /** Secret Volume Source. */
		secret: Option[Schema.SecretVolumeSource] = None
	)
}
