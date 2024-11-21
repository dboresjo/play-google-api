package com.boresjo.play.api.google.contactcenterinsights

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleCloudContactcenterinsightsV1BulkDeleteConversationsResponse(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1AnalysisRule(
	  /** Display Name of the analysis rule. */
		displayName: Option[String] = None,
	  /** Identifier. The resource name of the analysis rule. Format: projects/{project}/locations/{location}/analysisRules/{analysis_rule} */
		name: Option[String] = None,
	  /** If true, apply this rule to conversations. Otherwise, this rule is inactive and saved as a draft. */
		active: Option[Boolean] = None,
	  /** Selector of annotators to run and the phrase matchers to use for conversations that matches the conversation_filter. If not specified, NO annotators will be run. */
		annotatorSelector: Option[Schema.GoogleCloudContactcenterinsightsV1AnnotatorSelector] = None,
	  /** Percentage of conversations that we should apply this analysis setting automatically, between [0, 1]. For example, 0.1 means 10%. Conversations are sampled in a determenestic way. The original runtime_percentage & upload percentage will be replaced by defining filters on the conversation. */
		analysisPercentage: Option[BigDecimal] = None,
	  /** Output only. The most recent time at which this analysis rule was updated. */
		updateTime: Option[String] = None,
	  /** Output only. The time at which this analysis rule was created. */
		createTime: Option[String] = None,
	  /** Filter for the conversations that should apply this analysis rule. An empty filter means this analysis rule applies to all conversations. */
		conversationFilter: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ListConversationsResponse(
	  /** The conversations that match the request. */
		conversations: Option[List[Schema.GoogleCloudContactcenterinsightsV1Conversation]] = None,
	  /** A token which can be sent as `page_token` to retrieve the next page. If this field is set, it means there is another page available. If it is not set, it means no other pages are available. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1AnnotatorSelectorQaConfigScorecardList(
	  /** List of QaScorecardRevisions. */
		qaScorecardRevisions: Option[List[String]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ConversationTranscriptTranscriptSegment(
	  /** For conversations derived from multi-channel audio, this is the channel number corresponding to the audio from that channel. For audioChannelCount = N, its output values can range from '1' to 'N'. A channel tag of 0 indicates that the audio is mono. */
		channelTag: Option[Int] = None,
	  /** A list of the word-specific information for each word in the segment. */
		words: Option[List[Schema.GoogleCloudContactcenterinsightsV1alpha1ConversationTranscriptTranscriptSegmentWordInfo]] = None,
	  /** The text of this segment. */
		text: Option[String] = None,
	  /** The language code of this segment as a [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt) language tag. Example: "en-US". */
		languageCode: Option[String] = None,
	  /** CCAI metadata relating to the current transcript segment. */
		dialogflowSegmentMetadata: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1ConversationTranscriptTranscriptSegmentDialogflowSegmentMetadata] = None,
	  /** The participant of this segment. */
		segmentParticipant: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1ConversationParticipant] = None,
	  /** The time that the message occurred, if provided. */
		messageTime: Option[String] = None,
	  /** The sentiment for this transcript segment. */
		sentiment: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1SentimentData] = None,
	  /** A confidence estimate between 0.0 and 1.0 of the fidelity of this segment. A default value of 0.0 indicates that the value is unset. */
		confidence: Option[BigDecimal] = None
	)
	
	object GoogleCloudContactcenterinsightsV1alpha1Dimension {
		enum DimensionKeyEnum extends Enum[DimensionKeyEnum] { case DIMENSION_KEY_UNSPECIFIED, ISSUE, AGENT, AGENT_TEAM, QA_QUESTION_ID, QA_QUESTION_ANSWER_VALUE, CONVERSATION_PROFILE_ID }
	}
	case class GoogleCloudContactcenterinsightsV1alpha1Dimension(
	  /** Output only. Metadata about the QA question dimension. */
		qaQuestionDimensionMetadata: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1DimensionQaQuestionDimensionMetadata] = None,
	  /** Output only. Metadata about the QA question-answer dimension. */
		qaQuestionAnswerDimensionMetadata: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1DimensionQaQuestionAnswerDimensionMetadata] = None,
	  /** Output only. Metadata about the agent dimension. */
		agentDimensionMetadata: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1DimensionAgentDimensionMetadata] = None,
	  /** The key of the dimension. */
		dimensionKey: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1Dimension.DimensionKeyEnum] = None,
	  /** Output only. Metadata about the issue dimension. */
		issueDimensionMetadata: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1DimensionIssueDimensionMetadata] = None
	)
	
	object GoogleCloudContactcenterinsightsV1EntityMentionData {
		enum TypeEnum extends Enum[TypeEnum] { case MENTION_TYPE_UNSPECIFIED, PROPER, COMMON }
	}
	case class GoogleCloudContactcenterinsightsV1EntityMentionData(
	  /** The type of the entity mention. */
		`type`: Option[Schema.GoogleCloudContactcenterinsightsV1EntityMentionData.TypeEnum] = None,
	  /** The key of this entity in conversation entities. Can be used to retrieve the exact `Entity` this mention is attached to. */
		entityUniqueId: Option[String] = None,
	  /** Sentiment expressed for this mention of the entity. */
		sentiment: Option[Schema.GoogleCloudContactcenterinsightsV1SentimentData] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ImportIssueModelRequestGcsSource(
	  /** Required. Format: `gs:///` */
		objectUri: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1QaAnswer(
	  /** The QaQuestion answered by this answer. */
		qaQuestion: Option[String] = None,
	  /** List of all individual answers given to the question. */
		answerSources: Option[List[Schema.GoogleCloudContactcenterinsightsV1alpha1QaAnswerAnswerSource]] = None,
	  /** Question text. E.g., "Did the agent greet the customer?" */
		questionBody: Option[String] = None,
	  /** The conversation the answer applies to. */
		conversation: Option[String] = None,
	  /** The main answer value, incorporating any manual edits if they exist. */
		answerValue: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1QaAnswerAnswerValue] = None,
	  /** User-defined list of arbitrary tags. Matches the value from QaScorecard.ScorecardQuestion.tags. Used for grouping/organization and for weighting the score of each answer. */
		tags: Option[List[String]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ConversationLevelSilence(
	  /** Percentage of the total conversation spent in silence. */
		silencePercentage: Option[BigDecimal] = None,
	  /** Amount of time calculated to be in silence. */
		silenceDuration: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1IssueModelResult(
	  /** Issue model that generates the result. Format: projects/{project}/locations/{location}/issueModels/{issue_model} */
		issueModel: Option[String] = None,
	  /** All the matched issues. */
		issues: Option[List[Schema.GoogleCloudContactcenterinsightsV1alpha1IssueAssignment]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1Analysis(
	  /** To select the annotators to run and the phrase matchers to use (if any). If not specified, all annotators will be run. */
		annotatorSelector: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1AnnotatorSelector] = None,
	  /** Output only. The result of the analysis, which is populated when the analysis finishes. */
		analysisResult: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1AnalysisResult] = None,
	  /** Output only. The time at which the analysis was requested. */
		requestTime: Option[String] = None,
	  /** Immutable. The resource name of the analysis. Format: projects/{project}/locations/{location}/conversations/{conversation}/analyses/{analysis} */
		name: Option[String] = None,
	  /** Output only. The time at which the analysis was created, which occurs when the long-running operation completes. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ConversationTranscriptTranscriptSegmentWordInfo(
	  /** A confidence estimate between 0.0 and 1.0 of the fidelity of this word. A default value of 0.0 indicates that the value is unset. */
		confidence: Option[BigDecimal] = None,
	  /** The word itself. Includes punctuation marks that surround the word. */
		word: Option[String] = None,
	  /** Time offset of the end of this word relative to the beginning of the total conversation. */
		endOffset: Option[String] = None,
	  /** Time offset of the start of this word relative to the beginning of the total conversation. */
		startOffset: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1IssueModelLabelStats(
	  /** Statistics on each issue. Key is the issue's resource name. */
		issueStats: Option[Map[String, Schema.GoogleCloudContactcenterinsightsV1alpha1IssueModelLabelStatsIssueStats]] = None,
	  /** Number of conversations the issue model has analyzed at this point in time. */
		analyzedConversationsCount: Option[String] = None,
	  /** Number of analyzed conversations for which no issue was applicable at this point in time. */
		unclassifiedConversationsCount: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1QueryMetricsResponseSlice(
	  /** A time series of metric values. This is only populated if the request specifies a time granularity other than NONE. */
		timeSeries: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1QueryMetricsResponseSliceTimeSeries] = None,
	  /** A unique combination of dimensions that this slice represents. */
		dimensions: Option[List[Schema.GoogleCloudContactcenterinsightsV1alpha1Dimension]] = None,
	  /** The total metric value. The interval of this data point is [starting create time, ending create time) from the request. */
		total: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1QueryMetricsResponseSliceDataPoint] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1UndeployIssueModelResponse(
	
	)
	
	object GoogleCloudContactcenterinsightsV1alpha1IngestConversationsRequestGcsSource {
		enum BucketObjectTypeEnum extends Enum[BucketObjectTypeEnum] { case BUCKET_OBJECT_TYPE_UNSPECIFIED, TRANSCRIPT, AUDIO }
	}
	case class GoogleCloudContactcenterinsightsV1alpha1IngestConversationsRequestGcsSource(
	  /** Optional. Custom keys to extract as conversation labels from metadata files in `metadata_bucket_uri`. Keys not included in this field will be ignored. Note that there is a limit of 100 labels per conversation. */
		customMetadataKeys: Option[List[String]] = None,
	  /** Optional. Specifies the type of the objects in `bucket_uri`. */
		bucketObjectType: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1IngestConversationsRequestGcsSource.BucketObjectTypeEnum] = None,
	  /** Required. The Cloud Storage bucket containing source objects. */
		bucketUri: Option[String] = None,
	  /** Optional. The Cloud Storage path to the conversation metadata. Note that: [1] Metadata files are expected to be in JSON format. [2] Metadata and source files (transcripts or audio) must be in separate buckets. [3] A source file and its corresponding metadata file must share the same name to be properly ingested, E.g. `gs://bucket/audio/conversation1.mp3` and `gs://bucket/metadata/conversation1.json`. */
		metadataBucketUri: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ConversationDataSource(
	  /** A Cloud Storage location specification for the audio and transcript. */
		gcsSource: Option[Schema.GoogleCloudContactcenterinsightsV1GcsSource] = None,
	  /** The source when the conversation comes from Dialogflow. */
		dialogflowSource: Option[Schema.GoogleCloudContactcenterinsightsV1DialogflowSource] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1DialogflowIntent(
	  /** The human-readable name of the intent. */
		displayName: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1SentimentData(
	  /** A non-negative number from 0 to infinity which represents the abolute magnitude of sentiment regardless of score. */
		magnitude: Option[BigDecimal] = None,
	  /** The sentiment score between -1.0 (negative) and 1.0 (positive). */
		score: Option[BigDecimal] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ListQaScorecardsResponse(
	  /** The QaScorecards under the parent. */
		qaScorecards: Option[List[Schema.GoogleCloudContactcenterinsightsV1QaScorecard]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1InitializeEncryptionSpecMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Partial errors during initializing operation that might cause the operation output to be incomplete. */
		partialErrors: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Output only. The original request for initialization. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1InitializeEncryptionSpecRequest] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None
	)
	
	object GoogleCloudContactcenterinsightsV1BulkDownloadFeedbackLabelsRequest {
		enum FeedbackLabelTypeEnum extends Enum[FeedbackLabelTypeEnum] { case FEEDBACK_LABEL_TYPE_UNSPECIFIED, QUALITY_AI, TOPIC_MODELING }
	}
	case class GoogleCloudContactcenterinsightsV1BulkDownloadFeedbackLabelsRequest(
	  /** A cloud storage bucket destination. */
		gcsDestination: Option[Schema.GoogleCloudContactcenterinsightsV1BulkDownloadFeedbackLabelsRequestGcsDestination] = None,
	  /** Required. The parent resource for new feedback labels. */
		parent: Option[String] = None,
	  /** Optional. A filter to reduce results to a specific subset. Supports disjunctions (OR) and conjunctions (AND). Supported fields: &#42; `issue_model_id` &#42; `qa_question_id` &#42; `qa_scorecard_id` &#42; `min_create_time` &#42; `max_create_time` &#42; `min_update_time` &#42; `max_update_time` &#42; `feedback_label_type`: QUALITY_AI, TOPIC_MODELING */
		filter: Option[String] = None,
	  /** Optional. The type of feedback labels that will be downloaded. */
		feedbackLabelType: Option[Schema.GoogleCloudContactcenterinsightsV1BulkDownloadFeedbackLabelsRequest.FeedbackLabelTypeEnum] = None,
	  /** Optional. Limits the maximum number of feedback labels that will be downloaded. The first `N` feedback labels will be downloaded. */
		maxDownloadCount: Option[Int] = None,
	  /** Optional. Filter parent conversations to download feedback labels for. When specified, the feedback labels will be downloaded for the conversations that match the filter. If `template_qa_scorecard_id` is set, all the conversations that match the filter will be paired with the questions under the scorecard for labeling. */
		conversationFilter: Option[String] = None,
	  /** Optional. If set, a template for labeling conversations and scorecard questions will be created from the conversation_filter and the questions under the scorecard(s). The feedback label `filter` will be ignored. */
		templateQaScorecardId: Option[List[String]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1HoldData(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1QaScorecardResultQaTagResult(
	  /** The score the tag applies to. */
		score: Option[BigDecimal] = None,
	  /** The tag the score applies to. */
		tag: Option[String] = None,
	  /** The normalized score the tag applies to. */
		normalizedScore: Option[BigDecimal] = None,
	  /** The potential score the tag applies to. */
		potentialScore: Option[BigDecimal] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1IngestConversationsMetadata(
	  /** Output only. The original request for ingest. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1IngestConversationsRequest] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Statistics for IngestConversations operation. */
		ingestConversationsStats: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1IngestConversationsMetadataIngestConversationsStats] = None,
	  /** Output only. Partial errors during ingest operation that might cause the operation output to be incomplete. */
		partialErrors: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1QueryMetricsResponseSliceDataPoint(
	  /** The interval that this data point represents. &#42; If this is the total data point, the interval is [starting create time, ending create time) from the request. &#42; If this a data point from the time series, the interval is [time, time + time granularity from the request). */
		interval: Option[Schema.GoogleTypeInterval] = None,
	  /** The measure related to conversations. */
		conversationMeasure: Option[Schema.GoogleCloudContactcenterinsightsV1QueryMetricsResponseSliceDataPointConversationMeasure] = None
	)
	
	object GoogleCloudContactcenterinsightsV1Entity {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, PERSON, LOCATION, ORGANIZATION, EVENT, WORK_OF_ART, CONSUMER_GOOD, OTHER, PHONE_NUMBER, ADDRESS, DATE, NUMBER, PRICE }
	}
	case class GoogleCloudContactcenterinsightsV1Entity(
	  /** The representative name for the entity. */
		displayName: Option[String] = None,
	  /** The entity type. */
		`type`: Option[Schema.GoogleCloudContactcenterinsightsV1Entity.TypeEnum] = None,
	  /** Metadata associated with the entity. For most entity types, the metadata is a Wikipedia URL (`wikipedia_url`) and Knowledge Graph MID (`mid`), if they are available. For the metadata associated with other entity types, see the Type table below. */
		metadata: Option[Map[String, String]] = None,
	  /** The salience score associated with the entity in the [0, 1.0] range. The salience score for an entity provides information about the importance or centrality of that entity to the entire document text. Scores closer to 0 are less salient, while scores closer to 1.0 are highly salient. */
		salience: Option[BigDecimal] = None,
	  /** The aggregate sentiment expressed for this entity in the conversation. */
		sentiment: Option[Schema.GoogleCloudContactcenterinsightsV1SentimentData] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1UploadConversationMetadata(
	  /** Output only. The original request. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1UploadConversationRequest] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The redaction config applied to the uploaded conversation. */
		appliedRedactionConfig: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1RedactionConfig] = None,
	  /** Output only. The operation name for a successfully created analysis operation, if any. */
		analysisOperation: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ExportInsightsDataMetadata(
	  /** The original request for export. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1ExportInsightsDataRequest] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Partial errors during export operation that might cause the operation output to be incomplete. */
		partialErrors: Option[List[Schema.GoogleRpcStatus]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1InitializeEncryptionSpecRequest(
	  /** Required. The encryption spec used for CMEK encryption. It is required that the kms key is in the same region as the endpoint. The same key will be used for all provisioned resources, if encryption is available. If the `kms_key_name` field is left empty, no encryption will be enforced. */
		encryptionSpec: Option[Schema.GoogleCloudContactcenterinsightsV1EncryptionSpec] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ConversationTranscriptTranscriptSegmentWordInfo(
	  /** Time offset of the start of this word relative to the beginning of the total conversation. */
		startOffset: Option[String] = None,
	  /** The word itself. Includes punctuation marks that surround the word. */
		word: Option[String] = None,
	  /** Time offset of the end of this word relative to the beginning of the total conversation. */
		endOffset: Option[String] = None,
	  /** A confidence estimate between 0.0 and 1.0 of the fidelity of this word. A default value of 0.0 indicates that the value is unset. */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1InterruptionData(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1PhraseMatchRule(
	  /** Specifies whether the phrase must be missing from the transcript segment or present in the transcript segment. */
		negated: Option[Boolean] = None,
	  /** Required. The phrase to be matched. */
		query: Option[String] = None,
	  /** Provides additional information about the rule that specifies how to apply the rule. */
		config: Option[Schema.GoogleCloudContactcenterinsightsV1PhraseMatchRuleConfig] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1AnnotationBoundary(
	  /** The index in the sequence of transcribed pieces of the conversation where the boundary is located. This index starts at zero. */
		transcriptIndex: Option[Int] = None,
	  /** The word index of this boundary with respect to the first word in the transcript piece. This index starts at zero. */
		wordIndex: Option[Int] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1QaQuestionMetrics(
	  /** Output only. Accuracy of the model. Measures the percentage of correct answers the model gave on the test set. */
		accuracy: Option[BigDecimal] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ListQaScorecardRevisionsResponse(
	  /** The QaScorecards under the parent. */
		qaScorecardRevisions: Option[List[Schema.GoogleCloudContactcenterinsightsV1QaScorecardRevision]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1DeleteIssueModelRequest(
	  /** Required. The name of the issue model to delete. */
		name: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1DeployIssueModelResponse(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1EncryptionSpec(
	  /** Immutable. The resource name of the encryption key specification resource. Format: projects/{project}/locations/{location}/encryptionSpec */
		name: Option[String] = None,
	  /** Required. The name of customer-managed encryption key that is used to secure a resource and its sub-resources. If empty, the resource is secured by our default encryption key. Only the key in the same location as this resource is allowed to be used for encryption. Format: `projects/{project}/locations/{location}/keyRings/{keyRing}/cryptoKeys/{key}` */
		kmsKey: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1BulkAnalyzeConversationsRequest(
	  /** Required. The parent resource to create analyses in. */
		parent: Option[String] = None,
	  /** Required. Percentage of selected conversation to analyze, between [0, 100]. */
		analysisPercentage: Option[BigDecimal] = None,
	  /** Required. Filter used to select the subset of conversations to analyze. */
		filter: Option[String] = None,
	  /** To select the annotators to run and the phrase matchers to use (if any). If not specified, all annotators will be run. */
		annotatorSelector: Option[Schema.GoogleCloudContactcenterinsightsV1AnnotatorSelector] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1AnalysisResultCallAnalysisMetadata(
	  /** All the matched phrase matchers in the call. */
		phraseMatchers: Option[Map[String, Schema.GoogleCloudContactcenterinsightsV1PhraseMatchData]] = None,
	  /** A list of call annotations that apply to this call. */
		annotations: Option[List[Schema.GoogleCloudContactcenterinsightsV1CallAnnotation]] = None,
	  /** All the entities in the call. */
		entities: Option[Map[String, Schema.GoogleCloudContactcenterinsightsV1Entity]] = None,
	  /** Overall conversation-level issue modeling result. */
		issueModelResult: Option[Schema.GoogleCloudContactcenterinsightsV1IssueModelResult] = None,
	  /** Overall conversation-level silence during the call. */
		silence: Option[Schema.GoogleCloudContactcenterinsightsV1ConversationLevelSilence] = None,
	  /** Overall conversation-level sentiment for each channel of the call. */
		sentiments: Option[List[Schema.GoogleCloudContactcenterinsightsV1ConversationLevelSentiment]] = None,
	  /** All the matched intents in the call. */
		intents: Option[Map[String, Schema.GoogleCloudContactcenterinsightsV1Intent]] = None,
	  /** Results of scoring QaScorecards. */
		qaScorecardResults: Option[List[Schema.GoogleCloudContactcenterinsightsV1QaScorecardResult]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ConversationCallMetadata(
	  /** The audio channel that contains the agent. */
		agentChannel: Option[Int] = None,
	  /** The audio channel that contains the customer. */
		customerChannel: Option[Int] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1DimensionQaQuestionDimensionMetadata(
	  /** Optional. The QA scorecard ID. */
		qaScorecardId: Option[String] = None,
	  /** Optional. The full body of the question. */
		questionBody: Option[String] = None,
	  /** Optional. The QA question ID. */
		qaQuestionId: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1QueryMetricsMetadata(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1ConversationTranscriptTranscriptSegment(
	  /** For conversations derived from multi-channel audio, this is the channel number corresponding to the audio from that channel. For audioChannelCount = N, its output values can range from '1' to 'N'. A channel tag of 0 indicates that the audio is mono. */
		channelTag: Option[Int] = None,
	  /** The text of this segment. */
		text: Option[String] = None,
	  /** A confidence estimate between 0.0 and 1.0 of the fidelity of this segment. A default value of 0.0 indicates that the value is unset. */
		confidence: Option[BigDecimal] = None,
	  /** CCAI metadata relating to the current transcript segment. */
		dialogflowSegmentMetadata: Option[Schema.GoogleCloudContactcenterinsightsV1ConversationTranscriptTranscriptSegmentDialogflowSegmentMetadata] = None,
	  /** The language code of this segment as a [BCP-47](https://www.rfc-editor.org/rfc/bcp/bcp47.txt) language tag. Example: "en-US". */
		languageCode: Option[String] = None,
	  /** The participant of this segment. */
		segmentParticipant: Option[Schema.GoogleCloudContactcenterinsightsV1ConversationParticipant] = None,
	  /** The time that the message occurred, if provided. */
		messageTime: Option[String] = None,
	  /** The sentiment for this transcript segment. */
		sentiment: Option[Schema.GoogleCloudContactcenterinsightsV1SentimentData] = None,
	  /** A list of the word-specific information for each word in the segment. */
		words: Option[List[Schema.GoogleCloudContactcenterinsightsV1ConversationTranscriptTranscriptSegmentWordInfo]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1CreateAnalysisOperationMetadata(
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. The annotator selector used for the analysis (if any). */
		annotatorSelector: Option[Schema.GoogleCloudContactcenterinsightsV1AnnotatorSelector] = None,
	  /** Output only. The Conversation that this Analysis Operation belongs to. */
		conversation: Option[String] = None,
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1QaQuestionAnswerChoice(
	  /** Boolean value. */
		boolValue: Option[Boolean] = None,
	  /** Numerical value. */
		numValue: Option[BigDecimal] = None,
	  /** Numerical score of the answer, used for generating the overall score of a QaScorecardResult. If the answer uses na_value, this field is unused. */
		score: Option[BigDecimal] = None,
	  /** String value. */
		strValue: Option[String] = None,
	  /** A short string used as an identifier. */
		key: Option[String] = None,
	  /** A value of "Not Applicable (N/A)". If provided, this field may only be set to `true`. If a question receives this answer, it will be excluded from any score calculations. */
		naValue: Option[Boolean] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1BulkUploadFeedbackLabelsRequest(
	  /** Optional. If set, upload will not happen and the labels will be validated. If not set, then default behavior will be to upload the labels after validation is complete. */
		validateOnly: Option[Boolean] = None,
	  /** A cloud storage bucket source. */
		gcsSource: Option[Schema.GoogleCloudContactcenterinsightsV1BulkUploadFeedbackLabelsRequestGcsSource] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ImportIssueModelResponse(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ConversationDataSource(
	  /** A Cloud Storage location specification for the audio and transcript. */
		gcsSource: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1GcsSource] = None,
	  /** The source when the conversation comes from Dialogflow. */
		dialogflowSource: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1DialogflowSource] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1TuneQaScorecardRevisionRequest(
	  /** Optional. Run in validate only mode, no fine tuning will actually run. Data quality validations like training data distributions will run. Even when set to false, the data quality validations will still run but once the validations complete we will proceed with the fine tune, if applicable. */
		validateOnly: Option[Boolean] = None,
	  /** Required. Filter for selecting the feedback labels that needs to be used for training. This filter can be used to limit the feedback labels used for tuning to a feedback labels created or updated for a specific time-window etc. */
		filter: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1AnalysisResultCallAnalysisMetadata(
	  /** Results of scoring QaScorecards. */
		qaScorecardResults: Option[List[Schema.GoogleCloudContactcenterinsightsV1alpha1QaScorecardResult]] = None,
	  /** Overall conversation-level silence during the call. */
		silence: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1ConversationLevelSilence] = None,
	  /** Overall conversation-level issue modeling result. */
		issueModelResult: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1IssueModelResult] = None,
	  /** Overall conversation-level sentiment for each channel of the call. */
		sentiments: Option[List[Schema.GoogleCloudContactcenterinsightsV1alpha1ConversationLevelSentiment]] = None,
	  /** A list of call annotations that apply to this call. */
		annotations: Option[List[Schema.GoogleCloudContactcenterinsightsV1alpha1CallAnnotation]] = None,
	  /** All the matched phrase matchers in the call. */
		phraseMatchers: Option[Map[String, Schema.GoogleCloudContactcenterinsightsV1alpha1PhraseMatchData]] = None,
	  /** All the matched intents in the call. */
		intents: Option[Map[String, Schema.GoogleCloudContactcenterinsightsV1alpha1Intent]] = None,
	  /** All the entities in the call. */
		entities: Option[Map[String, Schema.GoogleCloudContactcenterinsightsV1alpha1Entity]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1AnalysisResult(
	  /** The time at which the analysis ended. */
		endTime: Option[String] = None,
	  /** Call-specific metadata created by the analysis. */
		callAnalysisMetadata: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1AnalysisResultCallAnalysisMetadata] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1IntentMatchData(
	  /** The id of the matched intent. Can be used to retrieve the corresponding intent information. */
		intentUniqueId: Option[String] = None
	)
	
	object GoogleCloudContactcenterinsightsV1alpha1ConversationParticipant {
		enum RoleEnum extends Enum[RoleEnum] { case ROLE_UNSPECIFIED, HUMAN_AGENT, AUTOMATED_AGENT, END_USER, ANY_AGENT }
	}
	case class GoogleCloudContactcenterinsightsV1alpha1ConversationParticipant(
	  /** A user-specified ID representing the participant. */
		userId: Option[String] = None,
	  /** The role of the participant. */
		role: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1ConversationParticipant.RoleEnum] = None,
	  /** Deprecated. Use `dialogflow_participant_name` instead. The name of the Dialogflow participant. Format: projects/{project}/locations/{location}/conversations/{conversation}/participants/{participant} */
		dialogflowParticipant: Option[String] = None,
	  /** The name of the participant provided by Dialogflow. Format: projects/{project}/locations/{location}/conversations/{conversation}/participants/{participant} */
		dialogflowParticipantName: Option[String] = None,
	  /** Obfuscated user ID from Dialogflow. */
		obfuscatedExternalUserId: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1BulkDeleteConversationsRequest(
	  /** Maximum number of conversations to delete. */
		maxDeleteCount: Option[Int] = None,
	  /** Filter used to select the subset of conversations to delete. */
		filter: Option[String] = None,
	  /** If set to true, all of this conversation's analyses will also be deleted. Otherwise, the request will only succeed if the conversation has no analyses. */
		force: Option[Boolean] = None,
	  /** Required. The parent resource to delete conversations from. Format: projects/{project}/locations/{location} */
		parent: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1DialogflowInteractionData(
	  /** The confidence of the match ranging from 0.0 (completely uncertain) to 1.0 (completely certain). */
		confidence: Option[BigDecimal] = None,
	  /** The Dialogflow intent resource path. Format: projects/{project}/agent/{agent}/intents/{intent} */
		dialogflowIntentId: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1QaAnswerAnswerValue(
	  /** A value of "Not Applicable (N/A)". Should only ever be `true`. */
		naValue: Option[Boolean] = None,
	  /** Output only. Numerical score of the answer. */
		score: Option[BigDecimal] = None,
	  /** Output only. The maximum potential score of the question. */
		potentialScore: Option[BigDecimal] = None,
	  /** A short string used as an identifier. Matches the value used in QaQuestion.AnswerChoice.key. */
		key: Option[String] = None,
	  /** String value. */
		strValue: Option[String] = None,
	  /** Output only. Normalized score of the questions. Calculated as score / potential_score. */
		normalizedScore: Option[BigDecimal] = None,
	  /** Numerical value. */
		numValue: Option[BigDecimal] = None,
	  /** Boolean value. */
		boolValue: Option[Boolean] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1CreateIssueModelMetadata(
	  /** The original request for creation. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1CreateIssueModelRequest] = None,
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ExactMatchConfig(
	  /** Whether to consider case sensitivity when performing an exact match. */
		caseSensitive: Option[Boolean] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ListIssueModelsResponse(
	  /** The issue models that match the request. */
		issueModels: Option[List[Schema.GoogleCloudContactcenterinsightsV1IssueModel]] = None
	)
	
	case class GoogleTypeInterval(
	  /** Optional. Inclusive start of the interval. If specified, a Timestamp matching this interval will have to be the same or after the start. */
		startTime: Option[String] = None,
	  /** Optional. Exclusive end of the interval. If specified, a Timestamp matching this interval will have to be before the end. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1QaScorecardResult(
	  /** The QaScorecardRevision scored by this result. */
		qaScorecardRevision: Option[String] = None,
	  /** The normalized score, which is the score divided by the potential score. Any manual edits are included if they exist. */
		normalizedScore: Option[BigDecimal] = None,
	  /** Output only. The timestamp that the revision was created. */
		createTime: Option[String] = None,
	  /** ID of the agent that handled the conversation. */
		agentId: Option[String] = None,
	  /** The maximum potential overall score of the scorecard. Any questions answered using `na_value` are excluded from this calculation. */
		potentialScore: Option[BigDecimal] = None,
	  /** List of all individual score sets. */
		scoreSources: Option[List[Schema.GoogleCloudContactcenterinsightsV1QaScorecardResultScoreSource]] = None,
	  /** The conversation scored by this result. */
		conversation: Option[String] = None,
	  /** Identifier. The name of the scorecard result. Format: projects/{project}/locations/{location}/qaScorecardResults/{qa_scorecard_result} */
		name: Option[String] = None,
	  /** The overall numerical score of the result, incorporating any manual edits if they exist. */
		score: Option[BigDecimal] = None,
	  /** Collection of tags and their scores. */
		qaTagResults: Option[List[Schema.GoogleCloudContactcenterinsightsV1QaScorecardResultQaTagResult]] = None,
	  /** Set of QaAnswers represented in the result. */
		qaAnswers: Option[List[Schema.GoogleCloudContactcenterinsightsV1QaAnswer]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1BulkDeleteConversationsMetadata(
	  /** Partial errors during bulk delete conversations operation that might cause the operation output to be incomplete. */
		partialErrors: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** The time the operation finished running. */
		endTime: Option[String] = None,
	  /** The time the operation was created. */
		createTime: Option[String] = None,
	  /** The original request for bulk delete. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1BulkDeleteConversationsRequest] = None
	)
	
	object GoogleCloudContactcenterinsightsV1PhraseMatchRuleGroup {
		enum TypeEnum extends Enum[TypeEnum] { case PHRASE_MATCH_RULE_GROUP_TYPE_UNSPECIFIED, ALL_OF, ANY_OF }
	}
	case class GoogleCloudContactcenterinsightsV1PhraseMatchRuleGroup(
	  /** A list of phrase match rules that are included in this group. */
		phraseMatchRules: Option[List[Schema.GoogleCloudContactcenterinsightsV1PhraseMatchRule]] = None,
	  /** Required. The type of this phrase match rule group. */
		`type`: Option[Schema.GoogleCloudContactcenterinsightsV1PhraseMatchRuleGroup.TypeEnum] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1InitializeEncryptionSpecResponse(
	
	)
	
	case class GoogleLongrunningListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.GoogleLongrunningOperation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1QaScorecardResult(
	  /** The maximum potential overall score of the scorecard. Any questions answered using `na_value` are excluded from this calculation. */
		potentialScore: Option[BigDecimal] = None,
	  /** Identifier. The name of the scorecard result. Format: projects/{project}/locations/{location}/qaScorecardResults/{qa_scorecard_result} */
		name: Option[String] = None,
	  /** List of all individual score sets. */
		scoreSources: Option[List[Schema.GoogleCloudContactcenterinsightsV1alpha1QaScorecardResultScoreSource]] = None,
	  /** The conversation scored by this result. */
		conversation: Option[String] = None,
	  /** Collection of tags and their scores. */
		qaTagResults: Option[List[Schema.GoogleCloudContactcenterinsightsV1alpha1QaScorecardResultQaTagResult]] = None,
	  /** ID of the agent that handled the conversation. */
		agentId: Option[String] = None,
	  /** The normalized score, which is the score divided by the potential score. Any manual edits are included if they exist. */
		normalizedScore: Option[BigDecimal] = None,
	  /** Set of QaAnswers represented in the result. */
		qaAnswers: Option[List[Schema.GoogleCloudContactcenterinsightsV1alpha1QaAnswer]] = None,
	  /** The QaScorecardRevision scored by this result. */
		qaScorecardRevision: Option[String] = None,
	  /** The overall numerical score of the result, incorporating any manual edits if they exist. */
		score: Option[BigDecimal] = None,
	  /** Output only. The timestamp that the revision was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1QaQuestion(
	  /** User-defined list of arbitrary tags for the question. Used for grouping/organization and for weighting the score of each question. */
		tags: Option[List[String]] = None,
	  /** Output only. The time at which this question was created. */
		createTime: Option[String] = None,
	  /** Metrics of the underlying tuned LLM over a holdout/test set while fine tuning the underlying LLM for the given question. This field will only be populated if and only if the question is part of a scorecard revision that has been tuned. */
		metrics: Option[Schema.GoogleCloudContactcenterinsightsV1QaQuestionMetrics] = None,
	  /** Question text. E.g., "Did the agent greet the customer?" */
		questionBody: Option[String] = None,
	  /** Defines the order of the question within its parent scorecard revision. */
		order: Option[Int] = None,
	  /** Identifier. The resource name of the question. Format: projects/{project}/locations/{location}/qaScorecards/{qa_scorecard}/revisions/{revision}/qaQuestions/{qa_question} */
		name: Option[String] = None,
	  /** A list of valid answers to the question, which the LLM must choose from. */
		answerChoices: Option[List[Schema.GoogleCloudContactcenterinsightsV1QaQuestionAnswerChoice]] = None,
	  /** Short, descriptive string, used in the UI where it's not practical to display the full question body. E.g., "Greeting". */
		abbreviation: Option[String] = None,
	  /** Instructions describing how to determine the answer. */
		answerInstructions: Option[String] = None,
	  /** Output only. The most recent time at which the question was updated. */
		updateTime: Option[String] = None,
	  /** Metadata about the tuning operation for the question.This field will only be populated if and only if the question is part of a scorecard revision that has been tuned. */
		tuningMetadata: Option[Schema.GoogleCloudContactcenterinsightsV1QaQuestionTuningMetadata] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ImportIssueModelMetadata(
	  /** The time the operation was created. */
		createTime: Option[String] = None,
	  /** The time the operation finished running. */
		endTime: Option[String] = None,
	  /** The original import request. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1ImportIssueModelRequest] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1BulkDeleteConversationsResponse(
	
	)
	
	case class GoogleLongrunningOperation(
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1PhraseMatchData(
	  /** The unique identifier (the resource name) of the phrase matcher. */
		phraseMatcher: Option[String] = None,
	  /** The human-readable name of the phrase matcher. */
		displayName: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1Analysis(
	  /** Output only. The result of the analysis, which is populated when the analysis finishes. */
		analysisResult: Option[Schema.GoogleCloudContactcenterinsightsV1AnalysisResult] = None,
	  /** Output only. The time at which the analysis was created, which occurs when the long-running operation completes. */
		createTime: Option[String] = None,
	  /** Immutable. The resource name of the analysis. Format: projects/{project}/locations/{location}/conversations/{conversation}/analyses/{analysis} */
		name: Option[String] = None,
	  /** To select the annotators to run and the phrase matchers to use (if any). If not specified, all annotators will be run. */
		annotatorSelector: Option[Schema.GoogleCloudContactcenterinsightsV1AnnotatorSelector] = None,
	  /** Output only. The time at which the analysis was requested. */
		requestTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ExportIssueModelRequestGcsDestination(
	  /** Required. Format: `gs:///` */
		objectUri: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1QueryMetricsMetadata(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1Intent(
	  /** The human-readable name of the intent. */
		displayName: Option[String] = None,
	  /** The unique identifier of the intent. */
		id: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1CallAnnotation(
	  /** Data specifying an issue match. */
		issueMatchData: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1IssueMatchData] = None,
	  /** Data specifying an intent match. */
		intentMatchData: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1IntentMatchData] = None,
	  /** Data specifying an entity mention. */
		entityMentionData: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1EntityMentionData] = None,
	  /** Data specifying an interruption. */
		interruptionData: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1InterruptionData] = None,
	  /** Data specifying a hold. */
		holdData: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1HoldData] = None,
	  /** The channel of the audio where the annotation occurs. For single-channel audio, this field is not populated. */
		channelTag: Option[Int] = None,
	  /** The boundary in the conversation where the annotation ends, inclusive. */
		annotationEndBoundary: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1AnnotationBoundary] = None,
	  /** The boundary in the conversation where the annotation starts, inclusive. */
		annotationStartBoundary: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1AnnotationBoundary] = None,
	  /** Data specifying silence. */
		silenceData: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1SilenceData] = None,
	  /** Data specifying sentiment. */
		sentimentData: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1SentimentData] = None,
	  /** Data specifying a phrase match. */
		phraseMatchData: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1PhraseMatchData] = None
	)
	
	object GoogleCloudContactcenterinsightsV1Dimension {
		enum DimensionKeyEnum extends Enum[DimensionKeyEnum] { case DIMENSION_KEY_UNSPECIFIED, ISSUE, AGENT, AGENT_TEAM, QA_QUESTION_ID, QA_QUESTION_ANSWER_VALUE, CONVERSATION_PROFILE_ID }
	}
	case class GoogleCloudContactcenterinsightsV1Dimension(
	  /** Output only. Metadata about the QA question dimension. */
		qaQuestionDimensionMetadata: Option[Schema.GoogleCloudContactcenterinsightsV1DimensionQaQuestionDimensionMetadata] = None,
	  /** Output only. Metadata about the issue dimension. */
		issueDimensionMetadata: Option[Schema.GoogleCloudContactcenterinsightsV1DimensionIssueDimensionMetadata] = None,
	  /** Output only. Metadata about the QA question-answer dimension. */
		qaQuestionAnswerDimensionMetadata: Option[Schema.GoogleCloudContactcenterinsightsV1DimensionQaQuestionAnswerDimensionMetadata] = None,
	  /** Output only. Metadata about the agent dimension. */
		agentDimensionMetadata: Option[Schema.GoogleCloudContactcenterinsightsV1DimensionAgentDimensionMetadata] = None,
	  /** The key of the dimension. */
		dimensionKey: Option[Schema.GoogleCloudContactcenterinsightsV1Dimension.DimensionKeyEnum] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1HoldData(
	
	)
	
	object GoogleCloudContactcenterinsightsV1alpha1IngestConversationsRequestTranscriptObjectConfig {
		enum MediumEnum extends Enum[MediumEnum] { case MEDIUM_UNSPECIFIED, PHONE_CALL, CHAT }
	}
	case class GoogleCloudContactcenterinsightsV1alpha1IngestConversationsRequestTranscriptObjectConfig(
	  /** Required. The medium transcript objects represent. */
		medium: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1IngestConversationsRequestTranscriptObjectConfig.MediumEnum] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1IssueMatchData(
	  /** Information about the issue's assignment. */
		issueAssignment: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1IssueAssignment] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1UndeployIssueModelResponse(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1DialogflowSource(
	  /** Cloud Storage URI that points to a file that contains the conversation audio. */
		audioUri: Option[String] = None,
	  /** Output only. The name of the Dialogflow conversation that this conversation resource is derived from. Format: projects/{project}/locations/{location}/conversations/{conversation} */
		dialogflowConversation: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1QaAnswerAnswerValue(
	  /** Output only. Numerical score of the answer. */
		score: Option[BigDecimal] = None,
	  /** A value of "Not Applicable (N/A)". Should only ever be `true`. */
		naValue: Option[Boolean] = None,
	  /** Numerical value. */
		numValue: Option[BigDecimal] = None,
	  /** A short string used as an identifier. Matches the value used in QaQuestion.AnswerChoice.key. */
		key: Option[String] = None,
	  /** String value. */
		strValue: Option[String] = None,
	  /** Output only. Normalized score of the questions. Calculated as score / potential_score. */
		normalizedScore: Option[BigDecimal] = None,
	  /** Output only. The maximum potential score of the question. */
		potentialScore: Option[BigDecimal] = None,
	  /** Boolean value. */
		boolValue: Option[Boolean] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1IssueModelLabelStatsIssueStats(
	  /** Issue resource. Format: projects/{project}/locations/{location}/issueModels/{issue_model}/issues/{issue} */
		issue: Option[String] = None,
	  /** Number of conversations attached to the issue at this point in time. */
		labeledConversationsCount: Option[String] = None,
	  /** Display name of the issue. */
		displayName: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1QaScorecard(
	  /** Output only. The most recent time at which the scorecard was updated. */
		updateTime: Option[String] = None,
	  /** Identifier. The scorecard name. Format: projects/{project}/locations/{location}/qaScorecards/{qa_scorecard} */
		name: Option[String] = None,
	  /** A text description explaining the intent of the scorecard. */
		description: Option[String] = None,
	  /** The user-specified display name of the scorecard. */
		displayName: Option[String] = None,
	  /** Output only. The time at which this scorecard was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1UndeployIssueModelMetadata(
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** The original request for undeployment. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1UndeployIssueModelRequest] = None,
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1DeleteIssueModelMetadata(
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** The original request for deletion. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1DeleteIssueModelRequest] = None,
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ImportIssueModelRequest(
	  /** Optional. If set to true, will create an issue model from the imported file with randomly generated IDs for the issue model and corresponding issues. Otherwise, replaces an existing model with the same ID as the file. */
		createNewModel: Option[Boolean] = None,
	  /** Google Cloud Storage source message. */
		gcsSource: Option[Schema.GoogleCloudContactcenterinsightsV1ImportIssueModelRequestGcsSource] = None,
	  /** Required. The parent resource of the issue model. */
		parent: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1BulkAnalyzeConversationsResponse(
	  /** Count of successful analyses. */
		successfulAnalysisCount: Option[Int] = None,
	  /** Count of failed analyses. */
		failedAnalysisCount: Option[Int] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1CalculateStatsResponse(
	  /** A map associating each custom highlighter resource name with its respective number of matches in the set of conversations. */
		customHighlighterMatches: Option[Map[String, Int]] = None,
	  /** The average number of turns per conversation. */
		averageTurnCount: Option[Int] = None,
	  /** The average duration of all conversations. The average is calculated using only conversations that have a time duration. */
		averageDuration: Option[String] = None,
	  /** The total number of conversations. */
		conversationCount: Option[Int] = None,
	  /** A time series representing the count of conversations created over time that match that requested filter criteria. */
		conversationCountTimeSeries: Option[Schema.GoogleCloudContactcenterinsightsV1CalculateStatsResponseTimeSeries] = None,
	  /** A map associating each issue resource name with its respective number of matches in the set of conversations. Key has the format: `projects//locations//issueModels//issues/` Deprecated, use `issue_matches_stats` field instead. */
		issueMatches: Option[Map[String, Int]] = None,
	  /** A map associating each smart highlighter display name with its respective number of matches in the set of conversations. */
		smartHighlighterMatches: Option[Map[String, Int]] = None,
	  /** A map associating each issue resource name with its respective number of matches in the set of conversations. Key has the format: `projects//locations//issueModels//issues/` */
		issueMatchesStats: Option[Map[String, Schema.GoogleCloudContactcenterinsightsV1IssueModelLabelStatsIssueStats]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1SpeechConfig(
	  /** The fully-qualified Speech Recognizer resource name. Format: `projects/{project_id}/locations/{location}/recognizer/{recognizer}` */
		speechRecognizer: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1PhraseMatchRuleConfig(
	  /** The configuration for the exact match rule. */
		exactMatchConfig: Option[Schema.GoogleCloudContactcenterinsightsV1ExactMatchConfig] = None
	)
	
	object GoogleCloudContactcenterinsightsV1alpha1AnnotatorSelectorSummarizationConfig {
		enum SummarizationModelEnum extends Enum[SummarizationModelEnum] { case SUMMARIZATION_MODEL_UNSPECIFIED, BASELINE_MODEL, BASELINE_MODEL_V2_0 }
	}
	case class GoogleCloudContactcenterinsightsV1alpha1AnnotatorSelectorSummarizationConfig(
	  /** Default summarization model to be used. */
		summarizationModel: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1AnnotatorSelectorSummarizationConfig.SummarizationModelEnum] = None,
	  /** Resource name of the Dialogflow conversation profile. Format: projects/{project}/locations/{location}/conversationProfiles/{conversation_profile} */
		conversationProfile: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1IngestConversationsRequestConversationConfig(
	  /** Optional. Indicates which of the channels, 1 or 2, contains the agent. Note that this must be set for conversations to be properly displayed and analyzed. */
		customerChannel: Option[Int] = None,
	  /** Optional. An opaque, user-specified string representing a human agent who handled all conversations in the import. Note that this will be overridden if per-conversation metadata is provided through the `metadata_bucket_uri`. */
		agentId: Option[String] = None,
	  /** Optional. Indicates which of the channels, 1 or 2, contains the agent. Note that this must be set for conversations to be properly displayed and analyzed. */
		agentChannel: Option[Int] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ConversationCallMetadata(
	  /** The audio channel that contains the agent. */
		agentChannel: Option[Int] = None,
	  /** The audio channel that contains the customer. */
		customerChannel: Option[Int] = None
	)
	
	object GoogleCloudContactcenterinsightsV1ConversationQualityMetadataAgentInfo {
		enum AgentTypeEnum extends Enum[AgentTypeEnum] { case ROLE_UNSPECIFIED, HUMAN_AGENT, AUTOMATED_AGENT, END_USER, ANY_AGENT }
	}
	case class GoogleCloudContactcenterinsightsV1ConversationQualityMetadataAgentInfo(
	  /** The agent's name. */
		displayName: Option[String] = None,
	  /** A user-specified string representing the agent. */
		agentId: Option[String] = None,
	  /** The agent type, e.g. HUMAN_AGENT. */
		agentType: Option[Schema.GoogleCloudContactcenterinsightsV1ConversationQualityMetadataAgentInfo.AgentTypeEnum] = None,
	  /** A user-provided string indicating the outcome of the agent's segment of the call. */
		dispositionCode: Option[String] = None,
	  /** A user-specified string representing the agent's team. */
		team: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1SentimentData(
	  /** A non-negative number from 0 to infinity which represents the abolute magnitude of sentiment regardless of score. */
		magnitude: Option[BigDecimal] = None,
	  /** The sentiment score between -1.0 (negative) and 1.0 (positive). */
		score: Option[BigDecimal] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ListAnalysesResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The analyses that match the request. */
		analyses: Option[List[Schema.GoogleCloudContactcenterinsightsV1Analysis]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ConversationTranscript(
	  /** A list of sequential transcript segments that comprise the conversation. */
		transcriptSegments: Option[List[Schema.GoogleCloudContactcenterinsightsV1ConversationTranscriptTranscriptSegment]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1AnnotatorSelector(
	  /** Configuration for the summarization annotator. */
		summarizationConfig: Option[Schema.GoogleCloudContactcenterinsightsV1AnnotatorSelectorSummarizationConfig] = None,
	  /** Whether to run the issue model annotator. A model should have already been deployed for this to take effect. */
		runIssueModelAnnotator: Option[Boolean] = None,
	  /** Whether to run the summarization annotator. */
		runSummarizationAnnotator: Option[Boolean] = None,
	  /** Whether to run the sentiment annotator. */
		runSentimentAnnotator: Option[Boolean] = None,
	  /** Whether to run the intent annotator. */
		runIntentAnnotator: Option[Boolean] = None,
	  /** Configuration for the QA annotator. */
		qaConfig: Option[Schema.GoogleCloudContactcenterinsightsV1AnnotatorSelectorQaConfig] = None,
	  /** Whether to run the active phrase matcher annotator(s). */
		runPhraseMatcherAnnotator: Option[Boolean] = None,
	  /** Whether to run the silence annotator. */
		runSilenceAnnotator: Option[Boolean] = None,
	  /** The list of phrase matchers to run. If not provided, all active phrase matchers will be used. If inactive phrase matchers are provided, they will not be used. Phrase matchers will be run only if run_phrase_matcher_annotator is set to true. Format: projects/{project}/locations/{location}/phraseMatchers/{phrase_matcher} */
		phraseMatchers: Option[List[String]] = None,
	  /** Whether to run the QA annotator. */
		runQaAnnotator: Option[Boolean] = None,
	  /** Whether to run the entity annotator. */
		runEntityAnnotator: Option[Boolean] = None,
	  /** Whether to run the interruption annotator. */
		runInterruptionAnnotator: Option[Boolean] = None,
	  /** The issue model to run. If not provided, the most recently deployed topic model will be used. The provided issue model will only be used for inference if the issue model is deployed and if run_issue_model_annotator is set to true. If more than one issue model is provided, only the first provided issue model will be used for inference. */
		issueModels: Option[List[String]] = None
	)
	
	object GoogleCloudContactcenterinsightsV1alpha1EntityMentionData {
		enum TypeEnum extends Enum[TypeEnum] { case MENTION_TYPE_UNSPECIFIED, PROPER, COMMON }
	}
	case class GoogleCloudContactcenterinsightsV1alpha1EntityMentionData(
	  /** Sentiment expressed for this mention of the entity. */
		sentiment: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1SentimentData] = None,
	  /** The key of this entity in conversation entities. Can be used to retrieve the exact `Entity` this mention is attached to. */
		entityUniqueId: Option[String] = None,
	  /** The type of the entity mention. */
		`type`: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1EntityMentionData.TypeEnum] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ImportIssueModelResponse(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1QueryMetricsResponseSliceDataPointConversationMeasureQaTagScore(
	  /** Average tag normalized score per tag. */
		averageTagNormalizedScore: Option[BigDecimal] = None,
	  /** Tag name. */
		tag: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1SpeechConfig(
	  /** The fully-qualified Speech Recognizer resource name. Format: `projects/{project_id}/locations/{location}/recognizer/{recognizer}` */
		speechRecognizer: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1DimensionQaQuestionAnswerDimensionMetadata(
	  /** Optional. The QA scorecard ID. */
		qaScorecardId: Option[String] = None,
	  /** Optional. The full body of the question. */
		questionBody: Option[String] = None,
	  /** Optional. The full body of the question. */
		answerValue: Option[String] = None,
	  /** Optional. The QA question ID. */
		qaQuestionId: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1FeedbackLabel(
	  /** Output only. Update time of the label. */
		updateTime: Option[String] = None,
	  /** Resource name of the resource to be labeled. */
		labeledResource: Option[String] = None,
	  /** Immutable. Resource name of the FeedbackLabel. Format: projects/{project}/locations/{location}/conversations/{conversation}/feedbackLabels/{feedback_label} */
		name: Option[String] = None,
	  /** Output only. Create time of the label. */
		createTime: Option[String] = None,
	  /** String label. */
		label: Option[String] = None,
	  /** QaAnswer label. */
		qaAnswerLabel: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1QaAnswerAnswerValue] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1SmartComposeSuggestionData(
	  /** Map that contains metadata about the Smart Compose suggestion and the document from which it originates. */
		metadata: Option[Map[String, String]] = None,
	  /** The name of the answer record. Format: projects/{project}/locations/{location}/answerRecords/{answer_record} */
		queryRecord: Option[String] = None,
	  /** The content of the suggestion. */
		suggestion: Option[String] = None,
	  /** The system's confidence score that this suggestion is a good match for this conversation, ranging from 0.0 (completely uncertain) to 1.0 (completely certain). */
		confidenceScore: Option[BigDecimal] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1AnnotatorSelector(
	  /** Whether to run the active phrase matcher annotator(s). */
		runPhraseMatcherAnnotator: Option[Boolean] = None,
	  /** Whether to run the summarization annotator. */
		runSummarizationAnnotator: Option[Boolean] = None,
	  /** Whether to run the QA annotator. */
		runQaAnnotator: Option[Boolean] = None,
	  /** Whether to run the silence annotator. */
		runSilenceAnnotator: Option[Boolean] = None,
	  /** Whether to run the interruption annotator. */
		runInterruptionAnnotator: Option[Boolean] = None,
	  /** Whether to run the entity annotator. */
		runEntityAnnotator: Option[Boolean] = None,
	  /** The list of phrase matchers to run. If not provided, all active phrase matchers will be used. If inactive phrase matchers are provided, they will not be used. Phrase matchers will be run only if run_phrase_matcher_annotator is set to true. Format: projects/{project}/locations/{location}/phraseMatchers/{phrase_matcher} */
		phraseMatchers: Option[List[String]] = None,
	  /** Whether to run the issue model annotator. A model should have already been deployed for this to take effect. */
		runIssueModelAnnotator: Option[Boolean] = None,
	  /** Configuration for the summarization annotator. */
		summarizationConfig: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1AnnotatorSelectorSummarizationConfig] = None,
	  /** Configuration for the QA annotator. */
		qaConfig: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1AnnotatorSelectorQaConfig] = None,
	  /** Whether to run the sentiment annotator. */
		runSentimentAnnotator: Option[Boolean] = None,
	  /** Whether to run the intent annotator. */
		runIntentAnnotator: Option[Boolean] = None,
	  /** The issue model to run. If not provided, the most recently deployed topic model will be used. The provided issue model will only be used for inference if the issue model is deployed and if run_issue_model_annotator is set to true. If more than one issue model is provided, only the first provided issue model will be used for inference. */
		issueModels: Option[List[String]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1CallAnnotation(
	  /** Data specifying an entity mention. */
		entityMentionData: Option[Schema.GoogleCloudContactcenterinsightsV1EntityMentionData] = None,
	  /** Data specifying an issue match. */
		issueMatchData: Option[Schema.GoogleCloudContactcenterinsightsV1IssueMatchData] = None,
	  /** The boundary in the conversation where the annotation ends, inclusive. */
		annotationEndBoundary: Option[Schema.GoogleCloudContactcenterinsightsV1AnnotationBoundary] = None,
	  /** Data specifying a hold. */
		holdData: Option[Schema.GoogleCloudContactcenterinsightsV1HoldData] = None,
	  /** Data specifying an interruption. */
		interruptionData: Option[Schema.GoogleCloudContactcenterinsightsV1InterruptionData] = None,
	  /** Data specifying silence. */
		silenceData: Option[Schema.GoogleCloudContactcenterinsightsV1SilenceData] = None,
	  /** Data specifying an intent match. */
		intentMatchData: Option[Schema.GoogleCloudContactcenterinsightsV1IntentMatchData] = None,
	  /** The boundary in the conversation where the annotation starts, inclusive. */
		annotationStartBoundary: Option[Schema.GoogleCloudContactcenterinsightsV1AnnotationBoundary] = None,
	  /** The channel of the audio where the annotation occurs. For single-channel audio, this field is not populated. */
		channelTag: Option[Int] = None,
	  /** Data specifying sentiment. */
		sentimentData: Option[Schema.GoogleCloudContactcenterinsightsV1SentimentData] = None,
	  /** Data specifying a phrase match. */
		phraseMatchData: Option[Schema.GoogleCloudContactcenterinsightsV1PhraseMatchData] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1DialogflowSource(
	  /** Output only. The name of the Dialogflow conversation that this conversation resource is derived from. Format: projects/{project}/locations/{location}/conversations/{conversation} */
		dialogflowConversation: Option[String] = None,
	  /** Cloud Storage URI that points to a file that contains the conversation audio. */
		audioUri: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1RedactionConfig(
	  /** The fully-qualified DLP deidentify template resource name. Format: `projects/{project}/deidentifyTemplates/{template}` */
		deidentifyTemplate: Option[String] = None,
	  /** The fully-qualified DLP inspect template resource name. Format: `projects/{project}/locations/{location}/inspectTemplates/{template}` */
		inspectTemplate: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1CalculateStatsResponseTimeSeriesInterval(
	  /** The number of conversations created in this interval. */
		conversationCount: Option[Int] = None,
	  /** The start time of this interval. */
		startTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1SmartComposeSuggestionData(
	  /** The content of the suggestion. */
		suggestion: Option[String] = None,
	  /** Map that contains metadata about the Smart Compose suggestion and the document from which it originates. */
		metadata: Option[Map[String, String]] = None,
	  /** The name of the answer record. Format: projects/{project}/locations/{location}/answerRecords/{answer_record} */
		queryRecord: Option[String] = None,
	  /** The system's confidence score that this suggestion is a good match for this conversation, ranging from 0.0 (completely uncertain) to 1.0 (completely certain). */
		confidenceScore: Option[BigDecimal] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1DeployIssueModelMetadata(
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** The original request for deployment. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1DeployIssueModelRequest] = None,
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1IngestConversationsMetadataIngestConversationsStats(
	  /** Output only. The number of new conversations added during this ingest operation. */
		successfulIngestCount: Option[Int] = None,
	  /** Output only. The number of objects skipped because another conversation with the same transcript uri had already been ingested. */
		duplicatesSkippedCount: Option[Int] = None,
	  /** Output only. The number of objects which were unable to be ingested due to errors. The errors are populated in the partial_errors field. */
		failedIngestCount: Option[Int] = None,
	  /** Output only. The number of objects processed during the ingest operation. */
		processedObjectCount: Option[Int] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1BulkAnalyzeConversationsRequest(
	  /** Required. Percentage of selected conversation to analyze, between [0, 100]. */
		analysisPercentage: Option[BigDecimal] = None,
	  /** To select the annotators to run and the phrase matchers to use (if any). If not specified, all annotators will be run. */
		annotatorSelector: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1AnnotatorSelector] = None,
	  /** Required. Filter used to select the subset of conversations to analyze. */
		filter: Option[String] = None,
	  /** Required. The parent resource to create analyses in. */
		parent: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1AnnotatorSelectorQaConfig(
	  /** A manual list of scorecards to score. */
		scorecardList: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1AnnotatorSelectorQaConfigScorecardList] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ConversationLevelSentiment(
	  /** The channel of the audio that the data applies to. */
		channelTag: Option[Int] = None,
	  /** Data specifying sentiment. */
		sentimentData: Option[Schema.GoogleCloudContactcenterinsightsV1SentimentData] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1DeployIssueModelResponse(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ListAllFeedbackLabelsResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The feedback labels that match the request. */
		feedbackLabels: Option[List[Schema.GoogleCloudContactcenterinsightsV1alpha1FeedbackLabel]] = None
	)
	
	object GoogleCloudContactcenterinsightsV1alpha1IssueModel {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, UNDEPLOYED, DEPLOYING, DEPLOYED, UNDEPLOYING, DELETING }
		enum ModelTypeEnum extends Enum[ModelTypeEnum] { case MODEL_TYPE_UNSPECIFIED, TYPE_V1, TYPE_V2 }
	}
	case class GoogleCloudContactcenterinsightsV1alpha1IssueModel(
	  /** Immutable. The resource name of the issue model. Format: projects/{project}/locations/{location}/issueModels/{issue_model} */
		name: Option[String] = None,
	  /** Output only. Number of issues in this issue model. */
		issueCount: Option[String] = None,
	  /** Configs for the input data that used to create the issue model. */
		inputDataConfig: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1IssueModelInputDataConfig] = None,
	  /** Output only. Immutable. The issue model's label statistics on its training data. */
		trainingStats: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1IssueModelLabelStats] = None,
	  /** Output only. The most recent time at which the issue model was updated. */
		updateTime: Option[String] = None,
	  /** Output only. State of the model. */
		state: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1IssueModel.StateEnum] = None,
	  /** Language of the model. */
		languageCode: Option[String] = None,
	  /** Type of the model. */
		modelType: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1IssueModel.ModelTypeEnum] = None,
	  /** The representative name for the issue model. */
		displayName: Option[String] = None,
	  /** Output only. The time at which this issue model was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1CreateIssueModelRequest(
	  /** Required. The parent resource of the issue model. */
		parent: Option[String] = None,
	  /** Required. The issue model to create. */
		issueModel: Option[Schema.GoogleCloudContactcenterinsightsV1IssueModel] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1FaqAnswerData(
	  /** The corresponding FAQ question. */
		question: Option[String] = None,
	  /** The name of the answer record. Format: projects/{project}/locations/{location}/answerRecords/{answer_record} */
		queryRecord: Option[String] = None,
	  /** Map that contains metadata about the FAQ answer and the document that it originates from. */
		metadata: Option[Map[String, String]] = None,
	  /** The knowledge document that this answer was extracted from. Format: projects/{project}/knowledgeBases/{knowledge_base}/documents/{document}. */
		source: Option[String] = None,
	  /** The piece of text from the `source` knowledge base document. */
		answer: Option[String] = None,
	  /** The system's confidence score that this answer is a good match for this conversation, ranging from 0.0 (completely uncertain) to 1.0 (completely certain). */
		confidenceScore: Option[BigDecimal] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1InitializeEncryptionSpecResponse(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1DimensionAgentDimensionMetadata(
	  /** Optional. A user-specified string representing the agent's team. */
		agentTeam: Option[String] = None,
	  /** Optional. The agent's name */
		agentDisplayName: Option[String] = None,
	  /** Optional. A user-specified string representing the agent. */
		agentId: Option[String] = None
	)
	
	object GoogleCloudContactcenterinsightsV1RuntimeAnnotationUserInput {
		enum QuerySourceEnum extends Enum[QuerySourceEnum] { case QUERY_SOURCE_UNSPECIFIED, AGENT_QUERY, SUGGESTED_QUERY }
	}
	case class GoogleCloudContactcenterinsightsV1RuntimeAnnotationUserInput(
	  /** The resource name of associated generator. Format: `projects//locations//generators/` */
		generatorName: Option[String] = None,
	  /** Query text. Article Search uses this to store the input query used to generate the search results. */
		query: Option[String] = None,
	  /** Query source for the answer. */
		querySource: Option[Schema.GoogleCloudContactcenterinsightsV1RuntimeAnnotationUserInput.QuerySourceEnum] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ExportIssueModelMetadata(
	  /** The time the operation was created. */
		createTime: Option[String] = None,
	  /** The time the operation finished running. */
		endTime: Option[String] = None,
	  /** The original export request. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1ExportIssueModelRequest] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ImportIssueModelRequest(
	  /** Google Cloud Storage source message. */
		gcsSource: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1ImportIssueModelRequestGcsSource] = None,
	  /** Required. The parent resource of the issue model. */
		parent: Option[String] = None,
	  /** Optional. If set to true, will create an issue model from the imported file with randomly generated IDs for the issue model and corresponding issues. Otherwise, replaces an existing model with the same ID as the file. */
		createNewModel: Option[Boolean] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1BulkDeleteConversationsRequest(
	  /** If set to true, all of this conversation's analyses will also be deleted. Otherwise, the request will only succeed if the conversation has no analyses. */
		force: Option[Boolean] = None,
	  /** Required. The parent resource to delete conversations from. Format: projects/{project}/locations/{location} */
		parent: Option[String] = None,
	  /** Maximum number of conversations to delete. */
		maxDeleteCount: Option[Int] = None,
	  /** Filter used to select the subset of conversations to delete. */
		filter: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1SmartReplyData(
	  /** The name of the answer record. Format: projects/{project}/locations/{location}/answerRecords/{answer_record} */
		queryRecord: Option[String] = None,
	  /** The system's confidence score that this reply is a good match for this conversation, ranging from 0.0 (completely uncertain) to 1.0 (completely certain). */
		confidenceScore: Option[BigDecimal] = None,
	  /** The content of the reply. */
		reply: Option[String] = None,
	  /** Map that contains metadata about the Smart Reply and the document from which it originates. */
		metadata: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1GcsSource(
	  /** Immutable. Cloud Storage URI that points to a file that contains the conversation transcript. */
		transcriptUri: Option[String] = None,
	  /** Cloud Storage URI that points to a file that contains the conversation audio. */
		audioUri: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1DimensionQaQuestionDimensionMetadata(
	  /** Optional. The QA question ID. */
		qaQuestionId: Option[String] = None,
	  /** Optional. The QA scorecard ID. */
		qaScorecardId: Option[String] = None,
	  /** Optional. The full body of the question. */
		questionBody: Option[String] = None
	)
	
	object GoogleCloudContactcenterinsightsV1IngestConversationsRequestGcsSource {
		enum BucketObjectTypeEnum extends Enum[BucketObjectTypeEnum] { case BUCKET_OBJECT_TYPE_UNSPECIFIED, TRANSCRIPT, AUDIO }
	}
	case class GoogleCloudContactcenterinsightsV1IngestConversationsRequestGcsSource(
	  /** Required. The Cloud Storage bucket containing source objects. */
		bucketUri: Option[String] = None,
	  /** Optional. Custom keys to extract as conversation labels from metadata files in `metadata_bucket_uri`. Keys not included in this field will be ignored. Note that there is a limit of 100 labels per conversation. */
		customMetadataKeys: Option[List[String]] = None,
	  /** Optional. The Cloud Storage path to the conversation metadata. Note that: [1] Metadata files are expected to be in JSON format. [2] Metadata and source files (transcripts or audio) must be in separate buckets. [3] A source file and its corresponding metadata file must share the same name to be properly ingested, E.g. `gs://bucket/audio/conversation1.mp3` and `gs://bucket/metadata/conversation1.json`. */
		metadataBucketUri: Option[String] = None,
	  /** Optional. Specifies the type of the objects in `bucket_uri`. */
		bucketObjectType: Option[Schema.GoogleCloudContactcenterinsightsV1IngestConversationsRequestGcsSource.BucketObjectTypeEnum] = None
	)
	
	object GoogleCloudContactcenterinsightsV1alpha1IssueModelInputDataConfig {
		enum MediumEnum extends Enum[MediumEnum] { case MEDIUM_UNSPECIFIED, PHONE_CALL, CHAT }
	}
	case class GoogleCloudContactcenterinsightsV1alpha1IssueModelInputDataConfig(
	  /** Output only. Number of conversations used in training. Output only. */
		trainingConversationsCount: Option[String] = None,
	  /** Medium of conversations used in training data. This field is being deprecated. To specify the medium to be used in training a new issue model, set the `medium` field on `filter`. */
		medium: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1IssueModelInputDataConfig.MediumEnum] = None,
	  /** A filter to reduce the conversations used for training the model to a specific subset. */
		filter: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1UndeployIssueModelRequest(
	  /** Required. The issue model to undeploy. */
		name: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1DeployIssueModelRequest(
	  /** Required. The issue model to deploy. */
		name: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1DeleteIssueModelMetadata(
	  /** The original request for deletion. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1DeleteIssueModelRequest] = None,
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ExportInsightsDataRequestBigQueryDestination(
	  /** A project ID or number. If specified, then export will attempt to write data to this project instead of the resource project. Otherwise, the resource project will be used. */
		projectId: Option[String] = None,
	  /** The BigQuery table name to which the insights data should be written. If this table does not exist, the export call returns an INVALID_ARGUMENT error. */
		table: Option[String] = None,
	  /** Required. The name of the BigQuery dataset that the snapshot result should be exported to. If this dataset does not exist, the export call returns an INVALID_ARGUMENT error. */
		dataset: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1AnnotatorSelectorQaConfig(
	  /** A manual list of scorecards to score. */
		scorecardList: Option[Schema.GoogleCloudContactcenterinsightsV1AnnotatorSelectorQaConfigScorecardList] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ListIssuesResponse(
	  /** The issues that match the request. */
		issues: Option[List[Schema.GoogleCloudContactcenterinsightsV1Issue]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1BulkDeleteConversationsMetadata(
	  /** The time the operation was created. */
		createTime: Option[String] = None,
	  /** The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Partial errors during bulk delete conversations operation that might cause the operation output to be incomplete. */
		partialErrors: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** The original request for bulk delete. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1BulkDeleteConversationsRequest] = None
	)
	
	object GoogleCloudContactcenterinsightsV1alpha1Entity {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, PERSON, LOCATION, ORGANIZATION, EVENT, WORK_OF_ART, CONSUMER_GOOD, OTHER, PHONE_NUMBER, ADDRESS, DATE, NUMBER, PRICE }
	}
	case class GoogleCloudContactcenterinsightsV1alpha1Entity(
	  /** Metadata associated with the entity. For most entity types, the metadata is a Wikipedia URL (`wikipedia_url`) and Knowledge Graph MID (`mid`), if they are available. For the metadata associated with other entity types, see the Type table below. */
		metadata: Option[Map[String, String]] = None,
	  /** The aggregate sentiment expressed for this entity in the conversation. */
		sentiment: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1SentimentData] = None,
	  /** The entity type. */
		`type`: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1Entity.TypeEnum] = None,
	  /** The salience score associated with the entity in the [0, 1.0] range. The salience score for an entity provides information about the importance or centrality of that entity to the entire document text. Scores closer to 0 are less salient, while scores closer to 1.0 are highly salient. */
		salience: Option[BigDecimal] = None,
	  /** The representative name for the entity. */
		displayName: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ExportIssueModelResponse(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1ConversationQualityMetadata(
	  /** An arbitrary integer value indicating the customer's satisfaction rating. */
		customerSatisfactionRating: Option[Int] = None,
	  /** Information about agents involved in the call. */
		agentInfo: Option[List[Schema.GoogleCloudContactcenterinsightsV1ConversationQualityMetadataAgentInfo]] = None,
	  /** The amount of time the customer waited to connect with an agent. */
		waitDuration: Option[String] = None,
	  /** An arbitrary string value specifying the menu path the customer took. */
		menuPath: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1IngestConversationsRequestConversationConfig(
	  /** Optional. Indicates which of the channels, 1 or 2, contains the agent. Note that this must be set for conversations to be properly displayed and analyzed. */
		customerChannel: Option[Int] = None,
	  /** Optional. Indicates which of the channels, 1 or 2, contains the agent. Note that this must be set for conversations to be properly displayed and analyzed. */
		agentChannel: Option[Int] = None,
	  /** Optional. An opaque, user-specified string representing a human agent who handled all conversations in the import. Note that this will be overridden if per-conversation metadata is provided through the `metadata_bucket_uri`. */
		agentId: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1DialogflowInteractionData(
	  /** The Dialogflow intent resource path. Format: projects/{project}/agent/{agent}/intents/{intent} */
		dialogflowIntentId: Option[String] = None,
	  /** The confidence of the match ranging from 0.0 (completely uncertain) to 1.0 (completely certain). */
		confidence: Option[BigDecimal] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ListAllFeedbackLabelsResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The feedback labels that match the request. */
		feedbackLabels: Option[List[Schema.GoogleCloudContactcenterinsightsV1FeedbackLabel]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1QueryMetricsResponseSliceDataPointConversationMeasure(
	  /** Average QA normalized score averaged for questions averaged across all revisions of the parent scorecard. Will be only populated if the request specifies a dimension of QA_QUESTION_ID. */
		averageQaQuestionNormalizedScore: Option[BigDecimal] = None,
	  /** Average QA normalized score for all the tags. */
		qaTagScores: Option[List[Schema.GoogleCloudContactcenterinsightsV1QueryMetricsResponseSliceDataPointConversationMeasureQaTagScore]] = None,
	  /** Average QA normalized score. Will exclude 0's in average calculation. */
		averageQaNormalizedScore: Option[BigDecimal] = None,
	  /** The average agent's sentiment score. */
		averageAgentSentimentScore: Option[BigDecimal] = None,
	  /** The conversation count. */
		conversationCount: Option[Int] = None,
	  /** The average turn count. */
		averageTurnCount: Option[BigDecimal] = None,
	  /** The average customer satisfaction rating. */
		averageCustomerSatisfactionRating: Option[BigDecimal] = None,
	  /** The average duration. */
		averageDuration: Option[String] = None,
	  /** The average client's sentiment score. */
		averageClientSentimentScore: Option[BigDecimal] = None,
	  /** The average silence percentage. */
		averageSilencePercentage: Option[BigDecimal] = None
	)
	
	object GoogleCloudContactcenterinsightsV1alpha1AnswerFeedback {
		enum CorrectnessLevelEnum extends Enum[CorrectnessLevelEnum] { case CORRECTNESS_LEVEL_UNSPECIFIED, NOT_CORRECT, PARTIALLY_CORRECT, FULLY_CORRECT }
	}
	case class GoogleCloudContactcenterinsightsV1alpha1AnswerFeedback(
	  /** Indicates whether an answer or item was displayed to the human agent in the agent desktop UI. */
		displayed: Option[Boolean] = None,
	  /** Indicates whether an answer or item was clicked by the human agent. */
		clicked: Option[Boolean] = None,
	  /** The correctness level of an answer. */
		correctnessLevel: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1AnswerFeedback.CorrectnessLevelEnum] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ConversationSummarizationSuggestionData(
	  /** The summarization content that is divided into sections. The key is the section's name and the value is the section's content. There is no specific format for the key or value. */
		textSections: Option[Map[String, String]] = None,
	  /** A map that contains metadata about the summarization and the document from which it originates. */
		metadata: Option[Map[String, String]] = None,
	  /** The name of the answer record. Format: projects/{project}/locations/{location}/answerRecords/{answer_record} */
		answerRecord: Option[String] = None,
	  /** The confidence score of the summarization. */
		confidence: Option[BigDecimal] = None,
	  /** The name of the model that generates this summary. Format: projects/{project}/locations/{location}/conversationModels/{conversation_model} */
		conversationModel: Option[String] = None,
	  /** The summarization content that is concatenated into one string. */
		text: Option[String] = None
	)
	
	object GoogleCloudContactcenterinsightsV1QaQuestionTuningMetadata {
		enum DatasetValidationWarningsEnum extends Enum[DatasetValidationWarningsEnum] { case DATASET_VALIDATION_WARNING_UNSPECIFIED, TOO_MANY_INVALID_FEEDBACK_LABELS, INSUFFICIENT_FEEDBACK_LABELS, INSUFFICIENT_FEEDBACK_LABELS_PER_ANSWER, ALL_FEEDBACK_LABELS_HAVE_THE_SAME_ANSWER }
	}
	case class GoogleCloudContactcenterinsightsV1QaQuestionTuningMetadata(
	  /** Error status of the tuning operation for the question. Will only be set if the tuning operation failed. */
		tuningError: Option[String] = None,
	  /** Total number of valid labels provided for the question at the time of tuining. */
		totalValidLabelCount: Option[String] = None,
	  /** A list of any applicable data validation warnings about the question's feedback labels. */
		datasetValidationWarnings: Option[List[Schema.GoogleCloudContactcenterinsightsV1QaQuestionTuningMetadata.DatasetValidationWarningsEnum]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1DimensionAgentDimensionMetadata(
	  /** Optional. A user-specified string representing the agent's team. */
		agentTeam: Option[String] = None,
	  /** Optional. A user-specified string representing the agent. */
		agentId: Option[String] = None,
	  /** Optional. The agent's name */
		agentDisplayName: Option[String] = None
	)
	
	object GoogleCloudContactcenterinsightsV1Conversation {
		enum MediumEnum extends Enum[MediumEnum] { case MEDIUM_UNSPECIFIED, PHONE_CALL, CHAT }
	}
	case class GoogleCloudContactcenterinsightsV1Conversation(
	  /** Output only. The number of turns in the conversation. */
		turnCount: Option[Int] = None,
	  /** Output only. The time at which the conversation was created. */
		createTime: Option[String] = None,
	  /** Output only. The conversation's latest analysis, if one exists. */
		latestAnalysis: Option[Schema.GoogleCloudContactcenterinsightsV1Analysis] = None,
	  /** The source of the audio and transcription for the conversation. */
		dataSource: Option[Schema.GoogleCloudContactcenterinsightsV1ConversationDataSource] = None,
	  /** A user-specified language code for the conversation. */
		languageCode: Option[String] = None,
	  /** Output only. Latest summary of the conversation. */
		latestSummary: Option[Schema.GoogleCloudContactcenterinsightsV1ConversationSummarizationSuggestionData] = None,
	  /** Output only. All the matched Dialogflow intents in the call. The key corresponds to a Dialogflow intent, format: projects/{project}/agent/{agent}/intents/{intent} */
		dialogflowIntents: Option[Map[String, Schema.GoogleCloudContactcenterinsightsV1DialogflowIntent]] = None,
	  /** Call-specific metadata. */
		callMetadata: Option[Schema.GoogleCloudContactcenterinsightsV1ConversationCallMetadata] = None,
	  /** An opaque, user-specified string representing the human agent who handled the conversation. */
		agentId: Option[String] = None,
	  /** Obfuscated user ID which the customer sent to us. */
		obfuscatedUserId: Option[String] = None,
	  /** Input only. The TTL for this resource. If specified, then this TTL will be used to calculate the expire time. */
		ttl: Option[String] = None,
	  /** Output only. The annotations that were generated during the customer and agent interaction. */
		runtimeAnnotations: Option[List[Schema.GoogleCloudContactcenterinsightsV1RuntimeAnnotation]] = None,
	  /** Input only. JSON metadata encoded as a string. This field is primarily used by Insights integrations with various telphony systems and must be in one of Insight's supported formats. */
		metadataJson: Option[String] = None,
	  /** Output only. The duration of the conversation. */
		duration: Option[String] = None,
	  /** Immutable. The conversation medium, if unspecified will default to PHONE_CALL. */
		medium: Option[Schema.GoogleCloudContactcenterinsightsV1Conversation.MediumEnum] = None,
	  /** The time at which this conversation should expire. After this time, the conversation data and any associated analyses will be deleted. */
		expireTime: Option[String] = None,
	  /** The time at which the conversation started. */
		startTime: Option[String] = None,
	  /** A map for the user to specify any custom fields. A maximum of 100 labels per conversation is allowed, with a maximum of 256 characters per entry. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The most recent time at which the conversation was updated. */
		updateTime: Option[String] = None,
	  /** Immutable. The resource name of the conversation. Format: projects/{project}/locations/{location}/conversations/{conversation} */
		name: Option[String] = None,
	  /** Conversation metadata related to quality management. */
		qualityMetadata: Option[Schema.GoogleCloudContactcenterinsightsV1ConversationQualityMetadata] = None,
	  /** Output only. The conversation transcript. */
		transcript: Option[Schema.GoogleCloudContactcenterinsightsV1ConversationTranscript] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1DimensionIssueDimensionMetadata(
	  /** The issue display name. */
		issueDisplayName: Option[String] = None,
	  /** The issue ID. */
		issueId: Option[String] = None,
	  /** The parent issue model ID. */
		issueModelId: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1SilenceData(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1GcsSource(
	  /** Immutable. Cloud Storage URI that points to a file that contains the conversation transcript. */
		transcriptUri: Option[String] = None,
	  /** Cloud Storage URI that points to a file that contains the conversation audio. */
		audioUri: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1EncryptionSpec(
	  /** Required. The name of customer-managed encryption key that is used to secure a resource and its sub-resources. If empty, the resource is secured by our default encryption key. Only the key in the same location as this resource is allowed to be used for encryption. Format: `projects/{project}/locations/{location}/keyRings/{keyRing}/cryptoKeys/{key}` */
		kmsKey: Option[String] = None,
	  /** Immutable. The resource name of the encryption key specification resource. Format: projects/{project}/locations/{location}/encryptionSpec */
		name: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1InterruptionData(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1QueryMetricsResponse(
	  /** Required. The location of the data. "projects/{project}/locations/{location}" */
		location: Option[String] = None,
	  /** A slice contains a total and (if the request specified a time granularity) a time series of metric values. Each slice contains a unique combination of the cardinality of dimensions from the request. */
		slices: Option[List[Schema.GoogleCloudContactcenterinsightsV1QueryMetricsResponseSlice]] = None,
	  /** The macro average slice contains aggregated averages across the selected dimension. i.e. if group_by agent is specified this field will contain the average across all agents. This field is only populated if the request specifies a Dimension. */
		macroAverageSlice: Option[Schema.GoogleCloudContactcenterinsightsV1QueryMetricsResponseSlice] = None,
	  /** The metrics last update time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1UploadConversationRequest(
	  /** Required. The conversation resource to create. */
		conversation: Option[Schema.GoogleCloudContactcenterinsightsV1Conversation] = None,
	  /** Optional. A unique ID for the new conversation. This ID will become the final component of the conversation's resource name. If no ID is specified, a server-generated ID will be used. This value should be 4-64 characters and must match the regular expression `^[a-z0-9-]{4,64}$`. Valid characters are `a-z-` */
		conversationId: Option[String] = None,
	  /** Optional. DLP settings for transcript redaction. Will default to the config specified in Settings. */
		redactionConfig: Option[Schema.GoogleCloudContactcenterinsightsV1RedactionConfig] = None,
	  /** Required. The parent resource of the conversation. */
		parent: Option[String] = None,
	  /** Optional. Speech-to-Text configuration. Will default to the config specified in Settings. */
		speechConfig: Option[Schema.GoogleCloudContactcenterinsightsV1SpeechConfig] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ExportIssueModelRequest(
	  /** Required. The issue model to export. */
		name: Option[String] = None,
	  /** Google Cloud Storage URI to export the issue model to. */
		gcsDestination: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1ExportIssueModelRequestGcsDestination] = None
	)
	
	object GoogleCloudContactcenterinsightsV1ExportInsightsDataRequest {
		enum WriteDispositionEnum extends Enum[WriteDispositionEnum] { case WRITE_DISPOSITION_UNSPECIFIED, WRITE_TRUNCATE, WRITE_APPEND }
	}
	case class GoogleCloudContactcenterinsightsV1ExportInsightsDataRequest(
	  /** A filter to reduce results to a specific subset. Useful for exporting conversations with specific properties. */
		filter: Option[String] = None,
	  /** Options for what to do if the destination table already exists. */
		writeDisposition: Option[Schema.GoogleCloudContactcenterinsightsV1ExportInsightsDataRequest.WriteDispositionEnum] = None,
	  /** Required. The parent resource to export data from. */
		parent: Option[String] = None,
	  /** A fully qualified KMS key name for BigQuery tables protected by CMEK. Format: projects/{project}/locations/{location}/keyRings/{keyring}/cryptoKeys/{key}/cryptoKeyVersions/{version} */
		kmsKey: Option[String] = None,
	  /** Specified if sink is a BigQuery table. */
		bigQueryDestination: Option[Schema.GoogleCloudContactcenterinsightsV1ExportInsightsDataRequestBigQueryDestination] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1DeployQaScorecardRevisionRequest(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1Settings(
	  /** Default DLP redaction resources to be applied while ingesting conversations. This applies to conversations ingested from the `UploadConversation` and `IngestConversations` endpoints, including conversations coming from CCAI Platform. */
		redactionConfig: Option[Schema.GoogleCloudContactcenterinsightsV1RedactionConfig] = None,
	  /** Immutable. The resource name of the settings resource. Format: projects/{project}/locations/{location}/settings */
		name: Option[String] = None,
	  /** A map that maps a notification trigger to a Pub/Sub topic. Each time a specified trigger occurs, Insights will notify the corresponding Pub/Sub topic. Keys are notification triggers. Supported keys are: &#42; "all-triggers": Notify each time any of the supported triggers occurs. &#42; "create-analysis": Notify each time an analysis is created. &#42; "create-conversation": Notify each time a conversation is created. &#42; "export-insights-data": Notify each time an export is complete. &#42; "ingest-conversations": Notify each time an IngestConversations LRO is complete. &#42; "update-conversation": Notify each time a conversation is updated via UpdateConversation. &#42; "upload-conversation": Notify when an UploadConversation LRO is complete. Values are Pub/Sub topics. The format of each Pub/Sub topic is: projects/{project}/topics/{topic} */
		pubsubNotificationSettings: Option[Map[String, String]] = None,
	  /** Default analysis settings. */
		analysisConfig: Option[Schema.GoogleCloudContactcenterinsightsV1SettingsAnalysisConfig] = None,
	  /** The default TTL for newly-created conversations. If a conversation has a specified expiration, that value will be used instead. Changing this value will not change the expiration of existing conversations. Conversations with no expire time persist until they are deleted. */
		conversationTtl: Option[String] = None,
	  /** Output only. The time at which the settings was created. */
		createTime: Option[String] = None,
	  /** A language code to be applied to each transcript segment unless the segment already specifies a language code. Language code defaults to "en-US" if it is neither specified on the segment nor here. */
		languageCode: Option[String] = None,
	  /** Output only. The time at which the settings were last updated. */
		updateTime: Option[String] = None,
	  /** Optional. Default Speech-to-Text resources to use while ingesting audio files. Optional, CCAI Insights will create a default if not provided. This applies to conversations ingested from the `UploadConversation` and `IngestConversations` endpoints, including conversations coming from CCAI Platform. */
		speechConfig: Option[Schema.GoogleCloudContactcenterinsightsV1SpeechConfig] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1PhraseMatchData(
	  /** The human-readable name of the phrase matcher. */
		displayName: Option[String] = None,
	  /** The unique identifier (the resource name) of the phrase matcher. */
		phraseMatcher: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1DialogflowIntent(
	  /** The human-readable name of the intent. */
		displayName: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1IntentMatchData(
	  /** The id of the matched intent. Can be used to retrieve the corresponding intent information. */
		intentUniqueId: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ListPhraseMatchersResponse(
	  /** The phrase matchers that match the request. */
		phraseMatchers: Option[List[Schema.GoogleCloudContactcenterinsightsV1PhraseMatcher]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudContactcenterinsightsV1IngestConversationsRequestTranscriptObjectConfig {
		enum MediumEnum extends Enum[MediumEnum] { case MEDIUM_UNSPECIFIED, PHONE_CALL, CHAT }
	}
	case class GoogleCloudContactcenterinsightsV1IngestConversationsRequestTranscriptObjectConfig(
	  /** Required. The medium transcript objects represent. */
		medium: Option[Schema.GoogleCloudContactcenterinsightsV1IngestConversationsRequestTranscriptObjectConfig.MediumEnum] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1UploadConversationRequest(
	  /** Optional. DLP settings for transcript redaction. Will default to the config specified in Settings. */
		redactionConfig: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1RedactionConfig] = None,
	  /** Required. The parent resource of the conversation. */
		parent: Option[String] = None,
	  /** Optional. Speech-to-Text configuration. Will default to the config specified in Settings. */
		speechConfig: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1SpeechConfig] = None,
	  /** Required. The conversation resource to create. */
		conversation: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1Conversation] = None,
	  /** Optional. A unique ID for the new conversation. This ID will become the final component of the conversation's resource name. If no ID is specified, a server-generated ID will be used. This value should be 4-64 characters and must match the regular expression `^[a-z0-9-]{4,64}$`. Valid characters are `a-z-` */
		conversationId: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1QueryMetricsResponseSliceTimeSeries(
	  /** The data points that make up the time series . */
		dataPoints: Option[List[Schema.GoogleCloudContactcenterinsightsV1QueryMetricsResponseSliceDataPoint]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1QueryMetricsResponseSliceDataPointConversationMeasure(
	  /** Average QA normalized score averaged for questions averaged across all revisions of the parent scorecard. Will be only populated if the request specifies a dimension of QA_QUESTION_ID. */
		averageQaQuestionNormalizedScore: Option[BigDecimal] = None,
	  /** The conversation count. */
		conversationCount: Option[Int] = None,
	  /** The average customer satisfaction rating. */
		averageCustomerSatisfactionRating: Option[BigDecimal] = None,
	  /** Average QA normalized score for all the tags. */
		qaTagScores: Option[List[Schema.GoogleCloudContactcenterinsightsV1alpha1QueryMetricsResponseSliceDataPointConversationMeasureQaTagScore]] = None,
	  /** The average turn count. */
		averageTurnCount: Option[BigDecimal] = None,
	  /** The average silence percentage. */
		averageSilencePercentage: Option[BigDecimal] = None,
	  /** The average client's sentiment score. */
		averageClientSentimentScore: Option[BigDecimal] = None,
	  /** The average agent's sentiment score. */
		averageAgentSentimentScore: Option[BigDecimal] = None,
	  /** Average QA normalized score. Will exclude 0's in average calculation. */
		averageQaNormalizedScore: Option[BigDecimal] = None,
	  /** The average duration. */
		averageDuration: Option[String] = None
	)
	
	object GoogleCloudContactcenterinsightsV1PhraseMatcher {
		enum RoleMatchEnum extends Enum[RoleMatchEnum] { case ROLE_UNSPECIFIED, HUMAN_AGENT, AUTOMATED_AGENT, END_USER, ANY_AGENT }
		enum TypeEnum extends Enum[TypeEnum] { case PHRASE_MATCHER_TYPE_UNSPECIFIED, ALL_OF, ANY_OF }
	}
	case class GoogleCloudContactcenterinsightsV1PhraseMatcher(
	  /** The role whose utterances the phrase matcher should be matched against. If the role is ROLE_UNSPECIFIED it will be matched against any utterances in the transcript. */
		roleMatch: Option[Schema.GoogleCloudContactcenterinsightsV1PhraseMatcher.RoleMatchEnum] = None,
	  /** Output only. The most recent time at which the activation status was updated. */
		activationUpdateTime: Option[String] = None,
	  /** Output only. The most recent time at which the phrase matcher was updated. */
		updateTime: Option[String] = None,
	  /** Output only. Immutable. The revision ID of the phrase matcher. A new revision is committed whenever the matcher is changed, except when it is activated or deactivated. A server generated random ID will be used. Example: locations/global/phraseMatchers/my-first-matcher@1234567 */
		revisionId: Option[String] = None,
	  /** Applies the phrase matcher only when it is active. */
		active: Option[Boolean] = None,
	  /** The resource name of the phrase matcher. Format: projects/{project}/locations/{location}/phraseMatchers/{phrase_matcher} */
		name: Option[String] = None,
	  /** Required. The type of this phrase matcher. */
		`type`: Option[Schema.GoogleCloudContactcenterinsightsV1PhraseMatcher.TypeEnum] = None,
	  /** Output only. The timestamp of when the revision was created. It is also the create time when a new matcher is added. */
		revisionCreateTime: Option[String] = None,
	  /** The human-readable name of the phrase matcher. */
		displayName: Option[String] = None,
	  /** A list of phase match rule groups that are included in this matcher. */
		phraseMatchRuleGroups: Option[List[Schema.GoogleCloudContactcenterinsightsV1PhraseMatchRuleGroup]] = None,
	  /** The customized version tag to use for the phrase matcher. If not specified, it will default to `revision_id`. */
		versionTag: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1BulkAnalyzeConversationsMetadata(
	  /** Output only. Partial errors during bulk analyze operation that might cause the operation output to be incomplete. */
		partialErrors: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** The number of requested analyses that have failed so far. */
		failedAnalysesCount: Option[Int] = None,
	  /** The time the operation finished running. */
		endTime: Option[String] = None,
	  /** The original request for bulk analyze. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1BulkAnalyzeConversationsRequest] = None,
	  /** Total number of analyses requested. Computed by the number of conversations returned by `filter` multiplied by `analysis_percentage` in the request. */
		totalRequestedAnalysesCount: Option[Int] = None,
	  /** The number of requested analyses that have completed successfully so far. */
		completedAnalysesCount: Option[Int] = None,
	  /** The time the operation was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1QueryMetricsResponseSlice(
	  /** A unique combination of dimensions that this slice represents. */
		dimensions: Option[List[Schema.GoogleCloudContactcenterinsightsV1Dimension]] = None,
	  /** The total metric value. The interval of this data point is [starting create time, ending create time) from the request. */
		total: Option[Schema.GoogleCloudContactcenterinsightsV1QueryMetricsResponseSliceDataPoint] = None,
	  /** A time series of metric values. This is only populated if the request specifies a time granularity other than NONE. */
		timeSeries: Option[Schema.GoogleCloudContactcenterinsightsV1QueryMetricsResponseSliceTimeSeries] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1AnnotatorSelectorQaConfigScorecardList(
	  /** List of QaScorecardRevisions. */
		qaScorecardRevisions: Option[List[String]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ConversationTranscriptTranscriptSegmentDialogflowSegmentMetadata(
	  /** Whether the transcript segment was covered under the configured smart reply allowlist in Agent Assist. */
		smartReplyAllowlistCovered: Option[Boolean] = None
	)
	
	case class GoogleRpcStatus(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1AnalysisResult(
	  /** The time at which the analysis ended. */
		endTime: Option[String] = None,
	  /** Call-specific metadata created by the analysis. */
		callAnalysisMetadata: Option[Schema.GoogleCloudContactcenterinsightsV1AnalysisResultCallAnalysisMetadata] = None
	)
	
	object GoogleCloudContactcenterinsightsV1alpha1ExportInsightsDataRequest {
		enum WriteDispositionEnum extends Enum[WriteDispositionEnum] { case WRITE_DISPOSITION_UNSPECIFIED, WRITE_TRUNCATE, WRITE_APPEND }
	}
	case class GoogleCloudContactcenterinsightsV1alpha1ExportInsightsDataRequest(
	  /** A filter to reduce results to a specific subset. Useful for exporting conversations with specific properties. */
		filter: Option[String] = None,
	  /** Required. The parent resource to export data from. */
		parent: Option[String] = None,
	  /** A fully qualified KMS key name for BigQuery tables protected by CMEK. Format: projects/{project}/locations/{location}/keyRings/{keyring}/cryptoKeys/{key}/cryptoKeyVersions/{version} */
		kmsKey: Option[String] = None,
	  /** Specified if sink is a BigQuery table. */
		bigQueryDestination: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1ExportInsightsDataRequestBigQueryDestination] = None,
	  /** Options for what to do if the destination table already exists. */
		writeDisposition: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1ExportInsightsDataRequest.WriteDispositionEnum] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1IngestConversationsMetadata(
	  /** Output only. Partial errors during ingest operation that might cause the operation output to be incomplete. */
		partialErrors: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The original request for ingest. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1IngestConversationsRequest] = None,
	  /** Output only. Statistics for IngestConversations operation. */
		ingestConversationsStats: Option[Schema.GoogleCloudContactcenterinsightsV1IngestConversationsMetadataIngestConversationsStats] = None
	)
	
	object GoogleCloudContactcenterinsightsV1AnswerFeedback {
		enum CorrectnessLevelEnum extends Enum[CorrectnessLevelEnum] { case CORRECTNESS_LEVEL_UNSPECIFIED, NOT_CORRECT, PARTIALLY_CORRECT, FULLY_CORRECT }
	}
	case class GoogleCloudContactcenterinsightsV1AnswerFeedback(
	  /** Indicates whether an answer or item was displayed to the human agent in the agent desktop UI. */
		displayed: Option[Boolean] = None,
	  /** The correctness level of an answer. */
		correctnessLevel: Option[Schema.GoogleCloudContactcenterinsightsV1AnswerFeedback.CorrectnessLevelEnum] = None,
	  /** Indicates whether an answer or item was clicked by the human agent. */
		clicked: Option[Boolean] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ImportIssueModelMetadata(
	  /** The time the operation was created. */
		createTime: Option[String] = None,
	  /** The original import request. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1ImportIssueModelRequest] = None,
	  /** The time the operation finished running. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1DimensionIssueDimensionMetadata(
	  /** The issue ID. */
		issueId: Option[String] = None,
	  /** The issue display name. */
		issueDisplayName: Option[String] = None,
	  /** The parent issue model ID. */
		issueModelId: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1IssueModelResult(
	  /** All the matched issues. */
		issues: Option[List[Schema.GoogleCloudContactcenterinsightsV1IssueAssignment]] = None,
	  /** Issue model that generates the result. Format: projects/{project}/locations/{location}/issueModels/{issue_model} */
		issueModel: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ExportInsightsDataResponse(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ConversationSummarizationSuggestionData(
	  /** The summarization content that is concatenated into one string. */
		text: Option[String] = None,
	  /** A map that contains metadata about the summarization and the document from which it originates. */
		metadata: Option[Map[String, String]] = None,
	  /** The name of the model that generates this summary. Format: projects/{project}/locations/{location}/conversationModels/{conversation_model} */
		conversationModel: Option[String] = None,
	  /** The name of the answer record. Format: projects/{project}/locations/{location}/answerRecords/{answer_record} */
		answerRecord: Option[String] = None,
	  /** The summarization content that is divided into sections. The key is the section's name and the value is the section's content. There is no specific format for the key or value. */
		textSections: Option[Map[String, String]] = None,
	  /** The confidence score of the summarization. */
		confidence: Option[BigDecimal] = None
	)
	
	object GoogleCloudContactcenterinsightsV1BulkDownloadFeedbackLabelsRequestGcsDestination {
		enum FormatEnum extends Enum[FormatEnum] { case FORMAT_UNSPECIFIED, CSV, JSON }
	}
	case class GoogleCloudContactcenterinsightsV1BulkDownloadFeedbackLabelsRequestGcsDestination(
	  /** Optional. Add whitespace to the JSON file. Makes easier to read, but increases file size. Only applicable for JSON format. */
		addWhitespace: Option[Boolean] = None,
	  /** Required. File format in which the labels will be exported. */
		format: Option[Schema.GoogleCloudContactcenterinsightsV1BulkDownloadFeedbackLabelsRequestGcsDestination.FormatEnum] = None,
	  /** Optional. The number of records per file. Applicable for either format. */
		recordsPerFileCount: Option[String] = None,
	  /** Optional. Always print fields with no presence. This is useful for printing fields that are not set, like implicit 0 value or empty lists/maps. Only applicable for JSON format. */
		alwaysPrintEmptyFields: Option[Boolean] = None,
	  /** Required. The Google Cloud Storage URI to write the feedback labels to. The file name will be used as a prefix for the files written to the bucket if the output needs to be split across multiple files, otherwise it will be used as is. The file extension will be appended to the file name based on the format selected. E.g. `gs://bucket_name/object_uri_prefix` */
		objectUri: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1DeployIssueModelMetadata(
	  /** The original request for deployment. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1DeployIssueModelRequest] = None,
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1SilenceData(
	
	)
	
	object GoogleCloudContactcenterinsightsV1alpha1RuntimeAnnotationUserInput {
		enum QuerySourceEnum extends Enum[QuerySourceEnum] { case QUERY_SOURCE_UNSPECIFIED, AGENT_QUERY, SUGGESTED_QUERY }
	}
	case class GoogleCloudContactcenterinsightsV1alpha1RuntimeAnnotationUserInput(
	  /** The resource name of associated generator. Format: `projects//locations//generators/` */
		generatorName: Option[String] = None,
	  /** Query source for the answer. */
		querySource: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1RuntimeAnnotationUserInput.QuerySourceEnum] = None,
	  /** Query text. Article Search uses this to store the input query used to generate the search results. */
		query: Option[String] = None
	)
	
	object GoogleCloudContactcenterinsightsV1QaAnswerAnswerSource {
		enum SourceTypeEnum extends Enum[SourceTypeEnum] { case SOURCE_TYPE_UNSPECIFIED, SYSTEM_GENERATED, MANUAL_EDIT }
	}
	case class GoogleCloudContactcenterinsightsV1QaAnswerAnswerSource(
	  /** The answer value from this source. */
		answerValue: Option[Schema.GoogleCloudContactcenterinsightsV1QaAnswerAnswerValue] = None,
	  /** What created the answer. */
		sourceType: Option[Schema.GoogleCloudContactcenterinsightsV1QaAnswerAnswerSource.SourceTypeEnum] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1SmartReplyData(
	  /** The content of the reply. */
		reply: Option[String] = None,
	  /** Map that contains metadata about the Smart Reply and the document from which it originates. */
		metadata: Option[Map[String, String]] = None,
	  /** The name of the answer record. Format: projects/{project}/locations/{location}/answerRecords/{answer_record} */
		queryRecord: Option[String] = None,
	  /** The system's confidence score that this reply is a good match for this conversation, ranging from 0.0 (completely uncertain) to 1.0 (completely certain). */
		confidenceScore: Option[BigDecimal] = None
	)
	
	object GoogleCloudContactcenterinsightsV1IssueModelInputDataConfig {
		enum MediumEnum extends Enum[MediumEnum] { case MEDIUM_UNSPECIFIED, PHONE_CALL, CHAT }
	}
	case class GoogleCloudContactcenterinsightsV1IssueModelInputDataConfig(
	  /** Output only. Number of conversations used in training. Output only. */
		trainingConversationsCount: Option[String] = None,
	  /** A filter to reduce the conversations used for training the model to a specific subset. */
		filter: Option[String] = None,
	  /** Medium of conversations used in training data. This field is being deprecated. To specify the medium to be used in training a new issue model, set the `medium` field on `filter`. */
		medium: Option[Schema.GoogleCloudContactcenterinsightsV1IssueModelInputDataConfig.MediumEnum] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1QueryMetricsResponseSliceDataPoint(
	  /** The measure related to conversations. */
		conversationMeasure: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1QueryMetricsResponseSliceDataPointConversationMeasure] = None,
	  /** The interval that this data point represents. &#42; If this is the total data point, the interval is [starting create time, ending create time) from the request. &#42; If this a data point from the time series, the interval is [time, time + time granularity from the request). */
		interval: Option[Schema.GoogleTypeInterval] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1BulkDownloadFeedbackLabelsMetadataDownloadStats(
	  /** The number of objects processed during the download operation. */
		processedObjectCount: Option[Int] = None,
	  /** Output only. Full name of the files written to Cloud storage. */
		fileNames: Option[List[String]] = None,
	  /** The number of new feedback labels downloaded during this operation. Different from "processed" because some labels might not be downloaded because an error. */
		successfulDownloadCount: Option[Int] = None,
	  /** Total number of files written to the provided Cloud Storage bucket. */
		totalFilesWritten: Option[Int] = None
	)
	
	object GoogleCloudContactcenterinsightsV1alpha1QaScorecardResultScoreSource {
		enum SourceTypeEnum extends Enum[SourceTypeEnum] { case SOURCE_TYPE_UNSPECIFIED, SYSTEM_GENERATED_ONLY, INCLUDES_MANUAL_EDITS }
	}
	case class GoogleCloudContactcenterinsightsV1alpha1QaScorecardResultScoreSource(
	  /** What created the score. */
		sourceType: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1QaScorecardResultScoreSource.SourceTypeEnum] = None,
	  /** The maximum potential overall score of the scorecard. Any questions answered using `na_value` are excluded from this calculation. */
		potentialScore: Option[BigDecimal] = None,
	  /** The normalized score, which is the score divided by the potential score. */
		normalizedScore: Option[BigDecimal] = None,
	  /** Collection of tags and their scores. */
		qaTagResults: Option[List[Schema.GoogleCloudContactcenterinsightsV1alpha1QaScorecardResultQaTagResult]] = None,
	  /** The overall numerical score of the result. */
		score: Option[BigDecimal] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1Intent(
	  /** The human-readable name of the intent. */
		displayName: Option[String] = None,
	  /** The unique identifier of the intent. */
		id: Option[String] = None
	)
	
	object GoogleCloudContactcenterinsightsV1QueryMetricsRequest {
		enum TimeGranularityEnum extends Enum[TimeGranularityEnum] { case TIME_GRANULARITY_UNSPECIFIED, NONE, DAILY, HOURLY, PER_MINUTE, PER_5_MINUTES, MONTHLY }
	}
	case class GoogleCloudContactcenterinsightsV1QueryMetricsRequest(
	  /** Measures to return. Defaults to all measures if this field is unspecified. A valid mask should traverse from the `measure` field from the response. For example, a path from a measure mask to get the conversation count is "conversation_measure.count". */
		measureMask: Option[String] = None,
	  /** The time granularity of each data point in the time series. Defaults to NONE if this field is unspecified. */
		timeGranularity: Option[Schema.GoogleCloudContactcenterinsightsV1QueryMetricsRequest.TimeGranularityEnum] = None,
	  /** Required. Filter to select a subset of conversations to compute the metrics. Must specify a window of the conversation create time to compute the metrics. The returned metrics will be from the range [DATE(starting create time), DATE(ending create time)).  */
		filter: Option[String] = None,
	  /** The dimensions that determine the grouping key for the query. Defaults to no dimension if this field is unspecified. If a dimension is specified, its key must also be specified. Each dimension's key must be unique. If a time granularity is also specified, metric values in the dimension will be bucketed by this granularity. Up to one dimension is supported for now. */
		dimensions: Option[List[Schema.GoogleCloudContactcenterinsightsV1Dimension]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1RuntimeAnnotation(
	  /** Agent Assist Smart Compose suggestion data. */
		smartComposeSuggestion: Option[Schema.GoogleCloudContactcenterinsightsV1SmartComposeSuggestionData] = None,
	  /** The time at which this annotation was created. */
		createTime: Option[String] = None,
	  /** Conversation summarization suggestion data. */
		conversationSummarizationSuggestion: Option[Schema.GoogleCloudContactcenterinsightsV1ConversationSummarizationSuggestionData] = None,
	  /** The feedback that the customer has about the answer in `data`. */
		answerFeedback: Option[Schema.GoogleCloudContactcenterinsightsV1AnswerFeedback] = None,
	  /** Agent Assist Article Suggestion data. */
		articleSuggestion: Option[Schema.GoogleCloudContactcenterinsightsV1ArticleSuggestionData] = None,
	  /** Agent Assist Smart Reply data. */
		smartReply: Option[Schema.GoogleCloudContactcenterinsightsV1SmartReplyData] = None,
	  /** The boundary in the conversation where the annotation ends, inclusive. */
		endBoundary: Option[Schema.GoogleCloudContactcenterinsightsV1AnnotationBoundary] = None,
	  /** The unique identifier of the annotation. Format: projects/{project}/locations/{location}/conversationDatasets/{dataset}/conversationDataItems/{data_item}/conversationAnnotations/{annotation} */
		annotationId: Option[String] = None,
	  /** The boundary in the conversation where the annotation starts, inclusive. */
		startBoundary: Option[Schema.GoogleCloudContactcenterinsightsV1AnnotationBoundary] = None,
	  /** Explicit input used for generating the answer */
		userInput: Option[Schema.GoogleCloudContactcenterinsightsV1RuntimeAnnotationUserInput] = None,
	  /** Dialogflow interaction data. */
		dialogflowInteraction: Option[Schema.GoogleCloudContactcenterinsightsV1DialogflowInteractionData] = None,
	  /** Agent Assist FAQ answer data. */
		faqAnswer: Option[Schema.GoogleCloudContactcenterinsightsV1FaqAnswerData] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1UndeployIssueModelMetadata(
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** The original request for undeployment. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1UndeployIssueModelRequest] = None,
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None
	)
	
	object GoogleCloudContactcenterinsightsV1AnnotatorSelectorSummarizationConfig {
		enum SummarizationModelEnum extends Enum[SummarizationModelEnum] { case SUMMARIZATION_MODEL_UNSPECIFIED, BASELINE_MODEL, BASELINE_MODEL_V2_0 }
	}
	case class GoogleCloudContactcenterinsightsV1AnnotatorSelectorSummarizationConfig(
	  /** Resource name of the Dialogflow conversation profile. Format: projects/{project}/locations/{location}/conversationProfiles/{conversation_profile} */
		conversationProfile: Option[String] = None,
	  /** Default summarization model to be used. */
		summarizationModel: Option[Schema.GoogleCloudContactcenterinsightsV1AnnotatorSelectorSummarizationConfig.SummarizationModelEnum] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1DeployIssueModelRequest(
	  /** Required. The issue model to deploy. */
		name: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ExportIssueModelRequest(
	  /** Google Cloud Storage URI to export the issue model to. */
		gcsDestination: Option[Schema.GoogleCloudContactcenterinsightsV1ExportIssueModelRequestGcsDestination] = None,
	  /** Required. The issue model to export. */
		name: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ConversationQualityMetadata(
	  /** Information about agents involved in the call. */
		agentInfo: Option[List[Schema.GoogleCloudContactcenterinsightsV1alpha1ConversationQualityMetadataAgentInfo]] = None,
	  /** The amount of time the customer waited to connect with an agent. */
		waitDuration: Option[String] = None,
	  /** An arbitrary string value specifying the menu path the customer took. */
		menuPath: Option[String] = None,
	  /** An arbitrary integer value indicating the customer's satisfaction rating. */
		customerSatisfactionRating: Option[Int] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1UndeployIssueModelRequest(
	  /** Required. The issue model to undeploy. */
		name: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1QueryMetricsResponseSliceTimeSeries(
	  /** The data points that make up the time series . */
		dataPoints: Option[List[Schema.GoogleCloudContactcenterinsightsV1alpha1QueryMetricsResponseSliceDataPoint]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ListFeedbackLabelsResponse(
	  /** The next page token. */
		nextPageToken: Option[String] = None,
	  /** The feedback labels that match the request. */
		feedbackLabels: Option[List[Schema.GoogleCloudContactcenterinsightsV1FeedbackLabel]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1SettingsAnalysisConfig(
	  /** Percentage of conversations created using the UploadConversation endpoint to analyze automatically, between [0, 100]. */
		uploadConversationAnalysisPercentage: Option[BigDecimal] = None,
	  /** To select the annotators to run and the phrase matchers to use (if any). If not specified, all annotators will be run. */
		annotatorSelector: Option[Schema.GoogleCloudContactcenterinsightsV1AnnotatorSelector] = None,
	  /** Percentage of conversations created using Dialogflow runtime integration to analyze automatically, between [0, 100]. */
		runtimeIntegrationAnalysisPercentage: Option[BigDecimal] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ArticleSuggestionData(
	  /** Article URI. */
		uri: Option[String] = None,
	  /** Article title. */
		title: Option[String] = None,
	  /** Map that contains metadata about the Article Suggestion and the document that it originates from. */
		metadata: Option[Map[String, String]] = None,
	  /** The knowledge document that this answer was extracted from. Format: projects/{project}/knowledgeBases/{knowledge_base}/documents/{document} */
		source: Option[String] = None,
	  /** The name of the answer record. Format: projects/{project}/locations/{location}/answerRecords/{answer_record} */
		queryRecord: Option[String] = None,
	  /** The system's confidence score that this article is a good match for this conversation, ranging from 0.0 (completely uncertain) to 1.0 (completely certain). */
		confidenceScore: Option[BigDecimal] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1FaqAnswerData(
	  /** The name of the answer record. Format: projects/{project}/locations/{location}/answerRecords/{answer_record} */
		queryRecord: Option[String] = None,
	  /** The corresponding FAQ question. */
		question: Option[String] = None,
	  /** The knowledge document that this answer was extracted from. Format: projects/{project}/knowledgeBases/{knowledge_base}/documents/{document}. */
		source: Option[String] = None,
	  /** The system's confidence score that this answer is a good match for this conversation, ranging from 0.0 (completely uncertain) to 1.0 (completely certain). */
		confidenceScore: Option[BigDecimal] = None,
	  /** Map that contains metadata about the FAQ answer and the document that it originates from. */
		metadata: Option[Map[String, String]] = None,
	  /** The piece of text from the `source` knowledge base document. */
		answer: Option[String] = None
	)
	
	object GoogleCloudContactcenterinsightsV1alpha1Conversation {
		enum MediumEnum extends Enum[MediumEnum] { case MEDIUM_UNSPECIFIED, PHONE_CALL, CHAT }
	}
	case class GoogleCloudContactcenterinsightsV1alpha1Conversation(
	  /** Output only. The conversation transcript. */
		transcript: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1ConversationTranscript] = None,
	  /** Output only. The number of turns in the conversation. */
		turnCount: Option[Int] = None,
	  /** Immutable. The conversation medium, if unspecified will default to PHONE_CALL. */
		medium: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1Conversation.MediumEnum] = None,
	  /** The time at which this conversation should expire. After this time, the conversation data and any associated analyses will be deleted. */
		expireTime: Option[String] = None,
	  /** The time at which the conversation started. */
		startTime: Option[String] = None,
	  /** Output only. The duration of the conversation. */
		duration: Option[String] = None,
	  /** A map for the user to specify any custom fields. A maximum of 100 labels per conversation is allowed, with a maximum of 256 characters per entry. */
		labels: Option[Map[String, String]] = None,
	  /** Immutable. The resource name of the conversation. Format: projects/{project}/locations/{location}/conversations/{conversation} */
		name: Option[String] = None,
	  /** The source of the audio and transcription for the conversation. */
		dataSource: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1ConversationDataSource] = None,
	  /** Conversation metadata related to quality management. */
		qualityMetadata: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1ConversationQualityMetadata] = None,
	  /** Output only. Latest summary of the conversation. */
		latestSummary: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1ConversationSummarizationSuggestionData] = None,
	  /** Output only. All the matched Dialogflow intents in the call. The key corresponds to a Dialogflow intent, format: projects/{project}/agent/{agent}/intents/{intent} */
		dialogflowIntents: Option[Map[String, Schema.GoogleCloudContactcenterinsightsV1alpha1DialogflowIntent]] = None,
	  /** Output only. The time at which the conversation was created. */
		createTime: Option[String] = None,
	  /** An opaque, user-specified string representing the human agent who handled the conversation. */
		agentId: Option[String] = None,
	  /** Input only. JSON metadata encoded as a string. This field is primarily used by Insights integrations with various telphony systems and must be in one of Insight's supported formats. */
		metadataJson: Option[String] = None,
	  /** Output only. The conversation's latest analysis, if one exists. */
		latestAnalysis: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1Analysis] = None,
	  /** Obfuscated user ID which the customer sent to us. */
		obfuscatedUserId: Option[String] = None,
	  /** Output only. The annotations that were generated during the customer and agent interaction. */
		runtimeAnnotations: Option[List[Schema.GoogleCloudContactcenterinsightsV1alpha1RuntimeAnnotation]] = None,
	  /** Input only. The TTL for this resource. If specified, then this TTL will be used to calculate the expire time. */
		ttl: Option[String] = None,
	  /** A user-specified language code for the conversation. */
		languageCode: Option[String] = None,
	  /** Call-specific metadata. */
		callMetadata: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1ConversationCallMetadata] = None,
	  /** Output only. The most recent time at which the conversation was updated. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ListFeedbackLabelsResponse(
	  /** The feedback labels that match the request. */
		feedbackLabels: Option[List[Schema.GoogleCloudContactcenterinsightsV1alpha1FeedbackLabel]] = None,
	  /** The next page token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1CalculateStatsResponseTimeSeries(
	  /** An ordered list of intervals from earliest to latest, where each interval represents the number of conversations that transpired during the time window. */
		points: Option[List[Schema.GoogleCloudContactcenterinsightsV1CalculateStatsResponseTimeSeriesInterval]] = None,
	  /** The duration of each interval. */
		intervalDuration: Option[String] = None
	)
	
	object GoogleCloudContactcenterinsightsV1ConversationParticipant {
		enum RoleEnum extends Enum[RoleEnum] { case ROLE_UNSPECIFIED, HUMAN_AGENT, AUTOMATED_AGENT, END_USER, ANY_AGENT }
	}
	case class GoogleCloudContactcenterinsightsV1ConversationParticipant(
	  /** The role of the participant. */
		role: Option[Schema.GoogleCloudContactcenterinsightsV1ConversationParticipant.RoleEnum] = None,
	  /** A user-specified ID representing the participant. */
		userId: Option[String] = None,
	  /** Obfuscated user ID from Dialogflow. */
		obfuscatedExternalUserId: Option[String] = None,
	  /** The name of the participant provided by Dialogflow. Format: projects/{project}/locations/{location}/conversations/{conversation}/participants/{participant} */
		dialogflowParticipantName: Option[String] = None,
	  /** Deprecated. Use `dialogflow_participant_name` instead. The name of the Dialogflow participant. Format: projects/{project}/locations/{location}/conversations/{conversation}/participants/{participant} */
		dialogflowParticipant: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1IssueModelLabelStats(
	  /** Statistics on each issue. Key is the issue's resource name. */
		issueStats: Option[Map[String, Schema.GoogleCloudContactcenterinsightsV1IssueModelLabelStatsIssueStats]] = None,
	  /** Number of analyzed conversations for which no issue was applicable at this point in time. */
		unclassifiedConversationsCount: Option[String] = None,
	  /** Number of conversations the issue model has analyzed at this point in time. */
		analyzedConversationsCount: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ExportInsightsDataRequestBigQueryDestination(
	  /** Required. The name of the BigQuery dataset that the snapshot result should be exported to. If this dataset does not exist, the export call returns an INVALID_ARGUMENT error. */
		dataset: Option[String] = None,
	  /** A project ID or number. If specified, then export will attempt to write data to this project instead of the resource project. Otherwise, the resource project will be used. */
		projectId: Option[String] = None,
	  /** The BigQuery table name to which the insights data should be written. If this table does not exist, the export call returns an INVALID_ARGUMENT error. */
		table: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1InitializeEncryptionSpecRequest(
	  /** Required. The encryption spec used for CMEK encryption. It is required that the kms key is in the same region as the endpoint. The same key will be used for all provisioned resources, if encryption is available. If the `kms_key_name` field is left empty, no encryption will be enforced. */
		encryptionSpec: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1EncryptionSpec] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ArticleSuggestionData(
	  /** Article URI. */
		uri: Option[String] = None,
	  /** Map that contains metadata about the Article Suggestion and the document that it originates from. */
		metadata: Option[Map[String, String]] = None,
	  /** Article title. */
		title: Option[String] = None,
	  /** The name of the answer record. Format: projects/{project}/locations/{location}/answerRecords/{answer_record} */
		queryRecord: Option[String] = None,
	  /** The system's confidence score that this article is a good match for this conversation, ranging from 0.0 (completely uncertain) to 1.0 (completely certain). */
		confidenceScore: Option[BigDecimal] = None,
	  /** The knowledge document that this answer was extracted from. Format: projects/{project}/knowledgeBases/{knowledge_base}/documents/{document} */
		source: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ExportInsightsDataResponse(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ExportIssueModelResponse(
	
	)
	
	object GoogleCloudContactcenterinsightsV1QaScorecardRevision {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, EDITABLE, TRAINING, TRAINING_FAILED, READY, DELETING, TRAINING_CANCELLED }
	}
	case class GoogleCloudContactcenterinsightsV1QaScorecardRevision(
	  /** Output only. The timestamp that the revision was created. */
		createTime: Option[String] = None,
	  /** Output only. State of the scorecard revision, indicating whether it's ready to be used in analysis. */
		state: Option[Schema.GoogleCloudContactcenterinsightsV1QaScorecardRevision.StateEnum] = None,
	  /** The snapshot of the scorecard at the time of this revision's creation. */
		snapshot: Option[Schema.GoogleCloudContactcenterinsightsV1QaScorecard] = None,
	  /** Output only. Alternative IDs for this revision of the scorecard, e.g., `latest`. */
		alternateIds: Option[List[String]] = None,
	  /** Identifier. The name of the scorecard revision. Format: projects/{project}/locations/{location}/qaScorecards/{qa_scorecard}/revisions/{revision} */
		name: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ListViewsResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The views that match the request. */
		views: Option[List[Schema.GoogleCloudContactcenterinsightsV1View]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1CreateAnalysisOperationMetadata(
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. The Conversation that this Analysis Operation belongs to. */
		conversation: Option[String] = None,
	  /** Output only. The annotator selector used for the analysis (if any). */
		annotatorSelector: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1AnnotatorSelector] = None,
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ConversationTranscript(
	  /** A list of sequential transcript segments that comprise the conversation. */
		transcriptSegments: Option[List[Schema.GoogleCloudContactcenterinsightsV1alpha1ConversationTranscriptTranscriptSegment]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1Issue(
	  /** Representative description of the issue. */
		displayDescription: Option[String] = None,
	  /** The representative name for the issue. */
		displayName: Option[String] = None,
	  /** Output only. The most recent time that this issue was updated. */
		updateTime: Option[String] = None,
	  /** Output only. Resource names of the sample representative utterances that match to this issue. */
		sampleUtterances: Option[List[String]] = None,
	  /** Output only. The time at which this issue was created. */
		createTime: Option[String] = None,
	  /** Immutable. The resource name of the issue. Format: projects/{project}/locations/{location}/issueModels/{issue_model}/issues/{issue} */
		name: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ListAnalysisRulesResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The analysis_rule that match the request. */
		analysisRules: Option[List[Schema.GoogleCloudContactcenterinsightsV1AnalysisRule]] = None
	)
	
	object GoogleCloudContactcenterinsightsV1IssueModel {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, UNDEPLOYED, DEPLOYING, DEPLOYED, UNDEPLOYING, DELETING }
		enum ModelTypeEnum extends Enum[ModelTypeEnum] { case MODEL_TYPE_UNSPECIFIED, TYPE_V1, TYPE_V2 }
	}
	case class GoogleCloudContactcenterinsightsV1IssueModel(
	  /** Immutable. The resource name of the issue model. Format: projects/{project}/locations/{location}/issueModels/{issue_model} */
		name: Option[String] = None,
	  /** Output only. State of the model. */
		state: Option[Schema.GoogleCloudContactcenterinsightsV1IssueModel.StateEnum] = None,
	  /** The representative name for the issue model. */
		displayName: Option[String] = None,
	  /** Output only. Immutable. The issue model's label statistics on its training data. */
		trainingStats: Option[Schema.GoogleCloudContactcenterinsightsV1IssueModelLabelStats] = None,
	  /** Type of the model. */
		modelType: Option[Schema.GoogleCloudContactcenterinsightsV1IssueModel.ModelTypeEnum] = None,
	  /** Language of the model. */
		languageCode: Option[String] = None,
	  /** Output only. Number of issues in this issue model. */
		issueCount: Option[String] = None,
	  /** Configs for the input data that used to create the issue model. */
		inputDataConfig: Option[Schema.GoogleCloudContactcenterinsightsV1IssueModelInputDataConfig] = None,
	  /** Output only. The most recent time at which the issue model was updated. */
		updateTime: Option[String] = None,
	  /** Output only. The time at which this issue model was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1IssueAssignment(
	  /** Resource name of the assigned issue. */
		issue: Option[String] = None,
	  /** Immutable. Display name of the assigned issue. This field is set at time of analyis and immutable since then. */
		displayName: Option[String] = None,
	  /** Score indicating the likelihood of the issue assignment. currently bounded on [0,1]. */
		score: Option[BigDecimal] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ExportInsightsDataMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Partial errors during export operation that might cause the operation output to be incomplete. */
		partialErrors: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** The original request for export. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1ExportInsightsDataRequest] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1IngestConversationsMetadataIngestConversationsStats(
	  /** Output only. The number of objects which were unable to be ingested due to errors. The errors are populated in the partial_errors field. */
		failedIngestCount: Option[Int] = None,
	  /** Output only. The number of objects skipped because another conversation with the same transcript uri had already been ingested. */
		duplicatesSkippedCount: Option[Int] = None,
	  /** Output only. The number of new conversations added during this ingest operation. */
		successfulIngestCount: Option[Int] = None,
	  /** Output only. The number of objects processed during the ingest operation. */
		processedObjectCount: Option[Int] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ConversationLevelSentiment(
	  /** The channel of the audio that the data applies to. */
		channelTag: Option[Int] = None,
	  /** Data specifying sentiment. */
		sentimentData: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1SentimentData] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1FeedbackLabel(
	  /** Output only. Create time of the label. */
		createTime: Option[String] = None,
	  /** Output only. Update time of the label. */
		updateTime: Option[String] = None,
	  /** Immutable. Resource name of the FeedbackLabel. Format: projects/{project}/locations/{location}/conversations/{conversation}/feedbackLabels/{feedback_label} */
		name: Option[String] = None,
	  /** Resource name of the resource to be labeled. */
		labeledResource: Option[String] = None,
	  /** QaAnswer label. */
		qaAnswerLabel: Option[Schema.GoogleCloudContactcenterinsightsV1QaAnswerAnswerValue] = None,
	  /** String label. */
		label: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1IngestConversationsResponse(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1ImportIssueModelRequestGcsSource(
	  /** Required. Format: `gs:///` */
		objectUri: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1AnnotationBoundary(
	  /** The index in the sequence of transcribed pieces of the conversation where the boundary is located. This index starts at zero. */
		transcriptIndex: Option[Int] = None,
	  /** The word index of this boundary with respect to the first word in the transcript piece. This index starts at zero. */
		wordIndex: Option[Int] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1IngestConversationsRequest(
	  /** Required. The parent resource for new conversations. */
		parent: Option[String] = None,
	  /** Optional. If set, this fields indicates the number of objects to ingest from the Cloud Storage bucket. If empty, the entire bucket will be ingested. Unless they are first deleted, conversations produced through sampling won't be ingested by subsequent ingest requests. */
		sampleSize: Option[Int] = None,
	  /** A cloud storage bucket source. Note that any previously ingested objects from the source will be skipped to avoid duplication. */
		gcsSource: Option[Schema.GoogleCloudContactcenterinsightsV1IngestConversationsRequestGcsSource] = None,
	  /** Configuration for when `source` contains conversation transcripts. */
		transcriptObjectConfig: Option[Schema.GoogleCloudContactcenterinsightsV1IngestConversationsRequestTranscriptObjectConfig] = None,
	  /** Configuration that applies to all conversations. */
		conversationConfig: Option[Schema.GoogleCloudContactcenterinsightsV1IngestConversationsRequestConversationConfig] = None,
	  /** Optional. DLP settings for transcript redaction. Optional, will default to the config specified in Settings. */
		redactionConfig: Option[Schema.GoogleCloudContactcenterinsightsV1RedactionConfig] = None,
	  /** Optional. Default Speech-to-Text configuration. Optional, will default to the config specified in Settings. */
		speechConfig: Option[Schema.GoogleCloudContactcenterinsightsV1SpeechConfig] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1DimensionQaQuestionAnswerDimensionMetadata(
	  /** Optional. The full body of the question. */
		answerValue: Option[String] = None,
	  /** Optional. The full body of the question. */
		questionBody: Option[String] = None,
	  /** Optional. The QA scorecard ID. */
		qaScorecardId: Option[String] = None,
	  /** Optional. The QA question ID. */
		qaQuestionId: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1UploadConversationMetadata(
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. The original request. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1UploadConversationRequest] = None,
	  /** Output only. The redaction config applied to the uploaded conversation. */
		appliedRedactionConfig: Option[Schema.GoogleCloudContactcenterinsightsV1RedactionConfig] = None,
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The operation name for a successfully created analysis operation, if any. */
		analysisOperation: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1QueryMetricsResponseSliceDataPointConversationMeasureQaTagScore(
	  /** Average tag normalized score per tag. */
		averageTagNormalizedScore: Option[BigDecimal] = None,
	  /** Tag name. */
		tag: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1BulkDownloadFeedbackLabelsResponse(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1IssueAssignment(
	  /** Score indicating the likelihood of the issue assignment. currently bounded on [0,1]. */
		score: Option[BigDecimal] = None,
	  /** Immutable. Display name of the assigned issue. This field is set at time of analyis and immutable since then. */
		displayName: Option[String] = None,
	  /** Resource name of the assigned issue. */
		issue: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1CreateIssueModelRequest(
	  /** Required. The issue model to create. */
		issueModel: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1IssueModel] = None,
	  /** Required. The parent resource of the issue model. */
		parent: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1IngestConversationsResponse(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1RuntimeAnnotation(
	  /** The feedback that the customer has about the answer in `data`. */
		answerFeedback: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1AnswerFeedback] = None,
	  /** Agent Assist FAQ answer data. */
		faqAnswer: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1FaqAnswerData] = None,
	  /** The unique identifier of the annotation. Format: projects/{project}/locations/{location}/conversationDatasets/{dataset}/conversationDataItems/{data_item}/conversationAnnotations/{annotation} */
		annotationId: Option[String] = None,
	  /** Explicit input used for generating the answer */
		userInput: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1RuntimeAnnotationUserInput] = None,
	  /** Agent Assist Smart Compose suggestion data. */
		smartComposeSuggestion: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1SmartComposeSuggestionData] = None,
	  /** The time at which this annotation was created. */
		createTime: Option[String] = None,
	  /** Dialogflow interaction data. */
		dialogflowInteraction: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1DialogflowInteractionData] = None,
	  /** Agent Assist Article Suggestion data. */
		articleSuggestion: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1ArticleSuggestionData] = None,
	  /** The boundary in the conversation where the annotation starts, inclusive. */
		startBoundary: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1AnnotationBoundary] = None,
	  /** Conversation summarization suggestion data. */
		conversationSummarizationSuggestion: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1ConversationSummarizationSuggestionData] = None,
	  /** Agent Assist Smart Reply data. */
		smartReply: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1SmartReplyData] = None,
	  /** The boundary in the conversation where the annotation ends, inclusive. */
		endBoundary: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1AnnotationBoundary] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ConversationTranscriptTranscriptSegmentDialogflowSegmentMetadata(
	  /** Whether the transcript segment was covered under the configured smart reply allowlist in Agent Assist. */
		smartReplyAllowlistCovered: Option[Boolean] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1View(
	  /** Output only. The most recent time at which the view was updated. */
		updateTime: Option[String] = None,
	  /** Immutable. The resource name of the view. Format: projects/{project}/locations/{location}/views/{view} */
		name: Option[String] = None,
	  /** The human-readable display name of the view. */
		displayName: Option[String] = None,
	  /** Output only. The time at which this view was created. */
		createTime: Option[String] = None,
	  /** String with specific view properties, must be non-empty. */
		value: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1IssueMatchData(
	  /** Information about the issue's assignment. */
		issueAssignment: Option[Schema.GoogleCloudContactcenterinsightsV1IssueAssignment] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1BulkAnalyzeConversationsResponse(
	  /** Count of successful analyses. */
		successfulAnalysisCount: Option[Int] = None,
	  /** Count of failed analyses. */
		failedAnalysisCount: Option[Int] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1DeleteIssueModelRequest(
	  /** Required. The name of the issue model to delete. */
		name: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1ExportIssueModelMetadata(
	  /** The original export request. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1ExportIssueModelRequest] = None,
	  /** The time the operation finished running. */
		endTime: Option[String] = None,
	  /** The time the operation was created. */
		createTime: Option[String] = None
	)
	
	object GoogleCloudContactcenterinsightsV1alpha1QaAnswerAnswerSource {
		enum SourceTypeEnum extends Enum[SourceTypeEnum] { case SOURCE_TYPE_UNSPECIFIED, SYSTEM_GENERATED, MANUAL_EDIT }
	}
	case class GoogleCloudContactcenterinsightsV1alpha1QaAnswerAnswerSource(
	  /** The answer value from this source. */
		answerValue: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1QaAnswerAnswerValue] = None,
	  /** What created the answer. */
		sourceType: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1QaAnswerAnswerSource.SourceTypeEnum] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1BulkDownloadFeedbackLabelsMetadata(
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. The original request for download. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1BulkDownloadFeedbackLabelsRequest] = None,
	  /** Output only. Statistics for BulkDownloadFeedbackLabels operation. */
		downloadStats: Option[Schema.GoogleCloudContactcenterinsightsV1BulkDownloadFeedbackLabelsMetadataDownloadStats] = None,
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Partial errors during ingest operation that might cause the operation output to be incomplete. */
		partialErrors: Option[List[Schema.GoogleRpcStatus]] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1CalculateIssueModelStatsResponse(
	  /** The latest label statistics for the queried issue model. Includes results on both training data and data labeled after deployment. */
		currentStats: Option[Schema.GoogleCloudContactcenterinsightsV1IssueModelLabelStats] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1IssueModelLabelStatsIssueStats(
	  /** Issue resource. Format: projects/{project}/locations/{location}/issueModels/{issue_model}/issues/{issue} */
		issue: Option[String] = None,
	  /** Number of conversations attached to the issue at this point in time. */
		labeledConversationsCount: Option[String] = None,
	  /** Display name of the issue. */
		displayName: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1IngestConversationsRequest(
	  /** Configuration for when `source` contains conversation transcripts. */
		transcriptObjectConfig: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1IngestConversationsRequestTranscriptObjectConfig] = None,
	  /** Optional. DLP settings for transcript redaction. Optional, will default to the config specified in Settings. */
		redactionConfig: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1RedactionConfig] = None,
	  /** A cloud storage bucket source. Note that any previously ingested objects from the source will be skipped to avoid duplication. */
		gcsSource: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1IngestConversationsRequestGcsSource] = None,
	  /** Required. The parent resource for new conversations. */
		parent: Option[String] = None,
	  /** Optional. If set, this fields indicates the number of objects to ingest from the Cloud Storage bucket. If empty, the entire bucket will be ingested. Unless they are first deleted, conversations produced through sampling won't be ingested by subsequent ingest requests. */
		sampleSize: Option[Int] = None,
	  /** Configuration that applies to all conversations. */
		conversationConfig: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1IngestConversationsRequestConversationConfig] = None,
	  /** Optional. Default Speech-to-Text configuration. Optional, will default to the config specified in Settings. */
		speechConfig: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1SpeechConfig] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1InitializeEncryptionSpecMetadata(
	  /** Partial errors during initializing operation that might cause the operation output to be incomplete. */
		partialErrors: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Output only. The original request for initialization. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1InitializeEncryptionSpecRequest] = None,
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1QaAnswer(
	  /** Question text. E.g., "Did the agent greet the customer?" */
		questionBody: Option[String] = None,
	  /** User-defined list of arbitrary tags. Matches the value from QaScorecard.ScorecardQuestion.tags. Used for grouping/organization and for weighting the score of each answer. */
		tags: Option[List[String]] = None,
	  /** The QaQuestion answered by this answer. */
		qaQuestion: Option[String] = None,
	  /** The conversation the answer applies to. */
		conversation: Option[String] = None,
	  /** List of all individual answers given to the question. */
		answerSources: Option[List[Schema.GoogleCloudContactcenterinsightsV1QaAnswerAnswerSource]] = None,
	  /** The main answer value, incorporating any manual edits if they exist. */
		answerValue: Option[Schema.GoogleCloudContactcenterinsightsV1QaAnswerAnswerValue] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1QueryMetricsResponse(
	  /** Required. The location of the data. "projects/{project}/locations/{location}" */
		location: Option[String] = None,
	  /** The macro average slice contains aggregated averages across the selected dimension. i.e. if group_by agent is specified this field will contain the average across all agents. This field is only populated if the request specifies a Dimension. */
		macroAverageSlice: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1QueryMetricsResponseSlice] = None,
	  /** A slice contains a total and (if the request specified a time granularity) a time series of metric values. Each slice contains a unique combination of the cardinality of dimensions from the request. */
		slices: Option[List[Schema.GoogleCloudContactcenterinsightsV1alpha1QueryMetricsResponseSlice]] = None,
	  /** The metrics last update time. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1UndeployQaScorecardRevisionRequest(
	
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1BulkAnalyzeConversationsMetadata(
	  /** Output only. Partial errors during bulk analyze operation that might cause the operation output to be incomplete. */
		partialErrors: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** The number of requested analyses that have completed successfully so far. */
		completedAnalysesCount: Option[Int] = None,
	  /** The number of requested analyses that have failed so far. */
		failedAnalysesCount: Option[Int] = None,
	  /** The original request for bulk analyze. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1BulkAnalyzeConversationsRequest] = None,
	  /** Total number of analyses requested. Computed by the number of conversations returned by `filter` multiplied by `analysis_percentage` in the request. */
		totalRequestedAnalysesCount: Option[Int] = None,
	  /** The time the operation finished running. */
		endTime: Option[String] = None,
	  /** The time the operation was created. */
		createTime: Option[String] = None
	)
	
	object GoogleCloudContactcenterinsightsV1QaScorecardResultScoreSource {
		enum SourceTypeEnum extends Enum[SourceTypeEnum] { case SOURCE_TYPE_UNSPECIFIED, SYSTEM_GENERATED_ONLY, INCLUDES_MANUAL_EDITS }
	}
	case class GoogleCloudContactcenterinsightsV1QaScorecardResultScoreSource(
	  /** The normalized score, which is the score divided by the potential score. */
		normalizedScore: Option[BigDecimal] = None,
	  /** Collection of tags and their scores. */
		qaTagResults: Option[List[Schema.GoogleCloudContactcenterinsightsV1QaScorecardResultQaTagResult]] = None,
	  /** What created the score. */
		sourceType: Option[Schema.GoogleCloudContactcenterinsightsV1QaScorecardResultScoreSource.SourceTypeEnum] = None,
	  /** The overall numerical score of the result. */
		score: Option[BigDecimal] = None,
	  /** The maximum potential overall score of the scorecard. Any questions answered using `na_value` are excluded from this calculation. */
		potentialScore: Option[BigDecimal] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ExportIssueModelRequestGcsDestination(
	  /** Required. Format: `gs:///` */
		objectUri: Option[String] = None
	)
	
	object GoogleCloudContactcenterinsightsV1alpha1ConversationQualityMetadataAgentInfo {
		enum AgentTypeEnum extends Enum[AgentTypeEnum] { case ROLE_UNSPECIFIED, HUMAN_AGENT, AUTOMATED_AGENT, END_USER, ANY_AGENT }
	}
	case class GoogleCloudContactcenterinsightsV1alpha1ConversationQualityMetadataAgentInfo(
	  /** A user-provided string indicating the outcome of the agent's segment of the call. */
		dispositionCode: Option[String] = None,
	  /** The agent's name. */
		displayName: Option[String] = None,
	  /** A user-specified string representing the agent. */
		agentId: Option[String] = None,
	  /** A user-specified string representing the agent's team. */
		team: Option[String] = None,
	  /** The agent type, e.g. HUMAN_AGENT. */
		agentType: Option[Schema.GoogleCloudContactcenterinsightsV1alpha1ConversationQualityMetadataAgentInfo.AgentTypeEnum] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1CreateIssueModelMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** The original request for creation. */
		request: Option[Schema.GoogleCloudContactcenterinsightsV1CreateIssueModelRequest] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1alpha1RedactionConfig(
	  /** The fully-qualified DLP inspect template resource name. Format: `projects/{project}/locations/{location}/inspectTemplates/{template}` */
		inspectTemplate: Option[String] = None,
	  /** The fully-qualified DLP deidentify template resource name. Format: `projects/{project}/deidentifyTemplates/{template}` */
		deidentifyTemplate: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ListQaQuestionsResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The QaQuestions under the parent. */
		qaQuestions: Option[List[Schema.GoogleCloudContactcenterinsightsV1QaQuestion]] = None
	)
	
	object GoogleCloudContactcenterinsightsV1BulkUploadFeedbackLabelsRequestGcsSource {
		enum FormatEnum extends Enum[FormatEnum] { case FORMAT_UNSPECIFIED, CSV, JSON }
	}
	case class GoogleCloudContactcenterinsightsV1BulkUploadFeedbackLabelsRequestGcsSource(
	  /** Required. The Google Cloud Storage URI of the file to import. Format: `gs://bucket_name/object_name` */
		objectUri: Option[String] = None,
	  /** Required. File format which will be ingested. */
		format: Option[Schema.GoogleCloudContactcenterinsightsV1BulkUploadFeedbackLabelsRequestGcsSource.FormatEnum] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1ConversationLevelSilence(
	  /** Percentage of the total conversation spent in silence. */
		silencePercentage: Option[BigDecimal] = None,
	  /** Amount of time calculated to be in silence. */
		silenceDuration: Option[String] = None
	)
	
	case class GoogleCloudContactcenterinsightsV1QaScorecardResultQaTagResult(
	  /** The potential score the tag applies to. */
		potentialScore: Option[BigDecimal] = None,
	  /** The normalized score the tag applies to. */
		normalizedScore: Option[BigDecimal] = None,
	  /** The tag the score applies to. */
		tag: Option[String] = None,
	  /** The score the tag applies to. */
		score: Option[BigDecimal] = None
	)
}
