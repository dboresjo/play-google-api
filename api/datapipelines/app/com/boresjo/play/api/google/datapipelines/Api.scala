package com.boresjo.play.api.google.datapipelines

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://datapipelines.googleapis.com/"

	object projects {
		object locations {
			object pipelines {
				class run(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDatapipelinesV1RunPipelineRequest(body: Schema.GoogleCloudDatapipelinesV1RunPipelineRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudDatapipelinesV1RunPipelineResponse])
				}
				object run {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pipelinesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): run = new run(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/pipelines/${pipelinesId}:run")).addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDatapipelinesV1Pipeline(body: Schema.GoogleCloudDatapipelinesV1Pipeline) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudDatapipelinesV1Pipeline])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/pipelines")).addQueryStringParameters("parent" -> parent.toString))
				}
				class stop(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDatapipelinesV1StopPipelineRequest(body: Schema.GoogleCloudDatapipelinesV1StopPipelineRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudDatapipelinesV1Pipeline])
				}
				object stop {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pipelinesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): stop = new stop(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/pipelines/${pipelinesId}:stop")).addQueryStringParameters("name" -> name.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pipelinesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/pipelines/${pipelinesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatapipelinesV1Pipeline]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudDatapipelinesV1Pipeline])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pipelinesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/pipelines/${pipelinesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDatapipelinesV1Pipeline]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDatapipelinesV1Pipeline(body: Schema.GoogleCloudDatapipelinesV1Pipeline) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudDatapipelinesV1Pipeline])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pipelinesId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/pipelines/${pipelinesId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatapipelinesV1ListPipelinesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudDatapipelinesV1ListPipelinesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, filter: String, pageToken: String, pageSize: Int, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/pipelines")).addQueryStringParameters("filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudDatapipelinesV1ListPipelinesResponse]] = (fun: list) => fun.apply()
				}
				object jobs {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDatapipelinesV1ListJobsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudDatapipelinesV1ListJobsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, pipelinesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/pipelines/${pipelinesId}/jobs")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudDatapipelinesV1ListJobsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
}
