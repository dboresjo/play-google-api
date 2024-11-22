package com.boresjo.play.api.google.script

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
		"""https://www.googleapis.com/auth/script.metrics""" /* View Google Apps Script project's metrics */,
		"""https://www.google.com/m8/feeds""" /* See, edit, download, and permanently delete your contacts */,
		"""https://www.googleapis.com/auth/spreadsheets""" /* See, edit, create, and delete all your Google Sheets spreadsheets */,
		"""https://www.googleapis.com/auth/forms""" /* View and manage your forms in Google Drive */,
		"""https://www.googleapis.com/auth/script.deployments""" /* Create and update Google Apps Script deployments */,
		"""https://www.googleapis.com/auth/drive""" /* See, edit, create, and delete all of your Google Drive files */,
		"""https://www.googleapis.com/auth/documents""" /* See, edit, create, and delete all your Google Docs documents */,
		"""https://mail.google.com/""" /* Read, compose, send, and permanently delete all your email from Gmail */,
		"""https://www.googleapis.com/auth/script.projects.readonly""" /* View Google Apps Script projects */,
		"""https://www.googleapis.com/auth/script.projects""" /* Create and update Google Apps Script projects */,
		"""https://www.google.com/calendar/feeds""" /* See, edit, share, and permanently delete all the calendars you can access using Google Calendar */,
		"""https://www.googleapis.com/auth/userinfo.email""" /* See your primary Google Account email address */,
		"""https://www.googleapis.com/auth/admin.directory.group""" /* View and manage the provisioning of groups on your domain */,
		"""https://www.googleapis.com/auth/script.deployments.readonly""" /* View Google Apps Script deployments */,
		"""https://www.googleapis.com/auth/groups""" /* View and manage your Google Groups */,
		"""https://www.googleapis.com/auth/admin.directory.user""" /* View and manage the provisioning of users on your domain */,
		"""https://www.googleapis.com/auth/script.processes""" /* View Google Apps Script processes */,
		"""https://www.googleapis.com/auth/forms.currentonly""" /* View and manage forms that this application has been installed in */
	)

	private val BASE_URL = "https://script.googleapis.com/"

	object projects {
		/** Creates a new, empty script project with no script files and a base manifest file. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/script.projects""")
			/** Perform the request */
			def withCreateProjectRequest(body: Schema.CreateProjectRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Project])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects").addQueryStringParameters())
		}
		/** Get metrics data for scripts, such as number of executions and active users. */
		class getMetrics(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Metrics]) {
			val scopes = Seq("""https://www.googleapis.com/auth/script.metrics""")
			/** Optional field indicating a specific deployment to retrieve metrics from. */
			def withMetricsFilterDeploymentId(metricsFilterDeploymentId: String) = new getMetrics(req.addQueryStringParameters("metricsFilter.deploymentId" -> metricsFilterDeploymentId.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Metrics])
		}
		object getMetrics {
			def apply(metricsGranularity: String, scriptId: String)(using signer: RequestSigner, ec: ExecutionContext): getMetrics = new getMetrics(ws.url(BASE_URL + s"v1/projects/${scriptId}/metrics").addQueryStringParameters("metricsGranularity" -> metricsGranularity.toString))
			given Conversion[getMetrics, Future[Schema.Metrics]] = (fun: getMetrics) => fun.apply()
		}
		/** Gets the content of the script project, including the code source and metadata for each script file. */
		class getContent(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Content]) {
			val scopes = Seq("""https://www.googleapis.com/auth/script.projects""", """https://www.googleapis.com/auth/script.projects.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Content])
		}
		object getContent {
			def apply(scriptId: String, versionNumber: Int)(using signer: RequestSigner, ec: ExecutionContext): getContent = new getContent(ws.url(BASE_URL + s"v1/projects/${scriptId}/content").addQueryStringParameters("versionNumber" -> versionNumber.toString))
			given Conversion[getContent, Future[Schema.Content]] = (fun: getContent) => fun.apply()
		}
		/** Updates the content of the specified script project. This content is stored as the HEAD version, and is used when the script is executed as a trigger, in the script editor, in add-on preview mode, or as a web app or Apps Script API in development mode. This clears all the existing files in the project. */
		class updateContent(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/script.projects""")
			/** Perform the request */
			def withContent(body: Schema.Content) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Content])
		}
		object updateContent {
			def apply(scriptId: String)(using signer: RequestSigner, ec: ExecutionContext): updateContent = new updateContent(ws.url(BASE_URL + s"v1/projects/${scriptId}/content").addQueryStringParameters())
		}
		/** Gets a script project's metadata. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Project]) {
			val scopes = Seq("""https://www.googleapis.com/auth/script.projects""", """https://www.googleapis.com/auth/script.projects.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Project])
		}
		object get {
			def apply(scriptId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${scriptId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Project]] = (fun: get) => fun.apply()
		}
		object versions {
			/** Creates a new immutable version using the current code, with a unique version number. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/script.projects""")
				/** Perform the request */
				def withVersion(body: Schema.Version) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Version])
			}
			object create {
				def apply(scriptId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${scriptId}/versions").addQueryStringParameters())
			}
			/** List the versions of a script project. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListVersionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/script.projects""", """https://www.googleapis.com/auth/script.projects.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListVersionsResponse])
			}
			object list {
				def apply(pageSize: Int, scriptId: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${scriptId}/versions").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListVersionsResponse]] = (fun: list) => fun.apply()
			}
			/** Gets a version of a script project. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Version]) {
				val scopes = Seq("""https://www.googleapis.com/auth/script.projects""", """https://www.googleapis.com/auth/script.projects.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Version])
			}
			object get {
				def apply(scriptId: String, versionNumber: Int)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${scriptId}/versions/${versionNumber}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Version]] = (fun: get) => fun.apply()
			}
		}
		object deployments {
			/** Creates a deployment of an Apps Script project. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/script.deployments""")
				/** Perform the request */
				def withDeploymentConfig(body: Schema.DeploymentConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Deployment])
			}
			object create {
				def apply(scriptId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${scriptId}/deployments").addQueryStringParameters())
			}
			/** Deletes a deployment of an Apps Script project. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/script.deployments""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(scriptId: String, deploymentId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${scriptId}/deployments/${deploymentId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a deployment of an Apps Script project. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Deployment]) {
				val scopes = Seq("""https://www.googleapis.com/auth/script.deployments""", """https://www.googleapis.com/auth/script.deployments.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Deployment])
			}
			object get {
				def apply(scriptId: String, deploymentId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${scriptId}/deployments/${deploymentId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Deployment]] = (fun: get) => fun.apply()
			}
			/** Updates a deployment of an Apps Script project. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/script.deployments""")
				/** Perform the request */
				def withUpdateDeploymentRequest(body: Schema.UpdateDeploymentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Deployment])
			}
			object update {
				def apply(deploymentId: String, scriptId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/projects/${scriptId}/deployments/${deploymentId}").addQueryStringParameters())
			}
			/** Lists the deployments of an Apps Script project. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDeploymentsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/script.deployments""", """https://www.googleapis.com/auth/script.deployments.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDeploymentsResponse])
			}
			object list {
				def apply(scriptId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${scriptId}/deployments").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListDeploymentsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object scripts {
		/** Runs a function in an Apps Script project. The script project must be deployed for use with the Apps Script API and the calling application must share the same Cloud Platform project. This method requires authorization with an OAuth 2.0 token that includes at least one of the scopes listed in the [Authorization](#authorization-scopes) section; script projects that do not require authorization cannot be executed through this API. To find the correct scopes to include in the authentication token, open the script project &#42;&#42;Overview&#42;&#42; page and scroll down to "Project OAuth Scopes." The error `403, PERMISSION_DENIED: The caller does not have permission` indicates that the Cloud Platform project used to authorize the request is not the same as the one used by the script. */
		class run(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://mail.google.com/""", """https://www.google.com/calendar/feeds""", """https://www.google.com/m8/feeds""", """https://www.googleapis.com/auth/admin.directory.group""", """https://www.googleapis.com/auth/admin.directory.user""", """https://www.googleapis.com/auth/documents""", """https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/forms""", """https://www.googleapis.com/auth/forms.currentonly""", """https://www.googleapis.com/auth/groups""", """https://www.googleapis.com/auth/spreadsheets""", """https://www.googleapis.com/auth/userinfo.email""")
			/** Perform the request */
			def withExecutionRequest(body: Schema.ExecutionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object run {
			def apply(scriptId: String)(using signer: RequestSigner, ec: ExecutionContext): run = new run(ws.url(BASE_URL + s"v1/scripts/${scriptId}:run").addQueryStringParameters())
		}
	}
	object processes {
		/** List information about processes made by or on behalf of a user, such as process type and current status. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListUserProcessesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/script.processes""")
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
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListUserProcessesResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/processes").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListUserProcessesResponse]] = (fun: list) => fun.apply()
		}
		/** List information about a script's executed processes, such as process type and current status. */
		class listScriptProcesses(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListScriptProcessesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/script.processes""")
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
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListScriptProcessesResponse])
		}
		object listScriptProcesses {
			def apply(pageSize: Int, pageToken: String, scriptId: String)(using signer: RequestSigner, ec: ExecutionContext): listScriptProcesses = new listScriptProcesses(ws.url(BASE_URL + s"v1/processes:listScriptProcesses").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "scriptId" -> scriptId.toString))
			given Conversion[listScriptProcesses, Future[Schema.ListScriptProcessesResponse]] = (fun: listScriptProcesses) => fun.apply()
		}
	}
}
