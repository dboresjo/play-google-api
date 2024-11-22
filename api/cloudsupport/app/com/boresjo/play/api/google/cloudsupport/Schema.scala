package com.boresjo.play.api.google.cloudsupport

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object WorkflowOperationMetadata {
		enum OperationActionEnum extends Enum[OperationActionEnum] { case OPERATION_ACTION_UNSPECIFIED, CREATE_SUPPORT_ACCOUNT, UPDATE_SUPPORT_ACCOUNT, PURCHASE_SUPPORT_ACCOUNT }
		enum WorkflowOperationTypeEnum extends Enum[WorkflowOperationTypeEnum] { case UNKNOWN_OPERATION_TYPE, WORKFLOWS_V1, WORKFLOWS_V2 }
	}
	case class WorkflowOperationMetadata(
	  /** The namespace that the job was scheduled in. Must be included in the workflow metadata so the workflow status can be retrieved. */
		namespace: Option[String] = None,
	  /** The type of action the operation is classified as. */
		operationAction: Option[Schema.WorkflowOperationMetadata.OperationActionEnum] = None,
	  /** Which version of the workflow service this operation came from. */
		workflowOperationType: Option[Schema.WorkflowOperationMetadata.WorkflowOperationTypeEnum] = None
	)
	
	case class DiffVersionResponse(
	  /** # gdata.&#42; are outside protos with mising documentation */
		objectVersion: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		objectSizeBytes: Option[String] = None
	)
	
	case class DiffChecksumsResponse(
	  /** # gdata.&#42; are outside protos with mising documentation */
		objectVersion: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		objectSizeBytes: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		checksumsLocation: Option[Schema.CompositeMedia] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		objectLocation: Option[Schema.CompositeMedia] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		chunkSizeBytes: Option[String] = None
	)
	
	case class CaseClassification(
	  /** The unique ID for a classification. Must be specified for case creation. To retrieve valid classification IDs for case creation, use `caseClassifications.search`. Classification IDs returned by `caseClassifications.search` are guaranteed to be valid for at least 6 months. If a given classification is deactiveated, it will immediately stop being returned. After 6 months, `case.create` requests using the classification ID will fail. */
		id: Option[String] = None,
	  /** A display name for the classification. The display name is not static and can change. To uniquely and consistently identify classifications, use the `CaseClassification.id` field. */
		displayName: Option[String] = None
	)
	
	case class Actor(
	  /** The email address of the actor. If not provided, it is inferred from the credentials supplied during case creation. When a name is provided, an email must also be provided. If the user is a Google Support agent, this is obfuscated. This field is deprecated. Use &#42;&#42;username&#42;&#42; field instead. */
		email: Option[String] = None,
	  /** Output only. Whether the actor is a Google support actor. */
		googleSupport: Option[Boolean] = None,
	  /** The name to display for the actor. If not provided, it is inferred from credentials supplied during case creation. When an email is provided, a display name must also be provided. This will be obfuscated if the user is a Google Support agent. */
		displayName: Option[String] = None,
	  /** Output only. The username of the actor. It may look like an email or other format provided by the identity provider. If not provided, it is inferred from the credentials supplied. When a name is provided, a username must also be provided. If the user is a Google Support agent, this will not be set. */
		username: Option[String] = None
	)
	
	object Media {
		enum ReferenceTypeEnum extends Enum[ReferenceTypeEnum] { case PATH, BLOB_REF, INLINE, GET_MEDIA, COMPOSITE_MEDIA, BIGSTORE_REF, DIFF_VERSION_RESPONSE, DIFF_CHECKSUMS_RESPONSE, DIFF_DOWNLOAD_RESPONSE, DIFF_UPLOAD_REQUEST, DIFF_UPLOAD_RESPONSE, COSMO_BINARY_REFERENCE, ARBITRARY_BYTES }
	}
	case class Media(
	  /** # gdata.&#42; are outside protos with mising documentation */
		length: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		hash: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		cosmoBinaryReference: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		bigstoreObjectRef: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		diffChecksumsResponse: Option[Schema.DiffChecksumsResponse] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		algorithm: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		referenceType: Option[Schema.Media.ReferenceTypeEnum] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		diffUploadRequest: Option[Schema.DiffUploadRequest] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		md5Hash: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		objectId: Option[Schema.ObjectId] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		contentTypeInfo: Option[Schema.ContentTypeInfo] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		token: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		diffVersionResponse: Option[Schema.DiffVersionResponse] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		filename: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		downloadParameters: Option[Schema.DownloadParameters] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		crc32cHash: Option[Int] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		blobstore2Info: Option[Schema.Blobstore2Info] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		sha256Hash: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		mediaId: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		diffDownloadResponse: Option[Schema.DiffDownloadResponse] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		diffUploadResponse: Option[Schema.DiffUploadResponse] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		blobRef: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		contentType: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		path: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		timestamp: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		isPotentialRetry: Option[Boolean] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		inline: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		sha1Hash: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		compositeMedia: Option[List[Schema.CompositeMedia]] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		hashVerified: Option[Boolean] = None
	)
	
	case class Blobstore2Info(
	  /** # gdata.&#42; are outside protos with mising documentation */
		uploadMetadataContainer: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		blobId: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		downloadReadHandle: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		readToken: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		blobGeneration: Option[String] = None
	)
	
	case class ListAttachmentsResponse(
	  /** A token to retrieve the next page of results. Set this in the `page_token` field of subsequent `cases.attachments.list` requests. If unspecified, there are no more results to retrieve. */
		nextPageToken: Option[String] = None,
	  /** The list of attachments associated with a case. */
		attachments: Option[List[Schema.Attachment]] = None
	)
	
	case class ListCommentsResponse(
	  /** A token to retrieve the next page of results. Set this in the `page_token` field of subsequent `cases.comments.list` requests. If unspecified, there are no more results to retrieve. */
		nextPageToken: Option[String] = None,
	  /** List of the comments associated with the case. */
		comments: Option[List[Schema.Comment]] = None
	)
	
	case class ContentTypeInfo(
	  /** # gdata.&#42; are outside protos with mising documentation */
		fromHeader: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		bestGuess: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		fromUrlPath: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		fromBytes: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		fromFileName: Option[String] = None
	)
	
	case class ObjectId(
	  /** # gdata.&#42; are outside protos with mising documentation */
		bucketName: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		objectName: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		generation: Option[String] = None
	)
	
	case class DiffUploadResponse(
	  /** # gdata.&#42; are outside protos with mising documentation */
		objectVersion: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		originalObject: Option[Schema.CompositeMedia] = None
	)
	
	case class EscalateCaseRequest(
	  /** The escalation information to be sent with the escalation request. */
		escalation: Option[Schema.Escalation] = None
	)
	
	case class Attachment(
	  /** Output only. The time at which the attachment was created. */
		createTime: Option[String] = None,
	  /** Output only. The size of the attachment in bytes. */
		sizeBytes: Option[String] = None,
	  /** Output only. The resource name of the attachment. */
		name: Option[String] = None,
	  /** Output only. The MIME type of the attachment (e.g. text/plain). */
		mimeType: Option[String] = None,
	  /** Output only. The user who uploaded the attachment. Note, the name and email will be obfuscated if the attachment was uploaded by Google support. */
		creator: Option[Schema.Actor] = None,
	  /** The filename of the attachment (e.g. `"graph.jpg"`). */
		filename: Option[String] = None
	)
	
	case class CloseCaseRequest(
	
	)
	
	object Case {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, NEW, IN_PROGRESS_GOOGLE_SUPPORT, ACTION_REQUIRED, SOLUTION_PROVIDED, CLOSED }
		enum PriorityEnum extends Enum[PriorityEnum] { case PRIORITY_UNSPECIFIED, P0, P1, P2, P3, P4 }
	}
	case class Case(
	  /** The language the user has requested to receive support in. This should be a BCP 47 language code (e.g., `"en"`, `"zh-CN"`, `"zh-TW"`, `"ja"`, `"ko"`). If no language or an unsupported language is specified, this field defaults to English (en). Language selection during case creation may affect your available support options. For a list of supported languages and their support working hours, see: https://cloud.google.com/support/docs/language-working-hours */
		languageCode: Option[String] = None,
	  /** Output only. The time this case was created. */
		createTime: Option[String] = None,
	  /** The timezone of the user who created the support case. It should be in a format IANA recognizes: https://www.iana.org/time-zones. There is no additional validation done by the API. */
		timeZone: Option[String] = None,
	  /** A user-supplied email address to send case update notifications for. This should only be used in BYOID flows, where we cannot infer the user's email address directly from their EUCs. */
		contactEmail: Option[String] = None,
	  /** Output only. The time this case was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. The current status of the support case. */
		state: Option[Schema.Case.StateEnum] = None,
	  /** A broad description of the issue. */
		description: Option[String] = None,
	  /** The priority of this case. */
		priority: Option[Schema.Case.PriorityEnum] = None,
	  /** Whether the case is currently escalated. */
		escalated: Option[Boolean] = None,
	  /** The resource name for the case. */
		name: Option[String] = None,
	  /** The issue classification applicable to this case. */
		classification: Option[Schema.CaseClassification] = None,
	  /** The email addresses to receive updates on this case. */
		subscriberEmailAddresses: Option[List[String]] = None,
	  /** The user who created the case. Note: The name and email will be obfuscated if the case was created by Google Support. */
		creator: Option[Schema.Actor] = None,
	  /** The short summary of the issue reported in this case. */
		displayName: Option[String] = None,
	  /** Whether this case was created for internal API testing and should not be acted on by the support team. */
		testCase: Option[Boolean] = None
	)
	
	case class DiffUploadRequest(
	  /** # gdata.&#42; are outside protos with mising documentation */
		checksumsInfo: Option[Schema.CompositeMedia] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		objectInfo: Option[Schema.CompositeMedia] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		objectVersion: Option[String] = None
	)
	
	object CompositeMedia {
		enum ReferenceTypeEnum extends Enum[ReferenceTypeEnum] { case PATH, BLOB_REF, INLINE, BIGSTORE_REF, COSMO_BINARY_REFERENCE }
	}
	case class CompositeMedia(
	  /** # gdata.&#42; are outside protos with mising documentation */
		blobstore2Info: Option[Schema.Blobstore2Info] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		sha1Hash: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		objectId: Option[Schema.ObjectId] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		md5Hash: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		inline: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		length: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		crc32cHash: Option[Int] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		path: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		referenceType: Option[Schema.CompositeMedia.ReferenceTypeEnum] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		cosmoBinaryReference: Option[String] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		blobRef: Option[String] = None
	)
	
	case class SearchCaseClassificationsResponse(
	  /** A token to retrieve the next page of results. Set this in the `page_token` field of subsequent `caseClassifications.list` requests. If unspecified, there are no more results to retrieve. */
		nextPageToken: Option[String] = None,
	  /** The classifications retrieved. */
		caseClassifications: Option[List[Schema.CaseClassification]] = None
	)
	
	case class SearchCasesResponse(
	  /** The list of cases associated with the parent after any filters have been applied. */
		cases: Option[List[Schema.Case]] = None,
	  /** A token to retrieve the next page of results. Set this in the `page_token` field of subsequent `cases.search` requests. If unspecified, there are no more results to retrieve. */
		nextPageToken: Option[String] = None
	)
	
	object Escalation {
		enum ReasonEnum extends Enum[ReasonEnum] { case REASON_UNSPECIFIED, RESOLUTION_TIME, TECHNICAL_EXPERTISE, BUSINESS_IMPACT }
	}
	case class Escalation(
	  /** Required. The reason why the Case is being escalated. */
		reason: Option[Schema.Escalation.ReasonEnum] = None,
	  /** Required. A free text description to accompany the `reason` field above. Provides additional context on why the case is being escalated. */
		justification: Option[String] = None
	)
	
	case class DiffDownloadResponse(
	  /** # gdata.&#42; are outside protos with mising documentation */
		objectLocation: Option[Schema.CompositeMedia] = None
	)
	
	case class CreateAttachmentRequest(
	  /** Required. The attachment to be created. */
		attachment: Option[Schema.Attachment] = None
	)
	
	case class Comment(
	  /** Output only. The time when the comment was created. */
		createTime: Option[String] = None,
	  /** Output only. Identifier. The resource name of the comment. */
		name: Option[String] = None,
	  /** The full comment body. Maximum of 12800 characters. */
		body: Option[String] = None,
	  /** Output only. The user or Google Support agent who created the comment. */
		creator: Option[Schema.Actor] = None,
	  /** Output only. DEPRECATED. DO NOT USE. A duplicate of the `body` field. This field is only present for legacy reasons. */
		plainTextBody: Option[String] = None
	)
	
	case class DownloadParameters(
	  /** # gdata.&#42; are outside protos with mising documentation */
		allowGzipCompression: Option[Boolean] = None,
	  /** # gdata.&#42; are outside protos with mising documentation */
		ignoreRange: Option[Boolean] = None
	)
	
	case class ListCasesResponse(
	  /** The list of cases associated with the parent after any filters have been applied. */
		cases: Option[List[Schema.Case]] = None,
	  /** A token to retrieve the next page of results. Set this in the `page_token` field of subsequent `cases.list` requests. If unspecified, there are no more results to retrieve. */
		nextPageToken: Option[String] = None
	)
}
