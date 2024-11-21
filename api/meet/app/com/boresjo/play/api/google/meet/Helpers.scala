package com.boresjo.play.api.google.meet

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaParticipant: Conversion[List[Schema.Participant], Option[List[Schema.Participant]]] = (fun: List[Schema.Participant]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaParticipantSession: Conversion[List[Schema.ParticipantSession], Option[List[Schema.ParticipantSession]]] = (fun: List[Schema.ParticipantSession]) => Option(fun)
		given putSchemaRecordingStateEnum: Conversion[Schema.Recording.StateEnum, Option[Schema.Recording.StateEnum]] = (fun: Schema.Recording.StateEnum) => Option(fun)
		given putSchemaDriveDestination: Conversion[Schema.DriveDestination, Option[Schema.DriveDestination]] = (fun: Schema.DriveDestination) => Option(fun)
		given putSchemaSignedinUser: Conversion[Schema.SignedinUser, Option[Schema.SignedinUser]] = (fun: Schema.SignedinUser) => Option(fun)
		given putSchemaAnonymousUser: Conversion[Schema.AnonymousUser, Option[Schema.AnonymousUser]] = (fun: Schema.AnonymousUser) => Option(fun)
		given putSchemaPhoneUser: Conversion[Schema.PhoneUser, Option[Schema.PhoneUser]] = (fun: Schema.PhoneUser) => Option(fun)
		given putListSchemaTranscriptEntry: Conversion[List[Schema.TranscriptEntry], Option[List[Schema.TranscriptEntry]]] = (fun: List[Schema.TranscriptEntry]) => Option(fun)
		given putListSchemaConferenceRecord: Conversion[List[Schema.ConferenceRecord], Option[List[Schema.ConferenceRecord]]] = (fun: List[Schema.ConferenceRecord]) => Option(fun)
		given putListSchemaRecording: Conversion[List[Schema.Recording], Option[List[Schema.Recording]]] = (fun: List[Schema.Recording]) => Option(fun)
		given putSchemaDocsDestination: Conversion[Schema.DocsDestination, Option[Schema.DocsDestination]] = (fun: Schema.DocsDestination) => Option(fun)
		given putSchemaTranscriptStateEnum: Conversion[Schema.Transcript.StateEnum, Option[Schema.Transcript.StateEnum]] = (fun: Schema.Transcript.StateEnum) => Option(fun)
		given putSchemaSpaceConfigEntryPointAccessEnum: Conversion[Schema.SpaceConfig.EntryPointAccessEnum, Option[Schema.SpaceConfig.EntryPointAccessEnum]] = (fun: Schema.SpaceConfig.EntryPointAccessEnum) => Option(fun)
		given putSchemaSpaceConfigAccessTypeEnum: Conversion[Schema.SpaceConfig.AccessTypeEnum, Option[Schema.SpaceConfig.AccessTypeEnum]] = (fun: Schema.SpaceConfig.AccessTypeEnum) => Option(fun)
		given putSchemaActiveConference: Conversion[Schema.ActiveConference, Option[Schema.ActiveConference]] = (fun: Schema.ActiveConference) => Option(fun)
		given putSchemaSpaceConfig: Conversion[Schema.SpaceConfig, Option[Schema.SpaceConfig]] = (fun: Schema.SpaceConfig) => Option(fun)
		given putListSchemaTranscript: Conversion[List[Schema.Transcript], Option[List[Schema.Transcript]]] = (fun: List[Schema.Transcript]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
