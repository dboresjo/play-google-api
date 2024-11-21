package com.boresjo.play.api.google.videointelligence

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleLongrunning_ListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.GoogleLongrunning_Operation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleLongrunning_Operation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.GoogleRpc_Status] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleRpc_Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GoogleProtobuf_Empty(
	
	)
	
	case class GoogleLongrunning_CancelOperationRequest(
	
	)
	
	object GoogleCloudVideointelligenceV1_AnnotateVideoRequest {
		enum FeaturesEnum extends Enum[FeaturesEnum] { case FEATURE_UNSPECIFIED, LABEL_DETECTION, SHOT_CHANGE_DETECTION, EXPLICIT_CONTENT_DETECTION, FACE_DETECTION, SPEECH_TRANSCRIPTION, TEXT_DETECTION, OBJECT_TRACKING, LOGO_RECOGNITION, PERSON_DETECTION }
	}
	case class GoogleCloudVideointelligenceV1_AnnotateVideoRequest(
	  /** Input video location. Currently, only [Cloud Storage](https://cloud.google.com/storage/) URIs are supported. URIs must be specified in the following format: `gs://bucket-id/object-id` (other URI formats return google.rpc.Code.INVALID_ARGUMENT). For more information, see [Request URIs](https://cloud.google.com/storage/docs/request-endpoints). To identify multiple videos, a video URI may include wildcards in the `object-id`. Supported wildcards: '&#42;' to match 0 or more characters; '?' to match 1 character. If unset, the input video should be embedded in the request as `input_content`. If set, `input_content` must be unset. */
		inputUri: Option[String] = None,
	  /** The video data bytes. If unset, the input video(s) should be specified via the `input_uri`. If set, `input_uri` must be unset. */
		inputContent: Option[String] = None,
	  /** Required. Requested video annotation features. */
		features: Option[List[Schema.GoogleCloudVideointelligenceV1_AnnotateVideoRequest.FeaturesEnum]] = None,
	  /** Additional video context and/or feature-specific parameters. */
		videoContext: Option[Schema.GoogleCloudVideointelligenceV1_VideoContext] = None,
	  /** Optional. Location where the output (in JSON format) should be stored. Currently, only [Cloud Storage](https://cloud.google.com/storage/) URIs are supported. These must be specified in the following format: `gs://bucket-id/object-id` (other URI formats return google.rpc.Code.INVALID_ARGUMENT). For more information, see [Request URIs](https://cloud.google.com/storage/docs/request-endpoints). */
		outputUri: Option[String] = None,
	  /** Optional. Cloud region where annotation should take place. Supported cloud regions are: `us-east1`, `us-west1`, `europe-west1`, `asia-east1`. If no region is specified, the region will be determined based on video file location. */
		locationId: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1_VideoContext(
	  /** Video segments to annotate. The segments may overlap and are not required to be contiguous or span the whole video. If unspecified, each video is treated as a single segment. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1_VideoSegment]] = None,
	  /** Config for LABEL_DETECTION. */
		labelDetectionConfig: Option[Schema.GoogleCloudVideointelligenceV1_LabelDetectionConfig] = None,
	  /** Config for SHOT_CHANGE_DETECTION. */
		shotChangeDetectionConfig: Option[Schema.GoogleCloudVideointelligenceV1_ShotChangeDetectionConfig] = None,
	  /** Config for EXPLICIT_CONTENT_DETECTION. */
		explicitContentDetectionConfig: Option[Schema.GoogleCloudVideointelligenceV1_ExplicitContentDetectionConfig] = None,
	  /** Config for FACE_DETECTION. */
		faceDetectionConfig: Option[Schema.GoogleCloudVideointelligenceV1_FaceDetectionConfig] = None,
	  /** Config for SPEECH_TRANSCRIPTION. */
		speechTranscriptionConfig: Option[Schema.GoogleCloudVideointelligenceV1_SpeechTranscriptionConfig] = None,
	  /** Config for TEXT_DETECTION. */
		textDetectionConfig: Option[Schema.GoogleCloudVideointelligenceV1_TextDetectionConfig] = None,
	  /** Config for PERSON_DETECTION. */
		personDetectionConfig: Option[Schema.GoogleCloudVideointelligenceV1_PersonDetectionConfig] = None,
	  /** Config for OBJECT_TRACKING. */
		objectTrackingConfig: Option[Schema.GoogleCloudVideointelligenceV1_ObjectTrackingConfig] = None
	)
	
	case class GoogleCloudVideointelligenceV1_VideoSegment(
	  /** Time-offset, relative to the beginning of the video, corresponding to the start of the segment (inclusive). */
		startTimeOffset: Option[String] = None,
	  /** Time-offset, relative to the beginning of the video, corresponding to the end of the segment (inclusive). */
		endTimeOffset: Option[String] = None
	)
	
	object GoogleCloudVideointelligenceV1_LabelDetectionConfig {
		enum LabelDetectionModeEnum extends Enum[LabelDetectionModeEnum] { case LABEL_DETECTION_MODE_UNSPECIFIED, SHOT_MODE, FRAME_MODE, SHOT_AND_FRAME_MODE }
	}
	case class GoogleCloudVideointelligenceV1_LabelDetectionConfig(
	  /** What labels should be detected with LABEL_DETECTION, in addition to video-level labels or segment-level labels. If unspecified, defaults to `SHOT_MODE`. */
		labelDetectionMode: Option[Schema.GoogleCloudVideointelligenceV1_LabelDetectionConfig.LabelDetectionModeEnum] = None,
	  /** Whether the video has been shot from a stationary (i.e., non-moving) camera. When set to true, might improve detection accuracy for moving objects. Should be used with `SHOT_AND_FRAME_MODE` enabled. */
		stationaryCamera: Option[Boolean] = None,
	  /** Model to use for label detection. Supported values: "builtin/stable" (the default if unset) and "builtin/latest". */
		model: Option[String] = None,
	  /** The confidence threshold we perform filtering on the labels from frame-level detection. If not set, it is set to 0.4 by default. The valid range for this threshold is [0.1, 0.9]. Any value set outside of this range will be clipped. Note: For best results, follow the default threshold. We will update the default threshold everytime when we release a new model. */
		frameConfidenceThreshold: Option[BigDecimal] = None,
	  /** The confidence threshold we perform filtering on the labels from video-level and shot-level detections. If not set, it's set to 0.3 by default. The valid range for this threshold is [0.1, 0.9]. Any value set outside of this range will be clipped. Note: For best results, follow the default threshold. We will update the default threshold everytime when we release a new model. */
		videoConfidenceThreshold: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1_ShotChangeDetectionConfig(
	  /** Model to use for shot change detection. Supported values: "builtin/stable" (the default if unset), "builtin/latest", and "builtin/legacy". */
		model: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1_ExplicitContentDetectionConfig(
	  /** Model to use for explicit content detection. Supported values: "builtin/stable" (the default if unset) and "builtin/latest". */
		model: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1_FaceDetectionConfig(
	  /** Model to use for face detection. Supported values: "builtin/stable" (the default if unset) and "builtin/latest". */
		model: Option[String] = None,
	  /** Whether bounding boxes are included in the face annotation output. */
		includeBoundingBoxes: Option[Boolean] = None,
	  /** Whether to enable face attributes detection, such as glasses, dark_glasses, mouth_open etc. Ignored if 'include_bounding_boxes' is set to false. */
		includeAttributes: Option[Boolean] = None
	)
	
	case class GoogleCloudVideointelligenceV1_SpeechTranscriptionConfig(
	  /** Required. &#42;Required&#42; The language of the supplied audio as a [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt) language tag. Example: "en-US". See [Language Support](https://cloud.google.com/speech/docs/languages) for a list of the currently supported language codes. */
		languageCode: Option[String] = None,
	  /** Optional. Maximum number of recognition hypotheses to be returned. Specifically, the maximum number of `SpeechRecognitionAlternative` messages within each `SpeechTranscription`. The server may return fewer than `max_alternatives`. Valid values are `0`-`30`. A value of `0` or `1` will return a maximum of one. If omitted, will return a maximum of one. */
		maxAlternatives: Option[Int] = None,
	  /** Optional. If set to `true`, the server will attempt to filter out profanities, replacing all but the initial character in each filtered word with asterisks, e.g. "f&#42;&#42;&#42;". If set to `false` or omitted, profanities won't be filtered out. */
		filterProfanity: Option[Boolean] = None,
	  /** Optional. A means to provide context to assist the speech recognition. */
		speechContexts: Option[List[Schema.GoogleCloudVideointelligenceV1_SpeechContext]] = None,
	  /** Optional. If 'true', adds punctuation to recognition result hypotheses. This feature is only available in select languages. Setting this for requests in other languages has no effect at all. The default 'false' value does not add punctuation to result hypotheses. NOTE: "This is currently offered as an experimental service, complimentary to all users. In the future this may be exclusively available as a premium feature." */
		enableAutomaticPunctuation: Option[Boolean] = None,
	  /** Optional. For file formats, such as MXF or MKV, supporting multiple audio tracks, specify up to two tracks. Default: track 0. */
		audioTracks: Option[List[Int]] = None,
	  /** Optional. If 'true', enables speaker detection for each recognized word in the top alternative of the recognition result using a speaker_tag provided in the WordInfo. Note: When this is true, we send all the words from the beginning of the audio for the top alternative in every consecutive response. This is done in order to improve our speaker tags as our models learn to identify the speakers in the conversation over time. */
		enableSpeakerDiarization: Option[Boolean] = None,
	  /** Optional. If set, specifies the estimated number of speakers in the conversation. If not set, defaults to '2'. Ignored unless enable_speaker_diarization is set to true. */
		diarizationSpeakerCount: Option[Int] = None,
	  /** Optional. If `true`, the top result includes a list of words and the confidence for those words. If `false`, no word-level confidence information is returned. The default is `false`. */
		enableWordConfidence: Option[Boolean] = None
	)
	
	case class GoogleCloudVideointelligenceV1_SpeechContext(
	  /** Optional. A list of strings containing words and phrases "hints" so that the speech recognition is more likely to recognize them. This can be used to improve the accuracy for specific words and phrases, for example, if specific commands are typically spoken by the user. This can also be used to add additional words to the vocabulary of the recognizer. See [usage limits](https://cloud.google.com/speech/limits#content). */
		phrases: Option[List[String]] = None
	)
	
	case class GoogleCloudVideointelligenceV1_TextDetectionConfig(
	  /** Language hint can be specified if the language to be detected is known a priori. It can increase the accuracy of the detection. Language hint must be language code in BCP-47 format. Automatic language detection is performed if no hint is provided. */
		languageHints: Option[List[String]] = None,
	  /** Model to use for text detection. Supported values: "builtin/stable" (the default if unset) and "builtin/latest". */
		model: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1_PersonDetectionConfig(
	  /** Whether bounding boxes are included in the person detection annotation output. */
		includeBoundingBoxes: Option[Boolean] = None,
	  /** Whether to enable pose landmarks detection. Ignored if 'include_bounding_boxes' is set to false. */
		includePoseLandmarks: Option[Boolean] = None,
	  /** Whether to enable person attributes detection, such as cloth color (black, blue, etc), type (coat, dress, etc), pattern (plain, floral, etc), hair, etc. Ignored if 'include_bounding_boxes' is set to false. */
		includeAttributes: Option[Boolean] = None
	)
	
	case class GoogleCloudVideointelligenceV1_ObjectTrackingConfig(
	  /** Model to use for object tracking. Supported values: "builtin/stable" (the default if unset) and "builtin/latest". */
		model: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_AnnotateVideoResponse(
	  /** Annotation results for all videos specified in `AnnotateVideoRequest`. */
		annotationResults: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_VideoAnnotationResults]] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_VideoAnnotationResults(
	  /** Video file location in [Cloud Storage](https://cloud.google.com/storage/). */
		inputUri: Option[String] = None,
	  /** Video segment on which the annotation is run. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1beta2_VideoSegment] = None,
	  /** Topical label annotations on video level or user-specified segment level. There is exactly one element for each unique label. */
		segmentLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_LabelAnnotation]] = None,
	  /** Presence label annotations on video level or user-specified segment level. There is exactly one element for each unique label. Compared to the existing topical `segment_label_annotations`, this field presents more fine-grained, segment-level labels detected in video content and is made available only when the client sets `LabelDetectionConfig.model` to "builtin/latest" in the request. */
		segmentPresenceLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_LabelAnnotation]] = None,
	  /** Topical label annotations on shot level. There is exactly one element for each unique label. */
		shotLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_LabelAnnotation]] = None,
	  /** Presence label annotations on shot level. There is exactly one element for each unique label. Compared to the existing topical `shot_label_annotations`, this field presents more fine-grained, shot-level labels detected in video content and is made available only when the client sets `LabelDetectionConfig.model` to "builtin/latest" in the request. */
		shotPresenceLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_LabelAnnotation]] = None,
	  /** Label annotations on frame level. There is exactly one element for each unique label. */
		frameLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_LabelAnnotation]] = None,
	  /** Deprecated. Please use `face_detection_annotations` instead. */
		faceAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_FaceAnnotation]] = None,
	  /** Face detection annotations. */
		faceDetectionAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_FaceDetectionAnnotation]] = None,
	  /** Shot annotations. Each shot is represented as a video segment. */
		shotAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_VideoSegment]] = None,
	  /** Explicit content annotation. */
		explicitAnnotation: Option[Schema.GoogleCloudVideointelligenceV1beta2_ExplicitContentAnnotation] = None,
	  /** Speech transcription. */
		speechTranscriptions: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_SpeechTranscription]] = None,
	  /** OCR text detection and tracking. Annotations for list of detected text snippets. Each will have list of frame information associated with it. */
		textAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_TextAnnotation]] = None,
	  /** Annotations for list of objects detected and tracked in video. */
		objectAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_ObjectTrackingAnnotation]] = None,
	  /** Annotations for list of logos detected, tracked and recognized in video. */
		logoRecognitionAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_LogoRecognitionAnnotation]] = None,
	  /** Person detection annotations. */
		personDetectionAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_PersonDetectionAnnotation]] = None,
	  /** If set, indicates an error. Note that for a single `AnnotateVideoRequest` some videos may succeed and some may fail. */
		error: Option[Schema.GoogleRpc_Status] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_VideoSegment(
	  /** Time-offset, relative to the beginning of the video, corresponding to the start of the segment (inclusive). */
		startTimeOffset: Option[String] = None,
	  /** Time-offset, relative to the beginning of the video, corresponding to the end of the segment (inclusive). */
		endTimeOffset: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_LabelAnnotation(
	  /** Detected entity. */
		entity: Option[Schema.GoogleCloudVideointelligenceV1beta2_Entity] = None,
	  /** Common categories for the detected entity. For example, when the label is `Terrier`, the category is likely `dog`. And in some cases there might be more than one categories e.g., `Terrier` could also be a `pet`. */
		categoryEntities: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_Entity]] = None,
	  /** All video segments where a label was detected. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_LabelSegment]] = None,
	  /** All video frames where a label was detected. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_LabelFrame]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_Entity(
	  /** Opaque entity ID. Some IDs may be available in [Google Knowledge Graph Search API](https://developers.google.com/knowledge-graph/). */
		entityId: Option[String] = None,
	  /** Textual description, e.g., `Fixed-gear bicycle`. */
		description: Option[String] = None,
	  /** Language code for `description` in BCP-47 format. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_LabelSegment(
	  /** Video segment where a label was detected. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1beta2_VideoSegment] = None,
	  /** Confidence that the label is accurate. Range: [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_LabelFrame(
	  /** Time-offset, relative to the beginning of the video, corresponding to the video frame for this location. */
		timeOffset: Option[String] = None,
	  /** Confidence that the label is accurate. Range: [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_FaceAnnotation(
	  /** Thumbnail of a representative face view (in JPEG format). */
		thumbnail: Option[String] = None,
	  /** All video segments where a face was detected. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_FaceSegment]] = None,
	  /** All video frames where a face was detected. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_FaceFrame]] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_FaceSegment(
	  /** Video segment where a face was detected. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1beta2_VideoSegment] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_FaceFrame(
	  /** Normalized Bounding boxes in a frame. There can be more than one boxes if the same face is detected in multiple locations within the current frame. */
		normalizedBoundingBoxes: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_NormalizedBoundingBox]] = None,
	  /** Time-offset, relative to the beginning of the video, corresponding to the video frame for this location. */
		timeOffset: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_NormalizedBoundingBox(
	  /** Left X coordinate. */
		left: Option[BigDecimal] = None,
	  /** Top Y coordinate. */
		top: Option[BigDecimal] = None,
	  /** Right X coordinate. */
		right: Option[BigDecimal] = None,
	  /** Bottom Y coordinate. */
		bottom: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_FaceDetectionAnnotation(
	  /** The face tracks with attributes. */
		tracks: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_Track]] = None,
	  /** The thumbnail of a person's face. */
		thumbnail: Option[String] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_Track(
	  /** Video segment of a track. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1beta2_VideoSegment] = None,
	  /** The object with timestamp and attributes per frame in the track. */
		timestampedObjects: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_TimestampedObject]] = None,
	  /** Optional. Attributes in the track level. */
		attributes: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_DetectedAttribute]] = None,
	  /** Optional. The confidence score of the tracked object. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_TimestampedObject(
	  /** Normalized Bounding box in a frame, where the object is located. */
		normalizedBoundingBox: Option[Schema.GoogleCloudVideointelligenceV1beta2_NormalizedBoundingBox] = None,
	  /** Time-offset, relative to the beginning of the video, corresponding to the video frame for this object. */
		timeOffset: Option[String] = None,
	  /** Optional. The attributes of the object in the bounding box. */
		attributes: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_DetectedAttribute]] = None,
	  /** Optional. The detected landmarks. */
		landmarks: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_DetectedLandmark]] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_DetectedAttribute(
	  /** The name of the attribute, for example, glasses, dark_glasses, mouth_open. A full list of supported type names will be provided in the document. */
		name: Option[String] = None,
	  /** Detected attribute confidence. Range [0, 1]. */
		confidence: Option[BigDecimal] = None,
	  /** Text value of the detection result. For example, the value for "HairColor" can be "black", "blonde", etc. */
		value: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_DetectedLandmark(
	  /** The name of this landmark, for example, left_hand, right_shoulder. */
		name: Option[String] = None,
	  /** The 2D point of the detected landmark using the normalized image coordindate system. The normalized coordinates have the range from 0 to 1. */
		point: Option[Schema.GoogleCloudVideointelligenceV1beta2_NormalizedVertex] = None,
	  /** The confidence score of the detected landmark. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_NormalizedVertex(
	  /** X coordinate. */
		x: Option[BigDecimal] = None,
	  /** Y coordinate. */
		y: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_ExplicitContentAnnotation(
	  /** All video frames where explicit content was detected. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_ExplicitContentFrame]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	object GoogleCloudVideointelligenceV1beta2_ExplicitContentFrame {
		enum PornographyLikelihoodEnum extends Enum[PornographyLikelihoodEnum] { case LIKELIHOOD_UNSPECIFIED, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
	}
	case class GoogleCloudVideointelligenceV1beta2_ExplicitContentFrame(
	  /** Time-offset, relative to the beginning of the video, corresponding to the video frame for this location. */
		timeOffset: Option[String] = None,
	  /** Likelihood of the pornography content.. */
		pornographyLikelihood: Option[Schema.GoogleCloudVideointelligenceV1beta2_ExplicitContentFrame.PornographyLikelihoodEnum] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_SpeechTranscription(
	  /** May contain one or more recognition hypotheses (up to the maximum specified in `max_alternatives`). These alternatives are ordered in terms of accuracy, with the top (first) alternative being the most probable, as ranked by the recognizer. */
		alternatives: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_SpeechRecognitionAlternative]] = None,
	  /** Output only. The [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt) language tag of the language in this result. This language code was detected to have the most likelihood of being spoken in the audio. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_SpeechRecognitionAlternative(
	  /** Transcript text representing the words that the user spoke. */
		transcript: Option[String] = None,
	  /** Output only. The confidence estimate between 0.0 and 1.0. A higher number indicates an estimated greater likelihood that the recognized words are correct. This field is set only for the top alternative. This field is not guaranteed to be accurate and users should not rely on it to be always provided. The default of 0.0 is a sentinel value indicating `confidence` was not set. */
		confidence: Option[BigDecimal] = None,
	  /** Output only. A list of word-specific information for each recognized word. Note: When `enable_speaker_diarization` is set to true, you will see all the words from the beginning of the audio. */
		words: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_WordInfo]] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_WordInfo(
	  /** Time offset relative to the beginning of the audio, and corresponding to the start of the spoken word. This field is only set if `enable_word_time_offsets=true` and only in the top hypothesis. This is an experimental feature and the accuracy of the time offset can vary. */
		startTime: Option[String] = None,
	  /** Time offset relative to the beginning of the audio, and corresponding to the end of the spoken word. This field is only set if `enable_word_time_offsets=true` and only in the top hypothesis. This is an experimental feature and the accuracy of the time offset can vary. */
		endTime: Option[String] = None,
	  /** The word corresponding to this set of information. */
		word: Option[String] = None,
	  /** Output only. The confidence estimate between 0.0 and 1.0. A higher number indicates an estimated greater likelihood that the recognized words are correct. This field is set only for the top alternative. This field is not guaranteed to be accurate and users should not rely on it to be always provided. The default of 0.0 is a sentinel value indicating `confidence` was not set. */
		confidence: Option[BigDecimal] = None,
	  /** Output only. A distinct integer value is assigned for every speaker within the audio. This field specifies which one of those speakers was detected to have spoken this word. Value ranges from 1 up to diarization_speaker_count, and is only set if speaker diarization is enabled. */
		speakerTag: Option[Int] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_TextAnnotation(
	  /** The detected text. */
		text: Option[String] = None,
	  /** All video segments where OCR detected text appears. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_TextSegment]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_TextSegment(
	  /** Video segment where a text snippet was detected. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1beta2_VideoSegment] = None,
	  /** Confidence for the track of detected text. It is calculated as the highest over all frames where OCR detected text appears. */
		confidence: Option[BigDecimal] = None,
	  /** Information related to the frames where OCR detected text appears. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_TextFrame]] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_TextFrame(
	  /** Bounding polygon of the detected text for this frame. */
		rotatedBoundingBox: Option[Schema.GoogleCloudVideointelligenceV1beta2_NormalizedBoundingPoly] = None,
	  /** Timestamp of this frame. */
		timeOffset: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_NormalizedBoundingPoly(
	  /** Normalized vertices of the bounding polygon. */
		vertices: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_NormalizedVertex]] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_ObjectTrackingAnnotation(
	  /** Non-streaming batch mode ONLY. Each object track corresponds to one video segment where it appears. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1beta2_VideoSegment] = None,
	  /** Streaming mode ONLY. In streaming mode, we do not know the end time of a tracked object before it is completed. Hence, there is no VideoSegment info returned. Instead, we provide a unique identifiable integer track_id so that the customers can correlate the results of the ongoing ObjectTrackAnnotation of the same track_id over time. */
		trackId: Option[String] = None,
	  /** Entity to specify the object category that this track is labeled as. */
		entity: Option[Schema.GoogleCloudVideointelligenceV1beta2_Entity] = None,
	  /** Object category's labeling confidence of this track. */
		confidence: Option[BigDecimal] = None,
	  /** Information corresponding to all frames where this object track appears. Non-streaming batch mode: it may be one or multiple ObjectTrackingFrame messages in frames. Streaming mode: it can only be one ObjectTrackingFrame message in frames. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_ObjectTrackingFrame]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_ObjectTrackingFrame(
	  /** The normalized bounding box location of this object track for the frame. */
		normalizedBoundingBox: Option[Schema.GoogleCloudVideointelligenceV1beta2_NormalizedBoundingBox] = None,
	  /** The timestamp of the frame in microseconds. */
		timeOffset: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_LogoRecognitionAnnotation(
	  /** Entity category information to specify the logo class that all the logo tracks within this LogoRecognitionAnnotation are recognized as. */
		entity: Option[Schema.GoogleCloudVideointelligenceV1beta2_Entity] = None,
	  /** All logo tracks where the recognized logo appears. Each track corresponds to one logo instance appearing in consecutive frames. */
		tracks: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_Track]] = None,
	  /** All video segments where the recognized logo appears. There might be multiple instances of the same logo class appearing in one VideoSegment. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_VideoSegment]] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_PersonDetectionAnnotation(
	  /** The detected tracks of a person. */
		tracks: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_Track]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1beta2_AnnotateVideoProgress(
	  /** Progress metadata for all videos specified in `AnnotateVideoRequest`. */
		annotationProgress: Option[List[Schema.GoogleCloudVideointelligenceV1beta2_VideoAnnotationProgress]] = None
	)
	
	object GoogleCloudVideointelligenceV1beta2_VideoAnnotationProgress {
		enum FeatureEnum extends Enum[FeatureEnum] { case FEATURE_UNSPECIFIED, LABEL_DETECTION, SHOT_CHANGE_DETECTION, EXPLICIT_CONTENT_DETECTION, FACE_DETECTION, SPEECH_TRANSCRIPTION, TEXT_DETECTION, OBJECT_TRACKING, LOGO_RECOGNITION, PERSON_DETECTION }
	}
	case class GoogleCloudVideointelligenceV1beta2_VideoAnnotationProgress(
	  /** Video file location in [Cloud Storage](https://cloud.google.com/storage/). */
		inputUri: Option[String] = None,
	  /** Approximate percentage processed thus far. Guaranteed to be 100 when fully processed. */
		progressPercent: Option[Int] = None,
	  /** Time when the request was received. */
		startTime: Option[String] = None,
	  /** Time of the most recent update. */
		updateTime: Option[String] = None,
	  /** Specifies which feature is being tracked if the request contains more than one feature. */
		feature: Option[Schema.GoogleCloudVideointelligenceV1beta2_VideoAnnotationProgress.FeatureEnum] = None,
	  /** Specifies which segment is being tracked if the request contains more than one segment. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1beta2_VideoSegment] = None
	)
	
	case class GoogleCloudVideointelligenceV1_AnnotateVideoResponse(
	  /** Annotation results for all videos specified in `AnnotateVideoRequest`. */
		annotationResults: Option[List[Schema.GoogleCloudVideointelligenceV1_VideoAnnotationResults]] = None
	)
	
	case class GoogleCloudVideointelligenceV1_VideoAnnotationResults(
	  /** Video file location in [Cloud Storage](https://cloud.google.com/storage/). */
		inputUri: Option[String] = None,
	  /** Video segment on which the annotation is run. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1_VideoSegment] = None,
	  /** Topical label annotations on video level or user-specified segment level. There is exactly one element for each unique label. */
		segmentLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1_LabelAnnotation]] = None,
	  /** Presence label annotations on video level or user-specified segment level. There is exactly one element for each unique label. Compared to the existing topical `segment_label_annotations`, this field presents more fine-grained, segment-level labels detected in video content and is made available only when the client sets `LabelDetectionConfig.model` to "builtin/latest" in the request. */
		segmentPresenceLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1_LabelAnnotation]] = None,
	  /** Topical label annotations on shot level. There is exactly one element for each unique label. */
		shotLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1_LabelAnnotation]] = None,
	  /** Presence label annotations on shot level. There is exactly one element for each unique label. Compared to the existing topical `shot_label_annotations`, this field presents more fine-grained, shot-level labels detected in video content and is made available only when the client sets `LabelDetectionConfig.model` to "builtin/latest" in the request. */
		shotPresenceLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1_LabelAnnotation]] = None,
	  /** Label annotations on frame level. There is exactly one element for each unique label. */
		frameLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1_LabelAnnotation]] = None,
	  /** Deprecated. Please use `face_detection_annotations` instead. */
		faceAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1_FaceAnnotation]] = None,
	  /** Face detection annotations. */
		faceDetectionAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1_FaceDetectionAnnotation]] = None,
	  /** Shot annotations. Each shot is represented as a video segment. */
		shotAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1_VideoSegment]] = None,
	  /** Explicit content annotation. */
		explicitAnnotation: Option[Schema.GoogleCloudVideointelligenceV1_ExplicitContentAnnotation] = None,
	  /** Speech transcription. */
		speechTranscriptions: Option[List[Schema.GoogleCloudVideointelligenceV1_SpeechTranscription]] = None,
	  /** OCR text detection and tracking. Annotations for list of detected text snippets. Each will have list of frame information associated with it. */
		textAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1_TextAnnotation]] = None,
	  /** Annotations for list of objects detected and tracked in video. */
		objectAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1_ObjectTrackingAnnotation]] = None,
	  /** Annotations for list of logos detected, tracked and recognized in video. */
		logoRecognitionAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1_LogoRecognitionAnnotation]] = None,
	  /** Person detection annotations. */
		personDetectionAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1_PersonDetectionAnnotation]] = None,
	  /** If set, indicates an error. Note that for a single `AnnotateVideoRequest` some videos may succeed and some may fail. */
		error: Option[Schema.GoogleRpc_Status] = None
	)
	
	case class GoogleCloudVideointelligenceV1_LabelAnnotation(
	  /** Detected entity. */
		entity: Option[Schema.GoogleCloudVideointelligenceV1_Entity] = None,
	  /** Common categories for the detected entity. For example, when the label is `Terrier`, the category is likely `dog`. And in some cases there might be more than one categories e.g., `Terrier` could also be a `pet`. */
		categoryEntities: Option[List[Schema.GoogleCloudVideointelligenceV1_Entity]] = None,
	  /** All video segments where a label was detected. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1_LabelSegment]] = None,
	  /** All video frames where a label was detected. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1_LabelFrame]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1_Entity(
	  /** Opaque entity ID. Some IDs may be available in [Google Knowledge Graph Search API](https://developers.google.com/knowledge-graph/). */
		entityId: Option[String] = None,
	  /** Textual description, e.g., `Fixed-gear bicycle`. */
		description: Option[String] = None,
	  /** Language code for `description` in BCP-47 format. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1_LabelSegment(
	  /** Video segment where a label was detected. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1_VideoSegment] = None,
	  /** Confidence that the label is accurate. Range: [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1_LabelFrame(
	  /** Time-offset, relative to the beginning of the video, corresponding to the video frame for this location. */
		timeOffset: Option[String] = None,
	  /** Confidence that the label is accurate. Range: [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1_FaceAnnotation(
	  /** Thumbnail of a representative face view (in JPEG format). */
		thumbnail: Option[String] = None,
	  /** All video segments where a face was detected. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1_FaceSegment]] = None,
	  /** All video frames where a face was detected. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1_FaceFrame]] = None
	)
	
	case class GoogleCloudVideointelligenceV1_FaceSegment(
	  /** Video segment where a face was detected. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1_VideoSegment] = None
	)
	
	case class GoogleCloudVideointelligenceV1_FaceFrame(
	  /** Normalized Bounding boxes in a frame. There can be more than one boxes if the same face is detected in multiple locations within the current frame. */
		normalizedBoundingBoxes: Option[List[Schema.GoogleCloudVideointelligenceV1_NormalizedBoundingBox]] = None,
	  /** Time-offset, relative to the beginning of the video, corresponding to the video frame for this location. */
		timeOffset: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1_NormalizedBoundingBox(
	  /** Left X coordinate. */
		left: Option[BigDecimal] = None,
	  /** Top Y coordinate. */
		top: Option[BigDecimal] = None,
	  /** Right X coordinate. */
		right: Option[BigDecimal] = None,
	  /** Bottom Y coordinate. */
		bottom: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1_FaceDetectionAnnotation(
	  /** The face tracks with attributes. */
		tracks: Option[List[Schema.GoogleCloudVideointelligenceV1_Track]] = None,
	  /** The thumbnail of a person's face. */
		thumbnail: Option[String] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1_Track(
	  /** Video segment of a track. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1_VideoSegment] = None,
	  /** The object with timestamp and attributes per frame in the track. */
		timestampedObjects: Option[List[Schema.GoogleCloudVideointelligenceV1_TimestampedObject]] = None,
	  /** Optional. Attributes in the track level. */
		attributes: Option[List[Schema.GoogleCloudVideointelligenceV1_DetectedAttribute]] = None,
	  /** Optional. The confidence score of the tracked object. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1_TimestampedObject(
	  /** Normalized Bounding box in a frame, where the object is located. */
		normalizedBoundingBox: Option[Schema.GoogleCloudVideointelligenceV1_NormalizedBoundingBox] = None,
	  /** Time-offset, relative to the beginning of the video, corresponding to the video frame for this object. */
		timeOffset: Option[String] = None,
	  /** Optional. The attributes of the object in the bounding box. */
		attributes: Option[List[Schema.GoogleCloudVideointelligenceV1_DetectedAttribute]] = None,
	  /** Optional. The detected landmarks. */
		landmarks: Option[List[Schema.GoogleCloudVideointelligenceV1_DetectedLandmark]] = None
	)
	
	case class GoogleCloudVideointelligenceV1_DetectedAttribute(
	  /** The name of the attribute, for example, glasses, dark_glasses, mouth_open. A full list of supported type names will be provided in the document. */
		name: Option[String] = None,
	  /** Detected attribute confidence. Range [0, 1]. */
		confidence: Option[BigDecimal] = None,
	  /** Text value of the detection result. For example, the value for "HairColor" can be "black", "blonde", etc. */
		value: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1_DetectedLandmark(
	  /** The name of this landmark, for example, left_hand, right_shoulder. */
		name: Option[String] = None,
	  /** The 2D point of the detected landmark using the normalized image coordindate system. The normalized coordinates have the range from 0 to 1. */
		point: Option[Schema.GoogleCloudVideointelligenceV1_NormalizedVertex] = None,
	  /** The confidence score of the detected landmark. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1_NormalizedVertex(
	  /** X coordinate. */
		x: Option[BigDecimal] = None,
	  /** Y coordinate. */
		y: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1_ExplicitContentAnnotation(
	  /** All video frames where explicit content was detected. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1_ExplicitContentFrame]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	object GoogleCloudVideointelligenceV1_ExplicitContentFrame {
		enum PornographyLikelihoodEnum extends Enum[PornographyLikelihoodEnum] { case LIKELIHOOD_UNSPECIFIED, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
	}
	case class GoogleCloudVideointelligenceV1_ExplicitContentFrame(
	  /** Time-offset, relative to the beginning of the video, corresponding to the video frame for this location. */
		timeOffset: Option[String] = None,
	  /** Likelihood of the pornography content.. */
		pornographyLikelihood: Option[Schema.GoogleCloudVideointelligenceV1_ExplicitContentFrame.PornographyLikelihoodEnum] = None
	)
	
	case class GoogleCloudVideointelligenceV1_SpeechTranscription(
	  /** May contain one or more recognition hypotheses (up to the maximum specified in `max_alternatives`). These alternatives are ordered in terms of accuracy, with the top (first) alternative being the most probable, as ranked by the recognizer. */
		alternatives: Option[List[Schema.GoogleCloudVideointelligenceV1_SpeechRecognitionAlternative]] = None,
	  /** Output only. The [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt) language tag of the language in this result. This language code was detected to have the most likelihood of being spoken in the audio. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1_SpeechRecognitionAlternative(
	  /** Transcript text representing the words that the user spoke. */
		transcript: Option[String] = None,
	  /** Output only. The confidence estimate between 0.0 and 1.0. A higher number indicates an estimated greater likelihood that the recognized words are correct. This field is set only for the top alternative. This field is not guaranteed to be accurate and users should not rely on it to be always provided. The default of 0.0 is a sentinel value indicating `confidence` was not set. */
		confidence: Option[BigDecimal] = None,
	  /** Output only. A list of word-specific information for each recognized word. Note: When `enable_speaker_diarization` is set to true, you will see all the words from the beginning of the audio. */
		words: Option[List[Schema.GoogleCloudVideointelligenceV1_WordInfo]] = None
	)
	
	case class GoogleCloudVideointelligenceV1_WordInfo(
	  /** Time offset relative to the beginning of the audio, and corresponding to the start of the spoken word. This field is only set if `enable_word_time_offsets=true` and only in the top hypothesis. This is an experimental feature and the accuracy of the time offset can vary. */
		startTime: Option[String] = None,
	  /** Time offset relative to the beginning of the audio, and corresponding to the end of the spoken word. This field is only set if `enable_word_time_offsets=true` and only in the top hypothesis. This is an experimental feature and the accuracy of the time offset can vary. */
		endTime: Option[String] = None,
	  /** The word corresponding to this set of information. */
		word: Option[String] = None,
	  /** Output only. The confidence estimate between 0.0 and 1.0. A higher number indicates an estimated greater likelihood that the recognized words are correct. This field is set only for the top alternative. This field is not guaranteed to be accurate and users should not rely on it to be always provided. The default of 0.0 is a sentinel value indicating `confidence` was not set. */
		confidence: Option[BigDecimal] = None,
	  /** Output only. A distinct integer value is assigned for every speaker within the audio. This field specifies which one of those speakers was detected to have spoken this word. Value ranges from 1 up to diarization_speaker_count, and is only set if speaker diarization is enabled. */
		speakerTag: Option[Int] = None
	)
	
	case class GoogleCloudVideointelligenceV1_TextAnnotation(
	  /** The detected text. */
		text: Option[String] = None,
	  /** All video segments where OCR detected text appears. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1_TextSegment]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1_TextSegment(
	  /** Video segment where a text snippet was detected. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1_VideoSegment] = None,
	  /** Confidence for the track of detected text. It is calculated as the highest over all frames where OCR detected text appears. */
		confidence: Option[BigDecimal] = None,
	  /** Information related to the frames where OCR detected text appears. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1_TextFrame]] = None
	)
	
	case class GoogleCloudVideointelligenceV1_TextFrame(
	  /** Bounding polygon of the detected text for this frame. */
		rotatedBoundingBox: Option[Schema.GoogleCloudVideointelligenceV1_NormalizedBoundingPoly] = None,
	  /** Timestamp of this frame. */
		timeOffset: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1_NormalizedBoundingPoly(
	  /** Normalized vertices of the bounding polygon. */
		vertices: Option[List[Schema.GoogleCloudVideointelligenceV1_NormalizedVertex]] = None
	)
	
	case class GoogleCloudVideointelligenceV1_ObjectTrackingAnnotation(
	  /** Non-streaming batch mode ONLY. Each object track corresponds to one video segment where it appears. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1_VideoSegment] = None,
	  /** Streaming mode ONLY. In streaming mode, we do not know the end time of a tracked object before it is completed. Hence, there is no VideoSegment info returned. Instead, we provide a unique identifiable integer track_id so that the customers can correlate the results of the ongoing ObjectTrackAnnotation of the same track_id over time. */
		trackId: Option[String] = None,
	  /** Entity to specify the object category that this track is labeled as. */
		entity: Option[Schema.GoogleCloudVideointelligenceV1_Entity] = None,
	  /** Object category's labeling confidence of this track. */
		confidence: Option[BigDecimal] = None,
	  /** Information corresponding to all frames where this object track appears. Non-streaming batch mode: it may be one or multiple ObjectTrackingFrame messages in frames. Streaming mode: it can only be one ObjectTrackingFrame message in frames. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1_ObjectTrackingFrame]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1_ObjectTrackingFrame(
	  /** The normalized bounding box location of this object track for the frame. */
		normalizedBoundingBox: Option[Schema.GoogleCloudVideointelligenceV1_NormalizedBoundingBox] = None,
	  /** The timestamp of the frame in microseconds. */
		timeOffset: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1_LogoRecognitionAnnotation(
	  /** Entity category information to specify the logo class that all the logo tracks within this LogoRecognitionAnnotation are recognized as. */
		entity: Option[Schema.GoogleCloudVideointelligenceV1_Entity] = None,
	  /** All logo tracks where the recognized logo appears. Each track corresponds to one logo instance appearing in consecutive frames. */
		tracks: Option[List[Schema.GoogleCloudVideointelligenceV1_Track]] = None,
	  /** All video segments where the recognized logo appears. There might be multiple instances of the same logo class appearing in one VideoSegment. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1_VideoSegment]] = None
	)
	
	case class GoogleCloudVideointelligenceV1_PersonDetectionAnnotation(
	  /** The detected tracks of a person. */
		tracks: Option[List[Schema.GoogleCloudVideointelligenceV1_Track]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1_AnnotateVideoProgress(
	  /** Progress metadata for all videos specified in `AnnotateVideoRequest`. */
		annotationProgress: Option[List[Schema.GoogleCloudVideointelligenceV1_VideoAnnotationProgress]] = None
	)
	
	object GoogleCloudVideointelligenceV1_VideoAnnotationProgress {
		enum FeatureEnum extends Enum[FeatureEnum] { case FEATURE_UNSPECIFIED, LABEL_DETECTION, SHOT_CHANGE_DETECTION, EXPLICIT_CONTENT_DETECTION, FACE_DETECTION, SPEECH_TRANSCRIPTION, TEXT_DETECTION, OBJECT_TRACKING, LOGO_RECOGNITION, PERSON_DETECTION }
	}
	case class GoogleCloudVideointelligenceV1_VideoAnnotationProgress(
	  /** Video file location in [Cloud Storage](https://cloud.google.com/storage/). */
		inputUri: Option[String] = None,
	  /** Approximate percentage processed thus far. Guaranteed to be 100 when fully processed. */
		progressPercent: Option[Int] = None,
	  /** Time when the request was received. */
		startTime: Option[String] = None,
	  /** Time of the most recent update. */
		updateTime: Option[String] = None,
	  /** Specifies which feature is being tracked if the request contains more than one feature. */
		feature: Option[Schema.GoogleCloudVideointelligenceV1_VideoAnnotationProgress.FeatureEnum] = None,
	  /** Specifies which segment is being tracked if the request contains more than one segment. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1_VideoSegment] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_AnnotateVideoResponse(
	  /** Annotation results for all videos specified in `AnnotateVideoRequest`. */
		annotationResults: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_VideoAnnotationResults]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_VideoAnnotationResults(
	  /** Video file location in [Cloud Storage](https://cloud.google.com/storage/). */
		inputUri: Option[String] = None,
	  /** Video segment on which the annotation is run. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p1beta1_VideoSegment] = None,
	  /** Topical label annotations on video level or user-specified segment level. There is exactly one element for each unique label. */
		segmentLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_LabelAnnotation]] = None,
	  /** Presence label annotations on video level or user-specified segment level. There is exactly one element for each unique label. Compared to the existing topical `segment_label_annotations`, this field presents more fine-grained, segment-level labels detected in video content and is made available only when the client sets `LabelDetectionConfig.model` to "builtin/latest" in the request. */
		segmentPresenceLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_LabelAnnotation]] = None,
	  /** Topical label annotations on shot level. There is exactly one element for each unique label. */
		shotLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_LabelAnnotation]] = None,
	  /** Presence label annotations on shot level. There is exactly one element for each unique label. Compared to the existing topical `shot_label_annotations`, this field presents more fine-grained, shot-level labels detected in video content and is made available only when the client sets `LabelDetectionConfig.model` to "builtin/latest" in the request. */
		shotPresenceLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_LabelAnnotation]] = None,
	  /** Label annotations on frame level. There is exactly one element for each unique label. */
		frameLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_LabelAnnotation]] = None,
	  /** Deprecated. Please use `face_detection_annotations` instead. */
		faceAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_FaceAnnotation]] = None,
	  /** Face detection annotations. */
		faceDetectionAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_FaceDetectionAnnotation]] = None,
	  /** Shot annotations. Each shot is represented as a video segment. */
		shotAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_VideoSegment]] = None,
	  /** Explicit content annotation. */
		explicitAnnotation: Option[Schema.GoogleCloudVideointelligenceV1p1beta1_ExplicitContentAnnotation] = None,
	  /** Speech transcription. */
		speechTranscriptions: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_SpeechTranscription]] = None,
	  /** OCR text detection and tracking. Annotations for list of detected text snippets. Each will have list of frame information associated with it. */
		textAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_TextAnnotation]] = None,
	  /** Annotations for list of objects detected and tracked in video. */
		objectAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_ObjectTrackingAnnotation]] = None,
	  /** Annotations for list of logos detected, tracked and recognized in video. */
		logoRecognitionAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_LogoRecognitionAnnotation]] = None,
	  /** Person detection annotations. */
		personDetectionAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_PersonDetectionAnnotation]] = None,
	  /** If set, indicates an error. Note that for a single `AnnotateVideoRequest` some videos may succeed and some may fail. */
		error: Option[Schema.GoogleRpc_Status] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_VideoSegment(
	  /** Time-offset, relative to the beginning of the video, corresponding to the start of the segment (inclusive). */
		startTimeOffset: Option[String] = None,
	  /** Time-offset, relative to the beginning of the video, corresponding to the end of the segment (inclusive). */
		endTimeOffset: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_LabelAnnotation(
	  /** Detected entity. */
		entity: Option[Schema.GoogleCloudVideointelligenceV1p1beta1_Entity] = None,
	  /** Common categories for the detected entity. For example, when the label is `Terrier`, the category is likely `dog`. And in some cases there might be more than one categories e.g., `Terrier` could also be a `pet`. */
		categoryEntities: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_Entity]] = None,
	  /** All video segments where a label was detected. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_LabelSegment]] = None,
	  /** All video frames where a label was detected. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_LabelFrame]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_Entity(
	  /** Opaque entity ID. Some IDs may be available in [Google Knowledge Graph Search API](https://developers.google.com/knowledge-graph/). */
		entityId: Option[String] = None,
	  /** Textual description, e.g., `Fixed-gear bicycle`. */
		description: Option[String] = None,
	  /** Language code for `description` in BCP-47 format. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_LabelSegment(
	  /** Video segment where a label was detected. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p1beta1_VideoSegment] = None,
	  /** Confidence that the label is accurate. Range: [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_LabelFrame(
	  /** Time-offset, relative to the beginning of the video, corresponding to the video frame for this location. */
		timeOffset: Option[String] = None,
	  /** Confidence that the label is accurate. Range: [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_FaceAnnotation(
	  /** Thumbnail of a representative face view (in JPEG format). */
		thumbnail: Option[String] = None,
	  /** All video segments where a face was detected. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_FaceSegment]] = None,
	  /** All video frames where a face was detected. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_FaceFrame]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_FaceSegment(
	  /** Video segment where a face was detected. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p1beta1_VideoSegment] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_FaceFrame(
	  /** Normalized Bounding boxes in a frame. There can be more than one boxes if the same face is detected in multiple locations within the current frame. */
		normalizedBoundingBoxes: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_NormalizedBoundingBox]] = None,
	  /** Time-offset, relative to the beginning of the video, corresponding to the video frame for this location. */
		timeOffset: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_NormalizedBoundingBox(
	  /** Left X coordinate. */
		left: Option[BigDecimal] = None,
	  /** Top Y coordinate. */
		top: Option[BigDecimal] = None,
	  /** Right X coordinate. */
		right: Option[BigDecimal] = None,
	  /** Bottom Y coordinate. */
		bottom: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_FaceDetectionAnnotation(
	  /** The face tracks with attributes. */
		tracks: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_Track]] = None,
	  /** The thumbnail of a person's face. */
		thumbnail: Option[String] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_Track(
	  /** Video segment of a track. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p1beta1_VideoSegment] = None,
	  /** The object with timestamp and attributes per frame in the track. */
		timestampedObjects: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_TimestampedObject]] = None,
	  /** Optional. Attributes in the track level. */
		attributes: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_DetectedAttribute]] = None,
	  /** Optional. The confidence score of the tracked object. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_TimestampedObject(
	  /** Normalized Bounding box in a frame, where the object is located. */
		normalizedBoundingBox: Option[Schema.GoogleCloudVideointelligenceV1p1beta1_NormalizedBoundingBox] = None,
	  /** Time-offset, relative to the beginning of the video, corresponding to the video frame for this object. */
		timeOffset: Option[String] = None,
	  /** Optional. The attributes of the object in the bounding box. */
		attributes: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_DetectedAttribute]] = None,
	  /** Optional. The detected landmarks. */
		landmarks: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_DetectedLandmark]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_DetectedAttribute(
	  /** The name of the attribute, for example, glasses, dark_glasses, mouth_open. A full list of supported type names will be provided in the document. */
		name: Option[String] = None,
	  /** Detected attribute confidence. Range [0, 1]. */
		confidence: Option[BigDecimal] = None,
	  /** Text value of the detection result. For example, the value for "HairColor" can be "black", "blonde", etc. */
		value: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_DetectedLandmark(
	  /** The name of this landmark, for example, left_hand, right_shoulder. */
		name: Option[String] = None,
	  /** The 2D point of the detected landmark using the normalized image coordindate system. The normalized coordinates have the range from 0 to 1. */
		point: Option[Schema.GoogleCloudVideointelligenceV1p1beta1_NormalizedVertex] = None,
	  /** The confidence score of the detected landmark. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_NormalizedVertex(
	  /** X coordinate. */
		x: Option[BigDecimal] = None,
	  /** Y coordinate. */
		y: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_ExplicitContentAnnotation(
	  /** All video frames where explicit content was detected. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_ExplicitContentFrame]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	object GoogleCloudVideointelligenceV1p1beta1_ExplicitContentFrame {
		enum PornographyLikelihoodEnum extends Enum[PornographyLikelihoodEnum] { case LIKELIHOOD_UNSPECIFIED, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
	}
	case class GoogleCloudVideointelligenceV1p1beta1_ExplicitContentFrame(
	  /** Time-offset, relative to the beginning of the video, corresponding to the video frame for this location. */
		timeOffset: Option[String] = None,
	  /** Likelihood of the pornography content.. */
		pornographyLikelihood: Option[Schema.GoogleCloudVideointelligenceV1p1beta1_ExplicitContentFrame.PornographyLikelihoodEnum] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_SpeechTranscription(
	  /** May contain one or more recognition hypotheses (up to the maximum specified in `max_alternatives`). These alternatives are ordered in terms of accuracy, with the top (first) alternative being the most probable, as ranked by the recognizer. */
		alternatives: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_SpeechRecognitionAlternative]] = None,
	  /** Output only. The [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt) language tag of the language in this result. This language code was detected to have the most likelihood of being spoken in the audio. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_SpeechRecognitionAlternative(
	  /** Transcript text representing the words that the user spoke. */
		transcript: Option[String] = None,
	  /** Output only. The confidence estimate between 0.0 and 1.0. A higher number indicates an estimated greater likelihood that the recognized words are correct. This field is set only for the top alternative. This field is not guaranteed to be accurate and users should not rely on it to be always provided. The default of 0.0 is a sentinel value indicating `confidence` was not set. */
		confidence: Option[BigDecimal] = None,
	  /** Output only. A list of word-specific information for each recognized word. Note: When `enable_speaker_diarization` is set to true, you will see all the words from the beginning of the audio. */
		words: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_WordInfo]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_WordInfo(
	  /** Time offset relative to the beginning of the audio, and corresponding to the start of the spoken word. This field is only set if `enable_word_time_offsets=true` and only in the top hypothesis. This is an experimental feature and the accuracy of the time offset can vary. */
		startTime: Option[String] = None,
	  /** Time offset relative to the beginning of the audio, and corresponding to the end of the spoken word. This field is only set if `enable_word_time_offsets=true` and only in the top hypothesis. This is an experimental feature and the accuracy of the time offset can vary. */
		endTime: Option[String] = None,
	  /** The word corresponding to this set of information. */
		word: Option[String] = None,
	  /** Output only. The confidence estimate between 0.0 and 1.0. A higher number indicates an estimated greater likelihood that the recognized words are correct. This field is set only for the top alternative. This field is not guaranteed to be accurate and users should not rely on it to be always provided. The default of 0.0 is a sentinel value indicating `confidence` was not set. */
		confidence: Option[BigDecimal] = None,
	  /** Output only. A distinct integer value is assigned for every speaker within the audio. This field specifies which one of those speakers was detected to have spoken this word. Value ranges from 1 up to diarization_speaker_count, and is only set if speaker diarization is enabled. */
		speakerTag: Option[Int] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_TextAnnotation(
	  /** The detected text. */
		text: Option[String] = None,
	  /** All video segments where OCR detected text appears. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_TextSegment]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_TextSegment(
	  /** Video segment where a text snippet was detected. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p1beta1_VideoSegment] = None,
	  /** Confidence for the track of detected text. It is calculated as the highest over all frames where OCR detected text appears. */
		confidence: Option[BigDecimal] = None,
	  /** Information related to the frames where OCR detected text appears. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_TextFrame]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_TextFrame(
	  /** Bounding polygon of the detected text for this frame. */
		rotatedBoundingBox: Option[Schema.GoogleCloudVideointelligenceV1p1beta1_NormalizedBoundingPoly] = None,
	  /** Timestamp of this frame. */
		timeOffset: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_NormalizedBoundingPoly(
	  /** Normalized vertices of the bounding polygon. */
		vertices: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_NormalizedVertex]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_ObjectTrackingAnnotation(
	  /** Non-streaming batch mode ONLY. Each object track corresponds to one video segment where it appears. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p1beta1_VideoSegment] = None,
	  /** Streaming mode ONLY. In streaming mode, we do not know the end time of a tracked object before it is completed. Hence, there is no VideoSegment info returned. Instead, we provide a unique identifiable integer track_id so that the customers can correlate the results of the ongoing ObjectTrackAnnotation of the same track_id over time. */
		trackId: Option[String] = None,
	  /** Entity to specify the object category that this track is labeled as. */
		entity: Option[Schema.GoogleCloudVideointelligenceV1p1beta1_Entity] = None,
	  /** Object category's labeling confidence of this track. */
		confidence: Option[BigDecimal] = None,
	  /** Information corresponding to all frames where this object track appears. Non-streaming batch mode: it may be one or multiple ObjectTrackingFrame messages in frames. Streaming mode: it can only be one ObjectTrackingFrame message in frames. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_ObjectTrackingFrame]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_ObjectTrackingFrame(
	  /** The normalized bounding box location of this object track for the frame. */
		normalizedBoundingBox: Option[Schema.GoogleCloudVideointelligenceV1p1beta1_NormalizedBoundingBox] = None,
	  /** The timestamp of the frame in microseconds. */
		timeOffset: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_LogoRecognitionAnnotation(
	  /** Entity category information to specify the logo class that all the logo tracks within this LogoRecognitionAnnotation are recognized as. */
		entity: Option[Schema.GoogleCloudVideointelligenceV1p1beta1_Entity] = None,
	  /** All logo tracks where the recognized logo appears. Each track corresponds to one logo instance appearing in consecutive frames. */
		tracks: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_Track]] = None,
	  /** All video segments where the recognized logo appears. There might be multiple instances of the same logo class appearing in one VideoSegment. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_VideoSegment]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_PersonDetectionAnnotation(
	  /** The detected tracks of a person. */
		tracks: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_Track]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p1beta1_AnnotateVideoProgress(
	  /** Progress metadata for all videos specified in `AnnotateVideoRequest`. */
		annotationProgress: Option[List[Schema.GoogleCloudVideointelligenceV1p1beta1_VideoAnnotationProgress]] = None
	)
	
	object GoogleCloudVideointelligenceV1p1beta1_VideoAnnotationProgress {
		enum FeatureEnum extends Enum[FeatureEnum] { case FEATURE_UNSPECIFIED, LABEL_DETECTION, SHOT_CHANGE_DETECTION, EXPLICIT_CONTENT_DETECTION, FACE_DETECTION, SPEECH_TRANSCRIPTION, TEXT_DETECTION, OBJECT_TRACKING, LOGO_RECOGNITION, PERSON_DETECTION }
	}
	case class GoogleCloudVideointelligenceV1p1beta1_VideoAnnotationProgress(
	  /** Video file location in [Cloud Storage](https://cloud.google.com/storage/). */
		inputUri: Option[String] = None,
	  /** Approximate percentage processed thus far. Guaranteed to be 100 when fully processed. */
		progressPercent: Option[Int] = None,
	  /** Time when the request was received. */
		startTime: Option[String] = None,
	  /** Time of the most recent update. */
		updateTime: Option[String] = None,
	  /** Specifies which feature is being tracked if the request contains more than one feature. */
		feature: Option[Schema.GoogleCloudVideointelligenceV1p1beta1_VideoAnnotationProgress.FeatureEnum] = None,
	  /** Specifies which segment is being tracked if the request contains more than one segment. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p1beta1_VideoSegment] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_AnnotateVideoResponse(
	  /** Annotation results for all videos specified in `AnnotateVideoRequest`. */
		annotationResults: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_VideoAnnotationResults]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_VideoAnnotationResults(
	  /** Video file location in [Cloud Storage](https://cloud.google.com/storage/). */
		inputUri: Option[String] = None,
	  /** Video segment on which the annotation is run. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p2beta1_VideoSegment] = None,
	  /** Topical label annotations on video level or user-specified segment level. There is exactly one element for each unique label. */
		segmentLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_LabelAnnotation]] = None,
	  /** Presence label annotations on video level or user-specified segment level. There is exactly one element for each unique label. Compared to the existing topical `segment_label_annotations`, this field presents more fine-grained, segment-level labels detected in video content and is made available only when the client sets `LabelDetectionConfig.model` to "builtin/latest" in the request. */
		segmentPresenceLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_LabelAnnotation]] = None,
	  /** Topical label annotations on shot level. There is exactly one element for each unique label. */
		shotLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_LabelAnnotation]] = None,
	  /** Presence label annotations on shot level. There is exactly one element for each unique label. Compared to the existing topical `shot_label_annotations`, this field presents more fine-grained, shot-level labels detected in video content and is made available only when the client sets `LabelDetectionConfig.model` to "builtin/latest" in the request. */
		shotPresenceLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_LabelAnnotation]] = None,
	  /** Label annotations on frame level. There is exactly one element for each unique label. */
		frameLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_LabelAnnotation]] = None,
	  /** Deprecated. Please use `face_detection_annotations` instead. */
		faceAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_FaceAnnotation]] = None,
	  /** Face detection annotations. */
		faceDetectionAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_FaceDetectionAnnotation]] = None,
	  /** Shot annotations. Each shot is represented as a video segment. */
		shotAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_VideoSegment]] = None,
	  /** Explicit content annotation. */
		explicitAnnotation: Option[Schema.GoogleCloudVideointelligenceV1p2beta1_ExplicitContentAnnotation] = None,
	  /** Speech transcription. */
		speechTranscriptions: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_SpeechTranscription]] = None,
	  /** OCR text detection and tracking. Annotations for list of detected text snippets. Each will have list of frame information associated with it. */
		textAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_TextAnnotation]] = None,
	  /** Annotations for list of objects detected and tracked in video. */
		objectAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_ObjectTrackingAnnotation]] = None,
	  /** Annotations for list of logos detected, tracked and recognized in video. */
		logoRecognitionAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_LogoRecognitionAnnotation]] = None,
	  /** Person detection annotations. */
		personDetectionAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_PersonDetectionAnnotation]] = None,
	  /** If set, indicates an error. Note that for a single `AnnotateVideoRequest` some videos may succeed and some may fail. */
		error: Option[Schema.GoogleRpc_Status] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_VideoSegment(
	  /** Time-offset, relative to the beginning of the video, corresponding to the start of the segment (inclusive). */
		startTimeOffset: Option[String] = None,
	  /** Time-offset, relative to the beginning of the video, corresponding to the end of the segment (inclusive). */
		endTimeOffset: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_LabelAnnotation(
	  /** Detected entity. */
		entity: Option[Schema.GoogleCloudVideointelligenceV1p2beta1_Entity] = None,
	  /** Common categories for the detected entity. For example, when the label is `Terrier`, the category is likely `dog`. And in some cases there might be more than one categories e.g., `Terrier` could also be a `pet`. */
		categoryEntities: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_Entity]] = None,
	  /** All video segments where a label was detected. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_LabelSegment]] = None,
	  /** All video frames where a label was detected. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_LabelFrame]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_Entity(
	  /** Opaque entity ID. Some IDs may be available in [Google Knowledge Graph Search API](https://developers.google.com/knowledge-graph/). */
		entityId: Option[String] = None,
	  /** Textual description, e.g., `Fixed-gear bicycle`. */
		description: Option[String] = None,
	  /** Language code for `description` in BCP-47 format. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_LabelSegment(
	  /** Video segment where a label was detected. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p2beta1_VideoSegment] = None,
	  /** Confidence that the label is accurate. Range: [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_LabelFrame(
	  /** Time-offset, relative to the beginning of the video, corresponding to the video frame for this location. */
		timeOffset: Option[String] = None,
	  /** Confidence that the label is accurate. Range: [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_FaceAnnotation(
	  /** Thumbnail of a representative face view (in JPEG format). */
		thumbnail: Option[String] = None,
	  /** All video segments where a face was detected. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_FaceSegment]] = None,
	  /** All video frames where a face was detected. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_FaceFrame]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_FaceSegment(
	  /** Video segment where a face was detected. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p2beta1_VideoSegment] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_FaceFrame(
	  /** Normalized Bounding boxes in a frame. There can be more than one boxes if the same face is detected in multiple locations within the current frame. */
		normalizedBoundingBoxes: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_NormalizedBoundingBox]] = None,
	  /** Time-offset, relative to the beginning of the video, corresponding to the video frame for this location. */
		timeOffset: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_NormalizedBoundingBox(
	  /** Left X coordinate. */
		left: Option[BigDecimal] = None,
	  /** Top Y coordinate. */
		top: Option[BigDecimal] = None,
	  /** Right X coordinate. */
		right: Option[BigDecimal] = None,
	  /** Bottom Y coordinate. */
		bottom: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_FaceDetectionAnnotation(
	  /** The face tracks with attributes. */
		tracks: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_Track]] = None,
	  /** The thumbnail of a person's face. */
		thumbnail: Option[String] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_Track(
	  /** Video segment of a track. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p2beta1_VideoSegment] = None,
	  /** The object with timestamp and attributes per frame in the track. */
		timestampedObjects: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_TimestampedObject]] = None,
	  /** Optional. Attributes in the track level. */
		attributes: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_DetectedAttribute]] = None,
	  /** Optional. The confidence score of the tracked object. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_TimestampedObject(
	  /** Normalized Bounding box in a frame, where the object is located. */
		normalizedBoundingBox: Option[Schema.GoogleCloudVideointelligenceV1p2beta1_NormalizedBoundingBox] = None,
	  /** Time-offset, relative to the beginning of the video, corresponding to the video frame for this object. */
		timeOffset: Option[String] = None,
	  /** Optional. The attributes of the object in the bounding box. */
		attributes: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_DetectedAttribute]] = None,
	  /** Optional. The detected landmarks. */
		landmarks: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_DetectedLandmark]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_DetectedAttribute(
	  /** The name of the attribute, for example, glasses, dark_glasses, mouth_open. A full list of supported type names will be provided in the document. */
		name: Option[String] = None,
	  /** Detected attribute confidence. Range [0, 1]. */
		confidence: Option[BigDecimal] = None,
	  /** Text value of the detection result. For example, the value for "HairColor" can be "black", "blonde", etc. */
		value: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_DetectedLandmark(
	  /** The name of this landmark, for example, left_hand, right_shoulder. */
		name: Option[String] = None,
	  /** The 2D point of the detected landmark using the normalized image coordindate system. The normalized coordinates have the range from 0 to 1. */
		point: Option[Schema.GoogleCloudVideointelligenceV1p2beta1_NormalizedVertex] = None,
	  /** The confidence score of the detected landmark. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_NormalizedVertex(
	  /** X coordinate. */
		x: Option[BigDecimal] = None,
	  /** Y coordinate. */
		y: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_ExplicitContentAnnotation(
	  /** All video frames where explicit content was detected. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_ExplicitContentFrame]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	object GoogleCloudVideointelligenceV1p2beta1_ExplicitContentFrame {
		enum PornographyLikelihoodEnum extends Enum[PornographyLikelihoodEnum] { case LIKELIHOOD_UNSPECIFIED, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
	}
	case class GoogleCloudVideointelligenceV1p2beta1_ExplicitContentFrame(
	  /** Time-offset, relative to the beginning of the video, corresponding to the video frame for this location. */
		timeOffset: Option[String] = None,
	  /** Likelihood of the pornography content.. */
		pornographyLikelihood: Option[Schema.GoogleCloudVideointelligenceV1p2beta1_ExplicitContentFrame.PornographyLikelihoodEnum] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_SpeechTranscription(
	  /** May contain one or more recognition hypotheses (up to the maximum specified in `max_alternatives`). These alternatives are ordered in terms of accuracy, with the top (first) alternative being the most probable, as ranked by the recognizer. */
		alternatives: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_SpeechRecognitionAlternative]] = None,
	  /** Output only. The [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt) language tag of the language in this result. This language code was detected to have the most likelihood of being spoken in the audio. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_SpeechRecognitionAlternative(
	  /** Transcript text representing the words that the user spoke. */
		transcript: Option[String] = None,
	  /** Output only. The confidence estimate between 0.0 and 1.0. A higher number indicates an estimated greater likelihood that the recognized words are correct. This field is set only for the top alternative. This field is not guaranteed to be accurate and users should not rely on it to be always provided. The default of 0.0 is a sentinel value indicating `confidence` was not set. */
		confidence: Option[BigDecimal] = None,
	  /** Output only. A list of word-specific information for each recognized word. Note: When `enable_speaker_diarization` is set to true, you will see all the words from the beginning of the audio. */
		words: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_WordInfo]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_WordInfo(
	  /** Time offset relative to the beginning of the audio, and corresponding to the start of the spoken word. This field is only set if `enable_word_time_offsets=true` and only in the top hypothesis. This is an experimental feature and the accuracy of the time offset can vary. */
		startTime: Option[String] = None,
	  /** Time offset relative to the beginning of the audio, and corresponding to the end of the spoken word. This field is only set if `enable_word_time_offsets=true` and only in the top hypothesis. This is an experimental feature and the accuracy of the time offset can vary. */
		endTime: Option[String] = None,
	  /** The word corresponding to this set of information. */
		word: Option[String] = None,
	  /** Output only. The confidence estimate between 0.0 and 1.0. A higher number indicates an estimated greater likelihood that the recognized words are correct. This field is set only for the top alternative. This field is not guaranteed to be accurate and users should not rely on it to be always provided. The default of 0.0 is a sentinel value indicating `confidence` was not set. */
		confidence: Option[BigDecimal] = None,
	  /** Output only. A distinct integer value is assigned for every speaker within the audio. This field specifies which one of those speakers was detected to have spoken this word. Value ranges from 1 up to diarization_speaker_count, and is only set if speaker diarization is enabled. */
		speakerTag: Option[Int] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_TextAnnotation(
	  /** The detected text. */
		text: Option[String] = None,
	  /** All video segments where OCR detected text appears. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_TextSegment]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_TextSegment(
	  /** Video segment where a text snippet was detected. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p2beta1_VideoSegment] = None,
	  /** Confidence for the track of detected text. It is calculated as the highest over all frames where OCR detected text appears. */
		confidence: Option[BigDecimal] = None,
	  /** Information related to the frames where OCR detected text appears. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_TextFrame]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_TextFrame(
	  /** Bounding polygon of the detected text for this frame. */
		rotatedBoundingBox: Option[Schema.GoogleCloudVideointelligenceV1p2beta1_NormalizedBoundingPoly] = None,
	  /** Timestamp of this frame. */
		timeOffset: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_NormalizedBoundingPoly(
	  /** Normalized vertices of the bounding polygon. */
		vertices: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_NormalizedVertex]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_ObjectTrackingAnnotation(
	  /** Non-streaming batch mode ONLY. Each object track corresponds to one video segment where it appears. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p2beta1_VideoSegment] = None,
	  /** Streaming mode ONLY. In streaming mode, we do not know the end time of a tracked object before it is completed. Hence, there is no VideoSegment info returned. Instead, we provide a unique identifiable integer track_id so that the customers can correlate the results of the ongoing ObjectTrackAnnotation of the same track_id over time. */
		trackId: Option[String] = None,
	  /** Entity to specify the object category that this track is labeled as. */
		entity: Option[Schema.GoogleCloudVideointelligenceV1p2beta1_Entity] = None,
	  /** Object category's labeling confidence of this track. */
		confidence: Option[BigDecimal] = None,
	  /** Information corresponding to all frames where this object track appears. Non-streaming batch mode: it may be one or multiple ObjectTrackingFrame messages in frames. Streaming mode: it can only be one ObjectTrackingFrame message in frames. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_ObjectTrackingFrame]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_ObjectTrackingFrame(
	  /** The normalized bounding box location of this object track for the frame. */
		normalizedBoundingBox: Option[Schema.GoogleCloudVideointelligenceV1p2beta1_NormalizedBoundingBox] = None,
	  /** The timestamp of the frame in microseconds. */
		timeOffset: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_LogoRecognitionAnnotation(
	  /** Entity category information to specify the logo class that all the logo tracks within this LogoRecognitionAnnotation are recognized as. */
		entity: Option[Schema.GoogleCloudVideointelligenceV1p2beta1_Entity] = None,
	  /** All logo tracks where the recognized logo appears. Each track corresponds to one logo instance appearing in consecutive frames. */
		tracks: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_Track]] = None,
	  /** All video segments where the recognized logo appears. There might be multiple instances of the same logo class appearing in one VideoSegment. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_VideoSegment]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_PersonDetectionAnnotation(
	  /** The detected tracks of a person. */
		tracks: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_Track]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p2beta1_AnnotateVideoProgress(
	  /** Progress metadata for all videos specified in `AnnotateVideoRequest`. */
		annotationProgress: Option[List[Schema.GoogleCloudVideointelligenceV1p2beta1_VideoAnnotationProgress]] = None
	)
	
	object GoogleCloudVideointelligenceV1p2beta1_VideoAnnotationProgress {
		enum FeatureEnum extends Enum[FeatureEnum] { case FEATURE_UNSPECIFIED, LABEL_DETECTION, SHOT_CHANGE_DETECTION, EXPLICIT_CONTENT_DETECTION, FACE_DETECTION, SPEECH_TRANSCRIPTION, TEXT_DETECTION, OBJECT_TRACKING, LOGO_RECOGNITION, PERSON_DETECTION }
	}
	case class GoogleCloudVideointelligenceV1p2beta1_VideoAnnotationProgress(
	  /** Video file location in [Cloud Storage](https://cloud.google.com/storage/). */
		inputUri: Option[String] = None,
	  /** Approximate percentage processed thus far. Guaranteed to be 100 when fully processed. */
		progressPercent: Option[Int] = None,
	  /** Time when the request was received. */
		startTime: Option[String] = None,
	  /** Time of the most recent update. */
		updateTime: Option[String] = None,
	  /** Specifies which feature is being tracked if the request contains more than one feature. */
		feature: Option[Schema.GoogleCloudVideointelligenceV1p2beta1_VideoAnnotationProgress.FeatureEnum] = None,
	  /** Specifies which segment is being tracked if the request contains more than one segment. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p2beta1_VideoSegment] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_AnnotateVideoResponse(
	  /** Annotation results for all videos specified in `AnnotateVideoRequest`. */
		annotationResults: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_VideoAnnotationResults]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_VideoAnnotationResults(
	  /** Video file location in [Cloud Storage](https://cloud.google.com/storage/). */
		inputUri: Option[String] = None,
	  /** Video segment on which the annotation is run. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_VideoSegment] = None,
	  /** Topical label annotations on video level or user-specified segment level. There is exactly one element for each unique label. */
		segmentLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_LabelAnnotation]] = None,
	  /** Presence label annotations on video level or user-specified segment level. There is exactly one element for each unique label. Compared to the existing topical `segment_label_annotations`, this field presents more fine-grained, segment-level labels detected in video content and is made available only when the client sets `LabelDetectionConfig.model` to "builtin/latest" in the request. */
		segmentPresenceLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_LabelAnnotation]] = None,
	  /** Topical label annotations on shot level. There is exactly one element for each unique label. */
		shotLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_LabelAnnotation]] = None,
	  /** Presence label annotations on shot level. There is exactly one element for each unique label. Compared to the existing topical `shot_label_annotations`, this field presents more fine-grained, shot-level labels detected in video content and is made available only when the client sets `LabelDetectionConfig.model` to "builtin/latest" in the request. */
		shotPresenceLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_LabelAnnotation]] = None,
	  /** Label annotations on frame level. There is exactly one element for each unique label. */
		frameLabelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_LabelAnnotation]] = None,
	  /** Deprecated. Please use `face_detection_annotations` instead. */
		faceAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_FaceAnnotation]] = None,
	  /** Face detection annotations. */
		faceDetectionAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_FaceDetectionAnnotation]] = None,
	  /** Shot annotations. Each shot is represented as a video segment. */
		shotAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_VideoSegment]] = None,
	  /** Explicit content annotation. */
		explicitAnnotation: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_ExplicitContentAnnotation] = None,
	  /** Speech transcription. */
		speechTranscriptions: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_SpeechTranscription]] = None,
	  /** OCR text detection and tracking. Annotations for list of detected text snippets. Each will have list of frame information associated with it. */
		textAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_TextAnnotation]] = None,
	  /** Annotations for list of objects detected and tracked in video. */
		objectAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_ObjectTrackingAnnotation]] = None,
	  /** Annotations for list of logos detected, tracked and recognized in video. */
		logoRecognitionAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_LogoRecognitionAnnotation]] = None,
	  /** Person detection annotations. */
		personDetectionAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_PersonDetectionAnnotation]] = None,
	  /** Celebrity recognition annotations. */
		celebrityRecognitionAnnotations: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_CelebrityRecognitionAnnotation] = None,
	  /** If set, indicates an error. Note that for a single `AnnotateVideoRequest` some videos may succeed and some may fail. */
		error: Option[Schema.GoogleRpc_Status] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_VideoSegment(
	  /** Time-offset, relative to the beginning of the video, corresponding to the start of the segment (inclusive). */
		startTimeOffset: Option[String] = None,
	  /** Time-offset, relative to the beginning of the video, corresponding to the end of the segment (inclusive). */
		endTimeOffset: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_LabelAnnotation(
	  /** Detected entity. */
		entity: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_Entity] = None,
	  /** Common categories for the detected entity. For example, when the label is `Terrier`, the category is likely `dog`. And in some cases there might be more than one categories e.g., `Terrier` could also be a `pet`. */
		categoryEntities: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_Entity]] = None,
	  /** All video segments where a label was detected. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_LabelSegment]] = None,
	  /** All video frames where a label was detected. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_LabelFrame]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_Entity(
	  /** Opaque entity ID. Some IDs may be available in [Google Knowledge Graph Search API](https://developers.google.com/knowledge-graph/). */
		entityId: Option[String] = None,
	  /** Textual description, e.g., `Fixed-gear bicycle`. */
		description: Option[String] = None,
	  /** Language code for `description` in BCP-47 format. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_LabelSegment(
	  /** Video segment where a label was detected. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_VideoSegment] = None,
	  /** Confidence that the label is accurate. Range: [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_LabelFrame(
	  /** Time-offset, relative to the beginning of the video, corresponding to the video frame for this location. */
		timeOffset: Option[String] = None,
	  /** Confidence that the label is accurate. Range: [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_FaceAnnotation(
	  /** Thumbnail of a representative face view (in JPEG format). */
		thumbnail: Option[String] = None,
	  /** All video segments where a face was detected. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_FaceSegment]] = None,
	  /** All video frames where a face was detected. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_FaceFrame]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_FaceSegment(
	  /** Video segment where a face was detected. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_VideoSegment] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_FaceFrame(
	  /** Normalized Bounding boxes in a frame. There can be more than one boxes if the same face is detected in multiple locations within the current frame. */
		normalizedBoundingBoxes: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_NormalizedBoundingBox]] = None,
	  /** Time-offset, relative to the beginning of the video, corresponding to the video frame for this location. */
		timeOffset: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_NormalizedBoundingBox(
	  /** Left X coordinate. */
		left: Option[BigDecimal] = None,
	  /** Top Y coordinate. */
		top: Option[BigDecimal] = None,
	  /** Right X coordinate. */
		right: Option[BigDecimal] = None,
	  /** Bottom Y coordinate. */
		bottom: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_FaceDetectionAnnotation(
	  /** The face tracks with attributes. */
		tracks: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_Track]] = None,
	  /** The thumbnail of a person's face. */
		thumbnail: Option[String] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_Track(
	  /** Video segment of a track. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_VideoSegment] = None,
	  /** The object with timestamp and attributes per frame in the track. */
		timestampedObjects: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_TimestampedObject]] = None,
	  /** Optional. Attributes in the track level. */
		attributes: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_DetectedAttribute]] = None,
	  /** Optional. The confidence score of the tracked object. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_TimestampedObject(
	  /** Normalized Bounding box in a frame, where the object is located. */
		normalizedBoundingBox: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_NormalizedBoundingBox] = None,
	  /** Time-offset, relative to the beginning of the video, corresponding to the video frame for this object. */
		timeOffset: Option[String] = None,
	  /** Optional. The attributes of the object in the bounding box. */
		attributes: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_DetectedAttribute]] = None,
	  /** Optional. The detected landmarks. */
		landmarks: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_DetectedLandmark]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_DetectedAttribute(
	  /** The name of the attribute, for example, glasses, dark_glasses, mouth_open. A full list of supported type names will be provided in the document. */
		name: Option[String] = None,
	  /** Detected attribute confidence. Range [0, 1]. */
		confidence: Option[BigDecimal] = None,
	  /** Text value of the detection result. For example, the value for "HairColor" can be "black", "blonde", etc. */
		value: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_DetectedLandmark(
	  /** The name of this landmark, for example, left_hand, right_shoulder. */
		name: Option[String] = None,
	  /** The 2D point of the detected landmark using the normalized image coordindate system. The normalized coordinates have the range from 0 to 1. */
		point: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_NormalizedVertex] = None,
	  /** The confidence score of the detected landmark. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_NormalizedVertex(
	  /** X coordinate. */
		x: Option[BigDecimal] = None,
	  /** Y coordinate. */
		y: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_ExplicitContentAnnotation(
	  /** All video frames where explicit content was detected. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_ExplicitContentFrame]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	object GoogleCloudVideointelligenceV1p3beta1_ExplicitContentFrame {
		enum PornographyLikelihoodEnum extends Enum[PornographyLikelihoodEnum] { case LIKELIHOOD_UNSPECIFIED, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
	}
	case class GoogleCloudVideointelligenceV1p3beta1_ExplicitContentFrame(
	  /** Time-offset, relative to the beginning of the video, corresponding to the video frame for this location. */
		timeOffset: Option[String] = None,
	  /** Likelihood of the pornography content.. */
		pornographyLikelihood: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_ExplicitContentFrame.PornographyLikelihoodEnum] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_SpeechTranscription(
	  /** May contain one or more recognition hypotheses (up to the maximum specified in `max_alternatives`). These alternatives are ordered in terms of accuracy, with the top (first) alternative being the most probable, as ranked by the recognizer. */
		alternatives: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_SpeechRecognitionAlternative]] = None,
	  /** Output only. The [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt) language tag of the language in this result. This language code was detected to have the most likelihood of being spoken in the audio. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_SpeechRecognitionAlternative(
	  /** Transcript text representing the words that the user spoke. */
		transcript: Option[String] = None,
	  /** Output only. The confidence estimate between 0.0 and 1.0. A higher number indicates an estimated greater likelihood that the recognized words are correct. This field is set only for the top alternative. This field is not guaranteed to be accurate and users should not rely on it to be always provided. The default of 0.0 is a sentinel value indicating `confidence` was not set. */
		confidence: Option[BigDecimal] = None,
	  /** Output only. A list of word-specific information for each recognized word. Note: When `enable_speaker_diarization` is set to true, you will see all the words from the beginning of the audio. */
		words: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_WordInfo]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_WordInfo(
	  /** Time offset relative to the beginning of the audio, and corresponding to the start of the spoken word. This field is only set if `enable_word_time_offsets=true` and only in the top hypothesis. This is an experimental feature and the accuracy of the time offset can vary. */
		startTime: Option[String] = None,
	  /** Time offset relative to the beginning of the audio, and corresponding to the end of the spoken word. This field is only set if `enable_word_time_offsets=true` and only in the top hypothesis. This is an experimental feature and the accuracy of the time offset can vary. */
		endTime: Option[String] = None,
	  /** The word corresponding to this set of information. */
		word: Option[String] = None,
	  /** Output only. The confidence estimate between 0.0 and 1.0. A higher number indicates an estimated greater likelihood that the recognized words are correct. This field is set only for the top alternative. This field is not guaranteed to be accurate and users should not rely on it to be always provided. The default of 0.0 is a sentinel value indicating `confidence` was not set. */
		confidence: Option[BigDecimal] = None,
	  /** Output only. A distinct integer value is assigned for every speaker within the audio. This field specifies which one of those speakers was detected to have spoken this word. Value ranges from 1 up to diarization_speaker_count, and is only set if speaker diarization is enabled. */
		speakerTag: Option[Int] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_TextAnnotation(
	  /** The detected text. */
		text: Option[String] = None,
	  /** All video segments where OCR detected text appears. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_TextSegment]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_TextSegment(
	  /** Video segment where a text snippet was detected. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_VideoSegment] = None,
	  /** Confidence for the track of detected text. It is calculated as the highest over all frames where OCR detected text appears. */
		confidence: Option[BigDecimal] = None,
	  /** Information related to the frames where OCR detected text appears. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_TextFrame]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_TextFrame(
	  /** Bounding polygon of the detected text for this frame. */
		rotatedBoundingBox: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_NormalizedBoundingPoly] = None,
	  /** Timestamp of this frame. */
		timeOffset: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_NormalizedBoundingPoly(
	  /** Normalized vertices of the bounding polygon. */
		vertices: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_NormalizedVertex]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_ObjectTrackingAnnotation(
	  /** Non-streaming batch mode ONLY. Each object track corresponds to one video segment where it appears. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_VideoSegment] = None,
	  /** Streaming mode ONLY. In streaming mode, we do not know the end time of a tracked object before it is completed. Hence, there is no VideoSegment info returned. Instead, we provide a unique identifiable integer track_id so that the customers can correlate the results of the ongoing ObjectTrackAnnotation of the same track_id over time. */
		trackId: Option[String] = None,
	  /** Entity to specify the object category that this track is labeled as. */
		entity: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_Entity] = None,
	  /** Object category's labeling confidence of this track. */
		confidence: Option[BigDecimal] = None,
	  /** Information corresponding to all frames where this object track appears. Non-streaming batch mode: it may be one or multiple ObjectTrackingFrame messages in frames. Streaming mode: it can only be one ObjectTrackingFrame message in frames. */
		frames: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_ObjectTrackingFrame]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_ObjectTrackingFrame(
	  /** The normalized bounding box location of this object track for the frame. */
		normalizedBoundingBox: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_NormalizedBoundingBox] = None,
	  /** The timestamp of the frame in microseconds. */
		timeOffset: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_LogoRecognitionAnnotation(
	  /** Entity category information to specify the logo class that all the logo tracks within this LogoRecognitionAnnotation are recognized as. */
		entity: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_Entity] = None,
	  /** All logo tracks where the recognized logo appears. Each track corresponds to one logo instance appearing in consecutive frames. */
		tracks: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_Track]] = None,
	  /** All video segments where the recognized logo appears. There might be multiple instances of the same logo class appearing in one VideoSegment. */
		segments: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_VideoSegment]] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_PersonDetectionAnnotation(
	  /** The detected tracks of a person. */
		tracks: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_Track]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_CelebrityRecognitionAnnotation(
	  /** The tracks detected from the input video, including recognized celebrities and other detected faces in the video. */
		celebrityTracks: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_CelebrityTrack]] = None,
	  /** Feature version. */
		version: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_CelebrityTrack(
	  /** Top N match of the celebrities for the face in this track. */
		celebrities: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_RecognizedCelebrity]] = None,
	  /** A track of a person's face. */
		faceTrack: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_Track] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_RecognizedCelebrity(
	  /** The recognized celebrity. */
		celebrity: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_Celebrity] = None,
	  /** Recognition confidence. Range [0, 1]. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_Celebrity(
	  /** The resource name of the celebrity. Have the format `video-intelligence/kg-mid` indicates a celebrity from preloaded gallery. kg-mid is the id in Google knowledge graph, which is unique for the celebrity. */
		name: Option[String] = None,
	  /** The celebrity name. */
		displayName: Option[String] = None,
	  /** Textual description of additional information about the celebrity, if applicable. */
		description: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_AnnotateVideoProgress(
	  /** Progress metadata for all videos specified in `AnnotateVideoRequest`. */
		annotationProgress: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_VideoAnnotationProgress]] = None
	)
	
	object GoogleCloudVideointelligenceV1p3beta1_VideoAnnotationProgress {
		enum FeatureEnum extends Enum[FeatureEnum] { case FEATURE_UNSPECIFIED, LABEL_DETECTION, SHOT_CHANGE_DETECTION, EXPLICIT_CONTENT_DETECTION, FACE_DETECTION, SPEECH_TRANSCRIPTION, TEXT_DETECTION, OBJECT_TRACKING, LOGO_RECOGNITION, CELEBRITY_RECOGNITION, PERSON_DETECTION }
	}
	case class GoogleCloudVideointelligenceV1p3beta1_VideoAnnotationProgress(
	  /** Video file location in [Cloud Storage](https://cloud.google.com/storage/). */
		inputUri: Option[String] = None,
	  /** Approximate percentage processed thus far. Guaranteed to be 100 when fully processed. */
		progressPercent: Option[Int] = None,
	  /** Time when the request was received. */
		startTime: Option[String] = None,
	  /** Time of the most recent update. */
		updateTime: Option[String] = None,
	  /** Specifies which feature is being tracked if the request contains more than one feature. */
		feature: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_VideoAnnotationProgress.FeatureEnum] = None,
	  /** Specifies which segment is being tracked if the request contains more than one segment. */
		segment: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_VideoSegment] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_StreamingAnnotateVideoResponse(
	  /** If set, returns a google.rpc.Status message that specifies the error for the operation. */
		error: Option[Schema.GoogleRpc_Status] = None,
	  /** Streaming annotation results. */
		annotationResults: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_StreamingVideoAnnotationResults] = None,
	  /** Google Cloud Storage URI that stores annotation results of one streaming session in JSON format. It is the annotation_result_storage_directory from the request followed by '/cloud_project_number-session_id'. */
		annotationResultsUri: Option[String] = None
	)
	
	case class GoogleCloudVideointelligenceV1p3beta1_StreamingVideoAnnotationResults(
	  /** Timestamp of the processed frame in microseconds. */
		frameTimestamp: Option[String] = None,
	  /** Shot annotation results. Each shot is represented as a video segment. */
		shotAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_VideoSegment]] = None,
	  /** Label annotation results. */
		labelAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_LabelAnnotation]] = None,
	  /** Explicit content annotation results. */
		explicitAnnotation: Option[Schema.GoogleCloudVideointelligenceV1p3beta1_ExplicitContentAnnotation] = None,
	  /** Object tracking results. */
		objectAnnotations: Option[List[Schema.GoogleCloudVideointelligenceV1p3beta1_ObjectTrackingAnnotation]] = None
	)
}
