package com.boresjo.play.api.google.workflowexecutions

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

	private val BASE_URL = "https://workflowexecutions.googleapis.com/"

	object projects {
		object locations {
			object workflows {
				/** Triggers a new execution using the latest revision of the given workflow by a Pub/Sub push notification. */
				class triggerPubsubExecution(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTriggerPubsubExecutionRequest(body: Schema.TriggerPubsubExecutionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Execution])
				}
				object triggerPubsubExecution {
					def apply(projectsId :PlayApi, locationsId :PlayApi, workflowsId :PlayApi, workflow: String)(using signer: RequestSigner, ec: ExecutionContext): triggerPubsubExecution = new triggerPubsubExecution(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflows/${workflowsId}:triggerPubsubExecution").addQueryStringParameters("workflow" -> workflow.toString))
				}
				object executions {
					/** Cancels an execution of the given name. */
					class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withCancelExecutionRequest(body: Schema.CancelExecutionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Execution])
					}
					object cancel {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workflowsId :PlayApi, executionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflows/${workflowsId}/executions/${executionsId}:cancel").addQueryStringParameters("name" -> name.toString))
					}
					/** Returns all metadata stored about an execution, excluding most data that is already accessible using other API methods. */
					class exportData(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ExportDataResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ExportDataResponse])
					}
					object exportData {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workflowsId :PlayApi, executionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): exportData = new exportData(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflows/${workflowsId}/executions/${executionsId}:exportData").addQueryStringParameters("name" -> name.toString))
						given Conversion[exportData, Future[Schema.ExportDataResponse]] = (fun: exportData) => fun.apply()
					}
					/** Creates a new execution using the latest revision of the given workflow. For more information, see Execute a workflow. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withExecution(body: Schema.Execution) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Execution])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workflowsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflows/${workflowsId}/executions").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes all step entries for an execution. */
					class deleteExecutionHistory(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withDeleteExecutionHistoryRequest(body: Schema.DeleteExecutionHistoryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object deleteExecutionHistory {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workflowsId :PlayApi, executionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): deleteExecutionHistory = new deleteExecutionHistory(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflows/${workflowsId}/executions/${executionsId}:deleteExecutionHistory").addQueryStringParameters("name" -> name.toString))
					}
					/** Returns an execution of the given name. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Execution]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. A view defining which fields should be filled in the returned execution. The API will default to the FULL view.<br>Possible values:<br>EXECUTION_VIEW_UNSPECIFIED: The default / unset value.<br>BASIC: Includes only basic metadata about the execution. The following fields are returned: name, start_time, end_time, duration, state, and workflow_revision_id.<br>FULL: Includes all data. */
						def withView(view: String) = new get(req.addQueryStringParameters("view" -> view.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Execution])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workflowsId :PlayApi, executionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflows/${workflowsId}/executions/${executionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Execution]] = (fun: get) => fun.apply()
					}
					/** Returns a list of executions which belong to the workflow with the given name. The method returns executions of all workflow revisions. Returned executions are ordered by their start time (newest first). */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListExecutionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. A view defining which fields should be filled in the returned executions. The API will default to the BASIC view.<br>Possible values:<br>EXECUTION_VIEW_UNSPECIFIED: The default / unset value.<br>BASIC: Includes only basic metadata about the execution. The following fields are returned: name, start_time, end_time, duration, state, and workflow_revision_id.<br>FULL: Includes all data. */
						def withView(view: String) = new list(req.addQueryStringParameters("view" -> view.toString))
						/** Optional. Filters applied to the `[Executions.ListExecutions]` results. The following fields are supported for filtering: `executionId`, `state`, `createTime`, `startTime`, `endTime`, `duration`, `workflowRevisionId`, `stepName`, `label`, and `disableConcurrencyQuotaOverflowBuffering`. For details, see AIP-160. For more information, see Filter executions. For example, if you are using the Google APIs Explorer: `state="SUCCEEDED"` or `startTime>"2023-08-01" AND state="FAILED"` */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Comma-separated list of fields that specify the ordering applied to the `[Executions.ListExecutions]` results. By default the ordering is based on descending `createTime`. The following fields are supported for ordering: `executionId`, `state`, `createTime`, `startTime`, `endTime`, `duration`, and `workflowRevisionId`. For details, see AIP-132. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListExecutionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workflowsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflows/${workflowsId}/executions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListExecutionsResponse]] = (fun: list) => fun.apply()
					}
					object stepEntries {
						/** Lists step entries for the corresponding workflow execution. Returned entries are ordered by their create_time. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListStepEntriesResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. Number of step entries to return per call. The default max is 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. A page token, received from a previous `ListStepEntries` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListStepEntries` must match the call that provided the page token. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. The number of step entries to skip. It can be used with or without a pageToken. If used with a pageToken, then it indicates the number of step entries to skip starting from the requested page.<br>Format: int32 */
							def withSkip(skip: Int) = new list(req.addQueryStringParameters("skip" -> skip.toString))
							/** Optional. Filters applied to the `[StepEntries.ListStepEntries]` results. The following fields are supported for filtering: `entryId`, `createTime`, `updateTime`, `routine`, `step`, `stepType`, `parent`, `state`. For details, see AIP-160. For example, if you are using the Google APIs Explorer: `state="SUCCEEDED"` or `createTime>"2023-08-01" AND state="FAILED"` */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional. Comma-separated list of fields that specify the ordering applied to the `[StepEntries.ListStepEntries]` results. By default the ordering is based on ascending `entryId`. The following fields are supported for ordering: `entryId`, `createTime`, `updateTime`, `routine`, `step`, `stepType`, `state`. For details, see AIP-132. */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListStepEntriesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workflowsId :PlayApi, executionsId :PlayApi, parent: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflows/${workflowsId}/executions/${executionsId}/stepEntries").addQueryStringParameters("parent" -> parent.toString, "view" -> view.toString))
							given Conversion[list, Future[Schema.ListStepEntriesResponse]] = (fun: list) => fun.apply()
						}
						/** Gets a step entry. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.StepEntry]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.StepEntry])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workflowsId :PlayApi, executionsId :PlayApi, stepEntriesId :PlayApi, name: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflows/${workflowsId}/executions/${executionsId}/stepEntries/${stepEntriesId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
							given Conversion[get, Future[Schema.StepEntry]] = (fun: get) => fun.apply()
						}
					}
					object callbacks {
						/** Returns a list of active callbacks that belong to the execution with the given name. The returned callbacks are ordered by callback ID. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCallbacksResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCallbacksResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workflowsId :PlayApi, executionsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflows/${workflowsId}/executions/${executionsId}/callbacks").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListCallbacksResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
		}
	}
}
