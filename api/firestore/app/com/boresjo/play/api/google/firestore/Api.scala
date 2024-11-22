package com.boresjo.play.api.google.firestore

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://firestore.googleapis.com/"

	object projects {
		object locations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			object backups {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirestoreAdminV1Backup]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirestoreAdminV1Backup])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, backupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backups/${backupsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirestoreAdminV1Backup]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirestoreAdminV1ListBackupsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirestoreAdminV1ListBackupsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backups").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.GoogleFirestoreAdminV1ListBackupsResponse]] = (fun: list) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, backupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backups/${backupsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
			}
		}
		object databases {
			class exportDocuments(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirestoreAdminV1ExportDocumentsRequest(body: Schema.GoogleFirestoreAdminV1ExportDocumentsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object exportDocuments {
				def apply(projectsId :PlayApi, databasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): exportDocuments = new exportDocuments(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}:exportDocuments").addQueryStringParameters("name" -> name.toString))
			}
			class restore(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirestoreAdminV1RestoreDatabaseRequest(body: Schema.GoogleFirestoreAdminV1RestoreDatabaseRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object restore {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): restore = new restore(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases:restore").addQueryStringParameters("parent" -> parent.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirestoreAdminV1Database(body: Schema.GoogleFirestoreAdminV1Database) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String, databaseId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases").addQueryStringParameters("parent" -> parent.toString, "databaseId" -> databaseId.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object delete {
				def apply(projectsId :PlayApi, databasesId :PlayApi, name: String, etag: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString))
				given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
			}
			class bulkDeleteDocuments(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirestoreAdminV1BulkDeleteDocumentsRequest(body: Schema.GoogleFirestoreAdminV1BulkDeleteDocumentsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object bulkDeleteDocuments {
				def apply(projectsId :PlayApi, databasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): bulkDeleteDocuments = new bulkDeleteDocuments(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}:bulkDeleteDocuments").addQueryStringParameters("name" -> name.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirestoreAdminV1Database]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirestoreAdminV1Database])
			}
			object get {
				def apply(projectsId :PlayApi, databasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleFirestoreAdminV1Database]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirestoreAdminV1Database(body: Schema.GoogleFirestoreAdminV1Database) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object patch {
				def apply(projectsId :PlayApi, databasesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class importDocuments(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleFirestoreAdminV1ImportDocumentsRequest(body: Schema.GoogleFirestoreAdminV1ImportDocumentsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object importDocuments {
				def apply(projectsId :PlayApi, databasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): importDocuments = new importDocuments(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}:importDocuments").addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirestoreAdminV1ListDatabasesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirestoreAdminV1ListDatabasesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, showDeleted: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases").addQueryStringParameters("parent" -> parent.toString, "showDeleted" -> showDeleted.toString))
				given Conversion[list, Future[Schema.GoogleFirestoreAdminV1ListDatabasesResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, databasesId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object get {
					def apply(projectsId :PlayApi, databasesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, databasesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleLongrunningCancelOperationRequest(body: Schema.GoogleLongrunningCancelOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, databasesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
			object backupSchedules {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleFirestoreAdminV1BackupSchedule(body: Schema.GoogleFirestoreAdminV1BackupSchedule) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirestoreAdminV1BackupSchedule])
				}
				object create {
					def apply(projectsId :PlayApi, databasesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/backupSchedules").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, databasesId :PlayApi, backupSchedulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/backupSchedules/${backupSchedulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirestoreAdminV1BackupSchedule]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirestoreAdminV1BackupSchedule])
				}
				object get {
					def apply(projectsId :PlayApi, databasesId :PlayApi, backupSchedulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/backupSchedules/${backupSchedulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirestoreAdminV1BackupSchedule]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleFirestoreAdminV1BackupSchedule(body: Schema.GoogleFirestoreAdminV1BackupSchedule) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleFirestoreAdminV1BackupSchedule])
				}
				object patch {
					def apply(projectsId :PlayApi, databasesId :PlayApi, backupSchedulesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/backupSchedules/${backupSchedulesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirestoreAdminV1ListBackupSchedulesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirestoreAdminV1ListBackupSchedulesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, databasesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/backupSchedules").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleFirestoreAdminV1ListBackupSchedulesResponse]] = (fun: list) => fun.apply()
				}
			}
			object collectionGroups {
				object indexes {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleFirestoreAdminV1Index(body: Schema.GoogleFirestoreAdminV1Index) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object create {
						def apply(projectsId :PlayApi, databasesId :PlayApi, collectionGroupsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/collectionGroups/${collectionGroupsId}/indexes").addQueryStringParameters("parent" -> parent.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirestoreAdminV1ListIndexesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirestoreAdminV1ListIndexesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, databasesId :PlayApi, collectionGroupsId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/collectionGroups/${collectionGroupsId}/indexes").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleFirestoreAdminV1ListIndexesResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirestoreAdminV1Index]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirestoreAdminV1Index])
					}
					object get {
						def apply(projectsId :PlayApi, databasesId :PlayApi, collectionGroupsId :PlayApi, indexesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/collectionGroups/${collectionGroupsId}/indexes/${indexesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleFirestoreAdminV1Index]] = (fun: get) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, databasesId :PlayApi, collectionGroupsId :PlayApi, indexesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/collectionGroups/${collectionGroupsId}/indexes/${indexesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
				}
				object fields {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirestoreAdminV1Field]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirestoreAdminV1Field])
					}
					object get {
						def apply(projectsId :PlayApi, databasesId :PlayApi, collectionGroupsId :PlayApi, fieldsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/collectionGroups/${collectionGroupsId}/fields/${fieldsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleFirestoreAdminV1Field]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleFirestoreAdminV1Field(body: Schema.GoogleFirestoreAdminV1Field) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object patch {
						def apply(projectsId :PlayApi, databasesId :PlayApi, collectionGroupsId :PlayApi, fieldsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/collectionGroups/${collectionGroupsId}/fields/${fieldsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirestoreAdminV1ListFieldsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirestoreAdminV1ListFieldsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, databasesId :PlayApi, collectionGroupsId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/collectionGroups/${collectionGroupsId}/fields").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleFirestoreAdminV1ListFieldsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object documents {
				class listCollectionIds(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withListCollectionIdsRequest(body: Schema.ListCollectionIdsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ListCollectionIdsResponse])
				}
				object listCollectionIds {
					def apply(projectsId :PlayApi, databasesId :PlayApi, documentsId :PlayApi, documentsId1 :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): listCollectionIds = new listCollectionIds(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents/${documentsId}/${documentsId1}:listCollectionIds").addQueryStringParameters("parent" -> parent.toString))
				}
				class listDocuments(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDocumentsResponse]) {
					/** Optional. The collection ID, relative to `parent`, to list. For example: `chatrooms` or `messages`. This is optional, and when not provided, Firestore will list documents from all collections under the provided `parent`. */
					def withCollectionId(collectionId: String) = new listDocuments(req.addQueryStringParameters("collectionId" -> collectionId.toString))
					/** Optional. The maximum number of documents to return in a single response. Firestore may return fewer than this value.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new listDocuments(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A page token, received from a previous `ListDocuments` response. Provide this to retrieve the subsequent page. When paginating, all other parameters (with the exception of `page_size`) must match the values set in the request that generated the page token. */
					def withPageToken(pageToken: String) = new listDocuments(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The optional ordering of the documents to return. For example: `priority desc, __name__ desc`. This mirrors the `ORDER BY` used in Firestore queries but in a string representation. When absent, documents are ordered based on `__name__ ASC`. */
					def withOrderBy(orderBy: String) = new listDocuments(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListDocumentsResponse])
				}
				object listDocuments {
					def apply(projectsId :PlayApi, databasesId :PlayApi, collectionId :PlayApi, parent: String, maskFieldPaths: String, transaction: String, readTime: String, showMissing: Boolean)(using auth: AuthToken, ec: ExecutionContext): listDocuments = new listDocuments(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents/${collectionId}").addQueryStringParameters("parent" -> parent.toString, "mask.fieldPaths" -> maskFieldPaths.toString, "transaction" -> transaction.toString, "readTime" -> readTime.toString, "showMissing" -> showMissing.toString))
					given Conversion[listDocuments, Future[Schema.ListDocumentsResponse]] = (fun: listDocuments) => fun.apply()
				}
				class beginTransaction(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withBeginTransactionRequest(body: Schema.BeginTransactionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BeginTransactionResponse])
				}
				object beginTransaction {
					def apply(projectsId :PlayApi, databasesId :PlayApi, database: String)(using auth: AuthToken, ec: ExecutionContext): beginTransaction = new beginTransaction(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents:beginTransaction").addQueryStringParameters("database" -> database.toString))
				}
				class runQuery(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRunQueryRequest(body: Schema.RunQueryRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RunQueryResponse])
				}
				object runQuery {
					def apply(projectsId :PlayApi, databasesId :PlayApi, documentsId :PlayApi, documentsId1 :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): runQuery = new runQuery(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents/${documentsId}/${documentsId1}:runQuery").addQueryStringParameters("parent" -> parent.toString))
				}
				class createDocument(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** The client-assigned document ID to use for this document. Optional. If not specified, an ID will be assigned by the service. */
					def withDocumentId(documentId: String) = new createDocument(req.addQueryStringParameters("documentId" -> documentId.toString))
					def withDocument(body: Schema.Document) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Document])
				}
				object createDocument {
					def apply(projectsId :PlayApi, databasesId :PlayApi, documentsId :PlayApi, parent: String, collectionId: String, maskFieldPaths: String)(using auth: AuthToken, ec: ExecutionContext): createDocument = new createDocument(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents/${documentsId}/${collectionId}").addQueryStringParameters("parent" -> parent.toString, "mask.fieldPaths" -> maskFieldPaths.toString))
				}
				class rollback(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRollbackRequest(body: Schema.RollbackRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object rollback {
					def apply(projectsId :PlayApi, databasesId :PlayApi, database: String)(using auth: AuthToken, ec: ExecutionContext): rollback = new rollback(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents:rollback").addQueryStringParameters("database" -> database.toString))
				}
				class batchWrite(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withBatchWriteRequest(body: Schema.BatchWriteRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchWriteResponse])
				}
				object batchWrite {
					def apply(projectsId :PlayApi, databasesId :PlayApi, database: String)(using auth: AuthToken, ec: ExecutionContext): batchWrite = new batchWrite(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents:batchWrite").addQueryStringParameters("database" -> database.toString))
				}
				class listen(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withListenRequest(body: Schema.ListenRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ListenResponse])
				}
				object listen {
					def apply(projectsId :PlayApi, databasesId :PlayApi, database: String)(using auth: AuthToken, ec: ExecutionContext): listen = new listen(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents:listen").addQueryStringParameters("database" -> database.toString))
				}
				class partitionQuery(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withPartitionQueryRequest(body: Schema.PartitionQueryRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PartitionQueryResponse])
				}
				object partitionQuery {
					def apply(projectsId :PlayApi, databasesId :PlayApi, documentsId :PlayApi, documentsId1 :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): partitionQuery = new partitionQuery(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents/${documentsId}/${documentsId1}:partitionQuery").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, databasesId :PlayApi, documentsId :PlayApi, documentsId1 :PlayApi, name: String, currentDocumentExists: Boolean, currentDocumentUpdateTime: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents/${documentsId}/${documentsId1}").addQueryStringParameters("name" -> name.toString, "currentDocument.exists" -> currentDocumentExists.toString, "currentDocument.updateTime" -> currentDocumentUpdateTime.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Document]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Document])
				}
				object get {
					def apply(projectsId :PlayApi, databasesId :PlayApi, documentsId :PlayApi, documentsId1 :PlayApi, name: String, maskFieldPaths: String, transaction: String, readTime: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents/${documentsId}/${documentsId1}").addQueryStringParameters("name" -> name.toString, "mask.fieldPaths" -> maskFieldPaths.toString, "transaction" -> transaction.toString, "readTime" -> readTime.toString))
					given Conversion[get, Future[Schema.Document]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDocument(body: Schema.Document) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Document])
				}
				object patch {
					def apply(projectsId :PlayApi, databasesId :PlayApi, documentsId :PlayApi, documentsId1 :PlayApi, name: String, updateMaskFieldPaths: String, maskFieldPaths: String, currentDocumentExists: Boolean, currentDocumentUpdateTime: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents/${documentsId}/${documentsId1}").addQueryStringParameters("name" -> name.toString, "updateMask.fieldPaths" -> updateMaskFieldPaths.toString, "mask.fieldPaths" -> maskFieldPaths.toString, "currentDocument.exists" -> currentDocumentExists.toString, "currentDocument.updateTime" -> currentDocumentUpdateTime.toString))
				}
				class batchGet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withBatchGetDocumentsRequest(body: Schema.BatchGetDocumentsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchGetDocumentsResponse])
				}
				object batchGet {
					def apply(projectsId :PlayApi, databasesId :PlayApi, database: String)(using auth: AuthToken, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents:batchGet").addQueryStringParameters("database" -> database.toString))
				}
				class write(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withWriteRequest(body: Schema.WriteRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.WriteResponse])
				}
				object write {
					def apply(projectsId :PlayApi, databasesId :PlayApi, database: String)(using auth: AuthToken, ec: ExecutionContext): write = new write(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents:write").addQueryStringParameters("database" -> database.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDocumentsResponse]) {
					/** Optional. The collection ID, relative to `parent`, to list. For example: `chatrooms` or `messages`. This is optional, and when not provided, Firestore will list documents from all collections under the provided `parent`. */
					def withCollectionId(collectionId: String) = new list(req.addQueryStringParameters("collectionId" -> collectionId.toString))
					/** Optional. The maximum number of documents to return in a single response. Firestore may return fewer than this value.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A page token, received from a previous `ListDocuments` response. Provide this to retrieve the subsequent page. When paginating, all other parameters (with the exception of `page_size`) must match the values set in the request that generated the page token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The optional ordering of the documents to return. For example: `priority desc, __name__ desc`. This mirrors the `ORDER BY` used in Firestore queries but in a string representation. When absent, documents are ordered based on `__name__ ASC`. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListDocumentsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, databasesId :PlayApi, documentsId :PlayApi, documentsId1 :PlayApi, collectionId :PlayApi, parent: String, maskFieldPaths: String, transaction: String, readTime: String, showMissing: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents/${documentsId}/${documentsId1}/${collectionId}").addQueryStringParameters("parent" -> parent.toString, "mask.fieldPaths" -> maskFieldPaths.toString, "transaction" -> transaction.toString, "readTime" -> readTime.toString, "showMissing" -> showMissing.toString))
					given Conversion[list, Future[Schema.ListDocumentsResponse]] = (fun: list) => fun.apply()
				}
				class commit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCommitRequest(body: Schema.CommitRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CommitResponse])
				}
				object commit {
					def apply(projectsId :PlayApi, databasesId :PlayApi, database: String)(using auth: AuthToken, ec: ExecutionContext): commit = new commit(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents:commit").addQueryStringParameters("database" -> database.toString))
				}
				class runAggregationQuery(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRunAggregationQueryRequest(body: Schema.RunAggregationQueryRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RunAggregationQueryResponse])
				}
				object runAggregationQuery {
					def apply(projectsId :PlayApi, databasesId :PlayApi, documentsId :PlayApi, documentsId1 :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): runAggregationQuery = new runAggregationQuery(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents/${documentsId}/${documentsId1}:runAggregationQuery").addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
	}
}
