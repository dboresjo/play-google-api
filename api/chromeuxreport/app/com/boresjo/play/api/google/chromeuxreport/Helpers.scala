package com.boresjo.play.api.google.chromeuxreport

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaQueryRequestFormFactorEnum: Conversion[Schema.QueryRequest.FormFactorEnum, Option[Schema.QueryRequest.FormFactorEnum]] = (fun: Schema.QueryRequest.FormFactorEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaRecord: Conversion[Schema.Record, Option[Schema.Record]] = (fun: Schema.Record) => Option(fun)
		given putSchemaUrlNormalization: Conversion[Schema.UrlNormalization, Option[Schema.UrlNormalization]] = (fun: Schema.UrlNormalization) => Option(fun)
		given putSchemaKey: Conversion[Schema.Key, Option[Schema.Key]] = (fun: Schema.Key) => Option(fun)
		given putMapStringSchemaMetric: Conversion[Map[String, Schema.Metric], Option[Map[String, Schema.Metric]]] = (fun: Map[String, Schema.Metric]) => Option(fun)
		given putSchemaCollectionPeriod: Conversion[Schema.CollectionPeriod, Option[Schema.CollectionPeriod]] = (fun: Schema.CollectionPeriod) => Option(fun)
		given putSchemaKeyFormFactorEnum: Conversion[Schema.Key.FormFactorEnum, Option[Schema.Key.FormFactorEnum]] = (fun: Schema.Key.FormFactorEnum) => Option(fun)
		given putListSchemaBin: Conversion[List[Schema.Bin], Option[List[Schema.Bin]]] = (fun: List[Schema.Bin]) => Option(fun)
		given putSchemaPercentiles: Conversion[Schema.Percentiles, Option[Schema.Percentiles]] = (fun: Schema.Percentiles) => Option(fun)
		given putMapStringBigDecimal: Conversion[Map[String, BigDecimal], Option[Map[String, BigDecimal]]] = (fun: Map[String, BigDecimal]) => Option(fun)
		given putJsValue: Conversion[JsValue, Option[JsValue]] = (fun: JsValue) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaQueryHistoryRequestFormFactorEnum: Conversion[Schema.QueryHistoryRequest.FormFactorEnum, Option[Schema.QueryHistoryRequest.FormFactorEnum]] = (fun: Schema.QueryHistoryRequest.FormFactorEnum) => Option(fun)
		given putSchemaHistoryRecord: Conversion[Schema.HistoryRecord, Option[Schema.HistoryRecord]] = (fun: Schema.HistoryRecord) => Option(fun)
		given putSchemaHistoryKey: Conversion[Schema.HistoryKey, Option[Schema.HistoryKey]] = (fun: Schema.HistoryKey) => Option(fun)
		given putMapStringSchemaMetricTimeseries: Conversion[Map[String, Schema.MetricTimeseries], Option[Map[String, Schema.MetricTimeseries]]] = (fun: Map[String, Schema.MetricTimeseries]) => Option(fun)
		given putListSchemaCollectionPeriod: Conversion[List[Schema.CollectionPeriod], Option[List[Schema.CollectionPeriod]]] = (fun: List[Schema.CollectionPeriod]) => Option(fun)
		given putSchemaHistoryKeyFormFactorEnum: Conversion[Schema.HistoryKey.FormFactorEnum, Option[Schema.HistoryKey.FormFactorEnum]] = (fun: Schema.HistoryKey.FormFactorEnum) => Option(fun)
		given putListSchemaTimeseriesBin: Conversion[List[Schema.TimeseriesBin], Option[List[Schema.TimeseriesBin]]] = (fun: List[Schema.TimeseriesBin]) => Option(fun)
		given putSchemaTimeseriesPercentiles: Conversion[Schema.TimeseriesPercentiles, Option[Schema.TimeseriesPercentiles]] = (fun: Schema.TimeseriesPercentiles) => Option(fun)
		given putMapStringSchemaFractionTimeseries: Conversion[Map[String, Schema.FractionTimeseries], Option[Map[String, Schema.FractionTimeseries]]] = (fun: Map[String, Schema.FractionTimeseries]) => Option(fun)
		given putListBigDecimal: Conversion[List[BigDecimal], Option[List[BigDecimal]]] = (fun: List[BigDecimal]) => Option(fun)
		given putListJsValue: Conversion[List[JsValue], Option[List[JsValue]]] = (fun: List[JsValue]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
