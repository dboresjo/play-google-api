package com.boresjo.play.api.google.analyticsdata

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object RunReportRequest {
		enum MetricAggregationsEnum extends Enum[MetricAggregationsEnum] { case METRIC_AGGREGATION_UNSPECIFIED, TOTAL, MINIMUM, MAXIMUM, COUNT }
	}
	case class RunReportRequest(
	  /** A Google Analytics property identifier whose events are tracked. Specified in the URL path and not the body. To learn more, see [where to find your Property ID](https://developers.google.com/analytics/devguides/reporting/data/v1/property-id). Within a batch request, this property should either be unspecified or consistent with the batch-level property. Example: properties/1234 */
		property: Option[String] = None,
	  /** The dimensions requested and displayed. */
		dimensions: Option[List[Schema.Dimension]] = None,
	  /** The metrics requested and displayed. */
		metrics: Option[List[Schema.Metric]] = None,
	  /** Date ranges of data to read. If multiple date ranges are requested, each response row will contain a zero based date range index. If two date ranges overlap, the event data for the overlapping days is included in the response rows for both date ranges. In a cohort request, this `dateRanges` must be unspecified. */
		dateRanges: Option[List[Schema.DateRange]] = None,
	  /** Dimension filters let you ask for only specific dimension values in the report. To learn more, see [Fundamentals of Dimension Filters](https://developers.google.com/analytics/devguides/reporting/data/v1/basics#dimension_filters) for examples. Metrics cannot be used in this filter. */
		dimensionFilter: Option[Schema.FilterExpression] = None,
	  /** The filter clause of metrics. Applied after aggregating the report's rows, similar to SQL having-clause. Dimensions cannot be used in this filter. */
		metricFilter: Option[Schema.FilterExpression] = None,
	  /** The row count of the start row. The first row is counted as row 0. When paging, the first request does not specify offset; or equivalently, sets offset to 0; the first request returns the first `limit` of rows. The second request sets offset to the `limit` of the first request; the second request returns the second `limit` of rows. To learn more about this pagination parameter, see [Pagination](https://developers.google.com/analytics/devguides/reporting/data/v1/basics#pagination). */
		offset: Option[String] = None,
	  /** The number of rows to return. If unspecified, 10,000 rows are returned. The API returns a maximum of 250,000 rows per request, no matter how many you ask for. `limit` must be positive. The API can also return fewer rows than the requested `limit`, if there aren't as many dimension values as the `limit`. For instance, there are fewer than 300 possible values for the dimension `country`, so when reporting on only `country`, you can't get more than 300 rows, even if you set `limit` to a higher value. To learn more about this pagination parameter, see [Pagination](https://developers.google.com/analytics/devguides/reporting/data/v1/basics#pagination). */
		limit: Option[String] = None,
	  /** Aggregation of metrics. Aggregated metric values will be shown in rows where the dimension_values are set to "RESERVED_(MetricAggregation)". Aggregates including both comparisons and multiple date ranges will be aggregated based on the date ranges. */
		metricAggregations: Option[List[Schema.RunReportRequest.MetricAggregationsEnum]] = None,
	  /** Specifies how rows are ordered in the response. Requests including both comparisons and multiple date ranges will have order bys applied on the comparisons. */
		orderBys: Option[List[Schema.OrderBy]] = None,
	  /** A currency code in ISO4217 format, such as "AED", "USD", "JPY". If the field is empty, the report uses the property's default currency. */
		currencyCode: Option[String] = None,
	  /** Cohort group associated with this request. If there is a cohort group in the request the 'cohort' dimension must be present. */
		cohortSpec: Option[Schema.CohortSpec] = None,
	  /** If false or unspecified, each row with all metrics equal to 0 will not be returned. If true, these rows will be returned if they are not separately removed by a filter. Regardless of this `keep_empty_rows` setting, only data recorded by the Google Analytics property can be displayed in a report. For example if a property never logs a `purchase` event, then a query for the `eventName` dimension and `eventCount` metric will not have a row eventName: "purchase" and eventCount: 0. */
		keepEmptyRows: Option[Boolean] = None,
	  /** Toggles whether to return the current state of this Google Analytics property's quota. Quota is returned in [PropertyQuota](#PropertyQuota). */
		returnPropertyQuota: Option[Boolean] = None,
	  /** Optional. The configuration of comparisons requested and displayed. The request only requires a comparisons field in order to receive a comparison column in the response. */
		comparisons: Option[List[Schema.Comparison]] = None
	)
	
	case class Dimension(
	  /** The name of the dimension. See the [API Dimensions](https://developers.google.com/analytics/devguides/reporting/data/v1/api-schema#dimensions) for the list of dimension names supported by core reporting methods such as `runReport` and `batchRunReports`. See [Realtime Dimensions](https://developers.google.com/analytics/devguides/reporting/data/v1/realtime-api-schema#dimensions) for the list of dimension names supported by the `runRealtimeReport` method. See [Funnel Dimensions](https://developers.google.com/analytics/devguides/reporting/data/v1/exploration-api-schema#dimensions) for the list of dimension names supported by the `runFunnelReport` method. If `dimensionExpression` is specified, `name` can be any string that you would like within the allowed character set. For example if a `dimensionExpression` concatenates `country` and `city`, you could call that dimension `countryAndCity`. Dimension names that you choose must match the regular expression `^[a-zA-Z0-9_]$`. Dimensions are referenced by `name` in `dimensionFilter`, `orderBys`, `dimensionExpression`, and `pivots`. */
		name: Option[String] = None,
	  /** One dimension can be the result of an expression of multiple dimensions. For example, dimension "country, city": concatenate(country, ", ", city). */
		dimensionExpression: Option[Schema.DimensionExpression] = None
	)
	
	case class DimensionExpression(
	  /** Used to convert a dimension value to lower case. */
		lowerCase: Option[Schema.CaseExpression] = None,
	  /** Used to convert a dimension value to upper case. */
		upperCase: Option[Schema.CaseExpression] = None,
	  /** Used to combine dimension values to a single dimension. For example, dimension "country, city": concatenate(country, ", ", city). */
		concatenate: Option[Schema.ConcatenateExpression] = None
	)
	
	case class CaseExpression(
	  /** Name of a dimension. The name must refer back to a name in dimensions field of the request. */
		dimensionName: Option[String] = None
	)
	
	case class ConcatenateExpression(
	  /** Names of dimensions. The names must refer back to names in the dimensions field of the request. */
		dimensionNames: Option[List[String]] = None,
	  /** The delimiter placed between dimension names. Delimiters are often single characters such as "|" or "," but can be longer strings. If a dimension value contains the delimiter, both will be present in response with no distinction. For example if dimension 1 value = "US,FR", dimension 2 value = "JP", and delimiter = ",", then the response will contain "US,FR,JP". */
		delimiter: Option[String] = None
	)
	
	case class Metric(
	  /** The name of the metric. See the [API Metrics](https://developers.google.com/analytics/devguides/reporting/data/v1/api-schema#metrics) for the list of metric names supported by core reporting methods such as `runReport` and `batchRunReports`. See [Realtime Metrics](https://developers.google.com/analytics/devguides/reporting/data/v1/realtime-api-schema#metrics) for the list of metric names supported by the `runRealtimeReport` method. See [Funnel Metrics](https://developers.google.com/analytics/devguides/reporting/data/v1/exploration-api-schema#metrics) for the list of metric names supported by the `runFunnelReport` method. If `expression` is specified, `name` can be any string that you would like within the allowed character set. For example if `expression` is `screenPageViews/sessions`, you could call that metric's name = `viewsPerSession`. Metric names that you choose must match the regular expression `^[a-zA-Z0-9_]$`. Metrics are referenced by `name` in `metricFilter`, `orderBys`, and metric `expression`. */
		name: Option[String] = None,
	  /** A mathematical expression for derived metrics. For example, the metric Event count per user is `eventCount/totalUsers`. */
		expression: Option[String] = None,
	  /** Indicates if a metric is invisible in the report response. If a metric is invisible, the metric will not produce a column in the response, but can be used in `metricFilter`, `orderBys`, or a metric `expression`. */
		invisible: Option[Boolean] = None
	)
	
	case class DateRange(
	  /** The inclusive start date for the query in the format `YYYY-MM-DD`. Cannot be after `end_date`. The format `NdaysAgo`, `yesterday`, or `today` is also accepted, and in that case, the date is inferred based on the property's reporting time zone. */
		startDate: Option[String] = None,
	  /** The inclusive end date for the query in the format `YYYY-MM-DD`. Cannot be before `start_date`. The format `NdaysAgo`, `yesterday`, or `today` is also accepted, and in that case, the date is inferred based on the property's reporting time zone. */
		endDate: Option[String] = None,
	  /** Assigns a name to this date range. The dimension `dateRange` is valued to this name in a report response. If set, cannot begin with `date_range_` or `RESERVED_`. If not set, date ranges are named by their zero based index in the request: `date_range_0`, `date_range_1`, etc. */
		name: Option[String] = None
	)
	
	case class FilterExpression(
	  /** The FilterExpressions in and_group have an AND relationship. */
		andGroup: Option[Schema.FilterExpressionList] = None,
	  /** The FilterExpressions in or_group have an OR relationship. */
		orGroup: Option[Schema.FilterExpressionList] = None,
	  /** The FilterExpression is NOT of not_expression. */
		notExpression: Option[Schema.FilterExpression] = None,
	  /** A primitive filter. In the same FilterExpression, all of the filter's field names need to be either all dimensions or all metrics. */
		filter: Option[Schema.Filter] = None
	)
	
	case class FilterExpressionList(
	  /** A list of filter expressions. */
		expressions: Option[List[Schema.FilterExpression]] = None
	)
	
	case class Filter(
	  /** The dimension name or metric name. In most methods, dimensions & metrics can be used for the first time in this field. However in a RunPivotReportRequest, this field must be additionally specified by name in the RunPivotReportRequest's dimensions or metrics. */
		fieldName: Option[String] = None,
	  /** Strings related filter. */
		stringFilter: Option[Schema.StringFilter] = None,
	  /** A filter for in list values. */
		inListFilter: Option[Schema.InListFilter] = None,
	  /** A filter for numeric or date values. */
		numericFilter: Option[Schema.NumericFilter] = None,
	  /** A filter for two values. */
		betweenFilter: Option[Schema.BetweenFilter] = None
	)
	
	object StringFilter {
		enum MatchTypeEnum extends Enum[MatchTypeEnum] { case MATCH_TYPE_UNSPECIFIED, EXACT, BEGINS_WITH, ENDS_WITH, CONTAINS, FULL_REGEXP, PARTIAL_REGEXP }
	}
	case class StringFilter(
	  /** The match type for this filter. */
		matchType: Option[Schema.StringFilter.MatchTypeEnum] = None,
	  /** The string value used for the matching. */
		value: Option[String] = None,
	  /** If true, the string value is case sensitive. */
		caseSensitive: Option[Boolean] = None
	)
	
	case class InListFilter(
	  /** The list of string values. Must be non-empty. */
		values: Option[List[String]] = None,
	  /** If true, the string value is case sensitive. */
		caseSensitive: Option[Boolean] = None
	)
	
	object NumericFilter {
		enum OperationEnum extends Enum[OperationEnum] { case OPERATION_UNSPECIFIED, EQUAL, LESS_THAN, LESS_THAN_OR_EQUAL, GREATER_THAN, GREATER_THAN_OR_EQUAL }
	}
	case class NumericFilter(
	  /** The operation type for this filter. */
		operation: Option[Schema.NumericFilter.OperationEnum] = None,
	  /** A numeric value or a date value. */
		value: Option[Schema.NumericValue] = None
	)
	
	case class NumericValue(
	  /** Integer value */
		int64Value: Option[String] = None,
	  /** Double value */
		doubleValue: Option[BigDecimal] = None
	)
	
	case class BetweenFilter(
	  /** Begins with this number. */
		fromValue: Option[Schema.NumericValue] = None,
	  /** Ends with this number. */
		toValue: Option[Schema.NumericValue] = None
	)
	
	case class OrderBy(
	  /** Sorts results by a metric's values. */
		metric: Option[Schema.MetricOrderBy] = None,
	  /** Sorts results by a dimension's values. */
		dimension: Option[Schema.DimensionOrderBy] = None,
	  /** Sorts results by a metric's values within a pivot column group. */
		pivot: Option[Schema.PivotOrderBy] = None,
	  /** If true, sorts by descending order. */
		desc: Option[Boolean] = None
	)
	
	case class MetricOrderBy(
	  /** A metric name in the request to order by. */
		metricName: Option[String] = None
	)
	
	object DimensionOrderBy {
		enum OrderTypeEnum extends Enum[OrderTypeEnum] { case ORDER_TYPE_UNSPECIFIED, ALPHANUMERIC, CASE_INSENSITIVE_ALPHANUMERIC, NUMERIC }
	}
	case class DimensionOrderBy(
	  /** A dimension name in the request to order by. */
		dimensionName: Option[String] = None,
	  /** Controls the rule for dimension value ordering. */
		orderType: Option[Schema.DimensionOrderBy.OrderTypeEnum] = None
	)
	
	case class PivotOrderBy(
	  /** In the response to order by, order rows by this column. Must be a metric name from the request. */
		metricName: Option[String] = None,
	  /** Used to select a dimension name and value pivot. If multiple pivot selections are given, the sort occurs on rows where all pivot selection dimension name and value pairs match the row's dimension name and value pair. */
		pivotSelections: Option[List[Schema.PivotSelection]] = None
	)
	
	case class PivotSelection(
	  /** Must be a dimension name from the request. */
		dimensionName: Option[String] = None,
	  /** Order by only when the named dimension is this value. */
		dimensionValue: Option[String] = None
	)
	
	case class CohortSpec(
	  /** Defines the selection criteria to group users into cohorts. Most cohort reports define only a single cohort. If multiple cohorts are specified, each cohort can be recognized in the report by their name. */
		cohorts: Option[List[Schema.Cohort]] = None,
	  /** Cohort reports follow cohorts over an extended reporting date range. This range specifies an offset duration to follow the cohorts over. */
		cohortsRange: Option[Schema.CohortsRange] = None,
	  /** Optional settings for a cohort report. */
		cohortReportSettings: Option[Schema.CohortReportSettings] = None
	)
	
	case class Cohort(
	  /** Assigns a name to this cohort. The dimension `cohort` is valued to this name in a report response. If set, cannot begin with `cohort_` or `RESERVED_`. If not set, cohorts are named by their zero based index `cohort_0`, `cohort_1`, etc. */
		name: Option[String] = None,
	  /** Dimension used by the cohort. Required and only supports `firstSessionDate`. */
		dimension: Option[String] = None,
	  /** The cohort selects users whose first touch date is between start date and end date defined in the `dateRange`. This `dateRange` does not specify the full date range of event data that is present in a cohort report. In a cohort report, this `dateRange` is extended by the granularity and offset present in the `cohortsRange`; event data for the extended reporting date range is present in a cohort report. In a cohort request, this `dateRange` is required and the `dateRanges` in the `RunReportRequest` or `RunPivotReportRequest` must be unspecified. This `dateRange` should generally be aligned with the cohort's granularity. If `CohortsRange` uses daily granularity, this `dateRange` can be a single day. If `CohortsRange` uses weekly granularity, this `dateRange` can be aligned to a week boundary, starting at Sunday and ending Saturday. If `CohortsRange` uses monthly granularity, this `dateRange` can be aligned to a month, starting at the first and ending on the last day of the month. */
		dateRange: Option[Schema.DateRange] = None
	)
	
	object CohortsRange {
		enum GranularityEnum extends Enum[GranularityEnum] { case GRANULARITY_UNSPECIFIED, DAILY, WEEKLY, MONTHLY }
	}
	case class CohortsRange(
	  /** Required. The granularity used to interpret the `startOffset` and `endOffset` for the extended reporting date range for a cohort report. */
		granularity: Option[Schema.CohortsRange.GranularityEnum] = None,
	  /** `startOffset` specifies the start date of the extended reporting date range for a cohort report. `startOffset` is commonly set to 0 so that reports contain data from the acquisition of the cohort forward. If `granularity` is `DAILY`, the `startDate` of the extended reporting date range is `startDate` of the cohort plus `startOffset` days. If `granularity` is `WEEKLY`, the `startDate` of the extended reporting date range is `startDate` of the cohort plus `startOffset &#42; 7` days. If `granularity` is `MONTHLY`, the `startDate` of the extended reporting date range is `startDate` of the cohort plus `startOffset &#42; 30` days. */
		startOffset: Option[Int] = None,
	  /** Required. `endOffset` specifies the end date of the extended reporting date range for a cohort report. `endOffset` can be any positive integer but is commonly set to 5 to 10 so that reports contain data on the cohort for the next several granularity time periods. If `granularity` is `DAILY`, the `endDate` of the extended reporting date range is `endDate` of the cohort plus `endOffset` days. If `granularity` is `WEEKLY`, the `endDate` of the extended reporting date range is `endDate` of the cohort plus `endOffset &#42; 7` days. If `granularity` is `MONTHLY`, the `endDate` of the extended reporting date range is `endDate` of the cohort plus `endOffset &#42; 30` days. */
		endOffset: Option[Int] = None
	)
	
	case class CohortReportSettings(
	  /** If true, accumulates the result from first touch day to the end day. Not supported in `RunReportRequest`. */
		accumulate: Option[Boolean] = None
	)
	
	case class Comparison(
	  /** Each comparison produces separate rows in the response. In the response, this comparison is identified by this name. If name is unspecified, we will use the saved comparisons display name. */
		name: Option[String] = None,
	  /** A basic comparison. */
		dimensionFilter: Option[Schema.FilterExpression] = None,
	  /** A saved comparison identified by the comparison's resource name. For example, 'comparisons/1234'. */
		comparison: Option[String] = None
	)
	
	case class RunReportResponse(
	  /** Describes dimension columns. The number of DimensionHeaders and ordering of DimensionHeaders matches the dimensions present in rows. */
		dimensionHeaders: Option[List[Schema.DimensionHeader]] = None,
	  /** Describes metric columns. The number of MetricHeaders and ordering of MetricHeaders matches the metrics present in rows. */
		metricHeaders: Option[List[Schema.MetricHeader]] = None,
	  /** Rows of dimension value combinations and metric values in the report. */
		rows: Option[List[Schema.Row]] = None,
	  /** If requested, the totaled values of metrics. */
		totals: Option[List[Schema.Row]] = None,
	  /** If requested, the maximum values of metrics. */
		maximums: Option[List[Schema.Row]] = None,
	  /** If requested, the minimum values of metrics. */
		minimums: Option[List[Schema.Row]] = None,
	  /** The total number of rows in the query result. `rowCount` is independent of the number of rows returned in the response, the `limit` request parameter, and the `offset` request parameter. For example if a query returns 175 rows and includes `limit` of 50 in the API request, the response will contain `rowCount` of 175 but only 50 rows. To learn more about this pagination parameter, see [Pagination](https://developers.google.com/analytics/devguides/reporting/data/v1/basics#pagination). */
		rowCount: Option[Int] = None,
	  /** Metadata for the report. */
		metadata: Option[Schema.ResponseMetaData] = None,
	  /** This Google Analytics property's quota state including this request. */
		propertyQuota: Option[Schema.PropertyQuota] = None,
	  /** Identifies what kind of resource this message is. This `kind` is always the fixed string "analyticsData#runReport". Useful to distinguish between response types in JSON. */
		kind: Option[String] = None
	)
	
	case class DimensionHeader(
	  /** The dimension's name. */
		name: Option[String] = None
	)
	
	object MetricHeader {
		enum TypeEnum extends Enum[TypeEnum] { case METRIC_TYPE_UNSPECIFIED, TYPE_INTEGER, TYPE_FLOAT, TYPE_SECONDS, TYPE_MILLISECONDS, TYPE_MINUTES, TYPE_HOURS, TYPE_STANDARD, TYPE_CURRENCY, TYPE_FEET, TYPE_MILES, TYPE_METERS, TYPE_KILOMETERS }
	}
	case class MetricHeader(
	  /** The metric's name. */
		name: Option[String] = None,
	  /** The metric's data type. */
		`type`: Option[Schema.MetricHeader.TypeEnum] = None
	)
	
	case class Row(
	  /** List of requested dimension values. In a PivotReport, dimension_values are only listed for dimensions included in a pivot. */
		dimensionValues: Option[List[Schema.DimensionValue]] = None,
	  /** List of requested visible metric values. */
		metricValues: Option[List[Schema.MetricValue]] = None
	)
	
	case class DimensionValue(
	  /** Value as a string if the dimension type is a string. */
		value: Option[String] = None
	)
	
	case class MetricValue(
	  /** Measurement value. See MetricHeader for type. */
		value: Option[String] = None
	)
	
	case class ResponseMetaData(
	  /** If true, indicates some buckets of dimension combinations are rolled into "(other)" row. This can happen for high cardinality reports. The metadata parameter dataLossFromOtherRow is populated based on the aggregated data table used in the report. The parameter will be accurately populated regardless of the filters and limits in the report. For example, the (other) row could be dropped from the report because the request contains a filter on sessionSource = google. This parameter will still be populated if data loss from other row was present in the input aggregate data used to generate this report. To learn more, see [About the (other) row and data sampling](https://support.google.com/analytics/answer/13208658#reports). */
		dataLossFromOtherRow: Option[Boolean] = None,
	  /** Describes the schema restrictions actively enforced in creating this report. To learn more, see [Access and data-restriction management](https://support.google.com/analytics/answer/10851388). */
		schemaRestrictionResponse: Option[Schema.SchemaRestrictionResponse] = None,
	  /** The currency code used in this report. Intended to be used in formatting currency metrics like `purchaseRevenue` for visualization. If currency_code was specified in the request, this response parameter will echo the request parameter; otherwise, this response parameter is the property's current currency_code. Currency codes are string encodings of currency types from the ISO 4217 standard (https://en.wikipedia.org/wiki/ISO_4217); for example "USD", "EUR", "JPY". To learn more, see https://support.google.com/analytics/answer/9796179. */
		currencyCode: Option[String] = None,
	  /** The property's current timezone. Intended to be used to interpret time-based dimensions like `hour` and `minute`. Formatted as strings from the IANA Time Zone database (https://www.iana.org/time-zones); for example "America/New_York" or "Asia/Tokyo". */
		timeZone: Option[String] = None,
	  /** If empty reason is specified, the report is empty for this reason. */
		emptyReason: Option[String] = None,
	  /** If `subjectToThresholding` is true, this report is subject to thresholding and only returns data that meets the minimum aggregation thresholds. It is possible for a request to be subject to thresholding thresholding and no data is absent from the report, and this happens when all data is above the thresholds. To learn more, see [Data thresholds](https://support.google.com/analytics/answer/9383630). */
		subjectToThresholding: Option[Boolean] = None,
	  /** If this report results is [sampled](https://support.google.com/analytics/answer/13331292), this describes the percentage of events used in this report. One `samplingMetadatas` is populated for each date range. Each `samplingMetadatas` corresponds to a date range in order that date ranges were specified in the request. However if the results are not sampled, this field will not be defined. */
		samplingMetadatas: Option[List[Schema.SamplingMetadata]] = None
	)
	
	case class SchemaRestrictionResponse(
	  /** All restrictions actively enforced in creating the report. For example, `purchaseRevenue` always has the restriction type `REVENUE_DATA`. However, this active response restriction is only populated if the user's custom role disallows access to `REVENUE_DATA`. */
		activeMetricRestrictions: Option[List[Schema.ActiveMetricRestriction]] = None
	)
	
	object ActiveMetricRestriction {
		enum RestrictedMetricTypesEnum extends Enum[RestrictedMetricTypesEnum] { case RESTRICTED_METRIC_TYPE_UNSPECIFIED, COST_DATA, REVENUE_DATA }
	}
	case class ActiveMetricRestriction(
	  /** The name of the restricted metric. */
		metricName: Option[String] = None,
	  /** The reason for this metric's restriction. */
		restrictedMetricTypes: Option[List[Schema.ActiveMetricRestriction.RestrictedMetricTypesEnum]] = None
	)
	
	case class SamplingMetadata(
	  /** The total number of events read in this sampled report for a date range. This is the size of the subset this property's data that was analyzed in this report. */
		samplesReadCount: Option[String] = None,
	  /** The total number of events present in this property's data that could have been analyzed in this report for a date range. Sampling uncovers the meaningful information about the larger data set, and this is the size of the larger data set. To calculate the percentage of available data that was used in this report, compute `samplesReadCount/samplingSpaceSize`. */
		samplingSpaceSize: Option[String] = None
	)
	
	case class PropertyQuota(
	  /** Standard Analytics Properties can use up to 200,000 tokens per day; Analytics 360 Properties can use 2,000,000 tokens per day. Most requests consume fewer than 10 tokens. */
		tokensPerDay: Option[Schema.QuotaStatus] = None,
	  /** Standard Analytics Properties can use up to 40,000 tokens per hour; Analytics 360 Properties can use 400,000 tokens per hour. An API request consumes a single number of tokens, and that number is deducted from all of the hourly, daily, and per project hourly quotas. */
		tokensPerHour: Option[Schema.QuotaStatus] = None,
	  /** Standard Analytics Properties can send up to 10 concurrent requests; Analytics 360 Properties can use up to 50 concurrent requests. */
		concurrentRequests: Option[Schema.QuotaStatus] = None,
	  /** Standard Analytics Properties and cloud project pairs can have up to 10 server errors per hour; Analytics 360 Properties and cloud project pairs can have up to 50 server errors per hour. */
		serverErrorsPerProjectPerHour: Option[Schema.QuotaStatus] = None,
	  /** Analytics Properties can send up to 120 requests with potentially thresholded dimensions per hour. In a batch request, each report request is individually counted for this quota if the request contains potentially thresholded dimensions. */
		potentiallyThresholdedRequestsPerHour: Option[Schema.QuotaStatus] = None,
	  /** Analytics Properties can use up to 35% of their tokens per project per hour. This amounts to standard Analytics Properties can use up to 14,000 tokens per project per hour, and Analytics 360 Properties can use 140,000 tokens per project per hour. An API request consumes a single number of tokens, and that number is deducted from all of the hourly, daily, and per project hourly quotas. */
		tokensPerProjectPerHour: Option[Schema.QuotaStatus] = None
	)
	
	case class QuotaStatus(
	  /** Quota consumed by this request. */
		consumed: Option[Int] = None,
	  /** Quota remaining after this request. */
		remaining: Option[Int] = None
	)
	
	case class RunPivotReportRequest(
	  /** A Google Analytics property identifier whose events are tracked. Specified in the URL path and not the body. To learn more, see [where to find your Property ID](https://developers.google.com/analytics/devguides/reporting/data/v1/property-id). Within a batch request, this property should either be unspecified or consistent with the batch-level property. Example: properties/1234 */
		property: Option[String] = None,
	  /** The dimensions requested. All defined dimensions must be used by one of the following: dimension_expression, dimension_filter, pivots, order_bys. */
		dimensions: Option[List[Schema.Dimension]] = None,
	  /** The metrics requested, at least one metric needs to be specified. All defined metrics must be used by one of the following: metric_expression, metric_filter, order_bys. */
		metrics: Option[List[Schema.Metric]] = None,
	  /** The date range to retrieve event data for the report. If multiple date ranges are specified, event data from each date range is used in the report. A special dimension with field name "dateRange" can be included in a Pivot's field names; if included, the report compares between date ranges. In a cohort request, this `dateRanges` must be unspecified. */
		dateRanges: Option[List[Schema.DateRange]] = None,
	  /** Describes the visual format of the report's dimensions in columns or rows. The union of the fieldNames (dimension names) in all pivots must be a subset of dimension names defined in Dimensions. No two pivots can share a dimension. A dimension is only visible if it appears in a pivot. */
		pivots: Option[List[Schema.Pivot]] = None,
	  /** The filter clause of dimensions. Dimensions must be requested to be used in this filter. Metrics cannot be used in this filter. */
		dimensionFilter: Option[Schema.FilterExpression] = None,
	  /** The filter clause of metrics. Applied at post aggregation phase, similar to SQL having-clause. Metrics must be requested to be used in this filter. Dimensions cannot be used in this filter. */
		metricFilter: Option[Schema.FilterExpression] = None,
	  /** A currency code in ISO4217 format, such as "AED", "USD", "JPY". If the field is empty, the report uses the property's default currency. */
		currencyCode: Option[String] = None,
	  /** Cohort group associated with this request. If there is a cohort group in the request the 'cohort' dimension must be present. */
		cohortSpec: Option[Schema.CohortSpec] = None,
	  /** If false or unspecified, each row with all metrics equal to 0 will not be returned. If true, these rows will be returned if they are not separately removed by a filter. Regardless of this `keep_empty_rows` setting, only data recorded by the Google Analytics property can be displayed in a report. For example if a property never logs a `purchase` event, then a query for the `eventName` dimension and `eventCount` metric will not have a row eventName: "purchase" and eventCount: 0. */
		keepEmptyRows: Option[Boolean] = None,
	  /** Toggles whether to return the current state of this Google Analytics property's quota. Quota is returned in [PropertyQuota](#PropertyQuota). */
		returnPropertyQuota: Option[Boolean] = None,
	  /** Optional. The configuration of comparisons requested and displayed. The request requires both a comparisons field and a comparisons dimension to receive a comparison column in the response. */
		comparisons: Option[List[Schema.Comparison]] = None
	)
	
	object Pivot {
		enum MetricAggregationsEnum extends Enum[MetricAggregationsEnum] { case METRIC_AGGREGATION_UNSPECIFIED, TOTAL, MINIMUM, MAXIMUM, COUNT }
	}
	case class Pivot(
	  /** Dimension names for visible columns in the report response. Including "dateRange" produces a date range column; for each row in the response, dimension values in the date range column will indicate the corresponding date range from the request. */
		fieldNames: Option[List[String]] = None,
	  /** Specifies how dimensions are ordered in the pivot. In the first Pivot, the OrderBys determine Row and PivotDimensionHeader ordering; in subsequent Pivots, the OrderBys determine only PivotDimensionHeader ordering. Dimensions specified in these OrderBys must be a subset of Pivot.field_names. */
		orderBys: Option[List[Schema.OrderBy]] = None,
	  /** The row count of the start row. The first row is counted as row 0. */
		offset: Option[String] = None,
	  /** The number of unique combinations of dimension values to return in this pivot. The `limit` parameter is required. A `limit` of 10,000 is common for single pivot requests. The product of the `limit` for each `pivot` in a `RunPivotReportRequest` must not exceed 250,000. For example, a two pivot request with `limit: 1000` in each pivot will fail because the product is `1,000,000`. */
		limit: Option[String] = None,
	  /** Aggregate the metrics by dimensions in this pivot using the specified metric_aggregations. */
		metricAggregations: Option[List[Schema.Pivot.MetricAggregationsEnum]] = None
	)
	
	case class RunPivotReportResponse(
	  /** Summarizes the columns and rows created by a pivot. Each pivot in the request produces one header in the response. If we have a request like this: "pivots": [{ "fieldNames": ["country", "city"] }, { "fieldNames": "eventName" }] We will have the following `pivotHeaders` in the response: "pivotHeaders" : [{ "dimensionHeaders": [{ "dimensionValues": [ { "value": "United Kingdom" }, { "value": "London" } ] }, { "dimensionValues": [ { "value": "Japan" }, { "value": "Osaka" } ] }] }, { "dimensionHeaders": [{ "dimensionValues": [{ "value": "session_start" }] }, { "dimensionValues": [{ "value": "scroll" }] }] }] */
		pivotHeaders: Option[List[Schema.PivotHeader]] = None,
	  /** Describes dimension columns. The number of DimensionHeaders and ordering of DimensionHeaders matches the dimensions present in rows. */
		dimensionHeaders: Option[List[Schema.DimensionHeader]] = None,
	  /** Describes metric columns. The number of MetricHeaders and ordering of MetricHeaders matches the metrics present in rows. */
		metricHeaders: Option[List[Schema.MetricHeader]] = None,
	  /** Rows of dimension value combinations and metric values in the report. */
		rows: Option[List[Schema.Row]] = None,
	  /** Aggregation of metric values. Can be totals, minimums, or maximums. The returned aggregations are controlled by the metric_aggregations in the pivot. The type of aggregation returned in each row is shown by the dimension_values which are set to "RESERVED_". */
		aggregates: Option[List[Schema.Row]] = None,
	  /** Metadata for the report. */
		metadata: Option[Schema.ResponseMetaData] = None,
	  /** This Google Analytics property's quota state including this request. */
		propertyQuota: Option[Schema.PropertyQuota] = None,
	  /** Identifies what kind of resource this message is. This `kind` is always the fixed string "analyticsData#runPivotReport". Useful to distinguish between response types in JSON. */
		kind: Option[String] = None
	)
	
	case class PivotHeader(
	  /** The size is the same as the cardinality of the corresponding dimension combinations. */
		pivotDimensionHeaders: Option[List[Schema.PivotDimensionHeader]] = None,
	  /** The cardinality of the pivot. The total number of rows for this pivot's fields regardless of how the parameters `offset` and `limit` are specified in the request. */
		rowCount: Option[Int] = None
	)
	
	case class PivotDimensionHeader(
	  /** Values of multiple dimensions in a pivot. */
		dimensionValues: Option[List[Schema.DimensionValue]] = None
	)
	
	case class BatchRunReportsRequest(
	  /** Individual requests. Each request has a separate report response. Each batch request is allowed up to 5 requests. */
		requests: Option[List[Schema.RunReportRequest]] = None
	)
	
	case class BatchRunReportsResponse(
	  /** Individual responses. Each response has a separate report request. */
		reports: Option[List[Schema.RunReportResponse]] = None,
	  /** Identifies what kind of resource this message is. This `kind` is always the fixed string "analyticsData#batchRunReports". Useful to distinguish between response types in JSON. */
		kind: Option[String] = None
	)
	
	case class BatchRunPivotReportsRequest(
	  /** Individual requests. Each request has a separate pivot report response. Each batch request is allowed up to 5 requests. */
		requests: Option[List[Schema.RunPivotReportRequest]] = None
	)
	
	case class BatchRunPivotReportsResponse(
	  /** Individual responses. Each response has a separate pivot report request. */
		pivotReports: Option[List[Schema.RunPivotReportResponse]] = None,
	  /** Identifies what kind of resource this message is. This `kind` is always the fixed string "analyticsData#batchRunPivotReports". Useful to distinguish between response types in JSON. */
		kind: Option[String] = None
	)
	
	case class Metadata(
	  /** Resource name of this metadata. */
		name: Option[String] = None,
	  /** The dimension descriptions. */
		dimensions: Option[List[Schema.DimensionMetadata]] = None,
	  /** The metric descriptions. */
		metrics: Option[List[Schema.MetricMetadata]] = None,
	  /** The comparison descriptions. */
		comparisons: Option[List[Schema.ComparisonMetadata]] = None
	)
	
	case class DimensionMetadata(
	  /** This dimension's name. Useable in [Dimension](#Dimension)'s `name`. For example, `eventName`. */
		apiName: Option[String] = None,
	  /** This dimension's name within the Google Analytics user interface. For example, `Event name`. */
		uiName: Option[String] = None,
	  /** Description of how this dimension is used and calculated. */
		description: Option[String] = None,
	  /** Still usable but deprecated names for this dimension. If populated, this dimension is available by either `apiName` or one of `deprecatedApiNames` for a period of time. After the deprecation period, the dimension will be available only by `apiName`. */
		deprecatedApiNames: Option[List[String]] = None,
	  /** True if the dimension is custom to this property. This includes user, event, & item scoped custom dimensions; to learn more about custom dimensions, see https://support.google.com/analytics/answer/14240153. This also include custom channel groups; to learn more about custom channel groups, see https://support.google.com/analytics/answer/13051316. */
		customDefinition: Option[Boolean] = None,
	  /** The display name of the category that this dimension belongs to. Similar dimensions and metrics are categorized together. */
		category: Option[String] = None
	)
	
	object MetricMetadata {
		enum TypeEnum extends Enum[TypeEnum] { case METRIC_TYPE_UNSPECIFIED, TYPE_INTEGER, TYPE_FLOAT, TYPE_SECONDS, TYPE_MILLISECONDS, TYPE_MINUTES, TYPE_HOURS, TYPE_STANDARD, TYPE_CURRENCY, TYPE_FEET, TYPE_MILES, TYPE_METERS, TYPE_KILOMETERS }
		enum BlockedReasonsEnum extends Enum[BlockedReasonsEnum] { case BLOCKED_REASON_UNSPECIFIED, NO_REVENUE_METRICS, NO_COST_METRICS }
	}
	case class MetricMetadata(
	  /** A metric name. Useable in [Metric](#Metric)'s `name`. For example, `eventCount`. */
		apiName: Option[String] = None,
	  /** This metric's name within the Google Analytics user interface. For example, `Event count`. */
		uiName: Option[String] = None,
	  /** Description of how this metric is used and calculated. */
		description: Option[String] = None,
	  /** Still usable but deprecated names for this metric. If populated, this metric is available by either `apiName` or one of `deprecatedApiNames` for a period of time. After the deprecation period, the metric will be available only by `apiName`. */
		deprecatedApiNames: Option[List[String]] = None,
	  /** The type of this metric. */
		`type`: Option[Schema.MetricMetadata.TypeEnum] = None,
	  /** The mathematical expression for this derived metric. Can be used in [Metric](#Metric)'s `expression` field for equivalent reports. Most metrics are not expressions, and for non-expressions, this field is empty. */
		expression: Option[String] = None,
	  /** True if the metric is a custom metric for this property. */
		customDefinition: Option[Boolean] = None,
	  /** If reasons are specified, your access is blocked to this metric for this property. API requests from you to this property for this metric will succeed; however, the report will contain only zeros for this metric. API requests with metric filters on blocked metrics will fail. If reasons are empty, you have access to this metric. To learn more, see [Access and data-restriction management](https://support.google.com/analytics/answer/10851388). */
		blockedReasons: Option[List[Schema.MetricMetadata.BlockedReasonsEnum]] = None,
	  /** The display name of the category that this metrics belongs to. Similar dimensions and metrics are categorized together. */
		category: Option[String] = None
	)
	
	case class ComparisonMetadata(
	  /** This comparison's resource name. Useable in [Comparison](#Comparison)'s `comparison` field. For example, 'comparisons/1234'. */
		apiName: Option[String] = None,
	  /** This comparison's name within the Google Analytics user interface. */
		uiName: Option[String] = None,
	  /** This comparison's description. */
		description: Option[String] = None
	)
	
	object RunRealtimeReportRequest {
		enum MetricAggregationsEnum extends Enum[MetricAggregationsEnum] { case METRIC_AGGREGATION_UNSPECIFIED, TOTAL, MINIMUM, MAXIMUM, COUNT }
	}
	case class RunRealtimeReportRequest(
	  /** The dimensions requested and displayed. */
		dimensions: Option[List[Schema.Dimension]] = None,
	  /** The metrics requested and displayed. */
		metrics: Option[List[Schema.Metric]] = None,
	  /** The filter clause of dimensions. Metrics cannot be used in this filter. */
		dimensionFilter: Option[Schema.FilterExpression] = None,
	  /** The filter clause of metrics. Applied at post aggregation phase, similar to SQL having-clause. Dimensions cannot be used in this filter. */
		metricFilter: Option[Schema.FilterExpression] = None,
	  /** The number of rows to return. If unspecified, 10,000 rows are returned. The API returns a maximum of 250,000 rows per request, no matter how many you ask for. `limit` must be positive. The API can also return fewer rows than the requested `limit`, if there aren't as many dimension values as the `limit`. For instance, there are fewer than 300 possible values for the dimension `country`, so when reporting on only `country`, you can't get more than 300 rows, even if you set `limit` to a higher value. */
		limit: Option[String] = None,
	  /** Aggregation of metrics. Aggregated metric values will be shown in rows where the dimension_values are set to "RESERVED_(MetricAggregation)". */
		metricAggregations: Option[List[Schema.RunRealtimeReportRequest.MetricAggregationsEnum]] = None,
	  /** Specifies how rows are ordered in the response. */
		orderBys: Option[List[Schema.OrderBy]] = None,
	  /** Toggles whether to return the current state of this Google Analytics property's Realtime quota. Quota is returned in [PropertyQuota](#PropertyQuota). */
		returnPropertyQuota: Option[Boolean] = None,
	  /** The minute ranges of event data to read. If unspecified, one minute range for the last 30 minutes will be used. If multiple minute ranges are requested, each response row will contain a zero based minute range index. If two minute ranges overlap, the event data for the overlapping minutes is included in the response rows for both minute ranges. */
		minuteRanges: Option[List[Schema.MinuteRange]] = None
	)
	
	case class MinuteRange(
	  /** The inclusive start minute for the query as a number of minutes before now. For example, `"startMinutesAgo": 29` specifies the report should include event data from 29 minutes ago and after. Cannot be after `endMinutesAgo`. If unspecified, `startMinutesAgo` is defaulted to 29. Standard Analytics properties can request up to the last 30 minutes of event data (`startMinutesAgo <= 29`), and 360 Analytics properties can request up to the last 60 minutes of event data (`startMinutesAgo <= 59`). */
		startMinutesAgo: Option[Int] = None,
	  /** The inclusive end minute for the query as a number of minutes before now. Cannot be before `startMinutesAgo`. For example, `"endMinutesAgo": 15` specifies the report should include event data from prior to 15 minutes ago. If unspecified, `endMinutesAgo` is defaulted to 0. Standard Analytics properties can request any minute in the last 30 minutes of event data (`endMinutesAgo <= 29`), and 360 Analytics properties can request any minute in the last 60 minutes of event data (`endMinutesAgo <= 59`). */
		endMinutesAgo: Option[Int] = None,
	  /** Assigns a name to this minute range. The dimension `dateRange` is valued to this name in a report response. If set, cannot begin with `date_range_` or `RESERVED_`. If not set, minute ranges are named by their zero based index in the request: `date_range_0`, `date_range_1`, etc. */
		name: Option[String] = None
	)
	
	case class RunRealtimeReportResponse(
	  /** Describes dimension columns. The number of DimensionHeaders and ordering of DimensionHeaders matches the dimensions present in rows. */
		dimensionHeaders: Option[List[Schema.DimensionHeader]] = None,
	  /** Describes metric columns. The number of MetricHeaders and ordering of MetricHeaders matches the metrics present in rows. */
		metricHeaders: Option[List[Schema.MetricHeader]] = None,
	  /** Rows of dimension value combinations and metric values in the report. */
		rows: Option[List[Schema.Row]] = None,
	  /** If requested, the totaled values of metrics. */
		totals: Option[List[Schema.Row]] = None,
	  /** If requested, the maximum values of metrics. */
		maximums: Option[List[Schema.Row]] = None,
	  /** If requested, the minimum values of metrics. */
		minimums: Option[List[Schema.Row]] = None,
	  /** The total number of rows in the query result. `rowCount` is independent of the number of rows returned in the response and the `limit` request parameter. For example if a query returns 175 rows and includes `limit` of 50 in the API request, the response will contain `rowCount` of 175 but only 50 rows. */
		rowCount: Option[Int] = None,
	  /** This Google Analytics property's Realtime quota state including this request. */
		propertyQuota: Option[Schema.PropertyQuota] = None,
	  /** Identifies what kind of resource this message is. This `kind` is always the fixed string "analyticsData#runRealtimeReport". Useful to distinguish between response types in JSON. */
		kind: Option[String] = None
	)
	
	object CheckCompatibilityRequest {
		enum CompatibilityFilterEnum extends Enum[CompatibilityFilterEnum] { case COMPATIBILITY_UNSPECIFIED, COMPATIBLE, INCOMPATIBLE }
	}
	case class CheckCompatibilityRequest(
	  /** The dimensions in this report. `dimensions` should be the same value as in your `runReport` request. */
		dimensions: Option[List[Schema.Dimension]] = None,
	  /** The metrics in this report. `metrics` should be the same value as in your `runReport` request. */
		metrics: Option[List[Schema.Metric]] = None,
	  /** The filter clause of dimensions. `dimensionFilter` should be the same value as in your `runReport` request. */
		dimensionFilter: Option[Schema.FilterExpression] = None,
	  /** The filter clause of metrics. `metricFilter` should be the same value as in your `runReport` request */
		metricFilter: Option[Schema.FilterExpression] = None,
	  /** Filters the dimensions and metrics in the response to just this compatibility. Commonly used as `”compatibilityFilter”: “COMPATIBLE”` to only return compatible dimensions & metrics. */
		compatibilityFilter: Option[Schema.CheckCompatibilityRequest.CompatibilityFilterEnum] = None
	)
	
	case class CheckCompatibilityResponse(
	  /** The compatibility of each dimension. */
		dimensionCompatibilities: Option[List[Schema.DimensionCompatibility]] = None,
	  /** The compatibility of each metric. */
		metricCompatibilities: Option[List[Schema.MetricCompatibility]] = None
	)
	
	object DimensionCompatibility {
		enum CompatibilityEnum extends Enum[CompatibilityEnum] { case COMPATIBILITY_UNSPECIFIED, COMPATIBLE, INCOMPATIBLE }
	}
	case class DimensionCompatibility(
	  /** The dimension metadata contains the API name for this compatibility information. The dimension metadata also contains other helpful information like the UI name and description. */
		dimensionMetadata: Option[Schema.DimensionMetadata] = None,
	  /** The compatibility of this dimension. If the compatibility is COMPATIBLE, this dimension can be successfully added to the report. */
		compatibility: Option[Schema.DimensionCompatibility.CompatibilityEnum] = None
	)
	
	object MetricCompatibility {
		enum CompatibilityEnum extends Enum[CompatibilityEnum] { case COMPATIBILITY_UNSPECIFIED, COMPATIBLE, INCOMPATIBLE }
	}
	case class MetricCompatibility(
	  /** The metric metadata contains the API name for this compatibility information. The metric metadata also contains other helpful information like the UI name and description. */
		metricMetadata: Option[Schema.MetricMetadata] = None,
	  /** The compatibility of this metric. If the compatibility is COMPATIBLE, this metric can be successfully added to the report. */
		compatibility: Option[Schema.MetricCompatibility.CompatibilityEnum] = None
	)
	
	object AudienceExport {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, FAILED }
	}
	case class AudienceExport(
	  /** Output only. Identifier. The audience export resource name assigned during creation. This resource name identifies this `AudienceExport`. Format: `properties/{property}/audienceExports/{audience_export}` */
		name: Option[String] = None,
	  /** Required. The audience resource name. This resource name identifies the audience being listed and is shared between the Analytics Data & Admin APIs. Format: `properties/{property}/audiences/{audience}` */
		audience: Option[String] = None,
	  /** Output only. The descriptive display name for this audience. For example, "Purchasers". */
		audienceDisplayName: Option[String] = None,
	  /** Required. The dimensions requested and displayed in the query response. */
		dimensions: Option[List[Schema.V1betaAudienceDimension]] = None,
	  /** Output only. The current state for this AudienceExport. */
		state: Option[Schema.AudienceExport.StateEnum] = None,
	  /** Output only. The time when CreateAudienceExport was called and the AudienceExport began the `CREATING` state. */
		beginCreatingTime: Option[String] = None,
	  /** Output only. The total quota tokens charged during creation of the AudienceExport. Because this token count is based on activity from the `CREATING` state, this tokens charged will be fixed once an AudienceExport enters the `ACTIVE` or `FAILED` states. */
		creationQuotaTokensCharged: Option[Int] = None,
	  /** Output only. The total number of rows in the AudienceExport result. */
		rowCount: Option[Int] = None,
	  /** Output only. Error message is populated when an audience export fails during creation. A common reason for such a failure is quota exhaustion. */
		errorMessage: Option[String] = None,
	  /** Output only. The percentage completed for this audience export ranging between 0 to 100. */
		percentageCompleted: Option[BigDecimal] = None
	)
	
	case class V1betaAudienceDimension(
	  /** Optional. The API name of the dimension. See the [API Dimensions](https://developers.google.com/analytics/devguides/reporting/data/v1/audience-list-api-schema#dimensions) for the list of dimension names. */
		dimensionName: Option[String] = None
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
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class QueryAudienceExportRequest(
	  /** Optional. The row count of the start row. The first row is counted as row 0. When paging, the first request does not specify offset; or equivalently, sets offset to 0; the first request returns the first `limit` of rows. The second request sets offset to the `limit` of the first request; the second request returns the second `limit` of rows. To learn more about this pagination parameter, see [Pagination](https://developers.google.com/analytics/devguides/reporting/data/v1/basics#pagination). */
		offset: Option[String] = None,
	  /** Optional. The number of rows to return. If unspecified, 10,000 rows are returned. The API returns a maximum of 250,000 rows per request, no matter how many you ask for. `limit` must be positive. The API can also return fewer rows than the requested `limit`, if there aren't as many dimension values as the `limit`. To learn more about this pagination parameter, see [Pagination](https://developers.google.com/analytics/devguides/reporting/data/v1/basics#pagination). */
		limit: Option[String] = None
	)
	
	case class QueryAudienceExportResponse(
	  /** Configuration data about AudienceExport being queried. Returned to help interpret the audience rows in this response. For example, the dimensions in this AudienceExport correspond to the columns in the AudienceRows. */
		audienceExport: Option[Schema.AudienceExport] = None,
	  /** Rows for each user in an audience export. The number of rows in this response will be less than or equal to request's page size. */
		audienceRows: Option[List[Schema.V1betaAudienceRow]] = None,
	  /** The total number of rows in the AudienceExport result. `rowCount` is independent of the number of rows returned in the response, the `limit` request parameter, and the `offset` request parameter. For example if a query returns 175 rows and includes `limit` of 50 in the API request, the response will contain `rowCount` of 175 but only 50 rows. To learn more about this pagination parameter, see [Pagination](https://developers.google.com/analytics/devguides/reporting/data/v1/basics#pagination). */
		rowCount: Option[Int] = None
	)
	
	case class V1betaAudienceRow(
	  /** Each dimension value attribute for an audience user. One dimension value will be added for each dimension column requested. */
		dimensionValues: Option[List[Schema.V1betaAudienceDimensionValue]] = None
	)
	
	case class V1betaAudienceDimensionValue(
	  /** Value as a string if the dimension type is a string. */
		value: Option[String] = None
	)
	
	case class ListAudienceExportsResponse(
	  /** Each audience export for a property. */
		audienceExports: Option[List[Schema.AudienceExport]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class AudienceListMetadata(
	
	)
}
