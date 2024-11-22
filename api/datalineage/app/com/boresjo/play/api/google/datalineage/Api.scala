package com.boresjo.play.api.google.datalineage

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://datalineage.googleapis.com/"

	object projects {
		object locations {
			/** Retrieve a list of links connected to a specific asset. Links represent the data flow between &#42;&#42;source&#42;&#42; (upstream) and &#42;&#42;target&#42;&#42; (downstream) assets in transformation pipelines. Links are stored in the same project as the Lineage Events that create them. You can retrieve links in every project where you have the `datalineage.events.get` permission. The project provided in the URL is used for Billing and Quota. */
			class searchLinks(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudDatacatalogLineageV1SearchLinksRequest(body: Schema.GoogleCloudDatacatalogLineageV1SearchLinksRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1SearchLinksResponse])
			}
			object searchLinks {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): searchLinks = new searchLinks(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:searchLinks").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Creates new lineage events together with their parents: process and run. Updates the process and run if they already exist. Mapped from Open Lineage specification: https://github.com/OpenLineage/OpenLineage/blob/main/spec/OpenLineage.json. */
			class processOpenLineageRunEvent(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogLineageV1ProcessOpenLineageRunEventResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1ProcessOpenLineageRunEventResponse])
			}
			object processOpenLineageRunEvent {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, requestId: String)(using signer: RequestSigner, ec: ExecutionContext): processOpenLineageRunEvent = new processOpenLineageRunEvent(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:processOpenLineageRunEvent").addQueryStringParameters("parent" -> parent.toString, "requestId" -> requestId.toString))
				given Conversion[processOpenLineageRunEvent, Future[Schema.GoogleCloudDatacatalogLineageV1ProcessOpenLineageRunEventResponse]] = (fun: processOpenLineageRunEvent) => fun.apply()
			}
			/** Retrieve information about LineageProcesses associated with specific links. LineageProcesses are transformation pipelines that result in data flowing from &#42;&#42;source&#42;&#42; to &#42;&#42;target&#42;&#42; assets. Links between assets represent this operation. If you have specific link names, you can use this method to verify which LineageProcesses contribute to creating those links. See the SearchLinks method for more information on how to retrieve link name. You can retrieve the LineageProcess information in every project where you have the `datalineage.events.get` permission. The project provided in the URL is used for Billing and Quota. */
			class batchSearchLinkProcesses(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudDatacatalogLineageV1BatchSearchLinkProcessesRequest(body: Schema.GoogleCloudDatacatalogLineageV1BatchSearchLinkProcessesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1BatchSearchLinkProcessesResponse])
			}
			object batchSearchLinkProcesses {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchSearchLinkProcesses = new batchSearchLinkProcesses(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:batchSearchLinkProcesses").addQueryStringParameters("parent" -> parent.toString))
			}
			object operations {
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
				}
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, filter: String, pageSize: Int, pageToken: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "name" -> name.toString))
					given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleLongrunningCancelOperationRequest(body: Schema.GoogleLongrunningCancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
			object processes {
				/** Creates a new process. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDatacatalogLineageV1Process(body: Schema.GoogleCloudDatacatalogLineageV1Process) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1Process])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, requestId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes").addQueryStringParameters("parent" -> parent.toString, "requestId" -> requestId.toString))
				}
				/** Deletes the process with the specified name. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, allowMissing: Boolean, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}").addQueryStringParameters("allowMissing" -> allowMissing.toString, "name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
				}
				/** Gets the details of the specified process. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogLineageV1Process]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1Process])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDatacatalogLineageV1Process]] = (fun: get) => fun.apply()
				}
				/** Updates a process. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDatacatalogLineageV1Process(body: Schema.GoogleCloudDatacatalogLineageV1Process) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1Process])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, name: String, allowMissing: Boolean, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}").addQueryStringParameters("name" -> name.toString, "allowMissing" -> allowMissing.toString, "updateMask" -> updateMask.toString))
				}
				/** List processes in the given project and location. List order is descending by insertion time. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogLineageV1ListProcessesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1ListProcessesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageSize: Int, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes").addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudDatacatalogLineageV1ListProcessesResponse]] = (fun: list) => fun.apply()
				}
				object runs {
					/** Creates a new run. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDatacatalogLineageV1Run(body: Schema.GoogleCloudDatacatalogLineageV1Run) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1Run])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, requestId: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}/runs").addQueryStringParameters("requestId" -> requestId.toString, "parent" -> parent.toString))
					}
					/** Deletes the run with the specified name. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, runsId :PlayApi, name: String, allowMissing: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}/runs/${runsId}").addQueryStringParameters("name" -> name.toString, "allowMissing" -> allowMissing.toString))
						given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
					}
					/** Gets the details of the specified run. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogLineageV1Run]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1Run])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, runsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}/runs/${runsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDatacatalogLineageV1Run]] = (fun: get) => fun.apply()
					}
					/** Updates a run. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDatacatalogLineageV1Run(body: Schema.GoogleCloudDatacatalogLineageV1Run) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1Run])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, runsId :PlayApi, name: String, updateMask: String, allowMissing: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}/runs/${runsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "allowMissing" -> allowMissing.toString))
					}
					/** Lists runs in the given project and location. List order is descending by `start_time`. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogLineageV1ListRunsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1ListRunsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}/runs").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudDatacatalogLineageV1ListRunsResponse]] = (fun: list) => fun.apply()
					}
					object lineageEvents {
						/** Deletes the lineage event with the specified name. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, runsId :PlayApi, lineageEventsId :PlayApi, name: String, allowMissing: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}/runs/${runsId}/lineageEvents/${lineageEventsId}").addQueryStringParameters("name" -> name.toString, "allowMissing" -> allowMissing.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						/** Creates a new lineage event. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDatacatalogLineageV1LineageEvent(body: Schema.GoogleCloudDatacatalogLineageV1LineageEvent) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1LineageEvent])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, runsId :PlayApi, parent: String, requestId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}/runs/${runsId}/lineageEvents").addQueryStringParameters("parent" -> parent.toString, "requestId" -> requestId.toString))
						}
						/** Lists lineage events in the given project and location. The list order is not defined. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogLineageV1ListLineageEventsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1ListLineageEventsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, runsId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}/runs/${runsId}/lineageEvents").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
							given Conversion[list, Future[Schema.GoogleCloudDatacatalogLineageV1ListLineageEventsResponse]] = (fun: list) => fun.apply()
						}
						/** Gets details of a specified lineage event. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogLineageV1LineageEvent]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1LineageEvent])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, runsId :PlayApi, lineageEventsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}/runs/${runsId}/lineageEvents/${lineageEventsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDatacatalogLineageV1LineageEvent]] = (fun: get) => fun.apply()
						}
					}
				}
			}
		}
	}
}
