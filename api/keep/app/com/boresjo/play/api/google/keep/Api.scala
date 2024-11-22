package com.boresjo.play.api.google.keep

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://keep.googleapis.com/"

	object notes {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withNote(body: Schema.Note) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Note])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/notes").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(notesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/notes/${notesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Note]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Note])
		}
		object get {
			def apply(notesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/notes/${notesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Note]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListNotesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListNotesResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/notes").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListNotesResponse]] = (fun: list) => fun.apply()
		}
		object permissions {
			class batchCreate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBatchCreatePermissionsRequest(body: Schema.BatchCreatePermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchCreatePermissionsResponse])
			}
			object batchCreate {
				def apply(notesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchCreate = new batchCreate(ws.url(BASE_URL + s"v1/notes/${notesId}/permissions:batchCreate").addQueryStringParameters("parent" -> parent.toString))
			}
			class batchDelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBatchDeletePermissionsRequest(body: Schema.BatchDeletePermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object batchDelete {
				def apply(notesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"v1/notes/${notesId}/permissions:batchDelete").addQueryStringParameters("parent" -> parent.toString))
			}
		}
	}
	object media {
		class download(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Attachment]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Attachment])
		}
		object download {
			def apply(notesId :PlayApi, attachmentsId :PlayApi, name: String, mimeType: String)(using auth: AuthToken, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"v1/notes/${notesId}/attachments/${attachmentsId}").addQueryStringParameters("name" -> name.toString, "mimeType" -> mimeType.toString))
			given Conversion[download, Future[Schema.Attachment]] = (fun: download) => fun.apply()
		}
	}
}
