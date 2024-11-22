package com.boresjo.play.api.google.discoveryengine

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
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleRpcStatus(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GoogleLongrunningCancelOperationRequest(
	
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleCloudDiscoveryengineV1CompleteQueryResponse(
	  /** Results of the matched query suggestions. The result list is ordered and the first result is a top suggestion. */
		querySuggestions: Option[List[Schema.GoogleCloudDiscoveryengineV1CompleteQueryResponseQuerySuggestion]] = None,
	  /** True if the returned suggestions are all tail suggestions. For tail matching to be triggered, include_tail_suggestions in the request must be true and there must be no suggestions that match the full query. */
		tailMatchTriggered: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1CompleteQueryResponseQuerySuggestion(
	  /** The suggestion for the query. */
		suggestion: Option[String] = None,
	  /** The unique document field paths that serve as the source of this suggestion if it was generated from completable fields. This field is only populated for the document-completable model. */
		completableFieldPaths: Option[List[String]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ImportSuggestionDenyListEntriesRequest(
	  /** The Inline source for the input content for suggestion deny list entries. */
		inlineSource: Option[Schema.GoogleCloudDiscoveryengineV1ImportSuggestionDenyListEntriesRequestInlineSource] = None,
	  /** Cloud Storage location for the input content. Only 1 file can be specified that contains all entries to import. Supported values `gcs_source.schema` for autocomplete suggestion deny list entry imports: &#42; `suggestion_deny_list` (default): One JSON [SuggestionDenyListEntry] per line. */
		gcsSource: Option[Schema.GoogleCloudDiscoveryengineV1GcsSource] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ImportSuggestionDenyListEntriesRequestInlineSource(
	  /** Required. A list of all denylist entries to import. Max of 1000 items. */
		entries: Option[List[Schema.GoogleCloudDiscoveryengineV1SuggestionDenyListEntry]] = None
	)
	
	object GoogleCloudDiscoveryengineV1SuggestionDenyListEntry {
		enum MatchOperatorEnum extends Enum[MatchOperatorEnum] { case MATCH_OPERATOR_UNSPECIFIED, EXACT_MATCH, CONTAINS }
	}
	case class GoogleCloudDiscoveryengineV1SuggestionDenyListEntry(
	  /** Required. Phrase to block from suggestions served. Can be maximum 125 characters. */
		blockPhrase: Option[String] = None,
	  /** Required. The match operator to apply for this phrase. Whether to block the exact phrase, or block any suggestions containing this phrase. */
		matchOperator: Option[Schema.GoogleCloudDiscoveryengineV1SuggestionDenyListEntry.MatchOperatorEnum] = None
	)
	
	case class GoogleCloudDiscoveryengineV1GcsSource(
	  /** Required. Cloud Storage URIs to input files. Each URI can be up to 2000 characters long. URIs can match the full object path (for example, `gs://bucket/directory/object.json`) or a pattern matching one or more files, such as `gs://bucket/directory/&#42;.json`. A request can contain at most 100 files (or 100,000 files if `data_schema` is `content`). Each file can be up to 2 GB (or 100 MB if `data_schema` is `content`). */
		inputUris: Option[List[String]] = None,
	  /** The schema to use when parsing the data from the source. Supported values for document imports: &#42; `document` (default): One JSON Document per line. Each document must have a valid Document.id. &#42; `content`: Unstructured data (e.g. PDF, HTML). Each file matched by `input_uris` becomes a document, with the ID set to the first 128 bits of SHA256(URI) encoded as a hex string. &#42; `custom`: One custom data JSON per row in arbitrary format that conforms to the defined Schema of the data store. This can only be used by the GENERIC Data Store vertical. &#42; `csv`: A CSV file with header conforming to the defined Schema of the data store. Each entry after the header is imported as a Document. This can only be used by the GENERIC Data Store vertical. Supported values for user event imports: &#42; `user_event` (default): One JSON UserEvent per line. */
		dataSchema: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1PurgeSuggestionDenyListEntriesRequest(
	
	)
	
	case class GoogleCloudDiscoveryengineV1ImportCompletionSuggestionsRequest(
	  /** The Inline source for suggestion entries. */
		inlineSource: Option[Schema.GoogleCloudDiscoveryengineV1ImportCompletionSuggestionsRequestInlineSource] = None,
	  /** Cloud Storage location for the input content. */
		gcsSource: Option[Schema.GoogleCloudDiscoveryengineV1GcsSource] = None,
	  /** BigQuery input source. */
		bigquerySource: Option[Schema.GoogleCloudDiscoveryengineV1BigQuerySource] = None,
	  /** The desired location of errors incurred during the Import. */
		errorConfig: Option[Schema.GoogleCloudDiscoveryengineV1ImportErrorConfig] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ImportCompletionSuggestionsRequestInlineSource(
	  /** Required. A list of all denylist entries to import. Max of 1000 items. */
		suggestions: Option[List[Schema.GoogleCloudDiscoveryengineV1CompletionSuggestion]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1CompletionSuggestion(
	  /** Global score of this suggestion. Control how this suggestion would be scored / ranked. */
		globalScore: Option[BigDecimal] = None,
	  /** Frequency of this suggestion. Will be used to rank suggestions when score is not available. */
		frequency: Option[String] = None,
	  /** Required. The suggestion text. */
		suggestion: Option[String] = None,
	  /** BCP-47 language code of this suggestion. */
		languageCode: Option[String] = None,
	  /** If two suggestions have the same groupId, they will not be returned together. Instead the one ranked higher will be returned. This can be used to deduplicate semantically identical suggestions. */
		groupId: Option[String] = None,
	  /** The score of this suggestion within its group. */
		groupScore: Option[BigDecimal] = None,
	  /** Alternative matching phrases for this suggestion. */
		alternativePhrases: Option[List[String]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1BigQuerySource(
	  /** BigQuery time partitioned table's _PARTITIONDATE in YYYY-MM-DD format. */
		partitionDate: Option[Schema.GoogleTypeDate] = None,
	  /** The project ID or the project number that contains the BigQuery source. Has a length limit of 128 characters. If not specified, inherits the project ID from the parent request. */
		projectId: Option[String] = None,
	  /** Required. The BigQuery data set to copy the data from with a length limit of 1,024 characters. */
		datasetId: Option[String] = None,
	  /** Required. The BigQuery table to copy the data from with a length limit of 1,024 characters. */
		tableId: Option[String] = None,
	  /** Intermediate Cloud Storage directory used for the import with a length limit of 2,000 characters. Can be specified if one wants to have the BigQuery export to a specific Cloud Storage directory. */
		gcsStagingDir: Option[String] = None,
	  /** The schema to use when parsing the data from the source. Supported values for user event imports: &#42; `user_event` (default): One UserEvent per row. Supported values for document imports: &#42; `document` (default): One Document format per row. Each document must have a valid Document.id and one of Document.json_data or Document.struct_data. &#42; `custom`: One custom data per row in arbitrary format that conforms to the defined Schema of the data store. This can only be used by the GENERIC Data Store vertical. */
		dataSchema: Option[String] = None
	)
	
	case class GoogleTypeDate(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ImportErrorConfig(
	  /** Cloud Storage prefix for import errors. This must be an empty, existing Cloud Storage directory. Import errors are written to sharded files in this directory, one per line, as a JSON-encoded `google.rpc.Status` message. */
		gcsPrefix: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1PurgeCompletionSuggestionsRequest(
	
	)
	
	object GoogleCloudDiscoveryengineV1Control {
		enum SolutionTypeEnum extends Enum[SolutionTypeEnum] { case SOLUTION_TYPE_UNSPECIFIED, SOLUTION_TYPE_RECOMMENDATION, SOLUTION_TYPE_SEARCH, SOLUTION_TYPE_CHAT, SOLUTION_TYPE_GENERATIVE_CHAT }
		enum UseCasesEnum extends Enum[UseCasesEnum] { case SEARCH_USE_CASE_UNSPECIFIED, SEARCH_USE_CASE_SEARCH, SEARCH_USE_CASE_BROWSE }
	}
	case class GoogleCloudDiscoveryengineV1Control(
	  /** Defines a boost-type control */
		boostAction: Option[Schema.GoogleCloudDiscoveryengineV1ControlBoostAction] = None,
	  /** Defines a filter-type control Currently not supported by Recommendation */
		filterAction: Option[Schema.GoogleCloudDiscoveryengineV1ControlFilterAction] = None,
	  /** Defines a redirect-type control. */
		redirectAction: Option[Schema.GoogleCloudDiscoveryengineV1ControlRedirectAction] = None,
	  /** Treats a group of terms as synonyms of one another. */
		synonymsAction: Option[Schema.GoogleCloudDiscoveryengineV1ControlSynonymsAction] = None,
	  /** Immutable. Fully qualified name `projects/&#42;/locations/global/dataStore/&#42;/controls/&#42;` */
		name: Option[String] = None,
	  /** Required. Human readable name. The identifier used in UI views. Must be UTF-8 encoded string. Length limit is 128 characters. Otherwise an INVALID ARGUMENT error is thrown. */
		displayName: Option[String] = None,
	  /** Output only. List of all ServingConfig IDs this control is attached to. May take up to 10 minutes to update after changes. */
		associatedServingConfigIds: Option[List[String]] = None,
	  /** Required. Immutable. What solution the control belongs to. Must be compatible with vertical of resource. Otherwise an INVALID ARGUMENT error is thrown. */
		solutionType: Option[Schema.GoogleCloudDiscoveryengineV1Control.SolutionTypeEnum] = None,
	  /** Specifies the use case for the control. Affects what condition fields can be set. Only applies to SOLUTION_TYPE_SEARCH. Currently only allow one use case per control. Must be set when solution_type is SolutionType.SOLUTION_TYPE_SEARCH. */
		useCases: Option[List[Schema.GoogleCloudDiscoveryengineV1Control.UseCasesEnum]] = None,
	  /** Determines when the associated action will trigger. Omit to always apply the action. Currently only a single condition may be specified. Otherwise an INVALID ARGUMENT error is thrown. */
		conditions: Option[List[Schema.GoogleCloudDiscoveryengineV1Condition]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ControlBoostAction(
	  /** Required. Strength of the boost, which should be in [-1, 1]. Negative boost means demotion. Default is 0.0 (No-op). */
		boost: Option[BigDecimal] = None,
	  /** Required. Specifies which products to apply the boost to. If no filter is provided all products will be boosted (No-op). Syntax documentation: https://cloud.google.com/retail/docs/filter-and-order Maximum length is 5000 characters. Otherwise an INVALID ARGUMENT error is thrown. */
		filter: Option[String] = None,
	  /** Required. Specifies which data store's documents can be boosted by this control. Full data store name e.g. projects/123/locations/global/collections/default_collection/dataStores/default_data_store */
		dataStore: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ControlFilterAction(
	  /** Required. A filter to apply on the matching condition results. Required Syntax documentation: https://cloud.google.com/retail/docs/filter-and-order Maximum length is 5000 characters. Otherwise an INVALID ARGUMENT error is thrown. */
		filter: Option[String] = None,
	  /** Required. Specifies which data store's documents can be filtered by this control. Full data store name e.g. projects/123/locations/global/collections/default_collection/dataStores/default_data_store */
		dataStore: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ControlRedirectAction(
	  /** Required. The URI to which the shopper will be redirected. Required. URI must have length equal or less than 2000 characters. Otherwise an INVALID ARGUMENT error is thrown. */
		redirectUri: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ControlSynonymsAction(
	  /** Defines a set of synonyms. Can specify up to 100 synonyms. Must specify at least 2 synonyms. Otherwise an INVALID ARGUMENT error is thrown. */
		synonyms: Option[List[String]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1Condition(
	  /** Search only A list of terms to match the query on. Cannot be set when Condition.query_regex is set. Maximum of 10 query terms. */
		queryTerms: Option[List[Schema.GoogleCloudDiscoveryengineV1ConditionQueryTerm]] = None,
	  /** Range of time(s) specifying when condition is active. Maximum of 10 time ranges. */
		activeTimeRange: Option[List[Schema.GoogleCloudDiscoveryengineV1ConditionTimeRange]] = None,
	  /** Optional. Query regex to match the whole search query. Cannot be set when Condition.query_terms is set. This is currently supporting promotion use case. */
		queryRegex: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ConditionQueryTerm(
	  /** The specific query value to match against Must be lowercase, must be UTF-8. Can have at most 3 space separated terms if full_match is true. Cannot be an empty string. Maximum length of 5000 characters. */
		value: Option[String] = None,
	  /** Whether the search query needs to exactly match the query term. */
		fullMatch: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ConditionTimeRange(
	  /** Start of time range. Range is inclusive. */
		startTime: Option[String] = None,
	  /** End of time range. Range is inclusive. Must be in the future. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ListControlsResponse(
	  /** All the Controls for a given data store. */
		controls: Option[List[Schema.GoogleCloudDiscoveryengineV1Control]] = None,
	  /** Pagination token, if not returned indicates the last page. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchRequest(
	  /** The branch resource name, such as `projects/&#42;/locations/global/collections/default_collection/dataStores/default_data_store/branches/0`. Use `default_branch` as the branch ID or leave this field empty, to search documents under the default branch. */
		branch: Option[String] = None,
	  /** Raw search query. */
		query: Option[String] = None,
	  /** Raw image query. */
		imageQuery: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestImageQuery] = None,
	  /** Maximum number of Documents to return. The maximum allowed value depends on the data type. Values above the maximum value are coerced to the maximum value. &#42; Websites with basic indexing: Default `10`, Maximum `25`. &#42; Websites with advanced indexing: Default `25`, Maximum `50`. &#42; Other: Default `50`, Maximum `100`. If this field is negative, an `INVALID_ARGUMENT` is returned. */
		pageSize: Option[Int] = None,
	  /** A page token received from a previous SearchService.Search call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to SearchService.Search must match the call that provided the page token. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		pageToken: Option[String] = None,
	  /** A 0-indexed integer that specifies the current offset (that is, starting result location, amongst the Documents deemed by the API as relevant) in search results. This field is only considered if page_token is unset. If this field is negative, an `INVALID_ARGUMENT` is returned. */
		offset: Option[Int] = None,
	  /** The maximum number of results to return for OneBox. This applies to each OneBox type individually. Default number is 10. */
		oneBoxPageSize: Option[Int] = None,
	  /** Specs defining dataStores to filter on in a search call and configurations for those dataStores. This is only considered for engines with multiple dataStores use case. For single dataStore within an engine, they should use the specs at the top level. */
		dataStoreSpecs: Option[List[Schema.GoogleCloudDiscoveryengineV1SearchRequestDataStoreSpec]] = None,
	  /** The filter syntax consists of an expression language for constructing a predicate from one or more fields of the documents being filtered. Filter expression is case-sensitive. If this field is unrecognizable, an `INVALID_ARGUMENT` is returned. Filtering in Vertex AI Search is done by mapping the LHS filter key to a key property defined in the Vertex AI Search backend -- this mapping is defined by the customer in their schema. For example a media customer might have a field 'name' in their schema. In this case the filter would look like this: filter --> name:'ANY("king kong")' For more information about filtering including syntax and filter operators, see [Filter](https://cloud.google.com/generative-ai-app-builder/docs/filter-search-metadata) */
		filter: Option[String] = None,
	  /** The default filter that is applied when a user performs a search without checking any filters on the search page. The filter applied to every search request when quality improvement such as query expansion is needed. In the case a query does not have a sufficient amount of results this filter will be used to determine whether or not to enable the query expansion flow. The original filter will still be used for the query expanded search. This field is strongly recommended to achieve high search quality. For more information about filter syntax, see SearchRequest.filter. */
		canonicalFilter: Option[String] = None,
	  /** The order in which documents are returned. Documents can be ordered by a field in an Document object. Leave it unset if ordered by relevance. `order_by` expression is case-sensitive. For more information on ordering the website search results, see [Order web search results](https://cloud.google.com/generative-ai-app-builder/docs/order-web-search-results). For more information on ordering the healthcare search results, see [Order healthcare search results](https://cloud.google.com/generative-ai-app-builder/docs/order-hc-results). If this field is unrecognizable, an `INVALID_ARGUMENT` is returned. */
		orderBy: Option[String] = None,
	  /** Information about the end user. Highly recommended for analytics. UserInfo.user_agent is used to deduce `device_type` for analytics. */
		userInfo: Option[Schema.GoogleCloudDiscoveryengineV1UserInfo] = None,
	  /** The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see [Standard fields](https://cloud.google.com/apis/design/standard_fields). This field helps to better interpret the query. If a value isn't specified, the query language code is automatically detected, which may not be accurate. */
		languageCode: Option[String] = None,
	  /** Facet specifications for faceted search. If empty, no facets are returned. A maximum of 100 values are allowed. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		facetSpecs: Option[List[Schema.GoogleCloudDiscoveryengineV1SearchRequestFacetSpec]] = None,
	  /** Boost specification to boost certain documents. For more information on boosting, see [Boosting](https://cloud.google.com/generative-ai-app-builder/docs/boost-search-results) */
		boostSpec: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestBoostSpec] = None,
	  /** Additional search parameters. For public website search only, supported values are: &#42; `user_country_code`: string. Default empty. If set to non-empty, results are restricted or boosted based on the location provided. For example, `user_country_code: "au"` For available codes see [Country Codes](https://developers.google.com/custom-search/docs/json_api_reference#countryCodes) &#42; `search_type`: double. Default empty. Enables non-webpage searching depending on the value. The only valid non-default value is 1, which enables image searching. For example, `search_type: 1` */
		params: Option[Map[String, JsValue]] = None,
	  /** The query expansion specification that specifies the conditions under which query expansion occurs. */
		queryExpansionSpec: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestQueryExpansionSpec] = None,
	  /** The spell correction specification that specifies the mode under which spell correction takes effect. */
		spellCorrectionSpec: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestSpellCorrectionSpec] = None,
	  /** A unique identifier for tracking visitors. For example, this could be implemented with an HTTP cookie, which should be able to uniquely identify a visitor on a single device. This unique identifier should not change if the visitor logs in or out of the website. This field should NOT have a fixed value such as `unknown_visitor`. This should be the same identifier as UserEvent.user_pseudo_id and CompleteQueryRequest.user_pseudo_id The field must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		userPseudoId: Option[String] = None,
	  /** A specification for configuring the behavior of content search. */
		contentSearchSpec: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestContentSearchSpec] = None,
	  /** Whether to turn on safe search. This is only supported for website search. */
		safeSearch: Option[Boolean] = None,
	  /** The user labels applied to a resource must meet the following requirements: &#42; Each resource can have multiple labels, up to a maximum of 64. &#42; Each label must be a key-value pair. &#42; Keys have a minimum length of 1 character and a maximum length of 63 characters and cannot be empty. Values can be empty and have a maximum length of 63 characters. &#42; Keys and values can contain only lowercase letters, numeric characters, underscores, and dashes. All characters must use UTF-8 encoding, and international characters are allowed. &#42; The key portion of a label must be unique. However, you can use the same key with multiple resources. &#42; Keys must start with a lowercase letter or international character. See [Google Cloud Document](https://cloud.google.com/resource-manager/docs/creating-managing-labels#requirements) for more details. */
		userLabels: Option[Map[String, String]] = None,
	  /** Search as you type configuration. Only supported for the IndustryVertical.MEDIA vertical. */
		searchAsYouTypeSpec: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestSearchAsYouTypeSpec] = None,
	  /** The session resource name. Optional. Session allows users to do multi-turn /search API calls or coordination between /search API calls and /answer API calls. Example #1 (multi-turn /search API calls): 1. Call /search API with the auto-session mode (see below). 2. Call /search API with the session ID generated in the first call. Here, the previous search query gets considered in query standing. I.e., if the first query is "How did Alphabet do in 2022?" and the current query is "How about 2023?", the current query will be interpreted as "How did Alphabet do in 2023?". Example #2 (coordination between /search API calls and /answer API calls): 1. Call /search API with the auto-session mode (see below). 2. Call /answer API with the session ID generated in the first call. Here, the answer generation happens in the context of the search results from the first search call. Auto-session mode: when `projects/.../sessions/-` is used, a new session gets automatically created. Otherwise, users can use the create-session API to create a session manually. Multi-turn Search feature is currently at private GA stage. Please use v1alpha or v1beta version instead before we launch this feature to public GA. Or ask for allowlisting through Google Support team. */
		session: Option[String] = None,
	  /** Session specification. Can be used only when `session` is set. */
		sessionSpec: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestSessionSpec] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchRequestImageQuery(
	  /** Base64 encoded image bytes. Supported image formats: JPEG, PNG, and BMP. */
		imageBytes: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchRequestDataStoreSpec(
	  /** Required. Full resource name of DataStore, such as `projects/{project}/locations/{location}/collections/{collection_id}/dataStores/{data_store_id}`. */
		dataStore: Option[String] = None,
	  /** Optional. Filter specification to filter documents in the data store specified by data_store field. For more information on filtering, see [Filtering](https://cloud.google.com/generative-ai-app-builder/docs/filter-search-metadata) */
		filter: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1UserInfo(
	  /** Highly recommended for logged-in users. Unique identifier for logged-in user, such as a user name. Don't set for anonymous users. Always use a hashed value for this ID. Don't set the field to the same fixed ID for different users. This mixes the event history of those users together, which results in degraded model quality. The field must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		userId: Option[String] = None,
	  /** User agent as included in the HTTP header. The field must be a UTF-8 encoded string with a length limit of 1,000 characters. Otherwise, an `INVALID_ARGUMENT` error is returned. This should not be set when using the client side event reporting with GTM or JavaScript tag in UserEventService.CollectUserEvent or if UserEvent.direct_user_request is set. */
		userAgent: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchRequestFacetSpec(
	  /** Required. The facet key specification. */
		facetKey: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestFacetSpecFacetKey] = None,
	  /** Maximum facet values that are returned for this facet. If unspecified, defaults to 20. The maximum allowed value is 300. Values above 300 are coerced to 300. For aggregation in healthcare search, when the [FacetKey.key] is "healthcare_aggregation_key", the limit will be overridden to 10,000 internally, regardless of the value set here. If this field is negative, an `INVALID_ARGUMENT` is returned. */
		limit: Option[Int] = None,
	  /** List of keys to exclude when faceting. By default, FacetKey.key is not excluded from the filter unless it is listed in this field. Listing a facet key in this field allows its values to appear as facet results, even when they are filtered out of search results. Using this field does not affect what search results are returned. For example, suppose there are 100 documents with the color facet "Red" and 200 documents with the color facet "Blue". A query containing the filter "color:ANY("Red")" and having "color" as FacetKey.key would by default return only "Red" documents in the search results, and also return "Red" with count 100 as the only color facet. Although there are also blue documents available, "Blue" would not be shown as an available facet value. If "color" is listed in "excludedFilterKeys", then the query returns the facet values "Red" with count 100 and "Blue" with count 200, because the "color" key is now excluded from the filter. Because this field doesn't affect search results, the search results are still correctly filtered to return only "Red" documents. A maximum of 100 values are allowed. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		excludedFilterKeys: Option[List[String]] = None,
	  /** Enables dynamic position for this facet. If set to true, the position of this facet among all facets in the response is determined automatically. If dynamic facets are enabled, it is ordered together. If set to false, the position of this facet in the response is the same as in the request, and it is ranked before the facets with dynamic position enable and all dynamic facets. For example, you may always want to have rating facet returned in the response, but it's not necessarily to always display the rating facet at the top. In that case, you can set enable_dynamic_position to true so that the position of rating facet in response is determined automatically. Another example, assuming you have the following facets in the request: &#42; "rating", enable_dynamic_position = true &#42; "price", enable_dynamic_position = false &#42; "brands", enable_dynamic_position = false And also you have a dynamic facets enabled, which generates a facet `gender`. Then the final order of the facets in the response can be ("price", "brands", "rating", "gender") or ("price", "brands", "gender", "rating") depends on how API orders "gender" and "rating" facets. However, notice that "price" and "brands" are always ranked at first and second position because their enable_dynamic_position is false. */
		enableDynamicPosition: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchRequestFacetSpecFacetKey(
	  /** Required. Supported textual and numerical facet keys in Document object, over which the facet values are computed. Facet key is case-sensitive. */
		key: Option[String] = None,
	  /** Set only if values should be bucketed into intervals. Must be set for facets with numerical values. Must not be set for facet with text values. Maximum number of intervals is 30. */
		intervals: Option[List[Schema.GoogleCloudDiscoveryengineV1Interval]] = None,
	  /** Only get facet for the given restricted values. Only supported on textual fields. For example, suppose "category" has three values "Action > 2022", "Action > 2021" and "Sci-Fi > 2022". If set "restricted_values" to "Action > 2022", the "category" facet only contains "Action > 2022". Only supported on textual fields. Maximum is 10. */
		restrictedValues: Option[List[String]] = None,
	  /** Only get facet values that start with the given string prefix. For example, suppose "category" has three values "Action > 2022", "Action > 2021" and "Sci-Fi > 2022". If set "prefixes" to "Action", the "category" facet only contains "Action > 2022" and "Action > 2021". Only supported on textual fields. Maximum is 10. */
		prefixes: Option[List[String]] = None,
	  /** Only get facet values that contain the given strings. For example, suppose "category" has three values "Action > 2022", "Action > 2021" and "Sci-Fi > 2022". If set "contains" to "2022", the "category" facet only contains "Action > 2022" and "Sci-Fi > 2022". Only supported on textual fields. Maximum is 10. */
		contains: Option[List[String]] = None,
	  /** True to make facet keys case insensitive when getting faceting values with prefixes or contains; false otherwise. */
		caseInsensitive: Option[Boolean] = None,
	  /** The order in which documents are returned. Allowed values are: &#42; "count desc", which means order by SearchResponse.Facet.values.count descending. &#42; "value desc", which means order by SearchResponse.Facet.values.value descending. Only applies to textual facets. If not set, textual values are sorted in [natural order](https://en.wikipedia.org/wiki/Natural_sort_order); numerical intervals are sorted in the order given by FacetSpec.FacetKey.intervals. */
		orderBy: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1Interval(
	  /** Inclusive lower bound. */
		minimum: Option[BigDecimal] = None,
	  /** Exclusive lower bound. */
		exclusiveMinimum: Option[BigDecimal] = None,
	  /** Inclusive upper bound. */
		maximum: Option[BigDecimal] = None,
	  /** Exclusive upper bound. */
		exclusiveMaximum: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchRequestBoostSpec(
	  /** Condition boost specifications. If a document matches multiple conditions in the specifictions, boost scores from these specifications are all applied and combined in a non-linear way. Maximum number of specifications is 20. */
		conditionBoostSpecs: Option[List[Schema.GoogleCloudDiscoveryengineV1SearchRequestBoostSpecConditionBoostSpec]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchRequestBoostSpecConditionBoostSpec(
	  /** An expression which specifies a boost condition. The syntax and supported fields are the same as a filter expression. See SearchRequest.filter for detail syntax and limitations. Examples: &#42; To boost documents with document ID "doc_1" or "doc_2", and color "Red" or "Blue": `(document_id: ANY("doc_1", "doc_2")) AND (color: ANY("Red", "Blue"))` */
		condition: Option[String] = None,
	  /** Strength of the condition boost, which should be in [-1, 1]. Negative boost means demotion. Default is 0.0. Setting to 1.0 gives the document a big promotion. However, it does not necessarily mean that the boosted document will be the top result at all times, nor that other documents will be excluded. Results could still be shown even when none of them matches the condition. And results that are significantly more relevant to the search query can still trump your heavily favored but irrelevant documents. Setting to -1.0 gives the document a big demotion. However, results that are deeply relevant might still be shown. The document will have an upstream battle to get a fairly high ranking, but it is not blocked out completely. Setting to 0.0 means no boost applied. The boosting condition is ignored. Only one of the (condition, boost) combination or the boost_control_spec below are set. If both are set then the global boost is ignored and the more fine-grained boost_control_spec is applied. */
		boost: Option[BigDecimal] = None,
	  /** Complex specification for custom ranking based on customer defined attribute value. */
		boostControlSpec: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestBoostSpecConditionBoostSpecBoostControlSpec] = None
	)
	
	object GoogleCloudDiscoveryengineV1SearchRequestBoostSpecConditionBoostSpecBoostControlSpec {
		enum AttributeTypeEnum extends Enum[AttributeTypeEnum] { case ATTRIBUTE_TYPE_UNSPECIFIED, NUMERICAL, FRESHNESS }
		enum InterpolationTypeEnum extends Enum[InterpolationTypeEnum] { case INTERPOLATION_TYPE_UNSPECIFIED, LINEAR }
	}
	case class GoogleCloudDiscoveryengineV1SearchRequestBoostSpecConditionBoostSpecBoostControlSpec(
	  /** The name of the field whose value will be used to determine the boost amount. */
		fieldName: Option[String] = None,
	  /** The attribute type to be used to determine the boost amount. The attribute value can be derived from the field value of the specified field_name. In the case of numerical it is straightforward i.e. attribute_value = numerical_field_value. In the case of freshness however, attribute_value = (time.now() - datetime_field_value). */
		attributeType: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestBoostSpecConditionBoostSpecBoostControlSpec.AttributeTypeEnum] = None,
	  /** The interpolation type to be applied to connect the control points listed below. */
		interpolationType: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestBoostSpecConditionBoostSpecBoostControlSpec.InterpolationTypeEnum] = None,
	  /** The control points used to define the curve. The monotonic function (defined through the interpolation_type above) passes through the control points listed here. */
		controlPoints: Option[List[Schema.GoogleCloudDiscoveryengineV1SearchRequestBoostSpecConditionBoostSpecBoostControlSpecControlPoint]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchRequestBoostSpecConditionBoostSpecBoostControlSpecControlPoint(
	  /** Can be one of: 1. The numerical field value. 2. The duration spec for freshness: The value must be formatted as an XSD `dayTimeDuration` value (a restricted subset of an ISO 8601 duration value). The pattern for this is: `nDnM]`. */
		attributeValue: Option[String] = None,
	  /** The value between -1 to 1 by which to boost the score if the attribute_value evaluates to the value specified above. */
		boostAmount: Option[BigDecimal] = None
	)
	
	object GoogleCloudDiscoveryengineV1SearchRequestQueryExpansionSpec {
		enum ConditionEnum extends Enum[ConditionEnum] { case CONDITION_UNSPECIFIED, DISABLED, AUTO }
	}
	case class GoogleCloudDiscoveryengineV1SearchRequestQueryExpansionSpec(
	  /** The condition under which query expansion should occur. Default to Condition.DISABLED. */
		condition: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestQueryExpansionSpec.ConditionEnum] = None,
	  /** Whether to pin unexpanded results. If this field is set to true, unexpanded products are always at the top of the search results, followed by the expanded results. */
		pinUnexpandedResults: Option[Boolean] = None
	)
	
	object GoogleCloudDiscoveryengineV1SearchRequestSpellCorrectionSpec {
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, SUGGESTION_ONLY, AUTO }
	}
	case class GoogleCloudDiscoveryengineV1SearchRequestSpellCorrectionSpec(
	  /** The mode under which spell correction replaces the original search query. Defaults to Mode.AUTO. */
		mode: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestSpellCorrectionSpec.ModeEnum] = None
	)
	
	object GoogleCloudDiscoveryengineV1SearchRequestContentSearchSpec {
		enum SearchResultModeEnum extends Enum[SearchResultModeEnum] { case SEARCH_RESULT_MODE_UNSPECIFIED, DOCUMENTS, CHUNKS }
	}
	case class GoogleCloudDiscoveryengineV1SearchRequestContentSearchSpec(
	  /** If `snippetSpec` is not specified, snippets are not included in the search response. */
		snippetSpec: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestContentSearchSpecSnippetSpec] = None,
	  /** If `summarySpec` is not specified, summaries are not included in the search response. */
		summarySpec: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestContentSearchSpecSummarySpec] = None,
	  /** If there is no extractive_content_spec provided, there will be no extractive answer in the search response. */
		extractiveContentSpec: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestContentSearchSpecExtractiveContentSpec] = None,
	  /** Specifies the search result mode. If unspecified, the search result mode defaults to `DOCUMENTS`. */
		searchResultMode: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestContentSearchSpec.SearchResultModeEnum] = None,
	  /** Specifies the chunk spec to be returned from the search response. Only available if the SearchRequest.ContentSearchSpec.search_result_mode is set to CHUNKS */
		chunkSpec: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestContentSearchSpecChunkSpec] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchRequestContentSearchSpecSnippetSpec(
	  /** [DEPRECATED] This field is deprecated. To control snippet return, use `return_snippet` field. For backwards compatibility, we will return snippet if max_snippet_count > 0. */
		maxSnippetCount: Option[Int] = None,
	  /** [DEPRECATED] This field is deprecated and will have no affect on the snippet. */
		referenceOnly: Option[Boolean] = None,
	  /** If `true`, then return snippet. If no snippet can be generated, we return "No snippet is available for this page." A `snippet_status` with `SUCCESS` or `NO_SNIPPET_AVAILABLE` will also be returned. */
		returnSnippet: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchRequestContentSearchSpecSummarySpec(
	  /** The number of top results to generate the summary from. If the number of results returned is less than `summaryResultCount`, the summary is generated from all of the results. At most 10 results for documents mode, or 50 for chunks mode, can be used to generate a summary. The chunks mode is used when SearchRequest.ContentSearchSpec.search_result_mode is set to CHUNKS. */
		summaryResultCount: Option[Int] = None,
	  /** Specifies whether to include citations in the summary. The default value is `false`. When this field is set to `true`, summaries include in-line citation numbers. Example summary including citations: BigQuery is Google Cloud's fully managed and completely serverless enterprise data warehouse [1]. BigQuery supports all data types, works across clouds, and has built-in machine learning and business intelligence, all within a unified platform [2, 3]. The citation numbers refer to the returned search results and are 1-indexed. For example, [1] means that the sentence is attributed to the first search result. [2, 3] means that the sentence is attributed to both the second and third search results. */
		includeCitations: Option[Boolean] = None,
	  /** Specifies whether to filter out adversarial queries. The default value is `false`. Google employs search-query classification to detect adversarial queries. No summary is returned if the search query is classified as an adversarial query. For example, a user might ask a question regarding negative comments about the company or submit a query designed to generate unsafe, policy-violating output. If this field is set to `true`, we skip generating summaries for adversarial queries and return fallback messages instead. */
		ignoreAdversarialQuery: Option[Boolean] = None,
	  /** Specifies whether to filter out queries that are not summary-seeking. The default value is `false`. Google employs search-query classification to detect summary-seeking queries. No summary is returned if the search query is classified as a non-summary seeking query. For example, `why is the sky blue` and `Who is the best soccer player in the world?` are summary-seeking queries, but `SFO airport` and `world cup 2026` are not. They are most likely navigational queries. If this field is set to `true`, we skip generating summaries for non-summary seeking queries and return fallback messages instead. */
		ignoreNonSummarySeekingQuery: Option[Boolean] = None,
	  /** Specifies whether to filter out queries that have low relevance. The default value is `false`. If this field is set to `false`, all search results are used regardless of relevance to generate answers. If set to `true`, only queries with high relevance search results will generate answers. */
		ignoreLowRelevantContent: Option[Boolean] = None,
	  /** Optional. Specifies whether to filter out jail-breaking queries. The default value is `false`. Google employs search-query classification to detect jail-breaking queries. No summary is returned if the search query is classified as a jail-breaking query. A user might add instructions to the query to change the tone, style, language, content of the answer, or ask the model to act as a different entity, e.g. "Reply in the tone of a competing company's CEO". If this field is set to `true`, we skip generating summaries for jail-breaking queries and return fallback messages instead. */
		ignoreJailBreakingQuery: Option[Boolean] = None,
	  /** If specified, the spec will be used to modify the prompt provided to the LLM. */
		modelPromptSpec: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestContentSearchSpecSummarySpecModelPromptSpec] = None,
	  /** Language code for Summary. Use language tags defined by [BCP47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt). Note: This is an experimental feature. */
		languageCode: Option[String] = None,
	  /** If specified, the spec will be used to modify the model specification provided to the LLM. */
		modelSpec: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestContentSearchSpecSummarySpecModelSpec] = None,
	  /** If true, answer will be generated from most relevant chunks from top search results. This feature will improve summary quality. Note that with this feature enabled, not all top search results will be referenced and included in the reference list, so the citation source index only points to the search results listed in the reference list. */
		useSemanticChunks: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchRequestContentSearchSpecSummarySpecModelPromptSpec(
	  /** Text at the beginning of the prompt that instructs the assistant. Examples are available in the user guide. */
		preamble: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchRequestContentSearchSpecSummarySpecModelSpec(
	  /** The model version used to generate the summary. Supported values are: &#42; `stable`: string. Default value when no value is specified. Uses a generally available, fine-tuned model. For more information, see [Answer generation model versions and lifecycle](https://cloud.google.com/generative-ai-app-builder/docs/answer-generation-models). &#42; `preview`: string. (Public preview) Uses a preview model. For more information, see [Answer generation model versions and lifecycle](https://cloud.google.com/generative-ai-app-builder/docs/answer-generation-models). */
		version: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchRequestContentSearchSpecExtractiveContentSpec(
	  /** The maximum number of extractive answers returned in each search result. An extractive answer is a verbatim answer extracted from the original document, which provides a precise and contextually relevant answer to the search query. If the number of matching answers is less than the `max_extractive_answer_count`, return all of the answers. Otherwise, return the `max_extractive_answer_count`. At most five answers are returned for each SearchResult. */
		maxExtractiveAnswerCount: Option[Int] = None,
	  /** The max number of extractive segments returned in each search result. Only applied if the DataStore is set to DataStore.ContentConfig.CONTENT_REQUIRED or DataStore.solution_types is SOLUTION_TYPE_CHAT. An extractive segment is a text segment extracted from the original document that is relevant to the search query, and, in general, more verbose than an extractive answer. The segment could then be used as input for LLMs to generate summaries and answers. If the number of matching segments is less than `max_extractive_segment_count`, return all of the segments. Otherwise, return the `max_extractive_segment_count`. */
		maxExtractiveSegmentCount: Option[Int] = None,
	  /** Specifies whether to return the confidence score from the extractive segments in each search result. This feature is available only for new or allowlisted data stores. To allowlist your data store, contact your Customer Engineer. The default value is `false`. */
		returnExtractiveSegmentScore: Option[Boolean] = None,
	  /** Specifies whether to also include the adjacent from each selected segments. Return at most `num_previous_segments` segments before each selected segments. */
		numPreviousSegments: Option[Int] = None,
	  /** Return at most `num_next_segments` segments after each selected segments. */
		numNextSegments: Option[Int] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchRequestContentSearchSpecChunkSpec(
	  /** The number of previous chunks to be returned of the current chunk. The maximum allowed value is 3. If not specified, no previous chunks will be returned. */
		numPreviousChunks: Option[Int] = None,
	  /** The number of next chunks to be returned of the current chunk. The maximum allowed value is 3. If not specified, no next chunks will be returned. */
		numNextChunks: Option[Int] = None
	)
	
	object GoogleCloudDiscoveryengineV1SearchRequestSearchAsYouTypeSpec {
		enum ConditionEnum extends Enum[ConditionEnum] { case CONDITION_UNSPECIFIED, DISABLED, ENABLED }
	}
	case class GoogleCloudDiscoveryengineV1SearchRequestSearchAsYouTypeSpec(
	  /** The condition under which search as you type should occur. Default to Condition.DISABLED. */
		condition: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestSearchAsYouTypeSpec.ConditionEnum] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchRequestSessionSpec(
	  /** If set, the search result gets stored to the "turn" specified by this query ID. Example: Let's say the session looks like this: session { name: ".../sessions/xxx" turns { query { text: "What is foo?" query_id: ".../questions/yyy" } answer: "Foo is ..." } turns { query { text: "How about bar then?" query_id: ".../questions/zzz" } } } The user can call /search API with a request like this: session: ".../sessions/xxx" session_spec { query_id: ".../questions/zzz" } Then, the API stores the search result, associated with the last turn. The stored search result can be used by a subsequent /answer API call (with the session ID and the query ID specified). Also, it is possible to call /search and /answer in parallel with the same session ID & query ID. */
		queryId: Option[String] = None,
	  /** The number of top search results to persist. The persisted search results can be used for the subsequent /answer api call. This field is simliar to the `summary_result_count` field in SearchRequest.ContentSearchSpec.SummarySpec.summary_result_count. At most 10 results for documents mode, or 50 for chunks mode. */
		searchResultPersistenceCount: Option[Int] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchResponse(
	  /** A list of matched documents. The order represents the ranking. */
		results: Option[List[Schema.GoogleCloudDiscoveryengineV1SearchResponseSearchResult]] = None,
	  /** Results of facets requested by user. */
		facets: Option[List[Schema.GoogleCloudDiscoveryengineV1SearchResponseFacet]] = None,
	  /** The estimated total count of matched items irrespective of pagination. The count of results returned by pagination may be less than the total_size that matches. */
		totalSize: Option[Int] = None,
	  /** A unique search token. This should be included in the UserEvent logs resulting from this search, which enables accurate attribution of search model performance. This also helps to identify a request during the customer support scenarios. */
		attributionToken: Option[String] = None,
	  /** The URI of a customer-defined redirect page. If redirect action is triggered, no search is performed, and only redirect_uri and attribution_token are set in the response. */
		redirectUri: Option[String] = None,
	  /** A token that can be sent as SearchRequest.page_token to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Contains the spell corrected query, if found. If the spell correction type is AUTOMATIC, then the search results are based on corrected_query. Otherwise the original query is used for search. */
		correctedQuery: Option[String] = None,
	  /** A summary as part of the search results. This field is only returned if SearchRequest.ContentSearchSpec.summary_spec is set. */
		summary: Option[Schema.GoogleCloudDiscoveryengineV1SearchResponseSummary] = None,
	  /** Query expansion information for the returned results. */
		queryExpansionInfo: Option[Schema.GoogleCloudDiscoveryengineV1SearchResponseQueryExpansionInfo] = None,
	  /** Session information. Only set if SearchRequest.session is provided. See its description for more details. */
		sessionInfo: Option[Schema.GoogleCloudDiscoveryengineV1SearchResponseSessionInfo] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchResponseSearchResult(
	  /** Document.id of the searched Document. */
		id: Option[String] = None,
	  /** The document data snippet in the search response. Only fields that are marked as `retrievable` are populated. */
		document: Option[Schema.GoogleCloudDiscoveryengineV1Document] = None,
	  /** The chunk data in the search response if the SearchRequest.ContentSearchSpec.search_result_mode is set to CHUNKS. */
		chunk: Option[Schema.GoogleCloudDiscoveryengineV1Chunk] = None
	)
	
	case class GoogleCloudDiscoveryengineV1Document(
	  /** The structured JSON data for the document. It should conform to the registered Schema or an `INVALID_ARGUMENT` error is thrown. */
		structData: Option[Map[String, JsValue]] = None,
	  /** The JSON string representation of the document. It should conform to the registered Schema or an `INVALID_ARGUMENT` error is thrown. */
		jsonData: Option[String] = None,
	  /** Immutable. The full resource name of the document. Format: `projects/{project}/locations/{location}/collections/{collection}/dataStores/{data_store}/branches/{branch}/documents/{document_id}`. This field must be a UTF-8 encoded string with a length limit of 1024 characters. */
		name: Option[String] = None,
	  /** Immutable. The identifier of the document. Id should conform to [RFC-1034](https://tools.ietf.org/html/rfc1034) standard with a length limit of 63 characters. */
		id: Option[String] = None,
	  /** The identifier of the schema located in the same data store. */
		schemaId: Option[String] = None,
	  /** The unstructured data linked to this document. Content must be set if this document is under a `CONTENT_REQUIRED` data store. */
		content: Option[Schema.GoogleCloudDiscoveryengineV1DocumentContent] = None,
	  /** The identifier of the parent document. Currently supports at most two level document hierarchy. Id should conform to [RFC-1034](https://tools.ietf.org/html/rfc1034) standard with a length limit of 63 characters. */
		parentDocumentId: Option[String] = None,
	  /** Output only. This field is OUTPUT_ONLY. It contains derived data that are not in the original input document. */
		derivedStructData: Option[Map[String, JsValue]] = None,
	  /** Output only. The last time the document was indexed. If this field is set, the document could be returned in search results. This field is OUTPUT_ONLY. If this field is not populated, it means the document has never been indexed. */
		indexTime: Option[String] = None,
	  /** Output only. The index status of the document. &#42; If document is indexed successfully, the index_time field is populated. &#42; Otherwise, if document is not indexed due to errors, the error_samples field is populated. &#42; Otherwise, index_status is unset. */
		indexStatus: Option[Schema.GoogleCloudDiscoveryengineV1DocumentIndexStatus] = None
	)
	
	case class GoogleCloudDiscoveryengineV1DocumentContent(
	  /** The content represented as a stream of bytes. The maximum length is 1,000,000 bytes (1 MB / ~0.95 MiB). Note: As with all `bytes` fields, this field is represented as pure binary in Protocol Buffers and base64-encoded string in JSON. For example, `abc123!?$&#42;&()'-=@~` should be represented as `YWJjMTIzIT8kKiYoKSctPUB+` in JSON. See https://developers.google.com/protocol-buffers/docs/proto3#json. */
		rawBytes: Option[String] = None,
	  /** The URI of the content. Only Cloud Storage URIs (e.g. `gs://bucket-name/path/to/file`) are supported. The maximum file size is 2.5 MB for text-based formats, 200 MB for other formats. */
		uri: Option[String] = None,
	  /** The MIME type of the content. Supported types: &#42; `application/pdf` (PDF, only native PDFs are supported for now) &#42; `text/html` (HTML) &#42; `application/vnd.openxmlformats-officedocument.wordprocessingml.document` (DOCX) &#42; `application/vnd.openxmlformats-officedocument.presentationml.presentation` (PPTX) &#42; `text/plain` (TXT) See https://www.iana.org/assignments/media-types/media-types.xhtml. */
		mimeType: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1DocumentIndexStatus(
	  /** The time when the document was indexed. If this field is populated, it means the document has been indexed. */
		indexTime: Option[String] = None,
	  /** A sample of errors encountered while indexing the document. If this field is populated, the document is not indexed due to errors. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1Chunk(
	  /** The full resource name of the chunk. Format: `projects/{project}/locations/{location}/collections/{collection}/dataStores/{data_store}/branches/{branch}/documents/{document_id}/chunks/{chunk_id}`. This field must be a UTF-8 encoded string with a length limit of 1024 characters. */
		name: Option[String] = None,
	  /** Unique chunk ID of the current chunk. */
		id: Option[String] = None,
	  /** Content is a string from a document (parsed content). */
		content: Option[String] = None,
	  /** Output only. Represents the relevance score based on similarity. Higher score indicates higher chunk relevance. The score is in range [-1.0, 1.0]. Only populated on SearchResponse. */
		relevanceScore: Option[BigDecimal] = None,
	  /** Metadata of the document from the current chunk. */
		documentMetadata: Option[Schema.GoogleCloudDiscoveryengineV1ChunkDocumentMetadata] = None,
	  /** Output only. This field is OUTPUT_ONLY. It contains derived data that are not in the original input document. */
		derivedStructData: Option[Map[String, JsValue]] = None,
	  /** Page span of the chunk. */
		pageSpan: Option[Schema.GoogleCloudDiscoveryengineV1ChunkPageSpan] = None,
	  /** Output only. Metadata of the current chunk. */
		chunkMetadata: Option[Schema.GoogleCloudDiscoveryengineV1ChunkChunkMetadata] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ChunkDocumentMetadata(
	  /** Uri of the document. */
		uri: Option[String] = None,
	  /** Title of the document. */
		title: Option[String] = None,
	  /** Data representation. The structured JSON data for the document. It should conform to the registered Schema or an `INVALID_ARGUMENT` error is thrown. */
		structData: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ChunkPageSpan(
	  /** The start page of the chunk. */
		pageStart: Option[Int] = None,
	  /** The end page of the chunk. */
		pageEnd: Option[Int] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ChunkChunkMetadata(
	  /** The previous chunks of the current chunk. The number is controlled by SearchRequest.ContentSearchSpec.ChunkSpec.num_previous_chunks. This field is only populated on SearchService.Search API. */
		previousChunks: Option[List[Schema.GoogleCloudDiscoveryengineV1Chunk]] = None,
	  /** The next chunks of the current chunk. The number is controlled by SearchRequest.ContentSearchSpec.ChunkSpec.num_next_chunks. This field is only populated on SearchService.Search API. */
		nextChunks: Option[List[Schema.GoogleCloudDiscoveryengineV1Chunk]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchResponseFacet(
	  /** The key for this facet. For example, `"colors"` or `"price"`. It matches SearchRequest.FacetSpec.FacetKey.key. */
		key: Option[String] = None,
	  /** The facet values for this field. */
		values: Option[List[Schema.GoogleCloudDiscoveryengineV1SearchResponseFacetFacetValue]] = None,
	  /** Whether the facet is dynamically generated. */
		dynamicFacet: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchResponseFacetFacetValue(
	  /** Text value of a facet, such as "Black" for facet "colors". */
		value: Option[String] = None,
	  /** Interval value for a facet, such as 10, 20) for facet "price". It matches [SearchRequest.FacetSpec.FacetKey.intervals. */
		interval: Option[Schema.GoogleCloudDiscoveryengineV1Interval] = None,
	  /** Number of items that have this facet value. */
		count: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1SearchResponseSummary {
		enum SummarySkippedReasonsEnum extends Enum[SummarySkippedReasonsEnum] { case SUMMARY_SKIPPED_REASON_UNSPECIFIED, ADVERSARIAL_QUERY_IGNORED, NON_SUMMARY_SEEKING_QUERY_IGNORED, OUT_OF_DOMAIN_QUERY_IGNORED, POTENTIAL_POLICY_VIOLATION, LLM_ADDON_NOT_ENABLED, NO_RELEVANT_CONTENT, JAIL_BREAKING_QUERY_IGNORED, CUSTOMER_POLICY_VIOLATION, NON_SUMMARY_SEEKING_QUERY_IGNORED_V2 }
	}
	case class GoogleCloudDiscoveryengineV1SearchResponseSummary(
	  /** The summary content. */
		summaryText: Option[String] = None,
	  /** Additional summary-skipped reasons. This provides the reason for ignored cases. If nothing is skipped, this field is not set. */
		summarySkippedReasons: Option[List[Schema.GoogleCloudDiscoveryengineV1SearchResponseSummary.SummarySkippedReasonsEnum]] = None,
	  /** A collection of Safety Attribute categories and their associated confidence scores. */
		safetyAttributes: Option[Schema.GoogleCloudDiscoveryengineV1SearchResponseSummarySafetyAttributes] = None,
	  /** Summary with metadata information. */
		summaryWithMetadata: Option[Schema.GoogleCloudDiscoveryengineV1SearchResponseSummarySummaryWithMetadata] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchResponseSummarySafetyAttributes(
	  /** The display names of Safety Attribute categories associated with the generated content. Order matches the Scores. */
		categories: Option[List[String]] = None,
	  /** The confidence scores of the each category, higher value means higher confidence. Order matches the Categories. */
		scores: Option[List[BigDecimal]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchResponseSummarySummaryWithMetadata(
	  /** Summary text with no citation information. */
		summary: Option[String] = None,
	  /** Citation metadata for given summary. */
		citationMetadata: Option[Schema.GoogleCloudDiscoveryengineV1SearchResponseSummaryCitationMetadata] = None,
	  /** Document References. */
		references: Option[List[Schema.GoogleCloudDiscoveryengineV1SearchResponseSummaryReference]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchResponseSummaryCitationMetadata(
	  /** Citations for segments. */
		citations: Option[List[Schema.GoogleCloudDiscoveryengineV1SearchResponseSummaryCitation]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchResponseSummaryCitation(
	  /** Index indicates the start of the segment, measured in bytes/unicode. */
		startIndex: Option[String] = None,
	  /** End of the attributed segment, exclusive. */
		endIndex: Option[String] = None,
	  /** Citation sources for the attributed segment. */
		sources: Option[List[Schema.GoogleCloudDiscoveryengineV1SearchResponseSummaryCitationSource]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchResponseSummaryCitationSource(
	  /** Document reference index from SummaryWithMetadata.references. It is 0-indexed and the value will be zero if the reference_index is not set explicitly. */
		referenceIndex: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchResponseSummaryReference(
	  /** Title of the document. */
		title: Option[String] = None,
	  /** Required. Document.name of the document. Full resource name of the referenced document, in the format `projects/&#42;/locations/&#42;/collections/&#42;/dataStores/&#42;/branches/&#42;/documents/&#42;`. */
		document: Option[String] = None,
	  /** Cloud Storage or HTTP uri for the document. */
		uri: Option[String] = None,
	  /** List of cited chunk contents derived from document content. */
		chunkContents: Option[List[Schema.GoogleCloudDiscoveryengineV1SearchResponseSummaryReferenceChunkContent]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchResponseSummaryReferenceChunkContent(
	  /** Chunk textual content. */
		content: Option[String] = None,
	  /** Page identifier. */
		pageIdentifier: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchResponseQueryExpansionInfo(
	  /** Bool describing whether query expansion has occurred. */
		expandedQuery: Option[Boolean] = None,
	  /** Number of pinned results. This field will only be set when expansion happens and SearchRequest.QueryExpansionSpec.pin_unexpanded_results is set to true. */
		pinnedResultCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchResponseSessionInfo(
	  /** Name of the session. If the auto-session mode is used (when SearchRequest.session ends with "-"), this field holds the newly generated session name. */
		name: Option[String] = None,
	  /** Query ID that corresponds to this search API call. One session can have multiple turns, each with a unique query ID. By specifying the session name and this query ID in the Answer API call, the answer generation happens in the context of the search results from this search call. */
		queryId: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ConverseConversationRequest(
	  /** Required. Current user input. */
		query: Option[Schema.GoogleCloudDiscoveryengineV1TextInput] = None,
	  /** The resource name of the Serving Config to use. Format: `projects/{project}/locations/{location}/collections/{collection}/dataStores/{data_store_id}/servingConfigs/{serving_config_id}` If this is not set, the default serving config will be used. */
		servingConfig: Option[String] = None,
	  /** The conversation to be used by auto session only. The name field will be ignored as we automatically assign new name for the conversation in auto session. */
		conversation: Option[Schema.GoogleCloudDiscoveryengineV1Conversation] = None,
	  /** Whether to turn on safe search. */
		safeSearch: Option[Boolean] = None,
	  /** The user labels applied to a resource must meet the following requirements: &#42; Each resource can have multiple labels, up to a maximum of 64. &#42; Each label must be a key-value pair. &#42; Keys have a minimum length of 1 character and a maximum length of 63 characters and cannot be empty. Values can be empty and have a maximum length of 63 characters. &#42; Keys and values can contain only lowercase letters, numeric characters, underscores, and dashes. All characters must use UTF-8 encoding, and international characters are allowed. &#42; The key portion of a label must be unique. However, you can use the same key with multiple resources. &#42; Keys must start with a lowercase letter or international character. See [Google Cloud Document](https://cloud.google.com/resource-manager/docs/creating-managing-labels#requirements) for more details. */
		userLabels: Option[Map[String, String]] = None,
	  /** A specification for configuring the summary returned in the response. */
		summarySpec: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestContentSearchSpecSummarySpec] = None,
	  /** The filter syntax consists of an expression language for constructing a predicate from one or more fields of the documents being filtered. Filter expression is case-sensitive. This will be used to filter search results which may affect the summary response. If this field is unrecognizable, an `INVALID_ARGUMENT` is returned. Filtering in Vertex AI Search is done by mapping the LHS filter key to a key property defined in the Vertex AI Search backend -- this mapping is defined by the customer in their schema. For example a media customer might have a field 'name' in their schema. In this case the filter would look like this: filter --> name:'ANY("king kong")' For more information about filtering including syntax and filter operators, see [Filter](https://cloud.google.com/generative-ai-app-builder/docs/filter-search-metadata) */
		filter: Option[String] = None,
	  /** Boost specification to boost certain documents in search results which may affect the converse response. For more information on boosting, see [Boosting](https://cloud.google.com/retail/docs/boosting#boost) */
		boostSpec: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestBoostSpec] = None
	)
	
	case class GoogleCloudDiscoveryengineV1TextInput(
	  /** Text input. */
		input: Option[String] = None,
	  /** Conversation context of the input. */
		context: Option[Schema.GoogleCloudDiscoveryengineV1ConversationContext] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ConversationContext(
	  /** The current list of documents the user is seeing. It contains the document resource references. */
		contextDocuments: Option[List[String]] = None,
	  /** The current active document the user opened. It contains the document resource reference. */
		activeDocument: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1Conversation {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, IN_PROGRESS, COMPLETED }
	}
	case class GoogleCloudDiscoveryengineV1Conversation(
	  /** Immutable. Fully qualified name `projects/{project}/locations/global/collections/{collection}/dataStore/&#42;/conversations/&#42;` or `projects/{project}/locations/global/collections/{collection}/engines/&#42;/conversations/&#42;`. */
		name: Option[String] = None,
	  /** The state of the Conversation. */
		state: Option[Schema.GoogleCloudDiscoveryengineV1Conversation.StateEnum] = None,
	  /** A unique identifier for tracking users. */
		userPseudoId: Option[String] = None,
	  /** Conversation messages. */
		messages: Option[List[Schema.GoogleCloudDiscoveryengineV1ConversationMessage]] = None,
	  /** Output only. The time the conversation started. */
		startTime: Option[String] = None,
	  /** Output only. The time the conversation finished. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ConversationMessage(
	  /** User text input. */
		userInput: Option[Schema.GoogleCloudDiscoveryengineV1TextInput] = None,
	  /** Search reply. */
		reply: Option[Schema.GoogleCloudDiscoveryengineV1Reply] = None,
	  /** Output only. Message creation timestamp. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1Reply(
	  /** Summary based on search results. */
		summary: Option[Schema.GoogleCloudDiscoveryengineV1SearchResponseSummary] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ConverseConversationResponse(
	  /** Answer to the current query. */
		reply: Option[Schema.GoogleCloudDiscoveryengineV1Reply] = None,
	  /** Updated conversation including the answer. */
		conversation: Option[Schema.GoogleCloudDiscoveryengineV1Conversation] = None,
	  /** Search Results. */
		searchResults: Option[List[Schema.GoogleCloudDiscoveryengineV1SearchResponseSearchResult]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ListConversationsResponse(
	  /** All the Conversations for a given data store. */
		conversations: Option[List[Schema.GoogleCloudDiscoveryengineV1Conversation]] = None,
	  /** Pagination token, if not returned indicates the last page. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerQueryRequest(
	  /** Required. Current user query. */
		query: Option[Schema.GoogleCloudDiscoveryengineV1Query] = None,
	  /** The session resource name. Not required. When session field is not set, the API is in sessionless mode. We support auto session mode: users can use the wildcard symbol `-` as session ID. A new ID will be automatically generated and assigned. */
		session: Option[String] = None,
	  /** Model specification. */
		safetySpec: Option[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestSafetySpec] = None,
	  /** Related questions specification. */
		relatedQuestionsSpec: Option[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestRelatedQuestionsSpec] = None,
	  /** Optional. Grounding specification. */
		groundingSpec: Option[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestGroundingSpec] = None,
	  /** Answer generation specification. */
		answerGenerationSpec: Option[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestAnswerGenerationSpec] = None,
	  /** Search specification. */
		searchSpec: Option[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpec] = None,
	  /** Query understanding specification. */
		queryUnderstandingSpec: Option[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestQueryUnderstandingSpec] = None,
	  /** Deprecated: This field is deprecated. Streaming Answer API will be supported. Asynchronous mode control. If enabled, the response will be returned with answer/session resource name without final answer. The API users need to do the polling to get the latest status of answer/session by calling ConversationalSearchService.GetAnswer or ConversationalSearchService.GetSession method. */
		asynchronousMode: Option[Boolean] = None,
	  /** A unique identifier for tracking visitors. For example, this could be implemented with an HTTP cookie, which should be able to uniquely identify a visitor on a single device. This unique identifier should not change if the visitor logs in or out of the website. This field should NOT have a fixed value such as `unknown_visitor`. The field must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		userPseudoId: Option[String] = None,
	  /** The user labels applied to a resource must meet the following requirements: &#42; Each resource can have multiple labels, up to a maximum of 64. &#42; Each label must be a key-value pair. &#42; Keys have a minimum length of 1 character and a maximum length of 63 characters and cannot be empty. Values can be empty and have a maximum length of 63 characters. &#42; Keys and values can contain only lowercase letters, numeric characters, underscores, and dashes. All characters must use UTF-8 encoding, and international characters are allowed. &#42; The key portion of a label must be unique. However, you can use the same key with multiple resources. &#42; Keys must start with a lowercase letter or international character. See [Google Cloud Document](https://cloud.google.com/resource-manager/docs/creating-managing-labels#requirements) for more details. */
		userLabels: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1Query(
	  /** Plain text. */
		text: Option[String] = None,
	  /** Unique Id for the query. */
		queryId: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerQueryRequestSafetySpec(
	  /** Enable the safety filtering on the answer response. It is false by default. */
		enable: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerQueryRequestRelatedQuestionsSpec(
	  /** Enable related questions feature if true. */
		enable: Option[Boolean] = None
	)
	
	object GoogleCloudDiscoveryengineV1AnswerQueryRequestGroundingSpec {
		enum FilteringLevelEnum extends Enum[FilteringLevelEnum] { case FILTERING_LEVEL_UNSPECIFIED, FILTERING_LEVEL_LOW, FILTERING_LEVEL_HIGH }
	}
	case class GoogleCloudDiscoveryengineV1AnswerQueryRequestGroundingSpec(
	  /** Optional. Specifies whether to include grounding_supports in the answer. The default value is `false`. When this field is set to `true`, returned answer will have `grounding_score` and will contain GroundingSupports for each claim. */
		includeGroundingSupports: Option[Boolean] = None,
	  /** Optional. Specifies whether to enable the filtering based on grounding score and at what level. */
		filteringLevel: Option[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestGroundingSpec.FilteringLevelEnum] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerQueryRequestAnswerGenerationSpec(
	  /** Answer generation model specification. */
		modelSpec: Option[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestAnswerGenerationSpecModelSpec] = None,
	  /** Answer generation prompt specification. */
		promptSpec: Option[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestAnswerGenerationSpecPromptSpec] = None,
	  /** Specifies whether to include citation metadata in the answer. The default value is `false`. */
		includeCitations: Option[Boolean] = None,
	  /** Language code for Answer. Use language tags defined by [BCP47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt). Note: This is an experimental feature. */
		answerLanguageCode: Option[String] = None,
	  /** Specifies whether to filter out adversarial queries. The default value is `false`. Google employs search-query classification to detect adversarial queries. No answer is returned if the search query is classified as an adversarial query. For example, a user might ask a question regarding negative comments about the company or submit a query designed to generate unsafe, policy-violating output. If this field is set to `true`, we skip generating answers for adversarial queries and return fallback messages instead. */
		ignoreAdversarialQuery: Option[Boolean] = None,
	  /** Specifies whether to filter out queries that are not answer-seeking. The default value is `false`. Google employs search-query classification to detect answer-seeking queries. No answer is returned if the search query is classified as a non-answer seeking query. If this field is set to `true`, we skip generating answers for non-answer seeking queries and return fallback messages instead. */
		ignoreNonAnswerSeekingQuery: Option[Boolean] = None,
	  /** Specifies whether to filter out queries that have low relevance. If this field is set to `false`, all search results are used regardless of relevance to generate answers. If set to `true` or unset, the behavior will be determined automatically by the service. */
		ignoreLowRelevantContent: Option[Boolean] = None,
	  /** Optional. Specifies whether to filter out jail-breaking queries. The default value is `false`. Google employs search-query classification to detect jail-breaking queries. No summary is returned if the search query is classified as a jail-breaking query. A user might add instructions to the query to change the tone, style, language, content of the answer, or ask the model to act as a different entity, e.g. "Reply in the tone of a competing company's CEO". If this field is set to `true`, we skip generating summaries for jail-breaking queries and return fallback messages instead. */
		ignoreJailBreakingQuery: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerQueryRequestAnswerGenerationSpecModelSpec(
	  /** Model version. If not set, it will use the default stable model. Allowed values are: stable, preview. */
		modelVersion: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerQueryRequestAnswerGenerationSpecPromptSpec(
	  /** Customized preamble. */
		preamble: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpec(
	  /** Search parameters. */
		searchParams: Option[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpecSearchParams] = None,
	  /** Search result list. */
		searchResultList: Option[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpecSearchResultList] = None
	)
	
	object GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpecSearchParams {
		enum SearchResultModeEnum extends Enum[SearchResultModeEnum] { case SEARCH_RESULT_MODE_UNSPECIFIED, DOCUMENTS, CHUNKS }
	}
	case class GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpecSearchParams(
	  /** Number of search results to return. The default value is 10. */
		maxReturnResults: Option[Int] = None,
	  /** The filter syntax consists of an expression language for constructing a predicate from one or more fields of the documents being filtered. Filter expression is case-sensitive. This will be used to filter search results which may affect the Answer response. If this field is unrecognizable, an `INVALID_ARGUMENT` is returned. Filtering in Vertex AI Search is done by mapping the LHS filter key to a key property defined in the Vertex AI Search backend -- this mapping is defined by the customer in their schema. For example a media customers might have a field 'name' in their schema. In this case the filter would look like this: filter --> name:'ANY("king kong")' For more information about filtering including syntax and filter operators, see [Filter](https://cloud.google.com/generative-ai-app-builder/docs/filter-search-metadata) */
		filter: Option[String] = None,
	  /** Boost specification to boost certain documents in search results which may affect the answer query response. For more information on boosting, see [Boosting](https://cloud.google.com/retail/docs/boosting#boost) */
		boostSpec: Option[Schema.GoogleCloudDiscoveryengineV1SearchRequestBoostSpec] = None,
	  /** The order in which documents are returned. Documents can be ordered by a field in an Document object. Leave it unset if ordered by relevance. `order_by` expression is case-sensitive. For more information on ordering, see [Ordering](https://cloud.google.com/retail/docs/filter-and-order#order) If this field is unrecognizable, an `INVALID_ARGUMENT` is returned. */
		orderBy: Option[String] = None,
	  /** Specifies the search result mode. If unspecified, the search result mode defaults to `DOCUMENTS`. See [parse and chunk documents](https://cloud.google.com/generative-ai-app-builder/docs/parse-chunk-documents) */
		searchResultMode: Option[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpecSearchParams.SearchResultModeEnum] = None,
	  /** Specs defining dataStores to filter on in a search call and configurations for those dataStores. This is only considered for engines with multiple dataStores use case. For single dataStore within an engine, they should use the specs at the top level. */
		dataStoreSpecs: Option[List[Schema.GoogleCloudDiscoveryengineV1SearchRequestDataStoreSpec]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpecSearchResultList(
	  /** Search results. */
		searchResults: Option[List[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpecSearchResultListSearchResult]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpecSearchResultListSearchResult(
	  /** Unstructured document information. */
		unstructuredDocumentInfo: Option[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpecSearchResultListSearchResultUnstructuredDocumentInfo] = None,
	  /** Chunk information. */
		chunkInfo: Option[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpecSearchResultListSearchResultChunkInfo] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpecSearchResultListSearchResultUnstructuredDocumentInfo(
	  /** Document resource name. */
		document: Option[String] = None,
	  /** URI for the document. */
		uri: Option[String] = None,
	  /** Title. */
		title: Option[String] = None,
	  /** List of document contexts. The content will be used for Answer Generation. This is supposed to be the main content of the document that can be long and comprehensive. */
		documentContexts: Option[List[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpecSearchResultListSearchResultUnstructuredDocumentInfoDocumentContext]] = None,
	  /** List of extractive segments. */
		extractiveSegments: Option[List[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpecSearchResultListSearchResultUnstructuredDocumentInfoExtractiveSegment]] = None,
	  /** Deprecated: This field is deprecated and will have no effect on the Answer generation. Please use document_contexts and extractive_segments fields. List of extractive answers. */
		extractiveAnswers: Option[List[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpecSearchResultListSearchResultUnstructuredDocumentInfoExtractiveAnswer]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpecSearchResultListSearchResultUnstructuredDocumentInfoDocumentContext(
	  /** Page identifier. */
		pageIdentifier: Option[String] = None,
	  /** Document content to be used for answer generation. */
		content: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpecSearchResultListSearchResultUnstructuredDocumentInfoExtractiveSegment(
	  /** Page identifier. */
		pageIdentifier: Option[String] = None,
	  /** Extractive segment content. */
		content: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpecSearchResultListSearchResultUnstructuredDocumentInfoExtractiveAnswer(
	  /** Page identifier. */
		pageIdentifier: Option[String] = None,
	  /** Extractive answer content. */
		content: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpecSearchResultListSearchResultChunkInfo(
	  /** Chunk resource name. */
		chunk: Option[String] = None,
	  /** Chunk textual content. */
		content: Option[String] = None,
	  /** Metadata of the document from the current chunk. */
		documentMetadata: Option[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpecSearchResultListSearchResultChunkInfoDocumentMetadata] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerQueryRequestSearchSpecSearchResultListSearchResultChunkInfoDocumentMetadata(
	  /** Uri of the document. */
		uri: Option[String] = None,
	  /** Title of the document. */
		title: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerQueryRequestQueryUnderstandingSpec(
	  /** Query classification specification. */
		queryClassificationSpec: Option[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestQueryUnderstandingSpecQueryClassificationSpec] = None,
	  /** Query rephraser specification. */
		queryRephraserSpec: Option[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestQueryUnderstandingSpecQueryRephraserSpec] = None
	)
	
	object GoogleCloudDiscoveryengineV1AnswerQueryRequestQueryUnderstandingSpecQueryClassificationSpec {
		enum TypesEnum extends Enum[TypesEnum] { case TYPE_UNSPECIFIED, ADVERSARIAL_QUERY, NON_ANSWER_SEEKING_QUERY, JAIL_BREAKING_QUERY, NON_ANSWER_SEEKING_QUERY_V2 }
	}
	case class GoogleCloudDiscoveryengineV1AnswerQueryRequestQueryUnderstandingSpecQueryClassificationSpec(
	  /** Enabled query classification types. */
		types: Option[List[Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequestQueryUnderstandingSpecQueryClassificationSpec.TypesEnum]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerQueryRequestQueryUnderstandingSpecQueryRephraserSpec(
	  /** Disable query rephraser. */
		disable: Option[Boolean] = None,
	  /** Max rephrase steps. The max number is 5 steps. If not set or set to < 1, it will be set to 1 by default. */
		maxRephraseSteps: Option[Int] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerQueryResponse(
	  /** Answer resource object. If AnswerQueryRequest.QueryUnderstandingSpec.QueryRephraserSpec.max_rephrase_steps is greater than 1, use Answer.name to fetch answer information using ConversationalSearchService.GetAnswer API. */
		answer: Option[Schema.GoogleCloudDiscoveryengineV1Answer] = None,
	  /** Session resource object. It will be only available when session field is set and valid in the AnswerQueryRequest request. */
		session: Option[Schema.GoogleCloudDiscoveryengineV1Session] = None,
	  /** A global unique ID used for logging. */
		answerQueryToken: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1Answer {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, IN_PROGRESS, FAILED, SUCCEEDED }
		enum AnswerSkippedReasonsEnum extends Enum[AnswerSkippedReasonsEnum] { case ANSWER_SKIPPED_REASON_UNSPECIFIED, ADVERSARIAL_QUERY_IGNORED, NON_ANSWER_SEEKING_QUERY_IGNORED, OUT_OF_DOMAIN_QUERY_IGNORED, POTENTIAL_POLICY_VIOLATION, NO_RELEVANT_CONTENT, JAIL_BREAKING_QUERY_IGNORED, CUSTOMER_POLICY_VIOLATION, NON_ANSWER_SEEKING_QUERY_IGNORED_V2, LOW_GROUNDED_ANSWER }
	}
	case class GoogleCloudDiscoveryengineV1Answer(
	  /** Immutable. Fully qualified name `projects/{project}/locations/global/collections/{collection}/engines/{engine}/sessions/&#42;/answers/&#42;` */
		name: Option[String] = None,
	  /** The state of the answer generation. */
		state: Option[Schema.GoogleCloudDiscoveryengineV1Answer.StateEnum] = None,
	  /** The textual answer. */
		answerText: Option[String] = None,
	  /** Citations. */
		citations: Option[List[Schema.GoogleCloudDiscoveryengineV1AnswerCitation]] = None,
	  /** References. */
		references: Option[List[Schema.GoogleCloudDiscoveryengineV1AnswerReference]] = None,
	  /** Suggested related questions. */
		relatedQuestions: Option[List[String]] = None,
	  /** Answer generation steps. */
		steps: Option[List[Schema.GoogleCloudDiscoveryengineV1AnswerStep]] = None,
	  /** Query understanding information. */
		queryUnderstandingInfo: Option[Schema.GoogleCloudDiscoveryengineV1AnswerQueryUnderstandingInfo] = None,
	  /** Additional answer-skipped reasons. This provides the reason for ignored cases. If nothing is skipped, this field is not set. */
		answerSkippedReasons: Option[List[Schema.GoogleCloudDiscoveryengineV1Answer.AnswerSkippedReasonsEnum]] = None,
	  /** Output only. Answer creation timestamp. */
		createTime: Option[String] = None,
	  /** Output only. Answer completed timestamp. */
		completeTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerCitation(
	  /** Index indicates the start of the segment, measured in bytes (UTF-8 unicode). */
		startIndex: Option[String] = None,
	  /** End of the attributed segment, exclusive. */
		endIndex: Option[String] = None,
	  /** Citation sources for the attributed segment. */
		sources: Option[List[Schema.GoogleCloudDiscoveryengineV1AnswerCitationSource]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerCitationSource(
	  /** ID of the citation source. */
		referenceId: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerReference(
	  /** Unstructured document information. */
		unstructuredDocumentInfo: Option[Schema.GoogleCloudDiscoveryengineV1AnswerReferenceUnstructuredDocumentInfo] = None,
	  /** Chunk information. */
		chunkInfo: Option[Schema.GoogleCloudDiscoveryengineV1AnswerReferenceChunkInfo] = None,
	  /** Structured document information. */
		structuredDocumentInfo: Option[Schema.GoogleCloudDiscoveryengineV1AnswerReferenceStructuredDocumentInfo] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerReferenceUnstructuredDocumentInfo(
	  /** Document resource name. */
		document: Option[String] = None,
	  /** URI for the document. */
		uri: Option[String] = None,
	  /** Title. */
		title: Option[String] = None,
	  /** List of cited chunk contents derived from document content. */
		chunkContents: Option[List[Schema.GoogleCloudDiscoveryengineV1AnswerReferenceUnstructuredDocumentInfoChunkContent]] = None,
	  /** The structured JSON metadata for the document. It is populated from the struct data from the Chunk in search result. */
		structData: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerReferenceUnstructuredDocumentInfoChunkContent(
	  /** Chunk textual content. */
		content: Option[String] = None,
	  /** Page identifier. */
		pageIdentifier: Option[String] = None,
	  /** The relevance of the chunk for a given query. Values range from 0.0 (completely irrelevant) to 1.0 (completely relevant). This value is for informational purpose only. It may change for the same query and chunk at any time due to a model retraining or change in implementation. */
		relevanceScore: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerReferenceChunkInfo(
	  /** Chunk resource name. */
		chunk: Option[String] = None,
	  /** Chunk textual content. */
		content: Option[String] = None,
	  /** The relevance of the chunk for a given query. Values range from 0.0 (completely irrelevant) to 1.0 (completely relevant). This value is for informational purpose only. It may change for the same query and chunk at any time due to a model retraining or change in implementation. */
		relevanceScore: Option[BigDecimal] = None,
	  /** Document metadata. */
		documentMetadata: Option[Schema.GoogleCloudDiscoveryengineV1AnswerReferenceChunkInfoDocumentMetadata] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerReferenceChunkInfoDocumentMetadata(
	  /** Document resource name. */
		document: Option[String] = None,
	  /** URI for the document. */
		uri: Option[String] = None,
	  /** Title. */
		title: Option[String] = None,
	  /** Page identifier. */
		pageIdentifier: Option[String] = None,
	  /** The structured JSON metadata for the document. It is populated from the struct data from the Chunk in search result. */
		structData: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerReferenceStructuredDocumentInfo(
	  /** Document resource name. */
		document: Option[String] = None,
	  /** Structured search data. */
		structData: Option[Map[String, JsValue]] = None
	)
	
	object GoogleCloudDiscoveryengineV1AnswerStep {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, IN_PROGRESS, FAILED, SUCCEEDED }
	}
	case class GoogleCloudDiscoveryengineV1AnswerStep(
	  /** The state of the step. */
		state: Option[Schema.GoogleCloudDiscoveryengineV1AnswerStep.StateEnum] = None,
	  /** The description of the step. */
		description: Option[String] = None,
	  /** The thought of the step. */
		thought: Option[String] = None,
	  /** Actions. */
		actions: Option[List[Schema.GoogleCloudDiscoveryengineV1AnswerStepAction]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerStepAction(
	  /** Search action. */
		searchAction: Option[Schema.GoogleCloudDiscoveryengineV1AnswerStepActionSearchAction] = None,
	  /** Observation. */
		observation: Option[Schema.GoogleCloudDiscoveryengineV1AnswerStepActionObservation] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerStepActionSearchAction(
	  /** The query to search. */
		query: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerStepActionObservation(
	  /** Search results observed by the search action, it can be snippets info or chunk info, depending on the citation type set by the user. */
		searchResults: Option[List[Schema.GoogleCloudDiscoveryengineV1AnswerStepActionObservationSearchResult]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerStepActionObservationSearchResult(
	  /** Document resource name. */
		document: Option[String] = None,
	  /** URI for the document. */
		uri: Option[String] = None,
	  /** Title. */
		title: Option[String] = None,
	  /** If citation_type is DOCUMENT_LEVEL_CITATION, populate document level snippets. */
		snippetInfo: Option[List[Schema.GoogleCloudDiscoveryengineV1AnswerStepActionObservationSearchResultSnippetInfo]] = None,
	  /** If citation_type is CHUNK_LEVEL_CITATION and chunk mode is on, populate chunk info. */
		chunkInfo: Option[List[Schema.GoogleCloudDiscoveryengineV1AnswerStepActionObservationSearchResultChunkInfo]] = None,
	  /** Data representation. The structured JSON data for the document. It's populated from the struct data from the Document, or the Chunk in search result. */
		structData: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerStepActionObservationSearchResultSnippetInfo(
	  /** Snippet content. */
		snippet: Option[String] = None,
	  /** Status of the snippet defined by the search team. */
		snippetStatus: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerStepActionObservationSearchResultChunkInfo(
	  /** Chunk resource name. */
		chunk: Option[String] = None,
	  /** Chunk textual content. */
		content: Option[String] = None,
	  /** The relevance of the chunk for a given query. Values range from 0.0 (completely irrelevant) to 1.0 (completely relevant). This value is for informational purpose only. It may change for the same query and chunk at any time due to a model retraining or change in implementation. */
		relevanceScore: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AnswerQueryUnderstandingInfo(
	  /** Query classification information. */
		queryClassificationInfo: Option[List[Schema.GoogleCloudDiscoveryengineV1AnswerQueryUnderstandingInfoQueryClassificationInfo]] = None
	)
	
	object GoogleCloudDiscoveryengineV1AnswerQueryUnderstandingInfoQueryClassificationInfo {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, ADVERSARIAL_QUERY, NON_ANSWER_SEEKING_QUERY, JAIL_BREAKING_QUERY, NON_ANSWER_SEEKING_QUERY_V2 }
	}
	case class GoogleCloudDiscoveryengineV1AnswerQueryUnderstandingInfoQueryClassificationInfo(
	  /** Query classification type. */
		`type`: Option[Schema.GoogleCloudDiscoveryengineV1AnswerQueryUnderstandingInfoQueryClassificationInfo.TypeEnum] = None,
	  /** Classification output. */
		positive: Option[Boolean] = None
	)
	
	object GoogleCloudDiscoveryengineV1Session {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, IN_PROGRESS }
	}
	case class GoogleCloudDiscoveryengineV1Session(
	  /** Immutable. Fully qualified name `projects/{project}/locations/global/collections/{collection}/engines/{engine}/sessions/&#42;` */
		name: Option[String] = None,
	  /** The state of the session. */
		state: Option[Schema.GoogleCloudDiscoveryengineV1Session.StateEnum] = None,
	  /** A unique identifier for tracking users. */
		userPseudoId: Option[String] = None,
	  /** Turns. */
		turns: Option[List[Schema.GoogleCloudDiscoveryengineV1SessionTurn]] = None,
	  /** Output only. The time the session started. */
		startTime: Option[String] = None,
	  /** Output only. The time the session finished. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SessionTurn(
	  /** The user query. */
		query: Option[Schema.GoogleCloudDiscoveryengineV1Query] = None,
	  /** The resource name of the answer to the user query. Only set if the answer generation (/answer API call) happened in this turn. */
		answer: Option[String] = None,
	  /** Output only. In ConversationalSearchService.GetSession API, if GetSessionRequest.include_answer_details is set to true, this field will be populated when getting answer query session. */
		detailedAnswer: Option[Schema.GoogleCloudDiscoveryengineV1Answer] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ListSessionsResponse(
	  /** All the Sessions for a given data store. */
		sessions: Option[List[Schema.GoogleCloudDiscoveryengineV1Session]] = None,
	  /** Pagination token, if not returned indicates the last page. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1DataStore {
		enum IndustryVerticalEnum extends Enum[IndustryVerticalEnum] { case INDUSTRY_VERTICAL_UNSPECIFIED, GENERIC, MEDIA, HEALTHCARE_FHIR }
		enum SolutionTypesEnum extends Enum[SolutionTypesEnum] { case SOLUTION_TYPE_UNSPECIFIED, SOLUTION_TYPE_RECOMMENDATION, SOLUTION_TYPE_SEARCH, SOLUTION_TYPE_CHAT, SOLUTION_TYPE_GENERATIVE_CHAT }
		enum ContentConfigEnum extends Enum[ContentConfigEnum] { case CONTENT_CONFIG_UNSPECIFIED, NO_CONTENT, CONTENT_REQUIRED, PUBLIC_WEBSITE, GOOGLE_WORKSPACE }
	}
	case class GoogleCloudDiscoveryengineV1DataStore(
	  /** Immutable. The full resource name of the data store. Format: `projects/{project}/locations/{location}/collections/{collection_id}/dataStores/{data_store_id}`. This field must be a UTF-8 encoded string with a length limit of 1024 characters. */
		name: Option[String] = None,
	  /** Required. The data store display name. This field must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an INVALID_ARGUMENT error is returned. */
		displayName: Option[String] = None,
	  /** Immutable. The industry vertical that the data store registers. */
		industryVertical: Option[Schema.GoogleCloudDiscoveryengineV1DataStore.IndustryVerticalEnum] = None,
	  /** The solutions that the data store enrolls. Available solutions for each industry_vertical: &#42; `MEDIA`: `SOLUTION_TYPE_RECOMMENDATION` and `SOLUTION_TYPE_SEARCH`. &#42; `SITE_SEARCH`: `SOLUTION_TYPE_SEARCH` is automatically enrolled. Other solutions cannot be enrolled. */
		solutionTypes: Option[List[Schema.GoogleCloudDiscoveryengineV1DataStore.SolutionTypesEnum]] = None,
	  /** Output only. The id of the default Schema asscociated to this data store. */
		defaultSchemaId: Option[String] = None,
	  /** Immutable. The content config of the data store. If this field is unset, the server behavior defaults to ContentConfig.NO_CONTENT. */
		contentConfig: Option[Schema.GoogleCloudDiscoveryengineV1DataStore.ContentConfigEnum] = None,
	  /** Output only. Timestamp the DataStore was created at. */
		createTime: Option[String] = None,
	  /** Optional. Configuration for advanced site search. */
		advancedSiteSearchConfig: Option[Schema.GoogleCloudDiscoveryengineV1AdvancedSiteSearchConfig] = None,
	  /** Input only. The KMS key to be used to protect this DataStore at creation time. Must be set for requests that need to comply with CMEK Org Policy protections. If this field is set and processed successfully, the DataStore will be protected by the KMS key, as indicated in the cmek_config field. */
		kmsKeyName: Option[String] = None,
	  /** Output only. CMEK-related information for the DataStore. */
		cmekConfig: Option[Schema.GoogleCloudDiscoveryengineV1CmekConfig] = None,
	  /** Output only. Data size estimation for billing. */
		billingEstimation: Option[Schema.GoogleCloudDiscoveryengineV1DataStoreBillingEstimation] = None,
	  /** Config to store data store type configuration for workspace data. This must be set when DataStore.content_config is set as DataStore.ContentConfig.GOOGLE_WORKSPACE. */
		workspaceConfig: Option[Schema.GoogleCloudDiscoveryengineV1WorkspaceConfig] = None,
	  /** Configuration for Document understanding and enrichment. */
		documentProcessingConfig: Option[Schema.GoogleCloudDiscoveryengineV1DocumentProcessingConfig] = None,
	  /** The start schema to use for this DataStore when provisioning it. If unset, a default vertical specialized schema will be used. This field is only used by CreateDataStore API, and will be ignored if used in other APIs. This field will be omitted from all API responses including CreateDataStore API. To retrieve a schema of a DataStore, use SchemaService.GetSchema API instead. The provided schema will be validated against certain rules on schema. Learn more from [this doc](https://cloud.google.com/generative-ai-app-builder/docs/provide-schema). */
		startingSchema: Option[Schema.GoogleCloudDiscoveryengineV1Schema] = None,
	  /** Optional. Stores serving config at DataStore level. */
		servingConfigDataStore: Option[Schema.GoogleCloudDiscoveryengineV1DataStoreServingConfigDataStore] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AdvancedSiteSearchConfig(
	
	)
	
	object GoogleCloudDiscoveryengineV1CmekConfig {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, KEY_ISSUE, DELETING, UNUSABLE, ACTIVE_ROTATING }
	}
	case class GoogleCloudDiscoveryengineV1CmekConfig(
	  /** Required. Name of the CmekConfig, of the form `projects/{project}/locations/{location}/cmekConfig` or `projects/{project}/locations/{location}/cmekConfigs/{cmekConfig}`. */
		name: Option[String] = None,
	  /** Kms key resource name which will be used to encrypt resources `projects/{project}/locations/{location}/keyRings/{keyRing}/cryptoKeys/{keyId}`. */
		kmsKey: Option[String] = None,
	  /** Kms key version resource name which will be used to encrypt resources `/cryptoKeyVersions/{keyVersion}`. */
		kmsKeyVersion: Option[String] = None,
	  /** Output only. State of the CmekConfig. */
		state: Option[Schema.GoogleCloudDiscoveryengineV1CmekConfig.StateEnum] = None,
	  /** Output only. The default CmekConfig for the Customer. */
		isDefault: Option[Boolean] = None,
	  /** Output only. The timestamp of the last key rotation. */
		lastRotationTimestampMicros: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1DataStoreBillingEstimation(
	  /** Data size for structured data in terms of bytes. */
		structuredDataSize: Option[String] = None,
	  /** Data size for unstructured data in terms of bytes. */
		unstructuredDataSize: Option[String] = None,
	  /** Data size for websites in terms of bytes. */
		websiteDataSize: Option[String] = None,
	  /** Last updated timestamp for structured data. */
		structuredDataUpdateTime: Option[String] = None,
	  /** Last updated timestamp for unstructured data. */
		unstructuredDataUpdateTime: Option[String] = None,
	  /** Last updated timestamp for websites. */
		websiteDataUpdateTime: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1WorkspaceConfig {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, GOOGLE_DRIVE, GOOGLE_MAIL, GOOGLE_SITES, GOOGLE_CALENDAR, GOOGLE_CHAT, GOOGLE_GROUPS, GOOGLE_KEEP }
	}
	case class GoogleCloudDiscoveryengineV1WorkspaceConfig(
	  /** The Google Workspace data source. */
		`type`: Option[Schema.GoogleCloudDiscoveryengineV1WorkspaceConfig.TypeEnum] = None,
	  /** Obfuscated Dasher customer ID. */
		dasherCustomerId: Option[String] = None,
	  /** Optional. The super admin service account for the workspace that will be used for access token generation. For now we only use it for Native Google Drive connector data ingestion. */
		superAdminServiceAccount: Option[String] = None,
	  /** Optional. The super admin email address for the workspace that will be used for access token generation. For now we only use it for Native Google Drive connector data ingestion. */
		superAdminEmailAddress: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1DocumentProcessingConfig(
	  /** The full resource name of the Document Processing Config. Format: `projects/&#42;/locations/&#42;/collections/&#42;/dataStores/&#42;/documentProcessingConfig`. */
		name: Option[String] = None,
	  /** Whether chunking mode is enabled. */
		chunkingConfig: Option[Schema.GoogleCloudDiscoveryengineV1DocumentProcessingConfigChunkingConfig] = None,
	  /** Configurations for default Document parser. If not specified, we will configure it as default DigitalParsingConfig, and the default parsing config will be applied to all file types for Document parsing. */
		defaultParsingConfig: Option[Schema.GoogleCloudDiscoveryengineV1DocumentProcessingConfigParsingConfig] = None,
	  /** Map from file type to override the default parsing configuration based on the file type. Supported keys: &#42; `pdf`: Override parsing config for PDF files, either digital parsing, ocr parsing or layout parsing is supported. &#42; `html`: Override parsing config for HTML files, only digital parsing and layout parsing are supported. &#42; `docx`: Override parsing config for DOCX files, only digital parsing and layout parsing are supported. &#42; `pptx`: Override parsing config for PPTX files, only digital parsing and layout parsing are supported. &#42; `xlsm`: Override parsing config for XLSM files, only digital parsing and layout parsing are supported. &#42; `xlsx`: Override parsing config for XLSX files, only digital parsing and layout parsing are supported. */
		parsingConfigOverrides: Option[Map[String, Schema.GoogleCloudDiscoveryengineV1DocumentProcessingConfigParsingConfig]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1DocumentProcessingConfigChunkingConfig(
	  /** Configuration for the layout based chunking. */
		layoutBasedChunkingConfig: Option[Schema.GoogleCloudDiscoveryengineV1DocumentProcessingConfigChunkingConfigLayoutBasedChunkingConfig] = None
	)
	
	case class GoogleCloudDiscoveryengineV1DocumentProcessingConfigChunkingConfigLayoutBasedChunkingConfig(
	  /** The token size limit for each chunk. Supported values: 100-500 (inclusive). Default value: 500. */
		chunkSize: Option[Int] = None,
	  /** Whether to include appending different levels of headings to chunks from the middle of the document to prevent context loss. Default value: False. */
		includeAncestorHeadings: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1DocumentProcessingConfigParsingConfig(
	  /** Configurations applied to digital parser. */
		digitalParsingConfig: Option[Schema.GoogleCloudDiscoveryengineV1DocumentProcessingConfigParsingConfigDigitalParsingConfig] = None,
	  /** Configurations applied to OCR parser. Currently it only applies to PDFs. */
		ocrParsingConfig: Option[Schema.GoogleCloudDiscoveryengineV1DocumentProcessingConfigParsingConfigOcrParsingConfig] = None,
	  /** Configurations applied to layout parser. */
		layoutParsingConfig: Option[Schema.GoogleCloudDiscoveryengineV1DocumentProcessingConfigParsingConfigLayoutParsingConfig] = None
	)
	
	case class GoogleCloudDiscoveryengineV1DocumentProcessingConfigParsingConfigDigitalParsingConfig(
	
	)
	
	case class GoogleCloudDiscoveryengineV1DocumentProcessingConfigParsingConfigOcrParsingConfig(
	  /** [DEPRECATED] This field is deprecated. To use the additional enhanced document elements processing, please switch to `layout_parsing_config`. */
		enhancedDocumentElements: Option[List[String]] = None,
	  /** If true, will use native text instead of OCR text on pages containing native text. */
		useNativeText: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1DocumentProcessingConfigParsingConfigLayoutParsingConfig(
	
	)
	
	case class GoogleCloudDiscoveryengineV1Schema(
	  /** The structured representation of the schema. */
		structSchema: Option[Map[String, JsValue]] = None,
	  /** The JSON representation of the schema. */
		jsonSchema: Option[String] = None,
	  /** Immutable. The full resource name of the schema, in the format of `projects/{project}/locations/{location}/collections/{collection}/dataStores/{data_store}/schemas/{schema}`. This field must be a UTF-8 encoded string with a length limit of 1024 characters. */
		name: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1DataStoreServingConfigDataStore(
	  /** If set true, the DataStore will not be available for serving search requests. */
		disabledForServing: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ListDataStoresResponse(
	  /** All the customer's DataStores. */
		dataStores: Option[List[Schema.GoogleCloudDiscoveryengineV1DataStore]] = None,
	  /** A token that can be sent as ListDataStoresRequest.page_token to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ListDocumentsResponse(
	  /** The Documents. */
		documents: Option[List[Schema.GoogleCloudDiscoveryengineV1Document]] = None,
	  /** A token that can be sent as ListDocumentsRequest.page_token to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1ImportDocumentsRequest {
		enum ReconciliationModeEnum extends Enum[ReconciliationModeEnum] { case RECONCILIATION_MODE_UNSPECIFIED, INCREMENTAL, FULL }
	}
	case class GoogleCloudDiscoveryengineV1ImportDocumentsRequest(
	  /** The Inline source for the input content for documents. */
		inlineSource: Option[Schema.GoogleCloudDiscoveryengineV1ImportDocumentsRequestInlineSource] = None,
	  /** Cloud Storage location for the input content. */
		gcsSource: Option[Schema.GoogleCloudDiscoveryengineV1GcsSource] = None,
	  /** BigQuery input source. */
		bigquerySource: Option[Schema.GoogleCloudDiscoveryengineV1BigQuerySource] = None,
	  /** FhirStore input source. */
		fhirStoreSource: Option[Schema.GoogleCloudDiscoveryengineV1FhirStoreSource] = None,
	  /** Spanner input source. */
		spannerSource: Option[Schema.GoogleCloudDiscoveryengineV1SpannerSource] = None,
	  /** Cloud SQL input source. */
		cloudSqlSource: Option[Schema.GoogleCloudDiscoveryengineV1CloudSqlSource] = None,
	  /** Firestore input source. */
		firestoreSource: Option[Schema.GoogleCloudDiscoveryengineV1FirestoreSource] = None,
	  /** AlloyDB input source. */
		alloyDbSource: Option[Schema.GoogleCloudDiscoveryengineV1AlloyDbSource] = None,
	  /** Cloud Bigtable input source. */
		bigtableSource: Option[Schema.GoogleCloudDiscoveryengineV1BigtableSource] = None,
	  /** The desired location of errors incurred during the Import. */
		errorConfig: Option[Schema.GoogleCloudDiscoveryengineV1ImportErrorConfig] = None,
	  /** The mode of reconciliation between existing documents and the documents to be imported. Defaults to ReconciliationMode.INCREMENTAL. */
		reconciliationMode: Option[Schema.GoogleCloudDiscoveryengineV1ImportDocumentsRequest.ReconciliationModeEnum] = None,
	  /** Indicates which fields in the provided imported documents to update. If not set, the default is to update all fields. */
		updateMask: Option[String] = None,
	  /** Whether to automatically generate IDs for the documents if absent. If set to `true`, Document.ids are automatically generated based on the hash of the payload, where IDs may not be consistent during multiple imports. In which case ReconciliationMode.FULL is highly recommended to avoid duplicate contents. If unset or set to `false`, Document.ids have to be specified using id_field, otherwise, documents without IDs fail to be imported. Supported data sources: &#42; GcsSource. GcsSource.data_schema must be `custom` or `csv`. Otherwise, an INVALID_ARGUMENT error is thrown. &#42; BigQuerySource. BigQuerySource.data_schema must be `custom` or `csv`. Otherwise, an INVALID_ARGUMENT error is thrown. &#42; SpannerSource. &#42; CloudSqlSource. &#42; FirestoreSource. &#42; BigtableSource. */
		autoGenerateIds: Option[Boolean] = None,
	  /** The field indicates the ID field or column to be used as unique IDs of the documents. For GcsSource it is the key of the JSON field. For instance, `my_id` for JSON `{"my_id": "some_uuid"}`. For others, it may be the column name of the table where the unique ids are stored. The values of the JSON field or the table column are used as the Document.ids. The JSON field or the table column must be of string type, and the values must be set as valid strings conform to [RFC-1034](https://tools.ietf.org/html/rfc1034) with 1-63 characters. Otherwise, documents without valid IDs fail to be imported. Only set this field when auto_generate_ids is unset or set as `false`. Otherwise, an INVALID_ARGUMENT error is thrown. If it is unset, a default value `_id` is used when importing from the allowed data sources. Supported data sources: &#42; GcsSource. GcsSource.data_schema must be `custom` or `csv`. Otherwise, an INVALID_ARGUMENT error is thrown. &#42; BigQuerySource. BigQuerySource.data_schema must be `custom` or `csv`. Otherwise, an INVALID_ARGUMENT error is thrown. &#42; SpannerSource. &#42; CloudSqlSource. &#42; FirestoreSource. &#42; BigtableSource. */
		idField: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ImportDocumentsRequestInlineSource(
	  /** Required. A list of documents to update/create. Each document must have a valid Document.id. Recommended max of 100 items. */
		documents: Option[List[Schema.GoogleCloudDiscoveryengineV1Document]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1FhirStoreSource(
	  /** Required. The full resource name of the FHIR store to import data from, in the format of `projects/{project}/locations/{location}/datasets/{dataset}/fhirStores/{fhir_store}`. */
		fhirStore: Option[String] = None,
	  /** Intermediate Cloud Storage directory used for the import with a length limit of 2,000 characters. Can be specified if one wants to have the FhirStore export to a specific Cloud Storage directory. */
		gcsStagingDir: Option[String] = None,
	  /** The FHIR resource types to import. The resource types should be a subset of all [supported FHIR resource types](https://cloud.google.com/generative-ai-app-builder/docs/fhir-schema-reference#resource-level-specification). Default to all supported FHIR resource types if empty. */
		resourceTypes: Option[List[String]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SpannerSource(
	  /** The project ID that contains the Spanner source. Has a length limit of 128 characters. If not specified, inherits the project ID from the parent request. */
		projectId: Option[String] = None,
	  /** Required. The instance ID of the source Spanner table. */
		instanceId: Option[String] = None,
	  /** Required. The database ID of the source Spanner table. */
		databaseId: Option[String] = None,
	  /** Required. The table name of the Spanner database that needs to be imported. */
		tableId: Option[String] = None,
	  /** Whether to apply data boost on Spanner export. Enabling this option will incur additional cost. More info can be found [here](https://cloud.google.com/spanner/docs/databoost/databoost-overview#billing_and_quotas). */
		enableDataBoost: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1CloudSqlSource(
	  /** The project ID that contains the Cloud SQL source. Has a length limit of 128 characters. If not specified, inherits the project ID from the parent request. */
		projectId: Option[String] = None,
	  /** Required. The Cloud SQL instance to copy the data from with a length limit of 256 characters. */
		instanceId: Option[String] = None,
	  /** Required. The Cloud SQL database to copy the data from with a length limit of 256 characters. */
		databaseId: Option[String] = None,
	  /** Required. The Cloud SQL table to copy the data from with a length limit of 256 characters. */
		tableId: Option[String] = None,
	  /** Intermediate Cloud Storage directory used for the import with a length limit of 2,000 characters. Can be specified if one wants to have the Cloud SQL export to a specific Cloud Storage directory. Ensure that the Cloud SQL service account has the necessary Cloud Storage Admin permissions to access the specified Cloud Storage directory. */
		gcsStagingDir: Option[String] = None,
	  /** Option for serverless export. Enabling this option will incur additional cost. More info can be found [here](https://cloud.google.com/sql/pricing#serverless). */
		offload: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1FirestoreSource(
	  /** The project ID that the Cloud SQL source is in with a length limit of 128 characters. If not specified, inherits the project ID from the parent request. */
		projectId: Option[String] = None,
	  /** Required. The Firestore database to copy the data from with a length limit of 256 characters. */
		databaseId: Option[String] = None,
	  /** Required. The Firestore collection (or entity) to copy the data from with a length limit of 1,500 characters. */
		collectionId: Option[String] = None,
	  /** Intermediate Cloud Storage directory used for the import with a length limit of 2,000 characters. Can be specified if one wants to have the Firestore export to a specific Cloud Storage directory. Ensure that the Firestore service account has the necessary Cloud Storage Admin permissions to access the specified Cloud Storage directory. */
		gcsStagingDir: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1AlloyDbSource(
	  /** The project ID that contains the AlloyDB source. Has a length limit of 128 characters. If not specified, inherits the project ID from the parent request. */
		projectId: Option[String] = None,
	  /** Required. The AlloyDB location to copy the data from with a length limit of 256 characters. */
		locationId: Option[String] = None,
	  /** Required. The AlloyDB cluster to copy the data from with a length limit of 256 characters. */
		clusterId: Option[String] = None,
	  /** Required. The AlloyDB database to copy the data from with a length limit of 256 characters. */
		databaseId: Option[String] = None,
	  /** Required. The AlloyDB table to copy the data from with a length limit of 256 characters. */
		tableId: Option[String] = None,
	  /** Intermediate Cloud Storage directory used for the import with a length limit of 2,000 characters. Can be specified if one wants to have the AlloyDB export to a specific Cloud Storage directory. Ensure that the AlloyDB service account has the necessary Cloud Storage Admin permissions to access the specified Cloud Storage directory. */
		gcsStagingDir: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1BigtableSource(
	  /** The project ID that contains the Bigtable source. Has a length limit of 128 characters. If not specified, inherits the project ID from the parent request. */
		projectId: Option[String] = None,
	  /** Required. The instance ID of the Cloud Bigtable that needs to be imported. */
		instanceId: Option[String] = None,
	  /** Required. The table ID of the Cloud Bigtable that needs to be imported. */
		tableId: Option[String] = None,
	  /** Required. Bigtable options that contains information needed when parsing data into typed structures. For example, column type annotations. */
		bigtableOptions: Option[Schema.GoogleCloudDiscoveryengineV1BigtableOptions] = None
	)
	
	case class GoogleCloudDiscoveryengineV1BigtableOptions(
	  /** The field name used for saving row key value in the document. The name has to match the pattern `a-zA-Z0-9&#42;`. */
		keyFieldName: Option[String] = None,
	  /** The mapping from family names to an object that contains column families level information for the given column family. If a family is not present in this map it will be ignored. */
		families: Option[Map[String, Schema.GoogleCloudDiscoveryengineV1BigtableOptionsBigtableColumnFamily]] = None
	)
	
	object GoogleCloudDiscoveryengineV1BigtableOptionsBigtableColumnFamily {
		enum EncodingEnum extends Enum[EncodingEnum] { case ENCODING_UNSPECIFIED, TEXT, BINARY }
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, STRING, NUMBER, INTEGER, VAR_INTEGER, BIG_NUMERIC, BOOLEAN, JSON }
	}
	case class GoogleCloudDiscoveryengineV1BigtableOptionsBigtableColumnFamily(
	  /** The field name to use for this column family in the document. The name has to match the pattern `a-zA-Z0-9&#42;`. If not set, it is parsed from the family name with best effort. However, due to different naming patterns, field name collisions could happen, where parsing behavior is undefined. */
		fieldName: Option[String] = None,
	  /** The encoding mode of the values when the type is not STRING. Acceptable encoding values are: &#42; `TEXT`: indicates values are alphanumeric text strings. &#42; `BINARY`: indicates values are encoded using `HBase Bytes.toBytes` family of functions. This can be overridden for a specific column by listing that column in `columns` and specifying an encoding for it. */
		encoding: Option[Schema.GoogleCloudDiscoveryengineV1BigtableOptionsBigtableColumnFamily.EncodingEnum] = None,
	  /** The type of values in this column family. The values are expected to be encoded using `HBase Bytes.toBytes` function when the encoding value is set to `BINARY`. */
		`type`: Option[Schema.GoogleCloudDiscoveryengineV1BigtableOptionsBigtableColumnFamily.TypeEnum] = None,
	  /** The list of objects that contains column level information for each column. If a column is not present in this list it will be ignored. */
		columns: Option[List[Schema.GoogleCloudDiscoveryengineV1BigtableOptionsBigtableColumn]] = None
	)
	
	object GoogleCloudDiscoveryengineV1BigtableOptionsBigtableColumn {
		enum EncodingEnum extends Enum[EncodingEnum] { case ENCODING_UNSPECIFIED, TEXT, BINARY }
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, STRING, NUMBER, INTEGER, VAR_INTEGER, BIG_NUMERIC, BOOLEAN, JSON }
	}
	case class GoogleCloudDiscoveryengineV1BigtableOptionsBigtableColumn(
	  /** Required. Qualifier of the column. If it cannot be decoded with utf-8, use a base-64 encoded string instead. */
		qualifier: Option[String] = None,
	  /** The field name to use for this column in the document. The name has to match the pattern `a-zA-Z0-9&#42;`. If not set, it is parsed from the qualifier bytes with best effort. However, due to different naming patterns, field name collisions could happen, where parsing behavior is undefined. */
		fieldName: Option[String] = None,
	  /** The encoding mode of the values when the type is not `STRING`. Acceptable encoding values are: &#42; `TEXT`: indicates values are alphanumeric text strings. &#42; `BINARY`: indicates values are encoded using `HBase Bytes.toBytes` family of functions. This can be overridden for a specific column by listing that column in `columns` and specifying an encoding for it. */
		encoding: Option[Schema.GoogleCloudDiscoveryengineV1BigtableOptionsBigtableColumn.EncodingEnum] = None,
	  /** The type of values in this column family. The values are expected to be encoded using `HBase Bytes.toBytes` function when the encoding value is set to `BINARY`. */
		`type`: Option[Schema.GoogleCloudDiscoveryengineV1BigtableOptionsBigtableColumn.TypeEnum] = None
	)
	
	case class GoogleCloudDiscoveryengineV1PurgeDocumentsRequest(
	  /** Cloud Storage location for the input content. Supported `data_schema`: &#42; `document_id`: One valid Document.id per line. */
		gcsSource: Option[Schema.GoogleCloudDiscoveryengineV1GcsSource] = None,
	  /** Inline source for the input content for purge. */
		inlineSource: Option[Schema.GoogleCloudDiscoveryengineV1PurgeDocumentsRequestInlineSource] = None,
	  /** Required. Filter matching documents to purge. Only currently supported value is `&#42;` (all items). */
		filter: Option[String] = None,
	  /** The desired location of errors incurred during the purge. */
		errorConfig: Option[Schema.GoogleCloudDiscoveryengineV1PurgeErrorConfig] = None,
	  /** Actually performs the purge. If `force` is set to false, return the expected purge count without deleting any documents. */
		force: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1PurgeDocumentsRequestInlineSource(
	  /** Required. A list of full resource name of documents to purge. In the format `projects/&#42;/locations/&#42;/collections/&#42;/dataStores/&#42;/branches/&#42;/documents/&#42;`. Recommended max of 100 items. */
		documents: Option[List[String]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1PurgeErrorConfig(
	  /** Cloud Storage prefix for purge errors. This must be an empty, existing Cloud Storage directory. Purge errors are written to sharded files in this directory, one per line, as a JSON-encoded `google.rpc.Status` message. */
		gcsPrefix: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1BatchGetDocumentsMetadataResponse(
	  /** The metadata of the Documents. */
		documentsMetadata: Option[List[Schema.GoogleCloudDiscoveryengineV1BatchGetDocumentsMetadataResponseDocumentMetadata]] = None
	)
	
	object GoogleCloudDiscoveryengineV1BatchGetDocumentsMetadataResponseDocumentMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, INDEXED, NOT_IN_TARGET_SITE, NOT_IN_INDEX }
	}
	case class GoogleCloudDiscoveryengineV1BatchGetDocumentsMetadataResponseDocumentMetadata(
	  /** The value of the matcher that was used to match the Document. */
		matcherValue: Option[Schema.GoogleCloudDiscoveryengineV1BatchGetDocumentsMetadataResponseDocumentMetadataMatcherValue] = None,
	  /** The state of the document. */
		state: Option[Schema.GoogleCloudDiscoveryengineV1BatchGetDocumentsMetadataResponseDocumentMetadata.StateEnum] = None,
	  /** The timestamp of the last time the Document was last indexed. */
		lastRefreshedTime: Option[String] = None,
	  /** The data ingestion source of the Document. Allowed values are: &#42; `batch`: Data ingested via Batch API, e.g., ImportDocuments. &#42; `streaming` Data ingested via Streaming API, e.g., FHIR streaming. */
		dataIngestionSource: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1BatchGetDocumentsMetadataResponseDocumentMetadataMatcherValue(
	  /** If match by URI, the URI of the Document. */
		uri: Option[String] = None,
	  /** Format: projects/{project}/locations/{location}/datasets/{dataset}/fhirStores/{fhir_store}/fhir/{resource_type}/{fhir_resource_id} */
		fhirResource: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1Engine {
		enum SolutionTypeEnum extends Enum[SolutionTypeEnum] { case SOLUTION_TYPE_UNSPECIFIED, SOLUTION_TYPE_RECOMMENDATION, SOLUTION_TYPE_SEARCH, SOLUTION_TYPE_CHAT, SOLUTION_TYPE_GENERATIVE_CHAT }
		enum IndustryVerticalEnum extends Enum[IndustryVerticalEnum] { case INDUSTRY_VERTICAL_UNSPECIFIED, GENERIC, MEDIA, HEALTHCARE_FHIR }
	}
	case class GoogleCloudDiscoveryengineV1Engine(
	  /** Configurations for the Chat Engine. Only applicable if solution_type is SOLUTION_TYPE_CHAT. */
		chatEngineConfig: Option[Schema.GoogleCloudDiscoveryengineV1EngineChatEngineConfig] = None,
	  /** Configurations for the Search Engine. Only applicable if solution_type is SOLUTION_TYPE_SEARCH. */
		searchEngineConfig: Option[Schema.GoogleCloudDiscoveryengineV1EngineSearchEngineConfig] = None,
	  /** Output only. Additional information of the Chat Engine. Only applicable if solution_type is SOLUTION_TYPE_CHAT. */
		chatEngineMetadata: Option[Schema.GoogleCloudDiscoveryengineV1EngineChatEngineMetadata] = None,
	  /** Immutable. The fully qualified resource name of the engine. This field must be a UTF-8 encoded string with a length limit of 1024 characters. Format: `projects/{project}/locations/{location}/collections/{collection}/engines/{engine}` engine should be 1-63 characters, and valid characters are /a-z0-9&#42;/. Otherwise, an INVALID_ARGUMENT error is returned. */
		name: Option[String] = None,
	  /** Required. The display name of the engine. Should be human readable. UTF-8 encoded string with limit of 1024 characters. */
		displayName: Option[String] = None,
	  /** Output only. Timestamp the Recommendation Engine was created at. */
		createTime: Option[String] = None,
	  /** Output only. Timestamp the Recommendation Engine was last updated. */
		updateTime: Option[String] = None,
	  /** The data stores associated with this engine. For SOLUTION_TYPE_SEARCH and SOLUTION_TYPE_RECOMMENDATION type of engines, they can only associate with at most one data store. If solution_type is SOLUTION_TYPE_CHAT, multiple DataStores in the same Collection can be associated here. Note that when used in CreateEngineRequest, one DataStore id must be provided as the system will use it for necessary initializations. */
		dataStoreIds: Option[List[String]] = None,
	  /** Required. The solutions of the engine. */
		solutionType: Option[Schema.GoogleCloudDiscoveryengineV1Engine.SolutionTypeEnum] = None,
	  /** The industry vertical that the engine registers. The restriction of the Engine industry vertical is based on DataStore: If unspecified, default to `GENERIC`. Vertical on Engine has to match vertical of the DataStore linked to the engine. */
		industryVertical: Option[Schema.GoogleCloudDiscoveryengineV1Engine.IndustryVerticalEnum] = None,
	  /** Common config spec that specifies the metadata of the engine. */
		commonConfig: Option[Schema.GoogleCloudDiscoveryengineV1EngineCommonConfig] = None,
	  /** Optional. Whether to disable analytics for searches performed on this engine. */
		disableAnalytics: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1EngineChatEngineConfig(
	  /** The configurationt generate the Dialogflow agent that is associated to this Engine. Note that these configurations are one-time consumed by and passed to Dialogflow service. It means they cannot be retrieved using EngineService.GetEngine or EngineService.ListEngines API after engine creation. */
		agentCreationConfig: Option[Schema.GoogleCloudDiscoveryengineV1EngineChatEngineConfigAgentCreationConfig] = None,
	  /** The resource name of an exist Dialogflow agent to link to this Chat Engine. Customers can either provide `agent_creation_config` to create agent or provide an agent name that links the agent with the Chat engine. Format: `projects//locations//agents/`. Note that the `dialogflow_agent_to_link` are one-time consumed by and passed to Dialogflow service. It means they cannot be retrieved using EngineService.GetEngine or EngineService.ListEngines API after engine creation. Use ChatEngineMetadata.dialogflow_agent for actual agent association after Engine is created. */
		dialogflowAgentToLink: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1EngineChatEngineConfigAgentCreationConfig(
	  /** Name of the company, organization or other entity that the agent represents. Used for knowledge connector LLM prompt and for knowledge search. */
		business: Option[String] = None,
	  /** Required. The default language of the agent as a language tag. See [Language Support](https://cloud.google.com/dialogflow/docs/reference/language) for a list of the currently supported language codes. */
		defaultLanguageCode: Option[String] = None,
	  /** Required. The time zone of the agent from the [time zone database](https://www.iana.org/time-zones), e.g., America/New_York, Europe/Paris. */
		timeZone: Option[String] = None,
	  /** Agent location for Agent creation, supported values: global/us/eu. If not provided, us Engine will create Agent using us-central-1 by default; eu Engine will create Agent using eu-west-1 by default. */
		location: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1EngineSearchEngineConfig {
		enum SearchTierEnum extends Enum[SearchTierEnum] { case SEARCH_TIER_UNSPECIFIED, SEARCH_TIER_STANDARD, SEARCH_TIER_ENTERPRISE }
		enum SearchAddOnsEnum extends Enum[SearchAddOnsEnum] { case SEARCH_ADD_ON_UNSPECIFIED, SEARCH_ADD_ON_LLM }
	}
	case class GoogleCloudDiscoveryengineV1EngineSearchEngineConfig(
	  /** The search feature tier of this engine. Different tiers might have different pricing. To learn more, check the pricing documentation. Defaults to SearchTier.SEARCH_TIER_STANDARD if not specified. */
		searchTier: Option[Schema.GoogleCloudDiscoveryengineV1EngineSearchEngineConfig.SearchTierEnum] = None,
	  /** The add-on that this search engine enables. */
		searchAddOns: Option[List[Schema.GoogleCloudDiscoveryengineV1EngineSearchEngineConfig.SearchAddOnsEnum]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1EngineChatEngineMetadata(
	  /** The resource name of a Dialogflow agent, that this Chat Engine refers to. Format: `projects//locations//agents/`. */
		dialogflowAgent: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1EngineCommonConfig(
	  /** The name of the company, business or entity that is associated with the engine. Setting this may help improve LLM related features. */
		companyName: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ListEnginesResponse(
	  /** All the customer's Engines. */
		engines: Option[List[Schema.GoogleCloudDiscoveryengineV1Engine]] = None,
	  /** Not supported. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1CheckGroundingRequest(
	  /** Answer candidate to check. It can have a maximum length of 4096 tokens. */
		answerCandidate: Option[String] = None,
	  /** List of facts for the grounding check. We support up to 200 facts. */
		facts: Option[List[Schema.GoogleCloudDiscoveryengineV1GroundingFact]] = None,
	  /** Configuration of the grounding check. */
		groundingSpec: Option[Schema.GoogleCloudDiscoveryengineV1CheckGroundingSpec] = None,
	  /** The user labels applied to a resource must meet the following requirements: &#42; Each resource can have multiple labels, up to a maximum of 64. &#42; Each label must be a key-value pair. &#42; Keys have a minimum length of 1 character and a maximum length of 63 characters and cannot be empty. Values can be empty and have a maximum length of 63 characters. &#42; Keys and values can contain only lowercase letters, numeric characters, underscores, and dashes. All characters must use UTF-8 encoding, and international characters are allowed. &#42; The key portion of a label must be unique. However, you can use the same key with multiple resources. &#42; Keys must start with a lowercase letter or international character. See [Google Cloud Document](https://cloud.google.com/resource-manager/docs/creating-managing-labels#requirements) for more details. */
		userLabels: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1GroundingFact(
	  /** Text content of the fact. Can be at most 10K characters long. */
		factText: Option[String] = None,
	  /** Attributes associated with the fact. Common attributes include `source` (indicating where the fact was sourced from), `author` (indicating the author of the fact), and so on. */
		attributes: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1CheckGroundingSpec(
	  /** The threshold (in [0,1]) used for determining whether a fact must be cited for a claim in the answer candidate. Choosing a higher threshold will lead to fewer but very strong citations, while choosing a lower threshold may lead to more but somewhat weaker citations. If unset, the threshold will default to 0.6. */
		citationThreshold: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDiscoveryengineV1CheckGroundingResponse(
	  /** The support score for the input answer candidate. Higher the score, higher is the fraction of claims that are supported by the provided facts. This is always set when a response is returned. */
		supportScore: Option[BigDecimal] = None,
	  /** List of facts cited across all claims in the answer candidate. These are derived from the facts supplied in the request. */
		citedChunks: Option[List[Schema.GoogleCloudDiscoveryengineV1FactChunk]] = None,
	  /** List of facts cited across all claims in the answer candidate. These are derived from the facts supplied in the request. */
		citedFacts: Option[List[Schema.GoogleCloudDiscoveryengineV1CheckGroundingResponseCheckGroundingFactChunk]] = None,
	  /** Claim texts and citation info across all claims in the answer candidate. */
		claims: Option[List[Schema.GoogleCloudDiscoveryengineV1CheckGroundingResponseClaim]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1FactChunk(
	  /** Text content of the fact chunk. Can be at most 10K characters long. */
		chunkText: Option[String] = None,
	  /** Source from which this fact chunk was retrieved. If it was retrieved from the GroundingFacts provided in the request then this field will contain the index of the specific fact from which this chunk was retrieved. */
		source: Option[String] = None,
	  /** The index of this chunk. Currently, only used for the streaming mode. */
		index: Option[Int] = None,
	  /** More fine-grained information for the source reference. */
		sourceMetadata: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1CheckGroundingResponseCheckGroundingFactChunk(
	  /** Text content of the fact chunk. Can be at most 10K characters long. */
		chunkText: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1CheckGroundingResponseClaim(
	  /** Position indicating the start of the claim in the answer candidate, measured in bytes. */
		startPos: Option[Int] = None,
	  /** Position indicating the end of the claim in the answer candidate, exclusive. */
		endPos: Option[Int] = None,
	  /** Text for the claim in the answer candidate. Always provided regardless of whether citations or anti-citations are found. */
		claimText: Option[String] = None,
	  /** A list of indices (into 'cited_chunks') specifying the citations associated with the claim. For instance [1,3,4] means that cited_chunks[1], cited_chunks[3], cited_chunks[4] are the facts cited supporting for the claim. A citation to a fact indicates that the claim is supported by the fact. */
		citationIndices: Option[List[Int]] = None,
	  /** Indicates that this claim required grounding check. When the system decided this claim doesn't require attribution/grounding check, this field will be set to false. In that case, no grounding check was done for the claim and therefore citation_indices should not be returned. */
		groundingCheckRequired: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ProvisionProjectRequest(
	  /** Required. Set to `true` to specify that caller has read and would like to give consent to the [Terms for data use](https://cloud.google.com/retail/data-use-terms). */
		acceptDataUseTerms: Option[Boolean] = None,
	  /** Required. The version of the [Terms for data use](https://cloud.google.com/retail/data-use-terms) that caller has read and would like to give consent to. Acceptable version is `2022-11-23`, and this may change over time. */
		dataUseTermsVersion: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1RankRequest(
	  /** The identifier of the model to use. It is one of: &#42; `semantic-ranker-512@latest`: Semantic ranking model with maxiumn input token size 512. It is set to `semantic-ranker-512@latest` by default if unspecified. */
		model: Option[String] = None,
	  /** The number of results to return. If this is unset or no bigger than zero, returns all results. */
		topN: Option[Int] = None,
	  /** The query to use. */
		query: Option[String] = None,
	  /** Required. A list of records to rank. At most 200 records to rank. */
		records: Option[List[Schema.GoogleCloudDiscoveryengineV1RankingRecord]] = None,
	  /** If true, the response will contain only record ID and score. By default, it is false, the response will contain record details. */
		ignoreRecordDetailsInResponse: Option[Boolean] = None,
	  /** The user labels applied to a resource must meet the following requirements: &#42; Each resource can have multiple labels, up to a maximum of 64. &#42; Each label must be a key-value pair. &#42; Keys have a minimum length of 1 character and a maximum length of 63 characters and cannot be empty. Values can be empty and have a maximum length of 63 characters. &#42; Keys and values can contain only lowercase letters, numeric characters, underscores, and dashes. All characters must use UTF-8 encoding, and international characters are allowed. &#42; The key portion of a label must be unique. However, you can use the same key with multiple resources. &#42; Keys must start with a lowercase letter or international character. See [Google Cloud Document](https://cloud.google.com/resource-manager/docs/creating-managing-labels#requirements) for more details. */
		userLabels: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1RankingRecord(
	  /** The unique ID to represent the record. */
		id: Option[String] = None,
	  /** The title of the record. Empty by default. At least one of title or content should be set otherwise an INVALID_ARGUMENT error is thrown. */
		title: Option[String] = None,
	  /** The content of the record. Empty by default. At least one of title or content should be set otherwise an INVALID_ARGUMENT error is thrown. */
		content: Option[String] = None,
	  /** The score of this record based on the given query and selected model. The score will be rounded to 2 decimal places. If the score is close to 0, it will be rounded to 0.0001 to avoid returning unset. */
		score: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDiscoveryengineV1RankResponse(
	  /** A list of records sorted by descending score. */
		records: Option[List[Schema.GoogleCloudDiscoveryengineV1RankingRecord]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1RecommendRequest(
	  /** Required. Context about the user, what they are looking at and what action they took to trigger the Recommend request. Note that this user event detail won't be ingested to userEvent logs. Thus, a separate userEvent write request is required for event logging. Don't set UserEvent.user_pseudo_id or UserEvent.user_info.user_id to the same fixed ID for different users. If you are trying to receive non-personalized recommendations (not recommended; this can negatively impact model performance), instead set UserEvent.user_pseudo_id to a random unique ID and leave UserEvent.user_info.user_id unset. */
		userEvent: Option[Schema.GoogleCloudDiscoveryengineV1UserEvent] = None,
	  /** Maximum number of results to return. Set this property to the number of recommendation results needed. If zero, the service chooses a reasonable default. The maximum allowed value is 100. Values above 100 are set to 100. */
		pageSize: Option[Int] = None,
	  /** Filter for restricting recommendation results with a length limit of 5,000 characters. Currently, only filter expressions on the `filter_tags` attribute is supported. Examples: &#42; `(filter_tags: ANY("Red", "Blue") OR filter_tags: ANY("Hot", "Cold"))` &#42; `(filter_tags: ANY("Red", "Blue")) AND NOT (filter_tags: ANY("Green"))` If `attributeFilteringSyntax` is set to true under the `params` field, then attribute-based expressions are expected instead of the above described tag-based syntax. Examples: &#42; (launguage: ANY("en", "es")) AND NOT (categories: ANY("Movie")) &#42; (available: true) AND (launguage: ANY("en", "es")) OR (categories: ANY("Movie")) If your filter blocks all results, the API returns generic (unfiltered) popular Documents. If you only want results strictly matching the filters, set `strictFiltering` to `true` in RecommendRequest.params to receive empty results instead. Note that the API never returns Documents with `storageStatus` as `EXPIRED` or `DELETED` regardless of filter choices. */
		filter: Option[String] = None,
	  /** Use validate only mode for this recommendation query. If set to `true`, a fake model is used that returns arbitrary Document IDs. Note that the validate only mode should only be used for testing the API, or if the model is not ready. */
		validateOnly: Option[Boolean] = None,
	  /** Additional domain specific parameters for the recommendations. Allowed values: &#42; `returnDocument`: Boolean. If set to `true`, the associated Document object is returned in RecommendResponse.RecommendationResult.document. &#42; `returnScore`: Boolean. If set to true, the recommendation score corresponding to each returned Document is set in RecommendResponse.RecommendationResult.metadata. The given score indicates the probability of a Document conversion given the user's context and history. &#42; `strictFiltering`: Boolean. True by default. If set to `false`, the service returns generic (unfiltered) popular Documents instead of empty if your filter blocks all recommendation results. &#42; `diversityLevel`: String. Default empty. If set to be non-empty, then it needs to be one of: &#42; `no-diversity` &#42; `low-diversity` &#42; `medium-diversity` &#42; `high-diversity` &#42; `auto-diversity` This gives request-level control and adjusts recommendation results based on Document category. &#42; `attributeFilteringSyntax`: Boolean. False by default. If set to true, the `filter` field is interpreted according to the new, attribute-based syntax. */
		params: Option[Map[String, JsValue]] = None,
	  /** The user labels applied to a resource must meet the following requirements: &#42; Each resource can have multiple labels, up to a maximum of 64. &#42; Each label must be a key-value pair. &#42; Keys have a minimum length of 1 character and a maximum length of 63 characters and cannot be empty. Values can be empty and have a maximum length of 63 characters. &#42; Keys and values can contain only lowercase letters, numeric characters, underscores, and dashes. All characters must use UTF-8 encoding, and international characters are allowed. &#42; The key portion of a label must be unique. However, you can use the same key with multiple resources. &#42; Keys must start with a lowercase letter or international character. See [Requirements for labels](https://cloud.google.com/resource-manager/docs/creating-managing-labels#requirements) for more details. */
		userLabels: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1UserEvent(
	  /** Required. User event type. Allowed values are: Generic values: &#42; `search`: Search for Documents. &#42; `view-item`: Detailed page view of a Document. &#42; `view-item-list`: View of a panel or ordered list of Documents. &#42; `view-home-page`: View of the home page. &#42; `view-category-page`: View of a category page, e.g. Home > Men > Jeans &#42; `add-feedback`: Add a user feedback. Retail-related values: &#42; `add-to-cart`: Add an item(s) to cart, e.g. in Retail online shopping &#42; `purchase`: Purchase an item(s) Media-related values: &#42; `media-play`: Start/resume watching a video, playing a song, etc. &#42; `media-complete`: Finished or stopped midway through a video, song, etc. Custom conversion value: &#42; `conversion`: Customer defined conversion event. */
		eventType: Option[String] = None,
	  /** Optional. Conversion type. Required if UserEvent.event_type is `conversion`. This is a customer-defined conversion name in lowercase letters or numbers separated by "-", such as "watch", "good-visit" etc. Do not set the field if UserEvent.event_type is not `conversion`. This mixes the custom conversion event with predefined events like `search`, `view-item` etc. */
		conversionType: Option[String] = None,
	  /** Required. A unique identifier for tracking visitors. For example, this could be implemented with an HTTP cookie, which should be able to uniquely identify a visitor on a single device. This unique identifier should not change if the visitor log in/out of the website. Do not set the field to the same fixed ID for different users. This mixes the event history of those users together, which results in degraded model quality. The field must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an `INVALID_ARGUMENT` error is returned. The field should not contain PII or user-data. We recommend to use Google Analytics [Client ID](https://developers.google.com/analytics/devguides/collection/analyticsjs/field-reference#clientId) for this field. */
		userPseudoId: Option[String] = None,
	  /** The Engine resource name, in the form of `projects/{project}/locations/{location}/collections/{collection_id}/engines/{engine_id}`. Optional. Only required for Engine produced user events. For example, user events from blended search. */
		engine: Option[String] = None,
	  /** The DataStore resource full name, of the form `projects/{project}/locations/{location}/collections/{collection_id}/dataStores/{data_store_id}`. Optional. Only required for user events whose data store can't by determined by UserEvent.engine or UserEvent.documents. If data store is set in the parent of write/import/collect user event requests, this field can be omitted. */
		dataStore: Option[String] = None,
	  /** Only required for UserEventService.ImportUserEvents method. Timestamp of when the user event happened. */
		eventTime: Option[String] = None,
	  /** Information about the end user. */
		userInfo: Option[Schema.GoogleCloudDiscoveryengineV1UserInfo] = None,
	  /** Should set to true if the request is made directly from the end user, in which case the UserEvent.user_info.user_agent can be populated from the HTTP request. This flag should be set only if the API request is made directly from the end user such as a mobile app (and not if a gateway or a server is processing and pushing the user events). This should not be set when using the JavaScript tag in UserEventService.CollectUserEvent. */
		directUserRequest: Option[Boolean] = None,
	  /** A unique identifier for tracking a visitor session with a length limit of 128 bytes. A session is an aggregation of an end user behavior in a time span. A general guideline to populate the session_id: 1. If user has no activity for 30 min, a new session_id should be assigned. 2. The session_id should be unique across users, suggest use uuid or add UserEvent.user_pseudo_id as prefix. */
		sessionId: Option[String] = None,
	  /** Page metadata such as categories and other critical information for certain event types such as `view-category-page`. */
		pageInfo: Option[Schema.GoogleCloudDiscoveryengineV1PageInfo] = None,
	  /** Token to attribute an API response to user action(s) to trigger the event. Highly recommended for user events that are the result of RecommendationService.Recommend. This field enables accurate attribution of recommendation model performance. The value must be one of: &#42; RecommendResponse.attribution_token for events that are the result of RecommendationService.Recommend. &#42; SearchResponse.attribution_token for events that are the result of SearchService.Search. This token enables us to accurately attribute page view or conversion completion back to the event and the particular predict response containing this clicked/purchased product. If user clicks on product K in the recommendation results, pass RecommendResponse.attribution_token as a URL parameter to product K's page. When recording events on product K's page, log the RecommendResponse.attribution_token to this field. */
		attributionToken: Option[String] = None,
	  /** The filter syntax consists of an expression language for constructing a predicate from one or more fields of the documents being filtered. One example is for `search` events, the associated SearchRequest may contain a filter expression in SearchRequest.filter conforming to https://google.aip.dev/160#filtering. Similarly, for `view-item-list` events that are generated from a RecommendRequest, this field may be populated directly from RecommendRequest.filter conforming to https://google.aip.dev/160#filtering. The value must be a UTF-8 encoded string with a length limit of 1,000 characters. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		filter: Option[String] = None,
	  /** List of Documents associated with this user event. This field is optional except for the following event types: &#42; `view-item` &#42; `add-to-cart` &#42; `purchase` &#42; `media-play` &#42; `media-complete` In a `search` event, this field represents the documents returned to the end user on the current page (the end user may have not finished browsing the whole page yet). When a new page is returned to the end user, after pagination/filtering/ordering even for the same query, a new `search` event with different UserEvent.documents is desired. */
		documents: Option[List[Schema.GoogleCloudDiscoveryengineV1DocumentInfo]] = None,
	  /** Panel metadata associated with this user event. */
		panel: Option[Schema.GoogleCloudDiscoveryengineV1PanelInfo] = None,
	  /** SearchService.Search details related to the event. This field should be set for `search` event. */
		searchInfo: Option[Schema.GoogleCloudDiscoveryengineV1SearchInfo] = None,
	  /** CompletionService.CompleteQuery details related to the event. This field should be set for `search` event when autocomplete function is enabled and the user clicks a suggestion for search. */
		completionInfo: Option[Schema.GoogleCloudDiscoveryengineV1CompletionInfo] = None,
	  /** The transaction metadata (if any) associated with this user event. */
		transactionInfo: Option[Schema.GoogleCloudDiscoveryengineV1TransactionInfo] = None,
	  /** A list of identifiers for the independent experiment groups this user event belongs to. This is used to distinguish between user events associated with different experiment setups. */
		tagIds: Option[List[String]] = None,
	  /** The promotion IDs if this is an event associated with promotions. Currently, this field is restricted to at most one ID. */
		promotionIds: Option[List[String]] = None,
	  /** Extra user event features to include in the recommendation model. These attributes must NOT contain data that needs to be parsed or processed further, e.g. JSON or other encodings. If you provide custom attributes for ingested user events, also include them in the user events that you associate with prediction requests. Custom attribute formatting must be consistent between imported events and events provided with prediction requests. This lets the Discovery Engine API use those custom attributes when training models and serving predictions, which helps improve recommendation quality. This field needs to pass all below criteria, otherwise an `INVALID_ARGUMENT` error is returned: &#42; The key must be a UTF-8 encoded string with a length limit of 5,000 characters. &#42; For text attributes, at most 400 values are allowed. Empty values are not allowed. Each value must be a UTF-8 encoded string with a length limit of 256 characters. &#42; For number attributes, at most 400 values are allowed. For product recommendations, an example of extra user information is `traffic_channel`, which is how a user arrives at the site. Users can arrive at the site by coming to the site directly, coming through Google search, or in other ways. */
		attributes: Option[Map[String, Schema.GoogleCloudDiscoveryengineV1CustomAttribute]] = None,
	  /** Media-specific info. */
		mediaInfo: Option[Schema.GoogleCloudDiscoveryengineV1MediaInfo] = None,
	  /** Optional. List of panels associated with this event. Used for page-level impression data. */
		panels: Option[List[Schema.GoogleCloudDiscoveryengineV1PanelInfo]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1PageInfo(
	  /** A unique ID of a web page view. This should be kept the same for all user events triggered from the same pageview. For example, an item detail page view could trigger multiple events as the user is browsing the page. The `pageview_id` property should be kept the same for all these events so that they can be grouped together properly. When using the client side event reporting with JavaScript pixel and Google Tag Manager, this value is filled in automatically. */
		pageviewId: Option[String] = None,
	  /** The most specific category associated with a category page. To represent full path of category, use '>' sign to separate different hierarchies. If '>' is part of the category name, replace it with other character(s). Category pages include special pages such as sales or promotions. For instance, a special sale page may have the category hierarchy: `"pageCategory" : "Sales > 2017 Black Friday Deals"`. Required for `view-category-page` events. Other event types should not set this field. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		pageCategory: Option[String] = None,
	  /** Complete URL (window.location.href) of the user's current page. When using the client side event reporting with JavaScript pixel and Google Tag Manager, this value is filled in automatically. Maximum length 5,000 characters. */
		uri: Option[String] = None,
	  /** The referrer URL of the current page. When using the client side event reporting with JavaScript pixel and Google Tag Manager, this value is filled in automatically. However, some browser privacy restrictions may cause this field to be empty. */
		referrerUri: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1DocumentInfo(
	  /** The Document resource ID. */
		id: Option[String] = None,
	  /** The Document resource full name, of the form: `projects/{project}/locations/{location}/collections/{collection_id}/dataStores/{data_store_id}/branches/{branch_id}/documents/{document_id}` */
		name: Option[String] = None,
	  /** The Document URI - only allowed for website data stores. */
		uri: Option[String] = None,
	  /** Quantity of the Document associated with the user event. Defaults to 1. For example, this field is 2 if two quantities of the same Document are involved in a `add-to-cart` event. Required for events of the following event types: &#42; `add-to-cart` &#42; `purchase` */
		quantity: Option[Int] = None,
	  /** The promotion IDs associated with this Document. Currently, this field is restricted to at most one ID. */
		promotionIds: Option[List[String]] = None,
	  /** Output only. Whether the referenced Document can be found in the data store. */
		joined: Option[Boolean] = None,
	  /** Optional. The conversion value associated with this Document. Must be set if UserEvent.event_type is "conversion". For example, a value of 1000 signifies that 1000 seconds were spent viewing a Document for the `watch` conversion type. */
		conversionValue: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDiscoveryengineV1PanelInfo(
	  /** Required. The panel ID. */
		panelId: Option[String] = None,
	  /** The display name of the panel. */
		displayName: Option[String] = None,
	  /** The ordered position of the panel, if shown to the user with other panels. If set, then total_panels must also be set. */
		panelPosition: Option[Int] = None,
	  /** The total number of panels, including this one, shown to the user. Must be set if panel_position is set. */
		totalPanels: Option[Int] = None,
	  /** Optional. The document IDs associated with this panel. */
		documents: Option[List[Schema.GoogleCloudDiscoveryengineV1DocumentInfo]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SearchInfo(
	  /** The user's search query. See SearchRequest.query for definition. The value must be a UTF-8 encoded string with a length limit of 5,000 characters. Otherwise, an `INVALID_ARGUMENT` error is returned. At least one of search_query or PageInfo.page_category is required for `search` events. Other event types should not set this field. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		searchQuery: Option[String] = None,
	  /** The order in which products are returned, if applicable. See SearchRequest.order_by for definition and syntax. The value must be a UTF-8 encoded string with a length limit of 1,000 characters. Otherwise, an `INVALID_ARGUMENT` error is returned. This can only be set for `search` events. Other event types should not set this field. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		orderBy: Option[String] = None,
	  /** An integer that specifies the current offset for pagination (the 0-indexed starting location, amongst the products deemed by the API as relevant). See SearchRequest.offset for definition. If this field is negative, an `INVALID_ARGUMENT` is returned. This can only be set for `search` events. Other event types should not set this field. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		offset: Option[Int] = None
	)
	
	case class GoogleCloudDiscoveryengineV1CompletionInfo(
	  /** End user selected CompleteQueryResponse.QuerySuggestion.suggestion. */
		selectedSuggestion: Option[String] = None,
	  /** End user selected CompleteQueryResponse.QuerySuggestion.suggestion position, starting from 0. */
		selectedPosition: Option[Int] = None
	)
	
	case class GoogleCloudDiscoveryengineV1TransactionInfo(
	  /** Required. Total non-zero value associated with the transaction. This value may include shipping, tax, or other adjustments to the total value that you want to include. */
		value: Option[BigDecimal] = None,
	  /** Required. Currency code. Use three-character ISO-4217 code. */
		currency: Option[String] = None,
	  /** The transaction ID with a length limit of 128 characters. */
		transactionId: Option[String] = None,
	  /** All the taxes associated with the transaction. */
		tax: Option[BigDecimal] = None,
	  /** All the costs associated with the products. These can be manufacturing costs, shipping expenses not borne by the end user, or any other costs, such that: &#42; Profit = value - tax - cost */
		cost: Option[BigDecimal] = None,
	  /** The total discount(s) value applied to this transaction. This figure should be excluded from TransactionInfo.value For example, if a user paid TransactionInfo.value amount, then nominal (pre-discount) value of the transaction is the sum of TransactionInfo.value and TransactionInfo.discount_value This means that profit is calculated the same way, regardless of the discount value, and that TransactionInfo.discount_value can be larger than TransactionInfo.value: &#42; Profit = value - tax - cost */
		discountValue: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDiscoveryengineV1CustomAttribute(
	  /** The textual values of this custom attribute. For example, `["yellow", "green"]` when the key is "color". Empty string is not allowed. Otherwise, an `INVALID_ARGUMENT` error is returned. Exactly one of CustomAttribute.text or CustomAttribute.numbers should be set. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		text: Option[List[String]] = None,
	  /** The numerical values of this custom attribute. For example, `[2.3, 15.4]` when the key is "lengths_cm". Exactly one of CustomAttribute.text or CustomAttribute.numbers should be set. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		numbers: Option[List[BigDecimal]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1MediaInfo(
	  /** The media progress time in seconds, if applicable. For example, if the end user has finished 90 seconds of a playback video, then MediaInfo.media_progress_duration.seconds should be set to 90. */
		mediaProgressDuration: Option[String] = None,
	  /** Media progress should be computed using only the media_progress_duration relative to the media total length. This value must be between `[0, 1.0]` inclusive. If this is not a playback or the progress cannot be computed (e.g. ongoing livestream), this field should be unset. */
		mediaProgressPercentage: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDiscoveryengineV1RecommendResponse(
	  /** A list of recommended Documents. The order represents the ranking (from the most relevant Document to the least). */
		results: Option[List[Schema.GoogleCloudDiscoveryengineV1RecommendResponseRecommendationResult]] = None,
	  /** A unique attribution token. This should be included in the UserEvent logs resulting from this recommendation, which enables accurate attribution of recommendation model performance. */
		attributionToken: Option[String] = None,
	  /** IDs of documents in the request that were missing from the default Branch associated with the requested ServingConfig. */
		missingIds: Option[List[String]] = None,
	  /** True if RecommendRequest.validate_only was set. */
		validateOnly: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1RecommendResponseRecommendationResult(
	  /** Resource ID of the recommended Document. */
		id: Option[String] = None,
	  /** Set if `returnDocument` is set to true in RecommendRequest.params. */
		document: Option[Schema.GoogleCloudDiscoveryengineV1Document] = None,
	  /** Additional Document metadata or annotations. Possible values: &#42; `score`: Recommendation score in double value. Is set if `returnScore` is set to true in RecommendRequest.params. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ListSchemasResponse(
	  /** The Schemas. */
		schemas: Option[List[Schema.GoogleCloudDiscoveryengineV1Schema]] = None,
	  /** A token that can be sent as ListSchemasRequest.page_token to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1TrainCustomModelRequest(
	  /** Cloud Storage training input. */
		gcsTrainingInput: Option[Schema.GoogleCloudDiscoveryengineV1TrainCustomModelRequestGcsTrainingInput] = None,
	  /** Model to be trained. Supported values are: &#42; &#42;&#42;search-tuning&#42;&#42;: Fine tuning the search system based on data provided. */
		modelType: Option[String] = None,
	  /** The desired location of errors incurred during the data ingestion and training. */
		errorConfig: Option[Schema.GoogleCloudDiscoveryengineV1ImportErrorConfig] = None,
	  /** If not provided, a UUID will be generated. */
		modelId: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1TrainCustomModelRequestGcsTrainingInput(
	  /** The Cloud Storage corpus data which could be associated in train data. The data path format is `gs:///`. A newline delimited jsonl/ndjson file. For search-tuning model, each line should have the _id, title and text. Example: `{"_id": "doc1", title: "relevant doc", "text": "relevant text"}` */
		corpusDataPath: Option[String] = None,
	  /** The gcs query data which could be associated in train data. The data path format is `gs:///`. A newline delimited jsonl/ndjson file. For search-tuning model, each line should have the _id and text. Example: {"_id": "query1", "text": "example query"} */
		queryDataPath: Option[String] = None,
	  /** Cloud Storage training data path whose format should be `gs:///`. The file should be in tsv format. Each line should have the doc_id and query_id and score (number). For search-tuning model, it should have the query-id corpus-id score as tsv file header. The score should be a number in `[0, inf+)`. The larger the number is, the more relevant the pair is. Example: &#42; `query-id\tcorpus-id\tscore` &#42; `query1\tdoc1\t1` */
		trainDataPath: Option[String] = None,
	  /** Cloud Storage test data. Same format as train_data_path. If not provided, a random 80/20 train/test split will be performed on train_data_path. */
		testDataPath: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ListCustomModelsResponse(
	  /** List of custom tuning models. */
		models: Option[List[Schema.GoogleCloudDiscoveryengineV1CustomTuningModel]] = None
	)
	
	object GoogleCloudDiscoveryengineV1CustomTuningModel {
		enum ModelStateEnum extends Enum[ModelStateEnum] { case MODEL_STATE_UNSPECIFIED, TRAINING_PAUSED, TRAINING, TRAINING_COMPLETE, READY_FOR_SERVING, TRAINING_FAILED, NO_IMPROVEMENT, INPUT_VALIDATION_FAILED }
	}
	case class GoogleCloudDiscoveryengineV1CustomTuningModel(
	  /** Required. The fully qualified resource name of the model. Format: `projects/{project}/locations/{location}/collections/{collection}/dataStores/{data_store}/customTuningModels/{custom_tuning_model}`. Model must be an alpha-numerical string with limit of 40 characters. */
		name: Option[String] = None,
	  /** The display name of the model. */
		displayName: Option[String] = None,
	  /** The version of the model. */
		modelVersion: Option[String] = None,
	  /** The state that the model is in (e.g.`TRAINING` or `TRAINING_FAILED`). */
		modelState: Option[Schema.GoogleCloudDiscoveryengineV1CustomTuningModel.ModelStateEnum] = None,
	  /** Deprecated: Timestamp the Model was created at. */
		createTime: Option[String] = None,
	  /** Timestamp the model training was initiated. */
		trainingStartTime: Option[String] = None,
	  /** The metrics of the trained model. */
		metrics: Option[Map[String, BigDecimal]] = None,
	  /** Currently this is only populated if the model state is `INPUT_VALIDATION_FAILED`. */
		errorMessage: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1SiteSearchEngine(
	  /** The fully qualified resource name of the site search engine. Format: `projects/&#42;/locations/&#42;/dataStores/&#42;/siteSearchEngine` */
		name: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1TargetSite {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, INCLUDE, EXCLUDE }
		enum IndexingStatusEnum extends Enum[IndexingStatusEnum] { case INDEXING_STATUS_UNSPECIFIED, PENDING, FAILED, SUCCEEDED, DELETING }
	}
	case class GoogleCloudDiscoveryengineV1TargetSite(
	  /** Output only. The fully qualified resource name of the target site. `projects/{project}/locations/{location}/collections/{collection}/dataStores/{data_store}/siteSearchEngine/targetSites/{target_site}` The `target_site_id` is system-generated. */
		name: Option[String] = None,
	  /** Required. Input only. The user provided URI pattern from which the `generated_uri_pattern` is generated. */
		providedUriPattern: Option[String] = None,
	  /** The type of the target site, e.g., whether the site is to be included or excluded. */
		`type`: Option[Schema.GoogleCloudDiscoveryengineV1TargetSite.TypeEnum] = None,
	  /** Input only. If set to false, a uri_pattern is generated to include all pages whose address contains the provided_uri_pattern. If set to true, an uri_pattern is generated to try to be an exact match of the provided_uri_pattern or just the specific page if the provided_uri_pattern is a specific one. provided_uri_pattern is always normalized to generate the URI pattern to be used by the search engine. */
		exactMatch: Option[Boolean] = None,
	  /** Output only. This is system-generated based on the provided_uri_pattern. */
		generatedUriPattern: Option[String] = None,
	  /** Output only. Root domain of the provided_uri_pattern. */
		rootDomainUri: Option[String] = None,
	  /** Output only. Site ownership and validity verification status. */
		siteVerificationInfo: Option[Schema.GoogleCloudDiscoveryengineV1SiteVerificationInfo] = None,
	  /** Output only. Indexing status. */
		indexingStatus: Option[Schema.GoogleCloudDiscoveryengineV1TargetSite.IndexingStatusEnum] = None,
	  /** Output only. The target site's last updated time. */
		updateTime: Option[String] = None,
	  /** Output only. Failure reason. */
		failureReason: Option[Schema.GoogleCloudDiscoveryengineV1TargetSiteFailureReason] = None
	)
	
	object GoogleCloudDiscoveryengineV1SiteVerificationInfo {
		enum SiteVerificationStateEnum extends Enum[SiteVerificationStateEnum] { case SITE_VERIFICATION_STATE_UNSPECIFIED, VERIFIED, UNVERIFIED, EXEMPTED }
	}
	case class GoogleCloudDiscoveryengineV1SiteVerificationInfo(
	  /** Site verification state indicating the ownership and validity. */
		siteVerificationState: Option[Schema.GoogleCloudDiscoveryengineV1SiteVerificationInfo.SiteVerificationStateEnum] = None,
	  /** Latest site verification time. */
		verifyTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1TargetSiteFailureReason(
	  /** Failed due to insufficient quota. */
		quotaFailure: Option[Schema.GoogleCloudDiscoveryengineV1TargetSiteFailureReasonQuotaFailure] = None
	)
	
	case class GoogleCloudDiscoveryengineV1TargetSiteFailureReasonQuotaFailure(
	  /** This number is an estimation on how much total quota this project needs to successfully complete indexing. */
		totalRequiredQuota: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1BatchCreateTargetSitesRequest(
	  /** Required. The request message specifying the resources to create. A maximum of 20 TargetSites can be created in a batch. */
		requests: Option[List[Schema.GoogleCloudDiscoveryengineV1CreateTargetSiteRequest]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1CreateTargetSiteRequest(
	  /** Required. Parent resource name of TargetSite, such as `projects/{project}/locations/{location}/collections/{collection}/dataStores/{data_store}/siteSearchEngine`. */
		parent: Option[String] = None,
	  /** Required. The TargetSite to create. */
		targetSite: Option[Schema.GoogleCloudDiscoveryengineV1TargetSite] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ListTargetSitesResponse(
	  /** List of TargetSites. */
		targetSites: Option[List[Schema.GoogleCloudDiscoveryengineV1TargetSite]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The total number of items matching the request. This will always be populated in the response. */
		totalSize: Option[Int] = None
	)
	
	case class GoogleCloudDiscoveryengineV1EnableAdvancedSiteSearchRequest(
	
	)
	
	case class GoogleCloudDiscoveryengineV1DisableAdvancedSiteSearchRequest(
	
	)
	
	case class GoogleCloudDiscoveryengineV1RecrawlUrisRequest(
	  /** Required. List of URIs to crawl. At most 10K URIs are supported, otherwise an INVALID_ARGUMENT error is thrown. Each URI should match at least one TargetSite in `site_search_engine`. */
		uris: Option[List[String]] = None,
	  /** Optional. Full resource name of the SiteCredential, such as `projects/&#42;/locations/&#42;/collections/&#42;/dataStores/&#42;/siteSearchEngine/siteCredentials/&#42;`. Only set to crawl private URIs. */
		siteCredential: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1BatchVerifyTargetSitesRequest(
	
	)
	
	case class GoogleCloudDiscoveryengineV1FetchDomainVerificationStatusResponse(
	  /** List of TargetSites containing the site verification status. */
		targetSites: Option[List[Schema.GoogleCloudDiscoveryengineV1TargetSite]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The total number of items matching the request. This will always be populated in the response. */
		totalSize: Option[Int] = None
	)
	
	case class GoogleApiHttpBody(
	  /** The HTTP Content-Type header value specifying the content type of the body. */
		contentType: Option[String] = None,
	  /** The HTTP request/response body as raw binary. */
		data: Option[String] = None,
	  /** Application specific response metadata. Must be set in the first response for streaming APIs. */
		extensions: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1PurgeUserEventsRequest(
	  /** Required. The filter string to specify the events to be deleted with a length limit of 5,000 characters. The eligible fields for filtering are: &#42; `eventType`: Double quoted UserEvent.event_type string. &#42; `eventTime`: in ISO 8601 "zulu" format. &#42; `userPseudoId`: Double quoted string. Specifying this will delete all events associated with a visitor. &#42; `userId`: Double quoted string. Specifying this will delete all events associated with a user. Examples: &#42; Deleting all events in a time range: `eventTime > "2012-04-23T18:25:43.511Z" eventTime < "2012-04-23T18:30:43.511Z"` &#42; Deleting specific eventType: `eventType = "search"` &#42; Deleting all events for a specific visitor: `userPseudoId = "visitor1024"` &#42; Deleting all events inside a DataStore: `&#42;` The filtering fields are assumed to have an implicit AND. */
		filter: Option[String] = None,
	  /** The `force` field is currently not supported. Purge user event requests will permanently delete all purgeable events. Once the development is complete: If `force` is set to false, the method will return the expected purge count without deleting any user events. This field will default to false if not included in the request. */
		force: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ImportUserEventsRequest(
	  /** The Inline source for the input content for UserEvents. */
		inlineSource: Option[Schema.GoogleCloudDiscoveryengineV1ImportUserEventsRequestInlineSource] = None,
	  /** Cloud Storage location for the input content. */
		gcsSource: Option[Schema.GoogleCloudDiscoveryengineV1GcsSource] = None,
	  /** BigQuery input source. */
		bigquerySource: Option[Schema.GoogleCloudDiscoveryengineV1BigQuerySource] = None,
	  /** The desired location of errors incurred during the Import. Cannot be set for inline user event imports. */
		errorConfig: Option[Schema.GoogleCloudDiscoveryengineV1ImportErrorConfig] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ImportUserEventsRequestInlineSource(
	  /** Required. A list of user events to import. Recommended max of 10k items. */
		userEvents: Option[List[Schema.GoogleCloudDiscoveryengineV1UserEvent]] = None
	)
	
	case class GoogleCloudDiscoveryengineLoggingErrorLog(
	  /** The service context in which this error has occurred. */
		serviceContext: Option[Schema.GoogleCloudDiscoveryengineLoggingServiceContext] = None,
	  /** A description of the context in which the error occurred. */
		context: Option[Schema.GoogleCloudDiscoveryengineLoggingErrorContext] = None,
	  /** A message describing the error. */
		message: Option[String] = None,
	  /** The RPC status associated with the error log. */
		status: Option[Schema.GoogleRpcStatus] = None,
	  /** The API request payload, represented as a protocol buffer. Most API request types are supported—for example: &#42; `type.googleapis.com/google.cloud.discoveryengine.v1alpha.DocumentService.CreateDocumentRequest` &#42; `type.googleapis.com/google.cloud.discoveryengine.v1alpha.UserEventService.WriteUserEventRequest` */
		requestPayload: Option[Map[String, JsValue]] = None,
	  /** The API response payload, represented as a protocol buffer. This is used to log some "soft errors", where the response is valid but we consider there are some quality issues like unjoined events. The following API responses are supported, and no PII is included: &#42; `google.cloud.discoveryengine.v1alpha.RecommendationService.Recommend` &#42; `google.cloud.discoveryengine.v1alpha.UserEventService.WriteUserEvent` &#42; `google.cloud.discoveryengine.v1alpha.UserEventService.CollectUserEvent` */
		responsePayload: Option[Map[String, JsValue]] = None,
	  /** The error payload that is populated on LRO import APIs. */
		importPayload: Option[Schema.GoogleCloudDiscoveryengineLoggingImportErrorContext] = None
	)
	
	case class GoogleCloudDiscoveryengineLoggingServiceContext(
	  /** An identifier of the service—for example, `discoveryengine.googleapis.com`. */
		service: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineLoggingErrorContext(
	  /** The HTTP request which was processed when the error was triggered. */
		httpRequest: Option[Schema.GoogleCloudDiscoveryengineLoggingHttpRequestContext] = None,
	  /** The location in the source code where the decision was made to report the error, usually the place where it was logged. */
		reportLocation: Option[Schema.GoogleCloudDiscoveryengineLoggingSourceLocation] = None
	)
	
	case class GoogleCloudDiscoveryengineLoggingHttpRequestContext(
	  /** The HTTP response status code for the request. */
		responseStatusCode: Option[Int] = None
	)
	
	case class GoogleCloudDiscoveryengineLoggingSourceLocation(
	  /** Human-readable name of a function or method—for example, `google.cloud.discoveryengine.v1alpha.RecommendationService.Recommend`. */
		functionName: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineLoggingImportErrorContext(
	  /** The operation resource name of the LRO. */
		operation: Option[String] = None,
	  /** Google Cloud Storage file path of the import source. Can be set for batch operation error. */
		gcsPath: Option[String] = None,
	  /** Line number of the content in file. Should be empty for permission or batch operation error. */
		lineNumber: Option[String] = None,
	  /** The detailed content which caused the error on importing a document. */
		document: Option[String] = None,
	  /** The detailed content which caused the error on importing a user event. */
		userEvent: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1BatchCreateTargetSiteMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1BatchCreateTargetSitesResponse(
	  /** TargetSites created. */
		targetSites: Option[List[Schema.GoogleCloudDiscoveryengineV1TargetSite]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1CreateDataStoreMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1CreateEngineMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1CreateSchemaMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1CreateTargetSiteMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1DeleteDataStoreMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1DeleteEngineMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1DeleteSchemaMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1DeleteTargetSiteMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1DisableAdvancedSiteSearchMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1DisableAdvancedSiteSearchResponse(
	
	)
	
	case class GoogleCloudDiscoveryengineV1EnableAdvancedSiteSearchMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1EnableAdvancedSiteSearchResponse(
	
	)
	
	case class GoogleCloudDiscoveryengineV1ImportCompletionSuggestionsMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of CompletionSuggestions successfully imported. */
		successCount: Option[String] = None,
	  /** Count of CompletionSuggestions that failed to be imported. */
		failureCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ImportCompletionSuggestionsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** The desired location of errors incurred during the Import. */
		errorConfig: Option[Schema.GoogleCloudDiscoveryengineV1ImportErrorConfig] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ImportDocumentsMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of entries that were processed successfully. */
		successCount: Option[String] = None,
	  /** Count of entries that encountered errors while processing. */
		failureCount: Option[String] = None,
	  /** Total count of entries that were processed. */
		totalCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ImportDocumentsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Echoes the destination for the complete errors in the request if set. */
		errorConfig: Option[Schema.GoogleCloudDiscoveryengineV1ImportErrorConfig] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ImportSuggestionDenyListEntriesMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ImportSuggestionDenyListEntriesResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Count of deny list entries successfully imported. */
		importedEntriesCount: Option[String] = None,
	  /** Count of deny list entries that failed to be imported. */
		failedEntriesCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ImportUserEventsMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of entries that were processed successfully. */
		successCount: Option[String] = None,
	  /** Count of entries that encountered errors while processing. */
		failureCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ImportUserEventsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Echoes the destination for the complete errors if this field was set in the request. */
		errorConfig: Option[Schema.GoogleCloudDiscoveryengineV1ImportErrorConfig] = None,
	  /** Count of user events imported with complete existing Documents. */
		joinedEventsCount: Option[String] = None,
	  /** Count of user events imported, but with Document information not found in the existing Branch. */
		unjoinedEventsCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1Project(
	  /** Output only. Full resource name of the project, for example `projects/{project}`. Note that when making requests, project number and project id are both acceptable, but the server will always respond in project number. */
		name: Option[String] = None,
	  /** Output only. The timestamp when this project is created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when this project is successfully provisioned. Empty value means this project is still provisioning and is not ready for use. */
		provisionCompletionTime: Option[String] = None,
	  /** Output only. A map of terms of services. The key is the `id` of ServiceTerms. */
		serviceTermsMap: Option[Map[String, Schema.GoogleCloudDiscoveryengineV1ProjectServiceTerms]] = None
	)
	
	object GoogleCloudDiscoveryengineV1ProjectServiceTerms {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, TERMS_ACCEPTED, TERMS_PENDING, TERMS_DECLINED }
	}
	case class GoogleCloudDiscoveryengineV1ProjectServiceTerms(
	  /** The unique identifier of this terms of service. Available terms: &#42; `GA_DATA_USE_TERMS`: [Terms for data use](https://cloud.google.com/retail/data-use-terms). When using this as `id`, the acceptable version to provide is `2022-11-23`. */
		id: Option[String] = None,
	  /** The version string of the terms of service. For acceptable values, see the comments for id above. */
		version: Option[String] = None,
	  /** Whether the project has accepted/rejected the service terms or it is still pending. */
		state: Option[Schema.GoogleCloudDiscoveryengineV1ProjectServiceTerms.StateEnum] = None,
	  /** The last time when the project agreed to the terms of service. */
		acceptTime: Option[String] = None,
	  /** The last time when the project declined or revoked the agreement to terms of service. */
		declineTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1ProvisionProjectMetadata(
	
	)
	
	case class GoogleCloudDiscoveryengineV1PurgeCompletionSuggestionsMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1PurgeCompletionSuggestionsResponse(
	  /** Whether the completion suggestions were successfully purged. */
		purgeSucceeded: Option[Boolean] = None,
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1PurgeDocumentsMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of entries that were deleted successfully. */
		successCount: Option[String] = None,
	  /** Count of entries that encountered errors while processing. */
		failureCount: Option[String] = None,
	  /** Count of entries that were ignored as entries were not found. */
		ignoredCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1PurgeDocumentsResponse(
	  /** The total count of documents purged as a result of the operation. */
		purgeCount: Option[String] = None,
	  /** A sample of document names that will be deleted. Only populated if `force` is set to false. A max of 100 names will be returned and the names are chosen at random. */
		purgeSample: Option[List[String]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1PurgeSuggestionDenyListEntriesMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1PurgeSuggestionDenyListEntriesResponse(
	  /** Number of suggestion deny list entries purged. */
		purgeCount: Option[String] = None,
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1TrainCustomModelMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1TrainCustomModelResponse(
	  /** A sample of errors encountered while processing the data. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Echoes the destination for the complete errors in the request if set. */
		errorConfig: Option[Schema.GoogleCloudDiscoveryengineV1ImportErrorConfig] = None,
	  /** The trained model status. Possible values are: &#42; &#42;&#42;bad-data&#42;&#42;: The training data quality is bad. &#42; &#42;&#42;no-improvement&#42;&#42;: Tuning didn't improve performance. Won't deploy. &#42; &#42;&#42;in-progress&#42;&#42;: Model training job creation is in progress. &#42; &#42;&#42;training&#42;&#42;: Model is actively training. &#42; &#42;&#42;evaluating&#42;&#42;: The model is evaluating trained metrics. &#42; &#42;&#42;indexing&#42;&#42;: The model trained metrics are indexing. &#42; &#42;&#42;ready&#42;&#42;: The model is ready for serving. */
		modelStatus: Option[String] = None,
	  /** The metrics of the trained model. */
		metrics: Option[Map[String, BigDecimal]] = None,
	  /** Fully qualified name of the CustomTuningModel. */
		modelName: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1UpdateCmekConfigMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1UpdateSchemaMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1UpdateTargetSiteMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaAclConfig(
	  /** Immutable. The full resource name of the acl configuration. Format: `projects/{project}/locations/{location}/aclConfig`. This field must be a UTF-8 encoded string with a length limit of 1024 characters. */
		name: Option[String] = None,
	  /** Identity provider config. */
		idpConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaIdpConfig] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaIdpConfig {
		enum IdpTypeEnum extends Enum[IdpTypeEnum] { case IDP_TYPE_UNSPECIFIED, GSUITE, THIRD_PARTY }
	}
	case class GoogleCloudDiscoveryengineV1alphaIdpConfig(
	  /** Identity provider type configured. */
		idpType: Option[Schema.GoogleCloudDiscoveryengineV1alphaIdpConfig.IdpTypeEnum] = None,
	  /** External Identity provider config. */
		externalIdpConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaIdpConfigExternalIdpConfig] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaIdpConfigExternalIdpConfig(
	  /** Workforce pool name. Example: "locations/global/workforcePools/pool_id" */
		workforcePoolName: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaAnswer {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, IN_PROGRESS, FAILED, SUCCEEDED }
		enum AnswerSkippedReasonsEnum extends Enum[AnswerSkippedReasonsEnum] { case ANSWER_SKIPPED_REASON_UNSPECIFIED, ADVERSARIAL_QUERY_IGNORED, NON_ANSWER_SEEKING_QUERY_IGNORED, OUT_OF_DOMAIN_QUERY_IGNORED, POTENTIAL_POLICY_VIOLATION, NO_RELEVANT_CONTENT, JAIL_BREAKING_QUERY_IGNORED, CUSTOMER_POLICY_VIOLATION, NON_ANSWER_SEEKING_QUERY_IGNORED_V2, LOW_GROUNDED_ANSWER }
	}
	case class GoogleCloudDiscoveryengineV1alphaAnswer(
	  /** Immutable. Fully qualified name `projects/{project}/locations/global/collections/{collection}/engines/{engine}/sessions/&#42;/answers/&#42;` */
		name: Option[String] = None,
	  /** The state of the answer generation. */
		state: Option[Schema.GoogleCloudDiscoveryengineV1alphaAnswer.StateEnum] = None,
	  /** The textual answer. */
		answerText: Option[String] = None,
	  /** Citations. */
		citations: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaAnswerCitation]] = None,
	  /** References. */
		references: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaAnswerReference]] = None,
	  /** Suggested related questions. */
		relatedQuestions: Option[List[String]] = None,
	  /** Answer generation steps. */
		steps: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaAnswerStep]] = None,
	  /** Query understanding information. */
		queryUnderstandingInfo: Option[Schema.GoogleCloudDiscoveryengineV1alphaAnswerQueryUnderstandingInfo] = None,
	  /** Additional answer-skipped reasons. This provides the reason for ignored cases. If nothing is skipped, this field is not set. */
		answerSkippedReasons: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaAnswer.AnswerSkippedReasonsEnum]] = None,
	  /** Output only. Answer creation timestamp. */
		createTime: Option[String] = None,
	  /** Output only. Answer completed timestamp. */
		completeTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaAnswerCitation(
	  /** Index indicates the start of the segment, measured in bytes (UTF-8 unicode). */
		startIndex: Option[String] = None,
	  /** End of the attributed segment, exclusive. */
		endIndex: Option[String] = None,
	  /** Citation sources for the attributed segment. */
		sources: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaAnswerCitationSource]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaAnswerCitationSource(
	  /** ID of the citation source. */
		referenceId: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaAnswerReference(
	  /** Unstructured document information. */
		unstructuredDocumentInfo: Option[Schema.GoogleCloudDiscoveryengineV1alphaAnswerReferenceUnstructuredDocumentInfo] = None,
	  /** Chunk information. */
		chunkInfo: Option[Schema.GoogleCloudDiscoveryengineV1alphaAnswerReferenceChunkInfo] = None,
	  /** Structured document information. */
		structuredDocumentInfo: Option[Schema.GoogleCloudDiscoveryengineV1alphaAnswerReferenceStructuredDocumentInfo] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaAnswerReferenceUnstructuredDocumentInfo(
	  /** Document resource name. */
		document: Option[String] = None,
	  /** URI for the document. */
		uri: Option[String] = None,
	  /** Title. */
		title: Option[String] = None,
	  /** List of cited chunk contents derived from document content. */
		chunkContents: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaAnswerReferenceUnstructuredDocumentInfoChunkContent]] = None,
	  /** The structured JSON metadata for the document. It is populated from the struct data from the Chunk in search result. */
		structData: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaAnswerReferenceUnstructuredDocumentInfoChunkContent(
	  /** Chunk textual content. */
		content: Option[String] = None,
	  /** Page identifier. */
		pageIdentifier: Option[String] = None,
	  /** The relevance of the chunk for a given query. Values range from 0.0 (completely irrelevant) to 1.0 (completely relevant). This value is for informational purpose only. It may change for the same query and chunk at any time due to a model retraining or change in implementation. */
		relevanceScore: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaAnswerReferenceChunkInfo(
	  /** Chunk resource name. */
		chunk: Option[String] = None,
	  /** Chunk textual content. */
		content: Option[String] = None,
	  /** The relevance of the chunk for a given query. Values range from 0.0 (completely irrelevant) to 1.0 (completely relevant). This value is for informational purpose only. It may change for the same query and chunk at any time due to a model retraining or change in implementation. */
		relevanceScore: Option[BigDecimal] = None,
	  /** Document metadata. */
		documentMetadata: Option[Schema.GoogleCloudDiscoveryengineV1alphaAnswerReferenceChunkInfoDocumentMetadata] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaAnswerReferenceChunkInfoDocumentMetadata(
	  /** Document resource name. */
		document: Option[String] = None,
	  /** URI for the document. */
		uri: Option[String] = None,
	  /** Title. */
		title: Option[String] = None,
	  /** Page identifier. */
		pageIdentifier: Option[String] = None,
	  /** The structured JSON metadata for the document. It is populated from the struct data from the Chunk in search result. */
		structData: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaAnswerReferenceStructuredDocumentInfo(
	  /** Document resource name. */
		document: Option[String] = None,
	  /** Structured search data. */
		structData: Option[Map[String, JsValue]] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaAnswerStep {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, IN_PROGRESS, FAILED, SUCCEEDED }
	}
	case class GoogleCloudDiscoveryengineV1alphaAnswerStep(
	  /** The state of the step. */
		state: Option[Schema.GoogleCloudDiscoveryengineV1alphaAnswerStep.StateEnum] = None,
	  /** The description of the step. */
		description: Option[String] = None,
	  /** The thought of the step. */
		thought: Option[String] = None,
	  /** Actions. */
		actions: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaAnswerStepAction]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaAnswerStepAction(
	  /** Search action. */
		searchAction: Option[Schema.GoogleCloudDiscoveryengineV1alphaAnswerStepActionSearchAction] = None,
	  /** Observation. */
		observation: Option[Schema.GoogleCloudDiscoveryengineV1alphaAnswerStepActionObservation] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaAnswerStepActionSearchAction(
	  /** The query to search. */
		query: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaAnswerStepActionObservation(
	  /** Search results observed by the search action, it can be snippets info or chunk info, depending on the citation type set by the user. */
		searchResults: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaAnswerStepActionObservationSearchResult]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaAnswerStepActionObservationSearchResult(
	  /** Document resource name. */
		document: Option[String] = None,
	  /** URI for the document. */
		uri: Option[String] = None,
	  /** Title. */
		title: Option[String] = None,
	  /** If citation_type is DOCUMENT_LEVEL_CITATION, populate document level snippets. */
		snippetInfo: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaAnswerStepActionObservationSearchResultSnippetInfo]] = None,
	  /** If citation_type is CHUNK_LEVEL_CITATION and chunk mode is on, populate chunk info. */
		chunkInfo: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaAnswerStepActionObservationSearchResultChunkInfo]] = None,
	  /** Data representation. The structured JSON data for the document. It's populated from the struct data from the Document, or the Chunk in search result. */
		structData: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaAnswerStepActionObservationSearchResultSnippetInfo(
	  /** Snippet content. */
		snippet: Option[String] = None,
	  /** Status of the snippet defined by the search team. */
		snippetStatus: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaAnswerStepActionObservationSearchResultChunkInfo(
	  /** Chunk resource name. */
		chunk: Option[String] = None,
	  /** Chunk textual content. */
		content: Option[String] = None,
	  /** The relevance of the chunk for a given query. Values range from 0.0 (completely irrelevant) to 1.0 (completely relevant). This value is for informational purpose only. It may change for the same query and chunk at any time due to a model retraining or change in implementation. */
		relevanceScore: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaAnswerQueryUnderstandingInfo(
	  /** Query classification information. */
		queryClassificationInfo: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaAnswerQueryUnderstandingInfoQueryClassificationInfo]] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaAnswerQueryUnderstandingInfoQueryClassificationInfo {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, ADVERSARIAL_QUERY, NON_ANSWER_SEEKING_QUERY, JAIL_BREAKING_QUERY, NON_ANSWER_SEEKING_QUERY_V2 }
	}
	case class GoogleCloudDiscoveryengineV1alphaAnswerQueryUnderstandingInfoQueryClassificationInfo(
	  /** Query classification type. */
		`type`: Option[Schema.GoogleCloudDiscoveryengineV1alphaAnswerQueryUnderstandingInfoQueryClassificationInfo.TypeEnum] = None,
	  /** Classification output. */
		positive: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaBatchCreateTargetSiteMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaBatchCreateTargetSitesResponse(
	  /** TargetSites created. */
		targetSites: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaTargetSite]] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaTargetSite {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, INCLUDE, EXCLUDE }
		enum IndexingStatusEnum extends Enum[IndexingStatusEnum] { case INDEXING_STATUS_UNSPECIFIED, PENDING, FAILED, SUCCEEDED, DELETING }
	}
	case class GoogleCloudDiscoveryengineV1alphaTargetSite(
	  /** Output only. The fully qualified resource name of the target site. `projects/{project}/locations/{location}/collections/{collection}/dataStores/{data_store}/siteSearchEngine/targetSites/{target_site}` The `target_site_id` is system-generated. */
		name: Option[String] = None,
	  /** Required. Input only. The user provided URI pattern from which the `generated_uri_pattern` is generated. */
		providedUriPattern: Option[String] = None,
	  /** The type of the target site, e.g., whether the site is to be included or excluded. */
		`type`: Option[Schema.GoogleCloudDiscoveryengineV1alphaTargetSite.TypeEnum] = None,
	  /** Input only. If set to false, a uri_pattern is generated to include all pages whose address contains the provided_uri_pattern. If set to true, an uri_pattern is generated to try to be an exact match of the provided_uri_pattern or just the specific page if the provided_uri_pattern is a specific one. provided_uri_pattern is always normalized to generate the URI pattern to be used by the search engine. */
		exactMatch: Option[Boolean] = None,
	  /** Output only. This is system-generated based on the provided_uri_pattern. */
		generatedUriPattern: Option[String] = None,
	  /** Output only. Root domain of the provided_uri_pattern. */
		rootDomainUri: Option[String] = None,
	  /** Output only. Site ownership and validity verification status. */
		siteVerificationInfo: Option[Schema.GoogleCloudDiscoveryengineV1alphaSiteVerificationInfo] = None,
	  /** Output only. Indexing status. */
		indexingStatus: Option[Schema.GoogleCloudDiscoveryengineV1alphaTargetSite.IndexingStatusEnum] = None,
	  /** Output only. The target site's last updated time. */
		updateTime: Option[String] = None,
	  /** Output only. Failure reason. */
		failureReason: Option[Schema.GoogleCloudDiscoveryengineV1alphaTargetSiteFailureReason] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaSiteVerificationInfo {
		enum SiteVerificationStateEnum extends Enum[SiteVerificationStateEnum] { case SITE_VERIFICATION_STATE_UNSPECIFIED, VERIFIED, UNVERIFIED, EXEMPTED }
	}
	case class GoogleCloudDiscoveryengineV1alphaSiteVerificationInfo(
	  /** Site verification state indicating the ownership and validity. */
		siteVerificationState: Option[Schema.GoogleCloudDiscoveryengineV1alphaSiteVerificationInfo.SiteVerificationStateEnum] = None,
	  /** Latest site verification time. */
		verifyTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaTargetSiteFailureReason(
	  /** Failed due to insufficient quota. */
		quotaFailure: Option[Schema.GoogleCloudDiscoveryengineV1alphaTargetSiteFailureReasonQuotaFailure] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaTargetSiteFailureReasonQuotaFailure(
	  /** This number is an estimation on how much total quota this project needs to successfully complete indexing. */
		totalRequiredQuota: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaCmekConfig {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, KEY_ISSUE, DELETING, UNUSABLE, ACTIVE_ROTATING }
	}
	case class GoogleCloudDiscoveryengineV1alphaCmekConfig(
	  /** Required. Name of the CmekConfig, of the form `projects/{project}/locations/{location}/cmekConfig` or `projects/{project}/locations/{location}/cmekConfigs/{cmekConfig}`. */
		name: Option[String] = None,
	  /** Kms key resource name which will be used to encrypt resources `projects/{project}/locations/{location}/keyRings/{keyRing}/cryptoKeys/{keyId}`. */
		kmsKey: Option[String] = None,
	  /** Kms key version resource name which will be used to encrypt resources `/cryptoKeyVersions/{keyVersion}`. */
		kmsKeyVersion: Option[String] = None,
	  /** Output only. State of the CmekConfig. */
		state: Option[Schema.GoogleCloudDiscoveryengineV1alphaCmekConfig.StateEnum] = None,
	  /** Output only. The default CmekConfig for the Customer. */
		isDefault: Option[Boolean] = None,
	  /** Output only. The timestamp of the last key rotation. */
		lastRotationTimestampMicros: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaControl {
		enum SolutionTypeEnum extends Enum[SolutionTypeEnum] { case SOLUTION_TYPE_UNSPECIFIED, SOLUTION_TYPE_RECOMMENDATION, SOLUTION_TYPE_SEARCH, SOLUTION_TYPE_CHAT, SOLUTION_TYPE_GENERATIVE_CHAT }
		enum UseCasesEnum extends Enum[UseCasesEnum] { case SEARCH_USE_CASE_UNSPECIFIED, SEARCH_USE_CASE_SEARCH, SEARCH_USE_CASE_BROWSE }
	}
	case class GoogleCloudDiscoveryengineV1alphaControl(
	  /** Defines a boost-type control */
		boostAction: Option[Schema.GoogleCloudDiscoveryengineV1alphaControlBoostAction] = None,
	  /** Defines a filter-type control Currently not supported by Recommendation */
		filterAction: Option[Schema.GoogleCloudDiscoveryengineV1alphaControlFilterAction] = None,
	  /** Defines a redirect-type control. */
		redirectAction: Option[Schema.GoogleCloudDiscoveryengineV1alphaControlRedirectAction] = None,
	  /** Treats a group of terms as synonyms of one another. */
		synonymsAction: Option[Schema.GoogleCloudDiscoveryengineV1alphaControlSynonymsAction] = None,
	  /** Immutable. Fully qualified name `projects/&#42;/locations/global/dataStore/&#42;/controls/&#42;` */
		name: Option[String] = None,
	  /** Required. Human readable name. The identifier used in UI views. Must be UTF-8 encoded string. Length limit is 128 characters. Otherwise an INVALID ARGUMENT error is thrown. */
		displayName: Option[String] = None,
	  /** Output only. List of all ServingConfig IDs this control is attached to. May take up to 10 minutes to update after changes. */
		associatedServingConfigIds: Option[List[String]] = None,
	  /** Required. Immutable. What solution the control belongs to. Must be compatible with vertical of resource. Otherwise an INVALID ARGUMENT error is thrown. */
		solutionType: Option[Schema.GoogleCloudDiscoveryengineV1alphaControl.SolutionTypeEnum] = None,
	  /** Specifies the use case for the control. Affects what condition fields can be set. Only applies to SOLUTION_TYPE_SEARCH. Currently only allow one use case per control. Must be set when solution_type is SolutionType.SOLUTION_TYPE_SEARCH. */
		useCases: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaControl.UseCasesEnum]] = None,
	  /** Determines when the associated action will trigger. Omit to always apply the action. Currently only a single condition may be specified. Otherwise an INVALID ARGUMENT error is thrown. */
		conditions: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaCondition]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaControlBoostAction(
	  /** Required. Strength of the boost, which should be in [-1, 1]. Negative boost means demotion. Default is 0.0 (No-op). */
		boost: Option[BigDecimal] = None,
	  /** Required. Specifies which products to apply the boost to. If no filter is provided all products will be boosted (No-op). Syntax documentation: https://cloud.google.com/retail/docs/filter-and-order Maximum length is 5000 characters. Otherwise an INVALID ARGUMENT error is thrown. */
		filter: Option[String] = None,
	  /** Required. Specifies which data store's documents can be boosted by this control. Full data store name e.g. projects/123/locations/global/collections/default_collection/dataStores/default_data_store */
		dataStore: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaControlFilterAction(
	  /** Required. A filter to apply on the matching condition results. Required Syntax documentation: https://cloud.google.com/retail/docs/filter-and-order Maximum length is 5000 characters. Otherwise an INVALID ARGUMENT error is thrown. */
		filter: Option[String] = None,
	  /** Required. Specifies which data store's documents can be filtered by this control. Full data store name e.g. projects/123/locations/global/collections/default_collection/dataStores/default_data_store */
		dataStore: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaControlRedirectAction(
	  /** Required. The URI to which the shopper will be redirected. Required. URI must have length equal or less than 2000 characters. Otherwise an INVALID ARGUMENT error is thrown. */
		redirectUri: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaControlSynonymsAction(
	  /** Defines a set of synonyms. Can specify up to 100 synonyms. Must specify at least 2 synonyms. Otherwise an INVALID ARGUMENT error is thrown. */
		synonyms: Option[List[String]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaCondition(
	  /** Search only A list of terms to match the query on. Cannot be set when Condition.query_regex is set. Maximum of 10 query terms. */
		queryTerms: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaConditionQueryTerm]] = None,
	  /** Range of time(s) specifying when condition is active. Maximum of 10 time ranges. */
		activeTimeRange: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaConditionTimeRange]] = None,
	  /** Optional. Query regex to match the whole search query. Cannot be set when Condition.query_terms is set. This is currently supporting promotion use case. */
		queryRegex: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaConditionQueryTerm(
	  /** The specific query value to match against Must be lowercase, must be UTF-8. Can have at most 3 space separated terms if full_match is true. Cannot be an empty string. Maximum length of 5000 characters. */
		value: Option[String] = None,
	  /** Whether the search query needs to exactly match the query term. */
		fullMatch: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaConditionTimeRange(
	  /** Start of time range. Range is inclusive. */
		startTime: Option[String] = None,
	  /** End of time range. Range is inclusive. Must be in the future. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaCreateDataStoreMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaCreateEngineMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaCreateEvaluationMetadata(
	
	)
	
	case class GoogleCloudDiscoveryengineV1alphaCreateSchemaMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaCreateSitemapMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaCreateTargetSiteMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaDataStore {
		enum IndustryVerticalEnum extends Enum[IndustryVerticalEnum] { case INDUSTRY_VERTICAL_UNSPECIFIED, GENERIC, MEDIA, HEALTHCARE_FHIR }
		enum SolutionTypesEnum extends Enum[SolutionTypesEnum] { case SOLUTION_TYPE_UNSPECIFIED, SOLUTION_TYPE_RECOMMENDATION, SOLUTION_TYPE_SEARCH, SOLUTION_TYPE_CHAT, SOLUTION_TYPE_GENERATIVE_CHAT }
		enum ContentConfigEnum extends Enum[ContentConfigEnum] { case CONTENT_CONFIG_UNSPECIFIED, NO_CONTENT, CONTENT_REQUIRED, PUBLIC_WEBSITE, GOOGLE_WORKSPACE }
	}
	case class GoogleCloudDiscoveryengineV1alphaDataStore(
	  /** Immutable. The full resource name of the data store. Format: `projects/{project}/locations/{location}/collections/{collection_id}/dataStores/{data_store_id}`. This field must be a UTF-8 encoded string with a length limit of 1024 characters. */
		name: Option[String] = None,
	  /** Required. The data store display name. This field must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an INVALID_ARGUMENT error is returned. */
		displayName: Option[String] = None,
	  /** Immutable. The industry vertical that the data store registers. */
		industryVertical: Option[Schema.GoogleCloudDiscoveryengineV1alphaDataStore.IndustryVerticalEnum] = None,
	  /** The solutions that the data store enrolls. Available solutions for each industry_vertical: &#42; `MEDIA`: `SOLUTION_TYPE_RECOMMENDATION` and `SOLUTION_TYPE_SEARCH`. &#42; `SITE_SEARCH`: `SOLUTION_TYPE_SEARCH` is automatically enrolled. Other solutions cannot be enrolled. */
		solutionTypes: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaDataStore.SolutionTypesEnum]] = None,
	  /** Output only. The id of the default Schema asscociated to this data store. */
		defaultSchemaId: Option[String] = None,
	  /** Immutable. The content config of the data store. If this field is unset, the server behavior defaults to ContentConfig.NO_CONTENT. */
		contentConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaDataStore.ContentConfigEnum] = None,
	  /** Output only. Timestamp the DataStore was created at. */
		createTime: Option[String] = None,
	  /** Optional. Configuration for advanced site search. */
		advancedSiteSearchConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaAdvancedSiteSearchConfig] = None,
	  /** Language info for DataStore. */
		languageInfo: Option[Schema.GoogleCloudDiscoveryengineV1alphaLanguageInfo] = None,
	  /** Optional. Configuration for Natural Language Query Understanding. */
		naturalLanguageQueryUnderstandingConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaNaturalLanguageQueryUnderstandingConfig] = None,
	  /** Input only. The KMS key to be used to protect this DataStore at creation time. Must be set for requests that need to comply with CMEK Org Policy protections. If this field is set and processed successfully, the DataStore will be protected by the KMS key, as indicated in the cmek_config field. */
		kmsKeyName: Option[String] = None,
	  /** Output only. CMEK-related information for the DataStore. */
		cmekConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaCmekConfig] = None,
	  /** Output only. Data store level identity provider config. */
		idpConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaIdpConfig] = None,
	  /** Output only. Data size estimation for billing. */
		billingEstimation: Option[Schema.GoogleCloudDiscoveryengineV1alphaDataStoreBillingEstimation] = None,
	  /** Immutable. Whether data in the DataStore has ACL information. If set to `true`, the source data must have ACL. ACL will be ingested when data is ingested by DocumentService.ImportDocuments methods. When ACL is enabled for the DataStore, Document can't be accessed by calling DocumentService.GetDocument or DocumentService.ListDocuments. Currently ACL is only supported in `GENERIC` industry vertical with non-`PUBLIC_WEBSITE` content config. */
		aclEnabled: Option[Boolean] = None,
	  /** Config to store data store type configuration for workspace data. This must be set when DataStore.content_config is set as DataStore.ContentConfig.GOOGLE_WORKSPACE. */
		workspaceConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaWorkspaceConfig] = None,
	  /** Configuration for Document understanding and enrichment. */
		documentProcessingConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaDocumentProcessingConfig] = None,
	  /** The start schema to use for this DataStore when provisioning it. If unset, a default vertical specialized schema will be used. This field is only used by CreateDataStore API, and will be ignored if used in other APIs. This field will be omitted from all API responses including CreateDataStore API. To retrieve a schema of a DataStore, use SchemaService.GetSchema API instead. The provided schema will be validated against certain rules on schema. Learn more from [this doc](https://cloud.google.com/generative-ai-app-builder/docs/provide-schema). */
		startingSchema: Option[Schema.GoogleCloudDiscoveryengineV1alphaSchema] = None,
	  /** Optional. Stores serving config at DataStore level. */
		servingConfigDataStore: Option[Schema.GoogleCloudDiscoveryengineV1alphaDataStoreServingConfigDataStore] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaAdvancedSiteSearchConfig(
	  /** If set true, initial indexing is disabled for the DataStore. */
		disableInitialIndex: Option[Boolean] = None,
	  /** If set true, automatic refresh is disabled for the DataStore. */
		disableAutomaticRefresh: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaLanguageInfo(
	  /** The language code for the DataStore. */
		languageCode: Option[String] = None,
	  /** Output only. This is the normalized form of language_code. E.g.: language_code of `en-GB`, `en_GB`, `en-UK` or `en-gb` will have normalized_language_code of `en-GB`. */
		normalizedLanguageCode: Option[String] = None,
	  /** Output only. Language part of normalized_language_code. E.g.: `en-US` -> `en`, `zh-Hans-HK` -> `zh`, `en` -> `en`. */
		language: Option[String] = None,
	  /** Output only. Region part of normalized_language_code, if present. E.g.: `en-US` -> `US`, `zh-Hans-HK` -> `HK`, `en` -> ``. */
		region: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaNaturalLanguageQueryUnderstandingConfig {
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, DISABLED, ENABLED }
	}
	case class GoogleCloudDiscoveryengineV1alphaNaturalLanguageQueryUnderstandingConfig(
	  /** Mode of Natural Language Query Understanding. If this field is unset, the behavior defaults to NaturalLanguageQueryUnderstandingConfig.Mode.DISABLED. */
		mode: Option[Schema.GoogleCloudDiscoveryengineV1alphaNaturalLanguageQueryUnderstandingConfig.ModeEnum] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaDataStoreBillingEstimation(
	  /** Data size for structured data in terms of bytes. */
		structuredDataSize: Option[String] = None,
	  /** Data size for unstructured data in terms of bytes. */
		unstructuredDataSize: Option[String] = None,
	  /** Data size for websites in terms of bytes. */
		websiteDataSize: Option[String] = None,
	  /** Last updated timestamp for structured data. */
		structuredDataUpdateTime: Option[String] = None,
	  /** Last updated timestamp for unstructured data. */
		unstructuredDataUpdateTime: Option[String] = None,
	  /** Last updated timestamp for websites. */
		websiteDataUpdateTime: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaWorkspaceConfig {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, GOOGLE_DRIVE, GOOGLE_MAIL, GOOGLE_SITES, GOOGLE_CALENDAR, GOOGLE_CHAT, GOOGLE_GROUPS, GOOGLE_KEEP }
	}
	case class GoogleCloudDiscoveryengineV1alphaWorkspaceConfig(
	  /** The Google Workspace data source. */
		`type`: Option[Schema.GoogleCloudDiscoveryengineV1alphaWorkspaceConfig.TypeEnum] = None,
	  /** Obfuscated Dasher customer ID. */
		dasherCustomerId: Option[String] = None,
	  /** Optional. The super admin service account for the workspace that will be used for access token generation. For now we only use it for Native Google Drive connector data ingestion. */
		superAdminServiceAccount: Option[String] = None,
	  /** Optional. The super admin email address for the workspace that will be used for access token generation. For now we only use it for Native Google Drive connector data ingestion. */
		superAdminEmailAddress: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaDocumentProcessingConfig(
	  /** The full resource name of the Document Processing Config. Format: `projects/&#42;/locations/&#42;/collections/&#42;/dataStores/&#42;/documentProcessingConfig`. */
		name: Option[String] = None,
	  /** Whether chunking mode is enabled. */
		chunkingConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaDocumentProcessingConfigChunkingConfig] = None,
	  /** Configurations for default Document parser. If not specified, we will configure it as default DigitalParsingConfig, and the default parsing config will be applied to all file types for Document parsing. */
		defaultParsingConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaDocumentProcessingConfigParsingConfig] = None,
	  /** Map from file type to override the default parsing configuration based on the file type. Supported keys: &#42; `pdf`: Override parsing config for PDF files, either digital parsing, ocr parsing or layout parsing is supported. &#42; `html`: Override parsing config for HTML files, only digital parsing and layout parsing are supported. &#42; `docx`: Override parsing config for DOCX files, only digital parsing and layout parsing are supported. &#42; `pptx`: Override parsing config for PPTX files, only digital parsing and layout parsing are supported. &#42; `xlsm`: Override parsing config for XLSM files, only digital parsing and layout parsing are supported. &#42; `xlsx`: Override parsing config for XLSX files, only digital parsing and layout parsing are supported. */
		parsingConfigOverrides: Option[Map[String, Schema.GoogleCloudDiscoveryengineV1alphaDocumentProcessingConfigParsingConfig]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaDocumentProcessingConfigChunkingConfig(
	  /** Configuration for the layout based chunking. */
		layoutBasedChunkingConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaDocumentProcessingConfigChunkingConfigLayoutBasedChunkingConfig] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaDocumentProcessingConfigChunkingConfigLayoutBasedChunkingConfig(
	  /** The token size limit for each chunk. Supported values: 100-500 (inclusive). Default value: 500. */
		chunkSize: Option[Int] = None,
	  /** Whether to include appending different levels of headings to chunks from the middle of the document to prevent context loss. Default value: False. */
		includeAncestorHeadings: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaDocumentProcessingConfigParsingConfig(
	  /** Configurations applied to digital parser. */
		digitalParsingConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaDocumentProcessingConfigParsingConfigDigitalParsingConfig] = None,
	  /** Configurations applied to OCR parser. Currently it only applies to PDFs. */
		ocrParsingConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaDocumentProcessingConfigParsingConfigOcrParsingConfig] = None,
	  /** Configurations applied to layout parser. */
		layoutParsingConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaDocumentProcessingConfigParsingConfigLayoutParsingConfig] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaDocumentProcessingConfigParsingConfigDigitalParsingConfig(
	
	)
	
	case class GoogleCloudDiscoveryengineV1alphaDocumentProcessingConfigParsingConfigOcrParsingConfig(
	  /** [DEPRECATED] This field is deprecated. To use the additional enhanced document elements processing, please switch to `layout_parsing_config`. */
		enhancedDocumentElements: Option[List[String]] = None,
	  /** If true, will use native text instead of OCR text on pages containing native text. */
		useNativeText: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaDocumentProcessingConfigParsingConfigLayoutParsingConfig(
	
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSchema(
	  /** The structured representation of the schema. */
		structSchema: Option[Map[String, JsValue]] = None,
	  /** The JSON representation of the schema. */
		jsonSchema: Option[String] = None,
	  /** Immutable. The full resource name of the schema, in the format of `projects/{project}/locations/{location}/collections/{collection}/dataStores/{data_store}/schemas/{schema}`. This field must be a UTF-8 encoded string with a length limit of 1024 characters. */
		name: Option[String] = None,
	  /** Output only. Configurations for fields of the schema. */
		fieldConfigs: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaFieldConfig]] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaFieldConfig {
		enum FieldTypeEnum extends Enum[FieldTypeEnum] { case FIELD_TYPE_UNSPECIFIED, OBJECT, STRING, NUMBER, INTEGER, BOOLEAN, GEOLOCATION, DATETIME }
		enum IndexableOptionEnum extends Enum[IndexableOptionEnum] { case INDEXABLE_OPTION_UNSPECIFIED, INDEXABLE_ENABLED, INDEXABLE_DISABLED }
		enum DynamicFacetableOptionEnum extends Enum[DynamicFacetableOptionEnum] { case DYNAMIC_FACETABLE_OPTION_UNSPECIFIED, DYNAMIC_FACETABLE_ENABLED, DYNAMIC_FACETABLE_DISABLED }
		enum SearchableOptionEnum extends Enum[SearchableOptionEnum] { case SEARCHABLE_OPTION_UNSPECIFIED, SEARCHABLE_ENABLED, SEARCHABLE_DISABLED }
		enum RetrievableOptionEnum extends Enum[RetrievableOptionEnum] { case RETRIEVABLE_OPTION_UNSPECIFIED, RETRIEVABLE_ENABLED, RETRIEVABLE_DISABLED }
		enum CompletableOptionEnum extends Enum[CompletableOptionEnum] { case COMPLETABLE_OPTION_UNSPECIFIED, COMPLETABLE_ENABLED, COMPLETABLE_DISABLED }
		enum RecsFilterableOptionEnum extends Enum[RecsFilterableOptionEnum] { case FILTERABLE_OPTION_UNSPECIFIED, FILTERABLE_ENABLED, FILTERABLE_DISABLED }
		enum AdvancedSiteSearchDataSourcesEnum extends Enum[AdvancedSiteSearchDataSourcesEnum] { case ADVANCED_SITE_SEARCH_DATA_SOURCE_UNSPECIFIED, METATAGS, PAGEMAP, URI_PATTERN_MAPPING, SCHEMA_ORG }
	}
	case class GoogleCloudDiscoveryengineV1alphaFieldConfig(
	  /** Required. Field path of the schema field. For example: `title`, `description`, `release_info.release_year`. */
		fieldPath: Option[String] = None,
	  /** Output only. Raw type of the field. */
		fieldType: Option[Schema.GoogleCloudDiscoveryengineV1alphaFieldConfig.FieldTypeEnum] = None,
	  /** If indexable_option is INDEXABLE_ENABLED, field values are indexed so that it can be filtered or faceted in SearchService.Search. If indexable_option is unset, the server behavior defaults to INDEXABLE_DISABLED for fields that support setting indexable options. For those fields that do not support setting indexable options, such as `object` and `boolean` and key properties, the server will skip indexable_option setting, and setting indexable_option for those fields will throw `INVALID_ARGUMENT` error. */
		indexableOption: Option[Schema.GoogleCloudDiscoveryengineV1alphaFieldConfig.IndexableOptionEnum] = None,
	  /** If dynamic_facetable_option is DYNAMIC_FACETABLE_ENABLED, field values are available for dynamic facet. Could only be DYNAMIC_FACETABLE_DISABLED if FieldConfig.indexable_option is INDEXABLE_DISABLED. Otherwise, an `INVALID_ARGUMENT` error will be returned. If dynamic_facetable_option is unset, the server behavior defaults to DYNAMIC_FACETABLE_DISABLED for fields that support setting dynamic facetable options. For those fields that do not support setting dynamic facetable options, such as `object` and `boolean`, the server will skip dynamic facetable option setting, and setting dynamic_facetable_option for those fields will throw `INVALID_ARGUMENT` error. */
		dynamicFacetableOption: Option[Schema.GoogleCloudDiscoveryengineV1alphaFieldConfig.DynamicFacetableOptionEnum] = None,
	  /** If searchable_option is SEARCHABLE_ENABLED, field values are searchable by text queries in SearchService.Search. If SEARCHABLE_ENABLED but field type is numerical, field values will not be searchable by text queries in SearchService.Search, as there are no text values associated to numerical fields. If searchable_option is unset, the server behavior defaults to SEARCHABLE_DISABLED for fields that support setting searchable options. Only `string` fields that have no key property mapping support setting searchable_option. For those fields that do not support setting searchable options, the server will skip searchable option setting, and setting searchable_option for those fields will throw `INVALID_ARGUMENT` error. */
		searchableOption: Option[Schema.GoogleCloudDiscoveryengineV1alphaFieldConfig.SearchableOptionEnum] = None,
	  /** If retrievable_option is RETRIEVABLE_ENABLED, field values are included in the search results. If retrievable_option is unset, the server behavior defaults to RETRIEVABLE_DISABLED for fields that support setting retrievable options. For those fields that do not support setting retrievable options, such as `object` and `boolean`, the server will skip retrievable option setting, and setting retrievable_option for those fields will throw `INVALID_ARGUMENT` error. */
		retrievableOption: Option[Schema.GoogleCloudDiscoveryengineV1alphaFieldConfig.RetrievableOptionEnum] = None,
	  /** If completable_option is COMPLETABLE_ENABLED, field values are directly used and returned as suggestions for Autocomplete in CompletionService.CompleteQuery. If completable_option is unset, the server behavior defaults to COMPLETABLE_DISABLED for fields that support setting completable options, which are just `string` fields. For those fields that do not support setting completable options, the server will skip completable option setting, and setting completable_option for those fields will throw `INVALID_ARGUMENT` error. */
		completableOption: Option[Schema.GoogleCloudDiscoveryengineV1alphaFieldConfig.CompletableOptionEnum] = None,
	  /** If recs_filterable_option is FILTERABLE_ENABLED, field values are filterable by filter expression in RecommendationService.Recommend. If FILTERABLE_ENABLED but the field type is numerical, field values are not filterable by text queries in RecommendationService.Recommend. Only textual fields are supported. If recs_filterable_option is unset, the default setting is FILTERABLE_DISABLED for fields that support setting filterable options. When a field set to [FILTERABLE_DISABLED] is filtered, a warning is generated and an empty result is returned. */
		recsFilterableOption: Option[Schema.GoogleCloudDiscoveryengineV1alphaFieldConfig.RecsFilterableOptionEnum] = None,
	  /** Output only. Type of the key property that this field is mapped to. Empty string if this is not annotated as mapped to a key property. Example types are `title`, `description`. Full list is defined by `keyPropertyMapping` in the schema field annotation. If the schema field has a `KeyPropertyMapping` annotation, `indexable_option` and `searchable_option` of this field cannot be modified. */
		keyPropertyType: Option[String] = None,
	  /** If this field is set, only the corresponding source will be indexed for this field. Otherwise, the values from different sources are merged. Assuming a page with `` in meta tag, and `` in page map: if this enum is set to METATAGS, we will only index ``; if this enum is not set, we will merge them and index ``. */
		advancedSiteSearchDataSources: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaFieldConfig.AdvancedSiteSearchDataSourcesEnum]] = None,
	  /** Field paths for indexing custom attribute from schema.org data. More details of schema.org and its defined types can be found at [schema.org](https://schema.org). It is only used on advanced site search schema. Currently only support full path from root. The full path to a field is constructed by concatenating field names, starting from `_root`, with a period `.` as the delimiter. Examples: &#42; Publish date of the root: _root.datePublished &#42; Publish date of the reviews: _root.review.datePublished */
		schemaOrgPaths: Option[List[String]] = None,
	  /** Optional. The metatag name found in the HTML page. If user defines this field, the value of this metatag name will be used to extract metatag. If the user does not define this field, the FieldConfig.field_path will be used to extract metatag. */
		metatagName: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaDataStoreServingConfigDataStore(
	  /** If set true, the DataStore will not be available for serving search requests. */
		disabledForServing: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaDeleteDataStoreMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaDeleteEngineMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaDeleteSchemaMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaDeleteSitemapMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaDeleteTargetSiteMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaDisableAdvancedSiteSearchMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaDisableAdvancedSiteSearchResponse(
	
	)
	
	case class GoogleCloudDiscoveryengineV1alphaEnableAdvancedSiteSearchMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaEnableAdvancedSiteSearchResponse(
	
	)
	
	object GoogleCloudDiscoveryengineV1alphaEngine {
		enum SolutionTypeEnum extends Enum[SolutionTypeEnum] { case SOLUTION_TYPE_UNSPECIFIED, SOLUTION_TYPE_RECOMMENDATION, SOLUTION_TYPE_SEARCH, SOLUTION_TYPE_CHAT, SOLUTION_TYPE_GENERATIVE_CHAT }
		enum IndustryVerticalEnum extends Enum[IndustryVerticalEnum] { case INDUSTRY_VERTICAL_UNSPECIFIED, GENERIC, MEDIA, HEALTHCARE_FHIR }
	}
	case class GoogleCloudDiscoveryengineV1alphaEngine(
	  /** Additional config specs for a `similar-items` engine. */
		similarDocumentsConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaEngineSimilarDocumentsEngineConfig] = None,
	  /** Configurations for the Chat Engine. Only applicable if solution_type is SOLUTION_TYPE_CHAT. */
		chatEngineConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaEngineChatEngineConfig] = None,
	  /** Configurations for the Search Engine. Only applicable if solution_type is SOLUTION_TYPE_SEARCH. */
		searchEngineConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaEngineSearchEngineConfig] = None,
	  /** Configurations for the Media Engine. Only applicable on the data stores with solution_type SOLUTION_TYPE_RECOMMENDATION and IndustryVertical.MEDIA vertical. */
		mediaRecommendationEngineConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaEngineMediaRecommendationEngineConfig] = None,
	  /** Output only. Additional information of a recommendation engine. Only applicable if solution_type is SOLUTION_TYPE_RECOMMENDATION. */
		recommendationMetadata: Option[Schema.GoogleCloudDiscoveryengineV1alphaEngineRecommendationMetadata] = None,
	  /** Output only. Additional information of the Chat Engine. Only applicable if solution_type is SOLUTION_TYPE_CHAT. */
		chatEngineMetadata: Option[Schema.GoogleCloudDiscoveryengineV1alphaEngineChatEngineMetadata] = None,
	  /** Immutable. The fully qualified resource name of the engine. This field must be a UTF-8 encoded string with a length limit of 1024 characters. Format: `projects/{project}/locations/{location}/collections/{collection}/engines/{engine}` engine should be 1-63 characters, and valid characters are /a-z0-9&#42;/. Otherwise, an INVALID_ARGUMENT error is returned. */
		name: Option[String] = None,
	  /** Required. The display name of the engine. Should be human readable. UTF-8 encoded string with limit of 1024 characters. */
		displayName: Option[String] = None,
	  /** Output only. Timestamp the Recommendation Engine was created at. */
		createTime: Option[String] = None,
	  /** Output only. Timestamp the Recommendation Engine was last updated. */
		updateTime: Option[String] = None,
	  /** The data stores associated with this engine. For SOLUTION_TYPE_SEARCH and SOLUTION_TYPE_RECOMMENDATION type of engines, they can only associate with at most one data store. If solution_type is SOLUTION_TYPE_CHAT, multiple DataStores in the same Collection can be associated here. Note that when used in CreateEngineRequest, one DataStore id must be provided as the system will use it for necessary initializations. */
		dataStoreIds: Option[List[String]] = None,
	  /** Required. The solutions of the engine. */
		solutionType: Option[Schema.GoogleCloudDiscoveryengineV1alphaEngine.SolutionTypeEnum] = None,
	  /** The industry vertical that the engine registers. The restriction of the Engine industry vertical is based on DataStore: If unspecified, default to `GENERIC`. Vertical on Engine has to match vertical of the DataStore linked to the engine. */
		industryVertical: Option[Schema.GoogleCloudDiscoveryengineV1alphaEngine.IndustryVerticalEnum] = None,
	  /** Common config spec that specifies the metadata of the engine. */
		commonConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaEngineCommonConfig] = None,
	  /** Optional. Whether to disable analytics for searches performed on this engine. */
		disableAnalytics: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaEngineSimilarDocumentsEngineConfig(
	
	)
	
	case class GoogleCloudDiscoveryengineV1alphaEngineChatEngineConfig(
	  /** The configurationt generate the Dialogflow agent that is associated to this Engine. Note that these configurations are one-time consumed by and passed to Dialogflow service. It means they cannot be retrieved using EngineService.GetEngine or EngineService.ListEngines API after engine creation. */
		agentCreationConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaEngineChatEngineConfigAgentCreationConfig] = None,
	  /** The resource name of an exist Dialogflow agent to link to this Chat Engine. Customers can either provide `agent_creation_config` to create agent or provide an agent name that links the agent with the Chat engine. Format: `projects//locations//agents/`. Note that the `dialogflow_agent_to_link` are one-time consumed by and passed to Dialogflow service. It means they cannot be retrieved using EngineService.GetEngine or EngineService.ListEngines API after engine creation. Use ChatEngineMetadata.dialogflow_agent for actual agent association after Engine is created. */
		dialogflowAgentToLink: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaEngineChatEngineConfigAgentCreationConfig(
	  /** Name of the company, organization or other entity that the agent represents. Used for knowledge connector LLM prompt and for knowledge search. */
		business: Option[String] = None,
	  /** Required. The default language of the agent as a language tag. See [Language Support](https://cloud.google.com/dialogflow/docs/reference/language) for a list of the currently supported language codes. */
		defaultLanguageCode: Option[String] = None,
	  /** Required. The time zone of the agent from the [time zone database](https://www.iana.org/time-zones), e.g., America/New_York, Europe/Paris. */
		timeZone: Option[String] = None,
	  /** Agent location for Agent creation, supported values: global/us/eu. If not provided, us Engine will create Agent using us-central-1 by default; eu Engine will create Agent using eu-west-1 by default. */
		location: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaEngineSearchEngineConfig {
		enum SearchTierEnum extends Enum[SearchTierEnum] { case SEARCH_TIER_UNSPECIFIED, SEARCH_TIER_STANDARD, SEARCH_TIER_ENTERPRISE }
		enum SearchAddOnsEnum extends Enum[SearchAddOnsEnum] { case SEARCH_ADD_ON_UNSPECIFIED, SEARCH_ADD_ON_LLM }
	}
	case class GoogleCloudDiscoveryengineV1alphaEngineSearchEngineConfig(
	  /** The search feature tier of this engine. Different tiers might have different pricing. To learn more, check the pricing documentation. Defaults to SearchTier.SEARCH_TIER_STANDARD if not specified. */
		searchTier: Option[Schema.GoogleCloudDiscoveryengineV1alphaEngineSearchEngineConfig.SearchTierEnum] = None,
	  /** The add-on that this search engine enables. */
		searchAddOns: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaEngineSearchEngineConfig.SearchAddOnsEnum]] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaEngineMediaRecommendationEngineConfig {
		enum TrainingStateEnum extends Enum[TrainingStateEnum] { case TRAINING_STATE_UNSPECIFIED, PAUSED, TRAINING }
	}
	case class GoogleCloudDiscoveryengineV1alphaEngineMediaRecommendationEngineConfig(
	  /** Required. The type of engine. e.g., `recommended-for-you`. This field together with optimization_objective describe engine metadata to use to control engine training and serving. Currently supported values: `recommended-for-you`, `others-you-may-like`, `more-like-this`, `most-popular-items`. */
		`type`: Option[String] = None,
	  /** The optimization objective. e.g., `cvr`. This field together with optimization_objective describe engine metadata to use to control engine training and serving. Currently supported values: `ctr`, `cvr`. If not specified, we choose default based on engine type. Default depends on type of recommendation: `recommended-for-you` => `ctr` `others-you-may-like` => `ctr` */
		optimizationObjective: Option[String] = None,
	  /** Name and value of the custom threshold for cvr optimization_objective. For target_field `watch-time`, target_field_value must be an integer value indicating the media progress time in seconds between (0, 86400] (excludes 0, includes 86400) (e.g., 90). For target_field `watch-percentage`, the target_field_value must be a valid float value between (0, 1.0] (excludes 0, includes 1.0) (e.g., 0.5). */
		optimizationObjectiveConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaEngineMediaRecommendationEngineConfigOptimizationObjectiveConfig] = None,
	  /** The training state that the engine is in (e.g. `TRAINING` or `PAUSED`). Since part of the cost of running the service is frequency of training - this can be used to determine when to train engine in order to control cost. If not specified: the default value for `CreateEngine` method is `TRAINING`. The default value for `UpdateEngine` method is to keep the state the same as before. */
		trainingState: Option[Schema.GoogleCloudDiscoveryengineV1alphaEngineMediaRecommendationEngineConfig.TrainingStateEnum] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaEngineMediaRecommendationEngineConfigOptimizationObjectiveConfig(
	  /** Required. The name of the field to target. Currently supported values: `watch-percentage`, `watch-time`. */
		targetField: Option[String] = None,
	  /** Required. The threshold to be applied to the target (e.g., 0.5). */
		targetFieldValueFloat: Option[BigDecimal] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaEngineRecommendationMetadata {
		enum ServingStateEnum extends Enum[ServingStateEnum] { case SERVING_STATE_UNSPECIFIED, INACTIVE, ACTIVE, TUNED }
		enum DataStateEnum extends Enum[DataStateEnum] { case DATA_STATE_UNSPECIFIED, DATA_OK, DATA_ERROR }
	}
	case class GoogleCloudDiscoveryengineV1alphaEngineRecommendationMetadata(
	  /** Output only. The serving state of the engine: `ACTIVE`, `NOT_ACTIVE`. */
		servingState: Option[Schema.GoogleCloudDiscoveryengineV1alphaEngineRecommendationMetadata.ServingStateEnum] = None,
	  /** Output only. The state of data requirements for this engine: `DATA_OK` and `DATA_ERROR`. Engine cannot be trained if the data is in `DATA_ERROR` state. Engine can have `DATA_ERROR` state even if serving state is `ACTIVE`: engines were trained successfully before, but cannot be refreshed because the underlying engine no longer has sufficient data for training. */
		dataState: Option[Schema.GoogleCloudDiscoveryengineV1alphaEngineRecommendationMetadata.DataStateEnum] = None,
	  /** Output only. The timestamp when the latest successful tune finished. Only applicable on Media Recommendation engines. */
		lastTuneTime: Option[String] = None,
	  /** Output only. The latest tune operation id associated with the engine. Only applicable on Media Recommendation engines. If present, this operation id can be used to determine if there is an ongoing tune for this engine. To check the operation status, send the GetOperation request with this operation id in the engine resource format. If no tuning has happened for this engine, the string is empty. */
		tuningOperation: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaEngineChatEngineMetadata(
	  /** The resource name of a Dialogflow agent, that this Chat Engine refers to. Format: `projects//locations//agents/`. */
		dialogflowAgent: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaEngineCommonConfig(
	  /** The name of the company, business or entity that is associated with the engine. Setting this may help improve LLM related features. */
		companyName: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaEstimateDataSizeMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaEstimateDataSizeResponse(
	  /** Data size in terms of bytes. */
		dataSizeBytes: Option[String] = None,
	  /** Total number of documents. */
		documentCount: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaEvaluation {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, RUNNING, SUCCEEDED, FAILED }
	}
	case class GoogleCloudDiscoveryengineV1alphaEvaluation(
	  /** Identifier. The full resource name of the Evaluation, in the format of `projects/{project}/locations/{location}/evaluations/{evaluation}`. This field must be a UTF-8 encoded string with a length limit of 1024 characters. */
		name: Option[String] = None,
	  /** Required. The specification of the evaluation. */
		evaluationSpec: Option[Schema.GoogleCloudDiscoveryengineV1alphaEvaluationEvaluationSpec] = None,
	  /** Output only. The metrics produced by the evaluation, averaged across all SampleQuerys in the SampleQuerySet. Only populated when the evaluation's state is SUCCEEDED. */
		qualityMetrics: Option[Schema.GoogleCloudDiscoveryengineV1alphaQualityMetrics] = None,
	  /** Output only. The state of the evaluation. */
		state: Option[Schema.GoogleCloudDiscoveryengineV1alphaEvaluation.StateEnum] = None,
	  /** Output only. The error that occurred during evaluation. Only populated when the evaluation's state is FAILED. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** Output only. Timestamp the Evaluation was created at. */
		createTime: Option[String] = None,
	  /** Output only. Timestamp the Evaluation was completed at. */
		endTime: Option[String] = None,
	  /** Output only. A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaEvaluationEvaluationSpec(
	  /** Required. The search request that is used to perform the evaluation. Only the following fields within SearchRequest are supported; if any other fields are provided, an UNSUPPORTED error will be returned: &#42; SearchRequest.serving_config &#42; SearchRequest.branch &#42; SearchRequest.canonical_filter &#42; SearchRequest.query_expansion_spec &#42; SearchRequest.spell_correction_spec &#42; SearchRequest.content_search_spec &#42; SearchRequest.user_pseudo_id */
		searchRequest: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequest] = None,
	  /** Required. The specification of the query set. */
		querySetSpec: Option[Schema.GoogleCloudDiscoveryengineV1alphaEvaluationEvaluationSpecQuerySetSpec] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaSearchRequest {
		enum RelevanceThresholdEnum extends Enum[RelevanceThresholdEnum] { case RELEVANCE_THRESHOLD_UNSPECIFIED, LOWEST, LOW, MEDIUM, HIGH }
	}
	case class GoogleCloudDiscoveryengineV1alphaSearchRequest(
	  /** Required. The resource name of the Search serving config, such as `projects/&#42;/locations/global/collections/default_collection/engines/&#42;/servingConfigs/default_serving_config`, or `projects/&#42;/locations/global/collections/default_collection/dataStores/default_data_store/servingConfigs/default_serving_config`. This field is used to identify the serving configuration name, set of models used to make the search. */
		servingConfig: Option[String] = None,
	  /** The branch resource name, such as `projects/&#42;/locations/global/collections/default_collection/dataStores/default_data_store/branches/0`. Use `default_branch` as the branch ID or leave this field empty, to search documents under the default branch. */
		branch: Option[String] = None,
	  /** Raw search query. */
		query: Option[String] = None,
	  /** Raw image query. */
		imageQuery: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestImageQuery] = None,
	  /** Maximum number of Documents to return. The maximum allowed value depends on the data type. Values above the maximum value are coerced to the maximum value. &#42; Websites with basic indexing: Default `10`, Maximum `25`. &#42; Websites with advanced indexing: Default `25`, Maximum `50`. &#42; Other: Default `50`, Maximum `100`. If this field is negative, an `INVALID_ARGUMENT` is returned. */
		pageSize: Option[Int] = None,
	  /** A page token received from a previous SearchService.Search call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to SearchService.Search must match the call that provided the page token. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		pageToken: Option[String] = None,
	  /** A 0-indexed integer that specifies the current offset (that is, starting result location, amongst the Documents deemed by the API as relevant) in search results. This field is only considered if page_token is unset. If this field is negative, an `INVALID_ARGUMENT` is returned. */
		offset: Option[Int] = None,
	  /** The maximum number of results to return for OneBox. This applies to each OneBox type individually. Default number is 10. */
		oneBoxPageSize: Option[Int] = None,
	  /** Specs defining dataStores to filter on in a search call and configurations for those dataStores. This is only considered for engines with multiple dataStores use case. For single dataStore within an engine, they should use the specs at the top level. */
		dataStoreSpecs: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestDataStoreSpec]] = None,
	  /** The filter syntax consists of an expression language for constructing a predicate from one or more fields of the documents being filtered. Filter expression is case-sensitive. If this field is unrecognizable, an `INVALID_ARGUMENT` is returned. Filtering in Vertex AI Search is done by mapping the LHS filter key to a key property defined in the Vertex AI Search backend -- this mapping is defined by the customer in their schema. For example a media customer might have a field 'name' in their schema. In this case the filter would look like this: filter --> name:'ANY("king kong")' For more information about filtering including syntax and filter operators, see [Filter](https://cloud.google.com/generative-ai-app-builder/docs/filter-search-metadata) */
		filter: Option[String] = None,
	  /** The default filter that is applied when a user performs a search without checking any filters on the search page. The filter applied to every search request when quality improvement such as query expansion is needed. In the case a query does not have a sufficient amount of results this filter will be used to determine whether or not to enable the query expansion flow. The original filter will still be used for the query expanded search. This field is strongly recommended to achieve high search quality. For more information about filter syntax, see SearchRequest.filter. */
		canonicalFilter: Option[String] = None,
	  /** The order in which documents are returned. Documents can be ordered by a field in an Document object. Leave it unset if ordered by relevance. `order_by` expression is case-sensitive. For more information on ordering the website search results, see [Order web search results](https://cloud.google.com/generative-ai-app-builder/docs/order-web-search-results). For more information on ordering the healthcare search results, see [Order healthcare search results](https://cloud.google.com/generative-ai-app-builder/docs/order-hc-results). If this field is unrecognizable, an `INVALID_ARGUMENT` is returned. */
		orderBy: Option[String] = None,
	  /** Information about the end user. Highly recommended for analytics. UserInfo.user_agent is used to deduce `device_type` for analytics. */
		userInfo: Option[Schema.GoogleCloudDiscoveryengineV1alphaUserInfo] = None,
	  /** The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see [Standard fields](https://cloud.google.com/apis/design/standard_fields). This field helps to better interpret the query. If a value isn't specified, the query language code is automatically detected, which may not be accurate. */
		languageCode: Option[String] = None,
	  /** The Unicode country/region code (CLDR) of a location, such as "US" and "419". For more information, see [Standard fields](https://cloud.google.com/apis/design/standard_fields). If set, then results will be boosted based on the region_code provided. */
		regionCode: Option[String] = None,
	  /** Facet specifications for faceted search. If empty, no facets are returned. A maximum of 100 values are allowed. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		facetSpecs: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestFacetSpec]] = None,
	  /** Boost specification to boost certain documents. For more information on boosting, see [Boosting](https://cloud.google.com/generative-ai-app-builder/docs/boost-search-results) */
		boostSpec: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestBoostSpec] = None,
	  /** Additional search parameters. For public website search only, supported values are: &#42; `user_country_code`: string. Default empty. If set to non-empty, results are restricted or boosted based on the location provided. For example, `user_country_code: "au"` For available codes see [Country Codes](https://developers.google.com/custom-search/docs/json_api_reference#countryCodes) &#42; `search_type`: double. Default empty. Enables non-webpage searching depending on the value. The only valid non-default value is 1, which enables image searching. For example, `search_type: 1` */
		params: Option[Map[String, JsValue]] = None,
	  /** The query expansion specification that specifies the conditions under which query expansion occurs. */
		queryExpansionSpec: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestQueryExpansionSpec] = None,
	  /** The spell correction specification that specifies the mode under which spell correction takes effect. */
		spellCorrectionSpec: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestSpellCorrectionSpec] = None,
	  /** A unique identifier for tracking visitors. For example, this could be implemented with an HTTP cookie, which should be able to uniquely identify a visitor on a single device. This unique identifier should not change if the visitor logs in or out of the website. This field should NOT have a fixed value such as `unknown_visitor`. This should be the same identifier as UserEvent.user_pseudo_id and CompleteQueryRequest.user_pseudo_id The field must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		userPseudoId: Option[String] = None,
	  /** A specification for configuring the behavior of content search. */
		contentSearchSpec: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestContentSearchSpec] = None,
	  /** Uses the provided embedding to do additional semantic document retrieval. The retrieval is based on the dot product of SearchRequest.EmbeddingSpec.EmbeddingVector.vector and the document embedding that is provided in SearchRequest.EmbeddingSpec.EmbeddingVector.field_path. If SearchRequest.EmbeddingSpec.EmbeddingVector.field_path is not provided, it will use ServingConfig.EmbeddingConfig.field_path. */
		embeddingSpec: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestEmbeddingSpec] = None,
	  /** The ranking expression controls the customized ranking on retrieval documents. This overrides ServingConfig.ranking_expression. The ranking expression is a single function or multiple functions that are joined by "+". &#42; ranking_expression = function, { " + ", function }; Supported functions: &#42; double &#42; relevance_score &#42; double &#42; dotProduct(embedding_field_path) Function variables: &#42; `relevance_score`: pre-defined keywords, used for measure relevance between query and document. &#42; `embedding_field_path`: the document embedding field used with query embedding vector. &#42; `dotProduct`: embedding function between embedding_field_path and query embedding vector. Example ranking expression: If document has an embedding field doc_embedding, the ranking expression could be `0.5 &#42; relevance_score + 0.3 &#42; dotProduct(doc_embedding)`. */
		rankingExpression: Option[String] = None,
	  /** Whether to turn on safe search. This is only supported for website search. */
		safeSearch: Option[Boolean] = None,
	  /** The user labels applied to a resource must meet the following requirements: &#42; Each resource can have multiple labels, up to a maximum of 64. &#42; Each label must be a key-value pair. &#42; Keys have a minimum length of 1 character and a maximum length of 63 characters and cannot be empty. Values can be empty and have a maximum length of 63 characters. &#42; Keys and values can contain only lowercase letters, numeric characters, underscores, and dashes. All characters must use UTF-8 encoding, and international characters are allowed. &#42; The key portion of a label must be unique. However, you can use the same key with multiple resources. &#42; Keys must start with a lowercase letter or international character. See [Google Cloud Document](https://cloud.google.com/resource-manager/docs/creating-managing-labels#requirements) for more details. */
		userLabels: Option[Map[String, String]] = None,
	  /** If `naturalLanguageQueryUnderstandingSpec` is not specified, no additional natural language query understanding will be done. */
		naturalLanguageQueryUnderstandingSpec: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestNaturalLanguageQueryUnderstandingSpec] = None,
	  /** Search as you type configuration. Only supported for the IndustryVertical.MEDIA vertical. */
		searchAsYouTypeSpec: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestSearchAsYouTypeSpec] = None,
	  /** Custom fine tuning configs. If set, it has higher priority than the configs set in ServingConfig.custom_fine_tuning_spec. */
		customFineTuningSpec: Option[Schema.GoogleCloudDiscoveryengineV1alphaCustomFineTuningSpec] = None,
	  /** The session resource name. Optional. Session allows users to do multi-turn /search API calls or coordination between /search API calls and /answer API calls. Example #1 (multi-turn /search API calls): 1. Call /search API with the auto-session mode (see below). 2. Call /search API with the session ID generated in the first call. Here, the previous search query gets considered in query standing. I.e., if the first query is "How did Alphabet do in 2022?" and the current query is "How about 2023?", the current query will be interpreted as "How did Alphabet do in 2023?". Example #2 (coordination between /search API calls and /answer API calls): 1. Call /search API with the auto-session mode (see below). 2. Call /answer API with the session ID generated in the first call. Here, the answer generation happens in the context of the search results from the first search call. Auto-session mode: when `projects/.../sessions/-` is used, a new session gets automatically created. Otherwise, users can use the create-session API to create a session manually. Multi-turn Search feature is currently at private GA stage. Please use v1alpha or v1beta version instead before we launch this feature to public GA. Or ask for allowlisting through Google Support team. */
		session: Option[String] = None,
	  /** Session specification. Can be used only when `session` is set. */
		sessionSpec: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestSessionSpec] = None,
	  /** The relevance threshold of the search results. Default to Google defined threshold, leveraging a balance of precision and recall to deliver both highly accurate results and comprehensive coverage of relevant information. */
		relevanceThreshold: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequest.RelevanceThresholdEnum] = None,
	  /** The specification for personalization. Notice that if both ServingConfig.personalization_spec and SearchRequest.personalization_spec are set, SearchRequest.personalization_spec overrides ServingConfig.personalization_spec. */
		personalizationSpec: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestPersonalizationSpec] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestImageQuery(
	  /** Base64 encoded image bytes. Supported image formats: JPEG, PNG, and BMP. */
		imageBytes: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestDataStoreSpec(
	  /** Required. Full resource name of DataStore, such as `projects/{project}/locations/{location}/collections/{collection_id}/dataStores/{data_store_id}`. */
		dataStore: Option[String] = None,
	  /** Optional. Filter specification to filter documents in the data store specified by data_store field. For more information on filtering, see [Filtering](https://cloud.google.com/generative-ai-app-builder/docs/filter-search-metadata) */
		filter: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaUserInfo(
	  /** Highly recommended for logged-in users. Unique identifier for logged-in user, such as a user name. Don't set for anonymous users. Always use a hashed value for this ID. Don't set the field to the same fixed ID for different users. This mixes the event history of those users together, which results in degraded model quality. The field must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		userId: Option[String] = None,
	  /** User agent as included in the HTTP header. The field must be a UTF-8 encoded string with a length limit of 1,000 characters. Otherwise, an `INVALID_ARGUMENT` error is returned. This should not be set when using the client side event reporting with GTM or JavaScript tag in UserEventService.CollectUserEvent or if UserEvent.direct_user_request is set. */
		userAgent: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestFacetSpec(
	  /** Required. The facet key specification. */
		facetKey: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestFacetSpecFacetKey] = None,
	  /** Maximum facet values that are returned for this facet. If unspecified, defaults to 20. The maximum allowed value is 300. Values above 300 are coerced to 300. For aggregation in healthcare search, when the [FacetKey.key] is "healthcare_aggregation_key", the limit will be overridden to 10,000 internally, regardless of the value set here. If this field is negative, an `INVALID_ARGUMENT` is returned. */
		limit: Option[Int] = None,
	  /** List of keys to exclude when faceting. By default, FacetKey.key is not excluded from the filter unless it is listed in this field. Listing a facet key in this field allows its values to appear as facet results, even when they are filtered out of search results. Using this field does not affect what search results are returned. For example, suppose there are 100 documents with the color facet "Red" and 200 documents with the color facet "Blue". A query containing the filter "color:ANY("Red")" and having "color" as FacetKey.key would by default return only "Red" documents in the search results, and also return "Red" with count 100 as the only color facet. Although there are also blue documents available, "Blue" would not be shown as an available facet value. If "color" is listed in "excludedFilterKeys", then the query returns the facet values "Red" with count 100 and "Blue" with count 200, because the "color" key is now excluded from the filter. Because this field doesn't affect search results, the search results are still correctly filtered to return only "Red" documents. A maximum of 100 values are allowed. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		excludedFilterKeys: Option[List[String]] = None,
	  /** Enables dynamic position for this facet. If set to true, the position of this facet among all facets in the response is determined automatically. If dynamic facets are enabled, it is ordered together. If set to false, the position of this facet in the response is the same as in the request, and it is ranked before the facets with dynamic position enable and all dynamic facets. For example, you may always want to have rating facet returned in the response, but it's not necessarily to always display the rating facet at the top. In that case, you can set enable_dynamic_position to true so that the position of rating facet in response is determined automatically. Another example, assuming you have the following facets in the request: &#42; "rating", enable_dynamic_position = true &#42; "price", enable_dynamic_position = false &#42; "brands", enable_dynamic_position = false And also you have a dynamic facets enabled, which generates a facet `gender`. Then the final order of the facets in the response can be ("price", "brands", "rating", "gender") or ("price", "brands", "gender", "rating") depends on how API orders "gender" and "rating" facets. However, notice that "price" and "brands" are always ranked at first and second position because their enable_dynamic_position is false. */
		enableDynamicPosition: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestFacetSpecFacetKey(
	  /** Required. Supported textual and numerical facet keys in Document object, over which the facet values are computed. Facet key is case-sensitive. */
		key: Option[String] = None,
	  /** Set only if values should be bucketed into intervals. Must be set for facets with numerical values. Must not be set for facet with text values. Maximum number of intervals is 30. */
		intervals: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaInterval]] = None,
	  /** Only get facet for the given restricted values. Only supported on textual fields. For example, suppose "category" has three values "Action > 2022", "Action > 2021" and "Sci-Fi > 2022". If set "restricted_values" to "Action > 2022", the "category" facet only contains "Action > 2022". Only supported on textual fields. Maximum is 10. */
		restrictedValues: Option[List[String]] = None,
	  /** Only get facet values that start with the given string prefix. For example, suppose "category" has three values "Action > 2022", "Action > 2021" and "Sci-Fi > 2022". If set "prefixes" to "Action", the "category" facet only contains "Action > 2022" and "Action > 2021". Only supported on textual fields. Maximum is 10. */
		prefixes: Option[List[String]] = None,
	  /** Only get facet values that contain the given strings. For example, suppose "category" has three values "Action > 2022", "Action > 2021" and "Sci-Fi > 2022". If set "contains" to "2022", the "category" facet only contains "Action > 2022" and "Sci-Fi > 2022". Only supported on textual fields. Maximum is 10. */
		contains: Option[List[String]] = None,
	  /** True to make facet keys case insensitive when getting faceting values with prefixes or contains; false otherwise. */
		caseInsensitive: Option[Boolean] = None,
	  /** The order in which documents are returned. Allowed values are: &#42; "count desc", which means order by SearchResponse.Facet.values.count descending. &#42; "value desc", which means order by SearchResponse.Facet.values.value descending. Only applies to textual facets. If not set, textual values are sorted in [natural order](https://en.wikipedia.org/wiki/Natural_sort_order); numerical intervals are sorted in the order given by FacetSpec.FacetKey.intervals. */
		orderBy: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaInterval(
	  /** Inclusive lower bound. */
		minimum: Option[BigDecimal] = None,
	  /** Exclusive lower bound. */
		exclusiveMinimum: Option[BigDecimal] = None,
	  /** Inclusive upper bound. */
		maximum: Option[BigDecimal] = None,
	  /** Exclusive upper bound. */
		exclusiveMaximum: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestBoostSpec(
	  /** Condition boost specifications. If a document matches multiple conditions in the specifictions, boost scores from these specifications are all applied and combined in a non-linear way. Maximum number of specifications is 20. */
		conditionBoostSpecs: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestBoostSpecConditionBoostSpec]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestBoostSpecConditionBoostSpec(
	  /** An expression which specifies a boost condition. The syntax and supported fields are the same as a filter expression. See SearchRequest.filter for detail syntax and limitations. Examples: &#42; To boost documents with document ID "doc_1" or "doc_2", and color "Red" or "Blue": `(document_id: ANY("doc_1", "doc_2")) AND (color: ANY("Red", "Blue"))` */
		condition: Option[String] = None,
	  /** Strength of the condition boost, which should be in [-1, 1]. Negative boost means demotion. Default is 0.0. Setting to 1.0 gives the document a big promotion. However, it does not necessarily mean that the boosted document will be the top result at all times, nor that other documents will be excluded. Results could still be shown even when none of them matches the condition. And results that are significantly more relevant to the search query can still trump your heavily favored but irrelevant documents. Setting to -1.0 gives the document a big demotion. However, results that are deeply relevant might still be shown. The document will have an upstream battle to get a fairly high ranking, but it is not blocked out completely. Setting to 0.0 means no boost applied. The boosting condition is ignored. Only one of the (condition, boost) combination or the boost_control_spec below are set. If both are set then the global boost is ignored and the more fine-grained boost_control_spec is applied. */
		boost: Option[BigDecimal] = None,
	  /** Complex specification for custom ranking based on customer defined attribute value. */
		boostControlSpec: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestBoostSpecConditionBoostSpecBoostControlSpec] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaSearchRequestBoostSpecConditionBoostSpecBoostControlSpec {
		enum AttributeTypeEnum extends Enum[AttributeTypeEnum] { case ATTRIBUTE_TYPE_UNSPECIFIED, NUMERICAL, FRESHNESS }
		enum InterpolationTypeEnum extends Enum[InterpolationTypeEnum] { case INTERPOLATION_TYPE_UNSPECIFIED, LINEAR }
	}
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestBoostSpecConditionBoostSpecBoostControlSpec(
	  /** The name of the field whose value will be used to determine the boost amount. */
		fieldName: Option[String] = None,
	  /** The attribute type to be used to determine the boost amount. The attribute value can be derived from the field value of the specified field_name. In the case of numerical it is straightforward i.e. attribute_value = numerical_field_value. In the case of freshness however, attribute_value = (time.now() - datetime_field_value). */
		attributeType: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestBoostSpecConditionBoostSpecBoostControlSpec.AttributeTypeEnum] = None,
	  /** The interpolation type to be applied to connect the control points listed below. */
		interpolationType: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestBoostSpecConditionBoostSpecBoostControlSpec.InterpolationTypeEnum] = None,
	  /** The control points used to define the curve. The monotonic function (defined through the interpolation_type above) passes through the control points listed here. */
		controlPoints: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestBoostSpecConditionBoostSpecBoostControlSpecControlPoint]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestBoostSpecConditionBoostSpecBoostControlSpecControlPoint(
	  /** Can be one of: 1. The numerical field value. 2. The duration spec for freshness: The value must be formatted as an XSD `dayTimeDuration` value (a restricted subset of an ISO 8601 duration value). The pattern for this is: `nDnM]`. */
		attributeValue: Option[String] = None,
	  /** The value between -1 to 1 by which to boost the score if the attribute_value evaluates to the value specified above. */
		boostAmount: Option[BigDecimal] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaSearchRequestQueryExpansionSpec {
		enum ConditionEnum extends Enum[ConditionEnum] { case CONDITION_UNSPECIFIED, DISABLED, AUTO }
	}
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestQueryExpansionSpec(
	  /** The condition under which query expansion should occur. Default to Condition.DISABLED. */
		condition: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestQueryExpansionSpec.ConditionEnum] = None,
	  /** Whether to pin unexpanded results. If this field is set to true, unexpanded products are always at the top of the search results, followed by the expanded results. */
		pinUnexpandedResults: Option[Boolean] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaSearchRequestSpellCorrectionSpec {
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, SUGGESTION_ONLY, AUTO }
	}
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestSpellCorrectionSpec(
	  /** The mode under which spell correction replaces the original search query. Defaults to Mode.AUTO. */
		mode: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestSpellCorrectionSpec.ModeEnum] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaSearchRequestContentSearchSpec {
		enum SearchResultModeEnum extends Enum[SearchResultModeEnum] { case SEARCH_RESULT_MODE_UNSPECIFIED, DOCUMENTS, CHUNKS }
	}
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestContentSearchSpec(
	  /** If `snippetSpec` is not specified, snippets are not included in the search response. */
		snippetSpec: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestContentSearchSpecSnippetSpec] = None,
	  /** If `summarySpec` is not specified, summaries are not included in the search response. */
		summarySpec: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestContentSearchSpecSummarySpec] = None,
	  /** If there is no extractive_content_spec provided, there will be no extractive answer in the search response. */
		extractiveContentSpec: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestContentSearchSpecExtractiveContentSpec] = None,
	  /** Specifies the search result mode. If unspecified, the search result mode defaults to `DOCUMENTS`. */
		searchResultMode: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestContentSearchSpec.SearchResultModeEnum] = None,
	  /** Specifies the chunk spec to be returned from the search response. Only available if the SearchRequest.ContentSearchSpec.search_result_mode is set to CHUNKS */
		chunkSpec: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestContentSearchSpecChunkSpec] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestContentSearchSpecSnippetSpec(
	  /** [DEPRECATED] This field is deprecated. To control snippet return, use `return_snippet` field. For backwards compatibility, we will return snippet if max_snippet_count > 0. */
		maxSnippetCount: Option[Int] = None,
	  /** [DEPRECATED] This field is deprecated and will have no affect on the snippet. */
		referenceOnly: Option[Boolean] = None,
	  /** If `true`, then return snippet. If no snippet can be generated, we return "No snippet is available for this page." A `snippet_status` with `SUCCESS` or `NO_SNIPPET_AVAILABLE` will also be returned. */
		returnSnippet: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestContentSearchSpecSummarySpec(
	  /** The number of top results to generate the summary from. If the number of results returned is less than `summaryResultCount`, the summary is generated from all of the results. At most 10 results for documents mode, or 50 for chunks mode, can be used to generate a summary. The chunks mode is used when SearchRequest.ContentSearchSpec.search_result_mode is set to CHUNKS. */
		summaryResultCount: Option[Int] = None,
	  /** Specifies whether to include citations in the summary. The default value is `false`. When this field is set to `true`, summaries include in-line citation numbers. Example summary including citations: BigQuery is Google Cloud's fully managed and completely serverless enterprise data warehouse [1]. BigQuery supports all data types, works across clouds, and has built-in machine learning and business intelligence, all within a unified platform [2, 3]. The citation numbers refer to the returned search results and are 1-indexed. For example, [1] means that the sentence is attributed to the first search result. [2, 3] means that the sentence is attributed to both the second and third search results. */
		includeCitations: Option[Boolean] = None,
	  /** Specifies whether to filter out adversarial queries. The default value is `false`. Google employs search-query classification to detect adversarial queries. No summary is returned if the search query is classified as an adversarial query. For example, a user might ask a question regarding negative comments about the company or submit a query designed to generate unsafe, policy-violating output. If this field is set to `true`, we skip generating summaries for adversarial queries and return fallback messages instead. */
		ignoreAdversarialQuery: Option[Boolean] = None,
	  /** Specifies whether to filter out queries that are not summary-seeking. The default value is `false`. Google employs search-query classification to detect summary-seeking queries. No summary is returned if the search query is classified as a non-summary seeking query. For example, `why is the sky blue` and `Who is the best soccer player in the world?` are summary-seeking queries, but `SFO airport` and `world cup 2026` are not. They are most likely navigational queries. If this field is set to `true`, we skip generating summaries for non-summary seeking queries and return fallback messages instead. */
		ignoreNonSummarySeekingQuery: Option[Boolean] = None,
	  /** Specifies whether to filter out queries that have low relevance. The default value is `false`. If this field is set to `false`, all search results are used regardless of relevance to generate answers. If set to `true`, only queries with high relevance search results will generate answers. */
		ignoreLowRelevantContent: Option[Boolean] = None,
	  /** Optional. Specifies whether to filter out jail-breaking queries. The default value is `false`. Google employs search-query classification to detect jail-breaking queries. No summary is returned if the search query is classified as a jail-breaking query. A user might add instructions to the query to change the tone, style, language, content of the answer, or ask the model to act as a different entity, e.g. "Reply in the tone of a competing company's CEO". If this field is set to `true`, we skip generating summaries for jail-breaking queries and return fallback messages instead. */
		ignoreJailBreakingQuery: Option[Boolean] = None,
	  /** If specified, the spec will be used to modify the prompt provided to the LLM. */
		modelPromptSpec: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestContentSearchSpecSummarySpecModelPromptSpec] = None,
	  /** Language code for Summary. Use language tags defined by [BCP47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt). Note: This is an experimental feature. */
		languageCode: Option[String] = None,
	  /** If specified, the spec will be used to modify the model specification provided to the LLM. */
		modelSpec: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestContentSearchSpecSummarySpecModelSpec] = None,
	  /** If true, answer will be generated from most relevant chunks from top search results. This feature will improve summary quality. Note that with this feature enabled, not all top search results will be referenced and included in the reference list, so the citation source index only points to the search results listed in the reference list. */
		useSemanticChunks: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestContentSearchSpecSummarySpecModelPromptSpec(
	  /** Text at the beginning of the prompt that instructs the assistant. Examples are available in the user guide. */
		preamble: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestContentSearchSpecSummarySpecModelSpec(
	  /** The model version used to generate the summary. Supported values are: &#42; `stable`: string. Default value when no value is specified. Uses a generally available, fine-tuned model. For more information, see [Answer generation model versions and lifecycle](https://cloud.google.com/generative-ai-app-builder/docs/answer-generation-models). &#42; `preview`: string. (Public preview) Uses a preview model. For more information, see [Answer generation model versions and lifecycle](https://cloud.google.com/generative-ai-app-builder/docs/answer-generation-models). */
		version: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestContentSearchSpecExtractiveContentSpec(
	  /** The maximum number of extractive answers returned in each search result. An extractive answer is a verbatim answer extracted from the original document, which provides a precise and contextually relevant answer to the search query. If the number of matching answers is less than the `max_extractive_answer_count`, return all of the answers. Otherwise, return the `max_extractive_answer_count`. At most five answers are returned for each SearchResult. */
		maxExtractiveAnswerCount: Option[Int] = None,
	  /** The max number of extractive segments returned in each search result. Only applied if the DataStore is set to DataStore.ContentConfig.CONTENT_REQUIRED or DataStore.solution_types is SOLUTION_TYPE_CHAT. An extractive segment is a text segment extracted from the original document that is relevant to the search query, and, in general, more verbose than an extractive answer. The segment could then be used as input for LLMs to generate summaries and answers. If the number of matching segments is less than `max_extractive_segment_count`, return all of the segments. Otherwise, return the `max_extractive_segment_count`. */
		maxExtractiveSegmentCount: Option[Int] = None,
	  /** Specifies whether to return the confidence score from the extractive segments in each search result. This feature is available only for new or allowlisted data stores. To allowlist your data store, contact your Customer Engineer. The default value is `false`. */
		returnExtractiveSegmentScore: Option[Boolean] = None,
	  /** Specifies whether to also include the adjacent from each selected segments. Return at most `num_previous_segments` segments before each selected segments. */
		numPreviousSegments: Option[Int] = None,
	  /** Return at most `num_next_segments` segments after each selected segments. */
		numNextSegments: Option[Int] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestContentSearchSpecChunkSpec(
	  /** The number of previous chunks to be returned of the current chunk. The maximum allowed value is 3. If not specified, no previous chunks will be returned. */
		numPreviousChunks: Option[Int] = None,
	  /** The number of next chunks to be returned of the current chunk. The maximum allowed value is 3. If not specified, no next chunks will be returned. */
		numNextChunks: Option[Int] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestEmbeddingSpec(
	  /** The embedding vector used for retrieval. Limit to 1. */
		embeddingVectors: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestEmbeddingSpecEmbeddingVector]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestEmbeddingSpecEmbeddingVector(
	  /** Embedding field path in schema. */
		fieldPath: Option[String] = None,
	  /** Query embedding vector. */
		vector: Option[List[BigDecimal]] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaSearchRequestNaturalLanguageQueryUnderstandingSpec {
		enum FilterExtractionConditionEnum extends Enum[FilterExtractionConditionEnum] { case CONDITION_UNSPECIFIED, DISABLED, ENABLED }
	}
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestNaturalLanguageQueryUnderstandingSpec(
	  /** The condition under which filter extraction should occur. Default to Condition.DISABLED. */
		filterExtractionCondition: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestNaturalLanguageQueryUnderstandingSpec.FilterExtractionConditionEnum] = None,
	  /** Field names used for location-based filtering, where geolocation filters are detected in natural language search queries. Only valid when the FilterExtractionCondition is set to `ENABLED`. If this field is set, it overrides the field names set in ServingConfig.geo_search_query_detection_field_names. */
		geoSearchQueryDetectionFieldNames: Option[List[String]] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaSearchRequestSearchAsYouTypeSpec {
		enum ConditionEnum extends Enum[ConditionEnum] { case CONDITION_UNSPECIFIED, DISABLED, ENABLED }
	}
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestSearchAsYouTypeSpec(
	  /** The condition under which search as you type should occur. Default to Condition.DISABLED. */
		condition: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestSearchAsYouTypeSpec.ConditionEnum] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaCustomFineTuningSpec(
	  /** Whether or not to enable and include custom fine tuned search adaptor model. */
		enableSearchAdaptor: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestSessionSpec(
	  /** If set, the search result gets stored to the "turn" specified by this query ID. Example: Let's say the session looks like this: session { name: ".../sessions/xxx" turns { query { text: "What is foo?" query_id: ".../questions/yyy" } answer: "Foo is ..." } turns { query { text: "How about bar then?" query_id: ".../questions/zzz" } } } The user can call /search API with a request like this: session: ".../sessions/xxx" session_spec { query_id: ".../questions/zzz" } Then, the API stores the search result, associated with the last turn. The stored search result can be used by a subsequent /answer API call (with the session ID and the query ID specified). Also, it is possible to call /search and /answer in parallel with the same session ID & query ID. */
		queryId: Option[String] = None,
	  /** The number of top search results to persist. The persisted search results can be used for the subsequent /answer api call. This field is simliar to the `summary_result_count` field in SearchRequest.ContentSearchSpec.SummarySpec.summary_result_count. At most 10 results for documents mode, or 50 for chunks mode. */
		searchResultPersistenceCount: Option[Int] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaSearchRequestPersonalizationSpec {
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, AUTO, DISABLED }
	}
	case class GoogleCloudDiscoveryengineV1alphaSearchRequestPersonalizationSpec(
	  /** The personalization mode of the search request. Defaults to Mode.AUTO. */
		mode: Option[Schema.GoogleCloudDiscoveryengineV1alphaSearchRequestPersonalizationSpec.ModeEnum] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaEvaluationEvaluationSpecQuerySetSpec(
	  /** Required. The full resource name of the SampleQuerySet used for the evaluation, in the format of `projects/{project}/locations/{location}/sampleQuerySets/{sampleQuerySet}`. */
		sampleQuerySet: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaQualityMetrics(
	  /** Recall per document, at various top-k cutoff levels. Recall is the fraction of relevant documents retrieved out of all relevant documents. Example (top-5): &#42; For a single SampleQuery, If 3 out of 5 relevant documents are retrieved in the top-5, recall@5 = 3/5 = 0.6 */
		docRecall: Option[Schema.GoogleCloudDiscoveryengineV1alphaQualityMetricsTopkMetrics] = None,
	  /** Precision per document, at various top-k cutoff levels. Precision is the fraction of retrieved documents that are relevant. Example (top-5): &#42; For a single SampleQuery, If 4 out of 5 retrieved documents in the top-5 are relevant, precision@5 = 4/5 = 0.8 */
		docPrecision: Option[Schema.GoogleCloudDiscoveryengineV1alphaQualityMetricsTopkMetrics] = None,
	  /** Normalized discounted cumulative gain (NDCG) per document, at various top-k cutoff levels. NDCG measures the ranking quality, giving higher relevance to top results. Example (top-3): Suppose SampleQuery with three retrieved documents (D1, D2, D3) and binary relevance judgements (1 for relevant, 0 for not relevant): Retrieved: [D3 (0), D1 (1), D2 (1)] Ideal: [D1 (1), D2 (1), D3 (0)] Calculate NDCG@3 for each SampleQuery: &#42; DCG@3: 0/log2(1+1) + 1/log2(2+1) + 1/log2(3+1) = 1.13 &#42; Ideal DCG@3: 1/log2(1+1) + 1/log2(2+1) + 0/log2(3+1) = 1.63 &#42; NDCG@3: 1.13/1.63 = 0.693 */
		docNdcg: Option[Schema.GoogleCloudDiscoveryengineV1alphaQualityMetricsTopkMetrics] = None,
	  /** Recall per page, at various top-k cutoff levels. Recall is the fraction of relevant pages retrieved out of all relevant pages. Example (top-5): &#42; For a single SampleQuery, if 3 out of 5 relevant pages are retrieved in the top-5, recall@5 = 3/5 = 0.6 */
		pageRecall: Option[Schema.GoogleCloudDiscoveryengineV1alphaQualityMetricsTopkMetrics] = None,
	  /** Normalized discounted cumulative gain (NDCG) per page, at various top-k cutoff levels. NDCG measures the ranking quality, giving higher relevance to top results. Example (top-3): Suppose SampleQuery with three retrieved pages (P1, P2, P3) and binary relevance judgements (1 for relevant, 0 for not relevant): Retrieved: [P3 (0), P1 (1), P2 (1)] Ideal: [P1 (1), P2 (1), P3 (0)] Calculate NDCG@3 for SampleQuery: &#42; DCG@3: 0/log2(1+1) + 1/log2(2+1) + 1/log2(3+1) = 1.13 &#42; Ideal DCG@3: 1/log2(1+1) + 1/log2(2+1) + 0/log2(3+1) = 1.63 &#42; NDCG@3: 1.13/1.63 = 0.693 */
		pageNdcg: Option[Schema.GoogleCloudDiscoveryengineV1alphaQualityMetricsTopkMetrics] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaQualityMetricsTopkMetrics(
	  /** The top-1 value. */
		top1: Option[BigDecimal] = None,
	  /** The top-3 value. */
		top3: Option[BigDecimal] = None,
	  /** The top-5 value. */
		top5: Option[BigDecimal] = None,
	  /** The top-10 value. */
		top10: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaFetchSitemapsResponse(
	  /** List of Sitemaps fetched. */
		sitemapsMetadata: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaFetchSitemapsResponseSitemapMetadata]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaFetchSitemapsResponseSitemapMetadata(
	  /** The Sitemap. */
		sitemap: Option[Schema.GoogleCloudDiscoveryengineV1alphaSitemap] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSitemap(
	  /** Public URI for the sitemap, e.g. `www.example.com/sitemap.xml`. */
		uri: Option[String] = None,
	  /** Output only. The fully qualified resource name of the sitemap. `projects/&#42;/locations/&#42;/collections/&#42;/dataStores/&#42;/siteSearchEngine/sitemaps/&#42;` The `sitemap_id` suffix is system-generated. */
		name: Option[String] = None,
	  /** Output only. The sitemap's creation time. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaGetUriPatternDocumentDataResponse(
	  /** Document data keyed by URI pattern. For example: document_data_map = { "www.url1.com/&#42;": { "Categories": ["category1", "category2"] }, "www.url2.com/&#42;": { "Categories": ["category3"] } } */
		documentDataMap: Option[Map[String, Map[String, JsValue]]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaImportCompletionSuggestionsMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of CompletionSuggestions successfully imported. */
		successCount: Option[String] = None,
	  /** Count of CompletionSuggestions that failed to be imported. */
		failureCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaImportCompletionSuggestionsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** The desired location of errors incurred during the Import. */
		errorConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaImportErrorConfig] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaImportErrorConfig(
	  /** Cloud Storage prefix for import errors. This must be an empty, existing Cloud Storage directory. Import errors are written to sharded files in this directory, one per line, as a JSON-encoded `google.rpc.Status` message. */
		gcsPrefix: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaImportDocumentsMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of entries that were processed successfully. */
		successCount: Option[String] = None,
	  /** Count of entries that encountered errors while processing. */
		failureCount: Option[String] = None,
	  /** Total count of entries that were processed. */
		totalCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaImportDocumentsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Echoes the destination for the complete errors in the request if set. */
		errorConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaImportErrorConfig] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaImportSampleQueriesMetadata(
	  /** ImportSampleQueries operation create time. */
		createTime: Option[String] = None,
	  /** ImportSampleQueries operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of SampleQuerys successfully imported. */
		successCount: Option[String] = None,
	  /** Count of SampleQuerys that failed to be imported. */
		failureCount: Option[String] = None,
	  /** Total count of SampleQuerys that were processed. */
		totalCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaImportSampleQueriesResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** The desired location of errors incurred during the Import. */
		errorConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaImportErrorConfig] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaImportSuggestionDenyListEntriesMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaImportSuggestionDenyListEntriesResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Count of deny list entries successfully imported. */
		importedEntriesCount: Option[String] = None,
	  /** Count of deny list entries that failed to be imported. */
		failedEntriesCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaImportUserEventsMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of entries that were processed successfully. */
		successCount: Option[String] = None,
	  /** Count of entries that encountered errors while processing. */
		failureCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaImportUserEventsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Echoes the destination for the complete errors if this field was set in the request. */
		errorConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaImportErrorConfig] = None,
	  /** Count of user events imported with complete existing Documents. */
		joinedEventsCount: Option[String] = None,
	  /** Count of user events imported, but with Document information not found in the existing Branch. */
		unjoinedEventsCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaListCustomModelsResponse(
	  /** List of custom tuning models. */
		models: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaCustomTuningModel]] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaCustomTuningModel {
		enum ModelStateEnum extends Enum[ModelStateEnum] { case MODEL_STATE_UNSPECIFIED, TRAINING_PAUSED, TRAINING, TRAINING_COMPLETE, READY_FOR_SERVING, TRAINING_FAILED, NO_IMPROVEMENT, INPUT_VALIDATION_FAILED }
	}
	case class GoogleCloudDiscoveryengineV1alphaCustomTuningModel(
	  /** Required. The fully qualified resource name of the model. Format: `projects/{project}/locations/{location}/collections/{collection}/dataStores/{data_store}/customTuningModels/{custom_tuning_model}`. Model must be an alpha-numerical string with limit of 40 characters. */
		name: Option[String] = None,
	  /** The display name of the model. */
		displayName: Option[String] = None,
	  /** The version of the model. */
		modelVersion: Option[String] = None,
	  /** The state that the model is in (e.g.`TRAINING` or `TRAINING_FAILED`). */
		modelState: Option[Schema.GoogleCloudDiscoveryengineV1alphaCustomTuningModel.ModelStateEnum] = None,
	  /** Deprecated: Timestamp the Model was created at. */
		createTime: Option[String] = None,
	  /** Timestamp the model training was initiated. */
		trainingStartTime: Option[String] = None,
	  /** The metrics of the trained model. */
		metrics: Option[Map[String, BigDecimal]] = None,
	  /** Currently this is only populated if the model state is `INPUT_VALIDATION_FAILED`. */
		errorMessage: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaObtainCrawlRateResponse {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, SUCCEEDED, FAILED }
	}
	case class GoogleCloudDiscoveryengineV1alphaObtainCrawlRateResponse(
	  /** The historical organic crawl rate timeseries data, used for monitoring. */
		organicCrawlRateTimeSeries: Option[Schema.GoogleCloudDiscoveryengineV1alphaOrganicCrawlRateTimeSeries] = None,
	  /** The historical dedicated crawl rate timeseries data, used for monitoring. */
		dedicatedCrawlRateTimeSeries: Option[Schema.GoogleCloudDiscoveryengineV1alphaDedicatedCrawlRateTimeSeries] = None,
	  /** Output only. The state of the response. */
		state: Option[Schema.GoogleCloudDiscoveryengineV1alphaObtainCrawlRateResponse.StateEnum] = None,
	  /** Errors from service when handling the request. */
		error: Option[Schema.GoogleRpcStatus] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaOrganicCrawlRateTimeSeries(
	  /** Google's organic crawl rate time series, which is the sum of all googlebots' crawl rate. Please refer to https://developers.google.com/search/docs/crawling-indexing/overview-google-crawlers for more details about googlebots. */
		googleOrganicCrawlRate: Option[Schema.GoogleCloudDiscoveryengineV1alphaCrawlRateTimeSeries] = None,
	  /** Vertex AI's organic crawl rate time series, which is the crawl rate of Google-CloudVertexBot when dedicate crawl is not set. Please refer to https://developers.google.com/search/docs/crawling-indexing/google-common-crawlers#google-cloudvertexbot for more details about Google-CloudVertexBot. */
		vertexAiOrganicCrawlRate: Option[Schema.GoogleCloudDiscoveryengineV1alphaCrawlRateTimeSeries] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaCrawlRateTimeSeries(
	  /** The QPS of the crawl rate. */
		qpsTimeSeries: Option[Schema.GoogleMonitoringV3TimeSeries] = None
	)
	
	object GoogleMonitoringV3TimeSeries {
		enum MetricKindEnum extends Enum[MetricKindEnum] { case METRIC_KIND_UNSPECIFIED, GAUGE, DELTA, CUMULATIVE }
		enum ValueTypeEnum extends Enum[ValueTypeEnum] { case VALUE_TYPE_UNSPECIFIED, BOOL, INT64, DOUBLE, STRING, DISTRIBUTION, MONEY }
	}
	case class GoogleMonitoringV3TimeSeries(
	  /** The associated metric. A fully-specified metric used to identify the time series. */
		metric: Option[Schema.GoogleApiMetric] = None,
	  /** The associated monitored resource. Custom metrics can use only certain monitored resource types in their time series data. For more information, see [Monitored resources for custom metrics](https://cloud.google.com/monitoring/custom-metrics/creating-metrics#custom-metric-resources). */
		resource: Option[Schema.GoogleApiMonitoredResource] = None,
	  /** Output only. The associated monitored resource metadata. When reading a time series, this field will include metadata labels that are explicitly named in the reduction. When creating a time series, this field is ignored. */
		metadata: Option[Schema.GoogleApiMonitoredResourceMetadata] = None,
	  /** The metric kind of the time series. When listing time series, this metric kind might be different from the metric kind of the associated metric if this time series is an alignment or reduction of other time series. When creating a time series, this field is optional. If present, it must be the same as the metric kind of the associated metric. If the associated metric's descriptor must be auto-created, then this field specifies the metric kind of the new descriptor and must be either `GAUGE` (the default) or `CUMULATIVE`. */
		metricKind: Option[Schema.GoogleMonitoringV3TimeSeries.MetricKindEnum] = None,
	  /** The value type of the time series. When listing time series, this value type might be different from the value type of the associated metric if this time series is an alignment or reduction of other time series. When creating a time series, this field is optional. If present, it must be the same as the type of the data in the `points` field. */
		valueType: Option[Schema.GoogleMonitoringV3TimeSeries.ValueTypeEnum] = None,
	  /** The data points of this time series. When listing time series, points are returned in reverse time order. When creating a time series, this field must contain exactly one point and the point's type must be the same as the value type of the associated metric. If the associated metric's descriptor must be auto-created, then the value type of the descriptor is determined by the point's type, which must be `BOOL`, `INT64`, `DOUBLE`, or `DISTRIBUTION`. */
		points: Option[List[Schema.GoogleMonitoringV3Point]] = None,
	  /** The units in which the metric value is reported. It is only applicable if the `value_type` is `INT64`, `DOUBLE`, or `DISTRIBUTION`. The `unit` defines the representation of the stored metric values. This field can only be changed through CreateTimeSeries when it is empty. */
		unit: Option[String] = None,
	  /** Input only. A detailed description of the time series that will be associated with the google.api.MetricDescriptor for the metric. Once set, this field cannot be changed through CreateTimeSeries. */
		description: Option[String] = None
	)
	
	case class GoogleApiMetric(
	  /** An existing metric type, see google.api.MetricDescriptor. For example, `custom.googleapis.com/invoice/paid/amount`. */
		`type`: Option[String] = None,
	  /** The set of label values that uniquely identify this metric. All labels listed in the `MetricDescriptor` must be assigned values. */
		labels: Option[Map[String, String]] = None
	)
	
	case class GoogleApiMonitoredResource(
	  /** Required. The monitored resource type. This field must match the `type` field of a MonitoredResourceDescriptor object. For example, the type of a Compute Engine VM instance is `gce_instance`. Some descriptors include the service name in the type; for example, the type of a Datastream stream is `datastream.googleapis.com/Stream`. */
		`type`: Option[String] = None,
	  /** Required. Values for all of the labels listed in the associated monitored resource descriptor. For example, Compute Engine VM instances use the labels `"project_id"`, `"instance_id"`, and `"zone"`. */
		labels: Option[Map[String, String]] = None
	)
	
	case class GoogleApiMonitoredResourceMetadata(
	  /** Output only. Values for predefined system metadata labels. System labels are a kind of metadata extracted by Google, including "machine_image", "vpc", "subnet_id", "security_group", "name", etc. System label values can be only strings, Boolean values, or a list of strings. For example: { "name": "my-test-instance", "security_group": ["a", "b", "c"], "spot_instance": false } */
		systemLabels: Option[Map[String, JsValue]] = None,
	  /** Output only. A map of user-defined metadata labels. */
		userLabels: Option[Map[String, String]] = None
	)
	
	case class GoogleMonitoringV3Point(
	  /** The time interval to which the data point applies. For `GAUGE` metrics, the start time is optional, but if it is supplied, it must equal the end time. For `DELTA` metrics, the start and end time should specify a non-zero interval, with subsequent points specifying contiguous and non-overlapping intervals. For `CUMULATIVE` metrics, the start and end time should specify a non-zero interval, with subsequent points specifying the same start time and increasing end times, until an event resets the cumulative value to zero and sets a new start time for the following points. */
		interval: Option[Schema.GoogleMonitoringV3TimeInterval] = None,
	  /** The value of the data point. */
		value: Option[Schema.GoogleMonitoringV3TypedValue] = None
	)
	
	case class GoogleMonitoringV3TimeInterval(
	  /** Required. The end of the time interval. */
		endTime: Option[String] = None,
	  /** Optional. The beginning of the time interval. The default value for the start time is the end time. The start time must not be later than the end time. */
		startTime: Option[String] = None
	)
	
	case class GoogleMonitoringV3TypedValue(
	  /** A Boolean value: `true` or `false`. */
		boolValue: Option[Boolean] = None,
	  /** A 64-bit integer. Its range is approximately ±9.2x1018. */
		int64Value: Option[String] = None,
	  /** A 64-bit double-precision floating-point number. Its magnitude is approximately ±10±300 and it has 16 significant digits of precision. */
		doubleValue: Option[BigDecimal] = None,
	  /** A variable-length string value. */
		stringValue: Option[String] = None,
	  /** A distribution value. */
		distributionValue: Option[Schema.GoogleApiDistribution] = None
	)
	
	case class GoogleApiDistribution(
	  /** The number of values in the population. Must be non-negative. This value must equal the sum of the values in `bucket_counts` if a histogram is provided. */
		count: Option[String] = None,
	  /** The arithmetic mean of the values in the population. If `count` is zero then this field must be zero. */
		mean: Option[BigDecimal] = None,
	  /** The sum of squared deviations from the mean of the values in the population. For values x_i this is: Sum[i=1..n]((x_i - mean)^2) Knuth, "The Art of Computer Programming", Vol. 2, page 232, 3rd edition describes Welford's method for accumulating this sum in one pass. If `count` is zero then this field must be zero. */
		sumOfSquaredDeviation: Option[BigDecimal] = None,
	  /** If specified, contains the range of the population values. The field must not be present if the `count` is zero. */
		range: Option[Schema.GoogleApiDistributionRange] = None,
	  /** Defines the histogram bucket boundaries. If the distribution does not contain a histogram, then omit this field. */
		bucketOptions: Option[Schema.GoogleApiDistributionBucketOptions] = None,
	  /** The number of values in each bucket of the histogram, as described in `bucket_options`. If the distribution does not have a histogram, then omit this field. If there is a histogram, then the sum of the values in `bucket_counts` must equal the value in the `count` field of the distribution. If present, `bucket_counts` should contain N values, where N is the number of buckets specified in `bucket_options`. If you supply fewer than N values, the remaining values are assumed to be 0. The order of the values in `bucket_counts` follows the bucket numbering schemes described for the three bucket types. The first value must be the count for the underflow bucket (number 0). The next N-2 values are the counts for the finite buckets (number 1 through N-2). The N'th value in `bucket_counts` is the count for the overflow bucket (number N-1). */
		bucketCounts: Option[List[String]] = None,
	  /** Must be in increasing order of `value` field. */
		exemplars: Option[List[Schema.GoogleApiDistributionExemplar]] = None
	)
	
	case class GoogleApiDistributionRange(
	  /** The minimum of the population values. */
		min: Option[BigDecimal] = None,
	  /** The maximum of the population values. */
		max: Option[BigDecimal] = None
	)
	
	case class GoogleApiDistributionBucketOptions(
	  /** The linear bucket. */
		linearBuckets: Option[Schema.GoogleApiDistributionBucketOptionsLinear] = None,
	  /** The exponential buckets. */
		exponentialBuckets: Option[Schema.GoogleApiDistributionBucketOptionsExponential] = None,
	  /** The explicit buckets. */
		explicitBuckets: Option[Schema.GoogleApiDistributionBucketOptionsExplicit] = None
	)
	
	case class GoogleApiDistributionBucketOptionsLinear(
	  /** Must be greater than 0. */
		numFiniteBuckets: Option[Int] = None,
	  /** Must be greater than 0. */
		width: Option[BigDecimal] = None,
	  /** Lower bound of the first bucket. */
		offset: Option[BigDecimal] = None
	)
	
	case class GoogleApiDistributionBucketOptionsExponential(
	  /** Must be greater than 0. */
		numFiniteBuckets: Option[Int] = None,
	  /** Must be greater than 1. */
		growthFactor: Option[BigDecimal] = None,
	  /** Must be greater than 0. */
		scale: Option[BigDecimal] = None
	)
	
	case class GoogleApiDistributionBucketOptionsExplicit(
	  /** The values must be monotonically increasing. */
		bounds: Option[List[BigDecimal]] = None
	)
	
	case class GoogleApiDistributionExemplar(
	  /** Value of the exemplar point. This value determines to which bucket the exemplar belongs. */
		value: Option[BigDecimal] = None,
	  /** The observation (sampling) time of the above value. */
		timestamp: Option[String] = None,
	  /** Contextual information about the example value. Examples are: Trace: type.googleapis.com/google.monitoring.v3.SpanContext Literal string: type.googleapis.com/google.protobuf.StringValue Labels dropped during aggregation: type.googleapis.com/google.monitoring.v3.DroppedLabels There may be only a single attachment of any given message type in a single exemplar, and this is enforced by the system. */
		attachments: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaDedicatedCrawlRateTimeSeries(
	  /** Vertex AI's dedicated crawl rate time series of user triggered crawl, which is the crawl rate of Google-CloudVertexBot when dedicate crawl is set, and user triggered crawl rate is for deterministic use cases like crawling urls or sitemaps specified by users. */
		userTriggeredCrawlRate: Option[Schema.GoogleCloudDiscoveryengineV1alphaCrawlRateTimeSeries] = None,
	  /** Vertex AI's dedicated crawl rate time series of auto-refresh, which is the crawl rate of Google-CloudVertexBot when dedicate crawl is set, and the crawl rate is for best effort use cases like refreshing urls periodically. */
		autoRefreshCrawlRate: Option[Schema.GoogleCloudDiscoveryengineV1alphaCrawlRateTimeSeries] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaProject(
	  /** Output only. Full resource name of the project, for example `projects/{project}`. Note that when making requests, project number and project id are both acceptable, but the server will always respond in project number. */
		name: Option[String] = None,
	  /** Output only. The timestamp when this project is created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when this project is successfully provisioned. Empty value means this project is still provisioning and is not ready for use. */
		provisionCompletionTime: Option[String] = None,
	  /** Output only. A map of terms of services. The key is the `id` of ServiceTerms. */
		serviceTermsMap: Option[Map[String, Schema.GoogleCloudDiscoveryengineV1alphaProjectServiceTerms]] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaProjectServiceTerms {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, TERMS_ACCEPTED, TERMS_PENDING, TERMS_DECLINED }
	}
	case class GoogleCloudDiscoveryengineV1alphaProjectServiceTerms(
	  /** The unique identifier of this terms of service. Available terms: &#42; `GA_DATA_USE_TERMS`: [Terms for data use](https://cloud.google.com/retail/data-use-terms). When using this as `id`, the acceptable version to provide is `2022-11-23`. */
		id: Option[String] = None,
	  /** The version string of the terms of service. For acceptable values, see the comments for id above. */
		version: Option[String] = None,
	  /** Whether the project has accepted/rejected the service terms or it is still pending. */
		state: Option[Schema.GoogleCloudDiscoveryengineV1alphaProjectServiceTerms.StateEnum] = None,
	  /** The last time when the project agreed to the terms of service. */
		acceptTime: Option[String] = None,
	  /** The last time when the project declined or revoked the agreement to terms of service. */
		declineTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaProvisionProjectMetadata(
	
	)
	
	case class GoogleCloudDiscoveryengineV1alphaPurgeCompletionSuggestionsMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaPurgeCompletionSuggestionsResponse(
	  /** Whether the completion suggestions were successfully purged. */
		purgeSucceeded: Option[Boolean] = None,
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaPurgeDocumentsMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of entries that were deleted successfully. */
		successCount: Option[String] = None,
	  /** Count of entries that encountered errors while processing. */
		failureCount: Option[String] = None,
	  /** Count of entries that were ignored as entries were not found. */
		ignoredCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaPurgeDocumentsResponse(
	  /** The total count of documents purged as a result of the operation. */
		purgeCount: Option[String] = None,
	  /** A sample of document names that will be deleted. Only populated if `force` is set to false. A max of 100 names will be returned and the names are chosen at random. */
		purgeSample: Option[List[String]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaPurgeSuggestionDenyListEntriesMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaPurgeSuggestionDenyListEntriesResponse(
	  /** Number of suggestion deny list entries purged. */
		purgeCount: Option[String] = None,
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaPurgeUserEventsMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of entries that were deleted successfully. */
		successCount: Option[String] = None,
	  /** Count of entries that encountered errors while processing. */
		failureCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaPurgeUserEventsResponse(
	  /** The total count of events purged as a result of the operation. */
		purgeCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaRecrawlUrisMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Unique URIs in the request that have invalid format. Sample limited to 1000. */
		invalidUris: Option[List[String]] = None,
	  /** Total number of unique URIs in the request that have invalid format. */
		invalidUrisCount: Option[Int] = None,
	  /** Unique URIs in the request that don't match any TargetSite in the DataStore, only match TargetSites that haven't been fully indexed, or match a TargetSite with type EXCLUDE. Sample limited to 1000. */
		urisNotMatchingTargetSites: Option[List[String]] = None,
	  /** Total number of URIs that don't match any TargetSites. */
		urisNotMatchingTargetSitesCount: Option[Int] = None,
	  /** Total number of unique URIs in the request that are not in invalid_uris. */
		validUrisCount: Option[Int] = None,
	  /** Total number of URIs that have been crawled so far. */
		successCount: Option[Int] = None,
	  /** Total number of URIs that have yet to be crawled. */
		pendingCount: Option[Int] = None,
	  /** Total number of URIs that were rejected due to insufficient indexing resources. */
		quotaExceededCount: Option[Int] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaRecrawlUrisResponse(
	  /** Details for a sample of up to 10 `failed_uris`. */
		failureSamples: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaRecrawlUrisResponseFailureInfo]] = None,
	  /** URIs that were not crawled before the LRO terminated. */
		failedUris: Option[List[String]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaRecrawlUrisResponseFailureInfo(
	  /** URI that failed to be crawled. */
		uri: Option[String] = None,
	  /** List of failure reasons by corpus type (e.g. desktop, mobile). */
		failureReasons: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaRecrawlUrisResponseFailureInfoFailureReason]] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaRecrawlUrisResponseFailureInfoFailureReason {
		enum CorpusTypeEnum extends Enum[CorpusTypeEnum] { case CORPUS_TYPE_UNSPECIFIED, DESKTOP, MOBILE }
	}
	case class GoogleCloudDiscoveryengineV1alphaRecrawlUrisResponseFailureInfoFailureReason(
	  /** DESKTOP, MOBILE, or CORPUS_TYPE_UNSPECIFIED. */
		corpusType: Option[Schema.GoogleCloudDiscoveryengineV1alphaRecrawlUrisResponseFailureInfoFailureReason.CorpusTypeEnum] = None,
	  /** Reason why the URI was not crawled. */
		errorMessage: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1alphaSession {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, IN_PROGRESS }
	}
	case class GoogleCloudDiscoveryengineV1alphaSession(
	  /** Immutable. Fully qualified name `projects/{project}/locations/global/collections/{collection}/engines/{engine}/sessions/&#42;` */
		name: Option[String] = None,
	  /** The state of the session. */
		state: Option[Schema.GoogleCloudDiscoveryengineV1alphaSession.StateEnum] = None,
	  /** A unique identifier for tracking users. */
		userPseudoId: Option[String] = None,
	  /** Turns. */
		turns: Option[List[Schema.GoogleCloudDiscoveryengineV1alphaSessionTurn]] = None,
	  /** Output only. The time the session started. */
		startTime: Option[String] = None,
	  /** Output only. The time the session finished. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSessionTurn(
	  /** The user query. */
		query: Option[Schema.GoogleCloudDiscoveryengineV1alphaQuery] = None,
	  /** The resource name of the answer to the user query. Only set if the answer generation (/answer API call) happened in this turn. */
		answer: Option[String] = None,
	  /** Output only. In ConversationalSearchService.GetSession API, if GetSessionRequest.include_answer_details is set to true, this field will be populated when getting answer query session. */
		detailedAnswer: Option[Schema.GoogleCloudDiscoveryengineV1alphaAnswer] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaQuery(
	  /** Plain text. */
		text: Option[String] = None,
	  /** Unique Id for the query. */
		queryId: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSetUriPatternDocumentDataMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaSetUriPatternDocumentDataResponse(
	
	)
	
	case class GoogleCloudDiscoveryengineV1alphaTrainCustomModelMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaTrainCustomModelResponse(
	  /** A sample of errors encountered while processing the data. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Echoes the destination for the complete errors in the request if set. */
		errorConfig: Option[Schema.GoogleCloudDiscoveryengineV1alphaImportErrorConfig] = None,
	  /** The trained model status. Possible values are: &#42; &#42;&#42;bad-data&#42;&#42;: The training data quality is bad. &#42; &#42;&#42;no-improvement&#42;&#42;: Tuning didn't improve performance. Won't deploy. &#42; &#42;&#42;in-progress&#42;&#42;: Model training job creation is in progress. &#42; &#42;&#42;training&#42;&#42;: Model is actively training. &#42; &#42;&#42;evaluating&#42;&#42;: The model is evaluating trained metrics. &#42; &#42;&#42;indexing&#42;&#42;: The model trained metrics are indexing. &#42; &#42;&#42;ready&#42;&#42;: The model is ready for serving. */
		modelStatus: Option[String] = None,
	  /** The metrics of the trained model. */
		metrics: Option[Map[String, BigDecimal]] = None,
	  /** Fully qualified name of the CustomTuningModel. */
		modelName: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaTuneEngineMetadata(
	  /** Required. The resource name of the engine that this tune applies to. Format: `projects/{project}/locations/{location}/collections/{collection_id}/engines/{engine_id}` */
		engine: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaTuneEngineResponse(
	
	)
	
	case class GoogleCloudDiscoveryengineV1alphaUpdateCmekConfigMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaUpdateSchemaMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1alphaUpdateTargetSiteMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaBatchCreateTargetSiteMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaBatchCreateTargetSitesResponse(
	  /** TargetSites created. */
		targetSites: Option[List[Schema.GoogleCloudDiscoveryengineV1betaTargetSite]] = None
	)
	
	object GoogleCloudDiscoveryengineV1betaTargetSite {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, INCLUDE, EXCLUDE }
		enum IndexingStatusEnum extends Enum[IndexingStatusEnum] { case INDEXING_STATUS_UNSPECIFIED, PENDING, FAILED, SUCCEEDED, DELETING }
	}
	case class GoogleCloudDiscoveryengineV1betaTargetSite(
	  /** Output only. The fully qualified resource name of the target site. `projects/{project}/locations/{location}/collections/{collection}/dataStores/{data_store}/siteSearchEngine/targetSites/{target_site}` The `target_site_id` is system-generated. */
		name: Option[String] = None,
	  /** Required. Input only. The user provided URI pattern from which the `generated_uri_pattern` is generated. */
		providedUriPattern: Option[String] = None,
	  /** The type of the target site, e.g., whether the site is to be included or excluded. */
		`type`: Option[Schema.GoogleCloudDiscoveryengineV1betaTargetSite.TypeEnum] = None,
	  /** Input only. If set to false, a uri_pattern is generated to include all pages whose address contains the provided_uri_pattern. If set to true, an uri_pattern is generated to try to be an exact match of the provided_uri_pattern or just the specific page if the provided_uri_pattern is a specific one. provided_uri_pattern is always normalized to generate the URI pattern to be used by the search engine. */
		exactMatch: Option[Boolean] = None,
	  /** Output only. This is system-generated based on the provided_uri_pattern. */
		generatedUriPattern: Option[String] = None,
	  /** Output only. Root domain of the provided_uri_pattern. */
		rootDomainUri: Option[String] = None,
	  /** Output only. Site ownership and validity verification status. */
		siteVerificationInfo: Option[Schema.GoogleCloudDiscoveryengineV1betaSiteVerificationInfo] = None,
	  /** Output only. Indexing status. */
		indexingStatus: Option[Schema.GoogleCloudDiscoveryengineV1betaTargetSite.IndexingStatusEnum] = None,
	  /** Output only. The target site's last updated time. */
		updateTime: Option[String] = None,
	  /** Output only. Failure reason. */
		failureReason: Option[Schema.GoogleCloudDiscoveryengineV1betaTargetSiteFailureReason] = None
	)
	
	object GoogleCloudDiscoveryengineV1betaSiteVerificationInfo {
		enum SiteVerificationStateEnum extends Enum[SiteVerificationStateEnum] { case SITE_VERIFICATION_STATE_UNSPECIFIED, VERIFIED, UNVERIFIED, EXEMPTED }
	}
	case class GoogleCloudDiscoveryengineV1betaSiteVerificationInfo(
	  /** Site verification state indicating the ownership and validity. */
		siteVerificationState: Option[Schema.GoogleCloudDiscoveryengineV1betaSiteVerificationInfo.SiteVerificationStateEnum] = None,
	  /** Latest site verification time. */
		verifyTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaTargetSiteFailureReason(
	  /** Failed due to insufficient quota. */
		quotaFailure: Option[Schema.GoogleCloudDiscoveryengineV1betaTargetSiteFailureReasonQuotaFailure] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaTargetSiteFailureReasonQuotaFailure(
	  /** This number is an estimation on how much total quota this project needs to successfully complete indexing. */
		totalRequiredQuota: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1betaControl {
		enum SolutionTypeEnum extends Enum[SolutionTypeEnum] { case SOLUTION_TYPE_UNSPECIFIED, SOLUTION_TYPE_RECOMMENDATION, SOLUTION_TYPE_SEARCH, SOLUTION_TYPE_CHAT, SOLUTION_TYPE_GENERATIVE_CHAT }
		enum UseCasesEnum extends Enum[UseCasesEnum] { case SEARCH_USE_CASE_UNSPECIFIED, SEARCH_USE_CASE_SEARCH, SEARCH_USE_CASE_BROWSE }
	}
	case class GoogleCloudDiscoveryengineV1betaControl(
	  /** Defines a boost-type control */
		boostAction: Option[Schema.GoogleCloudDiscoveryengineV1betaControlBoostAction] = None,
	  /** Defines a filter-type control Currently not supported by Recommendation */
		filterAction: Option[Schema.GoogleCloudDiscoveryengineV1betaControlFilterAction] = None,
	  /** Defines a redirect-type control. */
		redirectAction: Option[Schema.GoogleCloudDiscoveryengineV1betaControlRedirectAction] = None,
	  /** Treats a group of terms as synonyms of one another. */
		synonymsAction: Option[Schema.GoogleCloudDiscoveryengineV1betaControlSynonymsAction] = None,
	  /** Immutable. Fully qualified name `projects/&#42;/locations/global/dataStore/&#42;/controls/&#42;` */
		name: Option[String] = None,
	  /** Required. Human readable name. The identifier used in UI views. Must be UTF-8 encoded string. Length limit is 128 characters. Otherwise an INVALID ARGUMENT error is thrown. */
		displayName: Option[String] = None,
	  /** Output only. List of all ServingConfig IDs this control is attached to. May take up to 10 minutes to update after changes. */
		associatedServingConfigIds: Option[List[String]] = None,
	  /** Required. Immutable. What solution the control belongs to. Must be compatible with vertical of resource. Otherwise an INVALID ARGUMENT error is thrown. */
		solutionType: Option[Schema.GoogleCloudDiscoveryengineV1betaControl.SolutionTypeEnum] = None,
	  /** Specifies the use case for the control. Affects what condition fields can be set. Only applies to SOLUTION_TYPE_SEARCH. Currently only allow one use case per control. Must be set when solution_type is SolutionType.SOLUTION_TYPE_SEARCH. */
		useCases: Option[List[Schema.GoogleCloudDiscoveryengineV1betaControl.UseCasesEnum]] = None,
	  /** Determines when the associated action will trigger. Omit to always apply the action. Currently only a single condition may be specified. Otherwise an INVALID ARGUMENT error is thrown. */
		conditions: Option[List[Schema.GoogleCloudDiscoveryengineV1betaCondition]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaControlBoostAction(
	  /** Required. Strength of the boost, which should be in [-1, 1]. Negative boost means demotion. Default is 0.0 (No-op). */
		boost: Option[BigDecimal] = None,
	  /** Required. Specifies which products to apply the boost to. If no filter is provided all products will be boosted (No-op). Syntax documentation: https://cloud.google.com/retail/docs/filter-and-order Maximum length is 5000 characters. Otherwise an INVALID ARGUMENT error is thrown. */
		filter: Option[String] = None,
	  /** Required. Specifies which data store's documents can be boosted by this control. Full data store name e.g. projects/123/locations/global/collections/default_collection/dataStores/default_data_store */
		dataStore: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaControlFilterAction(
	  /** Required. A filter to apply on the matching condition results. Required Syntax documentation: https://cloud.google.com/retail/docs/filter-and-order Maximum length is 5000 characters. Otherwise an INVALID ARGUMENT error is thrown. */
		filter: Option[String] = None,
	  /** Required. Specifies which data store's documents can be filtered by this control. Full data store name e.g. projects/123/locations/global/collections/default_collection/dataStores/default_data_store */
		dataStore: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaControlRedirectAction(
	  /** Required. The URI to which the shopper will be redirected. Required. URI must have length equal or less than 2000 characters. Otherwise an INVALID ARGUMENT error is thrown. */
		redirectUri: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaControlSynonymsAction(
	  /** Defines a set of synonyms. Can specify up to 100 synonyms. Must specify at least 2 synonyms. Otherwise an INVALID ARGUMENT error is thrown. */
		synonyms: Option[List[String]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaCondition(
	  /** Search only A list of terms to match the query on. Cannot be set when Condition.query_regex is set. Maximum of 10 query terms. */
		queryTerms: Option[List[Schema.GoogleCloudDiscoveryengineV1betaConditionQueryTerm]] = None,
	  /** Range of time(s) specifying when condition is active. Maximum of 10 time ranges. */
		activeTimeRange: Option[List[Schema.GoogleCloudDiscoveryengineV1betaConditionTimeRange]] = None,
	  /** Optional. Query regex to match the whole search query. Cannot be set when Condition.query_terms is set. This is currently supporting promotion use case. */
		queryRegex: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaConditionQueryTerm(
	  /** The specific query value to match against Must be lowercase, must be UTF-8. Can have at most 3 space separated terms if full_match is true. Cannot be an empty string. Maximum length of 5000 characters. */
		value: Option[String] = None,
	  /** Whether the search query needs to exactly match the query term. */
		fullMatch: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaConditionTimeRange(
	  /** Start of time range. Range is inclusive. */
		startTime: Option[String] = None,
	  /** End of time range. Range is inclusive. Must be in the future. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaCreateDataStoreMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaCreateEngineMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaCreateEvaluationMetadata(
	
	)
	
	case class GoogleCloudDiscoveryengineV1betaCreateSchemaMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaCreateSitemapMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaCreateTargetSiteMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1betaDataStore {
		enum IndustryVerticalEnum extends Enum[IndustryVerticalEnum] { case INDUSTRY_VERTICAL_UNSPECIFIED, GENERIC, MEDIA, HEALTHCARE_FHIR }
		enum SolutionTypesEnum extends Enum[SolutionTypesEnum] { case SOLUTION_TYPE_UNSPECIFIED, SOLUTION_TYPE_RECOMMENDATION, SOLUTION_TYPE_SEARCH, SOLUTION_TYPE_CHAT, SOLUTION_TYPE_GENERATIVE_CHAT }
		enum ContentConfigEnum extends Enum[ContentConfigEnum] { case CONTENT_CONFIG_UNSPECIFIED, NO_CONTENT, CONTENT_REQUIRED, PUBLIC_WEBSITE, GOOGLE_WORKSPACE }
	}
	case class GoogleCloudDiscoveryengineV1betaDataStore(
	  /** Immutable. The full resource name of the data store. Format: `projects/{project}/locations/{location}/collections/{collection_id}/dataStores/{data_store_id}`. This field must be a UTF-8 encoded string with a length limit of 1024 characters. */
		name: Option[String] = None,
	  /** Required. The data store display name. This field must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an INVALID_ARGUMENT error is returned. */
		displayName: Option[String] = None,
	  /** Immutable. The industry vertical that the data store registers. */
		industryVertical: Option[Schema.GoogleCloudDiscoveryengineV1betaDataStore.IndustryVerticalEnum] = None,
	  /** The solutions that the data store enrolls. Available solutions for each industry_vertical: &#42; `MEDIA`: `SOLUTION_TYPE_RECOMMENDATION` and `SOLUTION_TYPE_SEARCH`. &#42; `SITE_SEARCH`: `SOLUTION_TYPE_SEARCH` is automatically enrolled. Other solutions cannot be enrolled. */
		solutionTypes: Option[List[Schema.GoogleCloudDiscoveryengineV1betaDataStore.SolutionTypesEnum]] = None,
	  /** Output only. The id of the default Schema asscociated to this data store. */
		defaultSchemaId: Option[String] = None,
	  /** Immutable. The content config of the data store. If this field is unset, the server behavior defaults to ContentConfig.NO_CONTENT. */
		contentConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaDataStore.ContentConfigEnum] = None,
	  /** Output only. Timestamp the DataStore was created at. */
		createTime: Option[String] = None,
	  /** Optional. Configuration for advanced site search. */
		advancedSiteSearchConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaAdvancedSiteSearchConfig] = None,
	  /** Language info for DataStore. */
		languageInfo: Option[Schema.GoogleCloudDiscoveryengineV1betaLanguageInfo] = None,
	  /** Optional. Configuration for Natural Language Query Understanding. */
		naturalLanguageQueryUnderstandingConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaNaturalLanguageQueryUnderstandingConfig] = None,
	  /** Input only. The KMS key to be used to protect this DataStore at creation time. Must be set for requests that need to comply with CMEK Org Policy protections. If this field is set and processed successfully, the DataStore will be protected by the KMS key, as indicated in the cmek_config field. */
		kmsKeyName: Option[String] = None,
	  /** Output only. CMEK-related information for the DataStore. */
		cmekConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaCmekConfig] = None,
	  /** Output only. Data size estimation for billing. */
		billingEstimation: Option[Schema.GoogleCloudDiscoveryengineV1betaDataStoreBillingEstimation] = None,
	  /** Config to store data store type configuration for workspace data. This must be set when DataStore.content_config is set as DataStore.ContentConfig.GOOGLE_WORKSPACE. */
		workspaceConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaWorkspaceConfig] = None,
	  /** Configuration for Document understanding and enrichment. */
		documentProcessingConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaDocumentProcessingConfig] = None,
	  /** The start schema to use for this DataStore when provisioning it. If unset, a default vertical specialized schema will be used. This field is only used by CreateDataStore API, and will be ignored if used in other APIs. This field will be omitted from all API responses including CreateDataStore API. To retrieve a schema of a DataStore, use SchemaService.GetSchema API instead. The provided schema will be validated against certain rules on schema. Learn more from [this doc](https://cloud.google.com/generative-ai-app-builder/docs/provide-schema). */
		startingSchema: Option[Schema.GoogleCloudDiscoveryengineV1betaSchema] = None,
	  /** Optional. Stores serving config at DataStore level. */
		servingConfigDataStore: Option[Schema.GoogleCloudDiscoveryengineV1betaDataStoreServingConfigDataStore] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaAdvancedSiteSearchConfig(
	
	)
	
	case class GoogleCloudDiscoveryengineV1betaLanguageInfo(
	  /** The language code for the DataStore. */
		languageCode: Option[String] = None,
	  /** Output only. This is the normalized form of language_code. E.g.: language_code of `en-GB`, `en_GB`, `en-UK` or `en-gb` will have normalized_language_code of `en-GB`. */
		normalizedLanguageCode: Option[String] = None,
	  /** Output only. Language part of normalized_language_code. E.g.: `en-US` -> `en`, `zh-Hans-HK` -> `zh`, `en` -> `en`. */
		language: Option[String] = None,
	  /** Output only. Region part of normalized_language_code, if present. E.g.: `en-US` -> `US`, `zh-Hans-HK` -> `HK`, `en` -> ``. */
		region: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1betaNaturalLanguageQueryUnderstandingConfig {
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, DISABLED, ENABLED }
	}
	case class GoogleCloudDiscoveryengineV1betaNaturalLanguageQueryUnderstandingConfig(
	  /** Mode of Natural Language Query Understanding. If this field is unset, the behavior defaults to NaturalLanguageQueryUnderstandingConfig.Mode.DISABLED. */
		mode: Option[Schema.GoogleCloudDiscoveryengineV1betaNaturalLanguageQueryUnderstandingConfig.ModeEnum] = None
	)
	
	object GoogleCloudDiscoveryengineV1betaCmekConfig {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, KEY_ISSUE, DELETING, UNUSABLE, ACTIVE_ROTATING }
	}
	case class GoogleCloudDiscoveryengineV1betaCmekConfig(
	  /** Required. Name of the CmekConfig, of the form `projects/{project}/locations/{location}/cmekConfig` or `projects/{project}/locations/{location}/cmekConfigs/{cmekConfig}`. */
		name: Option[String] = None,
	  /** Kms key resource name which will be used to encrypt resources `projects/{project}/locations/{location}/keyRings/{keyRing}/cryptoKeys/{keyId}`. */
		kmsKey: Option[String] = None,
	  /** Kms key version resource name which will be used to encrypt resources `/cryptoKeyVersions/{keyVersion}`. */
		kmsKeyVersion: Option[String] = None,
	  /** Output only. State of the CmekConfig. */
		state: Option[Schema.GoogleCloudDiscoveryengineV1betaCmekConfig.StateEnum] = None,
	  /** Output only. The default CmekConfig for the Customer. */
		isDefault: Option[Boolean] = None,
	  /** Output only. The timestamp of the last key rotation. */
		lastRotationTimestampMicros: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaDataStoreBillingEstimation(
	  /** Data size for structured data in terms of bytes. */
		structuredDataSize: Option[String] = None,
	  /** Data size for unstructured data in terms of bytes. */
		unstructuredDataSize: Option[String] = None,
	  /** Data size for websites in terms of bytes. */
		websiteDataSize: Option[String] = None,
	  /** Last updated timestamp for structured data. */
		structuredDataUpdateTime: Option[String] = None,
	  /** Last updated timestamp for unstructured data. */
		unstructuredDataUpdateTime: Option[String] = None,
	  /** Last updated timestamp for websites. */
		websiteDataUpdateTime: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1betaWorkspaceConfig {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, GOOGLE_DRIVE, GOOGLE_MAIL, GOOGLE_SITES, GOOGLE_CALENDAR, GOOGLE_CHAT, GOOGLE_GROUPS, GOOGLE_KEEP }
	}
	case class GoogleCloudDiscoveryengineV1betaWorkspaceConfig(
	  /** The Google Workspace data source. */
		`type`: Option[Schema.GoogleCloudDiscoveryengineV1betaWorkspaceConfig.TypeEnum] = None,
	  /** Obfuscated Dasher customer ID. */
		dasherCustomerId: Option[String] = None,
	  /** Optional. The super admin service account for the workspace that will be used for access token generation. For now we only use it for Native Google Drive connector data ingestion. */
		superAdminServiceAccount: Option[String] = None,
	  /** Optional. The super admin email address for the workspace that will be used for access token generation. For now we only use it for Native Google Drive connector data ingestion. */
		superAdminEmailAddress: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaDocumentProcessingConfig(
	  /** The full resource name of the Document Processing Config. Format: `projects/&#42;/locations/&#42;/collections/&#42;/dataStores/&#42;/documentProcessingConfig`. */
		name: Option[String] = None,
	  /** Whether chunking mode is enabled. */
		chunkingConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaDocumentProcessingConfigChunkingConfig] = None,
	  /** Configurations for default Document parser. If not specified, we will configure it as default DigitalParsingConfig, and the default parsing config will be applied to all file types for Document parsing. */
		defaultParsingConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaDocumentProcessingConfigParsingConfig] = None,
	  /** Map from file type to override the default parsing configuration based on the file type. Supported keys: &#42; `pdf`: Override parsing config for PDF files, either digital parsing, ocr parsing or layout parsing is supported. &#42; `html`: Override parsing config for HTML files, only digital parsing and layout parsing are supported. &#42; `docx`: Override parsing config for DOCX files, only digital parsing and layout parsing are supported. &#42; `pptx`: Override parsing config for PPTX files, only digital parsing and layout parsing are supported. &#42; `xlsm`: Override parsing config for XLSM files, only digital parsing and layout parsing are supported. &#42; `xlsx`: Override parsing config for XLSX files, only digital parsing and layout parsing are supported. */
		parsingConfigOverrides: Option[Map[String, Schema.GoogleCloudDiscoveryengineV1betaDocumentProcessingConfigParsingConfig]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaDocumentProcessingConfigChunkingConfig(
	  /** Configuration for the layout based chunking. */
		layoutBasedChunkingConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaDocumentProcessingConfigChunkingConfigLayoutBasedChunkingConfig] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaDocumentProcessingConfigChunkingConfigLayoutBasedChunkingConfig(
	  /** The token size limit for each chunk. Supported values: 100-500 (inclusive). Default value: 500. */
		chunkSize: Option[Int] = None,
	  /** Whether to include appending different levels of headings to chunks from the middle of the document to prevent context loss. Default value: False. */
		includeAncestorHeadings: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaDocumentProcessingConfigParsingConfig(
	  /** Configurations applied to digital parser. */
		digitalParsingConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaDocumentProcessingConfigParsingConfigDigitalParsingConfig] = None,
	  /** Configurations applied to OCR parser. Currently it only applies to PDFs. */
		ocrParsingConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaDocumentProcessingConfigParsingConfigOcrParsingConfig] = None,
	  /** Configurations applied to layout parser. */
		layoutParsingConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaDocumentProcessingConfigParsingConfigLayoutParsingConfig] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaDocumentProcessingConfigParsingConfigDigitalParsingConfig(
	
	)
	
	case class GoogleCloudDiscoveryengineV1betaDocumentProcessingConfigParsingConfigOcrParsingConfig(
	  /** [DEPRECATED] This field is deprecated. To use the additional enhanced document elements processing, please switch to `layout_parsing_config`. */
		enhancedDocumentElements: Option[List[String]] = None,
	  /** If true, will use native text instead of OCR text on pages containing native text. */
		useNativeText: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaDocumentProcessingConfigParsingConfigLayoutParsingConfig(
	
	)
	
	case class GoogleCloudDiscoveryengineV1betaSchema(
	  /** The structured representation of the schema. */
		structSchema: Option[Map[String, JsValue]] = None,
	  /** The JSON representation of the schema. */
		jsonSchema: Option[String] = None,
	  /** Immutable. The full resource name of the schema, in the format of `projects/{project}/locations/{location}/collections/{collection}/dataStores/{data_store}/schemas/{schema}`. This field must be a UTF-8 encoded string with a length limit of 1024 characters. */
		name: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaDataStoreServingConfigDataStore(
	  /** If set true, the DataStore will not be available for serving search requests. */
		disabledForServing: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaDeleteDataStoreMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaDeleteEngineMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaDeleteSchemaMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaDeleteSitemapMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaDeleteTargetSiteMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaDisableAdvancedSiteSearchMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaDisableAdvancedSiteSearchResponse(
	
	)
	
	case class GoogleCloudDiscoveryengineV1betaEnableAdvancedSiteSearchMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaEnableAdvancedSiteSearchResponse(
	
	)
	
	object GoogleCloudDiscoveryengineV1betaEngine {
		enum SolutionTypeEnum extends Enum[SolutionTypeEnum] { case SOLUTION_TYPE_UNSPECIFIED, SOLUTION_TYPE_RECOMMENDATION, SOLUTION_TYPE_SEARCH, SOLUTION_TYPE_CHAT, SOLUTION_TYPE_GENERATIVE_CHAT }
		enum IndustryVerticalEnum extends Enum[IndustryVerticalEnum] { case INDUSTRY_VERTICAL_UNSPECIFIED, GENERIC, MEDIA, HEALTHCARE_FHIR }
	}
	case class GoogleCloudDiscoveryengineV1betaEngine(
	  /** Configurations for the Chat Engine. Only applicable if solution_type is SOLUTION_TYPE_CHAT. */
		chatEngineConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaEngineChatEngineConfig] = None,
	  /** Configurations for the Search Engine. Only applicable if solution_type is SOLUTION_TYPE_SEARCH. */
		searchEngineConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaEngineSearchEngineConfig] = None,
	  /** Output only. Additional information of the Chat Engine. Only applicable if solution_type is SOLUTION_TYPE_CHAT. */
		chatEngineMetadata: Option[Schema.GoogleCloudDiscoveryengineV1betaEngineChatEngineMetadata] = None,
	  /** Immutable. The fully qualified resource name of the engine. This field must be a UTF-8 encoded string with a length limit of 1024 characters. Format: `projects/{project}/locations/{location}/collections/{collection}/engines/{engine}` engine should be 1-63 characters, and valid characters are /a-z0-9&#42;/. Otherwise, an INVALID_ARGUMENT error is returned. */
		name: Option[String] = None,
	  /** Required. The display name of the engine. Should be human readable. UTF-8 encoded string with limit of 1024 characters. */
		displayName: Option[String] = None,
	  /** Output only. Timestamp the Recommendation Engine was created at. */
		createTime: Option[String] = None,
	  /** Output only. Timestamp the Recommendation Engine was last updated. */
		updateTime: Option[String] = None,
	  /** The data stores associated with this engine. For SOLUTION_TYPE_SEARCH and SOLUTION_TYPE_RECOMMENDATION type of engines, they can only associate with at most one data store. If solution_type is SOLUTION_TYPE_CHAT, multiple DataStores in the same Collection can be associated here. Note that when used in CreateEngineRequest, one DataStore id must be provided as the system will use it for necessary initializations. */
		dataStoreIds: Option[List[String]] = None,
	  /** Required. The solutions of the engine. */
		solutionType: Option[Schema.GoogleCloudDiscoveryengineV1betaEngine.SolutionTypeEnum] = None,
	  /** The industry vertical that the engine registers. The restriction of the Engine industry vertical is based on DataStore: If unspecified, default to `GENERIC`. Vertical on Engine has to match vertical of the DataStore linked to the engine. */
		industryVertical: Option[Schema.GoogleCloudDiscoveryengineV1betaEngine.IndustryVerticalEnum] = None,
	  /** Common config spec that specifies the metadata of the engine. */
		commonConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaEngineCommonConfig] = None,
	  /** Optional. Whether to disable analytics for searches performed on this engine. */
		disableAnalytics: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaEngineChatEngineConfig(
	  /** The configurationt generate the Dialogflow agent that is associated to this Engine. Note that these configurations are one-time consumed by and passed to Dialogflow service. It means they cannot be retrieved using EngineService.GetEngine or EngineService.ListEngines API after engine creation. */
		agentCreationConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaEngineChatEngineConfigAgentCreationConfig] = None,
	  /** The resource name of an exist Dialogflow agent to link to this Chat Engine. Customers can either provide `agent_creation_config` to create agent or provide an agent name that links the agent with the Chat engine. Format: `projects//locations//agents/`. Note that the `dialogflow_agent_to_link` are one-time consumed by and passed to Dialogflow service. It means they cannot be retrieved using EngineService.GetEngine or EngineService.ListEngines API after engine creation. Use ChatEngineMetadata.dialogflow_agent for actual agent association after Engine is created. */
		dialogflowAgentToLink: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaEngineChatEngineConfigAgentCreationConfig(
	  /** Name of the company, organization or other entity that the agent represents. Used for knowledge connector LLM prompt and for knowledge search. */
		business: Option[String] = None,
	  /** Required. The default language of the agent as a language tag. See [Language Support](https://cloud.google.com/dialogflow/docs/reference/language) for a list of the currently supported language codes. */
		defaultLanguageCode: Option[String] = None,
	  /** Required. The time zone of the agent from the [time zone database](https://www.iana.org/time-zones), e.g., America/New_York, Europe/Paris. */
		timeZone: Option[String] = None,
	  /** Agent location for Agent creation, supported values: global/us/eu. If not provided, us Engine will create Agent using us-central-1 by default; eu Engine will create Agent using eu-west-1 by default. */
		location: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1betaEngineSearchEngineConfig {
		enum SearchTierEnum extends Enum[SearchTierEnum] { case SEARCH_TIER_UNSPECIFIED, SEARCH_TIER_STANDARD, SEARCH_TIER_ENTERPRISE }
		enum SearchAddOnsEnum extends Enum[SearchAddOnsEnum] { case SEARCH_ADD_ON_UNSPECIFIED, SEARCH_ADD_ON_LLM }
	}
	case class GoogleCloudDiscoveryengineV1betaEngineSearchEngineConfig(
	  /** The search feature tier of this engine. Different tiers might have different pricing. To learn more, check the pricing documentation. Defaults to SearchTier.SEARCH_TIER_STANDARD if not specified. */
		searchTier: Option[Schema.GoogleCloudDiscoveryengineV1betaEngineSearchEngineConfig.SearchTierEnum] = None,
	  /** The add-on that this search engine enables. */
		searchAddOns: Option[List[Schema.GoogleCloudDiscoveryengineV1betaEngineSearchEngineConfig.SearchAddOnsEnum]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaEngineChatEngineMetadata(
	  /** The resource name of a Dialogflow agent, that this Chat Engine refers to. Format: `projects//locations//agents/`. */
		dialogflowAgent: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaEngineCommonConfig(
	  /** The name of the company, business or entity that is associated with the engine. Setting this may help improve LLM related features. */
		companyName: Option[String] = None
	)
	
	object GoogleCloudDiscoveryengineV1betaEvaluation {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, RUNNING, SUCCEEDED, FAILED }
	}
	case class GoogleCloudDiscoveryengineV1betaEvaluation(
	  /** Identifier. The full resource name of the Evaluation, in the format of `projects/{project}/locations/{location}/evaluations/{evaluation}`. This field must be a UTF-8 encoded string with a length limit of 1024 characters. */
		name: Option[String] = None,
	  /** Required. The specification of the evaluation. */
		evaluationSpec: Option[Schema.GoogleCloudDiscoveryengineV1betaEvaluationEvaluationSpec] = None,
	  /** Output only. The metrics produced by the evaluation, averaged across all SampleQuerys in the SampleQuerySet. Only populated when the evaluation's state is SUCCEEDED. */
		qualityMetrics: Option[Schema.GoogleCloudDiscoveryengineV1betaQualityMetrics] = None,
	  /** Output only. The state of the evaluation. */
		state: Option[Schema.GoogleCloudDiscoveryengineV1betaEvaluation.StateEnum] = None,
	  /** Output only. The error that occurred during evaluation. Only populated when the evaluation's state is FAILED. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** Output only. Timestamp the Evaluation was created at. */
		createTime: Option[String] = None,
	  /** Output only. Timestamp the Evaluation was completed at. */
		endTime: Option[String] = None,
	  /** Output only. A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaEvaluationEvaluationSpec(
	  /** Required. The search request that is used to perform the evaluation. Only the following fields within SearchRequest are supported; if any other fields are provided, an UNSUPPORTED error will be returned: &#42; SearchRequest.serving_config &#42; SearchRequest.branch &#42; SearchRequest.canonical_filter &#42; SearchRequest.query_expansion_spec &#42; SearchRequest.spell_correction_spec &#42; SearchRequest.content_search_spec &#42; SearchRequest.user_pseudo_id */
		searchRequest: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequest] = None,
	  /** Required. The specification of the query set. */
		querySetSpec: Option[Schema.GoogleCloudDiscoveryengineV1betaEvaluationEvaluationSpecQuerySetSpec] = None
	)
	
	object GoogleCloudDiscoveryengineV1betaSearchRequest {
		enum RelevanceThresholdEnum extends Enum[RelevanceThresholdEnum] { case RELEVANCE_THRESHOLD_UNSPECIFIED, LOWEST, LOW, MEDIUM, HIGH }
	}
	case class GoogleCloudDiscoveryengineV1betaSearchRequest(
	  /** Required. The resource name of the Search serving config, such as `projects/&#42;/locations/global/collections/default_collection/engines/&#42;/servingConfigs/default_serving_config`, or `projects/&#42;/locations/global/collections/default_collection/dataStores/default_data_store/servingConfigs/default_serving_config`. This field is used to identify the serving configuration name, set of models used to make the search. */
		servingConfig: Option[String] = None,
	  /** The branch resource name, such as `projects/&#42;/locations/global/collections/default_collection/dataStores/default_data_store/branches/0`. Use `default_branch` as the branch ID or leave this field empty, to search documents under the default branch. */
		branch: Option[String] = None,
	  /** Raw search query. */
		query: Option[String] = None,
	  /** Raw image query. */
		imageQuery: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestImageQuery] = None,
	  /** Maximum number of Documents to return. The maximum allowed value depends on the data type. Values above the maximum value are coerced to the maximum value. &#42; Websites with basic indexing: Default `10`, Maximum `25`. &#42; Websites with advanced indexing: Default `25`, Maximum `50`. &#42; Other: Default `50`, Maximum `100`. If this field is negative, an `INVALID_ARGUMENT` is returned. */
		pageSize: Option[Int] = None,
	  /** A page token received from a previous SearchService.Search call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to SearchService.Search must match the call that provided the page token. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		pageToken: Option[String] = None,
	  /** A 0-indexed integer that specifies the current offset (that is, starting result location, amongst the Documents deemed by the API as relevant) in search results. This field is only considered if page_token is unset. If this field is negative, an `INVALID_ARGUMENT` is returned. */
		offset: Option[Int] = None,
	  /** The maximum number of results to return for OneBox. This applies to each OneBox type individually. Default number is 10. */
		oneBoxPageSize: Option[Int] = None,
	  /** Specs defining dataStores to filter on in a search call and configurations for those dataStores. This is only considered for engines with multiple dataStores use case. For single dataStore within an engine, they should use the specs at the top level. */
		dataStoreSpecs: Option[List[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestDataStoreSpec]] = None,
	  /** The filter syntax consists of an expression language for constructing a predicate from one or more fields of the documents being filtered. Filter expression is case-sensitive. If this field is unrecognizable, an `INVALID_ARGUMENT` is returned. Filtering in Vertex AI Search is done by mapping the LHS filter key to a key property defined in the Vertex AI Search backend -- this mapping is defined by the customer in their schema. For example a media customer might have a field 'name' in their schema. In this case the filter would look like this: filter --> name:'ANY("king kong")' For more information about filtering including syntax and filter operators, see [Filter](https://cloud.google.com/generative-ai-app-builder/docs/filter-search-metadata) */
		filter: Option[String] = None,
	  /** The default filter that is applied when a user performs a search without checking any filters on the search page. The filter applied to every search request when quality improvement such as query expansion is needed. In the case a query does not have a sufficient amount of results this filter will be used to determine whether or not to enable the query expansion flow. The original filter will still be used for the query expanded search. This field is strongly recommended to achieve high search quality. For more information about filter syntax, see SearchRequest.filter. */
		canonicalFilter: Option[String] = None,
	  /** The order in which documents are returned. Documents can be ordered by a field in an Document object. Leave it unset if ordered by relevance. `order_by` expression is case-sensitive. For more information on ordering the website search results, see [Order web search results](https://cloud.google.com/generative-ai-app-builder/docs/order-web-search-results). For more information on ordering the healthcare search results, see [Order healthcare search results](https://cloud.google.com/generative-ai-app-builder/docs/order-hc-results). If this field is unrecognizable, an `INVALID_ARGUMENT` is returned. */
		orderBy: Option[String] = None,
	  /** Information about the end user. Highly recommended for analytics. UserInfo.user_agent is used to deduce `device_type` for analytics. */
		userInfo: Option[Schema.GoogleCloudDiscoveryengineV1betaUserInfo] = None,
	  /** The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see [Standard fields](https://cloud.google.com/apis/design/standard_fields). This field helps to better interpret the query. If a value isn't specified, the query language code is automatically detected, which may not be accurate. */
		languageCode: Option[String] = None,
	  /** The Unicode country/region code (CLDR) of a location, such as "US" and "419". For more information, see [Standard fields](https://cloud.google.com/apis/design/standard_fields). If set, then results will be boosted based on the region_code provided. */
		regionCode: Option[String] = None,
	  /** Facet specifications for faceted search. If empty, no facets are returned. A maximum of 100 values are allowed. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		facetSpecs: Option[List[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestFacetSpec]] = None,
	  /** Boost specification to boost certain documents. For more information on boosting, see [Boosting](https://cloud.google.com/generative-ai-app-builder/docs/boost-search-results) */
		boostSpec: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestBoostSpec] = None,
	  /** Additional search parameters. For public website search only, supported values are: &#42; `user_country_code`: string. Default empty. If set to non-empty, results are restricted or boosted based on the location provided. For example, `user_country_code: "au"` For available codes see [Country Codes](https://developers.google.com/custom-search/docs/json_api_reference#countryCodes) &#42; `search_type`: double. Default empty. Enables non-webpage searching depending on the value. The only valid non-default value is 1, which enables image searching. For example, `search_type: 1` */
		params: Option[Map[String, JsValue]] = None,
	  /** The query expansion specification that specifies the conditions under which query expansion occurs. */
		queryExpansionSpec: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestQueryExpansionSpec] = None,
	  /** The spell correction specification that specifies the mode under which spell correction takes effect. */
		spellCorrectionSpec: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestSpellCorrectionSpec] = None,
	  /** A unique identifier for tracking visitors. For example, this could be implemented with an HTTP cookie, which should be able to uniquely identify a visitor on a single device. This unique identifier should not change if the visitor logs in or out of the website. This field should NOT have a fixed value such as `unknown_visitor`. This should be the same identifier as UserEvent.user_pseudo_id and CompleteQueryRequest.user_pseudo_id The field must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		userPseudoId: Option[String] = None,
	  /** A specification for configuring the behavior of content search. */
		contentSearchSpec: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestContentSearchSpec] = None,
	  /** Uses the provided embedding to do additional semantic document retrieval. The retrieval is based on the dot product of SearchRequest.EmbeddingSpec.EmbeddingVector.vector and the document embedding that is provided in SearchRequest.EmbeddingSpec.EmbeddingVector.field_path. If SearchRequest.EmbeddingSpec.EmbeddingVector.field_path is not provided, it will use ServingConfig.EmbeddingConfig.field_path. */
		embeddingSpec: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestEmbeddingSpec] = None,
	  /** The ranking expression controls the customized ranking on retrieval documents. This overrides ServingConfig.ranking_expression. The ranking expression is a single function or multiple functions that are joined by "+". &#42; ranking_expression = function, { " + ", function }; Supported functions: &#42; double &#42; relevance_score &#42; double &#42; dotProduct(embedding_field_path) Function variables: &#42; `relevance_score`: pre-defined keywords, used for measure relevance between query and document. &#42; `embedding_field_path`: the document embedding field used with query embedding vector. &#42; `dotProduct`: embedding function between embedding_field_path and query embedding vector. Example ranking expression: If document has an embedding field doc_embedding, the ranking expression could be `0.5 &#42; relevance_score + 0.3 &#42; dotProduct(doc_embedding)`. */
		rankingExpression: Option[String] = None,
	  /** Whether to turn on safe search. This is only supported for website search. */
		safeSearch: Option[Boolean] = None,
	  /** The user labels applied to a resource must meet the following requirements: &#42; Each resource can have multiple labels, up to a maximum of 64. &#42; Each label must be a key-value pair. &#42; Keys have a minimum length of 1 character and a maximum length of 63 characters and cannot be empty. Values can be empty and have a maximum length of 63 characters. &#42; Keys and values can contain only lowercase letters, numeric characters, underscores, and dashes. All characters must use UTF-8 encoding, and international characters are allowed. &#42; The key portion of a label must be unique. However, you can use the same key with multiple resources. &#42; Keys must start with a lowercase letter or international character. See [Google Cloud Document](https://cloud.google.com/resource-manager/docs/creating-managing-labels#requirements) for more details. */
		userLabels: Option[Map[String, String]] = None,
	  /** If `naturalLanguageQueryUnderstandingSpec` is not specified, no additional natural language query understanding will be done. */
		naturalLanguageQueryUnderstandingSpec: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestNaturalLanguageQueryUnderstandingSpec] = None,
	  /** Search as you type configuration. Only supported for the IndustryVertical.MEDIA vertical. */
		searchAsYouTypeSpec: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestSearchAsYouTypeSpec] = None,
	  /** The session resource name. Optional. Session allows users to do multi-turn /search API calls or coordination between /search API calls and /answer API calls. Example #1 (multi-turn /search API calls): 1. Call /search API with the auto-session mode (see below). 2. Call /search API with the session ID generated in the first call. Here, the previous search query gets considered in query standing. I.e., if the first query is "How did Alphabet do in 2022?" and the current query is "How about 2023?", the current query will be interpreted as "How did Alphabet do in 2023?". Example #2 (coordination between /search API calls and /answer API calls): 1. Call /search API with the auto-session mode (see below). 2. Call /answer API with the session ID generated in the first call. Here, the answer generation happens in the context of the search results from the first search call. Auto-session mode: when `projects/.../sessions/-` is used, a new session gets automatically created. Otherwise, users can use the create-session API to create a session manually. Multi-turn Search feature is currently at private GA stage. Please use v1alpha or v1beta version instead before we launch this feature to public GA. Or ask for allowlisting through Google Support team. */
		session: Option[String] = None,
	  /** Session specification. Can be used only when `session` is set. */
		sessionSpec: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestSessionSpec] = None,
	  /** The relevance threshold of the search results. Default to Google defined threshold, leveraging a balance of precision and recall to deliver both highly accurate results and comprehensive coverage of relevant information. */
		relevanceThreshold: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequest.RelevanceThresholdEnum] = None,
	  /** The specification for personalization. Notice that if both ServingConfig.personalization_spec and SearchRequest.personalization_spec are set, SearchRequest.personalization_spec overrides ServingConfig.personalization_spec. */
		personalizationSpec: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestPersonalizationSpec] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaSearchRequestImageQuery(
	  /** Base64 encoded image bytes. Supported image formats: JPEG, PNG, and BMP. */
		imageBytes: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaSearchRequestDataStoreSpec(
	  /** Required. Full resource name of DataStore, such as `projects/{project}/locations/{location}/collections/{collection_id}/dataStores/{data_store_id}`. */
		dataStore: Option[String] = None,
	  /** Optional. Filter specification to filter documents in the data store specified by data_store field. For more information on filtering, see [Filtering](https://cloud.google.com/generative-ai-app-builder/docs/filter-search-metadata) */
		filter: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaUserInfo(
	  /** Highly recommended for logged-in users. Unique identifier for logged-in user, such as a user name. Don't set for anonymous users. Always use a hashed value for this ID. Don't set the field to the same fixed ID for different users. This mixes the event history of those users together, which results in degraded model quality. The field must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		userId: Option[String] = None,
	  /** User agent as included in the HTTP header. The field must be a UTF-8 encoded string with a length limit of 1,000 characters. Otherwise, an `INVALID_ARGUMENT` error is returned. This should not be set when using the client side event reporting with GTM or JavaScript tag in UserEventService.CollectUserEvent or if UserEvent.direct_user_request is set. */
		userAgent: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaSearchRequestFacetSpec(
	  /** Required. The facet key specification. */
		facetKey: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestFacetSpecFacetKey] = None,
	  /** Maximum facet values that are returned for this facet. If unspecified, defaults to 20. The maximum allowed value is 300. Values above 300 are coerced to 300. For aggregation in healthcare search, when the [FacetKey.key] is "healthcare_aggregation_key", the limit will be overridden to 10,000 internally, regardless of the value set here. If this field is negative, an `INVALID_ARGUMENT` is returned. */
		limit: Option[Int] = None,
	  /** List of keys to exclude when faceting. By default, FacetKey.key is not excluded from the filter unless it is listed in this field. Listing a facet key in this field allows its values to appear as facet results, even when they are filtered out of search results. Using this field does not affect what search results are returned. For example, suppose there are 100 documents with the color facet "Red" and 200 documents with the color facet "Blue". A query containing the filter "color:ANY("Red")" and having "color" as FacetKey.key would by default return only "Red" documents in the search results, and also return "Red" with count 100 as the only color facet. Although there are also blue documents available, "Blue" would not be shown as an available facet value. If "color" is listed in "excludedFilterKeys", then the query returns the facet values "Red" with count 100 and "Blue" with count 200, because the "color" key is now excluded from the filter. Because this field doesn't affect search results, the search results are still correctly filtered to return only "Red" documents. A maximum of 100 values are allowed. Otherwise, an `INVALID_ARGUMENT` error is returned. */
		excludedFilterKeys: Option[List[String]] = None,
	  /** Enables dynamic position for this facet. If set to true, the position of this facet among all facets in the response is determined automatically. If dynamic facets are enabled, it is ordered together. If set to false, the position of this facet in the response is the same as in the request, and it is ranked before the facets with dynamic position enable and all dynamic facets. For example, you may always want to have rating facet returned in the response, but it's not necessarily to always display the rating facet at the top. In that case, you can set enable_dynamic_position to true so that the position of rating facet in response is determined automatically. Another example, assuming you have the following facets in the request: &#42; "rating", enable_dynamic_position = true &#42; "price", enable_dynamic_position = false &#42; "brands", enable_dynamic_position = false And also you have a dynamic facets enabled, which generates a facet `gender`. Then the final order of the facets in the response can be ("price", "brands", "rating", "gender") or ("price", "brands", "gender", "rating") depends on how API orders "gender" and "rating" facets. However, notice that "price" and "brands" are always ranked at first and second position because their enable_dynamic_position is false. */
		enableDynamicPosition: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaSearchRequestFacetSpecFacetKey(
	  /** Required. Supported textual and numerical facet keys in Document object, over which the facet values are computed. Facet key is case-sensitive. */
		key: Option[String] = None,
	  /** Set only if values should be bucketed into intervals. Must be set for facets with numerical values. Must not be set for facet with text values. Maximum number of intervals is 30. */
		intervals: Option[List[Schema.GoogleCloudDiscoveryengineV1betaInterval]] = None,
	  /** Only get facet for the given restricted values. Only supported on textual fields. For example, suppose "category" has three values "Action > 2022", "Action > 2021" and "Sci-Fi > 2022". If set "restricted_values" to "Action > 2022", the "category" facet only contains "Action > 2022". Only supported on textual fields. Maximum is 10. */
		restrictedValues: Option[List[String]] = None,
	  /** Only get facet values that start with the given string prefix. For example, suppose "category" has three values "Action > 2022", "Action > 2021" and "Sci-Fi > 2022". If set "prefixes" to "Action", the "category" facet only contains "Action > 2022" and "Action > 2021". Only supported on textual fields. Maximum is 10. */
		prefixes: Option[List[String]] = None,
	  /** Only get facet values that contain the given strings. For example, suppose "category" has three values "Action > 2022", "Action > 2021" and "Sci-Fi > 2022". If set "contains" to "2022", the "category" facet only contains "Action > 2022" and "Sci-Fi > 2022". Only supported on textual fields. Maximum is 10. */
		contains: Option[List[String]] = None,
	  /** True to make facet keys case insensitive when getting faceting values with prefixes or contains; false otherwise. */
		caseInsensitive: Option[Boolean] = None,
	  /** The order in which documents are returned. Allowed values are: &#42; "count desc", which means order by SearchResponse.Facet.values.count descending. &#42; "value desc", which means order by SearchResponse.Facet.values.value descending. Only applies to textual facets. If not set, textual values are sorted in [natural order](https://en.wikipedia.org/wiki/Natural_sort_order); numerical intervals are sorted in the order given by FacetSpec.FacetKey.intervals. */
		orderBy: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaInterval(
	  /** Inclusive lower bound. */
		minimum: Option[BigDecimal] = None,
	  /** Exclusive lower bound. */
		exclusiveMinimum: Option[BigDecimal] = None,
	  /** Inclusive upper bound. */
		maximum: Option[BigDecimal] = None,
	  /** Exclusive upper bound. */
		exclusiveMaximum: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaSearchRequestBoostSpec(
	  /** Condition boost specifications. If a document matches multiple conditions in the specifictions, boost scores from these specifications are all applied and combined in a non-linear way. Maximum number of specifications is 20. */
		conditionBoostSpecs: Option[List[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestBoostSpecConditionBoostSpec]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaSearchRequestBoostSpecConditionBoostSpec(
	  /** An expression which specifies a boost condition. The syntax and supported fields are the same as a filter expression. See SearchRequest.filter for detail syntax and limitations. Examples: &#42; To boost documents with document ID "doc_1" or "doc_2", and color "Red" or "Blue": `(document_id: ANY("doc_1", "doc_2")) AND (color: ANY("Red", "Blue"))` */
		condition: Option[String] = None,
	  /** Strength of the condition boost, which should be in [-1, 1]. Negative boost means demotion. Default is 0.0. Setting to 1.0 gives the document a big promotion. However, it does not necessarily mean that the boosted document will be the top result at all times, nor that other documents will be excluded. Results could still be shown even when none of them matches the condition. And results that are significantly more relevant to the search query can still trump your heavily favored but irrelevant documents. Setting to -1.0 gives the document a big demotion. However, results that are deeply relevant might still be shown. The document will have an upstream battle to get a fairly high ranking, but it is not blocked out completely. Setting to 0.0 means no boost applied. The boosting condition is ignored. Only one of the (condition, boost) combination or the boost_control_spec below are set. If both are set then the global boost is ignored and the more fine-grained boost_control_spec is applied. */
		boost: Option[BigDecimal] = None,
	  /** Complex specification for custom ranking based on customer defined attribute value. */
		boostControlSpec: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestBoostSpecConditionBoostSpecBoostControlSpec] = None
	)
	
	object GoogleCloudDiscoveryengineV1betaSearchRequestBoostSpecConditionBoostSpecBoostControlSpec {
		enum AttributeTypeEnum extends Enum[AttributeTypeEnum] { case ATTRIBUTE_TYPE_UNSPECIFIED, NUMERICAL, FRESHNESS }
		enum InterpolationTypeEnum extends Enum[InterpolationTypeEnum] { case INTERPOLATION_TYPE_UNSPECIFIED, LINEAR }
	}
	case class GoogleCloudDiscoveryengineV1betaSearchRequestBoostSpecConditionBoostSpecBoostControlSpec(
	  /** The name of the field whose value will be used to determine the boost amount. */
		fieldName: Option[String] = None,
	  /** The attribute type to be used to determine the boost amount. The attribute value can be derived from the field value of the specified field_name. In the case of numerical it is straightforward i.e. attribute_value = numerical_field_value. In the case of freshness however, attribute_value = (time.now() - datetime_field_value). */
		attributeType: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestBoostSpecConditionBoostSpecBoostControlSpec.AttributeTypeEnum] = None,
	  /** The interpolation type to be applied to connect the control points listed below. */
		interpolationType: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestBoostSpecConditionBoostSpecBoostControlSpec.InterpolationTypeEnum] = None,
	  /** The control points used to define the curve. The monotonic function (defined through the interpolation_type above) passes through the control points listed here. */
		controlPoints: Option[List[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestBoostSpecConditionBoostSpecBoostControlSpecControlPoint]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaSearchRequestBoostSpecConditionBoostSpecBoostControlSpecControlPoint(
	  /** Can be one of: 1. The numerical field value. 2. The duration spec for freshness: The value must be formatted as an XSD `dayTimeDuration` value (a restricted subset of an ISO 8601 duration value). The pattern for this is: `nDnM]`. */
		attributeValue: Option[String] = None,
	  /** The value between -1 to 1 by which to boost the score if the attribute_value evaluates to the value specified above. */
		boostAmount: Option[BigDecimal] = None
	)
	
	object GoogleCloudDiscoveryengineV1betaSearchRequestQueryExpansionSpec {
		enum ConditionEnum extends Enum[ConditionEnum] { case CONDITION_UNSPECIFIED, DISABLED, AUTO }
	}
	case class GoogleCloudDiscoveryengineV1betaSearchRequestQueryExpansionSpec(
	  /** The condition under which query expansion should occur. Default to Condition.DISABLED. */
		condition: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestQueryExpansionSpec.ConditionEnum] = None,
	  /** Whether to pin unexpanded results. If this field is set to true, unexpanded products are always at the top of the search results, followed by the expanded results. */
		pinUnexpandedResults: Option[Boolean] = None
	)
	
	object GoogleCloudDiscoveryengineV1betaSearchRequestSpellCorrectionSpec {
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, SUGGESTION_ONLY, AUTO }
	}
	case class GoogleCloudDiscoveryengineV1betaSearchRequestSpellCorrectionSpec(
	  /** The mode under which spell correction replaces the original search query. Defaults to Mode.AUTO. */
		mode: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestSpellCorrectionSpec.ModeEnum] = None
	)
	
	object GoogleCloudDiscoveryengineV1betaSearchRequestContentSearchSpec {
		enum SearchResultModeEnum extends Enum[SearchResultModeEnum] { case SEARCH_RESULT_MODE_UNSPECIFIED, DOCUMENTS, CHUNKS }
	}
	case class GoogleCloudDiscoveryengineV1betaSearchRequestContentSearchSpec(
	  /** If `snippetSpec` is not specified, snippets are not included in the search response. */
		snippetSpec: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestContentSearchSpecSnippetSpec] = None,
	  /** If `summarySpec` is not specified, summaries are not included in the search response. */
		summarySpec: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestContentSearchSpecSummarySpec] = None,
	  /** If there is no extractive_content_spec provided, there will be no extractive answer in the search response. */
		extractiveContentSpec: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestContentSearchSpecExtractiveContentSpec] = None,
	  /** Specifies the search result mode. If unspecified, the search result mode defaults to `DOCUMENTS`. */
		searchResultMode: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestContentSearchSpec.SearchResultModeEnum] = None,
	  /** Specifies the chunk spec to be returned from the search response. Only available if the SearchRequest.ContentSearchSpec.search_result_mode is set to CHUNKS */
		chunkSpec: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestContentSearchSpecChunkSpec] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaSearchRequestContentSearchSpecSnippetSpec(
	  /** [DEPRECATED] This field is deprecated. To control snippet return, use `return_snippet` field. For backwards compatibility, we will return snippet if max_snippet_count > 0. */
		maxSnippetCount: Option[Int] = None,
	  /** [DEPRECATED] This field is deprecated and will have no affect on the snippet. */
		referenceOnly: Option[Boolean] = None,
	  /** If `true`, then return snippet. If no snippet can be generated, we return "No snippet is available for this page." A `snippet_status` with `SUCCESS` or `NO_SNIPPET_AVAILABLE` will also be returned. */
		returnSnippet: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaSearchRequestContentSearchSpecSummarySpec(
	  /** The number of top results to generate the summary from. If the number of results returned is less than `summaryResultCount`, the summary is generated from all of the results. At most 10 results for documents mode, or 50 for chunks mode, can be used to generate a summary. The chunks mode is used when SearchRequest.ContentSearchSpec.search_result_mode is set to CHUNKS. */
		summaryResultCount: Option[Int] = None,
	  /** Specifies whether to include citations in the summary. The default value is `false`. When this field is set to `true`, summaries include in-line citation numbers. Example summary including citations: BigQuery is Google Cloud's fully managed and completely serverless enterprise data warehouse [1]. BigQuery supports all data types, works across clouds, and has built-in machine learning and business intelligence, all within a unified platform [2, 3]. The citation numbers refer to the returned search results and are 1-indexed. For example, [1] means that the sentence is attributed to the first search result. [2, 3] means that the sentence is attributed to both the second and third search results. */
		includeCitations: Option[Boolean] = None,
	  /** Specifies whether to filter out adversarial queries. The default value is `false`. Google employs search-query classification to detect adversarial queries. No summary is returned if the search query is classified as an adversarial query. For example, a user might ask a question regarding negative comments about the company or submit a query designed to generate unsafe, policy-violating output. If this field is set to `true`, we skip generating summaries for adversarial queries and return fallback messages instead. */
		ignoreAdversarialQuery: Option[Boolean] = None,
	  /** Specifies whether to filter out queries that are not summary-seeking. The default value is `false`. Google employs search-query classification to detect summary-seeking queries. No summary is returned if the search query is classified as a non-summary seeking query. For example, `why is the sky blue` and `Who is the best soccer player in the world?` are summary-seeking queries, but `SFO airport` and `world cup 2026` are not. They are most likely navigational queries. If this field is set to `true`, we skip generating summaries for non-summary seeking queries and return fallback messages instead. */
		ignoreNonSummarySeekingQuery: Option[Boolean] = None,
	  /** Specifies whether to filter out queries that have low relevance. The default value is `false`. If this field is set to `false`, all search results are used regardless of relevance to generate answers. If set to `true`, only queries with high relevance search results will generate answers. */
		ignoreLowRelevantContent: Option[Boolean] = None,
	  /** Optional. Specifies whether to filter out jail-breaking queries. The default value is `false`. Google employs search-query classification to detect jail-breaking queries. No summary is returned if the search query is classified as a jail-breaking query. A user might add instructions to the query to change the tone, style, language, content of the answer, or ask the model to act as a different entity, e.g. "Reply in the tone of a competing company's CEO". If this field is set to `true`, we skip generating summaries for jail-breaking queries and return fallback messages instead. */
		ignoreJailBreakingQuery: Option[Boolean] = None,
	  /** If specified, the spec will be used to modify the prompt provided to the LLM. */
		modelPromptSpec: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestContentSearchSpecSummarySpecModelPromptSpec] = None,
	  /** Language code for Summary. Use language tags defined by [BCP47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt). Note: This is an experimental feature. */
		languageCode: Option[String] = None,
	  /** If specified, the spec will be used to modify the model specification provided to the LLM. */
		modelSpec: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestContentSearchSpecSummarySpecModelSpec] = None,
	  /** If true, answer will be generated from most relevant chunks from top search results. This feature will improve summary quality. Note that with this feature enabled, not all top search results will be referenced and included in the reference list, so the citation source index only points to the search results listed in the reference list. */
		useSemanticChunks: Option[Boolean] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaSearchRequestContentSearchSpecSummarySpecModelPromptSpec(
	  /** Text at the beginning of the prompt that instructs the assistant. Examples are available in the user guide. */
		preamble: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaSearchRequestContentSearchSpecSummarySpecModelSpec(
	  /** The model version used to generate the summary. Supported values are: &#42; `stable`: string. Default value when no value is specified. Uses a generally available, fine-tuned model. For more information, see [Answer generation model versions and lifecycle](https://cloud.google.com/generative-ai-app-builder/docs/answer-generation-models). &#42; `preview`: string. (Public preview) Uses a preview model. For more information, see [Answer generation model versions and lifecycle](https://cloud.google.com/generative-ai-app-builder/docs/answer-generation-models). */
		version: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaSearchRequestContentSearchSpecExtractiveContentSpec(
	  /** The maximum number of extractive answers returned in each search result. An extractive answer is a verbatim answer extracted from the original document, which provides a precise and contextually relevant answer to the search query. If the number of matching answers is less than the `max_extractive_answer_count`, return all of the answers. Otherwise, return the `max_extractive_answer_count`. At most five answers are returned for each SearchResult. */
		maxExtractiveAnswerCount: Option[Int] = None,
	  /** The max number of extractive segments returned in each search result. Only applied if the DataStore is set to DataStore.ContentConfig.CONTENT_REQUIRED or DataStore.solution_types is SOLUTION_TYPE_CHAT. An extractive segment is a text segment extracted from the original document that is relevant to the search query, and, in general, more verbose than an extractive answer. The segment could then be used as input for LLMs to generate summaries and answers. If the number of matching segments is less than `max_extractive_segment_count`, return all of the segments. Otherwise, return the `max_extractive_segment_count`. */
		maxExtractiveSegmentCount: Option[Int] = None,
	  /** Specifies whether to return the confidence score from the extractive segments in each search result. This feature is available only for new or allowlisted data stores. To allowlist your data store, contact your Customer Engineer. The default value is `false`. */
		returnExtractiveSegmentScore: Option[Boolean] = None,
	  /** Specifies whether to also include the adjacent from each selected segments. Return at most `num_previous_segments` segments before each selected segments. */
		numPreviousSegments: Option[Int] = None,
	  /** Return at most `num_next_segments` segments after each selected segments. */
		numNextSegments: Option[Int] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaSearchRequestContentSearchSpecChunkSpec(
	  /** The number of previous chunks to be returned of the current chunk. The maximum allowed value is 3. If not specified, no previous chunks will be returned. */
		numPreviousChunks: Option[Int] = None,
	  /** The number of next chunks to be returned of the current chunk. The maximum allowed value is 3. If not specified, no next chunks will be returned. */
		numNextChunks: Option[Int] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaSearchRequestEmbeddingSpec(
	  /** The embedding vector used for retrieval. Limit to 1. */
		embeddingVectors: Option[List[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestEmbeddingSpecEmbeddingVector]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaSearchRequestEmbeddingSpecEmbeddingVector(
	  /** Embedding field path in schema. */
		fieldPath: Option[String] = None,
	  /** Query embedding vector. */
		vector: Option[List[BigDecimal]] = None
	)
	
	object GoogleCloudDiscoveryengineV1betaSearchRequestNaturalLanguageQueryUnderstandingSpec {
		enum FilterExtractionConditionEnum extends Enum[FilterExtractionConditionEnum] { case CONDITION_UNSPECIFIED, DISABLED, ENABLED }
	}
	case class GoogleCloudDiscoveryengineV1betaSearchRequestNaturalLanguageQueryUnderstandingSpec(
	  /** The condition under which filter extraction should occur. Default to Condition.DISABLED. */
		filterExtractionCondition: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestNaturalLanguageQueryUnderstandingSpec.FilterExtractionConditionEnum] = None,
	  /** Field names used for location-based filtering, where geolocation filters are detected in natural language search queries. Only valid when the FilterExtractionCondition is set to `ENABLED`. If this field is set, it overrides the field names set in ServingConfig.geo_search_query_detection_field_names. */
		geoSearchQueryDetectionFieldNames: Option[List[String]] = None
	)
	
	object GoogleCloudDiscoveryengineV1betaSearchRequestSearchAsYouTypeSpec {
		enum ConditionEnum extends Enum[ConditionEnum] { case CONDITION_UNSPECIFIED, DISABLED, ENABLED }
	}
	case class GoogleCloudDiscoveryengineV1betaSearchRequestSearchAsYouTypeSpec(
	  /** The condition under which search as you type should occur. Default to Condition.DISABLED. */
		condition: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestSearchAsYouTypeSpec.ConditionEnum] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaSearchRequestSessionSpec(
	  /** If set, the search result gets stored to the "turn" specified by this query ID. Example: Let's say the session looks like this: session { name: ".../sessions/xxx" turns { query { text: "What is foo?" query_id: ".../questions/yyy" } answer: "Foo is ..." } turns { query { text: "How about bar then?" query_id: ".../questions/zzz" } } } The user can call /search API with a request like this: session: ".../sessions/xxx" session_spec { query_id: ".../questions/zzz" } Then, the API stores the search result, associated with the last turn. The stored search result can be used by a subsequent /answer API call (with the session ID and the query ID specified). Also, it is possible to call /search and /answer in parallel with the same session ID & query ID. */
		queryId: Option[String] = None,
	  /** The number of top search results to persist. The persisted search results can be used for the subsequent /answer api call. This field is simliar to the `summary_result_count` field in SearchRequest.ContentSearchSpec.SummarySpec.summary_result_count. At most 10 results for documents mode, or 50 for chunks mode. */
		searchResultPersistenceCount: Option[Int] = None
	)
	
	object GoogleCloudDiscoveryengineV1betaSearchRequestPersonalizationSpec {
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, AUTO, DISABLED }
	}
	case class GoogleCloudDiscoveryengineV1betaSearchRequestPersonalizationSpec(
	  /** The personalization mode of the search request. Defaults to Mode.AUTO. */
		mode: Option[Schema.GoogleCloudDiscoveryengineV1betaSearchRequestPersonalizationSpec.ModeEnum] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaEvaluationEvaluationSpecQuerySetSpec(
	  /** Required. The full resource name of the SampleQuerySet used for the evaluation, in the format of `projects/{project}/locations/{location}/sampleQuerySets/{sampleQuerySet}`. */
		sampleQuerySet: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaQualityMetrics(
	  /** Recall per document, at various top-k cutoff levels. Recall is the fraction of relevant documents retrieved out of all relevant documents. Example (top-5): &#42; For a single SampleQuery, If 3 out of 5 relevant documents are retrieved in the top-5, recall@5 = 3/5 = 0.6 */
		docRecall: Option[Schema.GoogleCloudDiscoveryengineV1betaQualityMetricsTopkMetrics] = None,
	  /** Precision per document, at various top-k cutoff levels. Precision is the fraction of retrieved documents that are relevant. Example (top-5): &#42; For a single SampleQuery, If 4 out of 5 retrieved documents in the top-5 are relevant, precision@5 = 4/5 = 0.8 */
		docPrecision: Option[Schema.GoogleCloudDiscoveryengineV1betaQualityMetricsTopkMetrics] = None,
	  /** Normalized discounted cumulative gain (NDCG) per document, at various top-k cutoff levels. NDCG measures the ranking quality, giving higher relevance to top results. Example (top-3): Suppose SampleQuery with three retrieved documents (D1, D2, D3) and binary relevance judgements (1 for relevant, 0 for not relevant): Retrieved: [D3 (0), D1 (1), D2 (1)] Ideal: [D1 (1), D2 (1), D3 (0)] Calculate NDCG@3 for each SampleQuery: &#42; DCG@3: 0/log2(1+1) + 1/log2(2+1) + 1/log2(3+1) = 1.13 &#42; Ideal DCG@3: 1/log2(1+1) + 1/log2(2+1) + 0/log2(3+1) = 1.63 &#42; NDCG@3: 1.13/1.63 = 0.693 */
		docNdcg: Option[Schema.GoogleCloudDiscoveryengineV1betaQualityMetricsTopkMetrics] = None,
	  /** Recall per page, at various top-k cutoff levels. Recall is the fraction of relevant pages retrieved out of all relevant pages. Example (top-5): &#42; For a single SampleQuery, if 3 out of 5 relevant pages are retrieved in the top-5, recall@5 = 3/5 = 0.6 */
		pageRecall: Option[Schema.GoogleCloudDiscoveryengineV1betaQualityMetricsTopkMetrics] = None,
	  /** Normalized discounted cumulative gain (NDCG) per page, at various top-k cutoff levels. NDCG measures the ranking quality, giving higher relevance to top results. Example (top-3): Suppose SampleQuery with three retrieved pages (P1, P2, P3) and binary relevance judgements (1 for relevant, 0 for not relevant): Retrieved: [P3 (0), P1 (1), P2 (1)] Ideal: [P1 (1), P2 (1), P3 (0)] Calculate NDCG@3 for SampleQuery: &#42; DCG@3: 0/log2(1+1) + 1/log2(2+1) + 1/log2(3+1) = 1.13 &#42; Ideal DCG@3: 1/log2(1+1) + 1/log2(2+1) + 0/log2(3+1) = 1.63 &#42; NDCG@3: 1.13/1.63 = 0.693 */
		pageNdcg: Option[Schema.GoogleCloudDiscoveryengineV1betaQualityMetricsTopkMetrics] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaQualityMetricsTopkMetrics(
	  /** The top-1 value. */
		top1: Option[BigDecimal] = None,
	  /** The top-3 value. */
		top3: Option[BigDecimal] = None,
	  /** The top-5 value. */
		top5: Option[BigDecimal] = None,
	  /** The top-10 value. */
		top10: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaFetchSitemapsResponse(
	  /** List of Sitemaps fetched. */
		sitemapsMetadata: Option[List[Schema.GoogleCloudDiscoveryengineV1betaFetchSitemapsResponseSitemapMetadata]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaFetchSitemapsResponseSitemapMetadata(
	  /** The Sitemap. */
		sitemap: Option[Schema.GoogleCloudDiscoveryengineV1betaSitemap] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaSitemap(
	  /** Public URI for the sitemap, e.g. `www.example.com/sitemap.xml`. */
		uri: Option[String] = None,
	  /** Output only. The fully qualified resource name of the sitemap. `projects/&#42;/locations/&#42;/collections/&#42;/dataStores/&#42;/siteSearchEngine/sitemaps/&#42;` The `sitemap_id` suffix is system-generated. */
		name: Option[String] = None,
	  /** Output only. The sitemap's creation time. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaImportCompletionSuggestionsMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of CompletionSuggestions successfully imported. */
		successCount: Option[String] = None,
	  /** Count of CompletionSuggestions that failed to be imported. */
		failureCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaImportCompletionSuggestionsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** The desired location of errors incurred during the Import. */
		errorConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaImportErrorConfig] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaImportErrorConfig(
	  /** Cloud Storage prefix for import errors. This must be an empty, existing Cloud Storage directory. Import errors are written to sharded files in this directory, one per line, as a JSON-encoded `google.rpc.Status` message. */
		gcsPrefix: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaImportDocumentsMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of entries that were processed successfully. */
		successCount: Option[String] = None,
	  /** Count of entries that encountered errors while processing. */
		failureCount: Option[String] = None,
	  /** Total count of entries that were processed. */
		totalCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaImportDocumentsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Echoes the destination for the complete errors in the request if set. */
		errorConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaImportErrorConfig] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaImportSampleQueriesMetadata(
	  /** ImportSampleQueries operation create time. */
		createTime: Option[String] = None,
	  /** ImportSampleQueries operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of SampleQuerys successfully imported. */
		successCount: Option[String] = None,
	  /** Count of SampleQuerys that failed to be imported. */
		failureCount: Option[String] = None,
	  /** Total count of SampleQuerys that were processed. */
		totalCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaImportSampleQueriesResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** The desired location of errors incurred during the Import. */
		errorConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaImportErrorConfig] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaImportSuggestionDenyListEntriesMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaImportSuggestionDenyListEntriesResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Count of deny list entries successfully imported. */
		importedEntriesCount: Option[String] = None,
	  /** Count of deny list entries that failed to be imported. */
		failedEntriesCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaImportUserEventsMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of entries that were processed successfully. */
		successCount: Option[String] = None,
	  /** Count of entries that encountered errors while processing. */
		failureCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaImportUserEventsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Echoes the destination for the complete errors if this field was set in the request. */
		errorConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaImportErrorConfig] = None,
	  /** Count of user events imported with complete existing Documents. */
		joinedEventsCount: Option[String] = None,
	  /** Count of user events imported, but with Document information not found in the existing Branch. */
		unjoinedEventsCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaListCustomModelsResponse(
	  /** List of custom tuning models. */
		models: Option[List[Schema.GoogleCloudDiscoveryengineV1betaCustomTuningModel]] = None
	)
	
	object GoogleCloudDiscoveryengineV1betaCustomTuningModel {
		enum ModelStateEnum extends Enum[ModelStateEnum] { case MODEL_STATE_UNSPECIFIED, TRAINING_PAUSED, TRAINING, TRAINING_COMPLETE, READY_FOR_SERVING, TRAINING_FAILED, NO_IMPROVEMENT, INPUT_VALIDATION_FAILED }
	}
	case class GoogleCloudDiscoveryengineV1betaCustomTuningModel(
	  /** Required. The fully qualified resource name of the model. Format: `projects/{project}/locations/{location}/collections/{collection}/dataStores/{data_store}/customTuningModels/{custom_tuning_model}`. Model must be an alpha-numerical string with limit of 40 characters. */
		name: Option[String] = None,
	  /** The display name of the model. */
		displayName: Option[String] = None,
	  /** The version of the model. */
		modelVersion: Option[String] = None,
	  /** The state that the model is in (e.g.`TRAINING` or `TRAINING_FAILED`). */
		modelState: Option[Schema.GoogleCloudDiscoveryengineV1betaCustomTuningModel.ModelStateEnum] = None,
	  /** Deprecated: Timestamp the Model was created at. */
		createTime: Option[String] = None,
	  /** Timestamp the model training was initiated. */
		trainingStartTime: Option[String] = None,
	  /** The metrics of the trained model. */
		metrics: Option[Map[String, BigDecimal]] = None,
	  /** Currently this is only populated if the model state is `INPUT_VALIDATION_FAILED`. */
		errorMessage: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaProject(
	  /** Output only. Full resource name of the project, for example `projects/{project}`. Note that when making requests, project number and project id are both acceptable, but the server will always respond in project number. */
		name: Option[String] = None,
	  /** Output only. The timestamp when this project is created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when this project is successfully provisioned. Empty value means this project is still provisioning and is not ready for use. */
		provisionCompletionTime: Option[String] = None,
	  /** Output only. A map of terms of services. The key is the `id` of ServiceTerms. */
		serviceTermsMap: Option[Map[String, Schema.GoogleCloudDiscoveryengineV1betaProjectServiceTerms]] = None
	)
	
	object GoogleCloudDiscoveryengineV1betaProjectServiceTerms {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, TERMS_ACCEPTED, TERMS_PENDING, TERMS_DECLINED }
	}
	case class GoogleCloudDiscoveryengineV1betaProjectServiceTerms(
	  /** The unique identifier of this terms of service. Available terms: &#42; `GA_DATA_USE_TERMS`: [Terms for data use](https://cloud.google.com/retail/data-use-terms). When using this as `id`, the acceptable version to provide is `2022-11-23`. */
		id: Option[String] = None,
	  /** The version string of the terms of service. For acceptable values, see the comments for id above. */
		version: Option[String] = None,
	  /** Whether the project has accepted/rejected the service terms or it is still pending. */
		state: Option[Schema.GoogleCloudDiscoveryengineV1betaProjectServiceTerms.StateEnum] = None,
	  /** The last time when the project agreed to the terms of service. */
		acceptTime: Option[String] = None,
	  /** The last time when the project declined or revoked the agreement to terms of service. */
		declineTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaProvisionProjectMetadata(
	
	)
	
	case class GoogleCloudDiscoveryengineV1betaPurgeDocumentsMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of entries that were deleted successfully. */
		successCount: Option[String] = None,
	  /** Count of entries that encountered errors while processing. */
		failureCount: Option[String] = None,
	  /** Count of entries that were ignored as entries were not found. */
		ignoredCount: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaPurgeDocumentsResponse(
	  /** The total count of documents purged as a result of the operation. */
		purgeCount: Option[String] = None,
	  /** A sample of document names that will be deleted. Only populated if `force` is set to false. A max of 100 names will be returned and the names are chosen at random. */
		purgeSample: Option[List[String]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaPurgeSuggestionDenyListEntriesMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaPurgeSuggestionDenyListEntriesResponse(
	  /** Number of suggestion deny list entries purged. */
		purgeCount: Option[String] = None,
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaTrainCustomModelMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaTrainCustomModelResponse(
	  /** A sample of errors encountered while processing the data. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Echoes the destination for the complete errors in the request if set. */
		errorConfig: Option[Schema.GoogleCloudDiscoveryengineV1betaImportErrorConfig] = None,
	  /** The trained model status. Possible values are: &#42; &#42;&#42;bad-data&#42;&#42;: The training data quality is bad. &#42; &#42;&#42;no-improvement&#42;&#42;: Tuning didn't improve performance. Won't deploy. &#42; &#42;&#42;in-progress&#42;&#42;: Model training job creation is in progress. &#42; &#42;&#42;training&#42;&#42;: Model is actively training. &#42; &#42;&#42;evaluating&#42;&#42;: The model is evaluating trained metrics. &#42; &#42;&#42;indexing&#42;&#42;: The model trained metrics are indexing. &#42; &#42;&#42;ready&#42;&#42;: The model is ready for serving. */
		modelStatus: Option[String] = None,
	  /** The metrics of the trained model. */
		metrics: Option[Map[String, BigDecimal]] = None,
	  /** Fully qualified name of the CustomTuningModel. */
		modelName: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaTuneEngineMetadata(
	  /** Required. The resource name of the engine that this tune applies to. Format: `projects/{project}/locations/{location}/collections/{collection_id}/engines/{engine_id}` */
		engine: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaTuneEngineResponse(
	
	)
	
	case class GoogleCloudDiscoveryengineV1betaUpdateSchemaMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDiscoveryengineV1betaUpdateTargetSiteMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
}
