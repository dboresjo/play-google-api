package com.boresjo.play.api.google.dataproc

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
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the name should be a resource name ending with operations/{unique_id}. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is false, it means the operation is still in progress. If true, the operation is completed, and either error or response is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as Delete, the response is google.protobuf.Empty. If the original method is standard Get/Create/Update, the response should be the resource. For other methods, the response should have the type XxxResponse, where Xxx is the original method name. For example, if the original method name is TakeSnapshot(), the inferred response type is TakeSnapshotResponse. */
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
	
	case class AnalyzeBatchRequest(
	  /** Optional. A unique ID used to identify the request. If the service receives two AnalyzeBatchRequest (http://cloud/dataproc/docs/reference/rpc/google.cloud.dataproc.v1#google.cloud.dataproc.v1.AnalyzeBatchRequest)s with the same request_id, the second request is ignored and the Operation that corresponds to the first request created and stored in the backend is returned.Recommendation: Set this value to a UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier).The value must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
		requestId: Option[String] = None
	)
	
	case class AutoscalingPolicy(
	  /** Required. The policy id.The id must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). Cannot begin or end with underscore or hyphen. Must consist of between 3 and 50 characters. */
		id: Option[String] = None,
	  /** Output only. The "resource name" of the autoscaling policy, as described in https://cloud.google.com/apis/design/resource_names. For projects.regions.autoscalingPolicies, the resource name of the policy has the following format: projects/{project_id}/regions/{region}/autoscalingPolicies/{policy_id} For projects.locations.autoscalingPolicies, the resource name of the policy has the following format: projects/{project_id}/locations/{location}/autoscalingPolicies/{policy_id} */
		name: Option[String] = None,
		basicAlgorithm: Option[Schema.BasicAutoscalingAlgorithm] = None,
	  /** Required. Describes how the autoscaler will operate for primary workers. */
		workerConfig: Option[Schema.InstanceGroupAutoscalingPolicyConfig] = None,
	  /** Optional. Describes how the autoscaler will operate for secondary workers. */
		secondaryWorkerConfig: Option[Schema.InstanceGroupAutoscalingPolicyConfig] = None,
	  /** Optional. The labels to associate with this autoscaling policy. Label keys must contain 1 to 63 characters, and must conform to RFC 1035 (https://www.ietf.org/rfc/rfc1035.txt). Label values may be empty, but, if present, must contain 1 to 63 characters, and must conform to RFC 1035 (https://www.ietf.org/rfc/rfc1035.txt). No more than 32 labels can be associated with an autoscaling policy. */
		labels: Option[Map[String, String]] = None
	)
	
	case class BasicAutoscalingAlgorithm(
	  /** Optional. YARN autoscaling configuration. */
		yarnConfig: Option[Schema.BasicYarnAutoscalingConfig] = None,
	  /** Optional. Spark Standalone autoscaling configuration */
		sparkStandaloneConfig: Option[Schema.SparkStandaloneAutoscalingConfig] = None,
	  /** Optional. Duration between scaling events. A scaling period starts after the update operation from the previous event has completed.Bounds: 2m, 1d. Default: 2m. */
		cooldownPeriod: Option[String] = None
	)
	
	case class BasicYarnAutoscalingConfig(
	  /** Required. Timeout for YARN graceful decommissioning of Node Managers. Specifies the duration to wait for jobs to complete before forcefully removing workers (and potentially interrupting jobs). Only applicable to downscaling operations.Bounds: 0s, 1d. */
		gracefulDecommissionTimeout: Option[String] = None,
	  /** Required. Fraction of average YARN pending memory in the last cooldown period for which to add workers. A scale-up factor of 1.0 will result in scaling up so that there is no pending memory remaining after the update (more aggressive scaling). A scale-up factor closer to 0 will result in a smaller magnitude of scaling up (less aggressive scaling). See How autoscaling works (https://cloud.google.com/dataproc/docs/concepts/configuring-clusters/autoscaling#how_autoscaling_works) for more information.Bounds: 0.0, 1.0. */
		scaleUpFactor: Option[BigDecimal] = None,
	  /** Required. Fraction of average YARN pending memory in the last cooldown period for which to remove workers. A scale-down factor of 1 will result in scaling down so that there is no available memory remaining after the update (more aggressive scaling). A scale-down factor of 0 disables removing workers, which can be beneficial for autoscaling a single job. See How autoscaling works (https://cloud.google.com/dataproc/docs/concepts/configuring-clusters/autoscaling#how_autoscaling_works) for more information.Bounds: 0.0, 1.0. */
		scaleDownFactor: Option[BigDecimal] = None,
	  /** Optional. Minimum scale-up threshold as a fraction of total cluster size before scaling occurs. For example, in a 20-worker cluster, a threshold of 0.1 means the autoscaler must recommend at least a 2-worker scale-up for the cluster to scale. A threshold of 0 means the autoscaler will scale up on any recommended change.Bounds: 0.0, 1.0. Default: 0.0. */
		scaleUpMinWorkerFraction: Option[BigDecimal] = None,
	  /** Optional. Minimum scale-down threshold as a fraction of total cluster size before scaling occurs. For example, in a 20-worker cluster, a threshold of 0.1 means the autoscaler must recommend at least a 2 worker scale-down for the cluster to scale. A threshold of 0 means the autoscaler will scale down on any recommended change.Bounds: 0.0, 1.0. Default: 0.0. */
		scaleDownMinWorkerFraction: Option[BigDecimal] = None
	)
	
	case class SparkStandaloneAutoscalingConfig(
	  /** Required. Timeout for Spark graceful decommissioning of spark workers. Specifies the duration to wait for spark worker to complete spark decommissioning tasks before forcefully removing workers. Only applicable to downscaling operations.Bounds: 0s, 1d. */
		gracefulDecommissionTimeout: Option[String] = None,
	  /** Required. Fraction of required workers to add to Spark Standalone clusters. A scale-up factor of 1.0 will result in scaling up so that there are no more required workers for the Spark Job (more aggressive scaling). A scale-up factor closer to 0 will result in a smaller magnitude of scaling up (less aggressive scaling).Bounds: 0.0, 1.0. */
		scaleUpFactor: Option[BigDecimal] = None,
	  /** Required. Fraction of required executors to remove from Spark Serverless clusters. A scale-down factor of 1.0 will result in scaling down so that there are no more executors for the Spark Job.(more aggressive scaling). A scale-down factor closer to 0 will result in a smaller magnitude of scaling donw (less aggressive scaling).Bounds: 0.0, 1.0. */
		scaleDownFactor: Option[BigDecimal] = None,
	  /** Optional. Minimum scale-up threshold as a fraction of total cluster size before scaling occurs. For example, in a 20-worker cluster, a threshold of 0.1 means the autoscaler must recommend at least a 2-worker scale-up for the cluster to scale. A threshold of 0 means the autoscaler will scale up on any recommended change.Bounds: 0.0, 1.0. Default: 0.0. */
		scaleUpMinWorkerFraction: Option[BigDecimal] = None,
	  /** Optional. Minimum scale-down threshold as a fraction of total cluster size before scaling occurs. For example, in a 20-worker cluster, a threshold of 0.1 means the autoscaler must recommend at least a 2 worker scale-down for the cluster to scale. A threshold of 0 means the autoscaler will scale down on any recommended change.Bounds: 0.0, 1.0. Default: 0.0. */
		scaleDownMinWorkerFraction: Option[BigDecimal] = None,
	  /** Optional. Remove only idle workers when scaling down cluster */
		removeOnlyIdleWorkers: Option[Boolean] = None
	)
	
	case class InstanceGroupAutoscalingPolicyConfig(
	  /** Optional. Minimum number of instances for this group.Primary workers - Bounds: 2, max_instances. Default: 2. Secondary workers - Bounds: 0, max_instances. Default: 0. */
		minInstances: Option[Int] = None,
	  /** Required. Maximum number of instances for this group. Required for primary workers. Note that by default, clusters will not use secondary workers. Required for secondary workers if the minimum secondary instances is set.Primary workers - Bounds: [min_instances, ). Secondary workers - Bounds: [min_instances, ). Default: 0. */
		maxInstances: Option[Int] = None,
	  /** Optional. Weight for the instance group, which is used to determine the fraction of total workers in the cluster from this instance group. For example, if primary workers have weight 2, and secondary workers have weight 1, the cluster will have approximately 2 primary workers for each secondary worker.The cluster may not reach the specified balance if constrained by min/max bounds or other autoscaling settings. For example, if max_instances for secondary workers is 0, then only primary workers will be added. The cluster can also be out of balance when created.If weight is not set on any instance group, the cluster will default to equal weight for all groups: the cluster will attempt to maintain an equal number of workers in each group within the configured size bounds for each group. If weight is set for one group only, the cluster will default to zero weight on the unset group. For example if weight is set only on primary workers, the cluster will use primary workers only and no secondary workers. */
		weight: Option[Int] = None
	)
	
	case class ListAutoscalingPoliciesResponse(
	  /** Output only. Autoscaling policies list. */
		policies: Option[List[Schema.AutoscalingPolicy]] = None,
	  /** Output only. This token is included in the response if there are more results to fetch. */
		nextPageToken: Option[String] = None
	)
	
	object Batch {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, RUNNING, CANCELLING, CANCELLED, SUCCEEDED, FAILED }
	}
	case class Batch(
	  /** Output only. The resource name of the batch. */
		name: Option[String] = None,
	  /** Output only. A batch UUID (Unique Universal Identifier). The service generates this value when it creates the batch. */
		uuid: Option[String] = None,
	  /** Output only. The time when the batch was created. */
		createTime: Option[String] = None,
	  /** Optional. PySpark batch config. */
		pysparkBatch: Option[Schema.PySparkBatch] = None,
	  /** Optional. Spark batch config. */
		sparkBatch: Option[Schema.SparkBatch] = None,
	  /** Optional. SparkR batch config. */
		sparkRBatch: Option[Schema.SparkRBatch] = None,
	  /** Optional. SparkSql batch config. */
		sparkSqlBatch: Option[Schema.SparkSqlBatch] = None,
	  /** Output only. Runtime information about batch execution. */
		runtimeInfo: Option[Schema.RuntimeInfo] = None,
	  /** Output only. The state of the batch. */
		state: Option[Schema.Batch.StateEnum] = None,
	  /** Output only. Batch state details, such as a failure description if the state is FAILED. */
		stateMessage: Option[String] = None,
	  /** Output only. The time when the batch entered a current state. */
		stateTime: Option[String] = None,
	  /** Output only. The email address of the user who created the batch. */
		creator: Option[String] = None,
	  /** Optional. The labels to associate with this batch. Label keys must contain 1 to 63 characters, and must conform to RFC 1035 (https://www.ietf.org/rfc/rfc1035.txt). Label values may be empty, but, if present, must contain 1 to 63 characters, and must conform to RFC 1035 (https://www.ietf.org/rfc/rfc1035.txt). No more than 32 labels can be associated with a batch. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Runtime configuration for the batch execution. */
		runtimeConfig: Option[Schema.RuntimeConfig] = None,
	  /** Optional. Environment configuration for the batch execution. */
		environmentConfig: Option[Schema.EnvironmentConfig] = None,
	  /** Output only. The resource name of the operation associated with this batch. */
		operation: Option[String] = None,
	  /** Output only. Historical state information for the batch. */
		stateHistory: Option[List[Schema.StateHistory]] = None
	)
	
	case class PySparkBatch(
	  /** Required. The HCFS URI of the main Python file to use as the Spark driver. Must be a .py file. */
		mainPythonFileUri: Option[String] = None,
	  /** Optional. The arguments to pass to the driver. Do not include arguments that can be set as batch properties, such as --conf, since a collision can occur that causes an incorrect batch submission. */
		args: Option[List[String]] = None,
	  /** Optional. HCFS file URIs of Python files to pass to the PySpark framework. Supported file types: .py, .egg, and .zip. */
		pythonFileUris: Option[List[String]] = None,
	  /** Optional. HCFS URIs of jar files to add to the classpath of the Spark driver and tasks. */
		jarFileUris: Option[List[String]] = None,
	  /** Optional. HCFS URIs of files to be placed in the working directory of each executor. */
		fileUris: Option[List[String]] = None,
	  /** Optional. HCFS URIs of archives to be extracted into the working directory of each executor. Supported file types: .jar, .tar, .tar.gz, .tgz, and .zip. */
		archiveUris: Option[List[String]] = None
	)
	
	case class SparkBatch(
	  /** Optional. The HCFS URI of the jar file that contains the main class. */
		mainJarFileUri: Option[String] = None,
	  /** Optional. The name of the driver main class. The jar file that contains the class must be in the classpath or specified in jar_file_uris. */
		mainClass: Option[String] = None,
	  /** Optional. The arguments to pass to the driver. Do not include arguments that can be set as batch properties, such as --conf, since a collision can occur that causes an incorrect batch submission. */
		args: Option[List[String]] = None,
	  /** Optional. HCFS URIs of jar files to add to the classpath of the Spark driver and tasks. */
		jarFileUris: Option[List[String]] = None,
	  /** Optional. HCFS URIs of files to be placed in the working directory of each executor. */
		fileUris: Option[List[String]] = None,
	  /** Optional. HCFS URIs of archives to be extracted into the working directory of each executor. Supported file types: .jar, .tar, .tar.gz, .tgz, and .zip. */
		archiveUris: Option[List[String]] = None
	)
	
	case class SparkRBatch(
	  /** Required. The HCFS URI of the main R file to use as the driver. Must be a .R or .r file. */
		mainRFileUri: Option[String] = None,
	  /** Optional. The arguments to pass to the Spark driver. Do not include arguments that can be set as batch properties, such as --conf, since a collision can occur that causes an incorrect batch submission. */
		args: Option[List[String]] = None,
	  /** Optional. HCFS URIs of files to be placed in the working directory of each executor. */
		fileUris: Option[List[String]] = None,
	  /** Optional. HCFS URIs of archives to be extracted into the working directory of each executor. Supported file types: .jar, .tar, .tar.gz, .tgz, and .zip. */
		archiveUris: Option[List[String]] = None
	)
	
	case class SparkSqlBatch(
	  /** Required. The HCFS URI of the script that contains Spark SQL queries to execute. */
		queryFileUri: Option[String] = None,
	  /** Optional. Mapping of query variable names to values (equivalent to the Spark SQL command: SET name="value";). */
		queryVariables: Option[Map[String, String]] = None,
	  /** Optional. HCFS URIs of jar files to be added to the Spark CLASSPATH. */
		jarFileUris: Option[List[String]] = None
	)
	
	case class RuntimeInfo(
	  /** Output only. Map of remote access endpoints (such as web interfaces and APIs) to their URIs. */
		endpoints: Option[Map[String, String]] = None,
	  /** Output only. A URI pointing to the location of the stdout and stderr of the workload. */
		outputUri: Option[String] = None,
	  /** Output only. A URI pointing to the location of the diagnostics tarball. */
		diagnosticOutputUri: Option[String] = None,
	  /** Output only. Approximate workload resource usage, calculated when the workload completes (see Dataproc Serverless pricing (https://cloud.google.com/dataproc-serverless/pricing)).Note: This metric calculation may change in the future, for example, to capture cumulative workload resource consumption during workload execution (see the Dataproc Serverless release notes (https://cloud.google.com/dataproc-serverless/docs/release-notes) for announcements, changes, fixes and other Dataproc developments). */
		approximateUsage: Option[Schema.UsageMetrics] = None,
	  /** Output only. Snapshot of current workload resource usage. */
		currentUsage: Option[Schema.UsageSnapshot] = None
	)
	
	case class UsageMetrics(
	  /** Optional. DCU (Dataproc Compute Units) usage in (milliDCU x seconds) (see Dataproc Serverless pricing (https://cloud.google.com/dataproc-serverless/pricing)). */
		milliDcuSeconds: Option[String] = None,
	  /** Optional. Shuffle storage usage in (GB x seconds) (see Dataproc Serverless pricing (https://cloud.google.com/dataproc-serverless/pricing)). */
		shuffleStorageGbSeconds: Option[String] = None,
	  /** Optional. Accelerator usage in (milliAccelerator x seconds) (see Dataproc Serverless pricing (https://cloud.google.com/dataproc-serverless/pricing)). */
		milliAcceleratorSeconds: Option[String] = None,
	  /** Optional. Accelerator type being used, if any */
		acceleratorType: Option[String] = None
	)
	
	case class UsageSnapshot(
	  /** Optional. Milli (one-thousandth) Dataproc Compute Units (DCUs) (see Dataproc Serverless pricing (https://cloud.google.com/dataproc-serverless/pricing)). */
		milliDcu: Option[String] = None,
	  /** Optional. Shuffle Storage in gigabytes (GB). (see Dataproc Serverless pricing (https://cloud.google.com/dataproc-serverless/pricing)) */
		shuffleStorageGb: Option[String] = None,
	  /** Optional. Milli (one-thousandth) Dataproc Compute Units (DCUs) charged at premium tier (see Dataproc Serverless pricing (https://cloud.google.com/dataproc-serverless/pricing)). */
		milliDcuPremium: Option[String] = None,
	  /** Optional. Shuffle Storage in gigabytes (GB) charged at premium tier. (see Dataproc Serverless pricing (https://cloud.google.com/dataproc-serverless/pricing)) */
		shuffleStorageGbPremium: Option[String] = None,
	  /** Optional. Milli (one-thousandth) accelerator. (see Dataproc Serverless pricing (https://cloud.google.com/dataproc-serverless/pricing)) */
		milliAccelerator: Option[String] = None,
	  /** Optional. Accelerator type being used, if any */
		acceleratorType: Option[String] = None,
	  /** Optional. The timestamp of the usage snapshot. */
		snapshotTime: Option[String] = None
	)
	
	case class RuntimeConfig(
	  /** Optional. Version of the batch runtime. */
		version: Option[String] = None,
	  /** Optional. Optional custom container image for the job runtime environment. If not specified, a default container image will be used. */
		containerImage: Option[String] = None,
	  /** Optional. A mapping of property names to values, which are used to configure workload execution. */
		properties: Option[Map[String, String]] = None,
	  /** Optional. Dependency repository configuration. */
		repositoryConfig: Option[Schema.RepositoryConfig] = None,
	  /** Optional. Autotuning configuration of the workload. */
		autotuningConfig: Option[Schema.AutotuningConfig] = None,
	  /** Optional. Cohort identifier. Identifies families of the workloads having the same shape, e.g. daily ETL jobs. */
		cohort: Option[String] = None
	)
	
	case class RepositoryConfig(
	  /** Optional. Configuration for PyPi repository. */
		pypiRepositoryConfig: Option[Schema.PyPiRepositoryConfig] = None
	)
	
	case class PyPiRepositoryConfig(
	  /** Optional. PyPi repository address */
		pypiRepository: Option[String] = None
	)
	
	object AutotuningConfig {
		enum ScenariosEnum extends Enum[ScenariosEnum] { case SCENARIO_UNSPECIFIED, SCALING, BROADCAST_HASH_JOIN, MEMORY }
	}
	case class AutotuningConfig(
	  /** Optional. Scenarios for which tunings are applied. */
		scenarios: Option[List[Schema.AutotuningConfig.ScenariosEnum]] = None
	)
	
	case class EnvironmentConfig(
	  /** Optional. Execution configuration for a workload. */
		executionConfig: Option[Schema.ExecutionConfig] = None,
	  /** Optional. Peripherals configuration that workload has access to. */
		peripheralsConfig: Option[Schema.PeripheralsConfig] = None
	)
	
	case class ExecutionConfig(
	  /** Optional. Service account that used to execute workload. */
		serviceAccount: Option[String] = None,
	  /** Optional. Network URI to connect workload to. */
		networkUri: Option[String] = None,
	  /** Optional. Subnetwork URI to connect workload to. */
		subnetworkUri: Option[String] = None,
	  /** Optional. Tags used for network traffic control. */
		networkTags: Option[List[String]] = None,
	  /** Optional. The Cloud KMS key to use for encryption. */
		kmsKey: Option[String] = None,
	  /** Optional. Applies to sessions only. The duration to keep the session alive while it's idling. Exceeding this threshold causes the session to terminate. This field cannot be set on a batch workload. Minimum value is 10 minutes; maximum value is 14 days (see JSON representation of Duration (https://developers.google.com/protocol-buffers/docs/proto3#json)). Defaults to 1 hour if not set. If both ttl and idle_ttl are specified for an interactive session, the conditions are treated as OR conditions: the workload will be terminated when it has been idle for idle_ttl or when ttl has been exceeded, whichever occurs first. */
		idleTtl: Option[String] = None,
	  /** Optional. The duration after which the workload will be terminated, specified as the JSON representation for Duration (https://protobuf.dev/programming-guides/proto3/#json). When the workload exceeds this duration, it will be unconditionally terminated without waiting for ongoing work to finish. If ttl is not specified for a batch workload, the workload will be allowed to run until it exits naturally (or run forever without exiting). If ttl is not specified for an interactive session, it defaults to 24 hours. If ttl is not specified for a batch that uses 2.1+ runtime version, it defaults to 4 hours. Minimum value is 10 minutes; maximum value is 14 days. If both ttl and idle_ttl are specified (for an interactive session), the conditions are treated as OR conditions: the workload will be terminated when it has been idle for idle_ttl or when ttl has been exceeded, whichever occurs first. */
		ttl: Option[String] = None,
	  /** Optional. A Cloud Storage bucket used to stage workload dependencies, config files, and store workload output and other ephemeral data, such as Spark history files. If you do not specify a staging bucket, Cloud Dataproc will determine a Cloud Storage location according to the region where your workload is running, and then create and manage project-level, per-location staging and temporary buckets. This field requires a Cloud Storage bucket name, not a gs://... URI to a Cloud Storage bucket. */
		stagingBucket: Option[String] = None
	)
	
	case class PeripheralsConfig(
	  /** Optional. Resource name of an existing Dataproc Metastore service.Example: projects/[project_id]/locations/[region]/services/[service_id] */
		metastoreService: Option[String] = None,
	  /** Optional. The Spark History Server configuration for the workload. */
		sparkHistoryServerConfig: Option[Schema.SparkHistoryServerConfig] = None
	)
	
	case class SparkHistoryServerConfig(
	  /** Optional. Resource name of an existing Dataproc Cluster to act as a Spark History Server for the workload.Example: projects/[project_id]/regions/[region]/clusters/[cluster_name] */
		dataprocCluster: Option[String] = None
	)
	
	object StateHistory {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, RUNNING, CANCELLING, CANCELLED, SUCCEEDED, FAILED }
	}
	case class StateHistory(
	  /** Output only. The state of the batch at this point in history. */
		state: Option[Schema.StateHistory.StateEnum] = None,
	  /** Output only. Details about the state at this point in history. */
		stateMessage: Option[String] = None,
	  /** Output only. The time when the batch entered the historical state. */
		stateStartTime: Option[String] = None
	)
	
	case class ListBatchesResponse(
	  /** Output only. The batches from the specified collection. */
		batches: Option[List[Schema.Batch]] = None,
	  /** A token, which can be sent as page_token to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Output only. List of Batches that could not be included in the response. Attempting to get one of these resources may indicate why it was not included in the list response. */
		unreachable: Option[List[String]] = None
	)
	
	case class WriteSparkApplicationContextRequest(
	  /** Required. Parent (Batch) resource reference. */
		parent: Option[String] = None,
		sparkWrapperObjects: Option[List[Schema.SparkWrapperObject]] = None
	)
	
	case class SparkWrapperObject(
	  /** VM Timestamp associated with the data object. */
		eventTimestamp: Option[String] = None,
	  /** Application Id created by Spark. */
		applicationId: Option[String] = None,
		applicationInfo: Option[Schema.ApplicationInfo] = None,
		applicationEnvironmentInfo: Option[Schema.ApplicationEnvironmentInfo] = None,
		resourceProfileInfo: Option[Schema.ResourceProfileInfo] = None,
		appSummary: Option[Schema.AppSummary] = None,
		jobData: Option[Schema.JobData] = None,
		stageData: Option[Schema.StageData] = None,
		taskData: Option[Schema.TaskData] = None,
		executorStageSummary: Option[Schema.ExecutorStageSummary] = None,
		speculationStageSummary: Option[Schema.SpeculationStageSummary] = None,
		executorSummary: Option[Schema.ExecutorSummary] = None,
		rddStorageInfo: Option[Schema.RddStorageInfo] = None,
		streamBlockData: Option[Schema.StreamBlockData] = None,
		rddOperationGraph: Option[Schema.RddOperationGraph] = None,
		poolData: Option[Schema.PoolData] = None,
		processSummary: Option[Schema.ProcessSummary] = None,
		sqlExecutionUiData: Option[Schema.SqlExecutionUiData] = None,
		sparkPlanGraph: Option[Schema.SparkPlanGraph] = None,
		streamingQueryData: Option[Schema.StreamingQueryData] = None,
		streamingQueryProgress: Option[Schema.StreamingQueryProgress] = None
	)
	
	object ApplicationInfo {
		enum ApplicationContextIngestionStatusEnum extends Enum[ApplicationContextIngestionStatusEnum] { case APPLICATION_CONTEXT_INGESTION_STATUS_UNSPECIFIED, APPLICATION_CONTEXT_INGESTION_STATUS_COMPLETED }
		enum QuantileDataStatusEnum extends Enum[QuantileDataStatusEnum] { case QUANTILE_DATA_STATUS_UNSPECIFIED, QUANTILE_DATA_STATUS_COMPLETED, QUANTILE_DATA_STATUS_FAILED }
	}
	case class ApplicationInfo(
		applicationId: Option[String] = None,
		name: Option[String] = None,
		coresGranted: Option[Int] = None,
		maxCores: Option[Int] = None,
		coresPerExecutor: Option[Int] = None,
		memoryPerExecutorMb: Option[Int] = None,
		attempts: Option[List[Schema.ApplicationAttemptInfo]] = None,
		applicationContextIngestionStatus: Option[Schema.ApplicationInfo.ApplicationContextIngestionStatusEnum] = None,
		quantileDataStatus: Option[Schema.ApplicationInfo.QuantileDataStatusEnum] = None
	)
	
	case class ApplicationAttemptInfo(
		attemptId: Option[String] = None,
		startTime: Option[String] = None,
		endTime: Option[String] = None,
		lastUpdated: Option[String] = None,
		durationMillis: Option[String] = None,
		sparkUser: Option[String] = None,
		completed: Option[Boolean] = None,
		appSparkVersion: Option[String] = None
	)
	
	case class ApplicationEnvironmentInfo(
		runtime: Option[Schema.SparkRuntimeInfo] = None,
		sparkProperties: Option[Map[String, String]] = None,
		hadoopProperties: Option[Map[String, String]] = None,
		systemProperties: Option[Map[String, String]] = None,
		metricsProperties: Option[Map[String, String]] = None,
		classpathEntries: Option[Map[String, String]] = None,
		resourceProfiles: Option[List[Schema.ResourceProfileInfo]] = None
	)
	
	case class SparkRuntimeInfo(
		javaVersion: Option[String] = None,
		javaHome: Option[String] = None,
		scalaVersion: Option[String] = None
	)
	
	case class ResourceProfileInfo(
		resourceProfileId: Option[Int] = None,
		executorResources: Option[Map[String, Schema.ExecutorResourceRequest]] = None,
		taskResources: Option[Map[String, Schema.TaskResourceRequest]] = None
	)
	
	case class ExecutorResourceRequest(
		resourceName: Option[String] = None,
		amount: Option[String] = None,
		discoveryScript: Option[String] = None,
		vendor: Option[String] = None
	)
	
	case class TaskResourceRequest(
		resourceName: Option[String] = None,
		amount: Option[BigDecimal] = None
	)
	
	case class AppSummary(
		numCompletedJobs: Option[Int] = None,
		numCompletedStages: Option[Int] = None
	)
	
	object JobData {
		enum StatusEnum extends Enum[StatusEnum] { case JOB_EXECUTION_STATUS_UNSPECIFIED, JOB_EXECUTION_STATUS_RUNNING, JOB_EXECUTION_STATUS_SUCCEEDED, JOB_EXECUTION_STATUS_FAILED, JOB_EXECUTION_STATUS_UNKNOWN }
	}
	case class JobData(
		jobId: Option[String] = None,
		name: Option[String] = None,
		description: Option[String] = None,
		submissionTime: Option[String] = None,
		completionTime: Option[String] = None,
		stageIds: Option[List[String]] = None,
		jobGroup: Option[String] = None,
		status: Option[Schema.JobData.StatusEnum] = None,
		numTasks: Option[Int] = None,
		numActiveTasks: Option[Int] = None,
		numCompletedTasks: Option[Int] = None,
		numSkippedTasks: Option[Int] = None,
		numFailedTasks: Option[Int] = None,
		numKilledTasks: Option[Int] = None,
		numCompletedIndices: Option[Int] = None,
		numActiveStages: Option[Int] = None,
		numCompletedStages: Option[Int] = None,
		numSkippedStages: Option[Int] = None,
		numFailedStages: Option[Int] = None,
		killTasksSummary: Option[Map[String, Int]] = None,
		skippedStages: Option[List[Int]] = None,
		sqlExecutionId: Option[String] = None
	)
	
	object StageData {
		enum StatusEnum extends Enum[StatusEnum] { case STAGE_STATUS_UNSPECIFIED, STAGE_STATUS_ACTIVE, STAGE_STATUS_COMPLETE, STAGE_STATUS_FAILED, STAGE_STATUS_PENDING, STAGE_STATUS_SKIPPED }
	}
	case class StageData(
		status: Option[Schema.StageData.StatusEnum] = None,
		stageId: Option[String] = None,
		stageAttemptId: Option[Int] = None,
		numTasks: Option[Int] = None,
		numActiveTasks: Option[Int] = None,
		numCompleteTasks: Option[Int] = None,
		numFailedTasks: Option[Int] = None,
		numKilledTasks: Option[Int] = None,
		numCompletedIndices: Option[Int] = None,
		submissionTime: Option[String] = None,
		firstTaskLaunchedTime: Option[String] = None,
		completionTime: Option[String] = None,
		failureReason: Option[String] = None,
		stageMetrics: Option[Schema.StageMetrics] = None,
		name: Option[String] = None,
		description: Option[String] = None,
		details: Option[String] = None,
		schedulingPool: Option[String] = None,
		rddIds: Option[List[String]] = None,
		accumulatorUpdates: Option[List[Schema.AccumulableInfo]] = None,
		tasks: Option[Map[String, Schema.TaskData]] = None,
		executorSummary: Option[Map[String, Schema.ExecutorStageSummary]] = None,
		speculationSummary: Option[Schema.SpeculationStageSummary] = None,
		killedTasksSummary: Option[Map[String, Int]] = None,
		resourceProfileId: Option[Int] = None,
		peakExecutorMetrics: Option[Schema.ExecutorMetrics] = None,
		executorMetricsDistributions: Option[Schema.ExecutorMetricsDistributions] = None,
		isShufflePushEnabled: Option[Boolean] = None,
		shuffleMergersCount: Option[Int] = None,
		jobIds: Option[List[String]] = None,
		locality: Option[Map[String, String]] = None,
		parentStageIds: Option[List[String]] = None,
	  /** Summary metrics fields. These are included in response only if present in summary_metrics_mask field in request */
		taskQuantileMetrics: Option[Schema.TaskQuantileMetrics] = None
	)
	
	case class StageMetrics(
		executorDeserializeTimeMillis: Option[String] = None,
		executorDeserializeCpuTimeNanos: Option[String] = None,
		executorRunTimeMillis: Option[String] = None,
		executorCpuTimeNanos: Option[String] = None,
		resultSize: Option[String] = None,
		jvmGcTimeMillis: Option[String] = None,
		resultSerializationTimeMillis: Option[String] = None,
		memoryBytesSpilled: Option[String] = None,
		diskBytesSpilled: Option[String] = None,
		peakExecutionMemoryBytes: Option[String] = None,
		stageInputMetrics: Option[Schema.StageInputMetrics] = None,
		stageOutputMetrics: Option[Schema.StageOutputMetrics] = None,
		stageShuffleReadMetrics: Option[Schema.StageShuffleReadMetrics] = None,
		stageShuffleWriteMetrics: Option[Schema.StageShuffleWriteMetrics] = None
	)
	
	case class StageInputMetrics(
		bytesRead: Option[String] = None,
		recordsRead: Option[String] = None
	)
	
	case class StageOutputMetrics(
		bytesWritten: Option[String] = None,
		recordsWritten: Option[String] = None
	)
	
	case class StageShuffleReadMetrics(
		remoteBlocksFetched: Option[String] = None,
		localBlocksFetched: Option[String] = None,
		fetchWaitTimeMillis: Option[String] = None,
		remoteBytesRead: Option[String] = None,
		remoteBytesReadToDisk: Option[String] = None,
		localBytesRead: Option[String] = None,
		recordsRead: Option[String] = None,
		bytesRead: Option[String] = None,
		remoteReqsDuration: Option[String] = None,
		stageShufflePushReadMetrics: Option[Schema.StageShufflePushReadMetrics] = None
	)
	
	case class StageShufflePushReadMetrics(
		corruptMergedBlockChunks: Option[String] = None,
		mergedFetchFallbackCount: Option[String] = None,
		remoteMergedBlocksFetched: Option[String] = None,
		localMergedBlocksFetched: Option[String] = None,
		remoteMergedChunksFetched: Option[String] = None,
		localMergedChunksFetched: Option[String] = None,
		remoteMergedBytesRead: Option[String] = None,
		localMergedBytesRead: Option[String] = None,
		remoteMergedReqsDuration: Option[String] = None
	)
	
	case class StageShuffleWriteMetrics(
		bytesWritten: Option[String] = None,
		writeTimeNanos: Option[String] = None,
		recordsWritten: Option[String] = None
	)
	
	case class AccumulableInfo(
		accumullableInfoId: Option[String] = None,
		name: Option[String] = None,
		update: Option[String] = None,
		value: Option[String] = None
	)
	
	case class TaskData(
		stageId: Option[String] = None,
		stageAttemptId: Option[Int] = None,
		taskId: Option[String] = None,
		index: Option[Int] = None,
		attempt: Option[Int] = None,
		partitionId: Option[Int] = None,
		launchTime: Option[String] = None,
		resultFetchStart: Option[String] = None,
		durationMillis: Option[String] = None,
		executorId: Option[String] = None,
		host: Option[String] = None,
		status: Option[String] = None,
		taskLocality: Option[String] = None,
		speculative: Option[Boolean] = None,
		accumulatorUpdates: Option[List[Schema.AccumulableInfo]] = None,
		errorMessage: Option[String] = None,
		hasMetrics: Option[Boolean] = None,
		taskMetrics: Option[Schema.TaskMetrics] = None,
		executorLogs: Option[Map[String, String]] = None,
		schedulerDelayMillis: Option[String] = None,
		gettingResultTimeMillis: Option[String] = None
	)
	
	case class TaskMetrics(
		executorDeserializeTimeMillis: Option[String] = None,
		executorDeserializeCpuTimeNanos: Option[String] = None,
		executorRunTimeMillis: Option[String] = None,
		executorCpuTimeNanos: Option[String] = None,
		resultSize: Option[String] = None,
		jvmGcTimeMillis: Option[String] = None,
		resultSerializationTimeMillis: Option[String] = None,
		memoryBytesSpilled: Option[String] = None,
		diskBytesSpilled: Option[String] = None,
		peakExecutionMemoryBytes: Option[String] = None,
		inputMetrics: Option[Schema.InputMetrics] = None,
		outputMetrics: Option[Schema.OutputMetrics] = None,
		shuffleReadMetrics: Option[Schema.ShuffleReadMetrics] = None,
		shuffleWriteMetrics: Option[Schema.ShuffleWriteMetrics] = None
	)
	
	case class InputMetrics(
		bytesRead: Option[String] = None,
		recordsRead: Option[String] = None
	)
	
	case class OutputMetrics(
		bytesWritten: Option[String] = None,
		recordsWritten: Option[String] = None
	)
	
	case class ShuffleReadMetrics(
		remoteBlocksFetched: Option[String] = None,
		localBlocksFetched: Option[String] = None,
		fetchWaitTimeMillis: Option[String] = None,
		remoteBytesRead: Option[String] = None,
		remoteBytesReadToDisk: Option[String] = None,
		localBytesRead: Option[String] = None,
		recordsRead: Option[String] = None,
		remoteReqsDuration: Option[String] = None,
		shufflePushReadMetrics: Option[Schema.ShufflePushReadMetrics] = None
	)
	
	case class ShufflePushReadMetrics(
		corruptMergedBlockChunks: Option[String] = None,
		mergedFetchFallbackCount: Option[String] = None,
		remoteMergedBlocksFetched: Option[String] = None,
		localMergedBlocksFetched: Option[String] = None,
		remoteMergedChunksFetched: Option[String] = None,
		localMergedChunksFetched: Option[String] = None,
		remoteMergedBytesRead: Option[String] = None,
		localMergedBytesRead: Option[String] = None,
		remoteMergedReqsDuration: Option[String] = None
	)
	
	case class ShuffleWriteMetrics(
		bytesWritten: Option[String] = None,
		writeTimeNanos: Option[String] = None,
		recordsWritten: Option[String] = None
	)
	
	case class ExecutorStageSummary(
		stageId: Option[String] = None,
		stageAttemptId: Option[Int] = None,
		executorId: Option[String] = None,
		taskTimeMillis: Option[String] = None,
		failedTasks: Option[Int] = None,
		succeededTasks: Option[Int] = None,
		killedTasks: Option[Int] = None,
		inputBytes: Option[String] = None,
		inputRecords: Option[String] = None,
		outputBytes: Option[String] = None,
		outputRecords: Option[String] = None,
		shuffleRead: Option[String] = None,
		shuffleReadRecords: Option[String] = None,
		shuffleWrite: Option[String] = None,
		shuffleWriteRecords: Option[String] = None,
		memoryBytesSpilled: Option[String] = None,
		diskBytesSpilled: Option[String] = None,
		isExcludedForStage: Option[Boolean] = None,
		peakMemoryMetrics: Option[Schema.ExecutorMetrics] = None
	)
	
	case class ExecutorMetrics(
		metrics: Option[Map[String, String]] = None
	)
	
	case class SpeculationStageSummary(
		stageId: Option[String] = None,
		stageAttemptId: Option[Int] = None,
		numTasks: Option[Int] = None,
		numActiveTasks: Option[Int] = None,
		numCompletedTasks: Option[Int] = None,
		numFailedTasks: Option[Int] = None,
		numKilledTasks: Option[Int] = None
	)
	
	case class ExecutorMetricsDistributions(
		quantiles: Option[List[BigDecimal]] = None,
		taskTimeMillis: Option[List[BigDecimal]] = None,
		failedTasks: Option[List[BigDecimal]] = None,
		succeededTasks: Option[List[BigDecimal]] = None,
		killedTasks: Option[List[BigDecimal]] = None,
		inputBytes: Option[List[BigDecimal]] = None,
		inputRecords: Option[List[BigDecimal]] = None,
		outputBytes: Option[List[BigDecimal]] = None,
		outputRecords: Option[List[BigDecimal]] = None,
		shuffleRead: Option[List[BigDecimal]] = None,
		shuffleReadRecords: Option[List[BigDecimal]] = None,
		shuffleWrite: Option[List[BigDecimal]] = None,
		shuffleWriteRecords: Option[List[BigDecimal]] = None,
		memoryBytesSpilled: Option[List[BigDecimal]] = None,
		diskBytesSpilled: Option[List[BigDecimal]] = None,
		peakMemoryMetrics: Option[Schema.ExecutorPeakMetricsDistributions] = None
	)
	
	case class ExecutorPeakMetricsDistributions(
		quantiles: Option[List[BigDecimal]] = None,
		executorMetrics: Option[List[Schema.ExecutorMetrics]] = None
	)
	
	case class TaskQuantileMetrics(
		durationMillis: Option[Schema.Quantiles] = None,
		executorDeserializeTimeMillis: Option[Schema.Quantiles] = None,
		executorDeserializeCpuTimeNanos: Option[Schema.Quantiles] = None,
		executorRunTimeMillis: Option[Schema.Quantiles] = None,
		executorCpuTimeNanos: Option[Schema.Quantiles] = None,
		resultSize: Option[Schema.Quantiles] = None,
		jvmGcTimeMillis: Option[Schema.Quantiles] = None,
		resultSerializationTimeMillis: Option[Schema.Quantiles] = None,
		gettingResultTimeMillis: Option[Schema.Quantiles] = None,
		schedulerDelayMillis: Option[Schema.Quantiles] = None,
		peakExecutionMemoryBytes: Option[Schema.Quantiles] = None,
		memoryBytesSpilled: Option[Schema.Quantiles] = None,
		diskBytesSpilled: Option[Schema.Quantiles] = None,
		inputMetrics: Option[Schema.InputQuantileMetrics] = None,
		outputMetrics: Option[Schema.OutputQuantileMetrics] = None,
		shuffleReadMetrics: Option[Schema.ShuffleReadQuantileMetrics] = None,
		shuffleWriteMetrics: Option[Schema.ShuffleWriteQuantileMetrics] = None
	)
	
	case class Quantiles(
		minimum: Option[String] = None,
		percentile25: Option[String] = None,
		percentile50: Option[String] = None,
		percentile75: Option[String] = None,
		maximum: Option[String] = None,
		sum: Option[String] = None,
		count: Option[String] = None
	)
	
	case class InputQuantileMetrics(
		bytesRead: Option[Schema.Quantiles] = None,
		recordsRead: Option[Schema.Quantiles] = None
	)
	
	case class OutputQuantileMetrics(
		bytesWritten: Option[Schema.Quantiles] = None,
		recordsWritten: Option[Schema.Quantiles] = None
	)
	
	case class ShuffleReadQuantileMetrics(
		readBytes: Option[Schema.Quantiles] = None,
		readRecords: Option[Schema.Quantiles] = None,
		remoteBlocksFetched: Option[Schema.Quantiles] = None,
		localBlocksFetched: Option[Schema.Quantiles] = None,
		fetchWaitTimeMillis: Option[Schema.Quantiles] = None,
		remoteBytesRead: Option[Schema.Quantiles] = None,
		remoteBytesReadToDisk: Option[Schema.Quantiles] = None,
		totalBlocksFetched: Option[Schema.Quantiles] = None,
		remoteReqsDuration: Option[Schema.Quantiles] = None,
		shufflePushReadMetrics: Option[Schema.ShufflePushReadQuantileMetrics] = None
	)
	
	case class ShufflePushReadQuantileMetrics(
		corruptMergedBlockChunks: Option[Schema.Quantiles] = None,
		mergedFetchFallbackCount: Option[Schema.Quantiles] = None,
		remoteMergedBlocksFetched: Option[Schema.Quantiles] = None,
		localMergedBlocksFetched: Option[Schema.Quantiles] = None,
		remoteMergedChunksFetched: Option[Schema.Quantiles] = None,
		localMergedChunksFetched: Option[Schema.Quantiles] = None,
		remoteMergedBytesRead: Option[Schema.Quantiles] = None,
		localMergedBytesRead: Option[Schema.Quantiles] = None,
		remoteMergedReqsDuration: Option[Schema.Quantiles] = None
	)
	
	case class ShuffleWriteQuantileMetrics(
		writeBytes: Option[Schema.Quantiles] = None,
		writeRecords: Option[Schema.Quantiles] = None,
		writeTimeNanos: Option[Schema.Quantiles] = None
	)
	
	case class ExecutorSummary(
		executorId: Option[String] = None,
		hostPort: Option[String] = None,
		isActive: Option[Boolean] = None,
		rddBlocks: Option[Int] = None,
		memoryUsed: Option[String] = None,
		diskUsed: Option[String] = None,
		totalCores: Option[Int] = None,
		maxTasks: Option[Int] = None,
		activeTasks: Option[Int] = None,
		failedTasks: Option[Int] = None,
		completedTasks: Option[Int] = None,
		totalTasks: Option[Int] = None,
		totalDurationMillis: Option[String] = None,
		totalGcTimeMillis: Option[String] = None,
		totalInputBytes: Option[String] = None,
		totalShuffleRead: Option[String] = None,
		totalShuffleWrite: Option[String] = None,
		isExcluded: Option[Boolean] = None,
		maxMemory: Option[String] = None,
		addTime: Option[String] = None,
		removeTime: Option[String] = None,
		removeReason: Option[String] = None,
		executorLogs: Option[Map[String, String]] = None,
		memoryMetrics: Option[Schema.MemoryMetrics] = None,
		excludedInStages: Option[List[String]] = None,
		peakMemoryMetrics: Option[Schema.ExecutorMetrics] = None,
		attributes: Option[Map[String, String]] = None,
		resources: Option[Map[String, Schema.ResourceInformation]] = None,
		resourceProfileId: Option[Int] = None
	)
	
	case class MemoryMetrics(
		usedOnHeapStorageMemory: Option[String] = None,
		usedOffHeapStorageMemory: Option[String] = None,
		totalOnHeapStorageMemory: Option[String] = None,
		totalOffHeapStorageMemory: Option[String] = None
	)
	
	case class ResourceInformation(
		name: Option[String] = None,
		addresses: Option[List[String]] = None
	)
	
	case class RddStorageInfo(
		rddStorageId: Option[Int] = None,
		name: Option[String] = None,
		numPartitions: Option[Int] = None,
		numCachedPartitions: Option[Int] = None,
		storageLevel: Option[String] = None,
		memoryUsed: Option[String] = None,
		diskUsed: Option[String] = None,
		dataDistribution: Option[List[Schema.RddDataDistribution]] = None,
		partitions: Option[List[Schema.RddPartitionInfo]] = None
	)
	
	case class RddDataDistribution(
		address: Option[String] = None,
		memoryUsed: Option[String] = None,
		memoryRemaining: Option[String] = None,
		diskUsed: Option[String] = None,
		onHeapMemoryUsed: Option[String] = None,
		offHeapMemoryUsed: Option[String] = None,
		onHeapMemoryRemaining: Option[String] = None,
		offHeapMemoryRemaining: Option[String] = None
	)
	
	case class RddPartitionInfo(
		blockName: Option[String] = None,
		storageLevel: Option[String] = None,
		memoryUsed: Option[String] = None,
		diskUsed: Option[String] = None,
		executors: Option[List[String]] = None
	)
	
	case class StreamBlockData(
		name: Option[String] = None,
		executorId: Option[String] = None,
		hostPort: Option[String] = None,
		storageLevel: Option[String] = None,
		useMemory: Option[Boolean] = None,
		useDisk: Option[Boolean] = None,
		deserialized: Option[Boolean] = None,
		memSize: Option[String] = None,
		diskSize: Option[String] = None
	)
	
	case class RddOperationGraph(
		stageId: Option[String] = None,
		edges: Option[List[Schema.RddOperationEdge]] = None,
		outgoingEdges: Option[List[Schema.RddOperationEdge]] = None,
		incomingEdges: Option[List[Schema.RddOperationEdge]] = None,
		rootCluster: Option[Schema.RddOperationCluster] = None
	)
	
	case class RddOperationEdge(
		fromId: Option[Int] = None,
		toId: Option[Int] = None
	)
	
	case class RddOperationCluster(
		rddClusterId: Option[String] = None,
		name: Option[String] = None,
		childNodes: Option[List[Schema.RddOperationNode]] = None,
		childClusters: Option[List[Schema.RddOperationCluster]] = None
	)
	
	object RddOperationNode {
		enum OutputDeterministicLevelEnum extends Enum[OutputDeterministicLevelEnum] { case DETERMINISTIC_LEVEL_UNSPECIFIED, DETERMINISTIC_LEVEL_DETERMINATE, DETERMINISTIC_LEVEL_UNORDERED, DETERMINISTIC_LEVEL_INDETERMINATE }
	}
	case class RddOperationNode(
		nodeId: Option[Int] = None,
		name: Option[String] = None,
		cached: Option[Boolean] = None,
		barrier: Option[Boolean] = None,
		callsite: Option[String] = None,
		outputDeterministicLevel: Option[Schema.RddOperationNode.OutputDeterministicLevelEnum] = None
	)
	
	case class PoolData(
		name: Option[String] = None,
		stageIds: Option[List[String]] = None
	)
	
	case class ProcessSummary(
		processId: Option[String] = None,
		hostPort: Option[String] = None,
		isActive: Option[Boolean] = None,
		totalCores: Option[Int] = None,
		addTime: Option[String] = None,
		removeTime: Option[String] = None,
		processLogs: Option[Map[String, String]] = None
	)
	
	object SqlExecutionUiData {
		enum JobsEnum extends Enum[JobsEnum] { case JOB_EXECUTION_STATUS_UNSPECIFIED, JOB_EXECUTION_STATUS_RUNNING, JOB_EXECUTION_STATUS_SUCCEEDED, JOB_EXECUTION_STATUS_FAILED, JOB_EXECUTION_STATUS_UNKNOWN }
	}
	case class SqlExecutionUiData(
		executionId: Option[String] = None,
		rootExecutionId: Option[String] = None,
		description: Option[String] = None,
		details: Option[String] = None,
		physicalPlanDescription: Option[String] = None,
		modifiedConfigs: Option[Map[String, String]] = None,
		metrics: Option[List[Schema.SqlPlanMetric]] = None,
		submissionTime: Option[String] = None,
		completionTime: Option[String] = None,
		errorMessage: Option[String] = None,
		jobs: Option[Map[String, Schema.SqlExecutionUiData.JobsEnum]] = None,
		stages: Option[List[String]] = None,
		metricValuesIsNull: Option[Boolean] = None,
		metricValues: Option[Map[String, String]] = None
	)
	
	case class SqlPlanMetric(
		name: Option[String] = None,
		accumulatorId: Option[String] = None,
		metricType: Option[String] = None
	)
	
	case class SparkPlanGraph(
		executionId: Option[String] = None,
		nodes: Option[List[Schema.SparkPlanGraphNodeWrapper]] = None,
		edges: Option[List[Schema.SparkPlanGraphEdge]] = None
	)
	
	case class SparkPlanGraphNodeWrapper(
		node: Option[Schema.SparkPlanGraphNode] = None,
		cluster: Option[Schema.SparkPlanGraphCluster] = None
	)
	
	case class SparkPlanGraphNode(
		sparkPlanGraphNodeId: Option[String] = None,
		name: Option[String] = None,
		desc: Option[String] = None,
		metrics: Option[List[Schema.SqlPlanMetric]] = None
	)
	
	case class SparkPlanGraphCluster(
		sparkPlanGraphClusterId: Option[String] = None,
		name: Option[String] = None,
		desc: Option[String] = None,
		nodes: Option[List[Schema.SparkPlanGraphNodeWrapper]] = None,
		metrics: Option[List[Schema.SqlPlanMetric]] = None
	)
	
	case class SparkPlanGraphEdge(
		fromId: Option[String] = None,
		toId: Option[String] = None
	)
	
	case class StreamingQueryData(
		name: Option[String] = None,
		streamingQueryId: Option[String] = None,
		runId: Option[String] = None,
		isActive: Option[Boolean] = None,
		exception: Option[String] = None,
		startTimestamp: Option[String] = None,
		endTimestamp: Option[String] = None
	)
	
	case class StreamingQueryProgress(
		streamingQueryProgressId: Option[String] = None,
		runId: Option[String] = None,
		name: Option[String] = None,
		timestamp: Option[String] = None,
		batchId: Option[String] = None,
		batchDuration: Option[String] = None,
		durationMillis: Option[Map[String, String]] = None,
		eventTime: Option[Map[String, String]] = None,
		stateOperators: Option[List[Schema.StateOperatorProgress]] = None,
		sources: Option[List[Schema.SourceProgress]] = None,
		sink: Option[Schema.SinkProgress] = None,
		observedMetrics: Option[Map[String, String]] = None
	)
	
	case class StateOperatorProgress(
		operatorName: Option[String] = None,
		numRowsTotal: Option[String] = None,
		numRowsUpdated: Option[String] = None,
		allUpdatesTimeMs: Option[String] = None,
		numRowsRemoved: Option[String] = None,
		allRemovalsTimeMs: Option[String] = None,
		commitTimeMs: Option[String] = None,
		memoryUsedBytes: Option[String] = None,
		numRowsDroppedByWatermark: Option[String] = None,
		numShufflePartitions: Option[String] = None,
		numStateStoreInstances: Option[String] = None,
		customMetrics: Option[Map[String, String]] = None
	)
	
	case class SourceProgress(
		description: Option[String] = None,
		startOffset: Option[String] = None,
		endOffset: Option[String] = None,
		latestOffset: Option[String] = None,
		numInputRows: Option[String] = None,
		inputRowsPerSecond: Option[BigDecimal] = None,
		processedRowsPerSecond: Option[BigDecimal] = None,
		metrics: Option[Map[String, String]] = None
	)
	
	case class SinkProgress(
		description: Option[String] = None,
		numOutputRows: Option[String] = None,
		metrics: Option[Map[String, String]] = None
	)
	
	case class WriteSparkApplicationContextResponse(
	
	)
	
	case class SearchSparkApplicationsResponse(
	  /** Output only. High level information corresponding to an application. */
		sparkApplications: Option[List[Schema.SparkApplication]] = None,
	  /** This token is included in the response if there are more results to fetch. To fetch additional results, provide this value as the page_token in a subsequent SearchSparkApplicationsRequest. */
		nextPageToken: Option[String] = None
	)
	
	case class SparkApplication(
	  /** Identifier. Name of the spark application */
		name: Option[String] = None,
	  /** Output only. High level information corresponding to an application. */
		application: Option[Schema.ApplicationInfo] = None
	)
	
	case class AccessSparkApplicationResponse(
	  /** Output only. High level information corresponding to an application. */
		application: Option[Schema.ApplicationInfo] = None
	)
	
	case class SearchSparkApplicationJobsResponse(
	  /** Output only. Data corresponding to a spark job. */
		sparkApplicationJobs: Option[List[Schema.JobData]] = None,
	  /** This token is included in the response if there are more results to fetch. To fetch additional results, provide this value as the page_token in a subsequent SearchSparkApplicationJobsRequest. */
		nextPageToken: Option[String] = None
	)
	
	case class AccessSparkApplicationJobResponse(
	  /** Output only. Data corresponding to a spark job. */
		jobData: Option[Schema.JobData] = None
	)
	
	case class SearchSparkApplicationStagesResponse(
	  /** Output only. Data corresponding to a stage. */
		sparkApplicationStages: Option[List[Schema.StageData]] = None,
	  /** This token is included in the response if there are more results to fetch. To fetch additional results, provide this value as the page_token in a subsequent SearchSparkApplicationStages. */
		nextPageToken: Option[String] = None
	)
	
	case class SearchSparkApplicationStageAttemptsResponse(
	  /** Output only. Data corresponding to a stage attempts */
		sparkApplicationStageAttempts: Option[List[Schema.StageData]] = None,
	  /** This token is included in the response if there are more results to fetch. To fetch additional results, provide this value as the page_token in a subsequent ListSparkApplicationStageAttemptsRequest. */
		nextPageToken: Option[String] = None
	)
	
	case class AccessSparkApplicationStageAttemptResponse(
	  /** Output only. Data corresponding to a stage. */
		stageData: Option[Schema.StageData] = None
	)
	
	case class SearchSparkApplicationStageAttemptTasksResponse(
	  /** Output only. Data corresponding to tasks created by spark. */
		sparkApplicationStageAttemptTasks: Option[List[Schema.TaskData]] = None,
	  /** This token is included in the response if there are more results to fetch. To fetch additional results, provide this value as the page_token in a subsequent ListSparkApplicationStageAttemptTasksRequest. */
		nextPageToken: Option[String] = None
	)
	
	case class SearchSparkApplicationExecutorsResponse(
	  /** Details about executors used by the application. */
		sparkApplicationExecutors: Option[List[Schema.ExecutorSummary]] = None,
	  /** This token is included in the response if there are more results to fetch. To fetch additional results, provide this value as the page_token in a subsequent SearchSparkApplicationExecutorsListRequest. */
		nextPageToken: Option[String] = None
	)
	
	case class SearchSparkApplicationExecutorStageSummaryResponse(
	  /** Details about executors used by the application stage. */
		sparkApplicationStageExecutors: Option[List[Schema.ExecutorStageSummary]] = None,
	  /** This token is included in the response if there are more results to fetch. To fetch additional results, provide this value as the page_token in a subsequent SearchSparkApplicationExecutorsListRequest. */
		nextPageToken: Option[String] = None
	)
	
	case class SearchSparkApplicationSqlQueriesResponse(
	  /** Output only. SQL Execution Data */
		sparkApplicationSqlQueries: Option[List[Schema.SqlExecutionUiData]] = None,
	  /** This token is included in the response if there are more results to fetch. To fetch additional results, provide this value as the page_token in a subsequent SearchSparkApplicationSqlQueriesRequest. */
		nextPageToken: Option[String] = None
	)
	
	case class AccessSparkApplicationSqlQueryResponse(
	  /** SQL Execution Data */
		executionData: Option[Schema.SqlExecutionUiData] = None
	)
	
	case class AccessSparkApplicationSqlSparkPlanGraphResponse(
	  /** SparkPlanGraph for a Spark Application execution. */
		sparkPlanGraph: Option[Schema.SparkPlanGraph] = None
	)
	
	case class AccessSparkApplicationStageRddOperationGraphResponse(
	  /** RDD operation graph for a Spark Application Stage. */
		rddOperationGraph: Option[Schema.RddOperationGraph] = None
	)
	
	case class AccessSparkApplicationEnvironmentInfoResponse(
	  /** Details about the Environment that the application is running in. */
		applicationEnvironmentInfo: Option[Schema.ApplicationEnvironmentInfo] = None
	)
	
	case class SummarizeSparkApplicationJobsResponse(
	  /** Summary of a Spark Application Jobs */
		jobsSummary: Option[Schema.JobsSummary] = None
	)
	
	case class JobsSummary(
	  /** Spark Application Id */
		applicationId: Option[String] = None,
	  /** Spark Scheduling mode */
		schedulingMode: Option[String] = None,
	  /** Number of active jobs */
		activeJobs: Option[Int] = None,
	  /** Number of completed jobs */
		completedJobs: Option[Int] = None,
	  /** Number of failed jobs */
		failedJobs: Option[Int] = None,
	  /** Attempts info */
		attempts: Option[List[Schema.ApplicationAttemptInfo]] = None
	)
	
	case class SummarizeSparkApplicationStagesResponse(
	  /** Summary of a Spark Application Stages */
		stagesSummary: Option[Schema.StagesSummary] = None
	)
	
	case class StagesSummary(
		applicationId: Option[String] = None,
		numActiveStages: Option[Int] = None,
		numCompletedStages: Option[Int] = None,
		numSkippedStages: Option[Int] = None,
		numFailedStages: Option[Int] = None,
		numPendingStages: Option[Int] = None
	)
	
	case class SummarizeSparkApplicationStageAttemptTasksResponse(
	  /** Summary of tasks for a Spark Application Stage Attempt */
		stageAttemptTasksSummary: Option[Schema.StageAttemptTasksSummary] = None
	)
	
	case class StageAttemptTasksSummary(
		applicationId: Option[String] = None,
		stageId: Option[String] = None,
		stageAttemptId: Option[Int] = None,
		numTasks: Option[Int] = None,
		numRunningTasks: Option[Int] = None,
		numSuccessTasks: Option[Int] = None,
		numFailedTasks: Option[Int] = None,
		numKilledTasks: Option[Int] = None,
		numPendingTasks: Option[Int] = None
	)
	
	case class SummarizeSparkApplicationExecutorsResponse(
	  /** Spark Application Id */
		applicationId: Option[String] = None,
	  /** Consolidated summary for active executors. */
		activeExecutorSummary: Option[Schema.ConsolidatedExecutorSummary] = None,
	  /** Consolidated summary for dead executors. */
		deadExecutorSummary: Option[Schema.ConsolidatedExecutorSummary] = None,
	  /** Overall consolidated summary for all executors. */
		totalExecutorSummary: Option[Schema.ConsolidatedExecutorSummary] = None
	)
	
	case class ConsolidatedExecutorSummary(
		count: Option[Int] = None,
		rddBlocks: Option[Int] = None,
		memoryUsed: Option[String] = None,
		diskUsed: Option[String] = None,
		totalCores: Option[Int] = None,
		activeTasks: Option[Int] = None,
		failedTasks: Option[Int] = None,
		completedTasks: Option[Int] = None,
		totalTasks: Option[Int] = None,
		totalDurationMillis: Option[String] = None,
		totalGcTimeMillis: Option[String] = None,
		totalInputBytes: Option[String] = None,
		totalShuffleRead: Option[String] = None,
		totalShuffleWrite: Option[String] = None,
		isExcluded: Option[Int] = None,
		maxMemory: Option[String] = None,
		memoryMetrics: Option[Schema.MemoryMetrics] = None
	)
	
	case class Cluster(
	  /** Required. The Google Cloud Platform project ID that the cluster belongs to. */
		projectId: Option[String] = None,
	  /** Required. The cluster name, which must be unique within a project. The name must start with a lowercase letter, and can contain up to 51 lowercase letters, numbers, and hyphens. It cannot end with a hyphen. The name of a deleted cluster can be reused. */
		clusterName: Option[String] = None,
	  /** Optional. The cluster config for a cluster of Compute Engine Instances. Note that Dataproc may set default values, and values may change when clusters are updated.Exactly one of ClusterConfig or VirtualClusterConfig must be specified. */
		config: Option[Schema.ClusterConfig] = None,
	  /** Optional. The virtual cluster config is used when creating a Dataproc cluster that does not directly control the underlying compute resources, for example, when creating a Dataproc-on-GKE cluster (https://cloud.google.com/dataproc/docs/guides/dpgke/dataproc-gke-overview). Dataproc may set default values, and values may change when clusters are updated. Exactly one of config or virtual_cluster_config must be specified. */
		virtualClusterConfig: Option[Schema.VirtualClusterConfig] = None,
	  /** Optional. The labels to associate with this cluster. Label keys must contain 1 to 63 characters, and must conform to RFC 1035 (https://www.ietf.org/rfc/rfc1035.txt). Label values may be empty, but, if present, must contain 1 to 63 characters, and must conform to RFC 1035 (https://www.ietf.org/rfc/rfc1035.txt). No more than 32 labels can be associated with a cluster. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Cluster status. */
		status: Option[Schema.ClusterStatus] = None,
	  /** Output only. The previous cluster status. */
		statusHistory: Option[List[Schema.ClusterStatus]] = None,
	  /** Output only. A cluster UUID (Unique Universal Identifier). Dataproc generates this value when it creates the cluster. */
		clusterUuid: Option[String] = None,
	  /** Output only. Contains cluster daemon metrics such as HDFS and YARN stats.Beta Feature: This report is available for testing purposes only. It may be changed before final release. */
		metrics: Option[Schema.ClusterMetrics] = None
	)
	
	case class ClusterConfig(
	  /** Optional. A Cloud Storage bucket used to stage job dependencies, config files, and job driver console output. If you do not specify a staging bucket, Cloud Dataproc will determine a Cloud Storage location (US, ASIA, or EU) for your cluster's staging bucket according to the Compute Engine zone where your cluster is deployed, and then create and manage this project-level, per-location bucket (see Dataproc staging and temp buckets (https://cloud.google.com/dataproc/docs/concepts/configuring-clusters/staging-bucket)). This field requires a Cloud Storage bucket name, not a gs://... URI to a Cloud Storage bucket. */
		configBucket: Option[String] = None,
	  /** Optional. A Cloud Storage bucket used to store ephemeral cluster and jobs data, such as Spark and MapReduce history files. If you do not specify a temp bucket, Dataproc will determine a Cloud Storage location (US, ASIA, or EU) for your cluster's temp bucket according to the Compute Engine zone where your cluster is deployed, and then create and manage this project-level, per-location bucket. The default bucket has a TTL of 90 days, but you can use any TTL (or none) if you specify a bucket (see Dataproc staging and temp buckets (https://cloud.google.com/dataproc/docs/concepts/configuring-clusters/staging-bucket)). This field requires a Cloud Storage bucket name, not a gs://... URI to a Cloud Storage bucket. */
		tempBucket: Option[String] = None,
	  /** Optional. The shared Compute Engine config settings for all instances in a cluster. */
		gceClusterConfig: Option[Schema.GceClusterConfig] = None,
	  /** Optional. The Compute Engine config settings for the cluster's master instance. */
		masterConfig: Option[Schema.InstanceGroupConfig] = None,
	  /** Optional. The Compute Engine config settings for the cluster's worker instances. */
		workerConfig: Option[Schema.InstanceGroupConfig] = None,
	  /** Optional. The Compute Engine config settings for a cluster's secondary worker instances */
		secondaryWorkerConfig: Option[Schema.InstanceGroupConfig] = None,
	  /** Optional. The config settings for cluster software. */
		softwareConfig: Option[Schema.SoftwareConfig] = None,
	  /** Optional. Commands to execute on each node after config is completed. By default, executables are run on master and all worker nodes. You can test a node's role metadata to run an executable on a master or worker node, as shown below using curl (you can also use wget): ROLE=$(curl -H Metadata-Flavor:Google http://metadata/computeMetadata/v1/instance/attributes/dataproc-role) if [[ "${ROLE}" == 'Master' ]]; then ... master specific actions ... else ... worker specific actions ... fi  */
		initializationActions: Option[List[Schema.NodeInitializationAction]] = None,
	  /** Optional. Encryption settings for the cluster. */
		encryptionConfig: Option[Schema.EncryptionConfig] = None,
	  /** Optional. Autoscaling config for the policy associated with the cluster. Cluster does not autoscale if this field is unset. */
		autoscalingConfig: Option[Schema.AutoscalingConfig] = None,
	  /** Optional. Security settings for the cluster. */
		securityConfig: Option[Schema.SecurityConfig] = None,
	  /** Optional. Lifecycle setting for the cluster. */
		lifecycleConfig: Option[Schema.LifecycleConfig] = None,
	  /** Optional. Port/endpoint configuration for this cluster */
		endpointConfig: Option[Schema.EndpointConfig] = None,
	  /** Optional. Metastore configuration. */
		metastoreConfig: Option[Schema.MetastoreConfig] = None,
	  /** Optional. BETA. The Kubernetes Engine config for Dataproc clusters deployed to The Kubernetes Engine config for Dataproc clusters deployed to Kubernetes. These config settings are mutually exclusive with Compute Engine-based options, such as gce_cluster_config, master_config, worker_config, secondary_worker_config, and autoscaling_config. */
		gkeClusterConfig: Option[Schema.GkeClusterConfig] = None,
	  /** Optional. The config for Dataproc metrics. */
		dataprocMetricConfig: Option[Schema.DataprocMetricConfig] = None,
	  /** Optional. The node group settings. */
		auxiliaryNodeGroups: Option[List[Schema.AuxiliaryNodeGroup]] = None
	)
	
	object GceClusterConfig {
		enum PrivateIpv6GoogleAccessEnum extends Enum[PrivateIpv6GoogleAccessEnum] { case PRIVATE_IPV6_GOOGLE_ACCESS_UNSPECIFIED, INHERIT_FROM_SUBNETWORK, OUTBOUND, BIDIRECTIONAL }
	}
	case class GceClusterConfig(
	  /** Optional. The Compute Engine zone where the Dataproc cluster will be located. If omitted, the service will pick a zone in the cluster's Compute Engine region. On a get request, zone will always be present.A full URL, partial URI, or short name are valid. Examples: https://www.googleapis.com/compute/v1/projects/[project_id]/zones/[zone] projects/[project_id]/zones/[zone] [zone] */
		zoneUri: Option[String] = None,
	  /** Optional. The Compute Engine network to be used for machine communications. Cannot be specified with subnetwork_uri. If neither network_uri nor subnetwork_uri is specified, the "default" network of the project is used, if it exists. Cannot be a "Custom Subnet Network" (see Using Subnetworks (https://cloud.google.com/compute/docs/subnetworks) for more information).A full URL, partial URI, or short name are valid. Examples: https://www.googleapis.com/compute/v1/projects/[project_id]/global/networks/default projects/[project_id]/global/networks/default default */
		networkUri: Option[String] = None,
	  /** Optional. The Compute Engine subnetwork to be used for machine communications. Cannot be specified with network_uri.A full URL, partial URI, or short name are valid. Examples: https://www.googleapis.com/compute/v1/projects/[project_id]/regions/[region]/subnetworks/sub0 projects/[project_id]/regions/[region]/subnetworks/sub0 sub0 */
		subnetworkUri: Option[String] = None,
	  /** Optional. This setting applies to subnetwork-enabled networks. It is set to true by default in clusters created with image versions 2.2.x.When set to true: All cluster VMs have internal IP addresses. Google Private Access (https://cloud.google.com/vpc/docs/private-google-access) must be enabled to access Dataproc and other Google Cloud APIs. Off-cluster dependencies must be configured to be accessible without external IP addresses.When set to false: Cluster VMs are not restricted to internal IP addresses. Ephemeral external IP addresses are assigned to each cluster VM. */
		internalIpOnly: Option[Boolean] = None,
	  /** Optional. The type of IPv6 access for a cluster. */
		privateIpv6GoogleAccess: Option[Schema.GceClusterConfig.PrivateIpv6GoogleAccessEnum] = None,
	  /** Optional. The Dataproc service account (https://cloud.google.com/dataproc/docs/concepts/configuring-clusters/service-accounts#service_accounts_in_dataproc) (also see VM Data Plane identity (https://cloud.google.com/dataproc/docs/concepts/iam/dataproc-principals#vm_service_account_data_plane_identity)) used by Dataproc cluster VM instances to access Google Cloud Platform services.If not specified, the Compute Engine default service account (https://cloud.google.com/compute/docs/access/service-accounts#default_service_account) is used. */
		serviceAccount: Option[String] = None,
	  /** Optional. The URIs of service account scopes to be included in Compute Engine instances. The following base set of scopes is always included: https://www.googleapis.com/auth/cloud.useraccounts.readonly https://www.googleapis.com/auth/devstorage.read_write https://www.googleapis.com/auth/logging.writeIf no scopes are specified, the following defaults are also provided: https://www.googleapis.com/auth/bigquery https://www.googleapis.com/auth/bigtable.admin.table https://www.googleapis.com/auth/bigtable.data https://www.googleapis.com/auth/devstorage.full_control */
		serviceAccountScopes: Option[List[String]] = None,
	  /** The Compute Engine network tags to add to all instances (see Tagging instances (https://cloud.google.com/vpc/docs/add-remove-network-tags)). */
		tags: Option[List[String]] = None,
	  /** Optional. The Compute Engine metadata entries to add to all instances (see Project and instance metadata (https://cloud.google.com/compute/docs/storing-retrieving-metadata#project_and_instance_metadata)). */
		metadata: Option[Map[String, String]] = None,
	  /** Optional. Reservation Affinity for consuming Zonal reservation. */
		reservationAffinity: Option[Schema.ReservationAffinity] = None,
	  /** Optional. Node Group Affinity for sole-tenant clusters. */
		nodeGroupAffinity: Option[Schema.NodeGroupAffinity] = None,
	  /** Optional. Shielded Instance Config for clusters using Compute Engine Shielded VMs (https://cloud.google.com/security/shielded-cloud/shielded-vm). */
		shieldedInstanceConfig: Option[Schema.ShieldedInstanceConfig] = None,
	  /** Optional. Confidential Instance Config for clusters using Confidential VMs (https://cloud.google.com/compute/confidential-vm/docs). */
		confidentialInstanceConfig: Option[Schema.ConfidentialInstanceConfig] = None
	)
	
	object ReservationAffinity {
		enum ConsumeReservationTypeEnum extends Enum[ConsumeReservationTypeEnum] { case TYPE_UNSPECIFIED, NO_RESERVATION, ANY_RESERVATION, SPECIFIC_RESERVATION }
	}
	case class ReservationAffinity(
	  /** Optional. Type of reservation to consume */
		consumeReservationType: Option[Schema.ReservationAffinity.ConsumeReservationTypeEnum] = None,
	  /** Optional. Corresponds to the label key of reservation resource. */
		key: Option[String] = None,
	  /** Optional. Corresponds to the label values of reservation resource. */
		values: Option[List[String]] = None
	)
	
	case class NodeGroupAffinity(
	  /** Required. The URI of a sole-tenant node group resource (https://cloud.google.com/compute/docs/reference/rest/v1/nodeGroups) that the cluster will be created on.A full URL, partial URI, or node group name are valid. Examples: https://www.googleapis.com/compute/v1/projects/[project_id]/zones/[zone]/nodeGroups/node-group-1 projects/[project_id]/zones/[zone]/nodeGroups/node-group-1 node-group-1 */
		nodeGroupUri: Option[String] = None
	)
	
	case class ShieldedInstanceConfig(
	  /** Optional. Defines whether instances have Secure Boot enabled. */
		enableSecureBoot: Option[Boolean] = None,
	  /** Optional. Defines whether instances have the vTPM enabled. */
		enableVtpm: Option[Boolean] = None,
	  /** Optional. Defines whether instances have integrity monitoring enabled. */
		enableIntegrityMonitoring: Option[Boolean] = None
	)
	
	case class ConfidentialInstanceConfig(
	  /** Optional. Defines whether the instance should have confidential compute enabled. */
		enableConfidentialCompute: Option[Boolean] = None
	)
	
	object InstanceGroupConfig {
		enum PreemptibilityEnum extends Enum[PreemptibilityEnum] { case PREEMPTIBILITY_UNSPECIFIED, NON_PREEMPTIBLE, PREEMPTIBLE, SPOT }
	}
	case class InstanceGroupConfig(
	  /** Optional. The number of VM instances in the instance group. For HA cluster master_config groups, must be set to 3. For standard cluster master_config groups, must be set to 1. */
		numInstances: Option[Int] = None,
	  /** Output only. The list of instance names. Dataproc derives the names from cluster_name, num_instances, and the instance group. */
		instanceNames: Option[List[String]] = None,
	  /** Output only. List of references to Compute Engine instances. */
		instanceReferences: Option[List[Schema.InstanceReference]] = None,
	  /** Optional. The Compute Engine image resource used for cluster instances.The URI can represent an image or image family.Image examples: https://www.googleapis.com/compute/v1/projects/[project_id]/global/images/[image-id] projects/[project_id]/global/images/[image-id] image-idImage family examples. Dataproc will use the most recent image from the family: https://www.googleapis.com/compute/v1/projects/[project_id]/global/images/family/[custom-image-family-name] projects/[project_id]/global/images/family/[custom-image-family-name]If the URI is unspecified, it will be inferred from SoftwareConfig.image_version or the system default. */
		imageUri: Option[String] = None,
	  /** Optional. The Compute Engine machine type used for cluster instances.A full URL, partial URI, or short name are valid. Examples: https://www.googleapis.com/compute/v1/projects/[project_id]/zones/[zone]/machineTypes/n1-standard-2 projects/[project_id]/zones/[zone]/machineTypes/n1-standard-2 n1-standard-2Auto Zone Exception: If you are using the Dataproc Auto Zone Placement (https://cloud.google.com/dataproc/docs/concepts/configuring-clusters/auto-zone#using_auto_zone_placement) feature, you must use the short name of the machine type resource, for example, n1-standard-2. */
		machineTypeUri: Option[String] = None,
	  /** Optional. Disk option config settings. */
		diskConfig: Option[Schema.DiskConfig] = None,
	  /** Output only. Specifies that this instance group contains preemptible instances. */
		isPreemptible: Option[Boolean] = None,
	  /** Optional. Specifies the preemptibility of the instance group.The default value for master and worker groups is NON_PREEMPTIBLE. This default cannot be changed.The default value for secondary instances is PREEMPTIBLE. */
		preemptibility: Option[Schema.InstanceGroupConfig.PreemptibilityEnum] = None,
	  /** Output only. The config for Compute Engine Instance Group Manager that manages this group. This is only used for preemptible instance groups. */
		managedGroupConfig: Option[Schema.ManagedGroupConfig] = None,
	  /** Optional. The Compute Engine accelerator configuration for these instances. */
		accelerators: Option[List[Schema.AcceleratorConfig]] = None,
	  /** Optional. Specifies the minimum cpu platform for the Instance Group. See Dataproc -> Minimum CPU Platform (https://cloud.google.com/dataproc/docs/concepts/compute/dataproc-min-cpu). */
		minCpuPlatform: Option[String] = None,
	  /** Optional. The minimum number of primary worker instances to create. If min_num_instances is set, cluster creation will succeed if the number of primary workers created is at least equal to the min_num_instances number.Example: Cluster creation request with num_instances = 5 and min_num_instances = 3: If 4 VMs are created and 1 instance fails, the failed VM is deleted. The cluster is resized to 4 instances and placed in a RUNNING state. If 2 instances are created and 3 instances fail, the cluster in placed in an ERROR state. The failed VMs are not deleted. */
		minNumInstances: Option[Int] = None,
	  /** Optional. Instance flexibility Policy allowing a mixture of VM shapes and provisioning models. */
		instanceFlexibilityPolicy: Option[Schema.InstanceFlexibilityPolicy] = None,
	  /** Optional. Configuration to handle the startup of instances during cluster create and update process. */
		startupConfig: Option[Schema.StartupConfig] = None
	)
	
	case class InstanceReference(
	  /** The user-friendly name of the Compute Engine instance. */
		instanceName: Option[String] = None,
	  /** The unique identifier of the Compute Engine instance. */
		instanceId: Option[String] = None,
	  /** The public RSA key used for sharing data with this instance. */
		publicKey: Option[String] = None,
	  /** The public ECIES key used for sharing data with this instance. */
		publicEciesKey: Option[String] = None
	)
	
	case class DiskConfig(
	  /** Optional. Type of the boot disk (default is "pd-standard"). Valid values: "pd-balanced" (Persistent Disk Balanced Solid State Drive), "pd-ssd" (Persistent Disk Solid State Drive), or "pd-standard" (Persistent Disk Hard Disk Drive). See Disk types (https://cloud.google.com/compute/docs/disks#disk-types). */
		bootDiskType: Option[String] = None,
	  /** Optional. Size in GB of the boot disk (default is 500GB). */
		bootDiskSizeGb: Option[Int] = None,
	  /** Optional. Number of attached SSDs, from 0 to 8 (default is 0). If SSDs are not attached, the boot disk is used to store runtime logs and HDFS (https://hadoop.apache.org/docs/r1.2.1/hdfs_user_guide.html) data. If one or more SSDs are attached, this runtime bulk data is spread across them, and the boot disk contains only basic config and installed binaries.Note: Local SSD options may vary by machine type and number of vCPUs selected. */
		numLocalSsds: Option[Int] = None,
	  /** Optional. Interface type of local SSDs (default is "scsi"). Valid values: "scsi" (Small Computer System Interface), "nvme" (Non-Volatile Memory Express). See local SSD performance (https://cloud.google.com/compute/docs/disks/local-ssd#performance). */
		localSsdInterface: Option[String] = None,
	  /** Optional. Indicates how many IOPS to provision for the disk. This sets the number of I/O operations per second that the disk can handle. Note: This field is only supported if boot_disk_type is hyperdisk-balanced. */
		bootDiskProvisionedIops: Option[String] = None,
	  /** Optional. Indicates how much throughput to provision for the disk. This sets the number of throughput mb per second that the disk can handle. Values must be greater than or equal to 1. Note: This field is only supported if boot_disk_type is hyperdisk-balanced. */
		bootDiskProvisionedThroughput: Option[String] = None
	)
	
	case class ManagedGroupConfig(
	  /** Output only. The name of the Instance Template used for the Managed Instance Group. */
		instanceTemplateName: Option[String] = None,
	  /** Output only. The name of the Instance Group Manager for this group. */
		instanceGroupManagerName: Option[String] = None,
	  /** Output only. The partial URI to the instance group manager for this group. E.g. projects/my-project/regions/us-central1/instanceGroupManagers/my-igm. */
		instanceGroupManagerUri: Option[String] = None
	)
	
	case class AcceleratorConfig(
	  /** Full URL, partial URI, or short name of the accelerator type resource to expose to this instance. See Compute Engine AcceleratorTypes (https://cloud.google.com/compute/docs/reference/v1/acceleratorTypes).Examples: https://www.googleapis.com/compute/v1/projects/[project_id]/zones/[zone]/acceleratorTypes/nvidia-tesla-t4 projects/[project_id]/zones/[zone]/acceleratorTypes/nvidia-tesla-t4 nvidia-tesla-t4Auto Zone Exception: If you are using the Dataproc Auto Zone Placement (https://cloud.google.com/dataproc/docs/concepts/configuring-clusters/auto-zone#using_auto_zone_placement) feature, you must use the short name of the accelerator type resource, for example, nvidia-tesla-t4. */
		acceleratorTypeUri: Option[String] = None,
	  /** The number of the accelerator cards of this type exposed to this instance. */
		acceleratorCount: Option[Int] = None
	)
	
	case class InstanceFlexibilityPolicy(
	  /** Optional. Defines how the Group selects the provisioning model to ensure required reliability. */
		provisioningModelMix: Option[Schema.ProvisioningModelMix] = None,
	  /** Optional. List of instance selection options that the group will use when creating new VMs. */
		instanceSelectionList: Option[List[Schema.InstanceSelection]] = None,
	  /** Output only. A list of instance selection results in the group. */
		instanceSelectionResults: Option[List[Schema.InstanceSelectionResult]] = None
	)
	
	case class ProvisioningModelMix(
	  /** Optional. The base capacity that will always use Standard VMs to avoid risk of more preemption than the minimum capacity you need. Dataproc will create only standard VMs until it reaches standard_capacity_base, then it will start using standard_capacity_percent_above_base to mix Spot with Standard VMs. eg. If 15 instances are requested and standard_capacity_base is 5, Dataproc will create 5 standard VMs and then start mixing spot and standard VMs for remaining 10 instances. */
		standardCapacityBase: Option[Int] = None,
	  /** Optional. The percentage of target capacity that should use Standard VM. The remaining percentage will use Spot VMs. The percentage applies only to the capacity above standard_capacity_base. eg. If 15 instances are requested and standard_capacity_base is 5 and standard_capacity_percent_above_base is 30, Dataproc will create 5 standard VMs and then start mixing spot and standard VMs for remaining 10 instances. The mix will be 30% standard and 70% spot. */
		standardCapacityPercentAboveBase: Option[Int] = None
	)
	
	case class InstanceSelection(
	  /** Optional. Full machine-type names, e.g. "n1-standard-16". */
		machineTypes: Option[List[String]] = None,
	  /** Optional. Preference of this instance selection. Lower number means higher preference. Dataproc will first try to create a VM based on the machine-type with priority rank and fallback to next rank based on availability. Machine types and instance selections with the same priority have the same preference. */
		rank: Option[Int] = None
	)
	
	case class InstanceSelectionResult(
	  /** Output only. Full machine-type names, e.g. "n1-standard-16". */
		machineType: Option[String] = None,
	  /** Output only. Number of VM provisioned with the machine_type. */
		vmCount: Option[Int] = None
	)
	
	case class StartupConfig(
	  /** Optional. The config setting to enable cluster creation/ updation to be successful only after required_registration_fraction of instances are up and running. This configuration is applicable to only secondary workers for now. The cluster will fail if required_registration_fraction of instances are not available. This will include instance creation, agent registration, and service registration (if enabled). */
		requiredRegistrationFraction: Option[BigDecimal] = None
	)
	
	object SoftwareConfig {
		enum OptionalComponentsEnum extends Enum[OptionalComponentsEnum] { case COMPONENT_UNSPECIFIED, ANACONDA, DOCKER, DRUID, FLINK, HBASE, HIVE_WEBHCAT, HUDI, ICEBERG, JUPYTER, PRESTO, TRINO, RANGER, SOLR, ZEPPELIN, ZOOKEEPER }
	}
	case class SoftwareConfig(
	  /** Optional. The version of software inside the cluster. It must be one of the supported Dataproc Versions (https://cloud.google.com/dataproc/docs/concepts/versioning/dataproc-versions#supported-dataproc-image-versions), such as "1.2" (including a subminor version, such as "1.2.29"), or the "preview" version (https://cloud.google.com/dataproc/docs/concepts/versioning/dataproc-versions#other_versions). If unspecified, it defaults to the latest Debian version. */
		imageVersion: Option[String] = None,
	  /** Optional. The properties to set on daemon config files.Property keys are specified in prefix:property format, for example core:hadoop.tmp.dir. The following are supported prefixes and their mappings: capacity-scheduler: capacity-scheduler.xml core: core-site.xml distcp: distcp-default.xml hdfs: hdfs-site.xml hive: hive-site.xml mapred: mapred-site.xml pig: pig.properties spark: spark-defaults.conf yarn: yarn-site.xmlFor more information, see Cluster properties (https://cloud.google.com/dataproc/docs/concepts/cluster-properties). */
		properties: Option[Map[String, String]] = None,
	  /** Optional. The set of components to activate on the cluster. */
		optionalComponents: Option[List[Schema.SoftwareConfig.OptionalComponentsEnum]] = None
	)
	
	case class NodeInitializationAction(
	  /** Required. Cloud Storage URI of executable file. */
		executableFile: Option[String] = None,
	  /** Optional. Amount of time executable has to complete. Default is 10 minutes (see JSON representation of Duration (https://developers.google.com/protocol-buffers/docs/proto3#json)).Cluster creation fails with an explanatory error message (the name of the executable that caused the error and the exceeded timeout period) if the executable is not completed at end of the timeout period. */
		executionTimeout: Option[String] = None
	)
	
	case class EncryptionConfig(
	  /** Optional. The Cloud KMS key resource name to use for persistent disk encryption for all instances in the cluster. See Use CMEK with cluster data (https://cloud.google.com//dataproc/docs/concepts/configuring-clusters/customer-managed-encryption#use_cmek_with_cluster_data) for more information. */
		gcePdKmsKeyName: Option[String] = None,
	  /** Optional. The Cloud KMS key resource name to use for cluster persistent disk and job argument encryption. See Use CMEK with cluster data (https://cloud.google.com//dataproc/docs/concepts/configuring-clusters/customer-managed-encryption#use_cmek_with_cluster_data) for more information.When this key resource name is provided, the following job arguments of the following job types submitted to the cluster are encrypted using CMEK: FlinkJob args (https://cloud.google.com/dataproc/docs/reference/rest/v1/FlinkJob) HadoopJob args (https://cloud.google.com/dataproc/docs/reference/rest/v1/HadoopJob) SparkJob args (https://cloud.google.com/dataproc/docs/reference/rest/v1/SparkJob) SparkRJob args (https://cloud.google.com/dataproc/docs/reference/rest/v1/SparkRJob) PySparkJob args (https://cloud.google.com/dataproc/docs/reference/rest/v1/PySparkJob) SparkSqlJob (https://cloud.google.com/dataproc/docs/reference/rest/v1/SparkSqlJob) scriptVariables and queryList.queries HiveJob (https://cloud.google.com/dataproc/docs/reference/rest/v1/HiveJob) scriptVariables and queryList.queries PigJob (https://cloud.google.com/dataproc/docs/reference/rest/v1/PigJob) scriptVariables and queryList.queries PrestoJob (https://cloud.google.com/dataproc/docs/reference/rest/v1/PrestoJob) scriptVariables and queryList.queries */
		kmsKey: Option[String] = None
	)
	
	case class AutoscalingConfig(
	  /** Optional. The autoscaling policy used by the cluster.Only resource names including projectid and location (region) are valid. Examples: https://www.googleapis.com/compute/v1/projects/[project_id]/locations/[dataproc_region]/autoscalingPolicies/[policy_id] projects/[project_id]/locations/[dataproc_region]/autoscalingPolicies/[policy_id]Note that the policy must be in the same project and Dataproc region. */
		policyUri: Option[String] = None
	)
	
	case class SecurityConfig(
	  /** Optional. Kerberos related configuration. */
		kerberosConfig: Option[Schema.KerberosConfig] = None,
	  /** Optional. Identity related configuration, including service account based secure multi-tenancy user mappings. */
		identityConfig: Option[Schema.IdentityConfig] = None
	)
	
	case class KerberosConfig(
	  /** Optional. Flag to indicate whether to Kerberize the cluster (default: false). Set this field to true to enable Kerberos on a cluster. */
		enableKerberos: Option[Boolean] = None,
	  /** Optional. The Cloud Storage URI of a KMS encrypted file containing the root principal password. */
		rootPrincipalPasswordUri: Option[String] = None,
	  /** Optional. The URI of the KMS key used to encrypt sensitive files. */
		kmsKeyUri: Option[String] = None,
	  /** Optional. The Cloud Storage URI of the keystore file used for SSL encryption. If not provided, Dataproc will provide a self-signed certificate. */
		keystoreUri: Option[String] = None,
	  /** Optional. The Cloud Storage URI of the truststore file used for SSL encryption. If not provided, Dataproc will provide a self-signed certificate. */
		truststoreUri: Option[String] = None,
	  /** Optional. The Cloud Storage URI of a KMS encrypted file containing the password to the user provided keystore. For the self-signed certificate, this password is generated by Dataproc. */
		keystorePasswordUri: Option[String] = None,
	  /** Optional. The Cloud Storage URI of a KMS encrypted file containing the password to the user provided key. For the self-signed certificate, this password is generated by Dataproc. */
		keyPasswordUri: Option[String] = None,
	  /** Optional. The Cloud Storage URI of a KMS encrypted file containing the password to the user provided truststore. For the self-signed certificate, this password is generated by Dataproc. */
		truststorePasswordUri: Option[String] = None,
	  /** Optional. The remote realm the Dataproc on-cluster KDC will trust, should the user enable cross realm trust. */
		crossRealmTrustRealm: Option[String] = None,
	  /** Optional. The KDC (IP or hostname) for the remote trusted realm in a cross realm trust relationship. */
		crossRealmTrustKdc: Option[String] = None,
	  /** Optional. The admin server (IP or hostname) for the remote trusted realm in a cross realm trust relationship. */
		crossRealmTrustAdminServer: Option[String] = None,
	  /** Optional. The Cloud Storage URI of a KMS encrypted file containing the shared password between the on-cluster Kerberos realm and the remote trusted realm, in a cross realm trust relationship. */
		crossRealmTrustSharedPasswordUri: Option[String] = None,
	  /** Optional. The Cloud Storage URI of a KMS encrypted file containing the master key of the KDC database. */
		kdcDbKeyUri: Option[String] = None,
	  /** Optional. The lifetime of the ticket granting ticket, in hours. If not specified, or user specifies 0, then default value 10 will be used. */
		tgtLifetimeHours: Option[Int] = None,
	  /** Optional. The name of the on-cluster Kerberos realm. If not specified, the uppercased domain of hostnames will be the realm. */
		realm: Option[String] = None
	)
	
	case class IdentityConfig(
	  /** Required. Map of user to service account. */
		userServiceAccountMapping: Option[Map[String, String]] = None
	)
	
	case class LifecycleConfig(
	  /** Optional. The duration to keep the cluster alive while idling (when no jobs are running). Passing this threshold will cause the cluster to be deleted. Minimum value is 5 minutes; maximum value is 14 days (see JSON representation of Duration (https://developers.google.com/protocol-buffers/docs/proto3#json)). */
		idleDeleteTtl: Option[String] = None,
	  /** Optional. The time when cluster will be auto-deleted (see JSON representation of Timestamp (https://developers.google.com/protocol-buffers/docs/proto3#json)). */
		autoDeleteTime: Option[String] = None,
	  /** Optional. The lifetime duration of cluster. The cluster will be auto-deleted at the end of this period. Minimum value is 10 minutes; maximum value is 14 days (see JSON representation of Duration (https://developers.google.com/protocol-buffers/docs/proto3#json)). */
		autoDeleteTtl: Option[String] = None,
	  /** Output only. The time when cluster became idle (most recent job finished) and became eligible for deletion due to idleness (see JSON representation of Timestamp (https://developers.google.com/protocol-buffers/docs/proto3#json)). */
		idleStartTime: Option[String] = None
	)
	
	case class EndpointConfig(
	  /** Output only. The map of port descriptions to URLs. Will only be populated if enable_http_port_access is true. */
		httpPorts: Option[Map[String, String]] = None,
	  /** Optional. If true, enable http access to specific ports on the cluster from external sources. Defaults to false. */
		enableHttpPortAccess: Option[Boolean] = None
	)
	
	case class MetastoreConfig(
	  /** Required. Resource name of an existing Dataproc Metastore service.Example: projects/[project_id]/locations/[dataproc_region]/services/[service-name] */
		dataprocMetastoreService: Option[String] = None
	)
	
	case class GkeClusterConfig(
	  /** Optional. Deprecated. Use gkeClusterTarget. Used only for the deprecated beta. A target for the deployment. */
		namespacedGkeDeploymentTarget: Option[Schema.NamespacedGkeDeploymentTarget] = None,
	  /** Optional. A target GKE cluster to deploy to. It must be in the same project and region as the Dataproc cluster (the GKE cluster can be zonal or regional). Format: 'projects/{project}/locations/{location}/clusters/{cluster_id}' */
		gkeClusterTarget: Option[String] = None,
	  /** Optional. GKE node pools where workloads will be scheduled. At least one node pool must be assigned the DEFAULT GkeNodePoolTarget.Role. If a GkeNodePoolTarget is not specified, Dataproc constructs a DEFAULT GkeNodePoolTarget. Each role can be given to only one GkeNodePoolTarget. All node pools must have the same location settings. */
		nodePoolTarget: Option[List[Schema.GkeNodePoolTarget]] = None
	)
	
	case class NamespacedGkeDeploymentTarget(
	  /** Optional. The target GKE cluster to deploy to. Format: 'projects/{project}/locations/{location}/clusters/{cluster_id}' */
		targetGkeCluster: Option[String] = None,
	  /** Optional. A namespace within the GKE cluster to deploy into. */
		clusterNamespace: Option[String] = None
	)
	
	object GkeNodePoolTarget {
		enum RolesEnum extends Enum[RolesEnum] { case ROLE_UNSPECIFIED, DEFAULT, CONTROLLER, SPARK_DRIVER, SPARK_EXECUTOR }
	}
	case class GkeNodePoolTarget(
	  /** Required. The target GKE node pool. Format: 'projects/{project}/locations/{location}/clusters/{cluster}/nodePools/{node_pool}' */
		nodePool: Option[String] = None,
	  /** Required. The roles associated with the GKE node pool. */
		roles: Option[List[Schema.GkeNodePoolTarget.RolesEnum]] = None,
	  /** Input only. The configuration for the GKE node pool.If specified, Dataproc attempts to create a node pool with the specified shape. If one with the same name already exists, it is verified against all specified fields. If a field differs, the virtual cluster creation will fail.If omitted, any node pool with the specified name is used. If a node pool with the specified name does not exist, Dataproc create a node pool with default values.This is an input only field. It will not be returned by the API. */
		nodePoolConfig: Option[Schema.GkeNodePoolConfig] = None
	)
	
	case class GkeNodePoolConfig(
	  /** Optional. The node pool configuration. */
		config: Option[Schema.GkeNodeConfig] = None,
	  /** Optional. The list of Compute Engine zones (https://cloud.google.com/compute/docs/zones#available) where node pool nodes associated with a Dataproc on GKE virtual cluster will be located.Note: All node pools associated with a virtual cluster must be located in the same region as the virtual cluster, and they must be located in the same zone within that region.If a location is not specified during node pool creation, Dataproc on GKE will choose the zone. */
		locations: Option[List[String]] = None,
	  /** Optional. The autoscaler configuration for this node pool. The autoscaler is enabled only when a valid configuration is present. */
		autoscaling: Option[Schema.GkeNodePoolAutoscalingConfig] = None
	)
	
	case class GkeNodeConfig(
	  /** Optional. The name of a Compute Engine machine type (https://cloud.google.com/compute/docs/machine-types). */
		machineType: Option[String] = None,
	  /** Optional. The number of local SSD disks to attach to the node, which is limited by the maximum number of disks allowable per zone (see Adding Local SSDs (https://cloud.google.com/compute/docs/disks/local-ssd)). */
		localSsdCount: Option[Int] = None,
	  /** Optional. Whether the nodes are created as legacy preemptible VM instances (https://cloud.google.com/compute/docs/instances/preemptible). Also see Spot VMs, preemptible VM instances without a maximum lifetime. Legacy and Spot preemptible nodes cannot be used in a node pool with the CONTROLLER role or in the DEFAULT node pool if the CONTROLLER role is not assigned (the DEFAULT node pool will assume the CONTROLLER role). */
		preemptible: Option[Boolean] = None,
	  /** Optional. A list of hardware accelerators (https://cloud.google.com/compute/docs/gpus) to attach to each node. */
		accelerators: Option[List[Schema.GkeNodePoolAcceleratorConfig]] = None,
	  /** Optional. Minimum CPU platform (https://cloud.google.com/compute/docs/instances/specify-min-cpu-platform) to be used by this instance. The instance may be scheduled on the specified or a newer CPU platform. Specify the friendly names of CPU platforms, such as "Intel Haswell"` or Intel Sandy Bridge". */
		minCpuPlatform: Option[String] = None,
	  /** Optional. The Customer Managed Encryption Key (CMEK) (https://cloud.google.com/kubernetes-engine/docs/how-to/using-cmek) used to encrypt the boot disk attached to each node in the node pool. Specify the key using the following format: projects/{project}/locations/{location}/keyRings/{key_ring}/cryptoKeys/{crypto_key} */
		bootDiskKmsKey: Option[String] = None,
	  /** Optional. Whether the nodes are created as Spot VM instances (https://cloud.google.com/compute/docs/instances/spot). Spot VMs are the latest update to legacy preemptible VMs. Spot VMs do not have a maximum lifetime. Legacy and Spot preemptible nodes cannot be used in a node pool with the CONTROLLER role or in the DEFAULT node pool if the CONTROLLER role is not assigned (the DEFAULT node pool will assume the CONTROLLER role). */
		spot: Option[Boolean] = None
	)
	
	case class GkeNodePoolAcceleratorConfig(
	  /** The number of accelerator cards exposed to an instance. */
		acceleratorCount: Option[String] = None,
	  /** The accelerator type resource namename (see GPUs on Compute Engine). */
		acceleratorType: Option[String] = None,
	  /** Size of partitions to create on the GPU. Valid values are described in the NVIDIA mig user guide (https://docs.nvidia.com/datacenter/tesla/mig-user-guide/#partitioning). */
		gpuPartitionSize: Option[String] = None
	)
	
	case class GkeNodePoolAutoscalingConfig(
	  /** The minimum number of nodes in the node pool. Must be >= 0 and <= max_node_count. */
		minNodeCount: Option[Int] = None,
	  /** The maximum number of nodes in the node pool. Must be >= min_node_count, and must be > 0. Note: Quota must be sufficient to scale up the cluster. */
		maxNodeCount: Option[Int] = None
	)
	
	case class DataprocMetricConfig(
	  /** Required. Metrics sources to enable. */
		metrics: Option[List[Schema.Metric]] = None
	)
	
	object Metric {
		enum MetricSourceEnum extends Enum[MetricSourceEnum] { case METRIC_SOURCE_UNSPECIFIED, MONITORING_AGENT_DEFAULTS, HDFS, SPARK, YARN, SPARK_HISTORY_SERVER, HIVESERVER2, HIVEMETASTORE, FLINK }
	}
	case class Metric(
	  /** Required. A standard set of metrics is collected unless metricOverrides are specified for the metric source (see Custom metrics (https://cloud.google.com/dataproc/docs/guides/dataproc-metrics#custom_metrics) for more information). */
		metricSource: Option[Schema.Metric.MetricSourceEnum] = None,
	  /** Optional. Specify one or more Custom metrics (https://cloud.google.com/dataproc/docs/guides/dataproc-metrics#custom_metrics) to collect for the metric course (for the SPARK metric source (any Spark metric (https://spark.apache.org/docs/latest/monitoring.html#metrics) can be specified).Provide metrics in the following format: METRIC_SOURCE: INSTANCE:GROUP:METRIC Use camelcase as appropriate.Examples: yarn:ResourceManager:QueueMetrics:AppsCompleted spark:driver:DAGScheduler:job.allJobs sparkHistoryServer:JVM:Memory:NonHeapMemoryUsage.committed hiveserver2:JVM:Memory:NonHeapMemoryUsage.used Notes: Only the specified overridden metrics are collected for the metric source. For example, if one or more spark:executive metrics are listed as metric overrides, other SPARK metrics are not collected. The collection of the metrics for other enabled custom metric sources is unaffected. For example, if both SPARK andd YARN metric sources are enabled, and overrides are provided for Spark metrics only, all YARN metrics are collected. */
		metricOverrides: Option[List[String]] = None
	)
	
	case class AuxiliaryNodeGroup(
	  /** Required. Node group configuration. */
		nodeGroup: Option[Schema.NodeGroup] = None,
	  /** Optional. A node group ID. Generated if not specified.The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). Cannot begin or end with underscore or hyphen. Must consist of from 3 to 33 characters. */
		nodeGroupId: Option[String] = None
	)
	
	object NodeGroup {
		enum RolesEnum extends Enum[RolesEnum] { case ROLE_UNSPECIFIED, DRIVER }
	}
	case class NodeGroup(
	  /** The Node group resource name (https://aip.dev/122). */
		name: Option[String] = None,
	  /** Required. Node group roles. */
		roles: Option[List[Schema.NodeGroup.RolesEnum]] = None,
	  /** Optional. The node group instance group configuration. */
		nodeGroupConfig: Option[Schema.InstanceGroupConfig] = None,
	  /** Optional. Node group labels. Label keys must consist of from 1 to 63 characters and conform to RFC 1035 (https://www.ietf.org/rfc/rfc1035.txt). Label values can be empty. If specified, they must consist of from 1 to 63 characters and conform to RFC 1035 (https://www.ietf.org/rfc/rfc1035.txt). The node group must have no more than 32 labels. */
		labels: Option[Map[String, String]] = None
	)
	
	case class VirtualClusterConfig(
	  /** Optional. A Cloud Storage bucket used to stage job dependencies, config files, and job driver console output. If you do not specify a staging bucket, Cloud Dataproc will determine a Cloud Storage location (US, ASIA, or EU) for your cluster's staging bucket according to the Compute Engine zone where your cluster is deployed, and then create and manage this project-level, per-location bucket (see Dataproc staging and temp buckets (https://cloud.google.com/dataproc/docs/concepts/configuring-clusters/staging-bucket)). This field requires a Cloud Storage bucket name, not a gs://... URI to a Cloud Storage bucket. */
		stagingBucket: Option[String] = None,
	  /** Required. The configuration for running the Dataproc cluster on Kubernetes. */
		kubernetesClusterConfig: Option[Schema.KubernetesClusterConfig] = None,
	  /** Optional. Configuration of auxiliary services used by this cluster. */
		auxiliaryServicesConfig: Option[Schema.AuxiliaryServicesConfig] = None
	)
	
	case class KubernetesClusterConfig(
	  /** Optional. A namespace within the Kubernetes cluster to deploy into. If this namespace does not exist, it is created. If it exists, Dataproc verifies that another Dataproc VirtualCluster is not installed into it. If not specified, the name of the Dataproc Cluster is used. */
		kubernetesNamespace: Option[String] = None,
	  /** Required. The configuration for running the Dataproc cluster on GKE. */
		gkeClusterConfig: Option[Schema.GkeClusterConfig] = None,
	  /** Optional. The software configuration for this Dataproc cluster running on Kubernetes. */
		kubernetesSoftwareConfig: Option[Schema.KubernetesSoftwareConfig] = None
	)
	
	case class KubernetesSoftwareConfig(
	  /** The components that should be installed in this Dataproc cluster. The key must be a string from the KubernetesComponent enumeration. The value is the version of the software to be installed. At least one entry must be specified. */
		componentVersion: Option[Map[String, String]] = None,
	  /** The properties to set on daemon config files.Property keys are specified in prefix:property format, for example spark:spark.kubernetes.container.image. The following are supported prefixes and their mappings: spark: spark-defaults.confFor more information, see Cluster properties (https://cloud.google.com/dataproc/docs/concepts/cluster-properties). */
		properties: Option[Map[String, String]] = None
	)
	
	case class AuxiliaryServicesConfig(
	  /** Optional. The Hive Metastore configuration for this workload. */
		metastoreConfig: Option[Schema.MetastoreConfig] = None,
	  /** Optional. The Spark History Server configuration for the workload. */
		sparkHistoryServerConfig: Option[Schema.SparkHistoryServerConfig] = None
	)
	
	object ClusterStatus {
		enum StateEnum extends Enum[StateEnum] { case UNKNOWN, CREATING, RUNNING, ERROR, ERROR_DUE_TO_UPDATE, DELETING, UPDATING, STOPPING, STOPPED, STARTING, REPAIRING }
		enum SubstateEnum extends Enum[SubstateEnum] { case UNSPECIFIED, UNHEALTHY, STALE_STATUS }
	}
	case class ClusterStatus(
	  /** Output only. The cluster's state. */
		state: Option[Schema.ClusterStatus.StateEnum] = None,
	  /** Optional. Output only. Details of cluster's state. */
		detail: Option[String] = None,
	  /** Output only. Time when this state was entered (see JSON representation of Timestamp (https://developers.google.com/protocol-buffers/docs/proto3#json)). */
		stateStartTime: Option[String] = None,
	  /** Output only. Additional state information that includes status reported by the agent. */
		substate: Option[Schema.ClusterStatus.SubstateEnum] = None
	)
	
	case class ClusterMetrics(
	  /** The HDFS metrics. */
		hdfsMetrics: Option[Map[String, String]] = None,
	  /** YARN metrics. */
		yarnMetrics: Option[Map[String, String]] = None
	)
	
	case class StopClusterRequest(
	  /** Optional. Specifying the cluster_uuid means the RPC will fail (with error NOT_FOUND) if a cluster with the specified UUID does not exist. */
		clusterUuid: Option[String] = None,
	  /** Optional. A unique ID used to identify the request. If the server receives two StopClusterRequest (https://cloud.google.com/dataproc/docs/reference/rpc/google.cloud.dataproc.v1#google.cloud.dataproc.v1.StopClusterRequest)s with the same id, then the second request will be ignored and the first google.longrunning.Operation created and stored in the backend is returned.Recommendation: Set this value to a UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier).The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
		requestId: Option[String] = None
	)
	
	case class StartClusterRequest(
	  /** Optional. Specifying the cluster_uuid means the RPC will fail (with error NOT_FOUND) if a cluster with the specified UUID does not exist. */
		clusterUuid: Option[String] = None,
	  /** Optional. A unique ID used to identify the request. If the server receives two StartClusterRequest (https://cloud.google.com/dataproc/docs/reference/rpc/google.cloud.dataproc.v1#google.cloud.dataproc.v1.StartClusterRequest)s with the same id, then the second request will be ignored and the first google.longrunning.Operation created and stored in the backend is returned.Recommendation: Set this value to a UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier).The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
		requestId: Option[String] = None
	)
	
	case class RepairClusterRequest(
	  /** Optional. Specifying the cluster_uuid means the RPC will fail (with error NOT_FOUND) if a cluster with the specified UUID does not exist. */
		clusterUuid: Option[String] = None,
	  /** Optional. A unique ID used to identify the request. If the server receives two RepairClusterRequests with the same ID, the second request is ignored, and the first google.longrunning.Operation created and stored in the backend is returned.Recommendation: Set this value to a UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier).The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
		requestId: Option[String] = None,
	  /** Optional. Node pools and corresponding repair action to be taken. All node pools should be unique in this request. i.e. Multiple entries for the same node pool id are not allowed. */
		nodePools: Option[List[Schema.NodePool]] = None,
	  /** Optional. Timeout for graceful YARN decommissioning. Graceful decommissioning facilitates the removal of cluster nodes without interrupting jobs in progress. The timeout specifies the amount of time to wait for jobs finish before forcefully removing nodes. The default timeout is 0 for forceful decommissioning, and the maximum timeout period is 1 day. (see JSON Mapping—Duration (https://developers.google.com/protocol-buffers/docs/proto3#json)).graceful_decommission_timeout is supported in Dataproc image versions 1.2+. */
		gracefulDecommissionTimeout: Option[String] = None,
	  /** Optional. operation id of the parent operation sending the repair request */
		parentOperationId: Option[String] = None,
	  /** Optional. Cluster to be repaired */
		cluster: Option[Schema.ClusterToRepair] = None
	)
	
	object NodePool {
		enum RepairActionEnum extends Enum[RepairActionEnum] { case REPAIR_ACTION_UNSPECIFIED, DELETE }
	}
	case class NodePool(
	  /** Required. A unique id of the node pool. Primary and Secondary workers can be specified using special reserved ids PRIMARY_WORKER_POOL and SECONDARY_WORKER_POOL respectively. Aux node pools can be referenced using corresponding pool id. */
		id: Option[String] = None,
	  /** Name of instances to be repaired. These instances must belong to specified node pool. */
		instanceNames: Option[List[String]] = None,
	  /** Required. Repair action to take on specified resources of the node pool. */
		repairAction: Option[Schema.NodePool.RepairActionEnum] = None
	)
	
	object ClusterToRepair {
		enum ClusterRepairActionEnum extends Enum[ClusterRepairActionEnum] { case CLUSTER_REPAIR_ACTION_UNSPECIFIED, REPAIR_ERROR_DUE_TO_UPDATE_CLUSTER }
	}
	case class ClusterToRepair(
	  /** Required. Repair action to take on the cluster resource. */
		clusterRepairAction: Option[Schema.ClusterToRepair.ClusterRepairActionEnum] = None
	)
	
	case class ListClustersResponse(
	  /** Output only. The clusters in the project. */
		clusters: Option[List[Schema.Cluster]] = None,
	  /** Output only. This token is included in the response if there are more results to fetch. To fetch additional results, provide this value as the page_token in a subsequent ListClustersRequest. */
		nextPageToken: Option[String] = None
	)
	
	object DiagnoseClusterRequest {
		enum TarballAccessEnum extends Enum[TarballAccessEnum] { case TARBALL_ACCESS_UNSPECIFIED, GOOGLE_CLOUD_SUPPORT, GOOGLE_DATAPROC_DIAGNOSE }
	}
	case class DiagnoseClusterRequest(
	  /** Optional. (Optional) The output Cloud Storage directory for the diagnostic tarball. If not specified, a task-specific directory in the cluster's staging bucket will be used. */
		tarballGcsDir: Option[String] = None,
	  /** Optional. (Optional) The access type to the diagnostic tarball. If not specified, falls back to default access of the bucket */
		tarballAccess: Option[Schema.DiagnoseClusterRequest.TarballAccessEnum] = None,
	  /** Optional. Time interval in which diagnosis should be carried out on the cluster. */
		diagnosisInterval: Option[Schema.Interval] = None,
	  /** Optional. DEPRECATED Specifies the job on which diagnosis is to be performed. Format: projects/{project}/regions/{region}/jobs/{job} */
		job: Option[String] = None,
	  /** Optional. DEPRECATED Specifies the yarn application on which diagnosis is to be performed. */
		yarnApplicationId: Option[String] = None,
	  /** Optional. Specifies a list of jobs on which diagnosis is to be performed. Format: projects/{project}/regions/{region}/jobs/{job} */
		jobs: Option[List[String]] = None,
	  /** Optional. Specifies a list of yarn applications on which diagnosis is to be performed. */
		yarnApplicationIds: Option[List[String]] = None
	)
	
	case class Interval(
	  /** Optional. Inclusive start of the interval.If specified, a Timestamp matching this interval will have to be the same or after the start. */
		startTime: Option[String] = None,
	  /** Optional. Exclusive end of the interval.If specified, a Timestamp matching this interval will have to be before the end. */
		endTime: Option[String] = None
	)
	
	case class InjectCredentialsRequest(
	  /** Required. The cluster UUID. */
		clusterUuid: Option[String] = None,
	  /** Required. The encrypted credentials being injected in to the cluster.The client is responsible for encrypting the credentials in a way that is supported by the cluster.A wrapped value is used here so that the actual contents of the encrypted credentials are not written to audit logs. */
		credentialsCiphertext: Option[String] = None
	)
	
	case class SubmitJobRequest(
	  /** Required. The job resource. */
		job: Option[Schema.Job] = None,
	  /** Optional. A unique id used to identify the request. If the server receives two SubmitJobRequest (https://cloud.google.com/dataproc/docs/reference/rpc/google.cloud.dataproc.v1#google.cloud.dataproc.v1.SubmitJobRequest)s with the same id, then the second request will be ignored and the first Job created and stored in the backend is returned.It is recommended to always set this value to a UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier).The id must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
		requestId: Option[String] = None
	)
	
	case class Job(
	  /** Optional. The fully qualified reference to the job, which can be used to obtain the equivalent REST path of the job resource. If this property is not specified when a job is created, the server generates a job_id. */
		reference: Option[Schema.JobReference] = None,
	  /** Required. Job information, including how, when, and where to run the job. */
		placement: Option[Schema.JobPlacement] = None,
	  /** Optional. Job is a Hadoop job. */
		hadoopJob: Option[Schema.HadoopJob] = None,
	  /** Optional. Job is a Spark job. */
		sparkJob: Option[Schema.SparkJob] = None,
	  /** Optional. Job is a PySpark job. */
		pysparkJob: Option[Schema.PySparkJob] = None,
	  /** Optional. Job is a Hive job. */
		hiveJob: Option[Schema.HiveJob] = None,
	  /** Optional. Job is a Pig job. */
		pigJob: Option[Schema.PigJob] = None,
	  /** Optional. Job is a SparkR job. */
		sparkRJob: Option[Schema.SparkRJob] = None,
	  /** Optional. Job is a SparkSql job. */
		sparkSqlJob: Option[Schema.SparkSqlJob] = None,
	  /** Optional. Job is a Presto job. */
		prestoJob: Option[Schema.PrestoJob] = None,
	  /** Optional. Job is a Trino job. */
		trinoJob: Option[Schema.TrinoJob] = None,
	  /** Optional. Job is a Flink job. */
		flinkJob: Option[Schema.FlinkJob] = None,
	  /** Output only. The job status. Additional application-specific status information might be contained in the type_job and yarn_applications fields. */
		status: Option[Schema.JobStatus] = None,
	  /** Output only. The previous job status. */
		statusHistory: Option[List[Schema.JobStatus]] = None,
	  /** Output only. The collection of YARN applications spun up by this job.Beta Feature: This report is available for testing purposes only. It might be changed before final release. */
		yarnApplications: Option[List[Schema.YarnApplication]] = None,
	  /** Output only. A URI pointing to the location of the stdout of the job's driver program. */
		driverOutputResourceUri: Option[String] = None,
	  /** Output only. If present, the location of miscellaneous control files which can be used as part of job setup and handling. If not present, control files might be placed in the same location as driver_output_uri. */
		driverControlFilesUri: Option[String] = None,
	  /** Optional. The labels to associate with this job. Label keys must contain 1 to 63 characters, and must conform to RFC 1035 (https://www.ietf.org/rfc/rfc1035.txt). Label values can be empty, but, if present, must contain 1 to 63 characters, and must conform to RFC 1035 (https://www.ietf.org/rfc/rfc1035.txt). No more than 32 labels can be associated with a job. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Job scheduling configuration. */
		scheduling: Option[Schema.JobScheduling] = None,
	  /** Output only. A UUID that uniquely identifies a job within the project over time. This is in contrast to a user-settable reference.job_id that might be reused over time. */
		jobUuid: Option[String] = None,
	  /** Output only. Indicates whether the job is completed. If the value is false, the job is still in progress. If true, the job is completed, and status.state field will indicate if it was successful, failed, or cancelled. */
		done: Option[Boolean] = None,
	  /** Optional. Driver scheduling configuration. */
		driverSchedulingConfig: Option[Schema.DriverSchedulingConfig] = None
	)
	
	case class JobReference(
	  /** Optional. The ID of the Google Cloud Platform project that the job belongs to. If specified, must match the request project ID. */
		projectId: Option[String] = None,
	  /** Optional. The job ID, which must be unique within the project.The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), or hyphens (-). The maximum length is 100 characters.If not specified by the caller, the job ID will be provided by the server. */
		jobId: Option[String] = None
	)
	
	case class JobPlacement(
	  /** Required. The name of the cluster where the job will be submitted. */
		clusterName: Option[String] = None,
	  /** Output only. A cluster UUID generated by the Dataproc service when the job is submitted. */
		clusterUuid: Option[String] = None,
	  /** Optional. Cluster labels to identify a cluster where the job will be submitted. */
		clusterLabels: Option[Map[String, String]] = None
	)
	
	case class HadoopJob(
	  /** The HCFS URI of the jar file containing the main class. Examples: 'gs://foo-bucket/analytics-binaries/extract-useful-metrics-mr.jar' 'hdfs:/tmp/test-samples/custom-wordcount.jar' 'file:///home/usr/lib/hadoop-mapreduce/hadoop-mapreduce-examples.jar' */
		mainJarFileUri: Option[String] = None,
	  /** The name of the driver's main class. The jar file containing the class must be in the default CLASSPATH or specified in jar_file_uris. */
		mainClass: Option[String] = None,
	  /** Optional. The arguments to pass to the driver. Do not include arguments, such as -libjars or -Dfoo=bar, that can be set as job properties, since a collision might occur that causes an incorrect job submission. */
		args: Option[List[String]] = None,
	  /** Optional. Jar file URIs to add to the CLASSPATHs of the Hadoop driver and tasks. */
		jarFileUris: Option[List[String]] = None,
	  /** Optional. HCFS (Hadoop Compatible Filesystem) URIs of files to be copied to the working directory of Hadoop drivers and distributed tasks. Useful for naively parallel tasks. */
		fileUris: Option[List[String]] = None,
	  /** Optional. HCFS URIs of archives to be extracted in the working directory of Hadoop drivers and tasks. Supported file types: .jar, .tar, .tar.gz, .tgz, or .zip. */
		archiveUris: Option[List[String]] = None,
	  /** Optional. A mapping of property names to values, used to configure Hadoop. Properties that conflict with values set by the Dataproc API might be overwritten. Can include properties set in /etc/hadoop/conf/&#42;-site and classes in user code. */
		properties: Option[Map[String, String]] = None,
	  /** Optional. The runtime log config for job execution. */
		loggingConfig: Option[Schema.LoggingConfig] = None
	)
	
	object LoggingConfig {
		enum DriverLogLevelsEnum extends Enum[DriverLogLevelsEnum] { case LEVEL_UNSPECIFIED, ALL, TRACE, DEBUG, INFO, WARN, ERROR, FATAL, OFF }
	}
	case class LoggingConfig(
	  /** The per-package log levels for the driver. This can include "root" package name to configure rootLogger. Examples: - 'com.google = FATAL' - 'root = INFO' - 'org.apache = DEBUG' */
		driverLogLevels: Option[Map[String, Schema.LoggingConfig.DriverLogLevelsEnum]] = None
	)
	
	case class SparkJob(
	  /** The HCFS URI of the jar file that contains the main class. */
		mainJarFileUri: Option[String] = None,
	  /** The name of the driver's main class. The jar file that contains the class must be in the default CLASSPATH or specified in SparkJob.jar_file_uris. */
		mainClass: Option[String] = None,
	  /** Optional. The arguments to pass to the driver. Do not include arguments, such as --conf, that can be set as job properties, since a collision may occur that causes an incorrect job submission. */
		args: Option[List[String]] = None,
	  /** Optional. HCFS URIs of jar files to add to the CLASSPATHs of the Spark driver and tasks. */
		jarFileUris: Option[List[String]] = None,
	  /** Optional. HCFS URIs of files to be placed in the working directory of each executor. Useful for naively parallel tasks. */
		fileUris: Option[List[String]] = None,
	  /** Optional. HCFS URIs of archives to be extracted into the working directory of each executor. Supported file types: .jar, .tar, .tar.gz, .tgz, and .zip. */
		archiveUris: Option[List[String]] = None,
	  /** Optional. A mapping of property names to values, used to configure Spark. Properties that conflict with values set by the Dataproc API might be overwritten. Can include properties set in /etc/spark/conf/spark-defaults.conf and classes in user code. */
		properties: Option[Map[String, String]] = None,
	  /** Optional. The runtime log config for job execution. */
		loggingConfig: Option[Schema.LoggingConfig] = None
	)
	
	case class PySparkJob(
	  /** Required. The HCFS URI of the main Python file to use as the driver. Must be a .py file. */
		mainPythonFileUri: Option[String] = None,
	  /** Optional. The arguments to pass to the driver. Do not include arguments, such as --conf, that can be set as job properties, since a collision may occur that causes an incorrect job submission. */
		args: Option[List[String]] = None,
	  /** Optional. HCFS file URIs of Python files to pass to the PySpark framework. Supported file types: .py, .egg, and .zip. */
		pythonFileUris: Option[List[String]] = None,
	  /** Optional. HCFS URIs of jar files to add to the CLASSPATHs of the Python driver and tasks. */
		jarFileUris: Option[List[String]] = None,
	  /** Optional. HCFS URIs of files to be placed in the working directory of each executor. Useful for naively parallel tasks. */
		fileUris: Option[List[String]] = None,
	  /** Optional. HCFS URIs of archives to be extracted into the working directory of each executor. Supported file types: .jar, .tar, .tar.gz, .tgz, and .zip. */
		archiveUris: Option[List[String]] = None,
	  /** Optional. A mapping of property names to values, used to configure PySpark. Properties that conflict with values set by the Dataproc API might be overwritten. Can include properties set in /etc/spark/conf/spark-defaults.conf and classes in user code. */
		properties: Option[Map[String, String]] = None,
	  /** Optional. The runtime log config for job execution. */
		loggingConfig: Option[Schema.LoggingConfig] = None
	)
	
	case class HiveJob(
	  /** The HCFS URI of the script that contains Hive queries. */
		queryFileUri: Option[String] = None,
	  /** A list of queries. */
		queryList: Option[Schema.QueryList] = None,
	  /** Optional. Whether to continue executing queries if a query fails. The default value is false. Setting to true can be useful when executing independent parallel queries. */
		continueOnFailure: Option[Boolean] = None,
	  /** Optional. Mapping of query variable names to values (equivalent to the Hive command: SET name="value";). */
		scriptVariables: Option[Map[String, String]] = None,
	  /** Optional. A mapping of property names and values, used to configure Hive. Properties that conflict with values set by the Dataproc API might be overwritten. Can include properties set in /etc/hadoop/conf/&#42;-site.xml, /etc/hive/conf/hive-site.xml, and classes in user code. */
		properties: Option[Map[String, String]] = None,
	  /** Optional. HCFS URIs of jar files to add to the CLASSPATH of the Hive server and Hadoop MapReduce (MR) tasks. Can contain Hive SerDes and UDFs. */
		jarFileUris: Option[List[String]] = None
	)
	
	case class QueryList(
	  /** Required. The queries to execute. You do not need to end a query expression with a semicolon. Multiple queries can be specified in one string by separating each with a semicolon. Here is an example of a Dataproc API snippet that uses a QueryList to specify a HiveJob: "hiveJob": { "queryList": { "queries": [ "query1", "query2", "query3;query4", ] } }  */
		queries: Option[List[String]] = None
	)
	
	case class PigJob(
	  /** The HCFS URI of the script that contains the Pig queries. */
		queryFileUri: Option[String] = None,
	  /** A list of queries. */
		queryList: Option[Schema.QueryList] = None,
	  /** Optional. Whether to continue executing queries if a query fails. The default value is false. Setting to true can be useful when executing independent parallel queries. */
		continueOnFailure: Option[Boolean] = None,
	  /** Optional. Mapping of query variable names to values (equivalent to the Pig command: name=[value]). */
		scriptVariables: Option[Map[String, String]] = None,
	  /** Optional. A mapping of property names to values, used to configure Pig. Properties that conflict with values set by the Dataproc API might be overwritten. Can include properties set in /etc/hadoop/conf/&#42;-site.xml, /etc/pig/conf/pig.properties, and classes in user code. */
		properties: Option[Map[String, String]] = None,
	  /** Optional. HCFS URIs of jar files to add to the CLASSPATH of the Pig Client and Hadoop MapReduce (MR) tasks. Can contain Pig UDFs. */
		jarFileUris: Option[List[String]] = None,
	  /** Optional. The runtime log config for job execution. */
		loggingConfig: Option[Schema.LoggingConfig] = None
	)
	
	case class SparkRJob(
	  /** Required. The HCFS URI of the main R file to use as the driver. Must be a .R file. */
		mainRFileUri: Option[String] = None,
	  /** Optional. The arguments to pass to the driver. Do not include arguments, such as --conf, that can be set as job properties, since a collision may occur that causes an incorrect job submission. */
		args: Option[List[String]] = None,
	  /** Optional. HCFS URIs of files to be placed in the working directory of each executor. Useful for naively parallel tasks. */
		fileUris: Option[List[String]] = None,
	  /** Optional. HCFS URIs of archives to be extracted into the working directory of each executor. Supported file types: .jar, .tar, .tar.gz, .tgz, and .zip. */
		archiveUris: Option[List[String]] = None,
	  /** Optional. A mapping of property names to values, used to configure SparkR. Properties that conflict with values set by the Dataproc API might be overwritten. Can include properties set in /etc/spark/conf/spark-defaults.conf and classes in user code. */
		properties: Option[Map[String, String]] = None,
	  /** Optional. The runtime log config for job execution. */
		loggingConfig: Option[Schema.LoggingConfig] = None
	)
	
	case class SparkSqlJob(
	  /** The HCFS URI of the script that contains SQL queries. */
		queryFileUri: Option[String] = None,
	  /** A list of queries. */
		queryList: Option[Schema.QueryList] = None,
	  /** Optional. Mapping of query variable names to values (equivalent to the Spark SQL command: SET name="value";). */
		scriptVariables: Option[Map[String, String]] = None,
	  /** Optional. A mapping of property names to values, used to configure Spark SQL's SparkConf. Properties that conflict with values set by the Dataproc API might be overwritten. */
		properties: Option[Map[String, String]] = None,
	  /** Optional. HCFS URIs of jar files to be added to the Spark CLASSPATH. */
		jarFileUris: Option[List[String]] = None,
	  /** Optional. The runtime log config for job execution. */
		loggingConfig: Option[Schema.LoggingConfig] = None
	)
	
	case class PrestoJob(
	  /** The HCFS URI of the script that contains SQL queries. */
		queryFileUri: Option[String] = None,
	  /** A list of queries. */
		queryList: Option[Schema.QueryList] = None,
	  /** Optional. Whether to continue executing queries if a query fails. The default value is false. Setting to true can be useful when executing independent parallel queries. */
		continueOnFailure: Option[Boolean] = None,
	  /** Optional. The format in which query output will be displayed. See the Presto documentation for supported output formats */
		outputFormat: Option[String] = None,
	  /** Optional. Presto client tags to attach to this query */
		clientTags: Option[List[String]] = None,
	  /** Optional. A mapping of property names to values. Used to set Presto session properties (https://prestodb.io/docs/current/sql/set-session.html) Equivalent to using the --session flag in the Presto CLI */
		properties: Option[Map[String, String]] = None,
	  /** Optional. The runtime log config for job execution. */
		loggingConfig: Option[Schema.LoggingConfig] = None
	)
	
	case class TrinoJob(
	  /** The HCFS URI of the script that contains SQL queries. */
		queryFileUri: Option[String] = None,
	  /** A list of queries. */
		queryList: Option[Schema.QueryList] = None,
	  /** Optional. Whether to continue executing queries if a query fails. The default value is false. Setting to true can be useful when executing independent parallel queries. */
		continueOnFailure: Option[Boolean] = None,
	  /** Optional. The format in which query output will be displayed. See the Trino documentation for supported output formats */
		outputFormat: Option[String] = None,
	  /** Optional. Trino client tags to attach to this query */
		clientTags: Option[List[String]] = None,
	  /** Optional. A mapping of property names to values. Used to set Trino session properties (https://trino.io/docs/current/sql/set-session.html) Equivalent to using the --session flag in the Trino CLI */
		properties: Option[Map[String, String]] = None,
	  /** Optional. The runtime log config for job execution. */
		loggingConfig: Option[Schema.LoggingConfig] = None
	)
	
	case class FlinkJob(
	  /** The HCFS URI of the jar file that contains the main class. */
		mainJarFileUri: Option[String] = None,
	  /** The name of the driver's main class. The jar file that contains the class must be in the default CLASSPATH or specified in jarFileUris. */
		mainClass: Option[String] = None,
	  /** Optional. The arguments to pass to the driver. Do not include arguments, such as --conf, that can be set as job properties, since a collision might occur that causes an incorrect job submission. */
		args: Option[List[String]] = None,
	  /** Optional. HCFS URIs of jar files to add to the CLASSPATHs of the Flink driver and tasks. */
		jarFileUris: Option[List[String]] = None,
	  /** Optional. HCFS URI of the savepoint, which contains the last saved progress for starting the current job. */
		savepointUri: Option[String] = None,
	  /** Optional. A mapping of property names to values, used to configure Flink. Properties that conflict with values set by the Dataproc API might be overwritten. Can include properties set in /etc/flink/conf/flink-defaults.conf and classes in user code. */
		properties: Option[Map[String, String]] = None,
	  /** Optional. The runtime log config for job execution. */
		loggingConfig: Option[Schema.LoggingConfig] = None
	)
	
	object JobStatus {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, SETUP_DONE, RUNNING, CANCEL_PENDING, CANCEL_STARTED, CANCELLED, DONE, ERROR, ATTEMPT_FAILURE }
		enum SubstateEnum extends Enum[SubstateEnum] { case UNSPECIFIED, SUBMITTED, QUEUED, STALE_STATUS }
	}
	case class JobStatus(
	  /** Output only. A state message specifying the overall job state. */
		state: Option[Schema.JobStatus.StateEnum] = None,
	  /** Optional. Output only. Job state details, such as an error description if the state is ERROR. */
		details: Option[String] = None,
	  /** Output only. The time when this state was entered. */
		stateStartTime: Option[String] = None,
	  /** Output only. Additional state information, which includes status reported by the agent. */
		substate: Option[Schema.JobStatus.SubstateEnum] = None
	)
	
	object YarnApplication {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, NEW, NEW_SAVING, SUBMITTED, ACCEPTED, RUNNING, FINISHED, FAILED, KILLED }
	}
	case class YarnApplication(
	  /** Required. The application name. */
		name: Option[String] = None,
	  /** Required. The application state. */
		state: Option[Schema.YarnApplication.StateEnum] = None,
	  /** Required. The numerical progress of the application, from 1 to 100. */
		progress: Option[BigDecimal] = None,
	  /** Optional. The HTTP URL of the ApplicationMaster, HistoryServer, or TimelineServer that provides application-specific information. The URL uses the internal hostname, and requires a proxy server for resolution and, possibly, access. */
		trackingUrl: Option[String] = None
	)
	
	case class JobScheduling(
	  /** Optional. Maximum number of times per hour a driver can be restarted as a result of driver exiting with non-zero code before job is reported failed.A job might be reported as thrashing if the driver exits with a non-zero code four times within a 10-minute window.Maximum value is 10.Note: This restartable job option is not supported in Dataproc workflow templates (https://cloud.google.com/dataproc/docs/concepts/workflows/using-workflows#adding_jobs_to_a_template). */
		maxFailuresPerHour: Option[Int] = None,
	  /** Optional. Maximum total number of times a driver can be restarted as a result of the driver exiting with a non-zero code. After the maximum number is reached, the job will be reported as failed.Maximum value is 240.Note: Currently, this restartable job option is not supported in Dataproc workflow templates (https://cloud.google.com/dataproc/docs/concepts/workflows/using-workflows#adding_jobs_to_a_template). */
		maxFailuresTotal: Option[Int] = None
	)
	
	case class DriverSchedulingConfig(
	  /** Required. The amount of memory in MB the driver is requesting. */
		memoryMb: Option[Int] = None,
	  /** Required. The number of vCPUs the driver is requesting. */
		vcores: Option[Int] = None
	)
	
	case class ListJobsResponse(
	  /** Output only. Jobs list. */
		jobs: Option[List[Schema.Job]] = None,
	  /** Optional. This token is included in the response if there are more results to fetch. To fetch additional results, provide this value as the page_token in a subsequent ListJobsRequest. */
		nextPageToken: Option[String] = None,
	  /** Output only. List of jobs with kms_key-encrypted parameters that could not be decrypted. A response to a jobs.get request may indicate the reason for the decryption failure for a specific job. */
		unreachable: Option[List[String]] = None
	)
	
	case class CancelJobRequest(
	
	)
	
	object Session {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, TERMINATING, TERMINATED, FAILED }
	}
	case class Session(
	  /** Required. The resource name of the session. */
		name: Option[String] = None,
	  /** Output only. A session UUID (Unique Universal Identifier). The service generates this value when it creates the session. */
		uuid: Option[String] = None,
	  /** Output only. The time when the session was created. */
		createTime: Option[String] = None,
	  /** Optional. Jupyter session config. */
		jupyterSession: Option[Schema.JupyterConfig] = None,
	  /** Optional. Spark connect session config. */
		sparkConnectSession: Option[Schema.SparkConnectConfig] = None,
	  /** Output only. Runtime information about session execution. */
		runtimeInfo: Option[Schema.RuntimeInfo] = None,
	  /** Output only. A state of the session. */
		state: Option[Schema.Session.StateEnum] = None,
	  /** Output only. Session state details, such as the failure description if the state is FAILED. */
		stateMessage: Option[String] = None,
	  /** Output only. The time when the session entered the current state. */
		stateTime: Option[String] = None,
	  /** Output only. The email address of the user who created the session. */
		creator: Option[String] = None,
	  /** Optional. The labels to associate with the session. Label keys must contain 1 to 63 characters, and must conform to RFC 1035 (https://www.ietf.org/rfc/rfc1035.txt). Label values may be empty, but, if present, must contain 1 to 63 characters, and must conform to RFC 1035 (https://www.ietf.org/rfc/rfc1035.txt). No more than 32 labels can be associated with a session. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Runtime configuration for the session execution. */
		runtimeConfig: Option[Schema.RuntimeConfig] = None,
	  /** Optional. Environment configuration for the session execution. */
		environmentConfig: Option[Schema.EnvironmentConfig] = None,
	  /** Optional. The email address of the user who owns the session. */
		user: Option[String] = None,
	  /** Output only. Historical state information for the session. */
		stateHistory: Option[List[Schema.SessionStateHistory]] = None,
	  /** Optional. The session template used by the session.Only resource names, including project ID and location, are valid.Example: &#42; https://www.googleapis.com/compute/v1/projects/[project_id]/locations/[dataproc_region]/sessionTemplates/[template_id] &#42; projects/[project_id]/locations/[dataproc_region]/sessionTemplates/[template_id]The template must be in the same project and Dataproc region as the session. */
		sessionTemplate: Option[String] = None
	)
	
	object JupyterConfig {
		enum KernelEnum extends Enum[KernelEnum] { case KERNEL_UNSPECIFIED, PYTHON, SCALA }
	}
	case class JupyterConfig(
	  /** Optional. Kernel */
		kernel: Option[Schema.JupyterConfig.KernelEnum] = None,
	  /** Optional. Display name, shown in the Jupyter kernelspec card. */
		displayName: Option[String] = None
	)
	
	case class SparkConnectConfig(
	
	)
	
	object SessionStateHistory {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, TERMINATING, TERMINATED, FAILED }
	}
	case class SessionStateHistory(
	  /** Output only. The state of the session at this point in the session history. */
		state: Option[Schema.SessionStateHistory.StateEnum] = None,
	  /** Output only. Details about the state at this point in the session history. */
		stateMessage: Option[String] = None,
	  /** Output only. The time when the session entered the historical state. */
		stateStartTime: Option[String] = None
	)
	
	case class ListSessionsResponse(
	  /** Output only. The sessions from the specified collection. */
		sessions: Option[List[Schema.Session]] = None,
	  /** A token, which can be sent as page_token, to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class TerminateSessionRequest(
	  /** Optional. A unique ID used to identify the request. If the service receives two TerminateSessionRequest (https://cloud.google.com/dataproc/docs/reference/rpc/google.cloud.dataproc.v1#google.cloud.dataproc.v1.TerminateSessionRequest)s with the same ID, the second request is ignored.Recommendation: Set this value to a UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier).The value must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
		requestId: Option[String] = None
	)
	
	case class WriteSessionSparkApplicationContextRequest(
	  /** Required. Parent (Batch) resource reference. */
		parent: Option[String] = None,
	  /** Required. The batch of spark application context objects sent for ingestion. */
		sparkWrapperObjects: Option[List[Schema.SparkWrapperObject]] = None
	)
	
	case class WriteSessionSparkApplicationContextResponse(
	
	)
	
	case class SearchSessionSparkApplicationsResponse(
	  /** Output only. High level information corresponding to an application. */
		sparkApplications: Option[List[Schema.SparkApplication]] = None,
	  /** This token is included in the response if there are more results to fetch. To fetch additional results, provide this value as the page_token in a subsequent SearchSessionSparkApplicationsRequest. */
		nextPageToken: Option[String] = None
	)
	
	case class AccessSessionSparkApplicationResponse(
	  /** Output only. High level information corresponding to an application. */
		application: Option[Schema.ApplicationInfo] = None
	)
	
	case class SearchSessionSparkApplicationJobsResponse(
	  /** Output only. Data corresponding to a spark job. */
		sparkApplicationJobs: Option[List[Schema.JobData]] = None,
	  /** This token is included in the response if there are more results to fetch. To fetch additional results, provide this value as the page_token in a subsequent SearchSessionSparkApplicationJobsRequest. */
		nextPageToken: Option[String] = None
	)
	
	case class AccessSessionSparkApplicationJobResponse(
	  /** Output only. Data corresponding to a spark job. */
		jobData: Option[Schema.JobData] = None
	)
	
	case class SearchSessionSparkApplicationStagesResponse(
	  /** Output only. Data corresponding to a stage. */
		sparkApplicationStages: Option[List[Schema.StageData]] = None,
	  /** This token is included in the response if there are more results to fetch. To fetch additional results, provide this value as the page_token in a subsequent SearchSessionSparkApplicationStages. */
		nextPageToken: Option[String] = None
	)
	
	case class SearchSessionSparkApplicationStageAttemptsResponse(
	  /** Output only. Data corresponding to a stage attempts */
		sparkApplicationStageAttempts: Option[List[Schema.StageData]] = None,
	  /** This token is included in the response if there are more results to fetch. To fetch additional results, provide this value as the page_token in a subsequent SearchSessionSparkApplicationStageAttemptsRequest. */
		nextPageToken: Option[String] = None
	)
	
	case class AccessSessionSparkApplicationStageAttemptResponse(
	  /** Output only. Data corresponding to a stage. */
		stageData: Option[Schema.StageData] = None
	)
	
	case class SearchSessionSparkApplicationStageAttemptTasksResponse(
	  /** Output only. Data corresponding to tasks created by spark. */
		sparkApplicationStageAttemptTasks: Option[List[Schema.TaskData]] = None,
	  /** This token is included in the response if there are more results to fetch. To fetch additional results, provide this value as the page_token in a subsequent SearchSessionSparkApplicationStageAttemptTasksRequest. */
		nextPageToken: Option[String] = None
	)
	
	case class SearchSessionSparkApplicationExecutorsResponse(
	  /** Details about executors used by the application. */
		sparkApplicationExecutors: Option[List[Schema.ExecutorSummary]] = None,
	  /** This token is included in the response if there are more results to fetch. To fetch additional results, provide this value as the page_token in a subsequent SearchSessionSparkApplicationExecutorsRequest. */
		nextPageToken: Option[String] = None
	)
	
	case class SearchSessionSparkApplicationExecutorStageSummaryResponse(
	  /** Details about executors used by the application stage. */
		sparkApplicationStageExecutors: Option[List[Schema.ExecutorStageSummary]] = None,
	  /** This token is included in the response if there are more results to fetch. To fetch additional results, provide this value as the page_token in a subsequent SearchSessionSparkApplicationExecutorStageSummaryRequest. */
		nextPageToken: Option[String] = None
	)
	
	case class SearchSessionSparkApplicationSqlQueriesResponse(
	  /** Output only. SQL Execution Data */
		sparkApplicationSqlQueries: Option[List[Schema.SqlExecutionUiData]] = None,
	  /** This token is included in the response if there are more results to fetch. To fetch additional results, provide this value as the page_token in a subsequent SearchSessionSparkApplicationSqlQueriesRequest. */
		nextPageToken: Option[String] = None
	)
	
	case class AccessSessionSparkApplicationSqlQueryResponse(
	  /** SQL Execution Data */
		executionData: Option[Schema.SqlExecutionUiData] = None
	)
	
	case class AccessSessionSparkApplicationSqlSparkPlanGraphResponse(
	  /** SparkPlanGraph for a Spark Application execution. */
		sparkPlanGraph: Option[Schema.SparkPlanGraph] = None
	)
	
	case class AccessSessionSparkApplicationStageRddOperationGraphResponse(
	  /** RDD operation graph for a Spark Application Stage. */
		rddOperationGraph: Option[Schema.RddOperationGraph] = None
	)
	
	case class AccessSessionSparkApplicationEnvironmentInfoResponse(
	  /** Details about the Environment that the application is running in. */
		applicationEnvironmentInfo: Option[Schema.ApplicationEnvironmentInfo] = None
	)
	
	case class SummarizeSessionSparkApplicationJobsResponse(
	  /** Summary of a Spark Application Jobs */
		jobsSummary: Option[Schema.JobsSummary] = None
	)
	
	case class SummarizeSessionSparkApplicationStagesResponse(
	  /** Summary of a Spark Application Stages */
		stagesSummary: Option[Schema.StagesSummary] = None
	)
	
	case class SummarizeSessionSparkApplicationStageAttemptTasksResponse(
	  /** Summary of tasks for a Spark Application Stage Attempt */
		stageAttemptTasksSummary: Option[Schema.StageAttemptTasksSummary] = None
	)
	
	case class SummarizeSessionSparkApplicationExecutorsResponse(
	  /** Spark Application Id */
		applicationId: Option[String] = None,
	  /** Consolidated summary for active executors. */
		activeExecutorSummary: Option[Schema.ConsolidatedExecutorSummary] = None,
	  /** Consolidated summary for dead executors. */
		deadExecutorSummary: Option[Schema.ConsolidatedExecutorSummary] = None,
	  /** Overall consolidated summary for all executors. */
		totalExecutorSummary: Option[Schema.ConsolidatedExecutorSummary] = None
	)
	
	case class SessionTemplate(
	  /** Required. The resource name of the session template. */
		name: Option[String] = None,
	  /** Optional. Brief description of the template. */
		description: Option[String] = None,
	  /** Output only. The time when the template was created. */
		createTime: Option[String] = None,
	  /** Optional. Jupyter session config. */
		jupyterSession: Option[Schema.JupyterConfig] = None,
	  /** Optional. Spark connect session config. */
		sparkConnectSession: Option[Schema.SparkConnectConfig] = None,
	  /** Output only. The email address of the user who created the template. */
		creator: Option[String] = None,
	  /** Optional. Labels to associate with sessions created using this template. Label keys must contain 1 to 63 characters, and must conform to RFC 1035 (https://www.ietf.org/rfc/rfc1035.txt). Label values can be empty, but, if present, must contain 1 to 63 characters and conform to RFC 1035 (https://www.ietf.org/rfc/rfc1035.txt). No more than 32 labels can be associated with a session. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Runtime configuration for session execution. */
		runtimeConfig: Option[Schema.RuntimeConfig] = None,
	  /** Optional. Environment configuration for session execution. */
		environmentConfig: Option[Schema.EnvironmentConfig] = None,
	  /** Output only. The time the template was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. A session template UUID (Unique Universal Identifier). The service generates this value when it creates the session template. */
		uuid: Option[String] = None
	)
	
	case class ListSessionTemplatesResponse(
	  /** Output only. Session template list */
		sessionTemplates: Option[List[Schema.SessionTemplate]] = None,
	  /** A token, which can be sent as page_token to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class WorkflowTemplate(
		id: Option[String] = None,
	  /** Output only. The resource name of the workflow template, as described in https://cloud.google.com/apis/design/resource_names. For projects.regions.workflowTemplates, the resource name of the template has the following format: projects/{project_id}/regions/{region}/workflowTemplates/{template_id} For projects.locations.workflowTemplates, the resource name of the template has the following format: projects/{project_id}/locations/{location}/workflowTemplates/{template_id} */
		name: Option[String] = None,
	  /** Optional. Used to perform a consistent read-modify-write.This field should be left blank for a CreateWorkflowTemplate request. It is required for an UpdateWorkflowTemplate request, and must match the current server version. A typical update template flow would fetch the current template with a GetWorkflowTemplate request, which will return the current template with the version field filled in with the current server version. The user updates other fields in the template, then returns it as part of the UpdateWorkflowTemplate request. */
		version: Option[Int] = None,
	  /** Output only. The time template was created. */
		createTime: Option[String] = None,
	  /** Output only. The time template was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. The labels to associate with this template. These labels will be propagated to all jobs and clusters created by the workflow instance.Label keys must contain 1 to 63 characters, and must conform to RFC 1035 (https://www.ietf.org/rfc/rfc1035.txt).Label values may be empty, but, if present, must contain 1 to 63 characters, and must conform to RFC 1035 (https://www.ietf.org/rfc/rfc1035.txt).No more than 32 labels can be associated with a template. */
		labels: Option[Map[String, String]] = None,
	  /** Required. WorkflowTemplate scheduling information. */
		placement: Option[Schema.WorkflowTemplatePlacement] = None,
	  /** Required. The Directed Acyclic Graph of Jobs to submit. */
		jobs: Option[List[Schema.OrderedJob]] = None,
	  /** Optional. Template parameters whose values are substituted into the template. Values for parameters must be provided when the template is instantiated. */
		parameters: Option[List[Schema.TemplateParameter]] = None,
	  /** Optional. Timeout duration for the DAG of jobs, expressed in seconds (see JSON representation of duration (https://developers.google.com/protocol-buffers/docs/proto3#json)). The timeout duration must be from 10 minutes ("600s") to 24 hours ("86400s"). The timer begins when the first job is submitted. If the workflow is running at the end of the timeout period, any remaining jobs are cancelled, the workflow is ended, and if the workflow was running on a managed cluster, the cluster is deleted. */
		dagTimeout: Option[String] = None,
	  /** Optional. Encryption settings for encrypting workflow template job arguments. */
		encryptionConfig: Option[Schema.GoogleCloudDataprocV1WorkflowTemplateEncryptionConfig] = None
	)
	
	case class WorkflowTemplatePlacement(
	  /** A cluster that is managed by the workflow. */
		managedCluster: Option[Schema.ManagedCluster] = None,
	  /** Optional. A selector that chooses target cluster for jobs based on metadata.The selector is evaluated at the time each job is submitted. */
		clusterSelector: Option[Schema.ClusterSelector] = None
	)
	
	case class ManagedCluster(
	  /** Required. The cluster name prefix. A unique cluster name will be formed by appending a random suffix.The name must contain only lower-case letters (a-z), numbers (0-9), and hyphens (-). Must begin with a letter. Cannot begin or end with hyphen. Must consist of between 2 and 35 characters. */
		clusterName: Option[String] = None,
	  /** Required. The cluster configuration. */
		config: Option[Schema.ClusterConfig] = None,
	  /** Optional. The labels to associate with this cluster.Label keys must be between 1 and 63 characters long, and must conform to the following PCRE regular expression: \p{Ll}\p{Lo}{0,62}Label values must be between 1 and 63 characters long, and must conform to the following PCRE regular expression: \p{Ll}\p{Lo}\p{N}_-{0,63}No more than 32 labels can be associated with a given cluster. */
		labels: Option[Map[String, String]] = None
	)
	
	case class ClusterSelector(
	  /** Optional. The zone where workflow process executes. This parameter does not affect the selection of the cluster.If unspecified, the zone of the first cluster matching the selector is used. */
		zone: Option[String] = None,
	  /** Required. The cluster labels. Cluster must have all labels to match. */
		clusterLabels: Option[Map[String, String]] = None
	)
	
	case class OrderedJob(
	  /** Required. The step id. The id must be unique among all jobs within the template.The step id is used as prefix for job id, as job goog-dataproc-workflow-step-id label, and in prerequisiteStepIds field from other steps.The id must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). Cannot begin or end with underscore or hyphen. Must consist of between 3 and 50 characters. */
		stepId: Option[String] = None,
	  /** Optional. Job is a Hadoop job. */
		hadoopJob: Option[Schema.HadoopJob] = None,
	  /** Optional. Job is a Spark job. */
		sparkJob: Option[Schema.SparkJob] = None,
	  /** Optional. Job is a PySpark job. */
		pysparkJob: Option[Schema.PySparkJob] = None,
	  /** Optional. Job is a Hive job. */
		hiveJob: Option[Schema.HiveJob] = None,
	  /** Optional. Job is a Pig job. */
		pigJob: Option[Schema.PigJob] = None,
	  /** Optional. Job is a SparkR job. */
		sparkRJob: Option[Schema.SparkRJob] = None,
	  /** Optional. Job is a SparkSql job. */
		sparkSqlJob: Option[Schema.SparkSqlJob] = None,
	  /** Optional. Job is a Presto job. */
		prestoJob: Option[Schema.PrestoJob] = None,
	  /** Optional. Job is a Trino job. */
		trinoJob: Option[Schema.TrinoJob] = None,
	  /** Optional. Job is a Flink job. */
		flinkJob: Option[Schema.FlinkJob] = None,
	  /** Optional. The labels to associate with this job.Label keys must be between 1 and 63 characters long, and must conform to the following regular expression: \p{Ll}\p{Lo}{0,62}Label values must be between 1 and 63 characters long, and must conform to the following regular expression: \p{Ll}\p{Lo}\p{N}_-{0,63}No more than 32 labels can be associated with a given job. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Job scheduling configuration. */
		scheduling: Option[Schema.JobScheduling] = None,
	  /** Optional. The optional list of prerequisite job step_ids. If not specified, the job will start at the beginning of workflow. */
		prerequisiteStepIds: Option[List[String]] = None
	)
	
	case class TemplateParameter(
	  /** Required. Parameter name. The parameter name is used as the key, and paired with the parameter value, which are passed to the template when the template is instantiated. The name must contain only capital letters (A-Z), numbers (0-9), and underscores (_), and must not start with a number. The maximum length is 40 characters. */
		name: Option[String] = None,
	  /** Required. Paths to all fields that the parameter replaces. A field is allowed to appear in at most one parameter's list of field paths.A field path is similar in syntax to a google.protobuf.FieldMask. For example, a field path that references the zone field of a workflow template's cluster selector would be specified as placement.clusterSelector.zone.Also, field paths can reference fields using the following syntax: Values in maps can be referenced by key: labels'key' placement.clusterSelector.clusterLabels'key' placement.managedCluster.labels'key' placement.clusterSelector.clusterLabels'key' jobs'step-id'.labels'key' Jobs in the jobs list can be referenced by step-id: jobs'step-id'.hadoopJob.mainJarFileUri jobs'step-id'.hiveJob.queryFileUri jobs'step-id'.pySparkJob.mainPythonFileUri jobs'step-id'.hadoopJob.jarFileUris0 jobs'step-id'.hadoopJob.archiveUris0 jobs'step-id'.hadoopJob.fileUris0 jobs'step-id'.pySparkJob.pythonFileUris0 Items in repeated fields can be referenced by a zero-based index: jobs'step-id'.sparkJob.args0 Other examples: jobs'step-id'.hadoopJob.properties'key' jobs'step-id'.hadoopJob.args0 jobs'step-id'.hiveJob.scriptVariables'key' jobs'step-id'.hadoopJob.mainJarFileUri placement.clusterSelector.zoneIt may not be possible to parameterize maps and repeated fields in their entirety since only individual map values and individual items in repeated fields can be referenced. For example, the following field paths are invalid: placement.clusterSelector.clusterLabels jobs'step-id'.sparkJob.args */
		fields: Option[List[String]] = None,
	  /** Optional. Brief description of the parameter. Must not exceed 1024 characters. */
		description: Option[String] = None,
	  /** Optional. Validation rules to be applied to this parameter's value. */
		validation: Option[Schema.ParameterValidation] = None
	)
	
	case class ParameterValidation(
	  /** Validation based on regular expressions. */
		regex: Option[Schema.RegexValidation] = None,
	  /** Validation based on a list of allowed values. */
		values: Option[Schema.ValueValidation] = None
	)
	
	case class RegexValidation(
	  /** Required. RE2 regular expressions used to validate the parameter's value. The value must match the regex in its entirety (substring matches are not sufficient). */
		regexes: Option[List[String]] = None
	)
	
	case class ValueValidation(
	  /** Required. List of allowed values for the parameter. */
		values: Option[List[String]] = None
	)
	
	case class GoogleCloudDataprocV1WorkflowTemplateEncryptionConfig(
	  /** Optional. The Cloud KMS key name to use for encrypting workflow template job arguments.When this this key is provided, the following workflow template job arguments (https://cloud.google.com/dataproc/docs/concepts/workflows/use-workflows#adding_jobs_to_a_template), if present, are CMEK encrypted (https://cloud.google.com/dataproc/docs/concepts/configuring-clusters/customer-managed-encryption#use_cmek_with_workflow_template_data): FlinkJob args (https://cloud.google.com/dataproc/docs/reference/rest/v1/FlinkJob) HadoopJob args (https://cloud.google.com/dataproc/docs/reference/rest/v1/HadoopJob) SparkJob args (https://cloud.google.com/dataproc/docs/reference/rest/v1/SparkJob) SparkRJob args (https://cloud.google.com/dataproc/docs/reference/rest/v1/SparkRJob) PySparkJob args (https://cloud.google.com/dataproc/docs/reference/rest/v1/PySparkJob) SparkSqlJob (https://cloud.google.com/dataproc/docs/reference/rest/v1/SparkSqlJob) scriptVariables and queryList.queries HiveJob (https://cloud.google.com/dataproc/docs/reference/rest/v1/HiveJob) scriptVariables and queryList.queries PigJob (https://cloud.google.com/dataproc/docs/reference/rest/v1/PigJob) scriptVariables and queryList.queries PrestoJob (https://cloud.google.com/dataproc/docs/reference/rest/v1/PrestoJob) scriptVariables and queryList.queries */
		kmsKey: Option[String] = None
	)
	
	case class InstantiateWorkflowTemplateRequest(
	  /** Optional. The version of workflow template to instantiate. If specified, the workflow will be instantiated only if the current version of the workflow template has the supplied version.This option cannot be used to instantiate a previous version of workflow template. */
		version: Option[Int] = None,
	  /** Optional. A tag that prevents multiple concurrent workflow instances with the same tag from running. This mitigates risk of concurrent instances started due to retries.It is recommended to always set this value to a UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier).The tag must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
		requestId: Option[String] = None,
	  /** Optional. Map from parameter names to values that should be used for those parameters. Values may not exceed 1000 characters. */
		parameters: Option[Map[String, String]] = None
	)
	
	case class ListWorkflowTemplatesResponse(
	  /** Output only. WorkflowTemplates list. */
		templates: Option[List[Schema.WorkflowTemplate]] = None,
	  /** Output only. This token is included in the response if there are more results to fetch. To fetch additional results, provide this value as the page_token in a subsequent ListWorkflowTemplatesRequest. */
		nextPageToken: Option[String] = None,
	  /** Output only. List of workflow templates that could not be included in the response. Attempting to get one of these resources may indicate why it was not included in the list response. */
		unreachable: Option[List[String]] = None
	)
	
	case class ResizeNodeGroupRequest(
	  /** Required. The number of running instances for the node group to maintain. The group adds or removes instances to maintain the number of instances specified by this parameter. */
		size: Option[Int] = None,
	  /** Optional. A unique ID used to identify the request. If the server receives two ResizeNodeGroupRequest (https://cloud.google.com/dataproc/docs/reference/rpc/google.cloud.dataproc.v1#google.cloud.dataproc.v1.ResizeNodeGroupRequests) with the same ID, the second request is ignored and the first google.longrunning.Operation created and stored in the backend is returned.Recommendation: Set this value to a UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier).The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
		requestId: Option[String] = None,
	  /** Optional. Timeout for graceful YARN decommissioning. Graceful decommissioning (https://cloud.google.com/dataproc/docs/concepts/configuring-clusters/scaling-clusters#graceful_decommissioning) allows the removal of nodes from the Compute Engine node group without interrupting jobs in progress. This timeout specifies how long to wait for jobs in progress to finish before forcefully removing nodes (and potentially interrupting jobs). Default timeout is 0 (for forceful decommission), and the maximum allowed timeout is 1 day. (see JSON representation of Duration (https://developers.google.com/protocol-buffers/docs/proto3#json)).Only supported on Dataproc image versions 1.2 and higher. */
		gracefulDecommissionTimeout: Option[String] = None,
	  /** Optional. operation id of the parent operation sending the resize request */
		parentOperationId: Option[String] = None
	)
	
	object RepairNodeGroupRequest {
		enum RepairActionEnum extends Enum[RepairActionEnum] { case REPAIR_ACTION_UNSPECIFIED, REPLACE }
	}
	case class RepairNodeGroupRequest(
	  /** Required. Name of instances to be repaired. These instances must belong to specified node pool. */
		instanceNames: Option[List[String]] = None,
	  /** Required. Repair action to take on specified resources of the node pool. */
		repairAction: Option[Schema.RepairNodeGroupRequest.RepairActionEnum] = None,
	  /** Optional. A unique ID used to identify the request. If the server receives two RepairNodeGroupRequest with the same ID, the second request is ignored and the first google.longrunning.Operation created and stored in the backend is returned.Recommendation: Set this value to a UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier).The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
		requestId: Option[String] = None
	)
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the resource. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None
	)
	
	case class Policy(
	  /** Specifies the format of the policy.Valid values are 0, 1, and 3. Requests that specify an invalid value are rejected.Any operation that affects conditional role bindings must specify version 3. This requirement applies to the following operations: Getting a policy that includes a conditional role binding Adding a conditional role binding to a policy Changing a conditional role binding in a policy Removing any role binding, with or without a condition, from a policy that includes conditionsImportant: If you use IAM Conditions, you must include the etag field whenever you call setIamPolicy. If you omit this field, then IAM allows you to overwrite a version 3 policy with a version 1 policy, and all of the conditions in the version 3 policy are lost.If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset.To learn which resources support conditions in their IAM policies, see the IAM documentation (https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of members, or principals, with a role. Optionally, may specify a condition that determines how and when the bindings are applied. Each of the bindings must contain at least one principal.The bindings in a Policy can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the bindings grant 50 different roles to user:alice@example.com, and not to any other principal, then you can add another 1,450 principals to the bindings in the Policy. */
		bindings: Option[List[Schema.Binding]] = None,
	  /** etag is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the etag in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An etag is returned in the response to getIamPolicy, and systems are expected to put that etag in the request to setIamPolicy to ensure that their change will be applied to the same version of the policy.Important: If you use IAM Conditions, you must include the etag field whenever you call setIamPolicy. If you omit this field, then IAM allows you to overwrite a version 3 policy with a version 1 policy, and all of the conditions in the version 3 policy are lost. */
		etag: Option[String] = None
	)
	
	case class Binding(
	  /** Role that is assigned to the list of members, or principals. For example, roles/viewer, roles/editor, or roles/owner.For an overview of the IAM roles and permissions, see the IAM documentation (https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see here (https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. members can have the following values: allUsers: A special identifier that represents anyone who is on the internet; with or without a Google account. allAuthenticatedUsers: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. user:{emailid}: An email address that represents a specific Google account. For example, alice@example.com . serviceAccount:{emailid}: An email address that represents a Google service account. For example, my-other-app@appspot.gserviceaccount.com. serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]: An identifier for a Kubernetes service account (https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, my-project.svc.id.goog[my-namespace/my-kubernetes-sa]. group:{emailid}: An email address that represents a Google group. For example, admins@example.com. domain:{domain}: The G Suite domain (primary) that represents all the users of that domain. For example, google.com or example.com. principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}: A single identity in a workforce identity pool. principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}: All workforce identities in a group. principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}: All workforce identities with a specific attribute value. principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;: All identities in a workforce identity pool. principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}: A single identity in a workload identity pool. principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}: A workload identity pool group. principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}: All identities in a workload identity pool with a certain attribute. principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;: All identities in a workload identity pool. deleted:user:{emailid}?uid={uniqueid}: An email address (plus unique identifier) representing a user that has been recently deleted. For example, alice@example.com?uid=123456789012345678901. If the user is recovered, this value reverts to user:{emailid} and the recovered user retains the role in the binding. deleted:serviceAccount:{emailid}?uid={uniqueid}: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901. If the service account is undeleted, this value reverts to serviceAccount:{emailid} and the undeleted service account retains the role in the binding. deleted:group:{emailid}?uid={uniqueid}: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, admins@example.com?uid=123456789012345678901. If the group is recovered, this value reverts to group:{emailid} and the recovered group retains the role in the binding. deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}: Deleted single identity in a workforce identity pool. For example, deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value. */
		members: Option[List[String]] = None,
	  /** The condition that is associated with this binding.If the condition evaluates to true, then this binding applies to the current request.If the condition evaluates to false, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding.To learn which resources support conditions in their IAM policies, see the IAM documentation (https://cloud.google.com/iam/help/conditions/resource-policies). */
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
	
	case class GetIamPolicyRequest(
	  /** OPTIONAL: A GetPolicyOptions object for specifying options to GetIamPolicy. */
		options: Option[Schema.GetPolicyOptions] = None
	)
	
	case class GetPolicyOptions(
	  /** Optional. The maximum policy version that will be used to format the policy.Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected.Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset.The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1.To learn which resources support conditions in their IAM policies, see the IAM documentation (https://cloud.google.com/iam/help/conditions/resource-policies). */
		requestedPolicyVersion: Option[Int] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the resource. Permissions with wildcards (such as &#42; or storage.&#42;) are not allowed. For more information see IAM Overview (https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of TestPermissionsRequest.permissions that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	object AnalyzeOperationMetadata {
		enum AnalyzedWorkloadTypeEnum extends Enum[AnalyzedWorkloadTypeEnum] { case WORKLOAD_TYPE_UNSPECIFIED, BATCH }
	}
	case class AnalyzeOperationMetadata(
	  /** Output only. name of the workload being analyzed. */
		analyzedWorkloadName: Option[String] = None,
	  /** Output only. Type of the workload being analyzed. */
		analyzedWorkloadType: Option[Schema.AnalyzeOperationMetadata.AnalyzedWorkloadTypeEnum] = None,
	  /** Output only. unique identifier of the workload typically generated by control plane. E.g. batch uuid. */
		analyzedWorkloadUuid: Option[String] = None,
	  /** Output only. The time when the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the operation finished. */
		doneTime: Option[String] = None,
	  /** Output only. Short description of the operation. */
		description: Option[String] = None,
	  /** Output only. Labels associated with the operation. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Warnings encountered during operation execution. */
		warnings: Option[List[String]] = None
	)
	
	object BatchOperationMetadata {
		enum OperationTypeEnum extends Enum[OperationTypeEnum] { case BATCH_OPERATION_TYPE_UNSPECIFIED, BATCH }
	}
	case class BatchOperationMetadata(
	  /** Name of the batch for the operation. */
		batch: Option[String] = None,
	  /** Batch UUID for the operation. */
		batchUuid: Option[String] = None,
	  /** The time when the operation was created. */
		createTime: Option[String] = None,
	  /** The time when the operation finished. */
		doneTime: Option[String] = None,
	  /** The operation type. */
		operationType: Option[Schema.BatchOperationMetadata.OperationTypeEnum] = None,
	  /** Short description of the operation. */
		description: Option[String] = None,
	  /** Labels associated with the operation. */
		labels: Option[Map[String, String]] = None,
	  /** Warnings encountered during operation execution. */
		warnings: Option[List[String]] = None
	)
	
	case class ClusterOperationMetadata(
	  /** Output only. Name of the cluster for the operation. */
		clusterName: Option[String] = None,
	  /** Output only. Cluster UUID for the operation. */
		clusterUuid: Option[String] = None,
	  /** Output only. Current operation status. */
		status: Option[Schema.ClusterOperationStatus] = None,
	  /** Output only. The previous operation status. */
		statusHistory: Option[List[Schema.ClusterOperationStatus]] = None,
	  /** Output only. The operation type. */
		operationType: Option[String] = None,
	  /** Output only. Short description of operation. */
		description: Option[String] = None,
	  /** Output only. Labels associated with the operation */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Errors encountered during operation execution. */
		warnings: Option[List[String]] = None,
	  /** Output only. Child operation ids */
		childOperationIds: Option[List[String]] = None
	)
	
	object ClusterOperationStatus {
		enum StateEnum extends Enum[StateEnum] { case UNKNOWN, PENDING, RUNNING, DONE }
	}
	case class ClusterOperationStatus(
	  /** Output only. A message containing the operation state. */
		state: Option[Schema.ClusterOperationStatus.StateEnum] = None,
	  /** Output only. A message containing the detailed operation state. */
		innerState: Option[String] = None,
	  /** Output only. A message containing any operation metadata details. */
		details: Option[String] = None,
	  /** Output only. The time this state was entered. */
		stateStartTime: Option[String] = None
	)
	
	case class DiagnoseClusterResults(
	  /** Output only. The Cloud Storage URI of the diagnostic output. The output report is a plain text file with a summary of collected diagnostics. */
		outputUri: Option[String] = None
	)
	
	case class JobMetadata(
	  /** Output only. The job id. */
		jobId: Option[String] = None,
	  /** Output only. Most recent job status. */
		status: Option[Schema.JobStatus] = None,
	  /** Output only. Operation type. */
		operationType: Option[String] = None,
	  /** Output only. Job submission time. */
		startTime: Option[String] = None
	)
	
	object SessionOperationMetadata {
		enum OperationTypeEnum extends Enum[OperationTypeEnum] { case SESSION_OPERATION_TYPE_UNSPECIFIED, CREATE, TERMINATE, DELETE }
	}
	case class SessionOperationMetadata(
	  /** Name of the session for the operation. */
		session: Option[String] = None,
	  /** Session UUID for the operation. */
		sessionUuid: Option[String] = None,
	  /** The time when the operation was created. */
		createTime: Option[String] = None,
	  /** The time when the operation was finished. */
		doneTime: Option[String] = None,
	  /** The operation type. */
		operationType: Option[Schema.SessionOperationMetadata.OperationTypeEnum] = None,
	  /** Short description of the operation. */
		description: Option[String] = None,
	  /** Labels associated with the operation. */
		labels: Option[Map[String, String]] = None,
	  /** Warnings encountered during operation execution. */
		warnings: Option[List[String]] = None
	)
	
	object NodeGroupOperationMetadata {
		enum OperationTypeEnum extends Enum[OperationTypeEnum] { case NODE_GROUP_OPERATION_TYPE_UNSPECIFIED, CREATE, UPDATE, DELETE, RESIZE, REPAIR, UPDATE_LABELS, START, STOP }
	}
	case class NodeGroupOperationMetadata(
	  /** Output only. Node group ID for the operation. */
		nodeGroupId: Option[String] = None,
	  /** Output only. Cluster UUID associated with the node group operation. */
		clusterUuid: Option[String] = None,
	  /** Output only. Current operation status. */
		status: Option[Schema.ClusterOperationStatus] = None,
	  /** Output only. The previous operation status. */
		statusHistory: Option[List[Schema.ClusterOperationStatus]] = None,
	  /** The operation type. */
		operationType: Option[Schema.NodeGroupOperationMetadata.OperationTypeEnum] = None,
	  /** Output only. Short description of operation. */
		description: Option[String] = None,
	  /** Output only. Labels associated with the operation. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Errors encountered during operation execution. */
		warnings: Option[List[String]] = None
	)
	
	object WorkflowMetadata {
		enum StateEnum extends Enum[StateEnum] { case UNKNOWN, PENDING, RUNNING, DONE }
	}
	case class WorkflowMetadata(
	  /** Output only. The resource name of the workflow template as described in https://cloud.google.com/apis/design/resource_names. For projects.regions.workflowTemplates, the resource name of the template has the following format: projects/{project_id}/regions/{region}/workflowTemplates/{template_id} For projects.locations.workflowTemplates, the resource name of the template has the following format: projects/{project_id}/locations/{location}/workflowTemplates/{template_id} */
		template: Option[String] = None,
	  /** Output only. The version of template at the time of workflow instantiation. */
		version: Option[Int] = None,
	  /** Output only. The create cluster operation metadata. */
		createCluster: Option[Schema.ClusterOperation] = None,
	  /** Output only. The workflow graph. */
		graph: Option[Schema.WorkflowGraph] = None,
	  /** Output only. The delete cluster operation metadata. */
		deleteCluster: Option[Schema.ClusterOperation] = None,
	  /** Output only. The workflow state. */
		state: Option[Schema.WorkflowMetadata.StateEnum] = None,
	  /** Output only. The name of the target cluster. */
		clusterName: Option[String] = None,
	  /** Map from parameter names to values that were used for those parameters. */
		parameters: Option[Map[String, String]] = None,
	  /** Output only. Workflow start time. */
		startTime: Option[String] = None,
	  /** Output only. Workflow end time. */
		endTime: Option[String] = None,
	  /** Output only. The UUID of target cluster. */
		clusterUuid: Option[String] = None,
	  /** Output only. The timeout duration for the DAG of jobs, expressed in seconds (see JSON representation of duration (https://developers.google.com/protocol-buffers/docs/proto3#json)). */
		dagTimeout: Option[String] = None,
	  /** Output only. DAG start time, only set for workflows with dag_timeout when DAG begins. */
		dagStartTime: Option[String] = None,
	  /** Output only. DAG end time, only set for workflows with dag_timeout when DAG ends. */
		dagEndTime: Option[String] = None
	)
	
	case class ClusterOperation(
	  /** Output only. The id of the cluster operation. */
		operationId: Option[String] = None,
	  /** Output only. Error, if operation failed. */
		error: Option[String] = None,
	  /** Output only. Indicates the operation is done. */
		done: Option[Boolean] = None
	)
	
	case class WorkflowGraph(
	  /** Output only. The workflow nodes. */
		nodes: Option[List[Schema.WorkflowNode]] = None
	)
	
	object WorkflowNode {
		enum StateEnum extends Enum[StateEnum] { case NODE_STATE_UNSPECIFIED, BLOCKED, RUNNABLE, RUNNING, COMPLETED, FAILED }
	}
	case class WorkflowNode(
	  /** Output only. The name of the node. */
		stepId: Option[String] = None,
	  /** Output only. Node's prerequisite nodes. */
		prerequisiteStepIds: Option[List[String]] = None,
	  /** Output only. The job id; populated after the node enters RUNNING state. */
		jobId: Option[String] = None,
	  /** Output only. The node state. */
		state: Option[Schema.WorkflowNode.StateEnum] = None,
	  /** Output only. The error detail. */
		error: Option[String] = None
	)
}
