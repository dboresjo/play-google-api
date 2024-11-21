package com.boresjo.play.api.google.config

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://config.googleapis.com/"

	object projects {
		object locations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			object deployments {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, deploymentsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/deployments/${deploymentsId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class deleteState(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDeleteStatefileRequest(body: Schema.DeleteStatefileRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
				}
				object deleteState {
					def apply(projectsId :PlayApi, locationsId :PlayApi, deploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): deleteState = new deleteState(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/deployments/${deploymentsId}:deleteState")).addQueryStringParameters("name" -> name.toString))
				}
				class exportState(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withExportDeploymentStatefileRequest(body: Schema.ExportDeploymentStatefileRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Statefile])
				}
				object exportState {
					def apply(projectsId :PlayApi, locationsId :PlayApi, deploymentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): exportState = new exportState(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/deployments/${deploymentsId}:exportState")).addQueryStringParameters("parent" -> parent.toString))
				}
				class lock(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withLockDeploymentRequest(body: Schema.LockDeploymentRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object lock {
					def apply(projectsId :PlayApi, locationsId :PlayApi, deploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): lock = new lock(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/deployments/${deploymentsId}:lock")).addQueryStringParameters("name" -> name.toString))
				}
				class unlock(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withUnlockDeploymentRequest(body: Schema.UnlockDeploymentRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object unlock {
					def apply(projectsId :PlayApi, locationsId :PlayApi, deploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): unlock = new unlock(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/deployments/${deploymentsId}:unlock")).addQueryStringParameters("name" -> name.toString))
				}
				class exportLock(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LockInfo]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.LockInfo])
				}
				object exportLock {
					def apply(projectsId :PlayApi, locationsId :PlayApi, deploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): exportLock = new exportLock(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/deployments/${deploymentsId}:exportLock")).addQueryStringParameters("name" -> name.toString))
					given Conversion[exportLock, Future[Schema.LockInfo]] = (fun: exportLock) => fun.apply()
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, deploymentsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/deployments/${deploymentsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set to true, any revisions for this deployment will also be deleted. (Otherwise, the request will only work if the deployment has no revisions.) */
					def withForce(force: Boolean) = new delete(req.addQueryStringParameters("force" -> force.toString))
					/** Optional. Policy on how resources actuated by the deployment should be deleted. If unspecified, the default behavior is to delete the underlying resources.<br>Possible values:<br>DELETE_POLICY_UNSPECIFIED: Unspecified policy, resources will be deleted.<br>DELETE: Deletes resources actuated by the deployment.<br>ABANDON: Abandons resources and only deletes the deployment and its metadata. */
					def withDeletePolicy(deletePolicy: String) = new delete(req.addQueryStringParameters("deletePolicy" -> deletePolicy.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, deploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/deployments/${deploymentsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Deployment]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Deployment])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, deploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/deployments/${deploymentsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Deployment]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDeploymentsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListDeploymentsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/deployments")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.ListDeploymentsResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withDeployment(body: Schema.Deployment) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, deploymentId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/deployments")).addQueryStringParameters("parent" -> parent.toString, "deploymentId" -> deploymentId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, deploymentsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/deployments/${deploymentsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Field mask used to specify the fields to be overwritten in the Deployment resource by the update. The fields specified in the update_mask are relative to the resource, not the full request. A field will be overwritten if it is in the mask. If the user does not provide a mask then all fields will be overwritten.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withDeployment(body: Schema.Deployment) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, deploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/deployments/${deploymentsId}")).addQueryStringParameters("name" -> name.toString))
				}
				class importState(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withImportStatefileRequest(body: Schema.ImportStatefileRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Statefile])
				}
				object importState {
					def apply(projectsId :PlayApi, locationsId :PlayApi, deploymentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): importState = new importState(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/deployments/${deploymentsId}:importState")).addQueryStringParameters("parent" -> parent.toString))
				}
				object revisions {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListRevisionsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListRevisionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, deploymentsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/deployments/${deploymentsId}/revisions")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
						given Conversion[list, Future[Schema.ListRevisionsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Revision]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.Revision])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, deploymentsId :PlayApi, revisionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/deployments/${deploymentsId}/revisions/${revisionsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Revision]] = (fun: get) => fun.apply()
					}
					class exportState(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withExportRevisionStatefileRequest(body: Schema.ExportRevisionStatefileRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Statefile])
					}
					object exportState {
						def apply(projectsId :PlayApi, locationsId :PlayApi, deploymentsId :PlayApi, revisionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): exportState = new exportState(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/deployments/${deploymentsId}/revisions/${revisionsId}:exportState")).addQueryStringParameters("parent" -> parent.toString))
					}
					object resources {
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Resource]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.Resource])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, deploymentsId :PlayApi, revisionsId :PlayApi, resourcesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/deployments/${deploymentsId}/revisions/${revisionsId}/resources/${resourcesId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Resource]] = (fun: get) => fun.apply()
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListResourcesResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.ListResourcesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, deploymentsId :PlayApi, revisionsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/deployments/${deploymentsId}/revisions/${revisionsId}/resources")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
							given Conversion[list, Future[Schema.ListResourcesResponse]] = (fun: list) => fun.apply()
						}
					}
				}
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
			object terraformVersions {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTerraformVersionsResponse]) {
					/** Optional. When requesting a page of resources, 'page_size' specifies number of resources to return. If unspecified, at most 500 will be returned. The maximum value is 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Token returned by previous call to 'ListTerraformVersions' which specifies the position in the list from where to continue listing the resources. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Lists the TerraformVersions that match the filter expression. A filter expression filters the resources listed in the response. The expression must be of the form '{field} {operator} {value}' where operators: '<', '>', '<=', '>=', '!=', '=', ':' are supported (colon ':' represents a HAS operator which is roughly synonymous with equality). {field} can refer to a proto or JSON field, or a synthetic field. Field names can be camelCase or snake_case. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Field to use to sort the list. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListTerraformVersionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/terraformVersions")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListTerraformVersionsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TerraformVersion]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.TerraformVersion])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, terraformVersionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/terraformVersions/${terraformVersionsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.TerraformVersion]] = (fun: get) => fun.apply()
				}
			}
			object previews {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. The preview ID. */
					def withPreviewId(previewId: String) = new create(req.addQueryStringParameters("previewId" -> previewId.toString))
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withPreview(body: Schema.Preview) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/previews")).addQueryStringParameters("parent" -> parent.toString))
				}
				class `export`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withExportPreviewResultRequest(body: Schema.ExportPreviewResultRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ExportPreviewResultResponse])
				}
				object `export` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, previewsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `export` = new `export`(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/previews/${previewsId}:export")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, previewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/previews/${previewsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Preview]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Preview])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, previewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/previews/${previewsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Preview]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPreviewsResponse]) {
					/** Optional. When requesting a page of resources, 'page_size' specifies number of resources to return. If unspecified, at most 500 will be returned. The maximum value is 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Token returned by previous call to 'ListDeployments' which specifies the position in the list from where to continue listing the resources. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Lists the Deployments that match the filter expression. A filter expression filters the resources listed in the response. The expression must be of the form '{field} {operator} {value}' where operators: '<', '>', '<=', '>=', '!=', '=', ':' are supported (colon ':' represents a HAS operator which is roughly synonymous with equality). {field} can refer to a proto or JSON field, or a synthetic field. Field names can be camelCase or snake_case. Examples: - Filter by name: name = "projects/foo/locations/us-central1/deployments/bar - Filter by labels: - Resources that have a key called 'foo' labels.foo:&#42; - Resources that have a key called 'foo' whose value is 'bar' labels.foo = bar - Filter by state: - Deployments in CREATING state. state=CREATING */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Field to use to sort the list. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListPreviewsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/previews")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListPreviewsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
