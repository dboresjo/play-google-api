package com.boresjo.play.api.google.spanner

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListScansResponse(
	  /** Available scans based on the list query parameters. */
		scans: Option[List[Schema.Scan]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class Scan(
	  /** The unique name of the scan, specific to the Database service implementing this interface. */
		name: Option[String] = None,
	  /** Additional information provided by the implementer. */
		details: Option[Map[String, JsValue]] = None,
	  /** A range of time (inclusive) for when the scan is defined. The lower bound for when the scan is defined. */
		startTime: Option[String] = None,
	  /** The upper bound for when the scan is defined. */
		endTime: Option[String] = None,
	  /** Output only. Cloud Key Visualizer scan data. Note, this field is not available to the ListScans method. */
		scanData: Option[Schema.ScanData] = None
	)
	
	case class ScanData(
	  /** A range of time (inclusive) for when the contained data is defined. The lower bound for when the contained data is defined. */
		startTime: Option[String] = None,
	  /** The upper bound for when the contained data is defined. */
		endTime: Option[String] = None,
	  /** Cloud Key Visualizer scan data. The range of time this information covers is captured via the above time range fields. Note, this field is not available to the ListScans method. */
		data: Option[Schema.VisualizationData] = None
	)
	
	object VisualizationData {
		enum KeyUnitEnum extends Enum[KeyUnitEnum] { case KEY_UNIT_UNSPECIFIED, KEY, CHUNK }
	}
	case class VisualizationData(
	  /** The token delimiting a datasource name from the rest of a key in a data_source. */
		dataSourceSeparatorToken: Option[String] = None,
	  /** The token signifying the end of a data_source. */
		dataSourceEndToken: Option[String] = None,
	  /** We discretize the entire keyspace into buckets. Assuming each bucket has an inclusive keyrange and covers keys from k(i) ... k(n). In this case k(n) would be an end key for a given range. end_key_string is the collection of all such end keys */
		endKeyStrings: Option[List[String]] = None,
	  /** Keys of key ranges that contribute significantly to a given metric Can be thought of as heavy hitters. */
		indexedKeys: Option[List[String]] = None,
	  /** The list of extracted key prefix nodes used in the key prefix hierarchy. */
		prefixNodes: Option[List[Schema.PrefixNode]] = None,
	  /** The list of data objects for each metric. */
		metrics: Option[List[Schema.Metric]] = None,
	  /** The list of messages (info, alerts, ...) */
		diagnosticMessages: Option[List[Schema.DiagnosticMessage]] = None,
	  /** The token delimiting the key prefixes. */
		keySeparator: Option[String] = None,
	  /** The unit for the key: e.g. 'key' or 'chunk'. */
		keyUnit: Option[Schema.VisualizationData.KeyUnitEnum] = None,
	  /** Whether this scan contains PII. */
		hasPii: Option[Boolean] = None
	)
	
	case class PrefixNode(
	  /** The string represented by the prefix node. */
		word: Option[String] = None,
	  /** The index of the start key bucket of the range that this node spans. */
		startIndex: Option[Int] = None,
	  /** The index of the end key bucket of the range that this node spans. */
		endIndex: Option[Int] = None,
	  /** The depth in the prefix hierarchy. */
		depth: Option[Int] = None,
	  /** Whether this corresponds to a data_source name. */
		dataSourceNode: Option[Boolean] = None
	)
	
	object Metric {
		enum AggregationEnum extends Enum[AggregationEnum] { case AGGREGATION_UNSPECIFIED, MAX, SUM }
	}
	case class Metric(
	  /** Whether the metric is visible to the end user. */
		visible: Option[Boolean] = None,
	  /** The displayed label of the metric. */
		displayLabel: Option[Schema.LocalizedString] = None,
	  /** The unit of the metric. */
		unit: Option[Schema.LocalizedString] = None,
	  /** Information about the metric. */
		info: Option[Schema.LocalizedString] = None,
	  /** The category of the metric, e.g. "Activity", "Alerts", "Reads", etc. */
		category: Option[Schema.LocalizedString] = None,
	  /** Whether the metric has any non-zero data. */
		hasNonzeroData: Option[Boolean] = None,
	  /** The value that is considered hot for the metric. On a per metric basis hotness signals high utilization and something that might potentially be a cause for concern by the end user. hot_value is used to calibrate and scale visual color scales. */
		hotValue: Option[BigDecimal] = None,
	  /** The aggregation function used to aggregate each key bucket */
		aggregation: Option[Schema.Metric.AggregationEnum] = None,
	  /** The data for the metric as a matrix. */
		matrix: Option[Schema.MetricMatrix] = None,
	  /** The references to numerator and denominator metrics for a derived metric. */
		derived: Option[Schema.DerivedMetric] = None,
	  /** The (sparse) mapping from time index to an IndexedHotKey message, representing those time intervals for which there are hot keys. */
		indexedHotKeys: Option[Map[String, Schema.IndexedHotKey]] = None,
	  /** The (sparse) mapping from time interval index to an IndexedKeyRangeInfos message, representing those time intervals for which there are informational messages concerning key ranges. */
		indexedKeyRangeInfos: Option[Map[String, Schema.IndexedKeyRangeInfos]] = None
	)
	
	case class LocalizedString(
	  /** The token identifying the message, e.g. 'METRIC_READ_CPU'. This should be unique within the service. */
		token: Option[String] = None,
	  /** The canonical English version of this message. If no token is provided or the front-end has no message associated with the token, this text will be displayed as-is. */
		message: Option[String] = None,
	  /** A map of arguments used when creating the localized message. Keys represent parameter names which may be used by the localized version when substituting dynamic values. */
		args: Option[Map[String, String]] = None
	)
	
	case class MetricMatrix(
	  /** The rows of the matrix. */
		rows: Option[List[Schema.MetricMatrixRow]] = None
	)
	
	case class MetricMatrixRow(
	  /** The columns of the row. */
		cols: Option[List[BigDecimal]] = None
	)
	
	case class DerivedMetric(
	  /** The name of the numerator metric. e.g. "latency". */
		numerator: Option[Schema.LocalizedString] = None,
	  /** The name of the denominator metric. e.g. "rows". */
		denominator: Option[Schema.LocalizedString] = None
	)
	
	case class IndexedHotKey(
	  /** A (sparse) mapping from key bucket index to the index of the specific hot row key for that key bucket. The index of the hot row key can be translated to the actual row key via the ScanData.VisualizationData.indexed_keys repeated field. */
		sparseHotKeys: Option[Map[String, Int]] = None
	)
	
	case class IndexedKeyRangeInfos(
	  /** A (sparse) mapping from key bucket index to the KeyRangeInfos for that key bucket. */
		keyRangeInfos: Option[Map[String, Schema.KeyRangeInfos]] = None
	)
	
	case class KeyRangeInfos(
	  /** The list individual KeyRangeInfos. */
		infos: Option[List[Schema.KeyRangeInfo]] = None,
	  /** The total size of the list of all KeyRangeInfos. This may be larger than the number of repeated messages above. If that is the case, this number may be used to determine how many are not being shown. */
		totalSize: Option[Int] = None
	)
	
	case class KeyRangeInfo(
	  /** The index of the start key in indexed_keys. */
		startKeyIndex: Option[Int] = None,
	  /** The index of the end key in indexed_keys. */
		endKeyIndex: Option[Int] = None,
	  /** The number of keys this range covers. */
		keysCount: Option[String] = None,
	  /** The name of the metric. e.g. "latency". */
		metric: Option[Schema.LocalizedString] = None,
	  /** The value of the metric. */
		value: Option[BigDecimal] = None,
	  /** The unit of the metric. This is an unstructured field and will be mapped as is to the user. */
		unit: Option[Schema.LocalizedString] = None,
	  /** Information about this key range, for all metrics. */
		info: Option[Schema.LocalizedString] = None,
	  /** The list of context values for this key range. */
		contextValues: Option[List[Schema.ContextValue]] = None,
	  /** The time offset. This is the time since the start of the time interval. */
		timeOffset: Option[String] = None
	)
	
	object ContextValue {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, INFO, WARNING, ERROR, FATAL }
	}
	case class ContextValue(
	  /** The label for the context value. e.g. "latency". */
		label: Option[Schema.LocalizedString] = None,
	  /** The value for the context. */
		value: Option[BigDecimal] = None,
	  /** The unit of the context value. */
		unit: Option[String] = None,
	  /** The severity of this context. */
		severity: Option[Schema.ContextValue.SeverityEnum] = None
	)
	
	object DiagnosticMessage {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, INFO, WARNING, ERROR, FATAL }
	}
	case class DiagnosticMessage(
	  /** The severity of the diagnostic message. */
		severity: Option[Schema.DiagnosticMessage.SeverityEnum] = None,
	  /** The metric. */
		metric: Option[Schema.LocalizedString] = None,
	  /** The short message. */
		shortMessage: Option[Schema.LocalizedString] = None,
	  /** Information about this diagnostic information. */
		info: Option[Schema.LocalizedString] = None,
	  /** Whether this message is specific only for the current metric. By default Diagnostics are shown for all metrics, regardless which metric is the currently selected metric in the UI. However occasionally a metric will generate so many messages that the resulting visual clutter becomes overwhelming. In this case setting this to true, will show the diagnostic messages for that metric only if it is the currently selected metric. */
		metricSpecific: Option[Boolean] = None
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
	
	case class Empty(
	
	)
	
	case class ListDatabasesResponse(
	  /** Databases that matched the request. */
		databases: Option[List[Schema.Database]] = None,
	  /** `next_page_token` can be sent in a subsequent ListDatabases call to fetch more of the matching databases. */
		nextPageToken: Option[String] = None
	)
	
	object Database {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY, READY_OPTIMIZING }
		enum DatabaseDialectEnum extends Enum[DatabaseDialectEnum] { case DATABASE_DIALECT_UNSPECIFIED, GOOGLE_STANDARD_SQL, POSTGRESQL }
	}
	case class Database(
	  /** Required. The name of the database. Values are of the form `projects//instances//databases/`, where `` is as specified in the `CREATE DATABASE` statement. This name can be passed to other API methods to identify the database. */
		name: Option[String] = None,
	  /** Output only. The current database state. */
		state: Option[Schema.Database.StateEnum] = None,
	  /** Output only. If exists, the time at which the database creation started. */
		createTime: Option[String] = None,
	  /** Output only. Applicable only for restored databases. Contains information about the restore source. */
		restoreInfo: Option[Schema.RestoreInfo] = None,
	  /** Output only. For databases that are using customer managed encryption, this field contains the encryption configuration for the database. For databases that are using Google default or other types of encryption, this field is empty. */
		encryptionConfig: Option[Schema.EncryptionConfig] = None,
	  /** Output only. For databases that are using customer managed encryption, this field contains the encryption information for the database, such as all Cloud KMS key versions that are in use. The `encryption_status` field inside of each `EncryptionInfo` is not populated. For databases that are using Google default or other types of encryption, this field is empty. This field is propagated lazily from the backend. There might be a delay from when a key version is being used and when it appears in this field. */
		encryptionInfo: Option[List[Schema.EncryptionInfo]] = None,
	  /** Output only. The period in which Cloud Spanner retains all versions of data for the database. This is the same as the value of version_retention_period database option set using UpdateDatabaseDdl. Defaults to 1 hour, if not set. */
		versionRetentionPeriod: Option[String] = None,
	  /** Output only. Earliest timestamp at which older versions of the data can be read. This value is continuously updated by Cloud Spanner and becomes stale the moment it is queried. If you are using this value to recover data, make sure to account for the time from the moment when the value is queried to the moment when you initiate the recovery. */
		earliestVersionTime: Option[String] = None,
	  /** Output only. The read-write region which contains the database's leader replicas. This is the same as the value of default_leader database option set using DatabaseAdmin.CreateDatabase or DatabaseAdmin.UpdateDatabaseDdl. If not explicitly set, this is empty. */
		defaultLeader: Option[String] = None,
	  /** Output only. The dialect of the Cloud Spanner Database. */
		databaseDialect: Option[Schema.Database.DatabaseDialectEnum] = None,
	  /** Optional. Whether drop protection is enabled for this database. Defaults to false, if not set. For more details, please see how to [prevent accidental database deletion](https://cloud.google.com/spanner/docs/prevent-database-deletion). */
		enableDropProtection: Option[Boolean] = None,
	  /** Output only. If true, the database is being updated. If false, there are no ongoing update operations for the database. */
		reconciling: Option[Boolean] = None,
	  /** Output only. Applicable only for databases that use dual-region instance configurations. Contains information about the quorum. */
		quorumInfo: Option[Schema.QuorumInfo] = None
	)
	
	object RestoreInfo {
		enum SourceTypeEnum extends Enum[SourceTypeEnum] { case TYPE_UNSPECIFIED, BACKUP }
	}
	case class RestoreInfo(
	  /** The type of the restore source. */
		sourceType: Option[Schema.RestoreInfo.SourceTypeEnum] = None,
	  /** Information about the backup used to restore the database. The backup may no longer exist. */
		backupInfo: Option[Schema.BackupInfo] = None
	)
	
	case class BackupInfo(
	  /** Name of the backup. */
		backup: Option[String] = None,
	  /** The backup contains an externally consistent copy of `source_database` at the timestamp specified by `version_time`. If the CreateBackup request did not specify `version_time`, the `version_time` of the backup is equivalent to the `create_time`. */
		versionTime: Option[String] = None,
	  /** The time the CreateBackup request was received. */
		createTime: Option[String] = None,
	  /** Name of the database the backup was created from. */
		sourceDatabase: Option[String] = None
	)
	
	case class EncryptionConfig(
	  /** The Cloud KMS key to be used for encrypting and decrypting the database. Values are of the form `projects//locations//keyRings//cryptoKeys/`. */
		kmsKeyName: Option[String] = None,
	  /** Specifies the KMS configuration for one or more keys used to encrypt the database. Values are of the form `projects//locations//keyRings//cryptoKeys/`. The keys referenced by `kms_key_names` must fully cover all regions of the database's instance configuration. Some examples: &#42; For regional (single-region) instance configurations, specify a regional location KMS key. &#42; For multi-region instance configurations of type `GOOGLE_MANAGED`, either specify a multi-region location KMS key or multiple regional location KMS keys that cover all regions in the instance configuration. &#42; For an instance configuration of type `USER_MANAGED`, specify only regional location KMS keys to cover each region in the instance configuration. Multi-region location KMS keys aren't supported for `USER_MANAGED` type instance configurations. */
		kmsKeyNames: Option[List[String]] = None
	)
	
	object EncryptionInfo {
		enum EncryptionTypeEnum extends Enum[EncryptionTypeEnum] { case TYPE_UNSPECIFIED, GOOGLE_DEFAULT_ENCRYPTION, CUSTOMER_MANAGED_ENCRYPTION }
	}
	case class EncryptionInfo(
	  /** Output only. The type of encryption. */
		encryptionType: Option[Schema.EncryptionInfo.EncryptionTypeEnum] = None,
	  /** Output only. If present, the status of a recent encrypt/decrypt call on underlying data for this database or backup. Regardless of status, data is always encrypted at rest. */
		encryptionStatus: Option[Schema.Status] = None,
	  /** Output only. A Cloud KMS key version that is being used to protect the database or backup. */
		kmsKeyVersion: Option[String] = None
	)
	
	object QuorumInfo {
		enum InitiatorEnum extends Enum[InitiatorEnum] { case INITIATOR_UNSPECIFIED, GOOGLE, USER }
	}
	case class QuorumInfo(
	  /** Output only. The type of this quorum. See QuorumType for more information about quorum type specifications. */
		quorumType: Option[Schema.QuorumType] = None,
	  /** Output only. Whether this `ChangeQuorum` is Google or User initiated. */
		initiator: Option[Schema.QuorumInfo.InitiatorEnum] = None,
	  /** Output only. The timestamp when the request was triggered. */
		startTime: Option[String] = None,
	  /** Output only. The etag is used for optimistic concurrency control as a way to help prevent simultaneous `ChangeQuorum` requests that might create a race condition. */
		etag: Option[String] = None
	)
	
	case class QuorumType(
	  /** Single-region quorum type. */
		singleRegion: Option[Schema.SingleRegionQuorum] = None,
	  /** Dual-region quorum type. */
		dualRegion: Option[Schema.DualRegionQuorum] = None
	)
	
	case class SingleRegionQuorum(
	  /** Required. The location of the serving region, e.g. "us-central1". The location must be one of the regions within the dual-region instance configuration of your database. The list of valid locations is available using the GetInstanceConfig API. This should only be used if you plan to change quorum to the single-region quorum type. */
		servingLocation: Option[String] = None
	)
	
	case class DualRegionQuorum(
	
	)
	
	object CreateDatabaseRequest {
		enum DatabaseDialectEnum extends Enum[DatabaseDialectEnum] { case DATABASE_DIALECT_UNSPECIFIED, GOOGLE_STANDARD_SQL, POSTGRESQL }
	}
	case class CreateDatabaseRequest(
	  /** Required. A `CREATE DATABASE` statement, which specifies the ID of the new database. The database ID must conform to the regular expression `a-z&#42;[a-z0-9]` and be between 2 and 30 characters in length. If the database ID is a reserved word or if it contains a hyphen, the database ID must be enclosed in backticks (`` ` ``). */
		createStatement: Option[String] = None,
	  /** Optional. A list of DDL statements to run inside the newly created database. Statements can create tables, indexes, etc. These statements execute atomically with the creation of the database: if there is an error in any statement, the database is not created. */
		extraStatements: Option[List[String]] = None,
	  /** Optional. The encryption configuration for the database. If this field is not specified, Cloud Spanner will encrypt/decrypt all data at rest using Google default encryption. */
		encryptionConfig: Option[Schema.EncryptionConfig] = None,
	  /** Optional. The dialect of the Cloud Spanner Database. */
		databaseDialect: Option[Schema.CreateDatabaseRequest.DatabaseDialectEnum] = None,
	  /** Optional. Proto descriptors used by `CREATE/ALTER PROTO BUNDLE` statements in 'extra_statements'. Contains a protobuf-serialized [`google.protobuf.FileDescriptorSet`](https://github.com/protocolbuffers/protobuf/blob/main/src/google/protobuf/descriptor.proto) descriptor set. To generate it, [install](https://grpc.io/docs/protoc-installation/) and run `protoc` with --include_imports and --descriptor_set_out. For example, to generate for moon/shot/app.proto, run ``` $protoc --proto_path=/app_path --proto_path=/lib_path \ --include_imports \ --descriptor_set_out=descriptors.data \ moon/shot/app.proto ``` For more details, see protobuffer [self description](https://developers.google.com/protocol-buffers/docs/techniques#self-description). */
		protoDescriptors: Option[String] = None
	)
	
	case class UpdateDatabaseDdlRequest(
	  /** Required. DDL statements to be applied to the database. */
		statements: Option[List[String]] = None,
	  /** If empty, the new update request is assigned an automatically-generated operation ID. Otherwise, `operation_id` is used to construct the name of the resulting Operation. Specifying an explicit operation ID simplifies determining whether the statements were executed in the event that the UpdateDatabaseDdl call is replayed, or the return value is otherwise lost: the database and `operation_id` fields can be combined to form the `name` of the resulting longrunning.Operation: `/operations/`. `operation_id` should be unique within the database, and must be a valid identifier: `a-z&#42;`. Note that automatically-generated operation IDs always begin with an underscore. If the named operation already exists, UpdateDatabaseDdl returns `ALREADY_EXISTS`. */
		operationId: Option[String] = None,
	  /** Optional. Proto descriptors used by CREATE/ALTER PROTO BUNDLE statements. Contains a protobuf-serialized [google.protobuf.FileDescriptorSet](https://github.com/protocolbuffers/protobuf/blob/main/src/google/protobuf/descriptor.proto). To generate it, [install](https://grpc.io/docs/protoc-installation/) and run `protoc` with --include_imports and --descriptor_set_out. For example, to generate for moon/shot/app.proto, run ``` $protoc --proto_path=/app_path --proto_path=/lib_path \ --include_imports \ --descriptor_set_out=descriptors.data \ moon/shot/app.proto ``` For more details, see protobuffer [self description](https://developers.google.com/protocol-buffers/docs/techniques#self-description). */
		protoDescriptors: Option[String] = None
	)
	
	case class GetDatabaseDdlResponse(
	  /** A list of formatted DDL statements defining the schema of the database specified in the request. */
		statements: Option[List[String]] = None,
	  /** Proto descriptors stored in the database. Contains a protobuf-serialized [google.protobuf.FileDescriptorSet](https://github.com/protocolbuffers/protobuf/blob/main/src/google/protobuf/descriptor.proto). For more details, see protobuffer [self description](https://developers.google.com/protocol-buffers/docs/techniques#self-description). */
		protoDescriptors: Option[String] = None
	)
	
	case class ChangeQuorumRequest(
	  /** Required. Name of the database in which to apply `ChangeQuorum`. Values are of the form `projects//instances//databases/`. */
		name: Option[String] = None,
	  /** Required. The type of this quorum. */
		quorumType: Option[Schema.QuorumType] = None,
	  /** Optional. The etag is the hash of the `QuorumInfo`. The `ChangeQuorum` operation is only performed if the etag matches that of the `QuorumInfo` in the current database resource. Otherwise the API returns an `ABORTED` error. The etag is used for optimistic concurrency control as a way to help prevent simultaneous change quorum requests that could create a race condition. */
		etag: Option[String] = None
	)
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None
	)
	
	case class Policy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.Binding]] = None,
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
	
	case class GetIamPolicyRequest(
	  /** OPTIONAL: A `GetPolicyOptions` object for specifying options to `GetIamPolicy`. */
		options: Option[Schema.GetPolicyOptions] = None
	)
	
	case class GetPolicyOptions(
	  /** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		requestedPolicyVersion: Option[Int] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** REQUIRED: The set of permissions to check for 'resource'. Permissions with wildcards (such as '&#42;', 'spanner.&#42;', 'spanner.instances.&#42;') are not allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	object Backup {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY }
		enum DatabaseDialectEnum extends Enum[DatabaseDialectEnum] { case DATABASE_DIALECT_UNSPECIFIED, GOOGLE_STANDARD_SQL, POSTGRESQL }
	}
	case class Backup(
	  /** Required for the CreateBackup operation. Name of the database from which this backup was created. This needs to be in the same instance as the backup. Values are of the form `projects//instances//databases/`. */
		database: Option[String] = None,
	  /** The backup will contain an externally consistent copy of the database at the timestamp specified by `version_time`. If `version_time` is not specified, the system will set `version_time` to the `create_time` of the backup. */
		versionTime: Option[String] = None,
	  /** Required for the CreateBackup operation. The expiration time of the backup, with microseconds granularity that must be at least 6 hours and at most 366 days from the time the CreateBackup request is processed. Once the `expire_time` has passed, the backup is eligible to be automatically deleted by Cloud Spanner to free the resources used by the backup. */
		expireTime: Option[String] = None,
	  /** Output only for the CreateBackup operation. Required for the UpdateBackup operation. A globally unique identifier for the backup which cannot be changed. Values are of the form `projects//instances//backups/a-z&#42;[a-z0-9]` The final segment of the name must be between 2 and 60 characters in length. The backup is stored in the location(s) specified in the instance configuration of the instance containing the backup, identified by the prefix of the backup name of the form `projects//instances/`. */
		name: Option[String] = None,
	  /** Output only. The time the CreateBackup request is received. If the request does not specify `version_time`, the `version_time` of the backup will be equivalent to the `create_time`. */
		createTime: Option[String] = None,
	  /** Output only. Size of the backup in bytes. For a backup in an incremental backup chain, this is the sum of the `exclusive_size_bytes` of itself and all older backups in the chain. */
		sizeBytes: Option[String] = None,
	  /** Output only. The number of bytes that will be freed by deleting this backup. This value will be zero if, for example, this backup is part of an incremental backup chain and younger backups in the chain require that we keep its data. For backups not in an incremental backup chain, this is always the size of the backup. This value may change if backups on the same chain get created, deleted or expired. */
		freeableSizeBytes: Option[String] = None,
	  /** Output only. For a backup in an incremental backup chain, this is the storage space needed to keep the data that has changed since the previous backup. For all other backups, this is always the size of the backup. This value may change if backups on the same chain get deleted or expired. This field can be used to calculate the total storage space used by a set of backups. For example, the total space used by all backups of a database can be computed by summing up this field. */
		exclusiveSizeBytes: Option[String] = None,
	  /** Output only. The current state of the backup. */
		state: Option[Schema.Backup.StateEnum] = None,
	  /** Output only. The names of the restored databases that reference the backup. The database names are of the form `projects//instances//databases/`. Referencing databases may exist in different instances. The existence of any referencing database prevents the backup from being deleted. When a restored database from the backup enters the `READY` state, the reference to the backup is removed. */
		referencingDatabases: Option[List[String]] = None,
	  /** Output only. The encryption information for the backup. */
		encryptionInfo: Option[Schema.EncryptionInfo] = None,
	  /** Output only. The encryption information for the backup, whether it is protected by one or more KMS keys. The information includes all Cloud KMS key versions used to encrypt the backup. The `encryption_status` field inside of each `EncryptionInfo` is not populated. At least one of the key versions must be available for the backup to be restored. If a key version is revoked in the middle of a restore, the restore behavior is undefined. */
		encryptionInformation: Option[List[Schema.EncryptionInfo]] = None,
	  /** Output only. The database dialect information for the backup. */
		databaseDialect: Option[Schema.Backup.DatabaseDialectEnum] = None,
	  /** Output only. The names of the destination backups being created by copying this source backup. The backup names are of the form `projects//instances//backups/`. Referencing backups may exist in different instances. The existence of any referencing backup prevents the backup from being deleted. When the copy operation is done (either successfully completed or cancelled or the destination backup is deleted), the reference to the backup is removed. */
		referencingBackups: Option[List[String]] = None,
	  /** Output only. The max allowed expiration time of the backup, with microseconds granularity. A backup's expiration time can be configured in multiple APIs: CreateBackup, UpdateBackup, CopyBackup. When updating or copying an existing backup, the expiration time specified must be less than `Backup.max_expire_time`. */
		maxExpireTime: Option[String] = None,
	  /** Output only. List of backup schedule URIs that are associated with creating this backup. This is only applicable for scheduled backups, and is empty for on-demand backups. To optimize for storage, whenever possible, multiple schedules are collapsed together to create one backup. In such cases, this field captures the list of all backup schedule URIs that are associated with creating this backup. If collapsing is not done, then this field captures the single backup schedule URI associated with creating this backup. */
		backupSchedules: Option[List[String]] = None,
	  /** Output only. Populated only for backups in an incremental backup chain. Backups share the same chain id if and only if they belong to the same incremental backup chain. Use this field to determine which backups are part of the same incremental backup chain. The ordering of backups in the chain can be determined by ordering the backup `version_time`. */
		incrementalBackupChainId: Option[String] = None,
	  /** Output only. Data deleted at a time older than this is guaranteed not to be retained in order to support this backup. For a backup in an incremental backup chain, this is the version time of the oldest backup that exists or ever existed in the chain. For all other backups, this is the version time of the backup. This field can be used to understand what data is being retained by the backup system. */
		oldestVersionTime: Option[String] = None
	)
	
	case class CopyBackupRequest(
	  /** Required. The id of the backup copy. The `backup_id` appended to `parent` forms the full backup_uri of the form `projects//instances//backups/`. */
		backupId: Option[String] = None,
	  /** Required. The source backup to be copied. The source backup needs to be in READY state for it to be copied. Once CopyBackup is in progress, the source backup cannot be deleted or cleaned up on expiration until CopyBackup is finished. Values are of the form: `projects//instances//backups/`. */
		sourceBackup: Option[String] = None,
	  /** Required. The expiration time of the backup in microsecond granularity. The expiration time must be at least 6 hours and at most 366 days from the `create_time` of the source backup. Once the `expire_time` has passed, the backup is eligible to be automatically deleted by Cloud Spanner to free the resources used by the backup. */
		expireTime: Option[String] = None,
	  /** Optional. The encryption configuration used to encrypt the backup. If this field is not specified, the backup will use the same encryption configuration as the source backup by default, namely encryption_type = `USE_CONFIG_DEFAULT_OR_BACKUP_ENCRYPTION`. */
		encryptionConfig: Option[Schema.CopyBackupEncryptionConfig] = None
	)
	
	object CopyBackupEncryptionConfig {
		enum EncryptionTypeEnum extends Enum[EncryptionTypeEnum] { case ENCRYPTION_TYPE_UNSPECIFIED, USE_CONFIG_DEFAULT_OR_BACKUP_ENCRYPTION, GOOGLE_DEFAULT_ENCRYPTION, CUSTOMER_MANAGED_ENCRYPTION }
	}
	case class CopyBackupEncryptionConfig(
	  /** Required. The encryption type of the backup. */
		encryptionType: Option[Schema.CopyBackupEncryptionConfig.EncryptionTypeEnum] = None,
	  /** Optional. The Cloud KMS key that will be used to protect the backup. This field should be set only when encryption_type is `CUSTOMER_MANAGED_ENCRYPTION`. Values are of the form `projects//locations//keyRings//cryptoKeys/`. */
		kmsKeyName: Option[String] = None,
	  /** Optional. Specifies the KMS configuration for the one or more keys used to protect the backup. Values are of the form `projects//locations//keyRings//cryptoKeys/`. KMS keys specified can be in any order. The keys referenced by `kms_key_names` must fully cover all regions of the backup's instance configuration. Some examples: &#42; For regional (single-region) instance configurations, specify a regional location KMS key. &#42; For multi-region instance configurations of type `GOOGLE_MANAGED`, either specify a multi-region location KMS key or multiple regional location KMS keys that cover all regions in the instance configuration. &#42; For an instance configuration of type `USER_MANAGED`, specify only regional location KMS keys to cover each region in the instance configuration. Multi-region location KMS keys aren't supported for `USER_MANAGED` type instance configurations. */
		kmsKeyNames: Option[List[String]] = None
	)
	
	case class ListBackupsResponse(
	  /** The list of matching backups. Backups returned are ordered by `create_time` in descending order, starting from the most recent `create_time`. */
		backups: Option[List[Schema.Backup]] = None,
	  /** `next_page_token` can be sent in a subsequent ListBackups call to fetch more of the matching backups. */
		nextPageToken: Option[String] = None
	)
	
	case class RestoreDatabaseRequest(
	  /** Required. The id of the database to create and restore to. This database must not already exist. The `database_id` appended to `parent` forms the full database name of the form `projects//instances//databases/`. */
		databaseId: Option[String] = None,
	  /** Name of the backup from which to restore. Values are of the form `projects//instances//backups/`. */
		backup: Option[String] = None,
	  /** Optional. An encryption configuration describing the encryption type and key resources in Cloud KMS used to encrypt/decrypt the database to restore to. If this field is not specified, the restored database will use the same encryption configuration as the backup by default, namely encryption_type = `USE_CONFIG_DEFAULT_OR_BACKUP_ENCRYPTION`. */
		encryptionConfig: Option[Schema.RestoreDatabaseEncryptionConfig] = None
	)
	
	object RestoreDatabaseEncryptionConfig {
		enum EncryptionTypeEnum extends Enum[EncryptionTypeEnum] { case ENCRYPTION_TYPE_UNSPECIFIED, USE_CONFIG_DEFAULT_OR_BACKUP_ENCRYPTION, GOOGLE_DEFAULT_ENCRYPTION, CUSTOMER_MANAGED_ENCRYPTION }
	}
	case class RestoreDatabaseEncryptionConfig(
	  /** Required. The encryption type of the restored database. */
		encryptionType: Option[Schema.RestoreDatabaseEncryptionConfig.EncryptionTypeEnum] = None,
	  /** Optional. The Cloud KMS key that will be used to encrypt/decrypt the restored database. This field should be set only when encryption_type is `CUSTOMER_MANAGED_ENCRYPTION`. Values are of the form `projects//locations//keyRings//cryptoKeys/`. */
		kmsKeyName: Option[String] = None,
	  /** Optional. Specifies the KMS configuration for one or more keys used to encrypt the database. Values have the form `projects//locations//keyRings//cryptoKeys/`. The keys referenced by `kms_key_names` must fully cover all regions of the database's instance configuration. Some examples: &#42; For regional (single-region) instance configurations, specify a regional location KMS key. &#42; For multi-region instance configurations of type `GOOGLE_MANAGED`, either specify a multi-region location KMS key or multiple regional location KMS keys that cover all regions in the instance configuration. &#42; For an instance configuration of type `USER_MANAGED`, specify only regional location KMS keys to cover each region in the instance configuration. Multi-region location KMS keys aren't supported for `USER_MANAGED` type instance configurations. */
		kmsKeyNames: Option[List[String]] = None
	)
	
	case class ListDatabaseOperationsResponse(
	  /** The list of matching database long-running operations. Each operation's name will be prefixed by the database's name. The operation's metadata field type `metadata.type_url` describes the type of the metadata. */
		operations: Option[List[Schema.Operation]] = None,
	  /** `next_page_token` can be sent in a subsequent ListDatabaseOperations call to fetch more of the matching metadata. */
		nextPageToken: Option[String] = None
	)
	
	case class ListBackupOperationsResponse(
	  /** The list of matching backup long-running operations. Each operation's name will be prefixed by the backup's name. The operation's metadata field type `metadata.type_url` describes the type of the metadata. Operations returned include those that are pending or have completed/failed/canceled within the last 7 days. Operations returned are ordered by `operation.metadata.value.progress.start_time` in descending order starting from the most recently started operation. */
		operations: Option[List[Schema.Operation]] = None,
	  /** `next_page_token` can be sent in a subsequent ListBackupOperations call to fetch more of the matching metadata. */
		nextPageToken: Option[String] = None
	)
	
	case class ListDatabaseRolesResponse(
	  /** Database roles that matched the request. */
		databaseRoles: Option[List[Schema.DatabaseRole]] = None,
	  /** `next_page_token` can be sent in a subsequent ListDatabaseRoles call to fetch more of the matching roles. */
		nextPageToken: Option[String] = None
	)
	
	case class DatabaseRole(
	  /** Required. The name of the database role. Values are of the form `projects//instances//databases//databaseRoles/` where `` is as specified in the `CREATE ROLE` DDL statement. */
		name: Option[String] = None
	)
	
	case class BackupSchedule(
	  /** Identifier. Output only for the CreateBackupSchedule operation. Required for the UpdateBackupSchedule operation. A globally unique identifier for the backup schedule which cannot be changed. Values are of the form `projects//instances//databases//backupSchedules/a-z&#42;[a-z0-9]` The final segment of the name must be between 2 and 60 characters in length. */
		name: Option[String] = None,
	  /** Optional. The schedule specification based on which the backup creations are triggered. */
		spec: Option[Schema.BackupScheduleSpec] = None,
	  /** Optional. The retention duration of a backup that must be at least 6 hours and at most 366 days. The backup is eligible to be automatically deleted once the retention period has elapsed. */
		retentionDuration: Option[String] = None,
	  /** Optional. The encryption configuration that will be used to encrypt the backup. If this field is not specified, the backup will use the same encryption configuration as the database. */
		encryptionConfig: Option[Schema.CreateBackupEncryptionConfig] = None,
	  /** The schedule creates only full backups. */
		fullBackupSpec: Option[Schema.FullBackupSpec] = None,
	  /** The schedule creates incremental backup chains. */
		incrementalBackupSpec: Option[Schema.IncrementalBackupSpec] = None,
	  /** Output only. The timestamp at which the schedule was last updated. If the schedule has never been updated, this field contains the timestamp when the schedule was first created. */
		updateTime: Option[String] = None
	)
	
	case class BackupScheduleSpec(
	  /** Cron style schedule specification. */
		cronSpec: Option[Schema.CrontabSpec] = None
	)
	
	case class CrontabSpec(
	  /** Required. Textual representation of the crontab. User can customize the backup frequency and the backup version time using the cron expression. The version time must be in UTC timzeone. The backup will contain an externally consistent copy of the database at the version time. Allowed frequencies are 12 hour, 1 day, 1 week and 1 month. Examples of valid cron specifications: &#42; `0 2/12 &#42; &#42; &#42; ` : every 12 hours at (2, 14) hours past midnight in UTC. &#42; `0 2,14 &#42; &#42; &#42; ` : every 12 hours at (2,14) hours past midnight in UTC. &#42; `0 2 &#42; &#42; &#42; ` : once a day at 2 past midnight in UTC. &#42; `0 2 &#42; &#42; 0 ` : once a week every Sunday at 2 past midnight in UTC. &#42; `0 2 8 &#42; &#42; ` : once a month on 8th day at 2 past midnight in UTC. */
		text: Option[String] = None,
	  /** Output only. The time zone of the times in `CrontabSpec.text`. Currently only UTC is supported. */
		timeZone: Option[String] = None,
	  /** Output only. Schedule backups will contain an externally consistent copy of the database at the version time specified in `schedule_spec.cron_spec`. However, Spanner may not initiate the creation of the scheduled backups at that version time. Spanner will initiate the creation of scheduled backups within the time window bounded by the version_time specified in `schedule_spec.cron_spec` and version_time + `creation_window`. */
		creationWindow: Option[String] = None
	)
	
	object CreateBackupEncryptionConfig {
		enum EncryptionTypeEnum extends Enum[EncryptionTypeEnum] { case ENCRYPTION_TYPE_UNSPECIFIED, USE_DATABASE_ENCRYPTION, GOOGLE_DEFAULT_ENCRYPTION, CUSTOMER_MANAGED_ENCRYPTION }
	}
	case class CreateBackupEncryptionConfig(
	  /** Required. The encryption type of the backup. */
		encryptionType: Option[Schema.CreateBackupEncryptionConfig.EncryptionTypeEnum] = None,
	  /** Optional. The Cloud KMS key that will be used to protect the backup. This field should be set only when encryption_type is `CUSTOMER_MANAGED_ENCRYPTION`. Values are of the form `projects//locations//keyRings//cryptoKeys/`. */
		kmsKeyName: Option[String] = None,
	  /** Optional. Specifies the KMS configuration for the one or more keys used to protect the backup. Values are of the form `projects//locations//keyRings//cryptoKeys/`. The keys referenced by `kms_key_names` must fully cover all regions of the backup's instance configuration. Some examples: &#42; For regional (single-region) instance configurations, specify a regional location KMS key. &#42; For multi-region instance configurations of type `GOOGLE_MANAGED`, either specify a multi-region location KMS key or multiple regional location KMS keys that cover all regions in the instance configuration. &#42; For an instance configuration of type `USER_MANAGED`, specify only regional location KMS keys to cover each region in the instance configuration. Multi-region location KMS keys aren't supported for `USER_MANAGED` type instance configurations. */
		kmsKeyNames: Option[List[String]] = None
	)
	
	case class FullBackupSpec(
	
	)
	
	case class IncrementalBackupSpec(
	
	)
	
	case class ListBackupSchedulesResponse(
	  /** The list of backup schedules for a database. */
		backupSchedules: Option[List[Schema.BackupSchedule]] = None,
	  /** `next_page_token` can be sent in a subsequent ListBackupSchedules call to fetch more of the schedules. */
		nextPageToken: Option[String] = None
	)
	
	case class ListInstanceConfigsResponse(
	  /** The list of requested instance configurations. */
		instanceConfigs: Option[List[Schema.InstanceConfig]] = None,
	  /** `next_page_token` can be sent in a subsequent ListInstanceConfigs call to fetch more of the matching instance configurations. */
		nextPageToken: Option[String] = None
	)
	
	object InstanceConfig {
		enum ConfigTypeEnum extends Enum[ConfigTypeEnum] { case TYPE_UNSPECIFIED, GOOGLE_MANAGED, USER_MANAGED }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY }
		enum FreeInstanceAvailabilityEnum extends Enum[FreeInstanceAvailabilityEnum] { case FREE_INSTANCE_AVAILABILITY_UNSPECIFIED, AVAILABLE, UNSUPPORTED, DISABLED, QUOTA_EXCEEDED }
		enum QuorumTypeEnum extends Enum[QuorumTypeEnum] { case QUORUM_TYPE_UNSPECIFIED, REGION, DUAL_REGION, MULTI_REGION }
	}
	case class InstanceConfig(
	  /** A unique identifier for the instance configuration. Values are of the form `projects//instanceConfigs/a-z&#42;`. User instance configuration must start with `custom-`. */
		name: Option[String] = None,
	  /** The name of this instance configuration as it appears in UIs. */
		displayName: Option[String] = None,
	  /** Output only. Whether this instance configuration is a Google-managed or user-managed configuration. */
		configType: Option[Schema.InstanceConfig.ConfigTypeEnum] = None,
	  /** The geographic placement of nodes in this instance configuration and their replication properties. To create user-managed configurations, input `replicas` must include all replicas in `replicas` of the `base_config` and include one or more replicas in the `optional_replicas` of the `base_config`. */
		replicas: Option[List[Schema.ReplicaInfo]] = None,
	  /** Output only. The available optional replicas to choose from for user-managed configurations. Populated for Google-managed configurations. */
		optionalReplicas: Option[List[Schema.ReplicaInfo]] = None,
	  /** Base configuration name, e.g. projects//instanceConfigs/nam3, based on which this configuration is created. Only set for user-managed configurations. `base_config` must refer to a configuration of type `GOOGLE_MANAGED` in the same project as this configuration. */
		baseConfig: Option[String] = None,
	  /** Cloud Labels are a flexible and lightweight mechanism for organizing cloud resources into groups that reflect a customer's organizational needs and deployment strategies. Cloud Labels can be used to filter collections of resources. They can be used to control how resource metrics are aggregated. And they can be used as arguments to policy management rules (e.g. route, firewall, load balancing, etc.). &#42; Label keys must be between 1 and 63 characters long and must conform to the following regular expression: `a-z{0,62}`. &#42; Label values must be between 0 and 63 characters long and must conform to the regular expression `[a-z0-9_-]{0,63}`. &#42; No more than 64 labels can be associated with a given resource. See https://goo.gl/xmQnxf for more information on and examples of labels. If you plan to use labels in your own code, please note that additional characters may be allowed in the future. Therefore, you are advised to use an internal label representation, such as JSON, which doesn't rely upon specific characters being disallowed. For example, representing labels as the string: name + "_" + value would prove problematic if we were to allow "_" in a future release. */
		labels: Option[Map[String, String]] = None,
	  /** etag is used for optimistic concurrency control as a way to help prevent simultaneous updates of a instance configuration from overwriting each other. It is strongly suggested that systems make use of the etag in the read-modify-write cycle to perform instance configuration updates in order to avoid race conditions: An etag is returned in the response which contains instance configurations, and systems are expected to put that etag in the request to update instance configuration to ensure that their change is applied to the same version of the instance configuration. If no etag is provided in the call to update the instance configuration, then the existing instance configuration is overwritten blindly. */
		etag: Option[String] = None,
	  /** Allowed values of the "default_leader" schema option for databases in instances that use this instance configuration. */
		leaderOptions: Option[List[String]] = None,
	  /** Output only. If true, the instance configuration is being created or updated. If false, there are no ongoing operations for the instance configuration. */
		reconciling: Option[Boolean] = None,
	  /** Output only. The current instance configuration state. Applicable only for `USER_MANAGED` configurations. */
		state: Option[Schema.InstanceConfig.StateEnum] = None,
	  /** Output only. Describes whether free instances are available to be created in this instance configuration. */
		freeInstanceAvailability: Option[Schema.InstanceConfig.FreeInstanceAvailabilityEnum] = None,
	  /** Output only. The `QuorumType` of the instance configuration. */
		quorumType: Option[Schema.InstanceConfig.QuorumTypeEnum] = None,
	  /** Output only. The storage limit in bytes per processing unit. */
		storageLimitPerProcessingUnit: Option[String] = None
	)
	
	object ReplicaInfo {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, READ_WRITE, READ_ONLY, WITNESS }
	}
	case class ReplicaInfo(
	  /** The location of the serving resources, e.g., "us-central1". */
		location: Option[String] = None,
	  /** The type of replica. */
		`type`: Option[Schema.ReplicaInfo.TypeEnum] = None,
	  /** If true, this location is designated as the default leader location where leader replicas are placed. See the [region types documentation](https://cloud.google.com/spanner/docs/instances#region_types) for more details. */
		defaultLeaderLocation: Option[Boolean] = None
	)
	
	case class CreateInstanceConfigRequest(
	  /** Required. The ID of the instance configuration to create. Valid identifiers are of the form `custom-[-a-z0-9]&#42;[a-z0-9]` and must be between 2 and 64 characters in length. The `custom-` prefix is required to avoid name conflicts with Google-managed configurations. */
		instanceConfigId: Option[String] = None,
	  /** Required. The `InstanceConfig` proto of the configuration to create. `instance_config.name` must be `/instanceConfigs/`. `instance_config.base_config` must be a Google-managed configuration name, e.g. /instanceConfigs/us-east1, /instanceConfigs/nam3. */
		instanceConfig: Option[Schema.InstanceConfig] = None,
	  /** An option to validate, but not actually execute, a request, and provide the same response. */
		validateOnly: Option[Boolean] = None
	)
	
	case class UpdateInstanceConfigRequest(
	  /** Required. The user instance configuration to update, which must always include the instance configuration name. Otherwise, only fields mentioned in update_mask need be included. To prevent conflicts of concurrent updates, etag can be used. */
		instanceConfig: Option[Schema.InstanceConfig] = None,
	  /** Required. A mask specifying which fields in InstanceConfig should be updated. The field mask must always be specified; this prevents any future fields in InstanceConfig from being erased accidentally by clients that do not know about them. Only display_name and labels can be updated. */
		updateMask: Option[String] = None,
	  /** An option to validate, but not actually execute, a request, and provide the same response. */
		validateOnly: Option[Boolean] = None
	)
	
	case class ListInstanceConfigOperationsResponse(
	  /** The list of matching instance configuration long-running operations. Each operation's name will be prefixed by the name of the instance configuration. The operation's metadata field type `metadata.type_url` describes the type of the metadata. */
		operations: Option[List[Schema.Operation]] = None,
	  /** `next_page_token` can be sent in a subsequent ListInstanceConfigOperations call to fetch more of the matching metadata. */
		nextPageToken: Option[String] = None
	)
	
	case class ListInstancesResponse(
	  /** The list of requested instances. */
		instances: Option[List[Schema.Instance]] = None,
	  /** `next_page_token` can be sent in a subsequent ListInstances call to fetch more of the matching instances. */
		nextPageToken: Option[String] = None,
	  /** The list of unreachable instances. It includes the names of instances whose metadata could not be retrieved within instance_deadline. */
		unreachable: Option[List[String]] = None
	)
	
	object Instance {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY }
		enum InstanceTypeEnum extends Enum[InstanceTypeEnum] { case INSTANCE_TYPE_UNSPECIFIED, PROVISIONED, FREE_INSTANCE }
		enum EditionEnum extends Enum[EditionEnum] { case EDITION_UNSPECIFIED, STANDARD, ENTERPRISE, ENTERPRISE_PLUS }
		enum DefaultBackupScheduleTypeEnum extends Enum[DefaultBackupScheduleTypeEnum] { case DEFAULT_BACKUP_SCHEDULE_TYPE_UNSPECIFIED, NONE, AUTOMATIC }
	}
	case class Instance(
	  /** Required. A unique identifier for the instance, which cannot be changed after the instance is created. Values are of the form `projects//instances/a-z&#42;[a-z0-9]`. The final segment of the name must be between 2 and 64 characters in length. */
		name: Option[String] = None,
	  /** Required. The name of the instance's configuration. Values are of the form `projects//instanceConfigs/`. See also InstanceConfig and ListInstanceConfigs. */
		config: Option[String] = None,
	  /** Required. The descriptive name for this instance as it appears in UIs. Must be unique per project and between 4 and 30 characters in length. */
		displayName: Option[String] = None,
	  /** The number of nodes allocated to this instance. At most, one of either `node_count` or `processing_units` should be present in the message. Users can set the `node_count` field to specify the target number of nodes allocated to the instance. If autoscaling is enabled, `node_count` is treated as an `OUTPUT_ONLY` field and reflects the current number of nodes allocated to the instance. This might be zero in API responses for instances that are not yet in the `READY` state. For more information, see [Compute capacity, nodes, and processing units](https://cloud.google.com/spanner/docs/compute-capacity). */
		nodeCount: Option[Int] = None,
	  /** The number of processing units allocated to this instance. At most, one of either `processing_units` or `node_count` should be present in the message. Users can set the `processing_units` field to specify the target number of processing units allocated to the instance. If autoscaling is enabled, `processing_units` is treated as an `OUTPUT_ONLY` field and reflects the current number of processing units allocated to the instance. This might be zero in API responses for instances that are not yet in the `READY` state. For more information, see [Compute capacity, nodes and processing units](https://cloud.google.com/spanner/docs/compute-capacity). */
		processingUnits: Option[Int] = None,
	  /** Output only. Lists the compute capacity per ReplicaSelection. A replica selection identifies a set of replicas with common properties. Replicas identified by a ReplicaSelection are scaled with the same compute capacity. */
		replicaComputeCapacity: Option[List[Schema.ReplicaComputeCapacity]] = None,
	  /** Optional. The autoscaling configuration. Autoscaling is enabled if this field is set. When autoscaling is enabled, node_count and processing_units are treated as OUTPUT_ONLY fields and reflect the current compute capacity allocated to the instance. */
		autoscalingConfig: Option[Schema.AutoscalingConfig] = None,
	  /** Output only. The current instance state. For CreateInstance, the state must be either omitted or set to `CREATING`. For UpdateInstance, the state must be either omitted or set to `READY`. */
		state: Option[Schema.Instance.StateEnum] = None,
	  /** Cloud Labels are a flexible and lightweight mechanism for organizing cloud resources into groups that reflect a customer's organizational needs and deployment strategies. Cloud Labels can be used to filter collections of resources. They can be used to control how resource metrics are aggregated. And they can be used as arguments to policy management rules (e.g. route, firewall, load balancing, etc.). &#42; Label keys must be between 1 and 63 characters long and must conform to the following regular expression: `a-z{0,62}`. &#42; Label values must be between 0 and 63 characters long and must conform to the regular expression `[a-z0-9_-]{0,63}`. &#42; No more than 64 labels can be associated with a given resource. See https://goo.gl/xmQnxf for more information on and examples of labels. If you plan to use labels in your own code, please note that additional characters may be allowed in the future. And so you are advised to use an internal label representation, such as JSON, which doesn't rely upon specific characters being disallowed. For example, representing labels as the string: name + "_" + value would prove problematic if we were to allow "_" in a future release. */
		labels: Option[Map[String, String]] = None,
	  /** The `InstanceType` of the current instance. */
		instanceType: Option[Schema.Instance.InstanceTypeEnum] = None,
	  /** Deprecated. This field is not populated. */
		endpointUris: Option[List[String]] = None,
	  /** Output only. The time at which the instance was created. */
		createTime: Option[String] = None,
	  /** Output only. The time at which the instance was most recently updated. */
		updateTime: Option[String] = None,
	  /** Free instance metadata. Only populated for free instances. */
		freeInstanceMetadata: Option[Schema.FreeInstanceMetadata] = None,
	  /** Optional. The `Edition` of the current instance. */
		edition: Option[Schema.Instance.EditionEnum] = None,
	  /** Optional. Controls the default backup behavior for new databases within the instance. Note that `AUTOMATIC` is not permitted for free instances, as backups and backup schedules are not allowed for free instances. In the `GetInstance` or `ListInstances` response, if the value of default_backup_schedule_type is unset or NONE, no default backup schedule will be created for new databases within the instance. */
		defaultBackupScheduleType: Option[Schema.Instance.DefaultBackupScheduleTypeEnum] = None
	)
	
	case class ReplicaComputeCapacity(
	  /** Required. Identifies replicas by specified properties. All replicas in the selection have the same amount of compute capacity. */
		replicaSelection: Option[Schema.InstanceReplicaSelection] = None,
	  /** The number of nodes allocated to each replica. This may be zero in API responses for instances that are not yet in state `READY`. */
		nodeCount: Option[Int] = None,
	  /** The number of processing units allocated to each replica. This may be zero in API responses for instances that are not yet in state `READY`. */
		processingUnits: Option[Int] = None
	)
	
	case class InstanceReplicaSelection(
	  /** Required. Name of the location of the replicas (e.g., "us-central1"). */
		location: Option[String] = None
	)
	
	case class AutoscalingConfig(
	  /** Required. Autoscaling limits for an instance. */
		autoscalingLimits: Option[Schema.AutoscalingLimits] = None,
	  /** Required. The autoscaling targets for an instance. */
		autoscalingTargets: Option[Schema.AutoscalingTargets] = None,
	  /** Optional. Optional asymmetric autoscaling options. Replicas matching the replica selection criteria will be autoscaled independently from other replicas. The autoscaler will scale the replicas based on the utilization of replicas identified by the replica selection. Replica selections should not overlap with each other. Other replicas (those do not match any replica selection) will be autoscaled together and will have the same compute capacity allocated to them. */
		asymmetricAutoscalingOptions: Option[List[Schema.AsymmetricAutoscalingOption]] = None
	)
	
	case class AutoscalingLimits(
	  /** Minimum number of nodes allocated to the instance. If set, this number should be greater than or equal to 1. */
		minNodes: Option[Int] = None,
	  /** Minimum number of processing units allocated to the instance. If set, this number should be multiples of 1000. */
		minProcessingUnits: Option[Int] = None,
	  /** Maximum number of nodes allocated to the instance. If set, this number should be greater than or equal to min_nodes. */
		maxNodes: Option[Int] = None,
	  /** Maximum number of processing units allocated to the instance. If set, this number should be multiples of 1000 and be greater than or equal to min_processing_units. */
		maxProcessingUnits: Option[Int] = None
	)
	
	case class AutoscalingTargets(
	  /** Required. The target high priority cpu utilization percentage that the autoscaler should be trying to achieve for the instance. This number is on a scale from 0 (no utilization) to 100 (full utilization). The valid range is [10, 90] inclusive. */
		highPriorityCpuUtilizationPercent: Option[Int] = None,
	  /** Required. The target storage utilization percentage that the autoscaler should be trying to achieve for the instance. This number is on a scale from 0 (no utilization) to 100 (full utilization). The valid range is [10, 99] inclusive. */
		storageUtilizationPercent: Option[Int] = None
	)
	
	case class AsymmetricAutoscalingOption(
	  /** Required. Selects the replicas to which this AsymmetricAutoscalingOption applies. Only read-only replicas are supported. */
		replicaSelection: Option[Schema.InstanceReplicaSelection] = None,
	  /** Optional. Overrides applied to the top-level autoscaling configuration for the selected replicas. */
		overrides: Option[Schema.AutoscalingConfigOverrides] = None
	)
	
	case class AutoscalingConfigOverrides(
	  /** Optional. If specified, overrides the min/max limit in the top-level autoscaling configuration for the selected replicas. */
		autoscalingLimits: Option[Schema.AutoscalingLimits] = None,
	  /** Optional. If specified, overrides the autoscaling target high_priority_cpu_utilization_percent in the top-level autoscaling configuration for the selected replicas. */
		autoscalingTargetHighPriorityCpuUtilizationPercent: Option[Int] = None
	)
	
	object FreeInstanceMetadata {
		enum ExpireBehaviorEnum extends Enum[ExpireBehaviorEnum] { case EXPIRE_BEHAVIOR_UNSPECIFIED, FREE_TO_PROVISIONED, REMOVE_AFTER_GRACE_PERIOD }
	}
	case class FreeInstanceMetadata(
	  /** Output only. Timestamp after which the instance will either be upgraded or scheduled for deletion after a grace period. ExpireBehavior is used to choose between upgrading or scheduling the free instance for deletion. This timestamp is set during the creation of a free instance. */
		expireTime: Option[String] = None,
	  /** Output only. If present, the timestamp at which the free instance was upgraded to a provisioned instance. */
		upgradeTime: Option[String] = None,
	  /** Specifies the expiration behavior of a free instance. The default of ExpireBehavior is `REMOVE_AFTER_GRACE_PERIOD`. This can be modified during or after creation, and before expiration. */
		expireBehavior: Option[Schema.FreeInstanceMetadata.ExpireBehaviorEnum] = None
	)
	
	case class ListInstancePartitionsResponse(
	  /** The list of requested instancePartitions. */
		instancePartitions: Option[List[Schema.InstancePartition]] = None,
	  /** `next_page_token` can be sent in a subsequent ListInstancePartitions call to fetch more of the matching instance partitions. */
		nextPageToken: Option[String] = None,
	  /** The list of unreachable instances or instance partitions. It includes the names of instances or instance partitions whose metadata could not be retrieved within instance_partition_deadline. */
		unreachable: Option[List[String]] = None
	)
	
	object InstancePartition {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY }
	}
	case class InstancePartition(
	  /** Required. A unique identifier for the instance partition. Values are of the form `projects//instances//instancePartitions/a-z&#42;[a-z0-9]`. The final segment of the name must be between 2 and 64 characters in length. An instance partition's name cannot be changed after the instance partition is created. */
		name: Option[String] = None,
	  /** Required. The name of the instance partition's configuration. Values are of the form `projects//instanceConfigs/`. See also InstanceConfig and ListInstanceConfigs. */
		config: Option[String] = None,
	  /** Required. The descriptive name for this instance partition as it appears in UIs. Must be unique per project and between 4 and 30 characters in length. */
		displayName: Option[String] = None,
	  /** The number of nodes allocated to this instance partition. Users can set the `node_count` field to specify the target number of nodes allocated to the instance partition. This may be zero in API responses for instance partitions that are not yet in state `READY`. */
		nodeCount: Option[Int] = None,
	  /** The number of processing units allocated to this instance partition. Users can set the `processing_units` field to specify the target number of processing units allocated to the instance partition. This might be zero in API responses for instance partitions that are not yet in the `READY` state. */
		processingUnits: Option[Int] = None,
	  /** Output only. The current instance partition state. */
		state: Option[Schema.InstancePartition.StateEnum] = None,
	  /** Output only. The time at which the instance partition was created. */
		createTime: Option[String] = None,
	  /** Output only. The time at which the instance partition was most recently updated. */
		updateTime: Option[String] = None,
	  /** Output only. The names of the databases that reference this instance partition. Referencing databases should share the parent instance. The existence of any referencing database prevents the instance partition from being deleted. */
		referencingDatabases: Option[List[String]] = None,
	  /** Output only. Deprecated: This field is not populated. Output only. The names of the backups that reference this instance partition. Referencing backups should share the parent instance. The existence of any referencing backup prevents the instance partition from being deleted. */
		referencingBackups: Option[List[String]] = None,
	  /** Used for optimistic concurrency control as a way to help prevent simultaneous updates of a instance partition from overwriting each other. It is strongly suggested that systems make use of the etag in the read-modify-write cycle to perform instance partition updates in order to avoid race conditions: An etag is returned in the response which contains instance partitions, and systems are expected to put that etag in the request to update instance partitions to ensure that their change will be applied to the same version of the instance partition. If no etag is provided in the call to update instance partition, then the existing instance partition is overwritten blindly. */
		etag: Option[String] = None
	)
	
	case class CreateInstanceRequest(
	  /** Required. The ID of the instance to create. Valid identifiers are of the form `a-z&#42;[a-z0-9]` and must be between 2 and 64 characters in length. */
		instanceId: Option[String] = None,
	  /** Required. The instance to create. The name may be omitted, but if specified must be `/instances/`. */
		instance: Option[Schema.Instance] = None
	)
	
	case class UpdateInstanceRequest(
	  /** Required. The instance to update, which must always include the instance name. Otherwise, only fields mentioned in field_mask need be included. */
		instance: Option[Schema.Instance] = None,
	  /** Required. A mask specifying which fields in Instance should be updated. The field mask must always be specified; this prevents any future fields in Instance from being erased accidentally by clients that do not know about them. */
		fieldMask: Option[String] = None
	)
	
	case class CreateInstancePartitionRequest(
	  /** Required. The ID of the instance partition to create. Valid identifiers are of the form `a-z&#42;[a-z0-9]` and must be between 2 and 64 characters in length. */
		instancePartitionId: Option[String] = None,
	  /** Required. The instance partition to create. The instance_partition.name may be omitted, but if specified must be `/instancePartitions/`. */
		instancePartition: Option[Schema.InstancePartition] = None
	)
	
	case class UpdateInstancePartitionRequest(
	  /** Required. The instance partition to update, which must always include the instance partition name. Otherwise, only fields mentioned in field_mask need be included. */
		instancePartition: Option[Schema.InstancePartition] = None,
	  /** Required. A mask specifying which fields in InstancePartition should be updated. The field mask must always be specified; this prevents any future fields in InstancePartition from being erased accidentally by clients that do not know about them. */
		fieldMask: Option[String] = None
	)
	
	case class ListInstancePartitionOperationsResponse(
	  /** The list of matching instance partition long-running operations. Each operation's name will be prefixed by the instance partition's name. The operation's metadata field type `metadata.type_url` describes the type of the metadata. */
		operations: Option[List[Schema.Operation]] = None,
	  /** `next_page_token` can be sent in a subsequent ListInstancePartitionOperations call to fetch more of the matching metadata. */
		nextPageToken: Option[String] = None,
	  /** The list of unreachable instance partitions. It includes the names of instance partitions whose operation metadata could not be retrieved within instance_partition_deadline. */
		unreachableInstancePartitions: Option[List[String]] = None
	)
	
	case class MoveInstanceRequest(
	  /** Required. The target instance configuration where to move the instance. Values are of the form `projects//instanceConfigs/`. */
		targetConfig: Option[String] = None
	)
	
	case class CreateSessionRequest(
	  /** Required. The session to create. */
		session: Option[Schema.Session] = None
	)
	
	case class Session(
	  /** Output only. The name of the session. This is always system-assigned. */
		name: Option[String] = None,
	  /** The labels for the session. &#42; Label keys must be between 1 and 63 characters long and must conform to the following regular expression: `[a-z]([-a-z0-9]&#42;[a-z0-9])?`. &#42; Label values must be between 0 and 63 characters long and must conform to the regular expression `([a-z]([-a-z0-9]&#42;[a-z0-9])?)?`. &#42; No more than 64 labels can be associated with a given session. See https://goo.gl/xmQnxf for more information on and examples of labels. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The timestamp when the session is created. */
		createTime: Option[String] = None,
	  /** Output only. The approximate timestamp when the session is last used. It is typically earlier than the actual last use time. */
		approximateLastUseTime: Option[String] = None,
	  /** The database role which created this session. */
		creatorRole: Option[String] = None,
	  /** Optional. If true, specifies a multiplexed session. Use a multiplexed session for multiple, concurrent read-only operations. Don't use them for read-write transactions, partitioned reads, or partitioned queries. Use CreateSession to create multiplexed sessions. Don't use BatchCreateSessions to create a multiplexed session. You can't delete or list multiplexed sessions. */
		multiplexed: Option[Boolean] = None
	)
	
	case class BatchCreateSessionsRequest(
	  /** Parameters to be applied to each created session. */
		sessionTemplate: Option[Schema.Session] = None,
	  /** Required. The number of sessions to be created in this batch call. The API may return fewer than the requested number of sessions. If a specific number of sessions are desired, the client can make additional calls to BatchCreateSessions (adjusting session_count as necessary). */
		sessionCount: Option[Int] = None
	)
	
	case class BatchCreateSessionsResponse(
	  /** The freshly created sessions. */
		session: Option[List[Schema.Session]] = None
	)
	
	case class ListSessionsResponse(
	  /** The list of requested sessions. */
		sessions: Option[List[Schema.Session]] = None,
	  /** `next_page_token` can be sent in a subsequent ListSessions call to fetch more of the matching sessions. */
		nextPageToken: Option[String] = None
	)
	
	object ExecuteSqlRequest {
		enum QueryModeEnum extends Enum[QueryModeEnum] { case NORMAL, PLAN, PROFILE, WITH_STATS, WITH_PLAN_AND_STATS }
	}
	case class ExecuteSqlRequest(
	  /** The transaction to use. For queries, if none is provided, the default is a temporary read-only transaction with strong concurrency. Standard DML statements require a read-write transaction. To protect against replays, single-use transactions are not supported. The caller must either supply an existing transaction ID or begin a new transaction. Partitioned DML requires an existing Partitioned DML transaction ID. */
		transaction: Option[Schema.TransactionSelector] = None,
	  /** Required. The SQL string. */
		sql: Option[String] = None,
	  /** Parameter names and values that bind to placeholders in the SQL string. A parameter placeholder consists of the `@` character followed by the parameter name (for example, `@firstName`). Parameter names must conform to the naming requirements of identifiers as specified at https://cloud.google.com/spanner/docs/lexical#identifiers. Parameters can appear anywhere that a literal value is expected. The same parameter name can be used more than once, for example: `"WHERE id > @msg_id AND id < @msg_id + 100"` It is an error to execute a SQL statement with unbound parameters. */
		params: Option[Map[String, JsValue]] = None,
	  /** It is not always possible for Cloud Spanner to infer the right SQL type from a JSON value. For example, values of type `BYTES` and values of type `STRING` both appear in params as JSON strings. In these cases, `param_types` can be used to specify the exact SQL type for some or all of the SQL statement parameters. See the definition of Type for more information about SQL types. */
		paramTypes: Option[Map[String, Schema.Type]] = None,
	  /** If this request is resuming a previously interrupted SQL statement execution, `resume_token` should be copied from the last PartialResultSet yielded before the interruption. Doing this enables the new SQL statement execution to resume where the last one left off. The rest of the request parameters must exactly match the request that yielded this token. */
		resumeToken: Option[String] = None,
	  /** Used to control the amount of debugging information returned in ResultSetStats. If partition_token is set, query_mode can only be set to QueryMode.NORMAL. */
		queryMode: Option[Schema.ExecuteSqlRequest.QueryModeEnum] = None,
	  /** If present, results will be restricted to the specified partition previously created using PartitionQuery(). There must be an exact match for the values of fields common to this message and the PartitionQueryRequest message used to create this partition_token. */
		partitionToken: Option[String] = None,
	  /** A per-transaction sequence number used to identify this request. This field makes each request idempotent such that if the request is received multiple times, at most one will succeed. The sequence number must be monotonically increasing within the transaction. If a request arrives for the first time with an out-of-order sequence number, the transaction may be aborted. Replays of previously handled requests will yield the same response as the first execution. Required for DML statements. Ignored for queries. */
		seqno: Option[String] = None,
	  /** Query optimizer configuration to use for the given query. */
		queryOptions: Option[Schema.QueryOptions] = None,
	  /** Common options for this request. */
		requestOptions: Option[Schema.RequestOptions] = None,
	  /** Directed read options for this request. */
		directedReadOptions: Option[Schema.DirectedReadOptions] = None,
	  /** If this is for a partitioned query and this field is set to `true`, the request is executed with Spanner Data Boost independent compute resources. If the field is set to `true` but the request does not set `partition_token`, the API returns an `INVALID_ARGUMENT` error. */
		dataBoostEnabled: Option[Boolean] = None
	)
	
	case class TransactionSelector(
	  /** Execute the read or SQL query in a temporary transaction. This is the most efficient way to execute a transaction that consists of a single SQL query. */
		singleUse: Option[Schema.TransactionOptions] = None,
	  /** Execute the read or SQL query in a previously-started transaction. */
		id: Option[String] = None,
	  /** Begin a new transaction and execute this read or SQL query in it. The transaction ID of the new transaction is returned in ResultSetMetadata.transaction, which is a Transaction. */
		begin: Option[Schema.TransactionOptions] = None
	)
	
	case class TransactionOptions(
	  /** Transaction may write. Authorization to begin a read-write transaction requires `spanner.databases.beginOrRollbackReadWriteTransaction` permission on the `session` resource. */
		readWrite: Option[Schema.ReadWrite] = None,
	  /** Partitioned DML transaction. Authorization to begin a Partitioned DML transaction requires `spanner.databases.beginPartitionedDmlTransaction` permission on the `session` resource. */
		partitionedDml: Option[Schema.PartitionedDml] = None,
	  /** Transaction will not write. Authorization to begin a read-only transaction requires `spanner.databases.beginReadOnlyTransaction` permission on the `session` resource. */
		readOnly: Option[Schema.ReadOnly] = None,
	  /** When `exclude_txn_from_change_streams` is set to `true`: &#42; Modifications from this transaction will not be recorded in change streams with DDL option `allow_txn_exclusion=true` that are tracking columns modified by these transactions. &#42; Modifications from this transaction will be recorded in change streams with DDL option `allow_txn_exclusion=false or not set` that are tracking columns modified by these transactions. When `exclude_txn_from_change_streams` is set to `false` or not set, Modifications from this transaction will be recorded in all change streams that are tracking columns modified by these transactions. `exclude_txn_from_change_streams` may only be specified for read-write or partitioned-dml transactions, otherwise the API will return an `INVALID_ARGUMENT` error. */
		excludeTxnFromChangeStreams: Option[Boolean] = None
	)
	
	object ReadWrite {
		enum ReadLockModeEnum extends Enum[ReadLockModeEnum] { case READ_LOCK_MODE_UNSPECIFIED, PESSIMISTIC, OPTIMISTIC }
	}
	case class ReadWrite(
	  /** Read lock mode for the transaction. */
		readLockMode: Option[Schema.ReadWrite.ReadLockModeEnum] = None,
	  /** Optional. Clients should pass the transaction ID of the previous transaction attempt that was aborted if this transaction is being executed on a multiplexed session. */
		multiplexedSessionPreviousTransactionId: Option[String] = None
	)
	
	case class PartitionedDml(
	
	)
	
	case class ReadOnly(
	  /** Read at a timestamp where all previously committed transactions are visible. */
		strong: Option[Boolean] = None,
	  /** Executes all reads at a timestamp >= `min_read_timestamp`. This is useful for requesting fresher data than some previous read, or data that is fresh enough to observe the effects of some previously committed transaction whose timestamp is known. Note that this option can only be used in single-use transactions. A timestamp in RFC3339 UTC \"Zulu\" format, accurate to nanoseconds. Example: `"2014-10-02T15:01:23.045123456Z"`. */
		minReadTimestamp: Option[String] = None,
	  /** Read data at a timestamp >= `NOW - max_staleness` seconds. Guarantees that all writes that have committed more than the specified number of seconds ago are visible. Because Cloud Spanner chooses the exact timestamp, this mode works even if the client's local clock is substantially skewed from Cloud Spanner commit timestamps. Useful for reading the freshest data available at a nearby replica, while bounding the possible staleness if the local replica has fallen behind. Note that this option can only be used in single-use transactions. */
		maxStaleness: Option[String] = None,
	  /** Executes all reads at the given timestamp. Unlike other modes, reads at a specific timestamp are repeatable; the same read at the same timestamp always returns the same data. If the timestamp is in the future, the read will block until the specified timestamp, modulo the read's deadline. Useful for large scale consistent reads such as mapreduces, or for coordinating many reads against a consistent snapshot of the data. A timestamp in RFC3339 UTC \"Zulu\" format, accurate to nanoseconds. Example: `"2014-10-02T15:01:23.045123456Z"`. */
		readTimestamp: Option[String] = None,
	  /** Executes all reads at a timestamp that is `exact_staleness` old. The timestamp is chosen soon after the read is started. Guarantees that all writes that have committed more than the specified number of seconds ago are visible. Because Cloud Spanner chooses the exact timestamp, this mode works even if the client's local clock is substantially skewed from Cloud Spanner commit timestamps. Useful for reading at nearby replicas without the distributed timestamp negotiation overhead of `max_staleness`. */
		exactStaleness: Option[String] = None,
	  /** If true, the Cloud Spanner-selected read timestamp is included in the Transaction message that describes the transaction. */
		returnReadTimestamp: Option[Boolean] = None
	)
	
	object Type {
		enum CodeEnum extends Enum[CodeEnum] { case TYPE_CODE_UNSPECIFIED, BOOL, INT64, FLOAT64, FLOAT32, TIMESTAMP, DATE, STRING, BYTES, ARRAY, STRUCT, NUMERIC, JSON, PROTO, ENUM, INTERVAL }
		enum TypeAnnotationEnum extends Enum[TypeAnnotationEnum] { case TYPE_ANNOTATION_CODE_UNSPECIFIED, PG_NUMERIC, PG_JSONB }
	}
	case class Type(
	  /** Required. The TypeCode for this type. */
		code: Option[Schema.Type.CodeEnum] = None,
	  /** If code == ARRAY, then `array_element_type` is the type of the array elements. */
		arrayElementType: Option[Schema.Type] = None,
	  /** If code == STRUCT, then `struct_type` provides type information for the struct's fields. */
		structType: Option[Schema.StructType] = None,
	  /** The TypeAnnotationCode that disambiguates SQL type that Spanner will use to represent values of this type during query processing. This is necessary for some type codes because a single TypeCode can be mapped to different SQL types depending on the SQL dialect. type_annotation typically is not needed to process the content of a value (it doesn't affect serialization) and clients can ignore it on the read path. */
		typeAnnotation: Option[Schema.Type.TypeAnnotationEnum] = None,
	  /** If code == PROTO or code == ENUM, then `proto_type_fqn` is the fully qualified name of the proto type representing the proto/enum definition. */
		protoTypeFqn: Option[String] = None
	)
	
	case class StructType(
	  /** The list of fields that make up this struct. Order is significant, because values of this struct type are represented as lists, where the order of field values matches the order of fields in the StructType. In turn, the order of fields matches the order of columns in a read request, or the order of fields in the `SELECT` clause of a query. */
		fields: Option[List[Schema.Field]] = None
	)
	
	case class Field(
	  /** The name of the field. For reads, this is the column name. For SQL queries, it is the column alias (e.g., `"Word"` in the query `"SELECT 'hello' AS Word"`), or the column name (e.g., `"ColName"` in the query `"SELECT ColName FROM Table"`). Some columns might have an empty name (e.g., `"SELECT UPPER(ColName)"`). Note that a query result can contain multiple fields with the same name. */
		name: Option[String] = None,
	  /** The type of the field. */
		`type`: Option[Schema.Type] = None
	)
	
	case class QueryOptions(
	  /** An option to control the selection of optimizer version. This parameter allows individual queries to pick different query optimizer versions. Specifying `latest` as a value instructs Cloud Spanner to use the latest supported query optimizer version. If not specified, Cloud Spanner uses the optimizer version set at the database level options. Any other positive integer (from the list of supported optimizer versions) overrides the default optimizer version for query execution. The list of supported optimizer versions can be queried from SPANNER_SYS.SUPPORTED_OPTIMIZER_VERSIONS. Executing a SQL statement with an invalid optimizer version fails with an `INVALID_ARGUMENT` error. See https://cloud.google.com/spanner/docs/query-optimizer/manage-query-optimizer for more information on managing the query optimizer. The `optimizer_version` statement hint has precedence over this setting. */
		optimizerVersion: Option[String] = None,
	  /** An option to control the selection of optimizer statistics package. This parameter allows individual queries to use a different query optimizer statistics package. Specifying `latest` as a value instructs Cloud Spanner to use the latest generated statistics package. If not specified, Cloud Spanner uses the statistics package set at the database level options, or the latest package if the database option is not set. The statistics package requested by the query has to be exempt from garbage collection. This can be achieved with the following DDL statement: ``` ALTER STATISTICS SET OPTIONS (allow_gc=false) ``` The list of available statistics packages can be queried from `INFORMATION_SCHEMA.SPANNER_STATISTICS`. Executing a SQL statement with an invalid optimizer statistics package or with a statistics package that allows garbage collection fails with an `INVALID_ARGUMENT` error. */
		optimizerStatisticsPackage: Option[String] = None
	)
	
	object RequestOptions {
		enum PriorityEnum extends Enum[PriorityEnum] { case PRIORITY_UNSPECIFIED, PRIORITY_LOW, PRIORITY_MEDIUM, PRIORITY_HIGH }
	}
	case class RequestOptions(
	  /** Priority for the request. */
		priority: Option[Schema.RequestOptions.PriorityEnum] = None,
	  /** A per-request tag which can be applied to queries or reads, used for statistics collection. Both request_tag and transaction_tag can be specified for a read or query that belongs to a transaction. This field is ignored for requests where it's not applicable (e.g. CommitRequest). Legal characters for `request_tag` values are all printable characters (ASCII 32 - 126) and the length of a request_tag is limited to 50 characters. Values that exceed this limit are truncated. Any leading underscore (_) characters will be removed from the string. */
		requestTag: Option[String] = None,
	  /** A tag used for statistics collection about this transaction. Both request_tag and transaction_tag can be specified for a read or query that belongs to a transaction. The value of transaction_tag should be the same for all requests belonging to the same transaction. If this request doesn't belong to any transaction, transaction_tag will be ignored. Legal characters for `transaction_tag` values are all printable characters (ASCII 32 - 126) and the length of a transaction_tag is limited to 50 characters. Values that exceed this limit are truncated. Any leading underscore (_) characters will be removed from the string. */
		transactionTag: Option[String] = None
	)
	
	case class DirectedReadOptions(
	  /** Include_replicas indicates the order of replicas (as they appear in this list) to process the request. If auto_failover_disabled is set to true and all replicas are exhausted without finding a healthy replica, Spanner will wait for a replica in the list to become available, requests may fail due to `DEADLINE_EXCEEDED` errors. */
		includeReplicas: Option[Schema.IncludeReplicas] = None,
	  /** Exclude_replicas indicates that specified replicas should be excluded from serving requests. Spanner will not route requests to the replicas in this list. */
		excludeReplicas: Option[Schema.ExcludeReplicas] = None
	)
	
	case class IncludeReplicas(
	  /** The directed read replica selector. */
		replicaSelections: Option[List[Schema.ReplicaSelection]] = None,
	  /** If true, Spanner will not route requests to a replica outside the include_replicas list when all of the specified replicas are unavailable or unhealthy. Default value is `false`. */
		autoFailoverDisabled: Option[Boolean] = None
	)
	
	object ReplicaSelection {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, READ_WRITE, READ_ONLY }
	}
	case class ReplicaSelection(
	  /** The location or region of the serving requests, e.g. "us-east1". */
		location: Option[String] = None,
	  /** The type of replica. */
		`type`: Option[Schema.ReplicaSelection.TypeEnum] = None
	)
	
	case class ExcludeReplicas(
	  /** The directed read replica selector. */
		replicaSelections: Option[List[Schema.ReplicaSelection]] = None
	)
	
	case class ResultSet(
	  /** Metadata about the result set, such as row type information. */
		metadata: Option[Schema.ResultSetMetadata] = None,
	  /** Each element in `rows` is a row whose format is defined by metadata.row_type. The ith element in each row matches the ith field in metadata.row_type. Elements are encoded based on type as described here. */
		rows: Option[List[List[JsValue]]] = None,
	  /** Query plan and execution statistics for the SQL statement that produced this result set. These can be requested by setting ExecuteSqlRequest.query_mode. DML statements always produce stats containing the number of rows modified, unless executed using the ExecuteSqlRequest.QueryMode.PLAN ExecuteSqlRequest.query_mode. Other fields may or may not be populated, based on the ExecuteSqlRequest.query_mode. */
		stats: Option[Schema.ResultSetStats] = None,
	  /** Optional. A precommit token will be included if the read-write transaction is on a multiplexed session. The precommit token with the highest sequence number from this transaction attempt should be passed to the Commit request for this transaction. */
		precommitToken: Option[Schema.MultiplexedSessionPrecommitToken] = None
	)
	
	case class ResultSetMetadata(
	  /** Indicates the field names and types for the rows in the result set. For example, a SQL query like `"SELECT UserId, UserName FROM Users"` could return a `row_type` value like: "fields": [ { "name": "UserId", "type": { "code": "INT64" } }, { "name": "UserName", "type": { "code": "STRING" } }, ] */
		rowType: Option[Schema.StructType] = None,
	  /** If the read or SQL query began a transaction as a side-effect, the information about the new transaction is yielded here. */
		transaction: Option[Schema.Transaction] = None,
	  /** A SQL query can be parameterized. In PLAN mode, these parameters can be undeclared. This indicates the field names and types for those undeclared parameters in the SQL query. For example, a SQL query like `"SELECT &#42; FROM Users where UserId = @userId and UserName = @userName "` could return a `undeclared_parameters` value like: "fields": [ { "name": "UserId", "type": { "code": "INT64" } }, { "name": "UserName", "type": { "code": "STRING" } }, ] */
		undeclaredParameters: Option[Schema.StructType] = None
	)
	
	case class Transaction(
	  /** `id` may be used to identify the transaction in subsequent Read, ExecuteSql, Commit, or Rollback calls. Single-use read-only transactions do not have IDs, because single-use transactions do not support multiple requests. */
		id: Option[String] = None,
	  /** For snapshot read-only transactions, the read timestamp chosen for the transaction. Not returned by default: see TransactionOptions.ReadOnly.return_read_timestamp. A timestamp in RFC3339 UTC \"Zulu\" format, accurate to nanoseconds. Example: `"2014-10-02T15:01:23.045123456Z"`. */
		readTimestamp: Option[String] = None,
	  /** A precommit token will be included in the response of a BeginTransaction request if the read-write transaction is on a multiplexed session and a mutation_key was specified in the BeginTransaction. The precommit token with the highest sequence number from this transaction attempt should be passed to the Commit request for this transaction. */
		precommitToken: Option[Schema.MultiplexedSessionPrecommitToken] = None
	)
	
	case class MultiplexedSessionPrecommitToken(
	  /** Opaque precommit token. */
		precommitToken: Option[String] = None,
	  /** An incrementing seq number is generated on every precommit token that is returned. Clients should remember the precommit token with the highest sequence number from the current transaction attempt. */
		seqNum: Option[Int] = None
	)
	
	case class ResultSetStats(
	  /** QueryPlan for the query associated with this result. */
		queryPlan: Option[Schema.QueryPlan] = None,
	  /** Aggregated statistics from the execution of the query. Only present when the query is profiled. For example, a query could return the statistics as follows: { "rows_returned": "3", "elapsed_time": "1.22 secs", "cpu_time": "1.19 secs" } */
		queryStats: Option[Map[String, JsValue]] = None,
	  /** Standard DML returns an exact count of rows that were modified. */
		rowCountExact: Option[String] = None,
	  /** Partitioned DML does not offer exactly-once semantics, so it returns a lower bound of the rows modified. */
		rowCountLowerBound: Option[String] = None
	)
	
	case class QueryPlan(
	  /** The nodes in the query plan. Plan nodes are returned in pre-order starting with the plan root. Each PlanNode's `id` corresponds to its index in `plan_nodes`. */
		planNodes: Option[List[Schema.PlanNode]] = None,
	  /** Optional. The advices/recommendations for a query. Currently this field will be serving index recommendations for a query. */
		queryAdvice: Option[Schema.QueryAdvisorResult] = None
	)
	
	object PlanNode {
		enum KindEnum extends Enum[KindEnum] { case KIND_UNSPECIFIED, RELATIONAL, SCALAR }
	}
	case class PlanNode(
	  /** The `PlanNode`'s index in node list. */
		index: Option[Int] = None,
	  /** Used to determine the type of node. May be needed for visualizing different kinds of nodes differently. For example, If the node is a SCALAR node, it will have a condensed representation which can be used to directly embed a description of the node in its parent. */
		kind: Option[Schema.PlanNode.KindEnum] = None,
	  /** The display name for the node. */
		displayName: Option[String] = None,
	  /** List of child node `index`es and their relationship to this parent. */
		childLinks: Option[List[Schema.ChildLink]] = None,
	  /** Condensed representation for SCALAR nodes. */
		shortRepresentation: Option[Schema.ShortRepresentation] = None,
	  /** Attributes relevant to the node contained in a group of key-value pairs. For example, a Parameter Reference node could have the following information in its metadata: { "parameter_reference": "param1", "parameter_type": "array" } */
		metadata: Option[Map[String, JsValue]] = None,
	  /** The execution statistics associated with the node, contained in a group of key-value pairs. Only present if the plan was returned as a result of a profile query. For example, number of executions, number of rows/time per execution etc. */
		executionStats: Option[Map[String, JsValue]] = None
	)
	
	case class ChildLink(
	  /** The node to which the link points. */
		childIndex: Option[Int] = None,
	  /** The type of the link. For example, in Hash Joins this could be used to distinguish between the build child and the probe child, or in the case of the child being an output variable, to represent the tag associated with the output variable. */
		`type`: Option[String] = None,
	  /** Only present if the child node is SCALAR and corresponds to an output variable of the parent node. The field carries the name of the output variable. For example, a `TableScan` operator that reads rows from a table will have child links to the `SCALAR` nodes representing the output variables created for each column that is read by the operator. The corresponding `variable` fields will be set to the variable names assigned to the columns. */
		variable: Option[String] = None
	)
	
	case class ShortRepresentation(
	  /** A string representation of the expression subtree rooted at this node. */
		description: Option[String] = None,
	  /** A mapping of (subquery variable name) -> (subquery node id) for cases where the `description` string of this node references a `SCALAR` subquery contained in the expression subtree rooted at this node. The referenced `SCALAR` subquery may not necessarily be a direct child of this node. */
		subqueries: Option[Map[String, Int]] = None
	)
	
	case class QueryAdvisorResult(
	  /** Optional. Index Recommendation for a query. This is an optional field and the recommendation will only be available when the recommendation guarantees significant improvement in query performance. */
		indexAdvice: Option[List[Schema.IndexAdvice]] = None
	)
	
	case class IndexAdvice(
	  /** Optional. DDL statements to add new indexes that will improve the query. */
		ddl: Option[List[String]] = None,
	  /** Optional. Estimated latency improvement factor. For example if the query currently takes 500 ms to run and the estimated latency with new indexes is 100 ms this field will be 5. */
		improvementFactor: Option[BigDecimal] = None
	)
	
	case class PartialResultSet(
	  /** Metadata about the result set, such as row type information. Only present in the first response. */
		metadata: Option[Schema.ResultSetMetadata] = None,
	  /** A streamed result set consists of a stream of values, which might be split into many `PartialResultSet` messages to accommodate large rows and/or large values. Every N complete values defines a row, where N is equal to the number of entries in metadata.row_type.fields. Most values are encoded based on type as described here. It is possible that the last value in values is "chunked", meaning that the rest of the value is sent in subsequent `PartialResultSet`(s). This is denoted by the chunked_value field. Two or more chunked values can be merged to form a complete value as follows: &#42; `bool/number/null`: cannot be chunked &#42; `string`: concatenate the strings &#42; `list`: concatenate the lists. If the last element in a list is a `string`, `list`, or `object`, merge it with the first element in the next list by applying these rules recursively. &#42; `object`: concatenate the (field name, field value) pairs. If a field name is duplicated, then apply these rules recursively to merge the field values. Some examples of merging: # Strings are concatenated. "foo", "bar" => "foobar" # Lists of non-strings are concatenated. [2, 3], [4] => [2, 3, 4] # Lists are concatenated, but the last and first elements are merged # because they are strings. ["a", "b"], ["c", "d"] => ["a", "bc", "d"] # Lists are concatenated, but the last and first elements are merged # because they are lists. Recursively, the last and first elements # of the inner lists are merged because they are strings. ["a", ["b", "c"]], [["d"], "e"] => ["a", ["b", "cd"], "e"] # Non-overlapping object fields are combined. {"a": "1"}, {"b": "2"} => {"a": "1", "b": 2"} # Overlapping object fields are merged. {"a": "1"}, {"a": "2"} => {"a": "12"} # Examples of merging objects containing lists of strings. {"a": ["1"]}, {"a": ["2"]} => {"a": ["12"]} For a more complete example, suppose a streaming SQL query is yielding a result set whose rows contain a single string field. The following `PartialResultSet`s might be yielded: { "metadata": { ... } "values": ["Hello", "W"] "chunked_value": true "resume_token": "Af65..." } { "values": ["orl"] "chunked_value": true } { "values": ["d"] "resume_token": "Zx1B..." } This sequence of `PartialResultSet`s encodes two rows, one containing the field value `"Hello"`, and a second containing the field value `"World" = "W" + "orl" + "d"`. Not all `PartialResultSet`s contain a `resume_token`. Execution can only be resumed from a previously yielded `resume_token`. For the above sequence of `PartialResultSet`s, resuming the query with `"resume_token": "Af65..."` will yield results from the `PartialResultSet` with value `["orl"]`. */
		values: Option[List[JsValue]] = None,
	  /** If true, then the final value in values is chunked, and must be combined with more values from subsequent `PartialResultSet`s to obtain a complete field value. */
		chunkedValue: Option[Boolean] = None,
	  /** Streaming calls might be interrupted for a variety of reasons, such as TCP connection loss. If this occurs, the stream of results can be resumed by re-sending the original request and including `resume_token`. Note that executing any other transaction in the same session invalidates the token. */
		resumeToken: Option[String] = None,
	  /** Query plan and execution statistics for the statement that produced this streaming result set. These can be requested by setting ExecuteSqlRequest.query_mode and are sent only once with the last response in the stream. This field will also be present in the last response for DML statements. */
		stats: Option[Schema.ResultSetStats] = None,
	  /** Optional. A precommit token will be included if the read-write transaction is on a multiplexed session. The precommit token with the highest sequence number from this transaction attempt should be passed to the Commit request for this transaction. */
		precommitToken: Option[Schema.MultiplexedSessionPrecommitToken] = None
	)
	
	case class ExecuteBatchDmlRequest(
	  /** Required. The transaction to use. Must be a read-write transaction. To protect against replays, single-use transactions are not supported. The caller must either supply an existing transaction ID or begin a new transaction. */
		transaction: Option[Schema.TransactionSelector] = None,
	  /** Required. The list of statements to execute in this batch. Statements are executed serially, such that the effects of statement `i` are visible to statement `i+1`. Each statement must be a DML statement. Execution stops at the first failed statement; the remaining statements are not executed. Callers must provide at least one statement. */
		statements: Option[List[Schema.Statement]] = None,
	  /** Required. A per-transaction sequence number used to identify this request. This field makes each request idempotent such that if the request is received multiple times, at most one will succeed. The sequence number must be monotonically increasing within the transaction. If a request arrives for the first time with an out-of-order sequence number, the transaction may be aborted. Replays of previously handled requests will yield the same response as the first execution. */
		seqno: Option[String] = None,
	  /** Common options for this request. */
		requestOptions: Option[Schema.RequestOptions] = None
	)
	
	case class Statement(
	  /** Required. The DML string. */
		sql: Option[String] = None,
	  /** Parameter names and values that bind to placeholders in the DML string. A parameter placeholder consists of the `@` character followed by the parameter name (for example, `@firstName`). Parameter names can contain letters, numbers, and underscores. Parameters can appear anywhere that a literal value is expected. The same parameter name can be used more than once, for example: `"WHERE id > @msg_id AND id < @msg_id + 100"` It is an error to execute a SQL statement with unbound parameters. */
		params: Option[Map[String, JsValue]] = None,
	  /** It is not always possible for Cloud Spanner to infer the right SQL type from a JSON value. For example, values of type `BYTES` and values of type `STRING` both appear in params as JSON strings. In these cases, `param_types` can be used to specify the exact SQL type for some or all of the SQL statement parameters. See the definition of Type for more information about SQL types. */
		paramTypes: Option[Map[String, Schema.Type]] = None
	)
	
	case class ExecuteBatchDmlResponse(
	  /** One ResultSet for each statement in the request that ran successfully, in the same order as the statements in the request. Each ResultSet does not contain any rows. The ResultSetStats in each ResultSet contain the number of rows modified by the statement. Only the first ResultSet in the response contains valid ResultSetMetadata. */
		resultSets: Option[List[Schema.ResultSet]] = None,
	  /** If all DML statements are executed successfully, the status is `OK`. Otherwise, the error status of the first failed statement. */
		status: Option[Schema.Status] = None,
	  /** Optional. A precommit token will be included if the read-write transaction is on a multiplexed session. The precommit token with the highest sequence number from this transaction attempt should be passed to the Commit request for this transaction. */
		precommitToken: Option[Schema.MultiplexedSessionPrecommitToken] = None
	)
	
	object ReadRequest {
		enum OrderByEnum extends Enum[OrderByEnum] { case ORDER_BY_UNSPECIFIED, ORDER_BY_PRIMARY_KEY, ORDER_BY_NO_ORDER }
		enum LockHintEnum extends Enum[LockHintEnum] { case LOCK_HINT_UNSPECIFIED, LOCK_HINT_SHARED, LOCK_HINT_EXCLUSIVE }
	}
	case class ReadRequest(
	  /** The transaction to use. If none is provided, the default is a temporary read-only transaction with strong concurrency. */
		transaction: Option[Schema.TransactionSelector] = None,
	  /** Required. The name of the table in the database to be read. */
		table: Option[String] = None,
	  /** If non-empty, the name of an index on table. This index is used instead of the table primary key when interpreting key_set and sorting result rows. See key_set for further information. */
		index: Option[String] = None,
	  /** Required. The columns of table to be returned for each row matching this request. */
		columns: Option[List[String]] = None,
	  /** Required. `key_set` identifies the rows to be yielded. `key_set` names the primary keys of the rows in table to be yielded, unless index is present. If index is present, then key_set instead names index keys in index. If the partition_token field is empty, rows are yielded in table primary key order (if index is empty) or index key order (if index is non-empty). If the partition_token field is not empty, rows will be yielded in an unspecified order. It is not an error for the `key_set` to name rows that do not exist in the database. Read yields nothing for nonexistent rows. */
		keySet: Option[Schema.KeySet] = None,
	  /** If greater than zero, only the first `limit` rows are yielded. If `limit` is zero, the default is no limit. A limit cannot be specified if `partition_token` is set. */
		limit: Option[String] = None,
	  /** If this request is resuming a previously interrupted read, `resume_token` should be copied from the last PartialResultSet yielded before the interruption. Doing this enables the new read to resume where the last read left off. The rest of the request parameters must exactly match the request that yielded this token. */
		resumeToken: Option[String] = None,
	  /** If present, results will be restricted to the specified partition previously created using PartitionRead(). There must be an exact match for the values of fields common to this message and the PartitionReadRequest message used to create this partition_token. */
		partitionToken: Option[String] = None,
	  /** Common options for this request. */
		requestOptions: Option[Schema.RequestOptions] = None,
	  /** Directed read options for this request. */
		directedReadOptions: Option[Schema.DirectedReadOptions] = None,
	  /** If this is for a partitioned read and this field is set to `true`, the request is executed with Spanner Data Boost independent compute resources. If the field is set to `true` but the request does not set `partition_token`, the API returns an `INVALID_ARGUMENT` error. */
		dataBoostEnabled: Option[Boolean] = None,
	  /** Optional. Order for the returned rows. By default, Spanner will return result rows in primary key order except for PartitionRead requests. For applications that do not require rows to be returned in primary key (`ORDER_BY_PRIMARY_KEY`) order, setting `ORDER_BY_NO_ORDER` option allows Spanner to optimize row retrieval, resulting in lower latencies in certain cases (e.g. bulk point lookups). */
		orderBy: Option[Schema.ReadRequest.OrderByEnum] = None,
	  /** Optional. Lock Hint for the request, it can only be used with read-write transactions. */
		lockHint: Option[Schema.ReadRequest.LockHintEnum] = None
	)
	
	case class KeySet(
	  /** A list of specific keys. Entries in `keys` should have exactly as many elements as there are columns in the primary or index key with which this `KeySet` is used. Individual key values are encoded as described here. */
		keys: Option[List[List[JsValue]]] = None,
	  /** A list of key ranges. See KeyRange for more information about key range specifications. */
		ranges: Option[List[Schema.KeyRange]] = None,
	  /** For convenience `all` can be set to `true` to indicate that this `KeySet` matches all keys in the table or index. Note that any keys specified in `keys` or `ranges` are only yielded once. */
		all: Option[Boolean] = None
	)
	
	case class KeyRange(
	  /** If the start is closed, then the range includes all rows whose first `len(start_closed)` key columns exactly match `start_closed`. */
		startClosed: Option[List[JsValue]] = None,
	  /** If the start is open, then the range excludes rows whose first `len(start_open)` key columns exactly match `start_open`. */
		startOpen: Option[List[JsValue]] = None,
	  /** If the end is closed, then the range includes all rows whose first `len(end_closed)` key columns exactly match `end_closed`. */
		endClosed: Option[List[JsValue]] = None,
	  /** If the end is open, then the range excludes rows whose first `len(end_open)` key columns exactly match `end_open`. */
		endOpen: Option[List[JsValue]] = None
	)
	
	case class BeginTransactionRequest(
	  /** Required. Options for the new transaction. */
		options: Option[Schema.TransactionOptions] = None,
	  /** Common options for this request. Priority is ignored for this request. Setting the priority in this request_options struct will not do anything. To set the priority for a transaction, set it on the reads and writes that are part of this transaction instead. */
		requestOptions: Option[Schema.RequestOptions] = None,
	  /** Optional. Required for read-write transactions on a multiplexed session that commit mutations but do not perform any reads or queries. Clients should randomly select one of the mutations from the mutation set and send it as a part of this request. */
		mutationKey: Option[Schema.Mutation] = None
	)
	
	case class Mutation(
	  /** Insert new rows in a table. If any of the rows already exist, the write or transaction fails with error `ALREADY_EXISTS`. */
		insert: Option[Schema.Write] = None,
	  /** Update existing rows in a table. If any of the rows does not already exist, the transaction fails with error `NOT_FOUND`. */
		update: Option[Schema.Write] = None,
	  /** Like insert, except that if the row already exists, then its column values are overwritten with the ones provided. Any column values not explicitly written are preserved. When using insert_or_update, just as when using insert, all `NOT NULL` columns in the table must be given a value. This holds true even when the row already exists and will therefore actually be updated. */
		insertOrUpdate: Option[Schema.Write] = None,
	  /** Like insert, except that if the row already exists, it is deleted, and the column values provided are inserted instead. Unlike insert_or_update, this means any values not explicitly written become `NULL`. In an interleaved table, if you create the child table with the `ON DELETE CASCADE` annotation, then replacing a parent row also deletes the child rows. Otherwise, you must delete the child rows before you replace the parent row. */
		replace: Option[Schema.Write] = None,
	  /** Delete rows from a table. Succeeds whether or not the named rows were present. */
		delete: Option[Schema.Delete] = None
	)
	
	case class Write(
	  /** Required. The table whose rows will be written. */
		table: Option[String] = None,
	  /** The names of the columns in table to be written. The list of columns must contain enough columns to allow Cloud Spanner to derive values for all primary key columns in the row(s) to be modified. */
		columns: Option[List[String]] = None,
	  /** The values to be written. `values` can contain more than one list of values. If it does, then multiple rows are written, one for each entry in `values`. Each list in `values` must have exactly as many entries as there are entries in columns above. Sending multiple lists is equivalent to sending multiple `Mutation`s, each containing one `values` entry and repeating table and columns. Individual values in each list are encoded as described here. */
		values: Option[List[List[JsValue]]] = None
	)
	
	case class Delete(
	  /** Required. The table whose rows will be deleted. */
		table: Option[String] = None,
	  /** Required. The primary keys of the rows within table to delete. The primary keys must be specified in the order in which they appear in the `PRIMARY KEY()` clause of the table's equivalent DDL statement (the DDL statement used to create the table). Delete is idempotent. The transaction will succeed even if some or all rows do not exist. */
		keySet: Option[Schema.KeySet] = None
	)
	
	case class CommitRequest(
	  /** Commit a previously-started transaction. */
		transactionId: Option[String] = None,
	  /** Execute mutations in a temporary transaction. Note that unlike commit of a previously-started transaction, commit with a temporary transaction is non-idempotent. That is, if the `CommitRequest` is sent to Cloud Spanner more than once (for instance, due to retries in the application, or in the transport library), it is possible that the mutations are executed more than once. If this is undesirable, use BeginTransaction and Commit instead. */
		singleUseTransaction: Option[Schema.TransactionOptions] = None,
	  /** The mutations to be executed when this transaction commits. All mutations are applied atomically, in the order they appear in this list. */
		mutations: Option[List[Schema.Mutation]] = None,
	  /** If `true`, then statistics related to the transaction will be included in the CommitResponse. Default value is `false`. */
		returnCommitStats: Option[Boolean] = None,
	  /** Optional. The amount of latency this request is configured to incur in order to improve throughput. If this field is not set, Spanner assumes requests are relatively latency sensitive and automatically determines an appropriate delay time. You can specify a commit delay value between 0 and 500 ms. */
		maxCommitDelay: Option[String] = None,
	  /** Common options for this request. */
		requestOptions: Option[Schema.RequestOptions] = None,
	  /** Optional. If the read-write transaction was executed on a multiplexed session, the precommit token with the highest sequence number received in this transaction attempt, should be included here. Failing to do so will result in a FailedPrecondition error. */
		precommitToken: Option[Schema.MultiplexedSessionPrecommitToken] = None
	)
	
	case class CommitResponse(
	  /** The Cloud Spanner timestamp at which the transaction committed. */
		commitTimestamp: Option[String] = None,
	  /** The statistics about this Commit. Not returned by default. For more information, see CommitRequest.return_commit_stats. */
		commitStats: Option[Schema.CommitStats] = None,
	  /** If specified, transaction has not committed yet. Clients must retry the commit with the new precommit token. */
		precommitToken: Option[Schema.MultiplexedSessionPrecommitToken] = None
	)
	
	case class CommitStats(
	  /** The total number of mutations for the transaction. Knowing the `mutation_count` value can help you maximize the number of mutations in a transaction and minimize the number of API round trips. You can also monitor this value to prevent transactions from exceeding the system [limit](https://cloud.google.com/spanner/quotas#limits_for_creating_reading_updating_and_deleting_data). If the number of mutations exceeds the limit, the server returns [INVALID_ARGUMENT](https://cloud.google.com/spanner/docs/reference/rest/v1/Code#ENUM_VALUES.INVALID_ARGUMENT). */
		mutationCount: Option[String] = None
	)
	
	case class RollbackRequest(
	  /** Required. The transaction to roll back. */
		transactionId: Option[String] = None
	)
	
	case class PartitionQueryRequest(
	  /** Read only snapshot transactions are supported, read/write and single use transactions are not. */
		transaction: Option[Schema.TransactionSelector] = None,
	  /** Required. The query request to generate partitions for. The request fails if the query is not root partitionable. For a query to be root partitionable, it needs to satisfy a few conditions. For example, if the query execution plan contains a distributed union operator, then it must be the first operator in the plan. For more information about other conditions, see [Read data in parallel](https://cloud.google.com/spanner/docs/reads#read_data_in_parallel). The query request must not contain DML commands, such as `INSERT`, `UPDATE`, or `DELETE`. Use `ExecuteStreamingSql` with a PartitionedDml transaction for large, partition-friendly DML operations. */
		sql: Option[String] = None,
	  /** Parameter names and values that bind to placeholders in the SQL string. A parameter placeholder consists of the `@` character followed by the parameter name (for example, `@firstName`). Parameter names can contain letters, numbers, and underscores. Parameters can appear anywhere that a literal value is expected. The same parameter name can be used more than once, for example: `"WHERE id > @msg_id AND id < @msg_id + 100"` It is an error to execute a SQL statement with unbound parameters. */
		params: Option[Map[String, JsValue]] = None,
	  /** It is not always possible for Cloud Spanner to infer the right SQL type from a JSON value. For example, values of type `BYTES` and values of type `STRING` both appear in params as JSON strings. In these cases, `param_types` can be used to specify the exact SQL type for some or all of the SQL query parameters. See the definition of Type for more information about SQL types. */
		paramTypes: Option[Map[String, Schema.Type]] = None,
	  /** Additional options that affect how many partitions are created. */
		partitionOptions: Option[Schema.PartitionOptions] = None
	)
	
	case class PartitionOptions(
	  /** &#42;&#42;Note:&#42;&#42; This hint is currently ignored by PartitionQuery and PartitionRead requests. The desired data size for each partition generated. The default for this option is currently 1 GiB. This is only a hint. The actual size of each partition may be smaller or larger than this size request. */
		partitionSizeBytes: Option[String] = None,
	  /** &#42;&#42;Note:&#42;&#42; This hint is currently ignored by PartitionQuery and PartitionRead requests. The desired maximum number of partitions to return. For example, this may be set to the number of workers available. The default for this option is currently 10,000. The maximum value is currently 200,000. This is only a hint. The actual number of partitions returned may be smaller or larger than this maximum count request. */
		maxPartitions: Option[String] = None
	)
	
	case class PartitionResponse(
	  /** Partitions created by this request. */
		partitions: Option[List[Schema.Partition]] = None,
	  /** Transaction created by this request. */
		transaction: Option[Schema.Transaction] = None
	)
	
	case class Partition(
	  /** This token can be passed to Read, StreamingRead, ExecuteSql, or ExecuteStreamingSql requests to restrict the results to those identified by this partition token. */
		partitionToken: Option[String] = None
	)
	
	case class PartitionReadRequest(
	  /** Read only snapshot transactions are supported, read/write and single use transactions are not. */
		transaction: Option[Schema.TransactionSelector] = None,
	  /** Required. The name of the table in the database to be read. */
		table: Option[String] = None,
	  /** If non-empty, the name of an index on table. This index is used instead of the table primary key when interpreting key_set and sorting result rows. See key_set for further information. */
		index: Option[String] = None,
	  /** The columns of table to be returned for each row matching this request. */
		columns: Option[List[String]] = None,
	  /** Required. `key_set` identifies the rows to be yielded. `key_set` names the primary keys of the rows in table to be yielded, unless index is present. If index is present, then key_set instead names index keys in index. It is not an error for the `key_set` to name rows that do not exist in the database. Read yields nothing for nonexistent rows. */
		keySet: Option[Schema.KeySet] = None,
	  /** Additional options that affect how many partitions are created. */
		partitionOptions: Option[Schema.PartitionOptions] = None
	)
	
	case class BatchWriteRequest(
	  /** Common options for this request. */
		requestOptions: Option[Schema.RequestOptions] = None,
	  /** Required. The groups of mutations to be applied. */
		mutationGroups: Option[List[Schema.MutationGroup]] = None,
	  /** Optional. When `exclude_txn_from_change_streams` is set to `true`: &#42; Modifications from all transactions in this batch write operation will not be recorded in change streams with DDL option `allow_txn_exclusion=true` that are tracking columns modified by these transactions. &#42; Modifications from all transactions in this batch write operation will be recorded in change streams with DDL option `allow_txn_exclusion=false or not set` that are tracking columns modified by these transactions. When `exclude_txn_from_change_streams` is set to `false` or not set, Modifications from all transactions in this batch write operation will be recorded in all change streams that are tracking columns modified by these transactions. */
		excludeTxnFromChangeStreams: Option[Boolean] = None
	)
	
	case class MutationGroup(
	  /** Required. The mutations in this group. */
		mutations: Option[List[Schema.Mutation]] = None
	)
	
	case class BatchWriteResponse(
	  /** The mutation groups applied in this batch. The values index into the `mutation_groups` field in the corresponding `BatchWriteRequest`. */
		indexes: Option[List[Int]] = None,
	  /** An `OK` status indicates success. Any other status indicates a failure. */
		status: Option[Schema.Status] = None,
	  /** The commit timestamp of the transaction that applied this batch. Present if `status` is `OK`, absent otherwise. */
		commitTimestamp: Option[String] = None
	)
	
	case class CopyBackupMetadata(
	  /** The name of the backup being created through the copy operation. Values are of the form `projects//instances//backups/`. */
		name: Option[String] = None,
	  /** The name of the source backup that is being copied. Values are of the form `projects//instances//backups/`. */
		sourceBackup: Option[String] = None,
	  /** The progress of the CopyBackup operation. */
		progress: Option[Schema.OperationProgress] = None,
	  /** The time at which cancellation of CopyBackup operation was received. Operations.CancelOperation starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		cancelTime: Option[String] = None
	)
	
	case class OperationProgress(
	  /** Percent completion of the operation. Values are between 0 and 100 inclusive. */
		progressPercent: Option[Int] = None,
	  /** Time the request was received. */
		startTime: Option[String] = None,
	  /** If set, the time at which this operation failed or was completed successfully. */
		endTime: Option[String] = None
	)
	
	case class CreateBackupMetadata(
	  /** The name of the backup being created. */
		name: Option[String] = None,
	  /** The name of the database the backup is created from. */
		database: Option[String] = None,
	  /** The progress of the CreateBackup operation. */
		progress: Option[Schema.OperationProgress] = None,
	  /** The time at which cancellation of this operation was received. Operations.CancelOperation starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		cancelTime: Option[String] = None
	)
	
	case class CreateDatabaseMetadata(
	  /** The database being created. */
		database: Option[String] = None
	)
	
	object CreateInstanceMetadata {
		enum ExpectedFulfillmentPeriodEnum extends Enum[ExpectedFulfillmentPeriodEnum] { case FULFILLMENT_PERIOD_UNSPECIFIED, FULFILLMENT_PERIOD_NORMAL, FULFILLMENT_PERIOD_EXTENDED }
	}
	case class CreateInstanceMetadata(
	  /** The instance being created. */
		instance: Option[Schema.Instance] = None,
	  /** The time at which the CreateInstance request was received. */
		startTime: Option[String] = None,
	  /** The time at which this operation was cancelled. If set, this operation is in the process of undoing itself (which is guaranteed to succeed) and cannot be cancelled again. */
		cancelTime: Option[String] = None,
	  /** The time at which this operation failed or was completed successfully. */
		endTime: Option[String] = None,
	  /** The expected fulfillment period of this create operation. */
		expectedFulfillmentPeriod: Option[Schema.CreateInstanceMetadata.ExpectedFulfillmentPeriodEnum] = None
	)
	
	case class CreateInstanceConfigMetadata(
	  /** The target instance configuration end state. */
		instanceConfig: Option[Schema.InstanceConfig] = None,
	  /** The progress of the CreateInstanceConfig operation. */
		progress: Option[Schema.InstanceOperationProgress] = None,
	  /** The time at which this operation was cancelled. */
		cancelTime: Option[String] = None
	)
	
	case class InstanceOperationProgress(
	  /** Percent completion of the operation. Values are between 0 and 100 inclusive. */
		progressPercent: Option[Int] = None,
	  /** Time the request was received. */
		startTime: Option[String] = None,
	  /** If set, the time at which this operation failed or was completed successfully. */
		endTime: Option[String] = None
	)
	
	case class CreateInstancePartitionMetadata(
	  /** The instance partition being created. */
		instancePartition: Option[Schema.InstancePartition] = None,
	  /** The time at which the CreateInstancePartition request was received. */
		startTime: Option[String] = None,
	  /** The time at which this operation was cancelled. If set, this operation is in the process of undoing itself (which is guaranteed to succeed) and cannot be cancelled again. */
		cancelTime: Option[String] = None,
	  /** The time at which this operation failed or was completed successfully. */
		endTime: Option[String] = None
	)
	
	case class OptimizeRestoredDatabaseMetadata(
	  /** Name of the restored database being optimized. */
		name: Option[String] = None,
	  /** The progress of the post-restore optimizations. */
		progress: Option[Schema.OperationProgress] = None
	)
	
	case class ChangeQuorumMetadata(
	  /** The request for ChangeQuorum. */
		request: Option[Schema.ChangeQuorumRequest] = None,
	  /** Time the request was received. */
		startTime: Option[String] = None,
	  /** If set, the time at which this operation failed or was completed successfully. */
		endTime: Option[String] = None
	)
	
	object RestoreDatabaseMetadata {
		enum SourceTypeEnum extends Enum[SourceTypeEnum] { case TYPE_UNSPECIFIED, BACKUP }
	}
	case class RestoreDatabaseMetadata(
	  /** Name of the database being created and restored to. */
		name: Option[String] = None,
	  /** The type of the restore source. */
		sourceType: Option[Schema.RestoreDatabaseMetadata.SourceTypeEnum] = None,
	  /** Information about the backup used to restore the database. */
		backupInfo: Option[Schema.BackupInfo] = None,
	  /** The progress of the RestoreDatabase operation. */
		progress: Option[Schema.OperationProgress] = None,
	  /** The time at which cancellation of this operation was received. Operations.CancelOperation starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		cancelTime: Option[String] = None,
	  /** If exists, the name of the long-running operation that will be used to track the post-restore optimization process to optimize the performance of the restored database, and remove the dependency on the restore source. The name is of the form `projects//instances//databases//operations/` where the is the name of database being created and restored to. The metadata type of the long-running operation is OptimizeRestoredDatabaseMetadata. This long-running operation will be automatically created by the system after the RestoreDatabase long-running operation completes successfully. This operation will not be created if the restore was not successful. */
		optimizeDatabaseOperationName: Option[String] = None
	)
	
	case class UpdateDatabaseDdlMetadata(
	  /** The database being modified. */
		database: Option[String] = None,
	  /** For an update this list contains all the statements. For an individual statement, this list contains only that statement. */
		statements: Option[List[String]] = None,
	  /** Reports the commit timestamps of all statements that have succeeded so far, where `commit_timestamps[i]` is the commit timestamp for the statement `statements[i]`. */
		commitTimestamps: Option[List[String]] = None,
	  /** Output only. When true, indicates that the operation is throttled e.g. due to resource constraints. When resources become available the operation will resume and this field will be false again. */
		throttled: Option[Boolean] = None,
	  /** The progress of the UpdateDatabaseDdl operations. All DDL statements will have continuously updating progress, and `progress[i]` is the operation progress for `statements[i]`. Also, `progress[i]` will have start time and end time populated with commit timestamp of operation, as well as a progress of 100% once the operation has completed. */
		progress: Option[List[Schema.OperationProgress]] = None,
	  /** The brief action info for the DDL statements. `actions[i]` is the brief info for `statements[i]`. */
		actions: Option[List[Schema.DdlStatementActionInfo]] = None
	)
	
	case class DdlStatementActionInfo(
	  /** The action for the DDL statement, e.g. CREATE, ALTER, DROP, GRANT, etc. This field is a non-empty string. */
		action: Option[String] = None,
	  /** The entity type for the DDL statement, e.g. TABLE, INDEX, VIEW, etc. This field can be empty string for some DDL statement, e.g. for statement "ANALYZE", `entity_type` = "". */
		entityType: Option[String] = None,
	  /** The entity name(s) being operated on the DDL statement. E.g. 1. For statement "CREATE TABLE t1(...)", `entity_names` = ["t1"]. 2. For statement "GRANT ROLE r1, r2 ...", `entity_names` = ["r1", "r2"]. 3. For statement "ANALYZE", `entity_names` = []. */
		entityNames: Option[List[String]] = None
	)
	
	case class UpdateDatabaseMetadata(
	  /** The request for UpdateDatabase. */
		request: Option[Schema.UpdateDatabaseRequest] = None,
	  /** The progress of the UpdateDatabase operation. */
		progress: Option[Schema.OperationProgress] = None,
	  /** The time at which this operation was cancelled. If set, this operation is in the process of undoing itself (which is best-effort). */
		cancelTime: Option[String] = None
	)
	
	case class UpdateDatabaseRequest(
	  /** Required. The database to update. The `name` field of the database is of the form `projects//instances//databases/`. */
		database: Option[Schema.Database] = None,
	  /** Required. The list of fields to update. Currently, only `enable_drop_protection` field can be updated. */
		updateMask: Option[String] = None
	)
	
	object UpdateInstanceMetadata {
		enum ExpectedFulfillmentPeriodEnum extends Enum[ExpectedFulfillmentPeriodEnum] { case FULFILLMENT_PERIOD_UNSPECIFIED, FULFILLMENT_PERIOD_NORMAL, FULFILLMENT_PERIOD_EXTENDED }
	}
	case class UpdateInstanceMetadata(
	  /** The desired end state of the update. */
		instance: Option[Schema.Instance] = None,
	  /** The time at which UpdateInstance request was received. */
		startTime: Option[String] = None,
	  /** The time at which this operation was cancelled. If set, this operation is in the process of undoing itself (which is guaranteed to succeed) and cannot be cancelled again. */
		cancelTime: Option[String] = None,
	  /** The time at which this operation failed or was completed successfully. */
		endTime: Option[String] = None,
	  /** The expected fulfillment period of this update operation. */
		expectedFulfillmentPeriod: Option[Schema.UpdateInstanceMetadata.ExpectedFulfillmentPeriodEnum] = None
	)
	
	case class UpdateInstanceConfigMetadata(
	  /** The desired instance configuration after updating. */
		instanceConfig: Option[Schema.InstanceConfig] = None,
	  /** The progress of the UpdateInstanceConfig operation. */
		progress: Option[Schema.InstanceOperationProgress] = None,
	  /** The time at which this operation was cancelled. */
		cancelTime: Option[String] = None
	)
	
	case class UpdateInstancePartitionMetadata(
	  /** The desired end state of the update. */
		instancePartition: Option[Schema.InstancePartition] = None,
	  /** The time at which UpdateInstancePartition request was received. */
		startTime: Option[String] = None,
	  /** The time at which this operation was cancelled. If set, this operation is in the process of undoing itself (which is guaranteed to succeed) and cannot be cancelled again. */
		cancelTime: Option[String] = None,
	  /** The time at which this operation failed or was completed successfully. */
		endTime: Option[String] = None
	)
}
