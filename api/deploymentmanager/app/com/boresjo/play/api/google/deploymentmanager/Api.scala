package com.boresjo.play.api.google.deploymentmanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://deploymentmanager.googleapis.com/"

	object deployments {
		class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTestPermissionsRequest(body: Schema.TestPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestPermissionsResponse])
		}
		object testIamPermissions {
			def apply(project: String, resource: String, headerBypassBillingFilter: Boolean)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${resource}/testIamPermissions")).addQueryStringParameters("header.bypassBillingFilter" -> headerBypassBillingFilter.toString))
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDeployment(body: Schema.Deployment) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
		}
		object insert {
			def apply(project: String, preview: Boolean, createPolicy: String, headerBypassBillingFilter: Boolean)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(auth(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments")).addQueryStringParameters("preview" -> preview.toString, "createPolicy" -> createPolicy.toString, "header.bypassBillingFilter" -> headerBypassBillingFilter.toString))
		}
		class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGlobalSetPolicyRequest(body: Schema.GlobalSetPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(project: String, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${resource}/setIamPolicy")).addQueryStringParameters())
		}
		class stop(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDeploymentsStopRequest(body: Schema.DeploymentsStopRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
		}
		object stop {
			def apply(project: String, deployment: String)(using auth: AuthToken, ec: ExecutionContext): stop = new stop(auth(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${deployment}/stop")).addQueryStringParameters())
		}
		class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(project: String, resource: String, optionsRequestedPolicyVersion: Int, headerBypassBillingFilter: Boolean)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${resource}/getIamPolicy")).addQueryStringParameters("optionsRequestedPolicyVersion" -> optionsRequestedPolicyVersion.toString, "header.bypassBillingFilter" -> headerBypassBillingFilter.toString))
			given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(project: String, deployment: String, deletePolicy: String, headerBypassBillingFilter: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${deployment}")).addQueryStringParameters("deletePolicy" -> deletePolicy.toString, "header.bypassBillingFilter" -> headerBypassBillingFilter.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Deployment]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Deployment])
		}
		object get {
			def apply(project: String, deployment: String, headerBypassBillingFilter: Boolean)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${deployment}")).addQueryStringParameters("header.bypassBillingFilter" -> headerBypassBillingFilter.toString))
			given Conversion[get, Future[Schema.Deployment]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDeployment(body: Schema.Deployment) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Operation])
		}
		object update {
			def apply(project: String, deployment: String, createPolicy: String, deletePolicy: String, preview: Boolean, headerBypassBillingFilter: Boolean)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${deployment}")).addQueryStringParameters("createPolicy" -> createPolicy.toString, "deletePolicy" -> deletePolicy.toString, "preview" -> preview.toString, "header.bypassBillingFilter" -> headerBypassBillingFilter.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DeploymentsListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.DeploymentsListResponse])
		}
		object list {
			def apply(project: String, maxResults: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments")).addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
			given Conversion[list, Future[Schema.DeploymentsListResponse]] = (fun: list) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDeployment(body: Schema.Deployment) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(project: String, deployment: String, createPolicy: String, deletePolicy: String, preview: Boolean, headerBypassBillingFilter: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${deployment}")).addQueryStringParameters("createPolicy" -> createPolicy.toString, "deletePolicy" -> deletePolicy.toString, "preview" -> preview.toString, "header.bypassBillingFilter" -> headerBypassBillingFilter.toString))
		}
		class cancelPreview(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withDeploymentsCancelPreviewRequest(body: Schema.DeploymentsCancelPreviewRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
		}
		object cancelPreview {
			def apply(project: String, deployment: String)(using auth: AuthToken, ec: ExecutionContext): cancelPreview = new cancelPreview(auth(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${deployment}/cancelPreview")).addQueryStringParameters())
		}
	}
	object manifests {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Manifest]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Manifest])
		}
		object get {
			def apply(project: String, deployment: String, manifest: String, headerBypassBillingFilter: Boolean)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${deployment}/manifests/${manifest}")).addQueryStringParameters("header.bypassBillingFilter" -> headerBypassBillingFilter.toString))
			given Conversion[get, Future[Schema.Manifest]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ManifestsListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ManifestsListResponse])
		}
		object list {
			def apply(project: String, deployment: String, maxResults: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${deployment}/manifests")).addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
			given Conversion[list, Future[Schema.ManifestsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object operations {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(project: String, operation: String, headerBypassBillingFilter: Boolean)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/operations/${operation}")).addQueryStringParameters("header.bypassBillingFilter" -> headerBypassBillingFilter.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.OperationsListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.OperationsListResponse])
		}
		object list {
			def apply(project: String, maxResults: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/operations")).addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
			given Conversion[list, Future[Schema.OperationsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object resources {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Resource]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Resource])
		}
		object get {
			def apply(project: String, deployment: String, resource: String, headerBypassBillingFilter: Boolean)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${deployment}/resources/${resource}")).addQueryStringParameters("header.bypassBillingFilter" -> headerBypassBillingFilter.toString))
			given Conversion[get, Future[Schema.Resource]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ResourcesListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ResourcesListResponse])
		}
		object list {
			def apply(project: String, deployment: String, maxResults: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${deployment}/resources")).addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
			given Conversion[list, Future[Schema.ResourcesListResponse]] = (fun: list) => fun.apply()
		}
	}
	object types {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TypesListResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.TypesListResponse])
		}
		object list {
			def apply(project: String, maxResults: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/types")).addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
			given Conversion[list, Future[Schema.TypesListResponse]] = (fun: list) => fun.apply()
		}
	}
}
