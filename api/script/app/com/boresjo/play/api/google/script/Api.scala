package com.boresjo.play.api.google.script

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://script.googleapis.com/"

	object projects {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCreateProjectRequest(body: Schema.CreateProjectRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Project])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects")).addQueryStringParameters())
		}
		class getMetrics(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Metrics]) {
			/** Optional field indicating a specific deployment to retrieve metrics from. */
			def withMetricsFilterDeploymentId(metricsFilterDeploymentId: String) = new getMetrics(req.addQueryStringParameters("metricsFilter.deploymentId" -> metricsFilterDeploymentId.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.Metrics])
		}
		object getMetrics {
			def apply(metricsGranularity: String, scriptId: String)(using auth: AuthToken, ec: ExecutionContext): getMetrics = new getMetrics(auth(ws.url(BASE_URL + s"v1/projects/${scriptId}/metrics")).addQueryStringParameters("metricsGranularity" -> metricsGranularity.toString))
			given Conversion[getMetrics, Future[Schema.Metrics]] = (fun: getMetrics) => fun.apply()
		}
		class getContent(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Content]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Content])
		}
		object getContent {
			def apply(scriptId: String, versionNumber: Int)(using auth: AuthToken, ec: ExecutionContext): getContent = new getContent(auth(ws.url(BASE_URL + s"v1/projects/${scriptId}/content")).addQueryStringParameters("versionNumber" -> versionNumber.toString))
			given Conversion[getContent, Future[Schema.Content]] = (fun: getContent) => fun.apply()
		}
		class updateContent(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withContent(body: Schema.Content) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Content])
		}
		object updateContent {
			def apply(scriptId: String)(using auth: AuthToken, ec: ExecutionContext): updateContent = new updateContent(auth(ws.url(BASE_URL + s"v1/projects/${scriptId}/content")).addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Project]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Project])
		}
		object get {
			def apply(scriptId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${scriptId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.Project]] = (fun: get) => fun.apply()
		}
		object versions {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withVersion(body: Schema.Version) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Version])
			}
			object create {
				def apply(scriptId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${scriptId}/versions")).addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListVersionsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListVersionsResponse])
			}
			object list {
				def apply(pageSize: Int, scriptId: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${scriptId}/versions")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListVersionsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Version]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Version])
			}
			object get {
				def apply(scriptId: String, versionNumber: Int)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${scriptId}/versions/${versionNumber}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.Version]] = (fun: get) => fun.apply()
			}
		}
		object deployments {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withDeploymentConfig(body: Schema.DeploymentConfig) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Deployment])
			}
			object create {
				def apply(scriptId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${scriptId}/deployments")).addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(scriptId: String, deploymentId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${scriptId}/deployments/${deploymentId}")).addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Deployment]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Deployment])
			}
			object get {
				def apply(scriptId: String, deploymentId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${scriptId}/deployments/${deploymentId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.Deployment]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUpdateDeploymentRequest(body: Schema.UpdateDeploymentRequest) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Deployment])
			}
			object update {
				def apply(deploymentId: String, scriptId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v1/projects/${scriptId}/deployments/${deploymentId}")).addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDeploymentsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListDeploymentsResponse])
			}
			object list {
				def apply(scriptId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${scriptId}/deployments")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListDeploymentsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object scripts {
		class run(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withExecutionRequest(body: Schema.ExecutionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
		}
		object run {
			def apply(scriptId: String)(using auth: AuthToken, ec: ExecutionContext): run = new run(auth(ws.url(BASE_URL + s"v1/scripts/${scriptId}:run")).addQueryStringParameters())
		}
	}
	object processes {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListUserProcessesResponse]) {
			/** Optional field used to limit returned processes to those originating from a script function with the given function name. */
			def withUserProcessFilterFunctionName(userProcessFilterFunctionName: String) = new list(req.addQueryStringParameters("userProcessFilter.functionName" -> userProcessFilterFunctionName.toString))
			/** Optional field used to limit returned processes to those that completed on or before the given timestamp.<br>Format: google-datetime */
			def withUserProcessFilterEndTime(userProcessFilterEndTime: String) = new list(req.addQueryStringParameters("userProcessFilter.endTime" -> userProcessFilterEndTime.toString))
			/** Optional field used to limit returned processes to those originating from projects with project names containing a specific string. */
			def withUserProcessFilterProjectName(userProcessFilterProjectName: String) = new list(req.addQueryStringParameters("userProcessFilter.projectName" -> userProcessFilterProjectName.toString))
			/** Optional field used to limit returned processes to those that were started on or after the given timestamp.<br>Format: google-datetime */
			def withUserProcessFilterStartTime(userProcessFilterStartTime: String) = new list(req.addQueryStringParameters("userProcessFilter.startTime" -> userProcessFilterStartTime.toString))
			/** Optional field used to limit returned processes to those having one of the specified process statuses.<br>Possible values:<br>PROCESS_STATUS_UNSPECIFIED: Unspecified status.<br>RUNNING: The process is currently running.<br>PAUSED: The process has paused.<br>COMPLETED: The process has completed.<br>CANCELED: The process was cancelled.<br>FAILED: The process failed.<br>TIMED_OUT: The process timed out.<br>UNKNOWN: Process status unknown.<br>DELAYED: The process is delayed, waiting for quota.<br>EXECUTION_DISABLED: AppsScript executions are disabled by Admin. */
			def withUserProcessFilterStatuses(userProcessFilterStatuses: String) = new list(req.addQueryStringParameters("userProcessFilter.statuses" -> userProcessFilterStatuses.toString))
			/** Optional field used to limit returned processes to those having one of the specified process types.<br>Possible values:<br>PROCESS_TYPE_UNSPECIFIED: Unspecified type.<br>ADD_ON: The process was started from an add-on entry point.<br>EXECUTION_API: The process was started using the Apps Script API.<br>TIME_DRIVEN: The process was started from a time-based trigger.<br>TRIGGER: The process was started from an event-based trigger.<br>WEBAPP: The process was started from a web app entry point.<br>EDITOR: The process was started using the Apps Script IDE.<br>SIMPLE_TRIGGER: The process was started from a G Suite simple trigger.<br>MENU: The process was started from a G Suite menu item.<br>BATCH_TASK: The process was started as a task in a batch job. */
			def withUserProcessFilterTypes(userProcessFilterTypes: String) = new list(req.addQueryStringParameters("userProcessFilter.types" -> userProcessFilterTypes.toString))
			/** Optional field used to limit returned processes to those originating from projects with a specific script ID. */
			def withUserProcessFilterScriptId(userProcessFilterScriptId: String) = new list(req.addQueryStringParameters("userProcessFilter.scriptId" -> userProcessFilterScriptId.toString))
			/** Optional field used to limit returned processes to those originating from projects with a specific deployment ID. */
			def withUserProcessFilterDeploymentId(userProcessFilterDeploymentId: String) = new list(req.addQueryStringParameters("userProcessFilter.deploymentId" -> userProcessFilterDeploymentId.toString))
			/** Optional field used to limit returned processes to those having one of the specified user access levels.<br>Possible values:<br>USER_ACCESS_LEVEL_UNSPECIFIED: User access level unspecified<br>NONE: The user has no access.<br>READ: The user has read-only access.<br>WRITE: The user has write access.<br>OWNER: The user is an owner. */
			def withUserProcessFilterUserAccessLevels(userProcessFilterUserAccessLevels: String) = new list(req.addQueryStringParameters("userProcessFilter.userAccessLevels" -> userProcessFilterUserAccessLevels.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.ListUserProcessesResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/processes")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListUserProcessesResponse]] = (fun: list) => fun.apply()
		}
		class listScriptProcesses(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListScriptProcessesResponse]) {
			/** Optional field used to limit returned processes to those that were started on or after the given timestamp.<br>Format: google-datetime */
			def withScriptProcessFilterStartTime(scriptProcessFilterStartTime: String) = new listScriptProcesses(req.addQueryStringParameters("scriptProcessFilter.startTime" -> scriptProcessFilterStartTime.toString))
			/** Optional field used to limit returned processes to those having one of the specified process statuses.<br>Possible values:<br>PROCESS_STATUS_UNSPECIFIED: Unspecified status.<br>RUNNING: The process is currently running.<br>PAUSED: The process has paused.<br>COMPLETED: The process has completed.<br>CANCELED: The process was cancelled.<br>FAILED: The process failed.<br>TIMED_OUT: The process timed out.<br>UNKNOWN: Process status unknown.<br>DELAYED: The process is delayed, waiting for quota.<br>EXECUTION_DISABLED: AppsScript executions are disabled by Admin. */
			def withScriptProcessFilterStatuses(scriptProcessFilterStatuses: String) = new listScriptProcesses(req.addQueryStringParameters("scriptProcessFilter.statuses" -> scriptProcessFilterStatuses.toString))
			/** Optional field used to limit returned processes to those originating from projects with a specific deployment ID. */
			def withScriptProcessFilterDeploymentId(scriptProcessFilterDeploymentId: String) = new listScriptProcesses(req.addQueryStringParameters("scriptProcessFilter.deploymentId" -> scriptProcessFilterDeploymentId.toString))
			/** Optional field used to limit returned processes to those that completed on or before the given timestamp.<br>Format: google-datetime */
			def withScriptProcessFilterEndTime(scriptProcessFilterEndTime: String) = new listScriptProcesses(req.addQueryStringParameters("scriptProcessFilter.endTime" -> scriptProcessFilterEndTime.toString))
			/** Optional field used to limit returned processes to those originating from a script function with the given function name. */
			def withScriptProcessFilterFunctionName(scriptProcessFilterFunctionName: String) = new listScriptProcesses(req.addQueryStringParameters("scriptProcessFilter.functionName" -> scriptProcessFilterFunctionName.toString))
			/** Optional field used to limit returned processes to those having one of the specified user access levels.<br>Possible values:<br>USER_ACCESS_LEVEL_UNSPECIFIED: User access level unspecified<br>NONE: The user has no access.<br>READ: The user has read-only access.<br>WRITE: The user has write access.<br>OWNER: The user is an owner. */
			def withScriptProcessFilterUserAccessLevels(scriptProcessFilterUserAccessLevels: String) = new listScriptProcesses(req.addQueryStringParameters("scriptProcessFilter.userAccessLevels" -> scriptProcessFilterUserAccessLevels.toString))
			/** Optional field used to limit returned processes to those having one of the specified process types.<br>Possible values:<br>PROCESS_TYPE_UNSPECIFIED: Unspecified type.<br>ADD_ON: The process was started from an add-on entry point.<br>EXECUTION_API: The process was started using the Apps Script API.<br>TIME_DRIVEN: The process was started from a time-based trigger.<br>TRIGGER: The process was started from an event-based trigger.<br>WEBAPP: The process was started from a web app entry point.<br>EDITOR: The process was started using the Apps Script IDE.<br>SIMPLE_TRIGGER: The process was started from a G Suite simple trigger.<br>MENU: The process was started from a G Suite menu item.<br>BATCH_TASK: The process was started as a task in a batch job. */
			def withScriptProcessFilterTypes(scriptProcessFilterTypes: String) = new listScriptProcesses(req.addQueryStringParameters("scriptProcessFilter.types" -> scriptProcessFilterTypes.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.ListScriptProcessesResponse])
		}
		object listScriptProcesses {
			def apply(pageSize: Int, pageToken: String, scriptId: String)(using auth: AuthToken, ec: ExecutionContext): listScriptProcesses = new listScriptProcesses(auth(ws.url(BASE_URL + s"v1/processes:listScriptProcesses")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "scriptId" -> scriptId.toString))
			given Conversion[listScriptProcesses, Future[Schema.ListScriptProcessesResponse]] = (fun: listScriptProcesses) => fun.apply()
		}
	}
}
