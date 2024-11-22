package com.boresjo.play.api.google.texttospeech

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListVoicesResponse(
	  /** The list of voices. */
		voices: Option[List[Schema.Voice]] = None
	)
	
	object Voice {
		enum SsmlGenderEnum extends Enum[SsmlGenderEnum] { case SSML_VOICE_GENDER_UNSPECIFIED, MALE, FEMALE, NEUTRAL }
	}
	case class Voice(
	  /** The languages that this voice supports, expressed as [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt) language tags (e.g. "en-US", "es-419", "cmn-tw"). */
		languageCodes: Option[List[String]] = None,
	  /** The name of this voice. Each distinct voice has a unique name. */
		name: Option[String] = None,
	  /** The gender of this voice. */
		ssmlGender: Option[Schema.Voice.SsmlGenderEnum] = None,
	  /** The natural sample rate (in hertz) for this voice. */
		naturalSampleRateHertz: Option[Int] = None
	)
	
	case class SynthesizeSpeechRequest(
	  /** Required. The Synthesizer requires either plain text or SSML as input. */
		input: Option[Schema.SynthesisInput] = None,
	  /** Required. The desired voice of the synthesized audio. */
		voice: Option[Schema.VoiceSelectionParams] = None,
	  /** Required. The configuration of the synthesized audio. */
		audioConfig: Option[Schema.AudioConfig] = None,
	  /** Advanced voice options. */
		advancedVoiceOptions: Option[Schema.AdvancedVoiceOptions] = None
	)
	
	case class SynthesisInput(
	  /** The raw text to be synthesized. */
		text: Option[String] = None,
	  /** The SSML document to be synthesized. The SSML document must be valid and well-formed. Otherwise the RPC will fail and return google.rpc.Code.INVALID_ARGUMENT. For more information, see [SSML](https://cloud.google.com/text-to-speech/docs/ssml). */
		ssml: Option[String] = None,
	  /** The multi-speaker input to be synthesized. Only applicable for multi-speaker synthesis. */
		multiSpeakerMarkup: Option[Schema.MultiSpeakerMarkup] = None,
	  /** Optional. The pronunciation customizations to be applied to the input. If this is set, the input will be synthesized using the given pronunciation customizations. The initial support will be for EFIGS (English, French, Italian, German, Spanish) languages, as provided in VoiceSelectionParams. Journey and Instant Clone voices are not supported yet. In order to customize the pronunciation of a phrase, there must be an exact match of the phrase in the input types. If using SSML, the phrase must not be inside a phoneme tag (entirely or partially). */
		customPronunciations: Option[Schema.CustomPronunciations] = None
	)
	
	case class MultiSpeakerMarkup(
	  /** Required. Speaker turns. */
		turns: Option[List[Schema.Turn]] = None
	)
	
	case class Turn(
	  /** Required. The speaker of the turn, for example, 'O' or 'Q'. Please refer to documentation for available speakers. */
		speaker: Option[String] = None,
	  /** Required. The text to speak. */
		text: Option[String] = None
	)
	
	case class CustomPronunciations(
	  /** The pronunciation customizations to be applied. */
		pronunciations: Option[List[Schema.CustomPronunciationParams]] = None
	)
	
	object CustomPronunciationParams {
		enum PhoneticEncodingEnum extends Enum[PhoneticEncodingEnum] { case PHONETIC_ENCODING_UNSPECIFIED, PHONETIC_ENCODING_IPA, PHONETIC_ENCODING_X_SAMPA }
	}
	case class CustomPronunciationParams(
	  /** The phrase to which the customization will be applied. The phrase can be multiple words (in the case of proper nouns etc), but should not span to a whole sentence. */
		phrase: Option[String] = None,
	  /** The phonetic encoding of the phrase. */
		phoneticEncoding: Option[Schema.CustomPronunciationParams.PhoneticEncodingEnum] = None,
	  /** The pronunciation of the phrase. This must be in the phonetic encoding specified above. */
		pronunciation: Option[String] = None
	)
	
	object VoiceSelectionParams {
		enum SsmlGenderEnum extends Enum[SsmlGenderEnum] { case SSML_VOICE_GENDER_UNSPECIFIED, MALE, FEMALE, NEUTRAL }
	}
	case class VoiceSelectionParams(
	  /** Required. The language (and potentially also the region) of the voice expressed as a [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt) language tag, e.g. "en-US". This should not include a script tag (e.g. use "cmn-cn" rather than "cmn-Hant-cn"), because the script will be inferred from the input provided in the SynthesisInput. The TTS service will use this parameter to help choose an appropriate voice. Note that the TTS service may choose a voice with a slightly different language code than the one selected; it may substitute a different region (e.g. using en-US rather than en-CA if there isn't a Canadian voice available), or even a different language, e.g. using "nb" (Norwegian Bokmal) instead of "no" (Norwegian)". */
		languageCode: Option[String] = None,
	  /** The name of the voice. If both the name and the gender are not set, the service will choose a voice based on the other parameters such as language_code. */
		name: Option[String] = None,
	  /** The preferred gender of the voice. If not set, the service will choose a voice based on the other parameters such as language_code and name. Note that this is only a preference, not requirement; if a voice of the appropriate gender is not available, the synthesizer should substitute a voice with a different gender rather than failing the request. */
		ssmlGender: Option[Schema.VoiceSelectionParams.SsmlGenderEnum] = None,
	  /** The configuration for a custom voice. If [CustomVoiceParams.model] is set, the service will choose the custom voice matching the specified configuration. */
		customVoice: Option[Schema.CustomVoiceParams] = None,
	  /** Optional. The configuration for a voice clone. If [VoiceCloneParams.voice_clone_key] is set, the service will choose the voice clone matching the specified configuration. */
		voiceClone: Option[Schema.VoiceCloneParams] = None
	)
	
	object CustomVoiceParams {
		enum ReportedUsageEnum extends Enum[ReportedUsageEnum] { case REPORTED_USAGE_UNSPECIFIED, REALTIME, OFFLINE }
	}
	case class CustomVoiceParams(
	  /** Required. The name of the AutoML model that synthesizes the custom voice. */
		model: Option[String] = None,
	  /** Optional. Deprecated. The usage of the synthesized audio to be reported. */
		reportedUsage: Option[Schema.CustomVoiceParams.ReportedUsageEnum] = None
	)
	
	case class VoiceCloneParams(
	  /** Required. Created by GenerateVoiceCloningKey. */
		voiceCloningKey: Option[String] = None
	)
	
	object AudioConfig {
		enum AudioEncodingEnum extends Enum[AudioEncodingEnum] { case AUDIO_ENCODING_UNSPECIFIED, LINEAR16, MP3, OGG_OPUS, MULAW, ALAW }
	}
	case class AudioConfig(
	  /** Required. The format of the audio byte stream. */
		audioEncoding: Option[Schema.AudioConfig.AudioEncodingEnum] = None,
	  /** Optional. Input only. Speaking rate/speed, in the range [0.25, 4.0]. 1.0 is the normal native speed supported by the specific voice. 2.0 is twice as fast, and 0.5 is half as fast. If unset(0.0), defaults to the native 1.0 speed. Any other values < 0.25 or > 4.0 will return an error. */
		speakingRate: Option[BigDecimal] = None,
	  /** Optional. Input only. Speaking pitch, in the range [-20.0, 20.0]. 20 means increase 20 semitones from the original pitch. -20 means decrease 20 semitones from the original pitch. */
		pitch: Option[BigDecimal] = None,
	  /** Optional. Input only. Volume gain (in dB) of the normal native volume supported by the specific voice, in the range [-96.0, 16.0]. If unset, or set to a value of 0.0 (dB), will play at normal native signal amplitude. A value of -6.0 (dB) will play at approximately half the amplitude of the normal native signal amplitude. A value of +6.0 (dB) will play at approximately twice the amplitude of the normal native signal amplitude. Strongly recommend not to exceed +10 (dB) as there's usually no effective increase in loudness for any value greater than that. */
		volumeGainDb: Option[BigDecimal] = None,
	  /** Optional. The synthesis sample rate (in hertz) for this audio. When this is specified in SynthesizeSpeechRequest, if this is different from the voice's natural sample rate, then the synthesizer will honor this request by converting to the desired sample rate (which might result in worse audio quality), unless the specified sample rate is not supported for the encoding chosen, in which case it will fail the request and return google.rpc.Code.INVALID_ARGUMENT. */
		sampleRateHertz: Option[Int] = None,
	  /** Optional. Input only. An identifier which selects 'audio effects' profiles that are applied on (post synthesized) text to speech. Effects are applied on top of each other in the order they are given. See [audio profiles](https://cloud.google.com/text-to-speech/docs/audio-profiles) for current supported profile ids. */
		effectsProfileId: Option[List[String]] = None
	)
	
	case class AdvancedVoiceOptions(
	  /** Only for Journey voices. If false, the synthesis will be context aware and have higher latency. */
		lowLatencyJourneySynthesis: Option[Boolean] = None
	)
	
	case class SynthesizeSpeechResponse(
	  /** The audio data bytes encoded as specified in the request, including the header for encodings that are wrapped in containers (e.g. MP3, OGG_OPUS). For LINEAR16 audio, we include the WAV header. Note: as with all bytes fields, protobuffers use a pure binary representation, whereas JSON representations use base64. */
		audioContent: Option[String] = None
	)
	
	case class ListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.Operation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Operation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class Empty(
	
	)
	
	case class CancelOperationRequest(
	
	)
	
	case class SynthesizeLongAudioRequest(
	  /** Required. The Synthesizer requires either plain text or SSML as input. */
		input: Option[Schema.SynthesisInput] = None,
	  /** Required. The configuration of the synthesized audio. */
		audioConfig: Option[Schema.AudioConfig] = None,
	  /** Required. Specifies a Cloud Storage URI for the synthesis results. Must be specified in the format: `gs://bucket_name/object_name`, and the bucket must already exist. */
		outputGcsUri: Option[String] = None,
	  /** Required. The desired voice of the synthesized audio. */
		voice: Option[Schema.VoiceSelectionParams] = None
	)
	
	case class SynthesizeLongAudioMetadata(
	  /** Time when the request was received. */
		startTime: Option[String] = None,
	  /** Deprecated. Do not use. */
		lastUpdateTime: Option[String] = None,
	  /** The progress of the most recent processing update in percentage, ie. 70.0%. */
		progressPercentage: Option[BigDecimal] = None
	)
	
	case class GoogleCloudTexttospeechV1SynthesizeLongAudioMetadata(
	  /** Time when the request was received. */
		startTime: Option[String] = None,
	  /** Deprecated. Do not use. */
		lastUpdateTime: Option[String] = None,
	  /** The progress of the most recent processing update in percentage, ie. 70.0%. */
		progressPercentage: Option[BigDecimal] = None
	)
}
