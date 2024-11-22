package com.boresjo.play.api.google.alloydb

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

	private val BASE_URL = "https://alloydb.googleapis.com/"

	object projects {
		object locations {
			/** Gets information about a location. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudLocationLocation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudLocationLocation])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudLocationLocation]] = (fun: get) => fun.apply()
			}
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudLocationListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudLocationListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleCloudLocationListLocationsResponse]] = (fun: list) => fun.apply()
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
			object clusters {
				/** Promotes a SECONDARY cluster. This turns down replication from the PRIMARY cluster and promotes a secondary cluster into its own standalone cluster. Imperative only. */
				class promote(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withPromoteClusterRequest(body: Schema.PromoteClusterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object promote {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): promote = new promote(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:promote").addQueryStringParameters("name" -> name.toString))
				}
				/** Exports data from the cluster. Imperative only. */
				class `export`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withExportClusterRequest(body: Schema.ExportClusterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object `export` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:export").addQueryStringParameters("name" -> name.toString))
				}
				/** Deletes a single Cluster. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. The current etag of the Cluster. If an etag is provided and does not match the current etag of the Cluster, deletion will be blocked and an ABORTED error will be returned. */
					def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
					/** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
					def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. Whether to cascade delete child instances for given cluster. */
					def withForce(force: Boolean) = new delete(req.addQueryStringParameters("force" -> force.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Gets details of a single Cluster. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Cluster]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The view of the cluster to return. Returns all default fields if not set.<br>Possible values:<br>CLUSTER_VIEW_UNSPECIFIED: CLUSTER_VIEW_UNSPECIFIED Not specified, equivalent to BASIC.<br>CLUSTER_VIEW_BASIC: BASIC server responses include all the relevant cluster details, excluding Cluster.ContinuousBackupInfo.EarliestRestorableTime and other view-specific fields. The default value.<br>CLUSTER_VIEW_CONTINUOUS_BACKUP: CONTINUOUS_BACKUP response returns all the fields from BASIC plus the earliest restorable time if continuous backups are enabled. May increase latency. */
					def withView(view: String) = new get(req.addQueryStringParameters("view" -> view.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Cluster])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Cluster]] = (fun: get) => fun.apply()
				}
				/** Lists Clusters in a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListClustersResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Requested page size. Server may return fewer items than requested. If unspecified, server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Filtering results */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Hint for how to order the results */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListClustersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListClustersResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a cluster of type SECONDARY in the given location using the primary cluster as the source. */
				class createsecondary(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new createsecondary(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
					def withValidateOnly(validateOnly: Boolean) = new createsecondary(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Perform the request */
					def withCluster(body: Schema.Cluster) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object createsecondary {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): createsecondary = new createsecondary(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters:createsecondary").addQueryStringParameters("parent" -> parent.toString, "clusterId" -> clusterId.toString))
				}
				/** Creates a new Cluster in a given project and location, with a volume restored from the provided source, either a backup ID or a point-in-time and a source cluster. */
				class restore(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRestoreClusterRequest(body: Schema.RestoreClusterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object restore {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): restore = new restore(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters:restore").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Creates a new Cluster in a given project and location. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
					def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Perform the request */
					def withCluster(body: Schema.Cluster) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, clusterId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters").addQueryStringParameters("parent" -> parent.toString, "clusterId" -> clusterId.toString))
				}
				/** Updates the parameters of a single Cluster. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Field mask is used to specify the fields to be overwritten in the Cluster resource by the update. The fields specified in the update_mask are relative to the resource, not the full request. A field will be overwritten if it is in the mask. If the user does not provide a mask then all fields will be overwritten.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
					def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. If set to true, update succeeds even if cluster is not found. In that case, a new cluster is created and `update_mask` is ignored. */
					def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					/** Perform the request */
					def withCluster(body: Schema.Cluster) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Upgrades a single Cluster. Imperative only. */
				class upgrade(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withUpgradeClusterRequest(body: Schema.UpgradeClusterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object upgrade {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): upgrade = new upgrade(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:upgrade").addQueryStringParameters("name" -> name.toString))
				}
				/** Switches the roles of PRIMARY and SECONDARY clusters without any data loss. This promotes the SECONDARY cluster to PRIMARY and sets up the original PRIMARY cluster to replicate from this newly promoted cluster. */
				class switchover(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSwitchoverClusterRequest(body: Schema.SwitchoverClusterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object switchover {
					def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): switchover = new switchover(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}:switchover").addQueryStringParameters("name" -> name.toString))
				}
				object users {
					/** Creates a new User in a given project, location, and cluster. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Optional. If set, the backend validates the request, but doesn't actually execute it. */
						def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						/** Perform the request */
						def withUser(body: Schema.User) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.User])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, parent: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/users").addQueryStringParameters("parent" -> parent.toString, "userId" -> userId.toString))
					}
					/** Deletes a single User. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Optional. If set, the backend validates the request, but doesn't actually execute it. */
						def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, usersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/users/${usersId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Gets details of a single User. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.User]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.User])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, usersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/users/${usersId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.User]] = (fun: get) => fun.apply()
					}
					/** Updates the parameters of a single User. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Field mask is used to specify the fields to be overwritten in the User resource by the update. The fields specified in the update_mask are relative to the resource, not the full request. A field will be overwritten if it is in the mask. If the user does not provide a mask then all fields will be overwritten.<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Optional. If set, the backend validates the request, but doesn't actually execute it. */
						def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						/** Optional. Allow missing fields in the update mask. */
						def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
						/** Perform the request */
						def withUser(body: Schema.User) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.User])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, usersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/users/${usersId}").addQueryStringParameters("name" -> name.toString))
					}
					/** Lists Users in a given project and location. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListUsersResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Requested page size. Server may return fewer items than requested. If unspecified, server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A token identifying a page of results the server should return. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Filtering results */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Hint for how to order the results */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListUsersResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/users").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListUsersResponse]] = (fun: list) => fun.apply()
					}
				}
				object instances {
					/** Injects fault in an instance. Imperative only. */
					class injectFault(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withInjectFaultRequest(body: Schema.InjectFaultRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object injectFault {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): injectFault = new injectFault(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/instances/${instancesId}:injectFault").addQueryStringParameters("name" -> name.toString))
					}
					/** Creates a new SECONDARY Instance in a given project and location. */
					class createsecondary(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new createsecondary(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
						def withValidateOnly(validateOnly: Boolean) = new createsecondary(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						/** Perform the request */
						def withInstance(body: Schema.Instance) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object createsecondary {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, parent: String, instanceId: String)(using signer: RequestSigner, ec: ExecutionContext): createsecondary = new createsecondary(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/instances:createsecondary").addQueryStringParameters("parent" -> parent.toString, "instanceId" -> instanceId.toString))
					}
					/** Creates a new Instance in a given project and location. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
						def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						/** Perform the request */
						def withInstance(body: Schema.Instance) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, parent: String, instanceId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/instances").addQueryStringParameters("parent" -> parent.toString, "instanceId" -> instanceId.toString))
					}
					/** Deletes a single Instance. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Optional. The current etag of the Instance. If an etag is provided and does not match the current etag of the Instance, deletion will be blocked and an ABORTED error will be returned. */
						def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
						/** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
						def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					/** Gets details of a single Instance. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Instance]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Instance])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, instancesId :PlayApi, name: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
						given Conversion[get, Future[Schema.Instance]] = (fun: get) => fun.apply()
					}
					/** Lists Instances in a given project and location. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListInstancesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Requested page size. Server may return fewer items than requested. If unspecified, server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Filtering results */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Hint for how to order the results */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListInstancesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/instances").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListInstancesResponse]] = (fun: list) => fun.apply()
					}
					/** Get instance metadata used for a connection. */
					class getConnectionInfo(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ConnectionInfo]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new getConnectionInfo(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ConnectionInfo])
					}
					object getConnectionInfo {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, instancesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): getConnectionInfo = new getConnectionInfo(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/instances/${instancesId}/connectionInfo").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[getConnectionInfo, Future[Schema.ConnectionInfo]] = (fun: getConnectionInfo) => fun.apply()
					}
					/** Restart an Instance in a cluster. Imperative only. */
					class restart(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withRestartInstanceRequest(body: Schema.RestartInstanceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object restart {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): restart = new restart(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/instances/${instancesId}:restart").addQueryStringParameters("name" -> name.toString))
					}
					/** Updates the parameters of a single Instance. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Field mask is used to specify the fields to be overwritten in the Instance resource by the update. The fields specified in the update_mask are relative to the resource, not the full request. A field will be overwritten if it is in the mask. If the user does not provide a mask then all fields will be overwritten.<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Optional. If set, performs request validation, for example, permission checks and any other type of validation, but does not actually execute the create request. */
						def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						/** Optional. If set to true, update succeeds even if instance is not found. In that case, a new instance is created and `update_mask` is ignored. */
						def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
						/** Perform the request */
						def withInstance(body: Schema.Instance) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/instances/${instancesId}").addQueryStringParameters("name" -> name.toString))
					}
					/** Forces a Failover for a highly available instance. Failover promotes the HA standby instance as the new primary. Imperative only. */
					class failover(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withFailoverInstanceRequest(body: Schema.FailoverInstanceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object failover {
						def apply(projectsId :PlayApi, locationsId :PlayApi, clustersId :PlayApi, instancesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): failover = new failover(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/clusters/${clustersId}/instances/${instancesId}:failover").addQueryStringParameters("name" -> name.toString))
					}
				}
			}
			object backups {
				/** Creates a new Backup in a given project and location. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set, the backend validates the request, but doesn't actually execute it. */
					def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Perform the request */
					def withBackup(body: Schema.Backup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, backupId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backups").addQueryStringParameters("parent" -> parent.toString, "backupId" -> backupId.toString))
				}
				/** Deletes a single Backup. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set, the backend validates the request, but doesn't actually execute it. */
					def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. The current etag of the Backup. If an etag is provided and does not match the current etag of the Backup, deletion will be blocked and an ABORTED error will be returned. */
					def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, backupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backups/${backupsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Gets details of a single Backup. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Backup]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Backup])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, backupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backups/${backupsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Backup]] = (fun: get) => fun.apply()
				}
				/** Updates the parameters of a single Backup. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Field mask is used to specify the fields to be overwritten in the Backup resource by the update. The fields specified in the update_mask are relative to the resource, not the full request. A field will be overwritten if it is in the mask. If the user does not provide a mask then all fields will be overwritten.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server ignores the request if it has already been completed. The server guarantees that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, ignores the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set, the backend validates the request, but doesn't actually execute it. */
					def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. If set to true, update succeeds even if instance is not found. In that case, a new backup is created and `update_mask` is ignored. */
					def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					/** Perform the request */
					def withBackup(body: Schema.Backup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, backupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backups/${backupsId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists Backups in a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBackupsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBackupsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backups").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.ListBackupsResponse]] = (fun: list) => fun.apply()
				}
			}
			object supportedDatabaseFlags {
				/** Lists SupportedDatabaseFlags for a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSupportedDatabaseFlagsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSupportedDatabaseFlagsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/supportedDatabaseFlags").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListSupportedDatabaseFlagsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
