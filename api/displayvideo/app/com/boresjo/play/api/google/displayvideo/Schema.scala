package com.boresjo.play.api.google.displayvideo

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object AdGroupAd {
		enum EntityStatusEnum extends Enum[EntityStatusEnum] { case ENTITY_STATUS_UNSPECIFIED, ENTITY_STATUS_ACTIVE, ENTITY_STATUS_ARCHIVED, ENTITY_STATUS_DRAFT, ENTITY_STATUS_PAUSED, ENTITY_STATUS_SCHEDULED_FOR_DELETION }
	}
	case class AdGroupAd(
	  /** The resource name of the ad. */
		name: Option[String] = None,
	  /** The unique ID of the advertiser the ad belongs to. */
		advertiserId: Option[String] = None,
	  /** The unique ID of the ad. Assigned by the system. */
		adGroupAdId: Option[String] = None,
	  /** The unique ID of the ad group that the ad belongs to. */
		adGroupId: Option[String] = None,
	  /** The display name of the ad. Must be UTF-8 encoded with a maximum size of 255 bytes. */
		displayName: Option[String] = None,
	  /** The entity status of the ad. */
		entityStatus: Option[Schema.AdGroupAd.EntityStatusEnum] = None,
	  /** Details of an ad sourced from a Display & Video 360 creative. */
		displayVideoSourceAd: Option[Schema.DisplayVideoSourceAd] = None,
	  /** Details of an [ad served on the YouTube Home feed](//support.google.com/google-ads/answer/9709826). */
		mastheadAd: Option[Schema.MastheadAd] = None,
	  /** Details of an [in-stream ad skippable after 5 seconds](//support.google.com/displayvideo/answer/6274216), used for brand awareness or reach marketing objectives. */
		inStreamAd: Option[Schema.InStreamAd] = None,
	  /** Details of a [non-skippable short in-stream video ad](//support.google.com/displayvideo/answer/6274216), between 6 and 15 seconds, used for reach marketing objectives. */
		nonSkippableAd: Option[Schema.NonSkippableAd] = None,
	  /** Details of a [non-skippable short video ad](//support.google.com/displayvideo/answer/6274216), equal to or less than 6 seconds, used for reach. */
		bumperAd: Option[Schema.BumperAd] = None,
	  /** Details of an [audio ad](//support.google.com/displayvideo/answer/6274216) used for reach marketing objectives. */
		audioAd: Option[Schema.AudioAd] = None,
	  /** Details of an [ad promoting a video](//support.google.com/displayvideo/answer/6274216) that shows in places of discovery. */
		videoDiscoverAd: Option[Schema.VideoDiscoveryAd] = None,
	  /** Details of an [ad used in a video action campaign](//support.google.com/google-ads/answer/10147229) to drive actions to the business, service or product. */
		videoPerformanceAd: Option[Schema.VideoPerformanceAd] = None,
	  /** List of URLs used by the ad. */
		adUrls: Option[List[Schema.AdUrl]] = None
	)
	
	case class DisplayVideoSourceAd(
	  /** The ID of the source creative. */
		creativeId: Option[String] = None
	)
	
	object MastheadAd {
		enum VideoAspectRatioEnum extends Enum[VideoAspectRatioEnum] { case VIDEO_ASPECT_RATIO_UNSPECIFIED, VIDEO_ASPECT_RATIO_WIDESCREEN, VIDEO_ASPECT_RATIO_FIXED_16_9 }
	}
	case class MastheadAd(
	  /** The YouTube video used by the ad. */
		video: Option[Schema.YoutubeVideoDetails] = None,
	  /** The headline of the ad. */
		headline: Option[String] = None,
	  /** The description of the ad. */
		description: Option[String] = None,
	  /** The aspect ratio of the autoplaying YouTube video on the Masthead. */
		videoAspectRatio: Option[Schema.MastheadAd.VideoAspectRatioEnum] = None,
	  /** The amount of time in milliseconds after which the video will start to play. */
		autoplayVideoStartMillisecond: Option[String] = None,
	  /** The duration of time the video will autoplay. */
		autoplayVideoDuration: Option[String] = None,
	  /** The text on the call-to-action button. */
		callToActionButtonLabel: Option[String] = None,
	  /** The destination URL for the call-to-action button. */
		callToActionFinalUrl: Option[String] = None,
	  /** The tracking URL for the call-to-action button. */
		callToActionTrackingUrl: Option[String] = None,
	  /** Whether to show a background or banner that appears at the top of a YouTube page. */
		showChannelArt: Option[Boolean] = None,
	  /** The videos that appear next to the Masthead Ad on desktop. Can be no more than two. */
		companionYoutubeVideos: Option[List[Schema.YoutubeVideoDetails]] = None
	)
	
	object YoutubeVideoDetails {
		enum UnavailableReasonEnum extends Enum[UnavailableReasonEnum] { case VIDEO_UNAVAILABLE_REASON_UNSPECIFIED, VIDEO_UNAVAILABLE_REASON_PRIVATE, VIDEO_UNAVAILABLE_REASON_DELETED }
	}
	case class YoutubeVideoDetails(
	  /** The YouTube video ID which can be searched on YouTube webpage. */
		id: Option[String] = None,
	  /** The reason why the video data is not available. */
		unavailableReason: Option[Schema.YoutubeVideoDetails.UnavailableReasonEnum] = None
	)
	
	case class InStreamAd(
	  /** Common ad attributes. */
		commonInStreamAttribute: Option[Schema.CommonInStreamAttribute] = None,
	  /** The custom parameters to pass custom values to tracking URL template. */
		customParameters: Option[Map[String, String]] = None
	)
	
	case class CommonInStreamAttribute(
	  /** The webpage address that appears with the ad. */
		displayUrl: Option[String] = None,
	  /** The URL address of the webpage that people reach after they click the ad. */
		finalUrl: Option[String] = None,
	  /** The URL address loaded in the background for tracking purposes. */
		trackingUrl: Option[String] = None,
	  /** The text on the call-to-action button. */
		actionButtonLabel: Option[String] = None,
	  /** The headline of the call-to-action banner. */
		actionHeadline: Option[String] = None,
	  /** The YouTube video of the ad. */
		video: Option[Schema.YoutubeVideoDetails] = None,
	  /** The image which shows next to the video ad. */
		companionBanner: Option[Schema.ImageAsset] = None
	)
	
	case class ImageAsset(
	  /** MIME type of the image asset. */
		mimeType: Option[String] = None,
	  /** Metadata for this image at its original size. */
		fullSize: Option[Schema.Dimensions] = None,
	  /** File size of the image asset in bytes. */
		fileSize: Option[String] = None
	)
	
	case class Dimensions(
	  /** The width in pixels. */
		widthPixels: Option[Int] = None,
	  /** The height in pixels. */
		heightPixels: Option[Int] = None
	)
	
	case class NonSkippableAd(
	  /** Common ad attributes. */
		commonInStreamAttribute: Option[Schema.CommonInStreamAttribute] = None,
	  /** The custom parameters to pass custom values to tracking URL template. */
		customParameters: Option[Map[String, String]] = None
	)
	
	case class BumperAd(
	  /** Common ad attributes. */
		commonInStreamAttribute: Option[Schema.CommonInStreamAttribute] = None
	)
	
	case class AudioAd(
	  /** The webpage address that appears with the ad. */
		displayUrl: Option[String] = None,
	  /** The URL address of the webpage that people reach after they click the ad. */
		finalUrl: Option[String] = None,
	  /** The URL address loaded in the background for tracking purposes. */
		trackingUrl: Option[String] = None,
	  /** The YouTube video of the ad. */
		video: Option[Schema.YoutubeVideoDetails] = None
	)
	
	object VideoDiscoveryAd {
		enum ThumbnailEnum extends Enum[ThumbnailEnum] { case THUMBNAIL_UNSPECIFIED, THUMBNAIL_DEFAULT, THUMBNAIL_1, THUMBNAIL_2, THUMBNAIL_3 }
	}
	case class VideoDiscoveryAd(
	  /** The headline of ad. */
		headline: Option[String] = None,
	  /** First text line for the ad. */
		description1: Option[String] = None,
	  /** Second text line for the ad. */
		description2: Option[String] = None,
	  /** The YouTube video the ad promotes. */
		video: Option[Schema.YoutubeVideoDetails] = None,
	  /** Thumbnail image used in the ad. */
		thumbnail: Option[Schema.VideoDiscoveryAd.ThumbnailEnum] = None
	)
	
	case class VideoPerformanceAd(
	  /** The URL address of the webpage that people reach after they click the ad. */
		finalUrl: Option[String] = None,
	  /** The URL address loaded in the background for tracking purposes. */
		trackingUrl: Option[String] = None,
	  /** The list of text assets shown on the call-to-action button. */
		actionButtonLabels: Option[List[String]] = None,
	  /** The list of headlines shown on the call-to-action banner. */
		headlines: Option[List[String]] = None,
	  /** The list of lone headlines shown on the call-to-action banner. */
		longHeadlines: Option[List[String]] = None,
	  /** The list of descriptions shown on the call-to-action banner. */
		descriptions: Option[List[String]] = None,
	  /** The first piece after the domain in the display URL. */
		displayUrlBreadcrumb1: Option[String] = None,
	  /** The second piece after the domain in the display URL. */
		displayUrlBreadcrumb2: Option[String] = None,
	  /** The domain of the display URL. */
		domain: Option[String] = None,
	  /** The list of YouTube video assets used by this ad. */
		videos: Option[List[Schema.YoutubeVideoDetails]] = None,
	  /** The custom parameters to pass custom values to tracking URL template. */
		customParameters: Option[Map[String, String]] = None,
	  /** The list of companion banners used by this ad. */
		companionBanners: Option[List[Schema.ImageAsset]] = None
	)
	
	object AdUrl {
		enum TypeEnum extends Enum[TypeEnum] { case AD_URL_TYPE_UNSPECIFIED, AD_URL_TYPE_BEACON_IMPRESSION, AD_URL_TYPE_BEACON_EXPANDABLE_DCM_IMPRESSION, AD_URL_TYPE_BEACON_CLICK, AD_URL_TYPE_BEACON_SKIP }
	}
	case class AdUrl(
	  /** The type of the Ad URL. */
		`type`: Option[Schema.AdUrl.TypeEnum] = None,
	  /** The URL string value. */
		url: Option[String] = None
	)
	
	case class ListAdGroupAdsResponse(
	  /** The list of ad group ads. This list will be absent if empty. */
		adGroupAds: Option[List[Schema.AdGroupAd]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListAdGroupAds` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object AssignedTargetingOption {
		enum TargetingTypeEnum extends Enum[TargetingTypeEnum] { case TARGETING_TYPE_UNSPECIFIED, TARGETING_TYPE_CHANNEL, TARGETING_TYPE_APP_CATEGORY, TARGETING_TYPE_APP, TARGETING_TYPE_URL, TARGETING_TYPE_DAY_AND_TIME, TARGETING_TYPE_AGE_RANGE, TARGETING_TYPE_REGIONAL_LOCATION_LIST, TARGETING_TYPE_PROXIMITY_LOCATION_LIST, TARGETING_TYPE_GENDER, TARGETING_TYPE_VIDEO_PLAYER_SIZE, TARGETING_TYPE_USER_REWARDED_CONTENT, TARGETING_TYPE_PARENTAL_STATUS, TARGETING_TYPE_CONTENT_INSTREAM_POSITION, TARGETING_TYPE_CONTENT_OUTSTREAM_POSITION, TARGETING_TYPE_DEVICE_TYPE, TARGETING_TYPE_AUDIENCE_GROUP, TARGETING_TYPE_BROWSER, TARGETING_TYPE_HOUSEHOLD_INCOME, TARGETING_TYPE_ON_SCREEN_POSITION, TARGETING_TYPE_THIRD_PARTY_VERIFIER, TARGETING_TYPE_DIGITAL_CONTENT_LABEL_EXCLUSION, TARGETING_TYPE_SENSITIVE_CATEGORY_EXCLUSION, TARGETING_TYPE_ENVIRONMENT, TARGETING_TYPE_CARRIER_AND_ISP, TARGETING_TYPE_OPERATING_SYSTEM, TARGETING_TYPE_DEVICE_MAKE_MODEL, TARGETING_TYPE_KEYWORD, TARGETING_TYPE_NEGATIVE_KEYWORD_LIST, TARGETING_TYPE_VIEWABILITY, TARGETING_TYPE_CATEGORY, TARGETING_TYPE_INVENTORY_SOURCE, TARGETING_TYPE_LANGUAGE, TARGETING_TYPE_AUTHORIZED_SELLER_STATUS, TARGETING_TYPE_GEO_REGION, TARGETING_TYPE_INVENTORY_SOURCE_GROUP, TARGETING_TYPE_EXCHANGE, TARGETING_TYPE_SUB_EXCHANGE, TARGETING_TYPE_POI, TARGETING_TYPE_BUSINESS_CHAIN, TARGETING_TYPE_CONTENT_DURATION, TARGETING_TYPE_CONTENT_STREAM_TYPE, TARGETING_TYPE_NATIVE_CONTENT_POSITION, TARGETING_TYPE_OMID, TARGETING_TYPE_AUDIO_CONTENT_TYPE, TARGETING_TYPE_CONTENT_GENRE, TARGETING_TYPE_YOUTUBE_VIDEO, TARGETING_TYPE_YOUTUBE_CHANNEL, TARGETING_TYPE_SESSION_POSITION }
		enum InheritanceEnum extends Enum[InheritanceEnum] { case INHERITANCE_UNSPECIFIED, NOT_INHERITED, INHERITED_FROM_PARTNER, INHERITED_FROM_ADVERTISER }
	}
	case class AssignedTargetingOption(
	  /** Output only. The resource name for this assigned targeting option. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the assigned targeting option. The ID is only unique within a given resource and targeting type. It may be reused in other contexts. */
		assignedTargetingOptionId: Option[String] = None,
	  /** Output only. An alias for the assigned_targeting_option_id. This value can be used in place of `assignedTargetingOptionId` when retrieving or deleting existing targeting. This field will only be supported for all assigned targeting options of the following targeting types: &#42; `TARGETING_TYPE_AGE_RANGE` &#42; `TARGETING_TYPE_DEVICE_TYPE` &#42; `TARGETING_TYPE_DIGITAL_CONTENT_LABEL_EXCLUSION` &#42; `TARGETING_TYPE_ENVIRONMENT` &#42; `TARGETING_TYPE_EXCHANGE` &#42; `TARGETING_TYPE_GENDER` &#42; `TARGETING_TYPE_HOUSEHOLD_INCOME` &#42; `TARGETING_TYPE_NATIVE_CONTENT_POSITION` &#42; `TARGETING_TYPE_OMID` &#42; `TARGETING_TYPE_PARENTAL_STATUS` &#42; `TARGETING_TYPE_SENSITIVE_CATEGORY_EXCLUSION` &#42; `TARGETING_TYPE_VIDEO_PLAYER_SIZE` &#42; `TARGETING_TYPE_VIEWABILITY` This field is also supported for line item assigned targeting options of the following targeting types: &#42; `TARGETING_TYPE_CONTENT_INSTREAM_POSITION` &#42; `TARGETING_TYPE_CONTENT_OUTSTREAM_POSITION` */
		assignedTargetingOptionIdAlias: Option[String] = None,
	  /** Output only. Identifies the type of this assigned targeting option. */
		targetingType: Option[Schema.AssignedTargetingOption.TargetingTypeEnum] = None,
	  /** Output only. The inheritance status of the assigned targeting option. */
		inheritance: Option[Schema.AssignedTargetingOption.InheritanceEnum] = None,
	  /** Channel details. This field will be populated when the targeting_type is `TARGETING_TYPE_CHANNEL`. */
		channelDetails: Option[Schema.ChannelAssignedTargetingOptionDetails] = None,
	  /** App category details. This field will be populated when the targeting_type is `TARGETING_TYPE_APP_CATEGORY`. */
		appCategoryDetails: Option[Schema.AppCategoryAssignedTargetingOptionDetails] = None,
	  /** App details. This field will be populated when the targeting_type is `TARGETING_TYPE_APP`. */
		appDetails: Option[Schema.AppAssignedTargetingOptionDetails] = None,
	  /** URL details. This field will be populated when the targeting_type is `TARGETING_TYPE_URL`. */
		urlDetails: Option[Schema.UrlAssignedTargetingOptionDetails] = None,
	  /** Day and time details. This field will be populated when the targeting_type is `TARGETING_TYPE_DAY_AND_TIME`. */
		dayAndTimeDetails: Option[Schema.DayAndTimeAssignedTargetingOptionDetails] = None,
	  /** Age range details. This field will be populated when the targeting_type is `TARGETING_TYPE_AGE_RANGE`. */
		ageRangeDetails: Option[Schema.AgeRangeAssignedTargetingOptionDetails] = None,
	  /** Regional location list details. This field will be populated when the targeting_type is `TARGETING_TYPE_REGIONAL_LOCATION_LIST`. */
		regionalLocationListDetails: Option[Schema.RegionalLocationListAssignedTargetingOptionDetails] = None,
	  /** Proximity location list details. This field will be populated when the targeting_type is `TARGETING_TYPE_PROXIMITY_LOCATION_LIST`. */
		proximityLocationListDetails: Option[Schema.ProximityLocationListAssignedTargetingOptionDetails] = None,
	  /** Gender details. This field will be populated when the targeting_type is `TARGETING_TYPE_GENDER`. */
		genderDetails: Option[Schema.GenderAssignedTargetingOptionDetails] = None,
	  /** Video player size details. This field will be populated when the targeting_type is `TARGETING_TYPE_VIDEO_PLAYER_SIZE`. */
		videoPlayerSizeDetails: Option[Schema.VideoPlayerSizeAssignedTargetingOptionDetails] = None,
	  /** User rewarded content details. This field will be populated when the targeting_type is `TARGETING_TYPE_USER_REWARDED_CONTENT`. */
		userRewardedContentDetails: Option[Schema.UserRewardedContentAssignedTargetingOptionDetails] = None,
	  /** Parental status details. This field will be populated when the targeting_type is `TARGETING_TYPE_PARENTAL_STATUS`. */
		parentalStatusDetails: Option[Schema.ParentalStatusAssignedTargetingOptionDetails] = None,
	  /** Content instream position details. This field will be populated when the targeting_type is `TARGETING_TYPE_CONTENT_INSTREAM_POSITION`. */
		contentInstreamPositionDetails: Option[Schema.ContentInstreamPositionAssignedTargetingOptionDetails] = None,
	  /** Content outstream position details. This field will be populated when the targeting_type is `TARGETING_TYPE_CONTENT_OUTSTREAM_POSITION`. */
		contentOutstreamPositionDetails: Option[Schema.ContentOutstreamPositionAssignedTargetingOptionDetails] = None,
	  /** Device Type details. This field will be populated when the targeting_type is `TARGETING_TYPE_DEVICE_TYPE`. */
		deviceTypeDetails: Option[Schema.DeviceTypeAssignedTargetingOptionDetails] = None,
	  /** Audience targeting details. This field will be populated when the targeting_type is `TARGETING_TYPE_AUDIENCE_GROUP`. You can only target one audience group option per resource. */
		audienceGroupDetails: Option[Schema.AudienceGroupAssignedTargetingOptionDetails] = None,
	  /** Browser details. This field will be populated when the targeting_type is `TARGETING_TYPE_BROWSER`. */
		browserDetails: Option[Schema.BrowserAssignedTargetingOptionDetails] = None,
	  /** Household income details. This field will be populated when the targeting_type is `TARGETING_TYPE_HOUSEHOLD_INCOME`. */
		householdIncomeDetails: Option[Schema.HouseholdIncomeAssignedTargetingOptionDetails] = None,
	  /** On screen position details. This field will be populated when the targeting_type is `TARGETING_TYPE_ON_SCREEN_POSITION`. */
		onScreenPositionDetails: Option[Schema.OnScreenPositionAssignedTargetingOptionDetails] = None,
	  /** Carrier and ISP details. This field will be populated when the targeting_type is `TARGETING_TYPE_CARRIER_AND_ISP`. */
		carrierAndIspDetails: Option[Schema.CarrierAndIspAssignedTargetingOptionDetails] = None,
	  /** Keyword details. This field will be populated when the targeting_type is `TARGETING_TYPE_KEYWORD`. A maximum of 5000 direct negative keywords can be assigned to a resource. No limit on number of positive keywords that can be assigned. */
		keywordDetails: Option[Schema.KeywordAssignedTargetingOptionDetails] = None,
	  /** Keyword details. This field will be populated when the targeting_type is `TARGETING_TYPE_NEGATIVE_KEYWORD_LIST`. A maximum of 4 negative keyword lists can be assigned to a resource. */
		negativeKeywordListDetails: Option[Schema.NegativeKeywordListAssignedTargetingOptionDetails] = None,
	  /** Operating system details. This field will be populated when the targeting_type is `TARGETING_TYPE_OPERATING_SYSTEM`. */
		operatingSystemDetails: Option[Schema.OperatingSystemAssignedTargetingOptionDetails] = None,
	  /** Device make and model details. This field will be populated when the targeting_type is `TARGETING_TYPE_DEVICE_MAKE_MODEL`. */
		deviceMakeModelDetails: Option[Schema.DeviceMakeModelAssignedTargetingOptionDetails] = None,
	  /** Environment details. This field will be populated when the targeting_type is `TARGETING_TYPE_ENVIRONMENT`. */
		environmentDetails: Option[Schema.EnvironmentAssignedTargetingOptionDetails] = None,
	  /** Inventory source details. This field will be populated when the targeting_type is `TARGETING_TYPE_INVENTORY_SOURCE`. */
		inventorySourceDetails: Option[Schema.InventorySourceAssignedTargetingOptionDetails] = None,
	  /** Category details. This field will be populated when the targeting_type is `TARGETING_TYPE_CATEGORY`. Targeting a category will also target its subcategories. If a category is excluded from targeting and a subcategory is included, the exclusion will take precedence. */
		categoryDetails: Option[Schema.CategoryAssignedTargetingOptionDetails] = None,
	  /** Viewability details. This field will be populated when the targeting_type is `TARGETING_TYPE_VIEWABILITY`. You can only target one viewability option per resource. */
		viewabilityDetails: Option[Schema.ViewabilityAssignedTargetingOptionDetails] = None,
	  /** Authorized seller status details. This field will be populated when the targeting_type is `TARGETING_TYPE_AUTHORIZED_SELLER_STATUS`. You can only target one authorized seller status option per resource. If a resource doesn't have an authorized seller status option, all authorized sellers indicated as DIRECT or RESELLER in the ads.txt file are targeted by default. */
		authorizedSellerStatusDetails: Option[Schema.AuthorizedSellerStatusAssignedTargetingOptionDetails] = None,
	  /** Language details. This field will be populated when the targeting_type is `TARGETING_TYPE_LANGUAGE`. */
		languageDetails: Option[Schema.LanguageAssignedTargetingOptionDetails] = None,
	  /** Geographic region details. This field will be populated when the targeting_type is `TARGETING_TYPE_GEO_REGION`. */
		geoRegionDetails: Option[Schema.GeoRegionAssignedTargetingOptionDetails] = None,
	  /** Inventory source group details. This field will be populated when the targeting_type is `TARGETING_TYPE_INVENTORY_SOURCE_GROUP`. */
		inventorySourceGroupDetails: Option[Schema.InventorySourceGroupAssignedTargetingOptionDetails] = None,
	  /** Digital content label details. This field will be populated when the targeting_type is `TARGETING_TYPE_DIGITAL_CONTENT_LABEL_EXCLUSION`. Digital content labels are targeting exclusions. Advertiser level digital content label exclusions, if set, are always applied in serving (even though they aren't visible in resource settings). Resource settings can exclude content labels in addition to advertiser exclusions, but can't override them. A line item won't serve if all the digital content labels are excluded. */
		digitalContentLabelExclusionDetails: Option[Schema.DigitalContentLabelAssignedTargetingOptionDetails] = None,
	  /** Sensitive category details. This field will be populated when the targeting_type is `TARGETING_TYPE_SENSITIVE_CATEGORY_EXCLUSION`. Sensitive categories are targeting exclusions. Advertiser level sensitive category exclusions, if set, are always applied in serving (even though they aren't visible in resource settings). Resource settings can exclude sensitive categories in addition to advertiser exclusions, but can't override them. */
		sensitiveCategoryExclusionDetails: Option[Schema.SensitiveCategoryAssignedTargetingOptionDetails] = None,
	  /** Exchange details. This field will be populated when the targeting_type is `TARGETING_TYPE_EXCHANGE`. */
		exchangeDetails: Option[Schema.ExchangeAssignedTargetingOptionDetails] = None,
	  /** Sub-exchange details. This field will be populated when the targeting_type is `TARGETING_TYPE_SUB_EXCHANGE`. */
		subExchangeDetails: Option[Schema.SubExchangeAssignedTargetingOptionDetails] = None,
	  /** Third party verification details. This field will be populated when the targeting_type is `TARGETING_TYPE_THIRD_PARTY_VERIFIER`. */
		thirdPartyVerifierDetails: Option[Schema.ThirdPartyVerifierAssignedTargetingOptionDetails] = None,
	  /** POI details. This field will be populated when the targeting_type is `TARGETING_TYPE_POI`. */
		poiDetails: Option[Schema.PoiAssignedTargetingOptionDetails] = None,
	  /** Business chain details. This field will be populated when the targeting_type is `TARGETING_TYPE_BUSINESS_CHAIN`. */
		businessChainDetails: Option[Schema.BusinessChainAssignedTargetingOptionDetails] = None,
	  /** Content duration details. This field will be populated when the targeting_type is `TARGETING_TYPE_CONTENT_DURATION`. */
		contentDurationDetails: Option[Schema.ContentDurationAssignedTargetingOptionDetails] = None,
	  /** Content duration details. This field will be populated when the TargetingType is `TARGETING_TYPE_CONTENT_STREAM_TYPE`. */
		contentStreamTypeDetails: Option[Schema.ContentStreamTypeAssignedTargetingOptionDetails] = None,
	  /** Native content position details. This field will be populated when the targeting_type is `TARGETING_TYPE_NATIVE_CONTENT_POSITION`. */
		nativeContentPositionDetails: Option[Schema.NativeContentPositionAssignedTargetingOptionDetails] = None,
	  /** Open Measurement enabled inventory details. This field will be populated when the targeting_type is `TARGETING_TYPE_OMID`. */
		omidDetails: Option[Schema.OmidAssignedTargetingOptionDetails] = None,
	  /** Audio content type details. This field will be populated when the targeting_type is `TARGETING_TYPE_AUDIO_CONTENT_TYPE`. */
		audioContentTypeDetails: Option[Schema.AudioContentTypeAssignedTargetingOptionDetails] = None,
	  /** Content genre details. This field will be populated when the targeting_type is `TARGETING_TYPE_CONTENT_GENRE`. */
		contentGenreDetails: Option[Schema.ContentGenreAssignedTargetingOptionDetails] = None,
	  /** YouTube video details. This field will be populated when the targeting_type is `TARGETING_TYPE_YOUTUBE_VIDEO`. */
		youtubeVideoDetails: Option[Schema.YoutubeVideoAssignedTargetingOptionDetails] = None,
	  /** YouTube channel details. This field will be populated when the targeting_type is `TARGETING_TYPE_YOUTUBE_CHANNEL`. */
		youtubeChannelDetails: Option[Schema.YoutubeChannelAssignedTargetingOptionDetails] = None,
	  /** Session position details. This field will be populated when the targeting_type is `TARGETING_TYPE_SESSION_POSITION`. */
		sessionPositionDetails: Option[Schema.SessionPositionAssignedTargetingOptionDetails] = None
	)
	
	case class ChannelAssignedTargetingOptionDetails(
	  /** Required. ID of the channel. Should refer to the channel ID field on a [Partner-owned channel](partners.channels#Channel.FIELDS.channel_id) or [advertiser-owned channel](advertisers.channels#Channel.FIELDS.channel_id) resource. */
		channelId: Option[String] = None,
	  /** Indicates if this option is being negatively targeted. For advertiser level assigned targeting option, this field must be true. */
		negative: Option[Boolean] = None
	)
	
	case class AppCategoryAssignedTargetingOptionDetails(
	  /** Output only. The display name of the app category. */
		displayName: Option[String] = None,
	  /** Required. The targeting_option_id field when targeting_type is `TARGETING_TYPE_APP_CATEGORY`. */
		targetingOptionId: Option[String] = None,
	  /** Indicates if this option is being negatively targeted. */
		negative: Option[Boolean] = None
	)
	
	object AppAssignedTargetingOptionDetails {
		enum AppPlatformEnum extends Enum[AppPlatformEnum] { case APP_PLATFORM_UNSPECIFIED, APP_PLATFORM_IOS, APP_PLATFORM_ANDROID, APP_PLATFORM_ROKU, APP_PLATFORM_AMAZON_FIRETV, APP_PLATFORM_PLAYSTATION, APP_PLATFORM_APPLE_TV, APP_PLATFORM_XBOX, APP_PLATFORM_SAMSUNG_TV, APP_PLATFORM_ANDROID_TV, APP_PLATFORM_GENERIC_CTV, APP_PLATFORM_LG_TV, APP_PLATFORM_VIZIO_TV }
	}
	case class AppAssignedTargetingOptionDetails(
	  /** Required. The ID of the app. Android's Play store app uses bundle ID, for example `com.google.android.gm`. Apple's App store app ID uses 9 digit string, for example `422689480`. */
		appId: Option[String] = None,
	  /** Output only. The display name of the app. */
		displayName: Option[String] = None,
	  /** Indicates if this option is being negatively targeted. */
		negative: Option[Boolean] = None,
	  /** Indicates the platform of the targeted app. If this field is not specified, the app platform will be assumed to be mobile (i.e., Android or iOS), and we will derive the appropriate mobile platform from the app ID. */
		appPlatform: Option[Schema.AppAssignedTargetingOptionDetails.AppPlatformEnum] = None
	)
	
	case class UrlAssignedTargetingOptionDetails(
	  /** Required. The URL, for example `example.com`. DV360 supports two levels of subdirectory targeting, for example `www.example.com/one-subdirectory-level/second-level`, and five levels of subdomain targeting, for example `five.four.three.two.one.example.com`. */
		url: Option[String] = None,
	  /** Indicates if this option is being negatively targeted. */
		negative: Option[Boolean] = None
	)
	
	object DayAndTimeAssignedTargetingOptionDetails {
		enum DayOfWeekEnum extends Enum[DayOfWeekEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
		enum TimeZoneResolutionEnum extends Enum[TimeZoneResolutionEnum] { case TIME_ZONE_RESOLUTION_UNSPECIFIED, TIME_ZONE_RESOLUTION_END_USER, TIME_ZONE_RESOLUTION_ADVERTISER }
	}
	case class DayAndTimeAssignedTargetingOptionDetails(
	  /** Required. The day of the week for this day and time targeting setting. */
		dayOfWeek: Option[Schema.DayAndTimeAssignedTargetingOptionDetails.DayOfWeekEnum] = None,
	  /** Required. The start hour for day and time targeting. Must be between 0 (start of day) and 23 (1 hour before end of day). */
		startHour: Option[Int] = None,
	  /** Required. The end hour for day and time targeting. Must be between 1 (1 hour after start of day) and 24 (end of day). */
		endHour: Option[Int] = None,
	  /** Required. The mechanism used to determine which timezone to use for this day and time targeting setting. */
		timeZoneResolution: Option[Schema.DayAndTimeAssignedTargetingOptionDetails.TimeZoneResolutionEnum] = None
	)
	
	object AgeRangeAssignedTargetingOptionDetails {
		enum AgeRangeEnum extends Enum[AgeRangeEnum] { case AGE_RANGE_UNSPECIFIED, AGE_RANGE_18_24, AGE_RANGE_25_34, AGE_RANGE_35_44, AGE_RANGE_45_54, AGE_RANGE_55_64, AGE_RANGE_65_PLUS, AGE_RANGE_UNKNOWN, AGE_RANGE_18_20, AGE_RANGE_21_24, AGE_RANGE_25_29, AGE_RANGE_30_34, AGE_RANGE_35_39, AGE_RANGE_40_44, AGE_RANGE_45_49, AGE_RANGE_50_54, AGE_RANGE_55_59, AGE_RANGE_60_64 }
	}
	case class AgeRangeAssignedTargetingOptionDetails(
	  /** Required. The age range of an audience. We only support targeting a continuous age range of an audience. Thus, the age range represented in this field can be 1) targeted solely, or, 2) part of a larger continuous age range. The reach of a continuous age range targeting can be expanded by also targeting an audience of an unknown age. */
		ageRange: Option[Schema.AgeRangeAssignedTargetingOptionDetails.AgeRangeEnum] = None
	)
	
	case class RegionalLocationListAssignedTargetingOptionDetails(
	  /** Required. ID of the regional location list. Should refer to the location_list_id field of a LocationList resource whose type is `TARGETING_LOCATION_TYPE_REGIONAL`. */
		regionalLocationListId: Option[String] = None,
	  /** Indicates if this option is being negatively targeted. */
		negative: Option[Boolean] = None
	)
	
	object ProximityLocationListAssignedTargetingOptionDetails {
		enum ProximityRadiusUnitEnum extends Enum[ProximityRadiusUnitEnum] { case PROXIMITY_RADIUS_UNIT_UNSPECIFIED, PROXIMITY_RADIUS_UNIT_MILES, PROXIMITY_RADIUS_UNIT_KILOMETERS }
	}
	case class ProximityLocationListAssignedTargetingOptionDetails(
	  /** Required. ID of the proximity location list. Should refer to the location_list_id field of a LocationList resource whose type is `TARGETING_LOCATION_TYPE_PROXIMITY`. */
		proximityLocationListId: Option[String] = None,
	  /** Required. Radius expressed in the distance units set in proximity_radius_unit. This represents the size of the area around a chosen location that will be targeted. Radius should be between 1 and 500 miles or 800 kilometers. */
		proximityRadius: Option[BigDecimal] = None,
	  /** Required. Radius distance units. */
		proximityRadiusUnit: Option[Schema.ProximityLocationListAssignedTargetingOptionDetails.ProximityRadiusUnitEnum] = None
	)
	
	object GenderAssignedTargetingOptionDetails {
		enum GenderEnum extends Enum[GenderEnum] { case GENDER_UNSPECIFIED, GENDER_MALE, GENDER_FEMALE, GENDER_UNKNOWN }
	}
	case class GenderAssignedTargetingOptionDetails(
	  /** Required. The gender of the audience. */
		gender: Option[Schema.GenderAssignedTargetingOptionDetails.GenderEnum] = None
	)
	
	object VideoPlayerSizeAssignedTargetingOptionDetails {
		enum VideoPlayerSizeEnum extends Enum[VideoPlayerSizeEnum] { case VIDEO_PLAYER_SIZE_UNSPECIFIED, VIDEO_PLAYER_SIZE_SMALL, VIDEO_PLAYER_SIZE_LARGE, VIDEO_PLAYER_SIZE_HD, VIDEO_PLAYER_SIZE_UNKNOWN }
	}
	case class VideoPlayerSizeAssignedTargetingOptionDetails(
	  /** Required. The video player size. */
		videoPlayerSize: Option[Schema.VideoPlayerSizeAssignedTargetingOptionDetails.VideoPlayerSizeEnum] = None
	)
	
	object UserRewardedContentAssignedTargetingOptionDetails {
		enum UserRewardedContentEnum extends Enum[UserRewardedContentEnum] { case USER_REWARDED_CONTENT_UNSPECIFIED, USER_REWARDED_CONTENT_USER_REWARDED, USER_REWARDED_CONTENT_NOT_USER_REWARDED }
	}
	case class UserRewardedContentAssignedTargetingOptionDetails(
	  /** Required. The targeting_option_id field when targeting_type is `TARGETING_TYPE_USER_REWARDED_CONTENT`. */
		targetingOptionId: Option[String] = None,
	  /** Output only. User rewarded content status for video ads. */
		userRewardedContent: Option[Schema.UserRewardedContentAssignedTargetingOptionDetails.UserRewardedContentEnum] = None
	)
	
	object ParentalStatusAssignedTargetingOptionDetails {
		enum ParentalStatusEnum extends Enum[ParentalStatusEnum] { case PARENTAL_STATUS_UNSPECIFIED, PARENTAL_STATUS_PARENT, PARENTAL_STATUS_NOT_A_PARENT, PARENTAL_STATUS_UNKNOWN }
	}
	case class ParentalStatusAssignedTargetingOptionDetails(
	  /** Required. The parental status of the audience. */
		parentalStatus: Option[Schema.ParentalStatusAssignedTargetingOptionDetails.ParentalStatusEnum] = None
	)
	
	object ContentInstreamPositionAssignedTargetingOptionDetails {
		enum ContentInstreamPositionEnum extends Enum[ContentInstreamPositionEnum] { case CONTENT_INSTREAM_POSITION_UNSPECIFIED, CONTENT_INSTREAM_POSITION_PRE_ROLL, CONTENT_INSTREAM_POSITION_MID_ROLL, CONTENT_INSTREAM_POSITION_POST_ROLL, CONTENT_INSTREAM_POSITION_UNKNOWN }
		enum AdTypeEnum extends Enum[AdTypeEnum] { case AD_TYPE_UNSPECIFIED, AD_TYPE_DISPLAY, AD_TYPE_VIDEO, AD_TYPE_AUDIO }
	}
	case class ContentInstreamPositionAssignedTargetingOptionDetails(
	  /** Required. The content instream position for video or audio ads. */
		contentInstreamPosition: Option[Schema.ContentInstreamPositionAssignedTargetingOptionDetails.ContentInstreamPositionEnum] = None,
	  /** Output only. The ad type to target. Only applicable to insertion order targeting and new line items supporting the specified ad type will inherit this targeting option by default. Possible values are: &#42; `AD_TYPE_VIDEO`, the setting will be inherited by new line item when line_item_type is `LINE_ITEM_TYPE_VIDEO_DEFAULT`. &#42; `AD_TYPE_AUDIO`, the setting will be inherited by new line item when line_item_type is `LINE_ITEM_TYPE_AUDIO_DEFAULT`. */
		adType: Option[Schema.ContentInstreamPositionAssignedTargetingOptionDetails.AdTypeEnum] = None
	)
	
	object ContentOutstreamPositionAssignedTargetingOptionDetails {
		enum ContentOutstreamPositionEnum extends Enum[ContentOutstreamPositionEnum] { case CONTENT_OUTSTREAM_POSITION_UNSPECIFIED, CONTENT_OUTSTREAM_POSITION_UNKNOWN, CONTENT_OUTSTREAM_POSITION_IN_ARTICLE, CONTENT_OUTSTREAM_POSITION_IN_BANNER, CONTENT_OUTSTREAM_POSITION_IN_FEED, CONTENT_OUTSTREAM_POSITION_INTERSTITIAL }
		enum AdTypeEnum extends Enum[AdTypeEnum] { case AD_TYPE_UNSPECIFIED, AD_TYPE_DISPLAY, AD_TYPE_VIDEO, AD_TYPE_AUDIO }
	}
	case class ContentOutstreamPositionAssignedTargetingOptionDetails(
	  /** Required. The content outstream position. */
		contentOutstreamPosition: Option[Schema.ContentOutstreamPositionAssignedTargetingOptionDetails.ContentOutstreamPositionEnum] = None,
	  /** Output only. The ad type to target. Only applicable to insertion order targeting and new line items supporting the specified ad type will inherit this targeting option by default. Possible values are: &#42; `AD_TYPE_DISPLAY`, the setting will be inherited by new line item when line_item_type is `LINE_ITEM_TYPE_DISPLAY_DEFAULT`. &#42; `AD_TYPE_VIDEO`, the setting will be inherited by new line item when line_item_type is `LINE_ITEM_TYPE_VIDEO_DEFAULT`. */
		adType: Option[Schema.ContentOutstreamPositionAssignedTargetingOptionDetails.AdTypeEnum] = None
	)
	
	object DeviceTypeAssignedTargetingOptionDetails {
		enum DeviceTypeEnum extends Enum[DeviceTypeEnum] { case DEVICE_TYPE_UNSPECIFIED, DEVICE_TYPE_COMPUTER, DEVICE_TYPE_CONNECTED_TV, DEVICE_TYPE_SMART_PHONE, DEVICE_TYPE_TABLET, DEVICE_TYPE_CONNECTED_DEVICE }
	}
	case class DeviceTypeAssignedTargetingOptionDetails(
	  /** Required. The display name of the device type. */
		deviceType: Option[Schema.DeviceTypeAssignedTargetingOptionDetails.DeviceTypeEnum] = None,
	  /** Output only. Bid multiplier allows you to show your ads more or less frequently based on the device type. It will apply a multiplier on the original bid price. When this field is 0, it indicates this field is not applicable instead of multiplying 0 on the original bid price. For example, if the bid price without multiplier is $10.0 and the multiplier is 1.5 for Tablet, the resulting bid price for Tablet will be $15.0. Only applicable to YouTube and Partners line items. */
		youtubeAndPartnersBidMultiplier: Option[BigDecimal] = None
	)
	
	case class AudienceGroupAssignedTargetingOptionDetails(
	  /** The first and third party audience ids and recencies of included first and third party audience groups. Each first and third party audience group contains first and third party audience ids only. The relation between each first and third party audience group is INTERSECTION, and the result is UNION'ed with other audience groups. Repeated groups with same settings will be ignored. */
		includedFirstAndThirdPartyAudienceGroups: Option[List[Schema.FirstAndThirdPartyAudienceGroup]] = None,
	  /** The Google audience ids of the included Google audience group. Contains Google audience ids only. */
		includedGoogleAudienceGroup: Option[Schema.GoogleAudienceGroup] = None,
	  /** The custom list ids of the included custom list group. Contains custom list ids only. */
		includedCustomListGroup: Option[Schema.CustomListGroup] = None,
	  /** The combined audience ids of the included combined audience group. Contains combined audience ids only. */
		includedCombinedAudienceGroup: Option[Schema.CombinedAudienceGroup] = None,
	  /** The first and third party audience ids and recencies of the excluded first and third party audience group. Used for negative targeting. The COMPLEMENT of the UNION of this group and other excluded audience groups is used as an INTERSECTION to any positive audience targeting. All items are logically ‘OR’ of each other. */
		excludedFirstAndThirdPartyAudienceGroup: Option[Schema.FirstAndThirdPartyAudienceGroup] = None,
	  /** The Google audience ids of the excluded Google audience group. Used for negative targeting. The COMPLEMENT of the UNION of this group and other excluded audience groups is used as an INTERSECTION to any positive audience targeting. Only contains Affinity, In-market and Installed-apps type Google audiences. All items are logically ‘OR’ of each other. */
		excludedGoogleAudienceGroup: Option[Schema.GoogleAudienceGroup] = None
	)
	
	case class FirstAndThirdPartyAudienceGroup(
	  /** Required. All first and third party audience targeting settings in first and third party audience group. Repeated settings with same id are not allowed. */
		settings: Option[List[Schema.FirstAndThirdPartyAudienceTargetingSetting]] = None
	)
	
	object FirstAndThirdPartyAudienceTargetingSetting {
		enum RecencyEnum extends Enum[RecencyEnum] { case RECENCY_NO_LIMIT, RECENCY_1_MINUTE, RECENCY_5_MINUTES, RECENCY_10_MINUTES, RECENCY_15_MINUTES, RECENCY_30_MINUTES, RECENCY_1_HOUR, RECENCY_2_HOURS, RECENCY_3_HOURS, RECENCY_6_HOURS, RECENCY_12_HOURS, RECENCY_1_DAY, RECENCY_2_DAYS, RECENCY_3_DAYS, RECENCY_5_DAYS, RECENCY_7_DAYS, RECENCY_10_DAYS, RECENCY_14_DAYS, RECENCY_15_DAYS, RECENCY_21_DAYS, RECENCY_28_DAYS, RECENCY_30_DAYS, RECENCY_40_DAYS, RECENCY_45_DAYS, RECENCY_60_DAYS, RECENCY_90_DAYS, RECENCY_120_DAYS, RECENCY_180_DAYS, RECENCY_270_DAYS, RECENCY_365_DAYS }
	}
	case class FirstAndThirdPartyAudienceTargetingSetting(
	  /** Required. First and third party audience id of the first and third party audience targeting setting. This id is first_and_third_party_audience_id. */
		firstAndThirdPartyAudienceId: Option[String] = None,
	  /** The recency of the first and third party audience targeting setting. Only applicable to first party audiences, otherwise will be ignored. For more info, refer to https://support.google.com/displayvideo/answer/2949947#recency When unspecified, no recency limit will be used. */
		recency: Option[Schema.FirstAndThirdPartyAudienceTargetingSetting.RecencyEnum] = None
	)
	
	case class GoogleAudienceGroup(
	  /** Required. All Google audience targeting settings in Google audience group. Repeated settings with same id will be ignored. */
		settings: Option[List[Schema.GoogleAudienceTargetingSetting]] = None
	)
	
	case class GoogleAudienceTargetingSetting(
	  /** Required. Google audience id of the Google audience targeting setting. This id is google_audience_id. */
		googleAudienceId: Option[String] = None
	)
	
	case class CustomListGroup(
	  /** Required. All custom list targeting settings in custom list group. Repeated settings with same id will be ignored. */
		settings: Option[List[Schema.CustomListTargetingSetting]] = None
	)
	
	case class CustomListTargetingSetting(
	  /** Required. Custom id of custom list targeting setting. This id is custom_list_id. */
		customListId: Option[String] = None
	)
	
	case class CombinedAudienceGroup(
	  /** Required. All combined audience targeting settings in combined audience group. Repeated settings with same id will be ignored. The number of combined audience settings should be no more than five, error will be thrown otherwise. */
		settings: Option[List[Schema.CombinedAudienceTargetingSetting]] = None
	)
	
	case class CombinedAudienceTargetingSetting(
	  /** Required. Combined audience id of combined audience targeting setting. This id is combined_audience_id. */
		combinedAudienceId: Option[String] = None
	)
	
	case class BrowserAssignedTargetingOptionDetails(
	  /** Output only. The display name of the browser. */
		displayName: Option[String] = None,
	  /** Required. The targeting_option_id of a TargetingOption of type `TARGETING_TYPE_BROWSER`. */
		targetingOptionId: Option[String] = None,
	  /** Indicates if this option is being negatively targeted. All assigned browser targeting options on the same resource must have the same value for this field. */
		negative: Option[Boolean] = None
	)
	
	object HouseholdIncomeAssignedTargetingOptionDetails {
		enum HouseholdIncomeEnum extends Enum[HouseholdIncomeEnum] { case HOUSEHOLD_INCOME_UNSPECIFIED, HOUSEHOLD_INCOME_UNKNOWN, HOUSEHOLD_INCOME_LOWER_50_PERCENT, HOUSEHOLD_INCOME_TOP_41_TO_50_PERCENT, HOUSEHOLD_INCOME_TOP_31_TO_40_PERCENT, HOUSEHOLD_INCOME_TOP_21_TO_30_PERCENT, HOUSEHOLD_INCOME_TOP_11_TO_20_PERCENT, HOUSEHOLD_INCOME_TOP_10_PERCENT }
	}
	case class HouseholdIncomeAssignedTargetingOptionDetails(
	  /** Required. The household income of the audience. */
		householdIncome: Option[Schema.HouseholdIncomeAssignedTargetingOptionDetails.HouseholdIncomeEnum] = None
	)
	
	object OnScreenPositionAssignedTargetingOptionDetails {
		enum OnScreenPositionEnum extends Enum[OnScreenPositionEnum] { case ON_SCREEN_POSITION_UNSPECIFIED, ON_SCREEN_POSITION_UNKNOWN, ON_SCREEN_POSITION_ABOVE_THE_FOLD, ON_SCREEN_POSITION_BELOW_THE_FOLD }
		enum AdTypeEnum extends Enum[AdTypeEnum] { case AD_TYPE_UNSPECIFIED, AD_TYPE_DISPLAY, AD_TYPE_VIDEO, AD_TYPE_AUDIO }
	}
	case class OnScreenPositionAssignedTargetingOptionDetails(
	  /** Required. The targeting_option_id field when targeting_type is `TARGETING_TYPE_ON_SCREEN_POSITION`. */
		targetingOptionId: Option[String] = None,
	  /** Output only. The on screen position. */
		onScreenPosition: Option[Schema.OnScreenPositionAssignedTargetingOptionDetails.OnScreenPositionEnum] = None,
	  /** Output only. The ad type to target. Only applicable to insertion order targeting and new line items supporting the specified ad type will inherit this targeting option by default. Possible values are: &#42; `AD_TYPE_DISPLAY`, the setting will be inherited by new line item when line_item_type is `LINE_ITEM_TYPE_DISPLAY_DEFAULT`. &#42; `AD_TYPE_VIDEO`, the setting will be inherited by new line item when line_item_type is `LINE_ITEM_TYPE_VIDEO_DEFAULT`. */
		adType: Option[Schema.OnScreenPositionAssignedTargetingOptionDetails.AdTypeEnum] = None
	)
	
	case class CarrierAndIspAssignedTargetingOptionDetails(
	  /** Output only. The display name of the carrier or ISP. */
		displayName: Option[String] = None,
	  /** Required. The targeting_option_id of a TargetingOption of type `TARGETING_TYPE_CARRIER_AND_ISP`. */
		targetingOptionId: Option[String] = None,
	  /** Indicates if this option is being negatively targeted. All assigned carrier and ISP targeting options on the same resource must have the same value for this field. */
		negative: Option[Boolean] = None
	)
	
	case class KeywordAssignedTargetingOptionDetails(
	  /** Required. The keyword, for example `car insurance`. Positive keyword cannot be offensive word. Must be UTF-8 encoded with a maximum size of 255 bytes. Maximum number of characters is 80. Maximum number of words is 10. */
		keyword: Option[String] = None,
	  /** Indicates if this option is being negatively targeted. */
		negative: Option[Boolean] = None
	)
	
	case class NegativeKeywordListAssignedTargetingOptionDetails(
	  /** Required. ID of the negative keyword list. Should refer to the negative_keyword_list_id field of a NegativeKeywordList resource. */
		negativeKeywordListId: Option[String] = None
	)
	
	case class OperatingSystemAssignedTargetingOptionDetails(
	  /** Output only. The display name of the operating system. */
		displayName: Option[String] = None,
	  /** Required. The targeting option ID populated in targeting_option_id field when targeting_type is `TARGETING_TYPE_OPERATING_SYSTEM`. */
		targetingOptionId: Option[String] = None,
	  /** Indicates if this option is being negatively targeted. */
		negative: Option[Boolean] = None
	)
	
	case class DeviceMakeModelAssignedTargetingOptionDetails(
	  /** Output only. The display name of the device make and model. */
		displayName: Option[String] = None,
	  /** Required. The targeting_option_id field when targeting_type is `TARGETING_TYPE_DEVICE_MAKE_MODEL`. */
		targetingOptionId: Option[String] = None,
	  /** Indicates if this option is being negatively targeted. */
		negative: Option[Boolean] = None
	)
	
	object EnvironmentAssignedTargetingOptionDetails {
		enum EnvironmentEnum extends Enum[EnvironmentEnum] { case ENVIRONMENT_UNSPECIFIED, ENVIRONMENT_WEB_OPTIMIZED, ENVIRONMENT_WEB_NOT_OPTIMIZED, ENVIRONMENT_APP }
	}
	case class EnvironmentAssignedTargetingOptionDetails(
	  /** Required. The serving environment. */
		environment: Option[Schema.EnvironmentAssignedTargetingOptionDetails.EnvironmentEnum] = None
	)
	
	case class InventorySourceAssignedTargetingOptionDetails(
	  /** Required. ID of the inventory source. Should refer to the inventory_source_id field of an InventorySource resource. */
		inventorySourceId: Option[String] = None
	)
	
	case class CategoryAssignedTargetingOptionDetails(
	  /** Output only. The display name of the category. */
		displayName: Option[String] = None,
	  /** Required. The targeting_option_id field when targeting_type is `TARGETING_TYPE_CATEGORY`. */
		targetingOptionId: Option[String] = None,
	  /** Indicates if this option is being negatively targeted. */
		negative: Option[Boolean] = None
	)
	
	object ViewabilityAssignedTargetingOptionDetails {
		enum ViewabilityEnum extends Enum[ViewabilityEnum] { case VIEWABILITY_UNSPECIFIED, VIEWABILITY_10_PERCENT_OR_MORE, VIEWABILITY_20_PERCENT_OR_MORE, VIEWABILITY_30_PERCENT_OR_MORE, VIEWABILITY_40_PERCENT_OR_MORE, VIEWABILITY_50_PERCENT_OR_MORE, VIEWABILITY_60_PERCENT_OR_MORE, VIEWABILITY_70_PERCENT_OR_MORE, VIEWABILITY_80_PERCENT_OR_MORE, VIEWABILITY_90_PERCENT_OR_MORE }
	}
	case class ViewabilityAssignedTargetingOptionDetails(
	  /** Required. The predicted viewability percentage. */
		viewability: Option[Schema.ViewabilityAssignedTargetingOptionDetails.ViewabilityEnum] = None
	)
	
	object AuthorizedSellerStatusAssignedTargetingOptionDetails {
		enum AuthorizedSellerStatusEnum extends Enum[AuthorizedSellerStatusEnum] { case AUTHORIZED_SELLER_STATUS_UNSPECIFIED, AUTHORIZED_SELLER_STATUS_AUTHORIZED_DIRECT_SELLERS_ONLY, AUTHORIZED_SELLER_STATUS_AUTHORIZED_AND_NON_PARTICIPATING_PUBLISHERS }
	}
	case class AuthorizedSellerStatusAssignedTargetingOptionDetails(
	  /** Output only. The authorized seller status to target. */
		authorizedSellerStatus: Option[Schema.AuthorizedSellerStatusAssignedTargetingOptionDetails.AuthorizedSellerStatusEnum] = None,
	  /** Required. The targeting_option_id of a TargetingOption of type `TARGETING_TYPE_AUTHORIZED_SELLER_STATUS`. */
		targetingOptionId: Option[String] = None
	)
	
	case class LanguageAssignedTargetingOptionDetails(
	  /** Output only. The display name of the language (e.g., "French"). */
		displayName: Option[String] = None,
	  /** Required. The targeting_option_id of a TargetingOption of type `TARGETING_TYPE_LANGUAGE`. */
		targetingOptionId: Option[String] = None,
	  /** Indicates if this option is being negatively targeted. All assigned language targeting options on the same resource must have the same value for this field. */
		negative: Option[Boolean] = None
	)
	
	object GeoRegionAssignedTargetingOptionDetails {
		enum GeoRegionTypeEnum extends Enum[GeoRegionTypeEnum] { case GEO_REGION_TYPE_UNKNOWN, GEO_REGION_TYPE_OTHER, GEO_REGION_TYPE_COUNTRY, GEO_REGION_TYPE_REGION, GEO_REGION_TYPE_TERRITORY, GEO_REGION_TYPE_PROVINCE, GEO_REGION_TYPE_STATE, GEO_REGION_TYPE_PREFECTURE, GEO_REGION_TYPE_GOVERNORATE, GEO_REGION_TYPE_CANTON, GEO_REGION_TYPE_UNION_TERRITORY, GEO_REGION_TYPE_AUTONOMOUS_COMMUNITY, GEO_REGION_TYPE_DMA_REGION, GEO_REGION_TYPE_METRO, GEO_REGION_TYPE_CONGRESSIONAL_DISTRICT, GEO_REGION_TYPE_COUNTY, GEO_REGION_TYPE_MUNICIPALITY, GEO_REGION_TYPE_CITY, GEO_REGION_TYPE_POSTAL_CODE, GEO_REGION_TYPE_DEPARTMENT, GEO_REGION_TYPE_AIRPORT, GEO_REGION_TYPE_TV_REGION, GEO_REGION_TYPE_OKRUG, GEO_REGION_TYPE_BOROUGH, GEO_REGION_TYPE_CITY_REGION, GEO_REGION_TYPE_ARRONDISSEMENT, GEO_REGION_TYPE_NEIGHBORHOOD, GEO_REGION_TYPE_UNIVERSITY, GEO_REGION_TYPE_DISTRICT }
	}
	case class GeoRegionAssignedTargetingOptionDetails(
	  /** Output only. The display name of the geographic region (e.g., "Ontario, Canada"). */
		displayName: Option[String] = None,
	  /** Required. The targeting_option_id of a TargetingOption of type `TARGETING_TYPE_GEO_REGION`. */
		targetingOptionId: Option[String] = None,
	  /** Output only. The type of geographic region targeting. */
		geoRegionType: Option[Schema.GeoRegionAssignedTargetingOptionDetails.GeoRegionTypeEnum] = None,
	  /** Indicates if this option is being negatively targeted. */
		negative: Option[Boolean] = None
	)
	
	case class InventorySourceGroupAssignedTargetingOptionDetails(
	  /** Required. ID of the inventory source group. Should refer to the inventory_source_group_id field of an InventorySourceGroup resource. */
		inventorySourceGroupId: Option[String] = None
	)
	
	object DigitalContentLabelAssignedTargetingOptionDetails {
		enum ExcludedContentRatingTierEnum extends Enum[ExcludedContentRatingTierEnum] { case CONTENT_RATING_TIER_UNSPECIFIED, CONTENT_RATING_TIER_UNRATED, CONTENT_RATING_TIER_GENERAL, CONTENT_RATING_TIER_PARENTAL_GUIDANCE, CONTENT_RATING_TIER_TEENS, CONTENT_RATING_TIER_MATURE, CONTENT_RATING_TIER_FAMILIES }
	}
	case class DigitalContentLabelAssignedTargetingOptionDetails(
	  /** Required. The display name of the digital content label rating tier to be EXCLUDED. */
		excludedContentRatingTier: Option[Schema.DigitalContentLabelAssignedTargetingOptionDetails.ExcludedContentRatingTierEnum] = None
	)
	
	object SensitiveCategoryAssignedTargetingOptionDetails {
		enum ExcludedSensitiveCategoryEnum extends Enum[ExcludedSensitiveCategoryEnum] { case SENSITIVE_CATEGORY_UNSPECIFIED, SENSITIVE_CATEGORY_ADULT, SENSITIVE_CATEGORY_DEROGATORY, SENSITIVE_CATEGORY_DOWNLOADS_SHARING, SENSITIVE_CATEGORY_WEAPONS, SENSITIVE_CATEGORY_GAMBLING, SENSITIVE_CATEGORY_VIOLENCE, SENSITIVE_CATEGORY_SUGGESTIVE, SENSITIVE_CATEGORY_PROFANITY, SENSITIVE_CATEGORY_ALCOHOL, SENSITIVE_CATEGORY_DRUGS, SENSITIVE_CATEGORY_TOBACCO, SENSITIVE_CATEGORY_POLITICS, SENSITIVE_CATEGORY_RELIGION, SENSITIVE_CATEGORY_TRAGEDY, SENSITIVE_CATEGORY_TRANSPORTATION_ACCIDENTS, SENSITIVE_CATEGORY_SENSITIVE_SOCIAL_ISSUES, SENSITIVE_CATEGORY_SHOCKING, SENSITIVE_CATEGORY_EMBEDDED_VIDEO, SENSITIVE_CATEGORY_LIVE_STREAMING_VIDEO }
	}
	case class SensitiveCategoryAssignedTargetingOptionDetails(
	  /** Required. An enum for the DV360 Sensitive category content classified to be EXCLUDED. */
		excludedSensitiveCategory: Option[Schema.SensitiveCategoryAssignedTargetingOptionDetails.ExcludedSensitiveCategoryEnum] = None
	)
	
	object ExchangeAssignedTargetingOptionDetails {
		enum ExchangeEnum extends Enum[ExchangeEnum] { case EXCHANGE_UNSPECIFIED, EXCHANGE_GOOGLE_AD_MANAGER, EXCHANGE_APPNEXUS, EXCHANGE_BRIGHTROLL, EXCHANGE_ADFORM, EXCHANGE_ADMETA, EXCHANGE_ADMIXER, EXCHANGE_ADSMOGO, EXCHANGE_ADSWIZZ, EXCHANGE_BIDSWITCH, EXCHANGE_BRIGHTROLL_DISPLAY, EXCHANGE_CADREON, EXCHANGE_DAILYMOTION, EXCHANGE_FIVE, EXCHANGE_FLUCT, EXCHANGE_FREEWHEEL, EXCHANGE_GENIEE, EXCHANGE_GUMGUM, EXCHANGE_IMOBILE, EXCHANGE_IBILLBOARD, EXCHANGE_IMPROVE_DIGITAL, EXCHANGE_INDEX, EXCHANGE_KARGO, EXCHANGE_MICROAD, EXCHANGE_MOPUB, EXCHANGE_NEND, EXCHANGE_ONE_BY_AOL_DISPLAY, EXCHANGE_ONE_BY_AOL_MOBILE, EXCHANGE_ONE_BY_AOL_VIDEO, EXCHANGE_OOYALA, EXCHANGE_OPENX, EXCHANGE_PERMODO, EXCHANGE_PLATFORMONE, EXCHANGE_PLATFORMID, EXCHANGE_PUBMATIC, EXCHANGE_PULSEPOINT, EXCHANGE_REVENUEMAX, EXCHANGE_RUBICON, EXCHANGE_SMARTCLIP, EXCHANGE_SMARTRTB, EXCHANGE_SMARTSTREAMTV, EXCHANGE_SOVRN, EXCHANGE_SPOTXCHANGE, EXCHANGE_STROER, EXCHANGE_TEADSTV, EXCHANGE_TELARIA, EXCHANGE_TVN, EXCHANGE_UNITED, EXCHANGE_YIELDLAB, EXCHANGE_YIELDMO, EXCHANGE_UNRULYX, EXCHANGE_OPEN8, EXCHANGE_TRITON, EXCHANGE_TRIPLELIFT, EXCHANGE_TABOOLA, EXCHANGE_INMOBI, EXCHANGE_SMAATO, EXCHANGE_AJA, EXCHANGE_SUPERSHIP, EXCHANGE_NEXSTAR_DIGITAL, EXCHANGE_WAZE, EXCHANGE_SOUNDCAST, EXCHANGE_SHARETHROUGH, EXCHANGE_FYBER, EXCHANGE_RED_FOR_PUBLISHERS, EXCHANGE_MEDIANET, EXCHANGE_TAPJOY, EXCHANGE_VISTAR, EXCHANGE_DAX, EXCHANGE_JCD, EXCHANGE_PLACE_EXCHANGE, EXCHANGE_APPLOVIN, EXCHANGE_CONNATIX, EXCHANGE_RESET_DIGITAL, EXCHANGE_HIVESTACK, EXCHANGE_DRAX, EXCHANGE_APPLOVIN_GBID, EXCHANGE_FYBER_GBID, EXCHANGE_UNITY_GBID, EXCHANGE_CHARTBOOST_GBID, EXCHANGE_ADMOST_GBID, EXCHANGE_TOPON_GBID, EXCHANGE_NETFLIX, EXCHANGE_TUBI }
	}
	case class ExchangeAssignedTargetingOptionDetails(
	  /** Required. The enum value for the exchange. */
		exchange: Option[Schema.ExchangeAssignedTargetingOptionDetails.ExchangeEnum] = None
	)
	
	case class SubExchangeAssignedTargetingOptionDetails(
	  /** Required. The targeting_option_id of a TargetingOption of type `TARGETING_TYPE_SUB_EXCHANGE`. */
		targetingOptionId: Option[String] = None
	)
	
	case class ThirdPartyVerifierAssignedTargetingOptionDetails(
	  /** Third party brand verifier -- Adloox. */
		adloox: Option[Schema.Adloox] = None,
	  /** Third party brand verifier -- DoubleVerify. */
		doubleVerify: Option[Schema.DoubleVerify] = None,
	  /** Third party brand verifier -- Integral Ad Science. */
		integralAdScience: Option[Schema.IntegralAdScience] = None
	)
	
	object Adloox {
		enum ExcludedAdlooxCategoriesEnum extends Enum[ExcludedAdlooxCategoriesEnum] { case ADLOOX_UNSPECIFIED, ADULT_CONTENT_HARD, ADULT_CONTENT_SOFT, ILLEGAL_CONTENT, BORDERLINE_CONTENT, DISCRIMINATORY_CONTENT, VIOLENT_CONTENT_WEAPONS, LOW_VIEWABILITY_DOMAINS, FRAUD }
		enum ExcludedFraudIvtMfaCategoriesEnum extends Enum[ExcludedFraudIvtMfaCategoriesEnum] { case FRAUD_IVT_MFA_CATEGORY_UNSPECIFIED, FRAUD_IVT_MFA }
		enum AdultExplicitSexualContentEnum extends Enum[AdultExplicitSexualContentEnum] { case GARM_RISK_EXCLUSION_UNSPECIFIED, GARM_RISK_EXCLUSION_FLOOR, GARM_RISK_EXCLUSION_HIGH, GARM_RISK_EXCLUSION_MEDIUM, GARM_RISK_EXCLUSION_LOW }
		enum CrimeHarmfulActsIndividualsSocietyHumanRightsViolationsContentEnum extends Enum[CrimeHarmfulActsIndividualsSocietyHumanRightsViolationsContentEnum] { case GARM_RISK_EXCLUSION_UNSPECIFIED, GARM_RISK_EXCLUSION_FLOOR, GARM_RISK_EXCLUSION_HIGH, GARM_RISK_EXCLUSION_MEDIUM, GARM_RISK_EXCLUSION_LOW }
		enum ArmsAmmunitionContentEnum extends Enum[ArmsAmmunitionContentEnum] { case GARM_RISK_EXCLUSION_UNSPECIFIED, GARM_RISK_EXCLUSION_FLOOR, GARM_RISK_EXCLUSION_HIGH, GARM_RISK_EXCLUSION_MEDIUM, GARM_RISK_EXCLUSION_LOW }
		enum DeathInjuryMilitaryConflictContentEnum extends Enum[DeathInjuryMilitaryConflictContentEnum] { case GARM_RISK_EXCLUSION_UNSPECIFIED, GARM_RISK_EXCLUSION_FLOOR, GARM_RISK_EXCLUSION_HIGH, GARM_RISK_EXCLUSION_MEDIUM, GARM_RISK_EXCLUSION_LOW }
		enum DebatedSensitiveSocialIssueContentEnum extends Enum[DebatedSensitiveSocialIssueContentEnum] { case GARM_RISK_EXCLUSION_UNSPECIFIED, GARM_RISK_EXCLUSION_FLOOR, GARM_RISK_EXCLUSION_HIGH, GARM_RISK_EXCLUSION_MEDIUM, GARM_RISK_EXCLUSION_LOW }
		enum IllegalDrugsTobaccoEcigarettesVapingAlcoholContentEnum extends Enum[IllegalDrugsTobaccoEcigarettesVapingAlcoholContentEnum] { case GARM_RISK_EXCLUSION_UNSPECIFIED, GARM_RISK_EXCLUSION_FLOOR, GARM_RISK_EXCLUSION_HIGH, GARM_RISK_EXCLUSION_MEDIUM, GARM_RISK_EXCLUSION_LOW }
		enum OnlinePiracyContentEnum extends Enum[OnlinePiracyContentEnum] { case GARM_RISK_EXCLUSION_UNSPECIFIED, GARM_RISK_EXCLUSION_FLOOR, GARM_RISK_EXCLUSION_HIGH, GARM_RISK_EXCLUSION_MEDIUM, GARM_RISK_EXCLUSION_LOW }
		enum HateSpeechActsAggressionContentEnum extends Enum[HateSpeechActsAggressionContentEnum] { case GARM_RISK_EXCLUSION_UNSPECIFIED, GARM_RISK_EXCLUSION_FLOOR, GARM_RISK_EXCLUSION_HIGH, GARM_RISK_EXCLUSION_MEDIUM, GARM_RISK_EXCLUSION_LOW }
		enum ObscenityProfanityContentEnum extends Enum[ObscenityProfanityContentEnum] { case GARM_RISK_EXCLUSION_UNSPECIFIED, GARM_RISK_EXCLUSION_FLOOR, GARM_RISK_EXCLUSION_HIGH, GARM_RISK_EXCLUSION_MEDIUM, GARM_RISK_EXCLUSION_LOW }
		enum SpamHarmfulContentEnum extends Enum[SpamHarmfulContentEnum] { case GARM_RISK_EXCLUSION_UNSPECIFIED, GARM_RISK_EXCLUSION_FLOOR, GARM_RISK_EXCLUSION_HIGH, GARM_RISK_EXCLUSION_MEDIUM, GARM_RISK_EXCLUSION_LOW }
		enum TerrorismContentEnum extends Enum[TerrorismContentEnum] { case GARM_RISK_EXCLUSION_UNSPECIFIED, GARM_RISK_EXCLUSION_FLOOR, GARM_RISK_EXCLUSION_HIGH, GARM_RISK_EXCLUSION_MEDIUM, GARM_RISK_EXCLUSION_LOW }
		enum MisinformationContentEnum extends Enum[MisinformationContentEnum] { case GARM_RISK_EXCLUSION_UNSPECIFIED, GARM_RISK_EXCLUSION_FLOOR, GARM_RISK_EXCLUSION_HIGH, GARM_RISK_EXCLUSION_MEDIUM, GARM_RISK_EXCLUSION_LOW }
		enum DisplayIabViewabilityEnum extends Enum[DisplayIabViewabilityEnum] { case DISPLAY_IAB_VIEWABILITY_UNSPECIFIED, DISPLAY_IAB_VIEWABILITY_10, DISPLAY_IAB_VIEWABILITY_20, DISPLAY_IAB_VIEWABILITY_35, DISPLAY_IAB_VIEWABILITY_50, DISPLAY_IAB_VIEWABILITY_75 }
		enum VideoIabViewabilityEnum extends Enum[VideoIabViewabilityEnum] { case VIDEO_IAB_VIEWABILITY_UNSPECIFIED, VIDEO_IAB_VIEWABILITY_10, VIDEO_IAB_VIEWABILITY_20, VIDEO_IAB_VIEWABILITY_35, VIDEO_IAB_VIEWABILITY_50, VIDEO_IAB_VIEWABILITY_75 }
	}
	case class Adloox(
	  /** Adloox categories to exclude. */
		excludedAdlooxCategories: Option[List[Schema.Adloox.ExcludedAdlooxCategoriesEnum]] = None,
	  /** Optional. Adloox's fraud IVT MFA categories to exclude. */
		excludedFraudIvtMfaCategories: Option[List[Schema.Adloox.ExcludedFraudIvtMfaCategoriesEnum]] = None,
	  /** Optional. Adult and Explicit Sexual Content [GARM](https://wfanet.org/leadership/garm/about-garm) risk ranges to exclude. */
		adultExplicitSexualContent: Option[Schema.Adloox.AdultExplicitSexualContentEnum] = None,
	  /** Optional. Crime and Harmful Acts Content [GARM](https://wfanet.org/leadership/garm/about-garm) risk ranges to exclude. */
		crimeHarmfulActsIndividualsSocietyHumanRightsViolationsContent: Option[Schema.Adloox.CrimeHarmfulActsIndividualsSocietyHumanRightsViolationsContentEnum] = None,
	  /** Optional. Arms and Ammunition Content [GARM](https://wfanet.org/leadership/garm/about-garm) risk ranges to exclude. */
		armsAmmunitionContent: Option[Schema.Adloox.ArmsAmmunitionContentEnum] = None,
	  /** Optional. Death, Injury, or Military Conflict Content [GARM](https://wfanet.org/leadership/garm/about-garm) risk ranges to exclude. */
		deathInjuryMilitaryConflictContent: Option[Schema.Adloox.DeathInjuryMilitaryConflictContentEnum] = None,
	  /** Optional. Debated Sensitive Social Issue Content [GARM](https://wfanet.org/leadership/garm/about-garm) risk ranges to exclude. */
		debatedSensitiveSocialIssueContent: Option[Schema.Adloox.DebatedSensitiveSocialIssueContentEnum] = None,
	  /** Optional. Illegal Drugs/Alcohol Content [GARM](https://wfanet.org/leadership/garm/about-garm) risk ranges to exclude. */
		illegalDrugsTobaccoEcigarettesVapingAlcoholContent: Option[Schema.Adloox.IllegalDrugsTobaccoEcigarettesVapingAlcoholContentEnum] = None,
	  /** Optional. Online Piracy Content [GARM](https://wfanet.org/leadership/garm/about-garm) risk ranges to exclude. */
		onlinePiracyContent: Option[Schema.Adloox.OnlinePiracyContentEnum] = None,
	  /** Optional. Hate Speech and Acts of Aggression Content [GARM](https://wfanet.org/leadership/garm/about-garm) risk ranges to exclude. */
		hateSpeechActsAggressionContent: Option[Schema.Adloox.HateSpeechActsAggressionContentEnum] = None,
	  /** Optional. Obscenity and Profanity Content [GARM](https://wfanet.org/leadership/garm/about-garm) risk ranges to exclude. */
		obscenityProfanityContent: Option[Schema.Adloox.ObscenityProfanityContentEnum] = None,
	  /** Optional. Spam or Harmful Content [GARM](https://wfanet.org/leadership/garm/about-garm) risk ranges to exclude. */
		spamHarmfulContent: Option[Schema.Adloox.SpamHarmfulContentEnum] = None,
	  /** Optional. Terrorism Content [GARM](https://wfanet.org/leadership/garm/about-garm) risk ranges to exclude. */
		terrorismContent: Option[Schema.Adloox.TerrorismContentEnum] = None,
	  /** Optional. Misinformation Content [GARM](https://wfanet.org/leadership/garm/about-garm) risk ranges to exclude. */
		misinformationContent: Option[Schema.Adloox.MisinformationContentEnum] = None,
	  /** Optional. IAB viewability threshold for display ads. */
		displayIabViewability: Option[Schema.Adloox.DisplayIabViewabilityEnum] = None,
	  /** Optional. IAB viewability threshold for video ads. */
		videoIabViewability: Option[Schema.Adloox.VideoIabViewabilityEnum] = None
	)
	
	object DoubleVerify {
		enum AvoidedAgeRatingsEnum extends Enum[AvoidedAgeRatingsEnum] { case AGE_RATING_UNSPECIFIED, APP_AGE_RATE_UNKNOWN, APP_AGE_RATE_4_PLUS, APP_AGE_RATE_9_PLUS, APP_AGE_RATE_12_PLUS, APP_AGE_RATE_17_PLUS, APP_AGE_RATE_18_PLUS }
	}
	case class DoubleVerify(
	  /** DV Brand Safety Controls. */
		brandSafetyCategories: Option[Schema.DoubleVerifyBrandSafetyCategories] = None,
	  /** Avoid bidding on apps with the age rating. */
		avoidedAgeRatings: Option[List[Schema.DoubleVerify.AvoidedAgeRatingsEnum]] = None,
	  /** Avoid bidding on apps with the star ratings. */
		appStarRating: Option[Schema.DoubleVerifyAppStarRating] = None,
	  /** Display viewability settings (applicable to display line items only). */
		displayViewability: Option[Schema.DoubleVerifyDisplayViewability] = None,
	  /** Video viewability settings (applicable to video line items only). */
		videoViewability: Option[Schema.DoubleVerifyVideoViewability] = None,
	  /** Avoid Sites and Apps with historical Fraud & IVT Rates. */
		fraudInvalidTraffic: Option[Schema.DoubleVerifyFraudInvalidTraffic] = None,
	  /** The custom segment ID provided by DoubleVerify. The ID must start with "51" and consist of eight digits. Custom segment ID cannot be specified along with any of the following fields: &#42; brand_safety_categories &#42; avoided_age_ratings &#42; app_star_rating &#42; fraud_invalid_traffic */
		customSegmentId: Option[String] = None
	)
	
	object DoubleVerifyBrandSafetyCategories {
		enum AvoidedHighSeverityCategoriesEnum extends Enum[AvoidedHighSeverityCategoriesEnum] { case HIGHER_SEVERITY_UNSPECIFIED, ADULT_CONTENT_PORNOGRAPHY, COPYRIGHT_INFRINGEMENT, SUBSTANCE_ABUSE, GRAPHIC_VIOLENCE_WEAPONS, HATE_PROFANITY, CRIMINAL_SKILLS, NUISANCE_INCENTIVIZED_MALWARE_CLUTTER }
		enum AvoidedMediumSeverityCategoriesEnum extends Enum[AvoidedMediumSeverityCategoriesEnum] { case MEDIUM_SEVERITY_UNSPECIFIED, AD_SERVERS, ADULT_CONTENT_SWIMSUIT, ALTERNATIVE_LIFESTYLES, CELEBRITY_GOSSIP, GAMBLING, OCCULT, SEX_EDUCATION, DISASTER_AVIATION, DISASTER_MAN_MADE, DISASTER_NATURAL, DISASTER_TERRORIST_EVENTS, DISASTER_VEHICLE, ALCOHOL, SMOKING, NEGATIVE_NEWS_FINANCIAL, NON_ENGLISH, PARKING_PAGE, UNMODERATED_UGC, INFLAMMATORY_POLITICS_AND_NEWS, NEGATIVE_NEWS_PHARMACEUTICAL }
	}
	case class DoubleVerifyBrandSafetyCategories(
	  /** Unknown or unrateable. */
		avoidUnknownBrandSafetyCategory: Option[Boolean] = None,
	  /** Brand safety high severity avoidance categories. */
		avoidedHighSeverityCategories: Option[List[Schema.DoubleVerifyBrandSafetyCategories.AvoidedHighSeverityCategoriesEnum]] = None,
	  /** Brand safety medium severity avoidance categories. */
		avoidedMediumSeverityCategories: Option[List[Schema.DoubleVerifyBrandSafetyCategories.AvoidedMediumSeverityCategoriesEnum]] = None
	)
	
	object DoubleVerifyAppStarRating {
		enum AvoidedStarRatingEnum extends Enum[AvoidedStarRatingEnum] { case APP_STAR_RATE_UNSPECIFIED, APP_STAR_RATE_1_POINT_5_LESS, APP_STAR_RATE_2_LESS, APP_STAR_RATE_2_POINT_5_LESS, APP_STAR_RATE_3_LESS, APP_STAR_RATE_3_POINT_5_LESS, APP_STAR_RATE_4_LESS, APP_STAR_RATE_4_POINT_5_LESS }
	}
	case class DoubleVerifyAppStarRating(
	  /** Avoid bidding on apps with the star ratings. */
		avoidedStarRating: Option[Schema.DoubleVerifyAppStarRating.AvoidedStarRatingEnum] = None,
	  /** Avoid bidding on apps with insufficient star ratings. */
		avoidInsufficientStarRating: Option[Boolean] = None
	)
	
	object DoubleVerifyDisplayViewability {
		enum IabEnum extends Enum[IabEnum] { case IAB_VIEWED_RATE_UNSPECIFIED, IAB_VIEWED_RATE_80_PERCENT_HIGHER, IAB_VIEWED_RATE_75_PERCENT_HIGHER, IAB_VIEWED_RATE_70_PERCENT_HIGHER, IAB_VIEWED_RATE_65_PERCENT_HIGHER, IAB_VIEWED_RATE_60_PERCENT_HIGHER, IAB_VIEWED_RATE_55_PERCENT_HIGHER, IAB_VIEWED_RATE_50_PERCENT_HIGHER, IAB_VIEWED_RATE_40_PERCENT_HIGHER, IAB_VIEWED_RATE_30_PERCENT_HIGHER }
		enum ViewableDuringEnum extends Enum[ViewableDuringEnum] { case AVERAGE_VIEW_DURATION_UNSPECIFIED, AVERAGE_VIEW_DURATION_5_SEC, AVERAGE_VIEW_DURATION_10_SEC, AVERAGE_VIEW_DURATION_15_SEC }
	}
	case class DoubleVerifyDisplayViewability(
	  /** Target web and app inventory to maximize IAB viewable rate. */
		iab: Option[Schema.DoubleVerifyDisplayViewability.IabEnum] = None,
	  /** Target web and app inventory to maximize 100% viewable duration. */
		viewableDuring: Option[Schema.DoubleVerifyDisplayViewability.ViewableDuringEnum] = None
	)
	
	object DoubleVerifyVideoViewability {
		enum VideoIabEnum extends Enum[VideoIabEnum] { case VIDEO_IAB_UNSPECIFIED, IAB_VIEWABILITY_80_PERCENT_HIGHER, IAB_VIEWABILITY_75_PERCENT_HIGHER, IAB_VIEWABILITY_70_PERCENT_HIGHER, IAB_VIEWABILITY_65_PERCENT_HIHGER, IAB_VIEWABILITY_60_PERCENT_HIGHER, IAB_VIEWABILITY_55_PERCENT_HIHGER, IAB_VIEWABILITY_50_PERCENT_HIGHER, IAB_VIEWABILITY_40_PERCENT_HIHGER, IAB_VIEWABILITY_30_PERCENT_HIHGER }
		enum VideoViewableRateEnum extends Enum[VideoViewableRateEnum] { case VIDEO_VIEWABLE_RATE_UNSPECIFIED, VIEWED_PERFORMANCE_40_PERCENT_HIGHER, VIEWED_PERFORMANCE_35_PERCENT_HIGHER, VIEWED_PERFORMANCE_30_PERCENT_HIGHER, VIEWED_PERFORMANCE_25_PERCENT_HIGHER, VIEWED_PERFORMANCE_20_PERCENT_HIGHER, VIEWED_PERFORMANCE_10_PERCENT_HIGHER }
		enum PlayerImpressionRateEnum extends Enum[PlayerImpressionRateEnum] { case PLAYER_SIZE_400X300_UNSPECIFIED, PLAYER_SIZE_400X300_95, PLAYER_SIZE_400X300_70, PLAYER_SIZE_400X300_25, PLAYER_SIZE_400X300_5 }
	}
	case class DoubleVerifyVideoViewability(
	  /** Target web inventory to maximize IAB viewable rate. */
		videoIab: Option[Schema.DoubleVerifyVideoViewability.VideoIabEnum] = None,
	  /** Target web inventory to maximize fully viewable rate. */
		videoViewableRate: Option[Schema.DoubleVerifyVideoViewability.VideoViewableRateEnum] = None,
	  /** Target inventory to maximize impressions with 400x300 or greater player size. */
		playerImpressionRate: Option[Schema.DoubleVerifyVideoViewability.PlayerImpressionRateEnum] = None
	)
	
	object DoubleVerifyFraudInvalidTraffic {
		enum AvoidedFraudOptionEnum extends Enum[AvoidedFraudOptionEnum] { case FRAUD_UNSPECIFIED, AD_IMPRESSION_FRAUD_100, AD_IMPRESSION_FRAUD_50, AD_IMPRESSION_FRAUD_25, AD_IMPRESSION_FRAUD_10, AD_IMPRESSION_FRAUD_8, AD_IMPRESSION_FRAUD_6, AD_IMPRESSION_FRAUD_4, AD_IMPRESSION_FRAUD_2 }
	}
	case class DoubleVerifyFraudInvalidTraffic(
	  /** Avoid Sites and Apps with historical Fraud & IVT. */
		avoidedFraudOption: Option[Schema.DoubleVerifyFraudInvalidTraffic.AvoidedFraudOptionEnum] = None,
	  /** Insufficient Historical Fraud & IVT Stats. */
		avoidInsufficientOption: Option[Boolean] = None
	)
	
	object IntegralAdScience {
		enum TraqScoreOptionEnum extends Enum[TraqScoreOptionEnum] { case TRAQ_UNSPECIFIED, TRAQ_250, TRAQ_500, TRAQ_600, TRAQ_700, TRAQ_750, TRAQ_875, TRAQ_1000 }
		enum ExcludedAdultRiskEnum extends Enum[ExcludedAdultRiskEnum] { case ADULT_UNSPECIFIED, ADULT_HR, ADULT_HMR }
		enum ExcludedAlcoholRiskEnum extends Enum[ExcludedAlcoholRiskEnum] { case ALCOHOL_UNSPECIFIED, ALCOHOL_HR, ALCOHOL_HMR }
		enum ExcludedIllegalDownloadsRiskEnum extends Enum[ExcludedIllegalDownloadsRiskEnum] { case ILLEGAL_DOWNLOADS_UNSPECIFIED, ILLEGAL_DOWNLOADS_HR, ILLEGAL_DOWNLOADS_HMR }
		enum ExcludedDrugsRiskEnum extends Enum[ExcludedDrugsRiskEnum] { case DRUGS_UNSPECIFIED, DRUGS_HR, DRUGS_HMR }
		enum ExcludedHateSpeechRiskEnum extends Enum[ExcludedHateSpeechRiskEnum] { case HATE_SPEECH_UNSPECIFIED, HATE_SPEECH_HR, HATE_SPEECH_HMR }
		enum ExcludedOffensiveLanguageRiskEnum extends Enum[ExcludedOffensiveLanguageRiskEnum] { case OFFENSIVE_LANGUAGE_UNSPECIFIED, OFFENSIVE_LANGUAGE_HR, OFFENSIVE_LANGUAGE_HMR }
		enum ExcludedViolenceRiskEnum extends Enum[ExcludedViolenceRiskEnum] { case VIOLENCE_UNSPECIFIED, VIOLENCE_HR, VIOLENCE_HMR }
		enum ExcludedGamblingRiskEnum extends Enum[ExcludedGamblingRiskEnum] { case GAMBLING_UNSPECIFIED, GAMBLING_HR, GAMBLING_HMR }
		enum ExcludedAdFraudRiskEnum extends Enum[ExcludedAdFraudRiskEnum] { case SUSPICIOUS_ACTIVITY_UNSPECIFIED, SUSPICIOUS_ACTIVITY_HR, SUSPICIOUS_ACTIVITY_HMR }
		enum DisplayViewabilityEnum extends Enum[DisplayViewabilityEnum] { case PERFORMANCE_VIEWABILITY_UNSPECIFIED, PERFORMANCE_VIEWABILITY_40, PERFORMANCE_VIEWABILITY_50, PERFORMANCE_VIEWABILITY_60, PERFORMANCE_VIEWABILITY_70 }
		enum VideoViewabilityEnum extends Enum[VideoViewabilityEnum] { case VIDEO_VIEWABILITY_UNSPECIFIED, VIDEO_VIEWABILITY_40, VIDEO_VIEWABILITY_50, VIDEO_VIEWABILITY_60, VIDEO_VIEWABILITY_70 }
	}
	case class IntegralAdScience(
	  /** True advertising quality (applicable to Display line items only). */
		traqScoreOption: Option[Schema.IntegralAdScience.TraqScoreOptionEnum] = None,
	  /** Brand Safety - &#42;&#42;Unrateable&#42;&#42;. */
		excludeUnrateable: Option[Boolean] = None,
	  /** Brand Safety - &#42;&#42;Adult content&#42;&#42;. */
		excludedAdultRisk: Option[Schema.IntegralAdScience.ExcludedAdultRiskEnum] = None,
	  /** Brand Safety - &#42;&#42;Alcohol&#42;&#42;. */
		excludedAlcoholRisk: Option[Schema.IntegralAdScience.ExcludedAlcoholRiskEnum] = None,
	  /** Brand Safety - &#42;&#42;Illegal downloads&#42;&#42;. */
		excludedIllegalDownloadsRisk: Option[Schema.IntegralAdScience.ExcludedIllegalDownloadsRiskEnum] = None,
	  /** Brand Safety - &#42;&#42;Drugs&#42;&#42;. */
		excludedDrugsRisk: Option[Schema.IntegralAdScience.ExcludedDrugsRiskEnum] = None,
	  /** Brand Safety - &#42;&#42;Hate speech&#42;&#42;. */
		excludedHateSpeechRisk: Option[Schema.IntegralAdScience.ExcludedHateSpeechRiskEnum] = None,
	  /** Brand Safety - &#42;&#42;Offensive language&#42;&#42;. */
		excludedOffensiveLanguageRisk: Option[Schema.IntegralAdScience.ExcludedOffensiveLanguageRiskEnum] = None,
	  /** Brand Safety - &#42;&#42;Violence&#42;&#42;. */
		excludedViolenceRisk: Option[Schema.IntegralAdScience.ExcludedViolenceRiskEnum] = None,
	  /** Brand Safety - &#42;&#42;Gambling&#42;&#42;. */
		excludedGamblingRisk: Option[Schema.IntegralAdScience.ExcludedGamblingRiskEnum] = None,
	  /** Ad Fraud settings. */
		excludedAdFraudRisk: Option[Schema.IntegralAdScience.ExcludedAdFraudRiskEnum] = None,
	  /** Display Viewability section (applicable to display line items only). */
		displayViewability: Option[Schema.IntegralAdScience.DisplayViewabilityEnum] = None,
	  /** Video Viewability Section (applicable to video line items only). */
		videoViewability: Option[Schema.IntegralAdScience.VideoViewabilityEnum] = None,
	  /** The custom segment ID provided by Integral Ad Science. The ID must be between `1000001` and `1999999`, inclusive. */
		customSegmentId: Option[List[String]] = None
	)
	
	object PoiAssignedTargetingOptionDetails {
		enum ProximityRadiusUnitEnum extends Enum[ProximityRadiusUnitEnum] { case DISTANCE_UNIT_UNSPECIFIED, DISTANCE_UNIT_MILES, DISTANCE_UNIT_KILOMETERS }
	}
	case class PoiAssignedTargetingOptionDetails(
	  /** Output only. The display name of a POI, e.g. "Times Square", "Space Needle", followed by its full address if available. */
		displayName: Option[String] = None,
	  /** Required. The targeting_option_id of a TargetingOption of type `TARGETING_TYPE_POI`. Accepted POI targeting option IDs can be retrieved using `targetingTypes.targetingOptions.search`. If targeting a specific latitude/longitude coordinate removed from an address or POI name, you can generate the necessary targeting option ID by rounding the desired coordinate values to the 6th decimal place, removing the decimals, and concatenating the string values separated by a semicolon. For example, you can target the latitude/longitude pair of 40.7414691, -74.003387 using the targeting option ID "40741469;-74003387". &#42;&#42;Upon&#42;&#42; &#42;&#42;creation, this field value will be updated to append a semicolon and&#42;&#42; &#42;&#42;alphanumerical hash value if only latitude/longitude coordinates are&#42;&#42; &#42;&#42;provided.&#42;&#42; */
		targetingOptionId: Option[String] = None,
	  /** Output only. Latitude of the POI rounding to 6th decimal place. */
		latitude: Option[BigDecimal] = None,
	  /** Output only. Longitude of the POI rounding to 6th decimal place. */
		longitude: Option[BigDecimal] = None,
	  /** Required. The radius of the area around the POI that will be targeted. The units of the radius are specified by proximity_radius_unit. Must be 1 to 800 if unit is `DISTANCE_UNIT_KILOMETERS` and 1 to 500 if unit is `DISTANCE_UNIT_MILES`. */
		proximityRadiusAmount: Option[BigDecimal] = None,
	  /** Required. The unit of distance by which the targeting radius is measured. */
		proximityRadiusUnit: Option[Schema.PoiAssignedTargetingOptionDetails.ProximityRadiusUnitEnum] = None
	)
	
	object BusinessChainAssignedTargetingOptionDetails {
		enum ProximityRadiusUnitEnum extends Enum[ProximityRadiusUnitEnum] { case DISTANCE_UNIT_UNSPECIFIED, DISTANCE_UNIT_MILES, DISTANCE_UNIT_KILOMETERS }
	}
	case class BusinessChainAssignedTargetingOptionDetails(
	  /** Output only. The display name of a business chain, e.g. "KFC", "Chase Bank". */
		displayName: Option[String] = None,
	  /** Required. The targeting_option_id of a TargetingOption of type `TARGETING_TYPE_BUSINESS_CHAIN`. Accepted business chain targeting option IDs can be retrieved using SearchTargetingOptions. */
		targetingOptionId: Option[String] = None,
	  /** Required. The radius of the area around the business chain that will be targeted. The units of the radius are specified by proximity_radius_unit. Must be 1 to 800 if unit is `DISTANCE_UNIT_KILOMETERS` and 1 to 500 if unit is `DISTANCE_UNIT_MILES`. The minimum increment for both cases is 0.1. Inputs will be rounded to the nearest acceptable value if it is too granular, e.g. 15.57 will become 15.6. */
		proximityRadiusAmount: Option[BigDecimal] = None,
	  /** Required. The unit of distance by which the targeting radius is measured. */
		proximityRadiusUnit: Option[Schema.BusinessChainAssignedTargetingOptionDetails.ProximityRadiusUnitEnum] = None
	)
	
	object ContentDurationAssignedTargetingOptionDetails {
		enum ContentDurationEnum extends Enum[ContentDurationEnum] { case CONTENT_DURATION_UNSPECIFIED, CONTENT_DURATION_UNKNOWN, CONTENT_DURATION_0_TO_1_MIN, CONTENT_DURATION_1_TO_5_MIN, CONTENT_DURATION_5_TO_15_MIN, CONTENT_DURATION_15_TO_30_MIN, CONTENT_DURATION_30_TO_60_MIN, CONTENT_DURATION_OVER_60_MIN }
	}
	case class ContentDurationAssignedTargetingOptionDetails(
	  /** Required. The targeting_option_id field when targeting_type is `TARGETING_TYPE_CONTENT_DURATION`. */
		targetingOptionId: Option[String] = None,
	  /** Output only. The content duration. */
		contentDuration: Option[Schema.ContentDurationAssignedTargetingOptionDetails.ContentDurationEnum] = None
	)
	
	object ContentStreamTypeAssignedTargetingOptionDetails {
		enum ContentStreamTypeEnum extends Enum[ContentStreamTypeEnum] { case CONTENT_STREAM_TYPE_UNSPECIFIED, CONTENT_LIVE_STREAM, CONTENT_ON_DEMAND }
	}
	case class ContentStreamTypeAssignedTargetingOptionDetails(
	  /** Required. The targeting_option_id field when targeting_type is `TARGETING_TYPE_CONTENT_STREAM_TYPE`. */
		targetingOptionId: Option[String] = None,
	  /** Output only. The content stream type. */
		contentStreamType: Option[Schema.ContentStreamTypeAssignedTargetingOptionDetails.ContentStreamTypeEnum] = None
	)
	
	object NativeContentPositionAssignedTargetingOptionDetails {
		enum ContentPositionEnum extends Enum[ContentPositionEnum] { case NATIVE_CONTENT_POSITION_UNSPECIFIED, NATIVE_CONTENT_POSITION_UNKNOWN, NATIVE_CONTENT_POSITION_IN_ARTICLE, NATIVE_CONTENT_POSITION_IN_FEED, NATIVE_CONTENT_POSITION_PERIPHERAL, NATIVE_CONTENT_POSITION_RECOMMENDATION }
	}
	case class NativeContentPositionAssignedTargetingOptionDetails(
	  /** Required. The content position. */
		contentPosition: Option[Schema.NativeContentPositionAssignedTargetingOptionDetails.ContentPositionEnum] = None
	)
	
	object OmidAssignedTargetingOptionDetails {
		enum OmidEnum extends Enum[OmidEnum] { case OMID_UNSPECIFIED, OMID_FOR_MOBILE_DISPLAY_ADS }
	}
	case class OmidAssignedTargetingOptionDetails(
	  /** Required. The type of Open Measurement enabled inventory. */
		omid: Option[Schema.OmidAssignedTargetingOptionDetails.OmidEnum] = None
	)
	
	object AudioContentTypeAssignedTargetingOptionDetails {
		enum AudioContentTypeEnum extends Enum[AudioContentTypeEnum] { case AUDIO_CONTENT_TYPE_UNSPECIFIED, AUDIO_CONTENT_TYPE_UNKNOWN, AUDIO_CONTENT_TYPE_MUSIC, AUDIO_CONTENT_TYPE_BROADCAST, AUDIO_CONTENT_TYPE_PODCAST }
	}
	case class AudioContentTypeAssignedTargetingOptionDetails(
	  /** Required. The audio content type. */
		audioContentType: Option[Schema.AudioContentTypeAssignedTargetingOptionDetails.AudioContentTypeEnum] = None
	)
	
	case class ContentGenreAssignedTargetingOptionDetails(
	  /** Required. The targeting_option_id field when targeting_type is `TARGETING_TYPE_CONTENT_GENRE`. */
		targetingOptionId: Option[String] = None,
	  /** Output only. The display name of the content genre. */
		displayName: Option[String] = None,
	  /** Indicates if this option is being negatively targeted. */
		negative: Option[Boolean] = None
	)
	
	case class YoutubeVideoAssignedTargetingOptionDetails(
	  /** YouTube video id as it appears on the YouTube watch page. */
		videoId: Option[String] = None,
	  /** Indicates if this option is being negatively targeted. */
		negative: Option[Boolean] = None
	)
	
	case class YoutubeChannelAssignedTargetingOptionDetails(
	  /** The YouTube uploader channel id or the channel code of a YouTube channel. */
		channelId: Option[String] = None,
	  /** Indicates if this option is being negatively targeted. */
		negative: Option[Boolean] = None
	)
	
	object SessionPositionAssignedTargetingOptionDetails {
		enum SessionPositionEnum extends Enum[SessionPositionEnum] { case SESSION_POSITION_UNSPECIFIED, SESSION_POSITION_FIRST_IMPRESSION }
	}
	case class SessionPositionAssignedTargetingOptionDetails(
	  /** The position where the ad will show in a session. */
		sessionPosition: Option[Schema.SessionPositionAssignedTargetingOptionDetails.SessionPositionEnum] = None
	)
	
	case class ListAdGroupAssignedTargetingOptionsResponse(
	  /** The list of assigned targeting options. This list will be absent if empty. */
		assignedTargetingOptions: Option[List[Schema.AssignedTargetingOption]] = None,
	  /** A token identifying the next page of results. This value should be specified as the pageToken in a subsequent ListAdGroupAssignedTargetingOptionsRequest to fetch the next page of results. This token will be absent if there are no more AssignedTargetingOption resources to return. */
		nextPageToken: Option[String] = None
	)
	
	case class BulkListAdGroupAssignedTargetingOptionsResponse(
	  /** The list of wrapper objects, each providing an assigned targeting option and the ad group it is assigned to. This list will be absent if empty. */
		adGroupAssignedTargetingOptions: Option[List[Schema.AdGroupAssignedTargetingOption]] = None,
	  /** A token identifying the next page of results. This value should be specified as the pageToken in a subsequent call to `BulkListAdGroupAssignedTargetingOptions` to fetch the next page of results. This token will be absent if there are no more AdGroupAssignedTargetingOption resources to return. */
		nextPageToken: Option[String] = None
	)
	
	case class AdGroupAssignedTargetingOption(
	  /** The ID of the ad group the assigned targeting option is assigned to. */
		adGroupId: Option[String] = None,
	  /** The assigned targeting option resource. */
		assignedTargetingOption: Option[Schema.AssignedTargetingOption] = None
	)
	
	object AdGroup {
		enum AdGroupFormatEnum extends Enum[AdGroupFormatEnum] { case AD_GROUP_FORMAT_UNSPECIFIED, AD_GROUP_FORMAT_IN_STREAM, AD_GROUP_FORMAT_VIDEO_DISCOVERY, AD_GROUP_FORMAT_BUMPER, AD_GROUP_FORMAT_NON_SKIPPABLE_IN_STREAM, AD_GROUP_FORMAT_AUDIO, AD_GROUP_FORMAT_RESPONSIVE, AD_GROUP_FORMAT_REACH, AD_GROUP_FORMAT_MASTHEAD }
		enum EntityStatusEnum extends Enum[EntityStatusEnum] { case ENTITY_STATUS_UNSPECIFIED, ENTITY_STATUS_ACTIVE, ENTITY_STATUS_ARCHIVED, ENTITY_STATUS_DRAFT, ENTITY_STATUS_PAUSED, ENTITY_STATUS_SCHEDULED_FOR_DELETION }
	}
	case class AdGroup(
	  /** The resource name of the ad group. */
		name: Option[String] = None,
	  /** The unique ID of the advertiser the ad group belongs to. */
		advertiserId: Option[String] = None,
	  /** The unique ID of the ad group. Assigned by the system. */
		adGroupId: Option[String] = None,
	  /** The unique ID of the line item that the ad group belongs to. */
		lineItemId: Option[String] = None,
	  /** The display name of the ad group. Must be UTF-8 encoded with a maximum size of 255 bytes. */
		displayName: Option[String] = None,
	  /** The format of the ads in the ad group. */
		adGroupFormat: Option[Schema.AdGroup.AdGroupFormatEnum] = None,
	  /** The bidding strategy used by the ad group. Only the youtubeAndPartnersBid field can be used in the bidding strategy. */
		bidStrategy: Option[Schema.BiddingStrategy] = None,
	  /** Controls whether or not the ad group can spend its budget and bid on inventory. If the ad group's parent line item is not active, the ad group can't spend its budget even if its own status is `ENTITY_STATUS_ACTIVE`. */
		entityStatus: Option[Schema.AdGroup.EntityStatusEnum] = None,
	  /** The [optimized targeting](//support.google.com/displayvideo/answer/12060859) settings of the ad group. */
		targetingExpansion: Option[Schema.TargetingExpansionConfig] = None,
	  /** The settings of the product feed in this ad group. */
		productFeedData: Option[Schema.ProductFeedData] = None
	)
	
	case class BiddingStrategy(
	  /** A strategy that uses a fixed bid price. */
		fixedBid: Option[Schema.FixedBidStrategy] = None,
	  /** A strategy that automatically adjusts the bid to optimize to your performance goal while spending the full budget. At insertion order level, the markup_type of line items cannot be set to `PARTNER_REVENUE_MODEL_MARKUP_TYPE_CPM`. In addition, when performance_goal_type is one of: &#42; `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_CPA` &#42; `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_CPC` &#42; `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_AV_VIEWED` , the line_item_type of the insertion order line items must be either: &#42; `LINE_ITEM_TYPE_DISPLAY_DEFAULT` &#42; `LINE_ITEM_TYPE_VIDEO_DEFAULT` , and when performance_goal_type is either: &#42; `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_CIVA` &#42; `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_IVO_TEN` the line_item_type of the insertion order line items must be `LINE_ITEM_TYPE_VIDEO_DEFAULT`. */
		maximizeSpendAutoBid: Option[Schema.MaximizeSpendBidStrategy] = None,
	  /** A strategy that automatically adjusts the bid to meet or beat a specified performance goal. It is to be used only for a line item entity. */
		performanceGoalAutoBid: Option[Schema.PerformanceGoalBidStrategy] = None,
	  /** A bid strategy used by YouTube and Partners resources. It can only be used for a YouTube and Partners line item or ad group entity. */
		youtubeAndPartnersBid: Option[Schema.YoutubeAndPartnersBiddingStrategy] = None
	)
	
	case class FixedBidStrategy(
	  /** The fixed bid amount, in micros of the advertiser's currency. For insertion order entity, bid_amount_micros should be set as 0. For line item entity, bid_amount_micros must be greater than or equal to billable unit of the given currency and smaller than or equal to the upper limit 1000000000. For example, 1500000 represents 1.5 standard units of the currency. */
		bidAmountMicros: Option[String] = None
	)
	
	object MaximizeSpendBidStrategy {
		enum PerformanceGoalTypeEnum extends Enum[PerformanceGoalTypeEnum] { case BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_UNSPECIFIED, BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_CPA, BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_CPC, BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_VIEWABLE_CPM, BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_CUSTOM_ALGO, BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_CIVA, BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_IVO_TEN, BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_AV_VIEWED }
	}
	case class MaximizeSpendBidStrategy(
	  /** Required. The type of the performance goal that the bidding strategy tries to minimize while spending the full budget. `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_VIEWABLE_CPM` is not supported for this strategy. */
		performanceGoalType: Option[Schema.MaximizeSpendBidStrategy.PerformanceGoalTypeEnum] = None,
	  /** The maximum average CPM that may be bid, in micros of the advertiser's currency. Must be greater than or equal to a billable unit of the given currency. For example, 1500000 represents 1.5 standard units of the currency. */
		maxAverageCpmBidAmountMicros: Option[String] = None,
	  /** Whether the strategy takes deal floor prices into account. */
		raiseBidForDeals: Option[Boolean] = None,
	  /** The ID of the Custom Bidding Algorithm used by this strategy. Only applicable when performance_goal_type is set to `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_CUSTOM_ALGO`. */
		customBiddingAlgorithmId: Option[String] = None
	)
	
	object PerformanceGoalBidStrategy {
		enum PerformanceGoalTypeEnum extends Enum[PerformanceGoalTypeEnum] { case BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_UNSPECIFIED, BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_CPA, BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_CPC, BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_VIEWABLE_CPM, BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_CUSTOM_ALGO, BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_CIVA, BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_IVO_TEN, BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_AV_VIEWED }
	}
	case class PerformanceGoalBidStrategy(
	  /** Required. The type of the performance goal that the bidding strategy will try to meet or beat. For line item level usage, the value must be one of: &#42; `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_CPA` &#42; `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_CPC` &#42; `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_VIEWABLE_CPM` &#42; `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_CUSTOM_ALGO`. */
		performanceGoalType: Option[Schema.PerformanceGoalBidStrategy.PerformanceGoalTypeEnum] = None,
	  /** Required. The performance goal the bidding strategy will attempt to meet or beat, in micros of the advertiser's currency or in micro of the ROAS (Return On Advertising Spend) value which is also based on advertiser's currency. Must be greater than or equal to a billable unit of the given currency and smaller or equal to upper bounds. Each performance_goal_type has its upper bound: &#42; when performance_goal_type is `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_CPA`, upper bound is 10000.00 USD. &#42; when performance_goal_type is `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_CPC`, upper bound is 1000.00 USD. &#42; when performance_goal_type is `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_VIEWABLE_CPM`, upper bound is 1000.00 USD. &#42; when performance_goal_type is `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_CUSTOM_ALGO`, upper bound is 1000.00 and lower bound is 0.01. Example: If set to `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_VIEWABLE_CPM`, the bid price will be based on the probability that each available impression will be viewable. For example, if viewable CPM target is $2 and an impression is 40% likely to be viewable, the bid price will be $0.80 CPM (40% of $2). For example, 1500000 represents 1.5 standard units of the currency or ROAS value. */
		performanceGoalAmountMicros: Option[String] = None,
	  /** The maximum average CPM that may be bid, in micros of the advertiser's currency. Must be greater than or equal to a billable unit of the given currency. Not applicable when performance_goal_type is set to `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_VIEWABLE_CPM`. For example, 1500000 represents 1.5 standard units of the currency. */
		maxAverageCpmBidAmountMicros: Option[String] = None,
	  /** The ID of the Custom Bidding Algorithm used by this strategy. Only applicable when performance_goal_type is set to `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_CUSTOM_ALGO`. */
		customBiddingAlgorithmId: Option[String] = None
	)
	
	object YoutubeAndPartnersBiddingStrategy {
		enum TypeEnum extends Enum[TypeEnum] { case YOUTUBE_AND_PARTNERS_BIDDING_STRATEGY_TYPE_UNSPECIFIED, YOUTUBE_AND_PARTNERS_BIDDING_STRATEGY_TYPE_MANUAL_CPV, YOUTUBE_AND_PARTNERS_BIDDING_STRATEGY_TYPE_MANUAL_CPM, YOUTUBE_AND_PARTNERS_BIDDING_STRATEGY_TYPE_TARGET_CPA, YOUTUBE_AND_PARTNERS_BIDDING_STRATEGY_TYPE_TARGET_CPM, YOUTUBE_AND_PARTNERS_BIDDING_STRATEGY_TYPE_RESERVE_CPM, YOUTUBE_AND_PARTNERS_BIDDING_STRATEGY_TYPE_MAXIMIZE_LIFT, YOUTUBE_AND_PARTNERS_BIDDING_STRATEGY_TYPE_MAXIMIZE_CONVERSIONS, YOUTUBE_AND_PARTNERS_BIDDING_STRATEGY_TYPE_TARGET_CPV, YOUTUBE_AND_PARTNERS_BIDDING_STRATEGY_TYPE_TARGET_ROAS, YOUTUBE_AND_PARTNERS_BIDDING_STRATEGY_TYPE_MAXIMIZE_CONVERSION_VALUE }
		enum AdGroupEffectiveTargetCpaSourceEnum extends Enum[AdGroupEffectiveTargetCpaSourceEnum] { case BIDDING_SOURCE_UNSPECIFIED, BIDDING_SOURCE_LINE_ITEM, BIDDING_SOURCE_AD_GROUP }
	}
	case class YoutubeAndPartnersBiddingStrategy(
	  /** The type of the bidding strategy. */
		`type`: Option[Schema.YoutubeAndPartnersBiddingStrategy.TypeEnum] = None,
	  /** The value used by the bidding strategy. When the bidding strategy is assigned at the line item level, this field is only applicable for the following strategy types: &#42; `YOUTUBE_AND_PARTNERS_BIDDING_STRATEGY_TYPE_TARGET_CPA` &#42; `YOUTUBE_AND_PARTNERS_BIDDING_STRATEGY_TYPE_TARGET_ROAS` When the bidding strategy is assigned at the ad group level, this field is only applicable for the following strategy types: &#42; `YOUTUBE_AND_PARTNERS_BIDDING_STRATEGY_TYPE_MANUAL_CPM` &#42; `YOUTUBE_AND_PARTNERS_BIDDING_STRATEGY_TYPE_MANUAL_CPV` &#42; `YOUTUBE_AND_PARTNERS_BIDDING_STRATEGY_TYPE_TARGET_CPA` &#42; `YOUTUBE_AND_PARTNERS_BIDDING_STRATEGY_TYPE_TARGET_CPM` &#42; `YOUTUBE_AND_PARTNERS_BIDDING_STRATEGY_TYPE_RESERVE_CPM` &#42; `YOUTUBE_AND_PARTNERS_BIDDING_STRATEGY_TYPE_TARGET_ROAS` If not using an applicable strategy, the value of this field will be 0. */
		value: Option[String] = None,
	  /** Output only. The effective target CPA for ad group, in micros of advertiser's currency. */
		adGroupEffectiveTargetCpaValue: Option[String] = None,
	  /** Output only. Source of the effective target CPA value for ad group. */
		adGroupEffectiveTargetCpaSource: Option[Schema.YoutubeAndPartnersBiddingStrategy.AdGroupEffectiveTargetCpaSourceEnum] = None
	)
	
	object TargetingExpansionConfig {
		enum AudienceExpansionLevelEnum extends Enum[AudienceExpansionLevelEnum] { case UNKNOWN, NO_REACH, LEAST_REACH, MID_REACH, MOST_REACH }
	}
	case class TargetingExpansionConfig(
	  /** Required. Whether to enable Optimized Targeting for the line item. Optimized targeting is not compatible with all bid strategies. Attempting to set this field to `true` for a line item using one of the following combinations of BiddingStrategy fields and BiddingStrategyPerformanceGoalType will result in an error: maximize_auto_spend_bid: &#42; `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_CIVA` &#42; `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_IVO_TEN` &#42; `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_AV_VIEWED` performance_goal_auto_bid: &#42; `BIDDING_STRATEGY_PERFORMANCE_GOAL_TYPE_VIEWABLE_CPM` */
		enableOptimizedTargeting: Option[Boolean] = None,
	  /** Output only. Whether to exclude seed list for audience expansion. This field only applies to YouTube and Partners line item and ad group resources. */
		audienceExpansionSeedListExcluded: Option[Boolean] = None,
	  /** Output only. Magnitude of expansion for eligible first-party user lists under this ad group. This field only applies to YouTube and Partners line item and ad group resources. */
		audienceExpansionLevel: Option[Schema.TargetingExpansionConfig.AudienceExpansionLevelEnum] = None
	)
	
	object ProductFeedData {
		enum ProductMatchTypeEnum extends Enum[ProductMatchTypeEnum] { case PRODUCT_MATCH_TYPE_UNSPECIFIED, PRODUCT_MATCH_TYPE_ALL_PRODUCTS, PRODUCT_MATCH_TYPE_SPECIFIC_PRODUCTS, PRODUCT_MATCH_TYPE_CUSTOM_LABEL }
	}
	case class ProductFeedData(
	  /** How products are selected by the product feed. */
		productMatchType: Option[Schema.ProductFeedData.ProductMatchTypeEnum] = None,
	  /** A list of dimensions used to match products. */
		productMatchDimensions: Option[List[Schema.ProductMatchDimension]] = None,
	  /** Whether the product feed has opted-out of showing products. */
		isFeedDisabled: Option[Boolean] = None
	)
	
	case class ProductMatchDimension(
	  /** The ID of the product offer to match with a product with the same offer ID. */
		productOfferId: Option[String] = None,
	  /** The custom label to match all the products with the label. */
		customLabel: Option[Schema.CustomLabel] = None
	)
	
	object CustomLabel {
		enum KeyEnum extends Enum[KeyEnum] { case CUSTOM_LABEL_KEY_UNSPECIFIED, CUSTOM_LABEL_KEY_0, CUSTOM_LABEL_KEY_1, CUSTOM_LABEL_KEY_2, CUSTOM_LABEL_KEY_3, CUSTOM_LABEL_KEY_4 }
	}
	case class CustomLabel(
	  /** The key of the label. */
		key: Option[Schema.CustomLabel.KeyEnum] = None,
	  /** The value of the label. */
		value: Option[String] = None
	)
	
	case class ListAdGroupsResponse(
	  /** The list of ad groups. This list will be absent if empty. */
		adGroups: Option[List[Schema.AdGroup]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListAdGroups` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class ListLineItemAssignedTargetingOptionsResponse(
	  /** The list of assigned targeting options. This list will be absent if empty. */
		assignedTargetingOptions: Option[List[Schema.AssignedTargetingOption]] = None,
	  /** A token identifying the next page of results. This value should be specified as the pageToken in a subsequent ListLineItemAssignedTargetingOptionsRequest to fetch the next page of results. This token will be absent if there are no more assigned_targeting_options to return. */
		nextPageToken: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class BulkListAssignedTargetingOptionsResponse(
	  /** The list of wrapper objects, each providing an assigned targeting option and the line item it is assigned to. This list will be absent if empty. */
		lineItemAssignedTargetingOptions: Option[List[Schema.LineItemAssignedTargetingOption]] = None,
	  /** A token identifying the next page of results. This value should be specified as the pageToken in a subsequent call to `BulkListAssignedTargetingOptions` to fetch the next page of results. This token will be absent if there are no more line_item_assigned_targeting_options to return. */
		nextPageToken: Option[String] = None
	)
	
	case class LineItemAssignedTargetingOption(
	  /** The ID of the line item the assigned targeting option is assigned to. */
		lineItemId: Option[String] = None,
	  /** The assigned targeting option resource. */
		assignedTargetingOption: Option[Schema.AssignedTargetingOption] = None
	)
	
	case class BulkEditAssignedTargetingOptionsRequest(
	  /** Required. The ID of the line items whose targeting is being updated. */
		lineItemIds: Option[List[String]] = None,
	  /** The assigned targeting options to delete in batch, specified as a list of DeleteAssignedTargetingOptionsRequest. Supported targeting types include: &#42; `TARGETING_TYPE_AGE_RANGE` &#42; `TARGETING_TYPE_APP` &#42; `TARGETING_TYPE_APP_CATEGORY` &#42; `TARGETING_TYPE_AUDIENCE_GROUP` &#42; `TARGETING_TYPE_AUDIO_CONTENT_TYPE` &#42; `TARGETING_TYPE_AUTHORIZED_SELLER_STATUS` &#42; `TARGETING_TYPE_BROWSER` &#42; `TARGETING_TYPE_BUSINESS_CHAIN` &#42; `TARGETING_TYPE_CARRIER_AND_ISP` &#42; `TARGETING_TYPE_CATEGORY` &#42; `TARGETING_TYPE_CHANNEL` &#42; `TARGETING_TYPE_CONTENT_DURATION` &#42; `TARGETING_TYPE_CONTENT_GENRE` &#42; `TARGETING_TYPE_CONTENT_INSTREAM_POSITION` &#42; `TARGETING_TYPE_CONTENT_OUTSTREAM_POSITION` &#42; `TARGETING_TYPE_CONTENT_STREAM_TYPE` &#42; `TARGETING_TYPE_DAY_AND_TIME` &#42; `TARGETING_TYPE_DEVICE_MAKE_MODEL` &#42; `TARGETING_TYPE_DEVICE_TYPE` &#42; `TARGETING_TYPE_DIGITAL_CONTENT_LABEL_EXCLUSION` &#42; `TARGETING_TYPE_ENVIRONMENT` &#42; `TARGETING_TYPE_EXCHANGE` &#42; `TARGETING_TYPE_GENDER` &#42; `TARGETING_TYPE_GEO_REGION` &#42; `TARGETING_TYPE_HOUSEHOLD_INCOME` &#42; `TARGETING_TYPE_INVENTORY_SOURCE` &#42; `TARGETING_TYPE_INVENTORY_SOURCE_GROUP` &#42; `TARGETING_TYPE_KEYWORD` &#42; `TARGETING_TYPE_LANGUAGE` &#42; `TARGETING_TYPE_NATIVE_CONTENT_POSITION` &#42; `TARGETING_TYPE_NEGATIVE_KEYWORD_LIST` &#42; `TARGETING_TYPE_OMID` &#42; `TARGETING_TYPE_ON_SCREEN_POSITION` &#42; `TARGETING_TYPE_OPERATING_SYSTEM` &#42; `TARGETING_TYPE_PARENTAL_STATUS` &#42; `TARGETING_TYPE_POI` &#42; `TARGETING_TYPE_PROXIMITY_LOCATION_LIST` &#42; `TARGETING_TYPE_REGIONAL_LOCATION_LIST` &#42; `TARGETING_TYPE_SENSITIVE_CATEGORY_EXCLUSION` &#42; `TARGETING_TYPE_SUB_EXCHANGE` &#42; `TARGETING_TYPE_THIRD_PARTY_VERIFIER` &#42; `TARGETING_TYPE_URL` &#42; `TARGETING_TYPE_USER_REWARDED_CONTENT` &#42; `TARGETING_TYPE_VIDEO_PLAYER_SIZE` &#42; `TARGETING_TYPE_VIEWABILITY` */
		deleteRequests: Option[List[Schema.DeleteAssignedTargetingOptionsRequest]] = None,
	  /** The assigned targeting options to create in batch, specified as a list of CreateAssignedTargetingOptionsRequest. Supported targeting types include: &#42; `TARGETING_TYPE_AGE_RANGE` &#42; `TARGETING_TYPE_APP` &#42; `TARGETING_TYPE_APP_CATEGORY` &#42; `TARGETING_TYPE_AUDIENCE_GROUP` &#42; `TARGETING_TYPE_AUDIO_CONTENT_TYPE` &#42; `TARGETING_TYPE_AUTHORIZED_SELLER_STATUS` &#42; `TARGETING_TYPE_BROWSER` &#42; `TARGETING_TYPE_BUSINESS_CHAIN` &#42; `TARGETING_TYPE_CARRIER_AND_ISP` &#42; `TARGETING_TYPE_CATEGORY` &#42; `TARGETING_TYPE_CHANNEL` &#42; `TARGETING_TYPE_CONTENT_DURATION` &#42; `TARGETING_TYPE_CONTENT_GENRE` &#42; `TARGETING_TYPE_CONTENT_INSTREAM_POSITION` &#42; `TARGETING_TYPE_CONTENT_OUTSTREAM_POSITION` &#42; `TARGETING_TYPE_CONTENT_STREAM_TYPE` &#42; `TARGETING_TYPE_DAY_AND_TIME` &#42; `TARGETING_TYPE_DEVICE_MAKE_MODEL` &#42; `TARGETING_TYPE_DEVICE_TYPE` &#42; `TARGETING_TYPE_DIGITAL_CONTENT_LABEL_EXCLUSION` &#42; `TARGETING_TYPE_ENVIRONMENT` &#42; `TARGETING_TYPE_EXCHANGE` &#42; `TARGETING_TYPE_GENDER` &#42; `TARGETING_TYPE_GEO_REGION` &#42; `TARGETING_TYPE_HOUSEHOLD_INCOME` &#42; `TARGETING_TYPE_INVENTORY_SOURCE` &#42; `TARGETING_TYPE_INVENTORY_SOURCE_GROUP` &#42; `TARGETING_TYPE_KEYWORD` &#42; `TARGETING_TYPE_LANGUAGE` &#42; `TARGETING_TYPE_NATIVE_CONTENT_POSITION` &#42; `TARGETING_TYPE_NEGATIVE_KEYWORD_LIST` &#42; `TARGETING_TYPE_OMID` &#42; `TARGETING_TYPE_ON_SCREEN_POSITION` &#42; `TARGETING_TYPE_OPERATING_SYSTEM` &#42; `TARGETING_TYPE_PARENTAL_STATUS` &#42; `TARGETING_TYPE_POI` &#42; `TARGETING_TYPE_PROXIMITY_LOCATION_LIST` &#42; `TARGETING_TYPE_REGIONAL_LOCATION_LIST` &#42; `TARGETING_TYPE_SENSITIVE_CATEGORY_EXCLUSION` &#42; `TARGETING_TYPE_SUB_EXCHANGE` &#42; `TARGETING_TYPE_THIRD_PARTY_VERIFIER` &#42; `TARGETING_TYPE_URL` &#42; `TARGETING_TYPE_USER_REWARDED_CONTENT` &#42; `TARGETING_TYPE_VIDEO_PLAYER_SIZE` &#42; `TARGETING_TYPE_VIEWABILITY` */
		createRequests: Option[List[Schema.CreateAssignedTargetingOptionsRequest]] = None
	)
	
	object DeleteAssignedTargetingOptionsRequest {
		enum TargetingTypeEnum extends Enum[TargetingTypeEnum] { case TARGETING_TYPE_UNSPECIFIED, TARGETING_TYPE_CHANNEL, TARGETING_TYPE_APP_CATEGORY, TARGETING_TYPE_APP, TARGETING_TYPE_URL, TARGETING_TYPE_DAY_AND_TIME, TARGETING_TYPE_AGE_RANGE, TARGETING_TYPE_REGIONAL_LOCATION_LIST, TARGETING_TYPE_PROXIMITY_LOCATION_LIST, TARGETING_TYPE_GENDER, TARGETING_TYPE_VIDEO_PLAYER_SIZE, TARGETING_TYPE_USER_REWARDED_CONTENT, TARGETING_TYPE_PARENTAL_STATUS, TARGETING_TYPE_CONTENT_INSTREAM_POSITION, TARGETING_TYPE_CONTENT_OUTSTREAM_POSITION, TARGETING_TYPE_DEVICE_TYPE, TARGETING_TYPE_AUDIENCE_GROUP, TARGETING_TYPE_BROWSER, TARGETING_TYPE_HOUSEHOLD_INCOME, TARGETING_TYPE_ON_SCREEN_POSITION, TARGETING_TYPE_THIRD_PARTY_VERIFIER, TARGETING_TYPE_DIGITAL_CONTENT_LABEL_EXCLUSION, TARGETING_TYPE_SENSITIVE_CATEGORY_EXCLUSION, TARGETING_TYPE_ENVIRONMENT, TARGETING_TYPE_CARRIER_AND_ISP, TARGETING_TYPE_OPERATING_SYSTEM, TARGETING_TYPE_DEVICE_MAKE_MODEL, TARGETING_TYPE_KEYWORD, TARGETING_TYPE_NEGATIVE_KEYWORD_LIST, TARGETING_TYPE_VIEWABILITY, TARGETING_TYPE_CATEGORY, TARGETING_TYPE_INVENTORY_SOURCE, TARGETING_TYPE_LANGUAGE, TARGETING_TYPE_AUTHORIZED_SELLER_STATUS, TARGETING_TYPE_GEO_REGION, TARGETING_TYPE_INVENTORY_SOURCE_GROUP, TARGETING_TYPE_EXCHANGE, TARGETING_TYPE_SUB_EXCHANGE, TARGETING_TYPE_POI, TARGETING_TYPE_BUSINESS_CHAIN, TARGETING_TYPE_CONTENT_DURATION, TARGETING_TYPE_CONTENT_STREAM_TYPE, TARGETING_TYPE_NATIVE_CONTENT_POSITION, TARGETING_TYPE_OMID, TARGETING_TYPE_AUDIO_CONTENT_TYPE, TARGETING_TYPE_CONTENT_GENRE, TARGETING_TYPE_YOUTUBE_VIDEO, TARGETING_TYPE_YOUTUBE_CHANNEL, TARGETING_TYPE_SESSION_POSITION }
	}
	case class DeleteAssignedTargetingOptionsRequest(
	  /** Required. Identifies the type of this assigned targeting option. */
		targetingType: Option[Schema.DeleteAssignedTargetingOptionsRequest.TargetingTypeEnum] = None,
	  /** Required. The assigned targeting option IDs to delete. */
		assignedTargetingOptionIds: Option[List[String]] = None
	)
	
	object CreateAssignedTargetingOptionsRequest {
		enum TargetingTypeEnum extends Enum[TargetingTypeEnum] { case TARGETING_TYPE_UNSPECIFIED, TARGETING_TYPE_CHANNEL, TARGETING_TYPE_APP_CATEGORY, TARGETING_TYPE_APP, TARGETING_TYPE_URL, TARGETING_TYPE_DAY_AND_TIME, TARGETING_TYPE_AGE_RANGE, TARGETING_TYPE_REGIONAL_LOCATION_LIST, TARGETING_TYPE_PROXIMITY_LOCATION_LIST, TARGETING_TYPE_GENDER, TARGETING_TYPE_VIDEO_PLAYER_SIZE, TARGETING_TYPE_USER_REWARDED_CONTENT, TARGETING_TYPE_PARENTAL_STATUS, TARGETING_TYPE_CONTENT_INSTREAM_POSITION, TARGETING_TYPE_CONTENT_OUTSTREAM_POSITION, TARGETING_TYPE_DEVICE_TYPE, TARGETING_TYPE_AUDIENCE_GROUP, TARGETING_TYPE_BROWSER, TARGETING_TYPE_HOUSEHOLD_INCOME, TARGETING_TYPE_ON_SCREEN_POSITION, TARGETING_TYPE_THIRD_PARTY_VERIFIER, TARGETING_TYPE_DIGITAL_CONTENT_LABEL_EXCLUSION, TARGETING_TYPE_SENSITIVE_CATEGORY_EXCLUSION, TARGETING_TYPE_ENVIRONMENT, TARGETING_TYPE_CARRIER_AND_ISP, TARGETING_TYPE_OPERATING_SYSTEM, TARGETING_TYPE_DEVICE_MAKE_MODEL, TARGETING_TYPE_KEYWORD, TARGETING_TYPE_NEGATIVE_KEYWORD_LIST, TARGETING_TYPE_VIEWABILITY, TARGETING_TYPE_CATEGORY, TARGETING_TYPE_INVENTORY_SOURCE, TARGETING_TYPE_LANGUAGE, TARGETING_TYPE_AUTHORIZED_SELLER_STATUS, TARGETING_TYPE_GEO_REGION, TARGETING_TYPE_INVENTORY_SOURCE_GROUP, TARGETING_TYPE_EXCHANGE, TARGETING_TYPE_SUB_EXCHANGE, TARGETING_TYPE_POI, TARGETING_TYPE_BUSINESS_CHAIN, TARGETING_TYPE_CONTENT_DURATION, TARGETING_TYPE_CONTENT_STREAM_TYPE, TARGETING_TYPE_NATIVE_CONTENT_POSITION, TARGETING_TYPE_OMID, TARGETING_TYPE_AUDIO_CONTENT_TYPE, TARGETING_TYPE_CONTENT_GENRE, TARGETING_TYPE_YOUTUBE_VIDEO, TARGETING_TYPE_YOUTUBE_CHANNEL, TARGETING_TYPE_SESSION_POSITION }
	}
	case class CreateAssignedTargetingOptionsRequest(
	  /** Required. Identifies the type of this assigned targeting option. */
		targetingType: Option[Schema.CreateAssignedTargetingOptionsRequest.TargetingTypeEnum] = None,
	  /** Required. The assigned targeting options to create and add. */
		assignedTargetingOptions: Option[List[Schema.AssignedTargetingOption]] = None
	)
	
	case class BulkEditAssignedTargetingOptionsResponse(
	  /** Output only. The IDs of the line items which successfully updated. */
		updatedLineItemIds: Option[List[String]] = None,
	  /** Output only. The IDs of the line items which failed. */
		failedLineItemIds: Option[List[String]] = None,
	  /** The error information for each line item that failed to update. */
		errors: Option[List[Schema.Status]] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class ListAdvertiserAssignedTargetingOptionsResponse(
	  /** The list of assigned targeting options. This list will be absent if empty. */
		assignedTargetingOptions: Option[List[Schema.AssignedTargetingOption]] = None,
	  /** A token identifying the next page of results. This value should be specified as the pageToken in a subsequent ListAdvertiserAssignedTargetingOptionsRequest to fetch the next page of results. This token will be absent if there are no more assigned_targeting_options to return. */
		nextPageToken: Option[String] = None
	)
	
	case class BulkListAdvertiserAssignedTargetingOptionsResponse(
	  /** The list of assigned targeting options. This list will be absent if empty. */
		assignedTargetingOptions: Option[List[Schema.AssignedTargetingOption]] = None,
	  /** A token identifying the next page of results. This value should be specified as the pageToken in a subsequent BulkListAdvertiserAssignedTargetingOptionsRequest to fetch the next page of results. This token will be absent if there are no more assigned_targeting_options to return. */
		nextPageToken: Option[String] = None
	)
	
	case class BulkEditAdvertiserAssignedTargetingOptionsRequest(
	  /** The assigned targeting options to delete in batch, specified as a list of `DeleteAssignedTargetingOptionsRequest`. Supported targeting types: &#42; `TARGETING_TYPE_CHANNEL` &#42; `TARGETING_TYPE_DIGITAL_CONTENT_LABEL_EXCLUSION` &#42; `TARGETING_TYPE_OMID` &#42; `TARGETING_TYPE_SENSITIVE_CATEGORY_EXCLUSION` &#42; `TARGETING_TYPE_KEYWORD` */
		deleteRequests: Option[List[Schema.DeleteAssignedTargetingOptionsRequest]] = None,
	  /** The assigned targeting options to create in batch, specified as a list of `CreateAssignedTargetingOptionsRequest`. Supported targeting types: &#42; `TARGETING_TYPE_CHANNEL` &#42; `TARGETING_TYPE_DIGITAL_CONTENT_LABEL_EXCLUSION` &#42; `TARGETING_TYPE_OMID` &#42; `TARGETING_TYPE_SENSITIVE_CATEGORY_EXCLUSION` &#42; `TARGETING_TYPE_KEYWORD` */
		createRequests: Option[List[Schema.CreateAssignedTargetingOptionsRequest]] = None
	)
	
	case class BulkEditAdvertiserAssignedTargetingOptionsResponse(
	  /** The list of assigned targeting options that have been successfully created. This list will be absent if empty. */
		createdAssignedTargetingOptions: Option[List[Schema.AssignedTargetingOption]] = None
	)
	
	object Advertiser {
		enum EntityStatusEnum extends Enum[EntityStatusEnum] { case ENTITY_STATUS_UNSPECIFIED, ENTITY_STATUS_ACTIVE, ENTITY_STATUS_ARCHIVED, ENTITY_STATUS_DRAFT, ENTITY_STATUS_PAUSED, ENTITY_STATUS_SCHEDULED_FOR_DELETION }
	}
	case class Advertiser(
	  /** Output only. The resource name of the advertiser. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the advertiser. Assigned by the system. */
		advertiserId: Option[String] = None,
	  /** Required. Immutable. The unique ID of the partner that the advertiser belongs to. */
		partnerId: Option[String] = None,
	  /** Required. The display name of the advertiser. Must be UTF-8 encoded with a maximum size of 240 bytes. */
		displayName: Option[String] = None,
	  /** Required. Controls whether or not insertion orders and line items of the advertiser can spend their budgets and bid on inventory. &#42; Accepted values are `ENTITY_STATUS_ACTIVE`, `ENTITY_STATUS_PAUSED` and `ENTITY_STATUS_SCHEDULED_FOR_DELETION`. &#42; If set to `ENTITY_STATUS_SCHEDULED_FOR_DELETION`, the advertiser will be deleted 30 days from when it was first scheduled for deletion. */
		entityStatus: Option[Schema.Advertiser.EntityStatusEnum] = None,
	  /** Output only. The timestamp when the advertiser was last updated. Assigned by the system. */
		updateTime: Option[String] = None,
	  /** Required. General settings of the advertiser. */
		generalConfig: Option[Schema.AdvertiserGeneralConfig] = None,
	  /** Required. Immutable. Ad server related settings of the advertiser. */
		adServerConfig: Option[Schema.AdvertiserAdServerConfig] = None,
	  /** Required. Creative related settings of the advertiser. */
		creativeConfig: Option[Schema.AdvertiserCreativeConfig] = None,
	  /** Settings that control how advertiser data may be accessed. */
		dataAccessConfig: Option[Schema.AdvertiserDataAccessConfig] = None,
	  /** Integration details of the advertiser. Only integrationCode is currently applicable to advertiser. Other fields of IntegrationDetails are not supported and will be ignored if provided. */
		integrationDetails: Option[Schema.IntegrationDetails] = None,
	  /** Targeting settings related to ad serving of the advertiser. */
		servingConfig: Option[Schema.AdvertiserTargetingConfig] = None,
	  /** Whether integration with Mediaocean (Prisma) is enabled. By enabling this, you agree to the following: On behalf of my company, I authorize Mediaocean (Prisma) to send budget segment plans to Google, and I authorize Google to send corresponding reporting and invoices from DV360 to Mediaocean for the purposes of budget planning, billing, and reconciliation for this advertiser. */
		prismaEnabled: Option[Boolean] = None,
	  /** Required. Billing related settings of the advertiser. */
		billingConfig: Option[Schema.AdvertiserBillingConfig] = None
	)
	
	case class AdvertiserGeneralConfig(
	  /** Required. The domain URL of the advertiser's primary website. The system will send this information to publishers that require website URL to associate a campaign with an advertiser. Provide a URL with no path or query string, beginning with `http:` or `https:`. For example, http://www.example.com */
		domainUrl: Option[String] = None,
	  /** Output only. The standard TZ database name of the advertiser's time zone. For example, `America/New_York`. See more at: https://en.wikipedia.org/wiki/List_of_tz_database_time_zones For CM360 hybrid advertisers, the time zone is the same as that of the associated CM360 account; for third-party only advertisers, the time zone is the same as that of the parent partner. */
		timeZone: Option[String] = None,
	  /** Required. Immutable. Advertiser's currency in ISO 4217 format. Accepted codes and the currencies they represent are: Currency Code : Currency Name &#42; `ARS` : Argentine Peso &#42; `AUD` : Australian Dollar &#42; `BRL` : Brazilian Real &#42; `CAD` : Canadian Dollar &#42; `CHF` : Swiss Franc &#42; `CLP` : Chilean Peso &#42; `CNY` : Chinese Yuan &#42; `COP` : Colombian Peso &#42; `CZK` : Czech Koruna &#42; `DKK` : Danish Krone &#42; `EGP` : Egyption Pound &#42; `EUR` : Euro &#42; `GBP` : British Pound &#42; `HKD` : Hong Kong Dollar &#42; `HUF` : Hungarian Forint &#42; `IDR` : Indonesian Rupiah &#42; `ILS` : Israeli Shekel &#42; `INR` : Indian Rupee &#42; `JPY` : Japanese Yen &#42; `KRW` : South Korean Won &#42; `MXN` : Mexican Pesos &#42; `MYR` : Malaysian Ringgit &#42; `NGN` : Nigerian Naira &#42; `NOK` : Norwegian Krone &#42; `NZD` : New Zealand Dollar &#42; `PEN` : Peruvian Nuevo Sol &#42; `PLN` : Polish Zloty &#42; `RON` : New Romanian Leu &#42; `RUB` : Russian Ruble &#42; `SEK` : Swedish Krona &#42; `TRY` : Turkish Lira &#42; `TWD` : New Taiwan Dollar &#42; `USD` : US Dollar &#42; `ZAR` : South African Rand */
		currencyCode: Option[String] = None
	)
	
	case class AdvertiserAdServerConfig(
	  /** The configuration for advertisers that use third-party ad servers only. */
		thirdPartyOnlyConfig: Option[Schema.ThirdPartyOnlyConfig] = None,
	  /** The configuration for advertisers that use both Campaign Manager 360 (CM360) and third-party ad servers. */
		cmHybridConfig: Option[Schema.CmHybridConfig] = None
	)
	
	case class ThirdPartyOnlyConfig(
	  /** Whether or not order ID reporting for pixels is enabled. This value cannot be changed once set to `true`. */
		pixelOrderIdReportingEnabled: Option[Boolean] = None
	)
	
	case class CmHybridConfig(
	  /** Required. Immutable. Account ID of the CM360 Floodlight configuration linked with the DV360 advertiser. */
		cmAccountId: Option[String] = None,
	  /** Required. Immutable. ID of the CM360 Floodlight configuration linked with the DV360 advertiser. */
		cmFloodlightConfigId: Option[String] = None,
	  /** Output only. The set of CM360 Advertiser IDs sharing the CM360 Floodlight configuration. */
		cmAdvertiserIds: Option[List[String]] = None,
	  /** A list of CM360 sites whose placements will be synced to DV360 as creatives. If absent or empty in CreateAdvertiser method, the system will automatically create a CM360 site. Removing sites from this list may cause DV360 creatives synced from CM360 to be deleted. At least one site must be specified. */
		cmSyncableSiteIds: Option[List[String]] = None,
	  /** Whether or not to include DV360 data in CM360 data transfer reports. */
		dv360ToCmDataSharingEnabled: Option[Boolean] = None,
	  /** Whether or not to report DV360 cost to CM360. */
		dv360ToCmCostReportingEnabled: Option[Boolean] = None,
	  /** Required. Immutable. By setting this field to `true`, you, on behalf of your company, authorize the sharing of information from the given Floodlight configuration to this Display & Video 360 advertiser. */
		cmFloodlightLinkingAuthorized: Option[Boolean] = None
	)
	
	case class AdvertiserCreativeConfig(
	  /** An ID for configuring campaign monitoring provided by Integral Ad Service (IAS). The DV360 system will append an IAS "Campaign Monitor" tag containing this ID to the creative tag. */
		iasClientId: Option[String] = None,
	  /** Whether or not to disable Google's About this Ad feature that adds badging (to identify the content as an ad) and transparency information (on interaction with About this Ad) to your ads for Online Behavioral Advertising (OBA) and regulatory requirements. About this Ad gives users greater control over the ads they see and helps you explain why they're seeing your ad. [Learn more](//support.google.com/displayvideo/answer/14315795). If you choose to set this field to `true`, note that ads served through Display & Video 360 must comply to the following: &#42; Be Online Behavioral Advertising (OBA) compliant, as per your contract with Google Marketing Platform. &#42; In the European Economic Area (EEA), include transparency information and a mechanism for users to report illegal content in ads. If using an alternative ad badging, transparency, and reporting solution, you must ensure it includes the required transparency information and illegal content flagging mechanism and that you notify Google of any illegal content reports using the appropriate [form](//support.google.com/legal/troubleshooter/1114905?sjid=6787484030557261960-EU#ts=2981967%2C2982031%2C12980091). */
		obaComplianceDisabled: Option[Boolean] = None,
	  /** Whether or not the advertiser is enabled for dynamic creatives. */
		dynamicCreativeEnabled: Option[Boolean] = None,
	  /** By setting this field to `true`, you, on behalf of your company, authorize Google to use video creatives associated with this Display & Video 360 advertiser to provide reporting and features related to the advertiser's television campaigns. Applicable only when the advertiser has a CM360 hybrid ad server configuration. */
		videoCreativeDataSharingAuthorized: Option[Boolean] = None
	)
	
	case class AdvertiserDataAccessConfig(
	  /** Structured Data Files (SDF) settings for the advertiser. If not specified, the SDF settings of the parent partner are used. */
		sdfConfig: Option[Schema.AdvertiserSdfConfig] = None
	)
	
	case class AdvertiserSdfConfig(
	  /** Whether or not this advertiser overrides the SDF configuration of its parent partner. By default, an advertiser inherits the SDF configuration from the parent partner. To override the partner configuration, set this field to `true` and provide the new configuration in sdfConfig. */
		overridePartnerSdfConfig: Option[Boolean] = None,
	  /** The SDF configuration for the advertiser. &#42; Required when overridePartnerSdfConfig is `true`. &#42; Output only when overridePartnerSdfConfig is `false`. */
		sdfConfig: Option[Schema.SdfConfig] = None
	)
	
	object SdfConfig {
		enum VersionEnum extends Enum[VersionEnum] { case SDF_VERSION_UNSPECIFIED, SDF_VERSION_3_1, SDF_VERSION_4, SDF_VERSION_4_1, SDF_VERSION_4_2, SDF_VERSION_5, SDF_VERSION_5_1, SDF_VERSION_5_2, SDF_VERSION_5_3, SDF_VERSION_5_4, SDF_VERSION_5_5, SDF_VERSION_6, SDF_VERSION_7, SDF_VERSION_7_1, SDF_VERSION_8 }
	}
	case class SdfConfig(
	  /** Required. The version of SDF being used. */
		version: Option[Schema.SdfConfig.VersionEnum] = None,
	  /** An administrator email address to which the SDF processing status reports will be sent. */
		adminEmail: Option[String] = None
	)
	
	case class IntegrationDetails(
	  /** An external identifier to be associated with the entry. The integration code will show up together with the entry in many places in the system, for example, reporting. Must be UTF-8 encoded with a length of no more than 500 characters. */
		integrationCode: Option[String] = None,
	  /** Additional details of the entry in string format. Must be UTF-8 encoded with a length of no more than 1000 characters. */
		details: Option[String] = None
	)
	
	case class AdvertiserTargetingConfig(
	  /** Whether or not connected TV devices are exempt from viewability targeting for all video line items under the advertiser. */
		exemptTvFromViewabilityTargeting: Option[Boolean] = None
	)
	
	case class AdvertiserBillingConfig(
	  /** Required. The ID of a billing profile assigned to the advertiser. */
		billingProfileId: Option[String] = None
	)
	
	case class ListAdvertisersResponse(
	  /** The list of advertisers. This list will be absent if empty. */
		advertisers: Option[List[Schema.Advertiser]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListAdvertisers` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class AuditAdvertiserResponse(
	  /** The number of ACTIVE, PAUSED, and DRAFT line items under this advertiser. These line items count towards the limit of 9999 line items per advertiser. */
		usedLineItemsCount: Option[String] = None,
	  /** The number of ACTIVE, PAUSED and DRAFT insertion orders under this advertiser. These insertion orders count towards the limit of 9999 insertion orders per advertiser. */
		usedInsertionOrdersCount: Option[String] = None,
	  /** The number of ACTIVE and PAUSED campaigns under this advertiser. These campaigns count towards the limit of 9999 campaigns per advertiser. */
		usedCampaignsCount: Option[String] = None,
	  /** The number of channels created under this advertiser. These channels count towards the limit of 1000 channels per advertiser. */
		channelsCount: Option[String] = None,
	  /** The number of negatively targeted channels created under this advertiser. These negatively targeted channels count towards the limit of 5 negatively targeted channels per advertiser. */
		negativelyTargetedChannelsCount: Option[String] = None,
	  /** The number of negative keyword lists created under this advertiser. These negative keyword lists count towards the limit of 20 negative keyword lists per advertiser. */
		negativeKeywordListsCount: Option[String] = None,
	  /** The number of individual targeting options from the following targeting types that are assigned to a line item under this advertiser. These individual targeting options count towards the limit of 4500000 ad group targeting options per advertiser. Qualifying Targeting types: &#42; Channels, URLs, apps, and collections &#42; Demographic &#42; Google Audiences, including Affinity, Custom Affinity, and In-market audiences &#42; Inventory source &#42; Keyword &#42; Mobile app category &#42; User lists &#42; Video targeting &#42; Viewability */
		adGroupCriteriaCount: Option[String] = None,
	  /** The number of individual targeting options from the following targeting types that are assigned to a line item under this advertiser. These individual targeting options count towards the limit of 900000 campaign targeting options per advertiser. Qualifying Targeting types: &#42; Position &#42; Browser &#42; Connection speed &#42; Day and time &#42; Device and operating system &#42; Digital content label &#42; Sensitive categories &#42; Environment &#42; Geography, including business chains and proximity &#42; ISP &#42; Language &#42; Third-party verification */
		campaignCriteriaCount: Option[String] = None
	)
	
	case class CreateAssetRequest(
	  /** Required. The filename of the asset, including the file extension. The filename must be UTF-8 encoded with a maximum size of 240 bytes. */
		filename: Option[String] = None
	)
	
	case class CreateAssetResponse(
	  /** The uploaded asset, if successful. */
		asset: Option[Schema.Asset] = None
	)
	
	case class Asset(
	  /** Media ID of the uploaded asset. This is a unique identifier for the asset. This ID can be passed to other API calls, e.g. CreateCreative to associate the asset with a creative. The Media ID space updated on &#42;&#42;April 5, 2023&#42;&#42;. Update media IDs cached before &#42;&#42;April 5, 2023&#42;&#42; by retrieving the new media ID from associated creative resources or re-uploading the asset. */
		mediaId: Option[String] = None,
	  /** The asset content. For uploaded assets, the content is the serving path. */
		content: Option[String] = None
	)
	
	case class ListAssignedInventorySourcesResponse(
	  /** The list of assigned inventory sources. This list will be absent if empty. */
		assignedInventorySources: Option[List[Schema.AssignedInventorySource]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListAssignedInventorySources` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class AssignedInventorySource(
	  /** Output only. The resource name of the assigned inventory source. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the assigned inventory source. The ID is only unique within a given inventory source group. It may be reused in other contexts. */
		assignedInventorySourceId: Option[String] = None,
	  /** Required. The ID of the inventory source entity being targeted. */
		inventorySourceId: Option[String] = None
	)
	
	case class BulkEditAssignedInventorySourcesRequest(
	  /** The ID of the partner that owns the inventory source group. Only this partner has write access to these assigned inventory sources. */
		partnerId: Option[String] = None,
	  /** The ID of the advertiser that owns the parent inventory source group. The parent partner does not have access to these assigned inventory sources. */
		advertiserId: Option[String] = None,
	  /** The IDs of the assigned inventory sources to delete in bulk, specified as a list of assigned_inventory_source_ids. */
		deletedAssignedInventorySources: Option[List[String]] = None,
	  /** The assigned inventory sources to create in bulk, specified as a list of AssignedInventorySources. */
		createdAssignedInventorySources: Option[List[Schema.AssignedInventorySource]] = None
	)
	
	case class BulkEditAssignedInventorySourcesResponse(
	  /** The list of assigned inventory sources that have been successfully created. This list will be absent if empty. */
		assignedInventorySources: Option[List[Schema.AssignedInventorySource]] = None
	)
	
	case class ListAssignedLocationsResponse(
	  /** The list of assigned locations. This list will be absent if empty. */
		assignedLocations: Option[List[Schema.AssignedLocation]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListAssignedLocations` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class AssignedLocation(
	  /** Output only. The resource name of the assigned location. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the assigned location. The ID is only unique within a location list. It may be reused in other contexts. */
		assignedLocationId: Option[String] = None,
	  /** Required. The ID of the targeting option assigned to the location list. */
		targetingOptionId: Option[String] = None
	)
	
	case class BulkEditAssignedLocationsRequest(
	  /** The IDs of the assigned locations to delete in bulk, specified as a list of assignedLocationId values. */
		deletedAssignedLocations: Option[List[String]] = None,
	  /** The assigned locations to create in bulk, specified as a list of AssignedLocation resources. */
		createdAssignedLocations: Option[List[Schema.AssignedLocation]] = None
	)
	
	case class BulkEditAssignedLocationsResponse(
	  /** The list of assigned locations that have been successfully created. This list will be absent if empty. */
		assignedLocations: Option[List[Schema.AssignedLocation]] = None
	)
	
	case class ListCampaignAssignedTargetingOptionsResponse(
	  /** The list of assigned targeting options. This list will be absent if empty. */
		assignedTargetingOptions: Option[List[Schema.AssignedTargetingOption]] = None,
	  /** A token identifying the next page of results. This value should be specified as the pageToken in a subsequent ListCampaignAssignedTargetingOptionsRequest to fetch the next page of results. This token will be absent if there are no more assigned_targeting_options to return. */
		nextPageToken: Option[String] = None
	)
	
	case class BulkListCampaignAssignedTargetingOptionsResponse(
	  /** The list of assigned targeting options. This list will be absent if empty. */
		assignedTargetingOptions: Option[List[Schema.AssignedTargetingOption]] = None,
	  /** A token identifying the next page of results. This value should be specified as the pageToken in a subsequent BulkListCampaignAssignedTargetingOptionsRequest to fetch the next page of results. This token will be absent if there are no more assigned_targeting_options to return. */
		nextPageToken: Option[String] = None
	)
	
	object Campaign {
		enum EntityStatusEnum extends Enum[EntityStatusEnum] { case ENTITY_STATUS_UNSPECIFIED, ENTITY_STATUS_ACTIVE, ENTITY_STATUS_ARCHIVED, ENTITY_STATUS_DRAFT, ENTITY_STATUS_PAUSED, ENTITY_STATUS_SCHEDULED_FOR_DELETION }
	}
	case class Campaign(
	  /** Output only. The resource name of the campaign. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the advertiser the campaign belongs to. */
		advertiserId: Option[String] = None,
	  /** Output only. The unique ID of the campaign. Assigned by the system. */
		campaignId: Option[String] = None,
	  /** Required. The display name of the campaign. Must be UTF-8 encoded with a maximum size of 240 bytes. */
		displayName: Option[String] = None,
	  /** Required. Controls whether or not the insertion orders under this campaign can spend their budgets and bid on inventory. &#42; Accepted values are `ENTITY_STATUS_ACTIVE`, `ENTITY_STATUS_ARCHIVED`, and `ENTITY_STATUS_PAUSED`. &#42; For CreateCampaign method, `ENTITY_STATUS_ARCHIVED` is not allowed. */
		entityStatus: Option[Schema.Campaign.EntityStatusEnum] = None,
	  /** Output only. The timestamp when the campaign was last updated. Assigned by the system. */
		updateTime: Option[String] = None,
	  /** Required. The goal of the campaign. */
		campaignGoal: Option[Schema.CampaignGoal] = None,
	  /** Required. The planned spend and duration of the campaign. */
		campaignFlight: Option[Schema.CampaignFlight] = None,
	  /** Required. The frequency cap setting of the campaign. */
		frequencyCap: Option[Schema.FrequencyCap] = None,
	  /** The list of budgets available to this campaign. If this field is not set, the campaign uses an unlimited budget. */
		campaignBudgets: Option[List[Schema.CampaignBudget]] = None
	)
	
	object CampaignGoal {
		enum CampaignGoalTypeEnum extends Enum[CampaignGoalTypeEnum] { case CAMPAIGN_GOAL_TYPE_UNSPECIFIED, CAMPAIGN_GOAL_TYPE_APP_INSTALL, CAMPAIGN_GOAL_TYPE_BRAND_AWARENESS, CAMPAIGN_GOAL_TYPE_OFFLINE_ACTION, CAMPAIGN_GOAL_TYPE_ONLINE_ACTION }
	}
	case class CampaignGoal(
	  /** Required. The type of the campaign goal. */
		campaignGoalType: Option[Schema.CampaignGoal.CampaignGoalTypeEnum] = None,
	  /** Required. The performance goal of the campaign. Acceptable values for performance_goal_type are: &#42; `PERFORMANCE_GOAL_TYPE_CPM` &#42; `PERFORMANCE_GOAL_TYPE_CPC` &#42; `PERFORMANCE_GOAL_TYPE_CPA` &#42; `PERFORMANCE_GOAL_TYPE_CPIAVC` &#42; `PERFORMANCE_GOAL_TYPE_CTR` &#42; `PERFORMANCE_GOAL_TYPE_VIEWABILITY` &#42; `PERFORMANCE_GOAL_TYPE_OTHER` */
		performanceGoal: Option[Schema.PerformanceGoal] = None
	)
	
	object PerformanceGoal {
		enum PerformanceGoalTypeEnum extends Enum[PerformanceGoalTypeEnum] { case PERFORMANCE_GOAL_TYPE_UNSPECIFIED, PERFORMANCE_GOAL_TYPE_CPM, PERFORMANCE_GOAL_TYPE_CPC, PERFORMANCE_GOAL_TYPE_CPA, PERFORMANCE_GOAL_TYPE_CTR, PERFORMANCE_GOAL_TYPE_VIEWABILITY, PERFORMANCE_GOAL_TYPE_CPIAVC, PERFORMANCE_GOAL_TYPE_CPE, PERFORMANCE_GOAL_TYPE_CPV, PERFORMANCE_GOAL_TYPE_CLICK_CVR, PERFORMANCE_GOAL_TYPE_IMPRESSION_CVR, PERFORMANCE_GOAL_TYPE_VCPM, PERFORMANCE_GOAL_TYPE_VTR, PERFORMANCE_GOAL_TYPE_AUDIO_COMPLETION_RATE, PERFORMANCE_GOAL_TYPE_VIDEO_COMPLETION_RATE, PERFORMANCE_GOAL_TYPE_OTHER }
	}
	case class PerformanceGoal(
	  /** Required. The type of the performance goal. */
		performanceGoalType: Option[Schema.PerformanceGoal.PerformanceGoalTypeEnum] = None,
	  /** The goal amount, in micros of the advertiser's currency. Applicable when performance_goal_type is one of: &#42; `PERFORMANCE_GOAL_TYPE_CPM` &#42; `PERFORMANCE_GOAL_TYPE_CPC` &#42; `PERFORMANCE_GOAL_TYPE_CPA` &#42; `PERFORMANCE_GOAL_TYPE_CPIAVC` &#42; `PERFORMANCE_GOAL_TYPE_VCPM` For example 1500000 represents 1.5 standard units of the currency. */
		performanceGoalAmountMicros: Option[String] = None,
	  /** The decimal representation of the goal percentage in micros. Applicable when performance_goal_type is one of: &#42; `PERFORMANCE_GOAL_TYPE_CTR` &#42; `PERFORMANCE_GOAL_TYPE_VIEWABILITY` &#42; `PERFORMANCE_GOAL_TYPE_CLICK_CVR` &#42; `PERFORMANCE_GOAL_TYPE_IMPRESSION_CVR` &#42; `PERFORMANCE_GOAL_TYPE_VTR` &#42; `PERFORMANCE_GOAL_TYPE_AUDIO_COMPLETION_RATE` &#42; `PERFORMANCE_GOAL_TYPE_VIDEO_COMPLETION_RATE` For example, 70000 represents 7% (decimal 0.07). */
		performanceGoalPercentageMicros: Option[String] = None,
	  /** A key performance indicator (KPI) string, which can be empty. Must be UTF-8 encoded with a length of no more than 100 characters. Applicable when performance_goal_type is set to `PERFORMANCE_GOAL_TYPE_OTHER`. */
		performanceGoalString: Option[String] = None
	)
	
	case class CampaignFlight(
	  /** The amount the campaign is expected to spend for its given planned_dates. This will not limit serving, but will be used for tracking spend in the DV360 UI. The amount is in micros. Must be greater than or equal to 0. For example, 500000000 represents 500 standard units of the currency. */
		plannedSpendAmountMicros: Option[String] = None,
	  /** Required. The dates that the campaign is expected to run. They are resolved relative to the parent advertiser's time zone. &#42; The dates specified here will not affect serving. They are used to generate alerts and warnings. For example, if the flight date of any child insertion order is outside the range of these dates, the user interface will show a warning. &#42; `start_date` is required and must be the current date or later. &#42; `end_date` is optional. If specified, it must be the `start_date` or later. &#42; Any specified date must be before the year 2037. */
		plannedDates: Option[Schema.DateRange] = None
	)
	
	case class DateRange(
	  /** The lower bound of the date range, inclusive. Must specify a positive value for `year`, `month`, and `day`. */
		startDate: Option[Schema.Date] = None,
	  /** The upper bound of the date range, inclusive. Must specify a positive value for `year`, `month`, and `day`. */
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
	
	object FrequencyCap {
		enum TimeUnitEnum extends Enum[TimeUnitEnum] { case TIME_UNIT_UNSPECIFIED, TIME_UNIT_LIFETIME, TIME_UNIT_MONTHS, TIME_UNIT_WEEKS, TIME_UNIT_DAYS, TIME_UNIT_HOURS, TIME_UNIT_MINUTES }
	}
	case class FrequencyCap(
	  /** Whether unlimited frequency capping is applied. When this field is set to `true`, the remaining frequency cap fields are not applicable. */
		unlimited: Option[Boolean] = None,
	  /** The time unit in which the frequency cap will be applied. Required when unlimited is `false`. */
		timeUnit: Option[Schema.FrequencyCap.TimeUnitEnum] = None,
	  /** The number of time_unit the frequency cap will last. Required when unlimited is `false`. The following restrictions apply based on the value of time_unit: &#42; `TIME_UNIT_LIFETIME` - this field is output only and will default to 1 &#42; `TIME_UNIT_MONTHS` - must be between 1 and 2 &#42; `TIME_UNIT_WEEKS` - must be between 1 and 4 &#42; `TIME_UNIT_DAYS` - must be between 1 and 6 &#42; `TIME_UNIT_HOURS` - must be between 1 and 23 &#42; `TIME_UNIT_MINUTES` - must be between 1 and 59 */
		timeUnitCount: Option[Int] = None,
	  /** The maximum number of times a user may be shown the same ad during this period. Must be greater than 0. Required when unlimited is `false` and max_views is not set. */
		maxImpressions: Option[Int] = None,
	  /** Optional. The maximum number of times a user may click-through or fully view an ad during this period until it is no longer served to them. Must be greater than 0. Only applicable to YouTube and Partners resources. Required when unlimited is `false` and max_impressions is not set. */
		maxViews: Option[Int] = None
	)
	
	object CampaignBudget {
		enum BudgetUnitEnum extends Enum[BudgetUnitEnum] { case BUDGET_UNIT_UNSPECIFIED, BUDGET_UNIT_CURRENCY, BUDGET_UNIT_IMPRESSIONS }
		enum ExternalBudgetSourceEnum extends Enum[ExternalBudgetSourceEnum] { case EXTERNAL_BUDGET_SOURCE_UNSPECIFIED, EXTERNAL_BUDGET_SOURCE_NONE, EXTERNAL_BUDGET_SOURCE_MEDIA_OCEAN }
	}
	case class CampaignBudget(
	  /** The unique ID of the campaign budget. Assigned by the system. Do not set for new budgets. Must be included when updating or adding budgets to campaign_budgets. Otherwise, a new ID will be generated and assigned. */
		budgetId: Option[String] = None,
	  /** Required. The display name of the budget. Must be UTF-8 encoded with a maximum size of 240 bytes. */
		displayName: Option[String] = None,
	  /** Required. Immutable. Specifies whether the budget is measured in currency or impressions. */
		budgetUnit: Option[Schema.CampaignBudget.BudgetUnitEnum] = None,
	  /** Required. The total amount the linked insertion order segments can budget. The amount is in micros. Must be greater than 0. For example, 500000000 represents 500 standard units of the currency. */
		budgetAmountMicros: Option[String] = None,
	  /** Required. The date range for the campaign budget. Linked budget segments may have a different date range. They are resolved relative to the parent advertiser's time zone. Both `start_date` and `end_date` must be before the year 2037. */
		dateRange: Option[Schema.DateRange] = None,
	  /** Required. The external source of the budget. */
		externalBudgetSource: Option[Schema.CampaignBudget.ExternalBudgetSourceEnum] = None,
	  /** Immutable. The ID identifying this budget to the external source. If this field is set and the invoice detail level of the corresponding billing profile is set to "Budget level PO", all impressions served against this budget will include this ID on the invoice. Must be unique under the campaign. */
		externalBudgetId: Option[String] = None,
	  /** Immutable. The ID used to group budgets to be included the same invoice. If this field is set and the invoice level of the corresponding billing profile is set to "Budget invoice grouping ID", all external_budget_id sharing the same invoice_grouping_id will be grouped in the same invoice. */
		invoiceGroupingId: Option[String] = None,
	  /** Additional metadata for use by the Mediaocean Prisma tool. Required for Mediaocean budgets. Only applicable to prisma_enabled advertisers. */
		prismaConfig: Option[Schema.PrismaConfig] = None
	)
	
	object PrismaConfig {
		enum PrismaTypeEnum extends Enum[PrismaTypeEnum] { case PRISMA_TYPE_UNSPECIFIED, PRISMA_TYPE_DISPLAY, PRISMA_TYPE_SEARCH, PRISMA_TYPE_VIDEO, PRISMA_TYPE_AUDIO, PRISMA_TYPE_SOCIAL, PRISMA_TYPE_FEE }
	}
	case class PrismaConfig(
	  /** Required. The Prisma type. */
		prismaType: Option[Schema.PrismaConfig.PrismaTypeEnum] = None,
	  /** Required. Relevant client, product, and estimate codes from the Mediaocean Prisma tool. */
		prismaCpeCode: Option[Schema.PrismaCpeCode] = None,
	  /** Required. The entity allocated this budget (DSP, site, etc.). */
		supplier: Option[String] = None
	)
	
	case class PrismaCpeCode(
	  /** The Prisma client code. */
		prismaClientCode: Option[String] = None,
	  /** The Prisma product code. */
		prismaProductCode: Option[String] = None,
	  /** The Prisma estimate code. */
		prismaEstimateCode: Option[String] = None
	)
	
	case class ListCampaignsResponse(
	  /** The list of campaigns. This list will be absent if empty. */
		campaigns: Option[List[Schema.Campaign]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListCampaigns` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class Channel(
	  /** Output only. The resource name of the channel. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the channel. Assigned by the system. */
		channelId: Option[String] = None,
	  /** Required. The display name of the channel. Must be UTF-8 encoded with a maximum length of 240 bytes. */
		displayName: Option[String] = None,
	  /** The ID of the partner that owns the channel. */
		partnerId: Option[String] = None,
	  /** The ID of the advertiser that owns the channel. */
		advertiserId: Option[String] = None,
	  /** Output only. Number of line items that are directly targeting this channel positively. */
		positivelyTargetedLineItemCount: Option[String] = None,
	  /** Output only. Number of line items that are directly targeting this channel negatively. */
		negativelyTargetedLineItemCount: Option[String] = None
	)
	
	case class ListChannelsResponse(
	  /** The list of channels. This list will be absent if empty. */
		channels: Option[List[Schema.Channel]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListChannels` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class CombinedAudience(
	  /** Output only. The resource name of the combined audience. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the combined audience. Assigned by the system. */
		combinedAudienceId: Option[String] = None,
	  /** Output only. The display name of the combined audience. . */
		displayName: Option[String] = None
	)
	
	case class ListCombinedAudiencesResponse(
	  /** The list of combined audiences. This list will be absent if empty. */
		combinedAudiences: Option[List[Schema.CombinedAudience]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListCombinedAudiences` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object Creative {
		enum EntityStatusEnum extends Enum[EntityStatusEnum] { case ENTITY_STATUS_UNSPECIFIED, ENTITY_STATUS_ACTIVE, ENTITY_STATUS_ARCHIVED, ENTITY_STATUS_DRAFT, ENTITY_STATUS_PAUSED, ENTITY_STATUS_SCHEDULED_FOR_DELETION }
		enum CreativeTypeEnum extends Enum[CreativeTypeEnum] { case CREATIVE_TYPE_UNSPECIFIED, CREATIVE_TYPE_STANDARD, CREATIVE_TYPE_EXPANDABLE, CREATIVE_TYPE_VIDEO, CREATIVE_TYPE_NATIVE, CREATIVE_TYPE_TEMPLATED_APP_INSTALL, CREATIVE_TYPE_NATIVE_SITE_SQUARE, CREATIVE_TYPE_TEMPLATED_APP_INSTALL_INTERSTITIAL, CREATIVE_TYPE_LIGHTBOX, CREATIVE_TYPE_NATIVE_APP_INSTALL, CREATIVE_TYPE_NATIVE_APP_INSTALL_SQUARE, CREATIVE_TYPE_AUDIO, CREATIVE_TYPE_PUBLISHER_HOSTED, CREATIVE_TYPE_NATIVE_VIDEO, CREATIVE_TYPE_TEMPLATED_APP_INSTALL_VIDEO }
		enum HostingSourceEnum extends Enum[HostingSourceEnum] { case HOSTING_SOURCE_UNSPECIFIED, HOSTING_SOURCE_CM, HOSTING_SOURCE_THIRD_PARTY, HOSTING_SOURCE_HOSTED, HOSTING_SOURCE_RICH_MEDIA }
		enum CreativeAttributesEnum extends Enum[CreativeAttributesEnum] { case CREATIVE_ATTRIBUTE_UNSPECIFIED, CREATIVE_ATTRIBUTE_VAST, CREATIVE_ATTRIBUTE_VPAID_LINEAR, CREATIVE_ATTRIBUTE_VPAID_NON_LINEAR }
		enum ExpandingDirectionEnum extends Enum[ExpandingDirectionEnum] { case EXPANDING_DIRECTION_UNSPECIFIED, EXPANDING_DIRECTION_NONE, EXPANDING_DIRECTION_UP, EXPANDING_DIRECTION_DOWN, EXPANDING_DIRECTION_LEFT, EXPANDING_DIRECTION_RIGHT, EXPANDING_DIRECTION_UP_AND_LEFT, EXPANDING_DIRECTION_UP_AND_RIGHT, EXPANDING_DIRECTION_DOWN_AND_LEFT, EXPANDING_DIRECTION_DOWN_AND_RIGHT, EXPANDING_DIRECTION_UP_OR_DOWN, EXPANDING_DIRECTION_LEFT_OR_RIGHT, EXPANDING_DIRECTION_ANY_DIAGONAL }
	}
	case class Creative(
	  /** Output only. The resource name of the creative. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the advertiser the creative belongs to. */
		advertiserId: Option[String] = None,
	  /** Output only. The unique ID of the creative. Assigned by the system. */
		creativeId: Option[String] = None,
	  /** Output only. The unique ID of the Campaign Manager 360 placement associated with the creative. This field is only applicable for creatives that are synced from Campaign Manager. */
		cmPlacementId: Option[String] = None,
	  /** Required. The display name of the creative. Must be UTF-8 encoded with a maximum size of 240 bytes. */
		displayName: Option[String] = None,
	  /** Required. Controls whether or not the creative can serve. Accepted values are: &#42; `ENTITY_STATUS_ACTIVE` &#42; `ENTITY_STATUS_ARCHIVED` &#42; `ENTITY_STATUS_PAUSED` */
		entityStatus: Option[Schema.Creative.EntityStatusEnum] = None,
	  /** Output only. The timestamp when the creative was last updated, either by the user or system (e.g. creative review). Assigned by the system. */
		updateTime: Option[String] = None,
	  /** Output only. The timestamp when the creative was created. Assigned by the system. */
		createTime: Option[String] = None,
	  /** Required. Immutable. The type of the creative. */
		creativeType: Option[Schema.Creative.CreativeTypeEnum] = None,
	  /** Required. Indicates where the creative is hosted. */
		hostingSource: Option[Schema.Creative.HostingSourceEnum] = None,
	  /** Output only. Indicates whether the creative is dynamic. */
		dynamic: Option[Boolean] = None,
	  /** Required. Primary dimensions of the creative. Applicable to all creative types. The value of width_pixels and height_pixels defaults to `0` when creative_type is one of: &#42; `CREATIVE_TYPE_VIDEO` &#42; `CREATIVE_TYPE_AUDIO` &#42; `CREATIVE_TYPE_NATIVE_VIDEO` */
		dimensions: Option[Schema.Dimensions] = None,
	  /** Additional dimensions. Applicable when creative_type is one of: &#42; `CREATIVE_TYPE_STANDARD` &#42; `CREATIVE_TYPE_EXPANDABLE` &#42; `CREATIVE_TYPE_NATIVE` &#42; `CREATIVE_TYPE_NATIVE_SITE_SQUARE` &#42; `CREATIVE_TYPE_LIGHTBOX` &#42; `CREATIVE_TYPE_PUBLISHER_HOSTED` If this field is specified, width_pixels and height_pixels are both required and must be greater than or equal to 0. */
		additionalDimensions: Option[List[Schema.Dimensions]] = None,
	  /** Output only. Media duration of the creative. Applicable when creative_type is one of: &#42; `CREATIVE_TYPE_VIDEO` &#42; `CREATIVE_TYPE_AUDIO` &#42; `CREATIVE_TYPE_NATIVE_VIDEO` &#42; `CREATIVE_TYPE_PUBLISHER_HOSTED` */
		mediaDuration: Option[String] = None,
	  /** Output only. A list of attributes of the creative that is generated by the system. */
		creativeAttributes: Option[List[Schema.Creative.CreativeAttributesEnum]] = None,
	  /** Output only. The current status of the creative review process. */
		reviewStatus: Option[Schema.ReviewStatusInfo] = None,
	  /** Required. Assets associated to this creative. */
		assets: Option[List[Schema.AssetAssociation]] = None,
	  /** Required. Exit events for this creative. An exit (also known as a click tag) is any area in your creative that someone can click or tap to open an advertiser's landing page. Every creative must include at least one exit. You can add an exit to your creative in any of the following ways: &#42; Use Google Web Designer's tap area. &#42; Define a JavaScript variable called "clickTag". &#42; Use the Enabler (Enabler.exit()) to track exits in rich media formats. */
		exitEvents: Option[List[Schema.ExitEvent]] = None,
	  /** Timer custom events for a rich media creative. Timers track the time during which a user views and interacts with a specified part of a rich media creative. A creative can have multiple timer events, each timed independently. Leave it empty or unset for creatives containing image assets only. */
		timerEvents: Option[List[Schema.TimerEvent]] = None,
	  /** Counter events for a rich media creative. Counters track the number of times that a user interacts with any part of a rich media creative in a specified way (mouse-overs, mouse-outs, clicks, taps, data loading, keyboard entries, etc.). Any event that can be captured in the creative can be recorded as a counter. Leave it empty or unset for creatives containing image assets only. */
		counterEvents: Option[List[Schema.CounterEvent]] = None,
	  /** Third-party HTML tracking tag to be appended to the creative tag. */
		appendedTag: Option[String] = None,
	  /** ID information used to link this creative to an external system. Must be UTF-8 encoded with a length of no more than 10,000 characters. */
		integrationCode: Option[String] = None,
	  /** User notes for this creative. Must be UTF-8 encoded with a length of no more than 20,000 characters. */
		notes: Option[String] = None,
	  /** Indicates whether Integral Ad Science (IAS) campaign monitoring is enabled. To enable this for the creative, make sure the Advertiser.creative_config.ias_client_id has been set to your IAS client ID. */
		iasCampaignMonitoring: Option[Boolean] = None,
	  /** The IDs of companion creatives for a video creative. You can assign existing display creatives (with image or HTML5 assets) to serve surrounding the publisher's video player. Companions display around the video player while the video is playing and remain after the video has completed. Creatives contain additional dimensions can not be companion creatives. This field is only supported for following creative_type: &#42; `CREATIVE_TYPE_AUDIO` &#42; `CREATIVE_TYPE_VIDEO` */
		companionCreativeIds: Option[List[String]] = None,
	  /** Whether the user can choose to skip a video creative. This field is only supported for the following creative_type: &#42; `CREATIVE_TYPE_VIDEO` */
		skippable: Option[Boolean] = None,
	  /** Amount of time to play the video before the skip button appears. This field is required when skippable is true. This field is only supported for the following creative_type: &#42; `CREATIVE_TYPE_VIDEO` */
		skipOffset: Option[Schema.AudioVideoOffset] = None,
	  /** Amount of time to play the video before counting a view. This field is required when skippable is true. This field is only supported for the following creative_type: &#42; `CREATIVE_TYPE_VIDEO` */
		progressOffset: Option[Schema.AudioVideoOffset] = None,
	  /** Optional. An optional creative identifier provided by a registry that is unique across all platforms. Universal Ad ID is part of the VAST 4.0 standard. It can be modified after the creative is created. This field is only supported for the following creative_type: &#42; `CREATIVE_TYPE_VIDEO` */
		universalAdId: Option[Schema.UniversalAdId] = None,
	  /** Tracking URLs from third parties to track interactions with a video creative. This field is only supported for the following creative_type: &#42; `CREATIVE_TYPE_AUDIO` &#42; `CREATIVE_TYPE_VIDEO` &#42; `CREATIVE_TYPE_NATIVE_VIDEO` */
		thirdPartyUrls: Option[List[Schema.ThirdPartyUrl]] = None,
	  /** Output only. Audio/Video transcodes. Display & Video 360 transcodes the main asset into a number of alternative versions that use different file formats or have different properties (resolution, audio bit rate, and video bit rate), each designed for specific video players or bandwidths. These transcodes give a publisher's system more options to choose from for each impression on your video and ensures that the appropriate file serves based on the viewer’s connection and screen size. This field is only supported in following creative_type: &#42; `CREATIVE_TYPE_VIDEO` &#42; `CREATIVE_TYPE_NATIVE_VIDEO` &#42; `CREATIVE_TYPE_AUDIO` */
		transcodes: Option[List[Schema.Transcode]] = None,
	  /** Tracking URLs for analytics providers or third-party ad technology vendors. The URLs must start with https (except on inventory that doesn't require SSL compliance). If using macros in your URL, use only macros supported by Display & Video 360. Standard URLs only, no IMG or SCRIPT tags. This field is only writeable in following creative_type: &#42; `CREATIVE_TYPE_NATIVE` &#42; `CREATIVE_TYPE_NATIVE_SITE_SQUARE` &#42; `CREATIVE_TYPE_NATIVE_VIDEO` */
		trackerUrls: Option[List[String]] = None,
	  /** JavaScript measurement URL from supported third-party verification providers (ComScore, DoubleVerify, IAS, Moat). HTML script tags are not supported. This field is only writeable in following creative_type: &#42; `CREATIVE_TYPE_NATIVE` &#42; `CREATIVE_TYPE_NATIVE_SITE_SQUARE` &#42; `CREATIVE_TYPE_NATIVE_VIDEO` */
		jsTrackerUrl: Option[String] = None,
	  /** The Campaign Manager 360 tracking ad associated with the creative. Optional for the following creative_type when created by an advertiser that uses both Campaign Manager 360 and third-party ad serving: &#42; `CREATIVE_TYPE_NATIVE` &#42; `CREATIVE_TYPE_NATIVE_SITE_SQUARE` Output only for other cases. */
		cmTrackingAd: Option[Schema.CmTrackingAd] = None,
	  /** Specifies the OBA icon for a video creative. This field is only supported in following creative_type: &#42; `CREATIVE_TYPE_VIDEO` */
		obaIcon: Option[Schema.ObaIcon] = None,
	  /** Optional. The original third-party tag used for the creative. Required and only valid for third-party tag creatives. Third-party tag creatives are creatives with following hosting_source: &#42; `HOSTING_SOURCE_THIRD_PARTY` combined with following creative_type: &#42; `CREATIVE_TYPE_STANDARD` &#42; `CREATIVE_TYPE_EXPANDABLE` */
		thirdPartyTag: Option[String] = None,
	  /** Optional. Indicates that the creative requires MRAID (Mobile Rich Media Ad Interface Definitions system). Set this if the creative relies on mobile gestures for interactivity, such as swiping or tapping. Optional and only valid for third-party tag creatives. Third-party tag creatives are creatives with following hosting_source: &#42; `HOSTING_SOURCE_THIRD_PARTY` combined with following creative_type: &#42; `CREATIVE_TYPE_STANDARD` &#42; `CREATIVE_TYPE_EXPANDABLE` */
		requireMraid: Option[Boolean] = None,
	  /** Optional. Indicates that the creative relies on HTML5 to render properly. Optional and only valid for third-party tag creatives. Third-party tag creatives are creatives with following hosting_source: &#42; `HOSTING_SOURCE_THIRD_PARTY` combined with following creative_type: &#42; `CREATIVE_TYPE_STANDARD` &#42; `CREATIVE_TYPE_EXPANDABLE` */
		requireHtml5: Option[Boolean] = None,
	  /** Optional. Indicates that the creative will wait for a return ping for attribution. Only valid when using a Campaign Manager 360 tracking ad with a third-party ad server parameter and the ${DC_DBM_TOKEN} macro. Optional and only valid for third-party tag creatives or third-party VAST tag creatives. Third-party tag creatives are creatives with following hosting_source: &#42; `HOSTING_SOURCE_THIRD_PARTY` combined with following creative_type: &#42; `CREATIVE_TYPE_STANDARD` &#42; `CREATIVE_TYPE_EXPANDABLE` Third-party VAST tag creatives are creatives with following hosting_source: &#42; `HOSTING_SOURCE_THIRD_PARTY` combined with following creative_type: &#42; `CREATIVE_TYPE_AUDIO` &#42; `CREATIVE_TYPE_VIDEO` */
		requirePingForAttribution: Option[Boolean] = None,
	  /** Optional. Specifies the expanding direction of the creative. Required and only valid for third-party expandable creatives. Third-party expandable creatives are creatives with following hosting source: &#42; `HOSTING_SOURCE_THIRD_PARTY` combined with following creative_type: &#42; `CREATIVE_TYPE_EXPANDABLE` */
		expandingDirection: Option[Schema.Creative.ExpandingDirectionEnum] = None,
	  /** Optional. Indicates the creative will automatically expand on hover. Optional and only valid for third-party expandable creatives. Third-party expandable creatives are creatives with following hosting source: &#42; `HOSTING_SOURCE_THIRD_PARTY` combined with following creative_type: &#42; `CREATIVE_TYPE_EXPANDABLE` */
		expandOnHover: Option[Boolean] = None,
	  /** Optional. The URL of the VAST tag for a third-party VAST tag creative. Required and only valid for third-party VAST tag creatives. Third-party VAST tag creatives are creatives with following hosting_source: &#42; `HOSTING_SOURCE_THIRD_PARTY` combined with following creative_type: &#42; `CREATIVE_TYPE_AUDIO` &#42; `CREATIVE_TYPE_VIDEO` */
		vastTagUrl: Option[String] = None,
	  /** Output only. Indicates the third-party VAST tag creative requires VPAID (Digital Video Player-Ad Interface). Output only and only valid for third-party VAST tag creatives. Third-party VAST tag creatives are creatives with following hosting_source: &#42; `HOSTING_SOURCE_THIRD_PARTY` combined with following creative_type: &#42; `CREATIVE_TYPE_VIDEO` */
		vpaid: Option[Boolean] = None,
	  /** Output only. Indicates the third-party VAST tag creative requires HTML5 Video support. Output only and only valid for third-party VAST tag creatives. Third-party VAST tag creatives are creatives with following hosting_source: &#42; `HOSTING_SOURCE_THIRD_PARTY` combined with following creative_type: &#42; `CREATIVE_TYPE_VIDEO` */
		html5Video: Option[Boolean] = None,
	  /** Output only. The IDs of the line items this creative is associated with. To associate a creative to a line item, use LineItem.creative_ids instead. */
		lineItemIds: Option[List[String]] = None,
	  /** Output only. Indicates the third-party audio creative supports MP3. Output only and only valid for third-party audio creatives. Third-party audio creatives are creatives with following hosting_source: &#42; `HOSTING_SOURCE_THIRD_PARTY` combined with following creative_type: &#42; `CREATIVE_TYPE_AUDIO` */
		mp3Audio: Option[Boolean] = None,
	  /** Output only. Indicates the third-party audio creative supports OGG. Output only and only valid for third-party audio creatives. Third-party audio creatives are creatives with following hosting_source: &#42; `HOSTING_SOURCE_THIRD_PARTY` combined with following creative_type: &#42; `CREATIVE_TYPE_AUDIO` */
		oggAudio: Option[Boolean] = None
	)
	
	object ReviewStatusInfo {
		enum ApprovalStatusEnum extends Enum[ApprovalStatusEnum] { case APPROVAL_STATUS_UNSPECIFIED, APPROVAL_STATUS_PENDING_NOT_SERVABLE, APPROVAL_STATUS_PENDING_SERVABLE, APPROVAL_STATUS_APPROVED_SERVABLE, APPROVAL_STATUS_REJECTED_NOT_SERVABLE }
		enum CreativeAndLandingPageReviewStatusEnum extends Enum[CreativeAndLandingPageReviewStatusEnum] { case REVIEW_STATUS_UNSPECIFIED, REVIEW_STATUS_APPROVED, REVIEW_STATUS_REJECTED, REVIEW_STATUS_PENDING }
		enum ContentAndPolicyReviewStatusEnum extends Enum[ContentAndPolicyReviewStatusEnum] { case REVIEW_STATUS_UNSPECIFIED, REVIEW_STATUS_APPROVED, REVIEW_STATUS_REJECTED, REVIEW_STATUS_PENDING }
	}
	case class ReviewStatusInfo(
	  /** Represents the basic approval needed for a creative to begin serving. Summary of creative_and_landing_page_review_status and content_and_policy_review_status. */
		approvalStatus: Option[Schema.ReviewStatusInfo.ApprovalStatusEnum] = None,
	  /** Creative and landing page review status for the creative. */
		creativeAndLandingPageReviewStatus: Option[Schema.ReviewStatusInfo.CreativeAndLandingPageReviewStatusEnum] = None,
	  /** Content and policy review status for the creative. */
		contentAndPolicyReviewStatus: Option[Schema.ReviewStatusInfo.ContentAndPolicyReviewStatusEnum] = None,
	  /** Exchange review statuses for the creative. */
		exchangeReviewStatuses: Option[List[Schema.ExchangeReviewStatus]] = None,
	  /** Publisher review statuses for the creative. */
		publisherReviewStatuses: Option[List[Schema.PublisherReviewStatus]] = None
	)
	
	object ExchangeReviewStatus {
		enum ExchangeEnum extends Enum[ExchangeEnum] { case EXCHANGE_UNSPECIFIED, EXCHANGE_GOOGLE_AD_MANAGER, EXCHANGE_APPNEXUS, EXCHANGE_BRIGHTROLL, EXCHANGE_ADFORM, EXCHANGE_ADMETA, EXCHANGE_ADMIXER, EXCHANGE_ADSMOGO, EXCHANGE_ADSWIZZ, EXCHANGE_BIDSWITCH, EXCHANGE_BRIGHTROLL_DISPLAY, EXCHANGE_CADREON, EXCHANGE_DAILYMOTION, EXCHANGE_FIVE, EXCHANGE_FLUCT, EXCHANGE_FREEWHEEL, EXCHANGE_GENIEE, EXCHANGE_GUMGUM, EXCHANGE_IMOBILE, EXCHANGE_IBILLBOARD, EXCHANGE_IMPROVE_DIGITAL, EXCHANGE_INDEX, EXCHANGE_KARGO, EXCHANGE_MICROAD, EXCHANGE_MOPUB, EXCHANGE_NEND, EXCHANGE_ONE_BY_AOL_DISPLAY, EXCHANGE_ONE_BY_AOL_MOBILE, EXCHANGE_ONE_BY_AOL_VIDEO, EXCHANGE_OOYALA, EXCHANGE_OPENX, EXCHANGE_PERMODO, EXCHANGE_PLATFORMONE, EXCHANGE_PLATFORMID, EXCHANGE_PUBMATIC, EXCHANGE_PULSEPOINT, EXCHANGE_REVENUEMAX, EXCHANGE_RUBICON, EXCHANGE_SMARTCLIP, EXCHANGE_SMARTRTB, EXCHANGE_SMARTSTREAMTV, EXCHANGE_SOVRN, EXCHANGE_SPOTXCHANGE, EXCHANGE_STROER, EXCHANGE_TEADSTV, EXCHANGE_TELARIA, EXCHANGE_TVN, EXCHANGE_UNITED, EXCHANGE_YIELDLAB, EXCHANGE_YIELDMO, EXCHANGE_UNRULYX, EXCHANGE_OPEN8, EXCHANGE_TRITON, EXCHANGE_TRIPLELIFT, EXCHANGE_TABOOLA, EXCHANGE_INMOBI, EXCHANGE_SMAATO, EXCHANGE_AJA, EXCHANGE_SUPERSHIP, EXCHANGE_NEXSTAR_DIGITAL, EXCHANGE_WAZE, EXCHANGE_SOUNDCAST, EXCHANGE_SHARETHROUGH, EXCHANGE_FYBER, EXCHANGE_RED_FOR_PUBLISHERS, EXCHANGE_MEDIANET, EXCHANGE_TAPJOY, EXCHANGE_VISTAR, EXCHANGE_DAX, EXCHANGE_JCD, EXCHANGE_PLACE_EXCHANGE, EXCHANGE_APPLOVIN, EXCHANGE_CONNATIX, EXCHANGE_RESET_DIGITAL, EXCHANGE_HIVESTACK, EXCHANGE_DRAX, EXCHANGE_APPLOVIN_GBID, EXCHANGE_FYBER_GBID, EXCHANGE_UNITY_GBID, EXCHANGE_CHARTBOOST_GBID, EXCHANGE_ADMOST_GBID, EXCHANGE_TOPON_GBID, EXCHANGE_NETFLIX, EXCHANGE_TUBI }
		enum StatusEnum extends Enum[StatusEnum] { case REVIEW_STATUS_UNSPECIFIED, REVIEW_STATUS_APPROVED, REVIEW_STATUS_REJECTED, REVIEW_STATUS_PENDING }
	}
	case class ExchangeReviewStatus(
	  /** The exchange reviewing the creative. */
		exchange: Option[Schema.ExchangeReviewStatus.ExchangeEnum] = None,
	  /** Status of the exchange review. */
		status: Option[Schema.ExchangeReviewStatus.StatusEnum] = None
	)
	
	object PublisherReviewStatus {
		enum StatusEnum extends Enum[StatusEnum] { case REVIEW_STATUS_UNSPECIFIED, REVIEW_STATUS_APPROVED, REVIEW_STATUS_REJECTED, REVIEW_STATUS_PENDING }
	}
	case class PublisherReviewStatus(
	  /** The publisher reviewing the creative. */
		publisherName: Option[String] = None,
	  /** Status of the publisher review. */
		status: Option[Schema.PublisherReviewStatus.StatusEnum] = None
	)
	
	object AssetAssociation {
		enum RoleEnum extends Enum[RoleEnum] { case ASSET_ROLE_UNSPECIFIED, ASSET_ROLE_MAIN, ASSET_ROLE_BACKUP, ASSET_ROLE_POLITE_LOAD, ASSET_ROLE_HEADLINE, ASSET_ROLE_LONG_HEADLINE, ASSET_ROLE_BODY, ASSET_ROLE_LONG_BODY, ASSET_ROLE_CAPTION_URL, ASSET_ROLE_CALL_TO_ACTION, ASSET_ROLE_ADVERTISER_NAME, ASSET_ROLE_PRICE, ASSET_ROLE_ANDROID_APP_ID, ASSET_ROLE_IOS_APP_ID, ASSET_ROLE_RATING, ASSET_ROLE_ICON, ASSET_ROLE_COVER_IMAGE }
	}
	case class AssetAssociation(
	  /** The associated asset. */
		asset: Option[Schema.Asset] = None,
	  /** The role of this asset for the creative. */
		role: Option[Schema.AssetAssociation.RoleEnum] = None
	)
	
	object ExitEvent {
		enum TypeEnum extends Enum[TypeEnum] { case EXIT_EVENT_TYPE_UNSPECIFIED, EXIT_EVENT_TYPE_DEFAULT, EXIT_EVENT_TYPE_BACKUP }
	}
	case class ExitEvent(
	  /** Required. The type of the exit event. */
		`type`: Option[Schema.ExitEvent.TypeEnum] = None,
	  /** Required. The click through URL of the exit event. This is required when type is: &#42; `EXIT_EVENT_TYPE_DEFAULT` &#42; `EXIT_EVENT_TYPE_BACKUP` */
		url: Option[String] = None,
	  /** The name of the click tag of the exit event. The name must be unique within one creative. Leave it empty or unset for creatives containing image assets only. */
		name: Option[String] = None,
	  /** The name used to identify this event in reports. Leave it empty or unset for creatives containing image assets only. */
		reportingName: Option[String] = None
	)
	
	case class TimerEvent(
	  /** Required. The name of the timer event. */
		name: Option[String] = None,
	  /** Required. The name used to identify this timer event in reports. */
		reportingName: Option[String] = None
	)
	
	case class CounterEvent(
	  /** Required. The name of the counter event. */
		name: Option[String] = None,
	  /** Required. The name used to identify this counter event in reports. */
		reportingName: Option[String] = None
	)
	
	case class AudioVideoOffset(
	  /** The offset in percentage of the audio or video duration. */
		percentage: Option[String] = None,
	  /** The offset in seconds from the start of the audio or video. */
		seconds: Option[String] = None
	)
	
	object UniversalAdId {
		enum RegistryEnum extends Enum[RegistryEnum] { case UNIVERSAL_AD_REGISTRY_UNSPECIFIED, UNIVERSAL_AD_REGISTRY_OTHER, UNIVERSAL_AD_REGISTRY_AD_ID, UNIVERSAL_AD_REGISTRY_CLEARCAST, UNIVERSAL_AD_REGISTRY_DV360, UNIVERSAL_AD_REGISTRY_CM }
	}
	case class UniversalAdId(
	  /** The unique creative identifier. */
		id: Option[String] = None,
	  /** The registry provides unique creative identifiers. */
		registry: Option[Schema.UniversalAdId.RegistryEnum] = None
	)
	
	object ThirdPartyUrl {
		enum TypeEnum extends Enum[TypeEnum] { case THIRD_PARTY_URL_TYPE_UNSPECIFIED, THIRD_PARTY_URL_TYPE_IMPRESSION, THIRD_PARTY_URL_TYPE_CLICK_TRACKING, THIRD_PARTY_URL_TYPE_AUDIO_VIDEO_START, THIRD_PARTY_URL_TYPE_AUDIO_VIDEO_FIRST_QUARTILE, THIRD_PARTY_URL_TYPE_AUDIO_VIDEO_MIDPOINT, THIRD_PARTY_URL_TYPE_AUDIO_VIDEO_THIRD_QUARTILE, THIRD_PARTY_URL_TYPE_AUDIO_VIDEO_COMPLETE, THIRD_PARTY_URL_TYPE_AUDIO_VIDEO_MUTE, THIRD_PARTY_URL_TYPE_AUDIO_VIDEO_PAUSE, THIRD_PARTY_URL_TYPE_AUDIO_VIDEO_REWIND, THIRD_PARTY_URL_TYPE_AUDIO_VIDEO_FULLSCREEN, THIRD_PARTY_URL_TYPE_AUDIO_VIDEO_STOP, THIRD_PARTY_URL_TYPE_AUDIO_VIDEO_CUSTOM, THIRD_PARTY_URL_TYPE_AUDIO_VIDEO_SKIP, THIRD_PARTY_URL_TYPE_AUDIO_VIDEO_PROGRESS }
	}
	case class ThirdPartyUrl(
	  /** The type of interaction needs to be tracked by the tracking URL */
		`type`: Option[Schema.ThirdPartyUrl.TypeEnum] = None,
	  /** Tracking URL used to track the interaction. Provide a URL with optional path or query string, beginning with `https:`. For example, https://www.example.com/path */
		url: Option[String] = None
	)
	
	case class Transcode(
	  /** The name of the transcoded file. */
		name: Option[String] = None,
	  /** The MIME type of the transcoded file. */
		mimeType: Option[String] = None,
	  /** Indicates if the transcoding was successful. */
		transcoded: Option[Boolean] = None,
	  /** The dimensions of the transcoded video. */
		dimensions: Option[Schema.Dimensions] = None,
	  /** The transcoding bit rate of the transcoded video, in kilobits per second. */
		bitRateKbps: Option[String] = None,
	  /** The frame rate of the transcoded video, in frames per second. */
		frameRate: Option[BigDecimal] = None,
	  /** The bit rate for the audio stream of the transcoded video, or the bit rate for the transcoded audio, in kilobits per second. */
		audioBitRateKbps: Option[String] = None,
	  /** The sample rate for the audio stream of the transcoded video, or the sample rate for the transcoded audio, in hertz. */
		audioSampleRateHz: Option[String] = None,
	  /** The size of the transcoded file, in bytes. */
		fileSizeBytes: Option[String] = None
	)
	
	case class CmTrackingAd(
	  /** The placement ID of the campaign manager 360 tracking Ad. */
		cmPlacementId: Option[String] = None,
	  /** The creative ID of the campaign manager 360 tracking Ad. */
		cmCreativeId: Option[String] = None,
	  /** The ad ID of the campaign manager 360 tracking Ad. */
		cmAdId: Option[String] = None
	)
	
	object ObaIcon {
		enum PositionEnum extends Enum[PositionEnum] { case OBA_ICON_POSITION_UNSPECIFIED, OBA_ICON_POSITION_UPPER_RIGHT, OBA_ICON_POSITION_UPPER_LEFT, OBA_ICON_POSITION_LOWER_RIGHT, OBA_ICON_POSITION_LOWER_LEFT }
	}
	case class ObaIcon(
	  /** The URL of the OBA icon resource. */
		resourceUrl: Option[String] = None,
	  /** The MIME type of the OBA icon resource. */
		resourceMimeType: Option[String] = None,
	  /** The position of the OBA icon on the creative. */
		position: Option[Schema.ObaIcon.PositionEnum] = None,
	  /** The dimensions of the OBA icon. */
		dimensions: Option[Schema.Dimensions] = None,
	  /** The program of the OBA icon. For example: “AdChoices”. */
		program: Option[String] = None,
	  /** Required. The landing page URL of the OBA icon. Only URLs of the following domains are allowed: &#42; https://info.evidon.com &#42; https://l.betrad.com */
		landingPageUrl: Option[String] = None,
	  /** Required. The click tracking URL of the OBA icon. Only URLs of the following domains are allowed: &#42; https://info.evidon.com &#42; https://l.betrad.com */
		clickTrackingUrl: Option[String] = None,
	  /** Required. The view tracking URL of the OBA icon. Only URLs of the following domains are allowed: &#42; https://info.evidon.com &#42; https://l.betrad.com */
		viewTrackingUrl: Option[String] = None
	)
	
	case class ListCreativesResponse(
	  /** The list of creatives. This list will be absent if empty. */
		creatives: Option[List[Schema.Creative]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListCreativesRequest` method to retrieve the next page of results. If this field is null, it means this is the last page. */
		nextPageToken: Option[String] = None
	)
	
	object CustomBiddingAlgorithmRules {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACCEPTED, REJECTED }
	}
	case class CustomBiddingAlgorithmRules(
	  /** Output only. The resource name of the rules resource. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the custom bidding algorithm that the rules resource belongs to. */
		customBiddingAlgorithmId: Option[String] = None,
	  /** Output only. The unique ID of the rules resource. */
		customBiddingAlgorithmRulesId: Option[String] = None,
	  /** Output only. The time when the rules resource was created. */
		createTime: Option[String] = None,
	  /** Output only. Whether the rules resource is currently being used for scoring by the parent algorithm. */
		active: Option[Boolean] = None,
	  /** Output only. The state of the rules resource. */
		state: Option[Schema.CustomBiddingAlgorithmRules.StateEnum] = None,
	  /** Output only. Error code of the rejected rules resource. This field will only be populated when the state is `REJECTED`. */
		error: Option[Schema.CustomBiddingAlgorithmRulesError] = None,
	  /** Required. Immutable. The reference to the uploaded AlgorithmRules file. */
		rules: Option[Schema.CustomBiddingAlgorithmRulesRef] = None
	)
	
	object CustomBiddingAlgorithmRulesError {
		enum ErrorCodeEnum extends Enum[ErrorCodeEnum] { case ERROR_CODE_UNSPECIFIED, SYNTAX_ERROR, CONSTRAINT_VIOLATION_ERROR, INTERNAL_ERROR }
	}
	case class CustomBiddingAlgorithmRulesError(
	  /** The type of error. */
		errorCode: Option[Schema.CustomBiddingAlgorithmRulesError.ErrorCodeEnum] = None
	)
	
	case class CustomBiddingAlgorithmRulesRef(
	  /** A resource name to be used in media.download to download the rules files. Or media.upload to upload the rules files. Resource names have the format `customBiddingAlgorithms/{custom_bidding_algorithm_id}/rulesRef/{ref_id}`. */
		resourceName: Option[String] = None
	)
	
	case class ListCustomBiddingAlgorithmRulesResponse(
	  /** The list of CustomBiddingAlgorithmRules resources. This list will be absent if empty. */
		customBiddingRules: Option[List[Schema.CustomBiddingAlgorithmRules]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListCustomBiddingAlgorithmRulesRequest` method to retrieve the next page of results. If this field is null, it means this is the last page. */
		nextPageToken: Option[String] = None
	)
	
	object CustomBiddingAlgorithm {
		enum EntityStatusEnum extends Enum[EntityStatusEnum] { case ENTITY_STATUS_UNSPECIFIED, ENTITY_STATUS_ACTIVE, ENTITY_STATUS_ARCHIVED, ENTITY_STATUS_DRAFT, ENTITY_STATUS_PAUSED, ENTITY_STATUS_SCHEDULED_FOR_DELETION }
		enum CustomBiddingAlgorithmTypeEnum extends Enum[CustomBiddingAlgorithmTypeEnum] { case CUSTOM_BIDDING_ALGORITHM_TYPE_UNSPECIFIED, SCRIPT_BASED, ADS_DATA_HUB_BASED, GOAL_BUILDER_BASED, RULE_BASED }
	}
	case class CustomBiddingAlgorithm(
	  /** Output only. The resource name of the custom bidding algorithm. */
		name: Option[String] = None,
	  /** Immutable. The unique ID of the partner that owns the custom bidding algorithm. */
		partnerId: Option[String] = None,
	  /** Immutable. The unique ID of the advertiser that owns the custom bidding algorithm. */
		advertiserId: Option[String] = None,
	  /** Output only. The unique ID of the custom bidding algorithm. Assigned by the system. */
		customBiddingAlgorithmId: Option[String] = None,
	  /** Required. The display name of the custom bidding algorithm. Must be UTF-8 encoded with a maximum size of 240 bytes. */
		displayName: Option[String] = None,
	  /** Controls whether or not the custom bidding algorithm can be used as a bidding strategy. Accepted values are: &#42; `ENTITY_STATUS_ACTIVE` &#42; `ENTITY_STATUS_ARCHIVED` */
		entityStatus: Option[Schema.CustomBiddingAlgorithm.EntityStatusEnum] = None,
	  /** Required. Immutable. The type of custom bidding algorithm. */
		customBiddingAlgorithmType: Option[Schema.CustomBiddingAlgorithm.CustomBiddingAlgorithmTypeEnum] = None,
	  /** The IDs of the advertisers who have access to this algorithm. If advertiser_id is set, this field will only consist of that value. This field will not be set if the algorithm [`owner`](/display-video/api/reference/rest/v1/customBiddingAlgorithms#CustomBiddingAlgorithm.FIELDS.oneof_owner) is a partner and is being retrieved using an advertiser [`accessor`](/display-video/api/reference/rest/v1/customBiddingAlgorithms/list#body.QUERY_PARAMETERS.oneof_accessor). */
		sharedAdvertiserIds: Option[List[String]] = None,
	  /** Output only. The details of custom bidding models for each advertiser who has access. This field may only include the details of the queried advertiser if the algorithm [`owner`](/display-video/api/reference/rest/v1/customBiddingAlgorithms#CustomBiddingAlgorithm.FIELDS.oneof_owner) is a partner and is being retrieved using an advertiser [`accessor`](/display-video/api/reference/rest/v1/customBiddingAlgorithms/list#body.QUERY_PARAMETERS.oneof_accessor). */
		modelDetails: Option[List[Schema.CustomBiddingModelDetails]] = None
	)
	
	object CustomBiddingModelDetails {
		enum ReadinessStateEnum extends Enum[ReadinessStateEnum] { case READINESS_STATE_UNSPECIFIED, READINESS_STATE_ACTIVE, READINESS_STATE_INSUFFICIENT_DATA, READINESS_STATE_TRAINING, READINESS_STATE_NO_VALID_SCRIPT, READINESS_STATE_EVALUATION_FAILURE }
		enum SuspensionStateEnum extends Enum[SuspensionStateEnum] { case SUSPENSION_STATE_UNSPECIFIED, SUSPENSION_STATE_ENABLED, SUSPENSION_STATE_DORMANT, SUSPENSION_STATE_SUSPENDED }
	}
	case class CustomBiddingModelDetails(
	  /** The unique ID of the relevant advertiser. */
		advertiserId: Option[String] = None,
	  /** The readiness state of custom bidding model. */
		readinessState: Option[Schema.CustomBiddingModelDetails.ReadinessStateEnum] = None,
	  /** Output only. The suspension state of custom bidding model. */
		suspensionState: Option[Schema.CustomBiddingModelDetails.SuspensionStateEnum] = None
	)
	
	case class ListCustomBiddingAlgorithmsResponse(
	  /** The list of custom bidding algorithms. This list will be absent if empty. */
		customBiddingAlgorithms: Option[List[Schema.CustomBiddingAlgorithm]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListCustomBiddingAlgorithmsRequest` method to retrieve the next page of results. If this field is null, it means this is the last page. */
		nextPageToken: Option[String] = None
	)
	
	object CustomBiddingScript {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACCEPTED, REJECTED, PENDING }
	}
	case class CustomBiddingScript(
	  /** Output only. The resource name of the custom bidding script. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the custom bidding algorithm the script belongs to. */
		customBiddingAlgorithmId: Option[String] = None,
	  /** Output only. The unique ID of the custom bidding script. */
		customBiddingScriptId: Option[String] = None,
	  /** Output only. The time when the script was created. */
		createTime: Option[String] = None,
	  /** Output only. Whether the script is currently being used for scoring by the parent algorithm. */
		active: Option[Boolean] = None,
	  /** Output only. The state of the custom bidding script. */
		state: Option[Schema.CustomBiddingScript.StateEnum] = None,
	  /** Output only. Error details of a rejected custom bidding script. This field will only be populated when state is REJECTED. */
		errors: Option[List[Schema.ScriptError]] = None,
	  /** The reference to the uploaded script file. */
		script: Option[Schema.CustomBiddingScriptRef] = None
	)
	
	object ScriptError {
		enum ErrorCodeEnum extends Enum[ErrorCodeEnum] { case ERROR_CODE_UNSPECIFIED, SYNTAX_ERROR, DEPRECATED_SYNTAX, INTERNAL_ERROR }
	}
	case class ScriptError(
	  /** The type of error. */
		errorCode: Option[Schema.ScriptError.ErrorCodeEnum] = None,
	  /** The line number in the script where the error was thrown. */
		line: Option[String] = None,
	  /** The column number in the script where the error was thrown. */
		column: Option[String] = None,
	  /** The detailed error message. */
		errorMessage: Option[String] = None
	)
	
	case class CustomBiddingScriptRef(
	  /** A resource name to be used in media.download to Download the script files. Or media.upload to Upload the script files. Resource names have the format `customBiddingAlgorithms/{custom_bidding_algorithm_id}/scriptRef/{ref_id}`. */
		resourceName: Option[String] = None
	)
	
	case class ListCustomBiddingScriptsResponse(
	  /** The list of custom bidding scripts. This list will be absent if empty. */
		customBiddingScripts: Option[List[Schema.CustomBiddingScript]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListCustomBiddingScriptsRequest` method to retrieve the next page of results. If this field is null, it means this is the last page. */
		nextPageToken: Option[String] = None
	)
	
	case class CustomList(
	  /** Output only. The resource name of the custom list. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the custom list. Assigned by the system. */
		customListId: Option[String] = None,
	  /** Output only. The display name of the custom list. . */
		displayName: Option[String] = None
	)
	
	case class ListCustomListsResponse(
	  /** The list of custom lists. This list will be absent if empty. */
		customLists: Option[List[Schema.CustomList]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListCustomLists` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object FirstAndThirdPartyAudience {
		enum FirstAndThirdPartyAudienceTypeEnum extends Enum[FirstAndThirdPartyAudienceTypeEnum] { case FIRST_AND_THIRD_PARTY_AUDIENCE_TYPE_UNSPECIFIED, FIRST_AND_THIRD_PARTY_AUDIENCE_TYPE_FIRST_PARTY, FIRST_AND_THIRD_PARTY_AUDIENCE_TYPE_THIRD_PARTY }
		enum AudienceTypeEnum extends Enum[AudienceTypeEnum] { case AUDIENCE_TYPE_UNSPECIFIED, CUSTOMER_MATCH_CONTACT_INFO, CUSTOMER_MATCH_DEVICE_ID, CUSTOMER_MATCH_USER_ID, ACTIVITY_BASED, FREQUENCY_CAP, TAG_BASED, YOUTUBE_USERS, LICENSED }
		enum AudienceSourceEnum extends Enum[AudienceSourceEnum] { case AUDIENCE_SOURCE_UNSPECIFIED, DISPLAY_VIDEO_360, CAMPAIGN_MANAGER, AD_MANAGER, SEARCH_ADS_360, YOUTUBE, ADS_DATA_HUB }
	}
	case class FirstAndThirdPartyAudience(
	  /** Output only. The resource name of the first and third party audience. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the first and third party audience. Assigned by the system. */
		firstAndThirdPartyAudienceId: Option[String] = None,
	  /** The display name of the first and third party audience. */
		displayName: Option[String] = None,
	  /** The user-provided description of the audience. Only applicable to first party audiences. */
		description: Option[String] = None,
	  /** Whether the audience is a first or third party audience. */
		firstAndThirdPartyAudienceType: Option[Schema.FirstAndThirdPartyAudience.FirstAndThirdPartyAudienceTypeEnum] = None,
	  /** The type of the audience. */
		audienceType: Option[Schema.FirstAndThirdPartyAudience.AudienceTypeEnum] = None,
	  /** Output only. The source of the audience. */
		audienceSource: Option[Schema.FirstAndThirdPartyAudience.AudienceSourceEnum] = None,
	  /** The duration in days that an entry remains in the audience after the qualifying event. If the audience has no expiration, set the value of this field to 10000. Otherwise, the set value must be greater than 0 and less than or equal to 540. Only applicable to first party audiences. This field is required if one of the following audience_type is used: &#42; `CUSTOMER_MATCH_CONTACT_INFO` &#42; `CUSTOMER_MATCH_DEVICE_ID` */
		membershipDurationDays: Option[String] = None,
	  /** Output only. The estimated audience size for the Display network. If the size is less than 1000, the number will be hidden and 0 will be returned due to privacy reasons. Otherwise, the number will be rounded off to two significant digits. Only returned in GET request. */
		displayAudienceSize: Option[String] = None,
	  /** Output only. The estimated audience size for the Display network in the past month. If the size is less than 1000, the number will be hidden and 0 will be returned due to privacy reasons. Otherwise, the number will be rounded off to two significant digits. Only returned in GET request. */
		activeDisplayAudienceSize: Option[String] = None,
	  /** Output only. The estimated audience size for YouTube network. If the size is less than 1000, the number will be hidden and 0 will be returned due to privacy reasons. Otherwise, the number will be rounded off to two significant digits. Only applicable to first party audiences. Only returned in GET request. */
		youtubeAudienceSize: Option[String] = None,
	  /** Output only. The estimated audience size for Gmail network. If the size is less than 1000, the number will be hidden and 0 will be returned due to privacy reasons. Otherwise, the number will be rounded off to two significant digits. Only applicable to first party audiences. Only returned in GET request. */
		gmailAudienceSize: Option[String] = None,
	  /** Output only. The estimated mobile app audience size in Display network. If the size is less than 1000, the number will be hidden and 0 will be returned due to privacy reasons. Otherwise, the number will be rounded off to two significant digits. Only applicable to first party audiences. Only returned in GET request. */
		displayMobileAppAudienceSize: Option[String] = None,
	  /** Output only. The estimated mobile web audience size in Display network. If the size is less than 1000, the number will be hidden and 0 will be returned due to privacy reasons. Otherwise, the number will be rounded off to two significant digits. Only applicable to first party audiences. Only returned in GET request. */
		displayMobileWebAudienceSize: Option[String] = None,
	  /** Output only. The estimated desktop audience size in Display network. If the size is less than 1000, the number will be hidden and 0 will be returned due to privacy reasons. Otherwise, the number will be rounded off to two significant digits. Only applicable to first party audiences. Only returned in GET request. */
		displayDesktopAudienceSize: Option[String] = None,
	  /** The app_id matches with the type of the mobile_device_ids being uploaded. Only applicable to audience_type `CUSTOMER_MATCH_DEVICE_ID` */
		appId: Option[String] = None,
	  /** Input only. A list of contact information to define the initial audience members. Only applicable to audience_type `CUSTOMER_MATCH_CONTACT_INFO` */
		contactInfoList: Option[Schema.ContactInfoList] = None,
	  /** Input only. A list of mobile device IDs to define the initial audience members. Only applicable to audience_type `CUSTOMER_MATCH_DEVICE_ID` */
		mobileDeviceIdList: Option[Schema.MobileDeviceIdList] = None
	)
	
	case class ContactInfoList(
	  /** A list of ContactInfo objects defining Customer Match audience members. The size of members after splitting the contact_infos mustn't be greater than 500,000. */
		contactInfos: Option[List[Schema.ContactInfo]] = None,
	  /** Input only. The consent setting for the users in contact_infos. Leaving this field unset indicates that consent is not specified. If ad_user_data or ad_personalization fields are set to `CONSENT_STATUS_DENIED`, the request will return an error. */
		consent: Option[Schema.Consent] = None
	)
	
	case class ContactInfo(
	  /** SHA256 hashed first name of the member. Before hashing, remove all whitespace and make sure the string is all lowercase. Must also be set with the following fields: &#42; country_code &#42; hashed_last_name &#42; zip_codes */
		hashedFirstName: Option[String] = None,
	  /** SHA256 hashed last name of the member. Before hashing, remove all whitespace and make sure the string is all lowercase. Must also be set with the following fields: &#42; country_code &#42; hashed_first_name &#42; zip_codes */
		hashedLastName: Option[String] = None,
	  /** Country code of the member. Must also be set with the following fields: &#42; hashed_first_name &#42; hashed_last_name &#42; zip_codes */
		countryCode: Option[String] = None,
	  /** A list of SHA256 hashed email of the member. Before hashing, remove all whitespace and make sure the string is all lowercase. */
		hashedEmails: Option[List[String]] = None,
	  /** A list of SHA256 hashed phone numbers of the member. Before hashing, all phone numbers must be formatted using the [E.164 format](//en.wikipedia.org/wiki/E.164) and include the country calling code. */
		hashedPhoneNumbers: Option[List[String]] = None,
	  /** A list of zip codes of the member. Must also be set with the following fields: &#42; country_code &#42; hashed_first_name &#42; hashed_last_name */
		zipCodes: Option[List[String]] = None
	)
	
	object Consent {
		enum AdUserDataEnum extends Enum[AdUserDataEnum] { case CONSENT_STATUS_UNSPECIFIED, CONSENT_STATUS_GRANTED, CONSENT_STATUS_DENIED }
		enum AdPersonalizationEnum extends Enum[AdPersonalizationEnum] { case CONSENT_STATUS_UNSPECIFIED, CONSENT_STATUS_GRANTED, CONSENT_STATUS_DENIED }
	}
	case class Consent(
	  /** Represents consent for ad user data. */
		adUserData: Option[Schema.Consent.AdUserDataEnum] = None,
	  /** Represents consent for ad personalization. */
		adPersonalization: Option[Schema.Consent.AdPersonalizationEnum] = None
	)
	
	case class MobileDeviceIdList(
	  /** A list of mobile device IDs defining Customer Match audience members. The size of mobile_device_ids mustn't be greater than 500,000. */
		mobileDeviceIds: Option[List[String]] = None,
	  /** Input only. The consent setting for the users in mobile_device_ids. Leaving this field unset indicates that consent is not specified. If ad_user_data or ad_personalization fields are set to `CONSENT_STATUS_DENIED`, the request will return an error. */
		consent: Option[Schema.Consent] = None
	)
	
	case class ListFirstAndThirdPartyAudiencesResponse(
	  /** The list of first and third party audiences. Audience size properties will not be included. This list will be absent if empty. */
		firstAndThirdPartyAudiences: Option[List[Schema.FirstAndThirdPartyAudience]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListFirstAndThirdPartyAudiences` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class EditCustomerMatchMembersRequest(
	  /** Input only. A list of contact information to define the members to be added. */
		addedContactInfoList: Option[Schema.ContactInfoList] = None,
	  /** Input only. A list of mobile device IDs to define the members to be added. */
		addedMobileDeviceIdList: Option[Schema.MobileDeviceIdList] = None,
	  /** Input only. A list of contact information to define the members to be removed. */
		removedContactInfoList: Option[Schema.ContactInfoList] = None,
	  /** Input only. A list of mobile device IDs to define the members to be removed. */
		removedMobileDeviceIdList: Option[Schema.MobileDeviceIdList] = None,
	  /** Required. The ID of the owner advertiser of the updated Customer Match FirstAndThirdPartyAudience. */
		advertiserId: Option[String] = None
	)
	
	case class EditCustomerMatchMembersResponse(
	  /** Required. The ID of the updated Customer Match FirstAndThirdPartyAudience. */
		firstAndThirdPartyAudienceId: Option[String] = None
	)
	
	object FloodlightActivity {
		enum ServingStatusEnum extends Enum[ServingStatusEnum] { case FLOODLIGHT_ACTIVITY_SERVING_STATUS_UNSPECIFIED, FLOODLIGHT_ACTIVITY_SERVING_STATUS_ENABLED, FLOODLIGHT_ACTIVITY_SERVING_STATUS_DISABLED }
	}
	case class FloodlightActivity(
	  /** Output only. The resource name of the Floodlight activity. */
		name: Option[String] = None,
	  /** Required. Immutable. The ID of the parent Floodlight group. */
		floodlightGroupId: Option[String] = None,
	  /** Output only. The unique ID of the Floodlight activity. Assigned by the system. */
		floodlightActivityId: Option[String] = None,
	  /** Required. The display name of the Floodlight activity. */
		displayName: Option[String] = None,
	  /** Optional. Whether the Floodlight activity is served. */
		servingStatus: Option[Schema.FloodlightActivity.ServingStatusEnum] = None,
	  /** Output only. IDs of the advertisers that have access to the parent Floodlight group. Only advertisers under the provided partner ID will be listed in this field. */
		advertiserIds: Option[List[String]] = None,
	  /** Output only. A list of configuration objects designating whether remarketing for this Floodlight Activity is enabled and available for a specifc advertiser. If enabled, this Floodlight Activity generates a remarketing user list that is able to be used in targeting under the advertiser. */
		remarketingConfigs: Option[List[Schema.RemarketingConfig]] = None,
	  /** Output only. Whether tags are required to be compliant. */
		sslRequired: Option[Boolean] = None
	)
	
	case class RemarketingConfig(
	  /** Output only. The ID of the advertiser. */
		advertiserId: Option[String] = None,
	  /** Output only. Whether the Floodlight activity remarketing user list is available to the identified advertiser. */
		remarketingEnabled: Option[Boolean] = None
	)
	
	case class ListFloodlightActivitiesResponse(
	  /** The list of Floodlight activities. This list will be absent if empty. */
		floodlightActivities: Option[List[Schema.FloodlightActivity]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListFloodlightActivities` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object FloodlightGroup {
		enum WebTagTypeEnum extends Enum[WebTagTypeEnum] { case WEB_TAG_TYPE_UNSPECIFIED, WEB_TAG_TYPE_NONE, WEB_TAG_TYPE_IMAGE, WEB_TAG_TYPE_DYNAMIC }
	}
	case class FloodlightGroup(
	  /** Output only. The resource name of the Floodlight group. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the Floodlight group. Assigned by the system. */
		floodlightGroupId: Option[String] = None,
	  /** Required. The display name of the Floodlight group. */
		displayName: Option[String] = None,
	  /** Required. The web tag type enabled for the Floodlight group. */
		webTagType: Option[Schema.FloodlightGroup.WebTagTypeEnum] = None,
	  /** Required. The lookback window for the Floodlight group. Both click_days and impression_days are required. Acceptable values for both are `0` to `90`, inclusive. */
		lookbackWindow: Option[Schema.LookbackWindow] = None,
	  /** User-defined custom variables owned by the Floodlight group. Use custom Floodlight variables to create reporting data that is tailored to your unique business needs. Custom Floodlight variables use the keys `U1=`, `U2=`, and so on, and can take any values that you choose to pass to them. You can use them to track virtually any type of data that you collect about your customers, such as the genre of movie that a customer purchases, the country to which the item is shipped, and so on. Custom Floodlight variables may not be used to pass any data that could be used or recognized as personally identifiable information (PII). Example: `custom_variables { fields { "U1": value { number_value: 123.4 }, "U2": value { string_value: "MyVariable2" }, "U3": value { string_value: "MyVariable3" } } }` Acceptable values for keys are "U1" through "U100", inclusive. String values must be less than 64 characters long, and cannot contain the following characters: `"<>`. */
		customVariables: Option[Map[String, JsValue]] = None,
	  /** The Active View video viewability metric configuration for the Floodlight group. */
		activeViewConfig: Option[Schema.ActiveViewVideoViewabilityMetricConfig] = None
	)
	
	case class LookbackWindow(
	  /** Lookback window, in days, from the last time a given user clicked on one of your ads. */
		clickDays: Option[Int] = None,
	  /** Lookback window, in days, from the last time a given user viewed one of your ads. */
		impressionDays: Option[Int] = None
	)
	
	object ActiveViewVideoViewabilityMetricConfig {
		enum MinimumViewabilityEnum extends Enum[MinimumViewabilityEnum] { case VIEWABILITY_PERCENT_UNSPECIFIED, VIEWABILITY_PERCENT_0, VIEWABILITY_PERCENT_25, VIEWABILITY_PERCENT_50, VIEWABILITY_PERCENT_75, VIEWABILITY_PERCENT_100 }
		enum MinimumVolumeEnum extends Enum[MinimumVolumeEnum] { case VIDEO_VOLUME_PERCENT_UNSPECIFIED, VIDEO_VOLUME_PERCENT_0, VIDEO_VOLUME_PERCENT_10 }
		enum MinimumDurationEnum extends Enum[MinimumDurationEnum] { case VIDEO_DURATION_UNSPECIFIED, VIDEO_DURATION_SECONDS_NONE, VIDEO_DURATION_SECONDS_0, VIDEO_DURATION_SECONDS_1, VIDEO_DURATION_SECONDS_2, VIDEO_DURATION_SECONDS_3, VIDEO_DURATION_SECONDS_4, VIDEO_DURATION_SECONDS_5, VIDEO_DURATION_SECONDS_6, VIDEO_DURATION_SECONDS_7, VIDEO_DURATION_SECONDS_8, VIDEO_DURATION_SECONDS_9, VIDEO_DURATION_SECONDS_10, VIDEO_DURATION_SECONDS_11, VIDEO_DURATION_SECONDS_12, VIDEO_DURATION_SECONDS_13, VIDEO_DURATION_SECONDS_14, VIDEO_DURATION_SECONDS_15, VIDEO_DURATION_SECONDS_30, VIDEO_DURATION_SECONDS_45, VIDEO_DURATION_SECONDS_60 }
		enum MinimumQuartileEnum extends Enum[MinimumQuartileEnum] { case VIDEO_DURATION_QUARTILE_UNSPECIFIED, VIDEO_DURATION_QUARTILE_NONE, VIDEO_DURATION_QUARTILE_FIRST, VIDEO_DURATION_QUARTILE_SECOND, VIDEO_DURATION_QUARTILE_THIRD, VIDEO_DURATION_QUARTILE_FOURTH }
	}
	case class ActiveViewVideoViewabilityMetricConfig(
	  /** Required. The display name of the custom metric. */
		displayName: Option[String] = None,
	  /** Required. The minimum percentage of the video ad's pixels visible on the screen in order for an impression to be recorded. */
		minimumViewability: Option[Schema.ActiveViewVideoViewabilityMetricConfig.MinimumViewabilityEnum] = None,
	  /** Required. The minimum percentage of the video ad's volume required in order for an impression to be recorded. */
		minimumVolume: Option[Schema.ActiveViewVideoViewabilityMetricConfig.MinimumVolumeEnum] = None,
	  /** The minimum visible video duration required (in seconds) in order for an impression to be recorded. You must specify minimum_duration, minimum_quartile or both. If both are specified, an impression meets the metric criteria if either requirement is met (whichever happens first). */
		minimumDuration: Option[Schema.ActiveViewVideoViewabilityMetricConfig.MinimumDurationEnum] = None,
	  /** The minimum visible video duration required, based on the video quartiles, in order for an impression to be recorded. You must specify minimum_duration, minimum_quartile or both. If both are specified, an impression meets the metric criteria if either requirement is met (whichever happens first). */
		minimumQuartile: Option[Schema.ActiveViewVideoViewabilityMetricConfig.MinimumQuartileEnum] = None
	)
	
	object GoogleAudience {
		enum GoogleAudienceTypeEnum extends Enum[GoogleAudienceTypeEnum] { case GOOGLE_AUDIENCE_TYPE_UNSPECIFIED, GOOGLE_AUDIENCE_TYPE_AFFINITY, GOOGLE_AUDIENCE_TYPE_IN_MARKET, GOOGLE_AUDIENCE_TYPE_INSTALLED_APPS, GOOGLE_AUDIENCE_TYPE_NEW_MOBILE_DEVICES, GOOGLE_AUDIENCE_TYPE_LIFE_EVENT, GOOGLE_AUDIENCE_TYPE_EXTENDED_DEMOGRAPHIC }
	}
	case class GoogleAudience(
	  /** Output only. The resource name of the google audience. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the Google audience. Assigned by the system. */
		googleAudienceId: Option[String] = None,
	  /** Output only. The display name of the Google audience. . */
		displayName: Option[String] = None,
	  /** Output only. The type of Google audience. . */
		googleAudienceType: Option[Schema.GoogleAudience.GoogleAudienceTypeEnum] = None
	)
	
	case class ListGoogleAudiencesResponse(
	  /** The list of Google audiences. This list will be absent if empty. */
		googleAudiences: Option[List[Schema.GoogleAudience]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListGoogleAudiences` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object GuaranteedOrder {
		enum ExchangeEnum extends Enum[ExchangeEnum] { case EXCHANGE_UNSPECIFIED, EXCHANGE_GOOGLE_AD_MANAGER, EXCHANGE_APPNEXUS, EXCHANGE_BRIGHTROLL, EXCHANGE_ADFORM, EXCHANGE_ADMETA, EXCHANGE_ADMIXER, EXCHANGE_ADSMOGO, EXCHANGE_ADSWIZZ, EXCHANGE_BIDSWITCH, EXCHANGE_BRIGHTROLL_DISPLAY, EXCHANGE_CADREON, EXCHANGE_DAILYMOTION, EXCHANGE_FIVE, EXCHANGE_FLUCT, EXCHANGE_FREEWHEEL, EXCHANGE_GENIEE, EXCHANGE_GUMGUM, EXCHANGE_IMOBILE, EXCHANGE_IBILLBOARD, EXCHANGE_IMPROVE_DIGITAL, EXCHANGE_INDEX, EXCHANGE_KARGO, EXCHANGE_MICROAD, EXCHANGE_MOPUB, EXCHANGE_NEND, EXCHANGE_ONE_BY_AOL_DISPLAY, EXCHANGE_ONE_BY_AOL_MOBILE, EXCHANGE_ONE_BY_AOL_VIDEO, EXCHANGE_OOYALA, EXCHANGE_OPENX, EXCHANGE_PERMODO, EXCHANGE_PLATFORMONE, EXCHANGE_PLATFORMID, EXCHANGE_PUBMATIC, EXCHANGE_PULSEPOINT, EXCHANGE_REVENUEMAX, EXCHANGE_RUBICON, EXCHANGE_SMARTCLIP, EXCHANGE_SMARTRTB, EXCHANGE_SMARTSTREAMTV, EXCHANGE_SOVRN, EXCHANGE_SPOTXCHANGE, EXCHANGE_STROER, EXCHANGE_TEADSTV, EXCHANGE_TELARIA, EXCHANGE_TVN, EXCHANGE_UNITED, EXCHANGE_YIELDLAB, EXCHANGE_YIELDMO, EXCHANGE_UNRULYX, EXCHANGE_OPEN8, EXCHANGE_TRITON, EXCHANGE_TRIPLELIFT, EXCHANGE_TABOOLA, EXCHANGE_INMOBI, EXCHANGE_SMAATO, EXCHANGE_AJA, EXCHANGE_SUPERSHIP, EXCHANGE_NEXSTAR_DIGITAL, EXCHANGE_WAZE, EXCHANGE_SOUNDCAST, EXCHANGE_SHARETHROUGH, EXCHANGE_FYBER, EXCHANGE_RED_FOR_PUBLISHERS, EXCHANGE_MEDIANET, EXCHANGE_TAPJOY, EXCHANGE_VISTAR, EXCHANGE_DAX, EXCHANGE_JCD, EXCHANGE_PLACE_EXCHANGE, EXCHANGE_APPLOVIN, EXCHANGE_CONNATIX, EXCHANGE_RESET_DIGITAL, EXCHANGE_HIVESTACK, EXCHANGE_DRAX, EXCHANGE_APPLOVIN_GBID, EXCHANGE_FYBER_GBID, EXCHANGE_UNITY_GBID, EXCHANGE_CHARTBOOST_GBID, EXCHANGE_ADMOST_GBID, EXCHANGE_TOPON_GBID, EXCHANGE_NETFLIX, EXCHANGE_TUBI }
	}
	case class GuaranteedOrder(
	  /** Output only. The resource name of the guaranteed order. */
		name: Option[String] = None,
	  /** Output only. The legacy ID of the guaranteed order. Assigned by the original exchange. The legacy ID is unique within one exchange, but is not guaranteed to be unique across all guaranteed orders. This ID is used in SDF and UI. */
		legacyGuaranteedOrderId: Option[String] = None,
	  /** Required. Immutable. The exchange where the guaranteed order originated. */
		exchange: Option[Schema.GuaranteedOrder.ExchangeEnum] = None,
	  /** Output only. The unique identifier of the guaranteed order. The guaranteed order IDs have the format `{exchange}-{legacy_guaranteed_order_id}`. */
		guaranteedOrderId: Option[String] = None,
	  /** Required. The display name of the guaranteed order. Must be UTF-8 encoded with a maximum size of 240 bytes. */
		displayName: Option[String] = None,
	  /** The status settings of the guaranteed order. */
		status: Option[Schema.GuaranteedOrderStatus] = None,
	  /** Output only. The timestamp when the guaranteed order was last updated. Assigned by the system. */
		updateTime: Option[String] = None,
	  /** Required. The publisher name of the guaranteed order. Must be UTF-8 encoded with a maximum size of 240 bytes. */
		publisherName: Option[String] = None,
	  /** The partner with read/write access to the guaranteed order. */
		readWritePartnerId: Option[String] = None,
	  /** The advertiser with read/write access to the guaranteed order. This is also the default advertiser of the guaranteed order. */
		readWriteAdvertiserId: Option[String] = None,
	  /** Whether all advertisers of read_write_partner_id have read access to the guaranteed order. Only applicable if read_write_partner_id is set. If True, overrides read_advertiser_ids. */
		readAccessInherited: Option[Boolean] = None,
	  /** The IDs of advertisers with read access to the guaranteed order. This field must not include the advertiser assigned to read_write_advertiser_id if it is set. All advertisers in this field must belong to read_write_partner_id or the same partner as read_write_advertiser_id. */
		readAdvertiserIds: Option[List[String]] = None,
	  /** Output only. The ID of default advertiser of the guaranteed order. The default advertiser is either the read_write_advertiser_id or, if that is not set, the first advertiser listed in read_advertiser_ids. Otherwise, there is no default advertiser. */
		defaultAdvertiserId: Option[String] = None,
	  /** The ID of the default campaign that is assigned to the guaranteed order. The default campaign must belong to the default advertiser. */
		defaultCampaignId: Option[String] = None
	)
	
	object GuaranteedOrderStatus {
		enum EntityStatusEnum extends Enum[EntityStatusEnum] { case ENTITY_STATUS_UNSPECIFIED, ENTITY_STATUS_ACTIVE, ENTITY_STATUS_ARCHIVED, ENTITY_STATUS_DRAFT, ENTITY_STATUS_PAUSED, ENTITY_STATUS_SCHEDULED_FOR_DELETION }
		enum ConfigStatusEnum extends Enum[ConfigStatusEnum] { case GUARANTEED_ORDER_CONFIG_STATUS_UNSPECIFIED, PENDING, COMPLETED }
	}
	case class GuaranteedOrderStatus(
	  /** Whether or not the guaranteed order is servable. Acceptable values are `ENTITY_STATUS_ACTIVE`, `ENTITY_STATUS_ARCHIVED`, and `ENTITY_STATUS_PAUSED`. Default value is `ENTITY_STATUS_ACTIVE`. */
		entityStatus: Option[Schema.GuaranteedOrderStatus.EntityStatusEnum] = None,
	  /** The user-provided reason for pausing this guaranteed order. Must be UTF-8 encoded with a maximum length of 100 bytes. Only applicable when entity_status is set to `ENTITY_STATUS_PAUSED`. */
		entityPauseReason: Option[String] = None,
	  /** Output only. The configuration status of the guaranteed order. Acceptable values are `PENDING` and `COMPLETED`. A guaranteed order must be configured (fill in the required fields, choose creatives, and select a default campaign) before it can serve. Currently the configuration action can only be performed via UI. */
		configStatus: Option[Schema.GuaranteedOrderStatus.ConfigStatusEnum] = None
	)
	
	case class ListGuaranteedOrdersResponse(
	  /** The list of guaranteed orders. This list will be absent if empty. */
		guaranteedOrders: Option[List[Schema.GuaranteedOrder]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListGuaranteedOrders` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class EditGuaranteedOrderReadAccessorsRequest(
	  /** Required. The partner context in which the change is being made. */
		partnerId: Option[String] = None,
	  /** Whether to give all advertisers of the read/write accessor partner read access to the guaranteed order. Only applicable if read_write_partner_id is set in the guaranteed order. */
		readAccessInherited: Option[Boolean] = None,
	  /** The advertisers to add as read accessors to the guaranteed order. */
		addedAdvertisers: Option[List[String]] = None,
	  /** The advertisers to remove as read accessors to the guaranteed order. */
		removedAdvertisers: Option[List[String]] = None
	)
	
	case class EditGuaranteedOrderReadAccessorsResponse(
	  /** Whether all advertisers of read_write_partner_id have read access to the guaranteed order. */
		readAccessInherited: Option[Boolean] = None,
	  /** The IDs of advertisers with read access to the guaranteed order. */
		readAdvertiserIds: Option[List[String]] = None
	)
	
	case class ListInsertionOrderAssignedTargetingOptionsResponse(
	  /** The list of assigned targeting options. This list will be absent if empty. */
		assignedTargetingOptions: Option[List[Schema.AssignedTargetingOption]] = None,
	  /** A token identifying the next page of results. This value should be specified as the pageToken in a subsequent ListInsertionOrderAssignedTargetingOptionsRequest to fetch the next page of results. This token will be absent if there are no more assigned_targeting_options to return. */
		nextPageToken: Option[String] = None
	)
	
	case class BulkListInsertionOrderAssignedTargetingOptionsResponse(
	  /** The list of assigned targeting options. This list will be absent if empty. */
		assignedTargetingOptions: Option[List[Schema.AssignedTargetingOption]] = None,
	  /** A token identifying the next page of results. This value should be specified as the pageToken in a subsequent BulkListInsertionOrderAssignedTargetingOptionsRequest to fetch the next page of results. This token will be absent if there are no more assigned_targeting_options to return. */
		nextPageToken: Option[String] = None
	)
	
	object InsertionOrder {
		enum InsertionOrderTypeEnum extends Enum[InsertionOrderTypeEnum] { case INSERTION_ORDER_TYPE_UNSPECIFIED, RTB, OVER_THE_TOP }
		enum EntityStatusEnum extends Enum[EntityStatusEnum] { case ENTITY_STATUS_UNSPECIFIED, ENTITY_STATUS_ACTIVE, ENTITY_STATUS_ARCHIVED, ENTITY_STATUS_DRAFT, ENTITY_STATUS_PAUSED, ENTITY_STATUS_SCHEDULED_FOR_DELETION }
		enum ReservationTypeEnum extends Enum[ReservationTypeEnum] { case RESERVATION_TYPE_UNSPECIFIED, RESERVATION_TYPE_NOT_GUARANTEED, RESERVATION_TYPE_PROGRAMMATIC_GUARANTEED, RESERVATION_TYPE_TAG_GUARANTEED, RESERVATION_TYPE_PETRA_VIRAL, RESERVATION_TYPE_INSTANT_RESERVE }
		enum OptimizationObjectiveEnum extends Enum[OptimizationObjectiveEnum] { case OPTIMIZATION_OBJECTIVE_UNSPECIFIED, CONVERSION, CLICK, BRAND_AWARENESS, CUSTOM, NO_OBJECTIVE }
	}
	case class InsertionOrder(
	  /** Output only. The resource name of the insertion order. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the advertiser the insertion order belongs to. */
		advertiserId: Option[String] = None,
	  /** Required. Immutable. The unique ID of the campaign that the insertion order belongs to. */
		campaignId: Option[String] = None,
	  /** Output only. The unique ID of the insertion order. Assigned by the system. */
		insertionOrderId: Option[String] = None,
	  /** Required. The display name of the insertion order. Must be UTF-8 encoded with a maximum size of 240 bytes. */
		displayName: Option[String] = None,
	  /** The type of insertion order. If this field is unspecified in creation, the value defaults to `RTB`. */
		insertionOrderType: Option[Schema.InsertionOrder.InsertionOrderTypeEnum] = None,
	  /** Required. Controls whether or not the insertion order can spend its budget and bid on inventory. &#42; For CreateInsertionOrder method, only `ENTITY_STATUS_DRAFT` is allowed. To activate an insertion order, use UpdateInsertionOrder method and update the status to `ENTITY_STATUS_ACTIVE` after creation. &#42; An insertion order cannot be changed back to `ENTITY_STATUS_DRAFT` status from any other status. &#42; An insertion order cannot be set to `ENTITY_STATUS_ACTIVE` if its parent campaign is not active. */
		entityStatus: Option[Schema.InsertionOrder.EntityStatusEnum] = None,
	  /** Output only. The timestamp when the insertion order was last updated. Assigned by the system. */
		updateTime: Option[String] = None,
	  /** The partner costs associated with the insertion order. If absent or empty in CreateInsertionOrder method, the newly created insertion order will inherit partner costs from the partner settings. */
		partnerCosts: Option[List[Schema.PartnerCost]] = None,
	  /** Required. The budget spending speed setting of the insertion order. pacing_type `PACING_TYPE_ASAP` is not compatible with pacing_period `PACING_PERIOD_FLIGHT`. */
		pacing: Option[Schema.Pacing] = None,
	  /** Required. The frequency capping setting of the insertion order. */
		frequencyCap: Option[Schema.FrequencyCap] = None,
	  /** Additional integration details of the insertion order. */
		integrationDetails: Option[Schema.IntegrationDetails] = None,
	  /** Required. The key performance indicator (KPI) of the insertion order. This is represented as referred to as the "Goal" in the Display & Video 360 interface. */
		kpi: Option[Schema.Kpi] = None,
	  /** Required. The budget allocation settings of the insertion order. */
		budget: Option[Schema.InsertionOrderBudget] = None,
	  /** The bidding strategy of the insertion order. By default, fixed_bid is set. */
		bidStrategy: Option[Schema.BiddingStrategy] = None,
	  /** Output only. The reservation type of the insertion order. */
		reservationType: Option[Schema.InsertionOrder.ReservationTypeEnum] = None,
	  /** Optional. The optimization objective of the insertion order. &#42;&#42;This field is only available to allowlisted customers.&#42;&#42; If a customer is not allowlisted, this field will be null and attempts to set it will return an error. */
		optimizationObjective: Option[Schema.InsertionOrder.OptimizationObjectiveEnum] = None
	)
	
	object PartnerCost {
		enum CostTypeEnum extends Enum[CostTypeEnum] { case PARTNER_COST_TYPE_UNSPECIFIED, PARTNER_COST_TYPE_ADLOOX, PARTNER_COST_TYPE_ADLOOX_PREBID, PARTNER_COST_TYPE_ADSAFE, PARTNER_COST_TYPE_ADXPOSE, PARTNER_COST_TYPE_AGGREGATE_KNOWLEDGE, PARTNER_COST_TYPE_AGENCY_TRADING_DESK, PARTNER_COST_TYPE_DV360_FEE, PARTNER_COST_TYPE_COMSCORE_VCE, PARTNER_COST_TYPE_DATA_MANAGEMENT_PLATFORM, PARTNER_COST_TYPE_DEFAULT, PARTNER_COST_TYPE_DOUBLE_VERIFY, PARTNER_COST_TYPE_DOUBLE_VERIFY_PREBID, PARTNER_COST_TYPE_EVIDON, PARTNER_COST_TYPE_INTEGRAL_AD_SCIENCE_VIDEO, PARTNER_COST_TYPE_INTEGRAL_AD_SCIENCE_PREBID, PARTNER_COST_TYPE_MEDIA_COST_DATA, PARTNER_COST_TYPE_MOAT_VIDEO, PARTNER_COST_TYPE_NIELSEN_DAR, PARTNER_COST_TYPE_SHOP_LOCAL, PARTNER_COST_TYPE_TERACENT, PARTNER_COST_TYPE_THIRD_PARTY_AD_SERVER, PARTNER_COST_TYPE_TRUST_METRICS, PARTNER_COST_TYPE_VIZU, PARTNER_COST_TYPE_ADLINGO_FEE, PARTNER_COST_TYPE_CUSTOM_FEE_1, PARTNER_COST_TYPE_CUSTOM_FEE_2, PARTNER_COST_TYPE_CUSTOM_FEE_3, PARTNER_COST_TYPE_CUSTOM_FEE_4, PARTNER_COST_TYPE_CUSTOM_FEE_5, PARTNER_COST_TYPE_SCIBIDS_FEE }
		enum FeeTypeEnum extends Enum[FeeTypeEnum] { case PARTNER_COST_FEE_TYPE_UNSPECIFIED, PARTNER_COST_FEE_TYPE_CPM_FEE, PARTNER_COST_FEE_TYPE_MEDIA_FEE }
		enum InvoiceTypeEnum extends Enum[InvoiceTypeEnum] { case PARTNER_COST_INVOICE_TYPE_UNSPECIFIED, PARTNER_COST_INVOICE_TYPE_DV360, PARTNER_COST_INVOICE_TYPE_PARTNER }
	}
	case class PartnerCost(
	  /** Required. The type of the partner cost. */
		costType: Option[Schema.PartnerCost.CostTypeEnum] = None,
	  /** Required. The fee type for this partner cost. */
		feeType: Option[Schema.PartnerCost.FeeTypeEnum] = None,
	  /** The CPM fee amount in micros of advertiser's currency. Applicable when the fee_type is `PARTNER_FEE_TYPE_CPM_FEE`. Must be greater than or equal to 0. For example, for 1.5 standard unit of the advertiser's currency, set this field to 1500000. */
		feeAmount: Option[String] = None,
	  /** The media fee percentage in millis (1/1000 of a percent). Applicable when the fee_type is `PARTNER_FEE_TYPE_MEDIA_FEE`. Must be greater than or equal to 0. For example: 100 represents 0.1%. */
		feePercentageMillis: Option[String] = None,
	  /** The invoice type for this partner cost. &#42; Required when cost_type is one of: - `PARTNER_COST_TYPE_ADLOOX` - `PARTNER_COST_TYPE_DOUBLE_VERIFY` - `PARTNER_COST_TYPE_INTEGRAL_AD_SCIENCE`. &#42; Output only for other types. */
		invoiceType: Option[Schema.PartnerCost.InvoiceTypeEnum] = None
	)
	
	object Pacing {
		enum PacingPeriodEnum extends Enum[PacingPeriodEnum] { case PACING_PERIOD_UNSPECIFIED, PACING_PERIOD_DAILY, PACING_PERIOD_FLIGHT }
		enum PacingTypeEnum extends Enum[PacingTypeEnum] { case PACING_TYPE_UNSPECIFIED, PACING_TYPE_AHEAD, PACING_TYPE_ASAP, PACING_TYPE_EVEN }
	}
	case class Pacing(
	  /** Required. The time period in which the pacing budget will be spent. When automatic budget allocation is enabled at the insertion order via automationType, this field is output only and defaults to `PACING_PERIOD_FLIGHT`. */
		pacingPeriod: Option[Schema.Pacing.PacingPeriodEnum] = None,
	  /** Required. The type of pacing that defines how the budget amount will be spent across the pacing_period. `PACING_TYPE_ASAP` is not compatible with pacing_period `PACING_PERIOD_FLIGHT` for insertion orders. */
		pacingType: Option[Schema.Pacing.PacingTypeEnum] = None,
	  /** Maximum currency amount to spend every day in micros of advertiser's currency. Applicable when the budget is currency based. Must be greater than 0. For example, for 1.5 standard unit of the currency, set this field to 1500000. The value assigned will be rounded to whole billable units for the relevant currency by the following rules: any positive value less than a single billable unit will be rounded up to one billable unit and any value larger than a single billable unit will be rounded down to the nearest billable value. For example, if the currency's billable unit is 0.01, and this field is set to 10257770, it will round down to 10250000, a value of 10.25. If set to 505, it will round up to 10000, a value of 0.01. */
		dailyMaxMicros: Option[String] = None,
	  /** Maximum number of impressions to serve every day. Applicable when the budget is impression based. Must be greater than 0. */
		dailyMaxImpressions: Option[String] = None
	)
	
	object Kpi {
		enum KpiTypeEnum extends Enum[KpiTypeEnum] { case KPI_TYPE_UNSPECIFIED, KPI_TYPE_CPM, KPI_TYPE_CPC, KPI_TYPE_CPA, KPI_TYPE_CTR, KPI_TYPE_VIEWABILITY, KPI_TYPE_CPIAVC, KPI_TYPE_CPE, KPI_TYPE_CPV, KPI_TYPE_CLICK_CVR, KPI_TYPE_IMPRESSION_CVR, KPI_TYPE_VCPM, KPI_TYPE_VTR, KPI_TYPE_AUDIO_COMPLETION_RATE, KPI_TYPE_VIDEO_COMPLETION_RATE, KPI_TYPE_CPCL, KPI_TYPE_CPCV, KPI_TYPE_TOS10, KPI_TYPE_MAXIMIZE_PACING, KPI_TYPE_CUSTOM_IMPRESSION_VALUE_OVER_COST, KPI_TYPE_OTHER }
	}
	case class Kpi(
	  /** Required. The type of KPI. */
		kpiType: Option[Schema.Kpi.KpiTypeEnum] = None,
	  /** The goal amount, in micros of the advertiser's currency. Applicable when kpi_type is one of: &#42; `KPI_TYPE_CPM` &#42; `KPI_TYPE_CPC` &#42; `KPI_TYPE_CPA` &#42; `KPI_TYPE_CPIAVC` &#42; `KPI_TYPE_VCPM` For example: 1500000 represents 1.5 standard units of the currency. */
		kpiAmountMicros: Option[String] = None,
	  /** The decimal representation of the goal percentage in micros. Applicable when kpi_type is one of: &#42; `KPI_TYPE_CTR` &#42; `KPI_TYPE_VIEWABILITY` &#42; `KPI_TYPE_CLICK_CVR` &#42; `KPI_TYPE_IMPRESSION_CVR` &#42; `KPI_TYPE_VTR` &#42; `KPI_TYPE_AUDIO_COMPLETION_RATE` &#42; `KPI_TYPE_VIDEO_COMPLETION_RATE` For example: 70000 represents 7% (decimal 0.07). */
		kpiPercentageMicros: Option[String] = None,
	  /** A KPI string, which can be empty. Must be UTF-8 encoded with a length of no more than 100 characters. Applicable when kpi_type is `KPI_TYPE_OTHER`. */
		kpiString: Option[String] = None,
	  /** Optional. Custom Bidding Algorithm ID associated with KPI_CUSTOM_IMPRESSION_VALUE_OVER_COST. This field is ignored if the proper KPI is not selected. */
		kpiAlgorithmId: Option[String] = None
	)
	
	object InsertionOrderBudget {
		enum BudgetUnitEnum extends Enum[BudgetUnitEnum] { case BUDGET_UNIT_UNSPECIFIED, BUDGET_UNIT_CURRENCY, BUDGET_UNIT_IMPRESSIONS }
		enum AutomationTypeEnum extends Enum[AutomationTypeEnum] { case INSERTION_ORDER_AUTOMATION_TYPE_UNSPECIFIED, INSERTION_ORDER_AUTOMATION_TYPE_BUDGET, INSERTION_ORDER_AUTOMATION_TYPE_NONE, INSERTION_ORDER_AUTOMATION_TYPE_BID_BUDGET }
	}
	case class InsertionOrderBudget(
	  /** Required. Immutable. The budget unit specifies whether the budget is currency based or impression based. */
		budgetUnit: Option[Schema.InsertionOrderBudget.BudgetUnitEnum] = None,
	  /** The type of automation used to manage bid and budget for the insertion order. If this field is unspecified in creation, the value defaults to `INSERTION_ORDER_AUTOMATION_TYPE_NONE`. */
		automationType: Option[Schema.InsertionOrderBudget.AutomationTypeEnum] = None,
	  /** Required. The list of budget segments. Use a budget segment to specify a specific budget for a given period of time an insertion order is running. */
		budgetSegments: Option[List[Schema.InsertionOrderBudgetSegment]] = None
	)
	
	case class InsertionOrderBudgetSegment(
	  /** Required. The budget amount the insertion order will spend for the given date_range. The amount is in micros. Must be greater than 0. For example, 500000000 represents 500 standard units of the currency. */
		budgetAmountMicros: Option[String] = None,
	  /** The budget segment description. It can be used to enter Purchase Order information for each budget segment and have that information printed on the invoices. Must be UTF-8 encoded. */
		description: Option[String] = None,
	  /** Required. The start and end date settings of the budget segment. They are resolved relative to the parent advertiser's time zone. &#42; When creating a new budget segment, both `start_date` and `end_date` must be in the future. &#42; An existing budget segment with a `start_date` in the past has a mutable `end_date` but an immutable `start_date`. &#42; `end_date` must be the `start_date` or later, both before the year 2037. */
		dateRange: Option[Schema.DateRange] = None,
	  /** The budget_id of the campaign budget that this insertion order budget segment is a part of. */
		campaignBudgetId: Option[String] = None
	)
	
	case class ListInsertionOrdersResponse(
	  /** The list of insertion orders. This list will be absent if empty. */
		insertionOrders: Option[List[Schema.InsertionOrder]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListInsertionOrders` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class InventorySourceGroup(
	  /** Output only. The resource name of the inventory source group. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the inventory source group. Assigned by the system. */
		inventorySourceGroupId: Option[String] = None,
	  /** Required. The display name of the inventory source group. Must be UTF-8 encoded with a maximum size of 240 bytes. */
		displayName: Option[String] = None
	)
	
	case class ListInventorySourceGroupsResponse(
	  /** The list of inventory source groups. This list will be absent if empty. */
		inventorySourceGroups: Option[List[Schema.InventorySourceGroup]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListInventorySourceGroups` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object InventorySource {
		enum InventorySourceTypeEnum extends Enum[InventorySourceTypeEnum] { case INVENTORY_SOURCE_TYPE_UNSPECIFIED, INVENTORY_SOURCE_TYPE_PRIVATE, INVENTORY_SOURCE_TYPE_AUCTION_PACKAGE }
		enum InventorySourceProductTypeEnum extends Enum[InventorySourceProductTypeEnum] { case INVENTORY_SOURCE_PRODUCT_TYPE_UNSPECIFIED, PREFERRED_DEAL, PRIVATE_AUCTION, PROGRAMMATIC_GUARANTEED, TAG_GUARANTEED, YOUTUBE_RESERVE, INSTANT_RESERVE, GUARANTEED_PACKAGE, PROGRAMMATIC_TV, AUCTION_PACKAGE }
		enum CommitmentEnum extends Enum[CommitmentEnum] { case INVENTORY_SOURCE_COMMITMENT_UNSPECIFIED, INVENTORY_SOURCE_COMMITMENT_GUARANTEED, INVENTORY_SOURCE_COMMITMENT_NON_GUARANTEED }
		enum DeliveryMethodEnum extends Enum[DeliveryMethodEnum] { case INVENTORY_SOURCE_DELIVERY_METHOD_UNSPECIFIED, INVENTORY_SOURCE_DELIVERY_METHOD_PROGRAMMATIC, INVENTORY_SOURCE_DELIVERY_METHOD_TAG }
		enum ExchangeEnum extends Enum[ExchangeEnum] { case EXCHANGE_UNSPECIFIED, EXCHANGE_GOOGLE_AD_MANAGER, EXCHANGE_APPNEXUS, EXCHANGE_BRIGHTROLL, EXCHANGE_ADFORM, EXCHANGE_ADMETA, EXCHANGE_ADMIXER, EXCHANGE_ADSMOGO, EXCHANGE_ADSWIZZ, EXCHANGE_BIDSWITCH, EXCHANGE_BRIGHTROLL_DISPLAY, EXCHANGE_CADREON, EXCHANGE_DAILYMOTION, EXCHANGE_FIVE, EXCHANGE_FLUCT, EXCHANGE_FREEWHEEL, EXCHANGE_GENIEE, EXCHANGE_GUMGUM, EXCHANGE_IMOBILE, EXCHANGE_IBILLBOARD, EXCHANGE_IMPROVE_DIGITAL, EXCHANGE_INDEX, EXCHANGE_KARGO, EXCHANGE_MICROAD, EXCHANGE_MOPUB, EXCHANGE_NEND, EXCHANGE_ONE_BY_AOL_DISPLAY, EXCHANGE_ONE_BY_AOL_MOBILE, EXCHANGE_ONE_BY_AOL_VIDEO, EXCHANGE_OOYALA, EXCHANGE_OPENX, EXCHANGE_PERMODO, EXCHANGE_PLATFORMONE, EXCHANGE_PLATFORMID, EXCHANGE_PUBMATIC, EXCHANGE_PULSEPOINT, EXCHANGE_REVENUEMAX, EXCHANGE_RUBICON, EXCHANGE_SMARTCLIP, EXCHANGE_SMARTRTB, EXCHANGE_SMARTSTREAMTV, EXCHANGE_SOVRN, EXCHANGE_SPOTXCHANGE, EXCHANGE_STROER, EXCHANGE_TEADSTV, EXCHANGE_TELARIA, EXCHANGE_TVN, EXCHANGE_UNITED, EXCHANGE_YIELDLAB, EXCHANGE_YIELDMO, EXCHANGE_UNRULYX, EXCHANGE_OPEN8, EXCHANGE_TRITON, EXCHANGE_TRIPLELIFT, EXCHANGE_TABOOLA, EXCHANGE_INMOBI, EXCHANGE_SMAATO, EXCHANGE_AJA, EXCHANGE_SUPERSHIP, EXCHANGE_NEXSTAR_DIGITAL, EXCHANGE_WAZE, EXCHANGE_SOUNDCAST, EXCHANGE_SHARETHROUGH, EXCHANGE_FYBER, EXCHANGE_RED_FOR_PUBLISHERS, EXCHANGE_MEDIANET, EXCHANGE_TAPJOY, EXCHANGE_VISTAR, EXCHANGE_DAX, EXCHANGE_JCD, EXCHANGE_PLACE_EXCHANGE, EXCHANGE_APPLOVIN, EXCHANGE_CONNATIX, EXCHANGE_RESET_DIGITAL, EXCHANGE_HIVESTACK, EXCHANGE_DRAX, EXCHANGE_APPLOVIN_GBID, EXCHANGE_FYBER_GBID, EXCHANGE_UNITY_GBID, EXCHANGE_CHARTBOOST_GBID, EXCHANGE_ADMOST_GBID, EXCHANGE_TOPON_GBID, EXCHANGE_NETFLIX, EXCHANGE_TUBI }
	}
	case class InventorySource(
	  /** Output only. The resource name of the inventory source. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the inventory source. Assigned by the system. */
		inventorySourceId: Option[String] = None,
	  /** The display name of the inventory source. Must be UTF-8 encoded with a maximum size of 240 bytes. */
		displayName: Option[String] = None,
	  /** Denotes the type of the inventory source. */
		inventorySourceType: Option[Schema.InventorySource.InventorySourceTypeEnum] = None,
	  /** Output only. The product type of the inventory source, denoting the way through which it sells inventory. */
		inventorySourceProductType: Option[Schema.InventorySource.InventorySourceProductTypeEnum] = None,
	  /** Whether the inventory source has a guaranteed or non-guaranteed delivery. */
		commitment: Option[Schema.InventorySource.CommitmentEnum] = None,
	  /** The delivery method of the inventory source. &#42; For non-guaranteed inventory sources, the only acceptable value is `INVENTORY_SOURCE_DELIVERY_METHOD_PROGRAMMATIC`. &#42; For guaranteed inventory sources, acceptable values are `INVENTORY_SOURCE_DELIVERY_METHOD_TAG` and `INVENTORY_SOURCE_DELIVERY_METHOD_PROGRAMMATIC`. */
		deliveryMethod: Option[Schema.InventorySource.DeliveryMethodEnum] = None,
	  /** The ID in the exchange space that uniquely identifies the inventory source. Must be unique across buyers within each exchange but not necessarily unique across exchanges. */
		dealId: Option[String] = None,
	  /** The status settings of the inventory source. */
		status: Option[Schema.InventorySourceStatus] = None,
	  /** The exchange to which the inventory source belongs. */
		exchange: Option[Schema.InventorySource.ExchangeEnum] = None,
	  /** Output only. The timestamp when the inventory source was last updated. Assigned by the system. */
		updateTime: Option[String] = None,
	  /** Required. The rate details of the inventory source. */
		rateDetails: Option[Schema.RateDetails] = None,
	  /** The publisher/seller name of the inventory source. */
		publisherName: Option[String] = None,
	  /** The time range when this inventory source starts and stops serving. */
		timeRange: Option[Schema.TimeRange] = None,
	  /** The creative requirements of the inventory source. Not applicable for auction packages. */
		creativeConfigs: Option[List[Schema.CreativeConfig]] = None,
	  /** Immutable. The ID of the guaranteed order that this inventory source belongs to. Only applicable when commitment is `INVENTORY_SOURCE_COMMITMENT_GUARANTEED`. */
		guaranteedOrderId: Option[String] = None,
	  /** The partner or advertisers that have read/write access to the inventory source. Output only when commitment is `INVENTORY_SOURCE_COMMITMENT_GUARANTEED`, in which case the read/write accessors are inherited from the parent guaranteed order. Required when commitment is `INVENTORY_SOURCE_COMMITMENT_NON_GUARANTEED`. If commitment is `INVENTORY_SOURCE_COMMITMENT_NON_GUARANTEED` and a partner is set in this field, all advertisers under this partner will automatically have read-only access to the inventory source. These advertisers will not be included in read_advertiser_ids. */
		readWriteAccessors: Option[Schema.InventorySourceAccessors] = None,
	  /** Output only. The IDs of advertisers with read-only access to the inventory source. */
		readAdvertiserIds: Option[List[String]] = None,
	  /** Output only. The IDs of partners with read-only access to the inventory source. All advertisers of partners in this field inherit read-only access to the inventory source. */
		readPartnerIds: Option[List[String]] = None
	)
	
	object InventorySourceStatus {
		enum EntityStatusEnum extends Enum[EntityStatusEnum] { case ENTITY_STATUS_UNSPECIFIED, ENTITY_STATUS_ACTIVE, ENTITY_STATUS_ARCHIVED, ENTITY_STATUS_DRAFT, ENTITY_STATUS_PAUSED, ENTITY_STATUS_SCHEDULED_FOR_DELETION }
		enum SellerStatusEnum extends Enum[SellerStatusEnum] { case ENTITY_STATUS_UNSPECIFIED, ENTITY_STATUS_ACTIVE, ENTITY_STATUS_ARCHIVED, ENTITY_STATUS_DRAFT, ENTITY_STATUS_PAUSED, ENTITY_STATUS_SCHEDULED_FOR_DELETION }
		enum ConfigStatusEnum extends Enum[ConfigStatusEnum] { case INVENTORY_SOURCE_CONFIG_STATUS_UNSPECIFIED, INVENTORY_SOURCE_CONFIG_STATUS_PENDING, INVENTORY_SOURCE_CONFIG_STATUS_COMPLETED }
	}
	case class InventorySourceStatus(
	  /** Whether or not the inventory source is servable. Acceptable values are `ENTITY_STATUS_ACTIVE`, `ENTITY_STATUS_ARCHIVED`, and `ENTITY_STATUS_PAUSED`. Default value is `ENTITY_STATUS_ACTIVE`. */
		entityStatus: Option[Schema.InventorySourceStatus.EntityStatusEnum] = None,
	  /** The user-provided reason for pausing this inventory source. Must not exceed 100 characters. Only applicable when entity_status is set to `ENTITY_STATUS_PAUSED`. */
		entityPauseReason: Option[String] = None,
	  /** Output only. The status set by the seller for the inventory source. Only applicable for inventory sources synced directly from the publishers. Acceptable values are `ENTITY_STATUS_ACTIVE` and `ENTITY_STATUS_PAUSED`. */
		sellerStatus: Option[Schema.InventorySourceStatus.SellerStatusEnum] = None,
	  /** Output only. The seller-provided reason for pausing this inventory source. Only applicable for inventory sources synced directly from the publishers and when seller_status is set to `ENTITY_STATUS_PAUSED`. */
		sellerPauseReason: Option[String] = None,
	  /** Output only. The configuration status of the inventory source. Only applicable for guaranteed inventory sources. Acceptable values are `INVENTORY_SOURCE_CONFIG_STATUS_PENDING` and `INVENTORY_SOURCE_CONFIG_STATUS_COMPLETED`. An inventory source must be configured (fill in the required fields, choose creatives, and select a default campaign) before it can serve. */
		configStatus: Option[Schema.InventorySourceStatus.ConfigStatusEnum] = None
	)
	
	object RateDetails {
		enum InventorySourceRateTypeEnum extends Enum[InventorySourceRateTypeEnum] { case INVENTORY_SOURCE_RATE_TYPE_UNSPECIFIED, INVENTORY_SOURCE_RATE_TYPE_CPM_FIXED, INVENTORY_SOURCE_RATE_TYPE_CPM_FLOOR, INVENTORY_SOURCE_RATE_TYPE_CPD, INVENTORY_SOURCE_RATE_TYPE_FLAT }
	}
	case class RateDetails(
	  /** The rate type. Acceptable values are `INVENTORY_SOURCE_RATE_TYPE_CPM_FIXED`, `INVENTORY_SOURCE_RATE_TYPE_CPM_FLOOR`, and `INVENTORY_SOURCE_RATE_TYPE_CPD`. */
		inventorySourceRateType: Option[Schema.RateDetails.InventorySourceRateTypeEnum] = None,
	  /** The rate for the inventory source. */
		rate: Option[Schema.Money] = None,
	  /** Required for guaranteed inventory sources. The number of impressions guaranteed by the seller. */
		unitsPurchased: Option[String] = None,
	  /** Output only. The amount that the buyer has committed to spending on the inventory source up front. Only applicable for guaranteed inventory sources. */
		minimumSpend: Option[Schema.Money] = None
	)
	
	case class Money(
	  /** The three-letter currency code defined in ISO 4217. */
		currencyCode: Option[String] = None,
	  /** The whole units of the amount. For example if `currencyCode` is `"USD"`, then 1 unit is one US dollar. */
		units: Option[String] = None,
	  /** Number of nano (10^-9) units of the amount. The value must be between -999,999,999 and +999,999,999 inclusive. If `units` is positive, `nanos` must be positive or zero. If `units` is zero, `nanos` can be positive, zero, or negative. If `units` is negative, `nanos` must be negative or zero. For example $-1.75 is represented as `units`=-1 and `nanos`=-750,000,000. */
		nanos: Option[Int] = None
	)
	
	case class TimeRange(
	  /** Required. The lower bound of a time range, inclusive. */
		startTime: Option[String] = None,
	  /** Required. The upper bound of a time range, inclusive. */
		endTime: Option[String] = None
	)
	
	object CreativeConfig {
		enum CreativeTypeEnum extends Enum[CreativeTypeEnum] { case CREATIVE_TYPE_UNSPECIFIED, CREATIVE_TYPE_STANDARD, CREATIVE_TYPE_EXPANDABLE, CREATIVE_TYPE_VIDEO, CREATIVE_TYPE_NATIVE, CREATIVE_TYPE_TEMPLATED_APP_INSTALL, CREATIVE_TYPE_NATIVE_SITE_SQUARE, CREATIVE_TYPE_TEMPLATED_APP_INSTALL_INTERSTITIAL, CREATIVE_TYPE_LIGHTBOX, CREATIVE_TYPE_NATIVE_APP_INSTALL, CREATIVE_TYPE_NATIVE_APP_INSTALL_SQUARE, CREATIVE_TYPE_AUDIO, CREATIVE_TYPE_PUBLISHER_HOSTED, CREATIVE_TYPE_NATIVE_VIDEO, CREATIVE_TYPE_TEMPLATED_APP_INSTALL_VIDEO }
	}
	case class CreativeConfig(
	  /** The type of creative that can be assigned to the inventory source. Only the following types are supported: &#42; `CREATIVE_TYPE_STANDARD` &#42; `CREATIVE_TYPE_VIDEO` */
		creativeType: Option[Schema.CreativeConfig.CreativeTypeEnum] = None,
	  /** The configuration for display creatives. Applicable when creative_type is `CREATIVE_TYPE_STANDARD`. */
		displayCreativeConfig: Option[Schema.InventorySourceDisplayCreativeConfig] = None,
	  /** The configuration for video creatives. Applicable when creative_type is `CREATIVE_TYPE_VIDEO`. */
		videoCreativeConfig: Option[Schema.InventorySourceVideoCreativeConfig] = None
	)
	
	case class InventorySourceDisplayCreativeConfig(
	  /** The size requirements for display creatives that can be assigned to the inventory source. */
		creativeSize: Option[Schema.Dimensions] = None
	)
	
	case class InventorySourceVideoCreativeConfig(
	  /** The duration requirements for the video creatives that can be assigned to the inventory source. */
		duration: Option[String] = None
	)
	
	case class InventorySourceAccessors(
	  /** The partner with access to the inventory source. */
		partner: Option[Schema.InventorySourceAccessorsPartnerAccessor] = None,
	  /** The advertisers with access to the inventory source. All advertisers must belong to the same partner. */
		advertisers: Option[Schema.InventorySourceAccessorsAdvertiserAccessors] = None
	)
	
	case class InventorySourceAccessorsPartnerAccessor(
	  /** The ID of the partner. */
		partnerId: Option[String] = None
	)
	
	case class InventorySourceAccessorsAdvertiserAccessors(
	  /** The IDs of the advertisers. */
		advertiserIds: Option[List[String]] = None
	)
	
	case class ListInventorySourcesResponse(
	  /** The list of inventory sources. This list will be absent if empty. */
		inventorySources: Option[List[Schema.InventorySource]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListInventorySources` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class EditInventorySourceReadWriteAccessorsRequest(
	  /** Required. The partner context by which the accessors change is being made. */
		partnerId: Option[String] = None,
	  /** Set the partner context as read/write accessor of the inventory source. This will remove all other current read/write advertiser accessors. */
		assignPartner: Option[Boolean] = None,
	  /** The advertisers to add or remove from the list of advertisers that have read/write access to the inventory source. This change will remove an existing partner read/write accessor. */
		advertisersUpdate: Option[Schema.EditInventorySourceReadWriteAccessorsRequestAdvertisersUpdate] = None
	)
	
	case class EditInventorySourceReadWriteAccessorsRequestAdvertisersUpdate(
	  /** The advertisers to add. */
		addedAdvertisers: Option[List[String]] = None,
	  /** The advertisers to remove. */
		removedAdvertisers: Option[List[String]] = None
	)
	
	case class ListInvoicesResponse(
	  /** The list of invoices. This list will be absent if empty. */
		invoices: Option[List[Schema.Invoice]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListInvoices` method to retrieve the next page of results. This token will be absent if there are no more invoices to return. */
		nextPageToken: Option[String] = None
	)
	
	object Invoice {
		enum InvoiceTypeEnum extends Enum[InvoiceTypeEnum] { case INVOICE_TYPE_UNSPECIFIED, INVOICE_TYPE_CREDIT, INVOICE_TYPE_INVOICE }
	}
	case class Invoice(
	  /** The resource name of the invoice. */
		name: Option[String] = None,
	  /** The unique ID of the invoice. */
		invoiceId: Option[String] = None,
	  /** The display name of the invoice. */
		displayName: Option[String] = None,
	  /** The ID of the payments profile the invoice belongs to. Appears on the invoice PDF as `Billing ID`. */
		paymentsProfileId: Option[String] = None,
	  /** The ID of the payments account the invoice belongs to. Appears on the invoice PDF as `Billing Account Number`. */
		paymentsAccountId: Option[String] = None,
	  /** The date when the invoice was issued. */
		issueDate: Option[Schema.Date] = None,
	  /** The date when the invoice is due. */
		dueDate: Option[Schema.Date] = None,
	  /** The service start and end dates which are covered by this invoice. */
		serviceDateRange: Option[Schema.DateRange] = None,
	  /** The currency used in the invoice in ISO 4217 format. */
		currencyCode: Option[String] = None,
	  /** The pre-tax subtotal amount, in micros of the invoice's currency. For example, if currency_code is `USD`, then 1000000 represents one US dollar. */
		subtotalAmountMicros: Option[String] = None,
	  /** The sum of all taxes in invoice, in micros of the invoice's currency. For example, if currency_code is `USD`, then 1000000 represents one US dollar. */
		totalTaxAmountMicros: Option[String] = None,
	  /** The invoice total amount, in micros of the invoice's currency. For example, if currency_code is `USD`, then 1000000 represents one US dollar. */
		totalAmountMicros: Option[String] = None,
	  /** Purchase order number associated with the invoice. */
		purchaseOrderNumber: Option[String] = None,
	  /** The budget grouping ID for this invoice. This field will only be set if the invoice level of the corresponding billing profile was set to "Budget invoice grouping ID". */
		budgetInvoiceGroupingId: Option[String] = None,
	  /** The type of invoice document. */
		invoiceType: Option[Schema.Invoice.InvoiceTypeEnum] = None,
	  /** The ID of the original invoice being adjusted by this invoice, if applicable. May appear on the invoice PDF as `Reference invoice number`. If replaced_invoice_ids is set, this field will be empty. */
		correctedInvoiceId: Option[String] = None,
	  /** The ID(s) of any originally issued invoice that is being cancelled by this invoice, if applicable. Multiple invoices may be listed if those invoices are being consolidated into a single invoice. May appear on invoice PDF as `Replaced invoice numbers`. If corrected_invoice_id is set, this field will be empty. */
		replacedInvoiceIds: Option[List[String]] = None,
	  /** The URL to download a PDF copy of the invoice. This URL is user specific and requires a valid OAuth 2.0 access token to access. The access token must be provided in an `Authorization: Bearer` HTTP header and be authorized for one of the following scopes: &#42; `https://www.googleapis.com/auth/display-video-mediaplanning` &#42; `https://www.googleapis.com/auth/display-video` The URL will be valid for 7 days after retrieval of this invoice object or until this invoice is retrieved again. */
		pdfUrl: Option[String] = None,
	  /** The list of summarized information for each budget associated with this invoice. This field will only be set if the invoice detail level of the corresponding billing profile was set to "Budget level PO". */
		budgetSummaries: Option[List[Schema.BudgetSummary]] = None,
	  /** The total amount of costs or adjustments not tied to a particular budget, in micros of the invoice's currency. For example, if currency_code is `USD`, then 1000000 represents one US dollar. */
		nonBudgetMicros: Option[String] = None
	)
	
	case class BudgetSummary(
	  /** Corresponds to the external_budget_id of a campaign budget. If the value is not set in the campaign budget, this field will be empty. */
		externalBudgetId: Option[String] = None,
	  /** Relevant client, product, and estimate codes from the Mediaocean Prisma tool. Only applicable for campaign budgets with an external_budget_source of EXTERNAL_BUDGET_SOURCE_MEDIA_OCEAN. */
		prismaCpeCode: Option[Schema.PrismaCpeCode] = None,
	  /** The sum of charges made under this budget before taxes, in micros of the invoice's currency. For example, if currency_code is `USD`, then 1000000 represents one US dollar. */
		preTaxAmountMicros: Option[String] = None,
	  /** The amount of tax applied to charges under this budget, in micros of the invoice's currency. For example, if currency_code is `USD`, then 1000000 represents one US dollar. */
		taxAmountMicros: Option[String] = None,
	  /** The total sum of charges made under this budget, including tax, in micros of the invoice's currency. For example, if currency_code is `USD`, then 1000000 represents one US dollar. */
		totalAmountMicros: Option[String] = None
	)
	
	case class LookupInvoiceCurrencyResponse(
	  /** Currency used by the advertiser in ISO 4217 format. */
		currencyCode: Option[String] = None
	)
	
	object LineItem {
		enum LineItemTypeEnum extends Enum[LineItemTypeEnum] { case LINE_ITEM_TYPE_UNSPECIFIED, LINE_ITEM_TYPE_DISPLAY_DEFAULT, LINE_ITEM_TYPE_DISPLAY_MOBILE_APP_INSTALL, LINE_ITEM_TYPE_VIDEO_DEFAULT, LINE_ITEM_TYPE_VIDEO_MOBILE_APP_INSTALL, LINE_ITEM_TYPE_DISPLAY_MOBILE_APP_INVENTORY, LINE_ITEM_TYPE_VIDEO_MOBILE_APP_INVENTORY, LINE_ITEM_TYPE_AUDIO_DEFAULT, LINE_ITEM_TYPE_VIDEO_OVER_THE_TOP, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_ACTION, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_NON_SKIPPABLE, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_VIDEO_SEQUENCE, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_AUDIO, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_REACH, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_SIMPLE, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_NON_SKIPPABLE_OVER_THE_TOP, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_REACH_OVER_THE_TOP, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_SIMPLE_OVER_THE_TOP, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_TARGET_FREQUENCY, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_VIEW, LINE_ITEM_TYPE_DISPLAY_OUT_OF_HOME, LINE_ITEM_TYPE_VIDEO_OUT_OF_HOME }
		enum EntityStatusEnum extends Enum[EntityStatusEnum] { case ENTITY_STATUS_UNSPECIFIED, ENTITY_STATUS_ACTIVE, ENTITY_STATUS_ARCHIVED, ENTITY_STATUS_DRAFT, ENTITY_STATUS_PAUSED, ENTITY_STATUS_SCHEDULED_FOR_DELETION }
		enum WarningMessagesEnum extends Enum[WarningMessagesEnum] { case LINE_ITEM_WARNING_MESSAGE_UNSPECIFIED, INVALID_FLIGHT_DATES, EXPIRED, PENDING_FLIGHT, ALL_PARTNER_ENABLED_EXCHANGES_NEGATIVELY_TARGETED, INVALID_INVENTORY_SOURCE, APP_INVENTORY_INVALID_SITE_TARGETING, APP_INVENTORY_INVALID_AUDIENCE_LISTS, NO_VALID_CREATIVE, PARENT_INSERTION_ORDER_PAUSED, PARENT_INSERTION_ORDER_EXPIRED }
		enum ReservationTypeEnum extends Enum[ReservationTypeEnum] { case RESERVATION_TYPE_UNSPECIFIED, RESERVATION_TYPE_NOT_GUARANTEED, RESERVATION_TYPE_PROGRAMMATIC_GUARANTEED, RESERVATION_TYPE_TAG_GUARANTEED, RESERVATION_TYPE_PETRA_VIRAL, RESERVATION_TYPE_INSTANT_RESERVE }
	}
	case class LineItem(
	  /** Output only. The resource name of the line item. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the advertiser the line item belongs to. */
		advertiserId: Option[String] = None,
	  /** Output only. The unique ID of the campaign that the line item belongs to. */
		campaignId: Option[String] = None,
	  /** Required. Immutable. The unique ID of the insertion order that the line item belongs to. */
		insertionOrderId: Option[String] = None,
	  /** Output only. The unique ID of the line item. Assigned by the system. */
		lineItemId: Option[String] = None,
	  /** Required. The display name of the line item. Must be UTF-8 encoded with a maximum size of 240 bytes. */
		displayName: Option[String] = None,
	  /** Required. Immutable. The type of the line item. */
		lineItemType: Option[Schema.LineItem.LineItemTypeEnum] = None,
	  /** Required. Controls whether or not the line item can spend its budget and bid on inventory. &#42; For CreateLineItem method, only `ENTITY_STATUS_DRAFT` is allowed. To activate a line item, use UpdateLineItem method and update the status to `ENTITY_STATUS_ACTIVE` after creation. &#42; A line item cannot be changed back to `ENTITY_STATUS_DRAFT` status from any other status. &#42; If the line item's parent insertion order is not active, the line item can't spend its budget even if its own status is `ENTITY_STATUS_ACTIVE`. */
		entityStatus: Option[Schema.LineItem.EntityStatusEnum] = None,
	  /** Output only. The timestamp when the line item was last updated. Assigned by the system. */
		updateTime: Option[String] = None,
	  /** The partner costs associated with the line item. If absent or empty in CreateLineItem method, the newly created line item will inherit partner costs from its parent insertion order. */
		partnerCosts: Option[List[Schema.PartnerCost]] = None,
	  /** Required. The start and end time of the line item's flight. */
		flight: Option[Schema.LineItemFlight] = None,
	  /** Required. The budget allocation setting of the line item. */
		budget: Option[Schema.LineItemBudget] = None,
	  /** Required. The budget spending speed setting of the line item. */
		pacing: Option[Schema.Pacing] = None,
	  /** Required. The impression frequency cap settings of the line item. The max_impressions field in this settings object must be used if assigning a limited cap. */
		frequencyCap: Option[Schema.FrequencyCap] = None,
	  /** Required. The partner revenue model setting of the line item. */
		partnerRevenueModel: Option[Schema.PartnerRevenueModel] = None,
	  /** The conversion tracking setting of the line item. */
		conversionCounting: Option[Schema.ConversionCountingConfig] = None,
	  /** The IDs of the creatives associated with the line item. */
		creativeIds: Option[List[String]] = None,
	  /** Required. The bidding strategy of the line item. */
		bidStrategy: Option[Schema.BiddingStrategy] = None,
	  /** Integration details of the line item. */
		integrationDetails: Option[Schema.IntegrationDetails] = None,
	  /** The [optimized targeting](//support.google.com/displayvideo/answer/12060859) settings of the line item. This config is only applicable for display, video, or audio line items that use automated bidding and positively target eligible audience lists. */
		targetingExpansion: Option[Schema.TargetingExpansionConfig] = None,
	  /** Output only. The warning messages generated by the line item. These warnings do not block saving the line item, but some may block the line item from running. */
		warningMessages: Option[List[Schema.LineItem.WarningMessagesEnum]] = None,
	  /** The mobile app promoted by the line item. This is applicable only when line_item_type is either `LINE_ITEM_TYPE_DISPLAY_MOBILE_APP_INSTALL` or `LINE_ITEM_TYPE_VIDEO_MOBILE_APP_INSTALL`. */
		mobileApp: Option[Schema.MobileApp] = None,
	  /** Output only. The reservation type of the line item. */
		reservationType: Option[Schema.LineItem.ReservationTypeEnum] = None,
	  /** Whether to exclude new exchanges from automatically being targeted by the line item. This field is false by default. */
		excludeNewExchanges: Option[Boolean] = None,
	  /** Output only. Settings specific to YouTube and Partners line items. */
		youtubeAndPartnersSettings: Option[Schema.YoutubeAndPartnersSettings] = None
	)
	
	object LineItemFlight {
		enum FlightDateTypeEnum extends Enum[FlightDateTypeEnum] { case LINE_ITEM_FLIGHT_DATE_TYPE_UNSPECIFIED, LINE_ITEM_FLIGHT_DATE_TYPE_INHERITED, LINE_ITEM_FLIGHT_DATE_TYPE_CUSTOM }
	}
	case class LineItemFlight(
	  /** Required. The type of the line item's flight dates. */
		flightDateType: Option[Schema.LineItemFlight.FlightDateTypeEnum] = None,
	  /** The flight start and end dates of the line item. They are resolved relative to the parent advertiser's time zone. &#42; Required when flight_date_type is `LINE_ITEM_FLIGHT_DATE_TYPE_CUSTOM`. Output only otherwise. &#42; When creating a new flight, both `start_date` and `end_date` must be in the future. &#42; An existing flight with a `start_date` in the past has a mutable `end_date` but an immutable `start_date`. &#42; `end_date` must be the `start_date` or later, both before the year 2037. */
		dateRange: Option[Schema.DateRange] = None
	)
	
	object LineItemBudget {
		enum BudgetAllocationTypeEnum extends Enum[BudgetAllocationTypeEnum] { case LINE_ITEM_BUDGET_ALLOCATION_TYPE_UNSPECIFIED, LINE_ITEM_BUDGET_ALLOCATION_TYPE_AUTOMATIC, LINE_ITEM_BUDGET_ALLOCATION_TYPE_FIXED, LINE_ITEM_BUDGET_ALLOCATION_TYPE_UNLIMITED }
		enum BudgetUnitEnum extends Enum[BudgetUnitEnum] { case BUDGET_UNIT_UNSPECIFIED, BUDGET_UNIT_CURRENCY, BUDGET_UNIT_IMPRESSIONS }
	}
	case class LineItemBudget(
	  /** Required. The type of the budget allocation. `LINE_ITEM_BUDGET_ALLOCATION_TYPE_AUTOMATIC` is only applicable when automatic budget allocation is enabled for the parent insertion order. */
		budgetAllocationType: Option[Schema.LineItemBudget.BudgetAllocationTypeEnum] = None,
	  /** Output only. The budget unit specifies whether the budget is currency based or impression based. This value is inherited from the parent insertion order. */
		budgetUnit: Option[Schema.LineItemBudget.BudgetUnitEnum] = None,
	  /** The maximum budget amount the line item will spend. Must be greater than 0. When budget_allocation_type is: &#42; `LINE_ITEM_BUDGET_ALLOCATION_TYPE_AUTOMATIC`, this field is immutable and is set by the system. &#42; `LINE_ITEM_BUDGET_ALLOCATION_TYPE_FIXED`, if budget_unit is: - `BUDGET_UNIT_CURRENCY`, this field represents maximum budget amount to spend, in micros of the advertiser's currency. For example, 1500000 represents 1.5 standard units of the currency. - `BUDGET_UNIT_IMPRESSIONS`, this field represents the maximum number of impressions to serve. &#42; `LINE_ITEM_BUDGET_ALLOCATION_TYPE_UNLIMITED`, this field is not applicable and will be ignored by the system. */
		maxAmount: Option[String] = None
	)
	
	object PartnerRevenueModel {
		enum MarkupTypeEnum extends Enum[MarkupTypeEnum] { case PARTNER_REVENUE_MODEL_MARKUP_TYPE_UNSPECIFIED, PARTNER_REVENUE_MODEL_MARKUP_TYPE_CPM, PARTNER_REVENUE_MODEL_MARKUP_TYPE_MEDIA_COST_MARKUP, PARTNER_REVENUE_MODEL_MARKUP_TYPE_TOTAL_MEDIA_COST_MARKUP }
	}
	case class PartnerRevenueModel(
	  /** Required. The markup type of the partner revenue model. */
		markupType: Option[Schema.PartnerRevenueModel.MarkupTypeEnum] = None,
	  /** Required. The markup amount of the partner revenue model. Must be greater than or equal to 0. &#42; When the markup_type is set to be `PARTNER_REVENUE_MODEL_MARKUP_TYPE_CPM`, this field represents the CPM markup in micros of advertiser's currency. For example, 1500000 represents 1.5 standard units of the currency. &#42; When the markup_type is set to be `PARTNER_REVENUE_MODEL_MARKUP_TYPE_MEDIA_COST_MARKUP`, this field represents the media cost percent markup in millis. For example, 100 represents 0.1% (decimal 0.001). &#42; When the markup_type is set to be `PARTNER_REVENUE_MODEL_MARKUP_TYPE_TOTAL_MEDIA_COST_MARKUP`, this field represents the total media cost percent markup in millis. For example, 100 represents 0.1% (decimal 0.001). */
		markupAmount: Option[String] = None
	)
	
	case class ConversionCountingConfig(
	  /** The percentage of post-view conversions to count, in millis (1/1000 of a percent). Must be between 0 and 100000 inclusive. For example, to track 50% of the post-click conversions, set a value of 50000. */
		postViewCountPercentageMillis: Option[String] = None,
	  /** The Floodlight activity configs used to track conversions. The number of conversions counted is the sum of all of the conversions counted by all of the Floodlight activity IDs specified in this field. */
		floodlightActivityConfigs: Option[List[Schema.TrackingFloodlightActivityConfig]] = None
	)
	
	case class TrackingFloodlightActivityConfig(
	  /** Required. The ID of the Floodlight activity. */
		floodlightActivityId: Option[String] = None,
	  /** Required. The number of days after an ad has been clicked in which a conversion may be counted. Must be between 0 and 90 inclusive. */
		postClickLookbackWindowDays: Option[Int] = None,
	  /** Required. The number of days after an ad has been viewed in which a conversion may be counted. Must be between 0 and 90 inclusive. */
		postViewLookbackWindowDays: Option[Int] = None
	)
	
	object MobileApp {
		enum PlatformEnum extends Enum[PlatformEnum] { case PLATFORM_UNSPECIFIED, IOS, ANDROID }
	}
	case class MobileApp(
	  /** Required. The ID of the app provided by the platform store. Android apps are identified by the bundle ID used by Android's Play store, such as `com.google.android.gm`. iOS apps are identified by a nine-digit app ID used by Apple's App store, such as `422689480`. */
		appId: Option[String] = None,
	  /** Output only. The app platform. */
		platform: Option[Schema.MobileApp.PlatformEnum] = None,
	  /** Output only. The app name. */
		displayName: Option[String] = None,
	  /** Output only. The app publisher. */
		publisher: Option[String] = None
	)
	
	object YoutubeAndPartnersSettings {
		enum ContentCategoryEnum extends Enum[ContentCategoryEnum] { case YOUTUBE_AND_PARTNERS_CONTENT_CATEGORY_UNSPECIFIED, YOUTUBE_AND_PARTNERS_CONTENT_CATEGORY_STANDARD, YOUTUBE_AND_PARTNERS_CONTENT_CATEGORY_EXPANDED, YOUTUBE_AND_PARTNERS_CONTENT_CATEGORY_LIMITED }
		enum EffectiveContentCategoryEnum extends Enum[EffectiveContentCategoryEnum] { case YOUTUBE_AND_PARTNERS_CONTENT_CATEGORY_UNSPECIFIED, YOUTUBE_AND_PARTNERS_CONTENT_CATEGORY_STANDARD, YOUTUBE_AND_PARTNERS_CONTENT_CATEGORY_EXPANDED, YOUTUBE_AND_PARTNERS_CONTENT_CATEGORY_LIMITED }
	}
	case class YoutubeAndPartnersSettings(
	  /** The view frequency cap settings of the line item. The max_views field in this settings object must be used if assigning a limited cap. */
		viewFrequencyCap: Option[Schema.FrequencyCap] = None,
	  /** Optional. The third-party measurement configs of the line item. */
		thirdPartyMeasurementConfigs: Option[Schema.ThirdPartyMeasurementConfigs] = None,
	  /** Settings that control what YouTube and Partners inventories the line item will target. */
		inventorySourceSettings: Option[Schema.YoutubeAndPartnersInventorySourceConfig] = None,
	  /** The kind of content on which the YouTube and Partners ads will be shown. */
		contentCategory: Option[Schema.YoutubeAndPartnersSettings.ContentCategoryEnum] = None,
	  /** Output only. The content category which takes effect when serving the line item. When content category is set in both line item and advertiser, the stricter one will take effect when serving the line item. */
		effectiveContentCategory: Option[Schema.YoutubeAndPartnersSettings.EffectiveContentCategoryEnum] = None,
	  /** Optional. The average number of times you want ads from this line item to show to the same person over a certain period of time. */
		targetFrequency: Option[Schema.TargetFrequency] = None,
	  /** Optional. The ID of the merchant which is linked to the line item for product feed. */
		linkedMerchantId: Option[String] = None,
	  /** Optional. The IDs of the videos appear below the primary video ad when the ad is playing in the YouTube app on mobile devices. */
		relatedVideoIds: Option[List[String]] = None,
	  /** Optional. The ID of the form to generate leads. */
		leadFormId: Option[String] = None,
	  /** Optional. The settings related to VideoAdSequence. */
		videoAdSequenceSettings: Option[Schema.VideoAdSequenceSettings] = None
	)
	
	case class ThirdPartyMeasurementConfigs(
	  /** Optional. The third-party vendors measuring viewability. The following third-party vendors are applicable: &#42; `THIRD_PARTY_VENDOR_MOAT` &#42; `THIRD_PARTY_VENDOR_DOUBLE_VERIFY` &#42; `THIRD_PARTY_VENDOR_INTEGRAL_AD_SCIENCE` &#42; `THIRD_PARTY_VENDOR_COMSCORE` &#42; `THIRD_PARTY_VENDOR_TELEMETRY` &#42; `THIRD_PARTY_VENDOR_MEETRICS` */
		viewabilityVendorConfigs: Option[List[Schema.ThirdPartyVendorConfig]] = None,
	  /** Optional. The third-party vendors measuring brand safety. The following third-party vendors are applicable: &#42; `THIRD_PARTY_VENDOR_ZERF` &#42; `THIRD_PARTY_VENDOR_DOUBLE_VERIFY` &#42; `THIRD_PARTY_VENDOR_INTEGRAL_AD_SCIENCE` */
		brandSafetyVendorConfigs: Option[List[Schema.ThirdPartyVendorConfig]] = None,
	  /** Optional. The third-party vendors measuring reach. The following third-party vendors are applicable: &#42; `THIRD_PARTY_VENDOR_NIELSEN` &#42; `THIRD_PARTY_VENDOR_COMSCORE` &#42; `THIRD_PARTY_VENDOR_KANTAR` */
		reachVendorConfigs: Option[List[Schema.ThirdPartyVendorConfig]] = None,
	  /** Optional. The third-party vendors measuring brand lift. The following third-party vendors are applicable: &#42; `THIRD_PARTY_VENDOR_DYNATA` &#42; `THIRD_PARTY_VENDOR_KANTAR` */
		brandLiftVendorConfigs: Option[List[Schema.ThirdPartyVendorConfig]] = None
	)
	
	object ThirdPartyVendorConfig {
		enum VendorEnum extends Enum[VendorEnum] { case THIRD_PARTY_VENDOR_UNSPECIFIED, THIRD_PARTY_VENDOR_MOAT, THIRD_PARTY_VENDOR_DOUBLE_VERIFY, THIRD_PARTY_VENDOR_INTEGRAL_AD_SCIENCE, THIRD_PARTY_VENDOR_COMSCORE, THIRD_PARTY_VENDOR_TELEMETRY, THIRD_PARTY_VENDOR_MEETRICS, THIRD_PARTY_VENDOR_ZEFR, THIRD_PARTY_VENDOR_NIELSEN, THIRD_PARTY_VENDOR_KANTAR, THIRD_PARTY_VENDOR_DYNATA }
	}
	case class ThirdPartyVendorConfig(
	  /** The third-party measurement vendor. */
		vendor: Option[Schema.ThirdPartyVendorConfig.VendorEnum] = None,
	  /** The ID used by the platform of the third-party vendor to identify the line item. */
		placementId: Option[String] = None
	)
	
	case class YoutubeAndPartnersInventorySourceConfig(
	  /** Optional. Whether to target inventory on YouTube. This includes both search, channels and videos. */
		includeYoutube: Option[Boolean] = None,
	  /** Optional. Whether to target inventory in video apps available with Google TV. */
		includeGoogleTv: Option[Boolean] = None,
	  /** Whether to target inventory on a collection of partner sites and apps that follow the same brand safety standards as YouTube. */
		includeYoutubeVideoPartners: Option[Boolean] = None
	)
	
	object TargetFrequency {
		enum TimeUnitEnum extends Enum[TimeUnitEnum] { case TIME_UNIT_UNSPECIFIED, TIME_UNIT_LIFETIME, TIME_UNIT_MONTHS, TIME_UNIT_WEEKS, TIME_UNIT_DAYS, TIME_UNIT_HOURS, TIME_UNIT_MINUTES }
	}
	case class TargetFrequency(
	  /** The target number of times, on average, the ads will be shown to the same person in the timespan dictated by time_unit and time_unit_count. */
		targetCount: Option[String] = None,
	  /** The unit of time in which the target frequency will be applied. The following time unit is applicable: &#42; `TIME_UNIT_WEEKS` */
		timeUnit: Option[Schema.TargetFrequency.TimeUnitEnum] = None,
	  /** The number of time_unit the target frequency will last. The following restrictions apply based on the value of time_unit: &#42; `TIME_UNIT_WEEKS` - must be 1 */
		timeUnitCount: Option[Int] = None
	)
	
	object VideoAdSequenceSettings {
		enum MinimumDurationEnum extends Enum[MinimumDurationEnum] { case VIDEO_AD_SEQUENCE_MINIMUM_DURATION_UNSPECIFIED, VIDEO_AD_SEQUENCE_MINIMUM_DURATION_WEEK, VIDEO_AD_SEQUENCE_MINIMUM_DURATION_MONTH }
	}
	case class VideoAdSequenceSettings(
	  /** The minimum time interval before the same user sees this sequence again. */
		minimumDuration: Option[Schema.VideoAdSequenceSettings.MinimumDurationEnum] = None,
	  /** The steps of which the sequence consists. */
		steps: Option[List[Schema.VideoAdSequenceStep]] = None
	)
	
	object VideoAdSequenceStep {
		enum InteractionTypeEnum extends Enum[InteractionTypeEnum] { case INTERACTION_TYPE_UNSPECIFIED, INTERACTION_TYPE_PAID_VIEW, INTERACTION_TYPE_SKIP, INTERACTION_TYPE_IMPRESSION, INTERACTION_TYPE_ENGAGED_IMPRESSION }
	}
	case class VideoAdSequenceStep(
	  /** The ID of the step. */
		stepId: Option[String] = None,
	  /** The ID of the corresponding ad group of the step. */
		adGroupId: Option[String] = None,
	  /** The ID of the previous step. The first step does not have previous step. */
		previousStepId: Option[String] = None,
	  /** The interaction on the previous step that will lead the viewer to this step. The first step does not have interaction_type. */
		interactionType: Option[Schema.VideoAdSequenceStep.InteractionTypeEnum] = None
	)
	
	case class ListLineItemsResponse(
	  /** The list of line items. This list will be absent if empty. */
		lineItems: Option[List[Schema.LineItem]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListLineItems` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object GenerateDefaultLineItemRequest {
		enum LineItemTypeEnum extends Enum[LineItemTypeEnum] { case LINE_ITEM_TYPE_UNSPECIFIED, LINE_ITEM_TYPE_DISPLAY_DEFAULT, LINE_ITEM_TYPE_DISPLAY_MOBILE_APP_INSTALL, LINE_ITEM_TYPE_VIDEO_DEFAULT, LINE_ITEM_TYPE_VIDEO_MOBILE_APP_INSTALL, LINE_ITEM_TYPE_DISPLAY_MOBILE_APP_INVENTORY, LINE_ITEM_TYPE_VIDEO_MOBILE_APP_INVENTORY, LINE_ITEM_TYPE_AUDIO_DEFAULT, LINE_ITEM_TYPE_VIDEO_OVER_THE_TOP, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_ACTION, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_NON_SKIPPABLE, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_VIDEO_SEQUENCE, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_AUDIO, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_REACH, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_SIMPLE, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_NON_SKIPPABLE_OVER_THE_TOP, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_REACH_OVER_THE_TOP, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_SIMPLE_OVER_THE_TOP, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_TARGET_FREQUENCY, LINE_ITEM_TYPE_YOUTUBE_AND_PARTNERS_VIEW, LINE_ITEM_TYPE_DISPLAY_OUT_OF_HOME, LINE_ITEM_TYPE_VIDEO_OUT_OF_HOME }
	}
	case class GenerateDefaultLineItemRequest(
	  /** Required. The unique ID of the insertion order that the line item belongs to. */
		insertionOrderId: Option[String] = None,
	  /** Required. The display name of the line item. Must be UTF-8 encoded with a maximum size of 240 bytes. */
		displayName: Option[String] = None,
	  /** Required. The type of the line item. */
		lineItemType: Option[Schema.GenerateDefaultLineItemRequest.LineItemTypeEnum] = None,
	  /** The mobile app promoted by the line item. This is applicable only when line_item_type is either `LINE_ITEM_TYPE_DISPLAY_MOBILE_APP_INSTALL` or `LINE_ITEM_TYPE_VIDEO_MOBILE_APP_INSTALL`. */
		mobileApp: Option[Schema.MobileApp] = None
	)
	
	case class DuplicateLineItemRequest(
	  /** The display name of the new line item. Must be UTF-8 encoded with a maximum size of 240 bytes. */
		targetDisplayName: Option[String] = None
	)
	
	case class DuplicateLineItemResponse(
	  /** The ID of the created line item. */
		duplicateLineItemId: Option[String] = None
	)
	
	case class BulkUpdateLineItemsRequest(
	  /** Required. IDs of line items to update. */
		lineItemIds: Option[List[String]] = None,
	  /** Required. A line item object containing the fields to be updated and the new values to assign to all line items specified in line_item_ids." */
		targetLineItem: Option[Schema.LineItem] = None,
	  /** Required. A field mask identifying which fields to update. Only the following fields are currently supported: &#42; entityStatus */
		updateMask: Option[String] = None
	)
	
	case class BulkUpdateLineItemsResponse(
	  /** The IDs of successfully updated line items. */
		updatedLineItemIds: Option[List[String]] = None,
	  /** The IDs of line items that failed to update. */
		failedLineItemIds: Option[List[String]] = None,
	  /** The IDs of line items that are skipped for updates. For example, unnecessary mutates that will result in effectively no changes to line items will be skipped and corresponding line item IDs can be tracked here. */
		skippedLineItemIds: Option[List[String]] = None,
	  /** Errors returned by line items that failed to update. */
		errors: Option[List[Schema.Status]] = None
	)
	
	object LocationList {
		enum LocationTypeEnum extends Enum[LocationTypeEnum] { case TARGETING_LOCATION_TYPE_UNSPECIFIED, TARGETING_LOCATION_TYPE_PROXIMITY, TARGETING_LOCATION_TYPE_REGIONAL }
	}
	case class LocationList(
	  /** Output only. The resource name of the location list. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the location list. Assigned by the system. */
		locationListId: Option[String] = None,
	  /** Required. The display name of the location list. Must be UTF-8 encoded with a maximum size of 240 bytes. */
		displayName: Option[String] = None,
	  /** Required. Immutable. The type of location. All locations in the list will share this type. */
		locationType: Option[Schema.LocationList.LocationTypeEnum] = None,
	  /** Required. Immutable. The unique ID of the advertiser the location list belongs to. */
		advertiserId: Option[String] = None
	)
	
	case class ListLocationListsResponse(
	  /** The list of location lists. This list will be absent if empty. */
		locationLists: Option[List[Schema.LocationList]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListLocationLists` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class NegativeKeywordList(
	  /** Output only. The resource name of the negative keyword list. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the negative keyword list. Assigned by the system. */
		negativeKeywordListId: Option[String] = None,
	  /** Required. The display name of the negative keyword list. Must be UTF-8 encoded with a maximum size of 255 bytes. */
		displayName: Option[String] = None,
	  /** Output only. The unique ID of the advertiser the negative keyword list belongs to. */
		advertiserId: Option[String] = None,
	  /** Output only. Number of line items that are directly targeting this negative keyword list. */
		targetedLineItemCount: Option[String] = None
	)
	
	case class ListNegativeKeywordListsResponse(
	  /** The list of negative keyword lists. This list will be absent if empty. */
		negativeKeywordLists: Option[List[Schema.NegativeKeywordList]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListNegativeKeywordLists` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class ListNegativeKeywordsResponse(
	  /** The list of negative keywords. This list will be absent if empty. */
		negativeKeywords: Option[List[Schema.NegativeKeyword]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListNegativeKeywords` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class NegativeKeyword(
	  /** Output only. The resource name of the negative keyword. */
		name: Option[String] = None,
	  /** Required. Immutable. The negatively targeted keyword, for example `car insurance`. Must be UTF-8 encoded with a maximum size of 255 bytes. Maximum number of characters is 80. Maximum number of words is 10. Valid characters are restricted to ASCII characters only. The only URL-escaping permitted is for representing whitespace between words. Leading or trailing whitespace is ignored. */
		keywordValue: Option[String] = None
	)
	
	case class BulkEditNegativeKeywordsRequest(
	  /** The negative keywords to delete in batch, specified as a list of keyword_values. */
		deletedNegativeKeywords: Option[List[String]] = None,
	  /** The negative keywords to create in batch, specified as a list of NegativeKeywords. */
		createdNegativeKeywords: Option[List[Schema.NegativeKeyword]] = None
	)
	
	case class BulkEditNegativeKeywordsResponse(
	  /** The list of negative keywords that have been successfully created. This list will be absent if empty. */
		negativeKeywords: Option[List[Schema.NegativeKeyword]] = None
	)
	
	case class ReplaceNegativeKeywordsRequest(
	  /** The negative keywords that will replace the existing keywords in the negative keyword list, specified as a list of NegativeKeywords. */
		newNegativeKeywords: Option[List[Schema.NegativeKeyword]] = None
	)
	
	case class ReplaceNegativeKeywordsResponse(
	  /** The full list of negative keywords now present in the negative keyword list. */
		negativeKeywords: Option[List[Schema.NegativeKeyword]] = None
	)
	
	case class ListPartnerAssignedTargetingOptionsResponse(
	  /** The list of assigned targeting options. This list will be absent if empty. */
		assignedTargetingOptions: Option[List[Schema.AssignedTargetingOption]] = None,
	  /** A token identifying the next page of results. This value should be specified as the pageToken in a subsequent ListPartnerAssignedTargetingOptionsRequest to fetch the next page of results. This token will be absent if there are no more assigned_targeting_options to return. */
		nextPageToken: Option[String] = None
	)
	
	case class BulkEditPartnerAssignedTargetingOptionsRequest(
	  /** The assigned targeting options to delete in batch, specified as a list of `DeleteAssignedTargetingOptionsRequest`. Supported targeting types: &#42; `TARGETING_TYPE_CHANNEL` */
		deleteRequests: Option[List[Schema.DeleteAssignedTargetingOptionsRequest]] = None,
	  /** The assigned targeting options to create in batch, specified as a list of `CreateAssignedTargetingOptionsRequest`. Supported targeting types: &#42; `TARGETING_TYPE_CHANNEL` */
		createRequests: Option[List[Schema.CreateAssignedTargetingOptionsRequest]] = None
	)
	
	case class BulkEditPartnerAssignedTargetingOptionsResponse(
	  /** The list of assigned targeting options that have been successfully created. This list will be absent if empty. */
		createdAssignedTargetingOptions: Option[List[Schema.AssignedTargetingOption]] = None
	)
	
	object Partner {
		enum EntityStatusEnum extends Enum[EntityStatusEnum] { case ENTITY_STATUS_UNSPECIFIED, ENTITY_STATUS_ACTIVE, ENTITY_STATUS_ARCHIVED, ENTITY_STATUS_DRAFT, ENTITY_STATUS_PAUSED, ENTITY_STATUS_SCHEDULED_FOR_DELETION }
	}
	case class Partner(
	  /** Output only. The resource name of the partner. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the partner. Assigned by the system. */
		partnerId: Option[String] = None,
	  /** Output only. The timestamp when the partner was last updated. Assigned by the system. */
		updateTime: Option[String] = None,
	  /** The display name of the partner. Must be UTF-8 encoded with a maximum size of 240 bytes. */
		displayName: Option[String] = None,
	  /** Output only. The status of the partner. */
		entityStatus: Option[Schema.Partner.EntityStatusEnum] = None,
	  /** General settings of the partner. */
		generalConfig: Option[Schema.PartnerGeneralConfig] = None,
	  /** Ad server related settings of the partner. */
		adServerConfig: Option[Schema.PartnerAdServerConfig] = None,
	  /** Settings that control how partner data may be accessed. */
		dataAccessConfig: Option[Schema.PartnerDataAccessConfig] = None,
	  /** Settings that control which exchanges are enabled for the partner. */
		exchangeConfig: Option[Schema.ExchangeConfig] = None,
	  /** Billing related settings of the partner. */
		billingConfig: Option[Schema.PartnerBillingConfig] = None
	)
	
	case class PartnerGeneralConfig(
	  /** Immutable. The standard TZ database name of the partner's time zone. For example, `America/New_York`. See more at: https://en.wikipedia.org/wiki/List_of_tz_database_time_zones */
		timeZone: Option[String] = None,
	  /** Immutable. Partner's currency in ISO 4217 format. */
		currencyCode: Option[String] = None
	)
	
	case class PartnerAdServerConfig(
	  /** Measurement settings of a partner. */
		measurementConfig: Option[Schema.MeasurementConfig] = None
	)
	
	case class MeasurementConfig(
	  /** Whether or not to report DV360 cost to CM360. */
		dv360ToCmCostReportingEnabled: Option[Boolean] = None,
	  /** Whether or not to include DV360 data in CM360 data transfer reports. */
		dv360ToCmDataSharingEnabled: Option[Boolean] = None
	)
	
	case class PartnerDataAccessConfig(
	  /** Structured Data Files (SDF) settings for the partner. The SDF configuration for the partner. */
		sdfConfig: Option[Schema.SdfConfig] = None
	)
	
	case class ExchangeConfig(
	  /** All enabled exchanges in the partner. Duplicate enabled exchanges will be ignored. */
		enabledExchanges: Option[List[Schema.ExchangeConfigEnabledExchange]] = None
	)
	
	object ExchangeConfigEnabledExchange {
		enum ExchangeEnum extends Enum[ExchangeEnum] { case EXCHANGE_UNSPECIFIED, EXCHANGE_GOOGLE_AD_MANAGER, EXCHANGE_APPNEXUS, EXCHANGE_BRIGHTROLL, EXCHANGE_ADFORM, EXCHANGE_ADMETA, EXCHANGE_ADMIXER, EXCHANGE_ADSMOGO, EXCHANGE_ADSWIZZ, EXCHANGE_BIDSWITCH, EXCHANGE_BRIGHTROLL_DISPLAY, EXCHANGE_CADREON, EXCHANGE_DAILYMOTION, EXCHANGE_FIVE, EXCHANGE_FLUCT, EXCHANGE_FREEWHEEL, EXCHANGE_GENIEE, EXCHANGE_GUMGUM, EXCHANGE_IMOBILE, EXCHANGE_IBILLBOARD, EXCHANGE_IMPROVE_DIGITAL, EXCHANGE_INDEX, EXCHANGE_KARGO, EXCHANGE_MICROAD, EXCHANGE_MOPUB, EXCHANGE_NEND, EXCHANGE_ONE_BY_AOL_DISPLAY, EXCHANGE_ONE_BY_AOL_MOBILE, EXCHANGE_ONE_BY_AOL_VIDEO, EXCHANGE_OOYALA, EXCHANGE_OPENX, EXCHANGE_PERMODO, EXCHANGE_PLATFORMONE, EXCHANGE_PLATFORMID, EXCHANGE_PUBMATIC, EXCHANGE_PULSEPOINT, EXCHANGE_REVENUEMAX, EXCHANGE_RUBICON, EXCHANGE_SMARTCLIP, EXCHANGE_SMARTRTB, EXCHANGE_SMARTSTREAMTV, EXCHANGE_SOVRN, EXCHANGE_SPOTXCHANGE, EXCHANGE_STROER, EXCHANGE_TEADSTV, EXCHANGE_TELARIA, EXCHANGE_TVN, EXCHANGE_UNITED, EXCHANGE_YIELDLAB, EXCHANGE_YIELDMO, EXCHANGE_UNRULYX, EXCHANGE_OPEN8, EXCHANGE_TRITON, EXCHANGE_TRIPLELIFT, EXCHANGE_TABOOLA, EXCHANGE_INMOBI, EXCHANGE_SMAATO, EXCHANGE_AJA, EXCHANGE_SUPERSHIP, EXCHANGE_NEXSTAR_DIGITAL, EXCHANGE_WAZE, EXCHANGE_SOUNDCAST, EXCHANGE_SHARETHROUGH, EXCHANGE_FYBER, EXCHANGE_RED_FOR_PUBLISHERS, EXCHANGE_MEDIANET, EXCHANGE_TAPJOY, EXCHANGE_VISTAR, EXCHANGE_DAX, EXCHANGE_JCD, EXCHANGE_PLACE_EXCHANGE, EXCHANGE_APPLOVIN, EXCHANGE_CONNATIX, EXCHANGE_RESET_DIGITAL, EXCHANGE_HIVESTACK, EXCHANGE_DRAX, EXCHANGE_APPLOVIN_GBID, EXCHANGE_FYBER_GBID, EXCHANGE_UNITY_GBID, EXCHANGE_CHARTBOOST_GBID, EXCHANGE_ADMOST_GBID, EXCHANGE_TOPON_GBID, EXCHANGE_NETFLIX, EXCHANGE_TUBI }
	}
	case class ExchangeConfigEnabledExchange(
	  /** The enabled exchange. */
		exchange: Option[Schema.ExchangeConfigEnabledExchange.ExchangeEnum] = None,
	  /** Output only. Agency ID of Google Ad Manager. The field is only relevant when Google Ad Manager is the enabled exchange. */
		googleAdManagerAgencyId: Option[String] = None,
	  /** Output only. Network ID of Google Ad Manager. The field is only relevant when Google Ad Manager is the enabled exchange. */
		googleAdManagerBuyerNetworkId: Option[String] = None,
	  /** Output only. Seat ID of the enabled exchange. */
		seatId: Option[String] = None
	)
	
	case class PartnerBillingConfig(
	  /** The ID of a partner default billing profile. */
		billingProfileId: Option[String] = None
	)
	
	case class ListPartnersResponse(
	  /** The list of partners. This list will be absent if empty. */
		partners: Option[List[Schema.Partner]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListPartners` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object CreateSdfDownloadTaskRequest {
		enum VersionEnum extends Enum[VersionEnum] { case SDF_VERSION_UNSPECIFIED, SDF_VERSION_3_1, SDF_VERSION_4, SDF_VERSION_4_1, SDF_VERSION_4_2, SDF_VERSION_5, SDF_VERSION_5_1, SDF_VERSION_5_2, SDF_VERSION_5_3, SDF_VERSION_5_4, SDF_VERSION_5_5, SDF_VERSION_6, SDF_VERSION_7, SDF_VERSION_7_1, SDF_VERSION_8 }
	}
	case class CreateSdfDownloadTaskRequest(
	  /** The ID of the partner to download SDF for. */
		partnerId: Option[String] = None,
	  /** The ID of the advertiser to download SDF for. */
		advertiserId: Option[String] = None,
	  /** Filters on selected file types. The entities in each file are filtered by a chosen set of filter entities. The filter entities must be the same type as, or a parent type of, the selected file types. */
		parentEntityFilter: Option[Schema.ParentEntityFilter] = None,
	  /** Filters on entities by their entity IDs. */
		idFilter: Option[Schema.IdFilter] = None,
	  /** Filters on Inventory Sources by their IDs. */
		inventorySourceFilter: Option[Schema.InventorySourceFilter] = None,
	  /** Required. The SDF version of the downloaded file. If set to `SDF_VERSION_UNSPECIFIED`, this will default to the version specified by the advertiser or partner identified by `root_id`. An advertiser inherits its SDF version from its partner unless configured otherwise. */
		version: Option[Schema.CreateSdfDownloadTaskRequest.VersionEnum] = None
	)
	
	object ParentEntityFilter {
		enum FileTypeEnum extends Enum[FileTypeEnum] { case FILE_TYPE_UNSPECIFIED, FILE_TYPE_CAMPAIGN, FILE_TYPE_MEDIA_PRODUCT, FILE_TYPE_INSERTION_ORDER, FILE_TYPE_LINE_ITEM, FILE_TYPE_AD_GROUP, FILE_TYPE_AD, FILE_TYPE_LINE_ITEM_QA, FILE_TYPE_AD_GROUP_QA }
		enum FilterTypeEnum extends Enum[FilterTypeEnum] { case FILTER_TYPE_UNSPECIFIED, FILTER_TYPE_NONE, FILTER_TYPE_ADVERTISER_ID, FILTER_TYPE_CAMPAIGN_ID, FILTER_TYPE_MEDIA_PRODUCT_ID, FILTER_TYPE_INSERTION_ORDER_ID, FILTER_TYPE_LINE_ITEM_ID }
	}
	case class ParentEntityFilter(
	  /** Required. File types that will be returned. */
		fileType: Option[List[Schema.ParentEntityFilter.FileTypeEnum]] = None,
	  /** Required. Filter type used to filter fetched entities. */
		filterType: Option[Schema.ParentEntityFilter.FilterTypeEnum] = None,
	  /** The IDs of the specified filter type. This is used to filter entities to fetch. If filter type is not `FILTER_TYPE_NONE`, at least one ID must be specified. */
		filterIds: Option[List[String]] = None
	)
	
	case class IdFilter(
	  /** Campaigns to download by ID. All IDs must belong to the same Advertiser or Partner specified in CreateSdfDownloadTaskRequest. */
		campaignIds: Option[List[String]] = None,
	  /** Media Products to download by ID. All IDs must belong to the same Advertiser or Partner specified in CreateSdfDownloadTaskRequest. */
		mediaProductIds: Option[List[String]] = None,
	  /** Insertion Orders to download by ID. All IDs must belong to the same Advertiser or Partner specified in CreateSdfDownloadTaskRequest. */
		insertionOrderIds: Option[List[String]] = None,
	  /** Line Items to download by ID. All IDs must belong to the same Advertiser or Partner specified in CreateSdfDownloadTaskRequest. */
		lineItemIds: Option[List[String]] = None,
	  /** YouTube Ad Groups to download by ID. All IDs must belong to the same Advertiser or Partner specified in CreateSdfDownloadTaskRequest. */
		adGroupIds: Option[List[String]] = None,
	  /** YouTube Ads to download by ID. All IDs must belong to the same Advertiser or Partner specified in CreateSdfDownloadTaskRequest. */
		adGroupAdIds: Option[List[String]] = None,
	  /** Optional. Line Items, by ID, to download in QA format. All IDs must belong to the same Advertiser or Partner specified in CreateSdfDownloadTaskRequest. */
		lineItemQaIds: Option[List[String]] = None,
	  /** Optional. YouTube Ad Groups, by ID, to download in QA format. All IDs must belong to the same Advertiser or Partner specified in CreateSdfDownloadTaskRequest. */
		adGroupQaIds: Option[List[String]] = None
	)
	
	case class InventorySourceFilter(
	  /** Inventory Sources to download by ID. All IDs must belong to the same Advertiser or Partner specified in CreateSdfDownloadTaskRequest. Leave empty to download all Inventory Sources for the selected Advertiser or Partner. */
		inventorySourceIds: Option[List[String]] = None
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
	
	case class ListSitesResponse(
	  /** The list of sites. This list will be absent if empty. */
		sites: Option[List[Schema.Site]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListSites` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class Site(
	  /** Output only. The resource name of the site. */
		name: Option[String] = None,
	  /** Required. The app ID or URL of the site. Must be UTF-8 encoded with a maximum length of 240 bytes. */
		urlOrAppId: Option[String] = None
	)
	
	case class BulkEditSitesRequest(
	  /** The ID of the partner that owns the parent channel. */
		partnerId: Option[String] = None,
	  /** The ID of the advertiser that owns the parent channel. */
		advertiserId: Option[String] = None,
	  /** The sites to delete in batch, specified as a list of site url_or_app_ids. */
		deletedSites: Option[List[String]] = None,
	  /** The sites to create in batch, specified as a list of Sites. */
		createdSites: Option[List[Schema.Site]] = None
	)
	
	case class BulkEditSitesResponse(
	  /** The list of sites that have been successfully created. This list will be absent if empty. */
		sites: Option[List[Schema.Site]] = None
	)
	
	case class ReplaceSitesRequest(
	  /** The ID of the partner that owns the parent channel. */
		partnerId: Option[String] = None,
	  /** The ID of the advertiser that owns the parent channel. */
		advertiserId: Option[String] = None,
	  /** The sites that will replace the existing sites assigned to the channel, specified as a list of Sites. */
		newSites: Option[List[Schema.Site]] = None
	)
	
	case class ReplaceSitesResponse(
	  /** The list of sites in the channel after replacing. */
		sites: Option[List[Schema.Site]] = None
	)
	
	object TargetingOption {
		enum TargetingTypeEnum extends Enum[TargetingTypeEnum] { case TARGETING_TYPE_UNSPECIFIED, TARGETING_TYPE_CHANNEL, TARGETING_TYPE_APP_CATEGORY, TARGETING_TYPE_APP, TARGETING_TYPE_URL, TARGETING_TYPE_DAY_AND_TIME, TARGETING_TYPE_AGE_RANGE, TARGETING_TYPE_REGIONAL_LOCATION_LIST, TARGETING_TYPE_PROXIMITY_LOCATION_LIST, TARGETING_TYPE_GENDER, TARGETING_TYPE_VIDEO_PLAYER_SIZE, TARGETING_TYPE_USER_REWARDED_CONTENT, TARGETING_TYPE_PARENTAL_STATUS, TARGETING_TYPE_CONTENT_INSTREAM_POSITION, TARGETING_TYPE_CONTENT_OUTSTREAM_POSITION, TARGETING_TYPE_DEVICE_TYPE, TARGETING_TYPE_AUDIENCE_GROUP, TARGETING_TYPE_BROWSER, TARGETING_TYPE_HOUSEHOLD_INCOME, TARGETING_TYPE_ON_SCREEN_POSITION, TARGETING_TYPE_THIRD_PARTY_VERIFIER, TARGETING_TYPE_DIGITAL_CONTENT_LABEL_EXCLUSION, TARGETING_TYPE_SENSITIVE_CATEGORY_EXCLUSION, TARGETING_TYPE_ENVIRONMENT, TARGETING_TYPE_CARRIER_AND_ISP, TARGETING_TYPE_OPERATING_SYSTEM, TARGETING_TYPE_DEVICE_MAKE_MODEL, TARGETING_TYPE_KEYWORD, TARGETING_TYPE_NEGATIVE_KEYWORD_LIST, TARGETING_TYPE_VIEWABILITY, TARGETING_TYPE_CATEGORY, TARGETING_TYPE_INVENTORY_SOURCE, TARGETING_TYPE_LANGUAGE, TARGETING_TYPE_AUTHORIZED_SELLER_STATUS, TARGETING_TYPE_GEO_REGION, TARGETING_TYPE_INVENTORY_SOURCE_GROUP, TARGETING_TYPE_EXCHANGE, TARGETING_TYPE_SUB_EXCHANGE, TARGETING_TYPE_POI, TARGETING_TYPE_BUSINESS_CHAIN, TARGETING_TYPE_CONTENT_DURATION, TARGETING_TYPE_CONTENT_STREAM_TYPE, TARGETING_TYPE_NATIVE_CONTENT_POSITION, TARGETING_TYPE_OMID, TARGETING_TYPE_AUDIO_CONTENT_TYPE, TARGETING_TYPE_CONTENT_GENRE, TARGETING_TYPE_YOUTUBE_VIDEO, TARGETING_TYPE_YOUTUBE_CHANNEL, TARGETING_TYPE_SESSION_POSITION }
	}
	case class TargetingOption(
	  /** Output only. The resource name for this targeting option. */
		name: Option[String] = None,
	  /** Output only. A unique identifier for this targeting option. The tuple {`targeting_type`, `targeting_option_id`} will be unique. */
		targetingOptionId: Option[String] = None,
	  /** Output only. The type of this targeting option. */
		targetingType: Option[Schema.TargetingOption.TargetingTypeEnum] = None,
	  /** Digital content label details. */
		digitalContentLabelDetails: Option[Schema.DigitalContentLabelTargetingOptionDetails] = None,
	  /** Sensitive Category details. */
		sensitiveCategoryDetails: Option[Schema.SensitiveCategoryTargetingOptionDetails] = None,
	  /** App category details. */
		appCategoryDetails: Option[Schema.AppCategoryTargetingOptionDetails] = None,
	  /** On screen position details. */
		onScreenPositionDetails: Option[Schema.OnScreenPositionTargetingOptionDetails] = None,
	  /** Content outstream position details. */
		contentOutstreamPositionDetails: Option[Schema.ContentOutstreamPositionTargetingOptionDetails] = None,
	  /** Content instream position details. */
		contentInstreamPositionDetails: Option[Schema.ContentInstreamPositionTargetingOptionDetails] = None,
	  /** Video player size details. */
		videoPlayerSizeDetails: Option[Schema.VideoPlayerSizeTargetingOptionDetails] = None,
	  /** Age range details. */
		ageRangeDetails: Option[Schema.AgeRangeTargetingOptionDetails] = None,
	  /** Parental status details. */
		parentalStatusDetails: Option[Schema.ParentalStatusTargetingOptionDetails] = None,
	  /** User rewarded content details. */
		userRewardedContentDetails: Option[Schema.UserRewardedContentTargetingOptionDetails] = None,
	  /** Household income details. */
		householdIncomeDetails: Option[Schema.HouseholdIncomeTargetingOptionDetails] = None,
	  /** Gender details. */
		genderDetails: Option[Schema.GenderTargetingOptionDetails] = None,
	  /** Device type details. */
		deviceTypeDetails: Option[Schema.DeviceTypeTargetingOptionDetails] = None,
	  /** Browser details. */
		browserDetails: Option[Schema.BrowserTargetingOptionDetails] = None,
	  /** Carrier and ISP details. */
		carrierAndIspDetails: Option[Schema.CarrierAndIspTargetingOptionDetails] = None,
	  /** Environment details. */
		environmentDetails: Option[Schema.EnvironmentTargetingOptionDetails] = None,
	  /** Operating system resources details. */
		operatingSystemDetails: Option[Schema.OperatingSystemTargetingOptionDetails] = None,
	  /** Device make and model resource details. */
		deviceMakeModelDetails: Option[Schema.DeviceMakeModelTargetingOptionDetails] = None,
	  /** Viewability resource details. */
		viewabilityDetails: Option[Schema.ViewabilityTargetingOptionDetails] = None,
	  /** Category resource details. */
		categoryDetails: Option[Schema.CategoryTargetingOptionDetails] = None,
	  /** Language resource details. */
		languageDetails: Option[Schema.LanguageTargetingOptionDetails] = None,
	  /** Authorized seller status resource details. */
		authorizedSellerStatusDetails: Option[Schema.AuthorizedSellerStatusTargetingOptionDetails] = None,
	  /** Geographic region resource details. */
		geoRegionDetails: Option[Schema.GeoRegionTargetingOptionDetails] = None,
	  /** Exchange details. */
		exchangeDetails: Option[Schema.ExchangeTargetingOptionDetails] = None,
	  /** Sub-exchange details. */
		subExchangeDetails: Option[Schema.SubExchangeTargetingOptionDetails] = None,
	  /** POI resource details. */
		poiDetails: Option[Schema.PoiTargetingOptionDetails] = None,
	  /** Business chain resource details. */
		businessChainDetails: Option[Schema.BusinessChainTargetingOptionDetails] = None,
	  /** Content duration resource details. */
		contentDurationDetails: Option[Schema.ContentDurationTargetingOptionDetails] = None,
	  /** Content stream type resource details. */
		contentStreamTypeDetails: Option[Schema.ContentStreamTypeTargetingOptionDetails] = None,
	  /** Native content position details. */
		nativeContentPositionDetails: Option[Schema.NativeContentPositionTargetingOptionDetails] = None,
	  /** Open Measurement enabled inventory details. */
		omidDetails: Option[Schema.OmidTargetingOptionDetails] = None,
	  /** Audio content type details. */
		audioContentTypeDetails: Option[Schema.AudioContentTypeTargetingOptionDetails] = None,
	  /** Content genre resource details. */
		contentGenreDetails: Option[Schema.ContentGenreTargetingOptionDetails] = None
	)
	
	object DigitalContentLabelTargetingOptionDetails {
		enum ContentRatingTierEnum extends Enum[ContentRatingTierEnum] { case CONTENT_RATING_TIER_UNSPECIFIED, CONTENT_RATING_TIER_UNRATED, CONTENT_RATING_TIER_GENERAL, CONTENT_RATING_TIER_PARENTAL_GUIDANCE, CONTENT_RATING_TIER_TEENS, CONTENT_RATING_TIER_MATURE, CONTENT_RATING_TIER_FAMILIES }
	}
	case class DigitalContentLabelTargetingOptionDetails(
	  /** Output only. An enum for the content label brand safety tiers. */
		contentRatingTier: Option[Schema.DigitalContentLabelTargetingOptionDetails.ContentRatingTierEnum] = None
	)
	
	object SensitiveCategoryTargetingOptionDetails {
		enum SensitiveCategoryEnum extends Enum[SensitiveCategoryEnum] { case SENSITIVE_CATEGORY_UNSPECIFIED, SENSITIVE_CATEGORY_ADULT, SENSITIVE_CATEGORY_DEROGATORY, SENSITIVE_CATEGORY_DOWNLOADS_SHARING, SENSITIVE_CATEGORY_WEAPONS, SENSITIVE_CATEGORY_GAMBLING, SENSITIVE_CATEGORY_VIOLENCE, SENSITIVE_CATEGORY_SUGGESTIVE, SENSITIVE_CATEGORY_PROFANITY, SENSITIVE_CATEGORY_ALCOHOL, SENSITIVE_CATEGORY_DRUGS, SENSITIVE_CATEGORY_TOBACCO, SENSITIVE_CATEGORY_POLITICS, SENSITIVE_CATEGORY_RELIGION, SENSITIVE_CATEGORY_TRAGEDY, SENSITIVE_CATEGORY_TRANSPORTATION_ACCIDENTS, SENSITIVE_CATEGORY_SENSITIVE_SOCIAL_ISSUES, SENSITIVE_CATEGORY_SHOCKING, SENSITIVE_CATEGORY_EMBEDDED_VIDEO, SENSITIVE_CATEGORY_LIVE_STREAMING_VIDEO }
	}
	case class SensitiveCategoryTargetingOptionDetails(
	  /** Output only. An enum for the DV360 Sensitive category content classifier. */
		sensitiveCategory: Option[Schema.SensitiveCategoryTargetingOptionDetails.SensitiveCategoryEnum] = None
	)
	
	case class AppCategoryTargetingOptionDetails(
	  /** Output only. The name of the app collection. */
		displayName: Option[String] = None
	)
	
	object OnScreenPositionTargetingOptionDetails {
		enum OnScreenPositionEnum extends Enum[OnScreenPositionEnum] { case ON_SCREEN_POSITION_UNSPECIFIED, ON_SCREEN_POSITION_UNKNOWN, ON_SCREEN_POSITION_ABOVE_THE_FOLD, ON_SCREEN_POSITION_BELOW_THE_FOLD }
	}
	case class OnScreenPositionTargetingOptionDetails(
	  /** Output only. The on screen position. */
		onScreenPosition: Option[Schema.OnScreenPositionTargetingOptionDetails.OnScreenPositionEnum] = None
	)
	
	object ContentOutstreamPositionTargetingOptionDetails {
		enum ContentOutstreamPositionEnum extends Enum[ContentOutstreamPositionEnum] { case CONTENT_OUTSTREAM_POSITION_UNSPECIFIED, CONTENT_OUTSTREAM_POSITION_UNKNOWN, CONTENT_OUTSTREAM_POSITION_IN_ARTICLE, CONTENT_OUTSTREAM_POSITION_IN_BANNER, CONTENT_OUTSTREAM_POSITION_IN_FEED, CONTENT_OUTSTREAM_POSITION_INTERSTITIAL }
	}
	case class ContentOutstreamPositionTargetingOptionDetails(
	  /** Output only. The content outstream position. */
		contentOutstreamPosition: Option[Schema.ContentOutstreamPositionTargetingOptionDetails.ContentOutstreamPositionEnum] = None
	)
	
	object ContentInstreamPositionTargetingOptionDetails {
		enum ContentInstreamPositionEnum extends Enum[ContentInstreamPositionEnum] { case CONTENT_INSTREAM_POSITION_UNSPECIFIED, CONTENT_INSTREAM_POSITION_PRE_ROLL, CONTENT_INSTREAM_POSITION_MID_ROLL, CONTENT_INSTREAM_POSITION_POST_ROLL, CONTENT_INSTREAM_POSITION_UNKNOWN }
	}
	case class ContentInstreamPositionTargetingOptionDetails(
	  /** Output only. The content instream position. */
		contentInstreamPosition: Option[Schema.ContentInstreamPositionTargetingOptionDetails.ContentInstreamPositionEnum] = None
	)
	
	object VideoPlayerSizeTargetingOptionDetails {
		enum VideoPlayerSizeEnum extends Enum[VideoPlayerSizeEnum] { case VIDEO_PLAYER_SIZE_UNSPECIFIED, VIDEO_PLAYER_SIZE_SMALL, VIDEO_PLAYER_SIZE_LARGE, VIDEO_PLAYER_SIZE_HD, VIDEO_PLAYER_SIZE_UNKNOWN }
	}
	case class VideoPlayerSizeTargetingOptionDetails(
	  /** Output only. The video player size. */
		videoPlayerSize: Option[Schema.VideoPlayerSizeTargetingOptionDetails.VideoPlayerSizeEnum] = None
	)
	
	object AgeRangeTargetingOptionDetails {
		enum AgeRangeEnum extends Enum[AgeRangeEnum] { case AGE_RANGE_UNSPECIFIED, AGE_RANGE_18_24, AGE_RANGE_25_34, AGE_RANGE_35_44, AGE_RANGE_45_54, AGE_RANGE_55_64, AGE_RANGE_65_PLUS, AGE_RANGE_UNKNOWN, AGE_RANGE_18_20, AGE_RANGE_21_24, AGE_RANGE_25_29, AGE_RANGE_30_34, AGE_RANGE_35_39, AGE_RANGE_40_44, AGE_RANGE_45_49, AGE_RANGE_50_54, AGE_RANGE_55_59, AGE_RANGE_60_64 }
	}
	case class AgeRangeTargetingOptionDetails(
	  /** Output only. The age range of an audience. */
		ageRange: Option[Schema.AgeRangeTargetingOptionDetails.AgeRangeEnum] = None
	)
	
	object ParentalStatusTargetingOptionDetails {
		enum ParentalStatusEnum extends Enum[ParentalStatusEnum] { case PARENTAL_STATUS_UNSPECIFIED, PARENTAL_STATUS_PARENT, PARENTAL_STATUS_NOT_A_PARENT, PARENTAL_STATUS_UNKNOWN }
	}
	case class ParentalStatusTargetingOptionDetails(
	  /** Output only. The parental status of an audience. */
		parentalStatus: Option[Schema.ParentalStatusTargetingOptionDetails.ParentalStatusEnum] = None
	)
	
	object UserRewardedContentTargetingOptionDetails {
		enum UserRewardedContentEnum extends Enum[UserRewardedContentEnum] { case USER_REWARDED_CONTENT_UNSPECIFIED, USER_REWARDED_CONTENT_USER_REWARDED, USER_REWARDED_CONTENT_NOT_USER_REWARDED }
	}
	case class UserRewardedContentTargetingOptionDetails(
	  /** Output only. User rewarded content status for video ads. */
		userRewardedContent: Option[Schema.UserRewardedContentTargetingOptionDetails.UserRewardedContentEnum] = None
	)
	
	object HouseholdIncomeTargetingOptionDetails {
		enum HouseholdIncomeEnum extends Enum[HouseholdIncomeEnum] { case HOUSEHOLD_INCOME_UNSPECIFIED, HOUSEHOLD_INCOME_UNKNOWN, HOUSEHOLD_INCOME_LOWER_50_PERCENT, HOUSEHOLD_INCOME_TOP_41_TO_50_PERCENT, HOUSEHOLD_INCOME_TOP_31_TO_40_PERCENT, HOUSEHOLD_INCOME_TOP_21_TO_30_PERCENT, HOUSEHOLD_INCOME_TOP_11_TO_20_PERCENT, HOUSEHOLD_INCOME_TOP_10_PERCENT }
	}
	case class HouseholdIncomeTargetingOptionDetails(
	  /** Output only. The household income of an audience. */
		householdIncome: Option[Schema.HouseholdIncomeTargetingOptionDetails.HouseholdIncomeEnum] = None
	)
	
	object GenderTargetingOptionDetails {
		enum GenderEnum extends Enum[GenderEnum] { case GENDER_UNSPECIFIED, GENDER_MALE, GENDER_FEMALE, GENDER_UNKNOWN }
	}
	case class GenderTargetingOptionDetails(
	  /** Output only. The gender of an audience. */
		gender: Option[Schema.GenderTargetingOptionDetails.GenderEnum] = None
	)
	
	object DeviceTypeTargetingOptionDetails {
		enum DeviceTypeEnum extends Enum[DeviceTypeEnum] { case DEVICE_TYPE_UNSPECIFIED, DEVICE_TYPE_COMPUTER, DEVICE_TYPE_CONNECTED_TV, DEVICE_TYPE_SMART_PHONE, DEVICE_TYPE_TABLET, DEVICE_TYPE_CONNECTED_DEVICE }
	}
	case class DeviceTypeTargetingOptionDetails(
	  /** Output only. The device type that is used to be targeted. */
		deviceType: Option[Schema.DeviceTypeTargetingOptionDetails.DeviceTypeEnum] = None
	)
	
	case class BrowserTargetingOptionDetails(
	  /** Output only. The display name of the browser. */
		displayName: Option[String] = None
	)
	
	object CarrierAndIspTargetingOptionDetails {
		enum TypeEnum extends Enum[TypeEnum] { case CARRIER_AND_ISP_TYPE_UNSPECIFIED, CARRIER_AND_ISP_TYPE_ISP, CARRIER_AND_ISP_TYPE_CARRIER }
	}
	case class CarrierAndIspTargetingOptionDetails(
	  /** Output only. The display name of the carrier or ISP. */
		displayName: Option[String] = None,
	  /** Output only. The type indicating if it's carrier or ISP. */
		`type`: Option[Schema.CarrierAndIspTargetingOptionDetails.TypeEnum] = None
	)
	
	object EnvironmentTargetingOptionDetails {
		enum EnvironmentEnum extends Enum[EnvironmentEnum] { case ENVIRONMENT_UNSPECIFIED, ENVIRONMENT_WEB_OPTIMIZED, ENVIRONMENT_WEB_NOT_OPTIMIZED, ENVIRONMENT_APP }
	}
	case class EnvironmentTargetingOptionDetails(
	  /** Output only. The serving environment. */
		environment: Option[Schema.EnvironmentTargetingOptionDetails.EnvironmentEnum] = None
	)
	
	case class OperatingSystemTargetingOptionDetails(
	  /** Output only. The display name of the operating system. */
		displayName: Option[String] = None
	)
	
	case class DeviceMakeModelTargetingOptionDetails(
	  /** Output only. The display name of the device make and model. */
		displayName: Option[String] = None
	)
	
	object ViewabilityTargetingOptionDetails {
		enum ViewabilityEnum extends Enum[ViewabilityEnum] { case VIEWABILITY_UNSPECIFIED, VIEWABILITY_10_PERCENT_OR_MORE, VIEWABILITY_20_PERCENT_OR_MORE, VIEWABILITY_30_PERCENT_OR_MORE, VIEWABILITY_40_PERCENT_OR_MORE, VIEWABILITY_50_PERCENT_OR_MORE, VIEWABILITY_60_PERCENT_OR_MORE, VIEWABILITY_70_PERCENT_OR_MORE, VIEWABILITY_80_PERCENT_OR_MORE, VIEWABILITY_90_PERCENT_OR_MORE }
	}
	case class ViewabilityTargetingOptionDetails(
	  /** Output only. The predicted viewability percentage. */
		viewability: Option[Schema.ViewabilityTargetingOptionDetails.ViewabilityEnum] = None
	)
	
	case class CategoryTargetingOptionDetails(
	  /** Output only. The display name of the category. */
		displayName: Option[String] = None
	)
	
	case class LanguageTargetingOptionDetails(
	  /** Output only. The display name of the language (e.g., "French"). */
		displayName: Option[String] = None
	)
	
	object AuthorizedSellerStatusTargetingOptionDetails {
		enum AuthorizedSellerStatusEnum extends Enum[AuthorizedSellerStatusEnum] { case AUTHORIZED_SELLER_STATUS_UNSPECIFIED, AUTHORIZED_SELLER_STATUS_AUTHORIZED_DIRECT_SELLERS_ONLY, AUTHORIZED_SELLER_STATUS_AUTHORIZED_AND_NON_PARTICIPATING_PUBLISHERS }
	}
	case class AuthorizedSellerStatusTargetingOptionDetails(
	  /** Output only. The authorized seller status. */
		authorizedSellerStatus: Option[Schema.AuthorizedSellerStatusTargetingOptionDetails.AuthorizedSellerStatusEnum] = None
	)
	
	object GeoRegionTargetingOptionDetails {
		enum GeoRegionTypeEnum extends Enum[GeoRegionTypeEnum] { case GEO_REGION_TYPE_UNKNOWN, GEO_REGION_TYPE_OTHER, GEO_REGION_TYPE_COUNTRY, GEO_REGION_TYPE_REGION, GEO_REGION_TYPE_TERRITORY, GEO_REGION_TYPE_PROVINCE, GEO_REGION_TYPE_STATE, GEO_REGION_TYPE_PREFECTURE, GEO_REGION_TYPE_GOVERNORATE, GEO_REGION_TYPE_CANTON, GEO_REGION_TYPE_UNION_TERRITORY, GEO_REGION_TYPE_AUTONOMOUS_COMMUNITY, GEO_REGION_TYPE_DMA_REGION, GEO_REGION_TYPE_METRO, GEO_REGION_TYPE_CONGRESSIONAL_DISTRICT, GEO_REGION_TYPE_COUNTY, GEO_REGION_TYPE_MUNICIPALITY, GEO_REGION_TYPE_CITY, GEO_REGION_TYPE_POSTAL_CODE, GEO_REGION_TYPE_DEPARTMENT, GEO_REGION_TYPE_AIRPORT, GEO_REGION_TYPE_TV_REGION, GEO_REGION_TYPE_OKRUG, GEO_REGION_TYPE_BOROUGH, GEO_REGION_TYPE_CITY_REGION, GEO_REGION_TYPE_ARRONDISSEMENT, GEO_REGION_TYPE_NEIGHBORHOOD, GEO_REGION_TYPE_UNIVERSITY, GEO_REGION_TYPE_DISTRICT }
	}
	case class GeoRegionTargetingOptionDetails(
	  /** Output only. The display name of the geographic region (e.g., "Ontario, Canada"). */
		displayName: Option[String] = None,
	  /** Output only. The type of geographic region targeting. */
		geoRegionType: Option[Schema.GeoRegionTargetingOptionDetails.GeoRegionTypeEnum] = None
	)
	
	object ExchangeTargetingOptionDetails {
		enum ExchangeEnum extends Enum[ExchangeEnum] { case EXCHANGE_UNSPECIFIED, EXCHANGE_GOOGLE_AD_MANAGER, EXCHANGE_APPNEXUS, EXCHANGE_BRIGHTROLL, EXCHANGE_ADFORM, EXCHANGE_ADMETA, EXCHANGE_ADMIXER, EXCHANGE_ADSMOGO, EXCHANGE_ADSWIZZ, EXCHANGE_BIDSWITCH, EXCHANGE_BRIGHTROLL_DISPLAY, EXCHANGE_CADREON, EXCHANGE_DAILYMOTION, EXCHANGE_FIVE, EXCHANGE_FLUCT, EXCHANGE_FREEWHEEL, EXCHANGE_GENIEE, EXCHANGE_GUMGUM, EXCHANGE_IMOBILE, EXCHANGE_IBILLBOARD, EXCHANGE_IMPROVE_DIGITAL, EXCHANGE_INDEX, EXCHANGE_KARGO, EXCHANGE_MICROAD, EXCHANGE_MOPUB, EXCHANGE_NEND, EXCHANGE_ONE_BY_AOL_DISPLAY, EXCHANGE_ONE_BY_AOL_MOBILE, EXCHANGE_ONE_BY_AOL_VIDEO, EXCHANGE_OOYALA, EXCHANGE_OPENX, EXCHANGE_PERMODO, EXCHANGE_PLATFORMONE, EXCHANGE_PLATFORMID, EXCHANGE_PUBMATIC, EXCHANGE_PULSEPOINT, EXCHANGE_REVENUEMAX, EXCHANGE_RUBICON, EXCHANGE_SMARTCLIP, EXCHANGE_SMARTRTB, EXCHANGE_SMARTSTREAMTV, EXCHANGE_SOVRN, EXCHANGE_SPOTXCHANGE, EXCHANGE_STROER, EXCHANGE_TEADSTV, EXCHANGE_TELARIA, EXCHANGE_TVN, EXCHANGE_UNITED, EXCHANGE_YIELDLAB, EXCHANGE_YIELDMO, EXCHANGE_UNRULYX, EXCHANGE_OPEN8, EXCHANGE_TRITON, EXCHANGE_TRIPLELIFT, EXCHANGE_TABOOLA, EXCHANGE_INMOBI, EXCHANGE_SMAATO, EXCHANGE_AJA, EXCHANGE_SUPERSHIP, EXCHANGE_NEXSTAR_DIGITAL, EXCHANGE_WAZE, EXCHANGE_SOUNDCAST, EXCHANGE_SHARETHROUGH, EXCHANGE_FYBER, EXCHANGE_RED_FOR_PUBLISHERS, EXCHANGE_MEDIANET, EXCHANGE_TAPJOY, EXCHANGE_VISTAR, EXCHANGE_DAX, EXCHANGE_JCD, EXCHANGE_PLACE_EXCHANGE, EXCHANGE_APPLOVIN, EXCHANGE_CONNATIX, EXCHANGE_RESET_DIGITAL, EXCHANGE_HIVESTACK, EXCHANGE_DRAX, EXCHANGE_APPLOVIN_GBID, EXCHANGE_FYBER_GBID, EXCHANGE_UNITY_GBID, EXCHANGE_CHARTBOOST_GBID, EXCHANGE_ADMOST_GBID, EXCHANGE_TOPON_GBID, EXCHANGE_NETFLIX, EXCHANGE_TUBI }
	}
	case class ExchangeTargetingOptionDetails(
	  /** Output only. The type of exchange. */
		exchange: Option[Schema.ExchangeTargetingOptionDetails.ExchangeEnum] = None
	)
	
	case class SubExchangeTargetingOptionDetails(
	  /** Output only. The display name of the sub-exchange. */
		displayName: Option[String] = None
	)
	
	case class PoiTargetingOptionDetails(
	  /** Output only. Latitude of the POI rounding to 6th decimal place. */
		latitude: Option[BigDecimal] = None,
	  /** Output only. Longitude of the POI rounding to 6th decimal place. */
		longitude: Option[BigDecimal] = None,
	  /** Output only. The display name of a POI(e.g. "Times Square", "Space Needle"), followed by its full address if available. */
		displayName: Option[String] = None
	)
	
	object BusinessChainTargetingOptionDetails {
		enum GeoRegionTypeEnum extends Enum[GeoRegionTypeEnum] { case GEO_REGION_TYPE_UNKNOWN, GEO_REGION_TYPE_OTHER, GEO_REGION_TYPE_COUNTRY, GEO_REGION_TYPE_REGION, GEO_REGION_TYPE_TERRITORY, GEO_REGION_TYPE_PROVINCE, GEO_REGION_TYPE_STATE, GEO_REGION_TYPE_PREFECTURE, GEO_REGION_TYPE_GOVERNORATE, GEO_REGION_TYPE_CANTON, GEO_REGION_TYPE_UNION_TERRITORY, GEO_REGION_TYPE_AUTONOMOUS_COMMUNITY, GEO_REGION_TYPE_DMA_REGION, GEO_REGION_TYPE_METRO, GEO_REGION_TYPE_CONGRESSIONAL_DISTRICT, GEO_REGION_TYPE_COUNTY, GEO_REGION_TYPE_MUNICIPALITY, GEO_REGION_TYPE_CITY, GEO_REGION_TYPE_POSTAL_CODE, GEO_REGION_TYPE_DEPARTMENT, GEO_REGION_TYPE_AIRPORT, GEO_REGION_TYPE_TV_REGION, GEO_REGION_TYPE_OKRUG, GEO_REGION_TYPE_BOROUGH, GEO_REGION_TYPE_CITY_REGION, GEO_REGION_TYPE_ARRONDISSEMENT, GEO_REGION_TYPE_NEIGHBORHOOD, GEO_REGION_TYPE_UNIVERSITY, GEO_REGION_TYPE_DISTRICT }
	}
	case class BusinessChainTargetingOptionDetails(
	  /** Output only. The display name of the business chain, e.g. "KFC", "Chase Bank". */
		businessChain: Option[String] = None,
	  /** Output only. The display name of the geographic region, e.g. "Ontario, Canada". */
		geoRegion: Option[String] = None,
	  /** Output only. The type of the geographic region. */
		geoRegionType: Option[Schema.BusinessChainTargetingOptionDetails.GeoRegionTypeEnum] = None
	)
	
	object ContentDurationTargetingOptionDetails {
		enum ContentDurationEnum extends Enum[ContentDurationEnum] { case CONTENT_DURATION_UNSPECIFIED, CONTENT_DURATION_UNKNOWN, CONTENT_DURATION_0_TO_1_MIN, CONTENT_DURATION_1_TO_5_MIN, CONTENT_DURATION_5_TO_15_MIN, CONTENT_DURATION_15_TO_30_MIN, CONTENT_DURATION_30_TO_60_MIN, CONTENT_DURATION_OVER_60_MIN }
	}
	case class ContentDurationTargetingOptionDetails(
	  /** Output only. The content duration. */
		contentDuration: Option[Schema.ContentDurationTargetingOptionDetails.ContentDurationEnum] = None
	)
	
	object ContentStreamTypeTargetingOptionDetails {
		enum ContentStreamTypeEnum extends Enum[ContentStreamTypeEnum] { case CONTENT_STREAM_TYPE_UNSPECIFIED, CONTENT_LIVE_STREAM, CONTENT_ON_DEMAND }
	}
	case class ContentStreamTypeTargetingOptionDetails(
	  /** Output only. The content stream type. */
		contentStreamType: Option[Schema.ContentStreamTypeTargetingOptionDetails.ContentStreamTypeEnum] = None
	)
	
	object NativeContentPositionTargetingOptionDetails {
		enum ContentPositionEnum extends Enum[ContentPositionEnum] { case NATIVE_CONTENT_POSITION_UNSPECIFIED, NATIVE_CONTENT_POSITION_UNKNOWN, NATIVE_CONTENT_POSITION_IN_ARTICLE, NATIVE_CONTENT_POSITION_IN_FEED, NATIVE_CONTENT_POSITION_PERIPHERAL, NATIVE_CONTENT_POSITION_RECOMMENDATION }
	}
	case class NativeContentPositionTargetingOptionDetails(
	  /** Output only. The content position. */
		contentPosition: Option[Schema.NativeContentPositionTargetingOptionDetails.ContentPositionEnum] = None
	)
	
	object OmidTargetingOptionDetails {
		enum OmidEnum extends Enum[OmidEnum] { case OMID_UNSPECIFIED, OMID_FOR_MOBILE_DISPLAY_ADS }
	}
	case class OmidTargetingOptionDetails(
	  /** Output only. The type of Open Measurement enabled inventory. */
		omid: Option[Schema.OmidTargetingOptionDetails.OmidEnum] = None
	)
	
	object AudioContentTypeTargetingOptionDetails {
		enum AudioContentTypeEnum extends Enum[AudioContentTypeEnum] { case AUDIO_CONTENT_TYPE_UNSPECIFIED, AUDIO_CONTENT_TYPE_UNKNOWN, AUDIO_CONTENT_TYPE_MUSIC, AUDIO_CONTENT_TYPE_BROADCAST, AUDIO_CONTENT_TYPE_PODCAST }
	}
	case class AudioContentTypeTargetingOptionDetails(
	  /** Output only. The audio content type. */
		audioContentType: Option[Schema.AudioContentTypeTargetingOptionDetails.AudioContentTypeEnum] = None
	)
	
	case class ContentGenreTargetingOptionDetails(
	  /** Output only. The display name of the content genre */
		displayName: Option[String] = None
	)
	
	case class ListTargetingOptionsResponse(
	  /** The list of targeting options. This list will be absent if empty. */
		targetingOptions: Option[List[Schema.TargetingOption]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListTargetingOptions` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class SearchTargetingOptionsRequest(
	  /** Required. The Advertiser this request is being made in the context of. */
		advertiserId: Option[String] = None,
	  /** Requested page size. Must be between `1` and `200`. If unspecified will default to `100`. Returns error code `INVALID_ARGUMENT` if an invalid value is specified. */
		pageSize: Option[Int] = None,
	  /** A token identifying a page of results the server should return. Typically, this is the value of next_page_token returned from the previous call to `SearchTargetingOptions` method. If not specified, the first page of results will be returned. */
		pageToken: Option[String] = None,
	  /** Search terms for POI targeting options. Can only be used when targeting_type is `TARGETING_TYPE_POI`. */
		poiSearchTerms: Option[Schema.PoiSearchTerms] = None,
	  /** Search terms for Business Chain targeting options. Can only be used when targeting_type is `TARGETING_TYPE_BUSINESS_CHAIN`. */
		businessChainSearchTerms: Option[Schema.BusinessChainSearchTerms] = None,
	  /** Search terms for geo region targeting options. Can only be used when targeting_type is `TARGETING_TYPE_GEO_REGION`. */
		geoRegionSearchTerms: Option[Schema.GeoRegionSearchTerms] = None
	)
	
	case class PoiSearchTerms(
	  /** The search query for the desired POI name, street address, or coordinate of the desired POI. The query can be a prefix, e.g. "Times squar", "40.7505045,-73.99562", "315 W 44th St", etc. */
		poiQuery: Option[String] = None
	)
	
	case class BusinessChainSearchTerms(
	  /** The search query for the desired business chain. The query must be the full name of the business, e.g. "KFC", "mercedes-benz". */
		businessChainQuery: Option[String] = None,
	  /** The search query for the desired geo region, e.g. "Seattle", "United State". */
		regionQuery: Option[String] = None
	)
	
	case class GeoRegionSearchTerms(
	  /** The search query for the desired geo region. The query can be a prefix, e.g. "New Yor", "Seattle", "USA", etc. */
		geoRegionQuery: Option[String] = None
	)
	
	case class SearchTargetingOptionsResponse(
	  /** The list of targeting options that match the search criteria. This list will be absent if empty. */
		targetingOptions: Option[List[Schema.TargetingOption]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `SearchTargetingOptions` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class User(
	  /** Output only. The resource name of the user. */
		name: Option[String] = None,
	  /** Output only. The unique ID of the user. Assigned by the system. */
		userId: Option[String] = None,
	  /** Required. Immutable. The email address used to identify the user. */
		email: Option[String] = None,
	  /** Required. The display name of the user. Must be UTF-8 encoded with a maximum size of 240 bytes. */
		displayName: Option[String] = None,
	  /** The assigned user roles. Required in CreateUser. Output only in UpdateUser. Can only be updated through BulkEditAssignedUserRoles. */
		assignedUserRoles: Option[List[Schema.AssignedUserRole]] = None,
	  /** Output only. The timestamp when the user last logged in DV360 UI. */
		lastLoginTime: Option[String] = None
	)
	
	object AssignedUserRole {
		enum UserRoleEnum extends Enum[UserRoleEnum] { case USER_ROLE_UNSPECIFIED, ADMIN, ADMIN_PARTNER_CLIENT, STANDARD, STANDARD_PLANNER, STANDARD_PLANNER_LIMITED, STANDARD_PARTNER_CLIENT, READ_ONLY, REPORTING_ONLY, LIMITED_REPORTING_ONLY, CREATIVE, CREATIVE_ADMIN }
	}
	case class AssignedUserRole(
	  /** Output only. The ID of the assigned user role. */
		assignedUserRoleId: Option[String] = None,
	  /** The ID of the partner that the assigned user role applies to. */
		partnerId: Option[String] = None,
	  /** The ID of the advertiser that the assigend user role applies to. */
		advertiserId: Option[String] = None,
	  /** Required. The user role to assign to a user for the entity. */
		userRole: Option[Schema.AssignedUserRole.UserRoleEnum] = None
	)
	
	case class ListUsersResponse(
	  /** The list of users. This list will be absent if empty. */
		users: Option[List[Schema.User]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the page_token field in the subsequent call to `ListUsers` method to retrieve the next page of results. This token will be absent if there are no more results to return. */
		nextPageToken: Option[String] = None
	)
	
	case class BulkEditAssignedUserRolesRequest(
	  /** The assigned user roles to delete in batch, specified as a list of assigned_user_role_ids. The format of assigned_user_role_id is `entityType-entityid`, for example `partner-123`. */
		deletedAssignedUserRoles: Option[List[String]] = None,
	  /** The assigned user roles to create in batch, specified as a list of AssignedUserRoles. */
		createdAssignedUserRoles: Option[List[Schema.AssignedUserRole]] = None
	)
	
	case class BulkEditAssignedUserRolesResponse(
	  /** The list of assigned user roles that have been successfully created. This list will be absent if empty. */
		createdAssignedUserRoles: Option[List[Schema.AssignedUserRole]] = None
	)
	
	case class GoogleBytestreamMedia(
	  /** Name of the media resource. */
		resourceName: Option[String] = None
	)
	
	object SdfDownloadTaskMetadata {
		enum VersionEnum extends Enum[VersionEnum] { case SDF_VERSION_UNSPECIFIED, SDF_VERSION_3_1, SDF_VERSION_4, SDF_VERSION_4_1, SDF_VERSION_4_2, SDF_VERSION_5, SDF_VERSION_5_1, SDF_VERSION_5_2, SDF_VERSION_5_3, SDF_VERSION_5_4, SDF_VERSION_5_5, SDF_VERSION_6, SDF_VERSION_7, SDF_VERSION_7_1, SDF_VERSION_8 }
	}
	case class SdfDownloadTaskMetadata(
	  /** The time when the operation was created. */
		createTime: Option[String] = None,
	  /** The time when execution was completed. */
		endTime: Option[String] = None,
	  /** The SDF version used to execute this download task. */
		version: Option[Schema.SdfDownloadTaskMetadata.VersionEnum] = None
	)
	
	case class SdfDownloadTask(
	  /** A resource name to be used in media.download to Download the prepared files. Resource names have the format `download/sdfdownloadtasks/media/{media_id}`. `media_id` will be made available by the long running operation service once the task status is done. */
		resourceName: Option[String] = None
	)
	
	case class AlgorithmRules(
	  /** Rules for the impression signals. */
		impressionSignalRuleset: Option[Schema.AlgorithmRulesRuleset] = None
	)
	
	object AlgorithmRulesRuleset {
		enum AggregationTypeEnum extends Enum[AggregationTypeEnum] { case RULE_AGGREGATION_TYPE_UNSPECIFIED, SUM_OF_VALUES, PRODUCT_OF_VALUES, MAXIMUM_VALUE }
	}
	case class AlgorithmRulesRuleset(
	  /** List of rules to generate the impression value. */
		rules: Option[List[Schema.AlgorithmRulesRule]] = None,
	  /** How to aggregate values of evaluated rules. */
		aggregationType: Option[Schema.AlgorithmRulesRuleset.AggregationTypeEnum] = None,
	  /** Maximum value the ruleset can evaluate to. */
		maxValue: Option[BigDecimal] = None
	)
	
	case class AlgorithmRulesRule(
	  /** List of conditions in this rule. The criteria among conditions should be mutually exclusive. */
		conditions: Option[List[Schema.AlgorithmRulesRuleCondition]] = None,
	  /** The default return value applied when none of the conditions are met. */
		defaultReturnValue: Option[Schema.AlgorithmRulesSignalValue] = None
	)
	
	case class AlgorithmRulesRuleCondition(
	  /** List of comparisons that build `if` statement condition. The comparisons are combined into a single condition with `AND` logical operators. */
		signalComparisons: Option[List[Schema.AlgorithmRulesSignalComparison]] = None,
	  /** The value returned if the `signalComparisons` condition evaluates to `TRUE`. */
		returnValue: Option[Schema.AlgorithmRulesSignalValue] = None
	)
	
	object AlgorithmRulesSignalComparison {
		enum ComparisonOperatorEnum extends Enum[ComparisonOperatorEnum] { case COMPARISON_OPERATOR_UNSPECIFIED, EQUAL, GREATER_THAN, LESS_THAN, GREATER_THAN_OR_EQUAL_TO, LESS_THAN_OR_EQUAL_TO }
	}
	case class AlgorithmRulesSignalComparison(
	  /** Signal to compare. */
		signal: Option[Schema.AlgorithmRulesSignal] = None,
	  /** Operator used to compare the two values. In the resulting experession, the `signal` will be the first value and the `comparisonValue will be the second. */
		comparisonOperator: Option[Schema.AlgorithmRulesSignalComparison.ComparisonOperatorEnum] = None,
	  /** Value to compare signal to. */
		comparisonValue: Option[Schema.AlgorithmRulesComparisonValue] = None
	)
	
	object AlgorithmRulesSignal {
		enum ImpressionSignalEnum extends Enum[ImpressionSignalEnum] { case IMPRESSION_SIGNAL_UNSPECIFIED, DAY_AND_TIME, DEVICE_TYPE, AD_POSITION, OPERATING_SYSTEM_ID, MOBILE_MODEL_ID, EXCHANGE, ENVIRONMENT, COUNTRY_ID, CITY_ID, BROWSER_ID, CREATIVE_DIMENSION }
	}
	case class AlgorithmRulesSignal(
	  /** Signal based on impressions. */
		impressionSignal: Option[Schema.AlgorithmRulesSignal.ImpressionSignalEnum] = None
	)
	
	object AlgorithmRulesComparisonValue {
		enum DeviceTypeValueEnum extends Enum[DeviceTypeValueEnum] { case RULE_DEVICE_TYPE_UNSPECIFIED, RULE_DEVICE_TYPE_COMPUTER, RULE_DEVICE_TYPE_CONNECTED_TV, RULE_DEVICE_TYPE_SMART_PHONE, RULE_DEVICE_TYPE_TABLET, RULE_DEVICE_TYPE_CONNECTED_DEVICE, RULE_DEVICE_TYPE_SET_TOP_BOX }
		enum OnScreenPositionValueEnum extends Enum[OnScreenPositionValueEnum] { case ON_SCREEN_POSITION_UNSPECIFIED, ON_SCREEN_POSITION_UNKNOWN, ON_SCREEN_POSITION_ABOVE_THE_FOLD, ON_SCREEN_POSITION_BELOW_THE_FOLD }
		enum EnvironmentValueEnum extends Enum[EnvironmentValueEnum] { case ENVIRONMENT_UNSPECIFIED, ENVIRONMENT_WEB_OPTIMIZED, ENVIRONMENT_WEB_NOT_OPTIMIZED, ENVIRONMENT_APP }
		enum ExchangeValueEnum extends Enum[ExchangeValueEnum] { case EXCHANGE_UNSPECIFIED, EXCHANGE_GOOGLE_AD_MANAGER, EXCHANGE_APPNEXUS, EXCHANGE_BRIGHTROLL, EXCHANGE_ADFORM, EXCHANGE_ADMETA, EXCHANGE_ADMIXER, EXCHANGE_ADSMOGO, EXCHANGE_ADSWIZZ, EXCHANGE_BIDSWITCH, EXCHANGE_BRIGHTROLL_DISPLAY, EXCHANGE_CADREON, EXCHANGE_DAILYMOTION, EXCHANGE_FIVE, EXCHANGE_FLUCT, EXCHANGE_FREEWHEEL, EXCHANGE_GENIEE, EXCHANGE_GUMGUM, EXCHANGE_IMOBILE, EXCHANGE_IBILLBOARD, EXCHANGE_IMPROVE_DIGITAL, EXCHANGE_INDEX, EXCHANGE_KARGO, EXCHANGE_MICROAD, EXCHANGE_MOPUB, EXCHANGE_NEND, EXCHANGE_ONE_BY_AOL_DISPLAY, EXCHANGE_ONE_BY_AOL_MOBILE, EXCHANGE_ONE_BY_AOL_VIDEO, EXCHANGE_OOYALA, EXCHANGE_OPENX, EXCHANGE_PERMODO, EXCHANGE_PLATFORMONE, EXCHANGE_PLATFORMID, EXCHANGE_PUBMATIC, EXCHANGE_PULSEPOINT, EXCHANGE_REVENUEMAX, EXCHANGE_RUBICON, EXCHANGE_SMARTCLIP, EXCHANGE_SMARTRTB, EXCHANGE_SMARTSTREAMTV, EXCHANGE_SOVRN, EXCHANGE_SPOTXCHANGE, EXCHANGE_STROER, EXCHANGE_TEADSTV, EXCHANGE_TELARIA, EXCHANGE_TVN, EXCHANGE_UNITED, EXCHANGE_YIELDLAB, EXCHANGE_YIELDMO, EXCHANGE_UNRULYX, EXCHANGE_OPEN8, EXCHANGE_TRITON, EXCHANGE_TRIPLELIFT, EXCHANGE_TABOOLA, EXCHANGE_INMOBI, EXCHANGE_SMAATO, EXCHANGE_AJA, EXCHANGE_SUPERSHIP, EXCHANGE_NEXSTAR_DIGITAL, EXCHANGE_WAZE, EXCHANGE_SOUNDCAST, EXCHANGE_SHARETHROUGH, EXCHANGE_FYBER, EXCHANGE_RED_FOR_PUBLISHERS, EXCHANGE_MEDIANET, EXCHANGE_TAPJOY, EXCHANGE_VISTAR, EXCHANGE_DAX, EXCHANGE_JCD, EXCHANGE_PLACE_EXCHANGE, EXCHANGE_APPLOVIN, EXCHANGE_CONNATIX, EXCHANGE_RESET_DIGITAL, EXCHANGE_HIVESTACK, EXCHANGE_DRAX, EXCHANGE_APPLOVIN_GBID, EXCHANGE_FYBER_GBID, EXCHANGE_UNITY_GBID, EXCHANGE_CHARTBOOST_GBID, EXCHANGE_ADMOST_GBID, EXCHANGE_TOPON_GBID, EXCHANGE_NETFLIX, EXCHANGE_TUBI }
	}
	case class AlgorithmRulesComparisonValue(
	  /** Integer value. */
		int64Value: Option[String] = None,
	  /** Double value. */
		doubleValue: Option[BigDecimal] = None,
	  /** String value. */
		stringValue: Option[String] = None,
	  /** Boolean value. */
		boolValue: Option[Boolean] = None,
	  /** Creative dimension value. */
		creativeDimensionValue: Option[Schema.Dimensions] = None,
	  /** Day and time value. Only `TIME_ZONE_RESOLUTION_END_USER` is supported. */
		dayAndTimeValue: Option[Schema.DayAndTime] = None,
	  /** Device type value. */
		deviceTypeValue: Option[Schema.AlgorithmRulesComparisonValue.DeviceTypeValueEnum] = None,
	  /** Ad position value. */
		onScreenPositionValue: Option[Schema.AlgorithmRulesComparisonValue.OnScreenPositionValueEnum] = None,
	  /** Environment value. */
		environmentValue: Option[Schema.AlgorithmRulesComparisonValue.EnvironmentValueEnum] = None,
	  /** Exchange value. */
		exchangeValue: Option[Schema.AlgorithmRulesComparisonValue.ExchangeValueEnum] = None
	)
	
	object DayAndTime {
		enum DayOfWeekEnum extends Enum[DayOfWeekEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
		enum TimeZoneResolutionEnum extends Enum[TimeZoneResolutionEnum] { case TIME_ZONE_RESOLUTION_UNSPECIFIED, TIME_ZONE_RESOLUTION_END_USER, TIME_ZONE_RESOLUTION_ADVERTISER }
	}
	case class DayAndTime(
	  /** Required. Day of the week. */
		dayOfWeek: Option[Schema.DayAndTime.DayOfWeekEnum] = None,
	  /** Required. Hour of the day. */
		hourOfDay: Option[Int] = None,
	  /** Required. The mechanism used to determine the relevant timezone. */
		timeZoneResolution: Option[Schema.DayAndTime.TimeZoneResolutionEnum] = None
	)
	
	case class AlgorithmRulesSignalValue(
	  /** Value to use as result. */
		number: Option[BigDecimal] = None
	)
}
