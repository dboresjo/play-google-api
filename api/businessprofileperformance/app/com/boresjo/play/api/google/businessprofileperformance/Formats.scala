package com.boresjo.play.api.google.businessprofileperformance

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGetDailyMetricsTimeSeriesResponse: Format[Schema.GetDailyMetricsTimeSeriesResponse] = Json.format[Schema.GetDailyMetricsTimeSeriesResponse]
	given fmtTimeSeries: Format[Schema.TimeSeries] = Json.format[Schema.TimeSeries]
	given fmtDatedValue: Format[Schema.DatedValue] = Json.format[Schema.DatedValue]
	given fmtDate: Format[Schema.Date] = Json.format[Schema.Date]
	given fmtFetchMultiDailyMetricsTimeSeriesResponse: Format[Schema.FetchMultiDailyMetricsTimeSeriesResponse] = Json.format[Schema.FetchMultiDailyMetricsTimeSeriesResponse]
	given fmtMultiDailyMetricTimeSeries: Format[Schema.MultiDailyMetricTimeSeries] = Json.format[Schema.MultiDailyMetricTimeSeries]
	given fmtDailyMetricTimeSeries: Format[Schema.DailyMetricTimeSeries] = Json.format[Schema.DailyMetricTimeSeries]
	given fmtDailyMetricTimeSeriesDailyMetricEnum: Format[Schema.DailyMetricTimeSeries.DailyMetricEnum] = JsonEnumFormat[Schema.DailyMetricTimeSeries.DailyMetricEnum]
	given fmtDailySubEntityType: Format[Schema.DailySubEntityType] = Json.format[Schema.DailySubEntityType]
	given fmtDailySubEntityTypeDayOfWeekEnum: Format[Schema.DailySubEntityType.DayOfWeekEnum] = JsonEnumFormat[Schema.DailySubEntityType.DayOfWeekEnum]
	given fmtTimeOfDay: Format[Schema.TimeOfDay] = Json.format[Schema.TimeOfDay]
	given fmtListSearchKeywordImpressionsMonthlyResponse: Format[Schema.ListSearchKeywordImpressionsMonthlyResponse] = Json.format[Schema.ListSearchKeywordImpressionsMonthlyResponse]
	given fmtSearchKeywordCount: Format[Schema.SearchKeywordCount] = Json.format[Schema.SearchKeywordCount]
	given fmtInsightsValue: Format[Schema.InsightsValue] = Json.format[Schema.InsightsValue]
}
