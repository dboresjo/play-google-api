package com.boresjo.play.api.google.documentai

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleCloudDocumentaiUiv1beta3ExportProcessorVersionMetadata(
	  /** The common metadata about the operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3DocumentId(
	  /** Points to a specific revision of the document if set. */
		revisionRef: Option[Schema.GoogleCloudDocumentaiUiv1beta3RevisionRef] = None,
	  /** A document id within unmanaged dataset. */
		unmanagedDocId: Option[Schema.GoogleCloudDocumentaiUiv1beta3DocumentIdUnmanagedDocumentId] = None,
	  /** A document id within user-managed Cloud Storage. */
		gcsManagedDocId: Option[Schema.GoogleCloudDocumentaiUiv1beta3DocumentIdGCSManagedDocumentId] = None
	)
	
	case class GoogleCloudDocumentaiV1ProcessResponse(
	  /** The status of human review on the processed document. */
		humanReviewStatus: Option[Schema.GoogleCloudDocumentaiV1HumanReviewStatus] = None,
	  /** The document payload, will populate fields based on the processor's behavior. */
		document: Option[Schema.GoogleCloudDocumentaiV1Document] = None
	)
	
	case class GoogleCloudDocumentaiV1ReviewDocumentOperationMetadata(
	  /** The Crowd Compute question ID. */
		questionId: Option[String] = None,
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3EvaluateProcessorVersionMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3UndeployProcessorVersionMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3DeployProcessorVersionResponse(
	
	)
	
	object GoogleCloudDocumentaiV1beta3Dataset {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, UNINITIALIZED, INITIALIZING, INITIALIZED }
	}
	case class GoogleCloudDocumentaiV1beta3Dataset(
	  /** Output only. Reserved for future use. */
		satisfiesPzi: Option[Boolean] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Optional. Deprecated. Warehouse-based dataset configuration is not supported. */
		documentWarehouseConfig: Option[Schema.GoogleCloudDocumentaiV1beta3DatasetDocumentWarehouseConfig] = None,
	  /** Optional. A lightweight indexing source with low latency and high reliability, but lacking advanced features like CMEK and content-based search. */
		spannerIndexingConfig: Option[Schema.GoogleCloudDocumentaiV1beta3DatasetSpannerIndexingConfig] = None,
	  /** Required. State of the dataset. Ignored when updating dataset. */
		state: Option[Schema.GoogleCloudDocumentaiV1beta3Dataset.StateEnum] = None,
	  /** Optional. User-managed Cloud Storage dataset configuration. Use this configuration if the dataset documents are stored under a user-managed Cloud Storage location. */
		gcsManagedConfig: Option[Schema.GoogleCloudDocumentaiV1beta3DatasetGCSManagedConfig] = None,
	  /** Dataset resource name. Format: `projects/{project}/locations/{location}/processors/{processor}/dataset` */
		name: Option[String] = None,
	  /** Optional. Unmanaged dataset configuration. Use this configuration if the dataset documents are managed by the document service internally (not user-managed). */
		unmanagedDatasetConfig: Option[Schema.GoogleCloudDocumentaiV1beta3DatasetUnmanagedDatasetConfig] = None
	)
	
	case class GoogleCloudDocumentaiV1TrainProcessorVersionRequestFoundationModelTuningOptions(
	  /** Optional. The number of steps to run for model tuning. Valid values are between 1 and 400. If not provided, recommended steps will be used. */
		trainSteps: Option[Int] = None,
	  /** Optional. The multiplier to apply to the recommended learning rate. Valid values are between 0.1 and 10. If not provided, recommended learning rate will be used. */
		learningRateMultiplier: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentTextAnchorTextSegment(
	  /** TextSegment start UTF-8 char index in the Document.text. */
		startIndex: Option[String] = None,
	  /** TextSegment half open end UTF-8 char index in the Document.text. */
		endIndex: Option[String] = None
	)
	
	object GoogleCloudDocumentaiUiv1beta3ResyncDatasetMetadataDatasetResyncStatus {
		enum DatasetInconsistencyTypeEnum extends Enum[DatasetInconsistencyTypeEnum] { case DATASET_INCONSISTENCY_TYPE_UNSPECIFIED, DATASET_INCONSISTENCY_TYPE_NO_STORAGE_MARKER }
	}
	case class GoogleCloudDocumentaiUiv1beta3ResyncDatasetMetadataDatasetResyncStatus(
	  /** The type of the inconsistency of the dataset. */
		datasetInconsistencyType: Option[Schema.GoogleCloudDocumentaiUiv1beta3ResyncDatasetMetadataDatasetResyncStatus.DatasetInconsistencyTypeEnum] = None,
	  /** The status of resyncing the dataset with regards to the detected inconsistency. Empty if ResyncDatasetRequest.validate_only is `true`. */
		status: Option[Schema.GoogleRpcStatus] = None
	)
	
	case class GoogleCloudDocumentaiV1EnableProcessorMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentRevision(
	  /** The revisions that this revision is based on. This can include one or more parent (when documents are merged.) This field represents the index into the `revisions` field. */
		parent: Option[List[Int]] = None,
	  /** If the change was made by a person specify the name or id of that person. */
		agent: Option[String] = None,
	  /** The time that the revision was created, internally generated by doc proto storage at the time of create. */
		createTime: Option[String] = None,
	  /** Id of the revision, internally generated by doc proto storage. Unique within the context of the document. */
		id: Option[String] = None,
	  /** Human Review information of this revision. */
		humanReview: Option[Schema.GoogleCloudDocumentaiV1DocumentRevisionHumanReview] = None,
	  /** The revisions that this revision is based on. Must include all the ids that have anything to do with this revision - eg. there are `provenance.parent.revision` fields that index into this field. */
		parentIds: Option[List[String]] = None,
	  /** If the annotation was made by processor identify the processor by its resource name. */
		processor: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1UndeployProcessorVersionMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3ImportProcessorVersionResponse(
	  /** The destination processor version name. */
		processorVersion: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1ProcessRequest(
	  /** A raw document on Google Cloud Storage. */
		gcsDocument: Option[Schema.GoogleCloudDocumentaiV1GcsDocument] = None,
	  /** Optional. The labels with user-defined metadata for the request. Label keys and values can be no longer than 63 characters (Unicode codepoints) and can only contain lowercase letters, numeric characters, underscores, and dashes. International characters are allowed. Label values are optional. Label keys must start with a letter. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Option to remove images from the document. */
		imagelessMode: Option[Boolean] = None,
	  /** Whether human review should be skipped for this request. Default to `false`. */
		skipHumanReview: Option[Boolean] = None,
	  /** An inline document proto. */
		inlineDocument: Option[Schema.GoogleCloudDocumentaiV1Document] = None,
	  /** A raw document content (bytes). */
		rawDocument: Option[Schema.GoogleCloudDocumentaiV1RawDocument] = None,
	  /** Inference-time options for the process API */
		processOptions: Option[Schema.GoogleCloudDocumentaiV1ProcessOptions] = None,
	  /** Specifies which fields to include in the ProcessResponse.document output. Only supports top-level document and pages field, so it must be in the form of `{document_field_name}` or `pages.{page_field_name}`. */
		fieldMask: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3BatchUpdateDocumentsMetadataIndividualBatchUpdateStatus(
	  /** The status of updating the document in storage. */
		status: Option[Schema.GoogleRpcStatus] = None,
	  /** The document id of the document. */
		documentId: Option[Schema.GoogleCloudDocumentaiUiv1beta3DocumentId] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageTable(
	  /** Body rows of the table. */
		bodyRows: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageTableTableRow]] = None,
	  /** Header rows of the table. */
		headerRows: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageTableTableRow]] = None,
	  /** The history of this table. */
		provenance: Option[Schema.GoogleCloudDocumentaiV1DocumentProvenance] = None,
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None,
	  /** Layout for Table. */
		layout: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None
	)
	
	object GoogleCloudDocumentaiV1beta3CommonOperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RUNNING, CANCELLING, SUCCEEDED, FAILED, CANCELLED }
	}
	case class GoogleCloudDocumentaiV1beta3CommonOperationMetadata(
	  /** The creation time of the operation. */
		createTime: Option[String] = None,
	  /** The state of the operation. */
		state: Option[Schema.GoogleCloudDocumentaiV1beta3CommonOperationMetadata.StateEnum] = None,
	  /** The last update time of the operation. */
		updateTime: Option[String] = None,
	  /** A message providing more details about the current state of processing. */
		stateMessage: Option[String] = None,
	  /** A related resource to this operation. */
		resource: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3BatchProcessResponse(
	
	)
	
	case class GoogleCloudDocumentaiV1SetDefaultProcessorVersionMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageTableTableRow(
	  /** Cells that make up this row. */
		cells: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageTableTableCell]] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3ResyncDatasetResponse(
	
	)
	
	case class GoogleCloudDocumentaiV1DeployProcessorVersionRequest(
	
	)
	
	case class GoogleCloudDocumentaiV1beta3DatasetSpannerIndexingConfig(
	
	)
	
	case class GoogleCloudDocumentaiV1DocumentChunkedDocumentChunkChunkPageHeader(
	  /** Page span of the header. */
		pageSpan: Option[Schema.GoogleCloudDocumentaiV1DocumentChunkedDocumentChunkChunkPageSpan] = None,
	  /** Header in text format. */
		text: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3DocumentIdUnmanagedDocumentId(
	  /** Required. The id of the document. */
		docId: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentDocumentLayout(
	  /** List of blocks in the document. */
		blocks: Option[List[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlock]] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3UpdateLabelerPoolOperationMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3ResyncDatasetMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None,
	  /** The list of dataset resync statuses. Not checked when ResyncDatasetRequest.dataset_documents is specified. */
		datasetResyncStatuses: Option[List[Schema.GoogleCloudDocumentaiUiv1beta3ResyncDatasetMetadataDatasetResyncStatus]] = None,
	  /** The list of document resync statuses. The same document could have multiple `individual_document_resync_statuses` if it has multiple inconsistencies. */
		individualDocumentResyncStatuses: Option[List[Schema.GoogleCloudDocumentaiUiv1beta3ResyncDatasetMetadataIndividualDocumentResyncStatus]] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3BatchMoveDocumentsMetadataIndividualBatchMoveStatus(
	  /** The status of moving the document. */
		status: Option[Schema.GoogleRpcStatus] = None,
	  /** The document id of the document. */
		documentId: Option[Schema.GoogleCloudDocumentaiUiv1beta3DocumentId] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3EnableProcessorResponse(
	
	)
	
	object GoogleCloudDocumentaiV1CommonOperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RUNNING, CANCELLING, SUCCEEDED, FAILED, CANCELLED }
	}
	case class GoogleCloudDocumentaiV1CommonOperationMetadata(
	  /** The creation time of the operation. */
		createTime: Option[String] = None,
	  /** The last update time of the operation. */
		updateTime: Option[String] = None,
	  /** The state of the operation. */
		state: Option[Schema.GoogleCloudDocumentaiV1CommonOperationMetadata.StateEnum] = None,
	  /** A message providing more details about the current state of processing. */
		stateMessage: Option[String] = None,
	  /** A related resource to this operation. */
		resource: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageLine(
	  /** Layout for Line. */
		layout: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None,
	  /** The history of this annotation. */
		provenance: Option[Schema.GoogleCloudDocumentaiV1DocumentProvenance] = None,
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3ImportDocumentsResponse(
	
	)
	
	case class GoogleCloudDocumentaiV1DeleteProcessorMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3CreateLabelerPoolOperationMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1ProcessorVersionGenAiModelInfo(
	  /** Information for a custom Generative AI model created by the user. */
		customGenAiModelInfo: Option[Schema.GoogleCloudDocumentaiV1ProcessorVersionGenAiModelInfoCustomGenAiModelInfo] = None,
	  /** Information for a pretrained Google-managed foundation model. */
		foundationGenAiModelInfo: Option[Schema.GoogleCloudDocumentaiV1ProcessorVersionGenAiModelInfoFoundationGenAiModelInfo] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3DisableProcessorMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1GcsDocument(
	  /** An IANA MIME type (RFC6838) of the content. */
		mimeType: Option[String] = None,
	  /** The Cloud Storage object uri. */
		gcsUri: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentSchemaEntityType(
	  /** Description the nested structure, or composition of an entity. */
		properties: Option[List[Schema.GoogleCloudDocumentaiV1DocumentSchemaEntityTypeProperty]] = None,
	  /** The entity type that this type is derived from. For now, one and only one should be set. */
		baseTypes: Option[List[String]] = None,
	  /** If specified, lists all the possible values for this entity. This should not be more than a handful of values. If the number of values is >10 or could change frequently use the `EntityType.value_ontology` field and specify a list of all possible values in a value ontology file. */
		enumValues: Option[Schema.GoogleCloudDocumentaiV1DocumentSchemaEntityTypeEnumValues] = None,
	  /** Name of the type. It must be unique within the schema file and cannot be a "Common Type". The following naming conventions are used: - Use `snake_casing`. - Name matching is case-sensitive. - Maximum 64 characters. - Must start with a letter. - Allowed characters: ASCII letters `[a-z0-9_-]`. (For backward compatibility internal infrastructure and tooling can handle any ascii character.) - The `/` is sometimes used to denote a property of a type. For example `line_item/amount`. This convention is deprecated, but will still be honored for backward compatibility. */
		name: Option[String] = None,
	  /** User defined name for the type. */
		displayName: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageFormField(
	  /** Created for Labeling UI to export key text. If corrections were made to the text identified by the `field_name.text_anchor`, this field will contain the correction. */
		correctedKeyText: Option[String] = None,
	  /** Layout for the FormField value. */
		fieldValue: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None,
	  /** Layout for the FormField name. e.g. `Address`, `Email`, `Grand total`, `Phone number`, etc. */
		fieldName: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None,
	  /** A list of detected languages for name together with confidence. */
		nameDetectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None,
	  /** Created for Labeling UI to export value text. If corrections were made to the text identified by the `field_value.text_anchor`, this field will contain the correction. */
		correctedValueText: Option[String] = None,
	  /** A list of detected languages for value together with confidence. */
		valueDetectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None,
	  /** If the value is non-textual, this field represents the type. Current valid values are: - blank (this indicates the `field_value` is normal text) - `unfilled_checkbox` - `filled_checkbox` */
		valueType: Option[String] = None,
	  /** The history of this annotation. */
		provenance: Option[Schema.GoogleCloudDocumentaiV1DocumentProvenance] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentProvenanceParent(
	  /** The index of the index into current revision's parent_ids list. */
		revision: Option[Int] = None,
	  /** The index of the parent item in the corresponding item list (eg. list of entities, properties within entities, etc.) in the parent revision. */
		index: Option[Int] = None,
	  /** The id of the parent provenance. */
		id: Option[Int] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3DeployProcessorVersionResponse(
	
	)
	
	case class GoogleCloudDocumentaiUiv1beta3ImportDocumentsMetadataImportConfigValidationResult(
	  /** The source Cloud Storage URI specified in the import config. */
		inputGcsSource: Option[String] = None,
	  /** The validation status of import config. */
		status: Option[Schema.GoogleRpcStatus] = None
	)
	
	object GoogleCloudDocumentaiV1beta3BatchProcessMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, WAITING, RUNNING, SUCCEEDED, CANCELLING, CANCELLED, FAILED }
	}
	case class GoogleCloudDocumentaiV1beta3BatchProcessMetadata(
	  /** The last update time of the operation. */
		updateTime: Option[String] = None,
	  /** A message providing more details about the current state of processing. For example, the error message if the operation is failed. */
		stateMessage: Option[String] = None,
	  /** The creation time of the operation. */
		createTime: Option[String] = None,
	  /** The state of the current batch processing. */
		state: Option[Schema.GoogleCloudDocumentaiV1beta3BatchProcessMetadata.StateEnum] = None,
	  /** The list of response details of each document. */
		individualProcessStatuses: Option[List[Schema.GoogleCloudDocumentaiV1beta3BatchProcessMetadataIndividualProcessStatus]] = None
	)
	
	case class GoogleCloudDocumentaiV1ProcessOptionsLayoutConfigChunkingConfig(
	  /** Optional. The chunk sizes to use when splitting documents, in order of level. */
		chunkSize: Option[Int] = None,
	  /** Optional. Whether or not to include ancestor headings when splitting. */
		includeAncestorHeadings: Option[Boolean] = None
	)
	
	object GoogleCloudDocumentaiV1EvaluationMultiConfidenceMetrics {
		enum MetricsTypeEnum extends Enum[MetricsTypeEnum] { case METRICS_TYPE_UNSPECIFIED, AGGREGATE }
	}
	case class GoogleCloudDocumentaiV1EvaluationMultiConfidenceMetrics(
	  /** The Estimated Calibration Error (ECE) of the confidence of the predicted entities. */
		estimatedCalibrationError: Option[BigDecimal] = None,
	  /** The calculated area under the precision recall curve (AUPRC), computed by integrating over all confidence thresholds. */
		auprc: Option[BigDecimal] = None,
	  /** Metrics across confidence levels with fuzzy matching enabled. */
		confidenceLevelMetrics: Option[List[Schema.GoogleCloudDocumentaiV1EvaluationConfidenceLevelMetrics]] = None,
	  /** The ECE for the predicted entities with fuzzy matching disabled, i.e., exact matching only. */
		estimatedCalibrationErrorExact: Option[BigDecimal] = None,
	  /** The metrics type for the label. */
		metricsType: Option[Schema.GoogleCloudDocumentaiV1EvaluationMultiConfidenceMetrics.MetricsTypeEnum] = None,
	  /** Metrics across confidence levels with only exact matching. */
		confidenceLevelMetricsExact: Option[List[Schema.GoogleCloudDocumentaiV1EvaluationConfidenceLevelMetrics]] = None,
	  /** The AUPRC for metrics with fuzzy matching disabled, i.e., exact matching only. */
		auprcExact: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDocumentaiV1ProcessorVersionDeprecationInfo(
	  /** The time at which this processor version will be deprecated. */
		deprecationTime: Option[String] = None,
	  /** If set, the processor version that will be used as a replacement. */
		replacementProcessorVersion: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3ImportProcessorVersionMetadata(
	  /** The basic metadata for the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3BatchUpdateDocumentsResponse(
	
	)
	
	case class GoogleCloudDocumentaiV1UndeployProcessorVersionResponse(
	
	)
	
	case class GoogleCloudDocumentaiV1DocumentOutputConfigGcsOutputConfigShardingConfig(
	  /** The number of pages per shard. */
		pagesPerShard: Option[Int] = None,
	  /** The number of overlapping pages between consecutive shards. */
		pagesOverlap: Option[Int] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentSchema(
	  /** Description of the schema. */
		description: Option[String] = None,
	  /** Display name to show to users. */
		displayName: Option[String] = None,
	  /** Entity types of the schema. */
		entityTypes: Option[List[Schema.GoogleCloudDocumentaiV1DocumentSchemaEntityType]] = None,
	  /** Metadata of the schema. */
		metadata: Option[Schema.GoogleCloudDocumentaiV1DocumentSchemaMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1TrainProcessorVersionMetadataDatasetValidation(
	  /** Error information pertaining to specific documents. A maximum of 10 document errors will be returned. Any document with errors will not be used throughout training. */
		documentErrors: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Error information for the dataset as a whole. A maximum of 10 dataset errors will be returned. A single dataset error is terminal for training. */
		datasetErrors: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** The total number of dataset errors. */
		datasetErrorCount: Option[Int] = None,
	  /** The total number of document errors. */
		documentErrorCount: Option[Int] = None
	)
	
	case class GoogleRpcStatus(
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3AutoLabelDocumentsMetadataIndividualAutoLabelStatus(
	  /** The document id of the auto-labeled document. This will replace the gcs_uri. */
		documentId: Option[Schema.GoogleCloudDocumentaiUiv1beta3DocumentId] = None,
	  /** The status of the document auto-labeling. */
		status: Option[Schema.GoogleRpcStatus] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentStyle(
	  /** [Text decoration](https://www.w3schools.com/cssref/pr_text_text-decoration.asp). Follows CSS standard.  */
		textDecoration: Option[String] = None,
	  /** Font family such as `Arial`, `Times New Roman`. https://www.w3schools.com/cssref/pr_font_font-family.asp */
		fontFamily: Option[String] = None,
	  /** Text color. */
		color: Option[Schema.GoogleTypeColor] = None,
	  /** Text background color. */
		backgroundColor: Option[Schema.GoogleTypeColor] = None,
	  /** Font size. */
		fontSize: Option[Schema.GoogleCloudDocumentaiV1DocumentStyleFontSize] = None,
	  /** [Font weight](https://www.w3schools.com/cssref/pr_font_weight.asp). Possible values are `normal`, `bold`, `bolder`, and `lighter`. */
		fontWeight: Option[String] = None,
	  /** Text anchor indexing into the Document.text. */
		textAnchor: Option[Schema.GoogleCloudDocumentaiV1DocumentTextAnchor] = None,
	  /** [Text style](https://www.w3schools.com/cssref/pr_font_font-style.asp). Possible values are `normal`, `italic`, and `oblique`. */
		textStyle: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageVisualElement(
	  /** Type of the VisualElement. */
		`type`: Option[String] = None,
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None,
	  /** Layout for VisualElement. */
		layout: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None
	)
	
	case class GoogleTypeColor(
	  /** The amount of green in the color as a value in the interval [0, 1]. */
		green: Option[BigDecimal] = None,
	  /** The amount of red in the color as a value in the interval [0, 1]. */
		red: Option[BigDecimal] = None,
	  /** The amount of blue in the color as a value in the interval [0, 1]. */
		blue: Option[BigDecimal] = None,
	  /** The fraction of this color that should be applied to the pixel. That is, the final pixel color is defined by the equation: `pixel color = alpha &#42; (this color) + (1.0 - alpha) &#42; (background color)` This means that a value of 1.0 corresponds to a solid color, whereas a value of 0.0 corresponds to a completely transparent color. This uses a wrapper message rather than a simple float scalar so that it is possible to distinguish between a default value and the value being unset. If omitted, this color object is rendered as a solid color (as if the alpha value had been explicitly given a value of 1.0). */
		alpha: Option[BigDecimal] = None
	)
	
	case class GoogleTypePostalAddress(
	  /** Optional. Generally refers to the city/town portion of the address. Examples: US city, IT comune, UK post town. In regions of the world where localities are not well defined or do not fit into this structure well, leave locality empty and use address_lines. */
		locality: Option[String] = None,
	  /** Optional. The recipient at the address. This field may, under certain circumstances, contain multiline information. For example, it might contain "care of" information. */
		recipients: Option[List[String]] = None,
	  /** The schema revision of the `PostalAddress`. This must be set to 0, which is the latest revision. All new revisions &#42;&#42;must&#42;&#42; be backward compatible with old revisions. */
		revision: Option[Int] = None,
	  /** Required. CLDR region code of the country/region of the address. This is never inferred and it is up to the user to ensure the value is correct. See https://cldr.unicode.org/ and https://www.unicode.org/cldr/charts/30/supplemental/territory_information.html for details. Example: "CH" for Switzerland. */
		regionCode: Option[String] = None,
	  /** Optional. Postal code of the address. Not all countries use or require postal codes to be present, but where they are used, they may trigger additional validation with other parts of the address (e.g. state/zip validation in the U.S.A.). */
		postalCode: Option[String] = None,
	  /** Optional. The name of the organization at the address. */
		organization: Option[String] = None,
	  /** Unstructured address lines describing the lower levels of an address. Because values in address_lines do not have type information and may sometimes contain multiple values in a single field (e.g. "Austin, TX"), it is important that the line order is clear. The order of address lines should be "envelope order" for the country/region of the address. In places where this can vary (e.g. Japan), address_language is used to make it explicit (e.g. "ja" for large-to-small ordering and "ja-Latn" or "en" for small-to-large). This way, the most specific line of an address can be selected based on the language. The minimum permitted structural representation of an address consists of a region_code with all remaining information placed in the address_lines. It would be possible to format such an address very approximately without geocoding, but no semantic reasoning could be made about any of the address components until it was at least partially resolved. Creating an address only containing a region_code and address_lines, and then geocoding is the recommended way to handle completely unstructured addresses (as opposed to guessing which parts of the address should be localities or administrative areas). */
		addressLines: Option[List[String]] = None,
	  /** Optional. Highest administrative subdivision which is used for postal addresses of a country or region. For example, this can be a state, a province, an oblast, or a prefecture. Specifically, for Spain this is the province and not the autonomous community (e.g. "Barcelona" and not "Catalonia"). Many countries don't use an administrative area in postal addresses. E.g. in Switzerland this should be left unpopulated. */
		administrativeArea: Option[String] = None,
	  /** Optional. Sublocality of the address. For example, this can be neighborhoods, boroughs, districts. */
		sublocality: Option[String] = None,
	  /** Optional. Additional, country-specific, sorting code. This is not used in most regions. Where it is used, the value is either a string like "CEDEX", optionally followed by a number (e.g. "CEDEX 7"), or just a number alone, representing the "sector code" (Jamaica), "delivery area indicator" (Malawi) or "post office indicator" (e.g. Côte d'Ivoire). */
		sortingCode: Option[String] = None,
	  /** Optional. BCP-47 language code of the contents of this address (if known). This is often the UI language of the input form or is expected to match one of the languages used in the address' country/region, or their transliterated equivalents. This can affect formatting in certain countries, but is not critical to the correctness of the data and will never affect any validation or other non-formatting related operations. If this value is not known, it should be omitted (rather than specifying a possibly incorrect default). Examples: "zh-Hant", "ja", "ja-Latn", "en". */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentChunkedDocumentChunkChunkPageFooter(
	  /** Footer in text format. */
		text: Option[String] = None,
	  /** Page span of the footer. */
		pageSpan: Option[Schema.GoogleCloudDocumentaiV1DocumentChunkedDocumentChunkChunkPageSpan] = None
	)
	
	case class GoogleCloudDocumentaiV1DisableProcessorResponse(
	
	)
	
	case class GoogleCloudDocumentaiUiv1beta3AutoLabelDocumentsMetadata(
	  /** The list of individual auto-labeling statuses of the dataset documents. */
		individualAutoLabelStatuses: Option[List[Schema.GoogleCloudDocumentaiUiv1beta3AutoLabelDocumentsMetadataIndividualAutoLabelStatus]] = None,
	  /** Total number of the auto-labeling documents. */
		totalDocumentCount: Option[Int] = None,
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentOutputConfig(
	  /** Output config to write the results to Cloud Storage. */
		gcsOutputConfig: Option[Schema.GoogleCloudDocumentaiV1DocumentOutputConfigGcsOutputConfig] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3DeleteProcessorMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3DeleteProcessorVersionMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1Document(
	  /** Placeholder. Relationship among Document.entities. */
		entityRelations: Option[List[Schema.GoogleCloudDocumentaiV1DocumentEntityRelation]] = None,
	  /** Document chunked based on chunking config. */
		chunkedDocument: Option[Schema.GoogleCloudDocumentaiV1DocumentChunkedDocument] = None,
	  /** Parsed layout of the document. */
		documentLayout: Option[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayout] = None,
	  /** An IANA published [media type (MIME type)](https://www.iana.org/assignments/media-types/media-types.xhtml). */
		mimeType: Option[String] = None,
	  /** Placeholder. Revision history of this document. */
		revisions: Option[List[Schema.GoogleCloudDocumentaiV1DocumentRevision]] = None,
	  /** Visual page layout for the Document. */
		pages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPage]] = None,
	  /** Optional. UTF-8 encoded text in reading order from the document. */
		text: Option[String] = None,
	  /** Any error that occurred while processing this document. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** Optional. Inline document content, represented as a stream of bytes. Note: As with all `bytes` fields, protobuffers use a pure binary representation, whereas JSON representations use base64. */
		content: Option[String] = None,
	  /** Placeholder. A list of text corrections made to Document.text. This is usually used for annotating corrections to OCR mistakes. Text changes for a given revision may not overlap with each other. */
		textChanges: Option[List[Schema.GoogleCloudDocumentaiV1DocumentTextChange]] = None,
	  /** Optional. Currently supports Google Cloud Storage URI of the form `gs://bucket_name/object_name`. Object versioning is not supported. For more information, refer to [Google Cloud Storage Request URIs](https://cloud.google.com/storage/docs/reference-uris). */
		uri: Option[String] = None,
	  /** A list of entities detected on Document.text. For document shards, entities in this list may cross shard boundaries. */
		entities: Option[List[Schema.GoogleCloudDocumentaiV1DocumentEntity]] = None,
	  /** Information about the sharding if this document is sharded part of a larger document. If the document is not sharded, this message is not specified. */
		shardInfo: Option[Schema.GoogleCloudDocumentaiV1DocumentShardInfo] = None,
	  /** Styles for the Document.text. */
		textStyles: Option[List[Schema.GoogleCloudDocumentaiV1DocumentStyle]] = None
	)
	
	case class GoogleCloudDocumentaiV1EvaluationMetrics(
	  /** The amount of documents that had an occurrence of this label. */
		totalDocumentsCount: Option[Int] = None,
	  /** The amount of documents with a ground truth occurrence. */
		groundTruthDocumentCount: Option[Int] = None,
	  /** The calculated precision. */
		precision: Option[BigDecimal] = None,
	  /** The amount of documents with a predicted occurrence. */
		predictedDocumentCount: Option[Int] = None,
	  /** The amount of false positives. */
		falsePositivesCount: Option[Int] = None,
	  /** The amount of true positives. */
		truePositivesCount: Option[Int] = None,
	  /** The calculated recall. */
		recall: Option[BigDecimal] = None,
	  /** The amount of occurrences in ground truth documents. */
		groundTruthOccurrencesCount: Option[Int] = None,
	  /** The amount of occurrences in predicted documents. */
		predictedOccurrencesCount: Option[Int] = None,
	  /** The calculated f1 score. */
		f1Score: Option[BigDecimal] = None,
	  /** The amount of false negatives. */
		falseNegativesCount: Option[Int] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3SampleDocumentsResponse(
	  /** The status of sampling documents in training split. */
		sampleTrainingStatus: Option[Schema.GoogleRpcStatus] = None,
	  /** The result of the sampling process. */
		selectedDocuments: Option[List[Schema.GoogleCloudDocumentaiUiv1beta3SampleDocumentsResponseSelectedDocument]] = None,
	  /** The status of sampling documents in test split. */
		sampleTestStatus: Option[Schema.GoogleRpcStatus] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3SampleDocumentsResponseSelectedDocument(
	  /** An internal identifier for document. */
		documentId: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentChunkedDocumentChunkChunkPageSpan(
	  /** Page where chunk starts in the document. */
		pageStart: Option[Int] = None,
	  /** Page where chunk ends in the document. */
		pageEnd: Option[Int] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3GcsPrefix(
	  /** The URI prefix. */
		gcsUriPrefix: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentStyleFontSize(
	  /** Font size for the text. */
		size: Option[BigDecimal] = None,
	  /** Unit for the font size. Follows CSS naming (such as `in`, `px`, and `pt`). */
		unit: Option[String] = None
	)
	
	object GoogleCloudDocumentaiUiv1beta3RevisionRef {
		enum RevisionCaseEnum extends Enum[RevisionCaseEnum] { case REVISION_CASE_UNSPECIFIED, LATEST_HUMAN_REVIEW, LATEST_TIMESTAMP, BASE_OCR_REVISION }
	}
	case class GoogleCloudDocumentaiUiv1beta3RevisionRef(
	  /** Reads the revision given by the id. */
		revisionId: Option[String] = None,
	  /** Reads the revision generated by the processor version. The format takes the full resource name of processor version. `projects/{project}/locations/{location}/processors/{processor}/processorVersions/{processorVersion}` */
		latestProcessorVersion: Option[String] = None,
	  /** Reads the revision by the predefined case. */
		revisionCase: Option[Schema.GoogleCloudDocumentaiUiv1beta3RevisionRef.RevisionCaseEnum] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3BatchDeleteDocumentsResponse(
	
	)
	
	case class GoogleCloudDocumentaiV1BoundingPoly(
	  /** The bounding polygon vertices. */
		vertices: Option[List[Schema.GoogleCloudDocumentaiV1Vertex]] = None,
	  /** The bounding polygon normalized vertices. */
		normalizedVertices: Option[List[Schema.GoogleCloudDocumentaiV1NormalizedVertex]] = None
	)
	
	case class GoogleCloudDocumentaiV1OcrConfigPremiumFeatures(
	  /** Turn on selection mark detector in OCR engine. Only available in OCR 2.0 (and later) processors. */
		enableSelectionMarkDetection: Option[Boolean] = None,
	  /** Turn on the model that can extract LaTeX math formulas. */
		enableMathOcr: Option[Boolean] = None,
	  /** Turn on font identification model and return font style information. */
		computeStyleInfo: Option[Boolean] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3ExportProcessorVersionResponse(
	  /** The Cloud Storage URI containing the output artifacts. */
		gcsUri: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3DatasetUnmanagedDatasetConfig(
	
	)
	
	case class GoogleCloudDocumentaiV1beta3ImportDocumentsMetadata(
	  /** Validation statuses of the batch documents import config. */
		importConfigValidationResults: Option[List[Schema.GoogleCloudDocumentaiV1beta3ImportDocumentsMetadataImportConfigValidationResult]] = None,
	  /** The list of response details of each document. */
		individualImportStatuses: Option[List[Schema.GoogleCloudDocumentaiV1beta3ImportDocumentsMetadataIndividualImportStatus]] = None,
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1beta3CommonOperationMetadata] = None,
	  /** Total number of the documents that are qualified for importing. */
		totalDocumentCount: Option[Int] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutListBlock(
	  /** List entries that constitute a list block. */
		listEntries: Option[List[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutListEntry]] = None,
	  /** Type of the list_entries (if exist). Available options are `ordered` and `unordered`. */
		`type`: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageDetectedBarcode(
	  /** Layout for DetectedBarcode. */
		layout: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None,
	  /** Detailed barcode information of the DetectedBarcode. */
		barcode: Option[Schema.GoogleCloudDocumentaiV1Barcode] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3SampleDocumentsMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutTableBlock(
	  /** Header rows at the top of the table. */
		headerRows: Option[List[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutTableRow]] = None,
	  /** Body rows containing main table content. */
		bodyRows: Option[List[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutTableRow]] = None,
	  /** Table caption/title. */
		caption: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1OcrConfig(
	  /** Turn on font identification model and return font style information. Deprecated, use PremiumFeatures.compute_style_info instead. */
		computeStyleInfo: Option[Boolean] = None,
	  /** Enables intelligent document quality scores after OCR. Can help with diagnosing why OCR responses are of poor quality for a given input. Adds additional latency comparable to regular OCR to the process call. */
		enableImageQualityScores: Option[Boolean] = None,
	  /** Enables special handling for PDFs with existing text information. Results in better text extraction quality in such PDF inputs. */
		enableNativePdfParsing: Option[Boolean] = None,
	  /** Includes symbol level OCR information if set to true. */
		enableSymbol: Option[Boolean] = None,
	  /** Turn off character box detector in OCR engine. Character box detection is enabled by default in OCR 2.0 (and later) processors. */
		disableCharacterBoxesDetection: Option[Boolean] = None,
	  /** A list of advanced OCR options to further fine-tune OCR behavior. Current valid values are: - `legacy_layout`: a heuristics layout detection algorithm, which serves as an alternative to the current ML-based layout detection algorithm. Customers can choose the best suitable layout algorithm based on their situation. */
		advancedOcrOptions: Option[List[String]] = None,
	  /** Hints for the OCR model. */
		hints: Option[Schema.GoogleCloudDocumentaiV1OcrConfigHints] = None,
	  /** Configurations for premium OCR features. */
		premiumFeatures: Option[Schema.GoogleCloudDocumentaiV1OcrConfigPremiumFeatures] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentEntity(
	  /** Optional. Entities can be nested to form a hierarchical data structure representing the content in the document. */
		properties: Option[List[Schema.GoogleCloudDocumentaiV1DocumentEntity]] = None,
	  /** Optional. The history of this annotation. */
		provenance: Option[Schema.GoogleCloudDocumentaiV1DocumentProvenance] = None,
	  /** Optional. Confidence of detected Schema entity. Range `[0, 1]`. */
		confidence: Option[BigDecimal] = None,
	  /** Optional. Represents the provenance of this entity wrt. the location on the page where it was found. */
		pageAnchor: Option[Schema.GoogleCloudDocumentaiV1DocumentPageAnchor] = None,
	  /** Required. Entity type from a schema e.g. `Address`. */
		`type`: Option[String] = None,
	  /** Optional. Provenance of the entity. Text anchor indexing into the Document.text. */
		textAnchor: Option[Schema.GoogleCloudDocumentaiV1DocumentTextAnchor] = None,
	  /** Optional. Normalized entity value. Absent if the extracted value could not be converted or the type (e.g. address) is not supported for certain parsers. This field is also only populated for certain supported document types. */
		normalizedValue: Option[Schema.GoogleCloudDocumentaiV1DocumentEntityNormalizedValue] = None,
	  /** Optional. Text value of the entity e.g. `1600 Amphitheatre Pkwy`. */
		mentionText: Option[String] = None,
	  /** Optional. Whether the entity will be redacted for de-identification purposes. */
		redacted: Option[Boolean] = None,
	  /** Optional. Canonical id. This will be a unique value in the entity list for this document. */
		id: Option[String] = None,
	  /** Optional. Deprecated. Use `id` field instead. */
		mentionId: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1RawDocument(
	  /** An IANA MIME type (RFC6838) indicating the nature and format of the content. */
		mimeType: Option[String] = None,
	  /** The display name of the document, it supports all Unicode characters except the following: `&#42;`, `?`, `[`, `]`, `%`, `{`, `}`,`'`, `\"`, `,` `~`, `=` and `:` are reserved. If not specified, a default ID is generated. */
		displayName: Option[String] = None,
	  /** Inline document content. */
		content: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3ImportProcessorVersionResponse(
	  /** The destination processor version name. */
		processorVersion: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3BatchDeleteDocumentsMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1beta3CommonOperationMetadata] = None,
	  /** Total number of documents deleting from dataset. */
		totalDocumentCount: Option[Int] = None,
	  /** The list of response details of each document. */
		individualBatchDeleteStatuses: Option[List[Schema.GoogleCloudDocumentaiV1beta3BatchDeleteDocumentsMetadataIndividualBatchDeleteStatus]] = None,
	  /** Total number of documents that failed to be deleted in storage. */
		errorDocumentCount: Option[Int] = None
	)
	
	case class GoogleCloudDocumentaiV1EvaluateProcessorVersionMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3UndeployProcessorVersionResponse(
	
	)
	
	object GoogleCloudDocumentaiV1DocumentPageLayout {
		enum OrientationEnum extends Enum[OrientationEnum] { case ORIENTATION_UNSPECIFIED, PAGE_UP, PAGE_RIGHT, PAGE_DOWN, PAGE_LEFT }
	}
	case class GoogleCloudDocumentaiV1DocumentPageLayout(
	  /** Text anchor indexing into the Document.text. */
		textAnchor: Option[Schema.GoogleCloudDocumentaiV1DocumentTextAnchor] = None,
	  /** The bounding polygon for the Layout. */
		boundingPoly: Option[Schema.GoogleCloudDocumentaiV1BoundingPoly] = None,
	  /** Detected orientation for the Layout. */
		orientation: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout.OrientationEnum] = None,
	  /** Confidence of the current Layout within context of the object this layout is for. e.g. confidence can be for a single token, a table, a visual element, etc. depending on context. Range `[0, 1]`. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentShardInfo(
	  /** The index of the first character in Document.text in the overall document global text. */
		textOffset: Option[String] = None,
	  /** The 0-based index of this shard. */
		shardIndex: Option[String] = None,
	  /** Total number of shards. */
		shardCount: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3UpdateDatasetOperationMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1BatchProcessRequest(
	  /** Optional. The labels with user-defined metadata for the request. Label keys and values can be no longer than 63 characters (Unicode codepoints) and can only contain lowercase letters, numeric characters, underscores, and dashes. International characters are allowed. Label values are optional. Label keys must start with a letter. */
		labels: Option[Map[String, String]] = None,
	  /** Inference-time options for the process API */
		processOptions: Option[Schema.GoogleCloudDocumentaiV1ProcessOptions] = None,
	  /** The output configuration for the BatchProcessDocuments method. */
		documentOutputConfig: Option[Schema.GoogleCloudDocumentaiV1DocumentOutputConfig] = None,
	  /** The input documents for the BatchProcessDocuments method. */
		inputDocuments: Option[Schema.GoogleCloudDocumentaiV1BatchDocumentsInputConfig] = None,
	  /** Whether human review should be skipped for this request. Default to `false`. */
		skipHumanReview: Option[Boolean] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3DocumentId(
	  /** Points to a specific revision of the document if set. */
		revisionRef: Option[Schema.GoogleCloudDocumentaiV1beta3RevisionRef] = None,
	  /** A document id within user-managed Cloud Storage. */
		gcsManagedDocId: Option[Schema.GoogleCloudDocumentaiV1beta3DocumentIdGCSManagedDocumentId] = None,
	  /** A document id within unmanaged dataset. */
		unmanagedDocId: Option[Schema.GoogleCloudDocumentaiV1beta3DocumentIdUnmanagedDocumentId] = None
	)
	
	case class GoogleCloudDocumentaiV1BatchProcessMetadataIndividualProcessStatus(
	  /** The status processing the document. */
		status: Option[Schema.GoogleRpcStatus] = None,
	  /** The status of human review on the processed document. */
		humanReviewStatus: Option[Schema.GoogleCloudDocumentaiV1HumanReviewStatus] = None,
	  /** The source of the document, same as the input_gcs_source field in the request when the batch process started. */
		inputGcsSource: Option[String] = None,
	  /** The Cloud Storage output destination (in the request as DocumentOutputConfig.GcsOutputConfig.gcs_uri) of the processed document if it was successful, otherwise empty. */
		outputGcsDestination: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentEntityRelation(
	  /** Relationship description. */
		relation: Option[String] = None,
	  /** Subject entity id. */
		subjectId: Option[String] = None,
	  /** Object entity id. */
		objectId: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentChunkedDocumentChunk(
	  /** Unused. */
		sourceBlockIds: Option[List[String]] = None,
	  /** Page footers associated with the chunk. */
		pageFooters: Option[List[Schema.GoogleCloudDocumentaiV1DocumentChunkedDocumentChunkChunkPageFooter]] = None,
	  /** ID of the chunk. */
		chunkId: Option[String] = None,
	  /** Page span of the chunk. */
		pageSpan: Option[Schema.GoogleCloudDocumentaiV1DocumentChunkedDocumentChunkChunkPageSpan] = None,
	  /** Page headers associated with the chunk. */
		pageHeaders: Option[List[Schema.GoogleCloudDocumentaiV1DocumentChunkedDocumentChunkChunkPageHeader]] = None,
	  /** Text content of the chunk. */
		content: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageMatrix(
	  /** The matrix data. */
		data: Option[String] = None,
	  /** Number of columns in the matrix. */
		cols: Option[Int] = None,
	  /** This encodes information about what data type the matrix uses. For example, 0 (CV_8U) is an unsigned 8-bit image. For the full list of OpenCV primitive data types, please refer to https://docs.opencv.org/4.3.0/d1/d1b/group__core__hal__interface.html */
		`type`: Option[Int] = None,
	  /** Number of rows in the matrix. */
		rows: Option[Int] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3EnableProcessorResponse(
	
	)
	
	case class GoogleCloudDocumentaiV1TrainProcessorVersionRequestInputData(
	  /** The documents used for testing the trained version. */
		testDocuments: Option[Schema.GoogleCloudDocumentaiV1BatchDocumentsInputConfig] = None,
	  /** The documents used for training the new version. */
		trainingDocuments: Option[Schema.GoogleCloudDocumentaiV1BatchDocumentsInputConfig] = None
	)
	
	case class GoogleCloudDocumentaiV1BatchDocumentsInputConfig(
	  /** The set of documents individually specified on Cloud Storage. */
		gcsDocuments: Option[Schema.GoogleCloudDocumentaiV1GcsDocuments] = None,
	  /** The set of documents that match the specified Cloud Storage `gcs_prefix`. */
		gcsPrefix: Option[Schema.GoogleCloudDocumentaiV1GcsPrefix] = None
	)
	
	case class GoogleCloudDocumentaiV1ListProcessorsResponse(
	  /** Points to the next processor, otherwise empty. */
		nextPageToken: Option[String] = None,
	  /** The list of processors. */
		processors: Option[List[Schema.GoogleCloudDocumentaiV1Processor]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageDimension(
	  /** Page width. */
		width: Option[BigDecimal] = None,
	  /** Dimension unit. */
		unit: Option[String] = None,
	  /** Page height. */
		height: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDocumentaiV1Vertex(
	  /** X coordinate. */
		x: Option[Int] = None,
	  /** Y coordinate (starts from the top of the image). */
		y: Option[Int] = None
	)
	
	object GoogleCloudDocumentaiV1ReviewDocumentResponse {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, REJECTED, SUCCEEDED }
	}
	case class GoogleCloudDocumentaiV1ReviewDocumentResponse(
	  /** The state of the review operation. */
		state: Option[Schema.GoogleCloudDocumentaiV1ReviewDocumentResponse.StateEnum] = None,
	  /** The reason why the review is rejected by reviewer. */
		rejectionReason: Option[String] = None,
	  /** The Cloud Storage uri for the human reviewed document if the review is succeeded. */
		gcsDestination: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentTextChange(
	  /** The text that replaces the text identified in the `text_anchor`. */
		changedText: Option[String] = None,
	  /** Provenance of the correction. Text anchor indexing into the Document.text. There can only be a single `TextAnchor.text_segments` element. If the start and end index of the text segment are the same, the text change is inserted before that index. */
		textAnchor: Option[Schema.GoogleCloudDocumentaiV1DocumentTextAnchor] = None,
	  /** The history of this annotation. */
		provenance: Option[List[Schema.GoogleCloudDocumentaiV1DocumentProvenance]] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3TrainProcessorVersionMetadataDatasetValidation(
	  /** The total number of dataset errors. */
		datasetErrorCount: Option[Int] = None,
	  /** Error information pertaining to specific documents. A maximum of 10 document errors will be returned. Any document with errors will not be used throughout training. */
		documentErrors: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** The total number of document errors. */
		documentErrorCount: Option[Int] = None,
	  /** Error information for the dataset as a whole. A maximum of 10 dataset errors will be returned. A single dataset error is terminal for training. */
		datasetErrors: Option[List[Schema.GoogleRpcStatus]] = None
	)
	
	case class GoogleCloudDocumentaiV1EvaluationConfidenceLevelMetrics(
	  /** The confidence level. */
		confidenceLevel: Option[BigDecimal] = None,
	  /** The metrics at the specific confidence level. */
		metrics: Option[Schema.GoogleCloudDocumentaiV1EvaluationMetrics] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3TrainProcessorVersionResponse(
	  /** The resource name of the processor version produced by training. */
		processorVersion: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1SetDefaultProcessorVersionRequest(
	  /** Required. The resource name of child ProcessorVersion to use as default. Format: `projects/{project}/locations/{location}/processors/{processor}/processorVersions/{version}` */
		defaultProcessorVersion: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1EnableProcessorRequest(
	
	)
	
	case class GoogleCloudDocumentaiV1EnableProcessorResponse(
	
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageTokenStyleInfo(
	  /** Whether the text is handwritten. */
		handwritten: Option[Boolean] = None,
	  /** TrueType weight on a scale `100` (thin) to `1000` (ultra-heavy). Normal is `400`, bold is `700`. */
		fontWeight: Option[Int] = None,
	  /** Whether the text is a superscript. This feature is not supported yet. */
		superscript: Option[Boolean] = None,
	  /** Whether the text is bold (equivalent to font_weight is at least `700`). */
		bold: Option[Boolean] = None,
	  /** Whether the text is italic. */
		italic: Option[Boolean] = None,
	  /** Whether the text is underlined. */
		underlined: Option[Boolean] = None,
	  /** Color of the text. */
		textColor: Option[Schema.GoogleTypeColor] = None,
	  /** Letter spacing in points. */
		letterSpacing: Option[BigDecimal] = None,
	  /** Font size in pixels, equal to _unrounded font_size_ &#42; _resolution_ ÷ `72.0`. */
		pixelFontSize: Option[BigDecimal] = None,
	  /** Name or style of the font. */
		fontType: Option[String] = None,
	  /** Whether the text is in small caps. This feature is not supported yet. */
		smallcaps: Option[Boolean] = None,
	  /** Whether the text is a subscript. This feature is not supported yet. */
		subscript: Option[Boolean] = None,
	  /** Font size in points (`1` point is `¹⁄₇₂` inches). */
		fontSize: Option[Int] = None,
	  /** Color of the background. */
		backgroundColor: Option[Schema.GoogleTypeColor] = None,
	  /** Whether the text is strikethrough. This feature is not supported yet. */
		strikeout: Option[Boolean] = None
	)
	
	case class GoogleCloudDocumentaiV1UndeployProcessorVersionRequest(
	
	)
	
	case class GoogleCloudDocumentaiUiv1beta3EvaluateProcessorVersionMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None
	)
	
	object GoogleCloudDocumentaiV1beta3ReviewDocumentResponse {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, REJECTED, SUCCEEDED }
	}
	case class GoogleCloudDocumentaiV1beta3ReviewDocumentResponse(
	  /** The reason why the review is rejected by reviewer. */
		rejectionReason: Option[String] = None,
	  /** The state of the review operation. */
		state: Option[Schema.GoogleCloudDocumentaiV1beta3ReviewDocumentResponse.StateEnum] = None,
	  /** The Cloud Storage uri for the human reviewed document if the review is succeeded. */
		gcsDestination: Option[String] = None
	)
	
	object GoogleCloudDocumentaiV1beta3RevisionRef {
		enum RevisionCaseEnum extends Enum[RevisionCaseEnum] { case REVISION_CASE_UNSPECIFIED, LATEST_HUMAN_REVIEW, LATEST_TIMESTAMP, BASE_OCR_REVISION }
	}
	case class GoogleCloudDocumentaiV1beta3RevisionRef(
	  /** Reads the revision given by the id. */
		revisionId: Option[String] = None,
	  /** Reads the revision by the predefined case. */
		revisionCase: Option[Schema.GoogleCloudDocumentaiV1beta3RevisionRef.RevisionCaseEnum] = None,
	  /** Reads the revision generated by the processor version. The format takes the full resource name of processor version. `projects/{project}/locations/{location}/processors/{processor}/processorVersions/{processorVersion}` */
		latestProcessorVersion: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1TrainProcessorVersionMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1CommonOperationMetadata] = None,
	  /** The test dataset validation information. */
		testDatasetValidation: Option[Schema.GoogleCloudDocumentaiV1TrainProcessorVersionMetadataDatasetValidation] = None,
	  /** The training dataset validation information. */
		trainingDatasetValidation: Option[Schema.GoogleCloudDocumentaiV1TrainProcessorVersionMetadataDatasetValidation] = None
	)
	
	case class GoogleCloudLocationLocation(
	  /** Service-specific metadata. For example the available capacity at the given location. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** Resource name for the location, which may vary between implementations. For example: `"projects/example-project/locations/us-east1"` */
		name: Option[String] = None,
	  /** The friendly name for this location, typically a nearby city name. For example, "Tokyo". */
		displayName: Option[String] = None,
	  /** Cross-service attributes for the location. For example {"cloud.googleapis.com/region": "us-east1"} */
		labels: Option[Map[String, String]] = None,
	  /** The canonical id for this location. For example: `"us-east1"`. */
		locationId: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3UpdateHumanReviewConfigMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3ImportDocumentsMetadataImportConfigValidationResult(
	  /** The source Cloud Storage URI specified in the import config. */
		inputGcsSource: Option[String] = None,
	  /** The validation status of import config. */
		status: Option[Schema.GoogleRpcStatus] = None
	)
	
	object GoogleCloudDocumentaiV1ReviewDocumentRequest {
		enum PriorityEnum extends Enum[PriorityEnum] { case DEFAULT, URGENT }
	}
	case class GoogleCloudDocumentaiV1ReviewDocumentRequest(
	  /** Whether the validation should be performed on the ad-hoc review request. */
		enableSchemaValidation: Option[Boolean] = None,
	  /** An inline document proto. */
		inlineDocument: Option[Schema.GoogleCloudDocumentaiV1Document] = None,
	  /** The priority of the human review task. */
		priority: Option[Schema.GoogleCloudDocumentaiV1ReviewDocumentRequest.PriorityEnum] = None,
	  /** The document schema of the human review task. */
		documentSchema: Option[Schema.GoogleCloudDocumentaiV1DocumentSchema] = None
	)
	
	case class GoogleCloudDocumentaiV1DisableProcessorRequest(
	
	)
	
	case class GoogleCloudDocumentaiUiv1beta3DisableProcessorMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageBlock(
	  /** The history of this annotation. */
		provenance: Option[Schema.GoogleCloudDocumentaiV1DocumentProvenance] = None,
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None,
	  /** Layout for Block. */
		layout: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3SetDefaultProcessorVersionMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3TrainProcessorVersionMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1beta3CommonOperationMetadata] = None,
	  /** The training dataset validation information. */
		trainingDatasetValidation: Option[Schema.GoogleCloudDocumentaiV1beta3TrainProcessorVersionMetadataDatasetValidation] = None,
	  /** The test dataset validation information. */
		testDatasetValidation: Option[Schema.GoogleCloudDocumentaiV1beta3TrainProcessorVersionMetadataDatasetValidation] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentTextAnchor(
	  /** Contains the content of the text span so that users do not have to look it up in the text_segments. It is always populated for formFields. */
		content: Option[String] = None,
	  /** The text segments from the Document.text. */
		textSegments: Option[List[Schema.GoogleCloudDocumentaiV1DocumentTextAnchorTextSegment]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageParagraph(
	  /** The history of this annotation. */
		provenance: Option[Schema.GoogleCloudDocumentaiV1DocumentProvenance] = None,
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None,
	  /** Layout for Paragraph. */
		layout: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3TrainProcessorVersionMetadataDatasetValidation(
	  /** The total number of dataset errors. */
		datasetErrorCount: Option[Int] = None,
	  /** Error information pertaining to specific documents. A maximum of 10 document errors will be returned. Any document with errors will not be used throughout training. */
		documentErrors: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** The total number of document errors. */
		documentErrorCount: Option[Int] = None,
	  /** Error information for the dataset as a whole. A maximum of 10 dataset errors will be returned. A single dataset error is terminal for training. */
		datasetErrors: Option[List[Schema.GoogleRpcStatus]] = None
	)
	
	case class GoogleCloudDocumentaiV1NormalizedVertex(
	  /** Y coordinate (starts from the top of the image). */
		y: Option[BigDecimal] = None,
	  /** X coordinate. */
		x: Option[BigDecimal] = None
	)
	
	object GoogleCloudDocumentaiV1HumanReviewStatus {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, SKIPPED, VALIDATION_PASSED, IN_PROGRESS, ERROR }
	}
	case class GoogleCloudDocumentaiV1HumanReviewStatus(
	  /** The name of the operation triggered by the processed document. This field is populated only when the state is `HUMAN_REVIEW_IN_PROGRESS`. It has the same response type and metadata as the long-running operation returned by ReviewDocument. */
		humanReviewOperation: Option[String] = None,
	  /** A message providing more details about the human review state. */
		stateMessage: Option[String] = None,
	  /** The state of human review on the processing request. */
		state: Option[Schema.GoogleCloudDocumentaiV1HumanReviewStatus.StateEnum] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageImageQualityScores(
	  /** The overall quality score. Range `[0, 1]` where `1` is perfect quality. */
		qualityScore: Option[BigDecimal] = None,
	  /** A list of detected defects. */
		detectedDefects: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageImageQualityScoresDetectedDefect]] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3BatchUpdateDocumentsMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None,
	  /** The list of response details of each document. */
		individualBatchUpdateStatuses: Option[List[Schema.GoogleCloudDocumentaiUiv1beta3BatchUpdateDocumentsMetadataIndividualBatchUpdateStatus]] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3ImportDocumentsMetadataIndividualImportStatus(
	  /** The document id of imported document if it was successful, otherwise empty. */
		outputDocumentId: Option[Schema.GoogleCloudDocumentaiV1beta3DocumentId] = None,
	  /** The source Cloud Storage URI of the document. */
		inputGcsSource: Option[String] = None,
	  /** The status of the importing of the document. */
		status: Option[Schema.GoogleRpcStatus] = None
	)
	
	case class GoogleTypeTimeZone(
	  /** Optional. IANA Time Zone Database version number, e.g. "2019a". */
		version: Option[String] = None,
	  /** IANA Time Zone Database time zone, e.g. "America/New_York". */
		id: Option[String] = None
	)
	
	case class GoogleLongrunningOperation(
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None
	)
	
	object GoogleCloudDocumentaiV1Processor {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ENABLED, DISABLED, ENABLING, DISABLING, CREATING, FAILED, DELETING }
	}
	case class GoogleCloudDocumentaiV1Processor(
	  /** The [KMS key](https://cloud.google.com/security-key-management) used for encryption and decryption in CMEK scenarios. */
		kmsKeyName: Option[String] = None,
	  /** Output only. Immutable. The http endpoint that can be called to invoke processing. */
		processEndpoint: Option[String] = None,
	  /** The display name of the processor. */
		displayName: Option[String] = None,
	  /** The time the processor was created. */
		createTime: Option[String] = None,
	  /** The default processor version. */
		defaultProcessorVersion: Option[String] = None,
	  /** Output only. The state of the processor. */
		state: Option[Schema.GoogleCloudDocumentaiV1Processor.StateEnum] = None,
	  /** Output only. Immutable. The resource name of the processor. Format: `projects/{project}/locations/{location}/processors/{processor}` */
		name: Option[String] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzi: Option[Boolean] = None,
	  /** The processor type, such as: `OCR_PROCESSOR`, `INVOICE_PROCESSOR`. To get a list of processor types, see FetchProcessorTypes. */
		`type`: Option[String] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Output only. The processor version aliases. */
		processorVersionAliases: Option[List[Schema.GoogleCloudDocumentaiV1ProcessorVersionAlias]] = None
	)
	
	case class GoogleCloudDocumentaiV1OcrConfigHints(
	  /** List of BCP-47 language codes to use for OCR. In most cases, not specifying it yields the best results since it enables automatic language detection. For languages based on the Latin alphabet, setting hints is not needed. In rare cases, when the language of the text in the image is known, setting a hint will help get better results (although it will be a significant hindrance if the hint is wrong). */
		languageHints: Option[List[String]] = None
	)
	
	case class GoogleCloudDocumentaiV1EvaluateProcessorVersionRequest(
	  /** Optional. The documents used in the evaluation. If unspecified, use the processor's dataset as evaluation input. */
		evaluationDocuments: Option[Schema.GoogleCloudDocumentaiV1BatchDocumentsInputConfig] = None
	)
	
	case class GoogleTypeDateTime(
	  /** Optional. Month of year. Must be from 1 to 12, or 0 if specifying a datetime without a month. */
		month: Option[Int] = None,
	  /** UTC offset. Must be whole seconds, between -18 hours and +18 hours. For example, a UTC offset of -4:00 would be represented as { seconds: -14400 }. */
		utcOffset: Option[String] = None,
	  /** Time zone. */
		timeZone: Option[Schema.GoogleTypeTimeZone] = None,
	  /** Optional. Seconds of minutes of the time. Must normally be from 0 to 59, defaults to 0. An API may allow the value 60 if it allows leap-seconds. */
		seconds: Option[Int] = None,
	  /** Optional. Hours of day in 24 hour format. Should be from 0 to 23, defaults to 0 (midnight). An API may choose to allow the value "24:00:00" for scenarios like business closing time. */
		hours: Option[Int] = None,
	  /** Optional. Fractions of seconds in nanoseconds. Must be from 0 to 999,999,999, defaults to 0. */
		nanos: Option[Int] = None,
	  /** Optional. Minutes of hour of day. Must be from 0 to 59, defaults to 0. */
		minutes: Option[Int] = None,
	  /** Optional. Year of date. Must be from 1 to 9999, or 0 if specifying a datetime without a year. */
		year: Option[Int] = None,
	  /** Optional. Day of month. Must be from 1 to 31 and valid for the year and month, or 0 if specifying a datetime without a day. */
		day: Option[Int] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3DisableProcessorResponse(
	
	)
	
	case class GoogleCloudDocumentaiV1GcsDocuments(
	  /** The list of documents. */
		documents: Option[List[Schema.GoogleCloudDocumentaiV1GcsDocument]] = None
	)
	
	case class GoogleCloudDocumentaiV1DeployProcessorVersionResponse(
	
	)
	
	case class GoogleCloudDocumentaiV1GcsPrefix(
	  /** The URI prefix. */
		gcsUriPrefix: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentEntityNormalizedValue(
	  /** Optional. An optional field to store a normalized string. For some entity types, one of respective `structured_value` fields may also be populated. Also not all the types of `structured_value` will be normalized. For example, some processors may not generate `float` or `integer` normalized text by default. Below are sample formats mapped to structured values. - Money/Currency type (`money_value`) is in the ISO 4217 text format. - Date type (`date_value`) is in the ISO 8601 text format. - Datetime type (`datetime_value`) is in the ISO 8601 text format. */
		text: Option[String] = None,
	  /** Float value. */
		floatValue: Option[BigDecimal] = None,
	  /** Integer value. */
		integerValue: Option[Int] = None,
	  /** Date value. Includes year, month, day. See also: https://github.com/googleapis/googleapis/blob/master/google/type/date.proto */
		dateValue: Option[Schema.GoogleTypeDate] = None,
	  /** Boolean value. Can be used for entities with binary values, or for checkboxes. */
		booleanValue: Option[Boolean] = None,
	  /** Money value. See also: https://github.com/googleapis/googleapis/blob/master/google/type/money.proto */
		moneyValue: Option[Schema.GoogleTypeMoney] = None,
	  /** Postal address. See also: https://github.com/googleapis/googleapis/blob/master/google/type/postal_address.proto */
		addressValue: Option[Schema.GoogleTypePostalAddress] = None,
	  /** DateTime value. Includes date, time, and timezone. See also: https://github.com/googleapis/googleapis/blob/master/google/type/datetime.proto */
		datetimeValue: Option[Schema.GoogleTypeDateTime] = None
	)
	
	object GoogleCloudDocumentaiUiv1beta3ExportDocumentsMetadataSplitExportStat {
		enum SplitTypeEnum extends Enum[SplitTypeEnum] { case DATASET_SPLIT_TYPE_UNSPECIFIED, DATASET_SPLIT_TRAIN, DATASET_SPLIT_TEST, DATASET_SPLIT_UNASSIGNED }
	}
	case class GoogleCloudDocumentaiUiv1beta3ExportDocumentsMetadataSplitExportStat(
	  /** The dataset split type. */
		splitType: Option[Schema.GoogleCloudDocumentaiUiv1beta3ExportDocumentsMetadataSplitExportStat.SplitTypeEnum] = None,
	  /** Total number of documents with the given dataset split type to be exported. */
		totalDocumentCount: Option[Int] = None
	)
	
	case class GoogleLongrunningListOperationsResponse(
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None,
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.GoogleLongrunningOperation]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentChunkedDocument(
	  /** List of chunks. */
		chunks: Option[List[Schema.GoogleCloudDocumentaiV1DocumentChunkedDocumentChunk]] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3DeleteLabelerPoolOperationMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None
	)
	
	object GoogleCloudDocumentaiV1DocumentSchemaEntityTypeProperty {
		enum OccurrenceTypeEnum extends Enum[OccurrenceTypeEnum] { case OCCURRENCE_TYPE_UNSPECIFIED, OPTIONAL_ONCE, OPTIONAL_MULTIPLE, REQUIRED_ONCE, REQUIRED_MULTIPLE }
	}
	case class GoogleCloudDocumentaiV1DocumentSchemaEntityTypeProperty(
	  /** User defined name for the property. */
		displayName: Option[String] = None,
	  /** Occurrence type limits the number of instances an entity type appears in the document. */
		occurrenceType: Option[Schema.GoogleCloudDocumentaiV1DocumentSchemaEntityTypeProperty.OccurrenceTypeEnum] = None,
	  /** The name of the property. Follows the same guidelines as the EntityType name. */
		name: Option[String] = None,
	  /** A reference to the value type of the property. This type is subject to the same conventions as the `Entity.base_types` field. */
		valueType: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1ProcessorVersionGenAiModelInfoFoundationGenAiModelInfo(
	  /** The minimum number of labeled documents in the training dataset required for finetuning. */
		minTrainLabeledDocuments: Option[Int] = None,
	  /** Whether finetuning is allowed for this base processor version. */
		finetuningAllowed: Option[Boolean] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3DocumentIdUnmanagedDocumentId(
	  /** Required. The id of the document. */
		docId: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlock(
	  /** Block consisting of list content/structure. */
		listBlock: Option[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutListBlock] = None,
	  /** Page span of the block. */
		pageSpan: Option[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutPageSpan] = None,
	  /** Block consisting of table content/structure. */
		tableBlock: Option[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutTableBlock] = None,
	  /** ID of the block. */
		blockId: Option[String] = None,
	  /** Block consisting of text content. */
		textBlock: Option[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutTextBlock] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentOutputConfigGcsOutputConfig(
	  /** The Cloud Storage uri (a directory) of the output. */
		gcsUri: Option[String] = None,
	  /** Specifies the sharding config for the output document. */
		shardingConfig: Option[Schema.GoogleCloudDocumentaiV1DocumentOutputConfigGcsOutputConfigShardingConfig] = None,
	  /** Specifies which fields to include in the output documents. Only supports top level document and pages field so it must be in the form of `{document_field_name}` or `pages.{page_field_name}`. */
		fieldMask: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutTextBlock(
	  /** A text block could further have child blocks. Repeated blocks support further hierarchies and nested blocks. */
		blocks: Option[List[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlock]] = None,
	  /** Text content stored in the block. */
		text: Option[String] = None,
	  /** Type of the text in the block. Available options are: `paragraph`, `subtitle`, `heading-1`, `heading-2`, `heading-3`, `heading-4`, `heading-5`, `header`, `footer`. */
		`type`: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1ProcessorVersionAlias(
	  /** The alias in the form of `processor_version` resource name. */
		alias: Option[String] = None,
	  /** The resource name of aliased processor version. */
		processorVersion: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1FetchProcessorTypesResponse(
	  /** The list of processor types. */
		processorTypes: Option[List[Schema.GoogleCloudDocumentaiV1ProcessorType]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageTableTableCell(
	  /** How many rows this cell spans. */
		rowSpan: Option[Int] = None,
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None,
	  /** Layout for TableCell. */
		layout: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None,
	  /** How many columns this cell spans. */
		colSpan: Option[Int] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutPageSpan(
	  /** Page where block starts in the document. */
		pageStart: Option[Int] = None,
	  /** Page where block ends in the document. */
		pageEnd: Option[Int] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3EnableProcessorMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1Barcode(
	  /** Value format describes the format of the value that a barcode encodes. The supported formats are: - `CONTACT_INFO`: Contact information. - `EMAIL`: Email address. - `ISBN`: ISBN identifier. - `PHONE`: Phone number. - `PRODUCT`: Product. - `SMS`: SMS message. - `TEXT`: Text string. - `URL`: URL address. - `WIFI`: Wifi information. - `GEO`: Geo-localization. - `CALENDAR_EVENT`: Calendar event. - `DRIVER_LICENSE`: Driver's license. */
		valueFormat: Option[String] = None,
	  /** Raw value encoded in the barcode. For example: `'MEBKM:TITLE:Google;URL:https://www.google.com;;'`. */
		rawValue: Option[String] = None,
	  /** Format of a barcode. The supported formats are: - `CODE_128`: Code 128 type. - `CODE_39`: Code 39 type. - `CODE_93`: Code 93 type. - `CODABAR`: Codabar type. - `DATA_MATRIX`: 2D Data Matrix type. - `ITF`: ITF type. - `EAN_13`: EAN-13 type. - `EAN_8`: EAN-8 type. - `QR_CODE`: 2D QR code type. - `UPC_A`: UPC-A type. - `UPC_E`: UPC-E type. - `PDF417`: PDF417 type. - `AZTEC`: 2D Aztec code type. - `DATABAR`: GS1 DataBar code type. */
		format: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3ExportDocumentsMetadata(
	  /** The list of response details of each document. */
		individualExportStatuses: Option[List[Schema.GoogleCloudDocumentaiUiv1beta3ExportDocumentsMetadataIndividualExportStatus]] = None,
	  /** The list of statistics for each dataset split type. */
		splitExportStats: Option[List[Schema.GoogleCloudDocumentaiUiv1beta3ExportDocumentsMetadataSplitExportStat]] = None,
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPage(
	  /** The history of this page. */
		provenance: Option[Schema.GoogleCloudDocumentaiV1DocumentProvenance] = None,
	  /** A list of visually detected text paragraphs on the page. A collection of lines that a human would perceive as a paragraph. */
		paragraphs: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageParagraph]] = None,
	  /** Physical dimension of the page. */
		dimension: Option[Schema.GoogleCloudDocumentaiV1DocumentPageDimension] = None,
	  /** 1-based index for current Page in a parent Document. Useful when a page is taken out of a Document for individual processing. */
		pageNumber: Option[Int] = None,
	  /** A list of visually detected tokens on the page. */
		tokens: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageToken]] = None,
	  /** Rendered image for this page. This image is preprocessed to remove any skew, rotation, and distortions such that the annotation bounding boxes can be upright and axis-aligned. */
		image: Option[Schema.GoogleCloudDocumentaiV1DocumentPageImage] = None,
	  /** Layout for the page. */
		layout: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None,
	  /** A list of visually detected symbols on the page. */
		symbols: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageSymbol]] = None,
	  /** A list of visually detected form fields on the page. */
		formFields: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageFormField]] = None,
	  /** Image quality scores. */
		imageQualityScores: Option[Schema.GoogleCloudDocumentaiV1DocumentPageImageQualityScores] = None,
	  /** A list of visually detected tables on the page. */
		tables: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageTable]] = None,
	  /** A list of visually detected text lines on the page. A collection of tokens that a human would perceive as a line. */
		lines: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageLine]] = None,
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None,
	  /** Transformation matrices that were applied to the original document image to produce Page.image. */
		transforms: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageMatrix]] = None,
	  /** A list of detected barcodes. */
		detectedBarcodes: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedBarcode]] = None,
	  /** A list of visually detected text blocks on the page. A block has a set of lines (collected into paragraphs) that have a common line-spacing and orientation. */
		blocks: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageBlock]] = None,
	  /** A list of detected non-text visual elements e.g. checkbox, signature etc. on the page. */
		visualElements: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageVisualElement]] = None
	)
	
	case class GoogleCloudDocumentaiV1DisableProcessorMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1CommonOperationMetadata] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	object GoogleCloudDocumentaiV1beta3ReviewDocumentOperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RUNNING, CANCELLING, SUCCEEDED, FAILED, CANCELLED }
	}
	case class GoogleCloudDocumentaiV1beta3ReviewDocumentOperationMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1beta3CommonOperationMetadata] = None,
	  /** The Crowd Compute question ID. */
		questionId: Option[String] = None,
	  /** Used only when Operation.done is false. */
		state: Option[Schema.GoogleCloudDocumentaiV1beta3ReviewDocumentOperationMetadata.StateEnum] = None,
	  /** The creation time of the operation. */
		createTime: Option[String] = None,
	  /** The last update time of the operation. */
		updateTime: Option[String] = None,
	  /** A message providing more details about the current state of processing. For example, the error message if the operation is failed. */
		stateMessage: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3UpdateDatasetOperationMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3UndeployProcessorVersionMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1Evaluation(
	  /** The time that the evaluation was created. */
		createTime: Option[String] = None,
	  /** The KMS key version with which data is encrypted. */
		kmsKeyVersionName: Option[String] = None,
	  /** The resource name of the evaluation. Format: `projects/{project}/locations/{location}/processors/{processor}/processorVersions/{processor_version}/evaluations/{evaluation}` */
		name: Option[String] = None,
	  /** Metrics for all the entities in aggregate. */
		allEntitiesMetrics: Option[Schema.GoogleCloudDocumentaiV1EvaluationMultiConfidenceMetrics] = None,
	  /** Metrics across confidence levels, for different entities. */
		entityMetrics: Option[Map[String, Schema.GoogleCloudDocumentaiV1EvaluationMultiConfidenceMetrics]] = None,
	  /** The KMS key name used for encryption. */
		kmsKeyName: Option[String] = None,
	  /** Counters for the documents used in the evaluation. */
		documentCounters: Option[Schema.GoogleCloudDocumentaiV1EvaluationCounters] = None
	)
	
	object GoogleCloudDocumentaiUiv1beta3BatchMoveDocumentsMetadata {
		enum DestSplitTypeEnum extends Enum[DestSplitTypeEnum] { case DATASET_SPLIT_TYPE_UNSPECIFIED, DATASET_SPLIT_TRAIN, DATASET_SPLIT_TEST, DATASET_SPLIT_UNASSIGNED }
		enum DestDatasetTypeEnum extends Enum[DestDatasetTypeEnum] { case DATASET_SPLIT_TYPE_UNSPECIFIED, DATASET_SPLIT_TRAIN, DATASET_SPLIT_TEST, DATASET_SPLIT_UNASSIGNED }
	}
	case class GoogleCloudDocumentaiUiv1beta3BatchMoveDocumentsMetadata(
	  /** The list of response details of each document. */
		individualBatchMoveStatuses: Option[List[Schema.GoogleCloudDocumentaiUiv1beta3BatchMoveDocumentsMetadataIndividualBatchMoveStatus]] = None,
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None,
	  /** The destination dataset split type. */
		destSplitType: Option[Schema.GoogleCloudDocumentaiUiv1beta3BatchMoveDocumentsMetadata.DestSplitTypeEnum] = None,
	  /** The destination dataset split type. */
		destDatasetType: Option[Schema.GoogleCloudDocumentaiUiv1beta3BatchMoveDocumentsMetadata.DestDatasetTypeEnum] = None
	)
	
	case class GoogleCloudDocumentaiV1DeleteProcessorVersionMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1ListProcessorVersionsResponse(
	  /** The list of processors. */
		processorVersions: Option[List[Schema.GoogleCloudDocumentaiV1ProcessorVersion]] = None,
	  /** Points to the next processor, otherwise empty. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3BatchDeleteDocumentsResponse(
	
	)
	
	case class GoogleCloudDocumentaiV1DocumentSchemaMetadata(
	  /** If set, all the nested entities must be prefixed with the parents. */
		prefixedNamingOnProperties: Option[Boolean] = None,
	  /** If set, we will skip the naming format validation in the schema. So the string values in `DocumentSchema.EntityType.name` and `DocumentSchema.EntityType.Property.name` will not be checked. */
		skipNamingValidation: Option[Boolean] = None,
	  /** If true, on a given page, there can be multiple `document` annotations covering it. */
		documentAllowMultipleLabels: Option[Boolean] = None,
	  /** If true, a `document` entity type can be applied to subdocument (splitting). Otherwise, it can only be applied to the entire document (classification). */
		documentSplitter: Option[Boolean] = None
	)
	
	object GoogleCloudDocumentaiUiv1beta3ResyncDatasetMetadataIndividualDocumentResyncStatus {
		enum DocumentInconsistencyTypeEnum extends Enum[DocumentInconsistencyTypeEnum] { case DOCUMENT_INCONSISTENCY_TYPE_UNSPECIFIED, DOCUMENT_INCONSISTENCY_TYPE_INVALID_DOCPROTO, DOCUMENT_INCONSISTENCY_TYPE_MISMATCHED_METADATA, DOCUMENT_INCONSISTENCY_TYPE_NO_PAGE_IMAGE }
	}
	case class GoogleCloudDocumentaiUiv1beta3ResyncDatasetMetadataIndividualDocumentResyncStatus(
	  /** The type of document inconsistency. */
		documentInconsistencyType: Option[Schema.GoogleCloudDocumentaiUiv1beta3ResyncDatasetMetadataIndividualDocumentResyncStatus.DocumentInconsistencyTypeEnum] = None,
	  /** The status of resyncing the document with regards to the detected inconsistency. Empty if ResyncDatasetRequest.validate_only is `true`. */
		status: Option[Schema.GoogleRpcStatus] = None,
	  /** The document identifier. */
		documentId: Option[Schema.GoogleCloudDocumentaiUiv1beta3DocumentId] = None
	)
	
	case class GoogleCloudDocumentaiV1ProcessOptionsLayoutConfig(
	  /** Optional. Config for chunking in layout parser processor. */
		chunkingConfig: Option[Schema.GoogleCloudDocumentaiV1ProcessOptionsLayoutConfigChunkingConfig] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3SetDefaultProcessorVersionResponse(
	
	)
	
	object GoogleCloudDocumentaiV1BatchProcessMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, WAITING, RUNNING, SUCCEEDED, CANCELLING, CANCELLED, FAILED }
	}
	case class GoogleCloudDocumentaiV1BatchProcessMetadata(
	  /** The state of the current batch processing. */
		state: Option[Schema.GoogleCloudDocumentaiV1BatchProcessMetadata.StateEnum] = None,
	  /** The creation time of the operation. */
		createTime: Option[String] = None,
	  /** The list of response details of each document. */
		individualProcessStatuses: Option[List[Schema.GoogleCloudDocumentaiV1BatchProcessMetadataIndividualProcessStatus]] = None,
	  /** A message providing more details about the current state of processing. For example, the error message if the operation is failed. */
		stateMessage: Option[String] = None,
	  /** The last update time of the operation. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1ProcessOptions(
	  /** Optional. Override the schema of the ProcessorVersion. Will return an Invalid Argument error if this field is set when the underlying ProcessorVersion doesn't support schema override. */
		schemaOverride: Option[Schema.GoogleCloudDocumentaiV1DocumentSchema] = None,
	  /** Optional. Only applicable to `LAYOUT_PARSER_PROCESSOR`. Returns error if set on other processor types. */
		layoutConfig: Option[Schema.GoogleCloudDocumentaiV1ProcessOptionsLayoutConfig] = None,
	  /** Only process certain pages from the start. Process all if the document has fewer pages. */
		fromStart: Option[Int] = None,
	  /** Only applicable to `OCR_PROCESSOR` and `FORM_PARSER_PROCESSOR`. Returns error if set on other processor types. */
		ocrConfig: Option[Schema.GoogleCloudDocumentaiV1OcrConfig] = None,
	  /** Which pages to process (1-indexed). */
		individualPageSelector: Option[Schema.GoogleCloudDocumentaiV1ProcessOptionsIndividualPageSelector] = None,
	  /** Only process certain pages from the end, same as above. */
		fromEnd: Option[Int] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3DeployProcessorVersionMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3EvaluateProcessorVersionResponse(
	  /** The resource name of the created evaluation. */
		evaluation: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3DocumentIdGCSManagedDocumentId(
	  /** Id of the document (indexed) managed by Content Warehouse. */
		cwDocId: Option[String] = None,
	  /** Required. The Cloud Storage URI where the actual document is stored. */
		gcsUri: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1ListEvaluationsResponse(
	  /** The evaluations requested. */
		evaluations: Option[List[Schema.GoogleCloudDocumentaiV1Evaluation]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3ExportDocumentsMetadataIndividualExportStatus(
	  /** The path to source docproto of the document. */
		documentId: Option[Schema.GoogleCloudDocumentaiUiv1beta3DocumentId] = None,
	  /** The status of the exporting of the document. */
		status: Option[Schema.GoogleRpcStatus] = None,
	  /** The output_gcs_destination of the exported document if it was successful, otherwise empty. */
		outputGcsDestination: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3EvaluateProcessorVersionResponse(
	  /** The resource name of the created evaluation. */
		evaluation: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageImage(
	  /** Raw byte content of the image. */
		content: Option[String] = None,
	  /** Height of the image in pixels. */
		height: Option[Int] = None,
	  /** Width of the image in pixels. */
		width: Option[Int] = None,
	  /** Encoding [media type (MIME type)](https://www.iana.org/assignments/media-types/media-types.xhtml) for the image. */
		mimeType: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentRevisionHumanReview(
	  /** A message providing more details about the current state of processing. For example, the rejection reason when the state is `rejected`. */
		stateMessage: Option[String] = None,
	  /** Human review state. e.g. `requested`, `succeeded`, `rejected`. */
		state: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1EvaluateProcessorVersionResponse(
	  /** The resource name of the created evaluation. */
		evaluation: Option[String] = None
	)
	
	object GoogleCloudDocumentaiV1TrainProcessorVersionRequestCustomDocumentExtractionOptions {
		enum TrainingMethodEnum extends Enum[TrainingMethodEnum] { case TRAINING_METHOD_UNSPECIFIED, MODEL_BASED, TEMPLATE_BASED }
	}
	case class GoogleCloudDocumentaiV1TrainProcessorVersionRequestCustomDocumentExtractionOptions(
	  /** Training method to use for CDE training. */
		trainingMethod: Option[Schema.GoogleCloudDocumentaiV1TrainProcessorVersionRequestCustomDocumentExtractionOptions.TrainingMethodEnum] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3DisableProcessorResponse(
	
	)
	
	case class GoogleCloudDocumentaiV1beta3UndeployProcessorVersionResponse(
	
	)
	
	case class GoogleTypeMoney(
	  /** The three-letter currency code defined in ISO 4217. */
		currencyCode: Option[String] = None,
	  /** The whole units of the amount. For example if `currencyCode` is `"USD"`, then 1 unit is one US dollar. */
		units: Option[String] = None,
	  /** Number of nano (10^-9) units of the amount. The value must be between -999,999,999 and +999,999,999 inclusive. If `units` is positive, `nanos` must be positive or zero. If `units` is zero, `nanos` can be positive, zero, or negative. If `units` is negative, `nanos` must be negative or zero. For example $-1.75 is represented as `units`=-1 and `nanos`=-750,000,000. */
		nanos: Option[Int] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageToken(
	  /** Detected break at the end of a Token. */
		detectedBreak: Option[Schema.GoogleCloudDocumentaiV1DocumentPageTokenDetectedBreak] = None,
	  /** The history of this annotation. */
		provenance: Option[Schema.GoogleCloudDocumentaiV1DocumentProvenance] = None,
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None,
	  /** Layout for Token. */
		layout: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None,
	  /** Text style attributes. */
		styleInfo: Option[Schema.GoogleCloudDocumentaiV1DocumentPageTokenStyleInfo] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3DocumentIdGCSManagedDocumentId(
	  /** Id of the document (indexed) managed by Content Warehouse. */
		cwDocId: Option[String] = None,
	  /** Required. The Cloud Storage URI where the actual document is stored. */
		gcsUri: Option[String] = None
	)
	
	object GoogleCloudDocumentaiV1beta3HumanReviewStatus {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, SKIPPED, VALIDATION_PASSED, IN_PROGRESS, ERROR }
	}
	case class GoogleCloudDocumentaiV1beta3HumanReviewStatus(
	  /** The name of the operation triggered by the processed document. This field is populated only when the state is `HUMAN_REVIEW_IN_PROGRESS`. It has the same response type and metadata as the long-running operation returned by ReviewDocument. */
		humanReviewOperation: Option[String] = None,
	  /** A message providing more details about the human review state. */
		stateMessage: Option[String] = None,
	  /** The state of human review on the processing request. */
		state: Option[Schema.GoogleCloudDocumentaiV1beta3HumanReviewStatus.StateEnum] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageAnchor(
	  /** One or more references to visual page elements */
		pageRefs: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageAnchorPageRef]] = None
	)
	
	case class GoogleCloudDocumentaiV1ProcessOptionsIndividualPageSelector(
	  /** Optional. Indices of the pages (starting from 1). */
		pages: Option[List[Int]] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3SetDefaultProcessorVersionMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3DeployProcessorVersionMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1DeployProcessorVersionMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1CommonOperationMetadata] = None
	)
	
	object GoogleCloudDocumentaiV1DocumentPageAnchorPageRef {
		enum LayoutTypeEnum extends Enum[LayoutTypeEnum] { case LAYOUT_TYPE_UNSPECIFIED, BLOCK, PARAGRAPH, LINE, TOKEN, VISUAL_ELEMENT, TABLE, FORM_FIELD }
	}
	case class GoogleCloudDocumentaiV1DocumentPageAnchorPageRef(
	  /** Optional. Deprecated. Use PageRef.bounding_poly instead. */
		layoutId: Option[String] = None,
	  /** Optional. The type of the layout element that is being referenced if any. */
		layoutType: Option[Schema.GoogleCloudDocumentaiV1DocumentPageAnchorPageRef.LayoutTypeEnum] = None,
	  /** Optional. Confidence of detected page element, if applicable. Range `[0, 1]`. */
		confidence: Option[BigDecimal] = None,
	  /** Required. Index into the Document.pages element, for example using `Document.pages` to locate the related page element. This field is skipped when its value is the default `0`. See https://developers.google.com/protocol-buffers/docs/proto3#json. */
		page: Option[String] = None,
	  /** Optional. Identifies the bounding polygon of a layout element on the page. If `layout_type` is set, the bounding polygon must be exactly the same to the layout element it's referring to. */
		boundingPoly: Option[Schema.GoogleCloudDocumentaiV1BoundingPoly] = None
	)
	
	case class GoogleCloudDocumentaiV1BatchProcessResponse(
	
	)
	
	case class GoogleCloudDocumentaiUiv1beta3ImportDocumentsMetadata(
	  /** The list of response details of each document. */
		individualImportStatuses: Option[List[Schema.GoogleCloudDocumentaiUiv1beta3ImportDocumentsMetadataIndividualImportStatus]] = None,
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None,
	  /** Total number of the documents that are qualified for importing. */
		totalDocumentCount: Option[Int] = None,
	  /** Validation statuses of the batch documents import config. */
		importConfigValidationResults: Option[List[Schema.GoogleCloudDocumentaiUiv1beta3ImportDocumentsMetadataImportConfigValidationResult]] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3TrainProcessorVersionMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None,
	  /** The training dataset validation information. */
		trainingDatasetValidation: Option[Schema.GoogleCloudDocumentaiUiv1beta3TrainProcessorVersionMetadataDatasetValidation] = None,
	  /** The test dataset validation information. */
		testDatasetValidation: Option[Schema.GoogleCloudDocumentaiUiv1beta3TrainProcessorVersionMetadataDatasetValidation] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3DatasetDocumentWarehouseConfig(
	  /** Output only. The schema in Document AI Warehouse associated with the dataset. */
		schema: Option[String] = None,
	  /** Output only. The collection in Document AI Warehouse associated with the dataset. */
		collection: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageSymbol(
	  /** Layout for Symbol. */
		layout: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None,
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageImageQualityScoresDetectedDefect(
	  /** Name of the defect type. Supported values are: - `quality/defect_blurry` - `quality/defect_noisy` - `quality/defect_dark` - `quality/defect_faint` - `quality/defect_text_too_small` - `quality/defect_document_cutoff` - `quality/defect_text_cutoff` - `quality/defect_glare` */
		`type`: Option[String] = None,
	  /** Confidence of detected defect. Range `[0, 1]` where `1` indicates strong confidence that the defect exists. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleTypeDate(
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None,
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None
	)
	
	object GoogleCloudDocumentaiV1ProcessorVersion {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, DEPLOYED, DEPLOYING, UNDEPLOYED, UNDEPLOYING, CREATING, DELETING, FAILED, IMPORTING }
		enum ModelTypeEnum extends Enum[ModelTypeEnum] { case MODEL_TYPE_UNSPECIFIED, MODEL_TYPE_GENERATIVE, MODEL_TYPE_CUSTOM }
	}
	case class GoogleCloudDocumentaiV1ProcessorVersion(
	  /** The time the processor version was created. */
		createTime: Option[String] = None,
	  /** Output only. Information about Generative AI model-based processor versions. */
		genAiModelInfo: Option[Schema.GoogleCloudDocumentaiV1ProcessorVersionGenAiModelInfo] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** The display name of the processor version. */
		displayName: Option[String] = None,
	  /** Output only. The state of the processor version. */
		state: Option[Schema.GoogleCloudDocumentaiV1ProcessorVersion.StateEnum] = None,
	  /** The schema of the processor version. Describes the output. */
		documentSchema: Option[Schema.GoogleCloudDocumentaiV1DocumentSchema] = None,
	  /** Output only. Denotes that this `ProcessorVersion` is managed by Google. */
		googleManaged: Option[Boolean] = None,
	  /** The most recently invoked evaluation for the processor version. */
		latestEvaluation: Option[Schema.GoogleCloudDocumentaiV1EvaluationReference] = None,
	  /** Identifier. The resource name of the processor version. Format: `projects/{project}/locations/{location}/processors/{processor}/processorVersions/{processor_version}` */
		name: Option[String] = None,
	  /** If set, information about the eventual deprecation of this version. */
		deprecationInfo: Option[Schema.GoogleCloudDocumentaiV1ProcessorVersionDeprecationInfo] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzi: Option[Boolean] = None,
	  /** The KMS key name used for encryption. */
		kmsKeyName: Option[String] = None,
	  /** Output only. The model type of this processor version. */
		modelType: Option[Schema.GoogleCloudDocumentaiV1ProcessorVersion.ModelTypeEnum] = None,
	  /** The KMS key version with which data is encrypted. */
		kmsKeyVersionName: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3DeleteProcessorMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutListEntry(
	  /** A list entry is a list of blocks. Repeated blocks support further hierarchies and nested blocks. */
		blocks: Option[List[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlock]] = None
	)
	
	case class GoogleCloudDocumentaiV1EvaluationCounters(
	  /** How many documents were not included in the evaluation as they didn't pass validation. */
		invalidDocumentsCount: Option[Int] = None,
	  /** How many documents were used in the evaluation. */
		evaluatedDocumentsCount: Option[Int] = None,
	  /** How many documents were not included in the evaluation as Document AI failed to process them. */
		failedDocumentsCount: Option[Int] = None,
	  /** How many documents were sent for evaluation. */
		inputDocumentsCount: Option[Int] = None
	)
	
	case class GoogleCloudDocumentaiV1SetDefaultProcessorVersionResponse(
	
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageDetectedLanguage(
	  /** Confidence of detected language. Range `[0, 1]`. */
		confidence: Option[BigDecimal] = None,
	  /** The [BCP-47 language code](https://www.unicode.org/reports/tr35/#Unicode_locale_identifier), such as `en-US` or `sr-Latn`. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3DeleteProcessorVersionMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1beta3CommonOperationMetadata] = None
	)
	
	object GoogleCloudDocumentaiV1DocumentPageTokenDetectedBreak {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, SPACE, WIDE_SPACE, HYPHEN }
	}
	case class GoogleCloudDocumentaiV1DocumentPageTokenDetectedBreak(
	  /** Detected break type. */
		`type`: Option[Schema.GoogleCloudDocumentaiV1DocumentPageTokenDetectedBreak.TypeEnum] = None
	)
	
	object GoogleCloudDocumentaiV1ProcessorType {
		enum LaunchStageEnum extends Enum[LaunchStageEnum] { case LAUNCH_STAGE_UNSPECIFIED, UNIMPLEMENTED, PRELAUNCH, EARLY_ACCESS, ALPHA, BETA, GA, DEPRECATED }
	}
	case class GoogleCloudDocumentaiV1ProcessorType(
	  /** Launch stage of the processor type */
		launchStage: Option[Schema.GoogleCloudDocumentaiV1ProcessorType.LaunchStageEnum] = None,
	  /** Whether the processor type allows creation. If true, users can create a processor of this processor type. Otherwise, users need to request access. */
		allowCreation: Option[Boolean] = None,
	  /** The resource name of the processor type. Format: `projects/{project}/processorTypes/{processor_type}` */
		name: Option[String] = None,
	  /** The locations in which this processor is available. */
		availableLocations: Option[List[Schema.GoogleCloudDocumentaiV1ProcessorTypeLocationInfo]] = None,
	  /** The processor type, such as: `OCR_PROCESSOR`, `INVOICE_PROCESSOR`. */
		`type`: Option[String] = None,
	  /** The processor category, used by UI to group processor types. */
		category: Option[String] = None,
	  /** A set of Cloud Storage URIs of sample documents for this processor. */
		sampleDocumentUris: Option[List[String]] = None
	)
	
	case class GoogleCloudDocumentaiV1ProcessorTypeLocationInfo(
	  /** The location ID. For supported locations, refer to [regional and multi-regional support](/document-ai/docs/regions). */
		locationId: Option[String] = None
	)
	
	object GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RUNNING, CANCELLING, SUCCEEDED, FAILED, CANCELLED }
	}
	case class GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata(
	  /** The creation time of the operation. */
		createTime: Option[String] = None,
	  /** The last update time of the operation. */
		updateTime: Option[String] = None,
	  /** A related resource to this operation. */
		resource: Option[String] = None,
	  /** A message providing more details about the current state of processing. */
		stateMessage: Option[String] = None,
	  /** The state of the operation. */
		state: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata.StateEnum] = None
	)
	
	object GoogleCloudDocumentaiV1DocumentProvenance {
		enum TypeEnum extends Enum[TypeEnum] { case OPERATION_TYPE_UNSPECIFIED, ADD, REMOVE, UPDATE, REPLACE, EVAL_REQUESTED, EVAL_APPROVED, EVAL_SKIPPED }
	}
	case class GoogleCloudDocumentaiV1DocumentProvenance(
	  /** The Id of this operation. Needs to be unique within the scope of the revision. */
		id: Option[Int] = None,
	  /** The index of the revision that produced this element. */
		revision: Option[Int] = None,
	  /** The type of provenance operation. */
		`type`: Option[Schema.GoogleCloudDocumentaiV1DocumentProvenance.TypeEnum] = None,
	  /** References to the original elements that are replaced. */
		parents: Option[List[Schema.GoogleCloudDocumentaiV1DocumentProvenanceParent]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutTableCell(
	  /** How many rows this cell spans. */
		rowSpan: Option[Int] = None,
	  /** A table cell is a list of blocks. Repeated blocks support further hierarchies and nested blocks. */
		blocks: Option[List[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlock]] = None,
	  /** How many columns this cell spans. */
		colSpan: Option[Int] = None
	)
	
	case class GoogleCloudDocumentaiV1TrainProcessorVersionRequest(
	  /** Optional. The processor version to use as a base for training. This processor version must be a child of `parent`. Format: `projects/{project}/locations/{location}/processors/{processor}/processorVersions/{processorVersion}`. */
		baseProcessorVersion: Option[String] = None,
	  /** Required. The processor version to be created. */
		processorVersion: Option[Schema.GoogleCloudDocumentaiV1ProcessorVersion] = None,
	  /** Optional. The input data used to train the ProcessorVersion. */
		inputData: Option[Schema.GoogleCloudDocumentaiV1TrainProcessorVersionRequestInputData] = None,
	  /** Options to control Custom Document Extraction (CDE) Processor. */
		customDocumentExtractionOptions: Option[Schema.GoogleCloudDocumentaiV1TrainProcessorVersionRequestCustomDocumentExtractionOptions] = None,
	  /** Optional. The schema the processor version will be trained with. */
		documentSchema: Option[Schema.GoogleCloudDocumentaiV1DocumentSchema] = None,
	  /** Options to control foundation model tuning of a processor. */
		foundationModelTuningOptions: Option[Schema.GoogleCloudDocumentaiV1TrainProcessorVersionRequestFoundationModelTuningOptions] = None
	)
	
	case class GoogleCloudDocumentaiV1EvaluationReference(
	  /** The resource name of the Long Running Operation for the evaluation. */
		operation: Option[String] = None,
	  /** An aggregate of the statistics for the evaluation with fuzzy matching off. */
		aggregateMetricsExact: Option[Schema.GoogleCloudDocumentaiV1EvaluationMetrics] = None,
	  /** An aggregate of the statistics for the evaluation with fuzzy matching on. */
		aggregateMetrics: Option[Schema.GoogleCloudDocumentaiV1EvaluationMetrics] = None,
	  /** The resource name of the evaluation. */
		evaluation: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1ListProcessorTypesResponse(
	  /** Points to the next page, otherwise empty. */
		nextPageToken: Option[String] = None,
	  /** The processor types. */
		processorTypes: Option[List[Schema.GoogleCloudDocumentaiV1ProcessorType]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentSchemaEntityTypeEnumValues(
	  /** The individual values that this enum values type can include. */
		values: Option[List[String]] = None
	)
	
	case class GoogleCloudDocumentaiV1TrainProcessorVersionResponse(
	  /** The resource name of the processor version produced by training. */
		processorVersion: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3AutoLabelDocumentsResponse(
	
	)
	
	case class GoogleCloudDocumentaiUiv1beta3ImportDocumentsResponse(
	
	)
	
	case class GoogleCloudDocumentaiUiv1beta3BatchMoveDocumentsResponse(
	
	)
	
	case class GoogleCloudDocumentaiUiv1beta3BatchDeleteDocumentsMetadataIndividualBatchDeleteStatus(
	  /** The status of deleting the document in storage. */
		status: Option[Schema.GoogleRpcStatus] = None,
	  /** The document id of the document. */
		documentId: Option[Schema.GoogleCloudDocumentaiUiv1beta3DocumentId] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3BatchDeleteDocumentsMetadataIndividualBatchDeleteStatus(
	  /** The status of deleting the document in storage. */
		status: Option[Schema.GoogleRpcStatus] = None,
	  /** The document id of the document. */
		documentId: Option[Schema.GoogleCloudDocumentaiV1beta3DocumentId] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3EnableProcessorMetadata(
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3ImportDocumentsMetadataIndividualImportStatus(
	  /** The document id of imported document if it was successful, otherwise empty. */
		outputDocumentId: Option[Schema.GoogleCloudDocumentaiUiv1beta3DocumentId] = None,
	  /** The status of the importing of the document. */
		status: Option[Schema.GoogleRpcStatus] = None,
	  /** The source Cloud Storage URI of the document. */
		inputGcsSource: Option[String] = None,
	  /** The output_gcs_destination of the processed document if it was successful, otherwise empty. */
		outputGcsDestination: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3DatasetGCSManagedConfig(
	  /** Required. The Cloud Storage URI (a directory) where the documents belonging to the dataset must be stored. */
		gcsPrefix: Option[Schema.GoogleCloudDocumentaiV1beta3GcsPrefix] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3BatchDeleteDocumentsMetadata(
	  /** Total number of documents deleting from dataset. */
		totalDocumentCount: Option[Int] = None,
	  /** The list of response details of each document. */
		individualBatchDeleteStatuses: Option[List[Schema.GoogleCloudDocumentaiUiv1beta3BatchDeleteDocumentsMetadataIndividualBatchDeleteStatus]] = None,
	  /** The basic metadata of the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiUiv1beta3CommonOperationMetadata] = None,
	  /** Total number of documents that failed to be deleted in storage. */
		errorDocumentCount: Option[Int] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3SetDefaultProcessorVersionResponse(
	
	)
	
	case class GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutTableRow(
	  /** A table row is a list of table cells. */
		cells: Option[List[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutTableCell]] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3BatchProcessMetadataIndividualProcessStatus(
	  /** The Cloud Storage output destination (in the request as DocumentOutputConfig.GcsOutputConfig.gcs_uri) of the processed document if it was successful, otherwise empty. */
		outputGcsDestination: Option[String] = None,
	  /** The source of the document, same as the input_gcs_source field in the request when the batch process started. */
		inputGcsSource: Option[String] = None,
	  /** The name of the operation triggered by the processed document. If the human review process isn't triggered, this field will be empty. It has the same response type and metadata as the long-running operation returned by the ReviewDocument method. */
		humanReviewOperation: Option[String] = None,
	  /** The status processing the document. */
		status: Option[Schema.GoogleRpcStatus] = None,
	  /** The status of human review on the processed document. */
		humanReviewStatus: Option[Schema.GoogleCloudDocumentaiV1beta3HumanReviewStatus] = None
	)
	
	case class GoogleCloudDocumentaiV1beta3TrainProcessorVersionResponse(
	  /** The resource name of the processor version produced by training. */
		processorVersion: Option[String] = None
	)
	
	object GoogleCloudDocumentaiV1ProcessorVersionGenAiModelInfoCustomGenAiModelInfo {
		enum CustomModelTypeEnum extends Enum[CustomModelTypeEnum] { case CUSTOM_MODEL_TYPE_UNSPECIFIED, VERSIONED_FOUNDATION, FINE_TUNED }
	}
	case class GoogleCloudDocumentaiV1ProcessorVersionGenAiModelInfoCustomGenAiModelInfo(
	  /** The type of custom model created by the user. */
		customModelType: Option[Schema.GoogleCloudDocumentaiV1ProcessorVersionGenAiModelInfoCustomGenAiModelInfo.CustomModelTypeEnum] = None,
	  /** The base processor version ID for the custom model. */
		baseProcessorVersionId: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiUiv1beta3ExportDocumentsResponse(
	
	)
	
	case class GoogleCloudDocumentaiV1beta3ImportProcessorVersionMetadata(
	  /** The basic metadata for the long-running operation. */
		commonMetadata: Option[Schema.GoogleCloudDocumentaiV1beta3CommonOperationMetadata] = None
	)
	
	case class GoogleCloudLocationListLocationsResponse(
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None,
	  /** A list of locations that matches the specified filter in the request. */
		locations: Option[List[Schema.GoogleCloudLocationLocation]] = None
	)
}
