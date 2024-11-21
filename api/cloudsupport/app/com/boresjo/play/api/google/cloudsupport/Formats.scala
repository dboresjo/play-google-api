package com.boresjo.play.api.google.cloudsupport

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtWorkflowOperationMetadata: Format[Schema.WorkflowOperationMetadata] = Json.format[Schema.WorkflowOperationMetadata]
	given fmtWorkflowOperationMetadataOperationActionEnum: Format[Schema.WorkflowOperationMetadata.OperationActionEnum] = JsonEnumFormat[Schema.WorkflowOperationMetadata.OperationActionEnum]
	given fmtWorkflowOperationMetadataWorkflowOperationTypeEnum: Format[Schema.WorkflowOperationMetadata.WorkflowOperationTypeEnum] = JsonEnumFormat[Schema.WorkflowOperationMetadata.WorkflowOperationTypeEnum]
	given fmtDiffVersionResponse: Format[Schema.DiffVersionResponse] = Json.format[Schema.DiffVersionResponse]
	given fmtDiffChecksumsResponse: Format[Schema.DiffChecksumsResponse] = Json.format[Schema.DiffChecksumsResponse]
	given fmtCompositeMedia: Format[Schema.CompositeMedia] = Json.format[Schema.CompositeMedia]
	given fmtCaseClassification: Format[Schema.CaseClassification] = Json.format[Schema.CaseClassification]
	given fmtActor: Format[Schema.Actor] = Json.format[Schema.Actor]
	given fmtMedia: Format[Schema.Media] = Json.format[Schema.Media]
	given fmtMediaReferenceTypeEnum: Format[Schema.Media.ReferenceTypeEnum] = JsonEnumFormat[Schema.Media.ReferenceTypeEnum]
	given fmtDiffUploadRequest: Format[Schema.DiffUploadRequest] = Json.format[Schema.DiffUploadRequest]
	given fmtObjectId: Format[Schema.ObjectId] = Json.format[Schema.ObjectId]
	given fmtContentTypeInfo: Format[Schema.ContentTypeInfo] = Json.format[Schema.ContentTypeInfo]
	given fmtDownloadParameters: Format[Schema.DownloadParameters] = Json.format[Schema.DownloadParameters]
	given fmtBlobstore2Info: Format[Schema.Blobstore2Info] = Json.format[Schema.Blobstore2Info]
	given fmtDiffDownloadResponse: Format[Schema.DiffDownloadResponse] = Json.format[Schema.DiffDownloadResponse]
	given fmtDiffUploadResponse: Format[Schema.DiffUploadResponse] = Json.format[Schema.DiffUploadResponse]
	given fmtListAttachmentsResponse: Format[Schema.ListAttachmentsResponse] = Json.format[Schema.ListAttachmentsResponse]
	given fmtAttachment: Format[Schema.Attachment] = Json.format[Schema.Attachment]
	given fmtListCommentsResponse: Format[Schema.ListCommentsResponse] = Json.format[Schema.ListCommentsResponse]
	given fmtComment: Format[Schema.Comment] = Json.format[Schema.Comment]
	given fmtEscalateCaseRequest: Format[Schema.EscalateCaseRequest] = Json.format[Schema.EscalateCaseRequest]
	given fmtEscalation: Format[Schema.Escalation] = Json.format[Schema.Escalation]
	given fmtCloseCaseRequest: Format[Schema.CloseCaseRequest] = Json.format[Schema.CloseCaseRequest]
	given fmtCase: Format[Schema.Case] = Json.format[Schema.Case]
	given fmtCaseStateEnum: Format[Schema.Case.StateEnum] = JsonEnumFormat[Schema.Case.StateEnum]
	given fmtCasePriorityEnum: Format[Schema.Case.PriorityEnum] = JsonEnumFormat[Schema.Case.PriorityEnum]
	given fmtCompositeMediaReferenceTypeEnum: Format[Schema.CompositeMedia.ReferenceTypeEnum] = JsonEnumFormat[Schema.CompositeMedia.ReferenceTypeEnum]
	given fmtSearchCaseClassificationsResponse: Format[Schema.SearchCaseClassificationsResponse] = Json.format[Schema.SearchCaseClassificationsResponse]
	given fmtSearchCasesResponse: Format[Schema.SearchCasesResponse] = Json.format[Schema.SearchCasesResponse]
	given fmtEscalationReasonEnum: Format[Schema.Escalation.ReasonEnum] = JsonEnumFormat[Schema.Escalation.ReasonEnum]
	given fmtCreateAttachmentRequest: Format[Schema.CreateAttachmentRequest] = Json.format[Schema.CreateAttachmentRequest]
	given fmtListCasesResponse: Format[Schema.ListCasesResponse] = Json.format[Schema.ListCasesResponse]
}
