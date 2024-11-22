package com.boresjo.play.api.google.jobs

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
		"""https://www.googleapis.com/auth/jobs""" /* Manage job postings */
	)

	private val BASE_URL = "https://jobs.googleapis.com/"

	object projects {
		object operations {
			/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
			}
			object get {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v4/projects/${projectsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
			}
		}
		object tenants {
			/** Completes the specified prefix with keyword suggestions. Intended for use by a job search auto-complete search box. */
			class completeQuery(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CompleteQueryResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CompleteQueryResponse])
			}
			object completeQuery {
				def apply(projectsId :PlayApi, tenantsId :PlayApi, tenant: String, query: String, languageCodes: String, pageSize: Int, company: String, scope: String, `type`: String)(using signer: RequestSigner, ec: ExecutionContext): completeQuery = new completeQuery(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}:completeQuery").addQueryStringParameters("tenant" -> tenant.toString, "query" -> query.toString, "languageCodes" -> languageCodes.toString, "pageSize" -> pageSize.toString, "company" -> company.toString, "scope" -> scope.toString, "type" -> `type`.toString))
				given Conversion[completeQuery, Future[Schema.CompleteQueryResponse]] = (fun: completeQuery) => fun.apply()
			}
			/** Creates a new tenant entity. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
				/** Perform the request */
				def withTenant(body: Schema.Tenant) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Tenant])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes specified tenant. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, tenantsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Retrieves specified tenant. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Tenant]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Tenant])
			}
			object get {
				def apply(projectsId :PlayApi, tenantsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Tenant]] = (fun: get) => fun.apply()
			}
			/** Updates specified tenant. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
				/** Perform the request */
				def withTenant(body: Schema.Tenant) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Tenant])
			}
			object patch {
				def apply(projectsId :PlayApi, tenantsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists all tenants associated with the project. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTenantsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTenantsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListTenantsResponse]] = (fun: list) => fun.apply()
			}
			object companies {
				/** Creates a new company entity. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
					/** Perform the request */
					def withCompany(body: Schema.Company) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Company])
				}
				object create {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/companies").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes specified company. Prerequisite: The company has no jobs associated with it. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, companiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/companies/${companiesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Retrieves specified company. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Company]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Company])
				}
				object get {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, companiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/companies/${companiesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Company]] = (fun: get) => fun.apply()
				}
				/** Updates specified company. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
					/** Perform the request */
					def withCompany(body: Schema.Company) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Company])
				}
				object patch {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, companiesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/companies/${companiesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists all companies associated with the project. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCompaniesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCompaniesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, parent: String, pageToken: String, pageSize: Int, requireOpenJobs: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/companies").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "requireOpenJobs" -> requireOpenJobs.toString))
					given Conversion[list, Future[Schema.ListCompaniesResponse]] = (fun: list) => fun.apply()
				}
			}
			object clientEvents {
				/** Report events issued when end user interacts with customer's application that uses Cloud Talent Solution. You may inspect the created events in [self service tools](https://console.cloud.google.com/talent-solution/overview). [Learn more](https://cloud.google.com/talent-solution/docs/management-tools) about self service tools. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
					/** Perform the request */
					def withClientEvent(body: Schema.ClientEvent) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ClientEvent])
				}
				object create {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/clientEvents").addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object jobs {
				/** Creates a new job. Typically, the job becomes searchable within 10 seconds, but it may take up to 5 minutes. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
					/** Perform the request */
					def withJob(body: Schema.Job) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Job])
				}
				object create {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/jobs").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Searches for jobs using the provided SearchJobsRequest. This API call is intended for the use case of targeting passive job seekers (for example, job seekers who have signed up to receive email alerts about potential job opportunities), it has different algorithmic adjustments that are designed to specifically target passive job seekers. This call constrains the visibility of jobs present in the database, and only returns jobs the caller has permission to search against. */
				class searchForAlert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
					/** Perform the request */
					def withSearchJobsRequest(body: Schema.SearchJobsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SearchJobsResponse])
				}
				object searchForAlert {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): searchForAlert = new searchForAlert(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/jobs:searchForAlert").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Begins executing a batch update jobs operation. */
				class batchUpdate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
					/** Perform the request */
					def withBatchUpdateJobsRequest(body: Schema.BatchUpdateJobsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object batchUpdate {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/jobs:batchUpdate").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Begins executing a batch delete jobs operation. */
				class batchDelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
					/** Perform the request */
					def withBatchDeleteJobsRequest(body: Schema.BatchDeleteJobsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object batchDelete {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/jobs:batchDelete").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes the specified job. Typically, the job becomes unsearchable within 10 seconds, but it may take up to 5 minutes. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, jobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/jobs/${jobsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Begins executing a batch create jobs operation. */
				class batchCreate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
					/** Perform the request */
					def withBatchCreateJobsRequest(body: Schema.BatchCreateJobsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object batchCreate {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchCreate = new batchCreate(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/jobs:batchCreate").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Retrieves the specified job, whose status is OPEN or recently EXPIRED within the last 90 days. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Job]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Job])
				}
				object get {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, jobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/jobs/${jobsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Job]] = (fun: get) => fun.apply()
				}
				/** Updates specified job. Typically, updated contents become visible in search results within 10 seconds, but it may take up to 5 minutes. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
					/** Perform the request */
					def withJob(body: Schema.Job) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Job])
				}
				object patch {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, jobsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/jobs/${jobsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists jobs by filter. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListJobsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListJobsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, parent: String, filter: String, pageToken: String, pageSize: Int, jobView: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/jobs").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "jobView" -> jobView.toString))
					given Conversion[list, Future[Schema.ListJobsResponse]] = (fun: list) => fun.apply()
				}
				/** Searches for jobs using the provided SearchJobsRequest. This call constrains the visibility of jobs present in the database, and only returns jobs that the caller has permission to search against. */
				class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/jobs""")
					/** Perform the request */
					def withSearchJobsRequest(body: Schema.SearchJobsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SearchJobsResponse])
				}
				object search {
					def apply(projectsId :PlayApi, tenantsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v4/projects/${projectsId}/tenants/${tenantsId}/jobs:search").addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
	}
}
