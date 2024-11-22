package com.boresjo.play.api.google.books

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://books.googleapis.com/"

	object bookshelves {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Bookshelf]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Bookshelf])
		}
		object get {
			def apply(userId: String, shelf: String, source: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"books/v1/users/${userId}/bookshelves/${shelf}").addQueryStringParameters("source" -> source.toString))
			given Conversion[get, Future[Schema.Bookshelf]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Bookshelves]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Bookshelves])
		}
		object list {
			def apply(userId: String, source: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"books/v1/users/${userId}/bookshelves").addQueryStringParameters("source" -> source.toString))
			given Conversion[list, Future[Schema.Bookshelves]] = (fun: list) => fun.apply()
		}
		object volumes {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Volumes]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Volumes])
			}
			object list {
				def apply(userId: String, shelf: String, maxResults: Int, showPreorders: Boolean, source: String, startIndex: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"books/v1/users/${userId}/bookshelves/${shelf}/volumes").addQueryStringParameters("maxResults" -> maxResults.toString, "showPreorders" -> showPreorders.toString, "source" -> source.toString, "startIndex" -> startIndex.toString))
				given Conversion[list, Future[Schema.Volumes]] = (fun: list) => fun.apply()
			}
		}
	}
	object cloudloading {
		class addBook(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BooksCloudloadingResource]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.BooksCloudloadingResource])
		}
		object addBook {
			def apply(drive_document_id: String, mime_type: String, name: String, upload_client_token: String)(using auth: AuthToken, ec: ExecutionContext): addBook = new addBook(ws.url(BASE_URL + s"books/v1/cloudloading/addBook").addQueryStringParameters("drive_document_id" -> drive_document_id.toString, "mime_type" -> mime_type.toString, "name" -> name.toString, "upload_client_token" -> upload_client_token.toString))
			given Conversion[addBook, Future[Schema.BooksCloudloadingResource]] = (fun: addBook) => fun.apply()
		}
		class deleteBook(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object deleteBook {
			def apply(volumeId: String)(using auth: AuthToken, ec: ExecutionContext): deleteBook = new deleteBook(ws.url(BASE_URL + s"books/v1/cloudloading/deleteBook").addQueryStringParameters("volumeId" -> volumeId.toString))
			given Conversion[deleteBook, Future[Schema.Empty]] = (fun: deleteBook) => fun.apply()
		}
		class updateBook(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBooksCloudloadingResource(body: Schema.BooksCloudloadingResource) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BooksCloudloadingResource])
		}
		object updateBook {
			def apply()(using auth: AuthToken, ec: ExecutionContext): updateBook = new updateBook(ws.url(BASE_URL + s"books/v1/cloudloading/updateBook").addQueryStringParameters())
		}
	}
	object dictionary {
		class listOfflineMetadata(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Metadata]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Metadata])
		}
		object listOfflineMetadata {
			def apply(cpksver: String)(using auth: AuthToken, ec: ExecutionContext): listOfflineMetadata = new listOfflineMetadata(ws.url(BASE_URL + s"books/v1/dictionary/listOfflineMetadata").addQueryStringParameters("cpksver" -> cpksver.toString))
			given Conversion[listOfflineMetadata, Future[Schema.Metadata]] = (fun: listOfflineMetadata) => fun.apply()
		}
	}
	object familysharing {
		class getFamilyInfo(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FamilyInfo]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.FamilyInfo])
		}
		object getFamilyInfo {
			def apply(source: String)(using auth: AuthToken, ec: ExecutionContext): getFamilyInfo = new getFamilyInfo(ws.url(BASE_URL + s"books/v1/familysharing/getFamilyInfo").addQueryStringParameters("source" -> source.toString))
			given Conversion[getFamilyInfo, Future[Schema.FamilyInfo]] = (fun: getFamilyInfo) => fun.apply()
		}
		class share(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object share {
			def apply(docId: String, source: String, volumeId: String)(using auth: AuthToken, ec: ExecutionContext): share = new share(ws.url(BASE_URL + s"books/v1/familysharing/share").addQueryStringParameters("docId" -> docId.toString, "source" -> source.toString, "volumeId" -> volumeId.toString))
			given Conversion[share, Future[Schema.Empty]] = (fun: share) => fun.apply()
		}
		class unshare(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object unshare {
			def apply(docId: String, source: String, volumeId: String)(using auth: AuthToken, ec: ExecutionContext): unshare = new unshare(ws.url(BASE_URL + s"books/v1/familysharing/unshare").addQueryStringParameters("docId" -> docId.toString, "source" -> source.toString, "volumeId" -> volumeId.toString))
			given Conversion[unshare, Future[Schema.Empty]] = (fun: unshare) => fun.apply()
		}
	}
	object layers {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Layersummary]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Layersummary])
		}
		object get {
			def apply(volumeId: String, summaryId: String, contentVersion: String, source: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"books/v1/volumes/${volumeId}/layersummary/${summaryId}").addQueryStringParameters("contentVersion" -> contentVersion.toString, "source" -> source.toString))
			given Conversion[get, Future[Schema.Layersummary]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Layersummaries]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Layersummaries])
		}
		object list {
			def apply(volumeId: String, contentVersion: String, maxResults: Int, pageToken: String, source: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"books/v1/volumes/${volumeId}/layersummary").addQueryStringParameters("contentVersion" -> contentVersion.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "source" -> source.toString))
			given Conversion[list, Future[Schema.Layersummaries]] = (fun: list) => fun.apply()
		}
		object annotationData {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DictionaryAnnotationdata]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DictionaryAnnotationdata])
			}
			object get {
				def apply(volumeId: String, layerId: String, annotationDataId: String, allowWebDefinitions: Boolean, contentVersion: String, h: Int, locale: String, scale: Int, source: String, w: Int)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"books/v1/volumes/${volumeId}/layers/${layerId}/data/${annotationDataId}").addQueryStringParameters("allowWebDefinitions" -> allowWebDefinitions.toString, "contentVersion" -> contentVersion.toString, "h" -> h.toString, "locale" -> locale.toString, "scale" -> scale.toString, "source" -> source.toString, "w" -> w.toString))
				given Conversion[get, Future[Schema.DictionaryAnnotationdata]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Annotationsdata]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Annotationsdata])
			}
			object list {
				def apply(volumeId: String, layerId: String, annotationDataId: String, contentVersion: String, h: Int, locale: String, maxResults: Int, pageToken: String, scale: Int, source: String, updatedMax: String, updatedMin: String, w: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"books/v1/volumes/${volumeId}/layers/${layerId}/data").addQueryStringParameters("annotationDataId" -> annotationDataId.toString, "contentVersion" -> contentVersion.toString, "h" -> h.toString, "locale" -> locale.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "scale" -> scale.toString, "source" -> source.toString, "updatedMax" -> updatedMax.toString, "updatedMin" -> updatedMin.toString, "w" -> w.toString))
				given Conversion[list, Future[Schema.Annotationsdata]] = (fun: list) => fun.apply()
			}
		}
		object volumeAnnotations {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Volumeannotation]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Volumeannotation])
			}
			object get {
				def apply(volumeId: String, layerId: String, annotationId: String, locale: String, source: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"books/v1/volumes/${volumeId}/layers/${layerId}/annotations/${annotationId}").addQueryStringParameters("locale" -> locale.toString, "source" -> source.toString))
				given Conversion[get, Future[Schema.Volumeannotation]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Volumeannotations]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Volumeannotations])
			}
			object list {
				def apply(volumeId: String, layerId: String, contentVersion: String, endOffset: String, endPosition: String, locale: String, maxResults: Int, pageToken: String, showDeleted: Boolean, source: String, startOffset: String, startPosition: String, updatedMax: String, updatedMin: String, volumeAnnotationsVersion: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"books/v1/volumes/${volumeId}/layers/${layerId}").addQueryStringParameters("contentVersion" -> contentVersion.toString, "endOffset" -> endOffset.toString, "endPosition" -> endPosition.toString, "locale" -> locale.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "showDeleted" -> showDeleted.toString, "source" -> source.toString, "startOffset" -> startOffset.toString, "startPosition" -> startPosition.toString, "updatedMax" -> updatedMax.toString, "updatedMin" -> updatedMin.toString, "volumeAnnotationsVersion" -> volumeAnnotationsVersion.toString))
				given Conversion[list, Future[Schema.Volumeannotations]] = (fun: list) => fun.apply()
			}
		}
	}
	object myconfig {
		class syncVolumeLicenses(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Volumes]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Volumes])
		}
		object syncVolumeLicenses {
			def apply(cpksver: String, features: String, includeNonComicsSeries: Boolean, locale: String, nonce: String, showPreorders: Boolean, source: String, volumeIds: String)(using auth: AuthToken, ec: ExecutionContext): syncVolumeLicenses = new syncVolumeLicenses(ws.url(BASE_URL + s"books/v1/myconfig/syncVolumeLicenses").addQueryStringParameters("cpksver" -> cpksver.toString, "features" -> features.toString, "includeNonComicsSeries" -> includeNonComicsSeries.toString, "locale" -> locale.toString, "nonce" -> nonce.toString, "showPreorders" -> showPreorders.toString, "source" -> source.toString, "volumeIds" -> volumeIds.toString))
			given Conversion[syncVolumeLicenses, Future[Schema.Volumes]] = (fun: syncVolumeLicenses) => fun.apply()
		}
		class requestAccess(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RequestAccessData]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.RequestAccessData])
		}
		object requestAccess {
			def apply(cpksver: String, licenseTypes: String, locale: String, nonce: String, source: String, volumeId: String)(using auth: AuthToken, ec: ExecutionContext): requestAccess = new requestAccess(ws.url(BASE_URL + s"books/v1/myconfig/requestAccess").addQueryStringParameters("cpksver" -> cpksver.toString, "licenseTypes" -> licenseTypes.toString, "locale" -> locale.toString, "nonce" -> nonce.toString, "source" -> source.toString, "volumeId" -> volumeId.toString))
			given Conversion[requestAccess, Future[Schema.RequestAccessData]] = (fun: requestAccess) => fun.apply()
		}
		class updateUserSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withUsersettings(body: Schema.Usersettings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Usersettings])
		}
		object updateUserSettings {
			def apply()(using auth: AuthToken, ec: ExecutionContext): updateUserSettings = new updateUserSettings(ws.url(BASE_URL + s"books/v1/myconfig/updateUserSettings").addQueryStringParameters())
		}
		class releaseDownloadAccess(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DownloadAccesses]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.DownloadAccesses])
		}
		object releaseDownloadAccess {
			def apply(cpksver: String, locale: String, source: String, volumeIds: String)(using auth: AuthToken, ec: ExecutionContext): releaseDownloadAccess = new releaseDownloadAccess(ws.url(BASE_URL + s"books/v1/myconfig/releaseDownloadAccess").addQueryStringParameters("cpksver" -> cpksver.toString, "locale" -> locale.toString, "source" -> source.toString, "volumeIds" -> volumeIds.toString))
			given Conversion[releaseDownloadAccess, Future[Schema.DownloadAccesses]] = (fun: releaseDownloadAccess) => fun.apply()
		}
		class getUserSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Usersettings]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Usersettings])
		}
		object getUserSettings {
			def apply(country: String)(using auth: AuthToken, ec: ExecutionContext): getUserSettings = new getUserSettings(ws.url(BASE_URL + s"books/v1/myconfig/getUserSettings").addQueryStringParameters("country" -> country.toString))
			given Conversion[getUserSettings, Future[Schema.Usersettings]] = (fun: getUserSettings) => fun.apply()
		}
	}
	object mylibrary {
		object annotations {
			class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAnnotation(body: Schema.Annotation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Annotation])
			}
			object insert {
				def apply(annotationId: String, country: String, showOnlySummaryInResponse: Boolean, source: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"books/v1/mylibrary/annotations").addQueryStringParameters("annotationId" -> annotationId.toString, "country" -> country.toString, "showOnlySummaryInResponse" -> showOnlySummaryInResponse.toString, "source" -> source.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(annotationId: String, source: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"books/v1/mylibrary/annotations/${annotationId}").addQueryStringParameters("source" -> source.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAnnotation(body: Schema.Annotation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Annotation])
			}
			object update {
				def apply(annotationId: String, source: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"books/v1/mylibrary/annotations/${annotationId}").addQueryStringParameters("source" -> source.toString))
			}
			class summary(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AnnotationsSummary]) {
				/** Optional. String to identify the originator of this request. */
				def withSource(source: String) = new summary(req.addQueryStringParameters("source" -> source.toString))
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.AnnotationsSummary])
			}
			object summary {
				def apply(layerIds: String, volumeId: String)(using auth: AuthToken, ec: ExecutionContext): summary = new summary(ws.url(BASE_URL + s"books/v1/mylibrary/annotations/summary").addQueryStringParameters("layerIds" -> layerIds.toString, "volumeId" -> volumeId.toString))
				given Conversion[summary, Future[Schema.AnnotationsSummary]] = (fun: summary) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Annotations]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Annotations])
			}
			object list {
				def apply(contentVersion: String, layerId: String, layerIds: String, maxResults: Int, pageToken: String, showDeleted: Boolean, source: String, updatedMax: String, updatedMin: String, volumeId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"books/v1/mylibrary/annotations").addQueryStringParameters("contentVersion" -> contentVersion.toString, "layerId" -> layerId.toString, "layerIds" -> layerIds.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "showDeleted" -> showDeleted.toString, "source" -> source.toString, "updatedMax" -> updatedMax.toString, "updatedMin" -> updatedMin.toString, "volumeId" -> volumeId.toString))
				given Conversion[list, Future[Schema.Annotations]] = (fun: list) => fun.apply()
			}
		}
		object bookshelves {
			class clearVolumes(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object clearVolumes {
				def apply(shelf: String, source: String)(using auth: AuthToken, ec: ExecutionContext): clearVolumes = new clearVolumes(ws.url(BASE_URL + s"books/v1/mylibrary/bookshelves/${shelf}/clearVolumes").addQueryStringParameters("source" -> source.toString))
				given Conversion[clearVolumes, Future[Schema.Empty]] = (fun: clearVolumes) => fun.apply()
			}
			class removeVolume(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object removeVolume {
				def apply(shelf: String, reason: String, source: String, volumeId: String)(using auth: AuthToken, ec: ExecutionContext): removeVolume = new removeVolume(ws.url(BASE_URL + s"books/v1/mylibrary/bookshelves/${shelf}/removeVolume").addQueryStringParameters("reason" -> reason.toString, "source" -> source.toString, "volumeId" -> volumeId.toString))
				given Conversion[removeVolume, Future[Schema.Empty]] = (fun: removeVolume) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Bookshelf]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Bookshelf])
			}
			object get {
				def apply(shelf: String, source: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"books/v1/mylibrary/bookshelves/${shelf}").addQueryStringParameters("source" -> source.toString))
				given Conversion[get, Future[Schema.Bookshelf]] = (fun: get) => fun.apply()
			}
			class addVolume(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object addVolume {
				def apply(shelf: String, reason: String, source: String, volumeId: String)(using auth: AuthToken, ec: ExecutionContext): addVolume = new addVolume(ws.url(BASE_URL + s"books/v1/mylibrary/bookshelves/${shelf}/addVolume").addQueryStringParameters("reason" -> reason.toString, "source" -> source.toString, "volumeId" -> volumeId.toString))
				given Conversion[addVolume, Future[Schema.Empty]] = (fun: addVolume) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Bookshelves]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Bookshelves])
			}
			object list {
				def apply(source: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"books/v1/mylibrary/bookshelves").addQueryStringParameters("source" -> source.toString))
				given Conversion[list, Future[Schema.Bookshelves]] = (fun: list) => fun.apply()
			}
			class moveVolume(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object moveVolume {
				def apply(shelf: String, source: String, volumeId: String, volumePosition: Int)(using auth: AuthToken, ec: ExecutionContext): moveVolume = new moveVolume(ws.url(BASE_URL + s"books/v1/mylibrary/bookshelves/${shelf}/moveVolume").addQueryStringParameters("source" -> source.toString, "volumeId" -> volumeId.toString, "volumePosition" -> volumePosition.toString))
				given Conversion[moveVolume, Future[Schema.Empty]] = (fun: moveVolume) => fun.apply()
			}
			object volumes {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Volumes]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Volumes])
				}
				object list {
					def apply(shelf: String, country: String, maxResults: Int, projection: String, q: String, showPreorders: Boolean, source: String, startIndex: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"books/v1/mylibrary/bookshelves/${shelf}/volumes").addQueryStringParameters("country" -> country.toString, "maxResults" -> maxResults.toString, "projection" -> projection.toString, "q" -> q.toString, "showPreorders" -> showPreorders.toString, "source" -> source.toString, "startIndex" -> startIndex.toString))
					given Conversion[list, Future[Schema.Volumes]] = (fun: list) => fun.apply()
				}
			}
		}
		object readingpositions {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ReadingPosition]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ReadingPosition])
			}
			object get {
				def apply(volumeId: String, contentVersion: String, source: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"books/v1/mylibrary/readingpositions/${volumeId}").addQueryStringParameters("contentVersion" -> contentVersion.toString, "source" -> source.toString))
				given Conversion[get, Future[Schema.ReadingPosition]] = (fun: get) => fun.apply()
			}
			class setPosition(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				/** Random persistent device cookie optional on set position. */
				def withDeviceCookie(deviceCookie: String) = new setPosition(req.addQueryStringParameters("deviceCookie" -> deviceCookie.toString))
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object setPosition {
				def apply(volumeId: String, action: String, contentVersion: String, position: String, source: String, timestamp: String)(using auth: AuthToken, ec: ExecutionContext): setPosition = new setPosition(ws.url(BASE_URL + s"books/v1/mylibrary/readingpositions/${volumeId}/setPosition").addQueryStringParameters("action" -> action.toString, "contentVersion" -> contentVersion.toString, "position" -> position.toString, "source" -> source.toString, "timestamp" -> timestamp.toString))
				given Conversion[setPosition, Future[Schema.Empty]] = (fun: setPosition) => fun.apply()
			}
		}
	}
	object notification {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Notification]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Notification])
		}
		object get {
			def apply(locale: String, notification_id: String, source: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"books/v1/notification/get").addQueryStringParameters("locale" -> locale.toString, "notification_id" -> notification_id.toString, "source" -> source.toString))
			given Conversion[get, Future[Schema.Notification]] = (fun: get) => fun.apply()
		}
	}
	object onboarding {
		class listCategories(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Category]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Category])
		}
		object listCategories {
			def apply(locale: String)(using auth: AuthToken, ec: ExecutionContext): listCategories = new listCategories(ws.url(BASE_URL + s"books/v1/onboarding/listCategories").addQueryStringParameters("locale" -> locale.toString))
			given Conversion[listCategories, Future[Schema.Category]] = (fun: listCategories) => fun.apply()
		}
		class listCategoryVolumes(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Volume2]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Volume2])
		}
		object listCategoryVolumes {
			def apply(categoryId: String, locale: String, maxAllowedMaturityRating: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): listCategoryVolumes = new listCategoryVolumes(ws.url(BASE_URL + s"books/v1/onboarding/listCategoryVolumes").addQueryStringParameters("categoryId" -> categoryId.toString, "locale" -> locale.toString, "maxAllowedMaturityRating" -> maxAllowedMaturityRating.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[listCategoryVolumes, Future[Schema.Volume2]] = (fun: listCategoryVolumes) => fun.apply()
		}
	}
	object personalizedstream {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Discoveryclusters]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Discoveryclusters])
		}
		object get {
			def apply(locale: String, maxAllowedMaturityRating: String, source: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"books/v1/personalizedstream/get").addQueryStringParameters("locale" -> locale.toString, "maxAllowedMaturityRating" -> maxAllowedMaturityRating.toString, "source" -> source.toString))
			given Conversion[get, Future[Schema.Discoveryclusters]] = (fun: get) => fun.apply()
		}
	}
	object promooffer {
		class accept(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object accept {
			def apply(androidId: String, device: String, manufacturer: String, model: String, offerId: String, product: String, serial: String, volumeId: String)(using auth: AuthToken, ec: ExecutionContext): accept = new accept(ws.url(BASE_URL + s"books/v1/promooffer/accept").addQueryStringParameters("androidId" -> androidId.toString, "device" -> device.toString, "manufacturer" -> manufacturer.toString, "model" -> model.toString, "offerId" -> offerId.toString, "product" -> product.toString, "serial" -> serial.toString, "volumeId" -> volumeId.toString))
			given Conversion[accept, Future[Schema.Empty]] = (fun: accept) => fun.apply()
		}
		class dismiss(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object dismiss {
			def apply(androidId: String, device: String, manufacturer: String, model: String, offerId: String, product: String, serial: String)(using auth: AuthToken, ec: ExecutionContext): dismiss = new dismiss(ws.url(BASE_URL + s"books/v1/promooffer/dismiss").addQueryStringParameters("androidId" -> androidId.toString, "device" -> device.toString, "manufacturer" -> manufacturer.toString, "model" -> model.toString, "offerId" -> offerId.toString, "product" -> product.toString, "serial" -> serial.toString))
			given Conversion[dismiss, Future[Schema.Empty]] = (fun: dismiss) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Offers]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Offers])
		}
		object get {
			def apply(androidId: String, device: String, manufacturer: String, model: String, product: String, serial: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"books/v1/promooffer/get").addQueryStringParameters("androidId" -> androidId.toString, "device" -> device.toString, "manufacturer" -> manufacturer.toString, "model" -> model.toString, "product" -> product.toString, "serial" -> serial.toString))
			given Conversion[get, Future[Schema.Offers]] = (fun: get) => fun.apply()
		}
	}
	object series {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Series]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Series])
		}
		object get {
			def apply(series_id: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"books/v1/series/get").addQueryStringParameters("series_id" -> series_id.toString))
			given Conversion[get, Future[Schema.Series]] = (fun: get) => fun.apply()
		}
		object membership {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Seriesmembership]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Seriesmembership])
			}
			object get {
				def apply(page_size: Int, page_token: String, series_id: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"books/v1/series/membership/get").addQueryStringParameters("page_size" -> page_size.toString, "page_token" -> page_token.toString, "series_id" -> series_id.toString))
				given Conversion[get, Future[Schema.Seriesmembership]] = (fun: get) => fun.apply()
			}
		}
	}
	object volumes {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Volume]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Volume])
		}
		object get {
			def apply(volumeId: String, country: String, includeNonComicsSeries: Boolean, partner: String, projection: String, source: String, user_library_consistent_read: Boolean)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"books/v1/volumes/${volumeId}").addQueryStringParameters("country" -> country.toString, "includeNonComicsSeries" -> includeNonComicsSeries.toString, "partner" -> partner.toString, "projection" -> projection.toString, "source" -> source.toString, "user_library_consistent_read" -> user_library_consistent_read.toString))
			given Conversion[get, Future[Schema.Volume]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Volumes]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Volumes])
		}
		object list {
			def apply(download: String, filter: String, langRestrict: String, libraryRestrict: String, maxAllowedMaturityRating: String, maxResults: Int, orderBy: String, partner: String, printType: String, projection: String, q: String, showPreorders: Boolean, source: String, startIndex: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"books/v1/volumes").addQueryStringParameters("download" -> download.toString, "filter" -> filter.toString, "langRestrict" -> langRestrict.toString, "libraryRestrict" -> libraryRestrict.toString, "maxAllowedMaturityRating" -> maxAllowedMaturityRating.toString, "maxResults" -> maxResults.toString, "orderBy" -> orderBy.toString, "partner" -> partner.toString, "printType" -> printType.toString, "projection" -> projection.toString, "q" -> q.toString, "showPreorders" -> showPreorders.toString, "source" -> source.toString, "startIndex" -> startIndex.toString))
			given Conversion[list, Future[Schema.Volumes]] = (fun: list) => fun.apply()
		}
		object associated {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Volumes]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Volumes])
			}
			object list {
				def apply(volumeId: String, association: String, locale: String, maxAllowedMaturityRating: String, source: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"books/v1/volumes/${volumeId}/associated").addQueryStringParameters("association" -> association.toString, "locale" -> locale.toString, "maxAllowedMaturityRating" -> maxAllowedMaturityRating.toString, "source" -> source.toString))
				given Conversion[list, Future[Schema.Volumes]] = (fun: list) => fun.apply()
			}
		}
		object useruploaded {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Volumes]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Volumes])
			}
			object list {
				def apply(locale: String, maxResults: Int, processingState: String, source: String, startIndex: Int, volumeId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"books/v1/volumes/useruploaded").addQueryStringParameters("locale" -> locale.toString, "maxResults" -> maxResults.toString, "processingState" -> processingState.toString, "source" -> source.toString, "startIndex" -> startIndex.toString, "volumeId" -> volumeId.toString))
				given Conversion[list, Future[Schema.Volumes]] = (fun: list) => fun.apply()
			}
		}
		object mybooks {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Volumes]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Volumes])
			}
			object list {
				def apply(acquireMethod: String, country: String, locale: String, maxResults: Int, processingState: String, source: String, startIndex: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"books/v1/volumes/mybooks").addQueryStringParameters("acquireMethod" -> acquireMethod.toString, "country" -> country.toString, "locale" -> locale.toString, "maxResults" -> maxResults.toString, "processingState" -> processingState.toString, "source" -> source.toString, "startIndex" -> startIndex.toString))
				given Conversion[list, Future[Schema.Volumes]] = (fun: list) => fun.apply()
			}
		}
		object recommended {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Volumes]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Volumes])
			}
			object list {
				def apply(locale: String, maxAllowedMaturityRating: String, source: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"books/v1/volumes/recommended").addQueryStringParameters("locale" -> locale.toString, "maxAllowedMaturityRating" -> maxAllowedMaturityRating.toString, "source" -> source.toString))
				given Conversion[list, Future[Schema.Volumes]] = (fun: list) => fun.apply()
			}
			class rate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BooksVolumesRecommendedRateResponse]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.BooksVolumesRecommendedRateResponse])
			}
			object rate {
				def apply(locale: String, rating: String, source: String, volumeId: String)(using auth: AuthToken, ec: ExecutionContext): rate = new rate(ws.url(BASE_URL + s"books/v1/volumes/recommended/rate").addQueryStringParameters("locale" -> locale.toString, "rating" -> rating.toString, "source" -> source.toString, "volumeId" -> volumeId.toString))
				given Conversion[rate, Future[Schema.BooksVolumesRecommendedRateResponse]] = (fun: rate) => fun.apply()
			}
		}
	}
}
