package com.boresjo.play.api.google.cloudtasks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://cloudtasks.googleapis.com/"

	object projects {
		object locations {
			class getCmekConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CmekConfig]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CmekConfig])
			}
			object getCmekConfig {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getCmekConfig = new getCmekConfig(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/cmekConfig").addQueryStringParameters("name" -> name.toString))
				given Conversion[getCmekConfig, Future[Schema.CmekConfig]] = (fun: getCmekConfig) => fun.apply()
			}
			class updateCmekConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCmekConfig(body: Schema.CmekConfig) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CmekConfig])
			}
			object updateCmekConfig {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateCmekConfig = new updateCmekConfig(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/cmekConfig").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object queues {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, queuesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/queues/${queuesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class resume(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withResumeQueueRequest(body: Schema.ResumeQueueRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Queue])
				}
				object resume {
					def apply(projectsId :PlayApi, locationsId :PlayApi, queuesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): resume = new resume(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/queues/${queuesId}:resume").addQueryStringParameters("name" -> name.toString))
				}
				class purge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withPurgeQueueRequest(body: Schema.PurgeQueueRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Queue])
				}
				object purge {
					def apply(projectsId :PlayApi, locationsId :PlayApi, queuesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/queues/${queuesId}:purge").addQueryStringParameters("name" -> name.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, queuesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/queues/${queuesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, queuesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/queues/${queuesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Queue]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Queue])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, queuesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/queues/${queuesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Queue]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withQueue(body: Schema.Queue) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Queue])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, queuesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/queues/${queuesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListQueuesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListQueuesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/queues").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListQueuesResponse]] = (fun: list) => fun.apply()
				}
				class pause(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withPauseQueueRequest(body: Schema.PauseQueueRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Queue])
				}
				object pause {
					def apply(projectsId :PlayApi, locationsId :PlayApi, queuesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): pause = new pause(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/queues/${queuesId}:pause").addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withQueue(body: Schema.Queue) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Queue])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/queues").addQueryStringParameters("parent" -> parent.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, queuesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/queues/${queuesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object tasks {
					class run(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withRunTaskRequest(body: Schema.RunTaskRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Task])
					}
					object run {
						def apply(projectsId :PlayApi, locationsId :PlayApi, queuesId :PlayApi, tasksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): run = new run(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/queues/${queuesId}/tasks/${tasksId}:run").addQueryStringParameters("name" -> name.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCreateTaskRequest(body: Schema.CreateTaskRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Task])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, queuesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/queues/${queuesId}/tasks").addQueryStringParameters("parent" -> parent.toString))
					}
					class buffer(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. Task ID for the task being created. If not provided, Cloud Tasks generates an ID for the task. */
						def withTaskId(taskId: String) = new buffer(req.addQueryStringParameters("taskId" -> taskId.toString))
						def withBufferTaskRequest(body: Schema.BufferTaskRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BufferTaskResponse])
					}
					object buffer {
						def apply(projectsId :PlayApi, locationsId :PlayApi, queuesId :PlayApi, taskId :PlayApi, queue: String)(using auth: AuthToken, ec: ExecutionContext): buffer = new buffer(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/queues/${queuesId}/tasks/${taskId}:buffer").addQueryStringParameters("queue" -> queue.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, queuesId :PlayApi, tasksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/queues/${queuesId}/tasks/${tasksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Task]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Task])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, queuesId :PlayApi, tasksId :PlayApi, name: String, responseView: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/queues/${queuesId}/tasks/${tasksId}").addQueryStringParameters("name" -> name.toString, "responseView" -> responseView.toString))
						given Conversion[get, Future[Schema.Task]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTasksResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListTasksResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, queuesId :PlayApi, parent: String, responseView: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/queues/${queuesId}/tasks").addQueryStringParameters("parent" -> parent.toString, "responseView" -> responseView.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListTasksResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
}
