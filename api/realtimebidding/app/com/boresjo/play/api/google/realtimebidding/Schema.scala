package com.boresjo.play.api.google.realtimebidding

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class Bidder(
	  /** Output only. Name of the bidder resource that must follow the pattern `bidders/{bidderAccountId}`, where `{bidderAccountId}` is the account ID of the bidder whose information is to be received. One can get their account ID on the Authorized Buyers or Open Bidding UI, or by contacting their Google account manager. */
		name: Option[String] = None,
	  /** Output only. The base URL used in cookie match requests. Refer to https://developers.google.com/authorized-buyers/rtb/cookie-guide for further information. */
		cookieMatchingUrl: Option[String] = None,
	  /** Output only. The buyer's network ID used for cookie matching. This ID corresponds to the `google_nid` parameter in the URL used in cookie match requests. Refer to https://developers.google.com/authorized-buyers/rtb/cookie-guide for further information. */
		cookieMatchingNetworkId: Option[String] = None,
	  /** Output only. An option to bypass pretargeting for private auctions and preferred deals. When true, bid requests from these nonguaranteed deals will always be sent. When false, bid requests will be subject to regular pretargeting configurations. Programmatic Guaranteed deals will always be sent to the bidder, regardless of the value for this flag. Auction packages are not impacted by this value and are subject to the regular pretargeting configurations. */
		bypassNonguaranteedDealsPretargeting: Option[Boolean] = None,
	  /** Output only. The billing ID for the deals pretargeting config. This billing ID is sent on the bid request for guaranteed and nonguaranteed deals matched in pretargeting. */
		dealsBillingId: Option[String] = None
	)
	
	case class ListBiddersResponse(
	  /** List of bidders. */
		bidders: Option[List[Schema.Bidder]] = None,
	  /** A token which can be passed to a subsequent call to the `ListBidders` method to retrieve the next page of results in ListBiddersRequest.pageToken. */
		nextPageToken: Option[String] = None
	)
	
	object Endpoint {
		enum TradingLocationEnum extends Enum[TradingLocationEnum] { case TRADING_LOCATION_UNSPECIFIED, US_WEST, US_EAST, EUROPE, ASIA }
		enum BidProtocolEnum extends Enum[BidProtocolEnum] { case BID_PROTOCOL_UNSPECIFIED, GOOGLE_RTB, OPENRTB_JSON, OPENRTB_PROTOBUF }
	}
	case class Endpoint(
	  /** Output only. Name of the endpoint resource that must follow the pattern `bidders/{bidderAccountId}/endpoints/{endpointId}`, where {bidderAccountId} is the account ID of the bidder who operates this endpoint, and {endpointId} is a unique ID assigned by the server. */
		name: Option[String] = None,
	  /** Output only. The URL that bid requests should be sent to. */
		url: Option[String] = None,
	  /** The maximum number of queries per second allowed to be sent to this server. */
		maximumQps: Option[String] = None,
	  /** The trading location that bid requests should be sent from. See https://developers.google.com/authorized-buyers/rtb/peer-guide#trading-locations for further information. */
		tradingLocation: Option[Schema.Endpoint.TradingLocationEnum] = None,
	  /** The protocol that the bidder endpoint is using. */
		bidProtocol: Option[Schema.Endpoint.BidProtocolEnum] = None
	)
	
	case class ListEndpointsResponse(
	  /** List of bidder endpoints. */
		endpoints: Option[List[Schema.Endpoint]] = None,
	  /** A token which can be passed to a subsequent call to the `ListEndpoints` method to retrieve the next page of results in ListEndpointsRequest.pageToken. */
		nextPageToken: Option[String] = None
	)
	
	case class Buyer(
	  /** Output only. Name of the buyer resource that must follow the pattern `buyers/{buyerAccountId}`, where `{buyerAccountId}` is the account ID of the buyer account whose information is to be received. One can get their account ID on the Authorized Buyers or Open Bidding UI, or by contacting their Google account manager. */
		name: Option[String] = None,
	  /** Output only. The diplay name associated with this buyer account, as visible to sellers. */
		displayName: Option[String] = None,
	  /** Output only. The name of the bidder resource that is responsible for receiving bidding traffic for this account. The bidder name must follow the pattern `bidders/{bidderAccountId}`, where `{bidderAccountId}` is the account ID of the bidder receiving traffic for this buyer. */
		bidder: Option[String] = None,
	  /** Output only. The number of creatives that this buyer submitted through the API or bid with in the last 30 days. This is counted against the maximum number of active creatives. */
		activeCreativeCount: Option[String] = None,
	  /** Output only. The maximum number of active creatives that this buyer can have. */
		maximumActiveCreativeCount: Option[String] = None,
	  /** Output only. A list of billing IDs associated with this account. These IDs appear on: 1. A bid request, to signal which buyers are eligible to bid on a given opportunity, and which pretargeting configurations were matched for each eligible buyer. 2. The bid response, to attribute a winning impression to a specific account for billing, reporting, policy and publisher block enforcement. */
		billingIds: Option[List[String]] = None
	)
	
	case class ListBuyersResponse(
	  /** List of buyers. */
		buyers: Option[List[Schema.Buyer]] = None,
	  /** A token which can be passed to a subsequent call to the `ListBuyers` method to retrieve the next page of results in ListBuyersRequest.pageToken. */
		nextPageToken: Option[String] = None
	)
	
	case class ListCreativesResponse(
	  /** The list of creatives. */
		creatives: Option[List[Schema.Creative]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListCreativesRequest.pageToken field in the subsequent call to the `ListCreatives` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object Creative {
		enum CreativeFormatEnum extends Enum[CreativeFormatEnum] { case CREATIVE_FORMAT_UNSPECIFIED, HTML, VIDEO, NATIVE }
		enum RestrictedCategoriesEnum extends Enum[RestrictedCategoriesEnum] { case RESTRICTED_CATEGORY_UNSPECIFIED, ALCOHOL }
		enum DeclaredAttributesEnum extends Enum[DeclaredAttributesEnum] { case ATTRIBUTE_UNSPECIFIED, IMAGE_RICH_MEDIA, ADOBE_FLASH_FLV, IS_TAGGED, IS_COOKIE_TARGETED, IS_USER_INTEREST_TARGETED, EXPANDING_DIRECTION_NONE, EXPANDING_DIRECTION_UP, EXPANDING_DIRECTION_DOWN, EXPANDING_DIRECTION_LEFT, EXPANDING_DIRECTION_RIGHT, EXPANDING_DIRECTION_UP_LEFT, EXPANDING_DIRECTION_UP_RIGHT, EXPANDING_DIRECTION_DOWN_LEFT, EXPANDING_DIRECTION_DOWN_RIGHT, CREATIVE_TYPE_HTML, CREATIVE_TYPE_VAST_VIDEO, EXPANDING_DIRECTION_UP_OR_DOWN, EXPANDING_DIRECTION_LEFT_OR_RIGHT, EXPANDING_DIRECTION_ANY_DIAGONAL, EXPANDING_ACTION_ROLLOVER_TO_EXPAND, INSTREAM_VAST_VIDEO_TYPE_VPAID_FLASH, RICH_MEDIA_CAPABILITY_TYPE_MRAID, RICH_MEDIA_CAPABILITY_TYPE_FLASH, RICH_MEDIA_CAPABILITY_TYPE_HTML5, SKIPPABLE_INSTREAM_VIDEO, RICH_MEDIA_CAPABILITY_TYPE_SSL, RICH_MEDIA_CAPABILITY_TYPE_NON_SSL, RICH_MEDIA_CAPABILITY_TYPE_INTERSTITIAL, NON_SKIPPABLE_INSTREAM_VIDEO, NATIVE_ELIGIBILITY_ELIGIBLE, NON_VPAID, NATIVE_ELIGIBILITY_NOT_ELIGIBLE, ANY_INTERSTITIAL, NON_INTERSTITIAL, IN_BANNER_VIDEO, RENDERING_SIZELESS_ADX, OMSDK_1_0, RENDERING_PLAYABLE }
		enum DeclaredRestrictedCategoriesEnum extends Enum[DeclaredRestrictedCategoriesEnum] { case RESTRICTED_CATEGORY_UNSPECIFIED, ALCOHOL }
	}
	case class Creative(
	  /** Output only. Name of the creative. Follows the pattern `buyers/{buyer}/creatives/{creative}`, where `{buyer}` represents the account ID of the buyer who owns the creative, and `{creative}` is the buyer-specific creative ID that references this creative in the bid response. */
		name: Option[String] = None,
	  /** Output only. ID of the buyer account that this creative is owned by. Can be used to filter the response of the creatives.list method with equality and inequality check. */
		accountId: Option[String] = None,
	  /** Buyer-specific creative ID that references this creative in bid responses. This field is Ignored in update operations. Can be used to filter the response of the creatives.list method. The maximum length of the creative ID is 128 bytes. */
		creativeId: Option[String] = None,
	  /** An HTML creative. */
		html: Option[Schema.HtmlContent] = None,
	  /** A video creative. */
		video: Option[Schema.VideoContent] = None,
	  /** A native creative. */
		native: Option[Schema.NativeContent] = None,
	  /** Experimental field that can be used during the [FLEDGE Origin Trial](/authorized-buyers/rtb/fledge-origin-trial). The URL to fetch an interest group ad used in [TURTLEDOVE on-device auction](https://github.com/WICG/turtledove/blob/main/FLEDGE.md#1-browsers-record-interest-groups"). This should be unique among all creatives for a given `accountId`. This URL should be the same as the URL returned by [generateBid()](https://github.com/WICG/turtledove/blob/main/FLEDGE.md#32-on-device-bidding). */
		renderUrl: Option[String] = None,
	  /** Output only. The format of this creative. Can be used to filter the response of the creatives.list method. */
		creativeFormat: Option[Schema.Creative.CreativeFormatEnum] = None,
	  /** The agency ID for this creative. */
		agencyId: Option[String] = None,
	  /** The set of declared destination URLs for the creative. Can be used to filter the response of the creatives.list method. */
		declaredClickThroughUrls: Option[List[String]] = None,
	  /** The set of URLs to be called to record an impression. */
		impressionTrackingUrls: Option[List[String]] = None,
	  /** The link to AdChoices destination page. This is only supported for native ads. */
		adChoicesDestinationUrl: Option[String] = None,
	  /** All restricted categories for the ads that may be shown from this creative. */
		restrictedCategories: Option[List[Schema.Creative.RestrictedCategoriesEnum]] = None,
	  /** All declared attributes for the ads that may be shown from this creative. Can be used to filter the response of the creatives.list method. If the `excluded_attribute` field of a [bid request](https://developers.google.com/authorized-buyers/rtb/downloads/realtime-bidding-proto") contains one of the attributes that were declared or detected for a given creative, and a bid is submitted with that creative, the bid will be filtered before the auction. */
		declaredAttributes: Option[List[Schema.Creative.DeclaredAttributesEnum]] = None,
	  /** IDs for the declared ad technology vendors that may be used by this creative. See https://storage.googleapis.com/adx-rtb-dictionaries/vendors.txt for possible values. Can be used to filter the response of the creatives.list method. */
		declaredVendorIds: Option[List[Int]] = None,
	  /** All declared restricted categories for the ads that may be shown from this creative. Can be used to filter the response of the creatives.list method. */
		declaredRestrictedCategories: Option[List[Schema.Creative.DeclaredRestrictedCategoriesEnum]] = None,
	  /** The name of the company being advertised in the creative. Can be used to filter the response of the creatives.list method. */
		advertiserName: Option[String] = None,
	  /** Output only. The version of the creative. Version for a new creative is 1 and it increments during subsequent creative updates. */
		version: Option[Int] = None,
	  /** Output only. The last update timestamp of the creative through the API. */
		apiUpdateTime: Option[String] = None,
	  /** Output only. IDs of all of the deals with which this creative has been used in bidding. Can be used to filter the response of the creatives.list method. */
		dealIds: Option[List[String]] = None,
	  /** Output only. Top level status and detected attributes of a creative (for example domain, language, advertiser, product category, etc.) that affect whether (status) and where (context) a creative will be allowed to serve. */
		creativeServingDecision: Option[Schema.CreativeServingDecision] = None
	)
	
	case class HtmlContent(
	  /** The HTML snippet that displays the ad when inserted in the web page. */
		snippet: Option[String] = None,
	  /** The width of the HTML snippet in pixels. Can be used to filter the response of the creatives.list method. */
		width: Option[Int] = None,
	  /** The height of the HTML snippet in pixels. Can be used to filter the response of the creatives.list method. */
		height: Option[Int] = None
	)
	
	case class VideoContent(
	  /** The URL to fetch a video ad. The URL should return an XML response that conforms to the VAST 2.0, 3.0 or 4.x standard. */
		videoUrl: Option[String] = None,
	  /** The contents of a VAST document for a video ad. This document should conform to the VAST 2.0, 3.0, or 4.x standard. */
		videoVastXml: Option[String] = None,
	  /** Output only. Video metadata. */
		videoMetadata: Option[Schema.VideoMetadata] = None
	)
	
	object VideoMetadata {
		enum VastVersionEnum extends Enum[VastVersionEnum] { case VAST_VERSION_UNSPECIFIED, VAST_VERSION_1_0, VAST_VERSION_2_0, VAST_VERSION_3_0, VAST_VERSION_4_0 }
	}
	case class VideoMetadata(
	  /** Is this a valid VAST ad? Can be used to filter the response of the creatives.list method. */
		isValidVast: Option[Boolean] = None,
	  /** Is this a VPAID ad? Can be used to filter the response of the creatives.list method. */
		isVpaid: Option[Boolean] = None,
	  /** The minimum duration that the user has to watch before being able to skip this ad. If the field is not set, the ad is not skippable. If the field is set, the ad is skippable. Can be used to filter the response of the creatives.list method. */
		skipOffset: Option[String] = None,
	  /** The duration of the ad. Can be used to filter the response of the creatives.list method. */
		duration: Option[String] = None,
	  /** The maximum VAST version across all wrapped VAST documents. Can be used to filter the response of the creatives.list method. */
		vastVersion: Option[Schema.VideoMetadata.VastVersionEnum] = None,
	  /** The list of all media files declared in the VAST. If there are multiple VASTs in a wrapper chain, this includes the media files from the deepest one in the chain. */
		mediaFiles: Option[List[Schema.MediaFile]] = None
	)
	
	object MediaFile {
		enum MimeTypeEnum extends Enum[MimeTypeEnum] { case VIDEO_MIME_TYPE_UNSPECIFIED, MIME_VIDEO_XFLV, MIME_VIDEO_WEBM, MIME_VIDEO_MP4, MIME_VIDEO_OGG, MIME_VIDEO_YT_HOSTED, MIME_VIDEO_X_MS_WMV, MIME_VIDEO_3GPP, MIME_VIDEO_MOV, MIME_APPLICATION_SWF, MIME_APPLICATION_SURVEY, MIME_APPLICATION_JAVASCRIPT, MIME_APPLICATION_SILVERLIGHT, MIME_APPLICATION_MPEGURL, MIME_APPLICATION_MPEGDASH, MIME_AUDIO_MP4A, MIME_AUDIO_MP3, MIME_AUDIO_OGG }
	}
	case class MediaFile(
	  /** The MIME type of this media file. Can be used to filter the response of the creatives.list method. */
		mimeType: Option[Schema.MediaFile.MimeTypeEnum] = None,
	  /** Bitrate of the video file, in Kbps. Can be used to filter the response of the creatives.list method. */
		bitrate: Option[String] = None
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
	  /** The contents of a VAST document for a native video ad. */
		videoVastXml: Option[String] = None,
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
		priceDisplayText: Option[String] = None
	)
	
	case class Image(
	  /** The URL of the image. */
		url: Option[String] = None,
	  /** Image width in pixels. */
		width: Option[Int] = None,
	  /** Image height in pixels. */
		height: Option[Int] = None
	)
	
	object CreativeServingDecision {
		enum DetectedAttributesEnum extends Enum[DetectedAttributesEnum] { case ATTRIBUTE_UNSPECIFIED, IMAGE_RICH_MEDIA, ADOBE_FLASH_FLV, IS_TAGGED, IS_COOKIE_TARGETED, IS_USER_INTEREST_TARGETED, EXPANDING_DIRECTION_NONE, EXPANDING_DIRECTION_UP, EXPANDING_DIRECTION_DOWN, EXPANDING_DIRECTION_LEFT, EXPANDING_DIRECTION_RIGHT, EXPANDING_DIRECTION_UP_LEFT, EXPANDING_DIRECTION_UP_RIGHT, EXPANDING_DIRECTION_DOWN_LEFT, EXPANDING_DIRECTION_DOWN_RIGHT, CREATIVE_TYPE_HTML, CREATIVE_TYPE_VAST_VIDEO, EXPANDING_DIRECTION_UP_OR_DOWN, EXPANDING_DIRECTION_LEFT_OR_RIGHT, EXPANDING_DIRECTION_ANY_DIAGONAL, EXPANDING_ACTION_ROLLOVER_TO_EXPAND, INSTREAM_VAST_VIDEO_TYPE_VPAID_FLASH, RICH_MEDIA_CAPABILITY_TYPE_MRAID, RICH_MEDIA_CAPABILITY_TYPE_FLASH, RICH_MEDIA_CAPABILITY_TYPE_HTML5, SKIPPABLE_INSTREAM_VIDEO, RICH_MEDIA_CAPABILITY_TYPE_SSL, RICH_MEDIA_CAPABILITY_TYPE_NON_SSL, RICH_MEDIA_CAPABILITY_TYPE_INTERSTITIAL, NON_SKIPPABLE_INSTREAM_VIDEO, NATIVE_ELIGIBILITY_ELIGIBLE, NON_VPAID, NATIVE_ELIGIBILITY_NOT_ELIGIBLE, ANY_INTERSTITIAL, NON_INTERSTITIAL, IN_BANNER_VIDEO, RENDERING_SIZELESS_ADX, OMSDK_1_0, RENDERING_PLAYABLE }
		enum DetectedCategoriesTaxonomyEnum extends Enum[DetectedCategoriesTaxonomyEnum] { case AD_CATEGORY_TAXONOMY_UNSPECIFIED, IAB_CONTENT_1_0 }
	}
	case class CreativeServingDecision(
	  /** The set of detected destination URLs for the creative. Can be used to filter the response of the creatives.list method. */
		detectedClickThroughUrls: Option[List[String]] = None,
	  /** Detected advertisers and brands. */
		detectedAdvertisers: Option[List[Schema.AdvertiserAndBrand]] = None,
	  /** Detected product categories, if any. See the ad-product-categories.txt file in the technical documentation for a list of IDs. Can be used to filter the response of the creatives.list method. */
		detectedProductCategories: Option[List[Int]] = None,
	  /** The detected languages for this creative. The order is arbitrary. The codes are 2 or 5 characters and are documented at https://developers.google.com/adwords/api/docs/appendix/languagecodes. Can be used to filter the response of the creatives.list method. */
		detectedLanguages: Option[List[String]] = None,
	  /** The detected domains for this creative. */
		detectedDomains: Option[List[String]] = None,
	  /** Detected sensitive categories, if any. Can be used to filter the response of the creatives.list method. See the ad-sensitive-categories.txt file in the technical documentation for a list of IDs. You should use these IDs along with the excluded-sensitive-category field in the bid request to filter your bids. */
		detectedSensitiveCategories: Option[List[Int]] = None,
	  /** Publisher-excludable attributes that were detected for this creative. Can be used to filter the response of the creatives.list method. If the `excluded_attribute` field of a [bid request](https://developers.google.com/authorized-buyers/rtb/downloads/realtime-bidding-proto) contains one of the attributes that were declared or detected for a given creative, and a bid is submitted with that creative, the bid will be filtered before the auction. */
		detectedAttributes: Option[List[Schema.CreativeServingDecision.DetectedAttributesEnum]] = None,
	  /** IDs of the ad technology vendors that were detected to be used by this creative. See https://storage.googleapis.com/adx-rtb-dictionaries/vendors.txt for possible values. Can be used to filter the response of the creatives.list method. If the `allowed_vendor_type` field of a [bid request](https://developers.google.com/authorized-buyers/rtb/downloads/realtime-bidding-proto) does not contain one of the vendor type IDs that were declared or detected for a given creative, and a bid is submitted with that creative, the bid will be filtered before the auction. */
		detectedVendorIds: Option[List[Int]] = None,
	  /** The detected ad technology providers. */
		adTechnologyProviders: Option[Schema.AdTechnologyProviders] = None,
	  /** The last time the creative status was updated. Can be used to filter the response of the creatives.list method. */
		lastStatusUpdate: Option[String] = None,
	  /** Policy compliance of this creative when bidding on Programmatic Guaranteed and Preferred Deals (outside of Russia and China). */
		dealsPolicyCompliance: Option[Schema.PolicyCompliance] = None,
	  /** Policy compliance of this creative when bidding in open auction, private auction, or auction packages (outside of Russia and China). */
		networkPolicyCompliance: Option[Schema.PolicyCompliance] = None,
	  /** Policy compliance of this creative when bidding in Open Bidding (outside of Russia and China). For the list of platform policies, see: https://support.google.com/platformspolicy/answer/3013851. */
		platformPolicyCompliance: Option[Schema.PolicyCompliance] = None,
	  /** The policy compliance of this creative in China. When approved or disapproved, this applies to both deals and open auction in China. When pending review, this creative is allowed to serve for deals but not for open auction. */
		chinaPolicyCompliance: Option[Schema.PolicyCompliance] = None,
	  /** The policy compliance of this creative in Russia. When approved or disapproved, this applies to both deals and open auction in Russia. When pending review, this creative is allowed to serve for deals but not for open auction. */
		russiaPolicyCompliance: Option[Schema.PolicyCompliance] = None,
	  /** Output only. The taxonomy in which the detected_categories field is expressed. */
		detectedCategoriesTaxonomy: Option[Schema.CreativeServingDecision.DetectedCategoriesTaxonomyEnum] = None,
	  /** Output only. IDs of the detected categories, if any. The taxonomy in which the categories are expressed is specified by the detected_categories_taxonomy field. Can be used to filter the response of the creatives.list method. */
		detectedCategories: Option[List[String]] = None
	)
	
	case class AdvertiserAndBrand(
	  /** See https://storage.googleapis.com/adx-rtb-dictionaries/advertisers.txt for the list of possible values. Can be used to filter the response of the creatives.list method. */
		advertiserId: Option[String] = None,
	  /** Advertiser name. Can be used to filter the response of the creatives.list method. */
		advertiserName: Option[String] = None,
	  /** Detected brand ID or zero if no brand has been detected. See https://storage.googleapis.com/adx-rtb-dictionaries/brands.txt for the list of possible values. Can be used to filter the response of the creatives.list method. */
		brandId: Option[String] = None,
	  /** Brand name. Can be used to filter the response of the creatives.list method. */
		brandName: Option[String] = None
	)
	
	case class AdTechnologyProviders(
	  /** The detected [Google Ad Tech Providers (ATP)](https://support.google.com/admanager/answer/9012903) for this creative. See https://storage.googleapis.com/adx-rtb-dictionaries/providers.csv for mapping of provider ID to provided name, a privacy policy URL, and a list of domains which can be attributed to the provider. */
		detectedProviderIds: Option[List[String]] = None,
	  /** The detected IAB Global Vendor List (GVL) IDs for this creative. See the IAB Global Vendor List at https://vendor-list.consensu.org/v2/vendor-list.json for details about the vendors. */
		detectedGvlIds: Option[List[String]] = None,
	  /** Domains of detected unidentified ad technology providers (if any). You must ensure that the creatives used in bids placed for inventory that will serve to EEA or UK users does not contain unidentified ad technology providers. Google reserves the right to filter non-compliant bids. */
		unidentifiedProviderDomains: Option[List[String]] = None
	)
	
	object PolicyCompliance {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNSPECIFIED, PENDING_REVIEW, DISAPPROVED, APPROVED, CERTIFICATE_REQUIRED }
	}
	case class PolicyCompliance(
	  /** Serving status for the given transaction type (for example, open auction, deals) or region (for example, China, Russia). Can be used to filter the response of the creatives.list method. */
		status: Option[Schema.PolicyCompliance.StatusEnum] = None,
	  /** Topics related to the policy compliance for this transaction type (for example, open auction, deals) or region (for example, China, Russia). Topics may be present only if status is DISAPPROVED. */
		topics: Option[List[Schema.PolicyTopicEntry]] = None
	)
	
	case class PolicyTopicEntry(
	  /** Policy topic this entry refers to. For example, "ALCOHOL", "TRADEMARKS_IN_AD_TEXT", or "DESTINATION_NOT_WORKING". The set of possible policy topics is not fixed for a particular API version and may change at any time. Can be used to filter the response of the creatives.list method */
		policyTopic: Option[String] = None,
	  /** URL of the help center article describing this policy topic. */
		helpCenterUrl: Option[String] = None,
	  /** Pieces of evidence associated with this policy topic entry. */
		evidences: Option[List[Schema.PolicyTopicEvidence]] = None,
	  /** Whether or not the policy topic is missing a certificate. Some policy topics require a certificate to unblock serving in some regions. For more information about creative certification, refer to: https://support.google.com/authorizedbuyers/answer/7450776 */
		missingCertificate: Option[Boolean] = None
	)
	
	case class PolicyTopicEvidence(
	  /** The creative's destination URL did not function properly or was incorrectly set up. */
		destinationNotWorking: Option[Schema.DestinationNotWorkingEvidence] = None,
	  /** The creative's destination URL was not crawlable by Google. */
		destinationNotCrawlable: Option[Schema.DestinationNotCrawlableEvidence] = None,
	  /** HTTP calls made by the creative that resulted in policy violations. */
		httpCall: Option[Schema.HttpCallEvidence] = None,
	  /** Number of HTTP calls made by the creative, broken down by domain. */
		domainCall: Option[Schema.DomainCallEvidence] = None,
	  /** Evidence for HTTP cookie-related policy violations. */
		httpCookie: Option[Schema.HttpCookieEvidence] = None,
	  /** URL of the actual landing page. */
		destinationUrl: Option[Schema.DestinationUrlEvidence] = None,
	  /** Total download size and URL-level download size breakdown for resources in a creative. */
		downloadSize: Option[Schema.DownloadSizeEvidence] = None
	)
	
	object DestinationNotWorkingEvidence {
		enum DnsErrorEnum extends Enum[DnsErrorEnum] { case DNS_ERROR_UNSPECIFIED, ERROR_DNS, GOOGLE_CRAWLER_DNS_ISSUE }
		enum InvalidPageEnum extends Enum[InvalidPageEnum] { case INVALID_PAGE_UNSPECIFIED, EMPTY_OR_ERROR_PAGE }
		enum RedirectionErrorEnum extends Enum[RedirectionErrorEnum] { case REDIRECTION_ERROR_UNSPECIFIED, TOO_MANY_REDIRECTS, INVALID_REDIRECT, EMPTY_REDIRECT, REDIRECT_ERROR_UNKNOWN }
		enum UrlRejectedEnum extends Enum[UrlRejectedEnum] { case URL_REJECTED_UNSPECIFIED, BAD_REQUEST, MALFORMED_URL, URL_REJECTED_UNKNOWN }
		enum PlatformEnum extends Enum[PlatformEnum] { case PLATFORM_UNSPECIFIED, PERSONAL_COMPUTER, ANDROID, IOS }
	}
	case class DestinationNotWorkingEvidence(
	  /** DNS lookup errors. */
		dnsError: Option[Schema.DestinationNotWorkingEvidence.DnsErrorEnum] = None,
	  /** HTTP error code (for example, 404 or 5xx) */
		httpError: Option[Int] = None,
	  /** Page was crawled successfully, but was detected as either a page with no content or an error page. */
		invalidPage: Option[Schema.DestinationNotWorkingEvidence.InvalidPageEnum] = None,
	  /** HTTP redirect chain error. */
		redirectionError: Option[Schema.DestinationNotWorkingEvidence.RedirectionErrorEnum] = None,
	  /** Rejected because of malformed URLs or invalid requests. */
		urlRejected: Option[Schema.DestinationNotWorkingEvidence.UrlRejectedEnum] = None,
	  /** The full non-working URL. */
		expandedUrl: Option[String] = None,
	  /** Platform of the non-working URL. */
		platform: Option[Schema.DestinationNotWorkingEvidence.PlatformEnum] = None,
	  /** Approximate time when the ad destination was last checked. */
		lastCheckTime: Option[String] = None
	)
	
	object DestinationNotCrawlableEvidence {
		enum ReasonEnum extends Enum[ReasonEnum] { case REASON_UNSPECIFIED, UNREACHABLE_ROBOTS, TIMEOUT_ROBOTS, ROBOTED_DENIED, UNKNOWN }
	}
	case class DestinationNotCrawlableEvidence(
	  /** Destination URL that was attempted to be crawled. */
		crawledUrl: Option[String] = None,
	  /** Reason of destination not crawlable. */
		reason: Option[Schema.DestinationNotCrawlableEvidence.ReasonEnum] = None,
	  /** Approximate time of the crawl. */
		crawlTime: Option[String] = None
	)
	
	case class HttpCallEvidence(
	  /** URLs of HTTP calls made by the creative. */
		urls: Option[List[String]] = None
	)
	
	case class DomainCallEvidence(
	  /** The total number of HTTP calls made by the creative, including but not limited to the number of calls in the top_http_call_domains. */
		totalHttpCallCount: Option[Int] = None,
	  /** Breakdown of the most frequent domains called through HTTP by the creative. */
		topHttpCallDomains: Option[List[Schema.DomainCalls]] = None
	)
	
	case class DomainCalls(
	  /** The domain name. */
		domain: Option[String] = None,
	  /** Number of HTTP calls made to the domain. */
		httpCallCount: Option[Int] = None
	)
	
	case class HttpCookieEvidence(
	  /** Names of cookies that violate Google policies. For TOO_MANY_COOKIES policy, this will be the cookie names of top domains with the largest number of cookies. For other policies, this will be all the cookie names that violate the policy. */
		cookieNames: Option[List[String]] = None,
	  /** The largest number of cookies set by a creative. If this field is set, cookie_names above will be set to the cookie names of top domains with the largest number of cookies. This field will only be set for TOO_MANY_COOKIES policy. */
		maxCookieCount: Option[Int] = None
	)
	
	case class DestinationUrlEvidence(
	  /** The full landing page URL of the destination. */
		destinationUrl: Option[String] = None
	)
	
	case class DownloadSizeEvidence(
	  /** Total download size (in kilobytes) for all the resources in the creative. */
		totalDownloadSizeKb: Option[Int] = None,
	  /** Download size broken down by URLs with the top download size. */
		topUrlDownloadSizeBreakdowns: Option[List[Schema.UrlDownloadSize]] = None
	)
	
	case class UrlDownloadSize(
	  /** The normalized URL with query parameters and fragment removed. */
		normalizedUrl: Option[String] = None,
	  /** Download size of the URL in kilobytes. */
		downloadSizeKb: Option[Int] = None
	)
	
	case class WatchCreativesRequest(
	
	)
	
	case class WatchCreativesResponse(
	  /** The Pub/Sub topic that will be used to publish creative serving status notifications. This would be of the format `projects/{project_id}/topics/{topic_id}`. */
		topic: Option[String] = None,
	  /** The Pub/Sub subscription that can be used to pull creative status notifications. This would be of the format `projects/{project_id}/subscriptions/{subscription_id}`. Subscription is created with pull delivery. All service accounts belonging to the bidder will have read access to this subscription. Subscriptions that are inactive for more than 90 days will be disabled. Use watchCreatives to re-enable the subscription. */
		subscription: Option[String] = None
	)
	
	case class ListPretargetingConfigsResponse(
	  /** List of pretargeting configurations. */
		pretargetingConfigs: Option[List[Schema.PretargetingConfig]] = None,
	  /** A token which can be passed to a subsequent call to the `ListPretargetingConfigs` method to retrieve the next page of results in ListPretargetingConfigsRequest.pageToken. */
		nextPageToken: Option[String] = None
	)
	
	object PretargetingConfig {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, SUSPENDED }
		enum IncludedFormatsEnum extends Enum[IncludedFormatsEnum] { case CREATIVE_FORMAT_UNSPECIFIED, HTML, VAST, NATIVE }
		enum InterstitialTargetingEnum extends Enum[InterstitialTargetingEnum] { case INTERSTITIAL_TARGETING_UNSPECIFIED, ONLY_INTERSTITIAL_REQUESTS, ONLY_NON_INTERSTITIAL_REQUESTS }
		enum AllowedUserTargetingModesEnum extends Enum[AllowedUserTargetingModesEnum] { case USER_TARGETING_MODE_UNSPECIFIED, REMARKETING_ADS, INTEREST_BASED_TARGETING }
		enum IncludedUserIdTypesEnum extends Enum[IncludedUserIdTypesEnum] { case USER_ID_TYPE_UNSPECIFIED, HOSTED_MATCH_DATA, GOOGLE_COOKIE, DEVICE_ID, PUBLISHER_PROVIDED_ID, PUBLISHER_FIRST_PARTY_ID }
		enum IncludedPlatformsEnum extends Enum[IncludedPlatformsEnum] { case PLATFORM_UNSPECIFIED, PERSONAL_COMPUTER, PHONE, TABLET, CONNECTED_TV }
		enum IncludedEnvironmentsEnum extends Enum[IncludedEnvironmentsEnum] { case ENVIRONMENT_UNSPECIFIED, APP, WEB }
	}
	case class PretargetingConfig(
	  /** Output only. Name of the pretargeting configuration that must follow the pattern `bidders/{bidder_account_id}/pretargetingConfigs/{config_id}` */
		name: Option[String] = None,
	  /** The diplay name associated with this configuration. This name must be unique among all the pretargeting configurations a bidder has. */
		displayName: Option[String] = None,
	  /** Output only. The identifier that corresponds to this pretargeting configuration that helps buyers track and attribute their spend across their own arbitrary divisions. If a bid request matches more than one configuration, the buyer chooses which billing_id to attribute each of their bids. */
		billingId: Option[String] = None,
	  /** Output only. The state of this pretargeting configuration. */
		state: Option[Schema.PretargetingConfig.StateEnum] = None,
	  /** Creative formats included by this configuration. Only bid requests eligible for at least one of the specified creative formats will be sent. An unset value will allow all bid requests to be sent, regardless of format. */
		includedFormats: Option[List[Schema.PretargetingConfig.IncludedFormatsEnum]] = None,
	  /** The maximum QPS threshold for this configuration. The bidder should receive no more than this number of bid requests matching this configuration per second across all their bidding endpoints among all trading locations. Further information available at https://developers.google.com/authorized-buyers/rtb/peer-guide */
		maximumQps: Option[String] = None,
	  /** The geos included or excluded in this configuration defined in https://storage.googleapis.com/adx-rtb-dictionaries/geo-table.csv */
		geoTargeting: Option[Schema.NumericTargetingDimension] = None,
	  /** Output only. Existing included or excluded geos that are invalid. Previously targeted geos may become invalid due to privacy restrictions. */
		invalidGeoIds: Option[List[String]] = None,
	  /** The remarketing lists included or excluded in this configuration as defined in UserList. */
		userListTargeting: Option[Schema.NumericTargetingDimension] = None,
	  /** The interstitial targeting specified for this configuration. The unset value will allow bid requests to be sent regardless of whether they are for interstitials or not. */
		interstitialTargeting: Option[Schema.PretargetingConfig.InterstitialTargetingEnum] = None,
	  /** Targeting modes included by this configuration. A bid request must allow all the specified targeting modes. An unset value allows all bid requests to be sent, regardless of which targeting modes they allow. */
		allowedUserTargetingModes: Option[List[Schema.PretargetingConfig.AllowedUserTargetingModesEnum]] = None,
	  /** The sensitive content category label IDs excluded in this configuration. Bid requests for inventory with any of the specified content label IDs will not be sent. Refer to this file https://storage.googleapis.com/adx-rtb-dictionaries/content-labels.txt for category IDs. */
		excludedContentLabelIds: Option[List[String]] = None,
	  /** User identifier types included in this configuration. At least one of the user identifier types specified in this list must be available for the bid request to be sent. */
		includedUserIdTypes: Option[List[Schema.PretargetingConfig.IncludedUserIdTypesEnum]] = None,
	  /** The languages included in this configuration, represented by their language code. See https://developers.google.com/adwords/api/docs/appendix/languagecodes. */
		includedLanguages: Option[List[String]] = None,
	  /** The mobile operating systems included in this configuration as defined in https://storage.googleapis.com/adx-rtb-dictionaries/mobile-os.csv */
		includedMobileOperatingSystemIds: Option[List[String]] = None,
	  /** The verticals included or excluded in this configuration as defined in https://developers.google.com/authorized-buyers/rtb/downloads/publisher-verticals */
		verticalTargeting: Option[Schema.NumericTargetingDimension] = None,
	  /** The platforms included by this configration. Bid requests for devices with the specified platform types will be sent. An unset value allows all bid requests to be sent, regardless of platform. */
		includedPlatforms: Option[List[Schema.PretargetingConfig.IncludedPlatformsEnum]] = None,
	  /** Creative dimensions included by this configuration. Only bid requests eligible for at least one of the specified creative dimensions will be sent. An unset value allows all bid requests to be sent, regardless of creative dimension. */
		includedCreativeDimensions: Option[List[Schema.CreativeDimensions]] = None,
	  /** Environments that are being included. Bid requests will not be sent for a given environment if it is not included. Further restrictions can be applied to included environments to target only a subset of its inventory. An unset value includes all environments. */
		includedEnvironments: Option[List[Schema.PretargetingConfig.IncludedEnvironmentsEnum]] = None,
	  /** Targeting on a subset of site inventory. If WEB is listed in included_environments, the specified targeting is applied. A maximum of 50,000 site URLs can be targeted. An unset value for targeting allows all web-based bid requests to be sent. Sites can either be targeting positively (bid requests will be sent only if the destination site is listed in the targeting dimension) or negatively (bid requests will be sent only if the destination site is not listed in the pretargeting configuration). */
		webTargeting: Option[Schema.StringTargetingDimension] = None,
	  /** Targeting on a subset of app inventory. If APP is listed in targeted_environments, the specified targeting is applied. A maximum of 30,000 app IDs can be targeted. An unset value for targeting allows all app-based bid requests to be sent. Apps can either be targeting positively (bid requests will be sent only if the destination app is listed in the targeting dimension) or negatively (bid requests will be sent only if the destination app is not listed in the targeting dimension). */
		appTargeting: Option[Schema.AppTargeting] = None,
	  /** Targeting on a subset of publisher inventory. Publishers can either be targeted positively (bid requests will be sent only if the publisher is listed in the targeting dimension) or negatively (bid requests will be sent only if the publisher is not listed in the targeting dimension). A maximum of 10,000 publisher IDs can be targeted. Publisher IDs are found in [ads.txt](https://iabtechlab.com/ads-txt/) / [app-ads.txt](https://iabtechlab.com/app-ads-txt/) and in bid requests in the `BidRequest.publisher_id` field on the [Google RTB protocol](https://developers.google.com/authorized-buyers/rtb/downloads/realtime-bidding-proto) or the `BidRequest.site.publisher.id` / `BidRequest.app.publisher.id` field on the [OpenRTB protocol](https://developers.google.com/authorized-buyers/rtb/downloads/openrtb-adx-proto). Publisher IDs will be returned in the order that they were entered. */
		publisherTargeting: Option[Schema.StringTargetingDimension] = None,
	  /** The targeted minimum viewability decile, ranging in values [0, 10]. A value of 5 means that the configuration will only match adslots for which we predict at least 50% viewability. Values > 10 will be rounded down to 10. An unset value or a value of 0 indicates that bid requests will be sent regardless of viewability. */
		minimumViewabilityDecile: Option[Int] = None
	)
	
	case class NumericTargetingDimension(
	  /** The IDs included in a configuration. */
		includedIds: Option[List[String]] = None,
	  /** The IDs excluded in a configuration. */
		excludedIds: Option[List[String]] = None
	)
	
	case class CreativeDimensions(
	  /** The width of the creative in pixels. */
		width: Option[String] = None,
	  /** The height of the creative in pixels. */
		height: Option[String] = None
	)
	
	object StringTargetingDimension {
		enum TargetingModeEnum extends Enum[TargetingModeEnum] { case TARGETING_MODE_UNSPECIFIED, INCLUSIVE, EXCLUSIVE }
	}
	case class StringTargetingDimension(
	  /** How the items in this list should be targeted. */
		targetingMode: Option[Schema.StringTargetingDimension.TargetingModeEnum] = None,
	  /** The values specified. */
		values: Option[List[String]] = None
	)
	
	case class AppTargeting(
	  /** Targeted app IDs. App IDs can refer to those found in an app store or ones that are not published in an app store. A maximum of 30,000 app IDs can be targeted. */
		mobileAppTargeting: Option[Schema.StringTargetingDimension] = None,
	  /** Lists of included and excluded mobile app categories as defined in https://developers.google.com/adwords/api/docs/appendix/mobileappcategories.csv. */
		mobileAppCategoryTargeting: Option[Schema.NumericTargetingDimension] = None
	)
	
	case class Empty(
	
	)
	
	case class ActivatePretargetingConfigRequest(
	
	)
	
	case class SuspendPretargetingConfigRequest(
	
	)
	
	object AddTargetedSitesRequest {
		enum TargetingModeEnum extends Enum[TargetingModeEnum] { case TARGETING_MODE_UNSPECIFIED, INCLUSIVE, EXCLUSIVE }
	}
	case class AddTargetedSitesRequest(
	  /** A list of site URLs to target in the pretargeting configuration. These values will be added to the list of targeted URLs in PretargetingConfig.webTargeting.values. */
		sites: Option[List[String]] = None,
	  /** Required. The targeting mode that should be applied to the list of site URLs. If there are existing targeted sites, must be equal to the existing PretargetingConfig.webTargeting.targetingMode or a 400 bad request error will be returned. */
		targetingMode: Option[Schema.AddTargetedSitesRequest.TargetingModeEnum] = None
	)
	
	case class RemoveTargetedSitesRequest(
	  /** A list of site URLs to stop targeting in the pretargeting configuration. These values will be removed from the list of targeted URLs in PretargetingConfig.webTargeting.values. */
		sites: Option[List[String]] = None
	)
	
	object AddTargetedAppsRequest {
		enum TargetingModeEnum extends Enum[TargetingModeEnum] { case TARGETING_MODE_UNSPECIFIED, INCLUSIVE, EXCLUSIVE }
	}
	case class AddTargetedAppsRequest(
	  /** A list of app IDs to target in the pretargeting configuration. These values will be added to the list of targeted app IDs in PretargetingConfig.appTargeting.mobileAppTargeting.values. */
		appIds: Option[List[String]] = None,
	  /** Required. The targeting mode that should be applied to the list of app IDs. If there are existing targeted app IDs, must be equal to the existing PretargetingConfig.appTargeting.mobileAppTargeting.targetingMode or a 400 bad request error will be returned. */
		targetingMode: Option[Schema.AddTargetedAppsRequest.TargetingModeEnum] = None
	)
	
	case class RemoveTargetedAppsRequest(
	  /** A list of app IDs to stop targeting in the pretargeting configuration. These values will be removed from the list of targeted app IDs in PretargetingConfig.appTargeting.mobileAppTargeting.values. */
		appIds: Option[List[String]] = None
	)
	
	object AddTargetedPublishersRequest {
		enum TargetingModeEnum extends Enum[TargetingModeEnum] { case TARGETING_MODE_UNSPECIFIED, INCLUSIVE, EXCLUSIVE }
	}
	case class AddTargetedPublishersRequest(
	  /** A list of publisher IDs to target in the pretargeting configuration. These values will be added to the list of targeted publisher IDs in PretargetingConfig.publisherTargeting.values. Publishers are identified by their publisher ID from ads.txt / app-ads.txt. See https://iabtechlab.com/ads-txt/ and https://iabtechlab.com/app-ads-txt/ for more details. */
		publisherIds: Option[List[String]] = None,
	  /** Required. The targeting mode that should be applied to the list of publisher IDs. If are existing publisher IDs, must be equal to the existing PretargetingConfig.publisherTargeting.targetingMode or a 400 bad request error will be returned. */
		targetingMode: Option[Schema.AddTargetedPublishersRequest.TargetingModeEnum] = None
	)
	
	case class RemoveTargetedPublishersRequest(
	  /** A list of publisher IDs to stop targeting in the pretargeting configuration. These values will be removed from the list of targeted publisher IDs in PretargetingConfig.publisherTargeting.values. Publishers are identified by their publisher ID from ads.txt / app-ads.txt. See https://iabtechlab.com/ads-txt/ and https://iabtechlab.com/app-ads-txt/ for more details. */
		publisherIds: Option[List[String]] = None
	)
	
	case class ListPublisherConnectionsResponse(
	  /** The list of publisher connections. */
		publisherConnections: Option[List[Schema.PublisherConnection]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListPublisherConnectionsRequest.pageToken field in the subsequent call to the `ListPublisherConnections` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object PublisherConnection {
		enum PublisherPlatformEnum extends Enum[PublisherPlatformEnum] { case PUBLISHER_PLATFORM_UNSPECIFIED, GOOGLE_AD_MANAGER, ADMOB }
		enum BiddingStateEnum extends Enum[BiddingStateEnum] { case STATE_UNSPECIFIED, PENDING, REJECTED, APPROVED }
	}
	case class PublisherConnection(
	  /** Output only. Name of the publisher connection. This follows the pattern `bidders/{bidder}/publisherConnections/{publisher}`, where `{bidder}` represents the account ID of the bidder, and `{publisher}` is the ads.txt/app-ads.txt publisher ID. */
		name: Option[String] = None,
	  /** Output only. Whether the publisher is an Ad Manager or AdMob publisher. */
		publisherPlatform: Option[Schema.PublisherConnection.PublisherPlatformEnum] = None,
	  /** Output only. Publisher display name. */
		displayName: Option[String] = None,
	  /** Whether the publisher has been approved by the bidder. */
		biddingState: Option[Schema.PublisherConnection.BiddingStateEnum] = None,
	  /** Output only. The time at which the publisher initiated a connection with the bidder (irrespective of if or when the bidder approves it). This is subsequently updated if the publisher revokes and re-initiates the connection. */
		createTime: Option[String] = None
	)
	
	case class BatchApprovePublisherConnectionsRequest(
	  /** Required. The names of the publishers with which connections will be approved. In the pattern `bidders/{bidder}/publisherConnections/{publisher}` where `{bidder}` is the account ID of the bidder, and `{publisher}` is the ads.txt/app-ads.txt publisher ID. */
		names: Option[List[String]] = None
	)
	
	case class BatchApprovePublisherConnectionsResponse(
	  /** The publisher connections that have been approved. */
		publisherConnections: Option[List[Schema.PublisherConnection]] = None
	)
	
	case class BatchRejectPublisherConnectionsRequest(
	  /** Required. The names of the publishers with whom connection will be rejected. In the pattern `bidders/{bidder}/publisherConnections/{publisher}` where `{bidder}` is the account ID of the bidder, and `{publisher}` is the ads.txt/app-ads.txt publisher ID. */
		names: Option[List[String]] = None
	)
	
	case class BatchRejectPublisherConnectionsResponse(
	  /** The publisher connections that have been rejected. */
		publisherConnections: Option[List[Schema.PublisherConnection]] = None
	)
	
	object UserList {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNSPECIFIED, OPEN, CLOSED }
	}
	case class UserList(
	  /** Output only. Name of the user list that must follow the pattern `buyers/{buyer}/userLists/{user_list}`, where `{buyer}` represents the account ID of the buyer who owns the user list. For a bidder accessing user lists on behalf of a child seat buyer, `{buyer}` represents the account ID of the child seat buyer. `{user_list}` is an int64 identifier assigned by Google to uniquely identify a user list. */
		name: Option[String] = None,
	  /** Required. Display name of the user list. This must be unique across all user lists for a given account. */
		displayName: Option[String] = None,
	  /** The description for the user list. */
		description: Option[String] = None,
	  /** Output only. The status of the user list. A new user list starts out as open. */
		status: Option[Schema.UserList.StatusEnum] = None,
	  /** Required. Deprecated. This will be removed in October 2023. For more information, see the release notes: https://developers.google.com/authorized-buyers/apis/relnotes#real-time-bidding-api The URL restriction for the user list. */
		urlRestriction: Option[Schema.UrlRestriction] = None,
	  /** Required. The number of days a user's cookie stays on the user list. The field must be between 0 and 540 inclusive. */
		membershipDurationDays: Option[String] = None
	)
	
	object UrlRestriction {
		enum RestrictionTypeEnum extends Enum[RestrictionTypeEnum] { case RESTRICTION_TYPE_UNSPECIFIED, CONTAINS, EQUALS, STARTS_WITH, ENDS_WITH, DOES_NOT_EQUAL, DOES_NOT_CONTAIN, DOES_NOT_START_WITH, DOES_NOT_END_WITH }
	}
	case class UrlRestriction(
	  /** Required. The URL to use for applying the restriction on the user list. */
		url: Option[String] = None,
	  /** The restriction type for the specified URL. */
		restrictionType: Option[Schema.UrlRestriction.RestrictionTypeEnum] = None,
	  /** Start date (if specified) of the URL restriction. */
		startDate: Option[Schema.Date] = None,
	  /** End date (if specified) of the URL restriction. End date should be later than the start date for the date range to be valid. */
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
	
	case class ListUserListsResponse(
	  /** List of user lists from the search. */
		userLists: Option[List[Schema.UserList]] = None,
	  /** The continuation page token to send back to the server in a subsequent request. Due to a currently known issue, it is recommended that the caller keep invoking the list method until the time a next page token is not returned, even if the result set is empty. */
		nextPageToken: Option[String] = None
	)
	
	case class OpenUserListRequest(
	
	)
	
	case class CloseUserListRequest(
	
	)
	
	case class GetRemarketingTagResponse(
	  /** An HTML tag that can be placed on the advertiser's page to add users to a user list. For more information and code samples on using snippets on your website, refer to [Tag your site for remarketing](https://support.google.com/google-ads/answer/2476688). */
		snippet: Option[String] = None
	)
}
