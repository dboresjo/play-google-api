package com.boresjo.play.api.google.workstations

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

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
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class CancelOperationRequest(
	
	)
	
	case class WorkstationCluster(
	  /** Identifier. Full name of this workstation cluster. */
		name: Option[String] = None,
	  /** Optional. Human-readable name for this workstation cluster. */
		displayName: Option[String] = None,
	  /** Output only. A system-assigned unique identifier for this workstation cluster. */
		uid: Option[String] = None,
	  /** Output only. Indicates whether this workstation cluster is currently being updated to match its intended state. */
		reconciling: Option[Boolean] = None,
	  /** Optional. Client-specified annotations. */
		annotations: Option[Map[String, String]] = None,
	  /** Optional. [Labels](https://cloud.google.com/workstations/docs/label-resources) that are applied to the workstation cluster and that are also propagated to the underlying Compute Engine resources. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Time when this workstation cluster was created. */
		createTime: Option[String] = None,
	  /** Output only. Time when this workstation cluster was most recently updated. */
		updateTime: Option[String] = None,
	  /** Output only. Time when this workstation cluster was soft-deleted. */
		deleteTime: Option[String] = None,
	  /** Optional. Checksum computed by the server. May be sent on update and delete requests to make sure that the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Immutable. Name of the Compute Engine network in which instances associated with this workstation cluster will be created. */
		network: Option[String] = None,
	  /** Immutable. Name of the Compute Engine subnetwork in which instances associated with this workstation cluster will be created. Must be part of the subnetwork specified for this workstation cluster. */
		subnetwork: Option[String] = None,
	  /** Output only. The private IP address of the control plane for this workstation cluster. Workstation VMs need access to this IP address to work with the service, so make sure that your firewall rules allow egress from the workstation VMs to this address. */
		controlPlaneIp: Option[String] = None,
	  /** Optional. Configuration for private workstation cluster. */
		privateClusterConfig: Option[Schema.PrivateClusterConfig] = None,
	  /** Optional. Configuration options for a custom domain. */
		domainConfig: Option[Schema.DomainConfig] = None,
	  /** Output only. Whether this workstation cluster is in degraded mode, in which case it may require user action to restore full functionality. Details can be found in conditions. */
		degraded: Option[Boolean] = None,
	  /** Output only. Status conditions describing the workstation cluster's current state. */
		conditions: Option[List[Schema.Status]] = None,
	  /** Optional. Tag keys/values directly bound to this resource. For example: "123/environment": "production", "123/costCenter": "marketing" */
		tags: Option[Map[String, String]] = None
	)
	
	case class PrivateClusterConfig(
	  /** Immutable. Whether Workstations endpoint is private. */
		enablePrivateEndpoint: Option[Boolean] = None,
	  /** Output only. Hostname for the workstation cluster. This field will be populated only when private endpoint is enabled. To access workstations in the workstation cluster, create a new DNS zone mapping this domain name to an internal IP address and a forwarding rule mapping that address to the service attachment. */
		clusterHostname: Option[String] = None,
	  /** Output only. Service attachment URI for the workstation cluster. The service attachemnt is created when private endpoint is enabled. To access workstations in the workstation cluster, configure access to the managed service using [Private Service Connect](https://cloud.google.com/vpc/docs/configure-private-service-connect-services). */
		serviceAttachmentUri: Option[String] = None,
	  /** Optional. Additional projects that are allowed to attach to the workstation cluster's service attachment. By default, the workstation cluster's project and the VPC host project (if different) are allowed. */
		allowedProjects: Option[List[String]] = None
	)
	
	case class DomainConfig(
	  /** Immutable. Domain used by Workstations for HTTP ingress. */
		domain: Option[String] = None
	)
	
	case class ListWorkstationClustersResponse(
	  /** The requested workstation clusters. */
		workstationClusters: Option[List[Schema.WorkstationCluster]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Unreachable resources. */
		unreachable: Option[List[String]] = None
	)
	
	case class WorkstationConfig(
	  /** Identifier. Full name of this workstation configuration. */
		name: Option[String] = None,
	  /** Optional. Human-readable name for this workstation configuration. */
		displayName: Option[String] = None,
	  /** Output only. A system-assigned unique identifier for this workstation configuration. */
		uid: Option[String] = None,
	  /** Output only. Indicates whether this workstation configuration is currently being updated to match its intended state. */
		reconciling: Option[Boolean] = None,
	  /** Optional. Client-specified annotations. */
		annotations: Option[Map[String, String]] = None,
	  /** Optional. [Labels](https://cloud.google.com/workstations/docs/label-resources) that are applied to the workstation configuration and that are also propagated to the underlying Compute Engine resources. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Time when this workstation configuration was created. */
		createTime: Option[String] = None,
	  /** Output only. Time when this workstation configuration was most recently updated. */
		updateTime: Option[String] = None,
	  /** Output only. Time when this workstation configuration was soft-deleted. */
		deleteTime: Option[String] = None,
	  /** Optional. Checksum computed by the server. May be sent on update and delete requests to make sure that the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Optional. Number of seconds to wait before automatically stopping a workstation after it last received user traffic. A value of `"0s"` indicates that Cloud Workstations VMs created with this configuration should never time out due to idleness. Provide [duration](https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#duration) terminated by `s` for seconds—for example, `"7200s"` (2 hours). The default is `"1200s"` (20 minutes). */
		idleTimeout: Option[String] = None,
	  /** Optional. Number of seconds that a workstation can run until it is automatically shut down. We recommend that workstations be shut down daily to reduce costs and so that security updates can be applied upon restart. The idle_timeout and running_timeout fields are independent of each other. Note that the running_timeout field shuts down VMs after the specified time, regardless of whether or not the VMs are idle. Provide duration terminated by `s` for seconds—for example, `"54000s"` (15 hours). Defaults to `"43200s"` (12 hours). A value of `"0s"` indicates that workstations using this configuration should never time out. If encryption_key is set, it must be greater than `"0s"` and less than `"86400s"` (24 hours). Warning: A value of `"0s"` indicates that Cloud Workstations VMs created with this configuration have no maximum running time. This is strongly discouraged because you incur costs and will not pick up security updates. */
		runningTimeout: Option[String] = None,
	  /** Optional. Maximum number of workstations under this configuration a user can have `workstations.workstation.use` permission on. Only enforced on CreateWorkstation API calls on the user issuing the API request. Can be overridden by: - granting a user workstations.workstationConfigs.exemptMaxUsableWorkstationLimit permission, or - having a user with that permission create a workstation and granting another user `workstations.workstation.use` permission on that workstation. If not specified, defaults to `0`, which indicates unlimited. */
		maxUsableWorkstations: Option[Int] = None,
	  /** Optional. Runtime host for the workstation. */
		host: Option[Schema.Host] = None,
	  /** Optional. Directories to persist across workstation sessions. */
		persistentDirectories: Option[List[Schema.PersistentDirectory]] = None,
	  /** Optional. Ephemeral directories which won't persist across workstation sessions. */
		ephemeralDirectories: Option[List[Schema.EphemeralDirectory]] = None,
	  /** Optional. Container that runs upon startup for each workstation using this workstation configuration. */
		container: Option[Schema.Container] = None,
	  /** Immutable. Encrypts resources of this workstation configuration using a customer-managed encryption key (CMEK). If specified, the boot disk of the Compute Engine instance and the persistent disk are encrypted using this encryption key. If this field is not set, the disks are encrypted using a generated key. Customer-managed encryption keys do not protect disk metadata. If the customer-managed encryption key is rotated, when the workstation instance is stopped, the system attempts to recreate the persistent disk with the new version of the key. Be sure to keep older versions of the key until the persistent disk is recreated. Otherwise, data on the persistent disk might be lost. If the encryption key is revoked, the workstation session automatically stops within 7 hours. Immutable after the workstation configuration is created. */
		encryptionKey: Option[Schema.CustomerEncryptionKey] = None,
	  /** Optional. Readiness checks to perform when starting a workstation using this workstation configuration. Mark a workstation as running only after all specified readiness checks return 200 status codes. */
		readinessChecks: Option[List[Schema.ReadinessCheck]] = None,
	  /** Optional. Immutable. Specifies the zones used to replicate the VM and disk resources within the region. If set, exactly two zones within the workstation cluster's region must be specified—for example, `['us-central1-a', 'us-central1-f']`. If this field is empty, two default zones within the region are used. Immutable after the workstation configuration is created. */
		replicaZones: Option[List[String]] = None,
	  /** Output only. Whether this resource is degraded, in which case it may require user action to restore full functionality. See also the conditions field. */
		degraded: Option[Boolean] = None,
	  /** Output only. Status conditions describing the current resource state. */
		conditions: Option[List[Schema.Status]] = None,
	  /** Optional. Whether to enable Linux `auditd` logging on the workstation. When enabled, a service_account must also be specified that has `roles/logging.logWriter` and `roles/monitoring.metricWriter` on the project. Operating system audit logging is distinct from [Cloud Audit Logs](https://cloud.google.com/workstations/docs/audit-logging) and [Container output logging](https://cloud.google.com/workstations/docs/container-output-logging#overview). Operating system audit logs are available in the [Cloud Logging](https://cloud.google.com/logging/docs) console by querying: resource.type="gce_instance" log_name:"/logs/linux-auditd" */
		enableAuditAgent: Option[Boolean] = None,
	  /** Optional. Disables support for plain TCP connections in the workstation. By default the service supports TCP connections through a websocket relay. Setting this option to true disables that relay, which prevents the usage of services that require plain TCP connections, such as SSH. When enabled, all communication must occur over HTTPS or WSS. */
		disableTcpConnections: Option[Boolean] = None,
	  /** Optional. A list of PortRanges specifying single ports or ranges of ports that are externally accessible in the workstation. Allowed ports must be one of 22, 80, or within range 1024-65535. If not specified defaults to ports 22, 80, and ports 1024-65535. */
		allowedPorts: Option[List[Schema.PortRange]] = None,
	  /** Optional. Grant creator of a workstation `roles/workstations.policyAdmin` role along with `roles/workstations.user` role on the workstation created by them. This allows workstation users to share access to either their entire workstation, or individual ports. Defaults to false. */
		grantWorkstationAdminRoleOnCreate: Option[Boolean] = None
	)
	
	case class Host(
	  /** Specifies a Compute Engine instance as the host. */
		gceInstance: Option[Schema.GceInstance] = None
	)
	
	case class GceInstance(
	  /** Optional. The type of machine to use for VM instances—for example, `"e2-standard-4"`. For more information about machine types that Cloud Workstations supports, see the list of [available machine types](https://cloud.google.com/workstations/docs/available-machine-types). */
		machineType: Option[String] = None,
	  /** Optional. The email address of the service account for Cloud Workstations VMs created with this configuration. When specified, be sure that the service account has `logging.logEntries.create` and `monitoring.timeSeries.create` permissions on the project so it can write logs out to Cloud Logging. If using a custom container image, the service account must have [Artifact Registry Reader](https://cloud.google.com/artifact-registry/docs/access-control#roles) permission to pull the specified image. If you as the administrator want to be able to `ssh` into the underlying VM, you need to set this value to a service account for which you have the `iam.serviceAccounts.actAs` permission. Conversely, if you don't want anyone to be able to `ssh` into the underlying VM, use a service account where no one has that permission. If not set, VMs run with a service account provided by the Cloud Workstations service, and the image must be publicly accessible. */
		serviceAccount: Option[String] = None,
	  /** Optional. Scopes to grant to the service_account. When specified, users of workstations under this configuration must have `iam.serviceAccounts.actAs` on the service account. */
		serviceAccountScopes: Option[List[String]] = None,
	  /** Optional. Network tags to add to the Compute Engine VMs backing the workstations. This option applies [network tags](https://cloud.google.com/vpc/docs/add-remove-network-tags) to VMs created with this configuration. These network tags enable the creation of [firewall rules](https://cloud.google.com/workstations/docs/configure-firewall-rules). */
		tags: Option[List[String]] = None,
	  /** Optional. The number of VMs that the system should keep idle so that new workstations can be started quickly for new users. Defaults to `0` in the API. */
		poolSize: Option[Int] = None,
	  /** Output only. Number of instances currently available in the pool for faster workstation startup. */
		pooledInstances: Option[Int] = None,
	  /** Optional. When set to true, disables public IP addresses for VMs. If you disable public IP addresses, you must set up Private Google Access or Cloud NAT on your network. If you use Private Google Access and you use `private.googleapis.com` or `restricted.googleapis.com` for Container Registry and Artifact Registry, make sure that you set up DNS records for domains `&#42;.gcr.io` and `&#42;.pkg.dev`. Defaults to false (VMs have public IP addresses). */
		disablePublicIpAddresses: Option[Boolean] = None,
	  /** Optional. Whether to enable nested virtualization on Cloud Workstations VMs created using this workstation configuration. Defaults to false. Nested virtualization lets you run virtual machine (VM) instances inside your workstation. Before enabling nested virtualization, consider the following important considerations. Cloud Workstations instances are subject to the [same restrictions as Compute Engine instances](https://cloud.google.com/compute/docs/instances/nested-virtualization/overview#restrictions): &#42; &#42;&#42;Organization policy&#42;&#42;: projects, folders, or organizations may be restricted from creating nested VMs if the &#42;&#42;Disable VM nested virtualization&#42;&#42; constraint is enforced in the organization policy. For more information, see the Compute Engine section, [Checking whether nested virtualization is allowed](https://cloud.google.com/compute/docs/instances/nested-virtualization/managing-constraint#checking_whether_nested_virtualization_is_allowed). &#42; &#42;&#42;Performance&#42;&#42;: nested VMs might experience a 10% or greater decrease in performance for workloads that are CPU-bound and possibly greater than a 10% decrease for workloads that are input/output bound. &#42; &#42;&#42;Machine Type&#42;&#42;: nested virtualization can only be enabled on workstation configurations that specify a machine_type in the N1 or N2 machine series. */
		enableNestedVirtualization: Option[Boolean] = None,
	  /** Optional. A set of Compute Engine Shielded instance options. */
		shieldedInstanceConfig: Option[Schema.GceShieldedInstanceConfig] = None,
	  /** Optional. A set of Compute Engine Confidential VM instance options. */
		confidentialInstanceConfig: Option[Schema.GceConfidentialInstanceConfig] = None,
	  /** Optional. The size of the boot disk for the VM in gigabytes (GB). The minimum boot disk size is `30` GB. Defaults to `50` GB. */
		bootDiskSizeGb: Option[Int] = None,
	  /** Optional. A list of the type and count of accelerator cards attached to the instance. */
		accelerators: Option[List[Schema.Accelerator]] = None,
	  /** Optional. A list of the boost configurations that workstations created using this workstation configuration are allowed to use. */
		boostConfigs: Option[List[Schema.BoostConfig]] = None,
	  /** Optional. Whether to disable SSH access to the VM. */
		disableSsh: Option[Boolean] = None,
	  /** Optional. Resource manager tags to be bound to this instance. Tag keys and values have the same definition as [resource manager tags](https://cloud.google.com/resource-manager/docs/tags/tags-overview). Keys must be in the format `tagKeys/{tag_key_id}`, and values are in the format `tagValues/456`. */
		vmTags: Option[Map[String, String]] = None
	)
	
	case class GceShieldedInstanceConfig(
	  /** Optional. Whether the instance has Secure Boot enabled. */
		enableSecureBoot: Option[Boolean] = None,
	  /** Optional. Whether the instance has the vTPM enabled. */
		enableVtpm: Option[Boolean] = None,
	  /** Optional. Whether the instance has integrity monitoring enabled. */
		enableIntegrityMonitoring: Option[Boolean] = None
	)
	
	case class GceConfidentialInstanceConfig(
	  /** Optional. Whether the instance has confidential compute enabled. */
		enableConfidentialCompute: Option[Boolean] = None
	)
	
	case class Accelerator(
	  /** Optional. Type of accelerator resource to attach to the instance, for example, `"nvidia-tesla-p100"`. */
		`type`: Option[String] = None,
	  /** Optional. Number of accelerator cards exposed to the instance. */
		count: Option[Int] = None
	)
	
	case class BoostConfig(
	  /** Optional. Required. The id to be used for the boost configuration. */
		id: Option[String] = None,
	  /** Optional. The type of machine that boosted VM instances will use—for example, `e2-standard-4`. For more information about machine types that Cloud Workstations supports, see the list of [available machine types](https://cloud.google.com/workstations/docs/available-machine-types). Defaults to `e2-standard-4`. */
		machineType: Option[String] = None,
	  /** Optional. A list of the type and count of accelerator cards attached to the boost instance. Defaults to `none`. */
		accelerators: Option[List[Schema.Accelerator]] = None,
	  /** Optional. The size of the boot disk for the VM in gigabytes (GB). The minimum boot disk size is `30` GB. Defaults to `50` GB. */
		bootDiskSizeGb: Option[Int] = None,
	  /** Optional. Whether to enable nested virtualization on boosted Cloud Workstations VMs running using this boost configuration. Defaults to false. Nested virtualization lets you run virtual machine (VM) instances inside your workstation. Before enabling nested virtualization, consider the following important considerations. Cloud Workstations instances are subject to the [same restrictions as Compute Engine instances](https://cloud.google.com/compute/docs/instances/nested-virtualization/overview#restrictions): &#42; &#42;&#42;Organization policy&#42;&#42;: projects, folders, or organizations may be restricted from creating nested VMs if the &#42;&#42;Disable VM nested virtualization&#42;&#42; constraint is enforced in the organization policy. For more information, see the Compute Engine section, [Checking whether nested virtualization is allowed](https://cloud.google.com/compute/docs/instances/nested-virtualization/managing-constraint#checking_whether_nested_virtualization_is_allowed). &#42; &#42;&#42;Performance&#42;&#42;: nested VMs might experience a 10% or greater decrease in performance for workloads that are CPU-bound and possibly greater than a 10% decrease for workloads that are input/output bound. &#42; &#42;&#42;Machine Type&#42;&#42;: nested virtualization can only be enabled on boost configurations that specify a machine_type in the N1 or N2 machine series. */
		enableNestedVirtualization: Option[Boolean] = None,
	  /** Optional. The number of boost VMs that the system should keep idle so that workstations can be boosted quickly. Defaults to `0`. */
		poolSize: Option[Int] = None
	)
	
	case class PersistentDirectory(
	  /** A PersistentDirectory backed by a Compute Engine persistent disk. */
		gcePd: Option[Schema.GceRegionalPersistentDisk] = None,
	  /** Optional. Location of this directory in the running workstation. */
		mountPath: Option[String] = None
	)
	
	object GceRegionalPersistentDisk {
		enum ReclaimPolicyEnum extends Enum[ReclaimPolicyEnum] { case RECLAIM_POLICY_UNSPECIFIED, DELETE, RETAIN }
	}
	case class GceRegionalPersistentDisk(
	  /** Optional. The GB capacity of a persistent home directory for each workstation created with this configuration. Must be empty if source_snapshot is set. Valid values are `10`, `50`, `100`, `200`, `500`, or `1000`. Defaults to `200`. If less than `200` GB, the disk_type must be `"pd-balanced"` or `"pd-ssd"`. */
		sizeGb: Option[Int] = None,
	  /** Optional. Type of file system that the disk should be formatted with. The workstation image must support this file system type. Must be empty if source_snapshot is set. Defaults to `"ext4"`. */
		fsType: Option[String] = None,
	  /** Optional. The [type of the persistent disk](https://cloud.google.com/compute/docs/disks#disk-types) for the home directory. Defaults to `"pd-standard"`. */
		diskType: Option[String] = None,
	  /** Optional. Name of the snapshot to use as the source for the disk. If set, size_gb and fs_type must be empty. */
		sourceSnapshot: Option[String] = None,
	  /** Optional. Whether the persistent disk should be deleted when the workstation is deleted. Valid values are `DELETE` and `RETAIN`. Defaults to `DELETE`. */
		reclaimPolicy: Option[Schema.GceRegionalPersistentDisk.ReclaimPolicyEnum] = None
	)
	
	case class EphemeralDirectory(
	  /** An EphemeralDirectory backed by a Compute Engine persistent disk. */
		gcePd: Option[Schema.GcePersistentDisk] = None,
	  /** Required. Location of this directory in the running workstation. */
		mountPath: Option[String] = None
	)
	
	case class GcePersistentDisk(
	  /** Optional. Type of the disk to use. Defaults to `"pd-standard"`. */
		diskType: Option[String] = None,
	  /** Optional. Name of the snapshot to use as the source for the disk. Must be empty if source_image is set. Must be empty if read_only is false. Updating source_snapshot will update content in the ephemeral directory after the workstation is restarted. This field is mutable. */
		sourceSnapshot: Option[String] = None,
	  /** Optional. Name of the disk image to use as the source for the disk. Must be empty if source_snapshot is set. Updating source_image will update content in the ephemeral directory after the workstation is restarted. This field is mutable. */
		sourceImage: Option[String] = None,
	  /** Optional. Whether the disk is read only. If true, the disk may be shared by multiple VMs and source_snapshot must be set. */
		readOnly: Option[Boolean] = None
	)
	
	case class Container(
	  /** Optional. A Docker container image that defines a custom environment. Cloud Workstations provides a number of [preconfigured images](https://cloud.google.com/workstations/docs/preconfigured-base-images), but you can create your own [custom container images](https://cloud.google.com/workstations/docs/custom-container-images). If using a private image, the `host.gceInstance.serviceAccount` field must be specified in the workstation configuration. If using a custom container image, the service account must have [Artifact Registry Reader](https://cloud.google.com/artifact-registry/docs/access-control#roles) permission to pull the specified image. Otherwise, the image must be publicly accessible. */
		image: Option[String] = None,
	  /** Optional. If set, overrides the default ENTRYPOINT specified by the image. */
		command: Option[List[String]] = None,
	  /** Optional. Arguments passed to the entrypoint. */
		args: Option[List[String]] = None,
	  /** Optional. Environment variables passed to the container's entrypoint. */
		env: Option[Map[String, String]] = None,
	  /** Optional. If set, overrides the default DIR specified by the image. */
		workingDir: Option[String] = None,
	  /** Optional. If set, overrides the USER specified in the image with the given uid. */
		runAsUser: Option[Int] = None
	)
	
	case class CustomerEncryptionKey(
	  /** Immutable. The name of the Google Cloud KMS encryption key. For example, `"projects/PROJECT_ID/locations/REGION/keyRings/KEY_RING/cryptoKeys/KEY_NAME"`. The key must be in the same region as the workstation configuration. */
		kmsKey: Option[String] = None,
	  /** Immutable. The service account to use with the specified KMS key. We recommend that you use a separate service account and follow KMS best practices. For more information, see [Separation of duties](https://cloud.google.com/kms/docs/separation-of-duties) and `gcloud kms keys add-iam-policy-binding` [`--member`](https://cloud.google.com/sdk/gcloud/reference/kms/keys/add-iam-policy-binding#--member). */
		kmsKeyServiceAccount: Option[String] = None
	)
	
	case class ReadinessCheck(
	  /** Optional. Path to which the request should be sent. */
		path: Option[String] = None,
	  /** Optional. Port to which the request should be sent. */
		port: Option[Int] = None
	)
	
	case class PortRange(
	  /** Required. Starting port number for the current range of ports. Valid ports are 22, 80, and ports within the range 1024-65535. */
		first: Option[Int] = None,
	  /** Required. Ending port number for the current range of ports. Valid ports are 22, 80, and ports within the range 1024-65535. */
		last: Option[Int] = None
	)
	
	case class ListWorkstationConfigsResponse(
	  /** The requested configs. */
		workstationConfigs: Option[List[Schema.WorkstationConfig]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Unreachable resources. */
		unreachable: Option[List[String]] = None
	)
	
	case class ListUsableWorkstationConfigsResponse(
	  /** The requested configs. */
		workstationConfigs: Option[List[Schema.WorkstationConfig]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Unreachable resources. */
		unreachable: Option[List[String]] = None
	)
	
	object Workstation {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STATE_STARTING, STATE_RUNNING, STATE_STOPPING, STATE_STOPPED }
	}
	case class Workstation(
	  /** Identifier. Full name of this workstation. */
		name: Option[String] = None,
	  /** Optional. Human-readable name for this workstation. */
		displayName: Option[String] = None,
	  /** Output only. A system-assigned unique identifier for this workstation. */
		uid: Option[String] = None,
	  /** Output only. Indicates whether this workstation is currently being updated to match its intended state. */
		reconciling: Option[Boolean] = None,
	  /** Optional. Client-specified annotations. */
		annotations: Option[Map[String, String]] = None,
	  /** Optional. [Labels](https://cloud.google.com/workstations/docs/label-resources) that are applied to the workstation and that are also propagated to the underlying Compute Engine resources. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Time when this workstation was created. */
		createTime: Option[String] = None,
	  /** Output only. Time when this workstation was most recently updated. */
		updateTime: Option[String] = None,
	  /** Output only. Time when this workstation was most recently successfully started, regardless of the workstation's initial state. */
		startTime: Option[String] = None,
	  /** Output only. Time when this workstation was soft-deleted. */
		deleteTime: Option[String] = None,
	  /** Optional. Checksum computed by the server. May be sent on update and delete requests to make sure that the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Output only. Current state of the workstation. */
		state: Option[Schema.Workstation.StateEnum] = None,
	  /** Output only. Host to which clients can send HTTPS traffic that will be received by the workstation. Authorized traffic will be received to the workstation as HTTP on port 80. To send traffic to a different port, clients may prefix the host with the destination port in the format `{port}-{host}`. */
		host: Option[String] = None,
	  /** Optional. Environment variables passed to the workstation container's entrypoint. */
		env: Option[Map[String, String]] = None,
	  /** Output only. The name of the Google Cloud KMS encryption key used to encrypt this workstation. The KMS key can only be configured in the WorkstationConfig. The expected format is `projects/&#42;/locations/&#42;/keyRings/&#42;/cryptoKeys/&#42;`. */
		kmsKey: Option[String] = None
	)
	
	case class ListWorkstationsResponse(
	  /** The requested workstations. */
		workstations: Option[List[Schema.Workstation]] = None,
	  /** Optional. Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Optional. Unreachable resources. */
		unreachable: Option[List[String]] = None
	)
	
	case class ListUsableWorkstationsResponse(
	  /** The requested workstations. */
		workstations: Option[List[Schema.Workstation]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Unreachable resources. */
		unreachable: Option[List[String]] = None
	)
	
	case class StartWorkstationRequest(
	  /** Optional. If set, validate the request and preview the review, but do not actually apply it. */
		validateOnly: Option[Boolean] = None,
	  /** Optional. If set, the request will be rejected if the latest version of the workstation on the server does not have this ETag. */
		etag: Option[String] = None
	)
	
	case class StopWorkstationRequest(
	  /** Optional. If set, validate the request and preview the review, but do not actually apply it. */
		validateOnly: Option[Boolean] = None,
	  /** Optional. If set, the request will be rejected if the latest version of the workstation on the server does not have this ETag. */
		etag: Option[String] = None
	)
	
	case class GenerateAccessTokenRequest(
	  /** Desired expiration time of the access token. This value must be at most 24 hours in the future. If a value is not specified, the token's expiration time will be set to a default value of 1 hour in the future. */
		expireTime: Option[String] = None,
	  /** Desired lifetime duration of the access token. This value must be at most 24 hours. If a value is not specified, the token's lifetime will be set to a default value of 1 hour. */
		ttl: Option[String] = None,
	  /** Optional. Port for which the access token should be generated. If specified, the generated access token grants access only to the specified port of the workstation. If specified, values must be within the range [1 - 65535]. If not specified, the generated access token grants access to all ports of the workstation. */
		port: Option[Int] = None
	)
	
	case class GenerateAccessTokenResponse(
	  /** The generated bearer access token. To use this token, include it in an Authorization header of an HTTP request sent to the associated workstation's hostname—for example, `Authorization: Bearer `. */
		accessToken: Option[String] = None,
	  /** Time at which the generated token will expire. */
		expireTime: Option[String] = None
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
	  /** Output only. Time that the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. Time that the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusMessage: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
}
