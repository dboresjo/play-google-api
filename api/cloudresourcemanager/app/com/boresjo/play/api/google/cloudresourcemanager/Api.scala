package com.boresjo.play.api.google.cloudresourcemanager

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */,
		"""https://www.googleapis.com/auth/cloud-platform.read-only""" /* View your data across Google Cloud services and see the email address of your Google Account */
	)

	private val BASE_URL = "https://cloudresourcemanager.googleapis.com/"

	object liens {
		/** List all Liens applied to the `parent` resource. Callers of this method will require permission on the `parent` resource. For example, a Lien with a `parent` of `projects/1234` requires permission `resourcemanager.projects.get`. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLiensResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLiensResponse])
		}
		object list {
			def apply(parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/liens").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListLiensResponse]] = (fun: list) => fun.apply()
		}
		/** Retrieve a Lien by `name`. Callers of this method will require permission on the `parent` resource. For example, a Lien with a `parent` of `projects/1234` requires permission `resourcemanager.projects.get` */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Lien]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Lien])
		}
		object get {
			def apply(liensId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/liens/${liensId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Lien]] = (fun: get) => fun.apply()
		}
		/** Create a Lien which applies to the resource denoted by the `parent` field. Callers of this method will require permission on the `parent` resource. For example, applying to `projects/1234` requires permission `resourcemanager.projects.updateLiens`. NOTE: Some resources may limit the number of Liens which may be applied. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def withLien(body: Schema.Lien) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Lien])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/liens").addQueryStringParameters())
		}
		/** Delete a Lien by `name`. Callers of this method will require permission on the `parent` resource. For example, a Lien with a `parent` of `projects/1234` requires permission `resourcemanager.projects.updateLiens`. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(liensId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/liens/${liensId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
	}
	object operations {
		/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
	}
	object folders {
		/** Returns permissions that a caller has on the specified folder. The `resource` field should be the folder's resource name, for example: "folders/1234". There are no permissions required for making this API call. */
		class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(foldersId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v3/folders/${foldersId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Moves a folder under a new resource parent. Returns an `Operation` which can be used to track the progress of the folder move workflow. Upon success, the `Operation.response` field will be populated with the moved folder. Upon failure, a `FolderOperationError` categorizing the failure cause will be returned - if the failure occurs synchronously then the `FolderOperationError` will be returned in the `Status.details` field. If it occurs asynchronously, then the FolderOperation will be returned in the `Operation.error` field. In addition, the `Operation.metadata` field will be populated with a `FolderOperation` message as an aid to stateless clients. Folder moves will be rejected if they violate either the naming, height, or fanout constraints described in the CreateFolder documentation. The caller must have `resourcemanager.folders.move` permission on the folder's current and proposed new parent. */
		class move(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withMoveFolderRequest(body: Schema.MoveFolderRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object move {
			def apply(foldersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"v3/folders/${foldersId}:move").addQueryStringParameters("name" -> name.toString))
		}
		/** Cancels the deletion request for a folder. This method may be called on a folder in any state. If the folder is in the ACTIVE state the result will be a no-op success. In order to succeed, the folder's parent must be in the ACTIVE state. In addition, reintroducing the folder into the tree must not violate folder naming, height, and fanout constraints described in the CreateFolder documentation. The caller must have `resourcemanager.folders.undelete` permission on the identified folder. */
		class undelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withUndeleteFolderRequest(body: Schema.UndeleteFolderRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object undelete {
			def apply(foldersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v3/folders/${foldersId}:undelete").addQueryStringParameters("name" -> name.toString))
		}
		/** Gets the access control policy for a folder. The returned policy may be empty if no such policy or resource exists. The `resource` field should be the folder's resource name, for example: "folders/1234". The caller must have `resourcemanager.folders.getIamPolicy` permission on the identified folder. */
		class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(foldersId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v3/folders/${foldersId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Requests deletion of a folder. The folder is moved into the DELETE_REQUESTED state immediately, and is deleted approximately 30 days later. This method may only be called on an empty folder, where a folder is empty if it doesn't contain any folders or projects in the ACTIVE state. If called on a folder in DELETE_REQUESTED state the operation will result in a no-op success. The caller must have `resourcemanager.folders.delete` permission on the identified folder. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(foldersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/folders/${foldersId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		/** Retrieves a folder identified by the supplied resource name. Valid folder resource names have the format `folders/{folder_id}` (for example, `folders/1234`). The caller must have `resourcemanager.folders.get` permission on the identified folder. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Folder]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Folder])
		}
		object get {
			def apply(foldersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/folders/${foldersId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Folder]] = (fun: get) => fun.apply()
		}
		/** Updates a folder, changing its `display_name`. Changes to the folder `display_name` will be rejected if they violate either the `display_name` formatting rules or the naming constraints described in the CreateFolder documentation. The folder's `display_name` must start and end with a letter or digit, may contain letters, digits, spaces, hyphens and underscores and can be between 3 and 30 characters. This is captured by the regular expression: `\p{L}\p{N}{1,28}[\p{L}\p{N}]`. The caller must have `resourcemanager.folders.update` permission on the identified folder. If the update fails due to the unique name constraint then a `PreconditionFailure` explaining this violation will be returned in the Status.details field. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withFolder(body: Schema.Folder) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(foldersId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/folders/${foldersId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		/** Creates a folder in the resource hierarchy. Returns an `Operation` which can be used to track the progress of the folder creation workflow. Upon success, the `Operation.response` field will be populated with the created Folder. In order to succeed, the addition of this new folder must not violate the folder naming, height, or fanout constraints. + The folder's `display_name` must be distinct from all other folders that share its parent. + The addition of the folder must not cause the active folder hierarchy to exceed a height of 10. Note, the full active + deleted folder hierarchy is allowed to reach a height of 20; this provides additional headroom when moving folders that contain deleted folders. + The addition of the folder must not cause the total number of folders under its parent to exceed 300. If the operation fails due to a folder constraint violation, some errors may be returned by the `CreateFolder` request, with status code `FAILED_PRECONDITION` and an error description. Other folder constraint violations will be communicated in the `Operation`, with the specific `PreconditionFailure` returned in the details list in the `Operation.error` field. The caller must have `resourcemanager.folders.create` permission on the identified parent. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withFolder(body: Schema.Folder) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/folders").addQueryStringParameters())
		}
		/** Sets the access control policy on a folder, replacing any existing policy. The `resource` field should be the folder's resource name, for example: "folders/1234". The caller must have `resourcemanager.folders.setIamPolicy` permission on the identified folder. */
		class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(foldersId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v3/folders/${foldersId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Lists the folders that are direct descendants of supplied parent resource. `list()` provides a strongly consistent view of the folders underneath the specified parent resource. `list()` returns folders sorted based upon the (ascending) lexical ordering of their display_name. The caller must have `resourcemanager.folders.list` permission on the identified parent. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFoldersResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Optional. The maximum number of folders to return in the response. The server can return fewer folders than requested. If unspecified, server picks an appropriate default.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A pagination token returned from a previous call to `ListFolders` that indicates where this listing should continue from. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. Controls whether folders in the DELETE_REQUESTED state should be returned. Defaults to false. */
			def withShowDeleted(showDeleted: Boolean) = new list(req.addQueryStringParameters("showDeleted" -> showDeleted.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFoldersResponse])
		}
		object list {
			def apply(parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/folders").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.ListFoldersResponse]] = (fun: list) => fun.apply()
		}
		/** Search for folders that match specific filter criteria. `search()` provides an eventually consistent view of the folders a user has access to which meet the specified filter criteria. This will only return folders on which the caller has the permission `resourcemanager.folders.get`. */
		class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchFoldersResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Optional. The maximum number of folders to return in the response. The server can return fewer folders than requested. If unspecified, server picks an appropriate default.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new search(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A pagination token returned from a previous call to `SearchFolders` that indicates from where search should continue. */
			def withPageToken(pageToken: String) = new search(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. Search criteria used to select the folders to return. If no search criteria is specified then all accessible folders will be returned. Query expressions can be used to restrict results based upon displayName, state and parent, where the operators `=` (`:`) `NOT`, `AND` and `OR` can be used along with the suffix wildcard symbol `&#42;`. The `displayName` field in a query expression should use escaped quotes for values that include whitespace to prevent unexpected behavior. ``` | Field | Description | |-------------------------|----------------------------------------| | displayName | Filters by displayName. | | parent | Filters by parent (for example: folders/123). | | state, lifecycleState | Filters by state. | ``` Some example queries are: &#42; Query `displayName=Test&#42;` returns Folder resources whose display name starts with "Test". &#42; Query `state=ACTIVE` returns Folder resources with `state` set to `ACTIVE`. &#42; Query `parent=folders/123` returns Folder resources that have `folders/123` as a parent resource. &#42; Query `parent=folders/123 AND state=ACTIVE` returns active Folder resources that have `folders/123` as a parent resource. &#42; Query `displayName=\\"Test String\\"` returns Folder resources with display names that include both "Test" and "String". */
			def withQuery(query: String) = new search(req.addQueryStringParameters("query" -> query.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchFoldersResponse])
		}
		object search {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v3/folders:search").addQueryStringParameters())
			given Conversion[search, Future[Schema.SearchFoldersResponse]] = (fun: search) => fun.apply()
		}
	}
	object organizations {
		/** Returns the permissions that a caller has on the specified organization. The `resource` field should be the organization's resource name, for example: "organizations/123". There are no permissions required for making this API call. */
		class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(organizationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v3/organizations/${organizationsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Sets the access control policy on an organization resource. Replaces any existing policy. The `resource` field should be the organization's resource name, for example: "organizations/123". Authorization requires the IAM permission `resourcemanager.organizations.setIamPolicy` on the specified organization. */
		class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(organizationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v3/organizations/${organizationsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Gets the access control policy for an organization resource. The policy may be empty if no such policy or resource exists. The `resource` field should be the organization's resource name, for example: "organizations/123". Authorization requires the IAM permission `resourcemanager.organizations.getIamPolicy` on the specified organization. */
		class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(organizationsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v3/organizations/${organizationsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Fetches an organization resource identified by the specified resource name. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Organization]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Organization])
		}
		object get {
			def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/organizations/${organizationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Organization]] = (fun: get) => fun.apply()
		}
		/** Searches organization resources that are visible to the user and satisfy the specified filter. This method returns organizations in an unspecified order. New organizations do not necessarily appear at the end of the results, and may take a small amount of time to appear. Search will only return organizations on which the user has the permission `resourcemanager.organizations.get` or has super admin privileges. */
		class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchOrganizationsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Optional. The maximum number of organizations to return in the response. The server can return fewer organizations than requested. If unspecified, server picks an appropriate default.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new search(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A pagination token returned from a previous call to `SearchOrganizations` that indicates from where listing should continue. */
			def withPageToken(pageToken: String) = new search(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. An optional query string used to filter the Organizations to return in the response. Query rules are case-insensitive. ``` | Field | Description | |------------------|--------------------------------------------| | directoryCustomerId, owner.directoryCustomerId | Filters by directory customer id. | | domain | Filters by domain. | ``` Organizations may be queried by `directoryCustomerId` or by `domain`, where the domain is a G Suite domain, for example: &#42; Query `directorycustomerid:123456789` returns Organization resources with `owner.directory_customer_id` equal to `123456789`. &#42; Query `domain:google.com` returns Organization resources corresponding to the domain `google.com`. */
			def withQuery(query: String) = new search(req.addQueryStringParameters("query" -> query.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchOrganizationsResponse])
		}
		object search {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v3/organizations:search").addQueryStringParameters())
			given Conversion[search, Future[Schema.SearchOrganizationsResponse]] = (fun: search) => fun.apply()
		}
	}
	object projects {
		/** Returns permissions that a caller has on the specified project, in the format `projects/{ProjectIdOrNumber}` e.g. projects/123.. */
		class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(projectsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v3/projects/${projectsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Move a project to another place in your resource hierarchy, under a new resource parent. Returns an operation which can be used to track the process of the project move workflow. Upon success, the `Operation.response` field will be populated with the moved project. The caller must have `resourcemanager.projects.move` permission on the project, on the project's current and proposed new parent. If project has no current parent, or it currently does not have an associated organization resource, you will also need the `resourcemanager.projects.setIamPolicy` permission in the project.  */
		class move(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withMoveProjectRequest(body: Schema.MoveProjectRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object move {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"v3/projects/${projectsId}:move").addQueryStringParameters("name" -> name.toString))
		}
		/** Restores the project identified by the specified `name` (for example, `projects/415104041262`). You can only use this method for a project that has a lifecycle state of DELETE_REQUESTED. After deletion starts, the project cannot be restored. The caller must have `resourcemanager.projects.undelete` permission for this project. */
		class undelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withUndeleteProjectRequest(body: Schema.UndeleteProjectRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object undelete {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v3/projects/${projectsId}:undelete").addQueryStringParameters("name" -> name.toString))
		}
		/** Returns the IAM access control policy for the specified project, in the format `projects/{ProjectIdOrNumber}` e.g. projects/123. Permission is denied if the policy or the resource do not exist. */
		class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(projectsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v3/projects/${projectsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Marks the project identified by the specified `name` (for example, `projects/415104041262`) for deletion. This method will only affect the project if it has a lifecycle state of ACTIVE. This method changes the Project's lifecycle state from ACTIVE to DELETE_REQUESTED. The deletion starts at an unspecified time, at which point the Project is no longer accessible. Until the deletion completes, you can check the lifecycle state checked by retrieving the project with GetProject, and the project remains visible to ListProjects. However, you cannot update the project. After the deletion completes, the project is not retrievable by the GetProject, ListProjects, and SearchProjects methods. This method behaves idempotently, such that deleting a `DELETE_REQUESTED` project will not cause an error, but also won't do anything. The caller must have `resourcemanager.projects.delete` permissions for this project. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		/** Retrieves the project identified by the specified `name` (for example, `projects/415104041262`). The caller must have `resourcemanager.projects.get` permission for this project. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Project]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Project])
		}
		object get {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Project]] = (fun: get) => fun.apply()
		}
		/** Updates the `display_name` and labels of the project identified by the specified `name` (for example, `projects/415104041262`). Deleting all labels requires an update mask for labels field. The caller must have `resourcemanager.projects.update` permission for this project. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. An update mask to selectively update fields.<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			/** Perform the request */
			def withProject(body: Schema.Project) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}").addQueryStringParameters("name" -> name.toString))
		}
		/** Request that a new project be created. The result is an `Operation` which can be used to track the creation process. This process usually takes a few seconds, but can sometimes take much longer. The tracking `Operation` is automatically deleted after a few hours, so there is no need to call `DeleteOperation`. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withProject(body: Schema.Project) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects").addQueryStringParameters())
		}
		/** Sets the IAM access control policy for the specified project, in the format `projects/{ProjectIdOrNumber}` e.g. projects/123. CAUTION: This method will replace the existing policy, and cannot be used to append additional IAM settings. Note: Removing service accounts from policies or changing their roles can render services completely inoperable. It is important to understand how the service account is being used before removing or updating its roles. The following constraints apply when using `setIamPolicy()`: + Project does not support `allUsers` and `allAuthenticatedUsers` as `members` in a `Binding` of a `Policy`. + The owner role can be granted to a `user`, `serviceAccount`, or a group that is part of an organization. For example, group@myownpersonaldomain.com could be added as an owner to a project in the myownpersonaldomain.com organization, but not the examplepetstore.com organization. + Service accounts can be made owners of a project directly without any restrictions. However, to be added as an owner, a user must be invited using the Cloud Platform console and must accept the invitation. + A user cannot be granted the owner role using `setIamPolicy()`. The user must be granted the owner role using the Cloud Platform Console and must explicitly accept the invitation. + Invitations to grant the owner role cannot be sent using `setIamPolicy()`; they must be sent only using the Cloud Platform Console. + If the project is not part of an organization, there must be at least one owner who has accepted the Terms of Service (ToS) agreement in the policy. Calling `setIamPolicy()` to remove the last ToS-accepted owner from the policy will fail. This restriction also applies to legacy projects that no longer have owners who have accepted the ToS. Edits to IAM policies will be rejected until the lack of a ToS-accepting owner is rectified. If the project is part of an organization, you can remove all owners, potentially making the organization inaccessible. */
		class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(projectsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v3/projects/${projectsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Lists projects that are direct children of the specified folder or organization resource. `list()` provides a strongly consistent view of the projects underneath the specified parent resource. `list()` returns projects sorted based upon the (ascending) lexical ordering of their `display_name`. The caller must have `resourcemanager.projects.list` permission on the identified parent. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListProjectsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Optional. A pagination token returned from a previous call to ListProjects that indicates from where listing should continue. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. The maximum number of projects to return in the response. The server can return fewer projects than requested. If unspecified, server picks an appropriate default.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. Indicate that projects in the `DELETE_REQUESTED` state should also be returned. Normally only `ACTIVE` projects are returned. */
			def withShowDeleted(showDeleted: Boolean) = new list(req.addQueryStringParameters("showDeleted" -> showDeleted.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListProjectsResponse])
		}
		object list {
			def apply(parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.ListProjectsResponse]] = (fun: list) => fun.apply()
		}
		/** Search for projects that the caller has the `resourcemanager.projects.get` permission on, and also satisfy the specified query. This method returns projects in an unspecified order. This method is eventually consistent with project mutations; this means that a newly created project may not appear in the results or recent updates to an existing project may not be reflected in the results. To retrieve the latest state of a project, use the GetProject method. */
		class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchProjectsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Optional. A query string for searching for projects that the caller has `resourcemanager.projects.get` permission to. If multiple fields are included in the query, then it will return results that match any of the fields. Some eligible fields are: ``` | Field | Description | |-------------------------|----------------------------------------------| | displayName, name | Filters by displayName. | | parent | Project's parent (for example: folders/123, organizations/&#42;). Prefer parent field over parent.type and parent.id.| | parent.type | Parent's type: `folder` or `organization`. | | parent.id | Parent's id number (for example: 123) | | id, projectId | Filters by projectId. | | state, lifecycleState | Filters by state. | | labels | Filters by label name or value. | | labels.\ (where &#42;key&#42; is the name of a label) | Filters by label name.| ``` Search expressions are case insensitive. Some examples queries: ``` | Query | Description | |------------------|-----------------------------------------------------| | name:how&#42; | The project's name starts with "how". | | name:Howl | The project's name is `Howl` or `howl`. | | name:HOWL | Equivalent to above. | | NAME:howl | Equivalent to above. | | labels.color:&#42; | The project has the label `color`. | | labels.color:red | The project's label `color` has the value `red`. | | labels.color:red labels.size:big | The project's label `color` has the value `red` or its label `size` has the value `big`. | ``` If no query is specified, the call will return projects for which the user has the `resourcemanager.projects.get` permission. */
			def withQuery(query: String) = new search(req.addQueryStringParameters("query" -> query.toString))
			/** Optional. A pagination token returned from a previous call to ListProjects that indicates from where listing should continue. */
			def withPageToken(pageToken: String) = new search(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. The maximum number of projects to return in the response. The server can return fewer projects than requested. If unspecified, server picks an appropriate default.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new search(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchProjectsResponse])
		}
		object search {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v3/projects:search").addQueryStringParameters())
			given Conversion[search, Future[Schema.SearchProjectsResponse]] = (fun: search) => fun.apply()
		}
	}
	object tagBindings {
		/** Lists the TagBindings for the given Google Cloud resource, as specified with `parent`. NOTE: The `parent` field is expected to be a full resource name: https://cloud.google.com/apis/design/resource_names#full_resource_name */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTagBindingsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Optional. The maximum number of TagBindings to return in the response. The server allows a maximum of 300 TagBindings to return. If unspecified, the server will use 100 as the default.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A pagination token returned from a previous call to `ListTagBindings` that indicates where this listing should continue from. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTagBindingsResponse])
		}
		object list {
			def apply(parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/tagBindings").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.ListTagBindingsResponse]] = (fun: list) => fun.apply()
		}
		/** Creates a TagBinding between a TagValue and a Google Cloud resource. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. Set to true to perform the validations necessary for creating the resource, but not actually perform the action. */
			def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
			/** Perform the request */
			def withTagBinding(body: Schema.TagBinding) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/tagBindings").addQueryStringParameters())
		}
		/** Deletes a TagBinding. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(tagBindingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/tagBindings/${tagBindingsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
	}
	object effectiveTags {
		/** Return a list of effective tags for the given Google Cloud resource, as specified in `parent`. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEffectiveTagsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Optional. The maximum number of effective tags to return in the response. The server allows a maximum of 300 effective tags to return in a single page. If unspecified, the server will use 100 as the default.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A pagination token returned from a previous call to `ListEffectiveTags` that indicates from where this listing should continue. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEffectiveTagsResponse])
		}
		object list {
			def apply(parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/effectiveTags").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.ListEffectiveTagsResponse]] = (fun: list) => fun.apply()
		}
	}
	object tagKeys {
		/** Returns permissions that a caller has on the specified TagKey. The `resource` field should be the TagKey's resource name. For example, "tagKeys/1234". There are no permissions required for making this API call. */
		class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(tagKeysId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v3/tagKeys/${tagKeysId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Retrieves a TagKey by its namespaced name. This method will return `PERMISSION_DENIED` if the key does not exist or the user does not have permission to view it. */
		class getNamespaced(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TagKey]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TagKey])
		}
		object getNamespaced {
			def apply(name: String)(using signer: RequestSigner, ec: ExecutionContext): getNamespaced = new getNamespaced(ws.url(BASE_URL + s"v3/tagKeys/namespaced").addQueryStringParameters("name" -> name.toString))
			given Conversion[getNamespaced, Future[Schema.TagKey]] = (fun: getNamespaced) => fun.apply()
		}
		/** Gets the access control policy for a TagKey. The returned policy may be empty if no such policy or resource exists. The `resource` field should be the TagKey's resource name. For example, "tagKeys/1234". The caller must have `cloudresourcemanager.googleapis.com/tagKeys.getIamPolicy` permission on the specified TagKey. */
		class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(tagKeysId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v3/tagKeys/${tagKeysId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Deletes a TagKey. The TagKey cannot be deleted if it has any child TagValues. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. Set as true to perform validations necessary for deletion, but not actually perform the action. */
			def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
			/** Optional. The etag known to the client for the expected state of the TagKey. This is to be used for optimistic concurrency. */
			def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(tagKeysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/tagKeys/${tagKeysId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		/** Retrieves a TagKey. This method will return `PERMISSION_DENIED` if the key does not exist or the user does not have permission to view it. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TagKey]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TagKey])
		}
		object get {
			def apply(tagKeysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/tagKeys/${tagKeysId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.TagKey]] = (fun: get) => fun.apply()
		}
		/** Updates the attributes of the TagKey resource. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withTagKey(body: Schema.TagKey) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(tagKeysId :PlayApi, name: String, updateMask: String, validateOnly: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/tagKeys/${tagKeysId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "validateOnly" -> validateOnly.toString))
		}
		/** Lists all TagKeys for a parent resource. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTagKeysResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Optional. The maximum number of TagKeys to return in the response. The server allows a maximum of 300 TagKeys to return. If unspecified, the server will use 100 as the default.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A pagination token returned from a previous call to `ListTagKey` that indicates where this listing should continue from. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTagKeysResponse])
		}
		object list {
			def apply(parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/tagKeys").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.ListTagKeysResponse]] = (fun: list) => fun.apply()
		}
		/** Creates a new TagKey. If another request with the same parameters is sent while the original request is in process, the second request will receive an error. A maximum of 1000 TagKeys can exist under a parent at any given time. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. Set to true to perform validations necessary for creating the resource, but not actually perform the action. */
			def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
			/** Perform the request */
			def withTagKey(body: Schema.TagKey) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/tagKeys").addQueryStringParameters())
		}
		/** Sets the access control policy on a TagKey, replacing any existing policy. The `resource` field should be the TagKey's resource name. For example, "tagKeys/1234". The caller must have `resourcemanager.tagKeys.setIamPolicy` permission on the identified tagValue. */
		class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(tagKeysId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v3/tagKeys/${tagKeysId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
	}
	object tagValues {
		/** Returns permissions that a caller has on the specified TagValue. The `resource` field should be the TagValue's resource name. For example: `tagValues/1234`. There are no permissions required for making this API call. */
		class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
		}
		object testIamPermissions {
			def apply(tagValuesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v3/tagValues/${tagValuesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Retrieves a TagValue by its namespaced name. This method will return `PERMISSION_DENIED` if the value does not exist or the user does not have permission to view it. */
		class getNamespaced(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TagValue]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TagValue])
		}
		object getNamespaced {
			def apply(name: String)(using signer: RequestSigner, ec: ExecutionContext): getNamespaced = new getNamespaced(ws.url(BASE_URL + s"v3/tagValues/namespaced").addQueryStringParameters("name" -> name.toString))
			given Conversion[getNamespaced, Future[Schema.TagValue]] = (fun: getNamespaced) => fun.apply()
		}
		/** Gets the access control policy for a TagValue. The returned policy may be empty if no such policy or resource exists. The `resource` field should be the TagValue's resource name. For example: `tagValues/1234`. The caller must have the `cloudresourcemanager.googleapis.com/tagValues.getIamPolicy` permission on the identified TagValue to get the access control policy. */
		class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object getIamPolicy {
			def apply(tagValuesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v3/tagValues/${tagValuesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		/** Deletes a TagValue. The TagValue cannot have any bindings when it is deleted. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. Set as true to perform the validations necessary for deletion, but not actually perform the action. */
			def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
			/** Optional. The etag known to the client for the expected state of the TagValue. This is to be used for optimistic concurrency. */
			def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(tagValuesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/tagValues/${tagValuesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		/** Retrieves a TagValue. This method will return `PERMISSION_DENIED` if the value does not exist or the user does not have permission to view it. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TagValue]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TagValue])
		}
		object get {
			def apply(tagValuesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/tagValues/${tagValuesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.TagValue]] = (fun: get) => fun.apply()
		}
		/** Updates the attributes of the TagValue resource. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. Fields to be updated.<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			/** Optional. True to perform validations necessary for updating the resource, but not actually perform the action. */
			def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
			/** Perform the request */
			def withTagValue(body: Schema.TagValue) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(tagValuesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/tagValues/${tagValuesId}").addQueryStringParameters("name" -> name.toString))
		}
		/** Lists all TagValues for a specific TagKey. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTagValuesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Optional. The maximum number of TagValues to return in the response. The server allows a maximum of 300 TagValues to return. If unspecified, the server will use 100 as the default.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A pagination token returned from a previous call to `ListTagValues` that indicates where this listing should continue from. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTagValuesResponse])
		}
		object list {
			def apply(parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/tagValues").addQueryStringParameters("parent" -> parent.toString))
			given Conversion[list, Future[Schema.ListTagValuesResponse]] = (fun: list) => fun.apply()
		}
		/** Creates a TagValue as a child of the specified TagKey. If a another request with the same parameters is sent while the original request is in process the second request will receive an error. A maximum of 1000 TagValues can exist under a TagKey at any given time. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Optional. Set as true to perform the validations necessary for creating the resource, but not actually perform the action. */
			def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
			/** Perform the request */
			def withTagValue(body: Schema.TagValue) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/tagValues").addQueryStringParameters())
		}
		/** Sets the access control policy on a TagValue, replacing any existing policy. The `resource` field should be the TagValue's resource name. For example: `tagValues/1234`. The caller must have `resourcemanager.tagValues.setIamPolicy` permission on the identified tagValue. */
		class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
		}
		object setIamPolicy {
			def apply(tagValuesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v3/tagValues/${tagValuesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
		}
		object tagHolds {
			/** Creates a TagHold. Returns ALREADY_EXISTS if a TagHold with the same resource and origin exists under the same TagValue. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. Set to true to perform the validations necessary for creating the resource, but not actually perform the action. */
				def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
				/** Perform the request */
				def withTagHold(body: Schema.TagHold) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(tagValuesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/tagValues/${tagValuesId}/tagHolds").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a TagHold. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. Set to true to perform the validations necessary for deleting the resource, but not actually perform the action. */
				def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
			}
			object delete {
				def apply(tagValuesId :PlayApi, tagHoldsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/tagValues/${tagValuesId}/tagHolds/${tagHoldsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
			}
			/** Lists TagHolds under a TagValue. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTagHoldsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Optional. The maximum number of TagHolds to return in the response. The server allows a maximum of 300 TagHolds to return. If unspecified, the server will use 100 as the default.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A pagination token returned from a previous call to `ListTagHolds` that indicates where this listing should continue from. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. Criteria used to select a subset of TagHolds parented by the TagValue to return. This field follows the syntax defined by aip.dev/160; the `holder` and `origin` fields are supported for filtering. Currently only `AND` syntax is supported. Some example queries are: &#42; `holder = //compute.googleapis.com/compute/projects/myproject/regions/us-east-1/instanceGroupManagers/instance-group` &#42; `origin = 35678234` &#42; `holder = //compute.googleapis.com/compute/projects/myproject/regions/us-east-1/instanceGroupManagers/instance-group AND origin = 35678234` */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTagHoldsResponse])
			}
			object list {
				def apply(tagValuesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/tagValues/${tagValuesId}/tagHolds").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListTagHoldsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
