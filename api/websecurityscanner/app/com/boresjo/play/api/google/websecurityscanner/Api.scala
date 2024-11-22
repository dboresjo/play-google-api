package com.boresjo.play.api.google.websecurityscanner

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://websecurityscanner.googleapis.com/"

	object projects {
		object scanConfigs {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withScanConfig(body: Schema.ScanConfig) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ScanConfig])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, scanConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ScanConfig]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ScanConfig])
			}
			object get {
				def apply(projectsId :PlayApi, scanConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.ScanConfig]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListScanConfigsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListScanConfigsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, pageSize: Int, pageToken: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListScanConfigsResponse]] = (fun: list) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withScanConfig(body: Schema.ScanConfig) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ScanConfig])
			}
			object patch {
				def apply(projectsId :PlayApi, scanConfigsId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			class start(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withStartScanRunRequest(body: Schema.StartScanRunRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ScanRun])
			}
			object start {
				def apply(projectsId :PlayApi, scanConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): start = new start(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}:start").addQueryStringParameters("name" -> name.toString))
			}
			object scanRuns {
				class stop(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withStopScanRunRequest(body: Schema.StopScanRunRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ScanRun])
				}
				object stop {
					def apply(projectsId :PlayApi, scanConfigsId :PlayApi, scanRunsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}/scanRuns/${scanRunsId}:stop").addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ScanRun]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ScanRun])
				}
				object get {
					def apply(projectsId :PlayApi, scanConfigsId :PlayApi, scanRunsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}/scanRuns/${scanRunsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ScanRun]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListScanRunsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListScanRunsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, scanConfigsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}/scanRuns").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListScanRunsResponse]] = (fun: list) => fun.apply()
				}
				object findingTypeStats {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFindingTypeStatsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListFindingTypeStatsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, scanConfigsId :PlayApi, scanRunsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}/scanRuns/${scanRunsId}/findingTypeStats").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListFindingTypeStatsResponse]] = (fun: list) => fun.apply()
					}
				}
				object findings {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Finding]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Finding])
					}
					object get {
						def apply(projectsId :PlayApi, scanConfigsId :PlayApi, scanRunsId :PlayApi, findingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}/scanRuns/${scanRunsId}/findings/${findingsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Finding]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFindingsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListFindingsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, scanConfigsId :PlayApi, scanRunsId :PlayApi, pageToken: String, pageSize: Int, parent: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}/scanRuns/${scanRunsId}/findings").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListFindingsResponse]] = (fun: list) => fun.apply()
					}
				}
				object crawledUrls {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCrawledUrlsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCrawledUrlsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, scanConfigsId :PlayApi, scanRunsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}/scanRuns/${scanRunsId}/crawledUrls").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListCrawledUrlsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
}
