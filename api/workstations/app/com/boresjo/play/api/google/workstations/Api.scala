package com.boresjo.play.api.google.workstations

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

	private val BASE_URL = "https://workstations.googleapis.com/"

	object projects {
		object locations {
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
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
			object workstationClusters {
				/** Creates a new workstation cluster. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. If set, validate the request and preview the review, but do not actually apply it. */
					def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Perform the request */
					def withWorkstationCluster(body: Schema.WorkstationCluster) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, workstationClusterId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters").addQueryStringParameters("parent" -> parent.toString, "workstationClusterId" -> workstationClusterId.toString))
				}
				/** Deletes the specified workstation cluster. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. If set, validate the request and preview the review, but do not apply it. */
					def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. If set, the request will be rejected if the latest version of the workstation cluster on the server does not have this ETag. */
					def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
					/** Optional. If set, any workstation configurations and workstations in the workstation cluster are also deleted. Otherwise, the request only works if the workstation cluster has no configurations or workstations. */
					def withForce(force: Boolean) = new delete(req.addQueryStringParameters("force" -> force.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Returns the requested workstation cluster. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.WorkstationCluster]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.WorkstationCluster])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.WorkstationCluster]] = (fun: get) => fun.apply()
				}
				/** Updates an existing workstation cluster. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. If set, validate the request and preview the review, but do not actually apply it. */
					def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. If set, and the workstation cluster is not found, a new workstation cluster will be created. In this situation, update_mask is ignored. */
					def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					/** Perform the request */
					def withWorkstationCluster(body: Schema.WorkstationCluster) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Returns all workstation clusters in the specified location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListWorkstationClustersResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Maximum number of items to return.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. next_page_token value returned from a previous List request, if any. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListWorkstationClustersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListWorkstationClustersResponse]] = (fun: list) => fun.apply()
				}
				object workstationConfigs {
					/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
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
						def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					/** Returns the requested workstation configuration. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.WorkstationConfig]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.WorkstationConfig])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.WorkstationConfig]] = (fun: get) => fun.apply()
					}
					/** Updates an existing workstation configuration. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. If set, validate the request and preview the review, but do not actually apply it. */
						def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						/** Optional. If set and the workstation configuration is not found, a new workstation configuration will be created. In this situation, update_mask is ignored. */
						def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
						/** Perform the request */
						def withWorkstationConfig(body: Schema.WorkstationConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Creates a new workstation configuration. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. If set, validate the request and preview the review, but do not actually apply it. */
						def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						/** Perform the request */
						def withWorkstationConfig(body: Schema.WorkstationConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, parent: String, workstationConfigId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs").addQueryStringParameters("parent" -> parent.toString, "workstationConfigId" -> workstationConfigId.toString))
					}
					/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Deletes the specified workstation configuration. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. If set, validate the request and preview the review, but do not actually apply it. */
						def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						/** Optional. If set, the request is rejected if the latest version of the workstation configuration on the server does not have this ETag. */
						def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
						/** Optional. If set, any workstations in the workstation configuration are also deleted. Otherwise, the request works only if the workstation configuration has no workstations. */
						def withForce(force: Boolean) = new delete(req.addQueryStringParameters("force" -> force.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					/** Returns all workstation configurations in the specified cluster. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListWorkstationConfigsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Maximum number of items to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. next_page_token value returned from a previous List request, if any. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListWorkstationConfigsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListWorkstationConfigsResponse]] = (fun: list) => fun.apply()
					}
					/** Returns all workstation configurations in the specified cluster on which the caller has the "workstations.workstation.create" permission. */
					class listUsable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListUsableWorkstationConfigsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Maximum number of items to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new listUsable(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. next_page_token value returned from a previous List request, if any. */
						def withPageToken(pageToken: String) = new listUsable(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListUsableWorkstationConfigsResponse])
					}
					object listUsable {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): listUsable = new listUsable(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs:listUsable").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[listUsable, Future[Schema.ListUsableWorkstationConfigsResponse]] = (fun: listUsable) => fun.apply()
					}
					object workstations {
						/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
						class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
						}
						object testIamPermissions {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, workstationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations/${workstationsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
						}
						/** Stops running a workstation, reducing costs. */
						class stop(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withStopWorkstationRequest(body: Schema.StopWorkstationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
						}
						object stop {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, workstationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations/${workstationsId}:stop").addQueryStringParameters("name" -> name.toString))
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
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, workstationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations/${workstationsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
							given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
						}
						/** Deletes the specified workstation. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. If set, validate the request and preview the review, but do not actually apply it. */
							def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
							/** Optional. If set, the request will be rejected if the latest version of the workstation on the server does not have this ETag. */
							def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, workstationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations/${workstationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
						}
						/** Returns the requested workstation. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Workstation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Workstation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, workstationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations/${workstationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Workstation]] = (fun: get) => fun.apply()
						}
						/** Returns a short-lived credential that can be used to send authenticated and authorized traffic to a workstation. */
						class generateAccessToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGenerateAccessTokenRequest(body: Schema.GenerateAccessTokenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GenerateAccessTokenResponse])
						}
						object generateAccessToken {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, workstationsId :PlayApi, workstation: String)(using signer: RequestSigner, ec: ExecutionContext): generateAccessToken = new generateAccessToken(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations/${workstationsId}:generateAccessToken").addQueryStringParameters("workstation" -> workstation.toString))
						}
						/** Creates a new workstation. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. If set, validate the request and preview the review, but do not actually apply it. */
							def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
							/** Perform the request */
							def withWorkstation(body: Schema.Workstation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, parent: String, workstationId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations").addQueryStringParameters("parent" -> parent.toString, "workstationId" -> workstationId.toString))
						}
						/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
						class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
						}
						object setIamPolicy {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, workstationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations/${workstationsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						}
						/** Updates an existing workstation. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. If set, validate the request and preview the review, but do not actually apply it. */
							def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
							/** Optional. If set and the workstation configuration is not found, a new workstation configuration is created. In this situation, update_mask is ignored. */
							def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
							/** Perform the request */
							def withWorkstation(body: Schema.Workstation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, workstationsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations/${workstationsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						/** Starts running a workstation so that users can connect to it. */
						class start(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withStartWorkstationRequest(body: Schema.StartWorkstationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
						}
						object start {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, workstationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): start = new start(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations/${workstationsId}:start").addQueryStringParameters("name" -> name.toString))
						}
						/** Returns all Workstations using the specified workstation configuration. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListWorkstationsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. Maximum number of items to return.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. next_page_token value returned from a previous List request, if any. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListWorkstationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListWorkstationsResponse]] = (fun: list) => fun.apply()
						}
						/** Returns all workstations using the specified workstation configuration on which the caller has the "workstations.workstations.use" permission. */
						class listUsable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListUsableWorkstationsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. Maximum number of items to return.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new listUsable(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. next_page_token value returned from a previous List request, if any. */
							def withPageToken(pageToken: String) = new listUsable(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListUsableWorkstationsResponse])
						}
						object listUsable {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): listUsable = new listUsable(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations:listUsable").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[listUsable, Future[Schema.ListUsableWorkstationsResponse]] = (fun: listUsable) => fun.apply()
						}
					}
				}
			}
		}
	}
}
