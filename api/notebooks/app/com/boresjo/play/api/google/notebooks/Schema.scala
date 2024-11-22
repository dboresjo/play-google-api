package com.boresjo.play.api.google.notebooks

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
	
	case class CancelOperationRequest(
	
	)
	
	case class ListInstancesResponse(
	  /** A list of returned instances. */
		instances: Option[List[Schema.Instance]] = None,
	  /** Page token that can be used to continue listing from the last result in the next list call. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. For example, ['us-west1-a', 'us-central1-b']. A ListInstancesResponse will only contain either instances or unreachables, */
		unreachable: Option[List[String]] = None
	)
	
	object Instance {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STARTING, PROVISIONING, ACTIVE, STOPPING, STOPPED, DELETED, UPGRADING, INITIALIZING, SUSPENDING, SUSPENDED }
		enum HealthStateEnum extends Enum[HealthStateEnum] { case HEALTH_STATE_UNSPECIFIED, HEALTHY, UNHEALTHY, AGENT_NOT_INSTALLED, AGENT_NOT_RUNNING }
	}
	case class Instance(
	  /** Output only. The name of this notebook instance. Format: `projects/{project_id}/locations/{location}/instances/{instance_id}` */
		name: Option[String] = None,
	  /** Optional. Compute Engine setup for the notebook. Uses notebook-defined fields. */
		gceSetup: Option[Schema.GceSetup] = None,
	  /** Output only. The proxy endpoint that is used to access the Jupyter notebook. */
		proxyUri: Option[String] = None,
	  /** Optional. Input only. The owner of this instance after creation. Format: `alias@example.com` Currently supports one owner only. If not specified, all of the service account users of your VM instance's service account can use the instance. */
		instanceOwners: Option[List[String]] = None,
	  /** Output only. Email address of entity that sent original CreateInstance request. */
		creator: Option[String] = None,
	  /** Output only. The state of this instance. */
		state: Option[Schema.Instance.StateEnum] = None,
	  /** Output only. The upgrade history of this instance. */
		upgradeHistory: Option[List[Schema.UpgradeHistoryEntry]] = None,
	  /** Output only. Unique ID of the resource. */
		id: Option[String] = None,
	  /** Output only. Instance health_state. */
		healthState: Option[Schema.Instance.HealthStateEnum] = None,
	  /** Output only. Additional information about instance health. Example: healthInfo": { "docker_proxy_agent_status": "1", "docker_status": "1", "jupyterlab_api_status": "-1", "jupyterlab_status": "-1", "updated": "2020-10-18 09:40:03.573409" } */
		healthInfo: Option[Map[String, String]] = None,
	  /** Output only. Instance creation time. */
		createTime: Option[String] = None,
	  /** Output only. Instance update time. */
		updateTime: Option[String] = None,
	  /** Optional. If true, the notebook instance will not register with the proxy. */
		disableProxyAccess: Option[Boolean] = None,
	  /** Optional. Labels to apply to this instance. These can be later modified by the UpdateInstance method. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The workforce pools proxy endpoint that is used to access the Jupyter notebook. */
		thirdPartyProxyUrl: Option[String] = None,
	  /** Output only. Reserved for future use for Zone Separation. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Output only. Reserved for future use for Zone Isolation. */
		satisfiesPzi: Option[Boolean] = None
	)
	
	case class GceSetup(
	  /** Optional. The machine type of the VM instance. https://cloud.google.com/compute/docs/machine-resource */
		machineType: Option[String] = None,
	  /** Optional. The minimum CPU platform to use for this instance. The list of valid values can be found in https://cloud.google.com/compute/docs/instances/specify-min-cpu-platform#availablezones */
		minCpuPlatform: Option[String] = None,
	  /** Optional. The hardware accelerators used on this instance. If you use accelerators, make sure that your configuration has [enough vCPUs and memory to support the `machine_type` you have selected](https://cloud.google.com/compute/docs/gpus/#gpus-list). Currently supports only one accelerator configuration. */
		acceleratorConfigs: Option[List[Schema.AcceleratorConfig]] = None,
	  /** Optional. The service account that serves as an identity for the VM instance. Currently supports only one service account. */
		serviceAccounts: Option[List[Schema.ServiceAccount]] = None,
	  /** Optional. Use a Compute Engine VM image to start the notebook instance. */
		vmImage: Option[Schema.VmImage] = None,
	  /** Optional. Use a container image to start the notebook instance. */
		containerImage: Option[Schema.ContainerImage] = None,
	  /** Optional. The boot disk for the VM. */
		bootDisk: Option[Schema.BootDisk] = None,
	  /** Optional. Data disks attached to the VM instance. Currently supports only one data disk. */
		dataDisks: Option[List[Schema.DataDisk]] = None,
	  /** Optional. Shielded VM configuration. [Images using supported Shielded VM features](https://cloud.google.com/compute/docs/instances/modifying-shielded-vm). */
		shieldedInstanceConfig: Option[Schema.ShieldedInstanceConfig] = None,
	  /** Optional. The network interfaces for the VM. Supports only one interface. */
		networkInterfaces: Option[List[Schema.NetworkInterface]] = None,
	  /** Optional. If true, no external IP will be assigned to this VM instance. */
		disablePublicIp: Option[Boolean] = None,
	  /** Optional. The Compute Engine network tags to add to runtime (see [Add network tags](https://cloud.google.com/vpc/docs/add-remove-network-tags)). */
		tags: Option[List[String]] = None,
	  /** Optional. Custom metadata to apply to this instance. */
		metadata: Option[Map[String, String]] = None,
	  /** Optional. Flag to enable ip forwarding or not, default false/off. https://cloud.google.com/vpc/docs/using-routes#canipforward */
		enableIpForwarding: Option[Boolean] = None,
	  /** Optional. Configuration for GPU drivers. */
		gpuDriverConfig: Option[Schema.GPUDriverConfig] = None
	)
	
	object AcceleratorConfig {
		enum TypeEnum extends Enum[TypeEnum] { case ACCELERATOR_TYPE_UNSPECIFIED, NVIDIA_TESLA_P100, NVIDIA_TESLA_V100, NVIDIA_TESLA_P4, NVIDIA_TESLA_T4, NVIDIA_TESLA_A100, NVIDIA_A100_80GB, NVIDIA_L4, NVIDIA_TESLA_T4_VWS, NVIDIA_TESLA_P100_VWS, NVIDIA_TESLA_P4_VWS }
	}
	case class AcceleratorConfig(
	  /** Optional. Type of this accelerator. */
		`type`: Option[Schema.AcceleratorConfig.TypeEnum] = None,
	  /** Optional. Count of cores of this accelerator. */
		coreCount: Option[String] = None
	)
	
	case class ServiceAccount(
	  /** Optional. Email address of the service account. */
		email: Option[String] = None,
	  /** Output only. The list of scopes to be made available for this service account. Set by the CLH to https://www.googleapis.com/auth/cloud-platform */
		scopes: Option[List[String]] = None
	)
	
	case class VmImage(
	  /** Required. The name of the Google Cloud project that this VM image belongs to. Format: `{project_id}` */
		project: Option[String] = None,
	  /** Optional. Use VM image name to find the image. */
		name: Option[String] = None,
	  /** Optional. Use this VM image family to find the image; the newest image in this family will be used. */
		family: Option[String] = None
	)
	
	case class ContainerImage(
	  /** Required. The path to the container image repository. For example: `gcr.io/{project_id}/{image_name}` */
		repository: Option[String] = None,
	  /** Optional. The tag of the container image. If not specified, this defaults to the latest tag. */
		tag: Option[String] = None
	)
	
	object BootDisk {
		enum DiskTypeEnum extends Enum[DiskTypeEnum] { case DISK_TYPE_UNSPECIFIED, PD_STANDARD, PD_SSD, PD_BALANCED, PD_EXTREME }
		enum DiskEncryptionEnum extends Enum[DiskEncryptionEnum] { case DISK_ENCRYPTION_UNSPECIFIED, GMEK, CMEK }
	}
	case class BootDisk(
	  /** Optional. The size of the boot disk in GB attached to this instance, up to a maximum of 64000 GB (64 TB). If not specified, this defaults to the recommended value of 150GB. */
		diskSizeGb: Option[String] = None,
	  /** Optional. Indicates the type of the disk. */
		diskType: Option[Schema.BootDisk.DiskTypeEnum] = None,
	  /** Optional. Input only. Disk encryption method used on the boot and data disks, defaults to GMEK. */
		diskEncryption: Option[Schema.BootDisk.DiskEncryptionEnum] = None,
	  /** Optional. Input only. The KMS key used to encrypt the disks, only applicable if disk_encryption is CMEK. Format: `projects/{project_id}/locations/{location}/keyRings/{key_ring_id}/cryptoKeys/{key_id}` Learn more about using your own encryption keys. */
		kmsKey: Option[String] = None
	)
	
	object DataDisk {
		enum DiskTypeEnum extends Enum[DiskTypeEnum] { case DISK_TYPE_UNSPECIFIED, PD_STANDARD, PD_SSD, PD_BALANCED, PD_EXTREME }
		enum DiskEncryptionEnum extends Enum[DiskEncryptionEnum] { case DISK_ENCRYPTION_UNSPECIFIED, GMEK, CMEK }
	}
	case class DataDisk(
	  /** Optional. The size of the disk in GB attached to this VM instance, up to a maximum of 64000 GB (64 TB). If not specified, this defaults to 100. */
		diskSizeGb: Option[String] = None,
	  /** Optional. Input only. Indicates the type of the disk. */
		diskType: Option[Schema.DataDisk.DiskTypeEnum] = None,
	  /** Optional. Input only. Disk encryption method used on the boot and data disks, defaults to GMEK. */
		diskEncryption: Option[Schema.DataDisk.DiskEncryptionEnum] = None,
	  /** Optional. Input only. The KMS key used to encrypt the disks, only applicable if disk_encryption is CMEK. Format: `projects/{project_id}/locations/{location}/keyRings/{key_ring_id}/cryptoKeys/{key_id}` Learn more about using your own encryption keys. */
		kmsKey: Option[String] = None
	)
	
	case class ShieldedInstanceConfig(
	  /** Optional. Defines whether the VM instance has Secure Boot enabled. Secure Boot helps ensure that the system only runs authentic software by verifying the digital signature of all boot components, and halting the boot process if signature verification fails. Disabled by default. */
		enableSecureBoot: Option[Boolean] = None,
	  /** Optional. Defines whether the VM instance has the vTPM enabled. Enabled by default. */
		enableVtpm: Option[Boolean] = None,
	  /** Optional. Defines whether the VM instance has integrity monitoring enabled. Enables monitoring and attestation of the boot integrity of the VM instance. The attestation is performed against the integrity policy baseline. This baseline is initially derived from the implicitly trusted boot image when the VM instance is created. Enabled by default. */
		enableIntegrityMonitoring: Option[Boolean] = None
	)
	
	object NetworkInterface {
		enum NicTypeEnum extends Enum[NicTypeEnum] { case NIC_TYPE_UNSPECIFIED, VIRTIO_NET, GVNIC }
	}
	case class NetworkInterface(
	  /** Optional. The name of the VPC that this VM instance is in. Format: `projects/{project_id}/global/networks/{network_id}` */
		network: Option[String] = None,
	  /** Optional. The name of the subnet that this VM instance is in. Format: `projects/{project_id}/regions/{region}/subnetworks/{subnetwork_id}` */
		subnet: Option[String] = None,
	  /** Optional. The type of vNIC to be used on this interface. This may be gVNIC or VirtioNet. */
		nicType: Option[Schema.NetworkInterface.NicTypeEnum] = None,
	  /** Optional. An array of configurations for this interface. Currently, only one access config, ONE_TO_ONE_NAT, is supported. If no accessConfigs specified, the instance will have an external internet access through an ephemeral external IP address. */
		accessConfigs: Option[List[Schema.AccessConfig]] = None
	)
	
	case class AccessConfig(
	  /** An external IP address associated with this instance. Specify an unused static external IP address available to the project or leave this field undefined to use an IP from a shared ephemeral IP address pool. If you specify a static external IP address, it must live in the same region as the zone of the instance. */
		externalIp: Option[String] = None
	)
	
	case class GPUDriverConfig(
	  /** Optional. Whether the end user authorizes Google Cloud to install GPU driver on this VM instance. If this field is empty or set to false, the GPU driver won't be installed. Only applicable to instances with GPUs. */
		enableGpuDriver: Option[Boolean] = None,
	  /** Optional. Specify a custom Cloud Storage path where the GPU driver is stored. If not specified, we'll automatically choose from official GPU drivers. */
		customGpuDriverPath: Option[String] = None
	)
	
	object UpgradeHistoryEntry {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STARTED, SUCCEEDED, FAILED }
		enum ActionEnum extends Enum[ActionEnum] { case ACTION_UNSPECIFIED, UPGRADE, ROLLBACK }
	}
	case class UpgradeHistoryEntry(
	  /** Optional. The snapshot of the boot disk of this notebook instance before upgrade. */
		snapshot: Option[String] = None,
	  /** Optional. The VM image before this instance upgrade. */
		vmImage: Option[String] = None,
	  /** Optional. The container image before this instance upgrade. */
		containerImage: Option[String] = None,
	  /** Optional. The framework of this notebook instance. */
		framework: Option[String] = None,
	  /** Optional. The version of the notebook instance before this upgrade. */
		version: Option[String] = None,
	  /** Output only. The state of this instance upgrade history entry. */
		state: Option[Schema.UpgradeHistoryEntry.StateEnum] = None,
	  /** Immutable. The time that this instance upgrade history entry is created. */
		createTime: Option[String] = None,
	  /** Optional. Action. Rolloback or Upgrade. */
		action: Option[Schema.UpgradeHistoryEntry.ActionEnum] = None,
	  /** Optional. Target VM Version, like m63. */
		targetVersion: Option[String] = None
	)
	
	case class StartInstanceRequest(
	
	)
	
	case class StopInstanceRequest(
	
	)
	
	case class ResetInstanceRequest(
	
	)
	
	case class CheckInstanceUpgradabilityResponse(
	  /** If an instance is upgradeable. */
		upgradeable: Option[Boolean] = None,
	  /** The version this instance will be upgraded to if calling the upgrade endpoint. This field will only be populated if field upgradeable is true. */
		upgradeVersion: Option[String] = None,
	  /** Additional information about upgrade. */
		upgradeInfo: Option[String] = None,
	  /** The new image self link this instance will be upgraded to if calling the upgrade endpoint. This field will only be populated if field upgradeable is true. */
		upgradeImage: Option[String] = None
	)
	
	case class UpgradeInstanceRequest(
	
	)
	
	case class ResizeDiskRequest(
	  /** Required. The boot disk to be resized. Only disk_size_gb will be used. */
		bootDisk: Option[Schema.BootDisk] = None,
	  /** Required. The data disk to be resized. Only disk_size_gb will be used. */
		dataDisk: Option[Schema.DataDisk] = None
	)
	
	case class RollbackInstanceRequest(
	  /** Required. The snapshot for rollback. Example: "projects/test-project/global/snapshots/krwlzipynril". */
		targetSnapshot: Option[String] = None,
	  /** Required. Output only. Revision Id */
		revisionId: Option[String] = None
	)
	
	case class DiagnoseInstanceRequest(
	  /** Required. Defines flags that are used to run the diagnostic tool */
		diagnosticConfig: Option[Schema.DiagnosticConfig] = None,
	  /** Optional. Maximum amount of time in minutes before the operation times out. */
		timeoutMinutes: Option[Int] = None
	)
	
	case class DiagnosticConfig(
	  /** Required. User Cloud Storage bucket location (REQUIRED). Must be formatted with path prefix (`gs://$GCS_BUCKET`). Permissions: User Managed Notebooks: - storage.buckets.writer: Must be given to the project's service account attached to VM. Google Managed Notebooks: - storage.buckets.writer: Must be given to the project's service account or user credentials attached to VM depending on authentication mode. Cloud Storage bucket Log file will be written to `gs://$GCS_BUCKET/$RELATIVE_PATH/$VM_DATE_$TIME.tar.gz` */
		gcsBucket: Option[String] = None,
	  /** Optional. Defines the relative storage path in the Cloud Storage bucket where the diagnostic logs will be written: Default path will be the root directory of the Cloud Storage bucket (`gs://$GCS_BUCKET/$DATE_$TIME.tar.gz`) Example of full path where Log file will be written: `gs://$GCS_BUCKET/$RELATIVE_PATH/` */
		relativePath: Option[String] = None,
	  /** Optional. Enables flag to repair service for instance */
		enableRepairFlag: Option[Boolean] = None,
	  /** Optional. Enables flag to capture packets from the instance for 30 seconds */
		enablePacketCaptureFlag: Option[Boolean] = None,
	  /** Optional. Enables flag to copy all `/home/jupyter` folder contents */
		enableCopyHomeFilesFlag: Option[Boolean] = None
	)
	
	case class Config(
	  /** Output only. The default values for configuration. */
		defaultValues: Option[Schema.DefaultValues] = None,
	  /** Output only. The supported values for configuration. */
		supportedValues: Option[Schema.SupportedValues] = None,
	  /** Output only. The list of available images to create a WbI. */
		availableImages: Option[List[Schema.ImageRelease]] = None
	)
	
	case class DefaultValues(
	  /** Output only. The default machine type used by the backend if not provided by the user. */
		machineType: Option[String] = None
	)
	
	case class SupportedValues(
	  /** Output only. The machine types supported by WbI. */
		machineTypes: Option[List[String]] = None,
	  /** Output only. The accelerator types supported by WbI. */
		acceleratorTypes: Option[List[String]] = None
	)
	
	case class ImageRelease(
	  /** Output only. The name of the image of the form workbench-instances-vYYYYmmdd-- */
		imageName: Option[String] = None,
	  /** Output only. The release of the image of the form m123 */
		releaseName: Option[String] = None
	)
	
	case class RestoreInstanceRequest(
	  /** Snapshot to be used for restore. */
		snapshot: Option[Schema.Snapshot] = None
	)
	
	case class Snapshot(
	  /** Required. The ID of the snapshot. */
		snapshotId: Option[String] = None,
	  /** Required. The project ID of the snapshot. */
		projectId: Option[String] = None
	)
	
	case class ReportInstanceInfoSystemRequest(
	  /** Required. The VM hardware token for authenticating the VM. https://cloud.google.com/compute/docs/instances/verifying-instance-identity */
		vmId: Option[String] = None,
	  /** Required. The Event to be reported. */
		event: Option[Schema.Event] = None
	)
	
	object Event {
		enum TypeEnum extends Enum[TypeEnum] { case EVENT_TYPE_UNSPECIFIED, IDLE, HEARTBEAT, HEALTH, MAINTENANCE, METADATA_CHANGE }
	}
	case class Event(
	  /** Optional. Event report time. */
		reportTime: Option[String] = None,
	  /** Optional. Event type. */
		`type`: Option[Schema.Event.TypeEnum] = None,
	  /** Optional. Event details. This field is used to pass event information. */
		details: Option[Map[String, String]] = None
	)
	
	case class UpgradeInstanceSystemRequest(
	  /** Required. The VM hardware token for authenticating the VM. https://cloud.google.com/compute/docs/instances/verifying-instance-identity */
		vmId: Option[String] = None
	)
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None
	)
	
	case class Policy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.Binding]] = None,
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
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
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
	  /** Human-readable status of the operation, if any. */
		statusMessage: Option[String] = None,
	  /** Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have google.longrunning.Operation.error value with a google.rpc.Status.code of `1`, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** API version used to start the operation. */
		apiVersion: Option[String] = None,
	  /** API endpoint name of this operation. */
		endpoint: Option[String] = None
	)
}
