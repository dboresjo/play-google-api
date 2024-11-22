package com.boresjo.play.api.google.dataform

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

	private val BASE_URL = "https://dataform.googleapis.com/"

	object projects {
		object locations {
			/** Update default config for a given project and location. */
			class updateConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. Specifies the fields to be updated in the config.<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new updateConfig(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				/** Perform the request */
				def withConfig(body: Schema.Config) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Config])
			}
			object updateConfig {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateConfig = new updateConfig(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/config").addQueryStringParameters("name" -> name.toString))
			}
			/** Gets information about a location. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			/** Get default config for a given project and location. */
			class getConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Config]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Config])
			}
			object getConfig {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getConfig = new getConfig(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/config").addQueryStringParameters("name" -> name.toString))
				given Conversion[getConfig, Future[Schema.Config]] = (fun: getConfig) => fun.apply()
			}
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object collections {
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
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
					def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
			}
			object repositories {
				/** Computes a Repository's Git access token status. */
				class computeAccessTokenStatus(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ComputeRepositoryAccessTokenStatusResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ComputeRepositoryAccessTokenStatusResponse])
				}
				object computeAccessTokenStatus {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): computeAccessTokenStatus = new computeAccessTokenStatus(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:computeAccessTokenStatus").addQueryStringParameters("name" -> name.toString))
					given Conversion[computeAccessTokenStatus, Future[Schema.ComputeRepositoryAccessTokenStatusResponse]] = (fun: computeAccessTokenStatus) => fun.apply()
				}
				/** Applies a Git commit to a Repository. The Repository must not have a value for `git_remote_settings.url`. */
				class commit(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withCommitRepositoryChangesRequest(body: Schema.CommitRepositoryChangesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CommitRepositoryChangesResponse])
				}
				object commit {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): commit = new commit(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:commit").addQueryStringParameters("name" -> name.toString))
				}
				/** Returns the contents of a given Repository directory. The Repository must not have a value for `git_remote_settings.url`. */
				class queryDirectoryContents(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.QueryRepositoryDirectoryContentsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The Commit SHA for the commit to query from. If unset, the directory will be queried from HEAD. */
					def withCommitSha(commitSha: String) = new queryDirectoryContents(req.addQueryStringParameters("commitSha" -> commitSha.toString))
					/** Optional. The directory's full path including directory name, relative to root. If left unset, the root is used. */
					def withPath(path: String) = new queryDirectoryContents(req.addQueryStringParameters("path" -> path.toString))
					/** Optional. Maximum number of paths to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new queryDirectoryContents(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Page token received from a previous `QueryRepositoryDirectoryContents` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `QueryRepositoryDirectoryContents`, with the exception of `page_size`, must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new queryDirectoryContents(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.QueryRepositoryDirectoryContentsResponse])
				}
				object queryDirectoryContents {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): queryDirectoryContents = new queryDirectoryContents(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:queryDirectoryContents").addQueryStringParameters("name" -> name.toString))
					given Conversion[queryDirectoryContents, Future[Schema.QueryRepositoryDirectoryContentsResponse]] = (fun: queryDirectoryContents) => fun.apply()
				}
				/** Returns the contents of a file (inside a Repository). The Repository must not have a value for `git_remote_settings.url`. */
				class readFile(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ReadRepositoryFileResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The commit SHA for the commit to read from. If unset, the file will be read from HEAD. */
					def withCommitSha(commitSha: String) = new readFile(req.addQueryStringParameters("commitSha" -> commitSha.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ReadRepositoryFileResponse])
				}
				object readFile {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String, path: String)(using signer: RequestSigner, ec: ExecutionContext): readFile = new readFile(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:readFile").addQueryStringParameters("name" -> name.toString, "path" -> path.toString))
					given Conversion[readFile, Future[Schema.ReadRepositoryFileResponse]] = (fun: readFile) => fun.apply()
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
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				/** Fetches a single Repository. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Repository]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Repository])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Repository]] = (fun: get) => fun.apply()
				}
				/** Updates a single Repository. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Specifies the fields to be updated in the repository. If left unset, all fields will be updated.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Perform the request */
					def withRepository(body: Schema.Repository) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Repository])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Fetches a Repository's remote branches. */
				class fetchRemoteBranches(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FetchRemoteBranchesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FetchRemoteBranchesResponse])
				}
				object fetchRemoteBranches {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): fetchRemoteBranches = new fetchRemoteBranches(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:fetchRemoteBranches").addQueryStringParameters("name" -> name.toString))
					given Conversion[fetchRemoteBranches, Future[Schema.FetchRemoteBranchesResponse]] = (fun: fetchRemoteBranches) => fun.apply()
				}
				/** Lists Repositories in a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListRepositoriesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Maximum number of repositories to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Page token received from a previous `ListRepositories` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListRepositories`, with the exception of `page_size`, must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. This field only supports ordering by `name`. If unspecified, the server will choose the ordering. If specified, the default order is ascending for the `name` field. */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Optional. Filter for the returned list. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListRepositoriesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListRepositoriesResponse]] = (fun: list) => fun.apply()
				}
				/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Fetches a Repository's history of commits. The Repository must not have a value for `git_remote_settings.url`. */
				class fetchHistory(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FetchRepositoryHistoryResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Maximum number of commits to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new fetchHistory(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Page token received from a previous `FetchRepositoryHistory` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `FetchRepositoryHistory`, with the exception of `page_size`, must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new fetchHistory(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FetchRepositoryHistoryResponse])
				}
				object fetchHistory {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): fetchHistory = new fetchHistory(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:fetchHistory").addQueryStringParameters("name" -> name.toString))
					given Conversion[fetchHistory, Future[Schema.FetchRepositoryHistoryResponse]] = (fun: fetchHistory) => fun.apply()
				}
				/** Creates a new Repository in a given project and location. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRepository(body: Schema.Repository) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Repository])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, repositoryId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories").addQueryStringParameters("parent" -> parent.toString, "repositoryId" -> repositoryId.toString))
				}
				/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Deletes a single Repository. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, name: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				object releaseConfigs {
					/** Creates a new ReleaseConfig in a given Repository. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withReleaseConfig(body: Schema.ReleaseConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReleaseConfig])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, releaseConfigId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/releaseConfigs").addQueryStringParameters("parent" -> parent.toString, "releaseConfigId" -> releaseConfigId.toString))
					}
					/** Deletes a single ReleaseConfig. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, releaseConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/releaseConfigs/${releaseConfigsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Fetches a single ReleaseConfig. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ReleaseConfig]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ReleaseConfig])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, releaseConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/releaseConfigs/${releaseConfigsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.ReleaseConfig]] = (fun: get) => fun.apply()
					}
					/** Updates a single ReleaseConfig. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Specifies the fields to be updated in the release config. If left unset, all fields will be updated.<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						/** Perform the request */
						def withReleaseConfig(body: Schema.ReleaseConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ReleaseConfig])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, releaseConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/releaseConfigs/${releaseConfigsId}").addQueryStringParameters("name" -> name.toString))
					}
					/** Lists ReleaseConfigs in a given Repository. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListReleaseConfigsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Maximum number of release configs to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token received from a previous `ListReleaseConfigs` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListReleaseConfigs`, with the exception of `page_size`, must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListReleaseConfigsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/releaseConfigs").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListReleaseConfigsResponse]] = (fun: list) => fun.apply()
					}
				}
				object workflowInvocations {
					/** Requests cancellation of a running WorkflowInvocation. */
					class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withCancelWorkflowInvocationRequest(body: Schema.CancelWorkflowInvocationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object cancel {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workflowInvocationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowInvocations/${workflowInvocationsId}:cancel").addQueryStringParameters("name" -> name.toString))
					}
					/** Creates a new WorkflowInvocation in a given Repository. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withWorkflowInvocation(body: Schema.WorkflowInvocation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.WorkflowInvocation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowInvocations").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Returns WorkflowInvocationActions in a given WorkflowInvocation. */
					class query(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.QueryWorkflowInvocationActionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Maximum number of workflow invocations to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new query(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token received from a previous `QueryWorkflowInvocationActions` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `QueryWorkflowInvocationActions`, with the exception of `page_size`, must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new query(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.QueryWorkflowInvocationActionsResponse])
					}
					object query {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workflowInvocationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): query = new query(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowInvocations/${workflowInvocationsId}:query").addQueryStringParameters("name" -> name.toString))
						given Conversion[query, Future[Schema.QueryWorkflowInvocationActionsResponse]] = (fun: query) => fun.apply()
					}
					/** Deletes a single WorkflowInvocation. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workflowInvocationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowInvocations/${workflowInvocationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Fetches a single WorkflowInvocation. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.WorkflowInvocation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.WorkflowInvocation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workflowInvocationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowInvocations/${workflowInvocationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.WorkflowInvocation]] = (fun: get) => fun.apply()
					}
					/** Lists WorkflowInvocations in a given Repository. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListWorkflowInvocationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Maximum number of workflow invocations to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token received from a previous `ListWorkflowInvocations` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListWorkflowInvocations`, with the exception of `page_size`, must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. This field only supports ordering by `name`. If unspecified, the server will choose the ordering. If specified, the default order is ascending for the `name` field. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Optional. Filter for the returned list. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListWorkflowInvocationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowInvocations").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListWorkflowInvocationsResponse]] = (fun: list) => fun.apply()
					}
				}
				object workflowConfigs {
					/** Creates a new WorkflowConfig in a given Repository. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withWorkflowConfig(body: Schema.WorkflowConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.WorkflowConfig])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, workflowConfigId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowConfigs").addQueryStringParameters("parent" -> parent.toString, "workflowConfigId" -> workflowConfigId.toString))
					}
					/** Deletes a single WorkflowConfig. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workflowConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowConfigs/${workflowConfigsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Fetches a single WorkflowConfig. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.WorkflowConfig]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.WorkflowConfig])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workflowConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowConfigs/${workflowConfigsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.WorkflowConfig]] = (fun: get) => fun.apply()
					}
					/** Updates a single WorkflowConfig. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Specifies the fields to be updated in the workflow config. If left unset, all fields will be updated.<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						/** Perform the request */
						def withWorkflowConfig(body: Schema.WorkflowConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.WorkflowConfig])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workflowConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowConfigs/${workflowConfigsId}").addQueryStringParameters("name" -> name.toString))
					}
					/** Lists WorkflowConfigs in a given Repository. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListWorkflowConfigsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Maximum number of workflow configs to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token received from a previous `ListWorkflowConfigs` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListWorkflowConfigs`, with the exception of `page_size`, must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListWorkflowConfigsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workflowConfigs").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListWorkflowConfigsResponse]] = (fun: list) => fun.apply()
					}
				}
				object workspaces {
					/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Deletes a file (inside a Workspace). */
					class removeFile(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withRemoveFileRequest(body: Schema.RemoveFileRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object removeFile {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String)(using signer: RequestSigner, ec: ExecutionContext): removeFile = new removeFile(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:removeFile").addQueryStringParameters("workspace" -> workspace.toString))
					}
					/** Finds the contents of a given Workspace directory by filter. */
					class searchFiles(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchFilesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Maximum number of search results to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new searchFiles(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token received from a previous `SearchFilesRequest` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `SearchFilesRequest`, with the exception of `page_size`, must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new searchFiles(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Optional filter for the returned list in filtering format. Filtering is only currently supported on the `path` field. See https://google.aip.dev/160 for details. */
						def withFilter(filter: String) = new searchFiles(req.addQueryStringParameters("filter" -> filter.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchFilesResponse])
					}
					object searchFiles {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String)(using signer: RequestSigner, ec: ExecutionContext): searchFiles = new searchFiles(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:searchFiles").addQueryStringParameters("workspace" -> workspace.toString))
						given Conversion[searchFiles, Future[Schema.SearchFilesResponse]] = (fun: searchFiles) => fun.apply()
					}
					/** Applies a Git commit for uncommitted files in a Workspace. */
					class commit(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withCommitWorkspaceChangesRequest(body: Schema.CommitWorkspaceChangesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object commit {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): commit = new commit(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:commit").addQueryStringParameters("name" -> name.toString))
					}
					/** Fetches Git statuses for the files in a Workspace. */
					class fetchFileGitStatuses(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FetchFileGitStatusesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FetchFileGitStatusesResponse])
					}
					object fetchFileGitStatuses {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): fetchFileGitStatuses = new fetchFileGitStatuses(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:fetchFileGitStatuses").addQueryStringParameters("name" -> name.toString))
						given Conversion[fetchFileGitStatuses, Future[Schema.FetchFileGitStatusesResponse]] = (fun: fetchFileGitStatuses) => fun.apply()
					}
					/** Pushes Git commits from a Workspace to the Repository's remote. */
					class push(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withPushGitCommitsRequest(body: Schema.PushGitCommitsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object push {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): push = new push(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:push").addQueryStringParameters("name" -> name.toString))
					}
					/** Fetches Git ahead/behind against a remote branch. */
					class fetchGitAheadBehind(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FetchGitAheadBehindResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The name of the branch in the Git remote against which this workspace should be compared. If left unset, the repository's default branch name will be used. */
						def withRemoteBranch(remoteBranch: String) = new fetchGitAheadBehind(req.addQueryStringParameters("remoteBranch" -> remoteBranch.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FetchGitAheadBehindResponse])
					}
					object fetchGitAheadBehind {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): fetchGitAheadBehind = new fetchGitAheadBehind(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:fetchGitAheadBehind").addQueryStringParameters("name" -> name.toString))
						given Conversion[fetchGitAheadBehind, Future[Schema.FetchGitAheadBehindResponse]] = (fun: fetchGitAheadBehind) => fun.apply()
					}
					/** Returns the contents of a file (inside a Workspace). */
					class readFile(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ReadFileResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The Git revision of the file to return. If left empty, the current contents of `path` will be returned. */
						def withRevision(revision: String) = new readFile(req.addQueryStringParameters("revision" -> revision.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ReadFileResponse])
					}
					object readFile {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String, path: String)(using signer: RequestSigner, ec: ExecutionContext): readFile = new readFile(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:readFile").addQueryStringParameters("workspace" -> workspace.toString, "path" -> path.toString))
						given Conversion[readFile, Future[Schema.ReadFileResponse]] = (fun: readFile) => fun.apply()
					}
					/** Writes to a file (inside a Workspace). */
					class writeFile(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withWriteFileRequest(body: Schema.WriteFileRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.WriteFileResponse])
					}
					object writeFile {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String)(using signer: RequestSigner, ec: ExecutionContext): writeFile = new writeFile(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:writeFile").addQueryStringParameters("workspace" -> workspace.toString))
					}
					/** Deletes a single Workspace. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Deletes a directory (inside a Workspace) and all of its contents. */
					class removeDirectory(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withRemoveDirectoryRequest(body: Schema.RemoveDirectoryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object removeDirectory {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String)(using signer: RequestSigner, ec: ExecutionContext): removeDirectory = new removeDirectory(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:removeDirectory").addQueryStringParameters("workspace" -> workspace.toString))
					}
					/** Pulls Git commits from the Repository's remote into a Workspace. */
					class pull(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withPullGitCommitsRequest(body: Schema.PullGitCommitsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object pull {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): pull = new pull(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:pull").addQueryStringParameters("name" -> name.toString))
					}
					/** Fetches a single Workspace. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Workspace]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Workspace])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Workspace]] = (fun: get) => fun.apply()
					}
					/** Installs dependency NPM packages (inside a Workspace). */
					class installNpmPackages(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withInstallNpmPackagesRequest(body: Schema.InstallNpmPackagesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.InstallNpmPackagesResponse])
					}
					object installNpmPackages {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String)(using signer: RequestSigner, ec: ExecutionContext): installNpmPackages = new installNpmPackages(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:installNpmPackages").addQueryStringParameters("workspace" -> workspace.toString))
					}
					/** Moves a file (inside a Workspace) to a new location. */
					class moveFile(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withMoveFileRequest(body: Schema.MoveFileRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.MoveFileResponse])
					}
					object moveFile {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String)(using signer: RequestSigner, ec: ExecutionContext): moveFile = new moveFile(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:moveFile").addQueryStringParameters("workspace" -> workspace.toString))
					}
					/** Returns the contents of a given Workspace directory. */
					class queryDirectoryContents(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.QueryDirectoryContentsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The directory's full path including directory name, relative to the workspace root. If left unset, the workspace root is used. */
						def withPath(path: String) = new queryDirectoryContents(req.addQueryStringParameters("path" -> path.toString))
						/** Optional. Maximum number of paths to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new queryDirectoryContents(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token received from a previous `QueryDirectoryContents` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `QueryDirectoryContents`, with the exception of `page_size`, must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new queryDirectoryContents(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.QueryDirectoryContentsResponse])
					}
					object queryDirectoryContents {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String)(using signer: RequestSigner, ec: ExecutionContext): queryDirectoryContents = new queryDirectoryContents(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:queryDirectoryContents").addQueryStringParameters("workspace" -> workspace.toString))
						given Conversion[queryDirectoryContents, Future[Schema.QueryDirectoryContentsResponse]] = (fun: queryDirectoryContents) => fun.apply()
					}
					/** Creates a new Workspace in a given Repository. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withWorkspace(body: Schema.Workspace) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Workspace])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String, workspaceId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces").addQueryStringParameters("parent" -> parent.toString, "workspaceId" -> workspaceId.toString))
					}
					/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
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
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					/** Fetches Git diff for an uncommitted file in a Workspace. */
					class fetchFileDiff(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FetchFileDiffResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FetchFileDiffResponse])
					}
					object fetchFileDiff {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String, path: String)(using signer: RequestSigner, ec: ExecutionContext): fetchFileDiff = new fetchFileDiff(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:fetchFileDiff").addQueryStringParameters("workspace" -> workspace.toString, "path" -> path.toString))
						given Conversion[fetchFileDiff, Future[Schema.FetchFileDiffResponse]] = (fun: fetchFileDiff) => fun.apply()
					}
					/** Performs a Git reset for uncommitted files in a Workspace. */
					class reset(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withResetWorkspaceChangesRequest(body: Schema.ResetWorkspaceChangesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object reset {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): reset = new reset(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:reset").addQueryStringParameters("name" -> name.toString))
					}
					/** Moves a directory (inside a Workspace), and all of its contents, to a new location. */
					class moveDirectory(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withMoveDirectoryRequest(body: Schema.MoveDirectoryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.MoveDirectoryResponse])
					}
					object moveDirectory {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String)(using signer: RequestSigner, ec: ExecutionContext): moveDirectory = new moveDirectory(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:moveDirectory").addQueryStringParameters("workspace" -> workspace.toString))
					}
					/** Lists Workspaces in a given Repository. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListWorkspacesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Maximum number of workspaces to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token received from a previous `ListWorkspaces` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListWorkspaces`, with the exception of `page_size`, must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. This field only supports ordering by `name`. If unspecified, the server will choose the ordering. If specified, the default order is ascending for the `name` field. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Optional. Filter for the returned list. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListWorkspacesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListWorkspacesResponse]] = (fun: list) => fun.apply()
					}
					/** Creates a directory inside a Workspace. */
					class makeDirectory(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withMakeDirectoryRequest(body: Schema.MakeDirectoryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.MakeDirectoryResponse])
					}
					object makeDirectory {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, workspacesId :PlayApi, workspace: String)(using signer: RequestSigner, ec: ExecutionContext): makeDirectory = new makeDirectory(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/workspaces/${workspacesId}:makeDirectory").addQueryStringParameters("workspace" -> workspace.toString))
					}
				}
				object compilationResults {
					/** Lists CompilationResults in a given Repository. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCompilationResultsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Maximum number of compilation results to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token received from a previous `ListCompilationResults` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListCompilationResults`, with the exception of `page_size`, must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. This field only supports ordering by `name` and `create_time`. If unspecified, the server will choose the ordering. If specified, the default order is ascending for the `name` field. */
						def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
						/** Optional. Filter for the returned list. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCompilationResultsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/compilationResults").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListCompilationResultsResponse]] = (fun: list) => fun.apply()
					}
					/** Fetches a single CompilationResult. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CompilationResult]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CompilationResult])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, compilationResultsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/compilationResults/${compilationResultsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.CompilationResult]] = (fun: get) => fun.apply()
					}
					/** Creates a new CompilationResult in a given project and location. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withCompilationResult(body: Schema.CompilationResult) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CompilationResult])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/compilationResults").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Returns CompilationResultActions in a given CompilationResult. */
					class query(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.QueryCompilationResultActionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Maximum number of compilation results to return. The server may return fewer items than requested. If unspecified, the server will pick an appropriate default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new query(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page token received from a previous `QueryCompilationResultActions` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `QueryCompilationResultActions`, with the exception of `page_size`, must match the call that provided the page token. */
						def withPageToken(pageToken: String) = new query(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Optional filter for the returned list. Filtering is only currently supported on the `file_path` field. */
						def withFilter(filter: String) = new query(req.addQueryStringParameters("filter" -> filter.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.QueryCompilationResultActionsResponse])
					}
					object query {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, compilationResultsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): query = new query(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/compilationResults/${compilationResultsId}:query").addQueryStringParameters("name" -> name.toString))
						given Conversion[query, Future[Schema.QueryCompilationResultActionsResponse]] = (fun: query) => fun.apply()
					}
				}
				object commentThreads {
					/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, commentThreadsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/commentThreads/${commentThreadsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
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
						def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, commentThreadsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/commentThreads/${commentThreadsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
						given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
					}
					object comments {
						/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
						class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
						}
						object setIamPolicy {
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, commentThreadsId :PlayApi, commentsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/commentThreads/${commentThreadsId}/comments/${commentsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
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
							def apply(projectsId :PlayApi, locationsId :PlayApi, repositoriesId :PlayApi, commentThreadsId :PlayApi, commentsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/repositories/${repositoriesId}/commentThreads/${commentThreadsId}/comments/${commentsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
							given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
						}
					}
				}
			}
		}
	}
}
