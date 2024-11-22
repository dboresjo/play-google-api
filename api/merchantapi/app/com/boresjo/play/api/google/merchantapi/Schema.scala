package com.boresjo.play.api.google.merchantapi

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class MerchantReview(
	  /** Identifier. The name of the merchant review. Format: `"{merchantreview.name=accounts/{account}/merchantReviews/{merchantReview}}"` */
		name: Option[String] = None,
	  /** Required. The user provided merchant review ID to uniquely identify the merchant review. */
		merchantReviewId: Option[String] = None,
	  /** Optional. A list of merchant review attributes. */
		attributes: Option[Schema.MerchantReviewAttributes] = None,
	  /** Required. A list of custom (merchant-provided) attributes. It can also be used for submitting any attribute of the data specification in its generic form (for example, `{ "name": "size type", "value": "regular" }`). This is useful for submitting attributes not explicitly exposed by the API, such as experimental attributes. Maximum allowed number of characters for each custom attribute is 10240 (represents sum of characters for name and value). Maximum 2500 custom attributes can be set per product, with total size of 102.4kB. Underscores in custom attribute names are replaced by spaces upon insertion. */
		customAttributes: Option[List[Schema.CustomAttribute]] = None,
	  /** Output only. The primary data source of the merchant review. */
		dataSource: Option[String] = None,
	  /** Output only. The status of a merchant review, data validation issues, that is, information about a merchant review computed asynchronously. */
		merchantReviewStatus: Option[Schema.MerchantReviewStatus] = None
	)
	
	object MerchantReviewAttributes {
		enum CollectionMethodEnum extends Enum[CollectionMethodEnum] { case COLLECTION_METHOD_UNSPECIFIED, MERCHANT_UNSOLICITED, POINT_OF_SALE, AFTER_FULFILLMENT }
	}
	case class MerchantReviewAttributes(
	  /** Required. Must be unique and stable across all requests. In other words, if a request today and another 90 days ago refer to the same merchant, they must have the same id. */
		merchantId: Option[String] = None,
	  /** Optional. Human-readable display name for the merchant. */
		merchantDisplayName: Option[String] = None,
	  /** Optional. URL to the merchant's main website. Do not use a redirect URL for this value. In other words, the value should point directly to the merchant's site. */
		merchantLink: Option[String] = None,
	  /** Optional. URL to the landing page that hosts the reviews for this merchant. Do not use a redirect URL. */
		merchantRatingLink: Option[String] = None,
	  /** Optional. The minimum possible number for the rating. This should be the worst possible rating and should not be a value for no rating. */
		minRating: Option[String] = None,
	  /** Optional. The maximum possible number for the rating. The value of the max rating must be greater than the value of the min rating. */
		maxRating: Option[String] = None,
	  /** Optional. The reviewer's overall rating of the merchant. */
		rating: Option[BigDecimal] = None,
	  /** Optional. The title of the review. */
		title: Option[String] = None,
	  /** Required. This should be any freeform text provided by the user and should not be truncated. If multiple responses to different questions are provided, all responses should be included, with the minimal context for the responses to make sense. Context should not be provided if questions were left unanswered. */
		content: Option[String] = None,
	  /** Optional. A permanent, unique identifier for the author of the review in the publisher's system. */
		reviewerId: Option[String] = None,
	  /** Optional. Display name of the review author. */
		reviewerUsername: Option[String] = None,
	  /** Optional. Set to true if the reviewer should remain anonymous. */
		isAnonymous: Option[Boolean] = None,
	  /** Optional. The method used to collect the review. */
		collectionMethod: Option[Schema.MerchantReviewAttributes.CollectionMethodEnum] = None,
	  /** Required. The timestamp indicating when the review was written. */
		reviewTime: Option[String] = None,
	  /** Required. The language of the review defined by BCP-47 language code. */
		reviewLanguage: Option[String] = None,
	  /** Optional. The country where the reviewer made the order defined by ISO 3166-1 Alpha-2 Country Code. */
		reviewCountry: Option[String] = None
	)
	
	case class CustomAttribute(
	  /** The name of the attribute. */
		name: Option[String] = None,
	  /** The value of the attribute. If `value` is not empty, `group_values` must be empty. */
		value: Option[String] = None,
	  /** Subattributes within this attribute group. If `group_values` is not empty, `value` must be empty. */
		groupValues: Option[List[Schema.CustomAttribute]] = None
	)
	
	case class MerchantReviewStatus(
	  /** Output only. The intended destinations for the merchant review. */
		destinationStatuses: Option[List[Schema.MerchantReviewDestinationStatus]] = None,
	  /** Output only. A list of all issues associated with the merchant review. */
		itemLevelIssues: Option[List[Schema.MerchantReviewItemLevelIssue]] = None,
	  /** Output only. Date on which the item has been created, in [ISO 8601](http://en.wikipedia.org/wiki/ISO_8601) format. */
		createTime: Option[String] = None,
	  /** Output only. Date on which the item has been last updated, in [ISO 8601](http://en.wikipedia.org/wiki/ISO_8601) format. */
		lastUpdateTime: Option[String] = None
	)
	
	object MerchantReviewDestinationStatus {
		enum ReportingContextEnum extends Enum[ReportingContextEnum] { case REPORTING_CONTEXT_ENUM_UNSPECIFIED, SHOPPING_ADS, DISCOVERY_ADS, DEMAND_GEN_ADS, DEMAND_GEN_ADS_DISCOVER_SURFACE, VIDEO_ADS, DISPLAY_ADS, LOCAL_INVENTORY_ADS, VEHICLE_INVENTORY_ADS, FREE_LISTINGS, FREE_LOCAL_LISTINGS, FREE_LOCAL_VEHICLE_LISTINGS, YOUTUBE_SHOPPING, CLOUD_RETAIL, LOCAL_CLOUD_RETAIL, PRODUCT_REVIEWS, MERCHANT_REVIEWS, YOUTUBE_CHECKOUT }
	}
	case class MerchantReviewDestinationStatus(
	  /** Output only. The name of the reporting context. */
		reportingContext: Option[Schema.MerchantReviewDestinationStatus.ReportingContextEnum] = None
	)
	
	object MerchantReviewItemLevelIssue {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, NOT_IMPACTED, DISAPPROVED }
		enum ReportingContextEnum extends Enum[ReportingContextEnum] { case REPORTING_CONTEXT_ENUM_UNSPECIFIED, SHOPPING_ADS, DISCOVERY_ADS, DEMAND_GEN_ADS, DEMAND_GEN_ADS_DISCOVER_SURFACE, VIDEO_ADS, DISPLAY_ADS, LOCAL_INVENTORY_ADS, VEHICLE_INVENTORY_ADS, FREE_LISTINGS, FREE_LOCAL_LISTINGS, FREE_LOCAL_VEHICLE_LISTINGS, YOUTUBE_SHOPPING, CLOUD_RETAIL, LOCAL_CLOUD_RETAIL, PRODUCT_REVIEWS, MERCHANT_REVIEWS, YOUTUBE_CHECKOUT }
	}
	case class MerchantReviewItemLevelIssue(
	  /** Output only. The error code of the issue. */
		code: Option[String] = None,
	  /** Output only. How this issue affects serving of the merchant review. */
		severity: Option[Schema.MerchantReviewItemLevelIssue.SeverityEnum] = None,
	  /** Output only. Whether the issue can be resolved by the merchant. */
		resolution: Option[String] = None,
	  /** Output only. The attribute's name, if the issue is caused by a single attribute. */
		attribute: Option[String] = None,
	  /** Output only. The reporting context the issue applies to. */
		reportingContext: Option[Schema.MerchantReviewItemLevelIssue.ReportingContextEnum] = None,
	  /** Output only. A short issue description in English. */
		description: Option[String] = None,
	  /** Output only. A detailed issue description in English. */
		detail: Option[String] = None,
	  /** Output only. The URL of a web page to help with resolving this issue. */
		documentation: Option[String] = None
	)
	
	case class ListMerchantReviewsResponse(
	  /** The merchant review. */
		merchantReviews: Option[List[Schema.MerchantReview]] = None,
	  /** The token to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class ProductReview(
	  /** Identifier. The name of the product review. Format: `"{productreview.name=accounts/{account}/productReviews/{productReview}}"` */
		name: Option[String] = None,
	  /** Required. The permanent, unique identifier for the product review in the publisher’s system. */
		productReviewId: Option[String] = None,
	  /** Optional. A list of product review attributes. */
		attributes: Option[Schema.ProductReviewAttributes] = None,
	  /** Optional. A list of custom (merchant-provided) attributes. */
		customAttributes: Option[List[Schema.CustomAttribute]] = None,
	  /** Output only. The primary data source of the product review. */
		dataSource: Option[String] = None,
	  /** Output only. The status of a product review, data validation issues, that is, information about a product review computed asynchronously. */
		productReviewStatus: Option[Schema.ProductReviewStatus] = None
	)
	
	object ProductReviewAttributes {
		enum CollectionMethodEnum extends Enum[CollectionMethodEnum] { case COLLECTION_METHOD_UNSPECIFIED, UNSOLICITED, POST_FULFILLMENT }
	}
	case class ProductReviewAttributes(
	  /** Optional. The name of the aggregator of the product reviews. A publisher may use a reviews aggregator to manage reviews and provide the feeds. This element indicates the use of an aggregator and contains information about the aggregator. */
		aggregatorName: Option[String] = None,
	  /** Optional. The name of the subclient of the product reviews. The subclient is an identifier of the product review source. It should be equivalent to the directory provided in the file data source path. */
		subclientName: Option[String] = None,
	  /** Optional. The name of the publisher of the product reviews. The information about the publisher, which may be a retailer, manufacturer, reviews service company, or any entity that publishes product reviews. */
		publisherName: Option[String] = None,
	  /** Optional. A link to the company favicon of the publisher. The image dimensions should be favicon size: 16x16 pixels. The image format should be GIF, JPG or PNG. */
		publisherFavicon: Option[String] = None,
	  /** Optional. The author of the product review. A permanent, unique identifier for the author of the review in the publisher's system. */
		reviewerId: Option[String] = None,
	  /** Optional. Set to true if the reviewer should remain anonymous. */
		reviewerIsAnonymous: Option[Boolean] = None,
	  /** Optional. The name of the reviewer of the product review. */
		reviewerUsername: Option[String] = None,
	  /** Optional. The language of the review defined by BCP-47 language code. */
		reviewLanguage: Option[String] = None,
	  /** Optional. The country of the review defined by ISO 3166-1 Alpha-2 Country Code. */
		reviewCountry: Option[String] = None,
	  /** Required. The timestamp indicating when the review was written. */
		reviewTime: Option[String] = None,
	  /** Optional. The title of the review. */
		title: Option[String] = None,
	  /** Required. The content of the review. */
		content: Option[String] = None,
	  /** Optional. Contains the advantages based on the opinion of the reviewer. Omit boilerplate text like "pro:" unless it was written by the reviewer. */
		pros: Option[List[String]] = None,
	  /** Optional. Contains the disadvantages based on the opinion of the reviewer. Omit boilerplate text like "con:" unless it was written by the reviewer. */
		cons: Option[List[String]] = None,
	  /** Optional. The URI of the review landing page. */
		reviewLink: Option[Schema.ReviewLink] = None,
	  /** Optional. A URI to an image of the reviewed product created by the review author. The URI does not have to end with an image file extension. */
		reviewerImageLinks: Option[List[String]] = None,
	  /** Optional. Contains the ratings associated with the review. The minimum possible number for the rating. This should be the worst possible rating and should not be a value for no rating. */
		minRating: Option[String] = None,
	  /** Optional. The maximum possible number for the rating. The value of the max rating must be greater than the value of the min attribute. */
		maxRating: Option[String] = None,
	  /** Optional. The reviewer's overall rating of the product. */
		rating: Option[BigDecimal] = None,
	  /** Optional. Descriptive name of a product. */
		productNames: Option[List[String]] = None,
	  /** Optional. The URI of the product. This URI can have the same value as the `review_link` element, if the review URI and the product URI are the same. */
		productLinks: Option[List[String]] = None,
	  /** Optional. Contains ASINs (Amazon Standard Identification Numbers) associated with a product. */
		asins: Option[List[String]] = None,
	  /** Optional. Contains GTINs (global trade item numbers) associated with a product. Sub-types of GTINs (e.g. UPC, EAN, ISBN, JAN) are supported. */
		gtins: Option[List[String]] = None,
	  /** Optional. Contains MPNs (manufacturer part numbers) associated with a product. */
		mpns: Option[List[String]] = None,
	  /** Optional. Contains SKUs (stock keeping units) associated with a product. Often this matches the product Offer Id in the product feed. */
		skus: Option[List[String]] = None,
	  /** Optional. Contains brand names associated with a product. */
		brands: Option[List[String]] = None,
	  /** Optional. Indicates whether the review is marked as spam in the publisher's system. */
		isSpam: Option[Boolean] = None,
	  /** Optional. The method used to collect the review. */
		collectionMethod: Option[Schema.ProductReviewAttributes.CollectionMethodEnum] = None,
	  /** Optional. A permanent, unique identifier for the transaction associated with the review in the publisher's system. This ID can be used to indicate that multiple reviews are associated with the same transaction. */
		transactionId: Option[String] = None
	)
	
	object ReviewLink {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, SINGLETON, GROUP }
	}
	case class ReviewLink(
	  /** Optional. Type of the review URI. */
		`type`: Option[Schema.ReviewLink.TypeEnum] = None,
	  /** Optional. The URI of the review landing page. For example: `http://www.example.com/review_5.html`. */
		link: Option[String] = None
	)
	
	case class ProductReviewStatus(
	  /** Output only. The intended destinations for the product review. */
		destinationStatuses: Option[List[Schema.ProductReviewDestinationStatus]] = None,
	  /** Output only. A list of all issues associated with the product review. */
		itemLevelIssues: Option[List[Schema.ProductReviewItemLevelIssue]] = None,
	  /** Output only. Date on which the item has been created, in [ISO 8601](http://en.wikipedia.org/wiki/ISO_8601) format. */
		createTime: Option[String] = None,
	  /** Output only. Date on which the item has been last updated, in [ISO 8601](http://en.wikipedia.org/wiki/ISO_8601) format. */
		lastUpdateTime: Option[String] = None
	)
	
	object ProductReviewDestinationStatus {
		enum ReportingContextEnum extends Enum[ReportingContextEnum] { case REPORTING_CONTEXT_ENUM_UNSPECIFIED, SHOPPING_ADS, DISCOVERY_ADS, DEMAND_GEN_ADS, DEMAND_GEN_ADS_DISCOVER_SURFACE, VIDEO_ADS, DISPLAY_ADS, LOCAL_INVENTORY_ADS, VEHICLE_INVENTORY_ADS, FREE_LISTINGS, FREE_LOCAL_LISTINGS, FREE_LOCAL_VEHICLE_LISTINGS, YOUTUBE_SHOPPING, CLOUD_RETAIL, LOCAL_CLOUD_RETAIL, PRODUCT_REVIEWS, MERCHANT_REVIEWS, YOUTUBE_CHECKOUT }
	}
	case class ProductReviewDestinationStatus(
	  /** Output only. The name of the reporting context. */
		reportingContext: Option[Schema.ProductReviewDestinationStatus.ReportingContextEnum] = None
	)
	
	object ProductReviewItemLevelIssue {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, NOT_IMPACTED, DISAPPROVED }
		enum ReportingContextEnum extends Enum[ReportingContextEnum] { case REPORTING_CONTEXT_ENUM_UNSPECIFIED, SHOPPING_ADS, DISCOVERY_ADS, DEMAND_GEN_ADS, DEMAND_GEN_ADS_DISCOVER_SURFACE, VIDEO_ADS, DISPLAY_ADS, LOCAL_INVENTORY_ADS, VEHICLE_INVENTORY_ADS, FREE_LISTINGS, FREE_LOCAL_LISTINGS, FREE_LOCAL_VEHICLE_LISTINGS, YOUTUBE_SHOPPING, CLOUD_RETAIL, LOCAL_CLOUD_RETAIL, PRODUCT_REVIEWS, MERCHANT_REVIEWS, YOUTUBE_CHECKOUT }
	}
	case class ProductReviewItemLevelIssue(
	  /** Output only. The error code of the issue. */
		code: Option[String] = None,
	  /** Output only. How this issue affects serving of the product review. */
		severity: Option[Schema.ProductReviewItemLevelIssue.SeverityEnum] = None,
	  /** Output only. Whether the issue can be resolved by the merchant. */
		resolution: Option[String] = None,
	  /** Output only. The attribute's name, if the issue is caused by a single attribute. */
		attribute: Option[String] = None,
	  /** Output only. The reporting context the issue applies to. */
		reportingContext: Option[Schema.ProductReviewItemLevelIssue.ReportingContextEnum] = None,
	  /** Output only. A short issue description in English. */
		description: Option[String] = None,
	  /** Output only. A detailed issue description in English. */
		detail: Option[String] = None,
	  /** Output only. The URL of a web page to help with resolving this issue. */
		documentation: Option[String] = None
	)
	
	case class ListProductReviewsResponse(
	  /** The product review. */
		productReviews: Option[List[Schema.ProductReview]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object ProductStatusChangeMessage {
		enum ResourceTypeEnum extends Enum[ResourceTypeEnum] { case RESOURCE_UNSPECIFIED, PRODUCT }
		enum AttributeEnum extends Enum[AttributeEnum] { case ATTRIBUTE_UNSPECIFIED, STATUS }
	}
	case class ProductStatusChangeMessage(
	  /** The target account that owns the entity that changed. Format : `accounts/{merchant_id}` */
		account: Option[String] = None,
	  /** The account that manages the merchant's account. can be the same as merchant id if it is standalone account. Format : `accounts/{service_provider_id}` */
		managingAccount: Option[String] = None,
	  /** The resource that changed, in this case it will always be `Product`. */
		resourceType: Option[Schema.ProductStatusChangeMessage.ResourceTypeEnum] = None,
	  /** The attribute in the resource that changed, in this case it will be always `Status`. */
		attribute: Option[Schema.ProductStatusChangeMessage.AttributeEnum] = None,
	  /** A message to describe the change that happened to the product */
		changes: Option[List[Schema.ProductChange]] = None,
	  /** The product id. */
		resourceId: Option[String] = None,
	  /** The product name. Format: `{product.name=accounts/{account}/products/{product}}` */
		resource: Option[String] = None,
	  /** The product expiration time. */
		expirationTime: Option[String] = None
	)
	
	object ProductChange {
		enum ReportingContextEnum extends Enum[ReportingContextEnum] { case REPORTING_CONTEXT_ENUM_UNSPECIFIED, SHOPPING_ADS, DISCOVERY_ADS, DEMAND_GEN_ADS, DEMAND_GEN_ADS_DISCOVER_SURFACE, VIDEO_ADS, DISPLAY_ADS, LOCAL_INVENTORY_ADS, VEHICLE_INVENTORY_ADS, FREE_LISTINGS, FREE_LOCAL_LISTINGS, FREE_LOCAL_VEHICLE_LISTINGS, YOUTUBE_SHOPPING, CLOUD_RETAIL, LOCAL_CLOUD_RETAIL, PRODUCT_REVIEWS, MERCHANT_REVIEWS, YOUTUBE_CHECKOUT }
	}
	case class ProductChange(
	  /** The old value of the changed resource or attribute. */
		oldValue: Option[String] = None,
	  /** The new value of the changed resource or attribute. */
		newValue: Option[String] = None,
	  /** Countries that have the change (if applicable) */
		regionCode: Option[String] = None,
	  /** Reporting contexts that have the change (if applicable) */
		reportingContext: Option[Schema.ProductChange.ReportingContextEnum] = None
	)
}
