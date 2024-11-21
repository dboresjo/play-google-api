package com.boresjo.play.api.google.notebooks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://notebooks.googleapis.com/"

	object projects {
		object locations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel")).addQueryStringParameters("name" -> name.toString))
				}
			}
			object instances {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class resizeDisk(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withResizeDiskRequest(body: Schema.ResizeDiskRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object resizeDisk {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, notebookInstance: String)(using auth: AuthToken, ec: ExecutionContext): resizeDisk = new resizeDisk(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:resizeDisk")).addQueryStringParameters("notebookInstance" -> notebookInstance.toString))
				}
				class restore(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRestoreInstanceRequest(body: Schema.RestoreInstanceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object restore {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): restore = new restore(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:restore")).addQueryStringParameters("name" -> name.toString))
				}
				class checkUpgradability(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CheckInstanceUpgradabilityResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.CheckInstanceUpgradabilityResponse])
				}
				object checkUpgradability {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, notebookInstance: String)(using auth: AuthToken, ec: ExecutionContext): checkUpgradability = new checkUpgradability(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:checkUpgradability")).addQueryStringParameters("notebookInstance" -> notebookInstance.toString))
					given Conversion[checkUpgradability, Future[Schema.CheckInstanceUpgradabilityResponse]] = (fun: checkUpgradability) => fun.apply()
				}
				class diagnose(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDiagnoseInstanceRequest(body: Schema.DiagnoseInstanceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object diagnose {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): diagnose = new diagnose(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:diagnose")).addQueryStringParameters("name" -> name.toString))
				}
				class rollback(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRollbackInstanceRequest(body: Schema.RollbackInstanceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object rollback {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): rollback = new rollback(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:rollback")).addQueryStringParameters("name" -> name.toString))
				}
				class reportInfoSystem(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withReportInstanceInfoSystemRequest(body: Schema.ReportInstanceInfoSystemRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object reportInfoSystem {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): reportInfoSystem = new reportInfoSystem(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:reportInfoSystem")).addQueryStringParameters("name" -> name.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					/** Optional. Idempotent request UUID. */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class reset(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withResetInstanceRequest(body: Schema.ResetInstanceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object reset {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): reset = new reset(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:reset")).addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Instance]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Instance])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Instance]] = (fun: get) => fun.apply()
				}
				class getConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Config]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Config])
				}
				object getConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getConfig = new getConfig(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances:getConfig")).addQueryStringParameters("name" -> name.toString))
					given Conversion[getConfig, Future[Schema.Config]] = (fun: getConfig) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInstancesResponse]) {
					/** Optional. Maximum return size of the list call.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A previous returned page token that can be used to continue listing from the last result. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Sort results. Supported values are "name", "name desc" or "" (unsorted). */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Optional. List filter. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListInstancesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListInstancesResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Idempotent request UUID. */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withInstance(body: Schema.Instance) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, instanceId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances")).addQueryStringParameters("parent" -> parent.toString, "instanceId" -> instanceId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class stop(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withStopInstanceRequest(body: Schema.StopInstanceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object stop {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): stop = new stop(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:stop")).addQueryStringParameters("name" -> name.toString))
				}
				class upgradeSystem(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withUpgradeInstanceSystemRequest(body: Schema.UpgradeInstanceSystemRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object upgradeSystem {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): upgradeSystem = new upgradeSystem(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:upgradeSystem")).addQueryStringParameters("name" -> name.toString))
				}
				class upgrade(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withUpgradeInstanceRequest(body: Schema.UpgradeInstanceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object upgrade {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): upgrade = new upgrade(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:upgrade")).addQueryStringParameters("name" -> name.toString))
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Idempotent request UUID. */
					def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withInstance(body: Schema.Instance) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class start(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withStartInstanceRequest(body: Schema.StartInstanceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object start {
					def apply(projectsId :PlayApi, locationsId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): start = new start(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/instances/${instancesId}:start")).addQueryStringParameters("name" -> name.toString))
				}
			}
		}
	}
}
