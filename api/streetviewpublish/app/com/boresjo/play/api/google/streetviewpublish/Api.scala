package com.boresjo.play.api.google.streetviewpublish

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://streetviewpublish.googleapis.com/"

	object photoSequence {
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(sequenceId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/photoSequence/${sequenceId}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			/** Optional. The filter expression. For example: `published_status=PUBLISHED`. The filters supported are: `published_status`. See https://google.aip.dev/160 for more information. */
			def withFilter(filter: String) = new get(req.addQueryStringParameters("filter" -> filter.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(sequenceId: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/photoSequence/${sequenceId}").addQueryStringParameters("view" -> view.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPhotoSequence(body: Schema.PhotoSequence) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply(inputType: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/photoSequence").addQueryStringParameters("inputType" -> inputType.toString))
		}
		class startUpload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEmpty(body: Schema.Empty) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UploadRef])
		}
		object startUpload {
			def apply()(using auth: AuthToken, ec: ExecutionContext): startUpload = new startUpload(ws.url(BASE_URL + s"v1/photoSequence:startUpload").addQueryStringParameters())
		}
	}
	object photoSequences {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPhotoSequencesResponse]) {
			/** Optional. The filter expression. For example: `imagery_type=SPHERICAL`. The filters supported are: `imagery_type`, `processing_state`, `min_latitude`, `max_latitude`, `min_longitude`, `max_longitude`, `filename_query`, `min_capture_time_seconds`, `max_capture_time_seconds. See https://google.aip.dev/160 for more information. Filename queries should sent as a Phrase in order to support multiple words and special characters by adding escaped quotes. Ex: filename_query="example of a phrase.mp4" */
			def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
			/** Optional. The maximum number of photo sequences to return. `pageSize` must be non-negative. If `pageSize` is zero or is not provided, the default page size of 100 is used. The number of photo sequences returned in the response may be less than `pageSize` if the number of matches is less than `pageSize`. This is currently unimplemented but is in process.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. The nextPageToken value returned from a previous ListPhotoSequences request, if any. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListPhotoSequencesResponse])
		}
		object list {
			def apply()(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/photoSequences").addQueryStringParameters())
			given Conversion[list, Future[Schema.ListPhotoSequencesResponse]] = (fun: list) => fun.apply()
		}
	}
	object photos {
		class batchGet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BatchGetPhotosResponse]) {
			/** Optional. The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. If language_code is unspecified, the user's language preference for Google services is used. */
			def withLanguageCode(languageCode: String) = new batchGet(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.BatchGetPhotosResponse])
		}
		object batchGet {
			def apply(view: String, photoIds: String)(using auth: AuthToken, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"v1/photos:batchGet").addQueryStringParameters("view" -> view.toString, "photoIds" -> photoIds.toString))
			given Conversion[batchGet, Future[Schema.BatchGetPhotosResponse]] = (fun: batchGet) => fun.apply()
		}
		class batchUpdate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBatchUpdatePhotosRequest(body: Schema.BatchUpdatePhotosRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUpdatePhotosResponse])
		}
		object batchUpdate {
			def apply()(using auth: AuthToken, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"v1/photos:batchUpdate").addQueryStringParameters())
		}
		class batchDelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBatchDeletePhotosRequest(body: Schema.BatchDeletePhotosRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchDeletePhotosResponse])
		}
		object batchDelete {
			def apply()(using auth: AuthToken, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"v1/photos:batchDelete").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPhotosResponse]) {
			/** Optional. The filter expression. For example: `placeId=ChIJj61dQgK6j4AR4GeTYWZsKWw`. The filters supported are: `placeId`, `min_latitude`, `max_latitude`, `min_longitude`, `max_longitude`. See https://google.aip.dev/160 for more information. */
			def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
			/** Optional. The maximum number of photos to return. `pageSize` must be non-negative. If `pageSize` is zero or is not provided, the default page size of 100 is used. The number of photos returned in the response may be less than `pageSize` if the number of photos that belong to the user is less than `pageSize`.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. The BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. If language_code is unspecified, the user's language preference for Google services is used. */
			def withLanguageCode(languageCode: String) = new list(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			/** Optional. The nextPageToken value returned from a previous ListPhotos request, if any. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListPhotosResponse])
		}
		object list {
			def apply(view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/photos").addQueryStringParameters("view" -> view.toString))
			given Conversion[list, Future[Schema.ListPhotosResponse]] = (fun: list) => fun.apply()
		}
	}
	object photo {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPhoto(body: Schema.Photo) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Photo])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/photo").addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Photo]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Photo])
		}
		object get {
			def apply(view: String, languageCode: String, photoId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/photo/${photoId}").addQueryStringParameters("view" -> view.toString, "languageCode" -> languageCode.toString))
			given Conversion[get, Future[Schema.Photo]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPhoto(body: Schema.Photo) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Photo])
		}
		object update {
			def apply(updateMask: String, id: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/photo/${id}").addQueryStringParameters("updateMask" -> updateMask.toString))
		}
		class startUpload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEmpty(body: Schema.Empty) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UploadRef])
		}
		object startUpload {
			def apply()(using auth: AuthToken, ec: ExecutionContext): startUpload = new startUpload(ws.url(BASE_URL + s"v1/photo:startUpload").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(photoId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/photo/${photoId}").addQueryStringParameters())
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
	}
}
