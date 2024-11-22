package com.boresjo.play.api.google.bigquerydatapolicy

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
		"""https://www.googleapis.com/auth/bigquery""" /* View and manage your data in Google BigQuery and see the email address for your Google Account */,
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://bigquerydatapolicy.googleapis.com/"

	object projects {
		object locations {
			object dataPolicies {
				/** Returns the caller's permission on the specified data policy resource. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataPoliciesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataPolicies/${dataPoliciesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Renames the id (display name) of the specified data policy. */
				class rename(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRenameDataPolicyRequest(body: Schema.RenameDataPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DataPolicy])
				}
				object rename {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataPoliciesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): rename = new rename(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataPolicies/${dataPoliciesId}:rename").addQueryStringParameters("name" -> name.toString))
				}
				/** Gets the IAM policy for the specified data policy. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataPoliciesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataPolicies/${dataPoliciesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Deletes the data policy specified by its resource name. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. If true, the data policy will be deleted even when it is referenced by one or more table columns. */
					def withForce(force: Boolean) = new delete(req.addQueryStringParameters("force" -> force.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataPoliciesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataPolicies/${dataPoliciesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets the data policy specified by its resource name. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DataPolicy]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DataPolicy])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataPoliciesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataPolicies/${dataPoliciesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.DataPolicy]] = (fun: get) => fun.apply()
				}
				/** Updates the metadata for an existing data policy. The target data policy can be specified by the resource name. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. If set to true, and the data policy is not found, a new data policy will be created. In this situation, update_mask is ignored. */
					def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					/** Perform the request */
					def withDataPolicy(body: Schema.DataPolicy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.DataPolicy])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataPoliciesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataPolicies/${dataPoliciesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** List all of the data policies in the specified parent project. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDataPoliciesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDataPoliciesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataPolicies").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListDataPoliciesResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a new data policy under a project with the given `dataPolicyId` (used as the display name), policy tag, and data policy type. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withDataPolicy(body: Schema.DataPolicy) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DataPolicy])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataPolicies").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Sets the IAM policy for the specified data policy. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataPoliciesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataPolicies/${dataPoliciesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
			}
		}
	}
}
