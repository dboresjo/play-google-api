package com.boresjo.play.api.google.cloudresourcemanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://cloudresourcemanager.googleapis.com/"

	object liens {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLiensResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLiensResponse])
		}
		object list {
			def apply(parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/liens").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListLiensResponse]] = (fun: list) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Lien]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Lien])
		}
		object get {
			def apply(liensId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/liens/${liensId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Lien]] = (fun: get) => fun.apply()
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLien(body: Schema.Lien) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Lien])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/liens").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(liensId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/liens/${liensId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
	}
	object operations {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
	}
	object folders {
		class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(foldersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v3/folders/${foldersId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
		class move(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withMoveFolderRequest(body: Schema.MoveFolderRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object move {
			def apply(foldersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"v3/folders/${foldersId}:move").addQueryStringParameters("name" -> name.toString))
		}
		class undelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withUndeleteFolderRequest(body: Schema.UndeleteFolderRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object undelete {
			def apply(foldersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v3/folders/${foldersId}:undelete").addQueryStringParameters("name" -> name.toString))
		}
		class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(foldersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v3/folders/${foldersId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(foldersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/folders/${foldersId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Folder]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Folder])
		}
		object get {
			def apply(foldersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/folders/${foldersId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Folder]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withFolder(body: Schema.Folder) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(foldersId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/folders/${foldersId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withFolder(body: Schema.Folder) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/folders").addQueryStringParameters())
		}
		class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(foldersId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v3/folders/${foldersId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFoldersResponse]) {
			/** Optional. The maximum number of folders to return in the response. The server can return fewer folders than requested. If unspecified, server picks an appropriate default.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A pagination token returned from a previous call to `ListFolders` that indicates where this listing should continue from. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. Controls whether folders in the DELETE_REQUESTED state should be returned. Defaults to false. */
			def withShowDeleted(showDeleted: Boolean) = new list(req.addQueryStringParameters("showDeleted" -> showDeleted.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListFoldersResponse])
		}
		object list {
			def apply(parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/folders").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.ListFoldersResponse]] = (fun: list) => fun.apply()
		}
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchFoldersResponse]) {
			/** Optional. The maximum number of folders to return in the response. The server can return fewer folders than requested. If unspecified, server picks an appropriate default.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new search(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A pagination token returned from a previous call to `SearchFolders` that indicates from where search should continue. */
			def withPageToken(pageToken: String) = new search(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. Search criteria used to select the folders to return. If no search criteria is specified then all accessible folders will be returned. Query expressions can be used to restrict results based upon displayName, state and parent, where the operators `=` (`:`) `NOT`, `AND` and `OR` can be used along with the suffix wildcard symbol `&#42;`. The `displayName` field in a query expression should use escaped quotes for values that include whitespace to prevent unexpected behavior. ``` | Field | Description | |-------------------------|----------------------------------------| | displayName | Filters by displayName. | | parent | Filters by parent (for example: folders/123). | | state, lifecycleState | Filters by state. | ``` Some example queries are: &#42; Query `displayName=Test&#42;` returns Folder resources whose display name starts with "Test". &#42; Query `state=ACTIVE` returns Folder resources with `state` set to `ACTIVE`. &#42; Query `parent=folders/123` returns Folder resources that have `folders/123` as a parent resource. &#42; Query `parent=folders/123 AND state=ACTIVE` returns active Folder resources that have `folders/123` as a parent resource. &#42; Query `displayName=\\"Test String\\"` returns Folder resources with display names that include both "Test" and "String". */
			def withQuery(query: String) = new search(req.addQueryStringParameters("query" -> query.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SearchFoldersResponse])
		}
		object search {
			def apply()(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v3/folders:search").addQueryStringParameters())
			given Conversion[search, Future[Schema.SearchFoldersResponse]] = (fun: search) => fun.apply()
		}
	}
	object organizations {
		class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(organizationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v3/organizations/${organizationsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
		class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(organizationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v3/organizations/${organizationsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(organizationsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v3/organizations/${organizationsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Organization]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Organization])
		}
		object get {
			def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/organizations/${organizationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Organization]] = (fun: get) => fun.apply()
		}
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchOrganizationsResponse]) {
			/** Optional. The maximum number of organizations to return in the response. The server can return fewer organizations than requested. If unspecified, server picks an appropriate default.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new search(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A pagination token returned from a previous call to `SearchOrganizations` that indicates from where listing should continue. */
			def withPageToken(pageToken: String) = new search(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. An optional query string used to filter the Organizations to return in the response. Query rules are case-insensitive. ``` | Field | Description | |------------------|--------------------------------------------| | directoryCustomerId, owner.directoryCustomerId | Filters by directory customer id. | | domain | Filters by domain. | ``` Organizations may be queried by `directoryCustomerId` or by `domain`, where the domain is a G Suite domain, for example: &#42; Query `directorycustomerid:123456789` returns Organization resources with `owner.directory_customer_id` equal to `123456789`. &#42; Query `domain:google.com` returns Organization resources corresponding to the domain `google.com`. */
			def withQuery(query: String) = new search(req.addQueryStringParameters("query" -> query.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SearchOrganizationsResponse])
		}
		object search {
			def apply()(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v3/organizations:search").addQueryStringParameters())
			given Conversion[search, Future[Schema.SearchOrganizationsResponse]] = (fun: search) => fun.apply()
		}
	}
	object projects {
		class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(projectsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v3/projects/${projectsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
		class move(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withMoveProjectRequest(body: Schema.MoveProjectRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object move {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"v3/projects/${projectsId}:move").addQueryStringParameters("name" -> name.toString))
		}
		class undelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withUndeleteProjectRequest(body: Schema.UndeleteProjectRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object undelete {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v3/projects/${projectsId}:undelete").addQueryStringParameters("name" -> name.toString))
		}
		class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(projectsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v3/projects/${projectsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Project]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Project])
		}
		object get {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Project]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. An update mask to selectively update fields.<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			def withProject(body: Schema.Project) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}").addQueryStringParameters("name" -> name.toString))
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withProject(body: Schema.Project) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects").addQueryStringParameters())
		}
		class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(projectsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v3/projects/${projectsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListProjectsResponse]) {
			/** Optional. A pagination token returned from a previous call to ListProjects that indicates from where listing should continue. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. The maximum number of projects to return in the response. The server can return fewer projects than requested. If unspecified, server picks an appropriate default.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. Indicate that projects in the `DELETE_REQUESTED` state should also be returned. Normally only `ACTIVE` projects are returned. */
			def withShowDeleted(showDeleted: Boolean) = new list(req.addQueryStringParameters("showDeleted" -> showDeleted.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListProjectsResponse])
		}
		object list {
			def apply(parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.ListProjectsResponse]] = (fun: list) => fun.apply()
		}
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchProjectsResponse]) {
			/** Optional. A query string for searching for projects that the caller has `resourcemanager.projects.get` permission to. If multiple fields are included in the query, then it will return results that match any of the fields. Some eligible fields are: ``` | Field | Description | |-------------------------|----------------------------------------------| | displayName, name | Filters by displayName. | | parent | Project's parent (for example: folders/123, organizations/&#42;). Prefer parent field over parent.type and parent.id.| | parent.type | Parent's type: `folder` or `organization`. | | parent.id | Parent's id number (for example: 123) | | id, projectId | Filters by projectId. | | state, lifecycleState | Filters by state. | | labels | Filters by label name or value. | | labels.\ (where &#42;key&#42; is the name of a label) | Filters by label name.| ``` Search expressions are case insensitive. Some examples queries: ``` | Query | Description | |------------------|-----------------------------------------------------| | name:how&#42; | The project's name starts with "how". | | name:Howl | The project's name is `Howl` or `howl`. | | name:HOWL | Equivalent to above. | | NAME:howl | Equivalent to above. | | labels.color:&#42; | The project has the label `color`. | | labels.color:red | The project's label `color` has the value `red`. | | labels.color:red labels.size:big | The project's label `color` has the value `red` or its label `size` has the value `big`. | ``` If no query is specified, the call will return projects for which the user has the `resourcemanager.projects.get` permission. */
			def withQuery(query: String) = new search(req.addQueryStringParameters("query" -> query.toString))
			/** Optional. A pagination token returned from a previous call to ListProjects that indicates from where listing should continue. */
			def withPageToken(pageToken: String) = new search(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. The maximum number of projects to return in the response. The server can return fewer projects than requested. If unspecified, server picks an appropriate default.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new search(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SearchProjectsResponse])
		}
		object search {
			def apply()(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v3/projects:search").addQueryStringParameters())
			given Conversion[search, Future[Schema.SearchProjectsResponse]] = (fun: search) => fun.apply()
		}
	}
	object tagBindings {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTagBindingsResponse]) {
			/** Optional. The maximum number of TagBindings to return in the response. The server allows a maximum of 300 TagBindings to return. If unspecified, the server will use 100 as the default.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A pagination token returned from a previous call to `ListTagBindings` that indicates where this listing should continue from. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListTagBindingsResponse])
		}
		object list {
			def apply(parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/tagBindings").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.ListTagBindingsResponse]] = (fun: list) => fun.apply()
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. Set to true to perform the validations necessary for creating the resource, but not actually perform the action. */
			def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
			def withTagBinding(body: Schema.TagBinding) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/tagBindings").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(tagBindingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/tagBindings/${tagBindingsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
	}
	object effectiveTags {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEffectiveTagsResponse]) {
			/** Optional. The maximum number of effective tags to return in the response. The server allows a maximum of 300 effective tags to return in a single page. If unspecified, the server will use 100 as the default.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A pagination token returned from a previous call to `ListEffectiveTags` that indicates from where this listing should continue. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListEffectiveTagsResponse])
		}
		object list {
			def apply(parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/effectiveTags").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.ListEffectiveTagsResponse]] = (fun: list) => fun.apply()
		}
	}
	object tagKeys {
		class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(tagKeysId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v3/tagKeys/${tagKeysId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
		class getNamespaced(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TagKey]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.TagKey])
		}
		object getNamespaced {
			def apply(name: String)(using auth: AuthToken, ec: ExecutionContext): getNamespaced = new getNamespaced(ws.url(BASE_URL + s"v3/tagKeys/namespaced").addQueryStringParameters("name" -> name.toString))
			given Conversion[getNamespaced, Future[Schema.TagKey]] = (fun: getNamespaced) => fun.apply()
		}
		class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(tagKeysId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v3/tagKeys/${tagKeysId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			/** Optional. Set as true to perform validations necessary for deletion, but not actually perform the action. */
			def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
			/** Optional. The etag known to the client for the expected state of the TagKey. This is to be used for optimistic concurrency. */
			def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(tagKeysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/tagKeys/${tagKeysId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TagKey]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.TagKey])
		}
		object get {
			def apply(tagKeysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/tagKeys/${tagKeysId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.TagKey]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTagKey(body: Schema.TagKey) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(tagKeysId :PlayApi, name: String, updateMask: String, validateOnly: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/tagKeys/${tagKeysId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "validateOnly" -> validateOnly.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTagKeysResponse]) {
			/** Optional. The maximum number of TagKeys to return in the response. The server allows a maximum of 300 TagKeys to return. If unspecified, the server will use 100 as the default.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A pagination token returned from a previous call to `ListTagKey` that indicates where this listing should continue from. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListTagKeysResponse])
		}
		object list {
			def apply(parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/tagKeys").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.ListTagKeysResponse]] = (fun: list) => fun.apply()
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. Set to true to perform validations necessary for creating the resource, but not actually perform the action. */
			def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
			def withTagKey(body: Schema.TagKey) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/tagKeys").addQueryStringParameters())
		}
		class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(tagKeysId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v3/tagKeys/${tagKeysId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
	}
	object tagValues {
		class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(tagValuesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v3/tagValues/${tagValuesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
		class getNamespaced(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TagValue]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.TagValue])
		}
		object getNamespaced {
			def apply(name: String)(using auth: AuthToken, ec: ExecutionContext): getNamespaced = new getNamespaced(ws.url(BASE_URL + s"v3/tagValues/namespaced").addQueryStringParameters("name" -> name.toString))
			given Conversion[getNamespaced, Future[Schema.TagValue]] = (fun: getNamespaced) => fun.apply()
		}
		class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(tagValuesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v3/tagValues/${tagValuesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			/** Optional. Set as true to perform the validations necessary for deletion, but not actually perform the action. */
			def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
			/** Optional. The etag known to the client for the expected state of the TagValue. This is to be used for optimistic concurrency. */
			def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(tagValuesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/tagValues/${tagValuesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TagValue]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.TagValue])
		}
		object get {
			def apply(tagValuesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/tagValues/${tagValuesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.TagValue]] = (fun: get) => fun.apply()
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. Fields to be updated.<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			/** Optional. True to perform validations necessary for updating the resource, but not actually perform the action. */
			def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
			def withTagValue(body: Schema.TagValue) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(tagValuesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/tagValues/${tagValuesId}").addQueryStringParameters("name" -> name.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTagValuesResponse]) {
			/** Optional. The maximum number of TagValues to return in the response. The server allows a maximum of 300 TagValues to return. If unspecified, the server will use 100 as the default.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A pagination token returned from a previous call to `ListTagValues` that indicates where this listing should continue from. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListTagValuesResponse])
		}
		object list {
			def apply(parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/tagValues").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.ListTagValuesResponse]] = (fun: list) => fun.apply()
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. Set as true to perform the validations necessary for creating the resource, but not actually perform the action. */
			def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
			def withTagValue(body: Schema.TagValue) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/tagValues").addQueryStringParameters())
		}
		class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(tagValuesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v3/tagValues/${tagValuesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		object tagHolds {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. Set to true to perform the validations necessary for creating the resource, but not actually perform the action. */
				def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
				def withTagHold(body: Schema.TagHold) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(tagValuesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/tagValues/${tagValuesId}/tagHolds").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				/** Optional. Set to true to perform the validations necessary for deleting the resource, but not actually perform the action. */
				def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
			}
			object delete {
				def apply(tagValuesId :PlayApi, tagHoldsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/tagValues/${tagValuesId}/tagHolds/${tagHoldsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTagHoldsResponse]) {
				/** Optional. The maximum number of TagHolds to return in the response. The server allows a maximum of 300 TagHolds to return. If unspecified, the server will use 100 as the default.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A pagination token returned from a previous call to `ListTagHolds` that indicates where this listing should continue from. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. Criteria used to select a subset of TagHolds parented by the TagValue to return. This field follows the syntax defined by aip.dev/160; the `holder` and `origin` fields are supported for filtering. Currently only `AND` syntax is supported. Some example queries are: &#42; `holder = //compute.googleapis.com/compute/projects/myproject/regions/us-east-1/instanceGroupManagers/instance-group` &#42; `origin = 35678234` &#42; `holder = //compute.googleapis.com/compute/projects/myproject/regions/us-east-1/instanceGroupManagers/instance-group AND origin = 35678234` */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListTagHoldsResponse])
			}
			object list {
				def apply(tagValuesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/tagValues/${tagValuesId}/tagHolds").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListTagHoldsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
