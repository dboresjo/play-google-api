package com.boresjo.play.api.google.keep

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
		"""https://www.googleapis.com/auth/keep""" /* See, edit, create and permanently delete all your Google Keep data */,
		"""https://www.googleapis.com/auth/keep.readonly""" /* View all your Google Keep data */
	)

	private val BASE_URL = "https://keep.googleapis.com/"

	object notes {
		/** Creates a new note. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/keep""")
			/** Perform the request */
			def withNote(body: Schema.Note) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Note])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/notes").addQueryStringParameters())
		}
		/** Deletes a note. Caller must have the `OWNER` role on the note to delete. Deleting a note removes the resource immediately and cannot be undone. Any collaborators will lose access to the note. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/keep""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(notesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/notes/${notesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Gets a note. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Note]) {
			val scopes = Seq("""https://www.googleapis.com/auth/keep""", """https://www.googleapis.com/auth/keep.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Note])
		}
		object get {
			def apply(notesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/notes/${notesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Note]] = (fun: get) => fun.apply()
		}
		/** Lists notes. Every list call returns a page of results with `page_size` as the upper bound of returned items. A `page_size` of zero allows the server to choose the upper bound. The ListNotesResponse contains at most `page_size` entries. If there are more things left to list, it provides a `next_page_token` value. (Page tokens are opaque values.) To get the next page of results, copy the result's `next_page_token` into the next request's `page_token`. Repeat until the `next_page_token` returned with a page of results is empty. ListNotes return consistent results in the face of concurrent changes, or signals that it cannot with an ABORTED error. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListNotesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/keep""", """https://www.googleapis.com/auth/keep.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListNotesResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/notes").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListNotesResponse]] = (fun: list) => fun.apply()
		}
		object permissions {
			/** Creates one or more permissions on the note. Only permissions with the `WRITER` role may be created. If adding any permission fails, then the entire request fails and no changes are made. */
			class batchCreate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/keep""")
				/** Perform the request */
				def withBatchCreatePermissionsRequest(body: Schema.BatchCreatePermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchCreatePermissionsResponse])
			}
			object batchCreate {
				def apply(notesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchCreate = new batchCreate(ws.url(BASE_URL + s"v1/notes/${notesId}/permissions:batchCreate").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes one or more permissions on the note. The specified entities will immediately lose access. A permission with the `OWNER` role can't be removed. If removing a permission fails, then the entire request fails and no changes are made. Returns a 400 bad request error if a specified permission does not exist on the note. */
			class batchDelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/keep""")
				/** Perform the request */
				def withBatchDeletePermissionsRequest(body: Schema.BatchDeletePermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object batchDelete {
				def apply(notesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"v1/notes/${notesId}/permissions:batchDelete").addQueryStringParameters("parent" -> parent.toString))
			}
		}
	}
	object media {
		/** Gets an attachment. To download attachment media via REST requires the alt=media query parameter. Returns a 400 bad request error if attachment media is not available in the requested MIME type. */
		class download(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Attachment]) {
			val scopes = Seq("""https://www.googleapis.com/auth/keep""", """https://www.googleapis.com/auth/keep.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Attachment])
		}
		object download {
			def apply(notesId :PlayApi, attachmentsId :PlayApi, name: String, mimeType: String)(using signer: RequestSigner, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"v1/notes/${notesId}/attachments/${attachmentsId}").addQueryStringParameters("name" -> name.toString, "mimeType" -> mimeType.toString))
			given Conversion[download, Future[Schema.Attachment]] = (fun: download) => fun.apply()
		}
	}
}
