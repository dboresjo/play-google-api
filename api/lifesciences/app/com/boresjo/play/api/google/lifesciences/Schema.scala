package com.boresjo.play.api.google.lifesciences

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
	  /** The server-assigned name for the operation. This may be passed to the other operation methods to retrieve information about the operation's status. */
		name: Option[String] = None,
	  /** An Metadata object. This will always be returned with the Operation. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** An Empty object. */
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
	
	case class CancelOperationRequest(
	
	)
	
	case class Empty(
	
	)
	
	case class RunPipelineRequest(
	  /** Required. The description of the pipeline to run. */
		pipeline: Option[Schema.Pipeline] = None,
	  /** User-defined labels to associate with the returned operation. These labels are not propagated to any Google Cloud Platform resources used by the operation, and can be modified at any time. To associate labels with resources created while executing the operation, see the appropriate resource message (for example, `VirtualMachine`). */
		labels: Option[Map[String, String]] = None,
	  /** The name of an existing Pub/Sub topic. The server will publish messages to this topic whenever the status of the operation changes. The Life Sciences Service Agent account must have publisher permissions to the specified topic or notifications will not be sent. */
		pubSubTopic: Option[String] = None
	)
	
	case class Pipeline(
	  /** The list of actions to execute, in the order they are specified. */
		actions: Option[List[Schema.Action]] = None,
	  /** The resources required for execution. */
		resources: Option[Schema.Resources] = None,
	  /** The environment to pass into every action. Each action can also specify additional environment variables but cannot delete an entry from this map (though they can overwrite it with a different value). */
		environment: Option[Map[String, String]] = None,
	  /** The encrypted environment to pass into every action. Each action can also specify its own encrypted environment. The secret must decrypt to a JSON-encoded dictionary where key-value pairs serve as environment variable names and their values. The decoded environment variables can overwrite the values specified by the `environment` field. */
		encryptedEnvironment: Option[Schema.Secret] = None,
	  /** The maximum amount of time to give the pipeline to complete. This includes the time spent waiting for a worker to be allocated. If the pipeline fails to complete before the timeout, it will be cancelled and the error code will be set to DEADLINE_EXCEEDED. If unspecified, it will default to 7 days. */
		timeout: Option[String] = None
	)
	
	case class Action(
	  /** An optional name for the container. The container hostname will be set to this name, making it useful for inter-container communication. The name must contain only upper and lowercase alphanumeric characters and hyphens and cannot start with a hyphen. */
		containerName: Option[String] = None,
	  /** Required. The URI to pull the container image from. Note that all images referenced by actions in the pipeline are pulled before the first action runs. If multiple actions reference the same image, it is only pulled once, ensuring that the same image is used for all actions in a single pipeline. The image URI can be either a complete host and image specification (e.g., quay.io/biocontainers/samtools), a library and image name (e.g., google/cloud-sdk) or a bare image name ('bash') to pull from the default library. No schema is required in any of these cases. If the specified image is not public, the service account specified for the Virtual Machine must have access to pull the images from GCR, or appropriate credentials must be specified in the google.cloud.lifesciences.v2beta.Action.credentials field. */
		imageUri: Option[String] = None,
	  /** If specified, overrides the `CMD` specified in the container. If the container also has an `ENTRYPOINT` the values are used as entrypoint arguments. Otherwise, they are used as a command and arguments to run inside the container. */
		commands: Option[List[String]] = None,
	  /** If specified, overrides the `ENTRYPOINT` specified in the container. */
		entrypoint: Option[String] = None,
	  /** The environment to pass into the container. This environment is merged with values specified in the google.cloud.lifesciences.v2beta.Pipeline message, overwriting any duplicate values. In addition to the values passed here, a few other values are automatically injected into the environment. These cannot be hidden or overwritten. `GOOGLE_PIPELINE_FAILED` will be set to "1" if the pipeline failed because an action has exited with a non-zero status (and did not have the `IGNORE_EXIT_STATUS` flag set). This can be used to determine if additional debug or logging actions should execute. `GOOGLE_LAST_EXIT_STATUS` will be set to the exit status of the last non-background action that executed. This can be used by workflow engine authors to determine whether an individual action has succeeded or failed. */
		environment: Option[Map[String, String]] = None,
	  /** The encrypted environment to pass into the container. This environment is merged with values specified in the google.cloud.lifesciences.v2beta.Pipeline message, overwriting any duplicate values. The secret must decrypt to a JSON-encoded dictionary where key-value pairs serve as environment variable names and their values. The decoded environment variables can overwrite the values specified by the `environment` field. */
		encryptedEnvironment: Option[Schema.Secret] = None,
	  /** An optional identifier for a PID namespace to run the action inside. Multiple actions should use the same string to share a namespace. If unspecified, a separate isolated namespace is used. */
		pidNamespace: Option[String] = None,
	  /** A map of containers to host port mappings for this container. If the container already specifies exposed ports, use the `PUBLISH_EXPOSED_PORTS` flag instead. The host port number must be less than 65536. If it is zero, an unused random port is assigned. To determine the resulting port number, consult the `ContainerStartedEvent` in the operation metadata. */
		portMappings: Option[Map[String, Int]] = None,
	  /** A list of mounts to make available to the action. In addition to the values specified here, every action has a special virtual disk mounted under `/google` that contains log files and other operational components. - /google/logs All logs written during the pipeline execution. - /google/logs/output The combined standard output and standard error of all actions run as part of the pipeline execution. - /google/logs/action/&#42;/stdout The complete contents of each individual action's standard output. - /google/logs/action/&#42;/stderr The complete contents of each individual action's standard error output.  */
		mounts: Option[List[Schema.Mount]] = None,
	  /** Labels to associate with the action. This field is provided to assist workflow engine authors in identifying actions (for example, to indicate what sort of action they perform, such as localization or debugging). They are returned in the operation metadata, but are otherwise ignored. */
		labels: Option[Map[String, String]] = None,
	  /** If the specified image is hosted on a private registry other than Google Container Registry, the credentials required to pull the image must be specified here as an encrypted secret. The secret must decrypt to a JSON-encoded dictionary containing both `username` and `password` keys. */
		credentials: Option[Schema.Secret] = None,
	  /** The maximum amount of time to give the action to complete. If the action fails to complete before the timeout, it will be terminated and the exit status will be non-zero. The pipeline will continue or terminate based on the rules defined by the `ALWAYS_RUN` and `IGNORE_EXIT_STATUS` flags. */
		timeout: Option[String] = None,
	  /** Normally, a non-zero exit status causes the pipeline to fail. This flag allows execution of other actions to continue instead. */
		ignoreExitStatus: Option[Boolean] = None,
	  /** This flag allows an action to continue running in the background while executing subsequent actions. This is useful to provide services to other actions (or to provide debugging support tools like SSH servers). */
		runInBackground: Option[Boolean] = None,
	  /** By default, after an action fails, no further actions are run. This flag indicates that this action must be run even if the pipeline has already failed. This is useful for actions that copy output files off of the VM or for debugging. Note that no actions will be run if image prefetching fails. */
		alwaysRun: Option[Boolean] = None,
	  /** Enable access to the FUSE device for this action. Filesystems can then be mounted into disks shared with other actions. The other actions do not need the `enable_fuse` flag to access the mounted filesystem. This has the effect of causing the container to be executed with `CAP_SYS_ADMIN` and exposes `/dev/fuse` to the container, so use it only for containers you trust. */
		enableFuse: Option[Boolean] = None,
	  /** Exposes all ports specified by `EXPOSE` statements in the container. To discover the host side port numbers, consult the `ACTION_STARTED` event in the operation metadata. */
		publishExposedPorts: Option[Boolean] = None,
	  /** All container images are typically downloaded before any actions are executed. This helps prevent typos in URIs or issues like lack of disk space from wasting large amounts of compute resources. If set, this flag prevents the worker from downloading the image until just before the action is executed. */
		disableImagePrefetch: Option[Boolean] = None,
	  /** A small portion of the container's standard error stream is typically captured and returned inside the `ContainerStoppedEvent`. Setting this flag disables this functionality. */
		disableStandardErrorCapture: Option[Boolean] = None,
	  /** Prevents the container from accessing the external network. */
		blockExternalNetwork: Option[Boolean] = None
	)
	
	case class Secret(
	  /** The name of the Cloud KMS key that will be used to decrypt the secret value. The VM service account must have the required permissions and authentication scopes to invoke the `decrypt` method on the specified key. */
		keyName: Option[String] = None,
	  /** The value of the cipherText response from the `encrypt` method. This field is intentionally unaudited. */
		cipherText: Option[String] = None
	)
	
	case class Mount(
	  /** The name of the disk to mount, as specified in the resources section. */
		disk: Option[String] = None,
	  /** The path to mount the disk inside the container. */
		path: Option[String] = None,
	  /** If true, the disk is mounted read-only inside the container. */
		readOnly: Option[Boolean] = None
	)
	
	case class Resources(
	  /** The list of regions allowed for VM allocation. If set, the `zones` field must not be set. */
		regions: Option[List[String]] = None,
	  /** The list of zones allowed for VM allocation. If set, the `regions` field must not be set. */
		zones: Option[List[String]] = None,
	  /** The virtual machine specification. */
		virtualMachine: Option[Schema.VirtualMachine] = None
	)
	
	case class VirtualMachine(
	  /** Required. The machine type of the virtual machine to create. Must be the short name of a standard machine type (such as "n1-standard-1") or a custom machine type (such as "custom-1-4096", where "1" indicates the number of vCPUs and "4096" indicates the memory in MB). See [Creating an instance with a custom machine type](https://cloud.google.com/compute/docs/instances/creating-instance-with-custom-machine-type#create) for more specifications on creating a custom machine type. */
		machineType: Option[String] = None,
	  /** If true, allocate a preemptible VM. */
		preemptible: Option[Boolean] = None,
	  /** Optional set of labels to apply to the VM and any attached disk resources. These labels must adhere to the [name and value restrictions](https://cloud.google.com/compute/docs/labeling-resources) on VM labels imposed by Compute Engine. Labels keys with the prefix 'google-' are reserved for use by Google. Labels applied at creation time to the VM. Applied on a best-effort basis to attached disk resources shortly after VM creation. */
		labels: Option[Map[String, String]] = None,
	  /** The list of disks to create and attach to the VM. Specify either the `volumes[]` field or the `disks[]` field, but not both. */
		disks: Option[List[Schema.Disk]] = None,
	  /** The VM network configuration. */
		network: Option[Schema.Network] = None,
	  /** The list of accelerators to attach to the VM. */
		accelerators: Option[List[Schema.Accelerator]] = None,
	  /** The service account to install on the VM. This account does not need any permissions other than those required by the pipeline. */
		serviceAccount: Option[Schema.ServiceAccount] = None,
	  /** The size of the boot disk, in GB. The boot disk must be large enough to accommodate all of the Docker images from each action in the pipeline at the same time. If not specified, a small but reasonable default value is used. */
		bootDiskSizeGb: Option[Int] = None,
	  /** The CPU platform to request. An instance based on a newer platform can be allocated, but never one with fewer capabilities. The value of this parameter must be a valid Compute Engine CPU platform name (such as "Intel Skylake"). This parameter is only useful for carefully optimized work loads where the CPU platform has a significant impact. For more information about the effect of this parameter, see https://cloud.google.com/compute/docs/instances/specify-min-cpu-platform. */
		cpuPlatform: Option[String] = None,
	  /** The host operating system image to use. Currently, only Container-Optimized OS images can be used. The default value is `projects/cos-cloud/global/images/family/cos-stable`, which selects the latest stable release of Container-Optimized OS. This option is provided to allow testing against the beta release of the operating system to ensure that the new version does not interact negatively with production pipelines. To test a pipeline against the beta release of Container-Optimized OS, use the value `projects/cos-cloud/global/images/family/cos-beta`. */
		bootImage: Option[String] = None,
	  /** The NVIDIA driver version to use when attaching an NVIDIA GPU accelerator. The version specified here must be compatible with the GPU libraries contained in the container being executed, and must be one of the drivers hosted in the `nvidia-drivers-us-public` bucket on Google Cloud Storage. */
		nvidiaDriverVersion: Option[String] = None,
	  /** Whether Stackdriver monitoring should be enabled on the VM. */
		enableStackdriverMonitoring: Option[Boolean] = None,
	  /** The Compute Engine Disk Images to use as a Docker cache. The disks will be mounted into the Docker folder in a way that the images present in the cache will not need to be pulled. The digests of the cached images must match those of the tags used or the latest version will still be pulled. The root directory of the ext4 image must contain `image` and `overlay2` directories copied from the Docker directory of a VM where the desired Docker images have already been pulled. Any images pulled that are not cached will be stored on the first cache disk instead of the boot disk. Only a single image is supported. */
		dockerCacheImages: Option[List[String]] = None,
	  /** The list of disks and other storage to create or attach to the VM. Specify either the `volumes[]` field or the `disks[]` field, but not both. */
		volumes: Option[List[Schema.Volume]] = None,
	  /** If specified, the VM will only be allocated inside the matching reservation. It will fail if the VM parameters don't match the reservation. */
		reservation: Option[String] = None
	)
	
	case class Disk(
	  /** A user-supplied name for the disk. Used when mounting the disk into actions. The name must contain only upper and lowercase alphanumeric characters and hyphens and cannot start with a hyphen. */
		name: Option[String] = None,
	  /** The size, in GB, of the disk to attach. If the size is not specified, a default is chosen to ensure reasonable I/O performance. If the disk type is specified as `local-ssd`, multiple local drives are automatically combined to provide the requested size. Note, however, that each physical SSD is 375GB in size, and no more than 8 drives can be attached to a single instance. */
		sizeGb: Option[Int] = None,
	  /** The Compute Engine disk type. If unspecified, `pd-standard` is used. */
		`type`: Option[String] = None,
	  /** An optional image to put on the disk before attaching it to the VM. */
		sourceImage: Option[String] = None
	)
	
	case class Network(
	  /** The network name to attach the VM's network interface to. The value will be prefixed with `global/networks/` unless it contains a `/`, in which case it is assumed to be a fully specified network resource URL. If unspecified, the global default network is used. */
		network: Option[String] = None,
	  /** If set to true, do not attach a public IP address to the VM. Note that without a public IP address, additional configuration is required to allow the VM to access Google services. See https://cloud.google.com/vpc/docs/configure-private-google-access for more information. */
		usePrivateAddress: Option[Boolean] = None,
	  /** If the specified network is configured for custom subnet creation, the name of the subnetwork to attach the instance to must be specified here. The value is prefixed with `regions/&#42;/subnetworks/` unless it contains a `/`, in which case it is assumed to be a fully specified subnetwork resource URL. If the `&#42;` character appears in the value, it is replaced with the region that the virtual machine has been allocated in. */
		subnetwork: Option[String] = None
	)
	
	case class Accelerator(
	  /** The accelerator type string (for example, "nvidia-tesla-t4"). Only NVIDIA GPU accelerators are currently supported. If an NVIDIA GPU is attached, the required runtime libraries will be made available to all containers under `/usr/local/nvidia`. The driver version to install must be specified using the NVIDIA driver version parameter on the virtual machine specification. Note that attaching a GPU increases the worker VM startup time by a few minutes. */
		`type`: Option[String] = None,
	  /** How many accelerators of this type to attach. */
		count: Option[String] = None
	)
	
	case class ServiceAccount(
	  /** Email address of the service account. If not specified, the default Compute Engine service account for the project will be used. */
		email: Option[String] = None,
	  /** List of scopes to be enabled for this service account on the VM, in addition to the cloud-platform API scope that will be added by default. */
		scopes: Option[List[String]] = None
	)
	
	case class Volume(
	  /** A user-supplied name for the volume. Used when mounting the volume into `Actions`. The name must contain only upper and lowercase alphanumeric characters and hyphens and cannot start with a hyphen. */
		volume: Option[String] = None,
	  /** Configuration for a persistent disk. */
		persistentDisk: Option[Schema.PersistentDisk] = None,
	  /** Configuration for a existing disk. */
		existingDisk: Option[Schema.ExistingDisk] = None,
	  /** Configuration for an NFS mount. */
		nfsMount: Option[Schema.NFSMount] = None
	)
	
	case class PersistentDisk(
	  /** The size, in GB, of the disk to attach. If the size is not specified, a default is chosen to ensure reasonable I/O performance. If the disk type is specified as `local-ssd`, multiple local drives are automatically combined to provide the requested size. Note, however, that each physical SSD is 375GB in size, and no more than 8 drives can be attached to a single instance. */
		sizeGb: Option[Int] = None,
	  /** The Compute Engine disk type. If unspecified, `pd-standard` is used. */
		`type`: Option[String] = None,
	  /** An image to put on the disk before attaching it to the VM. */
		sourceImage: Option[String] = None
	)
	
	case class ExistingDisk(
	  /** If `disk` contains slashes, the Cloud Life Sciences API assumes that it is a complete URL for the disk. If `disk` does not contain slashes, the Cloud Life Sciences API assumes that the disk is a zonal disk and a URL will be generated of the form `zones//disks/`, where `` is the zone in which the instance is allocated. The disk must be ext4 formatted. If all `Mount` references to this disk have the `read_only` flag set to true, the disk will be attached in `read-only` mode and can be shared with other instances. Otherwise, the disk will be available for writing but cannot be shared. */
		disk: Option[String] = None
	)
	
	case class NFSMount(
	  /** A target NFS mount. The target must be specified as `address:/mount". */
		target: Option[String] = None
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
	
	case class ContainerKilledEvent(
	  /** The numeric ID of the action that started the container. */
		actionId: Option[Int] = None
	)
	
	case class ContainerStartedEvent(
	  /** The numeric ID of the action that started this container. */
		actionId: Option[Int] = None,
	  /** The container-to-host port mappings installed for this container. This set will contain any ports exposed using the `PUBLISH_EXPOSED_PORTS` flag as well as any specified in the `Action` definition. */
		portMappings: Option[Map[String, Int]] = None,
	  /** The public IP address that can be used to connect to the container. This field is only populated when at least one port mapping is present. If the instance was created with a private address, this field will be empty even if port mappings exist. */
		ipAddress: Option[String] = None
	)
	
	case class ContainerStoppedEvent(
	  /** The numeric ID of the action that started this container. */
		actionId: Option[Int] = None,
	  /** The exit status of the container. */
		exitStatus: Option[Int] = None,
	  /** The tail end of any content written to standard error by the container. If the content emits large amounts of debugging noise or contains sensitive information, you can prevent the content from being printed by setting the `DISABLE_STANDARD_ERROR_CAPTURE` flag. Note that only a small amount of the end of the stream is captured here. The entire stream is stored in the `/google/logs` directory mounted into each action, and can be copied off the machine as described elsewhere. */
		stderr: Option[String] = None
	)
	
	case class DelayedEvent(
	  /** A textual description of the cause of the delay. The string can change without notice because it is often generated by another service (such as Compute Engine). */
		cause: Option[String] = None,
	  /** If the delay was caused by a resource shortage, this field lists the Compute Engine metrics that are preventing this operation from running (for example, `CPUS` or `INSTANCES`). If the particular metric is not known, a single `UNKNOWN` metric will be present. */
		metrics: Option[List[String]] = None
	)
	
	case class Event(
	  /** The time at which the event occurred. */
		timestamp: Option[String] = None,
	  /** A human-readable description of the event. Note that these strings can change at any time without notice. Any application logic must use the information in the `details` field. */
		description: Option[String] = None,
	  /** See google.cloud.lifesciences.v2beta.DelayedEvent. */
		delayed: Option[Schema.DelayedEvent] = None,
	  /** See google.cloud.lifesciences.v2beta.WorkerAssignedEvent. */
		workerAssigned: Option[Schema.WorkerAssignedEvent] = None,
	  /** See google.cloud.lifesciences.v2beta.WorkerReleasedEvent. */
		workerReleased: Option[Schema.WorkerReleasedEvent] = None,
	  /** See google.cloud.lifesciences.v2beta.PullStartedEvent. */
		pullStarted: Option[Schema.PullStartedEvent] = None,
	  /** See google.cloud.lifesciences.v2beta.PullStoppedEvent. */
		pullStopped: Option[Schema.PullStoppedEvent] = None,
	  /** See google.cloud.lifesciences.v2beta.ContainerStartedEvent. */
		containerStarted: Option[Schema.ContainerStartedEvent] = None,
	  /** See google.cloud.lifesciences.v2beta.ContainerStoppedEvent. */
		containerStopped: Option[Schema.ContainerStoppedEvent] = None,
	  /** See google.cloud.lifesciences.v2beta.ContainerKilledEvent. */
		containerKilled: Option[Schema.ContainerKilledEvent] = None,
	  /** See google.cloud.lifesciences.v2beta.UnexpectedExitStatusEvent. */
		unexpectedExitStatus: Option[Schema.UnexpectedExitStatusEvent] = None,
	  /** See google.cloud.lifesciences.v2beta.FailedEvent. */
		failed: Option[Schema.FailedEvent] = None
	)
	
	case class WorkerAssignedEvent(
	  /** The zone the worker is running in. */
		zone: Option[String] = None,
	  /** The worker's instance name. */
		instance: Option[String] = None,
	  /** The machine type that was assigned for the worker. */
		machineType: Option[String] = None
	)
	
	case class WorkerReleasedEvent(
	  /** The zone the worker was running in. */
		zone: Option[String] = None,
	  /** The worker's instance name. */
		instance: Option[String] = None
	)
	
	case class PullStartedEvent(
	  /** The URI of the image that was pulled. */
		imageUri: Option[String] = None
	)
	
	case class PullStoppedEvent(
	  /** The URI of the image that was pulled. */
		imageUri: Option[String] = None
	)
	
	case class UnexpectedExitStatusEvent(
	  /** The numeric ID of the action that started the container. */
		actionId: Option[Int] = None,
	  /** The exit status of the container. */
		exitStatus: Option[Int] = None
	)
	
	object FailedEvent {
		enum CodeEnum extends Enum[CodeEnum] { case OK, CANCELLED, UNKNOWN, INVALID_ARGUMENT, DEADLINE_EXCEEDED, NOT_FOUND, ALREADY_EXISTS, PERMISSION_DENIED, UNAUTHENTICATED, RESOURCE_EXHAUSTED, FAILED_PRECONDITION, ABORTED, OUT_OF_RANGE, UNIMPLEMENTED, INTERNAL, UNAVAILABLE, DATA_LOSS }
	}
	case class FailedEvent(
	  /** The Google standard error code that best describes this failure. */
		code: Option[Schema.FailedEvent.CodeEnum] = None,
	  /** The human-readable description of the cause of the failure. */
		cause: Option[String] = None
	)
	
	case class Metadata(
	  /** The pipeline this operation represents. */
		pipeline: Option[Schema.Pipeline] = None,
	  /** The user-defined labels associated with this operation. */
		labels: Option[Map[String, String]] = None,
	  /** The list of events that have happened so far during the execution of this operation. */
		events: Option[List[Schema.Event]] = None,
	  /** The time at which the operation was created by the API. */
		createTime: Option[String] = None,
	  /** The first time at which resources were allocated to execute the pipeline. */
		startTime: Option[String] = None,
	  /** The time at which execution was completed and resources were cleaned up. */
		endTime: Option[String] = None,
	  /** The name of the Cloud Pub/Sub topic where notifications of operation status changes are sent. */
		pubSubTopic: Option[String] = None
	)
	
	case class RunPipelineResponse(
	
	)
}
