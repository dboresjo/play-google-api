package com.boresjo.play.api.google.vmmigration

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

	private val BASE_URL = "https://vmmigration.googleapis.com/"

	object projects {
		object locations {
			/** Gets information about a location. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
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
			object groups {
				/** Removes a MigratingVm from a Group. */
				class removeGroupMigration(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRemoveGroupMigrationRequest(body: Schema.RemoveGroupMigrationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object removeGroupMigration {
					def apply(projectsId :PlayApi, locationsId :PlayApi, groupsId :PlayApi, group: String)(using signer: RequestSigner, ec: ExecutionContext): removeGroupMigration = new removeGroupMigration(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/groups/${groupsId}:removeGroupMigration").addQueryStringParameters("group" -> group.toString))
				}
				/** Creates a new Group in a given project and location. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGroup(body: Schema.Group) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, groupId: String, requestId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/groups").addQueryStringParameters("parent" -> parent.toString, "groupId" -> groupId.toString, "requestId" -> requestId.toString))
				}
				/** Deletes a single Group. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, groupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/groups/${groupsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Adds a MigratingVm to a Group. */
				class addGroupMigration(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withAddGroupMigrationRequest(body: Schema.AddGroupMigrationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object addGroupMigration {
					def apply(projectsId :PlayApi, locationsId :PlayApi, groupsId :PlayApi, group: String)(using signer: RequestSigner, ec: ExecutionContext): addGroupMigration = new addGroupMigration(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/groups/${groupsId}:addGroupMigration").addQueryStringParameters("group" -> group.toString))
				}
				/** Gets details of a single Group. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Group]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Group])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, groupsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/groups/${groupsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Group]] = (fun: get) => fun.apply()
				}
				/** Updates the parameters of a single Group. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGroup(body: Schema.Group) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, groupsId :PlayApi, name: String, updateMask: String, requestId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/groups/${groupsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "requestId" -> requestId.toString))
				}
				/** Lists Groups in a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListGroupsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum number of groups to return. The service may return fewer than this value. If unspecified, at most 500 groups will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. The filter request. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. the order by fields for the result. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListGroupsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/groups").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListGroupsResponse]] = (fun: list) => fun.apply()
				}
			}
			object imageImports {
				/** Creates a new ImageImport in a given project. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def withImageImport(body: Schema.ImageImport) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, imageImportId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/imageImports").addQueryStringParameters("parent" -> parent.toString, "imageImportId" -> imageImportId.toString))
				}
				/** Deletes a single ImageImport. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and t he request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, imageImportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/imageImports/${imageImportsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Gets details of a single ImageImport. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ImageImport]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ImageImport])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, imageImportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/imageImports/${imageImportsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ImageImport]] = (fun: get) => fun.apply()
				}
				/** Lists ImageImports in a given project. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListImageImportsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum number of targets to return. The service may return fewer than this value. If unspecified, at most 500 targets will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A page token, received from a previous `ListImageImports` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListImageImports` must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The filter request (according to AIP-160). */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. The order by fields for the result (according to AIP-132). Currently ordering is only possible by "name" field. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListImageImportsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/imageImports").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListImageImportsResponse]] = (fun: list) => fun.apply()
				}
				object imageImportJobs {
					/** Lists ImageImportJobs in a given project. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListImageImportJobsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum number of targets to return. The service may return fewer than this value. If unspecified, at most 500 targets will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token, received from a previous `ListImageImportJobs` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListImageImportJobs` must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The filter request (according to AIP-160). */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. The order by fields for the result (according to AIP-132). Currently ordering is only possible by "name" field. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListImageImportJobsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, imageImportsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/imageImports/${imageImportsId}/imageImportJobs").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListImageImportJobsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets details of a single ImageImportJob. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ImageImportJob]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ImageImportJob])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, imageImportsId :PlayApi, imageImportJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/imageImports/${imageImportsId}/imageImportJobs/${imageImportJobsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.ImageImportJob]] = (fun: get) => fun.apply()
					}
					/** Initiates the cancellation of a running clone job. */
					class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withCancelImageImportJobRequest(body: Schema.CancelImageImportJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object cancel {
						def apply(projectsId :PlayApi, locationsId :PlayApi, imageImportsId :PlayApi, imageImportJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/imageImports/${imageImportsId}/imageImportJobs/${imageImportJobsId}:cancel").addQueryStringParameters("name" -> name.toString))
					}
				}
			}
			object targetProjects {
				/** Creates a new TargetProject in a given project. NOTE: TargetProject is a global resource; hence the only supported value for location is `global`. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTargetProject(body: Schema.TargetProject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, targetProjectId: String, requestId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/targetProjects").addQueryStringParameters("parent" -> parent.toString, "targetProjectId" -> targetProjectId.toString, "requestId" -> requestId.toString))
				}
				/** Deletes a single TargetProject. NOTE: TargetProject is a global resource; hence the only supported value for location is `global`. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, targetProjectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/targetProjects/${targetProjectsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Gets details of a single TargetProject. NOTE: TargetProject is a global resource; hence the only supported value for location is `global`. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TargetProject]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TargetProject])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, targetProjectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/targetProjects/${targetProjectsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.TargetProject]] = (fun: get) => fun.apply()
				}
				/** Updates the parameters of a single TargetProject. NOTE: TargetProject is a global resource; hence the only supported value for location is `global`. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTargetProject(body: Schema.TargetProject) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, targetProjectsId :PlayApi, name: String, updateMask: String, requestId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/targetProjects/${targetProjectsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "requestId" -> requestId.toString))
				}
				/** Lists TargetProjects in a given project. NOTE: TargetProject is a global resource; hence the only supported value for location is `global`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTargetProjectsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum number of targets to return. The service may return fewer than this value. If unspecified, at most 500 targets will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. The filter request. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. the order by fields for the result. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTargetProjectsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/targetProjects").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListTargetProjectsResponse]] = (fun: list) => fun.apply()
				}
			}
			object sources {
				/** Creates a new Source in a given project and location. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSource(body: Schema.Source) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, sourceId: String, requestId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources").addQueryStringParameters("parent" -> parent.toString, "sourceId" -> sourceId.toString, "requestId" -> requestId.toString))
				}
				/** List remote source's inventory of VMs. The remote source is the onprem vCenter (remote in the sense it's not in Compute Engine). The inventory describes the list of existing VMs in that source. Note that this operation lists the VMs on the remote source, as opposed to listing the MigratingVms resources in the vmmigration service. */
				class fetchInventory(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FetchInventoryResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FetchInventoryResponse])
				}
				object fetchInventory {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, source: String, forceRefresh: Boolean, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): fetchInventory = new fetchInventory(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}:fetchInventory").addQueryStringParameters("source" -> source.toString, "forceRefresh" -> forceRefresh.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[fetchInventory, Future[Schema.FetchInventoryResponse]] = (fun: fetchInventory) => fun.apply()
				}
				/** Deletes a single Source. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Updates the parameters of a single Source. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSource(body: Schema.Source) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, name: String, updateMask: String, requestId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "requestId" -> requestId.toString))
				}
				/** Lists Sources in a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSourcesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum number of sources to return. The service may return fewer than this value. If unspecified, at most 500 sources will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. The filter request. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. the order by fields for the result. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSourcesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListSourcesResponse]] = (fun: list) => fun.apply()
				}
				/** Gets details of a single Source. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Source]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Source])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Source]] = (fun: get) => fun.apply()
				}
				object utilizationReports {
					/** Lists Utilization Reports of the given Source. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListUtilizationReportsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The level of details of each report. Defaults to BASIC.<br>Possible values:<br>UTILIZATION_REPORT_VIEW_UNSPECIFIED: The default / unset value. The API will default to FULL on single report request and BASIC for multiple reports request.<br>BASIC: Get the report metadata, without the list of VMs and their utilization info.<br>FULL: Include everything. */
						def withView(view: String) = new list(req.addQueryStringParameters("view" -> view.toString))
						/** Optional. The maximum number of reports to return. The service may return fewer than this value. If unspecified, at most 500 reports will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. The filter request. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. the order by fields for the result. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListUtilizationReportsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/utilizationReports").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListUtilizationReportsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets a single Utilization Report. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.UtilizationReport]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The level of details of the report. Defaults to FULL<br>Possible values:<br>UTILIZATION_REPORT_VIEW_UNSPECIFIED: The default / unset value. The API will default to FULL on single report request and BASIC for multiple reports request.<br>BASIC: Get the report metadata, without the list of VMs and their utilization info.<br>FULL: Include everything. */
						def withView(view: String) = new get(req.addQueryStringParameters("view" -> view.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.UtilizationReport])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, utilizationReportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/utilizationReports/${utilizationReportsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.UtilizationReport]] = (fun: get) => fun.apply()
					}
					/** Creates a new UtilizationReport. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withUtilizationReport(body: Schema.UtilizationReport) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, parent: String, utilizationReportId: String, requestId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/utilizationReports").addQueryStringParameters("parent" -> parent.toString, "utilizationReportId" -> utilizationReportId.toString, "requestId" -> requestId.toString))
					}
					/** Deletes a single Utilization Report. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, utilizationReportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/utilizationReports/${utilizationReportsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
				}
				object datacenterConnectors {
					/** Upgrades the appliance relate to this DatacenterConnector to the in-place updateable version. */
					class upgradeAppliance(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withUpgradeApplianceRequest(body: Schema.UpgradeApplianceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object upgradeAppliance {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, datacenterConnectorsId :PlayApi, datacenterConnector: String)(using signer: RequestSigner, ec: ExecutionContext): upgradeAppliance = new upgradeAppliance(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/datacenterConnectors/${datacenterConnectorsId}:upgradeAppliance").addQueryStringParameters("datacenterConnector" -> datacenterConnector.toString))
					}
					/** Creates a new DatacenterConnector in a given Source. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withDatacenterConnector(body: Schema.DatacenterConnector) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, parent: String, datacenterConnectorId: String, requestId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/datacenterConnectors").addQueryStringParameters("parent" -> parent.toString, "datacenterConnectorId" -> datacenterConnectorId.toString, "requestId" -> requestId.toString))
					}
					/** Deletes a single DatacenterConnector. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, datacenterConnectorsId :PlayApi, name: String, requestId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/datacenterConnectors/${datacenterConnectorsId}").addQueryStringParameters("name" -> name.toString, "requestId" -> requestId.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					/** Gets details of a single DatacenterConnector. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DatacenterConnector]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DatacenterConnector])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, datacenterConnectorsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/datacenterConnectors/${datacenterConnectorsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.DatacenterConnector]] = (fun: get) => fun.apply()
					}
					/** Lists DatacenterConnectors in a given Source. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDatacenterConnectorsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum number of connectors to return. The service may return fewer than this value. If unspecified, at most 500 sources will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. The filter request. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. the order by fields for the result. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDatacenterConnectorsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/datacenterConnectors").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListDatacenterConnectorsResponse]] = (fun: list) => fun.apply()
					}
				}
				object migratingVms {
					/** Creates a new MigratingVm in a given Source. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withMigratingVm(body: Schema.MigratingVm) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, parent: String, migratingVmId: String, requestId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms").addQueryStringParameters("parent" -> parent.toString, "migratingVmId" -> migratingVmId.toString, "requestId" -> requestId.toString))
					}
					/** Marks a migration as completed, deleting migration resources that are no longer being used. Only applicable after cutover is done. */
					class finalizeMigration(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withFinalizeMigrationRequest(body: Schema.FinalizeMigrationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object finalizeMigration {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, migratingVm: String)(using signer: RequestSigner, ec: ExecutionContext): finalizeMigration = new finalizeMigration(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}:finalizeMigration").addQueryStringParameters("migratingVm" -> migratingVm.toString))
					}
					/** Pauses a migration for a VM. If cycle tasks are running they will be cancelled, preserving source task data. Further replication cycles will not be triggered while the VM is paused. */
					class pauseMigration(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withPauseMigrationRequest(body: Schema.PauseMigrationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object pauseMigration {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, migratingVm: String)(using signer: RequestSigner, ec: ExecutionContext): pauseMigration = new pauseMigration(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}:pauseMigration").addQueryStringParameters("migratingVm" -> migratingVm.toString))
					}
					/** Deletes a single MigratingVm. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					/** Gets details of a single MigratingVm. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.MigratingVm]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The level of details of the migrating VM.<br>Possible values:<br>MIGRATING_VM_VIEW_UNSPECIFIED: View is unspecified. The API will fallback to the default value.<br>MIGRATING_VM_VIEW_BASIC: Get the migrating VM basic details. The basic details do not include the recent clone jobs and recent cutover jobs lists.<br>MIGRATING_VM_VIEW_FULL: Include everything. */
						def withView(view: String) = new get(req.addQueryStringParameters("view" -> view.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.MigratingVm])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.MigratingVm]] = (fun: get) => fun.apply()
					}
					/** Updates the parameters of a single MigratingVm. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withMigratingVm(body: Schema.MigratingVm) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, name: String, updateMask: String, requestId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "requestId" -> requestId.toString))
					}
					/** Lists MigratingVms in a given Source. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListMigratingVmsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The maximum number of migrating VMs to return. The service may return fewer than this value. If unspecified, at most 500 migrating VMs will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. The filter request. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Optional. the order by fields for the result. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Optional. The level of details of each migrating VM.<br>Possible values:<br>MIGRATING_VM_VIEW_UNSPECIFIED: View is unspecified. The API will fallback to the default value.<br>MIGRATING_VM_VIEW_BASIC: Get the migrating VM basic details. The basic details do not include the recent clone jobs and recent cutover jobs lists.<br>MIGRATING_VM_VIEW_FULL: Include everything. */
						def withView(view: String) = new list(req.addQueryStringParameters("view" -> view.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListMigratingVmsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListMigratingVmsResponse]] = (fun: list) => fun.apply()
					}
					/** Starts migration for a VM. Starts the process of uploading data and creating snapshots, in replication cycles scheduled by the policy. */
					class startMigration(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withStartMigrationRequest(body: Schema.StartMigrationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object startMigration {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, migratingVm: String)(using signer: RequestSigner, ec: ExecutionContext): startMigration = new startMigration(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}:startMigration").addQueryStringParameters("migratingVm" -> migratingVm.toString))
					}
					/** Resumes a migration for a VM. When called on a paused migration, will start the process of uploading data and creating snapshots; when called on a completed cut-over migration, will update the migration to active state and start the process of uploading data and creating snapshots. */
					class resumeMigration(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withResumeMigrationRequest(body: Schema.ResumeMigrationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object resumeMigration {
						def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, migratingVm: String)(using signer: RequestSigner, ec: ExecutionContext): resumeMigration = new resumeMigration(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}:resumeMigration").addQueryStringParameters("migratingVm" -> migratingVm.toString))
					}
					object cloneJobs {
						/** Initiates a Clone of a specific migrating VM. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withCloneJob(body: Schema.CloneJob) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, parent: String, cloneJobId: String, requestId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}/cloneJobs").addQueryStringParameters("parent" -> parent.toString, "cloneJobId" -> cloneJobId.toString, "requestId" -> requestId.toString))
						}
						/** Initiates the cancellation of a running clone job. */
						class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withCancelCloneJobRequest(body: Schema.CancelCloneJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
						}
						object cancel {
							def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, cloneJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}/cloneJobs/${cloneJobsId}:cancel").addQueryStringParameters("name" -> name.toString))
						}
						/** Lists the CloneJobs of a migrating VM. Only 25 most recent CloneJobs are listed. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCloneJobsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. The maximum number of clone jobs to return. The service may return fewer than this value. If unspecified, at most 500 clone jobs will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. The filter request. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional. the order by fields for the result. */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCloneJobsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}/cloneJobs").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListCloneJobsResponse]] = (fun: list) => fun.apply()
						}
						/** Gets details of a single CloneJob. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CloneJob]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CloneJob])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, cloneJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}/cloneJobs/${cloneJobsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.CloneJob]] = (fun: get) => fun.apply()
						}
					}
					object cutoverJobs {
						/** Initiates a Cutover of a specific migrating VM. The returned LRO is completed when the cutover job resource is created and the job is initiated. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withCutoverJob(body: Schema.CutoverJob) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, parent: String, cutoverJobId: String, requestId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}/cutoverJobs").addQueryStringParameters("parent" -> parent.toString, "cutoverJobId" -> cutoverJobId.toString, "requestId" -> requestId.toString))
						}
						/** Initiates the cancellation of a running cutover job. */
						class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withCancelCutoverJobRequest(body: Schema.CancelCutoverJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
						}
						object cancel {
							def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, cutoverJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}/cutoverJobs/${cutoverJobsId}:cancel").addQueryStringParameters("name" -> name.toString))
						}
						/** Lists the CutoverJobs of a migrating VM. Only 25 most recent CutoverJobs are listed. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCutoverJobsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. The maximum number of cutover jobs to return. The service may return fewer than this value. If unspecified, at most 500 cutover jobs will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. The filter request. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional. the order by fields for the result. */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCutoverJobsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}/cutoverJobs").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListCutoverJobsResponse]] = (fun: list) => fun.apply()
						}
						/** Gets details of a single CutoverJob. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CutoverJob]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CutoverJob])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, cutoverJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}/cutoverJobs/${cutoverJobsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.CutoverJob]] = (fun: get) => fun.apply()
						}
					}
					object replicationCycles {
						/** Lists ReplicationCycles in a given MigratingVM. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListReplicationCyclesResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. The maximum number of replication cycles to return. The service may return fewer than this value. If unspecified, at most 100 migrating VMs will be returned. The maximum value is 100; values above 100 will be coerced to 100.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. The filter request. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Optional. the order by fields for the result. */
							def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListReplicationCyclesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}/replicationCycles").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListReplicationCyclesResponse]] = (fun: list) => fun.apply()
						}
						/** Gets details of a single ReplicationCycle. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ReplicationCycle]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ReplicationCycle])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, sourcesId :PlayApi, migratingVmsId :PlayApi, replicationCyclesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/sources/${sourcesId}/migratingVms/${migratingVmsId}/replicationCycles/${replicationCyclesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.ReplicationCycle]] = (fun: get) => fun.apply()
						}
					}
				}
			}
		}
	}
}
