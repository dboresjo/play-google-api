package com.boresjo.play.api.google.developerconnect

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

	private val BASE_URL = "https://developerconnect.googleapis.com/"

	object projects {
		object locations {
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
			object connections {
				/** FetchLinkableGitRepositories returns a list of git repositories from an SCM that are available to be added to a Connection. */
				class fetchLinkableGitRepositories(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FetchLinkableGitRepositoriesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Number of results to return in the list. Defaults to 20.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new fetchLinkableGitRepositories(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Page start. */
					def withPageToken(pageToken: String) = new fetchLinkableGitRepositories(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FetchLinkableGitRepositoriesResponse])
				}
				object fetchLinkableGitRepositories {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, connection: String)(using signer: RequestSigner, ec: ExecutionContext): fetchLinkableGitRepositories = new fetchLinkableGitRepositories(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}:fetchLinkableGitRepositories").addQueryStringParameters("connection" -> connection.toString))
					given Conversion[fetchLinkableGitRepositories, Future[Schema.FetchLinkableGitRepositoriesResponse]] = (fun: fetchLinkableGitRepositories) => fun.apply()
				}
				/** Creates a new Connection in a given project and location. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set, validate the request, but do not actually post it. */
					def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Perform the request */
					def withConnection(body: Schema.Connection) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, connectionId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections").addQueryStringParameters("parent" -> parent.toString, "connectionId" -> connectionId.toString))
				}
				/** ProcessGitHubEnterpriseWebhook is called by the external GitHub Enterprise instances for notifying events. */
				class processGitHubEnterpriseWebhook(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq()
					/** Perform the request */
					def withProcessGitHubEnterpriseWebhookRequest(body: Schema.ProcessGitHubEnterpriseWebhookRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object processGitHubEnterpriseWebhook {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): processGitHubEnterpriseWebhook = new processGitHubEnterpriseWebhook(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections:processGitHubEnterpriseWebhook").addQueryStringParameters("parent" -> parent.toString))
				}
				/** FetchGitHubInstallations returns the list of GitHub Installations that are available to be added to a Connection. For github.com, only installations accessible to the authorizer token are returned. For GitHub Enterprise, all installations are returned. */
				class fetchGitHubInstallations(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FetchGitHubInstallationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FetchGitHubInstallationsResponse])
				}
				object fetchGitHubInstallations {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, connection: String)(using signer: RequestSigner, ec: ExecutionContext): fetchGitHubInstallations = new fetchGitHubInstallations(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}:fetchGitHubInstallations").addQueryStringParameters("connection" -> connection.toString))
					given Conversion[fetchGitHubInstallations, Future[Schema.FetchGitHubInstallationsResponse]] = (fun: fetchGitHubInstallations) => fun.apply()
				}
				/** Deletes a single Connection. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set, validate the request, but do not actually post it. */
					def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Optional. The current etag of the Connection. If an etag is provided and does not match the current etag of the Connection, deletion will be blocked and an ABORTED error will be returned. */
					def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Gets details of a single Connection. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Connection]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Connection])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Connection]] = (fun: get) => fun.apply()
				}
				/** Updates the parameters of a single Connection. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
					def withRequestId(requestId: String) = new patch(req.addQueryStringParameters("requestId" -> requestId.toString))
					/** Optional. If set to true, and the connection is not found a new connection will be created. In this situation `update_mask` is ignored. The creation will succeed only if the input connection has all the necessary information (e.g a github_config with both user_oauth_token and installation_id properties). */
					def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
					/** Optional. If set, validate the request, but do not actually post it. */
					def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
					/** Perform the request */
					def withConnection(body: Schema.Connection) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists Connections in a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListConnectionsResponse]) {
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
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListConnectionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListConnectionsResponse]] = (fun: list) => fun.apply()
				}
				object gitRepositoryLinks {
					/** ProcessGitLabWebhook is called by the GitLab.com for notifying events. */
					class processGitLabWebhook(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq()
						/** Perform the request */
						def withProcessGitLabWebhookRequest(body: Schema.ProcessGitLabWebhookRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object processGitLabWebhook {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, gitRepositoryLinksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): processGitLabWebhook = new processGitLabWebhook(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/gitRepositoryLinks/${gitRepositoryLinksId}:processGitLabWebhook").addQueryStringParameters("name" -> name.toString))
					}
					/** ProcessGitLabEnterpriseWebhook is called by the external GitLab Enterprise instances for notifying events. */
					class processGitLabEnterpriseWebhook(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq()
						/** Perform the request */
						def withProcessGitLabEnterpriseWebhookRequest(body: Schema.ProcessGitLabEnterpriseWebhookRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
					}
					object processGitLabEnterpriseWebhook {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, gitRepositoryLinksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): processGitLabEnterpriseWebhook = new processGitLabEnterpriseWebhook(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/gitRepositoryLinks/${gitRepositoryLinksId}:processGitLabEnterpriseWebhook").addQueryStringParameters("name" -> name.toString))
					}
					/** Creates a GitRepositoryLink. Upon linking a Git Repository, Developer Connect will configure the Git Repository to send webhook events to Developer Connect. Connections that use Firebase GitHub Application will have events forwarded to the Firebase service. All other Connections will have events forwarded to Cloud Build. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Optional. If set, validate the request, but do not actually post it. */
						def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						/** Perform the request */
						def withGitRepositoryLink(body: Schema.GitRepositoryLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, parent: String, gitRepositoryLinkId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/gitRepositoryLinks").addQueryStringParameters("parent" -> parent.toString, "gitRepositoryLinkId" -> gitRepositoryLinkId.toString))
					}
					/** Fetches read token of a given gitRepositoryLink. */
					class fetchReadToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withFetchReadTokenRequest(body: Schema.FetchReadTokenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FetchReadTokenResponse])
					}
					object fetchReadToken {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, gitRepositoryLinksId :PlayApi, gitRepositoryLink: String)(using signer: RequestSigner, ec: ExecutionContext): fetchReadToken = new fetchReadToken(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/gitRepositoryLinks/${gitRepositoryLinksId}:fetchReadToken").addQueryStringParameters("gitRepositoryLink" -> gitRepositoryLink.toString))
					}
					/** Fetches read/write token of a given gitRepositoryLink. */
					class fetchReadWriteToken(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withFetchReadWriteTokenRequest(body: Schema.FetchReadWriteTokenRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FetchReadWriteTokenResponse])
					}
					object fetchReadWriteToken {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, gitRepositoryLinksId :PlayApi, gitRepositoryLink: String)(using signer: RequestSigner, ec: ExecutionContext): fetchReadWriteToken = new fetchReadWriteToken(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/gitRepositoryLinks/${gitRepositoryLinksId}:fetchReadWriteToken").addQueryStringParameters("gitRepositoryLink" -> gitRepositoryLink.toString))
					}
					/** Deletes a single GitRepositoryLink. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
						def withRequestId(requestId: String) = new delete(req.addQueryStringParameters("requestId" -> requestId.toString))
						/** Optional. If set, validate the request, but do not actually post it. */
						def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
						/** Optional. This checksum is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
						def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, gitRepositoryLinksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/gitRepositoryLinks/${gitRepositoryLinksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
					/** Gets details of a single GitRepositoryLink. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GitRepositoryLink]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GitRepositoryLink])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, gitRepositoryLinksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/gitRepositoryLinks/${gitRepositoryLinksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GitRepositoryLink]] = (fun: get) => fun.apply()
					}
					/** Fetch the list of branches or tags for a given repository. */
					class fetchGitRefs(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FetchGitRefsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Number of results to return in the list. Default to 20.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new fetchGitRefs(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page start. */
						def withPageToken(pageToken: String) = new fetchGitRefs(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FetchGitRefsResponse])
					}
					object fetchGitRefs {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, gitRepositoryLinksId :PlayApi, gitRepositoryLink: String, refType: String)(using signer: RequestSigner, ec: ExecutionContext): fetchGitRefs = new fetchGitRefs(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/gitRepositoryLinks/${gitRepositoryLinksId}:fetchGitRefs").addQueryStringParameters("gitRepositoryLink" -> gitRepositoryLink.toString, "refType" -> refType.toString))
						given Conversion[fetchGitRefs, Future[Schema.FetchGitRefsResponse]] = (fun: fetchGitRefs) => fun.apply()
					}
					/** Lists GitRepositoryLinks in a given project, location, and connection. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListGitRepositoryLinksResponse]) {
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
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListGitRepositoryLinksResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/gitRepositoryLinks").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.ListGitRepositoryLinksResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
}
