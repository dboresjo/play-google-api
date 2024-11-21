package com.boresjo.play.api.google.oracledatabase

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
	
	case class ListCloudExadataInfrastructuresResponse(
	  /** The list of Exadata Infrastructures. */
		cloudExadataInfrastructures: Option[List[Schema.CloudExadataInfrastructure]] = None,
	  /** A token for fetching next page of response. */
		nextPageToken: Option[String] = None
	)
	
	case class CloudExadataInfrastructure(
	  /** Identifier. The name of the Exadata Infrastructure resource with the format: projects/{project}/locations/{region}/cloudExadataInfrastructures/{cloud_exadata_infrastructure} */
		name: Option[String] = None,
	  /** Optional. User friendly name for this resource. */
		displayName: Option[String] = None,
	  /** Optional. Google Cloud Platform location where Oracle Exadata is hosted. */
		gcpOracleZone: Option[String] = None,
	  /** Output only. Entitlement ID of the private offer against which this infrastructure resource is provisioned. */
		entitlementId: Option[String] = None,
	  /** Optional. Various properties of the infra. */
		properties: Option[Schema.CloudExadataInfrastructureProperties] = None,
	  /** Optional. Labels or tags associated with the resource. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The date and time that the Exadata Infrastructure was created. */
		createTime: Option[String] = None
	)
	
	object CloudExadataInfrastructureProperties {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PROVISIONING, AVAILABLE, UPDATING, TERMINATING, TERMINATED, FAILED, MAINTENANCE_IN_PROGRESS }
	}
	case class CloudExadataInfrastructureProperties(
	  /** Output only. OCID of created infra. https://docs.oracle.com/en-us/iaas/Content/General/Concepts/identifiers.htm#Oracle */
		ocid: Option[String] = None,
	  /** Optional. The number of compute servers for the Exadata Infrastructure. */
		computeCount: Option[Int] = None,
	  /** Optional. The number of Cloud Exadata storage servers for the Exadata Infrastructure. */
		storageCount: Option[Int] = None,
	  /** Optional. The total storage allocated to the Exadata Infrastructure resource, in gigabytes (GB). */
		totalStorageSizeGb: Option[Int] = None,
	  /** Output only. The available storage can be allocated to the Exadata Infrastructure resource, in gigabytes (GB). */
		availableStorageSizeGb: Option[Int] = None,
	  /** Optional. Maintenance window for repair. */
		maintenanceWindow: Option[Schema.MaintenanceWindow] = None,
	  /** Output only. The current lifecycle state of the Exadata Infrastructure. */
		state: Option[Schema.CloudExadataInfrastructureProperties.StateEnum] = None,
	  /** Required. The shape of the Exadata Infrastructure. The shape determines the amount of CPU, storage, and memory resources allocated to the instance. */
		shape: Option[String] = None,
	  /** Output only. Deep link to the OCI console to view this resource. */
		ociUrl: Option[String] = None,
	  /** Output only. The number of enabled CPU cores. */
		cpuCount: Option[Int] = None,
	  /** Output only. The total number of CPU cores available. */
		maxCpuCount: Option[Int] = None,
	  /** Output only. The memory allocated in GBs. */
		memorySizeGb: Option[Int] = None,
	  /** Output only. The total memory available in GBs. */
		maxMemoryGb: Option[Int] = None,
	  /** Output only. The local node storage allocated in GBs. */
		dbNodeStorageSizeGb: Option[Int] = None,
	  /** Output only. The total local node storage available in GBs. */
		maxDbNodeStorageSizeGb: Option[Int] = None,
	  /** Output only. Size, in terabytes, of the DATA disk group. */
		dataStorageSizeTb: Option[BigDecimal] = None,
	  /** Output only. The total available DATA disk group size. */
		maxDataStorageTb: Option[BigDecimal] = None,
	  /** Output only. The requested number of additional storage servers activated for the Exadata Infrastructure. */
		activatedStorageCount: Option[Int] = None,
	  /** Output only. The requested number of additional storage servers for the Exadata Infrastructure. */
		additionalStorageCount: Option[Int] = None,
	  /** Output only. The software version of the database servers (dom0) in the Exadata Infrastructure. */
		dbServerVersion: Option[String] = None,
	  /** Output only. The software version of the storage servers (cells) in the Exadata Infrastructure. */
		storageServerVersion: Option[String] = None,
	  /** Output only. The OCID of the next maintenance run. */
		nextMaintenanceRunId: Option[String] = None,
	  /** Output only. The time when the next maintenance run will occur. */
		nextMaintenanceRunTime: Option[String] = None,
	  /** Output only. The time when the next security maintenance run will occur. */
		nextSecurityMaintenanceRunTime: Option[String] = None,
	  /** Optional. The list of customer contacts. */
		customerContacts: Option[List[Schema.CustomerContact]] = None,
	  /** Output only. The monthly software version of the storage servers (cells) in the Exadata Infrastructure. Example: 20.1.15 */
		monthlyStorageServerVersion: Option[String] = None,
	  /** Output only. The monthly software version of the database servers (dom0) in the Exadata Infrastructure. Example: 20.1.15 */
		monthlyDbServerVersion: Option[String] = None
	)
	
	object MaintenanceWindow {
		enum PreferenceEnum extends Enum[PreferenceEnum] { case MAINTENANCE_WINDOW_PREFERENCE_UNSPECIFIED, CUSTOM_PREFERENCE, NO_PREFERENCE }
		enum MonthsEnum extends Enum[MonthsEnum] { case MONTH_UNSPECIFIED, JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER }
		enum DaysOfWeekEnum extends Enum[DaysOfWeekEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
		enum PatchingModeEnum extends Enum[PatchingModeEnum] { case PATCHING_MODE_UNSPECIFIED, ROLLING, NON_ROLLING }
	}
	case class MaintenanceWindow(
	  /** Optional. The maintenance window scheduling preference. */
		preference: Option[Schema.MaintenanceWindow.PreferenceEnum] = None,
	  /** Optional. Months during the year when maintenance should be performed. */
		months: Option[List[Schema.MaintenanceWindow.MonthsEnum]] = None,
	  /** Optional. Weeks during the month when maintenance should be performed. Weeks start on the 1st, 8th, 15th, and 22nd days of the month, and have a duration of 7 days. Weeks start and end based on calendar dates, not days of the week. */
		weeksOfMonth: Option[List[Int]] = None,
	  /** Optional. Days during the week when maintenance should be performed. */
		daysOfWeek: Option[List[Schema.MaintenanceWindow.DaysOfWeekEnum]] = None,
	  /** Optional. The window of hours during the day when maintenance should be performed. The window is a 4 hour slot. Valid values are: 0 - represents time slot 0:00 - 3:59 UTC 4 - represents time slot 4:00 - 7:59 UTC 8 - represents time slot 8:00 - 11:59 UTC 12 - represents time slot 12:00 - 15:59 UTC 16 - represents time slot 16:00 - 19:59 UTC 20 - represents time slot 20:00 - 23:59 UTC */
		hoursOfDay: Option[List[Int]] = None,
	  /** Optional. Lead time window allows user to set a lead time to prepare for a down time. The lead time is in weeks and valid value is between 1 to 4. */
		leadTimeWeek: Option[Int] = None,
	  /** Optional. Cloud CloudExadataInfrastructure node patching method, either "ROLLING" or "NONROLLING". Default value is ROLLING. */
		patchingMode: Option[Schema.MaintenanceWindow.PatchingModeEnum] = None,
	  /** Optional. Determines the amount of time the system will wait before the start of each database server patching operation. Custom action timeout is in minutes and valid value is between 15 to 120 (inclusive). */
		customActionTimeoutMins: Option[Int] = None,
	  /** Optional. If true, enables the configuration of a custom action timeout (waiting period) between database server patching operations. */
		isCustomActionTimeoutEnabled: Option[Boolean] = None
	)
	
	case class CustomerContact(
	  /** Required. The email address used by Oracle to send notifications regarding databases and infrastructure. */
		email: Option[String] = None
	)
	
	case class ListCloudVmClustersResponse(
	  /** The list of VM Clusters. */
		cloudVmClusters: Option[List[Schema.CloudVmCluster]] = None,
	  /** A token to fetch the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class CloudVmCluster(
	  /** Identifier. The name of the VM Cluster resource with the format: projects/{project}/locations/{region}/cloudVmClusters/{cloud_vm_cluster} */
		name: Option[String] = None,
	  /** Required. The name of the Exadata Infrastructure resource on which VM cluster resource is created, in the following format: projects/{project}/locations/{region}/cloudExadataInfrastuctures/{cloud_extradata_infrastructure} */
		exadataInfrastructure: Option[String] = None,
	  /** Optional. User friendly name for this resource. */
		displayName: Option[String] = None,
	  /** Output only. Google Cloud Platform location where Oracle Exadata is hosted. It is same as Google Cloud Platform Oracle zone of Exadata infrastructure. */
		gcpOracleZone: Option[String] = None,
	  /** Optional. Various properties of the VM Cluster. */
		properties: Option[Schema.CloudVmClusterProperties] = None,
	  /** Optional. Labels or tags associated with the VM Cluster. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The date and time that the VM cluster was created. */
		createTime: Option[String] = None,
	  /** Required. Network settings. CIDR to use for cluster IP allocation. */
		cidr: Option[String] = None,
	  /** Required. CIDR range of the backup subnet. */
		backupSubnetCidr: Option[String] = None,
	  /** Required. The name of the VPC network. Format: projects/{project}/global/networks/{network} */
		network: Option[String] = None
	)
	
	object CloudVmClusterProperties {
		enum LicenseTypeEnum extends Enum[LicenseTypeEnum] { case LICENSE_TYPE_UNSPECIFIED, LICENSE_INCLUDED, BRING_YOUR_OWN_LICENSE }
		enum DiskRedundancyEnum extends Enum[DiskRedundancyEnum] { case DISK_REDUNDANCY_UNSPECIFIED, HIGH, NORMAL }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PROVISIONING, AVAILABLE, UPDATING, TERMINATING, TERMINATED, FAILED, MAINTENANCE_IN_PROGRESS }
	}
	case class CloudVmClusterProperties(
	  /** Output only. Oracle Cloud Infrastructure ID of VM Cluster. */
		ocid: Option[String] = None,
	  /** Required. License type of VM Cluster. */
		licenseType: Option[Schema.CloudVmClusterProperties.LicenseTypeEnum] = None,
	  /** Optional. Grid Infrastructure Version. */
		giVersion: Option[String] = None,
	  /** Optional. Time zone of VM Cluster to set. Defaults to UTC if not specified. */
		timeZone: Option[Schema.TimeZone] = None,
	  /** Optional. SSH public keys to be stored with cluster. */
		sshPublicKeys: Option[List[String]] = None,
	  /** Optional. Number of database servers. */
		nodeCount: Option[Int] = None,
	  /** Output only. Shape of VM Cluster. */
		shape: Option[String] = None,
	  /** Optional. OCPU count per VM. Minimum is 0.1. */
		ocpuCount: Option[BigDecimal] = None,
	  /** Optional. Memory allocated in GBs. */
		memorySizeGb: Option[Int] = None,
	  /** Optional. Local storage per VM. */
		dbNodeStorageSizeGb: Option[Int] = None,
	  /** Output only. The storage allocation for the disk group, in gigabytes (GB). */
		storageSizeGb: Option[Int] = None,
	  /** Optional. The data disk group size to be allocated in TBs. */
		dataStorageSizeTb: Option[BigDecimal] = None,
	  /** Optional. The type of redundancy. */
		diskRedundancy: Option[Schema.CloudVmClusterProperties.DiskRedundancyEnum] = None,
	  /** Optional. Use exadata sparse snapshots. */
		sparseDiskgroupEnabled: Option[Boolean] = None,
	  /** Optional. Use local backup. */
		localBackupEnabled: Option[Boolean] = None,
	  /** Optional. Prefix for VM cluster host names. */
		hostnamePrefix: Option[String] = None,
	  /** Optional. Data collection options for diagnostics. */
		diagnosticsDataCollectionOptions: Option[Schema.DataCollectionOptions] = None,
	  /** Output only. State of the cluster. */
		state: Option[Schema.CloudVmClusterProperties.StateEnum] = None,
	  /** Output only. SCAN listener port - TCP */
		scanListenerPortTcp: Option[Int] = None,
	  /** Output only. SCAN listener port - TLS */
		scanListenerPortTcpSsl: Option[Int] = None,
	  /** Output only. Parent DNS domain where SCAN DNS and hosts names are qualified. ex: ocispdelegated.ocisp10jvnet.oraclevcn.com */
		domain: Option[String] = None,
	  /** Output only. SCAN DNS name. ex: sp2-yi0xq-scan.ocispdelegated.ocisp10jvnet.oraclevcn.com */
		scanDns: Option[String] = None,
	  /** Output only. host name without domain. format: "-" with some suffix. ex: sp2-yi0xq where "sp2" is the hostname_prefix. */
		hostname: Option[String] = None,
	  /** Required. Number of enabled CPU cores. */
		cpuCoreCount: Option[Int] = None,
	  /** Output only. Operating system version of the image. */
		systemVersion: Option[String] = None,
	  /** Output only. OCIDs of scan IPs. */
		scanIpIds: Option[List[String]] = None,
	  /** Output only. OCID of scan DNS record. */
		scanDnsRecordId: Option[String] = None,
	  /** Output only. Deep link to the OCI console to view this resource. */
		ociUrl: Option[String] = None,
	  /** Optional. OCID of database servers. */
		dbServerOcids: Option[List[String]] = None,
	  /** Output only. Compartment ID of cluster. */
		compartmentId: Option[String] = None,
	  /** Output only. DNS listener IP. */
		dnsListenerIp: Option[String] = None,
	  /** Optional. OCI Cluster name. */
		clusterName: Option[String] = None
	)
	
	case class TimeZone(
	  /** IANA Time Zone Database time zone, e.g. "America/New_York". */
		id: Option[String] = None,
	  /** Optional. IANA Time Zone Database version number, e.g. "2019a". */
		version: Option[String] = None
	)
	
	case class DataCollectionOptions(
	  /** Optional. Indicates whether diagnostic collection is enabled for the VM cluster */
		diagnosticsEventsEnabled: Option[Boolean] = None,
	  /** Optional. Indicates whether health monitoring is enabled for the VM cluster */
		healthMonitoringEnabled: Option[Boolean] = None,
	  /** Optional. Indicates whether incident logs and trace collection are enabled for the VM cluster */
		incidentLogsEnabled: Option[Boolean] = None
	)
	
	case class ListEntitlementsResponse(
	  /** The list of Entitlements */
		entitlements: Option[List[Schema.Entitlement]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None
	)
	
	object Entitlement {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACCOUNT_NOT_LINKED, ACCOUNT_NOT_ACTIVE, ACTIVE }
	}
	case class Entitlement(
	  /** Identifier. The name of the Entitlement resource with the format: projects/{project}/locations/{region}/entitlements/{entitlement} */
		name: Option[String] = None,
	  /** Details of the OCI Cloud Account. */
		cloudAccountDetails: Option[Schema.CloudAccountDetails] = None,
	  /** Output only. Google Cloud Marketplace order ID (aka entitlement ID) */
		entitlementId: Option[String] = None,
	  /** Output only. Entitlement State. */
		state: Option[Schema.Entitlement.StateEnum] = None
	)
	
	case class CloudAccountDetails(
	  /** Output only. OCI account name. */
		cloudAccount: Option[String] = None,
	  /** Output only. OCI account home region. */
		cloudAccountHomeRegion: Option[String] = None,
	  /** Output only. URL to link an existing account. */
		linkExistingAccountUri: Option[String] = None,
	  /** Output only. URL to create a new account and link. */
		accountCreationUri: Option[String] = None
	)
	
	case class ListDbServersResponse(
	  /** The list of database servers. */
		dbServers: Option[List[Schema.DbServer]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None
	)
	
	case class DbServer(
	  /** Identifier. The name of the database server resource with the format: projects/{project}/locations/{location}/cloudExadataInfrastructures/{cloud_exadata_infrastructure}/dbServers/{db_server} */
		name: Option[String] = None,
	  /** Optional. User friendly name for this resource. */
		displayName: Option[String] = None,
	  /** Optional. Various properties of the database server. */
		properties: Option[Schema.DbServerProperties] = None
	)
	
	object DbServerProperties {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, AVAILABLE, UNAVAILABLE, DELETING, DELETED }
	}
	case class DbServerProperties(
	  /** Output only. OCID of database server. */
		ocid: Option[String] = None,
	  /** Optional. OCPU count per database. */
		ocpuCount: Option[Int] = None,
	  /** Optional. Maximum OCPU count per database. */
		maxOcpuCount: Option[Int] = None,
	  /** Optional. Memory allocated in GBs. */
		memorySizeGb: Option[Int] = None,
	  /** Optional. Maximum memory allocated in GBs. */
		maxMemorySizeGb: Option[Int] = None,
	  /** Optional. Local storage per VM. */
		dbNodeStorageSizeGb: Option[Int] = None,
	  /** Optional. Maximum local storage per VM. */
		maxDbNodeStorageSizeGb: Option[Int] = None,
	  /** Optional. Vm count per database. */
		vmCount: Option[Int] = None,
	  /** Output only. State of the database server. */
		state: Option[Schema.DbServerProperties.StateEnum] = None,
	  /** Output only. OCID of database nodes associated with the database server. */
		dbNodeIds: Option[List[String]] = None
	)
	
	case class ListDbNodesResponse(
	  /** The list of DB Nodes */
		dbNodes: Option[List[Schema.DbNode]] = None,
	  /** A token identifying a page of results the node should return. */
		nextPageToken: Option[String] = None
	)
	
	case class DbNode(
	  /** Identifier. The name of the database node resource in the following format: projects/{project}/locations/{location}/cloudVmClusters/{cloud_vm_cluster}/dbNodes/{db_node} */
		name: Option[String] = None,
	  /** Optional. Various properties of the database node. */
		properties: Option[Schema.DbNodeProperties] = None
	)
	
	object DbNodeProperties {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PROVISIONING, AVAILABLE, UPDATING, STOPPING, STOPPED, STARTING, TERMINATING, TERMINATED, FAILED }
	}
	case class DbNodeProperties(
	  /** Output only. OCID of database node. */
		ocid: Option[String] = None,
	  /** Optional. OCPU count per database node. */
		ocpuCount: Option[Int] = None,
	  /** Memory allocated in GBs. */
		memorySizeGb: Option[Int] = None,
	  /** Optional. Local storage per database node. */
		dbNodeStorageSizeGb: Option[Int] = None,
	  /** Optional. Database server OCID. */
		dbServerOcid: Option[String] = None,
	  /** Optional. DNS */
		hostname: Option[String] = None,
	  /** Output only. State of the database node. */
		state: Option[Schema.DbNodeProperties.StateEnum] = None,
	  /** Total CPU core count of the database node. */
		totalCpuCoreCount: Option[Int] = None
	)
	
	case class ListGiVersionsResponse(
	  /** The list of Oracle Grid Infrastructure (GI) versions. */
		giVersions: Option[List[Schema.GiVersion]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None
	)
	
	case class GiVersion(
	  /** Identifier. The name of the Oracle Grid Infrastructure (GI) version resource with the format: projects/{project}/locations/{region}/giVersions/{gi_versions} */
		name: Option[String] = None,
	  /** Optional. version */
		version: Option[String] = None
	)
	
	case class ListDbSystemShapesResponse(
	  /** The list of Database System shapes. */
		dbSystemShapes: Option[List[Schema.DbSystemShape]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None
	)
	
	case class DbSystemShape(
	  /** Identifier. The name of the Database System Shape resource with the format: projects/{project}/locations/{region}/dbSystemShapes/{db_system_shape} */
		name: Option[String] = None,
	  /** Optional. shape */
		shape: Option[String] = None,
	  /** Optional. Minimum number of database servers. */
		minNodeCount: Option[Int] = None,
	  /** Optional. Maximum number of database servers. */
		maxNodeCount: Option[Int] = None,
	  /** Optional. Minimum number of storage servers. */
		minStorageCount: Option[Int] = None,
	  /** Optional. Maximum number of storage servers. */
		maxStorageCount: Option[Int] = None,
	  /** Optional. Number of cores per node. */
		availableCoreCountPerNode: Option[Int] = None,
	  /** Optional. Memory per database server node in gigabytes. */
		availableMemoryPerNodeGb: Option[Int] = None,
	  /** Optional. Storage per storage server in terabytes. */
		availableDataStorageTb: Option[Int] = None,
	  /** Optional. Minimum core count per node. */
		minCoreCountPerNode: Option[Int] = None,
	  /** Optional. Minimum memory per node in gigabytes. */
		minMemoryPerNodeGb: Option[Int] = None,
	  /** Optional. Minimum node storage per database server in gigabytes. */
		minDbNodeStoragePerNodeGb: Option[Int] = None
	)
	
	case class ListAutonomousDatabasesResponse(
	  /** The list of Autonomous Databases. */
		autonomousDatabases: Option[List[Schema.AutonomousDatabase]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None
	)
	
	case class AutonomousDatabase(
	  /** Identifier. The name of the Autonomous Database resource in the following format: projects/{project}/locations/{region}/autonomousDatabases/{autonomous_database} */
		name: Option[String] = None,
	  /** Optional. The name of the Autonomous Database. The database name must be unique in the project. The name must begin with a letter and can contain a maximum of 30 alphanumeric characters. */
		database: Option[String] = None,
	  /** Optional. The display name for the Autonomous Database. The name does not have to be unique within your project. */
		displayName: Option[String] = None,
	  /** Output only. The ID of the subscription entitlement associated with the Autonomous Database. */
		entitlementId: Option[String] = None,
	  /** Optional. The password for the default ADMIN user. */
		adminPassword: Option[String] = None,
	  /** Optional. The properties of the Autonomous Database. */
		properties: Option[Schema.AutonomousDatabaseProperties] = None,
	  /** Optional. The labels or tags associated with the Autonomous Database. */
		labels: Option[Map[String, String]] = None,
	  /** Required. The name of the VPC network used by the Autonomous Database in the following format: projects/{project}/global/networks/{network} */
		network: Option[String] = None,
	  /** Required. The subnet CIDR range for the Autonmous Database. */
		cidr: Option[String] = None,
	  /** Output only. The date and time that the Autonomous Database was created. */
		createTime: Option[String] = None
	)
	
	object AutonomousDatabaseProperties {
		enum DbWorkloadEnum extends Enum[DbWorkloadEnum] { case DB_WORKLOAD_UNSPECIFIED, OLTP, DW, AJD, APEX }
		enum DbEditionEnum extends Enum[DbEditionEnum] { case DATABASE_EDITION_UNSPECIFIED, STANDARD_EDITION, ENTERPRISE_EDITION }
		enum LicenseTypeEnum extends Enum[LicenseTypeEnum] { case LICENSE_TYPE_UNSPECIFIED, LICENSE_INCLUDED, BRING_YOUR_OWN_LICENSE }
		enum MaintenanceScheduleTypeEnum extends Enum[MaintenanceScheduleTypeEnum] { case MAINTENANCE_SCHEDULE_TYPE_UNSPECIFIED, EARLY, REGULAR }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PROVISIONING, AVAILABLE, STOPPING, STOPPED, STARTING, TERMINATING, TERMINATED, UNAVAILABLE, RESTORE_IN_PROGRESS, RESTORE_FAILED, BACKUP_IN_PROGRESS, SCALE_IN_PROGRESS, AVAILABLE_NEEDS_ATTENTION, UPDATING, MAINTENANCE_IN_PROGRESS, RESTARTING, RECREATING, ROLE_CHANGE_IN_PROGRESS, UPGRADING, INACCESSIBLE, STANDBY }
		enum LocalDisasterRecoveryTypeEnum extends Enum[LocalDisasterRecoveryTypeEnum] { case LOCAL_DISASTER_RECOVERY_TYPE_UNSPECIFIED, ADG, BACKUP_BASED }
		enum DataSafeStateEnum extends Enum[DataSafeStateEnum] { case DATA_SAFE_STATE_UNSPECIFIED, REGISTERING, REGISTERED, DEREGISTERING, NOT_REGISTERED, FAILED }
		enum DatabaseManagementStateEnum extends Enum[DatabaseManagementStateEnum] { case DATABASE_MANAGEMENT_STATE_UNSPECIFIED, ENABLING, ENABLED, DISABLING, NOT_ENABLED, FAILED_ENABLING, FAILED_DISABLING }
		enum OpenModeEnum extends Enum[OpenModeEnum] { case OPEN_MODE_UNSPECIFIED, READ_ONLY, READ_WRITE }
		enum OperationsInsightsStateEnum extends Enum[OperationsInsightsStateEnum] { case OPERATIONS_INSIGHTS_STATE_UNSPECIFIED, ENABLING, ENABLED, DISABLING, NOT_ENABLED, FAILED_ENABLING, FAILED_DISABLING }
		enum PermissionLevelEnum extends Enum[PermissionLevelEnum] { case PERMISSION_LEVEL_UNSPECIFIED, RESTRICTED, UNRESTRICTED }
		enum RefreshableModeEnum extends Enum[RefreshableModeEnum] { case REFRESHABLE_MODE_UNSPECIFIED, AUTOMATIC, MANUAL }
		enum RefreshableStateEnum extends Enum[RefreshableStateEnum] { case REFRESHABLE_STATE_UNSPECIFIED, REFRESHING, NOT_REFRESHING }
		enum RoleEnum extends Enum[RoleEnum] { case ROLE_UNSPECIFIED, PRIMARY, STANDBY, DISABLED_STANDBY, BACKUP_COPY, SNAPSHOT_STANDBY }
	}
	case class AutonomousDatabaseProperties(
	  /** Output only. OCID of the Autonomous Database. https://docs.oracle.com/en-us/iaas/Content/General/Concepts/identifiers.htm#Oracle */
		ocid: Option[String] = None,
	  /** Optional. The number of compute servers for the Autonomous Database. */
		computeCount: Option[BigDecimal] = None,
	  /** Optional. The number of CPU cores to be made available to the database. */
		cpuCoreCount: Option[Int] = None,
	  /** Optional. The size of the data stored in the database, in terabytes. */
		dataStorageSizeTb: Option[Int] = None,
	  /** Optional. The size of the data stored in the database, in gigabytes. */
		dataStorageSizeGb: Option[Int] = None,
	  /** Required. The workload type of the Autonomous Database. */
		dbWorkload: Option[Schema.AutonomousDatabaseProperties.DbWorkloadEnum] = None,
	  /** Optional. The edition of the Autonomous Databases. */
		dbEdition: Option[Schema.AutonomousDatabaseProperties.DbEditionEnum] = None,
	  /** Optional. The character set for the Autonomous Database. The default is AL32UTF8. */
		characterSet: Option[String] = None,
	  /** Optional. The national character set for the Autonomous Database. The default is AL16UTF16. */
		nCharacterSet: Option[String] = None,
	  /** Optional. The private endpoint IP address for the Autonomous Database. */
		privateEndpointIp: Option[String] = None,
	  /** Optional. The private endpoint label for the Autonomous Database. */
		privateEndpointLabel: Option[String] = None,
	  /** Optional. The Oracle Database version for the Autonomous Database. */
		dbVersion: Option[String] = None,
	  /** Optional. This field indicates if auto scaling is enabled for the Autonomous Database CPU core count. */
		isAutoScalingEnabled: Option[Boolean] = None,
	  /** Optional. This field indicates if auto scaling is enabled for the Autonomous Database storage. */
		isStorageAutoScalingEnabled: Option[Boolean] = None,
	  /** Required. The license type used for the Autonomous Database. */
		licenseType: Option[Schema.AutonomousDatabaseProperties.LicenseTypeEnum] = None,
	  /** Optional. The list of customer contacts. */
		customerContacts: Option[List[Schema.CustomerContact]] = None,
	  /** Optional. The ID of the Oracle Cloud Infrastructure vault secret. */
		secretId: Option[String] = None,
	  /** Optional. The ID of the Oracle Cloud Infrastructure vault. */
		vaultId: Option[String] = None,
	  /** Optional. The maintenance schedule of the Autonomous Database. */
		maintenanceScheduleType: Option[Schema.AutonomousDatabaseProperties.MaintenanceScheduleTypeEnum] = None,
	  /** Optional. This field specifies if the Autonomous Database requires mTLS connections. */
		mtlsConnectionRequired: Option[Boolean] = None,
	  /** Optional. The retention period for the Autonomous Database. This field is specified in days, can range from 1 day to 60 days, and has a default value of 60 days. */
		backupRetentionPeriodDays: Option[Int] = None,
	  /** Output only. The amount of storage currently being used for user and system data, in terabytes. */
		actualUsedDataStorageSizeTb: Option[BigDecimal] = None,
	  /** Output only. The amount of storage currently allocated for the database tables and billed for, rounded up in terabytes. */
		allocatedStorageSizeTb: Option[BigDecimal] = None,
	  /** Output only. The details for the Oracle APEX Application Development. */
		apexDetails: Option[Schema.AutonomousDatabaseApex] = None,
	  /** Output only. This field indicates the status of Data Guard and Access control for the Autonomous Database. The field's value is null if Data Guard is disabled or Access Control is disabled. The field's value is TRUE if both Data Guard and Access Control are enabled, and the Autonomous Database is using primary IP access control list (ACL) for standby. The field's value is FALSE if both Data Guard and Access Control are enabled, and the Autonomous Database is using a different IP access control list (ACL) for standby compared to primary. */
		arePrimaryAllowlistedIpsUsed: Option[Boolean] = None,
	  /** Output only. The details of the current lifestyle state of the Autonomous Database. */
		lifecycleDetails: Option[String] = None,
	  /** Output only. The current lifecycle state of the Autonomous Database. */
		state: Option[Schema.AutonomousDatabaseProperties.StateEnum] = None,
	  /** Output only. The Autonomous Container Database OCID. */
		autonomousContainerDatabaseId: Option[String] = None,
	  /** Output only. The list of available Oracle Database upgrade versions for an Autonomous Database. */
		availableUpgradeVersions: Option[List[String]] = None,
	  /** Output only. The connection strings used to connect to an Autonomous Database. */
		connectionStrings: Option[Schema.AutonomousDatabaseConnectionStrings] = None,
	  /** Output only. The Oracle Connection URLs for an Autonomous Database. */
		connectionUrls: Option[Schema.AutonomousDatabaseConnectionUrls] = None,
	  /** Output only. This field indicates the number of seconds of data loss during a Data Guard failover. */
		failedDataRecoveryDuration: Option[String] = None,
	  /** Output only. The memory assigned to in-memory tables in an Autonomous Database. */
		memoryTableGbs: Option[Int] = None,
	  /** Output only. This field indicates whether the Autonomous Database has local (in-region) Data Guard enabled. */
		isLocalDataGuardEnabled: Option[Boolean] = None,
	  /** Output only. This field indicates the maximum data loss limit for an Autonomous Database, in seconds. */
		localAdgAutoFailoverMaxDataLossLimit: Option[Int] = None,
	  /** Output only. The details of the Autonomous Data Guard standby database. */
		localStandbyDb: Option[Schema.AutonomousDatabaseStandbySummary] = None,
	  /** Output only. The amount of memory enabled per ECPU, in gigabytes. */
		memoryPerOracleComputeUnitGbs: Option[Int] = None,
	  /** Output only. This field indicates the local disaster recovery (DR) type of an Autonomous Database. */
		localDisasterRecoveryType: Option[Schema.AutonomousDatabaseProperties.LocalDisasterRecoveryTypeEnum] = None,
	  /** Output only. The current state of the Data Safe registration for the Autonomous Database. */
		dataSafeState: Option[Schema.AutonomousDatabaseProperties.DataSafeStateEnum] = None,
	  /** Output only. The current state of database management for the Autonomous Database. */
		databaseManagementState: Option[Schema.AutonomousDatabaseProperties.DatabaseManagementStateEnum] = None,
	  /** Output only. This field indicates the current mode of the Autonomous Database. */
		openMode: Option[Schema.AutonomousDatabaseProperties.OpenModeEnum] = None,
	  /** Output only. This field indicates the state of Operations Insights for the Autonomous Database. */
		operationsInsightsState: Option[Schema.AutonomousDatabaseProperties.OperationsInsightsStateEnum] = None,
	  /** Output only. The list of OCIDs of standby databases located in Autonomous Data Guard remote regions that are associated with the source database. */
		peerDbIds: Option[List[String]] = None,
	  /** Output only. The permission level of the Autonomous Database. */
		permissionLevel: Option[Schema.AutonomousDatabaseProperties.PermissionLevelEnum] = None,
	  /** Output only. The private endpoint for the Autonomous Database. */
		privateEndpoint: Option[String] = None,
	  /** Output only. The refresh mode of the cloned Autonomous Database. */
		refreshableMode: Option[Schema.AutonomousDatabaseProperties.RefreshableModeEnum] = None,
	  /** Output only. The refresh State of the clone. */
		refreshableState: Option[Schema.AutonomousDatabaseProperties.RefreshableStateEnum] = None,
	  /** Output only. The Data Guard role of the Autonomous Database. */
		role: Option[Schema.AutonomousDatabaseProperties.RoleEnum] = None,
	  /** Output only. The list and details of the scheduled operations of the Autonomous Database. */
		scheduledOperationDetails: Option[List[Schema.ScheduledOperationDetails]] = None,
	  /** Output only. The SQL Web Developer URL for the Autonomous Database. */
		sqlWebDeveloperUrl: Option[String] = None,
	  /** Output only. The list of available regions that can be used to create a clone for the Autonomous Database. */
		supportedCloneRegions: Option[List[String]] = None,
	  /** Output only. The storage space used by Autonomous Database, in gigabytes. */
		usedDataStorageSizeTbs: Option[Int] = None,
	  /** Output only. The Oracle Cloud Infrastructure link for the Autonomous Database. */
		ociUrl: Option[String] = None,
	  /** Output only. The storage space used by automatic backups of Autonomous Database, in gigabytes. */
		totalAutoBackupStorageSizeGbs: Option[BigDecimal] = None,
	  /** Output only. The long term backup schedule of the Autonomous Database. */
		nextLongTermBackupTime: Option[String] = None,
	  /** Output only. The date and time when maintenance will begin. */
		maintenanceBeginTime: Option[String] = None,
	  /** Output only. The date and time when maintenance will end. */
		maintenanceEndTime: Option[String] = None
	)
	
	case class AutonomousDatabaseApex(
	  /** Output only. The Oracle APEX Application Development version. */
		apexVersion: Option[String] = None,
	  /** Output only. The Oracle REST Data Services (ORDS) version. */
		ordsVersion: Option[String] = None
	)
	
	case class AutonomousDatabaseConnectionStrings(
	  /** Output only. Returns all connection strings that can be used to connect to the Autonomous Database. */
		allConnectionStrings: Option[Schema.AllConnectionStrings] = None,
	  /** Output only. The database service provides the least level of resources to each SQL statement, but supports the most number of concurrent SQL statements. */
		dedicated: Option[String] = None,
	  /** Output only. The database service provides the highest level of resources to each SQL statement. */
		high: Option[String] = None,
	  /** Output only. The database service provides the least level of resources to each SQL statement. */
		low: Option[String] = None,
	  /** Output only. The database service provides a lower level of resources to each SQL statement. */
		medium: Option[String] = None,
	  /** Output only. A list of connection string profiles to allow clients to group, filter, and select values based on the structured metadata. */
		profiles: Option[List[Schema.DatabaseConnectionStringProfile]] = None
	)
	
	case class AllConnectionStrings(
	  /** Output only. The database service provides the highest level of resources to each SQL statement. */
		high: Option[String] = None,
	  /** Output only. The database service provides the least level of resources to each SQL statement. */
		low: Option[String] = None,
	  /** Output only. The database service provides a lower level of resources to each SQL statement. */
		medium: Option[String] = None
	)
	
	object DatabaseConnectionStringProfile {
		enum ConsumerGroupEnum extends Enum[ConsumerGroupEnum] { case CONSUMER_GROUP_UNSPECIFIED, HIGH, MEDIUM, LOW, TP, TPURGENT }
		enum HostFormatEnum extends Enum[HostFormatEnum] { case HOST_FORMAT_UNSPECIFIED, FQDN, IP }
		enum ProtocolEnum extends Enum[ProtocolEnum] { case PROTOCOL_UNSPECIFIED, TCP, TCPS }
		enum SessionModeEnum extends Enum[SessionModeEnum] { case SESSION_MODE_UNSPECIFIED, DIRECT, INDIRECT }
		enum SyntaxFormatEnum extends Enum[SyntaxFormatEnum] { case SYNTAX_FORMAT_UNSPECIFIED, LONG, EZCONNECT, EZCONNECTPLUS }
		enum TlsAuthenticationEnum extends Enum[TlsAuthenticationEnum] { case TLS_AUTHENTICATION_UNSPECIFIED, SERVER, MUTUAL }
	}
	case class DatabaseConnectionStringProfile(
	  /** Output only. The current consumer group being used by the connection. */
		consumerGroup: Option[Schema.DatabaseConnectionStringProfile.ConsumerGroupEnum] = None,
	  /** Output only. The display name for the database connection. */
		displayName: Option[String] = None,
	  /** Output only. The host name format being currently used in connection string. */
		hostFormat: Option[Schema.DatabaseConnectionStringProfile.HostFormatEnum] = None,
	  /** Output only. This field indicates if the connection string is regional and is only applicable for cross-region Data Guard. */
		isRegional: Option[Boolean] = None,
	  /** Output only. The protocol being used by the connection. */
		protocol: Option[Schema.DatabaseConnectionStringProfile.ProtocolEnum] = None,
	  /** Output only. The current session mode of the connection. */
		sessionMode: Option[Schema.DatabaseConnectionStringProfile.SessionModeEnum] = None,
	  /** Output only. The syntax of the connection string. */
		syntaxFormat: Option[Schema.DatabaseConnectionStringProfile.SyntaxFormatEnum] = None,
	  /** Output only. This field indicates the TLS authentication type of the connection. */
		tlsAuthentication: Option[Schema.DatabaseConnectionStringProfile.TlsAuthenticationEnum] = None,
	  /** Output only. The value of the connection string. */
		value: Option[String] = None
	)
	
	case class AutonomousDatabaseConnectionUrls(
	  /** Output only. Oracle Application Express (APEX) URL. */
		apexUri: Option[String] = None,
	  /** Output only. The URL of the Database Transforms for the Autonomous Database. */
		databaseTransformsUri: Option[String] = None,
	  /** Output only. The URL of the Graph Studio for the Autonomous Database. */
		graphStudioUri: Option[String] = None,
	  /** Output only. The URL of the Oracle Machine Learning (OML) Notebook for the Autonomous Database. */
		machineLearningNotebookUri: Option[String] = None,
	  /** Output only. The URL of Machine Learning user management the Autonomous Database. */
		machineLearningUserManagementUri: Option[String] = None,
	  /** Output only. The URL of the MongoDB API for the Autonomous Database. */
		mongoDbUri: Option[String] = None,
	  /** Output only. The Oracle REST Data Services (ORDS) URL of the Web Access for the Autonomous Database. */
		ordsUri: Option[String] = None,
	  /** Output only. The URL of the Oracle SQL Developer Web for the Autonomous Database. */
		sqlDevWebUri: Option[String] = None
	)
	
	object AutonomousDatabaseStandbySummary {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PROVISIONING, AVAILABLE, STOPPING, STOPPED, STARTING, TERMINATING, TERMINATED, UNAVAILABLE, RESTORE_IN_PROGRESS, RESTORE_FAILED, BACKUP_IN_PROGRESS, SCALE_IN_PROGRESS, AVAILABLE_NEEDS_ATTENTION, UPDATING, MAINTENANCE_IN_PROGRESS, RESTARTING, RECREATING, ROLE_CHANGE_IN_PROGRESS, UPGRADING, INACCESSIBLE, STANDBY }
	}
	case class AutonomousDatabaseStandbySummary(
	  /** Output only. The amount of time, in seconds, that the data of the standby database lags in comparison to the data of the primary database. */
		lagTimeDuration: Option[String] = None,
	  /** Output only. The additional details about the current lifecycle state of the Autonomous Database. */
		lifecycleDetails: Option[String] = None,
	  /** Output only. The current lifecycle state of the Autonomous Database. */
		state: Option[Schema.AutonomousDatabaseStandbySummary.StateEnum] = None,
	  /** Output only. The date and time the Autonomous Data Guard role was switched for the standby Autonomous Database. */
		dataGuardRoleChangedTime: Option[String] = None,
	  /** Output only. The date and time the Disaster Recovery role was switched for the standby Autonomous Database. */
		disasterRecoveryRoleChangedTime: Option[String] = None
	)
	
	object ScheduledOperationDetails {
		enum DayOfWeekEnum extends Enum[DayOfWeekEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class ScheduledOperationDetails(
	  /** Output only. Day of week. */
		dayOfWeek: Option[Schema.ScheduledOperationDetails.DayOfWeekEnum] = None,
	  /** Output only. Auto start time. */
		startTime: Option[Schema.TimeOfDay] = None,
	  /** Output only. Auto stop time. */
		stopTime: Option[Schema.TimeOfDay] = None
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
	
	case class RestoreAutonomousDatabaseRequest(
	  /** Required. The time and date to restore the database to. */
		restoreTime: Option[String] = None
	)
	
	object GenerateAutonomousDatabaseWalletRequest {
		enum TypeEnum extends Enum[TypeEnum] { case GENERATE_TYPE_UNSPECIFIED, ALL, SINGLE }
	}
	case class GenerateAutonomousDatabaseWalletRequest(
	  /** Optional. The type of wallet generation for the Autonomous Database. The default value is SINGLE. */
		`type`: Option[Schema.GenerateAutonomousDatabaseWalletRequest.TypeEnum] = None,
	  /** Optional. True when requesting regional connection strings in PDB connect info, applicable to cross-region Data Guard only. */
		isRegional: Option[Boolean] = None,
	  /** Required. The password used to encrypt the keys inside the wallet. The password must be a minimum of 8 characters. */
		password: Option[String] = None
	)
	
	case class GenerateAutonomousDatabaseWalletResponse(
	  /** Output only. The base64 encoded wallet files. */
		archiveContent: Option[String] = None
	)
	
	case class ListAutonomousDbVersionsResponse(
	  /** The list of Autonomous Database versions. */
		autonomousDbVersions: Option[List[Schema.AutonomousDbVersion]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None
	)
	
	object AutonomousDbVersion {
		enum DbWorkloadEnum extends Enum[DbWorkloadEnum] { case DB_WORKLOAD_UNSPECIFIED, OLTP, DW, AJD, APEX }
	}
	case class AutonomousDbVersion(
	  /** Identifier. The name of the Autonomous Database Version resource with the format: projects/{project}/locations/{region}/autonomousDbVersions/{autonomous_db_version} */
		name: Option[String] = None,
	  /** Output only. An Oracle Database version for Autonomous Database. */
		version: Option[String] = None,
	  /** Output only. The Autonomous Database workload type. */
		dbWorkload: Option[Schema.AutonomousDbVersion.DbWorkloadEnum] = None,
	  /** Output only. A URL that points to a detailed description of the Autonomous Database version. */
		workloadUri: Option[String] = None
	)
	
	case class ListAutonomousDatabaseCharacterSetsResponse(
	  /** The list of Autonomous Database Character Sets. */
		autonomousDatabaseCharacterSets: Option[List[Schema.AutonomousDatabaseCharacterSet]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None
	)
	
	object AutonomousDatabaseCharacterSet {
		enum CharacterSetTypeEnum extends Enum[CharacterSetTypeEnum] { case CHARACTER_SET_TYPE_UNSPECIFIED, DATABASE, NATIONAL }
	}
	case class AutonomousDatabaseCharacterSet(
	  /** Identifier. The name of the Autonomous Database Character Set resource in the following format: projects/{project}/locations/{region}/autonomousDatabaseCharacterSets/{autonomous_database_character_set} */
		name: Option[String] = None,
	  /** Output only. The character set type for the Autonomous Database. */
		characterSetType: Option[Schema.AutonomousDatabaseCharacterSet.CharacterSetTypeEnum] = None,
	  /** Output only. The character set name for the Autonomous Database which is the ID in the resource name. */
		characterSet: Option[String] = None
	)
	
	case class ListAutonomousDatabaseBackupsResponse(
	  /** The list of Autonomous Database Backups. */
		autonomousDatabaseBackups: Option[List[Schema.AutonomousDatabaseBackup]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None
	)
	
	case class AutonomousDatabaseBackup(
	  /** Identifier. The name of the Autonomous Database Backup resource with the format: projects/{project}/locations/{region}/autonomousDatabaseBackups/{autonomous_database_backup} */
		name: Option[String] = None,
	  /** Required. The name of the Autonomous Database resource for which the backup is being created. Format: projects/{project}/locations/{region}/autonomousDatabases/{autonomous_database} */
		autonomousDatabase: Option[String] = None,
	  /** Optional. User friendly name for the Backup. The name does not have to be unique. */
		displayName: Option[String] = None,
	  /** Optional. Various properties of the backup. */
		properties: Option[Schema.AutonomousDatabaseBackupProperties] = None,
	  /** Optional. labels or tags associated with the resource. */
		labels: Option[Map[String, String]] = None
	)
	
	object AutonomousDatabaseBackupProperties {
		enum LifecycleStateEnum extends Enum[LifecycleStateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, DELETED, FAILED, UPDATING }
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, INCREMENTAL, FULL, LONG_TERM }
	}
	case class AutonomousDatabaseBackupProperties(
	  /** Output only. OCID of the Autonomous Database backup. https://docs.oracle.com/en-us/iaas/Content/General/Concepts/identifiers.htm#Oracle */
		ocid: Option[String] = None,
	  /** Optional. Retention period in days for the backup. */
		retentionPeriodDays: Option[Int] = None,
	  /** Output only. The OCID of the compartment. */
		compartmentId: Option[String] = None,
	  /** Output only. The quantity of data in the database, in terabytes. */
		databaseSizeTb: Option[BigDecimal] = None,
	  /** Output only. A valid Oracle Database version for Autonomous Database. */
		dbVersion: Option[String] = None,
	  /** Output only. Indicates if the backup is long term backup. */
		isLongTermBackup: Option[Boolean] = None,
	  /** Output only. Indicates if the backup is automatic or user initiated. */
		isAutomaticBackup: Option[Boolean] = None,
	  /** Output only. Indicates if the backup can be used to restore the Autonomous Database. */
		isRestorable: Option[Boolean] = None,
	  /** Optional. The OCID of the key store of Oracle Vault. */
		keyStoreId: Option[String] = None,
	  /** Optional. The wallet name for Oracle Key Vault. */
		keyStoreWallet: Option[String] = None,
	  /** Optional. The OCID of the key container that is used as the master encryption key in database transparent data encryption (TDE) operations. */
		kmsKeyId: Option[String] = None,
	  /** Optional. The OCID of the key container version that is used in database transparent data encryption (TDE) operations KMS Key can have multiple key versions. If none is specified, the current key version (latest) of the Key Id is used for the operation. Autonomous Database Serverless does not use key versions, hence is not applicable for Autonomous Database Serverless instances. */
		kmsKeyVersionId: Option[String] = None,
	  /** Output only. Additional information about the current lifecycle state. */
		lifecycleDetails: Option[String] = None,
	  /** Output only. The lifecycle state of the backup. */
		lifecycleState: Option[Schema.AutonomousDatabaseBackupProperties.LifecycleStateEnum] = None,
	  /** Output only. The backup size in terabytes. */
		sizeTb: Option[BigDecimal] = None,
	  /** Output only. Timestamp until when the backup will be available. */
		availableTillTime: Option[String] = None,
	  /** Output only. The date and time the backup completed. */
		endTime: Option[String] = None,
	  /** Output only. The date and time the backup started. */
		startTime: Option[String] = None,
	  /** Output only. The type of the backup. */
		`type`: Option[Schema.AutonomousDatabaseBackupProperties.TypeEnum] = None,
	  /** Optional. The OCID of the vault. */
		vaultId: Option[String] = None
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
	  /** Output only. The status of the operation. */
		statusMessage: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have been cancelled successfully have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None,
	  /** Output only. An estimated percentage of the operation that has been completed at a given moment of time, between 0 and 100. */
		percentComplete: Option[BigDecimal] = None
	)
	
	case class LocationMetadata(
	  /** Output only. Google Cloud Platform Oracle zones in a location. */
		gcpOracleZones: Option[List[String]] = None
	)
}
