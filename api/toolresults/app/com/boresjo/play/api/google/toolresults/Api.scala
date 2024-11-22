package com.boresjo.play.api.google.toolresults

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://toolresults.googleapis.com/"

	object projects {
		class getSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProjectSettings]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ProjectSettings])
		}
		object getSettings {
			def apply(projectId: String)(using auth: AuthToken, ec: ExecutionContext): getSettings = new getSettings(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/settings").addQueryStringParameters())
			given Conversion[getSettings, Future[Schema.ProjectSettings]] = (fun: getSettings) => fun.apply()
		}
		class initializeSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProjectSettings]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.ProjectSettings])
		}
		object initializeSettings {
			def apply(projectId: String)(using auth: AuthToken, ec: ExecutionContext): initializeSettings = new initializeSettings(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}:initializeSettings").addQueryStringParameters())
			given Conversion[initializeSettings, Future[Schema.ProjectSettings]] = (fun: initializeSettings) => fun.apply()
		}
		object histories {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** A unique request ID for server to detect duplicated requests. For example, a UUID. Optional, but strongly recommended. */
				def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
				def withHistory(body: Schema.History) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.History])
			}
			object create {
				def apply(projectId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories").addQueryStringParameters())
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.History]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.History])
			}
			object get {
				def apply(projectId: String, historyId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.History]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListHistoriesResponse]) {
				/** A continuation token to resume the query at the next item. Optional. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** The maximum number of Histories to fetch. Default value: 20. The server will use this default if the field is not set or has a value of 0. Any value greater than 100 will be treated as 100. Optional.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** If set, only return histories with the given name. Optional. */
				def withFilterByName(filterByName: String) = new list(req.addQueryStringParameters("filterByName" -> filterByName.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListHistoriesResponse])
			}
			object list {
				def apply(projectId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories").addQueryStringParameters())
				given Conversion[list, Future[Schema.ListHistoriesResponse]] = (fun: list) => fun.apply()
			}
			object executions {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** A unique request ID for server to detect duplicated requests. For example, a UUID. Optional, but strongly recommended. */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withExecution(body: Schema.Execution) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Execution])
				}
				object create {
					def apply(projectId: String, historyId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions").addQueryStringParameters())
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Execution]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Execution])
				}
				object get {
					def apply(projectId: String, historyId: String, executionId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}").addQueryStringParameters())
					given Conversion[get, Future[Schema.Execution]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** A unique request ID for server to detect duplicated requests. For example, a UUID. Optional, but strongly recommended. */
					def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withExecution(body: Schema.Execution) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Execution])
				}
				object patch {
					def apply(projectId: String, historyId: String, executionId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}").addQueryStringParameters())
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListExecutionsResponse]) {
					/** A continuation token to resume the query at the next item. Optional. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** The maximum number of Executions to fetch. Default value: 25. The server will use this default if the field is not set or has a value of 0. Optional.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListExecutionsResponse])
				}
				object list {
					def apply(projectId: String, historyId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions").addQueryStringParameters())
					given Conversion[list, Future[Schema.ListExecutionsResponse]] = (fun: list) => fun.apply()
				}
				object environments {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Environment]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Environment])
					}
					object get {
						def apply(projectId: String, historyId: String, executionId: String, environmentId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/environments/${environmentId}").addQueryStringParameters())
						given Conversion[get, Future[Schema.Environment]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEnvironmentsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListEnvironmentsResponse])
					}
					object list {
						def apply(projectId: String, historyId: String, executionId: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/environments").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.ListEnvironmentsResponse]] = (fun: list) => fun.apply()
					}
				}
				object clusters {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ScreenshotCluster]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ScreenshotCluster])
					}
					object get {
						def apply(projectId: String, historyId: String, executionId: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/clusters/${clusterId}").addQueryStringParameters())
						given Conversion[get, Future[Schema.ScreenshotCluster]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListScreenshotClustersResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListScreenshotClustersResponse])
					}
					object list {
						def apply(projectId: String, historyId: String, executionId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/clusters").addQueryStringParameters())
						given Conversion[list, Future[Schema.ListScreenshotClustersResponse]] = (fun: list) => fun.apply()
					}
				}
				object steps {
					class getPerfMetricsSummary(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PerfMetricsSummary]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.PerfMetricsSummary])
					}
					object getPerfMetricsSummary {
						def apply(projectId: String, historyId: String, executionId: String, stepId: String)(using auth: AuthToken, ec: ExecutionContext): getPerfMetricsSummary = new getPerfMetricsSummary(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}/perfMetricsSummary").addQueryStringParameters())
						given Conversion[getPerfMetricsSummary, Future[Schema.PerfMetricsSummary]] = (fun: getPerfMetricsSummary) => fun.apply()
					}
					class accessibilityClusters(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListStepAccessibilityClustersResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListStepAccessibilityClustersResponse])
					}
					object accessibilityClusters {
						def apply(projectsId :PlayApi, historiesId :PlayApi, executionsId :PlayApi, stepsId :PlayApi, name: String, locale: String)(using auth: AuthToken, ec: ExecutionContext): accessibilityClusters = new accessibilityClusters(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectsId}/histories/${historiesId}/executions/${executionsId}/steps/${stepsId}:accessibilityClusters").addQueryStringParameters("name" -> name.toString, "locale" -> locale.toString))
						given Conversion[accessibilityClusters, Future[Schema.ListStepAccessibilityClustersResponse]] = (fun: accessibilityClusters) => fun.apply()
					}
					class publishXunitXmlFiles(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withPublishXunitXmlFilesRequest(body: Schema.PublishXunitXmlFilesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Step])
					}
					object publishXunitXmlFiles {
						def apply(projectId: String, historyId: String, executionId: String, stepId: String)(using auth: AuthToken, ec: ExecutionContext): publishXunitXmlFiles = new publishXunitXmlFiles(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}:publishXunitXmlFiles").addQueryStringParameters())
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** A unique request ID for server to detect duplicated requests. For example, a UUID. Optional, but strongly recommended. */
						def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
						def withStep(body: Schema.Step) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Step])
					}
					object patch {
						def apply(projectId: String, historyId: String, executionId: String, stepId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}").addQueryStringParameters())
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** A unique request ID for server to detect duplicated requests. For example, a UUID. Optional, but strongly recommended. */
						def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
						def withStep(body: Schema.Step) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Step])
					}
					object create {
						def apply(projectId: String, historyId: String, executionId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps").addQueryStringParameters())
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Step]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Step])
					}
					object get {
						def apply(projectId: String, historyId: String, executionId: String, stepId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}").addQueryStringParameters())
						given Conversion[get, Future[Schema.Step]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListStepsResponse]) {
						/** A continuation token to resume the query at the next item. Optional. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** The maximum number of Steps to fetch. Default value: 25. The server will use this default if the field is not set or has a value of 0. Optional.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListStepsResponse])
					}
					object list {
						def apply(projectId: String, historyId: String, executionId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps").addQueryStringParameters())
						given Conversion[list, Future[Schema.ListStepsResponse]] = (fun: list) => fun.apply()
					}
					object perfSampleSeries {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withPerfSampleSeries(body: Schema.PerfSampleSeries) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PerfSampleSeries])
						}
						object create {
							def apply(projectId: String, historyId: String, executionId: String, stepId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}/perfSampleSeries").addQueryStringParameters())
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PerfSampleSeries]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.PerfSampleSeries])
						}
						object get {
							def apply(projectId: String, historyId: String, executionId: String, stepId: String, sampleSeriesId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}/perfSampleSeries/${sampleSeriesId}").addQueryStringParameters())
							given Conversion[get, Future[Schema.PerfSampleSeries]] = (fun: get) => fun.apply()
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPerfSampleSeriesResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListPerfSampleSeriesResponse])
						}
						object list {
							def apply(projectId: String, historyId: String, executionId: String, stepId: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}/perfSampleSeries").addQueryStringParameters("filter" -> filter.toString))
							given Conversion[list, Future[Schema.ListPerfSampleSeriesResponse]] = (fun: list) => fun.apply()
						}
						object samples {
							class batchCreate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
								def withBatchCreatePerfSamplesRequest(body: Schema.BatchCreatePerfSamplesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchCreatePerfSamplesResponse])
							}
							object batchCreate {
								def apply(projectId: String, historyId: String, executionId: String, stepId: String, sampleSeriesId: String)(using auth: AuthToken, ec: ExecutionContext): batchCreate = new batchCreate(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}/perfSampleSeries/${sampleSeriesId}/samples:batchCreate").addQueryStringParameters())
							}
							class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPerfSamplesResponse]) {
								/** Optional, the next_page_token returned in the previous response */
								def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListPerfSamplesResponse])
							}
							object list {
								def apply(projectId: String, historyId: String, executionId: String, stepId: String, sampleSeriesId: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}/perfSampleSeries/${sampleSeriesId}/samples").addQueryStringParameters("pageSize" -> pageSize.toString))
								given Conversion[list, Future[Schema.ListPerfSamplesResponse]] = (fun: list) => fun.apply()
							}
						}
					}
					object testCases {
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TestCase]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.TestCase])
						}
						object get {
							def apply(projectId: String, historyId: String, executionId: String, stepId: String, testCaseId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}/testCases/${testCaseId}").addQueryStringParameters())
							given Conversion[get, Future[Schema.TestCase]] = (fun: get) => fun.apply()
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTestCasesResponse]) {
							/** A continuation token to resume the query at the next item. Optional. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** The maximum number of TestCases to fetch. Default value: 100. The server will use this default if the field is not set or has a value of 0. Optional.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListTestCasesResponse])
						}
						object list {
							def apply(projectId: String, historyId: String, executionId: String, stepId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}/testCases").addQueryStringParameters())
							given Conversion[list, Future[Schema.ListTestCasesResponse]] = (fun: list) => fun.apply()
						}
					}
					object thumbnails {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListStepThumbnailsResponse]) {
							/** A continuation token to resume the query at the next item. Optional. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** The maximum number of thumbnails to fetch. Default value: 50. The server will use this default if the field is not set or has a value of 0. Optional.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListStepThumbnailsResponse])
						}
						object list {
							def apply(projectId: String, historyId: String, executionId: String, stepId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}/thumbnails").addQueryStringParameters())
							given Conversion[list, Future[Schema.ListStepThumbnailsResponse]] = (fun: list) => fun.apply()
						}
					}
					object perfMetricsSummary {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withPerfMetricsSummary(body: Schema.PerfMetricsSummary) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PerfMetricsSummary])
						}
						object create {
							def apply(projectId: String, historyId: String, executionId: String, stepId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}/perfMetricsSummary").addQueryStringParameters())
						}
					}
				}
			}
		}
	}
}
