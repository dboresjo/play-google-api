package com.boresjo.play.api.google.dataflow

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GetDebugConfigRequest(
	  /** The worker id, i.e., VM hostname. */
		workerId: Option[String] = None,
	  /** The internal component id for which debug configuration is requested. */
		componentId: Option[String] = None,
	  /** The [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints) that contains the job specified by job_id. */
		location: Option[String] = None
	)
	
	case class GetDebugConfigResponse(
	  /** The encoded debug configuration for the requested component. */
		config: Option[String] = None
	)
	
	object SendDebugCaptureRequest {
		enum DataFormatEnum extends Enum[DataFormatEnum] { case DATA_FORMAT_UNSPECIFIED, RAW, JSON, ZLIB, BROTLI }
	}
	case class SendDebugCaptureRequest(
	  /** The worker id, i.e., VM hostname. */
		workerId: Option[String] = None,
	  /** The internal component id for which debug information is sent. */
		componentId: Option[String] = None,
	  /** The encoded debug information. */
		data: Option[String] = None,
	  /** Format for the data field above (id=5). */
		dataFormat: Option[Schema.SendDebugCaptureRequest.DataFormatEnum] = None,
	  /** The [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints) that contains the job specified by job_id. */
		location: Option[String] = None
	)
	
	case class SendDebugCaptureResponse(
	
	)
	
	object Snapshot {
		enum StateEnum extends Enum[StateEnum] { case UNKNOWN_SNAPSHOT_STATE, PENDING, RUNNING, READY, FAILED, DELETED }
	}
	case class Snapshot(
	  /** The unique ID of this snapshot. */
		id: Option[String] = None,
	  /** The project this snapshot belongs to. */
		projectId: Option[String] = None,
	  /** The job this snapshot was created from. */
		sourceJobId: Option[String] = None,
	  /** The time this snapshot was created. */
		creationTime: Option[String] = None,
	  /** The time after which this snapshot will be automatically deleted. */
		ttl: Option[String] = None,
	  /** State of the snapshot. */
		state: Option[Schema.Snapshot.StateEnum] = None,
	  /** Pub/Sub snapshot metadata. */
		pubsubMetadata: Option[List[Schema.PubsubSnapshotMetadata]] = None,
	  /** User specified description of the snapshot. Maybe empty. */
		description: Option[String] = None,
	  /** The disk byte size of the snapshot. Only available for snapshots in READY state. */
		diskSizeBytes: Option[String] = None,
	  /** Cloud region where this snapshot lives in, e.g., "us-central1". */
		region: Option[String] = None
	)
	
	case class PubsubSnapshotMetadata(
	  /** The name of the Pubsub topic. */
		topicName: Option[String] = None,
	  /** The name of the Pubsub snapshot. */
		snapshotName: Option[String] = None,
	  /** The expire time of the Pubsub snapshot. */
		expireTime: Option[String] = None
	)
	
	case class DeleteSnapshotResponse(
	
	)
	
	case class ListSnapshotsResponse(
	  /** Returned snapshots. */
		snapshots: Option[List[Schema.Snapshot]] = None
	)
	
	object Job {
		enum TypeEnum extends Enum[TypeEnum] { case JOB_TYPE_UNKNOWN, JOB_TYPE_BATCH, JOB_TYPE_STREAMING }
		enum CurrentStateEnum extends Enum[CurrentStateEnum] { case JOB_STATE_UNKNOWN, JOB_STATE_STOPPED, JOB_STATE_RUNNING, JOB_STATE_DONE, JOB_STATE_FAILED, JOB_STATE_CANCELLED, JOB_STATE_UPDATED, JOB_STATE_DRAINING, JOB_STATE_DRAINED, JOB_STATE_PENDING, JOB_STATE_CANCELLING, JOB_STATE_QUEUED, JOB_STATE_RESOURCE_CLEANING_UP }
		enum RequestedStateEnum extends Enum[RequestedStateEnum] { case JOB_STATE_UNKNOWN, JOB_STATE_STOPPED, JOB_STATE_RUNNING, JOB_STATE_DONE, JOB_STATE_FAILED, JOB_STATE_CANCELLED, JOB_STATE_UPDATED, JOB_STATE_DRAINING, JOB_STATE_DRAINED, JOB_STATE_PENDING, JOB_STATE_CANCELLING, JOB_STATE_QUEUED, JOB_STATE_RESOURCE_CLEANING_UP }
	}
	case class Job(
	  /** The unique ID of this job. This field is set by the Dataflow service when the job is created, and is immutable for the life of the job. */
		id: Option[String] = None,
	  /** The ID of the Google Cloud project that the job belongs to. */
		projectId: Option[String] = None,
	  /** Optional. The user-specified Dataflow job name. Only one active job with a given name can exist in a project within one region at any given time. Jobs in different regions can have the same name. If a caller attempts to create a job with the same name as an active job that already exists, the attempt returns the existing job. The name must match the regular expression `[a-z]([-a-z0-9]{0,1022}[a-z0-9])?` */
		name: Option[String] = None,
	  /** Optional. The type of Dataflow job. */
		`type`: Option[Schema.Job.TypeEnum] = None,
	  /** Optional. The environment for the job. */
		environment: Option[Schema.Environment] = None,
	  /** Exactly one of step or steps_location should be specified. The top-level steps that constitute the entire job. Only retrieved with JOB_VIEW_ALL. */
		steps: Option[List[Schema.Step]] = None,
	  /** The Cloud Storage location where the steps are stored. */
		stepsLocation: Option[String] = None,
	  /** The current state of the job. Jobs are created in the `JOB_STATE_STOPPED` state unless otherwise specified. A job in the `JOB_STATE_RUNNING` state may asynchronously enter a terminal state. After a job has reached a terminal state, no further state updates may be made. This field might be mutated by the Dataflow service; callers cannot mutate it. */
		currentState: Option[Schema.Job.CurrentStateEnum] = None,
	  /** The timestamp associated with the current state. */
		currentStateTime: Option[String] = None,
	  /** The job's requested state. Applies to `UpdateJob` requests. Set `requested_state` with `UpdateJob` requests to switch between the states `JOB_STATE_STOPPED` and `JOB_STATE_RUNNING`. You can also use `UpdateJob` requests to change a job's state from `JOB_STATE_RUNNING` to `JOB_STATE_CANCELLED`, `JOB_STATE_DONE`, or `JOB_STATE_DRAINED`. These states irrevocably terminate the job if it hasn't already reached a terminal state. This field has no effect on `CreateJob` requests. */
		requestedState: Option[Schema.Job.RequestedStateEnum] = None,
	  /** Deprecated. */
		executionInfo: Option[Schema.JobExecutionInfo] = None,
	  /** The timestamp when the job was initially created. Immutable and set by the Cloud Dataflow service. */
		createTime: Option[String] = None,
	  /** If this job is an update of an existing job, this field is the job ID of the job it replaced. When sending a `CreateJobRequest`, you can update a job by specifying it here. The job named here is stopped, and its intermediate state is transferred to this job. */
		replaceJobId: Option[String] = None,
	  /** Optional. The map of transform name prefixes of the job to be replaced to the corresponding name prefixes of the new job. */
		transformNameMapping: Option[Map[String, String]] = None,
	  /** The client's unique identifier of the job, re-used across retried attempts. If this field is set, the service will ensure its uniqueness. The request to create a job will fail if the service has knowledge of a previously submitted job with the same client's ID and job name. The caller may use this field to ensure idempotence of job creation across retried attempts to create a job. By default, the field is empty and, in that case, the service ignores it. */
		clientRequestId: Option[String] = None,
	  /** If another job is an update of this job (and thus, this job is in `JOB_STATE_UPDATED`), this field contains the ID of that job. */
		replacedByJobId: Option[String] = None,
	  /** A set of files the system should be aware of that are used for temporary storage. These temporary files will be removed on job completion. No duplicates are allowed. No file patterns are supported. The supported files are: Google Cloud Storage: storage.googleapis.com/{bucket}/{object} bucket.storage.googleapis.com/{object} */
		tempFiles: Option[List[String]] = None,
	  /** User-defined labels for this job. The labels map can contain no more than 64 entries. Entries of the labels map are UTF8 strings that comply with the following restrictions: &#42; Keys must conform to regexp: \p{Ll}\p{Lo}{0,62} &#42; Values must conform to regexp: [\p{Ll}\p{Lo}\p{N}_-]{0,63} &#42; Both keys and values are additionally constrained to be <= 128 bytes in size. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. The [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints) that contains this job. */
		location: Option[String] = None,
	  /** Preliminary field: The format of this data may change at any time. A description of the user pipeline and stages through which it is executed. Created by Cloud Dataflow service. Only retrieved with JOB_VIEW_DESCRIPTION or JOB_VIEW_ALL. */
		pipelineDescription: Option[Schema.PipelineDescription] = None,
	  /** This field may be mutated by the Cloud Dataflow service; callers cannot mutate it. */
		stageStates: Option[List[Schema.ExecutionStageState]] = None,
	  /** This field is populated by the Dataflow service to support filtering jobs by the metadata values provided here. Populated for ListJobs and all GetJob views SUMMARY and higher. */
		jobMetadata: Option[Schema.JobMetadata] = None,
	  /** The timestamp when the job was started (transitioned to JOB_STATE_PENDING). Flexible resource scheduling jobs are started with some delay after job creation, so start_time is unset before start and is updated when the job is started by the Cloud Dataflow service. For other jobs, start_time always equals to create_time and is immutable and set by the Cloud Dataflow service. */
		startTime: Option[String] = None,
	  /** If this is specified, the job's initial state is populated from the given snapshot. */
		createdFromSnapshotId: Option[String] = None,
	  /** Reserved for future use. This field is set only in responses from the server; it is ignored if it is set in any requests. */
		satisfiesPzs: Option[Boolean] = None,
	  /** This field may ONLY be modified at runtime using the projects.jobs.update method to adjust job behavior. This field has no effect when specified at job creation. */
		runtimeUpdatableParams: Option[Schema.RuntimeUpdatableParams] = None,
	  /** Output only. Reserved for future use. This field is set only in responses from the server; it is ignored if it is set in any requests. */
		satisfiesPzi: Option[Boolean] = None,
	  /** Output only. Resources used by the Dataflow Service to run the job. */
		serviceResources: Option[Schema.ServiceResources] = None
	)
	
	object Environment {
		enum FlexResourceSchedulingGoalEnum extends Enum[FlexResourceSchedulingGoalEnum] { case FLEXRS_UNSPECIFIED, FLEXRS_SPEED_OPTIMIZED, FLEXRS_COST_OPTIMIZED }
		enum ShuffleModeEnum extends Enum[ShuffleModeEnum] { case SHUFFLE_MODE_UNSPECIFIED, VM_BASED, SERVICE_BASED }
		enum StreamingModeEnum extends Enum[StreamingModeEnum] { case STREAMING_MODE_UNSPECIFIED, STREAMING_MODE_EXACTLY_ONCE, STREAMING_MODE_AT_LEAST_ONCE }
	}
	case class Environment(
	  /** The prefix of the resources the system should use for temporary storage. The system will append the suffix "/temp-{JOBNAME} to this resource prefix, where {JOBNAME} is the value of the job_name field. The resulting bucket and object prefix is used as the prefix of the resources used to store temporary data needed during the job execution. NOTE: This will override the value in taskrunner_settings. The supported resource type is: Google Cloud Storage: storage.googleapis.com/{bucket}/{object} bucket.storage.googleapis.com/{object} */
		tempStoragePrefix: Option[String] = None,
	  /** The type of cluster manager API to use. If unknown or unspecified, the service will attempt to choose a reasonable default. This should be in the form of the API service name, e.g. "compute.googleapis.com". */
		clusterManagerApiService: Option[String] = None,
	  /** The list of experiments to enable. This field should be used for SDK related experiments and not for service related experiments. The proper field for service related experiments is service_options. */
		experiments: Option[List[String]] = None,
	  /** Optional. The list of service options to enable. This field should be used for service related experiments only. These experiments, when graduating to GA, should be replaced by dedicated fields or become default (i.e. always on). */
		serviceOptions: Option[List[String]] = None,
	  /** Optional. If set, contains the Cloud KMS key identifier used to encrypt data at rest, AKA a Customer Managed Encryption Key (CMEK). Format: projects/PROJECT_ID/locations/LOCATION/keyRings/KEY_RING/cryptoKeys/KEY */
		serviceKmsKeyName: Option[String] = None,
	  /** The worker pools. At least one "harness" worker pool must be specified in order for the job to have workers. */
		workerPools: Option[List[Schema.WorkerPool]] = None,
	  /** A description of the process that generated the request. */
		userAgent: Option[Map[String, JsValue]] = None,
	  /** A structure describing which components and their versions of the service are required in order to run the job. */
		version: Option[Map[String, JsValue]] = None,
	  /** Optional. The dataset for the current project where various workflow related tables are stored. The supported resource type is: Google BigQuery: bigquery.googleapis.com/{dataset} */
		dataset: Option[String] = None,
	  /** The Cloud Dataflow SDK pipeline options specified by the user. These options are passed through the service and are used to recreate the SDK pipeline options on the worker in a language agnostic and platform independent way. */
		sdkPipelineOptions: Option[Map[String, JsValue]] = None,
	  /** Experimental settings. */
		internalExperiments: Option[Map[String, JsValue]] = None,
	  /** Optional. Identity to run virtual machines as. Defaults to the default account. */
		serviceAccountEmail: Option[String] = None,
	  /** Optional. Which Flexible Resource Scheduling mode to run in. */
		flexResourceSchedulingGoal: Option[Schema.Environment.FlexResourceSchedulingGoalEnum] = None,
	  /** Optional. The Compute Engine region (https://cloud.google.com/compute/docs/regions-zones/regions-zones) in which worker processing should occur, e.g. "us-west1". Mutually exclusive with worker_zone. If neither worker_region nor worker_zone is specified, default to the control plane's region. */
		workerRegion: Option[String] = None,
	  /** Optional. The Compute Engine zone (https://cloud.google.com/compute/docs/regions-zones/regions-zones) in which worker processing should occur, e.g. "us-west1-a". Mutually exclusive with worker_region. If neither worker_region nor worker_zone is specified, a zone in the control plane's region is chosen based on available capacity. */
		workerZone: Option[String] = None,
	  /** Output only. The shuffle mode used for the job. */
		shuffleMode: Option[Schema.Environment.ShuffleModeEnum] = None,
	  /** Optional. Any debugging options to be supplied to the job. */
		debugOptions: Option[Schema.DebugOptions] = None,
	  /** Output only. Whether the job uses the Streaming Engine resource-based billing model. */
		useStreamingEngineResourceBasedBilling: Option[Boolean] = None,
	  /** Optional. Specifies the Streaming Engine message processing guarantees. Reduces cost and latency but might result in duplicate messages committed to storage. Designed to run simple mapping streaming ETL jobs at the lowest cost. For example, Change Data Capture (CDC) to BigQuery is a canonical use case. For more information, see [Set the pipeline streaming mode](https://cloud.google.com/dataflow/docs/guides/streaming-modes). */
		streamingMode: Option[Schema.Environment.StreamingModeEnum] = None
	)
	
	object WorkerPool {
		enum DefaultPackageSetEnum extends Enum[DefaultPackageSetEnum] { case DEFAULT_PACKAGE_SET_UNKNOWN, DEFAULT_PACKAGE_SET_NONE, DEFAULT_PACKAGE_SET_JAVA, DEFAULT_PACKAGE_SET_PYTHON }
		enum TeardownPolicyEnum extends Enum[TeardownPolicyEnum] { case TEARDOWN_POLICY_UNKNOWN, TEARDOWN_ALWAYS, TEARDOWN_ON_SUCCESS, TEARDOWN_NEVER }
		enum IpConfigurationEnum extends Enum[IpConfigurationEnum] { case WORKER_IP_UNSPECIFIED, WORKER_IP_PUBLIC, WORKER_IP_PRIVATE }
	}
	case class WorkerPool(
	  /** The kind of the worker pool; currently only `harness` and `shuffle` are supported. */
		kind: Option[String] = None,
	  /** Number of Google Compute Engine workers in this pool needed to execute the job. If zero or unspecified, the service will attempt to choose a reasonable default. */
		numWorkers: Option[Int] = None,
	  /** Packages to be installed on workers. */
		packages: Option[List[Schema.Package]] = None,
	  /** The default package set to install. This allows the service to select a default set of packages which are useful to worker harnesses written in a particular language. */
		defaultPackageSet: Option[Schema.WorkerPool.DefaultPackageSetEnum] = None,
	  /** Machine type (e.g. "n1-standard-1"). If empty or unspecified, the service will attempt to choose a reasonable default. */
		machineType: Option[String] = None,
	  /** Sets the policy for determining when to turndown worker pool. Allowed values are: `TEARDOWN_ALWAYS`, `TEARDOWN_ON_SUCCESS`, and `TEARDOWN_NEVER`. `TEARDOWN_ALWAYS` means workers are always torn down regardless of whether the job succeeds. `TEARDOWN_ON_SUCCESS` means workers are torn down if the job succeeds. `TEARDOWN_NEVER` means the workers are never torn down. If the workers are not torn down by the service, they will continue to run and use Google Compute Engine VM resources in the user's project until they are explicitly terminated by the user. Because of this, Google recommends using the `TEARDOWN_ALWAYS` policy except for small, manually supervised test jobs. If unknown or unspecified, the service will attempt to choose a reasonable default. */
		teardownPolicy: Option[Schema.WorkerPool.TeardownPolicyEnum] = None,
	  /** Size of root disk for VMs, in GB. If zero or unspecified, the service will attempt to choose a reasonable default. */
		diskSizeGb: Option[Int] = None,
	  /** Type of root disk for VMs. If empty or unspecified, the service will attempt to choose a reasonable default. */
		diskType: Option[String] = None,
	  /** Fully qualified source image for disks. */
		diskSourceImage: Option[String] = None,
	  /** Zone to run the worker pools in. If empty or unspecified, the service will attempt to choose a reasonable default. */
		zone: Option[String] = None,
	  /** Settings passed through to Google Compute Engine workers when using the standard Dataflow task runner. Users should ignore this field. */
		taskrunnerSettings: Option[Schema.TaskRunnerSettings] = None,
	  /** The action to take on host maintenance, as defined by the Google Compute Engine API. */
		onHostMaintenance: Option[String] = None,
	  /** Data disks that are used by a VM in this workflow. */
		dataDisks: Option[List[Schema.Disk]] = None,
	  /** Metadata to set on the Google Compute Engine VMs. */
		metadata: Option[Map[String, String]] = None,
	  /** Settings for autoscaling of this WorkerPool. */
		autoscalingSettings: Option[Schema.AutoscalingSettings] = None,
	  /** Extra arguments for this worker pool. */
		poolArgs: Option[Map[String, JsValue]] = None,
	  /** Network to which VMs will be assigned. If empty or unspecified, the service will use the network "default". */
		network: Option[String] = None,
	  /** Subnetwork to which VMs will be assigned, if desired. Expected to be of the form "regions/REGION/subnetworks/SUBNETWORK". */
		subnetwork: Option[String] = None,
	  /** Required. Docker container image that executes the Cloud Dataflow worker harness, residing in Google Container Registry. Deprecated for the Fn API path. Use sdk_harness_container_images instead. */
		workerHarnessContainerImage: Option[String] = None,
	  /** The number of threads per worker harness. If empty or unspecified, the service will choose a number of threads (according to the number of cores on the selected machine type for batch, or 1 by convention for streaming). */
		numThreadsPerWorker: Option[Int] = None,
	  /** Configuration for VM IPs. */
		ipConfiguration: Option[Schema.WorkerPool.IpConfigurationEnum] = None,
	  /** Set of SDK harness containers needed to execute this pipeline. This will only be set in the Fn API path. For non-cross-language pipelines this should have only one entry. Cross-language pipelines will have two or more entries. */
		sdkHarnessContainerImages: Option[List[Schema.SdkHarnessContainerImage]] = None
	)
	
	case class Package(
	  /** The name of the package. */
		name: Option[String] = None,
	  /** The resource to read the package from. The supported resource type is: Google Cloud Storage: storage.googleapis.com/{bucket} bucket.storage.googleapis.com/ */
		location: Option[String] = None
	)
	
	case class TaskRunnerSettings(
	  /** The UNIX user ID on the worker VM to use for tasks launched by taskrunner; e.g. "root". */
		taskUser: Option[String] = None,
	  /** The UNIX group ID on the worker VM to use for tasks launched by taskrunner; e.g. "wheel". */
		taskGroup: Option[String] = None,
	  /** The OAuth2 scopes to be requested by the taskrunner in order to access the Cloud Dataflow API. */
		oauthScopes: Option[List[String]] = None,
	  /** The base URL for the taskrunner to use when accessing Google Cloud APIs. When workers access Google Cloud APIs, they logically do so via relative URLs. If this field is specified, it supplies the base URL to use for resolving these relative URLs. The normative algorithm used is defined by RFC 1808, "Relative Uniform Resource Locators". If not specified, the default value is "http://www.googleapis.com/" */
		baseUrl: Option[String] = None,
	  /** The API version of endpoint, e.g. "v1b3" */
		dataflowApiVersion: Option[String] = None,
	  /** The settings to pass to the parallel worker harness. */
		parallelWorkerSettings: Option[Schema.WorkerSettings] = None,
	  /** The location on the worker for task-specific subdirectories. */
		baseTaskDir: Option[String] = None,
	  /** Whether to continue taskrunner if an exception is hit. */
		continueOnException: Option[Boolean] = None,
	  /** Whether to send taskrunner log info to Google Compute Engine VM serial console. */
		logToSerialconsole: Option[Boolean] = None,
	  /** Whether to also send taskrunner log info to stderr. */
		alsologtostderr: Option[Boolean] = None,
	  /** Indicates where to put logs. If this is not specified, the logs will not be uploaded. The supported resource type is: Google Cloud Storage: storage.googleapis.com/{bucket}/{object} bucket.storage.googleapis.com/{object} */
		logUploadLocation: Option[String] = None,
	  /** The directory on the VM to store logs. */
		logDir: Option[String] = None,
	  /** The prefix of the resources the taskrunner should use for temporary storage. The supported resource type is: Google Cloud Storage: storage.googleapis.com/{bucket}/{object} bucket.storage.googleapis.com/{object} */
		tempStoragePrefix: Option[String] = None,
	  /** The command to launch the worker harness. */
		harnessCommand: Option[String] = None,
	  /** The file to store the workflow in. */
		workflowFileName: Option[String] = None,
	  /** The file to store preprocessing commands in. */
		commandlinesFileName: Option[String] = None,
	  /** The ID string of the VM. */
		vmId: Option[String] = None,
	  /** The suggested backend language. */
		languageHint: Option[String] = None,
	  /** The streaming worker main class name. */
		streamingWorkerMainClass: Option[String] = None
	)
	
	case class WorkerSettings(
	  /** The base URL for accessing Google Cloud APIs. When workers access Google Cloud APIs, they logically do so via relative URLs. If this field is specified, it supplies the base URL to use for resolving these relative URLs. The normative algorithm used is defined by RFC 1808, "Relative Uniform Resource Locators". If not specified, the default value is "http://www.googleapis.com/" */
		baseUrl: Option[String] = None,
	  /** Whether to send work progress updates to the service. */
		reportingEnabled: Option[Boolean] = None,
	  /** The Cloud Dataflow service path relative to the root URL, for example, "dataflow/v1b3/projects". */
		servicePath: Option[String] = None,
	  /** The Shuffle service path relative to the root URL, for example, "shuffle/v1beta1". */
		shuffleServicePath: Option[String] = None,
	  /** The ID of the worker running this pipeline. */
		workerId: Option[String] = None,
	  /** The prefix of the resources the system should use for temporary storage. The supported resource type is: Google Cloud Storage: storage.googleapis.com/{bucket}/{object} bucket.storage.googleapis.com/{object} */
		tempStoragePrefix: Option[String] = None
	)
	
	case class Disk(
	  /** Size of disk in GB. If zero or unspecified, the service will attempt to choose a reasonable default. */
		sizeGb: Option[Int] = None,
	  /** Disk storage type, as defined by Google Compute Engine. This must be a disk type appropriate to the project and zone in which the workers will run. If unknown or unspecified, the service will attempt to choose a reasonable default. For example, the standard persistent disk type is a resource name typically ending in "pd-standard". If SSD persistent disks are available, the resource name typically ends with "pd-ssd". The actual valid values are defined the Google Compute Engine API, not by the Cloud Dataflow API; consult the Google Compute Engine documentation for more information about determining the set of available disk types for a particular project and zone. Google Compute Engine Disk types are local to a particular project in a particular zone, and so the resource name will typically look something like this: compute.googleapis.com/projects/project-id/zones/zone/diskTypes/pd-standard */
		diskType: Option[String] = None,
	  /** Directory in a VM where disk is mounted. */
		mountPoint: Option[String] = None
	)
	
	object AutoscalingSettings {
		enum AlgorithmEnum extends Enum[AlgorithmEnum] { case AUTOSCALING_ALGORITHM_UNKNOWN, AUTOSCALING_ALGORITHM_NONE, AUTOSCALING_ALGORITHM_BASIC }
	}
	case class AutoscalingSettings(
	  /** The algorithm to use for autoscaling. */
		algorithm: Option[Schema.AutoscalingSettings.AlgorithmEnum] = None,
	  /** The maximum number of workers to cap scaling at. */
		maxNumWorkers: Option[Int] = None
	)
	
	case class SdkHarnessContainerImage(
	  /** A docker container image that resides in Google Container Registry. */
		containerImage: Option[String] = None,
	  /** If true, recommends the Dataflow service to use only one core per SDK container instance with this image. If false (or unset) recommends using more than one core per SDK container instance with this image for efficiency. Note that Dataflow service may choose to override this property if needed. */
		useSingleCorePerContainer: Option[Boolean] = None,
	  /** Environment ID for the Beam runner API proto Environment that corresponds to the current SDK Harness. */
		environmentId: Option[String] = None,
	  /** The set of capabilities enumerated in the above Environment proto. See also [beam_runner_api.proto](https://github.com/apache/beam/blob/master/model/pipeline/src/main/proto/org/apache/beam/model/pipeline/v1/beam_runner_api.proto) */
		capabilities: Option[List[String]] = None
	)
	
	case class DebugOptions(
	  /** Optional. When true, enables the logging of the literal hot key to the user's Cloud Logging. */
		enableHotKeyLogging: Option[Boolean] = None,
	  /** Configuration options for sampling elements from a running pipeline. */
		dataSampling: Option[Schema.DataSamplingConfig] = None
	)
	
	object DataSamplingConfig {
		enum BehaviorsEnum extends Enum[BehaviorsEnum] { case DATA_SAMPLING_BEHAVIOR_UNSPECIFIED, DISABLED, ALWAYS_ON, EXCEPTIONS }
	}
	case class DataSamplingConfig(
	  /** List of given sampling behaviors to enable. For example, specifying behaviors = [ALWAYS_ON] samples in-flight elements but does not sample exceptions. Can be used to specify multiple behaviors like, behaviors = [ALWAYS_ON, EXCEPTIONS] for specifying periodic sampling and exception sampling. If DISABLED is in the list, then sampling will be disabled and ignore the other given behaviors. Ordering does not matter. */
		behaviors: Option[List[Schema.DataSamplingConfig.BehaviorsEnum]] = None
	)
	
	case class Step(
	  /** The kind of step in the Cloud Dataflow job. */
		kind: Option[String] = None,
	  /** The name that identifies the step. This must be unique for each step with respect to all other steps in the Cloud Dataflow job. */
		name: Option[String] = None,
	  /** Named properties associated with the step. Each kind of predefined step has its own required set of properties. Must be provided on Create. Only retrieved with JOB_VIEW_ALL. */
		properties: Option[Map[String, JsValue]] = None
	)
	
	case class JobExecutionInfo(
	  /** A mapping from each stage to the information about that stage. */
		stages: Option[Map[String, Schema.JobExecutionStageInfo]] = None
	)
	
	case class JobExecutionStageInfo(
	  /** The steps associated with the execution stage. Note that stages may have several steps, and that a given step might be run by more than one stage. */
		stepName: Option[List[String]] = None
	)
	
	case class PipelineDescription(
	  /** Description of each transform in the pipeline and collections between them. */
		originalPipelineTransform: Option[List[Schema.TransformSummary]] = None,
	  /** Description of each stage of execution of the pipeline. */
		executionPipelineStage: Option[List[Schema.ExecutionStageSummary]] = None,
	  /** Pipeline level display data. */
		displayData: Option[List[Schema.DisplayData]] = None,
	  /** A hash value of the submitted pipeline portable graph step names if exists. */
		stepNamesHash: Option[String] = None
	)
	
	object TransformSummary {
		enum KindEnum extends Enum[KindEnum] { case UNKNOWN_KIND, PAR_DO_KIND, GROUP_BY_KEY_KIND, FLATTEN_KIND, READ_KIND, WRITE_KIND, CONSTANT_KIND, SINGLETON_KIND, SHUFFLE_KIND }
	}
	case class TransformSummary(
	  /** Type of transform. */
		kind: Option[Schema.TransformSummary.KindEnum] = None,
	  /** SDK generated id of this transform instance. */
		id: Option[String] = None,
	  /** User provided name for this transform instance. */
		name: Option[String] = None,
	  /** Transform-specific display data. */
		displayData: Option[List[Schema.DisplayData]] = None,
	  /** User names for all collection outputs to this transform. */
		outputCollectionName: Option[List[String]] = None,
	  /** User names for all collection inputs to this transform. */
		inputCollectionName: Option[List[String]] = None
	)
	
	case class DisplayData(
	  /** The key identifying the display data. This is intended to be used as a label for the display data when viewed in a dax monitoring system. */
		key: Option[String] = None,
	  /** The namespace for the key. This is usually a class name or programming language namespace (i.e. python module) which defines the display data. This allows a dax monitoring system to specially handle the data and perform custom rendering. */
		namespace: Option[String] = None,
	  /** Contains value if the data is of string type. */
		strValue: Option[String] = None,
	  /** Contains value if the data is of int64 type. */
		int64Value: Option[String] = None,
	  /** Contains value if the data is of float type. */
		floatValue: Option[BigDecimal] = None,
	  /** Contains value if the data is of java class type. */
		javaClassValue: Option[String] = None,
	  /** Contains value if the data is of timestamp type. */
		timestampValue: Option[String] = None,
	  /** Contains value if the data is of duration type. */
		durationValue: Option[String] = None,
	  /** Contains value if the data is of a boolean type. */
		boolValue: Option[Boolean] = None,
	  /** A possible additional shorter value to display. For example a java_class_name_value of com.mypackage.MyDoFn will be stored with MyDoFn as the short_str_value and com.mypackage.MyDoFn as the java_class_name value. short_str_value can be displayed and java_class_name_value will be displayed as a tooltip. */
		shortStrValue: Option[String] = None,
	  /** An optional full URL. */
		url: Option[String] = None,
	  /** An optional label to display in a dax UI for the element. */
		label: Option[String] = None
	)
	
	object ExecutionStageSummary {
		enum KindEnum extends Enum[KindEnum] { case UNKNOWN_KIND, PAR_DO_KIND, GROUP_BY_KEY_KIND, FLATTEN_KIND, READ_KIND, WRITE_KIND, CONSTANT_KIND, SINGLETON_KIND, SHUFFLE_KIND }
	}
	case class ExecutionStageSummary(
	  /** Dataflow service generated name for this stage. */
		name: Option[String] = None,
	  /** Dataflow service generated id for this stage. */
		id: Option[String] = None,
	  /** Type of transform this stage is executing. */
		kind: Option[Schema.ExecutionStageSummary.KindEnum] = None,
	  /** Input sources for this stage. */
		inputSource: Option[List[Schema.StageSource]] = None,
	  /** Output sources for this stage. */
		outputSource: Option[List[Schema.StageSource]] = None,
	  /** Other stages that must complete before this stage can run. */
		prerequisiteStage: Option[List[String]] = None,
	  /** Transforms that comprise this execution stage. */
		componentTransform: Option[List[Schema.ComponentTransform]] = None,
	  /** Collections produced and consumed by component transforms of this stage. */
		componentSource: Option[List[Schema.ComponentSource]] = None
	)
	
	case class StageSource(
	  /** Human-readable name for this source; may be user or system generated. */
		userName: Option[String] = None,
	  /** Dataflow service generated name for this source. */
		name: Option[String] = None,
	  /** User name for the original user transform or collection with which this source is most closely associated. */
		originalTransformOrCollection: Option[String] = None,
	  /** Size of the source, if measurable. */
		sizeBytes: Option[String] = None
	)
	
	case class ComponentTransform(
	  /** Human-readable name for this transform; may be user or system generated. */
		userName: Option[String] = None,
	  /** Dataflow service generated name for this source. */
		name: Option[String] = None,
	  /** User name for the original user transform with which this transform is most closely associated. */
		originalTransform: Option[String] = None
	)
	
	case class ComponentSource(
	  /** Human-readable name for this transform; may be user or system generated. */
		userName: Option[String] = None,
	  /** Dataflow service generated name for this source. */
		name: Option[String] = None,
	  /** User name for the original user transform or collection with which this source is most closely associated. */
		originalTransformOrCollection: Option[String] = None
	)
	
	object ExecutionStageState {
		enum ExecutionStageStateEnum extends Enum[ExecutionStageStateEnum] { case JOB_STATE_UNKNOWN, JOB_STATE_STOPPED, JOB_STATE_RUNNING, JOB_STATE_DONE, JOB_STATE_FAILED, JOB_STATE_CANCELLED, JOB_STATE_UPDATED, JOB_STATE_DRAINING, JOB_STATE_DRAINED, JOB_STATE_PENDING, JOB_STATE_CANCELLING, JOB_STATE_QUEUED, JOB_STATE_RESOURCE_CLEANING_UP }
	}
	case class ExecutionStageState(
	  /** The name of the execution stage. */
		executionStageName: Option[String] = None,
	  /** Executions stage states allow the same set of values as JobState. */
		executionStageState: Option[Schema.ExecutionStageState.ExecutionStageStateEnum] = None,
	  /** The time at which the stage transitioned to this state. */
		currentStateTime: Option[String] = None
	)
	
	case class JobMetadata(
	  /** The SDK version used to run the job. */
		sdkVersion: Option[Schema.SdkVersion] = None,
	  /** Identification of a Spanner source used in the Dataflow job. */
		spannerDetails: Option[List[Schema.SpannerIODetails]] = None,
	  /** Identification of a BigQuery source used in the Dataflow job. */
		bigqueryDetails: Option[List[Schema.BigQueryIODetails]] = None,
	  /** Identification of a Cloud Bigtable source used in the Dataflow job. */
		bigTableDetails: Option[List[Schema.BigTableIODetails]] = None,
	  /** Identification of a Pub/Sub source used in the Dataflow job. */
		pubsubDetails: Option[List[Schema.PubSubIODetails]] = None,
	  /** Identification of a File source used in the Dataflow job. */
		fileDetails: Option[List[Schema.FileIODetails]] = None,
	  /** Identification of a Datastore source used in the Dataflow job. */
		datastoreDetails: Option[List[Schema.DatastoreIODetails]] = None,
	  /** List of display properties to help UI filter jobs. */
		userDisplayProperties: Option[Map[String, String]] = None
	)
	
	object SdkVersion {
		enum SdkSupportStatusEnum extends Enum[SdkSupportStatusEnum] { case UNKNOWN, SUPPORTED, STALE, DEPRECATED, UNSUPPORTED }
	}
	case class SdkVersion(
	  /** The version of the SDK used to run the job. */
		version: Option[String] = None,
	  /** A readable string describing the version of the SDK. */
		versionDisplayName: Option[String] = None,
	  /** The support status for this SDK version. */
		sdkSupportStatus: Option[Schema.SdkVersion.SdkSupportStatusEnum] = None,
	  /** Output only. Known bugs found in this SDK version. */
		bugs: Option[List[Schema.SdkBug]] = None
	)
	
	object SdkBug {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, GENERAL, PERFORMANCE, DATALOSS }
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, NOTICE, WARNING, SEVERE }
	}
	case class SdkBug(
	  /** Output only. Describes the impact of this SDK bug. */
		`type`: Option[Schema.SdkBug.TypeEnum] = None,
	  /** Output only. How severe the SDK bug is. */
		severity: Option[Schema.SdkBug.SeverityEnum] = None,
	  /** Output only. Link to more information on the bug. */
		uri: Option[String] = None
	)
	
	case class SpannerIODetails(
	  /** ProjectId accessed in the connection. */
		projectId: Option[String] = None,
	  /** InstanceId accessed in the connection. */
		instanceId: Option[String] = None,
	  /** DatabaseId accessed in the connection. */
		databaseId: Option[String] = None
	)
	
	case class BigQueryIODetails(
	  /** Table accessed in the connection. */
		table: Option[String] = None,
	  /** Dataset accessed in the connection. */
		dataset: Option[String] = None,
	  /** Project accessed in the connection. */
		projectId: Option[String] = None,
	  /** Query used to access data in the connection. */
		query: Option[String] = None
	)
	
	case class BigTableIODetails(
	  /** ProjectId accessed in the connection. */
		projectId: Option[String] = None,
	  /** InstanceId accessed in the connection. */
		instanceId: Option[String] = None,
	  /** TableId accessed in the connection. */
		tableId: Option[String] = None
	)
	
	case class PubSubIODetails(
	  /** Topic accessed in the connection. */
		topic: Option[String] = None,
	  /** Subscription used in the connection. */
		subscription: Option[String] = None
	)
	
	case class FileIODetails(
	  /** File Pattern used to access files by the connector. */
		filePattern: Option[String] = None
	)
	
	case class DatastoreIODetails(
	  /** Namespace used in the connection. */
		namespace: Option[String] = None,
	  /** ProjectId accessed in the connection. */
		projectId: Option[String] = None
	)
	
	case class RuntimeUpdatableParams(
	  /** The maximum number of workers to cap autoscaling at. This field is currently only supported for Streaming Engine jobs. */
		maxNumWorkers: Option[Int] = None,
	  /** The minimum number of workers to scale down to. This field is currently only supported for Streaming Engine jobs. */
		minNumWorkers: Option[Int] = None,
	  /** Target worker utilization, compared against the aggregate utilization of the worker pool by autoscaler, to determine upscaling and downscaling when absent other constraints such as backlog. For more information, see [Update an existing pipeline](https://cloud.google.com/dataflow/docs/guides/updating-a-pipeline). */
		workerUtilizationHint: Option[BigDecimal] = None
	)
	
	case class ServiceResources(
	  /** Output only. List of Cloud Zones being used by the Dataflow Service for this job. Example: us-central1-c */
		zones: Option[List[String]] = None
	)
	
	case class ListJobsResponse(
	  /** A subset of the requested job information. */
		jobs: Option[List[Schema.Job]] = None,
	  /** Set if there may be more results than fit in this response. */
		nextPageToken: Option[String] = None,
	  /** Zero or more messages describing the [regional endpoints] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints) that failed to respond. */
		failedLocation: Option[List[Schema.FailedLocation]] = None
	)
	
	case class FailedLocation(
	  /** The name of the [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints) that failed to respond. */
		name: Option[String] = None
	)
	
	case class SnapshotJobRequest(
	  /** TTL for the snapshot. */
		ttl: Option[String] = None,
	  /** The location that contains this job. */
		location: Option[String] = None,
	  /** If true, perform snapshots for sources which support this. */
		snapshotSources: Option[Boolean] = None,
	  /** User specified description of the snapshot. Maybe empty. */
		description: Option[String] = None
	)
	
	case class CreateJobFromTemplateRequest(
	  /** Required. The job name to use for the created job. */
		jobName: Option[String] = None,
	  /** Required. A Cloud Storage path to the template from which to create the job. Must be a valid Cloud Storage URL, beginning with `gs://`. */
		gcsPath: Option[String] = None,
	  /** The runtime parameters to pass to the job. */
		parameters: Option[Map[String, String]] = None,
	  /** The runtime environment for the job. */
		environment: Option[Schema.RuntimeEnvironment] = None,
	  /** The [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints) to which to direct the request. */
		location: Option[String] = None
	)
	
	object RuntimeEnvironment {
		enum IpConfigurationEnum extends Enum[IpConfigurationEnum] { case WORKER_IP_UNSPECIFIED, WORKER_IP_PUBLIC, WORKER_IP_PRIVATE }
		enum StreamingModeEnum extends Enum[StreamingModeEnum] { case STREAMING_MODE_UNSPECIFIED, STREAMING_MODE_EXACTLY_ONCE, STREAMING_MODE_AT_LEAST_ONCE }
	}
	case class RuntimeEnvironment(
	  /** Optional. The initial number of Google Compute Engine instances for the job. The default value is 11. */
		numWorkers: Option[Int] = None,
	  /** Optional. The maximum number of Google Compute Engine instances to be made available to your pipeline during execution, from 1 to 1000. The default value is 1. */
		maxWorkers: Option[Int] = None,
	  /** Optional. The Compute Engine [availability zone](https://cloud.google.com/compute/docs/regions-zones/regions-zones) for launching worker instances to run your pipeline. In the future, worker_zone will take precedence. */
		zone: Option[String] = None,
	  /** Optional. The email address of the service account to run the job as. */
		serviceAccountEmail: Option[String] = None,
	  /** Required. The Cloud Storage path to use for temporary files. Must be a valid Cloud Storage URL, beginning with `gs://`. */
		tempLocation: Option[String] = None,
	  /** Optional. Whether to bypass the safety checks for the job's temporary directory. Use with caution. */
		bypassTempDirValidation: Option[Boolean] = None,
	  /** Optional. The machine type to use for the job. Defaults to the value from the template if not specified. */
		machineType: Option[String] = None,
	  /** Optional. Additional experiment flags for the job, specified with the `--experiments` option. */
		additionalExperiments: Option[List[String]] = None,
	  /** Optional. Network to which VMs will be assigned. If empty or unspecified, the service will use the network "default". */
		network: Option[String] = None,
	  /** Optional. Subnetwork to which VMs will be assigned, if desired. You can specify a subnetwork using either a complete URL or an abbreviated path. Expected to be of the form "https://www.googleapis.com/compute/v1/projects/HOST_PROJECT_ID/regions/REGION/subnetworks/SUBNETWORK" or "regions/REGION/subnetworks/SUBNETWORK". If the subnetwork is located in a Shared VPC network, you must use the complete URL. */
		subnetwork: Option[String] = None,
	  /** Optional. Additional user labels to be specified for the job. Keys and values should follow the restrictions specified in the [labeling restrictions](https://cloud.google.com/compute/docs/labeling-resources#restrictions) page. An object containing a list of "key": value pairs. Example: { "name": "wrench", "mass": "1kg", "count": "3" }. */
		additionalUserLabels: Option[Map[String, String]] = None,
	  /** Optional. Name for the Cloud KMS key for the job. Key format is: projects//locations//keyRings//cryptoKeys/ */
		kmsKeyName: Option[String] = None,
	  /** Optional. Configuration for VM IPs. */
		ipConfiguration: Option[Schema.RuntimeEnvironment.IpConfigurationEnum] = None,
	  /** Required. The Compute Engine region (https://cloud.google.com/compute/docs/regions-zones/regions-zones) in which worker processing should occur, e.g. "us-west1". Mutually exclusive with worker_zone. If neither worker_region nor worker_zone is specified, default to the control plane's region. */
		workerRegion: Option[String] = None,
	  /** Optional. The Compute Engine zone (https://cloud.google.com/compute/docs/regions-zones/regions-zones) in which worker processing should occur, e.g. "us-west1-a". Mutually exclusive with worker_region. If neither worker_region nor worker_zone is specified, a zone in the control plane's region is chosen based on available capacity. If both `worker_zone` and `zone` are set, `worker_zone` takes precedence. */
		workerZone: Option[String] = None,
	  /** Optional. Whether to enable Streaming Engine for the job. */
		enableStreamingEngine: Option[Boolean] = None,
	  /** Optional. The disk size, in gigabytes, to use on each remote Compute Engine worker instance. */
		diskSizeGb: Option[Int] = None,
	  /** Optional. Specifies the Streaming Engine message processing guarantees. Reduces cost and latency but might result in duplicate messages committed to storage. Designed to run simple mapping streaming ETL jobs at the lowest cost. For example, Change Data Capture (CDC) to BigQuery is a canonical use case. For more information, see [Set the pipeline streaming mode](https://cloud.google.com/dataflow/docs/guides/streaming-modes). */
		streamingMode: Option[Schema.RuntimeEnvironment.StreamingModeEnum] = None
	)
	
	case class LaunchTemplateParameters(
	  /** Required. The job name to use for the created job. The name must match the regular expression `[a-z]([-a-z0-9]{0,1022}[a-z0-9])?` */
		jobName: Option[String] = None,
	  /** The runtime parameters to pass to the job. */
		parameters: Option[Map[String, String]] = None,
	  /** The runtime environment for the job. */
		environment: Option[Schema.RuntimeEnvironment] = None,
	  /** If set, replace the existing pipeline with the name specified by jobName with this pipeline, preserving state. */
		update: Option[Boolean] = None,
	  /** Only applicable when updating a pipeline. Map of transform name prefixes of the job to be replaced to the corresponding name prefixes of the new job. */
		transformNameMapping: Option[Map[String, String]] = None
	)
	
	case class LaunchTemplateResponse(
	  /** The job that was launched, if the request was not a dry run and the job was successfully launched. */
		job: Option[Schema.Job] = None
	)
	
	object GetTemplateResponse {
		enum TemplateTypeEnum extends Enum[TemplateTypeEnum] { case UNKNOWN, LEGACY, FLEX }
	}
	case class GetTemplateResponse(
	  /** The status of the get template request. Any problems with the request will be indicated in the error_details. */
		status: Option[Schema.Status] = None,
	  /** The template metadata describing the template name, available parameters, etc. */
		metadata: Option[Schema.TemplateMetadata] = None,
	  /** Template Type. */
		templateType: Option[Schema.GetTemplateResponse.TemplateTypeEnum] = None,
	  /** Describes the runtime metadata with SDKInfo and available parameters. */
		runtimeMetadata: Option[Schema.RuntimeMetadata] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class TemplateMetadata(
	  /** Required. The name of the template. */
		name: Option[String] = None,
	  /** Optional. A description of the template. */
		description: Option[String] = None,
	  /** The parameters for the template. */
		parameters: Option[List[Schema.ParameterMetadata]] = None,
	  /** Optional. Indicates if the template is streaming or not. */
		streaming: Option[Boolean] = None,
	  /** Optional. Indicates if the streaming template supports at least once mode. */
		supportsAtLeastOnce: Option[Boolean] = None,
	  /** Optional. Indicates if the streaming template supports exactly once mode. */
		supportsExactlyOnce: Option[Boolean] = None,
	  /** Optional. Indicates the default streaming mode for a streaming template. Only valid if both supports_at_least_once and supports_exactly_once are true. Possible values: UNSPECIFIED, EXACTLY_ONCE and AT_LEAST_ONCE */
		defaultStreamingMode: Option[String] = None
	)
	
	object ParameterMetadata {
		enum ParamTypeEnum extends Enum[ParamTypeEnum] { case DEFAULT, TEXT, GCS_READ_BUCKET, GCS_WRITE_BUCKET, GCS_READ_FILE, GCS_WRITE_FILE, GCS_READ_FOLDER, GCS_WRITE_FOLDER, PUBSUB_TOPIC, PUBSUB_SUBSCRIPTION, BIGQUERY_TABLE, JAVASCRIPT_UDF_FILE, SERVICE_ACCOUNT, MACHINE_TYPE, KMS_KEY_NAME, WORKER_REGION, WORKER_ZONE, BOOLEAN, ENUM, NUMBER, KAFKA_TOPIC, KAFKA_READ_TOPIC, KAFKA_WRITE_TOPIC }
	}
	case class ParameterMetadata(
	  /** Required. The name of the parameter. */
		name: Option[String] = None,
	  /** Required. The label to display for the parameter. */
		label: Option[String] = None,
	  /** Required. The help text to display for the parameter. */
		helpText: Option[String] = None,
	  /** Optional. Whether the parameter is optional. Defaults to false. */
		isOptional: Option[Boolean] = None,
	  /** Optional. Regexes that the parameter must match. */
		regexes: Option[List[String]] = None,
	  /** Optional. The type of the parameter. Used for selecting input picker. */
		paramType: Option[Schema.ParameterMetadata.ParamTypeEnum] = None,
	  /** Optional. Additional metadata for describing this parameter. */
		customMetadata: Option[Map[String, String]] = None,
	  /** Optional. Specifies a group name for this parameter to be rendered under. Group header text will be rendered exactly as specified in this field. Only considered when parent_name is NOT provided. */
		groupName: Option[String] = None,
	  /** Optional. Specifies the name of the parent parameter. Used in conjunction with 'parent_trigger_values' to make this parameter conditional (will only be rendered conditionally). Should be mappable to a ParameterMetadata.name field. */
		parentName: Option[String] = None,
	  /** Optional. The value(s) of the 'parent_name' parameter which will trigger this parameter to be shown. If left empty, ANY non-empty value in parent_name will trigger this parameter to be shown. Only considered when this parameter is conditional (when 'parent_name' has been provided). */
		parentTriggerValues: Option[List[String]] = None,
	  /** Optional. The options shown when ENUM ParameterType is specified. */
		enumOptions: Option[List[Schema.ParameterMetadataEnumOption]] = None,
	  /** Optional. The default values will pre-populate the parameter with the given value from the proto. If default_value is left empty, the parameter will be populated with a default of the relevant type, e.g. false for a boolean. */
		defaultValue: Option[String] = None,
	  /** Optional. Whether the parameter should be hidden in the UI. */
		hiddenUi: Option[Boolean] = None
	)
	
	case class ParameterMetadataEnumOption(
	  /** Required. The value of the enum option. */
		value: Option[String] = None,
	  /** Optional. The label to display for the enum option. */
		label: Option[String] = None,
	  /** Optional. The description to display for the enum option. */
		description: Option[String] = None
	)
	
	case class RuntimeMetadata(
	  /** SDK Info for the template. */
		sdkInfo: Option[Schema.SDKInfo] = None,
	  /** The parameters for the template. */
		parameters: Option[List[Schema.ParameterMetadata]] = None
	)
	
	object SDKInfo {
		enum LanguageEnum extends Enum[LanguageEnum] { case UNKNOWN, JAVA, PYTHON, GO }
	}
	case class SDKInfo(
	  /** Required. The SDK Language. */
		language: Option[Schema.SDKInfo.LanguageEnum] = None,
	  /** Optional. The SDK version. */
		version: Option[String] = None
	)
	
	case class LaunchFlexTemplateRequest(
	  /** Required. Parameter to launch a job form Flex Template. */
		launchParameter: Option[Schema.LaunchFlexTemplateParameter] = None,
	  /** If true, the request is validated but not actually executed. Defaults to false. */
		validateOnly: Option[Boolean] = None
	)
	
	case class LaunchFlexTemplateParameter(
	  /** Required. The job name to use for the created job. For update job request, job name should be same as the existing running job. */
		jobName: Option[String] = None,
	  /** Spec about the container image to launch. */
		containerSpec: Option[Schema.ContainerSpec] = None,
	  /** Cloud Storage path to a file with json serialized ContainerSpec as content. */
		containerSpecGcsPath: Option[String] = None,
	  /** The parameters for FlexTemplate. Ex. {"num_workers":"5"} */
		parameters: Option[Map[String, String]] = None,
	  /** Launch options for this flex template job. This is a common set of options across languages and templates. This should not be used to pass job parameters. */
		launchOptions: Option[Map[String, String]] = None,
	  /** The runtime environment for the FlexTemplate job */
		environment: Option[Schema.FlexTemplateRuntimeEnvironment] = None,
	  /** Set this to true if you are sending a request to update a running streaming job. When set, the job name should be the same as the running job. */
		update: Option[Boolean] = None,
	  /** Use this to pass transform_name_mappings for streaming update jobs. Ex:{"oldTransformName":"newTransformName",...}' */
		transformNameMappings: Option[Map[String, String]] = None
	)
	
	case class ContainerSpec(
	  /** Name of the docker container image. E.g., gcr.io/project/some-image */
		image: Option[String] = None,
	  /** Metadata describing a template including description and validation rules. */
		metadata: Option[Schema.TemplateMetadata] = None,
	  /** Required. SDK info of the Flex Template. */
		sdkInfo: Option[Schema.SDKInfo] = None,
	  /** Default runtime environment for the job. */
		defaultEnvironment: Option[Schema.FlexTemplateRuntimeEnvironment] = None,
	  /** Secret Manager secret id for username to authenticate to private registry. */
		imageRepositoryUsernameSecretId: Option[String] = None,
	  /** Secret Manager secret id for password to authenticate to private registry. */
		imageRepositoryPasswordSecretId: Option[String] = None,
	  /** Cloud Storage path to self-signed certificate of private registry. */
		imageRepositoryCertPath: Option[String] = None
	)
	
	object FlexTemplateRuntimeEnvironment {
		enum IpConfigurationEnum extends Enum[IpConfigurationEnum] { case WORKER_IP_UNSPECIFIED, WORKER_IP_PUBLIC, WORKER_IP_PRIVATE }
		enum FlexrsGoalEnum extends Enum[FlexrsGoalEnum] { case FLEXRS_UNSPECIFIED, FLEXRS_SPEED_OPTIMIZED, FLEXRS_COST_OPTIMIZED }
		enum AutoscalingAlgorithmEnum extends Enum[AutoscalingAlgorithmEnum] { case AUTOSCALING_ALGORITHM_UNKNOWN, AUTOSCALING_ALGORITHM_NONE, AUTOSCALING_ALGORITHM_BASIC }
		enum StreamingModeEnum extends Enum[StreamingModeEnum] { case STREAMING_MODE_UNSPECIFIED, STREAMING_MODE_EXACTLY_ONCE, STREAMING_MODE_AT_LEAST_ONCE }
	}
	case class FlexTemplateRuntimeEnvironment(
	  /** The initial number of Google Compute Engine instances for the job. */
		numWorkers: Option[Int] = None,
	  /** The maximum number of Google Compute Engine instances to be made available to your pipeline during execution, from 1 to 1000. */
		maxWorkers: Option[Int] = None,
	  /** The Compute Engine [availability zone](https://cloud.google.com/compute/docs/regions-zones/regions-zones) for launching worker instances to run your pipeline. In the future, worker_zone will take precedence. */
		zone: Option[String] = None,
	  /** The email address of the service account to run the job as. */
		serviceAccountEmail: Option[String] = None,
	  /** The Cloud Storage path to use for temporary files. Must be a valid Cloud Storage URL, beginning with `gs://`. */
		tempLocation: Option[String] = None,
	  /** The machine type to use for the job. Defaults to the value from the template if not specified. */
		machineType: Option[String] = None,
	  /** Additional experiment flags for the job. */
		additionalExperiments: Option[List[String]] = None,
	  /** Network to which VMs will be assigned. If empty or unspecified, the service will use the network "default". */
		network: Option[String] = None,
	  /** Subnetwork to which VMs will be assigned, if desired. You can specify a subnetwork using either a complete URL or an abbreviated path. Expected to be of the form "https://www.googleapis.com/compute/v1/projects/HOST_PROJECT_ID/regions/REGION/subnetworks/SUBNETWORK" or "regions/REGION/subnetworks/SUBNETWORK". If the subnetwork is located in a Shared VPC network, you must use the complete URL. */
		subnetwork: Option[String] = None,
	  /** Additional user labels to be specified for the job. Keys and values must follow the restrictions specified in the [labeling restrictions](https://cloud.google.com/compute/docs/labeling-resources#restrictions) page. An object containing a list of "key": value pairs. Example: { "name": "wrench", "mass": "1kg", "count": "3" }. */
		additionalUserLabels: Option[Map[String, String]] = None,
	  /** Name for the Cloud KMS key for the job. Key format is: projects//locations//keyRings//cryptoKeys/ */
		kmsKeyName: Option[String] = None,
	  /** Configuration for VM IPs. */
		ipConfiguration: Option[Schema.FlexTemplateRuntimeEnvironment.IpConfigurationEnum] = None,
	  /** The Compute Engine region (https://cloud.google.com/compute/docs/regions-zones/regions-zones) in which worker processing should occur, e.g. "us-west1". Mutually exclusive with worker_zone. If neither worker_region nor worker_zone is specified, default to the control plane's region. */
		workerRegion: Option[String] = None,
	  /** The Compute Engine zone (https://cloud.google.com/compute/docs/regions-zones/regions-zones) in which worker processing should occur, e.g. "us-west1-a". Mutually exclusive with worker_region. If neither worker_region nor worker_zone is specified, a zone in the control plane's region is chosen based on available capacity. If both `worker_zone` and `zone` are set, `worker_zone` takes precedence. */
		workerZone: Option[String] = None,
	  /** Whether to enable Streaming Engine for the job. */
		enableStreamingEngine: Option[Boolean] = None,
	  /** Set FlexRS goal for the job. https://cloud.google.com/dataflow/docs/guides/flexrs */
		flexrsGoal: Option[Schema.FlexTemplateRuntimeEnvironment.FlexrsGoalEnum] = None,
	  /** The Cloud Storage path for staging local files. Must be a valid Cloud Storage URL, beginning with `gs://`. */
		stagingLocation: Option[String] = None,
	  /** Docker registry location of container image to use for the 'worker harness. Default is the container for the version of the SDK. Note this field is only valid for portable pipelines. */
		sdkContainerImage: Option[String] = None,
	  /** Worker disk size, in gigabytes. */
		diskSizeGb: Option[Int] = None,
	  /** The algorithm to use for autoscaling */
		autoscalingAlgorithm: Option[Schema.FlexTemplateRuntimeEnvironment.AutoscalingAlgorithmEnum] = None,
	  /** If true, when processing time is spent almost entirely on garbage collection (GC), saves a heap dump before ending the thread or process. If false, ends the thread or process without saving a heap dump. Does not save a heap dump when the Java Virtual Machine (JVM) has an out of memory error during processing. The location of the heap file is either echoed back to the user, or the user is given the opportunity to download the heap file. */
		dumpHeapOnOom: Option[Boolean] = None,
	  /** Cloud Storage bucket (directory) to upload heap dumps to. Enabling this field implies that `dump_heap_on_oom` is set to true. */
		saveHeapDumpsToGcsPath: Option[String] = None,
	  /** The machine type to use for launching the job. The default is n1-standard-1. */
		launcherMachineType: Option[String] = None,
	  /** If true serial port logging will be enabled for the launcher VM. */
		enableLauncherVmSerialPortLogging: Option[Boolean] = None,
	  /** Optional. Specifies the Streaming Engine message processing guarantees. Reduces cost and latency but might result in duplicate messages committed to storage. Designed to run simple mapping streaming ETL jobs at the lowest cost. For example, Change Data Capture (CDC) to BigQuery is a canonical use case. For more information, see [Set the pipeline streaming mode](https://cloud.google.com/dataflow/docs/guides/streaming-modes). */
		streamingMode: Option[Schema.FlexTemplateRuntimeEnvironment.StreamingModeEnum] = None
	)
	
	case class LaunchFlexTemplateResponse(
	  /** The job that was launched, if the request was not a dry run and the job was successfully launched. */
		job: Option[Schema.Job] = None
	)
	
	case class ListJobMessagesResponse(
	  /** Messages in ascending timestamp order. */
		jobMessages: Option[List[Schema.JobMessage]] = None,
	  /** The token to obtain the next page of results if there are more. */
		nextPageToken: Option[String] = None,
	  /** Autoscaling events in ascending timestamp order. */
		autoscalingEvents: Option[List[Schema.AutoscalingEvent]] = None
	)
	
	object JobMessage {
		enum MessageImportanceEnum extends Enum[MessageImportanceEnum] { case JOB_MESSAGE_IMPORTANCE_UNKNOWN, JOB_MESSAGE_DEBUG, JOB_MESSAGE_DETAILED, JOB_MESSAGE_BASIC, JOB_MESSAGE_WARNING, JOB_MESSAGE_ERROR }
	}
	case class JobMessage(
	  /** Deprecated. */
		id: Option[String] = None,
	  /** The timestamp of the message. */
		time: Option[String] = None,
	  /** The text of the message. */
		messageText: Option[String] = None,
	  /** Importance level of the message. */
		messageImportance: Option[Schema.JobMessage.MessageImportanceEnum] = None
	)
	
	object AutoscalingEvent {
		enum EventTypeEnum extends Enum[EventTypeEnum] { case TYPE_UNKNOWN, TARGET_NUM_WORKERS_CHANGED, CURRENT_NUM_WORKERS_CHANGED, ACTUATION_FAILURE, NO_CHANGE }
	}
	case class AutoscalingEvent(
	  /** The current number of workers the job has. */
		currentNumWorkers: Option[String] = None,
	  /** The target number of workers the worker pool wants to resize to use. */
		targetNumWorkers: Option[String] = None,
	  /** The type of autoscaling event to report. */
		eventType: Option[Schema.AutoscalingEvent.EventTypeEnum] = None,
	  /** A message describing why the system decided to adjust the current number of workers, why it failed, or why the system decided to not make any changes to the number of workers. */
		description: Option[Schema.StructuredMessage] = None,
	  /** The time this event was emitted to indicate a new target or current num_workers value. */
		time: Option[String] = None,
	  /** A short and friendly name for the worker pool this event refers to. */
		workerPool: Option[String] = None
	)
	
	case class StructuredMessage(
	  /** Human-readable version of message. */
		messageText: Option[String] = None,
	  /** Identifier for this message type. Used by external systems to internationalize or personalize message. */
		messageKey: Option[String] = None,
	  /** The structured data associated with this message. */
		parameters: Option[List[Schema.Parameter]] = None
	)
	
	case class Parameter(
	  /** Key or name for this parameter. */
		key: Option[String] = None,
	  /** Value for this parameter. */
		value: Option[JsValue] = None
	)
	
	case class JobMetrics(
	  /** Timestamp as of which metric values are current. */
		metricTime: Option[String] = None,
	  /** All metrics for this job. */
		metrics: Option[List[Schema.MetricUpdate]] = None
	)
	
	case class MetricUpdate(
	  /** Name of the metric. */
		name: Option[Schema.MetricStructuredName] = None,
	  /** Metric aggregation kind. The possible metric aggregation kinds are "Sum", "Max", "Min", "Mean", "Set", "And", "Or", and "Distribution". The specified aggregation kind is case-insensitive. If omitted, this is not an aggregated value but instead a single metric sample value. */
		kind: Option[String] = None,
	  /** True if this metric is reported as the total cumulative aggregate value accumulated since the worker started working on this WorkItem. By default this is false, indicating that this metric is reported as a delta that is not associated with any WorkItem. */
		cumulative: Option[Boolean] = None,
	  /** Worker-computed aggregate value for aggregation kinds "Sum", "Max", "Min", "And", and "Or". The possible value types are Long, Double, and Boolean. */
		scalar: Option[JsValue] = None,
	  /** Worker-computed aggregate value for the "Mean" aggregation kind. This holds the sum of the aggregated values and is used in combination with mean_count below to obtain the actual mean aggregate value. The only possible value types are Long and Double. */
		meanSum: Option[JsValue] = None,
	  /** Worker-computed aggregate value for the "Mean" aggregation kind. This holds the count of the aggregated values and is used in combination with mean_sum above to obtain the actual mean aggregate value. The only possible value type is Long. */
		meanCount: Option[JsValue] = None,
	  /** Worker-computed aggregate value for the "Set" aggregation kind. The only possible value type is a list of Values whose type can be Long, Double, or String, according to the metric's type. All Values in the list must be of the same type. */
		set: Option[JsValue] = None,
	  /** A struct value describing properties of a distribution of numeric values. */
		distribution: Option[JsValue] = None,
	  /** A struct value describing properties of a Gauge. Metrics of gauge type show the value of a metric across time, and is aggregated based on the newest value. */
		gauge: Option[JsValue] = None,
	  /** Worker-computed aggregate value for internal use by the Dataflow service. */
		internal: Option[JsValue] = None,
	  /** Timestamp associated with the metric value. Optional when workers are reporting work progress; it will be filled in responses from the metrics API. */
		updateTime: Option[String] = None
	)
	
	case class MetricStructuredName(
	  /** Origin (namespace) of metric name. May be blank for user-define metrics; will be "dataflow" for metrics defined by the Dataflow service or SDK. */
		origin: Option[String] = None,
	  /** Worker-defined metric name. */
		name: Option[String] = None,
	  /** Zero or more labeled fields which identify the part of the job this metric is associated with, such as the name of a step or collection. For example, built-in counters associated with steps will have context['step'] = . Counters associated with PCollections in the SDK will have context['pcollection'] = . */
		context: Option[Map[String, String]] = None
	)
	
	case class JobExecutionDetails(
	  /** The stages of the job execution. */
		stages: Option[List[Schema.StageSummary]] = None,
	  /** If present, this response does not contain all requested tasks. To obtain the next page of results, repeat the request with page_token set to this value. */
		nextPageToken: Option[String] = None
	)
	
	object StageSummary {
		enum StateEnum extends Enum[StateEnum] { case EXECUTION_STATE_UNKNOWN, EXECUTION_STATE_NOT_STARTED, EXECUTION_STATE_RUNNING, EXECUTION_STATE_SUCCEEDED, EXECUTION_STATE_FAILED, EXECUTION_STATE_CANCELLED }
	}
	case class StageSummary(
	  /** ID of this stage */
		stageId: Option[String] = None,
	  /** State of this stage. */
		state: Option[Schema.StageSummary.StateEnum] = None,
	  /** Start time of this stage. */
		startTime: Option[String] = None,
	  /** End time of this stage. If the work item is completed, this is the actual end time of the stage. Otherwise, it is the predicted end time. */
		endTime: Option[String] = None,
	  /** Progress for this stage. Only applicable to Batch jobs. */
		progress: Option[Schema.ProgressTimeseries] = None,
	  /** Metrics for this stage. */
		metrics: Option[List[Schema.MetricUpdate]] = None,
	  /** Straggler summary for this stage. */
		stragglerSummary: Option[Schema.StragglerSummary] = None
	)
	
	case class ProgressTimeseries(
	  /** The current progress of the component, in the range [0,1]. */
		currentProgress: Option[BigDecimal] = None,
	  /** History of progress for the component. Points are sorted by time. */
		dataPoints: Option[List[Schema.Point]] = None
	)
	
	case class Point(
	  /** The timestamp of the point. */
		time: Option[String] = None,
	  /** The value of the point. */
		value: Option[BigDecimal] = None
	)
	
	case class StragglerSummary(
	  /** The total count of stragglers. */
		totalStragglerCount: Option[String] = None,
	  /** Aggregated counts of straggler causes, keyed by the string representation of the StragglerCause enum. */
		stragglerCauseCount: Option[Map[String, String]] = None,
	  /** The most recent stragglers. */
		recentStragglers: Option[List[Schema.Straggler]] = None
	)
	
	case class Straggler(
	  /** Batch straggler identification and debugging information. */
		batchStraggler: Option[Schema.StragglerInfo] = None,
	  /** Streaming straggler identification and debugging information. */
		streamingStraggler: Option[Schema.StreamingStragglerInfo] = None
	)
	
	case class StragglerInfo(
	  /** The time when the work item attempt became a straggler. */
		startTime: Option[String] = None,
	  /** The straggler causes, keyed by the string representation of the StragglerCause enum and contains specialized debugging information for each straggler cause. */
		causes: Option[Map[String, Schema.StragglerDebuggingInfo]] = None
	)
	
	case class StragglerDebuggingInfo(
	  /** Hot key debugging details. */
		hotKey: Option[Schema.HotKeyDebuggingInfo] = None
	)
	
	case class HotKeyDebuggingInfo(
	  /** Debugging information for each detected hot key. Keyed by a hash of the key. */
		detectedHotKeys: Option[Map[String, Schema.HotKeyInfo]] = None
	)
	
	case class HotKeyInfo(
	  /** The age of the hot key measured from when it was first detected. */
		hotKeyAge: Option[String] = None,
	  /** A detected hot key that is causing limited parallelism. This field will be populated only if the following flag is set to true: "--enable_hot_key_logging". */
		key: Option[String] = None,
	  /** If true, then the above key is truncated and cannot be deserialized. This occurs if the key above is populated and the key size is >5MB. */
		keyTruncated: Option[Boolean] = None
	)
	
	case class StreamingStragglerInfo(
	  /** Start time of this straggler. */
		startTime: Option[String] = None,
	  /** End time of this straggler. */
		endTime: Option[String] = None,
	  /** Name of the worker where the straggler was detected. */
		workerName: Option[String] = None,
	  /** The event-time watermark lag at the time of the straggler detection. */
		dataWatermarkLag: Option[String] = None,
	  /** The system watermark lag at the time of the straggler detection. */
		systemWatermarkLag: Option[String] = None
	)
	
	case class StageExecutionDetails(
	  /** Workers that have done work on the stage. */
		workers: Option[List[Schema.WorkerDetails]] = None,
	  /** If present, this response does not contain all requested tasks. To obtain the next page of results, repeat the request with page_token set to this value. */
		nextPageToken: Option[String] = None
	)
	
	case class WorkerDetails(
	  /** Name of this worker */
		workerName: Option[String] = None,
	  /** Work items processed by this worker, sorted by time. */
		workItems: Option[List[Schema.WorkItemDetails]] = None
	)
	
	object WorkItemDetails {
		enum StateEnum extends Enum[StateEnum] { case EXECUTION_STATE_UNKNOWN, EXECUTION_STATE_NOT_STARTED, EXECUTION_STATE_RUNNING, EXECUTION_STATE_SUCCEEDED, EXECUTION_STATE_FAILED, EXECUTION_STATE_CANCELLED }
	}
	case class WorkItemDetails(
	  /** Name of this work item. */
		taskId: Option[String] = None,
	  /** Attempt ID of this work item */
		attemptId: Option[String] = None,
	  /** Start time of this work item attempt. */
		startTime: Option[String] = None,
	  /** End time of this work item attempt. If the work item is completed, this is the actual end time of the work item. Otherwise, it is the predicted end time. */
		endTime: Option[String] = None,
	  /** State of this work item. */
		state: Option[Schema.WorkItemDetails.StateEnum] = None,
	  /** Progress of this work item. */
		progress: Option[Schema.ProgressTimeseries] = None,
	  /** Metrics for this work item. */
		metrics: Option[List[Schema.MetricUpdate]] = None,
	  /** Information about straggler detections for this work item. */
		stragglerInfo: Option[Schema.StragglerInfo] = None
	)
	
	case class ReportWorkItemStatusRequest(
	  /** The ID of the worker reporting the WorkItem status. If this does not match the ID of the worker which the Dataflow service believes currently has the lease on the WorkItem, the report will be dropped (with an error response). */
		workerId: Option[String] = None,
	  /** The order is unimportant, except that the order of the WorkItemServiceState messages in the ReportWorkItemStatusResponse corresponds to the order of WorkItemStatus messages here. */
		workItemStatuses: Option[List[Schema.WorkItemStatus]] = None,
	  /** The current timestamp at the worker. */
		currentWorkerTime: Option[String] = None,
	  /** The [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints) that contains the WorkItem's job. */
		location: Option[String] = None,
	  /** Untranslated bag-of-bytes WorkProgressUpdateRequest from UnifiedWorker. */
		unifiedWorkerRequest: Option[Map[String, JsValue]] = None,
	  /** Optional. The project number of the project which owns the WorkItem's job. */
		projectNumber: Option[String] = None
	)
	
	case class WorkItemStatus(
	  /** Identifies the WorkItem. */
		workItemId: Option[String] = None,
	  /** The report index. When a WorkItem is leased, the lease will contain an initial report index. When a WorkItem's status is reported to the system, the report should be sent with that report index, and the response will contain the index the worker should use for the next report. Reports received with unexpected index values will be rejected by the service. In order to preserve idempotency, the worker should not alter the contents of a report, even if the worker must submit the same report multiple times before getting back a response. The worker should not submit a subsequent report until the response for the previous report had been received from the service. */
		reportIndex: Option[String] = None,
	  /** Amount of time the worker requests for its lease. */
		requestedLeaseDuration: Option[String] = None,
	  /** True if the WorkItem was completed (successfully or unsuccessfully). */
		completed: Option[Boolean] = None,
	  /** Specifies errors which occurred during processing. If errors are provided, and completed = true, then the WorkItem is considered to have failed. */
		errors: Option[List[Schema.Status]] = None,
	  /** Worker output counters for this WorkItem. */
		counterUpdates: Option[List[Schema.CounterUpdate]] = None,
	  /** DEPRECATED in favor of counter_updates. */
		metricUpdates: Option[List[Schema.MetricUpdate]] = None,
	  /** The worker's progress through this WorkItem. */
		reportedProgress: Option[Schema.ApproximateReportedProgress] = None,
	  /** A worker may split an active map task in two parts, "primary" and "residual", continuing to process the primary part and returning the residual part into the pool of available work. This event is called a "dynamic split" and is critical to the dynamic work rebalancing feature. The two obtained sub-tasks are called "parts" of the split. The parts, if concatenated, must represent the same input as would be read by the current task if the split did not happen. The exact way in which the original task is decomposed into the two parts is specified either as a position demarcating them (stop_position), or explicitly as two DerivedSources, if this task consumes a user-defined source type (dynamic_source_split). The "current" task is adjusted as a result of the split: after a task with range [A, B) sends a stop_position update at C, its range is considered to be [A, C), e.g.: &#42; Progress should be interpreted relative to the new range, e.g. "75% completed" means "75% of [A, C) completed" &#42; The worker should interpret proposed_stop_position relative to the new range, e.g. "split at 68%" should be interpreted as "split at 68% of [A, C)". &#42; If the worker chooses to split again using stop_position, only stop_positions in [A, C) will be accepted. &#42; Etc. dynamic_source_split has similar semantics: e.g., if a task with source S splits using dynamic_source_split into {P, R} (where P and R must be together equivalent to S), then subsequent progress and proposed_stop_position should be interpreted relative to P, and in a potential subsequent dynamic_source_split into {P', R'}, P' and R' must be together equivalent to P, etc. */
		stopPosition: Option[Schema.Position] = None,
	  /** See documentation of stop_position. */
		dynamicSourceSplit: Option[Schema.DynamicSourceSplit] = None,
	  /** If the work item represented a SourceOperationRequest, and the work is completed, contains the result of the operation. */
		sourceOperationResponse: Option[Schema.SourceOperationResponse] = None,
	  /** Total time the worker spent being throttled by external systems. */
		totalThrottlerWaitTimeSeconds: Option[BigDecimal] = None,
	  /** DEPRECATED in favor of dynamic_source_split. */
		sourceFork: Option[Schema.SourceFork] = None,
	  /** DEPRECATED in favor of reported_progress. */
		progress: Option[Schema.ApproximateProgress] = None
	)
	
	case class CounterUpdate(
	  /** Counter name and aggregation type. */
		nameAndKind: Option[Schema.NameAndKind] = None,
	  /** The service-generated short identifier for this counter. The short_id -> (name, metadata) mapping is constant for the lifetime of a job. */
		shortId: Option[String] = None,
	  /** Counter structured name and metadata. */
		structuredNameAndMetadata: Option[Schema.CounterStructuredNameAndMetadata] = None,
	  /** True if this counter is reported as the total cumulative aggregate value accumulated since the worker started working on this WorkItem. By default this is false, indicating that this counter is reported as a delta. */
		cumulative: Option[Boolean] = None,
	  /** Integer value for Sum, Max, Min. */
		integer: Option[Schema.SplitInt64] = None,
	  /** Floating point value for Sum, Max, Min. */
		floatingPoint: Option[BigDecimal] = None,
	  /** Boolean value for And, Or. */
		`boolean`: Option[Boolean] = None,
	  /** Integer mean aggregation value for Mean. */
		integerMean: Option[Schema.IntegerMean] = None,
	  /** Floating point mean aggregation value for Mean. */
		floatingPointMean: Option[Schema.FloatingPointMean] = None,
	  /** List of integers, for Set. */
		integerList: Option[Schema.IntegerList] = None,
	  /** List of floating point numbers, for Set. */
		floatingPointList: Option[Schema.FloatingPointList] = None,
	  /** List of strings, for Set. */
		stringList: Option[Schema.StringList] = None,
	  /** Distribution data */
		distribution: Option[Schema.DistributionUpdate] = None,
	  /** Value for internally-defined counters used by the Dataflow service. */
		internal: Option[JsValue] = None,
	  /** Gauge data */
		integerGauge: Option[Schema.IntegerGauge] = None
	)
	
	object NameAndKind {
		enum KindEnum extends Enum[KindEnum] { case INVALID, SUM, MAX, MIN, MEAN, OR, AND, SET, DISTRIBUTION, LATEST_VALUE }
	}
	case class NameAndKind(
	  /** Name of the counter. */
		name: Option[String] = None,
	  /** Counter aggregation kind. */
		kind: Option[Schema.NameAndKind.KindEnum] = None
	)
	
	case class CounterStructuredNameAndMetadata(
	  /** Structured name of the counter. */
		name: Option[Schema.CounterStructuredName] = None,
	  /** Metadata associated with a counter */
		metadata: Option[Schema.CounterMetadata] = None
	)
	
	object CounterStructuredName {
		enum OriginEnum extends Enum[OriginEnum] { case SYSTEM, USER }
		enum PortionEnum extends Enum[PortionEnum] { case ALL, KEY, VALUE }
	}
	case class CounterStructuredName(
	  /** Counter name. Not necessarily globally-unique, but unique within the context of the other fields. Required. */
		name: Option[String] = None,
	  /** One of the standard Origins defined above. */
		origin: Option[Schema.CounterStructuredName.OriginEnum] = None,
	  /** A string containing a more specific namespace of the counter's origin. */
		originNamespace: Option[String] = None,
	  /** System generated name of the original step in the user's graph, before optimization. */
		originalStepName: Option[String] = None,
	  /** Name of the optimized step being executed by the workers. */
		componentStepName: Option[String] = None,
	  /** Name of the stage. An execution step contains multiple component steps. */
		executionStepName: Option[String] = None,
	  /** ID of a particular worker. */
		workerId: Option[String] = None,
	  /** Portion of this counter, either key or value. */
		portion: Option[Schema.CounterStructuredName.PortionEnum] = None,
	  /** Index of an input collection that's being read from/written to as a side input. The index identifies a step's side inputs starting by 1 (e.g. the first side input has input_index 1, the third has input_index 3). Side inputs are identified by a pair of (original_step_name, input_index). This field helps uniquely identify them. */
		inputIndex: Option[Int] = None,
	  /** The step name requesting an operation, such as GBK. I.e. the ParDo causing a read/write from shuffle to occur, or a read from side inputs. */
		originalRequestingStepName: Option[String] = None
	)
	
	object CounterMetadata {
		enum KindEnum extends Enum[KindEnum] { case INVALID, SUM, MAX, MIN, MEAN, OR, AND, SET, DISTRIBUTION, LATEST_VALUE }
		enum StandardUnitsEnum extends Enum[StandardUnitsEnum] { case BYTES, BYTES_PER_SEC, MILLISECONDS, MICROSECONDS, NANOSECONDS, TIMESTAMP_MSEC, TIMESTAMP_USEC, TIMESTAMP_NSEC }
	}
	case class CounterMetadata(
	  /** Counter aggregation kind. */
		kind: Option[Schema.CounterMetadata.KindEnum] = None,
	  /** Human-readable description of the counter semantics. */
		description: Option[String] = None,
	  /** System defined Units, see above enum. */
		standardUnits: Option[Schema.CounterMetadata.StandardUnitsEnum] = None,
	  /** A string referring to the unit type. */
		otherUnits: Option[String] = None
	)
	
	case class SplitInt64(
	  /** The low order bits: n & 0xffffffff. */
		lowBits: Option[Int] = None,
	  /** The high order bits, including the sign: n >> 32. */
		highBits: Option[Int] = None
	)
	
	case class IntegerMean(
	  /** The sum of all values being aggregated. */
		sum: Option[Schema.SplitInt64] = None,
	  /** The number of values being aggregated. */
		count: Option[Schema.SplitInt64] = None
	)
	
	case class FloatingPointMean(
	  /** The sum of all values being aggregated. */
		sum: Option[BigDecimal] = None,
	  /** The number of values being aggregated. */
		count: Option[Schema.SplitInt64] = None
	)
	
	case class IntegerList(
	  /** Elements of the list. */
		elements: Option[List[Schema.SplitInt64]] = None
	)
	
	case class FloatingPointList(
	  /** Elements of the list. */
		elements: Option[List[BigDecimal]] = None
	)
	
	case class StringList(
	  /** Elements of the list. */
		elements: Option[List[String]] = None
	)
	
	case class DistributionUpdate(
	  /** The minimum value present in the distribution. */
		min: Option[Schema.SplitInt64] = None,
	  /** The maximum value present in the distribution. */
		max: Option[Schema.SplitInt64] = None,
	  /** The count of the number of elements present in the distribution. */
		count: Option[Schema.SplitInt64] = None,
	  /** Use an int64 since we'd prefer the added precision. If overflow is a common problem we can detect it and use an additional int64 or a double. */
		sum: Option[Schema.SplitInt64] = None,
	  /** Use a double since the sum of squares is likely to overflow int64. */
		sumOfSquares: Option[BigDecimal] = None,
	  /** (Optional) Histogram of value counts for the distribution. */
		histogram: Option[Schema.Histogram] = None
	)
	
	case class Histogram(
	  /** Counts of values in each bucket. For efficiency, prefix and trailing buckets with count = 0 are elided. Buckets can store the full range of values of an unsigned long, with ULLONG_MAX falling into the 59th bucket with range [1e19, 2e19). */
		bucketCounts: Option[List[String]] = None,
	  /** Starting index of first stored bucket. The non-inclusive upper-bound of the ith bucket is given by: pow(10,(i-first_bucket_offset)/3) &#42; (1,2,5)[(i-first_bucket_offset)%3] */
		firstBucketOffset: Option[Int] = None
	)
	
	case class IntegerGauge(
	  /** The value of the variable represented by this gauge. */
		value: Option[Schema.SplitInt64] = None,
	  /** The time at which this value was measured. Measured as msecs from epoch. */
		timestamp: Option[String] = None
	)
	
	case class ApproximateReportedProgress(
	  /** A Position within the work to represent a progress. */
		position: Option[Schema.Position] = None,
	  /** Completion as fraction of the input consumed, from 0.0 (beginning, nothing consumed), to 1.0 (end of the input, entire input consumed). */
		fractionConsumed: Option[BigDecimal] = None,
	  /** Total amount of parallelism in the input of this task that remains, (i.e. can be delegated to this task and any new tasks via dynamic splitting). Always at least 1 for non-finished work items and 0 for finished. "Amount of parallelism" refers to how many non-empty parts of the input can be read in parallel. This does not necessarily equal number of records. An input that can be read in parallel down to the individual records is called "perfectly splittable". An example of non-perfectly parallelizable input is a block-compressed file format where a block of records has to be read as a whole, but different blocks can be read in parallel. Examples: &#42; If we are processing record #30 (starting at 1) out of 50 in a perfectly splittable 50-record input, this value should be 21 (20 remaining + 1 current). &#42; If we are reading through block 3 in a block-compressed file consisting of 5 blocks, this value should be 3 (since blocks 4 and 5 can be processed in parallel by new tasks via dynamic splitting and the current task remains processing block 3). &#42; If we are reading through the last block in a block-compressed file, or reading or processing the last record in a perfectly splittable input, this value should be 1, because apart from the current task, no additional remainder can be split off. */
		remainingParallelism: Option[Schema.ReportedParallelism] = None,
	  /** Total amount of parallelism in the portion of input of this task that has already been consumed and is no longer active. In the first two examples above (see remaining_parallelism), the value should be 29 or 2 respectively. The sum of remaining_parallelism and consumed_parallelism should equal the total amount of parallelism in this work item. If specified, must be finite. */
		consumedParallelism: Option[Schema.ReportedParallelism] = None
	)
	
	case class Position(
	  /** Position is past all other positions. Also useful for the end position of an unbounded range. */
		end: Option[Boolean] = None,
	  /** Position is a string key, ordered lexicographically. */
		key: Option[String] = None,
	  /** Position is a byte offset. */
		byteOffset: Option[String] = None,
	  /** Position is a record index. */
		recordIndex: Option[String] = None,
	  /** CloudPosition is a base64 encoded BatchShufflePosition (with FIXED sharding). */
		shufflePosition: Option[String] = None,
	  /** CloudPosition is a concat position. */
		concatPosition: Option[Schema.ConcatPosition] = None
	)
	
	case class ConcatPosition(
	  /** Index of the inner source. */
		index: Option[Int] = None,
	  /** Position within the inner source. */
		position: Option[Schema.Position] = None
	)
	
	case class ReportedParallelism(
	  /** Specifies whether the parallelism is infinite. If true, "value" is ignored. Infinite parallelism means the service will assume that the work item can always be split into more non-empty work items by dynamic splitting. This is a work-around for lack of support for infinity by the current JSON-based Java RPC stack. */
		isInfinite: Option[Boolean] = None,
	  /** Specifies the level of parallelism in case it is finite. */
		value: Option[BigDecimal] = None
	)
	
	case class DynamicSourceSplit(
	  /** Primary part (continued to be processed by worker). Specified relative to the previously-current source. Becomes current. */
		primary: Option[Schema.DerivedSource] = None,
	  /** Residual part (returned to the pool of work). Specified relative to the previously-current source. */
		residual: Option[Schema.DerivedSource] = None
	)
	
	object DerivedSource {
		enum DerivationModeEnum extends Enum[DerivationModeEnum] { case SOURCE_DERIVATION_MODE_UNKNOWN, SOURCE_DERIVATION_MODE_INDEPENDENT, SOURCE_DERIVATION_MODE_CHILD_OF_CURRENT, SOURCE_DERIVATION_MODE_SIBLING_OF_CURRENT }
	}
	case class DerivedSource(
	  /** Specification of the source. */
		source: Option[Schema.Source] = None,
	  /** What source to base the produced source on (if any). */
		derivationMode: Option[Schema.DerivedSource.DerivationModeEnum] = None
	)
	
	case class Source(
	  /** The source to read from, plus its parameters. */
		spec: Option[Map[String, JsValue]] = None,
	  /** The codec to use to decode data read from the source. */
		codec: Option[Map[String, JsValue]] = None,
	  /** While splitting, sources may specify the produced bundles as differences against another source, in order to save backend-side memory and allow bigger jobs. For details, see SourceSplitRequest. To support this use case, the full set of parameters of the source is logically obtained by taking the latest explicitly specified value of each parameter in the order: base_specs (later items win), spec (overrides anything in base_specs). */
		baseSpecs: Option[List[Map[String, JsValue]]] = None,
	  /** Optionally, metadata for this source can be supplied right away, avoiding a SourceGetMetadataOperation roundtrip (see SourceOperationRequest). This field is meaningful only in the Source objects populated by the user (e.g. when filling in a DerivedSource). Source objects supplied by the framework to the user don't have this field populated. */
		metadata: Option[Schema.SourceMetadata] = None,
	  /** Setting this value to true hints to the framework that the source doesn't need splitting, and using SourceSplitRequest on it would yield SOURCE_SPLIT_OUTCOME_USE_CURRENT. E.g. a file splitter may set this to true when splitting a single file into a set of byte ranges of appropriate size, and set this to false when splitting a filepattern into individual files. However, for efficiency, a file splitter may decide to produce file subranges directly from the filepattern to avoid a splitting round-trip. See SourceSplitRequest for an overview of the splitting process. This field is meaningful only in the Source objects populated by the user (e.g. when filling in a DerivedSource). Source objects supplied by the framework to the user don't have this field populated. */
		doesNotNeedSplitting: Option[Boolean] = None
	)
	
	case class SourceMetadata(
	  /** Whether this source is known to produce key/value pairs with the (encoded) keys in lexicographically sorted order. */
		producesSortedKeys: Option[Boolean] = None,
	  /** Specifies that the size of this source is known to be infinite (this is a streaming source). */
		infinite: Option[Boolean] = None,
	  /** An estimate of the total size (in bytes) of the data that would be read from this source. This estimate is in terms of external storage size, before any decompression or other processing done by the reader. */
		estimatedSizeBytes: Option[String] = None
	)
	
	case class SourceOperationResponse(
	  /** A response to a request to split a source. */
		split: Option[Schema.SourceSplitResponse] = None,
	  /** A response to a request to get metadata about a source. */
		getMetadata: Option[Schema.SourceGetMetadataResponse] = None
	)
	
	object SourceSplitResponse {
		enum OutcomeEnum extends Enum[OutcomeEnum] { case SOURCE_SPLIT_OUTCOME_UNKNOWN, SOURCE_SPLIT_OUTCOME_USE_CURRENT, SOURCE_SPLIT_OUTCOME_SPLITTING_HAPPENED }
	}
	case class SourceSplitResponse(
	  /** Indicates whether splitting happened and produced a list of bundles. If this is USE_CURRENT_SOURCE_AS_IS, the current source should be processed "as is" without splitting. "bundles" is ignored in this case. If this is SPLITTING_HAPPENED, then "bundles" contains a list of bundles into which the source was split. */
		outcome: Option[Schema.SourceSplitResponse.OutcomeEnum] = None,
	  /** If outcome is SPLITTING_HAPPENED, then this is a list of bundles into which the source was split. Otherwise this field is ignored. This list can be empty, which means the source represents an empty input. */
		bundles: Option[List[Schema.DerivedSource]] = None,
	  /** DEPRECATED in favor of bundles. */
		shards: Option[List[Schema.SourceSplitShard]] = None
	)
	
	object SourceSplitShard {
		enum DerivationModeEnum extends Enum[DerivationModeEnum] { case SOURCE_DERIVATION_MODE_UNKNOWN, SOURCE_DERIVATION_MODE_INDEPENDENT, SOURCE_DERIVATION_MODE_CHILD_OF_CURRENT, SOURCE_DERIVATION_MODE_SIBLING_OF_CURRENT }
	}
	case class SourceSplitShard(
	  /** DEPRECATED */
		source: Option[Schema.Source] = None,
	  /** DEPRECATED */
		derivationMode: Option[Schema.SourceSplitShard.DerivationModeEnum] = None
	)
	
	case class SourceGetMetadataResponse(
	  /** The computed metadata. */
		metadata: Option[Schema.SourceMetadata] = None
	)
	
	case class SourceFork(
	  /** DEPRECATED */
		primary: Option[Schema.SourceSplitShard] = None,
	  /** DEPRECATED */
		residual: Option[Schema.SourceSplitShard] = None,
	  /** DEPRECATED */
		primarySource: Option[Schema.DerivedSource] = None,
	  /** DEPRECATED */
		residualSource: Option[Schema.DerivedSource] = None
	)
	
	case class ApproximateProgress(
	  /** Obsolete. */
		position: Option[Schema.Position] = None,
	  /** Obsolete. */
		percentComplete: Option[BigDecimal] = None,
	  /** Obsolete. */
		remainingTime: Option[String] = None
	)
	
	case class ReportWorkItemStatusResponse(
	  /** A set of messages indicating the service-side state for each WorkItem whose status was reported, in the same order as the WorkItemStatus messages in the ReportWorkItemStatusRequest which resulting in this response. */
		workItemServiceStates: Option[List[Schema.WorkItemServiceState]] = None,
	  /** Untranslated bag-of-bytes WorkProgressUpdateResponse for UnifiedWorker. */
		unifiedWorkerResponse: Option[Map[String, JsValue]] = None
	)
	
	case class WorkItemServiceState(
	  /** The progress point in the WorkItem where the Dataflow service suggests that the worker truncate the task. */
		splitRequest: Option[Schema.ApproximateSplitRequest] = None,
	  /** Time at which the current lease will expire. */
		leaseExpireTime: Option[String] = None,
	  /** New recommended reporting interval. */
		reportStatusInterval: Option[String] = None,
	  /** Other data returned by the service, specific to the particular worker harness. */
		harnessData: Option[Map[String, JsValue]] = None,
	  /** The index value to use for the next report sent by the worker. Note: If the report call fails for whatever reason, the worker should reuse this index for subsequent report attempts. */
		nextReportIndex: Option[String] = None,
	  /** The short ids that workers should use in subsequent metric updates. Workers should strive to use short ids whenever possible, but it is ok to request the short_id again if a worker lost track of it (e.g. if the worker is recovering from a crash). NOTE: it is possible that the response may have short ids for a subset of the metrics. */
		metricShortId: Option[List[Schema.MetricShortId]] = None,
	  /** A hot key is a symptom of poor data distribution in which there are enough elements mapped to a single key to impact pipeline performance. When present, this field includes metadata associated with any hot key. */
		hotKeyDetection: Option[Schema.HotKeyDetection] = None,
	  /** If set, a request to complete the work item with the given status. This will not be set to OK, unless supported by the specific kind of WorkItem. It can be used for the backend to indicate a WorkItem must terminate, e.g., for aborting work. */
		completeWorkStatus: Option[Schema.Status] = None,
	  /** Obsolete, always empty. */
		suggestedStopPosition: Option[Schema.Position] = None,
	  /** DEPRECATED in favor of split_request. */
		suggestedStopPoint: Option[Schema.ApproximateProgress] = None
	)
	
	case class ApproximateSplitRequest(
	  /** A Position at which to split the work item. */
		position: Option[Schema.Position] = None,
	  /** A fraction at which to split the work item, from 0.0 (beginning of the input) to 1.0 (end of the input). */
		fractionConsumed: Option[BigDecimal] = None,
	  /** The fraction of the remainder of work to split the work item at, from 0.0 (split at the current position) to 1.0 (end of the input). */
		fractionOfRemainder: Option[BigDecimal] = None
	)
	
	case class MetricShortId(
	  /** The index of the corresponding metric in the ReportWorkItemStatusRequest. Required. */
		metricIndex: Option[Int] = None,
	  /** The service-generated short identifier for the metric. */
		shortId: Option[String] = None
	)
	
	case class HotKeyDetection(
	  /** The age of the hot key measured from when it was first detected. */
		hotKeyAge: Option[String] = None,
	  /** System-defined name of the step containing this hot key. Unique across the workflow. */
		systemName: Option[String] = None,
	  /** User-provided name of the step that contains this hot key. */
		userStepName: Option[String] = None
	)
	
	case class LeaseWorkItemRequest(
	  /** Filter for WorkItem type. */
		workItemTypes: Option[List[String]] = None,
	  /** Worker capabilities. WorkItems might be limited to workers with specific capabilities. */
		workerCapabilities: Option[List[String]] = None,
	  /** The initial lease period. */
		requestedLeaseDuration: Option[String] = None,
	  /** The current timestamp at the worker. */
		currentWorkerTime: Option[String] = None,
	  /** Identifies the worker leasing work -- typically the ID of the virtual machine running the worker. */
		workerId: Option[String] = None,
	  /** The [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints) that contains the WorkItem's job. */
		location: Option[String] = None,
	  /** Untranslated bag-of-bytes WorkRequest from UnifiedWorker. */
		unifiedWorkerRequest: Option[Map[String, JsValue]] = None,
	  /** Optional. The project number of the project this worker belongs to. */
		projectNumber: Option[String] = None
	)
	
	case class LeaseWorkItemResponse(
	  /** A list of the leased WorkItems. */
		workItems: Option[List[Schema.WorkItem]] = None,
	  /** Untranslated bag-of-bytes WorkResponse for UnifiedWorker. */
		unifiedWorkerResponse: Option[Map[String, JsValue]] = None
	)
	
	case class WorkItem(
	  /** Identifies this WorkItem. */
		id: Option[String] = None,
	  /** Identifies the cloud project this WorkItem belongs to. */
		projectId: Option[String] = None,
	  /** Identifies the workflow job this WorkItem belongs to. */
		jobId: Option[String] = None,
	  /** Any required packages that need to be fetched in order to execute this WorkItem. */
		packages: Option[List[Schema.Package]] = None,
	  /** Additional information for MapTask WorkItems. */
		mapTask: Option[Schema.MapTask] = None,
	  /** Additional information for SeqMapTask WorkItems. */
		seqMapTask: Option[Schema.SeqMapTask] = None,
	  /** Additional information for ShellTask WorkItems. */
		shellTask: Option[Schema.ShellTask] = None,
	  /** Additional information for StreamingSetupTask WorkItems. */
		streamingSetupTask: Option[Schema.StreamingSetupTask] = None,
	  /** Additional information for source operation WorkItems. */
		sourceOperationTask: Option[Schema.SourceOperationRequest] = None,
	  /** Additional information for StreamingComputationTask WorkItems. */
		streamingComputationTask: Option[Schema.StreamingComputationTask] = None,
	  /** Additional information for StreamingConfigTask WorkItems. */
		streamingConfigTask: Option[Schema.StreamingConfigTask] = None,
	  /** Recommended reporting interval. */
		reportStatusInterval: Option[String] = None,
	  /** Time when the lease on this Work will expire. */
		leaseExpireTime: Option[String] = None,
	  /** Work item-specific configuration as an opaque blob. */
		configuration: Option[String] = None,
	  /** The initial index to use when reporting the status of the WorkItem. */
		initialReportIndex: Option[String] = None
	)
	
	case class MapTask(
	  /** The instructions in the MapTask. */
		instructions: Option[List[Schema.ParallelInstruction]] = None,
	  /** System-defined name of this MapTask. Unique across the workflow. */
		systemName: Option[String] = None,
	  /** System-defined name of the stage containing this MapTask. Unique across the workflow. */
		stageName: Option[String] = None,
	  /** Counter prefix that can be used to prefix counters. Not currently used in Dataflow. */
		counterPrefix: Option[String] = None
	)
	
	case class ParallelInstruction(
	  /** System-defined name of this operation. Unique across the workflow. */
		systemName: Option[String] = None,
	  /** User-provided name of this operation. */
		name: Option[String] = None,
	  /** System-defined name for the operation in the original workflow graph. */
		originalName: Option[String] = None,
	  /** Additional information for Read instructions. */
		read: Option[Schema.ReadInstruction] = None,
	  /** Additional information for Write instructions. */
		write: Option[Schema.WriteInstruction] = None,
	  /** Additional information for ParDo instructions. */
		parDo: Option[Schema.ParDoInstruction] = None,
	  /** Additional information for PartialGroupByKey instructions. */
		partialGroupByKey: Option[Schema.PartialGroupByKeyInstruction] = None,
	  /** Additional information for Flatten instructions. */
		flatten: Option[Schema.FlattenInstruction] = None,
	  /** Describes the outputs of the instruction. */
		outputs: Option[List[Schema.InstructionOutput]] = None
	)
	
	case class ReadInstruction(
	  /** The source to read from. */
		source: Option[Schema.Source] = None
	)
	
	case class WriteInstruction(
	  /** The input. */
		input: Option[Schema.InstructionInput] = None,
	  /** The sink to write to. */
		sink: Option[Schema.Sink] = None
	)
	
	case class InstructionInput(
	  /** The index (origin zero) of the parallel instruction that produces the output to be consumed by this input. This index is relative to the list of instructions in this input's instruction's containing MapTask. */
		producerInstructionIndex: Option[Int] = None,
	  /** The output index (origin zero) within the producer. */
		outputNum: Option[Int] = None
	)
	
	case class Sink(
	  /** The sink to write to, plus its parameters. */
		spec: Option[Map[String, JsValue]] = None,
	  /** The codec to use to encode data written to the sink. */
		codec: Option[Map[String, JsValue]] = None
	)
	
	case class ParDoInstruction(
	  /** The input. */
		input: Option[Schema.InstructionInput] = None,
	  /** Zero or more side inputs. */
		sideInputs: Option[List[Schema.SideInputInfo]] = None,
	  /** The user function to invoke. */
		userFn: Option[Map[String, JsValue]] = None,
	  /** The number of outputs. */
		numOutputs: Option[Int] = None,
	  /** Information about each of the outputs, if user_fn is a MultiDoFn. */
		multiOutputInfos: Option[List[Schema.MultiOutputInfo]] = None
	)
	
	case class SideInputInfo(
	  /** The source(s) to read element(s) from to get the value of this side input. If more than one source, then the elements are taken from the sources, in the specified order if order matters. At least one source is required. */
		sources: Option[List[Schema.Source]] = None,
	  /** How to interpret the source element(s) as a side input value. */
		kind: Option[Map[String, JsValue]] = None,
	  /** The id of the tag the user code will access this side input by; this should correspond to the tag of some MultiOutputInfo. */
		tag: Option[String] = None
	)
	
	case class MultiOutputInfo(
	  /** The id of the tag the user code will emit to this output by; this should correspond to the tag of some SideInputInfo. */
		tag: Option[String] = None
	)
	
	case class PartialGroupByKeyInstruction(
	  /** Describes the input to the partial group-by-key instruction. */
		input: Option[Schema.InstructionInput] = None,
	  /** The codec to use for interpreting an element in the input PTable. */
		inputElementCodec: Option[Map[String, JsValue]] = None,
	  /** The value combining function to invoke. */
		valueCombiningFn: Option[Map[String, JsValue]] = None,
	  /** Zero or more side inputs. */
		sideInputs: Option[List[Schema.SideInputInfo]] = None,
	  /** If this instruction includes a combining function, this is the name of the CombineValues instruction lifted into this instruction. */
		originalCombineValuesStepName: Option[String] = None,
	  /** If this instruction includes a combining function this is the name of the intermediate store between the GBK and the CombineValues. */
		originalCombineValuesInputStoreName: Option[String] = None
	)
	
	case class FlattenInstruction(
	  /** Describes the inputs to the flatten instruction. */
		inputs: Option[List[Schema.InstructionInput]] = None
	)
	
	case class InstructionOutput(
	  /** The user-provided name of this output. */
		name: Option[String] = None,
	  /** System-defined name of this output. Unique across the workflow. */
		systemName: Option[String] = None,
	  /** System-defined name for this output in the original workflow graph. Outputs that do not contribute to an original instruction do not set this. */
		originalName: Option[String] = None,
	  /** The codec to use to encode data being written via this output. */
		codec: Option[Map[String, JsValue]] = None,
	  /** For system-generated byte and mean byte metrics, certain instructions should only report the key size. */
		onlyCountKeyBytes: Option[Boolean] = None,
	  /** For system-generated byte and mean byte metrics, certain instructions should only report the value size. */
		onlyCountValueBytes: Option[Boolean] = None
	)
	
	case class SeqMapTask(
	  /** Information about each of the inputs. */
		inputs: Option[List[Schema.SideInputInfo]] = None,
	  /** The user function to invoke. */
		userFn: Option[Map[String, JsValue]] = None,
	  /** Information about each of the outputs. */
		outputInfos: Option[List[Schema.SeqMapTaskOutputInfo]] = None,
	  /** The user-provided name of the SeqDo operation. */
		name: Option[String] = None,
	  /** System-defined name of the SeqDo operation. Unique across the workflow. */
		systemName: Option[String] = None,
	  /** System-defined name of the stage containing the SeqDo operation. Unique across the workflow. */
		stageName: Option[String] = None
	)
	
	case class SeqMapTaskOutputInfo(
	  /** The id of the TupleTag the user code will tag the output value by. */
		tag: Option[String] = None,
	  /** The sink to write the output value to. */
		sink: Option[Schema.Sink] = None
	)
	
	case class ShellTask(
	  /** The shell command to run. */
		command: Option[String] = None,
	  /** Exit code for the task. */
		exitCode: Option[Int] = None
	)
	
	case class StreamingSetupTask(
	  /** The TCP port on which the worker should listen for messages from other streaming computation workers. */
		receiveWorkPort: Option[Int] = None,
	  /** The TCP port used by the worker to communicate with the Dataflow worker harness. */
		workerHarnessPort: Option[Int] = None,
	  /** The global topology of the streaming Dataflow job. */
		streamingComputationTopology: Option[Schema.TopologyConfig] = None,
	  /** The user has requested drain. */
		drain: Option[Boolean] = None,
	  /** Configures streaming appliance snapshot. */
		snapshotConfig: Option[Schema.StreamingApplianceSnapshotConfig] = None
	)
	
	case class TopologyConfig(
	  /** The computations associated with a streaming Dataflow job. */
		computations: Option[List[Schema.ComputationTopology]] = None,
	  /** The disks assigned to a streaming Dataflow job. */
		dataDiskAssignments: Option[List[Schema.DataDiskAssignment]] = None,
	  /** Maps user stage names to stable computation names. */
		userStageToComputationNameMap: Option[Map[String, String]] = None,
	  /** The size (in bits) of keys that will be assigned to source messages. */
		forwardingKeyBits: Option[Int] = None,
	  /** Version number for persistent state. */
		persistentStateVersion: Option[Int] = None
	)
	
	case class ComputationTopology(
	  /** The system stage name. */
		systemStageName: Option[String] = None,
	  /** The ID of the computation. */
		computationId: Option[String] = None,
	  /** The key ranges processed by the computation. */
		keyRanges: Option[List[Schema.KeyRangeLocation]] = None,
	  /** The inputs to the computation. */
		inputs: Option[List[Schema.StreamLocation]] = None,
	  /** The outputs from the computation. */
		outputs: Option[List[Schema.StreamLocation]] = None,
	  /** The state family values. */
		stateFamilies: Option[List[Schema.StateFamilyConfig]] = None
	)
	
	case class KeyRangeLocation(
	  /** The start (inclusive) of the key range. */
		start: Option[String] = None,
	  /** The end (exclusive) of the key range. */
		end: Option[String] = None,
	  /** The physical location of this range assignment to be used for streaming computation cross-worker message delivery. */
		deliveryEndpoint: Option[String] = None,
	  /** The name of the data disk where data for this range is stored. This name is local to the Google Cloud Platform project and uniquely identifies the disk within that project, for example "myproject-1014-104817-4c2-harness-0-disk-1". */
		dataDisk: Option[String] = None,
	  /** DEPRECATED. The location of the persistent state for this range, as a persistent directory in the worker local filesystem. */
		deprecatedPersistentDirectory: Option[String] = None
	)
	
	case class StreamLocation(
	  /** The stream is part of another computation within the current streaming Dataflow job. */
		streamingStageLocation: Option[Schema.StreamingStageLocation] = None,
	  /** The stream is a pubsub stream. */
		pubsubLocation: Option[Schema.PubsubLocation] = None,
	  /** The stream is a streaming side input. */
		sideInputLocation: Option[Schema.StreamingSideInputLocation] = None,
	  /** The stream is a custom source. */
		customSourceLocation: Option[Schema.CustomSourceLocation] = None
	)
	
	case class StreamingStageLocation(
	  /** Identifies the particular stream within the streaming Dataflow job. */
		streamId: Option[String] = None
	)
	
	case class PubsubLocation(
	  /** A pubsub topic, in the form of "pubsub.googleapis.com/topics//" */
		topic: Option[String] = None,
	  /** A pubsub subscription, in the form of "pubsub.googleapis.com/subscriptions//" */
		subscription: Option[String] = None,
	  /** If set, contains a pubsub label from which to extract record timestamps. If left empty, record timestamps will be generated upon arrival. */
		timestampLabel: Option[String] = None,
	  /** If set, contains a pubsub label from which to extract record ids. If left empty, record deduplication will be strictly best effort. */
		idLabel: Option[String] = None,
	  /** Indicates whether the pipeline allows late-arriving data. */
		dropLateData: Option[Boolean] = None,
	  /** If set, specifies the pubsub subscription that will be used for tracking custom time timestamps for watermark estimation. */
		trackingSubscription: Option[String] = None,
	  /** If true, then the client has requested to get pubsub attributes. */
		withAttributes: Option[Boolean] = None,
	  /** If true, then this location represents dynamic topics. */
		dynamicDestinations: Option[Boolean] = None
	)
	
	case class StreamingSideInputLocation(
	  /** Identifies the particular side input within the streaming Dataflow job. */
		tag: Option[String] = None,
	  /** Identifies the state family where this side input is stored. */
		stateFamily: Option[String] = None
	)
	
	case class CustomSourceLocation(
	  /** Whether this source is stateful. */
		stateful: Option[Boolean] = None
	)
	
	case class StateFamilyConfig(
	  /** The state family value. */
		stateFamily: Option[String] = None,
	  /** If true, this family corresponds to a read operation. */
		isRead: Option[Boolean] = None
	)
	
	case class DataDiskAssignment(
	  /** VM instance name the data disks mounted to, for example "myproject-1014-104817-4c2-harness-0". */
		vmInstance: Option[String] = None,
	  /** Mounted data disks. The order is important a data disk's 0-based index in this list defines which persistent directory the disk is mounted to, for example the list of { "myproject-1014-104817-4c2-harness-0-disk-0" }, { "myproject-1014-104817-4c2-harness-0-disk-1" }. */
		dataDisks: Option[List[String]] = None
	)
	
	case class StreamingApplianceSnapshotConfig(
	  /** If set, indicates the snapshot id for the snapshot being performed. */
		snapshotId: Option[String] = None,
	  /** Indicates which endpoint is used to import appliance state. */
		importStateEndpoint: Option[String] = None
	)
	
	case class SourceOperationRequest(
	  /** System-defined name of the Read instruction for this source. Unique across the workflow. */
		systemName: Option[String] = None,
	  /** User-provided name of the Read instruction for this source. */
		name: Option[String] = None,
	  /** System-defined name for the Read instruction for this source in the original workflow graph. */
		originalName: Option[String] = None,
	  /** System-defined name of the stage containing the source operation. Unique across the workflow. */
		stageName: Option[String] = None,
	  /** Information about a request to split a source. */
		split: Option[Schema.SourceSplitRequest] = None,
	  /** Information about a request to get metadata about a source. */
		getMetadata: Option[Schema.SourceGetMetadataRequest] = None
	)
	
	case class SourceSplitRequest(
	  /** Specification of the source to be split. */
		source: Option[Schema.Source] = None,
	  /** Hints for tuning the splitting process. */
		options: Option[Schema.SourceSplitOptions] = None
	)
	
	case class SourceSplitOptions(
	  /** The source should be split into a set of bundles where the estimated size of each is approximately this many bytes. */
		desiredBundleSizeBytes: Option[String] = None,
	  /** DEPRECATED in favor of desired_bundle_size_bytes. */
		desiredShardSizeBytes: Option[String] = None
	)
	
	case class SourceGetMetadataRequest(
	  /** Specification of the source whose metadata should be computed. */
		source: Option[Schema.Source] = None
	)
	
	object StreamingComputationTask {
		enum TaskTypeEnum extends Enum[TaskTypeEnum] { case STREAMING_COMPUTATION_TASK_UNKNOWN, STREAMING_COMPUTATION_TASK_STOP, STREAMING_COMPUTATION_TASK_START }
	}
	case class StreamingComputationTask(
	  /** A type of streaming computation task. */
		taskType: Option[Schema.StreamingComputationTask.TaskTypeEnum] = None,
	  /** Describes the set of data disks this task should apply to. */
		dataDisks: Option[List[Schema.MountedDataDisk]] = None,
	  /** Contains ranges of a streaming computation this task should apply to. */
		computationRanges: Option[List[Schema.StreamingComputationRanges]] = None
	)
	
	case class MountedDataDisk(
	  /** The name of the data disk. This name is local to the Google Cloud Platform project and uniquely identifies the disk within that project, for example "myproject-1014-104817-4c2-harness-0-disk-1". */
		dataDisk: Option[String] = None
	)
	
	case class StreamingComputationRanges(
	  /** The ID of the computation. */
		computationId: Option[String] = None,
	  /** Data disk assignments for ranges from this computation. */
		rangeAssignments: Option[List[Schema.KeyRangeDataDiskAssignment]] = None
	)
	
	case class KeyRangeDataDiskAssignment(
	  /** The start (inclusive) of the key range. */
		start: Option[String] = None,
	  /** The end (exclusive) of the key range. */
		end: Option[String] = None,
	  /** The name of the data disk where data for this range is stored. This name is local to the Google Cloud Platform project and uniquely identifies the disk within that project, for example "myproject-1014-104817-4c2-harness-0-disk-1". */
		dataDisk: Option[String] = None
	)
	
	case class StreamingConfigTask(
	  /** If present, the worker must use this endpoint to communicate with Windmill Service dispatchers, otherwise the worker must continue to use whatever endpoint it had been using. */
		windmillServiceEndpoint: Option[String] = None,
	  /** If present, the worker must use this port to communicate with Windmill Service dispatchers. Only applicable when windmill_service_endpoint is specified. */
		windmillServicePort: Option[String] = None,
	  /** Set of computation configuration information. */
		streamingComputationConfigs: Option[List[Schema.StreamingComputationConfig]] = None,
	  /** Map from user step names to state families. */
		userStepToStateFamilyNameMap: Option[Map[String, String]] = None,
	  /** Maximum size for work item commit supported windmill storage layer. */
		maxWorkItemCommitBytes: Option[String] = None,
	  /** Chunk size for commit streams from the harness to windmill. */
		commitStreamChunkSizeBytes: Option[String] = None,
	  /** Chunk size for get data streams from the harness to windmill. */
		getDataStreamChunkSizeBytes: Option[String] = None,
	  /** Operational limits for the streaming job. Can be used by the worker to validate outputs sent to the backend. */
		operationalLimits: Option[Schema.StreamingOperationalLimits] = None,
	  /** Binary encoded proto to control runtime behavior of the java runner v1 user worker. */
		userWorkerRunnerV1Settings: Option[String] = None,
	  /** Binary encoded proto to control runtime behavior of the runner v2 user worker. */
		userWorkerRunnerV2Settings: Option[String] = None
	)
	
	case class StreamingComputationConfig(
	  /** Unique identifier for this computation. */
		computationId: Option[String] = None,
	  /** System defined name for this computation. */
		systemName: Option[String] = None,
	  /** Stage name of this computation. */
		stageName: Option[String] = None,
	  /** Instructions that comprise the computation. */
		instructions: Option[List[Schema.ParallelInstruction]] = None,
	  /** Map from user name of stateful transforms in this stage to their state family. */
		transformUserNameToStateFamily: Option[Map[String, String]] = None
	)
	
	case class StreamingOperationalLimits(
	  /** The maximum size for a single output element. */
		maxProductionOutputBytes: Option[String] = None,
	  /** The maximum size allowed for a key. */
		maxKeyBytes: Option[String] = None,
	  /** The maximum size for a state tag. */
		maxTagBytes: Option[String] = None,
	  /** The maximum size for a value state field. */
		maxValueBytes: Option[String] = None,
	  /** The maximum size for an element in bag state. */
		maxBagElementBytes: Option[String] = None,
	  /** The maximum size for an element in sorted list state. */
		maxSortedListElementBytes: Option[String] = None,
	  /** The maximum size for an element in global data. */
		maxGlobalDataBytes: Option[String] = None,
	  /** The maximum size for a source state update. */
		maxSourceStateBytes: Option[String] = None
	)
	
	case class SendWorkerMessagesRequest(
	  /** The WorkerMessages to send. */
		workerMessages: Option[List[Schema.WorkerMessage]] = None,
	  /** The [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints) that contains the job. */
		location: Option[String] = None
	)
	
	case class WorkerMessage(
	  /** Labels are used to group WorkerMessages. For example, a worker_message about a particular container might have the labels: { "JOB_ID": "2015-04-22", "WORKER_ID": "wordcount-vm-2015…" "CONTAINER_TYPE": "worker", "CONTAINER_ID": "ac1234def"} Label tags typically correspond to Label enum values. However, for ease of development other strings can be used as tags. LABEL_UNSPECIFIED should not be used here. */
		labels: Option[Map[String, String]] = None,
	  /** The timestamp of the worker_message. */
		time: Option[String] = None,
	  /** The health of a worker. */
		workerHealthReport: Option[Schema.WorkerHealthReport] = None,
	  /** A worker message code. */
		workerMessageCode: Option[Schema.WorkerMessageCode] = None,
	  /** Resource metrics reported by workers. */
		workerMetrics: Option[Schema.ResourceUtilizationReport] = None,
	  /** Shutdown notice by workers. */
		workerShutdownNotice: Option[Schema.WorkerShutdownNotice] = None,
	  /** Record of worker lifecycle events. */
		workerLifecycleEvent: Option[Schema.WorkerLifecycleEvent] = None,
	  /** Thread scaling information reported by workers. */
		workerThreadScalingReport: Option[Schema.WorkerThreadScalingReport] = None,
	  /** Optional. Contains metrics related to go/dataflow-data-sampling-telemetry. */
		dataSamplingReport: Option[Schema.DataSamplingReport] = None,
	  /** Contains per-user worker telemetry used in streaming autoscaling. */
		streamingScalingReport: Option[Schema.StreamingScalingReport] = None,
	  /** System defined metrics for this worker. */
		perWorkerMetrics: Option[Schema.PerWorkerMetrics] = None
	)
	
	case class WorkerHealthReport(
	  /** Whether the VM is currently healthy. */
		vmIsHealthy: Option[Boolean] = None,
	  /** Whether the VM is in a permanently broken state. Broken VMs should be abandoned or deleted ASAP to avoid assigning or completing any work. */
		vmIsBroken: Option[Boolean] = None,
	  /** Code to describe a specific reason, if known, that a VM has reported broken state. */
		vmBrokenCode: Option[String] = None,
	  /** Message describing any unusual health reports. */
		msg: Option[String] = None,
	  /** The time the VM was booted. */
		vmStartupTime: Option[String] = None,
	  /** The interval at which the worker is sending health reports. The default value of 0 should be interpreted as the field is not being explicitly set by the worker. */
		reportInterval: Option[String] = None,
	  /** The pods running on the worker. See: http://kubernetes.io/v1.1/docs/api-reference/v1/definitions.html#_v1_pod This field is used by the worker to send the status of the indvidual containers running on each worker. */
		pods: Option[List[Map[String, JsValue]]] = None
	)
	
	case class WorkerMessageCode(
	  /** The code is a string intended for consumption by a machine that identifies the type of message being sent. Examples: 1. "HARNESS_STARTED" might be used to indicate the worker harness has started. 2. "GCS_DOWNLOAD_ERROR" might be used to indicate an error downloading a Cloud Storage file as part of the boot process of one of the worker containers. This is a string and not an enum to make it easy to add new codes without waiting for an API change. */
		code: Option[String] = None,
	  /** Parameters contains specific information about the code. This is a struct to allow parameters of different types. Examples: 1. For a "HARNESS_STARTED" message parameters might provide the name of the worker and additional data like timing information. 2. For a "GCS_DOWNLOAD_ERROR" parameters might contain fields listing the Cloud Storage objects being downloaded and fields containing errors. In general complex data structures should be avoided. If a worker needs to send a specific and complicated data structure then please consider defining a new proto and adding it to the data oneof in WorkerMessageResponse. Conventions: Parameters should only be used for information that isn't typically passed as a label. hostname and other worker identifiers should almost always be passed as labels since they will be included on most messages. */
		parameters: Option[Map[String, JsValue]] = None
	)
	
	case class ResourceUtilizationReport(
	  /** CPU utilization samples. */
		cpuTime: Option[List[Schema.CPUTime]] = None,
	  /** Optional. GPU usage samples. */
		gpuUsage: Option[List[Schema.GPUUsage]] = None,
	  /** Memory utilization samples. */
		memoryInfo: Option[List[Schema.MemInfo]] = None,
	  /** Per container information. Key: container name. */
		containers: Option[Map[String, Schema.ResourceUtilizationReport]] = None
	)
	
	case class CPUTime(
	  /** Timestamp of the measurement. */
		timestamp: Option[String] = None,
	  /** Total active CPU time across all cores (ie., non-idle) in milliseconds since start-up. */
		totalMs: Option[String] = None,
	  /** Average CPU utilization rate (% non-idle cpu / second) since previous sample. */
		rate: Option[BigDecimal] = None
	)
	
	case class GPUUsage(
	  /** Required. Timestamp of the measurement. */
		timestamp: Option[String] = None,
	  /** Required. Utilization info about the GPU. */
		utilization: Option[Schema.GPUUtilization] = None
	)
	
	case class GPUUtilization(
	  /** Required. GPU utilization rate of any kernel over the last sample period in the range of [0, 1]. */
		rate: Option[BigDecimal] = None
	)
	
	case class MemInfo(
	  /** Timestamp of the measurement. */
		timestamp: Option[String] = None,
	  /** Total memory (RSS) usage since start up in GB &#42; ms. */
		totalGbMs: Option[String] = None,
	  /** Instantenous memory (RSS) size in bytes. */
		currentRssBytes: Option[String] = None,
	  /** Instantenous memory limit in bytes. */
		currentLimitBytes: Option[String] = None,
	  /** Number of Out of Memory (OOM) events recorded since the previous measurement. */
		currentOoms: Option[String] = None
	)
	
	case class WorkerShutdownNotice(
	  /** The reason for the worker shutdown. Current possible values are: "UNKNOWN": shutdown reason is unknown. "PREEMPTION": shutdown reason is preemption. Other possible reasons may be added in the future. */
		reason: Option[String] = None
	)
	
	object WorkerLifecycleEvent {
		enum EventEnum extends Enum[EventEnum] { case UNKNOWN_EVENT, OS_START, CONTAINER_START, NETWORK_UP, STAGING_FILES_DOWNLOAD_START, STAGING_FILES_DOWNLOAD_FINISH, SDK_INSTALL_START, SDK_INSTALL_FINISH }
	}
	case class WorkerLifecycleEvent(
	  /** The event being reported. */
		event: Option[Schema.WorkerLifecycleEvent.EventEnum] = None,
	  /** Other stats that can accompany an event. E.g. { "downloaded_bytes" : "123456" } */
		metadata: Option[Map[String, String]] = None,
	  /** The start time of this container. All events will report this so that events can be grouped together across container/VM restarts. */
		containerStartTime: Option[String] = None
	)
	
	case class WorkerThreadScalingReport(
	  /** Current number of active threads in a worker. */
		currentThreadCount: Option[Int] = None
	)
	
	case class DataSamplingReport(
	  /** Optional. Delta of bytes sampled from previous report. */
		elementsSampledBytes: Option[String] = None,
	  /** Optional. Delta of number of elements sampled from previous report. */
		elementsSampledCount: Option[String] = None,
	  /** Optional. Delta of number of PCollections sampled from previous report. */
		pcollectionsSampledCount: Option[String] = None,
	  /** Optional. Delta of number of samples taken from user code exceptions from previous report. */
		exceptionsSampledCount: Option[String] = None,
	  /** Optional. Delta of bytes written to file from previous report. */
		bytesWrittenDelta: Option[String] = None,
	  /** Optional. Delta of errors counts from retrieving, or translating the samples from previous report. */
		translationErrorsCount: Option[String] = None,
	  /** Optional. Delta of errors counts from persisting the samples from previous report. */
		persistenceErrorsCount: Option[String] = None
	)
	
	case class StreamingScalingReport(
	  /** Current acive thread count. */
		activeThreadCount: Option[Int] = None,
	  /** Maximum thread count limit. */
		maximumThreadCount: Option[Int] = None,
	  /** Maximum bundle count. */
		maximumBundleCount: Option[Int] = None,
	  /** Current outstanding bundle count. */
		outstandingBundleCount: Option[Int] = None,
	  /** Current outstanding bytes. */
		outstandingBytes: Option[String] = None,
	  /** Maximum bytes. */
		maximumBytes: Option[String] = None,
		activeBundleCount: Option[Int] = None,
		outstandingBytesCount: Option[Int] = None,
		maximumBytesCount: Option[Int] = None
	)
	
	case class PerWorkerMetrics(
	  /** Optional. Metrics for a particular unfused step and namespace. */
		perStepNamespaceMetrics: Option[List[Schema.PerStepNamespaceMetrics]] = None
	)
	
	case class PerStepNamespaceMetrics(
	  /** The namespace of these metrics on the worker. */
		metricsNamespace: Option[String] = None,
	  /** The original system name of the unfused step that these metrics are reported from. */
		originalStep: Option[String] = None,
	  /** Optional. Metrics that are recorded for this namespace and unfused step. */
		metricValues: Option[List[Schema.MetricValue]] = None
	)
	
	case class MetricValue(
	  /** Base name for this metric. */
		metric: Option[String] = None,
	  /** Optional. Set of metric labels for this metric. */
		metricLabels: Option[Map[String, String]] = None,
	  /** Integer value of this metric. */
		valueInt64: Option[String] = None,
	  /** Histogram value of this metric. */
		valueHistogram: Option[Schema.DataflowHistogramValue] = None
	)
	
	case class DataflowHistogramValue(
	  /** Number of values recorded in this histogram. */
		count: Option[String] = None,
	  /** Describes the bucket boundaries used in the histogram. */
		bucketOptions: Option[Schema.BucketOptions] = None,
	  /** Optional. The number of values in each bucket of the histogram, as described in `bucket_options`. `bucket_counts` should contain N values, where N is the number of buckets specified in `bucket_options`. If `bucket_counts` has fewer than N values, the remaining values are assumed to be 0. */
		bucketCounts: Option[List[String]] = None,
	  /** Statistics on the values recorded in the histogram that fall out of the bucket boundaries. */
		outlierStats: Option[Schema.OutlierStats] = None
	)
	
	case class BucketOptions(
	  /** Bucket boundaries grow linearly. */
		linear: Option[Schema.Linear] = None,
	  /** Bucket boundaries grow exponentially. */
		exponential: Option[Schema.Base2Exponent] = None
	)
	
	case class Linear(
	  /** Must be greater than 0. */
		numberOfBuckets: Option[Int] = None,
	  /** Distance between bucket boundaries. Must be greater than 0. */
		width: Option[BigDecimal] = None,
	  /** Lower bound of the first bucket. */
		start: Option[BigDecimal] = None
	)
	
	case class Base2Exponent(
	  /** Must be greater than 0. */
		numberOfBuckets: Option[Int] = None,
	  /** Must be between -3 and 3. This forces the growth factor of the bucket boundaries to be between `2^(1/8)` and `256`. */
		scale: Option[Int] = None
	)
	
	case class OutlierStats(
	  /** Number of values that are smaller than the lower bound of the smallest bucket. */
		underflowCount: Option[String] = None,
	  /** Mean of values in the undeflow bucket. */
		underflowMean: Option[BigDecimal] = None,
	  /** Number of values that are larger than the upper bound of the largest bucket. */
		overflowCount: Option[String] = None,
	  /** Mean of values in the overflow bucket. */
		overflowMean: Option[BigDecimal] = None
	)
	
	case class SendWorkerMessagesResponse(
	  /** The servers response to the worker messages. */
		workerMessageResponses: Option[List[Schema.WorkerMessageResponse]] = None
	)
	
	case class WorkerMessageResponse(
	  /** The service's response to a worker's health report. */
		workerHealthReportResponse: Option[Schema.WorkerHealthReportResponse] = None,
	  /** Service's response to reporting worker metrics (currently empty). */
		workerMetricsResponse: Option[Schema.ResourceUtilizationReportResponse] = None,
	  /** Service's response to shutdown notice (currently empty). */
		workerShutdownNoticeResponse: Option[Schema.WorkerShutdownNoticeResponse] = None,
	  /** Service's thread scaling recommendation for workers. */
		workerThreadScalingReportResponse: Option[Schema.WorkerThreadScalingReportResponse] = None,
	  /** Service's streaming scaling response for workers. */
		streamingScalingReportResponse: Option[Schema.StreamingScalingReportResponse] = None
	)
	
	case class WorkerHealthReportResponse(
	  /** A positive value indicates the worker should change its reporting interval to the specified value. The default value of zero means no change in report rate is requested by the server. */
		reportInterval: Option[String] = None
	)
	
	case class ResourceUtilizationReportResponse(
	
	)
	
	case class WorkerShutdownNoticeResponse(
	
	)
	
	case class WorkerThreadScalingReportResponse(
	  /** Recommended number of threads for a worker. */
		recommendedThreadCount: Option[Int] = None
	)
	
	case class StreamingScalingReportResponse(
	  /** Maximum thread count limit; */
		maximumThreadCount: Option[Int] = None
	)
}
