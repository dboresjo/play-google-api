package com.boresjo.play.api.google.firestore

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */,
		"""https://www.googleapis.com/auth/datastore""" /* View and manage your Google Cloud Datastore data */
	)

	private val BASE_URL = "https://firestore.googleapis.com/"

	object projects {
		object locations {
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			/** Gets information about a location. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			object backups {
				/** Gets information about a backup. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirestoreAdminV1Backup]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirestoreAdminV1Backup])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, backupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backups/${backupsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirestoreAdminV1Backup]] = (fun: get) => fun.apply()
				}
				/** Lists all the backups. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirestoreAdminV1ListBackupsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirestoreAdminV1ListBackupsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backups").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.GoogleFirestoreAdminV1ListBackupsResponse]] = (fun: list) => fun.apply()
				}
				/** Deletes a backup. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, backupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backups/${backupsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
			}
		}
		object databases {
			/** Exports a copy of all or a subset of documents from Google Cloud Firestore to another storage system, such as Google Cloud Storage. Recent updates to documents may not be reflected in the export. The export occurs in the background and its progress can be monitored and managed via the Operation resource that is created. The output of an export may only be used once the associated operation is done. If an export operation is cancelled before completion it may leave partial data behind in Google Cloud Storage. For more details on export behavior and output format, refer to: https://cloud.google.com/firestore/docs/manage-data/export-import */
			class exportDocuments(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
				/** Perform the request */
				def withGoogleFirestoreAdminV1ExportDocumentsRequest(body: Schema.GoogleFirestoreAdminV1ExportDocumentsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object exportDocuments {
				def apply(projectsId :PlayApi, databasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): exportDocuments = new exportDocuments(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}:exportDocuments").addQueryStringParameters("name" -> name.toString))
			}
			/** Creates a new database by restoring from an existing backup. The new database must be in the same cloud region or multi-region location as the existing backup. This behaves similar to FirestoreAdmin.CreateDatabase except instead of creating a new empty database, a new database is created with the database type, index configuration, and documents from an existing backup. The long-running operation can be used to track the progress of the restore, with the Operation's metadata field type being the RestoreDatabaseMetadata. The response type is the Database if the restore was successful. The new database is not readable or writeable until the LRO has completed. */
			class restore(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
				/** Perform the request */
				def withGoogleFirestoreAdminV1RestoreDatabaseRequest(body: Schema.GoogleFirestoreAdminV1RestoreDatabaseRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object restore {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): restore = new restore(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases:restore").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Create a database. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
				/** Perform the request */
				def withGoogleFirestoreAdminV1Database(body: Schema.GoogleFirestoreAdminV1Database) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String, databaseId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases").addQueryStringParameters("parent" -> parent.toString, "databaseId" -> databaseId.toString))
			}
			/** Deletes a database. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object delete {
				def apply(projectsId :PlayApi, databasesId :PlayApi, name: String, etag: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString))
				given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
			}
			/** Bulk deletes a subset of documents from Google Cloud Firestore. Documents created or updated after the underlying system starts to process the request will not be deleted. The bulk delete occurs in the background and its progress can be monitored and managed via the Operation resource that is created. For more details on bulk delete behavior, refer to: https://cloud.google.com/firestore/docs/manage-data/bulk-delete */
			class bulkDeleteDocuments(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
				/** Perform the request */
				def withGoogleFirestoreAdminV1BulkDeleteDocumentsRequest(body: Schema.GoogleFirestoreAdminV1BulkDeleteDocumentsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object bulkDeleteDocuments {
				def apply(projectsId :PlayApi, databasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): bulkDeleteDocuments = new bulkDeleteDocuments(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}:bulkDeleteDocuments").addQueryStringParameters("name" -> name.toString))
			}
			/** Gets information about a database. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirestoreAdminV1Database]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirestoreAdminV1Database])
			}
			object get {
				def apply(projectsId :PlayApi, databasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleFirestoreAdminV1Database]] = (fun: get) => fun.apply()
			}
			/** Updates a database. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
				/** Perform the request */
				def withGoogleFirestoreAdminV1Database(body: Schema.GoogleFirestoreAdminV1Database) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object patch {
				def apply(projectsId :PlayApi, databasesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Imports documents into Google Cloud Firestore. Existing documents with the same name are overwritten. The import occurs in the background and its progress can be monitored and managed via the Operation resource that is created. If an ImportDocuments operation is cancelled, it is possible that a subset of the data has already been imported to Cloud Firestore. */
			class importDocuments(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
				/** Perform the request */
				def withGoogleFirestoreAdminV1ImportDocumentsRequest(body: Schema.GoogleFirestoreAdminV1ImportDocumentsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object importDocuments {
				def apply(projectsId :PlayApi, databasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): importDocuments = new importDocuments(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}:importDocuments").addQueryStringParameters("name" -> name.toString))
			}
			/** List all the databases in the project. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirestoreAdminV1ListDatabasesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirestoreAdminV1ListDatabasesResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, showDeleted: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases").addQueryStringParameters("parent" -> parent.toString, "showDeleted" -> showDeleted.toString))
				given Conversion[list, Future[Schema.GoogleFirestoreAdminV1ListDatabasesResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, databasesId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object get {
					def apply(projectsId :PlayApi, databasesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
				}
				/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, databasesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def withGoogleLongrunningCancelOperationRequest(body: Schema.GoogleLongrunningCancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, databasesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
			object backupSchedules {
				/** Creates a backup schedule on a database. At most two backup schedules can be configured on a database, one daily backup schedule and one weekly backup schedule. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def withGoogleFirestoreAdminV1BackupSchedule(body: Schema.GoogleFirestoreAdminV1BackupSchedule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFirestoreAdminV1BackupSchedule])
				}
				object create {
					def apply(projectsId :PlayApi, databasesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/backupSchedules").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a backup schedule. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, databasesId :PlayApi, backupSchedulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/backupSchedules/${backupSchedulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets information about a backup schedule. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirestoreAdminV1BackupSchedule]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirestoreAdminV1BackupSchedule])
				}
				object get {
					def apply(projectsId :PlayApi, databasesId :PlayApi, backupSchedulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/backupSchedules/${backupSchedulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleFirestoreAdminV1BackupSchedule]] = (fun: get) => fun.apply()
				}
				/** Updates a backup schedule. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def withGoogleFirestoreAdminV1BackupSchedule(body: Schema.GoogleFirestoreAdminV1BackupSchedule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleFirestoreAdminV1BackupSchedule])
				}
				object patch {
					def apply(projectsId :PlayApi, databasesId :PlayApi, backupSchedulesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/backupSchedules/${backupSchedulesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** List backup schedules. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirestoreAdminV1ListBackupSchedulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirestoreAdminV1ListBackupSchedulesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, databasesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/backupSchedules").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleFirestoreAdminV1ListBackupSchedulesResponse]] = (fun: list) => fun.apply()
				}
			}
			object collectionGroups {
				object indexes {
					/** Creates a composite index. This returns a google.longrunning.Operation which may be used to track the status of the creation. The metadata for the operation will be the type IndexOperationMetadata. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
						/** Perform the request */
						def withGoogleFirestoreAdminV1Index(body: Schema.GoogleFirestoreAdminV1Index) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object create {
						def apply(projectsId :PlayApi, databasesId :PlayApi, collectionGroupsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/collectionGroups/${collectionGroupsId}/indexes").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Lists composite indexes. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirestoreAdminV1ListIndexesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirestoreAdminV1ListIndexesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, databasesId :PlayApi, collectionGroupsId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/collectionGroups/${collectionGroupsId}/indexes").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleFirestoreAdminV1ListIndexesResponse]] = (fun: list) => fun.apply()
					}
					/** Gets a composite index. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirestoreAdminV1Index]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirestoreAdminV1Index])
					}
					object get {
						def apply(projectsId :PlayApi, databasesId :PlayApi, collectionGroupsId :PlayApi, indexesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/collectionGroups/${collectionGroupsId}/indexes/${indexesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleFirestoreAdminV1Index]] = (fun: get) => fun.apply()
					}
					/** Deletes a composite index. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, databasesId :PlayApi, collectionGroupsId :PlayApi, indexesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/collectionGroups/${collectionGroupsId}/indexes/${indexesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
				}
				object fields {
					/** Gets the metadata and configuration for a Field. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirestoreAdminV1Field]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirestoreAdminV1Field])
					}
					object get {
						def apply(projectsId :PlayApi, databasesId :PlayApi, collectionGroupsId :PlayApi, fieldsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/collectionGroups/${collectionGroupsId}/fields/${fieldsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleFirestoreAdminV1Field]] = (fun: get) => fun.apply()
					}
					/** Updates a field configuration. Currently, field updates apply only to single field index configuration. However, calls to FirestoreAdmin.UpdateField should provide a field mask to avoid changing any configuration that the caller isn't aware of. The field mask should be specified as: `{ paths: "index_config" }`. This call returns a google.longrunning.Operation which may be used to track the status of the field update. The metadata for the operation will be the type FieldOperationMetadata. To configure the default field settings for the database, use the special `Field` with resource name: `projects/{project_id}/databases/{database_id}/collectionGroups/__default__/fields/&#42;`. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
						/** Perform the request */
						def withGoogleFirestoreAdminV1Field(body: Schema.GoogleFirestoreAdminV1Field) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object patch {
						def apply(projectsId :PlayApi, databasesId :PlayApi, collectionGroupsId :PlayApi, fieldsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/collectionGroups/${collectionGroupsId}/fields/${fieldsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists the field configuration and metadata for this database. Currently, FirestoreAdmin.ListFields only supports listing fields that have been explicitly overridden. To issue this query, call FirestoreAdmin.ListFields with the filter set to `indexConfig.usesAncestorConfig:false` or `ttlConfig:&#42;`. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFirestoreAdminV1ListFieldsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFirestoreAdminV1ListFieldsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, databasesId :PlayApi, collectionGroupsId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/collectionGroups/${collectionGroupsId}/fields").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleFirestoreAdminV1ListFieldsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object documents {
				/** Lists all the collection IDs underneath a document. */
				class listCollectionIds(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def withListCollectionIdsRequest(body: Schema.ListCollectionIdsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ListCollectionIdsResponse])
				}
				object listCollectionIds {
					def apply(projectsId :PlayApi, databasesId :PlayApi, documentsId :PlayApi, documentsId1 :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): listCollectionIds = new listCollectionIds(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents/${documentsId}/${documentsId1}:listCollectionIds").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Lists documents. */
				class listDocuments(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDocumentsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Optional. The collection ID, relative to `parent`, to list. For example: `chatrooms` or `messages`. This is optional, and when not provided, Firestore will list documents from all collections under the provided `parent`. */
					def withCollectionId(collectionId: String) = new listDocuments(req.addQueryStringParameters("collectionId" -> collectionId.toString))
					/** Optional. The maximum number of documents to return in a single response. Firestore may return fewer than this value.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new listDocuments(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A page token, received from a previous `ListDocuments` response. Provide this to retrieve the subsequent page. When paginating, all other parameters (with the exception of `page_size`) must match the values set in the request that generated the page token. */
					def withPageToken(pageToken: String) = new listDocuments(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The optional ordering of the documents to return. For example: `priority desc, __name__ desc`. This mirrors the `ORDER BY` used in Firestore queries but in a string representation. When absent, documents are ordered based on `__name__ ASC`. */
					def withOrderBy(orderBy: String) = new listDocuments(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDocumentsResponse])
				}
				object listDocuments {
					def apply(projectsId :PlayApi, databasesId :PlayApi, collectionId :PlayApi, parent: String, maskFieldPaths: String, transaction: String, readTime: String, showMissing: Boolean)(using signer: RequestSigner, ec: ExecutionContext): listDocuments = new listDocuments(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents/${collectionId}").addQueryStringParameters("parent" -> parent.toString, "mask.fieldPaths" -> maskFieldPaths.toString, "transaction" -> transaction.toString, "readTime" -> readTime.toString, "showMissing" -> showMissing.toString))
					given Conversion[listDocuments, Future[Schema.ListDocumentsResponse]] = (fun: listDocuments) => fun.apply()
				}
				/** Starts a new transaction. */
				class beginTransaction(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def withBeginTransactionRequest(body: Schema.BeginTransactionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BeginTransactionResponse])
				}
				object beginTransaction {
					def apply(projectsId :PlayApi, databasesId :PlayApi, database: String)(using signer: RequestSigner, ec: ExecutionContext): beginTransaction = new beginTransaction(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents:beginTransaction").addQueryStringParameters("database" -> database.toString))
				}
				/** Runs a query. */
				class runQuery(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def withRunQueryRequest(body: Schema.RunQueryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RunQueryResponse])
				}
				object runQuery {
					def apply(projectsId :PlayApi, databasesId :PlayApi, documentsId :PlayApi, documentsId1 :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): runQuery = new runQuery(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents/${documentsId}/${documentsId1}:runQuery").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Creates a new document. */
				class createDocument(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** The client-assigned document ID to use for this document. Optional. If not specified, an ID will be assigned by the service. */
					def withDocumentId(documentId: String) = new createDocument(req.addQueryStringParameters("documentId" -> documentId.toString))
					/** Perform the request */
					def withDocument(body: Schema.Document) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Document])
				}
				object createDocument {
					def apply(projectsId :PlayApi, databasesId :PlayApi, documentsId :PlayApi, parent: String, collectionId: String, maskFieldPaths: String)(using signer: RequestSigner, ec: ExecutionContext): createDocument = new createDocument(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents/${documentsId}/${collectionId}").addQueryStringParameters("parent" -> parent.toString, "mask.fieldPaths" -> maskFieldPaths.toString))
				}
				/** Rolls back a transaction. */
				class rollback(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def withRollbackRequest(body: Schema.RollbackRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object rollback {
					def apply(projectsId :PlayApi, databasesId :PlayApi, database: String)(using signer: RequestSigner, ec: ExecutionContext): rollback = new rollback(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents:rollback").addQueryStringParameters("database" -> database.toString))
				}
				/** Applies a batch of write operations. The BatchWrite method does not apply the write operations atomically and can apply them out of order. Method does not allow more than one write per document. Each write succeeds or fails independently. See the BatchWriteResponse for the success status of each write. If you require an atomically applied set of writes, use Commit instead. */
				class batchWrite(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def withBatchWriteRequest(body: Schema.BatchWriteRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchWriteResponse])
				}
				object batchWrite {
					def apply(projectsId :PlayApi, databasesId :PlayApi, database: String)(using signer: RequestSigner, ec: ExecutionContext): batchWrite = new batchWrite(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents:batchWrite").addQueryStringParameters("database" -> database.toString))
				}
				/** Listens to changes. This method is only available via gRPC or WebChannel (not REST). */
				class listen(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def withListenRequest(body: Schema.ListenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ListenResponse])
				}
				object listen {
					def apply(projectsId :PlayApi, databasesId :PlayApi, database: String)(using signer: RequestSigner, ec: ExecutionContext): listen = new listen(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents:listen").addQueryStringParameters("database" -> database.toString))
				}
				/** Partitions a query by returning partition cursors that can be used to run the query in parallel. The returned partition cursors are split points that can be used by RunQuery as starting/end points for the query results. */
				class partitionQuery(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def withPartitionQueryRequest(body: Schema.PartitionQueryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PartitionQueryResponse])
				}
				object partitionQuery {
					def apply(projectsId :PlayApi, databasesId :PlayApi, documentsId :PlayApi, documentsId1 :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): partitionQuery = new partitionQuery(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents/${documentsId}/${documentsId1}:partitionQuery").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a document. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, databasesId :PlayApi, documentsId :PlayApi, documentsId1 :PlayApi, name: String, currentDocumentExists: Boolean, currentDocumentUpdateTime: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents/${documentsId}/${documentsId1}").addQueryStringParameters("name" -> name.toString, "currentDocument.exists" -> currentDocumentExists.toString, "currentDocument.updateTime" -> currentDocumentUpdateTime.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets a single document. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Document]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Document])
				}
				object get {
					def apply(projectsId :PlayApi, databasesId :PlayApi, documentsId :PlayApi, documentsId1 :PlayApi, name: String, maskFieldPaths: String, transaction: String, readTime: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents/${documentsId}/${documentsId1}").addQueryStringParameters("name" -> name.toString, "mask.fieldPaths" -> maskFieldPaths.toString, "transaction" -> transaction.toString, "readTime" -> readTime.toString))
					given Conversion[get, Future[Schema.Document]] = (fun: get) => fun.apply()
				}
				/** Updates or inserts a document. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def withDocument(body: Schema.Document) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Document])
				}
				object patch {
					def apply(projectsId :PlayApi, databasesId :PlayApi, documentsId :PlayApi, documentsId1 :PlayApi, name: String, updateMaskFieldPaths: String, maskFieldPaths: String, currentDocumentExists: Boolean, currentDocumentUpdateTime: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents/${documentsId}/${documentsId1}").addQueryStringParameters("name" -> name.toString, "updateMask.fieldPaths" -> updateMaskFieldPaths.toString, "mask.fieldPaths" -> maskFieldPaths.toString, "currentDocument.exists" -> currentDocumentExists.toString, "currentDocument.updateTime" -> currentDocumentUpdateTime.toString))
				}
				/** Gets multiple documents. Documents returned by this method are not guaranteed to be returned in the same order that they were requested. */
				class batchGet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def withBatchGetDocumentsRequest(body: Schema.BatchGetDocumentsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchGetDocumentsResponse])
				}
				object batchGet {
					def apply(projectsId :PlayApi, databasesId :PlayApi, database: String)(using signer: RequestSigner, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents:batchGet").addQueryStringParameters("database" -> database.toString))
				}
				/** Streams batches of document updates and deletes, in order. This method is only available via gRPC or WebChannel (not REST). */
				class write(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def withWriteRequest(body: Schema.WriteRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.WriteResponse])
				}
				object write {
					def apply(projectsId :PlayApi, databasesId :PlayApi, database: String)(using signer: RequestSigner, ec: ExecutionContext): write = new write(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents:write").addQueryStringParameters("database" -> database.toString))
				}
				/** Lists documents. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDocumentsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Optional. The collection ID, relative to `parent`, to list. For example: `chatrooms` or `messages`. This is optional, and when not provided, Firestore will list documents from all collections under the provided `parent`. */
					def withCollectionId(collectionId: String) = new list(req.addQueryStringParameters("collectionId" -> collectionId.toString))
					/** Optional. The maximum number of documents to return in a single response. Firestore may return fewer than this value.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A page token, received from a previous `ListDocuments` response. Provide this to retrieve the subsequent page. When paginating, all other parameters (with the exception of `page_size`) must match the values set in the request that generated the page token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The optional ordering of the documents to return. For example: `priority desc, __name__ desc`. This mirrors the `ORDER BY` used in Firestore queries but in a string representation. When absent, documents are ordered based on `__name__ ASC`. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDocumentsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, databasesId :PlayApi, documentsId :PlayApi, documentsId1 :PlayApi, collectionId :PlayApi, parent: String, maskFieldPaths: String, transaction: String, readTime: String, showMissing: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents/${documentsId}/${documentsId1}/${collectionId}").addQueryStringParameters("parent" -> parent.toString, "mask.fieldPaths" -> maskFieldPaths.toString, "transaction" -> transaction.toString, "readTime" -> readTime.toString, "showMissing" -> showMissing.toString))
					given Conversion[list, Future[Schema.ListDocumentsResponse]] = (fun: list) => fun.apply()
				}
				/** Commits a transaction, while optionally updating documents. */
				class commit(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def withCommitRequest(body: Schema.CommitRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CommitResponse])
				}
				object commit {
					def apply(projectsId :PlayApi, databasesId :PlayApi, database: String)(using signer: RequestSigner, ec: ExecutionContext): commit = new commit(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents:commit").addQueryStringParameters("database" -> database.toString))
				}
				/** Runs an aggregation query. Rather than producing Document results like Firestore.RunQuery, this API allows running an aggregation to produce a series of AggregationResult server-side. High-Level Example: ``` -- Return the number of documents in table given a filter. SELECT COUNT(&#42;) FROM ( SELECT &#42; FROM k where a = true ); ``` */
				class runAggregationQuery(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/datastore""")
					/** Perform the request */
					def withRunAggregationQueryRequest(body: Schema.RunAggregationQueryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RunAggregationQueryResponse])
				}
				object runAggregationQuery {
					def apply(projectsId :PlayApi, databasesId :PlayApi, documentsId :PlayApi, documentsId1 :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): runAggregationQuery = new runAggregationQuery(ws.url(BASE_URL + s"v1/projects/${projectsId}/databases/${databasesId}/documents/${documentsId}/${documentsId1}:runAggregationQuery").addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
	}
}
