package com.boresjo.play.api.google.memcache

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://memcache.googleapis.com/"

	object projects {
		object locations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel")).addQueryStringParameters("name" -> name.toString))
				}
			}
			object instances {
				class updateParameters(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withUpdateParametersRequest(body: Schema.UpdateParametersRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
				}
				object updateParameters {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateParameters = new updateParameters(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:updateParameters")).addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withInstance(body: Schema.Instance) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, instanceId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances")).addQueryStringParameters("parent" -> parent.toString, "instanceId" -> instanceId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class applyParameters(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withApplyParametersRequest(body: Schema.ApplyParametersRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object applyParameters {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): applyParameters = new applyParameters(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:applyParameters")).addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Instance]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Instance])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Instance]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInstancesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListInstancesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.ListInstancesResponse]] = (fun: list) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withInstance(body: Schema.Instance) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class upgrade(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudMemcacheV1UpgradeInstanceRequest(body: Schema.GoogleCloudMemcacheV1UpgradeInstanceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object upgrade {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): upgrade = new upgrade(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:upgrade")).addQueryStringParameters("name" -> name.toString))
				}
				class rescheduleMaintenance(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRescheduleMaintenanceRequest(body: Schema.RescheduleMaintenanceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object rescheduleMaintenance {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, instance: String)(using auth: AuthToken, ec: ExecutionContext): rescheduleMaintenance = new rescheduleMaintenance(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:rescheduleMaintenance")).addQueryStringParameters("instance" -> instance.toString))
				}
			}
		}
	}
}
