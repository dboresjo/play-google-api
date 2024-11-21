package com.boresjo.play.api.google.cloudfunctions

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://cloudfunctions.googleapis.com/"

	object projects {
		object locations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
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
			}
			object functions {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class setupFunctionUpgradeConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetupFunctionUpgradeConfigRequest(body: Schema.SetupFunctionUpgradeConfigRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object setupFunctionUpgradeConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setupFunctionUpgradeConfig = new setupFunctionUpgradeConfig(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}:setupFunctionUpgradeConfig")).addQueryStringParameters("name" -> name.toString))
				}
				class generateDownloadUrl(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGenerateDownloadUrlRequest(body: Schema.GenerateDownloadUrlRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GenerateDownloadUrlResponse])
				}
				object generateDownloadUrl {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): generateDownloadUrl = new generateDownloadUrl(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}:generateDownloadUrl")).addQueryStringParameters("name" -> name.toString))
				}
				class rollbackFunctionUpgradeTraffic(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRollbackFunctionUpgradeTrafficRequest(body: Schema.RollbackFunctionUpgradeTrafficRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object rollbackFunctionUpgradeTraffic {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): rollbackFunctionUpgradeTraffic = new rollbackFunctionUpgradeTraffic(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}:rollbackFunctionUpgradeTraffic")).addQueryStringParameters("name" -> name.toString))
				}
				class generateUploadUrl(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGenerateUploadUrlRequest(body: Schema.GenerateUploadUrlRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GenerateUploadUrlResponse])
				}
				object generateUploadUrl {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): generateUploadUrl = new generateUploadUrl(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions:generateUploadUrl")).addQueryStringParameters("parent" -> parent.toString))
				}
				class abortFunctionUpgrade(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAbortFunctionUpgradeRequest(body: Schema.AbortFunctionUpgradeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object abortFunctionUpgrade {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): abortFunctionUpgrade = new abortFunctionUpgrade(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}:abortFunctionUpgrade")).addQueryStringParameters("name" -> name.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Function]) {
					/** Optional. The optional version of the 1st gen function whose details should be obtained. The version of a 1st gen function is an integer that starts from 1 and gets incremented on redeployments. GCF may keep historical configs for old versions of 1st gen function. This field can be specified to fetch the historical configs. This field is valid only for GCF 1st gen function. */
					def withRevision(revision: String) = new get(req.addQueryStringParameters("revision" -> revision.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.Function])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Function]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withFunction(body: Schema.Function) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class redirectFunctionUpgradeTraffic(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRedirectFunctionUpgradeTrafficRequest(body: Schema.RedirectFunctionUpgradeTrafficRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object redirectFunctionUpgradeTraffic {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): redirectFunctionUpgradeTraffic = new redirectFunctionUpgradeTraffic(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}:redirectFunctionUpgradeTraffic")).addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withFunction(body: Schema.Function) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, functionId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions")).addQueryStringParameters("parent" -> parent.toString, "functionId" -> functionId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFunctionsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListFunctionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.ListFunctionsResponse]] = (fun: list) => fun.apply()
				}
				class commitFunctionUpgrade(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCommitFunctionUpgradeRequest(body: Schema.CommitFunctionUpgradeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object commitFunctionUpgrade {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): commitFunctionUpgrade = new commitFunctionUpgrade(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}:commitFunctionUpgrade")).addQueryStringParameters("name" -> name.toString))
				}
			}
			object runtimes {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListRuntimesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListRuntimesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/runtimes")).addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListRuntimesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
