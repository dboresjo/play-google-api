package com.boresjo.play.api.google.sqladmin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object Operation {
		enum StatusEnum extends Enum[StatusEnum] { case SQL_OPERATION_STATUS_UNSPECIFIED, PENDING, RUNNING, DONE }
		enum OperationTypeEnum extends Enum[OperationTypeEnum] { case SQL_OPERATION_TYPE_UNSPECIFIED, IMPORT, EXPORT, CREATE, UPDATE, DELETE, RESTART, BACKUP, SNAPSHOT, BACKUP_VOLUME, DELETE_VOLUME, RESTORE_VOLUME, INJECT_USER, CLONE, STOP_REPLICA, START_REPLICA, PROMOTE_REPLICA, CREATE_REPLICA, CREATE_USER, DELETE_USER, UPDATE_USER, CREATE_DATABASE, DELETE_DATABASE, UPDATE_DATABASE, FAILOVER, DELETE_BACKUP, RECREATE_REPLICA, TRUNCATE_LOG, DEMOTE_MASTER, MAINTENANCE, ENABLE_PRIVATE_IP, DEFER_MAINTENANCE, CREATE_CLONE, RESCHEDULE_MAINTENANCE, START_EXTERNAL_SYNC, LOG_CLEANUP, AUTO_RESTART, REENCRYPT, SWITCHOVER, ACQUIRE_SSRS_LEASE, RELEASE_SSRS_LEASE, RECONFIGURE_OLD_PRIMARY, CLUSTER_MAINTENANCE, SELF_SERVICE_MAINTENANCE, SWITCHOVER_TO_REPLICA, MAJOR_VERSION_UPGRADE, ADVANCED_BACKUP }
	}
	case class Operation(
	  /** This is always `sql#operation`. */
		kind: Option[String] = None,
		targetLink: Option[String] = None,
	  /** The status of an operation. */
		status: Option[Schema.Operation.StatusEnum] = None,
	  /** The email address of the user who initiated this operation. */
		user: Option[String] = None,
	  /** The time this operation was enqueued in UTC timezone in [RFC 3339](https://tools.ietf.org/html/rfc3339) format, for example `2012-11-15T16:19:00.094Z`. */
		insertTime: Option[String] = None,
	  /** The time this operation actually started in UTC timezone in [RFC 3339](https://tools.ietf.org/html/rfc3339) format, for example `2012-11-15T16:19:00.094Z`. */
		startTime: Option[String] = None,
	  /** The time this operation finished in UTC timezone in [RFC 3339](https://tools.ietf.org/html/rfc3339) format, for example `2012-11-15T16:19:00.094Z`. */
		endTime: Option[String] = None,
	  /** If errors occurred during processing of this operation, this field will be populated. */
		error: Option[Schema.OperationErrors] = None,
	  /** An Admin API warning message. */
		apiWarning: Option[Schema.ApiWarning] = None,
	  /** The type of the operation. Valid values are: &#42; `CREATE` &#42; `DELETE` &#42; `UPDATE` &#42; `RESTART` &#42; `IMPORT` &#42; `EXPORT` &#42; `BACKUP_VOLUME` &#42; `RESTORE_VOLUME` &#42; `CREATE_USER` &#42; `DELETE_USER` &#42; `CREATE_DATABASE` &#42; `DELETE_DATABASE` */
		operationType: Option[Schema.Operation.OperationTypeEnum] = None,
	  /** The context for import operation, if applicable. */
		importContext: Option[Schema.ImportContext] = None,
	  /** The context for export operation, if applicable. */
		exportContext: Option[Schema.ExportContext] = None,
	  /** The context for backup operation, if applicable. */
		backupContext: Option[Schema.BackupContext] = None,
	  /** An identifier that uniquely identifies the operation. You can use this identifier to retrieve the Operations resource that has information about the operation. */
		name: Option[String] = None,
	  /** Name of the database instance related to this operation. */
		targetId: Option[String] = None,
	  /** The URI of this resource. */
		selfLink: Option[String] = None,
	  /** The project ID of the target instance related to this operation. */
		targetProject: Option[String] = None,
	  /** The context for acquire SSRS lease operation, if applicable. */
		acquireSsrsLeaseContext: Option[Schema.AcquireSsrsLeaseContext] = None
	)
	
	case class OperationErrors(
	  /** This is always `sql#operationErrors`. */
		kind: Option[String] = None,
	  /** The list of errors encountered while processing this operation. */
		errors: Option[List[Schema.OperationError]] = None
	)
	
	case class OperationError(
	  /** This is always `sql#operationError`. */
		kind: Option[String] = None,
	  /** Identifies the specific error that occurred. */
		code: Option[String] = None,
	  /** Additional information about the error encountered. */
		message: Option[String] = None
	)
	
	object ApiWarning {
		enum CodeEnum extends Enum[CodeEnum] { case SQL_API_WARNING_CODE_UNSPECIFIED, REGION_UNREACHABLE, MAX_RESULTS_EXCEEDS_LIMIT, COMPROMISED_CREDENTIALS, INTERNAL_STATE_FAILURE }
	}
	case class ApiWarning(
	  /** Code to uniquely identify the warning type. */
		code: Option[Schema.ApiWarning.CodeEnum] = None,
	  /** The warning message. */
		message: Option[String] = None,
	  /** The region name for REGION_UNREACHABLE warning. */
		region: Option[String] = None
	)
	
	object ImportContext {
		enum FileTypeEnum extends Enum[FileTypeEnum] { case SQL_FILE_TYPE_UNSPECIFIED, SQL, CSV, BAK }
		case class CsvImportOptionsItem(
		  /** The table to which CSV data is imported. */
			table: Option[String] = None,
		  /** The columns to which CSV data is imported. If not specified, all columns of the database table are loaded with CSV data. */
			columns: Option[List[String]] = None,
		  /** Specifies the character that should appear before a data character that needs to be escaped. */
			escapeCharacter: Option[String] = None,
		  /** Specifies the quoting character to be used when a data value is quoted. */
			quoteCharacter: Option[String] = None,
		  /** Specifies the character that separates columns within each row (line) of the file. */
			fieldsTerminatedBy: Option[String] = None,
		  /** This is used to separate lines. If a line does not contain all fields, the rest of the columns are set to their default values. */
			linesTerminatedBy: Option[String] = None
		)
		
		object BakImportOptionsItem {
			case class EncryptionOptionsItem(
			  /** Path to the Certificate (.cer) in Cloud Storage, in the form `gs://bucketName/fileName`. The instance must have write permissions to the bucket and read access to the file. */
				certPath: Option[String] = None,
			  /** Path to the Certificate Private Key (.pvk) in Cloud Storage, in the form `gs://bucketName/fileName`. The instance must have write permissions to the bucket and read access to the file. */
				pvkPath: Option[String] = None,
			  /** Password that encrypts the private key */
				pvkPassword: Option[String] = None
			)
			
			enum BakTypeEnum extends Enum[BakTypeEnum] { case BAK_TYPE_UNSPECIFIED, FULL, DIFF, TLOG }
		}
		case class BakImportOptionsItem(
			encryptionOptions: Option[Schema.ImportContext.BakImportOptionsItem.EncryptionOptionsItem] = None,
		  /** Whether or not the backup set being restored is striped. Applies only to Cloud SQL for SQL Server. */
			striped: Option[Boolean] = None,
		  /** Whether or not the backup importing will restore database with NORECOVERY option Applies only to Cloud SQL for SQL Server. */
			noRecovery: Option[Boolean] = None,
		  /** Whether or not the backup importing request will just bring database online without downloading Bak content only one of "no_recovery" and "recovery_only" can be true otherwise error will return. Applies only to Cloud SQL for SQL Server. */
			recoveryOnly: Option[Boolean] = None,
		  /** Type of the bak content, FULL or DIFF */
			bakType: Option[Schema.ImportContext.BakImportOptionsItem.BakTypeEnum] = None,
		  /** Optional. The timestamp when the import should stop. This timestamp is in the [RFC 3339](https://tools.ietf.org/html/rfc3339) format (for example, `2023-10-01T16:19:00.094`). This field is equivalent to the STOPAT keyword and applies to Cloud SQL for SQL Server only. */
			stopAt: Option[String] = None,
		  /** Optional. The marked transaction where the import should stop. This field is equivalent to the STOPATMARK keyword and applies to Cloud SQL for SQL Server only. */
			stopAtMark: Option[String] = None
		)
		
		object SqlImportOptionsItem {
			case class PostgresImportOptionsItem(
			  /** Optional. The --clean flag for the pg_restore utility. This flag applies only if you enabled Cloud SQL to import files in parallel. */
				clean: Option[Boolean] = None,
			  /** Optional. The --if-exists flag for the pg_restore utility. This flag applies only if you enabled Cloud SQL to import files in parallel. */
				ifExists: Option[Boolean] = None
			)
		}
		case class SqlImportOptionsItem(
		  /** Optional. The number of threads to use for parallel import. */
			threads: Option[Int] = None,
		  /** Optional. Whether or not the import should be parallel. */
			parallel: Option[Boolean] = None,
		  /** Optional. Options for importing from a Cloud SQL for PostgreSQL instance. */
			postgresImportOptions: Option[Schema.ImportContext.SqlImportOptionsItem.PostgresImportOptionsItem] = None
		)
	}
	case class ImportContext(
	  /** Path to the import file in Cloud Storage, in the form `gs://bucketName/fileName`. Compressed gzip files (.gz) are supported when `fileType` is `SQL`. The instance must have write permissions to the bucket and read access to the file. */
		uri: Option[String] = None,
	  /** The target database for the import. If `fileType` is `SQL`, this field is required only if the import file does not specify a database, and is overridden by any database specification in the import file. If `fileType` is `CSV`, one database must be specified. */
		database: Option[String] = None,
	  /** This is always `sql#importContext`. */
		kind: Option[String] = None,
	  /** The file type for the specified uri.\`SQL`: The file contains SQL statements. \`CSV`: The file contains CSV data. */
		fileType: Option[Schema.ImportContext.FileTypeEnum] = None,
	  /** Options for importing data as CSV. */
		csvImportOptions: Option[Schema.ImportContext.CsvImportOptionsItem] = None,
	  /** The PostgreSQL user for this import operation. PostgreSQL instances only. */
		importUser: Option[String] = None,
	  /** Import parameters specific to SQL Server .BAK files */
		bakImportOptions: Option[Schema.ImportContext.BakImportOptionsItem] = None,
	  /** Optional. Options for importing data from SQL statements. */
		sqlImportOptions: Option[Schema.ImportContext.SqlImportOptionsItem] = None
	)
	
	object ExportContext {
		object SqlExportOptionsItem {
			case class MysqlExportOptionsItem(
			  /** Option to include SQL statement required to set up replication. If set to `1`, the dump file includes a CHANGE MASTER TO statement with the binary log coordinates, and --set-gtid-purged is set to ON. If set to `2`, the CHANGE MASTER TO statement is written as a SQL comment and has no effect. If set to any value other than `1`, --set-gtid-purged is set to OFF. */
				masterData: Option[Int] = None
			)
			
			case class PostgresExportOptionsItem(
			  /** Optional. Use this option to include DROP SQL statements. These statements are used to delete database objects before running the import operation. */
				clean: Option[Boolean] = None,
			  /** Optional. Option to include an IF EXISTS SQL statement with each DROP statement produced by clean. */
				ifExists: Option[Boolean] = None
			)
		}
		case class SqlExportOptionsItem(
		  /** Tables to export, or that were exported, from the specified database. If you specify tables, specify one and only one database. For PostgreSQL instances, you can specify only one table. */
			tables: Option[List[String]] = None,
		  /** Export only schemas. */
			schemaOnly: Option[Boolean] = None,
		  /** Options for exporting from MySQL. */
			mysqlExportOptions: Option[Schema.ExportContext.SqlExportOptionsItem.MysqlExportOptionsItem] = None,
		  /** Optional. The number of threads to use for parallel export. */
			threads: Option[Int] = None,
		  /** Optional. Whether or not the export should be parallel. */
			parallel: Option[Boolean] = None,
		  /** Options for exporting from a Cloud SQL for PostgreSQL instance. */
			postgresExportOptions: Option[Schema.ExportContext.SqlExportOptionsItem.PostgresExportOptionsItem] = None
		)
		
		case class CsvExportOptionsItem(
		  /** The select query used to extract the data. */
			selectQuery: Option[String] = None,
		  /** Specifies the character that should appear before a data character that needs to be escaped. */
			escapeCharacter: Option[String] = None,
		  /** Specifies the quoting character to be used when a data value is quoted. */
			quoteCharacter: Option[String] = None,
		  /** Specifies the character that separates columns within each row (line) of the file. */
			fieldsTerminatedBy: Option[String] = None,
		  /** This is used to separate lines. If a line does not contain all fields, the rest of the columns are set to their default values. */
			linesTerminatedBy: Option[String] = None
		)
		
		enum FileTypeEnum extends Enum[FileTypeEnum] { case SQL_FILE_TYPE_UNSPECIFIED, SQL, CSV, BAK }
		object BakExportOptionsItem {
			enum BakTypeEnum extends Enum[BakTypeEnum] { case BAK_TYPE_UNSPECIFIED, FULL, DIFF, TLOG }
		}
		case class BakExportOptionsItem(
		  /** Whether or not the export should be striped. */
			striped: Option[Boolean] = None,
		  /** Option for specifying how many stripes to use for the export. If blank, and the value of the striped field is true, the number of stripes is automatically chosen. */
			stripeCount: Option[Int] = None,
		  /** Type of this bak file will be export, FULL or DIFF, SQL Server only */
			bakType: Option[Schema.ExportContext.BakExportOptionsItem.BakTypeEnum] = None,
		  /** Deprecated: copy_only is deprecated. Use differential_base instead */
			copyOnly: Option[Boolean] = None,
		  /** Whether or not the backup can be used as a differential base copy_only backup can not be served as differential base */
			differentialBase: Option[Boolean] = None,
		  /** Optional. The begin timestamp when transaction log will be included in the export operation. [RFC 3339](https://tools.ietf.org/html/rfc3339) format (for example, `2023-10-01T16:19:00.094`) in UTC. When omitted, all available logs from the beginning of retention period will be included. Only applied to Cloud SQL for SQL Server. */
			exportLogStartTime: Option[String] = None,
		  /** Optional. The end timestamp when transaction log will be included in the export operation. [RFC 3339](https://tools.ietf.org/html/rfc3339) format (for example, `2023-10-01T16:19:00.094`) in UTC. When omitted, all available logs until current time will be included. Only applied to Cloud SQL for SQL Server. */
			exportLogEndTime: Option[String] = None
		)
	}
	case class ExportContext(
	  /** The path to the file in Google Cloud Storage where the export will be stored. The URI is in the form `gs://bucketName/fileName`. If the file already exists, the request succeeds, but the operation fails. If `fileType` is `SQL` and the filename ends with .gz, the contents are compressed. */
		uri: Option[String] = None,
	  /** Databases to be exported. `MySQL instances:` If `fileType` is `SQL` and no database is specified, all databases are exported, except for the `mysql` system database. If `fileType` is `CSV`, you can specify one database, either by using this property or by using the `csvExportOptions.selectQuery` property, which takes precedence over this property. `PostgreSQL instances:` You must specify one database to be exported. If `fileType` is `CSV`, this database must match the one specified in the `csvExportOptions.selectQuery` property. `SQL Server instances:` You must specify one database to be exported, and the `fileType` must be `BAK`. */
		databases: Option[List[String]] = None,
	  /** This is always `sql#exportContext`. */
		kind: Option[String] = None,
	  /** Options for exporting data as SQL statements. */
		sqlExportOptions: Option[Schema.ExportContext.SqlExportOptionsItem] = None,
	  /** Options for exporting data as CSV. `MySQL` and `PostgreSQL` instances only. */
		csvExportOptions: Option[Schema.ExportContext.CsvExportOptionsItem] = None,
	  /** The file type for the specified uri. */
		fileType: Option[Schema.ExportContext.FileTypeEnum] = None,
	  /** Option for export offload. */
		offload: Option[Boolean] = None,
	  /** Options for exporting BAK files (SQL Server-only) */
		bakExportOptions: Option[Schema.ExportContext.BakExportOptionsItem] = None
	)
	
	case class BackupContext(
	  /** The identifier of the backup. */
		backupId: Option[String] = None,
	  /** This is always `sql#backupContext`. */
		kind: Option[String] = None
	)
	
	case class AcquireSsrsLeaseContext(
	  /** The username to be used as the setup login to connect to the database server for SSRS setup. */
		setupLogin: Option[String] = None,
	  /** The username to be used as the service login to connect to the report database for SSRS setup. */
		serviceLogin: Option[String] = None,
	  /** The report database to be used for SSRS setup. */
		reportDatabase: Option[String] = None,
	  /** Lease duration needed for SSRS setup. */
		duration: Option[String] = None
	)
	
	case class InstancesCloneRequest(
	  /** Contains details about the clone operation. */
		cloneContext: Option[Schema.CloneContext] = None
	)
	
	case class CloneContext(
	  /** This is always `sql#cloneContext`. */
		kind: Option[String] = None,
	  /** Reserved for future use. */
		pitrTimestampMs: Option[String] = None,
	  /** Name of the Cloud SQL instance to be created as a clone. */
		destinationInstanceName: Option[String] = None,
	  /** Binary log coordinates, if specified, identify the position up to which the source instance is cloned. If not specified, the source instance is cloned up to the most recent binary log coordinates. */
		binLogCoordinates: Option[Schema.BinLogCoordinates] = None,
	  /** Timestamp, if specified, identifies the time to which the source instance is cloned. */
		pointInTime: Option[String] = None,
	  /** The name of the allocated ip range for the private ip Cloud SQL instance. For example: "google-managed-services-default". If set, the cloned instance ip will be created in the allocated range. The range name must comply with [RFC 1035](https://tools.ietf.org/html/rfc1035). Specifically, the name must be 1-63 characters long and match the regular expression [a-z]([-a-z0-9]&#42;[a-z0-9])?. Reserved for future use. */
		allocatedIpRange: Option[String] = None,
	  /** (SQL Server only) Clone only the specified databases from the source instance. Clone all databases if empty. */
		databaseNames: Option[List[String]] = None,
	  /** Optional. Copy clone and point-in-time recovery clone of an instance to the specified zone. If no zone is specified, clone to the same primary zone as the source instance. This field applies to all DB types. */
		preferredZone: Option[String] = None,
	  /** Optional. Copy clone and point-in-time recovery clone of a regional instance in the specified zones. If not specified, clone to the same secondary zone as the source instance. This value cannot be the same as the preferred_zone field. This field applies to all DB types. */
		preferredSecondaryZone: Option[String] = None
	)
	
	case class BinLogCoordinates(
	  /** Name of the binary log file for a Cloud SQL instance. */
		binLogFileName: Option[String] = None,
	  /** Position (offset) within the binary log file. */
		binLogPosition: Option[String] = None,
	  /** This is always `sql#binLogCoordinates`. */
		kind: Option[String] = None
	)
	
	case class InstancesDemoteMasterRequest(
	  /** Contains details about the demoteMaster operation. */
		demoteMasterContext: Option[Schema.DemoteMasterContext] = None
	)
	
	case class DemoteMasterContext(
	  /** This is always `sql#demoteMasterContext`. */
		kind: Option[String] = None,
	  /** Verify the GTID consistency for demote operation. Default value: `True`. Setting this flag to `false` enables you to bypass the GTID consistency check between on-premises primary instance and Cloud SQL instance during the demotion operation but also exposes you to the risk of future replication failures. Change the value only if you know the reason for the GTID divergence and are confident that doing so will not cause any replication issues. */
		verifyGtidConsistency: Option[Boolean] = None,
	  /** The name of the instance which will act as on-premises primary instance in the replication setup. */
		masterInstanceName: Option[String] = None,
	  /** Configuration specific to read-replicas replicating from the on-premises primary instance. */
		replicaConfiguration: Option[Schema.DemoteMasterConfiguration] = None,
	  /** Flag to skip replication setup on the instance. */
		skipReplicationSetup: Option[Boolean] = None
	)
	
	case class DemoteMasterConfiguration(
	  /** This is always `sql#demoteMasterConfiguration`. */
		kind: Option[String] = None,
	  /** MySQL specific configuration when replicating from a MySQL on-premises primary instance. Replication configuration information such as the username, password, certificates, and keys are not stored in the instance metadata. The configuration information is used only to set up the replication connection and is stored by MySQL in a file named `master.info` in the data directory. */
		mysqlReplicaConfiguration: Option[Schema.DemoteMasterMySqlReplicaConfiguration] = None
	)
	
	case class DemoteMasterMySqlReplicaConfiguration(
	  /** This is always `sql#demoteMasterMysqlReplicaConfiguration`. */
		kind: Option[String] = None,
	  /** The username for the replication connection. */
		username: Option[String] = None,
	  /** The password for the replication connection. */
		password: Option[String] = None,
	  /** PEM representation of the replica's private key. The corresponsing public key is encoded in the client's certificate. The format of the replica's private key can be either PKCS #1 or PKCS #8. */
		clientKey: Option[String] = None,
	  /** PEM representation of the replica's x509 certificate. */
		clientCertificate: Option[String] = None,
	  /** PEM representation of the trusted CA's x509 certificate. */
		caCertificate: Option[String] = None
	)
	
	case class InstancesDemoteRequest(
	  /** Required. Contains details about the demote operation. */
		demoteContext: Option[Schema.DemoteContext] = None
	)
	
	case class DemoteContext(
	  /** This is always `sql#demoteContext`. */
		kind: Option[String] = None,
	  /** Required. The name of the instance which acts as the on-premises primary instance in the replication setup. */
		sourceRepresentativeInstanceName: Option[String] = None
	)
	
	case class InstancesExportRequest(
	  /** Contains details about the export operation. */
		exportContext: Option[Schema.ExportContext] = None
	)
	
	case class InstancesFailoverRequest(
	  /** Failover Context. */
		failoverContext: Option[Schema.FailoverContext] = None
	)
	
	case class FailoverContext(
	  /** The current settings version of this instance. Request will be rejected if this version doesn't match the current settings version. */
		settingsVersion: Option[String] = None,
	  /** This is always `sql#failoverContext`. */
		kind: Option[String] = None
	)
	
	case class InstancesReencryptRequest(
	  /** Configuration specific to backup re-encryption */
		backupReencryptionConfig: Option[Schema.BackupReencryptionConfig] = None
	)
	
	object BackupReencryptionConfig {
		enum BackupTypeEnum extends Enum[BackupTypeEnum] { case BACKUP_TYPE_UNSPECIFIED, AUTOMATED, ON_DEMAND }
	}
	case class BackupReencryptionConfig(
	  /** Backup re-encryption limit */
		backupLimit: Option[Int] = None,
	  /** Type of backups users want to re-encrypt. */
		backupType: Option[Schema.BackupReencryptionConfig.BackupTypeEnum] = None
	)
	
	object DatabaseInstance {
		enum StateEnum extends Enum[StateEnum] { case SQL_INSTANCE_STATE_UNSPECIFIED, RUNNABLE, SUSPENDED, PENDING_DELETE, PENDING_CREATE, MAINTENANCE, FAILED, ONLINE_MAINTENANCE }
		enum DatabaseVersionEnum extends Enum[DatabaseVersionEnum] { case SQL_DATABASE_VERSION_UNSPECIFIED, MYSQL_5_1, MYSQL_5_5, MYSQL_5_6, MYSQL_5_7, MYSQL_8_0, MYSQL_8_0_18, MYSQL_8_0_26, MYSQL_8_0_27, MYSQL_8_0_28, MYSQL_8_0_29, MYSQL_8_0_30, MYSQL_8_0_31, MYSQL_8_0_32, MYSQL_8_0_33, MYSQL_8_0_34, MYSQL_8_0_35, MYSQL_8_0_36, MYSQL_8_0_37, MYSQL_8_0_38, MYSQL_8_0_39, MYSQL_8_0_40, MYSQL_8_0_41, MYSQL_8_0_42, MYSQL_8_4, SQLSERVER_2017_STANDARD, SQLSERVER_2017_ENTERPRISE, SQLSERVER_2017_EXPRESS, SQLSERVER_2017_WEB, POSTGRES_9_6, POSTGRES_10, POSTGRES_11, POSTGRES_12, POSTGRES_13, POSTGRES_14, POSTGRES_15, POSTGRES_16, POSTGRES_17, SQLSERVER_2019_STANDARD, SQLSERVER_2019_ENTERPRISE, SQLSERVER_2019_EXPRESS, SQLSERVER_2019_WEB, SQLSERVER_2022_STANDARD, SQLSERVER_2022_ENTERPRISE, SQLSERVER_2022_EXPRESS, SQLSERVER_2022_WEB }
		case class FailoverReplicaItem(
		  /** The name of the failover replica. If specified at instance creation, a failover replica is created for the instance. The name doesn't include the project ID. */
			name: Option[String] = None,
		  /** The availability status of the failover replica. A false status indicates that the failover replica is out of sync. The primary instance can only failover to the failover replica when the status is true. */
			available: Option[Boolean] = None
		)
		
		enum InstanceTypeEnum extends Enum[InstanceTypeEnum] { case SQL_INSTANCE_TYPE_UNSPECIFIED, CLOUD_SQL_INSTANCE, ON_PREMISES_INSTANCE, READ_REPLICA_INSTANCE }
		enum BackendTypeEnum extends Enum[BackendTypeEnum] { case SQL_BACKEND_TYPE_UNSPECIFIED, FIRST_GEN, SECOND_GEN, EXTERNAL }
		enum SuspensionReasonEnum extends Enum[SuspensionReasonEnum] { case SQL_SUSPENSION_REASON_UNSPECIFIED, BILLING_ISSUE, LEGAL_ISSUE, OPERATIONAL_ISSUE, KMS_KEY_ISSUE }
		enum SqlNetworkArchitectureEnum extends Enum[SqlNetworkArchitectureEnum] { case SQL_NETWORK_ARCHITECTURE_UNSPECIFIED, NEW_NETWORK_ARCHITECTURE, OLD_NETWORK_ARCHITECTURE }
	}
	case class DatabaseInstance(
	  /** This is always `sql#instance`. */
		kind: Option[String] = None,
	  /** The current serving state of the Cloud SQL instance. */
		state: Option[Schema.DatabaseInstance.StateEnum] = None,
	  /** The database engine type and version. The `databaseVersion` field cannot be changed after instance creation. */
		databaseVersion: Option[Schema.DatabaseInstance.DatabaseVersionEnum] = None,
	  /** The user settings. */
		settings: Option[Schema.Settings] = None,
	  /** This field is deprecated and will be removed from a future version of the API. Use the `settings.settingsVersion` field instead. */
		etag: Option[String] = None,
	  /** The name and status of the failover replica. */
		failoverReplica: Option[Schema.DatabaseInstance.FailoverReplicaItem] = None,
	  /** The name of the instance which will act as primary in the replication setup. */
		masterInstanceName: Option[String] = None,
	  /** The replicas of the instance. */
		replicaNames: Option[List[String]] = None,
	  /** The maximum disk size of the instance in bytes. */
		maxDiskSize: Option[String] = None,
	  /** The current disk usage of the instance in bytes. This property has been deprecated. Use the "cloudsql.googleapis.com/database/disk/bytes_used" metric in Cloud Monitoring API instead. Please see [this announcement](https://groups.google.com/d/msg/google-cloud-sql-announce/I_7-F9EBhT0/BtvFtdFeAgAJ) for details. */
		currentDiskSize: Option[String] = None,
	  /** The assigned IP addresses for the instance. */
		ipAddresses: Option[List[Schema.IpMapping]] = None,
	  /** SSL configuration. */
		serverCaCert: Option[Schema.SslCert] = None,
	  /** The instance type. */
		instanceType: Option[Schema.DatabaseInstance.InstanceTypeEnum] = None,
	  /** The project ID of the project containing the Cloud SQL instance. The Google apps domain is prefixed if applicable. */
		project: Option[String] = None,
	  /** The IPv6 address assigned to the instance. (Deprecated) This property was applicable only to First Generation instances. */
		ipv6Address: Option[String] = None,
	  /** The service account email address assigned to the instance.\This property is read-only. */
		serviceAccountEmailAddress: Option[String] = None,
	  /** Configuration specific to on-premises instances. */
		onPremisesConfiguration: Option[Schema.OnPremisesConfiguration] = None,
	  /** Configuration specific to failover replicas and read replicas. */
		replicaConfiguration: Option[Schema.ReplicaConfiguration] = None,
	  /** The backend type. `SECOND_GEN`: Cloud SQL database instance. `EXTERNAL`: A database server that is not managed by Google. This property is read-only; use the `tier` property in the `settings` object to determine the database type. */
		backendType: Option[Schema.DatabaseInstance.BackendTypeEnum] = None,
	  /** The URI of this resource. */
		selfLink: Option[String] = None,
	  /** If the instance state is SUSPENDED, the reason for the suspension. */
		suspensionReason: Option[List[Schema.DatabaseInstance.SuspensionReasonEnum]] = None,
	  /** Connection name of the Cloud SQL instance used in connection strings. */
		connectionName: Option[String] = None,
	  /** Name of the Cloud SQL instance. This does not include the project ID. */
		name: Option[String] = None,
	  /** The geographical region of the Cloud SQL instance. It can be one of the [regions](https://cloud.google.com/sql/docs/mysql/locations#location-r) where Cloud SQL operates: For example, `asia-east1`, `europe-west1`, and `us-central1`. The default value is `us-central1`. */
		region: Option[String] = None,
	  /** The Compute Engine zone that the instance is currently serving from. This value could be different from the zone that was specified when the instance was created if the instance has failed over to its secondary zone. WARNING: Changing this might restart the instance. */
		gceZone: Option[String] = None,
	  /** The Compute Engine zone that the failover instance is currently serving from for a regional instance. This value could be different from the zone that was specified when the instance was created if the instance has failed over to its secondary/failover zone. */
		secondaryGceZone: Option[String] = None,
	  /** Disk encryption configuration specific to an instance. */
		diskEncryptionConfiguration: Option[Schema.DiskEncryptionConfiguration] = None,
	  /** Disk encryption status specific to an instance. */
		diskEncryptionStatus: Option[Schema.DiskEncryptionStatus] = None,
	  /** Initial root password. Use only on creation. You must set root passwords before you can connect to PostgreSQL instances. */
		rootPassword: Option[String] = None,
	  /** The start time of any upcoming scheduled maintenance for this instance. */
		scheduledMaintenance: Option[Schema.SqlScheduledMaintenance] = None,
	  /** This status indicates whether the instance satisfies PZS. The status is reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Output only. Stores the current database version running on the instance including minor version such as `MYSQL_8_0_18`. */
		databaseInstalledVersion: Option[String] = None,
	  /** This field represents the report generated by the proactive database wellness job for OutOfDisk issues. &#42; Writers: &#42; the proactive database wellness job for OOD. &#42; Readers: &#42; the proactive database wellness job */
		outOfDiskReport: Option[Schema.SqlOutOfDiskReport] = None,
	  /** Output only. The time when the instance was created in [RFC 3339](https://tools.ietf.org/html/rfc3339) format, for example `2012-11-15T16:19:00.094Z`. */
		createTime: Option[String] = None,
	  /** Output only. List all maintenance versions applicable on the instance */
		availableMaintenanceVersions: Option[List[String]] = None,
	  /** The current software version on the instance. */
		maintenanceVersion: Option[String] = None,
	  /** Output only. All database versions that are available for upgrade. */
		upgradableDatabaseVersions: Option[List[Schema.AvailableDatabaseVersion]] = None,
		sqlNetworkArchitecture: Option[Schema.DatabaseInstance.SqlNetworkArchitectureEnum] = None,
	  /** Output only. The link to service attachment of PSC instance. */
		pscServiceAttachmentLink: Option[String] = None,
	  /** Output only. The dns name of the instance. */
		dnsName: Option[String] = None,
	  /** Output only. DEPRECATED: please use write_endpoint instead. */
		primaryDnsName: Option[String] = None,
	  /** Output only. The dns name of the primary instance in a replication group. */
		writeEndpoint: Option[String] = None,
	  /** Optional. A primary instance and disaster recovery (DR) replica pair. A DR replica is a cross-region replica that you designate for failover in the event that the primary instance experiences regional failure. Only applicable to MySQL. */
		replicationCluster: Option[Schema.ReplicationCluster] = None,
	  /** Gemini instance configuration. */
		geminiConfig: Option[Schema.GeminiInstanceConfig] = None,
	  /** Output only. This status indicates whether the instance satisfies PZI. The status is reserved for future use. */
		satisfiesPzi: Option[Boolean] = None,
	  /** Input only. Whether Cloud SQL is enabled to switch storing point-in-time recovery log files from a data disk to Cloud Storage. */
		switchTransactionLogsToCloudStorageEnabled: Option[Boolean] = None
	)
	
	object Settings {
		enum AvailabilityTypeEnum extends Enum[AvailabilityTypeEnum] { case SQL_AVAILABILITY_TYPE_UNSPECIFIED, ZONAL, REGIONAL }
		enum PricingPlanEnum extends Enum[PricingPlanEnum] { case SQL_PRICING_PLAN_UNSPECIFIED, PACKAGE, PER_USE }
		enum ReplicationTypeEnum extends Enum[ReplicationTypeEnum] { case SQL_REPLICATION_TYPE_UNSPECIFIED, SYNCHRONOUS, ASYNCHRONOUS }
		enum ActivationPolicyEnum extends Enum[ActivationPolicyEnum] { case SQL_ACTIVATION_POLICY_UNSPECIFIED, ALWAYS, NEVER, ON_DEMAND }
		enum DataDiskTypeEnum extends Enum[DataDiskTypeEnum] { case SQL_DATA_DISK_TYPE_UNSPECIFIED, PD_SSD, PD_HDD, OBSOLETE_LOCAL_SSD }
		enum EditionEnum extends Enum[EditionEnum] { case EDITION_UNSPECIFIED, ENTERPRISE, ENTERPRISE_PLUS }
		enum ConnectorEnforcementEnum extends Enum[ConnectorEnforcementEnum] { case CONNECTOR_ENFORCEMENT_UNSPECIFIED, NOT_REQUIRED, REQUIRED }
	}
	case class Settings(
	  /** The version of instance settings. This is a required field for update method to make sure concurrent updates are handled properly. During update, use the most recent settingsVersion value for this instance and do not try to update this value. */
		settingsVersion: Option[String] = None,
	  /** The App Engine app IDs that can access this instance. (Deprecated) Applied to First Generation instances only. */
		authorizedGaeApplications: Option[List[String]] = None,
	  /** The tier (or machine type) for this instance, for example `db-custom-1-3840`. WARNING: Changing this restarts the instance. */
		tier: Option[String] = None,
	  /** This is always `sql#settings`. */
		kind: Option[String] = None,
	  /** User-provided labels, represented as a dictionary where each label is a single key value pair. */
		userLabels: Option[Map[String, String]] = None,
	  /** Availability type. Potential values: &#42; `ZONAL`: The instance serves data from only one zone. Outages in that zone affect data accessibility. &#42; `REGIONAL`: The instance can serve data from more than one zone in a region (it is highly available)./ For more information, see [Overview of the High Availability Configuration](https://cloud.google.com/sql/docs/mysql/high-availability). */
		availabilityType: Option[Schema.Settings.AvailabilityTypeEnum] = None,
	  /** The pricing plan for this instance. This can be either `PER_USE` or `PACKAGE`. Only `PER_USE` is supported for Second Generation instances. */
		pricingPlan: Option[Schema.Settings.PricingPlanEnum] = None,
	  /** The type of replication this instance uses. This can be either `ASYNCHRONOUS` or `SYNCHRONOUS`. (Deprecated) This property was only applicable to First Generation instances. */
		replicationType: Option[Schema.Settings.ReplicationTypeEnum] = None,
	  /** The maximum size to which storage capacity can be automatically increased. The default value is 0, which specifies that there is no limit. */
		storageAutoResizeLimit: Option[String] = None,
	  /** The activation policy specifies when the instance is activated; it is applicable only when the instance state is RUNNABLE. Valid values: &#42; `ALWAYS`: The instance is on, and remains so even in the absence of connection requests. &#42; `NEVER`: The instance is off; it is not activated, even if a connection request arrives. */
		activationPolicy: Option[Schema.Settings.ActivationPolicyEnum] = None,
	  /** The settings for IP Management. This allows to enable or disable the instance IP and manage which external networks can connect to the instance. The IPv4 address cannot be disabled for Second Generation instances. */
		ipConfiguration: Option[Schema.IpConfiguration] = None,
	  /** Configuration to increase storage size automatically. The default value is true. */
		storageAutoResize: Option[Boolean] = None,
	  /** The location preference settings. This allows the instance to be located as near as possible to either an App Engine app or Compute Engine zone for better performance. App Engine co-location was only applicable to First Generation instances. */
		locationPreference: Option[Schema.LocationPreference] = None,
	  /** The database flags passed to the instance at startup. */
		databaseFlags: Option[List[Schema.DatabaseFlags]] = None,
	  /** The type of data disk: `PD_SSD` (default) or `PD_HDD`. Not used for First Generation instances. */
		dataDiskType: Option[Schema.Settings.DataDiskTypeEnum] = None,
	  /** The maintenance window for this instance. This specifies when the instance can be restarted for maintenance purposes. */
		maintenanceWindow: Option[Schema.MaintenanceWindow] = None,
	  /** The daily backup configuration for the instance. */
		backupConfiguration: Option[Schema.BackupConfiguration] = None,
	  /** Configuration specific to read replica instances. Indicates whether replication is enabled or not. WARNING: Changing this restarts the instance. */
		databaseReplicationEnabled: Option[Boolean] = None,
	  /** Configuration specific to read replica instances. Indicates whether database flags for crash-safe replication are enabled. This property was only applicable to First Generation instances. */
		crashSafeReplicationEnabled: Option[Boolean] = None,
	  /** The size of data disk, in GB. The data disk size minimum is 10GB. */
		dataDiskSizeGb: Option[String] = None,
	  /** Active Directory configuration, relevant only for Cloud SQL for SQL Server. */
		activeDirectoryConfig: Option[Schema.SqlActiveDirectoryConfig] = None,
	  /** The name of server Instance collation. */
		collation: Option[String] = None,
	  /** Deny maintenance periods */
		denyMaintenancePeriods: Option[List[Schema.DenyMaintenancePeriod]] = None,
	  /** Insights configuration, for now relevant only for Postgres. */
		insightsConfig: Option[Schema.InsightsConfig] = None,
	  /** The local user password validation policy of the instance. */
		passwordValidationPolicy: Option[Schema.PasswordValidationPolicy] = None,
	  /** SQL Server specific audit configuration. */
		sqlServerAuditConfig: Option[Schema.SqlServerAuditConfig] = None,
	  /** Optional. The edition of the instance. */
		edition: Option[Schema.Settings.EditionEnum] = None,
	  /** Specifies if connections must use Cloud SQL connectors. Option values include the following: `NOT_REQUIRED` (Cloud SQL instances can be connected without Cloud SQL Connectors) and `REQUIRED` (Only allow connections that use Cloud SQL Connectors). Note that using REQUIRED disables all existing authorized networks. If this field is not specified when creating a new instance, NOT_REQUIRED is used. If this field is not specified when patching or updating an existing instance, it is left unchanged in the instance. */
		connectorEnforcement: Option[Schema.Settings.ConnectorEnforcementEnum] = None,
	  /** Configuration to protect against accidental instance deletion. */
		deletionProtectionEnabled: Option[Boolean] = None,
	  /** Server timezone, relevant only for Cloud SQL for SQL Server. */
		timeZone: Option[String] = None,
	  /** Specifies advanced machine configuration for the instances relevant only for SQL Server. */
		advancedMachineFeatures: Option[Schema.AdvancedMachineFeatures] = None,
	  /** Configuration for data cache. */
		dataCacheConfig: Option[Schema.DataCacheConfig] = None,
	  /** Optional. When this parameter is set to true, Cloud SQL instances can connect to Vertex AI to pass requests for real-time predictions and insights to the AI. The default value is false. This applies only to Cloud SQL for PostgreSQL instances. */
		enableGoogleMlIntegration: Option[Boolean] = None,
	  /** Optional. By default, Cloud SQL instances have schema extraction disabled for Dataplex. When this parameter is set to true, schema extraction for Dataplex on Cloud SQL instances is activated. */
		enableDataplexIntegration: Option[Boolean] = None
	)
	
	object IpConfiguration {
		enum SslModeEnum extends Enum[SslModeEnum] { case SSL_MODE_UNSPECIFIED, ALLOW_UNENCRYPTED_AND_ENCRYPTED, ENCRYPTED_ONLY, TRUSTED_CLIENT_CERTIFICATE_REQUIRED }
		enum ServerCaModeEnum extends Enum[ServerCaModeEnum] { case CA_MODE_UNSPECIFIED, GOOGLE_MANAGED_INTERNAL_CA, GOOGLE_MANAGED_CAS_CA }
	}
	case class IpConfiguration(
	  /** Whether the instance is assigned a public IP address or not. */
		ipv4Enabled: Option[Boolean] = None,
	  /** The resource link for the VPC network from which the Cloud SQL instance is accessible for private IP. For example, `/projects/myProject/global/networks/default`. This setting can be updated, but it cannot be removed after it is set. */
		privateNetwork: Option[String] = None,
	  /** Use `ssl_mode` instead. Whether SSL/TLS connections over IP are enforced. If set to false, then allow both non-SSL/non-TLS and SSL/TLS connections. For SSL/TLS connections, the client certificate won't be verified. If set to true, then only allow connections encrypted with SSL/TLS and with valid client certificates. If you want to enforce SSL/TLS without enforcing the requirement for valid client certificates, then use the `ssl_mode` flag instead of the `require_ssl` flag. */
		requireSsl: Option[Boolean] = None,
	  /** The list of external networks that are allowed to connect to the instance using the IP. In 'CIDR' notation, also known as 'slash' notation (for example: `157.197.200.0/24`). */
		authorizedNetworks: Option[List[Schema.AclEntry]] = None,
	  /** The name of the allocated ip range for the private ip Cloud SQL instance. For example: "google-managed-services-default". If set, the instance ip will be created in the allocated range. The range name must comply with [RFC 1035](https://tools.ietf.org/html/rfc1035). Specifically, the name must be 1-63 characters long and match the regular expression `[a-z]([-a-z0-9]&#42;[a-z0-9])?.` */
		allocatedIpRange: Option[String] = None,
	  /** Controls connectivity to private IP instances from Google services, such as BigQuery. */
		enablePrivatePathForGoogleCloudServices: Option[Boolean] = None,
	  /** Specify how SSL/TLS is enforced in database connections. If you must use the `require_ssl` flag for backward compatibility, then only the following value pairs are valid: For PostgreSQL and MySQL: &#42; `ssl_mode=ALLOW_UNENCRYPTED_AND_ENCRYPTED` and `require_ssl=false` &#42; `ssl_mode=ENCRYPTED_ONLY` and `require_ssl=false` &#42; `ssl_mode=TRUSTED_CLIENT_CERTIFICATE_REQUIRED` and `require_ssl=true` For SQL Server: &#42; `ssl_mode=ALLOW_UNENCRYPTED_AND_ENCRYPTED` and `require_ssl=false` &#42; `ssl_mode=ENCRYPTED_ONLY` and `require_ssl=true` The value of `ssl_mode` has priority over the value of `require_ssl`. For example, for the pair `ssl_mode=ENCRYPTED_ONLY` and `require_ssl=false`, `ssl_mode=ENCRYPTED_ONLY` means accept only SSL connections, while `require_ssl=false` means accept both non-SSL and SSL connections. In this case, MySQL and PostgreSQL databases respect `ssl_mode` and accepts only SSL connections. */
		sslMode: Option[Schema.IpConfiguration.SslModeEnum] = None,
	  /** PSC settings for this instance. */
		pscConfig: Option[Schema.PscConfig] = None,
	  /** Specify what type of CA is used for the server certificate. */
		serverCaMode: Option[Schema.IpConfiguration.ServerCaModeEnum] = None
	)
	
	case class AclEntry(
	  /** The allowlisted value for the access control list. */
		value: Option[String] = None,
	  /** The time when this access control entry expires in [RFC 3339](https://tools.ietf.org/html/rfc3339) format, for example `2012-11-15T16:19:00.094Z`. */
		expirationTime: Option[String] = None,
	  /** Optional. A label to identify this entry. */
		name: Option[String] = None,
	  /** This is always `sql#aclEntry`. */
		kind: Option[String] = None
	)
	
	case class PscConfig(
	  /** Whether PSC connectivity is enabled for this instance. */
		pscEnabled: Option[Boolean] = None,
	  /** Optional. The list of consumer projects that are allow-listed for PSC connections to this instance. This instance can be connected to with PSC from any network in these projects. Each consumer project in this list may be represented by a project number (numeric) or by a project id (alphanumeric). */
		allowedConsumerProjects: Option[List[String]] = None,
	  /** Optional. The list of settings for requested Private Service Connect consumer endpoints that can be used to connect to this Cloud SQL instance. */
		pscAutoConnections: Option[List[Schema.PscAutoConnectionConfig]] = None
	)
	
	case class PscAutoConnectionConfig(
	  /** This is the project ID of consumer service project of this consumer endpoint. Optional. This is only applicable if consumer_network is a shared vpc network. */
		consumerProject: Option[String] = None,
	  /** The consumer network of this consumer endpoint. This must be a resource path that includes both the host project and the network name. For example, `projects/project1/global/networks/network1`. The consumer host project of this network might be different from the consumer service project. */
		consumerNetwork: Option[String] = None,
	  /** The IP address of the consumer endpoint. */
		ipAddress: Option[String] = None,
	  /** The connection status of the consumer endpoint. */
		status: Option[String] = None,
	  /** The connection policy status of the consumer network. */
		consumerNetworkStatus: Option[String] = None
	)
	
	case class LocationPreference(
	  /** The App Engine application to follow, it must be in the same region as the Cloud SQL instance. WARNING: Changing this might restart the instance. */
		followGaeApplication: Option[String] = None,
	  /** The preferred Compute Engine zone (for example: us-central1-a, us-central1-b, etc.). WARNING: Changing this might restart the instance. */
		zone: Option[String] = None,
	  /** The preferred Compute Engine zone for the secondary/failover (for example: us-central1-a, us-central1-b, etc.). To disable this field, set it to 'no_secondary_zone'. */
		secondaryZone: Option[String] = None,
	  /** This is always `sql#locationPreference`. */
		kind: Option[String] = None
	)
	
	case class DatabaseFlags(
	  /** The name of the flag. These flags are passed at instance startup, so include both server options and system variables. Flags are specified with underscores, not hyphens. For more information, see [Configuring Database Flags](https://cloud.google.com/sql/docs/mysql/flags) in the Cloud SQL documentation. */
		name: Option[String] = None,
	  /** The value of the flag. Boolean flags are set to `on` for true and `off` for false. This field must be omitted if the flag doesn't take a value. */
		value: Option[String] = None
	)
	
	object MaintenanceWindow {
		enum UpdateTrackEnum extends Enum[UpdateTrackEnum] { case SQL_UPDATE_TRACK_UNSPECIFIED, canary, stable, week5 }
	}
	case class MaintenanceWindow(
	  /** Hour of day - 0 to 23. Specify in the UTC time zone. */
		hour: Option[Int] = None,
	  /** Day of week - `MONDAY`, `TUESDAY`, `WEDNESDAY`, `THURSDAY`, `FRIDAY`, `SATURDAY`, or `SUNDAY`. Specify in the UTC time zone. Returned in output as an integer, 1 to 7, where `1` equals Monday. */
		day: Option[Int] = None,
	  /** Maintenance timing settings: `canary`, `stable`, or `week5`. For more information, see [About maintenance on Cloud SQL instances](https://cloud.google.com/sql/docs/mysql/maintenance). */
		updateTrack: Option[Schema.MaintenanceWindow.UpdateTrackEnum] = None,
	  /** This is always `sql#maintenanceWindow`. */
		kind: Option[String] = None
	)
	
	object BackupConfiguration {
		enum TransactionalLogStorageStateEnum extends Enum[TransactionalLogStorageStateEnum] { case TRANSACTIONAL_LOG_STORAGE_STATE_UNSPECIFIED, DISK, SWITCHING_TO_CLOUD_STORAGE, SWITCHED_TO_CLOUD_STORAGE, CLOUD_STORAGE }
	}
	case class BackupConfiguration(
	  /** Start time for the daily backup configuration in UTC timezone in the 24 hour format - `HH:MM`. */
		startTime: Option[String] = None,
	  /** Whether this configuration is enabled. */
		enabled: Option[Boolean] = None,
	  /** This is always `sql#backupConfiguration`. */
		kind: Option[String] = None,
	  /** (MySQL only) Whether binary log is enabled. If backup configuration is disabled, binarylog must be disabled as well. */
		binaryLogEnabled: Option[Boolean] = None,
	  /** Reserved for future use. */
		replicationLogArchivingEnabled: Option[Boolean] = None,
	  /** Location of the backup */
		location: Option[String] = None,
	  /** Whether point in time recovery is enabled. */
		pointInTimeRecoveryEnabled: Option[Boolean] = None,
	  /** Backup retention settings. */
		backupRetentionSettings: Option[Schema.BackupRetentionSettings] = None,
	  /** The number of days of transaction logs we retain for point in time restore, from 1-7. */
		transactionLogRetentionDays: Option[Int] = None,
	  /** Output only. This value contains the storage location of transactional logs used to perform point-in-time recovery (PITR) for the database. */
		transactionalLogStorageState: Option[Schema.BackupConfiguration.TransactionalLogStorageStateEnum] = None
	)
	
	object BackupRetentionSettings {
		enum RetentionUnitEnum extends Enum[RetentionUnitEnum] { case RETENTION_UNIT_UNSPECIFIED, COUNT }
	}
	case class BackupRetentionSettings(
	  /** The unit that 'retained_backups' represents. */
		retentionUnit: Option[Schema.BackupRetentionSettings.RetentionUnitEnum] = None,
	  /** Depending on the value of retention_unit, this is used to determine if a backup needs to be deleted. If retention_unit is 'COUNT', we will retain this many backups. */
		retainedBackups: Option[Int] = None
	)
	
	case class SqlActiveDirectoryConfig(
	  /** This is always sql#activeDirectoryConfig. */
		kind: Option[String] = None,
	  /** The name of the domain (e.g., mydomain.com). */
		domain: Option[String] = None
	)
	
	case class DenyMaintenancePeriod(
	  /** "deny maintenance period" start date. If the year of the start date is empty, the year of the end date also must be empty. In this case, it means the deny maintenance period recurs every year. The date is in format yyyy-mm-dd i.e., 2020-11-01, or mm-dd, i.e., 11-01 */
		startDate: Option[String] = None,
	  /** "deny maintenance period" end date. If the year of the end date is empty, the year of the start date also must be empty. In this case, it means the no maintenance interval recurs every year. The date is in format yyyy-mm-dd i.e., 2020-11-01, or mm-dd, i.e., 11-01 */
		endDate: Option[String] = None,
	  /** Time in UTC when the "deny maintenance period" starts on start_date and ends on end_date. The time is in format: HH:mm:SS, i.e., 00:00:00 */
		time: Option[String] = None
	)
	
	case class InsightsConfig(
	  /** Whether Query Insights feature is enabled. */
		queryInsightsEnabled: Option[Boolean] = None,
	  /** Whether Query Insights will record client address when enabled. */
		recordClientAddress: Option[Boolean] = None,
	  /** Whether Query Insights will record application tags from query when enabled. */
		recordApplicationTags: Option[Boolean] = None,
	  /** Maximum query length stored in bytes. Default value: 1024 bytes. Range: 256-4500 bytes. Query length more than this field value will be truncated to this value. When unset, query length will be the default value. Changing query length will restart the database. */
		queryStringLength: Option[Int] = None,
	  /** Number of query execution plans captured by Insights per minute for all queries combined. Default is 5. */
		queryPlansPerMinute: Option[Int] = None
	)
	
	object PasswordValidationPolicy {
		enum ComplexityEnum extends Enum[ComplexityEnum] { case COMPLEXITY_UNSPECIFIED, COMPLEXITY_DEFAULT }
	}
	case class PasswordValidationPolicy(
	  /** Minimum number of characters allowed. */
		minLength: Option[Int] = None,
	  /** The complexity of the password. */
		complexity: Option[Schema.PasswordValidationPolicy.ComplexityEnum] = None,
	  /** Number of previous passwords that cannot be reused. */
		reuseInterval: Option[Int] = None,
	  /** Disallow username as a part of the password. */
		disallowUsernameSubstring: Option[Boolean] = None,
	  /** Minimum interval after which the password can be changed. This flag is only supported for PostgreSQL. */
		passwordChangeInterval: Option[String] = None,
	  /** Whether the password policy is enabled or not. */
		enablePasswordPolicy: Option[Boolean] = None,
	  /** This field is deprecated and will be removed in a future version of the API. */
		disallowCompromisedCredentials: Option[Boolean] = None
	)
	
	case class SqlServerAuditConfig(
	  /** This is always sql#sqlServerAuditConfig */
		kind: Option[String] = None,
	  /** The name of the destination bucket (e.g., gs://mybucket). */
		bucket: Option[String] = None,
	  /** How long to keep generated audit files. */
		retentionInterval: Option[String] = None,
	  /** How often to upload generated audit files. */
		uploadInterval: Option[String] = None
	)
	
	case class AdvancedMachineFeatures(
	  /** The number of threads per physical core. */
		threadsPerCore: Option[Int] = None
	)
	
	case class DataCacheConfig(
	  /** Whether data cache is enabled for the instance. */
		dataCacheEnabled: Option[Boolean] = None
	)
	
	object IpMapping {
		enum TypeEnum extends Enum[TypeEnum] { case SQL_IP_ADDRESS_TYPE_UNSPECIFIED, PRIMARY, OUTGOING, PRIVATE, MIGRATED_1ST_GEN }
	}
	case class IpMapping(
	  /** The type of this IP address. A `PRIMARY` address is a public address that can accept incoming connections. A `PRIVATE` address is a private address that can accept incoming connections. An `OUTGOING` address is the source address of connections originating from the instance, if supported. */
		`type`: Option[Schema.IpMapping.TypeEnum] = None,
	  /** The IP address assigned. */
		ipAddress: Option[String] = None,
	  /** The due time for this IP to be retired in [RFC 3339](https://tools.ietf.org/html/rfc3339) format, for example `2012-11-15T16:19:00.094Z`. This field is only available when the IP is scheduled to be retired. */
		timeToRetire: Option[String] = None
	)
	
	case class SslCert(
	  /** This is always `sql#sslCert`. */
		kind: Option[String] = None,
	  /** Serial number, as extracted from the certificate. */
		certSerialNumber: Option[String] = None,
	  /** PEM representation. */
		cert: Option[String] = None,
	  /** The time when the certificate was created in [RFC 3339](https://tools.ietf.org/html/rfc3339) format, for example `2012-11-15T16:19:00.094Z` */
		createTime: Option[String] = None,
	  /** User supplied name. Constrained to [a-zA-Z.-_ ]+. */
		commonName: Option[String] = None,
	  /** The time when the certificate expires in [RFC 3339](https://tools.ietf.org/html/rfc3339) format, for example `2012-11-15T16:19:00.094Z`. */
		expirationTime: Option[String] = None,
	  /** Sha1 Fingerprint. */
		sha1Fingerprint: Option[String] = None,
	  /** Name of the database instance. */
		instance: Option[String] = None,
	  /** The URI of this resource. */
		selfLink: Option[String] = None
	)
	
	object OnPremisesConfiguration {
		enum SslOptionEnum extends Enum[SslOptionEnum] { case SSL_OPTION_UNSPECIFIED, DISABLE, REQUIRE, VERIFY_CA }
	}
	case class OnPremisesConfiguration(
	  /** The host and port of the on-premises instance in host:port format */
		hostPort: Option[String] = None,
	  /** This is always `sql#onPremisesConfiguration`. */
		kind: Option[String] = None,
	  /** The username for connecting to on-premises instance. */
		username: Option[String] = None,
	  /** The password for connecting to on-premises instance. */
		password: Option[String] = None,
	  /** PEM representation of the trusted CA's x509 certificate. */
		caCertificate: Option[String] = None,
	  /** PEM representation of the replica's x509 certificate. */
		clientCertificate: Option[String] = None,
	  /** PEM representation of the replica's private key. The corresponsing public key is encoded in the client's certificate. */
		clientKey: Option[String] = None,
	  /** The dump file to create the Cloud SQL replica. */
		dumpFilePath: Option[String] = None,
	  /** The reference to Cloud SQL instance if the source is Cloud SQL. */
		sourceInstance: Option[Schema.InstanceReference] = None,
	  /** Optional. A list of objects that the user selects for replication from an external source instance. */
		selectedObjects: Option[List[Schema.SelectedObjects]] = None,
	  /** Optional. SSL option for replica connection to the on-premises source. */
		sslOption: Option[Schema.OnPremisesConfiguration.SslOptionEnum] = None
	)
	
	case class InstanceReference(
	  /** The name of the Cloud SQL instance being referenced. This does not include the project ID. */
		name: Option[String] = None,
	  /** The region of the Cloud SQL instance being referenced. */
		region: Option[String] = None,
	  /** The project ID of the Cloud SQL instance being referenced. The default is the same project ID as the instance references it. */
		project: Option[String] = None
	)
	
	case class SelectedObjects(
	  /** Required. The name of the database to migrate. */
		database: Option[String] = None
	)
	
	case class ReplicaConfiguration(
	  /** This is always `sql#replicaConfiguration`. */
		kind: Option[String] = None,
	  /** MySQL specific configuration when replicating from a MySQL on-premises primary instance. Replication configuration information such as the username, password, certificates, and keys are not stored in the instance metadata. The configuration information is used only to set up the replication connection and is stored by MySQL in a file named `master.info` in the data directory. */
		mysqlReplicaConfiguration: Option[Schema.MySqlReplicaConfiguration] = None,
	  /** Specifies if the replica is the failover target. If the field is set to `true`, the replica will be designated as a failover replica. In case the primary instance fails, the replica instance will be promoted as the new primary instance. Only one replica can be specified as failover target, and the replica has to be in different zone with the primary instance. */
		failoverTarget: Option[Boolean] = None,
	  /** Optional. Specifies if a SQL Server replica is a cascadable replica. A cascadable replica is a SQL Server cross region replica that supports replica(s) under it. */
		cascadableReplica: Option[Boolean] = None
	)
	
	case class MySqlReplicaConfiguration(
	  /** Path to a SQL dump file in Google Cloud Storage from which the replica instance is to be created. The URI is in the form gs://bucketName/fileName. Compressed gzip files (.gz) are also supported. Dumps have the binlog co-ordinates from which replication begins. This can be accomplished by setting --master-data to 1 when using mysqldump. */
		dumpFilePath: Option[String] = None,
	  /** The username for the replication connection. */
		username: Option[String] = None,
	  /** The password for the replication connection. */
		password: Option[String] = None,
	  /** Seconds to wait between connect retries. MySQL's default is 60 seconds. */
		connectRetryInterval: Option[Int] = None,
	  /** Interval in milliseconds between replication heartbeats. */
		masterHeartbeatPeriod: Option[String] = None,
	  /** PEM representation of the trusted CA's x509 certificate. */
		caCertificate: Option[String] = None,
	  /** PEM representation of the replica's x509 certificate. */
		clientCertificate: Option[String] = None,
	  /** PEM representation of the replica's private key. The corresponsing public key is encoded in the client's certificate. */
		clientKey: Option[String] = None,
	  /** A list of permissible ciphers to use for SSL encryption. */
		sslCipher: Option[String] = None,
	  /** Whether or not to check the primary instance's Common Name value in the certificate that it sends during the SSL handshake. */
		verifyServerCertificate: Option[Boolean] = None,
	  /** This is always `sql#mysqlReplicaConfiguration`. */
		kind: Option[String] = None
	)
	
	case class DiskEncryptionConfiguration(
	  /** Resource name of KMS key for disk encryption */
		kmsKeyName: Option[String] = None,
	  /** This is always `sql#diskEncryptionConfiguration`. */
		kind: Option[String] = None
	)
	
	case class DiskEncryptionStatus(
	  /** KMS key version used to encrypt the Cloud SQL instance resource */
		kmsKeyVersionName: Option[String] = None,
	  /** This is always `sql#diskEncryptionStatus`. */
		kind: Option[String] = None
	)
	
	case class SqlScheduledMaintenance(
	  /** The start time of any upcoming scheduled maintenance for this instance. */
		startTime: Option[String] = None,
		canDefer: Option[Boolean] = None,
	  /** If the scheduled maintenance can be rescheduled. */
		canReschedule: Option[Boolean] = None,
	  /** Maintenance cannot be rescheduled to start beyond this deadline. */
		scheduleDeadlineTime: Option[String] = None
	)
	
	object SqlOutOfDiskReport {
		enum SqlOutOfDiskStateEnum extends Enum[SqlOutOfDiskStateEnum] { case SQL_OUT_OF_DISK_STATE_UNSPECIFIED, NORMAL, SOFT_SHUTDOWN }
	}
	case class SqlOutOfDiskReport(
	  /** This field represents the state generated by the proactive database wellness job for OutOfDisk issues. &#42; Writers: &#42; the proactive database wellness job for OOD. &#42; Readers: &#42; the proactive database wellness job */
		sqlOutOfDiskState: Option[Schema.SqlOutOfDiskReport.SqlOutOfDiskStateEnum] = None,
	  /** The minimum recommended increase size in GigaBytes This field is consumed by the frontend &#42; Writers: &#42; the proactive database wellness job for OOD. &#42; Readers: */
		sqlMinRecommendedIncreaseSizeGb: Option[Int] = None
	)
	
	case class AvailableDatabaseVersion(
	  /** The version's major version name. */
		majorVersion: Option[String] = None,
	  /** The database version name. For MySQL 8.0, this string provides the database major and minor version. */
		name: Option[String] = None,
	  /** The database version's display name. */
		displayName: Option[String] = None
	)
	
	case class ReplicationCluster(
	  /** Output only. If set, it indicates this instance has a private service access (PSA) dns endpoint that is pointing to the primary instance of the cluster. If this instance is the primary, the dns should be pointing to this instance. After Switchover or Replica failover, this DNS endpoint points to the promoted instance. This is a read-only field, returned to the user as information. This field can exist even if a standalone instance does not yet have a replica, or had a DR replica that was deleted. */
		psaWriteEndpoint: Option[String] = None,
	  /** Optional. If the instance is a primary instance, then this field identifies the disaster recovery (DR) replica. A DR replica is an optional configuration for Enterprise Plus edition instances. If the instance is a read replica, then the field is not set. Set this field to a replica name to designate a DR replica for a primary instance. Remove the replica name to remove the DR replica designation. */
		failoverDrReplicaName: Option[String] = None,
	  /** Output only. Read-only field that indicates whether the replica is a DR replica. This field is not set if the instance is a primary instance. */
		drReplica: Option[Boolean] = None
	)
	
	case class GeminiInstanceConfig(
	  /** Output only. Whether Gemini is enabled. */
		entitled: Option[Boolean] = None,
	  /** Output only. Whether the vacuum management is enabled. */
		googleVacuumMgmtEnabled: Option[Boolean] = None,
	  /** Output only. Whether canceling the out-of-memory (OOM) session is enabled. */
		oomSessionCancelEnabled: Option[Boolean] = None,
	  /** Output only. Whether the active query is enabled. */
		activeQueryEnabled: Option[Boolean] = None,
	  /** Output only. Whether the index advisor is enabled. */
		indexAdvisorEnabled: Option[Boolean] = None,
	  /** Output only. Whether the flag recommender is enabled. */
		flagRecommenderEnabled: Option[Boolean] = None
	)
	
	case class InstancesImportRequest(
	  /** Contains details about the import operation. */
		importContext: Option[Schema.ImportContext] = None
	)
	
	case class InstancesListResponse(
	  /** This is always `sql#instancesList`. */
		kind: Option[String] = None,
	  /** List of warnings that occurred while handling the request. */
		warnings: Option[List[Schema.ApiWarning]] = None,
	  /** List of database instance resources. */
		items: Option[List[Schema.DatabaseInstance]] = None,
	  /** The continuation token, used to page through large result sets. Provide this value in a subsequent request to return the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class InstancesListServerCasResponse(
	  /** List of server CA certificates for the instance. */
		certs: Option[List[Schema.SslCert]] = None,
		activeVersion: Option[String] = None,
	  /** This is always `sql#instancesListServerCas`. */
		kind: Option[String] = None
	)
	
	case class InstancesListServerCertificatesResponse(
	  /** List of server CA certificates for the instance. */
		caCerts: Option[List[Schema.SslCert]] = None,
	  /** List of server certificates for the instance, signed by the corresponding CA from the `ca_certs` list. */
		serverCerts: Option[List[Schema.SslCert]] = None,
	  /** The `sha1_fingerprint` of the active certificate from `server_certs`. */
		activeVersion: Option[String] = None,
	  /** This is always `sql#instancesListServerCertificates`. */
		kind: Option[String] = None
	)
	
	case class InstancesRestoreBackupRequest(
	  /** Parameters required to perform the restore backup operation. */
		restoreBackupContext: Option[Schema.RestoreBackupContext] = None
	)
	
	case class RestoreBackupContext(
	  /** This is always `sql#restoreBackupContext`. */
		kind: Option[String] = None,
	  /** The ID of the backup run to restore from. */
		backupRunId: Option[String] = None,
	  /** The ID of the instance that the backup was taken from. */
		instanceId: Option[String] = None,
	  /** The full project ID of the source instance. */
		project: Option[String] = None
	)
	
	case class InstancesRotateServerCaRequest(
	  /** Contains details about the rotate server CA operation. */
		rotateServerCaContext: Option[Schema.RotateServerCaContext] = None
	)
	
	case class RotateServerCaContext(
	  /** This is always `sql#rotateServerCaContext`. */
		kind: Option[String] = None,
	  /** The fingerprint of the next version to be rotated to. If left unspecified, will be rotated to the most recently added server CA version. */
		nextVersion: Option[String] = None
	)
	
	case class InstancesRotateServerCertificateRequest(
	  /** Optional. Contains details about the rotate server certificate operation. */
		rotateServerCertificateContext: Option[Schema.RotateServerCertificateContext] = None
	)
	
	case class RotateServerCertificateContext(
	  /** Optional. This is always `sql#rotateServerCertificateContext`. */
		kind: Option[String] = None,
	  /** The fingerprint of the next version to be rotated to. If left unspecified, will be rotated to the most recently added server certificate version. */
		nextVersion: Option[String] = None
	)
	
	case class InstancesTruncateLogRequest(
	  /** Contains details about the truncate log operation. */
		truncateLogContext: Option[Schema.TruncateLogContext] = None
	)
	
	case class TruncateLogContext(
	  /** This is always `sql#truncateLogContext`. */
		kind: Option[String] = None,
	  /** The type of log to truncate. Valid values are `MYSQL_GENERAL_TABLE` and `MYSQL_SLOW_TABLE`. */
		logType: Option[String] = None
	)
	
	case class SslCertsCreateEphemeralRequest(
	  /** PEM encoded public key to include in the signed certificate. */
		public_key: Option[String] = None,
	  /** Access token to include in the signed certificate. */
		access_token: Option[String] = None
	)
	
	case class SqlInstancesRescheduleMaintenanceRequestBody(
	  /** Required. The type of the reschedule the user wants. */
		reschedule: Option[Schema.Reschedule] = None
	)
	
	object Reschedule {
		enum RescheduleTypeEnum extends Enum[RescheduleTypeEnum] { case RESCHEDULE_TYPE_UNSPECIFIED, IMMEDIATE, NEXT_AVAILABLE_WINDOW, SPECIFIC_TIME }
	}
	case class Reschedule(
	  /** Required. The type of the reschedule. */
		rescheduleType: Option[Schema.Reschedule.RescheduleTypeEnum] = None,
	  /** Optional. Timestamp when the maintenance shall be rescheduled to if reschedule_type=SPECIFIC_TIME, in [RFC 3339](https://tools.ietf.org/html/rfc3339) format, for example `2012-11-15T16:19:00.094Z`. */
		scheduleTime: Option[String] = None
	)
	
	object SqlInstancesVerifyExternalSyncSettingsRequest {
		enum SyncModeEnum extends Enum[SyncModeEnum] { case EXTERNAL_SYNC_MODE_UNSPECIFIED, ONLINE, OFFLINE }
		enum MigrationTypeEnum extends Enum[MigrationTypeEnum] { case MIGRATION_TYPE_UNSPECIFIED, LOGICAL, PHYSICAL }
		enum SyncParallelLevelEnum extends Enum[SyncParallelLevelEnum] { case EXTERNAL_SYNC_PARALLEL_LEVEL_UNSPECIFIED, MIN, OPTIMAL, MAX }
	}
	case class SqlInstancesVerifyExternalSyncSettingsRequest(
	  /** Flag to enable verifying connection only */
		verifyConnectionOnly: Option[Boolean] = None,
	  /** External sync mode */
		syncMode: Option[Schema.SqlInstancesVerifyExternalSyncSettingsRequest.SyncModeEnum] = None,
	  /** Optional. Flag to verify settings required by replication setup only */
		verifyReplicationOnly: Option[Boolean] = None,
	  /** Optional. MySQL-specific settings for start external sync. */
		mysqlSyncConfig: Option[Schema.MySqlSyncConfig] = None,
	  /** Optional. MigrationType configures the migration to use physical files or logical dump files. If not set, then the logical dump file configuration is used. Valid values are `LOGICAL` or `PHYSICAL`. Only applicable to MySQL. */
		migrationType: Option[Schema.SqlInstancesVerifyExternalSyncSettingsRequest.MigrationTypeEnum] = None,
	  /** Optional. Parallel level for initial data sync. Only applicable for PostgreSQL. */
		syncParallelLevel: Option[Schema.SqlInstancesVerifyExternalSyncSettingsRequest.SyncParallelLevelEnum] = None,
	  /** Optional. Migrate only the specified objects from the source instance. If this field is empty, then migrate all objects. */
		selectedObjects: Option[List[Schema.ExternalSyncSelectedObject]] = None
	)
	
	case class MySqlSyncConfig(
	  /** Flags to use for the initial dump. */
		initialSyncFlags: Option[List[Schema.SyncFlags]] = None
	)
	
	case class SyncFlags(
	  /** The name of the flag. */
		name: Option[String] = None,
	  /** The value of the flag. This field must be omitted if the flag doesn't take a value. */
		value: Option[String] = None
	)
	
	case class ExternalSyncSelectedObject(
	  /** The name of the database that Cloud SQL migrates. */
		database: Option[String] = None
	)
	
	case class SqlInstancesVerifyExternalSyncSettingsResponse(
	  /** This is always `sql#migrationSettingErrorList`. */
		kind: Option[String] = None,
	  /** List of migration violations. */
		errors: Option[List[Schema.SqlExternalSyncSettingError]] = None,
	  /** List of migration warnings. */
		warnings: Option[List[Schema.SqlExternalSyncSettingError]] = None
	)
	
	object SqlExternalSyncSettingError {
		enum TypeEnum extends Enum[TypeEnum] { case SQL_EXTERNAL_SYNC_SETTING_ERROR_TYPE_UNSPECIFIED, CONNECTION_FAILURE, BINLOG_NOT_ENABLED, INCOMPATIBLE_DATABASE_VERSION, REPLICA_ALREADY_SETUP, INSUFFICIENT_PRIVILEGE, UNSUPPORTED_MIGRATION_TYPE, NO_PGLOGICAL_INSTALLED, PGLOGICAL_NODE_ALREADY_EXISTS, INVALID_WAL_LEVEL, INVALID_SHARED_PRELOAD_LIBRARY, INSUFFICIENT_MAX_REPLICATION_SLOTS, INSUFFICIENT_MAX_WAL_SENDERS, INSUFFICIENT_MAX_WORKER_PROCESSES, UNSUPPORTED_EXTENSIONS, INVALID_RDS_LOGICAL_REPLICATION, INVALID_LOGGING_SETUP, INVALID_DB_PARAM, UNSUPPORTED_GTID_MODE, SQLSERVER_AGENT_NOT_RUNNING, UNSUPPORTED_TABLE_DEFINITION, UNSUPPORTED_DEFINER, SQLSERVER_SERVERNAME_MISMATCH, PRIMARY_ALREADY_SETUP, UNSUPPORTED_BINLOG_FORMAT, BINLOG_RETENTION_SETTING, UNSUPPORTED_STORAGE_ENGINE, LIMITED_SUPPORT_TABLES, EXISTING_DATA_IN_REPLICA, MISSING_OPTIONAL_PRIVILEGES, RISKY_BACKUP_ADMIN_PRIVILEGE, INSUFFICIENT_GCS_PERMISSIONS, INVALID_FILE_INFO, UNSUPPORTED_DATABASE_SETTINGS, MYSQL_PARALLEL_IMPORT_INSUFFICIENT_PRIVILEGE, LOCAL_INFILE_OFF, TURN_ON_PITR_AFTER_PROMOTE, INCOMPATIBLE_DATABASE_MINOR_VERSION, SOURCE_MAX_SUBSCRIPTIONS, UNABLE_TO_VERIFY_DEFINERS, SUBSCRIPTION_CALCULATION_STATUS, PG_SUBSCRIPTION_COUNT, PG_SYNC_PARALLEL_LEVEL, INSUFFICIENT_DISK_SIZE, INSUFFICIENT_MACHINE_TIER, UNSUPPORTED_EXTENSIONS_NOT_MIGRATED, EXTENSIONS_NOT_MIGRATED, PG_CRON_FLAG_ENABLED_IN_REPLICA, EXTENSIONS_NOT_ENABLED_IN_REPLICA, UNSUPPORTED_COLUMNS, USERS_NOT_CREATED_IN_REPLICA, UNSUPPORTED_SYSTEM_OBJECTS, UNSUPPORTED_TABLES_WITH_REPLICA_IDENTITY, SELECTED_OBJECTS_NOT_EXIST_ON_SOURCE }
	}
	case class SqlExternalSyncSettingError(
	  /** Can be `sql#externalSyncSettingError` or `sql#externalSyncSettingWarning`. */
		kind: Option[String] = None,
	  /** Identifies the specific error that occurred. */
		`type`: Option[Schema.SqlExternalSyncSettingError.TypeEnum] = None,
	  /** Additional information about the error encountered. */
		detail: Option[String] = None
	)
	
	object SqlInstancesStartExternalSyncRequest {
		enum SyncModeEnum extends Enum[SyncModeEnum] { case EXTERNAL_SYNC_MODE_UNSPECIFIED, ONLINE, OFFLINE }
		enum SyncParallelLevelEnum extends Enum[SyncParallelLevelEnum] { case EXTERNAL_SYNC_PARALLEL_LEVEL_UNSPECIFIED, MIN, OPTIMAL, MAX }
		enum MigrationTypeEnum extends Enum[MigrationTypeEnum] { case MIGRATION_TYPE_UNSPECIFIED, LOGICAL, PHYSICAL }
	}
	case class SqlInstancesStartExternalSyncRequest(
	  /** External sync mode. */
		syncMode: Option[Schema.SqlInstancesStartExternalSyncRequest.SyncModeEnum] = None,
	  /** Whether to skip the verification step (VESS). */
		skipVerification: Option[Boolean] = None,
	  /** MySQL-specific settings for start external sync. */
		mysqlSyncConfig: Option[Schema.MySqlSyncConfig] = None,
	  /** Optional. Parallel level for initial data sync. Currently only applicable for MySQL. */
		syncParallelLevel: Option[Schema.SqlInstancesStartExternalSyncRequest.SyncParallelLevelEnum] = None,
	  /** Optional. MigrationType configures the migration to use physical files or logical dump files. If not set, then the logical dump file configuration is used. Valid values are `LOGICAL` or `PHYSICAL`. Only applicable to MySQL. */
		migrationType: Option[Schema.SqlInstancesStartExternalSyncRequest.MigrationTypeEnum] = None
	)
	
	case class PerformDiskShrinkContext(
	  /** The target disk shrink size in GigaBytes. */
		targetSizeGb: Option[String] = None
	)
	
	case class SqlInstancesGetDiskShrinkConfigResponse(
	  /** This is always `sql#getDiskShrinkConfig`. */
		kind: Option[String] = None,
	  /** The minimum size to which a disk can be shrunk in GigaBytes. */
		minimalTargetSizeGb: Option[String] = None,
	  /** Additional message to customers. */
		message: Option[String] = None
	)
	
	case class SqlInstancesResetReplicaSizeRequest(
	
	)
	
	case class SqlInstancesGetLatestRecoveryTimeResponse(
	  /** This is always `sql#getLatestRecoveryTime`. */
		kind: Option[String] = None,
	  /** Timestamp, identifies the latest recovery time of the source instance. */
		latestRecoveryTime: Option[String] = None
	)
	
	case class InstancesAcquireSsrsLeaseRequest(
	  /** Contains details about the acquire SSRS lease operation. */
		acquireSsrsLeaseContext: Option[Schema.AcquireSsrsLeaseContext] = None
	)
	
	case class SqlInstancesAcquireSsrsLeaseResponse(
	  /** The unique identifier for this operation. */
		operationId: Option[String] = None
	)
	
	case class SqlInstancesReleaseSsrsLeaseResponse(
	  /** The unique identifier for this operation. */
		operationId: Option[String] = None
	)
	
	object BackupRun {
		enum StatusEnum extends Enum[StatusEnum] { case SQL_BACKUP_RUN_STATUS_UNSPECIFIED, ENQUEUED, OVERDUE, RUNNING, FAILED, SUCCESSFUL, SKIPPED, DELETION_PENDING, DELETION_FAILED, DELETED }
		enum TypeEnum extends Enum[TypeEnum] { case SQL_BACKUP_RUN_TYPE_UNSPECIFIED, AUTOMATED, ON_DEMAND }
		enum BackupKindEnum extends Enum[BackupKindEnum] { case SQL_BACKUP_KIND_UNSPECIFIED, SNAPSHOT, PHYSICAL }
	}
	case class BackupRun(
	  /** This is always `sql#backupRun`. */
		kind: Option[String] = None,
	  /** The status of this run. */
		status: Option[Schema.BackupRun.StatusEnum] = None,
	  /** The time the run was enqueued in UTC timezone in [RFC 3339](https://tools.ietf.org/html/rfc3339) format, for example `2012-11-15T16:19:00.094Z`. */
		enqueuedTime: Option[String] = None,
	  /** The identifier for this backup run. Unique only for a specific Cloud SQL instance. */
		id: Option[String] = None,
	  /** The time the backup operation actually started in UTC timezone in [RFC 3339](https://tools.ietf.org/html/rfc3339) format, for example `2012-11-15T16:19:00.094Z`. */
		startTime: Option[String] = None,
	  /** The time the backup operation completed in UTC timezone in [RFC 3339](https://tools.ietf.org/html/rfc3339) format, for example `2012-11-15T16:19:00.094Z`. */
		endTime: Option[String] = None,
	  /** Information about why the backup operation failed. This is only present if the run has the FAILED status. */
		error: Option[Schema.OperationError] = None,
	  /** The type of this run; can be either "AUTOMATED" or "ON_DEMAND" or "FINAL". This field defaults to "ON_DEMAND" and is ignored, when specified for insert requests. */
		`type`: Option[Schema.BackupRun.TypeEnum] = None,
	  /** The description of this run, only applicable to on-demand backups. */
		description: Option[String] = None,
	  /** The start time of the backup window during which this the backup was attempted in [RFC 3339](https://tools.ietf.org/html/rfc3339) format, for example `2012-11-15T16:19:00.094Z`. */
		windowStartTime: Option[String] = None,
	  /** Name of the database instance. */
		instance: Option[String] = None,
	  /** The URI of this resource. */
		selfLink: Option[String] = None,
	  /** Location of the backups. */
		location: Option[String] = None,
	  /** Encryption configuration specific to a backup. */
		diskEncryptionConfiguration: Option[Schema.DiskEncryptionConfiguration] = None,
	  /** Encryption status specific to a backup. */
		diskEncryptionStatus: Option[Schema.DiskEncryptionStatus] = None,
	  /** Specifies the kind of backup, PHYSICAL or DEFAULT_SNAPSHOT. */
		backupKind: Option[Schema.BackupRun.BackupKindEnum] = None,
	  /** Backup time zone to prevent restores to an instance with a different time zone. Now relevant only for SQL Server. */
		timeZone: Option[String] = None,
	  /** Output only. The maximum chargeable bytes for the backup. */
		maxChargeableBytes: Option[String] = None
	)
	
	case class BackupRunsListResponse(
	  /** This is always `sql#backupRunsList`. */
		kind: Option[String] = None,
	  /** A list of backup runs in reverse chronological order of the enqueued time. */
		items: Option[List[Schema.BackupRun]] = None,
	  /** The continuation token, used to page through large result sets. Provide this value in a subsequent request to return the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object ConnectSettings {
		enum DatabaseVersionEnum extends Enum[DatabaseVersionEnum] { case SQL_DATABASE_VERSION_UNSPECIFIED, MYSQL_5_1, MYSQL_5_5, MYSQL_5_6, MYSQL_5_7, MYSQL_8_0, MYSQL_8_0_18, MYSQL_8_0_26, MYSQL_8_0_27, MYSQL_8_0_28, MYSQL_8_0_29, MYSQL_8_0_30, MYSQL_8_0_31, MYSQL_8_0_32, MYSQL_8_0_33, MYSQL_8_0_34, MYSQL_8_0_35, MYSQL_8_0_36, MYSQL_8_0_37, MYSQL_8_0_38, MYSQL_8_0_39, MYSQL_8_0_40, MYSQL_8_0_41, MYSQL_8_0_42, MYSQL_8_4, SQLSERVER_2017_STANDARD, SQLSERVER_2017_ENTERPRISE, SQLSERVER_2017_EXPRESS, SQLSERVER_2017_WEB, POSTGRES_9_6, POSTGRES_10, POSTGRES_11, POSTGRES_12, POSTGRES_13, POSTGRES_14, POSTGRES_15, POSTGRES_16, POSTGRES_17, SQLSERVER_2019_STANDARD, SQLSERVER_2019_ENTERPRISE, SQLSERVER_2019_EXPRESS, SQLSERVER_2019_WEB, SQLSERVER_2022_STANDARD, SQLSERVER_2022_ENTERPRISE, SQLSERVER_2022_EXPRESS, SQLSERVER_2022_WEB }
		enum BackendTypeEnum extends Enum[BackendTypeEnum] { case SQL_BACKEND_TYPE_UNSPECIFIED, FIRST_GEN, SECOND_GEN, EXTERNAL }
		enum ServerCaModeEnum extends Enum[ServerCaModeEnum] { case CA_MODE_UNSPECIFIED, GOOGLE_MANAGED_INTERNAL_CA, GOOGLE_MANAGED_CAS_CA }
	}
	case class ConnectSettings(
	  /** This is always `sql#connectSettings`. */
		kind: Option[String] = None,
	  /** SSL configuration. */
		serverCaCert: Option[Schema.SslCert] = None,
	  /** The assigned IP addresses for the instance. */
		ipAddresses: Option[List[Schema.IpMapping]] = None,
	  /** The cloud region for the instance. For example, `us-central1`, `europe-west1`. The region cannot be changed after instance creation. */
		region: Option[String] = None,
	  /** The database engine type and version. The `databaseVersion` field cannot be changed after instance creation. MySQL instances: `MYSQL_8_0`, `MYSQL_5_7` (default), or `MYSQL_5_6`. PostgreSQL instances: `POSTGRES_9_6`, `POSTGRES_10`, `POSTGRES_11`, `POSTGRES_12` (default), `POSTGRES_13`, or `POSTGRES_14`. SQL Server instances: `SQLSERVER_2017_STANDARD` (default), `SQLSERVER_2017_ENTERPRISE`, `SQLSERVER_2017_EXPRESS`, `SQLSERVER_2017_WEB`, `SQLSERVER_2019_STANDARD`, `SQLSERVER_2019_ENTERPRISE`, `SQLSERVER_2019_EXPRESS`, or `SQLSERVER_2019_WEB`. */
		databaseVersion: Option[Schema.ConnectSettings.DatabaseVersionEnum] = None,
	  /** `SECOND_GEN`: Cloud SQL database instance. `EXTERNAL`: A database server that is not managed by Google. This property is read-only; use the `tier` property in the `settings` object to determine the database type. */
		backendType: Option[Schema.ConnectSettings.BackendTypeEnum] = None,
	  /** Whether PSC connectivity is enabled for this instance. */
		pscEnabled: Option[Boolean] = None,
	  /** The dns name of the instance. */
		dnsName: Option[String] = None,
	  /** Specify what type of CA is used for the server certificate. */
		serverCaMode: Option[Schema.ConnectSettings.ServerCaModeEnum] = None
	)
	
	case class GenerateEphemeralCertRequest(
	  /** PEM encoded public key to include in the signed certificate. */
		public_key: Option[String] = None,
	  /** Optional. Access token to include in the signed certificate. */
		access_token: Option[String] = None,
	  /** Optional. Optional snapshot read timestamp to trade freshness for performance. */
		readTime: Option[String] = None,
	  /** Optional. If set, it will contain the cert valid duration. */
		validDuration: Option[String] = None
	)
	
	case class GenerateEphemeralCertResponse(
	  /** Generated cert */
		ephemeralCert: Option[Schema.SslCert] = None
	)
	
	case class Database(
	  /** This is always `sql#database`. */
		kind: Option[String] = None,
	  /** The Cloud SQL charset value. */
		charset: Option[String] = None,
	  /** The Cloud SQL collation value. */
		collation: Option[String] = None,
	  /** This field is deprecated and will be removed from a future version of the API. */
		etag: Option[String] = None,
	  /** The name of the database in the Cloud SQL instance. This does not include the project ID or instance name. */
		name: Option[String] = None,
	  /** The name of the Cloud SQL instance. This does not include the project ID. */
		instance: Option[String] = None,
	  /** The URI of this resource. */
		selfLink: Option[String] = None,
	  /** The project ID of the project containing the Cloud SQL database. The Google apps domain is prefixed if applicable. */
		project: Option[String] = None,
		sqlserverDatabaseDetails: Option[Schema.SqlServerDatabaseDetails] = None
	)
	
	case class SqlServerDatabaseDetails(
	  /** The version of SQL Server with which the database is to be made compatible */
		compatibilityLevel: Option[Int] = None,
	  /** The recovery model of a SQL Server database */
		recoveryModel: Option[String] = None
	)
	
	case class DatabasesListResponse(
	  /** This is always `sql#databasesList`. */
		kind: Option[String] = None,
	  /** List of database resources in the instance. */
		items: Option[List[Schema.Database]] = None
	)
	
	case class FlagsListResponse(
	  /** This is always `sql#flagsList`. */
		kind: Option[String] = None,
	  /** List of flags. */
		items: Option[List[Schema.Flag]] = None
	)
	
	object Flag {
		enum TypeEnum extends Enum[TypeEnum] { case SQL_FLAG_TYPE_UNSPECIFIED, BOOLEAN, STRING, INTEGER, NONE, MYSQL_TIMEZONE_OFFSET, FLOAT, REPEATED_STRING }
		enum AppliesToEnum extends Enum[AppliesToEnum] { case SQL_DATABASE_VERSION_UNSPECIFIED, MYSQL_5_1, MYSQL_5_5, MYSQL_5_6, MYSQL_5_7, MYSQL_8_0, MYSQL_8_0_18, MYSQL_8_0_26, MYSQL_8_0_27, MYSQL_8_0_28, MYSQL_8_0_29, MYSQL_8_0_30, MYSQL_8_0_31, MYSQL_8_0_32, MYSQL_8_0_33, MYSQL_8_0_34, MYSQL_8_0_35, MYSQL_8_0_36, MYSQL_8_0_37, MYSQL_8_0_38, MYSQL_8_0_39, MYSQL_8_0_40, MYSQL_8_0_41, MYSQL_8_0_42, MYSQL_8_4, SQLSERVER_2017_STANDARD, SQLSERVER_2017_ENTERPRISE, SQLSERVER_2017_EXPRESS, SQLSERVER_2017_WEB, POSTGRES_9_6, POSTGRES_10, POSTGRES_11, POSTGRES_12, POSTGRES_13, POSTGRES_14, POSTGRES_15, POSTGRES_16, POSTGRES_17, SQLSERVER_2019_STANDARD, SQLSERVER_2019_ENTERPRISE, SQLSERVER_2019_EXPRESS, SQLSERVER_2019_WEB, SQLSERVER_2022_STANDARD, SQLSERVER_2022_ENTERPRISE, SQLSERVER_2022_EXPRESS, SQLSERVER_2022_WEB }
	}
	case class Flag(
	  /** This is the name of the flag. Flag names always use underscores, not hyphens, for example: `max_allowed_packet` */
		name: Option[String] = None,
	  /** The type of the flag. Flags are typed to being `BOOLEAN`, `STRING`, `INTEGER` or `NONE`. `NONE` is used for flags that do not take a value, such as `skip_grant_tables`. */
		`type`: Option[Schema.Flag.TypeEnum] = None,
	  /** The database version this flag applies to. Can be MySQL instances: `MYSQL_8_0`, `MYSQL_8_0_18`, `MYSQL_8_0_26`, `MYSQL_5_7`, or `MYSQL_5_6`. PostgreSQL instances: `POSTGRES_9_6`, `POSTGRES_10`, `POSTGRES_11` or `POSTGRES_12`. SQL Server instances: `SQLSERVER_2017_STANDARD`, `SQLSERVER_2017_ENTERPRISE`, `SQLSERVER_2017_EXPRESS`, `SQLSERVER_2017_WEB`, `SQLSERVER_2019_STANDARD`, `SQLSERVER_2019_ENTERPRISE`, `SQLSERVER_2019_EXPRESS`, or `SQLSERVER_2019_WEB`. See [the complete list](/sql/docs/mysql/admin-api/rest/v1/SqlDatabaseVersion). */
		appliesTo: Option[List[Schema.Flag.AppliesToEnum]] = None,
	  /** For `STRING` flags, a list of strings that the value can be set to. */
		allowedStringValues: Option[List[String]] = None,
	  /** For `INTEGER` flags, the minimum allowed value. */
		minValue: Option[String] = None,
	  /** For `INTEGER` flags, the maximum allowed value. */
		maxValue: Option[String] = None,
	  /** Indicates whether changing this flag will trigger a database restart. Only applicable to Second Generation instances. */
		requiresRestart: Option[Boolean] = None,
	  /** This is always `sql#flag`. */
		kind: Option[String] = None,
	  /** Whether or not the flag is considered in beta. */
		inBeta: Option[Boolean] = None,
	  /** Use this field if only certain integers are accepted. Can be combined with min_value and max_value to add additional values. */
		allowedIntValues: Option[List[String]] = None
	)
	
	case class OperationsListResponse(
	  /** This is always `sql#operationsList`. */
		kind: Option[String] = None,
	  /** List of operation resources. */
		items: Option[List[Schema.Operation]] = None,
	  /** The continuation token, used to page through large result sets. Provide this value in a subsequent request to return the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class SslCertsInsertRequest(
	  /** User supplied name. Must be a distinct name from the other certificates for this instance. */
		commonName: Option[String] = None
	)
	
	case class SslCertsInsertResponse(
	  /** This is always `sql#sslCertsInsert`. */
		kind: Option[String] = None,
	  /** The operation to track the ssl certs insert request. */
		operation: Option[Schema.Operation] = None,
	  /** The server Certificate Authority's certificate. If this is missing you can force a new one to be generated by calling resetSslConfig method on instances resource. */
		serverCaCert: Option[Schema.SslCert] = None,
	  /** The new client certificate and private key. */
		clientCert: Option[Schema.SslCertDetail] = None
	)
	
	case class SslCertDetail(
	  /** The public information about the cert. */
		certInfo: Option[Schema.SslCert] = None,
	  /** The private key for the client cert, in pem format. Keep private in order to protect your security. */
		certPrivateKey: Option[String] = None
	)
	
	case class SslCertsListResponse(
	  /** This is always `sql#sslCertsList`. */
		kind: Option[String] = None,
	  /** List of client certificates for the instance. */
		items: Option[List[Schema.SslCert]] = None
	)
	
	case class TiersListResponse(
	  /** This is always `sql#tiersList`. */
		kind: Option[String] = None,
	  /** List of tiers. */
		items: Option[List[Schema.Tier]] = None
	)
	
	case class Tier(
	  /** An identifier for the machine type, for example, `db-custom-1-3840`. For related information, see [Pricing](/sql/pricing). */
		tier: Option[String] = None,
	  /** The maximum RAM usage of this tier in bytes. */
		RAM: Option[String] = None,
	  /** This is always `sql#tier`. */
		kind: Option[String] = None,
	  /** The maximum disk size of this tier in bytes. */
		DiskQuota: Option[String] = None,
	  /** The applicable regions for this tier. */
		region: Option[List[String]] = None
	)
	
	object User {
		enum TypeEnum extends Enum[TypeEnum] { case BUILT_IN, CLOUD_IAM_USER, CLOUD_IAM_SERVICE_ACCOUNT, CLOUD_IAM_GROUP, CLOUD_IAM_GROUP_USER, CLOUD_IAM_GROUP_SERVICE_ACCOUNT }
		enum DualPasswordTypeEnum extends Enum[DualPasswordTypeEnum] { case DUAL_PASSWORD_TYPE_UNSPECIFIED, NO_MODIFY_DUAL_PASSWORD, NO_DUAL_PASSWORD, DUAL_PASSWORD }
	}
	case class User(
	  /** This is always `sql#user`. */
		kind: Option[String] = None,
	  /** The password for the user. */
		password: Option[String] = None,
	  /** This field is deprecated and will be removed from a future version of the API. */
		etag: Option[String] = None,
	  /** The name of the user in the Cloud SQL instance. Can be omitted for `update` because it is already specified in the URL. */
		name: Option[String] = None,
	  /** Optional. The host from which the user can connect. For `insert` operations, host defaults to an empty string. For `update` operations, host is specified as part of the request URL. The host name cannot be updated after insertion. For a MySQL instance, it's required; for a PostgreSQL or SQL Server instance, it's optional. */
		host: Option[String] = None,
	  /** The name of the Cloud SQL instance. This does not include the project ID. Can be omitted for `update` because it is already specified on the URL. */
		instance: Option[String] = None,
	  /** The project ID of the project containing the Cloud SQL database. The Google apps domain is prefixed if applicable. Can be omitted for `update` because it is already specified on the URL. */
		project: Option[String] = None,
	  /** The user type. It determines the method to authenticate the user during login. The default is the database's built-in user type. */
		`type`: Option[Schema.User.TypeEnum] = None,
		sqlserverUserDetails: Option[Schema.SqlServerUserDetails] = None,
	  /** User level password validation policy. */
		passwordPolicy: Option[Schema.UserPasswordValidationPolicy] = None,
	  /** Dual password status for the user. */
		dualPasswordType: Option[Schema.User.DualPasswordTypeEnum] = None
	)
	
	case class SqlServerUserDetails(
	  /** If the user has been disabled */
		disabled: Option[Boolean] = None,
	  /** The server roles for this user */
		serverRoles: Option[List[String]] = None
	)
	
	case class UserPasswordValidationPolicy(
	  /** Number of failed login attempts allowed before user get locked. */
		allowedFailedAttempts: Option[Int] = None,
	  /** Expiration duration after password is updated. */
		passwordExpirationDuration: Option[String] = None,
	  /** If true, failed login attempts check will be enabled. */
		enableFailedAttemptsCheck: Option[Boolean] = None,
	  /** Output only. Read-only password status. */
		status: Option[Schema.PasswordStatus] = None,
	  /** If true, the user must specify the current password before changing the password. This flag is supported only for MySQL. */
		enablePasswordVerification: Option[Boolean] = None
	)
	
	case class PasswordStatus(
	  /** If true, user does not have login privileges. */
		locked: Option[Boolean] = None,
	  /** The expiration time of the current password. */
		passwordExpirationTime: Option[String] = None
	)
	
	case class UsersListResponse(
	  /** This is always `sql#usersList`. */
		kind: Option[String] = None,
	  /** List of user resources in the instance. */
		items: Option[List[Schema.User]] = None,
	  /** Unused. */
		nextPageToken: Option[String] = None
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
}
