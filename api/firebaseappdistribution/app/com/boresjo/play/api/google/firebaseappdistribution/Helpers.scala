package com.boresjo.play.api.google.firebaseappdistribution

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaGoogleFirebaseAppdistroV1Tester: Conversion[List[Schema.GoogleFirebaseAppdistroV1Tester], Option[List[Schema.GoogleFirebaseAppdistroV1Tester]]] = (fun: List[Schema.GoogleFirebaseAppdistroV1Tester]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaGoogleRpcStatus: Conversion[Schema.GoogleRpcStatus, Option[Schema.GoogleRpcStatus]] = (fun: Schema.GoogleRpcStatus) => Option(fun)
		given putSchemaGoogleFirebaseAppdistroV1ReleaseNotes: Conversion[Schema.GoogleFirebaseAppdistroV1ReleaseNotes, Option[Schema.GoogleFirebaseAppdistroV1ReleaseNotes]] = (fun: Schema.GoogleFirebaseAppdistroV1ReleaseNotes) => Option(fun)
		given putListSchemaGoogleFirebaseAppdistroV1Release: Conversion[List[Schema.GoogleFirebaseAppdistroV1Release], Option[List[Schema.GoogleFirebaseAppdistroV1Release]]] = (fun: List[Schema.GoogleFirebaseAppdistroV1Release]) => Option(fun)
		given putSchemaGdataCompositeMedia: Conversion[Schema.GdataCompositeMedia, Option[Schema.GdataCompositeMedia]] = (fun: Schema.GdataCompositeMedia) => Option(fun)
		given putListSchemaGoogleFirebaseAppdistroV1Group: Conversion[List[Schema.GoogleFirebaseAppdistroV1Group], Option[List[Schema.GoogleFirebaseAppdistroV1Group]]] = (fun: List[Schema.GoogleFirebaseAppdistroV1Group]) => Option(fun)
		given putListSchemaGoogleFirebaseAppdistroV1FeedbackReport: Conversion[List[Schema.GoogleFirebaseAppdistroV1FeedbackReport], Option[List[Schema.GoogleFirebaseAppdistroV1FeedbackReport]]] = (fun: List[Schema.GoogleFirebaseAppdistroV1FeedbackReport]) => Option(fun)
		given putSchemaGdataMediaReferenceTypeEnum: Conversion[Schema.GdataMedia.ReferenceTypeEnum, Option[Schema.GdataMedia.ReferenceTypeEnum]] = (fun: Schema.GdataMedia.ReferenceTypeEnum) => Option(fun)
		given putListSchemaGdataCompositeMedia: Conversion[List[Schema.GdataCompositeMedia], Option[List[Schema.GdataCompositeMedia]]] = (fun: List[Schema.GdataCompositeMedia]) => Option(fun)
		given putSchemaGdataBlobstore2Info: Conversion[Schema.GdataBlobstore2Info, Option[Schema.GdataBlobstore2Info]] = (fun: Schema.GdataBlobstore2Info) => Option(fun)
		given putSchemaGdataDiffDownloadResponse: Conversion[Schema.GdataDiffDownloadResponse, Option[Schema.GdataDiffDownloadResponse]] = (fun: Schema.GdataDiffDownloadResponse) => Option(fun)
		given putSchemaGdataDiffUploadRequest: Conversion[Schema.GdataDiffUploadRequest, Option[Schema.GdataDiffUploadRequest]] = (fun: Schema.GdataDiffUploadRequest) => Option(fun)
		given putSchemaGdataDiffChecksumsResponse: Conversion[Schema.GdataDiffChecksumsResponse, Option[Schema.GdataDiffChecksumsResponse]] = (fun: Schema.GdataDiffChecksumsResponse) => Option(fun)
		given putSchemaGdataDownloadParameters: Conversion[Schema.GdataDownloadParameters, Option[Schema.GdataDownloadParameters]] = (fun: Schema.GdataDownloadParameters) => Option(fun)
		given putSchemaGdataContentTypeInfo: Conversion[Schema.GdataContentTypeInfo, Option[Schema.GdataContentTypeInfo]] = (fun: Schema.GdataContentTypeInfo) => Option(fun)
		given putSchemaGdataDiffVersionResponse: Conversion[Schema.GdataDiffVersionResponse, Option[Schema.GdataDiffVersionResponse]] = (fun: Schema.GdataDiffVersionResponse) => Option(fun)
		given putSchemaGdataDiffUploadResponse: Conversion[Schema.GdataDiffUploadResponse, Option[Schema.GdataDiffUploadResponse]] = (fun: Schema.GdataDiffUploadResponse) => Option(fun)
		given putSchemaGdataObjectId: Conversion[Schema.GdataObjectId, Option[Schema.GdataObjectId]] = (fun: Schema.GdataObjectId) => Option(fun)
		given putListSchemaGoogleLongrunningOperation: Conversion[List[Schema.GoogleLongrunningOperation], Option[List[Schema.GoogleLongrunningOperation]]] = (fun: List[Schema.GoogleLongrunningOperation]) => Option(fun)
		given putSchemaGdataCompositeMediaReferenceTypeEnum: Conversion[Schema.GdataCompositeMedia.ReferenceTypeEnum, Option[Schema.GdataCompositeMedia.ReferenceTypeEnum]] = (fun: Schema.GdataCompositeMedia.ReferenceTypeEnum) => Option(fun)
		given putSchemaGoogleFirebaseAppdistroV1Release: Conversion[Schema.GoogleFirebaseAppdistroV1Release, Option[Schema.GoogleFirebaseAppdistroV1Release]] = (fun: Schema.GoogleFirebaseAppdistroV1Release) => Option(fun)
		given putSchemaGoogleFirebaseAppdistroV1UploadReleaseResponseResultEnum: Conversion[Schema.GoogleFirebaseAppdistroV1UploadReleaseResponse.ResultEnum, Option[Schema.GoogleFirebaseAppdistroV1UploadReleaseResponse.ResultEnum]] = (fun: Schema.GoogleFirebaseAppdistroV1UploadReleaseResponse.ResultEnum) => Option(fun)
		given putSchemaGdataMedia: Conversion[Schema.GdataMedia, Option[Schema.GdataMedia]] = (fun: Schema.GdataMedia) => Option(fun)
		given putSchemaGoogleFirebaseAppdistroV1TestCertificate: Conversion[Schema.GoogleFirebaseAppdistroV1TestCertificate, Option[Schema.GoogleFirebaseAppdistroV1TestCertificate]] = (fun: Schema.GoogleFirebaseAppdistroV1TestCertificate) => Option(fun)
		given putSchemaGoogleFirebaseAppdistroV1AabInfoIntegrationStateEnum: Conversion[Schema.GoogleFirebaseAppdistroV1AabInfo.IntegrationStateEnum, Option[Schema.GoogleFirebaseAppdistroV1AabInfo.IntegrationStateEnum]] = (fun: Schema.GoogleFirebaseAppdistroV1AabInfo.IntegrationStateEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
