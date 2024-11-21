package com.boresjo.play.api.google.firebaseappdistribution

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleFirebaseAppdistroV1BatchJoinGroupRequest(
	  /** Required. The emails of the testers to be added to the group. A maximum of 999 and a minimum of 1 tester can be created in a batch. */
		emails: Option[List[String]] = None,
	  /** Indicates whether to create tester resources based on `emails` if they don't exist yet. */
		createMissingTesters: Option[Boolean] = None
	)
	
	case class GoogleFirebaseAppdistroV1UploadReleaseMetadata(
	
	)
	
	case class GoogleFirebaseAppdistroV1BatchAddTestersResponse(
	  /** The testers which are created and/or already exist */
		testers: Option[List[Schema.GoogleFirebaseAppdistroV1Tester]] = None
	)
	
	case class GoogleFirebaseAppdistroV1DistributeReleaseResponse(
	
	)
	
	case class GoogleRpcStatus(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GdataDownloadParameters(
	  /** Determining whether or not Apiary should skip the inclusion of any Content-Range header on its response to Scotty. */
		ignoreRange: Option[Boolean] = None,
	  /** A boolean to be returned in the response to Scotty. Allows/disallows gzip encoding of the payload content when the server thinks it's advantageous (hence, does not guarantee compression) which allows Scotty to GZip the response to the client. */
		allowGzipCompression: Option[Boolean] = None
	)
	
	case class GoogleFirebaseAppdistroV1BatchAddTestersRequest(
	  /** Required. The email addresses of the tester resources to create. A maximum of 999 and a minimum of 1 tester can be created in a batch. */
		emails: Option[List[String]] = None
	)
	
	case class GoogleFirebaseAppdistroV1ListTestersResponse(
	  /** A short-lived token, which can be sent as `pageToken` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The testers listed. */
		testers: Option[List[Schema.GoogleFirebaseAppdistroV1Tester]] = None
	)
	
	case class GoogleFirebaseAppdistroV1TestCertificate(
	  /** Hex string of SHA1 hash of the test certificate used to resign the AAB */
		hashSha1: Option[String] = None,
	  /** Hex string of SHA256 hash of the test certificate used to resign the AAB */
		hashSha256: Option[String] = None,
	  /** Hex string of MD5 hash of the test certificate used to resign the AAB */
		hashMd5: Option[String] = None
	)
	
	case class GoogleLongrunningOperation(
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleFirebaseAppdistroV1DistributeReleaseRequest(
	  /** A list of tester email addresses to be given access to this release. A combined maximum of 999 `testerEmails` and `groupAliases` can be specified in a single request. */
		testerEmails: Option[List[String]] = None,
	  /** A list of group aliases (IDs) to be given access to this release. A combined maximum of 999 `testerEmails` and `groupAliases` can be specified in a single request. */
		groupAliases: Option[List[String]] = None
	)
	
	case class GoogleFirebaseAppdistroV1Release(
	  /** Output only. A link to the release in the tester web clip or Android app that lets testers (which were granted access to the app) view release notes and install the app onto their devices. */
		testingUri: Option[String] = None,
	  /** Output only. Display version of the release. For an Android release, the display version is the `versionName`. For an iOS release, the display version is the `CFBundleShortVersionString`. */
		displayVersion: Option[String] = None,
	  /** The name of the release resource. Format: `projects/{project_number}/apps/{app_id}/releases/{release_id}` */
		name: Option[String] = None,
	  /** Output only. The time the release was created. */
		createTime: Option[String] = None,
	  /** Output only. A link to the Firebase console displaying a single release. */
		firebaseConsoleUri: Option[String] = None,
	  /** Output only. A signed link (which expires in one hour) to directly download the app binary (IPA/APK/AAB) file. */
		binaryDownloadUri: Option[String] = None,
	  /** Output only. Build version of the release. For an Android release, the build version is the `versionCode`. For an iOS release, the build version is the `CFBundleVersion`. */
		buildVersion: Option[String] = None,
	  /** Notes of the release. */
		releaseNotes: Option[Schema.GoogleFirebaseAppdistroV1ReleaseNotes] = None
	)
	
	case class GoogleFirebaseAppdistroV1ListReleasesResponse(
	  /** A short-lived token, which can be sent as `pageToken` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The releases */
		releases: Option[List[Schema.GoogleFirebaseAppdistroV1Release]] = None
	)
	
	case class GdataDiffChecksumsResponse(
	  /** Exactly one of these fields must be populated. If checksums_location is filled, the server will return the corresponding contents to the user. If object_location is filled, the server will calculate the checksums based on the content there and return that to the user. For details on the format of the checksums, see http://go/scotty-diff-protocol. */
		checksumsLocation: Option[Schema.GdataCompositeMedia] = None,
	  /** The total size of the server object. */
		objectSizeBytes: Option[String] = None,
	  /** If set, calculate the checksums based on the contents and return them to the caller. */
		objectLocation: Option[Schema.GdataCompositeMedia] = None,
	  /** The chunk size of checksums. Must be a multiple of 256KB. */
		chunkSizeBytes: Option[String] = None,
	  /** The object version of the object the checksums are being returned for. */
		objectVersion: Option[String] = None
	)
	
	case class GoogleFirebaseAppdistroV1ListGroupsResponse(
	  /** The groups listed. */
		groups: Option[List[Schema.GoogleFirebaseAppdistroV1Group]] = None,
	  /** A short-lived token, which can be sent as `pageToken` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GdataObjectId(
	  /** The name of the bucket to which this object belongs. */
		bucketName: Option[String] = None,
	  /** The name of the object. */
		objectName: Option[String] = None,
	  /** Generation of the object. Generations are monotonically increasing across writes, allowing them to be be compared to determine which generation is newer. If this is omitted in a request, then you are requesting the live object. See http://go/bigstore-versions */
		generation: Option[String] = None
	)
	
	case class GoogleLongrunningWaitOperationRequest(
	  /** The maximum duration to wait before timing out. If left blank, the wait will be at most the time permitted by the underlying HTTP/RPC protocol. If RPC context deadline is also specified, the shorter one will be used. */
		timeout: Option[String] = None
	)
	
	case class GoogleFirebaseAppdistroV1FeedbackReport(
	  /** Output only. The resource name of the tester who submitted the feedback report. */
		tester: Option[String] = None,
	  /** Output only. The text of the feedback report. */
		text: Option[String] = None,
	  /** Output only. A signed link (which expires in one hour) that lets you directly download the screenshot. */
		screenshotUri: Option[String] = None,
	  /** Output only. A link to the Firebase console displaying the feedback report. */
		firebaseConsoleUri: Option[String] = None,
	  /** Output only. The time when the feedback report was created. */
		createTime: Option[String] = None,
	  /** The name of the feedback report resource. Format: `projects/{project_number}/apps/{app}/releases/{release}/feedbackReports/{feedback_report}` */
		name: Option[String] = None
	)
	
	case class GoogleFirebaseAppdistroV1ListFeedbackReportsResponse(
	  /** A short-lived token, which can be sent as `pageToken` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The feedback reports */
		feedbackReports: Option[List[Schema.GoogleFirebaseAppdistroV1FeedbackReport]] = None
	)
	
	object GdataMedia {
		enum ReferenceTypeEnum extends Enum[ReferenceTypeEnum] { case PATH, BLOB_REF, INLINE, GET_MEDIA, COMPOSITE_MEDIA, BIGSTORE_REF, DIFF_VERSION_RESPONSE, DIFF_CHECKSUMS_RESPONSE, DIFF_DOWNLOAD_RESPONSE, DIFF_UPLOAD_REQUEST, DIFF_UPLOAD_RESPONSE, COSMO_BINARY_REFERENCE, ARBITRARY_BYTES }
	}
	case class GdataMedia(
	  /** For Scotty Uploads: Scotty-provided hashes for uploads For Scotty Downloads: (WARNING: DO NOT USE WITHOUT PERMISSION FROM THE SCOTTY TEAM.) A Hash provided by the agent to be used to verify the data being downloaded. Currently only supported for inline payloads. Further, only crc32c_hash is currently supported. */
		crc32cHash: Option[Int] = None,
	  /** Describes what the field reference contains. */
		referenceType: Option[Schema.GdataMedia.ReferenceTypeEnum] = None,
	  /** MIME type of the data */
		contentType: Option[String] = None,
	  /** Size of the data, in bytes */
		length: Option[String] = None,
	  /** A composite media composed of one or more media objects, set if reference_type is COMPOSITE_MEDIA. The media length field must be set to the sum of the lengths of all composite media objects. Note: All composite media must have length specified. */
		compositeMedia: Option[List[Schema.GdataCompositeMedia]] = None,
	  /** Original file name */
		filename: Option[String] = None,
	  /** For Scotty uploads only. If a user sends a hash code and the backend has requested that Scotty verify the upload against the client hash, Scotty will perform the check on behalf of the backend and will reject it if the hashes don't match. This is set to true if Scotty performed this verification. */
		hashVerified: Option[Boolean] = None,
	  /** Blobstore v2 info, set if reference_type is BLOBSTORE_REF and it refers to a v2 blob. */
		blobstore2Info: Option[Schema.GdataBlobstore2Info] = None,
	  /** |is_potential_retry| is set false only when Scotty is certain that it has not sent the request before. When a client resumes an upload, this field must be set true in agent calls, because Scotty cannot be certain that it has never sent the request before due to potential failure in the session state persistence. */
		isPotentialRetry: Option[Boolean] = None,
	  /** Scotty-provided MD5 hash for an upload. */
		md5Hash: Option[String] = None,
	  /** Set if reference_type is DIFF_DOWNLOAD_RESPONSE. */
		diffDownloadResponse: Option[Schema.GdataDiffDownloadResponse] = None,
	  /** Scotty-provided SHA1 hash for an upload. */
		sha1Hash: Option[String] = None,
	  /** A unique fingerprint/version id for the media data */
		token: Option[String] = None,
	  /** Path to the data, set if reference_type is PATH */
		path: Option[String] = None,
	  /** Time at which the media data was last updated, in milliseconds since UNIX epoch */
		timestamp: Option[String] = None,
	  /** Media data, set if reference_type is INLINE */
		inline: Option[String] = None,
	  /** Set if reference_type is DIFF_UPLOAD_REQUEST. */
		diffUploadRequest: Option[Schema.GdataDiffUploadRequest] = None,
	  /** Deprecated, use one of explicit hash type fields instead. Algorithm used for calculating the hash. As of 2011/01/21, "MD5" is the only possible value for this field. New values may be added at any time. */
		algorithm: Option[String] = None,
	  /** A binary data reference for a media download. Serves as a technology-agnostic binary reference in some Google infrastructure. This value is a serialized storage_cosmo.BinaryReference proto. Storing it as bytes is a hack to get around the fact that the cosmo proto (as well as others it includes) doesn't support JavaScript. This prevents us from including the actual type of this field. */
		cosmoBinaryReference: Option[String] = None,
	  /** Set if reference_type is DIFF_CHECKSUMS_RESPONSE. */
		diffChecksumsResponse: Option[Schema.GdataDiffChecksumsResponse] = None,
	  /** Use object_id instead. */
		bigstoreObjectRef: Option[String] = None,
	  /** Parameters for a media download. */
		downloadParameters: Option[Schema.GdataDownloadParameters] = None,
	  /** Deprecated, use one of explicit hash type fields instead. These two hash related fields will only be populated on Scotty based media uploads and will contain the content of the hash group in the NotificationRequest: http://cs/#google3/blobstore2/api/scotty/service/proto/upload_listener.proto&q=class:Hash Hex encoded hash value of the uploaded media. */
		hash: Option[String] = None,
	  /** Extended content type information provided for Scotty uploads. */
		contentTypeInfo: Option[Schema.GdataContentTypeInfo] = None,
	  /** Blobstore v1 reference, set if reference_type is BLOBSTORE_REF This should be the byte representation of a blobstore.BlobRef. Since Blobstore is deprecating v1, use blobstore2_info instead. For now, any v2 blob will also be represented in this field as v1 BlobRef. */
		blobRef: Option[String] = None,
	  /** Media id to forward to the operation GetMedia. Can be set if reference_type is GET_MEDIA. */
		mediaId: Option[String] = None,
	  /** Set if reference_type is DIFF_VERSION_RESPONSE. */
		diffVersionResponse: Option[Schema.GdataDiffVersionResponse] = None,
	  /** Set if reference_type is DIFF_UPLOAD_RESPONSE. */
		diffUploadResponse: Option[Schema.GdataDiffUploadResponse] = None,
	  /** Reference to a TI Blob, set if reference_type is BIGSTORE_REF. */
		objectId: Option[Schema.GdataObjectId] = None,
	  /** Scotty-provided SHA256 hash for an upload. */
		sha256Hash: Option[String] = None
	)
	
	case class GoogleLongrunningCancelOperationRequest(
	
	)
	
	case class GoogleLongrunningListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.GoogleLongrunningOperation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class GdataDiffVersionResponse(
	  /** The total size of the server object. */
		objectSizeBytes: Option[String] = None,
	  /** The version of the object stored at the server. */
		objectVersion: Option[String] = None
	)
	
	object GdataCompositeMedia {
		enum ReferenceTypeEnum extends Enum[ReferenceTypeEnum] { case PATH, BLOB_REF, INLINE, BIGSTORE_REF, COSMO_BINARY_REFERENCE }
	}
	case class GdataCompositeMedia(
	  /** Describes what the field reference contains. */
		referenceType: Option[Schema.GdataCompositeMedia.ReferenceTypeEnum] = None,
	  /** Path to the data, set if reference_type is PATH */
		path: Option[String] = None,
	  /** Reference to a TI Blob, set if reference_type is BIGSTORE_REF. */
		objectId: Option[Schema.GdataObjectId] = None,
	  /** Blobstore v1 reference, set if reference_type is BLOBSTORE_REF This should be the byte representation of a blobstore.BlobRef. Since Blobstore is deprecating v1, use blobstore2_info instead. For now, any v2 blob will also be represented in this field as v1 BlobRef. */
		blobRef: Option[String] = None,
	  /** SHA-1 hash for the payload. */
		sha1Hash: Option[String] = None,
	  /** Blobstore v2 info, set if reference_type is BLOBSTORE_REF and it refers to a v2 blob. */
		blobstore2Info: Option[Schema.GdataBlobstore2Info] = None,
	  /** Media data, set if reference_type is INLINE */
		inline: Option[String] = None,
	  /** crc32.c hash for the payload. */
		crc32cHash: Option[Int] = None,
	  /** Size of the data, in bytes */
		length: Option[String] = None,
	  /** A binary data reference for a media download. Serves as a technology-agnostic binary reference in some Google infrastructure. This value is a serialized storage_cosmo.BinaryReference proto. Storing it as bytes is a hack to get around the fact that the cosmo proto (as well as others it includes) doesn't support JavaScript. This prevents us from including the actual type of this field. */
		cosmoBinaryReference: Option[String] = None,
	  /** MD5 hash for the payload. */
		md5Hash: Option[String] = None
	)
	
	object GoogleFirebaseAppdistroV1UploadReleaseResponse {
		enum ResultEnum extends Enum[ResultEnum] { case UPLOAD_RELEASE_RESULT_UNSPECIFIED, RELEASE_CREATED, RELEASE_UPDATED, RELEASE_UNMODIFIED }
	}
	case class GoogleFirebaseAppdistroV1UploadReleaseResponse(
	  /** Release associated with the uploaded binary. */
		release: Option[Schema.GoogleFirebaseAppdistroV1Release] = None,
	  /** Result of upload release. */
		result: Option[Schema.GoogleFirebaseAppdistroV1UploadReleaseResponse.ResultEnum] = None
	)
	
	case class GoogleFirebaseAppdistroV1UploadReleaseRequest(
	  /** Binary to upload */
		blob: Option[Schema.GdataMedia] = None
	)
	
	case class GoogleFirebaseAppdistroV1BatchRemoveTestersResponse(
	  /** List of deleted tester emails */
		emails: Option[List[String]] = None
	)
	
	case class GdataDiffUploadRequest(
	  /** The location of the new object. Agents must clone the object located here, as the upload server will delete the contents once a response is received. */
		objectInfo: Option[Schema.GdataCompositeMedia] = None,
	  /** The location of the checksums for the new object. Agents must clone the object located here, as the upload server will delete the contents once a response is received. For details on the format of the checksums, see http://go/scotty-diff-protocol. */
		checksumsInfo: Option[Schema.GdataCompositeMedia] = None,
	  /** The object version of the object that is the base version the incoming diff script will be applied to. This field will always be filled in. */
		objectVersion: Option[String] = None
	)
	
	case class GoogleFirebaseAppdistroV1Group(
	  /** Output only. The number of testers who are members of this group. */
		testerCount: Option[Int] = None,
	  /** Output only. The number of invite links for this group. */
		inviteLinkCount: Option[Int] = None,
	  /** Output only. The number of releases this group is permitted to access. */
		releaseCount: Option[Int] = None,
	  /** The name of the group resource. Format: `projects/{project_number}/groups/{group_alias}` */
		name: Option[String] = None,
	  /** Required. The display name of the group. */
		displayName: Option[String] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleFirebaseAppdistroV1BatchDeleteReleasesRequest(
	  /** Required. The names of the release resources to delete. Format: `projects/{project_number}/apps/{app_id}/releases/{release_id}` A maximum of 100 releases can be deleted per request. */
		names: Option[List[String]] = None
	)
	
	case class GdataBlobstore2Info(
	  /** The blob generation id. */
		blobGeneration: Option[String] = None,
	  /** Metadata passed from Blobstore -> Scotty for a new GCS upload. This is a signed, serialized blobstore2.BlobMetadataContainer proto which must never be consumed outside of Bigstore, and is not applicable to non-GCS media uploads. */
		uploadMetadataContainer: Option[String] = None,
	  /** The blob id, e.g., /blobstore/prod/playground/scotty */
		blobId: Option[String] = None,
	  /** Read handle passed from Bigstore -> Scotty for a GCS download. This is a signed, serialized blobstore2.ReadHandle proto which must never be set outside of Bigstore, and is not applicable to non-GCS media downloads. */
		downloadReadHandle: Option[String] = None,
	  /** The blob read token. Needed to read blobs that have not been replicated. Might not be available until the final call. */
		readToken: Option[String] = None
	)
	
	case class GdataContentTypeInfo(
	  /** The content type of the file derived by looking at specific bytes (i.e. "magic bytes") of the actual file. */
		fromBytes: Option[String] = None,
	  /** The content type of the file as specified in the request headers, multipart headers, or RUPIO start request. */
		fromHeader: Option[String] = None,
	  /** The content type of the file derived from the file extension of the URL path. The URL path is assumed to represent a file name (which is typically only true for agents that are providing a REST API). */
		fromUrlPath: Option[String] = None,
	  /** Scotty's best guess of what the content type of the file is. */
		bestGuess: Option[String] = None,
	  /** The content type of the file derived from the file extension of the original file name used by the client. */
		fromFileName: Option[String] = None
	)
	
	case class GoogleFirebaseAppdistroV1BatchRemoveTestersRequest(
	  /** Required. The email addresses of the tester resources to removed. A maximum of 999 and a minimum of 1 testers can be deleted in a batch. */
		emails: Option[List[String]] = None
	)
	
	case class GoogleFirebaseAppdistroV1BatchLeaveGroupRequest(
	  /** Required. The email addresses of the testers to be removed from the group. A maximum of 999 and a minimum of 1 testers can be removed in a batch. */
		emails: Option[List[String]] = None
	)
	
	case class GoogleFirebaseAppdistroV1Tester(
	  /** The resource names of the groups this tester belongs to. */
		groups: Option[List[String]] = None,
	  /** Output only. The time the tester was last active. This is the most recent time the tester installed one of the apps. If they've never installed one or if the release no longer exists, this is the time the tester was added to the project. */
		lastActivityTime: Option[String] = None,
	  /** The name of the tester associated with the Google account used to accept the tester invitation. */
		displayName: Option[String] = None,
	  /** The name of the tester resource. Format: `projects/{project_number}/testers/{email_address}` */
		name: Option[String] = None
	)
	
	case class GdataDiffDownloadResponse(
	  /** The original object location. */
		objectLocation: Option[Schema.GdataCompositeMedia] = None
	)
	
	object GoogleFirebaseAppdistroV1AabInfo {
		enum IntegrationStateEnum extends Enum[IntegrationStateEnum] { case AAB_INTEGRATION_STATE_UNSPECIFIED, INTEGRATED, PLAY_ACCOUNT_NOT_LINKED, NO_APP_WITH_GIVEN_BUNDLE_ID_IN_PLAY_ACCOUNT, APP_NOT_PUBLISHED, AAB_STATE_UNAVAILABLE, PLAY_IAS_TERMS_NOT_ACCEPTED }
	}
	case class GoogleFirebaseAppdistroV1AabInfo(
	  /** The name of the `AabInfo` resource. Format: `projects/{project_number}/apps/{app}/aabInfo` */
		name: Option[String] = None,
	  /** App bundle test certificate generated for the app. Set after the first app bundle is uploaded for this app. */
		testCertificate: Option[Schema.GoogleFirebaseAppdistroV1TestCertificate] = None,
	  /** App bundle integration state. Only valid for android apps. */
		integrationState: Option[Schema.GoogleFirebaseAppdistroV1AabInfo.IntegrationStateEnum] = None
	)
	
	case class GdataDiffUploadResponse(
	  /** The location of the original file for a diff upload request. Must be filled in if responding to an upload start notification. */
		originalObject: Option[Schema.GdataCompositeMedia] = None,
	  /** The object version of the object at the server. Must be included in the end notification response. The version in the end notification response must correspond to the new version of the object that is now stored at the server, after the upload. */
		objectVersion: Option[String] = None
	)
	
	case class GoogleFirebaseAppdistroV1ReleaseNotes(
	  /** The text of the release notes. */
		text: Option[String] = None
	)
}
