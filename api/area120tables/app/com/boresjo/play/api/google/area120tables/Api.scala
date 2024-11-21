package com.boresjo.play.api.google.area120tables

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://area120tables.googleapis.com/"

	object tables {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTablesResponse]) {
			/** Optional. Sorting order for the list of tables on createTime/updateTime. */
			def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.ListTablesResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha1/tables")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListTablesResponse]] = (fun: list) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Table]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Table])
		}
		object get {
			def apply(tablesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha1/tables/${tablesId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Table]] = (fun: get) => fun.apply()
		}
		object rows {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. Column key to use for values in the row. Defaults to user entered name.<br>Possible values:<br>VIEW_UNSPECIFIED: Defaults to user entered text.<br>COLUMN_ID_VIEW: Uses internally generated column id to identify values. */
				def withView(view: String) = new create(req.addQueryStringParameters("view" -> view.toString))
				def withRow(body: Schema.Row) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Row])
			}
			object create {
				def apply(tablesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1alpha1/tables/${tablesId}/rows")).addQueryStringParameters("parent" -> parent.toString))
			}
			class batchUpdate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBatchUpdateRowsRequest(body: Schema.BatchUpdateRowsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.BatchUpdateRowsResponse])
			}
			object batchUpdate {
				def apply(tablesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchUpdate = new batchUpdate(auth(ws.url(BASE_URL + s"v1alpha1/tables/${tablesId}/rows:batchUpdate")).addQueryStringParameters("parent" -> parent.toString))
			}
			class batchDelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBatchDeleteRowsRequest(body: Schema.BatchDeleteRowsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
			}
			object batchDelete {
				def apply(tablesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchDelete = new batchDelete(auth(ws.url(BASE_URL + s"v1alpha1/tables/${tablesId}/rows:batchDelete")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(tablesId :PlayApi, rowsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1alpha1/tables/${tablesId}/rows/${rowsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class batchCreate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBatchCreateRowsRequest(body: Schema.BatchCreateRowsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.BatchCreateRowsResponse])
			}
			object batchCreate {
				def apply(tablesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchCreate = new batchCreate(auth(ws.url(BASE_URL + s"v1alpha1/tables/${tablesId}/rows:batchCreate")).addQueryStringParameters("parent" -> parent.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Row]) {
				/** Optional. Column key to use for values in the row. Defaults to user entered name.<br>Possible values:<br>VIEW_UNSPECIFIED: Defaults to user entered text.<br>COLUMN_ID_VIEW: Uses internally generated column id to identify values. */
				def withView(view: String) = new get(req.addQueryStringParameters("view" -> view.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.Row])
			}
			object get {
				def apply(tablesId :PlayApi, rowsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha1/tables/${tablesId}/rows/${rowsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Row]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. Column key to use for values in the row. Defaults to user entered name.<br>Possible values:<br>VIEW_UNSPECIFIED: Defaults to user entered text.<br>COLUMN_ID_VIEW: Uses internally generated column id to identify values. */
				def withView(view: String) = new patch(req.addQueryStringParameters("view" -> view.toString))
				def withRow(body: Schema.Row) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Row])
			}
			object patch {
				def apply(tablesId :PlayApi, rowsId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1alpha1/tables/${tablesId}/rows/${rowsId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListRowsResponse]) {
				/** Optional. Filter to only include resources matching the requirements. For more information, see [Filtering list results](https://support.google.com/area120-tables/answer/10503371). */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. Column key to use for values in the row. Defaults to user entered name.<br>Possible values:<br>VIEW_UNSPECIFIED: Defaults to user entered text.<br>COLUMN_ID_VIEW: Uses internally generated column id to identify values. */
				def withView(view: String) = new list(req.addQueryStringParameters("view" -> view.toString))
				/** Optional. Sorting order for the list of rows on createTime/updateTime. */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.ListRowsResponse])
			}
			object list {
				def apply(tablesId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha1/tables/${tablesId}/rows")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListRowsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object workspaces {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListWorkspacesResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ListWorkspacesResponse])
		}
		object list {
			def apply(pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha1/workspaces")).addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.ListWorkspacesResponse]] = (fun: list) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Workspace]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Workspace])
		}
		object get {
			def apply(workspacesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha1/workspaces/${workspacesId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Workspace]] = (fun: get) => fun.apply()
		}
	}
}
