package com.boresjo.play.api.google.area120tables

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
		"""https://www.googleapis.com/auth/drive.readonly""" /* See and download all your Google Drive files */,
		"""https://www.googleapis.com/auth/drive.file""" /* See, edit, create, and delete only the specific Google Drive files you use with this app */,
		"""https://www.googleapis.com/auth/spreadsheets.readonly""" /* See all your Google Sheets spreadsheets */,
		"""https://www.googleapis.com/auth/spreadsheets""" /* See, edit, create, and delete all your Google Sheets spreadsheets */,
		"""https://www.googleapis.com/auth/tables""" /* See, edit, create, and delete your tables in Tables by Area 120 */,
		"""https://www.googleapis.com/auth/drive""" /* See, edit, create, and delete all of your Google Drive files */
	)

	private val BASE_URL = "https://area120tables.googleapis.com/"

	object tables {
		/** Lists tables for the user. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTablesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.readonly""", """https://www.googleapis.com/auth/spreadsheets""", """https://www.googleapis.com/auth/spreadsheets.readonly""", """https://www.googleapis.com/auth/tables""")
			/** Optional. Sorting order for the list of tables on createTime/updateTime. */
			def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTablesResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha1/tables").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListTablesResponse]] = (fun: list) => fun.apply()
		}
		/** Gets a table. Returns NOT_FOUND if the table does not exist. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Table]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.readonly""", """https://www.googleapis.com/auth/spreadsheets""", """https://www.googleapis.com/auth/spreadsheets.readonly""", """https://www.googleapis.com/auth/tables""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Table])
		}
		object get {
			def apply(tablesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha1/tables/${tablesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Table]] = (fun: get) => fun.apply()
		}
		object rows {
			/** Creates a row. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/spreadsheets""", """https://www.googleapis.com/auth/tables""")
				/** Optional. Column key to use for values in the row. Defaults to user entered name.<br>Possible values:<br>VIEW_UNSPECIFIED: Defaults to user entered text.<br>COLUMN_ID_VIEW: Uses internally generated column id to identify values. */
				def withView(view: String) = new create(req.addQueryStringParameters("view" -> view.toString))
				/** Perform the request */
				def withRow(body: Schema.Row) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Row])
			}
			object create {
				def apply(tablesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1alpha1/tables/${tablesId}/rows").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Updates multiple rows. */
			class batchUpdate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/spreadsheets""", """https://www.googleapis.com/auth/tables""")
				/** Perform the request */
				def withBatchUpdateRowsRequest(body: Schema.BatchUpdateRowsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUpdateRowsResponse])
			}
			object batchUpdate {
				def apply(tablesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"v1alpha1/tables/${tablesId}/rows:batchUpdate").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes multiple rows. */
			class batchDelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/spreadsheets""", """https://www.googleapis.com/auth/tables""")
				/** Perform the request */
				def withBatchDeleteRowsRequest(body: Schema.BatchDeleteRowsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object batchDelete {
				def apply(tablesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"v1alpha1/tables/${tablesId}/rows:batchDelete").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a row. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/spreadsheets""", """https://www.googleapis.com/auth/tables""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(tablesId :PlayApi, rowsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1alpha1/tables/${tablesId}/rows/${rowsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Creates multiple rows. */
			class batchCreate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/spreadsheets""", """https://www.googleapis.com/auth/tables""")
				/** Perform the request */
				def withBatchCreateRowsRequest(body: Schema.BatchCreateRowsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchCreateRowsResponse])
			}
			object batchCreate {
				def apply(tablesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchCreate = new batchCreate(ws.url(BASE_URL + s"v1alpha1/tables/${tablesId}/rows:batchCreate").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Gets a row. Returns NOT_FOUND if the row does not exist in the table. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Row]) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.readonly""", """https://www.googleapis.com/auth/spreadsheets""", """https://www.googleapis.com/auth/spreadsheets.readonly""", """https://www.googleapis.com/auth/tables""")
				/** Optional. Column key to use for values in the row. Defaults to user entered name.<br>Possible values:<br>VIEW_UNSPECIFIED: Defaults to user entered text.<br>COLUMN_ID_VIEW: Uses internally generated column id to identify values. */
				def withView(view: String) = new get(req.addQueryStringParameters("view" -> view.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Row])
			}
			object get {
				def apply(tablesId :PlayApi, rowsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha1/tables/${tablesId}/rows/${rowsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Row]] = (fun: get) => fun.apply()
			}
			/** Updates a row. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/spreadsheets""", """https://www.googleapis.com/auth/tables""")
				/** Optional. Column key to use for values in the row. Defaults to user entered name.<br>Possible values:<br>VIEW_UNSPECIFIED: Defaults to user entered text.<br>COLUMN_ID_VIEW: Uses internally generated column id to identify values. */
				def withView(view: String) = new patch(req.addQueryStringParameters("view" -> view.toString))
				/** Perform the request */
				def withRow(body: Schema.Row) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Row])
			}
			object patch {
				def apply(tablesId :PlayApi, rowsId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1alpha1/tables/${tablesId}/rows/${rowsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			/** Lists rows in a table. Returns NOT_FOUND if the table does not exist. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListRowsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.readonly""", """https://www.googleapis.com/auth/spreadsheets""", """https://www.googleapis.com/auth/spreadsheets.readonly""", """https://www.googleapis.com/auth/tables""")
				/** Optional. Filter to only include resources matching the requirements. For more information, see [Filtering list results](https://support.google.com/area120-tables/answer/10503371). */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. Column key to use for values in the row. Defaults to user entered name.<br>Possible values:<br>VIEW_UNSPECIFIED: Defaults to user entered text.<br>COLUMN_ID_VIEW: Uses internally generated column id to identify values. */
				def withView(view: String) = new list(req.addQueryStringParameters("view" -> view.toString))
				/** Optional. Sorting order for the list of rows on createTime/updateTime. */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListRowsResponse])
			}
			object list {
				def apply(tablesId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha1/tables/${tablesId}/rows").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListRowsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object workspaces {
		/** Lists workspaces for the user. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListWorkspacesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.readonly""", """https://www.googleapis.com/auth/spreadsheets""", """https://www.googleapis.com/auth/spreadsheets.readonly""", """https://www.googleapis.com/auth/tables""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListWorkspacesResponse])
		}
		object list {
			def apply(pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha1/workspaces").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.ListWorkspacesResponse]] = (fun: list) => fun.apply()
		}
		/** Gets a workspace. Returns NOT_FOUND if the workspace does not exist. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Workspace]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.readonly""", """https://www.googleapis.com/auth/spreadsheets""", """https://www.googleapis.com/auth/spreadsheets.readonly""", """https://www.googleapis.com/auth/tables""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Workspace])
		}
		object get {
			def apply(workspacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha1/workspaces/${workspacesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Workspace]] = (fun: get) => fun.apply()
		}
	}
}
