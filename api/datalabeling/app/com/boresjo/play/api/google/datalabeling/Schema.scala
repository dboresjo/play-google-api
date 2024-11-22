package com.boresjo.play.api.google.datalabeling

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleLongrunningListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.GoogleLongrunningOperation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleLongrunningOperation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleRpcStatus(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleCloudDatalabelingV1beta1CreateDatasetRequest(
	  /** Required. The dataset to be created. */
		dataset: Option[Schema.GoogleCloudDatalabelingV1beta1Dataset] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1Dataset(
	  /** Output only. Dataset resource name, format is: projects/{project_id}/datasets/{dataset_id} */
		name: Option[String] = None,
	  /** Required. The display name of the dataset. Maximum of 64 characters. */
		displayName: Option[String] = None,
	  /** Optional. User-provided description of the annotation specification set. The description can be up to 10000 characters long. */
		description: Option[String] = None,
	  /** Output only. Time the dataset is created. */
		createTime: Option[String] = None,
	  /** Output only. This is populated with the original input configs where ImportData is called. It is available only after the clients import data to this dataset. */
		inputConfigs: Option[List[Schema.GoogleCloudDatalabelingV1beta1InputConfig]] = None,
	  /** Output only. The names of any related resources that are blocking changes to the dataset. */
		blockingResources: Option[List[String]] = None,
	  /** Output only. The number of data items in the dataset. */
		dataItemCount: Option[String] = None,
	  /** Last time that the Dataset is migrated to AI Platform V2. If any of the AnnotatedDataset is migrated, the last_migration_time in Dataset is also updated. */
		lastMigrateTime: Option[String] = None
	)
	
	object GoogleCloudDatalabelingV1beta1InputConfig {
		enum DataTypeEnum extends Enum[DataTypeEnum] { case DATA_TYPE_UNSPECIFIED, IMAGE, VIDEO, TEXT, GENERAL_DATA }
		enum AnnotationTypeEnum extends Enum[AnnotationTypeEnum] { case ANNOTATION_TYPE_UNSPECIFIED, IMAGE_CLASSIFICATION_ANNOTATION, IMAGE_BOUNDING_BOX_ANNOTATION, IMAGE_ORIENTED_BOUNDING_BOX_ANNOTATION, IMAGE_BOUNDING_POLY_ANNOTATION, IMAGE_POLYLINE_ANNOTATION, IMAGE_SEGMENTATION_ANNOTATION, VIDEO_SHOTS_CLASSIFICATION_ANNOTATION, VIDEO_OBJECT_TRACKING_ANNOTATION, VIDEO_OBJECT_DETECTION_ANNOTATION, VIDEO_EVENT_ANNOTATION, TEXT_CLASSIFICATION_ANNOTATION, TEXT_ENTITY_EXTRACTION_ANNOTATION, GENERAL_CLASSIFICATION_ANNOTATION }
	}
	case class GoogleCloudDatalabelingV1beta1InputConfig(
	  /** Required for text import, as language code must be specified. */
		textMetadata: Option[Schema.GoogleCloudDatalabelingV1beta1TextMetadata] = None,
	  /** Source located in Cloud Storage. */
		gcsSource: Option[Schema.GoogleCloudDatalabelingV1beta1GcsSource] = None,
	  /** Source located in BigQuery. You must specify this field if you are using this InputConfig in an EvaluationJob. */
		bigquerySource: Option[Schema.GoogleCloudDatalabelingV1beta1BigQuerySource] = None,
	  /** Required. Data type must be specifed when user tries to import data. */
		dataType: Option[Schema.GoogleCloudDatalabelingV1beta1InputConfig.DataTypeEnum] = None,
	  /** Optional. The type of annotation to be performed on this data. You must specify this field if you are using this InputConfig in an EvaluationJob. */
		annotationType: Option[Schema.GoogleCloudDatalabelingV1beta1InputConfig.AnnotationTypeEnum] = None,
	  /** Optional. Metadata about annotations for the input. You must specify this field if you are using this InputConfig in an EvaluationJob for a model version that performs classification. */
		classificationMetadata: Option[Schema.GoogleCloudDatalabelingV1beta1ClassificationMetadata] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1TextMetadata(
	  /** The language of this text, as a [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt). Default value is en-US. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1GcsSource(
	  /** Required. The input URI of source file. This must be a Cloud Storage path (`gs://...`). */
		inputUri: Option[String] = None,
	  /** Required. The format of the source file. Only "text/csv" is supported. */
		mimeType: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1BigQuerySource(
	  /** Required. BigQuery URI to a table, up to 2,000 characters long. If you specify the URI of a table that does not exist, Data Labeling Service creates a table at the URI with the correct schema when you create your EvaluationJob. If you specify the URI of a table that already exists, it must have the [correct schema](/ml-engine/docs/continuous-evaluation/create-job#table-schema). Provide the table URI in the following format: "bq://{your_project_id}/ {your_dataset_name}/{your_table_name}" [Learn more](/ml-engine/docs/continuous-evaluation/create-job#table-schema). */
		inputUri: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ClassificationMetadata(
	  /** Whether the classification task is multi-label or not. */
		isMultiLabel: Option[Boolean] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ListDatasetsResponse(
	  /** The list of datasets to return. */
		datasets: Option[List[Schema.GoogleCloudDatalabelingV1beta1Dataset]] = None,
	  /** A token to retrieve next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ImportDataRequest(
	  /** Required. Specify the input source of the data. */
		inputConfig: Option[Schema.GoogleCloudDatalabelingV1beta1InputConfig] = None,
	  /** Email of the user who started the import task and should be notified by email. If empty no notification will be sent. */
		userEmailAddress: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ExportDataRequest(
	  /** Required. Annotated dataset resource name. DataItem in Dataset and their annotations in specified annotated dataset will be exported. It's in format of projects/{project_id}/datasets/{dataset_id}/annotatedDatasets/ {annotated_dataset_id} */
		annotatedDataset: Option[String] = None,
	  /** Optional. Filter is not supported at this moment. */
		filter: Option[String] = None,
	  /** Required. Specify the output destination. */
		outputConfig: Option[Schema.GoogleCloudDatalabelingV1beta1OutputConfig] = None,
	  /** Email of the user who started the export task and should be notified by email. If empty no notification will be sent. */
		userEmailAddress: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1OutputConfig(
	  /** Output to a file in Cloud Storage. Should be used for labeling output other than image segmentation. */
		gcsDestination: Option[Schema.GoogleCloudDatalabelingV1beta1GcsDestination] = None,
	  /** Output to a folder in Cloud Storage. Should be used for image segmentation or document de-identification labeling outputs. */
		gcsFolderDestination: Option[Schema.GoogleCloudDatalabelingV1beta1GcsFolderDestination] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1GcsDestination(
	  /** Required. The output uri of destination file. */
		outputUri: Option[String] = None,
	  /** Required. The format of the gcs destination. Only "text/csv" and "application/json" are supported. */
		mimeType: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1GcsFolderDestination(
	  /** Required. Cloud Storage directory to export data to. */
		outputFolderUri: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1DataItem(
	  /** The image payload, a container of the image bytes/uri. */
		imagePayload: Option[Schema.GoogleCloudDatalabelingV1beta1ImagePayload] = None,
	  /** The text payload, a container of text content. */
		textPayload: Option[Schema.GoogleCloudDatalabelingV1beta1TextPayload] = None,
	  /** The video payload, a container of the video uri. */
		videoPayload: Option[Schema.GoogleCloudDatalabelingV1beta1VideoPayload] = None,
	  /** Output only. Name of the data item, in format of: projects/{project_id}/datasets/{dataset_id}/dataItems/{data_item_id} */
		name: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ImagePayload(
	  /** Image format. */
		mimeType: Option[String] = None,
	  /** A byte string of a thumbnail image. */
		imageThumbnail: Option[String] = None,
	  /** Image uri from the user bucket. */
		imageUri: Option[String] = None,
	  /** Signed uri of the image file in the service bucket. */
		signedUri: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1TextPayload(
	  /** Text content. */
		textContent: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1VideoPayload(
	  /** Video format. */
		mimeType: Option[String] = None,
	  /** Video uri from the user bucket. */
		videoUri: Option[String] = None,
	  /** The list of video thumbnails. */
		videoThumbnails: Option[List[Schema.GoogleCloudDatalabelingV1beta1VideoThumbnail]] = None,
	  /** FPS of the video. */
		frameRate: Option[BigDecimal] = None,
	  /** Signed uri of the video file in the service bucket. */
		signedUri: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1VideoThumbnail(
	  /** A byte string of the video frame. */
		thumbnail: Option[String] = None,
	  /** Time offset relative to the beginning of the video, corresponding to the video frame where the thumbnail has been extracted from. */
		timeOffset: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ListDataItemsResponse(
	  /** The list of data items to return. */
		dataItems: Option[List[Schema.GoogleCloudDatalabelingV1beta1DataItem]] = None,
	  /** A token to retrieve next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudDatalabelingV1beta1AnnotatedDataset {
		enum AnnotationSourceEnum extends Enum[AnnotationSourceEnum] { case ANNOTATION_SOURCE_UNSPECIFIED, OPERATOR }
		enum AnnotationTypeEnum extends Enum[AnnotationTypeEnum] { case ANNOTATION_TYPE_UNSPECIFIED, IMAGE_CLASSIFICATION_ANNOTATION, IMAGE_BOUNDING_BOX_ANNOTATION, IMAGE_ORIENTED_BOUNDING_BOX_ANNOTATION, IMAGE_BOUNDING_POLY_ANNOTATION, IMAGE_POLYLINE_ANNOTATION, IMAGE_SEGMENTATION_ANNOTATION, VIDEO_SHOTS_CLASSIFICATION_ANNOTATION, VIDEO_OBJECT_TRACKING_ANNOTATION, VIDEO_OBJECT_DETECTION_ANNOTATION, VIDEO_EVENT_ANNOTATION, TEXT_CLASSIFICATION_ANNOTATION, TEXT_ENTITY_EXTRACTION_ANNOTATION, GENERAL_CLASSIFICATION_ANNOTATION }
	}
	case class GoogleCloudDatalabelingV1beta1AnnotatedDataset(
	  /** Output only. AnnotatedDataset resource name in format of: projects/{project_id}/datasets/{dataset_id}/annotatedDatasets/ {annotated_dataset_id} */
		name: Option[String] = None,
	  /** Output only. The display name of the AnnotatedDataset. It is specified in HumanAnnotationConfig when user starts a labeling task. Maximum of 64 characters. */
		displayName: Option[String] = None,
	  /** Output only. The description of the AnnotatedDataset. It is specified in HumanAnnotationConfig when user starts a labeling task. Maximum of 10000 characters. */
		description: Option[String] = None,
	  /** Output only. Source of the annotation. */
		annotationSource: Option[Schema.GoogleCloudDatalabelingV1beta1AnnotatedDataset.AnnotationSourceEnum] = None,
	  /** Output only. Type of the annotation. It is specified when starting labeling task. */
		annotationType: Option[Schema.GoogleCloudDatalabelingV1beta1AnnotatedDataset.AnnotationTypeEnum] = None,
	  /** Output only. Number of examples in the annotated dataset. */
		exampleCount: Option[String] = None,
	  /** Output only. Number of examples that have annotation in the annotated dataset. */
		completedExampleCount: Option[String] = None,
	  /** Output only. Per label statistics. */
		labelStats: Option[Schema.GoogleCloudDatalabelingV1beta1LabelStats] = None,
	  /** Output only. Time the AnnotatedDataset was created. */
		createTime: Option[String] = None,
	  /** Output only. Additional information about AnnotatedDataset. */
		metadata: Option[Schema.GoogleCloudDatalabelingV1beta1AnnotatedDatasetMetadata] = None,
	  /** Output only. The names of any related resources that are blocking changes to the annotated dataset. */
		blockingResources: Option[List[String]] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1LabelStats(
	  /** Map of each annotation spec's example count. Key is the annotation spec name and value is the number of examples for that annotation spec. If the annotated dataset does not have annotation spec, the map will return a pair where the key is empty string and value is the total number of annotations. */
		exampleCount: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1AnnotatedDatasetMetadata(
	  /** Configuration for image classification task. */
		imageClassificationConfig: Option[Schema.GoogleCloudDatalabelingV1beta1ImageClassificationConfig] = None,
	  /** Configuration for image bounding box and bounding poly task. */
		boundingPolyConfig: Option[Schema.GoogleCloudDatalabelingV1beta1BoundingPolyConfig] = None,
	  /** Configuration for image polyline task. */
		polylineConfig: Option[Schema.GoogleCloudDatalabelingV1beta1PolylineConfig] = None,
	  /** Configuration for image segmentation task. */
		segmentationConfig: Option[Schema.GoogleCloudDatalabelingV1beta1SegmentationConfig] = None,
	  /** Configuration for video classification task. */
		videoClassificationConfig: Option[Schema.GoogleCloudDatalabelingV1beta1VideoClassificationConfig] = None,
	  /** Configuration for video object detection task. */
		objectDetectionConfig: Option[Schema.GoogleCloudDatalabelingV1beta1ObjectDetectionConfig] = None,
	  /** Configuration for video object tracking task. */
		objectTrackingConfig: Option[Schema.GoogleCloudDatalabelingV1beta1ObjectTrackingConfig] = None,
	  /** Configuration for video event labeling task. */
		eventConfig: Option[Schema.GoogleCloudDatalabelingV1beta1EventConfig] = None,
	  /** Configuration for text classification task. */
		textClassificationConfig: Option[Schema.GoogleCloudDatalabelingV1beta1TextClassificationConfig] = None,
	  /** Configuration for text entity extraction task. */
		textEntityExtractionConfig: Option[Schema.GoogleCloudDatalabelingV1beta1TextEntityExtractionConfig] = None,
	  /** HumanAnnotationConfig used when requesting the human labeling task for this AnnotatedDataset. */
		humanAnnotationConfig: Option[Schema.GoogleCloudDatalabelingV1beta1HumanAnnotationConfig] = None
	)
	
	object GoogleCloudDatalabelingV1beta1ImageClassificationConfig {
		enum AnswerAggregationTypeEnum extends Enum[AnswerAggregationTypeEnum] { case STRING_AGGREGATION_TYPE_UNSPECIFIED, MAJORITY_VOTE, UNANIMOUS_VOTE, NO_AGGREGATION }
	}
	case class GoogleCloudDatalabelingV1beta1ImageClassificationConfig(
	  /** Required. Annotation spec set resource name. */
		annotationSpecSet: Option[String] = None,
	  /** Optional. If allow_multi_label is true, contributors are able to choose multiple labels for one image. */
		allowMultiLabel: Option[Boolean] = None,
	  /** Optional. The type of how to aggregate answers. */
		answerAggregationType: Option[Schema.GoogleCloudDatalabelingV1beta1ImageClassificationConfig.AnswerAggregationTypeEnum] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1BoundingPolyConfig(
	  /** Required. Annotation spec set resource name. */
		annotationSpecSet: Option[String] = None,
	  /** Optional. Instruction message showed on contributors UI. */
		instructionMessage: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1PolylineConfig(
	  /** Required. Annotation spec set resource name. */
		annotationSpecSet: Option[String] = None,
	  /** Optional. Instruction message showed on contributors UI. */
		instructionMessage: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1SegmentationConfig(
	  /** Required. Annotation spec set resource name. format: projects/{project_id}/annotationSpecSets/{annotation_spec_set_id} */
		annotationSpecSet: Option[String] = None,
	  /** Instruction message showed on labelers UI. */
		instructionMessage: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1VideoClassificationConfig(
	  /** Required. The list of annotation spec set configs. Since watching a video clip takes much longer time than an image, we support label with multiple AnnotationSpecSet at the same time. Labels in each AnnotationSpecSet will be shown in a group to contributors. Contributors can select one or more (depending on whether to allow multi label) from each group. */
		annotationSpecSetConfigs: Option[List[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpecSetConfig]] = None,
	  /** Optional. Option to apply shot detection on the video. */
		applyShotDetection: Option[Boolean] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1AnnotationSpecSetConfig(
	  /** Required. Annotation spec set resource name. */
		annotationSpecSet: Option[String] = None,
	  /** Optional. If allow_multi_label is true, contributors are able to choose multiple labels from one annotation spec set. */
		allowMultiLabel: Option[Boolean] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ObjectDetectionConfig(
	  /** Required. Annotation spec set resource name. */
		annotationSpecSet: Option[String] = None,
	  /** Required. Number of frames per second to be extracted from the video. */
		extractionFrameRate: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ObjectTrackingConfig(
	  /** Required. Annotation spec set resource name. */
		annotationSpecSet: Option[String] = None,
	  /** Videos will be cut to smaller clips to make it easier for labelers to work on. Users can configure is field in seconds, if not set, default value is 20s. */
		clipLength: Option[Int] = None,
	  /** The overlap length between different video clips. Users can configure is field in seconds, if not set, default value is 0.3s. */
		overlapLength: Option[Int] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1EventConfig(
	  /** Required. The list of annotation spec set resource name. Similar to video classification, we support selecting event from multiple AnnotationSpecSet at the same time. */
		annotationSpecSets: Option[List[String]] = None,
	  /** Videos will be cut to smaller clips to make it easier for labelers to work on. Users can configure is field in seconds, if not set, default value is 60s. */
		clipLength: Option[Int] = None,
	  /** The overlap length between different video clips. Users can configure is field in seconds, if not set, default value is 1s. */
		overlapLength: Option[Int] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1TextClassificationConfig(
	  /** Optional. If allow_multi_label is true, contributors are able to choose multiple labels for one text segment. */
		allowMultiLabel: Option[Boolean] = None,
	  /** Required. Annotation spec set resource name. */
		annotationSpecSet: Option[String] = None,
	  /** Optional. Configs for sentiment selection. We deprecate sentiment analysis in data labeling side as it is incompatible with uCAIP. */
		sentimentConfig: Option[Schema.GoogleCloudDatalabelingV1beta1SentimentConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1SentimentConfig(
	  /** If set to true, contributors will have the option to select sentiment of the label they selected, to mark it as negative or positive label. Default is false. */
		enableLabelSentimentSelection: Option[Boolean] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1TextEntityExtractionConfig(
	  /** Required. Annotation spec set resource name. */
		annotationSpecSet: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1HumanAnnotationConfig(
	  /** Required. Instruction resource name. */
		instruction: Option[String] = None,
	  /** Required. A human-readable name for AnnotatedDataset defined by users. Maximum of 64 characters . */
		annotatedDatasetDisplayName: Option[String] = None,
	  /** Optional. A human-readable description for AnnotatedDataset. The description can be up to 10000 characters long. */
		annotatedDatasetDescription: Option[String] = None,
	  /** Optional. A human-readable label used to logically group labeling tasks. This string must match the regular expression `[a-zA-Z\\d_-]{0,128}`. */
		labelGroup: Option[String] = None,
	  /** Optional. The Language of this question, as a [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt). Default value is en-US. Only need to set this when task is language related. For example, French text classification. */
		languageCode: Option[String] = None,
	  /** Optional. Replication of questions. Each question will be sent to up to this number of contributors to label. Aggregated answers will be returned. Default is set to 1. For image related labeling, valid values are 1, 3, 5. */
		replicaCount: Option[Int] = None,
	  /** Optional. Maximum duration for contributors to answer a question. Maximum is 3600 seconds. Default is 3600 seconds. */
		questionDuration: Option[String] = None,
	  /** Optional. If you want your own labeling contributors to manage and work on this labeling request, you can set these contributors here. We will give them access to the question types in crowdcompute. Note that these emails must be registered in crowdcompute worker UI: https://crowd-compute.appspot.com/ */
		contributorEmails: Option[List[String]] = None,
	  /** Email of the user who started the labeling task and should be notified by email. If empty no notification will be sent. */
		userEmailAddress: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ListAnnotatedDatasetsResponse(
	  /** The list of annotated datasets to return. */
		annotatedDatasets: Option[List[Schema.GoogleCloudDatalabelingV1beta1AnnotatedDataset]] = None,
	  /** A token to retrieve next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudDatalabelingV1beta1LabelImageRequest {
		enum FeatureEnum extends Enum[FeatureEnum] { case FEATURE_UNSPECIFIED, CLASSIFICATION, BOUNDING_BOX, ORIENTED_BOUNDING_BOX, BOUNDING_POLY, POLYLINE, SEGMENTATION }
	}
	case class GoogleCloudDatalabelingV1beta1LabelImageRequest(
	  /** Configuration for image classification task. One of image_classification_config, bounding_poly_config, polyline_config and segmentation_config are required. */
		imageClassificationConfig: Option[Schema.GoogleCloudDatalabelingV1beta1ImageClassificationConfig] = None,
	  /** Configuration for bounding box and bounding poly task. One of image_classification_config, bounding_poly_config, polyline_config and segmentation_config are required. */
		boundingPolyConfig: Option[Schema.GoogleCloudDatalabelingV1beta1BoundingPolyConfig] = None,
	  /** Configuration for polyline task. One of image_classification_config, bounding_poly_config, polyline_config and segmentation_config are required. */
		polylineConfig: Option[Schema.GoogleCloudDatalabelingV1beta1PolylineConfig] = None,
	  /** Configuration for segmentation task. One of image_classification_config, bounding_poly_config, polyline_config and segmentation_config are required. */
		segmentationConfig: Option[Schema.GoogleCloudDatalabelingV1beta1SegmentationConfig] = None,
	  /** Required. Basic human annotation config. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1beta1HumanAnnotationConfig] = None,
	  /** Required. The type of image labeling task. */
		feature: Option[Schema.GoogleCloudDatalabelingV1beta1LabelImageRequest.FeatureEnum] = None
	)
	
	object GoogleCloudDatalabelingV1beta1LabelVideoRequest {
		enum FeatureEnum extends Enum[FeatureEnum] { case FEATURE_UNSPECIFIED, CLASSIFICATION, OBJECT_DETECTION, OBJECT_TRACKING, EVENT }
	}
	case class GoogleCloudDatalabelingV1beta1LabelVideoRequest(
	  /** Configuration for video classification task. One of video_classification_config, object_detection_config, object_tracking_config and event_config is required. */
		videoClassificationConfig: Option[Schema.GoogleCloudDatalabelingV1beta1VideoClassificationConfig] = None,
	  /** Configuration for video object detection task. One of video_classification_config, object_detection_config, object_tracking_config and event_config is required. */
		objectDetectionConfig: Option[Schema.GoogleCloudDatalabelingV1beta1ObjectDetectionConfig] = None,
	  /** Configuration for video object tracking task. One of video_classification_config, object_detection_config, object_tracking_config and event_config is required. */
		objectTrackingConfig: Option[Schema.GoogleCloudDatalabelingV1beta1ObjectTrackingConfig] = None,
	  /** Configuration for video event task. One of video_classification_config, object_detection_config, object_tracking_config and event_config is required. */
		eventConfig: Option[Schema.GoogleCloudDatalabelingV1beta1EventConfig] = None,
	  /** Required. Basic human annotation config. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1beta1HumanAnnotationConfig] = None,
	  /** Required. The type of video labeling task. */
		feature: Option[Schema.GoogleCloudDatalabelingV1beta1LabelVideoRequest.FeatureEnum] = None
	)
	
	object GoogleCloudDatalabelingV1beta1LabelTextRequest {
		enum FeatureEnum extends Enum[FeatureEnum] { case FEATURE_UNSPECIFIED, TEXT_CLASSIFICATION, TEXT_ENTITY_EXTRACTION }
	}
	case class GoogleCloudDatalabelingV1beta1LabelTextRequest(
	  /** Configuration for text classification task. One of text_classification_config and text_entity_extraction_config is required. */
		textClassificationConfig: Option[Schema.GoogleCloudDatalabelingV1beta1TextClassificationConfig] = None,
	  /** Configuration for entity extraction task. One of text_classification_config and text_entity_extraction_config is required. */
		textEntityExtractionConfig: Option[Schema.GoogleCloudDatalabelingV1beta1TextEntityExtractionConfig] = None,
	  /** Required. Basic human annotation config. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1beta1HumanAnnotationConfig] = None,
	  /** Required. The type of text labeling task. */
		feature: Option[Schema.GoogleCloudDatalabelingV1beta1LabelTextRequest.FeatureEnum] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1Example(
	  /** The image payload, a container of the image bytes/uri. */
		imagePayload: Option[Schema.GoogleCloudDatalabelingV1beta1ImagePayload] = None,
	  /** The text payload, a container of the text content. */
		textPayload: Option[Schema.GoogleCloudDatalabelingV1beta1TextPayload] = None,
	  /** The video payload, a container of the video uri. */
		videoPayload: Option[Schema.GoogleCloudDatalabelingV1beta1VideoPayload] = None,
	  /** Output only. Name of the example, in format of: projects/{project_id}/datasets/{dataset_id}/annotatedDatasets/ {annotated_dataset_id}/examples/{example_id} */
		name: Option[String] = None,
	  /** Output only. Annotations for the piece of data in Example. One piece of data can have multiple annotations. */
		annotations: Option[List[Schema.GoogleCloudDatalabelingV1beta1Annotation]] = None
	)
	
	object GoogleCloudDatalabelingV1beta1Annotation {
		enum AnnotationSourceEnum extends Enum[AnnotationSourceEnum] { case ANNOTATION_SOURCE_UNSPECIFIED, OPERATOR }
		enum AnnotationSentimentEnum extends Enum[AnnotationSentimentEnum] { case ANNOTATION_SENTIMENT_UNSPECIFIED, NEGATIVE, POSITIVE }
	}
	case class GoogleCloudDatalabelingV1beta1Annotation(
	  /** Output only. Unique name of this annotation, format is: projects/{project_id}/datasets/{dataset_id}/annotatedDatasets/{annotated_dataset}/examples/{example_id}/annotations/{annotation_id} */
		name: Option[String] = None,
	  /** Output only. The source of the annotation. */
		annotationSource: Option[Schema.GoogleCloudDatalabelingV1beta1Annotation.AnnotationSourceEnum] = None,
	  /** Output only. This is the actual annotation value, e.g classification, bounding box values are stored here. */
		annotationValue: Option[Schema.GoogleCloudDatalabelingV1beta1AnnotationValue] = None,
	  /** Output only. Annotation metadata, including information like votes for labels. */
		annotationMetadata: Option[Schema.GoogleCloudDatalabelingV1beta1AnnotationMetadata] = None,
	  /** Output only. Sentiment for this annotation. */
		annotationSentiment: Option[Schema.GoogleCloudDatalabelingV1beta1Annotation.AnnotationSentimentEnum] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1AnnotationValue(
	  /** Annotation value for image classification case. */
		imageClassificationAnnotation: Option[Schema.GoogleCloudDatalabelingV1beta1ImageClassificationAnnotation] = None,
	  /** Annotation value for image bounding box, oriented bounding box and polygon cases. */
		imageBoundingPolyAnnotation: Option[Schema.GoogleCloudDatalabelingV1beta1ImageBoundingPolyAnnotation] = None,
	  /** Annotation value for image polyline cases. Polyline here is different from BoundingPoly. It is formed by line segments connected to each other but not closed form(Bounding Poly). The line segments can cross each other. */
		imagePolylineAnnotation: Option[Schema.GoogleCloudDatalabelingV1beta1ImagePolylineAnnotation] = None,
	  /** Annotation value for image segmentation. */
		imageSegmentationAnnotation: Option[Schema.GoogleCloudDatalabelingV1beta1ImageSegmentationAnnotation] = None,
	  /** Annotation value for text classification case. */
		textClassificationAnnotation: Option[Schema.GoogleCloudDatalabelingV1beta1TextClassificationAnnotation] = None,
	  /** Annotation value for text entity extraction case. */
		textEntityExtractionAnnotation: Option[Schema.GoogleCloudDatalabelingV1beta1TextEntityExtractionAnnotation] = None,
	  /** Annotation value for video classification case. */
		videoClassificationAnnotation: Option[Schema.GoogleCloudDatalabelingV1beta1VideoClassificationAnnotation] = None,
	  /** Annotation value for video object detection and tracking case. */
		videoObjectTrackingAnnotation: Option[Schema.GoogleCloudDatalabelingV1beta1VideoObjectTrackingAnnotation] = None,
	  /** Annotation value for video event case. */
		videoEventAnnotation: Option[Schema.GoogleCloudDatalabelingV1beta1VideoEventAnnotation] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ImageClassificationAnnotation(
	  /** Label of image. */
		annotationSpec: Option[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpec] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1AnnotationSpec(
	  /** Required. The display name of the AnnotationSpec. Maximum of 64 characters. */
		displayName: Option[String] = None,
	  /** Optional. User-provided description of the annotation specification. The description can be up to 10,000 characters long. */
		description: Option[String] = None,
	  /** Output only. This is the integer index of the AnnotationSpec. The index for the whole AnnotationSpecSet is sequential starting from 0. For example, an AnnotationSpecSet with classes `dog` and `cat`, might contain one AnnotationSpec with `{ display_name: "dog", index: 0 }` and one AnnotationSpec with `{ display_name: "cat", index: 1 }`. This is especially useful for model training as it encodes the string labels into numeric values. */
		index: Option[Int] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ImageBoundingPolyAnnotation(
		boundingPoly: Option[Schema.GoogleCloudDatalabelingV1beta1BoundingPoly] = None,
		normalizedBoundingPoly: Option[Schema.GoogleCloudDatalabelingV1beta1NormalizedBoundingPoly] = None,
	  /** Label of object in this bounding polygon. */
		annotationSpec: Option[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpec] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1BoundingPoly(
	  /** The bounding polygon vertices. */
		vertices: Option[List[Schema.GoogleCloudDatalabelingV1beta1Vertex]] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1Vertex(
	  /** X coordinate. */
		x: Option[Int] = None,
	  /** Y coordinate. */
		y: Option[Int] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1NormalizedBoundingPoly(
	  /** The bounding polygon normalized vertices. */
		normalizedVertices: Option[List[Schema.GoogleCloudDatalabelingV1beta1NormalizedVertex]] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1NormalizedVertex(
	  /** X coordinate. */
		x: Option[BigDecimal] = None,
	  /** Y coordinate. */
		y: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ImagePolylineAnnotation(
		polyline: Option[Schema.GoogleCloudDatalabelingV1beta1Polyline] = None,
		normalizedPolyline: Option[Schema.GoogleCloudDatalabelingV1beta1NormalizedPolyline] = None,
	  /** Label of this polyline. */
		annotationSpec: Option[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpec] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1Polyline(
	  /** The polyline vertices. */
		vertices: Option[List[Schema.GoogleCloudDatalabelingV1beta1Vertex]] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1NormalizedPolyline(
	  /** The normalized polyline vertices. */
		normalizedVertices: Option[List[Schema.GoogleCloudDatalabelingV1beta1NormalizedVertex]] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ImageSegmentationAnnotation(
	  /** The mapping between rgb color and annotation spec. The key is the rgb color represented in format of rgb(0, 0, 0). The value is the AnnotationSpec. */
		annotationColors: Option[Map[String, Schema.GoogleCloudDatalabelingV1beta1AnnotationSpec]] = None,
	  /** Image format. */
		mimeType: Option[String] = None,
	  /** A byte string of a full image's color map. */
		imageBytes: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1TextClassificationAnnotation(
	  /** Label of the text. */
		annotationSpec: Option[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpec] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1TextEntityExtractionAnnotation(
	  /** Label of the text entities. */
		annotationSpec: Option[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpec] = None,
	  /** Position of the entity. */
		sequentialSegment: Option[Schema.GoogleCloudDatalabelingV1beta1SequentialSegment] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1SequentialSegment(
	  /** Start position (inclusive). */
		start: Option[Int] = None,
	  /** End position (exclusive). */
		end: Option[Int] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1VideoClassificationAnnotation(
	  /** The time segment of the video to which the annotation applies. */
		timeSegment: Option[Schema.GoogleCloudDatalabelingV1beta1TimeSegment] = None,
	  /** Label of the segment specified by time_segment. */
		annotationSpec: Option[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpec] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1TimeSegment(
	  /** Start of the time segment (inclusive), represented as the duration since the example start. */
		startTimeOffset: Option[String] = None,
	  /** End of the time segment (exclusive), represented as the duration since the example start. */
		endTimeOffset: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1VideoObjectTrackingAnnotation(
	  /** Label of the object tracked in this annotation. */
		annotationSpec: Option[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpec] = None,
	  /** The time segment of the video to which object tracking applies. */
		timeSegment: Option[Schema.GoogleCloudDatalabelingV1beta1TimeSegment] = None,
	  /** The list of frames where this object track appears. */
		objectTrackingFrames: Option[List[Schema.GoogleCloudDatalabelingV1beta1ObjectTrackingFrame]] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ObjectTrackingFrame(
		boundingPoly: Option[Schema.GoogleCloudDatalabelingV1beta1BoundingPoly] = None,
		normalizedBoundingPoly: Option[Schema.GoogleCloudDatalabelingV1beta1NormalizedBoundingPoly] = None,
	  /** The time offset of this frame relative to the beginning of the video. */
		timeOffset: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1VideoEventAnnotation(
	  /** Label of the event in this annotation. */
		annotationSpec: Option[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpec] = None,
	  /** The time segment of the video to which the annotation applies. */
		timeSegment: Option[Schema.GoogleCloudDatalabelingV1beta1TimeSegment] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1AnnotationMetadata(
	  /** Metadata related to human labeling. */
		operatorMetadata: Option[Schema.GoogleCloudDatalabelingV1beta1OperatorMetadata] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1OperatorMetadata(
	  /** Confidence score corresponding to a label. For examle, if 3 contributors have answered the question and 2 of them agree on the final label, the confidence score will be 0.67 (2/3). */
		score: Option[BigDecimal] = None,
	  /** The total number of contributors that answer this question. */
		totalVotes: Option[Int] = None,
	  /** The total number of contributors that choose this label. */
		labelVotes: Option[Int] = None,
	  /** Comments from contributors. */
		comments: Option[List[String]] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ListExamplesResponse(
	  /** The list of examples to return. */
		examples: Option[List[Schema.GoogleCloudDatalabelingV1beta1Example]] = None,
	  /** A token to retrieve next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1CreateAnnotationSpecSetRequest(
	  /** Required. Annotation spec set to create. Annotation specs must be included. Only one annotation spec will be accepted for annotation specs with same display_name. */
		annotationSpecSet: Option[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpecSet] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1AnnotationSpecSet(
	  /** Output only. The AnnotationSpecSet resource name in the following format: "projects/{project_id}/annotationSpecSets/{annotation_spec_set_id}" */
		name: Option[String] = None,
	  /** Required. The display name for AnnotationSpecSet that you define when you create it. Maximum of 64 characters. */
		displayName: Option[String] = None,
	  /** Optional. User-provided description of the annotation specification set. The description can be up to 10,000 characters long. */
		description: Option[String] = None,
	  /** Required. The array of AnnotationSpecs that you define when you create the AnnotationSpecSet. These are the possible labels for the labeling task. */
		annotationSpecs: Option[List[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpec]] = None,
	  /** Output only. The names of any related resources that are blocking changes to the annotation spec set. */
		blockingResources: Option[List[String]] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ListAnnotationSpecSetsResponse(
	  /** The list of annotation spec sets. */
		annotationSpecSets: Option[List[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpecSet]] = None,
	  /** A token to retrieve next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1CreateInstructionRequest(
	  /** Required. Instruction of how to perform the labeling task. */
		instruction: Option[Schema.GoogleCloudDatalabelingV1beta1Instruction] = None
	)
	
	object GoogleCloudDatalabelingV1beta1Instruction {
		enum DataTypeEnum extends Enum[DataTypeEnum] { case DATA_TYPE_UNSPECIFIED, IMAGE, VIDEO, TEXT, GENERAL_DATA }
	}
	case class GoogleCloudDatalabelingV1beta1Instruction(
	  /** Output only. Instruction resource name, format: projects/{project_id}/instructions/{instruction_id} */
		name: Option[String] = None,
	  /** Required. The display name of the instruction. Maximum of 64 characters. */
		displayName: Option[String] = None,
	  /** Optional. User-provided description of the instruction. The description can be up to 10000 characters long. */
		description: Option[String] = None,
	  /** Output only. Creation time of instruction. */
		createTime: Option[String] = None,
	  /** Output only. Last update time of instruction. */
		updateTime: Option[String] = None,
	  /** Required. The data type of this instruction. */
		dataType: Option[Schema.GoogleCloudDatalabelingV1beta1Instruction.DataTypeEnum] = None,
	  /** Deprecated: this instruction format is not supported any more. Instruction from a CSV file, such as for classification task. The CSV file should have exact two columns, in the following format: &#42; The first column is labeled data, such as an image reference, text. &#42; The second column is comma separated labels associated with data. */
		csvInstruction: Option[Schema.GoogleCloudDatalabelingV1beta1CsvInstruction] = None,
	  /** Instruction from a PDF document. The PDF should be in a Cloud Storage bucket. */
		pdfInstruction: Option[Schema.GoogleCloudDatalabelingV1beta1PdfInstruction] = None,
	  /** Output only. The names of any related resources that are blocking changes to the instruction. */
		blockingResources: Option[List[String]] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1CsvInstruction(
	  /** CSV file for the instruction. Only gcs path is allowed. */
		gcsFileUri: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1PdfInstruction(
	  /** PDF file for the instruction. Only gcs path is allowed. */
		gcsFileUri: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ListInstructionsResponse(
	  /** The list of Instructions to return. */
		instructions: Option[List[Schema.GoogleCloudDatalabelingV1beta1Instruction]] = None,
	  /** A token to retrieve next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudDatalabelingV1beta1Evaluation {
		enum AnnotationTypeEnum extends Enum[AnnotationTypeEnum] { case ANNOTATION_TYPE_UNSPECIFIED, IMAGE_CLASSIFICATION_ANNOTATION, IMAGE_BOUNDING_BOX_ANNOTATION, IMAGE_ORIENTED_BOUNDING_BOX_ANNOTATION, IMAGE_BOUNDING_POLY_ANNOTATION, IMAGE_POLYLINE_ANNOTATION, IMAGE_SEGMENTATION_ANNOTATION, VIDEO_SHOTS_CLASSIFICATION_ANNOTATION, VIDEO_OBJECT_TRACKING_ANNOTATION, VIDEO_OBJECT_DETECTION_ANNOTATION, VIDEO_EVENT_ANNOTATION, TEXT_CLASSIFICATION_ANNOTATION, TEXT_ENTITY_EXTRACTION_ANNOTATION, GENERAL_CLASSIFICATION_ANNOTATION }
	}
	case class GoogleCloudDatalabelingV1beta1Evaluation(
	  /** Output only. Resource name of an evaluation. The name has the following format: "projects/{project_id}/datasets/{dataset_id}/evaluations/ {evaluation_id}' */
		name: Option[String] = None,
	  /** Output only. Options used in the evaluation job that created this evaluation. */
		config: Option[Schema.GoogleCloudDatalabelingV1beta1EvaluationConfig] = None,
	  /** Output only. Timestamp for when the evaluation job that created this evaluation ran. */
		evaluationJobRunTime: Option[String] = None,
	  /** Output only. Timestamp for when this evaluation was created. */
		createTime: Option[String] = None,
	  /** Output only. Metrics comparing predictions to ground truth labels. */
		evaluationMetrics: Option[Schema.GoogleCloudDatalabelingV1beta1EvaluationMetrics] = None,
	  /** Output only. Type of task that the model version being evaluated performs, as defined in the evaluationJobConfig.inputConfig.annotationType field of the evaluation job that created this evaluation. */
		annotationType: Option[Schema.GoogleCloudDatalabelingV1beta1Evaluation.AnnotationTypeEnum] = None,
	  /** Output only. The number of items in the ground truth dataset that were used for this evaluation. Only populated when the evaulation is for certain AnnotationTypes. */
		evaluatedItemCount: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1EvaluationConfig(
	  /** Only specify this field if the related model performs image object detection (`IMAGE_BOUNDING_BOX_ANNOTATION`). Describes how to evaluate bounding boxes. */
		boundingBoxEvaluationOptions: Option[Schema.GoogleCloudDatalabelingV1beta1BoundingBoxEvaluationOptions] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1BoundingBoxEvaluationOptions(
	  /** Minimum [intersection-over-union (IOU)](/vision/automl/object-detection/docs/evaluate#intersection-over-union) required for 2 bounding boxes to be considered a match. This must be a number between 0 and 1. */
		iouThreshold: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1EvaluationMetrics(
		classificationMetrics: Option[Schema.GoogleCloudDatalabelingV1beta1ClassificationMetrics] = None,
		objectDetectionMetrics: Option[Schema.GoogleCloudDatalabelingV1beta1ObjectDetectionMetrics] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ClassificationMetrics(
	  /** Precision-recall curve based on ground truth labels, predicted labels, and scores for the predicted labels. */
		prCurve: Option[Schema.GoogleCloudDatalabelingV1beta1PrCurve] = None,
	  /** Confusion matrix of predicted labels vs. ground truth labels. */
		confusionMatrix: Option[Schema.GoogleCloudDatalabelingV1beta1ConfusionMatrix] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1PrCurve(
	  /** The annotation spec of the label for which the precision-recall curve calculated. If this field is empty, that means the precision-recall curve is an aggregate curve for all labels. */
		annotationSpec: Option[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpec] = None,
	  /** Area under the precision-recall curve. Not to be confused with area under a receiver operating characteristic (ROC) curve. */
		areaUnderCurve: Option[BigDecimal] = None,
	  /** Entries that make up the precision-recall graph. Each entry is a "point" on the graph drawn for a different `confidence_threshold`. */
		confidenceMetricsEntries: Option[List[Schema.GoogleCloudDatalabelingV1beta1ConfidenceMetricsEntry]] = None,
	  /** Mean average prcision of this curve. */
		meanAveragePrecision: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ConfidenceMetricsEntry(
	  /** Threshold used for this entry. For classification tasks, this is a classification threshold: a predicted label is categorized as positive or negative (in the context of this point on the PR curve) based on whether the label's score meets this threshold. For image object detection (bounding box) tasks, this is the [intersection-over-union (IOU)](/vision/automl/object-detection/docs/evaluate#intersection-over-union) threshold for the context of this point on the PR curve. */
		confidenceThreshold: Option[BigDecimal] = None,
	  /** Recall value. */
		recall: Option[BigDecimal] = None,
	  /** Precision value. */
		precision: Option[BigDecimal] = None,
	  /** Harmonic mean of recall and precision. */
		f1Score: Option[BigDecimal] = None,
	  /** Recall value for entries with label that has highest score. */
		recallAt1: Option[BigDecimal] = None,
	  /** Precision value for entries with label that has highest score. */
		precisionAt1: Option[BigDecimal] = None,
	  /** The harmonic mean of recall_at1 and precision_at1. */
		f1ScoreAt1: Option[BigDecimal] = None,
	  /** Recall value for entries with label that has highest 5 scores. */
		recallAt5: Option[BigDecimal] = None,
	  /** Precision value for entries with label that has highest 5 scores. */
		precisionAt5: Option[BigDecimal] = None,
	  /** The harmonic mean of recall_at5 and precision_at5. */
		f1ScoreAt5: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ConfusionMatrix(
		row: Option[List[Schema.GoogleCloudDatalabelingV1beta1Row]] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1Row(
	  /** The annotation spec of the ground truth label for this row. */
		annotationSpec: Option[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpec] = None,
	  /** A list of the confusion matrix entries. One entry for each possible predicted label. */
		entries: Option[List[Schema.GoogleCloudDatalabelingV1beta1ConfusionMatrixEntry]] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ConfusionMatrixEntry(
	  /** The annotation spec of a predicted label. */
		annotationSpec: Option[Schema.GoogleCloudDatalabelingV1beta1AnnotationSpec] = None,
	  /** Number of items predicted to have this label. (The ground truth label for these items is the `Row.annotationSpec` of this entry's parent.) */
		itemCount: Option[Int] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ObjectDetectionMetrics(
	  /** Precision-recall curve. */
		prCurve: Option[Schema.GoogleCloudDatalabelingV1beta1PrCurve] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1SearchEvaluationsResponse(
	  /** The list of evaluations matching the search. */
		evaluations: Option[List[Schema.GoogleCloudDatalabelingV1beta1Evaluation]] = None,
	  /** A token to retrieve next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1SearchExampleComparisonsRequest(
	  /** Optional. Requested page size. Server may return fewer results than requested. Default value is 100. */
		pageSize: Option[Int] = None,
	  /** Optional. A token identifying a page of results for the server to return. Typically obtained by the nextPageToken of the response to a previous search rquest. If you don't specify this field, the API call requests the first page of the search. */
		pageToken: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1SearchExampleComparisonsResponse(
	  /** A list of example comparisons matching the search criteria. */
		exampleComparisons: Option[List[Schema.GoogleCloudDatalabelingV1beta1ExampleComparison]] = None,
	  /** A token to retrieve next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ExampleComparison(
	  /** The ground truth output for the input. */
		groundTruthExample: Option[Schema.GoogleCloudDatalabelingV1beta1Example] = None,
	  /** Predictions by the model for the input. */
		modelCreatedExamples: Option[List[Schema.GoogleCloudDatalabelingV1beta1Example]] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1CreateEvaluationJobRequest(
	  /** Required. The evaluation job to create. */
		job: Option[Schema.GoogleCloudDatalabelingV1beta1EvaluationJob] = None
	)
	
	object GoogleCloudDatalabelingV1beta1EvaluationJob {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, SCHEDULED, RUNNING, PAUSED, STOPPED }
	}
	case class GoogleCloudDatalabelingV1beta1EvaluationJob(
	  /** Output only. After you create a job, Data Labeling Service assigns a name to the job with the following format: "projects/{project_id}/evaluationJobs/ {evaluation_job_id}" */
		name: Option[String] = None,
	  /** Required. Description of the job. The description can be up to 25,000 characters long. */
		description: Option[String] = None,
	  /** Output only. Describes the current state of the job. */
		state: Option[Schema.GoogleCloudDatalabelingV1beta1EvaluationJob.StateEnum] = None,
	  /** Required. Describes the interval at which the job runs. This interval must be at least 1 day, and it is rounded to the nearest day. For example, if you specify a 50-hour interval, the job runs every 2 days. You can provide the schedule in [crontab format](/scheduler/docs/configuring/cron-job-schedules) or in an [English-like format](/appengine/docs/standard/python/config/cronref#schedule_format). Regardless of what you specify, the job will run at 10:00 AM UTC. Only the interval from this schedule is used, not the specific time of day. */
		schedule: Option[String] = None,
	  /** Required. The [AI Platform Prediction model version](/ml-engine/docs/prediction-overview) to be evaluated. Prediction input and output is sampled from this model version. When creating an evaluation job, specify the model version in the following format: "projects/{project_id}/models/{model_name}/versions/{version_name}" There can only be one evaluation job per model version. */
		modelVersion: Option[String] = None,
	  /** Required. Configuration details for the evaluation job. */
		evaluationJobConfig: Option[Schema.GoogleCloudDatalabelingV1beta1EvaluationJobConfig] = None,
	  /** Required. Name of the AnnotationSpecSet describing all the labels that your machine learning model outputs. You must create this resource before you create an evaluation job and provide its name in the following format: "projects/{project_id}/annotationSpecSets/{annotation_spec_set_id}" */
		annotationSpecSet: Option[String] = None,
	  /** Required. Whether you want Data Labeling Service to provide ground truth labels for prediction input. If you want the service to assign human labelers to annotate your data, set this to `true`. If you want to provide your own ground truth labels in the evaluation job's BigQuery table, set this to `false`. */
		labelMissingGroundTruth: Option[Boolean] = None,
	  /** Output only. Every time the evaluation job runs and an error occurs, the failed attempt is appended to this array. */
		attempts: Option[List[Schema.GoogleCloudDatalabelingV1beta1Attempt]] = None,
	  /** Output only. Timestamp of when this evaluation job was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1EvaluationJobConfig(
	  /** Specify this field if your model version performs image classification or general classification. `annotationSpecSet` in this configuration must match EvaluationJob.annotationSpecSet. `allowMultiLabel` in this configuration must match `classificationMetadata.isMultiLabel` in input_config. */
		imageClassificationConfig: Option[Schema.GoogleCloudDatalabelingV1beta1ImageClassificationConfig] = None,
	  /** Specify this field if your model version performs image object detection (bounding box detection). `annotationSpecSet` in this configuration must match EvaluationJob.annotationSpecSet. */
		boundingPolyConfig: Option[Schema.GoogleCloudDatalabelingV1beta1BoundingPolyConfig] = None,
	  /** Specify this field if your model version performs text classification. `annotationSpecSet` in this configuration must match EvaluationJob.annotationSpecSet. `allowMultiLabel` in this configuration must match `classificationMetadata.isMultiLabel` in input_config. */
		textClassificationConfig: Option[Schema.GoogleCloudDatalabelingV1beta1TextClassificationConfig] = None,
	  /** Rquired. Details for the sampled prediction input. Within this configuration, there are requirements for several fields: &#42; `dataType` must be one of `IMAGE`, `TEXT`, or `GENERAL_DATA`. &#42; `annotationType` must be one of `IMAGE_CLASSIFICATION_ANNOTATION`, `TEXT_CLASSIFICATION_ANNOTATION`, `GENERAL_CLASSIFICATION_ANNOTATION`, or `IMAGE_BOUNDING_BOX_ANNOTATION` (image object detection). &#42; If your machine learning model performs classification, you must specify `classificationMetadata.isMultiLabel`. &#42; You must specify `bigquerySource` (not `gcsSource`). */
		inputConfig: Option[Schema.GoogleCloudDatalabelingV1beta1InputConfig] = None,
	  /** Required. Details for calculating evaluation metrics and creating Evaulations. If your model version performs image object detection, you must specify the `boundingBoxEvaluationOptions` field within this configuration. Otherwise, provide an empty object for this configuration. */
		evaluationConfig: Option[Schema.GoogleCloudDatalabelingV1beta1EvaluationConfig] = None,
	  /** Optional. Details for human annotation of your data. If you set labelMissingGroundTruth to `true` for this evaluation job, then you must specify this field. If you plan to provide your own ground truth labels, then omit this field. Note that you must create an Instruction resource before you can specify this field. Provide the name of the instruction resource in the `instruction` field within this configuration. */
		humanAnnotationConfig: Option[Schema.GoogleCloudDatalabelingV1beta1HumanAnnotationConfig] = None,
	  /** Required. Prediction keys that tell Data Labeling Service where to find the data for evaluation in your BigQuery table. When the service samples prediction input and output from your model version and saves it to BigQuery, the data gets stored as JSON strings in the BigQuery table. These keys tell Data Labeling Service how to parse the JSON. You can provide the following entries in this field: &#42; `data_json_key`: the data key for prediction input. You must provide either this key or `reference_json_key`. &#42; `reference_json_key`: the data reference key for prediction input. You must provide either this key or `data_json_key`. &#42; `label_json_key`: the label key for prediction output. Required. &#42; `label_score_json_key`: the score key for prediction output. Required. &#42; `bounding_box_json_key`: the bounding box key for prediction output. Required if your model version perform image object detection. Learn [how to configure prediction keys](/ml-engine/docs/continuous-evaluation/create-job#prediction-keys). */
		bigqueryImportKeys: Option[Map[String, String]] = None,
	  /** Required. The maximum number of predictions to sample and save to BigQuery during each evaluation interval. This limit overrides `example_sample_percentage`: even if the service has not sampled enough predictions to fulfill `example_sample_perecentage` during an interval, it stops sampling predictions when it meets this limit. */
		exampleCount: Option[Int] = None,
	  /** Required. Fraction of predictions to sample and save to BigQuery during each evaluation interval. For example, 0.1 means 10% of predictions served by your model version get saved to BigQuery. */
		exampleSamplePercentage: Option[BigDecimal] = None,
	  /** Optional. Configuration details for evaluation job alerts. Specify this field if you want to receive email alerts if the evaluation job finds that your predictions have low mean average precision during a run. */
		evaluationJobAlertConfig: Option[Schema.GoogleCloudDatalabelingV1beta1EvaluationJobAlertConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1EvaluationJobAlertConfig(
	  /** Required. An email address to send alerts to. */
		email: Option[String] = None,
	  /** Required. A number between 0 and 1 that describes a minimum mean average precision threshold. When the evaluation job runs, if it calculates that your model version's predictions from the recent interval have meanAveragePrecision below this threshold, then it sends an alert to your specified email. */
		minAcceptableMeanAveragePrecision: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1Attempt(
		attemptTime: Option[String] = None,
	  /** Details of errors that occurred. */
		partialFailures: Option[List[Schema.GoogleRpcStatus]] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1PauseEvaluationJobRequest(
	
	)
	
	case class GoogleCloudDatalabelingV1beta1ResumeEvaluationJobRequest(
	
	)
	
	case class GoogleCloudDatalabelingV1beta1ListEvaluationJobsResponse(
	  /** The list of evaluation jobs to return. */
		evaluationJobs: Option[List[Schema.GoogleCloudDatalabelingV1beta1EvaluationJob]] = None,
	  /** A token to retrieve next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1FeedbackThread(
	  /** Name of the feedback thread. Format: 'project/{project_id}/datasets/{dataset_id}/annotatedDatasets/{annotated_dataset_id}/feedbackThreads/{feedback_thread_id}' */
		name: Option[String] = None,
	  /** Metadata regarding the feedback thread. */
		feedbackThreadMetadata: Option[Schema.GoogleCloudDatalabelingV1beta1FeedbackThreadMetadata] = None
	)
	
	object GoogleCloudDatalabelingV1beta1FeedbackThreadMetadata {
		enum StatusEnum extends Enum[StatusEnum] { case FEEDBACK_THREAD_STATUS_UNSPECIFIED, NEW, REPLIED }
	}
	case class GoogleCloudDatalabelingV1beta1FeedbackThreadMetadata(
	  /** An image thumbnail of this thread. */
		thumbnail: Option[String] = None,
		status: Option[Schema.GoogleCloudDatalabelingV1beta1FeedbackThreadMetadata.StatusEnum] = None,
	  /** When the thread is created */
		createTime: Option[String] = None,
	  /** When the thread is last updated. */
		lastUpdateTime: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ListFeedbackThreadsResponse(
	  /** The list of feedback threads to return. */
		feedbackThreads: Option[List[Schema.GoogleCloudDatalabelingV1beta1FeedbackThread]] = None,
	  /** A token to retrieve next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1FeedbackMessage(
		requesterFeedbackMetadata: Option[Schema.GoogleCloudDatalabelingV1beta1RequesterFeedbackMetadata] = None,
		operatorFeedbackMetadata: Option[Schema.GoogleCloudDatalabelingV1beta1OperatorFeedbackMetadata] = None,
	  /** Name of the feedback message in a feedback thread. Format: 'project/{project_id}/datasets/{dataset_id}/annotatedDatasets/{annotated_dataset_id}/feedbackThreads/{feedback_thread_id}/feedbackMessage/{feedback_message_id}' */
		name: Option[String] = None,
	  /** String content of the feedback. Maximum of 10000 characters. */
		body: Option[String] = None,
	  /** The image storing this feedback if the feedback is an image representing operator's comments. */
		image: Option[String] = None,
	  /** Create time. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1RequesterFeedbackMetadata(
	
	)
	
	case class GoogleCloudDatalabelingV1beta1OperatorFeedbackMetadata(
	
	)
	
	case class GoogleCloudDatalabelingV1beta1ListFeedbackMessagesResponse(
	  /** The list of feedback messages to return. */
		feedbackMessages: Option[List[Schema.GoogleCloudDatalabelingV1beta1FeedbackMessage]] = None,
	  /** A token to retrieve next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1ImportDataOperationResponse(
	  /** Ouptut only. The name of imported dataset. */
		dataset: Option[String] = None,
	  /** Output only. Total number of examples requested to import */
		totalCount: Option[Int] = None,
	  /** Output only. Number of examples imported successfully. */
		importCount: Option[Int] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1ExportDataOperationResponse(
	  /** Ouptut only. The name of dataset. "projects/&#42;/datasets/&#42;" */
		dataset: Option[String] = None,
	  /** Output only. The name of annotated dataset in format "projects/&#42;/datasets/&#42;/annotatedDatasets/&#42;". */
		annotatedDataset: Option[String] = None,
	  /** Output only. Total number of examples requested to export */
		totalCount: Option[Int] = None,
	  /** Output only. Number of examples exported successfully. */
		exportCount: Option[Int] = None,
	  /** Output only. Statistic infos of labels in the exported dataset. */
		labelStats: Option[Schema.GoogleCloudDatalabelingV1alpha1LabelStats] = None,
	  /** Output only. output_config in the ExportData request. */
		outputConfig: Option[Schema.GoogleCloudDatalabelingV1alpha1OutputConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1LabelStats(
	  /** Map of each annotation spec's example count. Key is the annotation spec name and value is the number of examples for that annotation spec. If the annotated dataset does not have annotation spec, the map will return a pair where the key is empty string and value is the total number of annotations. */
		exampleCount: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1OutputConfig(
	  /** Output to a file in Cloud Storage. Should be used for labeling output other than image segmentation. */
		gcsDestination: Option[Schema.GoogleCloudDatalabelingV1alpha1GcsDestination] = None,
	  /** Output to a folder in Cloud Storage. Should be used for image segmentation or document de-identification labeling outputs. */
		gcsFolderDestination: Option[Schema.GoogleCloudDatalabelingV1alpha1GcsFolderDestination] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1GcsDestination(
	  /** Required. The output uri of destination file. */
		outputUri: Option[String] = None,
	  /** Required. The format of the gcs destination. Only "text/csv" and "application/json" are supported. */
		mimeType: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1GcsFolderDestination(
	  /** Required. Cloud Storage directory to export data to. */
		outputFolderUri: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1ImportDataOperationMetadata(
	  /** Output only. The name of imported dataset. "projects/&#42;/datasets/&#42;" */
		dataset: Option[String] = None,
	  /** Output only. Partial failures encountered. E.g. single files that couldn't be read. Status details field will contain standard GCP error details. */
		partialFailures: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Output only. Timestamp when import dataset request was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1ExportDataOperationMetadata(
	  /** Output only. The name of dataset to be exported. "projects/&#42;/datasets/&#42;" */
		dataset: Option[String] = None,
	  /** Output only. The name of annotated dataset in format "projects/&#42;/datasets/&#42;/annotatedDatasets/&#42;". */
		annotatedDataset: Option[String] = None,
	  /** Output only. Partial failures encountered. E.g. single files that couldn't be read. Status details field will contain standard GCP error details. */
		partialFailures: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Output only. Timestamp when export dataset request was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1CreateInstructionMetadata(
	  /** The name of the created Instruction. projects/{project_id}/instructions/{instruction_id} */
		instruction: Option[String] = None,
	  /** Partial failures encountered. E.g. single files that couldn't be read. Status details field will contain standard GCP error details. */
		partialFailures: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Timestamp when create instruction request was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1LabelOperationMetadata(
	  /** Details of label image classification operation. */
		imageClassificationDetails: Option[Schema.GoogleCloudDatalabelingV1alpha1LabelImageClassificationOperationMetadata] = None,
	  /** Details of label image bounding box operation. */
		imageBoundingBoxDetails: Option[Schema.GoogleCloudDatalabelingV1alpha1LabelImageBoundingBoxOperationMetadata] = None,
	  /** Details of label image bounding poly operation. */
		imageBoundingPolyDetails: Option[Schema.GoogleCloudDatalabelingV1alpha1LabelImageBoundingPolyOperationMetadata] = None,
	  /** Details of label image oriented bounding box operation. */
		imageOrientedBoundingBoxDetails: Option[Schema.GoogleCloudDatalabelingV1alpha1LabelImageOrientedBoundingBoxOperationMetadata] = None,
	  /** Details of label image polyline operation. */
		imagePolylineDetails: Option[Schema.GoogleCloudDatalabelingV1alpha1LabelImagePolylineOperationMetadata] = None,
	  /** Details of label image segmentation operation. */
		imageSegmentationDetails: Option[Schema.GoogleCloudDatalabelingV1alpha1LabelImageSegmentationOperationMetadata] = None,
	  /** Details of label video classification operation. */
		videoClassificationDetails: Option[Schema.GoogleCloudDatalabelingV1alpha1LabelVideoClassificationOperationMetadata] = None,
	  /** Details of label video object detection operation. */
		videoObjectDetectionDetails: Option[Schema.GoogleCloudDatalabelingV1alpha1LabelVideoObjectDetectionOperationMetadata] = None,
	  /** Details of label video object tracking operation. */
		videoObjectTrackingDetails: Option[Schema.GoogleCloudDatalabelingV1alpha1LabelVideoObjectTrackingOperationMetadata] = None,
	  /** Details of label video event operation. */
		videoEventDetails: Option[Schema.GoogleCloudDatalabelingV1alpha1LabelVideoEventOperationMetadata] = None,
	  /** Details of label text classification operation. */
		textClassificationDetails: Option[Schema.GoogleCloudDatalabelingV1alpha1LabelTextClassificationOperationMetadata] = None,
	  /** Details of label text entity extraction operation. */
		textEntityExtractionDetails: Option[Schema.GoogleCloudDatalabelingV1alpha1LabelTextEntityExtractionOperationMetadata] = None,
	  /** Output only. The name of dataset to be labeled. "projects/&#42;/datasets/&#42;" */
		dataset: Option[String] = None,
	  /** Output only. Progress of label operation. Range: [0, 100]. */
		progressPercent: Option[Int] = None,
	  /** Output only. Partial failures encountered. E.g. single files that couldn't be read. Status details field will contain standard GCP error details. */
		partialFailures: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Output only. Timestamp when labeling request was created. */
		createTime: Option[String] = None,
	  /** Output only. The name of annotated dataset in format "projects/&#42;/datasets/&#42;/annotatedDatasets/&#42;". */
		annotatedDataset: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1LabelImageClassificationOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1HumanAnnotationConfig(
	  /** Required. Instruction resource name. */
		instruction: Option[String] = None,
	  /** Required. A human-readable name for AnnotatedDataset defined by users. Maximum of 64 characters . */
		annotatedDatasetDisplayName: Option[String] = None,
	  /** Optional. A human-readable description for AnnotatedDataset. The description can be up to 10000 characters long. */
		annotatedDatasetDescription: Option[String] = None,
	  /** Optional. A human-readable label used to logically group labeling tasks. This string must match the regular expression `[a-zA-Z\\d_-]{0,128}`. */
		labelGroup: Option[String] = None,
	  /** Optional. The Language of this question, as a [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt). Default value is en-US. Only need to set this when task is language related. For example, French text classification. */
		languageCode: Option[String] = None,
	  /** Optional. Replication of questions. Each question will be sent to up to this number of contributors to label. Aggregated answers will be returned. Default is set to 1. For image related labeling, valid values are 1, 3, 5. */
		replicaCount: Option[Int] = None,
	  /** Optional. Maximum duration for contributors to answer a question. Maximum is 3600 seconds. Default is 3600 seconds. */
		questionDuration: Option[String] = None,
	  /** Optional. If you want your own labeling contributors to manage and work on this labeling request, you can set these contributors here. We will give them access to the question types in crowdcompute. Note that these emails must be registered in crowdcompute worker UI: https://crowd-compute.appspot.com/ */
		contributorEmails: Option[List[String]] = None,
	  /** Email of the user who started the labeling task and should be notified by email. If empty no notification will be sent. */
		userEmailAddress: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1LabelImageBoundingBoxOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1LabelImageBoundingPolyOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1LabelImageOrientedBoundingBoxOperationMetadata(
	  /** Basic human annotation config. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1LabelImagePolylineOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1LabelImageSegmentationOperationMetadata(
	  /** Basic human annotation config. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1LabelVideoClassificationOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1LabelVideoObjectDetectionOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1LabelVideoObjectTrackingOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1LabelVideoEventOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1LabelTextClassificationOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1alpha1LabelTextEntityExtractionOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ImportDataOperationResponse(
	  /** Ouptut only. The name of imported dataset. */
		dataset: Option[String] = None,
	  /** Output only. Total number of examples requested to import */
		totalCount: Option[Int] = None,
	  /** Output only. Number of examples imported successfully. */
		importCount: Option[Int] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ExportDataOperationResponse(
	  /** Ouptut only. The name of dataset. "projects/&#42;/datasets/&#42;" */
		dataset: Option[String] = None,
	  /** Output only. The name of annotated dataset in format "projects/&#42;/datasets/&#42;/annotatedDatasets/&#42;". */
		annotatedDataset: Option[String] = None,
	  /** Output only. Total number of examples requested to export */
		totalCount: Option[Int] = None,
	  /** Output only. Number of examples exported successfully. */
		exportCount: Option[Int] = None,
	  /** Output only. Statistic infos of labels in the exported dataset. */
		labelStats: Option[Schema.GoogleCloudDatalabelingV1beta1LabelStats] = None,
	  /** Output only. output_config in the ExportData request. */
		outputConfig: Option[Schema.GoogleCloudDatalabelingV1beta1OutputConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ImportDataOperationMetadata(
	  /** Output only. The name of imported dataset. "projects/&#42;/datasets/&#42;" */
		dataset: Option[String] = None,
	  /** Output only. Partial failures encountered. E.g. single files that couldn't be read. Status details field will contain standard GCP error details. */
		partialFailures: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Output only. Timestamp when import dataset request was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1ExportDataOperationMetadata(
	  /** Output only. The name of dataset to be exported. "projects/&#42;/datasets/&#42;" */
		dataset: Option[String] = None,
	  /** Output only. The name of annotated dataset in format "projects/&#42;/datasets/&#42;/annotatedDatasets/&#42;". */
		annotatedDataset: Option[String] = None,
	  /** Output only. Partial failures encountered. E.g. single files that couldn't be read. Status details field will contain standard GCP error details. */
		partialFailures: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Output only. Timestamp when export dataset request was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1CreateInstructionMetadata(
	  /** The name of the created Instruction. projects/{project_id}/instructions/{instruction_id} */
		instruction: Option[String] = None,
	  /** Partial failures encountered. E.g. single files that couldn't be read. Status details field will contain standard GCP error details. */
		partialFailures: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Timestamp when create instruction request was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1LabelOperationMetadata(
	  /** Details of label image classification operation. */
		imageClassificationDetails: Option[Schema.GoogleCloudDatalabelingV1beta1LabelImageClassificationOperationMetadata] = None,
	  /** Details of label image bounding box operation. */
		imageBoundingBoxDetails: Option[Schema.GoogleCloudDatalabelingV1beta1LabelImageBoundingBoxOperationMetadata] = None,
	  /** Details of label image bounding poly operation. */
		imageBoundingPolyDetails: Option[Schema.GoogleCloudDatalabelingV1beta1LabelImageBoundingPolyOperationMetadata] = None,
	  /** Details of label image oriented bounding box operation. */
		imageOrientedBoundingBoxDetails: Option[Schema.GoogleCloudDatalabelingV1beta1LabelImageOrientedBoundingBoxOperationMetadata] = None,
	  /** Details of label image polyline operation. */
		imagePolylineDetails: Option[Schema.GoogleCloudDatalabelingV1beta1LabelImagePolylineOperationMetadata] = None,
	  /** Details of label image segmentation operation. */
		imageSegmentationDetails: Option[Schema.GoogleCloudDatalabelingV1beta1LabelImageSegmentationOperationMetadata] = None,
	  /** Details of label video classification operation. */
		videoClassificationDetails: Option[Schema.GoogleCloudDatalabelingV1beta1LabelVideoClassificationOperationMetadata] = None,
	  /** Details of label video object detection operation. */
		videoObjectDetectionDetails: Option[Schema.GoogleCloudDatalabelingV1beta1LabelVideoObjectDetectionOperationMetadata] = None,
	  /** Details of label video object tracking operation. */
		videoObjectTrackingDetails: Option[Schema.GoogleCloudDatalabelingV1beta1LabelVideoObjectTrackingOperationMetadata] = None,
	  /** Details of label video event operation. */
		videoEventDetails: Option[Schema.GoogleCloudDatalabelingV1beta1LabelVideoEventOperationMetadata] = None,
	  /** Details of label text classification operation. */
		textClassificationDetails: Option[Schema.GoogleCloudDatalabelingV1beta1LabelTextClassificationOperationMetadata] = None,
	  /** Details of label text entity extraction operation. */
		textEntityExtractionDetails: Option[Schema.GoogleCloudDatalabelingV1beta1LabelTextEntityExtractionOperationMetadata] = None,
	  /** Output only. The name of dataset to be labeled. "projects/&#42;/datasets/&#42;" */
		dataset: Option[String] = None,
	  /** Output only. Progress of label operation. Range: [0, 100]. */
		progressPercent: Option[Int] = None,
	  /** Output only. Partial failures encountered. E.g. single files that couldn't be read. Status details field will contain standard GCP error details. */
		partialFailures: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Output only. Timestamp when labeling request was created. */
		createTime: Option[String] = None,
	  /** Output only. The name of annotated dataset in format "projects/&#42;/datasets/&#42;/annotatedDatasets/&#42;". */
		annotatedDataset: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1LabelImageClassificationOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1beta1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1LabelImageBoundingBoxOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1beta1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1LabelImageBoundingPolyOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1beta1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1LabelImageOrientedBoundingBoxOperationMetadata(
	  /** Basic human annotation config. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1beta1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1LabelImagePolylineOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1beta1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1LabelImageSegmentationOperationMetadata(
	  /** Basic human annotation config. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1beta1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1LabelVideoClassificationOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1beta1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1LabelVideoObjectDetectionOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1beta1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1LabelVideoObjectTrackingOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1beta1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1LabelVideoEventOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1beta1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1LabelTextClassificationOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1beta1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1beta1LabelTextEntityExtractionOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1beta1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1ImportDataOperationResponse(
	  /** Ouptut only. The name of imported dataset. */
		dataset: Option[String] = None,
	  /** Output only. Total number of examples requested to import */
		totalCount: Option[Int] = None,
	  /** Output only. Number of examples imported successfully. */
		importCount: Option[Int] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1ExportDataOperationResponse(
	  /** Ouptut only. The name of dataset. "projects/&#42;/datasets/&#42;" */
		dataset: Option[String] = None,
	  /** Output only. The name of annotated dataset in format "projects/&#42;/datasets/&#42;/annotatedDatasets/&#42;". */
		annotatedDataset: Option[String] = None,
	  /** Output only. Total number of examples requested to export */
		totalCount: Option[Int] = None,
	  /** Output only. Number of examples exported successfully. */
		exportCount: Option[Int] = None,
	  /** Output only. Statistic infos of labels in the exported dataset. */
		labelStats: Option[Schema.GoogleCloudDatalabelingV1p1alpha1LabelStats] = None,
	  /** Output only. output_config in the ExportData request. */
		outputConfig: Option[Schema.GoogleCloudDatalabelingV1p1alpha1OutputConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1LabelStats(
	  /** Map of each annotation spec's example count. Key is the annotation spec name and value is the number of examples for that annotation spec. If the annotated dataset does not have annotation spec, the map will return a pair where the key is empty string and value is the total number of annotations. */
		exampleCount: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1OutputConfig(
	  /** Output to a file in Cloud Storage. Should be used for labeling output other than image segmentation. */
		gcsDestination: Option[Schema.GoogleCloudDatalabelingV1p1alpha1GcsDestination] = None,
	  /** Output to a folder in Cloud Storage. Should be used for image segmentation or document de-identification labeling outputs. */
		gcsFolderDestination: Option[Schema.GoogleCloudDatalabelingV1p1alpha1GcsFolderDestination] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1GcsDestination(
	  /** Required. The output uri of destination file. */
		outputUri: Option[String] = None,
	  /** Required. The format of the gcs destination. Only "text/csv" and "application/json" are supported. */
		mimeType: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1GcsFolderDestination(
	  /** Required. Cloud Storage directory to export data to. */
		outputFolderUri: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1ImportDataOperationMetadata(
	  /** Output only. The name of imported dataset. "projects/&#42;/datasets/&#42;" */
		dataset: Option[String] = None,
	  /** Output only. Partial failures encountered. E.g. single files that couldn't be read. Status details field will contain standard GCP error details. */
		partialFailures: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Output only. Timestamp when import dataset request was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1ExportDataOperationMetadata(
	  /** Output only. The name of dataset to be exported. "projects/&#42;/datasets/&#42;" */
		dataset: Option[String] = None,
	  /** Output only. The name of annotated dataset in format "projects/&#42;/datasets/&#42;/annotatedDatasets/&#42;". */
		annotatedDataset: Option[String] = None,
	  /** Output only. Partial failures encountered. E.g. single files that couldn't be read. Status details field will contain standard GCP error details. */
		partialFailures: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Output only. Timestamp when export dataset request was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1CreateInstructionMetadata(
	  /** The name of the created Instruction. projects/{project_id}/instructions/{instruction_id} */
		instruction: Option[String] = None,
	  /** Partial failures encountered. E.g. single files that couldn't be read. Status details field will contain standard GCP error details. */
		partialFailures: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Timestamp when create instruction request was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1LabelOperationMetadata(
	  /** Details of label image classification operation. */
		imageClassificationDetails: Option[Schema.GoogleCloudDatalabelingV1p1alpha1LabelImageClassificationOperationMetadata] = None,
	  /** Details of label image bounding box operation. */
		imageBoundingBoxDetails: Option[Schema.GoogleCloudDatalabelingV1p1alpha1LabelImageBoundingBoxOperationMetadata] = None,
	  /** Details of label image bounding poly operation. */
		imageBoundingPolyDetails: Option[Schema.GoogleCloudDatalabelingV1p1alpha1LabelImageBoundingPolyOperationMetadata] = None,
	  /** Details of label image oriented bounding box operation. */
		imageOrientedBoundingBoxDetails: Option[Schema.GoogleCloudDatalabelingV1p1alpha1LabelImageOrientedBoundingBoxOperationMetadata] = None,
	  /** Details of label image polyline operation. */
		imagePolylineDetails: Option[Schema.GoogleCloudDatalabelingV1p1alpha1LabelImagePolylineOperationMetadata] = None,
	  /** Details of label image segmentation operation. */
		imageSegmentationDetails: Option[Schema.GoogleCloudDatalabelingV1p1alpha1LabelImageSegmentationOperationMetadata] = None,
	  /** Details of label video classification operation. */
		videoClassificationDetails: Option[Schema.GoogleCloudDatalabelingV1p1alpha1LabelVideoClassificationOperationMetadata] = None,
	  /** Details of label video object detection operation. */
		videoObjectDetectionDetails: Option[Schema.GoogleCloudDatalabelingV1p1alpha1LabelVideoObjectDetectionOperationMetadata] = None,
	  /** Details of label video object tracking operation. */
		videoObjectTrackingDetails: Option[Schema.GoogleCloudDatalabelingV1p1alpha1LabelVideoObjectTrackingOperationMetadata] = None,
	  /** Details of label video event operation. */
		videoEventDetails: Option[Schema.GoogleCloudDatalabelingV1p1alpha1LabelVideoEventOperationMetadata] = None,
	  /** Details of label text classification operation. */
		textClassificationDetails: Option[Schema.GoogleCloudDatalabelingV1p1alpha1LabelTextClassificationOperationMetadata] = None,
	  /** Details of label text entity extraction operation. */
		textEntityExtractionDetails: Option[Schema.GoogleCloudDatalabelingV1p1alpha1LabelTextEntityExtractionOperationMetadata] = None,
	  /** Output only. The name of dataset to be labeled. "projects/&#42;/datasets/&#42;" */
		dataset: Option[String] = None,
	  /** Output only. Progress of label operation. Range: [0, 100]. */
		progressPercent: Option[Int] = None,
	  /** Output only. Partial failures encountered. E.g. single files that couldn't be read. Status details field will contain standard GCP error details. */
		partialFailures: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Output only. Timestamp when labeling request was created. */
		createTime: Option[String] = None,
	  /** Output only. The name of annotated dataset in format "projects/&#42;/datasets/&#42;/annotatedDatasets/&#42;". */
		annotatedDataset: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1LabelImageClassificationOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1HumanAnnotationConfig(
	  /** Required. Instruction resource name. */
		instruction: Option[String] = None,
	  /** Required. A human-readable name for AnnotatedDataset defined by users. Maximum of 64 characters . */
		annotatedDatasetDisplayName: Option[String] = None,
	  /** Optional. A human-readable description for AnnotatedDataset. The description can be up to 10000 characters long. */
		annotatedDatasetDescription: Option[String] = None,
	  /** Optional. A human-readable label used to logically group labeling tasks. This string must match the regular expression `[a-zA-Z\\d_-]{0,128}`. */
		labelGroup: Option[String] = None,
	  /** Optional. The Language of this question, as a [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt). Default value is en-US. Only need to set this when task is language related. For example, French text classification. */
		languageCode: Option[String] = None,
	  /** Optional. Replication of questions. Each question will be sent to up to this number of contributors to label. Aggregated answers will be returned. Default is set to 1. For image related labeling, valid values are 1, 3, 5. */
		replicaCount: Option[Int] = None,
	  /** Optional. Maximum duration for contributors to answer a question. Maximum is 3600 seconds. Default is 3600 seconds. */
		questionDuration: Option[String] = None,
	  /** Optional. If you want your own labeling contributors to manage and work on this labeling request, you can set these contributors here. We will give them access to the question types in crowdcompute. Note that these emails must be registered in crowdcompute worker UI: https://crowd-compute.appspot.com/ */
		contributorEmails: Option[List[String]] = None,
	  /** Email of the user who started the labeling task and should be notified by email. If empty no notification will be sent. */
		userEmailAddress: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1LabelImageBoundingBoxOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1LabelImageBoundingPolyOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1LabelImageOrientedBoundingBoxOperationMetadata(
	  /** Basic human annotation config. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1LabelImagePolylineOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1LabelImageSegmentationOperationMetadata(
	  /** Basic human annotation config. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1LabelVideoClassificationOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1LabelVideoObjectDetectionOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1LabelVideoObjectTrackingOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1LabelVideoEventOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1LabelTextClassificationOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1LabelTextEntityExtractionOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p1alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p1alpha1GenerateAnalysisReportOperationMetadata(
	  /** The name of the dataset for which the analysis report is generated. Format: "projects/&#42;/datasets/&#42;" */
		dataset: Option[String] = None,
	  /** Timestamp when generate report request was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1ImportDataOperationResponse(
	  /** Ouptut only. The name of imported dataset. */
		dataset: Option[String] = None,
	  /** Output only. Total number of examples requested to import */
		totalCount: Option[Int] = None,
	  /** Output only. Number of examples imported successfully. */
		importCount: Option[Int] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1ExportDataOperationResponse(
	  /** Ouptut only. The name of dataset. "projects/&#42;/datasets/&#42;" */
		dataset: Option[String] = None,
	  /** Output only. The name of annotated dataset in format "projects/&#42;/datasets/&#42;/annotatedDatasets/&#42;". */
		annotatedDataset: Option[String] = None,
	  /** Output only. Total number of examples requested to export */
		totalCount: Option[Int] = None,
	  /** Output only. Number of examples exported successfully. */
		exportCount: Option[Int] = None,
	  /** Output only. Statistic infos of labels in the exported dataset. */
		labelStats: Option[Schema.GoogleCloudDatalabelingV1p2alpha1LabelStats] = None,
	  /** Output only. output_config in the ExportData request. */
		outputConfig: Option[Schema.GoogleCloudDatalabelingV1p2alpha1OutputConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1LabelStats(
	  /** Map of each annotation spec's example count. Key is the annotation spec name and value is the number of examples for that annotation spec. If the annotated dataset does not have annotation spec, the map will return a pair where the key is empty string and value is the total number of annotations. */
		exampleCount: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1OutputConfig(
	  /** Output to a file in Cloud Storage. Should be used for labeling output other than image segmentation. */
		gcsDestination: Option[Schema.GoogleCloudDatalabelingV1p2alpha1GcsDestination] = None,
	  /** Output to a folder in Cloud Storage. Should be used for image segmentation or document de-identification labeling outputs. */
		gcsFolderDestination: Option[Schema.GoogleCloudDatalabelingV1p2alpha1GcsFolderDestination] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1GcsDestination(
	  /** Required. The output uri of destination file. */
		outputUri: Option[String] = None,
	  /** Required. The format of the gcs destination. Only "text/csv" and "application/json" are supported. */
		mimeType: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1GcsFolderDestination(
	  /** Required. Cloud Storage directory to export data to. */
		outputFolderUri: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1ImportDataOperationMetadata(
	  /** Output only. The name of imported dataset. "projects/&#42;/datasets/&#42;" */
		dataset: Option[String] = None,
	  /** Output only. Partial failures encountered. E.g. single files that couldn't be read. Status details field will contain standard GCP error details. */
		partialFailures: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Output only. Timestamp when import dataset request was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1ExportDataOperationMetadata(
	  /** Output only. The name of dataset to be exported. "projects/&#42;/datasets/&#42;" */
		dataset: Option[String] = None,
	  /** Output only. The name of annotated dataset in format "projects/&#42;/datasets/&#42;/annotatedDatasets/&#42;". */
		annotatedDataset: Option[String] = None,
	  /** Output only. Partial failures encountered. E.g. single files that couldn't be read. Status details field will contain standard GCP error details. */
		partialFailures: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Output only. Timestamp when export dataset request was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1CreateInstructionMetadata(
	  /** The name of the created Instruction. projects/{project_id}/instructions/{instruction_id} */
		instruction: Option[String] = None,
	  /** Partial failures encountered. E.g. single files that couldn't be read. Status details field will contain standard GCP error details. */
		partialFailures: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Timestamp when create instruction request was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1LabelOperationMetadata(
	  /** Details of label image classification operation. */
		imageClassificationDetails: Option[Schema.GoogleCloudDatalabelingV1p2alpha1LabelImageClassificationOperationMetadata] = None,
	  /** Details of label image bounding box operation. */
		imageBoundingBoxDetails: Option[Schema.GoogleCloudDatalabelingV1p2alpha1LabelImageBoundingBoxOperationMetadata] = None,
	  /** Details of label image bounding poly operation. */
		imageBoundingPolyDetails: Option[Schema.GoogleCloudDatalabelingV1p2alpha1LabelImageBoundingPolyOperationMetadata] = None,
	  /** Details of label image oriented bounding box operation. */
		imageOrientedBoundingBoxDetails: Option[Schema.GoogleCloudDatalabelingV1p2alpha1LabelImageOrientedBoundingBoxOperationMetadata] = None,
	  /** Details of label image polyline operation. */
		imagePolylineDetails: Option[Schema.GoogleCloudDatalabelingV1p2alpha1LabelImagePolylineOperationMetadata] = None,
	  /** Details of label image segmentation operation. */
		imageSegmentationDetails: Option[Schema.GoogleCloudDatalabelingV1p2alpha1LabelImageSegmentationOperationMetadata] = None,
	  /** Details of label video classification operation. */
		videoClassificationDetails: Option[Schema.GoogleCloudDatalabelingV1p2alpha1LabelVideoClassificationOperationMetadata] = None,
	  /** Details of label video object detection operation. */
		videoObjectDetectionDetails: Option[Schema.GoogleCloudDatalabelingV1p2alpha1LabelVideoObjectDetectionOperationMetadata] = None,
	  /** Details of label video object tracking operation. */
		videoObjectTrackingDetails: Option[Schema.GoogleCloudDatalabelingV1p2alpha1LabelVideoObjectTrackingOperationMetadata] = None,
	  /** Details of label video event operation. */
		videoEventDetails: Option[Schema.GoogleCloudDatalabelingV1p2alpha1LabelVideoEventOperationMetadata] = None,
	  /** Details of label text classification operation. */
		textClassificationDetails: Option[Schema.GoogleCloudDatalabelingV1p2alpha1LabelTextClassificationOperationMetadata] = None,
	  /** Details of label text entity extraction operation. */
		textEntityExtractionDetails: Option[Schema.GoogleCloudDatalabelingV1p2alpha1LabelTextEntityExtractionOperationMetadata] = None,
	  /** Output only. The name of dataset to be labeled. "projects/&#42;/datasets/&#42;" */
		dataset: Option[String] = None,
	  /** Output only. Progress of label operation. Range: [0, 100]. */
		progressPercent: Option[Int] = None,
	  /** Output only. Partial failures encountered. E.g. single files that couldn't be read. Status details field will contain standard GCP error details. */
		partialFailures: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Output only. Timestamp when labeling request was created. */
		createTime: Option[String] = None,
	  /** Output only. The name of annotated dataset in format "projects/&#42;/datasets/&#42;/annotatedDatasets/&#42;". */
		annotatedDataset: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1LabelImageClassificationOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p2alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1HumanAnnotationConfig(
	  /** Required. Instruction resource name. */
		instruction: Option[String] = None,
	  /** Required. A human-readable name for AnnotatedDataset defined by users. Maximum of 64 characters . */
		annotatedDatasetDisplayName: Option[String] = None,
	  /** Optional. A human-readable description for AnnotatedDataset. The description can be up to 10000 characters long. */
		annotatedDatasetDescription: Option[String] = None,
	  /** Optional. A human-readable label used to logically group labeling tasks. This string must match the regular expression `[a-zA-Z\\d_-]{0,128}`. */
		labelGroup: Option[String] = None,
	  /** Optional. The Language of this question, as a [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt). Default value is en-US. Only need to set this when task is language related. For example, French text classification. */
		languageCode: Option[String] = None,
	  /** Optional. Replication of questions. Each question will be sent to up to this number of contributors to label. Aggregated answers will be returned. Default is set to 1. For image related labeling, valid values are 1, 3, 5. */
		replicaCount: Option[Int] = None,
	  /** Optional. Maximum duration for contributors to answer a question. Maximum is 3600 seconds. Default is 3600 seconds. */
		questionDuration: Option[String] = None,
	  /** Optional. If you want your own labeling contributors to manage and work on this labeling request, you can set these contributors here. We will give them access to the question types in crowdcompute. Note that these emails must be registered in crowdcompute worker UI: https://crowd-compute.appspot.com/ */
		contributorEmails: Option[List[String]] = None,
	  /** Email of the user who started the labeling task and should be notified by email. If empty no notification will be sent. */
		userEmailAddress: Option[String] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1LabelImageBoundingBoxOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p2alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1LabelImageBoundingPolyOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p2alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1LabelImageOrientedBoundingBoxOperationMetadata(
	  /** Basic human annotation config. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p2alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1LabelImagePolylineOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p2alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1LabelImageSegmentationOperationMetadata(
	  /** Basic human annotation config. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p2alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1LabelVideoClassificationOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p2alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1LabelVideoObjectDetectionOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p2alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1LabelVideoObjectTrackingOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p2alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1LabelVideoEventOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p2alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1LabelTextClassificationOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p2alpha1HumanAnnotationConfig] = None
	)
	
	case class GoogleCloudDatalabelingV1p2alpha1LabelTextEntityExtractionOperationMetadata(
	  /** Basic human annotation config used in labeling request. */
		basicConfig: Option[Schema.GoogleCloudDatalabelingV1p2alpha1HumanAnnotationConfig] = None
	)
}
