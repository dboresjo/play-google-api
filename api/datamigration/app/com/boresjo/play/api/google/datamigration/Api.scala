package com.boresjo.play.api.google.datamigration

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

	private val BASE_URL = "https://datamigration.googleapis.com/"

	object projects {
		object locations {
			/** Fetches a set of static IP addresses that need to be allowlisted by the customer when using the static-IP connectivity method. */
			class fetchStaticIps(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FetchStaticIpsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FetchStaticIpsResponse])
			}
			object fetchStaticIps {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): fetchStaticIps = new fetchStaticIps(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:fetchStaticIps").addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[fetchStaticIps, Future[Schema.FetchStaticIpsResponse]] = (fun: fetchStaticIps) => fun.apply()
			}
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
			object conversionWorkspaces {
				/** Retrieves a list of committed revisions of a specific conversion workspace. */
				class describeConversionWorkspaceRevisions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DescribeConversionWorkspaceRevisionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Optional filter to request a specific commit ID. */
					def withCommitId(commitId: String) = new describeConversionWorkspaceRevisions(req.addQueryStringParameters("commitId" -> commitId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DescribeConversionWorkspaceRevisionsResponse])
				}
				object describeConversionWorkspaceRevisions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, conversionWorkspace: String)(using signer: RequestSigner, ec: ExecutionContext): describeConversionWorkspaceRevisions = new describeConversionWorkspaceRevisions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:describeConversionWorkspaceRevisions").addQueryStringParameters("conversionWorkspace" -> conversionWorkspace.toString))
					given Conversion[describeConversionWorkspaceRevisions, Future[Schema.DescribeConversionWorkspaceRevisionsResponse]] = (fun: describeConversionWorkspaceRevisions) => fun.apply()
				}
				/** Searches/lists the background jobs for a specific conversion workspace. The background jobs are not resources like conversion workspaces or mapping rules, and they can't be created, updated or deleted. Instead, they are a way to expose the data plane jobs log. */
				class searchBackgroundJobs(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchBackgroundJobsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Whether or not to return just the most recent job per job type, */
					def withReturnMostRecentPerJobType(returnMostRecentPerJobType: Boolean) = new searchBackgroundJobs(req.addQueryStringParameters("returnMostRecentPerJobType" -> returnMostRecentPerJobType.toString))
					/** Optional. The maximum number of jobs to return. The service may return fewer than this value. If unspecified, at most 100 jobs are returned. The maximum value is 100; values above 100 are coerced to 100.<br>Format: int32 */
					def withMaxSize(maxSize: Int) = new searchBackgroundJobs(req.addQueryStringParameters("maxSize" -> maxSize.toString))
					/** Optional. If provided, only returns jobs that completed until (not including) the given timestamp.<br>Format: google-datetime */
					def withCompletedUntilTime(completedUntilTime: String) = new searchBackgroundJobs(req.addQueryStringParameters("completedUntilTime" -> completedUntilTime.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchBackgroundJobsResponse])
				}
				object searchBackgroundJobs {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, conversionWorkspace: String)(using signer: RequestSigner, ec: ExecutionContext): searchBackgroundJobs = new searchBackgroundJobs(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:searchBackgroundJobs").addQueryStringParameters("conversionWorkspace" -> conversionWorkspace.toString))
					given Conversion[searchBackgroundJobs, Future[Schema.SearchBackgroundJobsResponse]] = (fun: searchBackgroundJobs) => fun.apply()
				}
				/** Rolls back a conversion workspace to the last committed snapshot. */
				class rollback(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRollbackConversionWorkspaceRequest(body: Schema.RollbackConversionWorkspaceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object rollback {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): rollback = new rollback(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:rollback").addQueryStringParameters("name" -> name.toString))
				}
				/** Applies draft tree onto a specific destination database. */
				class apply(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withApplyConversionWorkspaceRequest(body: Schema.ApplyConversionWorkspaceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object apply {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): apply = new apply(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:apply").addQueryStringParameters("name" -> name.toString))
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
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Deletes a single conversion workspace. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, name: String, requestId: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}").addQueryStringParameters("name" -> name.toString, "requestId" -> requestId.toString, "force" -> force.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Gets details of a single conversion workspace. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ConversionWorkspace]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ConversionWorkspace])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ConversionWorkspace]] = (fun: get) => fun.apply()
				}
				/** Updates the parameters of a single conversion workspace. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withConversionWorkspace(body: Schema.ConversionWorkspace) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, name: String, updateMask: String, requestId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "requestId" -> requestId.toString))
				}
				/** Creates a draft tree schema for the destination database. */
				class convert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withConvertConversionWorkspaceRequest(body: Schema.ConvertConversionWorkspaceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object convert {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): convert = new convert(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:convert").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists conversion workspaces in a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListConversionWorkspacesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListConversionWorkspacesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.ListConversionWorkspacesResponse]] = (fun: list) => fun.apply()
				}
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Describes the database entities tree for a specific conversion workspace and a specific tree type. Database entities are not resources like conversion workspaces or mapping rules, and they can't be created, updated or deleted. Instead, they are simple data objects describing the structure of the client database. */
				class describeDatabaseEntities(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DescribeDatabaseEntitiesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum number of entities to return. The service may return fewer entities than the value specifies.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new describeDatabaseEntities(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. The nextPageToken value received in the previous call to conversionWorkspace.describeDatabaseEntities, used in the subsequent request to retrieve the next page of results. On first call this should be left blank. When paginating, all other parameters provided to conversionWorkspace.describeDatabaseEntities must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new describeDatabaseEntities(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Whether to retrieve the latest committed version of the entities or the latest version. This field is ignored if a specific commit_id is specified. */
					def withUncommitted(uncommitted: Boolean) = new describeDatabaseEntities(req.addQueryStringParameters("uncommitted" -> uncommitted.toString))
					/** Optional. Request a specific commit ID. If not specified, the entities from the latest commit are returned. */
					def withCommitId(commitId: String) = new describeDatabaseEntities(req.addQueryStringParameters("commitId" -> commitId.toString))
					/** Optional. Filter the returned entities based on AIP-160 standard. */
					def withFilter(filter: String) = new describeDatabaseEntities(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Results view based on AIP-157<br>Possible values:<br>DATABASE_ENTITY_VIEW_UNSPECIFIED: Unspecified view. Defaults to basic view.<br>DATABASE_ENTITY_VIEW_BASIC: Default view. Does not return DDLs or Issues.<br>DATABASE_ENTITY_VIEW_FULL: Return full entity details including mappings, ddl and issues.<br>DATABASE_ENTITY_VIEW_ROOT_SUMMARY: Top-most (Database, Schema) nodes which are returned contains summary details for their decendents such as the number of entities per type and issues rollups. When this view is used, only a single page of result is returned and the page_size property of the request is ignored. The returned page will only include the top-most node types. */
					def withView(view: String) = new describeDatabaseEntities(req.addQueryStringParameters("view" -> view.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DescribeDatabaseEntitiesResponse])
				}
				object describeDatabaseEntities {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, conversionWorkspace: String, tree: String)(using signer: RequestSigner, ec: ExecutionContext): describeDatabaseEntities = new describeDatabaseEntities(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:describeDatabaseEntities").addQueryStringParameters("conversionWorkspace" -> conversionWorkspace.toString, "tree" -> tree.toString))
					given Conversion[describeDatabaseEntities, Future[Schema.DescribeDatabaseEntitiesResponse]] = (fun: describeDatabaseEntities) => fun.apply()
				}
				/** Marks all the data in the conversion workspace as committed. */
				class commit(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCommitConversionWorkspaceRequest(body: Schema.CommitConversionWorkspaceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object commit {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): commit = new commit(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:commit").addQueryStringParameters("name" -> name.toString))
				}
				/** Imports a snapshot of the source database into the conversion workspace. */
				class seed(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSeedConversionWorkspaceRequest(body: Schema.SeedConversionWorkspaceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object seed {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): seed = new seed(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:seed").addQueryStringParameters("name" -> name.toString))
				}
				/** Creates a new conversion workspace in a given project and location. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withConversionWorkspace(body: Schema.ConversionWorkspace) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, conversionWorkspaceId: String, requestId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces").addQueryStringParameters("parent" -> parent.toString, "conversionWorkspaceId" -> conversionWorkspaceId.toString, "requestId" -> requestId.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object mappingRules {
					/** Creates a new mapping rule for a given conversion workspace. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withMappingRule(body: Schema.MappingRule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.MappingRule])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, parent: String, mappingRuleId: String, requestId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}/mappingRules").addQueryStringParameters("parent" -> parent.toString, "mappingRuleId" -> mappingRuleId.toString, "requestId" -> requestId.toString))
					}
					/** Imports the mapping rules for a given conversion workspace. Supports various formats of external rules files. */
					class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withImportMappingRulesRequest(body: Schema.ImportMappingRulesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}/mappingRules:import").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a single mapping rule. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. A unique ID used to identify the request. If the server receives two requests with the same ID, then the second request is ignored. It is recommended to always set this value to a UUID. The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
						def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, mappingRulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}/mappingRules/${mappingRulesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Gets the details of a mapping rule. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.MappingRule]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.MappingRule])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, mappingRulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}/mappingRules/${mappingRulesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.MappingRule]] = (fun: get) => fun.apply()
					}
					/** Lists the mapping rules for a specific conversion workspace. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListMappingRulesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListMappingRulesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversionWorkspacesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversionWorkspaces/${conversionWorkspacesId}/mappingRules").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListMappingRulesResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object privateConnections {
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, privateConnectionsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections/${privateConnectionsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
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
					def apply(projectsId :PlayApi, locationsId :PlayApi, privateConnectionsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections/${privateConnectionsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Deletes a single Database Migration Service private connection. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A unique ID used to identify the request. If the server receives two requests with the same ID, then the second request is ignored. It is recommended to always set this value to a UUID. The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, privateConnectionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections/${privateConnectionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Gets details of a single private connection. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PrivateConnection]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PrivateConnection])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, privateConnectionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections/${privateConnectionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.PrivateConnection]] = (fun: get) => fun.apply()
				}
				/** Retrieves a list of private connections in a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPrivateConnectionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPrivateConnectionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.ListPrivateConnectionsResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a new private connection in a given project and location. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A unique ID used to identify the request. If the server receives two requests with the same ID, then the second request is ignored. It is recommended to always set this value to a UUID. The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set to true, will skip validations. */
					def withSkipValidation(skipValidation: Boolean) = new create(req.addQueryStringParameters("skipValidation" -> skipValidation.toString))
					/** Perform the request */
					def withPrivateConnection(body: Schema.PrivateConnection) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, privateConnectionId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections").addQueryStringParameters("parent" -> parent.toString, "privateConnectionId" -> privateConnectionId.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, privateConnectionsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/privateConnections/${privateConnectionsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
			}
			object migrationJobs {
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Resume a migration job that is currently stopped and is resumable (was stopped during CDC phase). */
				class resume(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withResumeMigrationJobRequest(body: Schema.ResumeMigrationJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object resume {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): resume = new resume(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:resume").addQueryStringParameters("name" -> name.toString))
				}
				/** Stops a running migration job. */
				class stop(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withStopMigrationJobRequest(body: Schema.StopMigrationJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object stop {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:stop").addQueryStringParameters("name" -> name.toString))
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
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Deletes a single migration job. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, name: String, requestId: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}").addQueryStringParameters("name" -> name.toString, "requestId" -> requestId.toString, "force" -> force.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Demotes the destination database to become a read replica of the source. This is applicable for the following migrations: 1. MySQL to Cloud SQL for MySQL 2. PostgreSQL to Cloud SQL for PostgreSQL 3. PostgreSQL to AlloyDB for PostgreSQL. */
				class demoteDestination(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withDemoteDestinationRequest(body: Schema.DemoteDestinationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object demoteDestination {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): demoteDestination = new demoteDestination(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:demoteDestination").addQueryStringParameters("name" -> name.toString))
				}
				/** Gets details of a single migration job. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.MigrationJob]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.MigrationJob])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.MigrationJob]] = (fun: get) => fun.apply()
				}
				/** Lists migration jobs in a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListMigrationJobsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListMigrationJobsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.ListMigrationJobsResponse]] = (fun: list) => fun.apply()
				}
				/** Verify a migration job, making sure the destination can reach the source and that all configuration and prerequisites are met. */
				class verify(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withVerifyMigrationJobRequest(body: Schema.VerifyMigrationJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object verify {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): verify = new verify(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:verify").addQueryStringParameters("name" -> name.toString))
				}
				/** Generate a TCP Proxy configuration script to configure a cloud-hosted VM running a TCP Proxy. */
				class generateTcpProxyScript(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGenerateTcpProxyScriptRequest(body: Schema.GenerateTcpProxyScriptRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TcpProxyScript])
				}
				object generateTcpProxyScript {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, migrationJob: String)(using signer: RequestSigner, ec: ExecutionContext): generateTcpProxyScript = new generateTcpProxyScript(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:generateTcpProxyScript").addQueryStringParameters("migrationJob" -> migrationJob.toString))
				}
				/** Creates a new migration job in a given project and location. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A unique ID used to identify the request. If the server receives two requests with the same ID, then the second request is ignored. It is recommended to always set this value to a UUID. The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Perform the request */
					def withMigrationJob(body: Schema.MigrationJob) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, migrationJobId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs").addQueryStringParameters("parent" -> parent.toString, "migrationJobId" -> migrationJobId.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Promote a migration job, stopping replication to the destination and promoting the destination to be a standalone database. */
				class promote(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withPromoteMigrationJobRequest(body: Schema.PromoteMigrationJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object promote {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): promote = new promote(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:promote").addQueryStringParameters("name" -> name.toString))
				}
				/** Generate a SSH configuration script to configure the reverse SSH connectivity. */
				class generateSshScript(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGenerateSshScriptRequest(body: Schema.GenerateSshScriptRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SshScript])
				}
				object generateSshScript {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, migrationJob: String)(using signer: RequestSigner, ec: ExecutionContext): generateSshScript = new generateSshScript(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:generateSshScript").addQueryStringParameters("migrationJob" -> migrationJob.toString))
				}
				/** Restart a stopped or failed migration job, resetting the destination instance to its original state and starting the migration process from scratch. */
				class restart(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRestartMigrationJobRequest(body: Schema.RestartMigrationJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object restart {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): restart = new restart(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:restart").addQueryStringParameters("name" -> name.toString))
				}
				/** Updates the parameters of a single migration job. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withMigrationJob(body: Schema.MigrationJob) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, name: String, updateMask: String, requestId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "requestId" -> requestId.toString))
				}
				/** Start an already created migration job. */
				class start(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withStartMigrationJobRequest(body: Schema.StartMigrationJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object start {
					def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): start = new start(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}:start").addQueryStringParameters("name" -> name.toString))
				}
				object objects {
					/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, objectsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}/objects/${objectsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
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
						def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, objectsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}/objects/${objectsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, migrationJobsId :PlayApi, objectsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/migrationJobs/${migrationJobsId}/objects/${objectsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
				}
			}
			object connectionProfiles {
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionProfilesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles/${connectionProfilesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
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
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionProfilesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles/${connectionProfilesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Deletes a single Database Migration Service connection profile. A connection profile can only be deleted if it is not in use by any active migration jobs. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionProfilesId :PlayApi, name: String, requestId: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles/${connectionProfilesId}").addQueryStringParameters("name" -> name.toString, "requestId" -> requestId.toString, "force" -> force.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Gets details of a single connection profile. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ConnectionProfile]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ConnectionProfile])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles/${connectionProfilesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ConnectionProfile]] = (fun: get) => fun.apply()
				}
				/** Update the configuration of a single connection profile. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A unique ID used to identify the request. If the server receives two requests with the same ID, then the second request is ignored. It is recommended to always set this value to a UUID. The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
					def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. Only validate the connection profile, but don't update any resources. The default is false. Only supported for Oracle connection profiles. */
					def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. Update the connection profile without validating it. The default is false. Only supported for Oracle connection profiles. */
					def withSkipValidation(skipValidation: Boolean) = new patch(req.addQueryStringParameters("skipValidation" -> skipValidation.toString))
					/** Perform the request */
					def withConnectionProfile(body: Schema.ConnectionProfile) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionProfilesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles/${connectionProfilesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Retrieves a list of all connection profiles in a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListConnectionProfilesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListConnectionProfilesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
					given Conversion[list, Future[Schema.ListConnectionProfilesResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a new connection profile in a given project and location. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A unique ID used to identify the request. If the server receives two requests with the same ID, then the second request is ignored. It is recommended to always set this value to a UUID. The ID must contain only letters (a-z, A-Z), numbers (0-9), underscores (_), and hyphens (-). The maximum length is 40 characters. */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. Only validate the connection profile, but don't create any resources. The default is false. Only supported for Oracle connection profiles. */
					def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. Create the connection profile without validating it. The default is false. Only supported for Oracle connection profiles. */
					def withSkipValidation(skipValidation: Boolean) = new create(req.addQueryStringParameters("skipValidation" -> skipValidation.toString))
					/** Perform the request */
					def withConnectionProfile(body: Schema.ConnectionProfile) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, connectionProfileId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles").addQueryStringParameters("parent" -> parent.toString, "connectionProfileId" -> connectionProfileId.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionProfilesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connectionProfiles/${connectionProfilesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
			}
		}
	}
}
