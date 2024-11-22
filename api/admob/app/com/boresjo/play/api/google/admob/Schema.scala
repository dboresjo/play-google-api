package com.boresjo.play.api.google.admob

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class PublisherAccount(
	  /** Resource name of this account. Format is accounts/{publisher_id}. */
		name: Option[String] = None,
	  /** The unique ID by which this publisher account can be identified in the API requests (for example, pub-1234567890). */
		publisherId: Option[String] = None,
	  /** The time zone that is used in reports that are generated for this account. The value is a time-zone ID as specified by the CLDR project, for example, "America/Los_Angeles". */
		reportingTimeZone: Option[String] = None,
	  /** Currency code of the earning-related metrics, which is the 3-letter code defined in ISO 4217. The daily average rate is used for the currency conversion. */
		currencyCode: Option[String] = None
	)
	
	case class ListPublisherAccountsResponse(
	  /** Publisher that the client credentials can access. */
		account: Option[List[Schema.PublisherAccount]] = None,
	  /** If not empty, indicates that there might be more accounts for the request; you must pass this value in a new `ListPublisherAccountsRequest`. */
		nextPageToken: Option[String] = None
	)
	
	case class GenerateNetworkReportRequest(
	  /** Network report specification. */
		reportSpec: Option[Schema.NetworkReportSpec] = None
	)
	
	object NetworkReportSpec {
		enum DimensionsEnum extends Enum[DimensionsEnum] { case DIMENSION_UNSPECIFIED, DATE, MONTH, WEEK, AD_UNIT, APP, AD_TYPE, COUNTRY, FORMAT, PLATFORM, MOBILE_OS_VERSION, GMA_SDK_VERSION, APP_VERSION_NAME, SERVING_RESTRICTION }
		enum MetricsEnum extends Enum[MetricsEnum] { case METRIC_UNSPECIFIED, AD_REQUESTS, CLICKS, ESTIMATED_EARNINGS, IMPRESSIONS, IMPRESSION_CTR, IMPRESSION_RPM, MATCHED_REQUESTS, MATCH_RATE, SHOW_RATE }
	}
	case class NetworkReportSpec(
	  /** The date range for which the report is generated. */
		dateRange: Option[Schema.DateRange] = None,
	  /** List of dimensions of the report. The value combination of these dimensions determines the row of the report. If no dimensions are specified, the report returns a single row of requested metrics for the entire account. */
		dimensions: Option[List[Schema.NetworkReportSpec.DimensionsEnum]] = None,
	  /** List of metrics of the report. A report must specify at least one metric. */
		metrics: Option[List[Schema.NetworkReportSpec.MetricsEnum]] = None,
	  /** Describes which report rows to match based on their dimension values. */
		dimensionFilters: Option[List[Schema.NetworkReportSpecDimensionFilter]] = None,
	  /** Describes the sorting of report rows. The order of the condition in the list defines its precedence; the earlier the condition, the higher its precedence. If no sort conditions are specified, the row ordering is undefined. */
		sortConditions: Option[List[Schema.NetworkReportSpecSortCondition]] = None,
	  /** Localization settings of the report. */
		localizationSettings: Option[Schema.LocalizationSettings] = None,
	  /** Maximum number of report data rows to return. If the value is not set, the API returns as many rows as possible, up to 100000. Acceptable values are 1-100000, inclusive. Values larger than 100000 return an error. */
		maxReportRows: Option[Int] = None,
	  /** A report time zone. Accepts an IANA TZ name values, such as "America/Los_Angeles." If no time zone is defined, the account default takes effect. Check default value by the get account action. &#42;&#42;Warning:&#42;&#42; The "America/Los_Angeles" is the only supported value at the moment. */
		timeZone: Option[String] = None
	)
	
	case class DateRange(
	  /** Start date of the date range, inclusive. Must be less than or equal to the end date. */
		startDate: Option[Schema.Date] = None,
	  /** End date of the date range, inclusive. Must be greater than or equal to the start date. */
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
	
	object NetworkReportSpecDimensionFilter {
		enum DimensionEnum extends Enum[DimensionEnum] { case DIMENSION_UNSPECIFIED, DATE, MONTH, WEEK, AD_UNIT, APP, AD_TYPE, COUNTRY, FORMAT, PLATFORM, MOBILE_OS_VERSION, GMA_SDK_VERSION, APP_VERSION_NAME, SERVING_RESTRICTION }
	}
	case class NetworkReportSpecDimensionFilter(
	  /** Matches a row if its value for the specified dimension is in one of the values specified in this condition. */
		matchesAny: Option[Schema.StringList] = None,
	  /** Applies the filter criterion to the specified dimension. */
		dimension: Option[Schema.NetworkReportSpecDimensionFilter.DimensionEnum] = None
	)
	
	case class StringList(
	  /** The string values. */
		values: Option[List[String]] = None
	)
	
	object NetworkReportSpecSortCondition {
		enum DimensionEnum extends Enum[DimensionEnum] { case DIMENSION_UNSPECIFIED, DATE, MONTH, WEEK, AD_UNIT, APP, AD_TYPE, COUNTRY, FORMAT, PLATFORM, MOBILE_OS_VERSION, GMA_SDK_VERSION, APP_VERSION_NAME, SERVING_RESTRICTION }
		enum MetricEnum extends Enum[MetricEnum] { case METRIC_UNSPECIFIED, AD_REQUESTS, CLICKS, ESTIMATED_EARNINGS, IMPRESSIONS, IMPRESSION_CTR, IMPRESSION_RPM, MATCHED_REQUESTS, MATCH_RATE, SHOW_RATE }
		enum OrderEnum extends Enum[OrderEnum] { case SORT_ORDER_UNSPECIFIED, ASCENDING, DESCENDING }
	}
	case class NetworkReportSpecSortCondition(
	  /** Sort by the specified dimension. */
		dimension: Option[Schema.NetworkReportSpecSortCondition.DimensionEnum] = None,
	  /** Sort by the specified metric. */
		metric: Option[Schema.NetworkReportSpecSortCondition.MetricEnum] = None,
	  /** Sorting order of the dimension or metric. */
		order: Option[Schema.NetworkReportSpecSortCondition.OrderEnum] = None
	)
	
	case class LocalizationSettings(
	  /** Currency code of the earning related metrics, which is the 3-letter code defined in ISO 4217. The daily average rate is used for the currency conversion. Defaults to the account currency code if unspecified. */
		currencyCode: Option[String] = None,
	  /** Language used for any localized text, such as some dimension value display labels. The language tag defined in the IETF BCP47. Defaults to 'en-US' if unspecified. */
		languageCode: Option[String] = None
	)
	
	case class GenerateNetworkReportResponse(
	  /** Report generation settings that describes the report contents, such as the report date range and localization settings. */
		header: Option[Schema.ReportHeader] = None,
	  /** Actual report data. */
		row: Option[Schema.ReportRow] = None,
	  /** Additional information about the generated report, such as warnings about the data. */
		footer: Option[Schema.ReportFooter] = None
	)
	
	case class ReportHeader(
	  /** The date range for which the report is generated. This is identical to the range specified in the report request. */
		dateRange: Option[Schema.DateRange] = None,
	  /** Localization settings of the report. This is identical to the settings in the report request. */
		localizationSettings: Option[Schema.LocalizationSettings] = None,
	  /** The report time zone. The value is a time-zone ID as specified by the CLDR project, for example, "America/Los_Angeles". */
		reportingTimeZone: Option[String] = None
	)
	
	case class ReportRow(
	  /** Map of dimension values in a row, with keys as enum name of the dimensions. */
		dimensionValues: Option[Map[String, Schema.ReportRowDimensionValue]] = None,
	  /** Map of metric values in a row, with keys as enum name of the metrics. If a metric being requested has no value returned, the map will not include it. */
		metricValues: Option[Map[String, Schema.ReportRowMetricValue]] = None
	)
	
	case class ReportRowDimensionValue(
	  /** Dimension value in the format specified in the report's spec Dimension enum. */
		value: Option[String] = None,
	  /** The localized string representation of the value. If unspecified, the display label should be derived from the value. */
		displayLabel: Option[String] = None
	)
	
	case class ReportRowMetricValue(
	  /** Metric integer value. */
		integerValue: Option[String] = None,
	  /** Double precision (approximate) decimal values. Rates are from 0 to 1. */
		doubleValue: Option[BigDecimal] = None,
	  /** Amount in micros. One million is equivalent to one unit. Currency value is in the unit (USD, EUR or other) specified by the request. For example, $6.50 whould be represented as 6500000 micros. */
		microsValue: Option[String] = None
	)
	
	case class ReportFooter(
	  /** Warnings associated with generation of the report. */
		warnings: Option[List[Schema.ReportWarning]] = None,
	  /** Total number of rows that matched the request. Warning: This count does NOT always match the number of rows in the response. Do not make that assumption when processing the response. */
		matchingRowCount: Option[String] = None
	)
	
	object ReportWarning {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, DATA_BEFORE_ACCOUNT_TIMEZONE_CHANGE, DATA_DELAYED, OTHER, REPORT_CURRENCY_NOT_ACCOUNT_CURRENCY }
	}
	case class ReportWarning(
	  /** Type of the warning. */
		`type`: Option[Schema.ReportWarning.TypeEnum] = None,
	  /** Describes the details of the warning message, in English. */
		description: Option[String] = None
	)
	
	case class GenerateMediationReportRequest(
	  /** Network report specification. */
		reportSpec: Option[Schema.MediationReportSpec] = None
	)
	
	object MediationReportSpec {
		enum DimensionsEnum extends Enum[DimensionsEnum] { case DIMENSION_UNSPECIFIED, DATE, MONTH, WEEK, AD_SOURCE, AD_SOURCE_INSTANCE, AD_UNIT, APP, MEDIATION_GROUP, COUNTRY, FORMAT, PLATFORM, MOBILE_OS_VERSION, GMA_SDK_VERSION, APP_VERSION_NAME, SERVING_RESTRICTION }
		enum MetricsEnum extends Enum[MetricsEnum] { case METRIC_UNSPECIFIED, AD_REQUESTS, CLICKS, ESTIMATED_EARNINGS, IMPRESSIONS, IMPRESSION_CTR, MATCHED_REQUESTS, MATCH_RATE, OBSERVED_ECPM }
	}
	case class MediationReportSpec(
	  /** The date range for which the report is generated. */
		dateRange: Option[Schema.DateRange] = None,
	  /** List of dimensions of the report. The value combination of these dimensions determines the row of the report. If no dimensions are specified, the report returns a single row of requested metrics for the entire account. */
		dimensions: Option[List[Schema.MediationReportSpec.DimensionsEnum]] = None,
	  /** List of metrics of the report. A report must specify at least one metric. */
		metrics: Option[List[Schema.MediationReportSpec.MetricsEnum]] = None,
	  /** Describes which report rows to match based on their dimension values. */
		dimensionFilters: Option[List[Schema.MediationReportSpecDimensionFilter]] = None,
	  /** Describes the sorting of report rows. The order of the condition in the list defines its precedence; the earlier the condition, the higher its precedence. If no sort conditions are specified, the row ordering is undefined. */
		sortConditions: Option[List[Schema.MediationReportSpecSortCondition]] = None,
	  /** Localization settings of the report. */
		localizationSettings: Option[Schema.LocalizationSettings] = None,
	  /** Maximum number of report data rows to return. If the value is not set, the API returns as many rows as possible, up to 100000. Acceptable values are 1-100000, inclusive. Values larger than 100000 return an error. */
		maxReportRows: Option[Int] = None,
	  /** A report time zone. Accepts an IANA TZ name values, such as "America/Los_Angeles." If no time zone is defined, the account default takes effect. Check default value by the get account action. &#42;&#42;Warning:&#42;&#42; The "America/Los_Angeles" is the only supported value at the moment. */
		timeZone: Option[String] = None
	)
	
	object MediationReportSpecDimensionFilter {
		enum DimensionEnum extends Enum[DimensionEnum] { case DIMENSION_UNSPECIFIED, DATE, MONTH, WEEK, AD_SOURCE, AD_SOURCE_INSTANCE, AD_UNIT, APP, MEDIATION_GROUP, COUNTRY, FORMAT, PLATFORM, MOBILE_OS_VERSION, GMA_SDK_VERSION, APP_VERSION_NAME, SERVING_RESTRICTION }
	}
	case class MediationReportSpecDimensionFilter(
	  /** Matches a row if its value for the specified dimension is in one of the values specified in this condition. */
		matchesAny: Option[Schema.StringList] = None,
	  /** Applies the filter criterion to the specified dimension. */
		dimension: Option[Schema.MediationReportSpecDimensionFilter.DimensionEnum] = None
	)
	
	object MediationReportSpecSortCondition {
		enum DimensionEnum extends Enum[DimensionEnum] { case DIMENSION_UNSPECIFIED, DATE, MONTH, WEEK, AD_SOURCE, AD_SOURCE_INSTANCE, AD_UNIT, APP, MEDIATION_GROUP, COUNTRY, FORMAT, PLATFORM, MOBILE_OS_VERSION, GMA_SDK_VERSION, APP_VERSION_NAME, SERVING_RESTRICTION }
		enum MetricEnum extends Enum[MetricEnum] { case METRIC_UNSPECIFIED, AD_REQUESTS, CLICKS, ESTIMATED_EARNINGS, IMPRESSIONS, IMPRESSION_CTR, MATCHED_REQUESTS, MATCH_RATE, OBSERVED_ECPM }
		enum OrderEnum extends Enum[OrderEnum] { case SORT_ORDER_UNSPECIFIED, ASCENDING, DESCENDING }
	}
	case class MediationReportSpecSortCondition(
	  /** Sort by the specified dimension. */
		dimension: Option[Schema.MediationReportSpecSortCondition.DimensionEnum] = None,
	  /** Sort by the specified metric. */
		metric: Option[Schema.MediationReportSpecSortCondition.MetricEnum] = None,
	  /** Sorting order of the dimension or metric. */
		order: Option[Schema.MediationReportSpecSortCondition.OrderEnum] = None
	)
	
	case class GenerateMediationReportResponse(
	  /** Report generation settings that describes the report contents, such as the report date range and localization settings. */
		header: Option[Schema.ReportHeader] = None,
	  /** Actual report data. */
		row: Option[Schema.ReportRow] = None,
	  /** Additional information about the generated report, such as warnings about the data. */
		footer: Option[Schema.ReportFooter] = None
	)
	
	case class ListAppsResponse(
	  /** The resulting apps for the requested account. */
		apps: Option[List[Schema.App]] = None,
	  /** If not empty, indicates that there may be more apps for the request; this value should be passed in a new `ListAppsRequest`. */
		nextPageToken: Option[String] = None
	)
	
	object App {
		enum AppApprovalStateEnum extends Enum[AppApprovalStateEnum] { case APP_APPROVAL_STATE_UNSPECIFIED, ACTION_REQUIRED, IN_REVIEW, APPROVED }
	}
	case class App(
	  /** Resource name for this app. Format is accounts/{publisher_id}/apps/{app_id_fragment} Example: accounts/pub-9876543210987654/apps/0123456789 */
		name: Option[String] = None,
	  /** The externally visible ID of the app which can be used to integrate with the AdMob SDK. This is a read only property. Example: ca-app-pub-9876543210987654~0123456789 */
		appId: Option[String] = None,
	  /** Describes the platform of the app. Limited to "IOS" and "ANDROID". */
		platform: Option[String] = None,
	  /** The information for an app that is not linked to any app store. After an app is linked, this information is still retrivable. If no name is provided for the app upon creation, a placeholder name will be used. */
		manualAppInfo: Option[Schema.AppManualAppInfo] = None,
	  /** Immutable. The information for an app that is linked to an app store. This field is present if and only if the app is linked to an app store. */
		linkedAppInfo: Option[Schema.AppLinkedAppInfo] = None,
	  /** Output only. The approval state for the app. The field is read-only. */
		appApprovalState: Option[Schema.App.AppApprovalStateEnum] = None
	)
	
	case class AppManualAppInfo(
	  /** The display name of the app as shown in the AdMob UI, which is provided by the user. The maximum length allowed is 80 characters. */
		displayName: Option[String] = None
	)
	
	case class AppLinkedAppInfo(
	  /** The app store ID of the app; present if and only if the app is linked to an app store. If the app is added to the Google Play store, it will be the application ID of the app. For example: "com.example.myapp". See https://developer.android.com/studio/build/application-id. If the app is added to the Apple App Store, it will be app store ID. For example "105169111". Note that setting the app store id is considered an irreversible action. Once an app is linked, it cannot be unlinked. */
		appStoreId: Option[String] = None,
	  /** Output only. Display name of the app as it appears in the app store. This is an output-only field, and may be empty if the app cannot be found in the store. */
		displayName: Option[String] = None
	)
	
	case class ListAdUnitsResponse(
	  /** The resulting ad units for the requested account. */
		adUnits: Option[List[Schema.AdUnit]] = None,
	  /** If not empty, indicates that there may be more ad units for the request; this value should be passed in a new `ListAdUnitsRequest`. */
		nextPageToken: Option[String] = None
	)
	
	case class AdUnit(
	  /** Resource name for this ad unit. Format is accounts/{publisher_id}/adUnits/{ad_unit_id_fragment} Example: accounts/pub-9876543210987654/adUnits/0123456789 */
		name: Option[String] = None,
	  /** The externally visible ID of the ad unit which can be used to integrate with the AdMob SDK. This is a read only property. Example: ca-app-pub-9876543210987654/0123456789 */
		adUnitId: Option[String] = None,
	  /** The externally visible ID of the app this ad unit is associated with. Example: ca-app-pub-9876543210987654~0123456789 */
		appId: Option[String] = None,
	  /** The display name of the ad unit as shown in the AdMob UI, which is provided by the user. The maximum length allowed is 80 characters. */
		displayName: Option[String] = None,
	  /** AdFormat of the ad unit. Possible values are as follows: "APP_OPEN" - App Open ad format. "BANNER" - Banner ad format. "BANNER_INTERSTITIAL" - Legacy format that can be used as either banner or interstitial. This format can no longer be created but can be targeted by mediation groups. "INTERSTITIAL" - A full screen ad. Supported ad types are "RICH_MEDIA" and "VIDEO". "NATIVE" - Native ad format. "REWARDED" - An ad that, once viewed, gets a callback verifying the view so that a reward can be given to the user. Supported ad types are "RICH_MEDIA" (interactive) and video where video can not be excluded. "REWARDED_INTERSTITIAL" - Rewarded Interstitial ad format. Only supports video ad type. See https://support.google.com/admob/answer/9884467. */
		adFormat: Option[String] = None,
	  /** Ad media type supported by this ad unit. Possible values as follows: "RICH_MEDIA" - Text, image, and other non-video media. "VIDEO" - Video media. */
		adTypes: Option[List[String]] = None
	)
}
