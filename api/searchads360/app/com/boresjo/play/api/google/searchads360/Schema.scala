package com.boresjo.play.api.google.searchads360

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object GoogleAdsSearchads360V0Resources__CustomColumn {
		enum ValueTypeEnum extends Enum[ValueTypeEnum] { case UNSPECIFIED, UNKNOWN, STRING, INT64, DOUBLE, BOOLEAN, DATE }
		enum RenderTypeEnum extends Enum[RenderTypeEnum] { case UNSPECIFIED, UNKNOWN, NUMBER, PERCENT, MONEY, STRING, BOOLEAN, DATE }
	}
	case class GoogleAdsSearchads360V0Resources__CustomColumn(
	  /** Immutable. The resource name of the custom column. Custom column resource names have the form: `customers/{customer_id}/customColumns/{custom_column_id}` */
		resourceName: Option[String] = None,
	  /** Output only. ID of the custom column. */
		id: Option[String] = None,
	  /** Output only. User-defined name of the custom column. */
		name: Option[String] = None,
	  /** Output only. User-defined description of the custom column. */
		description: Option[String] = None,
	  /** Output only. The type of the result value of the custom column. */
		valueType: Option[Schema.GoogleAdsSearchads360V0Resources__CustomColumn.ValueTypeEnum] = None,
	  /** Output only. True when the custom column is referring to one or more attributes. */
		referencesAttributes: Option[Boolean] = None,
	  /** Output only. True when the custom column is referring to one or more metrics. */
		referencesMetrics: Option[Boolean] = None,
	  /** Output only. True when the custom column is available to be used in the query of SearchAds360Service.Search and SearchAds360Service.SearchStream. */
		queryable: Option[Boolean] = None,
	  /** Output only. The list of the referenced system columns of this custom column. For example, A custom column "sum of impressions and clicks" has referenced system columns of {"metrics.clicks", "metrics.impressions"}. */
		referencedSystemColumns: Option[List[String]] = None,
	  /** Output only. How the result value of the custom column should be interpreted. */
		renderType: Option[Schema.GoogleAdsSearchads360V0Resources__CustomColumn.RenderTypeEnum] = None
	)
	
	case class GoogleAdsSearchads360V0Services__ListCustomColumnsResponse(
	  /** The CustomColumns owned by the provided customer. */
		customColumns: Option[List[Schema.GoogleAdsSearchads360V0Resources__CustomColumn]] = None
	)
	
	case class GoogleAdsSearchads360V0Services__ListAccessibleCustomersResponse(
	  /** Resource name of customers directly accessible by the user authenticating the call. */
		resourceNames: Option[List[String]] = None
	)
	
	object GoogleAdsSearchads360V0Resources__SearchAds360Field {
		enum CategoryEnum extends Enum[CategoryEnum] { case UNSPECIFIED, UNKNOWN, RESOURCE, ATTRIBUTE, SEGMENT, METRIC }
		enum DataTypeEnum extends Enum[DataTypeEnum] { case UNSPECIFIED, UNKNOWN, BOOLEAN, DATE, DOUBLE, ENUM, FLOAT, INT32, INT64, MESSAGE, RESOURCE_NAME, STRING, UINT64 }
	}
	case class GoogleAdsSearchads360V0Resources__SearchAds360Field(
	  /** Output only. The resource name of the artifact. Artifact resource names have the form: `SearchAds360Fields/{name}` */
		resourceName: Option[String] = None,
	  /** Output only. The name of the artifact. */
		name: Option[String] = None,
	  /** Output only. The category of the artifact. */
		category: Option[Schema.GoogleAdsSearchads360V0Resources__SearchAds360Field.CategoryEnum] = None,
	  /** Output only. Whether the artifact can be used in a SELECT clause in search queries. */
		selectable: Option[Boolean] = None,
	  /** Output only. Whether the artifact can be used in a WHERE clause in search queries. */
		filterable: Option[Boolean] = None,
	  /** Output only. Whether the artifact can be used in a ORDER BY clause in search queries. */
		sortable: Option[Boolean] = None,
	  /** Output only. The names of all resources, segments, and metrics that are selectable with the described artifact. */
		selectableWith: Option[List[String]] = None,
	  /** Output only. The names of all resources that are selectable with the described artifact. Fields from these resources do not segment metrics when included in search queries. This field is only set for artifacts whose category is RESOURCE. */
		attributeResources: Option[List[String]] = None,
	  /** Output only. This field lists the names of all metrics that are selectable with the described artifact when it is used in the FROM clause. It is only set for artifacts whose category is RESOURCE. */
		metrics: Option[List[String]] = None,
	  /** Output only. This field lists the names of all artifacts, whether a segment or another resource, that segment metrics when included in search queries and when the described artifact is used in the FROM clause. It is only set for artifacts whose category is RESOURCE. */
		segments: Option[List[String]] = None,
	  /** Output only. Values the artifact can assume if it is a field of type ENUM. This field is only set for artifacts of category SEGMENT or ATTRIBUTE. */
		enumValues: Option[List[String]] = None,
	  /** Output only. This field determines the operators that can be used with the artifact in WHERE clauses. */
		dataType: Option[Schema.GoogleAdsSearchads360V0Resources__SearchAds360Field.DataTypeEnum] = None,
	  /** Output only. The URL of proto describing the artifact's data type. */
		typeUrl: Option[String] = None,
	  /** Output only. Whether the field artifact is repeated. */
		isRepeated: Option[Boolean] = None
	)
	
	case class GoogleAdsSearchads360V0Services__SearchSearchAds360FieldsRequest(
	  /** Required. The query string. */
		query: Option[String] = None,
	  /** Token of the page to retrieve. If not specified, the first page of results will be returned. Use the value obtained from `next_page_token` in the previous response in order to request the next page of results. */
		pageToken: Option[String] = None,
	  /** Number of elements to retrieve in a single page. When too large a page is requested, the server may decide to further limit the number of returned resources. */
		pageSize: Option[Int] = None
	)
	
	case class GoogleAdsSearchads360V0Services__SearchSearchAds360FieldsResponse(
	  /** The list of fields that matched the query. */
		results: Option[List[Schema.GoogleAdsSearchads360V0Resources__SearchAds360Field]] = None,
	  /** Pagination token used to retrieve the next page of results. Pass the content of this string as the `page_token` attribute of the next request. `next_page_token` is not returned for the last page. */
		nextPageToken: Option[String] = None,
	  /** Total number of results that match the query ignoring the LIMIT clause. */
		totalResultsCount: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Services__SearchSearchAds360Request {
		enum SummaryRowSettingEnum extends Enum[SummaryRowSettingEnum] { case UNSPECIFIED, UNKNOWN, NO_SUMMARY_ROW, SUMMARY_ROW_WITH_RESULTS, SUMMARY_ROW_ONLY }
	}
	case class GoogleAdsSearchads360V0Services__SearchSearchAds360Request(
	  /** Required. The query string. */
		query: Option[String] = None,
	  /** Token of the page to retrieve. If not specified, the first page of results will be returned. Use the value obtained from `next_page_token` in the previous response in order to request the next page of results. */
		pageToken: Option[String] = None,
	  /** Number of elements to retrieve in a single page. When too large a page is requested, the server may decide to further limit the number of returned resources. */
		pageSize: Option[Int] = None,
	  /** If true, the request is validated but not executed. */
		validateOnly: Option[Boolean] = None,
	  /** If true, the total number of results that match the query ignoring the LIMIT clause will be included in the response. Default is false. */
		returnTotalResultsCount: Option[Boolean] = None,
	  /** Determines whether a summary row will be returned. By default, summary row is not returned. If requested, the summary row will be sent in a response by itself after all other query results are returned. */
		summaryRowSetting: Option[Schema.GoogleAdsSearchads360V0Services__SearchSearchAds360Request.SummaryRowSettingEnum] = None
	)
	
	case class GoogleAdsSearchads360V0Services__SearchSearchAds360Response(
	  /** The list of rows that matched the query. */
		results: Option[List[Schema.GoogleAdsSearchads360V0Services__SearchAds360Row]] = None,
	  /** Pagination token used to retrieve the next page of results. Pass the content of this string as the `page_token` attribute of the next request. `next_page_token` is not returned for the last page. */
		nextPageToken: Option[String] = None,
	  /** Total number of results that match the query ignoring the LIMIT clause. */
		totalResultsCount: Option[String] = None,
	  /** FieldMask that represents what fields were requested by the user. */
		fieldMask: Option[String] = None,
	  /** Summary row that contains summary of metrics in results. Summary of metrics means aggregation of metrics across all results, here aggregation could be sum, average, rate, etc. */
		summaryRow: Option[Schema.GoogleAdsSearchads360V0Services__SearchAds360Row] = None,
	  /** The headers of the custom columns in the results. */
		customColumnHeaders: Option[List[Schema.GoogleAdsSearchads360V0Services__CustomColumnHeader]] = None,
	  /** The headers of the conversion custom metrics in the results. */
		conversionCustomMetricHeaders: Option[List[Schema.GoogleAdsSearchads360V0Services__ConversionCustomMetricHeader]] = None,
	  /** The headers of the conversion custom dimensions in the results. */
		conversionCustomDimensionHeaders: Option[List[Schema.GoogleAdsSearchads360V0Services__ConversionCustomDimensionHeader]] = None,
	  /** The headers of the raw event conversion metrics in the results. */
		rawEventConversionMetricHeaders: Option[List[Schema.GoogleAdsSearchads360V0Services__RawEventConversionMetricHeader]] = None,
	  /** The headers of the raw event conversion dimensions in the results. */
		rawEventConversionDimensionHeaders: Option[List[Schema.GoogleAdsSearchads360V0Services__RawEventConversionDimensionHeader]] = None
	)
	
	case class GoogleAdsSearchads360V0Services__SearchAds360Row(
	  /** The ad group referenced in the query. */
		adGroup: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroup] = None,
	  /** The ad referenced in the query. */
		adGroupAd: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroupAd] = None,
	  /** The ad group ad effective label referenced in the query. */
		adGroupAdEffectiveLabel: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroupAdEffectiveLabel] = None,
	  /** The ad group ad label referenced in the query. */
		adGroupAdLabel: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroupAdLabel] = None,
	  /** The ad group asset referenced in the query. */
		adGroupAsset: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroupAsset] = None,
	  /** The ad group asset set referenced in the query. */
		adGroupAssetSet: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroupAssetSet] = None,
	  /** The ad group audience view referenced in the query. */
		adGroupAudienceView: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroupAudienceView] = None,
	  /** The bid modifier referenced in the query. */
		adGroupBidModifier: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroupBidModifier] = None,
	  /** The criterion referenced in the query. */
		adGroupCriterion: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroupCriterion] = None,
	  /** The ad group criterion effective label referenced in the query. */
		adGroupCriterionEffectiveLabel: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroupCriterionEffectiveLabel] = None,
	  /** The ad group criterion label referenced in the query. */
		adGroupCriterionLabel: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroupCriterionLabel] = None,
	  /** The ad group effective label referenced in the query. */
		adGroupEffectiveLabel: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroupEffectiveLabel] = None,
	  /** The ad group label referenced in the query. */
		adGroupLabel: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroupLabel] = None,
	  /** The age range view referenced in the query. */
		ageRangeView: Option[Schema.GoogleAdsSearchads360V0Resources__AgeRangeView] = None,
	  /** The asset referenced in the query. */
		asset: Option[Schema.GoogleAdsSearchads360V0Resources__Asset] = None,
	  /** The asset group asset referenced in the query. */
		assetGroupAsset: Option[Schema.GoogleAdsSearchads360V0Resources__AssetGroupAsset] = None,
	  /** The asset group signal referenced in the query. */
		assetGroupSignal: Option[Schema.GoogleAdsSearchads360V0Resources__AssetGroupSignal] = None,
	  /** The asset group listing group filter referenced in the query. */
		assetGroupListingGroupFilter: Option[Schema.GoogleAdsSearchads360V0Resources__AssetGroupListingGroupFilter] = None,
	  /** The asset group top combination view referenced in the query. */
		assetGroupTopCombinationView: Option[Schema.GoogleAdsSearchads360V0Resources__AssetGroupTopCombinationView] = None,
	  /** The asset group referenced in the query. */
		assetGroup: Option[Schema.GoogleAdsSearchads360V0Resources__AssetGroup] = None,
	  /** The asset set asset referenced in the query. */
		assetSetAsset: Option[Schema.GoogleAdsSearchads360V0Resources__AssetSetAsset] = None,
	  /** The asset set referenced in the query. */
		assetSet: Option[Schema.GoogleAdsSearchads360V0Resources__AssetSet] = None,
	  /** The bidding strategy referenced in the query. */
		biddingStrategy: Option[Schema.GoogleAdsSearchads360V0Resources__BiddingStrategy] = None,
	  /** The campaign budget referenced in the query. */
		campaignBudget: Option[Schema.GoogleAdsSearchads360V0Resources__CampaignBudget] = None,
	  /** The campaign referenced in the query. */
		campaign: Option[Schema.GoogleAdsSearchads360V0Resources__Campaign] = None,
	  /** The campaign asset referenced in the query. */
		campaignAsset: Option[Schema.GoogleAdsSearchads360V0Resources__CampaignAsset] = None,
	  /** The campaign asset set referenced in the query. */
		campaignAssetSet: Option[Schema.GoogleAdsSearchads360V0Resources__CampaignAssetSet] = None,
	  /** The campaign audience view referenced in the query. */
		campaignAudienceView: Option[Schema.GoogleAdsSearchads360V0Resources__CampaignAudienceView] = None,
	  /** The campaign criterion referenced in the query. */
		campaignCriterion: Option[Schema.GoogleAdsSearchads360V0Resources__CampaignCriterion] = None,
	  /** The campaign effective label referenced in the query. */
		campaignEffectiveLabel: Option[Schema.GoogleAdsSearchads360V0Resources__CampaignEffectiveLabel] = None,
	  /** The campaign label referenced in the query. */
		campaignLabel: Option[Schema.GoogleAdsSearchads360V0Resources__CampaignLabel] = None,
	  /** The cart data sales view referenced in the query. */
		cartDataSalesView: Option[Schema.GoogleAdsSearchads360V0Resources__CartDataSalesView] = None,
	  /** The Audience referenced in the query. */
		audience: Option[Schema.GoogleAdsSearchads360V0Resources__Audience] = None,
	  /** The conversion action referenced in the query. */
		conversionAction: Option[Schema.GoogleAdsSearchads360V0Resources__ConversionAction] = None,
	  /** The conversion custom variable referenced in the query. */
		conversionCustomVariable: Option[Schema.GoogleAdsSearchads360V0Resources__ConversionCustomVariable] = None,
	  /** The customer referenced in the query. */
		customer: Option[Schema.GoogleAdsSearchads360V0Resources__Customer] = None,
	  /** The customer asset referenced in the query. */
		customerAsset: Option[Schema.GoogleAdsSearchads360V0Resources__CustomerAsset] = None,
	  /** The customer asset set referenced in the query. */
		customerAssetSet: Option[Schema.GoogleAdsSearchads360V0Resources__CustomerAssetSet] = None,
	  /** The accessible bidding strategy referenced in the query. */
		accessibleBiddingStrategy: Option[Schema.GoogleAdsSearchads360V0Resources__AccessibleBiddingStrategy] = None,
	  /** The CustomerManagerLink referenced in the query. */
		customerManagerLink: Option[Schema.GoogleAdsSearchads360V0Resources__CustomerManagerLink] = None,
	  /** The CustomerClient referenced in the query. */
		customerClient: Option[Schema.GoogleAdsSearchads360V0Resources__CustomerClient] = None,
	  /** The dynamic search ads search term view referenced in the query. */
		dynamicSearchAdsSearchTermView: Option[Schema.GoogleAdsSearchads360V0Resources__DynamicSearchAdsSearchTermView] = None,
	  /** The gender view referenced in the query. */
		genderView: Option[Schema.GoogleAdsSearchads360V0Resources__GenderView] = None,
	  /** The geo target constant referenced in the query. */
		geoTargetConstant: Option[Schema.GoogleAdsSearchads360V0Resources__GeoTargetConstant] = None,
	  /** The keyword view referenced in the query. */
		keywordView: Option[Schema.GoogleAdsSearchads360V0Resources__KeywordView] = None,
	  /** The label referenced in the query. */
		label: Option[Schema.GoogleAdsSearchads360V0Resources__Label] = None,
	  /** The language constant referenced in the query. */
		languageConstant: Option[Schema.GoogleAdsSearchads360V0Resources__LanguageConstant] = None,
	  /** The location view referenced in the query. */
		locationView: Option[Schema.GoogleAdsSearchads360V0Resources__LocationView] = None,
	  /** The Product Bidding Category referenced in the query. */
		productBiddingCategoryConstant: Option[Schema.GoogleAdsSearchads360V0Resources__ProductBiddingCategoryConstant] = None,
	  /** The product group view referenced in the query. */
		productGroupView: Option[Schema.GoogleAdsSearchads360V0Resources__ProductGroupView] = None,
	  /** The shopping performance view referenced in the query. */
		shoppingPerformanceView: Option[Schema.GoogleAdsSearchads360V0Resources__ShoppingPerformanceView] = None,
	  /** The user list referenced in the query. */
		userList: Option[Schema.GoogleAdsSearchads360V0Resources__UserList] = None,
	  /** The user location view referenced in the query. */
		userLocationView: Option[Schema.GoogleAdsSearchads360V0Resources__UserLocationView] = None,
	  /** The webpage view referenced in the query. */
		webpageView: Option[Schema.GoogleAdsSearchads360V0Resources__WebpageView] = None,
	  /** The event level visit referenced in the query. */
		visit: Option[Schema.GoogleAdsSearchads360V0Resources__Visit] = None,
	  /** The event level conversion referenced in the query. */
		conversion: Option[Schema.GoogleAdsSearchads360V0Resources__Conversion] = None,
	  /** The metrics. */
		metrics: Option[Schema.GoogleAdsSearchads360V0Common__Metrics] = None,
	  /** The segments. */
		segments: Option[Schema.GoogleAdsSearchads360V0Common__Segments] = None,
	  /** The custom columns. */
		customColumns: Option[List[Schema.GoogleAdsSearchads360V0Common__Value]] = None
	)
	
	object GoogleAdsSearchads360V0Resources__AdGroup {
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, PAUSED, REMOVED }
		enum TypeEnum extends Enum[TypeEnum] { case UNSPECIFIED, UNKNOWN, SEARCH_STANDARD, DISPLAY_STANDARD, SHOPPING_PRODUCT_ADS, SHOPPING_SHOWCASE_ADS, HOTEL_ADS, SHOPPING_SMART_ADS, VIDEO_BUMPER, VIDEO_TRUE_VIEW_IN_STREAM, VIDEO_TRUE_VIEW_IN_DISPLAY, VIDEO_NON_SKIPPABLE_IN_STREAM, VIDEO_OUTSTREAM, SEARCH_DYNAMIC_ADS, SHOPPING_COMPARISON_LISTING_ADS, PROMOTED_HOTEL_ADS, VIDEO_RESPONSIVE, VIDEO_EFFICIENT_REACH, SMART_CAMPAIGN_ADS, TRAVEL_ADS }
		enum AdRotationModeEnum extends Enum[AdRotationModeEnum] { case UNSPECIFIED, UNKNOWN, OPTIMIZE, ROTATE_FOREVER }
		enum EngineStatusEnum extends Enum[EngineStatusEnum] { case UNSPECIFIED, UNKNOWN, AD_GROUP_ELIGIBLE, AD_GROUP_EXPIRED, AD_GROUP_REMOVED, AD_GROUP_DRAFT, AD_GROUP_PAUSED, AD_GROUP_SERVING, AD_GROUP_SUBMITTED, CAMPAIGN_PAUSED, ACCOUNT_PAUSED }
	}
	case class GoogleAdsSearchads360V0Resources__AdGroup(
	  /** Immutable. The resource name of the ad group. Ad group resource names have the form: `customers/{customer_id}/adGroups/{ad_group_id}` */
		resourceName: Option[String] = None,
	  /** Output only. The ID of the ad group. */
		id: Option[String] = None,
	  /** The name of the ad group. This field is required and should not be empty when creating new ad groups. It must contain fewer than 255 UTF-8 full-width characters. It must not contain any null (code point 0x0), NL line feed (code point 0xA) or carriage return (code point 0xD) characters. */
		name: Option[String] = None,
	  /** The status of the ad group. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroup.StatusEnum] = None,
	  /** Immutable. The type of the ad group. */
		`type`: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroup.TypeEnum] = None,
	  /** The ad rotation mode of the ad group. */
		adRotationMode: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroup.AdRotationModeEnum] = None,
	  /** The maximum CPC (cost-per-click) bid. */
		cpcBidMicros: Option[String] = None,
	  /** Output only. The timestamp when this ad_group was created. The timestamp is in the customer's time zone and in "yyyy-MM-dd HH:mm:ss" format. */
		creationTime: Option[String] = None,
	  /** Output only. The Engine Status for ad group. */
		engineStatus: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroup.EngineStatusEnum] = None,
	  /** Setting for targeting related features. */
		targetingSetting: Option[Schema.GoogleAdsSearchads360V0Common__TargetingSetting] = None,
	  /** Output only. The resource names of labels attached to this ad group. */
		labels: Option[List[String]] = None,
	  /** Output only. The resource names of effective labels attached to this ad group. An effective label is a label inherited or directly assigned to this ad group. */
		effectiveLabels: Option[List[String]] = None,
	  /** Output only. ID of the ad group in the external engine account. This field is for non-Google Ads account only, for example, Yahoo Japan, Microsoft, Baidu etc. For Google Ads entity, use "ad_group.id" instead. */
		engineId: Option[String] = None,
	  /** Output only. Date when this ad group starts serving ads. By default, the ad group starts now or the ad group's start date, whichever is later. If this field is set, then the ad group starts at the beginning of the specified date in the customer's time zone. This field is only available for Microsoft Advertising and Facebook gateway accounts. Format: YYYY-MM-DD Example: 2019-03-14 */
		startDate: Option[String] = None,
	  /** Output only. Date when the ad group ends serving ads. By default, the ad group ends on the ad group's end date. If this field is set, then the ad group ends at the end of the specified date in the customer's time zone. This field is only available for Microsoft Advertising and Facebook gateway accounts. Format: YYYY-MM-DD Example: 2019-03-14 */
		endDate: Option[String] = None,
	  /** Output only. The language of the ads and keywords in an ad group. This field is only available for Microsoft Advertising accounts. More details: https://docs.microsoft.com/en-us/advertising/guides/ad-languages?view=bingads-13#adlanguage */
		languageCode: Option[String] = None,
	  /** Output only. The datetime when this ad group was last modified. The datetime is in the customer's time zone and in "yyyy-MM-dd HH:mm:ss.ssssss" format. */
		lastModifiedTime: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Common__TargetingSetting(
	  /** The per-targeting-dimension setting to restrict the reach of your campaign or ad group. */
		targetRestrictions: Option[List[Schema.GoogleAdsSearchads360V0Common__TargetRestriction]] = None
	)
	
	object GoogleAdsSearchads360V0Common__TargetRestriction {
		enum TargetingDimensionEnum extends Enum[TargetingDimensionEnum] { case UNSPECIFIED, UNKNOWN, KEYWORD, AUDIENCE, TOPIC, GENDER, AGE_RANGE, PLACEMENT, PARENTAL_STATUS, INCOME_RANGE }
	}
	case class GoogleAdsSearchads360V0Common__TargetRestriction(
	  /** The targeting dimension that these settings apply to. */
		targetingDimension: Option[Schema.GoogleAdsSearchads360V0Common__TargetRestriction.TargetingDimensionEnum] = None,
	  /** Indicates whether to restrict your ads to show only for the criteria you have selected for this targeting_dimension, or to target all values for this targeting_dimension and show ads based on your targeting in other TargetingDimensions. A value of `true` means that these criteria will only apply bid modifiers, and not affect targeting. A value of `false` means that these criteria will restrict targeting as well as applying bid modifiers. */
		bidOnly: Option[Boolean] = None
	)
	
	object GoogleAdsSearchads360V0Resources__AdGroupAd {
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, PAUSED, REMOVED }
		enum EngineStatusEnum extends Enum[EngineStatusEnum] { case UNSPECIFIED, UNKNOWN, AD_GROUP_AD_ELIGIBLE, AD_GROUP_AD_INAPPROPRIATE_FOR_CAMPAIGN, AD_GROUP_AD_MOBILE_URL_UNDER_REVIEW, AD_GROUP_AD_PARTIALLY_INVALID, AD_GROUP_AD_TO_BE_ACTIVATED, AD_GROUP_AD_NOT_REVIEWED, AD_GROUP_AD_ON_HOLD, AD_GROUP_AD_PAUSED, AD_GROUP_AD_REMOVED, AD_GROUP_AD_PENDING_REVIEW, AD_GROUP_AD_UNDER_REVIEW, AD_GROUP_AD_APPROVED, AD_GROUP_AD_DISAPPROVED, AD_GROUP_AD_SERVING, AD_GROUP_AD_ACCOUNT_PAUSED, AD_GROUP_AD_CAMPAIGN_PAUSED, AD_GROUP_AD_AD_GROUP_PAUSED }
	}
	case class GoogleAdsSearchads360V0Resources__AdGroupAd(
	  /** Immutable. The resource name of the ad. Ad group ad resource names have the form: `customers/{customer_id}/adGroupAds/{ad_group_id}~{ad_id}` */
		resourceName: Option[String] = None,
	  /** The status of the ad. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroupAd.StatusEnum] = None,
	  /** Immutable. The ad. */
		ad: Option[Schema.GoogleAdsSearchads360V0Resources__Ad] = None,
	  /** Output only. The timestamp when this ad_group_ad was created. The datetime is in the customer's time zone and in "yyyy-MM-dd HH:mm:ss.ssssss" format. */
		creationTime: Option[String] = None,
	  /** Output only. The resource names of labels attached to this ad group ad. */
		labels: Option[List[String]] = None,
	  /** Output only. The resource names of effective labels attached to this ad. An effective label is a label inherited or directly assigned to this ad. */
		effectiveLabels: Option[List[String]] = None,
	  /** Output only. ID of the ad in the external engine account. This field is for Search Ads 360 account only, for example, Yahoo Japan, Microsoft, Baidu etc. For non-Search Ads 360 entity, use "ad_group_ad.ad.id" instead. */
		engineId: Option[String] = None,
	  /** Output only. Additional status of the ad in the external engine account. Possible statuses (depending on the type of external account) include active, eligible, pending review, etc. */
		engineStatus: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroupAd.EngineStatusEnum] = None,
	  /** Output only. The datetime when this ad group ad was last modified. The datetime is in the customer's time zone and in "yyyy-MM-dd HH:mm:ss.ssssss" format. */
		lastModifiedTime: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources__Ad {
		enum TypeEnum extends Enum[TypeEnum] { case UNSPECIFIED, UNKNOWN, TEXT_AD, EXPANDED_TEXT_AD, CALL_ONLY_AD, EXPANDED_DYNAMIC_SEARCH_AD, HOTEL_AD, SHOPPING_SMART_AD, SHOPPING_PRODUCT_AD, VIDEO_AD, GMAIL_AD, IMAGE_AD, RESPONSIVE_SEARCH_AD, LEGACY_RESPONSIVE_DISPLAY_AD, APP_AD, LEGACY_APP_INSTALL_AD, RESPONSIVE_DISPLAY_AD, LOCAL_AD, HTML5_UPLOAD_AD, DYNAMIC_HTML5_AD, APP_ENGAGEMENT_AD, SHOPPING_COMPARISON_LISTING_AD, VIDEO_BUMPER_AD, VIDEO_NON_SKIPPABLE_IN_STREAM_AD, VIDEO_OUTSTREAM_AD, VIDEO_TRUEVIEW_DISCOVERY_AD, VIDEO_TRUEVIEW_IN_STREAM_AD, VIDEO_RESPONSIVE_AD, SMART_CAMPAIGN_AD, APP_PRE_REGISTRATION_AD, DISCOVERY_MULTI_ASSET_AD, DISCOVERY_CAROUSEL_AD, TRAVEL_AD, DISCOVERY_VIDEO_RESPONSIVE_AD, MULTIMEDIA_AD }
	}
	case class GoogleAdsSearchads360V0Resources__Ad(
	  /** Immutable. The resource name of the ad. Ad resource names have the form: `customers/{customer_id}/ads/{ad_id}` */
		resourceName: Option[String] = None,
	  /** Output only. The ID of the ad. */
		id: Option[String] = None,
	  /** The list of possible final URLs after all cross-domain redirects for the ad. */
		finalUrls: Option[List[String]] = None,
	  /** The URL that appears in the ad description for some ad formats. */
		displayUrl: Option[String] = None,
	  /** Output only. The type of ad. */
		`type`: Option[Schema.GoogleAdsSearchads360V0Resources__Ad.TypeEnum] = None,
	  /** Immutable. The name of the ad. This is only used to be able to identify the ad. It does not need to be unique and does not affect the served ad. The name field is currently only supported for DisplayUploadAd, ImageAd, ShoppingComparisonListingAd and VideoAd. */
		name: Option[String] = None,
	  /** Immutable. Details pertaining to a text ad. */
		textAd: Option[Schema.GoogleAdsSearchads360V0Common__SearchAds360TextAdInfo] = None,
	  /** Immutable. Details pertaining to an expanded text ad. */
		expandedTextAd: Option[Schema.GoogleAdsSearchads360V0Common__SearchAds360ExpandedTextAdInfo] = None,
	  /** Immutable. Details pertaining to a responsive search ad. */
		responsiveSearchAd: Option[Schema.GoogleAdsSearchads360V0Common__SearchAds360ResponsiveSearchAdInfo] = None,
	  /** Immutable. Details pertaining to a product ad. */
		productAd: Option[Schema.GoogleAdsSearchads360V0Common__SearchAds360ProductAdInfo] = None,
	  /** Immutable. Details pertaining to an expanded dynamic search ad. */
		expandedDynamicSearchAd: Option[Schema.GoogleAdsSearchads360V0Common__SearchAds360ExpandedDynamicSearchAdInfo] = None
	)
	
	case class GoogleAdsSearchads360V0Common__SearchAds360TextAdInfo(
	  /** The headline of the ad. */
		headline: Option[String] = None,
	  /** The first line of the ad's description. */
		description1: Option[String] = None,
	  /** The second line of the ad's description. */
		description2: Option[String] = None,
	  /** The displayed URL of the ad. */
		displayUrl: Option[String] = None,
	  /** The displayed mobile URL of the ad. */
		displayMobileUrl: Option[String] = None,
	  /** The tracking id of the ad. */
		adTrackingId: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Common__SearchAds360ExpandedTextAdInfo(
	  /** The headline of the ad. */
		headline: Option[String] = None,
	  /** The second headline of the ad. */
		headline2: Option[String] = None,
	  /** The third headline of the ad. */
		headline3: Option[String] = None,
	  /** The first line of the ad's description. */
		description1: Option[String] = None,
	  /** The second line of the ad's description. */
		description2: Option[String] = None,
	  /** Text appended to the auto-generated visible URL with a delimiter. */
		path1: Option[String] = None,
	  /** Text appended to path1 with a delimiter. */
		path2: Option[String] = None,
	  /** The tracking id of the ad. */
		adTrackingId: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Common__SearchAds360ResponsiveSearchAdInfo(
	  /** Text appended to the auto-generated visible URL with a delimiter. */
		path1: Option[String] = None,
	  /** Text appended to path1 with a delimiter. */
		path2: Option[String] = None,
	  /** The tracking id of the ad. */
		adTrackingId: Option[String] = None,
	  /** List of text assets for headlines. When the ad serves the headlines will be selected from this list. */
		headlines: Option[List[Schema.GoogleAdsSearchads360V0Common__AdTextAsset]] = None,
	  /** List of text assets for descriptions. When the ad serves the descriptions will be selected from this list. */
		descriptions: Option[List[Schema.GoogleAdsSearchads360V0Common__AdTextAsset]] = None
	)
	
	case class GoogleAdsSearchads360V0Common__AdTextAsset(
	  /** Asset text. */
		text: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Common__SearchAds360ProductAdInfo(
	
	)
	
	case class GoogleAdsSearchads360V0Common__SearchAds360ExpandedDynamicSearchAdInfo(
	  /** The first line of the ad's description. */
		description1: Option[String] = None,
	  /** The second line of the ad's description. */
		description2: Option[String] = None,
	  /** The tracking id of the ad. */
		adTrackingId: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__AdGroupAdEffectiveLabel(
	  /** Immutable. The resource name of the ad group ad effective label. Ad group ad effective label resource names have the form: `customers/{customer_id}/adGroupAdEffectiveLabels/{ad_group_id}~{ad_id}~{label_id}` */
		resourceName: Option[String] = None,
	  /** Immutable. The ad group ad to which the effective label is attached. */
		adGroupAd: Option[String] = None,
	  /** Immutable. The effective label assigned to the ad group ad. */
		label: Option[String] = None,
	  /** Output only. The ID of the Customer which owns the effective label. */
		ownerCustomerId: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__AdGroupAdLabel(
	  /** Immutable. The resource name of the ad group ad label. Ad group ad label resource names have the form: `customers/{customer_id}/adGroupAdLabels/{ad_group_id}~{ad_id}~{label_id}` */
		resourceName: Option[String] = None,
	  /** Immutable. The ad group ad to which the label is attached. */
		adGroupAd: Option[String] = None,
	  /** Immutable. The label assigned to the ad group ad. */
		label: Option[String] = None,
	  /** Output only. The ID of the Customer which owns the label. */
		ownerCustomerId: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources__AdGroupAsset {
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, REMOVED, PAUSED }
	}
	case class GoogleAdsSearchads360V0Resources__AdGroupAsset(
	  /** Immutable. The resource name of the ad group asset. AdGroupAsset resource names have the form: `customers/{customer_id}/adGroupAssets/{ad_group_id}~{asset_id}~{field_type}` */
		resourceName: Option[String] = None,
	  /** Required. Immutable. The ad group to which the asset is linked. */
		adGroup: Option[String] = None,
	  /** Required. Immutable. The asset which is linked to the ad group. */
		asset: Option[String] = None,
	  /** Status of the ad group asset. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroupAsset.StatusEnum] = None
	)
	
	object GoogleAdsSearchads360V0Resources__AdGroupAssetSet {
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, REMOVED }
	}
	case class GoogleAdsSearchads360V0Resources__AdGroupAssetSet(
	  /** Immutable. The resource name of the ad group asset set. Ad group asset set resource names have the form: `customers/{customer_id}/adGroupAssetSets/{ad_group_id}~{asset_set_id}` */
		resourceName: Option[String] = None,
	  /** Immutable. The ad group to which this asset set is linked. */
		adGroup: Option[String] = None,
	  /** Immutable. The asset set which is linked to the ad group. */
		assetSet: Option[String] = None,
	  /** Output only. The status of the ad group asset set. Read-only. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroupAssetSet.StatusEnum] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__AdGroupAudienceView(
	  /** Output only. The resource name of the ad group audience view. Ad group audience view resource names have the form: `customers/{customer_id}/adGroupAudienceViews/{ad_group_id}~{criterion_id}` */
		resourceName: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__AdGroupBidModifier(
	  /** Immutable. The resource name of the ad group bid modifier. Ad group bid modifier resource names have the form: `customers/{customer_id}/adGroupBidModifiers/{ad_group_id}~{criterion_id}` */
		resourceName: Option[String] = None,
	  /** The modifier for the bid when the criterion matches. The modifier must be in the range: 0.1 - 10.0. The range is 1.0 - 6.0 for PreferredContent. Use 0 to opt out of a Device type. */
		bidModifier: Option[BigDecimal] = None,
	  /** Immutable. A device criterion. */
		device: Option[Schema.GoogleAdsSearchads360V0Common__DeviceInfo] = None
	)
	
	object GoogleAdsSearchads360V0Common__DeviceInfo {
		enum TypeEnum extends Enum[TypeEnum] { case UNSPECIFIED, UNKNOWN, MOBILE, TABLET, DESKTOP, CONNECTED_TV, OTHER }
	}
	case class GoogleAdsSearchads360V0Common__DeviceInfo(
	  /** Type of the device. */
		`type`: Option[Schema.GoogleAdsSearchads360V0Common__DeviceInfo.TypeEnum] = None
	)
	
	object GoogleAdsSearchads360V0Resources__AdGroupCriterion {
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, PAUSED, REMOVED }
		enum TypeEnum extends Enum[TypeEnum] { case UNSPECIFIED, UNKNOWN, KEYWORD, PLACEMENT, MOBILE_APP_CATEGORY, MOBILE_APPLICATION, DEVICE, LOCATION, LISTING_GROUP, AD_SCHEDULE, AGE_RANGE, GENDER, INCOME_RANGE, PARENTAL_STATUS, YOUTUBE_VIDEO, YOUTUBE_CHANNEL, USER_LIST, PROXIMITY, TOPIC, LISTING_SCOPE, LANGUAGE, IP_BLOCK, CONTENT_LABEL, CARRIER, USER_INTEREST, WEBPAGE, OPERATING_SYSTEM_VERSION, APP_PAYMENT_MODEL, MOBILE_DEVICE, CUSTOM_AFFINITY, CUSTOM_INTENT, LOCATION_GROUP, CUSTOM_AUDIENCE, COMBINED_AUDIENCE, KEYWORD_THEME, AUDIENCE, LOCAL_SERVICE_ID, BRAND, BRAND_LIST, LIFE_EVENT }
		enum EngineStatusEnum extends Enum[EngineStatusEnum] { case UNSPECIFIED, UNKNOWN, AD_GROUP_CRITERION_ELIGIBLE, AD_GROUP_CRITERION_INAPPROPRIATE_FOR_CAMPAIGN, AD_GROUP_CRITERION_INVALID_MOBILE_SEARCH, AD_GROUP_CRITERION_INVALID_PC_SEARCH, AD_GROUP_CRITERION_INVALID_SEARCH, AD_GROUP_CRITERION_LOW_SEARCH_VOLUME, AD_GROUP_CRITERION_MOBILE_URL_UNDER_REVIEW, AD_GROUP_CRITERION_PARTIALLY_INVALID, AD_GROUP_CRITERION_TO_BE_ACTIVATED, AD_GROUP_CRITERION_UNDER_REVIEW, AD_GROUP_CRITERION_NOT_REVIEWED, AD_GROUP_CRITERION_ON_HOLD, AD_GROUP_CRITERION_PENDING_REVIEW, AD_GROUP_CRITERION_PAUSED, AD_GROUP_CRITERION_REMOVED, AD_GROUP_CRITERION_APPROVED, AD_GROUP_CRITERION_DISAPPROVED, AD_GROUP_CRITERION_SERVING, AD_GROUP_CRITERION_ACCOUNT_PAUSED }
	}
	case class GoogleAdsSearchads360V0Resources__AdGroupCriterion(
	  /** Immutable. The resource name of the ad group criterion. Ad group criterion resource names have the form: `customers/{customer_id}/adGroupCriteria/{ad_group_id}~{criterion_id}` */
		resourceName: Option[String] = None,
	  /** Output only. The ID of the criterion. */
		criterionId: Option[String] = None,
	  /** Output only. The timestamp when this ad group criterion was created. The timestamp is in the customer's time zone and in "yyyy-MM-dd HH:mm:ss" format. */
		creationTime: Option[String] = None,
	  /** The status of the criterion. This is the status of the ad group criterion entity, set by the client. Note: UI reports may incorporate additional information that affects whether a criterion is eligible to run. In some cases a criterion that's REMOVED in the API can still show as enabled in the UI. For example, campaigns by default show to users of all age ranges unless excluded. The UI will show each age range as "enabled", since they're eligible to see the ads; but AdGroupCriterion.status will show "removed", since no positive criterion was added. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroupCriterion.StatusEnum] = None,
	  /** Output only. Information regarding the quality of the criterion. */
		qualityInfo: Option[Schema.GoogleAdsSearchads360V0Resources_AdGroupCriterion_QualityInfo] = None,
	  /** Immutable. The ad group to which the criterion belongs. */
		adGroup: Option[String] = None,
	  /** Output only. The type of the criterion. */
		`type`: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroupCriterion.TypeEnum] = None,
	  /** Immutable. Whether to target (`false`) or exclude (`true`) the criterion. This field is immutable. To switch a criterion from positive to negative, remove then re-add it. */
		negative: Option[Boolean] = None,
	  /** Output only. The resource names of labels attached to this ad group criterion. */
		labels: Option[List[String]] = None,
	  /** Output only. The resource names of effective labels attached to this ad group criterion. An effective label is a label inherited or directly assigned to this ad group criterion. */
		effectiveLabels: Option[List[String]] = None,
	  /** The modifier for the bid when the criterion matches. The modifier must be in the range: 0.1 - 10.0. Most targetable criteria types support modifiers. */
		bidModifier: Option[BigDecimal] = None,
	  /** The CPC (cost-per-click) bid. */
		cpcBidMicros: Option[String] = None,
	  /** Output only. The effective CPC (cost-per-click) bid. */
		effectiveCpcBidMicros: Option[String] = None,
	  /** Output only. Estimates for criterion bids at various positions. */
		positionEstimates: Option[Schema.GoogleAdsSearchads360V0Resources_AdGroupCriterion_PositionEstimates] = None,
	  /** The list of possible final URLs after all cross-domain redirects for the ad. */
		finalUrls: Option[List[String]] = None,
	  /** Output only. The Engine Status for ad group criterion. */
		engineStatus: Option[Schema.GoogleAdsSearchads360V0Resources__AdGroupCriterion.EngineStatusEnum] = None,
	  /** URL template for appending params to final URL. */
		finalUrlSuffix: Option[String] = None,
	  /** The URL template for constructing a tracking URL. */
		trackingUrlTemplate: Option[String] = None,
	  /** Output only. ID of the ad group criterion in the external engine account. This field is for non-Google Ads account only, for example, Yahoo Japan, Microsoft, Baidu etc. For Google Ads entity, use "ad_group_criterion.criterion_id" instead. */
		engineId: Option[String] = None,
	  /** Output only. The datetime when this ad group criterion was last modified. The datetime is in the customer's time zone and in "yyyy-MM-dd HH:mm:ss.ssssss" format. */
		lastModifiedTime: Option[String] = None,
	  /** Immutable. Keyword. */
		keyword: Option[Schema.GoogleAdsSearchads360V0Common__KeywordInfo] = None,
	  /** Immutable. Listing group. */
		listingGroup: Option[Schema.GoogleAdsSearchads360V0Common__ListingGroupInfo] = None,
	  /** Immutable. Age range. */
		ageRange: Option[Schema.GoogleAdsSearchads360V0Common__AgeRangeInfo] = None,
	  /** Immutable. Gender. */
		gender: Option[Schema.GoogleAdsSearchads360V0Common__GenderInfo] = None,
	  /** Immutable. User List. */
		userList: Option[Schema.GoogleAdsSearchads360V0Common__UserListInfo] = None,
	  /** Immutable. Webpage */
		webpage: Option[Schema.GoogleAdsSearchads360V0Common__WebpageInfo] = None,
	  /** Immutable. Location. */
		location: Option[Schema.GoogleAdsSearchads360V0Common__LocationInfo] = None
	)
	
	case class GoogleAdsSearchads360V0Resources_AdGroupCriterion_QualityInfo(
	  /** Output only. The quality score. This field may not be populated if Google does not have enough information to determine a value. */
		qualityScore: Option[Int] = None
	)
	
	case class GoogleAdsSearchads360V0Resources_AdGroupCriterion_PositionEstimates(
	  /** Output only. The estimate of the CPC bid required for ad to be displayed at the top of the first page of search results. */
		topOfPageCpcMicros: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Common__KeywordInfo {
		enum MatchTypeEnum extends Enum[MatchTypeEnum] { case UNSPECIFIED, UNKNOWN, EXACT, PHRASE, BROAD }
	}
	case class GoogleAdsSearchads360V0Common__KeywordInfo(
	  /** The text of the keyword (at most 80 characters and 10 words). */
		text: Option[String] = None,
	  /** The match type of the keyword. */
		matchType: Option[Schema.GoogleAdsSearchads360V0Common__KeywordInfo.MatchTypeEnum] = None
	)
	
	object GoogleAdsSearchads360V0Common__ListingGroupInfo {
		enum TypeEnum extends Enum[TypeEnum] { case UNSPECIFIED, UNKNOWN, SUBDIVISION, UNIT }
	}
	case class GoogleAdsSearchads360V0Common__ListingGroupInfo(
	  /** Type of the listing group. */
		`type`: Option[Schema.GoogleAdsSearchads360V0Common__ListingGroupInfo.TypeEnum] = None
	)
	
	object GoogleAdsSearchads360V0Common__AgeRangeInfo {
		enum TypeEnum extends Enum[TypeEnum] { case UNSPECIFIED, UNKNOWN, AGE_RANGE_18_24, AGE_RANGE_25_34, AGE_RANGE_35_44, AGE_RANGE_45_54, AGE_RANGE_55_64, AGE_RANGE_65_UP, AGE_RANGE_UNDETERMINED }
	}
	case class GoogleAdsSearchads360V0Common__AgeRangeInfo(
	  /** Type of the age range. */
		`type`: Option[Schema.GoogleAdsSearchads360V0Common__AgeRangeInfo.TypeEnum] = None
	)
	
	object GoogleAdsSearchads360V0Common__GenderInfo {
		enum TypeEnum extends Enum[TypeEnum] { case UNSPECIFIED, UNKNOWN, MALE, FEMALE, UNDETERMINED }
	}
	case class GoogleAdsSearchads360V0Common__GenderInfo(
	  /** Type of the gender. */
		`type`: Option[Schema.GoogleAdsSearchads360V0Common__GenderInfo.TypeEnum] = None
	)
	
	case class GoogleAdsSearchads360V0Common__UserListInfo(
	  /** The User List resource name. */
		userList: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Common__WebpageInfo(
	  /** The name of the criterion that is defined by this parameter. The name value will be used for identifying, sorting and filtering criteria with this type of parameters. This field is required for CREATE operations and is prohibited on UPDATE operations. */
		criterionName: Option[String] = None,
	  /** Conditions, or logical expressions, for webpage targeting. The list of webpage targeting conditions are and-ed together when evaluated for targeting. An empty list of conditions indicates all pages of the campaign's website are targeted. This field is required for CREATE operations and is prohibited on UPDATE operations. */
		conditions: Option[List[Schema.GoogleAdsSearchads360V0Common__WebpageConditionInfo]] = None,
	  /** Website criteria coverage percentage. This is the computed percentage of website coverage based on the website target, negative website target and negative keywords in the ad group and campaign. For instance, when coverage returns as 1, it indicates it has 100% coverage. This field is read-only. */
		coveragePercentage: Option[BigDecimal] = None
	)
	
	object GoogleAdsSearchads360V0Common__WebpageConditionInfo {
		enum OperandEnum extends Enum[OperandEnum] { case UNSPECIFIED, UNKNOWN, URL, CATEGORY, PAGE_TITLE, PAGE_CONTENT, CUSTOM_LABEL }
		enum OperatorEnum extends Enum[OperatorEnum] { case UNSPECIFIED, UNKNOWN, EQUALS, CONTAINS }
	}
	case class GoogleAdsSearchads360V0Common__WebpageConditionInfo(
	  /** Operand of webpage targeting condition. */
		operand: Option[Schema.GoogleAdsSearchads360V0Common__WebpageConditionInfo.OperandEnum] = None,
	  /** Operator of webpage targeting condition. */
		operator: Option[Schema.GoogleAdsSearchads360V0Common__WebpageConditionInfo.OperatorEnum] = None,
	  /** Argument of webpage targeting condition. */
		argument: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Common__LocationInfo(
	  /** The geo target constant resource name. */
		geoTargetConstant: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__AdGroupCriterionEffectiveLabel(
	  /** Immutable. The resource name of the ad group criterion effective label. Ad group criterion effective label resource names have the form: `customers/{customer_id}/adGroupCriterionEffectiveLabels/{ad_group_id}~{criterion_id}~{label_id}` */
		resourceName: Option[String] = None,
	  /** Immutable. The ad group criterion to which the effective label is attached. */
		adGroupCriterion: Option[String] = None,
	  /** Immutable. The effective label assigned to the ad group criterion. */
		label: Option[String] = None,
	  /** Output only. The ID of the Customer which owns the effective label. */
		ownerCustomerId: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__AdGroupCriterionLabel(
	  /** Immutable. The resource name of the ad group criterion label. Ad group criterion label resource names have the form: `customers/{customer_id}/adGroupCriterionLabels/{ad_group_id}~{criterion_id}~{label_id}` */
		resourceName: Option[String] = None,
	  /** Immutable. The ad group criterion to which the label is attached. */
		adGroupCriterion: Option[String] = None,
	  /** Immutable. The label assigned to the ad group criterion. */
		label: Option[String] = None,
	  /** Output only. The ID of the Customer which owns the label. */
		ownerCustomerId: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__AdGroupEffectiveLabel(
	  /** Immutable. The resource name of the ad group effective label. Ad group effective label resource names have the form: `customers/{customer_id}/adGroupEffectiveLabels/{ad_group_id}~{label_id}` */
		resourceName: Option[String] = None,
	  /** Immutable. The ad group to which the effective label is attached. */
		adGroup: Option[String] = None,
	  /** Immutable. The effective label assigned to the ad group. */
		label: Option[String] = None,
	  /** Output only. The ID of the Customer which owns the effective label. */
		ownerCustomerId: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__AdGroupLabel(
	  /** Immutable. The resource name of the ad group label. Ad group label resource names have the form: `customers/{customer_id}/adGroupLabels/{ad_group_id}~{label_id}` */
		resourceName: Option[String] = None,
	  /** Immutable. The ad group to which the label is attached. */
		adGroup: Option[String] = None,
	  /** Immutable. The label assigned to the ad group. */
		label: Option[String] = None,
	  /** Output only. The ID of the Customer which owns the label. */
		ownerCustomerId: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__AgeRangeView(
	  /** Output only. The resource name of the age range view. Age range view resource names have the form: `customers/{customer_id}/ageRangeViews/{ad_group_id}~{criterion_id}` */
		resourceName: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources__Asset {
		enum TypeEnum extends Enum[TypeEnum] { case UNSPECIFIED, UNKNOWN, YOUTUBE_VIDEO, MEDIA_BUNDLE, IMAGE, TEXT, LEAD_FORM, BOOK_ON_GOOGLE, PROMOTION, CALLOUT, STRUCTURED_SNIPPET, SITELINK, PAGE_FEED, DYNAMIC_EDUCATION, MOBILE_APP, HOTEL_CALLOUT, CALL, PRICE, CALL_TO_ACTION, DYNAMIC_REAL_ESTATE, DYNAMIC_CUSTOM, DYNAMIC_HOTELS_AND_RENTALS, DYNAMIC_FLIGHTS, DISCOVERY_CAROUSEL_CARD, DYNAMIC_TRAVEL, DYNAMIC_LOCAL, DYNAMIC_JOBS, LOCATION, HOTEL_PROPERTY }
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, REMOVED, ARCHIVED, PENDING_SYSTEM_GENERATED }
		enum EngineStatusEnum extends Enum[EngineStatusEnum] { case UNSPECIFIED, UNKNOWN, SERVING, SERVING_LIMITED, DISAPPROVED, DISABLED, REMOVED }
	}
	case class GoogleAdsSearchads360V0Resources__Asset(
	  /** Immutable. The resource name of the asset. Asset resource names have the form: `customers/{customer_id}/assets/{asset_id}` */
		resourceName: Option[String] = None,
	  /** Output only. The ID of the asset. */
		id: Option[String] = None,
	  /** Optional name of the asset. */
		name: Option[String] = None,
	  /** Output only. Type of the asset. */
		`type`: Option[Schema.GoogleAdsSearchads360V0Resources__Asset.TypeEnum] = None,
	  /** A list of possible final URLs after all cross domain redirects. */
		finalUrls: Option[List[String]] = None,
	  /** URL template for constructing a tracking URL. */
		trackingUrlTemplate: Option[String] = None,
	  /** Output only. The status of the asset. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__Asset.StatusEnum] = None,
	  /** Output only. The timestamp when this asset was created. The timestamp is in the customer's time zone and in "yyyy-MM-dd HH:mm:ss" format. */
		creationTime: Option[String] = None,
	  /** Output only. The datetime when this asset was last modified. The datetime is in the customer's time zone and in "yyyy-MM-dd HH:mm:ss.ssssss" format. */
		lastModifiedTime: Option[String] = None,
	  /** Output only. The Engine Status for an asset. */
		engineStatus: Option[Schema.GoogleAdsSearchads360V0Resources__Asset.EngineStatusEnum] = None,
	  /** Immutable. A YouTube video asset. */
		youtubeVideoAsset: Option[Schema.GoogleAdsSearchads360V0Common__YoutubeVideoAsset] = None,
	  /** Output only. An image asset. */
		imageAsset: Option[Schema.GoogleAdsSearchads360V0Common__ImageAsset] = None,
	  /** Output only. A text asset. */
		textAsset: Option[Schema.GoogleAdsSearchads360V0Common__TextAsset] = None,
	  /** Output only. A unified callout asset. */
		calloutAsset: Option[Schema.GoogleAdsSearchads360V0Common__UnifiedCalloutAsset] = None,
	  /** Output only. A unified sitelink asset. */
		sitelinkAsset: Option[Schema.GoogleAdsSearchads360V0Common__UnifiedSitelinkAsset] = None,
	  /** Output only. A unified page feed asset. */
		pageFeedAsset: Option[Schema.GoogleAdsSearchads360V0Common__UnifiedPageFeedAsset] = None,
	  /** A mobile app asset. */
		mobileAppAsset: Option[Schema.GoogleAdsSearchads360V0Common__MobileAppAsset] = None,
	  /** Output only. A unified call asset. */
		callAsset: Option[Schema.GoogleAdsSearchads360V0Common__UnifiedCallAsset] = None,
	  /** Immutable. A call to action asset. */
		callToActionAsset: Option[Schema.GoogleAdsSearchads360V0Common__CallToActionAsset] = None,
	  /** Output only. A unified location asset. */
		locationAsset: Option[Schema.GoogleAdsSearchads360V0Common__UnifiedLocationAsset] = None
	)
	
	case class GoogleAdsSearchads360V0Common__YoutubeVideoAsset(
	  /** YouTube video id. This is the 11 character string value used in the YouTube video URL. */
		youtubeVideoId: Option[String] = None,
	  /** YouTube video title. */
		youtubeVideoTitle: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Common__ImageAsset {
		enum MimeTypeEnum extends Enum[MimeTypeEnum] { case UNSPECIFIED, UNKNOWN, IMAGE_JPEG, IMAGE_GIF, IMAGE_PNG, FLASH, TEXT_HTML, PDF, MSWORD, MSEXCEL, RTF, AUDIO_WAV, AUDIO_MP3, HTML5_AD_ZIP }
	}
	case class GoogleAdsSearchads360V0Common__ImageAsset(
	  /** File size of the image asset in bytes. */
		fileSize: Option[String] = None,
	  /** MIME type of the image asset. */
		mimeType: Option[Schema.GoogleAdsSearchads360V0Common__ImageAsset.MimeTypeEnum] = None,
	  /** Metadata for this image at its original size. */
		fullSize: Option[Schema.GoogleAdsSearchads360V0Common__ImageDimension] = None
	)
	
	case class GoogleAdsSearchads360V0Common__ImageDimension(
	  /** Height of the image. */
		heightPixels: Option[String] = None,
	  /** Width of the image. */
		widthPixels: Option[String] = None,
	  /** A URL that returns the image with this height and width. */
		url: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Common__TextAsset(
	  /** Text content of the text asset. */
		text: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Common__UnifiedCalloutAsset(
	  /** The callout text. The length of this string should be between 1 and 25, inclusive. */
		calloutText: Option[String] = None,
	  /** Start date of when this asset is effective and can begin serving, in yyyy-MM-dd format. */
		startDate: Option[String] = None,
	  /** Last date of when this asset is effective and still serving, in yyyy-MM-dd format. */
		endDate: Option[String] = None,
	  /** List of non-overlapping schedules specifying all time intervals for which the asset may serve. There can be a maximum of 6 schedules per day, 42 in total. */
		adScheduleTargets: Option[List[Schema.GoogleAdsSearchads360V0Common__AdScheduleInfo]] = None,
	  /** Whether to show the asset in search user's time zone. Applies to Microsoft Ads. */
		useSearcherTimeZone: Option[Boolean] = None
	)
	
	object GoogleAdsSearchads360V0Common__AdScheduleInfo {
		enum StartMinuteEnum extends Enum[StartMinuteEnum] { case UNSPECIFIED, UNKNOWN, ZERO, FIFTEEN, THIRTY, FORTY_FIVE }
		enum EndMinuteEnum extends Enum[EndMinuteEnum] { case UNSPECIFIED, UNKNOWN, ZERO, FIFTEEN, THIRTY, FORTY_FIVE }
		enum DayOfWeekEnum extends Enum[DayOfWeekEnum] { case UNSPECIFIED, UNKNOWN, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class GoogleAdsSearchads360V0Common__AdScheduleInfo(
	  /** Minutes after the start hour at which this schedule starts. This field is required for CREATE operations and is prohibited on UPDATE operations. */
		startMinute: Option[Schema.GoogleAdsSearchads360V0Common__AdScheduleInfo.StartMinuteEnum] = None,
	  /** Minutes after the end hour at which this schedule ends. The schedule is exclusive of the end minute. This field is required for CREATE operations and is prohibited on UPDATE operations. */
		endMinute: Option[Schema.GoogleAdsSearchads360V0Common__AdScheduleInfo.EndMinuteEnum] = None,
	  /** Starting hour in 24 hour time. This field must be between 0 and 23, inclusive. This field is required for CREATE operations and is prohibited on UPDATE operations. */
		startHour: Option[Int] = None,
	  /** Ending hour in 24 hour time; 24 signifies end of the day. This field must be between 0 and 24, inclusive. This field is required for CREATE operations and is prohibited on UPDATE operations. */
		endHour: Option[Int] = None,
	  /** Day of the week the schedule applies to. This field is required for CREATE operations and is prohibited on UPDATE operations. */
		dayOfWeek: Option[Schema.GoogleAdsSearchads360V0Common__AdScheduleInfo.DayOfWeekEnum] = None
	)
	
	case class GoogleAdsSearchads360V0Common__UnifiedSitelinkAsset(
	  /** URL display text for the sitelink. The length of this string should be between 1 and 25, inclusive. */
		linkText: Option[String] = None,
	  /** First line of the description for the sitelink. If set, the length should be between 1 and 35, inclusive, and description2 must also be set. */
		description1: Option[String] = None,
	  /** Second line of the description for the sitelink. If set, the length should be between 1 and 35, inclusive, and description1 must also be set. */
		description2: Option[String] = None,
	  /** Start date of when this asset is effective and can begin serving, in yyyy-MM-dd format. */
		startDate: Option[String] = None,
	  /** Last date of when this asset is effective and still serving, in yyyy-MM-dd format. */
		endDate: Option[String] = None,
	  /** List of non-overlapping schedules specifying all time intervals for which the asset may serve. There can be a maximum of 6 schedules per day, 42 in total. */
		adScheduleTargets: Option[List[Schema.GoogleAdsSearchads360V0Common__AdScheduleInfo]] = None,
	  /** ID used for tracking clicks for the sitelink asset. This is a Yahoo! Japan only field. */
		trackingId: Option[String] = None,
	  /** Whether to show the sitelink asset in search user's time zone. Applies to Microsoft Ads. */
		useSearcherTimeZone: Option[Boolean] = None,
	  /** Whether the preference is for the sitelink asset to be displayed on mobile devices. Applies to Microsoft Ads. */
		mobilePreferred: Option[Boolean] = None
	)
	
	case class GoogleAdsSearchads360V0Common__UnifiedPageFeedAsset(
	  /** The webpage that advertisers want to target. */
		pageUrl: Option[String] = None,
	  /** Labels used to group the page urls. */
		labels: Option[List[String]] = None
	)
	
	object GoogleAdsSearchads360V0Common__MobileAppAsset {
		enum AppStoreEnum extends Enum[AppStoreEnum] { case UNSPECIFIED, UNKNOWN, APPLE_APP_STORE, GOOGLE_APP_STORE }
	}
	case class GoogleAdsSearchads360V0Common__MobileAppAsset(
	  /** Required. A string that uniquely identifies a mobile application. It should just contain the platform native id, like "com.android.ebay" for Android or "12345689" for iOS. */
		appId: Option[String] = None,
	  /** Required. The application store that distributes this specific app. */
		appStore: Option[Schema.GoogleAdsSearchads360V0Common__MobileAppAsset.AppStoreEnum] = None
	)
	
	object GoogleAdsSearchads360V0Common__UnifiedCallAsset {
		enum CallConversionReportingStateEnum extends Enum[CallConversionReportingStateEnum] { case UNSPECIFIED, UNKNOWN, DISABLED, USE_ACCOUNT_LEVEL_CALL_CONVERSION_ACTION, USE_RESOURCE_LEVEL_CALL_CONVERSION_ACTION }
	}
	case class GoogleAdsSearchads360V0Common__UnifiedCallAsset(
	  /** Two-letter country code of the phone number. Examples: 'US', 'us'. */
		countryCode: Option[String] = None,
	  /** The advertiser's raw phone number. Examples: '1234567890', '(123)456-7890' */
		phoneNumber: Option[String] = None,
	  /** Output only. Indicates whether this CallAsset should use its own call conversion setting, follow the account level setting, or disable call conversion. */
		callConversionReportingState: Option[Schema.GoogleAdsSearchads360V0Common__UnifiedCallAsset.CallConversionReportingStateEnum] = None,
	  /** The conversion action to attribute a call conversion to. If not set, the default conversion action is used. This field only has effect if call_conversion_reporting_state is set to USE_RESOURCE_LEVEL_CALL_CONVERSION_ACTION. */
		callConversionAction: Option[String] = None,
	  /** List of non-overlapping schedules specifying all time intervals for which the asset may serve. There can be a maximum of 6 schedules per day, 42 in total. */
		adScheduleTargets: Option[List[Schema.GoogleAdsSearchads360V0Common__AdScheduleInfo]] = None,
	  /** Whether the call only shows the phone number without a link to the website. Applies to Microsoft Ads. */
		callOnly: Option[Boolean] = None,
	  /** Whether the call should be enabled on call tracking. Applies to Microsoft Ads. */
		callTrackingEnabled: Option[Boolean] = None,
	  /** Whether to show the call extension in search user's time zone. Applies to Microsoft Ads. */
		useSearcherTimeZone: Option[Boolean] = None,
	  /** Start date of when this asset is effective and can begin serving, in yyyy-MM-dd format. */
		startDate: Option[String] = None,
	  /** Last date of when this asset is effective and still serving, in yyyy-MM-dd format. */
		endDate: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Common__CallToActionAsset {
		enum CallToActionEnum extends Enum[CallToActionEnum] { case UNSPECIFIED, UNKNOWN, LEARN_MORE, GET_QUOTE, APPLY_NOW, SIGN_UP, CONTACT_US, SUBSCRIBE, DOWNLOAD, BOOK_NOW, SHOP_NOW, BUY_NOW, DONATE_NOW, ORDER_NOW, PLAY_NOW, SEE_MORE, START_NOW, VISIT_SITE, WATCH_NOW }
	}
	case class GoogleAdsSearchads360V0Common__CallToActionAsset(
	  /** Call to action. */
		callToAction: Option[Schema.GoogleAdsSearchads360V0Common__CallToActionAsset.CallToActionEnum] = None
	)
	
	object GoogleAdsSearchads360V0Common__UnifiedLocationAsset {
		enum LocationOwnershipTypeEnum extends Enum[LocationOwnershipTypeEnum] { case UNSPECIFIED, UNKNOWN, BUSINESS_OWNER, AFFILIATE }
	}
	case class GoogleAdsSearchads360V0Common__UnifiedLocationAsset(
	  /** Place IDs uniquely identify a place in the Google Places database and on Google Maps. This field is unique for a given customer ID and asset type. See https://developers.google.com/places/web-service/place-id to learn more about Place ID. */
		placeId: Option[String] = None,
	  /** The list of business locations for the customer. This will only be returned if the Location Asset is syncing from the Business Profile account. It is possible to have multiple Business Profile listings under the same account that point to the same Place ID. */
		businessProfileLocations: Option[List[Schema.GoogleAdsSearchads360V0Common__BusinessProfileLocation]] = None,
	  /** The type of location ownership. If the type is BUSINESS_OWNER, it will be served as a location extension. If the type is AFFILIATE, it will be served as an affiliate location. */
		locationOwnershipType: Option[Schema.GoogleAdsSearchads360V0Common__UnifiedLocationAsset.LocationOwnershipTypeEnum] = None
	)
	
	case class GoogleAdsSearchads360V0Common__BusinessProfileLocation(
	  /** Advertiser specified label for the location on the Business Profile account. This is synced from the Business Profile account. */
		labels: Option[List[String]] = None,
	  /** Business Profile store code of this location. This is synced from the Business Profile account. */
		storeCode: Option[String] = None,
	  /** Listing ID of this Business Profile location. This is synced from the linked Business Profile account. */
		listingId: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources__AssetGroupAsset {
		enum FieldTypeEnum extends Enum[FieldTypeEnum] { case UNSPECIFIED, UNKNOWN, HEADLINE, DESCRIPTION, MANDATORY_AD_TEXT, MARKETING_IMAGE, MEDIA_BUNDLE, YOUTUBE_VIDEO, BOOK_ON_GOOGLE, LEAD_FORM, PROMOTION, CALLOUT, STRUCTURED_SNIPPET, SITELINK, MOBILE_APP, HOTEL_CALLOUT, CALL, PRICE, LONG_HEADLINE, BUSINESS_NAME, SQUARE_MARKETING_IMAGE, PORTRAIT_MARKETING_IMAGE, LOGO, LANDSCAPE_LOGO, VIDEO, CALL_TO_ACTION_SELECTION, AD_IMAGE, BUSINESS_LOGO, HOTEL_PROPERTY, DISCOVERY_CAROUSEL_CARD }
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, REMOVED, PAUSED }
	}
	case class GoogleAdsSearchads360V0Resources__AssetGroupAsset(
	  /** Immutable. The resource name of the asset group asset. Asset group asset resource name have the form: `customers/{customer_id}/assetGroupAssets/{asset_group_id}~{asset_id}~{field_type}` */
		resourceName: Option[String] = None,
	  /** Immutable. The asset group which this asset group asset is linking. */
		assetGroup: Option[String] = None,
	  /** Immutable. The asset which this asset group asset is linking. */
		asset: Option[String] = None,
	  /** The description of the placement of the asset within the asset group. For example: HEADLINE, YOUTUBE_VIDEO etc */
		fieldType: Option[Schema.GoogleAdsSearchads360V0Resources__AssetGroupAsset.FieldTypeEnum] = None,
	  /** The status of the link between an asset and asset group. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__AssetGroupAsset.StatusEnum] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__AssetGroupSignal(
	  /** Immutable. The resource name of the asset group signal. Asset group signal resource name have the form: `customers/{customer_id}/assetGroupSignals/{asset_group_id}~{signal_id}` */
		resourceName: Option[String] = None,
	  /** Immutable. The asset group which this asset group signal belongs to. */
		assetGroup: Option[String] = None,
	  /** Immutable. The audience signal to be used by the performance max campaign. */
		audience: Option[Schema.GoogleAdsSearchads360V0Common__AudienceInfo] = None
	)
	
	case class GoogleAdsSearchads360V0Common__AudienceInfo(
	  /** The Audience resource name. */
		audience: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources__AssetGroupListingGroupFilter {
		enum TypeEnum extends Enum[TypeEnum] { case UNSPECIFIED, UNKNOWN, SUBDIVISION, UNIT_INCLUDED, UNIT_EXCLUDED }
		enum VerticalEnum extends Enum[VerticalEnum] { case UNSPECIFIED, UNKNOWN, SHOPPING }
	}
	case class GoogleAdsSearchads360V0Resources__AssetGroupListingGroupFilter(
	  /** Immutable. The resource name of the asset group listing group filter. Asset group listing group filter resource name have the form: `customers/{customer_id}/assetGroupListingGroupFilters/{asset_group_id}~{listing_group_filter_id}` */
		resourceName: Option[String] = None,
	  /** Immutable. The asset group which this asset group listing group filter is part of. */
		assetGroup: Option[String] = None,
	  /** Output only. The ID of the ListingGroupFilter. */
		id: Option[String] = None,
	  /** Immutable. Type of a listing group filter node. */
		`type`: Option[Schema.GoogleAdsSearchads360V0Resources__AssetGroupListingGroupFilter.TypeEnum] = None,
	  /** Immutable. The vertical the current node tree represents. All nodes in the same tree must belong to the same vertical. */
		vertical: Option[Schema.GoogleAdsSearchads360V0Resources__AssetGroupListingGroupFilter.VerticalEnum] = None,
	  /** Dimension value with which this listing group is refining its parent. Undefined for the root group. */
		caseValue: Option[Schema.GoogleAdsSearchads360V0Resources__ListingGroupFilterDimension] = None,
	  /** Immutable. Resource name of the parent listing group subdivision. Null for the root listing group filter node. */
		parentListingGroupFilter: Option[String] = None,
	  /** Output only. The path of dimensions defining this listing group filter. */
		path: Option[Schema.GoogleAdsSearchads360V0Resources__ListingGroupFilterDimensionPath] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__ListingGroupFilterDimension(
	  /** Bidding category of a product offer. */
		productBiddingCategory: Option[Schema.GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductBiddingCategory] = None,
	  /** Brand of a product offer. */
		productBrand: Option[Schema.GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductBrand] = None,
	  /** Locality of a product offer. */
		productChannel: Option[Schema.GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductChannel] = None,
	  /** Condition of a product offer. */
		productCondition: Option[Schema.GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductCondition] = None,
	  /** Custom attribute of a product offer. */
		productCustomAttribute: Option[Schema.GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductCustomAttribute] = None,
	  /** Item id of a product offer. */
		productItemId: Option[Schema.GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductItemId] = None,
	  /** Type of a product offer. */
		productType: Option[Schema.GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductType] = None
	)
	
	object GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductBiddingCategory {
		enum LevelEnum extends Enum[LevelEnum] { case UNSPECIFIED, UNKNOWN, LEVEL1, LEVEL2, LEVEL3, LEVEL4, LEVEL5 }
	}
	case class GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductBiddingCategory(
	  /** ID of the product bidding category. This ID is equivalent to the google_product_category ID as described in this article: https://support.google.com/merchants/answer/6324436 */
		id: Option[String] = None,
	  /** Indicates the level of the category in the taxonomy. */
		level: Option[Schema.GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductBiddingCategory.LevelEnum] = None
	)
	
	case class GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductBrand(
	  /** String value of the product brand. */
		value: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductChannel {
		enum ChannelEnum extends Enum[ChannelEnum] { case UNSPECIFIED, UNKNOWN, ONLINE, LOCAL }
	}
	case class GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductChannel(
	  /** Value of the locality. */
		channel: Option[Schema.GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductChannel.ChannelEnum] = None
	)
	
	object GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductCondition {
		enum ConditionEnum extends Enum[ConditionEnum] { case UNSPECIFIED, UNKNOWN, NEW, REFURBISHED, USED }
	}
	case class GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductCondition(
	  /** Value of the condition. */
		condition: Option[Schema.GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductCondition.ConditionEnum] = None
	)
	
	object GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductCustomAttribute {
		enum IndexEnum extends Enum[IndexEnum] { case UNSPECIFIED, UNKNOWN, INDEX0, INDEX1, INDEX2, INDEX3, INDEX4 }
	}
	case class GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductCustomAttribute(
	  /** String value of the product custom attribute. */
		value: Option[String] = None,
	  /** Indicates the index of the custom attribute. */
		index: Option[Schema.GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductCustomAttribute.IndexEnum] = None
	)
	
	case class GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductItemId(
	  /** Value of the id. */
		value: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductType {
		enum LevelEnum extends Enum[LevelEnum] { case UNSPECIFIED, UNKNOWN, LEVEL1, LEVEL2, LEVEL3, LEVEL4, LEVEL5 }
	}
	case class GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductType(
	  /** Value of the type. */
		value: Option[String] = None,
	  /** Level of the type. */
		level: Option[Schema.GoogleAdsSearchads360V0Resources_ListingGroupFilterDimension_ProductType.LevelEnum] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__ListingGroupFilterDimensionPath(
	  /** Output only. The complete path of dimensions through the listing group filter hierarchy (excluding the root node) to this listing group filter. */
		dimensions: Option[List[Schema.GoogleAdsSearchads360V0Resources__ListingGroupFilterDimension]] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__AssetGroupTopCombinationView(
	  /** Output only. The resource name of the asset group top combination view. AssetGroup Top Combination view resource names have the form: `"customers/{customer_id}/assetGroupTopCombinationViews/{asset_group_id}~{asset_combination_category}" */
		resourceName: Option[String] = None,
	  /** Output only. The top combinations of assets that served together. */
		assetGroupTopCombinations: Option[List[Schema.GoogleAdsSearchads360V0Resources__AssetGroupAssetCombinationData]] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__AssetGroupAssetCombinationData(
	  /** Output only. Served assets. */
		assetCombinationServedAssets: Option[List[Schema.GoogleAdsSearchads360V0Common__AssetUsage]] = None
	)
	
	object GoogleAdsSearchads360V0Common__AssetUsage {
		enum ServedAssetFieldTypeEnum extends Enum[ServedAssetFieldTypeEnum] { case UNSPECIFIED, UNKNOWN, HEADLINE_1, HEADLINE_2, HEADLINE_3, DESCRIPTION_1, DESCRIPTION_2, HEADLINE, HEADLINE_IN_PORTRAIT, LONG_HEADLINE, DESCRIPTION, DESCRIPTION_IN_PORTRAIT, BUSINESS_NAME_IN_PORTRAIT, BUSINESS_NAME, MARKETING_IMAGE, MARKETING_IMAGE_IN_PORTRAIT, SQUARE_MARKETING_IMAGE, PORTRAIT_MARKETING_IMAGE, LOGO, LANDSCAPE_LOGO, CALL_TO_ACTION, YOU_TUBE_VIDEO, SITELINK, CALL, MOBILE_APP, CALLOUT, STRUCTURED_SNIPPET, PRICE, PROMOTION, AD_IMAGE, LEAD_FORM, BUSINESS_LOGO }
	}
	case class GoogleAdsSearchads360V0Common__AssetUsage(
	  /** Resource name of the asset. */
		asset: Option[String] = None,
	  /** The served field type of the asset. */
		servedAssetFieldType: Option[Schema.GoogleAdsSearchads360V0Common__AssetUsage.ServedAssetFieldTypeEnum] = None
	)
	
	object GoogleAdsSearchads360V0Resources__AssetGroup {
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, PAUSED, REMOVED }
		enum AdStrengthEnum extends Enum[AdStrengthEnum] { case UNSPECIFIED, UNKNOWN, PENDING, NO_ADS, POOR, AVERAGE, GOOD, EXCELLENT }
	}
	case class GoogleAdsSearchads360V0Resources__AssetGroup(
	  /** Immutable. The resource name of the asset group. Asset group resource names have the form: `customers/{customer_id}/assetGroups/{asset_group_id}` */
		resourceName: Option[String] = None,
	  /** Output only. The ID of the asset group. */
		id: Option[String] = None,
	  /** Immutable. The campaign with which this asset group is associated. The asset which is linked to the asset group. */
		campaign: Option[String] = None,
	  /** Required. Name of the asset group. Required. It must have a minimum length of 1 and maximum length of 128. It must be unique under a campaign. */
		name: Option[String] = None,
	  /** A list of final URLs after all cross domain redirects. In performance max, by default, the urls are eligible for expansion unless opted out. */
		finalUrls: Option[List[String]] = None,
	  /** A list of final mobile URLs after all cross domain redirects. In performance max, by default, the urls are eligible for expansion unless opted out. */
		finalMobileUrls: Option[List[String]] = None,
	  /** The status of the asset group. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__AssetGroup.StatusEnum] = None,
	  /** First part of text that may appear appended to the url displayed in the ad. */
		path1: Option[String] = None,
	  /** Second part of text that may appear appended to the url displayed in the ad. This field can only be set when path1 is set. */
		path2: Option[String] = None,
	  /** Output only. Overall ad strength of this asset group. */
		adStrength: Option[Schema.GoogleAdsSearchads360V0Resources__AssetGroup.AdStrengthEnum] = None
	)
	
	object GoogleAdsSearchads360V0Resources__AssetSetAsset {
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, REMOVED }
	}
	case class GoogleAdsSearchads360V0Resources__AssetSetAsset(
	  /** Immutable. The resource name of the asset set asset. Asset set asset resource names have the form: `customers/{customer_id}/assetSetAssets/{asset_set_id}~{asset_id}` */
		resourceName: Option[String] = None,
	  /** Immutable. The asset set which this asset set asset is linking to. */
		assetSet: Option[String] = None,
	  /** Immutable. The asset which this asset set asset is linking to. */
		asset: Option[String] = None,
	  /** Output only. The status of the asset set asset. Read-only. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__AssetSetAsset.StatusEnum] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__AssetSet(
	  /** Output only. The ID of the asset set. */
		id: Option[String] = None,
	  /** Immutable. The resource name of the asset set. Asset set resource names have the form: `customers/{customer_id}/assetSets/{asset_set_id}` */
		resourceName: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources__BiddingStrategy {
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, REMOVED }
		enum TypeEnum extends Enum[TypeEnum] { case UNSPECIFIED, UNKNOWN, COMMISSION, ENHANCED_CPC, INVALID, MANUAL_CPA, MANUAL_CPC, MANUAL_CPM, MANUAL_CPV, MAXIMIZE_CONVERSIONS, MAXIMIZE_CONVERSION_VALUE, PAGE_ONE_PROMOTED, PERCENT_CPC, TARGET_CPA, TARGET_CPM, TARGET_IMPRESSION_SHARE, TARGET_OUTRANK_SHARE, TARGET_ROAS, TARGET_SPEND }
	}
	case class GoogleAdsSearchads360V0Resources__BiddingStrategy(
	  /** Immutable. The resource name of the bidding strategy. Bidding strategy resource names have the form: `customers/{customer_id}/biddingStrategies/{bidding_strategy_id}` */
		resourceName: Option[String] = None,
	  /** Output only. The ID of the bidding strategy. */
		id: Option[String] = None,
	  /** The name of the bidding strategy. All bidding strategies within an account must be named distinctly. The length of this string should be between 1 and 255, inclusive, in UTF-8 bytes, (trimmed). */
		name: Option[String] = None,
	  /** Output only. The status of the bidding strategy. This field is read-only. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__BiddingStrategy.StatusEnum] = None,
	  /** Output only. The type of the bidding strategy. Create a bidding strategy by setting the bidding scheme. This field is read-only. */
		`type`: Option[Schema.GoogleAdsSearchads360V0Resources__BiddingStrategy.TypeEnum] = None,
	  /** Immutable. The currency used by the bidding strategy (ISO 4217 three-letter code). For bidding strategies in manager customers, this currency can be set on creation and defaults to the manager customer's currency. For serving customers, this field cannot be set; all strategies in a serving customer implicitly use the serving customer's currency. In all cases the effective_currency_code field returns the currency used by the strategy. */
		currencyCode: Option[String] = None,
	  /** Output only. The currency used by the bidding strategy (ISO 4217 three-letter code). For bidding strategies in manager customers, this is the currency set by the advertiser when creating the strategy. For serving customers, this is the customer's currency_code. Bidding strategy metrics are reported in this currency. This field is read-only. */
		effectiveCurrencyCode: Option[String] = None,
	  /** Output only. The number of campaigns attached to this bidding strategy. This field is read-only. */
		campaignCount: Option[String] = None,
	  /** Output only. The number of non-removed campaigns attached to this bidding strategy. This field is read-only. */
		nonRemovedCampaignCount: Option[String] = None,
	  /** A bidding strategy that raises bids for clicks that seem more likely to lead to a conversion and lowers them for clicks where they seem less likely. */
		enhancedCpc: Option[Schema.GoogleAdsSearchads360V0Common__EnhancedCpc] = None,
	  /** An automated bidding strategy to help get the most conversion value for your campaigns while spending your budget. */
		maximizeConversionValue: Option[Schema.GoogleAdsSearchads360V0Common__MaximizeConversionValue] = None,
	  /** An automated bidding strategy to help get the most conversions for your campaigns while spending your budget. */
		maximizeConversions: Option[Schema.GoogleAdsSearchads360V0Common__MaximizeConversions] = None,
	  /** A bidding strategy that sets bids to help get as many conversions as possible at the target cost-per-acquisition (CPA) you set. */
		targetCpa: Option[Schema.GoogleAdsSearchads360V0Common__TargetCpa] = None,
	  /** A bidding strategy that automatically optimizes towards a chosen percentage of impressions. */
		targetImpressionShare: Option[Schema.GoogleAdsSearchads360V0Common__TargetImpressionShare] = None,
	  /** A bidding strategy that sets bids based on the target fraction of auctions where the advertiser should outrank a specific competitor. This field is deprecated. Creating a new bidding strategy with this field or attaching bidding strategies with this field to a campaign will fail. Mutates to strategies that already have this scheme populated are allowed. */
		targetOutrankShare: Option[Schema.GoogleAdsSearchads360V0Common__TargetOutrankShare] = None,
	  /** A bidding strategy that helps you maximize revenue while averaging a specific target Return On Ad Spend (ROAS). */
		targetRoas: Option[Schema.GoogleAdsSearchads360V0Common__TargetRoas] = None,
	  /** A bid strategy that sets your bids to help get as many clicks as possible within your budget. */
		targetSpend: Option[Schema.GoogleAdsSearchads360V0Common__TargetSpend] = None
	)
	
	case class GoogleAdsSearchads360V0Common__EnhancedCpc(
	
	)
	
	case class GoogleAdsSearchads360V0Common__MaximizeConversionValue(
	  /** The target return on ad spend (ROAS) option. If set, the bid strategy will maximize revenue while averaging the target return on ad spend. If the target ROAS is high, the bid strategy may not be able to spend the full budget. If the target ROAS is not set, the bid strategy will aim to achieve the highest possible ROAS for the budget. */
		targetRoas: Option[BigDecimal] = None,
	  /** Maximum bid limit that can be set by the bid strategy. The limit applies to all keywords managed by the strategy. Mutable for portfolio bidding strategies only. */
		cpcBidCeilingMicros: Option[String] = None,
	  /** Minimum bid limit that can be set by the bid strategy. The limit applies to all keywords managed by the strategy. Mutable for portfolio bidding strategies only. */
		cpcBidFloorMicros: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Common__MaximizeConversions(
	  /** Maximum bid limit that can be set by the bid strategy. The limit applies to all keywords managed by the strategy. Mutable for portfolio bidding strategies only. */
		cpcBidCeilingMicros: Option[String] = None,
	  /** Minimum bid limit that can be set by the bid strategy. The limit applies to all keywords managed by the strategy. Mutable for portfolio bidding strategies only. */
		cpcBidFloorMicros: Option[String] = None,
	  /** The target cost-per-action (CPA) option. This is the average amount that you would like to spend per conversion action specified in micro units of the bidding strategy's currency. If set, the bid strategy will get as many conversions as possible at or below the target cost-per-action. If the target CPA is not set, the bid strategy will aim to achieve the lowest possible CPA given the budget. */
		targetCpaMicros: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Common__TargetCpa(
	  /** Average CPA target. This target should be greater than or equal to minimum billable unit based on the currency for the account. */
		targetCpaMicros: Option[String] = None,
	  /** Maximum bid limit that can be set by the bid strategy. The limit applies to all keywords managed by the strategy. This should only be set for portfolio bid strategies. */
		cpcBidCeilingMicros: Option[String] = None,
	  /** Minimum bid limit that can be set by the bid strategy. The limit applies to all keywords managed by the strategy. This should only be set for portfolio bid strategies. */
		cpcBidFloorMicros: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Common__TargetImpressionShare {
		enum LocationEnum extends Enum[LocationEnum] { case UNSPECIFIED, UNKNOWN, ANYWHERE_ON_PAGE, TOP_OF_PAGE, ABSOLUTE_TOP_OF_PAGE }
	}
	case class GoogleAdsSearchads360V0Common__TargetImpressionShare(
	  /** The targeted location on the search results page. */
		location: Option[Schema.GoogleAdsSearchads360V0Common__TargetImpressionShare.LocationEnum] = None,
	  /** The chosen fraction of ads to be shown in the targeted location in micros. For example, 1% equals 10,000. */
		locationFractionMicros: Option[String] = None,
	  /** The highest CPC bid the automated bidding system is permitted to specify. This is a required field entered by the advertiser that sets the ceiling and specified in local micros. */
		cpcBidCeilingMicros: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Common__TargetOutrankShare(
	  /** Maximum bid limit that can be set by the bid strategy. The limit applies to all keywords managed by the strategy. */
		cpcBidCeilingMicros: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Common__TargetRoas(
	  /** Required. The chosen revenue (based on conversion data) per unit of spend. Value must be between 0.01 and 1000.0, inclusive. */
		targetRoas: Option[BigDecimal] = None,
	  /** Maximum bid limit that can be set by the bid strategy. The limit applies to all keywords managed by the strategy. This should only be set for portfolio bid strategies. */
		cpcBidCeilingMicros: Option[String] = None,
	  /** Minimum bid limit that can be set by the bid strategy. The limit applies to all keywords managed by the strategy. This should only be set for portfolio bid strategies. */
		cpcBidFloorMicros: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Common__TargetSpend(
	  /** Deprecated: The spend target under which to maximize clicks. A TargetSpend bidder will attempt to spend the smaller of this value or the natural throttling spend amount. If not specified, the budget is used as the spend target. This field is deprecated and should no longer be used. See https://ads-developers.googleblog.com/2020/05/reminder-about-sunset-creation-of.html for details. */
		targetSpendMicros: Option[String] = None,
	  /** Maximum bid limit that can be set by the bid strategy. The limit applies to all keywords managed by the strategy. */
		cpcBidCeilingMicros: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources__CampaignBudget {
		enum DeliveryMethodEnum extends Enum[DeliveryMethodEnum] { case UNSPECIFIED, UNKNOWN, STANDARD, ACCELERATED }
		enum PeriodEnum extends Enum[PeriodEnum] { case UNSPECIFIED, UNKNOWN, DAILY, FIXED_DAILY, CUSTOM_PERIOD }
	}
	case class GoogleAdsSearchads360V0Resources__CampaignBudget(
	  /** Immutable. The resource name of the campaign budget. Campaign budget resource names have the form: `customers/{customer_id}/campaignBudgets/{campaign_budget_id}` */
		resourceName: Option[String] = None,
	  /** The amount of the budget, in the local currency for the account. Amount is specified in micros, where one million is equivalent to one currency unit. Monthly spend is capped at 30.4 times this amount. */
		amountMicros: Option[String] = None,
	  /** The delivery method that determines the rate at which the campaign budget is spent. Defaults to STANDARD if unspecified in a create operation. */
		deliveryMethod: Option[Schema.GoogleAdsSearchads360V0Resources__CampaignBudget.DeliveryMethodEnum] = None,
	  /** Immutable. Period over which to spend the budget. Defaults to DAILY if not specified. */
		period: Option[Schema.GoogleAdsSearchads360V0Resources__CampaignBudget.PeriodEnum] = None
	)
	
	object GoogleAdsSearchads360V0Resources__Campaign {
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, PAUSED, REMOVED }
		enum ServingStatusEnum extends Enum[ServingStatusEnum] { case UNSPECIFIED, UNKNOWN, SERVING, NONE, ENDED, PENDING, SUSPENDED }
		enum BiddingStrategySystemStatusEnum extends Enum[BiddingStrategySystemStatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, LEARNING_NEW, LEARNING_SETTING_CHANGE, LEARNING_BUDGET_CHANGE, LEARNING_COMPOSITION_CHANGE, LEARNING_CONVERSION_TYPE_CHANGE, LEARNING_CONVERSION_SETTING_CHANGE, LIMITED_BY_CPC_BID_CEILING, LIMITED_BY_CPC_BID_FLOOR, LIMITED_BY_DATA, LIMITED_BY_BUDGET, LIMITED_BY_LOW_PRIORITY_SPEND, LIMITED_BY_LOW_QUALITY, LIMITED_BY_INVENTORY, MISCONFIGURED_ZERO_ELIGIBILITY, MISCONFIGURED_CONVERSION_TYPES, MISCONFIGURED_CONVERSION_SETTINGS, MISCONFIGURED_SHARED_BUDGET, MISCONFIGURED_STRATEGY_TYPE, PAUSED, UNAVAILABLE, MULTIPLE_LEARNING, MULTIPLE_LIMITED, MULTIPLE_MISCONFIGURED, MULTIPLE }
		enum AdServingOptimizationStatusEnum extends Enum[AdServingOptimizationStatusEnum] { case UNSPECIFIED, UNKNOWN, OPTIMIZE, CONVERSION_OPTIMIZE, ROTATE, ROTATE_INDEFINITELY, UNAVAILABLE }
		enum AdvertisingChannelTypeEnum extends Enum[AdvertisingChannelTypeEnum] { case UNSPECIFIED, UNKNOWN, SEARCH, DISPLAY, SHOPPING, HOTEL, VIDEO, MULTI_CHANNEL, LOCAL, SMART, PERFORMANCE_MAX, LOCAL_SERVICES, DISCOVERY, TRAVEL, SOCIAL }
		enum AdvertisingChannelSubTypeEnum extends Enum[AdvertisingChannelSubTypeEnum] { case UNSPECIFIED, UNKNOWN, SEARCH_MOBILE_APP, DISPLAY_MOBILE_APP, SEARCH_EXPRESS, DISPLAY_EXPRESS, SHOPPING_SMART_ADS, DISPLAY_GMAIL_AD, DISPLAY_SMART_CAMPAIGN, VIDEO_OUTSTREAM, VIDEO_ACTION, VIDEO_NON_SKIPPABLE, APP_CAMPAIGN, APP_CAMPAIGN_FOR_ENGAGEMENT, LOCAL_CAMPAIGN, SHOPPING_COMPARISON_LISTING_ADS, SMART_CAMPAIGN, VIDEO_SEQUENCE, APP_CAMPAIGN_FOR_PRE_REGISTRATION, VIDEO_REACH_TARGET_FREQUENCY, TRAVEL_ACTIVITIES, SOCIAL_FACEBOOK_TRACKING_ONLY }
		enum BiddingStrategyTypeEnum extends Enum[BiddingStrategyTypeEnum] { case UNSPECIFIED, UNKNOWN, COMMISSION, ENHANCED_CPC, INVALID, MANUAL_CPA, MANUAL_CPC, MANUAL_CPM, MANUAL_CPV, MAXIMIZE_CONVERSIONS, MAXIMIZE_CONVERSION_VALUE, PAGE_ONE_PROMOTED, PERCENT_CPC, TARGET_CPA, TARGET_CPM, TARGET_IMPRESSION_SHARE, TARGET_OUTRANK_SHARE, TARGET_ROAS, TARGET_SPEND }
		enum ExcludedParentAssetFieldTypesEnum extends Enum[ExcludedParentAssetFieldTypesEnum] { case UNSPECIFIED, UNKNOWN, HEADLINE, DESCRIPTION, MANDATORY_AD_TEXT, MARKETING_IMAGE, MEDIA_BUNDLE, YOUTUBE_VIDEO, BOOK_ON_GOOGLE, LEAD_FORM, PROMOTION, CALLOUT, STRUCTURED_SNIPPET, SITELINK, MOBILE_APP, HOTEL_CALLOUT, CALL, PRICE, LONG_HEADLINE, BUSINESS_NAME, SQUARE_MARKETING_IMAGE, PORTRAIT_MARKETING_IMAGE, LOGO, LANDSCAPE_LOGO, VIDEO, CALL_TO_ACTION_SELECTION, AD_IMAGE, BUSINESS_LOGO, HOTEL_PROPERTY, DISCOVERY_CAROUSEL_CARD }
	}
	case class GoogleAdsSearchads360V0Resources__Campaign(
	  /** Immutable. The resource name of the campaign. Campaign resource names have the form: `customers/{customer_id}/campaigns/{campaign_id}` */
		resourceName: Option[String] = None,
	  /** Output only. The ID of the campaign. */
		id: Option[String] = None,
	  /** The name of the campaign. This field is required and should not be empty when creating new campaigns. It must not contain any null (code point 0x0), NL line feed (code point 0xA) or carriage return (code point 0xD) characters. */
		name: Option[String] = None,
	  /** The status of the campaign. When a new campaign is added, the status defaults to ENABLED. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__Campaign.StatusEnum] = None,
	  /** Output only. The ad serving status of the campaign. */
		servingStatus: Option[Schema.GoogleAdsSearchads360V0Resources__Campaign.ServingStatusEnum] = None,
	  /** Output only. The system status of the campaign's bidding strategy. */
		biddingStrategySystemStatus: Option[Schema.GoogleAdsSearchads360V0Resources__Campaign.BiddingStrategySystemStatusEnum] = None,
	  /** The ad serving optimization status of the campaign. */
		adServingOptimizationStatus: Option[Schema.GoogleAdsSearchads360V0Resources__Campaign.AdServingOptimizationStatusEnum] = None,
	  /** Immutable. The primary serving target for ads within the campaign. The targeting options can be refined in `network_settings`. This field is required and should not be empty when creating new campaigns. Can be set only when creating campaigns. After the campaign is created, the field can not be changed. */
		advertisingChannelType: Option[Schema.GoogleAdsSearchads360V0Resources__Campaign.AdvertisingChannelTypeEnum] = None,
	  /** Immutable. Optional refinement to `advertising_channel_type`. Must be a valid sub-type of the parent channel type. Can be set only when creating campaigns. After campaign is created, the field can not be changed. */
		advertisingChannelSubType: Option[Schema.GoogleAdsSearchads360V0Resources__Campaign.AdvertisingChannelSubTypeEnum] = None,
	  /** The URL template for constructing a tracking URL. */
		trackingUrlTemplate: Option[String] = None,
	  /** The list of mappings used to substitute custom parameter tags in a `tracking_url_template`, `final_urls`, or `mobile_final_urls`. */
		urlCustomParameters: Option[List[Schema.GoogleAdsSearchads360V0Common__CustomParameter]] = None,
	  /** Settings for Real-Time Bidding, a feature only available for campaigns targeting the Ad Exchange network. */
		realTimeBiddingSetting: Option[Schema.GoogleAdsSearchads360V0Common__RealTimeBiddingSetting] = None,
	  /** The network settings for the campaign. */
		networkSettings: Option[Schema.GoogleAdsSearchads360V0Resources_Campaign_NetworkSettings] = None,
	  /** The setting for controlling Dynamic Search Ads (DSA). */
		dynamicSearchAdsSetting: Option[Schema.GoogleAdsSearchads360V0Resources_Campaign_DynamicSearchAdsSetting] = None,
	  /** The setting for controlling Shopping campaigns. */
		shoppingSetting: Option[Schema.GoogleAdsSearchads360V0Resources_Campaign_ShoppingSetting] = None,
	  /** The setting for ads geotargeting. */
		geoTargetTypeSetting: Option[Schema.GoogleAdsSearchads360V0Resources_Campaign_GeoTargetTypeSetting] = None,
	  /** Output only. The resource names of effective labels attached to this campaign. An effective label is a label inherited or directly assigned to this campaign. */
		effectiveLabels: Option[List[String]] = None,
	  /** Output only. The resource names of labels attached to this campaign. */
		labels: Option[List[String]] = None,
	  /** The resource name of the campaign budget of the campaign. */
		campaignBudget: Option[String] = None,
	  /** Output only. The type of bidding strategy. A bidding strategy can be created by setting either the bidding scheme to create a standard bidding strategy or the `bidding_strategy` field to create a portfolio bidding strategy. This field is read-only. */
		biddingStrategyType: Option[Schema.GoogleAdsSearchads360V0Resources__Campaign.BiddingStrategyTypeEnum] = None,
	  /** Output only. Resource name of AccessibleBiddingStrategy, a read-only view of the unrestricted attributes of the attached portfolio bidding strategy identified by 'bidding_strategy'. Empty, if the campaign does not use a portfolio strategy. Unrestricted strategy attributes are available to all customers with whom the strategy is shared and are read from the AccessibleBiddingStrategy resource. In contrast, restricted attributes are only available to the owner customer of the strategy and their managers. Restricted attributes can only be read from the BiddingStrategy resource. */
		accessibleBiddingStrategy: Option[String] = None,
	  /** The date when campaign started in serving customer's timezone in YYYY-MM-DD format. */
		startDate: Option[String] = None,
	  /** The last day of the campaign in serving customer's timezone in YYYY-MM-DD format. On create, defaults to 2037-12-30, which means the campaign will run indefinitely. To set an existing campaign to run indefinitely, set this field to 2037-12-30. */
		endDate: Option[String] = None,
	  /** Suffix used to append query parameters to landing pages that are served with parallel tracking. */
		finalUrlSuffix: Option[String] = None,
	  /** A list that limits how often each user will see this campaign's ads. */
		frequencyCaps: Option[List[Schema.GoogleAdsSearchads360V0Common__FrequencyCapEntry]] = None,
	  /** Selective optimization setting for this campaign, which includes a set of conversion actions to optimize this campaign towards. This feature only applies to app campaigns that use MULTI_CHANNEL as AdvertisingChannelType and APP_CAMPAIGN or APP_CAMPAIGN_FOR_ENGAGEMENT as AdvertisingChannelSubType. */
		selectiveOptimization: Option[Schema.GoogleAdsSearchads360V0Resources_Campaign_SelectiveOptimization] = None,
	  /** Optimization goal setting for this campaign, which includes a set of optimization goal types. */
		optimizationGoalSetting: Option[Schema.GoogleAdsSearchads360V0Resources_Campaign_OptimizationGoalSetting] = None,
	  /** Output only. Campaign-level settings for tracking information. */
		trackingSetting: Option[Schema.GoogleAdsSearchads360V0Resources_Campaign_TrackingSetting] = None,
	  /** Output only. ID of the campaign in the external engine account. This field is for non-Google Ads account only, for example, Yahoo Japan, Microsoft, Baidu etc. For Google Ads entity, use "campaign.id" instead. */
		engineId: Option[String] = None,
	  /** The asset field types that should be excluded from this campaign. Asset links with these field types will not be inherited by this campaign from the upper level. */
		excludedParentAssetFieldTypes: Option[List[Schema.GoogleAdsSearchads360V0Resources__Campaign.ExcludedParentAssetFieldTypesEnum]] = None,
	  /** Output only. The timestamp when this campaign was created. The timestamp is in the customer's time zone and in "yyyy-MM-dd HH:mm:ss" format. create_time will be deprecated in v1. Use creation_time instead. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when this campaign was created. The timestamp is in the customer's time zone and in "yyyy-MM-dd HH:mm:ss" format. */
		creationTime: Option[String] = None,
	  /** Output only. The datetime when this campaign was last modified. The datetime is in the customer's time zone and in "yyyy-MM-dd HH:mm:ss.ssssss" format. */
		lastModifiedTime: Option[String] = None,
	  /** Represents opting out of URL expansion to more targeted URLs. If opted out (true), only the final URLs in the asset group or URLs specified in the advertiser's Google Merchant Center or business data feeds are targeted. If opted in (false), the entire domain will be targeted. This field can only be set for Performance Max campaigns, where the default value is false. */
		urlExpansionOptOut: Option[Boolean] = None,
	  /** The resource name of the portfolio bidding strategy used by the campaign. */
		biddingStrategy: Option[String] = None,
	  /** Standard Manual CPA bidding strategy. Manual bidding strategy that allows advertiser to set the bid per advertiser-specified action. Supported only for Local Services campaigns. */
		manualCpa: Option[Schema.GoogleAdsSearchads360V0Common__ManualCpa] = None,
	  /** Standard Manual CPC bidding strategy. Manual click-based bidding where user pays per click. */
		manualCpc: Option[Schema.GoogleAdsSearchads360V0Common__ManualCpc] = None,
	  /** Standard Manual CPM bidding strategy. Manual impression-based bidding where user pays per thousand impressions. */
		manualCpm: Option[Schema.GoogleAdsSearchads360V0Common__ManualCpm] = None,
	  /** Standard Maximize Conversions bidding strategy that automatically maximizes number of conversions while spending your budget. */
		maximizeConversions: Option[Schema.GoogleAdsSearchads360V0Common__MaximizeConversions] = None,
	  /** Standard Maximize Conversion Value bidding strategy that automatically sets bids to maximize revenue while spending your budget. */
		maximizeConversionValue: Option[Schema.GoogleAdsSearchads360V0Common__MaximizeConversionValue] = None,
	  /** Standard Target CPA bidding strategy that automatically sets bids to help get as many conversions as possible at the target cost-per-acquisition (CPA) you set. */
		targetCpa: Option[Schema.GoogleAdsSearchads360V0Common__TargetCpa] = None,
	  /** Target Impression Share bidding strategy. An automated bidding strategy that sets bids to achieve a chosen percentage of impressions. */
		targetImpressionShare: Option[Schema.GoogleAdsSearchads360V0Common__TargetImpressionShare] = None,
	  /** Standard Target ROAS bidding strategy that automatically maximizes revenue while averaging a specific target return on ad spend (ROAS). */
		targetRoas: Option[Schema.GoogleAdsSearchads360V0Common__TargetRoas] = None,
	  /** Standard Target Spend bidding strategy that automatically sets your bids to help get as many clicks as possible within your budget. */
		targetSpend: Option[Schema.GoogleAdsSearchads360V0Common__TargetSpend] = None,
	  /** Standard Percent Cpc bidding strategy where bids are a fraction of the advertised price for some good or service. */
		percentCpc: Option[Schema.GoogleAdsSearchads360V0Common__PercentCpc] = None,
	  /** A bidding strategy that automatically optimizes cost per thousand impressions. */
		targetCpm: Option[Schema.GoogleAdsSearchads360V0Common__TargetCpm] = None
	)
	
	case class GoogleAdsSearchads360V0Common__CustomParameter(
	  /** The key matching the parameter tag name. */
		key: Option[String] = None,
	  /** The value to be substituted. */
		value: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Common__RealTimeBiddingSetting(
	  /** Whether the campaign is opted in to real-time bidding. */
		optIn: Option[Boolean] = None
	)
	
	case class GoogleAdsSearchads360V0Resources_Campaign_NetworkSettings(
	  /** Whether ads will be served with google.com search results. */
		targetGoogleSearch: Option[Boolean] = None,
	  /** Whether ads will be served on partner sites in the Google Search Network (requires `target_google_search` to also be `true`). */
		targetSearchNetwork: Option[Boolean] = None,
	  /** Whether ads will be served on specified placements in the Google Display Network. Placements are specified using the Placement criterion. */
		targetContentNetwork: Option[Boolean] = None,
	  /** Whether ads will be served on the Google Partner Network. This is available only to some select Google partner accounts. */
		targetPartnerSearchNetwork: Option[Boolean] = None
	)
	
	case class GoogleAdsSearchads360V0Resources_Campaign_DynamicSearchAdsSetting(
	  /** Required. The Internet domain name that this setting represents, for example, "google.com" or "www.google.com". */
		domainName: Option[String] = None,
	  /** Required. The language code specifying the language of the domain, for example, "en". */
		languageCode: Option[String] = None,
	  /** Whether the campaign uses advertiser supplied URLs exclusively. */
		useSuppliedUrlsOnly: Option[Boolean] = None
	)
	
	case class GoogleAdsSearchads360V0Resources_Campaign_ShoppingSetting(
	  /** Immutable. ID of the Merchant Center account. This field is required for create operations. This field is immutable for Shopping campaigns. */
		merchantId: Option[String] = None,
	  /** Sales country of products to include in the campaign.  */
		salesCountry: Option[String] = None,
	  /** Feed label of products to include in the campaign. Only one of feed_label or sales_country can be set. If used instead of sales_country, the feed_label field accepts country codes in the same format for example: 'XX'. Otherwise can be any string used for feed label in Google Merchant Center. */
		feedLabel: Option[String] = None,
	  /** Priority of the campaign. Campaigns with numerically higher priorities take precedence over those with lower priorities. This field is required for Shopping campaigns, with values between 0 and 2, inclusive. This field is optional for Smart Shopping campaigns, but must be equal to 3 if set. */
		campaignPriority: Option[Int] = None,
	  /** Whether to include local products. */
		enableLocal: Option[Boolean] = None,
	  /** Immutable. Whether to target Vehicle Listing inventory. */
		useVehicleInventory: Option[Boolean] = None
	)
	
	object GoogleAdsSearchads360V0Resources_Campaign_GeoTargetTypeSetting {
		enum PositiveGeoTargetTypeEnum extends Enum[PositiveGeoTargetTypeEnum] { case UNSPECIFIED, UNKNOWN, PRESENCE_OR_INTEREST, SEARCH_INTEREST, PRESENCE }
		enum NegativeGeoTargetTypeEnum extends Enum[NegativeGeoTargetTypeEnum] { case UNSPECIFIED, UNKNOWN, PRESENCE_OR_INTEREST, PRESENCE }
	}
	case class GoogleAdsSearchads360V0Resources_Campaign_GeoTargetTypeSetting(
	  /** The setting used for positive geotargeting in this particular campaign. */
		positiveGeoTargetType: Option[Schema.GoogleAdsSearchads360V0Resources_Campaign_GeoTargetTypeSetting.PositiveGeoTargetTypeEnum] = None,
	  /** The setting used for negative geotargeting in this particular campaign. */
		negativeGeoTargetType: Option[Schema.GoogleAdsSearchads360V0Resources_Campaign_GeoTargetTypeSetting.NegativeGeoTargetTypeEnum] = None
	)
	
	case class GoogleAdsSearchads360V0Common__FrequencyCapEntry(
	
	)
	
	case class GoogleAdsSearchads360V0Resources_Campaign_SelectiveOptimization(
	  /** The selected set of resource names for conversion actions for optimizing this campaign. */
		conversionActions: Option[List[String]] = None
	)
	
	object GoogleAdsSearchads360V0Resources_Campaign_OptimizationGoalSetting {
		enum OptimizationGoalTypesEnum extends Enum[OptimizationGoalTypesEnum] { case UNSPECIFIED, UNKNOWN, CALL_CLICKS, DRIVING_DIRECTIONS, APP_PRE_REGISTRATION }
	}
	case class GoogleAdsSearchads360V0Resources_Campaign_OptimizationGoalSetting(
	  /** The list of optimization goal types. */
		optimizationGoalTypes: Option[List[Schema.GoogleAdsSearchads360V0Resources_Campaign_OptimizationGoalSetting.OptimizationGoalTypesEnum]] = None
	)
	
	case class GoogleAdsSearchads360V0Resources_Campaign_TrackingSetting(
	  /** Output only. The url used for dynamic tracking. */
		trackingUrl: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Common__ManualCpa(
	
	)
	
	case class GoogleAdsSearchads360V0Common__ManualCpc(
	  /** Whether bids are to be enhanced based on conversion optimizer data. */
		enhancedCpcEnabled: Option[Boolean] = None
	)
	
	case class GoogleAdsSearchads360V0Common__ManualCpm(
	
	)
	
	case class GoogleAdsSearchads360V0Common__PercentCpc(
	  /** Maximum bid limit that can be set by the bid strategy. This is an optional field entered by the advertiser and specified in local micros. Note: A zero value is interpreted in the same way as having bid_ceiling undefined. */
		cpcBidCeilingMicros: Option[String] = None,
	  /** Adjusts the bid for each auction upward or downward, depending on the likelihood of a conversion. Individual bids may exceed cpc_bid_ceiling_micros, but the average bid amount for a campaign should not. */
		enhancedCpcEnabled: Option[Boolean] = None
	)
	
	case class GoogleAdsSearchads360V0Common__TargetCpm(
	
	)
	
	object GoogleAdsSearchads360V0Resources__CampaignAsset {
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, REMOVED, PAUSED }
	}
	case class GoogleAdsSearchads360V0Resources__CampaignAsset(
	  /** Immutable. The resource name of the campaign asset. CampaignAsset resource names have the form: `customers/{customer_id}/campaignAssets/{campaign_id}~{asset_id}~{field_type}` */
		resourceName: Option[String] = None,
	  /** Immutable. The campaign to which the asset is linked. */
		campaign: Option[String] = None,
	  /** Immutable. The asset which is linked to the campaign. */
		asset: Option[String] = None,
	  /** Output only. Status of the campaign asset. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__CampaignAsset.StatusEnum] = None
	)
	
	object GoogleAdsSearchads360V0Resources__CampaignAssetSet {
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, REMOVED }
	}
	case class GoogleAdsSearchads360V0Resources__CampaignAssetSet(
	  /** Immutable. The resource name of the campaign asset set. Asset set asset resource names have the form: `customers/{customer_id}/campaignAssetSets/{campaign_id}~{asset_set_id}` */
		resourceName: Option[String] = None,
	  /** Immutable. The campaign to which this asset set is linked. */
		campaign: Option[String] = None,
	  /** Immutable. The asset set which is linked to the campaign. */
		assetSet: Option[String] = None,
	  /** Output only. The status of the campaign asset set asset. Read-only. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__CampaignAssetSet.StatusEnum] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__CampaignAudienceView(
	  /** Output only. The resource name of the campaign audience view. Campaign audience view resource names have the form: `customers/{customer_id}/campaignAudienceViews/{campaign_id}~{criterion_id}` */
		resourceName: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources__CampaignCriterion {
		enum TypeEnum extends Enum[TypeEnum] { case UNSPECIFIED, UNKNOWN, KEYWORD, PLACEMENT, MOBILE_APP_CATEGORY, MOBILE_APPLICATION, DEVICE, LOCATION, LISTING_GROUP, AD_SCHEDULE, AGE_RANGE, GENDER, INCOME_RANGE, PARENTAL_STATUS, YOUTUBE_VIDEO, YOUTUBE_CHANNEL, USER_LIST, PROXIMITY, TOPIC, LISTING_SCOPE, LANGUAGE, IP_BLOCK, CONTENT_LABEL, CARRIER, USER_INTEREST, WEBPAGE, OPERATING_SYSTEM_VERSION, APP_PAYMENT_MODEL, MOBILE_DEVICE, CUSTOM_AFFINITY, CUSTOM_INTENT, LOCATION_GROUP, CUSTOM_AUDIENCE, COMBINED_AUDIENCE, KEYWORD_THEME, AUDIENCE, LOCAL_SERVICE_ID, BRAND, BRAND_LIST, LIFE_EVENT }
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, PAUSED, REMOVED }
	}
	case class GoogleAdsSearchads360V0Resources__CampaignCriterion(
	  /** Immutable. The resource name of the campaign criterion. Campaign criterion resource names have the form: `customers/{customer_id}/campaignCriteria/{campaign_id}~{criterion_id}` */
		resourceName: Option[String] = None,
	  /** Output only. The ID of the criterion. This field is ignored during mutate. */
		criterionId: Option[String] = None,
	  /** Output only. The display name of the criterion. This field is ignored for mutates. */
		displayName: Option[String] = None,
	  /** The modifier for the bids when the criterion matches. The modifier must be in the range: 0.1 - 10.0. Most targetable criteria types support modifiers. Use 0 to opt out of a Device type. */
		bidModifier: Option[BigDecimal] = None,
	  /** Immutable. Whether to target (`false`) or exclude (`true`) the criterion. */
		negative: Option[Boolean] = None,
	  /** Output only. The type of the criterion. */
		`type`: Option[Schema.GoogleAdsSearchads360V0Resources__CampaignCriterion.TypeEnum] = None,
	  /** The status of the criterion. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__CampaignCriterion.StatusEnum] = None,
	  /** Output only. The datetime when this campaign criterion was last modified. The datetime is in the customer's time zone and in "yyyy-MM-dd HH:mm:ss.ssssss" format. */
		lastModifiedTime: Option[String] = None,
	  /** Immutable. Keyword. */
		keyword: Option[Schema.GoogleAdsSearchads360V0Common__KeywordInfo] = None,
	  /** Immutable. Location. */
		location: Option[Schema.GoogleAdsSearchads360V0Common__LocationInfo] = None,
	  /** Immutable. Device. */
		device: Option[Schema.GoogleAdsSearchads360V0Common__DeviceInfo] = None,
	  /** Immutable. Age range. */
		ageRange: Option[Schema.GoogleAdsSearchads360V0Common__AgeRangeInfo] = None,
	  /** Immutable. Gender. */
		gender: Option[Schema.GoogleAdsSearchads360V0Common__GenderInfo] = None,
	  /** Immutable. User List. */
		userList: Option[Schema.GoogleAdsSearchads360V0Common__UserListInfo] = None,
	  /** Immutable. Language. */
		language: Option[Schema.GoogleAdsSearchads360V0Common__LanguageInfo] = None,
	  /** Immutable. Webpage. */
		webpage: Option[Schema.GoogleAdsSearchads360V0Common__WebpageInfo] = None,
	  /** Immutable. Location Group */
		locationGroup: Option[Schema.GoogleAdsSearchads360V0Common__LocationGroupInfo] = None
	)
	
	case class GoogleAdsSearchads360V0Common__LanguageInfo(
	  /** The language constant resource name. */
		languageConstant: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Common__LocationGroupInfo {
		enum RadiusUnitsEnum extends Enum[RadiusUnitsEnum] { case UNSPECIFIED, UNKNOWN, METERS, MILES, MILLI_MILES }
	}
	case class GoogleAdsSearchads360V0Common__LocationGroupInfo(
	  /** Geo target constant(s) restricting the scope of the geographic area within the feed. Currently only one geo target constant is allowed. */
		geoTargetConstants: Option[List[String]] = None,
	  /** Distance in units specifying the radius around targeted locations. This is required and must be set in CREATE operations. */
		radius: Option[String] = None,
	  /** Unit of the radius. Miles and meters are supported for geo target constants. Milli miles and meters are supported for feed item sets. This is required and must be set in CREATE operations. */
		radiusUnits: Option[Schema.GoogleAdsSearchads360V0Common__LocationGroupInfo.RadiusUnitsEnum] = None,
	  /** FeedItemSets whose FeedItems are targeted. If multiple IDs are specified, then all items that appear in at least one set are targeted. This field cannot be used with geo_target_constants. This is optional and can only be set in CREATE operations. */
		feedItemSets: Option[List[String]] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__CampaignEffectiveLabel(
	  /** Immutable. Name of the resource. CampaignEffectivelabel resource names have the form: `customers/{customer_id}/campaignEffectiveLabels/{campaign_id}~{label_id}` */
		resourceName: Option[String] = None,
	  /** Immutable. The campaign to which the effective label is attached. */
		campaign: Option[String] = None,
	  /** Immutable. The effective label assigned to the campaign. */
		label: Option[String] = None,
	  /** Output only. The ID of the Customer which owns the effective label. */
		ownerCustomerId: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__CampaignLabel(
	  /** Immutable. Name of the resource. Campaign label resource names have the form: `customers/{customer_id}/campaignLabels/{campaign_id}~{label_id}` */
		resourceName: Option[String] = None,
	  /** Immutable. The campaign to which the label is attached. */
		campaign: Option[String] = None,
	  /** Immutable. The label assigned to the campaign. */
		label: Option[String] = None,
	  /** Output only. The ID of the Customer which owns the label. */
		ownerCustomerId: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__CartDataSalesView(
	  /** Output only. The resource name of the Cart data sales view. Cart data sales view resource names have the form: `customers/{customer_id}/cartDataSalesView` */
		resourceName: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__Audience(
	  /** Immutable. The resource name of the audience. Audience names have the form: `customers/{customer_id}/audiences/{audience_id}` */
		resourceName: Option[String] = None,
	  /** Output only. ID of the audience. */
		id: Option[String] = None,
	  /** Required. Name of the audience. It should be unique across all audiences. It must have a minimum length of 1 and maximum length of 255. */
		name: Option[String] = None,
	  /** Description of this audience. */
		description: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources__ConversionAction {
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, REMOVED, HIDDEN }
		enum TypeEnum extends Enum[TypeEnum] { case UNSPECIFIED, UNKNOWN, AD_CALL, CLICK_TO_CALL, GOOGLE_PLAY_DOWNLOAD, GOOGLE_PLAY_IN_APP_PURCHASE, UPLOAD_CALLS, UPLOAD_CLICKS, WEBPAGE, WEBSITE_CALL, STORE_SALES_DIRECT_UPLOAD, STORE_SALES, FIREBASE_ANDROID_FIRST_OPEN, FIREBASE_ANDROID_IN_APP_PURCHASE, FIREBASE_ANDROID_CUSTOM, FIREBASE_IOS_FIRST_OPEN, FIREBASE_IOS_IN_APP_PURCHASE, FIREBASE_IOS_CUSTOM, THIRD_PARTY_APP_ANALYTICS_ANDROID_FIRST_OPEN, THIRD_PARTY_APP_ANALYTICS_ANDROID_IN_APP_PURCHASE, THIRD_PARTY_APP_ANALYTICS_ANDROID_CUSTOM, THIRD_PARTY_APP_ANALYTICS_IOS_FIRST_OPEN, THIRD_PARTY_APP_ANALYTICS_IOS_IN_APP_PURCHASE, THIRD_PARTY_APP_ANALYTICS_IOS_CUSTOM, ANDROID_APP_PRE_REGISTRATION, ANDROID_INSTALLS_ALL_OTHER_APPS, FLOODLIGHT_ACTION, FLOODLIGHT_TRANSACTION, GOOGLE_HOSTED, LEAD_FORM_SUBMIT, SALESFORCE, SEARCH_ADS_360, SMART_CAMPAIGN_AD_CLICKS_TO_CALL, SMART_CAMPAIGN_MAP_CLICKS_TO_CALL, SMART_CAMPAIGN_MAP_DIRECTIONS, SMART_CAMPAIGN_TRACKED_CALLS, STORE_VISITS, WEBPAGE_CODELESS, UNIVERSAL_ANALYTICS_GOAL, UNIVERSAL_ANALYTICS_TRANSACTION, GOOGLE_ANALYTICS_4_CUSTOM, GOOGLE_ANALYTICS_4_PURCHASE }
		enum CategoryEnum extends Enum[CategoryEnum] { case UNSPECIFIED, UNKNOWN, DEFAULT, PAGE_VIEW, PURCHASE, SIGNUP, LEAD, DOWNLOAD, ADD_TO_CART, BEGIN_CHECKOUT, SUBSCRIBE_PAID, PHONE_CALL_LEAD, IMPORTED_LEAD, SUBMIT_LEAD_FORM, BOOK_APPOINTMENT, REQUEST_QUOTE, GET_DIRECTIONS, OUTBOUND_CLICK, CONTACT, ENGAGEMENT, STORE_VISIT, STORE_SALE, QUALIFIED_LEAD, CONVERTED_LEAD }
	}
	case class GoogleAdsSearchads360V0Resources__ConversionAction(
	  /** Immutable. The resource name of the conversion action. Conversion action resource names have the form: `customers/{customer_id}/conversionActions/{conversion_action_id}` */
		resourceName: Option[String] = None,
	  /** Output only. The ID of the conversion action. */
		id: Option[String] = None,
	  /** The name of the conversion action. This field is required and should not be empty when creating new conversion actions. */
		name: Option[String] = None,
	  /** Output only. Timestamp of the Floodlight activity's creation, formatted in ISO 8601. */
		creationTime: Option[String] = None,
	  /** The status of this conversion action for conversion event accrual. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__ConversionAction.StatusEnum] = None,
	  /** Immutable. The type of this conversion action. */
		`type`: Option[Schema.GoogleAdsSearchads360V0Resources__ConversionAction.TypeEnum] = None,
	  /** If a conversion action's primary_for_goal bit is false, the conversion action is non-biddable for all campaigns regardless of their customer conversion goal or campaign conversion goal. However, custom conversion goals do not respect primary_for_goal, so if a campaign has a custom conversion goal configured with a primary_for_goal = false conversion action, that conversion action is still biddable. By default, primary_for_goal will be true if not set. In V9, primary_for_goal can only be set to false after creation through an 'update' operation because it's not declared as optional. */
		primaryForGoal: Option[Boolean] = None,
	  /** The category of conversions reported for this conversion action. */
		category: Option[Schema.GoogleAdsSearchads360V0Resources__ConversionAction.CategoryEnum] = None,
	  /** Output only. The resource name of the conversion action owner customer, or null if this is a system-defined conversion action. */
		ownerCustomer: Option[String] = None,
	  /** Whether this conversion action should be included in the "client_account_conversions" metric. */
		includeInClientAccountConversionsMetric: Option[Boolean] = None,
	  /** Output only. Whether this conversion action should be included in the "conversions" metric. */
		includeInConversionsMetric: Option[Boolean] = None,
	  /** The maximum number of days that may elapse between an interaction (for example, a click) and a conversion event. */
		clickThroughLookbackWindowDays: Option[String] = None,
	  /** Settings related to the value for conversion events associated with this conversion action. */
		valueSettings: Option[Schema.GoogleAdsSearchads360V0Resources_ConversionAction_ValueSettings] = None,
	  /** Settings related to this conversion action's attribution model. */
		attributionModelSettings: Option[Schema.GoogleAdsSearchads360V0Resources_ConversionAction_AttributionModelSettings] = None,
	  /** App ID for an app conversion action. */
		appId: Option[String] = None,
	  /** Output only. Floodlight settings for Floodlight conversion types. */
		floodlightSettings: Option[Schema.GoogleAdsSearchads360V0Resources_ConversionAction_FloodlightSettings] = None
	)
	
	case class GoogleAdsSearchads360V0Resources_ConversionAction_ValueSettings(
	  /** The value to use when conversion events for this conversion action are sent with an invalid, disallowed or missing value, or when this conversion action is configured to always use the default value. */
		defaultValue: Option[BigDecimal] = None,
	  /** The currency code to use when conversion events for this conversion action are sent with an invalid or missing currency code, or when this conversion action is configured to always use the default value. */
		defaultCurrencyCode: Option[String] = None,
	  /** Controls whether the default value and default currency code are used in place of the value and currency code specified in conversion events for this conversion action. */
		alwaysUseDefaultValue: Option[Boolean] = None
	)
	
	object GoogleAdsSearchads360V0Resources_ConversionAction_AttributionModelSettings {
		enum AttributionModelEnum extends Enum[AttributionModelEnum] { case UNSPECIFIED, UNKNOWN, EXTERNAL, GOOGLE_ADS_LAST_CLICK, GOOGLE_SEARCH_ATTRIBUTION_FIRST_CLICK, GOOGLE_SEARCH_ATTRIBUTION_LINEAR, GOOGLE_SEARCH_ATTRIBUTION_TIME_DECAY, GOOGLE_SEARCH_ATTRIBUTION_POSITION_BASED, GOOGLE_SEARCH_ATTRIBUTION_DATA_DRIVEN }
		enum DataDrivenModelStatusEnum extends Enum[DataDrivenModelStatusEnum] { case UNSPECIFIED, UNKNOWN, AVAILABLE, STALE, EXPIRED, NEVER_GENERATED }
	}
	case class GoogleAdsSearchads360V0Resources_ConversionAction_AttributionModelSettings(
	  /** The attribution model type of this conversion action. */
		attributionModel: Option[Schema.GoogleAdsSearchads360V0Resources_ConversionAction_AttributionModelSettings.AttributionModelEnum] = None,
	  /** Output only. The status of the data-driven attribution model for the conversion action. */
		dataDrivenModelStatus: Option[Schema.GoogleAdsSearchads360V0Resources_ConversionAction_AttributionModelSettings.DataDrivenModelStatusEnum] = None
	)
	
	case class GoogleAdsSearchads360V0Resources_ConversionAction_FloodlightSettings(
	  /** Output only. String used to identify a Floodlight activity group when reporting conversions. */
		activityGroupTag: Option[String] = None,
	  /** Output only. String used to identify a Floodlight activity when reporting conversions. */
		activityTag: Option[String] = None,
	  /** Output only. ID of the Floodlight activity in DoubleClick Campaign Manager (DCM). */
		activityId: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources__ConversionCustomVariable {
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ACTIVATION_NEEDED, ENABLED, PAUSED }
		enum FamilyEnum extends Enum[FamilyEnum] { case UNSPECIFIED, UNKNOWN, STANDARD, FLOODLIGHT }
		enum CardinalityEnum extends Enum[CardinalityEnum] { case UNSPECIFIED, UNKNOWN, BELOW_ALL_LIMITS, EXCEEDS_SEGMENTATION_LIMIT_BUT_NOT_STATS_LIMIT, APPROACHES_STATS_LIMIT, EXCEEDS_STATS_LIMIT }
	}
	case class GoogleAdsSearchads360V0Resources__ConversionCustomVariable(
	  /** Immutable. The resource name of the conversion custom variable. Conversion custom variable resource names have the form: `customers/{customer_id}/conversionCustomVariables/{conversion_custom_variable_id}` */
		resourceName: Option[String] = None,
	  /** Output only. The ID of the conversion custom variable. */
		id: Option[String] = None,
	  /** Required. The name of the conversion custom variable. Name should be unique. The maximum length of name is 100 characters. There should not be any extra spaces before and after. */
		name: Option[String] = None,
	  /** Required. Immutable. The tag of the conversion custom variable. Tag should be unique and consist of a "u" character directly followed with a number less than ormequal to 100. For example: "u4". */
		tag: Option[String] = None,
	  /** The status of the conversion custom variable for conversion event accrual. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__ConversionCustomVariable.StatusEnum] = None,
	  /** Output only. The resource name of the customer that owns the conversion custom variable. */
		ownerCustomer: Option[String] = None,
	  /** Output only. Family of the conversion custom variable. */
		family: Option[Schema.GoogleAdsSearchads360V0Resources__ConversionCustomVariable.FamilyEnum] = None,
	  /** Output only. Cardinality of the conversion custom variable. */
		cardinality: Option[Schema.GoogleAdsSearchads360V0Resources__ConversionCustomVariable.CardinalityEnum] = None,
	  /** Output only. Fields for Search Ads 360 floodlight conversion custom variables. */
		floodlightConversionCustomVariableInfo: Option[Schema.GoogleAdsSearchads360V0Resources_ConversionCustomVariable_FloodlightConversionCustomVariableInfo] = None,
	  /** Output only. The IDs of custom columns that use this conversion custom variable. */
		customColumnIds: Option[List[String]] = None
	)
	
	object GoogleAdsSearchads360V0Resources_ConversionCustomVariable_FloodlightConversionCustomVariableInfo {
		enum FloodlightVariableTypeEnum extends Enum[FloodlightVariableTypeEnum] { case UNSPECIFIED, UNKNOWN, DIMENSION, METRIC, UNSET }
		enum FloodlightVariableDataTypeEnum extends Enum[FloodlightVariableDataTypeEnum] { case UNSPECIFIED, UNKNOWN, NUMBER, STRING }
	}
	case class GoogleAdsSearchads360V0Resources_ConversionCustomVariable_FloodlightConversionCustomVariableInfo(
	  /** Output only. Floodlight variable type defined in Search Ads 360. */
		floodlightVariableType: Option[Schema.GoogleAdsSearchads360V0Resources_ConversionCustomVariable_FloodlightConversionCustomVariableInfo.FloodlightVariableTypeEnum] = None,
	  /** Output only. Floodlight variable data type defined in Search Ads 360. */
		floodlightVariableDataType: Option[Schema.GoogleAdsSearchads360V0Resources_ConversionCustomVariable_FloodlightConversionCustomVariableInfo.FloodlightVariableDataTypeEnum] = None
	)
	
	object GoogleAdsSearchads360V0Resources__Customer {
		enum AccountTypeEnum extends Enum[AccountTypeEnum] { case UNSPECIFIED, UNKNOWN, BAIDU, ENGINE_TRACK, FACEBOOK, FACEBOOK_GATEWAY, GOOGLE_ADS, MICROSOFT, SEARCH_ADS_360, YAHOO_JAPAN }
		enum AccountStatusEnum extends Enum[AccountStatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, PAUSED, SUSPENDED, REMOVED, DRAFT }
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, CANCELED, SUSPENDED, CLOSED }
	}
	case class GoogleAdsSearchads360V0Resources__Customer(
	  /** Immutable. The resource name of the customer. Customer resource names have the form: `customers/{customer_id}` */
		resourceName: Option[String] = None,
	  /** Output only. The ID of the customer. */
		id: Option[String] = None,
	  /** Optional, non-unique descriptive name of the customer. */
		descriptiveName: Option[String] = None,
	  /** Immutable. The currency in which the account operates. A subset of the currency codes from the ISO 4217 standard is supported. */
		currencyCode: Option[String] = None,
	  /** Immutable. The local timezone ID of the customer. */
		timeZone: Option[String] = None,
	  /** The URL template for constructing a tracking URL out of parameters. */
		trackingUrlTemplate: Option[String] = None,
	  /** The URL template for appending params to the final URL. */
		finalUrlSuffix: Option[String] = None,
	  /** Whether auto-tagging is enabled for the customer. */
		autoTaggingEnabled: Option[Boolean] = None,
	  /** Output only. Whether the customer is a manager. */
		manager: Option[Boolean] = None,
	  /** Output only. Conversion tracking setting for a customer. */
		conversionTrackingSetting: Option[Schema.GoogleAdsSearchads360V0Resources__ConversionTrackingSetting] = None,
	  /** Output only. Engine account type, for example, Google Ads, Microsoft Advertising, Yahoo Japan, Baidu, Facebook, Engine Track, etc. */
		accountType: Option[Schema.GoogleAdsSearchads360V0Resources__Customer.AccountTypeEnum] = None,
	  /** Output only. DoubleClick Campaign Manager (DCM) setting for a manager customer. */
		doubleClickCampaignManagerSetting: Option[Schema.GoogleAdsSearchads360V0Resources__DoubleClickCampaignManagerSetting] = None,
	  /** Output only. Account status, for example, Enabled, Paused, Removed, etc. */
		accountStatus: Option[Schema.GoogleAdsSearchads360V0Resources__Customer.AccountStatusEnum] = None,
	  /** Output only. The datetime when this customer was last modified. The datetime is in the customer's time zone and in "yyyy-MM-dd HH:mm:ss.ssssss" format. */
		lastModifiedTime: Option[String] = None,
	  /** Output only. ID of the account in the external engine account. */
		engineId: Option[String] = None,
	  /** Output only. The status of the customer. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__Customer.StatusEnum] = None,
	  /** Output only. The timestamp when this customer was created. The timestamp is in the customer's time zone and in "yyyy-MM-dd HH:mm:ss" format. */
		creationTime: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources__ConversionTrackingSetting {
		enum ConversionTrackingStatusEnum extends Enum[ConversionTrackingStatusEnum] { case UNSPECIFIED, UNKNOWN, NOT_CONVERSION_TRACKED, CONVERSION_TRACKING_MANAGED_BY_SELF, CONVERSION_TRACKING_MANAGED_BY_THIS_MANAGER, CONVERSION_TRACKING_MANAGED_BY_ANOTHER_MANAGER }
	}
	case class GoogleAdsSearchads360V0Resources__ConversionTrackingSetting(
	  /** Output only. The conversion tracking id used for this account. This id doesn't indicate whether the customer uses conversion tracking (conversion_tracking_status does). This field is read-only. */
		conversionTrackingId: Option[String] = None,
	  /** Output only. The conversion tracking id of the customer's manager. This is set when the customer is opted into conversion tracking, and it overrides conversion_tracking_id. This field can only be managed through the Google Ads UI. This field is read-only. */
		googleAdsCrossAccountConversionTrackingId: Option[String] = None,
	  /** Output only. The conversion tracking id of the customer's manager. This is set when the customer is opted into cross-account conversion tracking, and it overrides conversion_tracking_id. */
		crossAccountConversionTrackingId: Option[String] = None,
	  /** Output only. Whether the customer has accepted customer data terms. If using cross-account conversion tracking, this value is inherited from the manager. This field is read-only. For more information, see https://support.google.com/adspolicy/answer/7475709. */
		acceptedCustomerDataTerms: Option[Boolean] = None,
	  /** Output only. Conversion tracking status. It indicates whether the customer is using conversion tracking, and who is the conversion tracking owner of this customer. If this customer is using cross-account conversion tracking, the value returned will differ based on the `login-customer-id` of the request. */
		conversionTrackingStatus: Option[Schema.GoogleAdsSearchads360V0Resources__ConversionTrackingSetting.ConversionTrackingStatusEnum] = None,
	  /** Output only. Whether the customer is opted-in for enhanced conversions for leads. If using cross-account conversion tracking, this value is inherited from the manager. This field is read-only. */
		enhancedConversionsForLeadsEnabled: Option[Boolean] = None,
	  /** Output only. The resource name of the customer where conversions are created and managed. This field is read-only. */
		googleAdsConversionCustomer: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__DoubleClickCampaignManagerSetting(
	  /** Output only. ID of the Campaign Manager advertiser associated with this customer. */
		advertiserId: Option[String] = None,
	  /** Output only. ID of the Campaign Manager network associated with this customer. */
		networkId: Option[String] = None,
	  /** Output only. Time zone of the Campaign Manager network associated with this customer in IANA Time Zone Database format, such as America/New_York. */
		timeZone: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources__CustomerAsset {
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, REMOVED, PAUSED }
	}
	case class GoogleAdsSearchads360V0Resources__CustomerAsset(
	  /** Immutable. The resource name of the customer asset. CustomerAsset resource names have the form: `customers/{customer_id}/customerAssets/{asset_id}~{field_type}` */
		resourceName: Option[String] = None,
	  /** Required. Immutable. The asset which is linked to the customer. */
		asset: Option[String] = None,
	  /** Status of the customer asset. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__CustomerAsset.StatusEnum] = None
	)
	
	object GoogleAdsSearchads360V0Resources__CustomerAssetSet {
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, REMOVED }
	}
	case class GoogleAdsSearchads360V0Resources__CustomerAssetSet(
	  /** Immutable. The resource name of the customer asset set. Asset set asset resource names have the form: `customers/{customer_id}/customerAssetSets/{asset_set_id}` */
		resourceName: Option[String] = None,
	  /** Immutable. The asset set which is linked to the customer. */
		assetSet: Option[String] = None,
	  /** Immutable. The customer to which this asset set is linked. */
		customer: Option[String] = None,
	  /** Output only. The status of the customer asset set asset. Read-only. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__CustomerAssetSet.StatusEnum] = None
	)
	
	object GoogleAdsSearchads360V0Resources__AccessibleBiddingStrategy {
		enum TypeEnum extends Enum[TypeEnum] { case UNSPECIFIED, UNKNOWN, COMMISSION, ENHANCED_CPC, INVALID, MANUAL_CPA, MANUAL_CPC, MANUAL_CPM, MANUAL_CPV, MAXIMIZE_CONVERSIONS, MAXIMIZE_CONVERSION_VALUE, PAGE_ONE_PROMOTED, PERCENT_CPC, TARGET_CPA, TARGET_CPM, TARGET_IMPRESSION_SHARE, TARGET_OUTRANK_SHARE, TARGET_ROAS, TARGET_SPEND }
	}
	case class GoogleAdsSearchads360V0Resources__AccessibleBiddingStrategy(
	  /** Output only. The resource name of the accessible bidding strategy. AccessibleBiddingStrategy resource names have the form: `customers/{customer_id}/accessibleBiddingStrategies/{bidding_strategy_id}` */
		resourceName: Option[String] = None,
	  /** Output only. The ID of the bidding strategy. */
		id: Option[String] = None,
	  /** Output only. The name of the bidding strategy. */
		name: Option[String] = None,
	  /** Output only. The type of the bidding strategy. */
		`type`: Option[Schema.GoogleAdsSearchads360V0Resources__AccessibleBiddingStrategy.TypeEnum] = None,
	  /** Output only. The ID of the Customer which owns the bidding strategy. */
		ownerCustomerId: Option[String] = None,
	  /** Output only. descriptive_name of the Customer which owns the bidding strategy. */
		ownerDescriptiveName: Option[String] = None,
	  /** Output only. An automated bidding strategy to help get the most conversion value for your campaigns while spending your budget. */
		maximizeConversionValue: Option[Schema.GoogleAdsSearchads360V0Resources_AccessibleBiddingStrategy_MaximizeConversionValue] = None,
	  /** Output only. An automated bidding strategy to help get the most conversions for your campaigns while spending your budget. */
		maximizeConversions: Option[Schema.GoogleAdsSearchads360V0Resources_AccessibleBiddingStrategy_MaximizeConversions] = None,
	  /** Output only. A bidding strategy that sets bids to help get as many conversions as possible at the target cost-per-acquisition (CPA) you set. */
		targetCpa: Option[Schema.GoogleAdsSearchads360V0Resources_AccessibleBiddingStrategy_TargetCpa] = None,
	  /** Output only. A bidding strategy that automatically optimizes towards a chosen percentage of impressions. */
		targetImpressionShare: Option[Schema.GoogleAdsSearchads360V0Resources_AccessibleBiddingStrategy_TargetImpressionShare] = None,
	  /** Output only. A bidding strategy that helps you maximize revenue while averaging a specific target Return On Ad Spend (ROAS). */
		targetRoas: Option[Schema.GoogleAdsSearchads360V0Resources_AccessibleBiddingStrategy_TargetRoas] = None,
	  /** Output only. A bid strategy that sets your bids to help get as many clicks as possible within your budget. */
		targetSpend: Option[Schema.GoogleAdsSearchads360V0Resources_AccessibleBiddingStrategy_TargetSpend] = None
	)
	
	case class GoogleAdsSearchads360V0Resources_AccessibleBiddingStrategy_MaximizeConversionValue(
	  /** Output only. The target return on ad spend (ROAS) option. If set, the bid strategy will maximize revenue while averaging the target return on ad spend. If the target ROAS is high, the bid strategy may not be able to spend the full budget. If the target ROAS is not set, the bid strategy will aim to achieve the highest possible ROAS for the budget. */
		targetRoas: Option[BigDecimal] = None
	)
	
	case class GoogleAdsSearchads360V0Resources_AccessibleBiddingStrategy_MaximizeConversions(
	  /** Output only. The target cost per acquisition (CPA) option. This is the average amount that you would like to spend per acquisition. */
		targetCpa: Option[String] = None,
	  /** Output only. The target cost per acquisition (CPA) option. This is the average amount that you would like to spend per acquisition. */
		targetCpaMicros: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Resources_AccessibleBiddingStrategy_TargetCpa(
	  /** Output only. Average CPA target. This target should be greater than or equal to minimum billable unit based on the currency for the account. */
		targetCpaMicros: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources_AccessibleBiddingStrategy_TargetImpressionShare {
		enum LocationEnum extends Enum[LocationEnum] { case UNSPECIFIED, UNKNOWN, ANYWHERE_ON_PAGE, TOP_OF_PAGE, ABSOLUTE_TOP_OF_PAGE }
	}
	case class GoogleAdsSearchads360V0Resources_AccessibleBiddingStrategy_TargetImpressionShare(
	  /** Output only. The targeted location on the search results page. */
		location: Option[Schema.GoogleAdsSearchads360V0Resources_AccessibleBiddingStrategy_TargetImpressionShare.LocationEnum] = None,
	  /** The chosen fraction of ads to be shown in the targeted location in micros. For example, 1% equals 10,000. */
		locationFractionMicros: Option[String] = None,
	  /** Output only. The highest CPC bid the automated bidding system is permitted to specify. This is a required field entered by the advertiser that sets the ceiling and specified in local micros. */
		cpcBidCeilingMicros: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Resources_AccessibleBiddingStrategy_TargetRoas(
	  /** Output only. The chosen revenue (based on conversion data) per unit of spend. */
		targetRoas: Option[BigDecimal] = None
	)
	
	case class GoogleAdsSearchads360V0Resources_AccessibleBiddingStrategy_TargetSpend(
	  /** Output only. The spend target under which to maximize clicks. A TargetSpend bidder will attempt to spend the smaller of this value or the natural throttling spend amount. If not specified, the budget is used as the spend target. This field is deprecated and should no longer be used. See https://ads-developers.googleblog.com/2020/05/reminder-about-sunset-creation-of.html for details. */
		targetSpendMicros: Option[String] = None,
	  /** Output only. Maximum bid limit that can be set by the bid strategy. The limit applies to all keywords managed by the strategy. */
		cpcBidCeilingMicros: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources__CustomerManagerLink {
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ACTIVE, INACTIVE, PENDING, REFUSED, CANCELED }
	}
	case class GoogleAdsSearchads360V0Resources__CustomerManagerLink(
	  /** Immutable. Name of the resource. CustomerManagerLink resource names have the form: `customers/{customer_id}/customerManagerLinks/{manager_customer_id}~{manager_link_id}` */
		resourceName: Option[String] = None,
	  /** Output only. The manager customer linked to the customer. */
		managerCustomer: Option[String] = None,
	  /** Output only. ID of the customer-manager link. This field is read only. */
		managerLinkId: Option[String] = None,
	  /** Status of the link between the customer and the manager. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__CustomerManagerLink.StatusEnum] = None,
	  /** Output only. The timestamp when the CustomerManagerLink was created. The timestamp is in the customer's time zone and in "yyyy-MM-dd HH:mm:ss" format. */
		startTime: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources__CustomerClient {
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, CANCELED, SUSPENDED, CLOSED }
	}
	case class GoogleAdsSearchads360V0Resources__CustomerClient(
	  /** Output only. The resource name of the customer client. CustomerClient resource names have the form: `customers/{customer_id}/customerClients/{client_customer_id}` */
		resourceName: Option[String] = None,
	  /** Output only. The resource name of the client-customer which is linked to the given customer. Read only. */
		clientCustomer: Option[String] = None,
	  /** Output only. Specifies whether this is a hidden account. Read only. */
		hidden: Option[Boolean] = None,
	  /** Output only. Distance between given customer and client. For self link, the level value will be 0. Read only. */
		level: Option[String] = None,
	  /** Output only. Common Locale Data Repository (CLDR) string representation of the time zone of the client, for example, America/Los_Angeles. Read only. */
		timeZone: Option[String] = None,
	  /** Output only. Identifies if the client is a test account. Read only. */
		testAccount: Option[Boolean] = None,
	  /** Output only. Identifies if the client is a manager. Read only. */
		manager: Option[Boolean] = None,
	  /** Output only. Descriptive name for the client. Read only. */
		descriptiveName: Option[String] = None,
	  /** Output only. Currency code (for example, 'USD', 'EUR') for the client. Read only. */
		currencyCode: Option[String] = None,
	  /** Output only. The ID of the client customer. Read only. */
		id: Option[String] = None,
	  /** Output only. The resource names of the labels owned by the requesting customer that are applied to the client customer. Label resource names have the form: `customers/{customer_id}/labels/{label_id}` */
		appliedLabels: Option[List[String]] = None,
	  /** Output only. The status of the client customer. Read only. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__CustomerClient.StatusEnum] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__DynamicSearchAdsSearchTermView(
	  /** Output only. The resource name of the dynamic search ads search term view. Dynamic search ads search term view resource names have the form: `customers/{customer_id}/dynamicSearchAdsSearchTermViews/{ad_group_id}~{search_term_fingerprint}~{headline_fingerprint}~{landing_page_fingerprint}~{page_url_fingerprint}` */
		resourceName: Option[String] = None,
	  /** Output only. The dynamically selected landing page URL of the impression. This field is read-only. */
		landingPage: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__GenderView(
	  /** Output only. The resource name of the gender view. Gender view resource names have the form: `customers/{customer_id}/genderViews/{ad_group_id}~{criterion_id}` */
		resourceName: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources__GeoTargetConstant {
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, REMOVAL_PLANNED }
	}
	case class GoogleAdsSearchads360V0Resources__GeoTargetConstant(
	  /** Output only. The resource name of the geo target constant. Geo target constant resource names have the form: `geoTargetConstants/{geo_target_constant_id}` */
		resourceName: Option[String] = None,
	  /** Output only. The ID of the geo target constant. */
		id: Option[String] = None,
	  /** Output only. Geo target constant English name. */
		name: Option[String] = None,
	  /** Output only. The ISO-3166-1 alpha-2 country code that is associated with the target. */
		countryCode: Option[String] = None,
	  /** Output only. Geo target constant target type. */
		targetType: Option[String] = None,
	  /** Output only. Geo target constant status. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__GeoTargetConstant.StatusEnum] = None,
	  /** Output only. The fully qualified English name, consisting of the target's name and that of its parent and country. */
		canonicalName: Option[String] = None,
	  /** Output only. The resource name of the parent geo target constant. Geo target constant resource names have the form: `geoTargetConstants/{parent_geo_target_constant_id}` */
		parentGeoTarget: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__KeywordView(
	  /** Output only. The resource name of the keyword view. Keyword view resource names have the form: `customers/{customer_id}/keywordViews/{ad_group_id}~{criterion_id}` */
		resourceName: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources__Label {
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, REMOVED }
	}
	case class GoogleAdsSearchads360V0Resources__Label(
	  /** Immutable. Name of the resource. Label resource names have the form: `customers/{customer_id}/labels/{label_id}` */
		resourceName: Option[String] = None,
	  /** Output only. ID of the label. Read only. */
		id: Option[String] = None,
	  /** The name of the label. This field is required and should not be empty when creating a new label. The length of this string should be between 1 and 80, inclusive. */
		name: Option[String] = None,
	  /** Output only. Status of the label. Read only. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__Label.StatusEnum] = None,
	  /** A type of label displaying text on a colored background. */
		textLabel: Option[Schema.GoogleAdsSearchads360V0Common__TextLabel] = None
	)
	
	case class GoogleAdsSearchads360V0Common__TextLabel(
	  /** Background color of the label in HEX format. This string must match the regular expression '^\#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$'. Note: The background color may not be visible for manager accounts. */
		backgroundColor: Option[String] = None,
	  /** A short description of the label. The length must be no more than 200 characters. */
		description: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__LanguageConstant(
	  /** Output only. The resource name of the language constant. Language constant resource names have the form: `languageConstants/{criterion_id}` */
		resourceName: Option[String] = None,
	  /** Output only. The ID of the language constant. */
		id: Option[String] = None,
	  /** Output only. The language code, for example, "en_US", "en_AU", "es", "fr", etc. */
		code: Option[String] = None,
	  /** Output only. The full name of the language in English, for example, "English (US)", "Spanish", etc. */
		name: Option[String] = None,
	  /** Output only. Whether the language is targetable. */
		targetable: Option[Boolean] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__LocationView(
	  /** Output only. The resource name of the location view. Location view resource names have the form: `customers/{customer_id}/locationViews/{campaign_id}~{criterion_id}` */
		resourceName: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources__ProductBiddingCategoryConstant {
		enum LevelEnum extends Enum[LevelEnum] { case UNSPECIFIED, UNKNOWN, LEVEL1, LEVEL2, LEVEL3, LEVEL4, LEVEL5 }
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ACTIVE, OBSOLETE }
	}
	case class GoogleAdsSearchads360V0Resources__ProductBiddingCategoryConstant(
	  /** Output only. The resource name of the product bidding category. Product bidding category resource names have the form: `productBiddingCategoryConstants/{country_code}~{level}~{id}` */
		resourceName: Option[String] = None,
	  /** Output only. ID of the product bidding category. This ID is equivalent to the google_product_category ID as described in this article: https://support.google.com/merchants/answer/6324436. */
		id: Option[String] = None,
	  /** Output only. Two-letter upper-case country code of the product bidding category. */
		countryCode: Option[String] = None,
	  /** Output only. Resource name of the parent product bidding category. */
		productBiddingCategoryConstantParent: Option[String] = None,
	  /** Output only. Level of the product bidding category. */
		level: Option[Schema.GoogleAdsSearchads360V0Resources__ProductBiddingCategoryConstant.LevelEnum] = None,
	  /** Output only. Status of the product bidding category. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__ProductBiddingCategoryConstant.StatusEnum] = None,
	  /** Output only. Language code of the product bidding category. */
		languageCode: Option[String] = None,
	  /** Output only. Display value of the product bidding category localized according to language_code. */
		localizedName: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__ProductGroupView(
	  /** Output only. The resource name of the product group view. Product group view resource names have the form: `customers/{customer_id}/productGroupViews/{ad_group_id}~{criterion_id}` */
		resourceName: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__ShoppingPerformanceView(
	  /** Output only. The resource name of the Shopping performance view. Shopping performance view resource names have the form: `customers/{customer_id}/shoppingPerformanceView` */
		resourceName: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources__UserList {
		enum TypeEnum extends Enum[TypeEnum] { case UNSPECIFIED, UNKNOWN, REMARKETING, LOGICAL, EXTERNAL_REMARKETING, RULE_BASED, SIMILAR, CRM_BASED }
	}
	case class GoogleAdsSearchads360V0Resources__UserList(
	  /** Immutable. The resource name of the user list. User list resource names have the form: `customers/{customer_id}/userLists/{user_list_id}` */
		resourceName: Option[String] = None,
	  /** Output only. Id of the user list. */
		id: Option[String] = None,
	  /** Name of this user list. Depending on its access_reason, the user list name may not be unique (for example, if access_reason=SHARED) */
		name: Option[String] = None,
	  /** Output only. Type of this list. This field is read-only. */
		`type`: Option[Schema.GoogleAdsSearchads360V0Resources__UserList.TypeEnum] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__UserLocationView(
	  /** Output only. The resource name of the user location view. UserLocation view resource names have the form: `customers/{customer_id}/userLocationViews/{country_criterion_id}~{targeting_location}` */
		resourceName: Option[String] = None,
	  /** Output only. Criterion Id for the country. */
		countryCriterionId: Option[String] = None,
	  /** Output only. Indicates whether location was targeted or not. */
		targetingLocation: Option[Boolean] = None
	)
	
	case class GoogleAdsSearchads360V0Resources__WebpageView(
	  /** Output only. The resource name of the webpage view. Webpage view resource names have the form: `customers/{customer_id}/webpageViews/{ad_group_id}~{criterion_id}` */
		resourceName: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Resources__Visit {
		enum ProductChannelEnum extends Enum[ProductChannelEnum] { case UNSPECIFIED, UNKNOWN, ONLINE, LOCAL }
		enum AssetFieldTypeEnum extends Enum[AssetFieldTypeEnum] { case UNSPECIFIED, UNKNOWN, HEADLINE, DESCRIPTION, MANDATORY_AD_TEXT, MARKETING_IMAGE, MEDIA_BUNDLE, YOUTUBE_VIDEO, BOOK_ON_GOOGLE, LEAD_FORM, PROMOTION, CALLOUT, STRUCTURED_SNIPPET, SITELINK, MOBILE_APP, HOTEL_CALLOUT, CALL, PRICE, LONG_HEADLINE, BUSINESS_NAME, SQUARE_MARKETING_IMAGE, PORTRAIT_MARKETING_IMAGE, LOGO, LANDSCAPE_LOGO, VIDEO, CALL_TO_ACTION_SELECTION, AD_IMAGE, BUSINESS_LOGO, HOTEL_PROPERTY, DISCOVERY_CAROUSEL_CARD }
	}
	case class GoogleAdsSearchads360V0Resources__Visit(
	  /** Output only. The resource name of the visit. Visit resource names have the form: `customers/{customer_id}/visits/{ad_group_id}~{criterion_id}~{ds_visit_id}` */
		resourceName: Option[String] = None,
	  /** Output only. The ID of the visit. */
		id: Option[String] = None,
	  /** Output only. Search Ads 360 keyword ID. A value of 0 indicates that the keyword is unattributed. */
		criterionId: Option[String] = None,
	  /** Output only. The Search Ads 360 inventory account ID containing the product that was clicked on. Search Ads 360 generates this ID when you link an inventory account in Search Ads 360. */
		merchantId: Option[String] = None,
	  /** Output only. Ad ID. A value of 0 indicates that the ad is unattributed. */
		adId: Option[String] = None,
	  /** Output only. A unique string for each visit that is passed to the landing page as the click id URL parameter. */
		clickId: Option[String] = None,
	  /** Output only. The timestamp of the visit event. The timestamp is in the customer's time zone and in "yyyy-MM-dd HH:mm:ss" format. */
		visitDateTime: Option[String] = None,
	  /** Output only. The ID of the product clicked on. */
		productId: Option[String] = None,
	  /** Output only. The sales channel of the product that was clicked on: Online or Local. */
		productChannel: Option[Schema.GoogleAdsSearchads360V0Resources__Visit.ProductChannelEnum] = None,
	  /** Output only. The language (ISO-639-1) that has been set for the Merchant Center feed containing data about the product. */
		productLanguageCode: Option[String] = None,
	  /** Output only. The store in the Local Inventory Ad that was clicked on. This should match the store IDs used in your local products feed. */
		productStoreId: Option[String] = None,
	  /** Output only. The country (ISO-3166 format) registered for the inventory feed that contains the product clicked on. */
		productCountryCode: Option[String] = None,
	  /** Output only. ID of the asset which was interacted with during the visit event. */
		assetId: Option[String] = None,
	  /** Output only. Asset field type of the visit event. */
		assetFieldType: Option[Schema.GoogleAdsSearchads360V0Resources__Visit.AssetFieldTypeEnum] = None
	)
	
	object GoogleAdsSearchads360V0Resources__Conversion {
		enum ProductChannelEnum extends Enum[ProductChannelEnum] { case UNSPECIFIED, UNKNOWN, ONLINE, LOCAL }
		enum AttributionTypeEnum extends Enum[AttributionTypeEnum] { case UNSPECIFIED, UNKNOWN, VISIT, CRITERION_AD }
		enum StatusEnum extends Enum[StatusEnum] { case UNSPECIFIED, UNKNOWN, ENABLED, REMOVED }
		enum AssetFieldTypeEnum extends Enum[AssetFieldTypeEnum] { case UNSPECIFIED, UNKNOWN, HEADLINE, DESCRIPTION, MANDATORY_AD_TEXT, MARKETING_IMAGE, MEDIA_BUNDLE, YOUTUBE_VIDEO, BOOK_ON_GOOGLE, LEAD_FORM, PROMOTION, CALLOUT, STRUCTURED_SNIPPET, SITELINK, MOBILE_APP, HOTEL_CALLOUT, CALL, PRICE, LONG_HEADLINE, BUSINESS_NAME, SQUARE_MARKETING_IMAGE, PORTRAIT_MARKETING_IMAGE, LOGO, LANDSCAPE_LOGO, VIDEO, CALL_TO_ACTION_SELECTION, AD_IMAGE, BUSINESS_LOGO, HOTEL_PROPERTY, DISCOVERY_CAROUSEL_CARD }
	}
	case class GoogleAdsSearchads360V0Resources__Conversion(
	  /** Output only. The resource name of the conversion. Conversion resource names have the form: `customers/{customer_id}/conversions/{ad_group_id}~{criterion_id}~{ds_conversion_id}` */
		resourceName: Option[String] = None,
	  /** Output only. The ID of the conversion */
		id: Option[String] = None,
	  /** Output only. Search Ads 360 criterion ID. A value of 0 indicates that the criterion is unattributed. */
		criterionId: Option[String] = None,
	  /** Output only. The Search Ads 360 inventory account ID containing the product that was clicked on. Search Ads 360 generates this ID when you link an inventory account in Search Ads 360. */
		merchantId: Option[String] = None,
	  /** Output only. Ad ID. A value of 0 indicates that the ad is unattributed. */
		adId: Option[String] = None,
	  /** Output only. A unique string, for the visit that the conversion is attributed to, that is passed to the landing page as the click id URL parameter. */
		clickId: Option[String] = None,
	  /** Output only. The Search Ads 360 visit ID that the conversion is attributed to. */
		visitId: Option[String] = None,
	  /** Output only. For offline conversions, this is an ID provided by advertisers. If an advertiser doesn't specify such an ID, Search Ads 360 generates one. For online conversions, this is equal to the id column or the floodlight_order_id column depending on the advertiser's Floodlight instructions. */
		advertiserConversionId: Option[String] = None,
	  /** Output only. The ID of the product clicked on. */
		productId: Option[String] = None,
	  /** Output only. The sales channel of the product that was clicked on: Online or Local. */
		productChannel: Option[Schema.GoogleAdsSearchads360V0Resources__Conversion.ProductChannelEnum] = None,
	  /** Output only. The language (ISO-639-1) that has been set for the Merchant Center feed containing data about the product. */
		productLanguageCode: Option[String] = None,
	  /** Output only. The store in the Local Inventory Ad that was clicked on. This should match the store IDs used in your local products feed. */
		productStoreId: Option[String] = None,
	  /** Output only. The country (ISO-3166-format) registered for the inventory feed that contains the product clicked on. */
		productCountryCode: Option[String] = None,
	  /** Output only. What the conversion is attributed to: Visit or Keyword+Ad. */
		attributionType: Option[Schema.GoogleAdsSearchads360V0Resources__Conversion.AttributionTypeEnum] = None,
	  /** Output only. The timestamp of the conversion event. */
		conversionDateTime: Option[String] = None,
	  /** Output only. The timestamp of the last time the conversion was modified. */
		conversionLastModifiedDateTime: Option[String] = None,
	  /** Output only. The timestamp of the visit that the conversion is attributed to. */
		conversionVisitDateTime: Option[String] = None,
	  /** Output only. The quantity of items recorded by the conversion, as determined by the qty url parameter. The advertiser is responsible for dynamically populating the parameter (such as number of items sold in the conversion), otherwise it defaults to 1. */
		conversionQuantity: Option[String] = None,
	  /** Output only. The adjusted revenue in micros for the conversion event. This will always be in the currency of the serving account. */
		conversionRevenueMicros: Option[String] = None,
	  /** Output only. The original, unchanged revenue associated with the Floodlight event (in the currency of the current report), before Floodlight currency instruction modifications. */
		floodlightOriginalRevenue: Option[String] = None,
	  /** Output only. The Floodlight order ID provided by the advertiser for the conversion. */
		floodlightOrderId: Option[String] = None,
	  /** Output only. The status of the conversion, either ENABLED or REMOVED.. */
		status: Option[Schema.GoogleAdsSearchads360V0Resources__Conversion.StatusEnum] = None,
	  /** Output only. ID of the asset which was interacted with during the conversion event. */
		assetId: Option[String] = None,
	  /** Output only. Asset field type of the conversion event. */
		assetFieldType: Option[Schema.GoogleAdsSearchads360V0Resources__Conversion.AssetFieldTypeEnum] = None
	)
	
	object GoogleAdsSearchads360V0Common__Metrics {
		enum HistoricalCreativeQualityScoreEnum extends Enum[HistoricalCreativeQualityScoreEnum] { case UNSPECIFIED, UNKNOWN, BELOW_AVERAGE, AVERAGE, ABOVE_AVERAGE }
		enum HistoricalLandingPageQualityScoreEnum extends Enum[HistoricalLandingPageQualityScoreEnum] { case UNSPECIFIED, UNKNOWN, BELOW_AVERAGE, AVERAGE, ABOVE_AVERAGE }
		enum HistoricalSearchPredictedCtrEnum extends Enum[HistoricalSearchPredictedCtrEnum] { case UNSPECIFIED, UNKNOWN, BELOW_AVERAGE, AVERAGE, ABOVE_AVERAGE }
		enum InteractionEventTypesEnum extends Enum[InteractionEventTypesEnum] { case UNSPECIFIED, UNKNOWN, CLICK, ENGAGEMENT, VIDEO_VIEW, NONE }
	}
	case class GoogleAdsSearchads360V0Common__Metrics(
	  /** Search absolute top impression share is the percentage of your Search ad impressions that are shown in the most prominent Search position. */
		absoluteTopImpressionPercentage: Option[BigDecimal] = None,
	  /** All conversions from interactions (as oppose to view through conversions) divided by the number of ad interactions. */
		allConversionsFromInteractionsRate: Option[BigDecimal] = None,
	  /** The value of all conversions. */
		allConversionsValue: Option[BigDecimal] = None,
	  /** The value of all conversions. When this column is selected with date, the values in date column means the conversion date. Details for the by_conversion_date columns are available at https://support.google.com/sa360/answer/9250611. */
		allConversionsValueByConversionDate: Option[BigDecimal] = None,
	  /** The total number of conversions. This includes all conversions regardless of the value of include_in_conversions_metric. */
		allConversions: Option[BigDecimal] = None,
	  /** The total number of conversions. This includes all conversions regardless of the value of include_in_conversions_metric. When this column is selected with date, the values in date column means the conversion date. Details for the by_conversion_date columns are available at https://support.google.com/sa360/answer/9250611. */
		allConversionsByConversionDate: Option[BigDecimal] = None,
	  /** The value of all conversions divided by the total cost of ad interactions (such as clicks for text ads or views for video ads). */
		allConversionsValuePerCost: Option[BigDecimal] = None,
	  /** The number of times people clicked the "Call" button to call a store during or after clicking an ad. This number doesn't include whether or not calls were connected, or the duration of any calls. This metric applies to feed items only. */
		allConversionsFromClickToCall: Option[BigDecimal] = None,
	  /** The number of times people clicked a "Get directions" button to navigate to a store after clicking an ad. This metric applies to feed items only. */
		allConversionsFromDirections: Option[BigDecimal] = None,
	  /** The value of all conversions from interactions divided by the total number of interactions. */
		allConversionsFromInteractionsValuePerInteraction: Option[BigDecimal] = None,
	  /** The number of times people clicked a link to view a store's menu after clicking an ad. This metric applies to feed items only. */
		allConversionsFromMenu: Option[BigDecimal] = None,
	  /** The number of times people placed an order at a store after clicking an ad. This metric applies to feed items only. */
		allConversionsFromOrder: Option[BigDecimal] = None,
	  /** The number of other conversions (for example, posting a review or saving a location for a store) that occurred after people clicked an ad. This metric applies to feed items only. */
		allConversionsFromOtherEngagement: Option[BigDecimal] = None,
	  /** Estimated number of times people visited a store after clicking an ad. This metric applies to feed items only. */
		allConversionsFromStoreVisit: Option[BigDecimal] = None,
	  /** Clicks that Search Ads 360 has successfully recorded and forwarded to an advertiser's landing page. */
		visits: Option[BigDecimal] = None,
	  /** The number of times that people were taken to a store's URL after clicking an ad. This metric applies to feed items only. */
		allConversionsFromStoreWebsite: Option[BigDecimal] = None,
	  /** The average amount you pay per interaction. This amount is the total cost of your ads divided by the total number of interactions. */
		averageCost: Option[BigDecimal] = None,
	  /** The total cost of all clicks divided by the total number of clicks received. This metric is a monetary value and returned in the customer's currency by default. See the metrics_currency parameter at https://developers.google.com/search-ads/reporting/query/query-structure#parameters_clause */
		averageCpc: Option[BigDecimal] = None,
	  /** Average cost-per-thousand impressions (CPM). This metric is a monetary value and returned in the customer's currency by default. See the metrics_currency parameter at https://developers.google.com/search-ads/reporting/query/query-structure#parameters_clause */
		averageCpm: Option[BigDecimal] = None,
	  /** The number of clicks. */
		clicks: Option[String] = None,
	  /** The estimated percent of times that your ad was eligible to show on the Display Network but didn't because your budget was too low. Note: Content budget lost impression share is reported in the range of 0 to 0.9. Any value above 0.9 is reported as 0.9001. */
		contentBudgetLostImpressionShare: Option[BigDecimal] = None,
	  /** The impressions you've received on the Display Network divided by the estimated number of impressions you were eligible to receive. Note: Content impression share is reported in the range of 0.1 to 1. Any value below 0.1 is reported as 0.0999. */
		contentImpressionShare: Option[BigDecimal] = None,
	  /** The conversion custom metrics. */
		conversionCustomMetrics: Option[List[Schema.GoogleAdsSearchads360V0Common__Value]] = None,
	  /** The estimated percentage of impressions on the Display Network that your ads didn't receive due to poor Ad Rank. Note: Content rank lost impression share is reported in the range of 0 to 0.9. Any value above 0.9 is reported as 0.9001. */
		contentRankLostImpressionShare: Option[BigDecimal] = None,
	  /** Average biddable conversions (from interaction) per conversion eligible interaction. Shows how often, on average, an ad interaction leads to a biddable conversion. */
		conversionsFromInteractionsRate: Option[BigDecimal] = None,
	  /** The value of client account conversions. This only includes conversion actions which include_in_client_account_conversions_metric attribute is set to true. If you use conversion-based bidding, your bid strategies will optimize for these conversions. */
		clientAccountConversionsValue: Option[BigDecimal] = None,
	  /** The sum of biddable conversions value by conversion date. When this column is selected with date, the values in date column means the conversion date. */
		conversionsValueByConversionDate: Option[BigDecimal] = None,
	  /** The value of biddable conversion divided by the total cost of conversion eligible interactions. */
		conversionsValuePerCost: Option[BigDecimal] = None,
	  /** The value of conversions from interactions divided by the number of ad interactions. This only includes conversion actions which include_in_conversions_metric attribute is set to true. If you use conversion-based bidding, your bid strategies will optimize for these conversions. */
		conversionsFromInteractionsValuePerInteraction: Option[BigDecimal] = None,
	  /** The number of client account conversions. This only includes conversion actions which include_in_client_account_conversions_metric attribute is set to true. If you use conversion-based bidding, your bid strategies will optimize for these conversions. */
		clientAccountConversions: Option[BigDecimal] = None,
	  /** The sum of conversions by conversion date for biddable conversion types. Can be fractional due to attribution modeling. When this column is selected with date, the values in date column means the conversion date. */
		conversionsByConversionDate: Option[BigDecimal] = None,
	  /** The sum of your cost-per-click (CPC) and cost-per-thousand impressions (CPM) costs during this period. This metric is a monetary value and returned in the customer's currency by default. See the metrics_currency parameter at https://developers.google.com/search-ads/reporting/query/query-structure#parameters_clause */
		costMicros: Option[String] = None,
	  /** The cost of ad interactions divided by all conversions. */
		costPerAllConversions: Option[BigDecimal] = None,
	  /** Average conversion eligible cost per biddable conversion. */
		costPerConversion: Option[BigDecimal] = None,
	  /** The cost of ad interactions divided by current model attributed conversions. This only includes conversion actions which include_in_conversions_metric attribute is set to true. If you use conversion-based bidding, your bid strategies will optimize for these conversions. */
		costPerCurrentModelAttributedConversion: Option[BigDecimal] = None,
	  /** Conversions from when a customer clicks on an ad on one device, then converts on a different device or browser. Cross-device conversions are already included in all_conversions. */
		crossDeviceConversions: Option[BigDecimal] = None,
	  /** The number of cross-device conversions by conversion date. Details for the by_conversion_date columns are available at https://support.google.com/sa360/answer/9250611. */
		crossDeviceConversionsByConversionDate: Option[BigDecimal] = None,
	  /** The sum of the value of cross-device conversions. */
		crossDeviceConversionsValue: Option[BigDecimal] = None,
	  /** The sum of cross-device conversions value by conversion date. Details for the by_conversion_date columns are available at https://support.google.com/sa360/answer/9250611. */
		crossDeviceConversionsValueByConversionDate: Option[BigDecimal] = None,
	  /** The number of clicks your ad receives (Clicks) divided by the number of times your ad is shown (Impressions). */
		ctr: Option[BigDecimal] = None,
	  /** The number of conversions. This only includes conversion actions which include_in_conversions_metric attribute is set to true. If you use conversion-based bidding, your bid strategies will optimize for these conversions. */
		conversions: Option[BigDecimal] = None,
	  /** The sum of conversion values for the conversions included in the "conversions" field. This metric is useful only if you entered a value for your conversion actions. */
		conversionsValue: Option[BigDecimal] = None,
	  /** The creative historical quality score. */
		historicalCreativeQualityScore: Option[Schema.GoogleAdsSearchads360V0Common__Metrics.HistoricalCreativeQualityScoreEnum] = None,
	  /** The average quality score. */
		averageQualityScore: Option[BigDecimal] = None,
	  /** The quality of historical landing page experience. */
		historicalLandingPageQualityScore: Option[Schema.GoogleAdsSearchads360V0Common__Metrics.HistoricalLandingPageQualityScoreEnum] = None,
	  /** The historical quality score. */
		historicalQualityScore: Option[String] = None,
	  /** The historical search predicted click through rate (CTR). */
		historicalSearchPredictedCtr: Option[Schema.GoogleAdsSearchads360V0Common__Metrics.HistoricalSearchPredictedCtrEnum] = None,
	  /** Count of how often your ad has appeared on a search results page or website on the Google Network. */
		impressions: Option[String] = None,
	  /** How often people interact with your ad after it is shown to them. This is the number of interactions divided by the number of times your ad is shown. */
		interactionRate: Option[BigDecimal] = None,
	  /** The number of interactions. An interaction is the main user action associated with an ad format-clicks for text and shopping ads, views for video ads, and so on. */
		interactions: Option[String] = None,
	  /** The types of payable and free interactions. */
		interactionEventTypes: Option[List[Schema.GoogleAdsSearchads360V0Common__Metrics.InteractionEventTypesEnum]] = None,
	  /** The percentage of clicks filtered out of your total number of clicks (filtered + non-filtered clicks) during the reporting period. */
		invalidClickRate: Option[BigDecimal] = None,
	  /** Number of clicks Google considers illegitimate and doesn't charge you for. */
		invalidClicks: Option[String] = None,
	  /** The percentage of clicks that have been filtered out of your total number of clicks (filtered + non-filtered clicks) due to being general invalid clicks. These are clicks Google considers illegitimate that are detected through routine means of filtration (that is, known invalid data-center traffic, bots and spiders or other crawlers, irregular patterns, etc). You're not charged for them, and they don't affect your account statistics. See the help page at https://support.google.com/campaignmanager/answer/6076504 for details. */
		generalInvalidClickRate: Option[BigDecimal] = None,
	  /** Number of general invalid clicks. These are a subset of your invalid clicks that are detected through routine means of filtration (such as known invalid data-center traffic, bots and spiders or other crawlers, irregular patterns, etc.). You're not charged for them, and they don't affect your account statistics. See the help page at https://support.google.com/campaignmanager/answer/6076504 for details. */
		generalInvalidClicks: Option[String] = None,
	  /** The percentage of mobile clicks that go to a mobile-friendly page. */
		mobileFriendlyClicksPercentage: Option[BigDecimal] = None,
	  /** The raw event conversion metrics. */
		rawEventConversionMetrics: Option[List[Schema.GoogleAdsSearchads360V0Common__Value]] = None,
	  /** The percentage of the customer's Shopping or Search ad impressions that are shown in the most prominent Shopping position. See https://support.google.com/sa360/answer/9566729 for details. Any value below 0.1 is reported as 0.0999. */
		searchAbsoluteTopImpressionShare: Option[BigDecimal] = None,
	  /** The number estimating how often your ad wasn't the very first ad among the top ads in the search results due to a low budget. Note: Search budget lost absolute top impression share is reported in the range of 0 to 0.9. Any value above 0.9 is reported as 0.9001. */
		searchBudgetLostAbsoluteTopImpressionShare: Option[BigDecimal] = None,
	  /** The estimated percent of times that your ad was eligible to show on the Search Network but didn't because your budget was too low. Note: Search budget lost impression share is reported in the range of 0 to 0.9. Any value above 0.9 is reported as 0.9001. */
		searchBudgetLostImpressionShare: Option[BigDecimal] = None,
	  /** The number estimating how often your ad didn't show adjacent to the top organic search results due to a low budget. Note: Search budget lost top impression share is reported in the range of 0 to 0.9. Any value above 0.9 is reported as 0.9001. */
		searchBudgetLostTopImpressionShare: Option[BigDecimal] = None,
	  /** The number of clicks you've received on the Search Network divided by the estimated number of clicks you were eligible to receive. Note: Search click share is reported in the range of 0.1 to 1. Any value below 0.1 is reported as 0.0999. */
		searchClickShare: Option[BigDecimal] = None,
	  /** The impressions you've received divided by the estimated number of impressions you were eligible to receive on the Search Network for search terms that matched your keywords exactly (or were close variants of your keyword), regardless of your keyword match types. Note: Search exact match impression share is reported in the range of 0.1 to 1. Any value below 0.1 is reported as 0.0999. */
		searchExactMatchImpressionShare: Option[BigDecimal] = None,
	  /** The impressions you've received on the Search Network divided by the estimated number of impressions you were eligible to receive. Note: Search impression share is reported in the range of 0.1 to 1. Any value below 0.1 is reported as 0.0999. */
		searchImpressionShare: Option[BigDecimal] = None,
	  /** The number estimating how often your ad wasn't the very first ad among the top ads in the search results due to poor Ad Rank. Note: Search rank lost absolute top impression share is reported in the range of 0 to 0.9. Any value above 0.9 is reported as 0.9001. */
		searchRankLostAbsoluteTopImpressionShare: Option[BigDecimal] = None,
	  /** The estimated percentage of impressions on the Search Network that your ads didn't receive due to poor Ad Rank. Note: Search rank lost impression share is reported in the range of 0 to 0.9. Any value above 0.9 is reported as 0.9001. */
		searchRankLostImpressionShare: Option[BigDecimal] = None,
	  /** The number estimating how often your ad didn't show adjacent to the top organic search results due to poor Ad Rank. Note: Search rank lost top impression share is reported in the range of 0 to 0.9. Any value above 0.9 is reported as 0.9001. */
		searchRankLostTopImpressionShare: Option[BigDecimal] = None,
	  /** The impressions you've received among the top ads compared to the estimated number of impressions you were eligible to receive among the top ads. Note: Search top impression share is reported in the range of 0.1 to 1. Any value below 0.1 is reported as 0.0999. Top ads are generally above the top organic results, although they may show below the top organic results on certain queries. */
		searchTopImpressionShare: Option[BigDecimal] = None,
	  /** The percent of your ad impressions that are shown adjacent to the top organic search results. */
		topImpressionPercentage: Option[BigDecimal] = None,
	  /** The value of all conversions divided by the number of all conversions. */
		valuePerAllConversions: Option[BigDecimal] = None,
	  /** The value of all conversions divided by the number of all conversions. When this column is selected with date, the values in date column means the conversion date. Details for the by_conversion_date columns are available at https://support.google.com/sa360/answer/9250611. */
		valuePerAllConversionsByConversionDate: Option[BigDecimal] = None,
	  /** The value of biddable conversion divided by the number of biddable conversions. Shows how much, on average, each of the biddable conversions is worth. */
		valuePerConversion: Option[BigDecimal] = None,
	  /** Biddable conversions value by conversion date divided by biddable conversions by conversion date. Shows how much, on average, each of the biddable conversions is worth (by conversion date). When this column is selected with date, the values in date column means the conversion date. */
		valuePerConversionsByConversionDate: Option[BigDecimal] = None,
	  /** The total number of view-through conversions. These happen when a customer sees an image or rich media ad, then later completes a conversion on your site without interacting with (for example, clicking on) another ad. */
		clientAccountViewThroughConversions: Option[String] = None,
	  /** Client account cross-sell cost of goods sold (COGS) is the total cost of products sold as a result of advertising a different product. How it works: You report conversions with cart data for completed purchases on your website. If the ad that was interacted with before the purchase has an associated product (see Shopping Ads) then this product is considered the advertised product. Any product included in the order the customer places is a sold product. If these products don't match then this is considered cross-sell. Cross-sell cost of goods sold is the total cost of the products sold that weren't advertised. Example: Someone clicked on a Shopping ad for a hat then bought the same hat and a shirt. The hat has a cost of goods sold value of $3, the shirt has a cost of goods sold value of $5. The cross-sell cost of goods sold for this order is $5. This metric is only available if you report conversions with cart data. This metric is a monetary value and returned in the customer's currency by default. See the metrics_currency parameter at https://developers.google.com/search-ads/reporting/query/query-structure#parameters_clause */
		clientAccountCrossSellCostOfGoodsSoldMicros: Option[String] = None,
	  /** Cross-sell cost of goods sold (COGS) is the total cost of products sold as a result of advertising a different product. How it works: You report conversions with cart data for completed purchases on your website. If the ad that was interacted with before the purchase has an associated product (see Shopping Ads) then this product is considered the advertised product. Any product included in the order the customer places is a sold product. If these products don't match then this is considered cross-sell. Cross-sell cost of goods sold is the total cost of the products sold that weren't advertised. Example: Someone clicked on a Shopping ad for a hat then bought the same hat and a shirt. The hat has a cost of goods sold value of $3, the shirt has a cost of goods sold value of $5. The cross-sell cost of goods sold for this order is $5. This metric is only available if you report conversions with cart data. This metric is a monetary value and returned in the customer's currency by default. See the metrics_currency parameter at https://developers.google.com/search-ads/reporting/query/query-structure#parameters_clause */
		crossSellCostOfGoodsSoldMicros: Option[String] = None,
	  /** Client account cross-sell gross profit is the profit you made from products sold as a result of advertising a different product, minus cost of goods sold (COGS). How it works: You report conversions with cart data for completed purchases on your website. If the ad that was interacted with before the purchase has an associated product (see Shopping Ads) then this product is considered the advertised product. Any product included in the purchase is a sold product. If these products don't match then this is considered cross-sell. Cross-sell gross profit is the revenue you made from cross-sell attributed to your ads minus the cost of the goods sold. Example: Someone clicked on a Shopping ad for a hat then bought the same hat and a shirt. The shirt is priced $20 and has a cost of goods sold value of $5. The cross-sell gross profit of this order is $15 = $20 - $5. This metric is only available if you report conversions with cart data. This metric is a monetary value and returned in the customer's currency by default. See the metrics_currency parameter at https://developers.google.com/search-ads/reporting/query/query-structure#parameters_clause */
		clientAccountCrossSellGrossProfitMicros: Option[String] = None,
	  /** Cross-sell gross profit is the profit you made from products sold as a result of advertising a different product, minus cost of goods sold (COGS). How it works: You report conversions with cart data for completed purchases on your website. If the ad that was interacted with before the purchase has an associated product (see Shopping Ads) then this product is considered the advertised product. Any product included in the purchase is a sold product. If these products don't match then this is considered cross-sell. Cross-sell gross profit is the revenue you made from cross-sell attributed to your ads minus the cost of the goods sold. Example: Someone clicked on a Shopping ad for a hat then bought the same hat and a shirt. The shirt is priced $20 and has a cost of goods sold value of $5. The cross-sell gross profit of this order is $15 = $20 - $5. This metric is only available if you report conversions with cart data. This metric is a monetary value and returned in the customer's currency by default. See the metrics_currency parameter at https://developers.google.com/search-ads/reporting/query/query-structure#parameters_clause */
		crossSellGrossProfitMicros: Option[String] = None,
	  /** Client account cross-sell revenue is the total amount you made from products sold as a result of advertising a different product. How it works: You report conversions with cart data for completed purchases on your website. If the ad that was interacted with before the purchase has an associated product (see Shopping Ads) then this product is considered the advertised product. Any product included in the order the customer places is a sold product. If these products don't match then this is considered cross-sell. Cross-sell revenue is the total value you made from cross-sell attributed to your ads. Example: Someone clicked on a Shopping ad for a hat then bought the same hat and a shirt. The hat is priced $10 and the shirt is priced $20. The cross-sell revenue of this order is $20. This metric is only available if you report conversions with cart data. This metric is a monetary value and returned in the customer's currency by default. See the metrics_currency parameter at https://developers.google.com/search-ads/reporting/query/query-structure#parameters_clause */
		clientAccountCrossSellRevenueMicros: Option[String] = None,
	  /** Cross-sell revenue is the total amount you made from products sold as a result of advertising a different product. How it works: You report conversions with cart data for completed purchases on your website. If the ad that was interacted with before the purchase has an associated product (see Shopping Ads) then this product is considered the advertised product. Any product included in the order the customer places is a sold product. If these products don't match then this is considered cross-sell. Cross-sell revenue is the total value you made from cross-sell attributed to your ads. Example: Someone clicked on a Shopping ad for a hat then bought the same hat and a shirt. The hat is priced $10 and the shirt is priced $20. The cross-sell revenue of this order is $20. This metric is only available if you report conversions with cart data. This metric is a monetary value and returned in the customer's currency by default. See the metrics_currency parameter at https://developers.google.com/search-ads/reporting/query/query-structure#parameters_clause */
		crossSellRevenueMicros: Option[String] = None,
	  /** Client account cross-sell units sold is the total number of products sold as a result of advertising a different product. How it works: You report conversions with cart data for completed purchases on your website. If the ad that was interacted with before the purchase has an associated product (see Shopping Ads) then this product is considered the advertised product. Any product included in the order the customer places is a sold product. If these products don't match then this is considered cross-sell. Cross-sell units sold is the total number of cross-sold products from all orders attributed to your ads. Example: Someone clicked on a Shopping ad for a hat then bought the same hat, a shirt and a jacket. The cross-sell units sold in this order is 2. This metric is only available if you report conversions with cart data. */
		clientAccountCrossSellUnitsSold: Option[BigDecimal] = None,
	  /** Cross-sell units sold is the total number of products sold as a result of advertising a different product. How it works: You report conversions with cart data for completed purchases on your website. If the ad that was interacted with before the purchase has an associated product (see Shopping Ads) then this product is considered the advertised product. Any product included in the order the customer places is a sold product. If these products don't match then this is considered cross-sell. Cross-sell units sold is the total number of cross-sold products from all orders attributed to your ads. Example: Someone clicked on a Shopping ad for a hat then bought the same hat, a shirt and a jacket. The cross-sell units sold in this order is 2. This metric is only available if you report conversions with cart data. */
		crossSellUnitsSold: Option[BigDecimal] = None,
	  /** Client account lead cost of goods sold (COGS) is the total cost of products sold as a result of advertising the same product. How it works: You report conversions with cart data for completed purchases on your website. If the ad that was interacted with has an associated product (see Shopping Ads) then this product is considered the advertised product. Any product included in the order the customer places is a sold product. If the advertised and sold products match, then the cost of these goods is counted under lead cost of goods sold. Example: Someone clicked on a Shopping ad for a hat then bought the same hat and a shirt. The hat has a cost of goods sold value of $3, the shirt has a cost of goods sold value of $5. The lead cost of goods sold for this order is $3. This metric is only available if you report conversions with cart data. This metric is a monetary value and returned in the customer's currency by default. See the metrics_currency parameter at https://developers.google.com/search-ads/reporting/query/query-structure#parameters_clause */
		clientAccountLeadCostOfGoodsSoldMicros: Option[String] = None,
	  /** Lead cost of goods sold (COGS) is the total cost of products sold as a result of advertising the same product. How it works: You report conversions with cart data for completed purchases on your website. If the ad that was interacted with has an associated product (see Shopping Ads) then this product is considered the advertised product. Any product included in the order the customer places is a sold product. If the advertised and sold products match, then the cost of these goods is counted under lead cost of goods sold. Example: Someone clicked on a Shopping ad for a hat then bought the same hat and a shirt. The hat has a cost of goods sold value of $3, the shirt has a cost of goods sold value of $5. The lead cost of goods sold for this order is $3. This metric is only available if you report conversions with cart data. This metric is a monetary value and returned in the customer's currency by default. See the metrics_currency parameter at https://developers.google.com/search-ads/reporting/query/query-structure#parameters_clause */
		leadCostOfGoodsSoldMicros: Option[String] = None,
	  /** Client account lead gross profit is the profit you made from products sold as a result of advertising the same product, minus cost of goods sold (COGS). How it works: You report conversions with cart data for completed purchases on your website. If the ad that was interacted with before the purchase has an associated product (see Shopping Ads) then this product is considered the advertised product. Any product included in the order the customer places is a sold product. If the advertised and sold products match, then the revenue you made from these sales minus the cost of goods sold is your lead gross profit. Example: Someone clicked on a Shopping ad for a hat then bought the same hat and a shirt. The hat is priced $10 and has a cost of goods sold value of $3. The lead gross profit of this order is $7 = $10 - $3. This metric is only available if you report conversions with cart data. This metric is a monetary value and returned in the customer's currency by default. See the metrics_currency parameter at https://developers.google.com/search-ads/reporting/query/query-structure#parameters_clause */
		clientAccountLeadGrossProfitMicros: Option[String] = None,
	  /** Lead gross profit is the profit you made from products sold as a result of advertising the same product, minus cost of goods sold (COGS). How it works: You report conversions with cart data for completed purchases on your website. If the ad that was interacted with before the purchase has an associated product (see Shopping Ads) then this product is considered the advertised product. Any product included in the order the customer places is a sold product. If the advertised and sold products match, then the revenue you made from these sales minus the cost of goods sold is your lead gross profit. Example: Someone clicked on a Shopping ad for a hat then bought the same hat and a shirt. The hat is priced $10 and has a cost of goods sold value of $3. The lead gross profit of this order is $7 = $10 - $3. This metric is only available if you report conversions with cart data. This metric is a monetary value and returned in the customer's currency by default. See the metrics_currency parameter at https://developers.google.com/search-ads/reporting/query/query-structure#parameters_clause */
		leadGrossProfitMicros: Option[String] = None,
	  /** Client account lead revenue is the total amount you made from products sold as a result of advertising the same product. How it works: You report conversions with cart data for completed purchases on your website. If the ad that was interacted with before the purchase has an associated product (see Shopping Ads) then this product is considered the advertised product. Any product included in the order the customer places is a sold product. If the advertised and sold products match, then the total value you made from the sales of these products is shown under lead revenue. Example: Someone clicked on a Shopping ad for a hat then bought the same hat and a shirt. The hat is priced $10 and the shirt is priced $20. The lead revenue of this order is $10. This metric is only available if you report conversions with cart data. This metric is a monetary value and returned in the customer's currency by default. See the metrics_currency parameter at https://developers.google.com/search-ads/reporting/query/query-structure#parameters_clause */
		clientAccountLeadRevenueMicros: Option[String] = None,
	  /** Lead revenue is the total amount you made from products sold as a result of advertising the same product. How it works: You report conversions with cart data for completed purchases on your website. If the ad that was interacted with before the purchase has an associated product (see Shopping Ads) then this product is considered the advertised product. Any product included in the order the customer places is a sold product. If the advertised and sold products match, then the total value you made from the sales of these products is shown under lead revenue. Example: Someone clicked on a Shopping ad for a hat then bought the same hat and a shirt. The hat is priced $10 and the shirt is priced $20. The lead revenue of this order is $10. This metric is only available if you report conversions with cart data. This metric is a monetary value and returned in the customer's currency by default. See the metrics_currency parameter at https://developers.google.com/search-ads/reporting/query/query-structure#parameters_clause */
		leadRevenueMicros: Option[String] = None,
	  /** Client account lead units sold is the total number of products sold as a result of advertising the same product. How it works: You report conversions with cart data for completed purchases on your website. If the ad that was interacted with before the purchase has an associated product (see Shopping Ads) then this product is considered the advertised product. Any product included in the order the customer places is a sold product. If the advertised and sold products match, then the total number of these products sold is shown under lead units sold. Example: Someone clicked on a Shopping ad for a hat then bought the same hat, a shirt and a jacket. The lead units sold in this order is 1. This metric is only available if you report conversions with cart data. */
		clientAccountLeadUnitsSold: Option[BigDecimal] = None,
	  /** Lead units sold is the total number of products sold as a result of advertising the same product. How it works: You report conversions with cart data for completed purchases on your website. If the ad that was interacted with before the purchase has an associated product (see Shopping Ads) then this product is considered the advertised product. Any product included in the order the customer places is a sold product. If the advertised and sold products match, then the total number of these products sold is shown under lead units sold. Example: Someone clicked on a Shopping ad for a hat then bought the same hat, a shirt and a jacket. The lead units sold in this order is 1. This metric is only available if you report conversions with cart data. */
		leadUnitsSold: Option[BigDecimal] = None
	)
	
	case class GoogleAdsSearchads360V0Common__Value(
	  /** A boolean. */
		booleanValue: Option[Boolean] = None,
	  /** An int64. */
		int64Value: Option[String] = None,
	  /** A float. */
		floatValue: Option[BigDecimal] = None,
	  /** A double. */
		doubleValue: Option[BigDecimal] = None,
	  /** A string. */
		stringValue: Option[String] = None
	)
	
	object GoogleAdsSearchads360V0Common__Segments {
		enum AdNetworkTypeEnum extends Enum[AdNetworkTypeEnum] { case UNSPECIFIED, UNKNOWN, SEARCH, SEARCH_PARTNERS, CONTENT, YOUTUBE_SEARCH, YOUTUBE_WATCH, MIXED }
		enum ConversionActionCategoryEnum extends Enum[ConversionActionCategoryEnum] { case UNSPECIFIED, UNKNOWN, DEFAULT, PAGE_VIEW, PURCHASE, SIGNUP, LEAD, DOWNLOAD, ADD_TO_CART, BEGIN_CHECKOUT, SUBSCRIBE_PAID, PHONE_CALL_LEAD, IMPORTED_LEAD, SUBMIT_LEAD_FORM, BOOK_APPOINTMENT, REQUEST_QUOTE, GET_DIRECTIONS, OUTBOUND_CLICK, CONTACT, ENGAGEMENT, STORE_VISIT, STORE_SALE, QUALIFIED_LEAD, CONVERTED_LEAD }
		enum DayOfWeekEnum extends Enum[DayOfWeekEnum] { case UNSPECIFIED, UNKNOWN, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
		enum DeviceEnum extends Enum[DeviceEnum] { case UNSPECIFIED, UNKNOWN, MOBILE, TABLET, DESKTOP, CONNECTED_TV, OTHER }
		enum ProductChannelEnum extends Enum[ProductChannelEnum] { case UNSPECIFIED, UNKNOWN, ONLINE, LOCAL }
		enum ProductChannelExclusivityEnum extends Enum[ProductChannelExclusivityEnum] { case UNSPECIFIED, UNKNOWN, SINGLE_CHANNEL, MULTI_CHANNEL }
		enum ProductConditionEnum extends Enum[ProductConditionEnum] { case UNSPECIFIED, UNKNOWN, OLD, NEW, REFURBISHED, USED }
		enum ProductSoldConditionEnum extends Enum[ProductSoldConditionEnum] { case UNSPECIFIED, UNKNOWN, OLD, NEW, REFURBISHED, USED }
	}
	case class GoogleAdsSearchads360V0Common__Segments(
	  /** Ad network type. */
		adNetworkType: Option[Schema.GoogleAdsSearchads360V0Common__Segments.AdNetworkTypeEnum] = None,
	  /** Resource name of the conversion action. */
		conversionAction: Option[String] = None,
	  /** Conversion action category. */
		conversionActionCategory: Option[Schema.GoogleAdsSearchads360V0Common__Segments.ConversionActionCategoryEnum] = None,
	  /** Conversion action name. */
		conversionActionName: Option[String] = None,
	  /** The conversion custom dimensions. */
		conversionCustomDimensions: Option[List[Schema.GoogleAdsSearchads360V0Common__Value]] = None,
	  /** Date to which metrics apply. yyyy-MM-dd format, for example, 2018-04-17. */
		date: Option[String] = None,
	  /** Day of the week, for example, MONDAY. */
		dayOfWeek: Option[Schema.GoogleAdsSearchads360V0Common__Segments.DayOfWeekEnum] = None,
	  /** Device to which metrics apply. */
		device: Option[Schema.GoogleAdsSearchads360V0Common__Segments.DeviceEnum] = None,
	  /** Resource name of the geo target constant that represents a city. */
		geoTargetCity: Option[String] = None,
	  /** Resource name of the geo target constant that represents a country. */
		geoTargetCountry: Option[String] = None,
	  /** Resource name of the geo target constant that represents a metro. */
		geoTargetMetro: Option[String] = None,
	  /** Resource name of the geo target constant that represents a region. */
		geoTargetRegion: Option[String] = None,
	  /** Hour of day as a number between 0 and 23, inclusive. */
		hour: Option[Int] = None,
	  /** Keyword criterion. */
		keyword: Option[Schema.GoogleAdsSearchads360V0Common__Keyword] = None,
	  /** Month as represented by the date of the first day of a month. Formatted as yyyy-MM-dd. */
		month: Option[String] = None,
	  /** Bidding category (level 1) of the product. */
		productBiddingCategoryLevel1: Option[String] = None,
	  /** Bidding category (level 2) of the product. */
		productBiddingCategoryLevel2: Option[String] = None,
	  /** Bidding category (level 3) of the product. */
		productBiddingCategoryLevel3: Option[String] = None,
	  /** Bidding category (level 4) of the product. */
		productBiddingCategoryLevel4: Option[String] = None,
	  /** Bidding category (level 5) of the product. */
		productBiddingCategoryLevel5: Option[String] = None,
	  /** Brand of the product. */
		productBrand: Option[String] = None,
	  /** Channel of the product. */
		productChannel: Option[Schema.GoogleAdsSearchads360V0Common__Segments.ProductChannelEnum] = None,
	  /** Channel exclusivity of the product. */
		productChannelExclusivity: Option[Schema.GoogleAdsSearchads360V0Common__Segments.ProductChannelExclusivityEnum] = None,
	  /** Condition of the product. */
		productCondition: Option[Schema.GoogleAdsSearchads360V0Common__Segments.ProductConditionEnum] = None,
	  /** Resource name of the geo target constant for the country of sale of the product. */
		productCountry: Option[String] = None,
	  /** Custom attribute 0 of the product. */
		productCustomAttribute0: Option[String] = None,
	  /** Custom attribute 1 of the product. */
		productCustomAttribute1: Option[String] = None,
	  /** Custom attribute 2 of the product. */
		productCustomAttribute2: Option[String] = None,
	  /** Custom attribute 3 of the product. */
		productCustomAttribute3: Option[String] = None,
	  /** Custom attribute 4 of the product. */
		productCustomAttribute4: Option[String] = None,
	  /** Item ID of the product. */
		productItemId: Option[String] = None,
	  /** Resource name of the language constant for the language of the product. */
		productLanguage: Option[String] = None,
	  /** Bidding category (level 1) of the product sold. */
		productSoldBiddingCategoryLevel1: Option[String] = None,
	  /** Bidding category (level 2) of the product sold. */
		productSoldBiddingCategoryLevel2: Option[String] = None,
	  /** Bidding category (level 3) of the product sold. */
		productSoldBiddingCategoryLevel3: Option[String] = None,
	  /** Bidding category (level 4) of the product sold. */
		productSoldBiddingCategoryLevel4: Option[String] = None,
	  /** Bidding category (level 5) of the product sold. */
		productSoldBiddingCategoryLevel5: Option[String] = None,
	  /** Brand of the product sold. */
		productSoldBrand: Option[String] = None,
	  /** Condition of the product sold. */
		productSoldCondition: Option[Schema.GoogleAdsSearchads360V0Common__Segments.ProductSoldConditionEnum] = None,
	  /** Custom attribute 0 of the product sold. */
		productSoldCustomAttribute0: Option[String] = None,
	  /** Custom attribute 1 of the product sold. */
		productSoldCustomAttribute1: Option[String] = None,
	  /** Custom attribute 2 of the product sold. */
		productSoldCustomAttribute2: Option[String] = None,
	  /** Custom attribute 3 of the product sold. */
		productSoldCustomAttribute3: Option[String] = None,
	  /** Custom attribute 4 of the product sold. */
		productSoldCustomAttribute4: Option[String] = None,
	  /** Item ID of the product sold. */
		productSoldItemId: Option[String] = None,
	  /** Title of the product sold. */
		productSoldTitle: Option[String] = None,
	  /** Type (level 1) of the product sold. */
		productSoldTypeL1: Option[String] = None,
	  /** Type (level 2) of the product sold. */
		productSoldTypeL2: Option[String] = None,
	  /** Type (level 3) of the product sold. */
		productSoldTypeL3: Option[String] = None,
	  /** Type (level 4) of the product sold. */
		productSoldTypeL4: Option[String] = None,
	  /** Type (level 5) of the product sold. */
		productSoldTypeL5: Option[String] = None,
	  /** Store ID of the product. */
		productStoreId: Option[String] = None,
	  /** Title of the product. */
		productTitle: Option[String] = None,
	  /** Type (level 1) of the product. */
		productTypeL1: Option[String] = None,
	  /** Type (level 2) of the product. */
		productTypeL2: Option[String] = None,
	  /** Type (level 3) of the product. */
		productTypeL3: Option[String] = None,
	  /** Type (level 4) of the product. */
		productTypeL4: Option[String] = None,
	  /** Type (level 5) of the product. */
		productTypeL5: Option[String] = None,
	  /** Quarter as represented by the date of the first day of a quarter. Uses the calendar year for quarters, for example, the second quarter of 2018 starts on 2018-04-01. Formatted as yyyy-MM-dd. */
		quarter: Option[String] = None,
	  /** The raw event conversion dimensions. */
		rawEventConversionDimensions: Option[List[Schema.GoogleAdsSearchads360V0Common__Value]] = None,
	  /** Week as defined as Monday through Sunday, and represented by the date of Monday. Formatted as yyyy-MM-dd. */
		week: Option[String] = None,
	  /** Year, formatted as yyyy. */
		year: Option[Int] = None,
	  /** Only used with CustomerAsset, CampaignAsset and AdGroupAsset metrics. Indicates whether the interaction metrics occurred on the asset itself or a different asset or ad unit. Interactions (for example, clicks) are counted across all the parts of the served ad (for example, Ad itself and other components like Sitelinks) when they are served together. When interaction_on_this_asset is true, it means the interactions are on this specific asset and when interaction_on_this_asset is false, it means the interactions is not on this specific asset but on other parts of the served ad this asset is served with. */
		assetInteractionTarget: Option[Schema.GoogleAdsSearchads360V0Common__AssetInteractionTarget] = None
	)
	
	case class GoogleAdsSearchads360V0Common__Keyword(
	  /** The AdGroupCriterion resource name. */
		adGroupCriterion: Option[String] = None,
	  /** Keyword info. */
		info: Option[Schema.GoogleAdsSearchads360V0Common__KeywordInfo] = None
	)
	
	case class GoogleAdsSearchads360V0Common__AssetInteractionTarget(
	  /** The asset resource name. */
		asset: Option[String] = None,
	  /** Only used with CustomerAsset, CampaignAsset and AdGroupAsset metrics. Indicates whether the interaction metrics occurred on the asset itself or a different asset or ad unit. */
		interactionOnThisAsset: Option[Boolean] = None
	)
	
	case class GoogleAdsSearchads360V0Services__CustomColumnHeader(
	  /** The custom column ID. */
		id: Option[String] = None,
	  /** The user defined name of the custom column. */
		name: Option[String] = None,
	  /** True when the custom column references metrics. */
		referencesMetrics: Option[Boolean] = None
	)
	
	case class GoogleAdsSearchads360V0Services__ConversionCustomMetricHeader(
	  /** The conversion custom metric ID. */
		id: Option[String] = None,
	  /** The user defined name of the conversion custom metric. */
		name: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Services__ConversionCustomDimensionHeader(
	  /** The conversion custom dimension ID. */
		id: Option[String] = None,
	  /** The user defined name of the conversion custom dimension. */
		name: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Services__RawEventConversionMetricHeader(
	  /** The conversion custom variable ID. */
		id: Option[String] = None,
	  /** The user defined name of the raw event metric. */
		name: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Services__RawEventConversionDimensionHeader(
	  /** The conversion custom variable ID. */
		id: Option[String] = None,
	  /** The user defined name of the raw event dimension. */
		name: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Errors__SearchAds360Failure(
	  /** The list of errors that occurred. */
		errors: Option[List[Schema.GoogleAdsSearchads360V0Errors__SearchAds360Error]] = None,
	  /** The unique ID of the request that is used for debugging purposes. */
		requestId: Option[String] = None
	)
	
	case class GoogleAdsSearchads360V0Errors__SearchAds360Error(
	  /** An enum value that indicates which error occurred. */
		errorCode: Option[Schema.GoogleAdsSearchads360V0Errors__ErrorCode] = None,
	  /** A human-readable description of the error. */
		message: Option[String] = None,
	  /** The value that triggered the error. */
		trigger: Option[Schema.GoogleAdsSearchads360V0Common__Value] = None,
	  /** Describes the part of the request proto that caused the error. */
		location: Option[Schema.GoogleAdsSearchads360V0Errors__ErrorLocation] = None,
	  /** Additional error details, which are returned by certain error codes. Most error codes do not include details. */
		details: Option[Schema.GoogleAdsSearchads360V0Errors__ErrorDetails] = None
	)
	
	object GoogleAdsSearchads360V0Errors__ErrorCode {
		enum RequestErrorEnum extends Enum[RequestErrorEnum] { case UNSPECIFIED, UNKNOWN, RESOURCE_NAME_MISSING, RESOURCE_NAME_MALFORMED, BAD_RESOURCE_ID, INVALID_PRODUCT_NAME, INVALID_CUSTOMER_ID, OPERATION_REQUIRED, RESOURCE_NOT_FOUND, INVALID_PAGE_TOKEN, EXPIRED_PAGE_TOKEN, INVALID_PAGE_SIZE, REQUIRED_FIELD_MISSING, IMMUTABLE_FIELD, TOO_MANY_MUTATE_OPERATIONS, CANNOT_BE_EXECUTED_BY_MANAGER_ACCOUNT, CANNOT_MODIFY_FOREIGN_FIELD, INVALID_ENUM_VALUE, LOGIN_CUSTOMER_ID_PARAMETER_MISSING, LOGIN_OR_LINKED_CUSTOMER_ID_PARAMETER_REQUIRED, VALIDATE_ONLY_REQUEST_HAS_PAGE_TOKEN, CANNOT_RETURN_SUMMARY_ROW_FOR_REQUEST_WITHOUT_METRICS, CANNOT_RETURN_SUMMARY_ROW_FOR_VALIDATE_ONLY_REQUESTS, INCONSISTENT_RETURN_SUMMARY_ROW_VALUE, TOTAL_RESULTS_COUNT_NOT_ORIGINALLY_REQUESTED, RPC_DEADLINE_TOO_SHORT, PRODUCT_NOT_SUPPORTED }
		enum QueryErrorEnum extends Enum[QueryErrorEnum] { case UNSPECIFIED, UNKNOWN, QUERY_ERROR, BAD_ENUM_CONSTANT, BAD_ESCAPE_SEQUENCE, BAD_FIELD_NAME, BAD_LIMIT_VALUE, BAD_NUMBER, BAD_OPERATOR, BAD_PARAMETER_NAME, BAD_PARAMETER_VALUE, BAD_RESOURCE_TYPE_IN_FROM_CLAUSE, BAD_SYMBOL, BAD_VALUE, DATE_RANGE_TOO_WIDE, DATE_RANGE_TOO_NARROW, EXPECTED_AND, EXPECTED_BY, EXPECTED_DIMENSION_FIELD_IN_SELECT_CLAUSE, EXPECTED_FILTERS_ON_DATE_RANGE, EXPECTED_FROM, EXPECTED_LIST, EXPECTED_REFERENCED_FIELD_IN_SELECT_CLAUSE, EXPECTED_SELECT, EXPECTED_SINGLE_VALUE, EXPECTED_VALUE_WITH_BETWEEN_OPERATOR, INVALID_DATE_FORMAT, MISALIGNED_DATE_FOR_FILTER, INVALID_STRING_VALUE, INVALID_VALUE_WITH_BETWEEN_OPERATOR, INVALID_VALUE_WITH_DURING_OPERATOR, INVALID_VALUE_WITH_LIKE_OPERATOR, OPERATOR_FIELD_MISMATCH, PROHIBITED_EMPTY_LIST_IN_CONDITION, PROHIBITED_ENUM_CONSTANT, PROHIBITED_FIELD_COMBINATION_IN_SELECT_CLAUSE, PROHIBITED_FIELD_IN_ORDER_BY_CLAUSE, PROHIBITED_FIELD_IN_SELECT_CLAUSE, PROHIBITED_FIELD_IN_WHERE_CLAUSE, PROHIBITED_RESOURCE_TYPE_IN_FROM_CLAUSE, PROHIBITED_RESOURCE_TYPE_IN_SELECT_CLAUSE, PROHIBITED_RESOURCE_TYPE_IN_WHERE_CLAUSE, PROHIBITED_METRIC_IN_SELECT_OR_WHERE_CLAUSE, PROHIBITED_SEGMENT_IN_SELECT_OR_WHERE_CLAUSE, PROHIBITED_SEGMENT_WITH_METRIC_IN_SELECT_OR_WHERE_CLAUSE, LIMIT_VALUE_TOO_LOW, PROHIBITED_NEWLINE_IN_STRING, PROHIBITED_VALUE_COMBINATION_IN_LIST, PROHIBITED_VALUE_COMBINATION_WITH_BETWEEN_OPERATOR, STRING_NOT_TERMINATED, TOO_MANY_SEGMENTS, UNEXPECTED_END_OF_QUERY, UNEXPECTED_FROM_CLAUSE, UNRECOGNIZED_FIELD, UNEXPECTED_INPUT, REQUESTED_METRICS_FOR_MANAGER, FILTER_HAS_TOO_MANY_VALUES }
		enum AuthorizationErrorEnum extends Enum[AuthorizationErrorEnum] { case UNSPECIFIED, UNKNOWN, USER_PERMISSION_DENIED, PROJECT_DISABLED, AUTHORIZATION_ERROR, ACTION_NOT_PERMITTED, INCOMPLETE_SIGNUP, CUSTOMER_NOT_ENABLED, MISSING_TOS, INVALID_LOGIN_CUSTOMER_ID_SERVING_CUSTOMER_ID_COMBINATION, SERVICE_ACCESS_DENIED, ACCESS_DENIED_FOR_ACCOUNT_TYPE, METRIC_ACCESS_DENIED }
		enum InternalErrorEnum extends Enum[InternalErrorEnum] { case UNSPECIFIED, UNKNOWN, INTERNAL_ERROR, ERROR_CODE_NOT_PUBLISHED, TRANSIENT_ERROR, DEADLINE_EXCEEDED }
		enum QuotaErrorEnum extends Enum[QuotaErrorEnum] { case UNSPECIFIED, UNKNOWN, RESOURCE_EXHAUSTED, RESOURCE_TEMPORARILY_EXHAUSTED }
		enum AuthenticationErrorEnum extends Enum[AuthenticationErrorEnum] { case UNSPECIFIED, UNKNOWN, AUTHENTICATION_ERROR, CLIENT_CUSTOMER_ID_INVALID, CUSTOMER_NOT_FOUND, GOOGLE_ACCOUNT_DELETED, GOOGLE_ACCOUNT_COOKIE_INVALID, GOOGLE_ACCOUNT_AUTHENTICATION_FAILED, GOOGLE_ACCOUNT_USER_AND_ADS_USER_MISMATCH, LOGIN_COOKIE_REQUIRED, NOT_ADS_USER, OAUTH_TOKEN_INVALID, OAUTH_TOKEN_EXPIRED, OAUTH_TOKEN_DISABLED, OAUTH_TOKEN_REVOKED, OAUTH_TOKEN_HEADER_INVALID, LOGIN_COOKIE_INVALID, USER_ID_INVALID, TWO_STEP_VERIFICATION_NOT_ENROLLED, ADVANCED_PROTECTION_NOT_ENROLLED }
		enum DateErrorEnum extends Enum[DateErrorEnum] { case UNSPECIFIED, UNKNOWN, INVALID_FIELD_VALUES_IN_DATE, INVALID_FIELD_VALUES_IN_DATE_TIME, INVALID_STRING_DATE, INVALID_STRING_DATE_TIME_MICROS, INVALID_STRING_DATE_TIME_SECONDS, INVALID_STRING_DATE_TIME_SECONDS_WITH_OFFSET, EARLIER_THAN_MINIMUM_DATE, LATER_THAN_MAXIMUM_DATE, DATE_RANGE_MINIMUM_DATE_LATER_THAN_MAXIMUM_DATE, DATE_RANGE_MINIMUM_AND_MAXIMUM_DATES_BOTH_NULL }
		enum DateRangeErrorEnum extends Enum[DateRangeErrorEnum] { case UNSPECIFIED, UNKNOWN, INVALID_DATE, START_DATE_AFTER_END_DATE, CANNOT_SET_DATE_TO_PAST, AFTER_MAXIMUM_ALLOWABLE_DATE, CANNOT_MODIFY_START_DATE_IF_ALREADY_STARTED }
		enum DistinctErrorEnum extends Enum[DistinctErrorEnum] { case UNSPECIFIED, UNKNOWN, DUPLICATE_ELEMENT, DUPLICATE_TYPE }
		enum HeaderErrorEnum extends Enum[HeaderErrorEnum] { case UNSPECIFIED, UNKNOWN, INVALID_USER_SELECTED_CUSTOMER_ID, INVALID_LOGIN_CUSTOMER_ID }
		enum SizeLimitErrorEnum extends Enum[SizeLimitErrorEnum] { case UNSPECIFIED, UNKNOWN, REQUEST_SIZE_LIMIT_EXCEEDED, RESPONSE_SIZE_LIMIT_EXCEEDED }
		enum CustomColumnErrorEnum extends Enum[CustomColumnErrorEnum] { case UNSPECIFIED, UNKNOWN, CUSTOM_COLUMN_NOT_FOUND, CUSTOM_COLUMN_NOT_AVAILABLE }
		enum InvalidParameterErrorEnum extends Enum[InvalidParameterErrorEnum] { case UNSPECIFIED, UNKNOWN, INVALID_CURRENCY_CODE }
	}
	case class GoogleAdsSearchads360V0Errors__ErrorCode(
	  /** An error caused by the request */
		requestError: Option[Schema.GoogleAdsSearchads360V0Errors__ErrorCode.RequestErrorEnum] = None,
	  /** An error with the query */
		queryError: Option[Schema.GoogleAdsSearchads360V0Errors__ErrorCode.QueryErrorEnum] = None,
	  /** An error encountered when trying to authorize a user. */
		authorizationError: Option[Schema.GoogleAdsSearchads360V0Errors__ErrorCode.AuthorizationErrorEnum] = None,
	  /** An unexpected server-side error. */
		internalError: Option[Schema.GoogleAdsSearchads360V0Errors__ErrorCode.InternalErrorEnum] = None,
	  /** An error with the amount of quota remaining. */
		quotaError: Option[Schema.GoogleAdsSearchads360V0Errors__ErrorCode.QuotaErrorEnum] = None,
	  /** Indicates failure to properly authenticate user. */
		authenticationError: Option[Schema.GoogleAdsSearchads360V0Errors__ErrorCode.AuthenticationErrorEnum] = None,
	  /** The reasons for the date error */
		dateError: Option[Schema.GoogleAdsSearchads360V0Errors__ErrorCode.DateErrorEnum] = None,
	  /** The reasons for the date range error */
		dateRangeError: Option[Schema.GoogleAdsSearchads360V0Errors__ErrorCode.DateRangeErrorEnum] = None,
	  /** The reasons for the distinct error */
		distinctError: Option[Schema.GoogleAdsSearchads360V0Errors__ErrorCode.DistinctErrorEnum] = None,
	  /** The reasons for the header error. */
		headerError: Option[Schema.GoogleAdsSearchads360V0Errors__ErrorCode.HeaderErrorEnum] = None,
	  /** The reasons for the size limit error */
		sizeLimitError: Option[Schema.GoogleAdsSearchads360V0Errors__ErrorCode.SizeLimitErrorEnum] = None,
	  /** The reasons for the custom column error */
		customColumnError: Option[Schema.GoogleAdsSearchads360V0Errors__ErrorCode.CustomColumnErrorEnum] = None,
	  /** The reasons for invalid parameter errors. */
		invalidParameterError: Option[Schema.GoogleAdsSearchads360V0Errors__ErrorCode.InvalidParameterErrorEnum] = None
	)
	
	case class GoogleAdsSearchads360V0Errors__ErrorLocation(
	  /** A field path that indicates which field was invalid in the request. */
		fieldPathElements: Option[List[Schema.GoogleAdsSearchads360V0Errors_ErrorLocation_FieldPathElement]] = None
	)
	
	case class GoogleAdsSearchads360V0Errors_ErrorLocation_FieldPathElement(
	  /** The name of a field or a oneof */
		fieldName: Option[String] = None,
	  /** If field_name is a repeated field, this is the element that failed */
		index: Option[Int] = None
	)
	
	case class GoogleAdsSearchads360V0Errors__ErrorDetails(
	  /** The error code that should have been returned, but wasn't. This is used when the error code is not published in the client specified version. */
		unpublishedErrorCode: Option[String] = None,
	  /** Details on the quota error, including the scope (account or developer), the rate bucket name and the retry delay. */
		quotaErrorDetails: Option[Schema.GoogleAdsSearchads360V0Errors__QuotaErrorDetails] = None
	)
	
	object GoogleAdsSearchads360V0Errors__QuotaErrorDetails {
		enum RateScopeEnum extends Enum[RateScopeEnum] { case UNSPECIFIED, UNKNOWN, ACCOUNT, DEVELOPER }
	}
	case class GoogleAdsSearchads360V0Errors__QuotaErrorDetails(
	  /** The rate scope of the quota limit. */
		rateScope: Option[Schema.GoogleAdsSearchads360V0Errors__QuotaErrorDetails.RateScopeEnum] = None,
	  /** The high level description of the quota bucket. Examples are "Get requests for standard access" or "Requests per account". */
		rateName: Option[String] = None,
	  /** Backoff period that customers should wait before sending next request. */
		retryDelay: Option[String] = None
	)
}
