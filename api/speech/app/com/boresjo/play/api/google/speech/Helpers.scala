package com.boresjo.play.api.google.speech

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaPhraseSet: Conversion[Schema.PhraseSet, Option[Schema.PhraseSet]] = (fun: Schema.PhraseSet) => Option(fun)
		given putListSchemaPhrase: Conversion[List[Schema.Phrase], Option[List[Schema.Phrase]]] = (fun: List[Schema.Phrase]) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putSchemaPhraseSetStateEnum: Conversion[Schema.PhraseSet.StateEnum, Option[Schema.PhraseSet.StateEnum]] = (fun: Schema.PhraseSet.StateEnum) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListSchemaPhraseSet: Conversion[List[Schema.PhraseSet], Option[List[Schema.PhraseSet]]] = (fun: List[Schema.PhraseSet]) => Option(fun)
		given putSchemaCustomClass: Conversion[Schema.CustomClass, Option[Schema.CustomClass]] = (fun: Schema.CustomClass) => Option(fun)
		given putListSchemaClassItem: Conversion[List[Schema.ClassItem], Option[List[Schema.ClassItem]]] = (fun: List[Schema.ClassItem]) => Option(fun)
		given putSchemaCustomClassStateEnum: Conversion[Schema.CustomClass.StateEnum, Option[Schema.CustomClass.StateEnum]] = (fun: Schema.CustomClass.StateEnum) => Option(fun)
		given putListSchemaCustomClass: Conversion[List[Schema.CustomClass], Option[List[Schema.CustomClass]]] = (fun: List[Schema.CustomClass]) => Option(fun)
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaRecognitionConfig: Conversion[Schema.RecognitionConfig, Option[Schema.RecognitionConfig]] = (fun: Schema.RecognitionConfig) => Option(fun)
		given putSchemaRecognitionAudio: Conversion[Schema.RecognitionAudio, Option[Schema.RecognitionAudio]] = (fun: Schema.RecognitionAudio) => Option(fun)
		given putSchemaRecognitionConfigEncodingEnum: Conversion[Schema.RecognitionConfig.EncodingEnum, Option[Schema.RecognitionConfig.EncodingEnum]] = (fun: Schema.RecognitionConfig.EncodingEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaSpeechAdaptation: Conversion[Schema.SpeechAdaptation, Option[Schema.SpeechAdaptation]] = (fun: Schema.SpeechAdaptation) => Option(fun)
		given putSchemaTranscriptNormalization: Conversion[Schema.TranscriptNormalization, Option[Schema.TranscriptNormalization]] = (fun: Schema.TranscriptNormalization) => Option(fun)
		given putListSchemaSpeechContext: Conversion[List[Schema.SpeechContext], Option[List[Schema.SpeechContext]]] = (fun: List[Schema.SpeechContext]) => Option(fun)
		given putSchemaSpeakerDiarizationConfig: Conversion[Schema.SpeakerDiarizationConfig, Option[Schema.SpeakerDiarizationConfig]] = (fun: Schema.SpeakerDiarizationConfig) => Option(fun)
		given putSchemaRecognitionMetadata: Conversion[Schema.RecognitionMetadata, Option[Schema.RecognitionMetadata]] = (fun: Schema.RecognitionMetadata) => Option(fun)
		given putSchemaABNFGrammar: Conversion[Schema.ABNFGrammar, Option[Schema.ABNFGrammar]] = (fun: Schema.ABNFGrammar) => Option(fun)
		given putListSchemaEntry: Conversion[List[Schema.Entry], Option[List[Schema.Entry]]] = (fun: List[Schema.Entry]) => Option(fun)
		given putSchemaRecognitionMetadataInteractionTypeEnum: Conversion[Schema.RecognitionMetadata.InteractionTypeEnum, Option[Schema.RecognitionMetadata.InteractionTypeEnum]] = (fun: Schema.RecognitionMetadata.InteractionTypeEnum) => Option(fun)
		given putSchemaRecognitionMetadataMicrophoneDistanceEnum: Conversion[Schema.RecognitionMetadata.MicrophoneDistanceEnum, Option[Schema.RecognitionMetadata.MicrophoneDistanceEnum]] = (fun: Schema.RecognitionMetadata.MicrophoneDistanceEnum) => Option(fun)
		given putSchemaRecognitionMetadataOriginalMediaTypeEnum: Conversion[Schema.RecognitionMetadata.OriginalMediaTypeEnum, Option[Schema.RecognitionMetadata.OriginalMediaTypeEnum]] = (fun: Schema.RecognitionMetadata.OriginalMediaTypeEnum) => Option(fun)
		given putSchemaRecognitionMetadataRecordingDeviceTypeEnum: Conversion[Schema.RecognitionMetadata.RecordingDeviceTypeEnum, Option[Schema.RecognitionMetadata.RecordingDeviceTypeEnum]] = (fun: Schema.RecognitionMetadata.RecordingDeviceTypeEnum) => Option(fun)
		given putListSchemaSpeechRecognitionResult: Conversion[List[Schema.SpeechRecognitionResult], Option[List[Schema.SpeechRecognitionResult]]] = (fun: List[Schema.SpeechRecognitionResult]) => Option(fun)
		given putSchemaSpeechAdaptationInfo: Conversion[Schema.SpeechAdaptationInfo, Option[Schema.SpeechAdaptationInfo]] = (fun: Schema.SpeechAdaptationInfo) => Option(fun)
		given putListSchemaSpeechRecognitionAlternative: Conversion[List[Schema.SpeechRecognitionAlternative], Option[List[Schema.SpeechRecognitionAlternative]]] = (fun: List[Schema.SpeechRecognitionAlternative]) => Option(fun)
		given putListSchemaWordInfo: Conversion[List[Schema.WordInfo], Option[List[Schema.WordInfo]]] = (fun: List[Schema.WordInfo]) => Option(fun)
		given putSchemaTranscriptOutputConfig: Conversion[Schema.TranscriptOutputConfig, Option[Schema.TranscriptOutputConfig]] = (fun: Schema.TranscriptOutputConfig) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
