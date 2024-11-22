package com.boresjo.play.api.google.chat

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
		"""https://www.googleapis.com/auth/chat.admin.delete""" /* Delete conversations and spaces owned by your organization and remove access to associated files in Google Chat */,
		"""https://www.googleapis.com/auth/chat.admin.memberships""" /* View, add, update and remove members and managers in conversations owned by your organization */,
		"""https://www.googleapis.com/auth/chat.admin.memberships.readonly""" /* View members and managers in conversations owned by your organization */,
		"""https://www.googleapis.com/auth/chat.admin.spaces""" /* View or edit display name, description, and other metadata for all Google Chat conversations owned by your organization */,
		"""https://www.googleapis.com/auth/chat.admin.spaces.readonly""" /* View display name, description, and other metadata for all Google Chat conversations owned by your organization */,
		"""https://www.googleapis.com/auth/chat.app.delete""" /* On their own behalf, apps in Google Chat can delete conversations and spaces and remove access to associated files */,
		"""https://www.googleapis.com/auth/chat.app.memberships""" /* On their own behalf, apps in Google Chat can see, add, update, and remove members from conversations and spaces */,
		"""https://www.googleapis.com/auth/chat.app.spaces""" /* On their own behalf, apps in Google Chat can create conversations and spaces and see or update their metadata (including history settings and access settings) */,
		"""https://www.googleapis.com/auth/chat.app.spaces.create""" /* On their own behalf, apps in Google Chat can create conversations and spaces */,
		"""https://www.googleapis.com/auth/chat.bot""" /* Private Service: https://www.googleapis.com/auth/chat.bot */,
		"""https://www.googleapis.com/auth/chat.delete""" /* Delete conversations and spaces and remove access to associated files in Google Chat */,
		"""https://www.googleapis.com/auth/chat.import""" /* Import spaces, messages, and memberships into Google Chat. */,
		"""https://www.googleapis.com/auth/chat.memberships""" /* See, add, update, and remove members from conversations and spaces in Google Chat */,
		"""https://www.googleapis.com/auth/chat.memberships.app""" /* Add and remove itself from conversations and spaces in Google Chat */,
		"""https://www.googleapis.com/auth/chat.memberships.readonly""" /* View members in Google Chat conversations. */,
		"""https://www.googleapis.com/auth/chat.messages""" /* See, compose, send, update, and delete messages and their associated attachments, and add, see, and delete reactions to messages. */,
		"""https://www.googleapis.com/auth/chat.messages.create""" /* Compose and send messages in Google Chat */,
		"""https://www.googleapis.com/auth/chat.messages.reactions""" /* See, add, and delete reactions to messages in Google Chat */,
		"""https://www.googleapis.com/auth/chat.messages.reactions.create""" /* Add reactions to messages in Google Chat */,
		"""https://www.googleapis.com/auth/chat.messages.reactions.readonly""" /* View reactions to messages in Google Chat */,
		"""https://www.googleapis.com/auth/chat.messages.readonly""" /* See messages and their associated reactions and attachments in Google Chat */,
		"""https://www.googleapis.com/auth/chat.spaces""" /* Create conversations and spaces and see or update metadata (including history settings and access settings) in Google Chat */,
		"""https://www.googleapis.com/auth/chat.spaces.create""" /* Create new conversations and spaces in Google Chat */,
		"""https://www.googleapis.com/auth/chat.spaces.readonly""" /* View chat and spaces in Google Chat */,
		"""https://www.googleapis.com/auth/chat.users.readstate""" /* View and modify last read time for Google Chat conversations */,
		"""https://www.googleapis.com/auth/chat.users.readstate.readonly""" /* View last read time for Google Chat conversations */
	)

	private val BASE_URL = "https://chat.googleapis.com/"

	object media {
		/** Downloads media. Download is supported on the URI `/v1/media/{+name}?alt=media`. */
		class download(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Media]) {
			val scopes = Seq("""https://www.googleapis.com/auth/chat.bot""", """https://www.googleapis.com/auth/chat.messages""", """https://www.googleapis.com/auth/chat.messages.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Media])
		}
		object download {
			def apply(mediaId :PlayApi, resourceName: String)(using signer: RequestSigner, ec: ExecutionContext): download = new download(ws.url(BASE_URL + s"v1/media/${mediaId}").addQueryStringParameters("resourceName" -> resourceName.toString))
			given Conversion[download, Future[Schema.Media]] = (fun: download) => fun.apply()
		}
		/** Uploads an attachment. For an example, see [Upload media as a file attachment](https://developers.google.com/workspace/chat/upload-media-attachments). Requires user [authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user). You can upload attachments up to 200 MB. Certain file types aren't supported. For details, see [File types blocked by Google Chat](https://support.google.com/chat/answer/7651457?&co=GENIE.Platform%3DDesktop#File%20types%20blocked%20in%20Google%20Chat). */
		class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/chat.import""", """https://www.googleapis.com/auth/chat.messages""", """https://www.googleapis.com/auth/chat.messages.create""")
			/** Perform the request */
			def withUploadAttachmentRequest(body: Schema.UploadAttachmentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UploadAttachmentResponse])
		}
		object upload {
			def apply(spacesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/spaces/${spacesId}/attachments:upload").addQueryStringParameters("parent" -> parent.toString))
		}
	}
	object spaces {
		/** Completes the [import process](https://developers.google.com/workspace/chat/import-data) for the specified space and makes it visible to users. Requires [app authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app) and domain-wide delegation. For more information, see [Authorize Google Chat apps to import data](https://developers.google.com/workspace/chat/authorize-import). */
		class completeImport(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/chat.import""")
			/** Perform the request */
			def withCompleteImportSpaceRequest(body: Schema.CompleteImportSpaceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CompleteImportSpaceResponse])
		}
		object completeImport {
			def apply(spacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): completeImport = new completeImport(ws.url(BASE_URL + s"v1/spaces/${spacesId}:completeImport").addQueryStringParameters("name" -> name.toString))
		}
		/** Returns the existing direct message with the specified user. If no direct message space is found, returns a `404 NOT_FOUND` error. For an example, see [Find a direct message](/chat/api/guides/v1/spaces/find-direct-message). With [app authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app), returns the direct message space between the specified user and the calling Chat app. With [user authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user), returns the direct message space between the specified user and the authenticated user. // Supports the following types of [authentication](https://developers.google.com/workspace/chat/authenticate-authorize): - [App authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app) - [User authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user) */
		class findDirectMessage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Space]) {
			val scopes = Seq("""https://www.googleapis.com/auth/chat.bot""", """https://www.googleapis.com/auth/chat.spaces""", """https://www.googleapis.com/auth/chat.spaces.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Space])
		}
		object findDirectMessage {
			def apply(name: String)(using signer: RequestSigner, ec: ExecutionContext): findDirectMessage = new findDirectMessage(ws.url(BASE_URL + s"v1/spaces:findDirectMessage").addQueryStringParameters("name" -> name.toString))
			given Conversion[findDirectMessage, Future[Schema.Space]] = (fun: findDirectMessage) => fun.apply()
		}
		/** Creates a space with no members. Can be used to create a named space, or a group chat in `Import mode`. Spaces grouped by topics aren't supported. For an example, see [Create a space](https://developers.google.com/workspace/chat/create-spaces). If you receive the error message `ALREADY_EXISTS` when creating a space, try a different `displayName`. An existing space within the Google Workspace organization might already use this display name. Supports the following types of [authentication](https://developers.google.com/workspace/chat/authenticate-authorize): - [App authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app) with [administrator approval](https://support.google.com/a?p=chat-app-auth) in [Developer Preview](https://developers.google.com/workspace/preview) - [User authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user) When authenticating as an app, the `space.customer` field must be set in the request. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/chat.app.spaces""", """https://www.googleapis.com/auth/chat.app.spaces.create""", """https://www.googleapis.com/auth/chat.import""", """https://www.googleapis.com/auth/chat.spaces""", """https://www.googleapis.com/auth/chat.spaces.create""")
			/** Optional. A unique identifier for this request. A random UUID is recommended. Specifying an existing request ID returns the space created with that ID instead of creating a new space. Specifying an existing request ID from the same Chat app with a different authenticated user returns an error. */
			def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
			/** Perform the request */
			def withSpace(body: Schema.Space) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Space])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/spaces").addQueryStringParameters())
		}
		/** Deletes a named space. Always performs a cascading delete, which means that the space's child resources—like messages posted in the space and memberships in the space—are also deleted. For an example, see [Delete a space](https://developers.google.com/workspace/chat/delete-spaces). Supports the following types of [authentication](https://developers.google.com/workspace/chat/authenticate-authorize): - [App authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app) with [administrator approval](https://support.google.com/a?p=chat-app-auth) in [Developer Preview](https://developers.google.com/workspace/preview) - [User authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user) You can authenticate and authorize this method with administrator privileges by setting the `use_admin_access` field in the request. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/chat.admin.delete""", """https://www.googleapis.com/auth/chat.app.delete""", """https://www.googleapis.com/auth/chat.delete""", """https://www.googleapis.com/auth/chat.import""")
			/** Optional. When `true`, the method runs using the user's Google Workspace administrator privileges. The calling user must be a Google Workspace administrator with the [manage chat and spaces conversations privilege](https://support.google.com/a/answer/13369245). Requires the `chat.admin.delete` [OAuth 2.0 scope](https://developers.google.com/workspace/chat/authenticate-authorize#chat-api-scopes). */
			def withUseAdminAccess(useAdminAccess: Boolean) = new delete(req.addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(spacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/spaces/${spacesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
		/** Returns details about a space. For an example, see [Get details about a space](https://developers.google.com/workspace/chat/get-spaces). Supports the following types of [authentication](https://developers.google.com/workspace/chat/authenticate-authorize): - [App authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app) - [User authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user) You can authenticate and authorize this method with administrator privileges by setting the `use_admin_access` field in the request. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Space]) {
			val scopes = Seq("""https://www.googleapis.com/auth/chat.admin.spaces""", """https://www.googleapis.com/auth/chat.admin.spaces.readonly""", """https://www.googleapis.com/auth/chat.app.spaces""", """https://www.googleapis.com/auth/chat.bot""", """https://www.googleapis.com/auth/chat.spaces""", """https://www.googleapis.com/auth/chat.spaces.readonly""")
			/** Optional. When `true`, the method runs using the user's Google Workspace administrator privileges. The calling user must be a Google Workspace administrator with the [manage chat and spaces conversations privilege](https://support.google.com/a/answer/13369245). Requires the `chat.admin.spaces` or `chat.admin.spaces.readonly` [OAuth 2.0 scopes](https://developers.google.com/workspace/chat/authenticate-authorize#chat-api-scopes). */
			def withUseAdminAccess(useAdminAccess: Boolean) = new get(req.addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Space])
		}
		object get {
			def apply(spacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/spaces/${spacesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Space]] = (fun: get) => fun.apply()
		}
		/** Updates a space. For an example, see [Update a space](https://developers.google.com/workspace/chat/update-spaces). If you're updating the `displayName` field and receive the error message `ALREADY_EXISTS`, try a different display name.. An existing space within the Google Workspace organization might already use this display name. Supports the following types of [authentication](https://developers.google.com/workspace/chat/authenticate-authorize): - [App authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app) with [administrator approval](https://support.google.com/a?p=chat-app-auth) in [Developer Preview](https://developers.google.com/workspace/preview) - [User authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user) You can authenticate and authorize this method with administrator privileges by setting the `use_admin_access` field in the request. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/chat.admin.spaces""", """https://www.googleapis.com/auth/chat.app.spaces""", """https://www.googleapis.com/auth/chat.import""", """https://www.googleapis.com/auth/chat.spaces""")
			/** Required. The updated field paths, comma separated if there are multiple. You can update the following fields for a space: `space_details`: Updates the space's description. Supports up to 150 characters. `display_name`: Only supports updating the display name for spaces where `spaceType` field is `SPACE`. If you receive the error message `ALREADY_EXISTS`, try a different value. An existing space within the Google Workspace organization might already use this display name. `space_type`: Only supports changing a `GROUP_CHAT` space type to `SPACE`. Include `display_name` together with `space_type` in the update mask and ensure that the specified space has a non-empty display name and the `SPACE` space type. Including the `space_type` mask and the `SPACE` type in the specified space when updating the display name is optional if the existing space already has the `SPACE` type. Trying to update the space type in other ways results in an invalid argument error. `space_type` is not supported with `useAdminAccess`. `space_history_state`: Updates [space history settings](https://support.google.com/chat/answer/7664687) by turning history on or off for the space. Only supported if history settings are enabled for the Google Workspace organization. To update the space history state, you must omit all other field masks in your request. `space_history_state` is not supported with `useAdminAccess`. `access_settings.audience`: Updates the [access setting](https://support.google.com/chat/answer/11971020) of who can discover the space, join the space, and preview the messages in named space where `spaceType` field is `SPACE`. If the existing space has a target audience, you can remove the audience and restrict space access by omitting a value for this field mask. To update access settings for a space, the authenticating user must be a space manager and omit all other field masks in your request. You can't update this field if the space is in [import mode](https://developers.google.com/workspace/chat/import-data-overview). To learn more, see [Make a space discoverable to specific users](https://developers.google.com/workspace/chat/space-target-audience). `access_settings.audience` is not supported with `useAdminAccess`. `permission_settings`: Supports changing the [permission settings](https://support.google.com/chat/answer/13340792) of a space. When updating permission settings, you can only specify `permissionSettings` field masks; you cannot update other field masks at the same time. `permissionSettings` is not supported with `useAdminAccess`. The supported field masks include: - `permission_settings.manageMembersAndGroups` - `permission_settings.modifySpaceDetails` - `permission_settings.toggleHistory` - `permission_settings.useAtMentionAll` - `permission_settings.manageApps` - `permission_settings.manageWebhooks` - `permission_settings.replyMessages`<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			/** Optional. When `true`, the method runs using the user's Google Workspace administrator privileges. The calling user must be a Google Workspace administrator with the [manage chat and spaces conversations privilege](https://support.google.com/a/answer/13369245). Requires the `chat.admin.spaces` [OAuth 2.0 scope](https://developers.google.com/workspace/chat/authenticate-authorize#chat-api-scopes). Some `FieldMask` values are not supported using admin access. For details, see the description of `update_mask`. */
			def withUseAdminAccess(useAdminAccess: Boolean) = new patch(req.addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString))
			/** Perform the request */
			def withSpace(body: Schema.Space) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Space])
		}
		object patch {
			def apply(spacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/spaces/${spacesId}").addQueryStringParameters("name" -> name.toString))
		}
		/** Creates a space and adds specified users to it. The calling user is automatically added to the space, and shouldn't be specified as a membership in the request. For an example, see [Set up a space with initial members](https://developers.google.com/workspace/chat/set-up-spaces). To specify the human members to add, add memberships with the appropriate `membership.member.name`. To add a human user, use `users/{user}`, where `{user}` can be the email address for the user. For users in the same Workspace organization `{user}` can also be the `id` for the person from the People API, or the `id` for the user in the Directory API. For example, if the People API Person profile ID for `user@example.com` is `123456789`, you can add the user to the space by setting the `membership.member.name` to `users/user@example.com` or `users/123456789`. To specify the Google groups to add, add memberships with the appropriate `membership.group_member.name`. To add or invite a Google group, use `groups/{group}`, where `{group}` is the `id` for the group from the Cloud Identity Groups API. For example, you can use [Cloud Identity Groups lookup API](https://cloud.google.com/identity/docs/reference/rest/v1/groups/lookup) to retrieve the ID `123456789` for group email `group@example.com`, then you can add the group to the space by setting the `membership.group_member.name` to `groups/123456789`. Group email is not supported, and Google groups can only be added as members in named spaces. For a named space or group chat, if the caller blocks, or is blocked by some members, or doesn't have permission to add some members, then those members aren't added to the created space. To create a direct message (DM) between the calling user and another human user, specify exactly one membership to represent the human user. If one user blocks the other, the request fails and the DM isn't created. To create a DM between the calling user and the calling app, set `Space.singleUserBotDm` to `true` and don't specify any memberships. You can only use this method to set up a DM with the calling app. To add the calling app as a member of a space or an existing DM between two human users, see [Invite or add a user or app to a space](https://developers.google.com/workspace/chat/create-members). If a DM already exists between two users, even when one user blocks the other at the time a request is made, then the existing DM is returned. Spaces with threaded replies aren't supported. If you receive the error message `ALREADY_EXISTS` when setting up a space, try a different `displayName`. An existing space within the Google Workspace organization might already use this display name. Requires [user authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user). */
		class setup(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/chat.spaces""", """https://www.googleapis.com/auth/chat.spaces.create""")
			/** Perform the request */
			def withSetUpSpaceRequest(body: Schema.SetUpSpaceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Space])
		}
		object setup {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): setup = new setup(ws.url(BASE_URL + s"v1/spaces:setup").addQueryStringParameters())
		}
		/** Lists spaces the caller is a member of. Group chats and DMs aren't listed until the first message is sent. For an example, see [List spaces](https://developers.google.com/workspace/chat/list-spaces). Supports the following types of [authentication](https://developers.google.com/workspace/chat/authenticate-authorize): - [App authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app) - [User authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user) To list all named spaces by Google Workspace organization, use the [`spaces.search()`](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces/search) method using Workspace administrator privileges instead. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSpacesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/chat.bot""", """https://www.googleapis.com/auth/chat.spaces""", """https://www.googleapis.com/auth/chat.spaces.readonly""")
			/** Optional. The maximum number of spaces to return. The service might return fewer than this value. If unspecified, at most 100 spaces are returned. The maximum value is 1000. If you use a value more than 1000, it's automatically changed to 1000. Negative values return an `INVALID_ARGUMENT` error.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A page token, received from a previous list spaces call. Provide this parameter to retrieve the subsequent page. When paginating, the filter value should match the call that provided the page token. Passing a different value may lead to unexpected results. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. A query filter. You can filter spaces by the space type ([`space_type`](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces#spacetype)). To filter by space type, you must specify valid enum value, such as `SPACE` or `GROUP_CHAT` (the `space_type` can't be `SPACE_TYPE_UNSPECIFIED`). To query for multiple space types, use the `OR` operator. For example, the following queries are valid: ``` space_type = "SPACE" spaceType = "GROUP_CHAT" OR spaceType = "DIRECT_MESSAGE" ``` Invalid queries are rejected by the server with an `INVALID_ARGUMENT` error. */
			def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSpacesResponse])
		}
		object list {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/spaces").addQueryStringParameters())
			given Conversion[list, Future[Schema.ListSpacesResponse]] = (fun: list) => fun.apply()
		}
		/** Returns a list of spaces in a Google Workspace organization based on an administrator's search. Requires [user authentication with administrator privileges](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user#admin-privileges). In the request, set `use_admin_access` to `true`. */
		class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SearchSpacesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/chat.admin.spaces""", """https://www.googleapis.com/auth/chat.admin.spaces.readonly""")
			/** Optional. How the list of spaces is ordered. Supported attributes to order by are: - `membership_count.joined_direct_human_user_count` — Denotes the count of human users that have directly joined a space. - `last_active_time` — Denotes the time when last eligible item is added to any topic of this space. - `create_time` — Denotes the time of the space creation. Valid ordering operation values are: - `ASC` for ascending. Default value. - `DESC` for descending. The supported syntax are: - `membership_count.joined_direct_human_user_count DESC` - `membership_count.joined_direct_human_user_count ASC` - `last_active_time DESC` - `last_active_time ASC` - `create_time DESC` - `create_time ASC` */
			def withOrderBy(orderBy: String) = new search(req.addQueryStringParameters("orderBy" -> orderBy.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SearchSpacesResponse])
		}
		object search {
			def apply(useAdminAccess: Boolean, pageSize: Int, pageToken: String, query: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/spaces:search").addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "query" -> query.toString))
			given Conversion[search, Future[Schema.SearchSpacesResponse]] = (fun: search) => fun.apply()
		}
		object messages {
			/** Creates a message in a Google Chat space. For an example, see [Send a message](https://developers.google.com/workspace/chat/create-messages). The `create()` method requires either [user authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user) or [app authentication](https://developers.google.com/workspace/chat/authorize-import). Chat attributes the message sender differently depending on the type of authentication that you use in your request. The following image shows how Chat attributes a message when you use app authentication. Chat displays the Chat app as the message sender. The content of the message can contain text (`text`), cards (`cardsV2`), and accessory widgets (`accessoryWidgets`). ![Message sent with app authentication](https://developers.google.com/workspace/chat/images/message-app-auth.svg) The following image shows how Chat attributes a message when you use user authentication. Chat displays the user as the message sender and attributes the Chat app to the message by displaying its name. The content of message can only contain text (`text`). ![Message sent with user authentication](https://developers.google.com/workspace/chat/images/message-user-auth.svg) The maximum message size, including the message contents, is 32,000 bytes. For [webhook](https://developers.google.com/workspace/chat/quickstart/webhooks) requests, the response doesn't contain the full message. The response only populates the `name` and `thread.name` fields in addition to the information that was in the request. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/chat.bot""", """https://www.googleapis.com/auth/chat.import""", """https://www.googleapis.com/auth/chat.messages""", """https://www.googleapis.com/auth/chat.messages.create""")
				/** Optional. Deprecated: Use thread.thread_key instead. ID for the thread. Supports up to 4000 characters. To start or add to a thread, create a message and specify a `threadKey` or the thread.name. For example usage, see [Start or reply to a message thread](https://developers.google.com/workspace/chat/create-messages#create-message-thread). */
				def withThreadKey(threadKey: String) = new create(req.addQueryStringParameters("threadKey" -> threadKey.toString))
				/** Optional. A unique request ID for this message. Specifying an existing request ID returns the message created with that ID instead of creating a new message. */
				def withRequestId(requestId: String) = new create(req.addQueryStringParameters("requestId" -> requestId.toString))
				/** Optional. Specifies whether a message starts a thread or replies to one. Only supported in named spaces. When [responding to user interactions](https://developers.google.com/workspace/chat/receive-respond-interactions), this field is ignored. For interactions within a thread, the reply is created in the same thread. Otherwise, the reply is created as a new thread.<br>Possible values:<br>MESSAGE_REPLY_OPTION_UNSPECIFIED: Default. Starts a new thread. Using this option ignores any thread ID or `thread_key` that's included.<br>REPLY_MESSAGE_FALLBACK_TO_NEW_THREAD: Creates the message as a reply to the thread specified by thread ID or `thread_key`. If it fails, the message starts a new thread instead.<br>REPLY_MESSAGE_OR_FAIL: Creates the message as a reply to the thread specified by thread ID or `thread_key`. If a new `thread_key` is used, a new thread is created. If the message creation fails, a `NOT_FOUND` error is returned instead. */
				def withMessageReplyOption(messageReplyOption: String) = new create(req.addQueryStringParameters("messageReplyOption" -> messageReplyOption.toString))
				/** Optional. A custom ID for a message. Lets Chat apps get, update, or delete a message without needing to store the system-assigned ID in the message's resource name (represented in the message `name` field). The value for this field must meet the following requirements: &#42; Begins with `client-`. For example, `client-custom-name` is a valid custom ID, but `custom-name` is not. &#42; Contains up to 63 characters and only lowercase letters, numbers, and hyphens. &#42; Is unique within a space. A Chat app can't use the same custom ID for different messages. For details, see [Name a message](https://developers.google.com/workspace/chat/create-messages#name_a_created_message). */
				def withMessageId(messageId: String) = new create(req.addQueryStringParameters("messageId" -> messageId.toString))
				/** Perform the request */
				def withMessage(body: Schema.Message) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Message])
			}
			object create {
				def apply(spacesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/spaces/${spacesId}/messages").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a message. For an example, see [Delete a message](https://developers.google.com/workspace/chat/delete-messages). Supports the following types of [authentication](https://developers.google.com/workspace/chat/authenticate-authorize): - [App authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app) - [User authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user) When using app authentication, requests can only delete messages created by the calling Chat app. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/chat.bot""", """https://www.googleapis.com/auth/chat.import""", """https://www.googleapis.com/auth/chat.messages""")
				/** Optional. When `true`, deleting a message also deletes its threaded replies. When `false`, if a message has threaded replies, deletion fails. Only applies when [authenticating as a user](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user). Has no effect when [authenticating as a Chat app] (https://developers.google.com/workspace/chat/authenticate-authorize-chat-app). */
				def withForce(force: Boolean) = new delete(req.addQueryStringParameters("force" -> force.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(spacesId :PlayApi, messagesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/spaces/${spacesId}/messages/${messagesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Returns details about a message. For an example, see [Get details about a message](https://developers.google.com/workspace/chat/get-messages). Supports the following types of [authentication](https://developers.google.com/workspace/chat/authenticate-authorize): - [App authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app) - [User authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user) Note: Might return a message from a blocked member or space. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Message]) {
				val scopes = Seq("""https://www.googleapis.com/auth/chat.bot""", """https://www.googleapis.com/auth/chat.messages""", """https://www.googleapis.com/auth/chat.messages.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Message])
			}
			object get {
				def apply(spacesId :PlayApi, messagesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/spaces/${spacesId}/messages/${messagesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Message]] = (fun: get) => fun.apply()
			}
			/** Updates a message. There's a difference between the `patch` and `update` methods. The `patch` method uses a `patch` request while the `update` method uses a `put` request. We recommend using the `patch` method. For an example, see [Update a message](https://developers.google.com/workspace/chat/update-messages). Supports the following types of [authentication](https://developers.google.com/workspace/chat/authenticate-authorize): - [App authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app) - [User authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user) When using app authentication, requests can only update messages created by the calling Chat app. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/chat.bot""", """https://www.googleapis.com/auth/chat.import""", """https://www.googleapis.com/auth/chat.messages""")
				/** Optional. If `true` and the message isn't found, a new message is created and `updateMask` is ignored. The specified message ID must be [client-assigned](https://developers.google.com/workspace/chat/create-messages#name_a_created_message) or the request fails. */
				def withAllowMissing(allowMissing: Boolean) = new update(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
				/** Perform the request */
				def withMessage(body: Schema.Message) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Message])
			}
			object update {
				def apply(spacesId :PlayApi, messagesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/spaces/${spacesId}/messages/${messagesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Updates a message. There's a difference between the `patch` and `update` methods. The `patch` method uses a `patch` request while the `update` method uses a `put` request. We recommend using the `patch` method. For an example, see [Update a message](https://developers.google.com/workspace/chat/update-messages). Supports the following types of [authentication](https://developers.google.com/workspace/chat/authenticate-authorize): - [App authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app) - [User authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user) When using app authentication, requests can only update messages created by the calling Chat app. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/chat.bot""", """https://www.googleapis.com/auth/chat.import""", """https://www.googleapis.com/auth/chat.messages""")
				/** Optional. If `true` and the message isn't found, a new message is created and `updateMask` is ignored. The specified message ID must be [client-assigned](https://developers.google.com/workspace/chat/create-messages#name_a_created_message) or the request fails. */
				def withAllowMissing(allowMissing: Boolean) = new patch(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
				/** Perform the request */
				def withMessage(body: Schema.Message) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Message])
			}
			object patch {
				def apply(spacesId :PlayApi, messagesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/spaces/${spacesId}/messages/${messagesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists messages in a space that the caller is a member of, including messages from blocked members and spaces. If you list messages from a space with no messages, the response is an empty object. When using a REST/HTTP interface, the response contains an empty JSON object, `{}`. For an example, see [List messages](https://developers.google.com/workspace/chat/api/guides/v1/messages/list). Requires [user authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user). */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListMessagesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/chat.import""", """https://www.googleapis.com/auth/chat.messages""", """https://www.googleapis.com/auth/chat.messages.readonly""")
				/** Optional. The maximum number of messages returned. The service might return fewer messages than this value. If unspecified, at most 25 are returned. The maximum value is 1000. If you use a value more than 1000, it's automatically changed to 1000. Negative values return an `INVALID_ARGUMENT` error.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token received from a previous list messages call. Provide this parameter to retrieve the subsequent page. When paginating, all other parameters provided should match the call that provided the page token. Passing different values to the other parameters might lead to unexpected results. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. A query filter. You can filter messages by date (`create_time`) and thread (`thread.name`). To filter messages by the date they were created, specify the `create_time` with a timestamp in [RFC-3339](https://www.rfc-editor.org/rfc/rfc3339) format and double quotation marks. For example, `"2023-04-21T11:30:00-04:00"`. You can use the greater than operator `>` to list messages that were created after a timestamp, or the less than operator `<` to list messages that were created before a timestamp. To filter messages within a time interval, use the `AND` operator between two timestamps. To filter by thread, specify the `thread.name`, formatted as `spaces/{space}/threads/{thread}`. You can only specify one `thread.name` per query. To filter by both thread and date, use the `AND` operator in your query. For example, the following queries are valid: ``` create_time > "2012-04-21T11:30:00-04:00" create_time > "2012-04-21T11:30:00-04:00" AND thread.name = spaces/AAAAAAAAAAA/threads/123 create_time > "2012-04-21T11:30:00+00:00" AND create_time < "2013-01-01T00:00:00+00:00" AND thread.name = spaces/AAAAAAAAAAA/threads/123 thread.name = spaces/AAAAAAAAAAA/threads/123 ``` Invalid queries are rejected by the server with an `INVALID_ARGUMENT` error. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. How the list of messages is ordered. Specify a value to order by an ordering operation. Valid ordering operation values are as follows: - `ASC` for ascending. - `DESC` for descending. The default ordering is `create_time ASC`. */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Optional. Whether to include deleted messages. Deleted messages include deleted time and metadata about their deletion, but message content is unavailable. */
				def withShowDeleted(showDeleted: Boolean) = new list(req.addQueryStringParameters("showDeleted" -> showDeleted.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListMessagesResponse])
			}
			object list {
				def apply(spacesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/spaces/${spacesId}/messages").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListMessagesResponse]] = (fun: list) => fun.apply()
			}
			object reactions {
				/** Creates a reaction and adds it to a message. Only unicode emojis are supported. For an example, see [Add a reaction to a message](https://developers.google.com/workspace/chat/create-reactions). Requires [user authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user). */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/chat.import""", """https://www.googleapis.com/auth/chat.messages""", """https://www.googleapis.com/auth/chat.messages.reactions""", """https://www.googleapis.com/auth/chat.messages.reactions.create""")
					/** Perform the request */
					def withReaction(body: Schema.Reaction) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Reaction])
				}
				object create {
					def apply(spacesId :PlayApi, messagesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/spaces/${spacesId}/messages/${messagesId}/reactions").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Lists reactions to a message. For an example, see [List reactions for a message](https://developers.google.com/workspace/chat/list-reactions). Requires [user authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user). */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListReactionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/chat.messages""", """https://www.googleapis.com/auth/chat.messages.reactions""", """https://www.googleapis.com/auth/chat.messages.reactions.readonly""", """https://www.googleapis.com/auth/chat.messages.readonly""")
					/** Optional. The maximum number of reactions returned. The service can return fewer reactions than this value. If unspecified, the default value is 25. The maximum value is 200; values above 200 are changed to 200.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. (If resuming from a previous query.) A page token received from a previous list reactions call. Provide this to retrieve the subsequent page. When paginating, the filter value should match the call that provided the page token. Passing a different value might lead to unexpected results. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. A query filter. You can filter reactions by [emoji](https://developers.google.com/workspace/chat/api/reference/rest/v1/Emoji) (either `emoji.unicode` or `emoji.custom_emoji.uid`) and [user](https://developers.google.com/workspace/chat/api/reference/rest/v1/User) (`user.name`). To filter reactions for multiple emojis or users, join similar fields with the `OR` operator, such as `emoji.unicode = "🙂" OR emoji.unicode = "👍"` and `user.name = "users/AAAAAA" OR user.name = "users/BBBBBB"`. To filter reactions by emoji and user, use the `AND` operator, such as `emoji.unicode = "🙂" AND user.name = "users/AAAAAA"`. If your query uses both `AND` and `OR`, group them with parentheses. For example, the following queries are valid: ``` user.name = "users/{user}" emoji.unicode = "🙂" emoji.custom_emoji.uid = "{uid}" emoji.unicode = "🙂" OR emoji.unicode = "👍" emoji.unicode = "🙂" OR emoji.custom_emoji.uid = "{uid}" emoji.unicode = "🙂" AND user.name = "users/{user}" (emoji.unicode = "🙂" OR emoji.custom_emoji.uid = "{uid}") AND user.name = "users/{user}" ``` The following queries are invalid: ``` emoji.unicode = "🙂" AND emoji.unicode = "👍" emoji.unicode = "🙂" AND emoji.custom_emoji.uid = "{uid}" emoji.unicode = "🙂" OR user.name = "users/{user}" emoji.unicode = "🙂" OR emoji.custom_emoji.uid = "{uid}" OR user.name = "users/{user}" emoji.unicode = "🙂" OR emoji.custom_emoji.uid = "{uid}" AND user.name = "users/{user}" ``` Invalid queries are rejected by the server with an `INVALID_ARGUMENT` error. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListReactionsResponse])
				}
				object list {
					def apply(spacesId :PlayApi, messagesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/spaces/${spacesId}/messages/${messagesId}/reactions").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListReactionsResponse]] = (fun: list) => fun.apply()
				}
				/** Deletes a reaction to a message. Only unicode emojis are supported. For an example, see [Delete a reaction](https://developers.google.com/workspace/chat/delete-reactions). Requires [user authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user). */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/chat.import""", """https://www.googleapis.com/auth/chat.messages""", """https://www.googleapis.com/auth/chat.messages.reactions""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(spacesId :PlayApi, messagesId :PlayApi, reactionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/spaces/${spacesId}/messages/${messagesId}/reactions/${reactionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
			}
			object attachments {
				/** Gets the metadata of a message attachment. The attachment data is fetched using the [media API](https://developers.google.com/workspace/chat/api/reference/rest/v1/media/download). For an example, see [Get metadata about a message attachment](https://developers.google.com/workspace/chat/get-media-attachments). Requires [app authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app). */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Attachment]) {
					val scopes = Seq("""https://www.googleapis.com/auth/chat.bot""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Attachment])
				}
				object get {
					def apply(spacesId :PlayApi, messagesId :PlayApi, attachmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/spaces/${spacesId}/messages/${messagesId}/attachments/${attachmentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Attachment]] = (fun: get) => fun.apply()
				}
			}
		}
		object spaceEvents {
			/** Returns an event from a Google Chat space. The [event payload](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces.spaceEvents#SpaceEvent.FIELDS.oneof_payload) contains the most recent version of the resource that changed. For example, if you request an event about a new message but the message was later updated, the server returns the updated `Message` resource in the event payload. Note: The `permissionSettings` field is not returned in the Space object of the Space event data for this request. Requires [user authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user). To get an event, the authenticated user must be a member of the space. For an example, see [Get details about an event from a Google Chat space](https://developers.google.com/workspace/chat/get-space-event). */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SpaceEvent]) {
				val scopes = Seq("""https://www.googleapis.com/auth/chat.memberships""", """https://www.googleapis.com/auth/chat.memberships.readonly""", """https://www.googleapis.com/auth/chat.messages""", """https://www.googleapis.com/auth/chat.messages.reactions""", """https://www.googleapis.com/auth/chat.messages.reactions.readonly""", """https://www.googleapis.com/auth/chat.messages.readonly""", """https://www.googleapis.com/auth/chat.spaces""", """https://www.googleapis.com/auth/chat.spaces.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SpaceEvent])
			}
			object get {
				def apply(spacesId :PlayApi, spaceEventsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/spaces/${spacesId}/spaceEvents/${spaceEventsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.SpaceEvent]] = (fun: get) => fun.apply()
			}
			/** Lists events from a Google Chat space. For each event, the [payload](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces.spaceEvents#SpaceEvent.FIELDS.oneof_payload) contains the most recent version of the Chat resource. For example, if you list events about new space members, the server returns `Membership` resources that contain the latest membership details. If new members were removed during the requested period, the event payload contains an empty `Membership` resource. Requires [user authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user). To list events, the authenticated user must be a member of the space. For an example, see [List events from a Google Chat space](https://developers.google.com/workspace/chat/list-space-events). */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSpaceEventsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/chat.memberships""", """https://www.googleapis.com/auth/chat.memberships.readonly""", """https://www.googleapis.com/auth/chat.messages""", """https://www.googleapis.com/auth/chat.messages.reactions""", """https://www.googleapis.com/auth/chat.messages.reactions.readonly""", """https://www.googleapis.com/auth/chat.messages.readonly""", """https://www.googleapis.com/auth/chat.spaces""", """https://www.googleapis.com/auth/chat.spaces.readonly""")
				/** Optional. The maximum number of space events returned. The service might return fewer than this value. Negative values return an `INVALID_ARGUMENT` error.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous list space events call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to list space events must match the call that provided the page token. Passing different values to the other parameters might lead to unexpected results. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Required. A query filter. You must specify at least one event type (`event_type`) using the has `:` operator. To filter by multiple event types, use the `OR` operator. Omit batch event types in your filter. The request automatically returns any related batch events. For example, if you filter by new reactions (`google.workspace.chat.reaction.v1.created`), the server also returns batch new reactions events (`google.workspace.chat.reaction.v1.batchCreated`). For a list of supported event types, see the [`SpaceEvents` reference documentation](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces.spaceEvents#SpaceEvent.FIELDS.event_type). Optionally, you can also filter by start time (`start_time`) and end time (`end_time`): &#42; `start_time`: Exclusive timestamp from which to start listing space events. You can list events that occurred up to 28 days ago. If unspecified, lists space events from the past 28 days. &#42; `end_time`: Inclusive timestamp until which space events are listed. If unspecified, lists events up to the time of the request. To specify a start or end time, use the equals `=` operator and format in [RFC-3339](https://www.rfc-editor.org/rfc/rfc3339). To filter by both `start_time` and `end_time`, use the `AND` operator. For example, the following queries are valid: ``` start_time="2023-08-23T19:20:33+00:00" AND end_time="2023-08-23T19:21:54+00:00" ``` ``` start_time="2023-08-23T19:20:33+00:00" AND (event_types:"google.workspace.chat.space.v1.updated" OR event_types:"google.workspace.chat.message.v1.created") ``` The following queries are invalid: ``` start_time="2023-08-23T19:20:33+00:00" OR end_time="2023-08-23T19:21:54+00:00" ``` ``` event_types:"google.workspace.chat.space.v1.updated" AND event_types:"google.workspace.chat.message.v1.created" ``` Invalid queries are rejected by the server with an `INVALID_ARGUMENT` error. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSpaceEventsResponse])
			}
			object list {
				def apply(spacesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/spaces/${spacesId}/spaceEvents").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListSpaceEventsResponse]] = (fun: list) => fun.apply()
			}
		}
		object members {
			/** Creates a membership for the calling Chat app, a user, or a Google Group. Creating memberships for other Chat apps isn't supported. When creating a membership, if the specified member has their auto-accept policy turned off, then they're invited, and must accept the space invitation before joining. Otherwise, creating a membership adds the member directly to the specified space. Supports the following types of [authentication](https://developers.google.com/workspace/chat/authenticate-authorize): - [App authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app) with [administrator approval](https://support.google.com/a?p=chat-app-auth) in [Developer Preview](https://developers.google.com/workspace/preview) - [User authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user) You can authenticate and authorize this method with administrator privileges by setting the `use_admin_access` field in the request. For example usage, see: - [Invite or add a user to a space](https://developers.google.com/workspace/chat/create-members#create-user-membership). - [Invite or add a Google Group to a space](https://developers.google.com/workspace/chat/create-members#create-group-membership). - [Add the Chat app to a space](https://developers.google.com/workspace/chat/create-members#create-membership-calling-api). */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/chat.admin.memberships""", """https://www.googleapis.com/auth/chat.app.memberships""", """https://www.googleapis.com/auth/chat.import""", """https://www.googleapis.com/auth/chat.memberships""", """https://www.googleapis.com/auth/chat.memberships.app""")
				/** Optional. When `true`, the method runs using the user's Google Workspace administrator privileges. The calling user must be a Google Workspace administrator with the [manage chat and spaces conversations privilege](https://support.google.com/a/answer/13369245). Requires the `chat.admin.memberships` [OAuth 2.0 scope](https://developers.google.com/workspace/chat/authenticate-authorize#chat-api-scopes). Creating app memberships or creating memberships for users outside the administrator's Google Workspace organization isn't supported using admin access. */
				def withUseAdminAccess(useAdminAccess: Boolean) = new create(req.addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString))
				/** Perform the request */
				def withMembership(body: Schema.Membership) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Membership])
			}
			object create {
				def apply(spacesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/spaces/${spacesId}/members").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes a membership. For an example, see [Remove a user or a Google Chat app from a space](https://developers.google.com/workspace/chat/delete-members). Supports the following types of [authentication](https://developers.google.com/workspace/chat/authenticate-authorize): - [App authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app) with [administrator approval](https://support.google.com/a?p=chat-app-auth) in [Developer Preview](https://developers.google.com/workspace/preview) - [User authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user) You can authenticate and authorize this method with administrator privileges by setting the `use_admin_access` field in the request. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Membership]) {
				val scopes = Seq("""https://www.googleapis.com/auth/chat.admin.memberships""", """https://www.googleapis.com/auth/chat.app.memberships""", """https://www.googleapis.com/auth/chat.import""", """https://www.googleapis.com/auth/chat.memberships""", """https://www.googleapis.com/auth/chat.memberships.app""")
				/** Optional. When `true`, the method runs using the user's Google Workspace administrator privileges. The calling user must be a Google Workspace administrator with the [manage chat and spaces conversations privilege](https://support.google.com/a/answer/13369245). Requires the `chat.admin.memberships` [OAuth 2.0 scope](https://developers.google.com/workspace/chat/authenticate-authorize#chat-api-scopes). Deleting app memberships in a space isn't supported using admin access. */
				def withUseAdminAccess(useAdminAccess: Boolean) = new delete(req.addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Membership])
			}
			object delete {
				def apply(spacesId :PlayApi, membersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/spaces/${spacesId}/members/${membersId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Membership]] = (fun: delete) => fun.apply()
			}
			/** Returns details about a membership. For an example, see [Get details about a user's or Google Chat app's membership](https://developers.google.com/workspace/chat/get-members). Supports the following types of [authentication](https://developers.google.com/workspace/chat/authenticate-authorize): - [App authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app) - [User authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user) You can authenticate and authorize this method with administrator privileges by setting the `use_admin_access` field in the request. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Membership]) {
				val scopes = Seq("""https://www.googleapis.com/auth/chat.admin.memberships""", """https://www.googleapis.com/auth/chat.admin.memberships.readonly""", """https://www.googleapis.com/auth/chat.bot""", """https://www.googleapis.com/auth/chat.memberships""", """https://www.googleapis.com/auth/chat.memberships.readonly""")
				/** Required. Resource name of the membership to retrieve. To get the app's own membership [by using user authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user), you can optionally use `spaces/{space}/members/app`. Format: `spaces/{space}/members/{member}` or `spaces/{space}/members/app` You can use the user's email as an alias for `{member}`. For example, `spaces/{space}/members/example@gmail.com` where `example@gmail.com` is the email of the Google Chat user. */
				def withName(name: String) = new get(req.addQueryStringParameters("name" -> name.toString))
				/** Optional. When `true`, the method runs using the user's Google Workspace administrator privileges. The calling user must be a Google Workspace administrator with the [manage chat and spaces conversations privilege](https://support.google.com/a/answer/13369245). Requires the `chat.admin.memberships` or `chat.admin.memberships.readonly` [OAuth 2.0 scopes](https://developers.google.com/workspace/chat/authenticate-authorize#chat-api-scopes). Getting app memberships in a space isn't supported when using admin access. */
				def withUseAdminAccess(useAdminAccess: Boolean) = new get(req.addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Membership])
			}
			object get {
				def apply(spacesId :PlayApi, membersId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/spaces/${spacesId}/members/${membersId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Membership]] = (fun: get) => fun.apply()
			}
			/** Updates a membership. For an example, see [Update a user's membership in a space](https://developers.google.com/workspace/chat/update-members). Supports the following types of [authentication](https://developers.google.com/workspace/chat/authenticate-authorize): - [App authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app) with [administrator approval](https://support.google.com/a?p=chat-app-auth) in [Developer Preview](https://developers.google.com/workspace/preview) - [User authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user) You can authenticate and authorize this method with administrator privileges by setting the `use_admin_access` field in the request. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/chat.admin.memberships""", """https://www.googleapis.com/auth/chat.app.memberships""", """https://www.googleapis.com/auth/chat.import""", """https://www.googleapis.com/auth/chat.memberships""")
				/** Optional. When `true`, the method runs using the user's Google Workspace administrator privileges. The calling user must be a Google Workspace administrator with the [manage chat and spaces conversations privilege](https://support.google.com/a/answer/13369245). Requires the `chat.admin.memberships` [OAuth 2.0 scope](https://developers.google.com/workspace/chat/authenticate-authorize#chat-api-scopes). */
				def withUseAdminAccess(useAdminAccess: Boolean) = new patch(req.addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString))
				/** Perform the request */
				def withMembership(body: Schema.Membership) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Membership])
			}
			object patch {
				def apply(spacesId :PlayApi, membersId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/spaces/${spacesId}/members/${membersId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists memberships in a space. For an example, see [List users and Google Chat apps in a space](https://developers.google.com/workspace/chat/list-members). Listing memberships with [app authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app) lists memberships in spaces that the Chat app has access to, but excludes Chat app memberships, including its own. Listing memberships with [User authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user) lists memberships in spaces that the authenticated user has access to. Supports the following types of [authentication](https://developers.google.com/workspace/chat/authenticate-authorize): - [App authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-app) - [User authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user) You can authenticate and authorize this method with administrator privileges by setting the `use_admin_access` field in the request. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListMembershipsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/chat.admin.memberships""", """https://www.googleapis.com/auth/chat.admin.memberships.readonly""", """https://www.googleapis.com/auth/chat.bot""", """https://www.googleapis.com/auth/chat.import""", """https://www.googleapis.com/auth/chat.memberships""", """https://www.googleapis.com/auth/chat.memberships.readonly""")
				/** Optional. The maximum number of memberships to return. The service might return fewer than this value. If unspecified, at most 100 memberships are returned. The maximum value is 1000. If you use a value more than 1000, it's automatically changed to 1000. Negative values return an `INVALID_ARGUMENT` error.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous call to list memberships. Provide this parameter to retrieve the subsequent page. When paginating, all other parameters provided should match the call that provided the page token. Passing different values to the other parameters might lead to unexpected results. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. A query filter. You can filter memberships by a member's role ([`role`](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces.members#membershiprole)) and type ([`member.type`](https://developers.google.com/workspace/chat/api/reference/rest/v1/User#type)). To filter by role, set `role` to `ROLE_MEMBER` or `ROLE_MANAGER`. To filter by type, set `member.type` to `HUMAN` or `BOT`. You can also filter for `member.type` using the `!=` operator. To filter by both role and type, use the `AND` operator. To filter by either role or type, use the `OR` operator. Either `member.type = "HUMAN"` or `member.type != "BOT"` is required when `use_admin_access` is set to true. Other member type filters will be rejected. For example, the following queries are valid: ``` role = "ROLE_MANAGER" OR role = "ROLE_MEMBER" member.type = "HUMAN" AND role = "ROLE_MANAGER" member.type != "BOT" ``` The following queries are invalid: ``` member.type = "HUMAN" AND member.type = "BOT" role = "ROLE_MANAGER" AND role = "ROLE_MEMBER" ``` Invalid queries are rejected by the server with an `INVALID_ARGUMENT` error. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. When `true`, also returns memberships associated with a Google Group, in addition to other types of memberships. If a filter is set, Google Group memberships that don't match the filter criteria aren't returned. */
				def withShowGroups(showGroups: Boolean) = new list(req.addQueryStringParameters("showGroups" -> showGroups.toString))
				/** Optional. When `true`, also returns memberships associated with invited members, in addition to other types of memberships. If a filter is set, invited memberships that don't match the filter criteria aren't returned. Currently requires [user authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user). */
				def withShowInvited(showInvited: Boolean) = new list(req.addQueryStringParameters("showInvited" -> showInvited.toString))
				/** Optional. When `true`, the method runs using the user's Google Workspace administrator privileges. The calling user must be a Google Workspace administrator with the [manage chat and spaces conversations privilege](https://support.google.com/a/answer/13369245). Requires either the `chat.admin.memberships.readonly` or `chat.admin.memberships` [OAuth 2.0 scope](https://developers.google.com/workspace/chat/authenticate-authorize#chat-api-scopes). Listing app memberships in a space isn't supported when using admin access. */
				def withUseAdminAccess(useAdminAccess: Boolean) = new list(req.addQueryStringParameters("useAdminAccess" -> useAdminAccess.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListMembershipsResponse])
			}
			object list {
				def apply(spacesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/spaces/${spacesId}/members").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListMembershipsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object users {
		object spaces {
			/** Returns details about a user's read state within a space, used to identify read and unread messages. For an example, see [Get details about a user's space read state](https://developers.google.com/workspace/chat/get-space-read-state). Requires [user authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user). */
			class getSpaceReadState(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SpaceReadState]) {
				val scopes = Seq("""https://www.googleapis.com/auth/chat.users.readstate""", """https://www.googleapis.com/auth/chat.users.readstate.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SpaceReadState])
			}
			object getSpaceReadState {
				def apply(usersId :PlayApi, spacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getSpaceReadState = new getSpaceReadState(ws.url(BASE_URL + s"v1/users/${usersId}/spaces/${spacesId}/spaceReadState").addQueryStringParameters("name" -> name.toString))
				given Conversion[getSpaceReadState, Future[Schema.SpaceReadState]] = (fun: getSpaceReadState) => fun.apply()
			}
			/** Updates a user's read state within a space, used to identify read and unread messages. For an example, see [Update a user's space read state](https://developers.google.com/workspace/chat/update-space-read-state). Requires [user authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user). */
			class updateSpaceReadState(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/chat.users.readstate""")
				/** Perform the request */
				def withSpaceReadState(body: Schema.SpaceReadState) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SpaceReadState])
			}
			object updateSpaceReadState {
				def apply(usersId :PlayApi, spacesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateSpaceReadState = new updateSpaceReadState(ws.url(BASE_URL + s"v1/users/${usersId}/spaces/${spacesId}/spaceReadState").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			object threads {
				/** Returns details about a user's read state within a thread, used to identify read and unread messages. For an example, see [Get details about a user's thread read state](https://developers.google.com/workspace/chat/get-thread-read-state). Requires [user authentication](https://developers.google.com/workspace/chat/authenticate-authorize-chat-user). */
				class getThreadReadState(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ThreadReadState]) {
					val scopes = Seq("""https://www.googleapis.com/auth/chat.users.readstate""", """https://www.googleapis.com/auth/chat.users.readstate.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ThreadReadState])
				}
				object getThreadReadState {
					def apply(usersId :PlayApi, spacesId :PlayApi, threadsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getThreadReadState = new getThreadReadState(ws.url(BASE_URL + s"v1/users/${usersId}/spaces/${spacesId}/threads/${threadsId}/threadReadState").addQueryStringParameters("name" -> name.toString))
					given Conversion[getThreadReadState, Future[Schema.ThreadReadState]] = (fun: getThreadReadState) => fun.apply()
				}
			}
		}
	}
}
