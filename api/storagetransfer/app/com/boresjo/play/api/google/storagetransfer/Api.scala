package com.boresjo.play.api.google.storagetransfer

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://storagetransfer.googleapis.com/"

	object transferOperations {
		class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCancelOperationRequest(body: Schema.CancelOperationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
		}
		object cancel {
			def apply(transferOperationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/transferOperations/${transferOperationsId}:cancel")).addQueryStringParameters("name" -> name.toString))
		}
		class resume(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withResumeTransferOperationRequest(body: Schema.ResumeTransferOperationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
		}
		object resume {
			def apply(transferOperationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): resume = new resume(auth(ws.url(BASE_URL + s"v1/transferOperations/${transferOperationsId}:resume")).addQueryStringParameters("name" -> name.toString))
		}
		class pause(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withPauseTransferOperationRequest(body: Schema.PauseTransferOperationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
		}
		object pause {
			def apply(transferOperationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): pause = new pause(auth(ws.url(BASE_URL + s"v1/transferOperations/${transferOperationsId}:pause")).addQueryStringParameters("name" -> name.toString))
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(transferOperationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/transferOperations/${transferOperationsId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ListOperationsResponse])
		}
		object list {
			def apply(name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/transferOperations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
		}
	}
	object googleServiceAccounts {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleServiceAccount]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleServiceAccount])
		}
		object get {
			def apply(projectId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/googleServiceAccounts/${projectId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.GoogleServiceAccount]] = (fun: get) => fun.apply()
		}
	}
	object transferJobs {
		class run(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRunTransferJobRequest(body: Schema.RunTransferJobRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
		}
		object run {
			def apply(transferJobsId :PlayApi, jobName: String)(using auth: AuthToken, ec: ExecutionContext): run = new run(auth(ws.url(BASE_URL + s"v1/transferJobs/${transferJobsId}:run")).addQueryStringParameters("jobName" -> jobName.toString))
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTransferJob(body: Schema.TransferJob) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TransferJob])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/transferJobs")).addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(transferJobsId :PlayApi, jobName: String, projectId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/transferJobs/${transferJobsId}")).addQueryStringParameters("jobName" -> jobName.toString, "projectId" -> projectId.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TransferJob]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.TransferJob])
		}
		object get {
			def apply(transferJobsId :PlayApi, jobName: String, projectId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/transferJobs/${transferJobsId}")).addQueryStringParameters("jobName" -> jobName.toString, "projectId" -> projectId.toString))
			given Conversion[get, Future[Schema.TransferJob]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withUpdateTransferJobRequest(body: Schema.UpdateTransferJobRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.TransferJob])
		}
		object patch {
			def apply(transferJobsId :PlayApi, jobName: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/transferJobs/${transferJobsId}")).addQueryStringParameters("jobName" -> jobName.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTransferJobsResponse]) {
			/** Required. A list of query parameters specified as JSON text in the form of: ``` { "projectId":"my_project_id", "jobNames":["jobid1","jobid2",...], "jobStatuses":["status1","status2",...], "dataBackend":"QUERY_REPLICATION_CONFIGS", "sourceBucket":"source-bucket-name", "sinkBucket":"sink-bucket-name", } ``` The JSON formatting in the example is for display only; provide the query parameters without spaces or line breaks. &#42; `projectId` is required. &#42; Since `jobNames` and `jobStatuses` support multiple values, their values must be specified with array notation. `jobNames` and `jobStatuses` are optional. Valid values are case-insensitive: &#42; ENABLED &#42; DISABLED &#42; DELETED &#42; Specify `"dataBackend":"QUERY_REPLICATION_CONFIGS"` to return a list of cross-bucket replication jobs. &#42; Limit the results to jobs from a particular bucket with `sourceBucket` and/or to a particular bucket with `sinkBucket`. */
			def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
			def apply() = req.execute("GET").map(_.json.as[Schema.ListTransferJobsResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/transferJobs")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListTransferJobsResponse]] = (fun: list) => fun.apply()
		}
	}
	object projects {
		object agentPools {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAgentPool(body: Schema.AgentPool) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.AgentPool])
			}
			object create {
				def apply(projectsId :PlayApi, projectId: String, agentPoolId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/agentPools")).addQueryStringParameters("projectId" -> projectId.toString, "agentPoolId" -> agentPoolId.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, agentPoolsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/agentPools/${agentPoolsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AgentPool]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.AgentPool])
			}
			object get {
				def apply(projectsId :PlayApi, agentPoolsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/agentPools/${agentPoolsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.AgentPool]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAgentPool(body: Schema.AgentPool) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.AgentPool])
			}
			object patch {
				def apply(projectsId :PlayApi, agentPoolsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/agentPools/${agentPoolsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAgentPoolsResponse]) {
				/** An optional list of query parameters specified as JSON text in the form of: `{"agentPoolNames":["agentpool1","agentpool2",...]}` Since `agentPoolNames` support multiple values, its values must be specified with array notation. When the filter is either empty or not provided, the list returns all agent pools for the project. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.ListAgentPoolsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, projectId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/agentPools")).addQueryStringParameters("projectId" -> projectId.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAgentPoolsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
