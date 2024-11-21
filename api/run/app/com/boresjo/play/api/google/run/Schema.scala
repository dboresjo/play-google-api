package com.boresjo.play.api.google.run

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleCloudRunV2Metadata(
	  /** JSON encoded Google-generated Customer Metadata for a given resource/project. */
		metadata: Option[String] = None
	)
	
	case class GoogleCloudRunV2ExportImageRequest(
	  /** Required. The export destination url (the Artifact Registry repo). */
		destinationRepo: Option[String] = None
	)
	
	case class GoogleCloudRunV2ExportImageResponse(
	  /** An operation ID used to track the status of image exports tied to the original pod ID in the request. */
		operationId: Option[String] = None
	)
	
	object GoogleCloudRunV2ExportStatusResponse {
		enum OperationStateEnum extends Enum[OperationStateEnum] { case OPERATION_STATE_UNSPECIFIED, IN_PROGRESS, FINISHED }
	}
	case class GoogleCloudRunV2ExportStatusResponse(
	  /** The operation id. */
		operationId: Option[String] = None,
	  /** Output only. The state of the overall export operation. */
		operationState: Option[Schema.GoogleCloudRunV2ExportStatusResponse.OperationStateEnum] = None,
	  /** The status of each image export job. */
		imageExportStatuses: Option[List[Schema.GoogleCloudRunV2ImageExportStatus]] = None
	)
	
	object GoogleCloudRunV2ImageExportStatus {
		enum ExportJobStateEnum extends Enum[ExportJobStateEnum] { case EXPORT_JOB_STATE_UNSPECIFIED, IN_PROGRESS, FINISHED }
	}
	case class GoogleCloudRunV2ImageExportStatus(
	  /** Output only. Has the image export job finished (regardless of successful or failure). */
		exportJobState: Option[Schema.GoogleCloudRunV2ImageExportStatus.ExportJobStateEnum] = None,
	  /** The status of the export task if done. */
		status: Option[Schema.UtilStatusProto] = None,
	  /** The exported image ID as it will appear in Artifact Registry. */
		exportedImageDigest: Option[String] = None,
	  /** The image tag as it will appear in Artifact Registry. */
		tag: Option[String] = None
	)
	
	case class UtilStatusProto(
	  /** Numeric code drawn from the space specified below. Often, this is the canonical error space, and code is drawn from google3/util/task/codes.proto */
		code: Option[Int] = None,
	  /** The following are usually only present when code != 0 Space to which this status belongs */
		space: Option[String] = None,
	  /** Detail message */
		message: Option[String] = None,
	  /** The canonical error code (see codes.proto) that most closely corresponds to this status. This may be missing, and in the common case of the generic space, it definitely will be. */
		canonicalCode: Option[Int] = None,
	  /** message_set associates an arbitrary proto message with the status. */
		messageSet: Option[Schema.Proto2BridgeMessageSet] = None
	)
	
	case class Proto2BridgeMessageSet(
	
	)
	
	case class GoogleLongrunningListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.GoogleLongrunningOperation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleLongrunningOperation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleRpcStatus(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleLongrunningWaitOperationRequest(
	  /** The maximum duration to wait before timing out. If left blank, the wait will be at most the time permitted by the underlying HTTP/RPC protocol. If RPC context deadline is also specified, the shorter one will be used. */
		timeout: Option[String] = None
	)
	
	case class GoogleCloudRunV2SubmitBuildRequest(
	  /** Required. Source for the build. */
		storageSource: Option[Schema.GoogleCloudRunV2StorageSource] = None,
	  /** Required. Artifact Registry URI to store the built image. */
		imageUri: Option[String] = None,
	  /** Build the source using Buildpacks. */
		buildpackBuild: Option[Schema.GoogleCloudRunV2BuildpacksBuild] = None,
	  /** Build the source using Docker. This means the source has a Dockerfile. */
		dockerBuild: Option[Schema.GoogleCloudRunV2DockerBuild] = None,
	  /** Optional. The service account to use for the build. If not set, the default Cloud Build service account for the project will be used. */
		serviceAccount: Option[String] = None,
	  /** Optional. Name of the Cloud Build Custom Worker Pool that should be used to build the function. The format of this field is `projects/{project}/locations/{region}/workerPools/{workerPool}` where `{project}` and `{region}` are the project id and region respectively where the worker pool is defined and `{workerPool}` is the short name of the worker pool. */
		workerPool: Option[String] = None,
	  /** Optional. Additional tags to annotate the build. */
		tags: Option[List[String]] = None
	)
	
	case class GoogleCloudRunV2StorageSource(
	  /** Required. Google Cloud Storage bucket containing the source (see [Bucket Name Requirements](https://cloud.google.com/storage/docs/bucket-naming#requirements)). */
		bucket: Option[String] = None,
	  /** Required. Google Cloud Storage object containing the source. This object must be a gzipped archive file (`.tar.gz`) containing source to build. */
		`object`: Option[String] = None,
	  /** Optional. Google Cloud Storage generation for the object. If the generation is omitted, the latest generation will be used. */
		generation: Option[String] = None
	)
	
	case class GoogleCloudRunV2BuildpacksBuild(
	  /** The runtime name, e.g. 'go113'. Leave blank for generic builds. */
		runtime: Option[String] = None,
	  /** Optional. Name of the function target if the source is a function source. Required for function builds. */
		functionTarget: Option[String] = None,
	  /** Optional. cache_image_uri is the GCR/AR URL where the cache image will be stored. cache_image_uri is optional and omitting it will disable caching. This URL must be stable across builds. It is used to derive a build-specific temporary URL by substituting the tag with the build ID. The build will clean up the temporary image on a best-effort basis. */
		cacheImageUri: Option[String] = None,
	  /** Optional. The base image used to opt into automatic base image updates. */
		baseImage: Option[String] = None,
	  /** Optional. User-provided build-time environment variables. */
		environmentVariables: Option[Map[String, String]] = None,
	  /** Optional. Whether or not the application container will be enrolled in automatic base image updates. When true, the application will be built on a scratch base image, so the base layers can be appended at run time. */
		enableAutomaticUpdates: Option[Boolean] = None
	)
	
	case class GoogleCloudRunV2DockerBuild(
	
	)
	
	case class GoogleCloudRunV2SubmitBuildResponse(
	  /** Cloud Build operation to be polled via CloudBuild API. */
		buildOperation: Option[Schema.GoogleLongrunningOperation] = None,
	  /** URI of the base builder image in Artifact Registry being used in the build. Used to opt into automatic base image updates. */
		baseImageUri: Option[String] = None,
	  /** Warning message for the base image. */
		baseImageWarning: Option[String] = None
	)
	
	object GoogleCloudRunV2Execution {
		enum LaunchStageEnum extends Enum[LaunchStageEnum] { case LAUNCH_STAGE_UNSPECIFIED, UNIMPLEMENTED, PRELAUNCH, EARLY_ACCESS, ALPHA, BETA, GA, DEPRECATED }
	}
	case class GoogleCloudRunV2Execution(
	  /** Output only. The unique name of this Execution. */
		name: Option[String] = None,
	  /** Output only. Server assigned unique identifier for the Execution. The value is a UUID4 string and guaranteed to remain unchanged until the resource is deleted. */
		uid: Option[String] = None,
	  /** Output only. A number that monotonically increases every time the user modifies the desired state. */
		generation: Option[String] = None,
	  /** Output only. Unstructured key value map that can be used to organize and categorize objects. User-provided labels are shared with Google's billing system, so they can be used to filter, or break down billing charges by team, component, environment, state, etc. For more information, visit https://cloud.google.com/resource-manager/docs/creating-managing-labels or https://cloud.google.com/run/docs/configuring/labels */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Unstructured key value map that may be set by external tools to store and arbitrary metadata. They are not queryable and should be preserved when modifying objects. */
		annotations: Option[Map[String, String]] = None,
	  /** Output only. Represents time when the execution was acknowledged by the execution controller. It is not guaranteed to be set in happens-before order across separate operations. */
		createTime: Option[String] = None,
	  /** Output only. Represents time when the execution started to run. It is not guaranteed to be set in happens-before order across separate operations. */
		startTime: Option[String] = None,
	  /** Output only. Represents time when the execution was completed. It is not guaranteed to be set in happens-before order across separate operations. */
		completionTime: Option[String] = None,
	  /** Output only. The last-modified time. */
		updateTime: Option[String] = None,
	  /** Output only. For a deleted resource, the deletion time. It is only populated as a response to a Delete request. */
		deleteTime: Option[String] = None,
	  /** Output only. For a deleted resource, the time after which it will be permamently deleted. It is only populated as a response to a Delete request. */
		expireTime: Option[String] = None,
	  /** The least stable launch stage needed to create this resource, as defined by [Google Cloud Platform Launch Stages](https://cloud.google.com/terms/launch-stages). Cloud Run supports `ALPHA`, `BETA`, and `GA`. Note that this value might not be what was used as input. For example, if ALPHA was provided as input in the parent resource, but only BETA and GA-level features are were, this field will be BETA. */
		launchStage: Option[Schema.GoogleCloudRunV2Execution.LaunchStageEnum] = None,
	  /** Output only. The name of the parent Job. */
		job: Option[String] = None,
	  /** Output only. Specifies the maximum desired number of tasks the execution should run at any given time. Must be <= task_count. The actual number of tasks running in steady state will be less than this number when ((.spec.task_count - .status.successful) < .spec.parallelism), i.e. when the work left to do is less than max parallelism. */
		parallelism: Option[Int] = None,
	  /** Output only. Specifies the desired number of tasks the execution should run. Setting to 1 means that parallelism is limited to 1 and the success of that task signals the success of the execution. */
		taskCount: Option[Int] = None,
	  /** Output only. The template used to create tasks for this execution. */
		template: Option[Schema.GoogleCloudRunV2TaskTemplate] = None,
	  /** Output only. Indicates whether the resource's reconciliation is still in progress. See comments in `Job.reconciling` for additional information on reconciliation process in Cloud Run. */
		reconciling: Option[Boolean] = None,
	  /** Output only. The Condition of this Execution, containing its readiness status, and detailed error information in case it did not reach the desired state. */
		conditions: Option[List[Schema.GoogleCloudRunV2Condition]] = None,
	  /** Output only. The generation of this Execution. See comments in `reconciling` for additional information on reconciliation process in Cloud Run. */
		observedGeneration: Option[String] = None,
	  /** Output only. The number of actively running tasks. */
		runningCount: Option[Int] = None,
	  /** Output only. The number of tasks which reached phase Succeeded. */
		succeededCount: Option[Int] = None,
	  /** Output only. The number of tasks which reached phase Failed. */
		failedCount: Option[Int] = None,
	  /** Output only. The number of tasks which reached phase Cancelled. */
		cancelledCount: Option[Int] = None,
	  /** Output only. The number of tasks which have retried at least once. */
		retriedCount: Option[Int] = None,
	  /** Output only. URI where logs for this execution can be found in Cloud Console. */
		logUri: Option[String] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Output only. A system-generated fingerprint for this version of the resource. May be used to detect modification conflict during updates. */
		etag: Option[String] = None
	)
	
	object GoogleCloudRunV2TaskTemplate {
		enum ExecutionEnvironmentEnum extends Enum[ExecutionEnvironmentEnum] { case EXECUTION_ENVIRONMENT_UNSPECIFIED, EXECUTION_ENVIRONMENT_GEN1, EXECUTION_ENVIRONMENT_GEN2 }
	}
	case class GoogleCloudRunV2TaskTemplate(
	  /** Holds the single container that defines the unit of execution for this task. */
		containers: Option[List[Schema.GoogleCloudRunV2Container]] = None,
	  /** Optional. A list of Volumes to make available to containers. */
		volumes: Option[List[Schema.GoogleCloudRunV2Volume]] = None,
	  /** Number of retries allowed per Task, before marking this Task failed. Defaults to 3. */
		maxRetries: Option[Int] = None,
	  /** Optional. Max allowed time duration the Task may be active before the system will actively try to mark it failed and kill associated containers. This applies per attempt of a task, meaning each retry can run for the full timeout. Defaults to 600 seconds. */
		timeout: Option[String] = None,
	  /** Optional. Email address of the IAM service account associated with the Task of a Job. The service account represents the identity of the running task, and determines what permissions the task has. If not provided, the task will use the project's default service account. */
		serviceAccount: Option[String] = None,
	  /** Optional. The execution environment being used to host this Task. */
		executionEnvironment: Option[Schema.GoogleCloudRunV2TaskTemplate.ExecutionEnvironmentEnum] = None,
	  /** A reference to a customer managed encryption key (CMEK) to use to encrypt this container image. For more information, go to https://cloud.google.com/run/docs/securing/using-cmek */
		encryptionKey: Option[String] = None,
	  /** Optional. VPC Access configuration to use for this Task. For more information, visit https://cloud.google.com/run/docs/configuring/connecting-vpc. */
		vpcAccess: Option[Schema.GoogleCloudRunV2VpcAccess] = None
	)
	
	case class GoogleCloudRunV2Container(
	  /** Name of the container specified as a DNS_LABEL (RFC 1123). */
		name: Option[String] = None,
	  /** Required. Name of the container image in Dockerhub, Google Artifact Registry, or Google Container Registry. If the host is not provided, Dockerhub is assumed. */
		image: Option[String] = None,
	  /** Entrypoint array. Not executed within a shell. The docker image's ENTRYPOINT is used if this is not provided. */
		command: Option[List[String]] = None,
	  /** Arguments to the entrypoint. The docker image's CMD is used if this is not provided. */
		args: Option[List[String]] = None,
	  /** List of environment variables to set in the container. */
		env: Option[List[Schema.GoogleCloudRunV2EnvVar]] = None,
	  /** Compute Resource requirements by this container. */
		resources: Option[Schema.GoogleCloudRunV2ResourceRequirements] = None,
	  /** List of ports to expose from the container. Only a single port can be specified. The specified ports must be listening on all interfaces (0.0.0.0) within the container to be accessible. If omitted, a port number will be chosen and passed to the container through the PORT environment variable for the container to listen on. */
		ports: Option[List[Schema.GoogleCloudRunV2ContainerPort]] = None,
	  /** Volume to mount into the container's filesystem. */
		volumeMounts: Option[List[Schema.GoogleCloudRunV2VolumeMount]] = None,
	  /** Container's working directory. If not specified, the container runtime's default will be used, which might be configured in the container image. */
		workingDir: Option[String] = None,
	  /** Periodic probe of container liveness. Container will be restarted if the probe fails. */
		livenessProbe: Option[Schema.GoogleCloudRunV2Probe] = None,
	  /** Startup probe of application within the container. All other probes are disabled if a startup probe is provided, until it succeeds. Container will not be added to service endpoints if the probe fails. */
		startupProbe: Option[Schema.GoogleCloudRunV2Probe] = None,
	  /** Names of the containers that must start before this container. */
		dependsOn: Option[List[String]] = None
	)
	
	case class GoogleCloudRunV2EnvVar(
	  /** Required. Name of the environment variable. Must not exceed 32768 characters. */
		name: Option[String] = None,
	  /** Literal value of the environment variable. Defaults to "", and the maximum length is 32768 bytes. Variable references are not supported in Cloud Run. */
		value: Option[String] = None,
	  /** Source for the environment variable's value. */
		valueSource: Option[Schema.GoogleCloudRunV2EnvVarSource] = None
	)
	
	case class GoogleCloudRunV2EnvVarSource(
	  /** Selects a secret and a specific version from Cloud Secret Manager. */
		secretKeyRef: Option[Schema.GoogleCloudRunV2SecretKeySelector] = None
	)
	
	case class GoogleCloudRunV2SecretKeySelector(
	  /** Required. The name of the secret in Cloud Secret Manager. Format: {secret_name} if the secret is in the same project. projects/{project}/secrets/{secret_name} if the secret is in a different project. */
		secret: Option[String] = None,
	  /** The Cloud Secret Manager secret version. Can be 'latest' for the latest version, an integer for a specific version, or a version alias. */
		version: Option[String] = None
	)
	
	case class GoogleCloudRunV2ResourceRequirements(
	  /** Only `memory` and `cpu` keys in the map are supported. Notes: &#42; The only supported values for CPU are '1', '2', '4', and '8'. Setting 4 CPU requires at least 2Gi of memory. For more information, go to https://cloud.google.com/run/docs/configuring/cpu. &#42; For supported 'memory' values and syntax, go to https://cloud.google.com/run/docs/configuring/memory-limits */
		limits: Option[Map[String, String]] = None,
	  /** Determines whether CPU is only allocated during requests (true by default). However, if ResourceRequirements is set, the caller must explicitly set this field to true to preserve the default behavior. */
		cpuIdle: Option[Boolean] = None,
	  /** Determines whether CPU should be boosted on startup of a new container instance above the requested CPU threshold, this can help reduce cold-start latency. */
		startupCpuBoost: Option[Boolean] = None
	)
	
	case class GoogleCloudRunV2ContainerPort(
	  /** If specified, used to specify which protocol to use. Allowed values are "http1" and "h2c". */
		name: Option[String] = None,
	  /** Port number the container listens on. This must be a valid TCP port number, 0 < container_port < 65536. */
		containerPort: Option[Int] = None
	)
	
	case class GoogleCloudRunV2VolumeMount(
	  /** Required. This must match the Name of a Volume. */
		name: Option[String] = None,
	  /** Required. Path within the container at which the volume should be mounted. Must not contain ':'. For Cloud SQL volumes, it can be left empty, or must otherwise be `/cloudsql`. All instances defined in the Volume will be available as `/cloudsql/[instance]`. For more information on Cloud SQL volumes, visit https://cloud.google.com/sql/docs/mysql/connect-run */
		mountPath: Option[String] = None
	)
	
	case class GoogleCloudRunV2Probe(
	  /** Optional. Number of seconds after the container has started before the probe is initiated. Defaults to 0 seconds. Minimum value is 0. Maximum value for liveness probe is 3600. Maximum value for startup probe is 240. */
		initialDelaySeconds: Option[Int] = None,
	  /** Optional. Number of seconds after which the probe times out. Defaults to 1 second. Minimum value is 1. Maximum value is 3600. Must be smaller than period_seconds. */
		timeoutSeconds: Option[Int] = None,
	  /** Optional. How often (in seconds) to perform the probe. Default to 10 seconds. Minimum value is 1. Maximum value for liveness probe is 3600. Maximum value for startup probe is 240. Must be greater or equal than timeout_seconds. */
		periodSeconds: Option[Int] = None,
	  /** Optional. Minimum consecutive failures for the probe to be considered failed after having succeeded. Defaults to 3. Minimum value is 1. */
		failureThreshold: Option[Int] = None,
	  /** Optional. HTTPGet specifies the http request to perform. Exactly one of httpGet, tcpSocket, or grpc must be specified. */
		httpGet: Option[Schema.GoogleCloudRunV2HTTPGetAction] = None,
	  /** Optional. TCPSocket specifies an action involving a TCP port. Exactly one of httpGet, tcpSocket, or grpc must be specified. */
		tcpSocket: Option[Schema.GoogleCloudRunV2TCPSocketAction] = None,
	  /** Optional. GRPC specifies an action involving a gRPC port. Exactly one of httpGet, tcpSocket, or grpc must be specified. */
		grpc: Option[Schema.GoogleCloudRunV2GRPCAction] = None
	)
	
	case class GoogleCloudRunV2HTTPGetAction(
	  /** Optional. Path to access on the HTTP server. Defaults to '/'. */
		path: Option[String] = None,
	  /** Optional. Custom headers to set in the request. HTTP allows repeated headers. */
		httpHeaders: Option[List[Schema.GoogleCloudRunV2HTTPHeader]] = None,
	  /** Optional. Port number to access on the container. Must be in the range 1 to 65535. If not specified, defaults to the exposed port of the container, which is the value of container.ports[0].containerPort. */
		port: Option[Int] = None
	)
	
	case class GoogleCloudRunV2HTTPHeader(
	  /** Required. The header field name */
		name: Option[String] = None,
	  /** Optional. The header field value */
		value: Option[String] = None
	)
	
	case class GoogleCloudRunV2TCPSocketAction(
	  /** Optional. Port number to access on the container. Must be in the range 1 to 65535. If not specified, defaults to the exposed port of the container, which is the value of container.ports[0].containerPort. */
		port: Option[Int] = None
	)
	
	case class GoogleCloudRunV2GRPCAction(
	  /** Optional. Port number of the gRPC service. Number must be in the range 1 to 65535. If not specified, defaults to the exposed port of the container, which is the value of container.ports[0].containerPort. */
		port: Option[Int] = None,
	  /** Optional. Service is the name of the service to place in the gRPC HealthCheckRequest (see https://github.com/grpc/grpc/blob/master/doc/health-checking.md ). If this is not specified, the default behavior is defined by gRPC. */
		service: Option[String] = None
	)
	
	case class GoogleCloudRunV2Volume(
	  /** Required. Volume's name. */
		name: Option[String] = None,
	  /** Secret represents a secret that should populate this volume. */
		secret: Option[Schema.GoogleCloudRunV2SecretVolumeSource] = None,
	  /** For Cloud SQL volumes, contains the specific instances that should be mounted. Visit https://cloud.google.com/sql/docs/mysql/connect-run for more information on how to connect Cloud SQL and Cloud Run. */
		cloudSqlInstance: Option[Schema.GoogleCloudRunV2CloudSqlInstance] = None,
	  /** Ephemeral storage used as a shared volume. */
		emptyDir: Option[Schema.GoogleCloudRunV2EmptyDirVolumeSource] = None,
	  /** For NFS Voumes, contains the path to the nfs Volume */
		nfs: Option[Schema.GoogleCloudRunV2NFSVolumeSource] = None,
	  /** Persistent storage backed by a Google Cloud Storage bucket. */
		gcs: Option[Schema.GoogleCloudRunV2GCSVolumeSource] = None
	)
	
	case class GoogleCloudRunV2SecretVolumeSource(
	  /** Required. The name of the secret in Cloud Secret Manager. Format: {secret} if the secret is in the same project. projects/{project}/secrets/{secret} if the secret is in a different project. */
		secret: Option[String] = None,
	  /** If unspecified, the volume will expose a file whose name is the secret, relative to VolumeMount.mount_path. If specified, the key will be used as the version to fetch from Cloud Secret Manager and the path will be the name of the file exposed in the volume. When items are defined, they must specify a path and a version. */
		items: Option[List[Schema.GoogleCloudRunV2VersionToPath]] = None,
	  /** Integer representation of mode bits to use on created files by default. Must be a value between 0000 and 0777 (octal), defaulting to 0444. Directories within the path are not affected by this setting. Notes &#42; Internally, a umask of 0222 will be applied to any non-zero value. &#42; This is an integer representation of the mode bits. So, the octal integer value should look exactly as the chmod numeric notation with a leading zero. Some examples: for chmod 777 (a=rwx), set to 0777 (octal) or 511 (base-10). For chmod 640 (u=rw,g=r), set to 0640 (octal) or 416 (base-10). For chmod 755 (u=rwx,g=rx,o=rx), set to 0755 (octal) or 493 (base-10). &#42; This might be in conflict with other options that affect the file mode, like fsGroup, and the result can be other mode bits set. This might be in conflict with other options that affect the file mode, like fsGroup, and as a result, other mode bits could be set. */
		defaultMode: Option[Int] = None
	)
	
	case class GoogleCloudRunV2VersionToPath(
	  /** Required. The relative path of the secret in the container. */
		path: Option[String] = None,
	  /** The Cloud Secret Manager secret version. Can be 'latest' for the latest value, or an integer or a secret alias for a specific version. */
		version: Option[String] = None,
	  /** Integer octal mode bits to use on this file, must be a value between 01 and 0777 (octal). If 0 or not set, the Volume's default mode will be used. Notes &#42; Internally, a umask of 0222 will be applied to any non-zero value. &#42; This is an integer representation of the mode bits. So, the octal integer value should look exactly as the chmod numeric notation with a leading zero. Some examples: for chmod 777 (a=rwx), set to 0777 (octal) or 511 (base-10). For chmod 640 (u=rw,g=r), set to 0640 (octal) or 416 (base-10). For chmod 755 (u=rwx,g=rx,o=rx), set to 0755 (octal) or 493 (base-10). &#42; This might be in conflict with other options that affect the file mode, like fsGroup, and the result can be other mode bits set. */
		mode: Option[Int] = None
	)
	
	case class GoogleCloudRunV2CloudSqlInstance(
	  /** The Cloud SQL instance connection names, as can be found in https://console.cloud.google.com/sql/instances. Visit https://cloud.google.com/sql/docs/mysql/connect-run for more information on how to connect Cloud SQL and Cloud Run. Format: {project}:{location}:{instance} */
		instances: Option[List[String]] = None
	)
	
	object GoogleCloudRunV2EmptyDirVolumeSource {
		enum MediumEnum extends Enum[MediumEnum] { case MEDIUM_UNSPECIFIED, MEMORY }
	}
	case class GoogleCloudRunV2EmptyDirVolumeSource(
	  /** The medium on which the data is stored. Acceptable values today is only MEMORY or none. When none, the default will currently be backed by memory but could change over time. +optional */
		medium: Option[Schema.GoogleCloudRunV2EmptyDirVolumeSource.MediumEnum] = None,
	  /** Limit on the storage usable by this EmptyDir volume. The size limit is also applicable for memory medium. The maximum usage on memory medium EmptyDir would be the minimum value between the SizeLimit specified here and the sum of memory limits of all containers. The default is nil which means that the limit is undefined. More info: https://cloud.google.com/run/docs/configuring/in-memory-volumes#configure-volume. Info in Kubernetes: https://kubernetes.io/docs/concepts/storage/volumes/#emptydir */
		sizeLimit: Option[String] = None
	)
	
	case class GoogleCloudRunV2NFSVolumeSource(
	  /** Hostname or IP address of the NFS server */
		server: Option[String] = None,
	  /** Path that is exported by the NFS server. */
		path: Option[String] = None,
	  /** If true, the volume will be mounted as read only for all mounts. */
		readOnly: Option[Boolean] = None
	)
	
	case class GoogleCloudRunV2GCSVolumeSource(
	  /** Cloud Storage Bucket name. */
		bucket: Option[String] = None,
	  /** If true, the volume will be mounted as read only for all mounts. */
		readOnly: Option[Boolean] = None,
	  /** A list of additional flags to pass to the gcsfuse CLI. Options should be specified without the leading "--". */
		mountOptions: Option[List[String]] = None
	)
	
	object GoogleCloudRunV2VpcAccess {
		enum EgressEnum extends Enum[EgressEnum] { case VPC_EGRESS_UNSPECIFIED, ALL_TRAFFIC, PRIVATE_RANGES_ONLY }
	}
	case class GoogleCloudRunV2VpcAccess(
	  /** VPC Access connector name. Format: `projects/{project}/locations/{location}/connectors/{connector}`, where `{project}` can be project id or number. For more information on sending traffic to a VPC network via a connector, visit https://cloud.google.com/run/docs/configuring/vpc-connectors. */
		connector: Option[String] = None,
	  /** Optional. Traffic VPC egress settings. If not provided, it defaults to PRIVATE_RANGES_ONLY. */
		egress: Option[Schema.GoogleCloudRunV2VpcAccess.EgressEnum] = None,
	  /** Optional. Direct VPC egress settings. Currently only single network interface is supported. */
		networkInterfaces: Option[List[Schema.GoogleCloudRunV2NetworkInterface]] = None
	)
	
	case class GoogleCloudRunV2NetworkInterface(
	  /** Optional. The VPC network that the Cloud Run resource will be able to send traffic to. At least one of network or subnetwork must be specified. If both network and subnetwork are specified, the given VPC subnetwork must belong to the given VPC network. If network is not specified, it will be looked up from the subnetwork. */
		network: Option[String] = None,
	  /** Optional. The VPC subnetwork that the Cloud Run resource will get IPs from. At least one of network or subnetwork must be specified. If both network and subnetwork are specified, the given VPC subnetwork must belong to the given VPC network. If subnetwork is not specified, the subnetwork with the same name with the network will be used. */
		subnetwork: Option[String] = None,
	  /** Optional. Network tags applied to this Cloud Run resource. */
		tags: Option[List[String]] = None
	)
	
	object GoogleCloudRunV2Condition {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CONDITION_PENDING, CONDITION_RECONCILING, CONDITION_FAILED, CONDITION_SUCCEEDED }
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, ERROR, WARNING, INFO }
		enum ReasonEnum extends Enum[ReasonEnum] { case COMMON_REASON_UNDEFINED, UNKNOWN, REVISION_FAILED, PROGRESS_DEADLINE_EXCEEDED, CONTAINER_MISSING, CONTAINER_PERMISSION_DENIED, CONTAINER_IMAGE_UNAUTHORIZED, CONTAINER_IMAGE_AUTHORIZATION_CHECK_FAILED, ENCRYPTION_KEY_PERMISSION_DENIED, ENCRYPTION_KEY_CHECK_FAILED, SECRETS_ACCESS_CHECK_FAILED, WAITING_FOR_OPERATION, IMMEDIATE_RETRY, POSTPONED_RETRY, INTERNAL }
		enum RevisionReasonEnum extends Enum[RevisionReasonEnum] { case REVISION_REASON_UNDEFINED, PENDING, RESERVE, RETIRED, RETIRING, RECREATING, HEALTH_CHECK_CONTAINER_ERROR, CUSTOMIZED_PATH_RESPONSE_PENDING, MIN_INSTANCES_NOT_PROVISIONED, ACTIVE_REVISION_LIMIT_REACHED, NO_DEPLOYMENT, HEALTH_CHECK_SKIPPED, MIN_INSTANCES_WARMING }
		enum ExecutionReasonEnum extends Enum[ExecutionReasonEnum] { case EXECUTION_REASON_UNDEFINED, JOB_STATUS_SERVICE_POLLING_ERROR, NON_ZERO_EXIT_CODE, CANCELLED, CANCELLING, DELETED }
	}
	case class GoogleCloudRunV2Condition(
	  /** type is used to communicate the status of the reconciliation process. See also: https://github.com/knative/serving/blob/main/docs/spec/errors.md#error-conditions-and-reporting Types common to all resources include: &#42; "Ready": True when the Resource is ready. */
		`type`: Option[String] = None,
	  /** State of the condition. */
		state: Option[Schema.GoogleCloudRunV2Condition.StateEnum] = None,
	  /** Human readable message indicating details about the current status. */
		message: Option[String] = None,
	  /** Last time the condition transitioned from one status to another. */
		lastTransitionTime: Option[String] = None,
	  /** How to interpret failures of this condition, one of Error, Warning, Info */
		severity: Option[Schema.GoogleCloudRunV2Condition.SeverityEnum] = None,
	  /** Output only. A common (service-level) reason for this condition. */
		reason: Option[Schema.GoogleCloudRunV2Condition.ReasonEnum] = None,
	  /** Output only. A reason for the revision condition. */
		revisionReason: Option[Schema.GoogleCloudRunV2Condition.RevisionReasonEnum] = None,
	  /** Output only. A reason for the execution condition. */
		executionReason: Option[Schema.GoogleCloudRunV2Condition.ExecutionReasonEnum] = None
	)
	
	case class GoogleCloudRunV2ListExecutionsResponse(
	  /** The resulting list of Executions. */
		executions: Option[List[Schema.GoogleCloudRunV2Execution]] = None,
	  /** A token indicating there are more items than page_size. Use it in the next ListExecutions request to continue. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudRunV2CancelExecutionRequest(
	  /** Indicates that the request should be validated without actually cancelling any resources. */
		validateOnly: Option[Boolean] = None,
	  /** A system-generated fingerprint for this version of the resource. This may be used to detect modification conflict during updates. */
		etag: Option[String] = None
	)
	
	object GoogleCloudRunV2Job {
		enum LaunchStageEnum extends Enum[LaunchStageEnum] { case LAUNCH_STAGE_UNSPECIFIED, UNIMPLEMENTED, PRELAUNCH, EARLY_ACCESS, ALPHA, BETA, GA, DEPRECATED }
	}
	case class GoogleCloudRunV2Job(
	  /** The fully qualified name of this Job. Format: projects/{project}/locations/{location}/jobs/{job} */
		name: Option[String] = None,
	  /** Output only. Server assigned unique identifier for the Execution. The value is a UUID4 string and guaranteed to remain unchanged until the resource is deleted. */
		uid: Option[String] = None,
	  /** Output only. A number that monotonically increases every time the user modifies the desired state. */
		generation: Option[String] = None,
	  /** Unstructured key value map that can be used to organize and categorize objects. User-provided labels are shared with Google's billing system, so they can be used to filter, or break down billing charges by team, component, environment, state, etc. For more information, visit https://cloud.google.com/resource-manager/docs/creating-managing-labels or https://cloud.google.com/run/docs/configuring/labels. Cloud Run API v2 does not support labels with `run.googleapis.com`, `cloud.googleapis.com`, `serving.knative.dev`, or `autoscaling.knative.dev` namespaces, and they will be rejected. All system labels in v1 now have a corresponding field in v2 Job. */
		labels: Option[Map[String, String]] = None,
	  /** Unstructured key value map that may be set by external tools to store and arbitrary metadata. They are not queryable and should be preserved when modifying objects. Cloud Run API v2 does not support annotations with `run.googleapis.com`, `cloud.googleapis.com`, `serving.knative.dev`, or `autoscaling.knative.dev` namespaces, and they will be rejected on new resources. All system annotations in v1 now have a corresponding field in v2 Job. This field follows Kubernetes annotations' namespacing, limits, and rules. */
		annotations: Option[Map[String, String]] = None,
	  /** Output only. The creation time. */
		createTime: Option[String] = None,
	  /** Output only. The last-modified time. */
		updateTime: Option[String] = None,
	  /** Output only. The deletion time. It is only populated as a response to a Delete request. */
		deleteTime: Option[String] = None,
	  /** Output only. For a deleted resource, the time after which it will be permamently deleted. */
		expireTime: Option[String] = None,
	  /** Output only. Email address of the authenticated creator. */
		creator: Option[String] = None,
	  /** Output only. Email address of the last authenticated modifier. */
		lastModifier: Option[String] = None,
	  /** Arbitrary identifier for the API client. */
		client: Option[String] = None,
	  /** Arbitrary version identifier for the API client. */
		clientVersion: Option[String] = None,
	  /** The launch stage as defined by [Google Cloud Platform Launch Stages](https://cloud.google.com/terms/launch-stages). Cloud Run supports `ALPHA`, `BETA`, and `GA`. If no value is specified, GA is assumed. Set the launch stage to a preview stage on input to allow use of preview features in that stage. On read (or output), describes whether the resource uses preview features. For example, if ALPHA is provided as input, but only BETA and GA-level features are used, this field will be BETA on output. */
		launchStage: Option[Schema.GoogleCloudRunV2Job.LaunchStageEnum] = None,
	  /** Settings for the Binary Authorization feature. */
		binaryAuthorization: Option[Schema.GoogleCloudRunV2BinaryAuthorization] = None,
	  /** Required. The template used to create executions for this Job. */
		template: Option[Schema.GoogleCloudRunV2ExecutionTemplate] = None,
	  /** Output only. The generation of this Job. See comments in `reconciling` for additional information on reconciliation process in Cloud Run. */
		observedGeneration: Option[String] = None,
	  /** Output only. The Condition of this Job, containing its readiness status, and detailed error information in case it did not reach the desired state. */
		terminalCondition: Option[Schema.GoogleCloudRunV2Condition] = None,
	  /** Output only. The Conditions of all other associated sub-resources. They contain additional diagnostics information in case the Job does not reach its desired state. See comments in `reconciling` for additional information on reconciliation process in Cloud Run. */
		conditions: Option[List[Schema.GoogleCloudRunV2Condition]] = None,
	  /** Output only. Number of executions created for this job. */
		executionCount: Option[Int] = None,
	  /** Output only. Name of the last created execution. */
		latestCreatedExecution: Option[Schema.GoogleCloudRunV2ExecutionReference] = None,
	  /** Output only. Returns true if the Job is currently being acted upon by the system to bring it into the desired state. When a new Job is created, or an existing one is updated, Cloud Run will asynchronously perform all necessary steps to bring the Job to the desired state. This process is called reconciliation. While reconciliation is in process, `observed_generation` and `latest_succeeded_execution`, will have transient values that might mismatch the intended state: Once reconciliation is over (and this field is false), there are two possible outcomes: reconciliation succeeded and the state matches the Job, or there was an error, and reconciliation failed. This state can be found in `terminal_condition.state`. If reconciliation succeeded, the following fields will match: `observed_generation` and `generation`, `latest_succeeded_execution` and `latest_created_execution`. If reconciliation failed, `observed_generation` and `latest_succeeded_execution` will have the state of the last succeeded execution or empty for newly created Job. Additional information on the failure can be found in `terminal_condition` and `conditions`. */
		reconciling: Option[Boolean] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** A unique string used as a suffix creating a new execution. The Job will become ready when the execution is successfully started. The sum of job name and token length must be fewer than 63 characters. */
		startExecutionToken: Option[String] = None,
	  /** A unique string used as a suffix for creating a new execution. The Job will become ready when the execution is successfully completed. The sum of job name and token length must be fewer than 63 characters. */
		runExecutionToken: Option[String] = None,
	  /** Output only. A system-generated fingerprint for this version of the resource. May be used to detect modification conflict during updates. */
		etag: Option[String] = None
	)
	
	case class GoogleCloudRunV2BinaryAuthorization(
	  /** Optional. If True, indicates to use the default project's binary authorization policy. If False, binary authorization will be disabled. */
		useDefault: Option[Boolean] = None,
	  /** Optional. The path to a binary authorization policy. Format: `projects/{project}/platforms/cloudRun/{policy-name}` */
		policy: Option[String] = None,
	  /** Optional. If present, indicates to use Breakglass using this justification. If use_default is False, then it must be empty. For more information on breakglass, see https://cloud.google.com/binary-authorization/docs/using-breakglass */
		breakglassJustification: Option[String] = None
	)
	
	case class GoogleCloudRunV2ExecutionTemplate(
	  /** Unstructured key value map that can be used to organize and categorize objects. User-provided labels are shared with Google's billing system, so they can be used to filter, or break down billing charges by team, component, environment, state, etc. For more information, visit https://cloud.google.com/resource-manager/docs/creating-managing-labels or https://cloud.google.com/run/docs/configuring/labels. Cloud Run API v2 does not support labels with `run.googleapis.com`, `cloud.googleapis.com`, `serving.knative.dev`, or `autoscaling.knative.dev` namespaces, and they will be rejected. All system labels in v1 now have a corresponding field in v2 ExecutionTemplate. */
		labels: Option[Map[String, String]] = None,
	  /** Unstructured key value map that may be set by external tools to store and arbitrary metadata. They are not queryable and should be preserved when modifying objects. Cloud Run API v2 does not support annotations with `run.googleapis.com`, `cloud.googleapis.com`, `serving.knative.dev`, or `autoscaling.knative.dev` namespaces, and they will be rejected. All system annotations in v1 now have a corresponding field in v2 ExecutionTemplate. This field follows Kubernetes annotations' namespacing, limits, and rules. */
		annotations: Option[Map[String, String]] = None,
	  /** Specifies the maximum desired number of tasks the execution should run at given time. Must be <= task_count. When the job is run, if this field is 0 or unset, the maximum possible value will be used for that execution. The actual number of tasks running in steady state will be less than this number when there are fewer tasks waiting to be completed remaining, i.e. when the work left to do is less than max parallelism. */
		parallelism: Option[Int] = None,
	  /** Specifies the desired number of tasks the execution should run. Setting to 1 means that parallelism is limited to 1 and the success of that task signals the success of the execution. Defaults to 1. */
		taskCount: Option[Int] = None,
	  /** Required. Describes the task(s) that will be created when executing an execution. */
		template: Option[Schema.GoogleCloudRunV2TaskTemplate] = None
	)
	
	object GoogleCloudRunV2ExecutionReference {
		enum CompletionStatusEnum extends Enum[CompletionStatusEnum] { case COMPLETION_STATUS_UNSPECIFIED, EXECUTION_SUCCEEDED, EXECUTION_FAILED, EXECUTION_RUNNING, EXECUTION_PENDING, EXECUTION_CANCELLED }
	}
	case class GoogleCloudRunV2ExecutionReference(
	  /** Name of the execution. */
		name: Option[String] = None,
	  /** Creation timestamp of the execution. */
		createTime: Option[String] = None,
	  /** Creation timestamp of the execution. */
		completionTime: Option[String] = None,
	  /** The deletion time of the execution. It is only populated as a response to a Delete request. */
		deleteTime: Option[String] = None,
	  /** Status for the execution completion. */
		completionStatus: Option[Schema.GoogleCloudRunV2ExecutionReference.CompletionStatusEnum] = None
	)
	
	case class GoogleCloudRunV2ListJobsResponse(
	  /** The resulting list of Jobs. */
		jobs: Option[List[Schema.GoogleCloudRunV2Job]] = None,
	  /** A token indicating there are more items than page_size. Use it in the next ListJobs request to continue. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudRunV2RunJobRequest(
	  /** Indicates that the request should be validated without actually deleting any resources. */
		validateOnly: Option[Boolean] = None,
	  /** A system-generated fingerprint for this version of the resource. May be used to detect modification conflict during updates. */
		etag: Option[String] = None,
	  /** Overrides specification for a given execution of a job. If provided, overrides will be applied to update the execution or task spec. */
		overrides: Option[Schema.GoogleCloudRunV2Overrides] = None
	)
	
	case class GoogleCloudRunV2Overrides(
	  /** Per container override specification. */
		containerOverrides: Option[List[Schema.GoogleCloudRunV2ContainerOverride]] = None,
	  /** Optional. The desired number of tasks the execution should run. Will replace existing task_count value. */
		taskCount: Option[Int] = None,
	  /** Duration in seconds the task may be active before the system will actively try to mark it failed and kill associated containers. Will replace existing timeout_seconds value. */
		timeout: Option[String] = None
	)
	
	case class GoogleCloudRunV2ContainerOverride(
	  /** The name of the container specified as a DNS_LABEL. */
		name: Option[String] = None,
	  /** Optional. Arguments to the entrypoint. Will replace existing args for override. */
		args: Option[List[String]] = None,
	  /** List of environment variables to set in the container. Will be merged with existing env for override. */
		env: Option[List[Schema.GoogleCloudRunV2EnvVar]] = None,
	  /** Optional. True if the intention is to clear out existing args list. */
		clearArgs: Option[Boolean] = None
	)
	
	case class GoogleIamV1Policy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.GoogleIamV1Binding]] = None,
	  /** Specifies cloud audit logging configuration for this policy. */
		auditConfigs: Option[List[Schema.GoogleIamV1AuditConfig]] = None,
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An `etag` is returned in the response to `getIamPolicy`, and systems are expected to put that etag in the request to `setIamPolicy` to ensure that their change will be applied to the same version of the policy. &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. */
		etag: Option[String] = None
	)
	
	case class GoogleIamV1Binding(
	  /** Role that is assigned to the list of `members`, or principals. For example, `roles/viewer`, `roles/editor`, or `roles/owner`. For an overview of the IAM roles and permissions, see the [IAM documentation](https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see [here](https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. `members` can have the following values: &#42; `allUsers`: A special identifier that represents anyone who is on the internet; with or without a Google account. &#42; `allAuthenticatedUsers`: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. &#42; `user:{emailid}`: An email address that represents a specific Google account. For example, `alice@example.com` . &#42; `serviceAccount:{emailid}`: An email address that represents a Google service account. For example, `my-other-app@appspot.gserviceaccount.com`. &#42; `serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]`: An identifier for a [Kubernetes service account](https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, `my-project.svc.id.goog[my-namespace/my-kubernetes-sa]`. &#42; `group:{emailid}`: An email address that represents a Google group. For example, `admins@example.com`. &#42; `domain:{domain}`: The G Suite domain (primary) that represents all the users of that domain. For example, `google.com` or `example.com`. &#42; `principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workforce identity pool. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}`: All workforce identities in a group. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All workforce identities with a specific attribute value. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;`: All identities in a workforce identity pool. &#42; `principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workload identity pool. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}`: A workload identity pool group. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All identities in a workload identity pool with a certain attribute. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;`: All identities in a workload identity pool. &#42; `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a user that has been recently deleted. For example, `alice@example.com?uid=123456789012345678901`. If the user is recovered, this value reverts to `user:{emailid}` and the recovered user retains the role in the binding. &#42; `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, `my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901`. If the service account is undeleted, this value reverts to `serviceAccount:{emailid}` and the undeleted service account retains the role in the binding. &#42; `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, `admins@example.com?uid=123456789012345678901`. If the group is recovered, this value reverts to `group:{emailid}` and the recovered group retains the role in the binding. &#42; `deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: Deleted single identity in a workforce identity pool. For example, `deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value`. */
		members: Option[List[String]] = None,
	  /** The condition that is associated with this binding. If the condition evaluates to `true`, then this binding applies to the current request. If the condition evaluates to `false`, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		condition: Option[Schema.GoogleTypeExpr] = None
	)
	
	case class GoogleTypeExpr(
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None,
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None,
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None
	)
	
	case class GoogleIamV1AuditConfig(
	  /** Specifies a service that will be enabled for audit logging. For example, `storage.googleapis.com`, `cloudsql.googleapis.com`. `allServices` is a special value that covers all services. */
		service: Option[String] = None,
	  /** The configuration for logging of each type of permission. */
		auditLogConfigs: Option[List[Schema.GoogleIamV1AuditLogConfig]] = None
	)
	
	object GoogleIamV1AuditLogConfig {
		enum LogTypeEnum extends Enum[LogTypeEnum] { case LOG_TYPE_UNSPECIFIED, ADMIN_READ, DATA_WRITE, DATA_READ }
	}
	case class GoogleIamV1AuditLogConfig(
	  /** The log type that this config enables. */
		logType: Option[Schema.GoogleIamV1AuditLogConfig.LogTypeEnum] = None,
	  /** Specifies the identities that do not cause logging for this type of permission. Follows the same format of Binding.members. */
		exemptedMembers: Option[List[String]] = None
	)
	
	case class GoogleIamV1SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.GoogleIamV1Policy] = None,
	  /** OPTIONAL: A FieldMask specifying which fields of the policy to modify. Only the fields in the mask will be modified. If no mask is provided, the following default mask is used: `paths: "bindings, etag"` */
		updateMask: Option[String] = None
	)
	
	case class GoogleIamV1TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class GoogleIamV1TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	object GoogleCloudRunV2Revision {
		enum LaunchStageEnum extends Enum[LaunchStageEnum] { case LAUNCH_STAGE_UNSPECIFIED, UNIMPLEMENTED, PRELAUNCH, EARLY_ACCESS, ALPHA, BETA, GA, DEPRECATED }
		enum ExecutionEnvironmentEnum extends Enum[ExecutionEnvironmentEnum] { case EXECUTION_ENVIRONMENT_UNSPECIFIED, EXECUTION_ENVIRONMENT_GEN1, EXECUTION_ENVIRONMENT_GEN2 }
		enum EncryptionKeyRevocationActionEnum extends Enum[EncryptionKeyRevocationActionEnum] { case ENCRYPTION_KEY_REVOCATION_ACTION_UNSPECIFIED, PREVENT_NEW, SHUTDOWN }
	}
	case class GoogleCloudRunV2Revision(
	  /** Output only. The unique name of this Revision. */
		name: Option[String] = None,
	  /** Output only. Server assigned unique identifier for the Revision. The value is a UUID4 string and guaranteed to remain unchanged until the resource is deleted. */
		uid: Option[String] = None,
	  /** Output only. A number that monotonically increases every time the user modifies the desired state. */
		generation: Option[String] = None,
	  /** Output only. Unstructured key value map that can be used to organize and categorize objects. User-provided labels are shared with Google's billing system, so they can be used to filter, or break down billing charges by team, component, environment, state, etc. For more information, visit https://cloud.google.com/resource-manager/docs/creating-managing-labels or https://cloud.google.com/run/docs/configuring/labels. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Unstructured key value map that may be set by external tools to store and arbitrary metadata. They are not queryable and should be preserved when modifying objects. */
		annotations: Option[Map[String, String]] = None,
	  /** Output only. The creation time. */
		createTime: Option[String] = None,
	  /** Output only. The last-modified time. */
		updateTime: Option[String] = None,
	  /** Output only. For a deleted resource, the deletion time. It is only populated as a response to a Delete request. */
		deleteTime: Option[String] = None,
	  /** Output only. For a deleted resource, the time after which it will be permamently deleted. It is only populated as a response to a Delete request. */
		expireTime: Option[String] = None,
	  /** The least stable launch stage needed to create this resource, as defined by [Google Cloud Platform Launch Stages](https://cloud.google.com/terms/launch-stages). Cloud Run supports `ALPHA`, `BETA`, and `GA`. Note that this value might not be what was used as input. For example, if ALPHA was provided as input in the parent resource, but only BETA and GA-level features are were, this field will be BETA. */
		launchStage: Option[Schema.GoogleCloudRunV2Revision.LaunchStageEnum] = None,
	  /** Output only. The name of the parent service. */
		service: Option[String] = None,
	  /** Scaling settings for this revision. */
		scaling: Option[Schema.GoogleCloudRunV2RevisionScaling] = None,
	  /** VPC Access configuration for this Revision. For more information, visit https://cloud.google.com/run/docs/configuring/connecting-vpc. */
		vpcAccess: Option[Schema.GoogleCloudRunV2VpcAccess] = None,
	  /** Sets the maximum number of requests that each serving instance can receive. */
		maxInstanceRequestConcurrency: Option[Int] = None,
	  /** Max allowed time for an instance to respond to a request. */
		timeout: Option[String] = None,
	  /** Email address of the IAM service account associated with the revision of the service. The service account represents the identity of the running revision, and determines what permissions the revision has. */
		serviceAccount: Option[String] = None,
	  /** Holds the single container that defines the unit of execution for this Revision. */
		containers: Option[List[Schema.GoogleCloudRunV2Container]] = None,
	  /** A list of Volumes to make available to containers. */
		volumes: Option[List[Schema.GoogleCloudRunV2Volume]] = None,
	  /** The execution environment being used to host this Revision. */
		executionEnvironment: Option[Schema.GoogleCloudRunV2Revision.ExecutionEnvironmentEnum] = None,
	  /** A reference to a customer managed encryption key (CMEK) to use to encrypt this container image. For more information, go to https://cloud.google.com/run/docs/securing/using-cmek */
		encryptionKey: Option[String] = None,
	  /** Enables service mesh connectivity. */
		serviceMesh: Option[Schema.GoogleCloudRunV2ServiceMesh] = None,
	  /** The action to take if the encryption key is revoked. */
		encryptionKeyRevocationAction: Option[Schema.GoogleCloudRunV2Revision.EncryptionKeyRevocationActionEnum] = None,
	  /** If encryption_key_revocation_action is SHUTDOWN, the duration before shutting down all instances. The minimum increment is 1 hour. */
		encryptionKeyShutdownDuration: Option[String] = None,
	  /** Output only. Indicates whether the resource's reconciliation is still in progress. See comments in `Service.reconciling` for additional information on reconciliation process in Cloud Run. */
		reconciling: Option[Boolean] = None,
	  /** Output only. The Condition of this Revision, containing its readiness status, and detailed error information in case it did not reach a serving state. */
		conditions: Option[List[Schema.GoogleCloudRunV2Condition]] = None,
	  /** Output only. The generation of this Revision currently serving traffic. See comments in `reconciling` for additional information on reconciliation process in Cloud Run. */
		observedGeneration: Option[String] = None,
	  /** Output only. The Google Console URI to obtain logs for the Revision. */
		logUri: Option[String] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Enable session affinity. */
		sessionAffinity: Option[Boolean] = None,
	  /** Output only. The current effective scaling settings for the revision. */
		scalingStatus: Option[Schema.GoogleCloudRunV2RevisionScalingStatus] = None,
	  /** The node selector for the revision. */
		nodeSelector: Option[Schema.GoogleCloudRunV2NodeSelector] = None,
	  /** Output only. A system-generated fingerprint for this version of the resource. May be used to detect modification conflict during updates. */
		etag: Option[String] = None
	)
	
	case class GoogleCloudRunV2RevisionScaling(
	  /** Optional. Minimum number of serving instances that this resource should have. */
		minInstanceCount: Option[Int] = None,
	  /** Optional. Maximum number of serving instances that this resource should have. When unspecified, the field is set to the server default value of 100. For more information see https://cloud.google.com/run/docs/configuring/max-instances */
		maxInstanceCount: Option[Int] = None
	)
	
	case class GoogleCloudRunV2ServiceMesh(
	  /** The Mesh resource name. Format: `projects/{project}/locations/global/meshes/{mesh}`, where `{project}` can be project id or number. */
		mesh: Option[String] = None
	)
	
	case class GoogleCloudRunV2RevisionScalingStatus(
	  /** The current number of min instances provisioned for this revision. */
		desiredMinInstanceCount: Option[Int] = None
	)
	
	case class GoogleCloudRunV2NodeSelector(
	  /** Required. GPU accelerator type to attach to an instance. */
		accelerator: Option[String] = None
	)
	
	case class GoogleCloudRunV2ListRevisionsResponse(
	  /** The resulting list of Revisions. */
		revisions: Option[List[Schema.GoogleCloudRunV2Revision]] = None,
	  /** A token indicating there are more items than page_size. Use it in the next ListRevisions request to continue. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudRunV2Service {
		enum IngressEnum extends Enum[IngressEnum] { case INGRESS_TRAFFIC_UNSPECIFIED, INGRESS_TRAFFIC_ALL, INGRESS_TRAFFIC_INTERNAL_ONLY, INGRESS_TRAFFIC_INTERNAL_LOAD_BALANCER, INGRESS_TRAFFIC_NONE }
		enum LaunchStageEnum extends Enum[LaunchStageEnum] { case LAUNCH_STAGE_UNSPECIFIED, UNIMPLEMENTED, PRELAUNCH, EARLY_ACCESS, ALPHA, BETA, GA, DEPRECATED }
	}
	case class GoogleCloudRunV2Service(
	  /** The fully qualified name of this Service. In CreateServiceRequest, this field is ignored, and instead composed from CreateServiceRequest.parent and CreateServiceRequest.service_id. Format: projects/{project}/locations/{location}/services/{service_id} */
		name: Option[String] = None,
	  /** User-provided description of the Service. This field currently has a 512-character limit. */
		description: Option[String] = None,
	  /** Output only. Server assigned unique identifier for the trigger. The value is a UUID4 string and guaranteed to remain unchanged until the resource is deleted. */
		uid: Option[String] = None,
	  /** Output only. A number that monotonically increases every time the user modifies the desired state. Please note that unlike v1, this is an int64 value. As with most Google APIs, its JSON representation will be a `string` instead of an `integer`. */
		generation: Option[String] = None,
	  /** Optional. Unstructured key value map that can be used to organize and categorize objects. User-provided labels are shared with Google's billing system, so they can be used to filter, or break down billing charges by team, component, environment, state, etc. For more information, visit https://cloud.google.com/resource-manager/docs/creating-managing-labels or https://cloud.google.com/run/docs/configuring/labels. Cloud Run API v2 does not support labels with `run.googleapis.com`, `cloud.googleapis.com`, `serving.knative.dev`, or `autoscaling.knative.dev` namespaces, and they will be rejected. All system labels in v1 now have a corresponding field in v2 Service. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Unstructured key value map that may be set by external tools to store and arbitrary metadata. They are not queryable and should be preserved when modifying objects. Cloud Run API v2 does not support annotations with `run.googleapis.com`, `cloud.googleapis.com`, `serving.knative.dev`, or `autoscaling.knative.dev` namespaces, and they will be rejected in new resources. All system annotations in v1 now have a corresponding field in v2 Service. This field follows Kubernetes annotations' namespacing, limits, and rules. */
		annotations: Option[Map[String, String]] = None,
	  /** Output only. The creation time. */
		createTime: Option[String] = None,
	  /** Output only. The last-modified time. */
		updateTime: Option[String] = None,
	  /** Output only. The deletion time. It is only populated as a response to a Delete request. */
		deleteTime: Option[String] = None,
	  /** Output only. For a deleted resource, the time after which it will be permamently deleted. */
		expireTime: Option[String] = None,
	  /** Output only. Email address of the authenticated creator. */
		creator: Option[String] = None,
	  /** Output only. Email address of the last authenticated modifier. */
		lastModifier: Option[String] = None,
	  /** Arbitrary identifier for the API client. */
		client: Option[String] = None,
	  /** Arbitrary version identifier for the API client. */
		clientVersion: Option[String] = None,
	  /** Optional. Provides the ingress settings for this Service. On output, returns the currently observed ingress settings, or INGRESS_TRAFFIC_UNSPECIFIED if no revision is active. */
		ingress: Option[Schema.GoogleCloudRunV2Service.IngressEnum] = None,
	  /** Optional. The launch stage as defined by [Google Cloud Platform Launch Stages](https://cloud.google.com/terms/launch-stages). Cloud Run supports `ALPHA`, `BETA`, and `GA`. If no value is specified, GA is assumed. Set the launch stage to a preview stage on input to allow use of preview features in that stage. On read (or output), describes whether the resource uses preview features. For example, if ALPHA is provided as input, but only BETA and GA-level features are used, this field will be BETA on output. */
		launchStage: Option[Schema.GoogleCloudRunV2Service.LaunchStageEnum] = None,
	  /** Optional. Settings for the Binary Authorization feature. */
		binaryAuthorization: Option[Schema.GoogleCloudRunV2BinaryAuthorization] = None,
	  /** Required. The template used to create revisions for this Service. */
		template: Option[Schema.GoogleCloudRunV2RevisionTemplate] = None,
	  /** Optional. Specifies how to distribute traffic over a collection of Revisions belonging to the Service. If traffic is empty or not provided, defaults to 100% traffic to the latest `Ready` Revision. */
		traffic: Option[List[Schema.GoogleCloudRunV2TrafficTarget]] = None,
	  /** Optional. Specifies service-level scaling settings */
		scaling: Option[Schema.GoogleCloudRunV2ServiceScaling] = None,
	  /** Optional. Disables IAM permission check for run.routes.invoke for callers of this service. This feature is available by invitation only. For more information, visit https://cloud.google.com/run/docs/securing/managing-access#invoker_check. */
		invokerIamDisabled: Option[Boolean] = None,
	  /** Optional. Disables public resolution of the default URI of this service. */
		defaultUriDisabled: Option[Boolean] = None,
	  /** Output only. All URLs serving traffic for this Service. */
		urls: Option[List[String]] = None,
	  /** One or more custom audiences that you want this service to support. Specify each custom audience as the full URL in a string. The custom audiences are encoded in the token and used to authenticate requests. For more information, see https://cloud.google.com/run/docs/configuring/custom-audiences. */
		customAudiences: Option[List[String]] = None,
	  /** Output only. The generation of this Service currently serving traffic. See comments in `reconciling` for additional information on reconciliation process in Cloud Run. Please note that unlike v1, this is an int64 value. As with most Google APIs, its JSON representation will be a `string` instead of an `integer`. */
		observedGeneration: Option[String] = None,
	  /** Output only. The Condition of this Service, containing its readiness status, and detailed error information in case it did not reach a serving state. See comments in `reconciling` for additional information on reconciliation process in Cloud Run. */
		terminalCondition: Option[Schema.GoogleCloudRunV2Condition] = None,
	  /** Output only. The Conditions of all other associated sub-resources. They contain additional diagnostics information in case the Service does not reach its Serving state. See comments in `reconciling` for additional information on reconciliation process in Cloud Run. */
		conditions: Option[List[Schema.GoogleCloudRunV2Condition]] = None,
	  /** Output only. Name of the latest revision that is serving traffic. See comments in `reconciling` for additional information on reconciliation process in Cloud Run. */
		latestReadyRevision: Option[String] = None,
	  /** Output only. Name of the last created revision. See comments in `reconciling` for additional information on reconciliation process in Cloud Run. */
		latestCreatedRevision: Option[String] = None,
	  /** Output only. Detailed status information for corresponding traffic targets. See comments in `reconciling` for additional information on reconciliation process in Cloud Run. */
		trafficStatuses: Option[List[Schema.GoogleCloudRunV2TrafficTargetStatus]] = None,
	  /** Output only. The main URI in which this Service is serving traffic. */
		uri: Option[String] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Output only. Returns true if the Service is currently being acted upon by the system to bring it into the desired state. When a new Service is created, or an existing one is updated, Cloud Run will asynchronously perform all necessary steps to bring the Service to the desired serving state. This process is called reconciliation. While reconciliation is in process, `observed_generation`, `latest_ready_revison`, `traffic_statuses`, and `uri` will have transient values that might mismatch the intended state: Once reconciliation is over (and this field is false), there are two possible outcomes: reconciliation succeeded and the serving state matches the Service, or there was an error, and reconciliation failed. This state can be found in `terminal_condition.state`. If reconciliation succeeded, the following fields will match: `traffic` and `traffic_statuses`, `observed_generation` and `generation`, `latest_ready_revision` and `latest_created_revision`. If reconciliation failed, `traffic_statuses`, `observed_generation`, and `latest_ready_revision` will have the state of the last serving revision, or empty for newly created Services. Additional information on the failure can be found in `terminal_condition` and `conditions`. */
		reconciling: Option[Boolean] = None,
	  /** Output only. A system-generated fingerprint for this version of the resource. May be used to detect modification conflict during updates. */
		etag: Option[String] = None
	)
	
	object GoogleCloudRunV2RevisionTemplate {
		enum ExecutionEnvironmentEnum extends Enum[ExecutionEnvironmentEnum] { case EXECUTION_ENVIRONMENT_UNSPECIFIED, EXECUTION_ENVIRONMENT_GEN1, EXECUTION_ENVIRONMENT_GEN2 }
		enum EncryptionKeyRevocationActionEnum extends Enum[EncryptionKeyRevocationActionEnum] { case ENCRYPTION_KEY_REVOCATION_ACTION_UNSPECIFIED, PREVENT_NEW, SHUTDOWN }
	}
	case class GoogleCloudRunV2RevisionTemplate(
	  /** Optional. The unique name for the revision. If this field is omitted, it will be automatically generated based on the Service name. */
		revision: Option[String] = None,
	  /** Optional. Unstructured key value map that can be used to organize and categorize objects. User-provided labels are shared with Google's billing system, so they can be used to filter, or break down billing charges by team, component, environment, state, etc. For more information, visit https://cloud.google.com/resource-manager/docs/creating-managing-labels or https://cloud.google.com/run/docs/configuring/labels. Cloud Run API v2 does not support labels with `run.googleapis.com`, `cloud.googleapis.com`, `serving.knative.dev`, or `autoscaling.knative.dev` namespaces, and they will be rejected. All system labels in v1 now have a corresponding field in v2 RevisionTemplate. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Unstructured key value map that may be set by external tools to store and arbitrary metadata. They are not queryable and should be preserved when modifying objects. Cloud Run API v2 does not support annotations with `run.googleapis.com`, `cloud.googleapis.com`, `serving.knative.dev`, or `autoscaling.knative.dev` namespaces, and they will be rejected. All system annotations in v1 now have a corresponding field in v2 RevisionTemplate. This field follows Kubernetes annotations' namespacing, limits, and rules. */
		annotations: Option[Map[String, String]] = None,
	  /** Optional. Scaling settings for this Revision. */
		scaling: Option[Schema.GoogleCloudRunV2RevisionScaling] = None,
	  /** Optional. VPC Access configuration to use for this Revision. For more information, visit https://cloud.google.com/run/docs/configuring/connecting-vpc. */
		vpcAccess: Option[Schema.GoogleCloudRunV2VpcAccess] = None,
	  /** Optional. Max allowed time for an instance to respond to a request. */
		timeout: Option[String] = None,
	  /** Optional. Email address of the IAM service account associated with the revision of the service. The service account represents the identity of the running revision, and determines what permissions the revision has. If not provided, the revision will use the project's default service account. */
		serviceAccount: Option[String] = None,
	  /** Holds the single container that defines the unit of execution for this Revision. */
		containers: Option[List[Schema.GoogleCloudRunV2Container]] = None,
	  /** Optional. A list of Volumes to make available to containers. */
		volumes: Option[List[Schema.GoogleCloudRunV2Volume]] = None,
	  /** Optional. The sandbox environment to host this Revision. */
		executionEnvironment: Option[Schema.GoogleCloudRunV2RevisionTemplate.ExecutionEnvironmentEnum] = None,
	  /** A reference to a customer managed encryption key (CMEK) to use to encrypt this container image. For more information, go to https://cloud.google.com/run/docs/securing/using-cmek */
		encryptionKey: Option[String] = None,
	  /** Optional. Sets the maximum number of requests that each serving instance can receive. If not specified or 0, defaults to 80 when requested `CPU >= 1` and defaults to 1 when requested `CPU < 1`. */
		maxInstanceRequestConcurrency: Option[Int] = None,
	  /** Optional. Enables service mesh connectivity. */
		serviceMesh: Option[Schema.GoogleCloudRunV2ServiceMesh] = None,
	  /** Optional. The action to take if the encryption key is revoked. */
		encryptionKeyRevocationAction: Option[Schema.GoogleCloudRunV2RevisionTemplate.EncryptionKeyRevocationActionEnum] = None,
	  /** Optional. If encryption_key_revocation_action is SHUTDOWN, the duration before shutting down all instances. The minimum increment is 1 hour. */
		encryptionKeyShutdownDuration: Option[String] = None,
	  /** Optional. Enable session affinity. */
		sessionAffinity: Option[Boolean] = None,
	  /** Optional. Disables health checking containers during deployment. */
		healthCheckDisabled: Option[Boolean] = None,
	  /** Optional. The node selector for the revision template. */
		nodeSelector: Option[Schema.GoogleCloudRunV2NodeSelector] = None
	)
	
	object GoogleCloudRunV2TrafficTarget {
		enum TypeEnum extends Enum[TypeEnum] { case TRAFFIC_TARGET_ALLOCATION_TYPE_UNSPECIFIED, TRAFFIC_TARGET_ALLOCATION_TYPE_LATEST, TRAFFIC_TARGET_ALLOCATION_TYPE_REVISION }
	}
	case class GoogleCloudRunV2TrafficTarget(
	  /** The allocation type for this traffic target. */
		`type`: Option[Schema.GoogleCloudRunV2TrafficTarget.TypeEnum] = None,
	  /** Revision to which to send this portion of traffic, if traffic allocation is by revision. */
		revision: Option[String] = None,
	  /** Specifies percent of the traffic to this Revision. This defaults to zero if unspecified. */
		percent: Option[Int] = None,
	  /** Indicates a string to be part of the URI to exclusively reference this target. */
		tag: Option[String] = None
	)
	
	object GoogleCloudRunV2ServiceScaling {
		enum ScalingModeEnum extends Enum[ScalingModeEnum] { case SCALING_MODE_UNSPECIFIED, AUTOMATIC, MANUAL }
	}
	case class GoogleCloudRunV2ServiceScaling(
	  /** Optional. total min instances for the service. This number of instances is divided among all revisions with specified traffic based on the percent of traffic they are receiving. */
		minInstanceCount: Option[Int] = None,
	  /** Optional. The scaling mode for the service. */
		scalingMode: Option[Schema.GoogleCloudRunV2ServiceScaling.ScalingModeEnum] = None
	)
	
	object GoogleCloudRunV2TrafficTargetStatus {
		enum TypeEnum extends Enum[TypeEnum] { case TRAFFIC_TARGET_ALLOCATION_TYPE_UNSPECIFIED, TRAFFIC_TARGET_ALLOCATION_TYPE_LATEST, TRAFFIC_TARGET_ALLOCATION_TYPE_REVISION }
	}
	case class GoogleCloudRunV2TrafficTargetStatus(
	  /** The allocation type for this traffic target. */
		`type`: Option[Schema.GoogleCloudRunV2TrafficTargetStatus.TypeEnum] = None,
	  /** Revision to which this traffic is sent. */
		revision: Option[String] = None,
	  /** Specifies percent of the traffic to this Revision. */
		percent: Option[Int] = None,
	  /** Indicates the string used in the URI to exclusively reference this target. */
		tag: Option[String] = None,
	  /** Displays the target URI. */
		uri: Option[String] = None
	)
	
	case class GoogleCloudRunV2ListServicesResponse(
	  /** The resulting list of Services. */
		services: Option[List[Schema.GoogleCloudRunV2Service]] = None,
	  /** A token indicating there are more items than page_size. Use it in the next ListServices request to continue. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudRunV2Task {
		enum ExecutionEnvironmentEnum extends Enum[ExecutionEnvironmentEnum] { case EXECUTION_ENVIRONMENT_UNSPECIFIED, EXECUTION_ENVIRONMENT_GEN1, EXECUTION_ENVIRONMENT_GEN2 }
	}
	case class GoogleCloudRunV2Task(
	  /** Output only. The unique name of this Task. */
		name: Option[String] = None,
	  /** Output only. Server assigned unique identifier for the Task. The value is a UUID4 string and guaranteed to remain unchanged until the resource is deleted. */
		uid: Option[String] = None,
	  /** Output only. A number that monotonically increases every time the user modifies the desired state. */
		generation: Option[String] = None,
	  /** Output only. Unstructured key value map that can be used to organize and categorize objects. User-provided labels are shared with Google's billing system, so they can be used to filter, or break down billing charges by team, component, environment, state, etc. For more information, visit https://cloud.google.com/resource-manager/docs/creating-managing-labels or https://cloud.google.com/run/docs/configuring/labels */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Unstructured key value map that may be set by external tools to store and arbitrary metadata. They are not queryable and should be preserved when modifying objects. */
		annotations: Option[Map[String, String]] = None,
	  /** Output only. Represents time when the task was created by the system. It is not guaranteed to be set in happens-before order across separate operations. */
		createTime: Option[String] = None,
	  /** Output only. Represents time when the task was scheduled to run by the system. It is not guaranteed to be set in happens-before order across separate operations. */
		scheduledTime: Option[String] = None,
	  /** Output only. Represents time when the task started to run. It is not guaranteed to be set in happens-before order across separate operations. */
		startTime: Option[String] = None,
	  /** Output only. Represents time when the Task was completed. It is not guaranteed to be set in happens-before order across separate operations. */
		completionTime: Option[String] = None,
	  /** Output only. The last-modified time. */
		updateTime: Option[String] = None,
	  /** Output only. For a deleted resource, the deletion time. It is only populated as a response to a Delete request. */
		deleteTime: Option[String] = None,
	  /** Output only. For a deleted resource, the time after which it will be permamently deleted. It is only populated as a response to a Delete request. */
		expireTime: Option[String] = None,
	  /** Output only. The name of the parent Job. */
		job: Option[String] = None,
	  /** Output only. The name of the parent Execution. */
		execution: Option[String] = None,
	  /** Holds the single container that defines the unit of execution for this task. */
		containers: Option[List[Schema.GoogleCloudRunV2Container]] = None,
	  /** A list of Volumes to make available to containers. */
		volumes: Option[List[Schema.GoogleCloudRunV2Volume]] = None,
	  /** Number of retries allowed per Task, before marking this Task failed. */
		maxRetries: Option[Int] = None,
	  /** Max allowed time duration the Task may be active before the system will actively try to mark it failed and kill associated containers. This applies per attempt of a task, meaning each retry can run for the full timeout. */
		timeout: Option[String] = None,
	  /** Email address of the IAM service account associated with the Task of a Job. The service account represents the identity of the running task, and determines what permissions the task has. If not provided, the task will use the project's default service account. */
		serviceAccount: Option[String] = None,
	  /** The execution environment being used to host this Task. */
		executionEnvironment: Option[Schema.GoogleCloudRunV2Task.ExecutionEnvironmentEnum] = None,
	  /** Output only. Indicates whether the resource's reconciliation is still in progress. See comments in `Job.reconciling` for additional information on reconciliation process in Cloud Run. */
		reconciling: Option[Boolean] = None,
	  /** Output only. The Condition of this Task, containing its readiness status, and detailed error information in case it did not reach the desired state. */
		conditions: Option[List[Schema.GoogleCloudRunV2Condition]] = None,
	  /** Output only. The generation of this Task. See comments in `Job.reconciling` for additional information on reconciliation process in Cloud Run. */
		observedGeneration: Option[String] = None,
	  /** Output only. Index of the Task, unique per execution, and beginning at 0. */
		index: Option[Int] = None,
	  /** Output only. The number of times this Task was retried. Tasks are retried when they fail up to the maxRetries limit. */
		retried: Option[Int] = None,
	  /** Output only. Result of the last attempt of this Task. */
		lastAttemptResult: Option[Schema.GoogleCloudRunV2TaskAttemptResult] = None,
	  /** Output only. A reference to a customer managed encryption key (CMEK) to use to encrypt this container image. For more information, go to https://cloud.google.com/run/docs/securing/using-cmek */
		encryptionKey: Option[String] = None,
	  /** Output only. VPC Access configuration to use for this Task. For more information, visit https://cloud.google.com/run/docs/configuring/connecting-vpc. */
		vpcAccess: Option[Schema.GoogleCloudRunV2VpcAccess] = None,
	  /** Output only. URI where logs for this execution can be found in Cloud Console. */
		logUri: Option[String] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Output only. A system-generated fingerprint for this version of the resource. May be used to detect modification conflict during updates. */
		etag: Option[String] = None
	)
	
	case class GoogleCloudRunV2TaskAttemptResult(
	  /** Output only. The status of this attempt. If the status code is OK, then the attempt succeeded. */
		status: Option[Schema.GoogleRpcStatus] = None,
	  /** Output only. The exit code of this attempt. This may be unset if the container was unable to exit cleanly with a code due to some other failure. See status field for possible failure details. */
		exitCode: Option[Int] = None
	)
	
	case class GoogleCloudRunV2ListTasksResponse(
	  /** The resulting list of Tasks. */
		tasks: Option[List[Schema.GoogleCloudRunV2Task]] = None,
	  /** A token indicating there are more items than page_size. Use it in the next ListTasks request to continue. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1BuildOperationMetadata(
	  /** The build that the operation is tracking. */
		build: Option[Schema.GoogleDevtoolsCloudbuildV1Build] = None
	)
	
	object GoogleDevtoolsCloudbuildV1Build {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNKNOWN, PENDING, QUEUED, WORKING, SUCCESS, FAILURE, INTERNAL_ERROR, TIMEOUT, CANCELLED, EXPIRED }
	}
	case class GoogleDevtoolsCloudbuildV1Build(
	  /** Output only. The 'Build' name with format: `projects/{project}/locations/{location}/builds/{build}`, where {build} is a unique identifier generated by the service. */
		name: Option[String] = None,
	  /** Output only. Unique identifier of the build. */
		id: Option[String] = None,
	  /** Output only. ID of the project. */
		projectId: Option[String] = None,
	  /** Output only. Status of the build. */
		status: Option[Schema.GoogleDevtoolsCloudbuildV1Build.StatusEnum] = None,
	  /** Output only. Customer-readable message about the current status. */
		statusDetail: Option[String] = None,
	  /** Optional. The location of the source files to build. */
		source: Option[Schema.GoogleDevtoolsCloudbuildV1Source] = None,
	  /** Required. The operations to be performed on the workspace. */
		steps: Option[List[Schema.GoogleDevtoolsCloudbuildV1BuildStep]] = None,
	  /** Output only. Results of the build. */
		results: Option[Schema.GoogleDevtoolsCloudbuildV1Results] = None,
	  /** Output only. Time at which the request to create the build was received. */
		createTime: Option[String] = None,
	  /** Output only. Time at which execution of the build was started. */
		startTime: Option[String] = None,
	  /** Output only. Time at which execution of the build was finished. The difference between finish_time and start_time is the duration of the build's execution. */
		finishTime: Option[String] = None,
	  /** Amount of time that this build should be allowed to run, to second granularity. If this amount of time elapses, work on the build will cease and the build status will be `TIMEOUT`. `timeout` starts ticking from `startTime`. Default time is 60 minutes. */
		timeout: Option[String] = None,
	  /** A list of images to be pushed upon the successful completion of all build steps. The images are pushed using the builder service account's credentials. The digests of the pushed images will be stored in the `Build` resource's results field. If any of the images fail to be pushed, the build status is marked `FAILURE`. */
		images: Option[List[String]] = None,
	  /** TTL in queue for this build. If provided and the build is enqueued longer than this value, the build will expire and the build status will be `EXPIRED`. The TTL starts ticking from create_time. */
		queueTtl: Option[String] = None,
	  /** Artifacts produced by the build that should be uploaded upon successful completion of all build steps. */
		artifacts: Option[Schema.GoogleDevtoolsCloudbuildV1Artifacts] = None,
	  /** Cloud Storage bucket where logs should be written (see [Bucket Name Requirements](https://cloud.google.com/storage/docs/bucket-naming#requirements)). Logs file names will be of the format `${logs_bucket}/log-${build_id}.txt`. */
		logsBucket: Option[String] = None,
	  /** Output only. A permanent fixed identifier for source. */
		sourceProvenance: Option[Schema.GoogleDevtoolsCloudbuildV1SourceProvenance] = None,
	  /** Output only. The ID of the `BuildTrigger` that triggered this build, if it was triggered automatically. */
		buildTriggerId: Option[String] = None,
	  /** Special options for this build. */
		options: Option[Schema.GoogleDevtoolsCloudbuildV1BuildOptions] = None,
	  /** Output only. URL to logs for this build in Google Cloud Console. */
		logUrl: Option[String] = None,
	  /** Substitutions data for `Build` resource. */
		substitutions: Option[Map[String, String]] = None,
	  /** Tags for annotation of a `Build`. These are not docker tags. */
		tags: Option[List[String]] = None,
	  /** Secrets to decrypt using Cloud Key Management Service. Note: Secret Manager is the recommended technique for managing sensitive data with Cloud Build. Use `available_secrets` to configure builds to access secrets from Secret Manager. For instructions, see: https://cloud.google.com/cloud-build/docs/securing-builds/use-secrets */
		secrets: Option[List[Schema.GoogleDevtoolsCloudbuildV1Secret]] = None,
	  /** Output only. Stores timing information for phases of the build. Valid keys are: &#42; BUILD: time to execute all build steps. &#42; PUSH: time to push all artifacts including docker images and non docker artifacts. &#42; FETCHSOURCE: time to fetch source. &#42; SETUPBUILD: time to set up build. If the build does not specify source or images, these keys will not be included. */
		timing: Option[Map[String, Schema.GoogleDevtoolsCloudbuildV1TimeSpan]] = None,
	  /** Output only. Describes this build's approval configuration, status, and result. */
		approval: Option[Schema.GoogleDevtoolsCloudbuildV1BuildApproval] = None,
	  /** IAM service account whose credentials will be used at build runtime. Must be of the format `projects/{PROJECT_ID}/serviceAccounts/{ACCOUNT}`. ACCOUNT can be email address or uniqueId of the service account.  */
		serviceAccount: Option[String] = None,
	  /** Secrets and secret environment variables. */
		availableSecrets: Option[Schema.GoogleDevtoolsCloudbuildV1Secrets] = None,
	  /** Output only. Non-fatal problems encountered during the execution of the build. */
		warnings: Option[List[Schema.GoogleDevtoolsCloudbuildV1Warning]] = None,
	  /** Optional. Configuration for git operations. */
		gitConfig: Option[Schema.GoogleDevtoolsCloudbuildV1GitConfig] = None,
	  /** Output only. Contains information about the build when status=FAILURE. */
		failureInfo: Option[Schema.GoogleDevtoolsCloudbuildV1FailureInfo] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1Source(
	  /** If provided, get the source from this location in Cloud Storage. */
		storageSource: Option[Schema.GoogleDevtoolsCloudbuildV1StorageSource] = None,
	  /** If provided, get the source from this location in a Cloud Source Repository. */
		repoSource: Option[Schema.GoogleDevtoolsCloudbuildV1RepoSource] = None,
	  /** If provided, get the source from this Git repository. */
		gitSource: Option[Schema.GoogleDevtoolsCloudbuildV1GitSource] = None,
	  /** If provided, get the source from this manifest in Cloud Storage. This feature is in Preview; see description [here](https://github.com/GoogleCloudPlatform/cloud-builders/tree/master/gcs-fetcher). */
		storageSourceManifest: Option[Schema.GoogleDevtoolsCloudbuildV1StorageSourceManifest] = None,
	  /** Optional. If provided, get the source from this 2nd-gen Google Cloud Build repository resource. */
		connectedRepository: Option[Schema.GoogleDevtoolsCloudbuildV1ConnectedRepository] = None,
	  /** If provided, get the source from this Developer Connect config. */
		developerConnectConfig: Option[Schema.GoogleDevtoolsCloudbuildV1DeveloperConnectConfig] = None
	)
	
	object GoogleDevtoolsCloudbuildV1StorageSource {
		enum SourceFetcherEnum extends Enum[SourceFetcherEnum] { case SOURCE_FETCHER_UNSPECIFIED, GSUTIL, GCS_FETCHER }
	}
	case class GoogleDevtoolsCloudbuildV1StorageSource(
	  /** Cloud Storage bucket containing the source (see [Bucket Name Requirements](https://cloud.google.com/storage/docs/bucket-naming#requirements)). */
		bucket: Option[String] = None,
	  /** Required. Cloud Storage object containing the source. This object must be a zipped (`.zip`) or gzipped archive file (`.tar.gz`) containing source to build. */
		`object`: Option[String] = None,
	  /** Optional. Cloud Storage generation for the object. If the generation is omitted, the latest generation will be used. */
		generation: Option[String] = None,
	  /** Optional. Option to specify the tool to fetch the source file for the build. */
		sourceFetcher: Option[Schema.GoogleDevtoolsCloudbuildV1StorageSource.SourceFetcherEnum] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1RepoSource(
	  /** Optional. ID of the project that owns the Cloud Source Repository. If omitted, the project ID requesting the build is assumed. */
		projectId: Option[String] = None,
	  /** Required. Name of the Cloud Source Repository. */
		repoName: Option[String] = None,
	  /** Regex matching branches to build. The syntax of the regular expressions accepted is the syntax accepted by RE2 and described at https://github.com/google/re2/wiki/Syntax */
		branchName: Option[String] = None,
	  /** Regex matching tags to build. The syntax of the regular expressions accepted is the syntax accepted by RE2 and described at https://github.com/google/re2/wiki/Syntax */
		tagName: Option[String] = None,
	  /** Explicit commit SHA to build. */
		commitSha: Option[String] = None,
	  /** Optional. Directory, relative to the source root, in which to run the build. This must be a relative path. If a step's `dir` is specified and is an absolute path, this value is ignored for that step's execution. */
		dir: Option[String] = None,
	  /** Optional. Only trigger a build if the revision regex does NOT match the revision regex. */
		invertRegex: Option[Boolean] = None,
	  /** Optional. Substitutions to use in a triggered build. Should only be used with RunBuildTrigger */
		substitutions: Option[Map[String, String]] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1GitSource(
	  /** Required. Location of the Git repo to build. This will be used as a `git remote`, see https://git-scm.com/docs/git-remote. */
		url: Option[String] = None,
	  /** Optional. Directory, relative to the source root, in which to run the build. This must be a relative path. If a step's `dir` is specified and is an absolute path, this value is ignored for that step's execution. */
		dir: Option[String] = None,
	  /** Optional. The revision to fetch from the Git repository such as a branch, a tag, a commit SHA, or any Git ref. Cloud Build uses `git fetch` to fetch the revision from the Git repository; therefore make sure that the string you provide for `revision` is parsable by the command. For information on string values accepted by `git fetch`, see https://git-scm.com/docs/gitrevisions#_specifying_revisions. For information on `git fetch`, see https://git-scm.com/docs/git-fetch. */
		revision: Option[String] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1StorageSourceManifest(
	  /** Required. Cloud Storage bucket containing the source manifest (see [Bucket Name Requirements](https://cloud.google.com/storage/docs/bucket-naming#requirements)). */
		bucket: Option[String] = None,
	  /** Required. Cloud Storage object containing the source manifest. This object must be a JSON file. */
		`object`: Option[String] = None,
	  /** Cloud Storage generation for the object. If the generation is omitted, the latest generation will be used. */
		generation: Option[String] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1ConnectedRepository(
	  /** Required. Name of the Google Cloud Build repository, formatted as `projects/&#42;/locations/&#42;/connections/&#42;/repositories/&#42;`. */
		repository: Option[String] = None,
	  /** Optional. Directory, relative to the source root, in which to run the build. */
		dir: Option[String] = None,
	  /** Required. The revision to fetch from the Git repository such as a branch, a tag, a commit SHA, or any Git ref. */
		revision: Option[String] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1DeveloperConnectConfig(
	  /** Required. The Developer Connect Git repository link, formatted as `projects/&#42;/locations/&#42;/connections/&#42;/gitRepositoryLink/&#42;`. */
		gitRepositoryLink: Option[String] = None,
	  /** Required. Directory, relative to the source root, in which to run the build. */
		dir: Option[String] = None,
	  /** Required. The revision to fetch from the Git repository such as a branch, a tag, a commit SHA, or any Git ref. */
		revision: Option[String] = None
	)
	
	object GoogleDevtoolsCloudbuildV1BuildStep {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNKNOWN, PENDING, QUEUED, WORKING, SUCCESS, FAILURE, INTERNAL_ERROR, TIMEOUT, CANCELLED, EXPIRED }
	}
	case class GoogleDevtoolsCloudbuildV1BuildStep(
	  /** Required. The name of the container image that will run this particular build step. If the image is available in the host's Docker daemon's cache, it will be run directly. If not, the host will attempt to pull the image first, using the builder service account's credentials if necessary. The Docker daemon's cache will already have the latest versions of all of the officially supported build steps ([https://github.com/GoogleCloudPlatform/cloud-builders](https://github.com/GoogleCloudPlatform/cloud-builders)). The Docker daemon will also have cached many of the layers for some popular images, like "ubuntu", "debian", but they will be refreshed at the time you attempt to use them. If you built an image in a previous build step, it will be stored in the host's Docker daemon's cache and is available to use as the name for a later build step. */
		name: Option[String] = None,
	  /** A list of environment variable definitions to be used when running a step. The elements are of the form "KEY=VALUE" for the environment variable "KEY" being given the value "VALUE". */
		env: Option[List[String]] = None,
	  /** A list of arguments that will be presented to the step when it is started. If the image used to run the step's container has an entrypoint, the `args` are used as arguments to that entrypoint. If the image does not define an entrypoint, the first element in args is used as the entrypoint, and the remainder will be used as arguments. */
		args: Option[List[String]] = None,
	  /** Working directory to use when running this step's container. If this value is a relative path, it is relative to the build's working directory. If this value is absolute, it may be outside the build's working directory, in which case the contents of the path may not be persisted across build step executions, unless a `volume` for that path is specified. If the build specifies a `RepoSource` with `dir` and a step with a `dir`, which specifies an absolute path, the `RepoSource` `dir` is ignored for the step's execution. */
		dir: Option[String] = None,
	  /** Unique identifier for this build step, used in `wait_for` to reference this build step as a dependency. */
		id: Option[String] = None,
	  /** The ID(s) of the step(s) that this build step depends on. This build step will not start until all the build steps in `wait_for` have completed successfully. If `wait_for` is empty, this build step will start when all previous build steps in the `Build.Steps` list have completed successfully. */
		waitFor: Option[List[String]] = None,
	  /** Entrypoint to be used instead of the build step image's default entrypoint. If unset, the image's default entrypoint is used. */
		entrypoint: Option[String] = None,
	  /** A list of environment variables which are encrypted using a Cloud Key Management Service crypto key. These values must be specified in the build's `Secret`. */
		secretEnv: Option[List[String]] = None,
	  /** List of volumes to mount into the build step. Each volume is created as an empty volume prior to execution of the build step. Upon completion of the build, volumes and their contents are discarded. Using a named volume in only one step is not valid as it is indicative of a build request with an incorrect configuration. */
		volumes: Option[List[Schema.GoogleDevtoolsCloudbuildV1Volume]] = None,
	  /** Output only. Stores timing information for executing this build step. */
		timing: Option[Schema.GoogleDevtoolsCloudbuildV1TimeSpan] = None,
	  /** Output only. Stores timing information for pulling this build step's builder image only. */
		pullTiming: Option[Schema.GoogleDevtoolsCloudbuildV1TimeSpan] = None,
	  /** Time limit for executing this build step. If not defined, the step has no time limit and will be allowed to continue to run until either it completes or the build itself times out. */
		timeout: Option[String] = None,
	  /** Output only. Status of the build step. At this time, build step status is only updated on build completion; step status is not updated in real-time as the build progresses. */
		status: Option[Schema.GoogleDevtoolsCloudbuildV1BuildStep.StatusEnum] = None,
	  /** Allow this build step to fail without failing the entire build. If false, the entire build will fail if this step fails. Otherwise, the build will succeed, but this step will still have a failure status. Error information will be reported in the failure_detail field. */
		allowFailure: Option[Boolean] = None,
	  /** Output only. Return code from running the step. */
		exitCode: Option[Int] = None,
	  /** Allow this build step to fail without failing the entire build if and only if the exit code is one of the specified codes. If allow_failure is also specified, this field will take precedence. */
		allowExitCodes: Option[List[Int]] = None,
	  /** A shell script to be executed in the step. When script is provided, the user cannot specify the entrypoint or args. */
		script: Option[String] = None,
	  /** Option to include built-in and custom substitutions as env variables for this build step. This option will override the global option in BuildOption. */
		automapSubstitutions: Option[Boolean] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1Volume(
	  /** Name of the volume to mount. Volume names must be unique per build step and must be valid names for Docker volumes. Each named volume must be used by at least two build steps. */
		name: Option[String] = None,
	  /** Path at which to mount the volume. Paths must be absolute and cannot conflict with other volume paths on the same build step or with certain reserved volume paths. */
		path: Option[String] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1TimeSpan(
	  /** Start of time span. */
		startTime: Option[String] = None,
	  /** End of time span. */
		endTime: Option[String] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1Results(
	  /** Container images that were built as a part of the build. */
		images: Option[List[Schema.GoogleDevtoolsCloudbuildV1BuiltImage]] = None,
	  /** List of build step digests, in the order corresponding to build step indices. */
		buildStepImages: Option[List[String]] = None,
	  /** Path to the artifact manifest for non-container artifacts uploaded to Cloud Storage. Only populated when artifacts are uploaded to Cloud Storage. */
		artifactManifest: Option[String] = None,
	  /** Number of non-container artifacts uploaded to Cloud Storage. Only populated when artifacts are uploaded to Cloud Storage. */
		numArtifacts: Option[String] = None,
	  /** List of build step outputs, produced by builder images, in the order corresponding to build step indices. [Cloud Builders](https://cloud.google.com/cloud-build/docs/cloud-builders) can produce this output by writing to `$BUILDER_OUTPUT/output`. Only the first 50KB of data is stored. Note that the `$BUILDER_OUTPUT` variable is read-only and can't be substituted. */
		buildStepOutputs: Option[List[String]] = None,
	  /** Time to push all non-container artifacts to Cloud Storage. */
		artifactTiming: Option[Schema.GoogleDevtoolsCloudbuildV1TimeSpan] = None,
	  /** Python artifacts uploaded to Artifact Registry at the end of the build. */
		pythonPackages: Option[List[Schema.GoogleDevtoolsCloudbuildV1UploadedPythonPackage]] = None,
	  /** Maven artifacts uploaded to Artifact Registry at the end of the build. */
		mavenArtifacts: Option[List[Schema.GoogleDevtoolsCloudbuildV1UploadedMavenArtifact]] = None,
	  /** Npm packages uploaded to Artifact Registry at the end of the build. */
		npmPackages: Option[List[Schema.GoogleDevtoolsCloudbuildV1UploadedNpmPackage]] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1BuiltImage(
	  /** Name used to push the container image to Google Container Registry, as presented to `docker push`. */
		name: Option[String] = None,
	  /** Docker Registry 2.0 digest. */
		digest: Option[String] = None,
	  /** Output only. Stores timing information for pushing the specified image. */
		pushTiming: Option[Schema.GoogleDevtoolsCloudbuildV1TimeSpan] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1UploadedPythonPackage(
	  /** URI of the uploaded artifact. */
		uri: Option[String] = None,
	  /** Hash types and values of the Python Artifact. */
		fileHashes: Option[Schema.GoogleDevtoolsCloudbuildV1FileHashes] = None,
	  /** Output only. Stores timing information for pushing the specified artifact. */
		pushTiming: Option[Schema.GoogleDevtoolsCloudbuildV1TimeSpan] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1FileHashes(
	  /** Collection of file hashes. */
		fileHash: Option[List[Schema.GoogleDevtoolsCloudbuildV1Hash]] = None
	)
	
	object GoogleDevtoolsCloudbuildV1Hash {
		enum TypeEnum extends Enum[TypeEnum] { case NONE, SHA256, MD5, SHA512 }
	}
	case class GoogleDevtoolsCloudbuildV1Hash(
	  /** The type of hash that was performed. */
		`type`: Option[Schema.GoogleDevtoolsCloudbuildV1Hash.TypeEnum] = None,
	  /** The hash value. */
		value: Option[String] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1UploadedMavenArtifact(
	  /** URI of the uploaded artifact. */
		uri: Option[String] = None,
	  /** Hash types and values of the Maven Artifact. */
		fileHashes: Option[Schema.GoogleDevtoolsCloudbuildV1FileHashes] = None,
	  /** Output only. Stores timing information for pushing the specified artifact. */
		pushTiming: Option[Schema.GoogleDevtoolsCloudbuildV1TimeSpan] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1UploadedNpmPackage(
	  /** URI of the uploaded npm package. */
		uri: Option[String] = None,
	  /** Hash types and values of the npm package. */
		fileHashes: Option[Schema.GoogleDevtoolsCloudbuildV1FileHashes] = None,
	  /** Output only. Stores timing information for pushing the specified artifact. */
		pushTiming: Option[Schema.GoogleDevtoolsCloudbuildV1TimeSpan] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1Artifacts(
	  /** A list of images to be pushed upon the successful completion of all build steps. The images will be pushed using the builder service account's credentials. The digests of the pushed images will be stored in the Build resource's results field. If any of the images fail to be pushed, the build is marked FAILURE. */
		images: Option[List[String]] = None,
	  /** A list of objects to be uploaded to Cloud Storage upon successful completion of all build steps. Files in the workspace matching specified paths globs will be uploaded to the specified Cloud Storage location using the builder service account's credentials. The location and generation of the uploaded objects will be stored in the Build resource's results field. If any objects fail to be pushed, the build is marked FAILURE. */
		objects: Option[Schema.GoogleDevtoolsCloudbuildV1ArtifactObjects] = None,
	  /** A list of Maven artifacts to be uploaded to Artifact Registry upon successful completion of all build steps. Artifacts in the workspace matching specified paths globs will be uploaded to the specified Artifact Registry repository using the builder service account's credentials. If any artifacts fail to be pushed, the build is marked FAILURE. */
		mavenArtifacts: Option[List[Schema.GoogleDevtoolsCloudbuildV1MavenArtifact]] = None,
	  /** A list of Python packages to be uploaded to Artifact Registry upon successful completion of all build steps. The build service account credentials will be used to perform the upload. If any objects fail to be pushed, the build is marked FAILURE. */
		pythonPackages: Option[List[Schema.GoogleDevtoolsCloudbuildV1PythonPackage]] = None,
	  /** A list of npm packages to be uploaded to Artifact Registry upon successful completion of all build steps. Npm packages in the specified paths will be uploaded to the specified Artifact Registry repository using the builder service account's credentials. If any packages fail to be pushed, the build is marked FAILURE. */
		npmPackages: Option[List[Schema.GoogleDevtoolsCloudbuildV1NpmPackage]] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1ArtifactObjects(
	  /** Cloud Storage bucket and optional object path, in the form "gs://bucket/path/to/somewhere/". (see [Bucket Name Requirements](https://cloud.google.com/storage/docs/bucket-naming#requirements)). Files in the workspace matching any path pattern will be uploaded to Cloud Storage with this location as a prefix. */
		location: Option[String] = None,
	  /** Path globs used to match files in the build's workspace. */
		paths: Option[List[String]] = None,
	  /** Output only. Stores timing information for pushing all artifact objects. */
		timing: Option[Schema.GoogleDevtoolsCloudbuildV1TimeSpan] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1MavenArtifact(
	  /** Artifact Registry repository, in the form "https://$REGION-maven.pkg.dev/$PROJECT/$REPOSITORY" Artifact in the workspace specified by path will be uploaded to Artifact Registry with this location as a prefix. */
		repository: Option[String] = None,
	  /** Path to an artifact in the build's workspace to be uploaded to Artifact Registry. This can be either an absolute path, e.g. /workspace/my-app/target/my-app-1.0.SNAPSHOT.jar or a relative path from /workspace, e.g. my-app/target/my-app-1.0.SNAPSHOT.jar. */
		path: Option[String] = None,
	  /** Maven `artifactId` value used when uploading the artifact to Artifact Registry. */
		artifactId: Option[String] = None,
	  /** Maven `groupId` value used when uploading the artifact to Artifact Registry. */
		groupId: Option[String] = None,
	  /** Maven `version` value used when uploading the artifact to Artifact Registry. */
		version: Option[String] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1PythonPackage(
	  /** Artifact Registry repository, in the form "https://$REGION-python.pkg.dev/$PROJECT/$REPOSITORY" Files in the workspace matching any path pattern will be uploaded to Artifact Registry with this location as a prefix. */
		repository: Option[String] = None,
	  /** Path globs used to match files in the build's workspace. For Python/ Twine, this is usually `dist/&#42;`, and sometimes additionally an `.asc` file. */
		paths: Option[List[String]] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1NpmPackage(
	  /** Artifact Registry repository, in the form "https://$REGION-npm.pkg.dev/$PROJECT/$REPOSITORY" Npm package in the workspace specified by path will be zipped and uploaded to Artifact Registry with this location as a prefix. */
		repository: Option[String] = None,
	  /** Path to the package.json. e.g. workspace/path/to/package */
		packagePath: Option[String] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1SourceProvenance(
	  /** A copy of the build's `source.storage_source`, if exists, with any generations resolved. */
		resolvedStorageSource: Option[Schema.GoogleDevtoolsCloudbuildV1StorageSource] = None,
	  /** A copy of the build's `source.repo_source`, if exists, with any revisions resolved. */
		resolvedRepoSource: Option[Schema.GoogleDevtoolsCloudbuildV1RepoSource] = None,
	  /** A copy of the build's `source.storage_source_manifest`, if exists, with any revisions resolved. This feature is in Preview. */
		resolvedStorageSourceManifest: Option[Schema.GoogleDevtoolsCloudbuildV1StorageSourceManifest] = None,
	  /** Output only. A copy of the build's `source.connected_repository`, if exists, with any revisions resolved. */
		resolvedConnectedRepository: Option[Schema.GoogleDevtoolsCloudbuildV1ConnectedRepository] = None,
	  /** Output only. A copy of the build's `source.git_source`, if exists, with any revisions resolved. */
		resolvedGitSource: Option[Schema.GoogleDevtoolsCloudbuildV1GitSource] = None,
	  /** Output only. Hash(es) of the build source, which can be used to verify that the original source integrity was maintained in the build. Note that `FileHashes` will only be populated if `BuildOptions` has requested a `SourceProvenanceHash`. The keys to this map are file paths used as build source and the values contain the hash values for those files. If the build source came in a single package such as a gzipped tarfile (`.tar.gz`), the `FileHash` will be for the single path to that file. */
		fileHashes: Option[Map[String, Schema.GoogleDevtoolsCloudbuildV1FileHashes]] = None
	)
	
	object GoogleDevtoolsCloudbuildV1BuildOptions {
		enum SourceProvenanceHashEnum extends Enum[SourceProvenanceHashEnum] { case NONE, SHA256, MD5, SHA512 }
		enum RequestedVerifyOptionEnum extends Enum[RequestedVerifyOptionEnum] { case NOT_VERIFIED, VERIFIED }
		enum MachineTypeEnum extends Enum[MachineTypeEnum] { case UNSPECIFIED, N1_HIGHCPU_8, N1_HIGHCPU_32, E2_HIGHCPU_8, E2_HIGHCPU_32, E2_MEDIUM }
		enum SubstitutionOptionEnum extends Enum[SubstitutionOptionEnum] { case MUST_MATCH, ALLOW_LOOSE }
		enum LogStreamingOptionEnum extends Enum[LogStreamingOptionEnum] { case STREAM_DEFAULT, STREAM_ON, STREAM_OFF }
		enum LoggingEnum extends Enum[LoggingEnum] { case LOGGING_UNSPECIFIED, LEGACY, GCS_ONLY, STACKDRIVER_ONLY, CLOUD_LOGGING_ONLY, NONE }
		enum DefaultLogsBucketBehaviorEnum extends Enum[DefaultLogsBucketBehaviorEnum] { case DEFAULT_LOGS_BUCKET_BEHAVIOR_UNSPECIFIED, REGIONAL_USER_OWNED_BUCKET, LEGACY_BUCKET }
	}
	case class GoogleDevtoolsCloudbuildV1BuildOptions(
	  /** Requested hash for SourceProvenance. */
		sourceProvenanceHash: Option[List[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.SourceProvenanceHashEnum]] = None,
	  /** Requested verifiability options. */
		requestedVerifyOption: Option[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.RequestedVerifyOptionEnum] = None,
	  /** Compute Engine machine type on which to run the build. */
		machineType: Option[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.MachineTypeEnum] = None,
	  /** Requested disk size for the VM that runs the build. Note that this is &#42;NOT&#42; "disk free"; some of the space will be used by the operating system and build utilities. Also note that this is the minimum disk size that will be allocated for the build -- the build may run with a larger disk than requested. At present, the maximum disk size is 4000GB; builds that request more than the maximum are rejected with an error. */
		diskSizeGb: Option[String] = None,
	  /** Option to specify behavior when there is an error in the substitution checks. NOTE: this is always set to ALLOW_LOOSE for triggered builds and cannot be overridden in the build configuration file. */
		substitutionOption: Option[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.SubstitutionOptionEnum] = None,
	  /** Option to specify whether or not to apply bash style string operations to the substitutions. NOTE: this is always enabled for triggered builds and cannot be overridden in the build configuration file. */
		dynamicSubstitutions: Option[Boolean] = None,
	  /** Option to include built-in and custom substitutions as env variables for all build steps. */
		automapSubstitutions: Option[Boolean] = None,
	  /** Option to define build log streaming behavior to Cloud Storage. */
		logStreamingOption: Option[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.LogStreamingOptionEnum] = None,
	  /** This field deprecated; please use `pool.name` instead. */
		workerPool: Option[String] = None,
	  /** Optional. Specification for execution on a `WorkerPool`. See [running builds in a private pool](https://cloud.google.com/build/docs/private-pools/run-builds-in-private-pool) for more information. */
		pool: Option[Schema.GoogleDevtoolsCloudbuildV1PoolOption] = None,
	  /** Option to specify the logging mode, which determines if and where build logs are stored. */
		logging: Option[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.LoggingEnum] = None,
	  /** A list of global environment variable definitions that will exist for all build steps in this build. If a variable is defined in both globally and in a build step, the variable will use the build step value. The elements are of the form "KEY=VALUE" for the environment variable "KEY" being given the value "VALUE". */
		env: Option[List[String]] = None,
	  /** A list of global environment variables, which are encrypted using a Cloud Key Management Service crypto key. These values must be specified in the build's `Secret`. These variables will be available to all build steps in this build. */
		secretEnv: Option[List[String]] = None,
	  /** Global list of volumes to mount for ALL build steps Each volume is created as an empty volume prior to starting the build process. Upon completion of the build, volumes and their contents are discarded. Global volume names and paths cannot conflict with the volumes defined a build step. Using a global volume in a build with only one step is not valid as it is indicative of a build request with an incorrect configuration. */
		volumes: Option[List[Schema.GoogleDevtoolsCloudbuildV1Volume]] = None,
	  /** Optional. Option to specify how default logs buckets are setup. */
		defaultLogsBucketBehavior: Option[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.DefaultLogsBucketBehaviorEnum] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1PoolOption(
	  /** The `WorkerPool` resource to execute the build on. You must have `cloudbuild.workerpools.use` on the project hosting the WorkerPool. Format projects/{project}/locations/{location}/workerPools/{workerPoolId} */
		name: Option[String] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1Secret(
	  /** Cloud KMS key name to use to decrypt these envs. */
		kmsKeyName: Option[String] = None,
	  /** Map of environment variable name to its encrypted value. Secret environment variables must be unique across all of a build's secrets, and must be used by at least one build step. Values can be at most 64 KB in size. There can be at most 100 secret values across all of a build's secrets. */
		secretEnv: Option[Map[String, String]] = None
	)
	
	object GoogleDevtoolsCloudbuildV1BuildApproval {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, APPROVED, REJECTED, CANCELLED }
	}
	case class GoogleDevtoolsCloudbuildV1BuildApproval(
	  /** Output only. The state of this build's approval. */
		state: Option[Schema.GoogleDevtoolsCloudbuildV1BuildApproval.StateEnum] = None,
	  /** Output only. Configuration for manual approval of this build. */
		config: Option[Schema.GoogleDevtoolsCloudbuildV1ApprovalConfig] = None,
	  /** Output only. Result of manual approval for this Build. */
		result: Option[Schema.GoogleDevtoolsCloudbuildV1ApprovalResult] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1ApprovalConfig(
	  /** Whether or not approval is needed. If this is set on a build, it will become pending when created, and will need to be explicitly approved to start. */
		approvalRequired: Option[Boolean] = None
	)
	
	object GoogleDevtoolsCloudbuildV1ApprovalResult {
		enum DecisionEnum extends Enum[DecisionEnum] { case DECISION_UNSPECIFIED, APPROVED, REJECTED }
	}
	case class GoogleDevtoolsCloudbuildV1ApprovalResult(
	  /** Output only. Email of the user that called the ApproveBuild API to approve or reject a build at the time that the API was called. */
		approverAccount: Option[String] = None,
	  /** Output only. The time when the approval decision was made. */
		approvalTime: Option[String] = None,
	  /** Required. The decision of this manual approval. */
		decision: Option[Schema.GoogleDevtoolsCloudbuildV1ApprovalResult.DecisionEnum] = None,
	  /** Optional. An optional comment for this manual approval result. */
		comment: Option[String] = None,
	  /** Optional. An optional URL tied to this manual approval result. This field is essentially the same as comment, except that it will be rendered by the UI differently. An example use case is a link to an external job that approved this Build. */
		url: Option[String] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1Secrets(
	  /** Secrets in Secret Manager and associated secret environment variable. */
		secretManager: Option[List[Schema.GoogleDevtoolsCloudbuildV1SecretManagerSecret]] = None,
	  /** Secrets encrypted with KMS key and the associated secret environment variable. */
		inline: Option[List[Schema.GoogleDevtoolsCloudbuildV1InlineSecret]] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1SecretManagerSecret(
	  /** Resource name of the SecretVersion. In format: projects/&#42;/secrets/&#42;/versions/&#42; */
		versionName: Option[String] = None,
	  /** Environment variable name to associate with the secret. Secret environment variables must be unique across all of a build's secrets, and must be used by at least one build step. */
		env: Option[String] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1InlineSecret(
	  /** Resource name of Cloud KMS crypto key to decrypt the encrypted value. In format: projects/&#42;/locations/&#42;/keyRings/&#42;/cryptoKeys/&#42; */
		kmsKeyName: Option[String] = None,
	  /** Map of environment variable name to its encrypted value. Secret environment variables must be unique across all of a build's secrets, and must be used by at least one build step. Values can be at most 64 KB in size. There can be at most 100 secret values across all of a build's secrets. */
		envMap: Option[Map[String, String]] = None
	)
	
	object GoogleDevtoolsCloudbuildV1Warning {
		enum PriorityEnum extends Enum[PriorityEnum] { case PRIORITY_UNSPECIFIED, INFO, WARNING, ALERT }
	}
	case class GoogleDevtoolsCloudbuildV1Warning(
	  /** Explanation of the warning generated. */
		text: Option[String] = None,
	  /** The priority for this warning. */
		priority: Option[Schema.GoogleDevtoolsCloudbuildV1Warning.PriorityEnum] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1GitConfig(
	  /** Configuration for HTTP related git operations. */
		http: Option[Schema.GoogleDevtoolsCloudbuildV1HttpConfig] = None
	)
	
	case class GoogleDevtoolsCloudbuildV1HttpConfig(
	  /** SecretVersion resource of the HTTP proxy URL. The Service Account used in the build (either the default Service Account or user-specified Service Account) should have `secretmanager.versions.access` permissions on this secret. The proxy URL should be in format `protocol://@]proxyhost[:port]`. */
		proxySecretVersionName: Option[String] = None
	)
	
	object GoogleDevtoolsCloudbuildV1FailureInfo {
		enum TypeEnum extends Enum[TypeEnum] { case FAILURE_TYPE_UNSPECIFIED, PUSH_FAILED, PUSH_IMAGE_NOT_FOUND, PUSH_NOT_AUTHORIZED, LOGGING_FAILURE, USER_BUILD_STEP, FETCH_SOURCE_FAILED }
	}
	case class GoogleDevtoolsCloudbuildV1FailureInfo(
	  /** The name of the failure. */
		`type`: Option[Schema.GoogleDevtoolsCloudbuildV1FailureInfo.TypeEnum] = None,
	  /** Explains the failure issue in more detail using hard-coded text. */
		detail: Option[String] = None
	)
}
