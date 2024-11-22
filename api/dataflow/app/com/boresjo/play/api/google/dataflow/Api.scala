package com.boresjo.play.api.google.dataflow

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */,
		"""https://www.googleapis.com/auth/compute""" /* View and manage your Google Compute Engine resources */
	)

	private val BASE_URL = "https://dataflow.googleapis.com/"

	object projects {
		/** Deletes a snapshot. */
		class deleteSnapshots(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DeleteSnapshotResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.DeleteSnapshotResponse])
		}
		object deleteSnapshots {
			def apply(projectId: String, snapshotId: String, location: String)(using signer: RequestSigner, ec: ExecutionContext): deleteSnapshots = new deleteSnapshots(ws.url(BASE_URL + s"v1b3/projects/${projectId}/snapshots").addQueryStringParameters("snapshotId" -> snapshotId.toString, "location" -> location.toString))
			given Conversion[deleteSnapshots, Future[Schema.DeleteSnapshotResponse]] = (fun: deleteSnapshots) => fun.apply()
		}
		/** Send a worker_message to the service. */
		class workerMessages(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
			/** Perform the request */
			def withSendWorkerMessagesRequest(body: Schema.SendWorkerMessagesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SendWorkerMessagesResponse])
		}
		object workerMessages {
			def apply(projectId: String)(using signer: RequestSigner, ec: ExecutionContext): workerMessages = new workerMessages(ws.url(BASE_URL + s"v1b3/projects/${projectId}/WorkerMessages").addQueryStringParameters())
		}
		object locations {
			/** Send a worker_message to the service. */
			class workerMessages(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
				/** Perform the request */
				def withSendWorkerMessagesRequest(body: Schema.SendWorkerMessagesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SendWorkerMessagesResponse])
			}
			object workerMessages {
				def apply(projectId: String, location: String)(using signer: RequestSigner, ec: ExecutionContext): workerMessages = new workerMessages(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/WorkerMessages").addQueryStringParameters())
			}
			object snapshots {
				/** Gets information about a snapshot. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Snapshot]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Snapshot])
				}
				object get {
					def apply(projectId: String, location: String, snapshotId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/snapshots/${snapshotId}").addQueryStringParameters())
					given Conversion[get, Future[Schema.Snapshot]] = (fun: get) => fun.apply()
				}
				/** Deletes a snapshot. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DeleteSnapshotResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.DeleteSnapshotResponse])
				}
				object delete {
					def apply(projectId: String, location: String, snapshotId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/snapshots/${snapshotId}").addQueryStringParameters())
					given Conversion[delete, Future[Schema.DeleteSnapshotResponse]] = (fun: delete) => fun.apply()
				}
				/** Lists snapshots. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSnapshotsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSnapshotsResponse])
				}
				object list {
					def apply(projectId: String, location: String, jobId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/snapshots").addQueryStringParameters("jobId" -> jobId.toString))
					given Conversion[list, Future[Schema.ListSnapshotsResponse]] = (fun: list) => fun.apply()
				}
			}
			object flexTemplates {
				/** Launch a job with a FlexTemplate. */
				class launch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
					/** Perform the request */
					def withLaunchFlexTemplateRequest(body: Schema.LaunchFlexTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LaunchFlexTemplateResponse])
				}
				object launch {
					def apply(projectId: String, location: String)(using signer: RequestSigner, ec: ExecutionContext): launch = new launch(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/flexTemplates:launch").addQueryStringParameters())
				}
			}
			object templates {
				/** Creates a Cloud Dataflow job from a template. Do not enter confidential information when you supply string values using the API. To create a job, we recommend using `projects.locations.templates.create` with a [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints). Using `projects.templates.create` is not recommended, because your job will always start in `us-central1`. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
					/** Perform the request */
					def withCreateJobFromTemplateRequest(body: Schema.CreateJobFromTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Job])
				}
				object create {
					def apply(projectId: String, location: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/templates").addQueryStringParameters())
				}
				/** Launches a template. To launch a template, we recommend using `projects.locations.templates.launch` with a [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints). Using `projects.templates.launch` is not recommended, because jobs launched from the template will always start in `us-central1`. */
				class launch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
					/** Perform the request */
					def withLaunchTemplateParameters(body: Schema.LaunchTemplateParameters) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LaunchTemplateResponse])
				}
				object launch {
					def apply(projectId: String, location: String, validateOnly: Boolean, gcsPath: String, dynamicTemplateGcsPath: String, dynamicTemplateStagingLocation: String)(using signer: RequestSigner, ec: ExecutionContext): launch = new launch(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/templates:launch").addQueryStringParameters("validateOnly" -> validateOnly.toString, "gcsPath" -> gcsPath.toString, "dynamicTemplate.gcsPath" -> dynamicTemplateGcsPath.toString, "dynamicTemplate.stagingLocation" -> dynamicTemplateStagingLocation.toString))
				}
				/** Get the template associated with a template. To get the template, we recommend using `projects.locations.templates.get` with a [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints). Using `projects.templates.get` is not recommended, because only templates that are running in `us-central1` are retrieved. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GetTemplateResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GetTemplateResponse])
				}
				object get {
					def apply(projectId: String, location: String, gcsPath: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/templates:get").addQueryStringParameters("gcsPath" -> gcsPath.toString, "view" -> view.toString))
					given Conversion[get, Future[Schema.GetTemplateResponse]] = (fun: get) => fun.apply()
				}
			}
			object jobs {
				/** Request detailed information about the execution status of the job. EXPERIMENTAL. This API is subject to change or removal without notice. */
				class getExecutionDetails(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.JobExecutionDetails]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.JobExecutionDetails])
				}
				object getExecutionDetails {
					def apply(projectId: String, location: String, jobId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): getExecutionDetails = new getExecutionDetails(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}/executionDetails").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[getExecutionDetails, Future[Schema.JobExecutionDetails]] = (fun: getExecutionDetails) => fun.apply()
				}
				/** Creates a Cloud Dataflow job. To create a job, we recommend using `projects.locations.jobs.create` with a [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints). Using `projects.jobs.create` is not recommended, as your job will always start in `us-central1`. Do not enter confidential information when you supply string values using the API. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
					/** Perform the request */
					def withJob(body: Schema.Job) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Job])
				}
				object create {
					def apply(projectId: String, location: String, view: String, replaceJobId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs").addQueryStringParameters("view" -> view.toString, "replaceJobId" -> replaceJobId.toString))
				}
				/** Snapshot the state of a streaming job. */
				class snapshot(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
					/** Perform the request */
					def withSnapshotJobRequest(body: Schema.SnapshotJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Snapshot])
				}
				object snapshot {
					def apply(projectId: String, location: String, jobId: String)(using signer: RequestSigner, ec: ExecutionContext): snapshot = new snapshot(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}:snapshot").addQueryStringParameters())
				}
				/** Request the job status. To request the status of a job, we recommend using `projects.locations.jobs.getMetrics` with a [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints). Using `projects.jobs.getMetrics` is not recommended, as you can only request the status of jobs that are running in `us-central1`. */
				class getMetrics(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.JobMetrics]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.JobMetrics])
				}
				object getMetrics {
					def apply(projectId: String, location: String, jobId: String, startTime: String)(using signer: RequestSigner, ec: ExecutionContext): getMetrics = new getMetrics(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}/metrics").addQueryStringParameters("startTime" -> startTime.toString))
					given Conversion[getMetrics, Future[Schema.JobMetrics]] = (fun: getMetrics) => fun.apply()
				}
				/** Updates the state of an existing Cloud Dataflow job. To update the state of an existing job, we recommend using `projects.locations.jobs.update` with a [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints). Using `projects.jobs.update` is not recommended, as you can only update the state of jobs that are running in `us-central1`. */
				class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
					/** Perform the request */
					def withJob(body: Schema.Job) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Job])
				}
				object update {
					def apply(projectId: String, location: String, jobId: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}").addQueryStringParameters("updateMask" -> updateMask.toString))
				}
				/** List the jobs of a project. To list the jobs of a project in a region, we recommend using `projects.locations.jobs.list` with a [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints). To list the all jobs across all regions, use `projects.jobs.aggregated`. Using `projects.jobs.list` is not recommended, because you can only get the list of jobs that are running in `us-central1`. `projects.locations.jobs.list` and `projects.jobs.list` support filtering the list of jobs by name. Filtering by name isn't supported by `projects.jobs.aggregated`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListJobsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
					/** Optional. The job name. */
					def withName(name: String) = new list(req.addQueryStringParameters("name" -> name.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListJobsResponse])
				}
				object list {
					def apply(projectId: String, location: String, filter: String, view: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs").addQueryStringParameters("filter" -> filter.toString, "view" -> view.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListJobsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the state of the specified Cloud Dataflow job. To get the state of a job, we recommend using `projects.locations.jobs.get` with a [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints). Using `projects.jobs.get` is not recommended, as you can only get the state of jobs that are running in `us-central1`. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Job]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Job])
				}
				object get {
					def apply(projectId: String, location: String, jobId: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}").addQueryStringParameters("view" -> view.toString))
					given Conversion[get, Future[Schema.Job]] = (fun: get) => fun.apply()
				}
				object messages {
					/** Request the job status. To request the status of a job, we recommend using `projects.locations.jobs.messages.list` with a [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints). Using `projects.jobs.messages.list` is not recommended, as you can only request the status of jobs that are running in `us-central1`. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListJobMessagesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListJobMessagesResponse])
					}
					object list {
						def apply(projectId: String, location: String, jobId: String, minimumImportance: String, pageSize: Int, pageToken: String, startTime: String, endTime: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}/messages").addQueryStringParameters("minimumImportance" -> minimumImportance.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "startTime" -> startTime.toString, "endTime" -> endTime.toString))
						given Conversion[list, Future[Schema.ListJobMessagesResponse]] = (fun: list) => fun.apply()
					}
				}
				object stages {
					/** Request detailed information about the execution status of a stage of the job. EXPERIMENTAL. This API is subject to change or removal without notice. */
					class getExecutionDetails(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.StageExecutionDetails]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.StageExecutionDetails])
					}
					object getExecutionDetails {
						def apply(projectId: String, location: String, jobId: String, stageId: String, pageSize: Int, pageToken: String, startTime: String, endTime: String)(using signer: RequestSigner, ec: ExecutionContext): getExecutionDetails = new getExecutionDetails(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}/stages/${stageId}/executionDetails").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "startTime" -> startTime.toString, "endTime" -> endTime.toString))
						given Conversion[getExecutionDetails, Future[Schema.StageExecutionDetails]] = (fun: getExecutionDetails) => fun.apply()
					}
				}
				object snapshots {
					/** Lists snapshots. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSnapshotsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSnapshotsResponse])
					}
					object list {
						def apply(projectId: String, location: String, jobId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}/snapshots").addQueryStringParameters())
						given Conversion[list, Future[Schema.ListSnapshotsResponse]] = (fun: list) => fun.apply()
					}
				}
				object debug {
					/** Get encoded debug configuration for component. Not cacheable. */
					class getConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
						/** Perform the request */
						def withGetDebugConfigRequest(body: Schema.GetDebugConfigRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GetDebugConfigResponse])
					}
					object getConfig {
						def apply(projectId: String, location: String, jobId: String)(using signer: RequestSigner, ec: ExecutionContext): getConfig = new getConfig(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}/debug/getConfig").addQueryStringParameters())
					}
					/** Send encoded debug capture data for component. */
					class sendCapture(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
						/** Perform the request */
						def withSendDebugCaptureRequest(body: Schema.SendDebugCaptureRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SendDebugCaptureResponse])
					}
					object sendCapture {
						def apply(projectId: String, location: String, jobId: String)(using signer: RequestSigner, ec: ExecutionContext): sendCapture = new sendCapture(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}/debug/sendCapture").addQueryStringParameters())
					}
				}
				object workItems {
					/** Reports the status of dataflow WorkItems leased by a worker. */
					class reportStatus(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
						/** Perform the request */
						def withReportWorkItemStatusRequest(body: Schema.ReportWorkItemStatusRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReportWorkItemStatusResponse])
					}
					object reportStatus {
						def apply(projectId: String, location: String, jobId: String)(using signer: RequestSigner, ec: ExecutionContext): reportStatus = new reportStatus(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}/workItems:reportStatus").addQueryStringParameters())
					}
					/** Leases a dataflow WorkItem to run. */
					class lease(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
						/** Perform the request */
						def withLeaseWorkItemRequest(body: Schema.LeaseWorkItemRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LeaseWorkItemResponse])
					}
					object lease {
						def apply(projectId: String, location: String, jobId: String)(using signer: RequestSigner, ec: ExecutionContext): lease = new lease(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}/workItems:lease").addQueryStringParameters())
					}
				}
			}
		}
		object snapshots {
			/** Gets information about a snapshot. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Snapshot]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Snapshot])
			}
			object get {
				def apply(projectId: String, snapshotId: String, location: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1b3/projects/${projectId}/snapshots/${snapshotId}").addQueryStringParameters("location" -> location.toString))
				given Conversion[get, Future[Schema.Snapshot]] = (fun: get) => fun.apply()
			}
			/** Lists snapshots. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSnapshotsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSnapshotsResponse])
			}
			object list {
				def apply(projectId: String, jobId: String, location: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1b3/projects/${projectId}/snapshots").addQueryStringParameters("jobId" -> jobId.toString, "location" -> location.toString))
				given Conversion[list, Future[Schema.ListSnapshotsResponse]] = (fun: list) => fun.apply()
			}
		}
		object templates {
			/** Creates a Cloud Dataflow job from a template. Do not enter confidential information when you supply string values using the API. To create a job, we recommend using `projects.locations.templates.create` with a [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints). Using `projects.templates.create` is not recommended, because your job will always start in `us-central1`. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
				/** Perform the request */
				def withCreateJobFromTemplateRequest(body: Schema.CreateJobFromTemplateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Job])
			}
			object create {
				def apply(projectId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1b3/projects/${projectId}/templates").addQueryStringParameters())
			}
			/** Launches a template. To launch a template, we recommend using `projects.locations.templates.launch` with a [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints). Using `projects.templates.launch` is not recommended, because jobs launched from the template will always start in `us-central1`. */
			class launch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
				/** Perform the request */
				def withLaunchTemplateParameters(body: Schema.LaunchTemplateParameters) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LaunchTemplateResponse])
			}
			object launch {
				def apply(projectId: String, validateOnly: Boolean, gcsPath: String, dynamicTemplateGcsPath: String, dynamicTemplateStagingLocation: String, location: String)(using signer: RequestSigner, ec: ExecutionContext): launch = new launch(ws.url(BASE_URL + s"v1b3/projects/${projectId}/templates:launch").addQueryStringParameters("validateOnly" -> validateOnly.toString, "gcsPath" -> gcsPath.toString, "dynamicTemplate.gcsPath" -> dynamicTemplateGcsPath.toString, "dynamicTemplate.stagingLocation" -> dynamicTemplateStagingLocation.toString, "location" -> location.toString))
			}
			/** Get the template associated with a template. To get the template, we recommend using `projects.locations.templates.get` with a [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints). Using `projects.templates.get` is not recommended, because only templates that are running in `us-central1` are retrieved. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GetTemplateResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GetTemplateResponse])
			}
			object get {
				def apply(projectId: String, gcsPath: String, view: String, location: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1b3/projects/${projectId}/templates:get").addQueryStringParameters("gcsPath" -> gcsPath.toString, "view" -> view.toString, "location" -> location.toString))
				given Conversion[get, Future[Schema.GetTemplateResponse]] = (fun: get) => fun.apply()
			}
		}
		object jobs {
			/** List the jobs of a project across all regions. &#42;&#42;Note:&#42;&#42; This method doesn't support filtering the list of jobs by name. */
			class aggregated(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListJobsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
				/** Optional. The job name. */
				def withName(name: String) = new aggregated(req.addQueryStringParameters("name" -> name.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListJobsResponse])
			}
			object aggregated {
				def apply(projectId: String, filter: String, view: String, pageSize: Int, pageToken: String, location: String)(using signer: RequestSigner, ec: ExecutionContext): aggregated = new aggregated(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs:aggregated").addQueryStringParameters("filter" -> filter.toString, "view" -> view.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "location" -> location.toString))
				given Conversion[aggregated, Future[Schema.ListJobsResponse]] = (fun: aggregated) => fun.apply()
			}
			/** Creates a Cloud Dataflow job. To create a job, we recommend using `projects.locations.jobs.create` with a [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints). Using `projects.jobs.create` is not recommended, as your job will always start in `us-central1`. Do not enter confidential information when you supply string values using the API. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
				/** Perform the request */
				def withJob(body: Schema.Job) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Job])
			}
			object create {
				def apply(projectId: String, view: String, replaceJobId: String, location: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs").addQueryStringParameters("view" -> view.toString, "replaceJobId" -> replaceJobId.toString, "location" -> location.toString))
			}
			/** Snapshot the state of a streaming job. */
			class snapshot(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
				/** Perform the request */
				def withSnapshotJobRequest(body: Schema.SnapshotJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Snapshot])
			}
			object snapshot {
				def apply(projectId: String, jobId: String)(using signer: RequestSigner, ec: ExecutionContext): snapshot = new snapshot(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs/${jobId}:snapshot").addQueryStringParameters())
			}
			/** Request the job status. To request the status of a job, we recommend using `projects.locations.jobs.getMetrics` with a [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints). Using `projects.jobs.getMetrics` is not recommended, as you can only request the status of jobs that are running in `us-central1`. */
			class getMetrics(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.JobMetrics]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.JobMetrics])
			}
			object getMetrics {
				def apply(projectId: String, jobId: String, startTime: String, location: String)(using signer: RequestSigner, ec: ExecutionContext): getMetrics = new getMetrics(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs/${jobId}/metrics").addQueryStringParameters("startTime" -> startTime.toString, "location" -> location.toString))
				given Conversion[getMetrics, Future[Schema.JobMetrics]] = (fun: getMetrics) => fun.apply()
			}
			/** Updates the state of an existing Cloud Dataflow job. To update the state of an existing job, we recommend using `projects.locations.jobs.update` with a [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints). Using `projects.jobs.update` is not recommended, as you can only update the state of jobs that are running in `us-central1`. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
				/** Perform the request */
				def withJob(body: Schema.Job) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Job])
			}
			object update {
				def apply(projectId: String, jobId: String, location: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs/${jobId}").addQueryStringParameters("location" -> location.toString, "updateMask" -> updateMask.toString))
			}
			/** List the jobs of a project. To list the jobs of a project in a region, we recommend using `projects.locations.jobs.list` with a [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints). To list the all jobs across all regions, use `projects.jobs.aggregated`. Using `projects.jobs.list` is not recommended, because you can only get the list of jobs that are running in `us-central1`. `projects.locations.jobs.list` and `projects.jobs.list` support filtering the list of jobs by name. Filtering by name isn't supported by `projects.jobs.aggregated`. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListJobsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
				/** Optional. The job name. */
				def withName(name: String) = new list(req.addQueryStringParameters("name" -> name.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListJobsResponse])
			}
			object list {
				def apply(projectId: String, filter: String, view: String, pageSize: Int, pageToken: String, location: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs").addQueryStringParameters("filter" -> filter.toString, "view" -> view.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "location" -> location.toString))
				given Conversion[list, Future[Schema.ListJobsResponse]] = (fun: list) => fun.apply()
			}
			/** Gets the state of the specified Cloud Dataflow job. To get the state of a job, we recommend using `projects.locations.jobs.get` with a [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints). Using `projects.jobs.get` is not recommended, as you can only get the state of jobs that are running in `us-central1`. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Job]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Job])
			}
			object get {
				def apply(projectId: String, jobId: String, view: String, location: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs/${jobId}").addQueryStringParameters("view" -> view.toString, "location" -> location.toString))
				given Conversion[get, Future[Schema.Job]] = (fun: get) => fun.apply()
			}
			object messages {
				/** Request the job status. To request the status of a job, we recommend using `projects.locations.jobs.messages.list` with a [regional endpoint] (https://cloud.google.com/dataflow/docs/concepts/regional-endpoints). Using `projects.jobs.messages.list` is not recommended, as you can only request the status of jobs that are running in `us-central1`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListJobMessagesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListJobMessagesResponse])
				}
				object list {
					def apply(projectId: String, jobId: String, minimumImportance: String, pageSize: Int, pageToken: String, startTime: String, endTime: String, location: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs/${jobId}/messages").addQueryStringParameters("minimumImportance" -> minimumImportance.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "startTime" -> startTime.toString, "endTime" -> endTime.toString, "location" -> location.toString))
					given Conversion[list, Future[Schema.ListJobMessagesResponse]] = (fun: list) => fun.apply()
				}
			}
			object debug {
				/** Get encoded debug configuration for component. Not cacheable. */
				class getConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
					/** Perform the request */
					def withGetDebugConfigRequest(body: Schema.GetDebugConfigRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GetDebugConfigResponse])
				}
				object getConfig {
					def apply(projectId: String, jobId: String)(using signer: RequestSigner, ec: ExecutionContext): getConfig = new getConfig(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs/${jobId}/debug/getConfig").addQueryStringParameters())
				}
				/** Send encoded debug capture data for component. */
				class sendCapture(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
					/** Perform the request */
					def withSendDebugCaptureRequest(body: Schema.SendDebugCaptureRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SendDebugCaptureResponse])
				}
				object sendCapture {
					def apply(projectId: String, jobId: String)(using signer: RequestSigner, ec: ExecutionContext): sendCapture = new sendCapture(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs/${jobId}/debug/sendCapture").addQueryStringParameters())
				}
			}
			object workItems {
				/** Reports the status of dataflow WorkItems leased by a worker. */
				class reportStatus(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
					/** Perform the request */
					def withReportWorkItemStatusRequest(body: Schema.ReportWorkItemStatusRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReportWorkItemStatusResponse])
				}
				object reportStatus {
					def apply(projectId: String, jobId: String)(using signer: RequestSigner, ec: ExecutionContext): reportStatus = new reportStatus(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs/${jobId}/workItems:reportStatus").addQueryStringParameters())
				}
				/** Leases a dataflow WorkItem to run. */
				class lease(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
					/** Perform the request */
					def withLeaseWorkItemRequest(body: Schema.LeaseWorkItemRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LeaseWorkItemResponse])
				}
				object lease {
					def apply(projectId: String, jobId: String)(using signer: RequestSigner, ec: ExecutionContext): lease = new lease(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs/${jobId}/workItems:lease").addQueryStringParameters())
				}
			}
		}
	}
}
