package com.boresjo.play.api.google.jobs

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://jobs.googleapis.com/"

	object projects {
		object operations {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
			}
			object get {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v4/projects/${projectsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
			}
		}
		object tenants {
			class completeQuery(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CompleteQueryResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CompleteQueryResponse])
			}
			object completeQuery {
				def apply(projectsId :PlayApi, tenantsId :PlayApi, tenant: String, query: String, languageCodes: String, pageSize: Int, company: String, scope: String, `type`: String)(using auth: AuthToken, ec: ExecutionContext): completeQuery = new completeQuery(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}:completeQuery").addQueryStringParameters("tenant" -> tenant.toString, "query" -> query.toString, "languageCodes" -> languageCodes.toString, "pageSize" -> pageSize.toString, "company" -> company.toString, "scope" -> scope.toString, "type" -> `type`.toString))
				given Conversion[completeQuery, Future[Schema.CompleteQueryResponse]] = (fun: completeQuery) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withTenant(body: Schema.Tenant) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Tenant])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, tenantsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Tenant]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Tenant])
			}
			object get {
				def apply(projectsId :PlayApi, tenantsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Tenant]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withTenant(body: Schema.Tenant) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Tenant])
			}
			object patch {
				def apply(projectsId :PlayApi, tenantsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTenantsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListTenantsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListTenantsResponse]] = (fun: list) => fun.apply()
			}
			object companies {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCompany(body: Schema.Company) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Company])
				}
				object create {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/companies").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, companiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/companies/${companiesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Company]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Company])
				}
				object get {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, companiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/companies/${companiesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Company]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCompany(body: Schema.Company) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Company])
				}
				object patch {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, companiesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/companies/${companiesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCompaniesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCompaniesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, parent: String, pageToken: String, pageSize: Int, requireOpenJobs: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/companies").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "requireOpenJobs" -> requireOpenJobs.toString))
					given Conversion[list, Future[Schema.ListCompaniesResponse]] = (fun: list) => fun.apply()
				}
			}
			object clientEvents {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withClientEvent(body: Schema.ClientEvent) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ClientEvent])
				}
				object create {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/clientEvents").addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object jobs {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withJob(body: Schema.Job) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Job])
				}
				object create {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/jobs").addQueryStringParameters("parent" -> parent.toString))
				}
				class searchForAlert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSearchJobsRequest(body: Schema.SearchJobsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SearchJobsResponse])
				}
				object searchForAlert {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): searchForAlert = new searchForAlert(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/jobs:searchForAlert").addQueryStringParameters("parent" -> parent.toString))
				}
				class batchUpdate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withBatchUpdateJobsRequest(body: Schema.BatchUpdateJobsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object batchUpdate {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/jobs:batchUpdate").addQueryStringParameters("parent" -> parent.toString))
				}
				class batchDelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withBatchDeleteJobsRequest(body: Schema.BatchDeleteJobsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object batchDelete {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/jobs:batchDelete").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, jobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/jobs/${jobsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class batchCreate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withBatchCreateJobsRequest(body: Schema.BatchCreateJobsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object batchCreate {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchCreate = new batchCreate(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/jobs:batchCreate").addQueryStringParameters("parent" -> parent.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Job]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Job])
				}
				object get {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, jobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/jobs/${jobsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Job]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withJob(body: Schema.Job) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Job])
				}
				object patch {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, jobsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/jobs/${jobsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListJobsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListJobsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, parent: String, filter: String, pageToken: String, pageSize: Int, jobView: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/jobs").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "jobView" -> jobView.toString))
					given Conversion[list, Future[Schema.ListJobsResponse]] = (fun: list) => fun.apply()
				}
				class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSearchJobsRequest(body: Schema.SearchJobsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SearchJobsResponse])
				}
				object search {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/jobs:search").addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
	}
}
