package com.boresjo.play.api.google.chromeuxreport

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtQueryRequest: Format[Schema.QueryRequest] = Json.format[Schema.QueryRequest]
	given fmtQueryRequestFormFactorEnum: Format[Schema.QueryRequest.FormFactorEnum] = JsonEnumFormat[Schema.QueryRequest.FormFactorEnum]
	given fmtQueryResponse: Format[Schema.QueryResponse] = Json.format[Schema.QueryResponse]
	given fmtRecord: Format[Schema.Record] = Json.format[Schema.Record]
	given fmtUrlNormalization: Format[Schema.UrlNormalization] = Json.format[Schema.UrlNormalization]
	given fmtKey: Format[Schema.Key] = Json.format[Schema.Key]
	given fmtMetric: Format[Schema.Metric] = Json.format[Schema.Metric]
	given fmtCollectionPeriod: Format[Schema.CollectionPeriod] = Json.format[Schema.CollectionPeriod]
	given fmtKeyFormFactorEnum: Format[Schema.Key.FormFactorEnum] = JsonEnumFormat[Schema.Key.FormFactorEnum]
	given fmtBin: Format[Schema.Bin] = Json.format[Schema.Bin]
	given fmtPercentiles: Format[Schema.Percentiles] = Json.format[Schema.Percentiles]
	given fmtDate: Format[Schema.Date] = Json.format[Schema.Date]
	given fmtQueryHistoryRequest: Format[Schema.QueryHistoryRequest] = Json.format[Schema.QueryHistoryRequest]
	given fmtQueryHistoryRequestFormFactorEnum: Format[Schema.QueryHistoryRequest.FormFactorEnum] = JsonEnumFormat[Schema.QueryHistoryRequest.FormFactorEnum]
	given fmtQueryHistoryResponse: Format[Schema.QueryHistoryResponse] = Json.format[Schema.QueryHistoryResponse]
	given fmtHistoryRecord: Format[Schema.HistoryRecord] = Json.format[Schema.HistoryRecord]
	given fmtHistoryKey: Format[Schema.HistoryKey] = Json.format[Schema.HistoryKey]
	given fmtMetricTimeseries: Format[Schema.MetricTimeseries] = Json.format[Schema.MetricTimeseries]
	given fmtHistoryKeyFormFactorEnum: Format[Schema.HistoryKey.FormFactorEnum] = JsonEnumFormat[Schema.HistoryKey.FormFactorEnum]
	given fmtTimeseriesBin: Format[Schema.TimeseriesBin] = Json.format[Schema.TimeseriesBin]
	given fmtTimeseriesPercentiles: Format[Schema.TimeseriesPercentiles] = Json.format[Schema.TimeseriesPercentiles]
	given fmtFractionTimeseries: Format[Schema.FractionTimeseries] = Json.format[Schema.FractionTimeseries]
}
