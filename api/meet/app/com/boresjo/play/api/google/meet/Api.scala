package com.boresjo.play.api.google.meet

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
		"""https://www.googleapis.com/auth/meetings.space.readonly""" /* Read information about any of your Google Meet conferences */,
		"""https://www.googleapis.com/auth/meetings.space.created""" /* Create, edit, and see information about your Google Meet conferences created by the app. */
	)

	private val BASE_URL = "https://meet.googleapis.com/"

	object conferenceRecords {
		/** Gets a conference record by conference ID. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ConferenceRecord]) {
			val scopes = Seq("""https://www.googleapis.com/auth/meetings.space.created""", """https://www.googleapis.com/auth/meetings.space.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ConferenceRecord])
		}
		object get {
			def apply(conferenceRecordsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.ConferenceRecord]] = (fun: get) => fun.apply()
		}
		/** Lists the conference records. By default, ordered by start time and in descending order. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListConferenceRecordsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/meetings.space.created""", """https://www.googleapis.com/auth/meetings.space.readonly""")
			/** Optional. User specified filtering condition in [EBNF format](https://en.wikipedia.org/wiki/Extended_Backus%E2%80%93Naur_form). The following are the filterable fields: &#42; `space.meeting_code` &#42; `space.name` &#42; `start_time` &#42; `end_time` For example, consider the following filters: &#42; `space.name = "spaces/NAME"` &#42; `space.meeting_code = "abc-mnop-xyz"` &#42; `start_time>="2024-01-01T00:00:00.000Z" AND start_time<="2024-01-02T00:00:00.000Z"` &#42; `end_time IS NULL` */
			def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
			/** Optional. Maximum number of conference records to return. The service might return fewer than this value. If unspecified, at most 25 conference records are returned. The maximum value is 100; values above 100 are coerced to 100. Maximum might change in the future.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. Page token returned from previous List Call. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListConferenceRecordsResponse])
		}
		object list {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/conferenceRecords").addQueryStringParameters())
			given Conversion[list, Future[Schema.ListConferenceRecordsResponse]] = (fun: list) => fun.apply()
		}
		object participants {
			/** Lists the participants in a conference record. By default, ordered by join time and in descending order. This API supports `fields` as standard parameters like every other API. However, when the `fields` request parameter is omitted, this API defaults to `'participants/&#42;, next_page_token'`. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListParticipantsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/meetings.space.created""", """https://www.googleapis.com/auth/meetings.space.readonly""")
				/** Optional. User specified filtering condition in [EBNF format](https://en.wikipedia.org/wiki/Extended_Backus%E2%80%93Naur_form). The following are the filterable fields: &#42; `earliest_start_time` &#42; `latest_end_time` For example, `latest_end_time IS NULL` returns active participants in the conference. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListParticipantsResponse])
			}
			object list {
				def apply(conferenceRecordsId :PlayApi, pageSize: Int, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}/participants").addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListParticipantsResponse]] = (fun: list) => fun.apply()
			}
			/** Gets a participant by participant ID. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Participant]) {
				val scopes = Seq("""https://www.googleapis.com/auth/meetings.space.created""", """https://www.googleapis.com/auth/meetings.space.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Participant])
			}
			object get {
				def apply(conferenceRecordsId :PlayApi, participantsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}/participants/${participantsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Participant]] = (fun: get) => fun.apply()
			}
			object participantSessions {
				/** Gets a participant session by participant session ID. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ParticipantSession]) {
					val scopes = Seq("""https://www.googleapis.com/auth/meetings.space.created""", """https://www.googleapis.com/auth/meetings.space.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ParticipantSession])
				}
				object get {
					def apply(conferenceRecordsId :PlayApi, participantsId :PlayApi, participantSessionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}/participants/${participantsId}/participantSessions/${participantSessionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ParticipantSession]] = (fun: get) => fun.apply()
				}
				/** Lists the participant sessions of a participant in a conference record. By default, ordered by join time and in descending order. This API supports `fields` as standard parameters like every other API. However, when the `fields` request parameter is omitted this API defaults to `'participantsessions/&#42;, next_page_token'`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListParticipantSessionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/meetings.space.created""", """https://www.googleapis.com/auth/meetings.space.readonly""")
					/** Optional. Maximum number of participant sessions to return. The service might return fewer than this value. If unspecified, at most 100 participants are returned. The maximum value is 250; values above 250 are coerced to 250. Maximum might change in the future.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. User specified filtering condition in [EBNF format](https://en.wikipedia.org/wiki/Extended_Backus%E2%80%93Naur_form). The following are the filterable fields: &#42; `start_time` &#42; `end_time` For example, `end_time IS NULL` returns active participant sessions in the conference record. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. Page token returned from previous List Call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListParticipantSessionsResponse])
				}
				object list {
					def apply(conferenceRecordsId :PlayApi, participantsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}/participants/${participantsId}/participantSessions").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListParticipantSessionsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object transcripts {
			/** Gets a transcript by transcript ID. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Transcript]) {
				val scopes = Seq("""https://www.googleapis.com/auth/meetings.space.created""", """https://www.googleapis.com/auth/meetings.space.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Transcript])
			}
			object get {
				def apply(conferenceRecordsId :PlayApi, transcriptsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}/transcripts/${transcriptsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Transcript]] = (fun: get) => fun.apply()
			}
			/** Lists the set of transcripts from the conference record. By default, ordered by start time and in ascending order. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTranscriptsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/meetings.space.created""", """https://www.googleapis.com/auth/meetings.space.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTranscriptsResponse])
			}
			object list {
				def apply(conferenceRecordsId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}/transcripts").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListTranscriptsResponse]] = (fun: list) => fun.apply()
			}
			object entries {
				/** Gets a `TranscriptEntry` resource by entry ID. Note: The transcript entries returned by the Google Meet API might not match the transcription found in the Google Docs transcript file. This can occur when the Google Docs transcript file is modified after generation. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.TranscriptEntry]) {
					val scopes = Seq("""https://www.googleapis.com/auth/meetings.space.created""", """https://www.googleapis.com/auth/meetings.space.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.TranscriptEntry])
				}
				object get {
					def apply(conferenceRecordsId :PlayApi, transcriptsId :PlayApi, entriesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}/transcripts/${transcriptsId}/entries/${entriesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.TranscriptEntry]] = (fun: get) => fun.apply()
				}
				/** Lists the structured transcript entries per transcript. By default, ordered by start time and in ascending order. Note: The transcript entries returned by the Google Meet API might not match the transcription found in the Google Docs transcript file. This can occur when the Google Docs transcript file is modified after generation. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListTranscriptEntriesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/meetings.space.created""", """https://www.googleapis.com/auth/meetings.space.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListTranscriptEntriesResponse])
				}
				object list {
					def apply(conferenceRecordsId :PlayApi, transcriptsId :PlayApi, pageSize: Int, pageToken: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}/transcripts/${transcriptsId}/entries").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListTranscriptEntriesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object recordings {
			/** Gets a recording by recording ID. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Recording]) {
				val scopes = Seq("""https://www.googleapis.com/auth/meetings.space.created""", """https://www.googleapis.com/auth/meetings.space.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Recording])
			}
			object get {
				def apply(conferenceRecordsId :PlayApi, recordingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}/recordings/${recordingsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Recording]] = (fun: get) => fun.apply()
			}
			/** Lists the recording resources from the conference record. By default, ordered by start time and in ascending order. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListRecordingsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/meetings.space.created""", """https://www.googleapis.com/auth/meetings.space.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListRecordingsResponse])
			}
			object list {
				def apply(conferenceRecordsId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/conferenceRecords/${conferenceRecordsId}/recordings").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListRecordingsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object spaces {
		/** Creates a space. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/meetings.space.created""")
			/** Perform the request */
			def withSpace(body: Schema.Space) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Space])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/spaces").addQueryStringParameters())
		}
		/** Gets details about a meeting space. For an example, see [Get a meeting space](https://developers.google.com/meet/api/guides/meeting-spaces#get-meeting-space). */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Space]) {
			val scopes = Seq("""https://www.googleapis.com/auth/meetings.space.created""", """https://www.googleapis.com/auth/meetings.space.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Space])
		}
		object get {
			def apply(spacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/spaces/${spacesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Space]] = (fun: get) => fun.apply()
		}
		/** Ends an active conference (if there's one). For an example, see [End active conference](https://developers.google.com/meet/api/guides/meeting-spaces#end-active-conference). */
		class endActiveConference(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/meetings.space.created""")
			/** Perform the request */
			def withEndActiveConferenceRequest(body: Schema.EndActiveConferenceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object endActiveConference {
			def apply(spacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): endActiveConference = new endActiveConference(ws.url(BASE_URL + s"v2/spaces/${spacesId}:endActiveConference").addQueryStringParameters("name" -> name.toString))
		}
		/** Updates details about a meeting space. For an example, see [Update a meeting space](https://developers.google.com/meet/api/guides/meeting-spaces#update-meeting-space). */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/meetings.space.created""")
			/** Optional. Field mask used to specify the fields to be updated in the space. If update_mask isn't provided(not set, set with empty paths, or only has "" as paths), it defaults to update all fields provided with values in the request. Using "&#42;" as update_mask will update all fields, including deleting fields not set in the request.<br>Format: google-fieldmask */
			def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
			/** Perform the request */
			def withSpace(body: Schema.Space) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Space])
		}
		object patch {
			def apply(spacesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/spaces/${spacesId}").addQueryStringParameters("name" -> name.toString))
		}
	}
}
