package com.boresjo.play.api.google.drive

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://www.googleapis.com/drive/v3/"

	object operations {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
		}
		object list {
			def apply(name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"operations/${name}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
	}
	object operation {
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"operation/${name}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_ => ())
		}
		object cancel {
			def apply(name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"operation/${name}:cancel").addQueryStringParameters())
			given Conversion[cancel, Future[Unit]] = (fun: cancel) => fun.apply()
		}
	}
	object about {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.About]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.About])
		}
		object get {
			def apply()(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"about").addQueryStringParameters())
			given Conversion[get, Future[Schema.About]] = (fun: get) => fun.apply()
		}
	}
	object apps {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.App]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.App])
		}
		object get {
			def apply(appId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"apps/${appId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.App]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AppList]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AppList])
		}
		object list {
			def apply(appFilterExtensions: String, appFilterMimeTypes: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"apps").addQueryStringParameters("appFilterExtensions" -> appFilterExtensions.toString, "appFilterMimeTypes" -> appFilterMimeTypes.toString, "languageCode" -> languageCode.toString))
			given Conversion[list, Future[Schema.AppList]] = (fun: list) => fun.apply()
		}
	}
	object changes {
		class getStartPageToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.StartPageToken]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.StartPageToken])
		}
		object getStartPageToken {
			def apply(driveId: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, teamDriveId: String)(using auth: AuthToken, ec: ExecutionContext): getStartPageToken = new getStartPageToken(ws.url(BASE_URL + s"changes/startPageToken").addQueryStringParameters("driveId" -> driveId.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "teamDriveId" -> teamDriveId.toString))
			given Conversion[getStartPageToken, Future[Schema.StartPageToken]] = (fun: getStartPageToken) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ChangeList]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ChangeList])
		}
		object list {
			def apply(driveId: String, includeCorpusRemovals: Boolean, includeItemsFromAllDrives: Boolean, includeRemoved: Boolean, includeTeamDriveItems: Boolean, pageSize: Int, pageToken: String, restrictToMyDrive: Boolean, spaces: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, teamDriveId: String, includePermissionsForView: String, includeLabels: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"changes").addQueryStringParameters("driveId" -> driveId.toString, "includeCorpusRemovals" -> includeCorpusRemovals.toString, "includeItemsFromAllDrives" -> includeItemsFromAllDrives.toString, "includeRemoved" -> includeRemoved.toString, "includeTeamDriveItems" -> includeTeamDriveItems.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "restrictToMyDrive" -> restrictToMyDrive.toString, "spaces" -> spaces.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "teamDriveId" -> teamDriveId.toString, "includePermissionsForView" -> includePermissionsForView.toString, "includeLabels" -> includeLabels.toString))
			given Conversion[list, Future[Schema.ChangeList]] = (fun: list) => fun.apply()
		}
		class watch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withChannel(body: Schema.Channel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Channel])
		}
		object watch {
			def apply(driveId: String, includeCorpusRemovals: Boolean, includeItemsFromAllDrives: Boolean, includeRemoved: Boolean, includeTeamDriveItems: Boolean, pageSize: Int, pageToken: String, restrictToMyDrive: Boolean, spaces: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, teamDriveId: String, includePermissionsForView: String, includeLabels: String)(using auth: AuthToken, ec: ExecutionContext): watch = new watch(ws.url(BASE_URL + s"changes/watch").addQueryStringParameters("driveId" -> driveId.toString, "includeCorpusRemovals" -> includeCorpusRemovals.toString, "includeItemsFromAllDrives" -> includeItemsFromAllDrives.toString, "includeRemoved" -> includeRemoved.toString, "includeTeamDriveItems" -> includeTeamDriveItems.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "restrictToMyDrive" -> restrictToMyDrive.toString, "spaces" -> spaces.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "teamDriveId" -> teamDriveId.toString, "includePermissionsForView" -> includePermissionsForView.toString, "includeLabels" -> includeLabels.toString))
		}
	}
	object channels {
		class stop(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withChannel(body: Schema.Channel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object stop {
			def apply()(using auth: AuthToken, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"channels/stop").addQueryStringParameters())
		}
	}
	object comments {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withComment(body: Schema.Comment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Comment])
		}
		object create {
			def apply(fileId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"files/${fileId}/comments").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(fileId: String, commentId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"files/${fileId}/comments/${commentId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Comment]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Comment])
		}
		object get {
			def apply(fileId: String, commentId: String, includeDeleted: Boolean)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"files/${fileId}/comments/${commentId}").addQueryStringParameters("includeDeleted" -> includeDeleted.toString))
			given Conversion[get, Future[Schema.Comment]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withComment(body: Schema.Comment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Comment])
		}
		object update {
			def apply(fileId: String, commentId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"files/${fileId}/comments/${commentId}").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CommentList]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CommentList])
		}
		object list {
			def apply(fileId: String, includeDeleted: Boolean, pageSize: Int, pageToken: String, startModifiedTime: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"files/${fileId}/comments").addQueryStringParameters("includeDeleted" -> includeDeleted.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "startModifiedTime" -> startModifiedTime.toString))
			given Conversion[list, Future[Schema.CommentList]] = (fun: list) => fun.apply()
		}
	}
	object drives {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDrive(body: Schema.Drive) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Drive])
		}
		object create {
			def apply(requestId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"drives").addQueryStringParameters("requestId" -> requestId.toString))
		}
		class hide(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Drive]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Drive])
		}
		object hide {
			def apply(driveId: String)(using auth: AuthToken, ec: ExecutionContext): hide = new hide(ws.url(BASE_URL + s"drives/${driveId}/hide").addQueryStringParameters())
			given Conversion[hide, Future[Schema.Drive]] = (fun: hide) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(driveId: String, useDomainAdminAccess: Boolean, allowItemDeletion: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"drives/${driveId}").addQueryStringParameters("useDomainAdminAccess" -> useDomainAdminAccess.toString, "allowItemDeletion" -> allowItemDeletion.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Drive]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Drive])
		}
		object get {
			def apply(driveId: String, useDomainAdminAccess: Boolean)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"drives/${driveId}").addQueryStringParameters("useDomainAdminAccess" -> useDomainAdminAccess.toString))
			given Conversion[get, Future[Schema.Drive]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDrive(body: Schema.Drive) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Drive])
		}
		object update {
			def apply(driveId: String, useDomainAdminAccess: Boolean)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"drives/${driveId}").addQueryStringParameters("useDomainAdminAccess" -> useDomainAdminAccess.toString))
		}
		class unhide(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Drive]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Drive])
		}
		object unhide {
			def apply(driveId: String)(using auth: AuthToken, ec: ExecutionContext): unhide = new unhide(ws.url(BASE_URL + s"drives/${driveId}/unhide").addQueryStringParameters())
			given Conversion[unhide, Future[Schema.Drive]] = (fun: unhide) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DriveList]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.DriveList])
		}
		object list {
			def apply(pageSize: Int, pageToken: String, q: String, useDomainAdminAccess: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"drives").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "q" -> q.toString, "useDomainAdminAccess" -> useDomainAdminAccess.toString))
			given Conversion[list, Future[Schema.DriveList]] = (fun: list) => fun.apply()
		}
	}
	object files {
		class generateIds(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GeneratedIds]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GeneratedIds])
		}
		object generateIds {
			def apply(count: Int, space: String, `type`: String)(using auth: AuthToken, ec: ExecutionContext): generateIds = new generateIds(ws.url(BASE_URL + s"files/generateIds").addQueryStringParameters("count" -> count.toString, "space" -> space.toString, "type" -> `type`.toString))
			given Conversion[generateIds, Future[Schema.GeneratedIds]] = (fun: generateIds) => fun.apply()
		}
		class `export`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_ => ())
		}
		object `export` {
			def apply(fileId: String, mimeType: String)(using auth: AuthToken, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"files/${fileId}/export").addQueryStringParameters("mimeType" -> mimeType.toString))
			given Conversion[`export`, Future[Unit]] = (fun: `export`) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(fileId: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, enforceSingleParent: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"files/${fileId}").addQueryStringParameters("supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "enforceSingleParent" -> enforceSingleParent.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.File]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.File])
		}
		object get {
			def apply(fileId: String, acknowledgeAbuse: Boolean, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, includePermissionsForView: String, includeLabels: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"files/${fileId}").addQueryStringParameters("acknowledgeAbuse" -> acknowledgeAbuse.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "includePermissionsForView" -> includePermissionsForView.toString, "includeLabels" -> includeLabels.toString))
			given Conversion[get, Future[Schema.File]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withFile(body: Schema.File) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.File])
		}
		object update {
			def apply(fileId: String, addParents: String, enforceSingleParent: Boolean, keepRevisionForever: Boolean, ocrLanguage: String, removeParents: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, useContentAsIndexableText: Boolean, includePermissionsForView: String, includeLabels: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"files/${fileId}").addQueryStringParameters("addParents" -> addParents.toString, "enforceSingleParent" -> enforceSingleParent.toString, "keepRevisionForever" -> keepRevisionForever.toString, "ocrLanguage" -> ocrLanguage.toString, "removeParents" -> removeParents.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "useContentAsIndexableText" -> useContentAsIndexableText.toString, "includePermissionsForView" -> includePermissionsForView.toString, "includeLabels" -> includeLabels.toString))
		}
		class download(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			/** Optional. The MIME type the file should be downloaded as. This field can only be set when downloading Google Workspace documents. See [Export MIME types for Google Workspace documents](/drive/api/guides/ref-export-formats) for the list of supported MIME types. If not set, a Google Workspace document is downloaded with a default MIME type. The default MIME type might change in the future. */
			def withMimeType(mimeType: String) = new download(req.addQueryStringParameters("mimeType" -> mimeType.toString))
			/** Optional. The revision ID of the file to download. This field can only be set when downloading blob files, Google Docs, and Google Sheets. Returns `INVALID_ARGUMENT` if downloading a specific revision on the file is unsupported. */
			def withRevisionId(revisionId: String) = new download(req.addQueryStringParameters("revisionId" -> revisionId.toString))
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object download {
			def apply(fileId: String)(using auth: AuthToken, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"files/${fileId}/download").addQueryStringParameters())
			given Conversion[download, Future[Schema.Operation]] = (fun: download) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FileList]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.FileList])
		}
		object list {
			def apply(corpora: String, corpus: String, driveId: String, includeItemsFromAllDrives: Boolean, includeTeamDriveItems: Boolean, orderBy: String, pageSize: Int, pageToken: String, q: String, spaces: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, teamDriveId: String, includePermissionsForView: String, includeLabels: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"files").addQueryStringParameters("corpora" -> corpora.toString, "corpus" -> corpus.toString, "driveId" -> driveId.toString, "includeItemsFromAllDrives" -> includeItemsFromAllDrives.toString, "includeTeamDriveItems" -> includeTeamDriveItems.toString, "orderBy" -> orderBy.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "q" -> q.toString, "spaces" -> spaces.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "teamDriveId" -> teamDriveId.toString, "includePermissionsForView" -> includePermissionsForView.toString, "includeLabels" -> includeLabels.toString))
			given Conversion[list, Future[Schema.FileList]] = (fun: list) => fun.apply()
		}
		class modifyLabels(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withModifyLabelsRequest(body: Schema.ModifyLabelsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ModifyLabelsResponse])
		}
		object modifyLabels {
			def apply(fileId: String)(using auth: AuthToken, ec: ExecutionContext): modifyLabels = new modifyLabels(ws.url(BASE_URL + s"files/${fileId}/modifyLabels").addQueryStringParameters())
		}
		class listLabels(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LabelList]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LabelList])
		}
		object listLabels {
			def apply(fileId: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): listLabels = new listLabels(ws.url(BASE_URL + s"files/${fileId}/listLabels").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[listLabels, Future[Schema.LabelList]] = (fun: listLabels) => fun.apply()
		}
		class watch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withChannel(body: Schema.Channel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Channel])
		}
		object watch {
			def apply(fileId: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, acknowledgeAbuse: Boolean, includePermissionsForView: String, includeLabels: String)(using auth: AuthToken, ec: ExecutionContext): watch = new watch(ws.url(BASE_URL + s"files/${fileId}/watch").addQueryStringParameters("supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "acknowledgeAbuse" -> acknowledgeAbuse.toString, "includePermissionsForView" -> includePermissionsForView.toString, "includeLabels" -> includeLabels.toString))
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withFile(body: Schema.File) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.File])
		}
		object create {
			def apply(enforceSingleParent: Boolean, ignoreDefaultVisibility: Boolean, keepRevisionForever: Boolean, ocrLanguage: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, useContentAsIndexableText: Boolean, includePermissionsForView: String, includeLabels: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"files").addQueryStringParameters("enforceSingleParent" -> enforceSingleParent.toString, "ignoreDefaultVisibility" -> ignoreDefaultVisibility.toString, "keepRevisionForever" -> keepRevisionForever.toString, "ocrLanguage" -> ocrLanguage.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "useContentAsIndexableText" -> useContentAsIndexableText.toString, "includePermissionsForView" -> includePermissionsForView.toString, "includeLabels" -> includeLabels.toString))
		}
		class emptyTrash(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object emptyTrash {
			def apply(enforceSingleParent: Boolean, driveId: String)(using auth: AuthToken, ec: ExecutionContext): emptyTrash = new emptyTrash(ws.url(BASE_URL + s"files/trash").addQueryStringParameters("enforceSingleParent" -> enforceSingleParent.toString, "driveId" -> driveId.toString))
			given Conversion[emptyTrash, Future[Unit]] = (fun: emptyTrash) => fun.apply()
		}
		class copy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withFile(body: Schema.File) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.File])
		}
		object copy {
			def apply(fileId: String, enforceSingleParent: Boolean, ignoreDefaultVisibility: Boolean, keepRevisionForever: Boolean, ocrLanguage: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, includePermissionsForView: String, includeLabels: String)(using auth: AuthToken, ec: ExecutionContext): copy = new copy(ws.url(BASE_URL + s"files/${fileId}/copy").addQueryStringParameters("enforceSingleParent" -> enforceSingleParent.toString, "ignoreDefaultVisibility" -> ignoreDefaultVisibility.toString, "keepRevisionForever" -> keepRevisionForever.toString, "ocrLanguage" -> ocrLanguage.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "includePermissionsForView" -> includePermissionsForView.toString, "includeLabels" -> includeLabels.toString))
		}
	}
	object permissions {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPermission(body: Schema.Permission) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Permission])
		}
		object create {
			def apply(fileId: String, emailMessage: String, enforceSingleParent: Boolean, moveToNewOwnersRoot: Boolean, sendNotificationEmail: Boolean, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, transferOwnership: Boolean, useDomainAdminAccess: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"files/${fileId}/permissions").addQueryStringParameters("emailMessage" -> emailMessage.toString, "enforceSingleParent" -> enforceSingleParent.toString, "moveToNewOwnersRoot" -> moveToNewOwnersRoot.toString, "sendNotificationEmail" -> sendNotificationEmail.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "transferOwnership" -> transferOwnership.toString, "useDomainAdminAccess" -> useDomainAdminAccess.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(fileId: String, permissionId: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, useDomainAdminAccess: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"files/${fileId}/permissions/${permissionId}").addQueryStringParameters("supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "useDomainAdminAccess" -> useDomainAdminAccess.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Permission]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Permission])
		}
		object get {
			def apply(fileId: String, permissionId: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, useDomainAdminAccess: Boolean)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"files/${fileId}/permissions/${permissionId}").addQueryStringParameters("supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "useDomainAdminAccess" -> useDomainAdminAccess.toString))
			given Conversion[get, Future[Schema.Permission]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPermission(body: Schema.Permission) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Permission])
		}
		object update {
			def apply(fileId: String, permissionId: String, removeExpiration: Boolean, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, transferOwnership: Boolean, useDomainAdminAccess: Boolean)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"files/${fileId}/permissions/${permissionId}").addQueryStringParameters("removeExpiration" -> removeExpiration.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "transferOwnership" -> transferOwnership.toString, "useDomainAdminAccess" -> useDomainAdminAccess.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PermissionList]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.PermissionList])
		}
		object list {
			def apply(fileId: String, pageSize: Int, pageToken: String, supportsAllDrives: Boolean, supportsTeamDrives: Boolean, useDomainAdminAccess: Boolean, includePermissionsForView: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"files/${fileId}/permissions").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "supportsAllDrives" -> supportsAllDrives.toString, "supportsTeamDrives" -> supportsTeamDrives.toString, "useDomainAdminAccess" -> useDomainAdminAccess.toString, "includePermissionsForView" -> includePermissionsForView.toString))
			given Conversion[list, Future[Schema.PermissionList]] = (fun: list) => fun.apply()
		}
	}
	object replies {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withReply(body: Schema.Reply) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Reply])
		}
		object create {
			def apply(fileId: String, commentId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"files/${fileId}/comments/${commentId}/replies").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(fileId: String, commentId: String, replyId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"files/${fileId}/comments/${commentId}/replies/${replyId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Reply]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Reply])
		}
		object get {
			def apply(fileId: String, commentId: String, replyId: String, includeDeleted: Boolean)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"files/${fileId}/comments/${commentId}/replies/${replyId}").addQueryStringParameters("includeDeleted" -> includeDeleted.toString))
			given Conversion[get, Future[Schema.Reply]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withReply(body: Schema.Reply) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Reply])
		}
		object update {
			def apply(fileId: String, commentId: String, replyId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"files/${fileId}/comments/${commentId}/replies/${replyId}").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ReplyList]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ReplyList])
		}
		object list {
			def apply(fileId: String, commentId: String, includeDeleted: Boolean, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"files/${fileId}/comments/${commentId}/replies").addQueryStringParameters("includeDeleted" -> includeDeleted.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ReplyList]] = (fun: list) => fun.apply()
		}
	}
	object revisions {
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(fileId: String, revisionId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"files/${fileId}/revisions/${revisionId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Revision]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Revision])
		}
		object get {
			def apply(fileId: String, revisionId: String, acknowledgeAbuse: Boolean)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"files/${fileId}/revisions/${revisionId}").addQueryStringParameters("acknowledgeAbuse" -> acknowledgeAbuse.toString))
			given Conversion[get, Future[Schema.Revision]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RevisionList]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.RevisionList])
		}
		object list {
			def apply(fileId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"files/${fileId}/revisions").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.RevisionList]] = (fun: list) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRevision(body: Schema.Revision) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Revision])
		}
		object update {
			def apply(fileId: String, revisionId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"files/${fileId}/revisions/${revisionId}").addQueryStringParameters())
		}
	}
	object teamdrives {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTeamDrive(body: Schema.TeamDrive) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TeamDrive])
		}
		object create {
			def apply(requestId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"teamdrives").addQueryStringParameters("requestId" -> requestId.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(teamDriveId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"teamdrives/${teamDriveId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TeamDrive]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.TeamDrive])
		}
		object get {
			def apply(teamDriveId: String, useDomainAdminAccess: Boolean)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"teamdrives/${teamDriveId}").addQueryStringParameters("useDomainAdminAccess" -> useDomainAdminAccess.toString))
			given Conversion[get, Future[Schema.TeamDrive]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTeamDrive(body: Schema.TeamDrive) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.TeamDrive])
		}
		object update {
			def apply(teamDriveId: String, useDomainAdminAccess: Boolean)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"teamdrives/${teamDriveId}").addQueryStringParameters("useDomainAdminAccess" -> useDomainAdminAccess.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TeamDriveList]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.TeamDriveList])
		}
		object list {
			def apply(pageSize: Int, pageToken: String, q: String, useDomainAdminAccess: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"teamdrives").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "q" -> q.toString, "useDomainAdminAccess" -> useDomainAdminAccess.toString))
			given Conversion[list, Future[Schema.TeamDriveList]] = (fun: list) => fun.apply()
		}
	}
	object accessproposals {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AccessProposal]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AccessProposal])
		}
		object get {
			def apply(fileId: String, proposalId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"files/${fileId}/accessproposals/${proposalId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.AccessProposal]] = (fun: get) => fun.apply()
		}
		class resolve(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withResolveAccessProposalRequest(body: Schema.ResolveAccessProposalRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object resolve {
			def apply(fileId: String, proposalId: String)(using auth: AuthToken, ec: ExecutionContext): resolve = new resolve(ws.url(BASE_URL + s"files/${fileId}/accessproposals/${proposalId}:resolve").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAccessProposalsResponse]) {
			/** Optional. The continuation token on the list of access requests. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. The number of results per page<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAccessProposalsResponse])
		}
		object list {
			def apply(fileId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"files/${fileId}/accessproposals").addQueryStringParameters())
			given Conversion[list, Future[Schema.ListAccessProposalsResponse]] = (fun: list) => fun.apply()
		}
	}
}
