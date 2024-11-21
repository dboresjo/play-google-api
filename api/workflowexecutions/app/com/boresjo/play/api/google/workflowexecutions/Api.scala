package com.boresjo.play.api.google.workflowexecutions

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://workflowexecutions.googleapis.com/"

	object projects {
		object locations {
			object workflows {
				class triggerPubsubExecution(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTriggerPubsubExecutionRequest(body: Schema.TriggerPubsubExecutionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Execution])
				}
				object triggerPubsubExecution {
					def apply(projectsId :PlayApi, locationsId :PlayApi, workflowsId :PlayApi, workflow: String)(using auth: AuthToken, ec: ExecutionContext): triggerPubsubExecution = new triggerPubsubExecution(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflows/${workflowsId}:triggerPubsubExecution")).addQueryStringParameters("workflow" -> workflow.toString))
				}
				object executions {
					class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCancelExecutionRequest(body: Schema.CancelExecutionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Execution])
					}
					object cancel {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workflowsId :PlayApi, executionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflows/${workflowsId}/executions/${executionsId}:cancel")).addQueryStringParameters("name" -> name.toString))
					}
					class exportData(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ExportDataResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ExportDataResponse])
					}
					object exportData {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workflowsId :PlayApi, executionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): exportData = new exportData(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflows/${workflowsId}/executions/${executionsId}:exportData")).addQueryStringParameters("name" -> name.toString))
						given Conversion[exportData, Future[Schema.ExportDataResponse]] = (fun: exportData) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withExecution(body: Schema.Execution) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Execution])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workflowsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflows/${workflowsId}/executions")).addQueryStringParameters("parent" -> parent.toString))
					}
					class deleteExecutionHistory(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withDeleteExecutionHistoryRequest(body: Schema.DeleteExecutionHistoryRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
					}
					object deleteExecutionHistory {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workflowsId :PlayApi, executionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): deleteExecutionHistory = new deleteExecutionHistory(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflows/${workflowsId}/executions/${executionsId}:deleteExecutionHistory")).addQueryStringParameters("name" -> name.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Execution]) {
						/** Optional. A view defining which fields should be filled in the returned execution. The API will default to the FULL view.<br>Possible values:<br>EXECUTION_VIEW_UNSPECIFIED: The default / unset value.<br>BASIC: Includes only basic metadata about the execution. The following fields are returned: name, start_time, end_time, duration, state, and workflow_revision_id.<br>FULL: Includes all data. */
						def withView(view: String) = new get(req.addQueryStringParameters("view" -> view.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.Execution])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workflowsId :PlayApi, executionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflows/${workflowsId}/executions/${executionsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Execution]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListExecutionsResponse]) {
						/** Optional. A view defining which fields should be filled in the returned executions. The API will default to the BASIC view.<br>Possible values:<br>EXECUTION_VIEW_UNSPECIFIED: The default / unset value.<br>BASIC: Includes only basic metadata about the execution. The following fields are returned: name, start_time, end_time, duration, state, and workflow_revision_id.<br>FULL: Includes all data. */
						def withView(view: String) = new list(req.addQueryStringParameters("view" -> view.toString))
						/** Optional. Filters applied to the `[Executions.ListExecutions]` results. The following fields are supported for filtering: `executionId`, `state`, `createTime`, `startTime`, `endTime`, `duration`, `workflowRevisionId`, `stepName`, `label`, and `disableConcurrencyQuotaOverflowBuffering`. For details, see AIP-160. For more information, see Filter executions. For example, if you are using the Google APIs Explorer: `state="SUCCEEDED"` or `startTime>"2023-08-01" AND state="FAILED"` */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Comma-separated list of fields that specify the ordering applied to the `[Executions.ListExecutions]` results. By default the ordering is based on descending `createTime`. The following fields are supported for ordering: `executionId`, `state`, `createTime`, `startTime`, `endTime`, `duration`, and `workflowRevisionId`. For details, see AIP-132. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListExecutionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workflowsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflows/${workflowsId}/executions")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListExecutionsResponse]] = (fun: list) => fun.apply()
					}
					object stepEntries {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListStepEntriesResponse]) {
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
							def apply() = req.execute("GET").map(_.json.as[Schema.ListStepEntriesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workflowsId :PlayApi, executionsId :PlayApi, parent: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflows/${workflowsId}/executions/${executionsId}/stepEntries")).addQueryStringParameters("parent" -> parent.toString, "view" -> view.toString))
							given Conversion[list, Future[Schema.ListStepEntriesResponse]] = (fun: list) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.StepEntry]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.StepEntry])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workflowsId :PlayApi, executionsId :PlayApi, stepEntriesId :PlayApi, name: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflows/${workflowsId}/executions/${executionsId}/stepEntries/${stepEntriesId}")).addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
							given Conversion[get, Future[Schema.StepEntry]] = (fun: get) => fun.apply()
						}
					}
					object callbacks {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCallbacksResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.ListCallbacksResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workflowsId :PlayApi, executionsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workflows/${workflowsId}/executions/${executionsId}/callbacks")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListCallbacksResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
		}
	}
}
