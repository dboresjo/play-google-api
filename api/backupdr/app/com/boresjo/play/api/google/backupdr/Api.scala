package com.boresjo.play.api.google.backupdr

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://backupdr.googleapis.com/"

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
			object backupPlans {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and t he request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withBackupPlan(body: Schema.BackupPlan) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, backupPlanId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupPlans")).addQueryStringParameters("parent" -> parent.toString, "backupPlanId" -> backupPlanId.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BackupPlan]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.BackupPlan])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, backupPlansId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupPlans/${backupPlansId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.BackupPlan]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBackupPlansResponse]) {
					/** Optional. The maximum number of `BackupPlans` to return in a single response. If not specified, a default value will be chosen by the service. Note that the response may include a partial list and a caller should only rely on the response's next_page_token to determine if there are more instances left to be queried.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. The value of next_page_token received from a previous `ListBackupPlans` call. Provide this to retrieve the subsequent page in a multi-page list of results. When paginating, all other parameters provided to `ListBackupPlans` must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Field match expression used to filter the results. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Field by which to sort the results. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListBackupPlansResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupPlans")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListBackupPlansResponse]] = (fun: list) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, backupPlansId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupPlans/${backupPlansId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
			}
			object backupVaults {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, backupVaultsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults/${backupVaultsId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. Only validate the request, but do not perform mutations. The default is 'false'. */
					def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					def withBackupVault(body: Schema.BackupVault) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, backupVaultId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults")).addQueryStringParameters("parent" -> parent.toString, "backupVaultId" -> backupVaultId.toString))
				}
				class fetchUsable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FetchUsableBackupVaultsResponse]) {
					/** Optional. Requested page size. Server may return fewer items than requested. If unspecified, server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new fetchUsable(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token identifying a page of results the server should return. */
					def withPageToken(pageToken: String) = new fetchUsable(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Filtering results. */
					def withFilter(filter: String) = new fetchUsable(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Hint for how to order the results. */
					def withOrderBy(orderBy: String) = new fetchUsable(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.FetchUsableBackupVaultsResponse])
				}
				object fetchUsable {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): fetchUsable = new fetchUsable(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults:fetchUsable")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[fetchUsable, Future[Schema.FetchUsableBackupVaultsResponse]] = (fun: fetchUsable) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set to true, any data source from this backup vault will also be deleted. */
					def withForce(force: Boolean) = new delete(req.addQueryStringParameters("force" -> force.toString))
					/** Optional. Only validate the request, but do not perform mutations. The default is 'false'. */
					def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. If true and the BackupVault is not found, the request will succeed but no action will be taken. */
					def withAllowMissing(allowMissing: Boolean) = new delete(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					/** Optional. If set to true, backupvault deletion will proceed even if there are backup plans referencing the backupvault. The default is 'false'. */
					def withIgnoreBackupPlanReferences(ignoreBackupPlanReferences: Boolean) = new delete(req.addQueryStringParameters("ignoreBackupPlanReferences" -> ignoreBackupPlanReferences.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, backupVaultsId :PlayApi, name: String, etag: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults/${backupVaultsId}")).addQueryStringParameters("name" -> name.toString, "etag" -> etag.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BackupVault]) {
					/** Optional. Reserved for future use to provide a BASIC & FULL view of Backup Vault<br>Possible values:<br>BACKUP_VAULT_VIEW_UNSPECIFIED: If the value is not set, the default 'FULL' view is used.<br>BACKUP_VAULT_VIEW_BASIC: Includes basic data about the Backup Vault, but not the full contents.<br>BACKUP_VAULT_VIEW_FULL: Includes all data about the Backup Vault. This is the default value (for both ListBackupVaults and GetBackupVault). */
					def withView(view: String) = new get(req.addQueryStringParameters("view" -> view.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.BackupVault])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, backupVaultsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults/${backupVaultsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.BackupVault]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. Only validate the request, but do not perform mutations. The default is 'false'. */
					def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. If set to true, will not check plan duration against backup vault enforcement duration. */
					def withForce(force: Boolean) = new patch(req.addQueryStringParameters("force" -> force.toString))
					def withBackupVault(body: Schema.BackupVault) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, backupVaultsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults/${backupVaultsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBackupVaultsResponse]) {
					/** Optional. Requested page size. Server may return fewer items than requested. If unspecified, server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token identifying a page of results the server should return. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Filtering results. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Hint for how to order the results. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Optional. Reserved for future use to provide a BASIC & FULL view of Backup Vault.<br>Possible values:<br>BACKUP_VAULT_VIEW_UNSPECIFIED: If the value is not set, the default 'FULL' view is used.<br>BACKUP_VAULT_VIEW_BASIC: Includes basic data about the Backup Vault, but not the full contents.<br>BACKUP_VAULT_VIEW_FULL: Includes all data about the Backup Vault. This is the default value (for both ListBackupVaults and GetBackupVault). */
					def withView(view: String) = new list(req.addQueryStringParameters("view" -> view.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListBackupVaultsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListBackupVaultsResponse]] = (fun: list) => fun.apply()
				}
				object dataSources {
					class setInternalStatus(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetInternalStatusRequest(body: Schema.SetInternalStatusRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object setInternalStatus {
						def apply(projectsId :PlayApi, locationsId :PlayApi, backupVaultsId :PlayApi, dataSourcesId :PlayApi, dataSource: String)(using auth: AuthToken, ec: ExecutionContext): setInternalStatus = new setInternalStatus(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults/${backupVaultsId}/dataSources/${dataSourcesId}:setInternalStatus")).addQueryStringParameters("dataSource" -> dataSource.toString))
					}
					class finalizeBackup(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withFinalizeBackupRequest(body: Schema.FinalizeBackupRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object finalizeBackup {
						def apply(projectsId :PlayApi, locationsId :PlayApi, backupVaultsId :PlayApi, dataSourcesId :PlayApi, dataSource: String)(using auth: AuthToken, ec: ExecutionContext): finalizeBackup = new finalizeBackup(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults/${backupVaultsId}/dataSources/${dataSourcesId}:finalizeBackup")).addQueryStringParameters("dataSource" -> dataSource.toString))
					}
					class fetchAccessToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withFetchAccessTokenRequest(body: Schema.FetchAccessTokenRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.FetchAccessTokenResponse])
					}
					object fetchAccessToken {
						def apply(projectsId :PlayApi, locationsId :PlayApi, backupVaultsId :PlayApi, dataSourcesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): fetchAccessToken = new fetchAccessToken(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults/${backupVaultsId}/dataSources/${dataSourcesId}:fetchAccessToken")).addQueryStringParameters("name" -> name.toString))
					}
					class remove(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withRemoveDataSourceRequest(body: Schema.RemoveDataSourceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object remove {
						def apply(projectsId :PlayApi, locationsId :PlayApi, backupVaultsId :PlayApi, dataSourcesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): remove = new remove(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults/${backupVaultsId}/dataSources/${dataSourcesId}:remove")).addQueryStringParameters("name" -> name.toString))
					}
					class abandonBackup(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withAbandonBackupRequest(body: Schema.AbandonBackupRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object abandonBackup {
						def apply(projectsId :PlayApi, locationsId :PlayApi, backupVaultsId :PlayApi, dataSourcesId :PlayApi, dataSource: String)(using auth: AuthToken, ec: ExecutionContext): abandonBackup = new abandonBackup(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults/${backupVaultsId}/dataSources/${dataSourcesId}:abandonBackup")).addQueryStringParameters("dataSource" -> dataSource.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DataSource]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.DataSource])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, backupVaultsId :PlayApi, dataSourcesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults/${backupVaultsId}/dataSources/${dataSourcesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.DataSource]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Optional. Enable upsert. */
						def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
						def withDataSource(body: Schema.DataSource) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, backupVaultsId :PlayApi, dataSourcesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults/${backupVaultsId}/dataSources/${dataSourcesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDataSourcesResponse]) {
						/** Optional. Requested page size. Server may return fewer items than requested. If unspecified, server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A token identifying a page of results the server should return. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Filtering results. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. Hint for how to order the results. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListDataSourcesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, backupVaultsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults/${backupVaultsId}/dataSources")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListDataSourcesResponse]] = (fun: list) => fun.apply()
					}
					class initiateBackup(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withInitiateBackupRequest(body: Schema.InitiateBackupRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.InitiateBackupResponse])
					}
					object initiateBackup {
						def apply(projectsId :PlayApi, locationsId :PlayApi, backupVaultsId :PlayApi, dataSourcesId :PlayApi, dataSource: String)(using auth: AuthToken, ec: ExecutionContext): initiateBackup = new initiateBackup(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults/${backupVaultsId}/dataSources/${dataSourcesId}:initiateBackup")).addQueryStringParameters("dataSource" -> dataSource.toString))
					}
					object backups {
						class restore(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withRestoreBackupRequest(body: Schema.RestoreBackupRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
						}
						object restore {
							def apply(projectsId :PlayApi, locationsId :PlayApi, backupVaultsId :PlayApi, dataSourcesId :PlayApi, backupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): restore = new restore(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults/${backupVaultsId}/dataSources/${dataSourcesId}/backups/${backupsId}:restore")).addQueryStringParameters("name" -> name.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
							/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
							def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
							def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, backupVaultsId :PlayApi, dataSourcesId :PlayApi, backupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults/${backupVaultsId}/dataSources/${dataSourcesId}/backups/${backupsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Backup]) {
							/** Optional. Reserved for future use to provide a BASIC & FULL view of Backup resource.<br>Possible values:<br>BACKUP_VIEW_UNSPECIFIED: If the value is not set, the default 'FULL' view is used.<br>BACKUP_VIEW_BASIC: Includes basic data about the Backup, but not the full contents.<br>BACKUP_VIEW_FULL: Includes all data about the Backup. This is the default value (for both ListBackups and GetBackup). */
							def withView(view: String) = new get(req.addQueryStringParameters("view" -> view.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.Backup])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, backupVaultsId :PlayApi, dataSourcesId :PlayApi, backupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults/${backupVaultsId}/dataSources/${dataSourcesId}/backups/${backupsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.Backup]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
							def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
							def withBackup(body: Schema.Backup) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, backupVaultsId :PlayApi, dataSourcesId :PlayApi, backupsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults/${backupVaultsId}/dataSources/${dataSourcesId}/backups/${backupsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBackupsResponse]) {
							/** Optional. Requested page size. Server may return fewer items than requested. If unspecified, server will pick an appropriate default.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. A token identifying a page of results the server should return. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Filtering results. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional. Hint for how to order the results. */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							/** Optional. Reserved for future use to provide a BASIC & FULL view of Backup resource.<br>Possible values:<br>BACKUP_VIEW_UNSPECIFIED: If the value is not set, the default 'FULL' view is used.<br>BACKUP_VIEW_BASIC: Includes basic data about the Backup, but not the full contents.<br>BACKUP_VIEW_FULL: Includes all data about the Backup. This is the default value (for both ListBackups and GetBackup). */
							def withView(view: String) = new list(req.addQueryStringParameters("view" -> view.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.ListBackupsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, backupVaultsId :PlayApi, dataSourcesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupVaults/${backupVaultsId}/dataSources/${dataSourcesId}/backups")).addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.ListBackupsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object managementServers {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, managementServersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/managementServers/${managementServersId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, managementServersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/managementServers/${managementServersId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, managementServersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/managementServers/${managementServersId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ManagementServer]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ManagementServer])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, managementServersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/managementServers/${managementServersId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ManagementServer]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListManagementServersResponse]) {
					/** Optional. Requested page size. Server may return fewer items than requested. If unspecified, server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token identifying a page of results the server should return. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Filtering results. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Hint for how to order the results. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListManagementServersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/managementServers")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListManagementServersResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withManagementServer(body: Schema.ManagementServer) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, managementServerId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/managementServers")).addQueryStringParameters("parent" -> parent.toString, "managementServerId" -> managementServerId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, managementServersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/managementServers/${managementServersId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
			}
			object backupPlanAssociations {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and t he request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withBackupPlanAssociation(body: Schema.BackupPlanAssociation) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, backupPlanAssociationId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupPlanAssociations")).addQueryStringParameters("parent" -> parent.toString, "backupPlanAssociationId" -> backupPlanAssociationId.toString))
				}
				class triggerBackup(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTriggerBackupRequest(body: Schema.TriggerBackupRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object triggerBackup {
					def apply(projectsId :PlayApi, locationsId :PlayApi, backupPlanAssociationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): triggerBackup = new triggerBackup(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupPlanAssociations/${backupPlanAssociationsId}:triggerBackup")).addQueryStringParameters("name" -> name.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, backupPlanAssociationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupPlanAssociations/${backupPlanAssociationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BackupPlanAssociation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.BackupPlanAssociation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, backupPlanAssociationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupPlanAssociations/${backupPlanAssociationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.BackupPlanAssociation]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBackupPlanAssociationsResponse]) {
					/** Optional. Requested page size. Server may return fewer items than requested. If unspecified, server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token identifying a page of results the server should return. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Filtering results */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListBackupPlanAssociationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/backupPlanAssociations")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListBackupPlanAssociationsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
