package com.boresjo.play.api.google.streetviewpublish

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq(
		"""https://www.googleapis.com/auth/streetviewpublish""" /* Publish and manage your 360 photos on Google Street View */
	)

	private val BASE_URL = "https://streetviewpublish.googleapis.com/"

	object photoSequence {
		/** Deletes a PhotoSequence and its metadata. This method returns the following error codes: &#42; google.rpc.Code.PERMISSION_DENIED if the requesting user did not create the requested photo sequence. &#42; google.rpc.Code.NOT_FOUND if the photo sequence ID does not exist. &#42; google.rpc.Code.FAILED_PRECONDITION if the photo sequence ID is not yet finished processing. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/streetviewpublish""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(sequenceId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/photoSequence/${sequenceId}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Gets the metadata of the specified PhotoSequence via the Operation interface. This method returns the following three types of responses: &#42; `Operation.done` = false, if the processing of PhotoSequence is not finished yet. &#42; `Operation.done` = true and `Operation.error` is populated, if there was an error in processing. &#42; `Operation.done` = true and `Operation.response` is poulated, which contains a PhotoSequence message. This method returns the following error codes: &#42; google.rpc.Code.PERMISSION_DENIED if the requesting user did not create the requested PhotoSequence. &#42; google.rpc.Code.NOT_FOUND if the requested PhotoSequence does not exist. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/streetviewpublish""")
			/** Optional. The filter expression. For example: `published_status=PUBLISHED`. The filters supported are: `published_status`. See https://google.aip.dev/160 for more information. */
			def withFilter(filter: String) = new get(req.addQueryStringParameters("filter" -> filter.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(sequenceId: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/photoSequence/${sequenceId}").addQueryStringParameters("view" -> view.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
		/** After the client finishes uploading the PhotoSequence with the returned UploadRef, CreatePhotoSequence extracts a sequence of 360 photos from a video or Extensible Device Metadata (XDM, http://www.xdm.org/) to be published to Street View on Google Maps. `CreatePhotoSequence` returns an Operation, with the PhotoSequence Id set in the `Operation.name` field. This method returns the following error codes: &#42; google.rpc.Code.INVALID_ARGUMENT if the request is malformed. &#42; google.rpc.Code.NOT_FOUND if the upload reference does not exist. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/streetviewpublish""")
			/** Perform the request */
			def withPhotoSequence(body: Schema.PhotoSequence) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply(inputType: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/photoSequence").addQueryStringParameters("inputType" -> inputType.toString))
		}
		/** Creates an upload session to start uploading photo sequence data. The upload URL of the returned UploadRef is used to upload the data for the `photoSequence`. After the upload is complete, the UploadRef is used with CreatePhotoSequence to create the PhotoSequence object entry. */
		class startUpload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/streetviewpublish""")
			/** Perform the request */
			def withEmpty(body: Schema.Empty) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UploadRef])
		}
		object startUpload {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): startUpload = new startUpload(ws.url(BASE_URL + s"v1/photoSequence:startUpload").addQueryStringParameters())
		}
	}
	object photoSequences {
		/** Lists all the PhotoSequences that belong to the user, in descending CreatePhotoSequence timestamp order. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPhotoSequencesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/streetviewpublish""")
			/** Optional. The filter expression. For example: `imagery_type=SPHERICAL`. The filters supported are: `imagery_type`, `processing_state`, `min_latitude`, `max_latitude`, `min_longitude`, `max_longitude`, `filename_query`, `min_capture_time_seconds`, `max_capture_time_seconds. See https://google.aip.dev/160 for more information. Filename queries should sent as a Phrase in order to support multiple words and special characters by adding escaped quotes. Ex: filename_query="example of a phrase.mp4" */
			def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
			/** Optional. The maximum number of photo sequences to return. `pageSize` must be non-negative. If `pageSize` is zero or is not provided, the default page size of 100 is used. The number of photo sequences returned in the response may be less than `pageSize` if the number of matches is less than `pageSize`. This is currently unimplemented but is in process.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. The nextPageToken value returned from a previous ListPhotoSequences request, if any. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPhotoSequencesResponse])
		}
		object list {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/photoSequences").addQueryStringParameters())
			given Conversion[list, Future[Schema.ListPhotoSequencesResponse]] = (fun: list) => fun.apply()
		}
	}
	object photos {
		/** Gets the metadata of the specified Photo batch. Note that if BatchGetPhotos fails, either critical fields are missing or there is an authentication error. Even if BatchGetPhotos succeeds, individual photos in the batch may have failures. These failures are specified in each PhotoResponse.status in BatchGetPhotosResponse.results. See GetPhoto for specific failures that can occur per photo. */
		class batchGet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BatchGetPhotosResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/streetviewpublish""")
			/** Optional. The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. If language_code is unspecified, the user's language preference for Google services is used. */
			def withLanguageCode(languageCode: String) = new batchGet(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BatchGetPhotosResponse])
		}
		object batchGet {
			def apply(view: String, photoIds: String)(using signer: RequestSigner, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"v1/photos:batchGet").addQueryStringParameters("view" -> view.toString, "photoIds" -> photoIds.toString))
			given Conversion[batchGet, Future[Schema.BatchGetPhotosResponse]] = (fun: batchGet) => fun.apply()
		}
		/** Updates the metadata of Photos, such as pose, place association, connections, etc. Changing the pixels of photos is not supported. Note that if BatchUpdatePhotos fails, either critical fields are missing or there is an authentication error. Even if BatchUpdatePhotos succeeds, individual photos in the batch may have failures. These failures are specified in each PhotoResponse.status in BatchUpdatePhotosResponse.results. See UpdatePhoto for specific failures that can occur per photo. Only the fields specified in updateMask field are used. If `updateMask` is not present, the update applies to all fields. The number of UpdatePhotoRequest messages in a BatchUpdatePhotosRequest must not exceed 20. > Note: To update Pose.altitude, Pose.latLngPair has to be filled as well. Otherwise, the request will fail. */
		class batchUpdate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/streetviewpublish""")
			/** Perform the request */
			def withBatchUpdatePhotosRequest(body: Schema.BatchUpdatePhotosRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUpdatePhotosResponse])
		}
		object batchUpdate {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"v1/photos:batchUpdate").addQueryStringParameters())
		}
		/** Deletes a list of Photos and their metadata. Note that if BatchDeletePhotos fails, either critical fields are missing or there is an authentication error. Even if BatchDeletePhotos succeeds, individual photos in the batch may have failures. These failures are specified in each PhotoResponse.status in BatchDeletePhotosResponse.results. See DeletePhoto for specific failures that can occur per photo. */
		class batchDelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/streetviewpublish""")
			/** Perform the request */
			def withBatchDeletePhotosRequest(body: Schema.BatchDeletePhotosRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchDeletePhotosResponse])
		}
		object batchDelete {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"v1/photos:batchDelete").addQueryStringParameters())
		}
		/** Lists all the Photos that belong to the user. > Note: Recently created photos that are still being indexed are not returned in the response. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPhotosResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/streetviewpublish""")
			/** Optional. The filter expression. For example: `placeId=ChIJj61dQgK6j4AR4GeTYWZsKWw`. The filters supported are: `placeId`, `min_latitude`, `max_latitude`, `min_longitude`, `max_longitude`. See https://google.aip.dev/160 for more information. */
			def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
			/** Optional. The maximum number of photos to return. `pageSize` must be non-negative. If `pageSize` is zero or is not provided, the default page size of 100 is used. The number of photos returned in the response may be less than `pageSize` if the number of photos that belong to the user is less than `pageSize`.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. If language_code is unspecified, the user's language preference for Google services is used. */
			def withLanguageCode(languageCode: String) = new list(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			/** Optional. The nextPageToken value returned from a previous ListPhotos request, if any. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPhotosResponse])
		}
		object list {
			def apply(view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/photos").addQueryStringParameters("view" -> view.toString))
			given Conversion[list, Future[Schema.ListPhotosResponse]] = (fun: list) => fun.apply()
		}
	}
	object photo {
		/** After the client finishes uploading the photo with the returned UploadRef, CreatePhoto publishes the uploaded Photo to Street View on Google Maps. Currently, the only way to set heading, pitch, and roll in CreatePhoto is through the [Photo Sphere XMP metadata](https://developers.google.com/streetview/spherical-metadata) in the photo bytes. CreatePhoto ignores the `pose.heading`, `pose.pitch`, `pose.roll`, `pose.altitude`, and `pose.level` fields in Pose. This method returns the following error codes: &#42; google.rpc.Code.INVALID_ARGUMENT if the request is malformed or if the uploaded photo is not a 360 photo. &#42; google.rpc.Code.NOT_FOUND if the upload reference does not exist. &#42; google.rpc.Code.RESOURCE_EXHAUSTED if the account has reached the storage limit. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/streetviewpublish""")
			/** Perform the request */
			def withPhoto(body: Schema.Photo) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Photo])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/photo").addQueryStringParameters())
		}
		/** Gets the metadata of the specified Photo. This method returns the following error codes: &#42; google.rpc.Code.PERMISSION_DENIED if the requesting user did not create the requested Photo. &#42; google.rpc.Code.NOT_FOUND if the requested Photo does not exist. &#42; google.rpc.Code.UNAVAILABLE if the requested Photo is still being indexed. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Photo]) {
			val scopes = Seq("""https://www.googleapis.com/auth/streetviewpublish""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Photo])
		}
		object get {
			def apply(view: String, languageCode: String, photoId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/photo/${photoId}").addQueryStringParameters("view" -> view.toString, "languageCode" -> languageCode.toString))
			given Conversion[get, Future[Schema.Photo]] = (fun: get) => fun.apply()
		}
		/** Updates the metadata of a Photo, such as pose, place association, connections, etc. Changing the pixels of a photo is not supported. Only the fields specified in the updateMask field are used. If `updateMask` is not present, the update applies to all fields. This method returns the following error codes: &#42; google.rpc.Code.PERMISSION_DENIED if the requesting user did not create the requested photo. &#42; google.rpc.Code.INVALID_ARGUMENT if the request is malformed. &#42; google.rpc.Code.NOT_FOUND if the requested photo does not exist. &#42; google.rpc.Code.UNAVAILABLE if the requested Photo is still being indexed. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/streetviewpublish""")
			/** Perform the request */
			def withPhoto(body: Schema.Photo) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Photo])
		}
		object update {
			def apply(updateMask: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/photo/${id}").addQueryStringParameters("updateMask" -> updateMask.toString))
		}
		/** Creates an upload session to start uploading photo bytes. The method uses the upload URL of the returned UploadRef to upload the bytes for the Photo. In addition to the photo requirements shown in https://support.google.com/maps/answer/7012050?ref_topic=6275604, the photo must meet the following requirements: &#42; Photo Sphere XMP metadata must be included in the photo metadata. See https://developers.google.com/streetview/spherical-metadata for the required fields. &#42; The pixel size of the photo must meet the size requirements listed in https://support.google.com/maps/answer/7012050?ref_topic=6275604, and the photo must be a full 360 horizontally. After the upload completes, the method uses UploadRef with CreatePhoto to create the Photo object entry. */
		class startUpload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/streetviewpublish""")
			/** Perform the request */
			def withEmpty(body: Schema.Empty) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UploadRef])
		}
		object startUpload {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): startUpload = new startUpload(ws.url(BASE_URL + s"v1/photo:startUpload").addQueryStringParameters())
		}
		/** Deletes a Photo and its metadata. This method returns the following error codes: &#42; google.rpc.Code.PERMISSION_DENIED if the requesting user did not create the requested photo. &#42; google.rpc.Code.NOT_FOUND if the photo ID does not exist. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/streetviewpublish""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(photoId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/photo/${photoId}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
	}
}
