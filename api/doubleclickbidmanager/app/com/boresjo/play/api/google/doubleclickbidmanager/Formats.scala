package com.boresjo.play.api.google.doubleclickbidmanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtQuery: Format[Schema.Query] = Json.format[Schema.Query]
	given fmtQueryMetadata: Format[Schema.QueryMetadata] = Json.format[Schema.QueryMetadata]
	given fmtParameters: Format[Schema.Parameters] = Json.format[Schema.Parameters]
	given fmtQuerySchedule: Format[Schema.QuerySchedule] = Json.format[Schema.QuerySchedule]
	given fmtDataRange: Format[Schema.DataRange] = Json.format[Schema.DataRange]
	given fmtQueryMetadataFormatEnum: Format[Schema.QueryMetadata.FormatEnum] = JsonEnumFormat[Schema.QueryMetadata.FormatEnum]
	given fmtDataRangeRangeEnum: Format[Schema.DataRange.RangeEnum] = JsonEnumFormat[Schema.DataRange.RangeEnum]
	given fmtDate: Format[Schema.Date] = Json.format[Schema.Date]
	given fmtParametersTypeEnum: Format[Schema.Parameters.TypeEnum] = JsonEnumFormat[Schema.Parameters.TypeEnum]
	given fmtFilterPair: Format[Schema.FilterPair] = Json.format[Schema.FilterPair]
	given fmtOptions: Format[Schema.Options] = Json.format[Schema.Options]
	given fmtQueryScheduleFrequencyEnum: Format[Schema.QuerySchedule.FrequencyEnum] = JsonEnumFormat[Schema.QuerySchedule.FrequencyEnum]
	given fmtListQueriesResponse: Format[Schema.ListQueriesResponse] = Json.format[Schema.ListQueriesResponse]
	given fmtRunQueryRequest: Format[Schema.RunQueryRequest] = Json.format[Schema.RunQueryRequest]
	given fmtReport: Format[Schema.Report] = Json.format[Schema.Report]
	given fmtReportKey: Format[Schema.ReportKey] = Json.format[Schema.ReportKey]
	given fmtReportMetadata: Format[Schema.ReportMetadata] = Json.format[Schema.ReportMetadata]
	given fmtReportStatus: Format[Schema.ReportStatus] = Json.format[Schema.ReportStatus]
	given fmtReportStatusStateEnum: Format[Schema.ReportStatus.StateEnum] = JsonEnumFormat[Schema.ReportStatus.StateEnum]
	given fmtReportStatusFormatEnum: Format[Schema.ReportStatus.FormatEnum] = JsonEnumFormat[Schema.ReportStatus.FormatEnum]
	given fmtListReportsResponse: Format[Schema.ListReportsResponse] = Json.format[Schema.ListReportsResponse]
}
