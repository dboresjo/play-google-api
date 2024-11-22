package com.boresjo.play.api.google.datastore

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
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
	
	case class GoogleDatastoreAdminV1ExportEntitiesRequest(
	  /** Client-assigned labels. */
		labels: Option[Map[String, String]] = None,
	  /** Description of what data from the project is included in the export. */
		entityFilter: Option[Schema.GoogleDatastoreAdminV1EntityFilter] = None,
	  /** Required. Location for the export metadata and data files. The full resource URL of the external storage location. Currently, only Google Cloud Storage is supported. So output_url_prefix should be of the form: `gs://BUCKET_NAME[/NAMESPACE_PATH]`, where `BUCKET_NAME` is the name of the Cloud Storage bucket and `NAMESPACE_PATH` is an optional Cloud Storage namespace path (this is not a Cloud Datastore namespace). For more information about Cloud Storage namespace paths, see [Object name considerations](https://cloud.google.com/storage/docs/naming#object-considerations). The resulting files will be nested deeper than the specified URL prefix. The final output URL will be provided in the google.datastore.admin.v1.ExportEntitiesResponse.output_url field. That value should be used for subsequent ImportEntities operations. By nesting the data files deeper, the same Cloud Storage bucket can be used in multiple ExportEntities operations without conflict. */
		outputUrlPrefix: Option[String] = None
	)
	
	case class GoogleDatastoreAdminV1EntityFilter(
	  /** If empty, then this represents all kinds. */
		kinds: Option[List[String]] = None,
	  /** An empty list represents all namespaces. This is the preferred usage for projects that don't use namespaces. An empty string element represents the default namespace. This should be used if the project has data in non-default namespaces, but doesn't want to include them. Each namespace in this list must be unique. */
		namespaceIds: Option[List[String]] = None
	)
	
	case class GoogleDatastoreAdminV1ImportEntitiesRequest(
	  /** Client-assigned labels. */
		labels: Option[Map[String, String]] = None,
	  /** Required. The full resource URL of the external storage location. Currently, only Google Cloud Storage is supported. So input_url should be of the form: `gs://BUCKET_NAME[/NAMESPACE_PATH]/OVERALL_EXPORT_METADATA_FILE`, where `BUCKET_NAME` is the name of the Cloud Storage bucket, `NAMESPACE_PATH` is an optional Cloud Storage namespace path (this is not a Cloud Datastore namespace), and `OVERALL_EXPORT_METADATA_FILE` is the metadata file written by the ExportEntities operation. For more information about Cloud Storage namespace paths, see [Object name considerations](https://cloud.google.com/storage/docs/naming#object-considerations). For more information, see google.datastore.admin.v1.ExportEntitiesResponse.output_url. */
		inputUrl: Option[String] = None,
	  /** Optionally specify which kinds/namespaces are to be imported. If provided, the list must be a subset of the EntityFilter used in creating the export, otherwise a FAILED_PRECONDITION error will be returned. If no filter is specified then all entities from the export are imported. */
		entityFilter: Option[Schema.GoogleDatastoreAdminV1EntityFilter] = None
	)
	
	object GoogleDatastoreAdminV1Index {
		enum AncestorEnum extends Enum[AncestorEnum] { case ANCESTOR_MODE_UNSPECIFIED, NONE, ALL_ANCESTORS }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY, DELETING, ERROR }
	}
	case class GoogleDatastoreAdminV1Index(
	  /** Output only. Project ID. */
		projectId: Option[String] = None,
	  /** Output only. The resource ID of the index. */
		indexId: Option[String] = None,
	  /** Required. The entity kind to which this index applies. */
		kind: Option[String] = None,
	  /** Required. The index's ancestor mode. Must not be ANCESTOR_MODE_UNSPECIFIED. */
		ancestor: Option[Schema.GoogleDatastoreAdminV1Index.AncestorEnum] = None,
	  /** Required. An ordered sequence of property names and their index attributes. Requires: &#42; A maximum of 100 properties. */
		properties: Option[List[Schema.GoogleDatastoreAdminV1IndexedProperty]] = None,
	  /** Output only. The state of the index. */
		state: Option[Schema.GoogleDatastoreAdminV1Index.StateEnum] = None
	)
	
	object GoogleDatastoreAdminV1IndexedProperty {
		enum DirectionEnum extends Enum[DirectionEnum] { case DIRECTION_UNSPECIFIED, ASCENDING, DESCENDING }
	}
	case class GoogleDatastoreAdminV1IndexedProperty(
	  /** Required. The property name to index. */
		name: Option[String] = None,
	  /** Required. The indexed property's direction. Must not be DIRECTION_UNSPECIFIED. */
		direction: Option[Schema.GoogleDatastoreAdminV1IndexedProperty.DirectionEnum] = None
	)
	
	case class GoogleDatastoreAdminV1ListIndexesResponse(
	  /** The indexes. */
		indexes: Option[List[Schema.GoogleDatastoreAdminV1Index]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class LookupRequest(
	  /** The ID of the database against which to make the request. '(default)' is not allowed; please use empty string '' to refer the default database. */
		databaseId: Option[String] = None,
	  /** The options for this lookup request. */
		readOptions: Option[Schema.ReadOptions] = None,
	  /** Required. Keys of entities to look up. */
		keys: Option[List[Schema.Key]] = None,
	  /** The properties to return. Defaults to returning all properties. If this field is set and an entity has a property not referenced in the mask, it will be absent from LookupResponse.found.entity.properties. The entity's key is always returned. */
		propertyMask: Option[Schema.PropertyMask] = None
	)
	
	object ReadOptions {
		enum ReadConsistencyEnum extends Enum[ReadConsistencyEnum] { case READ_CONSISTENCY_UNSPECIFIED, STRONG, EVENTUAL }
	}
	case class ReadOptions(
	  /** The non-transactional read consistency to use. */
		readConsistency: Option[Schema.ReadOptions.ReadConsistencyEnum] = None,
	  /** The identifier of the transaction in which to read. A transaction identifier is returned by a call to Datastore.BeginTransaction. */
		transaction: Option[String] = None,
	  /** Options for beginning a new transaction for this request. The new transaction identifier will be returned in the corresponding response as either LookupResponse.transaction or RunQueryResponse.transaction. */
		newTransaction: Option[Schema.TransactionOptions] = None,
	  /** Reads entities as they were at the given time. This value is only supported for Cloud Firestore in Datastore mode. This must be a microsecond precision timestamp within the past one hour, or if Point-in-Time Recovery is enabled, can additionally be a whole minute timestamp within the past 7 days. */
		readTime: Option[String] = None
	)
	
	case class TransactionOptions(
	  /** The transaction should allow both reads and writes. */
		readWrite: Option[Schema.ReadWrite] = None,
	  /** The transaction should only allow reads. */
		readOnly: Option[Schema.ReadOnly] = None
	)
	
	case class ReadWrite(
	  /** The transaction identifier of the transaction being retried. */
		previousTransaction: Option[String] = None
	)
	
	case class ReadOnly(
	  /** Reads entities at the given time. This must be a microsecond precision timestamp within the past one hour, or if Point-in-Time Recovery is enabled, can additionally be a whole minute timestamp within the past 7 days. */
		readTime: Option[String] = None
	)
	
	case class Key(
	  /** Entities are partitioned into subsets, currently identified by a project ID and namespace ID. Queries are scoped to a single partition. */
		partitionId: Option[Schema.PartitionId] = None,
	  /** The entity path. An entity path consists of one or more elements composed of a kind and a string or numerical identifier, which identify entities. The first element identifies a _root entity_, the second element identifies a _child_ of the root entity, the third element identifies a child of the second entity, and so forth. The entities identified by all prefixes of the path are called the element's _ancestors_. An entity path is always fully complete: &#42;all&#42; of the entity's ancestors are required to be in the path along with the entity identifier itself. The only exception is that in some documented cases, the identifier in the last path element (for the entity) itself may be omitted. For example, the last path element of the key of `Mutation.insert` may have no identifier. A path can never be empty, and a path can have at most 100 elements. */
		path: Option[List[Schema.PathElement]] = None
	)
	
	case class PartitionId(
	  /** The ID of the project to which the entities belong. */
		projectId: Option[String] = None,
	  /** If not empty, the ID of the database to which the entities belong. */
		databaseId: Option[String] = None,
	  /** If not empty, the ID of the namespace to which the entities belong. */
		namespaceId: Option[String] = None
	)
	
	case class PathElement(
	  /** The kind of the entity. A kind matching regex `__.&#42;__` is reserved/read-only. A kind must not contain more than 1500 bytes when UTF-8 encoded. Cannot be `""`. Must be valid UTF-8 bytes. Legacy values that are not valid UTF-8 are encoded as `__bytes__` where `` is the base-64 encoding of the bytes. */
		kind: Option[String] = None,
	  /** The auto-allocated ID of the entity. Never equal to zero. Values less than zero are discouraged and may not be supported in the future. */
		id: Option[String] = None,
	  /** The name of the entity. A name matching regex `__.&#42;__` is reserved/read-only. A name must not be more than 1500 bytes when UTF-8 encoded. Cannot be `""`. Must be valid UTF-8 bytes. Legacy values that are not valid UTF-8 are encoded as `__bytes__` where `` is the base-64 encoding of the bytes. */
		name: Option[String] = None
	)
	
	case class PropertyMask(
	  /** The paths to the properties covered by this mask. A path is a list of property names separated by dots (`.`), for example `foo.bar` means the property `bar` inside the entity property `foo` inside the entity associated with this path. If a property name contains a dot `.` or a backslash `\`, then that name must be escaped. A path must not be empty, and may not reference a value inside an array value. */
		paths: Option[List[String]] = None
	)
	
	case class LookupResponse(
	  /** Entities found as `ResultType.FULL` entities. The order of results in this field is undefined and has no relation to the order of the keys in the input. */
		found: Option[List[Schema.EntityResult]] = None,
	  /** Entities not found as `ResultType.KEY_ONLY` entities. The order of results in this field is undefined and has no relation to the order of the keys in the input. */
		missing: Option[List[Schema.EntityResult]] = None,
	  /** A list of keys that were not looked up due to resource constraints. The order of results in this field is undefined and has no relation to the order of the keys in the input. */
		deferred: Option[List[Schema.Key]] = None,
	  /** The identifier of the transaction that was started as part of this Lookup request. Set only when ReadOptions.new_transaction was set in LookupRequest.read_options. */
		transaction: Option[String] = None,
	  /** The time at which these entities were read or found missing. */
		readTime: Option[String] = None
	)
	
	case class EntityResult(
	  /** The resulting entity. */
		entity: Option[Schema.Entity] = None,
	  /** The version of the entity, a strictly positive number that monotonically increases with changes to the entity. This field is set for `FULL` entity results. For missing entities in `LookupResponse`, this is the version of the snapshot that was used to look up the entity, and it is always set except for eventually consistent reads. */
		version: Option[String] = None,
	  /** The time at which the entity was created. This field is set for `FULL` entity results. If this entity is missing, this field will not be set. */
		createTime: Option[String] = None,
	  /** The time at which the entity was last changed. This field is set for `FULL` entity results. If this entity is missing, this field will not be set. */
		updateTime: Option[String] = None,
	  /** A cursor that points to the position after the result entity. Set only when the `EntityResult` is part of a `QueryResultBatch` message. */
		cursor: Option[String] = None
	)
	
	case class Entity(
	  /** The entity's key. An entity must have a key, unless otherwise documented (for example, an entity in `Value.entity_value` may have no key). An entity's kind is its key path's last element's kind, or null if it has no key. */
		key: Option[Schema.Key] = None,
	  /** The entity's properties. The map's keys are property names. A property name matching regex `__.&#42;__` is reserved. A reserved property name is forbidden in certain documented contexts. The map keys, represented as UTF-8, must not exceed 1,500 bytes and cannot be empty. */
		properties: Option[Map[String, Schema.Value]] = None
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
	  /** A timestamp value. When stored in the Datastore, precise only to microseconds; any additional precision is rounded down. */
		timestampValue: Option[String] = None,
	  /** A key value. */
		keyValue: Option[Schema.Key] = None,
	  /** A UTF-8 encoded string value. When `exclude_from_indexes` is false (it is indexed) , may have at most 1500 bytes. Otherwise, may be set to at most 1,000,000 bytes. */
		stringValue: Option[String] = None,
	  /** A blob value. May have at most 1,000,000 bytes. When `exclude_from_indexes` is false, may have at most 1500 bytes. In JSON requests, must be base64-encoded. */
		blobValue: Option[String] = None,
	  /** A geo point value representing a point on the surface of Earth. */
		geoPointValue: Option[Schema.LatLng] = None,
	  /** An entity value. - May have no key. - May have a key with an incomplete key path. - May have a reserved/read-only key. */
		entityValue: Option[Schema.Entity] = None,
	  /** An array value. Cannot contain another array value. A `Value` instance that sets field `array_value` must not set fields `meaning` or `exclude_from_indexes`. */
		arrayValue: Option[Schema.ArrayValue] = None,
	  /** The `meaning` field should only be populated for backwards compatibility. */
		meaning: Option[Int] = None,
	  /** If the value should be excluded from all indexes including those defined explicitly. */
		excludeFromIndexes: Option[Boolean] = None
	)
	
	case class LatLng(
	  /** The latitude in degrees. It must be in the range [-90.0, +90.0]. */
		latitude: Option[BigDecimal] = None,
	  /** The longitude in degrees. It must be in the range [-180.0, +180.0]. */
		longitude: Option[BigDecimal] = None
	)
	
	case class ArrayValue(
	  /** Values in the array. The order of values in an array is preserved as long as all values have identical settings for 'exclude_from_indexes'. */
		values: Option[List[Schema.Value]] = None
	)
	
	case class RunQueryRequest(
	  /** The ID of the database against which to make the request. '(default)' is not allowed; please use empty string '' to refer the default database. */
		databaseId: Option[String] = None,
	  /** Entities are partitioned into subsets, identified by a partition ID. Queries are scoped to a single partition. This partition ID is normalized with the standard default context partition ID. */
		partitionId: Option[Schema.PartitionId] = None,
	  /** The options for this query. */
		readOptions: Option[Schema.ReadOptions] = None,
	  /** The query to run. */
		query: Option[Schema.Query] = None,
	  /** The GQL query to run. This query must be a non-aggregation query. */
		gqlQuery: Option[Schema.GqlQuery] = None,
	  /** The properties to return. This field must not be set for a projection query. See LookupRequest.property_mask. */
		propertyMask: Option[Schema.PropertyMask] = None,
	  /** Optional. Explain options for the query. If set, additional query statistics will be returned. If not, only query results will be returned. */
		explainOptions: Option[Schema.ExplainOptions] = None
	)
	
	case class Query(
	  /** The projection to return. Defaults to returning all properties. */
		projection: Option[List[Schema.Projection]] = None,
	  /** The kinds to query (if empty, returns entities of all kinds). Currently at most 1 kind may be specified. */
		kind: Option[List[Schema.KindExpression]] = None,
	  /** The filter to apply. */
		filter: Option[Schema.Filter] = None,
	  /** The order to apply to the query results (if empty, order is unspecified). */
		order: Option[List[Schema.PropertyOrder]] = None,
	  /** The properties to make distinct. The query results will contain the first result for each distinct combination of values for the given properties (if empty, all results are returned). Requires: &#42; If `order` is specified, the set of distinct on properties must appear before the non-distinct on properties in `order`. */
		distinctOn: Option[List[Schema.PropertyReference]] = None,
	  /** A starting point for the query results. Query cursors are returned in query result batches and [can only be used to continue the same query](https://cloud.google.com/datastore/docs/concepts/queries#cursors_limits_and_offsets). */
		startCursor: Option[String] = None,
	  /** An ending point for the query results. Query cursors are returned in query result batches and [can only be used to limit the same query](https://cloud.google.com/datastore/docs/concepts/queries#cursors_limits_and_offsets). */
		endCursor: Option[String] = None,
	  /** The number of results to skip. Applies before limit, but after all other constraints. Optional. Must be >= 0 if specified. */
		offset: Option[Int] = None,
	  /** The maximum number of results to return. Applies after all other constraints. Optional. Unspecified is interpreted as no limit. Must be >= 0 if specified. */
		limit: Option[Int] = None,
	  /** Optional. A potential Nearest Neighbors Search. Applies after all other filters and ordering. Finds the closest vector embeddings to the given query vector. */
		findNearest: Option[Schema.FindNearest] = None
	)
	
	case class Projection(
	  /** The property to project. */
		property: Option[Schema.PropertyReference] = None
	)
	
	case class PropertyReference(
	  /** A reference to a property. Requires: &#42; MUST be a dot-delimited (`.`) string of segments, where each segment conforms to entity property name limitations. */
		name: Option[String] = None
	)
	
	case class KindExpression(
	  /** The name of the kind. */
		name: Option[String] = None
	)
	
	case class Filter(
	  /** A composite filter. */
		compositeFilter: Option[Schema.CompositeFilter] = None,
	  /** A filter on a property. */
		propertyFilter: Option[Schema.PropertyFilter] = None
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
	
	object PropertyFilter {
		enum OpEnum extends Enum[OpEnum] { case OPERATOR_UNSPECIFIED, LESS_THAN, LESS_THAN_OR_EQUAL, GREATER_THAN, GREATER_THAN_OR_EQUAL, EQUAL, IN, NOT_EQUAL, HAS_ANCESTOR, NOT_IN }
	}
	case class PropertyFilter(
	  /** The property to filter by. */
		property: Option[Schema.PropertyReference] = None,
	  /** The operator to filter by. */
		op: Option[Schema.PropertyFilter.OpEnum] = None,
	  /** The value to compare the property to. */
		value: Option[Schema.Value] = None
	)
	
	object PropertyOrder {
		enum DirectionEnum extends Enum[DirectionEnum] { case DIRECTION_UNSPECIFIED, ASCENDING, DESCENDING }
	}
	case class PropertyOrder(
	  /** The property to order by. */
		property: Option[Schema.PropertyReference] = None,
	  /** The direction to order by. Defaults to `ASCENDING`. */
		direction: Option[Schema.PropertyOrder.DirectionEnum] = None
	)
	
	object FindNearest {
		enum DistanceMeasureEnum extends Enum[DistanceMeasureEnum] { case DISTANCE_MEASURE_UNSPECIFIED, EUCLIDEAN, COSINE, DOT_PRODUCT }
	}
	case class FindNearest(
	  /** Required. An indexed vector property to search upon. Only documents which contain vectors whose dimensionality match the query_vector can be returned. */
		vectorProperty: Option[Schema.PropertyReference] = None,
	  /** Required. The query vector that we are searching on. Must be a vector of no more than 2048 dimensions. */
		queryVector: Option[Schema.Value] = None,
	  /** Required. The Distance Measure to use, required. */
		distanceMeasure: Option[Schema.FindNearest.DistanceMeasureEnum] = None,
	  /** Required. The number of nearest neighbors to return. Must be a positive integer of no more than 100. */
		limit: Option[Int] = None,
	  /** Optional. Optional name of the field to output the result of the vector distance calculation. Must conform to entity property limitations. */
		distanceResultProperty: Option[String] = None,
	  /** Optional. Option to specify a threshold for which no less similar documents will be returned. The behavior of the specified `distance_measure` will affect the meaning of the distance threshold. Since DOT_PRODUCT distances increase when the vectors are more similar, the comparison is inverted. &#42; For EUCLIDEAN, COSINE: WHERE distance <= distance_threshold &#42; For DOT_PRODUCT: WHERE distance >= distance_threshold */
		distanceThreshold: Option[BigDecimal] = None
	)
	
	case class GqlQuery(
	  /** A string of the format described [here](https://cloud.google.com/datastore/docs/apis/gql/gql_reference). */
		queryString: Option[String] = None,
	  /** When false, the query string must not contain any literals and instead must bind all values. For example, `SELECT &#42; FROM Kind WHERE a = 'string literal'` is not allowed, while `SELECT &#42; FROM Kind WHERE a = @value` is. */
		allowLiterals: Option[Boolean] = None,
	  /** For each non-reserved named binding site in the query string, there must be a named parameter with that name, but not necessarily the inverse. Key must match regex `A-Za-z_$&#42;`, must not match regex `__.&#42;__`, and must not be `""`. */
		namedBindings: Option[Map[String, Schema.GqlQueryParameter]] = None,
	  /** Numbered binding site @1 references the first numbered parameter, effectively using 1-based indexing, rather than the usual 0. For each binding site numbered i in `query_string`, there must be an i-th numbered parameter. The inverse must also be true. */
		positionalBindings: Option[List[Schema.GqlQueryParameter]] = None
	)
	
	case class GqlQueryParameter(
	  /** A value parameter. */
		value: Option[Schema.Value] = None,
	  /** A query cursor. Query cursors are returned in query result batches. */
		cursor: Option[String] = None
	)
	
	case class ExplainOptions(
	  /** Optional. Whether to execute this query. When false (the default), the query will be planned, returning only metrics from the planning stages. When true, the query will be planned and executed, returning the full query results along with both planning and execution stage metrics. */
		analyze: Option[Boolean] = None
	)
	
	case class RunQueryResponse(
	  /** A batch of query results (always present). */
		batch: Option[Schema.QueryResultBatch] = None,
	  /** The parsed form of the `GqlQuery` from the request, if it was set. */
		query: Option[Schema.Query] = None,
	  /** The identifier of the transaction that was started as part of this RunQuery request. Set only when ReadOptions.new_transaction was set in RunQueryRequest.read_options. */
		transaction: Option[String] = None,
	  /** Query explain metrics. This is only present when the RunQueryRequest.explain_options is provided, and it is sent only once with the last response in the stream. */
		explainMetrics: Option[Schema.ExplainMetrics] = None
	)
	
	object QueryResultBatch {
		enum EntityResultTypeEnum extends Enum[EntityResultTypeEnum] { case RESULT_TYPE_UNSPECIFIED, FULL, PROJECTION, KEY_ONLY }
		enum MoreResultsEnum extends Enum[MoreResultsEnum] { case MORE_RESULTS_TYPE_UNSPECIFIED, NOT_FINISHED, MORE_RESULTS_AFTER_LIMIT, MORE_RESULTS_AFTER_CURSOR, NO_MORE_RESULTS }
	}
	case class QueryResultBatch(
	  /** The number of results skipped, typically because of an offset. */
		skippedResults: Option[Int] = None,
	  /** A cursor that points to the position after the last skipped result. Will be set when `skipped_results` != 0. */
		skippedCursor: Option[String] = None,
	  /** The result type for every entity in `entity_results`. */
		entityResultType: Option[Schema.QueryResultBatch.EntityResultTypeEnum] = None,
	  /** The results for this batch. */
		entityResults: Option[List[Schema.EntityResult]] = None,
	  /** A cursor that points to the position after the last result in the batch. */
		endCursor: Option[String] = None,
	  /** The state of the query after the current batch. */
		moreResults: Option[Schema.QueryResultBatch.MoreResultsEnum] = None,
	  /** The version number of the snapshot this batch was returned from. This applies to the range of results from the query's `start_cursor` (or the beginning of the query if no cursor was given) to this batch's `end_cursor` (not the query's `end_cursor`). In a single transaction, subsequent query result batches for the same query can have a greater snapshot version number. Each batch's snapshot version is valid for all preceding batches. The value will be zero for eventually consistent queries. */
		snapshotVersion: Option[String] = None,
	  /** Read timestamp this batch was returned from. This applies to the range of results from the query's `start_cursor` (or the beginning of the query if no cursor was given) to this batch's `end_cursor` (not the query's `end_cursor`). In a single transaction, subsequent query result batches for the same query can have a greater timestamp. Each batch's read timestamp is valid for all preceding batches. This value will not be set for eventually consistent queries in Cloud Datastore. */
		readTime: Option[String] = None
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
	  /** The ID of the database against which to make the request. '(default)' is not allowed; please use empty string '' to refer the default database. */
		databaseId: Option[String] = None,
	  /** Entities are partitioned into subsets, identified by a partition ID. Queries are scoped to a single partition. This partition ID is normalized with the standard default context partition ID. */
		partitionId: Option[Schema.PartitionId] = None,
	  /** The options for this query. */
		readOptions: Option[Schema.ReadOptions] = None,
	  /** The query to run. */
		aggregationQuery: Option[Schema.AggregationQuery] = None,
	  /** The GQL query to run. This query must be an aggregation query. */
		gqlQuery: Option[Schema.GqlQuery] = None,
	  /** Optional. Explain options for the query. If set, additional query statistics will be returned. If not, only query results will be returned. */
		explainOptions: Option[Schema.ExplainOptions] = None
	)
	
	case class AggregationQuery(
	  /** Nested query for aggregation */
		nestedQuery: Option[Schema.Query] = None,
	  /** Optional. Series of aggregations to apply over the results of the `nested_query`. Requires: &#42; A minimum of one and maximum of five aggregations per query. */
		aggregations: Option[List[Schema.Aggregation]] = None
	)
	
	case class Aggregation(
	  /** Count aggregator. */
		count: Option[Schema.Count] = None,
	  /** Sum aggregator. */
		sum: Option[Schema.Sum] = None,
	  /** Average aggregator. */
		avg: Option[Schema.Avg] = None,
	  /** Optional. Optional name of the property to store the result of the aggregation. If not provided, Datastore will pick a default name following the format `property_`. For example: ``` AGGREGATE COUNT_UP_TO(1) AS count_up_to_1, COUNT_UP_TO(2), COUNT_UP_TO(3) AS count_up_to_3, COUNT(&#42;) OVER ( ... ); ``` becomes: ``` AGGREGATE COUNT_UP_TO(1) AS count_up_to_1, COUNT_UP_TO(2) AS property_1, COUNT_UP_TO(3) AS count_up_to_3, COUNT(&#42;) AS property_2 OVER ( ... ); ``` Requires: &#42; Must be unique across all aggregation aliases. &#42; Conform to entity property name limitations. */
		alias: Option[String] = None
	)
	
	case class Count(
	  /** Optional. Optional constraint on the maximum number of entities to count. This provides a way to set an upper bound on the number of entities to scan, limiting latency, and cost. Unspecified is interpreted as no bound. If a zero value is provided, a count result of zero should always be expected. High-Level Example: ``` AGGREGATE COUNT_UP_TO(1000) OVER ( SELECT &#42; FROM k ); ``` Requires: &#42; Must be non-negative when present. */
		upTo: Option[String] = None
	)
	
	case class Sum(
	  /** The property to aggregate on. */
		property: Option[Schema.PropertyReference] = None
	)
	
	case class Avg(
	  /** The property to aggregate on. */
		property: Option[Schema.PropertyReference] = None
	)
	
	case class RunAggregationQueryResponse(
	  /** A batch of aggregation results. Always present. */
		batch: Option[Schema.AggregationResultBatch] = None,
	  /** The parsed form of the `GqlQuery` from the request, if it was set. */
		query: Option[Schema.AggregationQuery] = None,
	  /** The identifier of the transaction that was started as part of this RunAggregationQuery request. Set only when ReadOptions.new_transaction was set in RunAggregationQueryRequest.read_options. */
		transaction: Option[String] = None,
	  /** Query explain metrics. This is only present when the RunAggregationQueryRequest.explain_options is provided, and it is sent only once with the last response in the stream. */
		explainMetrics: Option[Schema.ExplainMetrics] = None
	)
	
	object AggregationResultBatch {
		enum MoreResultsEnum extends Enum[MoreResultsEnum] { case MORE_RESULTS_TYPE_UNSPECIFIED, NOT_FINISHED, MORE_RESULTS_AFTER_LIMIT, MORE_RESULTS_AFTER_CURSOR, NO_MORE_RESULTS }
	}
	case class AggregationResultBatch(
	  /** The aggregation results for this batch. */
		aggregationResults: Option[List[Schema.AggregationResult]] = None,
	  /** The state of the query after the current batch. Only COUNT(&#42;) aggregations are supported in the initial launch. Therefore, expected result type is limited to `NO_MORE_RESULTS`. */
		moreResults: Option[Schema.AggregationResultBatch.MoreResultsEnum] = None,
	  /** Read timestamp this batch was returned from. In a single transaction, subsequent query result batches for the same query can have a greater timestamp. Each batch's read timestamp is valid for all preceding batches. */
		readTime: Option[String] = None
	)
	
	case class AggregationResult(
	  /** The result of the aggregation functions, ex: `COUNT(&#42;) AS total_entities`. The key is the alias assigned to the aggregation function on input and the size of this map equals the number of aggregation functions in the query. */
		aggregateProperties: Option[Map[String, Schema.Value]] = None
	)
	
	case class BeginTransactionRequest(
	  /** The ID of the database against which to make the request. '(default)' is not allowed; please use empty string '' to refer the default database. */
		databaseId: Option[String] = None,
	  /** Options for a new transaction. */
		transactionOptions: Option[Schema.TransactionOptions] = None
	)
	
	case class BeginTransactionResponse(
	  /** The transaction identifier (always present). */
		transaction: Option[String] = None
	)
	
	object CommitRequest {
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, TRANSACTIONAL, NON_TRANSACTIONAL }
	}
	case class CommitRequest(
	  /** The ID of the database against which to make the request. '(default)' is not allowed; please use empty string '' to refer the default database. */
		databaseId: Option[String] = None,
	  /** The type of commit to perform. Defaults to `TRANSACTIONAL`. */
		mode: Option[Schema.CommitRequest.ModeEnum] = None,
	  /** The identifier of the transaction associated with the commit. A transaction identifier is returned by a call to Datastore.BeginTransaction. */
		transaction: Option[String] = None,
	  /** Options for beginning a new transaction for this request. The transaction is committed when the request completes. If specified, TransactionOptions.mode must be TransactionOptions.ReadWrite. */
		singleUseTransaction: Option[Schema.TransactionOptions] = None,
	  /** The mutations to perform. When mode is `TRANSACTIONAL`, mutations affecting a single entity are applied in order. The following sequences of mutations affecting a single entity are not permitted in a single `Commit` request: - `insert` followed by `insert` - `update` followed by `insert` - `upsert` followed by `insert` - `delete` followed by `update` When mode is `NON_TRANSACTIONAL`, no two mutations may affect a single entity. */
		mutations: Option[List[Schema.Mutation]] = None
	)
	
	object Mutation {
		enum ConflictResolutionStrategyEnum extends Enum[ConflictResolutionStrategyEnum] { case STRATEGY_UNSPECIFIED, SERVER_VALUE, FAIL }
	}
	case class Mutation(
	  /** The entity to insert. The entity must not already exist. The entity key's final path element may be incomplete. */
		insert: Option[Schema.Entity] = None,
	  /** The entity to update. The entity must already exist. Must have a complete key path. */
		update: Option[Schema.Entity] = None,
	  /** The entity to upsert. The entity may or may not already exist. The entity key's final path element may be incomplete. */
		upsert: Option[Schema.Entity] = None,
	  /** The key of the entity to delete. The entity may or may not already exist. Must have a complete key path and must not be reserved/read-only. */
		delete: Option[Schema.Key] = None,
	  /** The version of the entity that this mutation is being applied to. If this does not match the current version on the server, the mutation conflicts. */
		baseVersion: Option[String] = None,
	  /** The update time of the entity that this mutation is being applied to. If this does not match the current update time on the server, the mutation conflicts. */
		updateTime: Option[String] = None,
	  /** The strategy to use when a conflict is detected. Defaults to `SERVER_VALUE`. If this is set, then `conflict_detection_strategy` must also be set. */
		conflictResolutionStrategy: Option[Schema.Mutation.ConflictResolutionStrategyEnum] = None,
	  /** The properties to write in this mutation. None of the properties in the mask may have a reserved name, except for `__key__`. This field is ignored for `delete`. If the entity already exists, only properties referenced in the mask are updated, others are left untouched. Properties referenced in the mask but not in the entity are deleted. */
		propertyMask: Option[Schema.PropertyMask] = None,
	  /** Optional. The transforms to perform on the entity. This field can be set only when the operation is `insert`, `update`, or `upsert`. If present, the transforms are be applied to the entity regardless of the property mask, in order, after the operation. */
		propertyTransforms: Option[List[Schema.PropertyTransform]] = None
	)
	
	object PropertyTransform {
		enum SetToServerValueEnum extends Enum[SetToServerValueEnum] { case SERVER_VALUE_UNSPECIFIED, REQUEST_TIME }
	}
	case class PropertyTransform(
	  /** Optional. The name of the property. Property paths (a list of property names separated by dots (`.`)) may be used to refer to properties inside entity values. For example `foo.bar` means the property `bar` inside the entity property `foo`. If a property name contains a dot `.` or a backlslash `\`, then that name must be escaped. */
		property: Option[String] = None,
	  /** Sets the property to the given server value. */
		setToServerValue: Option[Schema.PropertyTransform.SetToServerValueEnum] = None,
	  /** Adds the given value to the property's current value. This must be an integer or a double value. If the property is not an integer or double, or if the property does not yet exist, the transformation will set the property to the given value. If either of the given value or the current property value are doubles, both values will be interpreted as doubles. Double arithmetic and representation of double values follows IEEE 754 semantics. If there is positive/negative integer overflow, the property is resolved to the largest magnitude positive/negative integer. */
		increment: Option[Schema.Value] = None,
	  /** Sets the property to the maximum of its current value and the given value. This must be an integer or a double value. If the property is not an integer or double, or if the property does not yet exist, the transformation will set the property to the given value. If a maximum operation is applied where the property and the input value are of mixed types (that is - one is an integer and one is a double) the property takes on the type of the larger operand. If the operands are equivalent (e.g. 3 and 3.0), the property does not change. 0, 0.0, and -0.0 are all zero. The maximum of a zero stored value and zero input value is always the stored value. The maximum of any numeric value x and NaN is NaN. */
		maximum: Option[Schema.Value] = None,
	  /** Sets the property to the minimum of its current value and the given value. This must be an integer or a double value. If the property is not an integer or double, or if the property does not yet exist, the transformation will set the property to the input value. If a minimum operation is applied where the property and the input value are of mixed types (that is - one is an integer and one is a double) the property takes on the type of the smaller operand. If the operands are equivalent (e.g. 3 and 3.0), the property does not change. 0, 0.0, and -0.0 are all zero. The minimum of a zero stored value and zero input value is always the stored value. The minimum of any numeric value x and NaN is NaN. */
		minimum: Option[Schema.Value] = None,
	  /** Appends the given elements in order if they are not already present in the current property value. If the property is not an array, or if the property does not yet exist, it is first set to the empty array. Equivalent numbers of different types (e.g. 3L and 3.0) are considered equal when checking if a value is missing. NaN is equal to NaN, and the null value is equal to the null value. If the input contains multiple equivalent values, only the first will be considered. The corresponding transform result will be the null value. */
		appendMissingElements: Option[Schema.ArrayValue] = None,
	  /** Removes all of the given elements from the array in the property. If the property is not an array, or if the property does not yet exist, it is set to the empty array. Equivalent numbers of different types (e.g. 3L and 3.0) are considered equal when deciding whether an element should be removed. NaN is equal to NaN, and the null value is equal to the null value. This will remove all equivalent values if there are duplicates. The corresponding transform result will be the null value. */
		removeAllFromArray: Option[Schema.ArrayValue] = None
	)
	
	case class CommitResponse(
	  /** The result of performing the mutations. The i-th mutation result corresponds to the i-th mutation in the request. */
		mutationResults: Option[List[Schema.MutationResult]] = None,
	  /** The number of index entries updated during the commit, or zero if none were updated. */
		indexUpdates: Option[Int] = None,
	  /** The transaction commit timestamp. Not set for non-transactional commits. */
		commitTime: Option[String] = None
	)
	
	case class MutationResult(
	  /** The automatically allocated key. Set only when the mutation allocated a key. */
		key: Option[Schema.Key] = None,
	  /** The version of the entity on the server after processing the mutation. If the mutation doesn't change anything on the server, then the version will be the version of the current entity or, if no entity is present, a version that is strictly greater than the version of any previous entity and less than the version of any possible future entity. */
		version: Option[String] = None,
	  /** The create time of the entity. This field will not be set after a 'delete'. */
		createTime: Option[String] = None,
	  /** The update time of the entity on the server after processing the mutation. If the mutation doesn't change anything on the server, then the timestamp will be the update timestamp of the current entity. This field will not be set after a 'delete'. */
		updateTime: Option[String] = None,
	  /** Whether a conflict was detected for this mutation. Always false when a conflict detection strategy field is not set in the mutation. */
		conflictDetected: Option[Boolean] = None,
	  /** The results of applying each PropertyTransform, in the same order of the request. */
		transformResults: Option[List[Schema.Value]] = None
	)
	
	case class RollbackRequest(
	  /** The ID of the database against which to make the request. '(default)' is not allowed; please use empty string '' to refer the default database. */
		databaseId: Option[String] = None,
	  /** Required. The transaction identifier, returned by a call to Datastore.BeginTransaction. */
		transaction: Option[String] = None
	)
	
	case class RollbackResponse(
	
	)
	
	case class AllocateIdsRequest(
	  /** The ID of the database against which to make the request. '(default)' is not allowed; please use empty string '' to refer the default database. */
		databaseId: Option[String] = None,
	  /** Required. A list of keys with incomplete key paths for which to allocate IDs. No key may be reserved/read-only. */
		keys: Option[List[Schema.Key]] = None
	)
	
	case class AllocateIdsResponse(
	  /** The keys specified in the request (in the same order), each with its key path completed with a newly allocated ID. */
		keys: Option[List[Schema.Key]] = None
	)
	
	case class ReserveIdsRequest(
	  /** The ID of the database against which to make the request. '(default)' is not allowed; please use empty string '' to refer the default database. */
		databaseId: Option[String] = None,
	  /** Required. A list of keys with complete key paths whose numeric IDs should not be auto-allocated. */
		keys: Option[List[Schema.Key]] = None
	)
	
	case class ReserveIdsResponse(
	
	)
	
	case class GoogleDatastoreAdminV1beta1ExportEntitiesMetadata(
	  /** Metadata common to all Datastore Admin operations. */
		common: Option[Schema.GoogleDatastoreAdminV1beta1CommonMetadata] = None,
	  /** An estimate of the number of entities processed. */
		progressEntities: Option[Schema.GoogleDatastoreAdminV1beta1Progress] = None,
	  /** An estimate of the number of bytes processed. */
		progressBytes: Option[Schema.GoogleDatastoreAdminV1beta1Progress] = None,
	  /** Description of which entities are being exported. */
		entityFilter: Option[Schema.GoogleDatastoreAdminV1beta1EntityFilter] = None,
	  /** Location for the export metadata and data files. This will be the same value as the google.datastore.admin.v1beta1.ExportEntitiesRequest.output_url_prefix field. The final output location is provided in google.datastore.admin.v1beta1.ExportEntitiesResponse.output_url. */
		outputUrlPrefix: Option[String] = None
	)
	
	object GoogleDatastoreAdminV1beta1CommonMetadata {
		enum OperationTypeEnum extends Enum[OperationTypeEnum] { case OPERATION_TYPE_UNSPECIFIED, EXPORT_ENTITIES, IMPORT_ENTITIES }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, INITIALIZING, PROCESSING, CANCELLING, FINALIZING, SUCCESSFUL, FAILED, CANCELLED }
	}
	case class GoogleDatastoreAdminV1beta1CommonMetadata(
	  /** The time that work began on the operation. */
		startTime: Option[String] = None,
	  /** The time the operation ended, either successfully or otherwise. */
		endTime: Option[String] = None,
	  /** The type of the operation. Can be used as a filter in ListOperationsRequest. */
		operationType: Option[Schema.GoogleDatastoreAdminV1beta1CommonMetadata.OperationTypeEnum] = None,
	  /** The client-assigned labels which were provided when the operation was created. May also include additional labels. */
		labels: Option[Map[String, String]] = None,
	  /** The current state of the Operation. */
		state: Option[Schema.GoogleDatastoreAdminV1beta1CommonMetadata.StateEnum] = None
	)
	
	case class GoogleDatastoreAdminV1beta1Progress(
	  /** The amount of work that has been completed. Note that this may be greater than work_estimated. */
		workCompleted: Option[String] = None,
	  /** An estimate of how much work needs to be performed. May be zero if the work estimate is unavailable. */
		workEstimated: Option[String] = None
	)
	
	case class GoogleDatastoreAdminV1beta1EntityFilter(
	  /** If empty, then this represents all kinds. */
		kinds: Option[List[String]] = None,
	  /** An empty list represents all namespaces. This is the preferred usage for projects that don't use namespaces. An empty string element represents the default namespace. This should be used if the project has data in non-default namespaces, but doesn't want to include them. Each namespace in this list must be unique. */
		namespaceIds: Option[List[String]] = None
	)
	
	case class GoogleDatastoreAdminV1beta1ExportEntitiesResponse(
	  /** Location of the output metadata file. This can be used to begin an import into Cloud Datastore (this project or another project). See google.datastore.admin.v1beta1.ImportEntitiesRequest.input_url. Only present if the operation completed successfully. */
		outputUrl: Option[String] = None
	)
	
	case class GoogleDatastoreAdminV1beta1ImportEntitiesMetadata(
	  /** Metadata common to all Datastore Admin operations. */
		common: Option[Schema.GoogleDatastoreAdminV1beta1CommonMetadata] = None,
	  /** An estimate of the number of entities processed. */
		progressEntities: Option[Schema.GoogleDatastoreAdminV1beta1Progress] = None,
	  /** An estimate of the number of bytes processed. */
		progressBytes: Option[Schema.GoogleDatastoreAdminV1beta1Progress] = None,
	  /** Description of which entities are being imported. */
		entityFilter: Option[Schema.GoogleDatastoreAdminV1beta1EntityFilter] = None,
	  /** The location of the import metadata file. This will be the same value as the google.datastore.admin.v1beta1.ExportEntitiesResponse.output_url field. */
		inputUrl: Option[String] = None
	)
	
	case class GoogleDatastoreAdminV1ExportEntitiesMetadata(
	  /** Metadata common to all Datastore Admin operations. */
		common: Option[Schema.GoogleDatastoreAdminV1CommonMetadata] = None,
	  /** An estimate of the number of entities processed. */
		progressEntities: Option[Schema.GoogleDatastoreAdminV1Progress] = None,
	  /** An estimate of the number of bytes processed. */
		progressBytes: Option[Schema.GoogleDatastoreAdminV1Progress] = None,
	  /** Description of which entities are being exported. */
		entityFilter: Option[Schema.GoogleDatastoreAdminV1EntityFilter] = None,
	  /** Location for the export metadata and data files. This will be the same value as the google.datastore.admin.v1.ExportEntitiesRequest.output_url_prefix field. The final output location is provided in google.datastore.admin.v1.ExportEntitiesResponse.output_url. */
		outputUrlPrefix: Option[String] = None
	)
	
	object GoogleDatastoreAdminV1CommonMetadata {
		enum OperationTypeEnum extends Enum[OperationTypeEnum] { case OPERATION_TYPE_UNSPECIFIED, EXPORT_ENTITIES, IMPORT_ENTITIES, CREATE_INDEX, DELETE_INDEX }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, INITIALIZING, PROCESSING, CANCELLING, FINALIZING, SUCCESSFUL, FAILED, CANCELLED }
	}
	case class GoogleDatastoreAdminV1CommonMetadata(
	  /** The time that work began on the operation. */
		startTime: Option[String] = None,
	  /** The time the operation ended, either successfully or otherwise. */
		endTime: Option[String] = None,
	  /** The type of the operation. Can be used as a filter in ListOperationsRequest. */
		operationType: Option[Schema.GoogleDatastoreAdminV1CommonMetadata.OperationTypeEnum] = None,
	  /** The client-assigned labels which were provided when the operation was created. May also include additional labels. */
		labels: Option[Map[String, String]] = None,
	  /** The current state of the Operation. */
		state: Option[Schema.GoogleDatastoreAdminV1CommonMetadata.StateEnum] = None
	)
	
	case class GoogleDatastoreAdminV1Progress(
	  /** The amount of work that has been completed. Note that this may be greater than work_estimated. */
		workCompleted: Option[String] = None,
	  /** An estimate of how much work needs to be performed. May be zero if the work estimate is unavailable. */
		workEstimated: Option[String] = None
	)
	
	case class GoogleDatastoreAdminV1ExportEntitiesResponse(
	  /** Location of the output metadata file. This can be used to begin an import into Cloud Datastore (this project or another project). See google.datastore.admin.v1.ImportEntitiesRequest.input_url. Only present if the operation completed successfully. */
		outputUrl: Option[String] = None
	)
	
	case class GoogleDatastoreAdminV1ImportEntitiesMetadata(
	  /** Metadata common to all Datastore Admin operations. */
		common: Option[Schema.GoogleDatastoreAdminV1CommonMetadata] = None,
	  /** An estimate of the number of entities processed. */
		progressEntities: Option[Schema.GoogleDatastoreAdminV1Progress] = None,
	  /** An estimate of the number of bytes processed. */
		progressBytes: Option[Schema.GoogleDatastoreAdminV1Progress] = None,
	  /** Description of which entities are being imported. */
		entityFilter: Option[Schema.GoogleDatastoreAdminV1EntityFilter] = None,
	  /** The location of the import metadata file. This will be the same value as the google.datastore.admin.v1.ExportEntitiesResponse.output_url field. */
		inputUrl: Option[String] = None
	)
	
	case class GoogleDatastoreAdminV1IndexOperationMetadata(
	  /** Metadata common to all Datastore Admin operations. */
		common: Option[Schema.GoogleDatastoreAdminV1CommonMetadata] = None,
	  /** An estimate of the number of entities processed. */
		progressEntities: Option[Schema.GoogleDatastoreAdminV1Progress] = None,
	  /** The index resource ID that this operation is acting on. */
		indexId: Option[String] = None
	)
	
	object GoogleDatastoreAdminV1DatastoreFirestoreMigrationMetadata {
		enum MigrationStateEnum extends Enum[MigrationStateEnum] { case MIGRATION_STATE_UNSPECIFIED, RUNNING, PAUSED, COMPLETE }
		enum MigrationStepEnum extends Enum[MigrationStepEnum] { case MIGRATION_STEP_UNSPECIFIED, PREPARE, START, APPLY_WRITES_SYNCHRONOUSLY, COPY_AND_VERIFY, REDIRECT_EVENTUALLY_CONSISTENT_READS, REDIRECT_STRONGLY_CONSISTENT_READS, REDIRECT_WRITES }
	}
	case class GoogleDatastoreAdminV1DatastoreFirestoreMigrationMetadata(
	  /** The current state of migration from Cloud Datastore to Cloud Firestore in Datastore mode. */
		migrationState: Option[Schema.GoogleDatastoreAdminV1DatastoreFirestoreMigrationMetadata.MigrationStateEnum] = None,
	  /** The current step of migration from Cloud Datastore to Cloud Firestore in Datastore mode. */
		migrationStep: Option[Schema.GoogleDatastoreAdminV1DatastoreFirestoreMigrationMetadata.MigrationStepEnum] = None
	)
	
	object GoogleDatastoreAdminV1MigrationProgressEvent {
		enum StepEnum extends Enum[StepEnum] { case MIGRATION_STEP_UNSPECIFIED, PREPARE, START, APPLY_WRITES_SYNCHRONOUSLY, COPY_AND_VERIFY, REDIRECT_EVENTUALLY_CONSISTENT_READS, REDIRECT_STRONGLY_CONSISTENT_READS, REDIRECT_WRITES }
	}
	case class GoogleDatastoreAdminV1MigrationProgressEvent(
	  /** The step that is starting. An event with step set to `START` indicates that the migration has been reverted back to the initial pre-migration state. */
		step: Option[Schema.GoogleDatastoreAdminV1MigrationProgressEvent.StepEnum] = None,
	  /** Details for the `PREPARE` step. */
		prepareStepDetails: Option[Schema.GoogleDatastoreAdminV1PrepareStepDetails] = None,
	  /** Details for the `REDIRECT_WRITES` step. */
		redirectWritesStepDetails: Option[Schema.GoogleDatastoreAdminV1RedirectWritesStepDetails] = None
	)
	
	object GoogleDatastoreAdminV1PrepareStepDetails {
		enum ConcurrencyModeEnum extends Enum[ConcurrencyModeEnum] { case CONCURRENCY_MODE_UNSPECIFIED, PESSIMISTIC, OPTIMISTIC, OPTIMISTIC_WITH_ENTITY_GROUPS }
	}
	case class GoogleDatastoreAdminV1PrepareStepDetails(
	  /** The concurrency mode this database will use when it reaches the `REDIRECT_WRITES` step. */
		concurrencyMode: Option[Schema.GoogleDatastoreAdminV1PrepareStepDetails.ConcurrencyModeEnum] = None
	)
	
	object GoogleDatastoreAdminV1RedirectWritesStepDetails {
		enum ConcurrencyModeEnum extends Enum[ConcurrencyModeEnum] { case CONCURRENCY_MODE_UNSPECIFIED, PESSIMISTIC, OPTIMISTIC, OPTIMISTIC_WITH_ENTITY_GROUPS }
	}
	case class GoogleDatastoreAdminV1RedirectWritesStepDetails(
	  /** Ths concurrency mode for this database. */
		concurrencyMode: Option[Schema.GoogleDatastoreAdminV1RedirectWritesStepDetails.ConcurrencyModeEnum] = None
	)
	
	object GoogleDatastoreAdminV1MigrationStateEvent {
		enum StateEnum extends Enum[StateEnum] { case MIGRATION_STATE_UNSPECIFIED, RUNNING, PAUSED, COMPLETE }
	}
	case class GoogleDatastoreAdminV1MigrationStateEvent(
	  /** The new state of the migration. */
		state: Option[Schema.GoogleDatastoreAdminV1MigrationStateEvent.StateEnum] = None
	)
}
