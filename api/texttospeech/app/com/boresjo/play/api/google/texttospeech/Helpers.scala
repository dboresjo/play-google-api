package com.boresjo.play.api.google.texttospeech

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaVoice: Conversion[List[Schema.Voice], Option[List[Schema.Voice]]] = (fun: List[Schema.Voice]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaVoiceSsmlGenderEnum: Conversion[Schema.Voice.SsmlGenderEnum, Option[Schema.Voice.SsmlGenderEnum]] = (fun: Schema.Voice.SsmlGenderEnum) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaSynthesisInput: Conversion[Schema.SynthesisInput, Option[Schema.SynthesisInput]] = (fun: Schema.SynthesisInput) => Option(fun)
		given putSchemaVoiceSelectionParams: Conversion[Schema.VoiceSelectionParams, Option[Schema.VoiceSelectionParams]] = (fun: Schema.VoiceSelectionParams) => Option(fun)
		given putSchemaAudioConfig: Conversion[Schema.AudioConfig, Option[Schema.AudioConfig]] = (fun: Schema.AudioConfig) => Option(fun)
		given putSchemaAdvancedVoiceOptions: Conversion[Schema.AdvancedVoiceOptions, Option[Schema.AdvancedVoiceOptions]] = (fun: Schema.AdvancedVoiceOptions) => Option(fun)
		given putSchemaMultiSpeakerMarkup: Conversion[Schema.MultiSpeakerMarkup, Option[Schema.MultiSpeakerMarkup]] = (fun: Schema.MultiSpeakerMarkup) => Option(fun)
		given putSchemaCustomPronunciations: Conversion[Schema.CustomPronunciations, Option[Schema.CustomPronunciations]] = (fun: Schema.CustomPronunciations) => Option(fun)
		given putListSchemaTurn: Conversion[List[Schema.Turn], Option[List[Schema.Turn]]] = (fun: List[Schema.Turn]) => Option(fun)
		given putListSchemaCustomPronunciationParams: Conversion[List[Schema.CustomPronunciationParams], Option[List[Schema.CustomPronunciationParams]]] = (fun: List[Schema.CustomPronunciationParams]) => Option(fun)
		given putSchemaCustomPronunciationParamsPhoneticEncodingEnum: Conversion[Schema.CustomPronunciationParams.PhoneticEncodingEnum, Option[Schema.CustomPronunciationParams.PhoneticEncodingEnum]] = (fun: Schema.CustomPronunciationParams.PhoneticEncodingEnum) => Option(fun)
		given putSchemaVoiceSelectionParamsSsmlGenderEnum: Conversion[Schema.VoiceSelectionParams.SsmlGenderEnum, Option[Schema.VoiceSelectionParams.SsmlGenderEnum]] = (fun: Schema.VoiceSelectionParams.SsmlGenderEnum) => Option(fun)
		given putSchemaCustomVoiceParams: Conversion[Schema.CustomVoiceParams, Option[Schema.CustomVoiceParams]] = (fun: Schema.CustomVoiceParams) => Option(fun)
		given putSchemaVoiceCloneParams: Conversion[Schema.VoiceCloneParams, Option[Schema.VoiceCloneParams]] = (fun: Schema.VoiceCloneParams) => Option(fun)
		given putSchemaCustomVoiceParamsReportedUsageEnum: Conversion[Schema.CustomVoiceParams.ReportedUsageEnum, Option[Schema.CustomVoiceParams.ReportedUsageEnum]] = (fun: Schema.CustomVoiceParams.ReportedUsageEnum) => Option(fun)
		given putSchemaAudioConfigAudioEncodingEnum: Conversion[Schema.AudioConfig.AudioEncodingEnum, Option[Schema.AudioConfig.AudioEncodingEnum]] = (fun: Schema.AudioConfig.AudioEncodingEnum) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
