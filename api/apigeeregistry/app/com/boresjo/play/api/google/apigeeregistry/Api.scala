package com.boresjo.play.api.google.apigeeregistry

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://apigeeregistry.googleapis.com/"

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
			object artifacts {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, artifactsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/artifacts/${artifactsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class getContents(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
				}
				object getContents {
					def apply(projectsId :PlayApi, locationsId :PlayApi, artifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getContents = new getContents(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/artifacts/${artifactsId}:getContents").addQueryStringParameters("name" -> name.toString))
					given Conversion[getContents, Future[Schema.HttpBody]] = (fun: getContents) => fun.apply()
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, artifactsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/artifacts/${artifactsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, artifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class replaceArtifact(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withArtifact(body: Schema.Artifact) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Artifact])
				}
				object replaceArtifact {
					def apply(projectsId :PlayApi, locationsId :PlayApi, artifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): replaceArtifact = new replaceArtifact(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Artifact]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Artifact])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, artifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Artifact]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListArtifactsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListArtifactsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/artifacts").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.ListArtifactsResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withArtifact(body: Schema.Artifact) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Artifact])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, artifactId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/artifacts").addQueryStringParameters("parent" -> parent.toString, "artifactId" -> artifactId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, artifactsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/artifacts/${artifactsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
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
			object instances {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Instance]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Instance])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Instance]] = (fun: get) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withInstance(body: Schema.Instance) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, instanceId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances").addQueryStringParameters("parent" -> parent.toString, "instanceId" -> instanceId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
			}
			object apis {
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, name: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Api]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Api])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Api]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withApi(body: Schema.Api) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Api])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, name: String, updateMask: String, allowMissing: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "allowMissing" -> allowMissing.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListApisResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListApisResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.ListApisResponse]] = (fun: list) => fun.apply()
				}
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withApi(body: Schema.Api) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Api])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, apiId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis").addQueryStringParameters("parent" -> parent.toString, "apiId" -> apiId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object versions {
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, name: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ApiVersion]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ApiVersion])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.ApiVersion]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withApiVersion(body: Schema.ApiVersion) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ApiVersion])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, name: String, updateMask: String, allowMissing: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "allowMissing" -> allowMissing.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListApiVersionsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListApiVersionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
						given Conversion[list, Future[Schema.ListApiVersionsResponse]] = (fun: list) => fun.apply()
					}
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withApiVersion(body: Schema.ApiVersion) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApiVersion])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, parent: String, apiVersionId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions").addQueryStringParameters("parent" -> parent.toString, "apiVersionId" -> apiVersionId.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					object specs {
						class getContents(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
						}
						object getContents {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getContents = new getContents(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}:getContents").addQueryStringParameters("name" -> name.toString))
							given Conversion[getContents, Future[Schema.HttpBody]] = (fun: getContents) => fun.apply()
						}
						class rollback(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withRollbackApiSpecRequest(body: Schema.RollbackApiSpecRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApiSpec])
						}
						object rollback {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): rollback = new rollback(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}:rollback").addQueryStringParameters("name" -> name.toString))
						}
						class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
							/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
							def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
						}
						object getIamPolicy {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
							given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, name: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						class tagRevision(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							/** Required. The name of the spec to be tagged, including the revision ID is optional. If a revision is not specified, it will tag the latest revision. */
							def withName(name: String) = new tagRevision(req.addQueryStringParameters("name" -> name.toString))
							def withTagApiSpecRevisionRequest(body: Schema.TagApiSpecRevisionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApiSpec])
						}
						object tagRevision {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): tagRevision = new tagRevision(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}:tagRevision").addQueryStringParameters())
						}
						class deleteRevision(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ApiSpec]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.ApiSpec])
						}
						object deleteRevision {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): deleteRevision = new deleteRevision(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}:deleteRevision").addQueryStringParameters("name" -> name.toString))
							given Conversion[deleteRevision, Future[Schema.ApiSpec]] = (fun: deleteRevision) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withApiSpec(body: Schema.ApiSpec) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ApiSpec])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, name: String, updateMask: String, allowMissing: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "allowMissing" -> allowMissing.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListApiSpecsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListApiSpecsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
							given Conversion[list, Future[Schema.ListApiSpecsResponse]] = (fun: list) => fun.apply()
						}
						class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
						}
						object testIamPermissions {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
						}
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withApiSpec(body: Schema.ApiSpec) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApiSpec])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, parent: String, apiSpecId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs").addQueryStringParameters("parent" -> parent.toString, "apiSpecId" -> apiSpecId.toString))
						}
						class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
						}
						object setIamPolicy {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ApiSpec]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ApiSpec])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.ApiSpec]] = (fun: get) => fun.apply()
						}
						class listRevisions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListApiSpecRevisionsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListApiSpecRevisionsResponse])
						}
						object listRevisions {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, name: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): listRevisions = new listRevisions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}:listRevisions").addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
							given Conversion[listRevisions, Future[Schema.ListApiSpecRevisionsResponse]] = (fun: listRevisions) => fun.apply()
						}
						object artifacts {
							class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
								def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
							}
							object testIamPermissions {
								def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, artifactsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}/artifacts/${artifactsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
							}
							class getContents(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
							}
							object getContents {
								def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, artifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getContents = new getContents(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}/artifacts/${artifactsId}:getContents").addQueryStringParameters("name" -> name.toString))
								given Conversion[getContents, Future[Schema.HttpBody]] = (fun: getContents) => fun.apply()
							}
							class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
								/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
								def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
							}
							object getIamPolicy {
								def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, artifactsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}/artifacts/${artifactsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
								given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
							}
							class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
								def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
							}
							object delete {
								def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, artifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
							}
							class replaceArtifact(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
								def withArtifact(body: Schema.Artifact) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Artifact])
							}
							object replaceArtifact {
								def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, artifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): replaceArtifact = new replaceArtifact(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
							}
							class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Artifact]) {
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Artifact])
							}
							object get {
								def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, artifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.Artifact]] = (fun: get) => fun.apply()
							}
							class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListArtifactsResponse]) {
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListArtifactsResponse])
							}
							object list {
								def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}/artifacts").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
								given Conversion[list, Future[Schema.ListArtifactsResponse]] = (fun: list) => fun.apply()
							}
							class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
								def withArtifact(body: Schema.Artifact) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Artifact])
							}
							object create {
								def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, parent: String, artifactId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}/artifacts").addQueryStringParameters("parent" -> parent.toString, "artifactId" -> artifactId.toString))
							}
							class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
								def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
							}
							object setIamPolicy {
								def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, specsId :PlayApi, artifactsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/specs/${specsId}/artifacts/${artifactsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
							}
						}
					}
					object artifacts {
						class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
						}
						object testIamPermissions {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, artifactsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/artifacts/${artifactsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
						}
						class getContents(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
						}
						object getContents {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, artifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getContents = new getContents(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/artifacts/${artifactsId}:getContents").addQueryStringParameters("name" -> name.toString))
							given Conversion[getContents, Future[Schema.HttpBody]] = (fun: getContents) => fun.apply()
						}
						class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
							/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
							def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
						}
						object getIamPolicy {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, artifactsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/artifacts/${artifactsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
							given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, artifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						class replaceArtifact(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withArtifact(body: Schema.Artifact) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Artifact])
						}
						object replaceArtifact {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, artifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): replaceArtifact = new replaceArtifact(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Artifact]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Artifact])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, artifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Artifact]] = (fun: get) => fun.apply()
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListArtifactsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListArtifactsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/artifacts").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
							given Conversion[list, Future[Schema.ListArtifactsResponse]] = (fun: list) => fun.apply()
						}
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withArtifact(body: Schema.Artifact) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Artifact])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, parent: String, artifactId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/artifacts").addQueryStringParameters("parent" -> parent.toString, "artifactId" -> artifactId.toString))
						}
						class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
						}
						object setIamPolicy {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, versionsId :PlayApi, artifactsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/versions/${versionsId}/artifacts/${artifactsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						}
					}
				}
				object deployments {
					class rollback(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withRollbackApiDeploymentRequest(body: Schema.RollbackApiDeploymentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApiDeployment])
					}
					object rollback {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): rollback = new rollback(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}:rollback").addQueryStringParameters("name" -> name.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, name: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class tagRevision(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Required. The name of the deployment to be tagged, including the revision ID is optional. If a revision is not specified, it will tag the latest revision. */
						def withName(name: String) = new tagRevision(req.addQueryStringParameters("name" -> name.toString))
						def withTagApiDeploymentRevisionRequest(body: Schema.TagApiDeploymentRevisionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApiDeployment])
					}
					object tagRevision {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): tagRevision = new tagRevision(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}:tagRevision").addQueryStringParameters())
					}
					class deleteRevision(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ApiDeployment]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.ApiDeployment])
					}
					object deleteRevision {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): deleteRevision = new deleteRevision(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}:deleteRevision").addQueryStringParameters("name" -> name.toString))
						given Conversion[deleteRevision, Future[Schema.ApiDeployment]] = (fun: deleteRevision) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withApiDeployment(body: Schema.ApiDeployment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ApiDeployment])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, name: String, updateMask: String, allowMissing: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "allowMissing" -> allowMissing.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListApiDeploymentsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListApiDeploymentsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
						given Conversion[list, Future[Schema.ListApiDeploymentsResponse]] = (fun: list) => fun.apply()
					}
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withApiDeployment(body: Schema.ApiDeployment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ApiDeployment])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, parent: String, apiDeploymentId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments").addQueryStringParameters("parent" -> parent.toString, "apiDeploymentId" -> apiDeploymentId.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ApiDeployment]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ApiDeployment])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.ApiDeployment]] = (fun: get) => fun.apply()
					}
					class listRevisions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListApiDeploymentRevisionsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListApiDeploymentRevisionsResponse])
					}
					object listRevisions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, name: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): listRevisions = new listRevisions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}:listRevisions").addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[listRevisions, Future[Schema.ListApiDeploymentRevisionsResponse]] = (fun: listRevisions) => fun.apply()
					}
					object artifacts {
						class getContents(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
						}
						object getContents {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, artifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getContents = new getContents(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}/artifacts/${artifactsId}:getContents").addQueryStringParameters("name" -> name.toString))
							given Conversion[getContents, Future[Schema.HttpBody]] = (fun: getContents) => fun.apply()
						}
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withArtifact(body: Schema.Artifact) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Artifact])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, parent: String, artifactId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}/artifacts").addQueryStringParameters("parent" -> parent.toString, "artifactId" -> artifactId.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, artifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
						}
						class replaceArtifact(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withArtifact(body: Schema.Artifact) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Artifact])
						}
						object replaceArtifact {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, artifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): replaceArtifact = new replaceArtifact(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Artifact]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Artifact])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, artifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Artifact]] = (fun: get) => fun.apply()
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListArtifactsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListArtifactsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, deploymentsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/deployments/${deploymentsId}/artifacts").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
							given Conversion[list, Future[Schema.ListArtifactsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object artifacts {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, artifactsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/artifacts/${artifactsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					class getContents(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.HttpBody]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.HttpBody])
					}
					object getContents {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, artifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getContents = new getContents(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/artifacts/${artifactsId}:getContents").addQueryStringParameters("name" -> name.toString))
						given Conversion[getContents, Future[Schema.HttpBody]] = (fun: getContents) => fun.apply()
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, artifactsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/artifacts/${artifactsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, artifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class replaceArtifact(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withArtifact(body: Schema.Artifact) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Artifact])
					}
					object replaceArtifact {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, artifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): replaceArtifact = new replaceArtifact(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Artifact]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Artifact])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, artifactsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/artifacts/${artifactsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Artifact]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListArtifactsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListArtifactsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/artifacts").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
						given Conversion[list, Future[Schema.ListArtifactsResponse]] = (fun: list) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withArtifact(body: Schema.Artifact) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Artifact])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, parent: String, artifactId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/artifacts").addQueryStringParameters("parent" -> parent.toString, "artifactId" -> artifactId.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, apisId :PlayApi, artifactsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/apis/${apisId}/artifacts/${artifactsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
				}
			}
			object documents {
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/documents:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
			}
			object runtime {
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/runtime:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/runtime:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/runtime:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
			}
		}
	}
}
