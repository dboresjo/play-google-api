package com.boresjo.play.api.google.cloudfunctions

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

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
	
	object Function {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, FAILED, DEPLOYING, DELETING, UNKNOWN }
		enum EnvironmentEnum extends Enum[EnvironmentEnum] { case ENVIRONMENT_UNSPECIFIED, GEN_1, GEN_2 }
	}
	case class Function(
	  /** A user-defined name of the function. Function names must be unique globally and match pattern `projects/&#42;/locations/&#42;/functions/&#42;` */
		name: Option[String] = None,
	  /** User-provided description of a function. */
		description: Option[String] = None,
	  /** Describes the Build step of the function that builds a container from the given source. */
		buildConfig: Option[Schema.BuildConfig] = None,
	  /** Describes the Service being deployed. Currently deploys services to Cloud Run (fully managed). */
		serviceConfig: Option[Schema.ServiceConfig] = None,
	  /** An Eventarc trigger managed by Google Cloud Functions that fires events in response to a condition in another service. */
		eventTrigger: Option[Schema.EventTrigger] = None,
	  /** Output only. State of the function. */
		state: Option[Schema.Function.StateEnum] = None,
	  /** Output only. The last update timestamp of a Cloud Function. */
		updateTime: Option[String] = None,
	  /** Labels associated with this Cloud Function. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. State Messages for this Cloud Function. */
		stateMessages: Option[List[Schema.GoogleCloudFunctionsV2StateMessage]] = None,
	  /** Describe whether the function is 1st Gen or 2nd Gen. */
		environment: Option[Schema.Function.EnvironmentEnum] = None,
	  /** Output only. UpgradeInfo for this Cloud Function */
		upgradeInfo: Option[Schema.UpgradeInfo] = None,
	  /** Output only. The deployed url for the function. */
		url: Option[String] = None,
	  /** Resource name of a KMS crypto key (managed by the user) used to encrypt/decrypt function resources. It must match the pattern `projects/{project}/locations/{location}/keyRings/{key_ring}/cryptoKeys/{crypto_key}`. */
		kmsKeyName: Option[String] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Output only. The create timestamp of a Cloud Function. This is only applicable to 2nd Gen functions. */
		createTime: Option[String] = None
	)
	
	object BuildConfig {
		enum DockerRegistryEnum extends Enum[DockerRegistryEnum] { case DOCKER_REGISTRY_UNSPECIFIED, CONTAINER_REGISTRY, ARTIFACT_REGISTRY }
	}
	case class BuildConfig(
		automaticUpdatePolicy: Option[Schema.AutomaticUpdatePolicy] = None,
		onDeployUpdatePolicy: Option[Schema.OnDeployUpdatePolicy] = None,
	  /** Output only. The Cloud Build name of the latest successful deployment of the function. */
		build: Option[String] = None,
	  /** The runtime in which to run the function. Required when deploying a new function, optional when updating an existing function. For a complete list of possible choices, see the [`gcloud` command reference](https://cloud.google.com/sdk/gcloud/reference/functions/deploy#--runtime). */
		runtime: Option[String] = None,
	  /** The name of the function (as defined in source code) that will be executed. Defaults to the resource name suffix, if not specified. For backward compatibility, if function with given name is not found, then the system will try to use function named "function". For Node.js this is name of a function exported by the module specified in `source_location`. */
		entryPoint: Option[String] = None,
	  /** The location of the function source code. */
		source: Option[Schema.Source] = None,
	  /** Output only. A permanent fixed identifier for source. */
		sourceProvenance: Option[Schema.SourceProvenance] = None,
	  /** Name of the Cloud Build Custom Worker Pool that should be used to build the function. The format of this field is `projects/{project}/locations/{region}/workerPools/{workerPool}` where {project} and {region} are the project id and region respectively where the worker pool is defined and {workerPool} is the short name of the worker pool. If the project id is not the same as the function, then the Cloud Functions Service Agent (service-@gcf-admin-robot.iam.gserviceaccount.com) must be granted the role Cloud Build Custom Workers Builder (roles/cloudbuild.customworkers.builder) in the project. */
		workerPool: Option[String] = None,
	  /** User-provided build-time environment variables for the function */
		environmentVariables: Option[Map[String, String]] = None,
	  /** Docker Registry to use for this deployment. This configuration is only applicable to 1st Gen functions, 2nd Gen functions can only use Artifact Registry. If unspecified, it defaults to `ARTIFACT_REGISTRY`. If `docker_repository` field is specified, this field should either be left unspecified or set to `ARTIFACT_REGISTRY`. */
		dockerRegistry: Option[Schema.BuildConfig.DockerRegistryEnum] = None,
	  /** Repository in Artifact Registry to which the function docker image will be pushed after it is built by Cloud Build. If specified by user, it is created and managed by user with a customer managed encryption key. Otherwise, GCF will create and use a repository named 'gcf-artifacts' for every deployed region. It must match the pattern `projects/{project}/locations/{location}/repositories/{repository}`. Cross-project repositories are not supported. Cross-location repositories are not supported. Repository format must be 'DOCKER'. */
		dockerRepository: Option[String] = None,
	  /** Service account to be used for building the container. The format of this field is `projects/{projectId}/serviceAccounts/{serviceAccountEmail}`. */
		serviceAccount: Option[String] = None,
	  /** An identifier for Firebase function sources. Disclaimer: This field is only supported for Firebase function deployments. */
		sourceToken: Option[String] = None
	)
	
	case class AutomaticUpdatePolicy(
	
	)
	
	case class OnDeployUpdatePolicy(
	  /** Output only. contains the runtime version which was used during latest function deployment. */
		runtimeVersion: Option[String] = None
	)
	
	case class Source(
	  /** If provided, get the source from this location in Google Cloud Storage. */
		storageSource: Option[Schema.StorageSource] = None,
	  /** If provided, get the source from this location in a Cloud Source Repository. */
		repoSource: Option[Schema.RepoSource] = None,
	  /** If provided, get the source from GitHub repository. This option is valid only for GCF 1st Gen function. Example: https://github.com///blob// */
		gitUri: Option[String] = None
	)
	
	case class StorageSource(
	  /** Google Cloud Storage bucket containing the source (see [Bucket Name Requirements](https://cloud.google.com/storage/docs/bucket-naming#requirements)). */
		bucket: Option[String] = None,
	  /** Google Cloud Storage object containing the source. This object must be a gzipped archive file (`.tar.gz`) containing source to build. */
		`object`: Option[String] = None,
	  /** Google Cloud Storage generation for the object. If the generation is omitted, the latest generation will be used. */
		generation: Option[String] = None,
	  /** When the specified storage bucket is a 1st gen function uploard url bucket, this field should be set as the generated upload url for 1st gen deployment. */
		sourceUploadUrl: Option[String] = None
	)
	
	case class RepoSource(
	  /** Regex matching branches to build. The syntax of the regular expressions accepted is the syntax accepted by RE2 and described at https://github.com/google/re2/wiki/Syntax */
		branchName: Option[String] = None,
	  /** Regex matching tags to build. The syntax of the regular expressions accepted is the syntax accepted by RE2 and described at https://github.com/google/re2/wiki/Syntax */
		tagName: Option[String] = None,
	  /** Explicit commit SHA to build. */
		commitSha: Option[String] = None,
	  /** ID of the project that owns the Cloud Source Repository. If omitted, the project ID requesting the build is assumed. */
		projectId: Option[String] = None,
	  /** Name of the Cloud Source Repository. */
		repoName: Option[String] = None,
	  /** Directory, relative to the source root, in which to run the build. This must be a relative path. If a step's `dir` is specified and is an absolute path, this value is ignored for that step's execution. eg. helloworld (no leading slash allowed) */
		dir: Option[String] = None
	)
	
	case class SourceProvenance(
	  /** A copy of the build's `source.storage_source`, if exists, with any generations resolved. */
		resolvedStorageSource: Option[Schema.StorageSource] = None,
	  /** A copy of the build's `source.repo_source`, if exists, with any revisions resolved. */
		resolvedRepoSource: Option[Schema.RepoSource] = None,
	  /** A copy of the build's `source.git_uri`, if exists, with any commits resolved. */
		gitUri: Option[String] = None
	)
	
	object ServiceConfig {
		enum VpcConnectorEgressSettingsEnum extends Enum[VpcConnectorEgressSettingsEnum] { case VPC_CONNECTOR_EGRESS_SETTINGS_UNSPECIFIED, PRIVATE_RANGES_ONLY, ALL_TRAFFIC }
		enum IngressSettingsEnum extends Enum[IngressSettingsEnum] { case INGRESS_SETTINGS_UNSPECIFIED, ALLOW_ALL, ALLOW_INTERNAL_ONLY, ALLOW_INTERNAL_AND_GCLB }
		enum SecurityLevelEnum extends Enum[SecurityLevelEnum] { case SECURITY_LEVEL_UNSPECIFIED, SECURE_ALWAYS, SECURE_OPTIONAL }
	}
	case class ServiceConfig(
	  /** Output only. Name of the service associated with a Function. The format of this field is `projects/{project}/locations/{region}/services/{service}` */
		service: Option[String] = None,
	  /** The function execution timeout. Execution is considered failed and can be terminated if the function is not completed at the end of the timeout period. Defaults to 60 seconds. */
		timeoutSeconds: Option[Int] = None,
	  /** The amount of memory available for a function. Defaults to 256M. Supported units are k, M, G, Mi, Gi. If no unit is supplied the value is interpreted as bytes. See https://github.com/kubernetes/kubernetes/blob/master/staging/src/k8s.io/apimachinery/pkg/api/resource/quantity.go a full description. */
		availableMemory: Option[String] = None,
	  /** The number of CPUs used in a single container instance. Default value is calculated from available memory. Supports the same values as Cloud Run, see https://cloud.google.com/run/docs/reference/rest/v1/Container#resourcerequirements Example: "1" indicates 1 vCPU */
		availableCpu: Option[String] = None,
	  /** Environment variables that shall be available during function execution. */
		environmentVariables: Option[Map[String, String]] = None,
	  /** The limit on the maximum number of function instances that may coexist at a given time. In some cases, such as rapid traffic surges, Cloud Functions may, for a short period of time, create more instances than the specified max instances limit. If your function cannot tolerate this temporary behavior, you may want to factor in a safety margin and set a lower max instances value than your function can tolerate. See the [Max Instances](https://cloud.google.com/functions/docs/max-instances) Guide for more details. */
		maxInstanceCount: Option[Int] = None,
	  /** The limit on the minimum number of function instances that may coexist at a given time. Function instances are kept in idle state for a short period after they finished executing the request to reduce cold start time for subsequent requests. Setting a minimum instance count will ensure that the given number of instances are kept running in idle state always. This can help with cold start times when jump in incoming request count occurs after the idle instance would have been stopped in the default case. */
		minInstanceCount: Option[Int] = None,
	  /** The Serverless VPC Access connector that this cloud function can connect to. The format of this field is `projects/&#42;/locations/&#42;/connectors/&#42;`. */
		vpcConnector: Option[String] = None,
	  /** The egress settings for the connector, controlling what traffic is diverted through it. */
		vpcConnectorEgressSettings: Option[Schema.ServiceConfig.VpcConnectorEgressSettingsEnum] = None,
	  /** The ingress settings for the function, controlling what traffic can reach it. */
		ingressSettings: Option[Schema.ServiceConfig.IngressSettingsEnum] = None,
	  /** Output only. URI of the Service deployed. */
		uri: Option[String] = None,
	  /** The email of the service's service account. If empty, defaults to `{project_number}-compute@developer.gserviceaccount.com`. */
		serviceAccountEmail: Option[String] = None,
	  /** Whether 100% of traffic is routed to the latest revision. On CreateFunction and UpdateFunction, when set to true, the revision being deployed will serve 100% of traffic, ignoring any traffic split settings, if any. On GetFunction, true will be returned if the latest revision is serving 100% of traffic. */
		allTrafficOnLatestRevision: Option[Boolean] = None,
	  /** Secret environment variables configuration. */
		secretEnvironmentVariables: Option[List[Schema.SecretEnvVar]] = None,
	  /** Secret volumes configuration. */
		secretVolumes: Option[List[Schema.SecretVolume]] = None,
	  /** Output only. The name of service revision. */
		revision: Option[String] = None,
	  /** Sets the maximum number of concurrent requests that each instance can receive. Defaults to 1. */
		maxInstanceRequestConcurrency: Option[Int] = None,
	  /** Security level configure whether the function only accepts https. This configuration is only applicable to 1st Gen functions with Http trigger. By default https is optional for 1st Gen functions; 2nd Gen functions are https ONLY. */
		securityLevel: Option[Schema.ServiceConfig.SecurityLevelEnum] = None,
	  /** Optional. The binary authorization policy to be checked when deploying the Cloud Run service. */
		binaryAuthorizationPolicy: Option[String] = None
	)
	
	case class SecretEnvVar(
	  /** Name of the environment variable. */
		key: Option[String] = None,
	  /** Project identifier (preferably project number but can also be the project ID) of the project that contains the secret. If not set, it is assumed that the secret is in the same project as the function. */
		projectId: Option[String] = None,
	  /** Name of the secret in secret manager (not the full resource name). */
		secret: Option[String] = None,
	  /** Version of the secret (version number or the string 'latest'). It is recommended to use a numeric version for secret environment variables as any updates to the secret value is not reflected until new instances start. */
		version: Option[String] = None
	)
	
	case class SecretVolume(
	  /** The path within the container to mount the secret volume. For example, setting the mount_path as `/etc/secrets` would mount the secret value files under the `/etc/secrets` directory. This directory will also be completely shadowed and unavailable to mount any other secrets. Recommended mount path: /etc/secrets */
		mountPath: Option[String] = None,
	  /** Project identifier (preferably project number but can also be the project ID) of the project that contains the secret. If not set, it is assumed that the secret is in the same project as the function. */
		projectId: Option[String] = None,
	  /** Name of the secret in secret manager (not the full resource name). */
		secret: Option[String] = None,
	  /** List of secret versions to mount for this secret. If empty, the `latest` version of the secret will be made available in a file named after the secret under the mount point. */
		versions: Option[List[Schema.SecretVersion]] = None
	)
	
	case class SecretVersion(
	  /** Version of the secret (version number or the string 'latest'). It is preferable to use `latest` version with secret volumes as secret value changes are reflected immediately. */
		version: Option[String] = None,
	  /** Relative path of the file under the mount path where the secret value for this version will be fetched and made available. For example, setting the mount_path as '/etc/secrets' and path as `secret_foo` would mount the secret value file at `/etc/secrets/secret_foo`. */
		path: Option[String] = None
	)
	
	object EventTrigger {
		enum RetryPolicyEnum extends Enum[RetryPolicyEnum] { case RETRY_POLICY_UNSPECIFIED, RETRY_POLICY_DO_NOT_RETRY, RETRY_POLICY_RETRY }
	}
	case class EventTrigger(
	  /** Output only. The resource name of the Eventarc trigger. The format of this field is `projects/{project}/locations/{region}/triggers/{trigger}`. */
		trigger: Option[String] = None,
	  /** The region that the trigger will be in. The trigger will only receive events originating in this region. It can be the same region as the function, a different region or multi-region, or the global region. If not provided, defaults to the same region as the function. */
		triggerRegion: Option[String] = None,
	  /** Required. The type of event to observe. For example: `google.cloud.audit.log.v1.written` or `google.cloud.pubsub.topic.v1.messagePublished`. */
		eventType: Option[String] = None,
	  /** Criteria used to filter events. */
		eventFilters: Option[List[Schema.EventFilter]] = None,
	  /** Optional. The name of a Pub/Sub topic in the same project that will be used as the transport topic for the event delivery. Format: `projects/{project}/topics/{topic}`. This is only valid for events of type `google.cloud.pubsub.topic.v1.messagePublished`. The topic provided here will not be deleted at function deletion. */
		pubsubTopic: Option[String] = None,
	  /** Optional. The email of the trigger's service account. The service account must have permission to invoke Cloud Run services, the permission is `run.routes.invoke`. If empty, defaults to the Compute Engine default service account: `{project_number}-compute@developer.gserviceaccount.com`. */
		serviceAccountEmail: Option[String] = None,
	  /** Optional. If unset, then defaults to ignoring failures (i.e. not retrying them). */
		retryPolicy: Option[Schema.EventTrigger.RetryPolicyEnum] = None,
	  /** Optional. The name of the channel associated with the trigger in `projects/{project}/locations/{location}/channels/{channel}` format. You must provide a channel to receive events from Eventarc SaaS partners. */
		channel: Option[String] = None,
	  /** Optional. The hostname of the service that 1st Gen function should be observed. If no string is provided, the default service implementing the API will be used. For example, `storage.googleapis.com` is the default for all event types in the `google.storage` namespace. The field is only applicable to 1st Gen functions. */
		service: Option[String] = None
	)
	
	case class EventFilter(
	  /** Required. The name of a CloudEvents attribute. */
		attribute: Option[String] = None,
	  /** Required. The value for the attribute. */
		value: Option[String] = None,
	  /** Optional. The operator used for matching the events with the value of the filter. If not specified, only events that have an exact key-value pair specified in the filter are matched. The only allowed value is `match-path-pattern`. */
		operator: Option[String] = None
	)
	
	object GoogleCloudFunctionsV2StateMessage {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, ERROR, WARNING, INFO }
	}
	case class GoogleCloudFunctionsV2StateMessage(
	  /** Severity of the state message. */
		severity: Option[Schema.GoogleCloudFunctionsV2StateMessage.SeverityEnum] = None,
	  /** One-word CamelCase type of the state message. */
		`type`: Option[String] = None,
	  /** The message. */
		message: Option[String] = None
	)
	
	object UpgradeInfo {
		enum UpgradeStateEnum extends Enum[UpgradeStateEnum] { case UPGRADE_STATE_UNSPECIFIED, ELIGIBLE_FOR_2ND_GEN_UPGRADE, UPGRADE_OPERATION_IN_PROGRESS, SETUP_FUNCTION_UPGRADE_CONFIG_SUCCESSFUL, SETUP_FUNCTION_UPGRADE_CONFIG_ERROR, ABORT_FUNCTION_UPGRADE_ERROR, REDIRECT_FUNCTION_UPGRADE_TRAFFIC_SUCCESSFUL, REDIRECT_FUNCTION_UPGRADE_TRAFFIC_ERROR, ROLLBACK_FUNCTION_UPGRADE_TRAFFIC_ERROR, COMMIT_FUNCTION_UPGRADE_ERROR, DETACH_IN_PROGRESS }
	}
	case class UpgradeInfo(
	  /** UpgradeState of the function */
		upgradeState: Option[Schema.UpgradeInfo.UpgradeStateEnum] = None,
	  /** Describes the Cloud Run service which has been setup to prepare for 2nd gen upgrade. */
		serviceConfig: Option[Schema.ServiceConfig] = None,
	  /** Describes the Event trigger which has been setup to prepare for 2nd gen upgrade. */
		eventTrigger: Option[Schema.EventTrigger] = None,
	  /** Describes the Build step of the function that builds a container to prepare for 2nd gen upgrade. */
		buildConfig: Option[Schema.BuildConfig] = None
	)
	
	case class ListFunctionsResponse(
	  /** The functions that match the request. */
		functions: Option[List[Schema.Function]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. The response does not include any functions from these locations. */
		unreachable: Option[List[String]] = None
	)
	
	case class SetupFunctionUpgradeConfigRequest(
	
	)
	
	case class AbortFunctionUpgradeRequest(
	
	)
	
	case class RedirectFunctionUpgradeTrafficRequest(
	
	)
	
	case class RollbackFunctionUpgradeTrafficRequest(
	
	)
	
	case class CommitFunctionUpgradeRequest(
	
	)
	
	object GenerateUploadUrlRequest {
		enum EnvironmentEnum extends Enum[EnvironmentEnum] { case ENVIRONMENT_UNSPECIFIED, GEN_1, GEN_2 }
	}
	case class GenerateUploadUrlRequest(
	  /** Resource name of a KMS crypto key (managed by the user) used to encrypt/decrypt function source code objects in intermediate Cloud Storage buckets. When you generate an upload url and upload your source code, it gets copied to an intermediate Cloud Storage bucket. The source code is then copied to a versioned directory in the sources bucket in the consumer project during the function deployment. It must match the pattern `projects/{project}/locations/{location}/keyRings/{key_ring}/cryptoKeys/{crypto_key}`. The Google Cloud Functions service account (service-{project_number}@gcf-admin-robot.iam.gserviceaccount.com) must be granted the role 'Cloud KMS CryptoKey Encrypter/Decrypter (roles/cloudkms.cryptoKeyEncrypterDecrypter)' on the Key/KeyRing/Project/Organization (least access preferred). */
		kmsKeyName: Option[String] = None,
	  /** The function environment the generated upload url will be used for. The upload url for 2nd Gen functions can also be used for 1st gen functions, but not vice versa. If not specified, 2nd generation-style upload URLs are generated. */
		environment: Option[Schema.GenerateUploadUrlRequest.EnvironmentEnum] = None
	)
	
	case class GenerateUploadUrlResponse(
	  /** The generated Google Cloud Storage signed URL that should be used for a function source code upload. The uploaded file should be a zip archive which contains a function. */
		uploadUrl: Option[String] = None,
	  /** The location of the source code in the upload bucket. Once the archive is uploaded using the `upload_url` use this field to set the `function.build_config.source.storage_source` during CreateFunction and UpdateFunction. Generation defaults to 0, as Cloud Storage provides a new generation only upon uploading a new object or version of an object. */
		storageSource: Option[Schema.StorageSource] = None
	)
	
	case class GenerateDownloadUrlRequest(
	
	)
	
	case class GenerateDownloadUrlResponse(
	  /** The generated Google Cloud Storage signed URL that should be used for function source code download. */
		downloadUrl: Option[String] = None
	)
	
	case class ListRuntimesResponse(
	  /** The runtimes that match the request. */
		runtimes: Option[List[Schema.Runtime]] = None
	)
	
	object Runtime {
		enum StageEnum extends Enum[StageEnum] { case RUNTIME_STAGE_UNSPECIFIED, DEVELOPMENT, ALPHA, BETA, GA, DEPRECATED, DECOMMISSIONED }
		enum EnvironmentEnum extends Enum[EnvironmentEnum] { case ENVIRONMENT_UNSPECIFIED, GEN_1, GEN_2 }
	}
	case class Runtime(
	  /** The name of the runtime, e.g., 'go113', 'nodejs12', etc. */
		name: Option[String] = None,
	  /** The user facing name, eg 'Go 1.13', 'Node.js 12', etc. */
		displayName: Option[String] = None,
	  /** The stage of life this runtime is in, e.g., BETA, GA, etc. */
		stage: Option[Schema.Runtime.StageEnum] = None,
	  /** Warning messages, e.g., a deprecation warning. */
		warnings: Option[List[String]] = None,
	  /** The environment for the runtime. */
		environment: Option[Schema.Runtime.EnvironmentEnum] = None,
	  /** Deprecation date for the runtime. */
		deprecationDate: Option[Schema.Date] = None,
	  /** Decommission date for the runtime. */
		decommissionDate: Option[Schema.Date] = None
	)
	
	case class Date(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
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
	
	object OperationMetadataV1 {
		enum TypeEnum extends Enum[TypeEnum] { case OPERATION_UNSPECIFIED, CREATE_FUNCTION, UPDATE_FUNCTION, DELETE_FUNCTION }
	}
	case class OperationMetadataV1(
	  /** Target of the operation - for example `projects/project-1/locations/region-1/functions/function-1` */
		target: Option[String] = None,
	  /** Type of operation. */
		`type`: Option[Schema.OperationMetadataV1.TypeEnum] = None,
	  /** The original request that started the operation. */
		request: Option[Map[String, JsValue]] = None,
	  /** Version id of the function created or updated by an API call. This field is only populated for Create and Update operations. */
		versionId: Option[String] = None,
	  /** The last update timestamp of the operation. */
		updateTime: Option[String] = None,
	  /** The Cloud Build ID of the function created or updated by an API call. This field is only populated for Create and Update operations. */
		buildId: Option[String] = None,
	  /** An identifier for Firebase function sources. Disclaimer: This field is only supported for Firebase function deployments. */
		sourceToken: Option[String] = None,
	  /** The Cloud Build Name of the function deployment. This field is only populated for Create and Update operations. `projects//locations//builds/`. */
		buildName: Option[String] = None
	)
	
	object GoogleCloudFunctionsV2OperationMetadata {
		enum OperationTypeEnum extends Enum[OperationTypeEnum] { case OPERATIONTYPE_UNSPECIFIED, CREATE_FUNCTION, UPDATE_FUNCTION, DELETE_FUNCTION, REDIRECT_FUNCTION_UPGRADE_TRAFFIC, ROLLBACK_FUNCTION_UPGRADE_TRAFFIC, SETUP_FUNCTION_UPGRADE_CONFIG, ABORT_FUNCTION_UPGRADE, COMMIT_FUNCTION_UPGRADE }
	}
	case class GoogleCloudFunctionsV2OperationMetadata(
	  /** The time the operation was created. */
		createTime: Option[String] = None,
	  /** The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Human-readable status of the operation, if any. */
		statusDetail: Option[String] = None,
	  /** Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have google.longrunning.Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		cancelRequested: Option[Boolean] = None,
	  /** API version used to start the operation. */
		apiVersion: Option[String] = None,
	  /** The original request that started the operation. */
		requestResource: Option[Map[String, JsValue]] = None,
	  /** Mechanism for reporting in-progress stages */
		stages: Option[List[Schema.GoogleCloudFunctionsV2Stage]] = None,
	  /** An identifier for Firebase function sources. Disclaimer: This field is only supported for Firebase function deployments. */
		sourceToken: Option[String] = None,
	  /** The build name of the function for create and update operations. */
		buildName: Option[String] = None,
	  /** The operation type. */
		operationType: Option[Schema.GoogleCloudFunctionsV2OperationMetadata.OperationTypeEnum] = None
	)
	
	object GoogleCloudFunctionsV2Stage {
		enum NameEnum extends Enum[NameEnum] { case NAME_UNSPECIFIED, ARTIFACT_REGISTRY, BUILD, SERVICE, TRIGGER, SERVICE_ROLLBACK, TRIGGER_ROLLBACK }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, NOT_STARTED, IN_PROGRESS, COMPLETE }
	}
	case class GoogleCloudFunctionsV2Stage(
	  /** Name of the Stage. This will be unique for each Stage. */
		name: Option[Schema.GoogleCloudFunctionsV2Stage.NameEnum] = None,
	  /** Message describing the Stage */
		message: Option[String] = None,
	  /** Current state of the Stage */
		state: Option[Schema.GoogleCloudFunctionsV2Stage.StateEnum] = None,
	  /** Resource of the Stage */
		resource: Option[String] = None,
	  /** Link to the current Stage resource */
		resourceUri: Option[String] = None,
	  /** State messages from the current Stage. */
		stateMessages: Option[List[Schema.GoogleCloudFunctionsV2StateMessage]] = None
	)
	
	object GoogleCloudFunctionsV2LocationMetadata {
		enum EnvironmentsEnum extends Enum[EnvironmentsEnum] { case ENVIRONMENT_UNSPECIFIED, GEN_1, GEN_2 }
	}
	case class GoogleCloudFunctionsV2LocationMetadata(
	  /** The Cloud Function environments this location supports. */
		environments: Option[List[Schema.GoogleCloudFunctionsV2LocationMetadata.EnvironmentsEnum]] = None
	)
}
