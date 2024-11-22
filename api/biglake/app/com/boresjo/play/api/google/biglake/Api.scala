package com.boresjo.play.api.google.biglake

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
		"""https://www.googleapis.com/auth/bigquery""" /* View and manage your data in Google BigQuery and see the email address for your Google Account */,
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://biglake.googleapis.com/"

	object projects {
		object locations {
			object catalogs {
				/** Creates a new catalog. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCatalog(body: Schema.Catalog) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Catalog])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, catalogId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs").addQueryStringParameters("parent" -> parent.toString, "catalogId" -> catalogId.toString))
				}
				/** Deletes an existing catalog specified by the catalog ID. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Catalog]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Catalog])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Catalog]] = (fun: delete) => fun.apply()
				}
				/** Gets the catalog specified by the resource name. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Catalog]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Catalog])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Catalog]] = (fun: get) => fun.apply()
				}
				/** List all catalogs in a specified project. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCatalogsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCatalogsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListCatalogsResponse]] = (fun: list) => fun.apply()
				}
				object databases {
					/** Creates a new database. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withDatabase(body: Schema.Database) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Database])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String, databaseId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases").addQueryStringParameters("parent" -> parent.toString, "databaseId" -> databaseId.toString))
					}
					/** Deletes an existing database specified by the database ID. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Database]) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Database])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, databasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases/${databasesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Database]] = (fun: delete) => fun.apply()
					}
					/** Gets the database specified by the resource name. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Database]) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Database])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, databasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases/${databasesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Database]] = (fun: get) => fun.apply()
					}
					/** Updates an existing database specified by the database ID. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withDatabase(body: Schema.Database) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Database])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, databasesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases/${databasesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** List all databases in a specified catalog. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDatabasesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDatabasesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListDatabasesResponse]] = (fun: list) => fun.apply()
					}
					object tables {
						/** Creates a new table. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withTable(body: Schema.Table) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Table])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, databasesId :PlayApi, parent: String, tableId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases/${databasesId}/tables").addQueryStringParameters("parent" -> parent.toString, "tableId" -> tableId.toString))
						}
						/** Renames an existing table specified by the table ID. */
						class rename(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withRenameTableRequest(body: Schema.RenameTableRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Table])
						}
						object rename {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, databasesId :PlayApi, tablesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): rename = new rename(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases/${databasesId}/tables/${tablesId}:rename").addQueryStringParameters("name" -> name.toString))
						}
						/** Deletes an existing table specified by the table ID. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Table]) {
							val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Table])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, databasesId :PlayApi, tablesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases/${databasesId}/tables/${tablesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Table]] = (fun: delete) => fun.apply()
						}
						/** Gets the table specified by the resource name. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Table]) {
							val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Table])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, databasesId :PlayApi, tablesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases/${databasesId}/tables/${tablesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Table]] = (fun: get) => fun.apply()
						}
						/** Updates an existing table specified by the table ID. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withTable(body: Schema.Table) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Table])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, databasesId :PlayApi, tablesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases/${databasesId}/tables/${tablesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						/** List all tables in a specified database. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTablesResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTablesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, databasesId :PlayApi, parent: String, pageSize: Int, pageToken: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/databases/${databasesId}/tables").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
							given Conversion[list, Future[Schema.ListTablesResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
		}
	}
}
