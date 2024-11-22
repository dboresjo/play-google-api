package com.boresjo.play.api.google.meet

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://meet.googleapis.com/"

	object conferenceRecords {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ConferenceRecord]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ConferenceRecord])
		}
		object get {
			def apply(conferenceRecordsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.ConferenceRecord]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListConferenceRecordsResponse]) {
			/** Optional. User specified filtering condition in [EBNF format](https://en.wikipedia.org/wiki/Extended_Backus%E2%80%93Naur_form). The following are the filterable fields: &#42; `space.meeting_code` &#42; `space.name` &#42; `start_time` &#42; `end_time` For example, consider the following filters: &#42; `space.name = "spaces/NAME"` &#42; `space.meeting_code = "abc-mnop-xyz"` &#42; `start_time>="2024-01-01T00:00:00.000Z" AND start_time<="2024-01-02T00:00:00.000Z"` &#42; `end_time IS NULL` */
			def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
			/** Optional. Maximum number of conference records to return. The service might return fewer than this value. If unspecified, at most 25 conference records are returned. The maximum value is 100; values above 100 are coerced to 100. Maximum might change in the future.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. Page token returned from previous List Call. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListConferenceRecordsResponse])
		}
		object list {
			def apply()(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/conferenceRecords").addQueryStringParameters())
			given Conversion[list, Future[Schema.ListConferenceRecordsResponse]] = (fun: list) => fun.apply()
		}
		object participants {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListParticipantsResponse]) {
				/** Optional. User specified filtering condition in [EBNF format](https://en.wikipedia.org/wiki/Extended_Backus%E2%80%93Naur_form). The following are the filterable fields: &#42; `earliest_start_time` &#42; `latest_end_time` For example, `latest_end_time IS NULL` returns active participants in the conference. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListParticipantsResponse])
			}
			object list {
				def apply(conferenceRecordsId :PlayApi, pageSize: Int, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}/participants").addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListParticipantsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Participant]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Participant])
			}
			object get {
				def apply(conferenceRecordsId :PlayApi, participantsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}/participants/${participantsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Participant]] = (fun: get) => fun.apply()
			}
			object participantSessions {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ParticipantSession]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ParticipantSession])
				}
				object get {
					def apply(conferenceRecordsId :PlayApi, participantsId :PlayApi, participantSessionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}/participants/${participantsId}/participantSessions/${participantSessionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ParticipantSession]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListParticipantSessionsResponse]) {
					/** Optional. Maximum number of participant sessions to return. The service might return fewer than this value. If unspecified, at most 100 participants are returned. The maximum value is 250; values above 250 are coerced to 250. Maximum might change in the future.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. User specified filtering condition in [EBNF format](https://en.wikipedia.org/wiki/Extended_Backus%E2%80%93Naur_form). The following are the filterable fields: &#42; `start_time` &#42; `end_time` For example, `end_time IS NULL` returns active participant sessions in the conference record. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Page token returned from previous List Call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListParticipantSessionsResponse])
				}
				object list {
					def apply(conferenceRecordsId :PlayApi, participantsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}/participants/${participantsId}/participantSessions").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListParticipantSessionsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object transcripts {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Transcript]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Transcript])
			}
			object get {
				def apply(conferenceRecordsId :PlayApi, transcriptsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}/transcripts/${transcriptsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Transcript]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTranscriptsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListTranscriptsResponse])
			}
			object list {
				def apply(conferenceRecordsId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}/transcripts").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListTranscriptsResponse]] = (fun: list) => fun.apply()
			}
			object entries {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.TranscriptEntry]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.TranscriptEntry])
				}
				object get {
					def apply(conferenceRecordsId :PlayApi, transcriptsId :PlayApi, entriesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}/transcripts/${transcriptsId}/entries/${entriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.TranscriptEntry]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListTranscriptEntriesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListTranscriptEntriesResponse])
				}
				object list {
					def apply(conferenceRecordsId :PlayApi, transcriptsId :PlayApi, pageSize: Int, pageToken: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}/transcripts/${transcriptsId}/entries").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListTranscriptEntriesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object recordings {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Recording]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Recording])
			}
			object get {
				def apply(conferenceRecordsId :PlayApi, recordingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}/recordings/${recordingsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Recording]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListRecordingsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListRecordingsResponse])
			}
			object list {
				def apply(conferenceRecordsId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}/recordings").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListRecordingsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object spaces {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSpace(body: Schema.Space) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Space])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/spaces").addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Space]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Space])
		}
		object get {
			def apply(spacesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/spaces/${spacesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Space]] = (fun: get) => fun.apply()
		}
		class endActiveConference(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withEndActiveConferenceRequest(body: Schema.EndActiveConferenceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object endActiveConference {
			def apply(spacesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): endActiveConference = new endActiveConference(ws.url(BASE_URL + s"v2/spaces/${spacesId}:endActiveConference").addQueryStringParameters("name" -> name.toString))
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. Field mask used to specify the fields to be updated in the space. If update_mask isn't provided(not set, set with empty paths, or only has "" as paths), it defaults to update all fields provided with values in the request. Using "&#42;" as update_mask will update all fields, including deleting fields not set in the request.<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			def withSpace(body: Schema.Space) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Space])
		}
		object patch {
			def apply(spacesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/spaces/${spacesId}").addQueryStringParameters("name" -> name.toString))
		}
	}
}
