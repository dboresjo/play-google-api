package com.boresjo.play.api.google.meet

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListParticipantsResponse(
	  /** Token to be circulated back for further List call if current List doesn't include all the participants. Unset if all participants are returned. */
		nextPageToken: Option[String] = None,
	  /** List of participants in one page. */
		participants: Option[List[Schema.Participant]] = None,
	  /** Total, exact number of `participants`. By default, this field isn't included in the response. Set the field mask in [SystemParameterContext](https://cloud.google.com/apis/docs/system-parameters) to receive this field in the response. */
		totalSize: Option[Int] = None
	)
	
	case class ListParticipantSessionsResponse(
	  /** List of participants in one page. */
		participantSessions: Option[List[Schema.ParticipantSession]] = None,
	  /** Token to be circulated back for further List call if current List doesn't include all the participants. Unset if all participants are returned. */
		nextPageToken: Option[String] = None
	)
	
	case class ActiveConference(
	  /** Output only. Reference to 'ConferenceRecord' resource. Format: `conferenceRecords/{conference_record}` where `{conference_record}` is a unique ID for each instance of a call within a space. */
		conferenceRecord: Option[String] = None
	)
	
	object Recording {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STARTED, ENDED, FILE_GENERATED }
	}
	case class Recording(
	  /** Output only. Timestamp when the recording ended. */
		endTime: Option[String] = None,
	  /** Output only. Current state. */
		state: Option[Schema.Recording.StateEnum] = None,
	  /** Output only. Timestamp when the recording started. */
		startTime: Option[String] = None,
	  /** Output only. Recording is saved to Google Drive as an MP4 file. The `drive_destination` includes the Drive `fileId` that can be used to download the file using the `files.get` method of the Drive API. */
		driveDestination: Option[Schema.DriveDestination] = None,
	  /** Output only. Resource name of the recording. Format: `conferenceRecords/{conference_record}/recordings/{recording}` where `{recording}` is a 1:1 mapping to each unique recording session during the conference. */
		name: Option[String] = None
	)
	
	case class Participant(
	  /** Signed-in user. */
		signedinUser: Option[Schema.SignedinUser] = None,
	  /** Output only. Time when the participant first joined the meeting. */
		earliestStartTime: Option[String] = None,
	  /** Anonymous user. */
		anonymousUser: Option[Schema.AnonymousUser] = None,
	  /** User calling from their phone. */
		phoneUser: Option[Schema.PhoneUser] = None,
	  /** Output only. Resource name of the participant. Format: `conferenceRecords/{conference_record}/participants/{participant}` */
		name: Option[String] = None,
	  /** Output only. Time when the participant left the meeting for the last time. This can be null if it's an active meeting. */
		latestEndTime: Option[String] = None
	)
	
	case class DocsDestination(
	  /** Output only. The document ID for the underlying Google Docs transcript file. For example, "1kuceFZohVoCh6FulBHxwy6I15Ogpc4hP". Use the `documents.get` method of the Google Docs API (https://developers.google.com/docs/api/reference/rest/v1/documents/get) to fetch the content. */
		document: Option[String] = None,
	  /** Output only. URI for the Google Docs transcript file. Use `https://docs.google.com/document/d/{$DocumentId}/view` to browse the transcript in the browser. */
		exportUri: Option[String] = None
	)
	
	case class ListTranscriptEntriesResponse(
	  /** Token to be circulated back for further List call if current List doesn't include all the transcript entries. Unset if all entries are returned. */
		nextPageToken: Option[String] = None,
	  /** List of TranscriptEntries in one page. */
		transcriptEntries: Option[List[Schema.TranscriptEntry]] = None
	)
	
	case class SignedinUser(
	  /** Output only. Unique ID for the user. Interoperable with Admin SDK API and People API. Format: `users/{user}` */
		user: Option[String] = None,
	  /** Output only. For a personal device, it's the user's first name and last name. For a robot account, it's the administrator-specified device name. For example, "Altostrat Room". */
		displayName: Option[String] = None
	)
	
	case class PhoneUser(
	  /** Output only. Partially redacted user's phone number when calling. */
		displayName: Option[String] = None
	)
	
	case class ListConferenceRecordsResponse(
	  /** Token to be circulated back for further List call if current List does NOT include all the Conferences. Unset if all conferences have been returned. */
		nextPageToken: Option[String] = None,
	  /** List of conferences in one page. */
		conferenceRecords: Option[List[Schema.ConferenceRecord]] = None
	)
	
	case class ListRecordingsResponse(
	  /** Token to be circulated back for further List call if current List doesn't include all the recordings. Unset if all recordings are returned. */
		nextPageToken: Option[String] = None,
	  /** List of recordings in one page. */
		recordings: Option[List[Schema.Recording]] = None
	)
	
	case class DriveDestination(
	  /** Output only. Link used to play back the recording file in the browser. For example, `https://drive.google.com/file/d/{$fileId}/view`. */
		exportUri: Option[String] = None,
	  /** Output only. The `fileId` for the underlying MP4 file. For example, "1kuceFZohVoCh6FulBHxwy6I15Ogpc4hP". Use `$ GET https://www.googleapis.com/drive/v3/files/{$fileId}?alt=media` to download the blob. For more information, see https://developers.google.com/drive/api/v3/reference/files/get. */
		file: Option[String] = None
	)
	
	object Transcript {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STARTED, ENDED, FILE_GENERATED }
	}
	case class Transcript(
	  /** Output only. Timestamp when the transcript started. */
		startTime: Option[String] = None,
	  /** Output only. Where the Google Docs transcript is saved. */
		docsDestination: Option[Schema.DocsDestination] = None,
	  /** Output only. Current state. */
		state: Option[Schema.Transcript.StateEnum] = None,
	  /** Output only. Timestamp when the transcript stopped. */
		endTime: Option[String] = None,
	  /** Output only. Resource name of the transcript. Format: `conferenceRecords/{conference_record}/transcripts/{transcript}`, where `{transcript}` is a 1:1 mapping to each unique transcription session of the conference. */
		name: Option[String] = None
	)
	
	case class EndActiveConferenceRequest(
	
	)
	
	case class AnonymousUser(
	  /** Output only. User provided name when they join a conference anonymously. */
		displayName: Option[String] = None
	)
	
	case class ConferenceRecord(
	  /** Output only. Timestamp when the conference ended. Set for past conferences. Unset if the conference is ongoing. */
		endTime: Option[String] = None,
	  /** Output only. The space where the conference was held. */
		space: Option[String] = None,
	  /** Output only. Server enforced expiration time for when this conference record resource is deleted. The resource is deleted 30 days after the conference ends. */
		expireTime: Option[String] = None,
	  /** Identifier. Resource name of the conference record. Format: `conferenceRecords/{conference_record}` where `{conference_record}` is a unique ID for each instance of a call within a space. */
		name: Option[String] = None,
	  /** Output only. Timestamp when the conference started. Always set. */
		startTime: Option[String] = None
	)
	
	object SpaceConfig {
		enum EntryPointAccessEnum extends Enum[EntryPointAccessEnum] { case ENTRY_POINT_ACCESS_UNSPECIFIED, ALL, CREATOR_APP_ONLY }
		enum AccessTypeEnum extends Enum[AccessTypeEnum] { case ACCESS_TYPE_UNSPECIFIED, OPEN, TRUSTED, RESTRICTED }
	}
	case class SpaceConfig(
	  /** Defines the entry points that can be used to join meetings hosted in this meeting space. Default: EntryPointAccess.ALL */
		entryPointAccess: Option[Schema.SpaceConfig.EntryPointAccessEnum] = None,
	  /** Access type of the meeting space that determines who can join without knocking. Default: The user's default access settings. Controlled by the user's admin for enterprise users or RESTRICTED. */
		accessType: Option[Schema.SpaceConfig.AccessTypeEnum] = None
	)
	
	case class Space(
	  /** Active conference, if it exists. */
		activeConference: Option[Schema.ActiveConference] = None,
	  /** Configuration pertaining to the meeting space. */
		config: Option[Schema.SpaceConfig] = None,
	  /** Output only. URI used to join meetings consisting of `https://meet.google.com/` followed by the `meeting_code`. For example, `https://meet.google.com/abc-mnop-xyz`. */
		meetingUri: Option[String] = None,
	  /** Immutable. Resource name of the space. Format: `spaces/{space}`. `{space}` is the resource identifier for the space. It's a unique, server-generated ID and is case sensitive. For example, `jQCFfuBOdN5z`. For more information, see [How Meet identifies a meeting space](https://developers.google.com/meet/api/guides/meeting-spaces#identify-meeting-space). */
		name: Option[String] = None,
	  /** Output only. Type friendly unique string used to join the meeting. Format: `[a-z]+-[a-z]+-[a-z]+`. For example, `abc-mnop-xyz`. The maximum length is 128 characters. Can only be used as an alias of the space name to get the space. */
		meetingCode: Option[String] = None
	)
	
	case class ParticipantSession(
	  /** Output only. Timestamp when the user session starts. */
		startTime: Option[String] = None,
	  /** Output only. Timestamp when the user session ends. Unset if the user session hasn’t ended. */
		endTime: Option[String] = None,
	  /** Identifier. Session id. */
		name: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class ListTranscriptsResponse(
	  /** List of transcripts in one page. */
		transcripts: Option[List[Schema.Transcript]] = None,
	  /** Token to be circulated back for further List call if current List doesn't include all the transcripts. Unset if all transcripts are returned. */
		nextPageToken: Option[String] = None
	)
	
	case class TranscriptEntry(
	  /** Output only. Timestamp when the transcript entry ended. */
		endTime: Option[String] = None,
	  /** Output only. Resource name of the entry. Format: "conferenceRecords/{conference_record}/transcripts/{transcript}/entries/{entry}" */
		name: Option[String] = None,
	  /** Output only. Refers to the participant who speaks. */
		participant: Option[String] = None,
	  /** Output only. Timestamp when the transcript entry started. */
		startTime: Option[String] = None,
	  /** Output only. Language of spoken text, such as "en-US". IETF BCP 47 syntax (https://tools.ietf.org/html/bcp47) */
		languageCode: Option[String] = None,
	  /** Output only. The transcribed text of the participant's voice, at maximum 10K words. Note that the limit is subject to change. */
		text: Option[String] = None
	)
}
