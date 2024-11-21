package com.boresjo.play.api.google.firestore

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
	
	case class GoogleLongrunningListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.GoogleLongrunningOperation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleLongrunningOperation(
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
	
	case class GoogleLongrunningCancelOperationRequest(
	
	)
	
	object GoogleFirestoreAdminV1Index {
		enum QueryScopeEnum extends Enum[QueryScopeEnum] { case QUERY_SCOPE_UNSPECIFIED, COLLECTION, COLLECTION_GROUP, COLLECTION_RECURSIVE }
		enum ApiScopeEnum extends Enum[ApiScopeEnum] { case ANY_API, DATASTORE_MODE_API }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY, NEEDS_REPAIR }
	}
	case class GoogleFirestoreAdminV1Index(
	  /** Output only. A server defined name for this index. The form of this name for composite indexes will be: `projects/{project_id}/databases/{database_id}/collectionGroups/{collection_id}/indexes/{composite_index_id}` For single field indexes, this field will be empty. */
		name: Option[String] = None,
	  /** Indexes with a collection query scope specified allow queries against a collection that is the child of a specific document, specified at query time, and that has the same collection ID. Indexes with a collection group query scope specified allow queries against all collections descended from a specific document, specified at query time, and that have the same collection ID as this index. */
		queryScope: Option[Schema.GoogleFirestoreAdminV1Index.QueryScopeEnum] = None,
	  /** The API scope supported by this index. */
		apiScope: Option[Schema.GoogleFirestoreAdminV1Index.ApiScopeEnum] = None,
	  /** The fields supported by this index. For composite indexes, this requires a minimum of 2 and a maximum of 100 fields. The last field entry is always for the field path `__name__`. If, on creation, `__name__` was not specified as the last field, it will be added automatically with the same direction as that of the last field defined. If the final field in a composite index is not directional, the `__name__` will be ordered ASCENDING (unless explicitly specified). For single field indexes, this will always be exactly one entry with a field path equal to the field path of the associated field. */
		fields: Option[List[Schema.GoogleFirestoreAdminV1IndexField]] = None,
	  /** Output only. The serving state of the index. */
		state: Option[Schema.GoogleFirestoreAdminV1Index.StateEnum] = None
	)
	
	object GoogleFirestoreAdminV1IndexField {
		enum OrderEnum extends Enum[OrderEnum] { case ORDER_UNSPECIFIED, ASCENDING, DESCENDING }
		enum ArrayConfigEnum extends Enum[ArrayConfigEnum] { case ARRAY_CONFIG_UNSPECIFIED, CONTAINS }
	}
	case class GoogleFirestoreAdminV1IndexField(
	  /** Can be __name__. For single field indexes, this must match the name of the field or may be omitted. */
		fieldPath: Option[String] = None,
	  /** Indicates that this field supports ordering by the specified order or comparing using =, !=, <, <=, >, >=. */
		order: Option[Schema.GoogleFirestoreAdminV1IndexField.OrderEnum] = None,
	  /** Indicates that this field supports operations on `array_value`s. */
		arrayConfig: Option[Schema.GoogleFirestoreAdminV1IndexField.ArrayConfigEnum] = None,
	  /** Indicates that this field supports nearest neighbor and distance operations on vector. */
		vectorConfig: Option[Schema.GoogleFirestoreAdminV1VectorConfig] = None
	)
	
	case class GoogleFirestoreAdminV1VectorConfig(
	  /** Required. The vector dimension this configuration applies to. The resulting index will only include vectors of this dimension, and can be used for vector search with the same dimension. */
		dimension: Option[Int] = None,
	  /** Indicates the vector index is a flat index. */
		flat: Option[Schema.GoogleFirestoreAdminV1FlatIndex] = None
	)
	
	case class GoogleFirestoreAdminV1FlatIndex(
	
	)
	
	case class GoogleFirestoreAdminV1ListIndexesResponse(
	  /** The requested indexes. */
		indexes: Option[List[Schema.GoogleFirestoreAdminV1Index]] = None,
	  /** A page token that may be used to request another page of results. If blank, this is the last page. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleFirestoreAdminV1Field(
	  /** Required. A field name of the form: `projects/{project_id}/databases/{database_id}/collectionGroups/{collection_id}/fields/{field_path}` A field path can be a simple field name, e.g. `address` or a path to fields within `map_value` , e.g. `address.city`, or a special field path. The only valid special field is `&#42;`, which represents any field. Field paths can be quoted using `` ` `` (backtick). The only character that must be escaped within a quoted field path is the backtick character itself, escaped using a backslash. Special characters in field paths that must be quoted include: `&#42;`, `.`, `` ` `` (backtick), `[`, `]`, as well as any ascii symbolic characters. Examples: `` `address.city` `` represents a field named `address.city`, not the map key `city` in the field `address`. `` `&#42;` `` represents a field named `&#42;`, not any field. A special `Field` contains the default indexing settings for all fields. This field's resource name is: `projects/{project_id}/databases/{database_id}/collectionGroups/__default__/fields/&#42;` Indexes defined on this `Field` will be applied to all fields which do not have their own `Field` index configuration. */
		name: Option[String] = None,
	  /** The index configuration for this field. If unset, field indexing will revert to the configuration defined by the `ancestor_field`. To explicitly remove all indexes for this field, specify an index config with an empty list of indexes. */
		indexConfig: Option[Schema.GoogleFirestoreAdminV1IndexConfig] = None,
	  /** The TTL configuration for this `Field`. Setting or unsetting this will enable or disable the TTL for documents that have this `Field`. */
		ttlConfig: Option[Schema.GoogleFirestoreAdminV1TtlConfig] = None
	)
	
	case class GoogleFirestoreAdminV1IndexConfig(
	  /** The indexes supported for this field. */
		indexes: Option[List[Schema.GoogleFirestoreAdminV1Index]] = None,
	  /** Output only. When true, the `Field`'s index configuration is set from the configuration specified by the `ancestor_field`. When false, the `Field`'s index configuration is defined explicitly. */
		usesAncestorConfig: Option[Boolean] = None,
	  /** Output only. Specifies the resource name of the `Field` from which this field's index configuration is set (when `uses_ancestor_config` is true), or from which it &#42;would&#42; be set if this field had no index configuration (when `uses_ancestor_config` is false). */
		ancestorField: Option[String] = None,
	  /** Output only When true, the `Field`'s index configuration is in the process of being reverted. Once complete, the index config will transition to the same state as the field specified by `ancestor_field`, at which point `uses_ancestor_config` will be `true` and `reverting` will be `false`. */
		reverting: Option[Boolean] = None
	)
	
	object GoogleFirestoreAdminV1TtlConfig {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, NEEDS_REPAIR }
	}
	case class GoogleFirestoreAdminV1TtlConfig(
	  /** Output only. The state of the TTL configuration. */
		state: Option[Schema.GoogleFirestoreAdminV1TtlConfig.StateEnum] = None
	)
	
	case class GoogleFirestoreAdminV1ListFieldsResponse(
	  /** The requested fields. */
		fields: Option[List[Schema.GoogleFirestoreAdminV1Field]] = None,
	  /** A page token that may be used to request another page of results. If blank, this is the last page. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleFirestoreAdminV1ExportDocumentsRequest(
	  /** Which collection IDs to export. Unspecified means all collections. Each collection ID in this list must be unique. */
		collectionIds: Option[List[String]] = None,
	  /** The output URI. Currently only supports Google Cloud Storage URIs of the form: `gs://BUCKET_NAME[/NAMESPACE_PATH]`, where `BUCKET_NAME` is the name of the Google Cloud Storage bucket and `NAMESPACE_PATH` is an optional Google Cloud Storage namespace path. When choosing a name, be sure to consider Google Cloud Storage naming guidelines: https://cloud.google.com/storage/docs/naming. If the URI is a bucket (without a namespace path), a prefix will be generated based on the start time. */
		outputUriPrefix: Option[String] = None,
	  /** An empty list represents all namespaces. This is the preferred usage for databases that don't use namespaces. An empty string element represents the default namespace. This should be used if the database has data in non-default namespaces, but doesn't want to include them. Each namespace in this list must be unique. */
		namespaceIds: Option[List[String]] = None,
	  /** The timestamp that corresponds to the version of the database to be exported. The timestamp must be in the past, rounded to the minute and not older than earliestVersionTime. If specified, then the exported documents will represent a consistent view of the database at the provided time. Otherwise, there are no guarantees about the consistency of the exported documents. */
		snapshotTime: Option[String] = None
	)
	
	case class GoogleFirestoreAdminV1ImportDocumentsRequest(
	  /** Which collection IDs to import. Unspecified means all collections included in the import. Each collection ID in this list must be unique. */
		collectionIds: Option[List[String]] = None,
	  /** Location of the exported files. This must match the output_uri_prefix of an ExportDocumentsResponse from an export that has completed successfully. See: google.firestore.admin.v1.ExportDocumentsResponse.output_uri_prefix. */
		inputUriPrefix: Option[String] = None,
	  /** An empty list represents all namespaces. This is the preferred usage for databases that don't use namespaces. An empty string element represents the default namespace. This should be used if the database has data in non-default namespaces, but doesn't want to include them. Each namespace in this list must be unique. */
		namespaceIds: Option[List[String]] = None
	)
	
	case class GoogleFirestoreAdminV1BulkDeleteDocumentsRequest(
	  /** Optional. IDs of the collection groups to delete. Unspecified means all collection groups. Each collection group in this list must be unique. */
		collectionIds: Option[List[String]] = None,
	  /** Optional. Namespaces to delete. An empty list means all namespaces. This is the recommended usage for databases that don't use namespaces. An empty string element represents the default namespace. This should be used if the database has data in non-default namespaces, but doesn't want to delete from them. Each namespace in this list must be unique. */
		namespaceIds: Option[List[String]] = None
	)
	
	object GoogleFirestoreAdminV1Database {
		enum TypeEnum extends Enum[TypeEnum] { case DATABASE_TYPE_UNSPECIFIED, FIRESTORE_NATIVE, DATASTORE_MODE }
		enum ConcurrencyModeEnum extends Enum[ConcurrencyModeEnum] { case CONCURRENCY_MODE_UNSPECIFIED, OPTIMISTIC, PESSIMISTIC, OPTIMISTIC_WITH_ENTITY_GROUPS }
		enum PointInTimeRecoveryEnablementEnum extends Enum[PointInTimeRecoveryEnablementEnum] { case POINT_IN_TIME_RECOVERY_ENABLEMENT_UNSPECIFIED, POINT_IN_TIME_RECOVERY_ENABLED, POINT_IN_TIME_RECOVERY_DISABLED }
		enum AppEngineIntegrationModeEnum extends Enum[AppEngineIntegrationModeEnum] { case APP_ENGINE_INTEGRATION_MODE_UNSPECIFIED, ENABLED, DISABLED }
		enum DeleteProtectionStateEnum extends Enum[DeleteProtectionStateEnum] { case DELETE_PROTECTION_STATE_UNSPECIFIED, DELETE_PROTECTION_DISABLED, DELETE_PROTECTION_ENABLED }
	}
	case class GoogleFirestoreAdminV1Database(
	  /** The resource name of the Database. Format: `projects/{project}/databases/{database}` */
		name: Option[String] = None,
	  /** Output only. The system-generated UUID4 for this Database. */
		uid: Option[String] = None,
	  /** Output only. The timestamp at which this database was created. Databases created before 2016 do not populate create_time. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp at which this database was most recently updated. Note this only includes updates to the database resource and not data contained by the database. */
		updateTime: Option[String] = None,
	  /** Output only. The timestamp at which this database was deleted. Only set if the database has been deleted. */
		deleteTime: Option[String] = None,
	  /** The location of the database. Available locations are listed at https://cloud.google.com/firestore/docs/locations. */
		locationId: Option[String] = None,
	  /** The type of the database. See https://cloud.google.com/datastore/docs/firestore-or-datastore for information about how to choose. */
		`type`: Option[Schema.GoogleFirestoreAdminV1Database.TypeEnum] = None,
	  /** The concurrency control mode to use for this database. */
		concurrencyMode: Option[Schema.GoogleFirestoreAdminV1Database.ConcurrencyModeEnum] = None,
	  /** Output only. The period during which past versions of data are retained in the database. Any read or query can specify a `read_time` within this window, and will read the state of the database at that time. If the PITR feature is enabled, the retention period is 7 days. Otherwise, the retention period is 1 hour. */
		versionRetentionPeriod: Option[String] = None,
	  /** Output only. The earliest timestamp at which older versions of the data can be read from the database. See [version_retention_period] above; this field is populated with `now - version_retention_period`. This value is continuously updated, and becomes stale the moment it is queried. If you are using this value to recover data, make sure to account for the time from the moment when the value is queried to the moment when you initiate the recovery. */
		earliestVersionTime: Option[String] = None,
	  /** Whether to enable the PITR feature on this database. */
		pointInTimeRecoveryEnablement: Option[Schema.GoogleFirestoreAdminV1Database.PointInTimeRecoveryEnablementEnum] = None,
	  /** The App Engine integration mode to use for this database. */
		appEngineIntegrationMode: Option[Schema.GoogleFirestoreAdminV1Database.AppEngineIntegrationModeEnum] = None,
	  /** Output only. The key_prefix for this database. This key_prefix is used, in combination with the project ID ("~") to construct the application ID that is returned from the Cloud Datastore APIs in Google App Engine first generation runtimes. This value may be empty in which case the appid to use for URL-encoded keys is the project_id (eg: foo instead of v~foo). */
		keyPrefix: Option[String] = None,
	  /** State of delete protection for the database. */
		deleteProtectionState: Option[Schema.GoogleFirestoreAdminV1Database.DeleteProtectionStateEnum] = None,
	  /** Optional. Presence indicates CMEK is enabled for this database. */
		cmekConfig: Option[Schema.GoogleFirestoreAdminV1CmekConfig] = None,
	  /** Output only. The database resource's prior database ID. This field is only populated for deleted databases. */
		previousId: Option[String] = None,
	  /** Output only. Information about the provenance of this database. */
		sourceInfo: Option[Schema.GoogleFirestoreAdminV1SourceInfo] = None,
	  /** This checksum is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None
	)
	
	case class GoogleFirestoreAdminV1CmekConfig(
	  /** Required. Only keys in the same location as this database are allowed to be used for encryption. For Firestore's nam5 multi-region, this corresponds to Cloud KMS multi-region us. For Firestore's eur3 multi-region, this corresponds to Cloud KMS multi-region europe. See https://cloud.google.com/kms/docs/locations. The expected format is `projects/{project_id}/locations/{kms_location}/keyRings/{key_ring}/cryptoKeys/{crypto_key}`. */
		kmsKeyName: Option[String] = None,
	  /** Output only. Currently in-use [KMS key versions](https://cloud.google.com/kms/docs/resource-hierarchy#key_versions). During [key rotation](https://cloud.google.com/kms/docs/key-rotation), there can be multiple in-use key versions. The expected format is `projects/{project_id}/locations/{kms_location}/keyRings/{key_ring}/cryptoKeys/{crypto_key}/cryptoKeyVersions/{key_version}`. */
		activeKeyVersion: Option[List[String]] = None
	)
	
	case class GoogleFirestoreAdminV1SourceInfo(
	  /** If set, this database was restored from the specified backup (or a snapshot thereof). */
		backup: Option[Schema.GoogleFirestoreAdminV1BackupSource] = None,
	  /** The associated long-running operation. This field may not be set after the operation has completed. Format: `projects/{project}/databases/{database}/operations/{operation}`. */
		operation: Option[String] = None
	)
	
	case class GoogleFirestoreAdminV1BackupSource(
	  /** The resource name of the backup that was used to restore this database. Format: `projects/{project}/locations/{location}/backups/{backup}`. */
		backup: Option[String] = None
	)
	
	case class GoogleFirestoreAdminV1ListDatabasesResponse(
	  /** The databases in the project. */
		databases: Option[List[Schema.GoogleFirestoreAdminV1Database]] = None,
	  /** In the event that data about individual databases cannot be listed they will be recorded here. An example entry might be: projects/some_project/locations/some_location This can happen if the Cloud Region that the Database resides in is currently unavailable. In this case we can't fetch all the details about the database. You may be able to get a more detailed error message (or possibly fetch the resource) by sending a 'Get' request for the resource or a 'List' request for the specific location. */
		unreachable: Option[List[String]] = None
	)
	
	object GoogleFirestoreAdminV1Backup {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY, NOT_AVAILABLE }
	}
	case class GoogleFirestoreAdminV1Backup(
	  /** Output only. The unique resource name of the Backup. Format is `projects/{project}/locations/{location}/backups/{backup}`. */
		name: Option[String] = None,
	  /** Output only. Name of the Firestore database that the backup is from. Format is `projects/{project}/databases/{database}`. */
		database: Option[String] = None,
	  /** Output only. The system-generated UUID4 for the Firestore database that the backup is from. */
		databaseUid: Option[String] = None,
	  /** Output only. The backup contains an externally consistent copy of the database at this time. */
		snapshotTime: Option[String] = None,
	  /** Output only. The timestamp at which this backup expires. */
		expireTime: Option[String] = None,
	  /** Output only. Statistics about the backup. This data only becomes available after the backup is fully materialized to secondary storage. This field will be empty till then. */
		stats: Option[Schema.GoogleFirestoreAdminV1Stats] = None,
	  /** Output only. The current state of the backup. */
		state: Option[Schema.GoogleFirestoreAdminV1Backup.StateEnum] = None
	)
	
	case class GoogleFirestoreAdminV1Stats(
	  /** Output only. Summation of the size of all documents and index entries in the backup, measured in bytes. */
		sizeBytes: Option[String] = None,
	  /** Output only. The total number of documents contained in the backup. */
		documentCount: Option[String] = None,
	  /** Output only. The total number of index entries contained in the backup. */
		indexCount: Option[String] = None
	)
	
	case class GoogleFirestoreAdminV1ListBackupsResponse(
	  /** List of all backups for the project. */
		backups: Option[List[Schema.GoogleFirestoreAdminV1Backup]] = None,
	  /** List of locations that existing backups were not able to be fetched from. Instead of failing the entire requests when a single location is unreachable, this response returns a partial result set and list of locations unable to be reached here. The request can be retried against a single location to get a concrete error. */
		unreachable: Option[List[String]] = None
	)
	
	case class GoogleFirestoreAdminV1RestoreDatabaseRequest(
	  /** Required. The ID to use for the database, which will become the final component of the database's resource name. This database ID must not be associated with an existing database. This value should be 4-63 characters. Valid characters are /a-z-/ with first character a letter and the last a letter or a number. Must not be UUID-like /[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}/. "(default)" database ID is also valid. */
		databaseId: Option[String] = None,
	  /** Required. Backup to restore from. Must be from the same project as the parent. The restored database will be created in the same location as the source backup. Format is: `projects/{project_id}/locations/{location}/backups/{backup}` */
		backup: Option[String] = None,
	  /** Optional. Encryption configuration for the restored database. If this field is not specified, the restored database will use the same encryption configuration as the backup, namely use_source_encryption. */
		encryptionConfig: Option[Schema.GoogleFirestoreAdminV1EncryptionConfig] = None
	)
	
	case class GoogleFirestoreAdminV1EncryptionConfig(
	  /** Use Google default encryption. */
		googleDefaultEncryption: Option[Schema.GoogleFirestoreAdminV1GoogleDefaultEncryptionOptions] = None,
	  /** The database will use the same encryption configuration as the source. */
		useSourceEncryption: Option[Schema.GoogleFirestoreAdminV1SourceEncryptionOptions] = None,
	  /** Use Customer Managed Encryption Keys (CMEK) for encryption. */
		customerManagedEncryption: Option[Schema.GoogleFirestoreAdminV1CustomerManagedEncryptionOptions] = None
	)
	
	case class GoogleFirestoreAdminV1GoogleDefaultEncryptionOptions(
	
	)
	
	case class GoogleFirestoreAdminV1SourceEncryptionOptions(
	
	)
	
	case class GoogleFirestoreAdminV1CustomerManagedEncryptionOptions(
	  /** Required. Only keys in the same location as the database are allowed to be used for encryption. For Firestore's nam5 multi-region, this corresponds to Cloud KMS multi-region us. For Firestore's eur3 multi-region, this corresponds to Cloud KMS multi-region europe. See https://cloud.google.com/kms/docs/locations. The expected format is `projects/{project_id}/locations/{kms_location}/keyRings/{key_ring}/cryptoKeys/{crypto_key}`. */
		kmsKeyName: Option[String] = None
	)
	
	case class GoogleFirestoreAdminV1BackupSchedule(
	  /** Output only. The unique backup schedule identifier across all locations and databases for the given project. This will be auto-assigned. Format is `projects/{project}/databases/{database}/backupSchedules/{backup_schedule}` */
		name: Option[String] = None,
	  /** Output only. The timestamp at which this backup schedule was created and effective since. No backups will be created for this schedule before this time. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp at which this backup schedule was most recently updated. When a backup schedule is first created, this is the same as create_time. */
		updateTime: Option[String] = None,
	  /** At what relative time in the future, compared to its creation time, the backup should be deleted, e.g. keep backups for 7 days. The maximum supported retention period is 14 weeks. */
		retention: Option[String] = None,
	  /** For a schedule that runs daily. */
		dailyRecurrence: Option[Schema.GoogleFirestoreAdminV1DailyRecurrence] = None,
	  /** For a schedule that runs weekly on a specific day. */
		weeklyRecurrence: Option[Schema.GoogleFirestoreAdminV1WeeklyRecurrence] = None
	)
	
	case class GoogleFirestoreAdminV1DailyRecurrence(
	
	)
	
	object GoogleFirestoreAdminV1WeeklyRecurrence {
		enum DayEnum extends Enum[DayEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class GoogleFirestoreAdminV1WeeklyRecurrence(
	  /** The day of week to run. DAY_OF_WEEK_UNSPECIFIED is not allowed. */
		day: Option[Schema.GoogleFirestoreAdminV1WeeklyRecurrence.DayEnum] = None
	)
	
	case class GoogleFirestoreAdminV1ListBackupSchedulesResponse(
	  /** List of all backup schedules. */
		backupSchedules: Option[List[Schema.GoogleFirestoreAdminV1BackupSchedule]] = None
	)
	
	case class Document(
	  /** The resource name of the document, for example `projects/{project_id}/databases/{database_id}/documents/{document_path}`. */
		name: Option[String] = None,
	  /** The document's fields. The map keys represent field names. Field names matching the regular expression `__.&#42;__` are reserved. Reserved field names are forbidden except in certain documented contexts. The field names, represented as UTF-8, must not exceed 1,500 bytes and cannot be empty. Field paths may be used in other contexts to refer to structured fields defined here. For `map_value`, the field path is represented by a dot-delimited (`.`) string of segments. Each segment is either a simple field name (defined below) or a quoted field name. For example, the structured field `"foo" : { map_value: { "x&y" : { string_value: "hello" }}}` would be represented by the field path `` foo.`x&y` ``. A simple field name contains only characters `a` to `z`, `A` to `Z`, `0` to `9`, or `_`, and must not start with `0` to `9`. For example, `foo_bar_17`. A quoted field name starts and ends with `` ` `` and may contain any character. Some characters, including `` ` ``, must be escaped using a `\`. For example, `` `x&y` `` represents `x&y` and `` `bak\`tik` `` represents `` bak`tik ``. */
		fields: Option[Map[String, Schema.Value]] = None,
	  /** Output only. The time at which the document was created. This value increases monotonically when a document is deleted then recreated. It can also be compared to values from other documents and the `read_time` of a query. */
		createTime: Option[String] = None,
	  /** Output only. The time at which the document was last changed. This value is initially set to the `create_time` then increases monotonically with each change to the document. It can also be compared to values from other documents and the `read_time` of a query. */
		updateTime: Option[String] = None
	)
	
	object Value {
		enum NullValueEnum extends Enum[NullValueEnum] { case NULL_VALUE }
	}
	case class Value(
	  /** A null value. */
		nullValue: Option[Schema.Value.NullValueEnum] = None,
	  /** A boolean value. */
		booleanValue: Option[Boolean] = None,
	  /** An integer value. */
		integerValue: Option[String] = None,
	  /** A double value. */
		doubleValue: Option[BigDecimal] = None,
	  /** A timestamp value. Precise only to microseconds. When stored, any additional precision is rounded down. */
		timestampValue: Option[String] = None,
	  /** A string value. The string, represented as UTF-8, must not exceed 1 MiB - 89 bytes. Only the first 1,500 bytes of the UTF-8 representation are considered by queries. */
		stringValue: Option[String] = None,
	  /** A bytes value. Must not exceed 1 MiB - 89 bytes. Only the first 1,500 bytes are considered by queries. */
		bytesValue: Option[String] = None,
	  /** A reference to a document. For example: `projects/{project_id}/databases/{database_id}/documents/{document_path}`. */
		referenceValue: Option[String] = None,
	  /** A geo point value representing a point on the surface of Earth. */
		geoPointValue: Option[Schema.LatLng] = None,
	  /** An array value. Cannot directly contain another array value, though can contain a map which contains another array. */
		arrayValue: Option[Schema.ArrayValue] = None,
	  /** A map value. */
		mapValue: Option[Schema.MapValue] = None
	)
	
	case class LatLng(
	  /** The latitude in degrees. It must be in the range [-90.0, +90.0]. */
		latitude: Option[BigDecimal] = None,
	  /** The longitude in degrees. It must be in the range [-180.0, +180.0]. */
		longitude: Option[BigDecimal] = None
	)
	
	case class ArrayValue(
	  /** Values in the array. */
		values: Option[List[Schema.Value]] = None
	)
	
	case class MapValue(
	  /** The map's fields. The map keys represent field names. Field names matching the regular expression `__.&#42;__` are reserved. Reserved field names are forbidden except in certain documented contexts. The map keys, represented as UTF-8, must not exceed 1,500 bytes and cannot be empty. */
		fields: Option[Map[String, Schema.Value]] = None
	)
	
	case class ListDocumentsResponse(
	  /** The Documents found. */
		documents: Option[List[Schema.Document]] = None,
	  /** A token to retrieve the next page of documents. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class BatchGetDocumentsRequest(
	  /** The names of the documents to retrieve. In the format: `projects/{project_id}/databases/{database_id}/documents/{document_path}`. The request will fail if any of the document is not a child resource of the given `database`. Duplicate names will be elided. */
		documents: Option[List[String]] = None,
	  /** The fields to return. If not set, returns all fields. If a document has a field that is not present in this mask, that field will not be returned in the response. */
		mask: Option[Schema.DocumentMask] = None,
	  /** Reads documents in a transaction. */
		transaction: Option[String] = None,
	  /** Starts a new transaction and reads the documents. Defaults to a read-only transaction. The new transaction ID will be returned as the first response in the stream. */
		newTransaction: Option[Schema.TransactionOptions] = None,
	  /** Reads documents as they were at the given time. This must be a microsecond precision timestamp within the past one hour, or if Point-in-Time Recovery is enabled, can additionally be a whole minute timestamp within the past 7 days. */
		readTime: Option[String] = None
	)
	
	case class DocumentMask(
	  /** The list of field paths in the mask. See Document.fields for a field path syntax reference. */
		fieldPaths: Option[List[String]] = None
	)
	
	case class TransactionOptions(
	  /** The transaction can only be used for read operations. */
		readOnly: Option[Schema.ReadOnly] = None,
	  /** The transaction can be used for both read and write operations. */
		readWrite: Option[Schema.ReadWrite] = None
	)
	
	case class ReadOnly(
	  /** Reads documents at the given time. This must be a microsecond precision timestamp within the past one hour, or if Point-in-Time Recovery is enabled, can additionally be a whole minute timestamp within the past 7 days. */
		readTime: Option[String] = None
	)
	
	case class ReadWrite(
	  /** An optional transaction to retry. */
		retryTransaction: Option[String] = None
	)
	
	case class BatchGetDocumentsResponse(
	  /** A document that was requested. */
		found: Option[Schema.Document] = None,
	  /** A document name that was requested but does not exist. In the format: `projects/{project_id}/databases/{database_id}/documents/{document_path}`. */
		missing: Option[String] = None,
	  /** The transaction that was started as part of this request. Will only be set in the first response, and only if BatchGetDocumentsRequest.new_transaction was set in the request. */
		transaction: Option[String] = None,
	  /** The time at which the document was read. This may be monotically increasing, in this case the previous documents in the result stream are guaranteed not to have changed between their read_time and this one. */
		readTime: Option[String] = None
	)
	
	case class BeginTransactionRequest(
	  /** The options for the transaction. Defaults to a read-write transaction. */
		options: Option[Schema.TransactionOptions] = None
	)
	
	case class BeginTransactionResponse(
	  /** The transaction that was started. */
		transaction: Option[String] = None
	)
	
	case class CommitRequest(
	  /** The writes to apply. Always executed atomically and in order. */
		writes: Option[List[Schema.Write]] = None,
	  /** If set, applies all writes in this transaction, and commits it. */
		transaction: Option[String] = None
	)
	
	case class Write(
	  /** A document to write. */
		update: Option[Schema.Document] = None,
	  /** A document name to delete. In the format: `projects/{project_id}/databases/{database_id}/documents/{document_path}`. */
		delete: Option[String] = None,
	  /** Applies a transformation to a document. */
		transform: Option[Schema.DocumentTransform] = None,
	  /** The fields to update in this write. This field can be set only when the operation is `update`. If the mask is not set for an `update` and the document exists, any existing data will be overwritten. If the mask is set and the document on the server has fields not covered by the mask, they are left unchanged. Fields referenced in the mask, but not present in the input document, are deleted from the document on the server. The field paths in this mask must not contain a reserved field name. */
		updateMask: Option[Schema.DocumentMask] = None,
	  /** The transforms to perform after update. This field can be set only when the operation is `update`. If present, this write is equivalent to performing `update` and `transform` to the same document atomically and in order. */
		updateTransforms: Option[List[Schema.FieldTransform]] = None,
	  /** An optional precondition on the document. The write will fail if this is set and not met by the target document. */
		currentDocument: Option[Schema.Precondition] = None
	)
	
	case class DocumentTransform(
	  /** The name of the document to transform. */
		document: Option[String] = None,
	  /** The list of transformations to apply to the fields of the document, in order. This must not be empty. */
		fieldTransforms: Option[List[Schema.FieldTransform]] = None
	)
	
	object FieldTransform {
		enum SetToServerValueEnum extends Enum[SetToServerValueEnum] { case SERVER_VALUE_UNSPECIFIED, REQUEST_TIME }
	}
	case class FieldTransform(
	  /** The path of the field. See Document.fields for the field path syntax reference. */
		fieldPath: Option[String] = None,
	  /** Sets the field to the given server value. */
		setToServerValue: Option[Schema.FieldTransform.SetToServerValueEnum] = None,
	  /** Adds the given value to the field's current value. This must be an integer or a double value. If the field is not an integer or double, or if the field does not yet exist, the transformation will set the field to the given value. If either of the given value or the current field value are doubles, both values will be interpreted as doubles. Double arithmetic and representation of double values follow IEEE 754 semantics. If there is positive/negative integer overflow, the field is resolved to the largest magnitude positive/negative integer. */
		increment: Option[Schema.Value] = None,
	  /** Sets the field to the maximum of its current value and the given value. This must be an integer or a double value. If the field is not an integer or double, or if the field does not yet exist, the transformation will set the field to the given value. If a maximum operation is applied where the field and the input value are of mixed types (that is - one is an integer and one is a double) the field takes on the type of the larger operand. If the operands are equivalent (e.g. 3 and 3.0), the field does not change. 0, 0.0, and -0.0 are all zero. The maximum of a zero stored value and zero input value is always the stored value. The maximum of any numeric value x and NaN is NaN. */
		maximum: Option[Schema.Value] = None,
	  /** Sets the field to the minimum of its current value and the given value. This must be an integer or a double value. If the field is not an integer or double, or if the field does not yet exist, the transformation will set the field to the input value. If a minimum operation is applied where the field and the input value are of mixed types (that is - one is an integer and one is a double) the field takes on the type of the smaller operand. If the operands are equivalent (e.g. 3 and 3.0), the field does not change. 0, 0.0, and -0.0 are all zero. The minimum of a zero stored value and zero input value is always the stored value. The minimum of any numeric value x and NaN is NaN. */
		minimum: Option[Schema.Value] = None,
	  /** Append the given elements in order if they are not already present in the current field value. If the field is not an array, or if the field does not yet exist, it is first set to the empty array. Equivalent numbers of different types (e.g. 3L and 3.0) are considered equal when checking if a value is missing. NaN is equal to NaN, and Null is equal to Null. If the input contains multiple equivalent values, only the first will be considered. The corresponding transform_result will be the null value. */
		appendMissingElements: Option[Schema.ArrayValue] = None,
	  /** Remove all of the given elements from the array in the field. If the field is not an array, or if the field does not yet exist, it is set to the empty array. Equivalent numbers of the different types (e.g. 3L and 3.0) are considered equal when deciding whether an element should be removed. NaN is equal to NaN, and Null is equal to Null. This will remove all equivalent values if there are duplicates. The corresponding transform_result will be the null value. */
		removeAllFromArray: Option[Schema.ArrayValue] = None
	)
	
	case class Precondition(
	  /** When set to `true`, the target document must exist. When set to `false`, the target document must not exist. */
		exists: Option[Boolean] = None,
	  /** When set, the target document must exist and have been last updated at that time. Timestamp must be microsecond aligned. */
		updateTime: Option[String] = None
	)
	
	case class CommitResponse(
	  /** The result of applying the writes. This i-th write result corresponds to the i-th write in the request. */
		writeResults: Option[List[Schema.WriteResult]] = None,
	  /** The time at which the commit occurred. Any read with an equal or greater `read_time` is guaranteed to see the effects of the commit. */
		commitTime: Option[String] = None
	)
	
	case class WriteResult(
	  /** The last update time of the document after applying the write. Not set after a `delete`. If the write did not actually change the document, this will be the previous update_time. */
		updateTime: Option[String] = None,
	  /** The results of applying each DocumentTransform.FieldTransform, in the same order. */
		transformResults: Option[List[Schema.Value]] = None
	)
	
	case class RollbackRequest(
	  /** Required. The transaction to roll back. */
		transaction: Option[String] = None
	)
	
	case class RunQueryRequest(
	  /** A structured query. */
		structuredQuery: Option[Schema.StructuredQuery] = None,
	  /** Run the query within an already active transaction. The value here is the opaque transaction ID to execute the query in. */
		transaction: Option[String] = None,
	  /** Starts a new transaction and reads the documents. Defaults to a read-only transaction. The new transaction ID will be returned as the first response in the stream. */
		newTransaction: Option[Schema.TransactionOptions] = None,
	  /** Reads documents as they were at the given time. This must be a microsecond precision timestamp within the past one hour, or if Point-in-Time Recovery is enabled, can additionally be a whole minute timestamp within the past 7 days. */
		readTime: Option[String] = None,
	  /** Optional. Explain options for the query. If set, additional query statistics will be returned. If not, only query results will be returned. */
		explainOptions: Option[Schema.ExplainOptions] = None
	)
	
	case class StructuredQuery(
	  /** Optional sub-set of the fields to return. This acts as a DocumentMask over the documents returned from a query. When not set, assumes that the caller wants all fields returned. */
		select: Option[Schema.Projection] = None,
	  /** The collections to query. */
		from: Option[List[Schema.CollectionSelector]] = None,
	  /** The filter to apply. */
		where: Option[Schema.Filter] = None,
	  /** The order to apply to the query results. Firestore allows callers to provide a full ordering, a partial ordering, or no ordering at all. In all cases, Firestore guarantees a stable ordering through the following rules: &#42; The `order_by` is required to reference all fields used with an inequality filter. &#42; All fields that are required to be in the `order_by` but are not already present are appended in lexicographical ordering of the field name. &#42; If an order on `__name__` is not specified, it is appended by default. Fields are appended with the same sort direction as the last order specified, or 'ASCENDING' if no order was specified. For example: &#42; `ORDER BY a` becomes `ORDER BY a ASC, __name__ ASC` &#42; `ORDER BY a DESC` becomes `ORDER BY a DESC, __name__ DESC` &#42; `WHERE a > 1` becomes `WHERE a > 1 ORDER BY a ASC, __name__ ASC` &#42; `WHERE __name__ > ... AND a > 1` becomes `WHERE __name__ > ... AND a > 1 ORDER BY a ASC, __name__ ASC` */
		orderBy: Option[List[Schema.Order]] = None,
	  /** A potential prefix of a position in the result set to start the query at. The ordering of the result set is based on the `ORDER BY` clause of the original query. ``` SELECT &#42; FROM k WHERE a = 1 AND b > 2 ORDER BY b ASC, __name__ ASC; ``` This query's results are ordered by `(b ASC, __name__ ASC)`. Cursors can reference either the full ordering or a prefix of the location, though it cannot reference more fields than what are in the provided `ORDER BY`. Continuing off the example above, attaching the following start cursors will have varying impact: - `START BEFORE (2, /k/123)`: start the query right before `a = 1 AND b > 2 AND __name__ > /k/123`. - `START AFTER (10)`: start the query right after `a = 1 AND b > 10`. Unlike `OFFSET` which requires scanning over the first N results to skip, a start cursor allows the query to begin at a logical position. This position is not required to match an actual result, it will scan forward from this position to find the next document. Requires: &#42; The number of values cannot be greater than the number of fields specified in the `ORDER BY` clause. */
		startAt: Option[Schema.Cursor] = None,
	  /** A potential prefix of a position in the result set to end the query at. This is similar to `START_AT` but with it controlling the end position rather than the start position. Requires: &#42; The number of values cannot be greater than the number of fields specified in the `ORDER BY` clause. */
		endAt: Option[Schema.Cursor] = None,
	  /** The number of documents to skip before returning the first result. This applies after the constraints specified by the `WHERE`, `START AT`, & `END AT` but before the `LIMIT` clause. Requires: &#42; The value must be greater than or equal to zero if specified. */
		offset: Option[Int] = None,
	  /** The maximum number of results to return. Applies after all other constraints. Requires: &#42; The value must be greater than or equal to zero if specified. */
		limit: Option[Int] = None,
	  /** Optional. A potential nearest neighbors search. Applies after all other filters and ordering. Finds the closest vector embeddings to the given query vector. */
		findNearest: Option[Schema.FindNearest] = None
	)
	
	case class Projection(
	  /** The fields to return. If empty, all fields are returned. To only return the name of the document, use `['__name__']`. */
		fields: Option[List[Schema.FieldReference]] = None
	)
	
	case class FieldReference(
	  /** A reference to a field in a document. Requires: &#42; MUST be a dot-delimited (`.`) string of segments, where each segment conforms to document field name limitations. */
		fieldPath: Option[String] = None
	)
	
	case class CollectionSelector(
	  /** The collection ID. When set, selects only collections with this ID. */
		collectionId: Option[String] = None,
	  /** When false, selects only collections that are immediate children of the `parent` specified in the containing `RunQueryRequest`. When true, selects all descendant collections. */
		allDescendants: Option[Boolean] = None
	)
	
	case class Filter(
	  /** A composite filter. */
		compositeFilter: Option[Schema.CompositeFilter] = None,
	  /** A filter on a document field. */
		fieldFilter: Option[Schema.FieldFilter] = None,
	  /** A filter that takes exactly one argument. */
		unaryFilter: Option[Schema.UnaryFilter] = None
	)
	
	object CompositeFilter {
		enum OpEnum extends Enum[OpEnum] { case OPERATOR_UNSPECIFIED, AND, OR }
	}
	case class CompositeFilter(
	  /** The operator for combining multiple filters. */
		op: Option[Schema.CompositeFilter.OpEnum] = None,
	  /** The list of filters to combine. Requires: &#42; At least one filter is present. */
		filters: Option[List[Schema.Filter]] = None
	)
	
	object FieldFilter {
		enum OpEnum extends Enum[OpEnum] { case OPERATOR_UNSPECIFIED, LESS_THAN, LESS_THAN_OR_EQUAL, GREATER_THAN, GREATER_THAN_OR_EQUAL, EQUAL, NOT_EQUAL, ARRAY_CONTAINS, IN, ARRAY_CONTAINS_ANY, NOT_IN }
	}
	case class FieldFilter(
	  /** The field to filter by. */
		field: Option[Schema.FieldReference] = None,
	  /** The operator to filter by. */
		op: Option[Schema.FieldFilter.OpEnum] = None,
	  /** The value to compare to. */
		value: Option[Schema.Value] = None
	)
	
	object UnaryFilter {
		enum OpEnum extends Enum[OpEnum] { case OPERATOR_UNSPECIFIED, IS_NAN, IS_NULL, IS_NOT_NAN, IS_NOT_NULL }
	}
	case class UnaryFilter(
	  /** The unary operator to apply. */
		op: Option[Schema.UnaryFilter.OpEnum] = None,
	  /** The field to which to apply the operator. */
		field: Option[Schema.FieldReference] = None
	)
	
	object Order {
		enum DirectionEnum extends Enum[DirectionEnum] { case DIRECTION_UNSPECIFIED, ASCENDING, DESCENDING }
	}
	case class Order(
	  /** The field to order by. */
		field: Option[Schema.FieldReference] = None,
	  /** The direction to order by. Defaults to `ASCENDING`. */
		direction: Option[Schema.Order.DirectionEnum] = None
	)
	
	case class Cursor(
	  /** The values that represent a position, in the order they appear in the order by clause of a query. Can contain fewer values than specified in the order by clause. */
		values: Option[List[Schema.Value]] = None,
	  /** If the position is just before or just after the given values, relative to the sort order defined by the query. */
		before: Option[Boolean] = None
	)
	
	object FindNearest {
		enum DistanceMeasureEnum extends Enum[DistanceMeasureEnum] { case DISTANCE_MEASURE_UNSPECIFIED, EUCLIDEAN, COSINE, DOT_PRODUCT }
	}
	case class FindNearest(
	  /** Required. An indexed vector field to search upon. Only documents which contain vectors whose dimensionality match the query_vector can be returned. */
		vectorField: Option[Schema.FieldReference] = None,
	  /** Required. The query vector that we are searching on. Must be a vector of no more than 2048 dimensions. */
		queryVector: Option[Schema.Value] = None,
	  /** Required. The distance measure to use, required. */
		distanceMeasure: Option[Schema.FindNearest.DistanceMeasureEnum] = None,
	  /** Required. The number of nearest neighbors to return. Must be a positive integer of no more than 1000. */
		limit: Option[Int] = None,
	  /** Optional. Optional name of the field to output the result of the vector distance calculation. Must conform to document field name limitations. */
		distanceResultField: Option[String] = None,
	  /** Optional. Option to specify a threshold for which no less similar documents will be returned. The behavior of the specified `distance_measure` will affect the meaning of the distance threshold. Since DOT_PRODUCT distances increase when the vectors are more similar, the comparison is inverted. &#42; For EUCLIDEAN, COSINE: WHERE distance <= distance_threshold &#42; For DOT_PRODUCT: WHERE distance >= distance_threshold */
		distanceThreshold: Option[BigDecimal] = None
	)
	
	case class ExplainOptions(
	  /** Optional. Whether to execute this query. When false (the default), the query will be planned, returning only metrics from the planning stages. When true, the query will be planned and executed, returning the full query results along with both planning and execution stage metrics. */
		analyze: Option[Boolean] = None
	)
	
	case class RunQueryResponse(
	  /** The transaction that was started as part of this request. Can only be set in the first response, and only if RunQueryRequest.new_transaction was set in the request. If set, no other fields will be set in this response. */
		transaction: Option[String] = None,
	  /** A query result, not set when reporting partial progress. */
		document: Option[Schema.Document] = None,
	  /** The time at which the document was read. This may be monotonically increasing; in this case, the previous documents in the result stream are guaranteed not to have changed between their `read_time` and this one. If the query returns no results, a response with `read_time` and no `document` will be sent, and this represents the time at which the query was run. */
		readTime: Option[String] = None,
	  /** The number of results that have been skipped due to an offset between the last response and the current response. */
		skippedResults: Option[Int] = None,
	  /** If present, Firestore has completely finished the request and no more documents will be returned. */
		done: Option[Boolean] = None,
	  /** Query explain metrics. This is only present when the RunQueryRequest.explain_options is provided, and it is sent only once with the last response in the stream. */
		explainMetrics: Option[Schema.ExplainMetrics] = None
	)
	
	case class ExplainMetrics(
	  /** Planning phase information for the query. */
		planSummary: Option[Schema.PlanSummary] = None,
	  /** Aggregated stats from the execution of the query. Only present when ExplainOptions.analyze is set to true. */
		executionStats: Option[Schema.ExecutionStats] = None
	)
	
	case class PlanSummary(
	  /** The indexes selected for the query. For example: [ {"query_scope": "Collection", "properties": "(foo ASC, __name__ ASC)"}, {"query_scope": "Collection", "properties": "(bar ASC, __name__ ASC)"} ] */
		indexesUsed: Option[List[Map[String, JsValue]]] = None
	)
	
	case class ExecutionStats(
	  /** Total number of results returned, including documents, projections, aggregation results, keys. */
		resultsReturned: Option[String] = None,
	  /** Total time to execute the query in the backend. */
		executionDuration: Option[String] = None,
	  /** Total billable read operations. */
		readOperations: Option[String] = None,
	  /** Debugging statistics from the execution of the query. Note that the debugging stats are subject to change as Firestore evolves. It could include: { "indexes_entries_scanned": "1000", "documents_scanned": "20", "billing_details" : { "documents_billable": "20", "index_entries_billable": "1000", "min_query_cost": "0" } } */
		debugStats: Option[Map[String, JsValue]] = None
	)
	
	case class RunAggregationQueryRequest(
	  /** An aggregation query. */
		structuredAggregationQuery: Option[Schema.StructuredAggregationQuery] = None,
	  /** Run the aggregation within an already active transaction. The value here is the opaque transaction ID to execute the query in. */
		transaction: Option[String] = None,
	  /** Starts a new transaction as part of the query, defaulting to read-only. The new transaction ID will be returned as the first response in the stream. */
		newTransaction: Option[Schema.TransactionOptions] = None,
	  /** Executes the query at the given timestamp. This must be a microsecond precision timestamp within the past one hour, or if Point-in-Time Recovery is enabled, can additionally be a whole minute timestamp within the past 7 days. */
		readTime: Option[String] = None,
	  /** Optional. Explain options for the query. If set, additional query statistics will be returned. If not, only query results will be returned. */
		explainOptions: Option[Schema.ExplainOptions] = None
	)
	
	case class StructuredAggregationQuery(
	  /** Nested structured query. */
		structuredQuery: Option[Schema.StructuredQuery] = None,
	  /** Optional. Series of aggregations to apply over the results of the `structured_query`. Requires: &#42; A minimum of one and maximum of five aggregations per query. */
		aggregations: Option[List[Schema.Aggregation]] = None
	)
	
	case class Aggregation(
	  /** Count aggregator. */
		count: Option[Schema.Count] = None,
	  /** Sum aggregator. */
		sum: Option[Schema.Sum] = None,
	  /** Average aggregator. */
		avg: Option[Schema.Avg] = None,
	  /** Optional. Optional name of the field to store the result of the aggregation into. If not provided, Firestore will pick a default name following the format `field_`. For example: ``` AGGREGATE COUNT_UP_TO(1) AS count_up_to_1, COUNT_UP_TO(2), COUNT_UP_TO(3) AS count_up_to_3, COUNT(&#42;) OVER ( ... ); ``` becomes: ``` AGGREGATE COUNT_UP_TO(1) AS count_up_to_1, COUNT_UP_TO(2) AS field_1, COUNT_UP_TO(3) AS count_up_to_3, COUNT(&#42;) AS field_2 OVER ( ... ); ``` Requires: &#42; Must be unique across all aggregation aliases. &#42; Conform to document field name limitations. */
		alias: Option[String] = None
	)
	
	case class Count(
	  /** Optional. Optional constraint on the maximum number of documents to count. This provides a way to set an upper bound on the number of documents to scan, limiting latency, and cost. Unspecified is interpreted as no bound. High-Level Example: ``` AGGREGATE COUNT_UP_TO(1000) OVER ( SELECT &#42; FROM k ); ``` Requires: &#42; Must be greater than zero when present. */
		upTo: Option[String] = None
	)
	
	case class Sum(
	  /** The field to aggregate on. */
		field: Option[Schema.FieldReference] = None
	)
	
	case class Avg(
	  /** The field to aggregate on. */
		field: Option[Schema.FieldReference] = None
	)
	
	case class RunAggregationQueryResponse(
	  /** A single aggregation result. Not present when reporting partial progress. */
		result: Option[Schema.AggregationResult] = None,
	  /** The transaction that was started as part of this request. Only present on the first response when the request requested to start a new transaction. */
		transaction: Option[String] = None,
	  /** The time at which the aggregate result was computed. This is always monotonically increasing; in this case, the previous AggregationResult in the result stream are guaranteed not to have changed between their `read_time` and this one. If the query returns no results, a response with `read_time` and no `result` will be sent, and this represents the time at which the query was run. */
		readTime: Option[String] = None,
	  /** Query explain metrics. This is only present when the RunAggregationQueryRequest.explain_options is provided, and it is sent only once with the last response in the stream. */
		explainMetrics: Option[Schema.ExplainMetrics] = None
	)
	
	case class AggregationResult(
	  /** The result of the aggregation functions, ex: `COUNT(&#42;) AS total_docs`. The key is the alias assigned to the aggregation function on input and the size of this map equals the number of aggregation functions in the query. */
		aggregateFields: Option[Map[String, Schema.Value]] = None
	)
	
	case class PartitionQueryRequest(
	  /** A structured query. Query must specify collection with all descendants and be ordered by name ascending. Other filters, order bys, limits, offsets, and start/end cursors are not supported. */
		structuredQuery: Option[Schema.StructuredQuery] = None,
	  /** The desired maximum number of partition points. The partitions may be returned across multiple pages of results. The number must be positive. The actual number of partitions returned may be fewer. For example, this may be set to one fewer than the number of parallel queries to be run, or in running a data pipeline job, one fewer than the number of workers or compute instances available. */
		partitionCount: Option[String] = None,
	  /** The `next_page_token` value returned from a previous call to PartitionQuery that may be used to get an additional set of results. There are no ordering guarantees between sets of results. Thus, using multiple sets of results will require merging the different result sets. For example, two subsequent calls using a page_token may return: &#42; cursor B, cursor M, cursor Q &#42; cursor A, cursor U, cursor W To obtain a complete result set ordered with respect to the results of the query supplied to PartitionQuery, the results sets should be merged: cursor A, cursor B, cursor M, cursor Q, cursor U, cursor W */
		pageToken: Option[String] = None,
	  /** The maximum number of partitions to return in this call, subject to `partition_count`. For example, if `partition_count` = 10 and `page_size` = 8, the first call to PartitionQuery will return up to 8 partitions and a `next_page_token` if more results exist. A second call to PartitionQuery will return up to 2 partitions, to complete the total of 10 specified in `partition_count`. */
		pageSize: Option[Int] = None,
	  /** Reads documents as they were at the given time. This must be a microsecond precision timestamp within the past one hour, or if Point-in-Time Recovery is enabled, can additionally be a whole minute timestamp within the past 7 days. */
		readTime: Option[String] = None
	)
	
	case class PartitionQueryResponse(
	  /** Partition results. Each partition is a split point that can be used by RunQuery as a starting or end point for the query results. The RunQuery requests must be made with the same query supplied to this PartitionQuery request. The partition cursors will be ordered according to same ordering as the results of the query supplied to PartitionQuery. For example, if a PartitionQuery request returns partition cursors A and B, running the following three queries will return the entire result set of the original query: &#42; query, end_at A &#42; query, start_at A, end_at B &#42; query, start_at B An empty result may indicate that the query has too few results to be partitioned, or that the query is not yet supported for partitioning. */
		partitions: Option[List[Schema.Cursor]] = None,
	  /** A page token that may be used to request an additional set of results, up to the number specified by `partition_count` in the PartitionQuery request. If blank, there are no more results. */
		nextPageToken: Option[String] = None
	)
	
	case class WriteRequest(
	  /** The ID of the write stream to resume. This may only be set in the first message. When left empty, a new write stream will be created. */
		streamId: Option[String] = None,
	  /** The writes to apply. Always executed atomically and in order. This must be empty on the first request. This may be empty on the last request. This must not be empty on all other requests. */
		writes: Option[List[Schema.Write]] = None,
	  /** A stream token that was previously sent by the server. The client should set this field to the token from the most recent WriteResponse it has received. This acknowledges that the client has received responses up to this token. After sending this token, earlier tokens may not be used anymore. The server may close the stream if there are too many unacknowledged responses. Leave this field unset when creating a new stream. To resume a stream at a specific point, set this field and the `stream_id` field. Leave this field unset when creating a new stream. */
		streamToken: Option[String] = None,
	  /** Labels associated with this write request. */
		labels: Option[Map[String, String]] = None
	)
	
	case class WriteResponse(
	  /** The ID of the stream. Only set on the first message, when a new stream was created. */
		streamId: Option[String] = None,
	  /** A token that represents the position of this response in the stream. This can be used by a client to resume the stream at this point. This field is always set. */
		streamToken: Option[String] = None,
	  /** The result of applying the writes. This i-th write result corresponds to the i-th write in the request. */
		writeResults: Option[List[Schema.WriteResult]] = None,
	  /** The time at which the commit occurred. Any read with an equal or greater `read_time` is guaranteed to see the effects of the write. */
		commitTime: Option[String] = None
	)
	
	case class ListenRequest(
	  /** A target to add to this stream. */
		addTarget: Option[Schema.Target] = None,
	  /** The ID of a target to remove from this stream. */
		removeTarget: Option[Int] = None,
	  /** Labels associated with this target change. */
		labels: Option[Map[String, String]] = None
	)
	
	case class Target(
	  /** A target specified by a query. */
		query: Option[Schema.QueryTarget] = None,
	  /** A target specified by a set of document names. */
		documents: Option[Schema.DocumentsTarget] = None,
	  /** A resume token from a prior TargetChange for an identical target. Using a resume token with a different target is unsupported and may fail. */
		resumeToken: Option[String] = None,
	  /** Start listening after a specific `read_time`. The client must know the state of matching documents at this time. */
		readTime: Option[String] = None,
	  /** The target ID that identifies the target on the stream. Must be a positive number and non-zero. If `target_id` is 0 (or unspecified), the server will assign an ID for this target and return that in a `TargetChange::ADD` event. Once a target with `target_id=0` is added, all subsequent targets must also have `target_id=0`. If an `AddTarget` request with `target_id != 0` is sent to the server after a target with `target_id=0` is added, the server will immediately send a response with a `TargetChange::Remove` event. Note that if the client sends multiple `AddTarget` requests without an ID, the order of IDs returned in `TargetChage.target_ids` are undefined. Therefore, clients should provide a target ID instead of relying on the server to assign one. If `target_id` is non-zero, there must not be an existing active target on this stream with the same ID. */
		targetId: Option[Int] = None,
	  /** If the target should be removed once it is current and consistent. */
		once: Option[Boolean] = None,
	  /** The number of documents that last matched the query at the resume token or read time. This value is only relevant when a `resume_type` is provided. This value being present and greater than zero signals that the client wants `ExistenceFilter.unchanged_names` to be included in the response. */
		expectedCount: Option[Int] = None
	)
	
	case class QueryTarget(
	  /** The parent resource name. In the format: `projects/{project_id}/databases/{database_id}/documents` or `projects/{project_id}/databases/{database_id}/documents/{document_path}`. For example: `projects/my-project/databases/my-database/documents` or `projects/my-project/databases/my-database/documents/chatrooms/my-chatroom` */
		parent: Option[String] = None,
	  /** A structured query. */
		structuredQuery: Option[Schema.StructuredQuery] = None
	)
	
	case class DocumentsTarget(
	  /** The names of the documents to retrieve. In the format: `projects/{project_id}/databases/{database_id}/documents/{document_path}`. The request will fail if any of the document is not a child resource of the given `database`. Duplicate names will be elided. */
		documents: Option[List[String]] = None
	)
	
	case class ListenResponse(
	  /** Targets have changed. */
		targetChange: Option[Schema.TargetChange] = None,
	  /** A Document has changed. */
		documentChange: Option[Schema.DocumentChange] = None,
	  /** A Document has been deleted. */
		documentDelete: Option[Schema.DocumentDelete] = None,
	  /** A Document has been removed from a target (because it is no longer relevant to that target). */
		documentRemove: Option[Schema.DocumentRemove] = None,
	  /** A filter to apply to the set of documents previously returned for the given target. Returned when documents may have been removed from the given target, but the exact documents are unknown. */
		filter: Option[Schema.ExistenceFilter] = None
	)
	
	object TargetChange {
		enum TargetChangeTypeEnum extends Enum[TargetChangeTypeEnum] { case NO_CHANGE, ADD, REMOVE, CURRENT, RESET }
	}
	case class TargetChange(
	  /** The type of change that occurred. */
		targetChangeType: Option[Schema.TargetChange.TargetChangeTypeEnum] = None,
	  /** The target IDs of targets that have changed. If empty, the change applies to all targets. The order of the target IDs is not defined. */
		targetIds: Option[List[Int]] = None,
	  /** The error that resulted in this change, if applicable. */
		cause: Option[Schema.Status] = None,
	  /** A token that can be used to resume the stream for the given `target_ids`, or all targets if `target_ids` is empty. Not set on every target change. */
		resumeToken: Option[String] = None,
	  /** The consistent `read_time` for the given `target_ids` (omitted when the target_ids are not at a consistent snapshot). The stream is guaranteed to send a `read_time` with `target_ids` empty whenever the entire stream reaches a new consistent snapshot. ADD, CURRENT, and RESET messages are guaranteed to (eventually) result in a new consistent snapshot (while NO_CHANGE and REMOVE messages are not). For a given stream, `read_time` is guaranteed to be monotonically increasing. */
		readTime: Option[String] = None
	)
	
	case class DocumentChange(
	  /** The new state of the Document. If `mask` is set, contains only fields that were updated or added. */
		document: Option[Schema.Document] = None,
	  /** A set of target IDs of targets that match this document. */
		targetIds: Option[List[Int]] = None,
	  /** A set of target IDs for targets that no longer match this document. */
		removedTargetIds: Option[List[Int]] = None
	)
	
	case class DocumentDelete(
	  /** The resource name of the Document that was deleted. */
		document: Option[String] = None,
	  /** A set of target IDs for targets that previously matched this entity. */
		removedTargetIds: Option[List[Int]] = None,
	  /** The read timestamp at which the delete was observed. Greater or equal to the `commit_time` of the delete. */
		readTime: Option[String] = None
	)
	
	case class DocumentRemove(
	  /** The resource name of the Document that has gone out of view. */
		document: Option[String] = None,
	  /** A set of target IDs for targets that previously matched this document. */
		removedTargetIds: Option[List[Int]] = None,
	  /** The read timestamp at which the remove was observed. Greater or equal to the `commit_time` of the change/delete/remove. */
		readTime: Option[String] = None
	)
	
	case class ExistenceFilter(
	  /** The target ID to which this filter applies. */
		targetId: Option[Int] = None,
	  /** The total count of documents that match target_id. If different from the count of documents in the client that match, the client must manually determine which documents no longer match the target. The client can use the `unchanged_names` bloom filter to assist with this determination by testing ALL the document names against the filter; if the document name is NOT in the filter, it means the document no longer matches the target. */
		count: Option[Int] = None,
	  /** A bloom filter that, despite its name, contains the UTF-8 byte encodings of the resource names of ALL the documents that match target_id, in the form `projects/{project_id}/databases/{database_id}/documents/{document_path}`. This bloom filter may be omitted at the server's discretion, such as if it is deemed that the client will not make use of it or if it is too computationally expensive to calculate or transmit. Clients must gracefully handle this field being absent by falling back to the logic used before this field existed; that is, re-add the target without a resume token to figure out which documents in the client's cache are out of sync. */
		unchangedNames: Option[Schema.BloomFilter] = None
	)
	
	case class BloomFilter(
	  /** The bloom filter data. */
		bits: Option[Schema.BitSequence] = None,
	  /** The number of hashes used by the algorithm. */
		hashCount: Option[Int] = None
	)
	
	case class BitSequence(
	  /** The bytes that encode the bit sequence. May have a length of zero. */
		bitmap: Option[String] = None,
	  /** The number of bits of the last byte in `bitmap` to ignore as "padding". If the length of `bitmap` is zero, then this value must be `0`. Otherwise, this value must be between 0 and 7, inclusive. */
		padding: Option[Int] = None
	)
	
	case class ListCollectionIdsRequest(
	  /** The maximum number of results to return. */
		pageSize: Option[Int] = None,
	  /** A page token. Must be a value from ListCollectionIdsResponse. */
		pageToken: Option[String] = None,
	  /** Reads documents as they were at the given time. This must be a microsecond precision timestamp within the past one hour, or if Point-in-Time Recovery is enabled, can additionally be a whole minute timestamp within the past 7 days. */
		readTime: Option[String] = None
	)
	
	case class ListCollectionIdsResponse(
	  /** The collection ids. */
		collectionIds: Option[List[String]] = None,
	  /** A page token that may be used to continue the list. */
		nextPageToken: Option[String] = None
	)
	
	case class BatchWriteRequest(
	  /** The writes to apply. Method does not apply writes atomically and does not guarantee ordering. Each write succeeds or fails independently. You cannot write to the same document more than once per request. */
		writes: Option[List[Schema.Write]] = None,
	  /** Labels associated with this batch write. */
		labels: Option[Map[String, String]] = None
	)
	
	case class BatchWriteResponse(
	  /** The result of applying the writes. This i-th write result corresponds to the i-th write in the request. */
		writeResults: Option[List[Schema.WriteResult]] = None,
	  /** The status of applying the writes. This i-th write status corresponds to the i-th write in the request. */
		status: Option[List[Schema.Status]] = None
	)
	
	object GoogleFirestoreAdminV1FieldOperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case OPERATION_STATE_UNSPECIFIED, INITIALIZING, PROCESSING, CANCELLING, FINALIZING, SUCCESSFUL, FAILED, CANCELLED }
	}
	case class GoogleFirestoreAdminV1FieldOperationMetadata(
	  /** The time this operation started. */
		startTime: Option[String] = None,
	  /** The time this operation completed. Will be unset if operation still in progress. */
		endTime: Option[String] = None,
	  /** The field resource that this operation is acting on. For example: `projects/{project_id}/databases/{database_id}/collectionGroups/{collection_id}/fields/{field_path}` */
		field: Option[String] = None,
	  /** A list of IndexConfigDelta, which describe the intent of this operation. */
		indexConfigDeltas: Option[List[Schema.GoogleFirestoreAdminV1IndexConfigDelta]] = None,
	  /** The state of the operation. */
		state: Option[Schema.GoogleFirestoreAdminV1FieldOperationMetadata.StateEnum] = None,
	  /** The progress, in documents, of this operation. */
		progressDocuments: Option[Schema.GoogleFirestoreAdminV1Progress] = None,
	  /** The progress, in bytes, of this operation. */
		progressBytes: Option[Schema.GoogleFirestoreAdminV1Progress] = None,
	  /** Describes the deltas of TTL configuration. */
		ttlConfigDelta: Option[Schema.GoogleFirestoreAdminV1TtlConfigDelta] = None
	)
	
	object GoogleFirestoreAdminV1IndexConfigDelta {
		enum ChangeTypeEnum extends Enum[ChangeTypeEnum] { case CHANGE_TYPE_UNSPECIFIED, ADD, REMOVE }
	}
	case class GoogleFirestoreAdminV1IndexConfigDelta(
	  /** Specifies how the index is changing. */
		changeType: Option[Schema.GoogleFirestoreAdminV1IndexConfigDelta.ChangeTypeEnum] = None,
	  /** The index being changed. */
		index: Option[Schema.GoogleFirestoreAdminV1Index] = None
	)
	
	case class GoogleFirestoreAdminV1Progress(
	  /** The amount of work estimated. */
		estimatedWork: Option[String] = None,
	  /** The amount of work completed. */
		completedWork: Option[String] = None
	)
	
	object GoogleFirestoreAdminV1TtlConfigDelta {
		enum ChangeTypeEnum extends Enum[ChangeTypeEnum] { case CHANGE_TYPE_UNSPECIFIED, ADD, REMOVE }
	}
	case class GoogleFirestoreAdminV1TtlConfigDelta(
	  /** Specifies how the TTL configuration is changing. */
		changeType: Option[Schema.GoogleFirestoreAdminV1TtlConfigDelta.ChangeTypeEnum] = None
	)
	
	object GoogleFirestoreAdminV1IndexOperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case OPERATION_STATE_UNSPECIFIED, INITIALIZING, PROCESSING, CANCELLING, FINALIZING, SUCCESSFUL, FAILED, CANCELLED }
	}
	case class GoogleFirestoreAdminV1IndexOperationMetadata(
	  /** The time this operation started. */
		startTime: Option[String] = None,
	  /** The time this operation completed. Will be unset if operation still in progress. */
		endTime: Option[String] = None,
	  /** The index resource that this operation is acting on. For example: `projects/{project_id}/databases/{database_id}/collectionGroups/{collection_id}/indexes/{index_id}` */
		index: Option[String] = None,
	  /** The state of the operation. */
		state: Option[Schema.GoogleFirestoreAdminV1IndexOperationMetadata.StateEnum] = None,
	  /** The progress, in documents, of this operation. */
		progressDocuments: Option[Schema.GoogleFirestoreAdminV1Progress] = None,
	  /** The progress, in bytes, of this operation. */
		progressBytes: Option[Schema.GoogleFirestoreAdminV1Progress] = None
	)
	
	case class GoogleFirestoreAdminV1LocationMetadata(
	
	)
	
	object GoogleFirestoreAdminV1ExportDocumentsMetadata {
		enum OperationStateEnum extends Enum[OperationStateEnum] { case OPERATION_STATE_UNSPECIFIED, INITIALIZING, PROCESSING, CANCELLING, FINALIZING, SUCCESSFUL, FAILED, CANCELLED }
	}
	case class GoogleFirestoreAdminV1ExportDocumentsMetadata(
	  /** The time this operation started. */
		startTime: Option[String] = None,
	  /** The time this operation completed. Will be unset if operation still in progress. */
		endTime: Option[String] = None,
	  /** The state of the export operation. */
		operationState: Option[Schema.GoogleFirestoreAdminV1ExportDocumentsMetadata.OperationStateEnum] = None,
	  /** The progress, in documents, of this operation. */
		progressDocuments: Option[Schema.GoogleFirestoreAdminV1Progress] = None,
	  /** The progress, in bytes, of this operation. */
		progressBytes: Option[Schema.GoogleFirestoreAdminV1Progress] = None,
	  /** Which collection IDs are being exported. */
		collectionIds: Option[List[String]] = None,
	  /** Where the documents are being exported to. */
		outputUriPrefix: Option[String] = None,
	  /** Which namespace IDs are being exported. */
		namespaceIds: Option[List[String]] = None,
	  /** The timestamp that corresponds to the version of the database that is being exported. If unspecified, there are no guarantees about the consistency of the documents being exported. */
		snapshotTime: Option[String] = None
	)
	
	object GoogleFirestoreAdminV1BulkDeleteDocumentsMetadata {
		enum OperationStateEnum extends Enum[OperationStateEnum] { case OPERATION_STATE_UNSPECIFIED, INITIALIZING, PROCESSING, CANCELLING, FINALIZING, SUCCESSFUL, FAILED, CANCELLED }
	}
	case class GoogleFirestoreAdminV1BulkDeleteDocumentsMetadata(
	  /** The time this operation started. */
		startTime: Option[String] = None,
	  /** The time this operation completed. Will be unset if operation still in progress. */
		endTime: Option[String] = None,
	  /** The state of the operation. */
		operationState: Option[Schema.GoogleFirestoreAdminV1BulkDeleteDocumentsMetadata.OperationStateEnum] = None,
	  /** The progress, in documents, of this operation. */
		progressDocuments: Option[Schema.GoogleFirestoreAdminV1Progress] = None,
	  /** The progress, in bytes, of this operation. */
		progressBytes: Option[Schema.GoogleFirestoreAdminV1Progress] = None,
	  /** The IDs of the collection groups that are being deleted. */
		collectionIds: Option[List[String]] = None,
	  /** Which namespace IDs are being deleted. */
		namespaceIds: Option[List[String]] = None,
	  /** The timestamp that corresponds to the version of the database that is being read to get the list of documents to delete. This time can also be used as the timestamp of PITR in case of disaster recovery (subject to PITR window limit). */
		snapshotTime: Option[String] = None
	)
	
	object GoogleFirestoreAdminV1ImportDocumentsMetadata {
		enum OperationStateEnum extends Enum[OperationStateEnum] { case OPERATION_STATE_UNSPECIFIED, INITIALIZING, PROCESSING, CANCELLING, FINALIZING, SUCCESSFUL, FAILED, CANCELLED }
	}
	case class GoogleFirestoreAdminV1ImportDocumentsMetadata(
	  /** The time this operation started. */
		startTime: Option[String] = None,
	  /** The time this operation completed. Will be unset if operation still in progress. */
		endTime: Option[String] = None,
	  /** The state of the import operation. */
		operationState: Option[Schema.GoogleFirestoreAdminV1ImportDocumentsMetadata.OperationStateEnum] = None,
	  /** The progress, in documents, of this operation. */
		progressDocuments: Option[Schema.GoogleFirestoreAdminV1Progress] = None,
	  /** The progress, in bytes, of this operation. */
		progressBytes: Option[Schema.GoogleFirestoreAdminV1Progress] = None,
	  /** Which collection IDs are being imported. */
		collectionIds: Option[List[String]] = None,
	  /** The location of the documents being imported. */
		inputUriPrefix: Option[String] = None,
	  /** Which namespace IDs are being imported. */
		namespaceIds: Option[List[String]] = None
	)
	
	case class GoogleFirestoreAdminV1ExportDocumentsResponse(
	  /** Location of the output files. This can be used to begin an import into Cloud Firestore (this project or another project) after the operation completes successfully. */
		outputUriPrefix: Option[String] = None
	)
	
	case class GoogleFirestoreAdminV1CreateDatabaseMetadata(
	
	)
	
	case class GoogleFirestoreAdminV1DeleteDatabaseMetadata(
	
	)
	
	case class GoogleFirestoreAdminV1UpdateDatabaseMetadata(
	
	)
	
	object GoogleFirestoreAdminV1RestoreDatabaseMetadata {
		enum OperationStateEnum extends Enum[OperationStateEnum] { case OPERATION_STATE_UNSPECIFIED, INITIALIZING, PROCESSING, CANCELLING, FINALIZING, SUCCESSFUL, FAILED, CANCELLED }
	}
	case class GoogleFirestoreAdminV1RestoreDatabaseMetadata(
	  /** The time the restore was started. */
		startTime: Option[String] = None,
	  /** The time the restore finished, unset for ongoing restores. */
		endTime: Option[String] = None,
	  /** The operation state of the restore. */
		operationState: Option[Schema.GoogleFirestoreAdminV1RestoreDatabaseMetadata.OperationStateEnum] = None,
	  /** The name of the database being restored to. */
		database: Option[String] = None,
	  /** The name of the backup restoring from. */
		backup: Option[String] = None,
	  /** How far along the restore is as an estimated percentage of remaining time. */
		progressPercentage: Option[Schema.GoogleFirestoreAdminV1Progress] = None
	)
}
