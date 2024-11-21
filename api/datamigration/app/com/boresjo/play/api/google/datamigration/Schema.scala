package com.boresjo.play.api.google.datamigration

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
	
	case class ListMigrationJobsResponse(
	  /** The list of migration jobs objects. */
		migrationJobs: Option[List[Schema.MigrationJob]] = None,
	  /** A token which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object MigrationJob {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, MAINTENANCE, DRAFT, CREATING, NOT_STARTED, RUNNING, FAILED, COMPLETED, DELETING, STOPPING, STOPPED, DELETED, UPDATING, STARTING, RESTARTING, RESUMING }
		enum PhaseEnum extends Enum[PhaseEnum] { case PHASE_UNSPECIFIED, FULL_DUMP, CDC, PROMOTE_IN_PROGRESS, WAITING_FOR_SOURCE_WRITES_TO_STOP, PREPARING_THE_DUMP, READY_FOR_PROMOTE }
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, ONE_TIME, CONTINUOUS }
		enum DumpTypeEnum extends Enum[DumpTypeEnum] { case DUMP_TYPE_UNSPECIFIED, LOGICAL, PHYSICAL }
	}
	case class MigrationJob(
	  /** The name (URI) of this migration job resource, in the form of: projects/{project}/locations/{location}/migrationJobs/{migrationJob}. */
		name: Option[String] = None,
	  /** Output only. The timestamp when the migration job resource was created. A timestamp in RFC3339 UTC "Zulu" format, accurate to nanoseconds. Example: "2014-10-02T15:01:23.045123456Z". */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the migration job resource was last updated. A timestamp in RFC3339 UTC "Zulu" format, accurate to nanoseconds. Example: "2014-10-02T15:01:23.045123456Z". */
		updateTime: Option[String] = None,
	  /** The resource labels for migration job to use to annotate any related underlying resources such as Compute Engine VMs. An object containing a list of "key": "value" pairs. Example: `{ "name": "wrench", "mass": "1.3kg", "count": "3" }`. */
		labels: Option[Map[String, String]] = None,
	  /** The migration job display name. */
		displayName: Option[String] = None,
	  /** The current migration job state. */
		state: Option[Schema.MigrationJob.StateEnum] = None,
	  /** Output only. The current migration job phase. */
		phase: Option[Schema.MigrationJob.PhaseEnum] = None,
	  /** Required. The migration job type. */
		`type`: Option[Schema.MigrationJob.TypeEnum] = None,
	  /** The path to the dump file in Google Cloud Storage, in the format: (gs://[BUCKET_NAME]/[OBJECT_NAME]). This field and the "dump_flags" field are mutually exclusive. */
		dumpPath: Option[String] = None,
	  /** The initial dump flags. This field and the "dump_path" field are mutually exclusive. */
		dumpFlags: Option[Schema.DumpFlags] = None,
	  /** Required. The resource name (URI) of the source connection profile. */
		source: Option[String] = None,
	  /** Required. The resource name (URI) of the destination connection profile. */
		destination: Option[String] = None,
	  /** The details needed to communicate to the source over Reverse SSH tunnel connectivity. */
		reverseSshConnectivity: Option[Schema.ReverseSshConnectivity] = None,
	  /** The details of the VPC network that the source database is located in. */
		vpcPeeringConnectivity: Option[Schema.VpcPeeringConnectivity] = None,
	  /** static ip connectivity data (default, no additional details needed). */
		staticIpConnectivity: Option[Schema.StaticIpConnectivity] = None,
	  /** Output only. The duration of the migration job (in seconds). A duration in seconds with up to nine fractional digits, terminated by 's'. Example: "3.5s". */
		duration: Option[String] = None,
	  /** Output only. The error details in case of state FAILED. */
		error: Option[Schema.Status] = None,
	  /** The database engine type and provider of the source. */
		sourceDatabase: Option[Schema.DatabaseType] = None,
	  /** The database engine type and provider of the destination. */
		destinationDatabase: Option[Schema.DatabaseType] = None,
	  /** Output only. If the migration job is completed, the time when it was completed. */
		endTime: Option[String] = None,
	  /** The conversion workspace used by the migration. */
		conversionWorkspace: Option[Schema.ConversionWorkspaceInfo] = None,
	  /** This field can be used to select the entities to migrate as part of the migration job. It uses AIP-160 notation to select a subset of the entities configured on the associated conversion-workspace. This field should not be set on migration-jobs that are not associated with a conversion workspace. */
		filter: Option[String] = None,
	  /** The CMEK (customer-managed encryption key) fully qualified key name used for the migration job. This field supports all migration jobs types except for: &#42; Mysql to Mysql (use the cmek field in the cloudsql connection profile instead). &#42; PostrgeSQL to PostgreSQL (use the cmek field in the cloudsql connection profile instead). &#42; PostgreSQL to AlloyDB (use the kms_key_name field in the alloydb connection profile instead). Each Cloud CMEK key has the following format: projects/[PROJECT]/locations/[REGION]/keyRings/[RING]/cryptoKeys/[KEY_NAME] */
		cmekKeyName: Option[String] = None,
	  /** Optional. Data dump parallelism settings used by the migration. */
		performanceConfig: Option[Schema.PerformanceConfig] = None,
	  /** Optional. Configuration for SQL Server homogeneous migration. */
		sqlserverHomogeneousMigrationJobConfig: Option[Schema.SqlServerHomogeneousMigrationJobConfig] = None,
	  /** Optional. The type of the data dump. Supported for MySQL to CloudSQL for MySQL migrations only. */
		dumpType: Option[Schema.MigrationJob.DumpTypeEnum] = None,
	  /** Configuration for heterogeneous &#42;&#42;Oracle to Cloud SQL for PostgreSQL&#42;&#42; and &#42;&#42;Oracle to AlloyDB for PostgreSQL&#42;&#42; migrations. */
		oracleToPostgresConfig: Option[Schema.OracleToPostgresConfig] = None
	)
	
	case class DumpFlags(
	  /** The flags for the initial dump. */
		dumpFlags: Option[List[Schema.DumpFlag]] = None
	)
	
	case class DumpFlag(
	  /** The name of the flag */
		name: Option[String] = None,
	  /** The value of the flag. */
		value: Option[String] = None
	)
	
	case class ReverseSshConnectivity(
	  /** Required. The IP of the virtual machine (Compute Engine) used as the bastion server for the SSH tunnel. */
		vmIp: Option[String] = None,
	  /** Required. The forwarding port of the virtual machine (Compute Engine) used as the bastion server for the SSH tunnel. */
		vmPort: Option[Int] = None,
	  /** The name of the virtual machine (Compute Engine) used as the bastion server for the SSH tunnel. */
		vm: Option[String] = None,
	  /** The name of the VPC to peer with the Cloud SQL private network. */
		vpc: Option[String] = None
	)
	
	case class VpcPeeringConnectivity(
	  /** The name of the VPC network to peer with the Cloud SQL private network. */
		vpc: Option[String] = None
	)
	
	case class StaticIpConnectivity(
	
	)
	
	object DatabaseType {
		enum ProviderEnum extends Enum[ProviderEnum] { case DATABASE_PROVIDER_UNSPECIFIED, CLOUDSQL, RDS, AURORA, ALLOYDB }
		enum EngineEnum extends Enum[EngineEnum] { case DATABASE_ENGINE_UNSPECIFIED, MYSQL, POSTGRESQL, SQLSERVER, ORACLE }
	}
	case class DatabaseType(
	  /** The database provider. */
		provider: Option[Schema.DatabaseType.ProviderEnum] = None,
	  /** The database engine. */
		engine: Option[Schema.DatabaseType.EngineEnum] = None
	)
	
	case class ConversionWorkspaceInfo(
	  /** The resource name (URI) of the conversion workspace. */
		name: Option[String] = None,
	  /** The commit ID of the conversion workspace. */
		commitId: Option[String] = None
	)
	
	object PerformanceConfig {
		enum DumpParallelLevelEnum extends Enum[DumpParallelLevelEnum] { case DUMP_PARALLEL_LEVEL_UNSPECIFIED, MIN, OPTIMAL, MAX }
	}
	case class PerformanceConfig(
	  /** Initial dump parallelism level. */
		dumpParallelLevel: Option[Schema.PerformanceConfig.DumpParallelLevelEnum] = None
	)
	
	case class SqlServerHomogeneousMigrationJobConfig(
	  /** Required. Pattern that describes the default backup naming strategy. The specified pattern should ensure lexicographical order of backups. The pattern must define one of the following capture group sets: Capture group set #1 yy/yyyy - year, 2 or 4 digits mm - month number, 1-12 dd - day of month, 1-31 hh - hour of day, 00-23 mi - minutes, 00-59 ss - seconds, 00-59 Example: For backup file TestDB_20230802_155400.trn, use pattern: (?.&#42;)_backup_(?\d{4})(?\d{2})(?\d{2})_(?\d{2})(?\d{2})(?\d{2}).trn Capture group set #2 timestamp - unix timestamp Example: For backup file TestDB.1691448254.trn, use pattern: (?.&#42;)\.(?\d&#42;).trn or (?.&#42;)\.(?\d&#42;).trn */
		backupFilePattern: Option[String] = None,
	  /** Required. Backup details per database in Cloud Storage. */
		databaseBackups: Option[List[Schema.SqlServerDatabaseBackup]] = None,
	  /** Optional. Enable differential backups. */
		useDiffBackup: Option[Boolean] = None,
	  /** Optional. Promote databases when ready. */
		promoteWhenReady: Option[Boolean] = None
	)
	
	case class SqlServerDatabaseBackup(
	  /** Required. Name of a SQL Server database for which to define backup configuration. */
		database: Option[String] = None,
	  /** Optional. Encryption settings for the database. Required if provided database backups are encrypted. Encryption settings include path to certificate, path to certificate private key, and key password. */
		encryptionOptions: Option[Schema.SqlServerEncryptionOptions] = None
	)
	
	case class SqlServerEncryptionOptions(
	  /** Required. Path to certificate. */
		certPath: Option[String] = None,
	  /** Required. Path to certificate private key. */
		pvkPath: Option[String] = None,
	  /** Required. Input only. Private key password. */
		pvkPassword: Option[String] = None
	)
	
	case class OracleToPostgresConfig(
	  /** Optional. Configuration for Oracle source. */
		oracleSourceConfig: Option[Schema.OracleSourceConfig] = None,
	  /** Optional. Configuration for Postgres destination. */
		postgresDestinationConfig: Option[Schema.PostgresDestinationConfig] = None
	)
	
	case class OracleSourceConfig(
	  /** Optional. Maximum number of connections Database Migration Service will open to the source for full dump phase. */
		maxConcurrentFullDumpConnections: Option[Int] = None,
	  /** Optional. Maximum number of connections Database Migration Service will open to the source for CDC phase. */
		maxConcurrentCdcConnections: Option[Int] = None,
	  /** Optional. Whether to skip full dump or not. */
		skipFullDump: Option[Boolean] = None,
	  /** Optional. The schema change number (SCN) to start CDC data migration from. */
		cdcStartPosition: Option[String] = None,
	  /** Use LogMiner. */
		logMiner: Option[Schema.LogMiner] = None,
	  /** Use Binary Log Parser. */
		binaryLogParser: Option[Schema.BinaryLogParser] = None
	)
	
	case class LogMiner(
	
	)
	
	case class BinaryLogParser(
	  /** Use Oracle ASM. */
		oracleAsmLogFileAccess: Option[Schema.OracleAsmLogFileAccess] = None,
	  /** Use Oracle directories. */
		logFileDirectories: Option[Schema.LogFileDirectories] = None
	)
	
	case class OracleAsmLogFileAccess(
	
	)
	
	case class LogFileDirectories(
	  /** Required. Oracle directory for online logs. */
		onlineLogDirectory: Option[String] = None,
	  /** Required. Oracle directory for archived logs. */
		archivedLogDirectory: Option[String] = None
	)
	
	case class PostgresDestinationConfig(
	  /** Optional. Maximum number of connections Database Migration Service will open to the destination for data migration. */
		maxConcurrentConnections: Option[Int] = None,
	  /** Optional. Timeout for data migration transactions. */
		transactionTimeout: Option[String] = None
	)
	
	case class StartMigrationJobRequest(
	  /** Optional. Start the migration job without running prior configuration verification. Defaults to `false`. */
		skipValidation: Option[Boolean] = None
	)
	
	case class StopMigrationJobRequest(
	
	)
	
	case class ResumeMigrationJobRequest(
	  /** Optional. Resume the migration job without running prior configuration verification. Defaults to `false`. */
		skipValidation: Option[Boolean] = None
	)
	
	case class PromoteMigrationJobRequest(
	
	)
	
	case class DemoteDestinationRequest(
	
	)
	
	case class VerifyMigrationJobRequest(
	  /** Optional. Field mask is used to specify the changed fields to be verified. It will not update the migration job. */
		updateMask: Option[String] = None,
	  /** Optional. The changed migration job parameters to verify. It will not update the migration job. */
		migrationJob: Option[Schema.MigrationJob] = None
	)
	
	case class RestartMigrationJobRequest(
	  /** Optional. Restart the migration job without running prior configuration verification. Defaults to `false`. */
		skipValidation: Option[Boolean] = None
	)
	
	case class GenerateSshScriptRequest(
	  /** Required. Bastion VM Instance name to use or to create. */
		vm: Option[String] = None,
	  /** The VM creation configuration */
		vmCreationConfig: Option[Schema.VmCreationConfig] = None,
	  /** The VM selection configuration */
		vmSelectionConfig: Option[Schema.VmSelectionConfig] = None,
	  /** The port that will be open on the bastion host. */
		vmPort: Option[Int] = None
	)
	
	case class VmCreationConfig(
	  /** Required. VM instance machine type to create. */
		vmMachineType: Option[String] = None,
	  /** The Google Cloud Platform zone to create the VM in. */
		vmZone: Option[String] = None,
	  /** The subnet name the vm needs to be created in. */
		subnet: Option[String] = None
	)
	
	case class VmSelectionConfig(
	  /** Required. The Google Cloud Platform zone the VM is located. */
		vmZone: Option[String] = None
	)
	
	case class SshScript(
	  /** The ssh configuration script. */
		script: Option[String] = None
	)
	
	case class GenerateTcpProxyScriptRequest(
	  /** Required. The name of the Compute instance that will host the proxy. */
		vmName: Option[String] = None,
	  /** Required. The type of the Compute instance that will host the proxy. */
		vmMachineType: Option[String] = None,
	  /** Optional. The Google Cloud Platform zone to create the VM in. The fully qualified name of the zone must be specified, including the region name, for example "us-central1-b". If not specified, uses the "-b" zone of the destination Connection Profile's region. */
		vmZone: Option[String] = None,
	  /** Required. The name of the subnet the Compute instance will use for private connectivity. Must be supplied in the form of projects/{project}/regions/{region}/subnetworks/{subnetwork}. Note: the region for the subnet must match the Compute instance region. */
		vmSubnet: Option[String] = None
	)
	
	case class TcpProxyScript(
	  /** The TCP Proxy configuration script. */
		script: Option[String] = None
	)
	
	case class ListConnectionProfilesResponse(
	  /** The response list of connection profiles. */
		connectionProfiles: Option[List[Schema.ConnectionProfile]] = None,
	  /** A token which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object ConnectionProfile {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, DRAFT, CREATING, READY, UPDATING, DELETING, DELETED, FAILED }
		enum RoleEnum extends Enum[RoleEnum] { case ROLE_UNSPECIFIED, SOURCE, DESTINATION }
		enum ProviderEnum extends Enum[ProviderEnum] { case DATABASE_PROVIDER_UNSPECIFIED, CLOUDSQL, RDS, AURORA, ALLOYDB }
	}
	case class ConnectionProfile(
	  /** The name of this connection profile resource in the form of projects/{project}/locations/{location}/connectionProfiles/{connectionProfile}. */
		name: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. A timestamp in RFC3339 UTC "Zulu" format, accurate to nanoseconds. Example: "2014-10-02T15:01:23.045123456Z". */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was last updated. A timestamp in RFC3339 UTC "Zulu" format, accurate to nanoseconds. Example: "2014-10-02T15:01:23.045123456Z". */
		updateTime: Option[String] = None,
	  /** The resource labels for connection profile to use to annotate any related underlying resources such as Compute Engine VMs. An object containing a list of "key": "value" pairs. Example: `{ "name": "wrench", "mass": "1.3kg", "count": "3" }`. */
		labels: Option[Map[String, String]] = None,
	  /** The current connection profile state (e.g. DRAFT, READY, or FAILED). */
		state: Option[Schema.ConnectionProfile.StateEnum] = None,
	  /** The connection profile display name. */
		displayName: Option[String] = None,
	  /** Optional. The connection profile role. */
		role: Option[Schema.ConnectionProfile.RoleEnum] = None,
	  /** A MySQL database connection profile. */
		mysql: Option[Schema.MySqlConnectionProfile] = None,
	  /** A PostgreSQL database connection profile. */
		postgresql: Option[Schema.PostgreSqlConnectionProfile] = None,
	  /** Connection profile for a SQL Server data source. */
		sqlserver: Option[Schema.SqlServerConnectionProfile] = None,
	  /** An Oracle database connection profile. */
		oracle: Option[Schema.OracleConnectionProfile] = None,
	  /** A CloudSQL database connection profile. */
		cloudsql: Option[Schema.CloudSqlConnectionProfile] = None,
	  /** An AlloyDB cluster connection profile. */
		alloydb: Option[Schema.AlloyDbConnectionProfile] = None,
	  /** Output only. The error details in case of state FAILED. */
		error: Option[Schema.Status] = None,
	  /** The database provider. */
		provider: Option[Schema.ConnectionProfile.ProviderEnum] = None
	)
	
	case class MySqlConnectionProfile(
	  /** Required. The IP or hostname of the source MySQL database. */
		host: Option[String] = None,
	  /** Required. The network port of the source MySQL database. */
		port: Option[Int] = None,
	  /** Required. The username that Database Migration Service will use to connect to the database. The value is encrypted when stored in Database Migration Service. */
		username: Option[String] = None,
	  /** Required. Input only. The password for the user that Database Migration Service will be using to connect to the database. This field is not returned on request, and the value is encrypted when stored in Database Migration Service. */
		password: Option[String] = None,
	  /** Output only. Indicates If this connection profile password is stored. */
		passwordSet: Option[Boolean] = None,
	  /** SSL configuration for the destination to connect to the source database. */
		ssl: Option[Schema.SslConfig] = None,
	  /** If the source is a Cloud SQL database, use this field to provide the Cloud SQL instance ID of the source. */
		cloudSqlId: Option[String] = None
	)
	
	object SslConfig {
		enum TypeEnum extends Enum[TypeEnum] { case SSL_TYPE_UNSPECIFIED, SERVER_ONLY, SERVER_CLIENT, REQUIRED, NONE }
	}
	case class SslConfig(
	  /** Optional. The ssl config type according to 'client_key', 'client_certificate' and 'ca_certificate'. */
		`type`: Option[Schema.SslConfig.TypeEnum] = None,
	  /** Input only. The unencrypted PKCS#1 or PKCS#8 PEM-encoded private key associated with the Client Certificate. If this field is used then the 'client_certificate' field is mandatory. */
		clientKey: Option[String] = None,
	  /** Input only. The x509 PEM-encoded certificate that will be used by the replica to authenticate against the source database server.If this field is used then the 'client_key' field is mandatory. */
		clientCertificate: Option[String] = None,
	  /** Required. Input only. The x509 PEM-encoded certificate of the CA that signed the source database server's certificate. The replica will use this certificate to verify it's connecting to the right host. */
		caCertificate: Option[String] = None
	)
	
	object PostgreSqlConnectionProfile {
		enum NetworkArchitectureEnum extends Enum[NetworkArchitectureEnum] { case NETWORK_ARCHITECTURE_UNSPECIFIED, NETWORK_ARCHITECTURE_OLD_CSQL_PRODUCER, NETWORK_ARCHITECTURE_NEW_CSQL_PRODUCER }
	}
	case class PostgreSqlConnectionProfile(
	  /** Required. The IP or hostname of the source PostgreSQL database. */
		host: Option[String] = None,
	  /** Required. The network port of the source PostgreSQL database. */
		port: Option[Int] = None,
	  /** Required. The username that Database Migration Service will use to connect to the database. The value is encrypted when stored in Database Migration Service. */
		username: Option[String] = None,
	  /** Required. Input only. The password for the user that Database Migration Service will be using to connect to the database. This field is not returned on request, and the value is encrypted when stored in Database Migration Service. */
		password: Option[String] = None,
	  /** Optional. The name of the specific database within the host. */
		database: Option[String] = None,
	  /** Output only. Indicates If this connection profile password is stored. */
		passwordSet: Option[Boolean] = None,
	  /** SSL configuration for the destination to connect to the source database. */
		ssl: Option[Schema.SslConfig] = None,
	  /** If the source is a Cloud SQL database, use this field to provide the Cloud SQL instance ID of the source. */
		cloudSqlId: Option[String] = None,
	  /** Optional. If the destination is an AlloyDB database, use this field to provide the AlloyDB cluster ID. */
		alloydbClusterId: Option[String] = None,
	  /** Output only. If the source is a Cloud SQL database, this field indicates the network architecture it's associated with. */
		networkArchitecture: Option[Schema.PostgreSqlConnectionProfile.NetworkArchitectureEnum] = None,
	  /** Static ip connectivity data (default, no additional details needed). */
		staticIpConnectivity: Option[Schema.StaticIpConnectivity] = None,
	  /** Private service connect connectivity. */
		privateServiceConnectConnectivity: Option[Schema.PrivateServiceConnectConnectivity] = None
	)
	
	case class PrivateServiceConnectConnectivity(
	  /** Required. A service attachment that exposes a database, and has the following format: projects/{project}/regions/{region}/serviceAttachments/{service_attachment_name} */
		serviceAttachment: Option[String] = None
	)
	
	case class SqlServerConnectionProfile(
	  /** Required. The IP or hostname of the source SQL Server database. */
		host: Option[String] = None,
	  /** Required. The network port of the source SQL Server database. */
		port: Option[Int] = None,
	  /** Required. The username that Database Migration Service will use to connect to the database. The value is encrypted when stored in Database Migration Service. */
		username: Option[String] = None,
	  /** Required. Input only. The password for the user that Database Migration Service will be using to connect to the database. This field is not returned on request, and the value is encrypted when stored in Database Migration Service. */
		password: Option[String] = None,
	  /** Output only. Indicates whether a new password is included in the request. */
		passwordSet: Option[Boolean] = None,
	  /** SSL configuration for the destination to connect to the source database. */
		ssl: Option[Schema.SslConfig] = None,
	  /** If the source is a Cloud SQL database, use this field to provide the Cloud SQL instance ID of the source. */
		cloudSqlId: Option[String] = None,
	  /** The backup details in Cloud Storage for homogeneous migration to Cloud SQL for SQL Server. */
		backups: Option[Schema.SqlServerBackups] = None,
	  /** Static IP connectivity data (default, no additional details needed). */
		staticIpConnectivity: Option[Schema.StaticIpConnectivity] = None,
	  /** Forward SSH tunnel connectivity. */
		forwardSshConnectivity: Option[Schema.ForwardSshTunnelConnectivity] = None,
	  /** Private connectivity. */
		privateConnectivity: Option[Schema.PrivateConnectivity] = None,
	  /** Private Service Connect connectivity. */
		privateServiceConnectConnectivity: Option[Schema.PrivateServiceConnectConnectivity] = None
	)
	
	case class SqlServerBackups(
	  /** Required. The Cloud Storage bucket that stores backups for all replicated databases. */
		gcsBucket: Option[String] = None,
	  /** Optional. Cloud Storage path inside the bucket that stores backups. */
		gcsPrefix: Option[String] = None
	)
	
	case class ForwardSshTunnelConnectivity(
	  /** Required. Hostname for the SSH tunnel. */
		hostname: Option[String] = None,
	  /** Required. Username for the SSH tunnel. */
		username: Option[String] = None,
	  /** Port for the SSH tunnel, default value is 22. */
		port: Option[Int] = None,
	  /** Input only. SSH password. */
		password: Option[String] = None,
	  /** Input only. SSH private key. */
		privateKey: Option[String] = None
	)
	
	case class PrivateConnectivity(
	  /** Required. The resource name (URI) of the private connection. */
		privateConnection: Option[String] = None
	)
	
	case class OracleConnectionProfile(
	  /** Required. The IP or hostname of the source Oracle database. */
		host: Option[String] = None,
	  /** Required. The network port of the source Oracle database. */
		port: Option[Int] = None,
	  /** Required. The username that Database Migration Service will use to connect to the database. The value is encrypted when stored in Database Migration Service. */
		username: Option[String] = None,
	  /** Required. Input only. The password for the user that Database Migration Service will be using to connect to the database. This field is not returned on request, and the value is encrypted when stored in Database Migration Service. */
		password: Option[String] = None,
	  /** Output only. Indicates whether a new password is included in the request. */
		passwordSet: Option[Boolean] = None,
	  /** Required. Database service for the Oracle connection. */
		databaseService: Option[String] = None,
	  /** SSL configuration for the connection to the source Oracle database. &#42; Only `SERVER_ONLY` configuration is supported for Oracle SSL. &#42; SSL is supported for Oracle versions 12 and above. */
		ssl: Option[Schema.SslConfig] = None,
	  /** Static Service IP connectivity. */
		staticServiceIpConnectivity: Option[Schema.StaticServiceIpConnectivity] = None,
	  /** Forward SSH tunnel connectivity. */
		forwardSshConnectivity: Option[Schema.ForwardSshTunnelConnectivity] = None,
	  /** Private connectivity. */
		privateConnectivity: Option[Schema.PrivateConnectivity] = None,
	  /** Optional. Configuration for Oracle ASM connection. */
		oracleAsmConfig: Option[Schema.OracleAsmConfig] = None
	)
	
	case class StaticServiceIpConnectivity(
	
	)
	
	case class OracleAsmConfig(
	  /** Required. Hostname for the Oracle ASM connection. */
		hostname: Option[String] = None,
	  /** Required. Port for the Oracle ASM connection. */
		port: Option[Int] = None,
	  /** Required. Username for the Oracle ASM connection. */
		username: Option[String] = None,
	  /** Required. Input only. Password for the Oracle ASM connection. */
		password: Option[String] = None,
	  /** Output only. Indicates whether a new password is included in the request. */
		passwordSet: Option[Boolean] = None,
	  /** Required. ASM service name for the Oracle ASM connection. */
		asmService: Option[String] = None,
	  /** Optional. SSL configuration for the Oracle connection. */
		ssl: Option[Schema.SslConfig] = None
	)
	
	case class CloudSqlConnectionProfile(
	  /** Output only. The Cloud SQL instance ID that this connection profile is associated with. */
		cloudSqlId: Option[String] = None,
	  /** Immutable. Metadata used to create the destination Cloud SQL database. */
		settings: Option[Schema.CloudSqlSettings] = None,
	  /** Output only. The Cloud SQL database instance's private IP. */
		privateIp: Option[String] = None,
	  /** Output only. The Cloud SQL database instance's public IP. */
		publicIp: Option[String] = None,
	  /** Output only. The Cloud SQL database instance's additional (outgoing) public IP. Used when the Cloud SQL database availability type is REGIONAL (i.e. multiple zones / highly available). */
		additionalPublicIp: Option[String] = None
	)
	
	object CloudSqlSettings {
		enum DatabaseVersionEnum extends Enum[DatabaseVersionEnum] { case SQL_DATABASE_VERSION_UNSPECIFIED, MYSQL_5_6, MYSQL_5_7, MYSQL_8_0, MYSQL_8_0_18, MYSQL_8_0_26, MYSQL_8_0_27, MYSQL_8_0_28, MYSQL_8_0_30, MYSQL_8_0_31, MYSQL_8_0_32, MYSQL_8_0_33, MYSQL_8_0_34, MYSQL_8_0_35, MYSQL_8_0_36, MYSQL_8_0_37, MYSQL_8_4, POSTGRES_9_6, POSTGRES_11, POSTGRES_10, POSTGRES_12, POSTGRES_13, POSTGRES_14, POSTGRES_15, POSTGRES_16 }
		enum ActivationPolicyEnum extends Enum[ActivationPolicyEnum] { case SQL_ACTIVATION_POLICY_UNSPECIFIED, ALWAYS, NEVER }
		enum DataDiskTypeEnum extends Enum[DataDiskTypeEnum] { case SQL_DATA_DISK_TYPE_UNSPECIFIED, PD_SSD, PD_HDD }
		enum AvailabilityTypeEnum extends Enum[AvailabilityTypeEnum] { case SQL_AVAILABILITY_TYPE_UNSPECIFIED, ZONAL, REGIONAL }
		enum EditionEnum extends Enum[EditionEnum] { case EDITION_UNSPECIFIED, ENTERPRISE, ENTERPRISE_PLUS }
	}
	case class CloudSqlSettings(
	  /** The database engine type and version. Deprecated. Use database_version_name instead. */
		databaseVersion: Option[Schema.CloudSqlSettings.DatabaseVersionEnum] = None,
	  /** Optional. The database engine type and version name. */
		databaseVersionName: Option[String] = None,
	  /** The resource labels for a Cloud SQL instance to use to annotate any related underlying resources such as Compute Engine VMs. An object containing a list of "key": "value" pairs. Example: `{ "name": "wrench", "mass": "18kg", "count": "3" }`. */
		userLabels: Option[Map[String, String]] = None,
	  /** The tier (or machine type) for this instance, for example: `db-n1-standard-1` (MySQL instances) or `db-custom-1-3840` (PostgreSQL instances). For more information, see [Cloud SQL Instance Settings](https://cloud.google.com/sql/docs/mysql/instance-settings). */
		tier: Option[String] = None,
	  /** The maximum size to which storage capacity can be automatically increased. The default value is 0, which specifies that there is no limit. */
		storageAutoResizeLimit: Option[String] = None,
	  /** The activation policy specifies when the instance is activated; it is applicable only when the instance state is 'RUNNABLE'. Valid values: 'ALWAYS': The instance is on, and remains so even in the absence of connection requests. `NEVER`: The instance is off; it is not activated, even if a connection request arrives. */
		activationPolicy: Option[Schema.CloudSqlSettings.ActivationPolicyEnum] = None,
	  /** The settings for IP Management. This allows to enable or disable the instance IP and manage which external networks can connect to the instance. The IPv4 address cannot be disabled. */
		ipConfig: Option[Schema.SqlIpConfig] = None,
	  /** [default: ON] If you enable this setting, Cloud SQL checks your available storage every 30 seconds. If the available storage falls below a threshold size, Cloud SQL automatically adds additional storage capacity. If the available storage repeatedly falls below the threshold size, Cloud SQL continues to add storage until it reaches the maximum of 30 TB. */
		autoStorageIncrease: Option[Boolean] = None,
	  /** The database flags passed to the Cloud SQL instance at startup. An object containing a list of "key": value pairs. Example: { "name": "wrench", "mass": "1.3kg", "count": "3" }. */
		databaseFlags: Option[Map[String, String]] = None,
	  /** The type of storage: `PD_SSD` (default) or `PD_HDD`. */
		dataDiskType: Option[Schema.CloudSqlSettings.DataDiskTypeEnum] = None,
	  /** The storage capacity available to the database, in GB. The minimum (and default) size is 10GB. */
		dataDiskSizeGb: Option[String] = None,
	  /** The Google Cloud Platform zone where your Cloud SQL database instance is located. */
		zone: Option[String] = None,
	  /** Optional. The Google Cloud Platform zone where the failover Cloud SQL database instance is located. Used when the Cloud SQL database availability type is REGIONAL (i.e. multiple zones / highly available). */
		secondaryZone: Option[String] = None,
	  /** The Database Migration Service source connection profile ID, in the format: `projects/my_project_name/locations/us-central1/connectionProfiles/connection_profile_ID` */
		sourceId: Option[String] = None,
	  /** Input only. Initial root password. */
		rootPassword: Option[String] = None,
	  /** Output only. Indicates If this connection profile root password is stored. */
		rootPasswordSet: Option[Boolean] = None,
	  /** The Cloud SQL default instance level collation. */
		collation: Option[String] = None,
	  /** The KMS key name used for the csql instance. */
		cmekKeyName: Option[String] = None,
	  /** Optional. Availability type. Potential values: &#42; `ZONAL`: The instance serves data from only one zone. Outages in that zone affect data availability. &#42; `REGIONAL`: The instance can serve data from more than one zone in a region (it is highly available). */
		availabilityType: Option[Schema.CloudSqlSettings.AvailabilityTypeEnum] = None,
	  /** Optional. The edition of the given Cloud SQL instance. */
		edition: Option[Schema.CloudSqlSettings.EditionEnum] = None,
	  /** Optional. Data cache is an optional feature available for Cloud SQL for MySQL Enterprise Plus edition only. For more information on data cache, see [Data cache overview](https://cloud.google.com/sql/help/mysql-data-cache) in Cloud SQL documentation. */
		dataCacheConfig: Option[Schema.DataCacheConfig] = None
	)
	
	case class SqlIpConfig(
	  /** Whether the instance should be assigned an IPv4 address or not. */
		enableIpv4: Option[Boolean] = None,
	  /** The resource link for the VPC network from which the Cloud SQL instance is accessible for private IP. For example, `projects/myProject/global/networks/default`. This setting can be updated, but it cannot be removed after it is set. */
		privateNetwork: Option[String] = None,
	  /** Optional. The name of the allocated IP address range for the private IP Cloud SQL instance. This name refers to an already allocated IP range address. If set, the instance IP address will be created in the allocated range. Note that this IP address range can't be modified after the instance is created. If you change the VPC when configuring connectivity settings for the migration job, this field is not relevant. */
		allocatedIpRange: Option[String] = None,
	  /** Whether SSL connections over IP should be enforced or not. */
		requireSsl: Option[Boolean] = None,
	  /** The list of external networks that are allowed to connect to the instance using the IP. See https://en.wikipedia.org/wiki/CIDR_notation#CIDR_notation, also known as 'slash' notation (e.g. `192.168.100.0/24`). */
		authorizedNetworks: Option[List[Schema.SqlAclEntry]] = None
	)
	
	case class SqlAclEntry(
	  /** The allowlisted value for the access control list. */
		value: Option[String] = None,
	  /** The time when this access control entry expires in [RFC 3339](https://tools.ietf.org/html/rfc3339) format, for example: `2012-11-15T16:19:00.094Z`. */
		expireTime: Option[String] = None,
	  /** Input only. The time-to-leave of this access control entry. */
		ttl: Option[String] = None,
	  /** A label to identify this entry. */
		label: Option[String] = None
	)
	
	case class DataCacheConfig(
	  /** Optional. Whether data cache is enabled for the instance. */
		dataCacheEnabled: Option[Boolean] = None
	)
	
	case class AlloyDbConnectionProfile(
	  /** Required. The AlloyDB cluster ID that this connection profile is associated with. */
		clusterId: Option[String] = None,
	  /** Immutable. Metadata used to create the destination AlloyDB cluster. */
		settings: Option[Schema.AlloyDbSettings] = None
	)
	
	object AlloyDbSettings {
		enum DatabaseVersionEnum extends Enum[DatabaseVersionEnum] { case DATABASE_VERSION_UNSPECIFIED, POSTGRES_14, POSTGRES_15, POSTGRES_16 }
	}
	case class AlloyDbSettings(
	  /** Required. Input only. Initial user to setup during cluster creation. Required. */
		initialUser: Option[Schema.UserPassword] = None,
	  /** Required. The resource link for the VPC network in which cluster resources are created and from which they are accessible via Private IP. The network must belong to the same project as the cluster. It is specified in the form: "projects/{project_number}/global/networks/{network_id}". This is required to create a cluster. */
		vpcNetwork: Option[String] = None,
	  /** Labels for the AlloyDB cluster created by DMS. An object containing a list of 'key', 'value' pairs. */
		labels: Option[Map[String, String]] = None,
		primaryInstanceSettings: Option[Schema.PrimaryInstanceSettings] = None,
	  /** Optional. The encryption config can be specified to encrypt the data disks and other persistent data resources of a cluster with a customer-managed encryption key (CMEK). When this field is not specified, the cluster will then use default encryption scheme to protect the user data. */
		encryptionConfig: Option[Schema.EncryptionConfig] = None,
	  /** Optional. The database engine major version. This is an optional field. If a database version is not supplied at cluster creation time, then a default database version will be used. */
		databaseVersion: Option[Schema.AlloyDbSettings.DatabaseVersionEnum] = None
	)
	
	case class UserPassword(
	  /** The database username. */
		user: Option[String] = None,
	  /** The initial password for the user. */
		password: Option[String] = None,
	  /** Output only. Indicates if the initial_user.password field has been set. */
		passwordSet: Option[Boolean] = None
	)
	
	case class PrimaryInstanceSettings(
	  /** Required. The ID of the AlloyDB primary instance. The ID must satisfy the regex expression "[a-z0-9-]+". */
		id: Option[String] = None,
	  /** Configuration for the machines that host the underlying database engine. */
		machineConfig: Option[Schema.MachineConfig] = None,
	  /** Database flags to pass to AlloyDB when DMS is creating the AlloyDB cluster and instances. See the AlloyDB documentation for how these can be used. */
		databaseFlags: Option[Map[String, String]] = None,
	  /** Labels for the AlloyDB primary instance created by DMS. An object containing a list of 'key', 'value' pairs. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The private IP address for the Instance. This is the connection endpoint for an end-user application. */
		privateIp: Option[String] = None,
	  /** Optional. Metadata related to instance level network configuration. */
		instanceNetworkConfig: Option[Schema.InstanceNetworkConfig] = None,
	  /** Output only. All outbound public IP addresses configured for the instance. */
		outboundPublicIpAddresses: Option[List[String]] = None
	)
	
	case class MachineConfig(
	  /** The number of CPU's in the VM instance. */
		cpuCount: Option[Int] = None
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
	  /** Optional. CIDR range for one authorzied network of the instance. */
		cidrRange: Option[String] = None
	)
	
	case class EncryptionConfig(
	  /** The fully-qualified resource name of the KMS key. Each Cloud KMS key is regionalized and has the following format: projects/[PROJECT]/locations/[REGION]/keyRings/[RING]/cryptoKeys/[KEY_NAME] */
		kmsKeyName: Option[String] = None
	)
	
	object PrivateConnection {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, CREATED, FAILED, DELETING, FAILED_TO_DELETE, DELETED }
	}
	case class PrivateConnection(
	  /** The name of the resource. */
		name: Option[String] = None,
	  /** Output only. The create time of the resource. */
		createTime: Option[String] = None,
	  /** Output only. The last update time of the resource. */
		updateTime: Option[String] = None,
	  /** The resource labels for private connections to use to annotate any related underlying resources such as Compute Engine VMs. An object containing a list of "key": "value" pairs. Example: `{ "name": "wrench", "mass": "1.3kg", "count": "3" }`. */
		labels: Option[Map[String, String]] = None,
	  /** The private connection display name. */
		displayName: Option[String] = None,
	  /** Output only. The state of the private connection. */
		state: Option[Schema.PrivateConnection.StateEnum] = None,
	  /** Output only. The error details in case of state FAILED. */
		error: Option[Schema.Status] = None,
	  /** VPC peering configuration. */
		vpcPeeringConfig: Option[Schema.VpcPeeringConfig] = None
	)
	
	case class VpcPeeringConfig(
	  /** Required. Fully qualified name of the VPC that Database Migration Service will peer to. */
		vpcName: Option[String] = None,
	  /** Required. A free subnet for peering. (CIDR of /29) */
		subnet: Option[String] = None
	)
	
	case class ListPrivateConnectionsResponse(
	  /** List of private connections. */
		privateConnections: Option[List[Schema.PrivateConnection]] = None,
	  /** A token which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ConversionWorkspace(
	  /** Full name of the workspace resource, in the form of: projects/{project}/locations/{location}/conversionWorkspaces/{conversion_workspace}. */
		name: Option[String] = None,
	  /** Required. The source engine details. */
		source: Option[Schema.DatabaseEngineInfo] = None,
	  /** Required. The destination engine details. */
		destination: Option[Schema.DatabaseEngineInfo] = None,
	  /** Optional. A generic list of settings for the workspace. The settings are database pair dependant and can indicate default behavior for the mapping rules engine or turn on or off specific features. Such examples can be: convert_foreign_key_to_interleave=true, skip_triggers=false, ignore_non_table_synonyms=true */
		globalSettings: Option[Map[String, String]] = None,
	  /** Output only. Whether the workspace has uncommitted changes (changes which were made after the workspace was committed). */
		hasUncommittedChanges: Option[Boolean] = None,
	  /** Output only. The latest commit ID. */
		latestCommitId: Option[String] = None,
	  /** Output only. The timestamp when the workspace was committed. */
		latestCommitTime: Option[String] = None,
	  /** Output only. The timestamp when the workspace resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the workspace resource was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. The display name for the workspace. */
		displayName: Option[String] = None
	)
	
	object DatabaseEngineInfo {
		enum EngineEnum extends Enum[EngineEnum] { case DATABASE_ENGINE_UNSPECIFIED, MYSQL, POSTGRESQL, SQLSERVER, ORACLE }
	}
	case class DatabaseEngineInfo(
	  /** Required. Engine type. */
		engine: Option[Schema.DatabaseEngineInfo.EngineEnum] = None,
	  /** Required. Engine version, for example "12.c.1". */
		version: Option[String] = None
	)
	
	case class ListConversionWorkspacesResponse(
	  /** The list of conversion workspace objects. */
		conversionWorkspaces: Option[List[Schema.ConversionWorkspace]] = None,
	  /** A token which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object MappingRule {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ENABLED, DISABLED, DELETED }
		enum RuleScopeEnum extends Enum[RuleScopeEnum] { case DATABASE_ENTITY_TYPE_UNSPECIFIED, DATABASE_ENTITY_TYPE_SCHEMA, DATABASE_ENTITY_TYPE_TABLE, DATABASE_ENTITY_TYPE_COLUMN, DATABASE_ENTITY_TYPE_CONSTRAINT, DATABASE_ENTITY_TYPE_INDEX, DATABASE_ENTITY_TYPE_TRIGGER, DATABASE_ENTITY_TYPE_VIEW, DATABASE_ENTITY_TYPE_SEQUENCE, DATABASE_ENTITY_TYPE_STORED_PROCEDURE, DATABASE_ENTITY_TYPE_FUNCTION, DATABASE_ENTITY_TYPE_SYNONYM, DATABASE_ENTITY_TYPE_DATABASE_PACKAGE, DATABASE_ENTITY_TYPE_UDT, DATABASE_ENTITY_TYPE_MATERIALIZED_VIEW, DATABASE_ENTITY_TYPE_DATABASE }
	}
	case class MappingRule(
	  /** Full name of the mapping rule resource, in the form of: projects/{project}/locations/{location}/conversionWorkspaces/{set}/mappingRule/{rule}. */
		name: Option[String] = None,
	  /** Optional. A human readable name */
		displayName: Option[String] = None,
	  /** Optional. The mapping rule state */
		state: Option[Schema.MappingRule.StateEnum] = None,
	  /** Required. The rule scope */
		ruleScope: Option[Schema.MappingRule.RuleScopeEnum] = None,
	  /** Required. The rule filter */
		filter: Option[Schema.MappingRuleFilter] = None,
	  /** Required. The order in which the rule is applied. Lower order rules are applied before higher value rules so they may end up being overridden. */
		ruleOrder: Option[String] = None,
	  /** Output only. The revision ID of the mapping rule. A new revision is committed whenever the mapping rule is changed in any way. The format is an 8-character hexadecimal string. */
		revisionId: Option[String] = None,
	  /** Output only. The timestamp that the revision was created. */
		revisionCreateTime: Option[String] = None,
	  /** Optional. Rule to specify how a single entity should be renamed. */
		singleEntityRename: Option[Schema.SingleEntityRename] = None,
	  /** Optional. Rule to specify how multiple entities should be renamed. */
		multiEntityRename: Option[Schema.MultiEntityRename] = None,
	  /** Optional. Rule to specify how multiple entities should be relocated into a different schema. */
		entityMove: Option[Schema.EntityMove] = None,
	  /** Optional. Rule to specify how a single column is converted. */
		singleColumnChange: Option[Schema.SingleColumnChange] = None,
	  /** Optional. Rule to specify how multiple columns should be converted to a different data type. */
		multiColumnDataTypeChange: Option[Schema.MultiColumnDatatypeChange] = None,
	  /** Optional. Rule to specify how the data contained in a column should be transformed (such as trimmed, rounded, etc) provided that the data meets certain criteria. */
		conditionalColumnSetValue: Option[Schema.ConditionalColumnSetValue] = None,
	  /** Optional. Rule to specify how multiple tables should be converted with an additional rowid column. */
		convertRowidColumn: Option[Schema.ConvertRowIdToColumn] = None,
	  /** Optional. Rule to specify the primary key for a table */
		setTablePrimaryKey: Option[Schema.SetTablePrimaryKey] = None,
	  /** Optional. Rule to specify how a single package is converted. */
		singlePackageChange: Option[Schema.SinglePackageChange] = None,
	  /** Optional. Rule to change the sql code for an entity, for example, function, procedure. */
		sourceSqlChange: Option[Schema.SourceSqlChange] = None,
	  /** Optional. Rule to specify the list of columns to include or exclude from a table. */
		filterTableColumns: Option[Schema.FilterTableColumns] = None
	)
	
	case class MappingRuleFilter(
	  /** Optional. The rule should be applied to entities whose parent entity (fully qualified name) matches the given value. For example, if the rule applies to a table entity, the expected value should be a schema (schema). If the rule applies to a column or index entity, the expected value can be either a schema (schema) or a table (schema.table) */
		parentEntity: Option[String] = None,
	  /** Optional. The rule should be applied to entities whose non-qualified name starts with the given prefix. */
		entityNamePrefix: Option[String] = None,
	  /** Optional. The rule should be applied to entities whose non-qualified name ends with the given suffix. */
		entityNameSuffix: Option[String] = None,
	  /** Optional. The rule should be applied to entities whose non-qualified name contains the given string. */
		entityNameContains: Option[String] = None,
	  /** Optional. The rule should be applied to specific entities defined by their fully qualified names. */
		entities: Option[List[String]] = None
	)
	
	case class SingleEntityRename(
	  /** Required. The new name of the destination entity */
		newName: Option[String] = None
	)
	
	object MultiEntityRename {
		enum SourceNameTransformationEnum extends Enum[SourceNameTransformationEnum] { case ENTITY_NAME_TRANSFORMATION_UNSPECIFIED, ENTITY_NAME_TRANSFORMATION_NO_TRANSFORMATION, ENTITY_NAME_TRANSFORMATION_LOWER_CASE, ENTITY_NAME_TRANSFORMATION_UPPER_CASE, ENTITY_NAME_TRANSFORMATION_CAPITALIZED_CASE }
	}
	case class MultiEntityRename(
	  /** Optional. The pattern used to generate the new entity's name. This pattern must include the characters '{name}', which will be replaced with the name of the original entity. For example, the pattern 't_{name}' for an entity name jobs would be converted to 't_jobs'. If unspecified, the default value for this field is '{name}' */
		newNamePattern: Option[String] = None,
	  /** Optional. Additional transformation that can be done on the source entity name before it is being used by the new_name_pattern, for example lower case. If no transformation is desired, use NO_TRANSFORMATION */
		sourceNameTransformation: Option[Schema.MultiEntityRename.SourceNameTransformationEnum] = None
	)
	
	case class EntityMove(
	  /** Required. The new schema */
		newSchema: Option[String] = None
	)
	
	case class SingleColumnChange(
	  /** Optional. Column data type name. */
		dataType: Option[String] = None,
	  /** Optional. Charset override - instead of table level charset. */
		charset: Option[String] = None,
	  /** Optional. Collation override - instead of table level collation. */
		collation: Option[String] = None,
	  /** Optional. Column length - e.g. 50 as in varchar (50) - when relevant. */
		length: Option[String] = None,
	  /** Optional. Column precision - e.g. 8 as in double (8,2) - when relevant. */
		precision: Option[Int] = None,
	  /** Optional. Column scale - e.g. 2 as in double (8,2) - when relevant. */
		scale: Option[Int] = None,
	  /** Optional. Column fractional seconds precision - e.g. 2 as in timestamp (2) - when relevant. */
		fractionalSecondsPrecision: Option[Int] = None,
	  /** Optional. Is the column of array type. */
		array: Option[Boolean] = None,
	  /** Optional. The length of the array, only relevant if the column type is an array. */
		arrayLength: Option[Int] = None,
	  /** Optional. Is the column nullable. */
		nullable: Option[Boolean] = None,
	  /** Optional. Is the column auto-generated/identity. */
		autoGenerated: Option[Boolean] = None,
	  /** Optional. Is the column a UDT (User-defined Type). */
		udt: Option[Boolean] = None,
	  /** Optional. Custom engine specific features. */
		customFeatures: Option[Map[String, JsValue]] = None,
	  /** Optional. Specifies the list of values allowed in the column. */
		setValues: Option[List[String]] = None,
	  /** Optional. Comment associated with the column. */
		comment: Option[String] = None
	)
	
	case class MultiColumnDatatypeChange(
	  /** Required. Filter on source data type. */
		sourceDataTypeFilter: Option[String] = None,
	  /** Optional. Filter for text-based data types like varchar. */
		sourceTextFilter: Option[Schema.SourceTextFilter] = None,
	  /** Optional. Filter for fixed point number data types such as NUMERIC/NUMBER. */
		sourceNumericFilter: Option[Schema.SourceNumericFilter] = None,
	  /** Required. New data type. */
		newDataType: Option[String] = None,
	  /** Optional. Column length - e.g. varchar (50) - if not specified and relevant uses the source column length. */
		overrideLength: Option[String] = None,
	  /** Optional. Column scale - when relevant - if not specified and relevant uses the source column scale. */
		overrideScale: Option[Int] = None,
	  /** Optional. Column precision - when relevant - if not specified and relevant uses the source column precision. */
		overridePrecision: Option[Int] = None,
	  /** Optional. Column fractional seconds precision - used only for timestamp based datatypes - if not specified and relevant uses the source column fractional seconds precision. */
		overrideFractionalSecondsPrecision: Option[Int] = None,
	  /** Optional. Custom engine specific features. */
		customFeatures: Option[Map[String, JsValue]] = None
	)
	
	case class SourceTextFilter(
	  /** Optional. The filter will match columns with length greater than or equal to this number. */
		sourceMinLengthFilter: Option[String] = None,
	  /** Optional. The filter will match columns with length smaller than or equal to this number. */
		sourceMaxLengthFilter: Option[String] = None
	)
	
	object SourceNumericFilter {
		enum NumericFilterOptionEnum extends Enum[NumericFilterOptionEnum] { case NUMERIC_FILTER_OPTION_UNSPECIFIED, NUMERIC_FILTER_OPTION_ALL, NUMERIC_FILTER_OPTION_LIMIT, NUMERIC_FILTER_OPTION_LIMITLESS }
	}
	case class SourceNumericFilter(
	  /** Optional. The filter will match columns with scale greater than or equal to this number. */
		sourceMinScaleFilter: Option[Int] = None,
	  /** Optional. The filter will match columns with scale smaller than or equal to this number. */
		sourceMaxScaleFilter: Option[Int] = None,
	  /** Optional. The filter will match columns with precision greater than or equal to this number. */
		sourceMinPrecisionFilter: Option[Int] = None,
	  /** Optional. The filter will match columns with precision smaller than or equal to this number. */
		sourceMaxPrecisionFilter: Option[Int] = None,
	  /** Required. Enum to set the option defining the datatypes numeric filter has to be applied to */
		numericFilterOption: Option[Schema.SourceNumericFilter.NumericFilterOptionEnum] = None
	)
	
	case class ConditionalColumnSetValue(
	  /** Optional. Optional filter on source column length. Used for text based data types like varchar. */
		sourceTextFilter: Option[Schema.SourceTextFilter] = None,
	  /** Optional. Optional filter on source column precision and scale. Used for fixed point numbers such as NUMERIC/NUMBER data types. */
		sourceNumericFilter: Option[Schema.SourceNumericFilter] = None,
	  /** Required. Description of data transformation during migration. */
		valueTransformation: Option[Schema.ValueTransformation] = None,
	  /** Optional. Custom engine specific features. */
		customFeatures: Option[Map[String, JsValue]] = None
	)
	
	case class ValueTransformation(
	  /** Optional. Value is null */
		isNull: Option[Schema.Empty] = None,
	  /** Optional. Value is found in the specified list. */
		valueList: Option[Schema.ValueListFilter] = None,
	  /** Optional. Filter on relation between source value and compare value of type integer. */
		intComparison: Option[Schema.IntComparisonFilter] = None,
	  /** Optional. Filter on relation between source value and compare value of type double. */
		doubleComparison: Option[Schema.DoubleComparisonFilter] = None,
	  /** Optional. Set to null */
		assignNull: Option[Schema.Empty] = None,
	  /** Optional. Set to a specific value (value is converted to fit the target data type) */
		assignSpecificValue: Option[Schema.AssignSpecificValue] = None,
	  /** Optional. Set to min_value - if integer or numeric, will use int.minvalue, etc */
		assignMinValue: Option[Schema.Empty] = None,
	  /** Optional. Set to max_value - if integer or numeric, will use int.maxvalue, etc */
		assignMaxValue: Option[Schema.Empty] = None,
	  /** Optional. Allows the data to change scale */
		roundScale: Option[Schema.RoundToScale] = None,
	  /** Optional. Applies a hash function on the data */
		applyHash: Option[Schema.ApplyHash] = None
	)
	
	object ValueListFilter {
		enum ValuePresentListEnum extends Enum[ValuePresentListEnum] { case VALUE_PRESENT_IN_LIST_UNSPECIFIED, VALUE_PRESENT_IN_LIST_IF_VALUE_LIST, VALUE_PRESENT_IN_LIST_IF_VALUE_NOT_LIST }
	}
	case class ValueListFilter(
	  /** Required. Indicates whether the filter matches rows with values that are present in the list or those with values not present in it. */
		valuePresentList: Option[Schema.ValueListFilter.ValuePresentListEnum] = None,
	  /** Required. The list to be used to filter by */
		values: Option[List[String]] = None,
	  /** Required. Whether to ignore case when filtering by values. Defaults to false */
		ignoreCase: Option[Boolean] = None
	)
	
	object IntComparisonFilter {
		enum ValueComparisonEnum extends Enum[ValueComparisonEnum] { case VALUE_COMPARISON_UNSPECIFIED, VALUE_COMPARISON_IF_VALUE_SMALLER_THAN, VALUE_COMPARISON_IF_VALUE_SMALLER_EQUAL_THAN, VALUE_COMPARISON_IF_VALUE_LARGER_THAN, VALUE_COMPARISON_IF_VALUE_LARGER_EQUAL_THAN }
	}
	case class IntComparisonFilter(
	  /** Required. Relation between source value and compare value */
		valueComparison: Option[Schema.IntComparisonFilter.ValueComparisonEnum] = None,
	  /** Required. Integer compare value to be used */
		value: Option[String] = None
	)
	
	object DoubleComparisonFilter {
		enum ValueComparisonEnum extends Enum[ValueComparisonEnum] { case VALUE_COMPARISON_UNSPECIFIED, VALUE_COMPARISON_IF_VALUE_SMALLER_THAN, VALUE_COMPARISON_IF_VALUE_SMALLER_EQUAL_THAN, VALUE_COMPARISON_IF_VALUE_LARGER_THAN, VALUE_COMPARISON_IF_VALUE_LARGER_EQUAL_THAN }
	}
	case class DoubleComparisonFilter(
	  /** Required. Relation between source value and compare value */
		valueComparison: Option[Schema.DoubleComparisonFilter.ValueComparisonEnum] = None,
	  /** Required. Double compare value to be used */
		value: Option[BigDecimal] = None
	)
	
	case class AssignSpecificValue(
	  /** Required. Specific value to be assigned */
		value: Option[String] = None
	)
	
	case class RoundToScale(
	  /** Required. Scale value to be used */
		scale: Option[Int] = None
	)
	
	case class ApplyHash(
	  /** Optional. Generate UUID from the data's byte array */
		uuidFromBytes: Option[Schema.Empty] = None
	)
	
	case class ConvertRowIdToColumn(
	  /** Required. Only work on tables without primary key defined */
		onlyIfNoPrimaryKey: Option[Boolean] = None
	)
	
	case class SetTablePrimaryKey(
	  /** Required. List of column names for the primary key */
		primaryKeyColumns: Option[List[String]] = None,
	  /** Optional. Name for the primary key */
		primaryKey: Option[String] = None
	)
	
	case class SinglePackageChange(
	  /** Optional. Sql code for package description */
		packageDescription: Option[String] = None,
	  /** Optional. Sql code for package body */
		packageBody: Option[String] = None
	)
	
	case class SourceSqlChange(
	  /** Required. Sql code for source (stored procedure, function, trigger or view) */
		sqlCode: Option[String] = None
	)
	
	case class FilterTableColumns(
	  /** Optional. List of columns to be included for a particular table. */
		includeColumns: Option[List[String]] = None,
	  /** Optional. List of columns to be excluded for a particular table. */
		excludeColumns: Option[List[String]] = None
	)
	
	case class ListMappingRulesResponse(
	  /** The list of conversion workspace mapping rules. */
		mappingRules: Option[List[Schema.MappingRule]] = None,
	  /** A token which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class SeedConversionWorkspaceRequest(
	  /** Should the conversion workspace be committed automatically after the seed operation. */
		autoCommit: Option[Boolean] = None,
	  /** Optional. Fully qualified (Uri) name of the source connection profile. */
		sourceConnectionProfile: Option[String] = None,
	  /** Optional. Fully qualified (Uri) name of the destination connection profile. */
		destinationConnectionProfile: Option[String] = None
	)
	
	object ImportMappingRulesRequest {
		enum RulesFormatEnum extends Enum[RulesFormatEnum] { case IMPORT_RULES_FILE_FORMAT_UNSPECIFIED, IMPORT_RULES_FILE_FORMAT_HARBOUR_BRIDGE_SESSION_FILE, IMPORT_RULES_FILE_FORMAT_ORATOPG_CONFIG_FILE }
	}
	case class ImportMappingRulesRequest(
	  /** Required. The format of the rules content file. */
		rulesFormat: Option[Schema.ImportMappingRulesRequest.RulesFormatEnum] = None,
	  /** Required. One or more rules files. */
		rulesFiles: Option[List[Schema.RulesFile]] = None,
	  /** Required. Should the conversion workspace be committed automatically after the import operation. */
		autoCommit: Option[Boolean] = None
	)
	
	case class RulesFile(
	  /** Required. The filename of the rules that needs to be converted. The filename is used mainly so that future logs of the import rules job contain it, and can therefore be searched by it. */
		rulesSourceFilename: Option[String] = None,
	  /** Required. The text content of the rules that needs to be converted. */
		rulesContent: Option[String] = None
	)
	
	case class ConvertConversionWorkspaceRequest(
	  /** Optional. Specifies whether the conversion workspace is to be committed automatically after the conversion. */
		autoCommit: Option[Boolean] = None,
	  /** Optional. Filter the entities to convert. Leaving this field empty will convert all of the entities. Supports Google AIP-160 style filtering. */
		filter: Option[String] = None,
	  /** Optional. Automatically convert the full entity path for each entity specified by the filter. For example, if the filter specifies a table, that table schema (and database if there is one) will also be converted. */
		convertFullPath: Option[Boolean] = None
	)
	
	case class CommitConversionWorkspaceRequest(
	  /** Optional. Optional name of the commit. */
		commitName: Option[String] = None
	)
	
	case class RollbackConversionWorkspaceRequest(
	
	)
	
	case class ApplyConversionWorkspaceRequest(
	  /** Filter which entities to apply. Leaving this field empty will apply all of the entities. Supports Google AIP 160 based filtering. */
		filter: Option[String] = None,
	  /** Optional. Only validates the apply process, but doesn't change the destination database. Only works for PostgreSQL destination connection profile. */
		dryRun: Option[Boolean] = None,
	  /** Optional. Specifies whether the conversion workspace is to be committed automatically after the apply. */
		autoCommit: Option[Boolean] = None,
	  /** Optional. Fully qualified (Uri) name of the destination connection profile. */
		connectionProfile: Option[String] = None
	)
	
	case class DescribeDatabaseEntitiesResponse(
	  /** The list of database entities for the conversion workspace. */
		databaseEntities: Option[List[Schema.DatabaseEntity]] = None,
	  /** A token which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object DatabaseEntity {
		enum TreeEnum extends Enum[TreeEnum] { case TREE_TYPE_UNSPECIFIED, SOURCE, DRAFT, DESTINATION }
		enum EntityTypeEnum extends Enum[EntityTypeEnum] { case DATABASE_ENTITY_TYPE_UNSPECIFIED, DATABASE_ENTITY_TYPE_SCHEMA, DATABASE_ENTITY_TYPE_TABLE, DATABASE_ENTITY_TYPE_COLUMN, DATABASE_ENTITY_TYPE_CONSTRAINT, DATABASE_ENTITY_TYPE_INDEX, DATABASE_ENTITY_TYPE_TRIGGER, DATABASE_ENTITY_TYPE_VIEW, DATABASE_ENTITY_TYPE_SEQUENCE, DATABASE_ENTITY_TYPE_STORED_PROCEDURE, DATABASE_ENTITY_TYPE_FUNCTION, DATABASE_ENTITY_TYPE_SYNONYM, DATABASE_ENTITY_TYPE_DATABASE_PACKAGE, DATABASE_ENTITY_TYPE_UDT, DATABASE_ENTITY_TYPE_MATERIALIZED_VIEW, DATABASE_ENTITY_TYPE_DATABASE }
	}
	case class DatabaseEntity(
	  /** The short name (e.g. table name) of the entity. */
		shortName: Option[String] = None,
	  /** The full name of the parent entity (e.g. schema name). */
		parentEntity: Option[String] = None,
	  /** The type of tree the entity belongs to. */
		tree: Option[Schema.DatabaseEntity.TreeEnum] = None,
	  /** The type of the database entity (table, view, index, ...). */
		entityType: Option[Schema.DatabaseEntity.EntityTypeEnum] = None,
	  /** Details about entity mappings. For source tree entities, this holds the draft entities which were generated by the mapping rules. For draft tree entities, this holds the source entities which were converted to form the draft entity. Destination entities will have no mapping details. */
		mappings: Option[List[Schema.EntityMapping]] = None,
	  /** Details about the entity DDL script. Multiple DDL scripts are provided for child entities such as a table entity will have one DDL for the table with additional DDLs for each index, constraint and such. */
		entityDdl: Option[List[Schema.EntityDdl]] = None,
	  /** Details about the various issues found for the entity. */
		issues: Option[List[Schema.EntityIssue]] = None,
	  /** Database. */
		database: Option[Schema.DatabaseInstanceEntity] = None,
	  /** Schema. */
		schema: Option[Schema.SchemaEntity] = None,
	  /** Table. */
		table: Option[Schema.TableEntity] = None,
	  /** View. */
		view: Option[Schema.ViewEntity] = None,
	  /** Sequence. */
		sequence: Option[Schema.SequenceEntity] = None,
	  /** Stored procedure. */
		storedProcedure: Option[Schema.StoredProcedureEntity] = None,
	  /** Function. */
		databaseFunction: Option[Schema.FunctionEntity] = None,
	  /** Synonym. */
		synonym: Option[Schema.SynonymEntity] = None,
	  /** Package. */
		databasePackage: Option[Schema.PackageEntity] = None,
	  /** UDT. */
		udt: Option[Schema.UDTEntity] = None,
	  /** Materialized view. */
		materializedView: Option[Schema.MaterializedViewEntity] = None
	)
	
	object EntityMapping {
		enum SourceTypeEnum extends Enum[SourceTypeEnum] { case DATABASE_ENTITY_TYPE_UNSPECIFIED, DATABASE_ENTITY_TYPE_SCHEMA, DATABASE_ENTITY_TYPE_TABLE, DATABASE_ENTITY_TYPE_COLUMN, DATABASE_ENTITY_TYPE_CONSTRAINT, DATABASE_ENTITY_TYPE_INDEX, DATABASE_ENTITY_TYPE_TRIGGER, DATABASE_ENTITY_TYPE_VIEW, DATABASE_ENTITY_TYPE_SEQUENCE, DATABASE_ENTITY_TYPE_STORED_PROCEDURE, DATABASE_ENTITY_TYPE_FUNCTION, DATABASE_ENTITY_TYPE_SYNONYM, DATABASE_ENTITY_TYPE_DATABASE_PACKAGE, DATABASE_ENTITY_TYPE_UDT, DATABASE_ENTITY_TYPE_MATERIALIZED_VIEW, DATABASE_ENTITY_TYPE_DATABASE }
		enum DraftTypeEnum extends Enum[DraftTypeEnum] { case DATABASE_ENTITY_TYPE_UNSPECIFIED, DATABASE_ENTITY_TYPE_SCHEMA, DATABASE_ENTITY_TYPE_TABLE, DATABASE_ENTITY_TYPE_COLUMN, DATABASE_ENTITY_TYPE_CONSTRAINT, DATABASE_ENTITY_TYPE_INDEX, DATABASE_ENTITY_TYPE_TRIGGER, DATABASE_ENTITY_TYPE_VIEW, DATABASE_ENTITY_TYPE_SEQUENCE, DATABASE_ENTITY_TYPE_STORED_PROCEDURE, DATABASE_ENTITY_TYPE_FUNCTION, DATABASE_ENTITY_TYPE_SYNONYM, DATABASE_ENTITY_TYPE_DATABASE_PACKAGE, DATABASE_ENTITY_TYPE_UDT, DATABASE_ENTITY_TYPE_MATERIALIZED_VIEW, DATABASE_ENTITY_TYPE_DATABASE }
	}
	case class EntityMapping(
	  /** Source entity full name. The source entity can also be a column, index or constraint using the same naming notation schema.table.column. */
		sourceEntity: Option[String] = None,
	  /** Target entity full name. The draft entity can also include a column, index or constraint using the same naming notation schema.table.column. */
		draftEntity: Option[String] = None,
	  /** Type of source entity. */
		sourceType: Option[Schema.EntityMapping.SourceTypeEnum] = None,
	  /** Type of draft entity. */
		draftType: Option[Schema.EntityMapping.DraftTypeEnum] = None,
	  /** Entity mapping log entries. Multiple rules can be effective and contribute changes to a converted entity, such as a rule can handle the entity name, another rule can handle an entity type. In addition, rules which did not change the entity are also logged along with the reason preventing them to do so. */
		mappingLog: Option[List[Schema.EntityMappingLogEntry]] = None
	)
	
	case class EntityMappingLogEntry(
	  /** Which rule caused this log entry. */
		ruleId: Option[String] = None,
	  /** Rule revision ID. */
		ruleRevisionId: Option[String] = None,
	  /** Comment. */
		mappingComment: Option[String] = None
	)
	
	object EntityDdl {
		enum EntityTypeEnum extends Enum[EntityTypeEnum] { case DATABASE_ENTITY_TYPE_UNSPECIFIED, DATABASE_ENTITY_TYPE_SCHEMA, DATABASE_ENTITY_TYPE_TABLE, DATABASE_ENTITY_TYPE_COLUMN, DATABASE_ENTITY_TYPE_CONSTRAINT, DATABASE_ENTITY_TYPE_INDEX, DATABASE_ENTITY_TYPE_TRIGGER, DATABASE_ENTITY_TYPE_VIEW, DATABASE_ENTITY_TYPE_SEQUENCE, DATABASE_ENTITY_TYPE_STORED_PROCEDURE, DATABASE_ENTITY_TYPE_FUNCTION, DATABASE_ENTITY_TYPE_SYNONYM, DATABASE_ENTITY_TYPE_DATABASE_PACKAGE, DATABASE_ENTITY_TYPE_UDT, DATABASE_ENTITY_TYPE_MATERIALIZED_VIEW, DATABASE_ENTITY_TYPE_DATABASE }
	}
	case class EntityDdl(
	  /** Type of DDL (Create, Alter). */
		ddlType: Option[String] = None,
	  /** The name of the database entity the ddl refers to. */
		entity: Option[String] = None,
	  /** The actual ddl code. */
		ddl: Option[String] = None,
	  /** The entity type (if the DDL is for a sub entity). */
		entityType: Option[Schema.EntityDdl.EntityTypeEnum] = None,
	  /** EntityIssues found for this ddl. */
		issueId: Option[List[String]] = None
	)
	
	object EntityIssue {
		enum TypeEnum extends Enum[TypeEnum] { case ISSUE_TYPE_UNSPECIFIED, ISSUE_TYPE_DDL, ISSUE_TYPE_APPLY, ISSUE_TYPE_CONVERT }
		enum SeverityEnum extends Enum[SeverityEnum] { case ISSUE_SEVERITY_UNSPECIFIED, ISSUE_SEVERITY_INFO, ISSUE_SEVERITY_WARNING, ISSUE_SEVERITY_ERROR }
		enum EntityTypeEnum extends Enum[EntityTypeEnum] { case DATABASE_ENTITY_TYPE_UNSPECIFIED, DATABASE_ENTITY_TYPE_SCHEMA, DATABASE_ENTITY_TYPE_TABLE, DATABASE_ENTITY_TYPE_COLUMN, DATABASE_ENTITY_TYPE_CONSTRAINT, DATABASE_ENTITY_TYPE_INDEX, DATABASE_ENTITY_TYPE_TRIGGER, DATABASE_ENTITY_TYPE_VIEW, DATABASE_ENTITY_TYPE_SEQUENCE, DATABASE_ENTITY_TYPE_STORED_PROCEDURE, DATABASE_ENTITY_TYPE_FUNCTION, DATABASE_ENTITY_TYPE_SYNONYM, DATABASE_ENTITY_TYPE_DATABASE_PACKAGE, DATABASE_ENTITY_TYPE_UDT, DATABASE_ENTITY_TYPE_MATERIALIZED_VIEW, DATABASE_ENTITY_TYPE_DATABASE }
	}
	case class EntityIssue(
	  /** Unique Issue ID. */
		id: Option[String] = None,
	  /** The type of the issue. */
		`type`: Option[Schema.EntityIssue.TypeEnum] = None,
	  /** Severity of the issue */
		severity: Option[Schema.EntityIssue.SeverityEnum] = None,
	  /** Issue detailed message */
		message: Option[String] = None,
	  /** Error/Warning code */
		code: Option[String] = None,
	  /** The ddl which caused the issue, if relevant. */
		ddl: Option[String] = None,
	  /** The position of the issue found, if relevant. */
		position: Option[Schema.Position] = None,
	  /** The entity type (if the DDL is for a sub entity). */
		entityType: Option[Schema.EntityIssue.EntityTypeEnum] = None
	)
	
	case class Position(
	  /** Issue line number */
		line: Option[Int] = None,
	  /** Issue column number */
		column: Option[Int] = None,
	  /** Issue offset */
		offset: Option[Int] = None,
	  /** Issue length */
		length: Option[Int] = None
	)
	
	case class DatabaseInstanceEntity(
	  /** Custom engine specific features. */
		customFeatures: Option[Map[String, JsValue]] = None
	)
	
	case class SchemaEntity(
	  /** Custom engine specific features. */
		customFeatures: Option[Map[String, JsValue]] = None
	)
	
	case class TableEntity(
	  /** Table columns. */
		columns: Option[List[Schema.ColumnEntity]] = None,
	  /** Table constraints. */
		constraints: Option[List[Schema.ConstraintEntity]] = None,
	  /** Table indices. */
		indices: Option[List[Schema.IndexEntity]] = None,
	  /** Table triggers. */
		triggers: Option[List[Schema.TriggerEntity]] = None,
	  /** Custom engine specific features. */
		customFeatures: Option[Map[String, JsValue]] = None,
	  /** Comment associated with the table. */
		comment: Option[String] = None
	)
	
	case class ColumnEntity(
	  /** Column name. */
		name: Option[String] = None,
	  /** Column data type. */
		dataType: Option[String] = None,
	  /** Charset override - instead of table level charset. */
		charset: Option[String] = None,
	  /** Collation override - instead of table level collation. */
		collation: Option[String] = None,
	  /** Column length - e.g. varchar (50). */
		length: Option[String] = None,
	  /** Column precision - when relevant. */
		precision: Option[Int] = None,
	  /** Column scale - when relevant. */
		scale: Option[Int] = None,
	  /** Column fractional second precision - used for timestamp based datatypes. */
		fractionalSecondsPrecision: Option[Int] = None,
	  /** Is the column of array type. */
		array: Option[Boolean] = None,
	  /** If the column is array, of which length. */
		arrayLength: Option[Int] = None,
	  /** Is the column nullable. */
		nullable: Option[Boolean] = None,
	  /** Is the column auto-generated/identity. */
		autoGenerated: Option[Boolean] = None,
	  /** Is the column a UDT. */
		udt: Option[Boolean] = None,
	  /** Custom engine specific features. */
		customFeatures: Option[Map[String, JsValue]] = None,
	  /** Specifies the list of values allowed in the column. Only used for set data type. */
		setValues: Option[List[String]] = None,
	  /** Comment associated with the column. */
		comment: Option[String] = None,
	  /** Column order in the table. */
		ordinalPosition: Option[Int] = None,
	  /** Default value of the column. */
		defaultValue: Option[String] = None
	)
	
	case class ConstraintEntity(
	  /** The name of the table constraint. */
		name: Option[String] = None,
	  /** Type of constraint, for example unique, primary key, foreign key (currently only primary key is supported). */
		`type`: Option[String] = None,
	  /** Table columns used as part of the Constraint, for example primary key constraint should list the columns which constitutes the key. */
		tableColumns: Option[List[String]] = None,
	  /** Custom engine specific features. */
		customFeatures: Option[Map[String, JsValue]] = None,
	  /** Reference columns which may be associated with the constraint. For example, if the constraint is a FOREIGN_KEY, this represents the list of full names of referenced columns by the foreign key. */
		referenceColumns: Option[List[String]] = None,
	  /** Reference table which may be associated with the constraint. For example, if the constraint is a FOREIGN_KEY, this represents the list of full name of the referenced table by the foreign key. */
		referenceTable: Option[String] = None,
	  /** Table which is associated with the constraint. In case the constraint is defined on a table, this field is left empty as this information is stored in parent_name. However, if constraint is defined on a view, this field stores the table name on which the view is defined. */
		tableName: Option[String] = None
	)
	
	case class IndexEntity(
	  /** The name of the index. */
		name: Option[String] = None,
	  /** Type of index, for example B-TREE. */
		`type`: Option[String] = None,
	  /** Table columns used as part of the Index, for example B-TREE index should list the columns which constitutes the index. */
		tableColumns: Option[List[String]] = None,
	  /** For each table_column, mark whether it's sorting order is ascending (false) or descending (true). If no value is defined, assume all columns are sorted in ascending order. Otherwise, the number of items must match that of table_columns with each value specifying the direction of the matched column by its index. */
		tableColumnsDescending: Option[List[Boolean]] = None,
	  /** Boolean value indicating whether the index is unique. */
		unique: Option[Boolean] = None,
	  /** Custom engine specific features. */
		customFeatures: Option[Map[String, JsValue]] = None
	)
	
	case class TriggerEntity(
	  /** The name of the trigger. */
		name: Option[String] = None,
	  /** The DML, DDL, or database events that fire the trigger, for example INSERT, UPDATE. */
		triggeringEvents: Option[List[String]] = None,
	  /** Indicates when the trigger fires, for example BEFORE STATEMENT, AFTER EACH ROW. */
		triggerType: Option[String] = None,
	  /** The SQL code which creates the trigger. */
		sqlCode: Option[String] = None,
	  /** Custom engine specific features. */
		customFeatures: Option[Map[String, JsValue]] = None
	)
	
	case class ViewEntity(
	  /** The SQL code which creates the view. */
		sqlCode: Option[String] = None,
	  /** Custom engine specific features. */
		customFeatures: Option[Map[String, JsValue]] = None,
	  /** View constraints. */
		constraints: Option[List[Schema.ConstraintEntity]] = None
	)
	
	case class SequenceEntity(
	  /** Increment value for the sequence. */
		increment: Option[String] = None,
	  /** Start number for the sequence represented as bytes to accommodate large. numbers */
		startValue: Option[String] = None,
	  /** Maximum number for the sequence represented as bytes to accommodate large. numbers */
		maxValue: Option[String] = None,
	  /** Minimum number for the sequence represented as bytes to accommodate large. numbers */
		minValue: Option[String] = None,
	  /** Indicates whether the sequence value should cycle through. */
		cycle: Option[Boolean] = None,
	  /** Indicates number of entries to cache / precreate. */
		cache: Option[String] = None,
	  /** Custom engine specific features. */
		customFeatures: Option[Map[String, JsValue]] = None
	)
	
	case class StoredProcedureEntity(
	  /** The SQL code which creates the stored procedure. */
		sqlCode: Option[String] = None,
	  /** Custom engine specific features. */
		customFeatures: Option[Map[String, JsValue]] = None
	)
	
	case class FunctionEntity(
	  /** The SQL code which creates the function. */
		sqlCode: Option[String] = None,
	  /** Custom engine specific features. */
		customFeatures: Option[Map[String, JsValue]] = None
	)
	
	object SynonymEntity {
		enum SourceTypeEnum extends Enum[SourceTypeEnum] { case DATABASE_ENTITY_TYPE_UNSPECIFIED, DATABASE_ENTITY_TYPE_SCHEMA, DATABASE_ENTITY_TYPE_TABLE, DATABASE_ENTITY_TYPE_COLUMN, DATABASE_ENTITY_TYPE_CONSTRAINT, DATABASE_ENTITY_TYPE_INDEX, DATABASE_ENTITY_TYPE_TRIGGER, DATABASE_ENTITY_TYPE_VIEW, DATABASE_ENTITY_TYPE_SEQUENCE, DATABASE_ENTITY_TYPE_STORED_PROCEDURE, DATABASE_ENTITY_TYPE_FUNCTION, DATABASE_ENTITY_TYPE_SYNONYM, DATABASE_ENTITY_TYPE_DATABASE_PACKAGE, DATABASE_ENTITY_TYPE_UDT, DATABASE_ENTITY_TYPE_MATERIALIZED_VIEW, DATABASE_ENTITY_TYPE_DATABASE }
	}
	case class SynonymEntity(
	  /** The name of the entity for which the synonym is being created (the source). */
		sourceEntity: Option[String] = None,
	  /** The type of the entity for which the synonym is being created (usually a table or a sequence). */
		sourceType: Option[Schema.SynonymEntity.SourceTypeEnum] = None,
	  /** Custom engine specific features. */
		customFeatures: Option[Map[String, JsValue]] = None
	)
	
	case class PackageEntity(
	  /** The SQL code which creates the package. */
		packageSqlCode: Option[String] = None,
	  /** The SQL code which creates the package body. If the package specification has cursors or subprograms, then the package body is mandatory. */
		packageBody: Option[String] = None,
	  /** Custom engine specific features. */
		customFeatures: Option[Map[String, JsValue]] = None
	)
	
	case class UDTEntity(
	  /** The SQL code which creates the udt. */
		udtSqlCode: Option[String] = None,
	  /** The SQL code which creates the udt body. */
		udtBody: Option[String] = None,
	  /** Custom engine specific features. */
		customFeatures: Option[Map[String, JsValue]] = None
	)
	
	case class MaterializedViewEntity(
	  /** The SQL code which creates the view. */
		sqlCode: Option[String] = None,
	  /** Custom engine specific features. */
		customFeatures: Option[Map[String, JsValue]] = None
	)
	
	case class SearchBackgroundJobsResponse(
	  /** The list of conversion workspace mapping rules. */
		jobs: Option[List[Schema.BackgroundJobLogEntry]] = None
	)
	
	object BackgroundJobLogEntry {
		enum JobTypeEnum extends Enum[JobTypeEnum] { case BACKGROUND_JOB_TYPE_UNSPECIFIED, BACKGROUND_JOB_TYPE_SOURCE_SEED, BACKGROUND_JOB_TYPE_CONVERT, BACKGROUND_JOB_TYPE_APPLY_DESTINATION, BACKGROUND_JOB_TYPE_IMPORT_RULES_FILE }
		enum CompletionStateEnum extends Enum[CompletionStateEnum] { case JOB_COMPLETION_STATE_UNSPECIFIED, SUCCEEDED, FAILED }
	}
	case class BackgroundJobLogEntry(
	  /** The background job log entry ID. */
		id: Option[String] = None,
	  /** The type of job that was executed. */
		jobType: Option[Schema.BackgroundJobLogEntry.JobTypeEnum] = None,
	  /** The timestamp when the background job was started. */
		startTime: Option[String] = None,
	  /** The timestamp when the background job was finished. */
		finishTime: Option[String] = None,
	  /** Output only. Job completion state, i.e. the final state after the job completed. */
		completionState: Option[Schema.BackgroundJobLogEntry.CompletionStateEnum] = None,
	  /** Output only. Job completion comment, such as how many entities were seeded, how many warnings were found during conversion, and similar information. */
		completionComment: Option[String] = None,
	  /** Output only. Whether the client requested the conversion workspace to be committed after a successful completion of the job. */
		requestAutocommit: Option[Boolean] = None,
	  /** Output only. Seed job details. */
		seedJobDetails: Option[Schema.SeedJobDetails] = None,
	  /** Output only. Import rules job details. */
		importRulesJobDetails: Option[Schema.ImportRulesJobDetails] = None,
	  /** Output only. Convert job details. */
		convertJobDetails: Option[Schema.ConvertJobDetails] = None,
	  /** Output only. Apply job details. */
		applyJobDetails: Option[Schema.ApplyJobDetails] = None
	)
	
	case class SeedJobDetails(
	  /** Output only. The connection profile which was used for the seed job. */
		connectionProfile: Option[String] = None
	)
	
	object ImportRulesJobDetails {
		enum FileFormatEnum extends Enum[FileFormatEnum] { case IMPORT_RULES_FILE_FORMAT_UNSPECIFIED, IMPORT_RULES_FILE_FORMAT_HARBOUR_BRIDGE_SESSION_FILE, IMPORT_RULES_FILE_FORMAT_ORATOPG_CONFIG_FILE }
	}
	case class ImportRulesJobDetails(
	  /** Output only. File names used for the import rules job. */
		files: Option[List[String]] = None,
	  /** Output only. The requested file format. */
		fileFormat: Option[Schema.ImportRulesJobDetails.FileFormatEnum] = None
	)
	
	case class ConvertJobDetails(
	  /** Output only. AIP-160 based filter used to specify the entities to convert */
		filter: Option[String] = None
	)
	
	case class ApplyJobDetails(
	  /** Output only. The connection profile which was used for the apply job. */
		connectionProfile: Option[String] = None,
	  /** Output only. AIP-160 based filter used to specify the entities to apply */
		filter: Option[String] = None
	)
	
	case class DescribeConversionWorkspaceRevisionsResponse(
	  /** The list of conversion workspace revisions. */
		revisions: Option[List[Schema.ConversionWorkspace]] = None
	)
	
	case class FetchStaticIpsResponse(
	  /** List of static IPs. */
		staticIps: Option[List[String]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
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
	
	case class GoogleCloudClouddmsV1OperationMetadata(
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
	
	object MigrationJobVerificationError {
		enum ErrorCodeEnum extends Enum[ErrorCodeEnum] { case ERROR_CODE_UNSPECIFIED, CONNECTION_FAILURE, AUTHENTICATION_FAILURE, INVALID_CONNECTION_PROFILE_CONFIG, VERSION_INCOMPATIBILITY, CONNECTION_PROFILE_TYPES_INCOMPATIBILITY, NO_PGLOGICAL_INSTALLED, PGLOGICAL_NODE_ALREADY_EXISTS, INVALID_WAL_LEVEL, INVALID_SHARED_PRELOAD_LIBRARY, INSUFFICIENT_MAX_REPLICATION_SLOTS, INSUFFICIENT_MAX_WAL_SENDERS, INSUFFICIENT_MAX_WORKER_PROCESSES, UNSUPPORTED_EXTENSIONS, UNSUPPORTED_MIGRATION_TYPE, INVALID_RDS_LOGICAL_REPLICATION, UNSUPPORTED_GTID_MODE, UNSUPPORTED_TABLE_DEFINITION, UNSUPPORTED_DEFINER, CANT_RESTART_RUNNING_MIGRATION, SOURCE_ALREADY_SETUP, TABLES_WITH_LIMITED_SUPPORT, UNSUPPORTED_DATABASE_LOCALE, UNSUPPORTED_DATABASE_FDW_CONFIG, ERROR_RDBMS, SOURCE_SIZE_EXCEEDS_THRESHOLD, EXISTING_CONFLICTING_DATABASES, PARALLEL_IMPORT_INSUFFICIENT_PRIVILEGE, EXISTING_DATA, SOURCE_MAX_SUBSCRIPTIONS }
	}
	case class MigrationJobVerificationError(
	  /** Output only. An instance of ErrorCode specifying the error that occurred. */
		errorCode: Option[Schema.MigrationJobVerificationError.ErrorCodeEnum] = None,
	  /** Output only. A formatted message with further details about the error and a CTA. */
		errorMessage: Option[String] = None,
	  /** Output only. A specific detailed error message, if supplied by the engine. */
		errorDetailMessage: Option[String] = None
	)
}
