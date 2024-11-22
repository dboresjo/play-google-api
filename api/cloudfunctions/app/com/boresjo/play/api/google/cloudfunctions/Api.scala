package com.boresjo.play.api.google.cloudfunctions

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

	private val BASE_URL = "https://cloudfunctions.googleapis.com/"

	object projects {
		object locations {
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
			}
			object functions {
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Creates a 2nd Gen copy of the function configuration based on the 1st Gen function with the given name. This is the first step of the multi step process to upgrade 1st Gen functions to 2nd Gen. Only 2nd Gen configuration is setup as part of this request and traffic continues to be served by 1st Gen. */
				class setupFunctionUpgradeConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetupFunctionUpgradeConfigRequest(body: Schema.SetupFunctionUpgradeConfigRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object setupFunctionUpgradeConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setupFunctionUpgradeConfig = new setupFunctionUpgradeConfig(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}:setupFunctionUpgradeConfig").addQueryStringParameters("name" -> name.toString))
				}
				/** Returns a signed URL for downloading deployed function source code. The URL is only valid for a limited period and should be used within 30 minutes of generation. For more information about the signed URL usage see: https://cloud.google.com/storage/docs/access-control/signed-urls */
				class generateDownloadUrl(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGenerateDownloadUrlRequest(body: Schema.GenerateDownloadUrlRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GenerateDownloadUrlResponse])
				}
				object generateDownloadUrl {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): generateDownloadUrl = new generateDownloadUrl(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}:generateDownloadUrl").addQueryStringParameters("name" -> name.toString))
				}
				/** Reverts the traffic target of a function from the 2nd Gen copy to the original 1st Gen function. After this operation, all new traffic would be served by the 1st Gen. */
				class rollbackFunctionUpgradeTraffic(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRollbackFunctionUpgradeTrafficRequest(body: Schema.RollbackFunctionUpgradeTrafficRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object rollbackFunctionUpgradeTraffic {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): rollbackFunctionUpgradeTraffic = new rollbackFunctionUpgradeTraffic(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}:rollbackFunctionUpgradeTraffic").addQueryStringParameters("name" -> name.toString))
				}
				/** Returns a signed URL for uploading a function source code. For more information about the signed URL usage see: https://cloud.google.com/storage/docs/access-control/signed-urls. Once the function source code upload is complete, the used signed URL should be provided in CreateFunction or UpdateFunction request as a reference to the function source code. When uploading source code to the generated signed URL, please follow these restrictions: &#42; Source file type should be a zip file. &#42; No credentials should be attached - the signed URLs provide access to the target bucket using internal service identity; if credentials were attached, the identity from the credentials would be used, but that identity does not have permissions to upload files to the URL. When making a HTTP PUT request, specify this header: &#42; `content-type: application/zip` Do not specify this header: &#42; `Authorization: Bearer YOUR_TOKEN` */
				class generateUploadUrl(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGenerateUploadUrlRequest(body: Schema.GenerateUploadUrlRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GenerateUploadUrlResponse])
				}
				object generateUploadUrl {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): generateUploadUrl = new generateUploadUrl(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions:generateUploadUrl").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Aborts generation upgrade process for a function with the given name from the specified project. Deletes all 2nd Gen copy related configuration and resources which were created during the upgrade process. */
				class abortFunctionUpgrade(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withAbortFunctionUpgradeRequest(body: Schema.AbortFunctionUpgradeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object abortFunctionUpgrade {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): abortFunctionUpgrade = new abortFunctionUpgrade(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}:abortFunctionUpgrade").addQueryStringParameters("name" -> name.toString))
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
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Deletes a function with the given name from the specified project. If the given function is used by some trigger, the trigger will be updated to remove this function. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Returns a function with the given name from the requested project. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Function]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The optional version of the 1st gen function whose details should be obtained. The version of a 1st gen function is an integer that starts from 1 and gets incremented on redeployments. GCF may keep historical configs for old versions of 1st gen function. This field can be specified to fetch the historical configs. This field is valid only for GCF 1st gen function. */
					def withRevision(revision: String) = new get(req.addQueryStringParameters("revision" -> revision.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Function])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Function]] = (fun: get) => fun.apply()
				}
				/** Updates existing function. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withFunction(body: Schema.Function) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Changes the traffic target of a function from the original 1st Gen function to the 2nd Gen copy. This is the second step of the multi step process to upgrade 1st Gen functions to 2nd Gen. After this operation, all new traffic will be served by 2nd Gen copy. */
				class redirectFunctionUpgradeTraffic(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRedirectFunctionUpgradeTrafficRequest(body: Schema.RedirectFunctionUpgradeTrafficRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object redirectFunctionUpgradeTraffic {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): redirectFunctionUpgradeTraffic = new redirectFunctionUpgradeTraffic(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}:redirectFunctionUpgradeTraffic").addQueryStringParameters("name" -> name.toString))
				}
				/** Creates a new function. If a function with the given name already exists in the specified project, the long running operation will return `ALREADY_EXISTS` error. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withFunction(body: Schema.Function) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, functionId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions").addQueryStringParameters("parent" -> parent.toString, "functionId" -> functionId.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Returns a list of functions that belong to the requested project. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFunctionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFunctionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.ListFunctionsResponse]] = (fun: list) => fun.apply()
				}
				/** Finalizes the upgrade after which function upgrade can not be rolled back. This is the last step of the multi step process to upgrade 1st Gen functions to 2nd Gen. Deletes all original 1st Gen related configuration and resources. */
				class commitFunctionUpgrade(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCommitFunctionUpgradeRequest(body: Schema.CommitFunctionUpgradeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object commitFunctionUpgrade {
					def apply(projectsId :PlayApi, locationsId :PlayApi, functionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): commitFunctionUpgrade = new commitFunctionUpgrade(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/functions/${functionsId}:commitFunctionUpgrade").addQueryStringParameters("name" -> name.toString))
				}
			}
			object runtimes {
				/** Returns a list of runtimes that are supported for the requested project. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListRuntimesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListRuntimesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/runtimes").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListRuntimesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
