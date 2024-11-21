package com.boresjo.play.api.google.datacatalog

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
	
	case class GoogleCloudDatacatalogV1SearchCatalogRequest(
	  /** Required. The scope of this search request. The `scope` is invalid if `include_org_ids`, `include_project_ids` are empty AND `include_gcp_public_datasets` is set to `false`. In this case, the request returns an error. */
		scope: Option[Schema.GoogleCloudDatacatalogV1SearchCatalogRequestScope] = None,
	  /** Optional. The query string with a minimum of 3 characters and specific syntax. For more information, see [Data Catalog search syntax](https://cloud.google.com/data-catalog/docs/how-to/search-reference). An empty query string returns all data assets (in the specified scope) that you have access to. A query string can be a simple `xyz` or qualified by predicates: &#42; `name:x` &#42; `column:y` &#42; `description:z` */
		query: Option[String] = None,
	  /** Upper bound on the number of results you can get in a single response. Can't be negative or 0, defaults to 10 in this case. The maximum number is 1000. If exceeded, throws an "invalid argument" exception. */
		pageSize: Option[Int] = None,
	  /** Optional. Pagination token that, if specified, returns the next page of search results. If empty, returns the first page. This token is returned in the SearchCatalogResponse.next_page_token field of the response to a previous SearchCatalogRequest call. */
		pageToken: Option[String] = None,
	  /** Specifies the order of results. Currently supported case-sensitive values are: &#42; `relevance` that can only be descending &#42; `last_modified_timestamp [asc|desc]` with descending (`desc`) as default &#42; `default` that can only be descending Search queries don't guarantee full recall. Results that match your query might not be returned, even in subsequent result pages. Additionally, returned (and not returned) results can vary if you repeat search queries. If you are experiencing recall issues and you don't have to fetch the results in any specific order, consider setting this parameter to `default`. If this parameter is omitted, it defaults to the descending `relevance`. */
		orderBy: Option[String] = None,
	  /** Optional. If set, use searchAll permission granted on organizations from `include_org_ids` and projects from `include_project_ids` instead of the fine grained per resource permissions when filtering the search results. The only allowed `order_by` criteria for admin_search mode is `default`. Using this flags guarantees a full recall of the search results. */
		adminSearch: Option[Boolean] = None
	)
	
	case class GoogleCloudDatacatalogV1SearchCatalogRequestScope(
	  /** The list of organization IDs to search within. To find your organization ID, follow the steps from [Creating and managing organizations] (/resource-manager/docs/creating-managing-organization). */
		includeOrgIds: Option[List[String]] = None,
	  /** The list of project IDs to search within. For more information on the distinction between project names, IDs, and numbers, see [Projects](/docs/overview/#projects). */
		includeProjectIds: Option[List[String]] = None,
	  /** If `true`, include Google Cloud public datasets in search results. By default, they are excluded. See [Google Cloud Public Datasets](/public-datasets) for more information. */
		includeGcpPublicDatasets: Option[Boolean] = None,
	  /** Optional. The list of locations to search within. If empty, all locations are searched. Returns an error if any location in the list isn't one of the [Supported regions](https://cloud.google.com/data-catalog/docs/concepts/regions#supported_regions). If a location is unreachable, its name is returned in the `SearchCatalogResponse.unreachable` field. To get additional information on the error, repeat the search request and set the location name as the value of this parameter. */
		restrictedLocations: Option[List[String]] = None,
	  /** Optional. If `true`, search only among starred entries. By default, all results are returned, starred or not. */
		starredOnly: Option[Boolean] = None,
	  /** Optional. This field is deprecated. The search mechanism for public and private tag templates is the same. */
		includePublicTagTemplates: Option[Boolean] = None
	)
	
	case class GoogleCloudDatacatalogV1SearchCatalogResponse(
	  /** Search results. */
		results: Option[List[Schema.GoogleCloudDatacatalogV1SearchCatalogResult]] = None,
	  /** The approximate total number of entries matched by the query. */
		totalSize: Option[Int] = None,
	  /** Pagination token that can be used in subsequent calls to retrieve the next page of results. */
		nextPageToken: Option[String] = None,
	  /** Unreachable locations. Search results don't include data from those locations. To get additional information on an error, repeat the search request and restrict it to specific locations by setting the `SearchCatalogRequest.scope.restricted_locations` parameter. */
		unreachable: Option[List[String]] = None
	)
	
	object GoogleCloudDatacatalogV1SearchCatalogResult {
		enum SearchResultTypeEnum extends Enum[SearchResultTypeEnum] { case SEARCH_RESULT_TYPE_UNSPECIFIED, ENTRY, TAG_TEMPLATE, ENTRY_GROUP }
		enum IntegratedSystemEnum extends Enum[IntegratedSystemEnum] { case INTEGRATED_SYSTEM_UNSPECIFIED, BIGQUERY, CLOUD_PUBSUB, DATAPROC_METASTORE, DATAPLEX, CLOUD_SPANNER, CLOUD_BIGTABLE, CLOUD_SQL, LOOKER, VERTEX_AI }
	}
	case class GoogleCloudDatacatalogV1SearchCatalogResult(
	  /** Type of the search result. You can use this field to determine which get method to call to fetch the full resource. */
		searchResultType: Option[Schema.GoogleCloudDatacatalogV1SearchCatalogResult.SearchResultTypeEnum] = None,
	  /** Sub-type of the search result. A dot-delimited full type of the resource. The same type you specify in the `type` search predicate. Examples: `entry.table`, `entry.dataStream`, `tagTemplate`. */
		searchResultSubtype: Option[String] = None,
	  /** The relative name of the resource in URL format. Examples: &#42; `projects/{PROJECT_ID}/locations/{LOCATION_ID}/entryGroups/{ENTRY_GROUP_ID}/entries/{ENTRY_ID}` &#42; `projects/{PROJECT_ID}/tagTemplates/{TAG_TEMPLATE_ID}` */
		relativeResourceName: Option[String] = None,
	  /** The full name of the Google Cloud resource the entry belongs to. For more information, see [Full Resource Name] (/apis/design/resource_names#full_resource_name). Example: `//bigquery.googleapis.com/projects/PROJECT_ID/datasets/DATASET_ID/tables/TABLE_ID` */
		linkedResource: Option[String] = None,
	  /** The last modification timestamp of the entry in the source system. */
		modifyTime: Option[String] = None,
	  /** Output only. The source system that Data Catalog automatically integrates with, such as BigQuery, Cloud Pub/Sub, or Dataproc Metastore. */
		integratedSystem: Option[Schema.GoogleCloudDatacatalogV1SearchCatalogResult.IntegratedSystemEnum] = None,
	  /** Custom source system that you can manually integrate Data Catalog with. */
		userSpecifiedSystem: Option[String] = None,
	  /** Fully qualified name (FQN) of the resource. FQNs take two forms: &#42; For non-regionalized resources: `{SYSTEM}:{PROJECT}.{PATH_TO_RESOURCE_SEPARATED_WITH_DOTS}` &#42; For regionalized resources: `{SYSTEM}:{PROJECT}.{LOCATION_ID}.{PATH_TO_RESOURCE_SEPARATED_WITH_DOTS}` Example for a DPMS table: `dataproc_metastore:PROJECT_ID.LOCATION_ID.INSTANCE_ID.DATABASE_ID.TABLE_ID` */
		fullyQualifiedName: Option[String] = None,
	  /** The display name of the result. */
		displayName: Option[String] = None,
	  /** Entry description that can consist of several sentences or paragraphs that describe entry contents. */
		description: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1EntryGroup(
	  /** Identifier. The resource name of the entry group in URL format. Note: The entry group itself and its child resources might not be stored in the location specified in its name. */
		name: Option[String] = None,
	  /** A short name to identify the entry group, for example, "analytics data - jan 2011". Default value is an empty string. */
		displayName: Option[String] = None,
	  /** Entry group description. Can consist of several sentences or paragraphs that describe the entry group contents. Default value is an empty string. */
		description: Option[String] = None,
	  /** Output only. Timestamps of the entry group. Default value is empty. */
		dataCatalogTimestamps: Option[Schema.GoogleCloudDatacatalogV1SystemTimestamps] = None,
	  /** Optional. When set to [true], it means DataCatalog EntryGroup was transferred to Dataplex Catalog Service. It makes EntryGroup and its Entries to be read-only in DataCatalog. However, new Tags on EntryGroup and its Entries can be created. After setting the flag to [true] it cannot be unset. */
		transferredToDataplex: Option[Boolean] = None
	)
	
	case class GoogleCloudDatacatalogV1SystemTimestamps(
	  /** Creation timestamp of the resource within the given system. */
		createTime: Option[String] = None,
	  /** Timestamp of the last modification of the resource or its metadata within a given system. Note: Depending on the source system, not every modification updates this timestamp. For example, BigQuery timestamps every metadata modification but not data or permission changes. */
		updateTime: Option[String] = None,
	  /** Output only. Expiration timestamp of the resource within the given system. Currently only applicable to BigQuery resources. */
		expireTime: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1ListEntryGroupsResponse(
	  /** Entry group details. */
		entryGroups: Option[List[Schema.GoogleCloudDatacatalogV1EntryGroup]] = None,
	  /** Pagination token to specify in the next call to retrieve the next page of results. Empty if there are no more items. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudDatacatalogV1Entry {
		enum TypeEnum extends Enum[TypeEnum] { case ENTRY_TYPE_UNSPECIFIED, TABLE, MODEL, DATA_STREAM, FILESET, CLUSTER, DATABASE, DATA_SOURCE_CONNECTION, ROUTINE, LAKE, ZONE, SERVICE, DATABASE_SCHEMA, DASHBOARD, EXPLORE, LOOK, FEATURE_ONLINE_STORE, FEATURE_VIEW, FEATURE_GROUP }
		enum IntegratedSystemEnum extends Enum[IntegratedSystemEnum] { case INTEGRATED_SYSTEM_UNSPECIFIED, BIGQUERY, CLOUD_PUBSUB, DATAPROC_METASTORE, DATAPLEX, CLOUD_SPANNER, CLOUD_BIGTABLE, CLOUD_SQL, LOOKER, VERTEX_AI }
	}
	case class GoogleCloudDatacatalogV1Entry(
	  /** Output only. Identifier. The resource name of an entry in URL format. Note: The entry itself and its child resources might not be stored in the location specified in its name. */
		name: Option[String] = None,
	  /** The resource this metadata entry refers to. For Google Cloud Platform resources, `linked_resource` is the [Full Resource Name] (https://cloud.google.com/apis/design/resource_names#full_resource_name). For example, the `linked_resource` for a table resource from BigQuery is: `//bigquery.googleapis.com/projects/{PROJECT_ID}/datasets/{DATASET_ID}/tables/{TABLE_ID}` Output only when the entry is one of the types in the `EntryType` enum. For entries with a `user_specified_type`, this field is optional and defaults to an empty string. The resource string must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), periods (.), colons (:), slashes (/), dashes (-), and hashes (#). The maximum size is 200 bytes when encoded in UTF-8. */
		linkedResource: Option[String] = None,
	  /** [Fully Qualified Name (FQN)](https://cloud.google.com//data-catalog/docs/fully-qualified-names) of the resource. Set automatically for entries representing resources from synced systems. Settable only during creation, and read-only later. Can be used for search and lookup of the entries.  */
		fullyQualifiedName: Option[String] = None,
	  /** The type of the entry. For details, see [`EntryType`](#entrytype). */
		`type`: Option[Schema.GoogleCloudDatacatalogV1Entry.TypeEnum] = None,
	  /** Custom entry type that doesn't match any of the values allowed for input and listed in the `EntryType` enum. When creating an entry, first check the type values in the enum. If there are no appropriate types for the new entry, provide a custom value, for example, `my_special_type`. The `user_specified_type` string has the following limitations: &#42; Is case insensitive. &#42; Must begin with a letter or underscore. &#42; Can only contain letters, numbers, and underscores. &#42; Must be at least 1 character and at most 64 characters long. */
		userSpecifiedType: Option[String] = None,
	  /** Output only. Indicates the entry's source system that Data Catalog integrates with, such as BigQuery, Pub/Sub, or Dataproc Metastore. */
		integratedSystem: Option[Schema.GoogleCloudDatacatalogV1Entry.IntegratedSystemEnum] = None,
	  /** Indicates the entry's source system that Data Catalog doesn't automatically integrate with. The `user_specified_system` string has the following limitations: &#42; Is case insensitive. &#42; Must begin with a letter or underscore. &#42; Can only contain letters, numbers, and underscores. &#42; Must be at least 1 character and at most 64 characters long. */
		userSpecifiedSystem: Option[String] = None,
	  /** Specification that applies to a relational database system. Only settable when `user_specified_system` is equal to `SQL_DATABASE` */
		sqlDatabaseSystemSpec: Option[Schema.GoogleCloudDatacatalogV1SqlDatabaseSystemSpec] = None,
	  /** Specification that applies to Looker sysstem. Only settable when `user_specified_system` is equal to `LOOKER` */
		lookerSystemSpec: Option[Schema.GoogleCloudDatacatalogV1LookerSystemSpec] = None,
	  /** Specification that applies to Cloud Bigtable system. Only settable when `integrated_system` is equal to `CLOUD_BIGTABLE` */
		cloudBigtableSystemSpec: Option[Schema.GoogleCloudDatacatalogV1CloudBigtableSystemSpec] = None,
	  /** Specification that applies to a Cloud Storage fileset. Valid only for entries with the `FILESET` type. */
		gcsFilesetSpec: Option[Schema.GoogleCloudDatacatalogV1GcsFilesetSpec] = None,
	  /** Output only. Specification that applies to a BigQuery table. Valid only for entries with the `TABLE` type. */
		bigqueryTableSpec: Option[Schema.GoogleCloudDatacatalogV1BigQueryTableSpec] = None,
	  /** Output only. Specification for a group of BigQuery tables with the `[prefix]YYYYMMDD` name pattern. For more information, see [Introduction to partitioned tables] (https://cloud.google.com/bigquery/docs/partitioned-tables#partitioning_versus_sharding). */
		bigqueryDateShardedSpec: Option[Schema.GoogleCloudDatacatalogV1BigQueryDateShardedSpec] = None,
	  /** Specification that applies to a table resource. Valid only for entries with the `TABLE` or `EXPLORE` type. */
		databaseTableSpec: Option[Schema.GoogleCloudDatacatalogV1DatabaseTableSpec] = None,
	  /** Specification that applies to a data source connection. Valid only for entries with the `DATA_SOURCE_CONNECTION` type. */
		dataSourceConnectionSpec: Option[Schema.GoogleCloudDatacatalogV1DataSourceConnectionSpec] = None,
	  /** Specification that applies to a user-defined function or procedure. Valid only for entries with the `ROUTINE` type. */
		routineSpec: Option[Schema.GoogleCloudDatacatalogV1RoutineSpec] = None,
	  /** Specification that applies to a dataset. */
		datasetSpec: Option[Schema.GoogleCloudDatacatalogV1DatasetSpec] = None,
	  /** Specification that applies to a fileset resource. Valid only for entries with the `FILESET` type. */
		filesetSpec: Option[Schema.GoogleCloudDatacatalogV1FilesetSpec] = None,
	  /** Specification that applies to a Service resource. */
		serviceSpec: Option[Schema.GoogleCloudDatacatalogV1ServiceSpec] = None,
	  /** Model specification. */
		modelSpec: Option[Schema.GoogleCloudDatacatalogV1ModelSpec] = None,
	  /** FeatureonlineStore spec for Vertex AI Feature Store. */
		featureOnlineStoreSpec: Option[Schema.GoogleCloudDatacatalogV1FeatureOnlineStoreSpec] = None,
	  /** Display name of an entry. The maximum size is 500 bytes when encoded in UTF-8. Default value is an empty string. */
		displayName: Option[String] = None,
	  /** Entry description that can consist of several sentences or paragraphs that describe entry contents. The description must not contain Unicode non-characters as well as C0 and C1 control codes except tabs (HT), new lines (LF), carriage returns (CR), and page breaks (FF). The maximum size is 2000 bytes when encoded in UTF-8. Default value is an empty string. */
		description: Option[String] = None,
	  /** Business Context of the entry. Not supported for BigQuery datasets */
		businessContext: Option[Schema.GoogleCloudDatacatalogV1BusinessContext] = None,
	  /** Schema of the entry. An entry might not have any schema attached to it. */
		schema: Option[Schema.GoogleCloudDatacatalogV1Schema] = None,
	  /** Timestamps from the underlying resource, not from the Data Catalog entry. Output only when the entry has a system listed in the `IntegratedSystem` enum. For entries with `user_specified_system`, this field is optional and defaults to an empty timestamp. */
		sourceSystemTimestamps: Option[Schema.GoogleCloudDatacatalogV1SystemTimestamps] = None,
	  /** Resource usage statistics. */
		usageSignal: Option[Schema.GoogleCloudDatacatalogV1UsageSignal] = None,
	  /** Cloud labels attached to the entry. In Data Catalog, you can create and modify labels attached only to custom entries. Synced entries have unmodifiable labels that come from the source system. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Physical location of the entry. */
		dataSource: Option[Schema.GoogleCloudDatacatalogV1DataSource] = None,
	  /** Output only. Additional information related to the entry. Private to the current user. */
		personalDetails: Option[Schema.GoogleCloudDatacatalogV1PersonalDetails] = None
	)
	
	case class GoogleCloudDatacatalogV1SqlDatabaseSystemSpec(
	  /** SQL Database Engine. enum SqlEngine { UNDEFINED = 0; MY_SQL = 1; POSTGRE_SQL = 2; SQL_SERVER = 3; } Engine of the enclosing database instance. */
		sqlEngine: Option[String] = None,
	  /** Version of the database engine. */
		databaseVersion: Option[String] = None,
	  /** Host of the SQL database enum InstanceHost { UNDEFINED = 0; SELF_HOSTED = 1; CLOUD_SQL = 2; AMAZON_RDS = 3; AZURE_SQL = 4; } Host of the enclousing database instance. */
		instanceHost: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1LookerSystemSpec(
	  /** ID of the parent Looker Instance. Empty if it does not exist. Example value: `someinstance.looker.com` */
		parentInstanceId: Option[String] = None,
	  /** Name of the parent Looker Instance. Empty if it does not exist. */
		parentInstanceDisplayName: Option[String] = None,
	  /** ID of the parent Model. Empty if it does not exist. */
		parentModelId: Option[String] = None,
	  /** Name of the parent Model. Empty if it does not exist. */
		parentModelDisplayName: Option[String] = None,
	  /** ID of the parent View. Empty if it does not exist. */
		parentViewId: Option[String] = None,
	  /** Name of the parent View. Empty if it does not exist. */
		parentViewDisplayName: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1CloudBigtableSystemSpec(
	  /** Display name of the Instance. This is user specified and different from the resource name. */
		instanceDisplayName: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1GcsFilesetSpec(
	  /** Required. Patterns to identify a set of files in Google Cloud Storage. For more information, see [Wildcard Names] (https://cloud.google.com/storage/docs/gsutil/addlhelp/WildcardNames). Note: Currently, bucket wildcards are not supported. Examples of valid `file_patterns`: &#42; `gs://bucket_name/dir/&#42;`: matches all files in `bucket_name/dir` directory &#42; `gs://bucket_name/dir/&#42;&#42;`: matches all files in `bucket_name/dir` and all subdirectories &#42; `gs://bucket_name/file&#42;`: matches files prefixed by `file` in `bucket_name` &#42; `gs://bucket_name/??.txt`: matches files with two characters followed by `.txt` in `bucket_name` &#42; `gs://bucket_name/[aeiou].txt`: matches files that contain a single vowel character followed by `.txt` in `bucket_name` &#42; `gs://bucket_name/[a-m].txt`: matches files that contain `a`, `b`, ... or `m` followed by `.txt` in `bucket_name` &#42; `gs://bucket_name/a/&#42;/b`: matches all files in `bucket_name` that match the `a/&#42;/b` pattern, such as `a/c/b`, `a/d/b` &#42; `gs://another_bucket/a.txt`: matches `gs://another_bucket/a.txt` You can combine wildcards to match complex sets of files, for example: `gs://bucket_name/[a-m]??.j&#42;g` */
		filePatterns: Option[List[String]] = None,
	  /** Output only. Sample files contained in this fileset, not all files contained in this fileset are represented here. */
		sampleGcsFileSpecs: Option[List[Schema.GoogleCloudDatacatalogV1GcsFileSpec]] = None
	)
	
	case class GoogleCloudDatacatalogV1GcsFileSpec(
	  /** Required. Full file path. Example: `gs://bucket_name/a/b.txt`. */
		filePath: Option[String] = None,
	  /** Output only. Creation, modification, and expiration timestamps of a Cloud Storage file. */
		gcsTimestamps: Option[Schema.GoogleCloudDatacatalogV1SystemTimestamps] = None,
	  /** Output only. File size in bytes. */
		sizeBytes: Option[String] = None
	)
	
	object GoogleCloudDatacatalogV1BigQueryTableSpec {
		enum TableSourceTypeEnum extends Enum[TableSourceTypeEnum] { case TABLE_SOURCE_TYPE_UNSPECIFIED, BIGQUERY_VIEW, BIGQUERY_TABLE, BIGQUERY_MATERIALIZED_VIEW }
	}
	case class GoogleCloudDatacatalogV1BigQueryTableSpec(
	  /** Output only. The table source type. */
		tableSourceType: Option[Schema.GoogleCloudDatacatalogV1BigQueryTableSpec.TableSourceTypeEnum] = None,
	  /** Table view specification. Populated only if the `table_source_type` is `BIGQUERY_VIEW`. */
		viewSpec: Option[Schema.GoogleCloudDatacatalogV1ViewSpec] = None,
	  /** Specification of a BigQuery table. Populated only if the `table_source_type` is `BIGQUERY_TABLE`. */
		tableSpec: Option[Schema.GoogleCloudDatacatalogV1TableSpec] = None
	)
	
	case class GoogleCloudDatacatalogV1ViewSpec(
	  /** Output only. The query that defines the table view. */
		viewQuery: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1TableSpec(
	  /** Output only. If the table is date-sharded, that is, it matches the `[prefix]YYYYMMDD` name pattern, this field is the Data Catalog resource name of the date-sharded grouped entry. For example: `projects/{PROJECT_ID}/locations/{LOCATION}/entrygroups/{ENTRY_GROUP_ID}/entries/{ENTRY_ID}`. Otherwise, `grouped_entry` is empty. */
		groupedEntry: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1BigQueryDateShardedSpec(
	  /** Output only. The Data Catalog resource name of the dataset entry the current table belongs to. For example: `projects/{PROJECT_ID}/locations/{LOCATION}/entrygroups/{ENTRY_GROUP_ID}/entries/{ENTRY_ID}`. */
		dataset: Option[String] = None,
	  /** Output only. The table name prefix of the shards. The name of any given shard is `[table_prefix]YYYYMMDD`. For example, for the `MyTable20180101` shard, the `table_prefix` is `MyTable`. */
		tablePrefix: Option[String] = None,
	  /** Output only. Total number of shards. */
		shardCount: Option[String] = None,
	  /** Output only. BigQuery resource name of the latest shard. */
		latestShardResource: Option[String] = None
	)
	
	object GoogleCloudDatacatalogV1DatabaseTableSpec {
		enum TypeEnum extends Enum[TypeEnum] { case TABLE_TYPE_UNSPECIFIED, NATIVE, EXTERNAL }
	}
	case class GoogleCloudDatacatalogV1DatabaseTableSpec(
	  /** Type of this table. */
		`type`: Option[Schema.GoogleCloudDatacatalogV1DatabaseTableSpec.TypeEnum] = None,
	  /** Output only. Fields specific to a Dataplex table and present only in the Dataplex table entries. */
		dataplexTable: Option[Schema.GoogleCloudDatacatalogV1DataplexTableSpec] = None,
	  /** Spec what aplies to tables that are actually views. Not set for "real" tables. */
		databaseViewSpec: Option[Schema.GoogleCloudDatacatalogV1DatabaseTableSpecDatabaseViewSpec] = None
	)
	
	case class GoogleCloudDatacatalogV1DataplexTableSpec(
	  /** List of external tables registered by Dataplex in other systems based on the same underlying data. External tables allow to query this data in those systems. */
		externalTables: Option[List[Schema.GoogleCloudDatacatalogV1DataplexExternalTable]] = None,
	  /** Common Dataplex fields. */
		dataplexSpec: Option[Schema.GoogleCloudDatacatalogV1DataplexSpec] = None,
	  /** Indicates if the table schema is managed by the user or not. */
		userManaged: Option[Boolean] = None
	)
	
	object GoogleCloudDatacatalogV1DataplexExternalTable {
		enum SystemEnum extends Enum[SystemEnum] { case INTEGRATED_SYSTEM_UNSPECIFIED, BIGQUERY, CLOUD_PUBSUB, DATAPROC_METASTORE, DATAPLEX, CLOUD_SPANNER, CLOUD_BIGTABLE, CLOUD_SQL, LOOKER, VERTEX_AI }
	}
	case class GoogleCloudDatacatalogV1DataplexExternalTable(
	  /** Service in which the external table is registered. */
		system: Option[Schema.GoogleCloudDatacatalogV1DataplexExternalTable.SystemEnum] = None,
	  /** Fully qualified name (FQN) of the external table. */
		fullyQualifiedName: Option[String] = None,
	  /** Google Cloud resource name of the external table. */
		googleCloudResource: Option[String] = None,
	  /** Name of the Data Catalog entry representing the external table. */
		dataCatalogEntry: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1DataplexSpec(
	  /** Fully qualified resource name of an asset in Dataplex, to which the underlying data source (Cloud Storage bucket or BigQuery dataset) of the entity is attached. */
		asset: Option[String] = None,
	  /** Format of the data. */
		dataFormat: Option[Schema.GoogleCloudDatacatalogV1PhysicalSchema] = None,
	  /** Compression format of the data, e.g., zip, gzip etc. */
		compressionFormat: Option[String] = None,
	  /** Project ID of the underlying Cloud Storage or BigQuery data. Note that this may not be the same project as the correspondingly Dataplex lake / zone / asset. */
		projectId: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1PhysicalSchema(
	  /** Schema in Avro JSON format. */
		avro: Option[Schema.GoogleCloudDatacatalogV1PhysicalSchemaAvroSchema] = None,
	  /** Schema in Thrift format. */
		thrift: Option[Schema.GoogleCloudDatacatalogV1PhysicalSchemaThriftSchema] = None,
	  /** Schema in protocol buffer format. */
		protobuf: Option[Schema.GoogleCloudDatacatalogV1PhysicalSchemaProtobufSchema] = None,
	  /** Marks a Parquet-encoded data source. */
		parquet: Option[Schema.GoogleCloudDatacatalogV1PhysicalSchemaParquetSchema] = None,
	  /** Marks an ORC-encoded data source. */
		orc: Option[Schema.GoogleCloudDatacatalogV1PhysicalSchemaOrcSchema] = None,
	  /** Marks a CSV-encoded data source. */
		csv: Option[Schema.GoogleCloudDatacatalogV1PhysicalSchemaCsvSchema] = None
	)
	
	case class GoogleCloudDatacatalogV1PhysicalSchemaAvroSchema(
	  /** JSON source of the Avro schema. */
		text: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1PhysicalSchemaThriftSchema(
	  /** Thrift IDL source of the schema. */
		text: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1PhysicalSchemaProtobufSchema(
	  /** Protocol buffer source of the schema. */
		text: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1PhysicalSchemaParquetSchema(
	
	)
	
	case class GoogleCloudDatacatalogV1PhysicalSchemaOrcSchema(
	
	)
	
	case class GoogleCloudDatacatalogV1PhysicalSchemaCsvSchema(
	
	)
	
	object GoogleCloudDatacatalogV1DatabaseTableSpecDatabaseViewSpec {
		enum ViewTypeEnum extends Enum[ViewTypeEnum] { case VIEW_TYPE_UNSPECIFIED, STANDARD_VIEW, MATERIALIZED_VIEW }
	}
	case class GoogleCloudDatacatalogV1DatabaseTableSpecDatabaseViewSpec(
	  /** Type of this view. */
		viewType: Option[Schema.GoogleCloudDatacatalogV1DatabaseTableSpecDatabaseViewSpec.ViewTypeEnum] = None,
	  /** Name of a singular table this view reflects one to one. */
		baseTable: Option[String] = None,
	  /** SQL query used to generate this view. */
		sqlQuery: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1DataSourceConnectionSpec(
	  /** Output only. Fields specific to BigQuery connections. */
		bigqueryConnectionSpec: Option[Schema.GoogleCloudDatacatalogV1BigQueryConnectionSpec] = None
	)
	
	object GoogleCloudDatacatalogV1BigQueryConnectionSpec {
		enum ConnectionTypeEnum extends Enum[ConnectionTypeEnum] { case CONNECTION_TYPE_UNSPECIFIED, CLOUD_SQL }
	}
	case class GoogleCloudDatacatalogV1BigQueryConnectionSpec(
	  /** The type of the BigQuery connection. */
		connectionType: Option[Schema.GoogleCloudDatacatalogV1BigQueryConnectionSpec.ConnectionTypeEnum] = None,
	  /** Specification for the BigQuery connection to a Cloud SQL instance. */
		cloudSql: Option[Schema.GoogleCloudDatacatalogV1CloudSqlBigQueryConnectionSpec] = None,
	  /** True if there are credentials attached to the BigQuery connection; false otherwise. */
		hasCredential: Option[Boolean] = None
	)
	
	object GoogleCloudDatacatalogV1CloudSqlBigQueryConnectionSpec {
		enum TypeEnum extends Enum[TypeEnum] { case DATABASE_TYPE_UNSPECIFIED, POSTGRES, MYSQL }
	}
	case class GoogleCloudDatacatalogV1CloudSqlBigQueryConnectionSpec(
	  /** Cloud SQL instance ID in the format of `project:location:instance`. */
		instanceId: Option[String] = None,
	  /** Database name. */
		database: Option[String] = None,
	  /** Type of the Cloud SQL database. */
		`type`: Option[Schema.GoogleCloudDatacatalogV1CloudSqlBigQueryConnectionSpec.TypeEnum] = None
	)
	
	object GoogleCloudDatacatalogV1RoutineSpec {
		enum RoutineTypeEnum extends Enum[RoutineTypeEnum] { case ROUTINE_TYPE_UNSPECIFIED, SCALAR_FUNCTION, PROCEDURE }
	}
	case class GoogleCloudDatacatalogV1RoutineSpec(
	  /** The type of the routine. */
		routineType: Option[Schema.GoogleCloudDatacatalogV1RoutineSpec.RoutineTypeEnum] = None,
	  /** The language the routine is written in. The exact value depends on the source system. For BigQuery routines, possible values are: &#42; `SQL` &#42; `JAVASCRIPT` */
		language: Option[String] = None,
	  /** Arguments of the routine. */
		routineArguments: Option[List[Schema.GoogleCloudDatacatalogV1RoutineSpecArgument]] = None,
	  /** Return type of the argument. The exact value depends on the source system and the language. */
		returnType: Option[String] = None,
	  /** The body of the routine. */
		definitionBody: Option[String] = None,
	  /** Fields specific for BigQuery routines. */
		bigqueryRoutineSpec: Option[Schema.GoogleCloudDatacatalogV1BigQueryRoutineSpec] = None
	)
	
	object GoogleCloudDatacatalogV1RoutineSpecArgument {
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, IN, OUT, INOUT }
	}
	case class GoogleCloudDatacatalogV1RoutineSpecArgument(
	  /** The name of the argument. A return argument of a function might not have a name. */
		name: Option[String] = None,
	  /** Specifies whether the argument is input or output. */
		mode: Option[Schema.GoogleCloudDatacatalogV1RoutineSpecArgument.ModeEnum] = None,
	  /** Type of the argument. The exact value depends on the source system and the language. */
		`type`: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1BigQueryRoutineSpec(
	  /** Paths of the imported libraries. */
		importedLibraries: Option[List[String]] = None
	)
	
	case class GoogleCloudDatacatalogV1DatasetSpec(
	  /** Vertex AI Dataset specific fields */
		vertexDatasetSpec: Option[Schema.GoogleCloudDatacatalogV1VertexDatasetSpec] = None
	)
	
	object GoogleCloudDatacatalogV1VertexDatasetSpec {
		enum DataTypeEnum extends Enum[DataTypeEnum] { case DATA_TYPE_UNSPECIFIED, TABLE, IMAGE, TEXT, VIDEO, CONVERSATION, TIME_SERIES, DOCUMENT, TEXT_TO_SPEECH, TRANSLATION, STORE_VISION, ENTERPRISE_KNOWLEDGE_GRAPH, TEXT_PROMPT }
	}
	case class GoogleCloudDatacatalogV1VertexDatasetSpec(
	  /** The number of DataItems in this Dataset. Only apply for non-structured Dataset. */
		dataItemCount: Option[String] = None,
	  /** Type of the dataset. */
		dataType: Option[Schema.GoogleCloudDatacatalogV1VertexDatasetSpec.DataTypeEnum] = None
	)
	
	case class GoogleCloudDatacatalogV1FilesetSpec(
	  /** Fields specific to a Dataplex fileset and present only in the Dataplex fileset entries. */
		dataplexFileset: Option[Schema.GoogleCloudDatacatalogV1DataplexFilesetSpec] = None
	)
	
	case class GoogleCloudDatacatalogV1DataplexFilesetSpec(
	  /** Common Dataplex fields. */
		dataplexSpec: Option[Schema.GoogleCloudDatacatalogV1DataplexSpec] = None
	)
	
	case class GoogleCloudDatacatalogV1ServiceSpec(
	  /** Specification that applies to Instance entries of `CLOUD_BIGTABLE` system. */
		cloudBigtableInstanceSpec: Option[Schema.GoogleCloudDatacatalogV1CloudBigtableInstanceSpec] = None
	)
	
	case class GoogleCloudDatacatalogV1CloudBigtableInstanceSpec(
	  /** The list of clusters for the Instance. */
		cloudBigtableClusterSpecs: Option[List[Schema.GoogleCloudDatacatalogV1CloudBigtableInstanceSpecCloudBigtableClusterSpec]] = None
	)
	
	case class GoogleCloudDatacatalogV1CloudBigtableInstanceSpecCloudBigtableClusterSpec(
	  /** Name of the cluster. */
		displayName: Option[String] = None,
	  /** Location of the cluster, typically a Cloud zone. */
		location: Option[String] = None,
	  /** Type of the resource. For a cluster this would be "CLUSTER". */
		`type`: Option[String] = None,
	  /** A link back to the parent resource, in this case Instance. */
		linkedResource: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1ModelSpec(
	  /** Specification for vertex model resources. */
		vertexModelSpec: Option[Schema.GoogleCloudDatacatalogV1VertexModelSpec] = None
	)
	
	case class GoogleCloudDatacatalogV1VertexModelSpec(
	  /** The version ID of the model. */
		versionId: Option[String] = None,
	  /** User provided version aliases so that a model version can be referenced via alias */
		versionAliases: Option[List[String]] = None,
	  /** The description of this version. */
		versionDescription: Option[String] = None,
	  /** Source of a Vertex model. */
		vertexModelSourceInfo: Option[Schema.GoogleCloudDatacatalogV1VertexModelSourceInfo] = None,
	  /** URI of the Docker image to be used as the custom container for serving predictions. */
		containerImageUri: Option[String] = None
	)
	
	object GoogleCloudDatacatalogV1VertexModelSourceInfo {
		enum SourceTypeEnum extends Enum[SourceTypeEnum] { case MODEL_SOURCE_TYPE_UNSPECIFIED, AUTOML, CUSTOM, BQML, MODEL_GARDEN, GENIE, CUSTOM_TEXT_EMBEDDING, MARKETPLACE }
	}
	case class GoogleCloudDatacatalogV1VertexModelSourceInfo(
	  /** Type of the model source. */
		sourceType: Option[Schema.GoogleCloudDatacatalogV1VertexModelSourceInfo.SourceTypeEnum] = None,
	  /** If this Model is copy of another Model. If true then source_type pertains to the original. */
		copy: Option[Boolean] = None
	)
	
	object GoogleCloudDatacatalogV1FeatureOnlineStoreSpec {
		enum StorageTypeEnum extends Enum[StorageTypeEnum] { case STORAGE_TYPE_UNSPECIFIED, BIGTABLE, OPTIMIZED }
	}
	case class GoogleCloudDatacatalogV1FeatureOnlineStoreSpec(
	  /** Output only. Type of underelaying storage for the FeatureOnlineStore. */
		storageType: Option[Schema.GoogleCloudDatacatalogV1FeatureOnlineStoreSpec.StorageTypeEnum] = None
	)
	
	case class GoogleCloudDatacatalogV1BusinessContext(
	  /** Entry overview fields for rich text descriptions of entries. */
		entryOverview: Option[Schema.GoogleCloudDatacatalogV1EntryOverview] = None,
	  /** Contact people for the entry. */
		contacts: Option[Schema.GoogleCloudDatacatalogV1Contacts] = None
	)
	
	case class GoogleCloudDatacatalogV1EntryOverview(
	  /** Entry overview with support for rich text. The overview must only contain Unicode characters, and should be formatted using HTML. The maximum length is 10 MiB as this value holds HTML descriptions including encoded images. The maximum length of the text without images is 100 KiB. */
		overview: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1Contacts(
	  /** The list of contact people for the entry. */
		people: Option[List[Schema.GoogleCloudDatacatalogV1ContactsPerson]] = None
	)
	
	case class GoogleCloudDatacatalogV1ContactsPerson(
	  /** Designation of the person, for example, Data Steward. */
		designation: Option[String] = None,
	  /** Email of the person in the format of `john.doe@xyz`, ``, or `John Doe`. */
		email: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1Schema(
	  /** The unified GoogleSQL-like schema of columns. The overall maximum number of columns and nested columns is 10,000. The maximum nested depth is 15 levels. */
		columns: Option[List[Schema.GoogleCloudDatacatalogV1ColumnSchema]] = None
	)
	
	object GoogleCloudDatacatalogV1ColumnSchema {
		enum HighestIndexingTypeEnum extends Enum[HighestIndexingTypeEnum] { case INDEXING_TYPE_UNSPECIFIED, INDEXING_TYPE_NONE, INDEXING_TYPE_NON_UNIQUE, INDEXING_TYPE_UNIQUE, INDEXING_TYPE_PRIMARY_KEY }
	}
	case class GoogleCloudDatacatalogV1ColumnSchema(
	  /** Required. Name of the column. Must be a UTF-8 string without dots (.). The maximum size is 64 bytes. */
		column: Option[String] = None,
	  /** Required. Type of the column. Must be a UTF-8 string with the maximum size of 128 bytes. */
		`type`: Option[String] = None,
	  /** Optional. Description of the column. Default value is an empty string. The description must be a UTF-8 string with the maximum size of 2000 bytes. */
		description: Option[String] = None,
	  /** Optional. A column's mode indicates whether values in this column are required, nullable, or repeated. Only `NULLABLE`, `REQUIRED`, and `REPEATED` values are supported. Default mode is `NULLABLE`. */
		mode: Option[String] = None,
	  /** Optional. Default value for the column. */
		defaultValue: Option[String] = None,
	  /** Optional. Ordinal position */
		ordinalPosition: Option[Int] = None,
	  /** Optional. Most important inclusion of this column. */
		highestIndexingType: Option[Schema.GoogleCloudDatacatalogV1ColumnSchema.HighestIndexingTypeEnum] = None,
	  /** Optional. Schema of sub-columns. A column can have zero or more sub-columns. */
		subcolumns: Option[List[Schema.GoogleCloudDatacatalogV1ColumnSchema]] = None,
	  /** Looker specific column info of this column. */
		lookerColumnSpec: Option[Schema.GoogleCloudDatacatalogV1ColumnSchemaLookerColumnSpec] = None,
	  /** Optional. The subtype of the RANGE, if the type of this field is RANGE. If the type is RANGE, this field is required. Possible values for the field element type of a RANGE include: &#42; DATE &#42; DATETIME &#42; TIMESTAMP */
		rangeElementType: Option[Schema.GoogleCloudDatacatalogV1ColumnSchemaFieldElementType] = None,
	  /** Optional. Garbage collection policy for the column or column family. Applies to systems like Cloud Bigtable. */
		gcRule: Option[String] = None
	)
	
	object GoogleCloudDatacatalogV1ColumnSchemaLookerColumnSpec {
		enum TypeEnum extends Enum[TypeEnum] { case LOOKER_COLUMN_TYPE_UNSPECIFIED, DIMENSION, DIMENSION_GROUP, FILTER, MEASURE, PARAMETER }
	}
	case class GoogleCloudDatacatalogV1ColumnSchemaLookerColumnSpec(
	  /** Looker specific column type of this column. */
		`type`: Option[Schema.GoogleCloudDatacatalogV1ColumnSchemaLookerColumnSpec.TypeEnum] = None
	)
	
	case class GoogleCloudDatacatalogV1ColumnSchemaFieldElementType(
	  /** Required. The type of a field element. See ColumnSchema.type. */
		`type`: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1UsageSignal(
	  /** The end timestamp of the duration of usage statistics. */
		updateTime: Option[String] = None,
	  /** Output only. BigQuery usage statistics over each of the predefined time ranges. Supported time ranges are `{"24H", "7D", "30D"}`. */
		usageWithinTimeRange: Option[Map[String, Schema.GoogleCloudDatacatalogV1UsageStats]] = None,
	  /** Common usage statistics over each of the predefined time ranges. Supported time ranges are `{"24H", "7D", "30D", "Lifetime"}`. */
		commonUsageWithinTimeRange: Option[Map[String, Schema.GoogleCloudDatacatalogV1CommonUsageStats]] = None,
	  /** Favorite count in the source system. */
		favoriteCount: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1UsageStats(
	  /** The number of successful uses of the underlying entry. */
		totalCompletions: Option[BigDecimal] = None,
	  /** The number of failed attempts to use the underlying entry. */
		totalFailures: Option[BigDecimal] = None,
	  /** The number of cancelled attempts to use the underlying entry. */
		totalCancellations: Option[BigDecimal] = None,
	  /** Total time spent only on successful uses, in milliseconds. */
		totalExecutionTimeForCompletionsMillis: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDatacatalogV1CommonUsageStats(
	  /** View count in source system. */
		viewCount: Option[String] = None
	)
	
	object GoogleCloudDatacatalogV1DataSource {
		enum ServiceEnum extends Enum[ServiceEnum] { case SERVICE_UNSPECIFIED, CLOUD_STORAGE, BIGQUERY }
	}
	case class GoogleCloudDatacatalogV1DataSource(
	  /** Service that physically stores the data. */
		service: Option[Schema.GoogleCloudDatacatalogV1DataSource.ServiceEnum] = None,
	  /** Full name of a resource as defined by the service. For example: `//bigquery.googleapis.com/projects/{PROJECT_ID}/locations/{LOCATION}/datasets/{DATASET_ID}/tables/{TABLE_ID}` */
		resource: Option[String] = None,
	  /** Output only. Data Catalog entry name, if applicable. */
		sourceEntry: Option[String] = None,
	  /** Detailed properties of the underlying storage. */
		storageProperties: Option[Schema.GoogleCloudDatacatalogV1StorageProperties] = None
	)
	
	case class GoogleCloudDatacatalogV1StorageProperties(
	  /** Patterns to identify a set of files for this fileset. Examples of a valid `file_pattern`: &#42; `gs://bucket_name/dir/&#42;`: matches all files in the `bucket_name/dir` directory &#42; `gs://bucket_name/dir/&#42;&#42;`: matches all files in the `bucket_name/dir` and all subdirectories recursively &#42; `gs://bucket_name/file&#42;`: matches files prefixed by `file` in `bucket_name` &#42; `gs://bucket_name/??.txt`: matches files with two characters followed by `.txt` in `bucket_name` &#42; `gs://bucket_name/[aeiou].txt`: matches files that contain a single vowel character followed by `.txt` in `bucket_name` &#42; `gs://bucket_name/[a-m].txt`: matches files that contain `a`, `b`, ... or `m` followed by `.txt` in `bucket_name` &#42; `gs://bucket_name/a/&#42;/b`: matches all files in `bucket_name` that match the `a/&#42;/b` pattern, such as `a/c/b`, `a/d/b` &#42; `gs://another_bucket/a.txt`: matches `gs://another_bucket/a.txt` */
		filePattern: Option[List[String]] = None,
	  /** File type in MIME format, for example, `text/plain`. */
		fileType: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1PersonalDetails(
	  /** True if the entry is starred by the user; false otherwise. */
		starred: Option[Boolean] = None,
	  /** Set if the entry is starred; unset otherwise. */
		starTime: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1ListEntriesResponse(
	  /** Entry details. */
		entries: Option[List[Schema.GoogleCloudDatacatalogV1Entry]] = None,
	  /** Pagination token of the next results page. Empty if there are no more items in results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1ModifyEntryOverviewRequest(
	  /** Required. The new value for the Entry Overview. */
		entryOverview: Option[Schema.GoogleCloudDatacatalogV1EntryOverview] = None
	)
	
	case class GoogleCloudDatacatalogV1ModifyEntryContactsRequest(
	  /** Required. The new value for the Contacts. */
		contacts: Option[Schema.GoogleCloudDatacatalogV1Contacts] = None
	)
	
	object GoogleCloudDatacatalogV1TagTemplate {
		enum DataplexTransferStatusEnum extends Enum[DataplexTransferStatusEnum] { case DATAPLEX_TRANSFER_STATUS_UNSPECIFIED, MIGRATED, TRANSFERRED }
	}
	case class GoogleCloudDatacatalogV1TagTemplate(
	  /** Identifier. The resource name of the tag template in URL format. Note: The tag template itself and its child resources might not be stored in the location specified in its name. */
		name: Option[String] = None,
	  /** Display name for this template. Defaults to an empty string. The name must contain only Unicode letters, numbers (0-9), underscores (_), dashes (-), spaces ( ), and can't start or end with spaces. The maximum length is 200 characters. */
		displayName: Option[String] = None,
	  /** Indicates whether tags created with this template are public. Public tags do not require tag template access to appear in ListTags API response. Additionally, you can search for a public tag by value with a simple search query in addition to using a ``tag:`` predicate. */
		isPubliclyReadable: Option[Boolean] = None,
	  /** Required. Map of tag template field IDs to the settings for the field. This map is an exhaustive list of the allowed fields. The map must contain at least one field and at most 500 fields. The keys to this map are tag template field IDs. The IDs have the following limitations: &#42; Can contain uppercase and lowercase letters, numbers (0-9) and underscores (_). &#42; Must be at least 1 character and at most 64 characters long. &#42; Must start with a letter or underscore. */
		fields: Option[Map[String, Schema.GoogleCloudDatacatalogV1TagTemplateField]] = None,
	  /** Optional. Transfer status of the TagTemplate */
		dataplexTransferStatus: Option[Schema.GoogleCloudDatacatalogV1TagTemplate.DataplexTransferStatusEnum] = None
	)
	
	case class GoogleCloudDatacatalogV1TagTemplateField(
	  /** Identifier. The resource name of the tag template field in URL format. Example: `projects/{PROJECT_ID}/locations/{LOCATION}/tagTemplates/{TAG_TEMPLATE}/fields/{FIELD}` Note: The tag template field itself might not be stored in the location specified in its name. The name must contain only letters (a-z, A-Z), numbers (0-9), or underscores (_), and must start with a letter or underscore. The maximum length is 64 characters. */
		name: Option[String] = None,
	  /** The display name for this field. Defaults to an empty string. The name must contain only Unicode letters, numbers (0-9), underscores (_), dashes (-), spaces ( ), and can't start or end with spaces. The maximum length is 200 characters. */
		displayName: Option[String] = None,
	  /** Required. The type of value this tag field can contain. */
		`type`: Option[Schema.GoogleCloudDatacatalogV1FieldType] = None,
	  /** If true, this field is required. Defaults to false. */
		isRequired: Option[Boolean] = None,
	  /** The description for this field. Defaults to an empty string. */
		description: Option[String] = None,
	  /** The order of this field with respect to other fields in this tag template. For example, a higher value can indicate a more important field. The value can be negative. Multiple fields can have the same order and field orders within a tag don't have to be sequential. */
		order: Option[Int] = None
	)
	
	object GoogleCloudDatacatalogV1FieldType {
		enum PrimitiveTypeEnum extends Enum[PrimitiveTypeEnum] { case PRIMITIVE_TYPE_UNSPECIFIED, DOUBLE, STRING, BOOL, TIMESTAMP, RICHTEXT }
	}
	case class GoogleCloudDatacatalogV1FieldType(
	  /** Primitive types, such as string, boolean, etc. */
		primitiveType: Option[Schema.GoogleCloudDatacatalogV1FieldType.PrimitiveTypeEnum] = None,
	  /** An enum type. */
		enumType: Option[Schema.GoogleCloudDatacatalogV1FieldTypeEnumType] = None
	)
	
	case class GoogleCloudDatacatalogV1FieldTypeEnumType(
	  /** The set of allowed values for this enum. This set must not be empty and can include up to 100 allowed values. The display names of the values in this set must not be empty and must be case-insensitively unique within this set. The order of items in this set is preserved. This field can be used to create, remove, and reorder enum values. To rename enum values, use the `RenameTagTemplateFieldEnumValue` method. */
		allowedValues: Option[List[Schema.GoogleCloudDatacatalogV1FieldTypeEnumTypeEnumValue]] = None
	)
	
	case class GoogleCloudDatacatalogV1FieldTypeEnumTypeEnumValue(
	  /** Required. The display name of the enum value. Must not be an empty string. The name must contain only Unicode letters, numbers (0-9), underscores (_), dashes (-), spaces ( ), and can't start or end with spaces. The maximum length is 200 characters. */
		displayName: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1RenameTagTemplateFieldRequest(
	  /** Required. The new ID of this tag template field. For example, `my_new_field`. */
		newTagTemplateFieldId: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1RenameTagTemplateFieldEnumValueRequest(
	  /** Required. The new display name of the enum value. For example, `my_new_enum_value`. */
		newEnumValueDisplayName: Option[String] = None
	)
	
	object GoogleCloudDatacatalogV1Tag {
		enum DataplexTransferStatusEnum extends Enum[DataplexTransferStatusEnum] { case DATAPLEX_TRANSFER_STATUS_UNSPECIFIED, MIGRATED, TRANSFERRED }
	}
	case class GoogleCloudDatacatalogV1Tag(
	  /** Identifier. The resource name of the tag in URL format where tag ID is a system-generated identifier. Note: The tag itself might not be stored in the location specified in its name. */
		name: Option[String] = None,
	  /** Required. The resource name of the tag template this tag uses. Example: `projects/{PROJECT_ID}/locations/{LOCATION}/tagTemplates/{TAG_TEMPLATE_ID}` This field cannot be modified after creation. */
		template: Option[String] = None,
	  /** Output only. The display name of the tag template. */
		templateDisplayName: Option[String] = None,
	  /** Resources like entry can have schemas associated with them. This scope allows you to attach tags to an individual column based on that schema. To attach a tag to a nested column, separate column names with a dot (`.`). Example: `column.nested_column`. */
		column: Option[String] = None,
	  /** Required. Maps the ID of a tag field to its value and additional information about that field. Tag template defines valid field IDs. A tag must have at least 1 field and at most 500 fields. */
		fields: Option[Map[String, Schema.GoogleCloudDatacatalogV1TagField]] = None,
	  /** Output only. Denotes the transfer status of the Tag Template. */
		dataplexTransferStatus: Option[Schema.GoogleCloudDatacatalogV1Tag.DataplexTransferStatusEnum] = None
	)
	
	case class GoogleCloudDatacatalogV1TagField(
	  /** Output only. The display name of this field. */
		displayName: Option[String] = None,
	  /** The value of a tag field with a double type. */
		doubleValue: Option[BigDecimal] = None,
	  /** The value of a tag field with a string type. The maximum length is 2000 UTF-8 characters. */
		stringValue: Option[String] = None,
	  /** The value of a tag field with a boolean type. */
		boolValue: Option[Boolean] = None,
	  /** The value of a tag field with a timestamp type. */
		timestampValue: Option[String] = None,
	  /** The value of a tag field with an enum type. This value must be one of the allowed values listed in this enum. */
		enumValue: Option[Schema.GoogleCloudDatacatalogV1TagFieldEnumValue] = None,
	  /** The value of a tag field with a rich text type. The maximum length is 10 MiB as this value holds HTML descriptions including encoded images. The maximum length of the text without images is 100 KiB. */
		richtextValue: Option[String] = None,
	  /** Output only. The order of this field with respect to other fields in this tag. Can be set by Tag. For example, a higher value can indicate a more important field. The value can be negative. Multiple fields can have the same order, and field orders within a tag don't have to be sequential. */
		order: Option[Int] = None
	)
	
	case class GoogleCloudDatacatalogV1TagFieldEnumValue(
	  /** The display name of the enum value. */
		displayName: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1ListTagsResponse(
	  /** Tag details. */
		tags: Option[List[Schema.GoogleCloudDatacatalogV1Tag]] = None,
	  /** Pagination token of the next results page. Empty if there are no more items in results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1ReconcileTagsRequest(
	  /** Required. The name of the tag template, which is used for reconciliation. */
		tagTemplate: Option[String] = None,
	  /** If set to `true`, deletes entry tags related to a tag template not listed in the tags source from an entry. If set to `false`, unlisted tags are retained. */
		forceDeleteMissing: Option[Boolean] = None,
	  /** A list of tags to apply to an entry. A tag can specify a tag template, which must be the template specified in the `ReconcileTagsRequest`. The sole entry and each of its columns must be mentioned at most once. */
		tags: Option[List[Schema.GoogleCloudDatacatalogV1Tag]] = None
	)
	
	case class GoogleCloudDatacatalogV1StarEntryRequest(
	
	)
	
	case class GoogleCloudDatacatalogV1StarEntryResponse(
	
	)
	
	case class GoogleCloudDatacatalogV1UnstarEntryRequest(
	
	)
	
	case class GoogleCloudDatacatalogV1UnstarEntryResponse(
	
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
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class GoogleCloudDatacatalogV1ImportEntriesRequest(
	  /** Path to a Cloud Storage bucket that contains a dump ready for ingestion. */
		gcsBucketPath: Option[String] = None,
	  /** Optional. (Optional) Dataplex task job id, if specified will be used as part of ImportEntries LRO ID */
		jobId: Option[String] = None
	)
	
	object GoogleCloudDatacatalogV1SetConfigRequest {
		enum TagTemplateMigrationEnum extends Enum[TagTemplateMigrationEnum] { case TAG_TEMPLATE_MIGRATION_UNSPECIFIED, TAG_TEMPLATE_MIGRATION_ENABLED, TAG_TEMPLATE_MIGRATION_DISABLED }
		enum CatalogUiExperienceEnum extends Enum[CatalogUiExperienceEnum] { case CATALOG_UI_EXPERIENCE_UNSPECIFIED, CATALOG_UI_EXPERIENCE_ENABLED, CATALOG_UI_EXPERIENCE_DISABLED }
	}
	case class GoogleCloudDatacatalogV1SetConfigRequest(
	  /** Opt-in status for the migration of Tag Templates to Dataplex. */
		tagTemplateMigration: Option[Schema.GoogleCloudDatacatalogV1SetConfigRequest.TagTemplateMigrationEnum] = None,
	  /** Opt-in status for the UI switch to Dataplex. */
		catalogUiExperience: Option[Schema.GoogleCloudDatacatalogV1SetConfigRequest.CatalogUiExperienceEnum] = None
	)
	
	object GoogleCloudDatacatalogV1MigrationConfig {
		enum TagTemplateMigrationEnum extends Enum[TagTemplateMigrationEnum] { case TAG_TEMPLATE_MIGRATION_UNSPECIFIED, TAG_TEMPLATE_MIGRATION_ENABLED, TAG_TEMPLATE_MIGRATION_DISABLED }
		enum CatalogUiExperienceEnum extends Enum[CatalogUiExperienceEnum] { case CATALOG_UI_EXPERIENCE_UNSPECIFIED, CATALOG_UI_EXPERIENCE_ENABLED, CATALOG_UI_EXPERIENCE_DISABLED }
	}
	case class GoogleCloudDatacatalogV1MigrationConfig(
	  /** Opt-in status for the migration of Tag Templates to Dataplex. */
		tagTemplateMigration: Option[Schema.GoogleCloudDatacatalogV1MigrationConfig.TagTemplateMigrationEnum] = None,
	  /** Opt-in status for the UI switch to Dataplex. */
		catalogUiExperience: Option[Schema.GoogleCloudDatacatalogV1MigrationConfig.CatalogUiExperienceEnum] = None
	)
	
	case class GoogleCloudDatacatalogV1OrganizationConfig(
	  /** Map of organizations and project resource names and their configuration. The format for the map keys is `organizations/{organizationId}` or `projects/{projectId}`. */
		config: Option[Map[String, Schema.GoogleCloudDatacatalogV1MigrationConfig]] = None
	)
	
	object GoogleCloudDatacatalogV1Taxonomy {
		enum ActivatedPolicyTypesEnum extends Enum[ActivatedPolicyTypesEnum] { case POLICY_TYPE_UNSPECIFIED, FINE_GRAINED_ACCESS_CONTROL }
	}
	case class GoogleCloudDatacatalogV1Taxonomy(
	  /** Identifier. Resource name of this taxonomy in URL format. Note: Policy tag manager generates unique taxonomy IDs. */
		name: Option[String] = None,
	  /** Required. User-defined name of this taxonomy. The name can't start or end with spaces, must contain only Unicode letters, numbers, underscores, dashes, and spaces, and be at most 200 bytes long when encoded in UTF-8. The taxonomy display name must be unique within an organization. */
		displayName: Option[String] = None,
	  /** Optional. Description of this taxonomy. If not set, defaults to empty. The description must contain only Unicode characters, tabs, newlines, carriage returns, and page breaks, and be at most 2000 bytes long when encoded in UTF-8. */
		description: Option[String] = None,
	  /** Output only. Number of policy tags in this taxonomy. */
		policyTagCount: Option[Int] = None,
	  /** Output only. Creation and modification timestamps of this taxonomy. */
		taxonomyTimestamps: Option[Schema.GoogleCloudDatacatalogV1SystemTimestamps] = None,
	  /** Optional. A list of policy types that are activated for this taxonomy. If not set, defaults to an empty list. */
		activatedPolicyTypes: Option[List[Schema.GoogleCloudDatacatalogV1Taxonomy.ActivatedPolicyTypesEnum]] = None,
	  /** Output only. Identity of the service which owns the Taxonomy. This field is only populated when the taxonomy is created by a Google Cloud service. Currently only 'DATAPLEX' is supported. */
		service: Option[Schema.GoogleCloudDatacatalogV1TaxonomyService] = None
	)
	
	object GoogleCloudDatacatalogV1TaxonomyService {
		enum NameEnum extends Enum[NameEnum] { case MANAGING_SYSTEM_UNSPECIFIED, MANAGING_SYSTEM_DATAPLEX, MANAGING_SYSTEM_OTHER }
	}
	case class GoogleCloudDatacatalogV1TaxonomyService(
	  /** The Google Cloud service name. */
		name: Option[Schema.GoogleCloudDatacatalogV1TaxonomyService.NameEnum] = None,
	  /** The service agent for the service. */
		identity: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1ListTaxonomiesResponse(
	  /** Taxonomies that the project contains. */
		taxonomies: Option[List[Schema.GoogleCloudDatacatalogV1Taxonomy]] = None,
	  /** Pagination token of the next results page. Empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1PolicyTag(
	  /** Identifier. Resource name of this policy tag in the URL format. The policy tag manager generates unique taxonomy IDs and policy tag IDs. */
		name: Option[String] = None,
	  /** Required. User-defined name of this policy tag. The name can't start or end with spaces and must be unique within the parent taxonomy, contain only Unicode letters, numbers, underscores, dashes and spaces, and be at most 200 bytes long when encoded in UTF-8. */
		displayName: Option[String] = None,
	  /** Description of this policy tag. If not set, defaults to empty. The description must contain only Unicode characters, tabs, newlines, carriage returns and page breaks, and be at most 2000 bytes long when encoded in UTF-8. */
		description: Option[String] = None,
	  /** Resource name of this policy tag's parent policy tag. If empty, this is a top level tag. If not set, defaults to an empty string. For example, for the "LatLong" policy tag in the example above, this field contains the resource name of the "Geolocation" policy tag, and, for "Geolocation", this field is empty. */
		parentPolicyTag: Option[String] = None,
	  /** Output only. Resource names of child policy tags of this policy tag. */
		childPolicyTags: Option[List[String]] = None
	)
	
	case class GoogleCloudDatacatalogV1ListPolicyTagsResponse(
	  /** The policy tags that belong to the taxonomy. */
		policyTags: Option[List[Schema.GoogleCloudDatacatalogV1PolicyTag]] = None,
	  /** Pagination token of the next results page. Empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1ReplaceTaxonomyRequest(
	  /** Required. Taxonomy to update along with its child policy tags. */
		serializedTaxonomy: Option[Schema.GoogleCloudDatacatalogV1SerializedTaxonomy] = None
	)
	
	object GoogleCloudDatacatalogV1SerializedTaxonomy {
		enum ActivatedPolicyTypesEnum extends Enum[ActivatedPolicyTypesEnum] { case POLICY_TYPE_UNSPECIFIED, FINE_GRAINED_ACCESS_CONTROL }
	}
	case class GoogleCloudDatacatalogV1SerializedTaxonomy(
	  /** Required. Display name of the taxonomy. At most 200 bytes when encoded in UTF-8. */
		displayName: Option[String] = None,
	  /** Description of the serialized taxonomy. At most 2000 bytes when encoded in UTF-8. If not set, defaults to an empty description. */
		description: Option[String] = None,
	  /** Top level policy tags associated with the taxonomy, if any. */
		policyTags: Option[List[Schema.GoogleCloudDatacatalogV1SerializedPolicyTag]] = None,
	  /** A list of policy types that are activated per taxonomy. */
		activatedPolicyTypes: Option[List[Schema.GoogleCloudDatacatalogV1SerializedTaxonomy.ActivatedPolicyTypesEnum]] = None
	)
	
	case class GoogleCloudDatacatalogV1SerializedPolicyTag(
	  /** Resource name of the policy tag. This field is ignored when calling `ImportTaxonomies`. */
		policyTag: Option[String] = None,
	  /** Required. Display name of the policy tag. At most 200 bytes when encoded in UTF-8. */
		displayName: Option[String] = None,
	  /** Description of the serialized policy tag. At most 2000 bytes when encoded in UTF-8. If not set, defaults to an empty description. */
		description: Option[String] = None,
	  /** Children of the policy tag, if any. */
		childPolicyTags: Option[List[Schema.GoogleCloudDatacatalogV1SerializedPolicyTag]] = None
	)
	
	case class GoogleCloudDatacatalogV1ImportTaxonomiesRequest(
	  /** Inline source taxonomy to import. */
		inlineSource: Option[Schema.GoogleCloudDatacatalogV1InlineSource] = None,
	  /** Cross-regional source taxonomy to import. */
		crossRegionalSource: Option[Schema.GoogleCloudDatacatalogV1CrossRegionalSource] = None
	)
	
	case class GoogleCloudDatacatalogV1InlineSource(
	  /** Required. Taxonomies to import. */
		taxonomies: Option[List[Schema.GoogleCloudDatacatalogV1SerializedTaxonomy]] = None
	)
	
	case class GoogleCloudDatacatalogV1CrossRegionalSource(
	  /** Required. The resource name of the source taxonomy to import. */
		taxonomy: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1ImportTaxonomiesResponse(
	  /** Imported taxonomies. */
		taxonomies: Option[List[Schema.GoogleCloudDatacatalogV1Taxonomy]] = None
	)
	
	case class GoogleCloudDatacatalogV1ExportTaxonomiesResponse(
	  /** List of taxonomies and policy tags as nested protocol buffers. */
		taxonomies: Option[List[Schema.GoogleCloudDatacatalogV1SerializedTaxonomy]] = None
	)
	
	object GoogleCloudDatacatalogV1ReconcileTagsMetadata {
		enum StateEnum extends Enum[StateEnum] { case RECONCILIATION_STATE_UNSPECIFIED, RECONCILIATION_QUEUED, RECONCILIATION_IN_PROGRESS, RECONCILIATION_DONE }
	}
	case class GoogleCloudDatacatalogV1ReconcileTagsMetadata(
	  /** State of the reconciliation operation. */
		state: Option[Schema.GoogleCloudDatacatalogV1ReconcileTagsMetadata.StateEnum] = None,
	  /** Maps the name of each tagged column (or empty string for a sole entry) to tagging operation status. */
		errors: Option[Map[String, Schema.Status]] = None
	)
	
	case class GoogleCloudDatacatalogV1ReconcileTagsResponse(
	  /** Number of tags created in the request. */
		createdTagsCount: Option[String] = None,
	  /** Number of tags updated in the request. */
		updatedTagsCount: Option[String] = None,
	  /** Number of tags deleted in the request. */
		deletedTagsCount: Option[String] = None
	)
	
	object GoogleCloudDatacatalogV1ImportEntriesMetadata {
		enum StateEnum extends Enum[StateEnum] { case IMPORT_STATE_UNSPECIFIED, IMPORT_QUEUED, IMPORT_IN_PROGRESS, IMPORT_DONE, IMPORT_OBSOLETE }
	}
	case class GoogleCloudDatacatalogV1ImportEntriesMetadata(
	  /** State of the import operation. */
		state: Option[Schema.GoogleCloudDatacatalogV1ImportEntriesMetadata.StateEnum] = None,
	  /** Partial errors that are encountered during the ImportEntries operation. There is no guarantee that all the encountered errors are reported. However, if no errors are reported, it means that no errors were encountered. */
		errors: Option[List[Schema.Status]] = None
	)
	
	case class GoogleCloudDatacatalogV1ImportEntriesResponse(
	  /** Cumulative number of entries created and entries updated as a result of import operation. */
		upsertedEntriesCount: Option[String] = None,
	  /** Number of entries deleted as a result of import operation. */
		deletedEntriesCount: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogV1DumpItem(
	  /** Entry and its tags. */
		taggedEntry: Option[Schema.GoogleCloudDatacatalogV1TaggedEntry] = None
	)
	
	case class GoogleCloudDatacatalogV1TaggedEntry(
	  /** Non-encrypted Data Catalog v1 Entry. */
		v1Entry: Option[Schema.GoogleCloudDatacatalogV1Entry] = None,
	  /** Optional. Tags that should be ingested into the Data Catalog. Caller should populate template name, column and fields. */
		presentTags: Option[List[Schema.GoogleCloudDatacatalogV1Tag]] = None,
	  /** Optional. Tags that should be deleted from the Data Catalog. Caller should populate template name and column only. */
		absentTags: Option[List[Schema.GoogleCloudDatacatalogV1Tag]] = None
	)
}
