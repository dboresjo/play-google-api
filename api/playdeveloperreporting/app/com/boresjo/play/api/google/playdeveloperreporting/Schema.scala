package com.boresjo.play.api.google.playdeveloperreporting

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object GooglePlayDeveloperReportingV1beta1MetricsRow {
		enum AggregationPeriodEnum extends Enum[AggregationPeriodEnum] { case AGGREGATION_PERIOD_UNSPECIFIED, HOURLY, DAILY, FULL_RANGE }
	}
	case class GooglePlayDeveloperReportingV1beta1MetricsRow(
	  /** Granularity of the aggregation period of the row. */
		aggregationPeriod: Option[Schema.GooglePlayDeveloperReportingV1beta1MetricsRow.AggregationPeriodEnum] = None,
	  /** Dimension columns in the row. */
		dimensions: Option[List[Schema.GooglePlayDeveloperReportingV1beta1DimensionValue]] = None,
	  /** Metric columns in the row. */
		metrics: Option[List[Schema.GooglePlayDeveloperReportingV1beta1MetricValue]] = None,
	  /** Starting date (and time for hourly aggregation) of the period covered by this row. */
		startTime: Option[Schema.GoogleTypeDateTime] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1IssueAnnotation(
	  /** Contains the contents of the annotation message. */
		body: Option[String] = None,
	  /** Title for the annotation. */
		title: Option[String] = None,
	  /** Category that the annotation belongs to. An annotation will belong to a single category. Example categories: "Potential fix", "Insight". */
		category: Option[String] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1QueryCrashRateMetricSetResponse(
	  /** Returned rows of data. */
		rows: Option[List[Schema.GooglePlayDeveloperReportingV1beta1MetricsRow]] = None,
	  /** Continuation token to fetch the next page of data. */
		nextPageToken: Option[String] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1QuerySlowStartRateMetricSetResponse(
	  /** Returned rows of data. */
		rows: Option[List[Schema.GooglePlayDeveloperReportingV1beta1MetricsRow]] = None,
	  /** Continuation token to fetch the next page of data. */
		nextPageToken: Option[String] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1StuckBackgroundWakelockRateMetricSet(
	  /** Identifier. The resource name. Format: apps/{app}/stuckBackgroundWakelockRateMetricSet */
		name: Option[String] = None,
	  /** Summary about data freshness in this resource. */
		freshnessInfo: Option[Schema.GooglePlayDeveloperReportingV1beta1FreshnessInfo] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1Track(
	  /** Represents all active releases in the track. */
		servingReleases: Option[List[Schema.GooglePlayDeveloperReportingV1beta1Release]] = None,
	  /** Readable identifier of the track. */
		displayName: Option[String] = None,
	  /** The type of the track. */
		`type`: Option[String] = None
	)
	
	case class GoogleTypeDecimal(
	  /** The decimal value, as a string. The string representation consists of an optional sign, `+` (`U+002B`) or `-` (`U+002D`), followed by a sequence of zero or more decimal digits ("the integer"), optionally followed by a fraction, optionally followed by an exponent. An empty string &#42;&#42;should&#42;&#42; be interpreted as `0`. The fraction consists of a decimal point followed by zero or more decimal digits. The string must contain at least one digit in either the integer or the fraction. The number formed by the sign, the integer and the fraction is referred to as the significand. The exponent consists of the character `e` (`U+0065`) or `E` (`U+0045`) followed by one or more decimal digits. Services &#42;&#42;should&#42;&#42; normalize decimal values before storing them by: - Removing an explicitly-provided `+` sign (`+2.5` -> `2.5`). - Replacing a zero-length integer value with `0` (`.5` -> `0.5`). - Coercing the exponent character to upper-case, with explicit sign (`2.5e8` -> `2.5E+8`). - Removing an explicitly-provided zero exponent (`2.5E0` -> `2.5`). Services &#42;&#42;may&#42;&#42; perform additional normalization based on its own needs and the internal decimal implementation selected, such as shifting the decimal point and exponent value together (example: `2.5E-1` <-> `0.25`). Additionally, services &#42;&#42;may&#42;&#42; preserve trailing zeroes in the fraction to indicate increased precision, but are not required to do so. Note that only the `.` character is supported to divide the integer and the fraction; `,` &#42;&#42;should not&#42;&#42; be supported regardless of locale. Additionally, thousand separators &#42;&#42;should not&#42;&#42; be supported. If a service does support them, values &#42;&#42;must&#42;&#42; be normalized. The ENBF grammar is: DecimalString = '' | [Sign] Significand [Exponent]; Sign = '+' | '-'; Significand = Digits '.' | [Digits] '.' Digits; Exponent = ('e' | 'E') [Sign] Digits; Digits = { '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9' }; Services &#42;&#42;should&#42;&#42; clearly document the range of supported values, the maximum supported precision (total number of digits), and, if applicable, the scale (number of digits after the decimal point), as well as how it behaves when receiving out-of-bounds values. Services &#42;&#42;may&#42;&#42; choose to accept values passed as input even when the value has a higher precision or scale than the service supports, and &#42;&#42;should&#42;&#42; round the value to fit the supported scale. Alternatively, the service &#42;&#42;may&#42;&#42; error with `400 Bad Request` (`INVALID_ARGUMENT` in gRPC) if precision would be lost. Services &#42;&#42;should&#42;&#42; error with `400 Bad Request` (`INVALID_ARGUMENT` in gRPC) if the service receives a value outside of the supported range. */
		value: Option[String] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1SearchAccessibleAppsResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The apps accessible to the user calling the endpoint. */
		apps: Option[List[Schema.GooglePlayDeveloperReportingV1beta1App]] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1SearchErrorIssuesResponse(
	  /** ErrorIssues that were found. */
		errorIssues: Option[List[Schema.GooglePlayDeveloperReportingV1beta1ErrorIssue]] = None,
	  /** Continuation token to fetch the next page of data. */
		nextPageToken: Option[String] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1QueryExcessiveWakeupRateMetricSetResponse(
	  /** Continuation token to fetch the next page of data. */
		nextPageToken: Option[String] = None,
	  /** Returned rows of data. */
		rows: Option[List[Schema.GooglePlayDeveloperReportingV1beta1MetricsRow]] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1DeviceModelSummary(
	  /** Identifier of the device. */
		deviceId: Option[Schema.GooglePlayDeveloperReportingV1beta1DeviceId] = None,
	  /** Link to the device in Play Device Catalog. */
		deviceUri: Option[String] = None,
	  /** Display name of the device. */
		marketingName: Option[String] = None
	)
	
	object GooglePlayDeveloperReportingV1beta1TimelineSpec {
		enum AggregationPeriodEnum extends Enum[AggregationPeriodEnum] { case AGGREGATION_PERIOD_UNSPECIFIED, HOURLY, DAILY, FULL_RANGE }
	}
	case class GooglePlayDeveloperReportingV1beta1TimelineSpec(
	  /** Type of the aggregation period of the datapoints in the timeline. Intervals are identified by the date and time at the start of the interval. */
		aggregationPeriod: Option[Schema.GooglePlayDeveloperReportingV1beta1TimelineSpec.AggregationPeriodEnum] = None,
	  /** Ending datapoint of the timeline (exclusive). See start_time for restrictions. The timezone of the end point must match the timezone of the start point. */
		endTime: Option[Schema.GoogleTypeDateTime] = None,
	  /** Starting datapoint of the timeline (inclusive). Must be aligned to the aggregation period as follows: &#42; HOURLY: the 'minutes', 'seconds' and 'nanos' fields must be unset. The time_zone can be left unset (defaults to UTC) or set explicitly to "UTC". Setting any other utc_offset or timezone id will result in a validation error. &#42; DAILY: the 'hours', 'minutes', 'seconds' and 'nanos' fields must be unset. Different metric sets support different timezones. It can be left unset to use the default timezone specified by the metric set. The timezone of the end point must match the timezone of the start point. */
		startTime: Option[Schema.GoogleTypeDateTime] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1AppVersion(
	  /** Numeric version code of the app version (set by the app's developer). */
		versionCode: Option[String] = None
	)
	
	object GooglePlayDeveloperReportingV1beta1QueryCrashRateMetricSetRequest {
		enum UserCohortEnum extends Enum[UserCohortEnum] { case USER_COHORT_UNSPECIFIED, OS_PUBLIC, OS_BETA, APP_TESTERS }
	}
	case class GooglePlayDeveloperReportingV1beta1QueryCrashRateMetricSetRequest(
	  /** A page token, received from a previous call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to the request must match the call that provided the page token. */
		pageToken: Option[String] = None,
	  /** User view to select. The output data will correspond to the selected view. &#42;&#42;Supported values:&#42;&#42; &#42; `OS_PUBLIC` To select data from all publicly released Android versions. This is the default. Supports all the above dimensions. &#42; `APP_TESTERS` To select data from users who have opted in to be testers. Supports all the above dimensions. &#42; `OS_BETA` To select data from beta android versions only, excluding data from released android versions. Only the following dimensions are supported: &#42; `versionCode` (int64): version of the app that was running on the user's device. &#42; `osBuild` (string): OS build of the user's device, e.g., "T1B2.220916.004". */
		userCohort: Option[Schema.GooglePlayDeveloperReportingV1beta1QueryCrashRateMetricSetRequest.UserCohortEnum] = None,
	  /** Dimensions to slice the metrics by. &#42;&#42;Supported dimensions:&#42;&#42; &#42; `apiLevel` (string): the API level of Android that was running on the user's device, e.g., 26. &#42; `versionCode` (int64): version of the app that was running on the user's device. &#42; `deviceModel` (string): unique identifier of the user's device model. The form of the identifier is 'deviceBrand/device', where deviceBrand corresponds to Build.BRAND and device corresponds to Build.DEVICE, e.g., google/coral. &#42; `deviceBrand` (string): unique identifier of the user's device brand, e.g., google. &#42; `deviceType` (string): the type (also known as form factor) of the user's device, e.g., PHONE. &#42; `countryCode` (string): the country or region of the user's device based on their IP address, represented as a 2-letter ISO-3166 code (e.g. US for the United States). &#42; `deviceRamBucket` (int64): RAM of the device, in MB, in buckets (3GB, 4GB, etc.). &#42; `deviceSocMake` (string): Make of the device's primary system-on-chip, e.g., Samsung. [Reference](https://developer.android.com/reference/android/os/Build#SOC_MANUFACTURER) &#42; `deviceSocModel` (string): Model of the device's primary system-on-chip, e.g., "Exynos 2100". [Reference](https://developer.android.com/reference/android/os/Build#SOC_MODEL) &#42; `deviceCpuMake` (string): Make of the device's CPU, e.g., Qualcomm. &#42; `deviceCpuModel` (string): Model of the device's CPU, e.g., "Kryo 240". &#42; `deviceGpuMake` (string): Make of the device's GPU, e.g., ARM. &#42; `deviceGpuModel` (string): Model of the device's GPU, e.g., Mali. &#42; `deviceGpuVersion` (string): Version of the device's GPU, e.g., T750. &#42; `deviceVulkanVersion` (string): Vulkan version of the device, e.g., "4198400". &#42; `deviceGlEsVersion` (string): OpenGL ES version of the device, e.g., "196610". &#42; `deviceScreenSize` (string): Screen size of the device, e.g., NORMAL, LARGE. &#42; `deviceScreenDpi` (string): Screen density of the device, e.g., mdpi, hdpi. */
		dimensions: Option[List[String]] = None,
	  /** Metrics to aggregate. &#42;&#42;Supported metrics:&#42;&#42; &#42; `crashRate` (`google.type.Decimal`): Percentage of distinct users in the aggregation period that experienced at least one crash. &#42; `crashRate7dUserWeighted` (`google.type.Decimal`): Rolling average value of `crashRate` in the last 7 days. The daily values are weighted by the count of distinct users for the day. &#42; `crashRate28dUserWeighted` (`google.type.Decimal`): Rolling average value of `crashRate` in the last 28 days. The daily values are weighted by the count of distinct users for the day. Not supported in HOURLY granularity. &#42; `userPerceivedCrashRate` (`google.type.Decimal`): Percentage of distinct users in the aggregation period that experienced at least one crash while they were actively using your app (a user-perceived crash). An app is considered to be in active use if it is displaying any activity or executing any foreground service. &#42; `userPerceivedCrashRate7dUserWeighted` (`google.type.Decimal`): Rolling average value of `userPerceivedCrashRate` in the last 7 days. The daily values are weighted by the count of distinct users for the day. Not supported in HOURLY granularity. &#42; `userPerceivedCrashRate28dUserWeighted` (`google.type.Decimal`): Rolling average value of `userPerceivedCrashRate` in the last 28 days. The daily values are weighted by the count of distinct users for the day. Not supported in HOURLY granularity. &#42; `distinctUsers` (`google.type.Decimal`): Count of distinct users in the aggregation period that were used as normalization value for the `crashRate` and `userPerceivedCrashRate` metrics. A user is counted in this metric if they used the app actively during the aggregation period. An app is considered to be in active use if it is displaying any activity or executing any foreground service. Care must be taken not to aggregate this count further, as it may result in users being counted multiple times. The value is rounded to the nearest multiple of 10, 100, 1,000 or 1,000,000, depending on the magnitude of the value. */
		metrics: Option[List[String]] = None,
	  /** Maximum size of the returned data. If unspecified, at most 1000 rows will be returned. The maximum value is 100,000; values above 100,000 will be coerced to 100,000. */
		pageSize: Option[Int] = None,
	  /** Specification of the timeline aggregation parameters. &#42;&#42;Supported aggregation periods:&#42;&#42; &#42; DAILY: metrics are aggregated in calendar date intervals. Due to historical constraints, the default and only supported timezone is `America/Los_Angeles`. &#42; HOURLY: metrics are aggregated in hourly intervals. The default and only supported timezone is `UTC`. */
		timelineSpec: Option[Schema.GooglePlayDeveloperReportingV1beta1TimelineSpec] = None,
	  /** Filters to apply to data. The filtering expression follows [AIP-160](https://google.aip.dev/160) standard and supports filtering by equality of all breakdown dimensions. */
		filter: Option[String] = None
	)
	
	object GooglePlayDeveloperReportingV1beta1QuerySlowStartRateMetricSetRequest {
		enum UserCohortEnum extends Enum[UserCohortEnum] { case USER_COHORT_UNSPECIFIED, OS_PUBLIC, OS_BETA, APP_TESTERS }
	}
	case class GooglePlayDeveloperReportingV1beta1QuerySlowStartRateMetricSetRequest(
	  /** A page token, received from a previous call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to the request must match the call that provided the page token. */
		pageToken: Option[String] = None,
	  /** User view to select. The output data will correspond to the selected view. &#42;&#42;Supported values:&#42;&#42; &#42; `OS_PUBLIC` To select data from all publicly released Android versions. This is the default. Supports all the above dimensions. &#42; `APP_TESTERS` To select data from users who have opted in to be testers. Supports all the above dimensions. &#42; `OS_BETA` To select data from beta Android versions only, excluding data from released Android versions. Only the following dimensions are supported: &#42; `versionCode` (int64): version of the app that was running on the user's device. &#42; `osBuild` (string): OS build of the user's device, e.g., "T1B2.220916.004". */
		userCohort: Option[Schema.GooglePlayDeveloperReportingV1beta1QuerySlowStartRateMetricSetRequest.UserCohortEnum] = None,
	  /** Filters to apply to data. The filtering expression follows [AIP-160](https://google.aip.dev/160) standard and supports filtering by equality of all breakdown dimensions. */
		filter: Option[String] = None,
	  /** Specification of the timeline aggregation parameters. &#42;&#42;Supported aggregation periods:&#42;&#42; &#42; DAILY: metrics are aggregated in calendar date intervals. Due to historical constraints, the only supported timezone is `America/Los_Angeles`. */
		timelineSpec: Option[Schema.GooglePlayDeveloperReportingV1beta1TimelineSpec] = None,
	  /** Dimensions to slice the data by. &#42;&#42;Supported dimensions:&#42;&#42; &#42; `apiLevel` (string): the API level of Android that was running on the user's device, e.g., 26. &#42; `versionCode` (int64): version of the app that was running on the user's device. &#42; `deviceModel` (string): unique identifier of the user's device model. The form of the identifier is 'deviceBrand/device', where deviceBrand corresponds to Build.BRAND and device corresponds to Build.DEVICE, e.g., google/coral. &#42; `deviceBrand` (string): unique identifier of the user's device brand, e.g., google. &#42; `deviceType` (string): the type (also known as form factor) of the user's device, e.g., PHONE. &#42; `countryCode` (string): the country or region of the user's device based on their IP address, represented as a 2-letter ISO-3166 code (e.g. US for the United States). &#42; `deviceRamBucket` (int64): RAM of the device, in MB, in buckets (3GB, 4GB, etc.). &#42; `deviceSocMake` (string): Make of the device's primary system-on-chip, e.g., Samsung. [Reference](https://developer.android.com/reference/android/os/Build#SOC_MANUFACTURER) &#42; `deviceSocModel` (string): Model of the device's primary system-on-chip, e.g., "Exynos 2100". [Reference](https://developer.android.com/reference/android/os/Build#SOC_MODEL) &#42; `deviceCpuMake` (string): Make of the device's CPU, e.g., Qualcomm. &#42; `deviceCpuModel` (string): Model of the device's CPU, e.g., "Kryo 240". &#42; `deviceGpuMake` (string): Make of the device's GPU, e.g., ARM. &#42; `deviceGpuModel` (string): Model of the device's GPU, e.g., Mali. &#42; `deviceGpuVersion` (string): Version of the device's GPU, e.g., T750. &#42; `deviceVulkanVersion` (string): Vulkan version of the device, e.g., "4198400". &#42; `deviceGlEsVersion` (string): OpenGL ES version of the device, e.g., "196610". &#42; `deviceScreenSize` (string): Screen size of the device, e.g., NORMAL, LARGE. &#42; `deviceScreenDpi` (string): Screen density of the device, e.g., mdpi, hdpi. */
		dimensions: Option[List[String]] = None,
	  /** Maximum size of the returned data. If unspecified, at most 1000 rows will be returned. The maximum value is 100000; values above 100000 will be coerced to 100000. */
		pageSize: Option[Int] = None,
	  /** Metrics to aggregate. &#42;&#42;Supported metrics:&#42;&#42; &#42; `slowStartRate` (`google.type.Decimal`): Percentage of distinct users in the aggregation period that had a slow start. &#42; `slowStartRate7dUserWeighted` (`google.type.Decimal`): Rolling average value of `slowStartRate` in the last 7 days. The daily values are weighted by the count of distinct users for the day. &#42; `slowStartRate28dUserWeighted` (`google.type.Decimal`): Rolling average value of `slowStartRate` in the last 28 days. The daily values are weighted by the count of distinct users for the day. &#42; `distinctUsers` (`google.type.Decimal`): Count of distinct users in the aggregation period that were used as normalization value for the `slowStartRate` metric. A user is counted in this metric if their app was launched in the device. Care must be taken not to aggregate this count further, as it may result in users being counted multiple times. The value is rounded to the nearest multiple of 10, 100, 1,000 or 1,000,000, depending on the magnitude of the value. */
		metrics: Option[List[String]] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1ExcessiveWakeupRateMetricSet(
	  /** Identifier. The resource name. Format: apps/{app}/excessiveWakeupRateMetricSet */
		name: Option[String] = None,
	  /** Summary about data freshness in this resource. */
		freshnessInfo: Option[Schema.GooglePlayDeveloperReportingV1beta1FreshnessInfo] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1OsVersion(
	  /** Numeric version code of the OS - API level */
		apiLevel: Option[String] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1QueryErrorCountMetricSetResponse(
	  /** Returned rows. */
		rows: Option[List[Schema.GooglePlayDeveloperReportingV1beta1MetricsRow]] = None,
	  /** Continuation token to fetch the next page of data. */
		nextPageToken: Option[String] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1App(
	  /** Package name of the app. Example: `com.example.app123`. */
		packageName: Option[String] = None,
	  /** Title of the app. This is the latest title as set in the Play Console and may not yet have been reviewed, so might not match the Play Store. Example: `Google Maps`. */
		displayName: Option[String] = None,
	  /** Identifier. The resource name. Format: apps/{app} */
		name: Option[String] = None
	)
	
	object GooglePlayDeveloperReportingV1beta1QueryStuckBackgroundWakelockRateMetricSetRequest {
		enum UserCohortEnum extends Enum[UserCohortEnum] { case USER_COHORT_UNSPECIFIED, OS_PUBLIC, OS_BETA, APP_TESTERS }
	}
	case class GooglePlayDeveloperReportingV1beta1QueryStuckBackgroundWakelockRateMetricSetRequest(
	  /** Metrics to aggregate. &#42;&#42;Supported metrics:&#42;&#42; &#42; `stuckBgWakelockRate` (`google.type.Decimal`): Percentage of distinct users in the aggregation period that had a wakelock held in the background for longer than 1 hour. &#42; `stuckBgWakelockRate7dUserWeighted` (`google.type.Decimal`): Rolling average value of `stuckBgWakelockRate` in the last 7 days. The daily values are weighted by the count of distinct users for the day. &#42; `stuckBgWakelockRate28dUserWeighted` (`google.type.Decimal`): Rolling average value of `stuckBgWakelockRate` in the last 28 days. The daily values are weighted by the count of distinct users for the day. &#42; `distinctUsers` (`google.type.Decimal`): Count of distinct users in the aggregation period that were used as normalization value for the `stuckBgWakelockRate` metric. A user is counted in this metric if they app was doing any work on the device, i.e., not just active foreground usage but also background work. Care must be taken not to aggregate this count further, as it may result in users being counted multiple times. The value is rounded to the nearest multiple of 10, 100, 1,000 or 1,000,000, depending on the magnitude of the value. */
		metrics: Option[List[String]] = None,
	  /** Maximum size of the returned data. If unspecified, at most 1000 rows will be returned. The maximum value is 100000; values above 100000 will be coerced to 100000. */
		pageSize: Option[Int] = None,
	  /** User view to select. The output data will correspond to the selected view. &#42;&#42;Supported values:&#42;&#42; &#42; `OS_PUBLIC` To select data from all publicly released Android versions. This is the default. Supports all the above dimensions. &#42; `APP_TESTERS` To select data from users who have opted in to be testers. Supports all the above dimensions. &#42; `OS_BETA` To select data from beta android versions only, excluding data from released android versions. Only the following dimensions are supported: &#42; `versionCode` (int64): version of the app that was running on the user's device. &#42; `osBuild` (string): OS build of the user's device, e.g., "T1B2.220916.004". */
		userCohort: Option[Schema.GooglePlayDeveloperReportingV1beta1QueryStuckBackgroundWakelockRateMetricSetRequest.UserCohortEnum] = None,
	  /** A page token, received from a previous call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to the request must match the call that provided the page token. */
		pageToken: Option[String] = None,
	  /** Filters to apply to data. The filtering expression follows [AIP-160](https://google.aip.dev/160) standard and supports filtering by equality of all breakdown dimensions. */
		filter: Option[String] = None,
	  /** Dimensions to slice the data by. &#42;&#42;Supported dimensions:&#42;&#42; &#42; `apiLevel` (string): the API level of Android that was running on the user's device, e.g., 26. &#42; `versionCode` (int64): version of the app that was running on the user's device. &#42; `deviceModel` (string): unique identifier of the user's device model. The form of the identifier is 'deviceBrand/device', where deviceBrand corresponds to Build.BRAND and device corresponds to Build.DEVICE, e.g., google/coral. &#42; `deviceBrand` (string): unique identifier of the user's device brand, e.g., google. &#42; `deviceType` (string): the type (also known as form factor) of the user's device, e.g., PHONE. &#42; `countryCode` (string): the country or region of the user's device based on their IP address, represented as a 2-letter ISO-3166 code (e.g. US for the United States). &#42; `deviceRamBucket` (int64): RAM of the device, in MB, in buckets (3GB, 4GB, etc.). &#42; `deviceSocMake` (string): Make of the device's primary system-on-chip, e.g., Samsung. [Reference](https://developer.android.com/reference/android/os/Build#SOC_MANUFACTURER) &#42; `deviceSocModel` (string): Model of the device's primary system-on-chip, e.g., "Exynos 2100". [Reference](https://developer.android.com/reference/android/os/Build#SOC_MODEL) &#42; `deviceCpuMake` (string): Make of the device's CPU, e.g., Qualcomm. &#42; `deviceCpuModel` (string): Model of the device's CPU, e.g., "Kryo 240". &#42; `deviceGpuMake` (string): Make of the device's GPU, e.g., ARM. &#42; `deviceGpuModel` (string): Model of the device's GPU, e.g., Mali. &#42; `deviceGpuVersion` (string): Version of the device's GPU, e.g., T750. &#42; `deviceVulkanVersion` (string): Vulkan version of the device, e.g., "4198400". &#42; `deviceGlEsVersion` (string): OpenGL ES version of the device, e.g., "196610". &#42; `deviceScreenSize` (string): Screen size of the device, e.g., NORMAL, LARGE. &#42; `deviceScreenDpi` (string): Screen density of the device, e.g., mdpi, hdpi. */
		dimensions: Option[List[String]] = None,
	  /** Specification of the timeline aggregation parameters. &#42;&#42;Supported aggregation periods:&#42;&#42; &#42; DAILY: metrics are aggregated in calendar date intervals. Due to historical constraints, the only supported timezone is `America/Los_Angeles`. */
		timelineSpec: Option[Schema.GooglePlayDeveloperReportingV1beta1TimelineSpec] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1QueryStuckBackgroundWakelockRateMetricSetResponse(
	  /** Returned rows of data. */
		rows: Option[List[Schema.GooglePlayDeveloperReportingV1beta1MetricsRow]] = None,
	  /** Continuation token to fetch the next page of data. */
		nextPageToken: Option[String] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1SearchErrorReportsResponse(
	  /** Error reports that were found. */
		errorReports: Option[List[Schema.GooglePlayDeveloperReportingV1beta1ErrorReport]] = None,
	  /** Page token to fetch the next page of reports. */
		nextPageToken: Option[String] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1Anomaly(
	  /** Combination of dimensions in which the anomaly was detected. */
		dimensions: Option[List[Schema.GooglePlayDeveloperReportingV1beta1DimensionValue]] = None,
	  /** Timeline specification that covers the anomaly period. */
		timelineSpec: Option[Schema.GooglePlayDeveloperReportingV1beta1TimelineSpec] = None,
	  /** Identifier. Name of the anomaly. Format: apps/{app}/anomalies/{anomaly} */
		name: Option[String] = None,
	  /** Metric where the anomaly was detected, together with the anomalous value. */
		metric: Option[Schema.GooglePlayDeveloperReportingV1beta1MetricValue] = None,
	  /** Metric set resource where the anomaly was detected. */
		metricSet: Option[String] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1DimensionValue(
	  /** Actual value, represented as an int64. */
		int64Value: Option[String] = None,
	  /** Name of the dimension. */
		dimension: Option[String] = None,
	  /** Optional. Human-friendly label for the value, always in English. For example, 'Spain' for the 'ES' country code. Whereas the dimension value is stable, this value label is subject to change. Do not assume that the (value, value_label) relationship is stable. For example, the ISO country code 'MK' changed its name recently to 'North Macedonia'. */
		valueLabel: Option[String] = None,
	  /** Actual value, represented as a string. */
		stringValue: Option[String] = None
	)
	
	object GooglePlayDeveloperReportingV1beta1QueryExcessiveWakeupRateMetricSetRequest {
		enum UserCohortEnum extends Enum[UserCohortEnum] { case USER_COHORT_UNSPECIFIED, OS_PUBLIC, OS_BETA, APP_TESTERS }
	}
	case class GooglePlayDeveloperReportingV1beta1QueryExcessiveWakeupRateMetricSetRequest(
	  /** User view to select. The output data will correspond to the selected view. &#42;&#42;Supported values:&#42;&#42; &#42; `OS_PUBLIC` To select data from all publicly released Android versions. This is the default. Supports all the above dimensions. &#42; `APP_TESTERS` To select data from users who have opted in to be testers. Supports all the above dimensions. &#42; `OS_BETA` To select data from beta android versions only, excluding data from released android versions. Only the following dimensions are supported: &#42; `versionCode` (int64): version of the app that was running on the user's device. &#42; `osBuild` (string): OS build of the user's device, e.g., "T1B2.220916.004". */
		userCohort: Option[Schema.GooglePlayDeveloperReportingV1beta1QueryExcessiveWakeupRateMetricSetRequest.UserCohortEnum] = None,
	  /** Dimensions to slice the data by. &#42;&#42;Supported dimensions:&#42;&#42; &#42; `apiLevel` (string): the API level of Android that was running on the user's device, e.g., 26. &#42; `versionCode` (int64): version of the app that was running on the user's device. &#42; `deviceModel` (string): unique identifier of the user's device model. The form of the identifier is 'deviceBrand/device', where deviceBrand corresponds to Build.BRAND and device corresponds to Build.DEVICE, e.g., google/coral. &#42; `deviceBrand` (string): unique identifier of the user's device brand, e.g., google. &#42; `deviceType` (string): the type (also known as form factor) of the user's device, e.g., PHONE. &#42; `countryCode` (string): the country or region of the user's device based on their IP address, represented as a 2-letter ISO-3166 code (e.g. US for the United States). &#42; `deviceRamBucket` (int64): RAM of the device, in MB, in buckets (3GB, 4GB, etc.). &#42; `deviceSocMake` (string): Make of the device's primary system-on-chip, e.g., Samsung. [Reference](https://developer.android.com/reference/android/os/Build#SOC_MANUFACTURER) &#42; `deviceSocModel` (string): Model of the device's primary system-on-chip, e.g., "Exynos 2100". [Reference](https://developer.android.com/reference/android/os/Build#SOC_MODEL) &#42; `deviceCpuMake` (string): Make of the device's CPU, e.g., Qualcomm. &#42; `deviceCpuModel` (string): Model of the device's CPU, e.g., "Kryo 240". &#42; `deviceGpuMake` (string): Make of the device's GPU, e.g., ARM. &#42; `deviceGpuModel` (string): Model of the device's GPU, e.g., Mali. &#42; `deviceGpuVersion` (string): Version of the device's GPU, e.g., T750. &#42; `deviceVulkanVersion` (string): Vulkan version of the device, e.g., "4198400". &#42; `deviceGlEsVersion` (string): OpenGL ES version of the device, e.g., "196610". &#42; `deviceScreenSize` (string): Screen size of the device, e.g., NORMAL, LARGE. &#42; `deviceScreenDpi` (string): Screen density of the device, e.g., mdpi, hdpi. */
		dimensions: Option[List[String]] = None,
	  /** Specification of the timeline aggregation parameters. &#42;&#42;Supported aggregation periods:&#42;&#42; &#42; DAILY: metrics are aggregated in calendar date intervals. Due to historical constraints, the only supported timezone is `America/Los_Angeles`. */
		timelineSpec: Option[Schema.GooglePlayDeveloperReportingV1beta1TimelineSpec] = None,
	  /** A page token, received from a previous call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to the request must match the call that provided the page token. */
		pageToken: Option[String] = None,
	  /** Metrics to aggregate. &#42;&#42;Supported metrics:&#42;&#42; &#42; `excessiveWakeupRate` (`google.type.Decimal`): Percentage of distinct users in the aggregation period that had more than 10 wakeups per hour. &#42; `excessiveWakeupRate7dUserWeighted` (`google.type.Decimal`): Rolling average value of `excessiveWakeupRate` in the last 7 days. The daily values are weighted by the count of distinct users for the day. &#42; `excessiveWakeupRate28dUserWeighted` (`google.type.Decimal`): Rolling average value of `excessiveWakeupRate` in the last 28 days. The daily values are weighted by the count of distinct users for the day. &#42; `distinctUsers` (`google.type.Decimal`): Count of distinct users in the aggregation period that were used as normalization value for the `excessiveWakeupRate` metric. A user is counted in this metric if they app was doing any work on the device, i.e., not just active foreground usage but also background work. Care must be taken not to aggregate this count further, as it may result in users being counted multiple times. The value is rounded to the nearest multiple of 10, 100, 1,000 or 1,000,000, depending on the magnitude of the value. */
		metrics: Option[List[String]] = None,
	  /** Maximum size of the returned data. If unspecified, at most 1000 rows will be returned. The maximum value is 100000; values above 100000 will be coerced to 100000. */
		pageSize: Option[Int] = None,
	  /** Filters to apply to data. The filtering expression follows [AIP-160](https://google.aip.dev/160) standard and supports filtering by equality of all breakdown dimensions. */
		filter: Option[String] = None
	)
	
	case class GoogleTypeTimeZone(
	  /** IANA Time Zone Database time zone, e.g. "America/New_York". */
		id: Option[String] = None,
	  /** Optional. IANA Time Zone Database version number, e.g. "2019a". */
		version: Option[String] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1ReleaseFilterOptions(
	  /** List of tracks to filter releases over. Provides the grouping of version codes under releases and tracks. */
		tracks: Option[List[Schema.GooglePlayDeveloperReportingV1beta1Track]] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1AnrRateMetricSet(
	  /** Summary about data freshness in this resource. */
		freshnessInfo: Option[Schema.GooglePlayDeveloperReportingV1beta1FreshnessInfo] = None,
	  /** Identifier. The resource name. Format: apps/{app}/anrRateMetricSet */
		name: Option[String] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1ListAnomaliesResponse(
	  /** Continuation token to fetch the next page of data. */
		nextPageToken: Option[String] = None,
	  /** Anomalies that were found. */
		anomalies: Option[List[Schema.GooglePlayDeveloperReportingV1beta1Anomaly]] = None
	)
	
	object GooglePlayDeveloperReportingV1beta1QueryAnrRateMetricSetRequest {
		enum UserCohortEnum extends Enum[UserCohortEnum] { case USER_COHORT_UNSPECIFIED, OS_PUBLIC, OS_BETA, APP_TESTERS }
	}
	case class GooglePlayDeveloperReportingV1beta1QueryAnrRateMetricSetRequest(
	  /** Specification of the timeline aggregation parameters. &#42;&#42;Supported aggregation periods:&#42;&#42; &#42; DAILY: metrics are aggregated in calendar date intervals. Due to historical constraints, the default and only supported timezone is `America/Los_Angeles`. &#42; HOURLY: metrics are aggregated in hourly intervals. The default and only supported timezone is `UTC`. */
		timelineSpec: Option[Schema.GooglePlayDeveloperReportingV1beta1TimelineSpec] = None,
	  /** A page token, received from a previous call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to the request must match the call that provided the page token. */
		pageToken: Option[String] = None,
	  /** Metrics to aggregate. &#42;&#42;Supported metrics:&#42;&#42; &#42; `anrRate` (`google.type.Decimal`): Percentage of distinct users in the aggregation period that experienced at least one ANR. &#42; `anrRate7dUserWeighted` (`google.type.Decimal`): Rolling average value of `anrRate` in the last 7 days. The daily values are weighted by the count of distinct users for the day. Not supported in HOURLY granularity. &#42; `anrRate28dUserWeighted` (`google.type.Decimal`): Rolling average value of `anrRate` in the last 28 days. The daily values are weighted by the count of distinct users for the day. Not supported in HOURLY granularity. &#42; `userPerceivedAnrRate` (`google.type.Decimal`): Percentage of distinct users in the aggregation period that experienced at least one user-perceived ANR. User-perceived ANRs are currently those of 'Input dispatching' type. &#42; `userPerceivedAnrRate7dUserWeighted` (`google.type.Decimal`): Rolling average value of `userPerceivedAnrRate` in the last 7 days. The daily values are weighted by the count of distinct users for the day. Not supported in HOURLY granularity. &#42; `userPerceivedAnrRate28dUserWeighted` (`google.type.Decimal`): Rolling average value of `userPerceivedAnrRate` in the last 28 days. The daily values are weighted by the count of distinct users for the day. Not . supported in HOURLY granularity. &#42; `distinctUsers` (`google.type.Decimal`): Count of distinct users in the aggregation period that were used as normalization value for the `anrRate` and `userPerceivedAnrRate` metrics. A user is counted in this metric if they used the app in the foreground during the aggregation period. Care must be taken not to aggregate this count further, as it may result in users being counted multiple times. The value is rounded to the nearest multiple of 10, 100, 1,000 or 1,000,000, depending on the magnitude of the value. */
		metrics: Option[List[String]] = None,
	  /** User view to select. The output data will correspond to the selected view. &#42;&#42;Supported values:&#42;&#42; &#42; `OS_PUBLIC` To select data from all publicly released Android versions. This is the default. Supports all the above dimensions. &#42; `APP_TESTERS` To select data from users who have opted in to be testers. Supports all the above dimensions. &#42; `OS_BETA` To select data from beta android versions only, excluding data from released android versions. Only the following dimensions are supported: &#42; `versionCode` (int64): version of the app that was running on the user's device. &#42; `osBuild` (string): OS build of the user's device, e.g., "T1B2.220916.004". */
		userCohort: Option[Schema.GooglePlayDeveloperReportingV1beta1QueryAnrRateMetricSetRequest.UserCohortEnum] = None,
	  /** Filters to apply to data. The filtering expression follows [AIP-160](https://google.aip.dev/160) standard and supports filtering by equality of all breakdown dimensions. */
		filter: Option[String] = None,
	  /** Maximum size of the returned data. If unspecified, at most 1000 rows will be returned. The maximum value is 100,000; values above 100,000 will be coerced to 100,000. */
		pageSize: Option[Int] = None,
	  /** Dimensions to slice the metrics by. &#42;&#42;Supported dimensions:&#42;&#42; &#42; `apiLevel` (string): the API level of Android that was running on the user's device, e.g., 26. &#42; `versionCode` (int64): version of the app that was running on the user's device. &#42; `deviceModel` (string): unique identifier of the user's device model. The form of the identifier is 'deviceBrand/device', where deviceBrand corresponds to Build.BRAND and device corresponds to Build.DEVICE, e.g., google/coral. &#42; `deviceBrand` (string): unique identifier of the user's device brand, e.g., google. &#42; `deviceType` (string): the type (also known as form factor) of the user's device, e.g., PHONE. &#42; `countryCode` (string): the country or region of the user's device based on their IP address, represented as a 2-letter ISO-3166 code (e.g. US for the United States). &#42; `deviceRamBucket` (int64): RAM of the device, in MB, in buckets (3GB, 4GB, etc.). &#42; `deviceSocMake` (string): Make of the device's primary system-on-chip, e.g., Samsung. [Reference](https://developer.android.com/reference/android/os/Build#SOC_MANUFACTURER) &#42; `deviceSocModel` (string): Model of the device's primary system-on-chip, e.g., "Exynos 2100". [Reference](https://developer.android.com/reference/android/os/Build#SOC_MODEL) &#42; `deviceCpuMake` (string): Make of the device's CPU, e.g., Qualcomm. &#42; `deviceCpuModel` (string): Model of the device's CPU, e.g., "Kryo 240". &#42; `deviceGpuMake` (string): Make of the device's GPU, e.g., ARM. &#42; `deviceGpuModel` (string): Model of the device's GPU, e.g., Mali. &#42; `deviceGpuVersion` (string): Version of the device's GPU, e.g., T750. &#42; `deviceVulkanVersion` (string): Vulkan version of the device, e.g., "4198400". &#42; `deviceGlEsVersion` (string): OpenGL ES version of the device, e.g., "196610". &#42; `deviceScreenSize` (string): Screen size of the device, e.g., NORMAL, LARGE. &#42; `deviceScreenDpi` (string): Screen density of the device, e.g., mdpi, hdpi. */
		dimensions: Option[List[String]] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1SlowRenderingRateMetricSet(
	  /** Summary about data freshness in this resource. */
		freshnessInfo: Option[Schema.GooglePlayDeveloperReportingV1beta1FreshnessInfo] = None,
	  /** Identifier. The resource name. Format: apps/{app}/slowRenderingRateMetricSet */
		name: Option[String] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1ErrorCountMetricSet(
	  /** Summary about data freshness in this resource. */
		freshnessInfo: Option[Schema.GooglePlayDeveloperReportingV1beta1FreshnessInfo] = None,
	  /** The resource name. Format: apps/{app}/errorCountMetricSet */
		name: Option[String] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1DeviceId(
	  /** Value of Build.DEVICE. */
		buildDevice: Option[String] = None,
	  /** Value of Build.BRAND. */
		buildBrand: Option[String] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1SlowStartRateMetricSet(
	  /** Identifier. The resource name. Format: apps/{app}/slowStartRateMetricSet */
		name: Option[String] = None,
	  /** Summary about data freshness in this resource. */
		freshnessInfo: Option[Schema.GooglePlayDeveloperReportingV1beta1FreshnessInfo] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1QuerySlowRenderingRateMetricSetResponse(
	  /** Returned rows of data. */
		rows: Option[List[Schema.GooglePlayDeveloperReportingV1beta1MetricsRow]] = None,
	  /** Continuation token to fetch the next page of data. */
		nextPageToken: Option[String] = None
	)
	
	object GooglePlayDeveloperReportingV1beta1ErrorIssue {
		enum TypeEnum extends Enum[TypeEnum] { case ERROR_TYPE_UNSPECIFIED, APPLICATION_NOT_RESPONDING, CRASH, NON_FATAL }
	}
	case class GooglePlayDeveloperReportingV1beta1ErrorIssue(
	  /** The latest OS version in which this error cluster has occurred in the requested time period (only considering occurrences matching the filters and within the requested time period). */
		lastOsVersion: Option[Schema.GooglePlayDeveloperReportingV1beta1OsVersion] = None,
	  /** The earliest (inclusive) app version appearing in this ErrorIssue in the requested time period (only considering occurrences matching the filters). */
		firstAppVersion: Option[Schema.GooglePlayDeveloperReportingV1beta1AppVersion] = None,
	  /** Output only. Sample error reports which belong to this ErrorIssue. &#42;Note:&#42; currently a maximum of 1 per ErrorIssue is supported. Format: "apps/{app}/{report}" */
		sampleErrorReports: Option[List[String]] = None,
	  /** List of annotations for an issue. Annotations provide additional information that may help in diagnosing and fixing the issue. */
		annotations: Option[List[Schema.GooglePlayDeveloperReportingV1beta1IssueAnnotation]] = None,
	  /** Location where the issue happened. Depending on the type this can be either: &#42; APPLICATION_NOT_RESPONDING: the name of the activity or service that stopped responding. &#42; CRASH: the likely method name that caused the error. */
		location: Option[String] = None,
	  /** The total number of error reports in this issue (only considering occurrences matching the filters and within the requested time period). */
		errorReportCount: Option[String] = None,
	  /** The smallest OS version in which this error cluster has occurred in the requested time period (only considering occurrences matching the filters and within the requested time period). */
		firstOsVersion: Option[Schema.GooglePlayDeveloperReportingV1beta1OsVersion] = None,
	  /** An estimated percentage of users affected by any issue that are affected by this issue (only considering occurrences matching the filters and within the requested time period). */
		distinctUsersPercent: Option[Schema.GoogleTypeDecimal] = None,
	  /** An estimate of the number of unique users who have experienced this issue (only considering occurrences matching the filters and within the requested time period). */
		distinctUsers: Option[String] = None,
	  /** The latest (inclusive) app version appearing in this ErrorIssue in the requested time period (only considering occurrences matching the filters). */
		lastAppVersion: Option[Schema.GooglePlayDeveloperReportingV1beta1AppVersion] = None,
	  /** Identifier. The resource name of the issue. Format: apps/{app}/{issue} */
		name: Option[String] = None,
	  /** Link to the issue in Android vitals in the Play Console. */
		issueUri: Option[String] = None,
	  /** Type of the errors grouped in this issue. */
		`type`: Option[Schema.GooglePlayDeveloperReportingV1beta1ErrorIssue.TypeEnum] = None,
	  /** Start of the hour during which the last error report in this issue occurred. */
		lastErrorReportTime: Option[String] = None,
	  /** Cause of the issue. Depending on the type this can be either: &#42; APPLICATION_NOT_RESPONDING: the type of ANR that occurred, e.g., 'Input dispatching timed out'. &#42; CRASH: for Java unhandled exception errors, the type of the innermost exception that was thrown, e.g., IllegalArgumentException. For signals in native code, the signal that was raised, e.g. SIGSEGV. */
		cause: Option[String] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1Release(
	  /** The version codes contained in this release. */
		versionCodes: Option[List[String]] = None,
	  /** Readable identifier of the release. */
		displayName: Option[String] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1MetricValue(
	  /** Actual value, represented as a decimal number. */
		decimalValue: Option[Schema.GoogleTypeDecimal] = None,
	  /** Name of the metric. */
		metric: Option[String] = None,
	  /** Confidence interval of a value that is of type `type.Decimal`. */
		decimalValueConfidenceInterval: Option[Schema.GooglePlayDeveloperReportingV1beta1DecimalConfidenceInterval] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1QueryErrorCountMetricSetRequest(
	  /** Maximum size of the returned data. If unspecified, at most 1000 rows will be returned. The maximum value is 100000; values above 100000 will be coerced to 100000. */
		pageSize: Option[Int] = None,
	  /** Filters to apply to data. The filtering expression follows [AIP-160](https://google.aip.dev/160) standard and supports filtering by equality of all breakdown dimensions and: &#42; `isUserPerceived` (string): denotes whether error is user perceived or not, USER_PERCEIVED or NOT_USER_PERCEIVED. */
		filter: Option[String] = None,
	  /** A page token, received from a previous call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to the request must match the call that provided the page token. */
		pageToken: Option[String] = None,
	  /** Specification of the timeline aggregation parameters. &#42;&#42;Supported aggregation periods:&#42;&#42; &#42; DAILY: metrics are aggregated in calendar date intervals. The default and only supported timezone is `America/Los_Angeles`. */
		timelineSpec: Option[Schema.GooglePlayDeveloperReportingV1beta1TimelineSpec] = None,
	  /** Metrics to aggregate. &#42;&#42;Supported metrics:&#42;&#42; &#42; `errorReportCount` (`google.type.Decimal`): Absolute count of individual error reports that have been received for an app. &#42; `distinctUsers` (`google.type.Decimal`): Count of distinct users for which reports have been received. Care must be taken not to aggregate this count further, as it may result in users being counted multiple times. This value is not rounded, however it may be an approximation. */
		metrics: Option[List[String]] = None,
	  /** Dimensions to slice the data by. &#42;&#42;Supported dimensions:&#42;&#42; &#42; `apiLevel` (string): the API level of Android that was running on the user's device, e.g., 26. &#42; `versionCode` (int64): unique identifier of the user's device model. The form of the identifier is 'deviceBrand/device', where deviceBrand corresponds to Build.BRAND and device corresponds to Build.DEVICE, e.g., google/coral. &#42; `deviceModel` (string): unique identifier of the user's device model. &#42; `deviceType` (string): identifier of the device's form factor, e.g., PHONE. &#42; `reportType` (string): the type of error. The value should correspond to one of the possible values in ErrorType. &#42; `issueId` (string): the id an error was assigned to. The value should correspond to the `{issue}` component of the issue name. &#42; `deviceRamBucket` (int64): RAM of the device, in MB, in buckets (3GB, 4GB, etc.). &#42; `deviceSocMake` (string): Make of the device's primary system-on-chip, e.g., Samsung. [Reference](https://developer.android.com/reference/android/os/Build#SOC_MANUFACTURER) &#42; `deviceSocModel` (string): Model of the device's primary system-on-chip, e.g., "Exynos 2100". [Reference](https://developer.android.com/reference/android/os/Build#SOC_MODEL) &#42; `deviceCpuMake` (string): Make of the device's CPU, e.g., Qualcomm. &#42; `deviceCpuModel` (string): Model of the device's CPU, e.g., "Kryo 240". &#42; `deviceGpuMake` (string): Make of the device's GPU, e.g., ARM. &#42; `deviceGpuModel` (string): Model of the device's GPU, e.g., Mali. &#42; `deviceGpuVersion` (string): Version of the device's GPU, e.g., T750. &#42; `deviceVulkanVersion` (string): Vulkan version of the device, e.g., "4198400". &#42; `deviceGlEsVersion` (string): OpenGL ES version of the device, e.g., "196610". &#42; `deviceScreenSize` (string): Screen size of the device, e.g., NORMAL, LARGE. &#42; `deviceScreenDpi` (string): Screen density of the device, e.g., mdpi, hdpi. */
		dimensions: Option[List[String]] = None
	)
	
	object GooglePlayDeveloperReportingV1beta1QuerySlowRenderingRateMetricSetRequest {
		enum UserCohortEnum extends Enum[UserCohortEnum] { case USER_COHORT_UNSPECIFIED, OS_PUBLIC, OS_BETA, APP_TESTERS }
	}
	case class GooglePlayDeveloperReportingV1beta1QuerySlowRenderingRateMetricSetRequest(
	  /** Dimensions to slice the data by. &#42;&#42;Supported dimensions:&#42;&#42; &#42; `apiLevel` (string): the API level of Android that was running on the user's device, e.g., 26. &#42; `versionCode` (int64): version of the app that was running on the user's device. &#42; `deviceModel` (string): unique identifier of the user's device model. The form of the identifier is 'deviceBrand/device', where deviceBrand corresponds to Build.BRAND and device corresponds to Build.DEVICE, e.g., google/coral. &#42; `deviceBrand` (string): unique identifier of the user's device brand, e.g., google. &#42; `deviceType` (string): the type (also known as form factor) of the user's device, e.g., PHONE. &#42; `countryCode` (string): the country or region of the user's device based on their IP address, represented as a 2-letter ISO-3166 code (e.g. US for the United States). &#42; `deviceRamBucket` (int64): RAM of the device, in MB, in buckets (3GB, 4GB, etc.). &#42; `deviceSocMake` (string): Make of the device's primary system-on-chip, e.g., Samsung. [Reference](https://developer.android.com/reference/android/os/Build#SOC_MANUFACTURER) &#42; `deviceSocModel` (string): Model of the device's primary system-on-chip, e.g., "Exynos 2100". [Reference](https://developer.android.com/reference/android/os/Build#SOC_MODEL) &#42; `deviceCpuMake` (string): Make of the device's CPU, e.g., Qualcomm. &#42; `deviceCpuModel` (string): Model of the device's CPU, e.g., "Kryo 240". &#42; `deviceGpuMake` (string): Make of the device's GPU, e.g., ARM. &#42; `deviceGpuModel` (string): Model of the device's GPU, e.g., Mali. &#42; `deviceGpuVersion` (string): Version of the device's GPU, e.g., T750. &#42; `deviceVulkanVersion` (string): Vulkan version of the device, e.g., "4198400". &#42; `deviceGlEsVersion` (string): OpenGL ES version of the device, e.g., "196610". &#42; `deviceScreenSize` (string): Screen size of the device, e.g., NORMAL, LARGE. &#42; `deviceScreenDpi` (string): Screen density of the device, e.g., mdpi, hdpi. */
		dimensions: Option[List[String]] = None,
	  /** Metrics to aggregate. &#42;&#42;Supported metrics:&#42;&#42; &#42; `slowRenderingRate20Fps` (`google.type.Decimal`): Percentage of distinct users in the aggregation period that had a slow rendering. &#42; `slowRenderingRate20Fps7dUserWeighted` (`google.type.Decimal`): Rolling average value of `slowRenderingRate20Fps` in the last 7 days. The daily values are weighted by the count of distinct users for the day. &#42; `slowRenderingRate20Fps28dUserWeighted` (`google.type.Decimal`): Rolling average value of `slowRenderingRate20Fps` in the last 28 days. The daily values are weighted by the count of distinct users for the day. &#42; `slowRenderingRate30Fps` (`google.type.Decimal`): Percentage of distinct users in the aggregation period that had a slow rendering. &#42; `slowRenderingRate30Fps7dUserWeighted` (`google.type.Decimal`): Rolling average value of `slowRenderingRate30Fps` in the last 7 days. The daily values are weighted by the count of distinct users for the day. &#42; `slowRenderingRate30Fps28dUserWeighted` (`google.type.Decimal`): Rolling average value of `slowRenderingRate30Fps` in the last 28 days. The daily values are weighted by the count of distinct users for the day. &#42; `distinctUsers` (`google.type.Decimal`): Count of distinct users in the aggregation period that were used as normalization value for the `slowRenderingRate20Fps`/`slowRenderingRate30Fps` metric. A user is counted in this metric if their app was launched in the device. Care must be taken not to aggregate this count further, as it may result in users being counted multiple times. The value is rounded to the nearest multiple of 10, 100, 1,000 or 1,000,000, depending on the magnitude of the value. */
		metrics: Option[List[String]] = None,
	  /** A page token, received from a previous call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to the request must match the call that provided the page token. */
		pageToken: Option[String] = None,
	  /** Filters to apply to data. The filtering expression follows [AIP-160](https://google.aip.dev/160) standard and supports filtering by equality of all breakdown dimensions. */
		filter: Option[String] = None,
	  /** User view to select. The output data will correspond to the selected view. &#42;&#42;Supported values:&#42;&#42; &#42; `OS_PUBLIC` To select data from all publicly released Android versions. This is the default. Supports all the above dimensions. &#42; `APP_TESTERS` To select data from users who have opted in to be testers. Supports all the above dimensions. &#42; `OS_BETA` To select data from beta Android versions only, excluding data from released Android versions. Only the following dimensions are supported: &#42; `versionCode` (int64): version of the app that was running on the user's device. &#42; `osBuild` (string): OS build of the user's device, e.g., "T1B2.220916.004". */
		userCohort: Option[Schema.GooglePlayDeveloperReportingV1beta1QuerySlowRenderingRateMetricSetRequest.UserCohortEnum] = None,
	  /** Maximum size of the returned data. If unspecified, at most 1000 rows will be returned. The maximum value is 100000; values above 100000 will be coerced to 100000. */
		pageSize: Option[Int] = None,
	  /** Specification of the timeline aggregation parameters. &#42;&#42;Supported aggregation periods:&#42;&#42; &#42; DAILY: metrics are aggregated in calendar date intervals. Due to historical constraints, the only supported timezone is `America/Los_Angeles`. */
		timelineSpec: Option[Schema.GooglePlayDeveloperReportingV1beta1TimelineSpec] = None
	)
	
	object GooglePlayDeveloperReportingV1beta1FreshnessInfoFreshness {
		enum AggregationPeriodEnum extends Enum[AggregationPeriodEnum] { case AGGREGATION_PERIOD_UNSPECIFIED, HOURLY, DAILY, FULL_RANGE }
	}
	case class GooglePlayDeveloperReportingV1beta1FreshnessInfoFreshness(
	  /** Aggregation period for which data is available. */
		aggregationPeriod: Option[Schema.GooglePlayDeveloperReportingV1beta1FreshnessInfoFreshness.AggregationPeriodEnum] = None,
	  /** Latest end time for which data is available, for the aggregation period. The time is specified in the metric set's default timezone. &#42;Note:&#42; time ranges in TimelineSpec are represented as `start_time, end_time)`. For example, if the latest available timeline data point for a `DAILY` aggregation period is `2021-06-23 00:00:00 America/Los_Angeles`, the value of this field would be `2021-06-24 00:00:00 America/Los_Angeles` so it can be easily reused in [TimelineSpec.end_time. */
		latestEndTime: Option[Schema.GoogleTypeDateTime] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1QueryAnrRateMetricSetResponse(
	  /** Returned rows of data. */
		rows: Option[List[Schema.GooglePlayDeveloperReportingV1beta1MetricsRow]] = None,
	  /** Continuation token to fetch the next page of data. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleTypeDateTime(
	  /** Optional. Month of year. Must be from 1 to 12, or 0 if specifying a datetime without a month. */
		month: Option[Int] = None,
	  /** Optional. Fractions of seconds in nanoseconds. Must be from 0 to 999,999,999, defaults to 0. */
		nanos: Option[Int] = None,
	  /** Optional. Year of date. Must be from 1 to 9999, or 0 if specifying a datetime without a year. */
		year: Option[Int] = None,
	  /** UTC offset. Must be whole seconds, between -18 hours and +18 hours. For example, a UTC offset of -4:00 would be represented as { seconds: -14400 }. */
		utcOffset: Option[String] = None,
	  /** Optional. Minutes of hour of day. Must be from 0 to 59, defaults to 0. */
		minutes: Option[Int] = None,
	  /** Optional. Hours of day in 24 hour format. Should be from 0 to 23, defaults to 0 (midnight). An API may choose to allow the value "24:00:00" for scenarios like business closing time. */
		hours: Option[Int] = None,
	  /** Optional. Day of month. Must be from 1 to 31 and valid for the year and month, or 0 if specifying a datetime without a day. */
		day: Option[Int] = None,
	  /** Optional. Seconds of minutes of the time. Must normally be from 0 to 59, defaults to 0. An API may allow the value 60 if it allows leap-seconds. */
		seconds: Option[Int] = None,
	  /** Time zone. */
		timeZone: Option[Schema.GoogleTypeTimeZone] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1FreshnessInfo(
	  /** Information about data freshness for every supported aggregation period. This field has set semantics, keyed by the `aggregation_period` field. */
		freshnesses: Option[List[Schema.GooglePlayDeveloperReportingV1beta1FreshnessInfoFreshness]] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1DecimalConfidenceInterval(
	  /** The confidence interval's upper bound. */
		upperBound: Option[Schema.GoogleTypeDecimal] = None,
	  /** The confidence interval's lower bound. */
		lowerBound: Option[Schema.GoogleTypeDecimal] = None
	)
	
	case class GooglePlayDeveloperReportingV1beta1CrashRateMetricSet(
	  /** Summary about data freshness in this resource. */
		freshnessInfo: Option[Schema.GooglePlayDeveloperReportingV1beta1FreshnessInfo] = None,
	  /** Identifier. The resource name. Format: apps/{app}/crashRateMetricSet */
		name: Option[String] = None
	)
	
	object GooglePlayDeveloperReportingV1beta1ErrorReport {
		enum TypeEnum extends Enum[TypeEnum] { case ERROR_TYPE_UNSPECIFIED, APPLICATION_NOT_RESPONDING, CRASH, NON_FATAL }
	}
	case class GooglePlayDeveloperReportingV1beta1ErrorReport(
	  /** Type of the error for which this report was generated. */
		`type`: Option[Schema.GooglePlayDeveloperReportingV1beta1ErrorReport.TypeEnum] = None,
	  /** A device model on which an event in this error report occurred on. */
		deviceModel: Option[Schema.GooglePlayDeveloperReportingV1beta1DeviceModelSummary] = None,
	  /** Start of the hour during which the latest event in this error report occurred. */
		eventTime: Option[String] = None,
	  /** The issue this report was associated with. &#42;&#42;Please note:&#42;&#42; this resource is currently in Alpha. There could be changes to the issue grouping that would result in similar but more recent error reports being assigned to a different issue. */
		issue: Option[String] = None,
	  /** Version control system information from BUNDLE-METADATA/version-control-info.textproto or META-INF/version-control-info.textproto of the app bundle or APK, respectively. */
		vcsInformation: Option[String] = None,
	  /** The resource name of the report. Format: apps/{app}/{report} */
		name: Option[String] = None,
	  /** The app version on which an event in this error report occurred on. */
		appVersion: Option[Schema.GooglePlayDeveloperReportingV1beta1AppVersion] = None,
	  /** The OS version on which an event in this error report occurred on. */
		osVersion: Option[Schema.GooglePlayDeveloperReportingV1beta1OsVersion] = None,
	  /** Textual representation of the error report. These textual reports are produced by the platform. The reports are then sanitized and filtered to remove any potentially sensitive information. Although their format is fairly stable, they are not entirely meant for machine consumption and we cannot guarantee that there won't be subtle changes to the formatting that may break systems trying to parse information out of the reports. */
		reportText: Option[String] = None
	)
}
