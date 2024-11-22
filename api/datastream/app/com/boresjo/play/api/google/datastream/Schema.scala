package com.boresjo.play.api.google.datastream

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
	
	case class ListConnectionProfilesResponse(
	  /** List of connection profiles. */
		connectionProfiles: Option[List[Schema.ConnectionProfile]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ConnectionProfile(
	  /** Output only. The resource's name. */
		name: Option[String] = None,
	  /** Output only. The create time of the resource. */
		createTime: Option[String] = None,
	  /** Output only. The update time of the resource. */
		updateTime: Option[String] = None,
	  /** Labels. */
		labels: Option[Map[String, String]] = None,
	  /** Required. Display name. */
		displayName: Option[String] = None,
	  /** Oracle ConnectionProfile configuration. */
		oracleProfile: Option[Schema.OracleProfile] = None,
	  /** Cloud Storage ConnectionProfile configuration. */
		gcsProfile: Option[Schema.GcsProfile] = None,
	  /** MySQL ConnectionProfile configuration. */
		mysqlProfile: Option[Schema.MysqlProfile] = None,
	  /** BigQuery Connection Profile configuration. */
		bigqueryProfile: Option[Schema.BigQueryProfile] = None,
	  /** PostgreSQL Connection Profile configuration. */
		postgresqlProfile: Option[Schema.PostgresqlProfile] = None,
	  /** SQLServer Connection Profile configuration. */
		sqlServerProfile: Option[Schema.SqlServerProfile] = None,
	  /** Static Service IP connectivity. */
		staticServiceIpConnectivity: Option[Schema.StaticServiceIpConnectivity] = None,
	  /** Forward SSH tunnel connectivity. */
		forwardSshConnectivity: Option[Schema.ForwardSshTunnelConnectivity] = None,
	  /** Private connectivity. */
		privateConnectivity: Option[Schema.PrivateConnectivity] = None
	)
	
	case class OracleProfile(
	  /** Required. Hostname for the Oracle connection. */
		hostname: Option[String] = None,
	  /** Port for the Oracle connection, default value is 1521. */
		port: Option[Int] = None,
	  /** Required. Username for the Oracle connection. */
		username: Option[String] = None,
	  /** Optional. Password for the Oracle connection. Mutually exclusive with the `secret_manager_stored_password` field. */
		password: Option[String] = None,
	  /** Required. Database for the Oracle connection. */
		databaseService: Option[String] = None,
	  /** Connection string attributes */
		connectionAttributes: Option[Map[String, String]] = None,
	  /** Optional. SSL configuration for the Oracle connection. */
		oracleSslConfig: Option[Schema.OracleSslConfig] = None,
	  /** Optional. Configuration for Oracle ASM connection. */
		oracleAsmConfig: Option[Schema.OracleAsmConfig] = None,
	  /** Optional. A reference to a Secret Manager resource name storing the Oracle connection password. Mutually exclusive with the `password` field. */
		secretManagerStoredPassword: Option[String] = None
	)
	
	case class OracleSslConfig(
	  /** Input only. PEM-encoded certificate of the CA that signed the source database server's certificate. */
		caCertificate: Option[String] = None,
	  /** Output only. Indicates whether the ca_certificate field has been set for this Connection-Profile. */
		caCertificateSet: Option[Boolean] = None
	)
	
	case class OracleAsmConfig(
	  /** Required. Hostname for the Oracle ASM connection. */
		hostname: Option[String] = None,
	  /** Required. Port for the Oracle ASM connection. */
		port: Option[Int] = None,
	  /** Required. Username for the Oracle ASM connection. */
		username: Option[String] = None,
	  /** Required. Password for the Oracle ASM connection. */
		password: Option[String] = None,
	  /** Required. ASM service name for the Oracle ASM connection. */
		asmService: Option[String] = None,
	  /** Optional. Connection string attributes */
		connectionAttributes: Option[Map[String, String]] = None,
	  /** Optional. SSL configuration for the Oracle connection. */
		oracleSslConfig: Option[Schema.OracleSslConfig] = None
	)
	
	case class GcsProfile(
	  /** Required. The Cloud Storage bucket name. */
		bucket: Option[String] = None,
	  /** The root path inside the Cloud Storage bucket. */
		rootPath: Option[String] = None
	)
	
	case class MysqlProfile(
	  /** Required. Hostname for the MySQL connection. */
		hostname: Option[String] = None,
	  /** Port for the MySQL connection, default value is 3306. */
		port: Option[Int] = None,
	  /** Required. Username for the MySQL connection. */
		username: Option[String] = None,
	  /** Optional. Input only. Password for the MySQL connection. Mutually exclusive with the `secret_manager_stored_password` field. */
		password: Option[String] = None,
	  /** SSL configuration for the MySQL connection. */
		sslConfig: Option[Schema.MysqlSslConfig] = None
	)
	
	case class MysqlSslConfig(
	  /** Input only. PEM-encoded private key associated with the Client Certificate. If this field is used then the 'client_certificate' and the 'ca_certificate' fields are mandatory. */
		clientKey: Option[String] = None,
	  /** Output only. Indicates whether the client_key field is set. */
		clientKeySet: Option[Boolean] = None,
	  /** Input only. PEM-encoded certificate that will be used by the replica to authenticate against the source database server. If this field is used then the 'client_key' and the 'ca_certificate' fields are mandatory. */
		clientCertificate: Option[String] = None,
	  /** Output only. Indicates whether the client_certificate field is set. */
		clientCertificateSet: Option[Boolean] = None,
	  /** Input only. PEM-encoded certificate of the CA that signed the source database server's certificate. */
		caCertificate: Option[String] = None,
	  /** Output only. Indicates whether the ca_certificate field is set. */
		caCertificateSet: Option[Boolean] = None
	)
	
	case class BigQueryProfile(
	
	)
	
	case class PostgresqlProfile(
	  /** Required. Hostname for the PostgreSQL connection. */
		hostname: Option[String] = None,
	  /** Port for the PostgreSQL connection, default value is 5432. */
		port: Option[Int] = None,
	  /** Required. Username for the PostgreSQL connection. */
		username: Option[String] = None,
	  /** Optional. Password for the PostgreSQL connection. Mutually exclusive with the `secret_manager_stored_password` field. */
		password: Option[String] = None,
	  /** Required. Database for the PostgreSQL connection. */
		database: Option[String] = None
	)
	
	case class SqlServerProfile(
	  /** Required. Hostname for the SQLServer connection. */
		hostname: Option[String] = None,
	  /** Port for the SQLServer connection, default value is 1433. */
		port: Option[Int] = None,
	  /** Required. Username for the SQLServer connection. */
		username: Option[String] = None,
	  /** Optional. Password for the SQLServer connection. Mutually exclusive with the `secret_manager_stored_password` field. */
		password: Option[String] = None,
	  /** Required. Database for the SQLServer connection. */
		database: Option[String] = None
	)
	
	case class StaticServiceIpConnectivity(
	
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
	  /** Required. A reference to a private connection resource. Format: `projects/{project}/locations/{location}/privateConnections/{name}` */
		privateConnection: Option[String] = None
	)
	
	case class DiscoverConnectionProfileRequest(
	  /** An ad-hoc connection profile configuration. */
		connectionProfile: Option[Schema.ConnectionProfile] = None,
	  /** A reference to an existing connection profile. */
		connectionProfileName: Option[String] = None,
	  /** Whether to retrieve the full hierarchy of data objects (TRUE) or only the current level (FALSE). */
		fullHierarchy: Option[Boolean] = None,
	  /** The number of hierarchy levels below the current level to be retrieved. */
		hierarchyDepth: Option[Int] = None,
	  /** Oracle RDBMS to enrich with child data objects and metadata. */
		oracleRdbms: Option[Schema.OracleRdbms] = None,
	  /** MySQL RDBMS to enrich with child data objects and metadata. */
		mysqlRdbms: Option[Schema.MysqlRdbms] = None,
	  /** PostgreSQL RDBMS to enrich with child data objects and metadata. */
		postgresqlRdbms: Option[Schema.PostgresqlRdbms] = None,
	  /** SQLServer RDBMS to enrich with child data objects and metadata. */
		sqlServerRdbms: Option[Schema.SqlServerRdbms] = None
	)
	
	case class OracleRdbms(
	  /** Oracle schemas/databases in the database server. */
		oracleSchemas: Option[List[Schema.OracleSchema]] = None
	)
	
	case class OracleSchema(
	  /** Schema name. */
		schema: Option[String] = None,
	  /** Tables in the schema. */
		oracleTables: Option[List[Schema.OracleTable]] = None
	)
	
	case class OracleTable(
	  /** Table name. */
		table: Option[String] = None,
	  /** Oracle columns in the schema. When unspecified as part of include/exclude objects, includes/excludes everything. */
		oracleColumns: Option[List[Schema.OracleColumn]] = None
	)
	
	case class OracleColumn(
	  /** Column name. */
		column: Option[String] = None,
	  /** The Oracle data type. */
		dataType: Option[String] = None,
	  /** Column length. */
		length: Option[Int] = None,
	  /** Column precision. */
		precision: Option[Int] = None,
	  /** Column scale. */
		scale: Option[Int] = None,
	  /** Column encoding. */
		encoding: Option[String] = None,
	  /** Whether or not the column represents a primary key. */
		primaryKey: Option[Boolean] = None,
	  /** Whether or not the column can accept a null value. */
		nullable: Option[Boolean] = None,
	  /** The ordinal position of the column in the table. */
		ordinalPosition: Option[Int] = None
	)
	
	case class MysqlRdbms(
	  /** Mysql databases on the server */
		mysqlDatabases: Option[List[Schema.MysqlDatabase]] = None
	)
	
	case class MysqlDatabase(
	  /** Database name. */
		database: Option[String] = None,
	  /** Tables in the database. */
		mysqlTables: Option[List[Schema.MysqlTable]] = None
	)
	
	case class MysqlTable(
	  /** Table name. */
		table: Option[String] = None,
	  /** MySQL columns in the database. When unspecified as part of include/exclude objects, includes/excludes everything. */
		mysqlColumns: Option[List[Schema.MysqlColumn]] = None
	)
	
	case class MysqlColumn(
	  /** Column name. */
		column: Option[String] = None,
	  /** The MySQL data type. Full data types list can be found here: https://dev.mysql.com/doc/refman/8.0/en/data-types.html */
		dataType: Option[String] = None,
	  /** Column length. */
		length: Option[Int] = None,
	  /** Column collation. */
		collation: Option[String] = None,
	  /** Whether or not the column represents a primary key. */
		primaryKey: Option[Boolean] = None,
	  /** Whether or not the column can accept a null value. */
		nullable: Option[Boolean] = None,
	  /** The ordinal position of the column in the table. */
		ordinalPosition: Option[Int] = None,
	  /** Column precision. */
		precision: Option[Int] = None,
	  /** Column scale. */
		scale: Option[Int] = None
	)
	
	case class PostgresqlRdbms(
	  /** PostgreSQL schemas in the database server. */
		postgresqlSchemas: Option[List[Schema.PostgresqlSchema]] = None
	)
	
	case class PostgresqlSchema(
	  /** Schema name. */
		schema: Option[String] = None,
	  /** Tables in the schema. */
		postgresqlTables: Option[List[Schema.PostgresqlTable]] = None
	)
	
	case class PostgresqlTable(
	  /** Table name. */
		table: Option[String] = None,
	  /** PostgreSQL columns in the schema. When unspecified as part of include/exclude objects, includes/excludes everything. */
		postgresqlColumns: Option[List[Schema.PostgresqlColumn]] = None
	)
	
	case class PostgresqlColumn(
	  /** Column name. */
		column: Option[String] = None,
	  /** The PostgreSQL data type. */
		dataType: Option[String] = None,
	  /** Column length. */
		length: Option[Int] = None,
	  /** Column precision. */
		precision: Option[Int] = None,
	  /** Column scale. */
		scale: Option[Int] = None,
	  /** Whether or not the column represents a primary key. */
		primaryKey: Option[Boolean] = None,
	  /** Whether or not the column can accept a null value. */
		nullable: Option[Boolean] = None,
	  /** The ordinal position of the column in the table. */
		ordinalPosition: Option[Int] = None
	)
	
	case class SqlServerRdbms(
	  /** SQLServer schemas in the database server. */
		schemas: Option[List[Schema.SqlServerSchema]] = None
	)
	
	case class SqlServerSchema(
	  /** Schema name. */
		schema: Option[String] = None,
	  /** Tables in the schema. */
		tables: Option[List[Schema.SqlServerTable]] = None
	)
	
	case class SqlServerTable(
	  /** Table name. */
		table: Option[String] = None,
	  /** SQLServer columns in the schema. When unspecified as part of include/exclude objects, includes/excludes everything. */
		columns: Option[List[Schema.SqlServerColumn]] = None
	)
	
	case class SqlServerColumn(
	  /** Column name. */
		column: Option[String] = None,
	  /** The SQLServer data type. */
		dataType: Option[String] = None,
	  /** Column length. */
		length: Option[Int] = None,
	  /** Column precision. */
		precision: Option[Int] = None,
	  /** Column scale. */
		scale: Option[Int] = None,
	  /** Whether or not the column represents a primary key. */
		primaryKey: Option[Boolean] = None,
	  /** Whether or not the column can accept a null value. */
		nullable: Option[Boolean] = None,
	  /** The ordinal position of the column in the table. */
		ordinalPosition: Option[Int] = None
	)
	
	case class DiscoverConnectionProfileResponse(
	  /** Enriched Oracle RDBMS object. */
		oracleRdbms: Option[Schema.OracleRdbms] = None,
	  /** Enriched MySQL RDBMS object. */
		mysqlRdbms: Option[Schema.MysqlRdbms] = None,
	  /** Enriched PostgreSQL RDBMS object. */
		postgresqlRdbms: Option[Schema.PostgresqlRdbms] = None,
	  /** Enriched SQLServer RDBMS object. */
		sqlServerRdbms: Option[Schema.SqlServerRdbms] = None
	)
	
	case class ListStreamsResponse(
	  /** List of streams */
		streams: Option[List[Schema.Stream]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Stream {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, NOT_STARTED, RUNNING, PAUSED, MAINTENANCE, FAILED, FAILED_PERMANENTLY, STARTING, DRAINING }
	}
	case class Stream(
	  /** Output only. The stream's name. */
		name: Option[String] = None,
	  /** Output only. The creation time of the stream. */
		createTime: Option[String] = None,
	  /** Output only. The last update time of the stream. */
		updateTime: Option[String] = None,
	  /** Labels. */
		labels: Option[Map[String, String]] = None,
	  /** Required. Display name. */
		displayName: Option[String] = None,
	  /** Required. Source connection profile configuration. */
		sourceConfig: Option[Schema.SourceConfig] = None,
	  /** Required. Destination connection profile configuration. */
		destinationConfig: Option[Schema.DestinationConfig] = None,
	  /** The state of the stream. */
		state: Option[Schema.Stream.StateEnum] = None,
	  /** Automatically backfill objects included in the stream source configuration. Specific objects can be excluded. */
		backfillAll: Option[Schema.BackfillAllStrategy] = None,
	  /** Do not automatically backfill any objects. */
		backfillNone: Option[Schema.BackfillNoneStrategy] = None,
	  /** Output only. Errors on the Stream. */
		errors: Option[List[Schema.Error]] = None,
	  /** Immutable. A reference to a KMS encryption key. If provided, it will be used to encrypt the data. If left blank, data will be encrypted using an internal Stream-specific encryption key provisioned through KMS. */
		customerManagedEncryptionKey: Option[String] = None,
	  /** Output only. If the stream was recovered, the time of the last recovery. Note: This field is currently experimental. */
		lastRecoveryTime: Option[String] = None
	)
	
	case class SourceConfig(
	  /** Required. Source connection profile resoource. Format: `projects/{project}/locations/{location}/connectionProfiles/{name}` */
		sourceConnectionProfile: Option[String] = None,
	  /** Oracle data source configuration. */
		oracleSourceConfig: Option[Schema.OracleSourceConfig] = None,
	  /** MySQL data source configuration. */
		mysqlSourceConfig: Option[Schema.MysqlSourceConfig] = None,
	  /** PostgreSQL data source configuration. */
		postgresqlSourceConfig: Option[Schema.PostgresqlSourceConfig] = None,
	  /** SQLServer data source configuration. */
		sqlServerSourceConfig: Option[Schema.SqlServerSourceConfig] = None
	)
	
	case class OracleSourceConfig(
	  /** Oracle objects to include in the stream. */
		includeObjects: Option[Schema.OracleRdbms] = None,
	  /** Oracle objects to exclude from the stream. */
		excludeObjects: Option[Schema.OracleRdbms] = None,
	  /** Maximum number of concurrent CDC tasks. The number should be non-negative. If not set (or set to 0), the system's default value is used. */
		maxConcurrentCdcTasks: Option[Int] = None,
	  /** Maximum number of concurrent backfill tasks. The number should be non-negative. If not set (or set to 0), the system's default value is used. */
		maxConcurrentBackfillTasks: Option[Int] = None,
	  /** Drop large object values. */
		dropLargeObjects: Option[Schema.DropLargeObjects] = None,
	  /** Stream large object values. */
		streamLargeObjects: Option[Schema.StreamLargeObjects] = None,
	  /** Use LogMiner. */
		logMiner: Option[Schema.LogMiner] = None,
	  /** Use Binary Log Parser. */
		binaryLogParser: Option[Schema.BinaryLogParser] = None
	)
	
	case class DropLargeObjects(
	
	)
	
	case class StreamLargeObjects(
	
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
	
	case class MysqlSourceConfig(
	  /** MySQL objects to retrieve from the source. */
		includeObjects: Option[Schema.MysqlRdbms] = None,
	  /** MySQL objects to exclude from the stream. */
		excludeObjects: Option[Schema.MysqlRdbms] = None,
	  /** Maximum number of concurrent CDC tasks. The number should be non negative. If not set (or set to 0), the system's default value will be used. */
		maxConcurrentCdcTasks: Option[Int] = None,
	  /** Maximum number of concurrent backfill tasks. The number should be non negative. If not set (or set to 0), the system's default value will be used. */
		maxConcurrentBackfillTasks: Option[Int] = None,
	  /** Use Binary log position based replication. */
		binaryLogPosition: Option[Schema.BinaryLogPosition] = None,
	  /** Use GTID based replication. */
		gtid: Option[Schema.Gtid] = None
	)
	
	case class BinaryLogPosition(
	
	)
	
	case class Gtid(
	
	)
	
	case class PostgresqlSourceConfig(
	  /** PostgreSQL objects to include in the stream. */
		includeObjects: Option[Schema.PostgresqlRdbms] = None,
	  /** PostgreSQL objects to exclude from the stream. */
		excludeObjects: Option[Schema.PostgresqlRdbms] = None,
	  /** Required. Immutable. The name of the logical replication slot that's configured with the pgoutput plugin. */
		replicationSlot: Option[String] = None,
	  /** Required. The name of the publication that includes the set of all tables that are defined in the stream's include_objects. */
		publication: Option[String] = None,
	  /** Maximum number of concurrent backfill tasks. The number should be non negative. If not set (or set to 0), the system's default value will be used. */
		maxConcurrentBackfillTasks: Option[Int] = None
	)
	
	case class SqlServerSourceConfig(
	  /** SQLServer objects to include in the stream. */
		includeObjects: Option[Schema.SqlServerRdbms] = None,
	  /** SQLServer objects to exclude from the stream. */
		excludeObjects: Option[Schema.SqlServerRdbms] = None,
	  /** Max concurrent CDC tasks. */
		maxConcurrentCdcTasks: Option[Int] = None,
	  /** Max concurrent backfill tasks. */
		maxConcurrentBackfillTasks: Option[Int] = None,
	  /** CDC reader reads from transaction logs. */
		transactionLogs: Option[Schema.SqlServerTransactionLogs] = None,
	  /** CDC reader reads from change tables. */
		changeTables: Option[Schema.SqlServerChangeTables] = None
	)
	
	case class SqlServerTransactionLogs(
	
	)
	
	case class SqlServerChangeTables(
	
	)
	
	case class DestinationConfig(
	  /** Required. Destination connection profile resource. Format: `projects/{project}/locations/{location}/connectionProfiles/{name}` */
		destinationConnectionProfile: Option[String] = None,
	  /** A configuration for how data should be loaded to Cloud Storage. */
		gcsDestinationConfig: Option[Schema.GcsDestinationConfig] = None,
	  /** BigQuery destination configuration. */
		bigqueryDestinationConfig: Option[Schema.BigQueryDestinationConfig] = None
	)
	
	case class GcsDestinationConfig(
	  /** Path inside the Cloud Storage bucket to write data to. */
		path: Option[String] = None,
	  /** The maximum file size to be saved in the bucket. */
		fileRotationMb: Option[Int] = None,
	  /** The maximum duration for which new events are added before a file is closed and a new file is created. Values within the range of 15-60 seconds are allowed. */
		fileRotationInterval: Option[String] = None,
	  /** AVRO file format configuration. */
		avroFileFormat: Option[Schema.AvroFileFormat] = None,
	  /** JSON file format configuration. */
		jsonFileFormat: Option[Schema.JsonFileFormat] = None
	)
	
	case class AvroFileFormat(
	
	)
	
	object JsonFileFormat {
		enum SchemaFileFormatEnum extends Enum[SchemaFileFormatEnum] { case SCHEMA_FILE_FORMAT_UNSPECIFIED, NO_SCHEMA_FILE, AVRO_SCHEMA_FILE }
		enum CompressionEnum extends Enum[CompressionEnum] { case JSON_COMPRESSION_UNSPECIFIED, NO_COMPRESSION, GZIP }
	}
	case class JsonFileFormat(
	  /** The schema file format along JSON data files. */
		schemaFileFormat: Option[Schema.JsonFileFormat.SchemaFileFormatEnum] = None,
	  /** Compression of the loaded JSON file. */
		compression: Option[Schema.JsonFileFormat.CompressionEnum] = None
	)
	
	case class BigQueryDestinationConfig(
	  /** Single destination dataset. */
		singleTargetDataset: Option[Schema.SingleTargetDataset] = None,
	  /** Source hierarchy datasets. */
		sourceHierarchyDatasets: Option[Schema.SourceHierarchyDatasets] = None,
	  /** The guaranteed data freshness (in seconds) when querying tables created by the stream. Editing this field will only affect new tables created in the future, but existing tables will not be impacted. Lower values mean that queries will return fresher data, but may result in higher cost. */
		dataFreshness: Option[String] = None,
	  /** The standard mode */
		merge: Option[Schema.Merge] = None,
	  /** Append only mode */
		appendOnly: Option[Schema.AppendOnly] = None
	)
	
	case class SingleTargetDataset(
	  /** The dataset ID of the target dataset. DatasetIds allowed characters: https://cloud.google.com/bigquery/docs/reference/rest/v2/datasets#datasetreference. */
		datasetId: Option[String] = None
	)
	
	case class SourceHierarchyDatasets(
	  /** The dataset template to use for dynamic dataset creation. */
		datasetTemplate: Option[Schema.DatasetTemplate] = None
	)
	
	case class DatasetTemplate(
	  /** Required. The geographic location where the dataset should reside. See https://cloud.google.com/bigquery/docs/locations for supported locations. */
		location: Option[String] = None,
	  /** If supplied, every created dataset will have its name prefixed by the provided value. The prefix and name will be separated by an underscore. i.e. _. */
		datasetIdPrefix: Option[String] = None,
	  /** Describes the Cloud KMS encryption key that will be used to protect destination BigQuery table. The BigQuery Service Account associated with your project requires access to this encryption key. i.e. projects/{project}/locations/{location}/keyRings/{key_ring}/cryptoKeys/{cryptoKey}. See https://cloud.google.com/bigquery/docs/customer-managed-encryption for more information. */
		kmsKeyName: Option[String] = None
	)
	
	case class Merge(
	
	)
	
	case class AppendOnly(
	
	)
	
	case class BackfillAllStrategy(
	  /** Oracle data source objects to avoid backfilling. */
		oracleExcludedObjects: Option[Schema.OracleRdbms] = None,
	  /** MySQL data source objects to avoid backfilling. */
		mysqlExcludedObjects: Option[Schema.MysqlRdbms] = None,
	  /** PostgreSQL data source objects to avoid backfilling. */
		postgresqlExcludedObjects: Option[Schema.PostgresqlRdbms] = None,
	  /** SQLServer data source objects to avoid backfilling */
		sqlServerExcludedObjects: Option[Schema.SqlServerRdbms] = None
	)
	
	case class BackfillNoneStrategy(
	
	)
	
	case class Error(
	  /** A title that explains the reason for the error. */
		reason: Option[String] = None,
	  /** A unique identifier for this specific error, allowing it to be traced throughout the system in logs and API responses. */
		errorUuid: Option[String] = None,
	  /** A message containing more information about the error that occurred. */
		message: Option[String] = None,
	  /** The time when the error occurred. */
		errorTime: Option[String] = None,
	  /** Additional information about the error. */
		details: Option[Map[String, String]] = None
	)
	
	case class RunStreamRequest(
	  /** Optional. The CDC strategy of the stream. If not set, the system's default value will be used. */
		cdcStrategy: Option[Schema.CdcStrategy] = None,
	  /** Optional. Update the stream without validating it. */
		force: Option[Boolean] = None
	)
	
	case class CdcStrategy(
	  /** Optional. Start replicating from the most recent position in the source. */
		mostRecentStartPosition: Option[Schema.MostRecentStartPosition] = None,
	  /** Optional. Resume replication from the next available position in the source. */
		nextAvailableStartPosition: Option[Schema.NextAvailableStartPosition] = None,
	  /** Optional. Start replicating from a specific position in the source. */
		specificStartPosition: Option[Schema.SpecificStartPosition] = None
	)
	
	case class MostRecentStartPosition(
	
	)
	
	case class NextAvailableStartPosition(
	
	)
	
	case class SpecificStartPosition(
	  /** MySQL specific log position to start replicating from. */
		mysqlLogPosition: Option[Schema.MysqlLogPosition] = None,
	  /** Oracle SCN to start replicating from. */
		oracleScnPosition: Option[Schema.OracleScnPosition] = None
	)
	
	case class MysqlLogPosition(
	  /** Required. The binary log file name. */
		logFile: Option[String] = None,
	  /** Optional. The position within the binary log file. Default is head of file. */
		logPosition: Option[Int] = None
	)
	
	case class OracleScnPosition(
	  /** Required. SCN number from where Logs will be read */
		scn: Option[String] = None
	)
	
	case class StreamObject(
	  /** Output only. The object resource's name. */
		name: Option[String] = None,
	  /** Output only. The creation time of the object. */
		createTime: Option[String] = None,
	  /** Output only. The last update time of the object. */
		updateTime: Option[String] = None,
	  /** Required. Display name. */
		displayName: Option[String] = None,
	  /** Output only. Active errors on the object. */
		errors: Option[List[Schema.Error]] = None,
	  /** The latest backfill job that was initiated for the stream object. */
		backfillJob: Option[Schema.BackfillJob] = None,
	  /** The object identifier in the data source. */
		sourceObject: Option[Schema.SourceObjectIdentifier] = None
	)
	
	object BackfillJob {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, NOT_STARTED, PENDING, ACTIVE, STOPPED, FAILED, COMPLETED, UNSUPPORTED }
		enum TriggerEnum extends Enum[TriggerEnum] { case TRIGGER_UNSPECIFIED, AUTOMATIC, MANUAL }
	}
	case class BackfillJob(
	  /** Output only. Backfill job state. */
		state: Option[Schema.BackfillJob.StateEnum] = None,
	  /** Backfill job's triggering reason. */
		trigger: Option[Schema.BackfillJob.TriggerEnum] = None,
	  /** Output only. Backfill job's start time. */
		lastStartTime: Option[String] = None,
	  /** Output only. Backfill job's end time. */
		lastEndTime: Option[String] = None,
	  /** Output only. Errors which caused the backfill job to fail. */
		errors: Option[List[Schema.Error]] = None
	)
	
	case class SourceObjectIdentifier(
	  /** Oracle data source object identifier. */
		oracleIdentifier: Option[Schema.OracleObjectIdentifier] = None,
	  /** Mysql data source object identifier. */
		mysqlIdentifier: Option[Schema.MysqlObjectIdentifier] = None,
	  /** PostgreSQL data source object identifier. */
		postgresqlIdentifier: Option[Schema.PostgresqlObjectIdentifier] = None,
	  /** SQLServer data source object identifier. */
		sqlServerIdentifier: Option[Schema.SqlServerObjectIdentifier] = None
	)
	
	case class OracleObjectIdentifier(
	  /** Required. The schema name. */
		schema: Option[String] = None,
	  /** Required. The table name. */
		table: Option[String] = None
	)
	
	case class MysqlObjectIdentifier(
	  /** Required. The database name. */
		database: Option[String] = None,
	  /** Required. The table name. */
		table: Option[String] = None
	)
	
	case class PostgresqlObjectIdentifier(
	  /** Required. The schema name. */
		schema: Option[String] = None,
	  /** Required. The table name. */
		table: Option[String] = None
	)
	
	case class SqlServerObjectIdentifier(
	  /** Required. The schema name. */
		schema: Option[String] = None,
	  /** Required. The table name. */
		table: Option[String] = None
	)
	
	case class LookupStreamObjectRequest(
	  /** Required. The source object identifier which maps to the stream object. */
		sourceObjectIdentifier: Option[Schema.SourceObjectIdentifier] = None
	)
	
	case class ListStreamObjectsResponse(
	  /** List of stream objects. */
		streamObjects: Option[List[Schema.StreamObject]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. */
		nextPageToken: Option[String] = None
	)
	
	case class StartBackfillJobRequest(
	
	)
	
	case class StartBackfillJobResponse(
	  /** The stream object resource a backfill job was started for. */
		`object`: Option[Schema.StreamObject] = None
	)
	
	case class StopBackfillJobRequest(
	
	)
	
	case class StopBackfillJobResponse(
	  /** The stream object resource the backfill job was stopped for. */
		`object`: Option[Schema.StreamObject] = None
	)
	
	case class FetchStaticIpsResponse(
	  /** list of static ips by account */
		staticIps: Option[List[String]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object PrivateConnection {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, CREATED, FAILED, DELETING, FAILED_TO_DELETE }
	}
	case class PrivateConnection(
	  /** Output only. The resource's name. */
		name: Option[String] = None,
	  /** Output only. The create time of the resource. */
		createTime: Option[String] = None,
	  /** Output only. The update time of the resource. */
		updateTime: Option[String] = None,
	  /** Labels. */
		labels: Option[Map[String, String]] = None,
	  /** Required. Display name. */
		displayName: Option[String] = None,
	  /** Output only. The state of the Private Connection. */
		state: Option[Schema.PrivateConnection.StateEnum] = None,
	  /** Output only. In case of error, the details of the error in a user-friendly format. */
		error: Option[Schema.Error] = None,
	  /** VPC Peering Config. */
		vpcPeeringConfig: Option[Schema.VpcPeeringConfig] = None
	)
	
	case class VpcPeeringConfig(
	  /** Required. Fully qualified name of the VPC that Datastream will peer to. Format: `projects/{project}/global/{networks}/{name}` */
		vpc: Option[String] = None,
	  /** Required. A free subnet for peering. (CIDR of /29) */
		subnet: Option[String] = None
	)
	
	case class ListPrivateConnectionsResponse(
	  /** List of private connectivity configurations. */
		privateConnections: Option[List[Schema.PrivateConnection]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class Route(
	  /** Output only. The resource's name. */
		name: Option[String] = None,
	  /** Output only. The create time of the resource. */
		createTime: Option[String] = None,
	  /** Output only. The update time of the resource. */
		updateTime: Option[String] = None,
	  /** Labels. */
		labels: Option[Map[String, String]] = None,
	  /** Required. Display name. */
		displayName: Option[String] = None,
	  /** Required. Destination address for connection */
		destinationAddress: Option[String] = None,
	  /** Destination port for connection */
		destinationPort: Option[Int] = None
	)
	
	case class ListRoutesResponse(
	  /** List of Routes. */
		routes: Option[List[Schema.Route]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
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
		apiVersion: Option[String] = None,
	  /** Output only. Results of executed validations if there are any. */
		validationResult: Option[Schema.ValidationResult] = None
	)
	
	case class ValidationResult(
	  /** A list of validations (includes both executed as well as not executed validations). */
		validations: Option[List[Schema.Validation]] = None
	)
	
	object Validation {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, NOT_EXECUTED, FAILED, PASSED, WARNING }
	}
	case class Validation(
	  /** A short description of the validation. */
		description: Option[String] = None,
	  /** Output only. Validation execution status. */
		state: Option[Schema.Validation.StateEnum] = None,
	  /** Messages reflecting the validation results. */
		message: Option[List[Schema.ValidationMessage]] = None,
	  /** A custom code identifying this validation. */
		code: Option[String] = None
	)
	
	object ValidationMessage {
		enum LevelEnum extends Enum[LevelEnum] { case LEVEL_UNSPECIFIED, WARNING, ERROR }
	}
	case class ValidationMessage(
	  /** The result of the validation. */
		message: Option[String] = None,
	  /** Message severity level (warning or error). */
		level: Option[Schema.ValidationMessage.LevelEnum] = None,
	  /** Additional metadata related to the result. */
		metadata: Option[Map[String, String]] = None,
	  /** A custom code identifying this specific message. */
		code: Option[String] = None
	)
}
