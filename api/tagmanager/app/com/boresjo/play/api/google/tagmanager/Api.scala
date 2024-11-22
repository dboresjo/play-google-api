package com.boresjo.play.api.google.tagmanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://tagmanager.googleapis.com/"

	object accounts {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Account]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Account])
		}
		object get {
			def apply(accountsId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}").addQueryStringParameters("path" -> path.toString))
			given Conversion[get, Future[Schema.Account]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAccount(body: Schema.Account) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Account])
		}
		object update {
			def apply(accountsId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAccountsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAccountsResponse])
		}
		object list {
			def apply(includeGoogleTags: Boolean, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts").addQueryStringParameters("includeGoogleTags" -> includeGoogleTags.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListAccountsResponse]] = (fun: list) => fun.apply()
		}
		object user_permissions {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUserPermission(body: Schema.UserPermission) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UserPermission])
			}
			object create {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/user_permissions").addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(accountsId :PlayApi, user_permissionsId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/user_permissions/${user_permissionsId}").addQueryStringParameters("path" -> path.toString))
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.UserPermission]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.UserPermission])
			}
			object get {
				def apply(accountsId :PlayApi, user_permissionsId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/user_permissions/${user_permissionsId}").addQueryStringParameters("path" -> path.toString))
				given Conversion[get, Future[Schema.UserPermission]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUserPermission(body: Schema.UserPermission) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.UserPermission])
			}
			object update {
				def apply(accountsId :PlayApi, user_permissionsId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/user_permissions/${user_permissionsId}").addQueryStringParameters("path" -> path.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListUserPermissionsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListUserPermissionsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/user_permissions").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListUserPermissionsResponse]] = (fun: list) => fun.apply()
			}
		}
		object containers {
			class snippet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GetContainerSnippetResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GetContainerSnippetResponse])
			}
			object snippet {
				def apply(accountsId :PlayApi, containersId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): snippet = new snippet(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}:snippet").addQueryStringParameters("path" -> path.toString))
				given Conversion[snippet, Future[Schema.GetContainerSnippetResponse]] = (fun: snippet) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withContainer(body: Schema.Container) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Container])
			}
			object create {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers").addQueryStringParameters("parent" -> parent.toString))
			}
			class move_tag_id(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Container]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Container])
			}
			object move_tag_id {
				def apply(accountsId :PlayApi, containersId :PlayApi, path: String, tagId: String, tagName: String, copyUsers: Boolean, copySettings: Boolean, allowUserPermissionFeatureUpdate: Boolean, copyTermsOfService: Boolean)(using auth: AuthToken, ec: ExecutionContext): move_tag_id = new move_tag_id(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}:move_tag_id").addQueryStringParameters("path" -> path.toString, "tagId" -> tagId.toString, "tagName" -> tagName.toString, "copyUsers" -> copyUsers.toString, "copySettings" -> copySettings.toString, "allowUserPermissionFeatureUpdate" -> allowUserPermissionFeatureUpdate.toString, "copyTermsOfService" -> copyTermsOfService.toString))
				given Conversion[move_tag_id, Future[Schema.Container]] = (fun: move_tag_id) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(accountsId :PlayApi, containersId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}").addQueryStringParameters("path" -> path.toString))
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			class combine(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Container]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Container])
			}
			object combine {
				def apply(accountsId :PlayApi, containersId :PlayApi, path: String, containerId: String, allowUserPermissionFeatureUpdate: Boolean, settingSource: String)(using auth: AuthToken, ec: ExecutionContext): combine = new combine(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}:combine").addQueryStringParameters("path" -> path.toString, "containerId" -> containerId.toString, "allowUserPermissionFeatureUpdate" -> allowUserPermissionFeatureUpdate.toString, "settingSource" -> settingSource.toString))
				given Conversion[combine, Future[Schema.Container]] = (fun: combine) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Container]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Container])
			}
			object get {
				def apply(accountsId :PlayApi, containersId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}").addQueryStringParameters("path" -> path.toString))
				given Conversion[get, Future[Schema.Container]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withContainer(body: Schema.Container) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Container])
			}
			object update {
				def apply(accountsId :PlayApi, containersId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListContainersResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListContainersResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListContainersResponse]] = (fun: list) => fun.apply()
			}
			class lookup(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Container]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Container])
			}
			object lookup {
				def apply(destinationId: String, tagId: String)(using auth: AuthToken, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"tagmanager/v2/accounts/containers:lookup").addQueryStringParameters("destinationId" -> destinationId.toString, "tagId" -> tagId.toString))
				given Conversion[lookup, Future[Schema.Container]] = (fun: lookup) => fun.apply()
			}
			object environments {
				class reauthorize(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEnvironment(body: Schema.Environment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Environment])
				}
				object reauthorize {
					def apply(accountsId :PlayApi, containersId :PlayApi, environmentsId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): reauthorize = new reauthorize(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/environments/${environmentsId}:reauthorize").addQueryStringParameters("path" -> path.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEnvironment(body: Schema.Environment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Environment])
				}
				object create {
					def apply(accountsId :PlayApi, containersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/environments").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
				}
				object delete {
					def apply(accountsId :PlayApi, containersId :PlayApi, environmentsId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/environments/${environmentsId}").addQueryStringParameters("path" -> path.toString))
					given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Environment]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Environment])
				}
				object get {
					def apply(accountsId :PlayApi, containersId :PlayApi, environmentsId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/environments/${environmentsId}").addQueryStringParameters("path" -> path.toString))
					given Conversion[get, Future[Schema.Environment]] = (fun: get) => fun.apply()
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEnvironment(body: Schema.Environment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Environment])
				}
				object update {
					def apply(accountsId :PlayApi, containersId :PlayApi, environmentsId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/environments/${environmentsId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEnvironmentsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListEnvironmentsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, containersId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/environments").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListEnvironmentsResponse]] = (fun: list) => fun.apply()
				}
			}
			object versions {
				class set_latest(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ContainerVersion]) {
					def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.ContainerVersion])
				}
				object set_latest {
					def apply(accountsId :PlayApi, containersId :PlayApi, versionsId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): set_latest = new set_latest(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/versions/${versionsId}:set_latest").addQueryStringParameters("path" -> path.toString))
					given Conversion[set_latest, Future[Schema.ContainerVersion]] = (fun: set_latest) => fun.apply()
				}
				class undelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ContainerVersion]) {
					def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.ContainerVersion])
				}
				object undelete {
					def apply(accountsId :PlayApi, containersId :PlayApi, versionsId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/versions/${versionsId}:undelete").addQueryStringParameters("path" -> path.toString))
					given Conversion[undelete, Future[Schema.ContainerVersion]] = (fun: undelete) => fun.apply()
				}
				class publish(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PublishContainerVersionResponse]) {
					def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.PublishContainerVersionResponse])
				}
				object publish {
					def apply(accountsId :PlayApi, containersId :PlayApi, versionsId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): publish = new publish(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/versions/${versionsId}:publish").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
					given Conversion[publish, Future[Schema.PublishContainerVersionResponse]] = (fun: publish) => fun.apply()
				}
				class live(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ContainerVersion]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ContainerVersion])
				}
				object live {
					def apply(accountsId :PlayApi, containersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): live = new live(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/versions:live").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[live, Future[Schema.ContainerVersion]] = (fun: live) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
				}
				object delete {
					def apply(accountsId :PlayApi, containersId :PlayApi, versionsId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/versions/${versionsId}").addQueryStringParameters("path" -> path.toString))
					given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ContainerVersion]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ContainerVersion])
				}
				object get {
					def apply(accountsId :PlayApi, containersId :PlayApi, versionsId :PlayApi, path: String, containerVersionId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/versions/${versionsId}").addQueryStringParameters("path" -> path.toString, "containerVersionId" -> containerVersionId.toString))
					given Conversion[get, Future[Schema.ContainerVersion]] = (fun: get) => fun.apply()
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withContainerVersion(body: Schema.ContainerVersion) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ContainerVersion])
				}
				object update {
					def apply(accountsId :PlayApi, containersId :PlayApi, versionsId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/versions/${versionsId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
				}
			}
			object version_headers {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListContainerVersionsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListContainerVersionsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, containersId :PlayApi, parent: String, includeDeleted: Boolean, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/version_headers").addQueryStringParameters("parent" -> parent.toString, "includeDeleted" -> includeDeleted.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListContainerVersionsResponse]] = (fun: list) => fun.apply()
				}
				class latest(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ContainerVersionHeader]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ContainerVersionHeader])
				}
				object latest {
					def apply(accountsId :PlayApi, containersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): latest = new latest(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/version_headers:latest").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[latest, Future[Schema.ContainerVersionHeader]] = (fun: latest) => fun.apply()
				}
			}
			object destinations {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Destination]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Destination])
				}
				object get {
					def apply(accountsId :PlayApi, containersId :PlayApi, destinationsId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/destinations/${destinationsId}").addQueryStringParameters("path" -> path.toString))
					given Conversion[get, Future[Schema.Destination]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDestinationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListDestinationsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, containersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/destinations").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListDestinationsResponse]] = (fun: list) => fun.apply()
				}
				class link(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Destination]) {
					def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Destination])
				}
				object link {
					def apply(accountsId :PlayApi, containersId :PlayApi, parent: String, destinationId: String, allowUserPermissionFeatureUpdate: Boolean)(using auth: AuthToken, ec: ExecutionContext): link = new link(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/destinations:link").addQueryStringParameters("parent" -> parent.toString, "destinationId" -> destinationId.toString, "allowUserPermissionFeatureUpdate" -> allowUserPermissionFeatureUpdate.toString))
					given Conversion[link, Future[Schema.Destination]] = (fun: link) => fun.apply()
				}
			}
			object workspaces {
				class quick_preview(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.QuickPreviewResponse]) {
					def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.QuickPreviewResponse])
				}
				object quick_preview {
					def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): quick_preview = new quick_preview(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}:quick_preview").addQueryStringParameters("path" -> path.toString))
					given Conversion[quick_preview, Future[Schema.QuickPreviewResponse]] = (fun: quick_preview) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
				}
				object delete {
					def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}").addQueryStringParameters("path" -> path.toString))
					given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
				}
				class sync(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SyncWorkspaceResponse]) {
					def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.SyncWorkspaceResponse])
				}
				object sync {
					def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): sync = new sync(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}:sync").addQueryStringParameters("path" -> path.toString))
					given Conversion[sync, Future[Schema.SyncWorkspaceResponse]] = (fun: sync) => fun.apply()
				}
				class create_version(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withCreateContainerVersionRequestVersionOptions(body: Schema.CreateContainerVersionRequestVersionOptions) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CreateContainerVersionResponse])
				}
				object create_version {
					def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): create_version = new create_version(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}:create_version").addQueryStringParameters("path" -> path.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Workspace]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Workspace])
				}
				object get {
					def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}").addQueryStringParameters("path" -> path.toString))
					given Conversion[get, Future[Schema.Workspace]] = (fun: get) => fun.apply()
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withWorkspace(body: Schema.Workspace) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Workspace])
				}
				object update {
					def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListWorkspacesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListWorkspacesResponse])
				}
				object list {
					def apply(accountsId :PlayApi, containersId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListWorkspacesResponse]] = (fun: list) => fun.apply()
				}
				class resolve_conflict(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEntity(body: Schema.Entity) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
				}
				object resolve_conflict {
					def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): resolve_conflict = new resolve_conflict(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}:resolve_conflict").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
				}
				class getStatus(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GetWorkspaceStatusResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GetWorkspaceStatusResponse])
				}
				object getStatus {
					def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): getStatus = new getStatus(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/status").addQueryStringParameters("path" -> path.toString))
					given Conversion[getStatus, Future[Schema.GetWorkspaceStatusResponse]] = (fun: getStatus) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withWorkspace(body: Schema.Workspace) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Workspace])
				}
				object create {
					def apply(accountsId :PlayApi, containersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces").addQueryStringParameters("parent" -> parent.toString))
				}
				object gtag_config {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGtagConfig(body: Schema.GtagConfig) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GtagConfig])
					}
					object create {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/gtag_config").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, gtag_configId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/gtag_config/${gtag_configId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GtagConfig]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GtagConfig])
					}
					object get {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, gtag_configId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/gtag_config/${gtag_configId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[get, Future[Schema.GtagConfig]] = (fun: get) => fun.apply()
					}
					class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGtagConfig(body: Schema.GtagConfig) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GtagConfig])
					}
					object update {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, gtag_configId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/gtag_config/${gtag_configId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListGtagConfigResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListGtagConfigResponse])
					}
					object list {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/gtag_config").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListGtagConfigResponse]] = (fun: list) => fun.apply()
					}
				}
				object variables {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withVariable(body: Schema.Variable) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Variable])
					}
					object create {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/variables").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, variablesId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/variables/${variablesId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Variable]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Variable])
					}
					object get {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, variablesId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/variables/${variablesId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[get, Future[Schema.Variable]] = (fun: get) => fun.apply()
					}
					class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withVariable(body: Schema.Variable) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Variable])
					}
					object update {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, variablesId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/variables/${variablesId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
					}
					class revert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RevertVariableResponse]) {
						def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.RevertVariableResponse])
					}
					object revert {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, variablesId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): revert = new revert(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/variables/${variablesId}:revert").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
						given Conversion[revert, Future[Schema.RevertVariableResponse]] = (fun: revert) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListVariablesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListVariablesResponse])
					}
					object list {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/variables").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListVariablesResponse]] = (fun: list) => fun.apply()
					}
				}
				object transformations {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTransformation(body: Schema.Transformation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Transformation])
					}
					object create {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/transformations").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, transformationsId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/transformations/${transformationsId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Transformation]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Transformation])
					}
					object get {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, transformationsId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/transformations/${transformationsId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[get, Future[Schema.Transformation]] = (fun: get) => fun.apply()
					}
					class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTransformation(body: Schema.Transformation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Transformation])
					}
					object update {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, transformationsId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/transformations/${transformationsId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
					}
					class revert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RevertTransformationResponse]) {
						def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.RevertTransformationResponse])
					}
					object revert {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, transformationsId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): revert = new revert(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/transformations/${transformationsId}:revert").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
						given Conversion[revert, Future[Schema.RevertTransformationResponse]] = (fun: revert) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTransformationsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListTransformationsResponse])
					}
					object list {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/transformations").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListTransformationsResponse]] = (fun: list) => fun.apply()
					}
				}
				object zones {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withZone(body: Schema.Zone) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Zone])
					}
					object create {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/zones").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, zonesId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/zones/${zonesId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Zone]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Zone])
					}
					object get {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, zonesId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/zones/${zonesId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[get, Future[Schema.Zone]] = (fun: get) => fun.apply()
					}
					class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withZone(body: Schema.Zone) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Zone])
					}
					object update {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, zonesId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/zones/${zonesId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
					}
					class revert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RevertZoneResponse]) {
						def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.RevertZoneResponse])
					}
					object revert {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, zonesId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): revert = new revert(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/zones/${zonesId}:revert").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
						given Conversion[revert, Future[Schema.RevertZoneResponse]] = (fun: revert) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListZonesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListZonesResponse])
					}
					object list {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/zones").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListZonesResponse]] = (fun: list) => fun.apply()
					}
				}
				object clients {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withClient(body: Schema.Client) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Client])
					}
					object create {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/clients").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, clientsId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/clients/${clientsId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Client]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Client])
					}
					object get {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, clientsId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/clients/${clientsId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[get, Future[Schema.Client]] = (fun: get) => fun.apply()
					}
					class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withClient(body: Schema.Client) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Client])
					}
					object update {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, clientsId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/clients/${clientsId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
					}
					class revert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RevertClientResponse]) {
						def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.RevertClientResponse])
					}
					object revert {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, clientsId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): revert = new revert(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/clients/${clientsId}:revert").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
						given Conversion[revert, Future[Schema.RevertClientResponse]] = (fun: revert) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListClientsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListClientsResponse])
					}
					object list {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/clients").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListClientsResponse]] = (fun: list) => fun.apply()
					}
				}
				object tags {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTag(body: Schema.Tag) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Tag])
					}
					object create {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/tags").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, tagsId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/tags/${tagsId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Tag]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Tag])
					}
					object get {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, tagsId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/tags/${tagsId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[get, Future[Schema.Tag]] = (fun: get) => fun.apply()
					}
					class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTag(body: Schema.Tag) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Tag])
					}
					object update {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, tagsId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/tags/${tagsId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
					}
					class revert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RevertTagResponse]) {
						def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.RevertTagResponse])
					}
					object revert {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, tagsId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): revert = new revert(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/tags/${tagsId}:revert").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
						given Conversion[revert, Future[Schema.RevertTagResponse]] = (fun: revert) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTagsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListTagsResponse])
					}
					object list {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/tags").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListTagsResponse]] = (fun: list) => fun.apply()
					}
				}
				object triggers {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTrigger(body: Schema.Trigger) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Trigger])
					}
					object create {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/triggers").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, triggersId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/triggers/${triggersId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Trigger]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Trigger])
					}
					object get {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, triggersId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/triggers/${triggersId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[get, Future[Schema.Trigger]] = (fun: get) => fun.apply()
					}
					class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTrigger(body: Schema.Trigger) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Trigger])
					}
					object update {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, triggersId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/triggers/${triggersId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
					}
					class revert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RevertTriggerResponse]) {
						def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.RevertTriggerResponse])
					}
					object revert {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, triggersId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): revert = new revert(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/triggers/${triggersId}:revert").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
						given Conversion[revert, Future[Schema.RevertTriggerResponse]] = (fun: revert) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTriggersResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListTriggersResponse])
					}
					object list {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/triggers").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListTriggersResponse]] = (fun: list) => fun.apply()
					}
				}
				object built_in_variables {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CreateBuiltInVariableResponse]) {
						def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.CreateBuiltInVariableResponse])
					}
					object create {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, `type`: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/built_in_variables").addQueryStringParameters("parent" -> parent.toString, "type" -> `type`.toString))
						given Conversion[create, Future[Schema.CreateBuiltInVariableResponse]] = (fun: create) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, path: String, `type`: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/built_in_variables").addQueryStringParameters("path" -> path.toString, "type" -> `type`.toString))
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEnabledBuiltInVariablesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListEnabledBuiltInVariablesResponse])
					}
					object list {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/built_in_variables").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListEnabledBuiltInVariablesResponse]] = (fun: list) => fun.apply()
					}
					class revert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RevertBuiltInVariableResponse]) {
						def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.RevertBuiltInVariableResponse])
					}
					object revert {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, path: String, `type`: String)(using auth: AuthToken, ec: ExecutionContext): revert = new revert(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/built_in_variables:revert").addQueryStringParameters("path" -> path.toString, "type" -> `type`.toString))
						given Conversion[revert, Future[Schema.RevertBuiltInVariableResponse]] = (fun: revert) => fun.apply()
					}
				}
				object templates {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCustomTemplate(body: Schema.CustomTemplate) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CustomTemplate])
					}
					object create {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/templates").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, templatesId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/templates/${templatesId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CustomTemplate]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CustomTemplate])
					}
					object get {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, templatesId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/templates/${templatesId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[get, Future[Schema.CustomTemplate]] = (fun: get) => fun.apply()
					}
					class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCustomTemplate(body: Schema.CustomTemplate) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.CustomTemplate])
					}
					object update {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, templatesId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/templates/${templatesId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
					}
					class revert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RevertTemplateResponse]) {
						def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.RevertTemplateResponse])
					}
					object revert {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, templatesId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): revert = new revert(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/templates/${templatesId}:revert").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
						given Conversion[revert, Future[Schema.RevertTemplateResponse]] = (fun: revert) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTemplatesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListTemplatesResponse])
					}
					object list {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/templates").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListTemplatesResponse]] = (fun: list) => fun.apply()
					}
				}
				object folders {
					class move_entities_to_folder(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withFolder(body: Schema.Folder) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
					}
					object move_entities_to_folder {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, foldersId :PlayApi, path: String, tagId: String, variableId: String, triggerId: String)(using auth: AuthToken, ec: ExecutionContext): move_entities_to_folder = new move_entities_to_folder(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/folders/${foldersId}:move_entities_to_folder").addQueryStringParameters("path" -> path.toString, "tagId" -> tagId.toString, "variableId" -> variableId.toString, "triggerId" -> triggerId.toString))
					}
					class entities(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FolderEntities]) {
						def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.FolderEntities])
					}
					object entities {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, foldersId :PlayApi, path: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): entities = new entities(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/folders/${foldersId}:entities").addQueryStringParameters("path" -> path.toString, "pageToken" -> pageToken.toString))
						given Conversion[entities, Future[Schema.FolderEntities]] = (fun: entities) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withFolder(body: Schema.Folder) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Folder])
					}
					object create {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/folders").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, foldersId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/folders/${foldersId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Folder]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Folder])
					}
					object get {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, foldersId :PlayApi, path: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/folders/${foldersId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[get, Future[Schema.Folder]] = (fun: get) => fun.apply()
					}
					class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withFolder(body: Schema.Folder) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Folder])
					}
					object update {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, foldersId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/folders/${foldersId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
					}
					class revert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RevertFolderResponse]) {
						def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.RevertFolderResponse])
					}
					object revert {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, foldersId :PlayApi, path: String, fingerprint: String)(using auth: AuthToken, ec: ExecutionContext): revert = new revert(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/folders/${foldersId}:revert").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
						given Conversion[revert, Future[Schema.RevertFolderResponse]] = (fun: revert) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFoldersResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListFoldersResponse])
					}
					object list {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/folders").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListFoldersResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
}
