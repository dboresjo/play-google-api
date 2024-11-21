package com.boresjo.play.api.google.cloudsupport

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaWorkflowOperationMetadataOperationActionEnum: Conversion[Schema.WorkflowOperationMetadata.OperationActionEnum, Option[Schema.WorkflowOperationMetadata.OperationActionEnum]] = (fun: Schema.WorkflowOperationMetadata.OperationActionEnum) => Option(fun)
		given putSchemaWorkflowOperationMetadataWorkflowOperationTypeEnum: Conversion[Schema.WorkflowOperationMetadata.WorkflowOperationTypeEnum, Option[Schema.WorkflowOperationMetadata.WorkflowOperationTypeEnum]] = (fun: Schema.WorkflowOperationMetadata.WorkflowOperationTypeEnum) => Option(fun)
		given putSchemaCompositeMedia: Conversion[Schema.CompositeMedia, Option[Schema.CompositeMedia]] = (fun: Schema.CompositeMedia) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaDiffChecksumsResponse: Conversion[Schema.DiffChecksumsResponse, Option[Schema.DiffChecksumsResponse]] = (fun: Schema.DiffChecksumsResponse) => Option(fun)
		given putSchemaMediaReferenceTypeEnum: Conversion[Schema.Media.ReferenceTypeEnum, Option[Schema.Media.ReferenceTypeEnum]] = (fun: Schema.Media.ReferenceTypeEnum) => Option(fun)
		given putSchemaDiffUploadRequest: Conversion[Schema.DiffUploadRequest, Option[Schema.DiffUploadRequest]] = (fun: Schema.DiffUploadRequest) => Option(fun)
		given putSchemaObjectId: Conversion[Schema.ObjectId, Option[Schema.ObjectId]] = (fun: Schema.ObjectId) => Option(fun)
		given putSchemaContentTypeInfo: Conversion[Schema.ContentTypeInfo, Option[Schema.ContentTypeInfo]] = (fun: Schema.ContentTypeInfo) => Option(fun)
		given putSchemaDiffVersionResponse: Conversion[Schema.DiffVersionResponse, Option[Schema.DiffVersionResponse]] = (fun: Schema.DiffVersionResponse) => Option(fun)
		given putSchemaDownloadParameters: Conversion[Schema.DownloadParameters, Option[Schema.DownloadParameters]] = (fun: Schema.DownloadParameters) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaBlobstore2Info: Conversion[Schema.Blobstore2Info, Option[Schema.Blobstore2Info]] = (fun: Schema.Blobstore2Info) => Option(fun)
		given putSchemaDiffDownloadResponse: Conversion[Schema.DiffDownloadResponse, Option[Schema.DiffDownloadResponse]] = (fun: Schema.DiffDownloadResponse) => Option(fun)
		given putSchemaDiffUploadResponse: Conversion[Schema.DiffUploadResponse, Option[Schema.DiffUploadResponse]] = (fun: Schema.DiffUploadResponse) => Option(fun)
		given putListSchemaCompositeMedia: Conversion[List[Schema.CompositeMedia], Option[List[Schema.CompositeMedia]]] = (fun: List[Schema.CompositeMedia]) => Option(fun)
		given putListSchemaAttachment: Conversion[List[Schema.Attachment], Option[List[Schema.Attachment]]] = (fun: List[Schema.Attachment]) => Option(fun)
		given putListSchemaComment: Conversion[List[Schema.Comment], Option[List[Schema.Comment]]] = (fun: List[Schema.Comment]) => Option(fun)
		given putSchemaEscalation: Conversion[Schema.Escalation, Option[Schema.Escalation]] = (fun: Schema.Escalation) => Option(fun)
		given putSchemaActor: Conversion[Schema.Actor, Option[Schema.Actor]] = (fun: Schema.Actor) => Option(fun)
		given putSchemaCaseStateEnum: Conversion[Schema.Case.StateEnum, Option[Schema.Case.StateEnum]] = (fun: Schema.Case.StateEnum) => Option(fun)
		given putSchemaCasePriorityEnum: Conversion[Schema.Case.PriorityEnum, Option[Schema.Case.PriorityEnum]] = (fun: Schema.Case.PriorityEnum) => Option(fun)
		given putSchemaCaseClassification: Conversion[Schema.CaseClassification, Option[Schema.CaseClassification]] = (fun: Schema.CaseClassification) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaCompositeMediaReferenceTypeEnum: Conversion[Schema.CompositeMedia.ReferenceTypeEnum, Option[Schema.CompositeMedia.ReferenceTypeEnum]] = (fun: Schema.CompositeMedia.ReferenceTypeEnum) => Option(fun)
		given putListSchemaCaseClassification: Conversion[List[Schema.CaseClassification], Option[List[Schema.CaseClassification]]] = (fun: List[Schema.CaseClassification]) => Option(fun)
		given putListSchemaCase: Conversion[List[Schema.Case], Option[List[Schema.Case]]] = (fun: List[Schema.Case]) => Option(fun)
		given putSchemaEscalationReasonEnum: Conversion[Schema.Escalation.ReasonEnum, Option[Schema.Escalation.ReasonEnum]] = (fun: Schema.Escalation.ReasonEnum) => Option(fun)
		given putSchemaAttachment: Conversion[Schema.Attachment, Option[Schema.Attachment]] = (fun: Schema.Attachment) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
