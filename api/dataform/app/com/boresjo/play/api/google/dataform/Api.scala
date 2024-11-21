package com.boresjo.play.api.google.dataform

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://dataform.googleapis.com/"

	object projects {
		object locations {
			class updateConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. Specifies the fields to be updated in the config.<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new updateConfig(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withConfig(body: Schema.Config) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Config])
			}
			object updateConfig {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateConfig = new updateConfig(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/config")).addQueryStringParameters("name" -> name.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			class getConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Config]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Config])
			}
			object getConfig {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getConfig = new getConfig(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/config")).addQueryStringParameters("name" -> name.toString))
				given Conversion[getConfig, Future[Schema.Config]] = (fun: getConfig) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object collections {
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
			}
			object repositories {
				class computeAccessTokenStatus(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ComputeRepositoryAccessTokenStatusResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ComputeRepositoryAccessTokenStatusResponse])
				}
				object computeAccessTokenStatus {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): computeAccessTokenStatus = new computeAccessTokenStatus(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:computeAccessTokenStatus")).addQueryStringParameters("name" -> name.toString))
					given Conversion[computeAccessTokenStatus, Future[Schema.ComputeRepositoryAccessTokenStatusResponse]] = (fun: computeAccessTokenStatus) => fun.apply()
				}
				class commit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCommitRepositoryChangesRequest(body: Schema.CommitRepositoryChangesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CommitRepositoryChangesResponse])
				}
				object commit {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): commit = new commit(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:commit")).addQueryStringParameters("name" -> name.toString))
				}
				class queryDirectoryContents(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.QueryRepositoryDirectoryContentsResponse]) {
					/** Optional. The Commit SHA for the commit to query from. If unset, the directory will be queried from HEAD. */
					def withCommitSha(commitSha: String) = new queryDirectoryContents(req.addQueryStringParameters("commitSha" -> commitSha.toString))
					/** Optional. The directory's full path including directory name, relative to root. If left unset, the root is used. */
					def withPath(path: String) = new queryDirectoryContents(req.addQueryStringParameters("path" -> path.toString))
					/** Optional. Maximum number of paths to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new queryDirectoryContents(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Page token received from a previous `QueryRepositoryDirectoryContents` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `QueryRepositoryDirectoryContents`, with the exception of `page_size`, must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new queryDirectoryContents(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.QueryRepositoryDirectoryContentsResponse])
				}
				object queryDirectoryContents {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): queryDirectoryContents = new queryDirectoryContents(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:queryDirectoryContents")).addQueryStringParameters("name" -> name.toString))
					given Conversion[queryDirectoryContents, Future[Schema.QueryRepositoryDirectoryContentsResponse]] = (fun: queryDirectoryContents) => fun.apply()
				}
				class readFile(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ReadRepositoryFileResponse]) {
					/** Optional. The commit SHA for the commit to read from. If unset, the file will be read from HEAD. */
					def withCommitSha(commitSha: String) = new readFile(req.addQueryStringParameters("commitSha" -> commitSha.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ReadRepositoryFileResponse])
				}
				object readFile {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String, path: String)(using auth: AuthToken, ec: ExecutionContext): readFile = new readFile(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:readFile")).addQueryStringParameters("name" -> name.toString, "path" -> path.toString))
					given Conversion[readFile, Future[Schema.ReadRepositoryFileResponse]] = (fun: readFile) => fun.apply()
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Repository]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Repository])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Repository]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Specifies the fields to be updated in the repository. If left unset, all fields will be updated.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					def withRepository(body: Schema.Repository) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Repository])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}")).addQueryStringParameters("name" -> name.toString))
				}
				class fetchRemoteBranches(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FetchRemoteBranchesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.FetchRemoteBranchesResponse])
				}
				object fetchRemoteBranches {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): fetchRemoteBranches = new fetchRemoteBranches(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:fetchRemoteBranches")).addQueryStringParameters("name" -> name.toString))
					given Conversion[fetchRemoteBranches, Future[Schema.FetchRemoteBranchesResponse]] = (fun: fetchRemoteBranches) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListRepositoriesResponse]) {
					/** Optional. Maximum number of repositories to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Page token received from a previous `ListRepositories` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListRepositories`, with the exception of `page_size`, must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. This field only supports ordering by `name`. If unspecified, the server will choose the ordering. If specified, the default order is ascending for the `name` field. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Optional. Filter for the returned list. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListRepositoriesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListRepositoriesResponse]] = (fun: list) => fun.apply()
				}
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class fetchHistory(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FetchRepositoryHistoryResponse]) {
					/** Optional. Maximum number of commits to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new fetchHistory(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Page token received from a previous `FetchRepositoryHistory` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `FetchRepositoryHistory`, with the exception of `page_size`, must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new fetchHistory(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.FetchRepositoryHistoryResponse])
				}
				object fetchHistory {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): fetchHistory = new fetchHistory(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:fetchHistory")).addQueryStringParameters("name" -> name.toString))
					given Conversion[fetchHistory, Future[Schema.FetchRepositoryHistoryResponse]] = (fun: fetchHistory) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRepository(body: Schema.Repository) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Repository])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, repositoryId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories")).addQueryStringParameters("parent" -> parent.toString, "repositoryId" -> repositoryId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}")).addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				object releaseConfigs {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withReleaseConfig(body: Schema.ReleaseConfig) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ReleaseConfig])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, releaseConfigId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/releaseConfigs")).addQueryStringParameters("parent" -> parent.toString, "releaseConfigId" -> releaseConfigId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, releaseConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/releaseConfigs/${releaseConfigsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ReleaseConfig]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ReleaseConfig])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, releaseConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/releaseConfigs/${releaseConfigsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.ReleaseConfig]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. Specifies the fields to be updated in the release config. If left unset, all fields will be updated.<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						def withReleaseConfig(body: Schema.ReleaseConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.ReleaseConfig])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, releaseConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/releaseConfigs/${releaseConfigsId}")).addQueryStringParameters("name" -> name.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListReleaseConfigsResponse]) {
						/** Optional. Maximum number of release configs to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token received from a previous `ListReleaseConfigs` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListReleaseConfigs`, with the exception of `page_size`, must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListReleaseConfigsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/releaseConfigs")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListReleaseConfigsResponse]] = (fun: list) => fun.apply()
					}
				}
				object workflowInvocations {
					class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCancelWorkflowInvocationRequest(body: Schema.CancelWorkflowInvocationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
					}
					object cancel {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workflowInvocationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowInvocations/${workflowInvocationsId}:cancel")).addQueryStringParameters("name" -> name.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withWorkflowInvocation(body: Schema.WorkflowInvocation) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.WorkflowInvocation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowInvocations")).addQueryStringParameters("parent" -> parent.toString))
					}
					class query(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.QueryWorkflowInvocationActionsResponse]) {
						/** Optional. Maximum number of workflow invocations to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new query(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token received from a previous `QueryWorkflowInvocationActions` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `QueryWorkflowInvocationActions`, with the exception of `page_size`, must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new query(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.QueryWorkflowInvocationActionsResponse])
					}
					object query {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workflowInvocationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): query = new query(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowInvocations/${workflowInvocationsId}:query")).addQueryStringParameters("name" -> name.toString))
						given Conversion[query, Future[Schema.QueryWorkflowInvocationActionsResponse]] = (fun: query) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workflowInvocationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowInvocations/${workflowInvocationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.WorkflowInvocation]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.WorkflowInvocation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workflowInvocationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowInvocations/${workflowInvocationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.WorkflowInvocation]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListWorkflowInvocationsResponse]) {
						/** Optional. Maximum number of workflow invocations to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token received from a previous `ListWorkflowInvocations` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListWorkflowInvocations`, with the exception of `page_size`, must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. This field only supports ordering by `name`. If unspecified, the server will choose the ordering. If specified, the default order is ascending for the `name` field. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Optional. Filter for the returned list. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListWorkflowInvocationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowInvocations")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListWorkflowInvocationsResponse]] = (fun: list) => fun.apply()
					}
				}
				object workflowConfigs {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withWorkflowConfig(body: Schema.WorkflowConfig) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.WorkflowConfig])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, workflowConfigId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowConfigs")).addQueryStringParameters("parent" -> parent.toString, "workflowConfigId" -> workflowConfigId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workflowConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowConfigs/${workflowConfigsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.WorkflowConfig]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.WorkflowConfig])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workflowConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowConfigs/${workflowConfigsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.WorkflowConfig]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. Specifies the fields to be updated in the workflow config. If left unset, all fields will be updated.<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						def withWorkflowConfig(body: Schema.WorkflowConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.WorkflowConfig])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workflowConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowConfigs/${workflowConfigsId}")).addQueryStringParameters("name" -> name.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListWorkflowConfigsResponse]) {
						/** Optional. Maximum number of workflow configs to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token received from a previous `ListWorkflowConfigs` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListWorkflowConfigs`, with the exception of `page_size`, must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListWorkflowConfigsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowConfigs")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListWorkflowConfigsResponse]] = (fun: list) => fun.apply()
					}
				}
				object workspaces {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
					}
					class removeFile(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withRemoveFileRequest(body: Schema.RemoveFileRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
					}
					object removeFile {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String)(using auth: AuthToken, ec: ExecutionContext): removeFile = new removeFile(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:removeFile")).addQueryStringParameters("workspace" -> workspace.toString))
					}
					class searchFiles(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchFilesResponse]) {
						/** Optional. Maximum number of search results to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new searchFiles(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token received from a previous `SearchFilesRequest` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `SearchFilesRequest`, with the exception of `page_size`, must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new searchFiles(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Optional filter for the returned list in filtering format. Filtering is only currently supported on the `path` field. See https://google.aip.dev/160 for details. */
						def withFilter(filter: String) = new searchFiles(req.addQueryStringParameters("filter" -> filter.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.SearchFilesResponse])
					}
					object searchFiles {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String)(using auth: AuthToken, ec: ExecutionContext): searchFiles = new searchFiles(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:searchFiles")).addQueryStringParameters("workspace" -> workspace.toString))
						given Conversion[searchFiles, Future[Schema.SearchFilesResponse]] = (fun: searchFiles) => fun.apply()
					}
					class commit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCommitWorkspaceChangesRequest(body: Schema.CommitWorkspaceChangesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
					}
					object commit {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): commit = new commit(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:commit")).addQueryStringParameters("name" -> name.toString))
					}
					class fetchFileGitStatuses(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FetchFileGitStatusesResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.FetchFileGitStatusesResponse])
					}
					object fetchFileGitStatuses {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): fetchFileGitStatuses = new fetchFileGitStatuses(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:fetchFileGitStatuses")).addQueryStringParameters("name" -> name.toString))
						given Conversion[fetchFileGitStatuses, Future[Schema.FetchFileGitStatusesResponse]] = (fun: fetchFileGitStatuses) => fun.apply()
					}
					class push(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withPushGitCommitsRequest(body: Schema.PushGitCommitsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
					}
					object push {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): push = new push(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:push")).addQueryStringParameters("name" -> name.toString))
					}
					class fetchGitAheadBehind(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FetchGitAheadBehindResponse]) {
						/** Optional. The name of the branch in the Git remote against which this workspace should be compared. If left unset, the repository's default branch name will be used. */
						def withRemoteBranch(remoteBranch: String) = new fetchGitAheadBehind(req.addQueryStringParameters("remoteBranch" -> remoteBranch.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.FetchGitAheadBehindResponse])
					}
					object fetchGitAheadBehind {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): fetchGitAheadBehind = new fetchGitAheadBehind(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:fetchGitAheadBehind")).addQueryStringParameters("name" -> name.toString))
						given Conversion[fetchGitAheadBehind, Future[Schema.FetchGitAheadBehindResponse]] = (fun: fetchGitAheadBehind) => fun.apply()
					}
					class readFile(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ReadFileResponse]) {
						/** Optional. The Git revision of the file to return. If left empty, the current contents of `path` will be returned. */
						def withRevision(revision: String) = new readFile(req.addQueryStringParameters("revision" -> revision.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ReadFileResponse])
					}
					object readFile {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String, path: String)(using auth: AuthToken, ec: ExecutionContext): readFile = new readFile(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:readFile")).addQueryStringParameters("workspace" -> workspace.toString, "path" -> path.toString))
						given Conversion[readFile, Future[Schema.ReadFileResponse]] = (fun: readFile) => fun.apply()
					}
					class writeFile(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withWriteFileRequest(body: Schema.WriteFileRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.WriteFileResponse])
					}
					object writeFile {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String)(using auth: AuthToken, ec: ExecutionContext): writeFile = new writeFile(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:writeFile")).addQueryStringParameters("workspace" -> workspace.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class removeDirectory(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withRemoveDirectoryRequest(body: Schema.RemoveDirectoryRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
					}
					object removeDirectory {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String)(using auth: AuthToken, ec: ExecutionContext): removeDirectory = new removeDirectory(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:removeDirectory")).addQueryStringParameters("workspace" -> workspace.toString))
					}
					class pull(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withPullGitCommitsRequest(body: Schema.PullGitCommitsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
					}
					object pull {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): pull = new pull(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:pull")).addQueryStringParameters("name" -> name.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Workspace]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.Workspace])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Workspace]] = (fun: get) => fun.apply()
					}
					class installNpmPackages(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withInstallNpmPackagesRequest(body: Schema.InstallNpmPackagesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.InstallNpmPackagesResponse])
					}
					object installNpmPackages {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String)(using auth: AuthToken, ec: ExecutionContext): installNpmPackages = new installNpmPackages(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:installNpmPackages")).addQueryStringParameters("workspace" -> workspace.toString))
					}
					class moveFile(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withMoveFileRequest(body: Schema.MoveFileRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.MoveFileResponse])
					}
					object moveFile {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String)(using auth: AuthToken, ec: ExecutionContext): moveFile = new moveFile(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:moveFile")).addQueryStringParameters("workspace" -> workspace.toString))
					}
					class queryDirectoryContents(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.QueryDirectoryContentsResponse]) {
						/** Optional. The directory's full path including directory name, relative to the workspace root. If left unset, the workspace root is used. */
						def withPath(path: String) = new queryDirectoryContents(req.addQueryStringParameters("path" -> path.toString))
						/** Optional. Maximum number of paths to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new queryDirectoryContents(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token received from a previous `QueryDirectoryContents` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `QueryDirectoryContents`, with the exception of `page_size`, must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new queryDirectoryContents(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.QueryDirectoryContentsResponse])
					}
					object queryDirectoryContents {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String)(using auth: AuthToken, ec: ExecutionContext): queryDirectoryContents = new queryDirectoryContents(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:queryDirectoryContents")).addQueryStringParameters("workspace" -> workspace.toString))
						given Conversion[queryDirectoryContents, Future[Schema.QueryDirectoryContentsResponse]] = (fun: queryDirectoryContents) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withWorkspace(body: Schema.Workspace) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Workspace])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, workspaceId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces")).addQueryStringParameters("parent" -> parent.toString, "workspaceId" -> workspaceId.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					class fetchFileDiff(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FetchFileDiffResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.FetchFileDiffResponse])
					}
					object fetchFileDiff {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String, path: String)(using auth: AuthToken, ec: ExecutionContext): fetchFileDiff = new fetchFileDiff(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:fetchFileDiff")).addQueryStringParameters("workspace" -> workspace.toString, "path" -> path.toString))
						given Conversion[fetchFileDiff, Future[Schema.FetchFileDiffResponse]] = (fun: fetchFileDiff) => fun.apply()
					}
					class reset(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withResetWorkspaceChangesRequest(body: Schema.ResetWorkspaceChangesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
					}
					object reset {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): reset = new reset(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:reset")).addQueryStringParameters("name" -> name.toString))
					}
					class moveDirectory(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withMoveDirectoryRequest(body: Schema.MoveDirectoryRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.MoveDirectoryResponse])
					}
					object moveDirectory {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String)(using auth: AuthToken, ec: ExecutionContext): moveDirectory = new moveDirectory(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:moveDirectory")).addQueryStringParameters("workspace" -> workspace.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListWorkspacesResponse]) {
						/** Optional. Maximum number of workspaces to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token received from a previous `ListWorkspaces` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListWorkspaces`, with the exception of `page_size`, must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. This field only supports ordering by `name`. If unspecified, the server will choose the ordering. If specified, the default order is ascending for the `name` field. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Optional. Filter for the returned list. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListWorkspacesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListWorkspacesResponse]] = (fun: list) => fun.apply()
					}
					class makeDirectory(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withMakeDirectoryRequest(body: Schema.MakeDirectoryRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.MakeDirectoryResponse])
					}
					object makeDirectory {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String)(using auth: AuthToken, ec: ExecutionContext): makeDirectory = new makeDirectory(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:makeDirectory")).addQueryStringParameters("workspace" -> workspace.toString))
					}
				}
				object compilationResults {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCompilationResultsResponse]) {
						/** Optional. Maximum number of compilation results to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token received from a previous `ListCompilationResults` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListCompilationResults`, with the exception of `page_size`, must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. This field only supports ordering by `name` and `create_time`. If unspecified, the server will choose the ordering. If specified, the default order is ascending for the `name` field. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Optional. Filter for the returned list. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.ListCompilationResultsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/compilationResults")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListCompilationResultsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CompilationResult]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.CompilationResult])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, compilationResultsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/compilationResults/${compilationResultsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.CompilationResult]] = (fun: get) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCompilationResult(body: Schema.CompilationResult) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CompilationResult])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/compilationResults")).addQueryStringParameters("parent" -> parent.toString))
					}
					class query(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.QueryCompilationResultActionsResponse]) {
						/** Optional. Maximum number of compilation results to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new query(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token received from a previous `QueryCompilationResultActions` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `QueryCompilationResultActions`, with the exception of `page_size`, must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new query(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Optional filter for the returned list. Filtering is only currently supported on the `file_path` field. */
						def withFilter(filter: String) = new query(req.addQueryStringParameters("filter" -> filter.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.QueryCompilationResultActionsResponse])
					}
					object query {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, compilationResultsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): query = new query(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/compilationResults/${compilationResultsId}:query")).addQueryStringParameters("name" -> name.toString))
						given Conversion[query, Future[Schema.QueryCompilationResultActionsResponse]] = (fun: query) => fun.apply()
					}
				}
				object commentThreads {
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, commentThreadsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/commentThreads/${commentThreadsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
						/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
						def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, commentThreadsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/commentThreads/${commentThreadsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					object comments {
						class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
						}
						object setIamPolicy {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, commentThreadsId :PlayApi, commentsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/commentThreads/${commentThreadsId}/comments/${commentsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
						}
						class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
							/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
							def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.Policy])
						}
						object getIamPolicy {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, commentThreadsId :PlayApi, commentsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/commentThreads/${commentThreadsId}/comments/${commentsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
							given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
						}
					}
				}
			}
		}
	}
}
