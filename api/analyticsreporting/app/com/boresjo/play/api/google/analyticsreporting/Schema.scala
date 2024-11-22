package com.boresjo.play.api.google.analyticsreporting

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GetReportsRequest(
	  /** Requests, each request will have a separate response. There can be a maximum of 5 requests. All requests should have the same `dateRanges`, `viewId`, `segments`, `samplingLevel`, and `cohortGroup`. */
		reportRequests: Option[List[Schema.ReportRequest]] = None,
	  /** Enables [resource based quotas](/analytics/devguides/reporting/core/v4/limits-quotas#analytics_reporting_api_v4), (defaults to `False`). If this field is set to `True` the per view (profile) quotas are governed by the computational cost of the request. Note that using cost based quotas will higher enable sampling rates. (10 Million for `SMALL`, 100M for `LARGE`. See the [limits and quotas documentation](/analytics/devguides/reporting/core/v4/limits-quotas#analytics_reporting_api_v4) for details. */
		useResourceQuotas: Option[Boolean] = None
	)
	
	object ReportRequest {
		enum SamplingLevelEnum extends Enum[SamplingLevelEnum] { case SAMPLING_UNSPECIFIED, DEFAULT, SMALL, LARGE }
	}
	case class ReportRequest(
	  /** The Analytics [view ID](https://support.google.com/analytics/answer/1009618) from which to retrieve data. Every [ReportRequest](#ReportRequest) within a `batchGet` method must contain the same `viewId`. */
		viewId: Option[String] = None,
	  /** Date ranges in the request. The request can have a maximum of 2 date ranges. The response will contain a set of metric values for each combination of the dimensions for each date range in the request. So, if there are two date ranges, there will be two set of metric values, one for the original date range and one for the second date range. The `reportRequest.dateRanges` field should not be specified for cohorts or Lifetime value requests. If a date range is not provided, the default date range is (startDate: current date - 7 days, endDate: current date - 1 day). Every [ReportRequest](#ReportRequest) within a `batchGet` method must contain the same `dateRanges` definition. */
		dateRanges: Option[List[Schema.DateRange]] = None,
	  /** The desired report [sample](https://support.google.com/analytics/answer/2637192) size. If the the `samplingLevel` field is unspecified the `DEFAULT` sampling level is used. Every [ReportRequest](#ReportRequest) within a `batchGet` method must contain the same `samplingLevel` definition. See [developer guide](/analytics/devguides/reporting/core/v4/basics#sampling) for details. */
		samplingLevel: Option[Schema.ReportRequest.SamplingLevelEnum] = None,
	  /** The dimensions requested. Requests can have a total of 9 dimensions. */
		dimensions: Option[List[Schema.Dimension]] = None,
	  /** The dimension filter clauses for filtering Dimension Values. They are logically combined with the `AND` operator. Note that filtering occurs before any dimensions are aggregated, so that the returned metrics represent the total for only the relevant dimensions. */
		dimensionFilterClauses: Option[List[Schema.DimensionFilterClause]] = None,
	  /** The metrics requested. Requests must specify at least one metric. Requests can have a total of 10 metrics. */
		metrics: Option[List[Schema.Metric]] = None,
	  /** The metric filter clauses. They are logically combined with the `AND` operator. Metric filters look at only the first date range and not the comparing date range. Note that filtering on metrics occurs after the metrics are aggregated. */
		metricFilterClauses: Option[List[Schema.MetricFilterClause]] = None,
	  /** Dimension or metric filters that restrict the data returned for your request. To use the `filtersExpression`, supply a dimension or metric on which to filter, followed by the filter expression. For example, the following expression selects `ga:browser` dimension which starts with Firefox; `ga:browser=~^Firefox`. For more information on dimensions and metric filters, see [Filters reference](https://developers.google.com/analytics/devguides/reporting/core/v3/reference#filters). */
		filtersExpression: Option[String] = None,
	  /** Sort order on output rows. To compare two rows, the elements of the following are applied in order until a difference is found. All date ranges in the output get the same row order. */
		orderBys: Option[List[Schema.OrderBy]] = None,
	  /** Segment the data returned for the request. A segment definition helps look at a subset of the segment request. A request can contain up to four segments. Every [ReportRequest](#ReportRequest) within a `batchGet` method must contain the same `segments` definition. Requests with segments must have the `ga:segment` dimension. */
		segments: Option[List[Schema.Segment]] = None,
	  /** The pivot definitions. Requests can have a maximum of 2 pivots. */
		pivots: Option[List[Schema.Pivot]] = None,
	  /** Cohort group associated with this request. If there is a cohort group in the request the `ga:cohort` dimension must be present. Every [ReportRequest](#ReportRequest) within a `batchGet` method must contain the same `cohortGroup` definition. */
		cohortGroup: Option[Schema.CohortGroup] = None,
	  /** A continuation token to get the next page of the results. Adding this to the request will return the rows after the pageToken. The pageToken should be the value returned in the nextPageToken parameter in the response to the GetReports request. */
		pageToken: Option[String] = None,
	  /** Page size is for paging and specifies the maximum number of returned rows. Page size should be >= 0. A query returns the default of 1,000 rows. The Analytics Core Reporting API returns a maximum of 100,000 rows per request, no matter how many you ask for. It can also return fewer rows than requested, if there aren't as many dimension segments as you expect. For instance, there are fewer than 300 possible values for `ga:country`, so when segmenting only by country, you can't get more than 300 rows, even if you set `pageSize` to a higher value. */
		pageSize: Option[Int] = None,
	  /** If set to false, the response does not include rows if all the retrieved metrics are equal to zero. The default is false which will exclude these rows. */
		includeEmptyRows: Option[Boolean] = None,
	  /** If set to true, hides the total of all metrics for all the matching rows, for every date range. The default false and will return the totals. */
		hideTotals: Option[Boolean] = None,
	  /** If set to true, hides the minimum and maximum across all matching rows. The default is false and the value ranges are returned. */
		hideValueRanges: Option[Boolean] = None
	)
	
	case class DateRange(
	  /** The start date for the query in the format `YYYY-MM-DD`. */
		startDate: Option[String] = None,
	  /** The end date for the query in the format `YYYY-MM-DD`. */
		endDate: Option[String] = None
	)
	
	case class Dimension(
	  /** Name of the dimension to fetch, for example `ga:browser`. */
		name: Option[String] = None,
	  /** If non-empty, we place dimension values into buckets after string to int64. Dimension values that are not the string representation of an integral value will be converted to zero. The bucket values have to be in increasing order. Each bucket is closed on the lower end, and open on the upper end. The "first" bucket includes all values less than the first boundary, the "last" bucket includes all values up to infinity. Dimension values that fall in a bucket get transformed to a new dimension value. For example, if one gives a list of "0, 1, 3, 4, 7", then we return the following buckets: - bucket #1: values < 0, dimension value "<0" - bucket #2: values in [0,1), dimension value "0" - bucket #3: values in [1,3), dimension value "1-2" - bucket #4: values in [3,4), dimension value "3" - bucket #5: values in [4,7), dimension value "4-6" - bucket #6: values >= 7, dimension value "7+" NOTE: If you are applying histogram mutation on any dimension, and using that dimension in sort, you will want to use the sort type `HISTOGRAM_BUCKET` for that purpose. Without that the dimension values will be sorted according to dictionary (lexicographic) order. For example the ascending dictionary order is: "<50", "1001+", "121-1000", "50-120" And the ascending `HISTOGRAM_BUCKET` order is: "<50", "50-120", "121-1000", "1001+" The client has to explicitly request `"orderType": "HISTOGRAM_BUCKET"` for a histogram-mutated dimension. */
		histogramBuckets: Option[List[String]] = None
	)
	
	object DimensionFilterClause {
		enum OperatorEnum extends Enum[OperatorEnum] { case OPERATOR_UNSPECIFIED, OR, AND }
	}
	case class DimensionFilterClause(
	  /** The operator for combining multiple dimension filters. If unspecified, it is treated as an `OR`. */
		operator: Option[Schema.DimensionFilterClause.OperatorEnum] = None,
	  /** The repeated set of filters. They are logically combined based on the operator specified. */
		filters: Option[List[Schema.DimensionFilter]] = None
	)
	
	object DimensionFilter {
		enum OperatorEnum extends Enum[OperatorEnum] { case OPERATOR_UNSPECIFIED, REGEXP, BEGINS_WITH, ENDS_WITH, PARTIAL, EXACT, NUMERIC_EQUAL, NUMERIC_GREATER_THAN, NUMERIC_LESS_THAN, IN_LIST }
	}
	case class DimensionFilter(
	  /** The dimension to filter on. A DimensionFilter must contain a dimension. */
		dimensionName: Option[String] = None,
	  /** Logical `NOT` operator. If this boolean is set to true, then the matching dimension values will be excluded in the report. The default is false. */
		not: Option[Boolean] = None,
	  /** How to match the dimension to the expression. The default is REGEXP. */
		operator: Option[Schema.DimensionFilter.OperatorEnum] = None,
	  /** Strings or regular expression to match against. Only the first value of the list is used for comparison unless the operator is `IN_LIST`. If `IN_LIST` operator, then the entire list is used to filter the dimensions as explained in the description of the `IN_LIST` operator. */
		expressions: Option[List[String]] = None,
	  /** Should the match be case sensitive? Default is false. */
		caseSensitive: Option[Boolean] = None
	)
	
	object Metric {
		enum FormattingTypeEnum extends Enum[FormattingTypeEnum] { case METRIC_TYPE_UNSPECIFIED, INTEGER, FLOAT, CURRENCY, PERCENT, TIME }
	}
	case class Metric(
	  /** A metric expression in the request. An expression is constructed from one or more metrics and numbers. Accepted operators include: Plus (+), Minus (-), Negation (Unary -), Divided by (/), Multiplied by (&#42;), Parenthesis, Positive cardinal numbers (0-9), can include decimals and is limited to 1024 characters. Example `ga:totalRefunds/ga:users`, in most cases the metric expression is just a single metric name like `ga:users`. Adding mixed `MetricType` (E.g., `CURRENCY` + `PERCENTAGE`) metrics will result in unexpected results. */
		expression: Option[String] = None,
	  /** An alias for the metric expression is an alternate name for the expression. The alias can be used for filtering and sorting. This field is optional and is useful if the expression is not a single metric but a complex expression which cannot be used in filtering and sorting. The alias is also used in the response column header. */
		alias: Option[String] = None,
	  /** Specifies how the metric expression should be formatted, for example `INTEGER`. */
		formattingType: Option[Schema.Metric.FormattingTypeEnum] = None
	)
	
	object MetricFilterClause {
		enum OperatorEnum extends Enum[OperatorEnum] { case OPERATOR_UNSPECIFIED, OR, AND }
	}
	case class MetricFilterClause(
	  /** The operator for combining multiple metric filters. If unspecified, it is treated as an `OR`. */
		operator: Option[Schema.MetricFilterClause.OperatorEnum] = None,
	  /** The repeated set of filters. They are logically combined based on the operator specified. */
		filters: Option[List[Schema.MetricFilter]] = None
	)
	
	object MetricFilter {
		enum OperatorEnum extends Enum[OperatorEnum] { case OPERATOR_UNSPECIFIED, EQUAL, LESS_THAN, GREATER_THAN, IS_MISSING }
	}
	case class MetricFilter(
	  /** The metric that will be filtered on. A metricFilter must contain a metric name. A metric name can be an alias earlier defined as a metric or it can also be a metric expression. */
		metricName: Option[String] = None,
	  /** Logical `NOT` operator. If this boolean is set to true, then the matching metric values will be excluded in the report. The default is false. */
		not: Option[Boolean] = None,
	  /** Is the metric `EQUAL`, `LESS_THAN` or `GREATER_THAN` the comparisonValue, the default is `EQUAL`. If the operator is `IS_MISSING`, checks if the metric is missing and would ignore the comparisonValue. */
		operator: Option[Schema.MetricFilter.OperatorEnum] = None,
	  /** The value to compare against. */
		comparisonValue: Option[String] = None
	)
	
	object OrderBy {
		enum OrderTypeEnum extends Enum[OrderTypeEnum] { case ORDER_TYPE_UNSPECIFIED, VALUE, DELTA, SMART, HISTOGRAM_BUCKET, DIMENSION_AS_INTEGER }
		enum SortOrderEnum extends Enum[SortOrderEnum] { case SORT_ORDER_UNSPECIFIED, ASCENDING, DESCENDING }
	}
	case class OrderBy(
	  /** The field which to sort by. The default sort order is ascending. Example: `ga:browser`. Note, that you can only specify one field for sort here. For example, `ga:browser, ga:city` is not valid. */
		fieldName: Option[String] = None,
	  /** The order type. The default orderType is `VALUE`. */
		orderType: Option[Schema.OrderBy.OrderTypeEnum] = None,
	  /** The sorting order for the field. */
		sortOrder: Option[Schema.OrderBy.SortOrderEnum] = None
	)
	
	case class Segment(
	  /** A dynamic segment definition in the request. */
		dynamicSegment: Option[Schema.DynamicSegment] = None,
	  /** The segment ID of a built-in or custom segment, for example `gaid::-3`. */
		segmentId: Option[String] = None
	)
	
	case class DynamicSegment(
	  /** The name of the dynamic segment. */
		name: Option[String] = None,
	  /** User Segment to select users to include in the segment. */
		userSegment: Option[Schema.SegmentDefinition] = None,
	  /** Session Segment to select sessions to include in the segment. */
		sessionSegment: Option[Schema.SegmentDefinition] = None
	)
	
	case class SegmentDefinition(
	  /** A segment is defined by a set of segment filters which are combined together with a logical `AND` operation. */
		segmentFilters: Option[List[Schema.SegmentFilter]] = None
	)
	
	case class SegmentFilter(
	  /** A Simple segment conditions consist of one or more dimension/metric conditions that can be combined */
		simpleSegment: Option[Schema.SimpleSegment] = None,
	  /** Sequence conditions consist of one or more steps, where each step is defined by one or more dimension/metric conditions. Multiple steps can be combined with special sequence operators. */
		sequenceSegment: Option[Schema.SequenceSegment] = None,
	  /** If true, match the complement of simple or sequence segment. For example, to match all visits not from "New York", we can define the segment as follows: "sessionSegment": { "segmentFilters": [{ "simpleSegment" :{ "orFiltersForSegment": [{ "segmentFilterClauses":[{ "dimensionFilter": { "dimensionName": "ga:city", "expressions": ["New York"] } }] }] }, "not": "True" }] }, */
		not: Option[Boolean] = None
	)
	
	case class SimpleSegment(
	  /** A list of segment filters groups which are combined with logical `AND` operator. */
		orFiltersForSegment: Option[List[Schema.OrFiltersForSegment]] = None
	)
	
	case class OrFiltersForSegment(
	  /** List of segment filters to be combined with a `OR` operator. */
		segmentFilterClauses: Option[List[Schema.SegmentFilterClause]] = None
	)
	
	case class SegmentFilterClause(
	  /** Dimension Filter for the segment definition. */
		dimensionFilter: Option[Schema.SegmentDimensionFilter] = None,
	  /** Metric Filter for the segment definition. */
		metricFilter: Option[Schema.SegmentMetricFilter] = None,
	  /** Matches the complement (`!`) of the filter. */
		not: Option[Boolean] = None
	)
	
	object SegmentDimensionFilter {
		enum OperatorEnum extends Enum[OperatorEnum] { case OPERATOR_UNSPECIFIED, REGEXP, BEGINS_WITH, ENDS_WITH, PARTIAL, EXACT, IN_LIST, NUMERIC_LESS_THAN, NUMERIC_GREATER_THAN, NUMERIC_BETWEEN }
	}
	case class SegmentDimensionFilter(
	  /** Name of the dimension for which the filter is being applied. */
		dimensionName: Option[String] = None,
	  /** The operator to use to match the dimension with the expressions. */
		operator: Option[Schema.SegmentDimensionFilter.OperatorEnum] = None,
	  /** Should the match be case sensitive, ignored for `IN_LIST` operator. */
		caseSensitive: Option[Boolean] = None,
	  /** The list of expressions, only the first element is used for all operators */
		expressions: Option[List[String]] = None,
	  /** Minimum comparison values for `BETWEEN` match type. */
		minComparisonValue: Option[String] = None,
	  /** Maximum comparison values for `BETWEEN` match type. */
		maxComparisonValue: Option[String] = None
	)
	
	object SegmentMetricFilter {
		enum ScopeEnum extends Enum[ScopeEnum] { case UNSPECIFIED_SCOPE, PRODUCT, HIT, SESSION, USER }
		enum OperatorEnum extends Enum[OperatorEnum] { case UNSPECIFIED_OPERATOR, LESS_THAN, GREATER_THAN, EQUAL, BETWEEN }
	}
	case class SegmentMetricFilter(
	  /** Scope for a metric defines the level at which that metric is defined. The specified metric scope must be equal to or greater than its primary scope as defined in the data model. The primary scope is defined by if the segment is selecting users or sessions. */
		scope: Option[Schema.SegmentMetricFilter.ScopeEnum] = None,
	  /** The metric that will be filtered on. A `metricFilter` must contain a metric name. */
		metricName: Option[String] = None,
	  /** Specifies is the operation to perform to compare the metric. The default is `EQUAL`. */
		operator: Option[Schema.SegmentMetricFilter.OperatorEnum] = None,
	  /** The value to compare against. If the operator is `BETWEEN`, this value is treated as minimum comparison value. */
		comparisonValue: Option[String] = None,
	  /** Max comparison value is only used for `BETWEEN` operator. */
		maxComparisonValue: Option[String] = None
	)
	
	case class SequenceSegment(
	  /** The list of steps in the sequence. */
		segmentSequenceSteps: Option[List[Schema.SegmentSequenceStep]] = None,
	  /** If set, first step condition must match the first hit of the visitor (in the date range). */
		firstStepShouldMatchFirstHit: Option[Boolean] = None
	)
	
	object SegmentSequenceStep {
		enum MatchTypeEnum extends Enum[MatchTypeEnum] { case UNSPECIFIED_MATCH_TYPE, PRECEDES, IMMEDIATELY_PRECEDES }
	}
	case class SegmentSequenceStep(
	  /** A sequence is specified with a list of Or grouped filters which are combined with `AND` operator. */
		orFiltersForSegment: Option[List[Schema.OrFiltersForSegment]] = None,
	  /** Specifies if the step immediately precedes or can be any time before the next step. */
		matchType: Option[Schema.SegmentSequenceStep.MatchTypeEnum] = None
	)
	
	case class Pivot(
	  /** A list of dimensions to show as pivot columns. A Pivot can have a maximum of 4 dimensions. Pivot dimensions are part of the restriction on the total number of dimensions allowed in the request. */
		dimensions: Option[List[Schema.Dimension]] = None,
	  /** DimensionFilterClauses are logically combined with an `AND` operator: only data that is included by all these DimensionFilterClauses contributes to the values in this pivot region. Dimension filters can be used to restrict the columns shown in the pivot region. For example if you have `ga:browser` as the requested dimension in the pivot region, and you specify key filters to restrict `ga:browser` to only "IE" or "Firefox", then only those two browsers would show up as columns. */
		dimensionFilterClauses: Option[List[Schema.DimensionFilterClause]] = None,
	  /** The pivot metrics. Pivot metrics are part of the restriction on total number of metrics allowed in the request. */
		metrics: Option[List[Schema.Metric]] = None,
	  /** If k metrics were requested, then the response will contain some data-dependent multiple of k columns in the report. E.g., if you pivoted on the dimension `ga:browser` then you'd get k columns for "Firefox", k columns for "IE", k columns for "Chrome", etc. The ordering of the groups of columns is determined by descending order of "total" for the first of the k values. Ties are broken by lexicographic ordering of the first pivot dimension, then lexicographic ordering of the second pivot dimension, and so on. E.g., if the totals for the first value for Firefox, IE, and Chrome were 8, 2, 8, respectively, the order of columns would be Chrome, Firefox, IE. The following let you choose which of the groups of k columns are included in the response. */
		startGroup: Option[Int] = None,
	  /** Specifies the maximum number of groups to return. The default value is 10, also the maximum value is 1,000. */
		maxGroupCount: Option[Int] = None
	)
	
	case class CohortGroup(
	  /** The definition for the cohort. */
		cohorts: Option[List[Schema.Cohort]] = None,
	  /** Enable Life Time Value (LTV). LTV measures lifetime value for users acquired through different channels. Please see: [Cohort Analysis](https://support.google.com/analytics/answer/6074676) and [Lifetime Value](https://support.google.com/analytics/answer/6182550) If the value of lifetimeValue is false: - The metric values are similar to the values in the web interface cohort report. - The cohort definition date ranges must be aligned to the calendar week and month. i.e. while requesting `ga:cohortNthWeek` the `startDate` in the cohort definition should be a Sunday and the `endDate` should be the following Saturday, and for `ga:cohortNthMonth`, the `startDate` should be the 1st of the month and `endDate` should be the last day of the month. When the lifetimeValue is true: - The metric values will correspond to the values in the web interface LifeTime value report. - The Lifetime Value report shows you how user value (Revenue) and engagement (Appviews, Goal Completions, Sessions, and Session Duration) grow during the 90 days after a user is acquired. - The metrics are calculated as a cumulative average per user per the time increment. - The cohort definition date ranges need not be aligned to the calendar week and month boundaries. - The `viewId` must be an [app view ID](https://support.google.com/analytics/answer/2649553#WebVersusAppViews) */
		lifetimeValue: Option[Boolean] = None
	)
	
	object Cohort {
		enum TypeEnum extends Enum[TypeEnum] { case UNSPECIFIED_COHORT_TYPE, FIRST_VISIT_DATE }
	}
	case class Cohort(
	  /** A unique name for the cohort. If not defined name will be auto-generated with values cohort_[1234...]. */
		name: Option[String] = None,
	  /** Type of the cohort. The only supported type as of now is `FIRST_VISIT_DATE`. If this field is unspecified the cohort is treated as `FIRST_VISIT_DATE` type cohort. */
		`type`: Option[Schema.Cohort.TypeEnum] = None,
	  /** This is used for `FIRST_VISIT_DATE` cohort, the cohort selects users whose first visit date is between start date and end date defined in the DateRange. The date ranges should be aligned for cohort requests. If the request contains `ga:cohortNthDay` it should be exactly one day long, if `ga:cohortNthWeek` it should be aligned to the week boundary (starting at Sunday and ending Saturday), and for `ga:cohortNthMonth` the date range should be aligned to the month (starting at the first and ending on the last day of the month). For LTV requests there are no such restrictions. You do not need to supply a date range for the `reportsRequest.dateRanges` field. */
		dateRange: Option[Schema.DateRange] = None
	)
	
	case class GetReportsResponse(
	  /** Responses corresponding to each of the request. */
		reports: Option[List[Schema.Report]] = None,
	  /** The amount of resource quota tokens deducted to execute the query. Includes all responses. */
		queryCost: Option[Int] = None,
	  /** The amount of resource quota remaining for the property. */
		resourceQuotasRemaining: Option[Schema.ResourceQuotasRemaining] = None
	)
	
	case class Report(
	  /** The column headers. */
		columnHeader: Option[Schema.ColumnHeader] = None,
	  /** Response data. */
		data: Option[Schema.ReportData] = None,
	  /** Page token to retrieve the next page of results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class ColumnHeader(
	  /** The dimension names in the response. */
		dimensions: Option[List[String]] = None,
	  /** Metric headers for the metrics in the response. */
		metricHeader: Option[Schema.MetricHeader] = None
	)
	
	case class MetricHeader(
	  /** Headers for the metrics in the response. */
		metricHeaderEntries: Option[List[Schema.MetricHeaderEntry]] = None,
	  /** Headers for the pivots in the response. */
		pivotHeaders: Option[List[Schema.PivotHeader]] = None
	)
	
	object MetricHeaderEntry {
		enum TypeEnum extends Enum[TypeEnum] { case METRIC_TYPE_UNSPECIFIED, INTEGER, FLOAT, CURRENCY, PERCENT, TIME }
	}
	case class MetricHeaderEntry(
	  /** The name of the header. */
		name: Option[String] = None,
	  /** The type of the metric, for example `INTEGER`. */
		`type`: Option[Schema.MetricHeaderEntry.TypeEnum] = None
	)
	
	case class PivotHeader(
	  /** A single pivot section header. */
		pivotHeaderEntries: Option[List[Schema.PivotHeaderEntry]] = None,
	  /** The total number of groups for this pivot. */
		totalPivotGroupsCount: Option[Int] = None
	)
	
	case class PivotHeaderEntry(
	  /** The name of the dimensions in the pivot response. */
		dimensionNames: Option[List[String]] = None,
	  /** The values for the dimensions in the pivot. */
		dimensionValues: Option[List[String]] = None,
	  /** The metric header for the metric in the pivot. */
		metric: Option[Schema.MetricHeaderEntry] = None
	)
	
	case class ReportData(
	  /** There's one ReportRow for every unique combination of dimensions. */
		rows: Option[List[Schema.ReportRow]] = None,
	  /** For each requested date range, for the set of all rows that match the query, every requested value format gets a total. The total for a value format is computed by first totaling the metrics mentioned in the value format and then evaluating the value format as a scalar expression. E.g., The "totals" for `3 / (ga:sessions + 2)` we compute `3 / ((sum of all relevant ga:sessions) + 2)`. Totals are computed before pagination. */
		totals: Option[List[Schema.DateRangeValues]] = None,
	  /** Total number of matching rows for this query. */
		rowCount: Option[Int] = None,
	  /** Minimum and maximum values seen over all matching rows. These are both empty when `hideValueRanges` in the request is false, or when rowCount is zero. */
		minimums: Option[List[Schema.DateRangeValues]] = None,
	  /** Minimum and maximum values seen over all matching rows. These are both empty when `hideValueRanges` in the request is false, or when rowCount is zero. */
		maximums: Option[List[Schema.DateRangeValues]] = None,
	  /** If the results are [sampled](https://support.google.com/analytics/answer/2637192), this returns the total number of samples read, one entry per date range. If the results are not sampled this field will not be defined. See [developer guide](/analytics/devguides/reporting/core/v4/basics#sampling) for details. */
		samplesReadCounts: Option[List[String]] = None,
	  /** If the results are [sampled](https://support.google.com/analytics/answer/2637192), this returns the total number of samples present, one entry per date range. If the results are not sampled this field will not be defined. See [developer guide](/analytics/devguides/reporting/core/v4/basics#sampling) for details. */
		samplingSpaceSizes: Option[List[String]] = None,
	  /** Indicates if response to this request is golden or not. Data is golden when the exact same request will not produce any new results if asked at a later point in time. */
		isDataGolden: Option[Boolean] = None,
	  /** The last time the data in the report was refreshed. All the hits received before this timestamp are included in the calculation of the report. */
		dataLastRefreshed: Option[String] = None,
	  /** If empty reason is specified, the report is empty for this reason. */
		emptyReason: Option[String] = None
	)
	
	case class ReportRow(
	  /** List of requested dimensions. */
		dimensions: Option[List[String]] = None,
	  /** List of metrics for each requested DateRange. */
		metrics: Option[List[Schema.DateRangeValues]] = None
	)
	
	case class DateRangeValues(
	  /** Each value corresponds to each Metric in the request. */
		values: Option[List[String]] = None,
	  /** The values of each pivot region. */
		pivotValueRegions: Option[List[Schema.PivotValueRegion]] = None
	)
	
	case class PivotValueRegion(
	  /** The values of the metrics in each of the pivot regions. */
		values: Option[List[String]] = None
	)
	
	case class ResourceQuotasRemaining(
	  /** Daily resource quota remaining remaining. */
		dailyQuotaTokensRemaining: Option[Int] = None,
	  /** Hourly resource quota tokens remaining. */
		hourlyQuotaTokensRemaining: Option[Int] = None
	)
	
	object SearchUserActivityRequest {
		enum ActivityTypesEnum extends Enum[ActivityTypesEnum] { case ACTIVITY_TYPE_UNSPECIFIED, PAGEVIEW, SCREENVIEW, GOAL, ECOMMERCE, EVENT }
	}
	case class SearchUserActivityRequest(
	  /** Date range for which to retrieve the user activity. If a date range is not provided, the default date range is (startDate: current date - 7 days, endDate: current date - 1 day). */
		dateRange: Option[Schema.DateRange] = None,
	  /** Required. The Analytics [view ID](https://support.google.com/analytics/answer/1009618) from which to retrieve data. Every [SearchUserActivityRequest](#SearchUserActivityRequest) must contain the `viewId`. */
		viewId: Option[String] = None,
	  /** Required. Unique user Id to query for. Every [SearchUserActivityRequest](#SearchUserActivityRequest) must contain this field. */
		user: Option[Schema.User] = None,
	  /** Set of all activity types being requested. Only acvities matching these types will be returned in the response. If empty, all activies will be returned. */
		activityTypes: Option[List[Schema.SearchUserActivityRequest.ActivityTypesEnum]] = None,
	  /** Page size is for paging and specifies the maximum number of returned rows. Page size should be > 0. If the value is 0 or if the field isn't specified, the request returns the default of 1000 rows per page. */
		pageSize: Option[Int] = None,
	  /** A continuation token to get the next page of the results. Adding this to the request will return the rows after the pageToken. The pageToken should be the value returned in the nextPageToken parameter in the response to the [SearchUserActivityRequest](#SearchUserActivityRequest) request. */
		pageToken: Option[String] = None
	)
	
	object User {
		enum TypeEnum extends Enum[TypeEnum] { case USER_ID_TYPE_UNSPECIFIED, USER_ID, CLIENT_ID }
	}
	case class User(
	  /** Type of the user in the request. The field `userId` is associated with this type. */
		`type`: Option[Schema.User.TypeEnum] = None,
	  /** Unique Id of the user for which the data is being requested. */
		userId: Option[String] = None
	)
	
	case class SearchUserActivityResponse(
	  /** Each record represents a session (device details, duration, etc). */
		sessions: Option[List[Schema.UserActivitySession]] = None,
	  /** Total rows returned by this query (across different pages). */
		totalRows: Option[Int] = None,
	  /** This token should be passed to [SearchUserActivityRequest](#SearchUserActivityRequest) to retrieve the next page. */
		nextPageToken: Option[String] = None,
	  /** This field represents the [sampling rate](https://support.google.com/analytics/answer/2637192) for the given request and is a number between 0.0 to 1.0. See [developer guide](/analytics/devguides/reporting/core/v4/basics#sampling) for details. */
		sampleRate: Option[BigDecimal] = None
	)
	
	case class UserActivitySession(
	  /** Unique ID of the session. */
		sessionId: Option[String] = None,
	  /** The type of device used: "mobile", "tablet" etc. */
		deviceCategory: Option[String] = None,
	  /** Platform on which the activity happened: "android", "ios" etc. */
		platform: Option[String] = None,
	  /** The data source of a hit. By default, hits sent from analytics.js are reported as "web" and hits sent from the mobile SDKs are reported as "app". These values can be overridden in the Measurement Protocol. */
		dataSource: Option[String] = None,
	  /** Represents a detailed view into each of the activity in this session. */
		activities: Option[List[Schema.Activity]] = None,
	  /** Date of this session in ISO-8601 format. */
		sessionDate: Option[String] = None
	)
	
	object Activity {
		enum ActivityTypeEnum extends Enum[ActivityTypeEnum] { case ACTIVITY_TYPE_UNSPECIFIED, PAGEVIEW, SCREENVIEW, GOAL, ECOMMERCE, EVENT }
	}
	case class Activity(
	  /** Timestamp of the activity. If activities for a visit cross midnight and occur in two separate dates, then two sessions (one per date) share the session identifier. For example, say session ID 113472 has activity within 2019-08-20, and session ID 243742 has activity within 2019-08-25 and 2019-08-26. Session ID 113472 is one session, and session ID 243742 is two sessions. */
		activityTime: Option[String] = None,
	  /** The source of referrals. For manual campaign tracking, it is the value of the utm_source campaign tracking parameter. For AdWords autotagging, it is google. If you use neither, it is the domain of the source (e.g., document.referrer) referring the users. It may also contain a port address. If users arrived without a referrer, its value is (direct). */
		source: Option[String] = None,
	  /** The type of referrals. For manual campaign tracking, it is the value of the utm_medium campaign tracking parameter. For AdWords autotagging, it is cpc. If users came from a search engine detected by Google Analytics, it is organic. If the referrer is not a search engine, it is referral. If users came directly to the property and document.referrer is empty, its value is (none). */
		medium: Option[String] = None,
	  /** The Channel Group associated with an end user's session for this View (defined by the View's Channel Groupings). */
		channelGrouping: Option[String] = None,
	  /** For manual campaign tracking, it is the value of the utm_campaign campaign tracking parameter. For AdWords autotagging, it is the name(s) of the online ad campaign(s) you use for the property. If you use neither, its value is (not set). */
		campaign: Option[String] = None,
	  /** For manual campaign tracking, it is the value of the utm_term campaign tracking parameter. For AdWords traffic, it contains the best matching targeting criteria. For the display network, where multiple targeting criteria could have caused the ad to show up, it returns the best matching targeting criteria as selected by Ads. This could be display_keyword, site placement, boomuserlist, user_interest, age, or gender. Otherwise its value is (not set). */
		keyword: Option[String] = None,
	  /** The hostname from which the tracking request was made. */
		hostname: Option[String] = None,
	  /** The first page in users' sessions, or the landing page. */
		landingPagePath: Option[String] = None,
	  /** Type of this activity. */
		activityType: Option[Schema.Activity.ActivityTypeEnum] = None,
	  /** A list of all custom dimensions associated with this activity. */
		customDimension: Option[List[Schema.CustomDimension]] = None,
	  /** This will be set if `activity_type` equals `PAGEVIEW`. This field contains all the details about the visitor and the page that was visited. */
		pageview: Option[Schema.PageviewData] = None,
	  /** This will be set if `activity_type` equals `SCREEN_VIEW`. */
		appview: Option[Schema.ScreenviewData] = None,
	  /** This will be set if `activity_type` equals `ECOMMERCE`. */
		ecommerce: Option[Schema.EcommerceData] = None,
	  /** This field contains a list of all the goals that were reached in this activity when `activity_type` equals `GOAL`. */
		goals: Option[Schema.GoalSetData] = None,
	  /** This field contains all the details pertaining to an event and will be set if `activity_type` equals `EVENT`. */
		event: Option[Schema.EventData] = None
	)
	
	case class CustomDimension(
	  /** Slot number of custom dimension. */
		index: Option[Int] = None,
	  /** Value of the custom dimension. Default value (i.e. empty string) indicates clearing sesion/visitor scope custom dimension value. */
		value: Option[String] = None
	)
	
	case class PageviewData(
	  /** The URL of the page that the visitor viewed. */
		pagePath: Option[String] = None,
	  /** The title of the page that the visitor viewed. */
		pageTitle: Option[String] = None
	)
	
	case class ScreenviewData(
	  /** The name of the screen. */
		screenName: Option[String] = None,
	  /** Mobile manufacturer or branded name. Eg: "Google", "Apple" etc. */
		mobileDeviceBranding: Option[String] = None,
	  /** Mobile device model. Eg: "Pixel", "iPhone" etc. */
		mobileDeviceModel: Option[String] = None,
	  /** The application name. */
		appName: Option[String] = None
	)
	
	object EcommerceData {
		enum ActionTypeEnum extends Enum[ActionTypeEnum] { case UNKNOWN, CLICK, DETAILS_VIEW, ADD_TO_CART, REMOVE_FROM_CART, CHECKOUT, PAYMENT, REFUND, CHECKOUT_OPTION }
		enum EcommerceTypeEnum extends Enum[EcommerceTypeEnum] { case ECOMMERCE_TYPE_UNSPECIFIED, CLASSIC, ENHANCED }
	}
	case class EcommerceData(
	  /** Action associated with this e-commerce action. */
		actionType: Option[Schema.EcommerceData.ActionTypeEnum] = None,
	  /** Transaction details of this e-commerce action. */
		transaction: Option[Schema.TransactionData] = None,
	  /** Details of the products in this transaction. */
		products: Option[List[Schema.ProductData]] = None,
	  /** The type of this e-commerce activity. */
		ecommerceType: Option[Schema.EcommerceData.EcommerceTypeEnum] = None
	)
	
	case class TransactionData(
	  /** The transaction ID, supplied by the e-commerce tracking method, for the purchase in the shopping cart. */
		transactionId: Option[String] = None,
	  /** The total sale revenue (excluding shipping and tax) of the transaction. */
		transactionRevenue: Option[BigDecimal] = None,
	  /** Total tax for the transaction. */
		transactionTax: Option[BigDecimal] = None,
	  /** Total cost of shipping. */
		transactionShipping: Option[BigDecimal] = None
	)
	
	case class ProductData(
	  /** Unique code that represents the product. */
		productSku: Option[String] = None,
	  /** The product name, supplied by the e-commerce tracking application, for the purchased items. */
		productName: Option[String] = None,
	  /** The total revenue from purchased product items. */
		itemRevenue: Option[BigDecimal] = None,
	  /** Total number of this product units in the transaction. */
		productQuantity: Option[String] = None
	)
	
	case class GoalSetData(
	  /** All the goals that were reached in the current activity. */
		goals: Option[List[Schema.GoalData]] = None
	)
	
	case class GoalData(
	  /** This identifies the goal as configured for the profile. */
		goalIndex: Option[Int] = None,
	  /** Total number of goal completions in this activity. */
		goalCompletions: Option[String] = None,
	  /** Value in this goal. */
		goalValue: Option[BigDecimal] = None,
	  /** URL of the page where this goal was completed. */
		goalCompletionLocation: Option[String] = None,
	  /** URL of the page one step prior to the goal completion. */
		goalPreviousStep1: Option[String] = None,
	  /** URL of the page two steps prior to the goal completion. */
		goalPreviousStep2: Option[String] = None,
	  /** URL of the page three steps prior to the goal completion. */
		goalPreviousStep3: Option[String] = None,
	  /** Name of the goal. */
		goalName: Option[String] = None
	)
	
	case class EventData(
	  /** The object on the page that was interacted with. Eg: 'Video'. */
		eventCategory: Option[String] = None,
	  /** Type of interaction with the object. Eg: 'play'. */
		eventAction: Option[String] = None,
	  /** Label attached with the event. */
		eventLabel: Option[String] = None,
	  /** Numeric value associated with the event. */
		eventValue: Option[String] = None,
	  /** Number of such events in this activity. */
		eventCount: Option[String] = None
	)
}
