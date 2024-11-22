package com.boresjo.play.api.google.youtubereporting

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GdataDiffUploadRequest(
	  /** gdata */
		objectVersion: Option[String] = None,
	  /** gdata */
		checksumsInfo: Option[Schema.GdataCompositeMedia] = None,
	  /** gdata */
		objectInfo: Option[Schema.GdataCompositeMedia] = None
	)
	
	case class GdataDiffChecksumsResponse(
	  /** gdata */
		objectVersion: Option[String] = None,
	  /** gdata */
		chunkSizeBytes: Option[String] = None,
	  /** gdata */
		objectSizeBytes: Option[String] = None,
	  /** gdata */
		checksumsLocation: Option[Schema.GdataCompositeMedia] = None,
	  /** gdata */
		objectLocation: Option[Schema.GdataCompositeMedia] = None
	)
	
	case class GdataDiffVersionResponse(
	  /** gdata */
		objectVersion: Option[String] = None,
	  /** gdata */
		objectSizeBytes: Option[String] = None
	)
	
	case class GdataDiffDownloadResponse(
	  /** gdata */
		objectLocation: Option[Schema.GdataCompositeMedia] = None
	)
	
	case class Job(
	  /** The server-generated ID of the job (max. 40 characters). */
		id: Option[String] = None,
	  /** The name of the job (max. 100 characters). */
		name: Option[String] = None,
	  /** True if this a system-managed job that cannot be modified by the user; otherwise false. */
		systemManaged: Option[Boolean] = None,
	  /** The type of reports this job creates. Corresponds to the ID of a ReportType. */
		reportTypeId: Option[String] = None,
	  /** The date/time when this job will expire/expired. After a job expired, no new reports are generated. */
		expireTime: Option[String] = None,
	  /** The creation date/time of the job. */
		createTime: Option[String] = None
	)
	
	case class GdataBlobstore2Info(
	  /** gdata */
		uploadMetadataContainer: Option[String] = None,
	  /** gdata */
		downloadReadHandle: Option[String] = None,
	  /** gdata */
		blobId: Option[String] = None,
	  /** gdata */
		readToken: Option[String] = None,
	  /** gdata */
		blobGeneration: Option[String] = None
	)
	
	case class ReportType(
	  /** The ID of the report type (max. 100 characters). */
		id: Option[String] = None,
	  /** True if this a system-managed report type; otherwise false. Reporting jobs for system-managed report types are created automatically and can thus not be used in the `CreateJob` method. */
		systemManaged: Option[Boolean] = None,
	  /** The name of the report type (max. 100 characters). */
		name: Option[String] = None,
	  /** The date/time when this report type was/will be deprecated. */
		deprecateTime: Option[String] = None
	)
	
	case class ListReportTypesResponse(
	  /** The list of report types. */
		reportTypes: Option[List[Schema.ReportType]] = None,
	  /** A token to retrieve next page of results. Pass this value in the ListReportTypesRequest.page_token field in the subsequent call to `ListReportTypes` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GdataDiffUploadResponse(
	  /** gdata */
		originalObject: Option[Schema.GdataCompositeMedia] = None,
	  /** gdata */
		objectVersion: Option[String] = None
	)
	
	case class GdataContentTypeInfo(
	  /** gdata */
		fromFileName: Option[String] = None,
	  /** gdata */
		fromUrlPath: Option[String] = None,
	  /** gdata */
		fromBytes: Option[String] = None,
	  /** gdata */
		fromHeader: Option[String] = None,
	  /** gdata */
		bestGuess: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	object GdataMedia {
		enum ReferenceTypeEnum extends Enum[ReferenceTypeEnum] { case PATH, BLOB_REF, INLINE, GET_MEDIA, COMPOSITE_MEDIA, BIGSTORE_REF, DIFF_VERSION_RESPONSE, DIFF_CHECKSUMS_RESPONSE, DIFF_DOWNLOAD_RESPONSE, DIFF_UPLOAD_REQUEST, DIFF_UPLOAD_RESPONSE, COSMO_BINARY_REFERENCE, ARBITRARY_BYTES }
	}
	case class GdataMedia(
	  /** gdata */
		diffUploadRequest: Option[Schema.GdataDiffUploadRequest] = None,
	  /** gdata */
		hashVerified: Option[Boolean] = None,
	  /** gdata */
		contentType: Option[String] = None,
	  /** gdata */
		token: Option[String] = None,
	  /** gdata */
		diffDownloadResponse: Option[Schema.GdataDiffDownloadResponse] = None,
	  /** gdata */
		blobRef: Option[String] = None,
	  /** gdata */
		bigstoreObjectRef: Option[String] = None,
	  /** gdata */
		compositeMedia: Option[List[Schema.GdataCompositeMedia]] = None,
	  /** gdata */
		cosmoBinaryReference: Option[String] = None,
	  /** gdata */
		md5Hash: Option[String] = None,
	  /** gdata */
		mediaId: Option[String] = None,
	  /** gdata */
		blobstore2Info: Option[Schema.GdataBlobstore2Info] = None,
	  /** gdata */
		objectId: Option[Schema.GdataObjectId] = None,
	  /** gdata */
		referenceType: Option[Schema.GdataMedia.ReferenceTypeEnum] = None,
	  /** gdata */
		length: Option[String] = None,
	  /** gdata */
		diffUploadResponse: Option[Schema.GdataDiffUploadResponse] = None,
	  /** gdata */
		inline: Option[String] = None,
	  /** gdata */
		contentTypeInfo: Option[Schema.GdataContentTypeInfo] = None,
	  /** gdata */
		timestamp: Option[String] = None,
	  /** gdata */
		downloadParameters: Option[Schema.GdataDownloadParameters] = None,
	  /** gdata */
		path: Option[String] = None,
	  /** gdata */
		hash: Option[String] = None,
	  /** gdata */
		crc32cHash: Option[Int] = None,
	  /** gdata */
		isPotentialRetry: Option[Boolean] = None,
	  /** gdata */
		sha256Hash: Option[String] = None,
	  /** gdata */
		filename: Option[String] = None,
	  /** gdata */
		diffVersionResponse: Option[Schema.GdataDiffVersionResponse] = None,
	  /** gdata */
		algorithm: Option[String] = None,
	  /** gdata */
		sha1Hash: Option[String] = None,
	  /** gdata */
		diffChecksumsResponse: Option[Schema.GdataDiffChecksumsResponse] = None
	)
	
	case class ListJobsResponse(
	  /** The list of jobs. */
		jobs: Option[List[Schema.Job]] = None,
	  /** A token to retrieve next page of results. Pass this value in the ListJobsRequest.page_token field in the subsequent call to `ListJobs` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GdataDownloadParameters(
	  /** gdata */
		allowGzipCompression: Option[Boolean] = None,
	  /** gdata */
		ignoreRange: Option[Boolean] = None
	)
	
	case class GdataObjectId(
	  /** gdata */
		bucketName: Option[String] = None,
	  /** gdata */
		generation: Option[String] = None,
	  /** gdata */
		objectName: Option[String] = None
	)
	
	case class ListReportsResponse(
	  /** The list of report types. */
		reports: Option[List[Schema.Report]] = None,
	  /** A token to retrieve next page of results. Pass this value in the ListReportsRequest.page_token field in the subsequent call to `ListReports` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object GdataCompositeMedia {
		enum ReferenceTypeEnum extends Enum[ReferenceTypeEnum] { case PATH, BLOB_REF, INLINE, BIGSTORE_REF, COSMO_BINARY_REFERENCE }
	}
	case class GdataCompositeMedia(
	  /** gdata */
		objectId: Option[Schema.GdataObjectId] = None,
	  /** gdata */
		md5Hash: Option[String] = None,
	  /** gdata */
		length: Option[String] = None,
	  /** gdata */
		blobRef: Option[String] = None,
	  /** gdata */
		referenceType: Option[Schema.GdataCompositeMedia.ReferenceTypeEnum] = None,
	  /** gdata */
		blobstore2Info: Option[Schema.GdataBlobstore2Info] = None,
	  /** gdata */
		path: Option[String] = None,
	  /** gdata */
		cosmoBinaryReference: Option[String] = None,
	  /** gdata */
		sha1Hash: Option[String] = None,
	  /** gdata */
		crc32cHash: Option[Int] = None,
	  /** gdata */
		inline: Option[String] = None
	)
	
	case class Report(
	  /** The ID of the job that created this report. */
		jobId: Option[String] = None,
	  /** The date/time when this report was created. */
		createTime: Option[String] = None,
	  /** The start of the time period that the report instance covers. The value is inclusive. */
		startTime: Option[String] = None,
	  /** The URL from which the report can be downloaded (max. 1000 characters). */
		downloadUrl: Option[String] = None,
	  /** The end of the time period that the report instance covers. The value is exclusive. */
		endTime: Option[String] = None,
	  /** The date/time when the job this report belongs to will expire/expired. */
		jobExpireTime: Option[String] = None,
	  /** The server-generated ID of the report. */
		id: Option[String] = None
	)
}
