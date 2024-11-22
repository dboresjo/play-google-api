package com.boresjo.play.api.google.toolresults

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

	private val BASE_URL = "https://toolresults.googleapis.com/"

	object projects {
		/** Gets the Tool Results settings for a project. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to read from project */
		class getSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProjectSettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ProjectSettings])
		}
		object getSettings {
			def apply(projectId: String)(using signer: RequestSigner, ec: ExecutionContext): getSettings = new getSettings(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/settings").addQueryStringParameters())
			given Conversion[getSettings, Future[Schema.ProjectSettings]] = (fun: getSettings) => fun.apply()
		}
		/** Creates resources for settings which have not yet been set. Currently, this creates a single resource: a Google Cloud Storage bucket, to be used as the default bucket for this project. The bucket is created in an FTL-own storage project. Except for in rare cases, calling this method in parallel from multiple clients will only create a single bucket. In order to avoid unnecessary storage charges, the bucket is configured to automatically delete objects older than 90 days. The bucket is created with the following permissions: - Owner access for owners of central storage project (FTL-owned) - Writer access for owners/editors of customer project - Reader access for viewers of customer project The default ACL on objects created in the bucket is: - Owner access for owners of central storage project - Reader access for owners/editors/viewers of customer project See Google Cloud Storage documentation for more details. If there is already a default bucket set and the project can access the bucket, this call does nothing. However, if the project doesn't have the permission to access the bucket or the bucket is deleted, a new bucket will be created. May return any canonical error codes, including the following: - PERMISSION_DENIED - if the user is not authorized to write to project - Any error code raised by Google Cloud Storage */
		class initializeSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ProjectSettings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.ProjectSettings])
		}
		object initializeSettings {
			def apply(projectId: String)(using signer: RequestSigner, ec: ExecutionContext): initializeSettings = new initializeSettings(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}:initializeSettings").addQueryStringParameters())
			given Conversion[initializeSettings, Future[Schema.ProjectSettings]] = (fun: initializeSettings) => fun.apply()
		}
		object histories {
			/** Creates a History. The returned History will have the id set. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to write to project - INVALID_ARGUMENT - if the request is malformed - NOT_FOUND - if the containing project does not exist */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** A unique request ID for server to detect duplicated requests. For example, a UUID. Optional, but strongly recommended. */
				def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
				/** Perform the request */
				def withHistory(body: Schema.History) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.History])
			}
			object create {
				def apply(projectId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories").addQueryStringParameters())
			}
			/** Gets a History. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to read project - INVALID_ARGUMENT - if the request is malformed - NOT_FOUND - if the History does not exist */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.History]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.History])
			}
			object get {
				def apply(projectId: String, historyId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.History]] = (fun: get) => fun.apply()
			}
			/** Lists Histories for a given Project. The histories are sorted by modification time in descending order. The history_id key will be used to order the history with the same modification time. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to read project - INVALID_ARGUMENT - if the request is malformed - NOT_FOUND - if the History does not exist */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListHistoriesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** A continuation token to resume the query at the next item. Optional. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** The maximum number of Histories to fetch. Default value: 20. The server will use this default if the field is not set or has a value of 0. Any value greater than 100 will be treated as 100. Optional.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** If set, only return histories with the given name. Optional. */
				def withFilterByName(filterByName: String) = new list(req.addQueryStringParameters("filterByName" -> filterByName.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListHistoriesResponse])
			}
			object list {
				def apply(projectId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories").addQueryStringParameters())
				given Conversion[list, Future[Schema.ListHistoriesResponse]] = (fun: list) => fun.apply()
			}
			object executions {
				/** Creates an Execution. The returned Execution will have the id set. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to write to project - INVALID_ARGUMENT - if the request is malformed - NOT_FOUND - if the containing History does not exist */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** A unique request ID for server to detect duplicated requests. For example, a UUID. Optional, but strongly recommended. */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def withExecution(body: Schema.Execution) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Execution])
				}
				object create {
					def apply(projectId: String, historyId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions").addQueryStringParameters())
				}
				/** Gets an Execution. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to write to project - INVALID_ARGUMENT - if the request is malformed - NOT_FOUND - if the Execution does not exist */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Execution]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Execution])
				}
				object get {
					def apply(projectId: String, historyId: String, executionId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}").addQueryStringParameters())
					given Conversion[get, Future[Schema.Execution]] = (fun: get) => fun.apply()
				}
				/** Updates an existing Execution with the supplied partial entity. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to write to project - INVALID_ARGUMENT - if the request is malformed - FAILED_PRECONDITION - if the requested state transition is illegal - NOT_FOUND - if the containing History does not exist */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** A unique request ID for server to detect duplicated requests. For example, a UUID. Optional, but strongly recommended. */
					def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def withExecution(body: Schema.Execution) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Execution])
				}
				object patch {
					def apply(projectId: String, historyId: String, executionId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}").addQueryStringParameters())
				}
				/** Lists Executions for a given History. The executions are sorted by creation_time in descending order. The execution_id key will be used to order the executions with the same creation_time. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to read project - INVALID_ARGUMENT - if the request is malformed - NOT_FOUND - if the containing History does not exist */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListExecutionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** A continuation token to resume the query at the next item. Optional. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** The maximum number of Executions to fetch. Default value: 25. The server will use this default if the field is not set or has a value of 0. Optional.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListExecutionsResponse])
				}
				object list {
					def apply(projectId: String, historyId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions").addQueryStringParameters())
					given Conversion[list, Future[Schema.ListExecutionsResponse]] = (fun: list) => fun.apply()
				}
				object environments {
					/** Gets an Environment. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to read project - INVALID_ARGUMENT - if the request is malformed - NOT_FOUND - if the Environment does not exist */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Environment]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Environment])
					}
					object get {
						def apply(projectId: String, historyId: String, executionId: String, environmentId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/environments/${environmentId}").addQueryStringParameters())
						given Conversion[get, Future[Schema.Environment]] = (fun: get) => fun.apply()
					}
					/** Lists Environments for a given Execution. The Environments are sorted by display name. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to read project - INVALID_ARGUMENT - if the request is malformed - NOT_FOUND - if the containing Execution does not exist */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEnvironmentsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEnvironmentsResponse])
					}
					object list {
						def apply(projectId: String, historyId: String, executionId: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/environments").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.ListEnvironmentsResponse]] = (fun: list) => fun.apply()
					}
				}
				object clusters {
					/** Retrieves a single screenshot cluster by its ID */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ScreenshotCluster]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ScreenshotCluster])
					}
					object get {
						def apply(projectId: String, historyId: String, executionId: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/clusters/${clusterId}").addQueryStringParameters())
						given Conversion[get, Future[Schema.ScreenshotCluster]] = (fun: get) => fun.apply()
					}
					/** Lists Screenshot Clusters Returns the list of screenshot clusters corresponding to an execution. Screenshot clusters are created after the execution is finished. Clusters are created from a set of screenshots. Between any two screenshots, a matching score is calculated based off their metadata that determines how similar they are. Screenshots are placed in the cluster that has screens which have the highest matching scores. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListScreenshotClustersResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListScreenshotClustersResponse])
					}
					object list {
						def apply(projectId: String, historyId: String, executionId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/clusters").addQueryStringParameters())
						given Conversion[list, Future[Schema.ListScreenshotClustersResponse]] = (fun: list) => fun.apply()
					}
				}
				object steps {
					/** Retrieves a PerfMetricsSummary. May return any of the following error code(s): - NOT_FOUND - The specified PerfMetricsSummary does not exist */
					class getPerfMetricsSummary(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PerfMetricsSummary]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PerfMetricsSummary])
					}
					object getPerfMetricsSummary {
						def apply(projectId: String, historyId: String, executionId: String, stepId: String)(using signer: RequestSigner, ec: ExecutionContext): getPerfMetricsSummary = new getPerfMetricsSummary(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}/perfMetricsSummary").addQueryStringParameters())
						given Conversion[getPerfMetricsSummary, Future[Schema.PerfMetricsSummary]] = (fun: getPerfMetricsSummary) => fun.apply()
					}
					/** Lists accessibility clusters for a given Step May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to read project - INVALID_ARGUMENT - if the request is malformed - FAILED_PRECONDITION - if an argument in the request happens to be invalid; e.g. if the locale format is incorrect - NOT_FOUND - if the containing Step does not exist */
					class accessibilityClusters(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListStepAccessibilityClustersResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListStepAccessibilityClustersResponse])
					}
					object accessibilityClusters {
						def apply(projectsId :PlayApi, historiesId :PlayApi, executionsId :PlayApi, stepsId :PlayApi, name: String, locale: String)(using signer: RequestSigner, ec: ExecutionContext): accessibilityClusters = new accessibilityClusters(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectsId}/histories/${historiesId}/executions/${executionsId}/steps/${stepsId}:accessibilityClusters").addQueryStringParameters("name" -> name.toString, "locale" -> locale.toString))
						given Conversion[accessibilityClusters, Future[Schema.ListStepAccessibilityClustersResponse]] = (fun: accessibilityClusters) => fun.apply()
					}
					/** Publish xml files to an existing Step. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to write project - INVALID_ARGUMENT - if the request is malformed - FAILED_PRECONDITION - if the requested state transition is illegal, e.g. try to upload a duplicate xml file or a file too large. - NOT_FOUND - if the containing Execution does not exist */
					class publishXunitXmlFiles(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withPublishXunitXmlFilesRequest(body: Schema.PublishXunitXmlFilesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Step])
					}
					object publishXunitXmlFiles {
						def apply(projectId: String, historyId: String, executionId: String, stepId: String)(using signer: RequestSigner, ec: ExecutionContext): publishXunitXmlFiles = new publishXunitXmlFiles(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}:publishXunitXmlFiles").addQueryStringParameters())
					}
					/** Updates an existing Step with the supplied partial entity. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to write project - INVALID_ARGUMENT - if the request is malformed - FAILED_PRECONDITION - if the requested state transition is illegal (e.g try to upload a duplicate xml file), if the updated step is too large (more than 10Mib) - NOT_FOUND - if the containing Execution does not exist */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** A unique request ID for server to detect duplicated requests. For example, a UUID. Optional, but strongly recommended. */
						def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Perform the request */
						def withStep(body: Schema.Step) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Step])
					}
					object patch {
						def apply(projectId: String, historyId: String, executionId: String, stepId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}").addQueryStringParameters())
					}
					/** Creates a Step. The returned Step will have the id set. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to write to project - INVALID_ARGUMENT - if the request is malformed - FAILED_PRECONDITION - if the step is too large (more than 10Mib) - NOT_FOUND - if the containing Execution does not exist */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** A unique request ID for server to detect duplicated requests. For example, a UUID. Optional, but strongly recommended. */
						def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Perform the request */
						def withStep(body: Schema.Step) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Step])
					}
					object create {
						def apply(projectId: String, historyId: String, executionId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps").addQueryStringParameters())
					}
					/** Gets a Step. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to read project - INVALID_ARGUMENT - if the request is malformed - NOT_FOUND - if the Step does not exist */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Step]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Step])
					}
					object get {
						def apply(projectId: String, historyId: String, executionId: String, stepId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}").addQueryStringParameters())
						given Conversion[get, Future[Schema.Step]] = (fun: get) => fun.apply()
					}
					/** Lists Steps for a given Execution. The steps are sorted by creation_time in descending order. The step_id key will be used to order the steps with the same creation_time. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to read project - INVALID_ARGUMENT - if the request is malformed - FAILED_PRECONDITION - if an argument in the request happens to be invalid; e.g. if an attempt is made to list the children of a nonexistent Step - NOT_FOUND - if the containing Execution does not exist */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListStepsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** A continuation token to resume the query at the next item. Optional. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** The maximum number of Steps to fetch. Default value: 25. The server will use this default if the field is not set or has a value of 0. Optional.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListStepsResponse])
					}
					object list {
						def apply(projectId: String, historyId: String, executionId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps").addQueryStringParameters())
						given Conversion[list, Future[Schema.ListStepsResponse]] = (fun: list) => fun.apply()
					}
					object perfSampleSeries {
						/** Creates a PerfSampleSeries. May return any of the following error code(s): - ALREADY_EXISTS - PerfMetricSummary already exists for the given Step - NOT_FOUND - The containing Step does not exist */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withPerfSampleSeries(body: Schema.PerfSampleSeries) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PerfSampleSeries])
						}
						object create {
							def apply(projectId: String, historyId: String, executionId: String, stepId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}/perfSampleSeries").addQueryStringParameters())
						}
						/** Gets a PerfSampleSeries. May return any of the following error code(s): - NOT_FOUND - The specified PerfSampleSeries does not exist */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PerfSampleSeries]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PerfSampleSeries])
						}
						object get {
							def apply(projectId: String, historyId: String, executionId: String, stepId: String, sampleSeriesId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}/perfSampleSeries/${sampleSeriesId}").addQueryStringParameters())
							given Conversion[get, Future[Schema.PerfSampleSeries]] = (fun: get) => fun.apply()
						}
						/** Lists PerfSampleSeries for a given Step. The request provides an optional filter which specifies one or more PerfMetricsType to include in the result; if none returns all. The resulting PerfSampleSeries are sorted by ids. May return any of the following canonical error codes: - NOT_FOUND - The containing Step does not exist */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPerfSampleSeriesResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPerfSampleSeriesResponse])
						}
						object list {
							def apply(projectId: String, historyId: String, executionId: String, stepId: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}/perfSampleSeries").addQueryStringParameters("filter" -> filter.toString))
							given Conversion[list, Future[Schema.ListPerfSampleSeriesResponse]] = (fun: list) => fun.apply()
						}
						object samples {
							/** Creates a batch of PerfSamples - a client can submit multiple batches of Perf Samples through repeated calls to this method in order to split up a large request payload - duplicates and existing timestamp entries will be ignored. - the batch operation may partially succeed - the set of elements successfully inserted is returned in the response (omits items which already existed in the database). May return any of the following canonical error codes: - NOT_FOUND - The containing PerfSampleSeries does not exist */
							class batchCreate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def withBatchCreatePerfSamplesRequest(body: Schema.BatchCreatePerfSamplesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchCreatePerfSamplesResponse])
							}
							object batchCreate {
								def apply(projectId: String, historyId: String, executionId: String, stepId: String, sampleSeriesId: String)(using signer: RequestSigner, ec: ExecutionContext): batchCreate = new batchCreate(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}/perfSampleSeries/${sampleSeriesId}/samples:batchCreate").addQueryStringParameters())
							}
							/** Lists the Performance Samples of a given Sample Series - The list results are sorted by timestamps ascending - The default page size is 500 samples; and maximum size allowed 5000 - The response token indicates the last returned PerfSample timestamp - When the results size exceeds the page size, submit a subsequent request including the page token to return the rest of the samples up to the page limit May return any of the following canonical error codes: - OUT_OF_RANGE - The specified request page_token is out of valid range - NOT_FOUND - The containing PerfSampleSeries does not exist */
							class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPerfSamplesResponse]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Optional, the next_page_token returned in the previous response */
								def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPerfSamplesResponse])
							}
							object list {
								def apply(projectId: String, historyId: String, executionId: String, stepId: String, sampleSeriesId: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}/perfSampleSeries/${sampleSeriesId}/samples").addQueryStringParameters("pageSize" -> pageSize.toString))
								given Conversion[list, Future[Schema.ListPerfSamplesResponse]] = (fun: list) => fun.apply()
							}
						}
					}
					object testCases {
						/** Gets details of a Test Case for a Step. Experimental test cases API. Still in active development. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to write to project - INVALID_ARGUMENT - if the request is malformed - NOT_FOUND - if the containing Test Case does not exist */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TestCase]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TestCase])
						}
						object get {
							def apply(projectId: String, historyId: String, executionId: String, stepId: String, testCaseId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}/testCases/${testCaseId}").addQueryStringParameters())
							given Conversion[get, Future[Schema.TestCase]] = (fun: get) => fun.apply()
						}
						/** Lists Test Cases attached to a Step. Experimental test cases API. Still in active development. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to write to project - INVALID_ARGUMENT - if the request is malformed - NOT_FOUND - if the containing Step does not exist */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTestCasesResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** A continuation token to resume the query at the next item. Optional. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** The maximum number of TestCases to fetch. Default value: 100. The server will use this default if the field is not set or has a value of 0. Optional.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTestCasesResponse])
						}
						object list {
							def apply(projectId: String, historyId: String, executionId: String, stepId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}/testCases").addQueryStringParameters())
							given Conversion[list, Future[Schema.ListTestCasesResponse]] = (fun: list) => fun.apply()
						}
					}
					object thumbnails {
						/** Lists thumbnails of images attached to a step. May return any of the following canonical error codes: - PERMISSION_DENIED - if the user is not authorized to read from the project, or from any of the images - INVALID_ARGUMENT - if the request is malformed - NOT_FOUND - if the step does not exist, or if any of the images do not exist */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListStepThumbnailsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** A continuation token to resume the query at the next item. Optional. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** The maximum number of thumbnails to fetch. Default value: 50. The server will use this default if the field is not set or has a value of 0. Optional.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListStepThumbnailsResponse])
						}
						object list {
							def apply(projectId: String, historyId: String, executionId: String, stepId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}/thumbnails").addQueryStringParameters())
							given Conversion[list, Future[Schema.ListStepThumbnailsResponse]] = (fun: list) => fun.apply()
						}
					}
					object perfMetricsSummary {
						/** Creates a PerfMetricsSummary resource. Returns the existing one if it has already been created. May return any of the following error code(s): - NOT_FOUND - The containing Step does not exist */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withPerfMetricsSummary(body: Schema.PerfMetricsSummary) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PerfMetricsSummary])
						}
						object create {
							def apply(projectId: String, historyId: String, executionId: String, stepId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"toolresults/v1beta3/projects/${projectId}/histories/${historyId}/executions/${executionId}/steps/${stepId}/perfMetricsSummary").addQueryStringParameters())
						}
					}
				}
			}
		}
	}
}
