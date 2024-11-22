package com.boresjo.play.api.google.language

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object Entity {
		enum TypeEnum extends Enum[TypeEnum] { case UNKNOWN, PERSON, LOCATION, ORGANIZATION, EVENT, WORK_OF_ART, CONSUMER_GOOD, OTHER, PHONE_NUMBER, ADDRESS, DATE, NUMBER, PRICE }
	}
	case class Entity(
	  /** The representative name for the entity. */
		name: Option[String] = None,
	  /** Metadata associated with the entity. For the metadata associated with other entity types, see the Type table below. */
		metadata: Option[Map[String, String]] = None,
	  /** The mentions of this entity in the input document. The API currently supports proper noun mentions. */
		mentions: Option[List[Schema.EntityMention]] = None,
	  /** For calls to AnalyzeEntitySentiment this field will contain the aggregate sentiment expressed for this entity in the provided document. */
		sentiment: Option[Schema.Sentiment] = None,
	  /** The entity type. */
		`type`: Option[Schema.Entity.TypeEnum] = None
	)
	
	object Document {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, PLAIN_TEXT, HTML }
	}
	case class Document(
	  /** Required. If the type is not set or is `TYPE_UNSPECIFIED`, returns an `INVALID_ARGUMENT` error. */
		`type`: Option[Schema.Document.TypeEnum] = None,
	  /** The Google Cloud Storage URI where the file content is located. This URI must be of the form: gs://bucket_name/object_name. For more details, see https://cloud.google.com/storage/docs/reference-uris. NOTE: Cloud Storage object versioning is not supported. */
		gcsContentUri: Option[String] = None,
	  /** Optional. The language of the document (if not specified, the language is automatically detected). Both ISO and BCP-47 language codes are accepted. [Language Support](https://cloud.google.com/natural-language/docs/languages) lists currently supported languages for each API method. If the language (either specified by the caller or automatically detected) is not supported by the called API method, an `INVALID_ARGUMENT` error is returned. */
		languageCode: Option[String] = None,
	  /** The content of the input in string format. Cloud audit logging exempt since it is based on user data. */
		content: Option[String] = None
	)
	
	case class XPSRegressionEvaluationMetrics(
	  /** R squared. */
		rSquared: Option[BigDecimal] = None,
	  /** Mean absolute percentage error. Only set if all ground truth values are positive. */
		meanAbsolutePercentageError: Option[BigDecimal] = None,
	  /** Root Mean Squared Error (RMSE). */
		rootMeanSquaredError: Option[BigDecimal] = None,
	  /** A list of actual versus predicted points for the model being evaluated. */
		regressionMetricsEntries: Option[List[Schema.XPSRegressionMetricsEntry]] = None,
	  /** Mean Absolute Error (MAE). */
		meanAbsoluteError: Option[BigDecimal] = None,
	  /** Root mean squared log error. */
		rootMeanSquaredLogError: Option[BigDecimal] = None
	)
	
	case class XPSVisionTrainingOperationMetadata(
	  /** Aggregated infra usage within certain time period, for billing report purpose if XAI is enable in training request. */
		explanationUsage: Option[Schema.InfraUsage] = None
	)
	
	case class XPSVideoTrainingOperationMetadata(
	  /** This is an estimation of the node hours necessary for training a model, expressed in milli node hours (i.e. 1,000 value in this field means 1 node hour). A node hour represents the time a virtual machine spends running your training job. The cost of one node running for one hour is a node hour. */
		trainCostMilliNodeHour: Option[String] = None
	)
	
	case class XPSCategoryStatsSingleCategoryStats(
	  /** The number of occurrences of this value in the series. */
		count: Option[String] = None,
	  /** The CATEGORY value. */
		value: Option[String] = None
	)
	
	case class XPSTextToSpeechTrainResponse(
	
	)
	
	case class XPSResponseExplanationMetadataOutputMetadata(
	  /** Name of the output tensor. Only needed in train response. */
		outputTensorName: Option[String] = None
	)
	
	case class XPSTablesModelColumnInfo(
	  /** When given as part of a Model: Measurement of how much model predictions correctness on the TEST data depend on values in this column. A value between 0 and 1, higher means higher influence. These values are normalized - for all input feature columns of a given model they add to 1. When given back by Predict or Batch Predict: Measurement of how impactful for the prediction returned for the given row the value in this column was. Specifically, the feature importance specifies the marginal contribution that the feature made to the prediction score compared to the baseline score. These values are computed using the Sampled Shapley method. */
		featureImportance: Option[BigDecimal] = None,
	  /** The ID of the column. */
		columnId: Option[Int] = None
	)
	
	case class Color(
	  /** The fraction of this color that should be applied to the pixel. That is, the final pixel color is defined by the equation: `pixel color = alpha &#42; (this color) + (1.0 - alpha) &#42; (background color)` This means that a value of 1.0 corresponds to a solid color, whereas a value of 0.0 corresponds to a completely transparent color. This uses a wrapper message rather than a simple float scalar so that it is possible to distinguish between a default value and the value being unset. If omitted, this color object is rendered as a solid color (as if the alpha value had been explicitly given a value of 1.0). */
		alpha: Option[BigDecimal] = None,
	  /** The amount of red in the color as a value in the interval [0, 1]. */
		red: Option[BigDecimal] = None,
	  /** The amount of green in the color as a value in the interval [0, 1]. */
		green: Option[BigDecimal] = None,
	  /** The amount of blue in the color as a value in the interval [0, 1]. */
		blue: Option[BigDecimal] = None
	)
	
	case class XPSVideoActionMetricsEntry(
	  /** Metrics for each label-match confidence_threshold from 0.05,0.10,...,0.95,0.96,0.97,0.98,0.99. */
		confidenceMetricsEntries: Option[List[Schema.XPSVideoActionMetricsEntryConfidenceMetricsEntry]] = None,
	  /** This VideoActionMetricsEntry is calculated based on this prediction window length. If the predicted action's timestamp is inside the time window whose center is the ground truth action's timestamp with this specific length, the prediction result is treated as a true positive. */
		precisionWindowLength: Option[String] = None,
	  /** The mean average precision. */
		meanAveragePrecision: Option[BigDecimal] = None
	)
	
	case class XPSReportingMetrics(
	  /** The effective time training used. If set, this is used for quota management and billing. Deprecated. AutoML BE doesn't use this. Don't set. */
		effectiveTrainingDuration: Option[String] = None,
	  /** One entry per metric name. The values must be aggregated per metric name. */
		metricEntries: Option[List[Schema.XPSMetricEntry]] = None
	)
	
	object XPSDockerFormat {
		enum CpuArchitectureEnum extends Enum[CpuArchitectureEnum] { case CPU_ARCHITECTURE_UNSPECIFIED, CPU_ARCHITECTURE_X86_64 }
		enum GpuArchitectureEnum extends Enum[GpuArchitectureEnum] { case GPU_ARCHITECTURE_UNSPECIFIED, GPU_ARCHITECTURE_NVIDIA }
	}
	case class XPSDockerFormat(
	  /** Optional. Additional cpu information describing the requirements for the to be exported model files. */
		cpuArchitecture: Option[Schema.XPSDockerFormat.CpuArchitectureEnum] = None,
	  /** Optional. Additional gpu information describing the requirements for the to be exported model files. */
		gpuArchitecture: Option[Schema.XPSDockerFormat.GpuArchitectureEnum] = None
	)
	
	case class XPSVideoObjectTrackingEvaluationMetrics(
	  /** Output only. The single metric for bounding boxes evaluation: the mean_average_precision averaged over all bounding_box_metrics_entries. */
		boundingBoxMeanAveragePrecision: Option[BigDecimal] = None,
	  /** Output only. The bounding boxes match metrics for each Intersection-over-union threshold 0.05,0.10,...,0.95,0.96,0.97,0.98,0.99. */
		boundingBoxMetricsEntries: Option[List[Schema.XPSBoundingBoxMetricsEntry]] = None,
	  /** Output only. The single metric for tracking consistency evaluation: the mean_mismatch_rate averaged over all track_metrics_entries. */
		trackMeanMismatchRate: Option[BigDecimal] = None,
	  /** Output only. The tracks match metrics for each Intersection-over-union threshold 0.05,0.10,...,0.95,0.96,0.97,0.98,0.99. */
		trackMetricsEntries: Option[List[Schema.XPSTrackMetricsEntry]] = None,
	  /** The number of video frames used for model evaluation. */
		evaluatedFrameCount: Option[Int] = None,
	  /** The number of bounding boxes used for model evaluation. */
		evaluatedBoundingboxCount: Option[Int] = None,
	  /** Output only. The single metric for tracks bounding box iou evaluation: the mean_bounding_box_iou averaged over all track_metrics_entries. */
		trackMeanBoundingBoxIou: Option[BigDecimal] = None,
	  /** The number of tracks used for model evaluation. */
		evaluatedTrackCount: Option[Int] = None,
	  /** Output only. The single metric for tracks accuracy evaluation: the mean_average_precision averaged over all track_metrics_entries. */
		trackMeanAveragePrecision: Option[BigDecimal] = None
	)
	
	case class XPSColorMapIntColor(
	  /** The value should be in range of [0, 255]. */
		green: Option[Int] = None,
	  /** The value should be in range of [0, 255]. */
		blue: Option[Int] = None,
	  /** The value should be in range of [0, 255]. */
		red: Option[Int] = None
	)
	
	case class XPSTrackMetricsEntryConfidenceMetricsEntry(
	  /** Output only. The confidence threshold value used to compute the metrics. */
		confidenceThreshold: Option[BigDecimal] = None,
	  /** Output only. Bounding box intersection-over-union precision. Measures how well the bounding boxes overlap between each other (e.g. complete overlap or just barely above iou_threshold). */
		boundingBoxIou: Option[BigDecimal] = None,
	  /** Output only. Mismatch rate, which measures the tracking consistency, i.e. correctness of instance ID continuity. */
		mismatchRate: Option[BigDecimal] = None,
	  /** Output only. Tracking precision. */
		trackingPrecision: Option[BigDecimal] = None,
	  /** Output only. Tracking recall. */
		trackingRecall: Option[BigDecimal] = None
	)
	
	case class XPSImageModelServingSpec(
	  /** Populate under uCAIP request scope. */
		modelThroughputEstimation: Option[List[Schema.XPSImageModelServingSpecModelThroughputEstimation]] = None,
	  /** An estimated value of how much traffic a node can serve. Populated for AutoMl request only. */
		nodeQps: Option[BigDecimal] = None,
	  /** ## The fields below are only populated under uCAIP request scope. https://cloud.google.com/ml-engine/docs/runtime-version-list */
		tfRuntimeVersion: Option[String] = None
	)
	
	case class XPSArrayStats(
		commonStats: Option[Schema.XPSCommonStats] = None,
	  /** Stats of all the values of all arrays, as if they were a single long series of data. The type depends on the element type of the array. */
		memberStats: Option[Schema.XPSDataStats] = None
	)
	
	object XPSColumnSpecForecastingMetadata {
		enum ColumnTypeEnum extends Enum[ColumnTypeEnum] { case COLUMN_TYPE_UNSPECIFIED, KEY, KEY_METADATA, TIME_SERIES_AVAILABLE_PAST_ONLY, TIME_SERIES_AVAILABLE_PAST_AND_FUTURE }
	}
	case class XPSColumnSpecForecastingMetadata(
	  /** The type of the column for FORECASTING model training purposes. */
		columnType: Option[Schema.XPSColumnSpecForecastingMetadata.ColumnTypeEnum] = None
	)
	
	case class XPSSpeechEvaluationMetrics(
	  /** Evaluation metrics for all submodels contained in this model. */
		subModelEvaluationMetrics: Option[List[Schema.XPSSpeechEvaluationMetricsSubModelEvaluationMetric]] = None
	)
	
	object XPSVisualization {
		enum OverlayTypeEnum extends Enum[OverlayTypeEnum] { case OVERLAY_TYPE_UNSPECIFIED, NONE, ORIGINAL, GRAYSCALE, MASK_BLACK }
		enum PolarityEnum extends Enum[PolarityEnum] { case POLARITY_UNSPECIFIED, POSITIVE, NEGATIVE, BOTH }
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, PIXELS, OUTLINES }
		enum ColorMapEnum extends Enum[ColorMapEnum] { case COLOR_MAP_UNSPECIFIED, PINK_GREEN, VIRIDIS, RED, GREEN, RED_GREEN, PINK_WHITE_GREEN }
	}
	case class XPSVisualization(
	  /** How the original image is displayed in the visualization. Adjusting the overlay can help increase visual clarity if the original image makes it difficult to view the visualization. Defaults to NONE. */
		overlayType: Option[Schema.XPSVisualization.OverlayTypeEnum] = None,
	  /** Excludes attributions above the specified percentile from the highlighted areas. Using the clip_percent_upperbound and clip_percent_lowerbound together can be useful for filtering out noise and making it easier to see areas of strong attribution. Defaults to 99.9. */
		clipPercentUpperbound: Option[BigDecimal] = None,
	  /** Excludes attributions below the specified percentile, from the highlighted areas. Defaults to 62. */
		clipPercentLowerbound: Option[BigDecimal] = None,
	  /** Whether to only highlight pixels with positive contributions, negative or both. Defaults to POSITIVE. */
		polarity: Option[Schema.XPSVisualization.PolarityEnum] = None,
	  /** Type of the image visualization. Only applicable to Integrated Gradients attribution. OUTLINES shows regions of attribution, while PIXELS shows per-pixel attribution. Defaults to OUTLINES. */
		`type`: Option[Schema.XPSVisualization.TypeEnum] = None,
	  /** The color scheme used for the highlighted areas. Defaults to PINK_GREEN for Integrated Gradients attribution, which shows positive attributions in green and negative in pink. Defaults to VIRIDIS for XRAI attribution, which highlights the most influential regions in yellow and the least influential in blue. */
		colorMap: Option[Schema.XPSVisualization.ColorMapEnum] = None
	)
	
	case class XPSCoreMlFormat(
	
	)
	
	object AnalyzeSentimentRequest {
		enum EncodingTypeEnum extends Enum[EncodingTypeEnum] { case NONE, UTF8, UTF16, UTF32 }
	}
	case class AnalyzeSentimentRequest(
	  /** The encoding type used by the API to calculate sentence offsets. */
		encodingType: Option[Schema.AnalyzeSentimentRequest.EncodingTypeEnum] = None,
	  /** Required. Input document. */
		document: Option[Schema.Document] = None
	)
	
	case class XPSColumnSpec(
	  /** The data stats of the column. It's outputed in RefreshTablesStats and a required input for Train. */
		dataStats: Option[Schema.XPSDataStats] = None,
	  /** The unique id of the column. When Preprocess, the Tables BE will popuate the order id of the column, which reflects the order of the column inside the table, i.e. 0 means the first column in the table, N-1 means the last column. AutoML BE will persist this order id in Spanner and set the order id here when calling RefreshTablesStats and Train. Note: it's different than the column_spec_id that is generated in AutoML BE. */
		columnId: Option[Int] = None,
	  /** The display name of the column. It's outputed in Preprocess and a required input for RefreshTablesStats and Train. */
		displayName: Option[String] = None,
		forecastingMetadata: Option[Schema.XPSColumnSpecForecastingMetadata] = None,
	  /** It's outputed in RefreshTablesStats, and a required input in Train. */
		topCorrelatedColumns: Option[List[Schema.XPSColumnSpecCorrelatedColumn]] = None,
	  /** The data type of the column. It's outputed in Preprocess rpc and a required input for RefreshTablesStats and Train. */
		dataType: Option[Schema.XPSDataType] = None
	)
	
	case class XPSTextSentimentEvaluationMetrics(
	  /** Output only. Quadratic weighted kappa. Only set for the overall model evaluation, not for evaluation of a single annotation spec. */
		quadraticKappa: Option[BigDecimal] = None,
	  /** Output only. Recall. */
		recall: Option[BigDecimal] = None,
	  /** Output only. Linear weighted kappa. Only set for the overall model evaluation, not for evaluation of a single annotation spec. */
		linearKappa: Option[BigDecimal] = None,
	  /** Output only. Mean absolute error. Only set for the overall model evaluation, not for evaluation of a single annotation spec. */
		meanAbsoluteError: Option[BigDecimal] = None,
	  /** Output only. Confusion matrix of the evaluation. Only set for the overall model evaluation, not for evaluation of a single annotation spec. */
		confusionMatrix: Option[Schema.XPSConfusionMatrix] = None,
	  /** Output only. Precision. */
		precision: Option[BigDecimal] = None,
	  /** Output only. Mean squared error. Only set for the overall model evaluation, not for evaluation of a single annotation spec. */
		meanSquaredError: Option[BigDecimal] = None,
	  /** Output only. The harmonic mean of recall and precision. */
		f1Score: Option[BigDecimal] = None
	)
	
	object XPSImageModelServingSpecModelThroughputEstimation {
		enum ServomaticPartitionTypeEnum extends Enum[ServomaticPartitionTypeEnum] { case PARTITION_TYPE_UNSPECIFIED, PARTITION_ZERO, PARTITION_REDUCED_HOMING, PARTITION_JELLYFISH, PARTITION_CPU, PARTITION_CUSTOM_STORAGE_CPU }
		enum ComputeEngineAcceleratorTypeEnum extends Enum[ComputeEngineAcceleratorTypeEnum] { case UNSPECIFIED, NVIDIA_TESLA_K80, NVIDIA_TESLA_P100, NVIDIA_TESLA_V100, NVIDIA_TESLA_P4, NVIDIA_TESLA_T4, NVIDIA_TESLA_A100, NVIDIA_A100_80GB, NVIDIA_L4, NVIDIA_H100_80GB, NVIDIA_H100_MEGA_80GB, TPU_V2, TPU_V3, TPU_V4_POD, TPU_V5_LITEPOD }
	}
	case class XPSImageModelServingSpecModelThroughputEstimation(
	  /** The approximate qps a deployed node can serve. */
		nodeQps: Option[BigDecimal] = None,
		servomaticPartitionType: Option[Schema.XPSImageModelServingSpecModelThroughputEstimation.ServomaticPartitionTypeEnum] = None,
	  /** Estimated latency. */
		latencyInMilliseconds: Option[BigDecimal] = None,
		computeEngineAcceleratorType: Option[Schema.XPSImageModelServingSpecModelThroughputEstimation.ComputeEngineAcceleratorTypeEnum] = None
	)
	
	case class XPSClassificationEvaluationMetrics(
	  /** Confusion matrix of the evaluation. Only set for MULTICLASS classification problems where number of annotation specs is no more than 10. Only set for model level evaluation, not for evaluation per label. */
		confusionMatrix: Option[Schema.XPSConfusionMatrix] = None,
	  /** The Area under precision recall curve metric. */
		auPrc: Option[BigDecimal] = None,
	  /** The Area under precision recall curve metric based on priors. */
		baseAuPrc: Option[BigDecimal] = None,
	  /** The Area Under Receiver Operating Characteristic curve metric. Micro-averaged for the overall evaluation. */
		auRoc: Option[BigDecimal] = None,
	  /** The Log Loss metric. */
		logLoss: Option[BigDecimal] = None,
	  /** Metrics that have confidence thresholds. Precision-recall curve can be derived from it. */
		confidenceMetricsEntries: Option[List[Schema.XPSConfidenceMetricsEntry]] = None,
	  /** The number of examples used for model evaluation. */
		evaluatedExamplesCount: Option[Int] = None
	)
	
	case class XPSTablesRegressionMetrics(
	  /** Mean absolute error. */
		meanAbsoluteError: Option[BigDecimal] = None,
	  /** A list of actual versus predicted points for the model being evaluated. */
		regressionMetricsEntries: Option[List[Schema.XPSRegressionMetricsEntry]] = None,
	  /** Mean absolute percentage error, only set if all of the target column's values are positive. */
		meanAbsolutePercentageError: Option[BigDecimal] = None,
	  /** R squared. */
		rSquared: Option[BigDecimal] = None,
	  /** Root mean squared log error. */
		rootMeanSquaredLogError: Option[BigDecimal] = None,
	  /** Root mean squared error. */
		rootMeanSquaredError: Option[BigDecimal] = None
	)
	
	case class XPSVideoActionMetricsEntryConfidenceMetricsEntry(
	  /** Output only. Precision for the given confidence threshold. */
		precision: Option[BigDecimal] = None,
	  /** Output only. The confidence threshold value used to compute the metrics. */
		confidenceThreshold: Option[BigDecimal] = None,
	  /** Output only. Recall for the given confidence threshold. */
		recall: Option[BigDecimal] = None,
	  /** Output only. The harmonic mean of recall and precision. */
		f1Score: Option[BigDecimal] = None
	)
	
	object XPSTablesTrainingOperationMetadata {
		enum CreateModelStageEnum extends Enum[CreateModelStageEnum] { case CREATE_MODEL_STAGE_UNSPECIFIED, DATA_PREPROCESSING, TRAINING, EVALUATING, MODEL_POST_PROCESSING }
	}
	case class XPSTablesTrainingOperationMetadata(
	  /** Creating model budget. */
		trainBudgetMilliNodeHours: Option[String] = None,
	  /** Current stage of creating model. */
		createModelStage: Option[Schema.XPSTablesTrainingOperationMetadata.CreateModelStageEnum] = None,
	  /** This field is for training. When the operation is terminated successfully, AutoML Backend post this field to operation metadata in spanner. If the metadata has no trials returned, the training operation is supposed to be a failure. */
		topTrials: Option[List[Schema.XPSTuningTrial]] = None,
	  /** The optimization objective for model. */
		optimizationObjective: Option[String] = None,
	  /** Timestamp when training process starts. */
		trainingStartTime: Option[String] = None,
	  /** This field records the training objective value with respect to time, giving insight into how the model architecture search is performing as training time elapses. */
		trainingObjectivePoints: Option[List[Schema.XPSTrainingObjectivePoint]] = None
	)
	
	object TpuMetric {
		enum TpuTypeEnum extends Enum[TpuTypeEnum] { case UNKNOWN_TPU_TYPE, TPU_V2_POD, TPU_V2, TPU_V3_POD, TPU_V3, TPU_V5_LITEPOD }
	}
	case class TpuMetric(
	  /** Required. Seconds of TPU usage, e.g. 3600. */
		tpuSec: Option[String] = None,
	  /** Required. Type of TPU, e.g. TPU_V2, TPU_V3_POD. */
		tpuType: Option[Schema.TpuMetric.TpuTypeEnum] = None
	)
	
	object XPSSpeechEvaluationMetricsSubModelEvaluationMetric {
		enum BiasingModelTypeEnum extends Enum[BiasingModelTypeEnum] { case BIASING_MODEL_TYPE_UNSPECIFIED, COMMAND_AND_SEARCH, PHONE_CALL, VIDEO, DEFAULT }
	}
	case class XPSSpeechEvaluationMetricsSubModelEvaluationMetric(
		numSubstitutions: Option[Int] = None,
		numDeletions: Option[Int] = None,
	  /** Number of words over which the word error rate was computed. */
		numWords: Option[Int] = None,
	  /** Type of the biasing model. */
		biasingModelType: Option[Schema.XPSSpeechEvaluationMetricsSubModelEvaluationMetric.BiasingModelTypeEnum] = None,
	  /** Number of utterances used in the wer computation. */
		numUtterances: Option[Int] = None,
	  /** Below fields are used for debugging purposes */
		sentenceAccuracy: Option[BigDecimal] = None,
	  /** Word error rate (standard error metric used for speech recognition). */
		wer: Option[BigDecimal] = None,
	  /** If true then it means we have an enhanced version of the biasing models. */
		isEnhancedModel: Option[Boolean] = None,
		numInsertions: Option[Int] = None
	)
	
	case class XPSTablesDatasetMetadata(
	  /** (the column id : its CorrelationStats with target column). */
		targetColumnCorrelations: Option[Map[String, Schema.XPSCorrelationStats]] = None,
	  /** Id of the primary table column that should be used as the weight column. */
		weightColumnId: Option[Int] = None,
	  /** Id of the primary table column that should be used as the training label. */
		targetColumnId: Option[Int] = None,
	  /** Primary table. */
		primaryTableSpec: Option[Schema.XPSTableSpec] = None,
	  /** Id the column to split the table. */
		mlUseColumnId: Option[Int] = None
	)
	
	case class XPSStringStats(
	  /** The statistics of the top 20 unigrams, ordered by StringStats.UnigramStats.count. */
		topUnigramStats: Option[List[Schema.XPSStringStatsUnigramStats]] = None,
		commonStats: Option[Schema.XPSCommonStats] = None
	)
	
	object AnalyzeEntitiesRequest {
		enum EncodingTypeEnum extends Enum[EncodingTypeEnum] { case NONE, UTF8, UTF16, UTF32 }
	}
	case class AnalyzeEntitiesRequest(
	  /** Required. Input document. */
		document: Option[Schema.Document] = None,
	  /** The encoding type used by the API to calculate offsets. */
		encodingType: Option[Schema.AnalyzeEntitiesRequest.EncodingTypeEnum] = None
	)
	
	case class AnalyzeEntitiesResponse(
	  /** The language of the text, which will be the same as the language specified in the request or, if not specified, the automatically-detected language. See Document.language_code field for more details. */
		languageCode: Option[String] = None,
	  /** Whether the language is officially supported. The API may still return a response when the language is not supported, but it is on a best effort basis. */
		languageSupported: Option[Boolean] = None,
	  /** The recognized entities in the input document. */
		entities: Option[List[Schema.Entity]] = None
	)
	
	case class XPSTrainResponse(
	  /** Estimated model size in bytes once deployed. */
		deployedModelSizeBytes: Option[String] = None,
		tablesTrainResp: Option[Schema.XPSTablesTrainResponse] = None,
	  /** The trained model evaluation metrics. This can be optionally returned. */
		evaluationMetricsSet: Option[Schema.XPSEvaluationMetricsSet] = None,
	  /** Will only be needed for uCAIP from Beta. */
		textTrainResp: Option[Schema.XPSTextTrainResponse] = None,
		translationTrainResp: Option[Schema.XPSTranslationTrainResponse] = None,
		textToSpeechTrainResp: Option[Schema.XPSTextToSpeechTrainResponse] = None,
		videoClassificationTrainResp: Option[Schema.XPSVideoClassificationTrainResponse] = None,
	  /** Optional vision model error analysis configuration. The field is set when model error analysis is enabled in the training request. The results of error analysis will be binded together with evaluation results (in the format of AnnotatedExample). */
		errorAnalysisConfigs: Option[List[Schema.XPSVisionErrorAnalysisConfig]] = None,
	  /** VisionExplanationConfig for XAI on test set. Optional for when XAI is enable in training request. */
		explanationConfigs: Option[List[Schema.XPSResponseExplanationSpec]] = None,
		videoActionRecognitionTrainResp: Option[Schema.XPSVideoActionRecognitionTrainResponse] = None,
		imageClassificationTrainResp: Option[Schema.XPSImageClassificationTrainResponse] = None,
	  /** Token that represents the trained model. This is considered immutable and is persisted in AutoML. xPS can put their own proto in the byte string, to e.g. point to the model checkpoints. The token is passed to other xPS APIs to refer to the model. */
		modelToken: Option[String] = None,
		imageObjectDetectionTrainResp: Option[Schema.XPSImageObjectDetectionModelSpec] = None,
	  /** Examples used to evaluate the model (usually the test set), with the predicted annotations. The file_spec should point to recordio file(s) of AnnotatedExample. For each returned example, the example_id_token and annotations predicted by the model must be set. The example payload can and is recommended to be omitted. */
		evaluatedExampleSet: Option[Schema.XPSExampleSet] = None,
		videoObjectTrackingTrainResp: Option[Schema.XPSVideoObjectTrackingTrainResponse] = None,
		speechTrainResp: Option[Schema.XPSSpeechModelSpec] = None,
		imageSegmentationTrainResp: Option[Schema.XPSImageSegmentationTrainResponse] = None
	)
	
	object ModerateTextRequest {
		enum ModelVersionEnum extends Enum[ModelVersionEnum] { case MODEL_VERSION_UNSPECIFIED, MODEL_VERSION_1, MODEL_VERSION_2 }
	}
	case class ModerateTextRequest(
	  /** Required. Input document. */
		document: Option[Schema.Document] = None,
	  /** Optional. The model version to use for ModerateText. */
		modelVersion: Option[Schema.ModerateTextRequest.ModelVersionEnum] = None
	)
	
	case class TextSpan(
	  /** The content of the text span, which is a substring of the document. */
		content: Option[String] = None,
	  /** The API calculates the beginning offset of the content in the original document according to the EncodingType specified in the API request. */
		beginOffset: Option[Int] = None
	)
	
	case class Status(
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None,
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None
	)
	
	case class XPSTextExtractionEvaluationMetrics(
	  /** If the enclosing EvaluationMetrics.label is empty, confidence_metrics_entries is an evaluation of the entire model across all labels. If the enclosing EvaluationMetrics.label is set, confidence_metrics_entries applies to that label. */
		confidenceMetricsEntries: Option[List[Schema.XPSConfidenceMetricsEntry]] = None,
	  /** Values are at the highest F1 score on the precision-recall curve. Only confidence_threshold, recall, precision, and f1_score will be set. */
		bestF1ConfidenceMetrics: Option[Schema.XPSConfidenceMetricsEntry] = None,
	  /** Only recall, precision, and f1_score will be set. */
		perLabelConfidenceMetrics: Option[Map[String, Schema.XPSConfidenceMetricsEntry]] = None,
	  /** Confusion matrix of the model, at the default confidence threshold (0.0). Only set for whole-model evaluation, not for evaluation per label. */
		confusionMatrix: Option[Schema.XPSConfusionMatrix] = None
	)
	
	case class XPSXraiAttribution(
	  /** The number of steps for approximating the path integral. A good value to start is 50 and gradually increase until the sum to diff property is met within the desired error range. Valid range of its value is [1, 100], inclusively. */
		stepCount: Option[Int] = None
	)
	
	case class XPSTfJsFormat(
	
	)
	
	case class XPSTablesModelStructureModelParametersParameter(
	  /** Float type parameter value. */
		floatValue: Option[BigDecimal] = None,
	  /** Integer type parameter value. */
		intValue: Option[String] = None,
	  /** Parameter name. */
		name: Option[String] = None,
	  /** String type parameter value. */
		stringValue: Option[String] = None
	)
	
	case class XPSStringStatsUnigramStats(
	  /** The unigram. */
		value: Option[String] = None,
	  /** The number of occurrences of this unigram in the series. */
		count: Option[String] = None
	)
	
	case class XPSTimestampStatsGranularStats(
	  /** A map from granularity key to example count for that key. E.g. for hour_of_day `13` means 1pm, or for month_of_year `5` means May). */
		buckets: Option[Map[String, String]] = None
	)
	
	object XPSSpeechModelSpecSubModelSpec {
		enum BiasingModelTypeEnum extends Enum[BiasingModelTypeEnum] { case BIASING_MODEL_TYPE_UNSPECIFIED, COMMAND_AND_SEARCH, PHONE_CALL, VIDEO, DEFAULT }
	}
	case class XPSSpeechModelSpecSubModelSpec(
	  /** In S3, Recognition ClientContextId.client_id */
		clientId: Option[String] = None,
	  /** Type of the biasing model. */
		biasingModelType: Option[Schema.XPSSpeechModelSpecSubModelSpec.BiasingModelTypeEnum] = None,
	  /** If true then it means we have an enhanced version of the biasing models. */
		isEnhancedModel: Option[Boolean] = None,
	  /** In S3, Recognition ClientContextId.context_id */
		contextId: Option[String] = None
	)
	
	case class XPSVideoExportModelSpec(
	  /** Contains the model format and internal location of the model files to be exported/downloaded. Use the Google Cloud Storage bucket name which is provided via TrainRequest.gcs_bucket_name to store the model files. */
		exportModelOutputConfig: Option[List[Schema.XPSExportModelOutputConfig]] = None
	)
	
	case class XPSTranslationEvaluationMetrics(
	  /** BLEU score for base model. */
		baseBleuScore: Option[BigDecimal] = None,
	  /** BLEU score. */
		bleuScore: Option[BigDecimal] = None
	)
	
	case class XPSConfidenceMetricsEntry(
	  /** The harmonic mean of recall_at1 and precision_at1. */
		f1ScoreAt1: Option[BigDecimal] = None,
	  /** Recall (true positive rate) for the given confidence threshold. */
		recall: Option[BigDecimal] = None,
	  /** The number of model created labels that do not match a ground truth label. */
		falsePositiveCount: Option[String] = None,
	  /** The precision when only considering the label that has the highest prediction score and not below the confidence threshold for each example. */
		precisionAt1: Option[BigDecimal] = None,
	  /** The harmonic mean of recall and precision. */
		f1Score: Option[BigDecimal] = None,
	  /** The False Positive Rate when only considering the label that has the highest prediction score and not below the confidence threshold for each example. */
		falsePositiveRateAt1: Option[BigDecimal] = None,
	  /** Metrics are computed with an assumption that the model always returns at most this many predictions (ordered by their score, descendingly), but they all still need to meet the confidence_threshold. */
		positionThreshold: Option[Int] = None,
	  /** The recall (true positive rate) when only considering the label that has the highest prediction score and not below the confidence threshold for each example. */
		recallAt1: Option[BigDecimal] = None,
	  /** False Positive Rate for the given confidence threshold. */
		falsePositiveRate: Option[BigDecimal] = None,
	  /** Precision for the given confidence threshold. */
		precision: Option[BigDecimal] = None,
	  /** The number of model created labels that match a ground truth label. */
		truePositiveCount: Option[String] = None,
	  /** The number of labels that were not created by the model, but if they would, they would not match a ground truth label. */
		trueNegativeCount: Option[String] = None,
	  /** Metrics are computed with an assumption that the model never return predictions with score lower than this value. */
		confidenceThreshold: Option[BigDecimal] = None,
	  /** The number of ground truth labels that are not matched by a model created label. */
		falseNegativeCount: Option[String] = None
	)
	
	object XPSVisionErrorAnalysisConfig {
		enum QueryTypeEnum extends Enum[QueryTypeEnum] { case QUERY_TYPE_UNSPECIFIED, QUERY_TYPE_ALL_SIMILAR, QUERY_TYPE_SAME_CLASS_SIMILAR, QUERY_TYPE_SAME_CLASS_DISSIMILAR }
	}
	case class XPSVisionErrorAnalysisConfig(
	  /** The number of query examples in error analysis. */
		exampleCount: Option[Int] = None,
	  /** The query type used in retrieval. The enum values are frozen in the foreseeable future. */
		queryType: Option[Schema.XPSVisionErrorAnalysisConfig.QueryTypeEnum] = None
	)
	
	case class XPSTablesEvaluationMetrics(
	  /** Regression metrics. */
		regressionMetrics: Option[Schema.XPSTablesRegressionMetrics] = None,
	  /** Classification metrics. */
		classificationMetrics: Option[Schema.XPSTablesClassificationMetrics] = None
	)
	
	object XPSTextComponentModel {
		enum SubmodelTypeEnum extends Enum[SubmodelTypeEnum] { case TEXT_MODEL_TYPE_UNSPECIFIED, TEXT_MODEL_TYPE_DEFAULT, TEXT_MODEL_TYPE_META_ARCHITECT, TEXT_MODEL_TYPE_ATC, TEXT_MODEL_TYPE_CLARA2, TEXT_MODEL_TYPE_CHATBASE, TEXT_MODEL_TYPE_SAFT_SPAN_LABELING, TEXT_MODEL_TYPE_TEXT_EXTRACTION, TEXT_MODEL_TYPE_RELATIONSHIP_EXTRACTION, TEXT_MODEL_TYPE_COMPOSITE, TEXT_MODEL_TYPE_ALL_MODELS, TEXT_MODEL_TYPE_BERT, TEXT_MODEL_TYPE_ENC_PALM }
		enum PartitionEnum extends Enum[PartitionEnum] { case PARTITION_TYPE_UNSPECIFIED, PARTITION_ZERO, PARTITION_REDUCED_HOMING, PARTITION_JELLYFISH, PARTITION_CPU, PARTITION_CUSTOM_STORAGE_CPU }
	}
	case class XPSTextComponentModel(
	  /** The Cloud Storage resource path to hold batch prediction model. */
		batchPredictionModelGcsUri: Option[String] = None,
	  /** ## The fields below are only populated under uCAIP request scope. https://cloud.google.com/ml-engine/docs/runtime-version-list */
		tfRuntimeVersion: Option[String] = None,
	  /** The type of trained NL submodel */
		submodelType: Option[Schema.XPSTextComponentModel.SubmodelTypeEnum] = None,
	  /** The servomatic model version number. Populated by uCAIP BE as part of online PredictRequest. */
		versionNumber: Option[String] = None,
	  /** The name of servo model. Populated by uCAIP BE as part of online PredictRequest. */
		servoModelName: Option[String] = None,
	  /** The default model binary file used for serving (e.g. online predict, batch predict) via public Cloud Ai Platform API. */
		servingArtifact: Option[Schema.XPSModelArtifactItem] = None,
	  /** The name of the trained NL submodel. */
		submodelName: Option[String] = None,
	  /** The Cloud Storage resource path to hold online prediction model. */
		onlinePredictionModelGcsUri: Option[String] = None,
	  /** The partition where the model is deployed. Populated by uCAIP BE as part of online PredictRequest. */
		partition: Option[Schema.XPSTextComponentModel.PartitionEnum] = None
	)
	
	case class ClassificationCategory(
	  /** Optional. The classifier's severity of the category. This is only present when the ModerateTextRequest.ModelVersion is set to MODEL_VERSION_2, and the corresponding category has a severity score. */
		severity: Option[BigDecimal] = None,
	  /** The name of the category representing the document. */
		name: Option[String] = None,
	  /** The classifier's confidence of the category. Number represents how certain the classifier is that this category represents the given text. */
		confidence: Option[BigDecimal] = None
	)
	
	case class XPSVideoBatchPredictOperationMetadata(
	  /** All the partial batch prediction results that are completed at the moment. Output examples are sorted by completion time. The order will not be changed. Each output example should be the path of a single RecordIO file of AnnotatedExamples. */
		outputExamples: Option[List[String]] = None
	)
	
	object XPSResponseExplanationMetadataInputMetadata {
		enum ModalityEnum extends Enum[ModalityEnum] { case MODALITY_UNSPECIFIED, NUMERIC, IMAGE, CATEGORICAL }
	}
	case class XPSResponseExplanationMetadataInputMetadata(
	  /** Name of the input tensor for this model. Only needed in train response. */
		inputTensorName: Option[String] = None,
	  /** Visualization configurations for image explanation. */
		visualizationConfig: Option[Schema.XPSVisualization] = None,
	  /** Modality of the feature. Valid values are: numeric, image. Defaults to numeric. */
		modality: Option[Schema.XPSResponseExplanationMetadataInputMetadata.ModalityEnum] = None
	)
	
	case class XPSStructType(
	  /** Unordered map of struct field names to their data types. */
		fields: Option[Map[String, Schema.XPSDataType]] = None
	)
	
	case class XPSTextTrainResponse(
	  /** Component submodels. */
		componentModel: Option[List[Schema.XPSTextComponentModel]] = None
	)
	
	object CpuMetric {
		enum CpuTypeEnum extends Enum[CpuTypeEnum] { case UNKNOWN_CPU_TYPE, A2, A3, C2, C2D, CUSTOM, E2, G2, C3, M2, M1, N1, N2_CUSTOM, N2, N2D }
		enum MachineSpecEnum extends Enum[MachineSpecEnum] { case UNKNOWN_MACHINE_SPEC, N1_STANDARD_2, N1_STANDARD_4, N1_STANDARD_8, N1_STANDARD_16, N1_STANDARD_32, N1_STANDARD_64, N1_STANDARD_96, N1_HIGHMEM_2, N1_HIGHMEM_4, N1_HIGHMEM_8, N1_HIGHMEM_16, N1_HIGHMEM_32, N1_HIGHMEM_64, N1_HIGHMEM_96, N1_HIGHCPU_2, N1_HIGHCPU_4, N1_HIGHCPU_8, N1_HIGHCPU_16, N1_HIGHCPU_32, N1_HIGHCPU_64, N1_HIGHCPU_96, A2_HIGHGPU_1G, A2_HIGHGPU_2G, A2_HIGHGPU_4G, A2_HIGHGPU_8G, A2_MEGAGPU_16G, A2_ULTRAGPU_1G, A2_ULTRAGPU_2G, A2_ULTRAGPU_4G, A2_ULTRAGPU_8G, A3_HIGHGPU_1G, A3_HIGHGPU_2G, A3_HIGHGPU_4G, A3_HIGHGPU_8G, A3_MEGAGPU_8G, E2_STANDARD_2, E2_STANDARD_4, E2_STANDARD_8, E2_STANDARD_16, E2_STANDARD_32, E2_HIGHMEM_2, E2_HIGHMEM_4, E2_HIGHMEM_8, E2_HIGHMEM_16, E2_HIGHCPU_2, E2_HIGHCPU_4, E2_HIGHCPU_8, E2_HIGHCPU_16, E2_HIGHCPU_32, N2_STANDARD_2, N2_STANDARD_4, N2_STANDARD_8, N2_STANDARD_16, N2_STANDARD_32, N2_STANDARD_48, N2_STANDARD_64, N2_STANDARD_80, N2_STANDARD_96, N2_STANDARD_128, N2_HIGHMEM_2, N2_HIGHMEM_4, N2_HIGHMEM_8, N2_HIGHMEM_16, N2_HIGHMEM_32, N2_HIGHMEM_48, N2_HIGHMEM_64, N2_HIGHMEM_80, N2_HIGHMEM_96, N2_HIGHMEM_128, N2_HIGHCPU_2, N2_HIGHCPU_4, N2_HIGHCPU_8, N2_HIGHCPU_16, N2_HIGHCPU_32, N2_HIGHCPU_48, N2_HIGHCPU_64, N2_HIGHCPU_80, N2_HIGHCPU_96, N2D_STANDARD_2, N2D_STANDARD_4, N2D_STANDARD_8, N2D_STANDARD_16, N2D_STANDARD_32, N2D_STANDARD_48, N2D_STANDARD_64, N2D_STANDARD_80, N2D_STANDARD_96, N2D_STANDARD_128, N2D_STANDARD_224, N2D_HIGHMEM_2, N2D_HIGHMEM_4, N2D_HIGHMEM_8, N2D_HIGHMEM_16, N2D_HIGHMEM_32, N2D_HIGHMEM_48, N2D_HIGHMEM_64, N2D_HIGHMEM_80, N2D_HIGHMEM_96, N2D_HIGHCPU_2, N2D_HIGHCPU_4, N2D_HIGHCPU_8, N2D_HIGHCPU_16, N2D_HIGHCPU_32, N2D_HIGHCPU_48, N2D_HIGHCPU_64, N2D_HIGHCPU_80, N2D_HIGHCPU_96, N2D_HIGHCPU_128, N2D_HIGHCPU_224, C2_STANDARD_4, C2_STANDARD_8, C2_STANDARD_16, C2_STANDARD_30, C2_STANDARD_60, C2D_STANDARD_2, C2D_STANDARD_4, C2D_STANDARD_8, C2D_STANDARD_16, C2D_STANDARD_32, C2D_STANDARD_56, C2D_STANDARD_112, C2D_HIGHCPU_2, C2D_HIGHCPU_4, C2D_HIGHCPU_8, C2D_HIGHCPU_16, C2D_HIGHCPU_32, C2D_HIGHCPU_56, C2D_HIGHCPU_112, C2D_HIGHMEM_2, C2D_HIGHMEM_4, C2D_HIGHMEM_8, C2D_HIGHMEM_16, C2D_HIGHMEM_32, C2D_HIGHMEM_56, C2D_HIGHMEM_112, G2_STANDARD_4, G2_STANDARD_8, G2_STANDARD_12, G2_STANDARD_16, G2_STANDARD_24, G2_STANDARD_32, G2_STANDARD_48, G2_STANDARD_96, C3_STANDARD_4, C3_STANDARD_8, C3_STANDARD_22, C3_STANDARD_44, C3_STANDARD_88, C3_STANDARD_176, C3_HIGHCPU_4, C3_HIGHCPU_8, C3_HIGHCPU_22, C3_HIGHCPU_44, C3_HIGHCPU_88, C3_HIGHCPU_176, C3_HIGHMEM_4, C3_HIGHMEM_8, C3_HIGHMEM_22, C3_HIGHMEM_44, C3_HIGHMEM_88, C3_HIGHMEM_176 }
	}
	case class CpuMetric(
	  /** Required. Number of CPU cores. */
		coreNumber: Option[String] = None,
	  /** Required. Total seconds of core usage, e.g. 4. */
		coreSec: Option[String] = None,
	  /** Billing tracking labels. They do not contain any user data but only the labels set by Vertex Core Infra itself. Tracking labels' keys are defined with special format: goog-[\p{Ll}\p{N}]+ E.g. "key": "goog-k8s-cluster-name","value": "us-east1-b4rk" */
		trackingLabels: Option[Map[String, String]] = None,
	  /** Required. Type of cpu, e.g. N2. */
		cpuType: Option[Schema.CpuMetric.CpuTypeEnum] = None,
	  /** Required. Machine spec, e.g. N1_STANDARD_4. */
		machineSpec: Option[Schema.CpuMetric.MachineSpecEnum] = None
	)
	
	case class XPSVideoClassificationTrainResponse(
	  /** ## The fields below are only populated under uCAIP request scope. */
		modelArtifactSpec: Option[Schema.XPSVideoModelArtifactSpec] = None,
	  /** The actual train cost of creating this model, expressed in node seconds, i.e. 3,600 value in this field means 1 node hour. */
		trainCostNodeSeconds: Option[String] = None
	)
	
	case class XPSDataStats(
	  /** The statistics for ARRAY DataType. */
		arrayStats: Option[Schema.XPSArrayStats] = None,
	  /** The statistics for TIMESTAMP DataType. */
		timestampStats: Option[Schema.XPSTimestampStats] = None,
	  /** The statistics for FLOAT64 DataType. */
		float64Stats: Option[Schema.XPSFloat64Stats] = None,
	  /** The number of values that are null. */
		nullValueCount: Option[String] = None,
	  /** The statistics for STRUCT DataType. */
		structStats: Option[Schema.XPSStructStats] = None,
	  /** The statistics for CATEGORY DataType. */
		categoryStats: Option[Schema.XPSCategoryStats] = None,
	  /** The statistics for STRING DataType. */
		stringStats: Option[Schema.XPSStringStats] = None,
	  /** The number of values that are valid. */
		validValueCount: Option[String] = None,
	  /** The number of distinct values. */
		distinctValueCount: Option[String] = None
	)
	
	case class XPSCorrelationStats(
	  /** The correlation value using the Cramer's V measure. */
		cramersV: Option[BigDecimal] = None
	)
	
	case class XPSBatchPredictResponse(
	  /** Examples for batch prediction result. Under full API implementation, results are stored in shared RecordIO of AnnotatedExample protobufs, the annotations field of which is populated by XPS backend. */
		exampleSet: Option[Schema.XPSExampleSet] = None
	)
	
	case class ClassifyTextRequest(
	  /** Required. Input document. */
		document: Option[Schema.Document] = None
	)
	
	case class XPSResponseExplanationParameters(
	  /** An attribution method that computes Aumann-Shapley values taking advantage of the model's fully differentiable structure. Refer to this paper for more details: https://arxiv.org/abs/1703.01365 */
		integratedGradientsAttribution: Option[Schema.XPSIntegratedGradientsAttribution] = None,
	  /** An attribution method that redistributes Integrated Gradients attribution to segmented regions, taking advantage of the model's fully differentiable structure. Refer to this paper for more details: https://arxiv.org/abs/1906.02825 XRAI currently performs better on natural images, like a picture of a house or an animal. If the images are taken in artificial environments, like a lab or manufacturing line, or from diagnostic equipment, like x-rays or quality-control cameras, use Integrated Gradients instead. */
		xraiAttribution: Option[Schema.XPSXraiAttribution] = None
	)
	
	case class XPSMetricEntry(
	  /** Billing system labels for this (metric, value) pair. */
		systemLabels: Option[List[Schema.XPSMetricEntryLabel]] = None,
	  /** A signed 64-bit integer value. */
		int64Value: Option[String] = None,
	  /** For billing metrics that are using legacy sku's, set the legacy billing metric id here. This will be sent to Chemist as the "cloudbilling.googleapis.com/argentum_metric_id" label. Otherwise leave empty. */
		argentumMetricId: Option[String] = None,
	  /** A double value. */
		doubleValue: Option[BigDecimal] = None,
	  /** The metric name defined in the service configuration. */
		metricName: Option[String] = None
	)
	
	object XPSModelArtifactItem {
		enum ArtifactFormatEnum extends Enum[ArtifactFormatEnum] { case ARTIFACT_FORMAT_UNSPECIFIED, TF_CHECKPOINT, TF_SAVED_MODEL, TF_LITE, EDGE_TPU_TF_LITE, TF_JS, CORE_ML }
	}
	case class XPSModelArtifactItem(
	  /** The model artifact format. */
		artifactFormat: Option[Schema.XPSModelArtifactItem.ArtifactFormatEnum] = None,
	  /** The Google Cloud Storage URI that stores the model binary files. */
		gcsUri: Option[String] = None
	)
	
	case class AnnotateTextResponse(
	  /** The language of the text, which will be the same as the language specified in the request or, if not specified, the automatically-detected language. See Document.language_code field for more details. */
		languageCode: Option[String] = None,
	  /** Whether the language is officially supported by all requested features. The API may still return a response when the language is not supported, but it is on a best effort basis. */
		languageSupported: Option[Boolean] = None,
	  /** Harmful and sensitive categories identified in the input document. */
		moderationCategories: Option[List[Schema.ClassificationCategory]] = None,
	  /** The overall sentiment for the document. Populated if the user enables AnnotateTextRequest.Features.extract_document_sentiment. */
		documentSentiment: Option[Schema.Sentiment] = None,
	  /** Sentences in the input document. Populated if the user enables AnnotateTextRequest.Features.extract_document_sentiment. */
		sentences: Option[List[Schema.Sentence]] = None,
	  /** Categories identified in the input document. */
		categories: Option[List[Schema.ClassificationCategory]] = None,
	  /** Entities, along with their semantic information, in the input document. Populated if the user enables AnnotateTextRequest.Features.extract_entities . */
		entities: Option[List[Schema.Entity]] = None
	)
	
	case class XPSTablesClassificationMetrics(
	  /** Metrics building a curve. */
		curveMetrics: Option[List[Schema.XPSTablesClassificationMetricsCurveMetrics]] = None
	)
	
	case class XPSTrainingObjectivePoint(
	  /** The time at which this point was recorded. */
		createTime: Option[String] = None,
	  /** The objective value when this point was recorded. */
		value: Option[BigDecimal] = None
	)
	
	object XPSTranslationTrainResponse {
		enum ModelTypeEnum extends Enum[ModelTypeEnum] { case MODEL_TYPE_UNSPECIFIED, LEGACY, CURRENT }
	}
	case class XPSTranslationTrainResponse(
	  /** Type of the model. */
		modelType: Option[Schema.XPSTranslationTrainResponse.ModelTypeEnum] = None
	)
	
	object XPSDataType {
		enum TypeCodeEnum extends Enum[TypeCodeEnum] { case TYPE_CODE_UNSPECIFIED, FLOAT64, TIMESTAMP, STRING, ARRAY, STRUCT, CATEGORY }
	}
	case class XPSDataType(
	  /** The highly compatible data types to this data type. */
		compatibleDataTypes: Option[List[Schema.XPSDataType]] = None,
	  /** Required. The TypeCode for this type. */
		typeCode: Option[Schema.XPSDataType.TypeCodeEnum] = None,
	  /** If type_code == STRUCT, then `struct_type` provides type information for the struct's fields. */
		structType: Option[Schema.XPSStructType] = None,
	  /** If true, this DataType can also be `null`. */
		nullable: Option[Boolean] = None,
	  /** If type_code == TIMESTAMP then `time_format` provides the format in which that time field is expressed. The time_format must be written in `strftime` syntax. If time_format is not set, then the default format as described on the field is used. */
		timeFormat: Option[String] = None,
	  /** If type_code == ARRAY, then `list_element_type` is the type of the elements. */
		listElementType: Option[Schema.XPSDataType] = None
	)
	
	case class XPSImageModelArtifactSpec(
	  /** The model binary files in different formats for model export. */
		exportArtifact: Option[List[Schema.XPSModelArtifactItem]] = None,
	  /** Google Cloud Storage URI of decoded labels file for model export 'dict.txt'. */
		labelGcsUri: Option[String] = None,
	  /** The Tensorflow checkpoint files. e.g. Used for resumable training. */
		checkpointArtifact: Option[Schema.XPSModelArtifactItem] = None,
	  /** Google Cloud Storage URI of Tensorflow Lite metadata 'tflite_metadata.json'. */
		tfLiteMetadataGcsUri: Option[String] = None,
	  /** Google Cloud Storage URI prefix of Tensorflow JavaScript binary files 'groupX-shardXofX.bin'. Deprecated. */
		tfJsBinaryGcsPrefix: Option[String] = None,
	  /** The default model binary file used for serving (e.g. online predict, batch predict) via public Cloud AI Platform API. */
		servingArtifact: Option[Schema.XPSModelArtifactItem] = None
	)
	
	object DiskMetric {
		enum DiskTypeEnum extends Enum[DiskTypeEnum] { case UNKNOWN_DISK_TYPE, REGIONAL_SSD, REGIONAL_STORAGE, PD_SSD, PD_STANDARD, STORAGE_SNAPSHOT }
	}
	case class DiskMetric(
	  /** Required. Type of Disk, e.g. REGIONAL_SSD. */
		diskType: Option[Schema.DiskMetric.DiskTypeEnum] = None,
	  /** Required. Seconds of physical disk usage, e.g. 3600. */
		gibSec: Option[String] = None
	)
	
	case class XPSStructStats(
	  /** Map from a field name of the struct to data stats aggregated over series of all data in that field across all the structs. */
		fieldStats: Option[Map[String, Schema.XPSDataStats]] = None,
		commonStats: Option[Schema.XPSCommonStats] = None
	)
	
	case class XPSSpeechPreprocessStats(
	  /** The number of sentences in the training data set. */
		trainSentencesCount: Option[Int] = None,
	  /** The number of examples labeled as TRAIN by Speech xps server. */
		trainExamplesCount: Option[Int] = None,
	  /** The number of examples labelled as TEST by Speech xps server. */
		testExamplesCount: Option[Int] = None,
	  /** The number of rows marked HUMAN_LABELLED */
		numHumanLabeledExamples: Option[Int] = None,
	  /** Different types of data errors and the counts associated with them. */
		dataErrors: Option[List[Schema.XPSDataErrors]] = None,
	  /** The number of words in the test data set. */
		testWordsCount: Option[Int] = None,
	  /** The number of sentences in the test data set. */
		testSentencesCount: Option[Int] = None,
	  /** The number of rows marked as MACHINE_TRANSCRIBED */
		numMachineTranscribedExamples: Option[Int] = None,
	  /** The number of words in the training data set. */
		trainWordsCount: Option[Int] = None,
	  /** The number of samples found in the previously recorded logs data. */
		numLogsExamples: Option[Int] = None
	)
	
	case class XPSExportModelOutputConfig(
		tfSavedModelFormat: Option[Schema.XPSTfSavedModelFormat] = None,
		coreMlFormat: Option[Schema.XPSCoreMlFormat] = None,
		tfJsFormat: Option[Schema.XPSTfJsFormat] = None,
	  /** The Google Contained Registry path the exported files to be pushed to. This location is set if the exported format is DOCKDER. */
		outputGcrUri: Option[String] = None,
		tfLiteFormat: Option[Schema.XPSTfLiteFormat] = None,
	  /** The Google Cloud Storage directory where XPS will output the exported models and related files. Format: gs://bucket/directory */
		outputGcsUri: Option[String] = None,
		edgeTpuTfLiteFormat: Option[Schema.XPSEdgeTpuTfLiteFormat] = None,
	  /** For any model and format: If true, will additionally export FirebaseExportedModelInfo in a firebase.txt file. */
		exportFirebaseAuxiliaryInfo: Option[Boolean] = None,
		dockerFormat: Option[Schema.XPSDockerFormat] = None
	)
	
	case class XPSImageSegmentationEvaluationMetrics(
	  /** Metrics that have confidence thresholds. Precision-recall curve can be derived from it. */
		confidenceMetricsEntries: Option[List[Schema.XPSImageSegmentationEvaluationMetricsConfidenceMetricsEntry]] = None
	)
	
	object XPSFileSpec {
		enum FileFormatEnum extends Enum[FileFormatEnum] { case FILE_FORMAT_UNKNOWN, FILE_FORMAT_SSTABLE, FILE_FORMAT_TRANSLATION_RKV, FILE_FORMAT_RECORDIO, FILE_FORMAT_RAW_CSV, FILE_FORMAT_RAW_CAPACITOR }
	}
	case class XPSFileSpec(
	  /** Single file path, or file pattern of format "/path/to/file@shard_count". E.g. /cns/cell-d/somewhere/file@2 is expanded to two files: /cns/cell-d/somewhere/file-00000-of-00002 and /cns/cell-d/somewhere/file-00001-of-00002. */
		fileSpec: Option[String] = None,
	  /** Deprecated. Use file_spec. */
		directoryPath: Option[String] = None,
	  /** Deprecated. Use file_spec. */
		singleFilePath: Option[String] = None,
		fileFormat: Option[Schema.XPSFileSpec.FileFormatEnum] = None
	)
	
	case class XPSTrackMetricsEntry(
	  /** Output only. The mean mismatch rate over all confidence thresholds. */
		meanMismatchRate: Option[BigDecimal] = None,
	  /** Output only. Metrics for each label-match confidence_threshold from 0.05,0.10,...,0.95,0.96,0.97,0.98,0.99. Precision-recall curve is derived from them. */
		confidenceMetricsEntries: Option[List[Schema.XPSTrackMetricsEntryConfidenceMetricsEntry]] = None,
	  /** Output only. The mean average precision over all confidence thresholds. */
		meanTrackingAveragePrecision: Option[BigDecimal] = None,
	  /** Output only. The mean bounding box iou over all confidence thresholds. */
		meanBoundingBoxIou: Option[BigDecimal] = None,
	  /** Output only. The intersection-over-union threshold value between bounding boxes across frames used to compute this metric entry. */
		iouThreshold: Option[BigDecimal] = None
	)
	
	case class XPSVideoModelArtifactSpec(
	  /** The model binary files in different formats for model export. */
		exportArtifact: Option[List[Schema.XPSModelArtifactItem]] = None,
	  /** The default model binary file used for serving (e.g. batch predict) via public Cloud AI Platform API. */
		servingArtifact: Option[Schema.XPSModelArtifactItem] = None
	)
	
	object RamMetric {
		enum MachineSpecEnum extends Enum[MachineSpecEnum] { case UNKNOWN_MACHINE_SPEC, N1_STANDARD_2, N1_STANDARD_4, N1_STANDARD_8, N1_STANDARD_16, N1_STANDARD_32, N1_STANDARD_64, N1_STANDARD_96, N1_HIGHMEM_2, N1_HIGHMEM_4, N1_HIGHMEM_8, N1_HIGHMEM_16, N1_HIGHMEM_32, N1_HIGHMEM_64, N1_HIGHMEM_96, N1_HIGHCPU_2, N1_HIGHCPU_4, N1_HIGHCPU_8, N1_HIGHCPU_16, N1_HIGHCPU_32, N1_HIGHCPU_64, N1_HIGHCPU_96, A2_HIGHGPU_1G, A2_HIGHGPU_2G, A2_HIGHGPU_4G, A2_HIGHGPU_8G, A2_MEGAGPU_16G, A2_ULTRAGPU_1G, A2_ULTRAGPU_2G, A2_ULTRAGPU_4G, A2_ULTRAGPU_8G, A3_HIGHGPU_1G, A3_HIGHGPU_2G, A3_HIGHGPU_4G, A3_HIGHGPU_8G, A3_MEGAGPU_8G, E2_STANDARD_2, E2_STANDARD_4, E2_STANDARD_8, E2_STANDARD_16, E2_STANDARD_32, E2_HIGHMEM_2, E2_HIGHMEM_4, E2_HIGHMEM_8, E2_HIGHMEM_16, E2_HIGHCPU_2, E2_HIGHCPU_4, E2_HIGHCPU_8, E2_HIGHCPU_16, E2_HIGHCPU_32, N2_STANDARD_2, N2_STANDARD_4, N2_STANDARD_8, N2_STANDARD_16, N2_STANDARD_32, N2_STANDARD_48, N2_STANDARD_64, N2_STANDARD_80, N2_STANDARD_96, N2_STANDARD_128, N2_HIGHMEM_2, N2_HIGHMEM_4, N2_HIGHMEM_8, N2_HIGHMEM_16, N2_HIGHMEM_32, N2_HIGHMEM_48, N2_HIGHMEM_64, N2_HIGHMEM_80, N2_HIGHMEM_96, N2_HIGHMEM_128, N2_HIGHCPU_2, N2_HIGHCPU_4, N2_HIGHCPU_8, N2_HIGHCPU_16, N2_HIGHCPU_32, N2_HIGHCPU_48, N2_HIGHCPU_64, N2_HIGHCPU_80, N2_HIGHCPU_96, N2D_STANDARD_2, N2D_STANDARD_4, N2D_STANDARD_8, N2D_STANDARD_16, N2D_STANDARD_32, N2D_STANDARD_48, N2D_STANDARD_64, N2D_STANDARD_80, N2D_STANDARD_96, N2D_STANDARD_128, N2D_STANDARD_224, N2D_HIGHMEM_2, N2D_HIGHMEM_4, N2D_HIGHMEM_8, N2D_HIGHMEM_16, N2D_HIGHMEM_32, N2D_HIGHMEM_48, N2D_HIGHMEM_64, N2D_HIGHMEM_80, N2D_HIGHMEM_96, N2D_HIGHCPU_2, N2D_HIGHCPU_4, N2D_HIGHCPU_8, N2D_HIGHCPU_16, N2D_HIGHCPU_32, N2D_HIGHCPU_48, N2D_HIGHCPU_64, N2D_HIGHCPU_80, N2D_HIGHCPU_96, N2D_HIGHCPU_128, N2D_HIGHCPU_224, C2_STANDARD_4, C2_STANDARD_8, C2_STANDARD_16, C2_STANDARD_30, C2_STANDARD_60, C2D_STANDARD_2, C2D_STANDARD_4, C2D_STANDARD_8, C2D_STANDARD_16, C2D_STANDARD_32, C2D_STANDARD_56, C2D_STANDARD_112, C2D_HIGHCPU_2, C2D_HIGHCPU_4, C2D_HIGHCPU_8, C2D_HIGHCPU_16, C2D_HIGHCPU_32, C2D_HIGHCPU_56, C2D_HIGHCPU_112, C2D_HIGHMEM_2, C2D_HIGHMEM_4, C2D_HIGHMEM_8, C2D_HIGHMEM_16, C2D_HIGHMEM_32, C2D_HIGHMEM_56, C2D_HIGHMEM_112, G2_STANDARD_4, G2_STANDARD_8, G2_STANDARD_12, G2_STANDARD_16, G2_STANDARD_24, G2_STANDARD_32, G2_STANDARD_48, G2_STANDARD_96, C3_STANDARD_4, C3_STANDARD_8, C3_STANDARD_22, C3_STANDARD_44, C3_STANDARD_88, C3_STANDARD_176, C3_HIGHCPU_4, C3_HIGHCPU_8, C3_HIGHCPU_22, C3_HIGHCPU_44, C3_HIGHCPU_88, C3_HIGHCPU_176, C3_HIGHMEM_4, C3_HIGHMEM_8, C3_HIGHMEM_22, C3_HIGHMEM_44, C3_HIGHMEM_88, C3_HIGHMEM_176 }
		enum RamTypeEnum extends Enum[RamTypeEnum] { case UNKNOWN_RAM_TYPE, A2, A3, C2, C2D, CUSTOM, E2, G2, C3, M2, M1, N1, N2_CUSTOM, N2, N2D }
	}
	case class RamMetric(
	  /** Required. Machine spec, e.g. N1_STANDARD_4. */
		machineSpec: Option[Schema.RamMetric.MachineSpecEnum] = None,
	  /** Required. Type of ram. */
		ramType: Option[Schema.RamMetric.RamTypeEnum] = None,
	  /** Required. VM memory in gb. */
		memories: Option[BigDecimal] = None,
	  /** Required. VM memory in Gigabyte second, e.g. 3600. Using int64 type to match billing metrics definition. */
		gibSec: Option[String] = None,
	  /** Billing tracking labels. They do not contain any user data but only the labels set by Vertex Core Infra itself. Tracking labels' keys are defined with special format: goog-[\p{Ll}\p{N}]+ E.g. "key": "goog-k8s-cluster-name","value": "us-east1-b4rk" */
		trackingLabels: Option[Map[String, String]] = None
	)
	
	object GpuMetric {
		enum MachineSpecEnum extends Enum[MachineSpecEnum] { case UNKNOWN_MACHINE_SPEC, N1_STANDARD_2, N1_STANDARD_4, N1_STANDARD_8, N1_STANDARD_16, N1_STANDARD_32, N1_STANDARD_64, N1_STANDARD_96, N1_HIGHMEM_2, N1_HIGHMEM_4, N1_HIGHMEM_8, N1_HIGHMEM_16, N1_HIGHMEM_32, N1_HIGHMEM_64, N1_HIGHMEM_96, N1_HIGHCPU_2, N1_HIGHCPU_4, N1_HIGHCPU_8, N1_HIGHCPU_16, N1_HIGHCPU_32, N1_HIGHCPU_64, N1_HIGHCPU_96, A2_HIGHGPU_1G, A2_HIGHGPU_2G, A2_HIGHGPU_4G, A2_HIGHGPU_8G, A2_MEGAGPU_16G, A2_ULTRAGPU_1G, A2_ULTRAGPU_2G, A2_ULTRAGPU_4G, A2_ULTRAGPU_8G, A3_HIGHGPU_1G, A3_HIGHGPU_2G, A3_HIGHGPU_4G, A3_HIGHGPU_8G, A3_MEGAGPU_8G, E2_STANDARD_2, E2_STANDARD_4, E2_STANDARD_8, E2_STANDARD_16, E2_STANDARD_32, E2_HIGHMEM_2, E2_HIGHMEM_4, E2_HIGHMEM_8, E2_HIGHMEM_16, E2_HIGHCPU_2, E2_HIGHCPU_4, E2_HIGHCPU_8, E2_HIGHCPU_16, E2_HIGHCPU_32, N2_STANDARD_2, N2_STANDARD_4, N2_STANDARD_8, N2_STANDARD_16, N2_STANDARD_32, N2_STANDARD_48, N2_STANDARD_64, N2_STANDARD_80, N2_STANDARD_96, N2_STANDARD_128, N2_HIGHMEM_2, N2_HIGHMEM_4, N2_HIGHMEM_8, N2_HIGHMEM_16, N2_HIGHMEM_32, N2_HIGHMEM_48, N2_HIGHMEM_64, N2_HIGHMEM_80, N2_HIGHMEM_96, N2_HIGHMEM_128, N2_HIGHCPU_2, N2_HIGHCPU_4, N2_HIGHCPU_8, N2_HIGHCPU_16, N2_HIGHCPU_32, N2_HIGHCPU_48, N2_HIGHCPU_64, N2_HIGHCPU_80, N2_HIGHCPU_96, N2D_STANDARD_2, N2D_STANDARD_4, N2D_STANDARD_8, N2D_STANDARD_16, N2D_STANDARD_32, N2D_STANDARD_48, N2D_STANDARD_64, N2D_STANDARD_80, N2D_STANDARD_96, N2D_STANDARD_128, N2D_STANDARD_224, N2D_HIGHMEM_2, N2D_HIGHMEM_4, N2D_HIGHMEM_8, N2D_HIGHMEM_16, N2D_HIGHMEM_32, N2D_HIGHMEM_48, N2D_HIGHMEM_64, N2D_HIGHMEM_80, N2D_HIGHMEM_96, N2D_HIGHCPU_2, N2D_HIGHCPU_4, N2D_HIGHCPU_8, N2D_HIGHCPU_16, N2D_HIGHCPU_32, N2D_HIGHCPU_48, N2D_HIGHCPU_64, N2D_HIGHCPU_80, N2D_HIGHCPU_96, N2D_HIGHCPU_128, N2D_HIGHCPU_224, C2_STANDARD_4, C2_STANDARD_8, C2_STANDARD_16, C2_STANDARD_30, C2_STANDARD_60, C2D_STANDARD_2, C2D_STANDARD_4, C2D_STANDARD_8, C2D_STANDARD_16, C2D_STANDARD_32, C2D_STANDARD_56, C2D_STANDARD_112, C2D_HIGHCPU_2, C2D_HIGHCPU_4, C2D_HIGHCPU_8, C2D_HIGHCPU_16, C2D_HIGHCPU_32, C2D_HIGHCPU_56, C2D_HIGHCPU_112, C2D_HIGHMEM_2, C2D_HIGHMEM_4, C2D_HIGHMEM_8, C2D_HIGHMEM_16, C2D_HIGHMEM_32, C2D_HIGHMEM_56, C2D_HIGHMEM_112, G2_STANDARD_4, G2_STANDARD_8, G2_STANDARD_12, G2_STANDARD_16, G2_STANDARD_24, G2_STANDARD_32, G2_STANDARD_48, G2_STANDARD_96, C3_STANDARD_4, C3_STANDARD_8, C3_STANDARD_22, C3_STANDARD_44, C3_STANDARD_88, C3_STANDARD_176, C3_HIGHCPU_4, C3_HIGHCPU_8, C3_HIGHCPU_22, C3_HIGHCPU_44, C3_HIGHCPU_88, C3_HIGHCPU_176, C3_HIGHMEM_4, C3_HIGHMEM_8, C3_HIGHMEM_22, C3_HIGHMEM_44, C3_HIGHMEM_88, C3_HIGHMEM_176 }
		enum GpuTypeEnum extends Enum[GpuTypeEnum] { case UNKNOWN_GPU_TYPE, NVIDIA_TESLA_A100, NVIDIA_A100_80GB, NVIDIA_TESLA_K80, NVIDIA_L4, NVIDIA_TESLA_P100, NVIDIA_TESLA_P4, NVIDIA_TESLA_T4, NVIDIA_TESLA_V100, NVIDIA_H100_80GB }
	}
	case class GpuMetric(
	  /** Billing tracking labels. They do not contain any user data but only the labels set by Vertex Core Infra itself. Tracking labels' keys are defined with special format: goog-[\p{Ll}\p{N}]+ E.g. "key": "goog-k8s-cluster-name","value": "us-east1-b4rk" */
		trackingLabels: Option[Map[String, String]] = None,
	  /** Required. Machine spec, e.g. N1_STANDARD_4. */
		machineSpec: Option[Schema.GpuMetric.MachineSpecEnum] = None,
	  /** Required. Type of GPU, e.g. NVIDIA_TESLA_V100. */
		gpuType: Option[Schema.GpuMetric.GpuTypeEnum] = None,
	  /** Required. Seconds of GPU usage, e.g. 3600. */
		gpuSec: Option[String] = None
	)
	
	case class XPSTuningTrial(
	  /** The optimization objective evaluation of the eval split data. */
		trainingObjectivePoint: Option[Schema.XPSTrainingObjectivePoint] = None,
	  /** Model parameters for the trial. */
		modelStructure: Option[Schema.XPSTablesModelStructure] = None
	)
	
	case class XPSTfSavedModelFormat(
	
	)
	
	case class XPSTimestampStats(
		commonStats: Option[Schema.XPSCommonStats] = None,
	  /** The string key is the pre-defined granularity. Currently supported: hour_of_day, day_of_week, month_of_year. Granularities finer that the granularity of timestamp data are not populated (e.g. if timestamps are at day granularity, then hour_of_day is not populated). */
		granularStats: Option[Map[String, Schema.XPSTimestampStatsGranularStats]] = None,
		medianTimestampNanos: Option[String] = None
	)
	
	case class ModerateTextResponse(
	  /** The language of the text, which will be the same as the language specified in the request or, if not specified, the automatically-detected language. See Document.language_code field for more details. */
		languageCode: Option[String] = None,
	  /** Whether the language is officially supported. The API may still return a response when the language is not supported, but it is on a best effort basis. */
		languageSupported: Option[Boolean] = None,
	  /** Harmful and sensitive categories representing the input document. */
		moderationCategories: Option[List[Schema.ClassificationCategory]] = None
	)
	
	object XPSImageObjectDetectionModelSpec {
		enum StopReasonEnum extends Enum[StopReasonEnum] { case TRAIN_STOP_REASON_UNSPECIFIED, TRAIN_STOP_REASON_BUDGET_REACHED, TRAIN_STOP_REASON_MODEL_CONVERGED, TRAIN_STOP_REASON_MODEL_EARLY_STOPPED }
	}
	case class XPSImageObjectDetectionModelSpec(
	  /** The actual train cost of creating this model, expressed in node seconds, i.e. 3,600 value in this field means 1 node hour. */
		trainCostNodeSeconds: Option[String] = None,
	  /** Stop reason for training job, e.g. 'TRAIN_BUDGET_REACHED', 'MODEL_CONVERGED'. */
		stopReason: Option[Schema.XPSImageObjectDetectionModelSpec.StopReasonEnum] = None,
		exportModelSpec: Option[Schema.XPSImageExportModelSpec] = None,
		modelServingSpec: Option[Schema.XPSImageModelServingSpec] = None,
	  /** Max number of bounding box. */
		maxBoundingBoxCount: Option[String] = None,
	  /** Total number of classes. */
		classCount: Option[String] = None,
	  /** ## The fields below are only populated under uCAIP request scope. */
		modelArtifactSpec: Option[Schema.XPSImageModelArtifactSpec] = None
	)
	
	case class XPSMetricEntryLabel(
	  /** The name of the label. */
		labelName: Option[String] = None,
	  /** The value of the label. */
		labelValue: Option[String] = None
	)
	
	case class XPSEvaluationMetricsSet(
	  /** Number of the evaluation metrics (usually one per label plus overall). */
		numEvaluationMetrics: Option[String] = None,
	  /** File spec containing evaluation metrics of a model, must point to RecordIO file(s) of intelligence.cloud.automl.xps.EvaluationMetrics messages. */
		fileSpec: Option[Schema.XPSFileSpec] = None,
	  /** Inline EvaluationMetrics - should be relatively small. For passing large quantities of exhaustive metrics, use file_spec. */
		evaluationMetrics: Option[List[Schema.XPSEvaluationMetrics]] = None
	)
	
	case class XPSCategoryStats(
		commonStats: Option[Schema.XPSCommonStats] = None,
	  /** The statistics of the top 20 CATEGORY values, ordered by CategoryStats.SingleCategoryStats.count. */
		topCategoryStats: Option[List[Schema.XPSCategoryStatsSingleCategoryStats]] = None
	)
	
	case class Sentence(
	  /** For calls to AnalyzeSentiment or if AnnotateTextRequest.Features.extract_document_sentiment is set to true, this field will contain the sentiment for the sentence. */
		sentiment: Option[Schema.Sentiment] = None,
	  /** The sentence text. */
		text: Option[Schema.TextSpan] = None
	)
	
	case class XPSExampleSet(
	  /** Number of input sources. */
		numInputSources: Option[String] = None,
	  /** File spec of the examples or input sources. */
		fileSpec: Option[Schema.XPSFileSpec] = None,
	  /** Fingerprint of the example set. */
		fingerprint: Option[String] = None,
	  /** Number of examples. */
		numExamples: Option[String] = None
	)
	
	case class XPSXpsOperationMetadata(
	  /** Optional. XPS server can opt to provide example count of the long running operation (e.g. training, data importing, batch prediction). */
		exampleCount: Option[String] = None,
		tablesTrainingOperationMetadata: Option[Schema.XPSTablesTrainingOperationMetadata] = None,
		videoBatchPredictOperationMetadata: Option[Schema.XPSVideoBatchPredictOperationMetadata] = None,
		videoTrainingOperationMetadata: Option[Schema.XPSVideoTrainingOperationMetadata] = None,
	  /** Metrics for the operation. By the time the operation is terminated (whether succeeded or failed) as returned from XPS, AutoML BE assumes the metrics are finalized. AutoML BE transparently posts the metrics to Chemist if it's not empty, regardless of the response content or error type. If user is supposed to be charged in case of cancellation/error, this field should be set. In the case where the type of LRO doesn't require any billing, this field should be left unset. */
		reportingMetrics: Option[Schema.XPSReportingMetrics] = None,
		visionTrainingOperationMetadata: Option[Schema.XPSVisionTrainingOperationMetadata] = None
	)
	
	object EntityMention {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNKNOWN, PROPER, COMMON }
	}
	case class EntityMention(
	  /** The type of the entity mention. */
		`type`: Option[Schema.EntityMention.TypeEnum] = None,
	  /** For calls to AnalyzeEntitySentiment this field will contain the sentiment expressed for this mention of the entity in the provided document. */
		sentiment: Option[Schema.Sentiment] = None,
	  /** The mention text. */
		text: Option[Schema.TextSpan] = None,
	  /** Probability score associated with the entity. The score shows the probability of the entity mention being the entity type. The score is in (0, 1] range. */
		probability: Option[BigDecimal] = None
	)
	
	case class XPSColumnSpecCorrelatedColumn(
		columnId: Option[Int] = None,
		correlationStats: Option[Schema.XPSCorrelationStats] = None
	)
	
	case class AnnotateTextRequestFeatures(
	  /** Optional. Moderate the document for harmful and sensitive categories. */
		moderateText: Option[Boolean] = None,
	  /** Optional. Extract document-level sentiment. */
		extractDocumentSentiment: Option[Boolean] = None,
	  /** Optional. Classify the full document into categories. */
		classifyText: Option[Boolean] = None,
	  /** Optional. Extract entities. */
		extractEntities: Option[Boolean] = None
	)
	
	object XPSImageClassificationTrainResponse {
		enum StopReasonEnum extends Enum[StopReasonEnum] { case TRAIN_STOP_REASON_UNSPECIFIED, TRAIN_STOP_REASON_BUDGET_REACHED, TRAIN_STOP_REASON_MODEL_CONVERGED, TRAIN_STOP_REASON_MODEL_EARLY_STOPPED }
	}
	case class XPSImageClassificationTrainResponse(
		modelServingSpec: Option[Schema.XPSImageModelServingSpec] = None,
	  /** ## The fields below are only populated under uCAIP request scope. */
		modelArtifactSpec: Option[Schema.XPSImageModelArtifactSpec] = None,
	  /** The actual cost to create this model. - For edge type model, the cost is expressed in node hour. - For cloud type model,the cost is expressed in compute hour. - Populated for models created before GA. To be deprecated after GA. */
		trainCostInNodeTime: Option[String] = None,
	  /** Information of downloadable models that are pre-generated as part of training flow and will be persisted in AutoMl backend. Populated for AutoMl requests. */
		exportModelSpec: Option[Schema.XPSImageExportModelSpec] = None,
	  /** Stop reason for training job, e.g. 'TRAIN_BUDGET_REACHED', 'MODEL_CONVERGED', 'MODEL_EARLY_STOPPED'. */
		stopReason: Option[Schema.XPSImageClassificationTrainResponse.StopReasonEnum] = None,
	  /** The actual training cost, expressed in node seconds. Populated for models trained in node time. */
		trainCostNodeSeconds: Option[String] = None,
	  /** Total number of classes. */
		classCount: Option[String] = None
	)
	
	case class XPSRegressionMetricsEntry(
	  /** The actual target value for a row in the dataset. */
		trueValue: Option[BigDecimal] = None,
	  /** The observed value for a row in the dataset. */
		predictedValue: Option[BigDecimal] = None
	)
	
	case class XPSTablesModelStructureModelParameters(
		hyperparameters: Option[List[Schema.XPSTablesModelStructureModelParametersParameter]] = None
	)
	
	case class XPSEdgeTpuTfLiteFormat(
	
	)
	
	case class XPSFloat64StatsHistogramBucket(
	  /** The minimum value of the bucket, inclusive. */
		min: Option[BigDecimal] = None,
	  /** The number of data values that are in the bucket, i.e. are between min and max values. */
		count: Option[String] = None,
	  /** The maximum value of the bucket, exclusive unless max = `"Infinity"`, in which case it's inclusive. */
		max: Option[BigDecimal] = None
	)
	
	object AnnotateTextRequest {
		enum EncodingTypeEnum extends Enum[EncodingTypeEnum] { case NONE, UTF8, UTF16, UTF32 }
	}
	case class AnnotateTextRequest(
	  /** Required. Input document. */
		document: Option[Schema.Document] = None,
	  /** Required. The enabled features. */
		features: Option[Schema.AnnotateTextRequestFeatures] = None,
	  /** The encoding type used by the API to calculate offsets. */
		encodingType: Option[Schema.AnnotateTextRequest.EncodingTypeEnum] = None
	)
	
	case class XPSResponseExplanationSpec(
	  /** Explanation type. For AutoML Image Classification models, possible values are: &#42; `image-integrated-gradients` &#42; `image-xrai` */
		explanationType: Option[String] = None,
	  /** Parameters that configure explaining of the Model's predictions. */
		parameters: Option[Schema.XPSResponseExplanationParameters] = None,
	  /** Metadata describing the Model's input and output for explanation. */
		metadata: Option[Schema.XPSResponseExplanationMetadata] = None
	)
	
	case class XPSSpeechPreprocessResponse(
	  /** Location of shards of sstables (training data) of DataUtterance protos. */
		cnsTrainDataPath: Option[String] = None,
	  /** Location od shards of sstables (test data) of DataUtterance protos. */
		cnsTestDataPath: Option[String] = None,
	  /** The metrics for prebuilt speech models. They are included here because there is no prebuilt speech models stored in the AutoML. */
		prebuiltModelEvaluationMetrics: Option[Schema.XPSSpeechEvaluationMetrics] = None,
	  /** Stats associated with the data. */
		speechPreprocessStats: Option[Schema.XPSSpeechPreprocessStats] = None
	)
	
	case class XPSImageExportModelSpec(
	  /** Contains the model format and internal location of the model files to be exported/downloaded. Use the Google Cloud Storage bucket name which is provided via TrainRequest.gcs_bucket_name to store the model files. */
		exportModelOutputConfig: Option[List[Schema.XPSExportModelOutputConfig]] = None
	)
	
	case class XPSIntegratedGradientsAttribution(
	  /** The number of steps for approximating the path integral. A good value to start is 50 and gradually increase until the sum to diff property is within the desired error range. Valid range of its value is [1, 100], inclusively. */
		stepCount: Option[Int] = None
	)
	
	case class AnalyzeSentimentResponse(
	  /** The sentiment for all the sentences in the document. */
		sentences: Option[List[Schema.Sentence]] = None,
	  /** The language of the text, which will be the same as the language specified in the request or, if not specified, the automatically-detected language. See Document.language_code field for more details. */
		languageCode: Option[String] = None,
	  /** Whether the language is officially supported. The API may still return a response when the language is not supported, but it is on a best effort basis. */
		languageSupported: Option[Boolean] = None,
	  /** The overall sentiment of the input document. */
		documentSentiment: Option[Schema.Sentiment] = None
	)
	
	case class XPSImageSegmentationEvaluationMetricsConfidenceMetricsEntry(
	  /** DSC or the F1 score: The harmonic mean of recall and precision. */
		diceScoreCoefficient: Option[BigDecimal] = None,
	  /** Confusion matrix of the per confidence_threshold evaluation. Pixel counts are set here. Only set for model level evaluation, not for evaluation per label. */
		confusionMatrix: Option[Schema.XPSConfusionMatrix] = None,
	  /** Recall for the given confidence threshold. */
		recall: Option[BigDecimal] = None,
	  /** The confidence threshold value used to compute the metrics. */
		confidenceThreshold: Option[BigDecimal] = None,
	  /** Precision for the given confidence threshold. */
		precision: Option[BigDecimal] = None,
	  /** IOU score. */
		iouScore: Option[BigDecimal] = None
	)
	
	case class XPSTablesPreprocessResponse(
	  /** The table/column id, column_name and the DataTypes of the columns will be populated. */
		tablesDatasetMetadata: Option[Schema.XPSTablesDatasetMetadata] = None
	)
	
	case class XPSEvaluationMetrics(
		textExtractionEvalMetrics: Option[Schema.XPSTextExtractionEvaluationMetrics] = None,
		translationEvalMetrics: Option[Schema.XPSTranslationEvaluationMetrics] = None,
		videoObjectTrackingEvalMetrics: Option[Schema.XPSVideoObjectTrackingEvaluationMetrics] = None,
		regressionEvalMetrics: Option[Schema.XPSRegressionEvaluationMetrics] = None,
	  /** The number of examples used to create this evaluation metrics instance. */
		evaluatedExampleCount: Option[Int] = None,
		textSentimentEvalMetrics: Option[Schema.XPSTextSentimentEvaluationMetrics] = None,
		videoClassificationEvalMetrics: Option[Schema.XPSClassificationEvaluationMetrics] = None,
		imageSegmentationEvalMetrics: Option[Schema.XPSImageSegmentationEvaluationMetrics] = None,
	  /** The integer category label for which this evaluation metric instance had been created. Valid categories are 0 or higher. Overall model evaluation should set this to negative values (rather than implicit zero). Only used for Image Segmentation (prefer to set annotation_spec_id_token instead). Note: uCAIP Image Segmentation should use annotation_spec_id_token. */
		category: Option[Int] = None,
		tablesEvalMetrics: Option[Schema.XPSTablesEvaluationMetrics] = None,
	  /** The label for which this evaluation metrics instance had been created. Empty iff this is an overall model evaluation (like Tables evaluation metrics), i.e. aggregated across all labels. The label maps to AnnotationSpec.display_name in Public API protos. Only used by MVP implementation and text sentiment FULL implementation. */
		label: Option[String] = None,
		tablesClassificationEvalMetrics: Option[Schema.XPSClassificationEvaluationMetrics] = None,
		imageClassificationEvalMetrics: Option[Schema.XPSClassificationEvaluationMetrics] = None,
		imageObjectDetectionEvalMetrics: Option[Schema.XPSImageObjectDetectionEvaluationMetrics] = None,
	  /** The annotation_spec for which this evaluation metrics instance had been created. Empty iff this is an overall model evaluation (like Tables evaluation metrics), i.e. aggregated across all labels. The value comes from the input annotations in AnnotatedExample. For MVP product or for text sentiment models where annotation_spec_id_token is not available, set label instead. */
		annotationSpecIdToken: Option[String] = None,
		textClassificationEvalMetrics: Option[Schema.XPSClassificationEvaluationMetrics] = None,
		videoActionRecognitionEvalMetrics: Option[Schema.XPSVideoActionRecognitionEvaluationMetrics] = None
	)
	
	case class XPSPreprocessResponse(
		tablesPreprocessResponse: Option[Schema.XPSTablesPreprocessResponse] = None,
	  /** Preprocessed examples, that are to be imported into AutoML storage. This should point to RecordIO file(s) of PreprocessedExample messages. The PreprocessedExample.mvp_training_data-s returned here are later verbatim passed to Train() call in TrainExample.mvp_training_data. */
		outputExampleSet: Option[Schema.XPSExampleSet] = None,
		speechPreprocessResp: Option[Schema.XPSSpeechPreprocessResponse] = None,
		translationPreprocessResp: Option[Schema.XPSTranslationPreprocessResponse] = None
	)
	
	case class XPSTablesModelStructure(
	  /** A list of models. */
		modelParameters: Option[List[Schema.XPSTablesModelStructureModelParameters]] = None
	)
	
	case class XPSRow(
	  /** The ids of the columns. Note: The below `values` field must match order of this field, if this field is set. */
		columnIds: Option[List[Int]] = None,
	  /** The values of the row cells, given in the same order as the column_ids. If column_ids is not set, then in the same order as the input_feature_column_ids in TablesModelMetadata. */
		values: Option[List[JsValue]] = None
	)
	
	case class XPSFloat64Stats(
	  /** The standard deviation of the series. */
		standardDeviation: Option[BigDecimal] = None,
	  /** Ordered from 0 to k k-quantile values of the data series of n values. The value at index i is, approximately, the i&#42;n/k-th smallest value in the series; for i = 0 and i = k these are, respectively, the min and max values. */
		quantiles: Option[List[BigDecimal]] = None,
	  /** The mean of the series. */
		mean: Option[BigDecimal] = None,
		commonStats: Option[Schema.XPSCommonStats] = None,
	  /** Histogram buckets of the data series. Sorted by the min value of the bucket, ascendingly, and the number of the buckets is dynamically generated. The buckets are non-overlapping and completely cover whole FLOAT64 range with min of first bucket being `"-Infinity"`, and max of the last one being `"Infinity"`. */
		histogramBuckets: Option[List[Schema.XPSFloat64StatsHistogramBucket]] = None
	)
	
	case class Sentiment(
	  /** Sentiment score between -1.0 (negative sentiment) and 1.0 (positive sentiment). */
		score: Option[BigDecimal] = None,
	  /** A non-negative number in the [0, +inf] range, which represents the absolute magnitude of sentiment regardless of score (positive or negative). */
		magnitude: Option[BigDecimal] = None
	)
	
	case class XPSConfusionMatrix(
	  /** Rows in the confusion matrix. The number of rows is equal to the size of `annotation_spec_id_token`. `row[i].value[j]` is the number of examples that have ground truth of the `annotation_spec_id_token[i]` and are predicted as `annotation_spec_id_token[j]` by the model being evaluated. */
		row: Option[List[Schema.XPSConfusionMatrixRow]] = None,
	  /** For the following three repeated fields, only one is intended to be set. annotation_spec_id_token is preferable to be set. ID tokens of the annotation specs used in the confusion matrix. */
		annotationSpecIdToken: Option[List[String]] = None,
	  /** Sentiment labels used in the confusion matrix. Set only for text sentiment models. For AutoML Text Revamp, use `annotation_spec_id_token` instead and leave this field empty. */
		sentimentLabel: Option[List[Int]] = None,
	  /** Category (mainly for segmentation). Set only for image segmentation models. Note: uCAIP Image Segmentation should use annotation_spec_id_token. */
		category: Option[List[Int]] = None
	)
	
	case class XPSVideoObjectTrackingTrainResponse(
	  /** The actual train cost of creating this model, expressed in node seconds, i.e. 3,600 value in this field means 1 node hour. */
		trainCostNodeSeconds: Option[String] = None,
	  /** Populated for AutoML request only. */
		exportModelSpec: Option[Schema.XPSVideoExportModelSpec] = None,
	  /** ## The fields below are only populated under uCAIP request scope. */
		modelArtifactSpec: Option[Schema.XPSVideoModelArtifactSpec] = None
	)
	
	case class XPSTfLiteFormat(
	
	)
	
	case class XPSVideoActionRecognitionTrainResponse(
	  /** The actual train cost of creating this model, expressed in node seconds, i.e. 3,600 value in this field means 1 node hour. */
		trainCostNodeSeconds: Option[String] = None,
	  /** ## The fields below are only populated under uCAIP request scope. */
		modelArtifactSpec: Option[Schema.XPSVideoModelArtifactSpec] = None
	)
	
	case class XPSVideoActionRecognitionEvaluationMetrics(
	  /** Output only. The metric entries for precision window lengths: 1s,2s,3s,4s, 5s. */
		videoActionMetricsEntries: Option[List[Schema.XPSVideoActionMetricsEntry]] = None,
	  /** Output only. The number of ground truth actions used to create this evaluation. */
		evaluatedActionCount: Option[Int] = None
	)
	
	case class XPSSpeechModelSpec(
	  /** Required for speech xps backend. Speech xps has to use dataset_id and model_id as the primary key in db so that speech API can query the db directly. */
		datasetId: Option[String] = None,
	  /** Model specs for all submodels contained in this model. */
		subModelSpecs: Option[List[Schema.XPSSpeechModelSpecSubModelSpec]] = None,
		language: Option[String] = None
	)
	
	case class XPSColorMap(
	  /** Should be used during training. */
		annotationSpecIdToken: Option[String] = None,
	  /** This type is deprecated in favor of the IntColor below. This is because google.type.Color represent color has a float which semantically does not reflect discrete classes/categories concept. Moreover, to handle it well we need to have some tolerance when converting to a discretized color. As such, the recommendation is to have API surface still use google.type.Color while internally IntColor is used. */
		color: Option[Schema.Color] = None,
		intColor: Option[Schema.XPSColorMapIntColor] = None,
	  /** Should be used during preprocessing. */
		displayName: Option[String] = None
	)
	
	case class XPSTablesClassificationMetricsCurveMetrics(
	  /** The area under receiver operating characteristic curve. */
		aucRoc: Option[BigDecimal] = None,
	  /** The position threshold value used to compute the metrics. */
		positionThreshold: Option[Int] = None,
	  /** The area under the precision-recall curve. */
		aucPr: Option[BigDecimal] = None,
	  /** The Log loss metric. */
		logLoss: Option[BigDecimal] = None,
	  /** The CATEGORY row value (for ARRAY unnested) the curve metrics are for. */
		value: Option[String] = None,
	  /** Metrics that have confidence thresholds. Precision-recall curve and ROC curve can be derived from them. */
		confidenceMetricsEntries: Option[List[Schema.XPSTablesConfidenceMetricsEntry]] = None
	)
	
	case class XPSConfusionMatrixRow(
	  /** Same as above except intended to represent other counts (for e.g. for segmentation this is pixel count). NOTE(params): Only example_count or count is set (oneoff does not support repeated fields unless they are embedded inside another message). */
		count: Option[List[String]] = None,
	  /** Value of the specific cell in the confusion matrix. The number of values each row has (i.e. the length of the row) is equal to the length of the annotation_spec_id_token field. */
		exampleCount: Option[List[Int]] = None
	)
	
	case class XPSTablesConfidenceMetricsEntry(
	  /** True negative count. */
		trueNegativeCount: Option[String] = None,
	  /** Precision = #true positives / (#true positives + #false positives). */
		precision: Option[BigDecimal] = None,
	  /** The harmonic mean of recall and precision. (2 &#42; precision &#42; recall) / (precision + recall) */
		f1Score: Option[BigDecimal] = None,
	  /** The confidence threshold value used to compute the metrics. */
		confidenceThreshold: Option[BigDecimal] = None,
	  /** TPR = #true positives / (#true positives + #false negatvies) */
		truePositiveRate: Option[BigDecimal] = None,
	  /** Recall = #true positives / (#true positives + #false negatives). */
		recall: Option[BigDecimal] = None,
	  /** False negative count. */
		falseNegativeCount: Option[String] = None,
	  /** False positive count. */
		falsePositiveCount: Option[String] = None,
	  /** FPR = #false positives / (#false positives + #true negatives) */
		falsePositiveRate: Option[BigDecimal] = None,
	  /** True positive count. */
		truePositiveCount: Option[String] = None
	)
	
	case class XPSResponseExplanationMetadata(
	  /** Metadata of the output. */
		outputs: Option[Map[String, Schema.XPSResponseExplanationMetadataOutputMetadata]] = None,
	  /** Metadata of the input. */
		inputs: Option[Map[String, Schema.XPSResponseExplanationMetadataInputMetadata]] = None
	)
	
	case class XPSImageObjectDetectionEvaluationMetrics(
	  /** The single metric for bounding boxes evaluation: the mean_average_precision averaged over all bounding_box_metrics_entries. */
		boundingBoxMeanAveragePrecision: Option[BigDecimal] = None,
	  /** The bounding boxes match metrics for each Intersection-over-union threshold 0.05,0.10,...,0.95,0.96,0.97,0.98,0.99 and each label confidence threshold 0.05,0.10,...,0.95,0.96,0.97,0.98,0.99 pair. */
		boundingBoxMetricsEntries: Option[List[Schema.XPSBoundingBoxMetricsEntry]] = None,
	  /** The total number of bounding boxes (i.e. summed over all images) the ground truth used to create this evaluation had. */
		evaluatedBoundingBoxCount: Option[Int] = None
	)
	
	object XPSDataErrors {
		enum ErrorTypeEnum extends Enum[ErrorTypeEnum] { case ERROR_TYPE_UNSPECIFIED, UNSUPPORTED_AUDIO_FORMAT, FILE_EXTENSION_MISMATCH_WITH_AUDIO_FORMAT, FILE_TOO_LARGE, MISSING_TRANSCRIPTION }
	}
	case class XPSDataErrors(
	  /** Number of records having errors associated with the enum. */
		count: Option[Int] = None,
	  /** Type of the error. */
		errorType: Option[Schema.XPSDataErrors.ErrorTypeEnum] = None
	)
	
	case class XPSTableSpec(
	  /** Mapping from column id to column spec. */
		columnSpecs: Option[Map[String, Schema.XPSColumnSpec]] = None,
	  /** The number of valid rows. */
		validRowCount: Option[String] = None,
	  /** The number of rows in the table. */
		rowCount: Option[String] = None,
	  /** The total size of imported data of the table. */
		importedDataSizeInBytes: Option[String] = None,
	  /** The id of the time column. */
		timeColumnId: Option[Int] = None
	)
	
	object XPSImageSegmentationTrainResponse {
		enum StopReasonEnum extends Enum[StopReasonEnum] { case TRAIN_STOP_REASON_UNSPECIFIED, TRAIN_STOP_REASON_BUDGET_REACHED, TRAIN_STOP_REASON_MODEL_CONVERGED, TRAIN_STOP_REASON_MODEL_EARLY_STOPPED }
	}
	case class XPSImageSegmentationTrainResponse(
	  /** ## The fields below are only populated under uCAIP request scope. Model artifact spec stores and model gcs pathes and related metadata */
		modelArtifactSpec: Option[Schema.XPSImageModelArtifactSpec] = None,
	  /** Stop reason for training job, e.g. 'TRAIN_BUDGET_REACHED', 'MODEL_CONVERGED'. */
		stopReason: Option[Schema.XPSImageSegmentationTrainResponse.StopReasonEnum] = None,
	  /** Color map of the model. */
		colorMaps: Option[List[Schema.XPSColorMap]] = None,
	  /** The actual train cost of creating this model, expressed in node seconds, i.e. 3,600 value in this field means 1 node hour. */
		trainCostNodeSeconds: Option[String] = None,
	  /** NOTE: These fields are not used/needed in EAP but will be set later. */
		exportModelSpec: Option[Schema.XPSImageExportModelSpec] = None,
		modelServingSpec: Option[Schema.XPSImageModelServingSpec] = None
	)
	
	case class ClassifyTextResponse(
	  /** Whether the language is officially supported. The API may still return a response when the language is not supported, but it is on a best effort basis. */
		languageSupported: Option[Boolean] = None,
	  /** The language of the text, which will be the same as the language specified in the request or, if not specified, the automatically-detected language. See Document.language_code field for more details. */
		languageCode: Option[String] = None,
	  /** Categories representing the input document. */
		categories: Option[List[Schema.ClassificationCategory]] = None
	)
	
	case class XPSCommonStats(
		distinctValueCount: Option[String] = None,
		validValueCount: Option[String] = None,
		nullValueCount: Option[String] = None
	)
	
	case class XPSTranslationPreprocessResponse(
	  /** Total valid example count. */
		validExampleCount: Option[String] = None,
	  /** Total example count parsed. */
		parsedExampleCount: Option[String] = None
	)
	
	case class XPSBoundingBoxMetricsEntry(
	  /** Metrics for each label-match confidence_threshold from 0.05,0.10,...,0.95,0.96,0.97,0.98,0.99. */
		confidenceMetricsEntries: Option[List[Schema.XPSBoundingBoxMetricsEntryConfidenceMetricsEntry]] = None,
	  /** The intersection-over-union threshold value used to compute this metrics entry. */
		iouThreshold: Option[BigDecimal] = None,
	  /** The mean average precision. */
		meanAveragePrecision: Option[BigDecimal] = None
	)
	
	case class XPSTablesTrainResponse(
	  /** Sample rows from the dataset this model was trained. */
		predictionSampleRows: Option[List[Schema.XPSRow]] = None,
	  /** The actual training cost of the model, expressed in milli node hours, i.e. 1,000 value in this field means 1 node hour. Guaranteed to not exceed the train budget. */
		trainCostMilliNodeHours: Option[String] = None,
	  /** Output only. Auxiliary information for each of the input_feature_column_specs, with respect to this particular model. */
		tablesModelColumnInfo: Option[List[Schema.XPSTablesModelColumnInfo]] = None,
		modelStructure: Option[Schema.XPSTablesModelStructure] = None
	)
	
	case class XPSBoundingBoxMetricsEntryConfidenceMetricsEntry(
	  /** The harmonic mean of recall and precision. */
		f1Score: Option[BigDecimal] = None,
	  /** The confidence threshold value used to compute the metrics. */
		confidenceThreshold: Option[BigDecimal] = None,
	  /** Recall for the given confidence threshold. */
		recall: Option[BigDecimal] = None,
	  /** Precision for the given confidence threshold. */
		precision: Option[BigDecimal] = None
	)
	
	case class InfraUsage(
	  /** Aggregated gpu metrics since requested start_time. */
		gpuMetrics: Option[List[Schema.GpuMetric]] = None,
	  /** Aggregated ram metrics since requested start_time. */
		ramMetrics: Option[List[Schema.RamMetric]] = None,
	  /** Aggregated core metrics since requested start_time. */
		cpuMetrics: Option[List[Schema.CpuMetric]] = None,
	  /** Aggregated persistent disk metrics since requested start_time. */
		diskMetrics: Option[List[Schema.DiskMetric]] = None,
	  /** Aggregated tpu metrics since requested start_time. */
		tpuMetrics: Option[List[Schema.TpuMetric]] = None
	)
}
