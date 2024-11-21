package com.boresjo.play.api.google.datafusion

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://datafusion.googleapis.com/"

	object projects {
		object locations {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel")).addQueryStringParameters("name" -> name.toString))
				}
			}
			object versions {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAvailableVersionsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListAvailableVersionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, latestPatchOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/versions")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "latestPatchOnly" -> latestPatchOnly.toString))
					given Conversion[list, Future[Schema.ListAvailableVersionsResponse]] = (fun: list) => fun.apply()
				}
			}
			object instances {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class restart(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRestartInstanceRequest(body: Schema.RestartInstanceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object restart {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): restart = new restart(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:restart")).addQueryStringParameters("name" -> name.toString))
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withInstance(body: Schema.Instance) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInstancesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListInstancesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.ListInstancesResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withInstance(body: Schema.Instance) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, instanceId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances")).addQueryStringParameters("parent" -> parent.toString, "instanceId" -> instanceId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Instance]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Instance])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Instance]] = (fun: get) => fun.apply()
				}
				object dnsPeerings {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withDnsPeering(body: Schema.DnsPeering) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.DnsPeering])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, parent: String, dnsPeeringId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}/dnsPeerings")).addQueryStringParameters("parent" -> parent.toString, "dnsPeeringId" -> dnsPeeringId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, dnsPeeringsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}/dnsPeerings/${dnsPeeringsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDnsPeeringsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListDnsPeeringsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}/dnsPeerings")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListDnsPeeringsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
}
