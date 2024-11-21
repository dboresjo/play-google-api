package com.boresjo.play.api.google.adexchangebuyer2

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object Creative {
		enum AttributesEnum extends Enum[AttributesEnum] { case ATTRIBUTE_UNSPECIFIED, IMAGE_RICH_MEDIA, ADOBE_FLASH_FLV, IS_TAGGED, IS_COOKIE_TARGETED, IS_USER_INTEREST_TARGETED, EXPANDING_DIRECTION_NONE, EXPANDING_DIRECTION_UP, EXPANDING_DIRECTION_DOWN, EXPANDING_DIRECTION_LEFT, EXPANDING_DIRECTION_RIGHT, EXPANDING_DIRECTION_UP_LEFT, EXPANDING_DIRECTION_UP_RIGHT, EXPANDING_DIRECTION_DOWN_LEFT, EXPANDING_DIRECTION_DOWN_RIGHT, CREATIVE_TYPE_HTML, CREATIVE_TYPE_VAST_VIDEO, EXPANDING_DIRECTION_UP_OR_DOWN, EXPANDING_DIRECTION_LEFT_OR_RIGHT, EXPANDING_DIRECTION_ANY_DIAGONAL, EXPANDING_ACTION_ROLLOVER_TO_EXPAND, INSTREAM_VAST_VIDEO_TYPE_VPAID_FLASH, RICH_MEDIA_CAPABILITY_TYPE_MRAID, RICH_MEDIA_CAPABILITY_TYPE_FLASH, RICH_MEDIA_CAPABILITY_TYPE_HTML5, SKIPPABLE_INSTREAM_VIDEO, RICH_MEDIA_CAPABILITY_TYPE_SSL, RICH_MEDIA_CAPABILITY_TYPE_NON_SSL, RICH_MEDIA_CAPABILITY_TYPE_INTERSTITIAL, NON_SKIPPABLE_INSTREAM_VIDEO, NATIVE_ELIGIBILITY_ELIGIBLE, NON_VPAID, NATIVE_ELIGIBILITY_NOT_ELIGIBLE, ANY_INTERSTITIAL, NON_INTERSTITIAL, IN_BANNER_VIDEO, RENDERING_SIZELESS_ADX, OMSDK_1_0, RENDERING_PLAYABLE }
		enum RestrictedCategoriesEnum extends Enum[RestrictedCategoriesEnum] { case NO_RESTRICTED_CATEGORIES, ALCOHOL }
		enum DealsStatusEnum extends Enum[DealsStatusEnum] { case STATUS_UNSPECIFIED, NOT_CHECKED, CONDITIONALLY_APPROVED, APPROVED, DISAPPROVED, PENDING_REVIEW, STATUS_TYPE_UNSPECIFIED }
		enum OpenAuctionStatusEnum extends Enum[OpenAuctionStatusEnum] { case STATUS_UNSPECIFIED, NOT_CHECKED, CONDITIONALLY_APPROVED, APPROVED, DISAPPROVED, PENDING_REVIEW, STATUS_TYPE_UNSPECIFIED }
	}
	case class Creative(
	  /** The account that this creative belongs to. Can be used to filter the response of the creatives.list method. */
		accountId: Option[String] = None,
	  /** The buyer-defined creative ID of this creative. Can be used to filter the response of the creatives.list method. */
		creativeId: Option[String] = None,
	  /** An HTML creative. */
		html: Option[Schema.HtmlContent] = None,
	  /** A video creative. */
		video: Option[Schema.VideoContent] = None,
	  /** A native creative. */
		native: Option[Schema.NativeContent] = None,
	  /** The agency ID for this creative. */
		agencyId: Option[String] = None,
	  /** The set of destination URLs for the creative. */
		clickThroughUrls: Option[List[String]] = None,
	  /** The set of declared destination URLs for the creative. */
		declaredClickThroughUrls: Option[List[String]] = None,
	  /** The set of URLs to be called to record an impression. */
		impressionTrackingUrls: Option[List[String]] = None,
	  /** The link to AdChoices destination page. */
		adChoicesDestinationUrl: Option[String] = None,
	  /** All vendor IDs for the ads that may be shown from this creative. See https://storage.googleapis.com/adx-rtb-dictionaries/vendors.txt for possible values. */
		vendorIds: Option[List[Int]] = None,
	  /** All attributes for the ads that may be shown from this creative. Can be used to filter the response of the creatives.list method. */
		attributes: Option[List[Schema.Creative.AttributesEnum]] = None,
	  /** All restricted categories for the ads that may be shown from this creative. */
		restrictedCategories: Option[List[Schema.Creative.RestrictedCategoriesEnum]] = None,
	  /** The name of the company being advertised in the creative. */
		advertiserName: Option[String] = None,
	  /** Output only. The top-level deals status of this creative. If disapproved, an entry for 'auctionType=DIRECT_DEALS' (or 'ALL') in serving_restrictions will also exist. Note that this may be nuanced with other contextual restrictions, in which case, it may be preferable to read from serving_restrictions directly. Can be used to filter the response of the creatives.list method. */
		dealsStatus: Option[Schema.Creative.DealsStatusEnum] = None,
	  /** Output only. The top-level open auction status of this creative. If disapproved, an entry for 'auctionType = OPEN_AUCTION' (or 'ALL') in serving_restrictions will also exist. Note that this may be nuanced with other contextual restrictions, in which case, it may be preferable to read from serving_restrictions directly. Can be used to filter the response of the creatives.list method. */
		openAuctionStatus: Option[Schema.Creative.OpenAuctionStatusEnum] = None,
	  /** Output only. The granular status of this ad in specific contexts. A context here relates to where something ultimately serves (for example, a physical location, a platform, an HTTPS versus HTTP request, or the type of auction). */
		servingRestrictions: Option[List[Schema.ServingRestriction]] = None,
	  /** Output only. Detected advertiser IDs, if any. */
		detectedAdvertiserIds: Option[List[String]] = None,
	  /** Output only. Detected product categories, if any. See the ad-product-categories.txt file in the technical documentation for a list of IDs. */
		detectedProductCategories: Option[List[Int]] = None,
	  /** Output only. The detected languages for this creative. The order is arbitrary. The codes are 2 or 5 characters and are documented at https://developers.google.com/adwords/api/docs/appendix/languagecodes. */
		detectedLanguages: Option[List[String]] = None,
	  /** Output only. The detected domains for this creative. */
		detectedDomains: Option[List[String]] = None,
	  /** Output only. Detected sensitive categories, if any. See the ad-sensitive-categories.txt file in the technical documentation for a list of IDs. You should use these IDs along with the excluded-sensitive-category field in the bid request to filter your bids. */
		detectedSensitiveCategories: Option[List[Int]] = None,
	  /** Output only. Shows any corrections that were applied to this creative. */
		corrections: Option[List[Schema.Correction]] = None,
	  /** Output only. The detected ad technology providers. */
		adTechnologyProviders: Option[Schema.AdTechnologyProviders] = None,
	  /** Output only. The version of this creative. */
		version: Option[Int] = None,
	  /** Output only. The last update timestamp of the creative through the API. */
		apiUpdateTime: Option[String] = None
	)
	
	case class HtmlContent(
	  /** The HTML snippet that displays the ad when inserted in the web page. */
		snippet: Option[String] = None,
	  /** The width of the HTML snippet in pixels. */
		width: Option[Int] = None,
	  /** The height of the HTML snippet in pixels. */
		height: Option[Int] = None
	)
	
	case class VideoContent(
	  /** The URL to fetch a video ad. */
		videoUrl: Option[String] = None,
	  /** The contents of a VAST document for a video ad. This document should conform to the VAST 2.0 or 3.0 standard. */
		videoVastXml: Option[String] = None
	)
	
	case class NativeContent(
	  /** A short title for the ad. */
		headline: Option[String] = None,
	  /** A long description of the ad. */
		body: Option[String] = None,
	  /** A label for the button that the user is supposed to click. */
		callToAction: Option[String] = None,
	  /** The name of the advertiser or sponsor, to be displayed in the ad creative. */
		advertiserName: Option[String] = None,
	  /** A large image. */
		image: Option[Schema.Image] = None,
	  /** The URL to fetch a native video ad. */
		videoUrl: Option[String] = None,
	  /** A smaller image, for the advertiser's logo. */
		logo: Option[Schema.Image] = None,
	  /** The app icon, for app download ads. */
		appIcon: Option[Schema.Image] = None,
	  /** The app rating in the app store. Must be in the range [0-5]. */
		starRating: Option[BigDecimal] = None,
	  /** The URL that the browser/SDK will load when the user clicks the ad. */
		clickLinkUrl: Option[String] = None,
	  /** The URL to use for click tracking. */
		clickTrackingUrl: Option[String] = None,
	  /** The price of the promoted app including currency info. */
		priceDisplayText: Option[String] = None,
	  /** The URL to the app store to purchase/download the promoted app. */
		storeUrl: Option[String] = None
	)
	
	case class Image(
	  /** The URL of the image. */
		url: Option[String] = None,
	  /** Image width in pixels. */
		width: Option[Int] = None,
	  /** Image height in pixels. */
		height: Option[Int] = None
	)
	
	object ServingRestriction {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNSPECIFIED, DISAPPROVAL, PENDING_REVIEW }
	}
	case class ServingRestriction(
	  /** The status of the creative in this context (for example, it has been explicitly disapproved or is pending review). */
		status: Option[Schema.ServingRestriction.StatusEnum] = None,
	  /** The contexts for the restriction. */
		contexts: Option[List[Schema.ServingContext]] = None,
	  /** Any disapprovals bound to this restriction. Only present if status=DISAPPROVED. Can be used to filter the response of the creatives.list method. Deprecated; use disapproval field instead. */
		disapprovalReasons: Option[List[Schema.Disapproval]] = None,
	  /** Disapproval bound to this restriction. Only present if status=DISAPPROVED. Can be used to filter the response of the creatives.list method. */
		disapproval: Option[Schema.Disapproval] = None
	)
	
	object ServingContext {
		enum AllEnum extends Enum[AllEnum] { case SIMPLE_CONTEXT }
	}
	case class ServingContext(
	  /** Matches all contexts. */
		all: Option[Schema.ServingContext.AllEnum] = None,
	  /** Matches impressions coming from users &#42;or&#42; publishers in a specific location. */
		location: Option[Schema.LocationContext] = None,
	  /** Matches impressions coming from a particular platform. */
		platform: Option[Schema.PlatformContext] = None,
	  /** Matches impressions for a particular auction type. */
		auctionType: Option[Schema.AuctionContext] = None,
	  /** Matches impressions for a particular security type. */
		securityType: Option[Schema.SecurityContext] = None,
	  /** Matches impressions for a particular app type. */
		appType: Option[Schema.AppContext] = None
	)
	
	case class LocationContext(
	  /** IDs representing the geo location for this context. Refer to the [geo-table.csv](https://storage.googleapis.com/adx-rtb-dictionaries/geo-table.csv) file for different geo criteria IDs. */
		geoCriteriaIds: Option[List[Int]] = None
	)
	
	object PlatformContext {
		enum PlatformsEnum extends Enum[PlatformsEnum] { case DESKTOP, ANDROID, IOS }
	}
	case class PlatformContext(
	  /** The platforms this restriction applies to. */
		platforms: Option[List[Schema.PlatformContext.PlatformsEnum]] = None
	)
	
	object AuctionContext {
		enum AuctionTypesEnum extends Enum[AuctionTypesEnum] { case OPEN_AUCTION, DIRECT_DEALS }
	}
	case class AuctionContext(
	  /** The auction types this restriction applies to. */
		auctionTypes: Option[List[Schema.AuctionContext.AuctionTypesEnum]] = None
	)
	
	object SecurityContext {
		enum SecuritiesEnum extends Enum[SecuritiesEnum] { case INSECURE, SSL }
	}
	case class SecurityContext(
	  /** The security types in this context. */
		securities: Option[List[Schema.SecurityContext.SecuritiesEnum]] = None
	)
	
	object AppContext {
		enum AppTypesEnum extends Enum[AppTypesEnum] { case NATIVE, WEB }
	}
	case class AppContext(
	  /** The app types this restriction applies to. */
		appTypes: Option[List[Schema.AppContext.AppTypesEnum]] = None
	)
	
	object Disapproval {
		enum ReasonEnum extends Enum[ReasonEnum] { case LENGTH_OF_IMAGE_ANIMATION, BROKEN_URL, MEDIA_NOT_FUNCTIONAL, INVALID_FOURTH_PARTY_CALL, INCORRECT_REMARKETING_DECLARATION, LANDING_PAGE_ERROR, AD_SIZE_DOES_NOT_MATCH_AD_SLOT, NO_BORDER, FOURTH_PARTY_BROWSER_COOKIES, LSO_OBJECTS, BLANK_CREATIVE, DESTINATION_URLS_UNDECLARED, PROBLEM_WITH_CLICK_MACRO, INCORRECT_AD_TECHNOLOGY_DECLARATION, INCORRECT_DESTINATION_URL_DECLARATION, EXPANDABLE_INCORRECT_DIRECTION, EXPANDABLE_DIRECTION_NOT_SUPPORTED, EXPANDABLE_INVALID_VENDOR, EXPANDABLE_FUNCTIONALITY, VIDEO_INVALID_VENDOR, VIDEO_UNSUPPORTED_LENGTH, VIDEO_UNSUPPORTED_FORMAT, VIDEO_FUNCTIONALITY, LANDING_PAGE_DISABLED, MALWARE_SUSPECTED, ADULT_IMAGE_OR_VIDEO, INACCURATE_AD_TEXT, COUNTERFEIT_DESIGNER_GOODS, POP_UP, INVALID_RTB_PROTOCOL_USAGE, RAW_IP_ADDRESS_IN_SNIPPET, UNACCEPTABLE_CONTENT_SOFTWARE, UNAUTHORIZED_COOKIE_ON_GOOGLE_DOMAIN, UNDECLARED_FLASH_OBJECTS, INVALID_SSL_DECLARATION, DIRECT_DOWNLOAD_IN_AD, MAXIMUM_DOWNLOAD_SIZE_EXCEEDED, DESTINATION_URL_SITE_NOT_CRAWLABLE, BAD_URL_LEGAL_DISAPPROVAL, PHARMA_GAMBLING_ALCOHOL_NOT_ALLOWED, DYNAMIC_DNS_AT_DESTINATION_URL, POOR_IMAGE_OR_VIDEO_QUALITY, UNACCEPTABLE_IMAGE_CONTENT, INCORRECT_IMAGE_LAYOUT, IRRELEVANT_IMAGE_OR_VIDEO, DESTINATION_SITE_DOES_NOT_ALLOW_GOING_BACK, MISLEADING_CLAIMS_IN_AD, RESTRICTED_PRODUCTS, UNACCEPTABLE_CONTENT, AUTOMATED_AD_CLICKING, INVALID_URL_PROTOCOL, UNDECLARED_RESTRICTED_CONTENT, INVALID_REMARKETING_LIST_USAGE, DESTINATION_SITE_NOT_CRAWLABLE_ROBOTS_TXT, CLICK_TO_DOWNLOAD_NOT_AN_APP, INACCURATE_REVIEW_EXTENSION, SEXUALLY_EXPLICIT_CONTENT, GAINING_AN_UNFAIR_ADVANTAGE, GAMING_THE_GOOGLE_NETWORK, DANGEROUS_PRODUCTS_KNIVES, DANGEROUS_PRODUCTS_EXPLOSIVES, DANGEROUS_PRODUCTS_GUNS, DANGEROUS_PRODUCTS_DRUGS, DANGEROUS_PRODUCTS_TOBACCO, DANGEROUS_PRODUCTS_WEAPONS, UNCLEAR_OR_IRRELEVANT_AD, PROFESSIONAL_STANDARDS, DYSFUNCTIONAL_PROMOTION, INVALID_INTEREST_BASED_AD, MISUSE_OF_PERSONAL_INFORMATION, OMISSION_OF_RELEVANT_INFORMATION, UNAVAILABLE_PROMOTIONS, MISLEADING_PROMOTIONS, INAPPROPRIATE_CONTENT, SENSITIVE_EVENTS, SHOCKING_CONTENT, ENABLING_DISHONEST_BEHAVIOR, TECHNICAL_REQUIREMENTS, RESTRICTED_POLITICAL_CONTENT, UNSUPPORTED_CONTENT, INVALID_BIDDING_METHOD, VIDEO_TOO_LONG, VIOLATES_JAPANESE_PHARMACY_LAW, UNACCREDITED_PET_PHARMACY, ABORTION, CONTRACEPTIVES, NEED_CERTIFICATES_TO_ADVERTISE_IN_CHINA, KCDSP_REGISTRATION, NOT_FAMILY_SAFE, CLINICAL_TRIAL_RECRUITMENT, MAXIMUM_NUMBER_OF_HTTP_CALLS_EXCEEDED, MAXIMUM_NUMBER_OF_COOKIES_EXCEEDED, PERSONAL_LOANS, UNSUPPORTED_FLASH_CONTENT, MISUSE_BY_OMID_SCRIPT, NON_WHITELISTED_OMID_VENDOR, DESTINATION_EXPERIENCE, UNSUPPORTED_LANGUAGE, NON_SSL_COMPLIANT, TEMPORARY_PAUSE, BAIL_BONDS, EXPERIMENTAL_MEDICAL_TREATMENT }
	}
	case class Disapproval(
	  /** The categorized reason for disapproval. */
		reason: Option[Schema.Disapproval.ReasonEnum] = None,
	  /** Additional details about the reason for disapproval. */
		details: Option[List[String]] = None
	)
	
	object Correction {
		enum TypeEnum extends Enum[TypeEnum] { case CORRECTION_TYPE_UNSPECIFIED, VENDOR_IDS_ADDED, SSL_ATTRIBUTE_REMOVED, FLASH_FREE_ATTRIBUTE_REMOVED, FLASH_FREE_ATTRIBUTE_ADDED, REQUIRED_ATTRIBUTE_ADDED, REQUIRED_VENDOR_ADDED, SSL_ATTRIBUTE_ADDED, IN_BANNER_VIDEO_ATTRIBUTE_ADDED, MRAID_ATTRIBUTE_ADDED, FLASH_ATTRIBUTE_REMOVED, VIDEO_IN_SNIPPET_ATTRIBUTE_ADDED }
	}
	case class Correction(
	  /** The type of correction that was applied to the creative. */
		`type`: Option[Schema.Correction.TypeEnum] = None,
	  /** Additional details about what was corrected. */
		details: Option[List[String]] = None,
	  /** The contexts for the correction. */
		contexts: Option[List[Schema.ServingContext]] = None
	)
	
	case class AdTechnologyProviders(
	  /** The detected ad technology provider IDs for this creative. See https://storage.googleapis.com/adx-rtb-dictionaries/providers.csv for mapping of provider ID to provided name, a privacy policy URL, and a list of domains which can be attributed to the provider. If the creative contains provider IDs that are outside of those listed in the `BidRequest.adslot.consented_providers_settings.consented_providers` field on the (Google bid protocol)[https://developers.google.com/authorized-buyers/rtb/downloads/realtime-bidding-proto] and the `BidRequest.user.ext.consented_providers_settings.consented_providers` field on the (OpenRTB protocol)[https://developers.google.com/authorized-buyers/rtb/downloads/openrtb-adx-proto], and a bid is submitted with that creative for an impression that will serve to an EEA user, the bid will be filtered before the auction. */
		detectedProviderIds: Option[List[String]] = None,
	  /** Whether the creative contains an unidentified ad technology provider. If true for a given creative, any bid submitted with that creative for an impression that will serve to an EEA user will be filtered before the auction. */
		hasUnidentifiedProvider: Option[Boolean] = None
	)
	
	case class ListCreativesResponse(
	  /** The list of creatives. */
		creatives: Option[List[Schema.Creative]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListCreativesRequest.page_token field in the subsequent call to `ListCreatives` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class AddDealAssociationRequest(
	  /** The association between a creative and a deal that should be added. */
		association: Option[Schema.CreativeDealAssociation] = None
	)
	
	case class CreativeDealAssociation(
	  /** The account the creative belongs to. */
		accountId: Option[String] = None,
	  /** The ID of the creative associated with the deal. */
		creativeId: Option[String] = None,
	  /** The externalDealId for the deal associated with the creative. */
		dealsId: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class RemoveDealAssociationRequest(
	  /** The association between a creative and a deal that should be removed. */
		association: Option[Schema.CreativeDealAssociation] = None
	)
	
	case class ListDealAssociationsResponse(
	  /** The list of associations. */
		associations: Option[List[Schema.CreativeDealAssociation]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListDealAssociationsRequest.page_token field in the subsequent call to 'ListDealAssociation' method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class WatchCreativeRequest(
	  /** The Pub/Sub topic to publish notifications to. This topic must already exist and must give permission to ad-exchange-buyside-reports@google.com to write to the topic. This should be the full resource name in "projects/{project_id}/topics/{topic_id}" format. */
		topic: Option[String] = None
	)
	
	case class StopWatchingCreativeRequest(
	
	)
	
	case class ListImpressionMetricsResponse(
	  /** List of rows, each containing a set of impression metrics. */
		impressionMetricsRows: Option[List[Schema.ImpressionMetricsRow]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListImpressionMetricsRequest.pageToken field in the subsequent call to the impressionMetrics.list method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class ImpressionMetricsRow(
	  /** The number of impressions available to the buyer on Ad Exchange. In some cases this value may be unavailable. */
		availableImpressions: Option[Schema.MetricValue] = None,
	  /** The number of impressions that match the buyer's inventory pretargeting. */
		inventoryMatches: Option[Schema.MetricValue] = None,
	  /** The number of impressions for which Ad Exchange sent the buyer a bid request. */
		bidRequests: Option[Schema.MetricValue] = None,
	  /** The number of impressions for which the buyer successfully sent a response to Ad Exchange. */
		successfulResponses: Option[Schema.MetricValue] = None,
	  /** The number of impressions for which Ad Exchange received a response from the buyer that contained at least one applicable bid. */
		responsesWithBids: Option[Schema.MetricValue] = None,
	  /** The values of all dimensions associated with metric values in this row. */
		rowDimensions: Option[Schema.RowDimensions] = None
	)
	
	case class MetricValue(
	  /** The expected value of the metric. */
		value: Option[String] = None,
	  /** The variance (for example, square of the standard deviation) of the metric value. If value is exact, variance is 0. Can be used to calculate margin of error as a percentage of value, using the following formula, where Z is the standard constant that depends on the preferred size of the confidence interval (for example, for 90% confidence interval, use Z = 1.645): marginOfError = 100 &#42; Z &#42; sqrt(variance) / value */
		variance: Option[String] = None
	)
	
	case class RowDimensions(
	  /** The time interval that this row represents. */
		timeInterval: Option[Schema.TimeInterval] = None,
	  /** The publisher identifier for this row, if a breakdown by [BreakdownDimension.PUBLISHER_IDENTIFIER](https://developers.google.com/authorized-buyers/apis/reference/rest/v2beta1/bidders.accounts.filterSets#FilterSet.BreakdownDimension) was requested. */
		publisherIdentifier: Option[String] = None
	)
	
	case class TimeInterval(
	  /** The timestamp marking the start of the range (inclusive) for which data is included. */
		startTime: Option[String] = None,
	  /** The timestamp marking the end of the range (exclusive) for which data is included. */
		endTime: Option[String] = None
	)
	
	case class ListBidMetricsResponse(
	  /** List of rows, each containing a set of bid metrics. */
		bidMetricsRows: Option[List[Schema.BidMetricsRow]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListBidMetricsRequest.pageToken field in the subsequent call to the bidMetrics.list method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class BidMetricsRow(
	  /** The number of bids that Ad Exchange received from the buyer. */
		bids: Option[Schema.MetricValue] = None,
	  /** The number of bids that were permitted to compete in the auction. */
		bidsInAuction: Option[Schema.MetricValue] = None,
	  /** The number of bids that won the auction. */
		impressionsWon: Option[Schema.MetricValue] = None,
	  /** The number of bids that won the auction and also won the mediation waterfall (if any). */
		reachedQueries: Option[Schema.MetricValue] = None,
	  /** The number of bids for which the buyer was billed. Also called valid impressions as invalid impressions are not billed. */
		billedImpressions: Option[Schema.MetricValue] = None,
	  /** The number of bids for which the corresponding impression was measurable for viewability (as defined by Active View). */
		measurableImpressions: Option[Schema.MetricValue] = None,
	  /** The number of bids for which the corresponding impression was viewable (as defined by Active View). */
		viewableImpressions: Option[Schema.MetricValue] = None,
	  /** The values of all dimensions associated with metric values in this row. */
		rowDimensions: Option[Schema.RowDimensions] = None
	)
	
	case class ListFilteredBidRequestsResponse(
	  /** List of rows, with counts of filtered bid requests aggregated by callout status. */
		calloutStatusRows: Option[List[Schema.CalloutStatusRow]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListFilteredBidRequestsRequest.pageToken field in the subsequent call to the filteredBidRequests.list method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class CalloutStatusRow(
	  /** The number of impressions for which there was a bid request or bid response with the specified callout status. */
		impressionCount: Option[Schema.MetricValue] = None,
	  /** The ID of the callout status. See [callout-status-codes](https://developers.google.com/authorized-buyers/rtb/downloads/callout-status-codes). */
		calloutStatusId: Option[Int] = None,
	  /** The values of all dimensions associated with metric values in this row. */
		rowDimensions: Option[Schema.RowDimensions] = None
	)
	
	case class ListBidResponseErrorsResponse(
	  /** List of rows, with counts of bid responses aggregated by callout status. */
		calloutStatusRows: Option[List[Schema.CalloutStatusRow]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListBidResponseErrorsRequest.pageToken field in the subsequent call to the bidResponseErrors.list method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class ListBidResponsesWithoutBidsResponse(
	  /** List of rows, with counts of bid responses without bids aggregated by status. */
		bidResponseWithoutBidsStatusRows: Option[List[Schema.BidResponseWithoutBidsStatusRow]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListBidResponsesWithoutBidsRequest.pageToken field in the subsequent call to the bidResponsesWithoutBids.list method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object BidResponseWithoutBidsStatusRow {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNSPECIFIED, RESPONSES_WITHOUT_BIDS, RESPONSES_WITHOUT_BIDS_FOR_ACCOUNT, RESPONSES_WITHOUT_BIDS_FOR_DEAL }
	}
	case class BidResponseWithoutBidsStatusRow(
	  /** The number of impressions for which there was a bid response with the specified status. */
		impressionCount: Option[Schema.MetricValue] = None,
	  /** The status specifying why the bid responses were considered to have no applicable bids. */
		status: Option[Schema.BidResponseWithoutBidsStatusRow.StatusEnum] = None,
	  /** The values of all dimensions associated with metric values in this row. */
		rowDimensions: Option[Schema.RowDimensions] = None
	)
	
	case class ListFilteredBidsResponse(
	  /** List of rows, with counts of filtered bids aggregated by filtering reason (for example, creative status). */
		creativeStatusRows: Option[List[Schema.CreativeStatusRow]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListFilteredBidsRequest.pageToken field in the subsequent call to the filteredBids.list method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class CreativeStatusRow(
	  /** The number of bids with the specified status. */
		bidCount: Option[Schema.MetricValue] = None,
	  /** The ID of the creative status. See [creative-status-codes](https://developers.google.com/authorized-buyers/rtb/downloads/creative-status-codes). */
		creativeStatusId: Option[Int] = None,
	  /** The values of all dimensions associated with metric values in this row. */
		rowDimensions: Option[Schema.RowDimensions] = None
	)
	
	case class ListLosingBidsResponse(
	  /** List of rows, with counts of losing bids aggregated by loss reason (for example, creative status). */
		creativeStatusRows: Option[List[Schema.CreativeStatusRow]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListLosingBidsRequest.pageToken field in the subsequent call to the losingBids.list method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class ListNonBillableWinningBidsResponse(
	  /** List of rows, with counts of bids not billed aggregated by reason. */
		nonBillableWinningBidStatusRows: Option[List[Schema.NonBillableWinningBidStatusRow]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListNonBillableWinningBidsRequest.pageToken field in the subsequent call to the nonBillableWinningBids.list method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object NonBillableWinningBidStatusRow {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNSPECIFIED, AD_NOT_RENDERED, INVALID_IMPRESSION, FATAL_VAST_ERROR, LOST_IN_MEDIATION }
	}
	case class NonBillableWinningBidStatusRow(
	  /** The number of bids with the specified status. */
		bidCount: Option[Schema.MetricValue] = None,
	  /** The status specifying why the winning bids were not billed. */
		status: Option[Schema.NonBillableWinningBidStatusRow.StatusEnum] = None,
	  /** The values of all dimensions associated with metric values in this row. */
		rowDimensions: Option[Schema.RowDimensions] = None
	)
	
	object ListCreativeStatusBreakdownByDetailResponse {
		enum DetailTypeEnum extends Enum[DetailTypeEnum] { case DETAIL_TYPE_UNSPECIFIED, CREATIVE_ATTRIBUTE, VENDOR, SENSITIVE_CATEGORY, PRODUCT_CATEGORY, DISAPPROVAL_REASON, POLICY_TOPIC, ATP_VENDOR, VENDOR_DOMAIN, GVL_ID }
	}
	case class ListCreativeStatusBreakdownByDetailResponse(
	  /** List of rows, with counts of bids with a given creative status aggregated by detail. */
		filteredBidDetailRows: Option[List[Schema.FilteredBidDetailRow]] = None,
	  /** The type of detail that the detail IDs represent. */
		detailType: Option[Schema.ListCreativeStatusBreakdownByDetailResponse.DetailTypeEnum] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListCreativeStatusBreakdownByDetailRequest.pageToken field in the subsequent call to the filteredBids.details.list method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class FilteredBidDetailRow(
	  /** The number of bids with the specified detail. */
		bidCount: Option[Schema.MetricValue] = None,
	  /** Note: this field will be deprecated, use "detail" field instead. When "detail" field represents an integer value, this field is populated as the same integer value "detail" field represents, otherwise this field will be 0. The ID of the detail. The associated value can be looked up in the dictionary file corresponding to the DetailType in the response message. */
		detailId: Option[Int] = None,
	  /** The ID of the detail, can be numeric or text. The associated value can be looked up in the dictionary file corresponding to the DetailType in the response message. */
		detail: Option[String] = None,
	  /** The values of all dimensions associated with metric values in this row. */
		rowDimensions: Option[Schema.RowDimensions] = None
	)
	
	case class ListCreativeStatusBreakdownByCreativeResponse(
	  /** List of rows, with counts of bids with a given creative status aggregated by creative. */
		filteredBidCreativeRows: Option[List[Schema.FilteredBidCreativeRow]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListCreativeStatusBreakdownByCreativeRequest.pageToken field in the subsequent call to the filteredBids.creatives.list method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class FilteredBidCreativeRow(
	  /** The number of bids with the specified creative. */
		bidCount: Option[Schema.MetricValue] = None,
	  /** The ID of the creative. */
		creativeId: Option[String] = None,
	  /** The values of all dimensions associated with metric values in this row. */
		rowDimensions: Option[Schema.RowDimensions] = None
	)
	
	object Client {
		enum RoleEnum extends Enum[RoleEnum] { case CLIENT_ROLE_UNSPECIFIED, CLIENT_DEAL_VIEWER, CLIENT_DEAL_NEGOTIATOR, CLIENT_DEAL_APPROVER }
		enum StatusEnum extends Enum[StatusEnum] { case CLIENT_STATUS_UNSPECIFIED, DISABLED, ACTIVE }
		enum EntityTypeEnum extends Enum[EntityTypeEnum] { case ENTITY_TYPE_UNSPECIFIED, ADVERTISER, BRAND, AGENCY, ENTITY_TYPE_UNCLASSIFIED }
	}
	case class Client(
	  /** The globally-unique numerical ID of the client. The value of this field is ignored in create and update operations. */
		clientAccountId: Option[String] = None,
	  /** The role which is assigned to the client buyer. Each role implies a set of permissions granted to the client. Must be one of `CLIENT_DEAL_VIEWER`, `CLIENT_DEAL_NEGOTIATOR` or `CLIENT_DEAL_APPROVER`. */
		role: Option[Schema.Client.RoleEnum] = None,
	  /** The status of the client buyer. */
		status: Option[Schema.Client.StatusEnum] = None,
	  /** Name used to represent this client to publishers. You may have multiple clients that map to the same entity, but for each client the combination of `clientName` and entity must be unique. You can specify this field as empty. Maximum length of 255 characters is allowed. */
		clientName: Option[String] = None,
	  /** Whether the client buyer will be visible to sellers. */
		visibleToSeller: Option[Boolean] = None,
	  /** An optional field for specifying the type of the client entity: `ADVERTISER`, `BRAND`, or `AGENCY`. */
		entityType: Option[Schema.Client.EntityTypeEnum] = None,
	  /** Numerical identifier of the client entity. The entity can be an advertiser, a brand, or an agency. This identifier is unique among all the entities with the same type. The value of this field is ignored if the entity type is not provided. A list of all known advertisers with their identifiers is available in the [advertisers.txt](https://storage.googleapis.com/adx-rtb-dictionaries/advertisers.txt) file. A list of all known brands with their identifiers is available in the [brands.txt](https://storage.googleapis.com/adx-rtb-dictionaries/brands.txt) file. A list of all known agencies with their identifiers is available in the [agencies.txt](https://storage.googleapis.com/adx-rtb-dictionaries/agencies.txt) file. */
		entityId: Option[String] = None,
	  /** The name of the entity. This field is automatically fetched based on the type and ID. The value of this field is ignored in create and update operations. */
		entityName: Option[String] = None,
	  /** Optional arbitrary unique identifier of this client buyer from the standpoint of its Ad Exchange sponsor buyer. This field can be used to associate a client buyer with the identifier in the namespace of its sponsor buyer, lookup client buyers by that identifier and verify whether an Ad Exchange counterpart of a given client buyer already exists. If present, must be unique among all the client buyers for its Ad Exchange sponsor buyer. */
		partnerClientId: Option[String] = None
	)
	
	case class ListClientsResponse(
	  /** The returned list of clients. */
		clients: Option[List[Schema.Client]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListClientsRequest.pageToken field in the subsequent call to the accounts.clients.list method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class ListClientUsersResponse(
	  /** The returned list of client users. */
		users: Option[List[Schema.ClientUser]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListClientUsersRequest.pageToken field in the subsequent call to the clients.invitations.list method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object ClientUser {
		enum StatusEnum extends Enum[StatusEnum] { case USER_STATUS_UNSPECIFIED, PENDING, ACTIVE, DISABLED }
	}
	case class ClientUser(
	  /** The unique numerical ID of the client user that has accepted an invitation. The value of this field is ignored in an update operation. */
		userId: Option[String] = None,
	  /** Numerical account ID of the client buyer with which the user is associated; the buyer must be a client of the current sponsor buyer. The value of this field is ignored in an update operation. */
		clientAccountId: Option[String] = None,
	  /** The status of the client user. */
		status: Option[Schema.ClientUser.StatusEnum] = None,
	  /** User's email address. The value of this field is ignored in an update operation. */
		email: Option[String] = None
	)
	
	case class ClientUserInvitation(
	  /** The unique numerical ID of the invitation that is sent to the user. The value of this field is ignored in create operations. */
		invitationId: Option[String] = None,
	  /** The email address to which the invitation is sent. Email addresses should be unique among all client users under each sponsor buyer. */
		email: Option[String] = None,
	  /** Numerical account ID of the client buyer that the invited user is associated with. The value of this field is ignored in create operations. */
		clientAccountId: Option[String] = None
	)
	
	case class ListClientUserInvitationsResponse(
	  /** The returned list of client users. */
		invitations: Option[List[Schema.ClientUserInvitation]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListClientUserInvitationsRequest.pageToken field in the subsequent call to the clients.invitations.list method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object FilterSet {
		enum TimeSeriesGranularityEnum extends Enum[TimeSeriesGranularityEnum] { case TIME_SERIES_GRANULARITY_UNSPECIFIED, HOURLY, DAILY }
		enum FormatsEnum extends Enum[FormatsEnum] { case FORMAT_UNSPECIFIED, NATIVE_DISPLAY, NATIVE_VIDEO, NON_NATIVE_DISPLAY, NON_NATIVE_VIDEO }
		enum FormatEnum extends Enum[FormatEnum] { case FORMAT_UNSPECIFIED, NATIVE_DISPLAY, NATIVE_VIDEO, NON_NATIVE_DISPLAY, NON_NATIVE_VIDEO }
		enum EnvironmentEnum extends Enum[EnvironmentEnum] { case ENVIRONMENT_UNSPECIFIED, WEB, APP }
		enum PlatformsEnum extends Enum[PlatformsEnum] { case PLATFORM_UNSPECIFIED, DESKTOP, TABLET, MOBILE }
		enum BreakdownDimensionsEnum extends Enum[BreakdownDimensionsEnum] { case BREAKDOWN_DIMENSION_UNSPECIFIED, PUBLISHER_IDENTIFIER }
	}
	case class FilterSet(
	  /** A user-defined name of the filter set. Filter set names must be unique globally and match one of the patterns: - `bidders/&#42;/filterSets/&#42;` (for accessing bidder-level troubleshooting data) - `bidders/&#42;/accounts/&#42;/filterSets/&#42;` (for accessing account-level troubleshooting data) This field is required in create operations. */
		name: Option[String] = None,
	  /** A relative date range, defined by an offset from today and a duration. Interpreted relative to Pacific time zone. */
		relativeDateRange: Option[Schema.RelativeDateRange] = None,
	  /** An absolute date range, defined by a start date and an end date. Interpreted relative to Pacific time zone. */
		absoluteDateRange: Option[Schema.AbsoluteDateRange] = None,
	  /** An open-ended realtime time range, defined by the aggregation start timestamp. */
		realtimeTimeRange: Option[Schema.RealtimeTimeRange] = None,
	  /** The granularity of time intervals if a time series breakdown is preferred; optional. */
		timeSeriesGranularity: Option[Schema.FilterSet.TimeSeriesGranularityEnum] = None,
	  /** The ID of the creative on which to filter; optional. This field may be set only for a filter set that accesses account-level troubleshooting data, for example, one whose name matches the `bidders/&#42;/accounts/&#42;/filterSets/&#42;` pattern. */
		creativeId: Option[String] = None,
	  /** The ID of the deal on which to filter; optional. This field may be set only for a filter set that accesses account-level troubleshooting data, for example, one whose name matches the `bidders/&#42;/accounts/&#42;/filterSets/&#42;` pattern. */
		dealId: Option[String] = None,
	  /** Creative formats bidded on or allowed to bid on, can be empty. Although this field is a list, it can only be populated with a single item. A HTTP 400 bad request error will be returned in the response if you specify multiple items. */
		formats: Option[List[Schema.FilterSet.FormatsEnum]] = None,
	  /** Creative format bidded on or allowed to bid on, can be empty. */
		format: Option[Schema.FilterSet.FormatEnum] = None,
	  /** The environment on which to filter; optional. */
		environment: Option[Schema.FilterSet.EnvironmentEnum] = None,
	  /** The list of platforms on which to filter; may be empty. The filters represented by multiple platforms are ORed together (for example, if non-empty, results must match any one of the platforms). */
		platforms: Option[List[Schema.FilterSet.PlatformsEnum]] = None,
	  /** For Authorized Buyers only. The list of IDs of the seller (publisher) networks on which to filter; may be empty. The filters represented by multiple seller network IDs are ORed together (for example, if non-empty, results must match any one of the publisher networks). See [seller-network-ids](https://developers.google.com/authorized-buyers/rtb/downloads/seller-network-ids) file for the set of existing seller network IDs. */
		sellerNetworkIds: Option[List[Int]] = None,
	  /** For Open Bidding partners only. The list of publisher identifiers on which to filter; may be empty. The filters represented by multiple publisher identifiers are ORed together. */
		publisherIdentifiers: Option[List[String]] = None,
	  /** The set of dimensions along which to break down the response; may be empty. If multiple dimensions are requested, the breakdown is along the Cartesian product of the requested dimensions. */
		breakdownDimensions: Option[List[Schema.FilterSet.BreakdownDimensionsEnum]] = None
	)
	
	case class RelativeDateRange(
	  /** The end date of the filter set, specified as the number of days before today, for example, for a range where the last date is today: 0. */
		offsetDays: Option[Int] = None,
	  /** The number of days in the requested date range, for example, for a range spanning today: 1. For a range spanning the last 7 days: 7. */
		durationDays: Option[Int] = None
	)
	
	case class AbsoluteDateRange(
	  /** The start date of the range (inclusive). Must be within the 30 days leading up to current date, and must be equal to or before end_date. */
		startDate: Option[Schema.Date] = None,
	  /** The end date of the range (inclusive). Must be within the 30 days leading up to current date, and must be equal to or after start_date. */
		endDate: Option[Schema.Date] = None
	)
	
	case class Date(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class RealtimeTimeRange(
	  /** The start timestamp of the real-time RTB metrics aggregation. */
		startTimestamp: Option[String] = None
	)
	
	case class ListFilterSetsResponse(
	  /** The filter sets belonging to the buyer. */
		filterSets: Option[List[Schema.FilterSet]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListFilterSetsRequest.pageToken field in the subsequent call to the accounts.filterSets.list method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object Proposal {
		enum OriginatorRoleEnum extends Enum[OriginatorRoleEnum] { case BUYER_SELLER_ROLE_UNSPECIFIED, BUYER, SELLER }
		enum ProposalStateEnum extends Enum[ProposalStateEnum] { case PROPOSAL_STATE_UNSPECIFIED, PROPOSED, BUYER_ACCEPTED, SELLER_ACCEPTED, CANCELED, FINALIZED }
		enum LastUpdaterOrCommentorRoleEnum extends Enum[LastUpdaterOrCommentorRoleEnum] { case BUYER_SELLER_ROLE_UNSPECIFIED, BUYER, SELLER }
	}
	case class Proposal(
	  /** Output only. The unique ID of the proposal. */
		proposalId: Option[String] = None,
	  /** Output only. The time when the proposal was last revised. */
		updateTime: Option[String] = None,
	  /** Output only. The revision number for the proposal. Each update to the proposal or the deal causes the proposal revision number to auto-increment. The buyer keeps track of the last revision number they know of and pass it in when making an update. If the head revision number on the server has since incremented, then an ABORTED error is returned during the update operation to let the buyer know that a subsequent update was made. */
		proposalRevision: Option[String] = None,
	  /** The deals associated with this proposal. For Private Auction proposals (whose deals have NonGuaranteedAuctionTerms), there will only be one deal. */
		deals: Option[List[Schema.Deal]] = None,
	  /** Output only. Indicates whether the buyer/seller created the proposal. */
		originatorRole: Option[Schema.Proposal.OriginatorRoleEnum] = None,
	  /** Reference to the seller on the proposal. Note: This field may be set only when creating the resource. Modifying this field while updating the resource will result in an error. */
		seller: Option[Schema.Seller] = None,
	  /** Reference to the buyer on the proposal. Note: This field may be set only when creating the resource. Modifying this field while updating the resource will result in an error. */
		buyer: Option[Schema.Buyer] = None,
	  /** Output only. Reference to the buyer that will get billed for this proposal. */
		billedBuyer: Option[Schema.Buyer] = None,
	  /** The name for the proposal. */
		displayName: Option[String] = None,
	  /** Output only. The current state of the proposal. */
		proposalState: Option[Schema.Proposal.ProposalStateEnum] = None,
	  /** Output only. True if the proposal is being renegotiated. */
		isRenegotiating: Option[Boolean] = None,
	  /** Private data for buyer. (hidden from seller). */
		buyerPrivateData: Option[Schema.PrivateData] = None,
	  /** Output only. Contact information for the seller. */
		sellerContacts: Option[List[Schema.ContactInformation]] = None,
	  /** Contact information for the buyer. */
		buyerContacts: Option[List[Schema.ContactInformation]] = None,
	  /** Output only. Private auction ID if this proposal is a private auction proposal. */
		privateAuctionId: Option[String] = None,
	  /** Output only. True, if the buyside inventory setup is complete for this proposal. */
		isSetupComplete: Option[Boolean] = None,
	  /** Output only. The role of the last user that either updated the proposal or left a comment. */
		lastUpdaterOrCommentorRole: Option[Schema.Proposal.LastUpdaterOrCommentorRoleEnum] = None,
	  /** Output only. The notes associated with this proposal. */
		notes: Option[List[Schema.Note]] = None,
	  /** Output only. The terms and conditions set by the publisher for this proposal. */
		termsAndConditions: Option[String] = None
	)
	
	object Deal {
		enum SyndicationProductEnum extends Enum[SyndicationProductEnum] { case SYNDICATION_PRODUCT_UNSPECIFIED, CONTENT, MOBILE, VIDEO, GAMES }
		enum CreativePreApprovalPolicyEnum extends Enum[CreativePreApprovalPolicyEnum] { case CREATIVE_PRE_APPROVAL_POLICY_UNSPECIFIED, SELLER_PRE_APPROVAL_REQUIRED, SELLER_PRE_APPROVAL_NOT_REQUIRED }
		enum CreativeSafeFrameCompatibilityEnum extends Enum[CreativeSafeFrameCompatibilityEnum] { case CREATIVE_SAFE_FRAME_COMPATIBILITY_UNSPECIFIED, COMPATIBLE, INCOMPATIBLE }
		enum ProgrammaticCreativeSourceEnum extends Enum[ProgrammaticCreativeSourceEnum] { case PROGRAMMATIC_CREATIVE_SOURCE_UNSPECIFIED, ADVERTISER, PUBLISHER }
	}
	case class Deal(
	  /** Output only. A unique deal ID for the deal (server-assigned). */
		dealId: Option[String] = None,
	  /** Output only. ID of the proposal that this deal is part of. */
		proposalId: Option[String] = None,
	  /** Output only. The time of the deal creation. */
		createTime: Option[String] = None,
	  /** Output only. The time when the deal was last updated. */
		updateTime: Option[String] = None,
	  /** The product ID from which this deal was created. Note: This field may be set only when creating the resource. Modifying this field while updating the resource will result in an error. */
		createProductId: Option[String] = None,
	  /** Optional. Revision number of the product that the deal was created from. If present on create, and the server `product_revision` has advanced since the passed-in `create_product_revision`, an `ABORTED` error will be returned. Note: This field may be set only when creating the resource. Modifying this field while updating the resource will result in an error. */
		createProductRevision: Option[String] = None,
	  /** The name of the deal. */
		displayName: Option[String] = None,
	  /** Buyer private data (hidden from seller). */
		buyerPrivateData: Option[Schema.PrivateData] = None,
	  /** The negotiable terms of the deal. */
		dealTerms: Option[Schema.DealTerms] = None,
	  /** The web property code for the seller copied over from the product. */
		webPropertyCode: Option[String] = None,
	  /** Output only. Seller contact information for the deal. */
		sellerContacts: Option[List[Schema.ContactInformation]] = None,
	  /** Optional. Proposed flight start time of the deal. This will generally be stored in the granularity of one second since deal serving starts at seconds boundary. Any time specified with more granularity (for example, in milliseconds) will be truncated towards the start of time in seconds. */
		availableStartTime: Option[String] = None,
	  /** Proposed flight end time of the deal. This will generally be stored in a granularity of a second. A value is not required for Private Auction deals or Preferred Deals. */
		availableEndTime: Option[String] = None,
	  /** Description for the deal terms. */
		description: Option[String] = None,
	  /** The shared targeting visible to buyers and sellers. Each shared targeting entity is AND'd together. */
		targetingCriterion: Option[List[Schema.TargetingCriteria]] = None,
	  /** Output only. Specifies the subset of inventory targeted by the deal. */
		targeting: Option[Schema.MarketplaceTargeting] = None,
	  /** Output only. Restricitions about the creatives associated with the deal (for example, size) This is available for Programmatic Guaranteed/Preferred Deals in Ad Manager. */
		creativeRestrictions: Option[Schema.CreativeRestrictions] = None,
	  /** Output only. The external deal ID assigned to this deal once the deal is finalized. This is the deal ID that shows up in serving/reporting etc. */
		externalDealId: Option[String] = None,
	  /** The syndication product associated with the deal. Note: This field may be set only when creating the resource. Modifying this field while updating the resource will result in an error. */
		syndicationProduct: Option[Schema.Deal.SyndicationProductEnum] = None,
	  /** Output only. Specifies the creative pre-approval policy. */
		creativePreApprovalPolicy: Option[Schema.Deal.CreativePreApprovalPolicyEnum] = None,
	  /** Output only. Specifies whether the creative is safeFrame compatible. */
		creativeSafeFrameCompatibility: Option[Schema.Deal.CreativeSafeFrameCompatibilityEnum] = None,
	  /** Output only. Metadata about the serving status of this deal. */
		dealServingMetadata: Option[Schema.DealServingMetadata] = None,
	  /** Output only. Specifies the creative source for programmatic deals. PUBLISHER means creative is provided by seller and ADVERTISER means creative is provided by buyer. */
		programmaticCreativeSource: Option[Schema.Deal.ProgrammaticCreativeSourceEnum] = None,
	  /** The set of fields around delivery control that are interesting for a buyer to see but are non-negotiable. These are set by the publisher. */
		deliveryControl: Option[Schema.DeliveryControl] = None,
	  /** Output only. True, if the buyside inventory setup is complete for this deal. */
		isSetupComplete: Option[Boolean] = None
	)
	
	case class PrivateData(
	  /** A buyer or seller specified reference ID. This can be queried in the list operations (max-length: 1024 unicode code units). */
		referenceId: Option[String] = None
	)
	
	object DealTerms {
		enum BrandingTypeEnum extends Enum[BrandingTypeEnum] { case BRANDING_TYPE_UNSPECIFIED, BRANDED, SEMI_TRANSPARENT }
	}
	case class DealTerms(
	  /** Publisher provided description for the terms. */
		description: Option[String] = None,
	  /** Visibility of the URL in bid requests. (default: BRANDED) */
		brandingType: Option[Schema.DealTerms.BrandingTypeEnum] = None,
	  /** Non-binding estimate of the estimated gross spend for this deal. Can be set by buyer or seller. */
		estimatedGrossSpend: Option[Schema.Price] = None,
	  /** Non-binding estimate of the impressions served per day. Can be set by buyer or seller. */
		estimatedImpressionsPerDay: Option[String] = None,
	  /** The time zone name. For deals with Cost Per Day billing, defines the time zone used to mark the boundaries of a day. It should be an IANA TZ name, such as "America/Los_Angeles". For more information, see https://en.wikipedia.org/wiki/List_of_tz_database_time_zones. */
		sellerTimeZone: Option[String] = None,
	  /** The terms for guaranteed fixed price deals. */
		guaranteedFixedPriceTerms: Option[Schema.GuaranteedFixedPriceTerms] = None,
	  /** The terms for non-guaranteed fixed price deals. */
		nonGuaranteedFixedPriceTerms: Option[Schema.NonGuaranteedFixedPriceTerms] = None,
	  /** The terms for non-guaranteed auction deals. */
		nonGuaranteedAuctionTerms: Option[Schema.NonGuaranteedAuctionTerms] = None
	)
	
	object Price {
		enum PricingTypeEnum extends Enum[PricingTypeEnum] { case PRICING_TYPE_UNSPECIFIED, COST_PER_MILLE, COST_PER_DAY }
	}
	case class Price(
	  /** The pricing type for the deal/product. (default: CPM) */
		pricingType: Option[Schema.Price.PricingTypeEnum] = None,
	  /** The actual price with currency specified. */
		amount: Option[Schema.Money] = None
	)
	
	case class Money(
	  /** The three-letter currency code defined in ISO 4217. */
		currencyCode: Option[String] = None,
	  /** The whole units of the amount. For example if `currencyCode` is `"USD"`, then 1 unit is one US dollar. */
		units: Option[String] = None,
	  /** Number of nano (10^-9) units of the amount. The value must be between -999,999,999 and +999,999,999 inclusive. If `units` is positive, `nanos` must be positive or zero. If `units` is zero, `nanos` can be positive, zero, or negative. If `units` is negative, `nanos` must be negative or zero. For example $-1.75 is represented as `units`=-1 and `nanos`=-750,000,000. */
		nanos: Option[Int] = None
	)
	
	object GuaranteedFixedPriceTerms {
		enum ReservationTypeEnum extends Enum[ReservationTypeEnum] { case RESERVATION_TYPE_UNSPECIFIED, STANDARD, SPONSORSHIP }
	}
	case class GuaranteedFixedPriceTerms(
	  /** Count of guaranteed looks. Required for deal, optional for product. For CPD deals, buyer changes to guaranteed_looks will be ignored. */
		guaranteedLooks: Option[String] = None,
	  /** Guaranteed impressions as a percentage. This is the percentage of guaranteed looks that the buyer is guaranteeing to buy. */
		guaranteedImpressions: Option[String] = None,
	  /** Fixed price for the specified buyer. */
		fixedPrices: Option[List[Schema.PricePerBuyer]] = None,
	  /** Daily minimum looks for CPD deal types. For CPD deals, buyer should negotiate on this field instead of guaranteed_looks. */
		minimumDailyLooks: Option[String] = None,
	  /** The reservation type for a Programmatic Guaranteed deal. This indicates whether the number of impressions is fixed, or a percent of available impressions. If not specified, the default reservation type is STANDARD. */
		reservationType: Option[Schema.GuaranteedFixedPriceTerms.ReservationTypeEnum] = None,
	  /** The lifetime impression cap for CPM sponsorship deals. The deal will stop serving when the cap is reached. */
		impressionCap: Option[String] = None,
	  /** For sponsorship deals, this is the percentage of the seller's eligible impressions that the deal will serve until the cap is reached. */
		percentShareOfVoice: Option[String] = None
	)
	
	case class PricePerBuyer(
	  /** The specified price. */
		price: Option[Schema.Price] = None,
	  /** The buyer who will pay this price. If unset, all buyers can pay this price (if the advertisers match, and there's no more specific rule matching the buyer). */
		buyer: Option[Schema.Buyer] = None,
	  /** The list of advertisers for this price when associated with this buyer. If empty, all advertisers with this buyer pay this price. */
		advertiserIds: Option[List[String]] = None
	)
	
	case class Buyer(
	  /** Authorized Buyers account ID of the buyer. */
		accountId: Option[String] = None
	)
	
	case class NonGuaranteedFixedPriceTerms(
	  /** Fixed price for the specified buyer. */
		fixedPrices: Option[List[Schema.PricePerBuyer]] = None
	)
	
	case class NonGuaranteedAuctionTerms(
	  /** Reserve price for the specified buyer. */
		reservePricesPerBuyer: Option[List[Schema.PricePerBuyer]] = None,
	  /** True if open auction buyers are allowed to compete with invited buyers in this private auction. */
		autoOptimizePrivateAuction: Option[Boolean] = None
	)
	
	case class ContactInformation(
	  /** Email address for the contact. */
		email: Option[String] = None,
	  /** The name of the contact. */
		name: Option[String] = None
	)
	
	case class TargetingCriteria(
	  /** The key representing the shared targeting criterion. Targeting criteria defined by Google ad servers will begin with GOOG_. Third parties may define their own keys. A list of permissible keys along with the acceptable values will be provided as part of the external documentation. */
		key: Option[String] = None,
	  /** The list of values to exclude from targeting. Each value is AND'd together. */
		exclusions: Option[List[Schema.TargetingValue]] = None,
	  /** The list of value to include as part of the targeting. Each value is OR'd together. */
		inclusions: Option[List[Schema.TargetingValue]] = None
	)
	
	case class TargetingValue(
	  /** The long value to include/exclude. */
		longValue: Option[String] = None,
	  /** The string value to include/exclude. */
		stringValue: Option[String] = None,
	  /** The creative size value to include/exclude. Filled in when key = GOOG_CREATIVE_SIZE */
		creativeSizeValue: Option[Schema.CreativeSize] = None,
	  /** The daypart targeting to include / exclude. Filled in when the key is GOOG_DAYPART_TARGETING. The definition of this targeting is derived from the structure used by Ad Manager. */
		dayPartTargetingValue: Option[Schema.DayPartTargeting] = None
	)
	
	object CreativeSize {
		enum CreativeSizeTypeEnum extends Enum[CreativeSizeTypeEnum] { case CREATIVE_SIZE_TYPE_UNSPECIFIED, REGULAR, INTERSTITIAL, VIDEO, NATIVE }
		enum SkippableAdTypeEnum extends Enum[SkippableAdTypeEnum] { case SKIPPABLE_AD_TYPE_UNSPECIFIED, GENERIC, INSTREAM_SELECT, NOT_SKIPPABLE }
		enum NativeTemplateEnum extends Enum[NativeTemplateEnum] { case UNKNOWN_NATIVE_TEMPLATE, NATIVE_CONTENT_AD, NATIVE_APP_INSTALL_AD, NATIVE_VIDEO_CONTENT_AD, NATIVE_VIDEO_APP_INSTALL_AD }
		enum AllowedFormatsEnum extends Enum[AllowedFormatsEnum] { case UNKNOWN, AUDIO }
	}
	case class CreativeSize(
	  /** The creative size type. */
		creativeSizeType: Option[Schema.CreativeSize.CreativeSizeTypeEnum] = None,
	  /** For regular or video creative size type, specifies the size of the creative */
		size: Option[Schema.Size] = None,
	  /** For video creatives specifies the sizes of companion ads (if present). Companion sizes may be filled in only when creative_size_type = VIDEO */
		companionSizes: Option[List[Schema.Size]] = None,
	  /** The type of skippable ad for this creative. It will have a value only if creative_size_type = CreativeSizeType.VIDEO. */
		skippableAdType: Option[Schema.CreativeSize.SkippableAdTypeEnum] = None,
	  /** Output only. The native template for this creative. It will have a value only if creative_size_type = CreativeSizeType.NATIVE. */
		nativeTemplate: Option[Schema.CreativeSize.NativeTemplateEnum] = None,
	  /** What formats are allowed by the publisher. If this repeated field is empty then all formats are allowed. For example, if this field contains AllowedFormatType.AUDIO then the publisher only allows an audio ad (without any video). */
		allowedFormats: Option[List[Schema.CreativeSize.AllowedFormatsEnum]] = None
	)
	
	case class Size(
	  /** The width of the creative */
		width: Option[Int] = None,
	  /** The height of the creative. */
		height: Option[Int] = None
	)
	
	object DayPartTargeting {
		enum TimeZoneTypeEnum extends Enum[TimeZoneTypeEnum] { case TIME_ZONE_SOURCE_UNSPECIFIED, PUBLISHER, USER }
	}
	case class DayPartTargeting(
	  /** The timezone to use for interpreting the day part targeting. */
		timeZoneType: Option[Schema.DayPartTargeting.TimeZoneTypeEnum] = None,
	  /** A list of day part targeting criterion. */
		dayParts: Option[List[Schema.DayPart]] = None
	)
	
	object DayPart {
		enum DayOfWeekEnum extends Enum[DayOfWeekEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class DayPart(
	  /** The day of the week to target. If unspecified, applicable to all days. */
		dayOfWeek: Option[Schema.DayPart.DayOfWeekEnum] = None,
	  /** The starting time of day for the ad to show (minute level granularity). The start time is inclusive. This field is not available for filtering in PQL queries. */
		startTime: Option[Schema.TimeOfDay] = None,
	  /** The ending time of the day for the ad to show (minute level granularity). The end time is exclusive. This field is not available for filtering in PQL queries. */
		endTime: Option[Schema.TimeOfDay] = None
	)
	
	case class TimeOfDay(
	  /** Hours of a day in 24 hour format. Must be greater than or equal to 0 and typically must be less than or equal to 23. An API may choose to allow the value "24:00:00" for scenarios like business closing time. */
		hours: Option[Int] = None,
	  /** Minutes of an hour. Must be greater than or equal to 0 and less than or equal to 59. */
		minutes: Option[Int] = None,
	  /** Seconds of a minute. Must be greater than or equal to 0 and typically must be less than or equal to 59. An API may allow the value 60 if it allows leap-seconds. */
		seconds: Option[Int] = None,
	  /** Fractions of seconds, in nanoseconds. Must be greater than or equal to 0 and less than or equal to 999,999,999. */
		nanos: Option[Int] = None
	)
	
	case class MarketplaceTargeting(
	  /** Geo criteria IDs to be included/excluded. */
		geoTargeting: Option[Schema.CriteriaTargeting] = None,
	  /** Inventory sizes to be included/excluded. */
		inventorySizeTargeting: Option[Schema.InventorySizeTargeting] = None,
	  /** Technology targeting information, for example, operating system, device category. */
		technologyTargeting: Option[Schema.TechnologyTargeting] = None,
	  /** Placement targeting information, for example, URL, mobile applications. */
		placementTargeting: Option[Schema.PlacementTargeting] = None,
	  /** Video targeting information. */
		videoTargeting: Option[Schema.VideoTargeting] = None
	)
	
	case class CriteriaTargeting(
	  /** A list of numeric IDs to be included. */
		targetedCriteriaIds: Option[List[String]] = None,
	  /** A list of numeric IDs to be excluded. */
		excludedCriteriaIds: Option[List[String]] = None
	)
	
	case class InventorySizeTargeting(
	  /** A list of inventory sizes to be included. */
		targetedInventorySizes: Option[List[Schema.AdSize]] = None,
	  /** A list of inventory sizes to be excluded. */
		excludedInventorySizes: Option[List[Schema.AdSize]] = None
	)
	
	object AdSize {
		enum SizeTypeEnum extends Enum[SizeTypeEnum] { case SIZE_TYPE_UNSPECIFIED, PIXEL, INTERSTITIAL, NATIVE, FLUID }
	}
	case class AdSize(
	  /** The width of the ad slot in pixels. This field will be present only when size type is `PIXEL`. */
		width: Option[String] = None,
	  /** The height of the ad slot in pixels. This field will be present only when size type is `PIXEL`. */
		height: Option[String] = None,
	  /** The size type of the ad slot. */
		sizeType: Option[Schema.AdSize.SizeTypeEnum] = None
	)
	
	case class TechnologyTargeting(
	  /** IDs of device categories to be included/excluded. */
		deviceCategoryTargeting: Option[Schema.CriteriaTargeting] = None,
	  /** IDs of device capabilities to be included/excluded. */
		deviceCapabilityTargeting: Option[Schema.CriteriaTargeting] = None,
	  /** Operating system related targeting information. */
		operatingSystemTargeting: Option[Schema.OperatingSystemTargeting] = None
	)
	
	case class OperatingSystemTargeting(
	  /** IDs of operating systems to be included/excluded. */
		operatingSystemCriteria: Option[Schema.CriteriaTargeting] = None,
	  /** IDs of operating system versions to be included/excluded. */
		operatingSystemVersionCriteria: Option[Schema.CriteriaTargeting] = None
	)
	
	case class PlacementTargeting(
	  /** URLs to be included/excluded. */
		urlTargeting: Option[Schema.UrlTargeting] = None,
	  /** Mobile application targeting information in a deal. This doesn't apply to Auction Packages. */
		mobileApplicationTargeting: Option[Schema.MobileApplicationTargeting] = None
	)
	
	case class UrlTargeting(
	  /** A list of URLs to be included. */
		targetedUrls: Option[List[String]] = None,
	  /** A list of URLs to be excluded. */
		excludedUrls: Option[List[String]] = None
	)
	
	case class MobileApplicationTargeting(
	  /** Publisher owned apps to be targeted or excluded by the publisher to display the ads in. */
		firstPartyTargeting: Option[Schema.FirstPartyMobileApplicationTargeting] = None
	)
	
	case class FirstPartyMobileApplicationTargeting(
	  /** A list of application IDs to be included. */
		targetedAppIds: Option[List[String]] = None,
	  /** A list of application IDs to be excluded. */
		excludedAppIds: Option[List[String]] = None
	)
	
	object VideoTargeting {
		enum TargetedPositionTypesEnum extends Enum[TargetedPositionTypesEnum] { case POSITION_TYPE_UNSPECIFIED, PREROLL, MIDROLL, POSTROLL }
		enum ExcludedPositionTypesEnum extends Enum[ExcludedPositionTypesEnum] { case POSITION_TYPE_UNSPECIFIED, PREROLL, MIDROLL, POSTROLL }
	}
	case class VideoTargeting(
	  /** A list of video positions to be included. When the included list is present, the excluded list must be empty. When the excluded list is present, the included list must be empty. */
		targetedPositionTypes: Option[List[Schema.VideoTargeting.TargetedPositionTypesEnum]] = None,
	  /** A list of video positions to be excluded. Position types can either be included or excluded (XOR). */
		excludedPositionTypes: Option[List[Schema.VideoTargeting.ExcludedPositionTypesEnum]] = None
	)
	
	object CreativeRestrictions {
		enum CreativeFormatEnum extends Enum[CreativeFormatEnum] { case CREATIVE_FORMAT_UNSPECIFIED, DISPLAY, VIDEO }
		enum SkippableAdTypeEnum extends Enum[SkippableAdTypeEnum] { case SKIPPABLE_AD_TYPE_UNSPECIFIED, SKIPPABLE, INSTREAM_SELECT, NOT_SKIPPABLE }
	}
	case class CreativeRestrictions(
	  /** The format of the environment that the creatives will be displayed in. */
		creativeFormat: Option[Schema.CreativeRestrictions.CreativeFormatEnum] = None,
		creativeSpecifications: Option[List[Schema.CreativeSpecification]] = None,
	  /** Skippable video ads allow viewers to skip ads after 5 seconds. */
		skippableAdType: Option[Schema.CreativeRestrictions.SkippableAdTypeEnum] = None
	)
	
	case class CreativeSpecification(
	  /** The size of the creative. */
		creativeSize: Option[Schema.AdSize] = None,
	  /** Companion sizes may be filled in only when this is a video creative. */
		creativeCompanionSizes: Option[List[Schema.AdSize]] = None
	)
	
	case class DealServingMetadata(
	  /** Output only. Tracks which parties (if any) have paused a deal. */
		dealPauseStatus: Option[Schema.DealPauseStatus] = None
	)
	
	object DealPauseStatus {
		enum FirstPausedByEnum extends Enum[FirstPausedByEnum] { case BUYER_SELLER_ROLE_UNSPECIFIED, BUYER, SELLER }
	}
	case class DealPauseStatus(
	  /** True, if the buyer has paused the deal unilaterally. */
		hasBuyerPaused: Option[Boolean] = None,
	  /** True, if the seller has paused the deal unilaterally. */
		hasSellerPaused: Option[Boolean] = None,
	  /** The role of the person who first paused this deal. */
		firstPausedBy: Option[Schema.DealPauseStatus.FirstPausedByEnum] = None,
	  /** The buyer's reason for pausing, if the buyer paused the deal. */
		buyerPauseReason: Option[String] = None,
	  /** The seller's reason for pausing, if the seller paused the deal. */
		sellerPauseReason: Option[String] = None
	)
	
	object DeliveryControl {
		enum DeliveryRateTypeEnum extends Enum[DeliveryRateTypeEnum] { case DELIVERY_RATE_TYPE_UNSPECIFIED, EVENLY, FRONT_LOADED, AS_FAST_AS_POSSIBLE }
		enum CreativeBlockingLevelEnum extends Enum[CreativeBlockingLevelEnum] { case CREATIVE_BLOCKING_LEVEL_UNSPECIFIED, PUBLISHER_BLOCKING_RULES, ADX_POLICY_BLOCKING_ONLY }
	}
	case class DeliveryControl(
	  /** Output only. Specifies how the impression delivery will be paced. */
		deliveryRateType: Option[Schema.DeliveryControl.DeliveryRateTypeEnum] = None,
	  /** Output only. Specifies any frequency caps. */
		frequencyCaps: Option[List[Schema.FrequencyCap]] = None,
	  /** Output only. Specified the creative blocking levels to be applied. */
		creativeBlockingLevel: Option[Schema.DeliveryControl.CreativeBlockingLevelEnum] = None
	)
	
	object FrequencyCap {
		enum TimeUnitTypeEnum extends Enum[TimeUnitTypeEnum] { case TIME_UNIT_TYPE_UNSPECIFIED, MINUTE, HOUR, DAY, WEEK, MONTH, LIFETIME, POD, STREAM }
	}
	case class FrequencyCap(
	  /** The maximum number of impressions that can be served to a user within the specified time period. */
		maxImpressions: Option[Int] = None,
	  /** The amount of time, in the units specified by time_unit_type. Defines the amount of time over which impressions per user are counted and capped. */
		numTimeUnits: Option[Int] = None,
	  /** The time unit. Along with num_time_units defines the amount of time over which impressions per user are counted and capped. */
		timeUnitType: Option[Schema.FrequencyCap.TimeUnitTypeEnum] = None
	)
	
	case class Seller(
	  /** The unique ID for the seller. The seller fills in this field. The seller account ID is then available to buyer in the product. */
		accountId: Option[String] = None,
	  /** Output only. Ad manager network code for the seller. */
		subAccountId: Option[String] = None
	)
	
	object Note {
		enum CreatorRoleEnum extends Enum[CreatorRoleEnum] { case BUYER_SELLER_ROLE_UNSPECIFIED, BUYER, SELLER }
	}
	case class Note(
	  /** Output only. The revision number of the proposal when the note is created. */
		proposalRevision: Option[String] = None,
	  /** Output only. The unique ID for the note. */
		noteId: Option[String] = None,
	  /** Output only. The timestamp for when this note was created. */
		createTime: Option[String] = None,
	  /** Output only. The role of the person (buyer/seller) creating the note. */
		creatorRole: Option[Schema.Note.CreatorRoleEnum] = None,
	  /** The actual note to attach. (max-length: 1024 unicode code units) Note: This field may be set only when creating the resource. Modifying this field while updating the resource will result in an error. */
		note: Option[String] = None
	)
	
	case class ListProposalsResponse(
	  /** The list of proposals. */
		proposals: Option[List[Schema.Proposal]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class AddNoteRequest(
	  /** Details of the note to add. */
		note: Option[Schema.Note] = None
	)
	
	case class CancelNegotiationRequest(
	
	)
	
	case class AcceptProposalRequest(
	  /** The last known client revision number of the proposal. */
		proposalRevision: Option[String] = None
	)
	
	case class CompleteSetupRequest(
	
	)
	
	case class PauseProposalRequest(
	  /** The reason why the proposal is being paused. This human readable message will be displayed in the seller's UI. (Max length: 1000 unicode code units.) */
		reason: Option[String] = None
	)
	
	case class ResumeProposalRequest(
	
	)
	
	case class PauseProposalDealsRequest(
	  /** The external_deal_id's of the deals to be paused. If empty, all the deals in the proposal will be paused. */
		externalDealIds: Option[List[String]] = None,
	  /** The reason why the deals are being paused. This human readable message will be displayed in the seller's UI. (Max length: 1000 unicode code units.) */
		reason: Option[String] = None
	)
	
	case class ResumeProposalDealsRequest(
	  /** The external_deal_id's of the deals to resume. If empty, all the deals in the proposal will be resumed. */
		externalDealIds: Option[List[String]] = None
	)
	
	object Product {
		enum SyndicationProductEnum extends Enum[SyndicationProductEnum] { case SYNDICATION_PRODUCT_UNSPECIFIED, CONTENT, MOBILE, VIDEO, GAMES }
	}
	case class Product(
	  /** The unique ID for the product. */
		productId: Option[String] = None,
	  /** Creation time. */
		createTime: Option[String] = None,
	  /** Time of last update. */
		updateTime: Option[String] = None,
	  /** Information about the seller that created this product. */
		seller: Option[Schema.Seller] = None,
	  /** An ID which can be used by the Publisher Profile API to get more information about the seller that created this product. */
		publisherProfileId: Option[String] = None,
	  /** The display name for this product as set by the seller. */
		displayName: Option[String] = None,
	  /** The negotiable terms of the deal. */
		terms: Option[Schema.DealTerms] = None,
	  /** Optional contact information for the creator of this product. */
		creatorContacts: Option[List[Schema.ContactInformation]] = None,
	  /** Inventory availability dates. The start time will be truncated to seconds during serving. Thus, a field specified as 3:23:34.456 (HH:mm:ss.SSS) will be truncated to 3:23:34 when serving. */
		availableStartTime: Option[String] = None,
	  /** The proposed end time for the deal. The field will be truncated to the order of seconds during serving. */
		availableEndTime: Option[String] = None,
	  /** If the creator has already signed off on the product, then the buyer can finalize the deal by accepting the product as is. When copying to a proposal, if any of the terms are changed, then auto_finalize is automatically set to false. */
		hasCreatorSignedOff: Option[Boolean] = None,
	  /** Targeting that is shared between the buyer and the seller. Each targeting criterion has a specified key and for each key there is a list of inclusion value or exclusion values. */
		targetingCriterion: Option[List[Schema.TargetingCriteria]] = None,
	  /** The web-property code for the seller. This needs to be copied as is when adding a new deal to a proposal. */
		webPropertyCode: Option[String] = None,
	  /** The revision number of the product (auto-assigned by Marketplace). */
		productRevision: Option[String] = None,
	  /** The syndication product associated with the deal. */
		syndicationProduct: Option[Schema.Product.SyndicationProductEnum] = None
	)
	
	case class ListProductsResponse(
	  /** The list of matching products at their head revision number. */
		products: Option[List[Schema.Product]] = None,
	  /** List pagination support. */
		nextPageToken: Option[String] = None
	)
	
	case class PublisherProfile(
	  /** Unique ID for publisher profile. */
		publisherProfileId: Option[String] = None,
	  /** Seller of the publisher profile. */
		seller: Option[Schema.Seller] = None,
	  /** Name of the publisher profile. */
		displayName: Option[String] = None,
	  /** The list of domains represented in this publisher profile. Empty if this is a parent profile. These are top private domains, meaning that these will not contain a string like "photos.google.co.uk/123", but will instead contain "google.co.uk". */
		domains: Option[List[String]] = None,
	  /** The list of apps represented in this publisher profile. Empty if this is a parent profile. */
		mobileApps: Option[List[Schema.PublisherProfileMobileApplication]] = None,
	  /** A Google public URL to the logo for this publisher profile. The logo is stored as a PNG, JPG, or GIF image. */
		logoUrl: Option[String] = None,
	  /** Contact information for direct reservation deals. This is free text entered by the publisher and may include information like names, phone numbers and email addresses. */
		directDealsContact: Option[String] = None,
	  /** Contact information for programmatic deals. This is free text entered by the publisher and may include information like names, phone numbers and email addresses. */
		programmaticDealsContact: Option[String] = None,
	  /** URL to additional marketing and sales materials. */
		mediaKitUrl: Option[String] = None,
	  /** URL to a sample content page. */
		samplePageUrl: Option[String] = None,
	  /** URL to a publisher rate card. */
		rateCardInfoUrl: Option[String] = None,
	  /** URL to publisher's Google+ page. */
		googlePlusUrl: Option[String] = None,
	  /** Overview of the publisher. */
		overview: Option[String] = None,
	  /** Statement explaining what's unique about publisher's business, and why buyers should partner with the publisher. */
		buyerPitchStatement: Option[String] = None,
	  /** Up to three key metrics and rankings. Max 100 characters each. For example "#1 Mobile News Site for 20 Straight Months". */
		topHeadlines: Option[List[String]] = None,
	  /** Description on the publisher's audience. */
		audienceDescription: Option[String] = None,
	  /** Indicates if this profile is the parent profile of the seller. A parent profile represents all the inventory from the seller, as opposed to child profile that is created to brand a portion of inventory. One seller should have only one parent publisher profile, and can have multiple child profiles. Publisher profiles for the same seller will have same value of field google.ads.adexchange.buyer.v2beta1.PublisherProfile.seller. See https://support.google.com/admanager/answer/6035806 for details. */
		isParent: Option[Boolean] = None
	)
	
	object PublisherProfileMobileApplication {
		enum AppStoreEnum extends Enum[AppStoreEnum] { case APP_STORE_TYPE_UNSPECIFIED, APPLE_ITUNES, GOOGLE_PLAY, ROKU, AMAZON_FIRETV, PLAYSTATION, XBOX, SAMSUNG_TV, AMAZON, OPPO, SAMSUNG, VIVO, XIAOMI, LG_TV }
	}
	case class PublisherProfileMobileApplication(
	  /** The external ID for the app from its app store. */
		externalAppId: Option[String] = None,
	  /** The name of the app. */
		name: Option[String] = None,
	  /** The app store the app belongs to. */
		appStore: Option[Schema.PublisherProfileMobileApplication.AppStoreEnum] = None
	)
	
	case class ListPublisherProfilesResponse(
	  /** The list of matching publisher profiles. */
		publisherProfiles: Option[List[Schema.PublisherProfile]] = None,
	  /** List pagination support */
		nextPageToken: Option[String] = None
	)
}
