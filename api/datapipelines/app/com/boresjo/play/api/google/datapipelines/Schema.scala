package com.boresjo.play.api.google.datapipelines

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object GoogleCloudDatapipelinesV1Job {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STATE_PENDING, STATE_RUNNING, STATE_DONE, STATE_FAILED, STATE_CANCELLED }
	}
	case class GoogleCloudDatapipelinesV1Job(
	  /** Output only. The time of job termination. This is absent if the job is still running. */
		endTime: Option[String] = None,
	  /** Status capturing any error code or message related to job creation or execution. */
		status: Option[Schema.GoogleRpcStatus] = None,
	  /** The current state of the job. */
		state: Option[Schema.GoogleCloudDatapipelinesV1Job.StateEnum] = None,
	  /** Output only. The internal ID for the job. */
		id: Option[String] = None,
	  /** Required. The fully qualified resource name for the job. */
		name: Option[String] = None,
	  /** All the details that are specific to a Dataflow job. */
		dataflowJobDetails: Option[Schema.GoogleCloudDatapipelinesV1DataflowJobDetails] = None,
	  /** Output only. The time of job creation. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDatapipelinesV1Workload(
	  /** Template information and additional parameters needed to launch a Dataflow job using the flex launch API. */
		dataflowFlexTemplateRequest: Option[Schema.GoogleCloudDatapipelinesV1LaunchFlexTemplateRequest] = None,
	  /** Template information and additional parameters needed to launch a Dataflow job using the standard launch API. */
		dataflowLaunchTemplateRequest: Option[Schema.GoogleCloudDatapipelinesV1LaunchTemplateRequest] = None
	)
	
	object GoogleCloudDatapipelinesV1SdkVersion {
		enum SdkSupportStatusEnum extends Enum[SdkSupportStatusEnum] { case UNKNOWN, SUPPORTED, STALE, DEPRECATED, UNSUPPORTED }
	}
	case class GoogleCloudDatapipelinesV1SdkVersion(
	  /** The support status for this SDK version. */
		sdkSupportStatus: Option[Schema.GoogleCloudDatapipelinesV1SdkVersion.SdkSupportStatusEnum] = None,
	  /** A readable string describing the version of the SDK. */
		versionDisplayName: Option[String] = None,
	  /** The version of the SDK used to run the job. */
		version: Option[String] = None
	)
	
	case class GoogleCloudDatapipelinesV1LaunchTemplateRequest(
	  /** The parameters of the template to launch. This should be part of the body of the POST request. */
		launchParameters: Option[Schema.GoogleCloudDatapipelinesV1LaunchTemplateParameters] = None,
	  /** If true, the request is validated but not actually executed. Defaults to false. */
		validateOnly: Option[Boolean] = None,
	  /** The [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints) to which to direct the request. */
		location: Option[String] = None,
	  /** Required. The ID of the Cloud Platform project that the job belongs to. */
		projectId: Option[String] = None,
	  /** A Cloud Storage path to the template from which to create the job. Must be a valid Cloud Storage URL, beginning with 'gs://'. */
		gcsPath: Option[String] = None
	)
	
	case class GoogleCloudDatapipelinesV1DataflowJobDetails(
	  /** Cached version of all the metrics of interest for the job. This value gets stored here when the job is terminated. As long as the job is running, this field is populated from the Dataflow API. */
		resourceInfo: Option[Map[String, BigDecimal]] = None,
	  /** Output only. The SDK version used to run the job. */
		sdkVersion: Option[Schema.GoogleCloudDatapipelinesV1SdkVersion] = None,
	  /** Output only. The current number of workers used to run the jobs. Only set to a value if the job is still running. */
		currentWorkers: Option[Int] = None
	)
	
	case class GoogleCloudDatapipelinesV1LaunchTemplateParameters(
	  /** The runtime environment for the job. */
		environment: Option[Schema.GoogleCloudDatapipelinesV1RuntimeEnvironment] = None,
	  /** Required. The job name to use for the created job. */
		jobName: Option[String] = None,
	  /** The runtime parameters to pass to the job. */
		parameters: Option[Map[String, String]] = None,
	  /** If set, replace the existing pipeline with the name specified by jobName with this pipeline, preserving state. */
		update: Option[Boolean] = None,
	  /** Map of transform name prefixes of the job to be replaced to the corresponding name prefixes of the new job. Only applicable when updating a pipeline. */
		transformNameMapping: Option[Map[String, String]] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	object GoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment {
		enum FlexrsGoalEnum extends Enum[FlexrsGoalEnum] { case FLEXRS_UNSPECIFIED, FLEXRS_SPEED_OPTIMIZED, FLEXRS_COST_OPTIMIZED }
		enum IpConfigurationEnum extends Enum[IpConfigurationEnum] { case WORKER_IP_UNSPECIFIED, WORKER_IP_PUBLIC, WORKER_IP_PRIVATE }
	}
	case class GoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment(
	  /** Set FlexRS goal for the job. https://cloud.google.com/dataflow/docs/guides/flexrs */
		flexrsGoal: Option[Schema.GoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment.FlexrsGoalEnum] = None,
	  /** The initial number of Compute Engine instances for the job. */
		numWorkers: Option[Int] = None,
	  /** Subnetwork to which VMs will be assigned, if desired. You can specify a subnetwork using either a complete URL or an abbreviated path. Expected to be of the form "https://www.googleapis.com/compute/v1/projects/HOST_PROJECT_ID/regions/REGION/subnetworks/SUBNETWORK" or "regions/REGION/subnetworks/SUBNETWORK". If the subnetwork is located in a Shared VPC network, you must use the complete URL. */
		subnetwork: Option[String] = None,
	  /** Name for the Cloud KMS key for the job. Key format is: projects//locations//keyRings//cryptoKeys/ */
		kmsKeyName: Option[String] = None,
	  /** Additional user labels to be specified for the job. Keys and values must follow the restrictions specified in the [labeling restrictions](https://cloud.google.com/compute/docs/labeling-resources#restrictions). An object containing a list of key/value pairs. Example: `{ "name": "wrench", "mass": "1kg", "count": "3" }`. */
		additionalUserLabels: Option[Map[String, String]] = None,
	  /** Whether to enable Streaming Engine for the job. */
		enableStreamingEngine: Option[Boolean] = None,
	  /** The Compute Engine [availability zone](https://cloud.google.com/compute/docs/regions-zones/regions-zones) for launching worker instances to run your pipeline. In the future, worker_zone will take precedence. */
		zone: Option[String] = None,
	  /** Additional experiment flags for the job. */
		additionalExperiments: Option[List[String]] = None,
	  /** Configuration for VM IPs. */
		ipConfiguration: Option[Schema.GoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment.IpConfigurationEnum] = None,
	  /** The Cloud Storage path to use for temporary files. Must be a valid Cloud Storage URL, beginning with `gs://`. */
		tempLocation: Option[String] = None,
	  /** The email address of the service account to run the job as. */
		serviceAccountEmail: Option[String] = None,
	  /** The maximum number of Compute Engine instances to be made available to your pipeline during execution, from 1 to 1000. */
		maxWorkers: Option[Int] = None,
	  /** Network to which VMs will be assigned. If empty or unspecified, the service will use the network "default". */
		network: Option[String] = None,
	  /** The Compute Engine region (https://cloud.google.com/compute/docs/regions-zones/regions-zones) in which worker processing should occur, e.g. "us-west1". Mutually exclusive with worker_zone. If neither worker_region nor worker_zone is specified, defaults to the control plane region. */
		workerRegion: Option[String] = None,
	  /** The Compute Engine zone (https://cloud.google.com/compute/docs/regions-zones/regions-zones) in which worker processing should occur, e.g. "us-west1-a". Mutually exclusive with worker_region. If neither worker_region nor worker_zone is specified, a zone in the control plane region is chosen based on available capacity. If both `worker_zone` and `zone` are set, `worker_zone` takes precedence. */
		workerZone: Option[String] = None,
	  /** The machine type to use for the job. Defaults to the value from the template if not specified. */
		machineType: Option[String] = None
	)
	
	case class GoogleRpcStatus(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GoogleCloudDatapipelinesV1ListJobsResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Results that were accessible to the caller. Results are always in descending order of job creation date. */
		jobs: Option[List[Schema.GoogleCloudDatapipelinesV1Job]] = None
	)
	
	object GoogleCloudDatapipelinesV1Pipeline {
		enum TypeEnum extends Enum[TypeEnum] { case PIPELINE_TYPE_UNSPECIFIED, PIPELINE_TYPE_BATCH, PIPELINE_TYPE_STREAMING }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STATE_RESUMING, STATE_ACTIVE, STATE_STOPPING, STATE_ARCHIVED, STATE_PAUSED }
	}
	case class GoogleCloudDatapipelinesV1Pipeline(
	  /** Output only. Immutable. The timestamp when the pipeline was initially created. Set by the Data Pipelines service. */
		createTime: Option[String] = None,
	  /** Output only. Number of jobs. */
		jobCount: Option[Int] = None,
	  /** Immutable. The sources of the pipeline (for example, Dataplex). The keys and values are set by the corresponding sources during pipeline creation. */
		pipelineSources: Option[Map[String, String]] = None,
	  /** Optional. A service account email to be used with the Cloud Scheduler job. If not specified, the default compute engine service account will be used. */
		schedulerServiceAccountEmail: Option[String] = None,
	  /** Required. The type of the pipeline. This field affects the scheduling of the pipeline and the type of metrics to show for the pipeline. */
		`type`: Option[Schema.GoogleCloudDatapipelinesV1Pipeline.TypeEnum] = None,
	  /** Required. The display name of the pipeline. It can contain only letters ([A-Za-z]), numbers ([0-9]), hyphens (-), and underscores (_). */
		displayName: Option[String] = None,
	  /** The pipeline name. For example: `projects/PROJECT_ID/locations/LOCATION_ID/pipelines/PIPELINE_ID`. &#42; `PROJECT_ID` can contain letters ([A-Za-z]), numbers ([0-9]), hyphens (-), colons (:), and periods (.). For more information, see [Identifying projects](https://cloud.google.com/resource-manager/docs/creating-managing-projects#identifying_projects). &#42; `LOCATION_ID` is the canonical ID for the pipeline's location. The list of available locations can be obtained by calling `google.cloud.location.Locations.ListLocations`. Note that the Data Pipelines service is not available in all regions. It depends on Cloud Scheduler, an App Engine application, so it's only available in [App Engine regions](https://cloud.google.com/about/locations#region). &#42; `PIPELINE_ID` is the ID of the pipeline. Must be unique for the selected project and location. */
		name: Option[String] = None,
	  /** Internal scheduling information for a pipeline. If this information is provided, periodic jobs will be created per the schedule. If not, users are responsible for creating jobs externally. */
		scheduleInfo: Option[Schema.GoogleCloudDatapipelinesV1ScheduleSpec] = None,
	  /** Required. The state of the pipeline. When the pipeline is created, the state is set to 'PIPELINE_STATE_ACTIVE' by default. State changes can be requested by setting the state to stopping, paused, or resuming. State cannot be changed through UpdatePipeline requests. */
		state: Option[Schema.GoogleCloudDatapipelinesV1Pipeline.StateEnum] = None,
	  /** Workload information for creating new jobs. */
		workload: Option[Schema.GoogleCloudDatapipelinesV1Workload] = None,
	  /** Output only. Immutable. The timestamp when the pipeline was last modified. Set by the Data Pipelines service. */
		lastUpdateTime: Option[String] = None
	)
	
	case class GoogleCloudDatapipelinesV1LaunchFlexTemplateParameter(
	  /** Cloud Storage path to a file with a JSON-serialized ContainerSpec as content. */
		containerSpecGcsPath: Option[String] = None,
	  /** Required. The job name to use for the created job. For an update job request, the job name should be the same as the existing running job. */
		jobName: Option[String] = None,
	  /** Set this to true if you are sending a request to update a running streaming job. When set, the job name should be the same as the running job. */
		update: Option[Boolean] = None,
	  /** The parameters for the Flex Template. Example: `{"num_workers":"5"}` */
		parameters: Option[Map[String, String]] = None,
	  /** Launch options for this Flex Template job. This is a common set of options across languages and templates. This should not be used to pass job parameters. */
		launchOptions: Option[Map[String, String]] = None,
	  /** Use this to pass transform name mappings for streaming update jobs. Example: `{"oldTransformName":"newTransformName",...}` */
		transformNameMappings: Option[Map[String, String]] = None,
	  /** The runtime environment for the Flex Template job. */
		environment: Option[Schema.GoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment] = None
	)
	
	case class GoogleCloudDatapipelinesV1StopPipelineRequest(
	
	)
	
	object GoogleCloudDatapipelinesV1RuntimeEnvironment {
		enum IpConfigurationEnum extends Enum[IpConfigurationEnum] { case WORKER_IP_UNSPECIFIED, WORKER_IP_PUBLIC, WORKER_IP_PRIVATE }
	}
	case class GoogleCloudDatapipelinesV1RuntimeEnvironment(
	  /** The email address of the service account to run the job as. */
		serviceAccountEmail: Option[String] = None,
	  /** Subnetwork to which VMs will be assigned, if desired. You can specify a subnetwork using either a complete URL or an abbreviated path. Expected to be of the form "https://www.googleapis.com/compute/v1/projects/HOST_PROJECT_ID/regions/REGION/subnetworks/SUBNETWORK" or "regions/REGION/subnetworks/SUBNETWORK". If the subnetwork is located in a Shared VPC network, you must use the complete URL. */
		subnetwork: Option[String] = None,
	  /** Configuration for VM IPs. */
		ipConfiguration: Option[Schema.GoogleCloudDatapipelinesV1RuntimeEnvironment.IpConfigurationEnum] = None,
	  /** Network to which VMs will be assigned. If empty or unspecified, the service will use the network "default". */
		network: Option[String] = None,
	  /** Whether to bypass the safety checks for the job's temporary directory. Use with caution. */
		bypassTempDirValidation: Option[Boolean] = None,
	  /** The maximum number of Compute Engine instances to be made available to your pipeline during execution, from 1 to 1000. */
		maxWorkers: Option[Int] = None,
	  /** Additional experiment flags for the job. */
		additionalExperiments: Option[List[String]] = None,
	  /** The Cloud Storage path to use for temporary files. Must be a valid Cloud Storage URL, beginning with `gs://`. */
		tempLocation: Option[String] = None,
	  /** The machine type to use for the job. Defaults to the value from the template if not specified. */
		machineType: Option[String] = None,
	  /** The initial number of Compute Engine instances for the job. */
		numWorkers: Option[Int] = None,
	  /** The Compute Engine [availability zone](https://cloud.google.com/compute/docs/regions-zones/regions-zones) for launching worker instances to run your pipeline. In the future, worker_zone will take precedence. */
		zone: Option[String] = None,
	  /** Additional user labels to be specified for the job. Keys and values should follow the restrictions specified in the [labeling restrictions](https://cloud.google.com/compute/docs/labeling-resources#restrictions) page. An object containing a list of key/value pairs. Example: { "name": "wrench", "mass": "1kg", "count": "3" }. */
		additionalUserLabels: Option[Map[String, String]] = None,
	  /** Whether to enable Streaming Engine for the job. */
		enableStreamingEngine: Option[Boolean] = None,
	  /** The Compute Engine zone (https://cloud.google.com/compute/docs/regions-zones/regions-zones) in which worker processing should occur, e.g. "us-west1-a". Mutually exclusive with worker_region. If neither worker_region nor worker_zone is specified, a zone in the control plane's region is chosen based on available capacity. If both `worker_zone` and `zone` are set, `worker_zone` takes precedence. */
		workerZone: Option[String] = None,
	  /** Name for the Cloud KMS key for the job. The key format is: projects//locations//keyRings//cryptoKeys/ */
		kmsKeyName: Option[String] = None,
	  /** The Compute Engine region (https://cloud.google.com/compute/docs/regions-zones/regions-zones) in which worker processing should occur, e.g. "us-west1". Mutually exclusive with worker_zone. If neither worker_region nor worker_zone is specified, default to the control plane's region. */
		workerRegion: Option[String] = None
	)
	
	case class GoogleCloudDatapipelinesV1ScheduleSpec(
	  /** Unix-cron format of the schedule. This information is retrieved from the linked Cloud Scheduler. */
		schedule: Option[String] = None,
	  /** Timezone ID. This matches the timezone IDs used by the Cloud Scheduler API. If empty, UTC time is assumed. */
		timeZone: Option[String] = None,
	  /** Output only. When the next Scheduler job is going to run. */
		nextJobTime: Option[String] = None
	)
	
	case class GoogleCloudDatapipelinesV1ListPipelinesResponse(
	  /** Results that matched the filter criteria and were accessible to the caller. Results are always in descending order of pipeline creation date. */
		pipelines: Option[List[Schema.GoogleCloudDatapipelinesV1Pipeline]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDatapipelinesV1RunPipelineResponse(
	  /** Job that was created as part of RunPipeline operation. */
		job: Option[Schema.GoogleCloudDatapipelinesV1Job] = None
	)
	
	case class GoogleCloudDatapipelinesV1LaunchFlexTemplateRequest(
	  /** If true, the request is validated but not actually executed. Defaults to false. */
		validateOnly: Option[Boolean] = None,
	  /** Required. The ID of the Cloud Platform project that the job belongs to. */
		projectId: Option[String] = None,
	  /** Required. Parameter to launch a job from a Flex Template. */
		launchParameter: Option[Schema.GoogleCloudDatapipelinesV1LaunchFlexTemplateParameter] = None,
	  /** Required. The [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints) to which to direct the request. For example, `us-central1`, `us-west1`. */
		location: Option[String] = None
	)
	
	case class GoogleCloudDatapipelinesV1RunPipelineRequest(
	
	)
}
