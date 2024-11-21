package com.boresjo.play.api.google.metastore

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
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the name should be a resource name ending with operations/{unique_id}. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is false, it means the operation is still in progress. If true, the operation is completed, and either error or response is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as Delete, the response is google.protobuf.Empty. If the original method is standard Get/Create/Update, the response should be the resource. For other methods, the response should have the type XxxResponse, where Xxx is the original method name. For example, if the original method name is TakeSnapshot(), the inferred response type is TakeSnapshotResponse. */
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
	
	case class ListFederationsResponse(
	  /** The services in the specified location. */
		federations: Option[List[Schema.Federation]] = None,
	  /** A token that can be sent as page_token to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Federation {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, UPDATING, DELETING, ERROR }
	}
	case class Federation(
	  /** Immutable. The relative resource name of the federation, of the form: projects/{project_number}/locations/{location_id}/federations/{federation_id}`. */
		name: Option[String] = None,
	  /** Output only. The time when the metastore federation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the metastore federation was last updated. */
		updateTime: Option[String] = None,
	  /** User-defined labels for the metastore federation. */
		labels: Option[Map[String, String]] = None,
	  /** Immutable. The Apache Hive metastore version of the federation. All backend metastore versions must be compatible with the federation version. */
		version: Option[String] = None,
	  /** A map from BackendMetastore rank to BackendMetastores from which the federation service serves metadata at query time. The map key represents the order in which BackendMetastores should be evaluated to resolve database names at query time and should be greater than or equal to zero. A BackendMetastore with a lower number will be evaluated before a BackendMetastore with a higher number. */
		backendMetastores: Option[Map[String, Schema.BackendMetastore]] = None,
	  /** Output only. The federation endpoint. */
		endpointUri: Option[String] = None,
	  /** Output only. The current state of the federation. */
		state: Option[Schema.Federation.StateEnum] = None,
	  /** Output only. Additional information about the current state of the metastore federation, if available. */
		stateMessage: Option[String] = None,
	  /** Output only. The globally unique resource identifier of the metastore federation. */
		uid: Option[String] = None
	)
	
	object BackendMetastore {
		enum MetastoreTypeEnum extends Enum[MetastoreTypeEnum] { case METASTORE_TYPE_UNSPECIFIED, BIGQUERY, DATAPROC_METASTORE }
	}
	case class BackendMetastore(
	  /** The relative resource name of the metastore that is being federated. The formats of the relative resource names for the currently supported metastores are listed below: BigQuery projects/{project_id} Dataproc Metastore projects/{project_id}/locations/{location}/services/{service_id} */
		name: Option[String] = None,
	  /** The type of the backend metastore. */
		metastoreType: Option[Schema.BackendMetastore.MetastoreTypeEnum] = None
	)
	
	case class ListServicesResponse(
	  /** The services in the specified location. */
		services: Option[List[Schema.Service]] = None,
	  /** A token that can be sent as page_token to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Service {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, SUSPENDING, SUSPENDED, UPDATING, DELETING, ERROR, AUTOSCALING, MIGRATING }
		enum TierEnum extends Enum[TierEnum] { case TIER_UNSPECIFIED, DEVELOPER, ENTERPRISE }
		enum ReleaseChannelEnum extends Enum[ReleaseChannelEnum] { case RELEASE_CHANNEL_UNSPECIFIED, CANARY, STABLE }
		enum DatabaseTypeEnum extends Enum[DatabaseTypeEnum] { case DATABASE_TYPE_UNSPECIFIED, MYSQL, SPANNER }
	}
	case class Service(
	  /** Configuration information specific to running Hive metastore software as the metastore service. */
		hiveMetastoreConfig: Option[Schema.HiveMetastoreConfig] = None,
	  /** Immutable. The relative resource name of the metastore service, in the following format:projects/{project_number}/locations/{location_id}/services/{service_id}. */
		name: Option[String] = None,
	  /** Output only. The time when the metastore service was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the metastore service was last updated. */
		updateTime: Option[String] = None,
	  /** User-defined labels for the metastore service. */
		labels: Option[Map[String, String]] = None,
	  /** Immutable. The relative resource name of the VPC network on which the instance can be accessed. It is specified in the following form:projects/{project_number}/global/networks/{network_id}. */
		network: Option[String] = None,
	  /** Output only. The URI of the endpoint used to access the metastore service. */
		endpointUri: Option[String] = None,
	  /** The TCP port at which the metastore service is reached. Default: 9083. */
		port: Option[Int] = None,
	  /** Output only. The current state of the metastore service. */
		state: Option[Schema.Service.StateEnum] = None,
	  /** Output only. Additional information about the current state of the metastore service, if available. */
		stateMessage: Option[String] = None,
	  /** Output only. A Cloud Storage URI (starting with gs://) that specifies where artifacts related to the metastore service are stored. */
		artifactGcsUri: Option[String] = None,
	  /** The tier of the service. */
		tier: Option[Schema.Service.TierEnum] = None,
	  /** Optional. The setting that defines how metastore metadata should be integrated with external services and systems. */
		metadataIntegration: Option[Schema.MetadataIntegration] = None,
	  /** The one hour maintenance window of the metastore service. This specifies when the service can be restarted for maintenance purposes in UTC time. Maintenance window is not needed for services with the SPANNER database type. */
		maintenanceWindow: Option[Schema.MaintenanceWindow] = None,
	  /** Output only. The globally unique resource identifier of the metastore service. */
		uid: Option[String] = None,
	  /** Output only. The metadata management activities of the metastore service. */
		metadataManagementActivity: Option[Schema.MetadataManagementActivity] = None,
	  /** Immutable. The release channel of the service. If unspecified, defaults to STABLE. */
		releaseChannel: Option[Schema.Service.ReleaseChannelEnum] = None,
	  /** Immutable. Information used to configure the Dataproc Metastore service to encrypt customer data at rest. Cannot be updated. */
		encryptionConfig: Option[Schema.EncryptionConfig] = None,
	  /** The configuration specifying the network settings for the Dataproc Metastore service. */
		networkConfig: Option[Schema.NetworkConfig] = None,
	  /** Immutable. The database type that the Metastore service stores its data. */
		databaseType: Option[Schema.Service.DatabaseTypeEnum] = None,
	  /** The configuration specifying telemetry settings for the Dataproc Metastore service. If unspecified defaults to JSON. */
		telemetryConfig: Option[Schema.TelemetryConfig] = None,
	  /** Scaling configuration of the metastore service. */
		scalingConfig: Option[Schema.ScalingConfig] = None,
	  /** Optional. The configuration of scheduled backup for the metastore service. */
		scheduledBackup: Option[Schema.ScheduledBackup] = None,
	  /** Optional. Indicates if the dataproc metastore should be protected against accidental deletions. */
		deletionProtection: Option[Boolean] = None
	)
	
	object HiveMetastoreConfig {
		enum EndpointProtocolEnum extends Enum[EndpointProtocolEnum] { case ENDPOINT_PROTOCOL_UNSPECIFIED, THRIFT, GRPC }
	}
	case class HiveMetastoreConfig(
	  /** Immutable. The Hive metastore schema version. */
		version: Option[String] = None,
	  /** A mapping of Hive metastore configuration key-value pairs to apply to the Hive metastore (configured in hive-site.xml). The mappings override system defaults (some keys cannot be overridden). These overrides are also applied to auxiliary versions and can be further customized in the auxiliary version's AuxiliaryVersionConfig. */
		configOverrides: Option[Map[String, String]] = None,
	  /** Information used to configure the Hive metastore service as a service principal in a Kerberos realm. To disable Kerberos, use the UpdateService method and specify this field's path (hive_metastore_config.kerberos_config) in the request's update_mask while omitting this field from the request's service. */
		kerberosConfig: Option[Schema.KerberosConfig] = None,
	  /** The protocol to use for the metastore service endpoint. If unspecified, defaults to THRIFT. */
		endpointProtocol: Option[Schema.HiveMetastoreConfig.EndpointProtocolEnum] = None,
	  /** A mapping of Hive metastore version to the auxiliary version configuration. When specified, a secondary Hive metastore service is created along with the primary service. All auxiliary versions must be less than the service's primary version. The key is the auxiliary service name and it must match the regular expression a-z?. This means that the first character must be a lowercase letter, and all the following characters must be hyphens, lowercase letters, or digits, except the last character, which cannot be a hyphen. */
		auxiliaryVersions: Option[Map[String, Schema.AuxiliaryVersionConfig]] = None
	)
	
	case class KerberosConfig(
	  /** A Kerberos keytab file that can be used to authenticate a service principal with a Kerberos Key Distribution Center (KDC). */
		keytab: Option[Schema.Secret] = None,
	  /** A Kerberos principal that exists in the both the keytab the KDC to authenticate as. A typical principal is of the form primary/instance@REALM, but there is no exact format. */
		principal: Option[String] = None,
	  /** A Cloud Storage URI that specifies the path to a krb5.conf file. It is of the form gs://{bucket_name}/path/to/krb5.conf, although the file does not need to be named krb5.conf explicitly. */
		krb5ConfigGcsUri: Option[String] = None
	)
	
	case class Secret(
	  /** The relative resource name of a Secret Manager secret version, in the following form:projects/{project_number}/secrets/{secret_id}/versions/{version_id}. */
		cloudSecret: Option[String] = None
	)
	
	case class AuxiliaryVersionConfig(
	  /** The Hive metastore version of the auxiliary service. It must be less than the primary Hive metastore service's version. */
		version: Option[String] = None,
	  /** A mapping of Hive metastore configuration key-value pairs to apply to the auxiliary Hive metastore (configured in hive-site.xml) in addition to the primary version's overrides. If keys are present in both the auxiliary version's overrides and the primary version's overrides, the value from the auxiliary version's overrides takes precedence. */
		configOverrides: Option[Map[String, String]] = None,
	  /** Output only. The network configuration contains the endpoint URI(s) of the auxiliary Hive metastore service. */
		networkConfig: Option[Schema.NetworkConfig] = None
	)
	
	case class NetworkConfig(
	  /** Immutable. The consumer-side network configuration for the Dataproc Metastore instance. */
		consumers: Option[List[Schema.Consumer]] = None
	)
	
	case class Consumer(
	  /** Immutable. The subnetwork of the customer project from which an IP address is reserved and used as the Dataproc Metastore service's endpoint. It is accessible to hosts in the subnet and to all hosts in a subnet in the same region and same network. There must be at least one IP address available in the subnet's primary range. The subnet is specified in the following form:projects/{project_number}/regions/{region_id}/subnetworks/{subnetwork_id} */
		subnetwork: Option[String] = None,
	  /** Output only. The URI of the endpoint used to access the metastore service. */
		endpointUri: Option[String] = None,
	  /** Output only. The location of the endpoint URI. Format: projects/{project}/locations/{location}. */
		endpointLocation: Option[String] = None
	)
	
	case class MetadataIntegration(
	  /** Optional. The integration config for the Data Catalog service. */
		dataCatalogConfig: Option[Schema.DataCatalogConfig] = None
	)
	
	case class DataCatalogConfig(
	  /** Optional. Defines whether the metastore metadata should be synced to Data Catalog. The default value is to disable syncing metastore metadata to Data Catalog. */
		enabled: Option[Boolean] = None
	)
	
	object MaintenanceWindow {
		enum DayOfWeekEnum extends Enum[DayOfWeekEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class MaintenanceWindow(
	  /** The hour of day (0-23) when the window starts. */
		hourOfDay: Option[Int] = None,
	  /** The day of week, when the window starts. */
		dayOfWeek: Option[Schema.MaintenanceWindow.DayOfWeekEnum] = None
	)
	
	case class MetadataManagementActivity(
	  /** Output only. The latest metadata exports of the metastore service. */
		metadataExports: Option[List[Schema.MetadataExport]] = None,
	  /** Output only. The latest restores of the metastore service. */
		restores: Option[List[Schema.Restore]] = None
	)
	
	object MetadataExport {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RUNNING, SUCCEEDED, FAILED, CANCELLED }
		enum DatabaseDumpTypeEnum extends Enum[DatabaseDumpTypeEnum] { case TYPE_UNSPECIFIED, MYSQL, AVRO }
	}
	case class MetadataExport(
	  /** Output only. A Cloud Storage URI of a folder that metadata are exported to, in the form of gs:////, where is automatically generated. */
		destinationGcsUri: Option[String] = None,
	  /** Output only. The time when the export started. */
		startTime: Option[String] = None,
	  /** Output only. The time when the export ended. */
		endTime: Option[String] = None,
	  /** Output only. The current state of the export. */
		state: Option[Schema.MetadataExport.StateEnum] = None,
	  /** Output only. The type of the database dump. */
		databaseDumpType: Option[Schema.MetadataExport.DatabaseDumpTypeEnum] = None
	)
	
	object Restore {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RUNNING, SUCCEEDED, FAILED, CANCELLED }
		enum TypeEnum extends Enum[TypeEnum] { case RESTORE_TYPE_UNSPECIFIED, FULL, METADATA_ONLY }
	}
	case class Restore(
	  /** Output only. The time when the restore started. */
		startTime: Option[String] = None,
	  /** Output only. The time when the restore ended. */
		endTime: Option[String] = None,
	  /** Output only. The current state of the restore. */
		state: Option[Schema.Restore.StateEnum] = None,
	  /** Output only. The relative resource name of the metastore service backup to restore from, in the following form:projects/{project_id}/locations/{location_id}/services/{service_id}/backups/{backup_id}. */
		backup: Option[String] = None,
	  /** Output only. The type of restore. */
		`type`: Option[Schema.Restore.TypeEnum] = None,
	  /** Output only. The restore details containing the revision of the service to be restored to, in format of JSON. */
		details: Option[String] = None,
	  /** Optional. A Cloud Storage URI specifying where the backup artifacts are stored, in the format gs:///. */
		backupLocation: Option[String] = None
	)
	
	case class EncryptionConfig(
	  /** The fully qualified customer provided Cloud KMS key name to use for customer data encryption, in the following format:projects/{project_number}/locations/{location_id}/keyRings/{key_ring_id}/cryptoKeys/{crypto_key_id}. */
		kmsKey: Option[String] = None
	)
	
	object TelemetryConfig {
		enum LogFormatEnum extends Enum[LogFormatEnum] { case LOG_FORMAT_UNSPECIFIED, LEGACY, JSON }
	}
	case class TelemetryConfig(
	  /** The output format of the Dataproc Metastore service's logs. */
		logFormat: Option[Schema.TelemetryConfig.LogFormatEnum] = None
	)
	
	object ScalingConfig {
		enum InstanceSizeEnum extends Enum[InstanceSizeEnum] { case INSTANCE_SIZE_UNSPECIFIED, EXTRA_SMALL, SMALL, MEDIUM, LARGE, EXTRA_LARGE }
	}
	case class ScalingConfig(
	  /** An enum of readable instance sizes, with each instance size mapping to a float value (e.g. InstanceSize.EXTRA_SMALL = scaling_factor(0.1)) */
		instanceSize: Option[Schema.ScalingConfig.InstanceSizeEnum] = None,
	  /** Scaling factor, increments of 0.1 for values less than 1.0, and increments of 1.0 for values greater than 1.0. */
		scalingFactor: Option[BigDecimal] = None,
	  /** Optional. The autoscaling configuration. */
		autoscalingConfig: Option[Schema.AutoscalingConfig] = None
	)
	
	case class AutoscalingConfig(
	  /** Output only. The scaling factor of a service with autoscaling enabled. */
		autoscalingFactor: Option[BigDecimal] = None,
	  /** Optional. Whether or not autoscaling is enabled for this service. */
		autoscalingEnabled: Option[Boolean] = None,
	  /** Optional. The LimitConfig of the service. */
		limitConfig: Option[Schema.LimitConfig] = None
	)
	
	case class LimitConfig(
	  /** Optional. The highest scaling factor that the service should be autoscaled to. */
		maxScalingFactor: Option[BigDecimal] = None,
	  /** Optional. The lowest scaling factor that the service should be autoscaled to. */
		minScalingFactor: Option[BigDecimal] = None
	)
	
	case class ScheduledBackup(
	  /** Optional. Defines whether the scheduled backup is enabled. The default value is false. */
		enabled: Option[Boolean] = None,
	  /** Optional. The scheduled interval in Cron format, see https://en.wikipedia.org/wiki/Cron The default is empty: scheduled backup is not enabled. Must be specified to enable scheduled backups. */
		cronSchedule: Option[String] = None,
	  /** Optional. Specifies the time zone to be used when interpreting cron_schedule. Must be a time zone name from the time zone database (https://en.wikipedia.org/wiki/List_of_tz_database_time_zones), e.g. America/Los_Angeles or Africa/Abidjan. If left unspecified, the default is UTC. */
		timeZone: Option[String] = None,
	  /** Output only. The time when the next backups execution is scheduled to start. */
		nextScheduledTime: Option[String] = None,
	  /** Optional. A Cloud Storage URI of a folder, in the format gs:///. A sub-folder containing backup files will be stored below it. */
		backupLocation: Option[String] = None,
	  /** Output only. The details of the latest scheduled backup. */
		latestBackup: Option[Schema.LatestBackup] = None
	)
	
	object LatestBackup {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, IN_PROGRESS, SUCCEEDED, FAILED }
	}
	case class LatestBackup(
	  /** Output only. The ID of an in-progress scheduled backup. Empty if no backup is in progress. */
		backupId: Option[String] = None,
	  /** Output only. The time when the backup was started. */
		startTime: Option[String] = None,
	  /** Output only. The current state of the backup. */
		state: Option[Schema.LatestBackup.StateEnum] = None,
	  /** Output only. The duration of the backup completion. */
		duration: Option[String] = None
	)
	
	case class ListMetadataImportsResponse(
	  /** The imports in the specified service. */
		metadataImports: Option[List[Schema.MetadataImport]] = None,
	  /** A token that can be sent as page_token to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object MetadataImport {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RUNNING, SUCCEEDED, UPDATING, FAILED }
	}
	case class MetadataImport(
	  /** Immutable. A database dump from a pre-existing metastore's database. */
		databaseDump: Option[Schema.DatabaseDump] = None,
	  /** Immutable. The relative resource name of the metadata import, of the form:projects/{project_number}/locations/{location_id}/services/{service_id}/metadataImports/{metadata_import_id}. */
		name: Option[String] = None,
	  /** The description of the metadata import. */
		description: Option[String] = None,
	  /** Output only. The time when the metadata import was started. */
		createTime: Option[String] = None,
	  /** Output only. The time when the metadata import was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. The time when the metadata import finished. */
		endTime: Option[String] = None,
	  /** Output only. The current state of the metadata import. */
		state: Option[Schema.MetadataImport.StateEnum] = None
	)
	
	object DatabaseDump {
		enum DatabaseTypeEnum extends Enum[DatabaseTypeEnum] { case DATABASE_TYPE_UNSPECIFIED, MYSQL }
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, MYSQL, AVRO }
	}
	case class DatabaseDump(
	  /** The type of the database. */
		databaseType: Option[Schema.DatabaseDump.DatabaseTypeEnum] = None,
	  /** A Cloud Storage object or folder URI that specifies the source from which to import metadata. It must begin with gs://. */
		gcsUri: Option[String] = None,
	  /** The name of the source database. */
		sourceDatabase: Option[String] = None,
	  /** Optional. The type of the database dump. If unspecified, defaults to MYSQL. */
		`type`: Option[Schema.DatabaseDump.TypeEnum] = None
	)
	
	object ExportMetadataRequest {
		enum DatabaseDumpTypeEnum extends Enum[DatabaseDumpTypeEnum] { case TYPE_UNSPECIFIED, MYSQL, AVRO }
	}
	case class ExportMetadataRequest(
	  /** A Cloud Storage URI of a folder, in the format gs:///. A sub-folder containing exported files will be created below it. */
		destinationGcsFolder: Option[String] = None,
	  /** Optional. A request ID. Specify a unique request ID to allow the server to ignore the request if it has completed. The server will ignore subsequent requests that provide a duplicate request ID for at least 60 minutes after the first request.For example, if an initial request times out, followed by another request with the same request ID, the server ignores the second request to prevent the creation of duplicate commitments.The request ID must be a valid UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier#Format). A zero UUID (00000000-0000-0000-0000-000000000000) is not supported. */
		requestId: Option[String] = None,
	  /** Optional. The type of the database dump. If unspecified, defaults to MYSQL. */
		databaseDumpType: Option[Schema.ExportMetadataRequest.DatabaseDumpTypeEnum] = None
	)
	
	object RestoreServiceRequest {
		enum RestoreTypeEnum extends Enum[RestoreTypeEnum] { case RESTORE_TYPE_UNSPECIFIED, FULL, METADATA_ONLY }
	}
	case class RestoreServiceRequest(
	  /** Optional. The relative resource name of the metastore service backup to restore from, in the following form:projects/{project_id}/locations/{location_id}/services/{service_id}/backups/{backup_id}. Mutually exclusive with backup_location, and exactly one of the two must be set. */
		backup: Option[String] = None,
	  /** Optional. A Cloud Storage URI specifying the location of the backup artifacts, namely - backup avro files under "avro/", backup_metastore.json and service.json, in the following form:gs://. Mutually exclusive with backup, and exactly one of the two must be set. */
		backupLocation: Option[String] = None,
	  /** Optional. The type of restore. If unspecified, defaults to METADATA_ONLY. */
		restoreType: Option[Schema.RestoreServiceRequest.RestoreTypeEnum] = None,
	  /** Optional. A request ID. Specify a unique request ID to allow the server to ignore the request if it has completed. The server will ignore subsequent requests that provide a duplicate request ID for at least 60 minutes after the first request.For example, if an initial request times out, followed by another request with the same request ID, the server ignores the second request to prevent the creation of duplicate commitments.The request ID must be a valid UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier#Format). A zero UUID (00000000-0000-0000-0000-000000000000) is not supported. */
		requestId: Option[String] = None
	)
	
	case class ListBackupsResponse(
	  /** The backups of the specified service. */
		backups: Option[List[Schema.Backup]] = None,
	  /** A token that can be sent as page_token to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Backup {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, DELETING, ACTIVE, FAILED, RESTORING }
	}
	case class Backup(
	  /** Immutable. The relative resource name of the backup, in the following form:projects/{project_number}/locations/{location_id}/services/{service_id}/backups/{backup_id} */
		name: Option[String] = None,
	  /** Output only. The time when the backup was started. */
		createTime: Option[String] = None,
	  /** Output only. The time when the backup finished creating. */
		endTime: Option[String] = None,
	  /** Output only. The current state of the backup. */
		state: Option[Schema.Backup.StateEnum] = None,
	  /** Output only. The revision of the service at the time of backup. */
		serviceRevision: Option[Schema.Service] = None,
	  /** The description of the backup. */
		description: Option[String] = None,
	  /** Output only. Services that are restoring from the backup. */
		restoringServices: Option[List[String]] = None
	)
	
	case class QueryMetadataRequest(
	  /** Required. A read-only SQL query to execute against the metadata database. The query cannot change or mutate the data. */
		query: Option[String] = None
	)
	
	case class MoveTableToDatabaseRequest(
	  /** Required. The name of the table to be moved. */
		tableName: Option[String] = None,
	  /** Required. The name of the database where the table resides. */
		dbName: Option[String] = None,
	  /** Required. The name of the database where the table should be moved. */
		destinationDbName: Option[String] = None
	)
	
	case class AlterMetadataResourceLocationRequest(
	  /** Required. The relative metadata resource name in the following format.databases/{database_id} or databases/{database_id}/tables/{table_id} or databases/{database_id}/tables/{table_id}/partitions/{partition_id} */
		resourceName: Option[String] = None,
	  /** Required. The new location URI for the metadata resource. */
		locationUri: Option[String] = None
	)
	
	case class AlterTablePropertiesRequest(
	  /** Required. The name of the table containing the properties you're altering in the following format.databases/{database_id}/tables/{table_id} */
		tableName: Option[String] = None,
	  /** A field mask that specifies the metadata table properties that are overwritten by the update. Fields specified in the update_mask are relative to the resource (not to the full request). A field is overwritten if it is in the mask.For example, given the target properties: properties { a: 1 b: 2 } And an update properties: properties { a: 2 b: 3 c: 4 } then if the field mask is:paths: "properties.b", "properties.c"then the result will be: properties { a: 1 b: 3 c: 4 }  */
		updateMask: Option[String] = None,
	  /** A map that describes the desired values to mutate. If update_mask is empty, the properties will not update. Otherwise, the properties only alters the value whose associated paths exist in the update mask */
		properties: Option[Map[String, String]] = None
	)
	
	case class StartMigrationRequest(
	  /** Required. The configuration details for the migration. */
		migrationExecution: Option[Schema.MigrationExecution] = None,
	  /** Optional. A request ID. Specify a unique request ID to allow the server to ignore the request if it has completed. The server will ignore subsequent requests that provide a duplicate request ID for at least 60 minutes after the first request.For example, if an initial request times out, followed by another request with the same request ID, the server ignores the second request to prevent the creation of duplicate commitments.The request ID must be a valid UUID (https://en.wikipedia.org/wiki/Universally_unique_identifier#Format) A zero UUID (00000000-0000-0000-0000-000000000000) is not supported. */
		requestId: Option[String] = None
	)
	
	object MigrationExecution {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STARTING, RUNNING, CANCELLING, AWAITING_USER_ACTION, SUCCEEDED, FAILED, CANCELLED, DELETING }
		enum PhaseEnum extends Enum[PhaseEnum] { case PHASE_UNSPECIFIED, REPLICATION, CUTOVER }
	}
	case class MigrationExecution(
	  /** Configuration information specific to migrating from self-managed hive metastore on Google Cloud using Cloud SQL as the backend database to Dataproc Metastore. */
		cloudSqlMigrationConfig: Option[Schema.CloudSQLMigrationConfig] = None,
	  /** Output only. The relative resource name of the migration execution, in the following form: projects/{project_number}/locations/{location_id}/services/{service_id}/migrationExecutions/{migration_execution_id} */
		name: Option[String] = None,
	  /** Output only. The time when the migration execution was started. */
		createTime: Option[String] = None,
	  /** Output only. The time when the migration execution finished. */
		endTime: Option[String] = None,
	  /** Output only. The current state of the migration execution. */
		state: Option[Schema.MigrationExecution.StateEnum] = None,
	  /** Output only. The current phase of the migration execution. */
		phase: Option[Schema.MigrationExecution.PhaseEnum] = None,
	  /** Output only. Additional information about the current state of the migration execution. */
		stateMessage: Option[String] = None
	)
	
	case class CloudSQLMigrationConfig(
	  /** Required. Configuration information to start the Change Data Capture (CDC) streams from customer database to backend database of Dataproc Metastore. Dataproc Metastore switches to using its backend database after the cutover phase of migration. */
		cdcConfig: Option[Schema.CdcConfig] = None,
	  /** Required. Configuration information to establish customer database connection before the cutover phase of migration */
		cloudSqlConnectionConfig: Option[Schema.CloudSQLConnectionConfig] = None
	)
	
	case class CdcConfig(
	  /** Required. Fully qualified name of the Cloud SQL instance's VPC network or the shared VPC network that Datastream will peer to, in the following format: projects/{project_id}/locations/global/networks/{network_id}. More context in https://cloud.google.com/datastream/docs/network-connectivity-options#privateconnectivity */
		vpcNetwork: Option[String] = None,
	  /** Required. A /29 CIDR IP range for peering with datastream. */
		subnetIpRange: Option[String] = None,
	  /** Required. The username that the Datastream service should use for the MySQL connection. */
		username: Option[String] = None,
	  /** Required. Input only. The password for the user that Datastream service should use for the MySQL connection. This field is not returned on request. */
		password: Option[String] = None,
	  /** Required. The URL of the subnetwork resource to create the VM instance hosting the reverse proxy in. More context in https://cloud.google.com/datastream/docs/private-connectivity#reverse-csql-proxy The subnetwork should reside in the network provided in the request that Datastream will peer to and should be in the same region as Datastream, in the following format. projects/{project_id}/regions/{region_id}/subnetworks/{subnetwork_id} */
		reverseProxySubnet: Option[String] = None,
	  /** Optional. The bucket to write the intermediate stream event data in. The bucket name must be without any prefix like "gs://". See the bucket naming requirements (https://cloud.google.com/storage/docs/buckets#naming). This field is optional. If not set, the Artifacts Cloud Storage bucket will be used. */
		bucket: Option[String] = None,
	  /** Optional. The root path inside the Cloud Storage bucket. The stream event data will be written to this path. The default value is /migration. */
		rootPath: Option[String] = None
	)
	
	case class CloudSQLConnectionConfig(
	  /** Required. Cloud SQL database connection name (project_id:region:instance_name) */
		instanceConnectionName: Option[String] = None,
	  /** Required. The private IP address of the Cloud SQL instance. */
		ipAddress: Option[String] = None,
	  /** Required. The network port of the database. */
		port: Option[Int] = None,
	  /** Required. The hive database name. */
		hiveDatabaseName: Option[String] = None,
	  /** Required. The username that Dataproc Metastore service will use to connect to the database. */
		username: Option[String] = None,
	  /** Required. Input only. The password for the user that Dataproc Metastore service will be using to connect to the database. This field is not returned on request. */
		password: Option[String] = None,
	  /** Required. The relative resource name of the subnetwork to deploy the SOCKS5 proxy service in. The subnetwork should reside in a network through which the Cloud SQL instance is accessible. The resource name should be in the format, projects/{project_id}/regions/{region_id}/subnetworks/{subnetwork_id} */
		proxySubnet: Option[String] = None,
	  /** Required. The relative resource name of the subnetwork to be used for Private Service Connect. Note that this cannot be a regular subnet and is used only for NAT. (https://cloud.google.com/vpc/docs/about-vpc-hosted-services#psc-subnets) This subnet is used to publish the SOCKS5 proxy service. The subnet size must be at least /29 and it should reside in a network through which the Cloud SQL instance is accessible. The resource name should be in the format, projects/{project_id}/regions/{region_id}/subnetworks/{subnetwork_id} */
		natSubnet: Option[String] = None
	)
	
	case class CompleteMigrationRequest(
	
	)
	
	case class CancelMigrationRequest(
	
	)
	
	case class ListMigrationExecutionsResponse(
	  /** The migration executions on the specified service. */
		migrationExecutions: Option[List[Schema.MigrationExecution]] = None,
	  /** A token that can be sent as page_token to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ListLocationsResponse(
	  /** A list of locations that matches the specified filter in the request. */
		locations: Option[List[Schema.Location]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Location(
	  /** Resource name for the location, which may vary between implementations. For example: "projects/example-project/locations/us-east1" */
		name: Option[String] = None,
	  /** The canonical id for this location. For example: "us-east1". */
		locationId: Option[String] = None,
	  /** The friendly name for this location, typically a nearby city name. For example, "Tokyo". */
		displayName: Option[String] = None,
	  /** Cross-service attributes for the location. For example {"cloud.googleapis.com/region": "us-east1"}  */
		labels: Option[Map[String, String]] = None,
	  /** Service-specific metadata. For example the available capacity at the given location. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the resource. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None,
	  /** OPTIONAL: A FieldMask specifying which fields of the policy to modify. Only the fields in the mask will be modified. If no mask is provided, the following default mask is used:paths: "bindings, etag" */
		updateMask: Option[String] = None
	)
	
	case class Policy(
	  /** Specifies the format of the policy.Valid values are 0, 1, and 3. Requests that specify an invalid value are rejected.Any operation that affects conditional role bindings must specify version 3. This requirement applies to the following operations: Getting a policy that includes a conditional role binding Adding a conditional role binding to a policy Changing a conditional role binding in a policy Removing any role binding, with or without a condition, from a policy that includes conditionsImportant: If you use IAM Conditions, you must include the etag field whenever you call setIamPolicy. If you omit this field, then IAM allows you to overwrite a version 3 policy with a version 1 policy, and all of the conditions in the version 3 policy are lost.If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset.To learn which resources support conditions in their IAM policies, see the IAM documentation (https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of members, or principals, with a role. Optionally, may specify a condition that determines how and when the bindings are applied. Each of the bindings must contain at least one principal.The bindings in a Policy can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the bindings grant 50 different roles to user:alice@example.com, and not to any other principal, then you can add another 1,450 principals to the bindings in the Policy. */
		bindings: Option[List[Schema.Binding]] = None,
	  /** Specifies cloud audit logging configuration for this policy. */
		auditConfigs: Option[List[Schema.AuditConfig]] = None,
	  /** etag is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the etag in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An etag is returned in the response to getIamPolicy, and systems are expected to put that etag in the request to setIamPolicy to ensure that their change will be applied to the same version of the policy.Important: If you use IAM Conditions, you must include the etag field whenever you call setIamPolicy. If you omit this field, then IAM allows you to overwrite a version 3 policy with a version 1 policy, and all of the conditions in the version 3 policy are lost. */
		etag: Option[String] = None
	)
	
	case class Binding(
	  /** Role that is assigned to the list of members, or principals. For example, roles/viewer, roles/editor, or roles/owner.For an overview of the IAM roles and permissions, see the IAM documentation (https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see here (https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. members can have the following values: allUsers: A special identifier that represents anyone who is on the internet; with or without a Google account. allAuthenticatedUsers: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. user:{emailid}: An email address that represents a specific Google account. For example, alice@example.com . serviceAccount:{emailid}: An email address that represents a Google service account. For example, my-other-app@appspot.gserviceaccount.com. serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]: An identifier for a Kubernetes service account (https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, my-project.svc.id.goog[my-namespace/my-kubernetes-sa]. group:{emailid}: An email address that represents a Google group. For example, admins@example.com. domain:{domain}: The G Suite domain (primary) that represents all the users of that domain. For example, google.com or example.com. principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}: A single identity in a workforce identity pool. principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}: All workforce identities in a group. principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}: All workforce identities with a specific attribute value. principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;: All identities in a workforce identity pool. principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}: A single identity in a workload identity pool. principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}: A workload identity pool group. principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}: All identities in a workload identity pool with a certain attribute. principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;: All identities in a workload identity pool. deleted:user:{emailid}?uid={uniqueid}: An email address (plus unique identifier) representing a user that has been recently deleted. For example, alice@example.com?uid=123456789012345678901. If the user is recovered, this value reverts to user:{emailid} and the recovered user retains the role in the binding. deleted:serviceAccount:{emailid}?uid={uniqueid}: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901. If the service account is undeleted, this value reverts to serviceAccount:{emailid} and the undeleted service account retains the role in the binding. deleted:group:{emailid}?uid={uniqueid}: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, admins@example.com?uid=123456789012345678901. If the group is recovered, this value reverts to group:{emailid} and the recovered group retains the role in the binding. deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}: Deleted single identity in a workforce identity pool. For example, deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value. */
		members: Option[List[String]] = None,
	  /** The condition that is associated with this binding.If the condition evaluates to true, then this binding applies to the current request.If the condition evaluates to false, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding.To learn which resources support conditions in their IAM policies, see the IAM documentation (https://cloud.google.com/iam/help/conditions/resource-policies). */
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
	  /** Specifies a service that will be enabled for audit logging. For example, storage.googleapis.com, cloudsql.googleapis.com. allServices is a special value that covers all services. */
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
	  /** The set of permissions to check for the resource. Permissions with wildcards (such as &#42; or storage.&#42;) are not allowed. For more information see IAM Overview (https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of TestPermissionsRequest.permissions that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class LocationMetadata(
	  /** The versions of Hive Metastore that can be used when creating a new metastore service in this location. The server guarantees that exactly one HiveMetastoreVersion in the list will set is_default. */
		supportedHiveMetastoreVersions: Option[List[Schema.HiveMetastoreVersion]] = None,
	  /** The multi-region metadata if the current region is a multi-region. */
		multiRegionMetadata: Option[Schema.MultiRegionMetadata] = None,
	  /** Possible configurations supported if the current region is a custom region. */
		customRegionMetadata: Option[List[Schema.CustomRegionMetadata]] = None
	)
	
	case class HiveMetastoreVersion(
	  /** The semantic version of the Hive Metastore software. */
		version: Option[String] = None,
	  /** Whether version will be chosen by the server if a metastore service is created with a HiveMetastoreConfig that omits the version. */
		isDefault: Option[Boolean] = None
	)
	
	case class MultiRegionMetadata(
	  /** The regions constituting the multi-region. */
		constituentRegions: Option[List[String]] = None
	)
	
	case class CustomRegionMetadata(
	  /** The read-write regions for this custom region. */
		requiredReadWriteRegions: Option[List[String]] = None,
	  /** The read-only regions for this custom region. */
		optionalReadOnlyRegions: Option[List[String]] = None,
	  /** The Spanner witness region for this custom region. */
		witnessRegion: Option[String] = None
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
	  /** Output only. Identifies whether the caller has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to Code.CANCELLED. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	case class QueryMetadataResponse(
	  /** The manifest URI is link to a JSON instance in Cloud Storage. This instance manifests immediately along with QueryMetadataResponse. The content of the URI is not retriable until the long-running operation query against the metadata finishes. */
		resultManifestUri: Option[String] = None
	)
	
	case class MoveTableToDatabaseResponse(
	
	)
	
	case class AlterMetadataResourceLocationResponse(
	
	)
	
	case class ErrorDetails(
	  /** Additional structured details about this error.Keys define the failure items. Value describes the exception or details of the item. */
		details: Option[Map[String, String]] = None
	)
}
