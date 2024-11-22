package com.boresjo.play.api.google.apigeeregistry

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

	private val BASE_URL = "https://apigeeregistry.googleapis.com/"

	object projects {
		object locations {
			/** Gets information about a location. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object artifacts {
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, artifactsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/artifacts/${artifactsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Returns the contents of a specified artifact. If artifacts are stored with GZip compression, the default behavior is to return the artifact uncompressed (the mime_type response field indicates the exact format returned). */
				class getContents(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
				}
				object getContents {
					def apply(projectsId :PlayApi, locationsId :PlayApi, artifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getContents = new getContents(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/artifacts/${artifactsId}:getContents").addQueryStringParameters("name" -> name.toString))
					given Conversion[getContents, Future[Schema.HttpBody]] = (fun: getContents) => fun.apply()
				}
				/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, artifactsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/artifacts/${artifactsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Removes a specified artifact. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, artifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Used to replace a specified artifact. */
				class replaceArtifact(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withArtifact(body: Schema.Artifact) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Artifact])
				}
				object replaceArtifact {
					def apply(projectsId :PlayApi, locationsId :PlayApi, artifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): replaceArtifact = new replaceArtifact(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Returns a specified artifact. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Artifact]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Artifact])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, artifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Artifact]] = (fun: get) => fun.apply()
				}
				/** Returns matching artifacts. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListArtifactsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListArtifactsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/artifacts").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.ListArtifactsResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a specified artifact. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withArtifact(body: Schema.Artifact) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Artifact])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, artifactId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/artifacts").addQueryStringParameters("parent" -> parent.toString, "artifactId" -> artifactId.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, artifactsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/artifacts/${artifactsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
			}
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
			object instances {
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Deletes the Registry instance. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Gets details of a single Instance. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Instance]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Instance])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Instance]] = (fun: get) => fun.apply()
				}
				/** Provisions instance resources for the Registry. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withInstance(body: Schema.Instance) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, instanceId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances").addQueryStringParameters("parent" -> parent.toString, "instanceId" -> instanceId.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
			}
			object apis {
				/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Removes a specified API and all of the resources that it owns. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, name: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Returns a specified API. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Api]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Api])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Api]] = (fun: get) => fun.apply()
				}
				/** Used to modify a specified API. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withApi(body: Schema.Api) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Api])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, name: String, updateMask: String, allowMissing: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "allowMissing" -> allowMissing.toString))
				}
				/** Returns matching APIs. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListApisResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListApisResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.ListApisResponse]] = (fun: list) => fun.apply()
				}
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Creates a specified API. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withApi(body: Schema.Api) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Api])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, apiId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis").addQueryStringParameters("parent" -> parent.toString, "apiId" -> apiId.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object versions {
					/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
					class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					/** Removes a specified version and all of the resources that it owns. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, name: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Returns a specified version. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ApiVersion]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ApiVersion])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.ApiVersion]] = (fun: get) => fun.apply()
					}
					/** Used to modify a specified version. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withApiVersion(body: Schema.ApiVersion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ApiVersion])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, name: String, updateMask: String, allowMissing: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "allowMissing" -> allowMissing.toString))
					}
					/** Returns matching versions. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListApiVersionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListApiVersionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
						given Conversion[list, Future[Schema.ListApiVersionsResponse]] = (fun: list) => fun.apply()
					}
					/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Creates a specified version. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withApiVersion(body: Schema.ApiVersion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApiVersion])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, parent: String, apiVersionId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions").addQueryStringParameters("parent" -> parent.toString, "apiVersionId" -> apiVersionId.toString))
					}
					/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					object specs {
						/** Returns the contents of a specified spec. If specs are stored with GZip compression, the default behavior is to return the spec uncompressed (the mime_type response field indicates the exact format returned). */
						class getContents(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
						}
						object getContents {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getContents = new getContents(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}:getContents").addQueryStringParameters("name" -> name.toString))
							given Conversion[getContents, Future[Schema.HttpBody]] = (fun: getContents) => fun.apply()
						}
						/** Sets the current revision to a specified prior revision. Note that this creates a new revision with a new revision ID. */
						class rollback(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withRollbackApiSpecRequest(body: Schema.RollbackApiSpecRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApiSpec])
						}
						object rollback {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): rollback = new rollback(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}:rollback").addQueryStringParameters("name" -> name.toString))
						}
						/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
						class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
							def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
						}
						object getIamPolicy {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
							given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
						}
						/** Removes a specified spec, all revisions, and all child resources (e.g., artifacts). */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, name: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						/** Adds a tag to a specified revision of a spec. */
						class tagRevision(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Required. The name of the spec to be tagged, including the revision ID is optional. If a revision is not specified, it will tag the latest revision. */
							def withName(name: String) = new tagRevision(req.addQueryStringParameters("name" -> name.toString))
							/** Perform the request */
							def withTagApiSpecRevisionRequest(body: Schema.TagApiSpecRevisionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApiSpec])
						}
						object tagRevision {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): tagRevision = new tagRevision(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}:tagRevision").addQueryStringParameters())
						}
						/** Deletes a revision of a spec. */
						class deleteRevision(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ApiSpec]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.ApiSpec])
						}
						object deleteRevision {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): deleteRevision = new deleteRevision(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}:deleteRevision").addQueryStringParameters("name" -> name.toString))
							given Conversion[deleteRevision, Future[Schema.ApiSpec]] = (fun: deleteRevision) => fun.apply()
						}
						/** Used to modify a specified spec. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withApiSpec(body: Schema.ApiSpec) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ApiSpec])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, name: String, updateMask: String, allowMissing: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "allowMissing" -> allowMissing.toString))
						}
						/** Returns matching specs. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListApiSpecsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListApiSpecsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
							given Conversion[list, Future[Schema.ListApiSpecsResponse]] = (fun: list) => fun.apply()
						}
						/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
						class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
						}
						object testIamPermissions {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
						}
						/** Creates a specified spec. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withApiSpec(body: Schema.ApiSpec) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApiSpec])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, parent: String, apiSpecId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs").addQueryStringParameters("parent" -> parent.toString, "apiSpecId" -> apiSpecId.toString))
						}
						/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
						class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
						}
						object setIamPolicy {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						}
						/** Returns a specified spec. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ApiSpec]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ApiSpec])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.ApiSpec]] = (fun: get) => fun.apply()
						}
						/** Lists all revisions of a spec. Revisions are returned in descending order of revision creation time. */
						class listRevisions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListApiSpecRevisionsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListApiSpecRevisionsResponse])
						}
						object listRevisions {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, name: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): listRevisions = new listRevisions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}:listRevisions").addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
							given Conversion[listRevisions, Future[Schema.ListApiSpecRevisionsResponse]] = (fun: listRevisions) => fun.apply()
						}
						object artifacts {
							/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
							class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
							}
							object testIamPermissions {
								def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, artifactsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}/artifacts/${artifactsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
							}
							/** Returns the contents of a specified artifact. If artifacts are stored with GZip compression, the default behavior is to return the artifact uncompressed (the mime_type response field indicates the exact format returned). */
							class getContents(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
							}
							object getContents {
								def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, artifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getContents = new getContents(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}/artifacts/${artifactsId}:getContents").addQueryStringParameters("name" -> name.toString))
								given Conversion[getContents, Future[Schema.HttpBody]] = (fun: getContents) => fun.apply()
							}
							/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
							class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
								def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
							}
							object getIamPolicy {
								def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, artifactsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}/artifacts/${artifactsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
								given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
							}
							/** Removes a specified artifact. */
							class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
							}
							object delete {
								def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, artifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
							}
							/** Used to replace a specified artifact. */
							class replaceArtifact(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def withArtifact(body: Schema.Artifact) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Artifact])
							}
							object replaceArtifact {
								def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, artifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): replaceArtifact = new replaceArtifact(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
							}
							/** Returns a specified artifact. */
							class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Artifact]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Artifact])
							}
							object get {
								def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, artifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.Artifact]] = (fun: get) => fun.apply()
							}
							/** Returns matching artifacts. */
							class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListArtifactsResponse]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListArtifactsResponse])
							}
							object list {
								def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}/artifacts").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
								given Conversion[list, Future[Schema.ListArtifactsResponse]] = (fun: list) => fun.apply()
							}
							/** Creates a specified artifact. */
							class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def withArtifact(body: Schema.Artifact) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Artifact])
							}
							object create {
								def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, parent: String, artifactId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}/artifacts").addQueryStringParameters("parent" -> parent.toString, "artifactId" -> artifactId.toString))
							}
							/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
							class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
							}
							object setIamPolicy {
								def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, artifactsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}/artifacts/${artifactsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
							}
						}
					}
					object artifacts {
						/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
						class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
						}
						object testIamPermissions {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, artifactsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/artifacts/${artifactsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
						}
						/** Returns the contents of a specified artifact. If artifacts are stored with GZip compression, the default behavior is to return the artifact uncompressed (the mime_type response field indicates the exact format returned). */
						class getContents(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
						}
						object getContents {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, artifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getContents = new getContents(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/artifacts/${artifactsId}:getContents").addQueryStringParameters("name" -> name.toString))
							given Conversion[getContents, Future[Schema.HttpBody]] = (fun: getContents) => fun.apply()
						}
						/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
						class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
							def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
						}
						object getIamPolicy {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, artifactsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/artifacts/${artifactsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
							given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
						}
						/** Removes a specified artifact. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, artifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						/** Used to replace a specified artifact. */
						class replaceArtifact(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withArtifact(body: Schema.Artifact) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Artifact])
						}
						object replaceArtifact {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, artifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): replaceArtifact = new replaceArtifact(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
						}
						/** Returns a specified artifact. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Artifact]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Artifact])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, artifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Artifact]] = (fun: get) => fun.apply()
						}
						/** Returns matching artifacts. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListArtifactsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListArtifactsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/artifacts").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
							given Conversion[list, Future[Schema.ListArtifactsResponse]] = (fun: list) => fun.apply()
						}
						/** Creates a specified artifact. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withArtifact(body: Schema.Artifact) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Artifact])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, parent: String, artifactId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/artifacts").addQueryStringParameters("parent" -> parent.toString, "artifactId" -> artifactId.toString))
						}
						/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
						class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
						}
						object setIamPolicy {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, artifactsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/artifacts/${artifactsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						}
					}
				}
				object deployments {
					/** Sets the current revision to a specified prior revision. Note that this creates a new revision with a new revision ID. */
					class rollback(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withRollbackApiDeploymentRequest(body: Schema.RollbackApiDeploymentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApiDeployment])
					}
					object rollback {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): rollback = new rollback(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}:rollback").addQueryStringParameters("name" -> name.toString))
					}
					/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
					class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					/** Removes a specified deployment, all revisions, and all child resources (e.g., artifacts). */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, name: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Adds a tag to a specified revision of a deployment. */
					class tagRevision(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Required. The name of the deployment to be tagged, including the revision ID is optional. If a revision is not specified, it will tag the latest revision. */
						def withName(name: String) = new tagRevision(req.addQueryStringParameters("name" -> name.toString))
						/** Perform the request */
						def withTagApiDeploymentRevisionRequest(body: Schema.TagApiDeploymentRevisionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApiDeployment])
					}
					object tagRevision {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): tagRevision = new tagRevision(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}:tagRevision").addQueryStringParameters())
					}
					/** Deletes a revision of a deployment. */
					class deleteRevision(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ApiDeployment]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.ApiDeployment])
					}
					object deleteRevision {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): deleteRevision = new deleteRevision(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}:deleteRevision").addQueryStringParameters("name" -> name.toString))
						given Conversion[deleteRevision, Future[Schema.ApiDeployment]] = (fun: deleteRevision) => fun.apply()
					}
					/** Used to modify a specified deployment. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withApiDeployment(body: Schema.ApiDeployment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ApiDeployment])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, name: String, updateMask: String, allowMissing: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "allowMissing" -> allowMissing.toString))
					}
					/** Returns matching deployments. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListApiDeploymentsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListApiDeploymentsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
						given Conversion[list, Future[Schema.ListApiDeploymentsResponse]] = (fun: list) => fun.apply()
					}
					/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Creates a specified deployment. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withApiDeployment(body: Schema.ApiDeployment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApiDeployment])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, parent: String, apiDeploymentId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments").addQueryStringParameters("parent" -> parent.toString, "apiDeploymentId" -> apiDeploymentId.toString))
					}
					/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Returns a specified deployment. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ApiDeployment]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ApiDeployment])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.ApiDeployment]] = (fun: get) => fun.apply()
					}
					/** Lists all revisions of a deployment. Revisions are returned in descending order of revision creation time. */
					class listRevisions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListApiDeploymentRevisionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListApiDeploymentRevisionsResponse])
					}
					object listRevisions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, name: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): listRevisions = new listRevisions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}:listRevisions").addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[listRevisions, Future[Schema.ListApiDeploymentRevisionsResponse]] = (fun: listRevisions) => fun.apply()
					}
					object artifacts {
						/** Returns the contents of a specified artifact. If artifacts are stored with GZip compression, the default behavior is to return the artifact uncompressed (the mime_type response field indicates the exact format returned). */
						class getContents(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
						}
						object getContents {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, artifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getContents = new getContents(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}/artifacts/${artifactsId}:getContents").addQueryStringParameters("name" -> name.toString))
							given Conversion[getContents, Future[Schema.HttpBody]] = (fun: getContents) => fun.apply()
						}
						/** Creates a specified artifact. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withArtifact(body: Schema.Artifact) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Artifact])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, parent: String, artifactId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}/artifacts").addQueryStringParameters("parent" -> parent.toString, "artifactId" -> artifactId.toString))
						}
						/** Removes a specified artifact. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, artifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						/** Used to replace a specified artifact. */
						class replaceArtifact(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withArtifact(body: Schema.Artifact) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Artifact])
						}
						object replaceArtifact {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, artifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): replaceArtifact = new replaceArtifact(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
						}
						/** Returns a specified artifact. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Artifact]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Artifact])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, artifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Artifact]] = (fun: get) => fun.apply()
						}
						/** Returns matching artifacts. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListArtifactsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListArtifactsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}/artifacts").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
							given Conversion[list, Future[Schema.ListArtifactsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object artifacts {
					/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, artifactsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/artifacts/${artifactsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Returns the contents of a specified artifact. If artifacts are stored with GZip compression, the default behavior is to return the artifact uncompressed (the mime_type response field indicates the exact format returned). */
					class getContents(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
					}
					object getContents {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, artifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getContents = new getContents(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/artifacts/${artifactsId}:getContents").addQueryStringParameters("name" -> name.toString))
						given Conversion[getContents, Future[Schema.HttpBody]] = (fun: getContents) => fun.apply()
					}
					/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
					class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, artifactsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/artifacts/${artifactsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					/** Removes a specified artifact. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, artifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Used to replace a specified artifact. */
					class replaceArtifact(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withArtifact(body: Schema.Artifact) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Artifact])
					}
					object replaceArtifact {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, artifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): replaceArtifact = new replaceArtifact(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
					}
					/** Returns a specified artifact. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Artifact]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Artifact])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, artifactsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Artifact]] = (fun: get) => fun.apply()
					}
					/** Returns matching artifacts. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListArtifactsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListArtifactsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/artifacts").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
						given Conversion[list, Future[Schema.ListArtifactsResponse]] = (fun: list) => fun.apply()
					}
					/** Creates a specified artifact. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withArtifact(body: Schema.Artifact) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Artifact])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, parent: String, artifactId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/artifacts").addQueryStringParameters("parent" -> parent.toString, "artifactId" -> artifactId.toString))
					}
					/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, artifactsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/artifacts/${artifactsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
				}
			}
			object documents {
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
			}
			object runtime {
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/runtime:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/runtime:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/runtime:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
			}
		}
	}
}
