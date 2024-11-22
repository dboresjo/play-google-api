package com.boresjo.play.api.google.biglake

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://biglake.googleapis.com/"

	object projects {
		object locations {
			object catalogs {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCatalog(body: Schema.Catalog) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Catalog])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, catalogId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs").addQueryStringParameters("parent" -> parent.toString, "catalogId" -> catalogId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Catalog]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Catalog])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Catalog]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Catalog]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Catalog])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Catalog]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCatalogsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCatalogsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListCatalogsResponse]] = (fun: list) => fun.apply()
				}
				object databases {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withDatabase(body: Schema.Database) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Database])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String, databaseId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases").addQueryStringParameters("parent" -> parent.toString, "databaseId" -> databaseId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Database]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Database])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, databasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases/${databasesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Database]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Database]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Database])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, databasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases/${databasesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Database]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withDatabase(body: Schema.Database) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Database])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, databasesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases/${databasesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDatabasesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListDatabasesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListDatabasesResponse]] = (fun: list) => fun.apply()
					}
					object tables {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withTable(body: Schema.Table) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Table])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, databasesId :PlayApi, parent: String, tableId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases/${databasesId}/tables").addQueryStringParameters("parent" -> parent.toString, "tableId" -> tableId.toString))
						}
						class rename(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withRenameTableRequest(body: Schema.RenameTableRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Table])
						}
						object rename {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, databasesId :PlayApi, tablesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): rename = new rename(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases/${databasesId}/tables/${tablesId}:rename").addQueryStringParameters("name" -> name.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Table]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Table])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, databasesId :PlayApi, tablesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases/${databasesId}/tables/${tablesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Table]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Table]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Table])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, databasesId :PlayApi, tablesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases/${databasesId}/tables/${tablesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Table]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withTable(body: Schema.Table) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Table])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, databasesId :PlayApi, tablesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases/${databasesId}/tables/${tablesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTablesResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListTablesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, databasesId :PlayApi, parent: String, pageSize: Int, pageToken: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases/${databasesId}/tables").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
							given Conversion[list, Future[Schema.ListTablesResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
		}
	}
}
