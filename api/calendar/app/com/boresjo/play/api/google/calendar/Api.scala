package com.boresjo.play.api.google.calendar

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
		"""https://www.googleapis.com/auth/calendar""" /* See, edit, share, and permanently delete all the calendars you can access using Google Calendar */,
		"""https://www.googleapis.com/auth/calendar.events""" /* View and edit events on all your calendars */,
		"""https://www.googleapis.com/auth/calendar.events.readonly""" /* View events on all your calendars */,
		"""https://www.googleapis.com/auth/calendar.readonly""" /* See and download any calendar you can access using your Google Calendar */,
		"""https://www.googleapis.com/auth/calendar.settings.readonly""" /* View your Calendar settings */
	)

	private val BASE_URL = "https://www.googleapis.com/calendar/v3/"

	object acl {
		/** Creates an access control rule. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""")
			/** Whether to send notifications about the calendar sharing change. Optional. The default is True. */
			def withSendNotifications(sendNotifications: Boolean) = new insert(req.addQueryStringParameters("sendNotifications" -> sendNotifications.toString))
			/** Perform the request */
			def withAclRule(body: Schema.AclRule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AclRule])
		}
		object insert {
			def apply(calendarId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"calendars/${calendarId}/acl").addQueryStringParameters())
		}
		/** Watch for changes to ACL resources. */
		class watch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""")
			/** Maximum number of entries returned on one result page. By default the value is 100 entries. The page size can never be larger than 250 entries. Optional.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new watch(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Token specifying which result page to return. Optional. */
			def withPageToken(pageToken: String) = new watch(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Whether to include deleted ACLs in the result. Deleted ACLs are represented by role equal to "none". Deleted ACLs will always be included if syncToken is provided. Optional. The default is False. */
			def withShowDeleted(showDeleted: Boolean) = new watch(req.addQueryStringParameters("showDeleted" -> showDeleted.toString))
			/** Token obtained from the nextSyncToken field returned on the last page of results from the previous list request. It makes the result of this list request contain only entries that have changed since then. All entries deleted since the previous list request will always be in the result set and it is not allowed to set showDeleted to False.
If the syncToken expires, the server will respond with a 410 GONE response code and the client should clear its storage and perform a full synchronization without any syncToken.
Learn more about incremental synchronization.
Optional. The default is to return all entries. */
			def withSyncToken(syncToken: String) = new watch(req.addQueryStringParameters("syncToken" -> syncToken.toString))
			/** Perform the request */
			def withChannel(body: Schema.Channel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Channel])
		}
		object watch {
			def apply(calendarId: String)(using signer: RequestSigner, ec: ExecutionContext): watch = new watch(ws.url(BASE_URL + s"calendars/${calendarId}/acl/watch").addQueryStringParameters())
		}
		/** Deletes an access control rule. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(calendarId: String, ruleId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"calendars/${calendarId}/acl/${ruleId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Returns an access control rule. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AclRule]) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AclRule])
		}
		object get {
			def apply(calendarId: String, ruleId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"calendars/${calendarId}/acl/${ruleId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.AclRule]] = (fun: get) => fun.apply()
		}
		/** Updates an access control rule. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""")
			/** Whether to send notifications about the calendar sharing change. Note that there are no notifications on access removal. Optional. The default is True. */
			def withSendNotifications(sendNotifications: Boolean) = new update(req.addQueryStringParameters("sendNotifications" -> sendNotifications.toString))
			/** Perform the request */
			def withAclRule(body: Schema.AclRule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.AclRule])
		}
		object update {
			def apply(calendarId: String, ruleId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"calendars/${calendarId}/acl/${ruleId}").addQueryStringParameters())
		}
		/** Updates an access control rule. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""")
			/** Whether to send notifications about the calendar sharing change. Note that there are no notifications on access removal. Optional. The default is True. */
			def withSendNotifications(sendNotifications: Boolean) = new patch(req.addQueryStringParameters("sendNotifications" -> sendNotifications.toString))
			/** Perform the request */
			def withAclRule(body: Schema.AclRule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AclRule])
		}
		object patch {
			def apply(calendarId: String, ruleId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"calendars/${calendarId}/acl/${ruleId}").addQueryStringParameters())
		}
		/** Returns the rules in the access control list for the calendar. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Acl]) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""")
			/** Maximum number of entries returned on one result page. By default the value is 100 entries. The page size can never be larger than 250 entries. Optional.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Token specifying which result page to return. Optional. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Whether to include deleted ACLs in the result. Deleted ACLs are represented by role equal to "none". Deleted ACLs will always be included if syncToken is provided. Optional. The default is False. */
			def withShowDeleted(showDeleted: Boolean) = new list(req.addQueryStringParameters("showDeleted" -> showDeleted.toString))
			/** Token obtained from the nextSyncToken field returned on the last page of results from the previous list request. It makes the result of this list request contain only entries that have changed since then. All entries deleted since the previous list request will always be in the result set and it is not allowed to set showDeleted to False.
If the syncToken expires, the server will respond with a 410 GONE response code and the client should clear its storage and perform a full synchronization without any syncToken.
Learn more about incremental synchronization.
Optional. The default is to return all entries. */
			def withSyncToken(syncToken: String) = new list(req.addQueryStringParameters("syncToken" -> syncToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Acl])
		}
		object list {
			def apply(calendarId: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"calendars/${calendarId}/acl").addQueryStringParameters())
			given Conversion[list, Future[Schema.Acl]] = (fun: list) => fun.apply()
		}
	}
	object calendarList {
		/** Inserts an existing calendar into the user's calendar list. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""")
			/** Whether to use the foregroundColor and backgroundColor fields to write the calendar colors (RGB). If this feature is used, the index-based colorId field will be set to the best matching option automatically. Optional. The default is False. */
			def withColorRgbFormat(colorRgbFormat: Boolean) = new insert(req.addQueryStringParameters("colorRgbFormat" -> colorRgbFormat.toString))
			/** Perform the request */
			def withCalendarListEntry(body: Schema.CalendarListEntry) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CalendarListEntry])
		}
		object insert {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"users/me/calendarList").addQueryStringParameters())
		}
		/** Watch for changes to CalendarList resources. */
		class watch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.readonly""")
			/** Maximum number of entries returned on one result page. By default the value is 100 entries. The page size can never be larger than 250 entries. Optional.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new watch(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** The minimum access role for the user in the returned entries. Optional. The default is no restriction.<br>Possible values:<br>freeBusyReader: The user can read free/busy information.<br>owner: The user can read and modify events and access control lists.<br>reader: The user can read events that are not private.<br>writer: The user can read and modify events. */
			def withMinAccessRole(minAccessRole: String) = new watch(req.addQueryStringParameters("minAccessRole" -> minAccessRole.toString))
			/** Token specifying which result page to return. Optional. */
			def withPageToken(pageToken: String) = new watch(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Whether to include deleted calendar list entries in the result. Optional. The default is False. */
			def withShowDeleted(showDeleted: Boolean) = new watch(req.addQueryStringParameters("showDeleted" -> showDeleted.toString))
			/** Whether to show hidden entries. Optional. The default is False. */
			def withShowHidden(showHidden: Boolean) = new watch(req.addQueryStringParameters("showHidden" -> showHidden.toString))
			/** Token obtained from the nextSyncToken field returned on the last page of results from the previous list request. It makes the result of this list request contain only entries that have changed since then. If only read-only fields such as calendar properties or ACLs have changed, the entry won't be returned. All entries deleted and hidden since the previous list request will always be in the result set and it is not allowed to set showDeleted neither showHidden to False.
To ensure client state consistency minAccessRole query parameter cannot be specified together with nextSyncToken.
If the syncToken expires, the server will respond with a 410 GONE response code and the client should clear its storage and perform a full synchronization without any syncToken.
Learn more about incremental synchronization.
Optional. The default is to return all entries. */
			def withSyncToken(syncToken: String) = new watch(req.addQueryStringParameters("syncToken" -> syncToken.toString))
			/** Perform the request */
			def withChannel(body: Schema.Channel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Channel])
		}
		object watch {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): watch = new watch(ws.url(BASE_URL + s"users/me/calendarList/watch").addQueryStringParameters())
		}
		/** Removes a calendar from the user's calendar list. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(calendarId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"users/me/calendarList/${calendarId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Returns a calendar from the user's calendar list. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CalendarListEntry]) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CalendarListEntry])
		}
		object get {
			def apply(calendarId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"users/me/calendarList/${calendarId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.CalendarListEntry]] = (fun: get) => fun.apply()
		}
		/** Updates an existing calendar on the user's calendar list. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""")
			/** Whether to use the foregroundColor and backgroundColor fields to write the calendar colors (RGB). If this feature is used, the index-based colorId field will be set to the best matching option automatically. Optional. The default is False. */
			def withColorRgbFormat(colorRgbFormat: Boolean) = new update(req.addQueryStringParameters("colorRgbFormat" -> colorRgbFormat.toString))
			/** Perform the request */
			def withCalendarListEntry(body: Schema.CalendarListEntry) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.CalendarListEntry])
		}
		object update {
			def apply(calendarId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"users/me/calendarList/${calendarId}").addQueryStringParameters())
		}
		/** Updates an existing calendar on the user's calendar list. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""")
			/** Whether to use the foregroundColor and backgroundColor fields to write the calendar colors (RGB). If this feature is used, the index-based colorId field will be set to the best matching option automatically. Optional. The default is False. */
			def withColorRgbFormat(colorRgbFormat: Boolean) = new patch(req.addQueryStringParameters("colorRgbFormat" -> colorRgbFormat.toString))
			/** Perform the request */
			def withCalendarListEntry(body: Schema.CalendarListEntry) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.CalendarListEntry])
		}
		object patch {
			def apply(calendarId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"users/me/calendarList/${calendarId}").addQueryStringParameters())
		}
		/** Returns the calendars on the user's calendar list. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.CalendarList]) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.readonly""")
			/** Maximum number of entries returned on one result page. By default the value is 100 entries. The page size can never be larger than 250 entries. Optional.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** The minimum access role for the user in the returned entries. Optional. The default is no restriction.<br>Possible values:<br>freeBusyReader: The user can read free/busy information.<br>owner: The user can read and modify events and access control lists.<br>reader: The user can read events that are not private.<br>writer: The user can read and modify events. */
			def withMinAccessRole(minAccessRole: String) = new list(req.addQueryStringParameters("minAccessRole" -> minAccessRole.toString))
			/** Token specifying which result page to return. Optional. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Whether to include deleted calendar list entries in the result. Optional. The default is False. */
			def withShowDeleted(showDeleted: Boolean) = new list(req.addQueryStringParameters("showDeleted" -> showDeleted.toString))
			/** Whether to show hidden entries. Optional. The default is False. */
			def withShowHidden(showHidden: Boolean) = new list(req.addQueryStringParameters("showHidden" -> showHidden.toString))
			/** Token obtained from the nextSyncToken field returned on the last page of results from the previous list request. It makes the result of this list request contain only entries that have changed since then. If only read-only fields such as calendar properties or ACLs have changed, the entry won't be returned. All entries deleted and hidden since the previous list request will always be in the result set and it is not allowed to set showDeleted neither showHidden to False.
To ensure client state consistency minAccessRole query parameter cannot be specified together with nextSyncToken.
If the syncToken expires, the server will respond with a 410 GONE response code and the client should clear its storage and perform a full synchronization without any syncToken.
Learn more about incremental synchronization.
Optional. The default is to return all entries. */
			def withSyncToken(syncToken: String) = new list(req.addQueryStringParameters("syncToken" -> syncToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.CalendarList])
		}
		object list {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"users/me/calendarList").addQueryStringParameters())
			given Conversion[list, Future[Schema.CalendarList]] = (fun: list) => fun.apply()
		}
	}
	object calendars {
		/** Creates a secondary calendar. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""")
			/** Perform the request */
			def withCalendar(body: Schema.Calendar) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Calendar])
		}
		object insert {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"calendars").addQueryStringParameters())
		}
		/** Clears a primary calendar. This operation deletes all events associated with the primary calendar of an account. */
		class clear(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_ => ())
		}
		object clear {
			def apply(calendarId: String)(using signer: RequestSigner, ec: ExecutionContext): clear = new clear(ws.url(BASE_URL + s"calendars/${calendarId}/clear").addQueryStringParameters())
			given Conversion[clear, Future[Unit]] = (fun: clear) => fun.apply()
		}
		/** Deletes a secondary calendar. Use calendars.clear for clearing all events on primary calendars. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(calendarId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"calendars/${calendarId}").addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Returns metadata for a calendar. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Calendar]) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Calendar])
		}
		object get {
			def apply(calendarId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"calendars/${calendarId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Calendar]] = (fun: get) => fun.apply()
		}
		/** Updates metadata for a calendar. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""")
			/** Perform the request */
			def withCalendar(body: Schema.Calendar) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Calendar])
		}
		object update {
			def apply(calendarId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"calendars/${calendarId}").addQueryStringParameters())
		}
		/** Updates metadata for a calendar. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""")
			/** Perform the request */
			def withCalendar(body: Schema.Calendar) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Calendar])
		}
		object patch {
			def apply(calendarId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"calendars/${calendarId}").addQueryStringParameters())
		}
	}
	object channels {
		/** Stop watching resources through this channel */
		class stop(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.events""", """https://www.googleapis.com/auth/calendar.events.readonly""", """https://www.googleapis.com/auth/calendar.readonly""", """https://www.googleapis.com/auth/calendar.settings.readonly""")
			/** Perform the request */
			def withChannel(body: Schema.Channel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_ => ())
		}
		object stop {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"channels/stop").addQueryStringParameters())
		}
	}
	object colors {
		/** Returns the color definitions for calendars and events. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Colors]) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Colors])
		}
		object get {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"colors").addQueryStringParameters())
			given Conversion[get, Future[Schema.Colors]] = (fun: get) => fun.apply()
		}
	}
	object events {
		/** Moves an event to another calendar, i.e. changes an event's organizer. Note that only default events can be moved; birthday, focusTime, fromGmail, outOfOffice and workingLocation events cannot be moved. */
		class move(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Event]) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.events""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Event])
		}
		object move {
			def apply(calendarId: String, destination: String, eventId: String, sendNotifications: Boolean, sendUpdates: String)(using signer: RequestSigner, ec: ExecutionContext): move = new move(ws.url(BASE_URL + s"calendars/${calendarId}/events/${eventId}/move").addQueryStringParameters("destination" -> destination.toString, "sendNotifications" -> sendNotifications.toString, "sendUpdates" -> sendUpdates.toString))
			given Conversion[move, Future[Schema.Event]] = (fun: move) => fun.apply()
		}
		/** Creates an event. */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.events""")
			/** The maximum number of attendees to include in the response. If there are more than the specified number of attendees, only the participant is returned. Optional.<br>Format: int32 */
			def withMaxAttendees(maxAttendees: Int) = new insert(req.addQueryStringParameters("maxAttendees" -> maxAttendees.toString))
			/** Whether API client performing operation supports event attachments. Optional. The default is False. */
			def withSupportsAttachments(supportsAttachments: Boolean) = new insert(req.addQueryStringParameters("supportsAttachments" -> supportsAttachments.toString))
			/** Perform the request */
			def withEvent(body: Schema.Event) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Event])
		}
		object insert {
			def apply(calendarId: String, conferenceDataVersion: Int, sendNotifications: Boolean, sendUpdates: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"calendars/${calendarId}/events").addQueryStringParameters("conferenceDataVersion" -> conferenceDataVersion.toString, "sendNotifications" -> sendNotifications.toString, "sendUpdates" -> sendUpdates.toString))
		}
		/** Creates an event based on a simple text string. */
		class quickAdd(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Event]) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.events""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Event])
		}
		object quickAdd {
			def apply(calendarId: String, sendNotifications: Boolean, sendUpdates: String, text: String)(using signer: RequestSigner, ec: ExecutionContext): quickAdd = new quickAdd(ws.url(BASE_URL + s"calendars/${calendarId}/events/quickAdd").addQueryStringParameters("sendNotifications" -> sendNotifications.toString, "sendUpdates" -> sendUpdates.toString, "text" -> text.toString))
			given Conversion[quickAdd, Future[Schema.Event]] = (fun: quickAdd) => fun.apply()
		}
		/** Imports an event. This operation is used to add a private copy of an existing event to a calendar. Only events with an eventType of default may be imported.
Deprecated behavior: If a non-default event is imported, its type will be changed to default and any event-type-specific properties it may have will be dropped. */
		class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.events""")
			/** Whether API client performing operation supports event attachments. Optional. The default is False. */
			def withSupportsAttachments(supportsAttachments: Boolean) = new `import`(req.addQueryStringParameters("supportsAttachments" -> supportsAttachments.toString))
			/** Perform the request */
			def withEvent(body: Schema.Event) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Event])
		}
		object `import` {
			def apply(calendarId: String, conferenceDataVersion: Int)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"calendars/${calendarId}/events/import").addQueryStringParameters("conferenceDataVersion" -> conferenceDataVersion.toString))
		}
		/** Deletes an event. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.events""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(calendarId: String, eventId: String, sendNotifications: Boolean, sendUpdates: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"calendars/${calendarId}/events/${eventId}").addQueryStringParameters("sendNotifications" -> sendNotifications.toString, "sendUpdates" -> sendUpdates.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Returns an event based on its Google Calendar ID. To retrieve an event using its iCalendar ID, call the events.list method using the iCalUID parameter. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Event]) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.events""", """https://www.googleapis.com/auth/calendar.events.readonly""", """https://www.googleapis.com/auth/calendar.readonly""")
			/** The maximum number of attendees to include in the response. If there are more than the specified number of attendees, only the participant is returned. Optional.<br>Format: int32 */
			def withMaxAttendees(maxAttendees: Int) = new get(req.addQueryStringParameters("maxAttendees" -> maxAttendees.toString))
			/** Time zone used in the response. Optional. The default is the time zone of the calendar. */
			def withTimeZone(timeZone: String) = new get(req.addQueryStringParameters("timeZone" -> timeZone.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Event])
		}
		object get {
			def apply(alwaysIncludeEmail: Boolean, calendarId: String, eventId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"calendars/${calendarId}/events/${eventId}").addQueryStringParameters("alwaysIncludeEmail" -> alwaysIncludeEmail.toString))
			given Conversion[get, Future[Schema.Event]] = (fun: get) => fun.apply()
		}
		/** Updates an event. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.events""")
			/** The maximum number of attendees to include in the response. If there are more than the specified number of attendees, only the participant is returned. Optional.<br>Format: int32 */
			def withMaxAttendees(maxAttendees: Int) = new update(req.addQueryStringParameters("maxAttendees" -> maxAttendees.toString))
			/** Whether API client performing operation supports event attachments. Optional. The default is False. */
			def withSupportsAttachments(supportsAttachments: Boolean) = new update(req.addQueryStringParameters("supportsAttachments" -> supportsAttachments.toString))
			/** Perform the request */
			def withEvent(body: Schema.Event) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Event])
		}
		object update {
			def apply(alwaysIncludeEmail: Boolean, calendarId: String, conferenceDataVersion: Int, eventId: String, sendNotifications: Boolean, sendUpdates: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"calendars/${calendarId}/events/${eventId}").addQueryStringParameters("alwaysIncludeEmail" -> alwaysIncludeEmail.toString, "conferenceDataVersion" -> conferenceDataVersion.toString, "sendNotifications" -> sendNotifications.toString, "sendUpdates" -> sendUpdates.toString))
		}
		/** Updates an event. This method supports patch semantics. */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.events""")
			/** The maximum number of attendees to include in the response. If there are more than the specified number of attendees, only the participant is returned. Optional.<br>Format: int32 */
			def withMaxAttendees(maxAttendees: Int) = new patch(req.addQueryStringParameters("maxAttendees" -> maxAttendees.toString))
			/** Whether API client performing operation supports event attachments. Optional. The default is False. */
			def withSupportsAttachments(supportsAttachments: Boolean) = new patch(req.addQueryStringParameters("supportsAttachments" -> supportsAttachments.toString))
			/** Perform the request */
			def withEvent(body: Schema.Event) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Event])
		}
		object patch {
			def apply(alwaysIncludeEmail: Boolean, calendarId: String, conferenceDataVersion: Int, eventId: String, sendNotifications: Boolean, sendUpdates: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"calendars/${calendarId}/events/${eventId}").addQueryStringParameters("alwaysIncludeEmail" -> alwaysIncludeEmail.toString, "conferenceDataVersion" -> conferenceDataVersion.toString, "sendNotifications" -> sendNotifications.toString, "sendUpdates" -> sendUpdates.toString))
		}
		/** Returns events on the specified calendar. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Events]) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.events""", """https://www.googleapis.com/auth/calendar.events.readonly""", """https://www.googleapis.com/auth/calendar.readonly""")
			/** Event types to return. Optional. This parameter can be repeated multiple times to return events of different types. If unset, returns all event types.<br>Possible values:<br>birthday: Special all-day events with an annual recurrence.<br>default: Regular events.<br>focusTime: Focus time events.<br>fromGmail: Events from Gmail.<br>outOfOffice: Out of office events.<br>workingLocation: Working location events. */
			def withEventTypes(eventTypes: String) = new list(req.addQueryStringParameters("eventTypes" -> eventTypes.toString))
			/** Specifies an event ID in the iCalendar format to be provided in the response. Optional. Use this if you want to search for an event by its iCalendar ID. */
			def withICalUID(iCalUID: String) = new list(req.addQueryStringParameters("iCalUID" -> iCalUID.toString))
			/** The maximum number of attendees to include in the response. If there are more than the specified number of attendees, only the participant is returned. Optional.<br>Format: int32 */
			def withMaxAttendees(maxAttendees: Int) = new list(req.addQueryStringParameters("maxAttendees" -> maxAttendees.toString))
			/** Maximum number of events returned on one result page. The number of events in the resulting page may be less than this value, or none at all, even if there are more events matching the query. Incomplete pages can be detected by a non-empty nextPageToken field in the response. By default the value is 250 events. The page size can never be larger than 2500 events. Optional.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** The order of the events returned in the result. Optional. The default is an unspecified, stable order.<br>Possible values:<br>startTime: Order by the start date/time (ascending). This is only available when querying single events (i.e. the parameter singleEvents is True)<br>updated: Order by last modification time (ascending). */
			def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
			/** Token specifying which result page to return. Optional. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Free text search terms to find events that match these terms in the following fields:

- summary 
- description 
- location 
- attendee's displayName 
- attendee's email 
- organizer's displayName 
- organizer's email 
- workingLocationProperties.officeLocation.buildingId 
- workingLocationProperties.officeLocation.deskId 
- workingLocationProperties.officeLocation.label 
- workingLocationProperties.customLocation.label 
These search terms also match predefined keywords against all display title translations of working location, out-of-office, and focus-time events. For example, searching for "Office" or "Bureau" returns working location events of type officeLocation, whereas searching for "Out of office" or "Abwesend" returns out-of-office events. Optional. */
			def withQ(q: String) = new list(req.addQueryStringParameters("q" -> q.toString))
			/** Whether to include deleted events (with status equals "cancelled") in the result. Cancelled instances of recurring events (but not the underlying recurring event) will still be included if showDeleted and singleEvents are both False. If showDeleted and singleEvents are both True, only single instances of deleted events (but not the underlying recurring events) are returned. Optional. The default is False. */
			def withShowDeleted(showDeleted: Boolean) = new list(req.addQueryStringParameters("showDeleted" -> showDeleted.toString))
			/** Whether to include hidden invitations in the result. Optional. The default is False. */
			def withShowHiddenInvitations(showHiddenInvitations: Boolean) = new list(req.addQueryStringParameters("showHiddenInvitations" -> showHiddenInvitations.toString))
			/** Whether to expand recurring events into instances and only return single one-off events and instances of recurring events, but not the underlying recurring events themselves. Optional. The default is False. */
			def withSingleEvents(singleEvents: Boolean) = new list(req.addQueryStringParameters("singleEvents" -> singleEvents.toString))
			/** Token obtained from the nextSyncToken field returned on the last page of results from the previous list request. It makes the result of this list request contain only entries that have changed since then. All events deleted since the previous list request will always be in the result set and it is not allowed to set showDeleted to False.
There are several query parameters that cannot be specified together with nextSyncToken to ensure consistency of the client state.

These are: 
- iCalUID 
- orderBy 
- privateExtendedProperty 
- q 
- sharedExtendedProperty 
- timeMin 
- timeMax 
- updatedMin All other query parameters should be the same as for the initial synchronization to avoid undefined behavior. If the syncToken expires, the server will respond with a 410 GONE response code and the client should clear its storage and perform a full synchronization without any syncToken.
Learn more about incremental synchronization.
Optional. The default is to return all entries. */
			def withSyncToken(syncToken: String) = new list(req.addQueryStringParameters("syncToken" -> syncToken.toString))
			/** Upper bound (exclusive) for an event's start time to filter by. Optional. The default is not to filter by start time. Must be an RFC3339 timestamp with mandatory time zone offset, for example, 2011-06-03T10:00:00-07:00, 2011-06-03T10:00:00Z. Milliseconds may be provided but are ignored. If timeMin is set, timeMax must be greater than timeMin.<br>Format: date-time */
			def withTimeMax(timeMax: String) = new list(req.addQueryStringParameters("timeMax" -> timeMax.toString))
			/** Lower bound (exclusive) for an event's end time to filter by. Optional. The default is not to filter by end time. Must be an RFC3339 timestamp with mandatory time zone offset, for example, 2011-06-03T10:00:00-07:00, 2011-06-03T10:00:00Z. Milliseconds may be provided but are ignored. If timeMax is set, timeMin must be smaller than timeMax.<br>Format: date-time */
			def withTimeMin(timeMin: String) = new list(req.addQueryStringParameters("timeMin" -> timeMin.toString))
			/** Time zone used in the response. Optional. The default is the time zone of the calendar. */
			def withTimeZone(timeZone: String) = new list(req.addQueryStringParameters("timeZone" -> timeZone.toString))
			/** Lower bound for an event's last modification time (as a RFC3339 timestamp) to filter by. When specified, entries deleted since this time will always be included regardless of showDeleted. Optional. The default is not to filter by last modification time.<br>Format: date-time */
			def withUpdatedMin(updatedMin: String) = new list(req.addQueryStringParameters("updatedMin" -> updatedMin.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Events])
		}
		object list {
			def apply(alwaysIncludeEmail: Boolean, calendarId: String, privateExtendedProperty: String, sharedExtendedProperty: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"calendars/${calendarId}/events").addQueryStringParameters("alwaysIncludeEmail" -> alwaysIncludeEmail.toString, "privateExtendedProperty" -> privateExtendedProperty.toString, "sharedExtendedProperty" -> sharedExtendedProperty.toString))
			given Conversion[list, Future[Schema.Events]] = (fun: list) => fun.apply()
		}
		/** Watch for changes to Events resources. */
		class watch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.events""", """https://www.googleapis.com/auth/calendar.events.readonly""", """https://www.googleapis.com/auth/calendar.readonly""")
			/** Event types to return. Optional. This parameter can be repeated multiple times to return events of different types. If unset, returns all event types.<br>Possible values:<br>birthday: Special all-day events with an annual recurrence.<br>default: Regular events.<br>focusTime: Focus time events.<br>fromGmail: Events from Gmail.<br>outOfOffice: Out of office events.<br>workingLocation: Working location events. */
			def withEventTypes(eventTypes: String) = new watch(req.addQueryStringParameters("eventTypes" -> eventTypes.toString))
			/** Specifies an event ID in the iCalendar format to be provided in the response. Optional. Use this if you want to search for an event by its iCalendar ID. */
			def withICalUID(iCalUID: String) = new watch(req.addQueryStringParameters("iCalUID" -> iCalUID.toString))
			/** The maximum number of attendees to include in the response. If there are more than the specified number of attendees, only the participant is returned. Optional.<br>Format: int32 */
			def withMaxAttendees(maxAttendees: Int) = new watch(req.addQueryStringParameters("maxAttendees" -> maxAttendees.toString))
			/** Maximum number of events returned on one result page. The number of events in the resulting page may be less than this value, or none at all, even if there are more events matching the query. Incomplete pages can be detected by a non-empty nextPageToken field in the response. By default the value is 250 events. The page size can never be larger than 2500 events. Optional.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new watch(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** The order of the events returned in the result. Optional. The default is an unspecified, stable order.<br>Possible values:<br>startTime: Order by the start date/time (ascending). This is only available when querying single events (i.e. the parameter singleEvents is True)<br>updated: Order by last modification time (ascending). */
			def withOrderBy(orderBy: String) = new watch(req.addQueryStringParameters("orderBy" -> orderBy.toString))
			/** Token specifying which result page to return. Optional. */
			def withPageToken(pageToken: String) = new watch(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Free text search terms to find events that match these terms in the following fields:

- summary 
- description 
- location 
- attendee's displayName 
- attendee's email 
- organizer's displayName 
- organizer's email 
- workingLocationProperties.officeLocation.buildingId 
- workingLocationProperties.officeLocation.deskId 
- workingLocationProperties.officeLocation.label 
- workingLocationProperties.customLocation.label 
These search terms also match predefined keywords against all display title translations of working location, out-of-office, and focus-time events. For example, searching for "Office" or "Bureau" returns working location events of type officeLocation, whereas searching for "Out of office" or "Abwesend" returns out-of-office events. Optional. */
			def withQ(q: String) = new watch(req.addQueryStringParameters("q" -> q.toString))
			/** Whether to include deleted events (with status equals "cancelled") in the result. Cancelled instances of recurring events (but not the underlying recurring event) will still be included if showDeleted and singleEvents are both False. If showDeleted and singleEvents are both True, only single instances of deleted events (but not the underlying recurring events) are returned. Optional. The default is False. */
			def withShowDeleted(showDeleted: Boolean) = new watch(req.addQueryStringParameters("showDeleted" -> showDeleted.toString))
			/** Whether to include hidden invitations in the result. Optional. The default is False. */
			def withShowHiddenInvitations(showHiddenInvitations: Boolean) = new watch(req.addQueryStringParameters("showHiddenInvitations" -> showHiddenInvitations.toString))
			/** Whether to expand recurring events into instances and only return single one-off events and instances of recurring events, but not the underlying recurring events themselves. Optional. The default is False. */
			def withSingleEvents(singleEvents: Boolean) = new watch(req.addQueryStringParameters("singleEvents" -> singleEvents.toString))
			/** Token obtained from the nextSyncToken field returned on the last page of results from the previous list request. It makes the result of this list request contain only entries that have changed since then. All events deleted since the previous list request will always be in the result set and it is not allowed to set showDeleted to False.
There are several query parameters that cannot be specified together with nextSyncToken to ensure consistency of the client state.

These are: 
- iCalUID 
- orderBy 
- privateExtendedProperty 
- q 
- sharedExtendedProperty 
- timeMin 
- timeMax 
- updatedMin All other query parameters should be the same as for the initial synchronization to avoid undefined behavior. If the syncToken expires, the server will respond with a 410 GONE response code and the client should clear its storage and perform a full synchronization without any syncToken.
Learn more about incremental synchronization.
Optional. The default is to return all entries. */
			def withSyncToken(syncToken: String) = new watch(req.addQueryStringParameters("syncToken" -> syncToken.toString))
			/** Upper bound (exclusive) for an event's start time to filter by. Optional. The default is not to filter by start time. Must be an RFC3339 timestamp with mandatory time zone offset, for example, 2011-06-03T10:00:00-07:00, 2011-06-03T10:00:00Z. Milliseconds may be provided but are ignored. If timeMin is set, timeMax must be greater than timeMin.<br>Format: date-time */
			def withTimeMax(timeMax: String) = new watch(req.addQueryStringParameters("timeMax" -> timeMax.toString))
			/** Lower bound (exclusive) for an event's end time to filter by. Optional. The default is not to filter by end time. Must be an RFC3339 timestamp with mandatory time zone offset, for example, 2011-06-03T10:00:00-07:00, 2011-06-03T10:00:00Z. Milliseconds may be provided but are ignored. If timeMax is set, timeMin must be smaller than timeMax.<br>Format: date-time */
			def withTimeMin(timeMin: String) = new watch(req.addQueryStringParameters("timeMin" -> timeMin.toString))
			/** Time zone used in the response. Optional. The default is the time zone of the calendar. */
			def withTimeZone(timeZone: String) = new watch(req.addQueryStringParameters("timeZone" -> timeZone.toString))
			/** Lower bound for an event's last modification time (as a RFC3339 timestamp) to filter by. When specified, entries deleted since this time will always be included regardless of showDeleted. Optional. The default is not to filter by last modification time.<br>Format: date-time */
			def withUpdatedMin(updatedMin: String) = new watch(req.addQueryStringParameters("updatedMin" -> updatedMin.toString))
			/** Perform the request */
			def withChannel(body: Schema.Channel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Channel])
		}
		object watch {
			def apply(alwaysIncludeEmail: Boolean, calendarId: String, privateExtendedProperty: String, sharedExtendedProperty: String)(using signer: RequestSigner, ec: ExecutionContext): watch = new watch(ws.url(BASE_URL + s"calendars/${calendarId}/events/watch").addQueryStringParameters("alwaysIncludeEmail" -> alwaysIncludeEmail.toString, "privateExtendedProperty" -> privateExtendedProperty.toString, "sharedExtendedProperty" -> sharedExtendedProperty.toString))
		}
		/** Returns instances of the specified recurring event. */
		class instances(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Events]) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.events""", """https://www.googleapis.com/auth/calendar.events.readonly""", """https://www.googleapis.com/auth/calendar.readonly""")
			/** The maximum number of attendees to include in the response. If there are more than the specified number of attendees, only the participant is returned. Optional.<br>Format: int32 */
			def withMaxAttendees(maxAttendees: Int) = new instances(req.addQueryStringParameters("maxAttendees" -> maxAttendees.toString))
			/** Maximum number of events returned on one result page. By default the value is 250 events. The page size can never be larger than 2500 events. Optional.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new instances(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** The original start time of the instance in the result. Optional. */
			def withOriginalStart(originalStart: String) = new instances(req.addQueryStringParameters("originalStart" -> originalStart.toString))
			/** Token specifying which result page to return. Optional. */
			def withPageToken(pageToken: String) = new instances(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Whether to include deleted events (with status equals "cancelled") in the result. Cancelled instances of recurring events will still be included if singleEvents is False. Optional. The default is False. */
			def withShowDeleted(showDeleted: Boolean) = new instances(req.addQueryStringParameters("showDeleted" -> showDeleted.toString))
			/** Upper bound (exclusive) for an event's start time to filter by. Optional. The default is not to filter by start time. Must be an RFC3339 timestamp with mandatory time zone offset.<br>Format: date-time */
			def withTimeMax(timeMax: String) = new instances(req.addQueryStringParameters("timeMax" -> timeMax.toString))
			/** Lower bound (inclusive) for an event's end time to filter by. Optional. The default is not to filter by end time. Must be an RFC3339 timestamp with mandatory time zone offset.<br>Format: date-time */
			def withTimeMin(timeMin: String) = new instances(req.addQueryStringParameters("timeMin" -> timeMin.toString))
			/** Time zone used in the response. Optional. The default is the time zone of the calendar. */
			def withTimeZone(timeZone: String) = new instances(req.addQueryStringParameters("timeZone" -> timeZone.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Events])
		}
		object instances {
			def apply(alwaysIncludeEmail: Boolean, calendarId: String, eventId: String)(using signer: RequestSigner, ec: ExecutionContext): instances = new instances(ws.url(BASE_URL + s"calendars/${calendarId}/events/${eventId}/instances").addQueryStringParameters("alwaysIncludeEmail" -> alwaysIncludeEmail.toString))
			given Conversion[instances, Future[Schema.Events]] = (fun: instances) => fun.apply()
		}
	}
	object freebusy {
		/** Returns free/busy information for a set of calendars. */
		class query(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.readonly""")
			/** Perform the request */
			def withFreeBusyRequest(body: Schema.FreeBusyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FreeBusyResponse])
		}
		object query {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): query = new query(ws.url(BASE_URL + s"freeBusy").addQueryStringParameters())
		}
	}
	object settings {
		/** Returns a single user setting. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Setting]) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.readonly""", """https://www.googleapis.com/auth/calendar.settings.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Setting])
		}
		object get {
			def apply(setting: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"users/me/settings/${setting}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Setting]] = (fun: get) => fun.apply()
		}
		/** Returns all user settings for the authenticated user. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Settings]) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.readonly""", """https://www.googleapis.com/auth/calendar.settings.readonly""")
			/** Maximum number of entries returned on one result page. By default the value is 100 entries. The page size can never be larger than 250 entries. Optional.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new list(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Token specifying which result page to return. Optional. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Token obtained from the nextSyncToken field returned on the last page of results from the previous list request. It makes the result of this list request contain only entries that have changed since then.
If the syncToken expires, the server will respond with a 410 GONE response code and the client should clear its storage and perform a full synchronization without any syncToken.
Learn more about incremental synchronization.
Optional. The default is to return all entries. */
			def withSyncToken(syncToken: String) = new list(req.addQueryStringParameters("syncToken" -> syncToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Settings])
		}
		object list {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"users/me/settings").addQueryStringParameters())
			given Conversion[list, Future[Schema.Settings]] = (fun: list) => fun.apply()
		}
		/** Watch for changes to Settings resources. */
		class watch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/calendar""", """https://www.googleapis.com/auth/calendar.readonly""", """https://www.googleapis.com/auth/calendar.settings.readonly""")
			/** Maximum number of entries returned on one result page. By default the value is 100 entries. The page size can never be larger than 250 entries. Optional.<br>Format: int32 */
			def withMaxResults(maxResults: Int) = new watch(req.addQueryStringParameters("maxResults" -> maxResults.toString))
			/** Token specifying which result page to return. Optional. */
			def withPageToken(pageToken: String) = new watch(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Token obtained from the nextSyncToken field returned on the last page of results from the previous list request. It makes the result of this list request contain only entries that have changed since then.
If the syncToken expires, the server will respond with a 410 GONE response code and the client should clear its storage and perform a full synchronization without any syncToken.
Learn more about incremental synchronization.
Optional. The default is to return all entries. */
			def withSyncToken(syncToken: String) = new watch(req.addQueryStringParameters("syncToken" -> syncToken.toString))
			/** Perform the request */
			def withChannel(body: Schema.Channel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Channel])
		}
		object watch {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): watch = new watch(ws.url(BASE_URL + s"users/me/settings/watch").addQueryStringParameters())
		}
	}
}
