package com.boresjo.play.api.google.alloydb

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://alloydb.googleapis.com/"

	object projects {
		object locations {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudLocationLocation]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudLocationLocation])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudLocationLocation]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudLocationListLocationsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudLocationListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleCloudLocationListLocationsResponse]] = (fun: list) => fun.apply()
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
			object clusters {
				class promote(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withPromoteClusterRequest(body: Schema.PromoteClusterRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object promote {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): promote = new promote(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:promote")).addQueryStringParameters("name" -> name.toString))
				}
				class `export`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withExportClusterRequest(body: Schema.ExportClusterRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object `export` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): `export` = new `export`(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:export")).addQueryStringParameters("name" -> name.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. The current etag of the Cluster. If an etag is provided and does not match the current etag of the Cluster, deletion will be blocked and an ABORTED error will be returned. */
					def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
					/** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
					def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. Whether to cascade delete child instances for given cluster. */
					def withForce(force: Boolean) = new delete(req.addQueryStringParameters("force" -> force.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Cluster]) {
					/** Optional. The view of the cluster to return. Returns all default fields if not set.<br>Possible values:<br>CLUSTER_VIEW_UNSPECIFIED: CLUSTER_VIEW_UNSPECIFIED Not specified, equivalent to BASIC.<br>CLUSTER_VIEW_BASIC: BASIC server responses include all the relevant cluster details, excluding Cluster.ContinuousBackupInfo.EarliestRestorableTime and other view-specific fields. The default value.<br>CLUSTER_VIEW_CONTINUOUS_BACKUP: CONTINUOUS_BACKUP response returns all the fields from BASIC plus the earliest restorable time if continuous backups are enabled. May increase latency. */
					def withView(view: String) = new get(req.addQueryStringParameters("view" -> view.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.Cluster])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Cluster]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListClustersResponse]) {
					/** Optional. Requested page size. Server may return fewer items than requested. If unspecified, server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Filtering results */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Hint for how to order the results */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListClustersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListClustersResponse]] = (fun: list) => fun.apply()
				}
				class createsecondary(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new createsecondary(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
					def withValidateOnly(validateOnly: Boolean) = new createsecondary(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					def withCluster(body: Schema.Cluster) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object createsecondary {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): createsecondary = new createsecondary(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters:createsecondary")).addQueryStringParameters("parent" -> parent.toString, "clusterId" -> clusterId.toString))
				}
				class restore(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRestoreClusterRequest(body: Schema.RestoreClusterRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object restore {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): restore = new restore(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters:restore")).addQueryStringParameters("parent" -> parent.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
					def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					def withCluster(body: Schema.Cluster) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, clusterId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters")).addQueryStringParameters("parent" -> parent.toString, "clusterId" -> clusterId.toString))
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Field mask is used to specify the fields to be overwritten in the Cluster resource by the update. The fields specified in the update_mask are relative to the resource, not the full request. A field will be overwritten if it is in the mask. If the user does not provide a mask then all fields will be overwritten.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
					def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. If set to true, update succeeds even if cluster is not found. In that case, a new cluster is created and `update_mask` is ignored. */
					def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					def withCluster(body: Schema.Cluster) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}")).addQueryStringParameters("name" -> name.toString))
				}
				class upgrade(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withUpgradeClusterRequest(body: Schema.UpgradeClusterRequest) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
				}
				object upgrade {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): upgrade = new upgrade(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:upgrade")).addQueryStringParameters("name" -> name.toString))
				}
				class switchover(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSwitchoverClusterRequest(body: Schema.SwitchoverClusterRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object switchover {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): switchover = new switchover(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:switchover")).addQueryStringParameters("name" -> name.toString))
				}
				object users {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Optional. If set, the backend validates the request, but doesn't actually execute it. */
						def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						def withUser(body: Schema.User) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.User])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, parent: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/users")).addQueryStringParameters("parent" -> parent.toString, "userId" -> userId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Optional. If set, the backend validates the request, but doesn't actually execute it. */
						def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, usersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/users/${usersId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.User]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.User])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, usersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/users/${usersId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.User]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. Field mask is used to specify the fields to be overwritten in the User resource by the update. The fields specified in the update_mask are relative to the resource, not the full request. A field will be overwritten if it is in the mask. If the user does not provide a mask then all fields will be overwritten.<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Optional. If set, the backend validates the request, but doesn't actually execute it. */
						def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						/** Optional. Allow missing fields in the update mask. */
						def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
						def withUser(body: Schema.User) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.User])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, usersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/users/${usersId}")).addQueryStringParameters("name" -> name.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListUsersResponse]) {
						/** Optional. Requested page size. Server may return fewer items than requested. If unspecified, server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A token identifying a page of results the server should return. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Filtering results */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Hint for how to order the results */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListUsersResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/users")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListUsersResponse]] = (fun: list) => fun.apply()
					}
				}
				object instances {
					class injectFault(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withInjectFaultRequest(body: Schema.InjectFaultRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object injectFault {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): injectFault = new injectFault(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/instances/${instancesId}:injectFault")).addQueryStringParameters("name" -> name.toString))
					}
					class createsecondary(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new createsecondary(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
						def withValidateOnly(validateOnly: Boolean) = new createsecondary(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						def withInstance(body: Schema.Instance) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object createsecondary {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, parent: String, instanceId: String)(using auth: AuthToken, ec: ExecutionContext): createsecondary = new createsecondary(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/instances:createsecondary")).addQueryStringParameters("parent" -> parent.toString, "instanceId" -> instanceId.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
						def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						def withInstance(body: Schema.Instance) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, parent: String, instanceId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/instances")).addQueryStringParameters("parent" -> parent.toString, "instanceId" -> instanceId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Optional. The current etag of the Instance. If an etag is provided and does not match the current etag of the Instance, deletion will be blocked and an ABORTED error will be returned. */
						def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
						/** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
						def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/instances/${instancesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Instance]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.Instance])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, instancesId :PlayApi, name: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/instances/${instancesId}")).addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
						given Conversion[get, Future[Schema.Instance]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListInstancesResponse]) {
						/** Optional. Requested page size. Server may return fewer items than requested. If unspecified, server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Filtering results */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Hint for how to order the results */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListInstancesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/instances")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListInstancesResponse]] = (fun: list) => fun.apply()
					}
					class getConnectionInfo(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ConnectionInfo]) {
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new getConnectionInfo(req.addQueryStringParameters("requestId" -> requestId.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ConnectionInfo])
					}
					object getConnectionInfo {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, instancesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): getConnectionInfo = new getConnectionInfo(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/instances/${instancesId}/connectionInfo")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[getConnectionInfo, Future[Schema.ConnectionInfo]] = (fun: getConnectionInfo) => fun.apply()
					}
					class restart(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withRestartInstanceRequest(body: Schema.RestartInstanceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object restart {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): restart = new restart(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/instances/${instancesId}:restart")).addQueryStringParameters("name" -> name.toString))
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. Field mask is used to specify the fields to be overwritten in the Instance resource by the update. The fields specified in the update_mask are relative to the resource, not the full request. A field will be overwritten if it is in the mask. If the user does not provide a mask then all fields will be overwritten.<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
						def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						/** Optional. If set to true, update succeeds even if instance is not found. In that case, a new instance is created and `update_mask` is ignored. */
						def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
						def withInstance(body: Schema.Instance) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/instances/${instancesId}")).addQueryStringParameters("name" -> name.toString))
					}
					class failover(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withFailoverInstanceRequest(body: Schema.FailoverInstanceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object failover {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, instancesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): failover = new failover(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/instances/${instancesId}:failover")).addQueryStringParameters("name" -> name.toString))
					}
				}
			}
			object backups {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set, the backend validates the request, but doesn't actually execute it. */
					def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					def withBackup(body: Schema.Backup) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, backupId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backups")).addQueryStringParameters("parent" -> parent.toString, "backupId" -> backupId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set, the backend validates the request, but doesn't actually execute it. */
					def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. The current etag of the Backup. If an etag is provided and does not match the current etag of the Backup, deletion will be blocked and an ABORTED error will be returned. */
					def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, backupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backups/${backupsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Backup]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Backup])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, backupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backups/${backupsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Backup]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Field mask is used to specify the fields to be overwritten in the Backup resource by the update. The fields specified in the update_mask are relative to the resource, not the full request. A field will be overwritten if it is in the mask. If the user does not provide a mask then all fields will be overwritten.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set, the backend validates the request, but doesn't actually execute it. */
					def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. If set to true, update succeeds even if instance is not found. In that case, a new backup is created and `update_mask` is ignored. */
					def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					def withBackup(body: Schema.Backup) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, backupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backups/${backupsId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBackupsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListBackupsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backups")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.ListBackupsResponse]] = (fun: list) => fun.apply()
				}
			}
			object supportedDatabaseFlags {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSupportedDatabaseFlagsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListSupportedDatabaseFlagsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/supportedDatabaseFlags")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListSupportedDatabaseFlagsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
