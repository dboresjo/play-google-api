package com.boresjo.play.api.google.youtubereporting

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGdataDiffUploadRequest: Format[Schema.GdataDiffUploadRequest] = Json.format[Schema.GdataDiffUploadRequest]
	given fmtGdataCompositeMedia: Format[Schema.GdataCompositeMedia] = Json.format[Schema.GdataCompositeMedia]
	given fmtGdataDiffChecksumsResponse: Format[Schema.GdataDiffChecksumsResponse] = Json.format[Schema.GdataDiffChecksumsResponse]
	given fmtGdataDiffVersionResponse: Format[Schema.GdataDiffVersionResponse] = Json.format[Schema.GdataDiffVersionResponse]
	given fmtGdataDiffDownloadResponse: Format[Schema.GdataDiffDownloadResponse] = Json.format[Schema.GdataDiffDownloadResponse]
	given fmtJob: Format[Schema.Job] = Json.format[Schema.Job]
	given fmtGdataBlobstore2Info: Format[Schema.GdataBlobstore2Info] = Json.format[Schema.GdataBlobstore2Info]
	given fmtReportType: Format[Schema.ReportType] = Json.format[Schema.ReportType]
	given fmtListReportTypesResponse: Format[Schema.ListReportTypesResponse] = Json.format[Schema.ListReportTypesResponse]
	given fmtGdataDiffUploadResponse: Format[Schema.GdataDiffUploadResponse] = Json.format[Schema.GdataDiffUploadResponse]
	given fmtGdataContentTypeInfo: Format[Schema.GdataContentTypeInfo] = Json.format[Schema.GdataContentTypeInfo]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtGdataMedia: Format[Schema.GdataMedia] = Json.format[Schema.GdataMedia]
	given fmtGdataObjectId: Format[Schema.GdataObjectId] = Json.format[Schema.GdataObjectId]
	given fmtGdataMediaReferenceTypeEnum: Format[Schema.GdataMedia.ReferenceTypeEnum] = JsonEnumFormat[Schema.GdataMedia.ReferenceTypeEnum]
	given fmtGdataDownloadParameters: Format[Schema.GdataDownloadParameters] = Json.format[Schema.GdataDownloadParameters]
	given fmtListJobsResponse: Format[Schema.ListJobsResponse] = Json.format[Schema.ListJobsResponse]
	given fmtListReportsResponse: Format[Schema.ListReportsResponse] = Json.format[Schema.ListReportsResponse]
	given fmtReport: Format[Schema.Report] = Json.format[Schema.Report]
	given fmtGdataCompositeMediaReferenceTypeEnum: Format[Schema.GdataCompositeMedia.ReferenceTypeEnum] = JsonEnumFormat[Schema.GdataCompositeMedia.ReferenceTypeEnum]
}
