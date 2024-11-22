package com.boresjo.play.api.google.meet

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListParticipantsResponse: Format[Schema.ListParticipantsResponse] = Json.format[Schema.ListParticipantsResponse]
	given fmtParticipant: Format[Schema.Participant] = Json.format[Schema.Participant]
	given fmtListParticipantSessionsResponse: Format[Schema.ListParticipantSessionsResponse] = Json.format[Schema.ListParticipantSessionsResponse]
	given fmtParticipantSession: Format[Schema.ParticipantSession] = Json.format[Schema.ParticipantSession]
	given fmtActiveConference: Format[Schema.ActiveConference] = Json.format[Schema.ActiveConference]
	given fmtRecording: Format[Schema.Recording] = Json.format[Schema.Recording]
	given fmtRecordingStateEnum: Format[Schema.Recording.StateEnum] = JsonEnumFormat[Schema.Recording.StateEnum]
	given fmtDriveDestination: Format[Schema.DriveDestination] = Json.format[Schema.DriveDestination]
	given fmtSignedinUser: Format[Schema.SignedinUser] = Json.format[Schema.SignedinUser]
	given fmtAnonymousUser: Format[Schema.AnonymousUser] = Json.format[Schema.AnonymousUser]
	given fmtPhoneUser: Format[Schema.PhoneUser] = Json.format[Schema.PhoneUser]
	given fmtDocsDestination: Format[Schema.DocsDestination] = Json.format[Schema.DocsDestination]
	given fmtListTranscriptEntriesResponse: Format[Schema.ListTranscriptEntriesResponse] = Json.format[Schema.ListTranscriptEntriesResponse]
	given fmtTranscriptEntry: Format[Schema.TranscriptEntry] = Json.format[Schema.TranscriptEntry]
	given fmtListConferenceRecordsResponse: Format[Schema.ListConferenceRecordsResponse] = Json.format[Schema.ListConferenceRecordsResponse]
	given fmtConferenceRecord: Format[Schema.ConferenceRecord] = Json.format[Schema.ConferenceRecord]
	given fmtListRecordingsResponse: Format[Schema.ListRecordingsResponse] = Json.format[Schema.ListRecordingsResponse]
	given fmtTranscript: Format[Schema.Transcript] = Json.format[Schema.Transcript]
	given fmtTranscriptStateEnum: Format[Schema.Transcript.StateEnum] = JsonEnumFormat[Schema.Transcript.StateEnum]
	given fmtEndActiveConferenceRequest: Format[Schema.EndActiveConferenceRequest] = Json.format[Schema.EndActiveConferenceRequest]
	given fmtSpaceConfig: Format[Schema.SpaceConfig] = Json.format[Schema.SpaceConfig]
	given fmtSpaceConfigEntryPointAccessEnum: Format[Schema.SpaceConfig.EntryPointAccessEnum] = JsonEnumFormat[Schema.SpaceConfig.EntryPointAccessEnum]
	given fmtSpaceConfigAccessTypeEnum: Format[Schema.SpaceConfig.AccessTypeEnum] = JsonEnumFormat[Schema.SpaceConfig.AccessTypeEnum]
	given fmtSpace: Format[Schema.Space] = Json.format[Schema.Space]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtListTranscriptsResponse: Format[Schema.ListTranscriptsResponse] = Json.format[Schema.ListTranscriptsResponse]
}
