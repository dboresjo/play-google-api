package com.boresjo.play.api.google.businessprofileperformance

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putSchemaTimeSeries: Conversion[Schema.TimeSeries, Option[Schema.TimeSeries]] = (fun: Schema.TimeSeries) => Option(fun)
		given putListSchemaDatedValue: Conversion[List[Schema.DatedValue], Option[List[Schema.DatedValue]]] = (fun: List[Schema.DatedValue]) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaMultiDailyMetricTimeSeries: Conversion[List[Schema.MultiDailyMetricTimeSeries], Option[List[Schema.MultiDailyMetricTimeSeries]]] = (fun: List[Schema.MultiDailyMetricTimeSeries]) => Option(fun)
		given putListSchemaDailyMetricTimeSeries: Conversion[List[Schema.DailyMetricTimeSeries], Option[List[Schema.DailyMetricTimeSeries]]] = (fun: List[Schema.DailyMetricTimeSeries]) => Option(fun)
		given putSchemaDailyMetricTimeSeriesDailyMetricEnum: Conversion[Schema.DailyMetricTimeSeries.DailyMetricEnum, Option[Schema.DailyMetricTimeSeries.DailyMetricEnum]] = (fun: Schema.DailyMetricTimeSeries.DailyMetricEnum) => Option(fun)
		given putSchemaDailySubEntityType: Conversion[Schema.DailySubEntityType, Option[Schema.DailySubEntityType]] = (fun: Schema.DailySubEntityType) => Option(fun)
		given putSchemaDailySubEntityTypeDayOfWeekEnum: Conversion[Schema.DailySubEntityType.DayOfWeekEnum, Option[Schema.DailySubEntityType.DayOfWeekEnum]] = (fun: Schema.DailySubEntityType.DayOfWeekEnum) => Option(fun)
		given putSchemaTimeOfDay: Conversion[Schema.TimeOfDay, Option[Schema.TimeOfDay]] = (fun: Schema.TimeOfDay) => Option(fun)
		given putListSchemaSearchKeywordCount: Conversion[List[Schema.SearchKeywordCount], Option[List[Schema.SearchKeywordCount]]] = (fun: List[Schema.SearchKeywordCount]) => Option(fun)
		given putSchemaInsightsValue: Conversion[Schema.InsightsValue, Option[Schema.InsightsValue]] = (fun: Schema.InsightsValue) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
