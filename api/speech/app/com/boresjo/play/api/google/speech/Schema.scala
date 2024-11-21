package com.boresjo.play.api.google.speech

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class CreatePhraseSetRequest(
	  /** Required. The ID to use for the phrase set, which will become the final component of the phrase set's resource name. This value should restrict to letters, numbers, and hyphens, with the first character a letter, the last a letter or a number, and be 4-63 characters. */
		phraseSetId: Option[String] = None,
	  /** Required. The phrase set to create. */
		phraseSet: Option[Schema.PhraseSet] = None
	)
	
	object PhraseSet {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, DELETED }
	}
	case class PhraseSet(
	  /** The resource name of the phrase set. */
		name: Option[String] = None,
	  /** A list of word and phrases. */
		phrases: Option[List[Schema.Phrase]] = None,
	  /** Hint Boost. Positive value will increase the probability that a specific phrase will be recognized over other similar sounding phrases. The higher the boost, the higher the chance of false positive recognition as well. Negative boost values would correspond to anti-biasing. Anti-biasing is not enabled, so negative boost will simply be ignored. Though `boost` can accept a wide range of positive values, most use cases are best served with values between 0 (exclusive) and 20. We recommend using a binary search approach to finding the optimal value for your use case as well as adding phrases both with and without boost to your requests. */
		boost: Option[BigDecimal] = None,
	  /** Output only. The [KMS key name](https://cloud.google.com/kms/docs/resource-hierarchy#keys) with which the content of the PhraseSet is encrypted. The expected format is `projects/{project}/locations/{location}/keyRings/{key_ring}/cryptoKeys/{crypto_key}`. */
		kmsKeyName: Option[String] = None,
	  /** Output only. The [KMS key version name](https://cloud.google.com/kms/docs/resource-hierarchy#key_versions) with which content of the PhraseSet is encrypted. The expected format is `projects/{project}/locations/{location}/keyRings/{key_ring}/cryptoKeys/{crypto_key}/cryptoKeyVersions/{crypto_key_version}`. */
		kmsKeyVersionName: Option[String] = None,
	  /** Output only. System-assigned unique identifier for the PhraseSet. This field is not used. */
		uid: Option[String] = None,
	  /** Output only. User-settable, human-readable name for the PhraseSet. Must be 63 characters or less. This field is not used. */
		displayName: Option[String] = None,
	  /** Output only. The CustomClass lifecycle state. This field is not used. */
		state: Option[Schema.PhraseSet.StateEnum] = None,
	  /** Output only. The time at which this resource was requested for deletion. This field is not used. */
		deleteTime: Option[String] = None,
	  /** Output only. The time at which this resource will be purged. This field is not used. */
		expireTime: Option[String] = None,
	  /** Output only. Allows users to store small amounts of arbitrary data. Both the key and the value must be 63 characters or less each. At most 100 annotations. This field is not used. */
		annotations: Option[Map[String, String]] = None,
	  /** Output only. This checksum is computed by the server based on the value of other fields. This may be sent on update, undelete, and delete requests to ensure the client has an up-to-date value before proceeding. This field is not used. */
		etag: Option[String] = None,
	  /** Output only. Whether or not this PhraseSet is in the process of being updated. This field is not used. */
		reconciling: Option[Boolean] = None
	)
	
	case class Phrase(
	  /** The phrase itself. */
		value: Option[String] = None,
	  /** Hint Boost. Overrides the boost set at the phrase set level. Positive value will increase the probability that a specific phrase will be recognized over other similar sounding phrases. The higher the boost, the higher the chance of false positive recognition as well. Negative boost will simply be ignored. Though `boost` can accept a wide range of positive values, most use cases are best served with values between 0 and 20. We recommend using a binary search approach to finding the optimal value for your use case as well as adding phrases both with and without boost to your requests. */
		boost: Option[BigDecimal] = None
	)
	
	case class ListPhraseSetResponse(
	  /** The phrase set. */
		phraseSets: Option[List[Schema.PhraseSet]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class CreateCustomClassRequest(
	  /** Required. The ID to use for the custom class, which will become the final component of the custom class' resource name. This value should restrict to letters, numbers, and hyphens, with the first character a letter, the last a letter or a number, and be 4-63 characters. */
		customClassId: Option[String] = None,
	  /** Required. The custom class to create. */
		customClass: Option[Schema.CustomClass] = None
	)
	
	object CustomClass {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, DELETED }
	}
	case class CustomClass(
	  /** The resource name of the custom class. */
		name: Option[String] = None,
	  /** If this custom class is a resource, the custom_class_id is the resource id of the CustomClass. Case sensitive. */
		customClassId: Option[String] = None,
	  /** A collection of class items. */
		items: Option[List[Schema.ClassItem]] = None,
	  /** Output only. The [KMS key name](https://cloud.google.com/kms/docs/resource-hierarchy#keys) with which the content of the ClassItem is encrypted. The expected format is `projects/{project}/locations/{location}/keyRings/{key_ring}/cryptoKeys/{crypto_key}`. */
		kmsKeyName: Option[String] = None,
	  /** Output only. The [KMS key version name](https://cloud.google.com/kms/docs/resource-hierarchy#key_versions) with which content of the ClassItem is encrypted. The expected format is `projects/{project}/locations/{location}/keyRings/{key_ring}/cryptoKeys/{crypto_key}/cryptoKeyVersions/{crypto_key_version}`. */
		kmsKeyVersionName: Option[String] = None,
	  /** Output only. System-assigned unique identifier for the CustomClass. This field is not used. */
		uid: Option[String] = None,
	  /** Output only. User-settable, human-readable name for the CustomClass. Must be 63 characters or less. This field is not used. */
		displayName: Option[String] = None,
	  /** Output only. The CustomClass lifecycle state. This field is not used. */
		state: Option[Schema.CustomClass.StateEnum] = None,
	  /** Output only. The time at which this resource was requested for deletion. This field is not used. */
		deleteTime: Option[String] = None,
	  /** Output only. The time at which this resource will be purged. This field is not used. */
		expireTime: Option[String] = None,
	  /** Output only. Allows users to store small amounts of arbitrary data. Both the key and the value must be 63 characters or less each. At most 100 annotations. This field is not used. */
		annotations: Option[Map[String, String]] = None,
	  /** Output only. This checksum is computed by the server based on the value of other fields. This may be sent on update, undelete, and delete requests to ensure the client has an up-to-date value before proceeding. This field is not used. */
		etag: Option[String] = None,
	  /** Output only. Whether or not this CustomClass is in the process of being updated. This field is not used. */
		reconciling: Option[Boolean] = None
	)
	
	case class ClassItem(
	  /** The class item's value. */
		value: Option[String] = None
	)
	
	case class ListCustomClassesResponse(
	  /** The custom classes. */
		customClasses: Option[List[Schema.CustomClass]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
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
	
	case class RecognizeRequest(
	  /** Required. Provides information to the recognizer that specifies how to process the request. */
		config: Option[Schema.RecognitionConfig] = None,
	  /** Required. The audio data to be recognized. */
		audio: Option[Schema.RecognitionAudio] = None
	)
	
	object RecognitionConfig {
		enum EncodingEnum extends Enum[EncodingEnum] { case ENCODING_UNSPECIFIED, LINEAR16, FLAC, MULAW, AMR, AMR_WB, OGG_OPUS, SPEEX_WITH_HEADER_BYTE, MP3, WEBM_OPUS, ALAW }
	}
	case class RecognitionConfig(
	  /** Encoding of audio data sent in all `RecognitionAudio` messages. This field is optional for `FLAC` and `WAV` audio files and required for all other audio formats. For details, see AudioEncoding. */
		encoding: Option[Schema.RecognitionConfig.EncodingEnum] = None,
	  /** Sample rate in Hertz of the audio data sent in all `RecognitionAudio` messages. Valid values are: 8000-48000. 16000 is optimal. For best results, set the sampling rate of the audio source to 16000 Hz. If that's not possible, use the native sample rate of the audio source (instead of re-sampling). This field is optional for FLAC and WAV audio files, but is required for all other audio formats. For details, see AudioEncoding. */
		sampleRateHertz: Option[Int] = None,
	  /** The number of channels in the input audio data. ONLY set this for MULTI-CHANNEL recognition. Valid values for LINEAR16, OGG_OPUS and FLAC are `1`-`8`. Valid value for MULAW, AMR, AMR_WB and SPEEX_WITH_HEADER_BYTE is only `1`. If `0` or omitted, defaults to one channel (mono). Note: We only recognize the first channel by default. To perform independent recognition on each channel set `enable_separate_recognition_per_channel` to 'true'. */
		audioChannelCount: Option[Int] = None,
	  /** This needs to be set to `true` explicitly and `audio_channel_count` > 1 to get each channel recognized separately. The recognition result will contain a `channel_tag` field to state which channel that result belongs to. If this is not true, we will only recognize the first channel. The request is billed cumulatively for all channels recognized: `audio_channel_count` multiplied by the length of the audio. */
		enableSeparateRecognitionPerChannel: Option[Boolean] = None,
	  /** Required. The language of the supplied audio as a [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt) language tag. Example: "en-US". See [Language Support](https://cloud.google.com/speech-to-text/docs/languages) for a list of the currently supported language codes. */
		languageCode: Option[String] = None,
	  /** A list of up to 3 additional [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt) language tags, listing possible alternative languages of the supplied audio. See [Language Support](https://cloud.google.com/speech-to-text/docs/languages) for a list of the currently supported language codes. If alternative languages are listed, recognition result will contain recognition in the most likely language detected including the main language_code. The recognition result will include the language tag of the language detected in the audio. Note: This feature is only supported for Voice Command and Voice Search use cases and performance may vary for other use cases (e.g., phone call transcription). */
		alternativeLanguageCodes: Option[List[String]] = None,
	  /** Maximum number of recognition hypotheses to be returned. Specifically, the maximum number of `SpeechRecognitionAlternative` messages within each `SpeechRecognitionResult`. The server may return fewer than `max_alternatives`. Valid values are `0`-`30`. A value of `0` or `1` will return a maximum of one. If omitted, will return a maximum of one. */
		maxAlternatives: Option[Int] = None,
	  /** If set to `true`, the server will attempt to filter out profanities, replacing all but the initial character in each filtered word with asterisks, e.g. "f&#42;&#42;&#42;". If set to `false` or omitted, profanities won't be filtered out. */
		profanityFilter: Option[Boolean] = None,
	  /** Speech adaptation configuration improves the accuracy of speech recognition. For more information, see the [speech adaptation](https://cloud.google.com/speech-to-text/docs/adaptation) documentation. When speech adaptation is set it supersedes the `speech_contexts` field. */
		adaptation: Option[Schema.SpeechAdaptation] = None,
	  /** Optional. Use transcription normalization to automatically replace parts of the transcript with phrases of your choosing. For StreamingRecognize, this normalization only applies to stable partial transcripts (stability > 0.8) and final transcripts. */
		transcriptNormalization: Option[Schema.TranscriptNormalization] = None,
	  /** Array of SpeechContext. A means to provide context to assist the speech recognition. For more information, see [speech adaptation](https://cloud.google.com/speech-to-text/docs/adaptation). */
		speechContexts: Option[List[Schema.SpeechContext]] = None,
	  /** If `true`, the top result includes a list of words and the start and end time offsets (timestamps) for those words. If `false`, no word-level time offset information is returned. The default is `false`. */
		enableWordTimeOffsets: Option[Boolean] = None,
	  /** If `true`, the top result includes a list of words and the confidence for those words. If `false`, no word-level confidence information is returned. The default is `false`. */
		enableWordConfidence: Option[Boolean] = None,
	  /** If 'true', adds punctuation to recognition result hypotheses. This feature is only available in select languages. Setting this for requests in other languages has no effect at all. The default 'false' value does not add punctuation to result hypotheses. */
		enableAutomaticPunctuation: Option[Boolean] = None,
	  /** The spoken punctuation behavior for the call If not set, uses default behavior based on model of choice e.g. command_and_search will enable spoken punctuation by default If 'true', replaces spoken punctuation with the corresponding symbols in the request. For example, "how are you question mark" becomes "how are you?". See https://cloud.google.com/speech-to-text/docs/spoken-punctuation for support. If 'false', spoken punctuation is not replaced. */
		enableSpokenPunctuation: Option[Boolean] = None,
	  /** The spoken emoji behavior for the call If not set, uses default behavior based on model of choice If 'true', adds spoken emoji formatting for the request. This will replace spoken emojis with the corresponding Unicode symbols in the final transcript. If 'false', spoken emojis are not replaced. */
		enableSpokenEmojis: Option[Boolean] = None,
	  /** Config to enable speaker diarization and set additional parameters to make diarization better suited for your application. Note: When this is enabled, we send all the words from the beginning of the audio for the top alternative in every consecutive STREAMING responses. This is done in order to improve our speaker tags as our models learn to identify the speakers in the conversation over time. For non-streaming requests, the diarization results will be provided only in the top alternative of the FINAL SpeechRecognitionResult. */
		diarizationConfig: Option[Schema.SpeakerDiarizationConfig] = None,
	  /** Metadata regarding this request. */
		metadata: Option[Schema.RecognitionMetadata] = None,
	  /** Which model to select for the given request. Select the model best suited to your domain to get best results. If a model is not explicitly specified, then we auto-select a model based on the parameters in the RecognitionConfig. &#42;Model&#42; &#42;Description&#42; latest_long Best for long form content like media or conversation. latest_short Best for short form content like commands or single shot directed speech. command_and_search Best for short queries such as voice commands or voice search. phone_call Best for audio that originated from a phone call (typically recorded at an 8khz sampling rate). video Best for audio that originated from video or includes multiple speakers. Ideally the audio is recorded at a 16khz or greater sampling rate. This is a premium model that costs more than the standard rate. default Best for audio that is not one of the specific audio models. For example, long-form audio. Ideally the audio is high-fidelity, recorded at a 16khz or greater sampling rate. medical_conversation Best for audio that originated from a conversation between a medical provider and patient. medical_dictation Best for audio that originated from dictation notes by a medical provider.  */
		model: Option[String] = None,
	  /** Set to true to use an enhanced model for speech recognition. If `use_enhanced` is set to true and the `model` field is not set, then an appropriate enhanced model is chosen if an enhanced model exists for the audio. If `use_enhanced` is true and an enhanced version of the specified model does not exist, then the speech is recognized using the standard version of the specified model. */
		useEnhanced: Option[Boolean] = None
	)
	
	case class SpeechAdaptation(
	  /** A collection of phrase sets. To specify the hints inline, leave the phrase set's `name` blank and fill in the rest of its fields. Any phrase set can use any custom class. */
		phraseSets: Option[List[Schema.PhraseSet]] = None,
	  /** A collection of phrase set resource names to use. */
		phraseSetReferences: Option[List[String]] = None,
	  /** A collection of custom classes. To specify the classes inline, leave the class' `name` blank and fill in the rest of its fields, giving it a unique `custom_class_id`. Refer to the inline defined class in phrase hints by its `custom_class_id`. */
		customClasses: Option[List[Schema.CustomClass]] = None,
	  /** Augmented Backus-Naur form (ABNF) is a standardized grammar notation comprised by a set of derivation rules. See specifications: https://www.w3.org/TR/speech-grammar */
		abnfGrammar: Option[Schema.ABNFGrammar] = None
	)
	
	case class ABNFGrammar(
	  /** All declarations and rules of an ABNF grammar broken up into multiple strings that will end up concatenated. */
		abnfStrings: Option[List[String]] = None
	)
	
	case class TranscriptNormalization(
	  /** A list of replacement entries. We will perform replacement with one entry at a time. For example, the second entry in ["cat" => "dog", "mountain cat" => "mountain dog"] will never be applied because we will always process the first entry before it. At most 100 entries. */
		entries: Option[List[Schema.Entry]] = None
	)
	
	case class Entry(
	  /** What to replace. Max length is 100 characters. */
		search: Option[String] = None,
	  /** What to replace with. Max length is 100 characters. */
		replace: Option[String] = None,
	  /** Whether the search is case sensitive. */
		caseSensitive: Option[Boolean] = None
	)
	
	case class SpeechContext(
	  /** A list of strings containing words and phrases "hints" so that the speech recognition is more likely to recognize them. This can be used to improve the accuracy for specific words and phrases, for example, if specific commands are typically spoken by the user. This can also be used to add additional words to the vocabulary of the recognizer. See [usage limits](https://cloud.google.com/speech-to-text/quotas#content). List items can also be set to classes for groups of words that represent common concepts that occur in natural language. For example, rather than providing phrase hints for every month of the year, using the $MONTH class improves the likelihood of correctly transcribing audio that includes months. */
		phrases: Option[List[String]] = None,
	  /** Hint Boost. Positive value will increase the probability that a specific phrase will be recognized over other similar sounding phrases. The higher the boost, the higher the chance of false positive recognition as well. Negative boost values would correspond to anti-biasing. Anti-biasing is not enabled, so negative boost will simply be ignored. Though `boost` can accept a wide range of positive values, most use cases are best served with values between 0 and 20. We recommend using a binary search approach to finding the optimal value for your use case. */
		boost: Option[BigDecimal] = None
	)
	
	case class SpeakerDiarizationConfig(
	  /** If 'true', enables speaker detection for each recognized word in the top alternative of the recognition result using a speaker_label provided in the WordInfo. */
		enableSpeakerDiarization: Option[Boolean] = None,
	  /** Minimum number of speakers in the conversation. This range gives you more flexibility by allowing the system to automatically determine the correct number of speakers. If not set, the default value is 2. */
		minSpeakerCount: Option[Int] = None,
	  /** Maximum number of speakers in the conversation. This range gives you more flexibility by allowing the system to automatically determine the correct number of speakers. If not set, the default value is 6. */
		maxSpeakerCount: Option[Int] = None,
	  /** Output only. Unused. */
		speakerTag: Option[Int] = None
	)
	
	object RecognitionMetadata {
		enum InteractionTypeEnum extends Enum[InteractionTypeEnum] { case INTERACTION_TYPE_UNSPECIFIED, DISCUSSION, PRESENTATION, PHONE_CALL, VOICEMAIL, PROFESSIONALLY_PRODUCED, VOICE_SEARCH, VOICE_COMMAND, DICTATION }
		enum MicrophoneDistanceEnum extends Enum[MicrophoneDistanceEnum] { case MICROPHONE_DISTANCE_UNSPECIFIED, NEARFIELD, MIDFIELD, FARFIELD }
		enum OriginalMediaTypeEnum extends Enum[OriginalMediaTypeEnum] { case ORIGINAL_MEDIA_TYPE_UNSPECIFIED, AUDIO, VIDEO }
		enum RecordingDeviceTypeEnum extends Enum[RecordingDeviceTypeEnum] { case RECORDING_DEVICE_TYPE_UNSPECIFIED, SMARTPHONE, PC, PHONE_LINE, VEHICLE, OTHER_OUTDOOR_DEVICE, OTHER_INDOOR_DEVICE }
	}
	case class RecognitionMetadata(
	  /** The use case most closely describing the audio content to be recognized. */
		interactionType: Option[Schema.RecognitionMetadata.InteractionTypeEnum] = None,
	  /** The industry vertical to which this speech recognition request most closely applies. This is most indicative of the topics contained in the audio. Use the 6-digit NAICS code to identify the industry vertical - see https://www.naics.com/search/. */
		industryNaicsCodeOfAudio: Option[Int] = None,
	  /** The audio type that most closely describes the audio being recognized. */
		microphoneDistance: Option[Schema.RecognitionMetadata.MicrophoneDistanceEnum] = None,
	  /** The original media the speech was recorded on. */
		originalMediaType: Option[Schema.RecognitionMetadata.OriginalMediaTypeEnum] = None,
	  /** The type of device the speech was recorded with. */
		recordingDeviceType: Option[Schema.RecognitionMetadata.RecordingDeviceTypeEnum] = None,
	  /** The device used to make the recording. Examples 'Nexus 5X' or 'Polycom SoundStation IP 6000' or 'POTS' or 'VoIP' or 'Cardioid Microphone'. */
		recordingDeviceName: Option[String] = None,
	  /** Mime type of the original audio file. For example `audio/m4a`, `audio/x-alaw-basic`, `audio/mp3`, `audio/3gpp`. A list of possible audio mime types is maintained at http://www.iana.org/assignments/media-types/media-types.xhtml#audio */
		originalMimeType: Option[String] = None,
	  /** Description of the content. Eg. "Recordings of federal supreme court hearings from 2012". */
		audioTopic: Option[String] = None
	)
	
	case class RecognitionAudio(
	  /** The audio data bytes encoded as specified in `RecognitionConfig`. Note: as with all bytes fields, proto buffers use a pure binary representation, whereas JSON representations use base64. */
		content: Option[String] = None,
	  /** URI that points to a file that contains audio data bytes as specified in `RecognitionConfig`. The file must not be compressed (for example, gzip). Currently, only Google Cloud Storage URIs are supported, which must be specified in the following format: `gs://bucket_name/object_name` (other URI formats return google.rpc.Code.INVALID_ARGUMENT). For more information, see [Request URIs](https://cloud.google.com/storage/docs/reference-uris). */
		uri: Option[String] = None
	)
	
	case class RecognizeResponse(
	  /** Sequential list of transcription results corresponding to sequential portions of audio. */
		results: Option[List[Schema.SpeechRecognitionResult]] = None,
	  /** When available, billed audio seconds for the corresponding request. */
		totalBilledTime: Option[String] = None,
	  /** Provides information on adaptation behavior in response */
		speechAdaptationInfo: Option[Schema.SpeechAdaptationInfo] = None,
	  /** The ID associated with the request. This is a unique ID specific only to the given request. */
		requestId: Option[String] = None,
	  /** Whether request used legacy asr models (was not automatically migrated to use conformer models). */
		usingLegacyModels: Option[Boolean] = None
	)
	
	case class SpeechRecognitionResult(
	  /** May contain one or more recognition hypotheses (up to the maximum specified in `max_alternatives`). These alternatives are ordered in terms of accuracy, with the top (first) alternative being the most probable, as ranked by the recognizer. */
		alternatives: Option[List[Schema.SpeechRecognitionAlternative]] = None,
	  /** For multi-channel audio, this is the channel number corresponding to the recognized result for the audio from that channel. For audio_channel_count = N, its output values can range from '1' to 'N'. */
		channelTag: Option[Int] = None,
	  /** Time offset of the end of this result relative to the beginning of the audio. */
		resultEndTime: Option[String] = None,
	  /** Output only. The [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt) language tag of the language in this result. This language code was detected to have the most likelihood of being spoken in the audio. */
		languageCode: Option[String] = None
	)
	
	case class SpeechRecognitionAlternative(
	  /** Transcript text representing the words that the user spoke. In languages that use spaces to separate words, the transcript might have a leading space if it isn't the first result. You can concatenate each result to obtain the full transcript without using a separator. */
		transcript: Option[String] = None,
	  /** The confidence estimate between 0.0 and 1.0. A higher number indicates an estimated greater likelihood that the recognized words are correct. This field is set only for the top alternative of a non-streaming result or, of a streaming result where `is_final=true`. This field is not guaranteed to be accurate and users should not rely on it to be always provided. The default of 0.0 is a sentinel value indicating `confidence` was not set. */
		confidence: Option[BigDecimal] = None,
	  /** A list of word-specific information for each recognized word. Note: When `enable_speaker_diarization` is true, you will see all the words from the beginning of the audio. */
		words: Option[List[Schema.WordInfo]] = None
	)
	
	case class WordInfo(
	  /** Time offset relative to the beginning of the audio, and corresponding to the start of the spoken word. This field is only set if `enable_word_time_offsets=true` and only in the top hypothesis. This is an experimental feature and the accuracy of the time offset can vary. */
		startTime: Option[String] = None,
	  /** Time offset relative to the beginning of the audio, and corresponding to the end of the spoken word. This field is only set if `enable_word_time_offsets=true` and only in the top hypothesis. This is an experimental feature and the accuracy of the time offset can vary. */
		endTime: Option[String] = None,
	  /** The word corresponding to this set of information. */
		word: Option[String] = None,
	  /** The confidence estimate between 0.0 and 1.0. A higher number indicates an estimated greater likelihood that the recognized words are correct. This field is set only for the top alternative of a non-streaming result or, of a streaming result where `is_final=true`. This field is not guaranteed to be accurate and users should not rely on it to be always provided. The default of 0.0 is a sentinel value indicating `confidence` was not set. */
		confidence: Option[BigDecimal] = None,
	  /** Output only. A distinct integer value is assigned for every speaker within the audio. This field specifies which one of those speakers was detected to have spoken this word. Value ranges from '1' to diarization_speaker_count. speaker_tag is set if enable_speaker_diarization = 'true' and only for the top alternative. Note: Use speaker_label instead. */
		speakerTag: Option[Int] = None,
	  /** Output only. A label value assigned for every unique speaker within the audio. This field specifies which speaker was detected to have spoken this word. For some models, like medical_conversation this can be actual speaker role, for example "patient" or "provider", but generally this would be a number identifying a speaker. This field is only set if enable_speaker_diarization = 'true' and only for the top alternative. */
		speakerLabel: Option[String] = None
	)
	
	case class SpeechAdaptationInfo(
	  /** Whether there was a timeout when applying speech adaptation. If true, adaptation had no effect in the response transcript. */
		adaptationTimeout: Option[Boolean] = None,
	  /** If set, returns a message specifying which part of the speech adaptation request timed out. */
		timeoutMessage: Option[String] = None
	)
	
	case class LongRunningRecognizeRequest(
	  /** Required. Provides information to the recognizer that specifies how to process the request. */
		config: Option[Schema.RecognitionConfig] = None,
	  /** Required. The audio data to be recognized. */
		audio: Option[Schema.RecognitionAudio] = None,
	  /** Optional. Specifies an optional destination for the recognition results. */
		outputConfig: Option[Schema.TranscriptOutputConfig] = None
	)
	
	case class TranscriptOutputConfig(
	  /** Specifies a Cloud Storage URI for the recognition results. Must be specified in the format: `gs://bucket_name/object_name`, and the bucket must already exist. */
		gcsUri: Option[String] = None
	)
	
	case class LongRunningRecognizeMetadata(
	  /** Approximate percentage of audio processed thus far. Guaranteed to be 100 when the audio is fully processed and the results are available. */
		progressPercent: Option[Int] = None,
	  /** Time when the request was received. */
		startTime: Option[String] = None,
	  /** Time of the most recent processing update. */
		lastUpdateTime: Option[String] = None,
	  /** Output only. The URI of the audio file being transcribed. Empty if the audio was sent as byte content. */
		uri: Option[String] = None
	)
	
	case class LongRunningRecognizeResponse(
	  /** Sequential list of transcription results corresponding to sequential portions of audio. */
		results: Option[List[Schema.SpeechRecognitionResult]] = None,
	  /** When available, billed audio seconds for the corresponding request. */
		totalBilledTime: Option[String] = None,
	  /** Original output config if present in the request. */
		outputConfig: Option[Schema.TranscriptOutputConfig] = None,
	  /** If the transcript output fails this field contains the relevant error. */
		outputError: Option[Schema.Status] = None,
	  /** Provides information on speech adaptation behavior in response */
		speechAdaptationInfo: Option[Schema.SpeechAdaptationInfo] = None,
	  /** The ID associated with the request. This is a unique ID specific only to the given request. */
		requestId: Option[String] = None
	)
}
