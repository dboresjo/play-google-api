package com.boresjo.play.api.google.texttospeech

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListVoicesResponse: Format[Schema.ListVoicesResponse] = Json.format[Schema.ListVoicesResponse]
	given fmtVoice: Format[Schema.Voice] = Json.format[Schema.Voice]
	given fmtVoiceSsmlGenderEnum: Format[Schema.Voice.SsmlGenderEnum] = JsonEnumFormat[Schema.Voice.SsmlGenderEnum]
	given fmtSynthesizeSpeechRequest: Format[Schema.SynthesizeSpeechRequest] = Json.format[Schema.SynthesizeSpeechRequest]
	given fmtSynthesisInput: Format[Schema.SynthesisInput] = Json.format[Schema.SynthesisInput]
	given fmtVoiceSelectionParams: Format[Schema.VoiceSelectionParams] = Json.format[Schema.VoiceSelectionParams]
	given fmtAudioConfig: Format[Schema.AudioConfig] = Json.format[Schema.AudioConfig]
	given fmtAdvancedVoiceOptions: Format[Schema.AdvancedVoiceOptions] = Json.format[Schema.AdvancedVoiceOptions]
	given fmtMultiSpeakerMarkup: Format[Schema.MultiSpeakerMarkup] = Json.format[Schema.MultiSpeakerMarkup]
	given fmtCustomPronunciations: Format[Schema.CustomPronunciations] = Json.format[Schema.CustomPronunciations]
	given fmtTurn: Format[Schema.Turn] = Json.format[Schema.Turn]
	given fmtCustomPronunciationParams: Format[Schema.CustomPronunciationParams] = Json.format[Schema.CustomPronunciationParams]
	given fmtCustomPronunciationParamsPhoneticEncodingEnum: Format[Schema.CustomPronunciationParams.PhoneticEncodingEnum] = JsonEnumFormat[Schema.CustomPronunciationParams.PhoneticEncodingEnum]
	given fmtVoiceSelectionParamsSsmlGenderEnum: Format[Schema.VoiceSelectionParams.SsmlGenderEnum] = JsonEnumFormat[Schema.VoiceSelectionParams.SsmlGenderEnum]
	given fmtCustomVoiceParams: Format[Schema.CustomVoiceParams] = Json.format[Schema.CustomVoiceParams]
	given fmtVoiceCloneParams: Format[Schema.VoiceCloneParams] = Json.format[Schema.VoiceCloneParams]
	given fmtCustomVoiceParamsReportedUsageEnum: Format[Schema.CustomVoiceParams.ReportedUsageEnum] = JsonEnumFormat[Schema.CustomVoiceParams.ReportedUsageEnum]
	given fmtAudioConfigAudioEncodingEnum: Format[Schema.AudioConfig.AudioEncodingEnum] = JsonEnumFormat[Schema.AudioConfig.AudioEncodingEnum]
	given fmtSynthesizeSpeechResponse: Format[Schema.SynthesizeSpeechResponse] = Json.format[Schema.SynthesizeSpeechResponse]
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtSynthesizeLongAudioRequest: Format[Schema.SynthesizeLongAudioRequest] = Json.format[Schema.SynthesizeLongAudioRequest]
	given fmtSynthesizeLongAudioMetadata: Format[Schema.SynthesizeLongAudioMetadata] = Json.format[Schema.SynthesizeLongAudioMetadata]
	given fmtGoogleCloudTexttospeechV1SynthesizeLongAudioMetadata: Format[Schema.GoogleCloudTexttospeechV1SynthesizeLongAudioMetadata] = Json.format[Schema.GoogleCloudTexttospeechV1SynthesizeLongAudioMetadata]
}
