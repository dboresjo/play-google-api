package com.boresjo.play.api.google.vmmigration

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
	
	case class ListSourcesResponse(
	  /** Output only. The list of sources response. */
		sources: Option[List[Schema.Source]] = None,
	  /** Output only. A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Output only. Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class Source(
	  /** Vmware type source details. */
		vmware: Option[Schema.VmwareSourceDetails] = None,
	  /** AWS type source details. */
		aws: Option[Schema.AwsSourceDetails] = None,
	  /** Azure type source details. */
		azure: Option[Schema.AzureSourceDetails] = None,
	  /** Output only. The Source name. */
		name: Option[String] = None,
	  /** Output only. The create time timestamp. */
		createTime: Option[String] = None,
	  /** Output only. The update time timestamp. */
		updateTime: Option[String] = None,
	  /** The labels of the source. */
		labels: Option[Map[String, String]] = None,
	  /** User-provided description of the source. */
		description: Option[String] = None,
	  /** Optional. Immutable. The encryption details of the source data stored by the service. */
		encryption: Option[Schema.Encryption] = None
	)
	
	case class VmwareSourceDetails(
	  /** The credentials username. */
		username: Option[String] = None,
	  /** Input only. The credentials password. This is write only and can not be read in a GET operation. */
		password: Option[String] = None,
	  /** The ip address of the vcenter this Source represents. */
		vcenterIp: Option[String] = None,
	  /** The thumbprint representing the certificate for the vcenter. */
		thumbprint: Option[String] = None,
	  /** The hostname of the vcenter. */
		resolvedVcenterHost: Option[String] = None
	)
	
	object AwsSourceDetails {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, FAILED, ACTIVE }
	}
	case class AwsSourceDetails(
	  /** AWS Credentials using access key id and secret. */
		accessKeyCreds: Option[Schema.AccessKeyCredentials] = None,
	  /** Immutable. The AWS region that the source VMs will be migrated from. */
		awsRegion: Option[String] = None,
	  /** Output only. State of the source as determined by the health check. */
		state: Option[Schema.AwsSourceDetails.StateEnum] = None,
	  /** Output only. Provides details on the state of the Source in case of an error. */
		error: Option[Schema.Status] = None,
	  /** AWS resource tags to limit the scope of the source inventory. */
		inventoryTagList: Option[List[Schema.Tag]] = None,
	  /** AWS security group names to limit the scope of the source inventory. */
		inventorySecurityGroupNames: Option[List[String]] = None,
	  /** User specified tags to add to every M2VM generated resource in AWS. These tags will be set in addition to the default tags that are set as part of the migration process. The tags must not begin with the reserved prefix `m2vm`. */
		migrationResourcesUserTags: Option[Map[String, String]] = None,
	  /** Output only. The source's public IP. All communication initiated by this source will originate from this IP. */
		publicIp: Option[String] = None
	)
	
	case class AccessKeyCredentials(
	  /** AWS access key ID. */
		accessKeyId: Option[String] = None,
	  /** Input only. AWS secret access key. */
		secretAccessKey: Option[String] = None,
	  /** Input only. AWS session token. Used only when AWS security token service (STS) is responsible for creating the temporary credentials. */
		sessionToken: Option[String] = None
	)
	
	case class Tag(
	  /** Required. Key of tag. */
		key: Option[String] = None,
	  /** Required. Value of tag. */
		value: Option[String] = None
	)
	
	object AzureSourceDetails {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, FAILED, ACTIVE }
	}
	case class AzureSourceDetails(
	  /** Azure Credentials using tenant ID, client ID and secret. */
		clientSecretCreds: Option[Schema.ClientSecretCredentials] = None,
	  /** Immutable. Azure subscription ID. */
		subscriptionId: Option[String] = None,
	  /** Immutable. The Azure location (region) that the source VMs will be migrated from. */
		azureLocation: Option[String] = None,
	  /** Output only. State of the source as determined by the health check. */
		state: Option[Schema.AzureSourceDetails.StateEnum] = None,
	  /** Output only. Provides details on the state of the Source in case of an error. */
		error: Option[Schema.Status] = None,
	  /** User specified tags to add to every M2VM generated resource in Azure. These tags will be set in addition to the default tags that are set as part of the migration process. The tags must not begin with the reserved prefix `m4ce` or `m2vm`. */
		migrationResourcesUserTags: Option[Map[String, String]] = None,
	  /** Output only. The ID of the Azure resource group that contains all resources related to the migration process of this source. */
		resourceGroupId: Option[String] = None
	)
	
	case class ClientSecretCredentials(
	  /** Azure tenant ID. */
		tenantId: Option[String] = None,
	  /** Azure client ID. */
		clientId: Option[String] = None,
	  /** Input only. Azure client secret. */
		clientSecret: Option[String] = None
	)
	
	case class Encryption(
	  /** Required. The name of the encryption key that is stored in Google Cloud KMS. */
		kmsKey: Option[String] = None
	)
	
	case class FetchInventoryResponse(
	  /** The description of the VMs in a Source of type Vmware. */
		vmwareVms: Option[Schema.VmwareVmsDetails] = None,
	  /** The description of the VMs in a Source of type AWS. */
		awsVms: Option[Schema.AwsVmsDetails] = None,
	  /** The description of the VMs in a Source of type Azure. */
		azureVms: Option[Schema.AzureVmsDetails] = None,
	  /** Output only. The timestamp when the source was last queried (if the result is from the cache). */
		updateTime: Option[String] = None,
	  /** Output only. A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class VmwareVmsDetails(
	  /** The details of the vmware VMs. */
		details: Option[List[Schema.VmwareVmDetails]] = None
	)
	
	object VmwareVmDetails {
		enum PowerStateEnum extends Enum[PowerStateEnum] { case POWER_STATE_UNSPECIFIED, ON, OFF, SUSPENDED }
		enum BootOptionEnum extends Enum[BootOptionEnum] { case BOOT_OPTION_UNSPECIFIED, EFI, BIOS }
		enum ArchitectureEnum extends Enum[ArchitectureEnum] { case VM_ARCHITECTURE_UNSPECIFIED, VM_ARCHITECTURE_X86_FAMILY, VM_ARCHITECTURE_ARM64 }
	}
	case class VmwareVmDetails(
	  /** The VM's id in the source (note that this is not the MigratingVm's id). This is the moref id of the VM. */
		vmId: Option[String] = None,
	  /** The id of the vCenter's datacenter this VM is contained in. */
		datacenterId: Option[String] = None,
	  /** The descriptive name of the vCenter's datacenter this VM is contained in. */
		datacenterDescription: Option[String] = None,
	  /** The unique identifier of the VM in vCenter. */
		uuid: Option[String] = None,
	  /** The display name of the VM. Note that this is not necessarily unique. */
		displayName: Option[String] = None,
	  /** The power state of the VM at the moment list was taken. */
		powerState: Option[Schema.VmwareVmDetails.PowerStateEnum] = None,
	  /** The number of cpus in the VM. */
		cpuCount: Option[Int] = None,
	  /** The size of the memory of the VM in MB. */
		memoryMb: Option[Int] = None,
	  /** The number of disks the VM has. */
		diskCount: Option[Int] = None,
	  /** The total size of the storage allocated to the VM in MB. */
		committedStorageMb: Option[String] = None,
	  /** The VM's OS. See for example https://vdc-repo.vmware.com/vmwb-repository/dcr-public/da47f910-60ac-438b-8b9b-6122f4d14524/16b7274a-bf8b-4b4c-a05e-746f2aa93c8c/doc/vim.vm.GuestOsDescriptor.GuestOsIdentifier.html for types of strings this might hold. */
		guestDescription: Option[String] = None,
	  /** Output only. The VM Boot Option. */
		bootOption: Option[Schema.VmwareVmDetails.BootOptionEnum] = None,
	  /** Output only. The CPU architecture. */
		architecture: Option[Schema.VmwareVmDetails.ArchitectureEnum] = None
	)
	
	case class AwsVmsDetails(
	  /** The details of the AWS VMs. */
		details: Option[List[Schema.AwsVmDetails]] = None
	)
	
	object AwsVmDetails {
		enum PowerStateEnum extends Enum[PowerStateEnum] { case POWER_STATE_UNSPECIFIED, ON, OFF, SUSPENDED, PENDING }
		enum BootOptionEnum extends Enum[BootOptionEnum] { case BOOT_OPTION_UNSPECIFIED, EFI, BIOS }
		enum VirtualizationTypeEnum extends Enum[VirtualizationTypeEnum] { case VM_VIRTUALIZATION_TYPE_UNSPECIFIED, HVM, PARAVIRTUAL }
		enum ArchitectureEnum extends Enum[ArchitectureEnum] { case VM_ARCHITECTURE_UNSPECIFIED, I386, X86_64, ARM64, X86_64_MAC }
	}
	case class AwsVmDetails(
	  /** The VM ID in AWS. */
		vmId: Option[String] = None,
	  /** The display name of the VM. Note that this value is not necessarily unique. */
		displayName: Option[String] = None,
	  /** The id of the AWS's source this VM is connected to. */
		sourceId: Option[String] = None,
	  /** The descriptive name of the AWS's source this VM is connected to. */
		sourceDescription: Option[String] = None,
	  /** Output only. The power state of the VM at the moment list was taken. */
		powerState: Option[Schema.AwsVmDetails.PowerStateEnum] = None,
	  /** The number of cpus the VM has. */
		cpuCount: Option[Int] = None,
	  /** The memory size of the VM in MB. */
		memoryMb: Option[Int] = None,
	  /** The number of disks the VM has. */
		diskCount: Option[Int] = None,
	  /** The total size of the storage allocated to the VM in MB. */
		committedStorageMb: Option[String] = None,
	  /** The VM's OS. */
		osDescription: Option[String] = None,
	  /** The VM Boot Option. */
		bootOption: Option[Schema.AwsVmDetails.BootOptionEnum] = None,
	  /** The instance type of the VM. */
		instanceType: Option[String] = None,
	  /** The VPC ID the VM belongs to. */
		vpcId: Option[String] = None,
	  /** The security groups the VM belongs to. */
		securityGroups: Option[List[Schema.AwsSecurityGroup]] = None,
	  /** The tags of the VM. */
		tags: Option[Map[String, String]] = None,
	  /** The AWS zone of the VM. */
		zone: Option[String] = None,
	  /** The virtualization type. */
		virtualizationType: Option[Schema.AwsVmDetails.VirtualizationTypeEnum] = None,
	  /** The CPU architecture. */
		architecture: Option[Schema.AwsVmDetails.ArchitectureEnum] = None
	)
	
	case class AwsSecurityGroup(
	  /** The AWS security group id. */
		id: Option[String] = None,
	  /** The AWS security group name. */
		name: Option[String] = None
	)
	
	case class AzureVmsDetails(
	  /** The details of the Azure VMs. */
		details: Option[List[Schema.AzureVmDetails]] = None
	)
	
	object AzureVmDetails {
		enum PowerStateEnum extends Enum[PowerStateEnum] { case POWER_STATE_UNSPECIFIED, STARTING, RUNNING, STOPPING, STOPPED, DEALLOCATING, DEALLOCATED, UNKNOWN }
		enum BootOptionEnum extends Enum[BootOptionEnum] { case BOOT_OPTION_UNSPECIFIED, EFI, BIOS }
		enum ArchitectureEnum extends Enum[ArchitectureEnum] { case VM_ARCHITECTURE_UNSPECIFIED, VM_ARCHITECTURE_X86_FAMILY, VM_ARCHITECTURE_ARM64 }
	}
	case class AzureVmDetails(
	  /** The VM full path in Azure. */
		vmId: Option[String] = None,
	  /** The power state of the VM at the moment list was taken. */
		powerState: Option[Schema.AzureVmDetails.PowerStateEnum] = None,
	  /** VM size as configured in Azure. Determines the VM's hardware spec. */
		vmSize: Option[String] = None,
	  /** The number of cpus the VM has. */
		cpuCount: Option[Int] = None,
	  /** The memory size of the VM in MB. */
		memoryMb: Option[Int] = None,
	  /** The number of disks the VM has, including OS disk. */
		diskCount: Option[Int] = None,
	  /** The total size of the storage allocated to the VM in MB. */
		committedStorageMb: Option[String] = None,
	  /** Description of the OS disk. */
		osDisk: Option[Schema.OSDisk] = None,
	  /** Description of the data disks. */
		disks: Option[List[Schema.Disk]] = None,
	  /** Description of the OS. */
		osDescription: Option[Schema.OSDescription] = None,
	  /** The VM Boot Option. */
		bootOption: Option[Schema.AzureVmDetails.BootOptionEnum] = None,
	  /** The tags of the VM. */
		tags: Option[Map[String, String]] = None,
	  /** The VM's ComputerName. */
		computerName: Option[String] = None,
	  /** The CPU architecture. */
		architecture: Option[Schema.AzureVmDetails.ArchitectureEnum] = None
	)
	
	case class OSDisk(
	  /** The disk's type. */
		`type`: Option[String] = None,
	  /** The disk's full name. */
		name: Option[String] = None,
	  /** The disk's size in GB. */
		sizeGb: Option[Int] = None
	)
	
	case class Disk(
	  /** The disk name. */
		name: Option[String] = None,
	  /** The disk size in GB. */
		sizeGb: Option[Int] = None,
	  /** The disk's Logical Unit Number (LUN). */
		lun: Option[Int] = None
	)
	
	case class OSDescription(
	  /** OS type. */
		`type`: Option[String] = None,
	  /** OS publisher. */
		publisher: Option[String] = None,
	  /** OS offer. */
		offer: Option[String] = None,
	  /** OS plan. */
		plan: Option[String] = None
	)
	
	case class ListUtilizationReportsResponse(
	  /** Output only. The list of reports. */
		utilizationReports: Option[List[Schema.UtilizationReport]] = None,
	  /** Output only. A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Output only. Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object UtilizationReport {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, SUCCEEDED, FAILED }
		enum TimeFrameEnum extends Enum[TimeFrameEnum] { case TIME_FRAME_UNSPECIFIED, WEEK, MONTH, YEAR }
	}
	case class UtilizationReport(
	  /** Output only. The report unique name. */
		name: Option[String] = None,
	  /** The report display name, as assigned by the user. */
		displayName: Option[String] = None,
	  /** Output only. Current state of the report. */
		state: Option[Schema.UtilizationReport.StateEnum] = None,
	  /** Output only. The time the state was last set. */
		stateTime: Option[String] = None,
	  /** Output only. Provides details on the state of the report in case of an error. */
		error: Option[Schema.Status] = None,
	  /** Output only. The time the report was created (this refers to the time of the request, not the time the report creation completed). */
		createTime: Option[String] = None,
	  /** Time frame of the report. */
		timeFrame: Option[Schema.UtilizationReport.TimeFrameEnum] = None,
	  /** Output only. The point in time when the time frame ends. Notice that the time frame is counted backwards. For instance if the "frame_end_time" value is 2021/01/20 and the time frame is WEEK then the report covers the week between 2021/01/20 and 2021/01/14. */
		frameEndTime: Option[String] = None,
	  /** Output only. Total number of VMs included in the report. */
		vmCount: Option[Int] = None,
	  /** List of utilization information per VM. When sent as part of the request, the "vm_id" field is used in order to specify which VMs to include in the report. In that case all other fields are ignored. */
		vms: Option[List[Schema.VmUtilizationInfo]] = None
	)
	
	case class VmUtilizationInfo(
	  /** The description of the VM in a Source of type Vmware. */
		vmwareVmDetails: Option[Schema.VmwareVmDetails] = None,
	  /** The VM's ID in the source. */
		vmId: Option[String] = None,
	  /** Utilization metrics for this VM. */
		utilization: Option[Schema.VmUtilizationMetrics] = None
	)
	
	case class VmUtilizationMetrics(
	  /** Max CPU usage, percent. */
		cpuMaxPercent: Option[Int] = None,
	  /** Average CPU usage, percent. */
		cpuAveragePercent: Option[Int] = None,
	  /** Max memory usage, percent. */
		memoryMaxPercent: Option[Int] = None,
	  /** Average memory usage, percent. */
		memoryAveragePercent: Option[Int] = None,
	  /** Max disk IO rate, in kilobytes per second. */
		diskIoRateMaxKbps: Option[String] = None,
	  /** Average disk IO rate, in kilobytes per second. */
		diskIoRateAverageKbps: Option[String] = None,
	  /** Max network throughput (combined transmit-rates and receive-rates), in kilobytes per second. */
		networkThroughputMaxKbps: Option[String] = None,
	  /** Average network throughput (combined transmit-rates and receive-rates), in kilobytes per second. */
		networkThroughputAverageKbps: Option[String] = None
	)
	
	case class ListDatacenterConnectorsResponse(
	  /** Output only. The list of sources response. */
		datacenterConnectors: Option[List[Schema.DatacenterConnector]] = None,
	  /** Output only. A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Output only. Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object DatacenterConnector {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, OFFLINE, FAILED, ACTIVE }
	}
	case class DatacenterConnector(
	  /** Output only. The time the connector was created (as an API call, not when it was actually installed). */
		createTime: Option[String] = None,
	  /** Output only. The last time the connector was updated with an API call. */
		updateTime: Option[String] = None,
	  /** Output only. The connector's name. */
		name: Option[String] = None,
	  /** Immutable. A unique key for this connector. This key is internal to the OVA connector and is supplied with its creation during the registration process and can not be modified. */
		registrationId: Option[String] = None,
	  /** The service account to use in the connector when communicating with the cloud. */
		serviceAccount: Option[String] = None,
	  /** The version running in the DatacenterConnector. This is supplied by the OVA connector during the registration process and can not be modified. */
		version: Option[String] = None,
	  /** Output only. The communication channel between the datacenter connector and Google Cloud. */
		bucket: Option[String] = None,
	  /** Output only. State of the DatacenterConnector, as determined by the health checks. */
		state: Option[Schema.DatacenterConnector.StateEnum] = None,
	  /** Output only. The time the state was last set. */
		stateTime: Option[String] = None,
	  /** Output only. Provides details on the state of the Datacenter Connector in case of an error. */
		error: Option[Schema.Status] = None,
	  /** Output only. Appliance OVA version. This is the OVA which is manually installed by the user and contains the infrastructure for the automatically updatable components on the appliance. */
		applianceInfrastructureVersion: Option[String] = None,
	  /** Output only. Appliance last installed update bundle version. This is the version of the automatically updatable components on the appliance. */
		applianceSoftwareVersion: Option[String] = None,
	  /** Output only. The available versions for updating this appliance. */
		availableVersions: Option[Schema.AvailableUpdates] = None,
	  /** Output only. The status of the current / last upgradeAppliance operation. */
		upgradeStatus: Option[Schema.UpgradeStatus] = None
	)
	
	case class AvailableUpdates(
	  /** The newest deployable version of the appliance. The current appliance can't be updated into this version, and the owner must manually deploy this OVA to a new appliance. */
		newDeployableAppliance: Option[Schema.ApplianceVersion] = None,
	  /** The latest version for in place update. The current appliance can be updated to this version using the API or m4c CLI. */
		inPlaceUpdate: Option[Schema.ApplianceVersion] = None
	)
	
	case class ApplianceVersion(
	  /** The appliance version. */
		version: Option[String] = None,
	  /** A link for downloading the version. */
		uri: Option[String] = None,
	  /** Determine whether it's critical to upgrade the appliance to this version. */
		critical: Option[Boolean] = None,
	  /** Link to a page that contains the version release notes. */
		releaseNotesUri: Option[String] = None
	)
	
	object UpgradeStatus {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RUNNING, FAILED, SUCCEEDED }
	}
	case class UpgradeStatus(
	  /** The version to upgrade to. */
		version: Option[String] = None,
	  /** The state of the upgradeAppliance operation. */
		state: Option[Schema.UpgradeStatus.StateEnum] = None,
	  /** Output only. Provides details on the state of the upgrade operation in case of an error. */
		error: Option[Schema.Status] = None,
	  /** The time the operation was started. */
		startTime: Option[String] = None,
	  /** The version from which we upgraded. */
		previousVersion: Option[String] = None
	)
	
	case class UpgradeApplianceRequest(
	  /** A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	object MigratingVm {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, READY, FIRST_SYNC, ACTIVE, CUTTING_OVER, CUTOVER, FINAL_SYNC, PAUSED, FINALIZING, FINALIZED, ERROR }
	}
	case class MigratingVm(
	  /** Details of the target VM in Compute Engine. */
		computeEngineTargetDefaults: Option[Schema.ComputeEngineTargetDefaults] = None,
	  /** Details of the target Persistent Disks in Compute Engine. */
		computeEngineDisksTargetDefaults: Option[Schema.ComputeEngineDisksTargetDefaults] = None,
	  /** Output only. Details of the VM from a Vmware source. */
		vmwareSourceVmDetails: Option[Schema.VmwareSourceVmDetails] = None,
	  /** Output only. Details of the VM from an AWS source. */
		awsSourceVmDetails: Option[Schema.AwsSourceVmDetails] = None,
	  /** Output only. Details of the VM from an Azure source. */
		azureSourceVmDetails: Option[Schema.AzureSourceVmDetails] = None,
	  /** Output only. The identifier of the MigratingVm. */
		name: Option[String] = None,
	  /** The unique ID of the VM in the source. The VM's name in vSphere can be changed, so this is not the VM's name but rather its moRef id. This id is of the form vm-. */
		sourceVmId: Option[String] = None,
	  /** The display name attached to the MigratingVm by the user. */
		displayName: Option[String] = None,
	  /** The description attached to the migrating VM by the user. */
		description: Option[String] = None,
	  /** The replication schedule policy. */
		policy: Option[Schema.SchedulePolicy] = None,
	  /** Output only. The time the migrating VM was created (this refers to this resource and not to the time it was installed in the source). */
		createTime: Option[String] = None,
	  /** Output only. The last time the migrating VM resource was updated. */
		updateTime: Option[String] = None,
	  /** Output only. The most updated snapshot created time in the source that finished replication. */
		lastSync: Option[Schema.ReplicationSync] = None,
	  /** Output only. State of the MigratingVm. */
		state: Option[Schema.MigratingVm.StateEnum] = None,
	  /** Output only. The last time the migrating VM state was updated. */
		stateTime: Option[String] = None,
	  /** Output only. Details of the current running replication cycle. */
		currentSyncInfo: Option[Schema.ReplicationCycle] = None,
	  /** Output only. Details of the last replication cycle. This will be updated whenever a replication cycle is finished and is not to be confused with last_sync which is only updated on successful replication cycles. */
		lastReplicationCycle: Option[Schema.ReplicationCycle] = None,
	  /** Output only. The group this migrating vm is included in, if any. The group is represented by the full path of the appropriate Group resource. */
		group: Option[String] = None,
	  /** The labels of the migrating VM. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The recent clone jobs performed on the migrating VM. This field holds the vm's last completed clone job and the vm's running clone job, if one exists. Note: To have this field populated you need to explicitly request it via the "view" parameter of the Get/List request. */
		recentCloneJobs: Option[List[Schema.CloneJob]] = None,
	  /** Output only. Provides details on the state of the Migrating VM in case of an error in replication. */
		error: Option[Schema.Status] = None,
	  /** Output only. The recent cutover jobs performed on the migrating VM. This field holds the vm's last completed cutover job and the vm's running cutover job, if one exists. Note: To have this field populated you need to explicitly request it via the "view" parameter of the Get/List request. */
		recentCutoverJobs: Option[List[Schema.CutoverJob]] = None,
	  /** Output only. Provides details of future CutoverJobs of a MigratingVm. Set to empty when cutover forecast is unavailable. */
		cutoverForecast: Option[Schema.CutoverForecast] = None
	)
	
	object ComputeEngineTargetDefaults {
		enum DiskTypeEnum extends Enum[DiskTypeEnum] { case COMPUTE_ENGINE_DISK_TYPE_UNSPECIFIED, COMPUTE_ENGINE_DISK_TYPE_STANDARD, COMPUTE_ENGINE_DISK_TYPE_SSD, COMPUTE_ENGINE_DISK_TYPE_BALANCED, COMPUTE_ENGINE_DISK_TYPE_HYPERDISK_BALANCED }
		enum LicenseTypeEnum extends Enum[LicenseTypeEnum] { case COMPUTE_ENGINE_LICENSE_TYPE_DEFAULT, COMPUTE_ENGINE_LICENSE_TYPE_PAYG, COMPUTE_ENGINE_LICENSE_TYPE_BYOL }
		enum BootOptionEnum extends Enum[BootOptionEnum] { case COMPUTE_ENGINE_BOOT_OPTION_UNSPECIFIED, COMPUTE_ENGINE_BOOT_OPTION_EFI, COMPUTE_ENGINE_BOOT_OPTION_BIOS }
		enum BootConversionEnum extends Enum[BootConversionEnum] { case BOOT_CONVERSION_UNSPECIFIED, NONE, BIOS_TO_EFI }
	}
	case class ComputeEngineTargetDefaults(
	  /** The name of the VM to create. */
		vmName: Option[String] = None,
	  /** The full path of the resource of type TargetProject which represents the Compute Engine project in which to create this VM. */
		targetProject: Option[String] = None,
	  /** The zone in which to create the VM. */
		zone: Option[String] = None,
	  /** The machine type series to create the VM with. */
		machineTypeSeries: Option[String] = None,
	  /** The machine type to create the VM with. */
		machineType: Option[String] = None,
	  /** A list of network tags to associate with the VM. */
		networkTags: Option[List[String]] = None,
	  /** List of NICs connected to this VM. */
		networkInterfaces: Option[List[Schema.NetworkInterface]] = None,
	  /** The service account to associate the VM with. */
		serviceAccount: Option[String] = None,
	  /** The disk type to use in the VM. */
		diskType: Option[Schema.ComputeEngineTargetDefaults.DiskTypeEnum] = None,
	  /** A map of labels to associate with the VM. */
		labels: Option[Map[String, String]] = None,
	  /** The license type to use in OS adaptation. */
		licenseType: Option[Schema.ComputeEngineTargetDefaults.LicenseTypeEnum] = None,
	  /** Output only. The OS license returned from the adaptation module report. */
		appliedLicense: Option[Schema.AppliedLicense] = None,
	  /** Compute instance scheduling information (if empty default is used). */
		computeScheduling: Option[Schema.ComputeScheduling] = None,
	  /** Defines whether the instance has Secure Boot enabled. This can be set to true only if the VM boot option is EFI. */
		secureBoot: Option[Boolean] = None,
	  /** Optional. Defines whether the instance has vTPM enabled. This can be set to true only if the VM boot option is EFI. */
		enableVtpm: Option[Boolean] = None,
	  /** Optional. Defines whether the instance has integrity monitoring enabled. This can be set to true only if the VM boot option is EFI, and vTPM is enabled. */
		enableIntegrityMonitoring: Option[Boolean] = None,
	  /** Output only. The VM Boot Option, as set in the source VM. */
		bootOption: Option[Schema.ComputeEngineTargetDefaults.BootOptionEnum] = None,
	  /** The metadata key/value pairs to assign to the VM. */
		metadata: Option[Map[String, String]] = None,
	  /** Additional licenses to assign to the VM. */
		additionalLicenses: Option[List[String]] = None,
	  /** The hostname to assign to the VM. */
		hostname: Option[String] = None,
	  /** Optional. Immutable. The encryption to apply to the VM disks. */
		encryption: Option[Schema.Encryption] = None,
	  /** Optional. By default the virtual machine will keep its existing boot option. Setting this property will trigger an internal process which will convert the virtual machine from using the existing boot option to another. */
		bootConversion: Option[Schema.ComputeEngineTargetDefaults.BootConversionEnum] = None
	)
	
	object NetworkInterface {
		enum NetworkTierEnum extends Enum[NetworkTierEnum] { case COMPUTE_ENGINE_NETWORK_TIER_UNSPECIFIED, NETWORK_TIER_STANDARD, NETWORK_TIER_PREMIUM }
	}
	case class NetworkInterface(
	  /** The network to connect the NIC to. */
		network: Option[String] = None,
	  /** Optional. The subnetwork to connect the NIC to. */
		subnetwork: Option[String] = None,
	  /** Optional. The internal IP to define in the NIC. The formats accepted are: `ephemeral` \ ipv4 address \ a named address resource full path. */
		internalIp: Option[String] = None,
	  /** Optional. The external IP to define in the NIC. */
		externalIp: Option[String] = None,
	  /** Optional. The networking tier used for optimizing connectivity between instances and systems on the internet. Applies only for external ephemeral IP addresses. If left empty, will default to PREMIUM. */
		networkTier: Option[Schema.NetworkInterface.NetworkTierEnum] = None
	)
	
	object AppliedLicense {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, NONE, PAYG, BYOL }
	}
	case class AppliedLicense(
	  /** The license type that was used in OS adaptation. */
		`type`: Option[Schema.AppliedLicense.TypeEnum] = None,
	  /** The OS license returned from the adaptation module's report. */
		osLicense: Option[String] = None
	)
	
	object ComputeScheduling {
		enum OnHostMaintenanceEnum extends Enum[OnHostMaintenanceEnum] { case ON_HOST_MAINTENANCE_UNSPECIFIED, TERMINATE, MIGRATE }
		enum RestartTypeEnum extends Enum[RestartTypeEnum] { case RESTART_TYPE_UNSPECIFIED, AUTOMATIC_RESTART, NO_AUTOMATIC_RESTART }
	}
	case class ComputeScheduling(
	  /** How the instance should behave when the host machine undergoes maintenance that may temporarily impact instance performance. */
		onHostMaintenance: Option[Schema.ComputeScheduling.OnHostMaintenanceEnum] = None,
	  /** Whether the Instance should be automatically restarted whenever it is terminated by Compute Engine (not terminated by user). This configuration is identical to `automaticRestart` field in Compute Engine create instance under scheduling. It was changed to an enum (instead of a boolean) to match the default value in Compute Engine which is automatic restart. */
		restartType: Option[Schema.ComputeScheduling.RestartTypeEnum] = None,
	  /** A set of node affinity and anti-affinity configurations for sole tenant nodes. */
		nodeAffinities: Option[List[Schema.SchedulingNodeAffinity]] = None,
	  /** The minimum number of virtual CPUs this instance will consume when running on a sole-tenant node. Ignored if no node_affinites are configured. */
		minNodeCpus: Option[Int] = None
	)
	
	object SchedulingNodeAffinity {
		enum OperatorEnum extends Enum[OperatorEnum] { case OPERATOR_UNSPECIFIED, IN, NOT_IN }
	}
	case class SchedulingNodeAffinity(
	  /** The label key of Node resource to reference. */
		key: Option[String] = None,
	  /** The operator to use for the node resources specified in the `values` parameter. */
		operator: Option[Schema.SchedulingNodeAffinity.OperatorEnum] = None,
	  /** Corresponds to the label values of Node resource. */
		values: Option[List[String]] = None
	)
	
	case class ComputeEngineDisksTargetDefaults(
	  /** The zone in which to create the Persistent Disks. */
		zone: Option[String] = None,
	  /** Details of the disk only migration target. */
		disksTargetDefaults: Option[Schema.DisksMigrationDisksTargetDefaults] = None,
	  /** Details of the VM migration target. */
		vmTargetDefaults: Option[Schema.DisksMigrationVmTargetDefaults] = None,
	  /** The full path of the resource of type TargetProject which represents the Compute Engine project in which to create the Persistent Disks. */
		targetProject: Option[String] = None,
	  /** The details of each Persistent Disk to create. */
		disks: Option[List[Schema.PersistentDiskDefaults]] = None
	)
	
	case class DisksMigrationDisksTargetDefaults(
	
	)
	
	case class DisksMigrationVmTargetDefaults(
	  /** Required. The name of the VM to create. */
		vmName: Option[String] = None,
	  /** Optional. The machine type series to create the VM with. For presentation only. */
		machineTypeSeries: Option[String] = None,
	  /** Required. The machine type to create the VM with. */
		machineType: Option[String] = None,
	  /** Optional. A list of network tags to associate with the VM. */
		networkTags: Option[List[String]] = None,
	  /** Optional. NICs to attach to the VM. */
		networkInterfaces: Option[List[Schema.NetworkInterface]] = None,
	  /** Optional. The service account to associate the VM with. */
		serviceAccount: Option[String] = None,
	  /** Optional. Compute instance scheduling information (if empty default is used). */
		computeScheduling: Option[Schema.ComputeScheduling] = None,
	  /** Optional. Defines whether the instance has Secure Boot enabled. This can be set to true only if the VM boot option is EFI. */
		secureBoot: Option[Boolean] = None,
	  /** Optional. Defines whether the instance has vTPM enabled. */
		enableVtpm: Option[Boolean] = None,
	  /** Optional. Defines whether the instance has integrity monitoring enabled. */
		enableIntegrityMonitoring: Option[Boolean] = None,
	  /** Optional. The metadata key/value pairs to assign to the VM. */
		metadata: Option[Map[String, String]] = None,
	  /** Optional. Additional licenses to assign to the VM. */
		additionalLicenses: Option[List[String]] = None,
	  /** Optional. The hostname to assign to the VM. */
		hostname: Option[String] = None,
	  /** Optional. A map of labels to associate with the VM. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Details of the boot disk of the VM. */
		bootDiskDefaults: Option[Schema.BootDiskDefaults] = None,
	  /** Optional. The encryption to apply to the VM. */
		encryption: Option[Schema.Encryption] = None
	)
	
	object BootDiskDefaults {
		enum DiskTypeEnum extends Enum[DiskTypeEnum] { case COMPUTE_ENGINE_DISK_TYPE_UNSPECIFIED, COMPUTE_ENGINE_DISK_TYPE_STANDARD, COMPUTE_ENGINE_DISK_TYPE_SSD, COMPUTE_ENGINE_DISK_TYPE_BALANCED, COMPUTE_ENGINE_DISK_TYPE_HYPERDISK_BALANCED }
	}
	case class BootDiskDefaults(
	  /** The image to use when creating the disk. */
		image: Option[Schema.DiskImageDefaults] = None,
	  /** Optional. The name of the disk. */
		diskName: Option[String] = None,
	  /** Optional. The type of disk provisioning to use for the VM. */
		diskType: Option[Schema.BootDiskDefaults.DiskTypeEnum] = None,
	  /** Optional. Specifies a unique device name of your choice that is reflected into the /dev/disk/by-id/google-&#42; tree of a Linux operating system running within the instance. If not specified, the server chooses a default device name to apply to this disk, in the form persistent-disk-x, where x is a number assigned by Google Compute Engine. This field is only applicable for persistent disks. */
		deviceName: Option[String] = None,
	  /** Optional. The encryption to apply to the boot disk. */
		encryption: Option[Schema.Encryption] = None
	)
	
	case class DiskImageDefaults(
	  /** Required. The Image resource used when creating the disk. */
		sourceImage: Option[String] = None
	)
	
	object PersistentDiskDefaults {
		enum DiskTypeEnum extends Enum[DiskTypeEnum] { case COMPUTE_ENGINE_DISK_TYPE_UNSPECIFIED, COMPUTE_ENGINE_DISK_TYPE_STANDARD, COMPUTE_ENGINE_DISK_TYPE_SSD, COMPUTE_ENGINE_DISK_TYPE_BALANCED, COMPUTE_ENGINE_DISK_TYPE_HYPERDISK_BALANCED }
	}
	case class PersistentDiskDefaults(
	  /** Required. The ordinal number of the source VM disk. */
		sourceDiskNumber: Option[Int] = None,
	  /** Optional. The name of the Persistent Disk to create. */
		diskName: Option[String] = None,
	  /** The disk type to use. */
		diskType: Option[Schema.PersistentDiskDefaults.DiskTypeEnum] = None,
	  /** A map of labels to associate with the Persistent Disk. */
		additionalLabels: Option[Map[String, String]] = None,
	  /** Optional. The encryption to apply to the disk. */
		encryption: Option[Schema.Encryption] = None,
	  /** Optional. Details for attachment of the disk to a VM. Used when the disk is set to be attached to a target VM. */
		vmAttachmentDetails: Option[Schema.VmAttachmentDetails] = None
	)
	
	case class VmAttachmentDetails(
	  /** Optional. Specifies a unique device name of your choice that is reflected into the /dev/disk/by-id/google-&#42; tree of a Linux operating system running within the instance. If not specified, the server chooses a default device name to apply to this disk, in the form persistent-disk-x, where x is a number assigned by Google Compute Engine. This field is only applicable for persistent disks. */
		deviceName: Option[String] = None
	)
	
	object VmwareSourceVmDetails {
		enum FirmwareEnum extends Enum[FirmwareEnum] { case FIRMWARE_UNSPECIFIED, EFI, BIOS }
		enum ArchitectureEnum extends Enum[ArchitectureEnum] { case VM_ARCHITECTURE_UNSPECIFIED, VM_ARCHITECTURE_X86_FAMILY, VM_ARCHITECTURE_ARM64 }
	}
	case class VmwareSourceVmDetails(
	  /** Output only. The firmware type of the source VM. */
		firmware: Option[Schema.VmwareSourceVmDetails.FirmwareEnum] = None,
	  /** Output only. The total size of the disks being migrated in bytes. */
		committedStorageBytes: Option[String] = None,
	  /** Output only. The disks attached to the source VM. */
		disks: Option[List[Schema.VmwareDiskDetails]] = None,
	  /** Output only. Information about VM capabilities needed for some Compute Engine features. */
		vmCapabilitiesInfo: Option[Schema.VmCapabilities] = None,
	  /** Output only. The VM architecture. */
		architecture: Option[Schema.VmwareSourceVmDetails.ArchitectureEnum] = None
	)
	
	case class VmwareDiskDetails(
	  /** Output only. The ordinal number of the disk. */
		diskNumber: Option[Int] = None,
	  /** Output only. Size in GB. */
		sizeGb: Option[String] = None,
	  /** Output only. The disk label. */
		label: Option[String] = None
	)
	
	object VmCapabilities {
		enum OsCapabilitiesEnum extends Enum[OsCapabilitiesEnum] { case OS_CAPABILITY_UNSPECIFIED, OS_CAPABILITY_NVME_STORAGE_ACCESS, OS_CAPABILITY_GVNIC_NETWORK_INTERFACE }
	}
	case class VmCapabilities(
	  /** Output only. Unordered list. List of certain VM OS capabilities needed for some Compute Engine features. */
		osCapabilities: Option[List[Schema.VmCapabilities.OsCapabilitiesEnum]] = None,
	  /** Output only. The last time OS capabilities list was updated. */
		lastOsCapabilitiesUpdateTime: Option[String] = None
	)
	
	object AwsSourceVmDetails {
		enum FirmwareEnum extends Enum[FirmwareEnum] { case FIRMWARE_UNSPECIFIED, EFI, BIOS }
		enum ArchitectureEnum extends Enum[ArchitectureEnum] { case VM_ARCHITECTURE_UNSPECIFIED, VM_ARCHITECTURE_X86_FAMILY, VM_ARCHITECTURE_ARM64 }
	}
	case class AwsSourceVmDetails(
	  /** Output only. The firmware type of the source VM. */
		firmware: Option[Schema.AwsSourceVmDetails.FirmwareEnum] = None,
	  /** Output only. The total size of the disks being migrated in bytes. */
		committedStorageBytes: Option[String] = None,
	  /** Output only. The disks attached to the source VM. */
		disks: Option[List[Schema.AwsDiskDetails]] = None,
	  /** Output only. Information about VM capabilities needed for some Compute Engine features. */
		vmCapabilitiesInfo: Option[Schema.VmCapabilities] = None,
	  /** Output only. The VM architecture. */
		architecture: Option[Schema.AwsSourceVmDetails.ArchitectureEnum] = None
	)
	
	case class AwsDiskDetails(
	  /** Output only. The ordinal number of the disk. */
		diskNumber: Option[Int] = None,
	  /** Output only. AWS volume ID. */
		volumeId: Option[String] = None,
	  /** Output only. Size in GB. */
		sizeGb: Option[String] = None
	)
	
	object AzureSourceVmDetails {
		enum FirmwareEnum extends Enum[FirmwareEnum] { case FIRMWARE_UNSPECIFIED, EFI, BIOS }
		enum ArchitectureEnum extends Enum[ArchitectureEnum] { case VM_ARCHITECTURE_UNSPECIFIED, VM_ARCHITECTURE_X86_FAMILY, VM_ARCHITECTURE_ARM64 }
	}
	case class AzureSourceVmDetails(
	  /** Output only. The firmware type of the source VM. */
		firmware: Option[Schema.AzureSourceVmDetails.FirmwareEnum] = None,
	  /** Output only. The total size of the disks being migrated in bytes. */
		committedStorageBytes: Option[String] = None,
	  /** Output only. The disks attached to the source VM. */
		disks: Option[List[Schema.AzureDiskDetails]] = None,
	  /** Output only. Information about VM capabilities needed for some Compute Engine features. */
		vmCapabilitiesInfo: Option[Schema.VmCapabilities] = None,
	  /** Output only. The VM architecture. */
		architecture: Option[Schema.AzureSourceVmDetails.ArchitectureEnum] = None
	)
	
	case class AzureDiskDetails(
	  /** Output only. The ordinal number of the disk. */
		diskNumber: Option[Int] = None,
	  /** Output only. Azure disk ID. */
		diskId: Option[String] = None,
	  /** Output only. Size in GB. */
		sizeGb: Option[String] = None
	)
	
	case class SchedulePolicy(
	  /** The idle duration between replication stages. */
		idleDuration: Option[String] = None,
	  /** A flag to indicate whether to skip OS adaptation during the replication sync. OS adaptation is a process where the VM's operating system undergoes changes and adaptations to fully function on Compute Engine. */
		skipOsAdaptation: Option[Boolean] = None
	)
	
	case class ReplicationSync(
	  /** The most updated snapshot created time in the source that finished replication. */
		lastSyncTime: Option[String] = None
	)
	
	object ReplicationCycle {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RUNNING, PAUSED, FAILED, SUCCEEDED }
	}
	case class ReplicationCycle(
	  /** The identifier of the ReplicationCycle. */
		name: Option[String] = None,
	  /** The cycle's ordinal number. */
		cycleNumber: Option[Int] = None,
	  /** The time the replication cycle has started. */
		startTime: Option[String] = None,
	  /** The time the replication cycle has ended. */
		endTime: Option[String] = None,
	  /** The accumulated duration the replication cycle was paused. */
		totalPauseDuration: Option[String] = None,
	  /** The current progress in percentage of this cycle. Was replaced by 'steps' field, which breaks down the cycle progression more accurately. */
		progressPercent: Option[Int] = None,
	  /** The cycle's steps list representing its progress. */
		steps: Option[List[Schema.CycleStep]] = None,
	  /** State of the ReplicationCycle. */
		state: Option[Schema.ReplicationCycle.StateEnum] = None,
	  /** Output only. Provides details on the state of the cycle in case of an error. */
		error: Option[Schema.Status] = None,
	  /** Output only. Warnings that occurred during the cycle. */
		warnings: Option[List[Schema.MigrationWarning]] = None
	)
	
	case class CycleStep(
	  /** Initializing replication step. */
		initializingReplication: Option[Schema.InitializingReplicationStep] = None,
	  /** Replicating step. */
		replicating: Option[Schema.ReplicatingStep] = None,
	  /** Post processing step. */
		postProcessing: Option[Schema.PostProcessingStep] = None,
	  /** The time the cycle step has started. */
		startTime: Option[String] = None,
	  /** The time the cycle step has ended. */
		endTime: Option[String] = None
	)
	
	case class InitializingReplicationStep(
	
	)
	
	case class ReplicatingStep(
	  /** Total bytes to be handled in the step. */
		totalBytes: Option[String] = None,
	  /** Replicated bytes in the step. */
		replicatedBytes: Option[String] = None,
	  /** The source disks replication rate for the last 2 minutes in bytes per second. */
		lastTwoMinutesAverageBytesPerSecond: Option[String] = None,
	  /** The source disks replication rate for the last 30 minutes in bytes per second. */
		lastThirtyMinutesAverageBytesPerSecond: Option[String] = None
	)
	
	case class PostProcessingStep(
	
	)
	
	object MigrationWarning {
		enum CodeEnum extends Enum[CodeEnum] { case WARNING_CODE_UNSPECIFIED, ADAPTATION_WARNING }
	}
	case class MigrationWarning(
	  /** The warning code. */
		code: Option[Schema.MigrationWarning.CodeEnum] = None,
	  /** Output only. The localized warning message. */
		warningMessage: Option[Schema.LocalizedMessage] = None,
	  /** Output only. Suggested action for solving the warning. */
		actionItem: Option[Schema.LocalizedMessage] = None,
	  /** Output only. URL(s) pointing to additional information on handling the current warning. */
		helpLinks: Option[List[Schema.Link]] = None,
	  /** The time the warning occurred. */
		warningTime: Option[String] = None
	)
	
	case class LocalizedMessage(
	  /** The locale used following the specification defined at https://www.rfc-editor.org/rfc/bcp/bcp47.txt. Examples are: "en-US", "fr-CH", "es-MX" */
		locale: Option[String] = None,
	  /** The localized error message in the above locale. */
		message: Option[String] = None
	)
	
	case class Link(
	  /** Describes what the link offers. */
		description: Option[String] = None,
	  /** The URL of the link. */
		url: Option[String] = None
	)
	
	object CloneJob {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, ACTIVE, FAILED, SUCCEEDED, CANCELLED, CANCELLING, ADAPTING_OS }
	}
	case class CloneJob(
	  /** Output only. Details of the target VM in Compute Engine. */
		computeEngineTargetDetails: Option[Schema.ComputeEngineTargetDetails] = None,
	  /** Output only. Details of the target Persistent Disks in Compute Engine. */
		computeEngineDisksTargetDetails: Option[Schema.ComputeEngineDisksTargetDetails] = None,
	  /** Output only. The time the clone job was created (as an API call, not when it was actually created in the target). */
		createTime: Option[String] = None,
	  /** Output only. The time the clone job was ended. */
		endTime: Option[String] = None,
	  /** Output only. The name of the clone. */
		name: Option[String] = None,
	  /** Output only. State of the clone job. */
		state: Option[Schema.CloneJob.StateEnum] = None,
	  /** Output only. The time the state was last updated. */
		stateTime: Option[String] = None,
	  /** Output only. Provides details for the errors that led to the Clone Job's state. */
		error: Option[Schema.Status] = None,
	  /** Output only. The clone steps list representing its progress. */
		steps: Option[List[Schema.CloneStep]] = None
	)
	
	object ComputeEngineTargetDetails {
		enum DiskTypeEnum extends Enum[DiskTypeEnum] { case COMPUTE_ENGINE_DISK_TYPE_UNSPECIFIED, COMPUTE_ENGINE_DISK_TYPE_STANDARD, COMPUTE_ENGINE_DISK_TYPE_SSD, COMPUTE_ENGINE_DISK_TYPE_BALANCED, COMPUTE_ENGINE_DISK_TYPE_HYPERDISK_BALANCED }
		enum LicenseTypeEnum extends Enum[LicenseTypeEnum] { case COMPUTE_ENGINE_LICENSE_TYPE_DEFAULT, COMPUTE_ENGINE_LICENSE_TYPE_PAYG, COMPUTE_ENGINE_LICENSE_TYPE_BYOL }
		enum BootOptionEnum extends Enum[BootOptionEnum] { case COMPUTE_ENGINE_BOOT_OPTION_UNSPECIFIED, COMPUTE_ENGINE_BOOT_OPTION_EFI, COMPUTE_ENGINE_BOOT_OPTION_BIOS }
		enum BootConversionEnum extends Enum[BootConversionEnum] { case BOOT_CONVERSION_UNSPECIFIED, NONE, BIOS_TO_EFI }
	}
	case class ComputeEngineTargetDetails(
	  /** The name of the VM to create. */
		vmName: Option[String] = None,
	  /** The Google Cloud target project ID or project name. */
		project: Option[String] = None,
	  /** The zone in which to create the VM. */
		zone: Option[String] = None,
	  /** The machine type series to create the VM with. */
		machineTypeSeries: Option[String] = None,
	  /** The machine type to create the VM with. */
		machineType: Option[String] = None,
	  /** A list of network tags to associate with the VM. */
		networkTags: Option[List[String]] = None,
	  /** List of NICs connected to this VM. */
		networkInterfaces: Option[List[Schema.NetworkInterface]] = None,
	  /** The service account to associate the VM with. */
		serviceAccount: Option[String] = None,
	  /** The disk type to use in the VM. */
		diskType: Option[Schema.ComputeEngineTargetDetails.DiskTypeEnum] = None,
	  /** A map of labels to associate with the VM. */
		labels: Option[Map[String, String]] = None,
	  /** The license type to use in OS adaptation. */
		licenseType: Option[Schema.ComputeEngineTargetDetails.LicenseTypeEnum] = None,
	  /** The OS license returned from the adaptation module report. */
		appliedLicense: Option[Schema.AppliedLicense] = None,
	  /** Compute instance scheduling information (if empty default is used). */
		computeScheduling: Option[Schema.ComputeScheduling] = None,
	  /** Defines whether the instance has Secure Boot enabled. This can be set to true only if the VM boot option is EFI. */
		secureBoot: Option[Boolean] = None,
	  /** Optional. Defines whether the instance has vTPM enabled. */
		enableVtpm: Option[Boolean] = None,
	  /** Optional. Defines whether the instance has integrity monitoring enabled. */
		enableIntegrityMonitoring: Option[Boolean] = None,
	  /** The VM Boot Option, as set in the source VM. */
		bootOption: Option[Schema.ComputeEngineTargetDetails.BootOptionEnum] = None,
	  /** The metadata key/value pairs to assign to the VM. */
		metadata: Option[Map[String, String]] = None,
	  /** Additional licenses to assign to the VM. */
		additionalLicenses: Option[List[String]] = None,
	  /** The hostname to assign to the VM. */
		hostname: Option[String] = None,
	  /** Optional. The encryption to apply to the VM disks. */
		encryption: Option[Schema.Encryption] = None,
	  /** Optional. By default the virtual machine will keep its existing boot option. Setting this property will trigger an internal process which will convert the virtual machine from using the existing boot option to another. */
		bootConversion: Option[Schema.ComputeEngineTargetDetails.BootConversionEnum] = None
	)
	
	case class ComputeEngineDisksTargetDetails(
	  /** Details of the disks-only migration target. */
		disksTargetDetails: Option[Schema.DisksMigrationDisksTargetDetails] = None,
	  /** Details for the VM the migrated data disks are attached to. */
		vmTargetDetails: Option[Schema.DisksMigrationVmTargetDetails] = None,
	  /** The details of each created Persistent Disk. */
		disks: Option[List[Schema.PersistentDisk]] = None
	)
	
	case class DisksMigrationDisksTargetDetails(
	
	)
	
	case class DisksMigrationVmTargetDetails(
	  /** Output only. The URI of the Compute Engine VM. */
		vmUri: Option[String] = None
	)
	
	case class PersistentDisk(
	  /** The ordinal number of the source VM disk. */
		sourceDiskNumber: Option[Int] = None,
	  /** The URI of the Persistent Disk. */
		diskUri: Option[String] = None
	)
	
	case class CloneStep(
	  /** Adapting OS step. */
		adaptingOs: Option[Schema.AdaptingOSStep] = None,
	  /** Preparing VM disks step. */
		preparingVmDisks: Option[Schema.PreparingVMDisksStep] = None,
	  /** Instantiating migrated VM step. */
		instantiatingMigratedVm: Option[Schema.InstantiatingMigratedVMStep] = None,
	  /** The time the step has started. */
		startTime: Option[String] = None,
	  /** The time the step has ended. */
		endTime: Option[String] = None
	)
	
	case class AdaptingOSStep(
	
	)
	
	case class PreparingVMDisksStep(
	
	)
	
	case class InstantiatingMigratedVMStep(
	
	)
	
	object CutoverJob {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, FAILED, SUCCEEDED, CANCELLED, CANCELLING, ACTIVE, ADAPTING_OS }
	}
	case class CutoverJob(
	  /** Output only. Details of the target VM in Compute Engine. */
		computeEngineTargetDetails: Option[Schema.ComputeEngineTargetDetails] = None,
	  /** Output only. Details of the target Persistent Disks in Compute Engine. */
		computeEngineDisksTargetDetails: Option[Schema.ComputeEngineDisksTargetDetails] = None,
	  /** Output only. The time the cutover job was created (as an API call, not when it was actually created in the target). */
		createTime: Option[String] = None,
	  /** Output only. The time the cutover job had finished. */
		endTime: Option[String] = None,
	  /** Output only. The name of the cutover job. */
		name: Option[String] = None,
	  /** Output only. State of the cutover job. */
		state: Option[Schema.CutoverJob.StateEnum] = None,
	  /** Output only. The time the state was last updated. */
		stateTime: Option[String] = None,
	  /** Output only. The current progress in percentage of the cutover job. */
		progressPercent: Option[Int] = None,
	  /** Output only. Provides details for the errors that led to the Cutover Job's state. */
		error: Option[Schema.Status] = None,
	  /** Output only. A message providing possible extra details about the current state. */
		stateMessage: Option[String] = None,
	  /** Output only. The cutover steps list representing its progress. */
		steps: Option[List[Schema.CutoverStep]] = None
	)
	
	case class CutoverStep(
	  /** A replication cycle prior cutover step. */
		previousReplicationCycle: Option[Schema.ReplicationCycle] = None,
	  /** Shutting down VM step. */
		shuttingDownSourceVm: Option[Schema.ShuttingDownSourceVMStep] = None,
	  /** Final sync step. */
		finalSync: Option[Schema.ReplicationCycle] = None,
	  /** Preparing VM disks step. */
		preparingVmDisks: Option[Schema.PreparingVMDisksStep] = None,
	  /** Instantiating migrated VM step. */
		instantiatingMigratedVm: Option[Schema.InstantiatingMigratedVMStep] = None,
	  /** The time the step has started. */
		startTime: Option[String] = None,
	  /** The time the step has ended. */
		endTime: Option[String] = None
	)
	
	case class ShuttingDownSourceVMStep(
	
	)
	
	case class CutoverForecast(
	  /** Output only. Estimation of the CutoverJob duration. */
		estimatedCutoverJobDuration: Option[String] = None
	)
	
	case class ListMigratingVmsResponse(
	  /** Output only. The list of Migrating VMs response. */
		migratingVms: Option[List[Schema.MigratingVm]] = None,
	  /** Output only. A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Output only. Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class StartMigrationRequest(
	
	)
	
	case class ResumeMigrationRequest(
	
	)
	
	case class PauseMigrationRequest(
	
	)
	
	case class FinalizeMigrationRequest(
	
	)
	
	case class CancelCloneJobRequest(
	
	)
	
	case class ListCloneJobsResponse(
	  /** Output only. The list of clone jobs response. */
		cloneJobs: Option[List[Schema.CloneJob]] = None,
	  /** Output only. A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Output only. Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class CancelCutoverJobRequest(
	
	)
	
	case class ListCutoverJobsResponse(
	  /** Output only. The list of cutover jobs response. */
		cutoverJobs: Option[List[Schema.CutoverJob]] = None,
	  /** Output only. A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Output only. Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ListGroupsResponse(
	  /** Output only. The list of groups response. */
		groups: Option[List[Schema.Group]] = None,
	  /** Output only. A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Output only. Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Group {
		enum MigrationTargetTypeEnum extends Enum[MigrationTargetTypeEnum] { case MIGRATION_TARGET_TYPE_UNSPECIFIED, MIGRATION_TARGET_TYPE_GCE, MIGRATION_TARGET_TYPE_DISKS }
	}
	case class Group(
	  /** Output only. The Group name. */
		name: Option[String] = None,
	  /** Output only. The create time timestamp. */
		createTime: Option[String] = None,
	  /** Output only. The update time timestamp. */
		updateTime: Option[String] = None,
	  /** User-provided description of the group. */
		description: Option[String] = None,
	  /** Display name is a user defined name for this group which can be updated. */
		displayName: Option[String] = None,
	  /** Immutable. The target type of this group. */
		migrationTargetType: Option[Schema.Group.MigrationTargetTypeEnum] = None
	)
	
	case class AddGroupMigrationRequest(
	  /** The full path name of the MigratingVm to add. */
		migratingVm: Option[String] = None
	)
	
	case class RemoveGroupMigrationRequest(
	  /** The MigratingVm to remove. */
		migratingVm: Option[String] = None
	)
	
	case class ListTargetProjectsResponse(
	  /** Output only. The list of target response. */
		targetProjects: Option[List[Schema.TargetProject]] = None,
	  /** Output only. A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Output only. Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class TargetProject(
	  /** Output only. The name of the target project. */
		name: Option[String] = None,
	  /** Required. The target project ID (number) or project name. */
		project: Option[String] = None,
	  /** The target project's description. */
		description: Option[String] = None,
	  /** Output only. The time this target project resource was created (not related to when the Compute Engine project it points to was created). */
		createTime: Option[String] = None,
	  /** Output only. The last time the target project resource was updated. */
		updateTime: Option[String] = None
	)
	
	case class ListReplicationCyclesResponse(
	  /** Output only. The list of replication cycles response. */
		replicationCycles: Option[List[Schema.ReplicationCycle]] = None,
	  /** Output only. A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Output only. Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ListImageImportsResponse(
	  /** Output only. The list of target response. */
		imageImports: Option[List[Schema.ImageImport]] = None,
	  /** Output only. A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Output only. Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ImageImport(
	  /** Immutable. The path to the Cloud Storage file from which the image should be imported. */
		cloudStorageUri: Option[String] = None,
	  /** Immutable. Target details for importing a disk image, will be used by ImageImportJob. */
		diskImageTargetDefaults: Option[Schema.DiskImageTargetDetails] = None,
	  /** Immutable. Target details for importing a machine image, will be used by ImageImportJob. */
		machineImageTargetDefaults: Option[Schema.MachineImageTargetDetails] = None,
	  /** Output only. The resource path of the ImageImport. */
		name: Option[String] = None,
	  /** Output only. The time the image import was created. */
		createTime: Option[String] = None,
	  /** Output only. The result of the most recent runs for this ImageImport. All jobs for this ImageImport can be listed via ListImageImportJobs. */
		recentImageImportJobs: Option[List[Schema.ImageImportJob]] = None,
	  /** Immutable. The encryption details used by the image import process during the image adaptation for Compute Engine. */
		encryption: Option[Schema.Encryption] = None
	)
	
	case class DiskImageTargetDetails(
	  /** Optional. Use to set the parameters relevant for the OS adaptation process. */
		osAdaptationParameters: Option[Schema.ImageImportOsAdaptationParameters] = None,
	  /** Optional. Use to skip OS adaptation process. */
		dataDiskImageImport: Option[Schema.DataDiskImageImport] = None,
	  /** Required. The name of the image to be created. */
		imageName: Option[String] = None,
	  /** Required. Reference to the TargetProject resource that represents the target project in which the imported image will be created. */
		targetProject: Option[String] = None,
	  /** Optional. An optional description of the image. */
		description: Option[String] = None,
	  /** Optional. The name of the image family to which the new image belongs. */
		familyName: Option[String] = None,
	  /** Optional. A map of labels to associate with the image. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Additional licenses to assign to the image. Format: https://www.googleapis.com/compute/v1/projects/PROJECT_ID/global/licenses/LICENSE_NAME Or https://www.googleapis.com/compute/beta/projects/PROJECT_ID/global/licenses/LICENSE_NAME */
		additionalLicenses: Option[List[String]] = None,
	  /** Optional. Set to true to set the image storageLocations to the single region of the import job. When false, the closest multi-region is selected. */
		singleRegionStorage: Option[Boolean] = None,
	  /** Immutable. The encryption to apply to the image. */
		encryption: Option[Schema.Encryption] = None
	)
	
	object ImageImportOsAdaptationParameters {
		enum LicenseTypeEnum extends Enum[LicenseTypeEnum] { case COMPUTE_ENGINE_LICENSE_TYPE_DEFAULT, COMPUTE_ENGINE_LICENSE_TYPE_PAYG, COMPUTE_ENGINE_LICENSE_TYPE_BYOL }
	}
	case class ImageImportOsAdaptationParameters(
	  /** Optional. Set to true in order to generalize the imported image. The generalization process enables co-existence of multiple VMs created from the same image. For Windows, generalizing the image removes computer-specific information such as installed drivers and the computer security identifier (SID). */
		generalize: Option[Boolean] = None,
	  /** Optional. Choose which type of license to apply to the imported image. */
		licenseType: Option[Schema.ImageImportOsAdaptationParameters.LicenseTypeEnum] = None
	)
	
	case class DataDiskImageImport(
	
	)
	
	case class MachineImageTargetDetails(
	  /** Optional. Use to set the parameters relevant for the OS adaptation process. */
		osAdaptationParameters: Option[Schema.ImageImportOsAdaptationParameters] = None,
	  /** Optional. Use to skip OS adaptation process. */
		skipOsAdaptation: Option[Schema.SkipOsAdaptation] = None,
	  /** Required. The name of the machine image to be created. */
		machineImageName: Option[String] = None,
	  /** Required. Reference to the TargetProject resource that represents the target project in which the imported machine image will be created. */
		targetProject: Option[String] = None,
	  /** Optional. An optional description of the machine image. */
		description: Option[String] = None,
	  /** Optional. Set to true to set the machine image storageLocations to the single region of the import job. When false, the closest multi-region is selected. */
		singleRegionStorage: Option[Boolean] = None,
	  /** Immutable. The encryption to apply to the machine image. */
		encryption: Option[Schema.Encryption] = None,
	  /** Optional. Parameters overriding decisions based on the source machine image configurations. */
		machineImageParametersOverrides: Option[Schema.MachineImageParametersOverrides] = None,
	  /** Optional. The service account to assign to the instance created by the machine image. */
		serviceAccount: Option[Schema.ServiceAccount] = None,
	  /** Optional. Additional licenses to assign to the instance created by the machine image. Format: https://www.googleapis.com/compute/v1/projects/PROJECT_ID/global/licenses/LICENSE_NAME Or https://www.googleapis.com/compute/beta/projects/PROJECT_ID/global/licenses/LICENSE_NAME */
		additionalLicenses: Option[List[String]] = None,
	  /** Optional. The labels to apply to the instance created by the machine image. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. The tags to apply to the instance created by the machine image. */
		tags: Option[List[String]] = None,
	  /** Optional. Shielded instance configuration. */
		shieldedInstanceConfig: Option[Schema.ShieldedInstanceConfig] = None,
	  /** Optional. The network interfaces to create with the instance created by the machine image. Internal and external IP addresses are ignored for machine image import. */
		networkInterfaces: Option[List[Schema.NetworkInterface]] = None
	)
	
	case class SkipOsAdaptation(
	
	)
	
	case class MachineImageParametersOverrides(
	  /** Optional. The machine type to create the MachineImage with. If empty, the service will choose a relevant machine type based on the information from the source image. For more information about machine types, please refer to https://cloud.google.com/compute/docs/machine-resource. */
		machineType: Option[String] = None
	)
	
	case class ServiceAccount(
	  /** Required. The email address of the service account. */
		email: Option[String] = None,
	  /** Optional. The list of scopes to be made available for this service account. */
		scopes: Option[List[String]] = None
	)
	
	object ShieldedInstanceConfig {
		enum SecureBootEnum extends Enum[SecureBootEnum] { case SECURE_BOOT_UNSPECIFIED, TRUE, FALSE }
	}
	case class ShieldedInstanceConfig(
	  /** Optional. Defines whether the instance created by the machine image has Secure Boot enabled. This can be set to true only if the image boot option is EFI. */
		secureBoot: Option[Schema.ShieldedInstanceConfig.SecureBootEnum] = None,
	  /** Optional. Defines whether the instance created by the machine image has vTPM enabled. This can be set to true only if the image boot option is EFI. */
		enableVtpm: Option[Boolean] = None,
	  /** Optional. Defines whether the instance created by the machine image has integrity monitoring enabled. This can be set to true only if the image boot option is EFI, and vTPM is enabled. */
		enableIntegrityMonitoring: Option[Boolean] = None
	)
	
	object ImageImportJob {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, RUNNING, SUCCEEDED, FAILED, CANCELLING, CANCELLED }
	}
	case class ImageImportJob(
	  /** Output only. The path to the Cloud Storage file from which the image should be imported. */
		cloudStorageUri: Option[String] = None,
	  /** Output only. Target details used to import a disk image. */
		diskImageTargetDetails: Option[Schema.DiskImageTargetDetails] = None,
	  /** Output only. Target details used to import a machine image. */
		machineImageTargetDetails: Option[Schema.MachineImageTargetDetails] = None,
	  /** Output only. The resource path of the ImageImportJob. */
		name: Option[String] = None,
	  /** Output only. The resource paths of the resources created by the image import job. */
		createdResources: Option[List[String]] = None,
	  /** Output only. The state of the image import. */
		state: Option[Schema.ImageImportJob.StateEnum] = None,
	  /** Output only. The time the image import was created (as an API call, not when it was actually created in the target). */
		createTime: Option[String] = None,
	  /** Output only. The time the image import was ended. */
		endTime: Option[String] = None,
	  /** Output only. Provides details on the error that led to the image import state in case of an error. */
		errors: Option[List[Schema.Status]] = None,
	  /** Output only. Warnings that occurred during the image import. */
		warnings: Option[List[Schema.MigrationWarning]] = None,
	  /** Output only. The image import steps list representing its progress. */
		steps: Option[List[Schema.ImageImportStep]] = None
	)
	
	case class ImageImportStep(
	  /** Initializing step. */
		initializing: Option[Schema.InitializingImageImportStep] = None,
	  /** Loading source files step. */
		loadingSourceFiles: Option[Schema.LoadingImageSourceFilesStep] = None,
	  /** Adapting OS step. */
		adaptingOs: Option[Schema.AdaptingOSStep] = None,
	  /** Creating image step. */
		creatingImage: Option[Schema.CreatingImageStep] = None,
	  /** Output only. The time the step has started. */
		startTime: Option[String] = None,
	  /** Output only. The time the step has ended. */
		endTime: Option[String] = None
	)
	
	case class InitializingImageImportStep(
	
	)
	
	case class LoadingImageSourceFilesStep(
	
	)
	
	case class CreatingImageStep(
	
	)
	
	case class ListImageImportJobsResponse(
	  /** Output only. The list of target response. */
		imageImportJobs: Option[List[Schema.ImageImportJob]] = None,
	  /** Output only. A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Output only. Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class CancelImageImportJobRequest(
	
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
		statusMessage: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	object MigrationError {
		enum CodeEnum extends Enum[CodeEnum] { case ERROR_CODE_UNSPECIFIED, UNKNOWN_ERROR, SOURCE_VALIDATION_ERROR, SOURCE_REPLICATION_ERROR, TARGET_REPLICATION_ERROR, OS_ADAPTATION_ERROR, CLONE_ERROR, CUTOVER_ERROR, UTILIZATION_REPORT_ERROR, APPLIANCE_UPGRADE_ERROR, IMAGE_IMPORT_ERROR }
	}
	case class MigrationError(
	  /** Output only. The error code. */
		code: Option[Schema.MigrationError.CodeEnum] = None,
	  /** Output only. The localized error message. */
		errorMessage: Option[Schema.LocalizedMessage] = None,
	  /** Output only. Suggested action for solving the error. */
		actionItem: Option[Schema.LocalizedMessage] = None,
	  /** Output only. URL(s) pointing to additional information on handling the current error. */
		helpLinks: Option[List[Schema.Link]] = None,
	  /** Output only. The time the error occurred. */
		errorTime: Option[String] = None
	)
}
