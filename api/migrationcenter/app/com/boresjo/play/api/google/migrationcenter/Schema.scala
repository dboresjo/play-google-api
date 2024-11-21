package com.boresjo.play.api.google.migrationcenter

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
	
	case class ListAssetsResponse(
	  /** A list of assets. */
		assets: Option[List[Schema.Asset]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class Asset(
	  /** Output only. The full name of the asset. */
		name: Option[String] = None,
	  /** Output only. The timestamp when the asset was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the asset was last updated. */
		updateTime: Option[String] = None,
	  /** Labels as key value pairs. */
		labels: Option[Map[String, String]] = None,
	  /** Generic asset attributes. */
		attributes: Option[Map[String, String]] = None,
	  /** Output only. Asset information specific for virtual and physical machines. */
		machineDetails: Option[Schema.MachineDetails] = None,
	  /** Output only. The list of insights associated with the asset. */
		insightList: Option[Schema.InsightList] = None,
	  /** Output only. Performance data for the asset. */
		performanceData: Option[Schema.AssetPerformanceData] = None,
	  /** Output only. The list of sources contributing to the asset. */
		sources: Option[List[String]] = None,
	  /** Output only. The list of groups that the asset is assigned to. */
		assignedGroups: Option[List[String]] = None
	)
	
	object MachineDetails {
		enum PowerStateEnum extends Enum[PowerStateEnum] { case POWER_STATE_UNSPECIFIED, PENDING, ACTIVE, SUSPENDING, SUSPENDED, DELETING, DELETED }
	}
	case class MachineDetails(
	  /** Machine unique identifier. */
		uuid: Option[String] = None,
	  /** Machine name. */
		machineName: Option[String] = None,
	  /** Machine creation time. */
		createTime: Option[String] = None,
	  /** Number of logical CPU cores in the machine. Must be non-negative. */
		coreCount: Option[Int] = None,
	  /** The amount of memory in the machine. Must be non-negative. */
		memoryMb: Option[Int] = None,
	  /** Power state of the machine. */
		powerState: Option[Schema.MachineDetails.PowerStateEnum] = None,
	  /** Architecture details (vendor, CPU architecture). */
		architecture: Option[Schema.MachineArchitectureDetails] = None,
	  /** Guest OS information. */
		guestOs: Option[Schema.GuestOsDetails] = None,
	  /** Network details. */
		network: Option[Schema.MachineNetworkDetails] = None,
	  /** Disk details. */
		disks: Option[Schema.MachineDiskDetails] = None,
	  /** Platform specific information. */
		platform: Option[Schema.PlatformDetails] = None
	)
	
	object MachineArchitectureDetails {
		enum FirmwareTypeEnum extends Enum[FirmwareTypeEnum] { case FIRMWARE_TYPE_UNSPECIFIED, BIOS, EFI }
		enum HyperthreadingEnum extends Enum[HyperthreadingEnum] { case CPU_HYPER_THREADING_UNSPECIFIED, DISABLED, ENABLED }
	}
	case class MachineArchitectureDetails(
	  /** CPU architecture, e.g., "x64-based PC", "x86_64", "i686" etc. */
		cpuArchitecture: Option[String] = None,
	  /** CPU name, e.g., "Intel Xeon E5-2690", "AMD EPYC 7571" etc. */
		cpuName: Option[String] = None,
	  /** Hardware vendor. */
		vendor: Option[String] = None,
	  /** Deprecated: use MachineDetails.core_count instead. Number of CPU threads allocated to the machine. */
		cpuThreadCount: Option[Int] = None,
	  /** Number of processor sockets allocated to the machine. */
		cpuSocketCount: Option[Int] = None,
	  /** BIOS Details. */
		bios: Option[Schema.BiosDetails] = None,
	  /** Firmware type. */
		firmwareType: Option[Schema.MachineArchitectureDetails.FirmwareTypeEnum] = None,
	  /** CPU hyper-threading support. */
		hyperthreading: Option[Schema.MachineArchitectureDetails.HyperthreadingEnum] = None
	)
	
	case class BiosDetails(
	  /** BIOS name. This fields is deprecated. Please use the `id` field instead. */
		biosName: Option[String] = None,
	  /** BIOS ID. */
		id: Option[String] = None,
	  /** BIOS manufacturer. */
		manufacturer: Option[String] = None,
	  /** BIOS version. */
		version: Option[String] = None,
	  /** BIOS release date. */
		releaseDate: Option[Schema.Date] = None,
	  /** SMBIOS UUID. */
		smbiosUuid: Option[String] = None
	)
	
	case class Date(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	object GuestOsDetails {
		enum FamilyEnum extends Enum[FamilyEnum] { case OS_FAMILY_UNKNOWN, OS_FAMILY_WINDOWS, OS_FAMILY_LINUX, OS_FAMILY_UNIX }
	}
	case class GuestOsDetails(
	  /** The name of the operating system. */
		osName: Option[String] = None,
	  /** What family the OS belong to, if known. */
		family: Option[Schema.GuestOsDetails.FamilyEnum] = None,
	  /** The version of the operating system. */
		version: Option[String] = None,
	  /** OS and app configuration. */
		config: Option[Schema.GuestConfigDetails] = None,
	  /** Runtime information. */
		runtime: Option[Schema.GuestRuntimeDetails] = None
	)
	
	object GuestConfigDetails {
		enum SelinuxModeEnum extends Enum[SelinuxModeEnum] { case SE_LINUX_MODE_UNSPECIFIED, SE_LINUX_MODE_DISABLED, SE_LINUX_MODE_PERMISSIVE, SE_LINUX_MODE_ENFORCING }
	}
	case class GuestConfigDetails(
	  /** OS issue (typically /etc/issue in Linux). */
		issue: Option[String] = None,
	  /** Mount list (Linux fstab). */
		fstab: Option[Schema.FstabEntryList] = None,
	  /** Hosts file (/etc/hosts). */
		hosts: Option[Schema.HostsEntryList] = None,
	  /** NFS exports. */
		nfsExports: Option[Schema.NfsExportList] = None,
	  /** Security-Enhanced Linux (SELinux) mode. */
		selinuxMode: Option[Schema.GuestConfigDetails.SelinuxModeEnum] = None
	)
	
	case class FstabEntryList(
	  /** Fstab entries. */
		entries: Option[List[Schema.FstabEntry]] = None
	)
	
	case class FstabEntry(
	  /** The block special device or remote filesystem to be mounted. */
		spec: Option[String] = None,
	  /** The mount point for the filesystem. */
		file: Option[String] = None,
	  /** The type of the filesystem. */
		vfstype: Option[String] = None,
	  /** Mount options associated with the filesystem. */
		mntops: Option[String] = None,
	  /** Used by dump to determine which filesystems need to be dumped. */
		freq: Option[Int] = None,
	  /** Used by the fsck(8) program to determine the order in which filesystem checks are done at reboot time. */
		passno: Option[Int] = None
	)
	
	case class HostsEntryList(
	  /** Hosts entries. */
		entries: Option[List[Schema.HostsEntry]] = None
	)
	
	case class HostsEntry(
	  /** IP (raw, IPv4/6 agnostic). */
		ip: Option[String] = None,
	  /** List of host names / aliases. */
		hostNames: Option[List[String]] = None
	)
	
	case class NfsExportList(
	  /** NFS export entries. */
		entries: Option[List[Schema.NfsExport]] = None
	)
	
	case class NfsExport(
	  /** The directory being exported. */
		exportDirectory: Option[String] = None,
	  /** The hosts or networks to which the export is being shared. */
		hosts: Option[List[String]] = None
	)
	
	case class GuestRuntimeDetails(
	  /** Running background services. */
		services: Option[Schema.RunningServiceList] = None,
	  /** Running processes. */
		processes: Option[Schema.RunningProcessList] = None,
	  /** Runtime network information (connections, ports). */
		network: Option[Schema.RuntimeNetworkInfo] = None,
	  /** Last time the OS was booted. */
		lastBootTime: Option[String] = None,
	  /** Domain, e.g. c.stratozone-development.internal. */
		domain: Option[String] = None,
	  /** Machine name. */
		machineName: Option[String] = None,
	  /** Installed applications information. */
		installedApps: Option[Schema.GuestInstalledApplicationList] = None,
	  /** Open files information. */
		openFileList: Option[Schema.OpenFileList] = None
	)
	
	case class RunningServiceList(
	  /** Running service entries. */
		entries: Option[List[Schema.RunningService]] = None
	)
	
	object RunningService {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, PAUSED, STOPPED }
		enum StartModeEnum extends Enum[StartModeEnum] { case START_MODE_UNSPECIFIED, BOOT, SYSTEM, AUTO, MANUAL, DISABLED }
	}
	case class RunningService(
	  /** Service name. */
		serviceName: Option[String] = None,
	  /** Service state (OS-agnostic). */
		state: Option[Schema.RunningService.StateEnum] = None,
	  /** Service start mode (OS-agnostic). */
		startMode: Option[Schema.RunningService.StartModeEnum] = None,
	  /** Service binary path. */
		exePath: Option[String] = None,
	  /** Service command line. */
		cmdline: Option[String] = None,
	  /** Service pid. */
		pid: Option[String] = None
	)
	
	case class RunningProcessList(
	  /** Running process entries. */
		entries: Option[List[Schema.RunningProcess]] = None
	)
	
	case class RunningProcess(
	  /** Process ID. */
		pid: Option[String] = None,
	  /** Process binary path. */
		exePath: Option[String] = None,
	  /** Process full command line. */
		cmdline: Option[String] = None,
	  /** User running the process. */
		user: Option[String] = None,
	  /** Process extended attributes. */
		attributes: Option[Map[String, String]] = None
	)
	
	case class RuntimeNetworkInfo(
	  /** Time of the last network scan. */
		scanTime: Option[String] = None,
	  /** Network connections. */
		connections: Option[Schema.NetworkConnectionList] = None
	)
	
	case class NetworkConnectionList(
	  /** Network connection entries. */
		entries: Option[List[Schema.NetworkConnection]] = None
	)
	
	object NetworkConnection {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, OPENING, OPEN, LISTEN, CLOSING, CLOSED }
	}
	case class NetworkConnection(
	  /** Connection protocol (e.g. TCP/UDP). */
		protocol: Option[String] = None,
	  /** Local IP address. */
		localIpAddress: Option[String] = None,
	  /** Local port. */
		localPort: Option[Int] = None,
	  /** Remote IP address. */
		remoteIpAddress: Option[String] = None,
	  /** Remote port. */
		remotePort: Option[Int] = None,
	  /** Network connection state. */
		state: Option[Schema.NetworkConnection.StateEnum] = None,
	  /** Process ID. */
		pid: Option[String] = None,
	  /** Process or service name. */
		processName: Option[String] = None
	)
	
	case class GuestInstalledApplicationList(
	  /** Application entries. */
		entries: Option[List[Schema.GuestInstalledApplication]] = None
	)
	
	case class GuestInstalledApplication(
	  /** Installed application name. */
		applicationName: Option[String] = None,
	  /** Installed application vendor. */
		vendor: Option[String] = None,
	  /** The time when the application was installed. */
		installTime: Option[String] = None,
	  /** Source path. */
		path: Option[String] = None,
	  /** Installed application version. */
		version: Option[String] = None,
	  /** License strings associated with the installed application. */
		licenses: Option[List[String]] = None
	)
	
	case class OpenFileList(
	  /** Open file details entries. */
		entries: Option[List[Schema.OpenFileDetails]] = None
	)
	
	case class OpenFileDetails(
	  /** Opened file command. */
		command: Option[String] = None,
	  /** Opened file user. */
		user: Option[String] = None,
	  /** Opened file file type. */
		fileType: Option[String] = None,
	  /** Opened file file path. */
		filePath: Option[String] = None
	)
	
	case class MachineNetworkDetails(
	  /** The primary IP address of the machine. */
		primaryIpAddress: Option[String] = None,
	  /** The public IP address of the machine. */
		publicIpAddress: Option[String] = None,
	  /** MAC address of the machine. This property is used to uniqly identify the machine. */
		primaryMacAddress: Option[String] = None,
	  /** List of network adapters. */
		adapters: Option[Schema.NetworkAdapterList] = None
	)
	
	case class NetworkAdapterList(
	  /** Network adapter entries. */
		entries: Option[List[Schema.NetworkAdapterDetails]] = None
	)
	
	case class NetworkAdapterDetails(
	  /** Network adapter type (e.g. VMXNET3). */
		adapterType: Option[String] = None,
	  /** MAC address. */
		macAddress: Option[String] = None,
	  /** NetworkAddressList */
		addresses: Option[Schema.NetworkAddressList] = None
	)
	
	case class NetworkAddressList(
	  /** Network address entries. */
		entries: Option[List[Schema.NetworkAddress]] = None
	)
	
	object NetworkAddress {
		enum AssignmentEnum extends Enum[AssignmentEnum] { case ADDRESS_ASSIGNMENT_UNSPECIFIED, ADDRESS_ASSIGNMENT_STATIC, ADDRESS_ASSIGNMENT_DHCP }
	}
	case class NetworkAddress(
	  /** Assigned or configured IP Address. */
		ipAddress: Option[String] = None,
	  /** Subnet mask. */
		subnetMask: Option[String] = None,
	  /** Broadcast address. */
		bcast: Option[String] = None,
	  /** Fully qualified domain name. */
		fqdn: Option[String] = None,
	  /** Whether DHCP is used to assign addresses. */
		assignment: Option[Schema.NetworkAddress.AssignmentEnum] = None
	)
	
	case class MachineDiskDetails(
	  /** Disk total Capacity. */
		totalCapacityBytes: Option[String] = None,
	  /** Total disk free space. */
		totalFreeBytes: Option[String] = None,
	  /** List of disks. */
		disks: Option[Schema.DiskEntryList] = None
	)
	
	case class DiskEntryList(
	  /** Disk entries. */
		entries: Option[List[Schema.DiskEntry]] = None
	)
	
	object DiskEntry {
		enum InterfaceTypeEnum extends Enum[InterfaceTypeEnum] { case INTERFACE_TYPE_UNSPECIFIED, IDE, SATA, SAS, SCSI, NVME, FC, ISCSI }
	}
	case class DiskEntry(
	  /** Disk capacity. */
		capacityBytes: Option[String] = None,
	  /** Disk free space. */
		freeBytes: Option[String] = None,
	  /** Disk label. */
		diskLabel: Option[String] = None,
	  /** Disk label type (e.g. BIOS/GPT) */
		diskLabelType: Option[String] = None,
	  /** Disks interface type. */
		interfaceType: Option[Schema.DiskEntry.InterfaceTypeEnum] = None,
	  /** Partition layout. */
		partitions: Option[Schema.DiskPartitionList] = None,
	  /** Disk hardware address (e.g. 0:1 for SCSI). */
		hwAddress: Option[String] = None,
	  /** VMware disk details. */
		vmware: Option[Schema.VmwareDiskConfig] = None
	)
	
	case class DiskPartitionList(
	  /** Partition entries. */
		entries: Option[List[Schema.DiskPartition]] = None
	)
	
	case class DiskPartition(
	  /** Partition type. */
		`type`: Option[String] = None,
	  /** Partition file system. */
		fileSystem: Option[String] = None,
	  /** Mount pount (Linux/Windows) or drive letter (Windows). */
		mountPoint: Option[String] = None,
	  /** Partition capacity. */
		capacityBytes: Option[String] = None,
	  /** Partition free space. */
		freeBytes: Option[String] = None,
	  /** Partition UUID. */
		uuid: Option[String] = None,
	  /** Sub-partitions. */
		subPartitions: Option[Schema.DiskPartitionList] = None
	)
	
	object VmwareDiskConfig {
		enum BackingTypeEnum extends Enum[BackingTypeEnum] { case BACKING_TYPE_UNSPECIFIED, BACKING_TYPE_FLAT_V1, BACKING_TYPE_FLAT_V2, BACKING_TYPE_PMEM, BACKING_TYPE_RDM_V1, BACKING_TYPE_RDM_V2, BACKING_TYPE_SESPARSE, BACKING_TYPE_SESPARSE_V1, BACKING_TYPE_SESPARSE_V2 }
		enum VmdkModeEnum extends Enum[VmdkModeEnum] { case VMDK_MODE_UNSPECIFIED, DEPENDENT, INDEPENDENT_PERSISTENT, INDEPENDENT_NONPERSISTENT }
		enum RdmCompatibilityEnum extends Enum[RdmCompatibilityEnum] { case RDM_COMPATIBILITY_UNSPECIFIED, PHYSICAL_COMPATIBILITY, VIRTUAL_COMPATIBILITY }
	}
	case class VmwareDiskConfig(
	  /** VMDK backing type. */
		backingType: Option[Schema.VmwareDiskConfig.BackingTypeEnum] = None,
	  /** Is VMDK shared with other VMs. */
		shared: Option[Boolean] = None,
	  /** VMDK disk mode. */
		vmdkMode: Option[Schema.VmwareDiskConfig.VmdkModeEnum] = None,
	  /** RDM compatibility mode. */
		rdmCompatibility: Option[Schema.VmwareDiskConfig.RdmCompatibilityEnum] = None
	)
	
	case class PlatformDetails(
	  /** VMware specific details. */
		vmwareDetails: Option[Schema.VmwarePlatformDetails] = None,
	  /** AWS EC2 specific details. */
		awsEc2Details: Option[Schema.AwsEc2PlatformDetails] = None,
	  /** Azure VM specific details. */
		azureVmDetails: Option[Schema.AzureVmPlatformDetails] = None,
	  /** Generic platform details. */
		genericDetails: Option[Schema.GenericPlatformDetails] = None,
	  /** Physical machines platform details. */
		physicalDetails: Option[Schema.PhysicalPlatformDetails] = None
	)
	
	object VmwarePlatformDetails {
		enum EsxHyperthreadingEnum extends Enum[EsxHyperthreadingEnum] { case HYPERTHREADING_STATUS_UNSPECIFIED, HYPERTHREADING_STATUS_DISABLED, HYPERTHREADING_STATUS_ENABLED }
	}
	case class VmwarePlatformDetails(
	  /** vCenter version. */
		vcenterVersion: Option[String] = None,
	  /** ESX version. */
		esxVersion: Option[String] = None,
	  /** VMware os enum - https://vdc-repo.vmware.com/vmwb-repository/dcr-public/da47f910-60ac-438b-8b9b-6122f4d14524/16b7274a-bf8b-4b4c-a05e-746f2aa93c8c/doc/vim.vm.GuestOsDescriptor.GuestOsIdentifier.html. */
		osid: Option[String] = None,
	  /** Folder name in vCenter where asset resides. */
		vcenterFolder: Option[String] = None,
	  /** vCenter URI used in collection. */
		vcenterUri: Option[String] = None,
	  /** vCenter VM ID. */
		vcenterVmId: Option[String] = None,
	  /** Whether the ESX is hyperthreaded. */
		esxHyperthreading: Option[Schema.VmwarePlatformDetails.EsxHyperthreadingEnum] = None
	)
	
	object AwsEc2PlatformDetails {
		enum HyperthreadingEnum extends Enum[HyperthreadingEnum] { case HYPERTHREADING_STATUS_UNSPECIFIED, HYPERTHREADING_STATUS_DISABLED, HYPERTHREADING_STATUS_ENABLED }
	}
	case class AwsEc2PlatformDetails(
	  /** AWS platform's machine type label. */
		machineTypeLabel: Option[String] = None,
	  /** The location of the machine in the AWS format. */
		location: Option[String] = None,
	  /** Optional. Whether the machine is hyperthreaded. */
		hyperthreading: Option[Schema.AwsEc2PlatformDetails.HyperthreadingEnum] = None
	)
	
	object AzureVmPlatformDetails {
		enum HyperthreadingEnum extends Enum[HyperthreadingEnum] { case HYPERTHREADING_STATUS_UNSPECIFIED, HYPERTHREADING_STATUS_DISABLED, HYPERTHREADING_STATUS_ENABLED }
	}
	case class AzureVmPlatformDetails(
	  /** Azure platform's machine type label. */
		machineTypeLabel: Option[String] = None,
	  /** The location of the machine in the Azure format. */
		location: Option[String] = None,
	  /** Azure platform's provisioning state. */
		provisioningState: Option[String] = None,
	  /** Whether the machine is hyperthreaded. */
		hyperthreading: Option[Schema.AzureVmPlatformDetails.HyperthreadingEnum] = None
	)
	
	object GenericPlatformDetails {
		enum HyperthreadingEnum extends Enum[HyperthreadingEnum] { case HYPERTHREADING_STATUS_UNSPECIFIED, HYPERTHREADING_STATUS_DISABLED, HYPERTHREADING_STATUS_ENABLED }
	}
	case class GenericPlatformDetails(
	  /** Free text representation of the machine location. The format of this field should not be relied on. Different VMs in the same location may have different string values for this field. */
		location: Option[String] = None,
	  /** Whether the machine is hyperthreaded. */
		hyperthreading: Option[Schema.GenericPlatformDetails.HyperthreadingEnum] = None
	)
	
	object PhysicalPlatformDetails {
		enum HyperthreadingEnum extends Enum[HyperthreadingEnum] { case HYPERTHREADING_STATUS_UNSPECIFIED, HYPERTHREADING_STATUS_DISABLED, HYPERTHREADING_STATUS_ENABLED }
	}
	case class PhysicalPlatformDetails(
	  /** Free text representation of the machine location. The format of this field should not be relied on. Different machines in the same location may have different string values for this field. */
		location: Option[String] = None,
	  /** Whether the machine is hyperthreaded. */
		hyperthreading: Option[Schema.PhysicalPlatformDetails.HyperthreadingEnum] = None
	)
	
	case class InsightList(
	  /** Output only. Insights of the list. */
		insights: Option[List[Schema.Insight]] = None,
	  /** Output only. Update timestamp. */
		updateTime: Option[String] = None
	)
	
	case class Insight(
	  /** Output only. An insight about potential migrations for an asset. */
		migrationInsight: Option[Schema.MigrationInsight] = None,
	  /** Output only. A generic insight about an asset */
		genericInsight: Option[Schema.GenericInsight] = None
	)
	
	case class MigrationInsight(
	  /** Output only. Description of how well the asset this insight is associated with fits the proposed migration. */
		fit: Option[Schema.FitDescriptor] = None,
	  /** Output only. A Google Compute Engine target. */
		computeEngineTarget: Option[Schema.ComputeEngineMigrationTarget] = None
	)
	
	object FitDescriptor {
		enum FitLevelEnum extends Enum[FitLevelEnum] { case FIT_LEVEL_UNSPECIFIED, FIT, NO_FIT, REQUIRES_EFFORT }
	}
	case class FitDescriptor(
	  /** Output only. Fit level. */
		fitLevel: Option[Schema.FitDescriptor.FitLevelEnum] = None
	)
	
	case class ComputeEngineMigrationTarget(
	  /** Description of the suggested shape for the migration target. */
		shape: Option[Schema.ComputeEngineShapeDescriptor] = None
	)
	
	case class ComputeEngineShapeDescriptor(
	  /** Memory in mebibytes. */
		memoryMb: Option[Int] = None,
	  /** Number of physical cores. */
		physicalCoreCount: Option[Int] = None,
	  /** Output only. Number of logical cores. */
		logicalCoreCount: Option[Int] = None,
	  /** Output only. Compute Engine machine series. */
		series: Option[String] = None,
	  /** Output only. Compute Engine machine type. */
		machineType: Option[String] = None,
	  /** Output only. Compute Engine storage. Never empty. */
		storage: Option[List[Schema.ComputeStorageDescriptor]] = None
	)
	
	object ComputeStorageDescriptor {
		enum TypeEnum extends Enum[TypeEnum] { case PERSISTENT_DISK_TYPE_UNSPECIFIED, PERSISTENT_DISK_TYPE_STANDARD, PERSISTENT_DISK_TYPE_BALANCED, PERSISTENT_DISK_TYPE_SSD }
	}
	case class ComputeStorageDescriptor(
	  /** Output only. Disk type backing the storage. */
		`type`: Option[Schema.ComputeStorageDescriptor.TypeEnum] = None,
	  /** Output only. Disk size in GiB. */
		sizeGb: Option[Int] = None
	)
	
	case class GenericInsight(
	  /** Output only. Represents a globally unique message id for this insight, can be used for localization purposes, in case message_code is not yet known by the client use default_message instead. */
		messageId: Option[String] = None,
	  /** Output only. In case message_code is not yet known by the client default_message will be the message to be used instead. */
		defaultMessage: Option[String] = None,
	  /** Output only. Additional information about the insight, each entry can be a logical entry and must make sense if it is displayed with line breaks between each entry. Text can contain md style links. */
		additionalInformation: Option[List[String]] = None
	)
	
	case class AssetPerformanceData(
	  /** Daily resource usage aggregations. Contains all of the data available for an asset, up to the last 420 days. Aggregations are sorted from oldest to most recent. */
		dailyResourceUsageAggregations: Option[List[Schema.DailyResourceUsageAggregation]] = None
	)
	
	case class DailyResourceUsageAggregation(
	  /** Aggregation date. Day boundaries are at midnight UTC. */
		date: Option[Schema.Date] = None,
	  /** CPU usage. */
		cpu: Option[Schema.DailyResourceUsageAggregationCPU] = None,
	  /** Memory usage. */
		memory: Option[Schema.DailyResourceUsageAggregationMemory] = None,
	  /** Network usage. */
		network: Option[Schema.DailyResourceUsageAggregationNetwork] = None,
	  /** Disk usage. */
		disk: Option[Schema.DailyResourceUsageAggregationDisk] = None
	)
	
	case class DailyResourceUsageAggregationCPU(
	  /** CPU utilization percentage. */
		utilizationPercentage: Option[Schema.DailyResourceUsageAggregationStats] = None
	)
	
	case class DailyResourceUsageAggregationStats(
	  /** Average usage value. */
		average: Option[BigDecimal] = None,
	  /** Median usage value. */
		median: Option[BigDecimal] = None,
	  /** 95th percentile usage value. */
		ninteyFifthPercentile: Option[BigDecimal] = None,
	  /** Peak usage value. */
		peak: Option[BigDecimal] = None
	)
	
	case class DailyResourceUsageAggregationMemory(
	  /** Memory utilization percentage. */
		utilizationPercentage: Option[Schema.DailyResourceUsageAggregationStats] = None
	)
	
	case class DailyResourceUsageAggregationNetwork(
	  /** Network ingress in B/s. */
		ingressBps: Option[Schema.DailyResourceUsageAggregationStats] = None,
	  /** Network egress in B/s. */
		egressBps: Option[Schema.DailyResourceUsageAggregationStats] = None
	)
	
	case class DailyResourceUsageAggregationDisk(
	  /** Disk I/O operations per second. */
		iops: Option[Schema.DailyResourceUsageAggregationStats] = None
	)
	
	case class BatchUpdateAssetsRequest(
	  /** Required. The request message specifying the resources to update. A maximum of 1000 assets can be modified in a batch. */
		requests: Option[List[Schema.UpdateAssetRequest]] = None
	)
	
	case class UpdateAssetRequest(
	  /** Required. Field mask is used to specify the fields to be overwritten in the `Asset` resource by the update. The values specified in the `update_mask` field are relative to the resource, not the full request. A field will be overwritten if it is in the mask. A single &#42; value in the mask lets you to overwrite all fields. */
		updateMask: Option[String] = None,
	  /** Required. The resource being updated. */
		asset: Option[Schema.Asset] = None,
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	case class BatchUpdateAssetsResponse(
	  /** Update asset content. The content only includes values after field mask being applied. */
		assets: Option[List[Schema.Asset]] = None
	)
	
	case class BatchDeleteAssetsRequest(
	  /** Required. The IDs of the assets to delete. A maximum of 1000 assets can be deleted in a batch. Format: projects/{project}/locations/{location}/assets/{name}. */
		names: Option[List[String]] = None,
	  /** Optional. When this value is set to `true` the request is a no-op for non-existing assets. See https://google.aip.dev/135#delete-if-existing for additional details. Default value is `false`. */
		allowMissing: Option[Boolean] = None
	)
	
	case class Frames(
	  /** A repeated field of asset data. */
		framesData: Option[List[Schema.AssetFrame]] = None
	)
	
	object AssetFrame {
		enum CollectionTypeEnum extends Enum[CollectionTypeEnum] { case SOURCE_TYPE_UNKNOWN, SOURCE_TYPE_UPLOAD, SOURCE_TYPE_GUEST_OS_SCAN, SOURCE_TYPE_INVENTORY_SCAN, SOURCE_TYPE_CUSTOM, SOURCE_TYPE_DISCOVERY_CLIENT }
	}
	case class AssetFrame(
	  /** Asset information specific for virtual machines. */
		machineDetails: Option[Schema.MachineDetails] = None,
	  /** The time the data was reported. */
		reportTime: Option[String] = None,
	  /** Labels as key value pairs. */
		labels: Option[Map[String, String]] = None,
	  /** Generic asset attributes. */
		attributes: Option[Map[String, String]] = None,
	  /** Asset performance data samples. Samples that are from more than 40 days ago or after tomorrow are ignored. */
		performanceSamples: Option[List[Schema.PerformanceSample]] = None,
	  /** Optional. Trace token is optionally provided to assist with debugging and traceability. */
		traceToken: Option[String] = None,
	  /** Optional. Frame collection type, if not specified the collection type will be based on the source type of the source the frame was reported on. */
		collectionType: Option[Schema.AssetFrame.CollectionTypeEnum] = None
	)
	
	case class PerformanceSample(
	  /** Time the sample was collected. If omitted, the frame report time will be used. */
		sampleTime: Option[String] = None,
	  /** Memory usage sample. */
		memory: Option[Schema.MemoryUsageSample] = None,
	  /** CPU usage sample. */
		cpu: Option[Schema.CpuUsageSample] = None,
	  /** Network usage sample. */
		network: Option[Schema.NetworkUsageSample] = None,
	  /** Disk usage sample. */
		disk: Option[Schema.DiskUsageSample] = None
	)
	
	case class MemoryUsageSample(
	  /** Percentage of system memory utilized. Must be in the interval [0, 100]. */
		utilizedPercentage: Option[BigDecimal] = None
	)
	
	case class CpuUsageSample(
	  /** Percentage of total CPU capacity utilized. Must be in the interval [0, 100]. On most systems can be calculated using 100 - idle percentage. */
		utilizedPercentage: Option[BigDecimal] = None
	)
	
	case class NetworkUsageSample(
	  /** Average network ingress in B/s sampled over a short window. Must be non-negative. */
		averageIngressBps: Option[BigDecimal] = None,
	  /** Average network egress in B/s sampled over a short window. Must be non-negative. */
		averageEgressBps: Option[BigDecimal] = None
	)
	
	case class DiskUsageSample(
	  /** Average IOPS sampled over a short window. Must be non-negative. */
		averageIops: Option[BigDecimal] = None
	)
	
	case class ReportAssetFramesResponse(
	
	)
	
	case class AggregateAssetsValuesRequest(
	  /** Array of aggregations to perform. Up to 25 aggregations can be defined. */
		aggregations: Option[List[Schema.Aggregation]] = None,
	  /** Optional. The aggregation will be performed on assets that match the provided filter. */
		filter: Option[String] = None
	)
	
	case class Aggregation(
	  /** The name of the field on which to aggregate. */
		field: Option[String] = None,
	  /** Count the number of matching objects. */
		count: Option[Schema.AggregationCount] = None,
	  /** Sum over a numeric field. */
		sum: Option[Schema.AggregationSum] = None,
	  /** Creates a bucketed histogram of field values. */
		histogram: Option[Schema.AggregationHistogram] = None,
	  /** Creates a frequency distribution of all field values. */
		frequency: Option[Schema.AggregationFrequency] = None
	)
	
	case class AggregationCount(
	
	)
	
	case class AggregationSum(
	
	)
	
	case class AggregationHistogram(
	  /** Lower bounds of buckets. The response will contain `n+1` buckets for `n` bounds. The first bucket will count all assets for which the field value is smaller than the first bound. Subsequent buckets will count assets for which the field value is greater or equal to a lower bound and smaller than the next one. The last bucket will count assets for which the field value is greater or equal to the final lower bound. You can define up to 20 lower bounds. */
		lowerBounds: Option[List[BigDecimal]] = None
	)
	
	case class AggregationFrequency(
	
	)
	
	case class AggregateAssetsValuesResponse(
	  /** The aggregation results. */
		results: Option[List[Schema.AggregationResult]] = None
	)
	
	case class AggregationResult(
		field: Option[String] = None,
		count: Option[Schema.AggregationResultCount] = None,
		sum: Option[Schema.AggregationResultSum] = None,
		histogram: Option[Schema.AggregationResultHistogram] = None,
		frequency: Option[Schema.AggregationResultFrequency] = None
	)
	
	case class AggregationResultCount(
		value: Option[String] = None
	)
	
	case class AggregationResultSum(
		value: Option[BigDecimal] = None
	)
	
	case class AggregationResultHistogram(
	  /** Buckets in the histogram. There will be `n+1` buckets matching `n` lower bounds in the request. The first bucket will be from -infinity to the first bound. Subsequent buckets will be between one bound and the next. The final bucket will be from the final bound to infinity. */
		buckets: Option[List[Schema.AggregationResultHistogramBucket]] = None
	)
	
	case class AggregationResultHistogramBucket(
	  /** Lower bound - inclusive. */
		lowerBound: Option[BigDecimal] = None,
	  /** Upper bound - exclusive. */
		upperBound: Option[BigDecimal] = None,
	  /** Count of items in the bucket. */
		count: Option[String] = None
	)
	
	case class AggregationResultFrequency(
		values: Option[Map[String, String]] = None
	)
	
	object ImportJob {
		enum StateEnum extends Enum[StateEnum] { case IMPORT_JOB_STATE_UNSPECIFIED, IMPORT_JOB_STATE_PENDING, IMPORT_JOB_STATE_RUNNING, IMPORT_JOB_STATE_COMPLETED, IMPORT_JOB_STATE_FAILED, IMPORT_JOB_STATE_VALIDATING, IMPORT_JOB_STATE_FAILED_VALIDATION, IMPORT_JOB_STATE_READY }
	}
	case class ImportJob(
	  /** Output only. The full name of the import job. */
		name: Option[String] = None,
	  /** Optional. User-friendly display name. Maximum length is 256 characters. */
		displayName: Option[String] = None,
	  /** Output only. The timestamp when the import job was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the import job was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. The timestamp when the import job was completed. */
		completeTime: Option[String] = None,
	  /** Output only. The state of the import job. */
		state: Option[Schema.ImportJob.StateEnum] = None,
	  /** Labels as key value pairs. */
		labels: Option[Map[String, String]] = None,
	  /** Required. Reference to a source. */
		assetSource: Option[String] = None,
	  /** Output only. The report with the validation results of the import job. */
		validationReport: Option[Schema.ValidationReport] = None,
	  /** Output only. The report with the results of running the import job. */
		executionReport: Option[Schema.ExecutionReport] = None
	)
	
	case class ValidationReport(
	  /** List of errors found in files. */
		fileValidations: Option[List[Schema.FileValidationReport]] = None,
	  /** List of job level errors. */
		jobErrors: Option[List[Schema.ImportError]] = None
	)
	
	case class FileValidationReport(
	  /** The name of the file. */
		fileName: Option[String] = None,
	  /** Partial list of rows that encountered validation error. */
		rowErrors: Option[List[Schema.ImportRowError]] = None,
	  /** Flag indicating that processing was aborted due to maximum number of errors. */
		partialReport: Option[Boolean] = None,
	  /** List of file level errors. */
		fileErrors: Option[List[Schema.ImportError]] = None
	)
	
	case class ImportRowError(
	  /** The row number where the error was detected. */
		rowNumber: Option[Int] = None,
	  /** The name of the VM in the row. */
		vmName: Option[String] = None,
	  /** The VM UUID. */
		vmUuid: Option[String] = None,
	  /** The list of errors detected in the row. */
		errors: Option[List[Schema.ImportError]] = None,
	  /** Error details for a CSV file. */
		csvError: Option[Schema.ImportRowErrorCsvErrorDetails] = None,
	  /** Error details for an XLSX file. */
		xlsxError: Option[Schema.ImportRowErrorXlsxErrorDetails] = None
	)
	
	object ImportError {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, ERROR, WARNING, INFO }
	}
	case class ImportError(
	  /** The error information. */
		errorDetails: Option[String] = None,
	  /** The severity of the error. */
		severity: Option[Schema.ImportError.SeverityEnum] = None
	)
	
	case class ImportRowErrorCsvErrorDetails(
	  /** The row number where the error was detected. */
		rowNumber: Option[Int] = None
	)
	
	case class ImportRowErrorXlsxErrorDetails(
	  /** The name of the sheet where the error was detected. */
		sheet: Option[String] = None,
	  /** The row number where the error was detected. */
		rowNumber: Option[Int] = None
	)
	
	case class ExecutionReport(
	  /** Total number of asset frames reported for the import job. */
		framesReported: Option[Int] = None,
	  /** Validation errors encountered during the execution of the import job. */
		executionErrors: Option[Schema.ValidationReport] = None,
	  /** Output only. Total number of rows in the import job. */
		totalRowsCount: Option[Int] = None
	)
	
	case class ListImportJobsResponse(
	  /** The list of import jobs. */
		importJobs: Option[List[Schema.ImportJob]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ValidateImportJobRequest(
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	case class RunImportJobRequest(
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	object ImportDataFile {
		enum FormatEnum extends Enum[FormatEnum] { case IMPORT_JOB_FORMAT_UNSPECIFIED, IMPORT_JOB_FORMAT_RVTOOLS_XLSX, IMPORT_JOB_FORMAT_RVTOOLS_CSV, IMPORT_JOB_FORMAT_EXPORTED_AWS_CSV, IMPORT_JOB_FORMAT_EXPORTED_AZURE_CSV, IMPORT_JOB_FORMAT_STRATOZONE_CSV }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE }
	}
	case class ImportDataFile(
	  /** Output only. The name of the file. */
		name: Option[String] = None,
	  /** User-friendly display name. Maximum length is 63 characters. */
		displayName: Option[String] = None,
	  /** Required. The payload format. */
		format: Option[Schema.ImportDataFile.FormatEnum] = None,
	  /** Output only. The timestamp when the file was created. */
		createTime: Option[String] = None,
	  /** Output only. The state of the import data file. */
		state: Option[Schema.ImportDataFile.StateEnum] = None,
	  /** Information about a file that is uploaded to a storage service. */
		uploadFileInfo: Option[Schema.UploadFileInfo] = None
	)
	
	case class UploadFileInfo(
	  /** Output only. Upload URI for the file. */
		signedUri: Option[String] = None,
	  /** Output only. The headers that were used to sign the URI. */
		headers: Option[Map[String, String]] = None,
	  /** Output only. Expiration time of the upload URI. */
		uriExpirationTime: Option[String] = None
	)
	
	case class ListImportDataFilesResponse(
	  /** The list of import data files. */
		importDataFiles: Option[List[Schema.ImportDataFile]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ListGroupsResponse(
	  /** The list of Group */
		groups: Option[List[Schema.Group]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class Group(
	  /** Output only. The name of the group. */
		name: Option[String] = None,
	  /** Output only. The timestamp when the group was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the group was last updated. */
		updateTime: Option[String] = None,
	  /** Labels as key value pairs. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. User-friendly display name. */
		displayName: Option[String] = None,
	  /** Optional. The description of the group. */
		description: Option[String] = None
	)
	
	case class AddAssetsToGroupRequest(
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None,
	  /** Required. List of assets to be added. The maximum number of assets that can be added in a single request is 1000. */
		assets: Option[Schema.AssetList] = None,
	  /** Optional. When this value is set to `false` and one of the given assets is already an existing member of the group, the operation fails with an `Already Exists` error. When set to `true` this situation is silently ignored by the server. Default value is `false`. */
		allowExisting: Option[Boolean] = None
	)
	
	case class AssetList(
	  /** Required. A list of asset IDs */
		assetIds: Option[List[String]] = None
	)
	
	case class RemoveAssetsFromGroupRequest(
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None,
	  /** Required. List of assets to be removed. The maximum number of assets that can be removed in a single request is 1000. */
		assets: Option[Schema.AssetList] = None,
	  /** Optional. When this value is set to `false` and one of the given assets is not an existing member of the group, the operation fails with a `Not Found` error. When set to `true` this situation is silently ignored by the server. Default value is `false`. */
		allowMissing: Option[Boolean] = None
	)
	
	case class ListErrorFramesResponse(
	  /** The list of error frames. */
		errorFrames: Option[List[Schema.ErrorFrame]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ErrorFrame(
	  /** Output only. The identifier of the ErrorFrame. */
		name: Option[String] = None,
	  /** Output only. All the violations that were detected for the frame. */
		violations: Option[List[Schema.FrameViolationEntry]] = None,
	  /** Output only. The frame that was originally reported. */
		originalFrame: Option[Schema.AssetFrame] = None,
	  /** Output only. Frame ingestion time. */
		ingestionTime: Option[String] = None
	)
	
	case class FrameViolationEntry(
	  /** The field of the original frame where the violation occurred. */
		field: Option[String] = None,
	  /** A message describing the violation. */
		violation: Option[String] = None
	)
	
	case class ListSourcesResponse(
	  /** The list of sources. */
		sources: Option[List[Schema.Source]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Source {
		enum TypeEnum extends Enum[TypeEnum] { case SOURCE_TYPE_UNKNOWN, SOURCE_TYPE_UPLOAD, SOURCE_TYPE_GUEST_OS_SCAN, SOURCE_TYPE_INVENTORY_SCAN, SOURCE_TYPE_CUSTOM, SOURCE_TYPE_DISCOVERY_CLIENT }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, DELETING, INVALID }
	}
	case class Source(
	  /** Output only. The full name of the source. */
		name: Option[String] = None,
	  /** Output only. The timestamp when the source was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the source was last updated. */
		updateTime: Option[String] = None,
	  /** User-friendly display name. */
		displayName: Option[String] = None,
	  /** Free-text description. */
		description: Option[String] = None,
	  /** Data source type. */
		`type`: Option[Schema.Source.TypeEnum] = None,
	  /** The information confidence of the source. The higher the value, the higher the confidence. */
		priority: Option[Int] = None,
	  /** If `true`, the source is managed by other service(s). */
		managed: Option[Boolean] = None,
	  /** Output only. Number of frames that are still being processed. */
		pendingFrameCount: Option[Int] = None,
	  /** Output only. The number of frames that were reported by the source and contained errors. */
		errorFrameCount: Option[Int] = None,
	  /** Output only. The state of the source. */
		state: Option[Schema.Source.StateEnum] = None
	)
	
	case class ListPreferenceSetsResponse(
	  /** The list of PreferenceSets */
		preferenceSets: Option[List[Schema.PreferenceSet]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class PreferenceSet(
	  /** Output only. Name of the preference set. */
		name: Option[String] = None,
	  /** Output only. The timestamp when the preference set was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the preference set was last updated. */
		updateTime: Option[String] = None,
	  /** User-friendly display name. Maximum length is 63 characters. */
		displayName: Option[String] = None,
	  /** A description of the preference set. */
		description: Option[String] = None,
	  /** Optional. A set of preferences that applies to all virtual machines in the context. */
		virtualMachinePreferences: Option[Schema.VirtualMachinePreferences] = None
	)
	
	object VirtualMachinePreferences {
		enum TargetProductEnum extends Enum[TargetProductEnum] { case COMPUTE_MIGRATION_TARGET_PRODUCT_UNSPECIFIED, COMPUTE_MIGRATION_TARGET_PRODUCT_COMPUTE_ENGINE, COMPUTE_MIGRATION_TARGET_PRODUCT_VMWARE_ENGINE, COMPUTE_MIGRATION_TARGET_PRODUCT_SOLE_TENANCY }
		enum CommitmentPlanEnum extends Enum[CommitmentPlanEnum] { case COMMITMENT_PLAN_UNSPECIFIED, COMMITMENT_PLAN_NONE, COMMITMENT_PLAN_ONE_YEAR, COMMITMENT_PLAN_THREE_YEARS }
		enum SizingOptimizationStrategyEnum extends Enum[SizingOptimizationStrategyEnum] { case SIZING_OPTIMIZATION_STRATEGY_UNSPECIFIED, SIZING_OPTIMIZATION_STRATEGY_SAME_AS_SOURCE, SIZING_OPTIMIZATION_STRATEGY_MODERATE, SIZING_OPTIMIZATION_STRATEGY_AGGRESSIVE }
	}
	case class VirtualMachinePreferences(
	  /** Target product for assets using this preference set. Specify either target product or business goal, but not both. */
		targetProduct: Option[Schema.VirtualMachinePreferences.TargetProductEnum] = None,
	  /** Region preferences for assets using this preference set. If you are unsure which value to set, the migration service API region is often a good value to start with. */
		regionPreferences: Option[Schema.RegionPreferences] = None,
	  /** Commitment plan to consider when calculating costs for virtual machine insights and recommendations. If you are unsure which value to set, a 3 year commitment plan is often a good value to start with. */
		commitmentPlan: Option[Schema.VirtualMachinePreferences.CommitmentPlanEnum] = None,
	  /** Sizing optimization strategy specifies the preferred strategy used when extrapolating usage data to calculate insights and recommendations for a virtual machine. If you are unsure which value to set, a moderate sizing optimization strategy is often a good value to start with. */
		sizingOptimizationStrategy: Option[Schema.VirtualMachinePreferences.SizingOptimizationStrategyEnum] = None,
	  /** Compute Engine preferences concern insights and recommendations for Compute Engine target. */
		computeEnginePreferences: Option[Schema.ComputeEnginePreferences] = None,
	  /** Preferences concerning insights and recommendations for Google Cloud VMware Engine. */
		vmwareEnginePreferences: Option[Schema.VmwareEnginePreferences] = None,
	  /** Preferences concerning Sole Tenant nodes and virtual machines. */
		soleTenancyPreferences: Option[Schema.SoleTenancyPreferences] = None
	)
	
	case class RegionPreferences(
	  /** A list of preferred regions, ordered by the most preferred region first. Set only valid Google Cloud region names. See https://cloud.google.com/compute/docs/regions-zones for available regions. */
		preferredRegions: Option[List[String]] = None
	)
	
	object ComputeEnginePreferences {
		enum PersistentDiskTypeEnum extends Enum[PersistentDiskTypeEnum] { case PERSISTENT_DISK_TYPE_UNSPECIFIED, PERSISTENT_DISK_TYPE_STANDARD, PERSISTENT_DISK_TYPE_BALANCED, PERSISTENT_DISK_TYPE_SSD }
		enum LicenseTypeEnum extends Enum[LicenseTypeEnum] { case LICENSE_TYPE_UNSPECIFIED, LICENSE_TYPE_DEFAULT, LICENSE_TYPE_BRING_YOUR_OWN_LICENSE }
	}
	case class ComputeEnginePreferences(
	  /** Persistent disk type to use. If unspecified (default), all types are considered, based on available usage data. */
		persistentDiskType: Option[Schema.ComputeEnginePreferences.PersistentDiskTypeEnum] = None,
	  /** Preferences concerning the machine types to consider on Compute Engine. */
		machinePreferences: Option[Schema.MachinePreferences] = None,
	  /** License type to consider when calculating costs for virtual machine insights and recommendations. If unspecified, costs are calculated based on the default licensing plan. */
		licenseType: Option[Schema.ComputeEnginePreferences.LicenseTypeEnum] = None
	)
	
	case class MachinePreferences(
	  /** Compute Engine machine series to consider for insights and recommendations. If empty, no restriction is applied on the machine series. */
		allowedMachineSeries: Option[List[Schema.MachineSeries]] = None
	)
	
	case class MachineSeries(
	  /** Code to identify a machine series. Consult this for more details on the available series for Compute Engine: https://cloud.google.com/compute/docs/machine-resource#machine_type_comparison Consult this for more details on the available series for Google Cloud VMware Engine: https://cloud.google.com/vmware-engine/pricing */
		code: Option[String] = None
	)
	
	object VmwareEnginePreferences {
		enum CommitmentPlanEnum extends Enum[CommitmentPlanEnum] { case COMMITMENT_PLAN_UNSPECIFIED, ON_DEMAND, COMMITMENT_1_YEAR_MONTHLY_PAYMENTS, COMMITMENT_3_YEAR_MONTHLY_PAYMENTS, COMMITMENT_1_YEAR_UPFRONT_PAYMENT, COMMITMENT_3_YEAR_UPFRONT_PAYMENT }
	}
	case class VmwareEnginePreferences(
	  /** CPU overcommit ratio. Acceptable values are between 1.0 and 8.0, with 0.1 increment. */
		cpuOvercommitRatio: Option[BigDecimal] = None,
	  /** Memory overcommit ratio. Acceptable values are 1.0, 1.25, 1.5, 1.75 and 2.0. */
		memoryOvercommitRatio: Option[BigDecimal] = None,
	  /** The Deduplication and Compression ratio is based on the logical (Used Before) space required to store data before applying deduplication and compression, in relation to the physical (Used After) space required after applying deduplication and compression. Specifically, the ratio is the Used Before space divided by the Used After space. For example, if the Used Before space is 3 GB, but the physical Used After space is 1 GB, the deduplication and compression ratio is 3x. Acceptable values are between 1.0 and 4.0. */
		storageDeduplicationCompressionRatio: Option[BigDecimal] = None,
	  /** Commitment plan to consider when calculating costs for virtual machine insights and recommendations. If you are unsure which value to set, a 3 year commitment plan is often a good value to start with. */
		commitmentPlan: Option[Schema.VmwareEnginePreferences.CommitmentPlanEnum] = None
	)
	
	object SoleTenancyPreferences {
		enum HostMaintenancePolicyEnum extends Enum[HostMaintenancePolicyEnum] { case HOST_MAINTENANCE_POLICY_UNSPECIFIED, HOST_MAINTENANCE_POLICY_DEFAULT, HOST_MAINTENANCE_POLICY_RESTART_IN_PLACE, HOST_MAINTENANCE_POLICY_MIGRATE_WITHIN_NODE_GROUP }
		enum CommitmentPlanEnum extends Enum[CommitmentPlanEnum] { case COMMITMENT_PLAN_UNSPECIFIED, ON_DEMAND, COMMITMENT_1_YEAR, COMMITMENT_3_YEAR }
	}
	case class SoleTenancyPreferences(
	  /** CPU overcommit ratio. Acceptable values are between 1.0 and 2.0 inclusive. */
		cpuOvercommitRatio: Option[BigDecimal] = None,
	  /** Sole Tenancy nodes maintenance policy. */
		hostMaintenancePolicy: Option[Schema.SoleTenancyPreferences.HostMaintenancePolicyEnum] = None,
	  /** Commitment plan to consider when calculating costs for virtual machine insights and recommendations. If you are unsure which value to set, a 3 year commitment plan is often a good value to start with. */
		commitmentPlan: Option[Schema.SoleTenancyPreferences.CommitmentPlanEnum] = None,
	  /** A list of sole tenant node types. An empty list means that all possible node types will be considered. */
		nodeTypes: Option[List[Schema.SoleTenantNodeType]] = None
	)
	
	case class SoleTenantNodeType(
	  /** Name of the Sole Tenant node. Consult https://cloud.google.com/compute/docs/nodes/sole-tenant-nodes */
		nodeName: Option[String] = None
	)
	
	case class Settings(
	  /** Output only. The name of the resource. */
		name: Option[String] = None,
	  /** The preference set used by default for a project. */
		preferenceSet: Option[String] = None,
	  /** Disable Cloud Logging for the Migration Center API. Users are billed for the logs. */
		disableCloudLogging: Option[Boolean] = None
	)
	
	case class ReportConfig(
	  /** Output only. Name of resource. */
		name: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was last updated. */
		updateTime: Option[String] = None,
	  /** User-friendly display name. Maximum length is 63 characters. */
		displayName: Option[String] = None,
	  /** Free-text description. */
		description: Option[String] = None,
	  /** Required. Collection of combinations of groups and preference sets. */
		groupPreferencesetAssignments: Option[List[Schema.ReportConfigGroupPreferenceSetAssignment]] = None
	)
	
	case class ReportConfigGroupPreferenceSetAssignment(
	  /** Required. Name of the group. */
		group: Option[String] = None,
	  /** Required. Name of the Preference Set. */
		preferenceSet: Option[String] = None
	)
	
	case class ListReportConfigsResponse(
	  /** A list of report configs. */
		reportConfigs: Option[List[Schema.ReportConfig]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Report {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, TOTAL_COST_OF_OWNERSHIP }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, SUCCEEDED, FAILED }
	}
	case class Report(
	  /** Output only. Name of resource. */
		name: Option[String] = None,
	  /** Output only. Creation timestamp. */
		createTime: Option[String] = None,
	  /** Output only. Last update timestamp. */
		updateTime: Option[String] = None,
	  /** User-friendly display name. Maximum length is 63 characters. */
		displayName: Option[String] = None,
	  /** Free-text description. */
		description: Option[String] = None,
	  /** Report type. */
		`type`: Option[Schema.Report.TypeEnum] = None,
	  /** Report creation state. */
		state: Option[Schema.Report.StateEnum] = None,
	  /** Output only. Summary view of the Report. */
		summary: Option[Schema.ReportSummary] = None
	)
	
	case class ReportSummary(
	  /** Aggregate statistics for all the assets across all the groups. */
		allAssetsStats: Option[Schema.ReportSummaryAssetAggregateStats] = None,
	  /** Findings for each Group included in this report. */
		groupFindings: Option[List[Schema.ReportSummaryGroupFinding]] = None
	)
	
	case class ReportSummaryAssetAggregateStats(
	  /** Sum of the memory in bytes of all the assets in this collection. */
		totalMemoryBytes: Option[String] = None,
	  /** Sum of persistent storage in bytes of all the assets in this collection. */
		totalStorageBytes: Option[String] = None,
	  /** Sum of the CPU core count of all the assets in this collection. */
		totalCores: Option[String] = None,
	  /** Count of the number of unique assets in this collection. */
		totalAssets: Option[String] = None,
	  /** Total memory split into Used/Free buckets. */
		memoryUtilizationChart: Option[Schema.ReportSummaryUtilizationChartData] = None,
	  /** Total memory split into Used/Free buckets. */
		storageUtilizationChart: Option[Schema.ReportSummaryUtilizationChartData] = None,
	  /** Count of assets grouped by Operating System families. */
		operatingSystem: Option[Schema.ReportSummaryChartData] = None,
	  /** Histogram showing a distribution of logical CPU core counts. */
		coreCountHistogram: Option[Schema.ReportSummaryHistogramChartData] = None,
	  /** Histogram showing a distribution of memory sizes. */
		memoryBytesHistogram: Option[Schema.ReportSummaryHistogramChartData] = None,
	  /** Histogram showing a distribution of storage sizes. */
		storageBytesHistogram: Option[Schema.ReportSummaryHistogramChartData] = None
	)
	
	case class ReportSummaryUtilizationChartData(
	  /** Aggregate value which falls into the "Used" bucket. */
		used: Option[String] = None,
	  /** Aggregate value which falls into the "Free" bucket. */
		free: Option[String] = None
	)
	
	case class ReportSummaryChartData(
	  /** Each data point in the chart is represented as a name-value pair with the name being the x-axis label, and the value being the y-axis value. */
		dataPoints: Option[List[Schema.ReportSummaryChartDataDataPoint]] = None
	)
	
	case class ReportSummaryChartDataDataPoint(
	  /** The X-axis label for this data point. */
		label: Option[String] = None,
	  /** The Y-axis value for this data point. */
		value: Option[BigDecimal] = None
	)
	
	case class ReportSummaryHistogramChartData(
	  /** Buckets in the histogram. There will be `n+1` buckets matching `n` lower bounds in the request. The first bucket will be from -infinity to the first bound. Subsequent buckets will be between one bound and the next. The final bucket will be from the final bound to infinity. */
		buckets: Option[List[Schema.ReportSummaryHistogramChartDataBucket]] = None
	)
	
	case class ReportSummaryHistogramChartDataBucket(
	  /** Lower bound - inclusive. */
		lowerBound: Option[String] = None,
	  /** Upper bound - exclusive. */
		upperBound: Option[String] = None,
	  /** Count of items in the bucket. */
		count: Option[String] = None
	)
	
	case class ReportSummaryGroupFinding(
	  /** Display Name for the Group. */
		displayName: Option[String] = None,
	  /** Description for the Group. */
		description: Option[String] = None,
	  /** Summary statistics for all the assets in this group. */
		assetAggregateStats: Option[Schema.ReportSummaryAssetAggregateStats] = None,
	  /** This field is deprecated, do not rely on it having a value. */
		overlappingAssetCount: Option[String] = None,
	  /** Findings for each of the PreferenceSets for this group. */
		preferenceSetFindings: Option[List[Schema.ReportSummaryGroupPreferenceSetFinding]] = None
	)
	
	case class ReportSummaryGroupPreferenceSetFinding(
	  /** Display Name of the Preference Set */
		displayName: Option[String] = None,
	  /** Description for the Preference Set. */
		description: Option[String] = None,
	  /** A set of preferences that applies to all machines in the context. */
		machinePreferences: Option[Schema.VirtualMachinePreferences] = None,
	  /** Total monthly cost for this preference set. */
		monthlyCostTotal: Option[Schema.Money] = None,
	  /** Compute monthly cost for this preference set. */
		monthlyCostCompute: Option[Schema.Money] = None,
	  /** Licensing monthly cost for this preference set. */
		monthlyCostOsLicense: Option[Schema.Money] = None,
	  /** Network Egress monthly cost for this preference set. */
		monthlyCostNetworkEgress: Option[Schema.Money] = None,
	  /** Storage monthly cost for this preference set. */
		monthlyCostStorage: Option[Schema.Money] = None,
	  /** Miscellaneous monthly cost for this preference set. */
		monthlyCostOther: Option[Schema.Money] = None,
	  /** A set of findings that applies to Compute Engine machines in the input. */
		computeEngineFinding: Option[Schema.ReportSummaryComputeEngineFinding] = None,
	  /** A set of findings that applies to VMWare machines in the input. */
		vmwareEngineFinding: Option[Schema.ReportSummaryVmwareEngineFinding] = None,
	  /** A set of findings that applies to Sole-Tenant machines in the input. */
		soleTenantFinding: Option[Schema.ReportSummarySoleTenantFinding] = None
	)
	
	case class Money(
	  /** The three-letter currency code defined in ISO 4217. */
		currencyCode: Option[String] = None,
	  /** The whole units of the amount. For example if `currencyCode` is `"USD"`, then 1 unit is one US dollar. */
		units: Option[String] = None,
	  /** Number of nano (10^-9) units of the amount. The value must be between -999,999,999 and +999,999,999 inclusive. If `units` is positive, `nanos` must be positive or zero. If `units` is zero, `nanos` can be positive, zero, or negative. If `units` is negative, `nanos` must be negative or zero. For example $-1.75 is represented as `units`=-1 and `nanos`=-750,000,000. */
		nanos: Option[Int] = None
	)
	
	object ReportSummaryComputeEngineFinding {
		enum AllocatedDiskTypesEnum extends Enum[AllocatedDiskTypesEnum] { case PERSISTENT_DISK_TYPE_UNSPECIFIED, PERSISTENT_DISK_TYPE_STANDARD, PERSISTENT_DISK_TYPE_BALANCED, PERSISTENT_DISK_TYPE_SSD }
	}
	case class ReportSummaryComputeEngineFinding(
	  /** Set of regions in which the assets were allocated. */
		allocatedRegions: Option[List[String]] = None,
	  /** Count of assets which were allocated. */
		allocatedAssetCount: Option[String] = None,
	  /** Distribution of assets based on the Machine Series. */
		machineSeriesAllocations: Option[List[Schema.ReportSummaryMachineSeriesAllocation]] = None,
	  /** Set of disk types allocated to assets. */
		allocatedDiskTypes: Option[List[Schema.ReportSummaryComputeEngineFinding.AllocatedDiskTypesEnum]] = None
	)
	
	case class ReportSummaryMachineSeriesAllocation(
	  /** The Machine Series (e.g. "E2", "N2") */
		machineSeries: Option[Schema.MachineSeries] = None,
	  /** Count of assets allocated to this machine series. */
		allocatedAssetCount: Option[String] = None
	)
	
	case class ReportSummaryVmwareEngineFinding(
	  /** Set of regions in which the assets were allocated */
		allocatedRegions: Option[List[String]] = None,
	  /** Count of assets which are allocated */
		allocatedAssetCount: Option[String] = None,
	  /** Set of per-nodetype allocation records */
		nodeAllocations: Option[List[Schema.ReportSummaryVmwareNodeAllocation]] = None
	)
	
	case class ReportSummaryVmwareNodeAllocation(
	  /** VMWare node type, e.g. "ve1-standard-72" */
		vmwareNode: Option[Schema.ReportSummaryVmwareNode] = None,
	  /** Count of this node type to be provisioned */
		nodeCount: Option[String] = None,
	  /** Count of assets allocated to these nodes */
		allocatedAssetCount: Option[String] = None
	)
	
	case class ReportSummaryVmwareNode(
	  /** Code to identify VMware Engine node series, e.g. "ve1-standard-72". Based on the displayName of cloud.google.com/vmware-engine/docs/reference/rest/v1/projects.locations.nodeTypes */
		code: Option[String] = None
	)
	
	case class ReportSummarySoleTenantFinding(
	  /** Set of regions in which the assets are allocated */
		allocatedRegions: Option[List[String]] = None,
	  /** Count of assets which are allocated */
		allocatedAssetCount: Option[String] = None,
	  /** Set of per-nodetype allocation records */
		nodeAllocations: Option[List[Schema.ReportSummarySoleTenantNodeAllocation]] = None
	)
	
	case class ReportSummarySoleTenantNodeAllocation(
	  /** Sole Tenant node type, e.g. "m3-node-128-3904" */
		node: Option[Schema.SoleTenantNodeType] = None,
	  /** Count of this node type to be provisioned */
		nodeCount: Option[String] = None,
	  /** Count of assets allocated to these nodes */
		allocatedAssetCount: Option[String] = None
	)
	
	case class ListReportsResponse(
	  /** The list of Reports. */
		reports: Option[List[Schema.Report]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object DiscoveryClient {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, OFFLINE, DEGRADED, EXPIRED }
	}
	case class DiscoveryClient(
	  /** Output only. Identifier. Full name of this discovery client. */
		name: Option[String] = None,
	  /** Output only. Time when the discovery client was first created. */
		createTime: Option[String] = None,
	  /** Output only. Time when the discovery client was last updated. This value is not updated by heartbeats, to view the last heartbeat time please refer to the `heartbeat_time` field. */
		updateTime: Option[String] = None,
	  /** Required. Immutable. Full name of the source object associated with this discovery client. */
		source: Option[String] = None,
	  /** Required. Service account used by the discovery client for various operation. */
		serviceAccount: Option[String] = None,
	  /** Output only. This field is intended for internal use. */
		signalsEndpoint: Option[String] = None,
	  /** Optional. Free text display name. Maximum length is 63 characters. */
		displayName: Option[String] = None,
	  /** Optional. Free text description. Maximum length is 1000 characters. */
		description: Option[String] = None,
	  /** Optional. Labels as key value pairs. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Current state of the discovery client. */
		state: Option[Schema.DiscoveryClient.StateEnum] = None,
	  /** Output only. Client version, as reported in recent heartbeat. */
		version: Option[String] = None,
	  /** Output only. Errors affecting client functionality. */
		errors: Option[List[Schema.Status]] = None,
	  /** Output only. Last heartbeat time. Healthy clients are expected to send heartbeats regularly (normally every few minutes). */
		heartbeatTime: Option[String] = None,
	  /** Optional. Client expiration time in UTC. If specified, the backend will not accept new frames after this time. */
		expireTime: Option[String] = None,
	  /** Optional. Input only. Client time-to-live. If specified, the backend will not accept new frames after this time. This field is input only. The derived expiration time is provided as output through the `expire_time` field. */
		ttl: Option[String] = None
	)
	
	case class ListDiscoveryClientsResponse(
	  /** List of discovery clients. */
		discoveryClients: Option[List[Schema.DiscoveryClient]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class SendDiscoveryClientHeartbeatRequest(
	  /** Optional. Client application version. */
		version: Option[String] = None,
	  /** Optional. Errors affecting client functionality. */
		errors: Option[List[Schema.Status]] = None
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
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have been cancelled successfully have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
}
