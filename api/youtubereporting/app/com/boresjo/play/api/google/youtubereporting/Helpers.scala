package com.boresjo.play.api.google.youtubereporting

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaGdataCompositeMedia: Conversion[Schema.GdataCompositeMedia, Option[Schema.GdataCompositeMedia]] = (fun: Schema.GdataCompositeMedia) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaReportType: Conversion[List[Schema.ReportType], Option[List[Schema.ReportType]]] = (fun: List[Schema.ReportType]) => Option(fun)
		given putSchemaGdataDiffUploadRequest: Conversion[Schema.GdataDiffUploadRequest, Option[Schema.GdataDiffUploadRequest]] = (fun: Schema.GdataDiffUploadRequest) => Option(fun)
		given putSchemaGdataDiffDownloadResponse: Conversion[Schema.GdataDiffDownloadResponse, Option[Schema.GdataDiffDownloadResponse]] = (fun: Schema.GdataDiffDownloadResponse) => Option(fun)
		given putListSchemaGdataCompositeMedia: Conversion[List[Schema.GdataCompositeMedia], Option[List[Schema.GdataCompositeMedia]]] = (fun: List[Schema.GdataCompositeMedia]) => Option(fun)
		given putSchemaGdataBlobstore2Info: Conversion[Schema.GdataBlobstore2Info, Option[Schema.GdataBlobstore2Info]] = (fun: Schema.GdataBlobstore2Info) => Option(fun)
		given putSchemaGdataObjectId: Conversion[Schema.GdataObjectId, Option[Schema.GdataObjectId]] = (fun: Schema.GdataObjectId) => Option(fun)
		given putSchemaGdataMediaReferenceTypeEnum: Conversion[Schema.GdataMedia.ReferenceTypeEnum, Option[Schema.GdataMedia.ReferenceTypeEnum]] = (fun: Schema.GdataMedia.ReferenceTypeEnum) => Option(fun)
		given putSchemaGdataDiffUploadResponse: Conversion[Schema.GdataDiffUploadResponse, Option[Schema.GdataDiffUploadResponse]] = (fun: Schema.GdataDiffUploadResponse) => Option(fun)
		given putSchemaGdataContentTypeInfo: Conversion[Schema.GdataContentTypeInfo, Option[Schema.GdataContentTypeInfo]] = (fun: Schema.GdataContentTypeInfo) => Option(fun)
		given putSchemaGdataDownloadParameters: Conversion[Schema.GdataDownloadParameters, Option[Schema.GdataDownloadParameters]] = (fun: Schema.GdataDownloadParameters) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaGdataDiffVersionResponse: Conversion[Schema.GdataDiffVersionResponse, Option[Schema.GdataDiffVersionResponse]] = (fun: Schema.GdataDiffVersionResponse) => Option(fun)
		given putSchemaGdataDiffChecksumsResponse: Conversion[Schema.GdataDiffChecksumsResponse, Option[Schema.GdataDiffChecksumsResponse]] = (fun: Schema.GdataDiffChecksumsResponse) => Option(fun)
		given putListSchemaJob: Conversion[List[Schema.Job], Option[List[Schema.Job]]] = (fun: List[Schema.Job]) => Option(fun)
		given putListSchemaReport: Conversion[List[Schema.Report], Option[List[Schema.Report]]] = (fun: List[Schema.Report]) => Option(fun)
		given putSchemaGdataCompositeMediaReferenceTypeEnum: Conversion[Schema.GdataCompositeMedia.ReferenceTypeEnum, Option[Schema.GdataCompositeMedia.ReferenceTypeEnum]] = (fun: Schema.GdataCompositeMedia.ReferenceTypeEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
