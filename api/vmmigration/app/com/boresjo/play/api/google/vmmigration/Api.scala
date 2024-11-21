package com.boresjo.play.api.google.vmmigration

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://vmmigration.googleapis.com/"

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
			object groups {
				class removeGroupMigration(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRemoveGroupMigrationRequest(body: Schema.RemoveGroupMigrationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object removeGroupMigration {
					def apply(projectsId :PlayApi, locationsId :PlayApi, groupsId :PlayApi, group: String)(using auth: AuthToken, ec: ExecutionContext): removeGroupMigration = new removeGroupMigration(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/groups/${groupsId}:removeGroupMigration")).addQueryStringParameters("group" -> group.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGroup(body: Schema.Group) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, groupId: String, requestId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/groups")).addQueryStringParameters("parent" -> parent.toString, "groupId" -> groupId.toString, "requestId" -> requestId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, groupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/groups/${groupsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class addGroupMigration(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAddGroupMigrationRequest(body: Schema.AddGroupMigrationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object addGroupMigration {
					def apply(projectsId :PlayApi, locationsId :PlayApi, groupsId :PlayApi, group: String)(using auth: AuthToken, ec: ExecutionContext): addGroupMigration = new addGroupMigration(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/groups/${groupsId}:addGroupMigration")).addQueryStringParameters("group" -> group.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Group]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Group])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, groupsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/groups/${groupsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Group]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGroup(body: Schema.Group) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, groupsId :PlayApi, name: String, updateMask: String, requestId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/groups/${groupsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "requestId" -> requestId.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListGroupsResponse]) {
					/** Optional. The maximum number of groups to return. The service may return fewer than this value. If unspecified, at most 500 groups will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. The filter request. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. the order by fields for the result. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListGroupsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/groups")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListGroupsResponse]] = (fun: list) => fun.apply()
				}
			}
			object imageImports {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					def withImageImport(body: Schema.ImageImport) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, imageImportId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/imageImports")).addQueryStringParameters("parent" -> parent.toString, "imageImportId" -> imageImportId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and t he request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, imageImportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/imageImports/${imageImportsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ImageImport]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ImageImport])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, imageImportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/imageImports/${imageImportsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ImageImport]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListImageImportsResponse]) {
					/** Optional. The maximum number of targets to return. The service may return fewer than this value. If unspecified, at most 500 targets will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A page token, received from a previous `ListImageImports` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListImageImports` must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The filter request (according to AIP-160). */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. The order by fields for the result (according to AIP-132). Currently ordering is only possible by "name" field. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListImageImportsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/imageImports")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListImageImportsResponse]] = (fun: list) => fun.apply()
				}
				object imageImportJobs {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListImageImportJobsResponse]) {
						/** Optional. The maximum number of targets to return. The service may return fewer than this value. If unspecified, at most 500 targets will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token, received from a previous `ListImageImportJobs` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListImageImportJobs` must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The filter request (according to AIP-160). */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. The order by fields for the result (according to AIP-132). Currently ordering is only possible by "name" field. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListImageImportJobsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, imageImportsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/imageImports/${imageImportsId}/imageImportJobs")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListImageImportJobsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ImageImportJob]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ImageImportJob])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, imageImportsId :PlayApi, imageImportJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/imageImports/${imageImportsId}/imageImportJobs/${imageImportJobsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.ImageImportJob]] = (fun: get) => fun.apply()
					}
					class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCancelImageImportJobRequest(body: Schema.CancelImageImportJobRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object cancel {
						def apply(projectsId :PlayApi, locationsId :PlayApi, imageImportsId :PlayApi, imageImportJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/imageImports/${imageImportsId}/imageImportJobs/${imageImportJobsId}:cancel")).addQueryStringParameters("name" -> name.toString))
					}
				}
			}
			object targetProjects {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTargetProject(body: Schema.TargetProject) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, targetProjectId: String, requestId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/targetProjects")).addQueryStringParameters("parent" -> parent.toString, "targetProjectId" -> targetProjectId.toString, "requestId" -> requestId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, targetProjectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/targetProjects/${targetProjectsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TargetProject]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.TargetProject])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, targetProjectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/targetProjects/${targetProjectsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.TargetProject]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTargetProject(body: Schema.TargetProject) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, targetProjectsId :PlayApi, name: String, updateMask: String, requestId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/targetProjects/${targetProjectsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "requestId" -> requestId.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTargetProjectsResponse]) {
					/** Optional. The maximum number of targets to return. The service may return fewer than this value. If unspecified, at most 500 targets will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. The filter request. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. the order by fields for the result. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListTargetProjectsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/targetProjects")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListTargetProjectsResponse]] = (fun: list) => fun.apply()
				}
			}
			object sources {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSource(body: Schema.Source) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, sourceId: String, requestId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources")).addQueryStringParameters("parent" -> parent.toString, "sourceId" -> sourceId.toString, "requestId" -> requestId.toString))
				}
				class fetchInventory(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FetchInventoryResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.FetchInventoryResponse])
				}
				object fetchInventory {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, source: String, forceRefresh: Boolean, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): fetchInventory = new fetchInventory(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}:fetchInventory")).addQueryStringParameters("source" -> source.toString, "forceRefresh" -> forceRefresh.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[fetchInventory, Future[Schema.FetchInventoryResponse]] = (fun: fetchInventory) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSource(body: Schema.Source) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, name: String, updateMask: String, requestId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "requestId" -> requestId.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSourcesResponse]) {
					/** Optional. The maximum number of sources to return. The service may return fewer than this value. If unspecified, at most 500 sources will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. The filter request. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. the order by fields for the result. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListSourcesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListSourcesResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Source]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Source])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Source]] = (fun: get) => fun.apply()
				}
				object utilizationReports {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListUtilizationReportsResponse]) {
						/** Optional. The level of details of each report. Defaults to BASIC.<br>Possible values:<br>UTILIZATION_REPORT_VIEW_UNSPECIFIED: The default / unset value. The API will default to FULL on single report request and BASIC for multiple reports request.<br>BASIC: Get the report metadata, without the list of VMs and their utilization info.<br>FULL: Include everything. */
						def withView(view: String) = new list(req.addQueryStringParameters("view" -> view.toString))
						/** Optional. The maximum number of reports to return. The service may return fewer than this value. If unspecified, at most 500 reports will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. The filter request. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. the order by fields for the result. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListUtilizationReportsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/utilizationReports")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListUtilizationReportsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.UtilizationReport]) {
						/** Optional. The level of details of the report. Defaults to FULL<br>Possible values:<br>UTILIZATION_REPORT_VIEW_UNSPECIFIED: The default / unset value. The API will default to FULL on single report request and BASIC for multiple reports request.<br>BASIC: Get the report metadata, without the list of VMs and their utilization info.<br>FULL: Include everything. */
						def withView(view: String) = new get(req.addQueryStringParameters("view" -> view.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.UtilizationReport])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, utilizationReportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/utilizationReports/${utilizationReportsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.UtilizationReport]] = (fun: get) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withUtilizationReport(body: Schema.UtilizationReport) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, parent: String, utilizationReportId: String, requestId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/utilizationReports")).addQueryStringParameters("parent" -> parent.toString, "utilizationReportId" -> utilizationReportId.toString, "requestId" -> requestId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, utilizationReportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/utilizationReports/${utilizationReportsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
				}
				object datacenterConnectors {
					class upgradeAppliance(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withUpgradeApplianceRequest(body: Schema.UpgradeApplianceRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object upgradeAppliance {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, datacenterConnectorsId :PlayApi, datacenterConnector: String)(using auth: AuthToken, ec: ExecutionContext): upgradeAppliance = new upgradeAppliance(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/datacenterConnectors/${datacenterConnectorsId}:upgradeAppliance")).addQueryStringParameters("datacenterConnector" -> datacenterConnector.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withDatacenterConnector(body: Schema.DatacenterConnector) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, parent: String, datacenterConnectorId: String, requestId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/datacenterConnectors")).addQueryStringParameters("parent" -> parent.toString, "datacenterConnectorId" -> datacenterConnectorId.toString, "requestId" -> requestId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, datacenterConnectorsId :PlayApi, name: String, requestId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/datacenterConnectors/${datacenterConnectorsId}")).addQueryStringParameters("name" -> name.toString, "requestId" -> requestId.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DatacenterConnector]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.DatacenterConnector])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, datacenterConnectorsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/datacenterConnectors/${datacenterConnectorsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.DatacenterConnector]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDatacenterConnectorsResponse]) {
						/** Optional. The maximum number of connectors to return. The service may return fewer than this value. If unspecified, at most 500 sources will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. The filter request. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. the order by fields for the result. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListDatacenterConnectorsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/datacenterConnectors")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListDatacenterConnectorsResponse]] = (fun: list) => fun.apply()
					}
				}
				object migratingVms {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withMigratingVm(body: Schema.MigratingVm) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, parent: String, migratingVmId: String, requestId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms")).addQueryStringParameters("parent" -> parent.toString, "migratingVmId" -> migratingVmId.toString, "requestId" -> requestId.toString))
					}
					class finalizeMigration(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withFinalizeMigrationRequest(body: Schema.FinalizeMigrationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object finalizeMigration {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, migratingVm: String)(using auth: AuthToken, ec: ExecutionContext): finalizeMigration = new finalizeMigration(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}:finalizeMigration")).addQueryStringParameters("migratingVm" -> migratingVm.toString))
					}
					class pauseMigration(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withPauseMigrationRequest(body: Schema.PauseMigrationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object pauseMigration {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, migratingVm: String)(using auth: AuthToken, ec: ExecutionContext): pauseMigration = new pauseMigration(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}:pauseMigration")).addQueryStringParameters("migratingVm" -> migratingVm.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.MigratingVm]) {
						/** Optional. The level of details of the migrating VM.<br>Possible values:<br>MIGRATING_VM_VIEW_UNSPECIFIED: View is unspecified. The API will fallback to the default value.<br>MIGRATING_VM_VIEW_BASIC: Get the migrating VM basic details. The basic details do not include the recent clone jobs and recent cutover jobs lists.<br>MIGRATING_VM_VIEW_FULL: Include everything. */
						def withView(view: String) = new get(req.addQueryStringParameters("view" -> view.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.MigratingVm])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.MigratingVm]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withMigratingVm(body: Schema.MigratingVm) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, name: String, updateMask: String, requestId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "requestId" -> requestId.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListMigratingVmsResponse]) {
						/** Optional. The maximum number of migrating VMs to return. The service may return fewer than this value. If unspecified, at most 500 migrating VMs will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. The filter request. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. the order by fields for the result. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Optional. The level of details of each migrating VM.<br>Possible values:<br>MIGRATING_VM_VIEW_UNSPECIFIED: View is unspecified. The API will fallback to the default value.<br>MIGRATING_VM_VIEW_BASIC: Get the migrating VM basic details. The basic details do not include the recent clone jobs and recent cutover jobs lists.<br>MIGRATING_VM_VIEW_FULL: Include everything. */
						def withView(view: String) = new list(req.addQueryStringParameters("view" -> view.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListMigratingVmsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListMigratingVmsResponse]] = (fun: list) => fun.apply()
					}
					class startMigration(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withStartMigrationRequest(body: Schema.StartMigrationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object startMigration {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, migratingVm: String)(using auth: AuthToken, ec: ExecutionContext): startMigration = new startMigration(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}:startMigration")).addQueryStringParameters("migratingVm" -> migratingVm.toString))
					}
					class resumeMigration(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withResumeMigrationRequest(body: Schema.ResumeMigrationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
					}
					object resumeMigration {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, migratingVm: String)(using auth: AuthToken, ec: ExecutionContext): resumeMigration = new resumeMigration(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}:resumeMigration")).addQueryStringParameters("migratingVm" -> migratingVm.toString))
					}
					object cloneJobs {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withCloneJob(body: Schema.CloneJob) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, parent: String, cloneJobId: String, requestId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}/cloneJobs")).addQueryStringParameters("parent" -> parent.toString, "cloneJobId" -> cloneJobId.toString, "requestId" -> requestId.toString))
						}
						class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withCancelCloneJobRequest(body: Schema.CancelCloneJobRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
						}
						object cancel {
							def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, cloneJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}/cloneJobs/${cloneJobsId}:cancel")).addQueryStringParameters("name" -> name.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCloneJobsResponse]) {
							/** Optional. The maximum number of clone jobs to return. The service may return fewer than this value. If unspecified, at most 500 clone jobs will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. The filter request. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional. the order by fields for the result. */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.ListCloneJobsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}/cloneJobs")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListCloneJobsResponse]] = (fun: list) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CloneJob]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.CloneJob])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, cloneJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}/cloneJobs/${cloneJobsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.CloneJob]] = (fun: get) => fun.apply()
						}
					}
					object cutoverJobs {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withCutoverJob(body: Schema.CutoverJob) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, parent: String, cutoverJobId: String, requestId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}/cutoverJobs")).addQueryStringParameters("parent" -> parent.toString, "cutoverJobId" -> cutoverJobId.toString, "requestId" -> requestId.toString))
						}
						class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withCancelCutoverJobRequest(body: Schema.CancelCutoverJobRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
						}
						object cancel {
							def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, cutoverJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}/cutoverJobs/${cutoverJobsId}:cancel")).addQueryStringParameters("name" -> name.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCutoverJobsResponse]) {
							/** Optional. The maximum number of cutover jobs to return. The service may return fewer than this value. If unspecified, at most 500 cutover jobs will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. The filter request. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional. the order by fields for the result. */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.ListCutoverJobsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}/cutoverJobs")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListCutoverJobsResponse]] = (fun: list) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CutoverJob]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.CutoverJob])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, cutoverJobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}/cutoverJobs/${cutoverJobsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.CutoverJob]] = (fun: get) => fun.apply()
						}
					}
					object replicationCycles {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListReplicationCyclesResponse]) {
							/** Optional. The maximum number of replication cycles to return. The service may return fewer than this value. If unspecified, at most 100 migrating VMs will be returned. The maximum value is 100; values above 100 will be coerced to 100.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. The filter request. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional. the order by fields for the result. */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.ListReplicationCyclesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}/replicationCycles")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListReplicationCyclesResponse]] = (fun: list) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ReplicationCycle]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.ReplicationCycle])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, replicationCyclesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}/replicationCycles/${replicationCyclesId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.ReplicationCycle]] = (fun: get) => fun.apply()
						}
					}
				}
			}
		}
	}
}
