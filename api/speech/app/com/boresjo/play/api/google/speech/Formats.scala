package com.boresjo.play.api.google.speech

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtCreatePhraseSetRequest: Format[Schema.CreatePhraseSetRequest] = Json.format[Schema.CreatePhraseSetRequest]
	given fmtPhraseSet: Format[Schema.PhraseSet] = Json.format[Schema.PhraseSet]
	given fmtPhrase: Format[Schema.Phrase] = Json.format[Schema.Phrase]
	given fmtPhraseSetStateEnum: Format[Schema.PhraseSet.StateEnum] = JsonEnumFormat[Schema.PhraseSet.StateEnum]
	given fmtListPhraseSetResponse: Format[Schema.ListPhraseSetResponse] = Json.format[Schema.ListPhraseSetResponse]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtCreateCustomClassRequest: Format[Schema.CreateCustomClassRequest] = Json.format[Schema.CreateCustomClassRequest]
	given fmtCustomClass: Format[Schema.CustomClass] = Json.format[Schema.CustomClass]
	given fmtClassItem: Format[Schema.ClassItem] = Json.format[Schema.ClassItem]
	given fmtCustomClassStateEnum: Format[Schema.CustomClass.StateEnum] = JsonEnumFormat[Schema.CustomClass.StateEnum]
	given fmtListCustomClassesResponse: Format[Schema.ListCustomClassesResponse] = Json.format[Schema.ListCustomClassesResponse]
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtRecognizeRequest: Format[Schema.RecognizeRequest] = Json.format[Schema.RecognizeRequest]
	given fmtRecognitionConfig: Format[Schema.RecognitionConfig] = Json.format[Schema.RecognitionConfig]
	given fmtRecognitionAudio: Format[Schema.RecognitionAudio] = Json.format[Schema.RecognitionAudio]
	given fmtRecognitionConfigEncodingEnum: Format[Schema.RecognitionConfig.EncodingEnum] = JsonEnumFormat[Schema.RecognitionConfig.EncodingEnum]
	given fmtSpeechAdaptation: Format[Schema.SpeechAdaptation] = Json.format[Schema.SpeechAdaptation]
	given fmtTranscriptNormalization: Format[Schema.TranscriptNormalization] = Json.format[Schema.TranscriptNormalization]
	given fmtSpeechContext: Format[Schema.SpeechContext] = Json.format[Schema.SpeechContext]
	given fmtSpeakerDiarizationConfig: Format[Schema.SpeakerDiarizationConfig] = Json.format[Schema.SpeakerDiarizationConfig]
	given fmtRecognitionMetadata: Format[Schema.RecognitionMetadata] = Json.format[Schema.RecognitionMetadata]
	given fmtABNFGrammar: Format[Schema.ABNFGrammar] = Json.format[Schema.ABNFGrammar]
	given fmtEntry: Format[Schema.Entry] = Json.format[Schema.Entry]
	given fmtRecognitionMetadataInteractionTypeEnum: Format[Schema.RecognitionMetadata.InteractionTypeEnum] = JsonEnumFormat[Schema.RecognitionMetadata.InteractionTypeEnum]
	given fmtRecognitionMetadataMicrophoneDistanceEnum: Format[Schema.RecognitionMetadata.MicrophoneDistanceEnum] = JsonEnumFormat[Schema.RecognitionMetadata.MicrophoneDistanceEnum]
	given fmtRecognitionMetadataOriginalMediaTypeEnum: Format[Schema.RecognitionMetadata.OriginalMediaTypeEnum] = JsonEnumFormat[Schema.RecognitionMetadata.OriginalMediaTypeEnum]
	given fmtRecognitionMetadataRecordingDeviceTypeEnum: Format[Schema.RecognitionMetadata.RecordingDeviceTypeEnum] = JsonEnumFormat[Schema.RecognitionMetadata.RecordingDeviceTypeEnum]
	given fmtRecognizeResponse: Format[Schema.RecognizeResponse] = Json.format[Schema.RecognizeResponse]
	given fmtSpeechRecognitionResult: Format[Schema.SpeechRecognitionResult] = Json.format[Schema.SpeechRecognitionResult]
	given fmtSpeechAdaptationInfo: Format[Schema.SpeechAdaptationInfo] = Json.format[Schema.SpeechAdaptationInfo]
	given fmtSpeechRecognitionAlternative: Format[Schema.SpeechRecognitionAlternative] = Json.format[Schema.SpeechRecognitionAlternative]
	given fmtWordInfo: Format[Schema.WordInfo] = Json.format[Schema.WordInfo]
	given fmtLongRunningRecognizeRequest: Format[Schema.LongRunningRecognizeRequest] = Json.format[Schema.LongRunningRecognizeRequest]
	given fmtTranscriptOutputConfig: Format[Schema.TranscriptOutputConfig] = Json.format[Schema.TranscriptOutputConfig]
	given fmtLongRunningRecognizeMetadata: Format[Schema.LongRunningRecognizeMetadata] = Json.format[Schema.LongRunningRecognizeMetadata]
	given fmtLongRunningRecognizeResponse: Format[Schema.LongRunningRecognizeResponse] = Json.format[Schema.LongRunningRecognizeResponse]
}
