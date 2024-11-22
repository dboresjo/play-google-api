package com.boresjo.play.api.google.file

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
	
	case class Empty(
	
	)
	
	case class CancelOperationRequest(
	
	)
	
	case class ListInstancesResponse(
	  /** A list of instances in the project for the specified location. If the `{location}` value in the request is "-", the response contains a list of instances from all locations. If any location is unreachable, the response will only return instances in reachable locations and the "unreachable" field will be populated with a list of unreachable locations. */
		instances: Option[List[Schema.Instance]] = None,
	  /** The token you can use to retrieve the next page of results. Not returned if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Instance {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY, REPAIRING, DELETING, ERROR, RESTORING, SUSPENDED, SUSPENDING, RESUMING, REVERTING, PROMOTING }
		enum TierEnum extends Enum[TierEnum] { case TIER_UNSPECIFIED, STANDARD, PREMIUM, BASIC_HDD, BASIC_SSD, HIGH_SCALE_SSD, ENTERPRISE, ZONAL, REGIONAL }
		enum SuspensionReasonsEnum extends Enum[SuspensionReasonsEnum] { case SUSPENSION_REASON_UNSPECIFIED, KMS_KEY_ISSUE }
		enum ProtocolEnum extends Enum[ProtocolEnum] { case FILE_PROTOCOL_UNSPECIFIED, NFS_V3, NFS_V4_1 }
	}
	case class Instance(
	  /** Output only. The resource name of the instance, in the format `projects/{project}/locations/{location}/instances/{instance}`. */
		name: Option[String] = None,
	  /** The description of the instance (2048 characters or less). */
		description: Option[String] = None,
	  /** Output only. The instance state. */
		state: Option[Schema.Instance.StateEnum] = None,
	  /** Output only. Additional information about the instance state, if available. */
		statusMessage: Option[String] = None,
	  /** Output only. The time when the instance was created. */
		createTime: Option[String] = None,
	  /** The service tier of the instance. */
		tier: Option[Schema.Instance.TierEnum] = None,
	  /** Resource labels to represent user provided metadata. */
		labels: Option[Map[String, String]] = None,
	  /** File system shares on the instance. For this version, only a single file share is supported. */
		fileShares: Option[List[Schema.FileShareConfig]] = None,
	  /** VPC networks to which the instance is connected. For this version, only a single network is supported. */
		networks: Option[List[Schema.NetworkConfig]] = None,
	  /** Server-specified ETag for the instance resource to prevent simultaneous updates from overwriting each other. */
		etag: Option[String] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzi: Option[Boolean] = None,
	  /** KMS key name used for data encryption. */
		kmsKeyName: Option[String] = None,
	  /** Output only. Field indicates all the reasons the instance is in "SUSPENDED" state. */
		suspensionReasons: Option[List[Schema.Instance.SuspensionReasonsEnum]] = None,
	  /** Optional. Replication configuration. */
		replication: Option[Schema.Replication] = None,
	  /** Optional. Input only. Immutable. Tag key-value pairs are bound to this resource. For example: "123/environment": "production", "123/costCenter": "marketing" */
		tags: Option[Map[String, String]] = None,
	  /** Immutable. The protocol indicates the access protocol for all shares in the instance. This field is immutable and it cannot be changed after the instance has been created. Default value: `NFS_V3`. */
		protocol: Option[Schema.Instance.ProtocolEnum] = None,
	  /** Output only. Indicates whether this instance's performance is configurable. If enabled, adjust it using the 'performance_config' field. */
		configurablePerformanceEnabled: Option[Boolean] = None,
	  /** Optional. Used to configure performance. */
		performanceConfig: Option[Schema.PerformanceConfig] = None,
	  /** Output only. Used for getting performance limits. */
		performanceLimits: Option[Schema.PerformanceLimits] = None,
	  /** Optional. Indicates whether the instance is protected against deletion. */
		deletionProtectionEnabled: Option[Boolean] = None,
	  /** Optional. The reason for enabling deletion protection. */
		deletionProtectionReason: Option[String] = None
	)
	
	case class FileShareConfig(
	  /** Required. The name of the file share. Must use 1-16 characters for the basic service tier and 1-63 characters for all other service tiers. Must use lowercase letters, numbers, or underscores `[a-z0-9_]`. Must start with a letter. Immutable. */
		name: Option[String] = None,
	  /** File share capacity in gigabytes (GB). Filestore defines 1 GB as 1024^3 bytes. */
		capacityGb: Option[String] = None,
	  /** The resource name of the backup, in the format `projects/{project_number}/locations/{location_id}/backups/{backup_id}`, that this file share has been restored from. */
		sourceBackup: Option[String] = None,
	  /** Nfs Export Options. There is a limit of 10 export options per file share. */
		nfsExportOptions: Option[List[Schema.NfsExportOptions]] = None
	)
	
	object NfsExportOptions {
		enum AccessModeEnum extends Enum[AccessModeEnum] { case ACCESS_MODE_UNSPECIFIED, READ_ONLY, READ_WRITE }
		enum SquashModeEnum extends Enum[SquashModeEnum] { case SQUASH_MODE_UNSPECIFIED, NO_ROOT_SQUASH, ROOT_SQUASH }
	}
	case class NfsExportOptions(
	  /** List of either an IPv4 addresses in the format `{octet1}.{octet2}.{octet3}.{octet4}` or CIDR ranges in the format `{octet1}.{octet2}.{octet3}.{octet4}/{mask size}` which may mount the file share. Overlapping IP ranges are not allowed, both within and across NfsExportOptions. An error will be returned. The limit is 64 IP ranges/addresses for each FileShareConfig among all NfsExportOptions. */
		ipRanges: Option[List[String]] = None,
	  /** Either READ_ONLY, for allowing only read requests on the exported directory, or READ_WRITE, for allowing both read and write requests. The default is READ_WRITE. */
		accessMode: Option[Schema.NfsExportOptions.AccessModeEnum] = None,
	  /** Either NO_ROOT_SQUASH, for allowing root access on the exported directory, or ROOT_SQUASH, for not allowing root access. The default is NO_ROOT_SQUASH. */
		squashMode: Option[Schema.NfsExportOptions.SquashModeEnum] = None,
	  /** An integer representing the anonymous user id with a default value of 65534. Anon_uid may only be set with squash_mode of ROOT_SQUASH. An error will be returned if this field is specified for other squash_mode settings. */
		anonUid: Option[String] = None,
	  /** An integer representing the anonymous group id with a default value of 65534. Anon_gid may only be set with squash_mode of ROOT_SQUASH. An error will be returned if this field is specified for other squash_mode settings. */
		anonGid: Option[String] = None
	)
	
	object NetworkConfig {
		enum ModesEnum extends Enum[ModesEnum] { case ADDRESS_MODE_UNSPECIFIED, MODE_IPV4 }
		enum ConnectModeEnum extends Enum[ConnectModeEnum] { case CONNECT_MODE_UNSPECIFIED, DIRECT_PEERING, PRIVATE_SERVICE_ACCESS }
	}
	case class NetworkConfig(
	  /** The name of the Google Compute Engine [VPC network](https://cloud.google.com/vpc/docs/vpc) to which the instance is connected. */
		network: Option[String] = None,
	  /** Internet protocol versions for which the instance has IP addresses assigned. For this version, only MODE_IPV4 is supported. */
		modes: Option[List[Schema.NetworkConfig.ModesEnum]] = None,
	  /** Optional, reserved_ip_range can have one of the following two types of values. &#42; CIDR range value when using DIRECT_PEERING connect mode. &#42; [Allocated IP address range](https://cloud.google.com/compute/docs/ip-addresses/reserve-static-internal-ip-address) when using PRIVATE_SERVICE_ACCESS connect mode. When the name of an allocated IP address range is specified, it must be one of the ranges associated with the private service access connection. When specified as a direct CIDR value, it must be a /29 CIDR block for Basic tier, a /24 CIDR block for High Scale tier, or a /26 CIDR block for Enterprise tier in one of the [internal IP address ranges](https://www.arin.net/reference/research/statistics/address_filters/) that identifies the range of IP addresses reserved for this instance. For example, 10.0.0.0/29, 192.168.0.0/24 or 192.168.0.0/26, respectively. The range you specify can't overlap with either existing subnets or assigned IP address ranges for other Filestore instances in the selected VPC network. */
		reservedIpRange: Option[String] = None,
	  /** Output only. IPv4 addresses in the format `{octet1}.{octet2}.{octet3}.{octet4}` or IPv6 addresses in the format `{block1}:{block2}:{block3}:{block4}:{block5}:{block6}:{block7}:{block8}`. */
		ipAddresses: Option[List[String]] = None,
	  /** The network connect mode of the Filestore instance. If not provided, the connect mode defaults to DIRECT_PEERING. */
		connectMode: Option[Schema.NetworkConfig.ConnectModeEnum] = None
	)
	
	object Replication {
		enum RoleEnum extends Enum[RoleEnum] { case ROLE_UNSPECIFIED, ACTIVE, STANDBY }
	}
	case class Replication(
	  /** Optional. The replication role. */
		role: Option[Schema.Replication.RoleEnum] = None,
	  /** Optional. Replication configuration for the replica instance associated with this instance. Only a single replica is supported. */
		replicas: Option[List[Schema.ReplicaConfig]] = None
	)
	
	object ReplicaConfig {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY, REMOVING, FAILED }
		enum StateReasonsEnum extends Enum[StateReasonsEnum] { case STATE_REASON_UNSPECIFIED, PEER_INSTANCE_UNREACHABLE }
	}
	case class ReplicaConfig(
	  /** Output only. The replica state. */
		state: Option[Schema.ReplicaConfig.StateEnum] = None,
	  /** Output only. Additional information about the replication state, if available. */
		stateReasons: Option[List[Schema.ReplicaConfig.StateReasonsEnum]] = None,
	  /** Optional. The peer instance. */
		peerInstance: Option[String] = None,
	  /** Output only. The timestamp of the latest replication snapshot taken on the active instance and is already replicated safely. */
		lastActiveSyncTime: Option[String] = None
	)
	
	case class PerformanceConfig(
	  /** Provision IOPS dynamically based on the capacity of the instance. Provisioned read IOPS will be calculated by multiplying the capacity of the instance in TiB by the `iops_per_tb` value. For example, for a 2 TiB instance with an `iops_per_tb` value of 17000 the provisioned read IOPS will be 34000. If the calculated value is outside the supported range for the instance's capacity during instance creation, instance creation will fail with an `InvalidArgument` error. Similarly, if an instance capacity update would result in a value outside the supported range, the update will fail with an `InvalidArgument` error. */
		iopsPerTb: Option[Schema.IOPSPerTB] = None,
	  /** Choose a fixed provisioned IOPS value for the instance, which will remain constant regardless of instance capacity. Value must be a multiple of 1000. If the chosen value is outside the supported range for the instance's capacity during instance creation, instance creation will fail with an `InvalidArgument` error. Similarly, if an instance capacity update would result in a value outside the supported range, the update will fail with an `InvalidArgument` error. */
		fixedIops: Option[Schema.FixedIOPS] = None
	)
	
	case class IOPSPerTB(
	  /** Optional. Deprecated: `max_iops_per_tb` should be used instead of this parameter. Maximum read IOPS per TiB. */
		maxReadIopsPerTb: Option[String] = None,
	  /** Required. Maximum IOPS per TiB. */
		maxIopsPerTb: Option[String] = None
	)
	
	case class FixedIOPS(
	  /** Optional. Deprecated: `max_iops` should be used instead of this parameter. Maximum read IOPS. */
		maxReadIops: Option[String] = None,
	  /** Required. Maximum IOPS. */
		maxIops: Option[String] = None
	)
	
	case class PerformanceLimits(
	  /** Output only. The max read IOPS. */
		maxReadIops: Option[String] = None,
	  /** Output only. The max write IOPS. */
		maxWriteIops: Option[String] = None,
	  /** Output only. The max read throughput in bytes per second. */
		maxReadThroughputBps: Option[String] = None,
	  /** Output only. The max write throughput in bytes per second. */
		maxWriteThroughputBps: Option[String] = None
	)
	
	case class RestoreInstanceRequest(
	  /** Required. Name of the file share in the Filestore instance that the backup is being restored to. */
		fileShare: Option[String] = None,
	  /** The resource name of the backup, in the format `projects/{project_number}/locations/{location_id}/backups/{backup_id}`. */
		sourceBackup: Option[String] = None
	)
	
	case class RevertInstanceRequest(
	  /** Required. The snapshot resource ID, in the format 'my-snapshot', where the specified ID is the {snapshot_id} of the fully qualified name like `projects/{project_id}/locations/{location_id}/instances/{instance_id}/snapshots/{snapshot_id}` */
		targetSnapshotId: Option[String] = None
	)
	
	case class ListSnapshotsResponse(
	  /** A list of snapshots in the project for the specified instance. */
		snapshots: Option[List[Schema.Snapshot]] = None,
	  /** The token you can use to retrieve the next page of results. Not returned if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	object Snapshot {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY, DELETING }
	}
	case class Snapshot(
	  /** Output only. The resource name of the snapshot, in the format `projects/{project_id}/locations/{location_id}/instances/{instance_id}/snapshots/{snapshot_id}`. */
		name: Option[String] = None,
	  /** A description of the snapshot with 2048 characters or less. Requests with longer descriptions will be rejected. */
		description: Option[String] = None,
	  /** Output only. The snapshot state. */
		state: Option[Schema.Snapshot.StateEnum] = None,
	  /** Output only. The time when the snapshot was created. */
		createTime: Option[String] = None,
	  /** Resource labels to represent user provided metadata. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The amount of bytes needed to allocate a full copy of the snapshot content */
		filesystemUsedBytes: Option[String] = None,
	  /** Optional. Input only. Immutable. Tag key-value pairs are bound to this resource. For example: "123/environment": "production", "123/costCenter": "marketing" */
		tags: Option[Map[String, String]] = None
	)
	
	case class ListBackupsResponse(
	  /** A list of backups in the project for the specified location. If the `{location}` value in the request is "-", the response contains a list of backups from all locations. If any location is unreachable, the response will only return backups in reachable locations and the "unreachable" field will be populated with a list of unreachable locations. */
		backups: Option[List[Schema.Backup]] = None,
	  /** The token you can use to retrieve the next page of results. Not returned if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Backup {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, FINALIZING, READY, DELETING, INVALID }
		enum SourceInstanceTierEnum extends Enum[SourceInstanceTierEnum] { case TIER_UNSPECIFIED, STANDARD, PREMIUM, BASIC_HDD, BASIC_SSD, HIGH_SCALE_SSD, ENTERPRISE, ZONAL, REGIONAL }
		enum FileSystemProtocolEnum extends Enum[FileSystemProtocolEnum] { case FILE_PROTOCOL_UNSPECIFIED, NFS_V3, NFS_V4_1 }
	}
	case class Backup(
	  /** Output only. The resource name of the backup, in the format `projects/{project_number}/locations/{location_id}/backups/{backup_id}`. */
		name: Option[String] = None,
	  /** A description of the backup with 2048 characters or less. Requests with longer descriptions will be rejected. */
		description: Option[String] = None,
	  /** Output only. The backup state. */
		state: Option[Schema.Backup.StateEnum] = None,
	  /** Output only. The time when the backup was created. */
		createTime: Option[String] = None,
	  /** Resource labels to represent user provided metadata. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Capacity of the source file share when the backup was created. */
		capacityGb: Option[String] = None,
	  /** Output only. The size of the storage used by the backup. As backups share storage, this number is expected to change with backup creation/deletion. */
		storageBytes: Option[String] = None,
	  /** The resource name of the source Filestore instance, in the format `projects/{project_number}/locations/{location_id}/instances/{instance_id}`, used to create this backup. */
		sourceInstance: Option[String] = None,
	  /** Name of the file share in the source Filestore instance that the backup is created from. */
		sourceFileShare: Option[String] = None,
	  /** Output only. The service tier of the source Filestore instance that this backup is created from. */
		sourceInstanceTier: Option[Schema.Backup.SourceInstanceTierEnum] = None,
	  /** Output only. Amount of bytes that will be downloaded if the backup is restored. This may be different than storage bytes, since sequential backups of the same disk will share storage. */
		downloadBytes: Option[String] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzi: Option[Boolean] = None,
	  /** Immutable. KMS key name used for data encryption. */
		kmsKey: Option[String] = None,
	  /** Optional. Input only. Immutable. Tag key-value pairs are bound to this resource. For example: "123/environment": "production", "123/costCenter": "marketing" */
		tags: Option[Map[String, String]] = None,
	  /** Output only. The file system protocol of the source Filestore instance that this backup is created from. */
		fileSystemProtocol: Option[Schema.Backup.FileSystemProtocolEnum] = None
	)
	
	case class PromoteReplicaRequest(
	
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
	
	case class OperationMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusDetail: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have been cancelled successfully have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		cancelRequested: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	object GoogleCloudSaasacceleratorManagementProvidersV1Instance {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY, UPDATING, REPAIRING, DELETING, ERROR }
	}
	case class GoogleCloudSaasacceleratorManagementProvidersV1Instance(
	  /** Unique name of the resource. It uses the form: `projects/{project_number}/locations/{location_id}/instances/{instance_id}` Note: This name is passed, stored and logged across the rollout system. So use of consumer project_id or any other consumer PII in the name is strongly discouraged for wipeout (go/wipeout) compliance. See go/elysium/project_ids#storage-guidance for more details. */
		name: Option[String] = None,
	  /** Output only. Timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. Timestamp when the resource was last modified. */
		updateTime: Option[String] = None,
	  /** Optional. Resource labels to represent user provided metadata. Each label is a key-value pair, where both the key and the value are arbitrary strings provided by the user. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Current lifecycle state of the resource (e.g. if it's being created or ready to use). */
		state: Option[Schema.GoogleCloudSaasacceleratorManagementProvidersV1Instance.StateEnum] = None,
	  /** Software versions that are used to deploy this instance. This can be mutated by rollout services. */
		softwareVersions: Option[Map[String, String]] = None,
	  /** Optional. The MaintenancePolicies that have been attached to the instance. The key must be of the type name of the oneof policy name defined in MaintenancePolicy, and the referenced policy must define the same policy type. For details, please refer to go/mr-user-guide. Should not be set if maintenance_settings.maintenance_policies is set. */
		maintenancePolicyNames: Option[Map[String, String]] = None,
	  /** Output only. ID of the associated GCP tenant project. See go/get-instance-metadata. */
		tenantProjectId: Option[String] = None,
	  /** Output only. Custom string attributes used primarily to expose producer-specific information in monitoring dashboards. See go/get-instance-metadata. */
		producerMetadata: Option[Map[String, String]] = None,
	  /** Output only. The list of data plane resources provisioned for this instance, e.g. compute VMs. See go/get-instance-metadata. */
		provisionedResources: Option[List[Schema.GoogleCloudSaasacceleratorManagementProvidersV1ProvisionedResource]] = None,
	  /** Output only. SLO metadata for instance classification in the Standardized dataplane SLO platform. See go/cloud-ssa-standard-slo for feature description. */
		sloMetadata: Option[Schema.GoogleCloudSaasacceleratorManagementProvidersV1SloMetadata] = None,
	  /** The MaintenanceSchedule contains the scheduling information of published maintenance schedule with same key as software_versions. */
		maintenanceSchedules: Option[Map[String, Schema.GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSchedule]] = None,
	  /** consumer_defined_name is the name of the instance set by the service consumers. Generally this is different from the `name` field which reperesents the system-assigned id of the instance which the service consumers do not recognize. This is a required field for tenants onboarding to Maintenance Window notifications (go/slm-rollout-maintenance-policies#prerequisites). */
		consumerDefinedName: Option[String] = None,
	  /** Link to the SLM instance template. Only populated when updating SLM instances via SSA's Actuation service adaptor. Service producers with custom control plane (e.g. Cloud SQL) doesn't need to populate this field. Instead they should use software_versions. */
		slmInstanceTemplate: Option[String] = None,
	  /** Optional. The MaintenanceSettings associated with instance. */
		maintenanceSettings: Option[Schema.GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSettings] = None,
	  /** Optional. The instance_type of this instance of format: projects/{project_number}/locations/{location_id}/instanceTypes/{instance_type_id}. Instance Type represents a high-level tier or SKU of the service that this instance belong to. When enabled(eg: Maintenance Rollout), Rollout uses 'instance_type' along with 'software_versions' to determine whether instance needs an update or not. */
		instanceType: Option[String] = None,
	  /** Optional. notification_parameter are information that service producers may like to include that is not relevant to Rollout. This parameter will only be passed to Gamma and Cloud Logging for notification/logging purpose. */
		notificationParameters: Option[Map[String, Schema.GoogleCloudSaasacceleratorManagementProvidersV1NotificationParameter]] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1ProvisionedResource(
	  /** Type of the resource. This can be either a GCP resource or a custom one (e.g. another cloud provider's VM). For GCP compute resources use singular form of the names listed in GCP compute API documentation (https://cloud.google.com/compute/docs/reference/rest/v1/), prefixed with 'compute-', for example: 'compute-instance', 'compute-disk', 'compute-autoscaler'. */
		resourceType: Option[String] = None,
	  /** URL identifying the resource, e.g. "https://www.googleapis.com/compute/v1/projects/...)". */
		resourceUrl: Option[String] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1SloMetadata(
	  /** Name of the SLO tier the Instance belongs to. This name will be expected to match the tiers specified in the service SLO configuration. Field is mandatory and must not be empty. */
		tier: Option[String] = None,
	  /** Optional. List of nodes. Some producers need to use per-node metadata to calculate SLO. This field allows such producers to publish per-node SLO meta data, which will be consumed by SSA Eligibility Exporter and published in the form of per node metric to Monarch. */
		nodes: Option[List[Schema.GoogleCloudSaasacceleratorManagementProvidersV1NodeSloMetadata]] = None,
	  /** Optional. Multiple per-instance SLI eligibilities which apply for individual SLIs. */
		perSliEligibility: Option[Schema.GoogleCloudSaasacceleratorManagementProvidersV1PerSliSloEligibility] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1NodeSloMetadata(
	  /** The id of the node. This should be equal to SaasInstanceNode.node_id. */
		nodeId: Option[String] = None,
	  /** The location of the node, if different from instance location. */
		location: Option[String] = None,
	  /** If present, this will override eligibility for the node coming from instance or exclusions for specified SLIs. */
		perSliEligibility: Option[Schema.GoogleCloudSaasacceleratorManagementProvidersV1PerSliSloEligibility] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1PerSliSloEligibility(
	  /** An entry in the eligibilities map specifies an eligibility for a particular SLI for the given instance. The SLI key in the name must be a valid SLI name specified in the Eligibility Exporter binary flags otherwise an error will be emitted by Eligibility Exporter and the oncaller will be alerted. If an SLI has been defined in the binary flags but the eligibilities map does not contain it, the corresponding SLI time series will not be emitted by the Eligibility Exporter. This ensures a smooth rollout and compatibility between the data produced by different versions of the Eligibility Exporters. If eligibilities map contains a key for an SLI which has not been declared in the binary flags, there will be an error message emitted in the Eligibility Exporter log and the metric for the SLI in question will not be emitted. */
		eligibilities: Option[Map[String, Schema.GoogleCloudSaasacceleratorManagementProvidersV1SloEligibility]] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1SloEligibility(
	  /** Whether an instance is eligible or ineligible. */
		eligible: Option[Boolean] = None,
	  /** User-defined reason for the current value of instance eligibility. Usually, this can be directly mapped to the internal state. An empty reason is allowed. */
		reason: Option[String] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSchedule(
	  /** The scheduled start time for the maintenance. */
		startTime: Option[String] = None,
	  /** The scheduled end time for the maintenance. */
		endTime: Option[String] = None,
	  /** This field is deprecated, and will be always set to true since reschedule can happen multiple times now. This field should not be removed until all service producers remove this for their customers. */
		canReschedule: Option[Boolean] = None,
	  /** The rollout management policy this maintenance schedule is associated with. When doing reschedule update request, the reschedule should be against this given policy. */
		rolloutManagementPolicy: Option[String] = None,
	  /** schedule_deadline_time is the time deadline any schedule start time cannot go beyond, including reschedule. It's normally the initial schedule start time plus maintenance window length (1 day or 1 week). Maintenance cannot be scheduled to start beyond this deadline. */
		scheduleDeadlineTime: Option[String] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSettings(
	  /** Optional. Exclude instance from maintenance. When true, rollout service will not attempt maintenance on the instance. Rollout service will include the instance in reported rollout progress as not attempted. */
		exclude: Option[Boolean] = None,
	  /** Optional. The MaintenancePolicies that have been attached to the instance. The key must be of the type name of the oneof policy name defined in MaintenancePolicy, and the embedded policy must define the same policy type. For details, please refer to go/mr-user-guide. Should not be set if maintenance_policy_names is set. If only the name is needed, then only populate MaintenancePolicy.name. */
		maintenancePolicies: Option[Map[String, Schema.MaintenancePolicy]] = None,
	  /** Optional. If the update call is triggered from rollback, set the value as true. */
		isRollback: Option[Boolean] = None
	)
	
	object MaintenancePolicy {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, READY, DELETING }
	}
	case class MaintenancePolicy(
	  /** Required. MaintenancePolicy name using the form: `projects/{project_id}/locations/{location_id}/maintenancePolicies/{maintenance_policy_id}` where {project_id} refers to a GCP consumer project ID, {location_id} refers to a GCP region/zone, {maintenance_policy_id} must be 1-63 characters long and match the regular expression `[a-z0-9]([-a-z0-9]&#42;[a-z0-9])?`. */
		name: Option[String] = None,
	  /** Output only. The time when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. Description of what this policy is for. Create/Update methods return INVALID_ARGUMENT if the length is greater than 512. */
		description: Option[String] = None,
	  /** Optional. Resource labels to represent user provided metadata. Each label is a key-value pair, where both the key and the value are arbitrary strings provided by the user. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. The state of the policy. */
		state: Option[Schema.MaintenancePolicy.StateEnum] = None,
	  /** Maintenance policy applicable to instance update. */
		updatePolicy: Option[Schema.UpdatePolicy] = None
	)
	
	object UpdatePolicy {
		enum ChannelEnum extends Enum[ChannelEnum] { case UPDATE_CHANNEL_UNSPECIFIED, EARLIER, LATER, WEEK1, WEEK2, WEEK5 }
	}
	case class UpdatePolicy(
	  /** Optional. Maintenance window that is applied to resources covered by this policy. */
		window: Option[Schema.MaintenanceWindow] = None,
	  /** Optional. Relative scheduling channel applied to resource. */
		channel: Option[Schema.UpdatePolicy.ChannelEnum] = None,
	  /** Deny Maintenance Period that is applied to resource to indicate when maintenance is forbidden. The protocol supports zero-to-many such periods, but the current SLM Rollout implementation only supports zero-to-one. */
		denyMaintenancePeriods: Option[List[Schema.DenyMaintenancePeriod]] = None
	)
	
	case class MaintenanceWindow(
	  /** Daily cycle. */
		dailyCycle: Option[Schema.DailyCycle] = None,
	  /** Weekly cycle. */
		weeklyCycle: Option[Schema.WeeklyCycle] = None
	)
	
	case class DailyCycle(
	  /** Time within the day to start the operations. */
		startTime: Option[Schema.TimeOfDay] = None,
	  /** Output only. Duration of the time window, set by service producer. */
		duration: Option[String] = None
	)
	
	case class TimeOfDay(
	  /** Hours of a day in 24 hour format. Must be greater than or equal to 0 and typically must be less than or equal to 23. An API may choose to allow the value "24:00:00" for scenarios like business closing time. */
		hours: Option[Int] = None,
	  /** Minutes of an hour. Must be greater than or equal to 0 and less than or equal to 59. */
		minutes: Option[Int] = None,
	  /** Seconds of a minute. Must be greater than or equal to 0 and typically must be less than or equal to 59. An API may allow the value 60 if it allows leap-seconds. */
		seconds: Option[Int] = None,
	  /** Fractions of seconds, in nanoseconds. Must be greater than or equal to 0 and less than or equal to 999,999,999. */
		nanos: Option[Int] = None
	)
	
	case class WeeklyCycle(
	  /** User can specify multiple windows in a week. Minimum of 1 window. */
		schedule: Option[List[Schema.Schedule]] = None
	)
	
	object Schedule {
		enum DayEnum extends Enum[DayEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class Schedule(
	  /** Allows to define schedule that runs specified day of the week. */
		day: Option[Schema.Schedule.DayEnum] = None,
	  /** Time within the window to start the operations. */
		startTime: Option[Schema.TimeOfDay] = None,
	  /** Output only. Duration of the time window, set by service producer. */
		duration: Option[String] = None
	)
	
	case class DenyMaintenancePeriod(
	  /** Deny period start date. This can be: &#42; A full date, with non-zero year, month and day values. &#42; A month and day value, with a zero year. Allows recurring deny periods each year. Date matching this period will have to be the same or after the start. */
		startDate: Option[Schema.Date] = None,
	  /** Deny period end date. This can be: &#42; A full date, with non-zero year, month and day values. &#42; A month and day value, with a zero year. Allows recurring deny periods each year. Date matching this period will have to be before the end. */
		endDate: Option[Schema.Date] = None,
	  /** Time in UTC when the Blackout period starts on start_date and ends on end_date. This can be: &#42; Full time. &#42; All zeros for 00:00:00 UTC */
		time: Option[Schema.TimeOfDay] = None
	)
	
	case class Date(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1NotificationParameter(
	  /** Optional. Array of string values. e.g. instance's replica information. */
		values: Option[List[String]] = None
	)
}
