package com.boresjo.play.api.google.deploymentmanager

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
		"""https://www.googleapis.com/auth/cloud-platform.read-only""" /* View your data across Google Cloud services and see the email address of your Google Account */,
		"""https://www.googleapis.com/auth/ndev.cloudman""" /* View and manage your Google Cloud Platform management resources and deployment status information */,
		"""https://www.googleapis.com/auth/ndev.cloudman.readonly""" /* View your Google Cloud Platform management resources and deployment status information */
	)

	private val BASE_URL = "https://deploymentmanager.googleapis.com/"

	object deployments {
		/** Returns permissions that a caller has on the specified resource. */
		class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.cloudman""")
			/** Perform the request */
			def withTestPermissionsRequest(body: Schema.TestPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestPermissionsResponse])
		}
		object testIamPermissions {
			def apply(project: String, resource: String, headerBypassBillingFilter: Boolean)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${resource}/testIamPermissions").addQueryStringParameters("header.bypassBillingFilter" -> headerBypassBillingFilter.toString))
		}
		/** Creates a deployment and all of the resources described by the deployment manifest. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.cloudman""")
			/** Perform the request */
			def withDeployment(body: Schema.Deployment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object insert {
			def apply(project: String, preview: Boolean, createPolicy: String, headerBypassBillingFilter: Boolean)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments").addQueryStringParameters("preview" -> preview.toString, "createPolicy" -> createPolicy.toString, "header.bypassBillingFilter" -> headerBypassBillingFilter.toString))
		}
		/** Sets the access control policy on the specified resource. Replaces any existing policy. */
		class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.cloudman""")
			/** Perform the request */
			def withGlobalSetPolicyRequest(body: Schema.GlobalSetPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(project: String, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${resource}/setIamPolicy").addQueryStringParameters())
		}
		/** Stops an ongoing operation. This does not roll back any work that has already been completed, but prevents any new work from being started. */
		class stop(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.cloudman""")
			/** Perform the request */
			def withDeploymentsStopRequest(body: Schema.DeploymentsStopRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object stop {
			def apply(project: String, deployment: String)(using signer: RequestSigner, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${deployment}/stop").addQueryStringParameters())
		}
		/** Gets the access control policy for a resource. May be empty if no such policy or resource exists. */
		class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.cloudman""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(project: String, resource: String, optionsRequestedPolicyVersion: Int, headerBypassBillingFilter: Boolean)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${resource}/getIamPolicy").addQueryStringParameters("optionsRequestedPolicyVersion" -> optionsRequestedPolicyVersion.toString, "header.bypassBillingFilter" -> headerBypassBillingFilter.toString))
			given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
		}
		/** Deletes a deployment and all of the resources in the deployment. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.cloudman""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(project: String, deployment: String, deletePolicy: String, headerBypassBillingFilter: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${deployment}").addQueryStringParameters("deletePolicy" -> deletePolicy.toString, "header.bypassBillingFilter" -> headerBypassBillingFilter.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		/** Gets information about a specific deployment. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Deployment]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.cloudman""", """https://www.googleapis.com/auth/ndev.cloudman.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Deployment])
		}
		object get {
			def apply(project: String, deployment: String, headerBypassBillingFilter: Boolean)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${deployment}").addQueryStringParameters("header.bypassBillingFilter" -> headerBypassBillingFilter.toString))
			given Conversion[get, Future[Schema.Deployment]] = (fun: get) => fun.apply()
		}
		/** Updates a deployment and all of the resources described by the deployment manifest. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.cloudman""")
			/** Perform the request */
			def withDeployment(body: Schema.Deployment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Operation])
		}
		object update {
			def apply(project: String, deployment: String, createPolicy: String, deletePolicy: String, preview: Boolean, headerBypassBillingFilter: Boolean)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${deployment}").addQueryStringParameters("createPolicy" -> createPolicy.toString, "deletePolicy" -> deletePolicy.toString, "preview" -> preview.toString, "header.bypassBillingFilter" -> headerBypassBillingFilter.toString))
		}
		/** Lists all deployments for a given project. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DeploymentsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.cloudman""", """https://www.googleapis.com/auth/ndev.cloudman.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DeploymentsListResponse])
		}
		object list {
			def apply(project: String, maxResults: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
			given Conversion[list, Future[Schema.DeploymentsListResponse]] = (fun: list) => fun.apply()
		}
		/** Patches a deployment and all of the resources described by the deployment manifest. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.cloudman""")
			/** Perform the request */
			def withDeployment(body: Schema.Deployment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(project: String, deployment: String, createPolicy: String, deletePolicy: String, preview: Boolean, headerBypassBillingFilter: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${deployment}").addQueryStringParameters("createPolicy" -> createPolicy.toString, "deletePolicy" -> deletePolicy.toString, "preview" -> preview.toString, "header.bypassBillingFilter" -> headerBypassBillingFilter.toString))
		}
		/** Cancels and removes the preview currently associated with the deployment. */
		class cancelPreview(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/ndev.cloudman""")
			/** Perform the request */
			def withDeploymentsCancelPreviewRequest(body: Schema.DeploymentsCancelPreviewRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object cancelPreview {
			def apply(project: String, deployment: String)(using signer: RequestSigner, ec: ExecutionContext): cancelPreview = new cancelPreview(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${deployment}/cancelPreview").addQueryStringParameters())
		}
	}
	object manifests {
		/** Gets information about a specific manifest. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Manifest]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.cloudman""", """https://www.googleapis.com/auth/ndev.cloudman.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Manifest])
		}
		object get {
			def apply(project: String, deployment: String, manifest: String, headerBypassBillingFilter: Boolean)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${deployment}/manifests/${manifest}").addQueryStringParameters("header.bypassBillingFilter" -> headerBypassBillingFilter.toString))
			given Conversion[get, Future[Schema.Manifest]] = (fun: get) => fun.apply()
		}
		/** Lists all manifests for a given deployment. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ManifestsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.cloudman""", """https://www.googleapis.com/auth/ndev.cloudman.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ManifestsListResponse])
		}
		object list {
			def apply(project: String, deployment: String, maxResults: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${deployment}/manifests").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
			given Conversion[list, Future[Schema.ManifestsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object operations {
		/** Gets information about a specific operation. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.cloudman""", """https://www.googleapis.com/auth/ndev.cloudman.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(project: String, operation: String, headerBypassBillingFilter: Boolean)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/operations/${operation}").addQueryStringParameters("header.bypassBillingFilter" -> headerBypassBillingFilter.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
		/** Lists all operations for a project. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.OperationsListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.cloudman""", """https://www.googleapis.com/auth/ndev.cloudman.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.OperationsListResponse])
		}
		object list {
			def apply(project: String, maxResults: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/operations").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
			given Conversion[list, Future[Schema.OperationsListResponse]] = (fun: list) => fun.apply()
		}
	}
	object resources {
		/** Gets information about a single resource. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Resource]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.cloudman""", """https://www.googleapis.com/auth/ndev.cloudman.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Resource])
		}
		object get {
			def apply(project: String, deployment: String, resource: String, headerBypassBillingFilter: Boolean)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${deployment}/resources/${resource}").addQueryStringParameters("header.bypassBillingFilter" -> headerBypassBillingFilter.toString))
			given Conversion[get, Future[Schema.Resource]] = (fun: get) => fun.apply()
		}
		/** Lists all resources in a given deployment. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ResourcesListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.cloudman""", """https://www.googleapis.com/auth/ndev.cloudman.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ResourcesListResponse])
		}
		object list {
			def apply(project: String, deployment: String, maxResults: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/deployments/${deployment}/resources").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
			given Conversion[list, Future[Schema.ResourcesListResponse]] = (fun: list) => fun.apply()
		}
	}
	object types {
		/** Lists all resource types for Deployment Manager. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TypesListResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/ndev.cloudman""", """https://www.googleapis.com/auth/ndev.cloudman.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TypesListResponse])
		}
		object list {
			def apply(project: String, maxResults: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"deploymentmanager/v2/projects/${project}/global/types").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
			given Conversion[list, Future[Schema.TypesListResponse]] = (fun: list) => fun.apply()
		}
	}
}
