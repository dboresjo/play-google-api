package com.boresjo.play.api.google.recommendationengine

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
	
	case class GoogleCloudRecommendationengineV1beta1ListCatalogsResponse(
	  /** Output only. All the customer's catalogs. */
		catalogs: Option[List[Schema.GoogleCloudRecommendationengineV1beta1Catalog]] = None,
	  /** Pagination token, if not returned indicates the last page. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1Catalog(
	  /** The fully qualified resource name of the catalog. */
		name: Option[String] = None,
	  /** Required. The catalog display name. */
		displayName: Option[String] = None,
	  /** Required. The ID of the default event store. */
		defaultEventStoreId: Option[String] = None,
	  /** Required. The catalog item level configuration. */
		catalogItemLevelConfig: Option[Schema.GoogleCloudRecommendationengineV1beta1CatalogItemLevelConfig] = None
	)
	
	object GoogleCloudRecommendationengineV1beta1CatalogItemLevelConfig {
		enum EventItemLevelEnum extends Enum[EventItemLevelEnum] { case CATALOG_ITEM_LEVEL_UNSPECIFIED, VARIANT, MASTER }
		enum PredictItemLevelEnum extends Enum[PredictItemLevelEnum] { case CATALOG_ITEM_LEVEL_UNSPECIFIED, VARIANT, MASTER }
	}
	case class GoogleCloudRecommendationengineV1beta1CatalogItemLevelConfig(
	  /** Optional. Level of the catalog at which events are uploaded. See https://cloud.google.com/recommendations-ai/docs/catalog#catalog-levels for more details. */
		eventItemLevel: Option[Schema.GoogleCloudRecommendationengineV1beta1CatalogItemLevelConfig.EventItemLevelEnum] = None,
	  /** Optional. Level of the catalog at which predictions are made. See https://cloud.google.com/recommendations-ai/docs/catalog#catalog-levels for more details. */
		predictItemLevel: Option[Schema.GoogleCloudRecommendationengineV1beta1CatalogItemLevelConfig.PredictItemLevelEnum] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1CatalogItem(
	  /** Required. Catalog item identifier. UTF-8 encoded string with a length limit of 128 bytes. This id must be unique among all catalog items within the same catalog. It should also be used when logging user events in order for the user events to be joined with the Catalog. */
		id: Option[String] = None,
	  /** Required. Catalog item categories. This field is repeated for supporting one catalog item belonging to several parallel category hierarchies. For example, if a shoes product belongs to both ["Shoes & Accessories" -> "Shoes"] and ["Sports & Fitness" -> "Athletic Clothing" -> "Shoes"], it could be represented as: "categoryHierarchies": [ { "categories": ["Shoes & Accessories", "Shoes"]}, { "categories": ["Sports & Fitness", "Athletic Clothing", "Shoes"] } ] */
		categoryHierarchies: Option[List[Schema.GoogleCloudRecommendationengineV1beta1CatalogItemCategoryHierarchy]] = None,
	  /** Required. Catalog item title. UTF-8 encoded string with a length limit of 1 KiB. */
		title: Option[String] = None,
	  /** Optional. Catalog item description. UTF-8 encoded string with a length limit of 5 KiB. */
		description: Option[String] = None,
	  /** Optional. Highly encouraged. Extra catalog item attributes to be included in the recommendation model. For example, for retail products, this could include the store name, vendor, style, color, etc. These are very strong signals for recommendation model, thus we highly recommend providing the item attributes here. */
		itemAttributes: Option[Schema.GoogleCloudRecommendationengineV1beta1FeatureMap] = None,
	  /** Optional. Deprecated. The model automatically detects the text language. Your catalog can include text in different languages, but duplicating catalog items to provide text in multiple languages can result in degraded model performance. */
		languageCode: Option[String] = None,
	  /** Optional. Filtering tags associated with the catalog item. Each tag should be a UTF-8 encoded string with a length limit of 1 KiB. This tag can be used for filtering recommendation results by passing the tag as part of the predict request filter. */
		tags: Option[List[String]] = None,
	  /** Optional. Variant group identifier for prediction results. UTF-8 encoded string with a length limit of 128 bytes. This field must be enabled before it can be used. [Learn more](/recommendations-ai/docs/catalog#item-group-id). */
		itemGroupId: Option[String] = None,
	  /** Optional. Metadata specific to retail products. */
		productMetadata: Option[Schema.GoogleCloudRecommendationengineV1beta1ProductCatalogItem] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1CatalogItemCategoryHierarchy(
	  /** Required. Catalog item categories. Each category should be a UTF-8 encoded string with a length limit of 2 KiB. Note that the order in the list denotes the specificity (from least to most specific). */
		categories: Option[List[String]] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1FeatureMap(
	  /** Categorical features that can take on one of a limited number of possible values. Some examples would be the brand/maker of a product, or country of a customer. Feature names and values must be UTF-8 encoded strings. For example: `{ "colors": {"value": ["yellow", "green"]}, "sizes": {"value":["S", "M"]}` */
		categoricalFeatures: Option[Map[String, Schema.GoogleCloudRecommendationengineV1beta1FeatureMapStringList]] = None,
	  /** Numerical features. Some examples would be the height/weight of a product, or age of a customer. Feature names must be UTF-8 encoded strings. For example: `{ "lengths_cm": {"value":[2.3, 15.4]}, "heights_cm": {"value":[8.1, 6.4]} }` */
		numericalFeatures: Option[Map[String, Schema.GoogleCloudRecommendationengineV1beta1FeatureMapFloatList]] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1FeatureMapStringList(
	  /** String feature value with a length limit of 128 bytes. */
		value: Option[List[String]] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1FeatureMapFloatList(
	  /** Float feature value. */
		value: Option[List[BigDecimal]] = None
	)
	
	object GoogleCloudRecommendationengineV1beta1ProductCatalogItem {
		enum StockStateEnum extends Enum[StockStateEnum] { case STOCK_STATE_UNSPECIFIED, IN_STOCK, OUT_OF_STOCK, PREORDER, BACKORDER }
	}
	case class GoogleCloudRecommendationengineV1beta1ProductCatalogItem(
	  /** Optional. The exact product price. */
		exactPrice: Option[Schema.GoogleCloudRecommendationengineV1beta1ProductCatalogItemExactPrice] = None,
	  /** Optional. The product price range. */
		priceRange: Option[Schema.GoogleCloudRecommendationengineV1beta1ProductCatalogItemPriceRange] = None,
	  /** Optional. A map to pass the costs associated with the product. For example: {"manufacturing": 45.5} The profit of selling this item is computed like so: &#42; If 'exactPrice' is provided, profit = displayPrice - sum(costs) &#42; If 'priceRange' is provided, profit = minPrice - sum(costs) */
		costs: Option[Map[String, BigDecimal]] = None,
	  /** Optional. Only required if the price is set. Currency code for price/costs. Use three-character ISO-4217 code. */
		currencyCode: Option[String] = None,
	  /** Optional. Online stock state of the catalog item. Default is `IN_STOCK`. */
		stockState: Option[Schema.GoogleCloudRecommendationengineV1beta1ProductCatalogItem.StockStateEnum] = None,
	  /** Optional. The available quantity of the item. */
		availableQuantity: Option[String] = None,
	  /** Optional. Canonical URL directly linking to the item detail page with a length limit of 5 KiB.. */
		canonicalProductUri: Option[String] = None,
	  /** Optional. Product images for the catalog item. */
		images: Option[List[Schema.GoogleCloudRecommendationengineV1beta1Image]] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1ProductCatalogItemExactPrice(
	  /** Optional. Display price of the product. */
		displayPrice: Option[BigDecimal] = None,
	  /** Optional. Price of the product without any discount. If zero, by default set to be the 'displayPrice'. */
		originalPrice: Option[BigDecimal] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1ProductCatalogItemPriceRange(
	  /** Required. The minimum product price. */
		min: Option[BigDecimal] = None,
	  /** Required. The maximum product price. */
		max: Option[BigDecimal] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1Image(
	  /** Required. URL of the image with a length limit of 5 KiB. */
		uri: Option[String] = None,
	  /** Optional. Height of the image in number of pixels. */
		height: Option[Int] = None,
	  /** Optional. Width of the image in number of pixels. */
		width: Option[Int] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1ListCatalogItemsResponse(
	  /** The catalog items. */
		catalogItems: Option[List[Schema.GoogleCloudRecommendationengineV1beta1CatalogItem]] = None,
	  /** If empty, the list is complete. If nonempty, the token to pass to the next request's ListCatalogItemRequest.page_token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleCloudRecommendationengineV1beta1ImportCatalogItemsRequest(
	  /** Optional. Unique identifier provided by client, within the ancestor dataset scope. Ensures idempotency and used for request deduplication. Server-generated if unspecified. Up to 128 characters long. This is returned as google.longrunning.Operation.name in the response. */
		requestId: Option[String] = None,
	  /** Required. The desired input location of the data. */
		inputConfig: Option[Schema.GoogleCloudRecommendationengineV1beta1InputConfig] = None,
	  /** Optional. The desired location of errors incurred during the Import. */
		errorsConfig: Option[Schema.GoogleCloudRecommendationengineV1beta1ImportErrorsConfig] = None,
	  /** Optional. Indicates which fields in the provided imported 'items' to update. If not set, will by default update all fields. */
		updateMask: Option[String] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1InputConfig(
	  /** The Inline source for the input content for Catalog items. */
		catalogInlineSource: Option[Schema.GoogleCloudRecommendationengineV1beta1CatalogInlineSource] = None,
	  /** Google Cloud Storage location for the input content. */
		gcsSource: Option[Schema.GoogleCloudRecommendationengineV1beta1GcsSource] = None,
	  /** The Inline source for the input content for UserEvents. */
		userEventInlineSource: Option[Schema.GoogleCloudRecommendationengineV1beta1UserEventInlineSource] = None,
	  /** BigQuery input source. */
		bigQuerySource: Option[Schema.GoogleCloudRecommendationengineV1beta1BigQuerySource] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1CatalogInlineSource(
	  /** Optional. A list of catalog items to update/create. Recommended max of 10k items. */
		catalogItems: Option[List[Schema.GoogleCloudRecommendationengineV1beta1CatalogItem]] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1GcsSource(
	  /** Required. Google Cloud Storage URIs to input files. URI can be up to 2000 characters long. URIs can match the full object path (for example, `gs://bucket/directory/object.json`) or a pattern matching one or more files, such as `gs://bucket/directory/&#42;.json`. A request can contain at most 100 files, and each file can be up to 2 GB. See [Importing catalog information](/recommendations-ai/docs/upload-catalog) for the expected file format and setup instructions. */
		inputUris: Option[List[String]] = None,
	  /** Optional. The schema to use when parsing the data from the source. Supported values for catalog imports: 1: "catalog_recommendations_ai" using https://cloud.google.com/recommendations-ai/docs/upload-catalog#json (Default for catalogItems.import) 2: "catalog_merchant_center" using https://cloud.google.com/recommendations-ai/docs/upload-catalog#mc Supported values for user events imports: 1: "user_events_recommendations_ai" using https://cloud.google.com/recommendations-ai/docs/manage-user-events#import (Default for userEvents.import) 2. "user_events_ga360" using https://support.google.com/analytics/answer/3437719?hl=en */
		jsonSchema: Option[String] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1UserEventInlineSource(
	  /** Optional. A list of user events to import. Recommended max of 10k items. */
		userEvents: Option[List[Schema.GoogleCloudRecommendationengineV1beta1UserEvent]] = None
	)
	
	object GoogleCloudRecommendationengineV1beta1UserEvent {
		enum EventSourceEnum extends Enum[EventSourceEnum] { case EVENT_SOURCE_UNSPECIFIED, AUTOML, ECOMMERCE, BATCH_UPLOAD }
	}
	case class GoogleCloudRecommendationengineV1beta1UserEvent(
	  /** Required. User event type. Allowed values are: &#42; `add-to-cart` Products being added to cart. &#42; `add-to-list` Items being added to a list (shopping list, favorites etc). &#42; `category-page-view` Special pages such as sale or promotion pages viewed. &#42; `checkout-start` User starting a checkout process. &#42; `detail-page-view` Products detail page viewed. &#42; `home-page-view` Homepage viewed. &#42; `page-visit` Generic page visits not included in the event types above. &#42; `purchase-complete` User finishing a purchase. &#42; `refund` Purchased items being refunded or returned. &#42; `remove-from-cart` Products being removed from cart. &#42; `remove-from-list` Items being removed from a list. &#42; `search` Product search. &#42; `shopping-cart-page-view` User viewing a shopping cart. &#42; `impression` List of items displayed. Used by Google Tag Manager. */
		eventType: Option[String] = None,
	  /** Required. User information. */
		userInfo: Option[Schema.GoogleCloudRecommendationengineV1beta1UserInfo] = None,
	  /** Optional. User event detailed information common across different recommendation types. */
		eventDetail: Option[Schema.GoogleCloudRecommendationengineV1beta1EventDetail] = None,
	  /** Optional. Retail product specific user event metadata. This field is required for the following event types: &#42; `add-to-cart` &#42; `add-to-list` &#42; `category-page-view` &#42; `checkout-start` &#42; `detail-page-view` &#42; `purchase-complete` &#42; `refund` &#42; `remove-from-cart` &#42; `remove-from-list` &#42; `search` This field is optional for the following event types: &#42; `page-visit` &#42; `shopping-cart-page-view` - note that 'product_event_detail' should be set for this unless the shopping cart is empty. This field is not allowed for the following event types: &#42; `home-page-view` */
		productEventDetail: Option[Schema.GoogleCloudRecommendationengineV1beta1ProductEventDetail] = None,
	  /** Optional. Only required for ImportUserEvents method. Timestamp of user event created. */
		eventTime: Option[String] = None,
	  /** Optional. This field should &#42;not&#42; be set when using JavaScript pixel or the Recommendations AI Tag. Defaults to `EVENT_SOURCE_UNSPECIFIED`. */
		eventSource: Option[Schema.GoogleCloudRecommendationengineV1beta1UserEvent.EventSourceEnum] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1UserInfo(
	  /** Required. A unique identifier for tracking visitors with a length limit of 128 bytes. For example, this could be implemented with an HTTP cookie, which should be able to uniquely identify a visitor on a single device. This unique identifier should not change if the visitor logs in or out of the website. Maximum length 128 bytes. Cannot be empty. Don't set the field to the same fixed ID for different users. This mixes the event history of those users together, which results in degraded model quality. */
		visitorId: Option[String] = None,
	  /** Optional. Unique identifier for logged-in user with a length limit of 128 bytes. Required only for logged-in users. Don't set for anonymous users. Don't set the field to the same fixed ID for different users. This mixes the event history of those users together, which results in degraded model quality. */
		userId: Option[String] = None,
	  /** Optional. IP address of the user. This could be either IPv4 (e.g. 104.133.9.80) or IPv6 (e.g. 2001:0db8:85a3:0000:0000:8a2e:0370:7334). This should &#42;not&#42; be set when using the javascript pixel or if `direct_user_request` is set. Used to extract location information for personalization. */
		ipAddress: Option[String] = None,
	  /** Optional. User agent as included in the HTTP header. UTF-8 encoded string with a length limit of 1 KiB. This should &#42;not&#42; be set when using the JavaScript pixel or if `directUserRequest` is set. */
		userAgent: Option[String] = None,
	  /** Optional. Indicates if the request is made directly from the end user in which case the user_agent and ip_address fields can be populated from the HTTP request. This should &#42;not&#42; be set when using the javascript pixel. This flag should be set only if the API request is made directly from the end user such as a mobile app (and not if a gateway or a server is processing and pushing the user events). */
		directUserRequest: Option[Boolean] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1EventDetail(
	  /** Optional. Complete url (window.location.href) of the user's current page. When using the JavaScript pixel, this value is filled in automatically. Maximum length 5KB. */
		uri: Option[String] = None,
	  /** Optional. The referrer url of the current page. When using the JavaScript pixel, this value is filled in automatically. */
		referrerUri: Option[String] = None,
	  /** Optional. A unique id of a web page view. This should be kept the same for all user events triggered from the same pageview. For example, an item detail page view could trigger multiple events as the user is browsing the page. The `pageViewId` property should be kept the same for all these events so that they can be grouped together properly. This `pageViewId` will be automatically generated if using the JavaScript pixel. */
		pageViewId: Option[String] = None,
	  /** Optional. A list of identifiers for the independent experiment groups this user event belongs to. This is used to distinguish between user events associated with different experiment setups (e.g. using Recommendation Engine system, using different recommendation models). */
		experimentIds: Option[List[String]] = None,
	  /** Optional. Recommendation token included in the recommendation prediction response. This field enables accurate attribution of recommendation model performance. This token enables us to accurately attribute page view or purchase back to the event and the particular predict response containing this clicked/purchased item. If user clicks on product K in the recommendation results, pass the `PredictResponse.recommendationToken` property as a url parameter to product K's page. When recording events on product K's page, log the PredictResponse.recommendation_token to this field. Optional, but highly encouraged for user events that are the result of a recommendation prediction query. */
		recommendationToken: Option[String] = None,
	  /** Optional. Extra user event features to include in the recommendation model. For product recommendation, an example of extra user information is traffic_channel, i.e. how user arrives at the site. Users can arrive at the site by coming to the site directly, or coming through Google search, and etc. */
		eventAttributes: Option[Schema.GoogleCloudRecommendationengineV1beta1FeatureMap] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1ProductEventDetail(
	  /** At least one of search_query or page_categories is required for `search` events. Other event types should not set this field. The user's search query as UTF-8 encoded text with a length limit of 5 KiB. */
		searchQuery: Option[String] = None,
	  /** Required for `category-page-view` events. At least one of search_query or page_categories is required for `search` events. Other event types should not set this field. The categories associated with a category page. Category pages include special pages such as sales or promotions. For instance, a special sale page may have the category hierarchy: categories : ["Sales", "2017 Black Friday Deals"]. */
		pageCategories: Option[List[Schema.GoogleCloudRecommendationengineV1beta1CatalogItemCategoryHierarchy]] = None,
	  /** The main product details related to the event. This field is required for the following event types: &#42; `add-to-cart` &#42; `add-to-list` &#42; `checkout-start` &#42; `detail-page-view` &#42; `purchase-complete` &#42; `refund` &#42; `remove-from-cart` &#42; `remove-from-list` This field is optional for the following event types: &#42; `page-visit` &#42; `shopping-cart-page-view` - note that 'product_details' should be set for this unless the shopping cart is empty. &#42; `search` (highly encouraged) In a `search` event, this field represents the products returned to the end user on the current page (the end user may have not finished broswing the whole page yet). When a new page is returned to the end user, after pagination/filtering/ordering even for the same query, a new SEARCH event with different product_details is desired. The end user may have not finished broswing the whole page yet. This field is not allowed for the following event types: &#42; `category-page-view` &#42; `home-page-view` */
		productDetails: Option[List[Schema.GoogleCloudRecommendationengineV1beta1ProductDetail]] = None,
	  /** Required for `add-to-list` and `remove-from-list` events. The id or name of the list that the item is being added to or removed from. Other event types should not set this field. */
		listId: Option[String] = None,
	  /** Optional. The id or name of the associated shopping cart. This id is used to associate multiple items added or present in the cart before purchase. This can only be set for `add-to-cart`, `remove-from-cart`, `checkout-start`, `purchase-complete`, or `shopping-cart-page-view` events. */
		cartId: Option[String] = None,
	  /** Optional. A transaction represents the entire purchase transaction. Required for `purchase-complete` events. Optional for `checkout-start` events. Other event types should not set this field. */
		purchaseTransaction: Option[Schema.GoogleCloudRecommendationengineV1beta1PurchaseTransaction] = None
	)
	
	object GoogleCloudRecommendationengineV1beta1ProductDetail {
		enum StockStateEnum extends Enum[StockStateEnum] { case STOCK_STATE_UNSPECIFIED, IN_STOCK, OUT_OF_STOCK, PREORDER, BACKORDER }
	}
	case class GoogleCloudRecommendationengineV1beta1ProductDetail(
	  /** Required. Catalog item ID. UTF-8 encoded string with a length limit of 128 characters. */
		id: Option[String] = None,
	  /** Optional. Currency code for price/costs. Use three-character ISO-4217 code. Required only if originalPrice or displayPrice is set. */
		currencyCode: Option[String] = None,
	  /** Optional. Original price of the product. If provided, this will override the original price in Catalog for this product. */
		originalPrice: Option[BigDecimal] = None,
	  /** Optional. Display price of the product (e.g. discounted price). If provided, this will override the display price in Catalog for this product. */
		displayPrice: Option[BigDecimal] = None,
	  /** Optional. Item stock state. If provided, this overrides the stock state in Catalog for items in this event. */
		stockState: Option[Schema.GoogleCloudRecommendationengineV1beta1ProductDetail.StockStateEnum] = None,
	  /** Optional. Quantity of the product associated with the user event. For example, this field will be 2 if two products are added to the shopping cart for `add-to-cart` event. Required for `add-to-cart`, `add-to-list`, `remove-from-cart`, `checkout-start`, `purchase-complete`, `refund` event types. */
		quantity: Option[Int] = None,
	  /** Optional. Quantity of the products in stock when a user event happens. Optional. If provided, this overrides the available quantity in Catalog for this event. and can only be set if `stock_status` is set to `IN_STOCK`. Note that if an item is out of stock, you must set the `stock_state` field to be `OUT_OF_STOCK`. Leaving this field unspecified / as zero is not sufficient to mark the item out of stock. */
		availableQuantity: Option[Int] = None,
	  /** Optional. Extra features associated with a product in the user event. */
		itemAttributes: Option[Schema.GoogleCloudRecommendationengineV1beta1FeatureMap] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1PurchaseTransaction(
	  /** Optional. The transaction ID with a length limit of 128 bytes. */
		id: Option[String] = None,
	  /** Required. Total revenue or grand total associated with the transaction. This value include shipping, tax, or other adjustments to total revenue that you want to include as part of your revenue calculations. This field is not required if the event type is `refund`. */
		revenue: Option[BigDecimal] = None,
	  /** Optional. All the taxes associated with the transaction. */
		taxes: Option[Map[String, BigDecimal]] = None,
	  /** Optional. All the costs associated with the product. These can be manufacturing costs, shipping expenses not borne by the end user, or any other costs. Total product cost such that profit = revenue - (sum(taxes) + sum(costs)) If product_cost is not set, then profit = revenue - tax - shipping - sum(CatalogItem.costs). If CatalogItem.cost is not specified for one of the items, CatalogItem.cost based profit &#42;cannot&#42; be calculated for this Transaction. */
		costs: Option[Map[String, BigDecimal]] = None,
	  /** Required. Currency code. Use three-character ISO-4217 code. This field is not required if the event type is `refund`. */
		currencyCode: Option[String] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1BigQuerySource(
	  /** Optional. The project id (can be project # or id) that the BigQuery source is in. If not specified, inherits the project id from the parent request. */
		projectId: Option[String] = None,
	  /** Required. The BigQuery data set to copy the data from. */
		datasetId: Option[String] = None,
	  /** Required. The BigQuery table to copy the data from. */
		tableId: Option[String] = None,
	  /** Optional. Intermediate Cloud Storage directory used for the import. Can be specified if one wants to have the BigQuery export to a specific Cloud Storage directory. */
		gcsStagingDir: Option[String] = None,
	  /** Optional. The schema to use when parsing the data from the source. Supported values for catalog imports: 1: "catalog_recommendations_ai" using https://cloud.google.com/recommendations-ai/docs/upload-catalog#json (Default for catalogItems.import) 2: "catalog_merchant_center" using https://cloud.google.com/recommendations-ai/docs/upload-catalog#mc Supported values for user event imports: 1: "user_events_recommendations_ai" using https://cloud.google.com/recommendations-ai/docs/manage-user-events#import (Default for userEvents.import) 2. "user_events_ga360" using https://support.google.com/analytics/answer/3437719?hl=en */
		dataSchema: Option[String] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1ImportErrorsConfig(
	  /** Google Cloud Storage path for import errors. This must be an empty, existing Cloud Storage bucket. Import errors will be written to a file in this bucket, one per line, as a JSON-encoded `google.rpc.Status` message. */
		gcsPrefix: Option[String] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1CreatePredictionApiKeyRegistrationRequest(
	  /** Required. The prediction API key registration. */
		predictionApiKeyRegistration: Option[Schema.GoogleCloudRecommendationengineV1beta1PredictionApiKeyRegistration] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1PredictionApiKeyRegistration(
	  /** The API key. */
		apiKey: Option[String] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1ListPredictionApiKeyRegistrationsResponse(
	  /** The list of registered API keys. */
		predictionApiKeyRegistrations: Option[List[Schema.GoogleCloudRecommendationengineV1beta1PredictionApiKeyRegistration]] = None,
	  /** If empty, the list is complete. If nonempty, pass the token to the next request's `ListPredictionApiKeysRegistrationsRequest.pageToken`. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1PredictRequest(
	  /** Required. Context about the user, what they are looking at and what action they took to trigger the predict request. Note that this user event detail won't be ingested to userEvent logs. Thus, a separate userEvent write request is required for event logging. Don't set UserInfo.visitor_id or UserInfo.user_id to the same fixed ID for different users. If you are trying to receive non-personalized recommendations (not recommended; this can negatively impact model performance), instead set UserInfo.visitor_id to a random unique ID and leave UserInfo.user_id unset. */
		userEvent: Option[Schema.GoogleCloudRecommendationengineV1beta1UserEvent] = None,
	  /** Optional. Maximum number of results to return per page. Set this property to the number of prediction results required. If zero, the service will choose a reasonable default. */
		pageSize: Option[Int] = None,
	  /** Optional. The previous PredictResponse.next_page_token. */
		pageToken: Option[String] = None,
	  /** Optional. Filter for restricting prediction results. Accepts values for tags and the `filterOutOfStockItems` flag. &#42; Tag expressions. Restricts predictions to items that match all of the specified tags. Boolean operators `OR` and `NOT` are supported if the expression is enclosed in parentheses, and must be separated from the tag values by a space. `-"tagA"` is also supported and is equivalent to `NOT "tagA"`. Tag values must be double quoted UTF-8 encoded strings with a size limit of 1 KiB. &#42; filterOutOfStockItems. Restricts predictions to items that do not have a stockState value of OUT_OF_STOCK. Examples: &#42; tag=("Red" OR "Blue") tag="New-Arrival" tag=(NOT "promotional") &#42; filterOutOfStockItems tag=(-"promotional") &#42; filterOutOfStockItems If your filter blocks all prediction results, nothing will be returned. If you want generic (unfiltered) popular items to be returned instead, set `strictFiltering` to false in `PredictRequest.params`. */
		filter: Option[String] = None,
	  /** Optional. Use dryRun mode for this prediction query. If set to true, a fake model will be used that returns arbitrary catalog items. Note that the dryRun mode should only be used for testing the API, or if the model is not ready. */
		dryRun: Option[Boolean] = None,
	  /** Optional. Additional domain specific parameters for the predictions. Allowed values: &#42; `returnCatalogItem`: Boolean. If set to true, the associated catalogItem object will be returned in the `PredictResponse.PredictionResult.itemMetadata` object in the method response. &#42; `returnItemScore`: Boolean. If set to true, the prediction 'score' corresponding to each returned item will be set in the `metadata` field in the prediction response. The given 'score' indicates the probability of an item being clicked/purchased given the user's context and history. &#42; `strictFiltering`: Boolean. True by default. If set to false, the service will return generic (unfiltered) popular items instead of empty if your filter blocks all prediction results. &#42; `priceRerankLevel`: String. Default empty. If set to be non-empty, then it needs to be one of {'no-price-reranking', 'low-price-reranking', 'medium-price-reranking', 'high-price-reranking'}. This gives request level control and adjust prediction results based on product price. &#42; `diversityLevel`: String. Default empty. If set to be non-empty, then it needs to be one of {'no-diversity', 'low-diversity', 'medium-diversity', 'high-diversity', 'auto-diversity'}. This gives request level control and adjust prediction results based on product category. */
		params: Option[Map[String, JsValue]] = None,
	  /** Optional. The labels for the predict request. &#42; Label keys can contain lowercase letters, digits and hyphens, must start with a letter, and must end with a letter or digit. &#42; Non-zero label values can contain lowercase letters, digits and hyphens, must start with a letter, and must end with a letter or digit. &#42; No more than 64 labels can be associated with a given request. See https://goo.gl/xmQnxf for more information on and examples of labels. */
		labels: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1PredictResponse(
	  /** A list of recommended items. The order represents the ranking (from the most relevant item to the least). */
		results: Option[List[Schema.GoogleCloudRecommendationengineV1beta1PredictResponsePredictionResult]] = None,
	  /** A unique recommendation token. This should be included in the user event logs resulting from this recommendation, which enables accurate attribution of recommendation model performance. */
		recommendationToken: Option[String] = None,
	  /** IDs of items in the request that were missing from the catalog. */
		itemsMissingInCatalog: Option[List[String]] = None,
	  /** True if the dryRun property was set in the request. */
		dryRun: Option[Boolean] = None,
	  /** Additional domain specific prediction response metadata. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If empty, the list is complete. If nonempty, the token to pass to the next request's PredictRequest.page_token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1PredictResponsePredictionResult(
	  /** ID of the recommended catalog item */
		id: Option[String] = None,
	  /** Additional item metadata / annotations. Possible values: &#42; `catalogItem`: JSON representation of the catalogItem. Will be set if `returnCatalogItem` is set to true in `PredictRequest.params`. &#42; `score`: Prediction score in double value. Will be set if `returnItemScore` is set to true in `PredictRequest.params`. */
		itemMetadata: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleApiHttpBody(
	  /** The HTTP Content-Type header value specifying the content type of the body. */
		contentType: Option[String] = None,
	  /** The HTTP request/response body as raw binary. */
		data: Option[String] = None,
	  /** Application specific response metadata. Must be set in the first response for streaming APIs. */
		extensions: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1ListUserEventsResponse(
	  /** The user events. */
		userEvents: Option[List[Schema.GoogleCloudRecommendationengineV1beta1UserEvent]] = None,
	  /** If empty, the list is complete. If nonempty, the token to pass to the next request's ListUserEvents.page_token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1PurgeUserEventsRequest(
	  /** Required. The filter string to specify the events to be deleted. Empty string filter is not allowed. The eligible fields for filtering are: &#42; `eventType`: UserEvent.eventType field of type string. &#42; `eventTime`: in ISO 8601 "zulu" format. &#42; `visitorId`: field of type string. Specifying this will delete all events associated with a visitor. &#42; `userId`: field of type string. Specifying this will delete all events associated with a user. Examples: &#42; Deleting all events in a time range: `eventTime > "2012-04-23T18:25:43.511Z" eventTime < "2012-04-23T18:30:43.511Z"` &#42; Deleting specific eventType in time range: `eventTime > "2012-04-23T18:25:43.511Z" eventType = "detail-page-view"` &#42; Deleting all events for a specific visitor: `visitorId = "visitor1024"` The filtering fields are assumed to have an implicit AND. */
		filter: Option[String] = None,
	  /** Optional. The default value is false. Override this flag to true to actually perform the purge. If the field is not set to true, a sampling of events to be deleted will be returned. */
		force: Option[Boolean] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1ImportUserEventsRequest(
	  /** Optional. Unique identifier provided by client, within the ancestor dataset scope. Ensures idempotency for expensive long running operations. Server-generated if unspecified. Up to 128 characters long. This is returned as google.longrunning.Operation.name in the response. Note that this field must not be set if the desired input config is catalog_inline_source. */
		requestId: Option[String] = None,
	  /** Required. The desired input location of the data. */
		inputConfig: Option[Schema.GoogleCloudRecommendationengineV1beta1InputConfig] = None,
	  /** Optional. The desired location of errors incurred during the Import. */
		errorsConfig: Option[Schema.GoogleCloudRecommendationengineV1beta1ImportErrorsConfig] = None
	)
	
	object GoogleCloudRecommendationengineV1beta1RejoinUserEventsRequest {
		enum UserEventRejoinScopeEnum extends Enum[UserEventRejoinScopeEnum] { case USER_EVENT_REJOIN_SCOPE_UNSPECIFIED, JOINED_EVENTS, UNJOINED_EVENTS }
	}
	case class GoogleCloudRecommendationengineV1beta1RejoinUserEventsRequest(
	  /** Required. The type of the catalog rejoin to define the scope and range of the user events to be rejoined with catalog items. */
		userEventRejoinScope: Option[Schema.GoogleCloudRecommendationengineV1beta1RejoinUserEventsRequest.UserEventRejoinScopeEnum] = None
	)
	
	case class GoogleCloudRecommendationengineV1alphaRejoinCatalogMetadata(
	
	)
	
	case class GoogleCloudRecommendationengineV1alphaRejoinCatalogResponse(
	  /** Number of user events that were joined with latest catalog items. */
		rejoinedUserEventsCount: Option[String] = None
	)
	
	case class GoogleCloudRecommendationengineV1alphaTuningMetadata(
	  /** The resource name of the recommendation model that this tune applies to. Format: projects/{project_number}/locations/{location_id}/catalogs/{catalog_id}/eventStores/{event_store_id}/recommendationModels/{recommendation_model_id} */
		recommendationModel: Option[String] = None
	)
	
	case class GoogleCloudRecommendationengineV1alphaTuningResponse(
	
	)
	
	case class GoogleCloudRecommendationengineV1beta1ImportCatalogItemsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Echoes the destination for the complete errors in the request if set. */
		errorsConfig: Option[Schema.GoogleCloudRecommendationengineV1beta1ImportErrorsConfig] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1ImportMetadata(
	  /** Name of the operation. */
		operationName: Option[String] = None,
	  /** Id of the request / operation. This is parroting back the requestId that was passed in the request. */
		requestId: Option[String] = None,
	  /** Operation create time. */
		createTime: Option[String] = None,
	  /** Count of entries that were processed successfully. */
		successCount: Option[String] = None,
	  /** Count of entries that encountered errors while processing. */
		failureCount: Option[String] = None,
	  /** Operation last update time. If the operation is done, this is also the finish time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1ImportUserEventsResponse(
	  /** A sample of errors encountered while processing the request. */
		errorSamples: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Echoes the destination for the complete errors if this field was set in the request. */
		errorsConfig: Option[Schema.GoogleCloudRecommendationengineV1beta1ImportErrorsConfig] = None,
	  /** Aggregated statistics of user event import status. */
		importSummary: Option[Schema.GoogleCloudRecommendationengineV1beta1UserEventImportSummary] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1UserEventImportSummary(
	  /** Count of user events imported with complete existing catalog information. */
		joinedEventsCount: Option[String] = None,
	  /** Count of user events imported, but with catalog information not found in the imported catalog. */
		unjoinedEventsCount: Option[String] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1PurgeUserEventsMetadata(
	  /** The ID of the request / operation. */
		operationName: Option[String] = None,
	  /** Operation create time. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1PurgeUserEventsResponse(
	  /** The total count of events purged as a result of the operation. */
		purgedEventsCount: Option[String] = None,
	  /** A sampling of events deleted (or will be deleted) depending on the `force` property in the request. Max of 500 items will be returned. */
		userEventsSample: Option[List[Schema.GoogleCloudRecommendationengineV1beta1UserEvent]] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1RejoinUserEventsResponse(
	  /** Number of user events that were joined with latest catalog items. */
		rejoinedUserEventsCount: Option[String] = None
	)
	
	case class GoogleCloudRecommendationengineV1beta1RejoinUserEventsMetadata(
	
	)
}
