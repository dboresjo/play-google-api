package com.boresjo.play.api.google.datalineage

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://datalineage.googleapis.com/"

	object projects {
		object locations {
			class searchLinks(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudDatacatalogLineageV1SearchLinksRequest(body: Schema.GoogleCloudDatacatalogLineageV1SearchLinksRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1SearchLinksResponse])
			}
			object searchLinks {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): searchLinks = new searchLinks(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:searchLinks")).addQueryStringParameters("parent" -> parent.toString))
			}
			class processOpenLineageRunEvent(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogLineageV1ProcessOpenLineageRunEventResponse]) {
				def apply() = req.execute("POST").map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1ProcessOpenLineageRunEventResponse])
			}
			object processOpenLineageRunEvent {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, requestId: String)(using auth: AuthToken, ec: ExecutionContext): processOpenLineageRunEvent = new processOpenLineageRunEvent(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:processOpenLineageRunEvent")).addQueryStringParameters("parent" -> parent.toString, "requestId" -> requestId.toString))
				given Conversion[processOpenLineageRunEvent, Future[Schema.GoogleCloudDatacatalogLineageV1ProcessOpenLineageRunEventResponse]] = (fun: processOpenLineageRunEvent) => fun.apply()
			}
			class batchSearchLinkProcesses(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudDatacatalogLineageV1BatchSearchLinkProcessesRequest(body: Schema.GoogleCloudDatacatalogLineageV1BatchSearchLinkProcessesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1BatchSearchLinkProcessesResponse])
			}
			object batchSearchLinkProcesses {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchSearchLinkProcesses = new batchSearchLinkProcesses(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:batchSearchLinkProcesses")).addQueryStringParameters("parent" -> parent.toString))
			}
			object operations {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, filter: String, pageSize: Int, pageToken: String, name: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations")).addQueryStringParameters("filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "name" -> name.toString))
					given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleLongrunningCancelOperationRequest(body: Schema.GoogleLongrunningCancelOperationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel")).addQueryStringParameters("name" -> name.toString))
				}
			}
			object processes {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDatacatalogLineageV1Process(body: Schema.GoogleCloudDatacatalogLineageV1Process) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1Process])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, requestId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes")).addQueryStringParameters("parent" -> parent.toString, "requestId" -> requestId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, allowMissing: Boolean, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}")).addQueryStringParameters("allowMissing" -> allowMissing.toString, "name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogLineageV1Process]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1Process])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDatacatalogLineageV1Process]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDatacatalogLineageV1Process(body: Schema.GoogleCloudDatacatalogLineageV1Process) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1Process])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, name: String, allowMissing: Boolean, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}")).addQueryStringParameters("name" -> name.toString, "allowMissing" -> allowMissing.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogLineageV1ListProcessesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1ListProcessesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageSize: Int, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes")).addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudDatacatalogLineageV1ListProcessesResponse]] = (fun: list) => fun.apply()
				}
				object runs {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDatacatalogLineageV1Run(body: Schema.GoogleCloudDatacatalogLineageV1Run) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1Run])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, requestId: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}/runs")).addQueryStringParameters("requestId" -> requestId.toString, "parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, runsId :PlayApi, name: String, allowMissing: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}/runs/${runsId}")).addQueryStringParameters("name" -> name.toString, "allowMissing" -> allowMissing.toString))
						given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogLineageV1Run]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1Run])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, runsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}/runs/${runsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDatacatalogLineageV1Run]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDatacatalogLineageV1Run(body: Schema.GoogleCloudDatacatalogLineageV1Run) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1Run])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, runsId :PlayApi, name: String, updateMask: String, allowMissing: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}/runs/${runsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "allowMissing" -> allowMissing.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogLineageV1ListRunsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1ListRunsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}/runs")).addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudDatacatalogLineageV1ListRunsResponse]] = (fun: list) => fun.apply()
					}
					object lineageEvents {
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, runsId :PlayApi, lineageEventsId :PlayApi, name: String, allowMissing: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}/runs/${runsId}/lineageEvents/${lineageEventsId}")).addQueryStringParameters("name" -> name.toString, "allowMissing" -> allowMissing.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDatacatalogLineageV1LineageEvent(body: Schema.GoogleCloudDatacatalogLineageV1LineageEvent) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1LineageEvent])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, runsId :PlayApi, parent: String, requestId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}/runs/${runsId}/lineageEvents")).addQueryStringParameters("parent" -> parent.toString, "requestId" -> requestId.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogLineageV1ListLineageEventsResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1ListLineageEventsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, runsId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}/runs/${runsId}/lineageEvents")).addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
							given Conversion[list, Future[Schema.GoogleCloudDatacatalogLineageV1ListLineageEventsResponse]] = (fun: list) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatacatalogLineageV1LineageEvent]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudDatacatalogLineageV1LineageEvent])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, processesId :PlayApi, runsId :PlayApi, lineageEventsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processes/${processesId}/runs/${runsId}/lineageEvents/${lineageEventsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDatacatalogLineageV1LineageEvent]] = (fun: get) => fun.apply()
						}
					}
				}
			}
		}
	}
}
