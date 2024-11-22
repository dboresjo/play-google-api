package com.boresjo.play.api.google.gkeonprem

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://gkeonprem.googleapis.com/"

	object projects {
		object locations {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object vmwareClusters {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class unenroll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object unenroll {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, name: String, etag: String, allowMissing: Boolean, validateOnly: Boolean, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): unenroll = new unenroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}:unenroll").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString, "force" -> force.toString))
					given Conversion[unenroll, Future[Schema.Operation]] = (fun: unenroll) => fun.apply()
				}
				class queryVersionConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.QueryVmwareVersionConfigResponse]) {
					def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.QueryVmwareVersionConfigResponse])
				}
				object queryVersionConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, createConfigAdminClusterMembership: String, createConfigAdminClusterName: String, upgradeConfigClusterName: String)(using auth: AuthToken, ec: ExecutionContext): queryVersionConfig = new queryVersionConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters:queryVersionConfig").addQueryStringParameters("parent" -> parent.toString, "createConfig.adminClusterMembership" -> createConfigAdminClusterMembership.toString, "createConfig.adminClusterName" -> createConfigAdminClusterName.toString, "upgradeConfig.clusterName" -> upgradeConfigClusterName.toString))
					given Conversion[queryVersionConfig, Future[Schema.QueryVmwareVersionConfigResponse]] = (fun: queryVersionConfig) => fun.apply()
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class enroll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEnrollVmwareClusterRequest(body: Schema.EnrollVmwareClusterRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object enroll {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): enroll = new enroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters:enroll").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, name: String, etag: String, allowMissing: Boolean, validateOnly: Boolean, force: Boolean, ignoreErrors: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString, "force" -> force.toString, "ignoreErrors" -> ignoreErrors.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.VmwareCluster]) {
					/** Optional. If true, return Vmware Cluster including the one that only exists in RMS. */
					def withAllowMissing(allowMissing: Boolean) = new get(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.VmwareCluster])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, name: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
					given Conversion[get, Future[Schema.VmwareCluster]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withVmwareCluster(body: Schema.VmwareCluster) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, name: String, updateMask: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "validateOnly" -> validateOnly.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListVmwareClustersResponse]) {
					/** Optional. If true, return list of Vmware Clusters including the ones that only exists in RMS. */
					def withAllowMissing(allowMissing: Boolean) = new list(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListVmwareClustersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "view" -> view.toString))
					given Conversion[list, Future[Schema.ListVmwareClustersResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. If set to true, CLM will force CCFE to persist the cluster resource in RMS when the creation fails during standalone preflight checks. In that case the subsequent create call will fail with "cluster already exists" error and hence a update cluster is required to fix the cluster. */
					def withAllowPreflightFailure(allowPreflightFailure: Boolean) = new create(req.addQueryStringParameters("allowPreflightFailure" -> allowPreflightFailure.toString))
					def withVmwareCluster(body: Schema.VmwareCluster) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, vmwareClusterId: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters").addQueryStringParameters("parent" -> parent.toString, "vmwareClusterId" -> vmwareClusterId.toString, "validateOnly" -> validateOnly.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object operations {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
				}
				object vmwareNodePools {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, vmwareNodePoolsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools/${vmwareNodePoolsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					class unenroll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object unenroll {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, vmwareNodePoolsId :PlayApi, name: String, etag: String, allowMissing: Boolean, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): unenroll = new unenroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools/${vmwareNodePoolsId}:unenroll").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString))
						given Conversion[unenroll, Future[Schema.Operation]] = (fun: unenroll) => fun.apply()
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, vmwareNodePoolsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools/${vmwareNodePoolsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					class enroll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withEnrollVmwareNodePoolRequest(body: Schema.EnrollVmwareNodePoolRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object enroll {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): enroll = new enroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools:enroll").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, vmwareNodePoolsId :PlayApi, name: String, etag: String, allowMissing: Boolean, validateOnly: Boolean, ignoreErrors: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools/${vmwareNodePoolsId}").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString, "ignoreErrors" -> ignoreErrors.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.VmwareNodePool]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.VmwareNodePool])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, vmwareNodePoolsId :PlayApi, name: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools/${vmwareNodePoolsId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
						given Conversion[get, Future[Schema.VmwareNodePool]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withVmwareNodePool(body: Schema.VmwareNodePool) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, vmwareNodePoolsId :PlayApi, name: String, updateMask: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools/${vmwareNodePoolsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "validateOnly" -> validateOnly.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListVmwareNodePoolsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListVmwareNodePoolsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, parent: String, pageSize: Int, pageToken: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
						given Conversion[list, Future[Schema.ListVmwareNodePoolsResponse]] = (fun: list) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withVmwareNodePool(body: Schema.VmwareNodePool) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, parent: String, vmwareNodePoolId: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools").addQueryStringParameters("parent" -> parent.toString, "vmwareNodePoolId" -> vmwareNodePoolId.toString, "validateOnly" -> validateOnly.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, vmwareNodePoolsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools/${vmwareNodePoolsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					object operations {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, vmwareNodePoolsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools/${vmwareNodePoolsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, vmwareNodePoolsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools/${vmwareNodePoolsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
						}
					}
				}
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
			object vmwareAdminClusters {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareAdminClustersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters/${vmwareAdminClustersId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class unenroll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object unenroll {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareAdminClustersId :PlayApi, name: String, etag: String, allowMissing: Boolean, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): unenroll = new unenroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters/${vmwareAdminClustersId}:unenroll").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString))
					given Conversion[unenroll, Future[Schema.Operation]] = (fun: unenroll) => fun.apply()
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareAdminClustersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters/${vmwareAdminClustersId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class enroll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEnrollVmwareAdminClusterRequest(body: Schema.EnrollVmwareAdminClusterRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object enroll {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): enroll = new enroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters:enroll").addQueryStringParameters("parent" -> parent.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.VmwareAdminCluster]) {
					/** Optional. If true, return Vmware Admin Cluster including the one that only exists in RMS. */
					def withAllowMissing(allowMissing: Boolean) = new get(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.VmwareAdminCluster])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareAdminClustersId :PlayApi, name: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters/${vmwareAdminClustersId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
					given Conversion[get, Future[Schema.VmwareAdminCluster]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withVmwareAdminCluster(body: Schema.VmwareAdminCluster) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareAdminClustersId :PlayApi, name: String, updateMask: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters/${vmwareAdminClustersId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "validateOnly" -> validateOnly.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListVmwareAdminClustersResponse]) {
					/** Optional. If true, return list of Vmware Admin Clusters including the ones that only exists in RMS. */
					def withAllowMissing(allowMissing: Boolean) = new list(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListVmwareAdminClustersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
					given Conversion[list, Future[Schema.ListVmwareAdminClustersResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. If set to true, CLM will force CCFE to persist the cluster resource in RMS when the creation fails during standalone preflight checks. In that case the subsequent create call will fail with "cluster already exists" error and hence a update cluster is required to fix the cluster. */
					def withAllowPreflightFailure(allowPreflightFailure: Boolean) = new create(req.addQueryStringParameters("allowPreflightFailure" -> allowPreflightFailure.toString))
					def withVmwareAdminCluster(body: Schema.VmwareAdminCluster) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, vmwareAdminClusterId: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters").addQueryStringParameters("parent" -> parent.toString, "vmwareAdminClusterId" -> vmwareAdminClusterId.toString, "validateOnly" -> validateOnly.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareAdminClustersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters/${vmwareAdminClustersId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object operations {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareAdminClustersId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters/${vmwareAdminClustersId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareAdminClustersId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters/${vmwareAdminClustersId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
				}
			}
			object bareMetalAdminClusters {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalAdminClustersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters/${bareMetalAdminClustersId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class unenroll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object unenroll {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalAdminClustersId :PlayApi, name: String, etag: String, allowMissing: Boolean, validateOnly: Boolean, ignoreErrors: Boolean)(using auth: AuthToken, ec: ExecutionContext): unenroll = new unenroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters/${bareMetalAdminClustersId}:unenroll").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString, "ignoreErrors" -> ignoreErrors.toString))
					given Conversion[unenroll, Future[Schema.Operation]] = (fun: unenroll) => fun.apply()
				}
				class queryVersionConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.QueryBareMetalAdminVersionConfigResponse]) {
					def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.QueryBareMetalAdminVersionConfigResponse])
				}
				object queryVersionConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, upgradeConfigClusterName: String)(using auth: AuthToken, ec: ExecutionContext): queryVersionConfig = new queryVersionConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters:queryVersionConfig").addQueryStringParameters("parent" -> parent.toString, "upgradeConfig.clusterName" -> upgradeConfigClusterName.toString))
					given Conversion[queryVersionConfig, Future[Schema.QueryBareMetalAdminVersionConfigResponse]] = (fun: queryVersionConfig) => fun.apply()
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalAdminClustersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters/${bareMetalAdminClustersId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class enroll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEnrollBareMetalAdminClusterRequest(body: Schema.EnrollBareMetalAdminClusterRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object enroll {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): enroll = new enroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters:enroll").addQueryStringParameters("parent" -> parent.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BareMetalAdminCluster]) {
					/** Optional. If true, return BareMetal Admin Cluster including the one that only exists in RMS. */
					def withAllowMissing(allowMissing: Boolean) = new get(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.BareMetalAdminCluster])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalAdminClustersId :PlayApi, name: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters/${bareMetalAdminClustersId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
					given Conversion[get, Future[Schema.BareMetalAdminCluster]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withBareMetalAdminCluster(body: Schema.BareMetalAdminCluster) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalAdminClustersId :PlayApi, name: String, updateMask: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters/${bareMetalAdminClustersId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "validateOnly" -> validateOnly.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBareMetalAdminClustersResponse]) {
					/** Optional. If true, return list of BareMetal Admin Clusters including the ones that only exists in RMS. */
					def withAllowMissing(allowMissing: Boolean) = new list(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListBareMetalAdminClustersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
					given Conversion[list, Future[Schema.ListBareMetalAdminClustersResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. If set to true, CLM will force CCFE to persist the cluster resource in RMS when the creation fails during standalone preflight checks. In that case the subsequent create call will fail with "cluster already exists" error and hence a update cluster is required to fix the cluster. */
					def withAllowPreflightFailure(allowPreflightFailure: Boolean) = new create(req.addQueryStringParameters("allowPreflightFailure" -> allowPreflightFailure.toString))
					def withBareMetalAdminCluster(body: Schema.BareMetalAdminCluster) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, bareMetalAdminClusterId: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters").addQueryStringParameters("parent" -> parent.toString, "bareMetalAdminClusterId" -> bareMetalAdminClusterId.toString, "validateOnly" -> validateOnly.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalAdminClustersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters/${bareMetalAdminClustersId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object operations {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalAdminClustersId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters/${bareMetalAdminClustersId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalAdminClustersId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters/${bareMetalAdminClustersId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
				}
			}
			object bareMetalClusters {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class unenroll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object unenroll {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, name: String, etag: String, allowMissing: Boolean, validateOnly: Boolean, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): unenroll = new unenroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}:unenroll").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString, "force" -> force.toString))
					given Conversion[unenroll, Future[Schema.Operation]] = (fun: unenroll) => fun.apply()
				}
				class queryVersionConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.QueryBareMetalVersionConfigResponse]) {
					def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.QueryBareMetalVersionConfigResponse])
				}
				object queryVersionConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, createConfigAdminClusterMembership: String, createConfigAdminClusterName: String, upgradeConfigClusterName: String)(using auth: AuthToken, ec: ExecutionContext): queryVersionConfig = new queryVersionConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters:queryVersionConfig").addQueryStringParameters("parent" -> parent.toString, "createConfig.adminClusterMembership" -> createConfigAdminClusterMembership.toString, "createConfig.adminClusterName" -> createConfigAdminClusterName.toString, "upgradeConfig.clusterName" -> upgradeConfigClusterName.toString))
					given Conversion[queryVersionConfig, Future[Schema.QueryBareMetalVersionConfigResponse]] = (fun: queryVersionConfig) => fun.apply()
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class enroll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEnrollBareMetalClusterRequest(body: Schema.EnrollBareMetalClusterRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object enroll {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): enroll = new enroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters:enroll").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, name: String, etag: String, allowMissing: Boolean, validateOnly: Boolean, force: Boolean, ignoreErrors: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString, "force" -> force.toString, "ignoreErrors" -> ignoreErrors.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BareMetalCluster]) {
					/** Optional. If true, return BareMetal Cluster including the one that only exists in RMS. */
					def withAllowMissing(allowMissing: Boolean) = new get(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.BareMetalCluster])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, name: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
					given Conversion[get, Future[Schema.BareMetalCluster]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withBareMetalCluster(body: Schema.BareMetalCluster) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, name: String, updateMask: String, allowMissing: Boolean, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBareMetalClustersResponse]) {
					/** Optional. If true, return list of BareMetal Clusters including the ones that only exists in RMS. */
					def withAllowMissing(allowMissing: Boolean) = new list(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListBareMetalClustersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "view" -> view.toString))
					given Conversion[list, Future[Schema.ListBareMetalClustersResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. If set to true, CLM will force CCFE to persist the cluster resource in RMS when the creation fails during standalone preflight checks. In that case the subsequent create call will fail with "cluster already exists" error and hence a update cluster is required to fix the cluster. */
					def withAllowPreflightFailure(allowPreflightFailure: Boolean) = new create(req.addQueryStringParameters("allowPreflightFailure" -> allowPreflightFailure.toString))
					def withBareMetalCluster(body: Schema.BareMetalCluster) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, bareMetalClusterId: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters").addQueryStringParameters("parent" -> parent.toString, "bareMetalClusterId" -> bareMetalClusterId.toString, "validateOnly" -> validateOnly.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object operations {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
				}
				object bareMetalNodePools {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, bareMetalNodePoolsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools/${bareMetalNodePoolsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					class unenroll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object unenroll {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, bareMetalNodePoolsId :PlayApi, name: String, etag: String, allowMissing: Boolean, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): unenroll = new unenroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools/${bareMetalNodePoolsId}:unenroll").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString))
						given Conversion[unenroll, Future[Schema.Operation]] = (fun: unenroll) => fun.apply()
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, bareMetalNodePoolsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools/${bareMetalNodePoolsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					class enroll(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withEnrollBareMetalNodePoolRequest(body: Schema.EnrollBareMetalNodePoolRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object enroll {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): enroll = new enroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools:enroll").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, bareMetalNodePoolsId :PlayApi, name: String, etag: String, allowMissing: Boolean, validateOnly: Boolean, ignoreErrors: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools/${bareMetalNodePoolsId}").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString, "ignoreErrors" -> ignoreErrors.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BareMetalNodePool]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.BareMetalNodePool])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, bareMetalNodePoolsId :PlayApi, name: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools/${bareMetalNodePoolsId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
						given Conversion[get, Future[Schema.BareMetalNodePool]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withBareMetalNodePool(body: Schema.BareMetalNodePool) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, bareMetalNodePoolsId :PlayApi, name: String, updateMask: String, allowMissing: Boolean, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools/${bareMetalNodePoolsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBareMetalNodePoolsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListBareMetalNodePoolsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, parent: String, pageSize: Int, pageToken: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
						given Conversion[list, Future[Schema.ListBareMetalNodePoolsResponse]] = (fun: list) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withBareMetalNodePool(body: Schema.BareMetalNodePool) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, parent: String, bareMetalNodePoolId: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools").addQueryStringParameters("parent" -> parent.toString, "bareMetalNodePoolId" -> bareMetalNodePoolId.toString, "validateOnly" -> validateOnly.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, bareMetalNodePoolsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools/${bareMetalNodePoolsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					object operations {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, bareMetalNodePoolsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools/${bareMetalNodePoolsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, bareMetalNodePoolsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools/${bareMetalNodePoolsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
						}
					}
				}
			}
		}
	}
}
