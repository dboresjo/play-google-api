package com.boresjo.play.api.google.retail

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

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
	
	case class GoogleCloudRetailV2ExportAnalyticsMetricsRequest(
	  /** Required. The output location of the data. */
		outputConfig: Option[Schema.GoogleCloudRetailV2OutputConfig] = None,
	  /** A filtering expression to specify restrictions on returned metrics. The expression is a sequence of terms. Each term applies a restriction to the returned metrics. Use this expression to restrict results to a specific time range. Currently we expect only one types of fields: &#42; `timestamp`: This can be specified twice, once with a less than operator and once with a greater than operator. The `timestamp` restriction should result in one, contiguous, valid, `timestamp` range. Some examples of valid filters expressions: &#42; Example 1: `timestamp > "2012-04-23T18:25:43.511Z" timestamp < "2012-04-23T18:30:43.511Z"` &#42; Example 2: `timestamp > "2012-04-23T18:25:43.511Z"` */
		filter: Option[String] = None
	)
	
	case class GoogleCloudRetailV2OutputConfig(
	  /** The Google Cloud Storage location where the output is to be written to. */
		gcsDestination: Option[Schema.GoogleCloudRetailV2OutputConfigGcsDestination] = None,
	  /** The BigQuery location where the output is to be written to. */
		bigqueryDestination: Option[Schema.GoogleCloudRetailV2OutputConfigBigQueryDestination] = None
	)
	
	case class GoogleCloudRetailV2OutputConfigGcsDestination(
	  /** Required. The output uri prefix for saving output data to json files. Some mapping examples are as follows: output_uri_prefix sample output(assuming the object is foo.json) ======================== ============================================= gs://bucket/ gs://bucket/foo.json gs://bucket/folder/ gs://bucket/folder/foo.json gs://bucket/folder/item_ gs://bucket/folder/item_foo.json */
		outputUriPrefix: Option[String] = None
	)
	
	case class GoogleCloudRetailV2OutputConfigBigQueryDestination(
	  /** Required. The ID of a BigQuery Dataset. */
		datasetId: Option[String] = None,
	  /** Required. The prefix of exported BigQuery tables. */
		tableIdPrefix: Option[String] = None,
	  /** Required. Describes the table type. The following values are supported: &#42; `table`: A BigQuery native table. &#42; `view`: A virtual table defined by a SQL query. */
		tableType: Option[String] = None
	)
	
	case class GoogleCloudRetailV2ListCatalogsResponse(
	  /** All the customer's Catalogs. */
		catalogs: Option[List[Schema.GoogleCloudRetailV2Catalog]] = None,
	  /** A token that can be sent as ListCatalogsRequest.page_token to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudRetailV2Catalog(
	  /** Required. Immutable. The fully qualified resource name of the catalog. */
		name: Option[String] = None,
	  /** Required. Immutable. The catalog display name. This field must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an INVALID_ARGUMENT error is returned. */
		displayName: Option[String] = None,
	  /** Required. The product level configuration. */
		productLevelConfig: Option[Schema.GoogleCloudRetailV2ProductLevelConfig] = None
	)
	
	case class GoogleCloudRetailV2ProductLevelConfig(
	  /** The type of Products allowed to be ingested into the catalog. Acceptable values are: &#42; `primary` (default): You can ingest Products of all types. When ingesting a Product, its type will default to Product.Type.PRIMARY if unset. &#42; `variant` (incompatible with Retail Search): You can only ingest Product.Type.VARIANT Products. This means Product.primary_product_id cannot be empty. If this field is set to an invalid value other than these, an INVALID_ARGUMENT error is returned. If this field is `variant` and merchant_center_product_id_field is `itemGroupId`, an INVALID_ARGUMENT error is returned. See [Product levels](https://cloud.google.com/retail/docs/catalog#product-levels) for more details. */
		ingestionProductType: Option[String] = None,
	  /** Which field of [Merchant Center Product](/bigquery-transfer/docs/merchant-center-products-schema) should be imported as Product.id. Acceptable values are: &#42; `offerId` (default): Import `offerId` as the product ID. &#42; `itemGroupId`: Import `itemGroupId` as the product ID. Notice that Retail API will choose one item from the ones with the same `itemGroupId`, and use it to represent the item group. If this field is set to an invalid value other than these, an INVALID_ARGUMENT error is returned. If this field is `itemGroupId` and ingestion_product_type is `variant`, an INVALID_ARGUMENT error is returned. See [Product levels](https://cloud.google.com/retail/docs/catalog#product-levels) for more details. */
		merchantCenterProductIdField: Option[String] = None
	)
	
	case class GoogleCloudRetailV2SetDefaultBranchRequest(
	  /** The final component of the resource name of a branch. This field must be one of "0", "1" or "2". Otherwise, an INVALID_ARGUMENT error is returned. If there are no sufficient active products in the targeted branch and force is not set, a FAILED_PRECONDITION error is returned. */
		branchId: Option[String] = None,
	  /** Some note on this request, this can be retrieved by CatalogService.GetDefaultBranch before next valid default branch set occurs. This field must be a UTF-8 encoded string with a length limit of 1,000 characters. Otherwise, an INVALID_ARGUMENT error is returned. */
		note: Option[String] = None,
	  /** If set to true, it permits switching to a branch with branch_id even if it has no sufficient active products. */
		force: Option[Boolean] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleCloudRetailV2GetDefaultBranchResponse(
	  /** Full resource name of the branch id currently set as default branch. */
		branch: Option[String] = None,
	  /** The time when this branch is set to default. */
		setTime: Option[String] = None,
	  /** This corresponds to SetDefaultBranchRequest.note field, when this branch was set as default. */
		note: Option[String] = None
	)
	
	case class GoogleCloudRetailV2CompletionConfig(
	  /** Required. Immutable. Fully qualified name `projects/&#42;/locations/&#42;/catalogs/&#42;/completionConfig` */
		name: Option[String] = None,
	  /** Specifies the matching order for autocomplete suggestions, e.g., a query consisting of 'sh' with 'out-of-order' specified would suggest "women's shoes", whereas a query of 'red s' with 'exact-prefix' specified would suggest "red shoes". Currently supported values: &#42; 'out-of-order' &#42; 'exact-prefix' Default value: 'exact-prefix'. */
		matchingOrder: Option[String] = None,
	  /** The maximum number of autocomplete suggestions returned per term. Default value is 20. If left unset or set to 0, then will fallback to default value. Value range is 1 to 20. */
		maxSuggestions: Option[Int] = None,
	  /** The minimum number of characters needed to be typed in order to get suggestions. Default value is 2. If left unset or set to 0, then will fallback to default value. Value range is 1 to 20. */
		minPrefixLength: Option[Int] = None,
	  /** If set to true, the auto learning function is enabled. Auto learning uses user data to generate suggestions using ML techniques. Default value is false. Only after enabling auto learning can users use `cloud-retail` data in CompleteQueryRequest. */
		autoLearning: Option[Boolean] = None,
	  /** Output only. The source data for the latest import of the autocomplete suggestion phrases. */
		suggestionsInputConfig: Option[Schema.GoogleCloudRetailV2CompletionDataInputConfig] = None,
	  /** Output only. Name of the LRO corresponding to the latest suggestion terms list import. Can use GetOperation API method to retrieve the latest state of the Long Running Operation. */
		lastSuggestionsImportOperation: Option[String] = None,
	  /** Output only. The source data for the latest import of the autocomplete denylist phrases. */
		denylistInputConfig: Option[Schema.GoogleCloudRetailV2CompletionDataInputConfig] = None,
	  /** Output only. Name of the LRO corresponding to the latest denylist import. Can use GetOperation API to retrieve the latest state of the Long Running Operation. */
		lastDenylistImportOperation: Option[String] = None,
	  /** Output only. The source data for the latest import of the autocomplete allowlist phrases. */
		allowlistInputConfig: Option[Schema.GoogleCloudRetailV2CompletionDataInputConfig] = None,
	  /** Output only. Name of the LRO corresponding to the latest allowlist import. Can use GetOperation API to retrieve the latest state of the Long Running Operation. */
		lastAllowlistImportOperation: Option[String] = None
	)
	
	case class GoogleCloudRetailV2CompletionDataInputConfig(
	  /** Required. BigQuery input source. Add the IAM permission "BigQuery Data Viewer" for cloud-retail-customer-data-access@system.gserviceaccount.com before using this feature otherwise an error is thrown. */
		bigQuerySource: Option[Schema.GoogleCloudRetailV2BigQuerySource] = None
	)
	
	case class GoogleCloudRetailV2BigQuerySource(
	  /** BigQuery time partitioned table's _PARTITIONDATE in YYYY-MM-DD format. Only supported in ImportProductsRequest. */
		partitionDate: Option[Schema.GoogleTypeDate] = None,
	  /** The project ID (can be project # or ID) that the BigQuery source is in with a length limit of 128 characters. If not specified, inherits the project ID from the parent request. */
		projectId: Option[String] = None,
	  /** Required. The BigQuery data set to copy the data from with a length limit of 1,024 characters. */
		datasetId: Option[String] = None,
	  /** Required. The BigQuery table to copy the data from with a length limit of 1,024 characters. */
		tableId: Option[String] = None,
	  /** Intermediate Cloud Storage directory used for the import with a length limit of 2,000 characters. Can be specified if one wants to have the BigQuery export to a specific Cloud Storage directory. */
		gcsStagingDir: Option[String] = None,
	  /** The schema to use when parsing the data from the source. Supported values for product imports: &#42; `product` (default): One JSON Product per line. Each product must have a valid Product.id. &#42; `product_merchant_center`: See [Importing catalog data from Merchant Center](https://cloud.google.com/retail/recommendations-ai/docs/upload-catalog#mc). Supported values for user events imports: &#42; `user_event` (default): One JSON UserEvent per line. &#42; `user_event_ga360`: The schema is available here: https://support.google.com/analytics/answer/3437719. &#42; `user_event_ga4`: The schema is available here: https://support.google.com/analytics/answer/7029846. Supported values for autocomplete imports: &#42; `suggestions` (default): One JSON completion suggestion per line. &#42; `denylist`: One JSON deny suggestion per line. &#42; `allowlist`: One JSON allow suggestion per line. */
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
	
	object GoogleCloudRetailV2AttributesConfig {
		enum AttributeConfigLevelEnum extends Enum[AttributeConfigLevelEnum] { case ATTRIBUTE_CONFIG_LEVEL_UNSPECIFIED, PRODUCT_LEVEL_ATTRIBUTE_CONFIG, CATALOG_LEVEL_ATTRIBUTE_CONFIG }
	}
	case class GoogleCloudRetailV2AttributesConfig(
	  /** Required. Immutable. The fully qualified resource name of the attribute config. Format: `projects/&#42;/locations/&#42;/catalogs/&#42;/attributesConfig` */
		name: Option[String] = None,
	  /** Enable attribute(s) config at catalog level. For example, indexable, dynamic_facetable, or searchable for each attribute. The key is catalog attribute's name. For example: `color`, `brands`, `attributes.custom_attribute`, such as `attributes.xyz`. The maximum number of catalog attributes allowed in a request is 1000. */
		catalogAttributes: Option[Map[String, Schema.GoogleCloudRetailV2CatalogAttribute]] = None,
	  /** Output only. The AttributeConfigLevel used for this catalog. */
		attributeConfigLevel: Option[Schema.GoogleCloudRetailV2AttributesConfig.AttributeConfigLevelEnum] = None
	)
	
	object GoogleCloudRetailV2CatalogAttribute {
		enum TypeEnum extends Enum[TypeEnum] { case UNKNOWN, TEXTUAL, NUMERICAL }
		enum IndexableOptionEnum extends Enum[IndexableOptionEnum] { case INDEXABLE_OPTION_UNSPECIFIED, INDEXABLE_ENABLED, INDEXABLE_DISABLED }
		enum DynamicFacetableOptionEnum extends Enum[DynamicFacetableOptionEnum] { case DYNAMIC_FACETABLE_OPTION_UNSPECIFIED, DYNAMIC_FACETABLE_ENABLED, DYNAMIC_FACETABLE_DISABLED }
		enum SearchableOptionEnum extends Enum[SearchableOptionEnum] { case SEARCHABLE_OPTION_UNSPECIFIED, SEARCHABLE_ENABLED, SEARCHABLE_DISABLED }
		enum ExactSearchableOptionEnum extends Enum[ExactSearchableOptionEnum] { case EXACT_SEARCHABLE_OPTION_UNSPECIFIED, EXACT_SEARCHABLE_ENABLED, EXACT_SEARCHABLE_DISABLED }
		enum RetrievableOptionEnum extends Enum[RetrievableOptionEnum] { case RETRIEVABLE_OPTION_UNSPECIFIED, RETRIEVABLE_ENABLED, RETRIEVABLE_DISABLED }
	}
	case class GoogleCloudRetailV2CatalogAttribute(
	  /** Required. Attribute name. For example: `color`, `brands`, `attributes.custom_attribute`, such as `attributes.xyz`. To be indexable, the attribute name can contain only alpha-numeric characters and underscores. For example, an attribute named `attributes.abc_xyz` can be indexed, but an attribute named `attributes.abc-xyz` cannot be indexed. If the attribute key starts with `attributes.`, then the attribute is a custom attribute. Attributes such as `brands`, `patterns`, and `title` are built-in and called system attributes. */
		key: Option[String] = None,
	  /** Output only. Indicates whether this attribute has been used by any products. `True` if at least one Product is using this attribute in Product.attributes. Otherwise, this field is `False`. CatalogAttribute can be pre-loaded by using CatalogService.AddCatalogAttribute, CatalogService.ImportCatalogAttributes, or CatalogService.UpdateAttributesConfig APIs. This field is `False` for pre-loaded CatalogAttributes. Only pre-loaded catalog attributes that are neither in use by products nor predefined can be deleted. Catalog attributes that are either in use by products or are predefined attributes cannot be deleted; however, their configuration properties will reset to default values upon removal request. After catalog changes, it takes about 10 minutes for this field to update. */
		inUse: Option[Boolean] = None,
	  /** Output only. The type of this attribute. This is derived from the attribute in Product.attributes. */
		`type`: Option[Schema.GoogleCloudRetailV2CatalogAttribute.TypeEnum] = None,
	  /** When AttributesConfig.attribute_config_level is CATALOG_LEVEL_ATTRIBUTE_CONFIG, if INDEXABLE_ENABLED attribute values are indexed so that it can be filtered, faceted, or boosted in SearchService.Search. Must be specified when AttributesConfig.attribute_config_level is CATALOG_LEVEL_ATTRIBUTE_CONFIG, otherwise throws INVALID_FORMAT error. */
		indexableOption: Option[Schema.GoogleCloudRetailV2CatalogAttribute.IndexableOptionEnum] = None,
	  /** If DYNAMIC_FACETABLE_ENABLED, attribute values are available for dynamic facet. Could only be DYNAMIC_FACETABLE_DISABLED if CatalogAttribute.indexable_option is INDEXABLE_DISABLED. Otherwise, an INVALID_ARGUMENT error is returned. Must be specified, otherwise throws INVALID_FORMAT error. */
		dynamicFacetableOption: Option[Schema.GoogleCloudRetailV2CatalogAttribute.DynamicFacetableOptionEnum] = None,
	  /** When AttributesConfig.attribute_config_level is CATALOG_LEVEL_ATTRIBUTE_CONFIG, if SEARCHABLE_ENABLED, attribute values are searchable by text queries in SearchService.Search. If SEARCHABLE_ENABLED but attribute type is numerical, attribute values will not be searchable by text queries in SearchService.Search, as there are no text values associated to numerical attributes. Must be specified, when AttributesConfig.attribute_config_level is CATALOG_LEVEL_ATTRIBUTE_CONFIG, otherwise throws INVALID_FORMAT error. */
		searchableOption: Option[Schema.GoogleCloudRetailV2CatalogAttribute.SearchableOptionEnum] = None,
	  /** If EXACT_SEARCHABLE_ENABLED, attribute values will be exact searchable. This property only applies to textual custom attributes and requires indexable set to enabled to enable exact-searchable. If unset, the server behavior defaults to EXACT_SEARCHABLE_DISABLED. */
		exactSearchableOption: Option[Schema.GoogleCloudRetailV2CatalogAttribute.ExactSearchableOptionEnum] = None,
	  /** If RETRIEVABLE_ENABLED, attribute values are retrievable in the search results. If unset, the server behavior defaults to RETRIEVABLE_DISABLED. */
		retrievableOption: Option[Schema.GoogleCloudRetailV2CatalogAttribute.RetrievableOptionEnum] = None,
	  /** Contains facet options. */
		facetConfig: Option[Schema.GoogleCloudRetailV2CatalogAttributeFacetConfig] = None
	)
	
	case class GoogleCloudRetailV2CatalogAttributeFacetConfig(
	  /** If you don't set the facet SearchRequest.FacetSpec.FacetKey.intervals in the request to a numerical attribute, then we use the computed intervals with rounded bounds obtained from all its product numerical attribute values. The computed intervals might not be ideal for some attributes. Therefore, we give you the option to overwrite them with the facet_intervals field. The maximum of facet intervals per CatalogAttribute is 40. Each interval must have a lower bound or an upper bound. If both bounds are provided, then the lower bound must be smaller or equal than the upper bound. */
		facetIntervals: Option[List[Schema.GoogleCloudRetailV2Interval]] = None,
	  /** Each instance represents a list of attribute values to ignore as facet values for a specific time range. The maximum number of instances per CatalogAttribute is 25. */
		ignoredFacetValues: Option[List[Schema.GoogleCloudRetailV2CatalogAttributeFacetConfigIgnoredFacetValues]] = None,
	  /** Each instance replaces a list of facet values by a merged facet value. If a facet value is not in any list, then it will stay the same. To avoid conflicts, only paths of length 1 are accepted. In other words, if "dark_blue" merged into "BLUE", then the latter can't merge into "blues" because this would create a path of length 2. The maximum number of instances of MergedFacetValue per CatalogAttribute is 100. This feature is available only for textual custom attributes. */
		mergedFacetValues: Option[List[Schema.GoogleCloudRetailV2CatalogAttributeFacetConfigMergedFacetValue]] = None,
	  /** Use this field only if you want to merge a facet key into another facet key. */
		mergedFacet: Option[Schema.GoogleCloudRetailV2CatalogAttributeFacetConfigMergedFacet] = None,
	  /** Set this field only if you want to rerank based on facet values engaged by the user for the current key. This option is only possible for custom facetable textual keys. */
		rerankConfig: Option[Schema.GoogleCloudRetailV2CatalogAttributeFacetConfigRerankConfig] = None
	)
	
	case class GoogleCloudRetailV2Interval(
	  /** Inclusive lower bound. */
		minimum: Option[BigDecimal] = None,
	  /** Exclusive lower bound. */
		exclusiveMinimum: Option[BigDecimal] = None,
	  /** Inclusive upper bound. */
		maximum: Option[BigDecimal] = None,
	  /** Exclusive upper bound. */
		exclusiveMaximum: Option[BigDecimal] = None
	)
	
	case class GoogleCloudRetailV2CatalogAttributeFacetConfigIgnoredFacetValues(
	  /** List of facet values to ignore for the following time range. The facet values are the same as the attribute values. There is a limit of 10 values per instance of IgnoredFacetValues. Each value can have at most 128 characters. */
		values: Option[List[String]] = None,
	  /** Time range for the current list of facet values to ignore. If multiple time ranges are specified for an facet value for the current attribute, consider all of them. If both are empty, ignore always. If start time and end time are set, then start time must be before end time. If start time is not empty and end time is empty, then will ignore these facet values after the start time. */
		startTime: Option[String] = None,
	  /** If start time is empty and end time is not empty, then ignore these facet values before end time. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudRetailV2CatalogAttributeFacetConfigMergedFacetValue(
	  /** All the facet values that are replaces by the same merged_value that follows. The maximum number of values per MergedFacetValue is 25. Each value can have up to 128 characters. */
		values: Option[List[String]] = None,
	  /** All the previous values are replaced by this merged facet value. This merged_value must be non-empty and can have up to 128 characters. */
		mergedValue: Option[String] = None
	)
	
	case class GoogleCloudRetailV2CatalogAttributeFacetConfigMergedFacet(
	  /** The merged facet key should be a valid facet key that is different than the facet key of the current catalog attribute. We refer this is merged facet key as the child of the current catalog attribute. This merged facet key can't be a parent of another facet key (i.e. no directed path of length 2). This merged facet key needs to be either a textual custom attribute or a numerical custom attribute. */
		mergedFacetKey: Option[String] = None
	)
	
	case class GoogleCloudRetailV2CatalogAttributeFacetConfigRerankConfig(
	  /** If set to true, then we also rerank the dynamic facets based on the facet values engaged by the user for the current attribute key during serving. */
		rerankFacet: Option[Boolean] = None,
	  /** If empty, rerank on all facet values for the current key. Otherwise, will rerank on the facet values from this list only. */
		facetValues: Option[List[String]] = None
	)
	
	case class GoogleCloudRetailV2AddCatalogAttributeRequest(
	  /** Required. The CatalogAttribute to add. */
		catalogAttribute: Option[Schema.GoogleCloudRetailV2CatalogAttribute] = None
	)
	
	case class GoogleCloudRetailV2RemoveCatalogAttributeRequest(
	  /** Required. The attribute name key of the CatalogAttribute to remove. */
		key: Option[String] = None
	)
	
	case class GoogleCloudRetailV2ReplaceCatalogAttributeRequest(
	  /** Required. The updated CatalogAttribute. */
		catalogAttribute: Option[Schema.GoogleCloudRetailV2CatalogAttribute] = None,
	  /** Indicates which fields in the provided CatalogAttribute to update. The following are NOT supported: &#42; CatalogAttribute.key If not set, all supported fields are updated. */
		updateMask: Option[String] = None
	)
	
	object GoogleCloudRetailV2SearchRequest {
		enum SearchModeEnum extends Enum[SearchModeEnum] { case SEARCH_MODE_UNSPECIFIED, PRODUCT_SEARCH_ONLY, FACETED_SEARCH_ONLY }
	}
	case class GoogleCloudRetailV2SearchRequest(
	  /** The branch resource name, such as `projects/&#42;/locations/global/catalogs/default_catalog/branches/0`. Use "default_branch" as the branch ID or leave this field empty, to search products under the default branch. */
		branch: Option[String] = None,
	  /** Raw search query. If this field is empty, the request is considered a category browsing request and returned results are based on filter and page_categories. */
		query: Option[String] = None,
	  /** Required. A unique identifier for tracking visitors. For example, this could be implemented with an HTTP cookie, which should be able to uniquely identify a visitor on a single device. This unique identifier should not change if the visitor logs in or out of the website. This should be the same identifier as UserEvent.visitor_id. The field must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an INVALID_ARGUMENT error is returned. */
		visitorId: Option[String] = None,
	  /** User information. */
		userInfo: Option[Schema.GoogleCloudRetailV2UserInfo] = None,
	  /** Maximum number of Products to return. If unspecified, defaults to a reasonable value. The maximum allowed value is 120. Values above 120 will be coerced to 120. If this field is negative, an INVALID_ARGUMENT is returned. */
		pageSize: Option[Int] = None,
	  /** A page token SearchResponse.next_page_token, received from a previous SearchService.Search call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to SearchService.Search must match the call that provided the page token. Otherwise, an INVALID_ARGUMENT error is returned. */
		pageToken: Option[String] = None,
	  /** A 0-indexed integer that specifies the current offset (that is, starting result location, amongst the Products deemed by the API as relevant) in search results. This field is only considered if page_token is unset. If this field is negative, an INVALID_ARGUMENT is returned. */
		offset: Option[Int] = None,
	  /** The filter syntax consists of an expression language for constructing a predicate from one or more fields of the products being filtered. Filter expression is case-sensitive. For more information, see [Filter](https://cloud.google.com/retail/docs/filter-and-order#filter). If this field is unrecognizable, an INVALID_ARGUMENT is returned. */
		filter: Option[String] = None,
	  /** The default filter that is applied when a user performs a search without checking any filters on the search page. The filter applied to every search request when quality improvement such as query expansion is needed. In the case a query does not have a sufficient amount of results this filter will be used to determine whether or not to enable the query expansion flow. The original filter will still be used for the query expanded search. This field is strongly recommended to achieve high search quality. For more information about filter syntax, see SearchRequest.filter. */
		canonicalFilter: Option[String] = None,
	  /** The order in which products are returned. Products can be ordered by a field in an Product object. Leave it unset if ordered by relevance. OrderBy expression is case-sensitive. For more information, see [Order](https://cloud.google.com/retail/docs/filter-and-order#order). If this field is unrecognizable, an INVALID_ARGUMENT is returned. */
		orderBy: Option[String] = None,
	  /** Facet specifications for faceted search. If empty, no facets are returned. A maximum of 200 values are allowed. Otherwise, an INVALID_ARGUMENT error is returned. */
		facetSpecs: Option[List[Schema.GoogleCloudRetailV2SearchRequestFacetSpec]] = None,
	  /** Deprecated. Refer to https://cloud.google.com/retail/docs/configs#dynamic to enable dynamic facets. Do not set this field. The specification for dynamically generated facets. Notice that only textual facets can be dynamically generated. */
		dynamicFacetSpec: Option[Schema.GoogleCloudRetailV2SearchRequestDynamicFacetSpec] = None,
	  /** Boost specification to boost certain products. For more information, see [Boost results](https://cloud.google.com/retail/docs/boosting). Notice that if both ServingConfig.boost_control_ids and SearchRequest.boost_spec are set, the boost conditions from both places are evaluated. If a search request matches multiple boost conditions, the final boost score is equal to the sum of the boost scores from all matched boost conditions. */
		boostSpec: Option[Schema.GoogleCloudRetailV2SearchRequestBoostSpec] = None,
	  /** The query expansion specification that specifies the conditions under which query expansion occurs. For more information, see [Query expansion](https://cloud.google.com/retail/docs/result-size#query_expansion). */
		queryExpansionSpec: Option[Schema.GoogleCloudRetailV2SearchRequestQueryExpansionSpec] = None,
	  /** The keys to fetch and rollup the matching variant Products attributes, FulfillmentInfo or LocalInventorys attributes. The attributes from all the matching variant Products or LocalInventorys are merged and de-duplicated. Notice that rollup attributes will lead to extra query latency. Maximum number of keys is 30. For FulfillmentInfo, a fulfillment type and a fulfillment ID must be provided in the format of "fulfillmentType.fulfillmentId". E.g., in "pickupInStore.store123", "pickupInStore" is fulfillment type and "store123" is the store ID. Supported keys are: &#42; colorFamilies &#42; price &#42; originalPrice &#42; discount &#42; variantId &#42; inventory(place_id,price) &#42; inventory(place_id,original_price) &#42; inventory(place_id,attributes.key), where key is any key in the Product.local_inventories.attributes map. &#42; attributes.key, where key is any key in the Product.attributes map. &#42; pickupInStore.id, where id is any FulfillmentInfo.place_ids for FulfillmentInfo.type "pickup-in-store". &#42; shipToStore.id, where id is any FulfillmentInfo.place_ids for FulfillmentInfo.type "ship-to-store". &#42; sameDayDelivery.id, where id is any FulfillmentInfo.place_ids for FulfillmentInfo.type "same-day-delivery". &#42; nextDayDelivery.id, where id is any FulfillmentInfo.place_ids for FulfillmentInfo.type "next-day-delivery". &#42; customFulfillment1.id, where id is any FulfillmentInfo.place_ids for FulfillmentInfo.type "custom-type-1". &#42; customFulfillment2.id, where id is any FulfillmentInfo.place_ids for FulfillmentInfo.type "custom-type-2". &#42; customFulfillment3.id, where id is any FulfillmentInfo.place_ids for FulfillmentInfo.type "custom-type-3". &#42; customFulfillment4.id, where id is any FulfillmentInfo.place_ids for FulfillmentInfo.type "custom-type-4". &#42; customFulfillment5.id, where id is any FulfillmentInfo.place_ids for FulfillmentInfo.type "custom-type-5". If this field is set to an invalid value other than these, an INVALID_ARGUMENT error is returned. */
		variantRollupKeys: Option[List[String]] = None,
	  /** The categories associated with a category page. Must be set for category navigation queries to achieve good search quality. The format should be the same as UserEvent.page_categories; To represent full path of category, use '>' sign to separate different hierarchies. If '>' is part of the category name, replace it with other character(s). Category pages include special pages such as sales or promotions. For instance, a special sale page may have the category hierarchy: "pageCategories" : ["Sales > 2017 Black Friday Deals"]. */
		pageCategories: Option[List[String]] = None,
	  /** The search mode of the search request. If not specified, a single search request triggers both product search and faceted search. */
		searchMode: Option[Schema.GoogleCloudRetailV2SearchRequest.SearchModeEnum] = None,
	  /** The specification for personalization. Notice that if both ServingConfig.personalization_spec and SearchRequest.personalization_spec are set. SearchRequest.personalization_spec will override ServingConfig.personalization_spec. */
		personalizationSpec: Option[Schema.GoogleCloudRetailV2SearchRequestPersonalizationSpec] = None,
	  /** The labels applied to a resource must meet the following requirements: &#42; Each resource can have multiple labels, up to a maximum of 64. &#42; Each label must be a key-value pair. &#42; Keys have a minimum length of 1 character and a maximum length of 63 characters and cannot be empty. Values can be empty and have a maximum length of 63 characters. &#42; Keys and values can contain only lowercase letters, numeric characters, underscores, and dashes. All characters must use UTF-8 encoding, and international characters are allowed. &#42; The key portion of a label must be unique. However, you can use the same key with multiple resources. &#42; Keys must start with a lowercase letter or international character. For more information, see [Requirements for labels](https://cloud.google.com/resource-manager/docs/creating-managing-labels#requirements) in the Resource Manager documentation. */
		labels: Option[Map[String, String]] = None,
	  /** The spell correction specification that specifies the mode under which spell correction will take effect. */
		spellCorrectionSpec: Option[Schema.GoogleCloudRetailV2SearchRequestSpellCorrectionSpec] = None,
	  /** The entity for customers that may run multiple different entities, domains, sites or regions, for example, `Google US`, `Google Ads`, `Waymo`, `google.com`, `youtube.com`, etc. If this is set, it should be exactly matched with UserEvent.entity to get search results boosted by entity. */
		entity: Option[String] = None,
	  /** Optional. This field specifies all conversational related parameters addition to traditional retail search. */
		conversationalSearchSpec: Option[Schema.GoogleCloudRetailV2SearchRequestConversationalSearchSpec] = None,
	  /** Optional. This field specifies tile navigation related parameters. */
		tileNavigationSpec: Option[Schema.GoogleCloudRetailV2SearchRequestTileNavigationSpec] = None
	)
	
	case class GoogleCloudRetailV2UserInfo(
	  /** Highly recommended for logged-in users. Unique identifier for logged-in user, such as a user name. Don't set for anonymous users. Always use a hashed value for this ID. Don't set the field to the same fixed ID for different users. This mixes the event history of those users together, which results in degraded model quality. The field must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an INVALID_ARGUMENT error is returned. */
		userId: Option[String] = None,
	  /** The end user's IP address. This field is used to extract location information for personalization. This field must be either an IPv4 address (e.g. "104.133.9.80") or an IPv6 address (e.g. "2001:0db8:85a3:0000:0000:8a2e:0370:7334"). Otherwise, an INVALID_ARGUMENT error is returned. This should not be set when: &#42; setting SearchRequest.user_info. &#42; using the JavaScript tag in UserEventService.CollectUserEvent or if direct_user_request is set. */
		ipAddress: Option[String] = None,
	  /** User agent as included in the HTTP header. Required for getting SearchResponse.sponsored_results. The field must be a UTF-8 encoded string with a length limit of 1,000 characters. Otherwise, an INVALID_ARGUMENT error is returned. This should not be set when using the client side event reporting with GTM or JavaScript tag in UserEventService.CollectUserEvent or if direct_user_request is set. */
		userAgent: Option[String] = None,
	  /** True if the request is made directly from the end user, in which case the ip_address and user_agent can be populated from the HTTP request. This flag should be set only if the API request is made directly from the end user such as a mobile app (and not if a gateway or a server is processing and pushing the user events). This should not be set when using the JavaScript tag in UserEventService.CollectUserEvent. */
		directUserRequest: Option[Boolean] = None
	)
	
	case class GoogleCloudRetailV2SearchRequestFacetSpec(
	  /** Required. The facet key specification. */
		facetKey: Option[Schema.GoogleCloudRetailV2SearchRequestFacetSpecFacetKey] = None,
	  /** Maximum of facet values that should be returned for this facet. If unspecified, defaults to 50. The maximum allowed value is 300. Values above 300 will be coerced to 300. If this field is negative, an INVALID_ARGUMENT is returned. */
		limit: Option[Int] = None,
	  /** List of keys to exclude when faceting. By default, FacetKey.key is not excluded from the filter unless it is listed in this field. Listing a facet key in this field allows its values to appear as facet results, even when they are filtered out of search results. Using this field does not affect what search results are returned. For example, suppose there are 100 products with the color facet "Red" and 200 products with the color facet "Blue". A query containing the filter "colorFamilies:ANY("Red")" and having "colorFamilies" as FacetKey.key would by default return only "Red" products in the search results, and also return "Red" with count 100 as the only color facet. Although there are also blue products available, "Blue" would not be shown as an available facet value. If "colorFamilies" is listed in "excludedFilterKeys", then the query returns the facet values "Red" with count 100 and "Blue" with count 200, because the "colorFamilies" key is now excluded from the filter. Because this field doesn't affect search results, the search results are still correctly filtered to return only "Red" products. A maximum of 100 values are allowed. Otherwise, an INVALID_ARGUMENT error is returned. */
		excludedFilterKeys: Option[List[String]] = None,
	  /** Enables dynamic position for this facet. If set to true, the position of this facet among all facets in the response is determined by Google Retail Search. It is ordered together with dynamic facets if dynamic facets is enabled. If set to false, the position of this facet in the response is the same as in the request, and it is ranked before the facets with dynamic position enable and all dynamic facets. For example, you may always want to have rating facet returned in the response, but it's not necessarily to always display the rating facet at the top. In that case, you can set enable_dynamic_position to true so that the position of rating facet in response is determined by Google Retail Search. Another example, assuming you have the following facets in the request: &#42; "rating", enable_dynamic_position = true &#42; "price", enable_dynamic_position = false &#42; "brands", enable_dynamic_position = false And also you have a dynamic facets enable, which generates a facet "gender". Then, the final order of the facets in the response can be ("price", "brands", "rating", "gender") or ("price", "brands", "gender", "rating") depends on how Google Retail Search orders "gender" and "rating" facets. However, notice that "price" and "brands" are always ranked at first and second position because their enable_dynamic_position values are false. */
		enableDynamicPosition: Option[Boolean] = None
	)
	
	case class GoogleCloudRetailV2SearchRequestFacetSpecFacetKey(
	  /** Required. Supported textual and numerical facet keys in Product object, over which the facet values are computed. Facet key is case-sensitive. Allowed facet keys when FacetKey.query is not specified: &#42; textual_field = &#42; "brands" &#42; "categories" &#42; "genders" &#42; "ageGroups" &#42; "availability" &#42; "colorFamilies" &#42; "colors" &#42; "sizes" &#42; "materials" &#42; "patterns" &#42; "conditions" &#42; "attributes.key" &#42; "pickupInStore" &#42; "shipToStore" &#42; "sameDayDelivery" &#42; "nextDayDelivery" &#42; "customFulfillment1" &#42; "customFulfillment2" &#42; "customFulfillment3" &#42; "customFulfillment4" &#42; "customFulfillment5" &#42; "inventory(place_id,attributes.key)" &#42; numerical_field = &#42; "price" &#42; "discount" &#42; "rating" &#42; "ratingCount" &#42; "attributes.key" &#42; "inventory(place_id,price)" &#42; "inventory(place_id,original_price)" &#42; "inventory(place_id,attributes.key)" */
		key: Option[String] = None,
	  /** Set only if values should be bucketized into intervals. Must be set for facets with numerical values. Must not be set for facet with text values. Maximum number of intervals is 40. For all numerical facet keys that appear in the list of products from the catalog, the percentiles 0, 10, 30, 50, 70, 90, and 100 are computed from their distribution weekly. If the model assigns a high score to a numerical facet key and its intervals are not specified in the search request, these percentiles become the bounds for its intervals and are returned in the response. If the facet key intervals are specified in the request, then the specified intervals are returned instead. */
		intervals: Option[List[Schema.GoogleCloudRetailV2Interval]] = None,
	  /** Only get facet for the given restricted values. For example, when using "pickupInStore" as key and set restricted values to ["store123", "store456"], only facets for "store123" and "store456" are returned. Only supported on predefined textual fields, custom textual attributes and fulfillments. Maximum is 20. Must be set for the fulfillment facet keys: &#42; pickupInStore &#42; shipToStore &#42; sameDayDelivery &#42; nextDayDelivery &#42; customFulfillment1 &#42; customFulfillment2 &#42; customFulfillment3 &#42; customFulfillment4 &#42; customFulfillment5 */
		restrictedValues: Option[List[String]] = None,
	  /** Only get facet values that start with the given string prefix. For example, suppose "categories" has three values "Women > Shoe", "Women > Dress" and "Men > Shoe". If set "prefixes" to "Women", the "categories" facet gives only "Women > Shoe" and "Women > Dress". Only supported on textual fields. Maximum is 10. */
		prefixes: Option[List[String]] = None,
	  /** Only get facet values that contains the given strings. For example, suppose "categories" has three values "Women > Shoe", "Women > Dress" and "Men > Shoe". If set "contains" to "Shoe", the "categories" facet gives only "Women > Shoe" and "Men > Shoe". Only supported on textual fields. Maximum is 10. */
		contains: Option[List[String]] = None,
	  /** True to make facet keys case insensitive when getting faceting values with prefixes or contains; false otherwise. */
		caseInsensitive: Option[Boolean] = None,
	  /** The order in which SearchResponse.Facet.values are returned. Allowed values are: &#42; "count desc", which means order by SearchResponse.Facet.values.count descending. &#42; "value desc", which means order by SearchResponse.Facet.values.value descending. Only applies to textual facets. If not set, textual values are sorted in [natural order](https://en.wikipedia.org/wiki/Natural_sort_order); numerical intervals are sorted in the order given by FacetSpec.FacetKey.intervals; FulfillmentInfo.place_ids are sorted in the order given by FacetSpec.FacetKey.restricted_values. */
		orderBy: Option[String] = None,
	  /** The query that is used to compute facet for the given facet key. When provided, it overrides the default behavior of facet computation. The query syntax is the same as a filter expression. See SearchRequest.filter for detail syntax and limitations. Notice that there is no limitation on FacetKey.key when query is specified. In the response, SearchResponse.Facet.values.value is always "1" and SearchResponse.Facet.values.count is the number of results that match the query. For example, you can set a customized facet for "shipToStore", where FacetKey.key is "customizedShipToStore", and FacetKey.query is "availability: ANY(\"IN_STOCK\") AND shipToStore: ANY(\"123\")". Then the facet counts the products that are both in stock and ship to store "123". */
		query: Option[String] = None,
	  /** Returns the min and max value for each numerical facet intervals. Ignored for textual facets. */
		returnMinMax: Option[Boolean] = None
	)
	
	object GoogleCloudRetailV2SearchRequestDynamicFacetSpec {
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, DISABLED, ENABLED }
	}
	case class GoogleCloudRetailV2SearchRequestDynamicFacetSpec(
	  /** Mode of the DynamicFacet feature. Defaults to Mode.DISABLED if it's unset. */
		mode: Option[Schema.GoogleCloudRetailV2SearchRequestDynamicFacetSpec.ModeEnum] = None
	)
	
	case class GoogleCloudRetailV2SearchRequestBoostSpec(
	  /** Condition boost specifications. If a product matches multiple conditions in the specifictions, boost scores from these specifications are all applied and combined in a non-linear way. Maximum number of specifications is 20. */
		conditionBoostSpecs: Option[List[Schema.GoogleCloudRetailV2SearchRequestBoostSpecConditionBoostSpec]] = None,
	  /** Whether to skip boostspec validation. If this field is set to true, invalid BoostSpec.condition_boost_specs will be ignored and valid BoostSpec.condition_boost_specs will still be applied. */
		skipBoostSpecValidation: Option[Boolean] = None
	)
	
	case class GoogleCloudRetailV2SearchRequestBoostSpecConditionBoostSpec(
	  /** An expression which specifies a boost condition. The syntax and supported fields are the same as a filter expression. See SearchRequest.filter for detail syntax and limitations. Examples: &#42; To boost products with product ID "product_1" or "product_2", and color "Red" or "Blue": &#42; (id: ANY("product_1", "product_2")) AND (colorFamilies: ANY("Red","Blue")) */
		condition: Option[String] = None,
	  /** Strength of the condition boost, which should be in [-1, 1]. Negative boost means demotion. Default is 0.0. Setting to 1.0 gives the item a big promotion. However, it does not necessarily mean that the boosted item will be the top result at all times, nor that other items will be excluded. Results could still be shown even when none of them matches the condition. And results that are significantly more relevant to the search query can still trump your heavily favored but irrelevant items. Setting to -1.0 gives the item a big demotion. However, results that are deeply relevant might still be shown. The item will have an upstream battle to get a fairly high ranking, but it is not blocked out completely. Setting to 0.0 means no boost applied. The boosting condition is ignored. */
		boost: Option[BigDecimal] = None
	)
	
	object GoogleCloudRetailV2SearchRequestQueryExpansionSpec {
		enum ConditionEnum extends Enum[ConditionEnum] { case CONDITION_UNSPECIFIED, DISABLED, AUTO }
	}
	case class GoogleCloudRetailV2SearchRequestQueryExpansionSpec(
	  /** The condition under which query expansion should occur. Default to Condition.DISABLED. */
		condition: Option[Schema.GoogleCloudRetailV2SearchRequestQueryExpansionSpec.ConditionEnum] = None,
	  /** Whether to pin unexpanded results. If this field is set to true, unexpanded products are always at the top of the search results, followed by the expanded results. */
		pinUnexpandedResults: Option[Boolean] = None
	)
	
	object GoogleCloudRetailV2SearchRequestPersonalizationSpec {
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, AUTO, DISABLED }
	}
	case class GoogleCloudRetailV2SearchRequestPersonalizationSpec(
	  /** Defaults to Mode.AUTO. */
		mode: Option[Schema.GoogleCloudRetailV2SearchRequestPersonalizationSpec.ModeEnum] = None
	)
	
	object GoogleCloudRetailV2SearchRequestSpellCorrectionSpec {
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, SUGGESTION_ONLY, AUTO }
	}
	case class GoogleCloudRetailV2SearchRequestSpellCorrectionSpec(
	  /** The mode under which spell correction should take effect to replace the original search query. Default to Mode.AUTO. */
		mode: Option[Schema.GoogleCloudRetailV2SearchRequestSpellCorrectionSpec.ModeEnum] = None
	)
	
	case class GoogleCloudRetailV2SearchRequestConversationalSearchSpec(
	  /** This field specifies whether the customer would like to do conversational search. If this field is set to true, conversational related extra information will be returned from server side, including follow-up question, answer options, etc. */
		followupConversationRequested: Option[Boolean] = None,
	  /** This field specifies the conversation id, which maintains the state of the conversation between client side and server side. Use the value from the previous ConversationalSearchResult.conversation_id. For the initial request, this should be empty. */
		conversationId: Option[String] = None,
	  /** This field specifies the current user answer during the conversational search. This can be either user selected from suggested answers or user input plain text. */
		userAnswer: Option[Schema.GoogleCloudRetailV2SearchRequestConversationalSearchSpecUserAnswer] = None
	)
	
	case class GoogleCloudRetailV2SearchRequestConversationalSearchSpecUserAnswer(
	  /** This field specifies the incremental input text from the user during the conversational search. */
		textAnswer: Option[String] = None,
	  /** This field specifies the selected attributes during the conversational search. This should be a subset of ConversationalSearchResult.suggested_answers. */
		selectedAnswer: Option[Schema.GoogleCloudRetailV2SearchRequestConversationalSearchSpecUserAnswerSelectedAnswer] = None
	)
	
	case class GoogleCloudRetailV2SearchRequestConversationalSearchSpecUserAnswerSelectedAnswer(
	  /** This field is deprecated and should not be set. */
		productAttributeValues: Option[List[Schema.GoogleCloudRetailV2ProductAttributeValue]] = None,
	  /** This field specifies the selected answer which is a attribute key-value. */
		productAttributeValue: Option[Schema.GoogleCloudRetailV2ProductAttributeValue] = None
	)
	
	case class GoogleCloudRetailV2ProductAttributeValue(
	  /** The attribute name. */
		name: Option[String] = None,
	  /** The attribute value. */
		value: Option[String] = None
	)
	
	case class GoogleCloudRetailV2SearchRequestTileNavigationSpec(
	  /** This field specifies whether the customer would like to request tile navigation. */
		tileNavigationRequested: Option[Boolean] = None,
	  /** This field specifies the tiles which are already clicked in client side. NOTE: This field is not being used for filtering search products. Client side should also put all the applied tiles in SearchRequest.filter. */
		appliedTiles: Option[List[Schema.GoogleCloudRetailV2Tile]] = None
	)
	
	case class GoogleCloudRetailV2Tile(
	  /** The product attribute key-value. */
		productAttributeValue: Option[Schema.GoogleCloudRetailV2ProductAttributeValue] = None,
	  /** The product attribute key-numeric interval. */
		productAttributeInterval: Option[Schema.GoogleCloudRetailV2ProductAttributeInterval] = None,
	  /** The representative product id for this tile. */
		representativeProductId: Option[String] = None
	)
	
	case class GoogleCloudRetailV2ProductAttributeInterval(
	  /** The attribute name (e.g. "length") */
		name: Option[String] = None,
	  /** The numeric interval (e.g. [10, 20)) */
		interval: Option[Schema.GoogleCloudRetailV2Interval] = None
	)
	
	case class GoogleCloudRetailV2SearchResponse(
	  /** A list of matched items. The order represents the ranking. */
		results: Option[List[Schema.GoogleCloudRetailV2SearchResponseSearchResult]] = None,
	  /** Results of facets requested by user. */
		facets: Option[List[Schema.GoogleCloudRetailV2SearchResponseFacet]] = None,
	  /** The estimated total count of matched items irrespective of pagination. The count of results returned by pagination may be less than the total_size that matches. */
		totalSize: Option[Int] = None,
	  /** Contains the spell corrected query, if found. If the spell correction type is AUTOMATIC, then the search results are based on corrected_query. Otherwise the original query is used for search. */
		correctedQuery: Option[String] = None,
	  /** A unique search token. This should be included in the UserEvent logs resulting from this search, which enables accurate attribution of search model performance. */
		attributionToken: Option[String] = None,
	  /** A token that can be sent as SearchRequest.page_token to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Query expansion information for the returned results. */
		queryExpansionInfo: Option[Schema.GoogleCloudRetailV2SearchResponseQueryExpansionInfo] = None,
	  /** The URI of a customer-defined redirect page. If redirect action is triggered, no search is performed, and only redirect_uri and attribution_token are set in the response. */
		redirectUri: Option[String] = None,
	  /** The fully qualified resource name of applied [controls](https://cloud.google.com/retail/docs/serving-control-rules). */
		appliedControls: Option[List[String]] = None,
	  /** Metadata for pin controls which were applicable to the request. This contains two map fields, one for all matched pins and one for pins which were matched but not applied. The two maps are keyed by pin position, and the values are the product ids which were matched to that pin. */
		pinControlMetadata: Option[Schema.GoogleCloudRetailV2PinControlMetadata] = None,
	  /** The invalid SearchRequest.BoostSpec.condition_boost_specs that are not applied during serving. */
		invalidConditionBoostSpecs: Option[List[Schema.GoogleCloudRetailV2SearchRequestBoostSpecConditionBoostSpec]] = None,
	  /** Metadata related to A/B testing Experiment associated with this response. Only exists when an experiment is triggered. */
		experimentInfo: Option[List[Schema.GoogleCloudRetailV2ExperimentInfo]] = None,
	  /** This field specifies all related information that is needed on client side for UI rendering of conversational retail search. */
		conversationalSearchResult: Option[Schema.GoogleCloudRetailV2SearchResponseConversationalSearchResult] = None,
	  /** This field specifies all related information for tile navigation that will be used in client side. */
		tileNavigationResult: Option[Schema.GoogleCloudRetailV2SearchResponseTileNavigationResult] = None
	)
	
	case class GoogleCloudRetailV2SearchResponseSearchResult(
	  /** Product.id of the searched Product. */
		id: Option[String] = None,
	  /** The product data snippet in the search response. Only Product.name is guaranteed to be populated. Product.variants contains the product variants that match the search query. If there are multiple product variants matching the query, top 5 most relevant product variants are returned and ordered by relevancy. If relevancy can be deternmined, use matching_variant_fields to look up matched product variants fields. If relevancy cannot be determined, e.g. when searching "shoe" all products in a shoe product can be a match, 5 product variants are returned but order is meaningless. */
		product: Option[Schema.GoogleCloudRetailV2Product] = None,
	  /** The count of matched variant Products. */
		matchingVariantCount: Option[Int] = None,
	  /** If a variant Product matches the search query, this map indicates which Product fields are matched. The key is the Product.name, the value is a field mask of the matched Product fields. If matched attributes cannot be determined, this map will be empty. For example, a key "sku1" with field mask "products.color_info" indicates there is a match between "sku1" ColorInfo and the query. */
		matchingVariantFields: Option[Map[String, String]] = None,
	  /** The rollup matching variant Product attributes. The key is one of the SearchRequest.variant_rollup_keys. The values are the merged and de-duplicated Product attributes. Notice that the rollup values are respect filter. For example, when filtering by "colorFamilies:ANY(\"red\")" and rollup "colorFamilies", only "red" is returned. For textual and numerical attributes, the rollup values is a list of string or double values with type google.protobuf.ListValue. For example, if there are two variants with colors "red" and "blue", the rollup values are { key: "colorFamilies" value { list_value { values { string_value: "red" } values { string_value: "blue" } } } } For FulfillmentInfo, the rollup values is a double value with type google.protobuf.Value. For example, `{key: "pickupInStore.store1" value { number_value: 10 }}` means a there are 10 variants in this product are available in the store "store1". */
		variantRollupValues: Option[Map[String, JsValue]] = None,
	  /** Specifies previous events related to this product for this user based on UserEvent with same SearchRequest.visitor_id or UserInfo.user_id. This is set only when SearchRequest.PersonalizationSpec.mode is SearchRequest.PersonalizationSpec.Mode.AUTO. Possible values: &#42; `purchased`: Indicates that this product has been purchased before. */
		personalLabels: Option[List[String]] = None
	)
	
	object GoogleCloudRetailV2Product {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, PRIMARY, VARIANT, COLLECTION }
		enum AvailabilityEnum extends Enum[AvailabilityEnum] { case AVAILABILITY_UNSPECIFIED, IN_STOCK, OUT_OF_STOCK, PREORDER, BACKORDER }
	}
	case class GoogleCloudRetailV2Product(
	  /** Note that this field is applied in the following ways: &#42; If the Product is already expired when it is uploaded, this product is not indexed for search. &#42; If the Product is not expired when it is uploaded, only the Type.PRIMARY's and Type.COLLECTION's expireTime is respected, and Type.VARIANT's expireTime is not used. In general, we suggest the users to delete the stale products explicitly, instead of using this field to determine staleness. expire_time must be later than available_time and publish_time, otherwise an INVALID_ARGUMENT error is thrown. Corresponding properties: Google Merchant Center property [expiration_date](https://support.google.com/merchants/answer/6324499). */
		expireTime: Option[String] = None,
	  /** Input only. The TTL (time to live) of the product. Note that this is only applicable to Type.PRIMARY and Type.COLLECTION, and ignored for Type.VARIANT. In general, we suggest the users to delete the stale products explicitly, instead of using this field to determine staleness. If it is set, it must be a non-negative value, and expire_time is set as current timestamp plus ttl. The derived expire_time is returned in the output and ttl is left blank when retrieving the Product. If it is set, the product is not available for SearchService.Search after current timestamp plus ttl. However, the product can still be retrieved by ProductService.GetProduct and ProductService.ListProducts. */
		ttl: Option[String] = None,
	  /** Immutable. Full resource name of the product, such as `projects/&#42;/locations/global/catalogs/default_catalog/branches/default_branch/products/product_id`. */
		name: Option[String] = None,
	  /** Immutable. Product identifier, which is the final component of name. For example, this field is "id_1", if name is `projects/&#42;/locations/global/catalogs/default_catalog/branches/default_branch/products/id_1`. This field must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an INVALID_ARGUMENT error is returned. Corresponding properties: Google Merchant Center property [id](https://support.google.com/merchants/answer/6324405). Schema.org property [Product.sku](https://schema.org/sku). */
		id: Option[String] = None,
	  /** Immutable. The type of the product. Default to Catalog.product_level_config.ingestion_product_type if unset. */
		`type`: Option[Schema.GoogleCloudRetailV2Product.TypeEnum] = None,
	  /** Variant group identifier. Must be an id, with the same parent branch with this product. Otherwise, an error is thrown. For Type.PRIMARY Products, this field can only be empty or set to the same value as id. For VARIANT Products, this field cannot be empty. A maximum of 2,000 products are allowed to share the same Type.PRIMARY Product. Otherwise, an INVALID_ARGUMENT error is returned. Corresponding properties: Google Merchant Center property [item_group_id](https://support.google.com/merchants/answer/6324507). Schema.org property [Product.inProductGroupWithID](https://schema.org/inProductGroupWithID). */
		primaryProductId: Option[String] = None,
	  /** The id of the collection members when type is Type.COLLECTION. Non-existent product ids are allowed. The type of the members must be either Type.PRIMARY or Type.VARIANT otherwise an INVALID_ARGUMENT error is thrown. Should not set it for other types. A maximum of 1000 values are allowed. Otherwise, an INVALID_ARGUMENT error is return. */
		collectionMemberIds: Option[List[String]] = None,
	  /** The Global Trade Item Number (GTIN) of the product. This field must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an INVALID_ARGUMENT error is returned. This field must be a Unigram. Otherwise, an INVALID_ARGUMENT error is returned. Corresponding properties: Google Merchant Center property [gtin](https://support.google.com/merchants/answer/6324461). Schema.org property [Product.isbn](https://schema.org/isbn), [Product.gtin8](https://schema.org/gtin8), [Product.gtin12](https://schema.org/gtin12), [Product.gtin13](https://schema.org/gtin13), or [Product.gtin14](https://schema.org/gtin14). If the value is not a valid GTIN, an INVALID_ARGUMENT error is returned. */
		gtin: Option[String] = None,
	  /** Product categories. This field is repeated for supporting one product belonging to several parallel categories. Strongly recommended using the full path for better search / recommendation quality. To represent full path of category, use '>' sign to separate different hierarchies. If '>' is part of the category name, replace it with other character(s). For example, if a shoes product belongs to both ["Shoes & Accessories" -> "Shoes"] and ["Sports & Fitness" -> "Athletic Clothing" -> "Shoes"], it could be represented as: "categories": [ "Shoes & Accessories > Shoes", "Sports & Fitness > Athletic Clothing > Shoes" ] Must be set for Type.PRIMARY Product otherwise an INVALID_ARGUMENT error is returned. At most 250 values are allowed per Product unless overridden through the Google Cloud console. Empty values are not allowed. Each value must be a UTF-8 encoded string with a length limit of 5,000 characters. Otherwise, an INVALID_ARGUMENT error is returned. Corresponding properties: Google Merchant Center property google_product_category. Schema.org property [Product.category] (https://schema.org/category). [mc_google_product_category]: https://support.google.com/merchants/answer/6324436 */
		categories: Option[List[String]] = None,
	  /** Required. Product title. This field must be a UTF-8 encoded string with a length limit of 1,000 characters. Otherwise, an INVALID_ARGUMENT error is returned. Corresponding properties: Google Merchant Center property [title](https://support.google.com/merchants/answer/6324415). Schema.org property [Product.name](https://schema.org/name). */
		title: Option[String] = None,
	  /** The brands of the product. A maximum of 30 brands are allowed unless overridden through the Google Cloud console. Each brand must be a UTF-8 encoded string with a length limit of 1,000 characters. Otherwise, an INVALID_ARGUMENT error is returned. Corresponding properties: Google Merchant Center property [brand](https://support.google.com/merchants/answer/6324351). Schema.org property [Product.brand](https://schema.org/brand). */
		brands: Option[List[String]] = None,
	  /** Product description. This field must be a UTF-8 encoded string with a length limit of 5,000 characters. Otherwise, an INVALID_ARGUMENT error is returned. Corresponding properties: Google Merchant Center property [description](https://support.google.com/merchants/answer/6324468). Schema.org property [Product.description](https://schema.org/description). */
		description: Option[String] = None,
	  /** Language of the title/description and other string attributes. Use language tags defined by [BCP 47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt). For product prediction, this field is ignored and the model automatically detects the text language. The Product can include text in different languages, but duplicating Products to provide text in multiple languages can result in degraded model performance. For product search this field is in use. It defaults to "en-US" if unset. */
		languageCode: Option[String] = None,
	  /** Highly encouraged. Extra product attributes to be included. For example, for products, this could include the store name, vendor, style, color, etc. These are very strong signals for recommendation model, thus we highly recommend providing the attributes here. Features that can take on one of a limited number of possible values. Two types of features can be set are: Textual features. some examples would be the brand/maker of a product, or country of a customer. Numerical features. Some examples would be the height/weight of a product, or age of a customer. For example: `{ "vendor": {"text": ["vendor123", "vendor456"]}, "lengths_cm": {"numbers":[2.3, 15.4]}, "heights_cm": {"numbers":[8.1, 6.4]} }`. This field needs to pass all below criteria, otherwise an INVALID_ARGUMENT error is returned: &#42; Max entries count: 200. &#42; The key must be a UTF-8 encoded string with a length limit of 128 characters. &#42; For indexable attribute, the key must match the pattern: `a-zA-Z0-9&#42;`. For example, `key0LikeThis` or `KEY_1_LIKE_THIS`. &#42; For text attributes, at most 400 values are allowed. Empty values are not allowed. Each value must be a non-empty UTF-8 encoded string with a length limit of 256 characters. &#42; For number attributes, at most 400 values are allowed. */
		attributes: Option[Map[String, Schema.GoogleCloudRetailV2CustomAttribute]] = None,
	  /** Custom tags associated with the product. At most 250 values are allowed per Product. This value must be a UTF-8 encoded string with a length limit of 1,000 characters. Otherwise, an INVALID_ARGUMENT error is returned. This tag can be used for filtering recommendation results by passing the tag as part of the PredictRequest.filter. Corresponding properties: Google Merchant Center property [custom_label_0–4](https://support.google.com/merchants/answer/6324473). */
		tags: Option[List[String]] = None,
	  /** Product price and cost information. Corresponding properties: Google Merchant Center property [price](https://support.google.com/merchants/answer/6324371). */
		priceInfo: Option[Schema.GoogleCloudRetailV2PriceInfo] = None,
	  /** The rating of this product. */
		rating: Option[Schema.GoogleCloudRetailV2Rating] = None,
	  /** The timestamp when this Product becomes available for SearchService.Search. Note that this is only applicable to Type.PRIMARY and Type.COLLECTION, and ignored for Type.VARIANT. */
		availableTime: Option[String] = None,
	  /** The online availability of the Product. Default to Availability.IN_STOCK. For primary products with variants set the availability of the primary as Availability.OUT_OF_STOCK and set the true availability at the variant level. This way the primary product will be considered "in stock" as long as it has at least one variant in stock. For primary products with no variants set the true availability at the primary level. Corresponding properties: Google Merchant Center property [availability](https://support.google.com/merchants/answer/6324448). Schema.org property [Offer.availability](https://schema.org/availability). */
		availability: Option[Schema.GoogleCloudRetailV2Product.AvailabilityEnum] = None,
	  /** The available quantity of the item. */
		availableQuantity: Option[Int] = None,
	  /** Fulfillment information, such as the store IDs for in-store pickup or region IDs for different shipping methods. All the elements must have distinct FulfillmentInfo.type. Otherwise, an INVALID_ARGUMENT error is returned. */
		fulfillmentInfo: Option[List[Schema.GoogleCloudRetailV2FulfillmentInfo]] = None,
	  /** Canonical URL directly linking to the product detail page. It is strongly recommended to provide a valid uri for the product, otherwise the service performance could be significantly degraded. This field must be a UTF-8 encoded string with a length limit of 5,000 characters. Otherwise, an INVALID_ARGUMENT error is returned. Corresponding properties: Google Merchant Center property [link](https://support.google.com/merchants/answer/6324416). Schema.org property [Offer.url](https://schema.org/url). */
		uri: Option[String] = None,
	  /** Product images for the product. We highly recommend putting the main image first. A maximum of 300 images are allowed. Corresponding properties: Google Merchant Center property [image_link](https://support.google.com/merchants/answer/6324350). Schema.org property [Product.image](https://schema.org/image). */
		images: Option[List[Schema.GoogleCloudRetailV2Image]] = None,
	  /** The target group associated with a given audience (e.g. male, veterans, car owners, musicians, etc.) of the product. */
		audience: Option[Schema.GoogleCloudRetailV2Audience] = None,
	  /** The color of the product. Corresponding properties: Google Merchant Center property [color](https://support.google.com/merchants/answer/6324487). Schema.org property [Product.color](https://schema.org/color). */
		colorInfo: Option[Schema.GoogleCloudRetailV2ColorInfo] = None,
	  /** The size of the product. To represent different size systems or size types, consider using this format: [[[size_system:]size_type:]size_value]. For example, in "US:MENS:M", "US" represents size system; "MENS" represents size type; "M" represents size value. In "GIRLS:27", size system is empty; "GIRLS" represents size type; "27" represents size value. In "32 inches", both size system and size type are empty, while size value is "32 inches". A maximum of 20 values are allowed per Product. Each value must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an INVALID_ARGUMENT error is returned. Corresponding properties: Google Merchant Center property [size](https://support.google.com/merchants/answer/6324492), [size_type](https://support.google.com/merchants/answer/6324497), and [size_system](https://support.google.com/merchants/answer/6324502). Schema.org property [Product.size](https://schema.org/size). */
		sizes: Option[List[String]] = None,
	  /** The material of the product. For example, "leather", "wooden". A maximum of 20 values are allowed. Each value must be a UTF-8 encoded string with a length limit of 200 characters. Otherwise, an INVALID_ARGUMENT error is returned. Corresponding properties: Google Merchant Center property [material](https://support.google.com/merchants/answer/6324410). Schema.org property [Product.material](https://schema.org/material). */
		materials: Option[List[String]] = None,
	  /** The pattern or graphic print of the product. For example, "striped", "polka dot", "paisley". A maximum of 20 values are allowed per Product. Each value must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an INVALID_ARGUMENT error is returned. Corresponding properties: Google Merchant Center property [pattern](https://support.google.com/merchants/answer/6324483). Schema.org property [Product.pattern](https://schema.org/pattern). */
		patterns: Option[List[String]] = None,
	  /** The condition of the product. Strongly encouraged to use the standard values: "new", "refurbished", "used". A maximum of 1 value is allowed per Product. Each value must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an INVALID_ARGUMENT error is returned. Corresponding properties: Google Merchant Center property [condition](https://support.google.com/merchants/answer/6324469). Schema.org property [Offer.itemCondition](https://schema.org/itemCondition). */
		conditions: Option[List[String]] = None,
	  /** The promotions applied to the product. A maximum of 10 values are allowed per Product. Only Promotion.promotion_id will be used, other fields will be ignored if set. */
		promotions: Option[List[Schema.GoogleCloudRetailV2Promotion]] = None,
	  /** The timestamp when the product is published by the retailer for the first time, which indicates the freshness of the products. Note that this field is different from available_time, given it purely describes product freshness regardless of when it is available on search and recommendation. */
		publishTime: Option[String] = None,
	  /** Indicates which fields in the Products are returned in SearchResponse. Supported fields for all types: &#42; audience &#42; availability &#42; brands &#42; color_info &#42; conditions &#42; gtin &#42; materials &#42; name &#42; patterns &#42; price_info &#42; rating &#42; sizes &#42; title &#42; uri Supported fields only for Type.PRIMARY and Type.COLLECTION: &#42; categories &#42; description &#42; images Supported fields only for Type.VARIANT: &#42; Only the first image in images To mark attributes as retrievable, include paths of the form "attributes.key" where "key" is the key of a custom attribute, as specified in attributes. For Type.PRIMARY and Type.COLLECTION, the following fields are always returned in SearchResponse by default: &#42; name For Type.VARIANT, the following fields are always returned in by default: &#42; name &#42; color_info Note: Returning more fields in SearchResponse can increase response payload size and serving latency. This field is deprecated. Use the retrievable site-wide control instead. */
		retrievableFields: Option[String] = None,
	  /** Output only. Product variants grouped together on primary product which share similar product attributes. It's automatically grouped by primary_product_id for all the product variants. Only populated for Type.PRIMARY Products. Note: This field is OUTPUT_ONLY for ProductService.GetProduct. Do not set this field in API requests. */
		variants: Option[List[Schema.GoogleCloudRetailV2Product]] = None,
	  /** Output only. A list of local inventories specific to different places. This field can be managed by ProductService.AddLocalInventories and ProductService.RemoveLocalInventories APIs if fine-grained, high-volume updates are necessary. */
		localInventories: Option[List[Schema.GoogleCloudRetailV2LocalInventory]] = None
	)
	
	case class GoogleCloudRetailV2CustomAttribute(
	  /** The textual values of this custom attribute. For example, `["yellow", "green"]` when the key is "color". Empty string is not allowed. Otherwise, an INVALID_ARGUMENT error is returned. Exactly one of text or numbers should be set. Otherwise, an INVALID_ARGUMENT error is returned. */
		text: Option[List[String]] = None,
	  /** The numerical values of this custom attribute. For example, `[2.3, 15.4]` when the key is "lengths_cm". Exactly one of text or numbers should be set. Otherwise, an INVALID_ARGUMENT error is returned. */
		numbers: Option[List[BigDecimal]] = None,
	  /** This field is normally ignored unless AttributesConfig.attribute_config_level of the Catalog is set to the deprecated 'PRODUCT_LEVEL_ATTRIBUTE_CONFIG' mode. For information about product-level attribute configuration, see [Configuration modes](https://cloud.google.com/retail/docs/attribute-config#config-modes). If true, custom attribute values are searchable by text queries in SearchService.Search. This field is ignored in a UserEvent. Only set if type text is set. Otherwise, a INVALID_ARGUMENT error is returned. */
		searchable: Option[Boolean] = None,
	  /** This field is normally ignored unless AttributesConfig.attribute_config_level of the Catalog is set to the deprecated 'PRODUCT_LEVEL_ATTRIBUTE_CONFIG' mode. For information about product-level attribute configuration, see [Configuration modes](https://cloud.google.com/retail/docs/attribute-config#config-modes). If true, custom attribute values are indexed, so that they can be filtered, faceted or boosted in SearchService.Search. This field is ignored in a UserEvent. See SearchRequest.filter, SearchRequest.facet_specs and SearchRequest.boost_spec for more details. */
		indexable: Option[Boolean] = None
	)
	
	case class GoogleCloudRetailV2PriceInfo(
	  /** The 3-letter currency code defined in [ISO 4217](https://www.iso.org/iso-4217-currency-codes.html). If this field is an unrecognizable currency code, an INVALID_ARGUMENT error is returned. The Product.Type.VARIANT Products with the same Product.primary_product_id must share the same currency_code. Otherwise, a FAILED_PRECONDITION error is returned. */
		currencyCode: Option[String] = None,
	  /** Price of the product. Google Merchant Center property [price](https://support.google.com/merchants/answer/6324371). Schema.org property [Offer.price](https://schema.org/price). */
		price: Option[BigDecimal] = None,
	  /** Price of the product without any discount. If zero, by default set to be the price. If set, original_price should be greater than or equal to price, otherwise an INVALID_ARGUMENT error is thrown. */
		originalPrice: Option[BigDecimal] = None,
	  /** The costs associated with the sale of a particular product. Used for gross profit reporting. &#42; Profit = price - cost Google Merchant Center property [cost_of_goods_sold](https://support.google.com/merchants/answer/9017895). */
		cost: Option[BigDecimal] = None,
	  /** The timestamp when the price starts to be effective. This can be set as a future timestamp, and the price is only used for search after price_effective_time. If so, the original_price must be set and original_price is used before price_effective_time. Do not set if price is always effective because it will cause additional latency during search. */
		priceEffectiveTime: Option[String] = None,
	  /** The timestamp when the price stops to be effective. The price is used for search before price_expire_time. If this field is set, the original_price must be set and original_price is used after price_expire_time. Do not set if price is always effective because it will cause additional latency during search. */
		priceExpireTime: Option[String] = None,
	  /** Output only. The price range of all the child Product.Type.VARIANT Products grouped together on the Product.Type.PRIMARY Product. Only populated for Product.Type.PRIMARY Products. Note: This field is OUTPUT_ONLY for ProductService.GetProduct. Do not set this field in API requests. */
		priceRange: Option[Schema.GoogleCloudRetailV2PriceInfoPriceRange] = None
	)
	
	case class GoogleCloudRetailV2PriceInfoPriceRange(
	  /** The inclusive Product.pricing_info.price interval of all variant Product having the same Product.primary_product_id. */
		price: Option[Schema.GoogleCloudRetailV2Interval] = None,
	  /** The inclusive Product.pricing_info.original_price internal of all variant Product having the same Product.primary_product_id. */
		originalPrice: Option[Schema.GoogleCloudRetailV2Interval] = None
	)
	
	case class GoogleCloudRetailV2Rating(
	  /** The total number of ratings. This value is independent of the value of rating_histogram. This value must be nonnegative. Otherwise, an INVALID_ARGUMENT error is returned. */
		ratingCount: Option[Int] = None,
	  /** The average rating of the Product. The rating is scaled at 1-5. Otherwise, an INVALID_ARGUMENT error is returned. */
		averageRating: Option[BigDecimal] = None,
	  /** List of rating counts per rating value (index = rating - 1). The list is empty if there is no rating. If the list is non-empty, its size is always 5. Otherwise, an INVALID_ARGUMENT error is returned. For example, [41, 14, 13, 47, 303]. It means that the Product got 41 ratings with 1 star, 14 ratings with 2 star, and so on. */
		ratingHistogram: Option[List[Int]] = None
	)
	
	case class GoogleCloudRetailV2FulfillmentInfo(
	  /** The fulfillment type, including commonly used types (such as pickup in store and same day delivery), and custom types. Customers have to map custom types to their display names before rendering UI. Supported values: &#42; "pickup-in-store" &#42; "ship-to-store" &#42; "same-day-delivery" &#42; "next-day-delivery" &#42; "custom-type-1" &#42; "custom-type-2" &#42; "custom-type-3" &#42; "custom-type-4" &#42; "custom-type-5" If this field is set to an invalid value other than these, an INVALID_ARGUMENT error is returned. */
		`type`: Option[String] = None,
	  /** The IDs for this type, such as the store IDs for FulfillmentInfo.type.pickup-in-store or the region IDs for FulfillmentInfo.type.same-day-delivery. A maximum of 3000 values are allowed. Each value must be a string with a length limit of 30 characters, matching the pattern `[a-zA-Z0-9_-]+`, such as "store1" or "REGION-2". Otherwise, an INVALID_ARGUMENT error is returned. */
		placeIds: Option[List[String]] = None
	)
	
	case class GoogleCloudRetailV2Image(
	  /** Required. URI of the image. This field must be a valid UTF-8 encoded URI with a length limit of 5,000 characters. Otherwise, an INVALID_ARGUMENT error is returned. Google Merchant Center property [image_link](https://support.google.com/merchants/answer/6324350). Schema.org property [Product.image](https://schema.org/image). */
		uri: Option[String] = None,
	  /** Height of the image in number of pixels. This field must be nonnegative. Otherwise, an INVALID_ARGUMENT error is returned. */
		height: Option[Int] = None,
	  /** Width of the image in number of pixels. This field must be nonnegative. Otherwise, an INVALID_ARGUMENT error is returned. */
		width: Option[Int] = None
	)
	
	case class GoogleCloudRetailV2Audience(
	  /** The genders of the audience. Strongly encouraged to use the standard values: "male", "female", "unisex". At most 5 values are allowed. Each value must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an INVALID_ARGUMENT error is returned. Google Merchant Center property [gender](https://support.google.com/merchants/answer/6324479). Schema.org property [Product.audience.suggestedGender](https://schema.org/suggestedGender). */
		genders: Option[List[String]] = None,
	  /** The age groups of the audience. Strongly encouraged to use the standard values: "newborn" (up to 3 months old), "infant" (3–12 months old), "toddler" (1–5 years old), "kids" (5–13 years old), "adult" (typically teens or older). At most 5 values are allowed. Each value must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an INVALID_ARGUMENT error is returned. Google Merchant Center property [age_group](https://support.google.com/merchants/answer/6324463). Schema.org property [Product.audience.suggestedMinAge](https://schema.org/suggestedMinAge) and [Product.audience.suggestedMaxAge](https://schema.org/suggestedMaxAge). */
		ageGroups: Option[List[String]] = None
	)
	
	case class GoogleCloudRetailV2ColorInfo(
	  /** The standard color families. Strongly recommended to use the following standard color groups: "Red", "Pink", "Orange", "Yellow", "Purple", "Green", "Cyan", "Blue", "Brown", "White", "Gray", "Black" and "Mixed". Normally it is expected to have only 1 color family. May consider using single "Mixed" instead of multiple values. A maximum of 5 values are allowed. Each value must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an INVALID_ARGUMENT error is returned. Google Merchant Center property [color](https://support.google.com/merchants/answer/6324487). Schema.org property [Product.color](https://schema.org/color). */
		colorFamilies: Option[List[String]] = None,
	  /** The color display names, which may be different from standard color family names, such as the color aliases used in the website frontend. Normally it is expected to have only 1 color. May consider using single "Mixed" instead of multiple values. A maximum of 75 colors are allowed. Each value must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an INVALID_ARGUMENT error is returned. Google Merchant Center property [color](https://support.google.com/merchants/answer/6324487). Schema.org property [Product.color](https://schema.org/color). */
		colors: Option[List[String]] = None
	)
	
	case class GoogleCloudRetailV2Promotion(
	  /** Promotion identifier, which is the final component of name. For example, this field is "free_gift", if name is `projects/&#42;/locations/global/catalogs/default_catalog/promotions/free_gift`. The value must be a UTF-8 encoded string with a length limit of 128 characters, and match the pattern: `a-zA-Z&#42;`. For example, id0LikeThis or ID_1_LIKE_THIS. Otherwise, an INVALID_ARGUMENT error is returned. Corresponds to Google Merchant Center property [promotion_id](https://support.google.com/merchants/answer/7050148). */
		promotionId: Option[String] = None
	)
	
	case class GoogleCloudRetailV2LocalInventory(
	  /** The place ID for the current set of inventory information. */
		placeId: Option[String] = None,
	  /** Product price and cost information. Google Merchant Center property [price](https://support.google.com/merchants/answer/6324371). */
		priceInfo: Option[Schema.GoogleCloudRetailV2PriceInfo] = None,
	  /** Additional local inventory attributes, for example, store name, promotion tags, etc. This field needs to pass all below criteria, otherwise an INVALID_ARGUMENT error is returned: &#42; At most 30 attributes are allowed. &#42; The key must be a UTF-8 encoded string with a length limit of 32 characters. &#42; The key must match the pattern: `a-zA-Z0-9&#42;`. For example, key0LikeThis or KEY_1_LIKE_THIS. &#42; The attribute values must be of the same type (text or number). &#42; Only 1 value is allowed for each attribute. &#42; For text values, the length limit is 256 UTF-8 characters. &#42; The attribute does not support search. The `searchable` field should be unset or set to false. &#42; The max summed total bytes of custom attribute keys and values per product is 5MiB. */
		attributes: Option[Map[String, Schema.GoogleCloudRetailV2CustomAttribute]] = None,
	  /** Input only. Supported fulfillment types. Valid fulfillment type values include commonly used types (such as pickup in store and same day delivery), and custom types. Customers have to map custom types to their display names before rendering UI. Supported values: &#42; "pickup-in-store" &#42; "ship-to-store" &#42; "same-day-delivery" &#42; "next-day-delivery" &#42; "custom-type-1" &#42; "custom-type-2" &#42; "custom-type-3" &#42; "custom-type-4" &#42; "custom-type-5" If this field is set to an invalid value other than these, an INVALID_ARGUMENT error is returned. All the elements must be distinct. Otherwise, an INVALID_ARGUMENT error is returned. */
		fulfillmentTypes: Option[List[String]] = None
	)
	
	case class GoogleCloudRetailV2SearchResponseFacet(
	  /** The key for this facet. E.g., "colorFamilies" or "price" or "attributes.attr1". */
		key: Option[String] = None,
	  /** The facet values for this field. */
		values: Option[List[Schema.GoogleCloudRetailV2SearchResponseFacetFacetValue]] = None,
	  /** Whether the facet is dynamically generated. */
		dynamicFacet: Option[Boolean] = None
	)
	
	case class GoogleCloudRetailV2SearchResponseFacetFacetValue(
	  /** Text value of a facet, such as "Black" for facet "colorFamilies". */
		value: Option[String] = None,
	  /** Interval value for a facet, such as [10, 20) for facet "price". */
		interval: Option[Schema.GoogleCloudRetailV2Interval] = None,
	  /** Number of items that have this facet value. */
		count: Option[String] = None,
	  /** The minimum value in the FacetValue.interval. Only supported on numerical facets and returned if SearchRequest.FacetSpec.FacetKey.return_min_max is true. */
		minValue: Option[BigDecimal] = None,
	  /** The maximum value in the FacetValue.interval. Only supported on numerical facets and returned if SearchRequest.FacetSpec.FacetKey.return_min_max is true. */
		maxValue: Option[BigDecimal] = None
	)
	
	case class GoogleCloudRetailV2SearchResponseQueryExpansionInfo(
	  /** Bool describing whether query expansion has occurred. */
		expandedQuery: Option[Boolean] = None,
	  /** Number of pinned results. This field will only be set when expansion happens and SearchRequest.QueryExpansionSpec.pin_unexpanded_results is set to true. */
		pinnedResultCount: Option[String] = None
	)
	
	case class GoogleCloudRetailV2PinControlMetadata(
	  /** Map of all matched pins, keyed by pin position. */
		allMatchedPins: Option[Map[String, Schema.GoogleCloudRetailV2PinControlMetadataProductPins]] = None,
	  /** Map of pins that were dropped due to overlap with other matching pins, keyed by pin position. */
		droppedPins: Option[Map[String, Schema.GoogleCloudRetailV2PinControlMetadataProductPins]] = None
	)
	
	case class GoogleCloudRetailV2PinControlMetadataProductPins(
	  /** List of product ids which have associated pins. */
		productId: Option[List[String]] = None
	)
	
	case class GoogleCloudRetailV2ExperimentInfo(
	  /** A/B test between existing Cloud Retail Search ServingConfigs. */
		servingConfigExperiment: Option[Schema.GoogleCloudRetailV2ExperimentInfoServingConfigExperiment] = None,
	  /** The fully qualified resource name of the experiment that provides the serving config under test, should an active experiment exist. For example: `projects/&#42;/locations/global/catalogs/default_catalog/experiments/experiment_id` */
		experiment: Option[String] = None
	)
	
	case class GoogleCloudRetailV2ExperimentInfoServingConfigExperiment(
	  /** The fully qualified resource name of the original SearchRequest.placement in the search request prior to reassignment by experiment API. For example: `projects/&#42;/locations/&#42;/catalogs/&#42;/servingConfigs/&#42;`. */
		originalServingConfig: Option[String] = None,
	  /** The fully qualified resource name of the serving config Experiment.VariantArm.serving_config_id responsible for generating the search response. For example: `projects/&#42;/locations/&#42;/catalogs/&#42;/servingConfigs/&#42;`. */
		experimentServingConfig: Option[String] = None
	)
	
	case class GoogleCloudRetailV2SearchResponseConversationalSearchResult(
	  /** Conversation UUID. This field will be stored in client side storage to maintain the conversation session with server and will be used for next search request's SearchRequest.ConversationalSearchSpec.conversation_id to restore conversation state in server. */
		conversationId: Option[String] = None,
	  /** The current refined query for the conversational search. This field will be used in customer UI that the query in the search bar should be replaced with the refined query. For example, if SearchRequest.query is `dress` and next SearchRequest.ConversationalSearchSpec.UserAnswer.text_answer is `red color`, which does not match any product attribute value filters, the refined query will be `dress, red color`. */
		refinedQuery: Option[String] = None,
	  /** This field is deprecated but will be kept for backward compatibility. There is expected to have only one additional filter and the value will be the same to the same as field `additional_filter`. */
		additionalFilters: Option[List[Schema.GoogleCloudRetailV2SearchResponseConversationalSearchResultAdditionalFilter]] = None,
	  /** The follow-up question. e.g., `What is the color?` */
		followupQuestion: Option[String] = None,
	  /** The answer options provided to client for the follow-up question. */
		suggestedAnswers: Option[List[Schema.GoogleCloudRetailV2SearchResponseConversationalSearchResultSuggestedAnswer]] = None,
	  /** This is the incremental additional filters implied from the current user answer. User should add the suggested addition filters to the previous SearchRequest.filter, and use the merged filter in the follow up search request. */
		additionalFilter: Option[Schema.GoogleCloudRetailV2SearchResponseConversationalSearchResultAdditionalFilter] = None
	)
	
	case class GoogleCloudRetailV2SearchResponseConversationalSearchResultAdditionalFilter(
	  /** Product attribute value, including an attribute key and an attribute value. Other types can be added here in the future. */
		productAttributeValue: Option[Schema.GoogleCloudRetailV2ProductAttributeValue] = None
	)
	
	case class GoogleCloudRetailV2SearchResponseConversationalSearchResultSuggestedAnswer(
	  /** Product attribute value, including an attribute key and an attribute value. Other types can be added here in the future. */
		productAttributeValue: Option[Schema.GoogleCloudRetailV2ProductAttributeValue] = None
	)
	
	case class GoogleCloudRetailV2SearchResponseTileNavigationResult(
	  /** The current tiles that are used for tile navigation, sorted by engagement. */
		tiles: Option[List[Schema.GoogleCloudRetailV2Tile]] = None
	)
	
	case class GoogleCloudRetailV2CompleteQueryResponse(
	  /** Results of the matching suggestions. The result list is ordered and the first result is top suggestion. */
		completionResults: Option[List[Schema.GoogleCloudRetailV2CompleteQueryResponseCompletionResult]] = None,
	  /** A unique complete token. This should be included in the UserEvent.completion_detail for search events resulting from this completion, which enables accurate attribution of complete model performance. */
		attributionToken: Option[String] = None,
	  /** Deprecated. Matched recent searches of this user. The maximum number of recent searches is 10. This field is a restricted feature. If you want to enable it, contact Retail Search support. This feature is only available when CompleteQueryRequest.visitor_id field is set and UserEvent is imported. The recent searches satisfy the follow rules: &#42; They are ordered from latest to oldest. &#42; They are matched with CompleteQueryRequest.query case insensitively. &#42; They are transformed to lower case. &#42; They are UTF-8 safe. Recent searches are deduplicated. More recent searches will be reserved when duplication happens. */
		recentSearchResults: Option[List[Schema.GoogleCloudRetailV2CompleteQueryResponseRecentSearchResult]] = None,
	  /** A map of matched attribute suggestions. This field is only available for "cloud-retail" dataset. Current supported keys: &#42; `brands` &#42; `categories` */
		attributeResults: Option[Map[String, Schema.GoogleCloudRetailV2CompleteQueryResponseAttributeResult]] = None
	)
	
	case class GoogleCloudRetailV2CompleteQueryResponseCompletionResult(
	  /** The suggestion for the query. */
		suggestion: Option[String] = None,
	  /** Custom attributes for the suggestion term. &#42; For "user-data", the attributes are additional custom attributes ingested through BigQuery. &#42; For "cloud-retail", the attributes are product attributes generated by Cloud Retail. It requires UserEvent.product_details is imported properly. */
		attributes: Option[Map[String, Schema.GoogleCloudRetailV2CustomAttribute]] = None
	)
	
	case class GoogleCloudRetailV2CompleteQueryResponseRecentSearchResult(
	  /** The recent search query. */
		recentSearch: Option[String] = None
	)
	
	case class GoogleCloudRetailV2CompleteQueryResponseAttributeResult(
		suggestions: Option[List[String]] = None
	)
	
	case class GoogleCloudRetailV2ImportCompletionDataRequest(
	  /** Required. The desired input location of the data. */
		inputConfig: Option[Schema.GoogleCloudRetailV2CompletionDataInputConfig] = None,
	  /** Pub/Sub topic for receiving notification. If this field is set, when the import is finished, a notification is sent to specified Pub/Sub topic. The message data is JSON string of a Operation. Format of the Pub/Sub topic is `projects/{project}/topics/{topic}`. */
		notificationPubsubTopic: Option[String] = None
	)
	
	object GoogleCloudRetailV2Control {
		enum SolutionTypesEnum extends Enum[SolutionTypesEnum] { case SOLUTION_TYPE_UNSPECIFIED, SOLUTION_TYPE_RECOMMENDATION, SOLUTION_TYPE_SEARCH }
		enum SearchSolutionUseCaseEnum extends Enum[SearchSolutionUseCaseEnum] { case SEARCH_SOLUTION_USE_CASE_UNSPECIFIED, SEARCH_SOLUTION_USE_CASE_SEARCH, SEARCH_SOLUTION_USE_CASE_BROWSE }
	}
	case class GoogleCloudRetailV2Control(
	  /** A rule control - a condition-action pair. Enacts a set action when the condition is triggered. For example: Boost "gShoe" when query full matches "Running Shoes". */
		rule: Option[Schema.GoogleCloudRetailV2Rule] = None,
	  /** Immutable. Fully qualified name `projects/&#42;/locations/global/catalogs/&#42;/controls/&#42;` */
		name: Option[String] = None,
	  /** Required. The human readable control display name. Used in Retail UI. This field must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an INVALID_ARGUMENT error is thrown. */
		displayName: Option[String] = None,
	  /** Output only. List of serving config ids that are associated with this control in the same Catalog. Note the association is managed via the ServingConfig, this is an output only denormalized view. */
		associatedServingConfigIds: Option[List[String]] = None,
	  /** Required. Immutable. The solution types that the control is used for. Currently we support setting only one type of solution at creation time. Only `SOLUTION_TYPE_SEARCH` value is supported at the moment. If no solution type is provided at creation time, will default to SOLUTION_TYPE_SEARCH. */
		solutionTypes: Option[List[Schema.GoogleCloudRetailV2Control.SolutionTypesEnum]] = None,
	  /** Specifies the use case for the control. Affects what condition fields can be set. Only settable by search controls. Will default to SEARCH_SOLUTION_USE_CASE_SEARCH if not specified. Currently only allow one search_solution_use_case per control. */
		searchSolutionUseCase: Option[List[Schema.GoogleCloudRetailV2Control.SearchSolutionUseCaseEnum]] = None
	)
	
	case class GoogleCloudRetailV2Rule(
	  /** A boost action. */
		boostAction: Option[Schema.GoogleCloudRetailV2RuleBoostAction] = None,
	  /** Redirects a shopper to a specific page. */
		redirectAction: Option[Schema.GoogleCloudRetailV2RuleRedirectAction] = None,
	  /** Treats specific term as a synonym with a group of terms. Group of terms will not be treated as synonyms with the specific term. */
		onewaySynonymsAction: Option[Schema.GoogleCloudRetailV2RuleOnewaySynonymsAction] = None,
	  /** Prevents term from being associated with other terms. */
		doNotAssociateAction: Option[Schema.GoogleCloudRetailV2RuleDoNotAssociateAction] = None,
	  /** Replaces specific terms in the query. */
		replacementAction: Option[Schema.GoogleCloudRetailV2RuleReplacementAction] = None,
	  /** Ignores specific terms from query during search. */
		ignoreAction: Option[Schema.GoogleCloudRetailV2RuleIgnoreAction] = None,
	  /** Filters results. */
		filterAction: Option[Schema.GoogleCloudRetailV2RuleFilterAction] = None,
	  /** Treats a set of terms as synonyms of one another. */
		twowaySynonymsAction: Option[Schema.GoogleCloudRetailV2RuleTwowaySynonymsAction] = None,
	  /** Force returns an attribute as a facet in the request. */
		forceReturnFacetAction: Option[Schema.GoogleCloudRetailV2RuleForceReturnFacetAction] = None,
	  /** Remove an attribute as a facet in the request (if present). */
		removeFacetAction: Option[Schema.GoogleCloudRetailV2RuleRemoveFacetAction] = None,
	  /** Required. The condition that triggers the rule. If the condition is empty, the rule will always apply. */
		condition: Option[Schema.GoogleCloudRetailV2Condition] = None
	)
	
	case class GoogleCloudRetailV2RuleBoostAction(
	  /** Strength of the condition boost, which must be in [-1, 1]. Negative boost means demotion. Default is 0.0. Setting to 1.0 gives the item a big promotion. However, it does not necessarily mean that the boosted item will be the top result at all times, nor that other items will be excluded. Results could still be shown even when none of them matches the condition. And results that are significantly more relevant to the search query can still trump your heavily favored but irrelevant items. Setting to -1.0 gives the item a big demotion. However, results that are deeply relevant might still be shown. The item will have an upstream battle to get a fairly high ranking, but it is not blocked out completely. Setting to 0.0 means no boost applied. The boosting condition is ignored. */
		boost: Option[BigDecimal] = None,
	  /** The filter can have a max size of 5000 characters. An expression which specifies which products to apply an action to. The syntax and supported fields are the same as a filter expression. See SearchRequest.filter for detail syntax and limitations. Examples: &#42; To boost products with product ID "product_1" or "product_2", and color "Red" or "Blue": &#42;(id: ANY("product_1", "product_2")) &#42; &#42;AND &#42; &#42;(colorFamilies: ANY("Red", "Blue")) &#42; */
		productsFilter: Option[String] = None
	)
	
	case class GoogleCloudRetailV2RuleRedirectAction(
	  /** URL must have length equal or less than 2000 characters. */
		redirectUri: Option[String] = None
	)
	
	case class GoogleCloudRetailV2RuleOnewaySynonymsAction(
	  /** Terms from the search query. Will treat synonyms as their synonyms. Not themselves synonyms of the synonyms. Can specify up to 100 terms. */
		queryTerms: Option[List[String]] = None,
	  /** Defines a set of synonyms. Cannot contain duplicates. Can specify up to 100 synonyms. */
		synonyms: Option[List[String]] = None,
	  /** Will be [deprecated = true] post migration; */
		onewayTerms: Option[List[String]] = None
	)
	
	case class GoogleCloudRetailV2RuleDoNotAssociateAction(
	  /** Terms from the search query. Will not consider do_not_associate_terms for search if in search query. Can specify up to 100 terms. */
		queryTerms: Option[List[String]] = None,
	  /** Cannot contain duplicates or the query term. Can specify up to 100 terms. */
		doNotAssociateTerms: Option[List[String]] = None,
	  /** Will be [deprecated = true] post migration; */
		terms: Option[List[String]] = None
	)
	
	case class GoogleCloudRetailV2RuleReplacementAction(
	  /** Terms from the search query. Will be replaced by replacement term. Can specify up to 100 terms. */
		queryTerms: Option[List[String]] = None,
	  /** Term that will be used for replacement. */
		replacementTerm: Option[String] = None,
	  /** Will be [deprecated = true] post migration; */
		term: Option[String] = None
	)
	
	case class GoogleCloudRetailV2RuleIgnoreAction(
	  /** Terms to ignore in the search query. */
		ignoreTerms: Option[List[String]] = None
	)
	
	case class GoogleCloudRetailV2RuleFilterAction(
	  /** A filter to apply on the matching condition results. Supported features: &#42; filter must be set. &#42; Filter syntax is identical to SearchRequest.filter. For more information, see [Filter](/retail/docs/filter-and-order#filter). &#42; To filter products with product ID "product_1" or "product_2", and color "Red" or "Blue": &#42;(id: ANY("product_1", "product_2")) &#42; &#42;AND &#42; &#42;(colorFamilies: ANY("Red", "Blue")) &#42; */
		filter: Option[String] = None
	)
	
	case class GoogleCloudRetailV2RuleTwowaySynonymsAction(
	  /** Defines a set of synonyms. Can specify up to 100 synonyms. Must specify at least 2 synonyms. */
		synonyms: Option[List[String]] = None
	)
	
	case class GoogleCloudRetailV2RuleForceReturnFacetAction(
	  /** Each instance corresponds to a force return attribute for the given condition. There can't be more 15 instances here. */
		facetPositionAdjustments: Option[List[Schema.GoogleCloudRetailV2RuleForceReturnFacetActionFacetPositionAdjustment]] = None
	)
	
	case class GoogleCloudRetailV2RuleForceReturnFacetActionFacetPositionAdjustment(
	  /** The attribute name to force return as a facet. Each attribute name should be a valid attribute name, be non-empty and contain at most 80 characters long. */
		attributeName: Option[String] = None,
	  /** This is the position in the request as explained above. It should be strictly positive be at most 100. */
		position: Option[Int] = None
	)
	
	case class GoogleCloudRetailV2RuleRemoveFacetAction(
	  /** The attribute names (i.e. facet keys) to remove from the dynamic facets (if present in the request). There can't be more 3 attribute names. Each attribute name should be a valid attribute name, be non-empty and contain at most 80 characters. */
		attributeNames: Option[List[String]] = None
	)
	
	case class GoogleCloudRetailV2Condition(
	  /** A list (up to 10 entries) of terms to match the query on. If not specified, match all queries. If many query terms are specified, the condition is matched if any of the terms is a match (i.e. using the OR operator). */
		queryTerms: Option[List[Schema.GoogleCloudRetailV2ConditionQueryTerm]] = None,
	  /** Range of time(s) specifying when Condition is active. Condition true if any time range matches. */
		activeTimeRange: Option[List[Schema.GoogleCloudRetailV2ConditionTimeRange]] = None,
	  /** Used to support browse uses cases. A list (up to 10 entries) of categories or departments. The format should be the same as UserEvent.page_categories; */
		pageCategories: Option[List[String]] = None
	)
	
	case class GoogleCloudRetailV2ConditionQueryTerm(
	  /** The value of the term to match on. Value cannot be empty. Value can have at most 3 terms if specified as a partial match. Each space separated string is considered as one term. For example, "a b c" is 3 terms and allowed, but " a b c d" is 4 terms and not allowed for a partial match. */
		value: Option[String] = None,
	  /** Whether this is supposed to be a full or partial match. */
		fullMatch: Option[Boolean] = None
	)
	
	case class GoogleCloudRetailV2ConditionTimeRange(
	  /** Start of time range. Range is inclusive. */
		startTime: Option[String] = None,
	  /** End of time range. Range is inclusive. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudRetailV2ListControlsResponse(
	  /** All the Controls for a given catalog. */
		controls: Option[List[Schema.GoogleCloudRetailV2Control]] = None,
	  /** Pagination token, if not returned indicates the last page. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudRetailV2GenerativeQuestionsFeatureConfig(
	  /** Required. Resource name of the affected catalog. Format: projects/{project}/locations/{location}/catalogs/{catalog} */
		catalog: Option[String] = None,
	  /** Optional. Determines whether questions will be used at serving time. Note: This feature cannot be enabled until initial data requirements are satisfied. */
		featureEnabled: Option[Boolean] = None,
	  /** Optional. Minimum number of products in the response to trigger follow-up questions. Value must be 0 or positive. */
		minimumProducts: Option[Int] = None
	)
	
	case class GoogleCloudRetailV2ListGenerativeQuestionConfigsResponse(
	  /** All the questions for a given catalog. */
		generativeQuestionConfigs: Option[List[Schema.GoogleCloudRetailV2GenerativeQuestionConfig]] = None
	)
	
	case class GoogleCloudRetailV2GenerativeQuestionConfig(
	  /** Required. Resource name of the catalog. Format: projects/{project}/locations/{location}/catalogs/{catalog} */
		catalog: Option[String] = None,
	  /** Required. The facet to which the question is associated. */
		facet: Option[String] = None,
	  /** Output only. The LLM generated question. */
		generatedQuestion: Option[String] = None,
	  /** Optional. The question that will be used at serving time. Question can have a max length of 300 bytes. When not populated, generated_question should be used. */
		finalQuestion: Option[String] = None,
	  /** Output only. Values that can be used to answer the question. */
		exampleValues: Option[List[String]] = None,
	  /** Output only. The ratio of how often a question was asked. */
		frequency: Option[BigDecimal] = None,
	  /** Optional. Whether the question is asked at serving time. */
		allowedInConversation: Option[Boolean] = None
	)
	
	case class GoogleCloudRetailV2BatchUpdateGenerativeQuestionConfigsRequest(
	  /** Required. The updates question configs. */
		requests: Option[List[Schema.GoogleCloudRetailV2UpdateGenerativeQuestionConfigRequest]] = None
	)
	
	case class GoogleCloudRetailV2UpdateGenerativeQuestionConfigRequest(
	  /** Required. The question to update. */
		generativeQuestionConfig: Option[Schema.GoogleCloudRetailV2GenerativeQuestionConfig] = None,
	  /** Optional. Indicates which fields in the provided GenerativeQuestionConfig to update. The following are NOT supported: &#42; GenerativeQuestionConfig.frequency If not set or empty, all supported fields are updated. */
		updateMask: Option[String] = None
	)
	
	case class GoogleCloudRetailV2BatchUpdateGenerativeQuestionConfigsResponse(
	  /** Optional. The updates question configs. */
		generativeQuestionConfigs: Option[List[Schema.GoogleCloudRetailV2GenerativeQuestionConfig]] = None
	)
	
	object GoogleCloudRetailV2Model {
		enum TrainingStateEnum extends Enum[TrainingStateEnum] { case TRAINING_STATE_UNSPECIFIED, PAUSED, TRAINING }
		enum ServingStateEnum extends Enum[ServingStateEnum] { case SERVING_STATE_UNSPECIFIED, INACTIVE, ACTIVE, TUNED }
		enum PeriodicTuningStateEnum extends Enum[PeriodicTuningStateEnum] { case PERIODIC_TUNING_STATE_UNSPECIFIED, PERIODIC_TUNING_DISABLED, ALL_TUNING_DISABLED, PERIODIC_TUNING_ENABLED }
		enum DataStateEnum extends Enum[DataStateEnum] { case DATA_STATE_UNSPECIFIED, DATA_OK, DATA_ERROR }
		enum FilteringOptionEnum extends Enum[FilteringOptionEnum] { case RECOMMENDATIONS_FILTERING_OPTION_UNSPECIFIED, RECOMMENDATIONS_FILTERING_DISABLED, RECOMMENDATIONS_FILTERING_ENABLED }
	}
	case class GoogleCloudRetailV2Model(
	  /** Required. The fully qualified resource name of the model. Format: `projects/{project_number}/locations/{location_id}/catalogs/{catalog_id}/models/{model_id}` catalog_id has char limit of 50. recommendation_model_id has char limit of 40. */
		name: Option[String] = None,
	  /** Required. The display name of the model. Should be human readable, used to display Recommendation Models in the Retail Cloud Console Dashboard. UTF-8 encoded string with limit of 1024 characters. */
		displayName: Option[String] = None,
	  /** Optional. The training state that the model is in (e.g. `TRAINING` or `PAUSED`). Since part of the cost of running the service is frequency of training - this can be used to determine when to train model in order to control cost. If not specified: the default value for `CreateModel` method is `TRAINING`. The default value for `UpdateModel` method is to keep the state the same as before. */
		trainingState: Option[Schema.GoogleCloudRetailV2Model.TrainingStateEnum] = None,
	  /** Output only. The serving state of the model: `ACTIVE`, `NOT_ACTIVE`. */
		servingState: Option[Schema.GoogleCloudRetailV2Model.ServingStateEnum] = None,
	  /** Output only. Timestamp the Recommendation Model was created at. */
		createTime: Option[String] = None,
	  /** Output only. Timestamp the Recommendation Model was last updated. E.g. if a Recommendation Model was paused - this would be the time the pause was initiated. */
		updateTime: Option[String] = None,
	  /** Required. The type of model e.g. `home-page`. Currently supported values: `recommended-for-you`, `others-you-may-like`, `frequently-bought-together`, `page-optimization`, `similar-items`, `buy-it-again`, `on-sale-items`, and `recently-viewed`(readonly value). This field together with optimization_objective describe model metadata to use to control model training and serving. See https://cloud.google.com/retail/docs/models for more details on what the model metadata control and which combination of parameters are valid. For invalid combinations of parameters (e.g. type = `frequently-bought-together` and optimization_objective = `ctr`), you receive an error 400 if you try to create/update a recommendation with this set of knobs. */
		`type`: Option[String] = None,
	  /** Optional. The optimization objective e.g. `cvr`. Currently supported values: `ctr`, `cvr`, `revenue-per-order`. If not specified, we choose default based on model type. Default depends on type of recommendation: `recommended-for-you` => `ctr` `others-you-may-like` => `ctr` `frequently-bought-together` => `revenue_per_order` This field together with optimization_objective describe model metadata to use to control model training and serving. See https://cloud.google.com/retail/docs/models for more details on what the model metadata control and which combination of parameters are valid. For invalid combinations of parameters (e.g. type = `frequently-bought-together` and optimization_objective = `ctr`), you receive an error 400 if you try to create/update a recommendation with this set of knobs. */
		optimizationObjective: Option[String] = None,
	  /** Optional. The state of periodic tuning. The period we use is 3 months - to do a one-off tune earlier use the `TuneModel` method. Default value is `PERIODIC_TUNING_ENABLED`. */
		periodicTuningState: Option[Schema.GoogleCloudRetailV2Model.PeriodicTuningStateEnum] = None,
	  /** Output only. The timestamp when the latest successful tune finished. */
		lastTuneTime: Option[String] = None,
	  /** Output only. The tune operation associated with the model. Can be used to determine if there is an ongoing tune for this recommendation. Empty field implies no tune is goig on. */
		tuningOperation: Option[String] = None,
	  /** Output only. The state of data requirements for this model: `DATA_OK` and `DATA_ERROR`. Recommendation model cannot be trained if the data is in `DATA_ERROR` state. Recommendation model can have `DATA_ERROR` state even if serving state is `ACTIVE`: models were trained successfully before, but cannot be refreshed because model no longer has sufficient data for training. */
		dataState: Option[Schema.GoogleCloudRetailV2Model.DataStateEnum] = None,
	  /** Optional. If `RECOMMENDATIONS_FILTERING_ENABLED`, recommendation filtering by attributes is enabled for the model. */
		filteringOption: Option[Schema.GoogleCloudRetailV2Model.FilteringOptionEnum] = None,
	  /** Output only. The list of valid serving configs associated with the PageOptimizationConfig. */
		servingConfigLists: Option[List[Schema.GoogleCloudRetailV2ModelServingConfigList]] = None,
	  /** Optional. Additional model features config. */
		modelFeaturesConfig: Option[Schema.GoogleCloudRetailV2ModelModelFeaturesConfig] = None
	)
	
	case class GoogleCloudRetailV2ModelServingConfigList(
	  /** Optional. A set of valid serving configs that may be used for `PAGE_OPTIMIZATION`. */
		servingConfigIds: Option[List[String]] = None
	)
	
	case class GoogleCloudRetailV2ModelModelFeaturesConfig(
	  /** Additional configs for frequently-bought-together models. */
		frequentlyBoughtTogetherConfig: Option[Schema.GoogleCloudRetailV2ModelFrequentlyBoughtTogetherFeaturesConfig] = None
	)
	
	object GoogleCloudRetailV2ModelFrequentlyBoughtTogetherFeaturesConfig {
		enum ContextProductsTypeEnum extends Enum[ContextProductsTypeEnum] { case CONTEXT_PRODUCTS_TYPE_UNSPECIFIED, SINGLE_CONTEXT_PRODUCT, MULTIPLE_CONTEXT_PRODUCTS }
	}
	case class GoogleCloudRetailV2ModelFrequentlyBoughtTogetherFeaturesConfig(
	  /** Optional. Specifies the context of the model when it is used in predict requests. Can only be set for the `frequently-bought-together` type. If it isn't specified, it defaults to MULTIPLE_CONTEXT_PRODUCTS. */
		contextProductsType: Option[Schema.GoogleCloudRetailV2ModelFrequentlyBoughtTogetherFeaturesConfig.ContextProductsTypeEnum] = None
	)
	
	case class GoogleCloudRetailV2PauseModelRequest(
	
	)
	
	case class GoogleCloudRetailV2ResumeModelRequest(
	
	)
	
	case class GoogleCloudRetailV2ListModelsResponse(
	  /** List of Models. */
		models: Option[List[Schema.GoogleCloudRetailV2Model]] = None,
	  /** Pagination token, if not returned indicates the last page. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudRetailV2TuneModelRequest(
	
	)
	
	case class GoogleCloudRetailV2UserEvent(
	  /** Required. User event type. Allowed values are: &#42; `add-to-cart`: Products being added to cart. &#42; `remove-from-cart`: Products being removed from cart. &#42; `category-page-view`: Special pages such as sale or promotion pages viewed. &#42; `detail-page-view`: Products detail page viewed. &#42; `home-page-view`: Homepage viewed. &#42; `promotion-offered`: Promotion is offered to a user. &#42; `promotion-not-offered`: Promotion is not offered to a user. &#42; `purchase-complete`: User finishing a purchase. &#42; `search`: Product search. &#42; `shopping-cart-page-view`: User viewing a shopping cart. */
		eventType: Option[String] = None,
	  /** Required. A unique identifier for tracking visitors. For example, this could be implemented with an HTTP cookie, which should be able to uniquely identify a visitor on a single device. This unique identifier should not change if the visitor log in/out of the website. Don't set the field to the same fixed ID for different users. This mixes the event history of those users together, which results in degraded model quality. The field must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an INVALID_ARGUMENT error is returned. The field should not contain PII or user-data. We recommend to use Google Analytics [Client ID](https://developers.google.com/analytics/devguides/collection/analyticsjs/field-reference#clientId) for this field. */
		visitorId: Option[String] = None,
	  /** A unique identifier for tracking a visitor session with a length limit of 128 bytes. A session is an aggregation of an end user behavior in a time span. A general guideline to populate the sesion_id: 1. If user has no activity for 30 min, a new session_id should be assigned. 2. The session_id should be unique across users, suggest use uuid or add visitor_id as prefix. */
		sessionId: Option[String] = None,
	  /** Only required for UserEventService.ImportUserEvents method. Timestamp of when the user event happened. */
		eventTime: Option[String] = None,
	  /** A list of identifiers for the independent experiment groups this user event belongs to. This is used to distinguish between user events associated with different experiment setups (e.g. using Retail API, using different recommendation models). */
		experimentIds: Option[List[String]] = None,
	  /** Highly recommended for user events that are the result of PredictionService.Predict. This field enables accurate attribution of recommendation model performance. The value must be a valid PredictResponse.attribution_token for user events that are the result of PredictionService.Predict. The value must be a valid SearchResponse.attribution_token for user events that are the result of SearchService.Search. This token enables us to accurately attribute page view or purchase back to the event and the particular predict response containing this clicked/purchased product. If user clicks on product K in the recommendation results, pass PredictResponse.attribution_token as a URL parameter to product K's page. When recording events on product K's page, log the PredictResponse.attribution_token to this field. */
		attributionToken: Option[String] = None,
	  /** The main product details related to the event. This field is optional except for the following event types: &#42; `add-to-cart` &#42; `detail-page-view` &#42; `purchase-complete` In a `search` event, this field represents the products returned to the end user on the current page (the end user may have not finished browsing the whole page yet). When a new page is returned to the end user, after pagination/filtering/ordering even for the same query, a new `search` event with different product_details is desired. The end user may have not finished browsing the whole page yet. */
		productDetails: Option[List[Schema.GoogleCloudRetailV2ProductDetail]] = None,
	  /** The main auto-completion details related to the event. This field should be set for `search` event when autocomplete function is enabled and the user clicks a suggestion for search. */
		completionDetail: Option[Schema.GoogleCloudRetailV2CompletionDetail] = None,
	  /** Extra user event features to include in the recommendation model. If you provide custom attributes for ingested user events, also include them in the user events that you associate with prediction requests. Custom attribute formatting must be consistent between imported events and events provided with prediction requests. This lets the Retail API use those custom attributes when training models and serving predictions, which helps improve recommendation quality. This field needs to pass all below criteria, otherwise an INVALID_ARGUMENT error is returned: &#42; The key must be a UTF-8 encoded string with a length limit of 5,000 characters. &#42; For text attributes, at most 400 values are allowed. Empty values are not allowed. Each value must be a UTF-8 encoded string with a length limit of 256 characters. &#42; For number attributes, at most 400 values are allowed. For product recommendations, an example of extra user information is traffic_channel, which is how a user arrives at the site. Users can arrive at the site by coming to the site directly, coming through Google search, or in other ways. */
		attributes: Option[Map[String, Schema.GoogleCloudRetailV2CustomAttribute]] = None,
	  /** The ID or name of the associated shopping cart. This ID is used to associate multiple items added or present in the cart before purchase. This can only be set for `add-to-cart`, `purchase-complete`, or `shopping-cart-page-view` events. */
		cartId: Option[String] = None,
	  /** A transaction represents the entire purchase transaction. Required for `purchase-complete` events. Other event types should not set this field. Otherwise, an INVALID_ARGUMENT error is returned. */
		purchaseTransaction: Option[Schema.GoogleCloudRetailV2PurchaseTransaction] = None,
	  /** The user's search query. See SearchRequest.query for definition. The value must be a UTF-8 encoded string with a length limit of 5,000 characters. Otherwise, an INVALID_ARGUMENT error is returned. At least one of search_query or page_categories is required for `search` events. Other event types should not set this field. Otherwise, an INVALID_ARGUMENT error is returned. */
		searchQuery: Option[String] = None,
	  /** The filter syntax consists of an expression language for constructing a predicate from one or more fields of the products being filtered. See SearchRequest.filter for definition and syntax. The value must be a UTF-8 encoded string with a length limit of 1,000 characters. Otherwise, an INVALID_ARGUMENT error is returned. */
		filter: Option[String] = None,
	  /** The order in which products are returned. See SearchRequest.order_by for definition and syntax. The value must be a UTF-8 encoded string with a length limit of 1,000 characters. Otherwise, an INVALID_ARGUMENT error is returned. This can only be set for `search` events. Other event types should not set this field. Otherwise, an INVALID_ARGUMENT error is returned. */
		orderBy: Option[String] = None,
	  /** An integer that specifies the current offset for pagination (the 0-indexed starting location, amongst the products deemed by the API as relevant). See SearchRequest.offset for definition. If this field is negative, an INVALID_ARGUMENT is returned. This can only be set for `search` events. Other event types should not set this field. Otherwise, an INVALID_ARGUMENT error is returned. */
		offset: Option[Int] = None,
	  /** The categories associated with a category page. To represent full path of category, use '>' sign to separate different hierarchies. If '>' is part of the category name, replace it with other character(s). Category pages include special pages such as sales or promotions. For instance, a special sale page may have the category hierarchy: "pageCategories" : ["Sales > 2017 Black Friday Deals"]. Required for `category-page-view` events. At least one of search_query or page_categories is required for `search` events. Other event types should not set this field. Otherwise, an INVALID_ARGUMENT error is returned. */
		pageCategories: Option[List[String]] = None,
	  /** User information. */
		userInfo: Option[Schema.GoogleCloudRetailV2UserInfo] = None,
	  /** Complete URL (window.location.href) of the user's current page. When using the client side event reporting with JavaScript pixel and Google Tag Manager, this value is filled in automatically. Maximum length 5,000 characters. */
		uri: Option[String] = None,
	  /** The referrer URL of the current page. When using the client side event reporting with JavaScript pixel and Google Tag Manager, this value is filled in automatically. */
		referrerUri: Option[String] = None,
	  /** A unique ID of a web page view. This should be kept the same for all user events triggered from the same pageview. For example, an item detail page view could trigger multiple events as the user is browsing the page. The `pageViewId` property should be kept the same for all these events so that they can be grouped together properly. When using the client side event reporting with JavaScript pixel and Google Tag Manager, this value is filled in automatically. */
		pageViewId: Option[String] = None,
	  /** The entity for customers that may run multiple different entities, domains, sites or regions, for example, `Google US`, `Google Ads`, `Waymo`, `google.com`, `youtube.com`, etc. We recommend that you set this field to get better per-entity search, completion, and prediction results. */
		entity: Option[String] = None
	)
	
	case class GoogleCloudRetailV2ProductDetail(
	  /** Required. Product information. Required field(s): &#42; Product.id Optional override field(s): &#42; Product.price_info If any supported optional fields are provided, we will treat them as a full override when looking up product information from the catalog. Thus, it is important to ensure that the overriding fields are accurate and complete. All other product fields are ignored and instead populated via catalog lookup after event ingestion. */
		product: Option[Schema.GoogleCloudRetailV2Product] = None,
	  /** Quantity of the product associated with the user event. For example, this field will be 2 if two products are added to the shopping cart for `purchase-complete` event. Required for `add-to-cart` and `purchase-complete` event types. */
		quantity: Option[Int] = None
	)
	
	case class GoogleCloudRetailV2CompletionDetail(
	  /** Completion attribution token in CompleteQueryResponse.attribution_token. */
		completionAttributionToken: Option[String] = None,
	  /** End user selected CompleteQueryResponse.CompletionResult.suggestion. */
		selectedSuggestion: Option[String] = None,
	  /** End user selected CompleteQueryResponse.CompletionResult.suggestion position, starting from 0. */
		selectedPosition: Option[Int] = None
	)
	
	case class GoogleCloudRetailV2PurchaseTransaction(
	  /** The transaction ID with a length limit of 128 characters. */
		id: Option[String] = None,
	  /** Required. Total non-zero revenue or grand total associated with the transaction. This value include shipping, tax, or other adjustments to total revenue that you want to include as part of your revenue calculations. */
		revenue: Option[BigDecimal] = None,
	  /** All the taxes associated with the transaction. */
		tax: Option[BigDecimal] = None,
	  /** All the costs associated with the products. These can be manufacturing costs, shipping expenses not borne by the end user, or any other costs, such that: &#42; Profit = revenue - tax - cost */
		cost: Option[BigDecimal] = None,
	  /** Required. Currency code. Use three-character ISO-4217 code. */
		currencyCode: Option[String] = None
	)
	
	case class GoogleApiHttpBody(
	  /** The HTTP Content-Type header value specifying the content type of the body. */
		contentType: Option[String] = None,
	  /** The HTTP request/response body as raw binary. */
		data: Option[String] = None,
	  /** Application specific response metadata. Must be set in the first response for streaming APIs. */
		extensions: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GoogleCloudRetailV2PurgeUserEventsRequest(
	  /** Required. The filter string to specify the events to be deleted with a length limit of 5,000 characters. Empty string filter is not allowed. The eligible fields for filtering are: &#42; `eventType`: Double quoted UserEvent.event_type string. &#42; `eventTime`: in ISO 8601 "zulu" format. &#42; `visitorId`: Double quoted string. Specifying this will delete all events associated with a visitor. &#42; `userId`: Double quoted string. Specifying this will delete all events associated with a user. Examples: &#42; Deleting all events in a time range: `eventTime > "2012-04-23T18:25:43.511Z" eventTime < "2012-04-23T18:30:43.511Z"` &#42; Deleting specific eventType in time range: `eventTime > "2012-04-23T18:25:43.511Z" eventType = "detail-page-view"` &#42; Deleting all events for a specific visitor: `visitorId = "visitor1024"` The filtering fields are assumed to have an implicit AND. */
		filter: Option[String] = None,
	  /** Actually perform the purge. If `force` is set to false, the method will return the expected purge count without deleting any user events. */
		force: Option[Boolean] = None
	)
	
	case class GoogleCloudRetailV2ImportUserEventsRequest(
	  /** Required. The desired input location of the data. */
		inputConfig: Option[Schema.GoogleCloudRetailV2UserEventInputConfig] = None,
	  /** The desired location of errors incurred during the Import. Cannot be set for inline user event imports. */
		errorsConfig: Option[Schema.GoogleCloudRetailV2ImportErrorsConfig] = None
	)
	
	case class GoogleCloudRetailV2UserEventInputConfig(
	  /** Required. The Inline source for the input content for UserEvents. */
		userEventInlineSource: Option[Schema.GoogleCloudRetailV2UserEventInlineSource] = None,
	  /** Required. Google Cloud Storage location for the input content. */
		gcsSource: Option[Schema.GoogleCloudRetailV2GcsSource] = None,
	  /** Required. BigQuery input source. */
		bigQuerySource: Option[Schema.GoogleCloudRetailV2BigQuerySource] = None
	)
	
	case class GoogleCloudRetailV2UserEventInlineSource(
	  /** Required. A list of user events to import. Recommended max of 10k items. */
		userEvents: Option[List[Schema.GoogleCloudRetailV2UserEvent]] = None
	)
	
	case class GoogleCloudRetailV2GcsSource(
	  /** Required. Google Cloud Storage URIs to input files. URI can be up to 2000 characters long. URIs can match the full object path (for example, `gs://bucket/directory/object.json`) or a pattern matching one or more files, such as `gs://bucket/directory/&#42;.json`. A request can contain at most 100 files, and each file can be up to 2 GB. See [Importing product information](https://cloud.google.com/retail/recommendations-ai/docs/upload-catalog) for the expected file format and setup instructions. */
		inputUris: Option[List[String]] = None,
	  /** The schema to use when parsing the data from the source. Supported values for product imports: &#42; `product` (default): One JSON Product per line. Each product must have a valid Product.id. &#42; `product_merchant_center`: See [Importing catalog data from Merchant Center](https://cloud.google.com/retail/recommendations-ai/docs/upload-catalog#mc). Supported values for user events imports: &#42; `user_event` (default): One JSON UserEvent per line. &#42; `user_event_ga360`: Using https://support.google.com/analytics/answer/3437719. Supported values for control imports: &#42; `control` (default): One JSON Control per line. Supported values for catalog attribute imports: &#42; `catalog_attribute` (default): One CSV CatalogAttribute per line. */
		dataSchema: Option[String] = None
	)
	
	case class GoogleCloudRetailV2ImportErrorsConfig(
	  /** Google Cloud Storage prefix for import errors. This must be an empty, existing Cloud Storage directory. Import errors are written to sharded files in this directory, one per line, as a JSON-encoded `google.rpc.Status` message. */
		gcsPrefix: Option[String] = None
	)
	
	object GoogleCloudRetailV2RejoinUserEventsRequest {
		enum UserEventRejoinScopeEnum extends Enum[UserEventRejoinScopeEnum] { case USER_EVENT_REJOIN_SCOPE_UNSPECIFIED, JOINED_EVENTS, UNJOINED_EVENTS }
	}
	case class GoogleCloudRetailV2RejoinUserEventsRequest(
	  /** The type of the user event rejoin to define the scope and range of the user events to be rejoined with the latest product catalog. Defaults to `USER_EVENT_REJOIN_SCOPE_UNSPECIFIED` if this field is not set, or set to an invalid integer value. */
		userEventRejoinScope: Option[Schema.GoogleCloudRetailV2RejoinUserEventsRequest.UserEventRejoinScopeEnum] = None
	)
	
	case class GoogleCloudRetailV2PredictRequest(
	  /** Required. Context about the user, what they are looking at and what action they took to trigger the predict request. Note that this user event detail won't be ingested to userEvent logs. Thus, a separate userEvent write request is required for event logging. Don't set UserEvent.visitor_id or UserInfo.user_id to the same fixed ID for different users. If you are trying to receive non-personalized recommendations (not recommended; this can negatively impact model performance), instead set UserEvent.visitor_id to a random unique ID and leave UserInfo.user_id unset. */
		userEvent: Option[Schema.GoogleCloudRetailV2UserEvent] = None,
	  /** Maximum number of results to return. Set this property to the number of prediction results needed. If zero, the service will choose a reasonable default. The maximum allowed value is 100. Values above 100 will be coerced to 100. */
		pageSize: Option[Int] = None,
	  /** This field is not used; leave it unset. */
		pageToken: Option[String] = None,
	  /** Filter for restricting prediction results with a length limit of 5,000 characters. Accepts values for tags and the `filterOutOfStockItems` flag. &#42; Tag expressions. Restricts predictions to products that match all of the specified tags. Boolean operators `OR` and `NOT` are supported if the expression is enclosed in parentheses, and must be separated from the tag values by a space. `-"tagA"` is also supported and is equivalent to `NOT "tagA"`. Tag values must be double quoted UTF-8 encoded strings with a size limit of 1,000 characters. Note: "Recently viewed" models don't support tag filtering at the moment. &#42; filterOutOfStockItems. Restricts predictions to products that do not have a stockState value of OUT_OF_STOCK. Examples: &#42; tag=("Red" OR "Blue") tag="New-Arrival" tag=(NOT "promotional") &#42; filterOutOfStockItems tag=(-"promotional") &#42; filterOutOfStockItems If your filter blocks all prediction results, the API will return &#42;no&#42; results. If instead you want empty result sets to return generic (unfiltered) popular products, set `strictFiltering` to False in `PredictRequest.params`. Note that the API will never return items with storageStatus of "EXPIRED" or "DELETED" regardless of filter choices. If `filterSyntaxV2` is set to true under the `params` field, then attribute-based expressions are expected instead of the above described tag-based syntax. Examples: &#42; (colors: ANY("Red", "Blue")) AND NOT (categories: ANY("Phones")) &#42; (availability: ANY("IN_STOCK")) AND (colors: ANY("Red") OR categories: ANY("Phones")) For more information, see [Filter recommendations](https://cloud.google.com/retail/docs/filter-recs). */
		filter: Option[String] = None,
	  /** Use validate only mode for this prediction query. If set to true, a dummy model will be used that returns arbitrary products. Note that the validate only mode should only be used for testing the API, or if the model is not ready. */
		validateOnly: Option[Boolean] = None,
	  /** Additional domain specific parameters for the predictions. Allowed values: &#42; `returnProduct`: Boolean. If set to true, the associated product object will be returned in the `results.metadata` field in the prediction response. &#42; `returnScore`: Boolean. If set to true, the prediction 'score' corresponding to each returned product will be set in the `results.metadata` field in the prediction response. The given 'score' indicates the probability of a product being clicked/purchased given the user's context and history. &#42; `strictFiltering`: Boolean. True by default. If set to false, the service will return generic (unfiltered) popular products instead of empty if your filter blocks all prediction results. &#42; `priceRerankLevel`: String. Default empty. If set to be non-empty, then it needs to be one of {'no-price-reranking', 'low-price-reranking', 'medium-price-reranking', 'high-price-reranking'}. This gives request-level control and adjusts prediction results based on product price. &#42; `diversityLevel`: String. Default empty. If set to be non-empty, then it needs to be one of {'no-diversity', 'low-diversity', 'medium-diversity', 'high-diversity', 'auto-diversity'}. This gives request-level control and adjusts prediction results based on product category. &#42; `filterSyntaxV2`: Boolean. False by default. If set to true, the `filter` field is interpreteted according to the new, attribute-based syntax. */
		params: Option[Map[String, JsValue]] = None,
	  /** The labels applied to a resource must meet the following requirements: &#42; Each resource can have multiple labels, up to a maximum of 64. &#42; Each label must be a key-value pair. &#42; Keys have a minimum length of 1 character and a maximum length of 63 characters and cannot be empty. Values can be empty and have a maximum length of 63 characters. &#42; Keys and values can contain only lowercase letters, numeric characters, underscores, and dashes. All characters must use UTF-8 encoding, and international characters are allowed. &#42; The key portion of a label must be unique. However, you can use the same key with multiple resources. &#42; Keys must start with a lowercase letter or international character. See [Google Cloud Document](https://cloud.google.com/resource-manager/docs/creating-managing-labels#requirements) for more details. */
		labels: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudRetailV2PredictResponse(
	  /** A list of recommended products. The order represents the ranking (from the most relevant product to the least). */
		results: Option[List[Schema.GoogleCloudRetailV2PredictResponsePredictionResult]] = None,
	  /** A unique attribution token. This should be included in the UserEvent logs resulting from this recommendation, which enables accurate attribution of recommendation model performance. */
		attributionToken: Option[String] = None,
	  /** IDs of products in the request that were missing from the inventory. */
		missingIds: Option[List[String]] = None,
	  /** True if the validateOnly property was set in the request. */
		validateOnly: Option[Boolean] = None
	)
	
	case class GoogleCloudRetailV2PredictResponsePredictionResult(
	  /** ID of the recommended product */
		id: Option[String] = None,
	  /** Additional product metadata / annotations. Possible values: &#42; `product`: JSON representation of the product. Is set if `returnProduct` is set to true in `PredictRequest.params`. &#42; `score`: Prediction score in double value. Is set if `returnScore` is set to true in `PredictRequest.params`. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudRetailV2ListProductsResponse(
	  /** The Products. */
		products: Option[List[Schema.GoogleCloudRetailV2Product]] = None,
	  /** A token that can be sent as ListProductsRequest.page_token to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudRetailV2PurgeProductsRequest(
	  /** Required. The filter string to specify the products to be deleted with a length limit of 5,000 characters. Empty string filter is not allowed. "&#42;" implies delete all items in a branch. The eligible fields for filtering are: &#42; `availability`: Double quoted Product.availability string. &#42; `create_time` : in ISO 8601 "zulu" format. Supported syntax: &#42; Comparators (">", "<", ">=", "<=", "="). Examples: &#42; create_time <= "2015-02-13T17:05:46Z" &#42; availability = "IN_STOCK" &#42; Conjunctions ("AND") Examples: &#42; create_time <= "2015-02-13T17:05:46Z" AND availability = "PREORDER" &#42; Disjunctions ("OR") Examples: &#42; create_time <= "2015-02-13T17:05:46Z" OR availability = "IN_STOCK" &#42; Can support nested queries. Examples: &#42; (create_time <= "2015-02-13T17:05:46Z" AND availability = "PREORDER") OR (create_time >= "2015-02-14T13:03:32Z" AND availability = "IN_STOCK") &#42; Filter Limits: &#42; Filter should not contain more than 6 conditions. &#42; Max nesting depth should not exceed 2 levels. Examples queries: &#42; Delete back order products created before a timestamp. create_time <= "2015-02-13T17:05:46Z" OR availability = "BACKORDER" */
		filter: Option[String] = None,
	  /** Actually perform the purge. If `force` is set to false, the method will return the expected purge count without deleting any products. */
		force: Option[Boolean] = None
	)
	
	object GoogleCloudRetailV2ImportProductsRequest {
		enum ReconciliationModeEnum extends Enum[ReconciliationModeEnum] { case RECONCILIATION_MODE_UNSPECIFIED, INCREMENTAL, FULL }
	}
	case class GoogleCloudRetailV2ImportProductsRequest(
	  /** Deprecated. This field has no effect. */
		requestId: Option[String] = None,
	  /** Required. The desired input location of the data. */
		inputConfig: Option[Schema.GoogleCloudRetailV2ProductInputConfig] = None,
	  /** The desired location of errors incurred during the Import. */
		errorsConfig: Option[Schema.GoogleCloudRetailV2ImportErrorsConfig] = None,
	  /** Indicates which fields in the provided imported `products` to update. If not set, all fields are updated. If provided, only the existing product fields are updated. Missing products will not be created. */
		updateMask: Option[String] = None,
	  /** The mode of reconciliation between existing products and the products to be imported. Defaults to ReconciliationMode.INCREMENTAL. */
		reconciliationMode: Option[Schema.GoogleCloudRetailV2ImportProductsRequest.ReconciliationModeEnum] = None,
	  /** Full Pub/Sub topic name for receiving notification. If this field is set, when the import is finished, a notification is sent to specified Pub/Sub topic. The message data is JSON string of a Operation. Format of the Pub/Sub topic is `projects/{project}/topics/{topic}`. It has to be within the same project as ImportProductsRequest.parent. Make sure that both `cloud-retail-customer-data-access@system.gserviceaccount.com` and `service-@gcp-sa-retail.iam.gserviceaccount.com` have the `pubsub.topics.publish` IAM permission on the topic. Only supported when ImportProductsRequest.reconciliation_mode is set to `FULL`. */
		notificationPubsubTopic: Option[String] = None
	)
	
	case class GoogleCloudRetailV2ProductInputConfig(
	  /** The Inline source for the input content for products. */
		productInlineSource: Option[Schema.GoogleCloudRetailV2ProductInlineSource] = None,
	  /** Google Cloud Storage location for the input content. */
		gcsSource: Option[Schema.GoogleCloudRetailV2GcsSource] = None,
	  /** BigQuery input source. */
		bigQuerySource: Option[Schema.GoogleCloudRetailV2BigQuerySource] = None
	)
	
	case class GoogleCloudRetailV2ProductInlineSource(
	  /** Required. A list of products to update/create. Each product must have a valid Product.id. Recommended max of 100 items. */
		products: Option[List[Schema.GoogleCloudRetailV2Product]] = None
	)
	
	case class GoogleCloudRetailV2SetInventoryRequest(
	  /** Required. The inventory information to update. The allowable fields to update are: &#42; Product.price_info &#42; Product.availability &#42; Product.available_quantity &#42; Product.fulfillment_info The updated inventory fields must be specified in SetInventoryRequest.set_mask. If SetInventoryRequest.inventory.name is empty or invalid, an INVALID_ARGUMENT error is returned. If the caller does not have permission to update the Product named in Product.name, regardless of whether or not it exists, a PERMISSION_DENIED error is returned. If the Product to update does not have existing inventory information, the provided inventory information will be inserted. If the Product to update has existing inventory information, the provided inventory information will be merged while respecting the last update time for each inventory field, using the provided or default value for SetInventoryRequest.set_time. The caller can replace place IDs for a subset of fulfillment types in the following ways: &#42; Adds "fulfillment_info" in SetInventoryRequest.set_mask &#42; Specifies only the desired fulfillment types and corresponding place IDs to update in SetInventoryRequest.inventory.fulfillment_info The caller can clear all place IDs from a subset of fulfillment types in the following ways: &#42; Adds "fulfillment_info" in SetInventoryRequest.set_mask &#42; Specifies only the desired fulfillment types to clear in SetInventoryRequest.inventory.fulfillment_info &#42; Checks that only the desired fulfillment info types have empty SetInventoryRequest.inventory.fulfillment_info.place_ids The last update time is recorded for the following inventory fields: &#42; Product.price_info &#42; Product.availability &#42; Product.available_quantity &#42; Product.fulfillment_info If a full overwrite of inventory information while ignoring timestamps is needed, ProductService.UpdateProduct should be invoked instead. */
		inventory: Option[Schema.GoogleCloudRetailV2Product] = None,
	  /** Indicates which inventory fields in the provided Product to update. At least one field must be provided. If an unsupported or unknown field is provided, an INVALID_ARGUMENT error is returned and the entire update will be ignored. */
		setMask: Option[String] = None,
	  /** The time when the request is issued, used to prevent out-of-order updates on inventory fields with the last update time recorded. If not provided, the internal system time will be used. */
		setTime: Option[String] = None,
	  /** If set to true, and the Product with name Product.name is not found, the inventory update will still be processed and retained for at most 1 day until the Product is created. If set to false, a NOT_FOUND error is returned if the Product is not found. */
		allowMissing: Option[Boolean] = None
	)
	
	case class GoogleCloudRetailV2AddFulfillmentPlacesRequest(
	  /** Required. The fulfillment type, including commonly used types (such as pickup in store and same day delivery), and custom types. Supported values: &#42; "pickup-in-store" &#42; "ship-to-store" &#42; "same-day-delivery" &#42; "next-day-delivery" &#42; "custom-type-1" &#42; "custom-type-2" &#42; "custom-type-3" &#42; "custom-type-4" &#42; "custom-type-5" If this field is set to an invalid value other than these, an INVALID_ARGUMENT error is returned. This field directly corresponds to Product.fulfillment_info.type. */
		`type`: Option[String] = None,
	  /** Required. The IDs for this type, such as the store IDs for "pickup-in-store" or the region IDs for "same-day-delivery" to be added for this type. Duplicate IDs will be automatically ignored. At least 1 value is required, and a maximum of 2000 values are allowed. Each value must be a string with a length limit of 10 characters, matching the pattern `[a-zA-Z0-9_-]+`, such as "store1" or "REGION-2". Otherwise, an INVALID_ARGUMENT error is returned. If the total number of place IDs exceeds 2000 for this type after adding, then the update will be rejected. */
		placeIds: Option[List[String]] = None,
	  /** The time when the fulfillment updates are issued, used to prevent out-of-order updates on fulfillment information. If not provided, the internal system time will be used. */
		addTime: Option[String] = None,
	  /** If set to true, and the Product is not found, the fulfillment information will still be processed and retained for at most 1 day and processed once the Product is created. If set to false, a NOT_FOUND error is returned if the Product is not found. */
		allowMissing: Option[Boolean] = None
	)
	
	case class GoogleCloudRetailV2RemoveFulfillmentPlacesRequest(
	  /** Required. The fulfillment type, including commonly used types (such as pickup in store and same day delivery), and custom types. Supported values: &#42; "pickup-in-store" &#42; "ship-to-store" &#42; "same-day-delivery" &#42; "next-day-delivery" &#42; "custom-type-1" &#42; "custom-type-2" &#42; "custom-type-3" &#42; "custom-type-4" &#42; "custom-type-5" If this field is set to an invalid value other than these, an INVALID_ARGUMENT error is returned. This field directly corresponds to Product.fulfillment_info.type. */
		`type`: Option[String] = None,
	  /** Required. The IDs for this type, such as the store IDs for "pickup-in-store" or the region IDs for "same-day-delivery", to be removed for this type. At least 1 value is required, and a maximum of 2000 values are allowed. Each value must be a string with a length limit of 10 characters, matching the pattern `[a-zA-Z0-9_-]+`, such as "store1" or "REGION-2". Otherwise, an INVALID_ARGUMENT error is returned. */
		placeIds: Option[List[String]] = None,
	  /** The time when the fulfillment updates are issued, used to prevent out-of-order updates on fulfillment information. If not provided, the internal system time will be used. */
		removeTime: Option[String] = None,
	  /** If set to true, and the Product is not found, the fulfillment information will still be processed and retained for at most 1 day and processed once the Product is created. If set to false, a NOT_FOUND error is returned if the Product is not found. */
		allowMissing: Option[Boolean] = None
	)
	
	case class GoogleCloudRetailV2AddLocalInventoriesRequest(
	  /** Required. A list of inventory information at difference places. Each place is identified by its place ID. At most 3000 inventories are allowed per request. */
		localInventories: Option[List[Schema.GoogleCloudRetailV2LocalInventory]] = None,
	  /** Indicates which inventory fields in the provided list of LocalInventory to update. The field is updated to the provided value. If a field is set while the place does not have a previous local inventory, the local inventory at that store is created. If a field is set while the value of that field is not provided, the original field value, if it exists, is deleted. If the mask is not set or set with empty paths, all inventory fields will be updated. If an unsupported or unknown field is provided, an INVALID_ARGUMENT error is returned and the entire update will be ignored. */
		addMask: Option[String] = None,
	  /** The time when the inventory updates are issued. Used to prevent out-of-order updates on local inventory fields. If not provided, the internal system time will be used. */
		addTime: Option[String] = None,
	  /** If set to true, and the Product is not found, the local inventory will still be processed and retained for at most 1 day and processed once the Product is created. If set to false, a NOT_FOUND error is returned if the Product is not found. */
		allowMissing: Option[Boolean] = None
	)
	
	case class GoogleCloudRetailV2RemoveLocalInventoriesRequest(
	  /** Required. A list of place IDs to have their inventory deleted. At most 3000 place IDs are allowed per request. */
		placeIds: Option[List[String]] = None,
	  /** The time when the inventory deletions are issued. Used to prevent out-of-order updates and deletions on local inventory fields. If not provided, the internal system time will be used. */
		removeTime: Option[String] = None,
	  /** If set to true, and the Product is not found, the local inventory removal request will still be processed and retained for at most 1 day and processed once the Product is created. If set to false, a NOT_FOUND error is returned if the Product is not found. */
		allowMissing: Option[Boolean] = None
	)
	
	object GoogleCloudRetailV2ServingConfig {
		enum DiversityTypeEnum extends Enum[DiversityTypeEnum] { case DIVERSITY_TYPE_UNSPECIFIED, RULE_BASED_DIVERSITY, DATA_DRIVEN_DIVERSITY }
		enum SolutionTypesEnum extends Enum[SolutionTypesEnum] { case SOLUTION_TYPE_UNSPECIFIED, SOLUTION_TYPE_RECOMMENDATION, SOLUTION_TYPE_SEARCH }
	}
	case class GoogleCloudRetailV2ServingConfig(
	  /** Immutable. Fully qualified name `projects/&#42;/locations/global/catalogs/&#42;/servingConfig/&#42;` */
		name: Option[String] = None,
	  /** Required. The human readable serving config display name. Used in Retail UI. This field must be a UTF-8 encoded string with a length limit of 128 characters. Otherwise, an INVALID_ARGUMENT error is returned. */
		displayName: Option[String] = None,
	  /** The id of the model in the same Catalog to use at serving time. Currently only RecommendationModels are supported: https://cloud.google.com/retail/recommendations-ai/docs/create-models Can be changed but only to a compatible model (e.g. others-you-may-like CTR to others-you-may-like CVR). Required when solution_types is SOLUTION_TYPE_RECOMMENDATION. */
		modelId: Option[String] = None,
	  /** How much price ranking we want in serving results. Price reranking causes product items with a similar recommendation probability to be ordered by price, with the highest-priced items first. This setting could result in a decrease in click-through and conversion rates. Allowed values are: &#42; `no-price-reranking` &#42; `low-price-reranking` &#42; `medium-price-reranking` &#42; `high-price-reranking` If not specified, we choose default based on model type. Default value: `no-price-reranking`. Can only be set if solution_types is SOLUTION_TYPE_RECOMMENDATION. */
		priceRerankingLevel: Option[String] = None,
	  /** Facet specifications for faceted search. If empty, no facets are returned. The ids refer to the ids of Control resources with only the Facet control set. These controls are assumed to be in the same Catalog as the ServingConfig. A maximum of 100 values are allowed. Otherwise, an INVALID_ARGUMENT error is returned. Can only be set if solution_types is SOLUTION_TYPE_SEARCH. */
		facetControlIds: Option[List[String]] = None,
	  /** The specification for dynamically generated facets. Notice that only textual facets can be dynamically generated. Can only be set if solution_types is SOLUTION_TYPE_SEARCH. */
		dynamicFacetSpec: Option[Schema.GoogleCloudRetailV2SearchRequestDynamicFacetSpec] = None,
	  /** Condition boost specifications. If a product matches multiple conditions in the specifications, boost scores from these specifications are all applied and combined in a non-linear way. Maximum number of specifications is 100. Notice that if both ServingConfig.boost_control_ids and SearchRequest.boost_spec are set, the boost conditions from both places are evaluated. If a search request matches multiple boost conditions, the final boost score is equal to the sum of the boost scores from all matched boost conditions. Can only be set if solution_types is SOLUTION_TYPE_SEARCH. */
		boostControlIds: Option[List[String]] = None,
	  /** Condition filter specifications. If a product matches multiple conditions in the specifications, filters from these specifications are all applied and combined via the AND operator. Maximum number of specifications is 100. Can only be set if solution_types is SOLUTION_TYPE_SEARCH. */
		filterControlIds: Option[List[String]] = None,
	  /** Condition redirect specifications. Only the first triggered redirect action is applied, even if multiple apply. Maximum number of specifications is 1000. Can only be set if solution_types is SOLUTION_TYPE_SEARCH. */
		redirectControlIds: Option[List[String]] = None,
	  /** Condition synonyms specifications. If multiple syonyms conditions match, all matching synonyms control in the list will execute. Order of controls in the list will not matter. Maximum number of specifications is 100. Can only be set if solution_types is SOLUTION_TYPE_SEARCH. */
		twowaySynonymsControlIds: Option[List[String]] = None,
	  /** Condition oneway synonyms specifications. If multiple oneway synonyms conditions match, all matching oneway synonyms controls in the list will execute. Order of controls in the list will not matter. Maximum number of specifications is 100. Can only be set if solution_types is SOLUTION_TYPE_SEARCH. */
		onewaySynonymsControlIds: Option[List[String]] = None,
	  /** Condition do not associate specifications. If multiple do not associate conditions match, all matching do not associate controls in the list will execute. - Order does not matter. - Maximum number of specifications is 100. Can only be set if solution_types is SOLUTION_TYPE_SEARCH. */
		doNotAssociateControlIds: Option[List[String]] = None,
	  /** Condition replacement specifications. - Applied according to the order in the list. - A previously replaced term can not be re-replaced. - Maximum number of specifications is 100. Can only be set if solution_types is SOLUTION_TYPE_SEARCH. */
		replacementControlIds: Option[List[String]] = None,
	  /** Condition ignore specifications. If multiple ignore conditions match, all matching ignore controls in the list will execute. - Order does not matter. - Maximum number of specifications is 100. Can only be set if solution_types is SOLUTION_TYPE_SEARCH. */
		ignoreControlIds: Option[List[String]] = None,
	  /** How much diversity to use in recommendation model results e.g. `medium-diversity` or `high-diversity`. Currently supported values: &#42; `no-diversity` &#42; `low-diversity` &#42; `medium-diversity` &#42; `high-diversity` &#42; `auto-diversity` If not specified, we choose default based on recommendation model type. Default value: `no-diversity`. Can only be set if solution_types is SOLUTION_TYPE_RECOMMENDATION. */
		diversityLevel: Option[String] = None,
	  /** What kind of diversity to use - data driven or rule based. If unset, the server behavior defaults to RULE_BASED_DIVERSITY. */
		diversityType: Option[Schema.GoogleCloudRetailV2ServingConfig.DiversityTypeEnum] = None,
	  /** Whether to add additional category filters on the `similar-items` model. If not specified, we enable it by default. Allowed values are: &#42; `no-category-match`: No additional filtering of original results from the model and the customer's filters. &#42; `relaxed-category-match`: Only keep results with categories that match at least one item categories in the PredictRequests's context item. &#42; If customer also sends filters in the PredictRequest, then the results will satisfy both conditions (user given and category match). Can only be set if solution_types is SOLUTION_TYPE_RECOMMENDATION. */
		enableCategoryFilterLevel: Option[String] = None,
	  /** When the flag is enabled, the products in the denylist will not be filtered out in the recommendation filtering results. */
		ignoreRecsDenylist: Option[Boolean] = None,
	  /** The specification for personalization spec. Can only be set if solution_types is SOLUTION_TYPE_SEARCH. Notice that if both ServingConfig.personalization_spec and SearchRequest.personalization_spec are set. SearchRequest.personalization_spec will override ServingConfig.personalization_spec. */
		personalizationSpec: Option[Schema.GoogleCloudRetailV2SearchRequestPersonalizationSpec] = None,
	  /** Required. Immutable. Specifies the solution types that a serving config can be associated with. Currently we support setting only one type of solution. */
		solutionTypes: Option[List[Schema.GoogleCloudRetailV2ServingConfig.SolutionTypesEnum]] = None
	)
	
	case class GoogleCloudRetailV2ListServingConfigsResponse(
	  /** All the ServingConfigs for a given catalog. */
		servingConfigs: Option[List[Schema.GoogleCloudRetailV2ServingConfig]] = None,
	  /** Pagination token, if not returned indicates the last page. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudRetailV2AddControlRequest(
	  /** Required. The id of the control to apply. Assumed to be in the same catalog as the serving config - if id is not found a NOT_FOUND error is returned. */
		controlId: Option[String] = None
	)
	
	case class GoogleCloudRetailV2RemoveControlRequest(
	  /** Required. The id of the control to apply. Assumed to be in the same catalog as the serving config. */
		controlId: Option[String] = None
	)
	
	case class GoogleCloudRetailLoggingErrorLog(
	  /** The service context in which this error has occurred. */
		serviceContext: Option[Schema.GoogleCloudRetailLoggingServiceContext] = None,
	  /** A description of the context in which the error occurred. */
		context: Option[Schema.GoogleCloudRetailLoggingErrorContext] = None,
	  /** A message describing the error. */
		message: Option[String] = None,
	  /** The RPC status associated with the error log. */
		status: Option[Schema.GoogleRpcStatus] = None,
	  /** The API request payload, represented as a protocol buffer. Most API request types are supported. For example: "type.googleapis.com/google.cloud.retail.v2.ProductService.CreateProductRequest" "type.googleapis.com/google.cloud.retail.v2.UserEventService.WriteUserEventRequest" */
		requestPayload: Option[Map[String, JsValue]] = None,
	  /** The API response payload, represented as a protocol buffer. This is used to log some "soft errors", where the response is valid but we consider there are some quality issues like unjoined events. The following API responses are supported and no PII is included: "google.cloud.retail.v2.PredictionService.Predict" "google.cloud.retail.v2.UserEventService.WriteUserEvent" "google.cloud.retail.v2.UserEventService.CollectUserEvent" */
		responsePayload: Option[Map[String, JsValue]] = None,
	  /** The error payload that is populated on LRO import APIs. */
		importPayload: Option[Schema.GoogleCloudRetailLoggingImportErrorContext] = None
	)
	
	case class GoogleCloudRetailLoggingServiceContext(
	  /** An identifier of the service. For example, "retail.googleapis.com". */
		service: Option[String] = None
	)
	
	case class GoogleCloudRetailLoggingErrorContext(
	  /** The HTTP request which was processed when the error was triggered. */
		httpRequest: Option[Schema.GoogleCloudRetailLoggingHttpRequestContext] = None,
	  /** The location in the source code where the decision was made to report the error, usually the place where it was logged. */
		reportLocation: Option[Schema.GoogleCloudRetailLoggingSourceLocation] = None
	)
	
	case class GoogleCloudRetailLoggingHttpRequestContext(
	  /** The HTTP response status code for the request. */
		responseStatusCode: Option[Int] = None
	)
	
	case class GoogleCloudRetailLoggingSourceLocation(
	  /** Human-readable name of a function or method. For example, "google.cloud.retail.v2.UserEventService.ImportUserEvents". */
		functionName: Option[String] = None
	)
	
	case class GoogleCloudRetailLoggingImportErrorContext(
	  /** The operation resource name of the LRO. */
		operationName: Option[String] = None,
	  /** Cloud Storage file path of the import source. Can be set for batch operation error. */
		gcsPath: Option[String] = None,
	  /** Line number of the content in file. Should be empty for permission or batch operation error. */
		lineNumber: Option[String] = None,
	  /** The detailed content which caused the error on importing a catalog item. */
		catalogItem: Option[String] = None,
	  /** The detailed content which caused the error on importing a product. */
		product: Option[String] = None,
	  /** The detailed content which caused the error on importing a user event. */
		userEvent: Option[String] = None
	)
	
	case class GoogleCloudRetailV2AddFulfillmentPlacesMetadata(
	
	)
	
	case class GoogleCloudRetailV2AddFulfillmentPlacesResponse(
	
	)
	
	case class GoogleCloudRetailV2AddLocalInventoriesMetadata(
	
	)
	
	case class GoogleCloudRetailV2AddLocalInventoriesResponse(
	
	)
	
	case class GoogleCloudRetailV2CreateModelMetadata(
	  /** The resource name of the model that this create applies to. Format: `projects/{project_number}/locations/{location_id}/catalogs/{catalog_id}/models/{model_id}` */
		model: Option[String] = None
	)
	
	case class GoogleCloudRetailV2ExportAnalyticsMetricsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** This field is never set. */
		errorsConfig: Option[Schema.GoogleCloudRetailV2ExportErrorsConfig] = None,
	  /** Output result indicating where the data were exported to. */
		outputResult: Option[Schema.GoogleCloudRetailV2OutputResult] = None
	)
	
	case class GoogleCloudRetailV2ExportErrorsConfig(
	  /** Google Cloud Storage path for import errors. This must be an empty, existing Cloud Storage bucket. Export errors will be written to a file in this bucket, one per line, as a JSON-encoded `google.rpc.Status` message. */
		gcsPrefix: Option[String] = None
	)
	
	case class GoogleCloudRetailV2OutputResult(
	  /** The BigQuery location where the result is stored. */
		bigqueryResult: Option[List[Schema.GoogleCloudRetailV2BigQueryOutputResult]] = None,
	  /** The Google Cloud Storage location where the result is stored. */
		gcsResult: Option[List[Schema.GoogleCloudRetailV2GcsOutputResult]] = None
	)
	
	case class GoogleCloudRetailV2BigQueryOutputResult(
	  /** The ID of a BigQuery Dataset. */
		datasetId: Option[String] = None,
	  /** The ID of a BigQuery Table. */
		tableId: Option[String] = None
	)
	
	case class GoogleCloudRetailV2GcsOutputResult(
	  /** The uri of Gcs output */
		outputUri: Option[String] = None
	)
	
	case class GoogleCloudRetailV2ExportMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudRetailV2ImportCompletionDataResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None
	)
	
	case class GoogleCloudRetailV2ImportMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of entries that were processed successfully. */
		successCount: Option[String] = None,
	  /** Count of entries that encountered errors while processing. */
		failureCount: Option[String] = None,
	  /** Deprecated. This field is never set. */
		requestId: Option[String] = None,
	  /** Pub/Sub topic for receiving notification. If this field is set, when the import is finished, a notification is sent to specified Pub/Sub topic. The message data is JSON string of a Operation. Format of the Pub/Sub topic is `projects/{project}/topics/{topic}`. */
		notificationPubsubTopic: Option[String] = None
	)
	
	case class GoogleCloudRetailV2ImportProductsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Echoes the destination for the complete errors in the request if set. */
		errorsConfig: Option[Schema.GoogleCloudRetailV2ImportErrorsConfig] = None
	)
	
	case class GoogleCloudRetailV2ImportUserEventsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Echoes the destination for the complete errors if this field was set in the request. */
		errorsConfig: Option[Schema.GoogleCloudRetailV2ImportErrorsConfig] = None,
	  /** Aggregated statistics of user event import status. */
		importSummary: Option[Schema.GoogleCloudRetailV2UserEventImportSummary] = None
	)
	
	case class GoogleCloudRetailV2UserEventImportSummary(
	  /** Count of user events imported with complete existing catalog information. */
		joinedEventsCount: Option[String] = None,
	  /** Count of user events imported, but with catalog information not found in the imported catalog. */
		unjoinedEventsCount: Option[String] = None
	)
	
	case class GoogleCloudRetailV2PurgeMetadata(
	
	)
	
	case class GoogleCloudRetailV2PurgeProductsMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of entries that were deleted successfully. */
		successCount: Option[String] = None,
	  /** Count of entries that encountered errors while processing. */
		failureCount: Option[String] = None
	)
	
	case class GoogleCloudRetailV2PurgeProductsResponse(
	  /** The total count of products purged as a result of the operation. */
		purgeCount: Option[String] = None,
	  /** A sample of the product names that will be deleted. Only populated if `force` is set to false. A max of 100 names will be returned and the names are chosen at random. */
		purgeSample: Option[List[String]] = None
	)
	
	case class GoogleCloudRetailV2PurgeUserEventsResponse(
	  /** The total count of events purged as a result of the operation. */
		purgedEventsCount: Option[String] = None
	)
	
	case class GoogleCloudRetailV2RejoinUserEventsMetadata(
	
	)
	
	case class GoogleCloudRetailV2RejoinUserEventsResponse(
	  /** Number of user events that were joined with latest product catalog. */
		rejoinedUserEventsCount: Option[String] = None
	)
	
	case class GoogleCloudRetailV2RemoveFulfillmentPlacesMetadata(
	
	)
	
	case class GoogleCloudRetailV2RemoveFulfillmentPlacesResponse(
	
	)
	
	case class GoogleCloudRetailV2RemoveLocalInventoriesMetadata(
	
	)
	
	case class GoogleCloudRetailV2RemoveLocalInventoriesResponse(
	
	)
	
	case class GoogleCloudRetailV2SetInventoryMetadata(
	
	)
	
	case class GoogleCloudRetailV2SetInventoryResponse(
	
	)
	
	case class GoogleCloudRetailV2TuneModelMetadata(
	  /** The resource name of the model that this tune applies to. Format: `projects/{project_number}/locations/{location_id}/catalogs/{catalog_id}/models/{model_id}` */
		model: Option[String] = None
	)
	
	case class GoogleCloudRetailV2TuneModelResponse(
	
	)
	
	case class GoogleCloudRetailV2alphaAddFulfillmentPlacesMetadata(
	
	)
	
	case class GoogleCloudRetailV2alphaAddFulfillmentPlacesResponse(
	
	)
	
	case class GoogleCloudRetailV2alphaAddLocalInventoriesMetadata(
	
	)
	
	case class GoogleCloudRetailV2alphaAddLocalInventoriesResponse(
	
	)
	
	case class GoogleCloudRetailV2alphaCreateMerchantCenterAccountLinkMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudRetailV2alphaCreateModelMetadata(
	  /** The resource name of the model that this create applies to. Format: `projects/{project_number}/locations/{location_id}/catalogs/{catalog_id}/models/{model_id}` */
		model: Option[String] = None
	)
	
	case class GoogleCloudRetailV2alphaEnrollSolutionMetadata(
	
	)
	
	object GoogleCloudRetailV2alphaEnrollSolutionResponse {
		enum EnrolledSolutionEnum extends Enum[EnrolledSolutionEnum] { case SOLUTION_TYPE_UNSPECIFIED, SOLUTION_TYPE_RECOMMENDATION, SOLUTION_TYPE_SEARCH }
	}
	case class GoogleCloudRetailV2alphaEnrollSolutionResponse(
	  /** Retail API solution that the project has enrolled. */
		enrolledSolution: Option[Schema.GoogleCloudRetailV2alphaEnrollSolutionResponse.EnrolledSolutionEnum] = None
	)
	
	case class GoogleCloudRetailV2alphaExportAnalyticsMetricsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** This field is never set. */
		errorsConfig: Option[Schema.GoogleCloudRetailV2alphaExportErrorsConfig] = None,
	  /** Output result indicating where the data were exported to. */
		outputResult: Option[Schema.GoogleCloudRetailV2alphaOutputResult] = None
	)
	
	case class GoogleCloudRetailV2alphaExportErrorsConfig(
	  /** Google Cloud Storage path for import errors. This must be an empty, existing Cloud Storage bucket. Export errors will be written to a file in this bucket, one per line, as a JSON-encoded `google.rpc.Status` message. */
		gcsPrefix: Option[String] = None
	)
	
	case class GoogleCloudRetailV2alphaOutputResult(
	  /** The BigQuery location where the result is stored. */
		bigqueryResult: Option[List[Schema.GoogleCloudRetailV2alphaBigQueryOutputResult]] = None,
	  /** The Google Cloud Storage location where the result is stored. */
		gcsResult: Option[List[Schema.GoogleCloudRetailV2alphaGcsOutputResult]] = None
	)
	
	case class GoogleCloudRetailV2alphaBigQueryOutputResult(
	  /** The ID of a BigQuery Dataset. */
		datasetId: Option[String] = None,
	  /** The ID of a BigQuery Table. */
		tableId: Option[String] = None
	)
	
	case class GoogleCloudRetailV2alphaGcsOutputResult(
	  /** The uri of Gcs output */
		outputUri: Option[String] = None
	)
	
	case class GoogleCloudRetailV2alphaExportMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudRetailV2alphaExportProductsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** This field is never set. */
		errorsConfig: Option[Schema.GoogleCloudRetailV2alphaExportErrorsConfig] = None,
	  /** Output result indicating where the data were exported to. */
		outputResult: Option[Schema.GoogleCloudRetailV2alphaOutputResult] = None
	)
	
	case class GoogleCloudRetailV2alphaExportUserEventsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** This field is never set. */
		errorsConfig: Option[Schema.GoogleCloudRetailV2alphaExportErrorsConfig] = None,
	  /** Output result indicating where the data were exported to. */
		outputResult: Option[Schema.GoogleCloudRetailV2alphaOutputResult] = None
	)
	
	case class GoogleCloudRetailV2alphaImportCompletionDataResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None
	)
	
	case class GoogleCloudRetailV2alphaImportErrorsConfig(
	  /** Google Cloud Storage prefix for import errors. This must be an empty, existing Cloud Storage directory. Import errors are written to sharded files in this directory, one per line, as a JSON-encoded `google.rpc.Status` message. */
		gcsPrefix: Option[String] = None
	)
	
	case class GoogleCloudRetailV2alphaImportMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of entries that were processed successfully. */
		successCount: Option[String] = None,
	  /** Count of entries that encountered errors while processing. */
		failureCount: Option[String] = None,
	  /** Deprecated. This field is never set. */
		requestId: Option[String] = None,
	  /** Pub/Sub topic for receiving notification. If this field is set, when the import is finished, a notification is sent to specified Pub/Sub topic. The message data is JSON string of a Operation. Format of the Pub/Sub topic is `projects/{project}/topics/{topic}`. */
		notificationPubsubTopic: Option[String] = None,
	  /** Metadata related to transform user events. */
		transformedUserEventsMetadata: Option[Schema.GoogleCloudRetailV2alphaTransformedUserEventsMetadata] = None
	)
	
	case class GoogleCloudRetailV2alphaTransformedUserEventsMetadata(
	  /** Count of entries in the source user events BigQuery table. */
		sourceEventsCount: Option[String] = None,
	  /** Count of entries in the transformed user events BigQuery table, which could be different from the actually imported number of user events. */
		transformedEventsCount: Option[String] = None
	)
	
	case class GoogleCloudRetailV2alphaImportProductsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Echoes the destination for the complete errors in the request if set. */
		errorsConfig: Option[Schema.GoogleCloudRetailV2alphaImportErrorsConfig] = None
	)
	
	case class GoogleCloudRetailV2alphaImportUserEventsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Echoes the destination for the complete errors if this field was set in the request. */
		errorsConfig: Option[Schema.GoogleCloudRetailV2alphaImportErrorsConfig] = None,
	  /** Aggregated statistics of user event import status. */
		importSummary: Option[Schema.GoogleCloudRetailV2alphaUserEventImportSummary] = None
	)
	
	case class GoogleCloudRetailV2alphaUserEventImportSummary(
	  /** Count of user events imported with complete existing catalog information. */
		joinedEventsCount: Option[String] = None,
	  /** Count of user events imported, but with catalog information not found in the imported catalog. */
		unjoinedEventsCount: Option[String] = None
	)
	
	object GoogleCloudRetailV2alphaMerchantCenterAccountLink {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, ACTIVE, FAILED }
	}
	case class GoogleCloudRetailV2alphaMerchantCenterAccountLink(
	  /** Output only. Immutable. Full resource name of the Merchant Center Account Link, such as `projects/&#42;/locations/global/catalogs/default_catalog/merchantCenterAccountLinks/merchant_center_account_link`. */
		name: Option[String] = None,
	  /** Output only. Immutable. MerchantCenterAccountLink identifier, which is the final component of name. This field is auto generated and follows the convention: `BranchId_MerchantCenterAccountId`. `projects/&#42;/locations/global/catalogs/default_catalog/merchantCenterAccountLinks/id_1`. */
		id: Option[String] = None,
	  /** Required. The linked [Merchant center account id](https://developers.google.com/shopping-content/guides/accountstatuses). The account must be a standalone account or a sub-account of a MCA. */
		merchantCenterAccountId: Option[String] = None,
	  /** Required. The branch ID (e.g. 0/1/2) within the catalog that products from merchant_center_account_id are streamed to. When updating this field, an empty value will use the currently configured default branch. However, changing the default branch later on won't change the linked branch here. A single branch ID can only have one linked Merchant Center account ID. */
		branchId: Option[String] = None,
	  /** The FeedLabel used to perform filtering. Note: this replaces [region_id](https://developers.google.com/shopping-content/reference/rest/v2.1/products#Product.FIELDS.feed_label). Example value: `US`. Example value: `FeedLabel1`. */
		feedLabel: Option[String] = None,
	  /** Language of the title/description and other string attributes. Use language tags defined by [BCP 47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt). ISO 639-1. This specifies the language of offers in Merchant Center that will be accepted. If empty, no language filtering will be performed. Example value: `en`. */
		languageCode: Option[String] = None,
	  /** Criteria for the Merchant Center feeds to be ingested via the link. All offers will be ingested if the list is empty. Otherwise the offers will be ingested from selected feeds. */
		feedFilters: Option[List[Schema.GoogleCloudRetailV2alphaMerchantCenterAccountLinkMerchantCenterFeedFilter]] = None,
	  /** Output only. Represents the state of the link. */
		state: Option[Schema.GoogleCloudRetailV2alphaMerchantCenterAccountLink.StateEnum] = None,
	  /** Output only. Google Cloud project ID. */
		projectId: Option[String] = None,
	  /** Optional. An optional arbitrary string that could be used as a tag for tracking link source. */
		source: Option[String] = None
	)
	
	case class GoogleCloudRetailV2alphaMerchantCenterAccountLinkMerchantCenterFeedFilter(
	  /** Merchant Center primary feed ID. */
		primaryFeedId: Option[String] = None,
	  /** Merchant Center primary feed name. The name is used for the display purposes only. */
		primaryFeedName: Option[String] = None
	)
	
	object GoogleCloudRetailV2alphaModel {
		enum TrainingStateEnum extends Enum[TrainingStateEnum] { case TRAINING_STATE_UNSPECIFIED, PAUSED, TRAINING }
		enum ServingStateEnum extends Enum[ServingStateEnum] { case SERVING_STATE_UNSPECIFIED, INACTIVE, ACTIVE, TUNED }
		enum PeriodicTuningStateEnum extends Enum[PeriodicTuningStateEnum] { case PERIODIC_TUNING_STATE_UNSPECIFIED, PERIODIC_TUNING_DISABLED, ALL_TUNING_DISABLED, PERIODIC_TUNING_ENABLED }
		enum DataStateEnum extends Enum[DataStateEnum] { case DATA_STATE_UNSPECIFIED, DATA_OK, DATA_ERROR }
		enum FilteringOptionEnum extends Enum[FilteringOptionEnum] { case RECOMMENDATIONS_FILTERING_OPTION_UNSPECIFIED, RECOMMENDATIONS_FILTERING_DISABLED, RECOMMENDATIONS_FILTERING_ENABLED }
	}
	case class GoogleCloudRetailV2alphaModel(
	  /** Optional. The page optimization config. */
		pageOptimizationConfig: Option[Schema.GoogleCloudRetailV2alphaModelPageOptimizationConfig] = None,
	  /** Required. The fully qualified resource name of the model. Format: `projects/{project_number}/locations/{location_id}/catalogs/{catalog_id}/models/{model_id}` catalog_id has char limit of 50. recommendation_model_id has char limit of 40. */
		name: Option[String] = None,
	  /** Required. The display name of the model. Should be human readable, used to display Recommendation Models in the Retail Cloud Console Dashboard. UTF-8 encoded string with limit of 1024 characters. */
		displayName: Option[String] = None,
	  /** Optional. The training state that the model is in (e.g. `TRAINING` or `PAUSED`). Since part of the cost of running the service is frequency of training - this can be used to determine when to train model in order to control cost. If not specified: the default value for `CreateModel` method is `TRAINING`. The default value for `UpdateModel` method is to keep the state the same as before. */
		trainingState: Option[Schema.GoogleCloudRetailV2alphaModel.TrainingStateEnum] = None,
	  /** Output only. The serving state of the model: `ACTIVE`, `NOT_ACTIVE`. */
		servingState: Option[Schema.GoogleCloudRetailV2alphaModel.ServingStateEnum] = None,
	  /** Output only. Timestamp the Recommendation Model was created at. */
		createTime: Option[String] = None,
	  /** Output only. Timestamp the Recommendation Model was last updated. E.g. if a Recommendation Model was paused - this would be the time the pause was initiated. */
		updateTime: Option[String] = None,
	  /** Required. The type of model e.g. `home-page`. Currently supported values: `recommended-for-you`, `others-you-may-like`, `frequently-bought-together`, `page-optimization`, `similar-items`, `buy-it-again`, `on-sale-items`, and `recently-viewed`(readonly value). This field together with optimization_objective describe model metadata to use to control model training and serving. See https://cloud.google.com/retail/docs/models for more details on what the model metadata control and which combination of parameters are valid. For invalid combinations of parameters (e.g. type = `frequently-bought-together` and optimization_objective = `ctr`), you receive an error 400 if you try to create/update a recommendation with this set of knobs. */
		`type`: Option[String] = None,
	  /** Optional. The optimization objective e.g. `cvr`. Currently supported values: `ctr`, `cvr`, `revenue-per-order`. If not specified, we choose default based on model type. Default depends on type of recommendation: `recommended-for-you` => `ctr` `others-you-may-like` => `ctr` `frequently-bought-together` => `revenue_per_order` This field together with optimization_objective describe model metadata to use to control model training and serving. See https://cloud.google.com/retail/docs/models for more details on what the model metadata control and which combination of parameters are valid. For invalid combinations of parameters (e.g. type = `frequently-bought-together` and optimization_objective = `ctr`), you receive an error 400 if you try to create/update a recommendation with this set of knobs. */
		optimizationObjective: Option[String] = None,
	  /** Optional. The state of periodic tuning. The period we use is 3 months - to do a one-off tune earlier use the `TuneModel` method. Default value is `PERIODIC_TUNING_ENABLED`. */
		periodicTuningState: Option[Schema.GoogleCloudRetailV2alphaModel.PeriodicTuningStateEnum] = None,
	  /** Output only. The timestamp when the latest successful tune finished. */
		lastTuneTime: Option[String] = None,
	  /** Output only. The tune operation associated with the model. Can be used to determine if there is an ongoing tune for this recommendation. Empty field implies no tune is goig on. */
		tuningOperation: Option[String] = None,
	  /** Output only. The state of data requirements for this model: `DATA_OK` and `DATA_ERROR`. Recommendation model cannot be trained if the data is in `DATA_ERROR` state. Recommendation model can have `DATA_ERROR` state even if serving state is `ACTIVE`: models were trained successfully before, but cannot be refreshed because model no longer has sufficient data for training. */
		dataState: Option[Schema.GoogleCloudRetailV2alphaModel.DataStateEnum] = None,
	  /** Optional. If `RECOMMENDATIONS_FILTERING_ENABLED`, recommendation filtering by attributes is enabled for the model. */
		filteringOption: Option[Schema.GoogleCloudRetailV2alphaModel.FilteringOptionEnum] = None,
	  /** Output only. The list of valid serving configs associated with the PageOptimizationConfig. */
		servingConfigLists: Option[List[Schema.GoogleCloudRetailV2alphaModelServingConfigList]] = None,
	  /** Optional. Additional model features config. */
		modelFeaturesConfig: Option[Schema.GoogleCloudRetailV2alphaModelModelFeaturesConfig] = None
	)
	
	object GoogleCloudRetailV2alphaModelPageOptimizationConfig {
		enum RestrictionEnum extends Enum[RestrictionEnum] { case RESTRICTION_UNSPECIFIED, NO_RESTRICTION, UNIQUE_SERVING_CONFIG_RESTRICTION, UNIQUE_MODEL_RESTRICTION, UNIQUE_MODEL_TYPE_RESTRICTION }
	}
	case class GoogleCloudRetailV2alphaModelPageOptimizationConfig(
	  /** Required. The type of UserEvent this page optimization is shown for. Each page has an associated event type - this will be the corresponding event type for the page that the page optimization model is used on. Supported types: &#42; `add-to-cart`: Products being added to cart. &#42; `detail-page-view`: Products detail page viewed. &#42; `home-page-view`: Homepage viewed &#42; `category-page-view`: Homepage viewed &#42; `shopping-cart-page-view`: User viewing a shopping cart. `home-page-view` only allows models with type `recommended-for-you`. All other page_optimization_event_type allow all Model.types. */
		pageOptimizationEventType: Option[String] = None,
	  /** Required. A list of panel configurations. Limit = 5. */
		panels: Option[List[Schema.GoogleCloudRetailV2alphaModelPageOptimizationConfigPanel]] = None,
	  /** Optional. How to restrict results across panels e.g. can the same ServingConfig be shown on multiple panels at once. If unspecified, default to `UNIQUE_MODEL_RESTRICTION`. */
		restriction: Option[Schema.GoogleCloudRetailV2alphaModelPageOptimizationConfig.RestrictionEnum] = None
	)
	
	case class GoogleCloudRetailV2alphaModelPageOptimizationConfigPanel(
	  /** Optional. The name to display for the panel. */
		displayName: Option[String] = None,
	  /** Required. The candidates to consider on the panel. */
		candidates: Option[List[Schema.GoogleCloudRetailV2alphaModelPageOptimizationConfigCandidate]] = None,
	  /** Required. The default candidate. If the model fails at serving time, we fall back to the default. */
		defaultCandidate: Option[Schema.GoogleCloudRetailV2alphaModelPageOptimizationConfigCandidate] = None
	)
	
	case class GoogleCloudRetailV2alphaModelPageOptimizationConfigCandidate(
	  /** This has to be a valid ServingConfig identifier. For example, for a ServingConfig with full name: `projects/&#42;/locations/global/catalogs/default_catalog/servingConfigs/my_candidate_config`, this would be `my_candidate_config`. */
		servingConfigId: Option[String] = None
	)
	
	case class GoogleCloudRetailV2alphaModelServingConfigList(
	  /** Optional. A set of valid serving configs that may be used for `PAGE_OPTIMIZATION`. */
		servingConfigIds: Option[List[String]] = None
	)
	
	case class GoogleCloudRetailV2alphaModelModelFeaturesConfig(
	  /** Additional configs for frequently-bought-together models. */
		frequentlyBoughtTogetherConfig: Option[Schema.GoogleCloudRetailV2alphaModelFrequentlyBoughtTogetherFeaturesConfig] = None
	)
	
	object GoogleCloudRetailV2alphaModelFrequentlyBoughtTogetherFeaturesConfig {
		enum ContextProductsTypeEnum extends Enum[ContextProductsTypeEnum] { case CONTEXT_PRODUCTS_TYPE_UNSPECIFIED, SINGLE_CONTEXT_PRODUCT, MULTIPLE_CONTEXT_PRODUCTS }
	}
	case class GoogleCloudRetailV2alphaModelFrequentlyBoughtTogetherFeaturesConfig(
	  /** Optional. Specifies the context of the model when it is used in predict requests. Can only be set for the `frequently-bought-together` type. If it isn't specified, it defaults to MULTIPLE_CONTEXT_PRODUCTS. */
		contextProductsType: Option[Schema.GoogleCloudRetailV2alphaModelFrequentlyBoughtTogetherFeaturesConfig.ContextProductsTypeEnum] = None
	)
	
	case class GoogleCloudRetailV2alphaPurgeMetadata(
	
	)
	
	case class GoogleCloudRetailV2alphaPurgeProductsMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of entries that were deleted successfully. */
		successCount: Option[String] = None,
	  /** Count of entries that encountered errors while processing. */
		failureCount: Option[String] = None
	)
	
	case class GoogleCloudRetailV2alphaPurgeProductsResponse(
	  /** The total count of products purged as a result of the operation. */
		purgeCount: Option[String] = None,
	  /** A sample of the product names that will be deleted. Only populated if `force` is set to false. A max of 100 names will be returned and the names are chosen at random. */
		purgeSample: Option[List[String]] = None
	)
	
	case class GoogleCloudRetailV2alphaPurgeUserEventsResponse(
	  /** The total count of events purged as a result of the operation. */
		purgedEventsCount: Option[String] = None
	)
	
	case class GoogleCloudRetailV2alphaRejoinUserEventsMetadata(
	
	)
	
	case class GoogleCloudRetailV2alphaRejoinUserEventsResponse(
	  /** Number of user events that were joined with latest product catalog. */
		rejoinedUserEventsCount: Option[String] = None
	)
	
	case class GoogleCloudRetailV2alphaRemoveFulfillmentPlacesMetadata(
	
	)
	
	case class GoogleCloudRetailV2alphaRemoveFulfillmentPlacesResponse(
	
	)
	
	case class GoogleCloudRetailV2alphaRemoveLocalInventoriesMetadata(
	
	)
	
	case class GoogleCloudRetailV2alphaRemoveLocalInventoriesResponse(
	
	)
	
	case class GoogleCloudRetailV2alphaSetInventoryMetadata(
	
	)
	
	case class GoogleCloudRetailV2alphaSetInventoryResponse(
	
	)
	
	case class GoogleCloudRetailV2alphaTuneModelMetadata(
	  /** The resource name of the model that this tune applies to. Format: `projects/{project_number}/locations/{location_id}/catalogs/{catalog_id}/models/{model_id}` */
		model: Option[String] = None
	)
	
	case class GoogleCloudRetailV2alphaTuneModelResponse(
	
	)
	
	case class GoogleCloudRetailV2betaAddFulfillmentPlacesMetadata(
	
	)
	
	case class GoogleCloudRetailV2betaAddFulfillmentPlacesResponse(
	
	)
	
	case class GoogleCloudRetailV2betaAddLocalInventoriesMetadata(
	
	)
	
	case class GoogleCloudRetailV2betaAddLocalInventoriesResponse(
	
	)
	
	case class GoogleCloudRetailV2betaCreateModelMetadata(
	  /** The resource name of the model that this create applies to. Format: `projects/{project_number}/locations/{location_id}/catalogs/{catalog_id}/models/{model_id}` */
		model: Option[String] = None
	)
	
	case class GoogleCloudRetailV2betaExportAnalyticsMetricsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** This field is never set. */
		errorsConfig: Option[Schema.GoogleCloudRetailV2betaExportErrorsConfig] = None,
	  /** Output result indicating where the data were exported to. */
		outputResult: Option[Schema.GoogleCloudRetailV2betaOutputResult] = None
	)
	
	case class GoogleCloudRetailV2betaExportErrorsConfig(
	  /** Google Cloud Storage path for import errors. This must be an empty, existing Cloud Storage bucket. Export errors will be written to a file in this bucket, one per line, as a JSON-encoded `google.rpc.Status` message. */
		gcsPrefix: Option[String] = None
	)
	
	case class GoogleCloudRetailV2betaOutputResult(
	  /** The BigQuery location where the result is stored. */
		bigqueryResult: Option[List[Schema.GoogleCloudRetailV2betaBigQueryOutputResult]] = None,
	  /** The Google Cloud Storage location where the result is stored. */
		gcsResult: Option[List[Schema.GoogleCloudRetailV2betaGcsOutputResult]] = None
	)
	
	case class GoogleCloudRetailV2betaBigQueryOutputResult(
	  /** The ID of a BigQuery Dataset. */
		datasetId: Option[String] = None,
	  /** The ID of a BigQuery Table. */
		tableId: Option[String] = None
	)
	
	case class GoogleCloudRetailV2betaGcsOutputResult(
	  /** The uri of Gcs output */
		outputUri: Option[String] = None
	)
	
	case class GoogleCloudRetailV2betaExportMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudRetailV2betaExportProductsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** This field is never set. */
		errorsConfig: Option[Schema.GoogleCloudRetailV2betaExportErrorsConfig] = None,
	  /** Output result indicating where the data were exported to. */
		outputResult: Option[Schema.GoogleCloudRetailV2betaOutputResult] = None
	)
	
	case class GoogleCloudRetailV2betaExportUserEventsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** This field is never set. */
		errorsConfig: Option[Schema.GoogleCloudRetailV2betaExportErrorsConfig] = None,
	  /** Output result indicating where the data were exported to. */
		outputResult: Option[Schema.GoogleCloudRetailV2betaOutputResult] = None
	)
	
	case class GoogleCloudRetailV2betaImportCompletionDataResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None
	)
	
	case class GoogleCloudRetailV2betaImportErrorsConfig(
	  /** Google Cloud Storage prefix for import errors. This must be an empty, existing Cloud Storage directory. Import errors are written to sharded files in this directory, one per line, as a JSON-encoded `google.rpc.Status` message. */
		gcsPrefix: Option[String] = None
	)
	
	case class GoogleCloudRetailV2betaImportMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of entries that were processed successfully. */
		successCount: Option[String] = None,
	  /** Count of entries that encountered errors while processing. */
		failureCount: Option[String] = None,
	  /** Deprecated. This field is never set. */
		requestId: Option[String] = None,
	  /** Pub/Sub topic for receiving notification. If this field is set, when the import is finished, a notification is sent to specified Pub/Sub topic. The message data is JSON string of a Operation. Format of the Pub/Sub topic is `projects/{project}/topics/{topic}`. */
		notificationPubsubTopic: Option[String] = None
	)
	
	case class GoogleCloudRetailV2betaImportProductsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Echoes the destination for the complete errors in the request if set. */
		errorsConfig: Option[Schema.GoogleCloudRetailV2betaImportErrorsConfig] = None
	)
	
	case class GoogleCloudRetailV2betaImportUserEventsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Echoes the destination for the complete errors if this field was set in the request. */
		errorsConfig: Option[Schema.GoogleCloudRetailV2betaImportErrorsConfig] = None,
	  /** Aggregated statistics of user event import status. */
		importSummary: Option[Schema.GoogleCloudRetailV2betaUserEventImportSummary] = None
	)
	
	case class GoogleCloudRetailV2betaUserEventImportSummary(
	  /** Count of user events imported with complete existing catalog information. */
		joinedEventsCount: Option[String] = None,
	  /** Count of user events imported, but with catalog information not found in the imported catalog. */
		unjoinedEventsCount: Option[String] = None
	)
	
	object GoogleCloudRetailV2betaModel {
		enum TrainingStateEnum extends Enum[TrainingStateEnum] { case TRAINING_STATE_UNSPECIFIED, PAUSED, TRAINING }
		enum ServingStateEnum extends Enum[ServingStateEnum] { case SERVING_STATE_UNSPECIFIED, INACTIVE, ACTIVE, TUNED }
		enum PeriodicTuningStateEnum extends Enum[PeriodicTuningStateEnum] { case PERIODIC_TUNING_STATE_UNSPECIFIED, PERIODIC_TUNING_DISABLED, ALL_TUNING_DISABLED, PERIODIC_TUNING_ENABLED }
		enum DataStateEnum extends Enum[DataStateEnum] { case DATA_STATE_UNSPECIFIED, DATA_OK, DATA_ERROR }
		enum FilteringOptionEnum extends Enum[FilteringOptionEnum] { case RECOMMENDATIONS_FILTERING_OPTION_UNSPECIFIED, RECOMMENDATIONS_FILTERING_DISABLED, RECOMMENDATIONS_FILTERING_ENABLED }
	}
	case class GoogleCloudRetailV2betaModel(
	  /** Required. The fully qualified resource name of the model. Format: `projects/{project_number}/locations/{location_id}/catalogs/{catalog_id}/models/{model_id}` catalog_id has char limit of 50. recommendation_model_id has char limit of 40. */
		name: Option[String] = None,
	  /** Required. The display name of the model. Should be human readable, used to display Recommendation Models in the Retail Cloud Console Dashboard. UTF-8 encoded string with limit of 1024 characters. */
		displayName: Option[String] = None,
	  /** Optional. The training state that the model is in (e.g. `TRAINING` or `PAUSED`). Since part of the cost of running the service is frequency of training - this can be used to determine when to train model in order to control cost. If not specified: the default value for `CreateModel` method is `TRAINING`. The default value for `UpdateModel` method is to keep the state the same as before. */
		trainingState: Option[Schema.GoogleCloudRetailV2betaModel.TrainingStateEnum] = None,
	  /** Output only. The serving state of the model: `ACTIVE`, `NOT_ACTIVE`. */
		servingState: Option[Schema.GoogleCloudRetailV2betaModel.ServingStateEnum] = None,
	  /** Output only. Timestamp the Recommendation Model was created at. */
		createTime: Option[String] = None,
	  /** Output only. Timestamp the Recommendation Model was last updated. E.g. if a Recommendation Model was paused - this would be the time the pause was initiated. */
		updateTime: Option[String] = None,
	  /** Required. The type of model e.g. `home-page`. Currently supported values: `recommended-for-you`, `others-you-may-like`, `frequently-bought-together`, `page-optimization`, `similar-items`, `buy-it-again`, `on-sale-items`, and `recently-viewed`(readonly value). This field together with optimization_objective describe model metadata to use to control model training and serving. See https://cloud.google.com/retail/docs/models for more details on what the model metadata control and which combination of parameters are valid. For invalid combinations of parameters (e.g. type = `frequently-bought-together` and optimization_objective = `ctr`), you receive an error 400 if you try to create/update a recommendation with this set of knobs. */
		`type`: Option[String] = None,
	  /** Optional. The optimization objective e.g. `cvr`. Currently supported values: `ctr`, `cvr`, `revenue-per-order`. If not specified, we choose default based on model type. Default depends on type of recommendation: `recommended-for-you` => `ctr` `others-you-may-like` => `ctr` `frequently-bought-together` => `revenue_per_order` This field together with optimization_objective describe model metadata to use to control model training and serving. See https://cloud.google.com/retail/docs/models for more details on what the model metadata control and which combination of parameters are valid. For invalid combinations of parameters (e.g. type = `frequently-bought-together` and optimization_objective = `ctr`), you receive an error 400 if you try to create/update a recommendation with this set of knobs. */
		optimizationObjective: Option[String] = None,
	  /** Optional. The state of periodic tuning. The period we use is 3 months - to do a one-off tune earlier use the `TuneModel` method. Default value is `PERIODIC_TUNING_ENABLED`. */
		periodicTuningState: Option[Schema.GoogleCloudRetailV2betaModel.PeriodicTuningStateEnum] = None,
	  /** Output only. The timestamp when the latest successful tune finished. */
		lastTuneTime: Option[String] = None,
	  /** Output only. The tune operation associated with the model. Can be used to determine if there is an ongoing tune for this recommendation. Empty field implies no tune is goig on. */
		tuningOperation: Option[String] = None,
	  /** Output only. The state of data requirements for this model: `DATA_OK` and `DATA_ERROR`. Recommendation model cannot be trained if the data is in `DATA_ERROR` state. Recommendation model can have `DATA_ERROR` state even if serving state is `ACTIVE`: models were trained successfully before, but cannot be refreshed because model no longer has sufficient data for training. */
		dataState: Option[Schema.GoogleCloudRetailV2betaModel.DataStateEnum] = None,
	  /** Optional. If `RECOMMENDATIONS_FILTERING_ENABLED`, recommendation filtering by attributes is enabled for the model. */
		filteringOption: Option[Schema.GoogleCloudRetailV2betaModel.FilteringOptionEnum] = None,
	  /** Output only. The list of valid serving configs associated with the PageOptimizationConfig. */
		servingConfigLists: Option[List[Schema.GoogleCloudRetailV2betaModelServingConfigList]] = None,
	  /** Optional. Additional model features config. */
		modelFeaturesConfig: Option[Schema.GoogleCloudRetailV2betaModelModelFeaturesConfig] = None
	)
	
	case class GoogleCloudRetailV2betaModelServingConfigList(
	  /** Optional. A set of valid serving configs that may be used for `PAGE_OPTIMIZATION`. */
		servingConfigIds: Option[List[String]] = None
	)
	
	case class GoogleCloudRetailV2betaModelModelFeaturesConfig(
	  /** Additional configs for frequently-bought-together models. */
		frequentlyBoughtTogetherConfig: Option[Schema.GoogleCloudRetailV2betaModelFrequentlyBoughtTogetherFeaturesConfig] = None
	)
	
	object GoogleCloudRetailV2betaModelFrequentlyBoughtTogetherFeaturesConfig {
		enum ContextProductsTypeEnum extends Enum[ContextProductsTypeEnum] { case CONTEXT_PRODUCTS_TYPE_UNSPECIFIED, SINGLE_CONTEXT_PRODUCT, MULTIPLE_CONTEXT_PRODUCTS }
	}
	case class GoogleCloudRetailV2betaModelFrequentlyBoughtTogetherFeaturesConfig(
	  /** Optional. Specifies the context of the model when it is used in predict requests. Can only be set for the `frequently-bought-together` type. If it isn't specified, it defaults to MULTIPLE_CONTEXT_PRODUCTS. */
		contextProductsType: Option[Schema.GoogleCloudRetailV2betaModelFrequentlyBoughtTogetherFeaturesConfig.ContextProductsTypeEnum] = None
	)
	
	case class GoogleCloudRetailV2betaPurgeMetadata(
	
	)
	
	case class GoogleCloudRetailV2betaPurgeProductsMetadata(
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None,
	  /** Count of entries that were deleted successfully. */
		successCount: Option[String] = None,
	  /** Count of entries that encountered errors while processing. */
		failureCount: Option[String] = None
	)
	
	case class GoogleCloudRetailV2betaPurgeProductsResponse(
	  /** The total count of products purged as a result of the operation. */
		purgeCount: Option[String] = None,
	  /** A sample of the product names that will be deleted. Only populated if `force` is set to false. A max of 100 names will be returned and the names are chosen at random. */
		purgeSample: Option[List[String]] = None
	)
	
	case class GoogleCloudRetailV2betaPurgeUserEventsResponse(
	  /** The total count of events purged as a result of the operation. */
		purgedEventsCount: Option[String] = None
	)
	
	case class GoogleCloudRetailV2betaRejoinUserEventsMetadata(
	
	)
	
	case class GoogleCloudRetailV2betaRejoinUserEventsResponse(
	  /** Number of user events that were joined with latest product catalog. */
		rejoinedUserEventsCount: Option[String] = None
	)
	
	case class GoogleCloudRetailV2betaRemoveFulfillmentPlacesMetadata(
	
	)
	
	case class GoogleCloudRetailV2betaRemoveFulfillmentPlacesResponse(
	
	)
	
	case class GoogleCloudRetailV2betaRemoveLocalInventoriesMetadata(
	
	)
	
	case class GoogleCloudRetailV2betaRemoveLocalInventoriesResponse(
	
	)
	
	case class GoogleCloudRetailV2betaSetInventoryMetadata(
	
	)
	
	case class GoogleCloudRetailV2betaSetInventoryResponse(
	
	)
	
	case class GoogleCloudRetailV2betaTuneModelMetadata(
	  /** The resource name of the model that this tune applies to. Format: `projects/{project_number}/locations/{location_id}/catalogs/{catalog_id}/models/{model_id}` */
		model: Option[String] = None
	)
	
	case class GoogleCloudRetailV2betaTuneModelResponse(
	
	)
}
