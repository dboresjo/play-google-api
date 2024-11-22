package com.boresjo.play.api.google.tagmanager

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
		"""https://www.googleapis.com/auth/tagmanager.delete.containers""" /* Delete your Google Tag Manager containers */,
		"""https://www.googleapis.com/auth/tagmanager.edit.containers""" /* Manage your Google Tag Manager container and its subcomponents, excluding versioning and publishing */,
		"""https://www.googleapis.com/auth/tagmanager.edit.containerversions""" /* Manage your Google Tag Manager container versions */,
		"""https://www.googleapis.com/auth/tagmanager.manage.accounts""" /* View and manage your Google Tag Manager accounts */,
		"""https://www.googleapis.com/auth/tagmanager.manage.users""" /* Manage user permissions of your Google Tag Manager account and container */,
		"""https://www.googleapis.com/auth/tagmanager.publish""" /* Publish your Google Tag Manager container versions */,
		"""https://www.googleapis.com/auth/tagmanager.readonly""" /* View your Google Tag Manager container and its subcomponents */
	)

	private val BASE_URL = "https://tagmanager.googleapis.com/"

	object accounts {
		/** Gets a GTM Account. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Account]) {
			val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.manage.accounts""", """https://www.googleapis.com/auth/tagmanager.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Account])
		}
		object get {
			def apply(accountsId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}").addQueryStringParameters("path" -> path.toString))
			given Conversion[get, Future[Schema.Account]] = (fun: get) => fun.apply()
		}
		/** Updates a GTM Account. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.manage.accounts""")
			/** Perform the request */
			def withAccount(body: Schema.Account) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Account])
		}
		object update {
			def apply(accountsId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
		}
		/** Lists all GTM Accounts that a user has access to. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAccountsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.manage.accounts""", """https://www.googleapis.com/auth/tagmanager.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAccountsResponse])
		}
		object list {
			def apply(includeGoogleTags: Boolean, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts").addQueryStringParameters("includeGoogleTags" -> includeGoogleTags.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListAccountsResponse]] = (fun: list) => fun.apply()
		}
		object user_permissions {
			/** Creates a user's Account & Container access. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.manage.users""")
				/** Perform the request */
				def withUserPermission(body: Schema.UserPermission) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UserPermission])
			}
			object create {
				def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/user_permissions").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Removes a user from the account, revoking access to it and all of its containers. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.manage.users""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(accountsId :PlayApi, user_permissionsId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/user_permissions/${user_permissionsId}").addQueryStringParameters("path" -> path.toString))
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			/** Gets a user's Account & Container access. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.UserPermission]) {
				val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.manage.users""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.UserPermission])
			}
			object get {
				def apply(accountsId :PlayApi, user_permissionsId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/user_permissions/${user_permissionsId}").addQueryStringParameters("path" -> path.toString))
				given Conversion[get, Future[Schema.UserPermission]] = (fun: get) => fun.apply()
			}
			/** Updates a user's Account & Container access. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.manage.users""")
				/** Perform the request */
				def withUserPermission(body: Schema.UserPermission) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.UserPermission])
			}
			object update {
				def apply(accountsId :PlayApi, user_permissionsId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/user_permissions/${user_permissionsId}").addQueryStringParameters("path" -> path.toString))
			}
			/** List all users that have access to the account along with Account and Container user access granted to each of them. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListUserPermissionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.manage.users""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListUserPermissionsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/user_permissions").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListUserPermissionsResponse]] = (fun: list) => fun.apply()
			}
		}
		object containers {
			/** Gets the tagging snippet for a Container. */
			class snippet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GetContainerSnippetResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GetContainerSnippetResponse])
			}
			object snippet {
				def apply(accountsId :PlayApi, containersId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): snippet = new snippet(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}:snippet").addQueryStringParameters("path" -> path.toString))
				given Conversion[snippet, Future[Schema.GetContainerSnippetResponse]] = (fun: snippet) => fun.apply()
			}
			/** Creates a Container. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
				/** Perform the request */
				def withContainer(body: Schema.Container) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Container])
			}
			object create {
				def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Move Tag ID out of a Container. */
			class move_tag_id(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Container]) {
				val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Container])
			}
			object move_tag_id {
				def apply(accountsId :PlayApi, containersId :PlayApi, path: String, tagId: String, tagName: String, copyUsers: Boolean, copySettings: Boolean, allowUserPermissionFeatureUpdate: Boolean, copyTermsOfService: Boolean)(using signer: RequestSigner, ec: ExecutionContext): move_tag_id = new move_tag_id(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}:move_tag_id").addQueryStringParameters("path" -> path.toString, "tagId" -> tagId.toString, "tagName" -> tagName.toString, "copyUsers" -> copyUsers.toString, "copySettings" -> copySettings.toString, "allowUserPermissionFeatureUpdate" -> allowUserPermissionFeatureUpdate.toString, "copyTermsOfService" -> copyTermsOfService.toString))
				given Conversion[move_tag_id, Future[Schema.Container]] = (fun: move_tag_id) => fun.apply()
			}
			/** Deletes a Container. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.delete.containers""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(accountsId :PlayApi, containersId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}").addQueryStringParameters("path" -> path.toString))
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			/** Combines Containers. */
			class combine(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Container]) {
				val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Container])
			}
			object combine {
				def apply(accountsId :PlayApi, containersId :PlayApi, path: String, containerId: String, allowUserPermissionFeatureUpdate: Boolean, settingSource: String)(using signer: RequestSigner, ec: ExecutionContext): combine = new combine(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}:combine").addQueryStringParameters("path" -> path.toString, "containerId" -> containerId.toString, "allowUserPermissionFeatureUpdate" -> allowUserPermissionFeatureUpdate.toString, "settingSource" -> settingSource.toString))
				given Conversion[combine, Future[Schema.Container]] = (fun: combine) => fun.apply()
			}
			/** Gets a Container. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Container]) {
				val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Container])
			}
			object get {
				def apply(accountsId :PlayApi, containersId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}").addQueryStringParameters("path" -> path.toString))
				given Conversion[get, Future[Schema.Container]] = (fun: get) => fun.apply()
			}
			/** Updates a Container. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
				/** Perform the request */
				def withContainer(body: Schema.Container) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Container])
			}
			object update {
				def apply(accountsId :PlayApi, containersId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
			}
			/** Lists all Containers that belongs to a GTM Account. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListContainersResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListContainersResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListContainersResponse]] = (fun: list) => fun.apply()
			}
			/** Looks up a Container by destination ID or tag ID. */
			class lookup(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Container]) {
				val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Container])
			}
			object lookup {
				def apply(destinationId: String, tagId: String)(using signer: RequestSigner, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"tagmanager/v2/accounts/containers:lookup").addQueryStringParameters("destinationId" -> destinationId.toString, "tagId" -> tagId.toString))
				given Conversion[lookup, Future[Schema.Container]] = (fun: lookup) => fun.apply()
			}
			object environments {
				/** Re-generates the authorization code for a GTM Environment. */
				class reauthorize(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.publish""")
					/** Perform the request */
					def withEnvironment(body: Schema.Environment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Environment])
				}
				object reauthorize {
					def apply(accountsId :PlayApi, containersId :PlayApi, environmentsId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): reauthorize = new reauthorize(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/environments/${environmentsId}:reauthorize").addQueryStringParameters("path" -> path.toString))
				}
				/** Creates a GTM Environment. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
					/** Perform the request */
					def withEnvironment(body: Schema.Environment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Environment])
				}
				object create {
					def apply(accountsId :PlayApi, containersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/environments").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a GTM Environment. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
				}
				object delete {
					def apply(accountsId :PlayApi, containersId :PlayApi, environmentsId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/environments/${environmentsId}").addQueryStringParameters("path" -> path.toString))
					given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
				}
				/** Gets a GTM Environment. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Environment]) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Environment])
				}
				object get {
					def apply(accountsId :PlayApi, containersId :PlayApi, environmentsId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/environments/${environmentsId}").addQueryStringParameters("path" -> path.toString))
					given Conversion[get, Future[Schema.Environment]] = (fun: get) => fun.apply()
				}
				/** Updates a GTM Environment. */
				class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
					/** Perform the request */
					def withEnvironment(body: Schema.Environment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Environment])
				}
				object update {
					def apply(accountsId :PlayApi, containersId :PlayApi, environmentsId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/environments/${environmentsId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
				}
				/** Lists all GTM Environments of a GTM Container. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEnvironmentsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEnvironmentsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, containersId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/environments").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListEnvironmentsResponse]] = (fun: list) => fun.apply()
				}
			}
			object versions {
				/** Sets the latest version used for synchronization of workspaces when detecting conflicts and errors. */
				class set_latest(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ContainerVersion]) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.ContainerVersion])
				}
				object set_latest {
					def apply(accountsId :PlayApi, containersId :PlayApi, versionsId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): set_latest = new set_latest(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/versions/${versionsId}:set_latest").addQueryStringParameters("path" -> path.toString))
					given Conversion[set_latest, Future[Schema.ContainerVersion]] = (fun: set_latest) => fun.apply()
				}
				/** Undeletes a Container Version. */
				class undelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ContainerVersion]) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containerversions""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.ContainerVersion])
				}
				object undelete {
					def apply(accountsId :PlayApi, containersId :PlayApi, versionsId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/versions/${versionsId}:undelete").addQueryStringParameters("path" -> path.toString))
					given Conversion[undelete, Future[Schema.ContainerVersion]] = (fun: undelete) => fun.apply()
				}
				/** Publishes a Container Version. */
				class publish(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PublishContainerVersionResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.publish""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.PublishContainerVersionResponse])
				}
				object publish {
					def apply(accountsId :PlayApi, containersId :PlayApi, versionsId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): publish = new publish(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/versions/${versionsId}:publish").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
					given Conversion[publish, Future[Schema.PublishContainerVersionResponse]] = (fun: publish) => fun.apply()
				}
				/** Gets the live (i.e. published) container version */
				class live(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ContainerVersion]) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ContainerVersion])
				}
				object live {
					def apply(accountsId :PlayApi, containersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): live = new live(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/versions:live").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[live, Future[Schema.ContainerVersion]] = (fun: live) => fun.apply()
				}
				/** Deletes a Container Version. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containerversions""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
				}
				object delete {
					def apply(accountsId :PlayApi, containersId :PlayApi, versionsId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/versions/${versionsId}").addQueryStringParameters("path" -> path.toString))
					given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
				}
				/** Gets a Container Version. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ContainerVersion]) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.edit.containerversions""", """https://www.googleapis.com/auth/tagmanager.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ContainerVersion])
				}
				object get {
					def apply(accountsId :PlayApi, containersId :PlayApi, versionsId :PlayApi, path: String, containerVersionId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/versions/${versionsId}").addQueryStringParameters("path" -> path.toString, "containerVersionId" -> containerVersionId.toString))
					given Conversion[get, Future[Schema.ContainerVersion]] = (fun: get) => fun.apply()
				}
				/** Updates a Container Version. */
				class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containerversions""")
					/** Perform the request */
					def withContainerVersion(body: Schema.ContainerVersion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ContainerVersion])
				}
				object update {
					def apply(accountsId :PlayApi, containersId :PlayApi, versionsId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/versions/${versionsId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
				}
			}
			object version_headers {
				/** Lists all Container Versions of a GTM Container. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListContainerVersionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.edit.containerversions""", """https://www.googleapis.com/auth/tagmanager.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListContainerVersionsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, containersId :PlayApi, parent: String, includeDeleted: Boolean, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/version_headers").addQueryStringParameters("parent" -> parent.toString, "includeDeleted" -> includeDeleted.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListContainerVersionsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest container version header */
				class latest(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ContainerVersionHeader]) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ContainerVersionHeader])
				}
				object latest {
					def apply(accountsId :PlayApi, containersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): latest = new latest(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/version_headers:latest").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[latest, Future[Schema.ContainerVersionHeader]] = (fun: latest) => fun.apply()
				}
			}
			object destinations {
				/** Gets a Destination. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Destination]) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Destination])
				}
				object get {
					def apply(accountsId :PlayApi, containersId :PlayApi, destinationsId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/destinations/${destinationsId}").addQueryStringParameters("path" -> path.toString))
					given Conversion[get, Future[Schema.Destination]] = (fun: get) => fun.apply()
				}
				/** Lists all Destinations linked to a GTM Container. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDestinationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDestinationsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, containersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/destinations").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListDestinationsResponse]] = (fun: list) => fun.apply()
				}
				/** Adds a Destination to this Container and removes it from the Container to which it is currently linked. */
				class link(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Destination]) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Destination])
				}
				object link {
					def apply(accountsId :PlayApi, containersId :PlayApi, parent: String, destinationId: String, allowUserPermissionFeatureUpdate: Boolean)(using signer: RequestSigner, ec: ExecutionContext): link = new link(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/destinations:link").addQueryStringParameters("parent" -> parent.toString, "destinationId" -> destinationId.toString, "allowUserPermissionFeatureUpdate" -> allowUserPermissionFeatureUpdate.toString))
					given Conversion[link, Future[Schema.Destination]] = (fun: link) => fun.apply()
				}
			}
			object workspaces {
				/** Quick previews a workspace by creating a fake container version from all entities in the provided workspace. */
				class quick_preview(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.QuickPreviewResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containerversions""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.QuickPreviewResponse])
				}
				object quick_preview {
					def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): quick_preview = new quick_preview(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}:quick_preview").addQueryStringParameters("path" -> path.toString))
					given Conversion[quick_preview, Future[Schema.QuickPreviewResponse]] = (fun: quick_preview) => fun.apply()
				}
				/** Deletes a Workspace. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.delete.containers""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
				}
				object delete {
					def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}").addQueryStringParameters("path" -> path.toString))
					given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
				}
				/** Syncs a workspace to the latest container version by updating all unmodified workspace entities and displaying conflicts for modified entities. */
				class sync(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SyncWorkspaceResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.SyncWorkspaceResponse])
				}
				object sync {
					def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): sync = new sync(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}:sync").addQueryStringParameters("path" -> path.toString))
					given Conversion[sync, Future[Schema.SyncWorkspaceResponse]] = (fun: sync) => fun.apply()
				}
				/** Creates a Container Version from the entities present in the workspace, deletes the workspace, and sets the base container version to the newly created version. */
				class create_version(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containerversions""")
					/** Perform the request */
					def withCreateContainerVersionRequestVersionOptions(body: Schema.CreateContainerVersionRequestVersionOptions) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CreateContainerVersionResponse])
				}
				object create_version {
					def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): create_version = new create_version(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}:create_version").addQueryStringParameters("path" -> path.toString))
				}
				/** Gets a Workspace. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Workspace]) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Workspace])
				}
				object get {
					def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}").addQueryStringParameters("path" -> path.toString))
					given Conversion[get, Future[Schema.Workspace]] = (fun: get) => fun.apply()
				}
				/** Updates a Workspace. */
				class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
					/** Perform the request */
					def withWorkspace(body: Schema.Workspace) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Workspace])
				}
				object update {
					def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
				}
				/** Lists all Workspaces that belong to a GTM Container. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListWorkspacesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListWorkspacesResponse])
				}
				object list {
					def apply(accountsId :PlayApi, containersId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListWorkspacesResponse]] = (fun: list) => fun.apply()
				}
				/** Resolves a merge conflict for a workspace entity by updating it to the resolved entity passed in the request. */
				class resolve_conflict(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
					/** Perform the request */
					def withEntity(body: Schema.Entity) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
				}
				object resolve_conflict {
					def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): resolve_conflict = new resolve_conflict(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}:resolve_conflict").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
				}
				/** Finds conflicting and modified entities in the workspace. */
				class getStatus(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GetWorkspaceStatusResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GetWorkspaceStatusResponse])
				}
				object getStatus {
					def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): getStatus = new getStatus(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/status").addQueryStringParameters("path" -> path.toString))
					given Conversion[getStatus, Future[Schema.GetWorkspaceStatusResponse]] = (fun: getStatus) => fun.apply()
				}
				/** Creates a Workspace. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
					/** Perform the request */
					def withWorkspace(body: Schema.Workspace) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Workspace])
				}
				object create {
					def apply(accountsId :PlayApi, containersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces").addQueryStringParameters("parent" -> parent.toString))
				}
				object gtag_config {
					/** Creates a Google tag config. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def withGtagConfig(body: Schema.GtagConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GtagConfig])
					}
					object create {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/gtag_config").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a Google tag config. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, gtag_configId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/gtag_config/${gtag_configId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					/** Gets a Google tag config. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GtagConfig]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GtagConfig])
					}
					object get {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, gtag_configId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/gtag_config/${gtag_configId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[get, Future[Schema.GtagConfig]] = (fun: get) => fun.apply()
					}
					/** Updates a Google tag config. */
					class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def withGtagConfig(body: Schema.GtagConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GtagConfig])
					}
					object update {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, gtag_configId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/gtag_config/${gtag_configId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
					}
					/** Lists all Google tag configs in a Container. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListGtagConfigResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListGtagConfigResponse])
					}
					object list {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/gtag_config").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListGtagConfigResponse]] = (fun: list) => fun.apply()
					}
				}
				object variables {
					/** Creates a GTM Variable. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def withVariable(body: Schema.Variable) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Variable])
					}
					object create {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/variables").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a GTM Variable. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, variablesId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/variables/${variablesId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					/** Gets a GTM Variable. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Variable]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Variable])
					}
					object get {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, variablesId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/variables/${variablesId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[get, Future[Schema.Variable]] = (fun: get) => fun.apply()
					}
					/** Updates a GTM Variable. */
					class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def withVariable(body: Schema.Variable) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Variable])
					}
					object update {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, variablesId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/variables/${variablesId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
					}
					/** Reverts changes to a GTM Variable in a GTM Workspace. */
					class revert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RevertVariableResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.RevertVariableResponse])
					}
					object revert {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, variablesId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): revert = new revert(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/variables/${variablesId}:revert").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
						given Conversion[revert, Future[Schema.RevertVariableResponse]] = (fun: revert) => fun.apply()
					}
					/** Lists all GTM Variables of a Container. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListVariablesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListVariablesResponse])
					}
					object list {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/variables").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListVariablesResponse]] = (fun: list) => fun.apply()
					}
				}
				object transformations {
					/** Creates a GTM Transformation. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def withTransformation(body: Schema.Transformation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Transformation])
					}
					object create {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/transformations").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a GTM Transformation. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, transformationsId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/transformations/${transformationsId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					/** Gets a GTM Transformation. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Transformation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Transformation])
					}
					object get {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, transformationsId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/transformations/${transformationsId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[get, Future[Schema.Transformation]] = (fun: get) => fun.apply()
					}
					/** Updates a GTM Transformation. */
					class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def withTransformation(body: Schema.Transformation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Transformation])
					}
					object update {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, transformationsId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/transformations/${transformationsId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
					}
					/** Reverts changes to a GTM Transformation in a GTM Workspace. */
					class revert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RevertTransformationResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.RevertTransformationResponse])
					}
					object revert {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, transformationsId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): revert = new revert(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/transformations/${transformationsId}:revert").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
						given Conversion[revert, Future[Schema.RevertTransformationResponse]] = (fun: revert) => fun.apply()
					}
					/** Lists all GTM Transformations of a GTM container workspace. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTransformationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTransformationsResponse])
					}
					object list {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/transformations").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListTransformationsResponse]] = (fun: list) => fun.apply()
					}
				}
				object zones {
					/** Creates a GTM Zone. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def withZone(body: Schema.Zone) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Zone])
					}
					object create {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/zones").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a GTM Zone. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, zonesId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/zones/${zonesId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					/** Gets a GTM Zone. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Zone]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Zone])
					}
					object get {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, zonesId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/zones/${zonesId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[get, Future[Schema.Zone]] = (fun: get) => fun.apply()
					}
					/** Updates a GTM Zone. */
					class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def withZone(body: Schema.Zone) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Zone])
					}
					object update {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, zonesId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/zones/${zonesId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
					}
					/** Reverts changes to a GTM Zone in a GTM Workspace. */
					class revert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RevertZoneResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.RevertZoneResponse])
					}
					object revert {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, zonesId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): revert = new revert(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/zones/${zonesId}:revert").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
						given Conversion[revert, Future[Schema.RevertZoneResponse]] = (fun: revert) => fun.apply()
					}
					/** Lists all GTM Zones of a GTM container workspace. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListZonesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListZonesResponse])
					}
					object list {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/zones").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListZonesResponse]] = (fun: list) => fun.apply()
					}
				}
				object clients {
					/** Creates a GTM Client. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def withClient(body: Schema.Client) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Client])
					}
					object create {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/clients").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a GTM Client. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, clientsId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/clients/${clientsId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					/** Gets a GTM Client. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Client]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Client])
					}
					object get {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, clientsId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/clients/${clientsId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[get, Future[Schema.Client]] = (fun: get) => fun.apply()
					}
					/** Updates a GTM Client. */
					class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def withClient(body: Schema.Client) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Client])
					}
					object update {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, clientsId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/clients/${clientsId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
					}
					/** Reverts changes to a GTM Client in a GTM Workspace. */
					class revert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RevertClientResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.RevertClientResponse])
					}
					object revert {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, clientsId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): revert = new revert(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/clients/${clientsId}:revert").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
						given Conversion[revert, Future[Schema.RevertClientResponse]] = (fun: revert) => fun.apply()
					}
					/** Lists all GTM Clients of a GTM container workspace. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListClientsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListClientsResponse])
					}
					object list {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/clients").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListClientsResponse]] = (fun: list) => fun.apply()
					}
				}
				object tags {
					/** Creates a GTM Tag. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def withTag(body: Schema.Tag) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Tag])
					}
					object create {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/tags").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a GTM Tag. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, tagsId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/tags/${tagsId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					/** Gets a GTM Tag. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Tag]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Tag])
					}
					object get {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, tagsId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/tags/${tagsId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[get, Future[Schema.Tag]] = (fun: get) => fun.apply()
					}
					/** Updates a GTM Tag. */
					class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def withTag(body: Schema.Tag) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Tag])
					}
					object update {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, tagsId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/tags/${tagsId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
					}
					/** Reverts changes to a GTM Tag in a GTM Workspace. */
					class revert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RevertTagResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.RevertTagResponse])
					}
					object revert {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, tagsId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): revert = new revert(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/tags/${tagsId}:revert").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
						given Conversion[revert, Future[Schema.RevertTagResponse]] = (fun: revert) => fun.apply()
					}
					/** Lists all GTM Tags of a Container. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTagsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTagsResponse])
					}
					object list {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/tags").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListTagsResponse]] = (fun: list) => fun.apply()
					}
				}
				object triggers {
					/** Creates a GTM Trigger. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def withTrigger(body: Schema.Trigger) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Trigger])
					}
					object create {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/triggers").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a GTM Trigger. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, triggersId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/triggers/${triggersId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					/** Gets a GTM Trigger. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Trigger]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Trigger])
					}
					object get {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, triggersId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/triggers/${triggersId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[get, Future[Schema.Trigger]] = (fun: get) => fun.apply()
					}
					/** Updates a GTM Trigger. */
					class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def withTrigger(body: Schema.Trigger) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Trigger])
					}
					object update {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, triggersId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/triggers/${triggersId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
					}
					/** Reverts changes to a GTM Trigger in a GTM Workspace. */
					class revert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RevertTriggerResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.RevertTriggerResponse])
					}
					object revert {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, triggersId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): revert = new revert(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/triggers/${triggersId}:revert").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
						given Conversion[revert, Future[Schema.RevertTriggerResponse]] = (fun: revert) => fun.apply()
					}
					/** Lists all GTM Triggers of a Container. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTriggersResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTriggersResponse])
					}
					object list {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/triggers").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListTriggersResponse]] = (fun: list) => fun.apply()
					}
				}
				object built_in_variables {
					/** Creates one or more GTM Built-In Variables. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CreateBuiltInVariableResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.CreateBuiltInVariableResponse])
					}
					object create {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, `type`: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/built_in_variables").addQueryStringParameters("parent" -> parent.toString, "type" -> `type`.toString))
						given Conversion[create, Future[Schema.CreateBuiltInVariableResponse]] = (fun: create) => fun.apply()
					}
					/** Deletes one or more GTM Built-In Variables. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, path: String, `type`: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/built_in_variables").addQueryStringParameters("path" -> path.toString, "type" -> `type`.toString))
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					/** Lists all the enabled Built-In Variables of a GTM Container. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEnabledBuiltInVariablesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEnabledBuiltInVariablesResponse])
					}
					object list {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/built_in_variables").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListEnabledBuiltInVariablesResponse]] = (fun: list) => fun.apply()
					}
					/** Reverts changes to a GTM Built-In Variables in a GTM Workspace. */
					class revert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RevertBuiltInVariableResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.RevertBuiltInVariableResponse])
					}
					object revert {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, path: String, `type`: String)(using signer: RequestSigner, ec: ExecutionContext): revert = new revert(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/built_in_variables:revert").addQueryStringParameters("path" -> path.toString, "type" -> `type`.toString))
						given Conversion[revert, Future[Schema.RevertBuiltInVariableResponse]] = (fun: revert) => fun.apply()
					}
				}
				object templates {
					/** Creates a GTM Custom Template. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def withCustomTemplate(body: Schema.CustomTemplate) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CustomTemplate])
					}
					object create {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/templates").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a GTM Template. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, templatesId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/templates/${templatesId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					/** Gets a GTM Template. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CustomTemplate]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CustomTemplate])
					}
					object get {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, templatesId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/templates/${templatesId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[get, Future[Schema.CustomTemplate]] = (fun: get) => fun.apply()
					}
					/** Updates a GTM Template. */
					class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def withCustomTemplate(body: Schema.CustomTemplate) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.CustomTemplate])
					}
					object update {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, templatesId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/templates/${templatesId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
					}
					/** Reverts changes to a GTM Template in a GTM Workspace. */
					class revert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RevertTemplateResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.RevertTemplateResponse])
					}
					object revert {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, templatesId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): revert = new revert(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/templates/${templatesId}:revert").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
						given Conversion[revert, Future[Schema.RevertTemplateResponse]] = (fun: revert) => fun.apply()
					}
					/** Lists all GTM Templates of a GTM container workspace. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTemplatesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTemplatesResponse])
					}
					object list {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/templates").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListTemplatesResponse]] = (fun: list) => fun.apply()
					}
				}
				object folders {
					/** Moves entities to a GTM Folder. If {folder_id} in the request path equals 0, this will instead move entities out of the folder they currently belong to. */
					class move_entities_to_folder(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def withFolder(body: Schema.Folder) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
					}
					object move_entities_to_folder {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, foldersId :PlayApi, path: String, tagId: String, variableId: String, triggerId: String)(using signer: RequestSigner, ec: ExecutionContext): move_entities_to_folder = new move_entities_to_folder(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/folders/${foldersId}:move_entities_to_folder").addQueryStringParameters("path" -> path.toString, "tagId" -> tagId.toString, "variableId" -> variableId.toString, "triggerId" -> triggerId.toString))
					}
					/** List all entities in a GTM Folder. */
					class entities(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FolderEntities]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.FolderEntities])
					}
					object entities {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, foldersId :PlayApi, path: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): entities = new entities(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/folders/${foldersId}:entities").addQueryStringParameters("path" -> path.toString, "pageToken" -> pageToken.toString))
						given Conversion[entities, Future[Schema.FolderEntities]] = (fun: entities) => fun.apply()
					}
					/** Creates a GTM Folder. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def withFolder(body: Schema.Folder) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Folder])
					}
					object create {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/folders").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a GTM Folder. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, foldersId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/folders/${foldersId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					/** Gets a GTM Folder. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Folder]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Folder])
					}
					object get {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, foldersId :PlayApi, path: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/folders/${foldersId}").addQueryStringParameters("path" -> path.toString))
						given Conversion[get, Future[Schema.Folder]] = (fun: get) => fun.apply()
					}
					/** Updates a GTM Folder. */
					class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def withFolder(body: Schema.Folder) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Folder])
					}
					object update {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, foldersId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/folders/${foldersId}").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
					}
					/** Reverts changes to a GTM Folder in a GTM Workspace. */
					class revert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RevertFolderResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.RevertFolderResponse])
					}
					object revert {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, foldersId :PlayApi, path: String, fingerprint: String)(using signer: RequestSigner, ec: ExecutionContext): revert = new revert(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/folders/${foldersId}:revert").addQueryStringParameters("path" -> path.toString, "fingerprint" -> fingerprint.toString))
						given Conversion[revert, Future[Schema.RevertFolderResponse]] = (fun: revert) => fun.apply()
					}
					/** Lists all GTM Folders of a Container. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFoldersResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/tagmanager.edit.containers""", """https://www.googleapis.com/auth/tagmanager.readonly""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFoldersResponse])
					}
					object list {
						def apply(accountsId :PlayApi, containersId :PlayApi, workspacesId :PlayApi, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"tagmanager/v2/accounts/${accountsId}/containers/${containersId}/workspaces/${workspacesId}/folders").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListFoldersResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
}
