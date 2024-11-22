package com.boresjo.play.api.google.websecurityscanner

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

	private val BASE_URL = "https://websecurityscanner.googleapis.com/"

	object projects {
		object scanConfigs {
			/** Creates a new ScanConfig. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withScanConfig(body: Schema.ScanConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ScanConfig])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes an existing ScanConfig and its child resources. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, scanConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Gets a ScanConfig. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ScanConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ScanConfig])
			}
			object get {
				def apply(projectsId :PlayApi, scanConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.ScanConfig]] = (fun: get) => fun.apply()
			}
			/** Lists ScanConfigs under a given project. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListScanConfigsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListScanConfigsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, pageSize: Int, pageToken: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListScanConfigsResponse]] = (fun: list) => fun.apply()
			}
			/** Updates a ScanConfig. This method support partial update of a ScanConfig. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withScanConfig(body: Schema.ScanConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ScanConfig])
			}
			object patch {
				def apply(projectsId :PlayApi, scanConfigsId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			/** Start a ScanRun according to the given ScanConfig. */
			class start(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withStartScanRunRequest(body: Schema.StartScanRunRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ScanRun])
			}
			object start {
				def apply(projectsId :PlayApi, scanConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): start = new start(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}:start").addQueryStringParameters("name" -> name.toString))
			}
			object scanRuns {
				/** Stops a ScanRun. The stopped ScanRun is returned. */
				class stop(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withStopScanRunRequest(body: Schema.StopScanRunRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ScanRun])
				}
				object stop {
					def apply(projectsId :PlayApi, scanConfigsId :PlayApi, scanRunsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}/scanRuns/${scanRunsId}:stop").addQueryStringParameters("name" -> name.toString))
				}
				/** Gets a ScanRun. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ScanRun]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ScanRun])
				}
				object get {
					def apply(projectsId :PlayApi, scanConfigsId :PlayApi, scanRunsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}/scanRuns/${scanRunsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ScanRun]] = (fun: get) => fun.apply()
				}
				/** Lists ScanRuns under a given ScanConfig, in descending order of ScanRun stop time. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListScanRunsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListScanRunsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, scanConfigsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}/scanRuns").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListScanRunsResponse]] = (fun: list) => fun.apply()
				}
				object findingTypeStats {
					/** List all FindingTypeStats under a given ScanRun. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFindingTypeStatsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFindingTypeStatsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, scanConfigsId :PlayApi, scanRunsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}/scanRuns/${scanRunsId}/findingTypeStats").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListFindingTypeStatsResponse]] = (fun: list) => fun.apply()
					}
				}
				object findings {
					/** Gets a Finding. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Finding]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Finding])
					}
					object get {
						def apply(projectsId :PlayApi, scanConfigsId :PlayApi, scanRunsId :PlayApi, findingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}/scanRuns/${scanRunsId}/findings/${findingsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Finding]] = (fun: get) => fun.apply()
					}
					/** List Findings under a given ScanRun. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFindingsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFindingsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, scanConfigsId :PlayApi, scanRunsId :PlayApi, pageToken: String, pageSize: Int, parent: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}/scanRuns/${scanRunsId}/findings").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListFindingsResponse]] = (fun: list) => fun.apply()
					}
				}
				object crawledUrls {
					/** List CrawledUrls under a given ScanRun. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCrawledUrlsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCrawledUrlsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, scanConfigsId :PlayApi, scanRunsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/scanConfigs/${scanConfigsId}/scanRuns/${scanRunsId}/crawledUrls").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListCrawledUrlsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
}
