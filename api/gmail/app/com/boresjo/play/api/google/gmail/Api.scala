package com.boresjo.play.api.google.gmail

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
		"""https://mail.google.com/""" /* Read, compose, send, and permanently delete all your email from Gmail */,
		"""https://www.googleapis.com/auth/gmail.addons.current.action.compose""" /* Manage drafts and send emails when you interact with the add-on */,
		"""https://www.googleapis.com/auth/gmail.addons.current.message.action""" /* View your email messages when you interact with the add-on */,
		"""https://www.googleapis.com/auth/gmail.addons.current.message.metadata""" /* View your email message metadata when the add-on is running */,
		"""https://www.googleapis.com/auth/gmail.addons.current.message.readonly""" /* View your email messages when the add-on is running */,
		"""https://www.googleapis.com/auth/gmail.compose""" /* Manage drafts and send emails */,
		"""https://www.googleapis.com/auth/gmail.insert""" /* Add emails into your Gmail mailbox */,
		"""https://www.googleapis.com/auth/gmail.labels""" /* See and edit your email labels */,
		"""https://www.googleapis.com/auth/gmail.metadata""" /* View your email message metadata such as labels and headers, but not the email body */,
		"""https://www.googleapis.com/auth/gmail.modify""" /* Read, compose, and send emails from your Gmail account */,
		"""https://www.googleapis.com/auth/gmail.readonly""" /* View your email messages and settings */,
		"""https://www.googleapis.com/auth/gmail.send""" /* Send email on your behalf */,
		"""https://www.googleapis.com/auth/gmail.settings.basic""" /* See, edit, create, or change your email settings and filters in Gmail */,
		"""https://www.googleapis.com/auth/gmail.settings.sharing""" /* Manage your sensitive mail settings, including who can manage your mail */
	)

	private val BASE_URL = "https://gmail.googleapis.com/"

	object users {
		/** Gets the current user's Gmail profile. */
		class getProfile(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Profile]) {
			val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.compose""", """https://www.googleapis.com/auth/gmail.metadata""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Profile])
		}
		object getProfile {
			def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): getProfile = new getProfile(ws.url(BASE_URL + s"gmail/v1/users/${userId}/profile").addQueryStringParameters())
			given Conversion[getProfile, Future[Schema.Profile]] = (fun: getProfile) => fun.apply()
		}
		/** Set up or update a push notification watch on the given user mailbox. */
		class watch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.metadata""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""")
			/** Perform the request */
			def withWatchRequest(body: Schema.WatchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.WatchResponse])
		}
		object watch {
			def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): watch = new watch(ws.url(BASE_URL + s"gmail/v1/users/${userId}/watch").addQueryStringParameters())
		}
		/** Stop receiving push notifications for the given user mailbox. */
		class stop(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.metadata""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object stop {
			def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"gmail/v1/users/${userId}/stop").addQueryStringParameters())
			given Conversion[stop, Future[Unit]] = (fun: stop) => fun.apply()
		}
		object messages {
			/** Directly inserts a message into only this user's mailbox similar to `IMAP APPEND`, bypassing most scanning and classification. Does not send a message. */
			class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.insert""", """https://www.googleapis.com/auth/gmail.modify""")
				/** Perform the request */
				def withMessage(body: Schema.Message) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Message])
			}
			object insert {
				def apply(userId: String, internalDateSource: String, deleted: Boolean)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages").addQueryStringParameters("internalDateSource" -> internalDateSource.toString, "deleted" -> deleted.toString))
			}
			/** Modifies the labels on the specified message. */
			class modify(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""")
				/** Perform the request */
				def withModifyMessageRequest(body: Schema.ModifyMessageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Message])
			}
			object modify {
				def apply(userId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): modify = new modify(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages/${id}/modify").addQueryStringParameters())
			}
			/** Imports a message into only this user's mailbox, with standard email delivery scanning and classification similar to receiving via SMTP. This method doesn't perform SPF checks, so it might not work for some spam messages, such as those attempting to perform domain spoofing. This method does not send a message. */
			class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.insert""", """https://www.googleapis.com/auth/gmail.modify""")
				/** Perform the request */
				def withMessage(body: Schema.Message) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Message])
			}
			object `import` {
				def apply(userId: String, internalDateSource: String, neverMarkSpam: Boolean, processForCalendar: Boolean, deleted: Boolean)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages/import").addQueryStringParameters("internalDateSource" -> internalDateSource.toString, "neverMarkSpam" -> neverMarkSpam.toString, "processForCalendar" -> processForCalendar.toString, "deleted" -> deleted.toString))
			}
			/** Deletes many messages by message ID. Provides no guarantees that messages were not already deleted or even existed at all. */
			class batchDelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://mail.google.com/""")
				/** Perform the request */
				def withBatchDeleteMessagesRequest(body: Schema.BatchDeleteMessagesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
			}
			object batchDelete {
				def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages/batchDelete").addQueryStringParameters())
			}
			/** Immediately and permanently deletes the specified message. This operation cannot be undone. Prefer `messages.trash` instead. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://mail.google.com/""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(userId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages/${id}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			/** Gets the specified message. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Message]) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.addons.current.message.action""", """https://www.googleapis.com/auth/gmail.addons.current.message.metadata""", """https://www.googleapis.com/auth/gmail.addons.current.message.readonly""", """https://www.googleapis.com/auth/gmail.metadata""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Message])
			}
			object get {
				def apply(userId: String, id: String, format: String, metadataHeaders: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages/${id}").addQueryStringParameters("format" -> format.toString, "metadataHeaders" -> metadataHeaders.toString))
				given Conversion[get, Future[Schema.Message]] = (fun: get) => fun.apply()
			}
			/** Removes the specified message from the trash. */
			class untrash(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Message]) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Message])
			}
			object untrash {
				def apply(userId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): untrash = new untrash(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages/${id}/untrash").addQueryStringParameters())
				given Conversion[untrash, Future[Schema.Message]] = (fun: untrash) => fun.apply()
			}
			/** Modifies the labels on the specified messages. */
			class batchModify(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""")
				/** Perform the request */
				def withBatchModifyMessagesRequest(body: Schema.BatchModifyMessagesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
			}
			object batchModify {
				def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): batchModify = new batchModify(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages/batchModify").addQueryStringParameters())
			}
			/** Lists the messages in the user's mailbox. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListMessagesResponse]) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.metadata""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListMessagesResponse])
			}
			object list {
				def apply(userId: String, maxResults: Int, pageToken: String, q: String, labelIds: String, includeSpamTrash: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "q" -> q.toString, "labelIds" -> labelIds.toString, "includeSpamTrash" -> includeSpamTrash.toString))
				given Conversion[list, Future[Schema.ListMessagesResponse]] = (fun: list) => fun.apply()
			}
			/** Moves the specified message to the trash. */
			class trash(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Message]) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Message])
			}
			object trash {
				def apply(userId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): trash = new trash(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages/${id}/trash").addQueryStringParameters())
				given Conversion[trash, Future[Schema.Message]] = (fun: trash) => fun.apply()
			}
			/** Sends the specified message to the recipients in the `To`, `Cc`, and `Bcc` headers. For example usage, see [Sending email](https://developers.google.com/gmail/api/guides/sending). */
			class send(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.addons.current.action.compose""", """https://www.googleapis.com/auth/gmail.compose""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.send""")
				/** Perform the request */
				def withMessage(body: Schema.Message) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Message])
			}
			object send {
				def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): send = new send(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages/send").addQueryStringParameters())
			}
			object attachments {
				/** Gets the specified message attachment. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.MessagePartBody]) {
					val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.addons.current.message.action""", """https://www.googleapis.com/auth/gmail.addons.current.message.readonly""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.MessagePartBody])
				}
				object get {
					def apply(userId: String, messageId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages/${messageId}/attachments/${id}").addQueryStringParameters())
					given Conversion[get, Future[Schema.MessagePartBody]] = (fun: get) => fun.apply()
				}
			}
		}
		object history {
			/** Lists the history of all changes to the given mailbox. History results are returned in chronological order (increasing `historyId`). */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListHistoryResponse]) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.metadata""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListHistoryResponse])
			}
			object list {
				def apply(userId: String, maxResults: Int, pageToken: String, startHistoryId: String, labelId: String, historyTypes: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/history").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "startHistoryId" -> startHistoryId.toString, "labelId" -> labelId.toString, "historyTypes" -> historyTypes.toString))
				given Conversion[list, Future[Schema.ListHistoryResponse]] = (fun: list) => fun.apply()
			}
		}
		object labels {
			/** Creates a new label. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.labels""", """https://www.googleapis.com/auth/gmail.modify""")
				/** Perform the request */
				def withLabel(body: Schema.Label) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Label])
			}
			object create {
				def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"gmail/v1/users/${userId}/labels").addQueryStringParameters())
			}
			/** Immediately and permanently deletes the specified label and removes it from any messages and threads that it is applied to. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.labels""", """https://www.googleapis.com/auth/gmail.modify""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(userId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/labels/${id}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			/** Gets the specified label. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Label]) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.labels""", """https://www.googleapis.com/auth/gmail.metadata""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Label])
			}
			object get {
				def apply(userId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/labels/${id}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Label]] = (fun: get) => fun.apply()
			}
			/** Updates the specified label. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.labels""", """https://www.googleapis.com/auth/gmail.modify""")
				/** Perform the request */
				def withLabel(body: Schema.Label) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Label])
			}
			object update {
				def apply(userId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"gmail/v1/users/${userId}/labels/${id}").addQueryStringParameters())
			}
			/** Patch the specified label. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.labels""", """https://www.googleapis.com/auth/gmail.modify""")
				/** Perform the request */
				def withLabel(body: Schema.Label) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Label])
			}
			object patch {
				def apply(userId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"gmail/v1/users/${userId}/labels/${id}").addQueryStringParameters())
			}
			/** Lists all labels in the user's mailbox. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLabelsResponse]) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.labels""", """https://www.googleapis.com/auth/gmail.metadata""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLabelsResponse])
			}
			object list {
				def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/labels").addQueryStringParameters())
				given Conversion[list, Future[Schema.ListLabelsResponse]] = (fun: list) => fun.apply()
			}
		}
		object drafts {
			/** Creates a new draft with the `DRAFT` label. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.addons.current.action.compose""", """https://www.googleapis.com/auth/gmail.compose""", """https://www.googleapis.com/auth/gmail.modify""")
				/** Perform the request */
				def withDraft(body: Schema.Draft) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Draft])
			}
			object create {
				def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"gmail/v1/users/${userId}/drafts").addQueryStringParameters())
			}
			/** Immediately and permanently deletes the specified draft. Does not simply trash it. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.addons.current.action.compose""", """https://www.googleapis.com/auth/gmail.compose""", """https://www.googleapis.com/auth/gmail.modify""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(userId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/drafts/${id}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			/** Gets the specified draft. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Draft]) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.compose""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Draft])
			}
			object get {
				def apply(userId: String, id: String, format: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/drafts/${id}").addQueryStringParameters("format" -> format.toString))
				given Conversion[get, Future[Schema.Draft]] = (fun: get) => fun.apply()
			}
			/** Replaces a draft's content. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.addons.current.action.compose""", """https://www.googleapis.com/auth/gmail.compose""", """https://www.googleapis.com/auth/gmail.modify""")
				/** Perform the request */
				def withDraft(body: Schema.Draft) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Draft])
			}
			object update {
				def apply(userId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"gmail/v1/users/${userId}/drafts/${id}").addQueryStringParameters())
			}
			/** Lists the drafts in the user's mailbox. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDraftsResponse]) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.compose""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDraftsResponse])
			}
			object list {
				def apply(userId: String, maxResults: Int, pageToken: String, q: String, includeSpamTrash: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/drafts").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "q" -> q.toString, "includeSpamTrash" -> includeSpamTrash.toString))
				given Conversion[list, Future[Schema.ListDraftsResponse]] = (fun: list) => fun.apply()
			}
			/** Sends the specified, existing draft to the recipients in the `To`, `Cc`, and `Bcc` headers. */
			class send(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.addons.current.action.compose""", """https://www.googleapis.com/auth/gmail.compose""", """https://www.googleapis.com/auth/gmail.modify""")
				/** Perform the request */
				def withDraft(body: Schema.Draft) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Message])
			}
			object send {
				def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): send = new send(ws.url(BASE_URL + s"gmail/v1/users/${userId}/drafts/send").addQueryStringParameters())
			}
		}
		object threads {
			/** Modifies the labels applied to the thread. This applies to all messages in the thread. */
			class modify(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""")
				/** Perform the request */
				def withModifyThreadRequest(body: Schema.ModifyThreadRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Thread])
			}
			object modify {
				def apply(userId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): modify = new modify(ws.url(BASE_URL + s"gmail/v1/users/${userId}/threads/${id}/modify").addQueryStringParameters())
			}
			/** Immediately and permanently deletes the specified thread. Any messages that belong to the thread are also deleted. This operation cannot be undone. Prefer `threads.trash` instead. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
				val scopes = Seq("""https://mail.google.com/""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(userId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/threads/${id}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			/** Removes the specified thread from the trash. Any messages that belong to the thread are also removed from the trash. */
			class untrash(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Thread]) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Thread])
			}
			object untrash {
				def apply(userId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): untrash = new untrash(ws.url(BASE_URL + s"gmail/v1/users/${userId}/threads/${id}/untrash").addQueryStringParameters())
				given Conversion[untrash, Future[Schema.Thread]] = (fun: untrash) => fun.apply()
			}
			/** Gets the specified thread. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Thread]) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.addons.current.message.action""", """https://www.googleapis.com/auth/gmail.addons.current.message.metadata""", """https://www.googleapis.com/auth/gmail.addons.current.message.readonly""", """https://www.googleapis.com/auth/gmail.metadata""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Thread])
			}
			object get {
				def apply(userId: String, id: String, format: String, metadataHeaders: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/threads/${id}").addQueryStringParameters("format" -> format.toString, "metadataHeaders" -> metadataHeaders.toString))
				given Conversion[get, Future[Schema.Thread]] = (fun: get) => fun.apply()
			}
			/** Lists the threads in the user's mailbox. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListThreadsResponse]) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.metadata""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListThreadsResponse])
			}
			object list {
				def apply(userId: String, maxResults: Int, pageToken: String, q: String, labelIds: String, includeSpamTrash: Boolean)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/threads").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "q" -> q.toString, "labelIds" -> labelIds.toString, "includeSpamTrash" -> includeSpamTrash.toString))
				given Conversion[list, Future[Schema.ListThreadsResponse]] = (fun: list) => fun.apply()
			}
			/** Moves the specified thread to the trash. Any messages that belong to the thread are also moved to the trash. */
			class trash(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Thread]) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Thread])
			}
			object trash {
				def apply(userId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): trash = new trash(ws.url(BASE_URL + s"gmail/v1/users/${userId}/threads/${id}/trash").addQueryStringParameters())
				given Conversion[trash, Future[Schema.Thread]] = (fun: trash) => fun.apply()
			}
		}
		object settings {
			/** Updates IMAP settings. */
			class updateImap(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.basic""")
				/** Perform the request */
				def withImapSettings(body: Schema.ImapSettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ImapSettings])
			}
			object updateImap {
				def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): updateImap = new updateImap(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/imap").addQueryStringParameters())
			}
			/** Gets IMAP settings. */
			class getImap(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ImapSettings]) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""", """https://www.googleapis.com/auth/gmail.settings.basic""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ImapSettings])
			}
			object getImap {
				def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): getImap = new getImap(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/imap").addQueryStringParameters())
				given Conversion[getImap, Future[Schema.ImapSettings]] = (fun: getImap) => fun.apply()
			}
			/** Gets vacation responder settings. */
			class getVacation(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.VacationSettings]) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""", """https://www.googleapis.com/auth/gmail.settings.basic""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.VacationSettings])
			}
			object getVacation {
				def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): getVacation = new getVacation(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/vacation").addQueryStringParameters())
				given Conversion[getVacation, Future[Schema.VacationSettings]] = (fun: getVacation) => fun.apply()
			}
			/** Gets language settings. */
			class getLanguage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LanguageSettings]) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""", """https://www.googleapis.com/auth/gmail.settings.basic""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LanguageSettings])
			}
			object getLanguage {
				def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): getLanguage = new getLanguage(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/language").addQueryStringParameters())
				given Conversion[getLanguage, Future[Schema.LanguageSettings]] = (fun: getLanguage) => fun.apply()
			}
			/** Gets POP settings. */
			class getPop(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PopSettings]) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""", """https://www.googleapis.com/auth/gmail.settings.basic""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PopSettings])
			}
			object getPop {
				def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): getPop = new getPop(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/pop").addQueryStringParameters())
				given Conversion[getPop, Future[Schema.PopSettings]] = (fun: getPop) => fun.apply()
			}
			/** Updates vacation responder settings. */
			class updateVacation(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.basic""")
				/** Perform the request */
				def withVacationSettings(body: Schema.VacationSettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.VacationSettings])
			}
			object updateVacation {
				def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): updateVacation = new updateVacation(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/vacation").addQueryStringParameters())
			}
			/** Updates language settings. If successful, the return object contains the `displayLanguage` that was saved for the user, which may differ from the value passed into the request. This is because the requested `displayLanguage` may not be directly supported by Gmail but have a close variant that is, and so the variant may be chosen and saved instead. */
			class updateLanguage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.basic""")
				/** Perform the request */
				def withLanguageSettings(body: Schema.LanguageSettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LanguageSettings])
			}
			object updateLanguage {
				def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): updateLanguage = new updateLanguage(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/language").addQueryStringParameters())
			}
			/** Gets the auto-forwarding setting for the specified account. */
			class getAutoForwarding(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AutoForwarding]) {
				val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""", """https://www.googleapis.com/auth/gmail.settings.basic""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AutoForwarding])
			}
			object getAutoForwarding {
				def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): getAutoForwarding = new getAutoForwarding(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/autoForwarding").addQueryStringParameters())
				given Conversion[getAutoForwarding, Future[Schema.AutoForwarding]] = (fun: getAutoForwarding) => fun.apply()
			}
			/** Updates POP settings. */
			class updatePop(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.basic""")
				/** Perform the request */
				def withPopSettings(body: Schema.PopSettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.PopSettings])
			}
			object updatePop {
				def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): updatePop = new updatePop(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/pop").addQueryStringParameters())
			}
			/** Updates the auto-forwarding setting for the specified account. A verified forwarding address must be specified when auto-forwarding is enabled. This method is only available to service account clients that have been delegated domain-wide authority. */
			class updateAutoForwarding(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.sharing""")
				/** Perform the request */
				def withAutoForwarding(body: Schema.AutoForwarding) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.AutoForwarding])
			}
			object updateAutoForwarding {
				def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): updateAutoForwarding = new updateAutoForwarding(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/autoForwarding").addQueryStringParameters())
			}
			object sendAs {
				/** Sends a verification email to the specified send-as alias address. The verification status must be `pending`. This method is only available to service account clients that have been delegated domain-wide authority. */
				class verify(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
					val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.sharing""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
				}
				object verify {
					def apply(userId: String, sendAsEmail: String)(using signer: RequestSigner, ec: ExecutionContext): verify = new verify(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs/${sendAsEmail}/verify").addQueryStringParameters())
					given Conversion[verify, Future[Unit]] = (fun: verify) => fun.apply()
				}
				/** Creates a custom "from" send-as alias. If an SMTP MSA is specified, Gmail will attempt to connect to the SMTP service to validate the configuration before creating the alias. If ownership verification is required for the alias, a message will be sent to the email address and the resource's verification status will be set to `pending`; otherwise, the resource will be created with verification status set to `accepted`. If a signature is provided, Gmail will sanitize the HTML before saving it with the alias. This method is only available to service account clients that have been delegated domain-wide authority. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.sharing""")
					/** Perform the request */
					def withSendAs(body: Schema.SendAs) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SendAs])
				}
				object create {
					def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs").addQueryStringParameters())
				}
				/** Deletes the specified send-as alias. Revokes any verification that may have been required for using it. This method is only available to service account clients that have been delegated domain-wide authority. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
					val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.sharing""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
				}
				object delete {
					def apply(userId: String, sendAsEmail: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs/${sendAsEmail}").addQueryStringParameters())
					given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
				}
				/** Gets the specified send-as alias. Fails with an HTTP 404 error if the specified address is not a member of the collection. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SendAs]) {
					val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""", """https://www.googleapis.com/auth/gmail.settings.basic""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SendAs])
				}
				object get {
					def apply(userId: String, sendAsEmail: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs/${sendAsEmail}").addQueryStringParameters())
					given Conversion[get, Future[Schema.SendAs]] = (fun: get) => fun.apply()
				}
				/** Updates a send-as alias. If a signature is provided, Gmail will sanitize the HTML before saving it with the alias. Addresses other than the primary address for the account can only be updated by service account clients that have been delegated domain-wide authority. */
				class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.basic""", """https://www.googleapis.com/auth/gmail.settings.sharing""")
					/** Perform the request */
					def withSendAs(body: Schema.SendAs) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.SendAs])
				}
				object update {
					def apply(userId: String, sendAsEmail: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs/${sendAsEmail}").addQueryStringParameters())
				}
				/** Patch the specified send-as alias. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.basic""", """https://www.googleapis.com/auth/gmail.settings.sharing""")
					/** Perform the request */
					def withSendAs(body: Schema.SendAs) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SendAs])
				}
				object patch {
					def apply(userId: String, sendAsEmail: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs/${sendAsEmail}").addQueryStringParameters())
				}
				/** Lists the send-as aliases for the specified account. The result includes the primary send-as address associated with the account as well as any custom "from" aliases. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSendAsResponse]) {
					val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""", """https://www.googleapis.com/auth/gmail.settings.basic""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSendAsResponse])
				}
				object list {
					def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs").addQueryStringParameters())
					given Conversion[list, Future[Schema.ListSendAsResponse]] = (fun: list) => fun.apply()
				}
				object smimeInfo {
					/** Insert (upload) the given S/MIME config for the specified send-as alias. Note that pkcs12 format is required for the key. */
					class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.basic""", """https://www.googleapis.com/auth/gmail.settings.sharing""")
						/** Perform the request */
						def withSmimeInfo(body: Schema.SmimeInfo) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SmimeInfo])
					}
					object insert {
						def apply(userId: String, sendAsEmail: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs/${sendAsEmail}/smimeInfo").addQueryStringParameters())
					}
					/** Deletes the specified S/MIME config for the specified send-as alias. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
						val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.basic""", """https://www.googleapis.com/auth/gmail.settings.sharing""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(userId: String, sendAsEmail: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs/${sendAsEmail}/smimeInfo/${id}").addQueryStringParameters())
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					/** Gets the specified S/MIME config for the specified send-as alias. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SmimeInfo]) {
						val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""", """https://www.googleapis.com/auth/gmail.settings.basic""", """https://www.googleapis.com/auth/gmail.settings.sharing""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SmimeInfo])
					}
					object get {
						def apply(userId: String, sendAsEmail: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs/${sendAsEmail}/smimeInfo/${id}").addQueryStringParameters())
						given Conversion[get, Future[Schema.SmimeInfo]] = (fun: get) => fun.apply()
					}
					/** Sets the default S/MIME config for the specified send-as alias. */
					class setDefault(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
						val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.basic""", """https://www.googleapis.com/auth/gmail.settings.sharing""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
					}
					object setDefault {
						def apply(userId: String, sendAsEmail: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): setDefault = new setDefault(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs/${sendAsEmail}/smimeInfo/${id}/setDefault").addQueryStringParameters())
						given Conversion[setDefault, Future[Unit]] = (fun: setDefault) => fun.apply()
					}
					/** Lists S/MIME configs for the specified send-as alias. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSmimeInfoResponse]) {
						val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""", """https://www.googleapis.com/auth/gmail.settings.basic""", """https://www.googleapis.com/auth/gmail.settings.sharing""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSmimeInfoResponse])
					}
					object list {
						def apply(userId: String, sendAsEmail: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs/${sendAsEmail}/smimeInfo").addQueryStringParameters())
						given Conversion[list, Future[Schema.ListSmimeInfoResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object cse {
				object identities {
					/** Creates and configures a client-side encryption identity that's authorized to send mail from the user account. Google publishes the S/MIME certificate to a shared domain-wide directory so that people within a Google Workspace organization can encrypt and send mail to the identity. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.basic""", """https://www.googleapis.com/auth/gmail.settings.sharing""")
						/** Perform the request */
						def withCseIdentity(body: Schema.CseIdentity) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CseIdentity])
					}
					object create {
						def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/identities").addQueryStringParameters())
					}
					/** Deletes a client-side encryption identity. The authenticated user can no longer use the identity to send encrypted messages. You cannot restore the identity after you delete it. Instead, use the CreateCseIdentity method to create another identity with the same configuration. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
						val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.basic""", """https://www.googleapis.com/auth/gmail.settings.sharing""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(userId: String, cseEmailAddress: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/identities/${cseEmailAddress}").addQueryStringParameters())
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					/** Retrieves a client-side encryption identity configuration. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CseIdentity]) {
						val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""", """https://www.googleapis.com/auth/gmail.settings.basic""", """https://www.googleapis.com/auth/gmail.settings.sharing""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CseIdentity])
					}
					object get {
						def apply(userId: String, cseEmailAddress: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/identities/${cseEmailAddress}").addQueryStringParameters())
						given Conversion[get, Future[Schema.CseIdentity]] = (fun: get) => fun.apply()
					}
					/** Associates a different key pair with an existing client-side encryption identity. The updated key pair must validate against Google's [S/MIME certificate profiles](https://support.google.com/a/answer/7300887). */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.basic""", """https://www.googleapis.com/auth/gmail.settings.sharing""")
						/** Perform the request */
						def withCseIdentity(body: Schema.CseIdentity) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CseIdentity])
					}
					object patch {
						def apply(userId: String, emailAddress: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/identities/${emailAddress}").addQueryStringParameters())
					}
					/** Lists the client-side encrypted identities for an authenticated user. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCseIdentitiesResponse]) {
						val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""", """https://www.googleapis.com/auth/gmail.settings.basic""", """https://www.googleapis.com/auth/gmail.settings.sharing""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCseIdentitiesResponse])
					}
					object list {
						def apply(userId: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/identities").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.ListCseIdentitiesResponse]] = (fun: list) => fun.apply()
					}
				}
				object keypairs {
					/** Turns on a client-side encryption key pair that was turned off. The key pair becomes active again for any associated client-side encryption identities. */
					class enable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.basic""", """https://www.googleapis.com/auth/gmail.settings.sharing""")
						/** Perform the request */
						def withEnableCseKeyPairRequest(body: Schema.EnableCseKeyPairRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CseKeyPair])
					}
					object enable {
						def apply(userId: String, keyPairId: String)(using signer: RequestSigner, ec: ExecutionContext): enable = new enable(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/keypairs/${keyPairId}:enable").addQueryStringParameters())
					}
					/** Deletes a client-side encryption key pair permanently and immediately. You can only permanently delete key pairs that have been turned off for more than 30 days. To turn off a key pair, use the DisableCseKeyPair method. Gmail can't restore or decrypt any messages that were encrypted by an obliterated key. Authenticated users and Google Workspace administrators lose access to reading the encrypted messages. */
					class obliterate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.basic""", """https://www.googleapis.com/auth/gmail.settings.sharing""")
						/** Perform the request */
						def withObliterateCseKeyPairRequest(body: Schema.ObliterateCseKeyPairRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
					}
					object obliterate {
						def apply(userId: String, keyPairId: String)(using signer: RequestSigner, ec: ExecutionContext): obliterate = new obliterate(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/keypairs/${keyPairId}:obliterate").addQueryStringParameters())
					}
					/** Creates and uploads a client-side encryption S/MIME public key certificate chain and private key metadata for the authenticated user. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.basic""", """https://www.googleapis.com/auth/gmail.settings.sharing""")
						/** Perform the request */
						def withCseKeyPair(body: Schema.CseKeyPair) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CseKeyPair])
					}
					object create {
						def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/keypairs").addQueryStringParameters())
					}
					/** Turns off a client-side encryption key pair. The authenticated user can no longer use the key pair to decrypt incoming CSE message texts or sign outgoing CSE mail. To regain access, use the EnableCseKeyPair to turn on the key pair. After 30 days, you can permanently delete the key pair by using the ObliterateCseKeyPair method. */
					class disable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.basic""", """https://www.googleapis.com/auth/gmail.settings.sharing""")
						/** Perform the request */
						def withDisableCseKeyPairRequest(body: Schema.DisableCseKeyPairRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CseKeyPair])
					}
					object disable {
						def apply(userId: String, keyPairId: String)(using signer: RequestSigner, ec: ExecutionContext): disable = new disable(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/keypairs/${keyPairId}:disable").addQueryStringParameters())
					}
					/** Retrieves an existing client-side encryption key pair. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CseKeyPair]) {
						val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""", """https://www.googleapis.com/auth/gmail.settings.basic""", """https://www.googleapis.com/auth/gmail.settings.sharing""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CseKeyPair])
					}
					object get {
						def apply(userId: String, keyPairId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/keypairs/${keyPairId}").addQueryStringParameters())
						given Conversion[get, Future[Schema.CseKeyPair]] = (fun: get) => fun.apply()
					}
					/** Lists client-side encryption key pairs for an authenticated user. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCseKeyPairsResponse]) {
						val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""", """https://www.googleapis.com/auth/gmail.settings.basic""", """https://www.googleapis.com/auth/gmail.settings.sharing""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCseKeyPairsResponse])
					}
					object list {
						def apply(userId: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/keypairs").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.ListCseKeyPairsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object forwardingAddresses {
				/** Lists the forwarding addresses for the specified account. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListForwardingAddressesResponse]) {
					val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""", """https://www.googleapis.com/auth/gmail.settings.basic""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListForwardingAddressesResponse])
				}
				object list {
					def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/forwardingAddresses").addQueryStringParameters())
					given Conversion[list, Future[Schema.ListForwardingAddressesResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the specified forwarding address. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ForwardingAddress]) {
					val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""", """https://www.googleapis.com/auth/gmail.settings.basic""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ForwardingAddress])
				}
				object get {
					def apply(userId: String, forwardingEmail: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/forwardingAddresses/${forwardingEmail}").addQueryStringParameters())
					given Conversion[get, Future[Schema.ForwardingAddress]] = (fun: get) => fun.apply()
				}
				/** Creates a forwarding address. If ownership verification is required, a message will be sent to the recipient and the resource's verification status will be set to `pending`; otherwise, the resource will be created with verification status set to `accepted`. This method is only available to service account clients that have been delegated domain-wide authority. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.sharing""")
					/** Perform the request */
					def withForwardingAddress(body: Schema.ForwardingAddress) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ForwardingAddress])
				}
				object create {
					def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/forwardingAddresses").addQueryStringParameters())
				}
				/** Deletes the specified forwarding address and revokes any verification that may have been required. This method is only available to service account clients that have been delegated domain-wide authority. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
					val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.sharing""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
				}
				object delete {
					def apply(userId: String, forwardingEmail: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/forwardingAddresses/${forwardingEmail}").addQueryStringParameters())
					given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
				}
			}
			object filters {
				/** Lists the message filters of a Gmail user. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFiltersResponse]) {
					val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""", """https://www.googleapis.com/auth/gmail.settings.basic""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFiltersResponse])
				}
				object list {
					def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/filters").addQueryStringParameters())
					given Conversion[list, Future[Schema.ListFiltersResponse]] = (fun: list) => fun.apply()
				}
				/** Gets a filter. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Filter]) {
					val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""", """https://www.googleapis.com/auth/gmail.settings.basic""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Filter])
				}
				object get {
					def apply(userId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/filters/${id}").addQueryStringParameters())
					given Conversion[get, Future[Schema.Filter]] = (fun: get) => fun.apply()
				}
				/** Creates a filter. Note: you can only create a maximum of 1,000 filters. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.basic""")
					/** Perform the request */
					def withFilter(body: Schema.Filter) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Filter])
				}
				object create {
					def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/filters").addQueryStringParameters())
				}
				/** Immediately and permanently deletes the specified filter. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
					val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.basic""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
				}
				object delete {
					def apply(userId: String, id: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/filters/${id}").addQueryStringParameters())
					given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
				}
			}
			object delegates {
				/** Lists the delegates for the specified account. This method is only available to service account clients that have been delegated domain-wide authority. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDelegatesResponse]) {
					val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""", """https://www.googleapis.com/auth/gmail.settings.basic""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDelegatesResponse])
				}
				object list {
					def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/delegates").addQueryStringParameters())
					given Conversion[list, Future[Schema.ListDelegatesResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the specified delegate. Note that a delegate user must be referred to by their primary email address, and not an email alias. This method is only available to service account clients that have been delegated domain-wide authority. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Delegate]) {
					val scopes = Seq("""https://mail.google.com/""", """https://www.googleapis.com/auth/gmail.modify""", """https://www.googleapis.com/auth/gmail.readonly""", """https://www.googleapis.com/auth/gmail.settings.basic""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Delegate])
				}
				object get {
					def apply(userId: String, delegateEmail: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/delegates/${delegateEmail}").addQueryStringParameters())
					given Conversion[get, Future[Schema.Delegate]] = (fun: get) => fun.apply()
				}
				/** Adds a delegate with its verification status set directly to `accepted`, without sending any verification email. The delegate user must be a member of the same Google Workspace organization as the delegator user. Gmail imposes limitations on the number of delegates and delegators each user in a Google Workspace organization can have. These limits depend on your organization, but in general each user can have up to 25 delegates and up to 10 delegators. Note that a delegate user must be referred to by their primary email address, and not an email alias. Also note that when a new delegate is created, there may be up to a one minute delay before the new delegate is available for use. This method is only available to service account clients that have been delegated domain-wide authority. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.sharing""")
					/** Perform the request */
					def withDelegate(body: Schema.Delegate) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Delegate])
				}
				object create {
					def apply(userId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/delegates").addQueryStringParameters())
				}
				/** Removes the specified delegate (which can be of any verification status), and revokes any verification that may have been required for using it. Note that a delegate user must be referred to by their primary email address, and not an email alias. This method is only available to service account clients that have been delegated domain-wide authority. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
					val scopes = Seq("""https://www.googleapis.com/auth/gmail.settings.sharing""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
				}
				object delete {
					def apply(userId: String, delegateEmail: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/delegates/${delegateEmail}").addQueryStringParameters())
					given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
				}
			}
		}
	}
}
