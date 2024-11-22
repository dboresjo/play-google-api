package com.boresjo.play.api.google.gmail

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://gmail.googleapis.com/"

	object users {
		class getProfile(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Profile]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Profile])
		}
		object getProfile {
			def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): getProfile = new getProfile(ws.url(BASE_URL + s"gmail/v1/users/${userId}/profile").addQueryStringParameters())
			given Conversion[getProfile, Future[Schema.Profile]] = (fun: getProfile) => fun.apply()
		}
		class watch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withWatchRequest(body: Schema.WatchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.WatchResponse])
		}
		object watch {
			def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): watch = new watch(ws.url(BASE_URL + s"gmail/v1/users/${userId}/watch").addQueryStringParameters())
		}
		class stop(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_ => ())
		}
		object stop {
			def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"gmail/v1/users/${userId}/stop").addQueryStringParameters())
			given Conversion[stop, Future[Unit]] = (fun: stop) => fun.apply()
		}
		object messages {
			class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withMessage(body: Schema.Message) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Message])
			}
			object insert {
				def apply(userId: String, internalDateSource: String, deleted: Boolean)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages").addQueryStringParameters("internalDateSource" -> internalDateSource.toString, "deleted" -> deleted.toString))
			}
			class modify(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withModifyMessageRequest(body: Schema.ModifyMessageRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Message])
			}
			object modify {
				def apply(userId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): modify = new modify(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages/${id}/modify").addQueryStringParameters())
			}
			class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withMessage(body: Schema.Message) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Message])
			}
			object `import` {
				def apply(userId: String, internalDateSource: String, neverMarkSpam: Boolean, processForCalendar: Boolean, deleted: Boolean)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages/import").addQueryStringParameters("internalDateSource" -> internalDateSource.toString, "neverMarkSpam" -> neverMarkSpam.toString, "processForCalendar" -> processForCalendar.toString, "deleted" -> deleted.toString))
			}
			class batchDelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBatchDeleteMessagesRequest(body: Schema.BatchDeleteMessagesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
			}
			object batchDelete {
				def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages/batchDelete").addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(userId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages/${id}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Message]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Message])
			}
			object get {
				def apply(userId: String, id: String, format: String, metadataHeaders: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages/${id}").addQueryStringParameters("format" -> format.toString, "metadataHeaders" -> metadataHeaders.toString))
				given Conversion[get, Future[Schema.Message]] = (fun: get) => fun.apply()
			}
			class untrash(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Message]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Message])
			}
			object untrash {
				def apply(userId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): untrash = new untrash(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages/${id}/untrash").addQueryStringParameters())
				given Conversion[untrash, Future[Schema.Message]] = (fun: untrash) => fun.apply()
			}
			class batchModify(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBatchModifyMessagesRequest(body: Schema.BatchModifyMessagesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
			}
			object batchModify {
				def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): batchModify = new batchModify(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages/batchModify").addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListMessagesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListMessagesResponse])
			}
			object list {
				def apply(userId: String, maxResults: Int, pageToken: String, q: String, labelIds: String, includeSpamTrash: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "q" -> q.toString, "labelIds" -> labelIds.toString, "includeSpamTrash" -> includeSpamTrash.toString))
				given Conversion[list, Future[Schema.ListMessagesResponse]] = (fun: list) => fun.apply()
			}
			class trash(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Message]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Message])
			}
			object trash {
				def apply(userId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): trash = new trash(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages/${id}/trash").addQueryStringParameters())
				given Conversion[trash, Future[Schema.Message]] = (fun: trash) => fun.apply()
			}
			class send(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withMessage(body: Schema.Message) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Message])
			}
			object send {
				def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): send = new send(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages/send").addQueryStringParameters())
			}
			object attachments {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.MessagePartBody]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.MessagePartBody])
				}
				object get {
					def apply(userId: String, messageId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/messages/${messageId}/attachments/${id}").addQueryStringParameters())
					given Conversion[get, Future[Schema.MessagePartBody]] = (fun: get) => fun.apply()
				}
			}
		}
		object history {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListHistoryResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListHistoryResponse])
			}
			object list {
				def apply(userId: String, maxResults: Int, pageToken: String, startHistoryId: String, labelId: String, historyTypes: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/history").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "startHistoryId" -> startHistoryId.toString, "labelId" -> labelId.toString, "historyTypes" -> historyTypes.toString))
				given Conversion[list, Future[Schema.ListHistoryResponse]] = (fun: list) => fun.apply()
			}
		}
		object labels {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLabel(body: Schema.Label) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Label])
			}
			object create {
				def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"gmail/v1/users/${userId}/labels").addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(userId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/labels/${id}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Label]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Label])
			}
			object get {
				def apply(userId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/labels/${id}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Label]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLabel(body: Schema.Label) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Label])
			}
			object update {
				def apply(userId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"gmail/v1/users/${userId}/labels/${id}").addQueryStringParameters())
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLabel(body: Schema.Label) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Label])
			}
			object patch {
				def apply(userId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"gmail/v1/users/${userId}/labels/${id}").addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLabelsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListLabelsResponse])
			}
			object list {
				def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/labels").addQueryStringParameters())
				given Conversion[list, Future[Schema.ListLabelsResponse]] = (fun: list) => fun.apply()
			}
		}
		object drafts {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withDraft(body: Schema.Draft) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Draft])
			}
			object create {
				def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"gmail/v1/users/${userId}/drafts").addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(userId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/drafts/${id}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Draft]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Draft])
			}
			object get {
				def apply(userId: String, id: String, format: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/drafts/${id}").addQueryStringParameters("format" -> format.toString))
				given Conversion[get, Future[Schema.Draft]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withDraft(body: Schema.Draft) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Draft])
			}
			object update {
				def apply(userId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"gmail/v1/users/${userId}/drafts/${id}").addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDraftsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListDraftsResponse])
			}
			object list {
				def apply(userId: String, maxResults: Int, pageToken: String, q: String, includeSpamTrash: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/drafts").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "q" -> q.toString, "includeSpamTrash" -> includeSpamTrash.toString))
				given Conversion[list, Future[Schema.ListDraftsResponse]] = (fun: list) => fun.apply()
			}
			class send(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withDraft(body: Schema.Draft) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Message])
			}
			object send {
				def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): send = new send(ws.url(BASE_URL + s"gmail/v1/users/${userId}/drafts/send").addQueryStringParameters())
			}
		}
		object threads {
			class modify(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withModifyThreadRequest(body: Schema.ModifyThreadRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Thread])
			}
			object modify {
				def apply(userId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): modify = new modify(ws.url(BASE_URL + s"gmail/v1/users/${userId}/threads/${id}/modify").addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
			}
			object delete {
				def apply(userId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/threads/${id}").addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
			class untrash(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Thread]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Thread])
			}
			object untrash {
				def apply(userId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): untrash = new untrash(ws.url(BASE_URL + s"gmail/v1/users/${userId}/threads/${id}/untrash").addQueryStringParameters())
				given Conversion[untrash, Future[Schema.Thread]] = (fun: untrash) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Thread]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Thread])
			}
			object get {
				def apply(userId: String, id: String, format: String, metadataHeaders: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/threads/${id}").addQueryStringParameters("format" -> format.toString, "metadataHeaders" -> metadataHeaders.toString))
				given Conversion[get, Future[Schema.Thread]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListThreadsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListThreadsResponse])
			}
			object list {
				def apply(userId: String, maxResults: Int, pageToken: String, q: String, labelIds: String, includeSpamTrash: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/threads").addQueryStringParameters("maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString, "q" -> q.toString, "labelIds" -> labelIds.toString, "includeSpamTrash" -> includeSpamTrash.toString))
				given Conversion[list, Future[Schema.ListThreadsResponse]] = (fun: list) => fun.apply()
			}
			class trash(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Thread]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Thread])
			}
			object trash {
				def apply(userId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): trash = new trash(ws.url(BASE_URL + s"gmail/v1/users/${userId}/threads/${id}/trash").addQueryStringParameters())
				given Conversion[trash, Future[Schema.Thread]] = (fun: trash) => fun.apply()
			}
		}
		object settings {
			class updateImap(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withImapSettings(body: Schema.ImapSettings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ImapSettings])
			}
			object updateImap {
				def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): updateImap = new updateImap(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/imap").addQueryStringParameters())
			}
			class getImap(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ImapSettings]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ImapSettings])
			}
			object getImap {
				def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): getImap = new getImap(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/imap").addQueryStringParameters())
				given Conversion[getImap, Future[Schema.ImapSettings]] = (fun: getImap) => fun.apply()
			}
			class getVacation(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.VacationSettings]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.VacationSettings])
			}
			object getVacation {
				def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): getVacation = new getVacation(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/vacation").addQueryStringParameters())
				given Conversion[getVacation, Future[Schema.VacationSettings]] = (fun: getVacation) => fun.apply()
			}
			class getLanguage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LanguageSettings]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.LanguageSettings])
			}
			object getLanguage {
				def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): getLanguage = new getLanguage(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/language").addQueryStringParameters())
				given Conversion[getLanguage, Future[Schema.LanguageSettings]] = (fun: getLanguage) => fun.apply()
			}
			class getPop(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PopSettings]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.PopSettings])
			}
			object getPop {
				def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): getPop = new getPop(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/pop").addQueryStringParameters())
				given Conversion[getPop, Future[Schema.PopSettings]] = (fun: getPop) => fun.apply()
			}
			class updateVacation(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withVacationSettings(body: Schema.VacationSettings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.VacationSettings])
			}
			object updateVacation {
				def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): updateVacation = new updateVacation(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/vacation").addQueryStringParameters())
			}
			class updateLanguage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withLanguageSettings(body: Schema.LanguageSettings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.LanguageSettings])
			}
			object updateLanguage {
				def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): updateLanguage = new updateLanguage(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/language").addQueryStringParameters())
			}
			class getAutoForwarding(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AutoForwarding]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AutoForwarding])
			}
			object getAutoForwarding {
				def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): getAutoForwarding = new getAutoForwarding(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/autoForwarding").addQueryStringParameters())
				given Conversion[getAutoForwarding, Future[Schema.AutoForwarding]] = (fun: getAutoForwarding) => fun.apply()
			}
			class updatePop(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withPopSettings(body: Schema.PopSettings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.PopSettings])
			}
			object updatePop {
				def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): updatePop = new updatePop(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/pop").addQueryStringParameters())
			}
			class updateAutoForwarding(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAutoForwarding(body: Schema.AutoForwarding) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.AutoForwarding])
			}
			object updateAutoForwarding {
				def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): updateAutoForwarding = new updateAutoForwarding(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/autoForwarding").addQueryStringParameters())
			}
			object sendAs {
				class verify(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
					def apply() = auth.exec(req,_.execute("POST")).map(_ => ())
				}
				object verify {
					def apply(userId: String, sendAsEmail: String)(using auth: AuthToken, ec: ExecutionContext): verify = new verify(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs/${sendAsEmail}/verify").addQueryStringParameters())
					given Conversion[verify, Future[Unit]] = (fun: verify) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSendAs(body: Schema.SendAs) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SendAs])
				}
				object create {
					def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs").addQueryStringParameters())
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
				}
				object delete {
					def apply(userId: String, sendAsEmail: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs/${sendAsEmail}").addQueryStringParameters())
					given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SendAs]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SendAs])
				}
				object get {
					def apply(userId: String, sendAsEmail: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs/${sendAsEmail}").addQueryStringParameters())
					given Conversion[get, Future[Schema.SendAs]] = (fun: get) => fun.apply()
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSendAs(body: Schema.SendAs) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.SendAs])
				}
				object update {
					def apply(userId: String, sendAsEmail: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs/${sendAsEmail}").addQueryStringParameters())
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSendAs(body: Schema.SendAs) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SendAs])
				}
				object patch {
					def apply(userId: String, sendAsEmail: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs/${sendAsEmail}").addQueryStringParameters())
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSendAsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSendAsResponse])
				}
				object list {
					def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs").addQueryStringParameters())
					given Conversion[list, Future[Schema.ListSendAsResponse]] = (fun: list) => fun.apply()
				}
				object smimeInfo {
					class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSmimeInfo(body: Schema.SmimeInfo) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SmimeInfo])
					}
					object insert {
						def apply(userId: String, sendAsEmail: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs/${sendAsEmail}/smimeInfo").addQueryStringParameters())
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(userId: String, sendAsEmail: String, id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs/${sendAsEmail}/smimeInfo/${id}").addQueryStringParameters())
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SmimeInfo]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SmimeInfo])
					}
					object get {
						def apply(userId: String, sendAsEmail: String, id: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs/${sendAsEmail}/smimeInfo/${id}").addQueryStringParameters())
						given Conversion[get, Future[Schema.SmimeInfo]] = (fun: get) => fun.apply()
					}
					class setDefault(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
						def apply() = auth.exec(req,_.execute("POST")).map(_ => ())
					}
					object setDefault {
						def apply(userId: String, sendAsEmail: String, id: String)(using auth: AuthToken, ec: ExecutionContext): setDefault = new setDefault(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs/${sendAsEmail}/smimeInfo/${id}/setDefault").addQueryStringParameters())
						given Conversion[setDefault, Future[Unit]] = (fun: setDefault) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSmimeInfoResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListSmimeInfoResponse])
					}
					object list {
						def apply(userId: String, sendAsEmail: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/sendAs/${sendAsEmail}/smimeInfo").addQueryStringParameters())
						given Conversion[list, Future[Schema.ListSmimeInfoResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object cse {
				object identities {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCseIdentity(body: Schema.CseIdentity) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CseIdentity])
					}
					object create {
						def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/identities").addQueryStringParameters())
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
					}
					object delete {
						def apply(userId: String, cseEmailAddress: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/identities/${cseEmailAddress}").addQueryStringParameters())
						given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CseIdentity]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CseIdentity])
					}
					object get {
						def apply(userId: String, cseEmailAddress: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/identities/${cseEmailAddress}").addQueryStringParameters())
						given Conversion[get, Future[Schema.CseIdentity]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCseIdentity(body: Schema.CseIdentity) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CseIdentity])
					}
					object patch {
						def apply(userId: String, emailAddress: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/identities/${emailAddress}").addQueryStringParameters())
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCseIdentitiesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCseIdentitiesResponse])
					}
					object list {
						def apply(userId: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/identities").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.ListCseIdentitiesResponse]] = (fun: list) => fun.apply()
					}
				}
				object keypairs {
					class enable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withEnableCseKeyPairRequest(body: Schema.EnableCseKeyPairRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CseKeyPair])
					}
					object enable {
						def apply(userId: String, keyPairId: String)(using auth: AuthToken, ec: ExecutionContext): enable = new enable(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/keypairs/${keyPairId}:enable").addQueryStringParameters())
					}
					class obliterate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withObliterateCseKeyPairRequest(body: Schema.ObliterateCseKeyPairRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
					}
					object obliterate {
						def apply(userId: String, keyPairId: String)(using auth: AuthToken, ec: ExecutionContext): obliterate = new obliterate(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/keypairs/${keyPairId}:obliterate").addQueryStringParameters())
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withCseKeyPair(body: Schema.CseKeyPair) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CseKeyPair])
					}
					object create {
						def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/keypairs").addQueryStringParameters())
					}
					class disable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withDisableCseKeyPairRequest(body: Schema.DisableCseKeyPairRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CseKeyPair])
					}
					object disable {
						def apply(userId: String, keyPairId: String)(using auth: AuthToken, ec: ExecutionContext): disable = new disable(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/keypairs/${keyPairId}:disable").addQueryStringParameters())
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.CseKeyPair]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.CseKeyPair])
					}
					object get {
						def apply(userId: String, keyPairId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/keypairs/${keyPairId}").addQueryStringParameters())
						given Conversion[get, Future[Schema.CseKeyPair]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCseKeyPairsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCseKeyPairsResponse])
					}
					object list {
						def apply(userId: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/cse/keypairs").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.ListCseKeyPairsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object forwardingAddresses {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListForwardingAddressesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListForwardingAddressesResponse])
				}
				object list {
					def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/forwardingAddresses").addQueryStringParameters())
					given Conversion[list, Future[Schema.ListForwardingAddressesResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ForwardingAddress]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ForwardingAddress])
				}
				object get {
					def apply(userId: String, forwardingEmail: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/forwardingAddresses/${forwardingEmail}").addQueryStringParameters())
					given Conversion[get, Future[Schema.ForwardingAddress]] = (fun: get) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withForwardingAddress(body: Schema.ForwardingAddress) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ForwardingAddress])
				}
				object create {
					def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/forwardingAddresses").addQueryStringParameters())
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
				}
				object delete {
					def apply(userId: String, forwardingEmail: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/forwardingAddresses/${forwardingEmail}").addQueryStringParameters())
					given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
				}
			}
			object filters {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFiltersResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListFiltersResponse])
				}
				object list {
					def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/filters").addQueryStringParameters())
					given Conversion[list, Future[Schema.ListFiltersResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Filter]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Filter])
				}
				object get {
					def apply(userId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/filters/${id}").addQueryStringParameters())
					given Conversion[get, Future[Schema.Filter]] = (fun: get) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withFilter(body: Schema.Filter) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Filter])
				}
				object create {
					def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/filters").addQueryStringParameters())
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
				}
				object delete {
					def apply(userId: String, id: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/filters/${id}").addQueryStringParameters())
					given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
				}
			}
			object delegates {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDelegatesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListDelegatesResponse])
				}
				object list {
					def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/delegates").addQueryStringParameters())
					given Conversion[list, Future[Schema.ListDelegatesResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Delegate]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Delegate])
				}
				object get {
					def apply(userId: String, delegateEmail: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/delegates/${delegateEmail}").addQueryStringParameters())
					given Conversion[get, Future[Schema.Delegate]] = (fun: get) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDelegate(body: Schema.Delegate) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Delegate])
				}
				object create {
					def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/delegates").addQueryStringParameters())
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
				}
				object delete {
					def apply(userId: String, delegateEmail: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"gmail/v1/users/${userId}/settings/delegates/${delegateEmail}").addQueryStringParameters())
					given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
				}
			}
		}
	}
}
