package com.boresjo.play.api.google.cloudbuild

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://cloudbuild.googleapis.com/"

	object projects {
		object locations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLocationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, pageToken: String, pageSize: Int, name: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "name" -> name.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Location]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Location]] = (fun: get) => fun.apply()
			}
			object connections {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				class processWebhook(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withHttpBody(body: Schema.HttpBody) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object processWebhook {
					def apply(projectsId :PlayApi, locationsId :PlayApi, webhookKey: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): processWebhook = new processWebhook(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections:processWebhook").addQueryStringParameters("webhookKey" -> webhookKey.toString, "parent" -> parent.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Policy]) {
					/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
					def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					given Conversion[getIamPolicy, Future[Schema.Policy]] = (fun: getIamPolicy) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, validateOnly: Boolean, etag: String, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}").addQueryStringParameters("validateOnly" -> validateOnly.toString, "etag" -> etag.toString, "name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Connection]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Connection])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Connection]] = (fun: get) => fun.apply()
				}
				class fetchLinkableRepositories(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FetchLinkableRepositoriesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.FetchLinkableRepositoriesResponse])
				}
				object fetchLinkableRepositories {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, connection: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): fetchLinkableRepositories = new fetchLinkableRepositories(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}:fetchLinkableRepositories").addQueryStringParameters("connection" -> connection.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[fetchLinkableRepositories, Future[Schema.FetchLinkableRepositoriesResponse]] = (fun: fetchLinkableRepositories) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withConnection(body: Schema.Connection) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, etag: String, name: String, updateMask: String, allowMissing: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}").addQueryStringParameters("etag" -> etag.toString, "name" -> name.toString, "updateMask" -> updateMask.toString, "allowMissing" -> allowMissing.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListConnectionsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListConnectionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.ListConnectionsResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withConnection(body: Schema.Connection) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionId: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections").addQueryStringParameters("connectionId" -> connectionId.toString, "parent" -> parent.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object repositories {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withRepository(body: Schema.Repository) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, repositoryId: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/repositories").addQueryStringParameters("repositoryId" -> repositoryId.toString, "parent" -> parent.toString))
					}
					class accessReadToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withFetchReadTokenRequest(body: Schema.FetchReadTokenRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FetchReadTokenResponse])
					}
					object accessReadToken {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, repositoriesId :PlayApi, repository: String)(using auth: AuthToken, ec: ExecutionContext): accessReadToken = new accessReadToken(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/repositories/${repositoriesId}:accessReadToken").addQueryStringParameters("repository" -> repository.toString))
					}
					class batchCreate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withBatchCreateRepositoriesRequest(body: Schema.BatchCreateRepositoriesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
					}
					object batchCreate {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchCreate = new batchCreate(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/repositories:batchCreate").addQueryStringParameters("parent" -> parent.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Repository]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Repository])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, repositoriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/repositories/${repositoriesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Repository]] = (fun: get) => fun.apply()
					}
					class fetchGitRefs(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FetchGitRefsResponse]) {
						/** Optional. Number of results to return in the list. Default to 20.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new fetchGitRefs(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Page start. */
						def withPageToken(pageToken: String) = new fetchGitRefs(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.FetchGitRefsResponse])
					}
					object fetchGitRefs {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, repositoriesId :PlayApi, refType: String, repository: String)(using auth: AuthToken, ec: ExecutionContext): fetchGitRefs = new fetchGitRefs(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/repositories/${repositoriesId}:fetchGitRefs").addQueryStringParameters("refType" -> refType.toString, "repository" -> repository.toString))
						given Conversion[fetchGitRefs, Future[Schema.FetchGitRefsResponse]] = (fun: fetchGitRefs) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListRepositoriesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListRepositoriesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/repositories").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.ListRepositoriesResponse]] = (fun: list) => fun.apply()
					}
					class accessReadWriteToken(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withFetchReadWriteTokenRequest(body: Schema.FetchReadWriteTokenRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FetchReadWriteTokenResponse])
					}
					object accessReadWriteToken {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, repositoriesId :PlayApi, repository: String)(using auth: AuthToken, ec: ExecutionContext): accessReadWriteToken = new accessReadWriteToken(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/repositories/${repositoriesId}:accessReadWriteToken").addQueryStringParameters("repository" -> repository.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, connectionsId :PlayApi, repositoriesId :PlayApi, etag: String, name: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/connections/${connectionsId}/repositories/${repositoriesId}").addQueryStringParameters("etag" -> etag.toString, "name" -> name.toString, "validateOnly" -> validateOnly.toString))
						given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
					}
				}
			}
			object operations {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCancelOperationRequest(body: Schema.CancelOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
			}
		}
	}
}
