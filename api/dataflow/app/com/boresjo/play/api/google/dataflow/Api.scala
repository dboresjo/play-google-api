package com.boresjo.play.api.google.dataflow

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://dataflow.googleapis.com/"

	object projects {
		class deleteSnapshots(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DeleteSnapshotResponse]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.DeleteSnapshotResponse])
		}
		object deleteSnapshots {
			def apply(projectId: String, snapshotId: String, location: String)(using auth: AuthToken, ec: ExecutionContext): deleteSnapshots = new deleteSnapshots(ws.url(BASE_URL + s"v1b3/projects/${projectId}/snapshots").addQueryStringParameters("snapshotId" -> snapshotId.toString, "location" -> location.toString))
			given Conversion[deleteSnapshots, Future[Schema.DeleteSnapshotResponse]] = (fun: deleteSnapshots) => fun.apply()
		}
		class workerMessages(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSendWorkerMessagesRequest(body: Schema.SendWorkerMessagesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SendWorkerMessagesResponse])
		}
		object workerMessages {
			def apply(projectId: String)(using auth: AuthToken, ec: ExecutionContext): workerMessages = new workerMessages(ws.url(BASE_URL + s"v1b3/projects/${projectId}/WorkerMessages").addQueryStringParameters())
		}
		object locations {
			class workerMessages(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSendWorkerMessagesRequest(body: Schema.SendWorkerMessagesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SendWorkerMessagesResponse])
			}
			object workerMessages {
				def apply(projectId: String, location: String)(using auth: AuthToken, ec: ExecutionContext): workerMessages = new workerMessages(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/WorkerMessages").addQueryStringParameters())
			}
			object snapshots {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Snapshot]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Snapshot])
				}
				object get {
					def apply(projectId: String, location: String, snapshotId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/snapshots/${snapshotId}").addQueryStringParameters())
					given Conversion[get, Future[Schema.Snapshot]] = (fun: get) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DeleteSnapshotResponse]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.DeleteSnapshotResponse])
				}
				object delete {
					def apply(projectId: String, location: String, snapshotId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/snapshots/${snapshotId}").addQueryStringParameters())
					given Conversion[delete, Future[Schema.DeleteSnapshotResponse]] = (fun: delete) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSnapshotsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSnapshotsResponse])
				}
				object list {
					def apply(projectId: String, location: String, jobId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/snapshots").addQueryStringParameters("jobId" -> jobId.toString))
					given Conversion[list, Future[Schema.ListSnapshotsResponse]] = (fun: list) => fun.apply()
				}
			}
			object flexTemplates {
				class launch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLaunchFlexTemplateRequest(body: Schema.LaunchFlexTemplateRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LaunchFlexTemplateResponse])
				}
				object launch {
					def apply(projectId: String, location: String)(using auth: AuthToken, ec: ExecutionContext): launch = new launch(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/flexTemplates:launch").addQueryStringParameters())
				}
			}
			object templates {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCreateJobFromTemplateRequest(body: Schema.CreateJobFromTemplateRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Job])
				}
				object create {
					def apply(projectId: String, location: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/templates").addQueryStringParameters())
				}
				class launch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLaunchTemplateParameters(body: Schema.LaunchTemplateParameters) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LaunchTemplateResponse])
				}
				object launch {
					def apply(projectId: String, location: String, validateOnly: Boolean, gcsPath: String, dynamicTemplateGcsPath: String, dynamicTemplateStagingLocation: String)(using auth: AuthToken, ec: ExecutionContext): launch = new launch(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/templates:launch").addQueryStringParameters("validateOnly" -> validateOnly.toString, "gcsPath" -> gcsPath.toString, "dynamicTemplate.gcsPath" -> dynamicTemplateGcsPath.toString, "dynamicTemplate.stagingLocation" -> dynamicTemplateStagingLocation.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GetTemplateResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GetTemplateResponse])
				}
				object get {
					def apply(projectId: String, location: String, gcsPath: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/templates:get").addQueryStringParameters("gcsPath" -> gcsPath.toString, "view" -> view.toString))
					given Conversion[get, Future[Schema.GetTemplateResponse]] = (fun: get) => fun.apply()
				}
			}
			object jobs {
				class getExecutionDetails(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.JobExecutionDetails]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.JobExecutionDetails])
				}
				object getExecutionDetails {
					def apply(projectId: String, location: String, jobId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): getExecutionDetails = new getExecutionDetails(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}/executionDetails").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[getExecutionDetails, Future[Schema.JobExecutionDetails]] = (fun: getExecutionDetails) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withJob(body: Schema.Job) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Job])
				}
				object create {
					def apply(projectId: String, location: String, view: String, replaceJobId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs").addQueryStringParameters("view" -> view.toString, "replaceJobId" -> replaceJobId.toString))
				}
				class snapshot(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSnapshotJobRequest(body: Schema.SnapshotJobRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Snapshot])
				}
				object snapshot {
					def apply(projectId: String, location: String, jobId: String)(using auth: AuthToken, ec: ExecutionContext): snapshot = new snapshot(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}:snapshot").addQueryStringParameters())
				}
				class getMetrics(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.JobMetrics]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.JobMetrics])
				}
				object getMetrics {
					def apply(projectId: String, location: String, jobId: String, startTime: String)(using auth: AuthToken, ec: ExecutionContext): getMetrics = new getMetrics(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}/metrics").addQueryStringParameters("startTime" -> startTime.toString))
					given Conversion[getMetrics, Future[Schema.JobMetrics]] = (fun: getMetrics) => fun.apply()
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withJob(body: Schema.Job) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Job])
				}
				object update {
					def apply(projectId: String, location: String, jobId: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}").addQueryStringParameters("updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListJobsResponse]) {
					/** Optional. The job name. */
					def withName(name: String) = new list(req.addQueryStringParameters("name" -> name.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListJobsResponse])
				}
				object list {
					def apply(projectId: String, location: String, filter: String, view: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs").addQueryStringParameters("filter" -> filter.toString, "view" -> view.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListJobsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Job]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Job])
				}
				object get {
					def apply(projectId: String, location: String, jobId: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}").addQueryStringParameters("view" -> view.toString))
					given Conversion[get, Future[Schema.Job]] = (fun: get) => fun.apply()
				}
				object messages {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListJobMessagesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListJobMessagesResponse])
					}
					object list {
						def apply(projectId: String, location: String, jobId: String, minimumImportance: String, pageSize: Int, pageToken: String, startTime: String, endTime: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}/messages").addQueryStringParameters("minimumImportance" -> minimumImportance.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "startTime" -> startTime.toString, "endTime" -> endTime.toString))
						given Conversion[list, Future[Schema.ListJobMessagesResponse]] = (fun: list) => fun.apply()
					}
				}
				object stages {
					class getExecutionDetails(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.StageExecutionDetails]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.StageExecutionDetails])
					}
					object getExecutionDetails {
						def apply(projectId: String, location: String, jobId: String, stageId: String, pageSize: Int, pageToken: String, startTime: String, endTime: String)(using auth: AuthToken, ec: ExecutionContext): getExecutionDetails = new getExecutionDetails(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}/stages/${stageId}/executionDetails").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "startTime" -> startTime.toString, "endTime" -> endTime.toString))
						given Conversion[getExecutionDetails, Future[Schema.StageExecutionDetails]] = (fun: getExecutionDetails) => fun.apply()
					}
				}
				object snapshots {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSnapshotsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSnapshotsResponse])
					}
					object list {
						def apply(projectId: String, location: String, jobId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}/snapshots").addQueryStringParameters())
						given Conversion[list, Future[Schema.ListSnapshotsResponse]] = (fun: list) => fun.apply()
					}
				}
				object debug {
					class getConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGetDebugConfigRequest(body: Schema.GetDebugConfigRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GetDebugConfigResponse])
					}
					object getConfig {
						def apply(projectId: String, location: String, jobId: String)(using auth: AuthToken, ec: ExecutionContext): getConfig = new getConfig(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}/debug/getConfig").addQueryStringParameters())
					}
					class sendCapture(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSendDebugCaptureRequest(body: Schema.SendDebugCaptureRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SendDebugCaptureResponse])
					}
					object sendCapture {
						def apply(projectId: String, location: String, jobId: String)(using auth: AuthToken, ec: ExecutionContext): sendCapture = new sendCapture(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}/debug/sendCapture").addQueryStringParameters())
					}
				}
				object workItems {
					class reportStatus(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withReportWorkItemStatusRequest(body: Schema.ReportWorkItemStatusRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReportWorkItemStatusResponse])
					}
					object reportStatus {
						def apply(projectId: String, location: String, jobId: String)(using auth: AuthToken, ec: ExecutionContext): reportStatus = new reportStatus(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}/workItems:reportStatus").addQueryStringParameters())
					}
					class lease(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withLeaseWorkItemRequest(body: Schema.LeaseWorkItemRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LeaseWorkItemResponse])
					}
					object lease {
						def apply(projectId: String, location: String, jobId: String)(using auth: AuthToken, ec: ExecutionContext): lease = new lease(ws.url(BASE_URL + s"v1b3/projects/${projectId}/locations/${location}/jobs/${jobId}/workItems:lease").addQueryStringParameters())
					}
				}
			}
		}
		object snapshots {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Snapshot]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Snapshot])
			}
			object get {
				def apply(projectId: String, snapshotId: String, location: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1b3/projects/${projectId}/snapshots/${snapshotId}").addQueryStringParameters("location" -> location.toString))
				given Conversion[get, Future[Schema.Snapshot]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSnapshotsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSnapshotsResponse])
			}
			object list {
				def apply(projectId: String, jobId: String, location: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1b3/projects/${projectId}/snapshots").addQueryStringParameters("jobId" -> jobId.toString, "location" -> location.toString))
				given Conversion[list, Future[Schema.ListSnapshotsResponse]] = (fun: list) => fun.apply()
			}
		}
		object templates {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCreateJobFromTemplateRequest(body: Schema.CreateJobFromTemplateRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Job])
			}
			object create {
				def apply(projectId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1b3/projects/${projectId}/templates").addQueryStringParameters())
			}
			class launch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLaunchTemplateParameters(body: Schema.LaunchTemplateParameters) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LaunchTemplateResponse])
			}
			object launch {
				def apply(projectId: String, validateOnly: Boolean, gcsPath: String, dynamicTemplateGcsPath: String, dynamicTemplateStagingLocation: String, location: String)(using auth: AuthToken, ec: ExecutionContext): launch = new launch(ws.url(BASE_URL + s"v1b3/projects/${projectId}/templates:launch").addQueryStringParameters("validateOnly" -> validateOnly.toString, "gcsPath" -> gcsPath.toString, "dynamicTemplate.gcsPath" -> dynamicTemplateGcsPath.toString, "dynamicTemplate.stagingLocation" -> dynamicTemplateStagingLocation.toString, "location" -> location.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GetTemplateResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GetTemplateResponse])
			}
			object get {
				def apply(projectId: String, gcsPath: String, view: String, location: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1b3/projects/${projectId}/templates:get").addQueryStringParameters("gcsPath" -> gcsPath.toString, "view" -> view.toString, "location" -> location.toString))
				given Conversion[get, Future[Schema.GetTemplateResponse]] = (fun: get) => fun.apply()
			}
		}
		object jobs {
			class aggregated(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListJobsResponse]) {
				/** Optional. The job name. */
				def withName(name: String) = new aggregated(req.addQueryStringParameters("name" -> name.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListJobsResponse])
			}
			object aggregated {
				def apply(projectId: String, filter: String, view: String, pageSize: Int, pageToken: String, location: String)(using auth: AuthToken, ec: ExecutionContext): aggregated = new aggregated(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs:aggregated").addQueryStringParameters("filter" -> filter.toString, "view" -> view.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "location" -> location.toString))
				given Conversion[aggregated, Future[Schema.ListJobsResponse]] = (fun: aggregated) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withJob(body: Schema.Job) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Job])
			}
			object create {
				def apply(projectId: String, view: String, replaceJobId: String, location: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs").addQueryStringParameters("view" -> view.toString, "replaceJobId" -> replaceJobId.toString, "location" -> location.toString))
			}
			class snapshot(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSnapshotJobRequest(body: Schema.SnapshotJobRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Snapshot])
			}
			object snapshot {
				def apply(projectId: String, jobId: String)(using auth: AuthToken, ec: ExecutionContext): snapshot = new snapshot(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs/${jobId}:snapshot").addQueryStringParameters())
			}
			class getMetrics(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.JobMetrics]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.JobMetrics])
			}
			object getMetrics {
				def apply(projectId: String, jobId: String, startTime: String, location: String)(using auth: AuthToken, ec: ExecutionContext): getMetrics = new getMetrics(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs/${jobId}/metrics").addQueryStringParameters("startTime" -> startTime.toString, "location" -> location.toString))
				given Conversion[getMetrics, Future[Schema.JobMetrics]] = (fun: getMetrics) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withJob(body: Schema.Job) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Job])
			}
			object update {
				def apply(projectId: String, jobId: String, location: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs/${jobId}").addQueryStringParameters("location" -> location.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListJobsResponse]) {
				/** Optional. The job name. */
				def withName(name: String) = new list(req.addQueryStringParameters("name" -> name.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListJobsResponse])
			}
			object list {
				def apply(projectId: String, filter: String, view: String, pageSize: Int, pageToken: String, location: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs").addQueryStringParameters("filter" -> filter.toString, "view" -> view.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "location" -> location.toString))
				given Conversion[list, Future[Schema.ListJobsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Job]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Job])
			}
			object get {
				def apply(projectId: String, jobId: String, view: String, location: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs/${jobId}").addQueryStringParameters("view" -> view.toString, "location" -> location.toString))
				given Conversion[get, Future[Schema.Job]] = (fun: get) => fun.apply()
			}
			object messages {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListJobMessagesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListJobMessagesResponse])
				}
				object list {
					def apply(projectId: String, jobId: String, minimumImportance: String, pageSize: Int, pageToken: String, startTime: String, endTime: String, location: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs/${jobId}/messages").addQueryStringParameters("minimumImportance" -> minimumImportance.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "startTime" -> startTime.toString, "endTime" -> endTime.toString, "location" -> location.toString))
					given Conversion[list, Future[Schema.ListJobMessagesResponse]] = (fun: list) => fun.apply()
				}
			}
			object debug {
				class getConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetDebugConfigRequest(body: Schema.GetDebugConfigRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GetDebugConfigResponse])
				}
				object getConfig {
					def apply(projectId: String, jobId: String)(using auth: AuthToken, ec: ExecutionContext): getConfig = new getConfig(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs/${jobId}/debug/getConfig").addQueryStringParameters())
				}
				class sendCapture(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSendDebugCaptureRequest(body: Schema.SendDebugCaptureRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SendDebugCaptureResponse])
				}
				object sendCapture {
					def apply(projectId: String, jobId: String)(using auth: AuthToken, ec: ExecutionContext): sendCapture = new sendCapture(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs/${jobId}/debug/sendCapture").addQueryStringParameters())
				}
			}
			object workItems {
				class reportStatus(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withReportWorkItemStatusRequest(body: Schema.ReportWorkItemStatusRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReportWorkItemStatusResponse])
				}
				object reportStatus {
					def apply(projectId: String, jobId: String)(using auth: AuthToken, ec: ExecutionContext): reportStatus = new reportStatus(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs/${jobId}/workItems:reportStatus").addQueryStringParameters())
				}
				class lease(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLeaseWorkItemRequest(body: Schema.LeaseWorkItemRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LeaseWorkItemResponse])
				}
				object lease {
					def apply(projectId: String, jobId: String)(using auth: AuthToken, ec: ExecutionContext): lease = new lease(ws.url(BASE_URL + s"v1b3/projects/${projectId}/jobs/${jobId}/workItems:lease").addQueryStringParameters())
				}
			}
		}
	}
}
