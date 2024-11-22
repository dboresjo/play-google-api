package com.boresjo.play.api.google.gkeonprem

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

	private val BASE_URL = "https://gkeonprem.googleapis.com/"

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
			object vmwareClusters {
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Unenrolls an existing VMware user cluster and its node pools from the Anthos On-Prem API within a given project and location. Unenrollment removes the Cloud reference to the cluster without modifying the underlying OnPrem Resources. Clusters and node pools will continue to run; however, they will no longer be accessible through the Anthos On-Prem API or UI. */
				class unenroll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object unenroll {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, name: String, etag: String, allowMissing: Boolean, validateOnly: Boolean, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): unenroll = new unenroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}:unenroll").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString, "force" -> force.toString))
					given Conversion[unenroll, Future[Schema.Operation]] = (fun: unenroll) => fun.apply()
				}
				/** Queries the VMware user cluster version config. */
				class queryVersionConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.QueryVmwareVersionConfigResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.QueryVmwareVersionConfigResponse])
				}
				object queryVersionConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, createConfigAdminClusterMembership: String, createConfigAdminClusterName: String, upgradeConfigClusterName: String)(using signer: RequestSigner, ec: ExecutionContext): queryVersionConfig = new queryVersionConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters:queryVersionConfig").addQueryStringParameters("parent" -> parent.toString, "createConfig.adminClusterMembership" -> createConfigAdminClusterMembership.toString, "createConfig.adminClusterName" -> createConfigAdminClusterName.toString, "upgradeConfig.clusterName" -> upgradeConfigClusterName.toString))
					given Conversion[queryVersionConfig, Future[Schema.QueryVmwareVersionConfigResponse]] = (fun: queryVersionConfig) => fun.apply()
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
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Enrolls an existing VMware user cluster and its node pools to the Anthos On-Prem API within a given project and location. Through enrollment, an existing cluster will become Anthos On-Prem API managed. The corresponding GCP resources will be created and all future modifications to the cluster and/or its node pools will be expected to be performed through the API. */
				class enroll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withEnrollVmwareClusterRequest(body: Schema.EnrollVmwareClusterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object enroll {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): enroll = new enroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters:enroll").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a single VMware Cluster. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, name: String, etag: String, allowMissing: Boolean, validateOnly: Boolean, force: Boolean, ignoreErrors: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString, "force" -> force.toString, "ignoreErrors" -> ignoreErrors.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Gets details of a single VMware Cluster. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.VmwareCluster]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. If true, return Vmware Cluster including the one that only exists in RMS. */
					def withAllowMissing(allowMissing: Boolean) = new get(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.VmwareCluster])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, name: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
					given Conversion[get, Future[Schema.VmwareCluster]] = (fun: get) => fun.apply()
				}
				/** Updates the parameters of a single VMware cluster. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withVmwareCluster(body: Schema.VmwareCluster) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, name: String, updateMask: String, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "validateOnly" -> validateOnly.toString))
				}
				/** Lists VMware Clusters in a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListVmwareClustersResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. If true, return list of Vmware Clusters including the ones that only exists in RMS. */
					def withAllowMissing(allowMissing: Boolean) = new list(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListVmwareClustersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "view" -> view.toString))
					given Conversion[list, Future[Schema.ListVmwareClustersResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a new VMware user cluster in a given project and location. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. If set to true, CLM will force CCFE to persist the cluster resource in RMS when the creation fails during standalone preflight checks. In that case the subsequent create call will fail with "cluster already exists" error and hence a update cluster is required to fix the cluster. */
					def withAllowPreflightFailure(allowPreflightFailure: Boolean) = new create(req.addQueryStringParameters("allowPreflightFailure" -> allowPreflightFailure.toString))
					/** Perform the request */
					def withVmwareCluster(body: Schema.VmwareCluster) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, vmwareClusterId: String, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters").addQueryStringParameters("parent" -> parent.toString, "vmwareClusterId" -> vmwareClusterId.toString, "validateOnly" -> validateOnly.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object operations {
					/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
				}
				object vmwareNodePools {
					/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, vmwareNodePoolsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools/${vmwareNodePoolsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Unenrolls a VMware node pool to Anthos On-Prem API */
					class unenroll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object unenroll {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, vmwareNodePoolsId :PlayApi, name: String, etag: String, allowMissing: Boolean, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): unenroll = new unenroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools/${vmwareNodePoolsId}:unenroll").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString))
						given Conversion[unenroll, Future[Schema.Operation]] = (fun: unenroll) => fun.apply()
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
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, vmwareNodePoolsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools/${vmwareNodePoolsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					/** Enrolls a VMware node pool to Anthos On-Prem API */
					class enroll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withEnrollVmwareNodePoolRequest(body: Schema.EnrollVmwareNodePoolRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object enroll {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): enroll = new enroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools:enroll").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a single VMware node pool. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, vmwareNodePoolsId :PlayApi, name: String, etag: String, allowMissing: Boolean, validateOnly: Boolean, ignoreErrors: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools/${vmwareNodePoolsId}").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString, "ignoreErrors" -> ignoreErrors.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					/** Gets details of a single VMware node pool. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.VmwareNodePool]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.VmwareNodePool])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, vmwareNodePoolsId :PlayApi, name: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools/${vmwareNodePoolsId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
						given Conversion[get, Future[Schema.VmwareNodePool]] = (fun: get) => fun.apply()
					}
					/** Updates the parameters of a single VMware node pool. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withVmwareNodePool(body: Schema.VmwareNodePool) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, vmwareNodePoolsId :PlayApi, name: String, updateMask: String, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools/${vmwareNodePoolsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "validateOnly" -> validateOnly.toString))
					}
					/** Lists VMware node pools in a given project, location and VMWare cluster. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListVmwareNodePoolsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListVmwareNodePoolsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, parent: String, pageSize: Int, pageToken: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
						given Conversion[list, Future[Schema.ListVmwareNodePoolsResponse]] = (fun: list) => fun.apply()
					}
					/** Creates a new VMware node pool in a given project, location and VMWare cluster. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withVmwareNodePool(body: Schema.VmwareNodePool) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, parent: String, vmwareNodePoolId: String, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools").addQueryStringParameters("parent" -> parent.toString, "vmwareNodePoolId" -> vmwareNodePoolId.toString, "validateOnly" -> validateOnly.toString))
					}
					/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, vmwareNodePoolsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools/${vmwareNodePoolsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					object operations {
						/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, vmwareNodePoolsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools/${vmwareNodePoolsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
						}
						/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareClustersId :PlayApi, vmwareNodePoolsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareClusters/${vmwareClustersId}/vmwareNodePools/${vmwareNodePoolsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
						}
					}
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
			object vmwareAdminClusters {
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareAdminClustersId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters/${vmwareAdminClustersId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Unenrolls an existing VMware admin cluster from the Anthos On-Prem API within a given project and location. Unenrollment removes the Cloud reference to the cluster without modifying the underlying OnPrem Resources. Clusters will continue to run; however, they will no longer be accessible through the Anthos On-Prem API or its clients. */
				class unenroll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object unenroll {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareAdminClustersId :PlayApi, name: String, etag: String, allowMissing: Boolean, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): unenroll = new unenroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters/${vmwareAdminClustersId}:unenroll").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString))
					given Conversion[unenroll, Future[Schema.Operation]] = (fun: unenroll) => fun.apply()
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
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareAdminClustersId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters/${vmwareAdminClustersId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Enrolls an existing VMware admin cluster to the Anthos On-Prem API within a given project and location. Through enrollment, an existing admin cluster will become Anthos On-Prem API managed. The corresponding GCP resources will be created and all future modifications to the cluster will be expected to be performed through the API. */
				class enroll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withEnrollVmwareAdminClusterRequest(body: Schema.EnrollVmwareAdminClusterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object enroll {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): enroll = new enroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters:enroll").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Gets details of a single VMware admin cluster. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.VmwareAdminCluster]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. If true, return Vmware Admin Cluster including the one that only exists in RMS. */
					def withAllowMissing(allowMissing: Boolean) = new get(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.VmwareAdminCluster])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareAdminClustersId :PlayApi, name: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters/${vmwareAdminClustersId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
					given Conversion[get, Future[Schema.VmwareAdminCluster]] = (fun: get) => fun.apply()
				}
				/** Updates the parameters of a single VMware admin cluster. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withVmwareAdminCluster(body: Schema.VmwareAdminCluster) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareAdminClustersId :PlayApi, name: String, updateMask: String, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters/${vmwareAdminClustersId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "validateOnly" -> validateOnly.toString))
				}
				/** Lists VMware admin clusters in a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListVmwareAdminClustersResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. If true, return list of Vmware Admin Clusters including the ones that only exists in RMS. */
					def withAllowMissing(allowMissing: Boolean) = new list(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListVmwareAdminClustersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
					given Conversion[list, Future[Schema.ListVmwareAdminClustersResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a new VMware admin cluster in a given project and location. The API needs to be combined with creating a bootstrap cluster to work. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. If set to true, CLM will force CCFE to persist the cluster resource in RMS when the creation fails during standalone preflight checks. In that case the subsequent create call will fail with "cluster already exists" error and hence a update cluster is required to fix the cluster. */
					def withAllowPreflightFailure(allowPreflightFailure: Boolean) = new create(req.addQueryStringParameters("allowPreflightFailure" -> allowPreflightFailure.toString))
					/** Perform the request */
					def withVmwareAdminCluster(body: Schema.VmwareAdminCluster) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, vmwareAdminClusterId: String, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters").addQueryStringParameters("parent" -> parent.toString, "vmwareAdminClusterId" -> vmwareAdminClusterId.toString, "validateOnly" -> validateOnly.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareAdminClustersId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters/${vmwareAdminClustersId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object operations {
					/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareAdminClustersId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters/${vmwareAdminClustersId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, vmwareAdminClustersId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/vmwareAdminClusters/${vmwareAdminClustersId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
				}
			}
			object bareMetalAdminClusters {
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalAdminClustersId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters/${bareMetalAdminClustersId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Unenrolls an existing bare metal admin cluster from the Anthos On-Prem API within a given project and location. Unenrollment removes the Cloud reference to the cluster without modifying the underlying OnPrem Resources. Clusters will continue to run; however, they will no longer be accessible through the Anthos On-Prem API or its clients. */
				class unenroll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object unenroll {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalAdminClustersId :PlayApi, name: String, etag: String, allowMissing: Boolean, validateOnly: Boolean, ignoreErrors: Boolean)(using signer: RequestSigner, ec: ExecutionContext): unenroll = new unenroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters/${bareMetalAdminClustersId}:unenroll").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString, "ignoreErrors" -> ignoreErrors.toString))
					given Conversion[unenroll, Future[Schema.Operation]] = (fun: unenroll) => fun.apply()
				}
				/** Queries the bare metal admin cluster version config. */
				class queryVersionConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.QueryBareMetalAdminVersionConfigResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.QueryBareMetalAdminVersionConfigResponse])
				}
				object queryVersionConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, upgradeConfigClusterName: String)(using signer: RequestSigner, ec: ExecutionContext): queryVersionConfig = new queryVersionConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters:queryVersionConfig").addQueryStringParameters("parent" -> parent.toString, "upgradeConfig.clusterName" -> upgradeConfigClusterName.toString))
					given Conversion[queryVersionConfig, Future[Schema.QueryBareMetalAdminVersionConfigResponse]] = (fun: queryVersionConfig) => fun.apply()
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
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalAdminClustersId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters/${bareMetalAdminClustersId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Enrolls an existing bare metal admin cluster to the Anthos On-Prem API within a given project and location. Through enrollment, an existing admin cluster will become Anthos On-Prem API managed. The corresponding GCP resources will be created and all future modifications to the cluster will be expected to be performed through the API. */
				class enroll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withEnrollBareMetalAdminClusterRequest(body: Schema.EnrollBareMetalAdminClusterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object enroll {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): enroll = new enroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters:enroll").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Gets details of a single bare metal admin cluster. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BareMetalAdminCluster]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. If true, return BareMetal Admin Cluster including the one that only exists in RMS. */
					def withAllowMissing(allowMissing: Boolean) = new get(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BareMetalAdminCluster])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalAdminClustersId :PlayApi, name: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters/${bareMetalAdminClustersId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
					given Conversion[get, Future[Schema.BareMetalAdminCluster]] = (fun: get) => fun.apply()
				}
				/** Updates the parameters of a single bare metal admin cluster. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withBareMetalAdminCluster(body: Schema.BareMetalAdminCluster) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalAdminClustersId :PlayApi, name: String, updateMask: String, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters/${bareMetalAdminClustersId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "validateOnly" -> validateOnly.toString))
				}
				/** Lists bare metal admin clusters in a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBareMetalAdminClustersResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. If true, return list of BareMetal Admin Clusters including the ones that only exists in RMS. */
					def withAllowMissing(allowMissing: Boolean) = new list(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBareMetalAdminClustersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
					given Conversion[list, Future[Schema.ListBareMetalAdminClustersResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a new bare metal admin cluster in a given project and location. The API needs to be combined with creating a bootstrap cluster to work. See: https://cloud.google.com/anthos/clusters/docs/bare-metal/latest/installing/creating-clusters/create-admin-cluster-api#prepare_bootstrap_environment */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. If set to true, CLM will force CCFE to persist the cluster resource in RMS when the creation fails during standalone preflight checks. In that case the subsequent create call will fail with "cluster already exists" error and hence a update cluster is required to fix the cluster. */
					def withAllowPreflightFailure(allowPreflightFailure: Boolean) = new create(req.addQueryStringParameters("allowPreflightFailure" -> allowPreflightFailure.toString))
					/** Perform the request */
					def withBareMetalAdminCluster(body: Schema.BareMetalAdminCluster) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, bareMetalAdminClusterId: String, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters").addQueryStringParameters("parent" -> parent.toString, "bareMetalAdminClusterId" -> bareMetalAdminClusterId.toString, "validateOnly" -> validateOnly.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalAdminClustersId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters/${bareMetalAdminClustersId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object operations {
					/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalAdminClustersId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters/${bareMetalAdminClustersId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalAdminClustersId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalAdminClusters/${bareMetalAdminClustersId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
				}
			}
			object bareMetalClusters {
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Unenrolls an existing bare metal user cluster and its node pools from the Anthos On-Prem API within a given project and location. Unenrollment removes the Cloud reference to the cluster without modifying the underlying OnPrem Resources. Clusters and node pools will continue to run; however, they will no longer be accessible through the Anthos On-Prem API or its clients. */
				class unenroll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object unenroll {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, name: String, etag: String, allowMissing: Boolean, validateOnly: Boolean, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): unenroll = new unenroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}:unenroll").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString, "force" -> force.toString))
					given Conversion[unenroll, Future[Schema.Operation]] = (fun: unenroll) => fun.apply()
				}
				/** Queries the bare metal user cluster version config. */
				class queryVersionConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.QueryBareMetalVersionConfigResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.QueryBareMetalVersionConfigResponse])
				}
				object queryVersionConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, createConfigAdminClusterMembership: String, createConfigAdminClusterName: String, upgradeConfigClusterName: String)(using signer: RequestSigner, ec: ExecutionContext): queryVersionConfig = new queryVersionConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters:queryVersionConfig").addQueryStringParameters("parent" -> parent.toString, "createConfig.adminClusterMembership" -> createConfigAdminClusterMembership.toString, "createConfig.adminClusterName" -> createConfigAdminClusterName.toString, "upgradeConfig.clusterName" -> upgradeConfigClusterName.toString))
					given Conversion[queryVersionConfig, Future[Schema.QueryBareMetalVersionConfigResponse]] = (fun: queryVersionConfig) => fun.apply()
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
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Enrolls an existing bare metal user cluster and its node pools to the Anthos On-Prem API within a given project and location. Through enrollment, an existing cluster will become Anthos On-Prem API managed. The corresponding GCP resources will be created and all future modifications to the cluster and/or its node pools will be expected to be performed through the API. */
				class enroll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withEnrollBareMetalClusterRequest(body: Schema.EnrollBareMetalClusterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object enroll {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): enroll = new enroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters:enroll").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a single bare metal Cluster. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, name: String, etag: String, allowMissing: Boolean, validateOnly: Boolean, force: Boolean, ignoreErrors: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString, "force" -> force.toString, "ignoreErrors" -> ignoreErrors.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Gets details of a single bare metal Cluster. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BareMetalCluster]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. If true, return BareMetal Cluster including the one that only exists in RMS. */
					def withAllowMissing(allowMissing: Boolean) = new get(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BareMetalCluster])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, name: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
					given Conversion[get, Future[Schema.BareMetalCluster]] = (fun: get) => fun.apply()
				}
				/** Updates the parameters of a single bare metal Cluster. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withBareMetalCluster(body: Schema.BareMetalCluster) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, name: String, updateMask: String, allowMissing: Boolean, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString))
				}
				/** Lists bare metal clusters in a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBareMetalClustersResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. If true, return list of BareMetal Clusters including the ones that only exists in RMS. */
					def withAllowMissing(allowMissing: Boolean) = new list(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBareMetalClustersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "view" -> view.toString))
					given Conversion[list, Future[Schema.ListBareMetalClustersResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a new bare metal cluster in a given project and location. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. If set to true, CLM will force CCFE to persist the cluster resource in RMS when the creation fails during standalone preflight checks. In that case the subsequent create call will fail with "cluster already exists" error and hence a update cluster is required to fix the cluster. */
					def withAllowPreflightFailure(allowPreflightFailure: Boolean) = new create(req.addQueryStringParameters("allowPreflightFailure" -> allowPreflightFailure.toString))
					/** Perform the request */
					def withBareMetalCluster(body: Schema.BareMetalCluster) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, bareMetalClusterId: String, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters").addQueryStringParameters("parent" -> parent.toString, "bareMetalClusterId" -> bareMetalClusterId.toString, "validateOnly" -> validateOnly.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object operations {
					/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
					}
				}
				object bareMetalNodePools {
					/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, bareMetalNodePoolsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools/${bareMetalNodePoolsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Unenrolls a bare metal node pool from Anthos On-Prem API. */
					class unenroll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object unenroll {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, bareMetalNodePoolsId :PlayApi, name: String, etag: String, allowMissing: Boolean, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): unenroll = new unenroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools/${bareMetalNodePoolsId}:unenroll").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString))
						given Conversion[unenroll, Future[Schema.Operation]] = (fun: unenroll) => fun.apply()
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
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, bareMetalNodePoolsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools/${bareMetalNodePoolsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					/** Enrolls an existing bare metal node pool to the Anthos On-Prem API within a given project and location. Through enrollment, an existing node pool will become Anthos On-Prem API managed. The corresponding GCP resources will be created. */
					class enroll(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withEnrollBareMetalNodePoolRequest(body: Schema.EnrollBareMetalNodePoolRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object enroll {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): enroll = new enroll(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools:enroll").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a single bare metal node pool. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, bareMetalNodePoolsId :PlayApi, name: String, etag: String, allowMissing: Boolean, validateOnly: Boolean, ignoreErrors: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools/${bareMetalNodePoolsId}").addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString, "ignoreErrors" -> ignoreErrors.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					/** Gets details of a single bare metal node pool. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BareMetalNodePool]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BareMetalNodePool])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, bareMetalNodePoolsId :PlayApi, name: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools/${bareMetalNodePoolsId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
						given Conversion[get, Future[Schema.BareMetalNodePool]] = (fun: get) => fun.apply()
					}
					/** Updates the parameters of a single bare metal node pool. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withBareMetalNodePool(body: Schema.BareMetalNodePool) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, bareMetalNodePoolsId :PlayApi, name: String, updateMask: String, allowMissing: Boolean, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools/${bareMetalNodePoolsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "allowMissing" -> allowMissing.toString, "validateOnly" -> validateOnly.toString))
					}
					/** Lists bare metal node pools in a given project, location and bare metal cluster. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBareMetalNodePoolsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBareMetalNodePoolsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, parent: String, pageSize: Int, pageToken: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
						given Conversion[list, Future[Schema.ListBareMetalNodePoolsResponse]] = (fun: list) => fun.apply()
					}
					/** Creates a new bare metal node pool in a given project, location and Bare Metal cluster. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withBareMetalNodePool(body: Schema.BareMetalNodePool) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, parent: String, bareMetalNodePoolId: String, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools").addQueryStringParameters("parent" -> parent.toString, "bareMetalNodePoolId" -> bareMetalNodePoolId.toString, "validateOnly" -> validateOnly.toString))
					}
					/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, bareMetalNodePoolsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools/${bareMetalNodePoolsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					object operations {
						/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, bareMetalNodePoolsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools/${bareMetalNodePoolsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
						}
						/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, bareMetalClustersId :PlayApi, bareMetalNodePoolsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/bareMetalClusters/${bareMetalClustersId}/bareMetalNodePools/${bareMetalNodePoolsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
						}
					}
				}
			}
		}
	}
}
