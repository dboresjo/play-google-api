package com.boresjo.play.api.google.drive

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
		"""https://www.googleapis.com/auth/drive""" /* See, edit, create, and delete all of your Google Drive files */,
		"""https://www.googleapis.com/auth/drive.appdata""" /* See, create, and delete its own configuration data in your Google Drive */,
		"""https://www.googleapis.com/auth/drive.apps.readonly""" /* View your Google Drive apps */,
		"""https://www.googleapis.com/auth/drive.file""" /* See, edit, create, and delete only the specific Google Drive files you use with this app */,
		"""https://www.googleapis.com/auth/drive.meet.readonly""" /* See and download your Google Drive files that were created or edited by Google Meet. */,
		"""https://www.googleapis.com/auth/drive.metadata""" /* View and manage metadata of files in your Google Drive */,
		"""https://www.googleapis.com/auth/drive.metadata.readonly""" /* See information about your Google Drive files */,
		"""https://www.googleapis.com/auth/drive.photos.readonly""" /* View the photos, videos and albums in your Google Photos */,
		"""https://www.googleapis.com/auth/drive.readonly""" /* See and download all your Google Drive files */,
		"""https://www.googleapis.com/auth/drive.scripts""" /* Modify your Google Apps Script scripts' behavior */
	)

	private val BASE_URL = "https://www.googleapis.com/drive/v3/"

	object operations {
		/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
		}
		object list {
			def apply(name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
		}
		/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.meet.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"operations/${name}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
	}
	object operation {
		/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"operation/${name}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object cancel {
			def apply(name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"operation/${name}:cancel").addQueryStringParameters())
			given Conversion[cancel, Future[Unit]] = (fun: cancel) => fun.apply()
		}
	}
	object about {
		/** Gets information about the user, the user's Drive, and system capabilities. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.About]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.appdata""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.photos.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.About])
		}
		object get {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"about").addQueryStringParameters())
			given Conversion[get, Future[Schema.About]] = (fun: get) => fun.apply()
		}
	}
	object apps {
		/** Gets a specific app. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.App]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.appdata""", """https://www.googleapis.com/auth/drive.apps.readonly""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.App])
		}
		object get {
			def apply(appId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"apps/${appId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.App]] = (fun: get) => fun.apply()
		}
		/** Lists a user's installed apps. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AppList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive.apps.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AppList])
		}
		object list {
			def apply(appFilterExtensions: String, appFilterMimeTypes: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"apps").addQueryStringParameters("appFilterExtensions" -> appFilterExtensions.toString, "appFilterMimeTypes" -> appFilterMimeTypes.toString, "languageCode" -> languageCode.toString))
			given Conversion[list, Future[Schema.AppList]] = (fun: list) => fun.apply()
		}
	}
	object changes {
		/** Gets the starting pageToken for listing future changes. */
		class getStartPageToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.StartPageToken]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.appdata""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.meet.readonly""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.photos.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.StartPageToken])
		}
		object getStartPageToken {
			def apply(driveId: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, teamDriveId: String)(using signer: RequestSigner, ec: ExecutionContext): getStartPageToken = new getStartPageToken(ws.url(BASE_URL + s"changes/startPageToken").addQueryStringParameters("driveId" -> driveId.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "teamDriveId" -> teamDriveId.toString))
			given Conversion[getStartPageToken, Future[Schema.StartPageToken]] = (fun: getStartPageToken) => fun.apply()
		}
		/** Lists the changes for a user or shared drive. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ChangeList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.appdata""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.meet.readonly""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.photos.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ChangeList])
		}
		object list {
			def apply(driveId: String, includeCorpusRemovals: Boolean, includeItemsFromAllDrives: Boolean, includeRemoved: Boolean, includeTeamDriveItems: Boolean, pageSize: Int, pageToken: String, restrictToMyDrive: Boolean, spaces: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, teamDriveId: String, includePermissionsForView: String, includeLabels: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"changes").addQueryStringParameters("driveId" -> driveId.toString, "includeCorpusRemovals" -> includeCorpusRemovals.toString, "includeItemsFromAllDrives" -> includeItemsFromAllDrives.toString, "includeRemoved" -> includeRemoved.toString, "includeTeamDriveItems" -> includeTeamDriveItems.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "restrictToMyDrive" -> restrictToMyDrive.toString, "spaces" -> spaces.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "teamDriveId" -> teamDriveId.toString, "includePermissionsForView" -> includePermissionsForView.toString, "includeLabels" -> includeLabels.toString))
			given Conversion[list, Future[Schema.ChangeList]] = (fun: list) => fun.apply()
		}
		/** Subscribes to changes for a user. */
		class watch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.appdata""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.meet.readonly""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.photos.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def withChannel(body: Schema.Channel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Channel])
		}
		object watch {
			def apply(driveId: String, includeCorpusRemovals: Boolean, includeItemsFromAllDrives: Boolean, includeRemoved: Boolean, includeTeamDriveItems: Boolean, pageSize: Int, pageToken: String, restrictToMyDrive: Boolean, spaces: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, teamDriveId: String, includePermissionsForView: String, includeLabels: String)(using signer: RequestSigner, ec: ExecutionContext): watch = new watch(ws.url(BASE_URL + s"changes/watch").addQueryStringParameters("driveId" -> driveId.toString, "includeCorpusRemovals" -> includeCorpusRemovals.toString, "includeItemsFromAllDrives" -> includeItemsFromAllDrives.toString, "includeRemoved" -> includeRemoved.toString, "includeTeamDriveItems" -> includeTeamDriveItems.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "restrictToMyDrive" -> restrictToMyDrive.toString, "spaces" -> spaces.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "teamDriveId" -> teamDriveId.toString, "includePermissionsForView" -> includePermissionsForView.toString, "includeLabels" -> includeLabels.toString))
		}
	}
	object channels {
		/** Stops watching resources through this channel. */
		class stop(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.appdata""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.meet.readonly""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.photos.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def withChannel(body: Schema.Channel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object stop {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"channels/stop").addQueryStringParameters())
		}
	}
	object comments {
		/** Creates a comment on a file. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""")
			/** Perform the request */
			def withComment(body: Schema.Comment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Comment])
		}
		object create {
			def apply(fileId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"files/${fileId}/comments").addQueryStringParameters())
		}
		/** Deletes a comment. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(fileId: String, commentId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"files/${fileId}/comments/${commentId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Gets a comment by ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Comment]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.meet.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Comment])
		}
		object get {
			def apply(fileId: String, commentId: String, includeDeleted: Boolean)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"files/${fileId}/comments/${commentId}").addQueryStringParameters("includeDeleted" -> includeDeleted.toString))
			given Conversion[get, Future[Schema.Comment]] = (fun: get) => fun.apply()
		}
		/** Updates a comment with patch semantics. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""")
			/** Perform the request */
			def withComment(body: Schema.Comment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Comment])
		}
		object update {
			def apply(fileId: String, commentId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"files/${fileId}/comments/${commentId}").addQueryStringParameters())
		}
		/** Lists a file's comments. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CommentList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.meet.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CommentList])
		}
		object list {
			def apply(fileId: String, includeDeleted: Boolean, pageSize: Int, pageToken: String, startModifiedTime: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"files/${fileId}/comments").addQueryStringParameters("includeDeleted" -> includeDeleted.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "startModifiedTime" -> startModifiedTime.toString))
			given Conversion[list, Future[Schema.CommentList]] = (fun: list) => fun.apply()
		}
	}
	object drives {
		/** Creates a shared drive. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""")
			/** Perform the request */
			def withDrive(body: Schema.Drive) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Drive])
		}
		object create {
			def apply(requestId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"drives").addQueryStringParameters("requestId" -> requestId.toString))
		}
		/** Hides a shared drive from the default view. */
		class hide(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Drive]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Drive])
		}
		object hide {
			def apply(driveId: String)(using signer: RequestSigner, ec: ExecutionContext): hide = new hide(ws.url(BASE_URL + s"drives/${driveId}/hide").addQueryStringParameters())
			given Conversion[hide, Future[Schema.Drive]] = (fun: hide) => fun.apply()
		}
		/** Permanently deletes a shared drive for which the user is an `organizer`. The shared drive cannot contain any untrashed items. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(driveId: String, useDomainAdminAccess: Boolean, allowItemDeletion: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"drives/${driveId}").addQueryStringParameters("useDomainAdminAccess" -> useDomainAdminAccess.toString, "allowItemDeletion" -> allowItemDeletion.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Gets a shared drive's metadata by ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Drive]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Drive])
		}
		object get {
			def apply(driveId: String, useDomainAdminAccess: Boolean)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"drives/${driveId}").addQueryStringParameters("useDomainAdminAccess" -> useDomainAdminAccess.toString))
			given Conversion[get, Future[Schema.Drive]] = (fun: get) => fun.apply()
		}
		/** Updates the metadata for a shared drive. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""")
			/** Perform the request */
			def withDrive(body: Schema.Drive) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Drive])
		}
		object update {
			def apply(driveId: String, useDomainAdminAccess: Boolean)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"drives/${driveId}").addQueryStringParameters("useDomainAdminAccess" -> useDomainAdminAccess.toString))
		}
		/** Restores a shared drive to the default view. */
		class unhide(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Drive]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Drive])
		}
		object unhide {
			def apply(driveId: String)(using signer: RequestSigner, ec: ExecutionContext): unhide = new unhide(ws.url(BASE_URL + s"drives/${driveId}/unhide").addQueryStringParameters())
			given Conversion[unhide, Future[Schema.Drive]] = (fun: unhide) => fun.apply()
		}
		/**  Lists the user's shared drives. This method accepts the `q` parameter, which is a search query combining one or more search terms. For more information, see the [Search for shared drives](/drive/api/guides/search-shareddrives) guide. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DriveList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DriveList])
		}
		object list {
			def apply(pageSize: Int, pageToken: String, q: String, useDomainAdminAccess: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"drives").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "q" -> q.toString, "useDomainAdminAccess" -> useDomainAdminAccess.toString))
			given Conversion[list, Future[Schema.DriveList]] = (fun: list) => fun.apply()
		}
	}
	object files {
		/** Generates a set of file IDs which can be provided in create or copy requests. */
		class generateIds(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GeneratedIds]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.appdata""", """https://www.googleapis.com/auth/drive.file""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GeneratedIds])
		}
		object generateIds {
			def apply(count: Int, space: String, `type`: String)(using signer: RequestSigner, ec: ExecutionContext): generateIds = new generateIds(ws.url(BASE_URL + s"files/generateIds").addQueryStringParameters("count" -> count.toString, "space" -> space.toString, "type" -> `type`.toString))
			given Conversion[generateIds, Future[Schema.GeneratedIds]] = (fun: generateIds) => fun.apply()
		}
		/** Exports a Google Workspace document to the requested MIME type and returns exported byte content. Note that the exported content is limited to 10MB. */
		class `export`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.meet.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_ => ())
		}
		object `export` {
			def apply(fileId: String, mimeType: String)(using signer: RequestSigner, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"files/${fileId}/export").addQueryStringParameters("mimeType" -> mimeType.toString))
			given Conversion[`export`, Future[Unit]] = (fun: `export`) => fun.apply()
		}
		/** Permanently deletes a file owned by the user without moving it to the trash. If the file belongs to a shared drive, the user must be an `organizer` on the parent folder. If the target is a folder, all descendants owned by the user are also deleted. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.appdata""", """https://www.googleapis.com/auth/drive.file""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(fileId: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, enforceSingleParent: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"files/${fileId}").addQueryStringParameters("supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "enforceSingleParent" -> enforceSingleParent.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/**  Gets a file's metadata or content by ID. If you provide the URL parameter `alt=media`, then the response includes the file contents in the response body. Downloading content with `alt=media` only works if the file is stored in Drive. To download Google Docs, Sheets, and Slides use [`files.export`](/drive/api/reference/rest/v3/files/export) instead. For more information, see [Download & export files](/drive/api/guides/manage-downloads). */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.File]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.appdata""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.meet.readonly""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.photos.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.File])
		}
		object get {
			def apply(fileId: String, acknowledgeAbuse: Boolean, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, includePermissionsForView: String, includeLabels: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"files/${fileId}").addQueryStringParameters("acknowledgeAbuse" -> acknowledgeAbuse.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "includePermissionsForView" -> includePermissionsForView.toString, "includeLabels" -> includeLabels.toString))
			given Conversion[get, Future[Schema.File]] = (fun: get) => fun.apply()
		}
		/**  Updates a file's metadata and/or content. When calling this method, only populate fields in the request that you want to modify. When updating fields, some fields might be changed automatically, such as `modifiedDate`. This method supports patch semantics. This method supports an &#42;/upload&#42; URI and accepts uploaded media with the following characteristics: - &#42;Maximum file size:&#42; 5,120 GB - &#42;Accepted Media MIME types:&#42;`&#42;/&#42;` Note: Specify a valid MIME type, rather than the literal `&#42;/&#42;` value. The literal `&#42;/&#42;` is only used to indicate that any valid MIME type can be uploaded. For more information on uploading files, see [Upload file data](/drive/api/guides/manage-uploads). */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.appdata""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.scripts""")
			/** Perform the request */
			def withFile(body: Schema.File) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.File])
		}
		object update {
			def apply(fileId: String, addParents: String, enforceSingleParent: Boolean, keepRevisionForever: Boolean, ocrLanguage: String, removeParents: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, useContentAsIndexableText: Boolean, includePermissionsForView: String, includeLabels: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"files/${fileId}").addQueryStringParameters("addParents" -> addParents.toString, "enforceSingleParent" -> enforceSingleParent.toString, "keepRevisionForever" -> keepRevisionForever.toString, "ocrLanguage" -> ocrLanguage.toString, "removeParents" -> removeParents.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "useContentAsIndexableText" -> useContentAsIndexableText.toString, "includePermissionsForView" -> includePermissionsForView.toString, "includeLabels" -> includeLabels.toString))
		}
		/** Downloads content of a file. Operations are valid for 24 hours from the time of creation. */
		class download(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Optional. The MIME type the file should be downloaded as. This field can only be set when downloading Google Workspace documents. See [Export MIME types for Google Workspace documents](/drive/api/guides/ref-export-formats) for the list of supported MIME types. If not set, a Google Workspace document is downloaded with a default MIME type. The default MIME type might change in the future. */
			def withMimeType(mimeType: String) = new download(req.addQueryStringParameters("mimeType" -> mimeType.toString))
			/** Optional. The revision ID of the file to download. This field can only be set when downloading blob files, Google Docs, and Google Sheets. Returns `INVALID_ARGUMENT` if downloading a specific revision on the file is unsupported. */
			def withRevisionId(revisionId: String) = new download(req.addQueryStringParameters("revisionId" -> revisionId.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object download {
			def apply(fileId: String)(using signer: RequestSigner, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"files/${fileId}/download").addQueryStringParameters())
			given Conversion[download, Future[Schema.Operation]] = (fun: download) => fun.apply()
		}
		/**  Lists the user's files. This method accepts the `q` parameter, which is a search query combining one or more search terms. For more information, see the [Search for files & folders](/drive/api/guides/search-files) guide. &#42;Note:&#42; This method returns &#42;all&#42; files by default, including trashed files. If you don't want trashed files to appear in the list, use the `trashed=false` query parameter to remove trashed files from the results. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FileList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.appdata""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.meet.readonly""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.photos.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FileList])
		}
		object list {
			def apply(corpora: String, corpus: String, driveId: String, includeItemsFromAllDrives: Boolean, includeTeamDriveItems: Boolean, orderBy: String, pageSize: Int, pageToken: String, q: String, spaces: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, teamDriveId: String, includePermissionsForView: String, includeLabels: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"files").addQueryStringParameters("corpora" -> corpora.toString, "corpus" -> corpus.toString, "driveId" -> driveId.toString, "includeItemsFromAllDrives" -> includeItemsFromAllDrives.toString, "includeTeamDriveItems" -> includeTeamDriveItems.toString, "orderBy" -> orderBy.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "q" -> q.toString, "spaces" -> spaces.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "teamDriveId" -> teamDriveId.toString, "includePermissionsForView" -> includePermissionsForView.toString, "includeLabels" -> includeLabels.toString))
			given Conversion[list, Future[Schema.FileList]] = (fun: list) => fun.apply()
		}
		/** Modifies the set of labels applied to a file. Returns a list of the labels that were added or modified. */
		class modifyLabels(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.metadata""")
			/** Perform the request */
			def withModifyLabelsRequest(body: Schema.ModifyLabelsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ModifyLabelsResponse])
		}
		object modifyLabels {
			def apply(fileId: String)(using signer: RequestSigner, ec: ExecutionContext): modifyLabels = new modifyLabels(ws.url(BASE_URL + s"files/${fileId}/modifyLabels").addQueryStringParameters())
		}
		/** Lists the labels on a file. */
		class listLabels(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LabelList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.meet.readonly""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LabelList])
		}
		object listLabels {
			def apply(fileId: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): listLabels = new listLabels(ws.url(BASE_URL + s"files/${fileId}/listLabels").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[listLabels, Future[Schema.LabelList]] = (fun: listLabels) => fun.apply()
		}
		/** Subscribes to changes to a file. */
		class watch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.appdata""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.meet.readonly""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.photos.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def withChannel(body: Schema.Channel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Channel])
		}
		object watch {
			def apply(fileId: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, acknowledgeAbuse: Boolean, includePermissionsForView: String, includeLabels: String)(using signer: RequestSigner, ec: ExecutionContext): watch = new watch(ws.url(BASE_URL + s"files/${fileId}/watch").addQueryStringParameters("supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "acknowledgeAbuse" -> acknowledgeAbuse.toString, "includePermissionsForView" -> includePermissionsForView.toString, "includeLabels" -> includeLabels.toString))
		}
		/**  Creates a new file. This method supports an &#42;/upload&#42; URI and accepts uploaded media with the following characteristics: - &#42;Maximum file size:&#42; 5,120 GB - &#42;Accepted Media MIME types:&#42;`&#42;/&#42;` Note: Specify a valid MIME type, rather than the literal `&#42;/&#42;` value. The literal `&#42;/&#42;` is only used to indicate that any valid MIME type can be uploaded. For more information on uploading files, see [Upload file data](/drive/api/guides/manage-uploads). Apps creating shortcuts with `files.create` must specify the MIME type `application/vnd.google-apps.shortcut`. Apps should specify a file extension in the `name` property when inserting files with the API. For example, an operation to insert a JPEG file should specify something like `"name": "cat.jpg"` in the metadata. Subsequent `GET` requests include the read-only `fileExtension` property populated with the extension originally specified in the `title` property. When a Google Drive user requests to download a file, or when the file is downloaded through the sync client, Drive builds a full filename (with extension) based on the title. In cases where the extension is missing, Drive attempts to determine the extension based on the file's MIME type. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.appdata""", """https://www.googleapis.com/auth/drive.file""")
			/** Perform the request */
			def withFile(body: Schema.File) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.File])
		}
		object create {
			def apply(enforceSingleParent: Boolean, ignoreDefaultVisibility: Boolean, keepRevisionForever: Boolean, ocrLanguage: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, useContentAsIndexableText: Boolean, includePermissionsForView: String, includeLabels: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"files").addQueryStringParameters("enforceSingleParent" -> enforceSingleParent.toString, "ignoreDefaultVisibility" -> ignoreDefaultVisibility.toString, "keepRevisionForever" -> keepRevisionForever.toString, "ocrLanguage" -> ocrLanguage.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "useContentAsIndexableText" -> useContentAsIndexableText.toString, "includePermissionsForView" -> includePermissionsForView.toString, "includeLabels" -> includeLabels.toString))
		}
		/** Permanently deletes all of the user's trashed files. */
		class emptyTrash(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object emptyTrash {
			def apply(enforceSingleParent: Boolean, driveId: String)(using signer: RequestSigner, ec: ExecutionContext): emptyTrash = new emptyTrash(ws.url(BASE_URL + s"files/trash").addQueryStringParameters("enforceSingleParent" -> enforceSingleParent.toString, "driveId" -> driveId.toString))
			given Conversion[emptyTrash, Future[Unit]] = (fun: emptyTrash) => fun.apply()
		}
		/** Creates a copy of a file and applies any requested updates with patch semantics. */
		class copy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.appdata""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.photos.readonly""")
			/** Perform the request */
			def withFile(body: Schema.File) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.File])
		}
		object copy {
			def apply(fileId: String, enforceSingleParent: Boolean, ignoreDefaultVisibility: Boolean, keepRevisionForever: Boolean, ocrLanguage: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, includePermissionsForView: String, includeLabels: String)(using signer: RequestSigner, ec: ExecutionContext): copy = new copy(ws.url(BASE_URL + s"files/${fileId}/copy").addQueryStringParameters("enforceSingleParent" -> enforceSingleParent.toString, "ignoreDefaultVisibility" -> ignoreDefaultVisibility.toString, "keepRevisionForever" -> keepRevisionForever.toString, "ocrLanguage" -> ocrLanguage.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "includePermissionsForView" -> includePermissionsForView.toString, "includeLabels" -> includeLabels.toString))
		}
	}
	object permissions {
		/** Creates a permission for a file or shared drive. &#42;&#42;Warning:&#42;&#42; Concurrent permissions operations on the same file are not supported; only the last update is applied. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""")
			/** Perform the request */
			def withPermission(body: Schema.Permission) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Permission])
		}
		object create {
			def apply(fileId: String, emailMessage: String, enforceSingleParent: Boolean, moveToNewOwnersRoot: Boolean, sendNotificationEmail: Boolean, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, transferOwnership: Boolean, useDomainAdminAccess: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"files/${fileId}/permissions").addQueryStringParameters("emailMessage" -> emailMessage.toString, "enforceSingleParent" -> enforceSingleParent.toString, "moveToNewOwnersRoot" -> moveToNewOwnersRoot.toString, "sendNotificationEmail" -> sendNotificationEmail.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "transferOwnership" -> transferOwnership.toString, "useDomainAdminAccess" -> useDomainAdminAccess.toString))
		}
		/** Deletes a permission. &#42;&#42;Warning:&#42;&#42; Concurrent permissions operations on the same file are not supported; only the last update is applied. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(fileId: String, permissionId: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, useDomainAdminAccess: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"files/${fileId}/permissions/${permissionId}").addQueryStringParameters("supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "useDomainAdminAccess" -> useDomainAdminAccess.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Gets a permission by ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Permission]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.meet.readonly""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.photos.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Permission])
		}
		object get {
			def apply(fileId: String, permissionId: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, useDomainAdminAccess: Boolean)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"files/${fileId}/permissions/${permissionId}").addQueryStringParameters("supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "useDomainAdminAccess" -> useDomainAdminAccess.toString))
			given Conversion[get, Future[Schema.Permission]] = (fun: get) => fun.apply()
		}
		/** Updates a permission with patch semantics. &#42;&#42;Warning:&#42;&#42; Concurrent permissions operations on the same file are not supported; only the last update is applied. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""")
			/** Perform the request */
			def withPermission(body: Schema.Permission) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Permission])
		}
		object update {
			def apply(fileId: String, permissionId: String, removeExpiration: Boolean, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, transferOwnership: Boolean, useDomainAdminAccess: Boolean)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"files/${fileId}/permissions/${permissionId}").addQueryStringParameters("removeExpiration" -> removeExpiration.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "transferOwnership" -> transferOwnership.toString, "useDomainAdminAccess" -> useDomainAdminAccess.toString))
		}
		/** Lists a file's or shared drive's permissions. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PermissionList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.meet.readonly""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.photos.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PermissionList])
		}
		object list {
			def apply(fileId: String, pageSize: Int, pageToken: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, useDomainAdminAccess: Boolean, includePermissionsForView: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"files/${fileId}/permissions").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "useDomainAdminAccess" -> useDomainAdminAccess.toString, "includePermissionsForView" -> includePermissionsForView.toString))
			given Conversion[list, Future[Schema.PermissionList]] = (fun: list) => fun.apply()
		}
	}
	object replies {
		/** Creates a reply to a comment. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""")
			/** Perform the request */
			def withReply(body: Schema.Reply) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Reply])
		}
		object create {
			def apply(fileId: String, commentId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"files/${fileId}/comments/${commentId}/replies").addQueryStringParameters())
		}
		/** Deletes a reply. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(fileId: String, commentId: String, replyId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"files/${fileId}/comments/${commentId}/replies/${replyId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Gets a reply by ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Reply]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.meet.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Reply])
		}
		object get {
			def apply(fileId: String, commentId: String, replyId: String, includeDeleted: Boolean)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"files/${fileId}/comments/${commentId}/replies/${replyId}").addQueryStringParameters("includeDeleted" -> includeDeleted.toString))
			given Conversion[get, Future[Schema.Reply]] = (fun: get) => fun.apply()
		}
		/** Updates a reply with patch semantics. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""")
			/** Perform the request */
			def withReply(body: Schema.Reply) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Reply])
		}
		object update {
			def apply(fileId: String, commentId: String, replyId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"files/${fileId}/comments/${commentId}/replies/${replyId}").addQueryStringParameters())
		}
		/** Lists a comment's replies. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ReplyList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.meet.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ReplyList])
		}
		object list {
			def apply(fileId: String, commentId: String, includeDeleted: Boolean, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"files/${fileId}/comments/${commentId}/replies").addQueryStringParameters("includeDeleted" -> includeDeleted.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ReplyList]] = (fun: list) => fun.apply()
		}
	}
	object revisions {
		/** Permanently deletes a file version. You can only delete revisions for files with binary content in Google Drive, like images or videos. Revisions for other files, like Google Docs or Sheets, and the last remaining file version can't be deleted. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.appdata""", """https://www.googleapis.com/auth/drive.file""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(fileId: String, revisionId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"files/${fileId}/revisions/${revisionId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Gets a revision's metadata or content by ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Revision]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.appdata""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.meet.readonly""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.photos.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Revision])
		}
		object get {
			def apply(fileId: String, revisionId: String, acknowledgeAbuse: Boolean)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"files/${fileId}/revisions/${revisionId}").addQueryStringParameters("acknowledgeAbuse" -> acknowledgeAbuse.toString))
			given Conversion[get, Future[Schema.Revision]] = (fun: get) => fun.apply()
		}
		/** Lists a file's revisions. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RevisionList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.appdata""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.meet.readonly""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.photos.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.RevisionList])
		}
		object list {
			def apply(fileId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"files/${fileId}/revisions").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.RevisionList]] = (fun: list) => fun.apply()
		}
		/** Updates a revision with patch semantics. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.appdata""", """https://www.googleapis.com/auth/drive.file""")
			/** Perform the request */
			def withRevision(body: Schema.Revision) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Revision])
		}
		object update {
			def apply(fileId: String, revisionId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"files/${fileId}/revisions/${revisionId}").addQueryStringParameters())
		}
	}
	object teamdrives {
		/** Deprecated: Use `drives.create` instead. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""")
			/** Perform the request */
			def withTeamDrive(body: Schema.TeamDrive) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TeamDrive])
		}
		object create {
			def apply(requestId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"teamdrives").addQueryStringParameters("requestId" -> requestId.toString))
		}
		/** Deprecated: Use `drives.delete` instead. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(teamDriveId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"teamdrives/${teamDriveId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Deprecated: Use `drives.get` instead. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TeamDrive]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TeamDrive])
		}
		object get {
			def apply(teamDriveId: String, useDomainAdminAccess: Boolean)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"teamdrives/${teamDriveId}").addQueryStringParameters("useDomainAdminAccess" -> useDomainAdminAccess.toString))
			given Conversion[get, Future[Schema.TeamDrive]] = (fun: get) => fun.apply()
		}
		/** Deprecated: Use `drives.update` instead. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""")
			/** Perform the request */
			def withTeamDrive(body: Schema.TeamDrive) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.TeamDrive])
		}
		object update {
			def apply(teamDriveId: String, useDomainAdminAccess: Boolean)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"teamdrives/${teamDriveId}").addQueryStringParameters("useDomainAdminAccess" -> useDomainAdminAccess.toString))
		}
		/** Deprecated: Use `drives.list` instead. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TeamDriveList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TeamDriveList])
		}
		object list {
			def apply(pageSize: Int, pageToken: String, q: String, useDomainAdminAccess: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"teamdrives").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "q" -> q.toString, "useDomainAdminAccess" -> useDomainAdminAccess.toString))
			given Conversion[list, Future[Schema.TeamDriveList]] = (fun: list) => fun.apply()
		}
	}
	object accessproposals {
		/** Retrieves an AccessProposal by ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AccessProposal]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AccessProposal])
		}
		object get {
			def apply(fileId: String, proposalId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"files/${fileId}/accessproposals/${proposalId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.AccessProposal]] = (fun: get) => fun.apply()
		}
		/** Used to approve or deny an Access Proposal. */
		class resolve(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""")
			/** Perform the request */
			def withResolveAccessProposalRequest(body: Schema.ResolveAccessProposalRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object resolve {
			def apply(fileId: String, proposalId: String)(using signer: RequestSigner, ec: ExecutionContext): resolve = new resolve(ws.url(BASE_URL + s"files/${fileId}/accessproposals/${proposalId}:resolve").addQueryStringParameters())
		}
		/** List the AccessProposals on a file. Note: Only approvers are able to list AccessProposals on a file. If the user is not an approver, returns a 403. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAccessProposalsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.readonly""")
			/** Optional. The continuation token on the list of access requests. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. The number of results per page<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAccessProposalsResponse])
		}
		object list {
			def apply(fileId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"files/${fileId}/accessproposals").addQueryStringParameters())
			given Conversion[list, Future[Schema.ListAccessProposalsResponse]] = (fun: list) => fun.apply()
		}
	}
}
