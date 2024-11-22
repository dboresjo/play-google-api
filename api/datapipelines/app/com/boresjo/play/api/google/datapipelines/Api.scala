package com.boresjo.play.api.google.datapipelines

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

	private val BASE_URL = "https://datapipelines.googleapis.com/"

	object projects {
		object locations {
			object pipelines {
				/** Creates a job for the specified pipeline directly. You can use this method when the internal scheduler is not configured and you want to trigger the job directly or through an external system. Returns a "NOT_FOUND" error if the pipeline doesn't exist. Returns a "FORBIDDEN" error if the user doesn't have permission to access the pipeline or run jobs for the pipeline. */
				class run(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDatapipelinesV1RunPipelineRequest(body: Schema.GoogleCloudDatapipelinesV1RunPipelineRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatapipelinesV1RunPipelineResponse])
				}
				object run {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pipelinesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): run = new run(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/pipelines/${pipelinesId}:run").addQueryStringParameters("name" -> name.toString))
				}
				/** Creates a pipeline. For a batch pipeline, you can pass scheduler information. Data Pipelines uses the scheduler information to create an internal scheduler that runs jobs periodically. If the internal scheduler is not configured, you can use RunPipeline to run jobs. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDatapipelinesV1Pipeline(body: Schema.GoogleCloudDatapipelinesV1Pipeline) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatapipelinesV1Pipeline])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/pipelines").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Freezes pipeline execution permanently. If there's a corresponding scheduler entry, it's deleted, and the pipeline state is changed to "ARCHIVED". However, pipeline metadata is retained. */
				class stop(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDatapipelinesV1StopPipelineRequest(body: Schema.GoogleCloudDatapipelinesV1StopPipelineRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDatapipelinesV1Pipeline])
				}
				object stop {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pipelinesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/pipelines/${pipelinesId}:stop").addQueryStringParameters("name" -> name.toString))
				}
				/** Deletes a pipeline. If a scheduler job is attached to the pipeline, it will be deleted. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pipelinesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/pipelines/${pipelinesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Looks up a single pipeline. Returns a "NOT_FOUND" error if no such pipeline exists. Returns a "FORBIDDEN" error if the caller doesn't have permission to access it. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatapipelinesV1Pipeline]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatapipelinesV1Pipeline])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pipelinesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/pipelines/${pipelinesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDatapipelinesV1Pipeline]] = (fun: get) => fun.apply()
				}
				/** Updates a pipeline. If successful, the updated Pipeline is returned. Returns `NOT_FOUND` if the pipeline doesn't exist. If UpdatePipeline does not return successfully, you can retry the UpdatePipeline request until you receive a successful response. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDatapipelinesV1Pipeline(body: Schema.GoogleCloudDatapipelinesV1Pipeline) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDatapipelinesV1Pipeline])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pipelinesId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/pipelines/${pipelinesId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				/** Lists pipelines. Returns a "FORBIDDEN" error if the caller doesn't have permission to access it. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatapipelinesV1ListPipelinesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatapipelinesV1ListPipelinesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, filter: String, pageToken: String, pageSize: Int, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/pipelines").addQueryStringParameters("filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudDatapipelinesV1ListPipelinesResponse]] = (fun: list) => fun.apply()
				}
				object jobs {
					/** Lists jobs for a given pipeline. Throws a "FORBIDDEN" error if the caller doesn't have permission to access it. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatapipelinesV1ListJobsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDatapipelinesV1ListJobsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, pipelinesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/pipelines/${pipelinesId}/jobs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudDatapipelinesV1ListJobsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
}
