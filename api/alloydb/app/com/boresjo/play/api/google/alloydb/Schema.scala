package com.boresjo.play.api.google.alloydb

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
	
	case class ListClustersResponse(
	  /** The list of Cluster */
		clusters: Option[List[Schema.Cluster]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Cluster {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, READY, STOPPED, EMPTY, CREATING, DELETING, FAILED, BOOTSTRAPPING, MAINTENANCE, PROMOTING }
		enum ClusterTypeEnum extends Enum[ClusterTypeEnum] { case CLUSTER_TYPE_UNSPECIFIED, PRIMARY, SECONDARY }
		enum DatabaseVersionEnum extends Enum[DatabaseVersionEnum] { case DATABASE_VERSION_UNSPECIFIED, POSTGRES_13, POSTGRES_14, POSTGRES_15, POSTGRES_16 }
		enum SubscriptionTypeEnum extends Enum[SubscriptionTypeEnum] { case SUBSCRIPTION_TYPE_UNSPECIFIED, STANDARD, TRIAL }
	}
	case class Cluster(
	  /** Output only. Cluster created from backup. */
		backupSource: Option[Schema.BackupSource] = None,
	  /** Output only. Cluster created via DMS migration. */
		migrationSource: Option[Schema.MigrationSource] = None,
	  /** Output only. The name of the cluster resource with the format: &#42; projects/{project}/locations/{region}/clusters/{cluster_id} where the cluster ID segment should satisfy the regex expression `[a-z0-9-]+`. For more details see https://google.aip.dev/122. The prefix of the cluster resource name is the name of the parent resource: &#42; projects/{project}/locations/{region} */
		name: Option[String] = None,
	  /** User-settable and human-readable display name for the Cluster. */
		displayName: Option[String] = None,
	  /** Output only. The system-generated UID of the resource. The UID is assigned when the resource is created, and it is retained until it is deleted. */
		uid: Option[String] = None,
	  /** Output only. Create time stamp */
		createTime: Option[String] = None,
	  /** Output only. Update time stamp */
		updateTime: Option[String] = None,
	  /** Output only. Delete time stamp */
		deleteTime: Option[String] = None,
	  /** Labels as key value pairs */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The current serving state of the cluster. */
		state: Option[Schema.Cluster.StateEnum] = None,
	  /** Output only. The type of the cluster. This is an output-only field and it's populated at the Cluster creation time or the Cluster promotion time. The cluster type is determined by which RPC was used to create the cluster (i.e. `CreateCluster` vs. `CreateSecondaryCluster` */
		clusterType: Option[Schema.Cluster.ClusterTypeEnum] = None,
	  /** Optional. The database engine major version. This is an optional field and it is populated at the Cluster creation time. If a database version is not supplied at cluster creation time, then a default database version will be used. */
		databaseVersion: Option[Schema.Cluster.DatabaseVersionEnum] = None,
		networkConfig: Option[Schema.NetworkConfig] = None,
	  /** Required. The resource link for the VPC network in which cluster resources are created and from which they are accessible via Private IP. The network must belong to the same project as the cluster. It is specified in the form: `projects/{project}/global/networks/{network_id}`. This is required to create a cluster. Deprecated, use network_config.network instead. */
		network: Option[String] = None,
	  /** For Resource freshness validation (https://google.aip.dev/154) */
		etag: Option[String] = None,
	  /** Annotations to allow client tools to store small amount of arbitrary data. This is distinct from labels. https://google.aip.dev/128 */
		annotations: Option[Map[String, String]] = None,
	  /** Output only. Reconciling (https://google.aip.dev/128#reconciliation). Set to true if the current state of Cluster does not match the user's intended state, and the service is actively updating the resource to reconcile them. This can happen due to user-triggered updates or system actions like failover or maintenance. */
		reconciling: Option[Boolean] = None,
	  /** Input only. Initial user to setup during cluster creation. Required. If used in `RestoreCluster` this is ignored. */
		initialUser: Option[Schema.UserPassword] = None,
	  /** The automated backup policy for this cluster. If no policy is provided then the default policy will be used. If backups are supported for the cluster, the default policy takes one backup a day, has a backup window of 1 hour, and retains backups for 14 days. For more information on the defaults, consult the documentation for the message type. */
		automatedBackupPolicy: Option[Schema.AutomatedBackupPolicy] = None,
	  /** SSL configuration for this AlloyDB cluster. */
		sslConfig: Option[Schema.SslConfig] = None,
	  /** Optional. The encryption config can be specified to encrypt the data disks and other persistent data resources of a cluster with a customer-managed encryption key (CMEK). When this field is not specified, the cluster will then use default encryption scheme to protect the user data. */
		encryptionConfig: Option[Schema.EncryptionConfig] = None,
	  /** Output only. The encryption information for the cluster. */
		encryptionInfo: Option[Schema.EncryptionInfo] = None,
	  /** Optional. Continuous backup configuration for this cluster. */
		continuousBackupConfig: Option[Schema.ContinuousBackupConfig] = None,
	  /** Output only. Continuous backup properties for this cluster. */
		continuousBackupInfo: Option[Schema.ContinuousBackupInfo] = None,
	  /** Cross Region replication config specific to SECONDARY cluster. */
		secondaryConfig: Option[Schema.SecondaryConfig] = None,
	  /** Output only. Cross Region replication config specific to PRIMARY cluster. */
		primaryConfig: Option[Schema.PrimaryConfig] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Optional. The configuration for Private Service Connect (PSC) for the cluster. */
		pscConfig: Option[Schema.PscConfig] = None,
	  /** Optional. The maintenance update policy determines when to allow or deny updates. */
		maintenanceUpdatePolicy: Option[Schema.MaintenanceUpdatePolicy] = None,
	  /** Output only. The maintenance schedule for the cluster, generated for a specific rollout if a maintenance window is set. */
		maintenanceSchedule: Option[Schema.MaintenanceSchedule] = None,
	  /** Optional. Subscription type of the cluster. */
		subscriptionType: Option[Schema.Cluster.SubscriptionTypeEnum] = None,
	  /** Output only. Metadata for free trial clusters */
		trialMetadata: Option[Schema.TrialMetadata] = None,
	  /** Optional. Input only. Immutable. Tag keys/values directly bound to this resource. For example: ``` "123/environment": "production", "123/costCenter": "marketing" ``` */
		tags: Option[Map[String, String]] = None
	)
	
	case class BackupSource(
	  /** Output only. The system-generated UID of the backup which was used to create this resource. The UID is generated when the backup is created, and it is retained until the backup is deleted. */
		backupUid: Option[String] = None,
	  /** Required. The name of the backup resource with the format: &#42; projects/{project}/locations/{region}/backups/{backup_id} */
		backupName: Option[String] = None
	)
	
	object MigrationSource {
		enum SourceTypeEnum extends Enum[SourceTypeEnum] { case MIGRATION_SOURCE_TYPE_UNSPECIFIED, DMS }
	}
	case class MigrationSource(
	  /** Output only. The host and port of the on-premises instance in host:port format */
		hostPort: Option[String] = None,
	  /** Output only. Place holder for the external source identifier(e.g DMS job name) that created the cluster. */
		referenceId: Option[String] = None,
	  /** Output only. Type of migration source. */
		sourceType: Option[Schema.MigrationSource.SourceTypeEnum] = None
	)
	
	case class NetworkConfig(
	  /** Optional. The resource link for the VPC network in which cluster resources are created and from which they are accessible via Private IP. The network must belong to the same project as the cluster. It is specified in the form: `projects/{project_number}/global/networks/{network_id}`. This is required to create a cluster. */
		network: Option[String] = None,
	  /** Optional. Name of the allocated IP range for the private IP AlloyDB cluster, for example: "google-managed-services-default". If set, the instance IPs for this cluster will be created in the allocated range. The range name must comply with RFC 1035. Specifically, the name must be 1-63 characters long and match the regular expression `[a-z]([-a-z0-9]&#42;[a-z0-9])?`. Field name is intended to be consistent with Cloud SQL. */
		allocatedIpRange: Option[String] = None
	)
	
	case class UserPassword(
	  /** The database username. */
		user: Option[String] = None,
	  /** The initial password for the user. */
		password: Option[String] = None
	)
	
	case class AutomatedBackupPolicy(
	  /** Weekly schedule for the Backup. */
		weeklySchedule: Option[Schema.WeeklySchedule] = None,
	  /** Time-based Backup retention policy. */
		timeBasedRetention: Option[Schema.TimeBasedRetention] = None,
	  /** Quantity-based Backup retention policy to retain recent backups. */
		quantityBasedRetention: Option[Schema.QuantityBasedRetention] = None,
	  /** Whether automated automated backups are enabled. If not set, defaults to true. */
		enabled: Option[Boolean] = None,
	  /** The length of the time window during which a backup can be taken. If a backup does not succeed within this time window, it will be canceled and considered failed. The backup window must be at least 5 minutes long. There is no upper bound on the window. If not set, it defaults to 1 hour. */
		backupWindow: Option[String] = None,
	  /** Optional. The encryption config can be specified to encrypt the backups with a customer-managed encryption key (CMEK). When this field is not specified, the backup will then use default encryption scheme to protect the user data. */
		encryptionConfig: Option[Schema.EncryptionConfig] = None,
	  /** The location where the backup will be stored. Currently, the only supported option is to store the backup in the same region as the cluster. If empty, defaults to the region of the cluster. */
		location: Option[String] = None,
	  /** Labels to apply to backups created using this configuration. */
		labels: Option[Map[String, String]] = None
	)
	
	object WeeklySchedule {
		enum DaysOfWeekEnum extends Enum[DaysOfWeekEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class WeeklySchedule(
	  /** The times during the day to start a backup. The start times are assumed to be in UTC and to be an exact hour (e.g., 04:00:00). If no start times are provided, a single fixed start time is chosen arbitrarily. */
		startTimes: Option[List[Schema.GoogleTypeTimeOfDay]] = None,
	  /** The days of the week to perform a backup. If this field is left empty, the default of every day of the week is used. */
		daysOfWeek: Option[List[Schema.WeeklySchedule.DaysOfWeekEnum]] = None
	)
	
	case class GoogleTypeTimeOfDay(
	  /** Hours of a day in 24 hour format. Must be greater than or equal to 0 and typically must be less than or equal to 23. An API may choose to allow the value "24:00:00" for scenarios like business closing time. */
		hours: Option[Int] = None,
	  /** Minutes of an hour. Must be greater than or equal to 0 and less than or equal to 59. */
		minutes: Option[Int] = None,
	  /** Seconds of a minute. Must be greater than or equal to 0 and typically must be less than or equal to 59. An API may allow the value 60 if it allows leap-seconds. */
		seconds: Option[Int] = None,
	  /** Fractions of seconds, in nanoseconds. Must be greater than or equal to 0 and less than or equal to 999,999,999. */
		nanos: Option[Int] = None
	)
	
	case class TimeBasedRetention(
	  /** The retention period. */
		retentionPeriod: Option[String] = None
	)
	
	case class QuantityBasedRetention(
	  /** The number of backups to retain. */
		count: Option[Int] = None
	)
	
	case class EncryptionConfig(
	  /** The fully-qualified resource name of the KMS key. Each Cloud KMS key is regionalized and has the following format: projects/[PROJECT]/locations/[REGION]/keyRings/[RING]/cryptoKeys/[KEY_NAME] */
		kmsKeyName: Option[String] = None
	)
	
	object SslConfig {
		enum SslModeEnum extends Enum[SslModeEnum] { case SSL_MODE_UNSPECIFIED, SSL_MODE_ALLOW, SSL_MODE_REQUIRE, SSL_MODE_VERIFY_CA, ALLOW_UNENCRYPTED_AND_ENCRYPTED, ENCRYPTED_ONLY }
		enum CaSourceEnum extends Enum[CaSourceEnum] { case CA_SOURCE_UNSPECIFIED, CA_SOURCE_MANAGED }
	}
	case class SslConfig(
	  /** Optional. SSL mode. Specifies client-server SSL/TLS connection behavior. */
		sslMode: Option[Schema.SslConfig.SslModeEnum] = None,
	  /** Optional. Certificate Authority (CA) source. Only CA_SOURCE_MANAGED is supported currently, and is the default value. */
		caSource: Option[Schema.SslConfig.CaSourceEnum] = None
	)
	
	object EncryptionInfo {
		enum EncryptionTypeEnum extends Enum[EncryptionTypeEnum] { case TYPE_UNSPECIFIED, GOOGLE_DEFAULT_ENCRYPTION, CUSTOMER_MANAGED_ENCRYPTION }
	}
	case class EncryptionInfo(
	  /** Output only. Type of encryption. */
		encryptionType: Option[Schema.EncryptionInfo.EncryptionTypeEnum] = None,
	  /** Output only. Cloud KMS key versions that are being used to protect the database or the backup. */
		kmsKeyVersions: Option[List[String]] = None
	)
	
	case class ContinuousBackupConfig(
	  /** Whether ContinuousBackup is enabled. */
		enabled: Option[Boolean] = None,
	  /** The number of days that are eligible to restore from using PITR. To support the entire recovery window, backups and logs are retained for one day more than the recovery window. If not set, defaults to 14 days. */
		recoveryWindowDays: Option[Int] = None,
	  /** The encryption config can be specified to encrypt the backups with a customer-managed encryption key (CMEK). When this field is not specified, the backup will then use default encryption scheme to protect the user data. */
		encryptionConfig: Option[Schema.EncryptionConfig] = None
	)
	
	object ContinuousBackupInfo {
		enum ScheduleEnum extends Enum[ScheduleEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class ContinuousBackupInfo(
	  /** Output only. The encryption information for the WALs and backups required for ContinuousBackup. */
		encryptionInfo: Option[Schema.EncryptionInfo] = None,
	  /** Output only. When ContinuousBackup was most recently enabled. Set to null if ContinuousBackup is not enabled. */
		enabledTime: Option[String] = None,
	  /** Output only. Days of the week on which a continuous backup is taken. Output only field. Ignored if passed into the request. */
		schedule: Option[List[Schema.ContinuousBackupInfo.ScheduleEnum]] = None,
	  /** Output only. The earliest restorable time that can be restored to. Output only field. */
		earliestRestorableTime: Option[String] = None
	)
	
	case class SecondaryConfig(
	  /** The name of the primary cluster name with the format: &#42; projects/{project}/locations/{region}/clusters/{cluster_id} */
		primaryClusterName: Option[String] = None
	)
	
	case class PrimaryConfig(
	  /** Output only. Names of the clusters that are replicating from this cluster. */
		secondaryClusterNames: Option[List[String]] = None
	)
	
	case class PscConfig(
	  /** Optional. Create an instance that allows connections from Private Service Connect endpoints to the instance. */
		pscEnabled: Option[Boolean] = None
	)
	
	case class MaintenanceUpdatePolicy(
	  /** Preferred windows to perform maintenance. Currently limited to 1. */
		maintenanceWindows: Option[List[Schema.MaintenanceWindow]] = None
	)
	
	object MaintenanceWindow {
		enum DayEnum extends Enum[DayEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class MaintenanceWindow(
	  /** Preferred day of the week for maintenance, e.g. MONDAY, TUESDAY, etc. */
		day: Option[Schema.MaintenanceWindow.DayEnum] = None,
	  /** Preferred time to start the maintenance operation on the specified day. Maintenance will start within 1 hour of this time. */
		startTime: Option[Schema.GoogleTypeTimeOfDay] = None
	)
	
	case class MaintenanceSchedule(
	  /** Output only. The scheduled start time for the maintenance. */
		startTime: Option[String] = None
	)
	
	case class TrialMetadata(
	  /** start time of the trial cluster. */
		startTime: Option[String] = None,
	  /** End time of the trial cluster. */
		endTime: Option[String] = None,
	  /** Upgrade time of trial cluster to Standard cluster. */
		upgradeTime: Option[String] = None,
	  /** grace end time of the cluster. */
		graceEndTime: Option[String] = None
	)
	
	case class ExportClusterRequest(
	  /** Required. Option to export data to cloud storage. */
		gcsDestination: Option[Schema.GcsDestination] = None,
	  /** Options for exporting data in CSV format. Required field to be set for CSV file type. */
		csvExportOptions: Option[Schema.CsvExportOptions] = None,
	  /** Required. Name of the database where the query will be executed. Note - Value provided should be the same as expected from `SELECT current_database();` and NOT as a resource reference. */
		database: Option[String] = None
	)
	
	case class GcsDestination(
	  /** Required. The path to the file in Google Cloud Storage where the export will be stored. The URI is in the form `gs://bucketName/fileName`. If the file already exists, the request succeeds, but the operation fails. */
		uri: Option[String] = None
	)
	
	case class CsvExportOptions(
	  /** Required. The select_query used to extract the data. */
		selectQuery: Option[String] = None
	)
	
	object UpgradeClusterRequest {
		enum VersionEnum extends Enum[VersionEnum] { case DATABASE_VERSION_UNSPECIFIED, POSTGRES_13, POSTGRES_14, POSTGRES_15, POSTGRES_16 }
	}
	case class UpgradeClusterRequest(
	  /** Required. The version the cluster is going to be upgraded to. */
		version: Option[Schema.UpgradeClusterRequest.VersionEnum] = None,
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None,
	  /** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
		validateOnly: Option[Boolean] = None,
	  /** Optional. The current etag of the Cluster. If an etag is provided and does not match the current etag of the Cluster, upgrade will be blocked and an ABORTED error will be returned. */
		etag: Option[String] = None
	)
	
	case class PromoteClusterRequest(
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None,
	  /** Optional. The current etag of the Cluster. If an etag is provided and does not match the current etag of the Cluster, deletion will be blocked and an ABORTED error will be returned. */
		etag: Option[String] = None,
	  /** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
		validateOnly: Option[Boolean] = None
	)
	
	case class SwitchoverClusterRequest(
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None,
	  /** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
		validateOnly: Option[Boolean] = None
	)
	
	case class RestoreClusterRequest(
	  /** Backup source. */
		backupSource: Option[Schema.BackupSource] = None,
	  /** ContinuousBackup source. Continuous backup needs to be enabled in the source cluster for this operation to succeed. */
		continuousBackupSource: Option[Schema.ContinuousBackupSource] = None,
	  /** Required. ID of the requesting object. */
		clusterId: Option[String] = None,
	  /** Required. The resource being created */
		cluster: Option[Schema.Cluster] = None,
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None,
	  /** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
		validateOnly: Option[Boolean] = None
	)
	
	case class ContinuousBackupSource(
	  /** Required. The source cluster from which to restore. This cluster must have continuous backup enabled for this operation to succeed. For the required format, see the comment on the Cluster.name field. */
		cluster: Option[String] = None,
	  /** Required. The point in time to restore to. */
		pointInTime: Option[String] = None
	)
	
	case class ListInstancesResponse(
	  /** The list of Instance */
		instances: Option[List[Schema.Instance]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Instance {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, READY, STOPPED, CREATING, DELETING, MAINTENANCE, FAILED, BOOTSTRAPPING, PROMOTING }
		enum InstanceTypeEnum extends Enum[InstanceTypeEnum] { case INSTANCE_TYPE_UNSPECIFIED, PRIMARY, READ_POOL, SECONDARY }
		enum AvailabilityTypeEnum extends Enum[AvailabilityTypeEnum] { case AVAILABILITY_TYPE_UNSPECIFIED, ZONAL, REGIONAL }
	}
	case class Instance(
	  /** Output only. The name of the instance resource with the format: &#42; projects/{project}/locations/{region}/clusters/{cluster_id}/instances/{instance_id} where the cluster and instance ID segments should satisfy the regex expression `[a-z]([a-z0-9-]{0,61}[a-z0-9])?`, e.g. 1-63 characters of lowercase letters, numbers, and dashes, starting with a letter, and ending with a letter or number. For more details see https://google.aip.dev/122. The prefix of the instance resource name is the name of the parent resource: &#42; projects/{project}/locations/{region}/clusters/{cluster_id} */
		name: Option[String] = None,
	  /** User-settable and human-readable display name for the Instance. */
		displayName: Option[String] = None,
	  /** Output only. The system-generated UID of the resource. The UID is assigned when the resource is created, and it is retained until it is deleted. */
		uid: Option[String] = None,
	  /** Output only. Create time stamp */
		createTime: Option[String] = None,
	  /** Output only. Update time stamp */
		updateTime: Option[String] = None,
	  /** Output only. Delete time stamp */
		deleteTime: Option[String] = None,
	  /** Labels as key value pairs */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The current serving state of the instance. */
		state: Option[Schema.Instance.StateEnum] = None,
	  /** Required. The type of the instance. Specified at creation time. */
		instanceType: Option[Schema.Instance.InstanceTypeEnum] = None,
	  /** Configurations for the machines that host the underlying database engine. */
		machineConfig: Option[Schema.MachineConfig] = None,
	  /** Availability type of an Instance. If empty, defaults to REGIONAL for primary instances. For read pools, availability_type is always UNSPECIFIED. Instances in the read pools are evenly distributed across available zones within the region (i.e. read pools with more than one node will have a node in at least two zones). */
		availabilityType: Option[Schema.Instance.AvailabilityTypeEnum] = None,
	  /** The Compute Engine zone that the instance should serve from, per https://cloud.google.com/compute/docs/regions-zones This can ONLY be specified for ZONAL instances. If present for a REGIONAL instance, an error will be thrown. If this is absent for a ZONAL instance, instance is created in a random zone with available capacity. */
		gceZone: Option[String] = None,
	  /** Database flags. Set at the instance level. They are copied from the primary instance on secondary instance creation. Flags that have restrictions default to the value at primary instance on read instances during creation. Read instances can set new flags or override existing flags that are relevant for reads, for example, for enabling columnar cache on a read instance. Flags set on read instance might or might not be present on the primary instance. This is a list of "key": "value" pairs. "key": The name of the flag. These flags are passed at instance setup time, so include both server options and system variables for Postgres. Flags are specified with underscores, not hyphens. "value": The value of the flag. Booleans are set to &#42;&#42;on&#42;&#42; for true and &#42;&#42;off&#42;&#42; for false. This field must be omitted if the flag doesn't take a value. */
		databaseFlags: Option[Map[String, String]] = None,
	  /** Output only. This is set for the read-write VM of the PRIMARY instance only. */
		writableNode: Option[Schema.Node] = None,
	  /** Output only. List of available read-only VMs in this instance, including the standby for a PRIMARY instance. */
		nodes: Option[List[Schema.Node]] = None,
	  /** Configuration for query insights. */
		queryInsightsConfig: Option[Schema.QueryInsightsInstanceConfig] = None,
	  /** Read pool instance configuration. This is required if the value of instanceType is READ_POOL. */
		readPoolConfig: Option[Schema.ReadPoolConfig] = None,
	  /** Output only. The IP address for the Instance. This is the connection endpoint for an end-user application. */
		ipAddress: Option[String] = None,
	  /** Output only. The public IP addresses for the Instance. This is available ONLY when enable_public_ip is set. This is the connection endpoint for an end-user application. */
		publicIpAddress: Option[String] = None,
	  /** Output only. Reconciling (https://google.aip.dev/128#reconciliation). Set to true if the current state of Instance does not match the user's intended state, and the service is actively updating the resource to reconcile them. This can happen due to user-triggered updates or system actions like failover or maintenance. */
		reconciling: Option[Boolean] = None,
	  /** For Resource freshness validation (https://google.aip.dev/154) */
		etag: Option[String] = None,
	  /** Annotations to allow client tools to store small amount of arbitrary data. This is distinct from labels. https://google.aip.dev/128 */
		annotations: Option[Map[String, String]] = None,
	  /** Optional. Client connection specific configurations */
		clientConnectionConfig: Option[Schema.ClientConnectionConfig] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Optional. The configuration for Private Service Connect (PSC) for the instance. */
		pscInstanceConfig: Option[Schema.PscInstanceConfig] = None,
	  /** Optional. Instance-level network configuration. */
		networkConfig: Option[Schema.InstanceNetworkConfig] = None,
	  /** Output only. All outbound public IP addresses configured for the instance. */
		outboundPublicIpAddresses: Option[List[String]] = None
	)
	
	case class MachineConfig(
	  /** The number of CPU's in the VM instance. */
		cpuCount: Option[Int] = None
	)
	
	case class Node(
	  /** The Compute Engine zone of the VM e.g. "us-central1-b". */
		zoneId: Option[String] = None,
	  /** The identifier of the VM e.g. "test-read-0601-407e52be-ms3l". */
		id: Option[String] = None,
	  /** The private IP address of the VM e.g. "10.57.0.34". */
		ip: Option[String] = None,
	  /** Determined by state of the compute VM and postgres-service health. Compute VM state can have values listed in https://cloud.google.com/compute/docs/instances/instance-life-cycle and postgres-service health can have values: HEALTHY and UNHEALTHY. */
		state: Option[String] = None
	)
	
	case class QueryInsightsInstanceConfig(
	  /** Record application tags for an instance. This flag is turned "on" by default. */
		recordApplicationTags: Option[Boolean] = None,
	  /** Record client address for an instance. Client address is PII information. This flag is turned "on" by default. */
		recordClientAddress: Option[Boolean] = None,
	  /** Query string length. The default value is 1024. Any integer between 256 and 4500 is considered valid. */
		queryStringLength: Option[Int] = None,
	  /** Number of query execution plans captured by Insights per minute for all queries combined. The default value is 5. Any integer between 0 and 20 is considered valid. */
		queryPlansPerMinute: Option[Int] = None
	)
	
	case class ReadPoolConfig(
	  /** Read capacity, i.e. number of nodes in a read pool instance. */
		nodeCount: Option[Int] = None
	)
	
	case class ClientConnectionConfig(
	  /** Optional. Configuration to enforce connectors only (ex: AuthProxy) connections to the database. */
		requireConnectors: Option[Boolean] = None,
	  /** Optional. SSL configuration option for this instance. */
		sslConfig: Option[Schema.SslConfig] = None
	)
	
	case class PscInstanceConfig(
	  /** Output only. The service attachment created when Private Service Connect (PSC) is enabled for the instance. The name of the resource will be in the format of `projects//regions//serviceAttachments/` */
		serviceAttachmentLink: Option[String] = None,
	  /** Optional. List of consumer projects that are allowed to create PSC endpoints to service-attachments to this instance. */
		allowedConsumerProjects: Option[List[String]] = None,
	  /** Output only. The DNS name of the instance for PSC connectivity. Name convention: ...alloydb-psc.goog */
		pscDnsName: Option[String] = None
	)
	
	case class InstanceNetworkConfig(
	  /** Optional. A list of external network authorized to access this instance. */
		authorizedExternalNetworks: Option[List[Schema.AuthorizedNetwork]] = None,
	  /** Optional. Enabling public ip for the instance. */
		enablePublicIp: Option[Boolean] = None,
	  /** Optional. Enabling an outbound public IP address to support a database server sending requests out into the internet. */
		enableOutboundPublicIp: Option[Boolean] = None
	)
	
	case class AuthorizedNetwork(
	  /** CIDR range for one authorzied network of the instance. */
		cidrRange: Option[String] = None
	)
	
	case class FailoverInstanceRequest(
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None,
	  /** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
		validateOnly: Option[Boolean] = None
	)
	
	object InjectFaultRequest {
		enum FaultTypeEnum extends Enum[FaultTypeEnum] { case FAULT_TYPE_UNSPECIFIED, STOP_VM }
	}
	case class InjectFaultRequest(
	  /** Required. The type of fault to be injected in an instance. */
		faultType: Option[Schema.InjectFaultRequest.FaultTypeEnum] = None,
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None,
	  /** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
		validateOnly: Option[Boolean] = None
	)
	
	case class RestartInstanceRequest(
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None,
	  /** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
		validateOnly: Option[Boolean] = None,
	  /** Optional. Full name of the nodes as obtained from INSTANCE_VIEW_FULL to restart upon. Applicable only to read instances. */
		nodeIds: Option[List[String]] = None
	)
	
	case class ListBackupsResponse(
	  /** The list of Backup */
		backups: Option[List[Schema.Backup]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Backup {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, READY, CREATING, FAILED, DELETING }
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, ON_DEMAND, AUTOMATED, CONTINUOUS }
		enum DatabaseVersionEnum extends Enum[DatabaseVersionEnum] { case DATABASE_VERSION_UNSPECIFIED, POSTGRES_13, POSTGRES_14, POSTGRES_15, POSTGRES_16 }
	}
	case class Backup(
	  /** Output only. The name of the backup resource with the format: &#42; projects/{project}/locations/{region}/backups/{backup_id} where the cluster and backup ID segments should satisfy the regex expression `[a-z]([a-z0-9-]{0,61}[a-z0-9])?`, e.g. 1-63 characters of lowercase letters, numbers, and dashes, starting with a letter, and ending with a letter or number. For more details see https://google.aip.dev/122. The prefix of the backup resource name is the name of the parent resource: &#42; projects/{project}/locations/{region} */
		name: Option[String] = None,
	  /** User-settable and human-readable display name for the Backup. */
		displayName: Option[String] = None,
	  /** Output only. The system-generated UID of the resource. The UID is assigned when the resource is created, and it is retained until it is deleted. */
		uid: Option[String] = None,
	  /** Output only. Create time stamp */
		createTime: Option[String] = None,
	  /** Output only. Update time stamp */
		updateTime: Option[String] = None,
	  /** Output only. Delete time stamp */
		deleteTime: Option[String] = None,
	  /** Labels as key value pairs */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The current state of the backup. */
		state: Option[Schema.Backup.StateEnum] = None,
	  /** The backup type, which suggests the trigger for the backup. */
		`type`: Option[Schema.Backup.TypeEnum] = None,
	  /** User-provided description of the backup. */
		description: Option[String] = None,
	  /** Output only. The system-generated UID of the cluster which was used to create this resource. */
		clusterUid: Option[String] = None,
	  /** Required. The full resource name of the backup source cluster (e.g., projects/{project}/locations/{region}/clusters/{cluster_id}). */
		clusterName: Option[String] = None,
	  /** Output only. Reconciling (https://google.aip.dev/128#reconciliation), if true, indicates that the service is actively updating the resource. This can happen due to user-triggered updates or system actions like failover or maintenance. */
		reconciling: Option[Boolean] = None,
	  /** Optional. The encryption config can be specified to encrypt the backup with a customer-managed encryption key (CMEK). When this field is not specified, the backup will then use default encryption scheme to protect the user data. */
		encryptionConfig: Option[Schema.EncryptionConfig] = None,
	  /** Output only. The encryption information for the backup. */
		encryptionInfo: Option[Schema.EncryptionInfo] = None,
	  /** For Resource freshness validation (https://google.aip.dev/154) */
		etag: Option[String] = None,
	  /** Annotations to allow client tools to store small amount of arbitrary data. This is distinct from labels. https://google.aip.dev/128 */
		annotations: Option[Map[String, String]] = None,
	  /** Output only. The size of the backup in bytes. */
		sizeBytes: Option[String] = None,
	  /** Output only. The time at which after the backup is eligible to be garbage collected. It is the duration specified by the backup's retention policy, added to the backup's create_time. */
		expiryTime: Option[String] = None,
	  /** Output only. The QuantityBasedExpiry of the backup, specified by the backup's retention policy. Once the expiry quantity is over retention, the backup is eligible to be garbage collected. */
		expiryQuantity: Option[Schema.QuantityBasedExpiry] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Output only. The database engine major version of the cluster this backup was created from. Any restored cluster created from this backup will have the same database version. */
		databaseVersion: Option[Schema.Backup.DatabaseVersionEnum] = None,
	  /** Optional. Input only. Immutable. Tag keys/values directly bound to this resource. For example: ``` "123/environment": "production", "123/costCenter": "marketing" ``` */
		tags: Option[Map[String, String]] = None
	)
	
	case class QuantityBasedExpiry(
	  /** Output only. The backup's position among its backups with the same source cluster and type, by descending chronological order create time(i.e. newest first). */
		retentionCount: Option[Int] = None,
	  /** Output only. The length of the quantity-based queue, specified by the backup's retention policy. */
		totalRetentionCount: Option[Int] = None
	)
	
	case class ListSupportedDatabaseFlagsResponse(
	  /** The list of SupportedDatabaseFlags. */
		supportedDatabaseFlags: Option[List[Schema.SupportedDatabaseFlag]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None
	)
	
	object SupportedDatabaseFlag {
		enum ValueTypeEnum extends Enum[ValueTypeEnum] { case VALUE_TYPE_UNSPECIFIED, STRING, INTEGER, FLOAT, NONE }
		enum SupportedDbVersionsEnum extends Enum[SupportedDbVersionsEnum] { case DATABASE_VERSION_UNSPECIFIED, POSTGRES_13, POSTGRES_14, POSTGRES_15, POSTGRES_16 }
	}
	case class SupportedDatabaseFlag(
	  /** Restriction on STRING type value. */
		stringRestrictions: Option[Schema.StringRestrictions] = None,
	  /** Restriction on INTEGER type value. */
		integerRestrictions: Option[Schema.IntegerRestrictions] = None,
	  /** The name of the flag resource, following Google Cloud conventions, e.g.: &#42; projects/{project}/locations/{location}/flags/{flag} This field currently has no semantic meaning. */
		name: Option[String] = None,
	  /** The name of the database flag, e.g. "max_allowed_packets". The is a possibly key for the Instance.database_flags map field. */
		flagName: Option[String] = None,
		valueType: Option[Schema.SupportedDatabaseFlag.ValueTypeEnum] = None,
	  /** Whether the database flag accepts multiple values. If true, a comma-separated list of stringified values may be specified. */
		acceptsMultipleValues: Option[Boolean] = None,
	  /** Major database engine versions for which this flag is supported. */
		supportedDbVersions: Option[List[Schema.SupportedDatabaseFlag.SupportedDbVersionsEnum]] = None,
	  /** Whether setting or updating this flag on an Instance requires a database restart. If a flag that requires database restart is set, the backend will automatically restart the database (making sure to satisfy any availability SLO's). */
		requiresDbRestart: Option[Boolean] = None
	)
	
	case class StringRestrictions(
	  /** The list of allowed values, if bounded. This field will be empty if there is a unbounded number of allowed values. */
		allowedValues: Option[List[String]] = None
	)
	
	case class IntegerRestrictions(
	  /** The minimum value that can be specified, if applicable. */
		minValue: Option[String] = None,
	  /** The maximum value that can be specified, if applicable. */
		maxValue: Option[String] = None
	)
	
	case class ConnectionInfo(
	  /** The name of the ConnectionInfo singleton resource, e.g.: projects/{project}/locations/{location}/clusters/&#42;/instances/&#42;/connectionInfo This field currently has no semantic meaning. */
		name: Option[String] = None,
	  /** Output only. The private network IP address for the Instance. This is the default IP for the instance and is always created (even if enable_public_ip is set). This is the connection endpoint for an end-user application. */
		ipAddress: Option[String] = None,
	  /** Output only. The public IP addresses for the Instance. This is available ONLY when enable_public_ip is set. This is the connection endpoint for an end-user application. */
		publicIpAddress: Option[String] = None,
	  /** Output only. The unique ID of the Instance. */
		instanceUid: Option[String] = None
	)
	
	case class ListUsersResponse(
	  /** The list of User */
		users: Option[List[Schema.User]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object User {
		enum UserTypeEnum extends Enum[UserTypeEnum] { case USER_TYPE_UNSPECIFIED, ALLOYDB_BUILT_IN, ALLOYDB_IAM_USER }
	}
	case class User(
	  /** Output only. Name of the resource in the form of projects/{project}/locations/{location}/cluster/{cluster}/users/{user}. */
		name: Option[String] = None,
	  /** Input only. Password for the user. */
		password: Option[String] = None,
	  /** Optional. List of database roles this user has. The database role strings are subject to the PostgreSQL naming conventions. */
		databaseRoles: Option[List[String]] = None,
	  /** Optional. Type of this user. */
		userType: Option[Schema.User.UserTypeEnum] = None,
	  /** Input only. If the user already exists and it has additional roles, keep them granted. */
		keepExtraRoles: Option[Boolean] = None
	)
	
	case class GoogleCloudLocationListLocationsResponse(
	  /** A list of locations that matches the specified filter in the request. */
		locations: Option[List[Schema.GoogleCloudLocationLocation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudLocationLocation(
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
	
	case class ExportClusterResponse(
	  /** Required. Option to export data to cloud storage. */
		gcsDestination: Option[Schema.GcsDestination] = None
	)
	
	object UpgradeClusterResponse {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNSPECIFIED, NOT_STARTED, IN_PROGRESS, SUCCESS, FAILED, PARTIAL_SUCCESS, CANCEL_IN_PROGRESS, CANCELLED }
	}
	case class UpgradeClusterResponse(
	  /** Status of upgrade operation. */
		status: Option[Schema.UpgradeClusterResponse.StatusEnum] = None,
	  /** A user friendly message summarising the upgrade operation details and the next steps for the user if there is any. */
		message: Option[String] = None,
	  /** Array of upgrade details for the current cluster and all the secondary clusters associated with this cluster. */
		clusterUpgradeDetails: Option[List[Schema.ClusterUpgradeDetails]] = None
	)
	
	object ClusterUpgradeDetails {
		enum UpgradeStatusEnum extends Enum[UpgradeStatusEnum] { case STATUS_UNSPECIFIED, NOT_STARTED, IN_PROGRESS, SUCCESS, FAILED, PARTIAL_SUCCESS, CANCEL_IN_PROGRESS, CANCELLED }
		enum ClusterTypeEnum extends Enum[ClusterTypeEnum] { case CLUSTER_TYPE_UNSPECIFIED, PRIMARY, SECONDARY }
		enum DatabaseVersionEnum extends Enum[DatabaseVersionEnum] { case DATABASE_VERSION_UNSPECIFIED, POSTGRES_13, POSTGRES_14, POSTGRES_15, POSTGRES_16 }
	}
	case class ClusterUpgradeDetails(
	  /** Normalized name of the cluster */
		name: Option[String] = None,
	  /** Upgrade status of the cluster. */
		upgradeStatus: Option[Schema.ClusterUpgradeDetails.UpgradeStatusEnum] = None,
	  /** Cluster type which can either be primary or secondary. */
		clusterType: Option[Schema.ClusterUpgradeDetails.ClusterTypeEnum] = None,
	  /** Database version of the cluster after the upgrade operation. This will be the target version if the upgrade was successful otherwise it remains the same as that before the upgrade operation. */
		databaseVersion: Option[Schema.ClusterUpgradeDetails.DatabaseVersionEnum] = None,
	  /** Array containing stage info associated with this cluster. */
		stageInfo: Option[List[Schema.StageInfo]] = None,
	  /** Upgrade details of the instances directly associated with this cluster. */
		instanceUpgradeDetails: Option[List[Schema.InstanceUpgradeDetails]] = None
	)
	
	object StageInfo {
		enum StageEnum extends Enum[StageEnum] { case STAGE_UNSPECIFIED, ALLOYDB_PRECHECK, PG_UPGRADE_CHECK, PREPARE_FOR_UPGRADE, PRIMARY_INSTANCE_UPGRADE, READ_POOL_INSTANCES_UPGRADE, ROLLBACK, CLEANUP }
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNSPECIFIED, NOT_STARTED, IN_PROGRESS, SUCCESS, FAILED, PARTIAL_SUCCESS, CANCEL_IN_PROGRESS, CANCELLED }
	}
	case class StageInfo(
	  /** The stage. */
		stage: Option[Schema.StageInfo.StageEnum] = None,
	  /** Status of the stage. */
		status: Option[Schema.StageInfo.StatusEnum] = None,
	  /** logs_url is the URL for the logs associated with a stage if that stage has logs. Right now, only three stages have logs: ALLOYDB_PRECHECK, PG_UPGRADE_CHECK, PRIMARY_INSTANCE_UPGRADE. */
		logsUrl: Option[String] = None
	)
	
	object InstanceUpgradeDetails {
		enum UpgradeStatusEnum extends Enum[UpgradeStatusEnum] { case STATUS_UNSPECIFIED, NOT_STARTED, IN_PROGRESS, SUCCESS, FAILED, PARTIAL_SUCCESS, CANCEL_IN_PROGRESS, CANCELLED }
		enum InstanceTypeEnum extends Enum[InstanceTypeEnum] { case INSTANCE_TYPE_UNSPECIFIED, PRIMARY, READ_POOL, SECONDARY }
	}
	case class InstanceUpgradeDetails(
	  /** Normalized name of the instance. */
		name: Option[String] = None,
	  /** Upgrade status of the instance. */
		upgradeStatus: Option[Schema.InstanceUpgradeDetails.UpgradeStatusEnum] = None,
	  /** Instance type. */
		instanceType: Option[Schema.InstanceUpgradeDetails.InstanceTypeEnum] = None
	)
	
	object StorageDatabasecenterPartnerapiV1mainDatabaseResourceFeed {
		enum FeedTypeEnum extends Enum[FeedTypeEnum] { case FEEDTYPE_UNSPECIFIED, RESOURCE_METADATA, OBSERVABILITY_DATA, SECURITY_FINDING_DATA, RECOMMENDATION_SIGNAL_DATA }
	}
	case class StorageDatabasecenterPartnerapiV1mainDatabaseResourceFeed(
	  /** Primary key associated with the Resource. resource_id is available in individual feed level as well. */
		resourceId: Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceId] = None,
	  /** Required. Timestamp when feed is generated. */
		feedTimestamp: Option[String] = None,
	  /** Required. Type feed to be ingested into condor */
		feedType: Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceFeed.FeedTypeEnum] = None,
		resourceMetadata: Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata] = None,
		resourceHealthSignalData: Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData] = None,
		recommendationSignalData: Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceRecommendationSignalData] = None,
		observabilityMetricData: Option[Schema.StorageDatabasecenterPartnerapiV1mainObservabilityMetricData] = None
	)
	
	object StorageDatabasecenterPartnerapiV1mainDatabaseResourceId {
		enum ProviderEnum extends Enum[ProviderEnum] { case PROVIDER_UNSPECIFIED, GCP, AWS, AZURE, ONPREM, SELFMANAGED, PROVIDER_OTHER }
	}
	case class StorageDatabasecenterPartnerapiV1mainDatabaseResourceId(
	  /** Required. Cloud provider name. Ex: GCP/AWS/Azure/OnPrem/SelfManaged */
		provider: Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceId.ProviderEnum] = None,
	  /** Optional. Needs to be used only when the provider is PROVIDER_OTHER. */
		providerDescription: Option[String] = None,
	  /** Required. A service-local token that distinguishes this resource from other resources within the same service. */
		uniqueId: Option[String] = None,
	  /** Required. The type of resource this ID is identifying. Ex redis.googleapis.com/Instance, redis.googleapis.com/Cluster, alloydb.googleapis.com/Cluster, alloydb.googleapis.com/Instance, spanner.googleapis.com/Instance, spanner.googleapis.com/Database, firestore.googleapis.com/Database, sqladmin.googleapis.com/Instance, bigtableadmin.googleapis.com/Cluster, bigtableadmin.googleapis.com/Instance REQUIRED Please refer go/condor-common-datamodel */
		resourceType: Option[String] = None
	)
	
	object StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata {
		enum ExpectedStateEnum extends Enum[ExpectedStateEnum] { case STATE_UNSPECIFIED, HEALTHY, UNHEALTHY, SUSPENDED, DELETED, STATE_OTHER }
		enum CurrentStateEnum extends Enum[CurrentStateEnum] { case STATE_UNSPECIFIED, HEALTHY, UNHEALTHY, SUSPENDED, DELETED, STATE_OTHER }
		enum InstanceTypeEnum extends Enum[InstanceTypeEnum] { case INSTANCE_TYPE_UNSPECIFIED, SUB_RESOURCE_TYPE_UNSPECIFIED, PRIMARY, SECONDARY, READ_REPLICA, OTHER, SUB_RESOURCE_TYPE_PRIMARY, SUB_RESOURCE_TYPE_SECONDARY, SUB_RESOURCE_TYPE_READ_REPLICA, SUB_RESOURCE_TYPE_OTHER }
		enum EditionEnum extends Enum[EditionEnum] { case EDITION_UNSPECIFIED, EDITION_ENTERPRISE, EDITION_ENTERPRISE_PLUS }
	}
	case class StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata(
	  /** Required. Unique identifier for a Database resource */
		id: Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceId] = None,
	  /** Required. Different from DatabaseResourceId.unique_id, a resource name can be reused over time. That is, after a resource named "ABC" is deleted, the name "ABC" can be used to to create a new resource within the same source. Resource name to follow CAIS resource_name format as noted here go/condor-common-datamodel */
		resourceName: Option[String] = None,
	  /** Identifier for this resource's immediate parent/primary resource if the current resource is a replica or derived form of another Database resource. Else it would be NULL. REQUIRED if the immediate parent exists when first time resource is getting ingested, otherwise optional. */
		primaryResourceId: Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceId] = None,
	  /** Primary resource location. REQUIRED if the immediate parent exists when first time resource is getting ingested, otherwise optional. */
		primaryResourceLocation: Option[String] = None,
	  /** Closest parent Cloud Resource Manager container of this resource. It must be resource name of a Cloud Resource Manager project with the format of "/", such as "projects/123". For GCP provided resources, number should be project number. */
		resourceContainer: Option[String] = None,
	  /** The resource location. REQUIRED */
		location: Option[String] = None,
	  /** The creation time of the resource, i.e. the time when resource is created and recorded in partner service. */
		creationTime: Option[String] = None,
	  /** The time at which the resource was updated and recorded at partner service. */
		updationTime: Option[String] = None,
	  /** The state that the instance is expected to be in. For example, an instance state can transition to UNHEALTHY due to wrong patch update, while the expected state will remain at the HEALTHY. */
		expectedState: Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata.ExpectedStateEnum] = None,
	  /** Current state of the instance. */
		currentState: Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata.CurrentStateEnum] = None,
	  /** The type of the instance. Specified at creation time. */
		instanceType: Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata.InstanceTypeEnum] = None,
	  /** The product this resource represents. */
		product: Option[Schema.StorageDatabasecenterProtoCommonProduct] = None,
	  /** Availability configuration for this instance */
		availabilityConfiguration: Option[Schema.StorageDatabasecenterPartnerapiV1mainAvailabilityConfiguration] = None,
	  /** Backup configuration for this instance */
		backupConfiguration: Option[Schema.StorageDatabasecenterPartnerapiV1mainBackupConfiguration] = None,
	  /** Latest backup run information for this instance */
		backupRun: Option[Schema.StorageDatabasecenterPartnerapiV1mainBackupRun] = None,
	  /** Any custom metadata associated with the resource */
		customMetadata: Option[Schema.StorageDatabasecenterPartnerapiV1mainCustomMetadataData] = None,
	  /** Entitlements associated with the resource */
		entitlements: Option[List[Schema.StorageDatabasecenterPartnerapiV1mainEntitlement]] = None,
	  /** User-provided labels associated with the resource */
		userLabelSet: Option[Schema.StorageDatabasecenterPartnerapiV1mainUserLabels] = None,
	  /** Machine configuration for this resource. */
		machineConfiguration: Option[Schema.StorageDatabasecenterPartnerapiV1mainMachineConfiguration] = None,
	  /** Optional. Tags associated with this resources. */
		tagsSet: Option[Schema.StorageDatabasecenterPartnerapiV1mainTags] = None,
	  /** Optional. Edition represents whether the instance is ENTERPRISE or ENTERPRISE_PLUS. This information is core to Cloud SQL only and is used to identify the edition of the instance. */
		edition: Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata.EditionEnum] = None
	)
	
	object StorageDatabasecenterProtoCommonProduct {
		enum TypeEnum extends Enum[TypeEnum] { case PRODUCT_TYPE_UNSPECIFIED, PRODUCT_TYPE_CLOUD_SQL, CLOUD_SQL, PRODUCT_TYPE_ALLOYDB, ALLOYDB, PRODUCT_TYPE_SPANNER, PRODUCT_TYPE_ON_PREM, ON_PREM, PRODUCT_TYPE_MEMORYSTORE, PRODUCT_TYPE_BIGTABLE, PRODUCT_TYPE_OTHER, PRODUCT_TYPE_FIRESTORE }
		enum EngineEnum extends Enum[EngineEnum] { case ENGINE_UNSPECIFIED, ENGINE_MYSQL, MYSQL, ENGINE_POSTGRES, POSTGRES, ENGINE_SQL_SERVER, SQL_SERVER, ENGINE_NATIVE, NATIVE, ENGINE_CLOUD_SPANNER_WITH_POSTGRES_DIALECT, ENGINE_CLOUD_SPANNER_WITH_GOOGLESQL_DIALECT, ENGINE_MEMORYSTORE_FOR_REDIS, ENGINE_MEMORYSTORE_FOR_REDIS_CLUSTER, ENGINE_OTHER, ENGINE_FIRESTORE_WITH_NATIVE_MODE, ENGINE_FIRESTORE_WITH_DATASTORE_MODE }
	}
	case class StorageDatabasecenterProtoCommonProduct(
	  /** Type of specific database product. It could be CloudSQL, AlloyDB etc.. */
		`type`: Option[Schema.StorageDatabasecenterProtoCommonProduct.TypeEnum] = None,
	  /** The specific engine that the underlying database is running. */
		engine: Option[Schema.StorageDatabasecenterProtoCommonProduct.EngineEnum] = None,
	  /** Version of the underlying database engine. Example values: For MySQL, it could be "8.0", "5.7" etc.. For Postgres, it could be "14", "15" etc.. */
		version: Option[String] = None
	)
	
	object StorageDatabasecenterPartnerapiV1mainAvailabilityConfiguration {
		enum AvailabilityTypeEnum extends Enum[AvailabilityTypeEnum] { case AVAILABILITY_TYPE_UNSPECIFIED, ZONAL, REGIONAL, MULTI_REGIONAL, AVAILABILITY_TYPE_OTHER }
	}
	case class StorageDatabasecenterPartnerapiV1mainAvailabilityConfiguration(
	  /** Availability type. Potential values: &#42; `ZONAL`: The instance serves data from only one zone. Outages in that zone affect data accessibility. &#42; `REGIONAL`: The instance can serve data from more than one zone in a region (it is highly available). */
		availabilityType: Option[Schema.StorageDatabasecenterPartnerapiV1mainAvailabilityConfiguration.AvailabilityTypeEnum] = None,
		externalReplicaConfigured: Option[Boolean] = None,
		promotableReplicaConfigured: Option[Boolean] = None,
	  /** Checks for resources that are configured to have redundancy, and ongoing replication across regions */
		crossRegionReplicaConfigured: Option[Boolean] = None,
	  /** Checks for existence of (multi-cluster) routing configuration that allows automatic failover to a different zone/region in case of an outage. Applicable to Bigtable resources. */
		automaticFailoverRoutingConfigured: Option[Boolean] = None
	)
	
	case class StorageDatabasecenterPartnerapiV1mainBackupConfiguration(
	  /** Whether customer visible automated backups are enabled on the instance. */
		automatedBackupEnabled: Option[Boolean] = None,
	  /** Backup retention settings. */
		backupRetentionSettings: Option[Schema.StorageDatabasecenterPartnerapiV1mainRetentionSettings] = None,
	  /** Whether point-in-time recovery is enabled. This is optional field, if the database service does not have this feature or metadata is not available in control plane, this can be omitted. */
		pointInTimeRecoveryEnabled: Option[Boolean] = None
	)
	
	object StorageDatabasecenterPartnerapiV1mainRetentionSettings {
		enum RetentionUnitEnum extends Enum[RetentionUnitEnum] { case RETENTION_UNIT_UNSPECIFIED, COUNT, TIME, DURATION, RETENTION_UNIT_OTHER }
	}
	case class StorageDatabasecenterPartnerapiV1mainRetentionSettings(
	  /** The unit that 'retained_backups' represents. */
		retentionUnit: Option[Schema.StorageDatabasecenterPartnerapiV1mainRetentionSettings.RetentionUnitEnum] = None,
		timeBasedRetention: Option[String] = None,
		quantityBasedRetention: Option[Int] = None,
	  /** Duration based retention period i.e. 172800 seconds (2 days) */
		durationBasedRetention: Option[String] = None,
	  /** Timestamp based retention period i.e. 2024-05-01T00:00:00Z */
		timestampBasedRetentionTime: Option[String] = None
	)
	
	object StorageDatabasecenterPartnerapiV1mainBackupRun {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNSPECIFIED, SUCCESSFUL, FAILED }
	}
	case class StorageDatabasecenterPartnerapiV1mainBackupRun(
	  /** The time the backup operation started. REQUIRED */
		startTime: Option[String] = None,
	  /** The time the backup operation completed. REQUIRED */
		endTime: Option[String] = None,
	  /** The status of this run. REQUIRED */
		status: Option[Schema.StorageDatabasecenterPartnerapiV1mainBackupRun.StatusEnum] = None,
	  /** Information about why the backup operation failed. This is only present if the run has the FAILED status. OPTIONAL */
		error: Option[Schema.StorageDatabasecenterPartnerapiV1mainOperationError] = None
	)
	
	object StorageDatabasecenterPartnerapiV1mainOperationError {
		enum ErrorTypeEnum extends Enum[ErrorTypeEnum] { case OPERATION_ERROR_TYPE_UNSPECIFIED, KMS_KEY_ERROR, DATABASE_ERROR, STOCKOUT_ERROR, CANCELLATION_ERROR, SQLSERVER_ERROR, INTERNAL_ERROR }
	}
	case class StorageDatabasecenterPartnerapiV1mainOperationError(
	  /** Identifies the specific error that occurred. REQUIRED */
		code: Option[String] = None,
	  /** Additional information about the error encountered. REQUIRED */
		message: Option[String] = None,
		errorType: Option[Schema.StorageDatabasecenterPartnerapiV1mainOperationError.ErrorTypeEnum] = None
	)
	
	case class StorageDatabasecenterPartnerapiV1mainCustomMetadataData(
	  /** Metadata for individual internal resources in an instance. e.g. spanner instance can have multiple databases with unique configuration. */
		internalResourceMetadata: Option[List[Schema.StorageDatabasecenterPartnerapiV1mainInternalResourceMetadata]] = None
	)
	
	case class StorageDatabasecenterPartnerapiV1mainInternalResourceMetadata(
		resourceId: Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceId] = None,
	  /** Required. internal resource name for spanner this will be database name e.g."spanner.googleapis.com/projects/123/abc/instances/inst1/databases/db1" */
		resourceName: Option[String] = None,
		product: Option[Schema.StorageDatabasecenterProtoCommonProduct] = None,
	  /** Backup configuration for this database */
		backupConfiguration: Option[Schema.StorageDatabasecenterPartnerapiV1mainBackupConfiguration] = None,
	  /** Information about the last backup attempt for this database */
		backupRun: Option[Schema.StorageDatabasecenterPartnerapiV1mainBackupRun] = None
	)
	
	object StorageDatabasecenterPartnerapiV1mainEntitlement {
		enum TypeEnum extends Enum[TypeEnum] { case ENTITLEMENT_TYPE_UNSPECIFIED, GEMINI }
		enum EntitlementStateEnum extends Enum[EntitlementStateEnum] { case ENTITLEMENT_STATE_UNSPECIFIED, ENTITLED, REVOKED }
	}
	case class StorageDatabasecenterPartnerapiV1mainEntitlement(
	  /** An enum that represents the type of this entitlement. */
		`type`: Option[Schema.StorageDatabasecenterPartnerapiV1mainEntitlement.TypeEnum] = None,
	  /** The current state of user's accessibility to a feature/benefit. */
		entitlementState: Option[Schema.StorageDatabasecenterPartnerapiV1mainEntitlement.EntitlementStateEnum] = None
	)
	
	case class StorageDatabasecenterPartnerapiV1mainUserLabels(
		labels: Option[Map[String, String]] = None
	)
	
	case class StorageDatabasecenterPartnerapiV1mainMachineConfiguration(
	  /** The number of CPUs. TODO(b/342344482, b/342346271) add proto validations again after bug fix. */
		cpuCount: Option[Int] = None,
	  /** Memory size in bytes. TODO(b/342344482, b/342346271) add proto validations again after bug fix. */
		memorySizeInBytes: Option[String] = None,
	  /** Optional. Number of shards (if applicable). */
		shardCount: Option[Int] = None
	)
	
	case class StorageDatabasecenterPartnerapiV1mainTags(
	  /** The Tag key/value mappings. */
		tags: Option[Map[String, String]] = None
	)
	
	object StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData {
		enum ProviderEnum extends Enum[ProviderEnum] { case PROVIDER_UNSPECIFIED, GCP, AWS, AZURE, ONPREM, SELFMANAGED, PROVIDER_OTHER }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, RESOLVED, MUTED }
		enum SignalClassEnum extends Enum[SignalClassEnum] { case CLASS_UNSPECIFIED, THREAT, VULNERABILITY, MISCONFIGURATION, OBSERVATION, ERROR }
		enum SignalSeverityEnum extends Enum[SignalSeverityEnum] { case SIGNAL_SEVERITY_UNSPECIFIED, CRITICAL, HIGH, MEDIUM, LOW }
		enum SignalTypeEnum extends Enum[SignalTypeEnum] { case SIGNAL_TYPE_UNSPECIFIED, SIGNAL_TYPE_NOT_PROTECTED_BY_AUTOMATIC_FAILOVER, SIGNAL_TYPE_GROUP_NOT_REPLICATING_ACROSS_REGIONS, SIGNAL_TYPE_NOT_AVAILABLE_IN_MULTIPLE_ZONES, SIGNAL_TYPE_NOT_AVAILABLE_IN_MULTIPLE_REGIONS, SIGNAL_TYPE_NO_PROMOTABLE_REPLICA, SIGNAL_TYPE_NO_AUTOMATED_BACKUP_POLICY, SIGNAL_TYPE_SHORT_BACKUP_RETENTION, SIGNAL_TYPE_LAST_BACKUP_FAILED, SIGNAL_TYPE_LAST_BACKUP_OLD, SIGNAL_TYPE_VIOLATES_CIS_GCP_FOUNDATION_2_0, SIGNAL_TYPE_VIOLATES_CIS_GCP_FOUNDATION_1_3, SIGNAL_TYPE_VIOLATES_CIS_GCP_FOUNDATION_1_2, SIGNAL_TYPE_VIOLATES_CIS_GCP_FOUNDATION_1_1, SIGNAL_TYPE_VIOLATES_CIS_GCP_FOUNDATION_1_0, SIGNAL_TYPE_VIOLATES_CIS_CONTROLS_V8_0, SIGNAL_TYPE_VIOLATES_NIST_800_53, SIGNAL_TYPE_VIOLATES_NIST_800_53_R5, SIGNAL_TYPE_VIOLATES_NIST_CYBERSECURITY_FRAMEWORK_V1_0, SIGNAL_TYPE_VIOLATES_ISO_27001, SIGNAL_TYPE_VIOLATES_ISO_27001_V2022, SIGNAL_TYPE_VIOLATES_PCI_DSS_V3_2_1, SIGNAL_TYPE_VIOLATES_PCI_DSS_V4_0, SIGNAL_TYPE_VIOLATES_CLOUD_CONTROLS_MATRIX_V4, SIGNAL_TYPE_VIOLATES_HIPAA, SIGNAL_TYPE_VIOLATES_SOC2_V2017, SIGNAL_TYPE_LOGS_NOT_OPTIMIZED_FOR_TROUBLESHOOTING, SIGNAL_TYPE_QUERY_DURATIONS_NOT_LOGGED, SIGNAL_TYPE_VERBOSE_ERROR_LOGGING, SIGNAL_TYPE_QUERY_LOCK_WAITS_NOT_LOGGED, SIGNAL_TYPE_LOGGING_MOST_ERRORS, SIGNAL_TYPE_LOGGING_ONLY_CRITICAL_ERRORS, SIGNAL_TYPE_MINIMAL_ERROR_LOGGING, SIGNAL_TYPE_QUERY_STATISTICS_LOGGED, SIGNAL_TYPE_EXCESSIVE_LOGGING_OF_CLIENT_HOSTNAME, SIGNAL_TYPE_EXCESSIVE_LOGGING_OF_PARSER_STATISTICS, SIGNAL_TYPE_EXCESSIVE_LOGGING_OF_PLANNER_STATISTICS, SIGNAL_TYPE_NOT_LOGGING_ONLY_DDL_STATEMENTS, SIGNAL_TYPE_LOGGING_QUERY_STATISTICS, SIGNAL_TYPE_NOT_LOGGING_TEMPORARY_FILES, SIGNAL_TYPE_CONNECTION_MAX_NOT_CONFIGURED, SIGNAL_TYPE_USER_OPTIONS_CONFIGURED, SIGNAL_TYPE_EXPOSED_TO_PUBLIC_ACCESS, SIGNAL_TYPE_UNENCRYPTED_CONNECTIONS, SIGNAL_TYPE_NO_ROOT_PASSWORD, SIGNAL_TYPE_WEAK_ROOT_PASSWORD, SIGNAL_TYPE_ENCRYPTION_KEY_NOT_CUSTOMER_MANAGED, SIGNAL_TYPE_SERVER_AUTHENTICATION_NOT_REQUIRED, SIGNAL_TYPE_EXPOSED_BY_OWNERSHIP_CHAINING, SIGNAL_TYPE_EXPOSED_TO_EXTERNAL_SCRIPTS, SIGNAL_TYPE_EXPOSED_TO_LOCAL_DATA_LOADS, SIGNAL_TYPE_CONNECTION_ATTEMPTS_NOT_LOGGED, SIGNAL_TYPE_DISCONNECTIONS_NOT_LOGGED, SIGNAL_TYPE_LOGGING_EXCESSIVE_STATEMENT_INFO, SIGNAL_TYPE_EXPOSED_TO_REMOTE_ACCESS, SIGNAL_TYPE_DATABASE_NAMES_EXPOSED, SIGNAL_TYPE_SENSITIVE_TRACE_INFO_NOT_MASKED, SIGNAL_TYPE_PUBLIC_IP_ENABLED, SIGNAL_TYPE_IDLE, SIGNAL_TYPE_OVERPROVISIONED, SIGNAL_TYPE_HIGH_NUMBER_OF_OPEN_TABLES, SIGNAL_TYPE_HIGH_NUMBER_OF_TABLES, SIGNAL_TYPE_HIGH_TRANSACTION_ID_UTILIZATION, SIGNAL_TYPE_UNDERPROVISIONED, SIGNAL_TYPE_OUT_OF_DISK, SIGNAL_TYPE_SERVER_CERTIFICATE_NEAR_EXPIRY, SIGNAL_TYPE_DATABASE_AUDITING_DISABLED, SIGNAL_TYPE_RESTRICT_AUTHORIZED_NETWORKS, SIGNAL_TYPE_VIOLATE_POLICY_RESTRICT_PUBLIC_IP, SIGNAL_TYPE_QUOTA_LIMIT, SIGNAL_TYPE_NO_PASSWORD_POLICY, SIGNAL_TYPE_CONNECTIONS_PERFORMANCE_IMPACT, SIGNAL_TYPE_TMP_TABLES_PERFORMANCE_IMPACT, SIGNAL_TYPE_TRANS_LOGS_PERFORMANCE_IMPACT, SIGNAL_TYPE_HIGH_JOINS_WITHOUT_INDEXES, SIGNAL_TYPE_SUPERUSER_WRITING_TO_USER_TABLES, SIGNAL_TYPE_USER_GRANTED_ALL_PERMISSIONS, SIGNAL_TYPE_DATA_EXPORT_TO_EXTERNAL_CLOUD_STORAGE_BUCKET, SIGNAL_TYPE_DATA_EXPORT_TO_PUBLIC_CLOUD_STORAGE_BUCKET }
	}
	case class StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData(
	  /** Required. Unique identifier for the signal. This is an unique id which would be mainatined by partner to identify a signal. */
		signalId: Option[String] = None,
	  /** Required. The name of the signal, ex: PUBLIC_SQL_INSTANCE, SQL_LOG_ERROR_VERBOSITY etc. */
		name: Option[String] = None,
	  /** The external-uri of the signal, using which more information about this signal can be obtained. In GCP, this will take user to SCC page to get more details about signals. */
		externalUri: Option[String] = None,
	  /** Required. Database resource name associated with the signal. Resource name to follow CAIS resource_name format as noted here go/condor-common-datamodel */
		resourceName: Option[String] = None,
	  /** Cloud provider name. Ex: GCP/AWS/Azure/OnPrem/SelfManaged */
		provider: Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData.ProviderEnum] = None,
	  /** Closest parent container of this resource. In GCP, 'container' refers to a Cloud Resource Manager project. It must be resource name of a Cloud Resource Manager project with the format of "provider//", such as "projects/123". For GCP provided resources, number should be project number. */
		resourceContainer: Option[String] = None,
	  /** Description associated with signal */
		description: Option[String] = None,
	  /** Required. The last time at which the event described by this signal took place */
		eventTime: Option[String] = None,
		state: Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData.StateEnum] = None,
	  /** Required. The class of the signal, such as if it's a THREAT or VULNERABILITY. */
		signalClass: Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData.SignalClassEnum] = None,
	  /** The severity of the signal, such as if it's a HIGH or LOW severity. */
		signalSeverity: Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData.SignalSeverityEnum] = None,
	  /** Industry standards associated with this signal; if this signal is an issue, that could be a violation of the associated industry standard(s). For example, AUTO_BACKUP_DISABLED signal is associated with CIS GCP 1.1, CIS GCP 1.2, CIS GCP 1.3, NIST 800-53 and ISO-27001 compliance standards. If a database resource does not have automated backup enable, it will violate these following industry standards. */
		compliance: Option[List[Schema.StorageDatabasecenterPartnerapiV1mainCompliance]] = None,
	  /** Any other additional metadata */
		additionalMetadata: Option[Map[String, JsValue]] = None,
	  /** Required. Type of signal, for example, `AVAILABLE_IN_MULTIPLE_ZONES`, `LOGGING_MOST_ERRORS`, etc. */
		signalType: Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData.SignalTypeEnum] = None
	)
	
	case class StorageDatabasecenterPartnerapiV1mainCompliance(
	  /** Industry-wide compliance standards or benchmarks, such as CIS, PCI, and OWASP. */
		standard: Option[String] = None,
	  /** Version of the standard or benchmark, for example, 1.1 */
		version: Option[String] = None
	)
	
	object StorageDatabasecenterPartnerapiV1mainDatabaseResourceRecommendationSignalData {
		enum SignalTypeEnum extends Enum[SignalTypeEnum] { case SIGNAL_TYPE_UNSPECIFIED, SIGNAL_TYPE_NOT_PROTECTED_BY_AUTOMATIC_FAILOVER, SIGNAL_TYPE_GROUP_NOT_REPLICATING_ACROSS_REGIONS, SIGNAL_TYPE_NOT_AVAILABLE_IN_MULTIPLE_ZONES, SIGNAL_TYPE_NOT_AVAILABLE_IN_MULTIPLE_REGIONS, SIGNAL_TYPE_NO_PROMOTABLE_REPLICA, SIGNAL_TYPE_NO_AUTOMATED_BACKUP_POLICY, SIGNAL_TYPE_SHORT_BACKUP_RETENTION, SIGNAL_TYPE_LAST_BACKUP_FAILED, SIGNAL_TYPE_LAST_BACKUP_OLD, SIGNAL_TYPE_VIOLATES_CIS_GCP_FOUNDATION_2_0, SIGNAL_TYPE_VIOLATES_CIS_GCP_FOUNDATION_1_3, SIGNAL_TYPE_VIOLATES_CIS_GCP_FOUNDATION_1_2, SIGNAL_TYPE_VIOLATES_CIS_GCP_FOUNDATION_1_1, SIGNAL_TYPE_VIOLATES_CIS_GCP_FOUNDATION_1_0, SIGNAL_TYPE_VIOLATES_CIS_CONTROLS_V8_0, SIGNAL_TYPE_VIOLATES_NIST_800_53, SIGNAL_TYPE_VIOLATES_NIST_800_53_R5, SIGNAL_TYPE_VIOLATES_NIST_CYBERSECURITY_FRAMEWORK_V1_0, SIGNAL_TYPE_VIOLATES_ISO_27001, SIGNAL_TYPE_VIOLATES_ISO_27001_V2022, SIGNAL_TYPE_VIOLATES_PCI_DSS_V3_2_1, SIGNAL_TYPE_VIOLATES_PCI_DSS_V4_0, SIGNAL_TYPE_VIOLATES_CLOUD_CONTROLS_MATRIX_V4, SIGNAL_TYPE_VIOLATES_HIPAA, SIGNAL_TYPE_VIOLATES_SOC2_V2017, SIGNAL_TYPE_LOGS_NOT_OPTIMIZED_FOR_TROUBLESHOOTING, SIGNAL_TYPE_QUERY_DURATIONS_NOT_LOGGED, SIGNAL_TYPE_VERBOSE_ERROR_LOGGING, SIGNAL_TYPE_QUERY_LOCK_WAITS_NOT_LOGGED, SIGNAL_TYPE_LOGGING_MOST_ERRORS, SIGNAL_TYPE_LOGGING_ONLY_CRITICAL_ERRORS, SIGNAL_TYPE_MINIMAL_ERROR_LOGGING, SIGNAL_TYPE_QUERY_STATISTICS_LOGGED, SIGNAL_TYPE_EXCESSIVE_LOGGING_OF_CLIENT_HOSTNAME, SIGNAL_TYPE_EXCESSIVE_LOGGING_OF_PARSER_STATISTICS, SIGNAL_TYPE_EXCESSIVE_LOGGING_OF_PLANNER_STATISTICS, SIGNAL_TYPE_NOT_LOGGING_ONLY_DDL_STATEMENTS, SIGNAL_TYPE_LOGGING_QUERY_STATISTICS, SIGNAL_TYPE_NOT_LOGGING_TEMPORARY_FILES, SIGNAL_TYPE_CONNECTION_MAX_NOT_CONFIGURED, SIGNAL_TYPE_USER_OPTIONS_CONFIGURED, SIGNAL_TYPE_EXPOSED_TO_PUBLIC_ACCESS, SIGNAL_TYPE_UNENCRYPTED_CONNECTIONS, SIGNAL_TYPE_NO_ROOT_PASSWORD, SIGNAL_TYPE_WEAK_ROOT_PASSWORD, SIGNAL_TYPE_ENCRYPTION_KEY_NOT_CUSTOMER_MANAGED, SIGNAL_TYPE_SERVER_AUTHENTICATION_NOT_REQUIRED, SIGNAL_TYPE_EXPOSED_BY_OWNERSHIP_CHAINING, SIGNAL_TYPE_EXPOSED_TO_EXTERNAL_SCRIPTS, SIGNAL_TYPE_EXPOSED_TO_LOCAL_DATA_LOADS, SIGNAL_TYPE_CONNECTION_ATTEMPTS_NOT_LOGGED, SIGNAL_TYPE_DISCONNECTIONS_NOT_LOGGED, SIGNAL_TYPE_LOGGING_EXCESSIVE_STATEMENT_INFO, SIGNAL_TYPE_EXPOSED_TO_REMOTE_ACCESS, SIGNAL_TYPE_DATABASE_NAMES_EXPOSED, SIGNAL_TYPE_SENSITIVE_TRACE_INFO_NOT_MASKED, SIGNAL_TYPE_PUBLIC_IP_ENABLED, SIGNAL_TYPE_IDLE, SIGNAL_TYPE_OVERPROVISIONED, SIGNAL_TYPE_HIGH_NUMBER_OF_OPEN_TABLES, SIGNAL_TYPE_HIGH_NUMBER_OF_TABLES, SIGNAL_TYPE_HIGH_TRANSACTION_ID_UTILIZATION, SIGNAL_TYPE_UNDERPROVISIONED, SIGNAL_TYPE_OUT_OF_DISK, SIGNAL_TYPE_SERVER_CERTIFICATE_NEAR_EXPIRY, SIGNAL_TYPE_DATABASE_AUDITING_DISABLED, SIGNAL_TYPE_RESTRICT_AUTHORIZED_NETWORKS, SIGNAL_TYPE_VIOLATE_POLICY_RESTRICT_PUBLIC_IP, SIGNAL_TYPE_QUOTA_LIMIT, SIGNAL_TYPE_NO_PASSWORD_POLICY, SIGNAL_TYPE_CONNECTIONS_PERFORMANCE_IMPACT, SIGNAL_TYPE_TMP_TABLES_PERFORMANCE_IMPACT, SIGNAL_TYPE_TRANS_LOGS_PERFORMANCE_IMPACT, SIGNAL_TYPE_HIGH_JOINS_WITHOUT_INDEXES, SIGNAL_TYPE_SUPERUSER_WRITING_TO_USER_TABLES, SIGNAL_TYPE_USER_GRANTED_ALL_PERMISSIONS, SIGNAL_TYPE_DATA_EXPORT_TO_EXTERNAL_CLOUD_STORAGE_BUCKET, SIGNAL_TYPE_DATA_EXPORT_TO_PUBLIC_CLOUD_STORAGE_BUCKET }
		enum RecommendationStateEnum extends Enum[RecommendationStateEnum] { case UNSPECIFIED, ACTIVE, CLAIMED, SUCCEEDED, FAILED, DISMISSED }
	}
	case class StorageDatabasecenterPartnerapiV1mainDatabaseResourceRecommendationSignalData(
	  /** Required. Database resource name associated with the signal. Resource name to follow CAIS resource_name format as noted here go/condor-common-datamodel */
		resourceName: Option[String] = None,
	  /** Required. Type of signal, for example, `SIGNAL_TYPE_IDLE`, `SIGNAL_TYPE_HIGH_NUMBER_OF_TABLES`, etc. */
		signalType: Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceRecommendationSignalData.SignalTypeEnum] = None,
	  /** Required. last time recommendationw as refreshed */
		lastRefreshTime: Option[String] = None,
	  /** Required. Recommendation state */
		recommendationState: Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceRecommendationSignalData.RecommendationStateEnum] = None,
	  /** Required. Name of recommendation. Examples: organizations/1234/locations/us-central1/recommenders/google.cloudsql.instance.PerformanceRecommender/recommendations/9876 */
		recommender: Option[String] = None,
	  /** Required. ID of recommender. Examples: "google.cloudsql.instance.PerformanceRecommender" */
		recommenderId: Option[String] = None,
	  /** Required. Contains an identifier for a subtype of recommendations produced for the same recommender. Subtype is a function of content and impact, meaning a new subtype might be added when significant changes to `content` or `primary_impact.category` are introduced. See the Recommenders section to see a list of subtypes for a given Recommender. Examples: For recommender = "google.cloudsql.instance.PerformanceRecommender", recommender_subtype can be "MYSQL_HIGH_NUMBER_OF_OPEN_TABLES_BEST_PRACTICE"/"POSTGRES_HIGH_TRANSACTION_ID_UTILIZATION_BEST_PRACTICE" */
		recommenderSubtype: Option[String] = None,
	  /** Optional. Any other additional metadata specific to recommendation */
		additionalMetadata: Option[Map[String, JsValue]] = None
	)
	
	object StorageDatabasecenterPartnerapiV1mainObservabilityMetricData {
		enum MetricTypeEnum extends Enum[MetricTypeEnum] { case METRIC_TYPE_UNSPECIFIED, CPU_UTILIZATION, MEMORY_UTILIZATION, NETWORK_CONNECTIONS, STORAGE_UTILIZATION, STORAGE_USED_BYTES }
		enum AggregationTypeEnum extends Enum[AggregationTypeEnum] { case AGGREGATION_TYPE_UNSPECIFIED, PEAK, P99, P95, CURRENT }
	}
	case class StorageDatabasecenterPartnerapiV1mainObservabilityMetricData(
	  /** Required. Database resource name associated with the signal. Resource name to follow CAIS resource_name format as noted here go/condor-common-datamodel */
		resourceName: Option[String] = None,
	  /** Required. Type of metric like CPU, Memory, etc. */
		metricType: Option[Schema.StorageDatabasecenterPartnerapiV1mainObservabilityMetricData.MetricTypeEnum] = None,
	  /** Required. Type of aggregation performed on the metric. */
		aggregationType: Option[Schema.StorageDatabasecenterPartnerapiV1mainObservabilityMetricData.AggregationTypeEnum] = None,
	  /** Required. Value of the metric type. */
		value: Option[Schema.StorageDatabasecenterProtoCommonTypedValue] = None,
	  /** Required. The time the metric value was observed. */
		observationTime: Option[String] = None
	)
	
	case class StorageDatabasecenterProtoCommonTypedValue(
	  /** For double value */
		doubleValue: Option[BigDecimal] = None,
	  /** For integer value */
		int64Value: Option[String] = None,
	  /** For string value */
		stringValue: Option[String] = None,
	  /** For boolean value */
		boolValue: Option[Boolean] = None
	)
	
	object CloudControl2SharedOperationsReconciliationOperationMetadata {
		enum ExclusiveActionEnum extends Enum[ExclusiveActionEnum] { case UNKNOWN_REPAIR_ACTION, DELETE, RETRY }
	}
	case class CloudControl2SharedOperationsReconciliationOperationMetadata(
	  /** DEPRECATED. Use exclusive_action instead. */
		deleteResource: Option[Boolean] = None,
	  /** Excluisive action returned by the CLH. */
		exclusiveAction: Option[Schema.CloudControl2SharedOperationsReconciliationOperationMetadata.ExclusiveActionEnum] = None
	)
}
