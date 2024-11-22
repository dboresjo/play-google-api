package com.boresjo.play.api.google.workstations

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://workstations.googleapis.com/"

	object projects {
		object locations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
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
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
			object workstationClusters {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. If set, validate the request and preview the review, but do not actually apply it. */
					def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					def withWorkstationCluster(body: Schema.WorkstationCluster) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, workstationClusterId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters").addQueryStringParameters("parent" -> parent.toString, "workstationClusterId" -> workstationClusterId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					/** Optional. If set, validate the request and preview the review, but do not apply it. */
					def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. If set, the request will be rejected if the latest version of the workstation cluster on the server does not have this ETag. */
					def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
					/** Optional. If set, any workstation configurations and workstations in the workstation cluster are also deleted. Otherwise, the request only works if the workstation cluster has no configurations or workstations. */
					def withForce(force: Boolean) = new delete(req.addQueryStringParameters("force" -> force.toString))
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.WorkstationCluster]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.WorkstationCluster])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.WorkstationCluster]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. If set, validate the request and preview the review, but do not actually apply it. */
					def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. If set, and the workstation cluster is not found, a new workstation cluster will be created. In this situation, update_mask is ignored. */
					def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					def withWorkstationCluster(body: Schema.WorkstationCluster) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListWorkstationClustersResponse]) {
					/** Optional. Maximum number of items to return.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. next_page_token value returned from a previous List request, if any. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListWorkstationClustersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListWorkstationClustersResponse]] = (fun: list) => fun.apply()
				}
				object workstationConfigs {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.WorkstationConfig]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.WorkstationConfig])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.WorkstationConfig]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. If set, validate the request and preview the review, but do not actually apply it. */
						def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						/** Optional. If set and the workstation configuration is not found, a new workstation configuration will be created. In this situation, update_mask is ignored. */
						def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
						def withWorkstationConfig(body: Schema.WorkstationConfig) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. If set, validate the request and preview the review, but do not actually apply it. */
						def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						def withWorkstationConfig(body: Schema.WorkstationConfig) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, parent: String, workstationConfigId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs").addQueryStringParameters("parent" -> parent.toString, "workstationConfigId" -> workstationConfigId.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						/** Optional. If set, validate the request and preview the review, but do not actually apply it. */
						def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						/** Optional. If set, the request is rejected if the latest version of the workstation configuration on the server does not have this ETag. */
						def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
						/** Optional. If set, any workstations in the workstation configuration are also deleted. Otherwise, the request works only if the workstation configuration has no workstations. */
						def withForce(force: Boolean) = new delete(req.addQueryStringParameters("force" -> force.toString))
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListWorkstationConfigsResponse]) {
						/** Optional. Maximum number of items to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. next_page_token value returned from a previous List request, if any. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListWorkstationConfigsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListWorkstationConfigsResponse]] = (fun: list) => fun.apply()
					}
					class listUsable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListUsableWorkstationConfigsResponse]) {
						/** Optional. Maximum number of items to return.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new listUsable(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. next_page_token value returned from a previous List request, if any. */
						def withPageToken(pageToken: String) = new listUsable(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListUsableWorkstationConfigsResponse])
					}
					object listUsable {
						def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): listUsable = new listUsable(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs:listUsable").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[listUsable, Future[Schema.ListUsableWorkstationConfigsResponse]] = (fun: listUsable) => fun.apply()
					}
					object workstations {
						class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
						}
						object testIamPermissions {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, workstationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations/${workstationsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
						}
						class stop(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withStopWorkstationRequest(body: Schema.StopWorkstationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
						}
						object stop {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, workstationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations/${workstationsId}:stop").addQueryStringParameters("name" -> name.toString))
						}
						class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
							/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
							def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
						}
						object getIamPolicy {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, workstationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations/${workstationsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
							given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
							/** Optional. If set, validate the request and preview the review, but do not actually apply it. */
							def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
							/** Optional. If set, the request will be rejected if the latest version of the workstation on the server does not have this ETag. */
							def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, workstationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations/${workstationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Workstation]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Workstation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, workstationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations/${workstationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Workstation]] = (fun: get) => fun.apply()
						}
						class generateAccessToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGenerateAccessTokenRequest(body: Schema.GenerateAccessTokenRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GenerateAccessTokenResponse])
						}
						object generateAccessToken {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, workstationsId :PlayApi, workstation: String)(using auth: AuthToken, ec: ExecutionContext): generateAccessToken = new generateAccessToken(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations/${workstationsId}:generateAccessToken").addQueryStringParameters("workstation" -> workstation.toString))
						}
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							/** Optional. If set, validate the request and preview the review, but do not actually apply it. */
							def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
							def withWorkstation(body: Schema.Workstation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, parent: String, workstationId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations").addQueryStringParameters("parent" -> parent.toString, "workstationId" -> workstationId.toString))
						}
						class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
						}
						object setIamPolicy {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, workstationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations/${workstationsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							/** Optional. If set, validate the request and preview the review, but do not actually apply it. */
							def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
							/** Optional. If set and the workstation configuration is not found, a new workstation configuration is created. In this situation, update_mask is ignored. */
							def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
							def withWorkstation(body: Schema.Workstation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, workstationsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations/${workstationsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						class start(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withStartWorkstationRequest(body: Schema.StartWorkstationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
						}
						object start {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, workstationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): start = new start(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations/${workstationsId}:start").addQueryStringParameters("name" -> name.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListWorkstationsResponse]) {
							/** Optional. Maximum number of items to return.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. next_page_token value returned from a previous List request, if any. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListWorkstationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListWorkstationsResponse]] = (fun: list) => fun.apply()
						}
						class listUsable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListUsableWorkstationsResponse]) {
							/** Optional. Maximum number of items to return.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new listUsable(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. next_page_token value returned from a previous List request, if any. */
							def withPageToken(pageToken: String) = new listUsable(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListUsableWorkstationsResponse])
						}
						object listUsable {
							def apply(projectsId :PlayApi, locationsId :PlayApi, workstationClustersId :PlayApi, workstationConfigsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): listUsable = new listUsable(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/workstationClusters/${workstationClustersId}/workstationConfigs/${workstationConfigsId}/workstations:listUsable").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[listUsable, Future[Schema.ListUsableWorkstationsResponse]] = (fun: listUsable) => fun.apply()
						}
					}
				}
			}
		}
	}
}
