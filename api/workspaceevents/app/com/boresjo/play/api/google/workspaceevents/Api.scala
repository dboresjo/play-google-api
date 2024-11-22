package com.boresjo.play.api.google.workspaceevents

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
		"""https://www.googleapis.com/auth/drive.readonly""" /* See and download all your Google Drive files */,
		"""https://www.googleapis.com/auth/chat.messages.reactions.readonly""" /* View reactions to messages in Google Chat */,
		"""https://www.googleapis.com/auth/chat.spaces""" /* Create conversations and spaces and see or update metadata (including history settings and access settings) in Google Chat */,
		"""https://www.googleapis.com/auth/chat.messages""" /* See, compose, send, update, and delete messages and their associated attachments, and add, see, and delete reactions to messages. */,
		"""https://www.googleapis.com/auth/drive.metadata.readonly""" /* See information about your Google Drive files */,
		"""https://www.googleapis.com/auth/chat.spaces.readonly""" /* View chat and spaces in Google Chat */,
		"""https://www.googleapis.com/auth/chat.bot""" /* Private Service: https://www.googleapis.com/auth/chat.bot */,
		"""https://www.googleapis.com/auth/meetings.space.created""" /* Create, edit, and see information about your Google Meet conferences created by the app. */,
		"""https://www.googleapis.com/auth/drive.file""" /* See, edit, create, and delete only the specific Google Drive files you use with this app */,
		"""https://www.googleapis.com/auth/drive""" /* See, edit, create, and delete all of your Google Drive files */,
		"""https://www.googleapis.com/auth/chat.memberships.readonly""" /* View members in Google Chat conversations. */,
		"""https://www.googleapis.com/auth/chat.memberships""" /* See, add, update, and remove members from conversations and spaces in Google Chat */,
		"""https://www.googleapis.com/auth/meetings.space.readonly""" /* Read information about any of your Google Meet conferences */,
		"""https://www.googleapis.com/auth/chat.messages.readonly""" /* See messages and their associated reactions and attachments in Google Chat */,
		"""https://www.googleapis.com/auth/drive.metadata""" /* View and manage metadata of files in your Google Drive */,
		"""https://www.googleapis.com/auth/chat.messages.reactions""" /* See, add, and delete reactions to messages in Google Chat */
	)

	private val BASE_URL = "https://workspaceevents.googleapis.com/"

	object subscriptions {
		/** [Developer Preview](https://developers.google.com/workspace/preview): Reactivates a suspended Google Workspace subscription. This method resets your subscription's `State` field to `ACTIVE`. Before you use this method, you must fix the error that suspended the subscription. This method will ignore or reject any subscription that isn't currently in a suspended state. To learn how to use this method, see [Reactivate a Google Workspace subscription](https://developers.google.com/workspace/events/guides/reactivate-subscription). */
		class reactivate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/chat.memberships""", """https://www.googleapis.com/auth/chat.memberships.readonly""", """https://www.googleapis.com/auth/chat.messages""", """https://www.googleapis.com/auth/chat.messages.reactions""", """https://www.googleapis.com/auth/chat.messages.reactions.readonly""", """https://www.googleapis.com/auth/chat.messages.readonly""", """https://www.googleapis.com/auth/chat.spaces""", """https://www.googleapis.com/auth/chat.spaces.readonly""", """https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.readonly""", """https://www.googleapis.com/auth/meetings.space.created""", """https://www.googleapis.com/auth/meetings.space.readonly""")
			/** Perform the request */
			def withReactivateSubscriptionRequest(body: Schema.ReactivateSubscriptionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object reactivate {
			def apply(subscriptionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): reactivate = new reactivate(ws.url(BASE_URL + s"v1/subscriptions/${subscriptionsId}:reactivate").addQueryStringParameters("name" -> name.toString))
		}
		/** [Developer Preview](https://developers.google.com/workspace/preview): Creates a Google Workspace subscription. To learn how to use this method, see [Create a Google Workspace subscription](https://developers.google.com/workspace/events/guides/create-subscription). */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/chat.memberships""", """https://www.googleapis.com/auth/chat.memberships.readonly""", """https://www.googleapis.com/auth/chat.messages""", """https://www.googleapis.com/auth/chat.messages.reactions""", """https://www.googleapis.com/auth/chat.messages.reactions.readonly""", """https://www.googleapis.com/auth/chat.messages.readonly""", """https://www.googleapis.com/auth/chat.spaces""", """https://www.googleapis.com/auth/chat.spaces.readonly""", """https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.readonly""", """https://www.googleapis.com/auth/meetings.space.created""", """https://www.googleapis.com/auth/meetings.space.readonly""")
			/** Optional. If set to `true`, validates and previews the request, but doesn't create the subscription. */
			def withValidateOnly(validateOnly: Boolean) = new create(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
			/** Perform the request */
			def withSubscription(body: Schema.Subscription) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/subscriptions").addQueryStringParameters())
		}
		/** [Developer Preview](https://developers.google.com/workspace/preview): Deletes a Google Workspace subscription. To learn how to use this method, see [Delete a Google Workspace subscription](https://developers.google.com/workspace/events/guides/delete-subscription). */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/chat.bot""", """https://www.googleapis.com/auth/chat.memberships""", """https://www.googleapis.com/auth/chat.memberships.readonly""", """https://www.googleapis.com/auth/chat.messages""", """https://www.googleapis.com/auth/chat.messages.reactions""", """https://www.googleapis.com/auth/chat.messages.reactions.readonly""", """https://www.googleapis.com/auth/chat.messages.readonly""", """https://www.googleapis.com/auth/chat.spaces""", """https://www.googleapis.com/auth/chat.spaces.readonly""", """https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.readonly""", """https://www.googleapis.com/auth/meetings.space.created""", """https://www.googleapis.com/auth/meetings.space.readonly""")
			/** Optional. If set to `true` and the subscription isn't found, the request succeeds but doesn't delete the subscription. */
			def withAllowMissing(allowMissing: Boolean) = new delete(req.addQueryStringParameters("allowMissing" -> allowMissing.toString))
			/** Optional. If set to `true`, validates and previews the request, but doesn't delete the subscription. */
			def withValidateOnly(validateOnly: Boolean) = new delete(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
			/** Optional. Etag of the subscription. If present, it must match with the server's etag. Otherwise, request fails with the status `ABORTED`. */
			def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
		}
		object delete {
			def apply(subscriptionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/subscriptions/${subscriptionsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
		}
		/** [Developer Preview](https://developers.google.com/workspace/preview): Gets details about a Google Workspace subscription. To learn how to use this method, see [Get details about a Google Workspace subscription](https://developers.google.com/workspace/events/guides/get-subscription). */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Subscription]) {
			val scopes = Seq("""https://www.googleapis.com/auth/chat.bot""", """https://www.googleapis.com/auth/chat.memberships""", """https://www.googleapis.com/auth/chat.memberships.readonly""", """https://www.googleapis.com/auth/chat.messages""", """https://www.googleapis.com/auth/chat.messages.reactions""", """https://www.googleapis.com/auth/chat.messages.reactions.readonly""", """https://www.googleapis.com/auth/chat.messages.readonly""", """https://www.googleapis.com/auth/chat.spaces""", """https://www.googleapis.com/auth/chat.spaces.readonly""", """https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.readonly""", """https://www.googleapis.com/auth/meetings.space.created""", """https://www.googleapis.com/auth/meetings.space.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Subscription])
		}
		object get {
			def apply(subscriptionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/subscriptions/${subscriptionsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Subscription]] = (fun: get) => fun.apply()
		}
		/** [Developer Preview](https://developers.google.com/workspace/preview): Updates or renews a Google Workspace subscription. To learn how to use this method, see [Update or renew a Google Workspace subscription](https://developers.google.com/workspace/events/guides/update-subscription). */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/chat.memberships""", """https://www.googleapis.com/auth/chat.memberships.readonly""", """https://www.googleapis.com/auth/chat.messages""", """https://www.googleapis.com/auth/chat.messages.reactions""", """https://www.googleapis.com/auth/chat.messages.reactions.readonly""", """https://www.googleapis.com/auth/chat.messages.readonly""", """https://www.googleapis.com/auth/chat.spaces""", """https://www.googleapis.com/auth/chat.spaces.readonly""", """https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.readonly""", """https://www.googleapis.com/auth/meetings.space.created""", """https://www.googleapis.com/auth/meetings.space.readonly""")
			/** Optional. If set to `true`, validates and previews the request, but doesn't update the subscription. */
			def withValidateOnly(validateOnly: Boolean) = new patch(req.addQueryStringParameters("validateOnly" -> validateOnly.toString))
			/** Optional. The field to update. If omitted, updates any fields included in the request. You can update one of the following fields in a subscription: &#42; `expire_time`: The timestamp when the subscription expires. &#42; `ttl`: The time-to-live (TTL) or duration of the subscription. &#42; `event_types`: The list of event types to receive about the target resource. To fully replace the subscription (the equivalent of `PUT`), use `&#42;`. Any omitted fields are updated with empty values.<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			/** Perform the request */
			def withSubscription(body: Schema.Subscription) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
		}
		object patch {
			def apply(subscriptionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/subscriptions/${subscriptionsId}").addQueryStringParameters("name" -> name.toString))
		}
		/** [Developer Preview](https://developers.google.com/workspace/preview): Lists Google Workspace subscriptions. To learn how to use this method, see [List Google Workspace subscriptions](https://developers.google.com/workspace/events/guides/list-subscriptions). */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSubscriptionsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/chat.bot""", """https://www.googleapis.com/auth/chat.memberships""", """https://www.googleapis.com/auth/chat.memberships.readonly""", """https://www.googleapis.com/auth/chat.messages""", """https://www.googleapis.com/auth/chat.messages.reactions""", """https://www.googleapis.com/auth/chat.messages.reactions.readonly""", """https://www.googleapis.com/auth/chat.messages.readonly""", """https://www.googleapis.com/auth/chat.spaces""", """https://www.googleapis.com/auth/chat.spaces.readonly""", """https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.readonly""", """https://www.googleapis.com/auth/meetings.space.created""", """https://www.googleapis.com/auth/meetings.space.readonly""")
			/** Optional. The maximum number of subscriptions to return. The service might return fewer than this value. If unspecified or set to `0`, up to 50 subscriptions are returned. The maximum value is 100. If you specify a value more than 100, the system only returns 100 subscriptions.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A page token, received from a previous list subscriptions call. Provide this parameter to retrieve the subsequent page. When paginating, the filter value should match the call that provided the page token. Passing a different value might lead to unexpected results. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSubscriptionsResponse])
		}
		object list {
			def apply(filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/subscriptions").addQueryStringParameters("filter" -> filter.toString))
			given Conversion[list, Future[Schema.ListSubscriptionsResponse]] = (fun: list) => fun.apply()
		}
	}
	object operations {
		/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/chat.bot""", """https://www.googleapis.com/auth/chat.memberships""", """https://www.googleapis.com/auth/chat.memberships.readonly""", """https://www.googleapis.com/auth/chat.messages""", """https://www.googleapis.com/auth/chat.messages.reactions""", """https://www.googleapis.com/auth/chat.messages.reactions.readonly""", """https://www.googleapis.com/auth/chat.messages.readonly""", """https://www.googleapis.com/auth/chat.spaces""", """https://www.googleapis.com/auth/chat.spaces.readonly""", """https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.metadata""", """https://www.googleapis.com/auth/drive.metadata.readonly""", """https://www.googleapis.com/auth/drive.readonly""", """https://www.googleapis.com/auth/meetings.space.created""", """https://www.googleapis.com/auth/meetings.space.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
	}
}
