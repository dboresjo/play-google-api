package com.boresjo.play.api.google.doubleclickbidmanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaQueryMetadata: Conversion[Schema.QueryMetadata, Option[Schema.QueryMetadata]] = (fun: Schema.QueryMetadata) => Option(fun)
		given putSchemaParameters: Conversion[Schema.Parameters, Option[Schema.Parameters]] = (fun: Schema.Parameters) => Option(fun)
		given putSchemaQuerySchedule: Conversion[Schema.QuerySchedule, Option[Schema.QuerySchedule]] = (fun: Schema.QuerySchedule) => Option(fun)
		given putSchemaDataRange: Conversion[Schema.DataRange, Option[Schema.DataRange]] = (fun: Schema.DataRange) => Option(fun)
		given putSchemaQueryMetadataFormatEnum: Conversion[Schema.QueryMetadata.FormatEnum, Option[Schema.QueryMetadata.FormatEnum]] = (fun: Schema.QueryMetadata.FormatEnum) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaDataRangeRangeEnum: Conversion[Schema.DataRange.RangeEnum, Option[Schema.DataRange.RangeEnum]] = (fun: Schema.DataRange.RangeEnum) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaParametersTypeEnum: Conversion[Schema.Parameters.TypeEnum, Option[Schema.Parameters.TypeEnum]] = (fun: Schema.Parameters.TypeEnum) => Option(fun)
		given putListSchemaFilterPair: Conversion[List[Schema.FilterPair], Option[List[Schema.FilterPair]]] = (fun: List[Schema.FilterPair]) => Option(fun)
		given putSchemaOptions: Conversion[Schema.Options, Option[Schema.Options]] = (fun: Schema.Options) => Option(fun)
		given putSchemaQueryScheduleFrequencyEnum: Conversion[Schema.QuerySchedule.FrequencyEnum, Option[Schema.QuerySchedule.FrequencyEnum]] = (fun: Schema.QuerySchedule.FrequencyEnum) => Option(fun)
		given putListSchemaQuery: Conversion[List[Schema.Query], Option[List[Schema.Query]]] = (fun: List[Schema.Query]) => Option(fun)
		given putSchemaReportKey: Conversion[Schema.ReportKey, Option[Schema.ReportKey]] = (fun: Schema.ReportKey) => Option(fun)
		given putSchemaReportMetadata: Conversion[Schema.ReportMetadata, Option[Schema.ReportMetadata]] = (fun: Schema.ReportMetadata) => Option(fun)
		given putSchemaReportStatus: Conversion[Schema.ReportStatus, Option[Schema.ReportStatus]] = (fun: Schema.ReportStatus) => Option(fun)
		given putSchemaReportStatusStateEnum: Conversion[Schema.ReportStatus.StateEnum, Option[Schema.ReportStatus.StateEnum]] = (fun: Schema.ReportStatus.StateEnum) => Option(fun)
		given putSchemaReportStatusFormatEnum: Conversion[Schema.ReportStatus.FormatEnum, Option[Schema.ReportStatus.FormatEnum]] = (fun: Schema.ReportStatus.FormatEnum) => Option(fun)
		given putListSchemaReport: Conversion[List[Schema.Report], Option[List[Schema.Report]]] = (fun: List[Schema.Report]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
