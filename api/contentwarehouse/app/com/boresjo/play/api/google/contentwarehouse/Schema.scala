package com.boresjo.play.api.google.contentwarehouse

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleCloudContentwarehouseV1MergeFieldsOptions(
	  /** When merging repeated fields, the default behavior is to append entries from the source repeated field to the destination repeated field. If you instead want to keep only the entries from the source repeated field, set this flag to true. If you want to replace a repeated field within a message field on the destination message, you must set both replace_repeated_fields and replace_message_fields to true, otherwise the repeated fields will be appended. */
		replaceRepeatedFields: Option[Boolean] = None,
	  /** When merging message fields, the default behavior is to merge the content of two message fields together. If you instead want to use the field from the source message to replace the corresponding field in the destination message, set this flag to true. When this flag is set, specified submessage fields that are missing in source will be cleared in destination. */
		replaceMessageFields: Option[Boolean] = None
	)
	
	case class GoogleCloudContentwarehouseV1RuleEvaluatorOutput(
	  /** A subset of triggered rules that failed the validation check(s) after parsing. */
		invalidRules: Option[List[Schema.GoogleCloudContentwarehouseV1InvalidRule]] = None,
	  /** List of rules fetched from database for the given request trigger type. */
		triggeredRules: Option[List[Schema.GoogleCloudContentwarehouseV1Rule]] = None,
	  /** A subset of triggered rules that are evaluated true for a given request. */
		matchedRules: Option[List[Schema.GoogleCloudContentwarehouseV1Rule]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageSymbol(
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None,
	  /** Layout for Symbol. */
		layout: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None
	)
	
	case class GoogleCloudContentwarehouseV1DeleteDocumentRequest(
	  /** The meta information collected about the end user, used to enforce access control for the service. */
		requestMetadata: Option[Schema.GoogleCloudContentwarehouseV1RequestMetadata] = None
	)
	
	case class GoogleCloudContentwarehouseV1TextArray(
	  /** List of text values. */
		values: Option[List[String]] = None
	)
	
	object GoogleCloudDocumentaiV1DocumentPageAnchorPageRef {
		enum LayoutTypeEnum extends Enum[LayoutTypeEnum] { case LAYOUT_TYPE_UNSPECIFIED, BLOCK, PARAGRAPH, LINE, TOKEN, VISUAL_ELEMENT, TABLE, FORM_FIELD }
	}
	case class GoogleCloudDocumentaiV1DocumentPageAnchorPageRef(
	  /** Optional. The type of the layout element that is being referenced if any. */
		layoutType: Option[Schema.GoogleCloudDocumentaiV1DocumentPageAnchorPageRef.LayoutTypeEnum] = None,
	  /** Optional. Identifies the bounding polygon of a layout element on the page. If `layout_type` is set, the bounding polygon must be exactly the same to the layout element it's referring to. */
		boundingPoly: Option[Schema.GoogleCloudDocumentaiV1BoundingPoly] = None,
	  /** Optional. Deprecated. Use PageRef.bounding_poly instead. */
		layoutId: Option[String] = None,
	  /** Optional. Confidence of detected page element, if applicable. Range `[0, 1]`. */
		confidence: Option[BigDecimal] = None,
	  /** Required. Index into the Document.pages element, for example using `Document.pages` to locate the related page element. This field is skipped when its value is the default `0`. See https://developers.google.com/protocol-buffers/docs/proto3#json. */
		page: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1EnumTypeOptions(
	  /** Make sure the Enum property value provided in the document is in the possile value list during document creation. The validation check runs by default. */
		validationCheckDisabled: Option[Boolean] = None,
	  /** Required. List of possible enum values. */
		possibleValues: Option[List[String]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageDetectedBarcode(
	  /** Layout for DetectedBarcode. */
		layout: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None,
	  /** Detailed barcode information of the DetectedBarcode. */
		barcode: Option[Schema.GoogleCloudDocumentaiV1Barcode] = None
	)
	
	case class GoogleCloudContentwarehouseV1PropertyDefinitionSchemaSource(
	  /** The Doc AI processor type name. */
		processorType: Option[String] = None,
	  /** The schema name in the source. */
		name: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentTextChange(
	  /** The history of this annotation. */
		provenance: Option[List[Schema.GoogleCloudDocumentaiV1DocumentProvenance]] = None,
	  /** The text that replaces the text identified in the `text_anchor`. */
		changedText: Option[String] = None,
	  /** Provenance of the correction. Text anchor indexing into the Document.text. There can only be a single `TextAnchor.text_segments` element. If the start and end index of the text segment are the same, the text change is inserted before that index. */
		textAnchor: Option[Schema.GoogleCloudDocumentaiV1DocumentTextAnchor] = None
	)
	
	case class GoogleCloudContentwarehouseV1CustomWeightsMetadata(
	  /** List of schema and property name. Allows a maximum of 10 schemas to be specified for relevance boosting. */
		weightedSchemaProperties: Option[List[Schema.GoogleCloudContentwarehouseV1WeightedSchemaProperty]] = None
	)
	
	case class GoogleIamV1Binding(
	  /** Role that is assigned to the list of `members`, or principals. For example, `roles/viewer`, `roles/editor`, or `roles/owner`. For an overview of the IAM roles and permissions, see the [IAM documentation](https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see [here](https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** The condition that is associated with this binding. If the condition evaluates to `true`, then this binding applies to the current request. If the condition evaluates to `false`, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		condition: Option[Schema.GoogleTypeExpr] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. `members` can have the following values: &#42; `allUsers`: A special identifier that represents anyone who is on the internet; with or without a Google account. &#42; `allAuthenticatedUsers`: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. &#42; `user:{emailid}`: An email address that represents a specific Google account. For example, `alice@example.com` . &#42; `serviceAccount:{emailid}`: An email address that represents a Google service account. For example, `my-other-app@appspot.gserviceaccount.com`. &#42; `serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]`: An identifier for a [Kubernetes service account](https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, `my-project.svc.id.goog[my-namespace/my-kubernetes-sa]`. &#42; `group:{emailid}`: An email address that represents a Google group. For example, `admins@example.com`. &#42; `domain:{domain}`: The G Suite domain (primary) that represents all the users of that domain. For example, `google.com` or `example.com`. &#42; `principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workforce identity pool. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}`: All workforce identities in a group. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All workforce identities with a specific attribute value. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;`: All identities in a workforce identity pool. &#42; `principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workload identity pool. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}`: A workload identity pool group. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All identities in a workload identity pool with a certain attribute. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;`: All identities in a workload identity pool. &#42; `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a user that has been recently deleted. For example, `alice@example.com?uid=123456789012345678901`. If the user is recovered, this value reverts to `user:{emailid}` and the recovered user retains the role in the binding. &#42; `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, `my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901`. If the service account is undeleted, this value reverts to `serviceAccount:{emailid}` and the undeleted service account retains the role in the binding. &#42; `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, `admins@example.com?uid=123456789012345678901`. If the group is recovered, this value reverts to `group:{emailid}` and the recovered group retains the role in the binding. &#42; `deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: Deleted single identity in a workforce identity pool. For example, `deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value`. */
		members: Option[List[String]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageDetectedLanguage(
	  /** Confidence of detected language. Range `[0, 1]`. */
		confidence: Option[BigDecimal] = None,
	  /** The [BCP-47 language code](https://www.unicode.org/reports/tr35/#Unicode_locale_identifier), such as `en-US` or `sr-Latn`. */
		languageCode: Option[String] = None
	)
	
	object GoogleCloudContentwarehouseV1DocumentLink {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, SOFT_DELETED }
	}
	case class GoogleCloudContentwarehouseV1DocumentLink(
	  /** Document references of the source document. */
		sourceDocumentReference: Option[Schema.GoogleCloudContentwarehouseV1DocumentReference] = None,
	  /** The state of the documentlink. If target node has been deleted, the link is marked as invalid. Removing a source node will result in removal of all associated links. */
		state: Option[Schema.GoogleCloudContentwarehouseV1DocumentLink.StateEnum] = None,
	  /** Document references of the target document. */
		targetDocumentReference: Option[Schema.GoogleCloudContentwarehouseV1DocumentReference] = None,
	  /** Name of this document-link. It is required that the parent derived form the name to be consistent with the source document reference. Otherwise an exception will be thrown. Format: projects/{project_number}/locations/{location}/documents/{source_document_id}/documentLinks/{document_link_id}. */
		name: Option[String] = None,
	  /** Output only. The time when the documentLink is last updated. */
		updateTime: Option[String] = None,
	  /** Output only. The time when the documentLink is created. */
		createTime: Option[String] = None,
	  /** Description of this document-link. */
		description: Option[String] = None
	)
	
	object GoogleCloudDocumentaiV1DocumentPageLayout {
		enum OrientationEnum extends Enum[OrientationEnum] { case ORIENTATION_UNSPECIFIED, PAGE_UP, PAGE_RIGHT, PAGE_DOWN, PAGE_LEFT }
	}
	case class GoogleCloudDocumentaiV1DocumentPageLayout(
	  /** Confidence of the current Layout within context of the object this layout is for. e.g. confidence can be for a single token, a table, a visual element, etc. depending on context. Range `[0, 1]`. */
		confidence: Option[BigDecimal] = None,
	  /** Text anchor indexing into the Document.text. */
		textAnchor: Option[Schema.GoogleCloudDocumentaiV1DocumentTextAnchor] = None,
	  /** Detected orientation for the Layout. */
		orientation: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout.OrientationEnum] = None,
	  /** The bounding polygon for the Layout. */
		boundingPoly: Option[Schema.GoogleCloudDocumentaiV1BoundingPoly] = None
	)
	
	object CloudAiPlatformTenantresourceIamPolicyBinding {
		enum ResourceTypeEnum extends Enum[ResourceTypeEnum] { case RESOURCE_TYPE_UNSPECIFIED, PROJECT, SERVICE_ACCOUNT, GCS_BUCKET, SERVICE_CONSUMER, AR_REPO }
	}
	case class CloudAiPlatformTenantresourceIamPolicyBinding(
	  /** Input/Output [Required]. The member service accounts with the roles above. Note: placeholders are same as the resource above. */
		members: Option[List[String]] = None,
	  /** Input/Output [Required]. Specifies the type of resource that will be accessed by members. */
		resourceType: Option[Schema.CloudAiPlatformTenantresourceIamPolicyBinding.ResourceTypeEnum] = None,
	  /** Input/Output [Required]. The resource name that will be accessed by members, which also depends on resource_type. Note: placeholders are supported in resource names. For example, ${tpn} will be used when the tenant project number is not ready. */
		resource: Option[String] = None,
	  /** Input/Output [Required]. The role for members below. */
		role: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1QAResultHighlight(
	  /** Start index of the highlight. */
		startIndex: Option[Int] = None,
	  /** End index of the highlight, exclusive. */
		endIndex: Option[Int] = None
	)
	
	case class GoogleTypeInterval(
	  /** Optional. Exclusive end of the interval. If specified, a Timestamp matching this interval will have to be before the end. */
		endTime: Option[String] = None,
	  /** Optional. Inclusive start of the interval. If specified, a Timestamp matching this interval will have to be the same or after the start. */
		startTime: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1CreateDocumentRequest(
	  /** Default document policy during creation. This refers to an Identity and Access (IAM) policy, which specifies access controls for the Document. Conditions defined in the policy will be ignored. */
		policy: Option[Schema.GoogleIamV1Policy] = None,
	  /** Required. The document to create. */
		document: Option[Schema.GoogleCloudContentwarehouseV1Document] = None,
	  /** Request Option for processing Cloud AI Document in Document Warehouse. This field offers limited support for mapping entities from Cloud AI Document to Warehouse Document. Please consult with product team before using this field and other available options. */
		cloudAiDocumentOption: Option[Schema.GoogleCloudContentwarehouseV1CloudAIDocumentOption] = None,
	  /** The meta information collected about the end user, used to enforce access control for the service. */
		requestMetadata: Option[Schema.GoogleCloudContentwarehouseV1RequestMetadata] = None,
	  /** Field mask for creating Document fields. If mask path is empty, it means all fields are masked. For the `FieldMask` definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#fieldmask. */
		createMask: Option[String] = None
	)
	
	case class GoogleTypeDateTime(
	  /** UTC offset. Must be whole seconds, between -18 hours and +18 hours. For example, a UTC offset of -4:00 would be represented as { seconds: -14400 }. */
		utcOffset: Option[String] = None,
	  /** Optional. Day of month. Must be from 1 to 31 and valid for the year and month, or 0 if specifying a datetime without a day. */
		day: Option[Int] = None,
	  /** Optional. Month of year. Must be from 1 to 12, or 0 if specifying a datetime without a month. */
		month: Option[Int] = None,
	  /** Optional. Seconds of minutes of the time. Must normally be from 0 to 59, defaults to 0. An API may allow the value 60 if it allows leap-seconds. */
		seconds: Option[Int] = None,
	  /** Optional. Year of date. Must be from 1 to 9999, or 0 if specifying a datetime without a year. */
		year: Option[Int] = None,
	  /** Optional. Fractions of seconds in nanoseconds. Must be from 0 to 999,999,999, defaults to 0. */
		nanos: Option[Int] = None,
	  /** Time zone. */
		timeZone: Option[Schema.GoogleTypeTimeZone] = None,
	  /** Optional. Minutes of hour of day. Must be from 0 to 59, defaults to 0. */
		minutes: Option[Int] = None,
	  /** Optional. Hours of day in 24 hour format. Should be from 0 to 23, defaults to 0 (midnight). An API may choose to allow the value "24:00:00" for scenarios like business closing time. */
		hours: Option[Int] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageDimension(
	  /** Dimension unit. */
		unit: Option[String] = None,
	  /** Page width. */
		width: Option[BigDecimal] = None,
	  /** Page height. */
		height: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentChunkedDocument(
	  /** List of chunks. */
		chunks: Option[List[Schema.GoogleCloudDocumentaiV1DocumentChunkedDocumentChunk]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentRevision(
	  /** The revisions that this revision is based on. This can include one or more parent (when documents are merged.) This field represents the index into the `revisions` field. */
		parent: Option[List[Int]] = None,
	  /** If the annotation was made by processor identify the processor by its resource name. */
		processor: Option[String] = None,
	  /** Id of the revision, internally generated by doc proto storage. Unique within the context of the document. */
		id: Option[String] = None,
	  /** Human Review information of this revision. */
		humanReview: Option[Schema.GoogleCloudDocumentaiV1DocumentRevisionHumanReview] = None,
	  /** The revisions that this revision is based on. Must include all the ids that have anything to do with this revision - eg. there are `provenance.parent.revision` fields that index into this field. */
		parentIds: Option[List[String]] = None,
	  /** The time that the revision was created, internally generated by doc proto storage at the time of create. */
		createTime: Option[String] = None,
	  /** If the change was made by a person specify the name or id of that person. */
		agent: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1TextTypeOptions(
	
	)
	
	case class GoogleCloudContentwarehouseV1ResponseMetadata(
	  /** A unique id associated with this call. This id is logged for tracking purpose. */
		requestId: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1GcsIngestWithDocAiProcessorsPipeline(
	  /** The extract processors information. One matched extract processor will be used to process documents based on the classify processor result. If no classify processor is specified, the first extract processor will be used. */
		extractProcessorInfos: Option[List[Schema.GoogleCloudContentwarehouseV1ProcessorInfo]] = None,
	  /** The flag whether to skip ingested documents. If it is set to true, documents in Cloud Storage contains key "status" with value "status=ingested" in custom metadata will be skipped to ingest. */
		skipIngestedDocuments: Option[Boolean] = None,
	  /** The Cloud Storage folder path used to store the raw results from processors. Format: `gs:///`. */
		processorResultsFolderPath: Option[String] = None,
	  /** Optional. The config for the Cloud Storage Ingestion with DocAI Processors pipeline. It provides additional customization options to run the pipeline and can be skipped if it is not applicable. */
		pipelineConfig: Option[Schema.GoogleCloudContentwarehouseV1IngestPipelineConfig] = None,
	  /** The input Cloud Storage folder. All files under this folder will be imported to Document Warehouse. Format: `gs:///`. */
		inputPath: Option[String] = None,
	  /** The split and classify processor information. The split and classify result will be used to find a matched extract processor. */
		splitClassifyProcessorInfo: Option[Schema.GoogleCloudContentwarehouseV1ProcessorInfo] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentChunkedDocumentChunkChunkPageHeader(
	  /** Page span of the header. */
		pageSpan: Option[Schema.GoogleCloudDocumentaiV1DocumentChunkedDocumentChunkChunkPageSpan] = None,
	  /** Header in text format. */
		text: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1RunPipelineRequest(
	  /** Use a DocAI processor to process documents in Document Warehouse, and re-ingest the updated results into Document Warehouse. */
		processWithDocAiPipeline: Option[Schema.GoogleCloudContentwarehouseV1ProcessWithDocAiPipeline] = None,
	  /** Cloud Storage ingestion pipeline. */
		gcsIngestPipeline: Option[Schema.GoogleCloudContentwarehouseV1GcsIngestPipeline] = None,
	  /** Export docuemnts from Document Warehouse to CDW for training purpose. */
		exportCdwPipeline: Option[Schema.GoogleCloudContentwarehouseV1ExportToCdwPipeline] = None,
	  /** The meta information collected about the end user, used to enforce access control for the service. */
		requestMetadata: Option[Schema.GoogleCloudContentwarehouseV1RequestMetadata] = None,
	  /** Use DocAI processors to process documents in Cloud Storage and ingest them to Document Warehouse. */
		gcsIngestWithDocAiProcessorsPipeline: Option[Schema.GoogleCloudContentwarehouseV1GcsIngestWithDocAiProcessorsPipeline] = None
	)
	
	case class GoogleCloudContentwarehouseV1SetAclRequest(
	  /** The meta information collected about the end user, used to enforce access control for the service. */
		requestMetadata: Option[Schema.GoogleCloudContentwarehouseV1RequestMetadata] = None,
	  /** For Set Project ACL only. Authorization check for end user will be ignored when project_owner=true. */
		projectOwner: Option[Boolean] = None,
	  /** Required. REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. This refers to an Identity and Access (IAM) policy, which specifies access controls for the Document. You can set ACL with condition for projects only. Supported operators are: `=`, `!=`, `<`, `<=`, `>`, and `>=` where the left of the operator is `DocumentSchemaId` or property name and the right of the operator is a number or a quoted string. You must escape backslash (\\) and quote (\") characters. Boolean expressions (AND/OR) are supported up to 3 levels of nesting (for example, "((A AND B AND C) OR D) AND E"), a maximum of 10 comparisons are allowed in the expression. The expression must be < 6000 bytes in length. Sample condition: `"DocumentSchemaId = \"some schema id\" OR SchemaId.floatPropertyName >= 10"` */
		policy: Option[Schema.GoogleIamV1Policy] = None
	)
	
	case class GoogleCloudContentwarehouseV1RuleActionsPair(
	  /** Represents the rule. */
		rule: Option[Schema.GoogleCloudContentwarehouseV1Rule] = None,
	  /** Outputs of executing the actions associated with the above rule. */
		actionOutputs: Option[List[Schema.GoogleCloudContentwarehouseV1ActionOutput]] = None
	)
	
	case class GoogleCloudContentwarehouseV1ListLinkedTargetsResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Target document-links. */
		documentLinks: Option[List[Schema.GoogleCloudContentwarehouseV1DocumentLink]] = None
	)
	
	case class GoogleCloudContentwarehouseV1FetchAclRequest(
	  /** The meta information collected about the end user, used to enforce access control for the service. */
		requestMetadata: Option[Schema.GoogleCloudContentwarehouseV1RequestMetadata] = None,
	  /** For Get Project ACL only. Authorization check for end user will be ignored when project_owner=true. */
		projectOwner: Option[Boolean] = None
	)
	
	case class GoogleCloudContentwarehouseV1RequestMetadata(
	  /** Provides user unique identification and groups information. */
		userInfo: Option[Schema.GoogleCloudContentwarehouseV1UserInfo] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentEntityNormalizedValue(
	  /** Money value. See also: https://github.com/googleapis/googleapis/blob/master/google/type/money.proto */
		moneyValue: Option[Schema.GoogleTypeMoney] = None,
	  /** Integer value. */
		integerValue: Option[Int] = None,
	  /** Float value. */
		floatValue: Option[BigDecimal] = None,
	  /** DateTime value. Includes date, time, and timezone. See also: https://github.com/googleapis/googleapis/blob/master/google/type/datetime.proto */
		datetimeValue: Option[Schema.GoogleTypeDateTime] = None,
	  /** Postal address. See also: https://github.com/googleapis/googleapis/blob/master/google/type/postal_address.proto */
		addressValue: Option[Schema.GoogleTypePostalAddress] = None,
	  /** Date value. Includes year, month, day. See also: https://github.com/googleapis/googleapis/blob/master/google/type/date.proto */
		dateValue: Option[Schema.GoogleTypeDate] = None,
	  /** Boolean value. Can be used for entities with binary values, or for checkboxes. */
		booleanValue: Option[Boolean] = None,
	  /** Optional. An optional field to store a normalized string. For some entity types, one of respective `structured_value` fields may also be populated. Also not all the types of `structured_value` will be normalized. For example, some processors may not generate `float` or `integer` normalized text by default. Below are sample formats mapped to structured values. - Money/Currency type (`money_value`) is in the ISO 4217 text format. - Date type (`date_value`) is in the ISO 8601 text format. - Datetime type (`datetime_value`) is in the ISO 8601 text format. */
		text: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1UpdateDocumentResponse(
	  /** Output from Rule Engine recording the rule evaluator and action executor's output. Refer format in: google/cloud/contentwarehouse/v1/rule_engine.proto */
		ruleEngineOutput: Option[Schema.GoogleCloudContentwarehouseV1RuleEngineOutput] = None,
	  /** Additional information for the API invocation, such as the request tracking id. */
		metadata: Option[Schema.GoogleCloudContentwarehouseV1ResponseMetadata] = None,
	  /** Updated document after executing update request. */
		document: Option[Schema.GoogleCloudContentwarehouseV1Document] = None
	)
	
	case class GoogleCloudContentwarehouseV1CreateDocumentLinkRequest(
	  /** The meta information collected about the document creator, used to enforce access control for the service. */
		requestMetadata: Option[Schema.GoogleCloudContentwarehouseV1RequestMetadata] = None,
	  /** Required. Document links associated with the source documents (source_document_id). */
		documentLink: Option[Schema.GoogleCloudContentwarehouseV1DocumentLink] = None
	)
	
	case class GoogleCloudContentwarehouseV1DocumentReference(
	  /** Output only. The time when the document is created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the document is deleted. */
		deleteTime: Option[String] = None,
	  /** Output only. The time when the document is last updated. */
		updateTime: Option[String] = None,
	  /** display_name of the referenced document; this name does not need to be consistent to the display_name in the Document proto, depending on the ACL constraint. */
		displayName: Option[String] = None,
	  /** Stores the subset of the referenced document's content. This is useful to allow user peek the information of the referenced document. */
		snippet: Option[String] = None,
	  /** Required. Name of the referenced document. */
		documentName: Option[String] = None,
	  /** Document is a folder with legal hold. */
		documentIsLegalHoldFolder: Option[Boolean] = None,
	  /** Document is a folder with retention policy. */
		documentIsRetentionFolder: Option[Boolean] = None,
	  /** The document type of the document being referenced. */
		documentIsFolder: Option[Boolean] = None
	)
	
	object GoogleCloudContentwarehouseV1SearchDocumentsRequest {
		enum TotalResultSizeEnum extends Enum[TotalResultSizeEnum] { case TOTAL_RESULT_SIZE_UNSPECIFIED, ESTIMATED_SIZE, ACTUAL_SIZE }
	}
	case class GoogleCloudContentwarehouseV1SearchDocumentsRequest(
	  /** An expression specifying a histogram request against matching documents. Expression syntax is an aggregation function call with histogram facets and other options. The following aggregation functions are supported: &#42; `count(string_histogram_facet)`: Count the number of matching entities for each distinct attribute value. Data types: &#42; Histogram facet (aka filterable properties): Facet names with format <schema id>.<facet>. Facets will have the format of: `a-zA-Z`. If the facet is a child facet, then the parent hierarchy needs to be specified separated by dots in the prefix after the schema id. Thus, the format for a multi- level facet is: <schema id>.<parent facet name>. <child facet name>. Example: schema123.root_parent_facet.middle_facet.child_facet &#42; DocumentSchemaId: (with no schema id prefix) to get histograms for each document type (returns the schema id path, e.g. projects/12345/locations/us-west/documentSchemas/abc123). Example expression: &#42; Document type counts: count('DocumentSchemaId') &#42; For schema id, abc123, get the counts for MORTGAGE_TYPE: count('abc123.MORTGAGE_TYPE') */
		histogramQueries: Option[List[Schema.GoogleCloudContentwarehouseV1HistogramQuery]] = None,
	  /** Controls if the search document request requires the return of a total size of matched documents. See SearchDocumentsResponse.total_size. */
		totalResultSize: Option[Schema.GoogleCloudContentwarehouseV1SearchDocumentsRequest.TotalResultSizeEnum] = None,
	  /** The token specifying the current offset within search results. See SearchDocumentsResponse.next_page_token for an explanation of how to obtain the next set of query results. */
		pageToken: Option[String] = None,
	  /** The meta information collected about the end user, used to enforce access control and improve the search quality of the service. */
		requestMetadata: Option[Schema.GoogleCloudContentwarehouseV1RequestMetadata] = None,
	  /** Query used to search against documents (keyword, filters, etc.). */
		documentQuery: Option[Schema.GoogleCloudContentwarehouseV1DocumentQuery] = None,
	  /** Controls if the search document request requires the return of a total size of matched documents. See SearchDocumentsResponse.total_size. Enabling this flag may adversely impact performance. Hint: If this is used with pagination, set this flag on the initial query but set this to false on subsequent page calls (keep the total count locally). Defaults to false. */
		requireTotalSize: Option[Boolean] = None,
	  /** Experimental, do not use. The limit on the number of documents returned for the question-answering feature. To enable the question-answering feature, set [DocumentQuery].is_nl_query to true. */
		qaSizeLimit: Option[Int] = None,
	  /** An integer that specifies the current offset (that is, starting result location, amongst the documents deemed by the API as relevant) in search results. This field is only considered if page_token is unset. The maximum allowed value is 5000. Otherwise an error is thrown. For example, 0 means to return results starting from the first matching document, and 10 means to return from the 11th document. This can be used for pagination, (for example, pageSize = 10 and offset = 10 means to return from the second page). */
		offset: Option[Int] = None,
	  /** A limit on the number of documents returned in the search results. Increasing this value above the default value of 10 can increase search response time. The value can be between 1 and 100. */
		pageSize: Option[Int] = None,
	  /** The criteria determining how search results are sorted. For non-empty query, default is `"relevance desc"`. For empty query, default is `"upload_date desc"`. Supported options are: &#42; `"relevance desc"`: By relevance descending, as determined by the API algorithms. &#42; `"upload_date desc"`: By upload date descending. &#42; `"upload_date"`: By upload date ascending. &#42; `"update_date desc"`: By last updated date descending. &#42; `"update_date"`: By last updated date ascending. &#42; `"retrieval_importance desc"`: By retrieval importance of properties descending. This feature is still under development, please do not use unless otherwise instructed to do so. */
		orderBy: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageToken(
	  /** The history of this annotation. */
		provenance: Option[Schema.GoogleCloudDocumentaiV1DocumentProvenance] = None,
	  /** Text style attributes. */
		styleInfo: Option[Schema.GoogleCloudDocumentaiV1DocumentPageTokenStyleInfo] = None,
	  /** Layout for Token. */
		layout: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None,
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None,
	  /** Detected break at the end of a Token. */
		detectedBreak: Option[Schema.GoogleCloudDocumentaiV1DocumentPageTokenDetectedBreak] = None
	)
	
	case class GoogleTypeMoney(
	  /** The three-letter currency code defined in ISO 4217. */
		currencyCode: Option[String] = None,
	  /** The whole units of the amount. For example if `currencyCode` is `"USD"`, then 1 unit is one US dollar. */
		units: Option[String] = None,
	  /** Number of nano (10^-9) units of the amount. The value must be between -999,999,999 and +999,999,999 inclusive. If `units` is positive, `nanos` must be positive or zero. If `units` is zero, `nanos` can be positive, zero, or negative. If `units` is negative, `nanos` must be negative or zero. For example $-1.75 is represented as `units`=-1 and `nanos`=-750,000,000. */
		nanos: Option[Int] = None
	)
	
	case class CloudAiPlatformTenantresourceGcsBucketConfig(
	  /** Input/Output [Optional]. The KMS key name or the KMS grant name used for CMEK encryption. Only set this field when provisioning new GCS bucket. For existing GCS bucket, this field will be ignored because CMEK re-encryption is not supported. */
		kmsKeyReference: Option[String] = None,
	  /** Input/Output [Optional]. Only needed for per-entity tenant GCP resources. During Deprovision API, the on-demand deletion will only cover the tenant GCP resources with the specified entity name. */
		entityName: Option[String] = None,
	  /** Input/Output [Optional]. Only needed when the content in bucket need to be garbage collected within some amount of days. */
		ttlDays: Option[Int] = None,
	  /** Input/Output [Required]. IAM roles (viewer/admin) put on the bucket. */
		viewers: Option[List[String]] = None,
	  /** Input/Output [Optional]. The name of a GCS bucket with max length of 63 chars. If not set, a random UUID will be generated as bucket name. */
		bucketName: Option[String] = None,
		admins: Option[List[String]] = None
	)
	
	case class GoogleCloudContentwarehouseV1CloudAIDocumentOption(
	  /** If set, only selected entities will be converted to properties. */
		customizedEntitiesPropertiesConversions: Option[Map[String, String]] = None,
	  /** Whether to convert all the entities to properties. */
		enableEntitiesConversions: Option[Boolean] = None
	)
	
	case class GoogleTypeTimeZone(
	  /** Optional. IANA Time Zone Database version number, e.g. "2019a". */
		version: Option[String] = None,
	  /** IANA Time Zone Database time zone, e.g. "America/New_York". */
		id: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1CreateDocumentMetadata(
	
	)
	
	case class GoogleCloudContentwarehouseV1ListLinkedTargetsRequest(
	  /** The meta information collected about the document creator, used to enforce access control for the service. */
		requestMetadata: Option[Schema.GoogleCloudContentwarehouseV1RequestMetadata] = None
	)
	
	case class GoogleCloudContentwarehouseV1EnumValue(
	  /** String value of the enum field. This must match defined set of enums in document schema using EnumTypeOptions. */
		value: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1DateTimeTypeOptions(
	
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageTokenStyleInfo(
	  /** Whether the text is bold (equivalent to font_weight is at least `700`). */
		bold: Option[Boolean] = None,
	  /** Color of the text. */
		textColor: Option[Schema.GoogleTypeColor] = None,
	  /** Whether the text is strikethrough. This feature is not supported yet. */
		strikeout: Option[Boolean] = None,
	  /** Letter spacing in points. */
		letterSpacing: Option[BigDecimal] = None,
	  /** Whether the text is underlined. */
		underlined: Option[Boolean] = None,
	  /** Whether the text is in small caps. This feature is not supported yet. */
		smallcaps: Option[Boolean] = None,
	  /** Font size in pixels, equal to _unrounded font_size_ &#42; _resolution_ ÷ `72.0`. */
		pixelFontSize: Option[BigDecimal] = None,
	  /** Whether the text is italic. */
		italic: Option[Boolean] = None,
	  /** Name or style of the font. */
		fontType: Option[String] = None,
	  /** Whether the text is handwritten. */
		handwritten: Option[Boolean] = None,
	  /** Whether the text is a subscript. This feature is not supported yet. */
		subscript: Option[Boolean] = None,
	  /** Color of the background. */
		backgroundColor: Option[Schema.GoogleTypeColor] = None,
	  /** TrueType weight on a scale `100` (thin) to `1000` (ultra-heavy). Normal is `400`, bold is `700`. */
		fontWeight: Option[Int] = None,
	  /** Whether the text is a superscript. This feature is not supported yet. */
		superscript: Option[Boolean] = None,
	  /** Font size in points (`1` point is `¹⁄₇₂` inches). */
		fontSize: Option[Int] = None
	)
	
	case class GoogleCloudContentwarehouseV1DeleteDocumentAction(
	  /** Boolean field to select between hard vs soft delete options. Set 'true' for 'hard delete' and 'false' for 'soft delete'. */
		enableHardDelete: Option[Boolean] = None
	)
	
	case class GoogleCloudContentwarehouseV1IntegerArray(
	  /** List of integer values. */
		values: Option[List[Int]] = None
	)
	
	case class GoogleCloudContentwarehouseV1PropertyArray(
	  /** List of property values. */
		properties: Option[List[Schema.GoogleCloudContentwarehouseV1Property]] = None
	)
	
	case class GoogleCloudContentwarehouseV1RuleEngineOutput(
	  /** Name of the document against which the rules and actions were evaluated. */
		documentName: Option[String] = None,
	  /** Output from Rule Evaluator containing matched, unmatched and invalid rules. */
		ruleEvaluatorOutput: Option[Schema.GoogleCloudContentwarehouseV1RuleEvaluatorOutput] = None,
	  /** Output from Action Executor containing rule and corresponding actions execution result. */
		actionExecutorOutput: Option[Schema.GoogleCloudContentwarehouseV1ActionExecutorOutput] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentRevisionHumanReview(
	  /** A message providing more details about the current state of processing. For example, the rejection reason when the state is `rejected`. */
		stateMessage: Option[String] = None,
	  /** Human review state. e.g. `requested`, `succeeded`, `rejected`. */
		state: Option[String] = None
	)
	
	case class GoogleRpcStatus(
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None,
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1GcsIngestPipeline(
	  /** The Doc AI processor type name. Only used when the format of ingested files is Doc AI Document proto format. */
		processorType: Option[String] = None,
	  /** The Document Warehouse schema resource name. All documents processed by this pipeline will use this schema. Format: projects/{project_number}/locations/{location}/documentSchemas/{document_schema_id}. */
		schemaName: Option[String] = None,
	  /** The input Cloud Storage folder. All files under this folder will be imported to Document Warehouse. Format: `gs:///`. */
		inputPath: Option[String] = None,
	  /** Optional. The config for the Cloud Storage Ingestion pipeline. It provides additional customization options to run the pipeline and can be skipped if it is not applicable. */
		pipelineConfig: Option[Schema.GoogleCloudContentwarehouseV1IngestPipelineConfig] = None,
	  /** The flag whether to skip ingested documents. If it is set to true, documents in Cloud Storage contains key "status" with value "status=ingested" in custom metadata will be skipped to ingest. */
		skipIngestedDocuments: Option[Boolean] = None
	)
	
	case class GoogleCloudContentwarehouseV1RunPipelineMetadataProcessWithDocAiPipelineMetadata(
	  /** The DocAI processor to process the documents with. */
		processorInfo: Option[Schema.GoogleCloudContentwarehouseV1ProcessorInfo] = None,
	  /** The input list of all the resource names of the documents to be processed. */
		documents: Option[List[String]] = None
	)
	
	case class GoogleCloudContentwarehouseV1CreateDocumentResponse(
	  /** Additional information for the API invocation, such as the request tracking id. */
		metadata: Option[Schema.GoogleCloudContentwarehouseV1ResponseMetadata] = None,
	  /** Document created after executing create request. */
		document: Option[Schema.GoogleCloudContentwarehouseV1Document] = None,
	  /** post-processing LROs */
		longRunningOperations: Option[List[Schema.GoogleLongrunningOperation]] = None,
	  /** Output from Rule Engine recording the rule evaluator and action executor's output. Refer format in: google/cloud/contentwarehouse/v1/rule_engine.proto */
		ruleEngineOutput: Option[Schema.GoogleCloudContentwarehouseV1RuleEngineOutput] = None
	)
	
	case class GoogleCloudContentwarehouseV1UpdateDocumentSchemaRequest(
	  /** Required. The document schema to update with. */
		documentSchema: Option[Schema.GoogleCloudContentwarehouseV1DocumentSchema] = None
	)
	
	object GoogleCloudContentwarehouseV1Document {
		enum ContentCategoryEnum extends Enum[ContentCategoryEnum] { case CONTENT_CATEGORY_UNSPECIFIED, CONTENT_CATEGORY_IMAGE, CONTENT_CATEGORY_AUDIO, CONTENT_CATEGORY_VIDEO }
		enum RawDocumentFileTypeEnum extends Enum[RawDocumentFileTypeEnum] { case RAW_DOCUMENT_FILE_TYPE_UNSPECIFIED, RAW_DOCUMENT_FILE_TYPE_PDF, RAW_DOCUMENT_FILE_TYPE_DOCX, RAW_DOCUMENT_FILE_TYPE_XLSX, RAW_DOCUMENT_FILE_TYPE_PPTX, RAW_DOCUMENT_FILE_TYPE_TEXT, RAW_DOCUMENT_FILE_TYPE_TIFF }
	}
	case class GoogleCloudContentwarehouseV1Document(
	  /** The Document schema name. Format: projects/{project_number}/locations/{location}/documentSchemas/{document_schema_id}. */
		documentSchemaName: Option[String] = None,
	  /** Output only. The time when the document is last updated. */
		updateTime: Option[String] = None,
	  /** Output only. The time when the document is created. */
		createTime: Option[String] = None,
	  /** If true, text extraction will be performed. */
		textExtractionEnabled: Option[Boolean] = None,
	  /** The reference ID set by customers. Must be unique per project and location. */
		referenceId: Option[String] = None,
	  /** The user who lastly updates the document. */
		updater: Option[String] = None,
	  /** Title that describes the document. This can be the top heading or text that describes the document. */
		title: Option[String] = None,
	  /** Indicates the category (image, audio, video etc.) of the original content. */
		contentCategory: Option[Schema.GoogleCloudContentwarehouseV1Document.ContentCategoryEnum] = None,
	  /** Raw document file in Cloud Storage path. */
		rawDocumentPath: Option[String] = None,
	  /** Other document format, such as PPTX, XLXS */
		plainText: Option[String] = None,
	  /** This is used when DocAI was not used to load the document and parsing/ extracting is needed for the inline_raw_document. For example, if inline_raw_document is the byte representation of a PDF file, then this should be set to: RAW_DOCUMENT_FILE_TYPE_PDF. */
		rawDocumentFileType: Option[Schema.GoogleCloudContentwarehouseV1Document.RawDocumentFileTypeEnum] = None,
	  /** Uri to display the document, for example, in the UI. */
		displayUri: Option[String] = None,
	  /** Output only. If linked to a Collection with RetentionPolicy, the date when the document becomes mutable. */
		dispositionTime: Option[String] = None,
	  /** If true, text extraction will not be performed. */
		textExtractionDisabled: Option[Boolean] = None,
	  /** Raw document content. */
		inlineRawDocument: Option[String] = None,
	  /** The user who creates the document. */
		creator: Option[String] = None,
	  /** List of values that are user supplied metadata. */
		properties: Option[List[Schema.GoogleCloudContentwarehouseV1Property]] = None,
	  /** Document AI format to save the structured content, including OCR. */
		cloudAiDocument: Option[Schema.GoogleCloudDocumentaiV1Document] = None,
	  /** Required. Display name of the document given by the user. This name will be displayed in the UI. Customer can populate this field with the name of the document. This differs from the 'title' field as 'title' is optional and stores the top heading in the document. */
		displayName: Option[String] = None,
	  /** The resource name of the document. Format: projects/{project_number}/locations/{location}/documents/{document_id}. The name is ignored when creating a document. */
		name: Option[String] = None,
	  /** Output only. Indicates if the document has a legal hold on it. */
		legalHold: Option[Boolean] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentEntity(
	  /** Optional. Canonical id. This will be a unique value in the entity list for this document. */
		id: Option[String] = None,
	  /** Optional. Confidence of detected Schema entity. Range `[0, 1]`. */
		confidence: Option[BigDecimal] = None,
	  /** Required. Entity type from a schema e.g. `Address`. */
		`type`: Option[String] = None,
	  /** Optional. Entities can be nested to form a hierarchical data structure representing the content in the document. */
		properties: Option[List[Schema.GoogleCloudDocumentaiV1DocumentEntity]] = None,
	  /** Optional. Text value of the entity e.g. `1600 Amphitheatre Pkwy`. */
		mentionText: Option[String] = None,
	  /** Optional. Provenance of the entity. Text anchor indexing into the Document.text. */
		textAnchor: Option[Schema.GoogleCloudDocumentaiV1DocumentTextAnchor] = None,
	  /** Optional. Represents the provenance of this entity wrt. the location on the page where it was found. */
		pageAnchor: Option[Schema.GoogleCloudDocumentaiV1DocumentPageAnchor] = None,
	  /** Optional. The history of this annotation. */
		provenance: Option[Schema.GoogleCloudDocumentaiV1DocumentProvenance] = None,
	  /** Optional. Normalized entity value. Absent if the extracted value could not be converted or the type (e.g. address) is not supported for certain parsers. This field is also only populated for certain supported document types. */
		normalizedValue: Option[Schema.GoogleCloudDocumentaiV1DocumentEntityNormalizedValue] = None,
	  /** Optional. Deprecated. Use `id` field instead. */
		mentionId: Option[String] = None,
	  /** Optional. Whether the entity will be redacted for de-identification purposes. */
		redacted: Option[Boolean] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentStyle(
	  /** Text anchor indexing into the Document.text. */
		textAnchor: Option[Schema.GoogleCloudDocumentaiV1DocumentTextAnchor] = None,
	  /** Font size. */
		fontSize: Option[Schema.GoogleCloudDocumentaiV1DocumentStyleFontSize] = None,
	  /** Text background color. */
		backgroundColor: Option[Schema.GoogleTypeColor] = None,
	  /** Text color. */
		color: Option[Schema.GoogleTypeColor] = None,
	  /** [Text decoration](https://www.w3schools.com/cssref/pr_text_text-decoration.asp). Follows CSS standard.  */
		textDecoration: Option[String] = None,
	  /** Font family such as `Arial`, `Times New Roman`. https://www.w3schools.com/cssref/pr_font_font-family.asp */
		fontFamily: Option[String] = None,
	  /** [Text style](https://www.w3schools.com/cssref/pr_font_font-style.asp). Possible values are `normal`, `italic`, and `oblique`. */
		textStyle: Option[String] = None,
	  /** [Font weight](https://www.w3schools.com/cssref/pr_font_weight.asp). Possible values are `normal`, `bold`, `bolder`, and `lighter`. */
		fontWeight: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1UpdateDocumentMetadata(
	
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageTableTableRow(
	  /** Cells that make up this row. */
		cells: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageTableTableCell]] = None
	)
	
	case class GoogleTypeDate(
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None,
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None
	)
	
	case class GoogleCloudContentwarehouseV1PropertyTypeOptions(
	  /** Required. List of property definitions. */
		propertyDefinitions: Option[List[Schema.GoogleCloudContentwarehouseV1PropertyDefinition]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentProvenanceParent(
	  /** The id of the parent provenance. */
		id: Option[Int] = None,
	  /** The index of the index into current revision's parent_ids list. */
		revision: Option[Int] = None,
	  /** The index of the parent item in the corresponding item list (eg. list of entities, properties within entities, etc.) in the parent revision. */
		index: Option[Int] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlock(
	  /** Block consisting of text content. */
		textBlock: Option[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutTextBlock] = None,
	  /** Block consisting of table content/structure. */
		tableBlock: Option[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutTableBlock] = None,
	  /** ID of the block. */
		blockId: Option[String] = None,
	  /** Block consisting of list content/structure. */
		listBlock: Option[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutListBlock] = None,
	  /** Page span of the block. */
		pageSpan: Option[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutPageSpan] = None
	)
	
	case class GoogleCloudContentwarehouseV1EnumArray(
	  /** List of enum values. */
		values: Option[List[String]] = None
	)
	
	case class CloudAiPlatformTenantresourceInfraSpannerConfig(
	  /** Input [Optional]. The options to create a spanner database. Note: give the right options to ensure the right KMS key access audit logging and AxT logging in expected logging category. */
		createDatabaseOptions: Option[Schema.CloudAiPlatformTenantresourceInfraSpannerConfigCreateDatabaseOptions] = None,
		spannerLocalNamePrefix: Option[String] = None,
	  /** Input [Required]. Every database in Spanner can be identified by the following path name: /span//: */
		spannerUniverse: Option[String] = None,
	  /** Input [Optional]. The KMS key name or the KMS grant name used for CMEK encryption. Only set this field when provisioning new Infra Spanner databases. For existing Infra Spanner databases, this field will be ignored because CMEK re-encryption is not supported. For example, projects//locations//keyRings//cryptoKeys/ */
		kmsKeyReference: Option[String] = None,
	  /** Input [Optional]. The spanner borg service account for delegating the kms key to. For example, spanner-infra-cmek-nonprod@system.gserviceaccount.com, for the nonprod universe. */
		spannerBorgServiceAccount: Option[String] = None,
		spannerNamespace: Option[String] = None,
	  /** Input [Required]. The file path to the spanner SDL bundle. */
		sdlBundlePath: Option[String] = None
	)
	
	object GoogleCloudContentwarehouseV1Rule {
		enum TriggerTypeEnum extends Enum[TriggerTypeEnum] { case UNKNOWN, ON_CREATE, ON_UPDATE, ON_CREATE_LINK, ON_DELETE_LINK }
	}
	case class GoogleCloudContentwarehouseV1Rule(
	  /** Short description of the rule and its context. */
		description: Option[String] = None,
	  /** Identifies the trigger type for running the policy. */
		triggerType: Option[Schema.GoogleCloudContentwarehouseV1Rule.TriggerTypeEnum] = None,
	  /** List of actions that are executed when the rule is satisfied. */
		actions: Option[List[Schema.GoogleCloudContentwarehouseV1Action]] = None,
	  /** ID of the rule. It has to be unique across all the examples. This is managed internally. */
		ruleId: Option[String] = None,
	  /** Represents the conditional expression to be evaluated. Expression should evaluate to a boolean result. When the condition is true actions are executed. Example: user_role = "hsbc_role_1" AND doc.salary > 20000 */
		condition: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1ActionExecutorOutput(
	  /** List of rule and corresponding actions result. */
		ruleActionsPairs: Option[List[Schema.GoogleCloudContentwarehouseV1RuleActionsPair]] = None
	)
	
	case class GoogleCloudContentwarehouseV1UpdateRuleSetRequest(
	  /** Required. The rule set to update. */
		ruleSet: Option[Schema.GoogleCloudContentwarehouseV1RuleSet] = None
	)
	
	case class GoogleApiServiceconsumermanagementV1PolicyBinding(
	  /** Uses the same format as in IAM policy. `member` must include both a prefix and ID. For example, `user:{emailId}`, `serviceAccount:{emailId}`, `group:{emailId}`. */
		members: Option[List[String]] = None,
	  /** Role. (https://cloud.google.com/iam/docs/understanding-roles) For example, `roles/viewer`, `roles/editor`, or `roles/owner`. */
		role: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1ProcessWithDocAiPipeline(
	  /** The CDW processor information. */
		processorInfo: Option[Schema.GoogleCloudContentwarehouseV1ProcessorInfo] = None,
	  /** The Cloud Storage folder path used to store the exported documents before being sent to CDW. Format: `gs:///`. */
		exportFolderPath: Option[String] = None,
	  /** The list of all the resource names of the documents to be processed. Format: projects/{project_number}/locations/{location}/documents/{document_id}. */
		documents: Option[List[String]] = None,
	  /** The Cloud Storage folder path used to store the raw results from processors. Format: `gs:///`. */
		processorResultsFolderPath: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageBlock(
	  /** Layout for Block. */
		layout: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None,
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None,
	  /** The history of this annotation. */
		provenance: Option[Schema.GoogleCloudDocumentaiV1DocumentProvenance] = None
	)
	
	case class GoogleCloudContentwarehouseV1PublishAction(
	  /** The topic id in the Pub/Sub service for which messages will be published to. */
		topicId: Option[String] = None,
	  /** Messages to be published. */
		messages: Option[List[String]] = None
	)
	
	case class GoogleCloudDocumentaiV1Document(
	  /** Information about the sharding if this document is sharded part of a larger document. If the document is not sharded, this message is not specified. */
		shardInfo: Option[Schema.GoogleCloudDocumentaiV1DocumentShardInfo] = None,
	  /** Placeholder. Revision history of this document. */
		revisions: Option[List[Schema.GoogleCloudDocumentaiV1DocumentRevision]] = None,
	  /** Optional. Inline document content, represented as a stream of bytes. Note: As with all `bytes` fields, protobuffers use a pure binary representation, whereas JSON representations use base64. */
		content: Option[String] = None,
	  /** Optional. UTF-8 encoded text in reading order from the document. */
		text: Option[String] = None,
	  /** A list of entities detected on Document.text. For document shards, entities in this list may cross shard boundaries. */
		entities: Option[List[Schema.GoogleCloudDocumentaiV1DocumentEntity]] = None,
	  /** An IANA published [media type (MIME type)](https://www.iana.org/assignments/media-types/media-types.xhtml). */
		mimeType: Option[String] = None,
	  /** Placeholder. Relationship among Document.entities. */
		entityRelations: Option[List[Schema.GoogleCloudDocumentaiV1DocumentEntityRelation]] = None,
	  /** Any error that occurred while processing this document. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** Styles for the Document.text. */
		textStyles: Option[List[Schema.GoogleCloudDocumentaiV1DocumentStyle]] = None,
	  /** Optional. Currently supports Google Cloud Storage URI of the form `gs://bucket_name/object_name`. Object versioning is not supported. For more information, refer to [Google Cloud Storage Request URIs](https://cloud.google.com/storage/docs/reference-uris). */
		uri: Option[String] = None,
	  /** Document chunked based on chunking config. */
		chunkedDocument: Option[Schema.GoogleCloudDocumentaiV1DocumentChunkedDocument] = None,
	  /** Placeholder. A list of text corrections made to Document.text. This is usually used for annotating corrections to OCR mistakes. Text changes for a given revision may not overlap with each other. */
		textChanges: Option[List[Schema.GoogleCloudDocumentaiV1DocumentTextChange]] = None,
	  /** Parsed layout of the document. */
		documentLayout: Option[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayout] = None,
	  /** Visual page layout for the Document. */
		pages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPage]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentStyleFontSize(
	  /** Unit for the font size. Follows CSS naming (such as `in`, `px`, and `pt`). */
		unit: Option[String] = None,
	  /** Font size for the text. */
		size: Option[BigDecimal] = None
	)
	
	case class GoogleApiServiceconsumermanagementV1BillingConfig(
	  /** Name of the billing account. For example `billingAccounts/012345-567890-ABCDEF`. */
		billingAccount: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPage(
	  /** A list of visually detected tokens on the page. */
		tokens: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageToken]] = None,
	  /** Rendered image for this page. This image is preprocessed to remove any skew, rotation, and distortions such that the annotation bounding boxes can be upright and axis-aligned. */
		image: Option[Schema.GoogleCloudDocumentaiV1DocumentPageImage] = None,
	  /** A list of visually detected form fields on the page. */
		formFields: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageFormField]] = None,
	  /** 1-based index for current Page in a parent Document. Useful when a page is taken out of a Document for individual processing. */
		pageNumber: Option[Int] = None,
	  /** A list of visually detected tables on the page. */
		tables: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageTable]] = None,
	  /** A list of visually detected symbols on the page. */
		symbols: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageSymbol]] = None,
	  /** The history of this page. */
		provenance: Option[Schema.GoogleCloudDocumentaiV1DocumentProvenance] = None,
	  /** A list of detected non-text visual elements e.g. checkbox, signature etc. on the page. */
		visualElements: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageVisualElement]] = None,
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None,
	  /** Physical dimension of the page. */
		dimension: Option[Schema.GoogleCloudDocumentaiV1DocumentPageDimension] = None,
	  /** A list of visually detected text paragraphs on the page. A collection of lines that a human would perceive as a paragraph. */
		paragraphs: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageParagraph]] = None,
	  /** Layout for the page. */
		layout: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None,
	  /** Transformation matrices that were applied to the original document image to produce Page.image. */
		transforms: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageMatrix]] = None,
	  /** A list of detected barcodes. */
		detectedBarcodes: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedBarcode]] = None,
	  /** A list of visually detected text blocks on the page. A block has a set of lines (collected into paragraphs) that have a common line-spacing and orientation. */
		blocks: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageBlock]] = None,
	  /** Image quality scores. */
		imageQualityScores: Option[Schema.GoogleCloudDocumentaiV1DocumentPageImageQualityScores] = None,
	  /** A list of visually detected text lines on the page. A collection of tokens that a human would perceive as a line. */
		lines: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageLine]] = None
	)
	
	case class GoogleCloudContentwarehouseV1RemoveFromFolderAction(
	  /** Condition of the action to be executed. */
		condition: Option[String] = None,
	  /** Name of the folder under which new document is to be added. Format: projects/{project_number}/locations/{location}/documents/{document_id}. */
		folder: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentChunkedDocumentChunkChunkPageFooter(
	  /** Page span of the footer. */
		pageSpan: Option[Schema.GoogleCloudDocumentaiV1DocumentChunkedDocumentChunkChunkPageSpan] = None,
	  /** Footer in text format. */
		text: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentChunkedDocumentChunk(
	  /** Page headers associated with the chunk. */
		pageHeaders: Option[List[Schema.GoogleCloudDocumentaiV1DocumentChunkedDocumentChunkChunkPageHeader]] = None,
	  /** Page footers associated with the chunk. */
		pageFooters: Option[List[Schema.GoogleCloudDocumentaiV1DocumentChunkedDocumentChunkChunkPageFooter]] = None,
	  /** Text content of the chunk. */
		content: Option[String] = None,
	  /** ID of the chunk. */
		chunkId: Option[String] = None,
	  /** Page span of the chunk. */
		pageSpan: Option[Schema.GoogleCloudDocumentaiV1DocumentChunkedDocumentChunkChunkPageSpan] = None,
	  /** Unused. */
		sourceBlockIds: Option[List[String]] = None
	)
	
	case class GoogleCloudContentwarehouseV1IntegerTypeOptions(
	
	)
	
	case class GoogleCloudContentwarehouseV1ListRuleSetsResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The rule sets from the specified parent. */
		ruleSets: Option[List[Schema.GoogleCloudContentwarehouseV1RuleSet]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageImageQualityScores(
	  /** A list of detected defects. */
		detectedDefects: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageImageQualityScoresDetectedDefect]] = None,
	  /** The overall quality score. Range `[0, 1]` where `1` is perfect quality. */
		qualityScore: Option[BigDecimal] = None
	)
	
	object GoogleCloudContentwarehouseV1InitializeProjectRequest {
		enum AccessControlModeEnum extends Enum[AccessControlModeEnum] { case ACL_MODE_UNKNOWN, ACL_MODE_UNIVERSAL_ACCESS, ACL_MODE_DOCUMENT_LEVEL_ACCESS_CONTROL_BYOID, ACL_MODE_DOCUMENT_LEVEL_ACCESS_CONTROL_GCI }
		enum DatabaseTypeEnum extends Enum[DatabaseTypeEnum] { case DB_UNKNOWN, DB_INFRA_SPANNER, DB_CLOUD_SQL_POSTGRES }
		enum DocumentCreatorDefaultRoleEnum extends Enum[DocumentCreatorDefaultRoleEnum] { case DOCUMENT_CREATOR_DEFAULT_ROLE_UNSPECIFIED, DOCUMENT_ADMIN, DOCUMENT_EDITOR, DOCUMENT_VIEWER }
	}
	case class GoogleCloudContentwarehouseV1InitializeProjectRequest(
	  /** Required. The access control mode for accessing the customer data */
		accessControlMode: Option[Schema.GoogleCloudContentwarehouseV1InitializeProjectRequest.AccessControlModeEnum] = None,
	  /** Required. The type of database used to store customer data */
		databaseType: Option[Schema.GoogleCloudContentwarehouseV1InitializeProjectRequest.DatabaseTypeEnum] = None,
	  /** Optional. Whether to enable CAL user email logging. */
		enableCalUserEmailLogging: Option[Boolean] = None,
	  /** Optional. The KMS key used for CMEK encryption. It is required that the kms key is in the same region as the endpoint. The same key will be used for all provisioned resources, if encryption is available. If the kms_key is left empty, no encryption will be enforced. */
		kmsKey: Option[String] = None,
	  /** Optional. The default role for the person who create a document. */
		documentCreatorDefaultRole: Option[Schema.GoogleCloudContentwarehouseV1InitializeProjectRequest.DocumentCreatorDefaultRoleEnum] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutTableRow(
	  /** A table row is a list of table cells. */
		cells: Option[List[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutTableCell]] = None
	)
	
	case class CloudAiPlatformTenantresourceCloudSqlInstanceConfig(
	  /** Input [Optional]. MDB roles for corp access to CloudSQL instance. */
		mdbRolesForCorpAccess: Option[List[String]] = None,
	  /** Input [Optional]. The KMS key name or the KMS grant name used for CMEK encryption. Only set this field when provisioning new CloudSQL instances. For existing CloudSQL instances, this field will be ignored because CMEK re-encryption is not supported. */
		kmsKeyReference: Option[String] = None,
	  /** Output only. The CloudSQL instance connection name. */
		cloudSqlInstanceConnectionName: Option[String] = None,
	  /** Input [Required]. The SLM instance template to provision CloudSQL. */
		slmInstanceTemplate: Option[String] = None,
	  /** Input [Required]. The SLM instance type to provision CloudSQL. */
		slmInstanceType: Option[String] = None,
	  /** Output only. The SLM instance's full resource name. */
		slmInstanceName: Option[String] = None,
	  /** Input/Output [Optional]. The CloudSQL instance name within SLM instance. If not set, a random UUIC will be generated as instance name. */
		cloudSqlInstanceName: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageTable(
	  /** Header rows of the table. */
		headerRows: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageTableTableRow]] = None,
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None,
	  /** The history of this table. */
		provenance: Option[Schema.GoogleCloudDocumentaiV1DocumentProvenance] = None,
	  /** Body rows of the table. */
		bodyRows: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageTableTableRow]] = None,
	  /** Layout for Table. */
		layout: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None
	)
	
	case class GoogleCloudContentwarehouseV1TimestampArray(
	  /** List of timestamp values. */
		values: Option[List[Schema.GoogleCloudContentwarehouseV1TimestampValue]] = None
	)
	
	case class GoogleCloudContentwarehouseV1MapProperty(
	  /** Unordered map of dynamically typed values. */
		fields: Option[Map[String, Schema.GoogleCloudContentwarehouseV1Value]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutListEntry(
	  /** A list entry is a list of blocks. Repeated blocks support further hierarchies and nested blocks. */
		blocks: Option[List[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlock]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageTableTableCell(
	  /** How many rows this cell spans. */
		rowSpan: Option[Int] = None,
	  /** Layout for TableCell. */
		layout: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None,
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None,
	  /** How many columns this cell spans. */
		colSpan: Option[Int] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentDocumentLayout(
	  /** List of blocks in the document. */
		blocks: Option[List[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlock]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageImageQualityScoresDetectedDefect(
	  /** Confidence of detected defect. Range `[0, 1]` where `1` indicates strong confidence that the defect exists. */
		confidence: Option[BigDecimal] = None,
	  /** Name of the defect type. Supported values are: - `quality/defect_blurry` - `quality/defect_noisy` - `quality/defect_dark` - `quality/defect_faint` - `quality/defect_text_too_small` - `quality/defect_document_cutoff` - `quality/defect_text_cutoff` - `quality/defect_glare` */
		`type`: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1UserInfo(
	  /** A unique user identification string, as determined by the client. The maximum number of allowed characters is 255. Allowed characters include numbers 0 to 9, uppercase and lowercase letters, and restricted special symbols (:, @, +, -, _, ~) The format is "user:xxxx@example.com"; */
		id: Option[String] = None,
	  /** The unique group identifications which the user is belong to. The format is "group:yyyy@example.com"; */
		groupIds: Option[List[String]] = None
	)
	
	case class GoogleTypeColor(
	  /** The amount of red in the color as a value in the interval [0, 1]. */
		red: Option[BigDecimal] = None,
	  /** The fraction of this color that should be applied to the pixel. That is, the final pixel color is defined by the equation: `pixel color = alpha &#42; (this color) + (1.0 - alpha) &#42; (background color)` This means that a value of 1.0 corresponds to a solid color, whereas a value of 0.0 corresponds to a completely transparent color. This uses a wrapper message rather than a simple float scalar so that it is possible to distinguish between a default value and the value being unset. If omitted, this color object is rendered as a solid color (as if the alpha value had been explicitly given a value of 1.0). */
		alpha: Option[BigDecimal] = None,
	  /** The amount of blue in the color as a value in the interval [0, 1]. */
		blue: Option[BigDecimal] = None,
	  /** The amount of green in the color as a value in the interval [0, 1]. */
		green: Option[BigDecimal] = None
	)
	
	case class GoogleCloudDocumentaiV1Barcode(
	  /** Value format describes the format of the value that a barcode encodes. The supported formats are: - `CONTACT_INFO`: Contact information. - `EMAIL`: Email address. - `ISBN`: ISBN identifier. - `PHONE`: Phone number. - `PRODUCT`: Product. - `SMS`: SMS message. - `TEXT`: Text string. - `URL`: URL address. - `WIFI`: Wifi information. - `GEO`: Geo-localization. - `CALENDAR_EVENT`: Calendar event. - `DRIVER_LICENSE`: Driver's license. */
		valueFormat: Option[String] = None,
	  /** Format of a barcode. The supported formats are: - `CODE_128`: Code 128 type. - `CODE_39`: Code 39 type. - `CODE_93`: Code 93 type. - `CODABAR`: Codabar type. - `DATA_MATRIX`: 2D Data Matrix type. - `ITF`: ITF type. - `EAN_13`: EAN-13 type. - `EAN_8`: EAN-8 type. - `QR_CODE`: 2D QR code type. - `UPC_A`: UPC-A type. - `UPC_E`: UPC-E type. - `PDF417`: PDF417 type. - `AZTEC`: 2D Aztec code type. - `DATABAR`: GS1 DataBar code type. */
		format: Option[String] = None,
	  /** Raw value encoded in the barcode. For example: `'MEBKM:TITLE:Google;URL:https://www.google.com;;'`. */
		rawValue: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1RunPipelineMetadataExportToCdwPipelineMetadata(
	  /** The output CDW dataset resource name. */
		docAiDataset: Option[String] = None,
	  /** The output Cloud Storage folder in this pipeline. */
		outputPath: Option[String] = None,
	  /** The input list of all the resource names of the documents to be exported. */
		documents: Option[List[String]] = None
	)
	
	case class CloudAiPlatformTenantresourceInfraSpannerConfigCreateDatabaseOptions(
	  /** The service name for the CMEK encryption. For example, contentwarehouse.googleapis.com */
		cmekServiceName: Option[String] = None,
	  /** The cloud resource type for the CMEK encryption. For example, contentwarehouse.googleapis.com/Location */
		cmekCloudResourceType: Option[String] = None,
	  /** The cloud resource name for the CMEK encryption. For example, projects//locations/ */
		cmekCloudResourceName: Option[String] = None
	)
	
	object GoogleCloudContentwarehouseV1FileTypeFilter {
		enum FileTypeEnum extends Enum[FileTypeEnum] { case FILE_TYPE_UNSPECIFIED, ALL, FOLDER, DOCUMENT, ROOT_FOLDER }
	}
	case class GoogleCloudContentwarehouseV1FileTypeFilter(
	  /** The type of files to return. */
		fileType: Option[Schema.GoogleCloudContentwarehouseV1FileTypeFilter.FileTypeEnum] = None
	)
	
	object GoogleCloudContentwarehouseV1ActionOutput {
		enum ActionStateEnum extends Enum[ActionStateEnum] { case UNKNOWN, ACTION_SUCCEEDED, ACTION_FAILED, ACTION_TIMED_OUT, ACTION_PENDING }
	}
	case class GoogleCloudContentwarehouseV1ActionOutput(
	  /** State of an action. */
		actionState: Option[Schema.GoogleCloudContentwarehouseV1ActionOutput.ActionStateEnum] = None,
	  /** Action execution output message. */
		outputMessage: Option[String] = None,
	  /** ID of the action. */
		actionId: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1UpdateDocumentRequest(
	  /** Request Option for processing Cloud AI Document in Document Warehouse. This field offers limited support for mapping entities from Cloud AI Document to Warehouse Document. Please consult with product team before using this field and other available options. */
		cloudAiDocumentOption: Option[Schema.GoogleCloudContentwarehouseV1CloudAIDocumentOption] = None,
	  /** Options for the update operation. */
		updateOptions: Option[Schema.GoogleCloudContentwarehouseV1UpdateOptions] = None,
	  /** The meta information collected about the end user, used to enforce access control for the service. */
		requestMetadata: Option[Schema.GoogleCloudContentwarehouseV1RequestMetadata] = None,
	  /** Required. The document to update. */
		document: Option[Schema.GoogleCloudContentwarehouseV1Document] = None
	)
	
	case class GoogleIamV1Policy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An `etag` is returned in the response to `getIamPolicy`, and systems are expected to put that etag in the request to `setIamPolicy` to ensure that their change will be applied to the same version of the policy. &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. */
		etag: Option[String] = None,
	  /** Specifies cloud audit logging configuration for this policy. */
		auditConfigs: Option[List[Schema.GoogleIamV1AuditConfig]] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.GoogleIamV1Binding]] = None
	)
	
	object GoogleCloudContentwarehouseV1beta1InitializeProjectResponse {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, SUCCEEDED, FAILED, CANCELLED, RUNNING }
	}
	case class GoogleCloudContentwarehouseV1beta1InitializeProjectResponse(
	  /** The state of the project initialization process. */
		state: Option[Schema.GoogleCloudContentwarehouseV1beta1InitializeProjectResponse.StateEnum] = None,
	  /** The message of the project initialization process. */
		message: Option[String] = None
	)
	
	case class GoogleTypeExpr(
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None,
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1MapTypeOptions(
	
	)
	
	case class GoogleCloudContentwarehouseV1RunPipelineMetadata(
	  /** User unique identification and groups information. */
		userInfo: Option[Schema.GoogleCloudContentwarehouseV1UserInfo] = None,
	  /** Number of files that have failed at some point in the pipeline. */
		failedFileCount: Option[Int] = None,
	  /** The pipeline metadata for Process-with-DocAi pipeline. */
		processWithDocAiPipelineMetadata: Option[Schema.GoogleCloudContentwarehouseV1RunPipelineMetadataProcessWithDocAiPipelineMetadata] = None,
	  /** Number of files that were processed by the pipeline. */
		totalFileCount: Option[Int] = None,
	  /** The pipeline metadata for Export-to-CDW pipeline. */
		exportToCdwPipelineMetadata: Option[Schema.GoogleCloudContentwarehouseV1RunPipelineMetadataExportToCdwPipelineMetadata] = None,
	  /** The list of response details of each document. */
		individualDocumentStatuses: Option[List[Schema.GoogleCloudContentwarehouseV1RunPipelineMetadataIndividualDocumentStatus]] = None,
	  /** The pipeline metadata for GcsIngest pipeline. */
		gcsIngestPipelineMetadata: Option[Schema.GoogleCloudContentwarehouseV1RunPipelineMetadataGcsIngestPipelineMetadata] = None
	)
	
	case class GoogleCloudContentwarehouseV1DocumentQuery(
	  /** Experimental, do not use. If the query is a natural language question. False by default. If true, then the question-answering feature will be used instead of search, and `result_count` in SearchDocumentsRequest must be set. In addition, all other input fields related to search (pagination, histograms, etc.) will be ignored. */
		isNlQuery: Option[Boolean] = None,
	  /** Search the documents in the list. Format: projects/{project_number}/locations/{location}/documents/{document_id}. */
		documentNameFilter: Option[List[String]] = None,
	  /** To support the custom weighting across document schemas, customers need to provide the properties to be used to boost the ranking in the search request. For a search query with CustomWeightsMetadata specified, only the RetrievalImportance for the properties in the CustomWeightsMetadata will be honored. */
		customWeightsMetadata: Option[Schema.GoogleCloudContentwarehouseV1CustomWeightsMetadata] = None,
	  /** This filter specifies the exact document schema Document.document_schema_name of the documents to search against. If a value isn't specified, documents within the search results are associated with any schema. If multiple values are specified, documents within the search results may be associated with any of the specified schemas. At most 20 document schema names are allowed. */
		documentSchemaNames: Option[List[String]] = None,
	  /** This filter specifies a structured syntax to match against the [PropertyDefinition].is_filterable marked as `true`. The syntax for this expression is a subset of SQL syntax. Supported operators are: `=`, `!=`, `<`, `<=`, `>`, and `>=` where the left of the operator is a property name and the right of the operator is a number or a quoted string. You must escape backslash (\\) and quote (\") characters. Supported functions are `LOWER([property_name])` to perform a case insensitive match and `EMPTY([property_name])` to filter on the existence of a key. Boolean expressions (AND/OR/NOT) are supported up to 3 levels of nesting (for example, "((A AND B AND C) OR NOT D) AND E"), a maximum of 100 comparisons or functions are allowed in the expression. The expression must be < 6000 bytes in length. Sample Query: `(LOWER(driving_license)="class \"a\"" OR EMPTY(driving_license)) AND driving_years > 10` */
		customPropertyFilter: Option[String] = None,
	  /** Search all the documents under this specified folder. Format: projects/{project_number}/locations/{location}/documents/{document_id}. */
		folderNameFilter: Option[String] = None,
	  /** Documents created/updated within a range specified by this filter are searched against. */
		timeFilters: Option[List[Schema.GoogleCloudContentwarehouseV1TimeFilter]] = None,
	  /** For custom synonyms. Customers provide the synonyms based on context. One customer can provide multiple set of synonyms based on different context. The search query will be expanded based on the custom synonyms of the query context set. By default, no custom synonyms wll be applied if no query context is provided. It is not supported for CMEK compliant deployment. */
		queryContext: Option[List[String]] = None,
	  /** The query string that matches against the full text of the document and the searchable properties. The query partially supports [Google AIP style syntax](https://google.aip.dev/160). Specifically, the query supports literals, logical operators, negation operators, comparison operators, and functions. Literals: A bare literal value (examples: "42", "Hugo") is a value to be matched against. It searches over the full text of the document and the searchable properties. Logical operators: "AND", "and", "OR", and "or" are binary logical operators (example: "engineer OR developer"). Negation operators: "NOT" and "!" are negation operators (example: "NOT software"). Comparison operators: support the binary comparison operators =, !=, <, >, <= and >= for string, numeric, enum, boolean. Also support like operator `~~` for string. It provides semantic search functionality by parsing, stemming and doing synonyms expansion against the input query. To specify a property in the query, the left hand side expression in the comparison must be the property ID including the parent. The right hand side must be literals. For example: "\"projects/123/locations/us\".property_a < 1" matches results whose "property_a" is less than 1 in project 123 and us location. The literals and comparison expression can be connected in a single query (example: "software engineer \"projects/123/locations/us\".salary > 100"). Functions: supported functions are `LOWER([property_name])` to perform a case insensitive match and `EMPTY([property_name])` to filter on the existence of a key. Support nested expressions connected using parenthesis and logical operators. The default logical operators is `AND` if there is no operators between expressions. The query can be used with other filters e.g. `time_filters` and `folder_name_filter`. They are connected with `AND` operator under the hood. The maximum number of allowed characters is 255. */
		query: Option[String] = None,
	  /** This filter specifies a structured syntax to match against the PropertyDefinition.is_filterable marked as `true`. The relationship between the PropertyFilters is OR. */
		propertyFilter: Option[List[Schema.GoogleCloudContentwarehouseV1PropertyFilter]] = None,
	  /** This filter specifies the types of files to return: ALL, FOLDER, or FILE. If FOLDER or FILE is specified, then only either folders or files will be returned, respectively. If ALL is specified, both folders and files will be returned. If no value is specified, ALL files will be returned. */
		fileTypeFilter: Option[Schema.GoogleCloudContentwarehouseV1FileTypeFilter] = None,
	  /** The exact creator(s) of the documents to search against. If a value isn't specified, documents within the search results are associated with any creator. If multiple values are specified, documents within the search results may be associated with any of the specified creators. */
		documentCreatorFilter: Option[List[String]] = None
	)
	
	case class GoogleCloudContentwarehouseV1TimestampValue(
	  /** The string must represent a valid instant in UTC and is parsed using java.time.format.DateTimeFormatter.ISO_INSTANT. e.g. "2013-09-29T18:46:19Z" */
		textValue: Option[String] = None,
	  /** Timestamp value */
		timestampValue: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageLine(
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None,
	  /** The history of this annotation. */
		provenance: Option[Schema.GoogleCloudDocumentaiV1DocumentProvenance] = None,
	  /** Layout for Line. */
		layout: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None
	)
	
	case class GoogleCloudContentwarehouseV1DeleteDocumentLinkRequest(
	  /** The meta information collected about the document creator, used to enforce access control for the service. */
		requestMetadata: Option[Schema.GoogleCloudContentwarehouseV1RequestMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutTableCell(
	  /** A table cell is a list of blocks. Repeated blocks support further hierarchies and nested blocks. */
		blocks: Option[List[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlock]] = None,
	  /** How many columns this cell spans. */
		colSpan: Option[Int] = None,
	  /** How many rows this cell spans. */
		rowSpan: Option[Int] = None
	)
	
	object GoogleIamV1AuditLogConfig {
		enum LogTypeEnum extends Enum[LogTypeEnum] { case LOG_TYPE_UNSPECIFIED, ADMIN_READ, DATA_WRITE, DATA_READ }
	}
	case class GoogleIamV1AuditLogConfig(
	  /** Specifies the identities that do not cause logging for this type of permission. Follows the same format of Binding.members. */
		exemptedMembers: Option[List[String]] = None,
	  /** The log type that this config enables. */
		logType: Option[Schema.GoogleIamV1AuditLogConfig.LogTypeEnum] = None
	)
	
	case class GoogleCloudContentwarehouseV1beta1CreateDocumentMetadata(
	
	)
	
	case class GoogleCloudContentwarehouseV1Value(
	  /** Represents a timestamp value. */
		timestampValue: Option[Schema.GoogleCloudContentwarehouseV1TimestampValue] = None,
	  /** Represents a datetime value. */
		datetimeValue: Option[Schema.GoogleTypeDateTime] = None,
	  /** Represents a boolean value. */
		booleanValue: Option[Boolean] = None,
	  /** Represents an enum value. */
		enumValue: Option[Schema.GoogleCloudContentwarehouseV1EnumValue] = None,
	  /** Represents a integer value. */
		intValue: Option[Int] = None,
	  /** Represents a float value. */
		floatValue: Option[BigDecimal] = None,
	  /** Represents a string value. */
		stringValue: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1DataUpdateAction(
	  /** Map of (K, V) -> (valid name of the field, new value of the field) E.g., ("age", "60") entry triggers update of field age with a value of 60. If the field is not present then new entry is added. During update action execution, value strings will be casted to appropriate types. */
		entries: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudContentwarehouseV1ListLinkedSourcesResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Source document-links. */
		documentLinks: Option[List[Schema.GoogleCloudContentwarehouseV1DocumentLink]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageMatrix(
	  /** Number of columns in the matrix. */
		cols: Option[Int] = None,
	  /** The matrix data. */
		data: Option[String] = None,
	  /** This encodes information about what data type the matrix uses. For example, 0 (CV_8U) is an unsigned 8-bit image. For the full list of OpenCV primitive data types, please refer to https://docs.opencv.org/4.3.0/d1/d1b/group__core__hal__interface.html */
		`type`: Option[Int] = None,
	  /** Number of rows in the matrix. */
		rows: Option[Int] = None
	)
	
	object GoogleCloudDocumentaiV1DocumentPageTokenDetectedBreak {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, SPACE, WIDE_SPACE, HYPHEN }
	}
	case class GoogleCloudDocumentaiV1DocumentPageTokenDetectedBreak(
	  /** Detected break type. */
		`type`: Option[Schema.GoogleCloudDocumentaiV1DocumentPageTokenDetectedBreak.TypeEnum] = None
	)
	
	case class GoogleCloudContentwarehouseV1QAResult(
	  /** The calibrated confidence score for this document, in the range [0., 1.]. This represents the confidence level for whether the returned document and snippet answers the user's query. */
		confidenceScore: Option[BigDecimal] = None,
	  /** Highlighted sections in the snippet. */
		highlights: Option[List[Schema.GoogleCloudContentwarehouseV1QAResultHighlight]] = None
	)
	
	case class GoogleCloudContentwarehouseV1ListLinkedSourcesRequest(
	  /** A page token, received from a previous `ListLinkedSources` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListLinkedSources` must match the call that provided the page token. */
		pageToken: Option[String] = None,
	  /** The maximum number of document-links to return. The service may return fewer than this value. If unspecified, at most 50 document-links will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000. */
		pageSize: Option[Int] = None,
	  /** The meta information collected about the document creator, used to enforce access control for the service. */
		requestMetadata: Option[Schema.GoogleCloudContentwarehouseV1RequestMetadata] = None
	)
	
	case class GoogleCloudContentwarehouseV1Action(
	  /** Action removing a document from a folder. */
		removeFromFolderAction: Option[Schema.GoogleCloudContentwarehouseV1RemoveFromFolderAction] = None,
	  /** Action triggering data update operations. */
		dataUpdate: Option[Schema.GoogleCloudContentwarehouseV1DataUpdateAction] = None,
	  /** Action triggering access control operations. */
		accessControl: Option[Schema.GoogleCloudContentwarehouseV1AccessControlAction] = None,
	  /** Action triggering create document link operation. */
		addToFolder: Option[Schema.GoogleCloudContentwarehouseV1AddToFolderAction] = None,
	  /** ID of the action. Managed internally. */
		actionId: Option[String] = None,
	  /** Action triggering data validation operations. */
		dataValidation: Option[Schema.GoogleCloudContentwarehouseV1DataValidationAction] = None,
	  /** Action deleting the document. */
		deleteDocumentAction: Option[Schema.GoogleCloudContentwarehouseV1DeleteDocumentAction] = None,
	  /** Action publish to Pub/Sub operation. */
		publishToPubSub: Option[Schema.GoogleCloudContentwarehouseV1PublishAction] = None
	)
	
	object GoogleCloudContentwarehouseV1InitializeProjectResponse {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, SUCCEEDED, FAILED, CANCELLED, RUNNING }
	}
	case class GoogleCloudContentwarehouseV1InitializeProjectResponse(
	  /** The message of the project initialization process. */
		message: Option[String] = None,
	  /** The state of the project initialization process. */
		state: Option[Schema.GoogleCloudContentwarehouseV1InitializeProjectResponse.StateEnum] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutTableBlock(
	  /** Header rows at the top of the table. */
		headerRows: Option[List[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutTableRow]] = None,
	  /** Table caption/title. */
		caption: Option[String] = None,
	  /** Body rows containing main table content. */
		bodyRows: Option[List[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutTableRow]] = None
	)
	
	case class CloudAiPlatformTenantresourceTenantProjectConfig(
	  /** Input/Output [Required]. The billing account properties to create the tenant project. */
		billingConfig: Option[Schema.GoogleApiServiceconsumermanagementV1BillingConfig] = None,
	  /** Input/Output [Required]. The API services that are enabled on the tenant project during creation. */
		services: Option[List[String]] = None,
	  /** Input/Output [Required]. The folder that holds tenant projects and folder-level permissions will be automatically granted to all tenant projects under the folder. Note: the valid folder format is `folders/{folder_number}`. */
		folder: Option[String] = None,
	  /** Input/Output [Required]. The policy bindings that are applied to the tenant project during creation. At least one binding must have the role `roles/owner` with either `user` or `group` type. */
		policyBindings: Option[List[Schema.GoogleApiServiceconsumermanagementV1PolicyBinding]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentTextAnchor(
	  /** The text segments from the Document.text. */
		textSegments: Option[List[Schema.GoogleCloudDocumentaiV1DocumentTextAnchorTextSegment]] = None,
	  /** Contains the content of the text span so that users do not have to look it up in the text_segments. It is always populated for formFields. */
		content: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1DocumentSchema(
	  /** Schema description. */
		description: Option[String] = None,
	  /** The resource name of the document schema. Format: projects/{project_number}/locations/{location}/documentSchemas/{document_schema_id}. The name is ignored when creating a document schema. */
		name: Option[String] = None,
	  /** Document details. */
		propertyDefinitions: Option[List[Schema.GoogleCloudContentwarehouseV1PropertyDefinition]] = None,
	  /** Required. Name of the schema given by the user. Must be unique per project. */
		displayName: Option[String] = None,
	  /** Output only. The time when the document schema is last updated. */
		updateTime: Option[String] = None,
	  /** Document Type, true refers the document is a folder, otherwise it is a typical document. */
		documentIsFolder: Option[Boolean] = None,
	  /** Output only. The time when the document schema is created. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutListBlock(
	  /** List entries that constitute a list block. */
		listEntries: Option[List[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutListEntry]] = None,
	  /** Type of the list_entries (if exist). Available options are `ordered` and `unordered`. */
		`type`: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1HistogramQuery(
	  /** An expression specifies a histogram request against matching documents for searches. See SearchDocumentsRequest.histogram_queries for details about syntax. */
		histogramQuery: Option[String] = None,
	  /** Optional. Filter the result of histogram query by the property names. It only works with histogram query count('FilterableProperties'). It is an optional. It will perform histogram on all the property names for all the document schemas. Setting this field will have a better performance. */
		filters: Option[Schema.GoogleCloudContentwarehouseV1HistogramQueryPropertyNameFilter] = None,
	  /** Controls if the histogram query requires the return of a precise count. Enable this flag may adversely impact performance. Defaults to true. */
		requirePreciseResultSize: Option[Boolean] = None
	)
	
	case class GoogleCloudContentwarehouseV1DataValidationAction(
	  /** Map of (K, V) -> (field, string condition to be evaluated on the field) E.g., ("age", "age > 18 && age < 60") entry triggers validation of field age with the given condition. Map entries will be ANDed during validation. */
		conditions: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentShardInfo(
	  /** Total number of shards. */
		shardCount: Option[String] = None,
	  /** The 0-based index of this shard. */
		shardIndex: Option[String] = None,
	  /** The index of the first character in Document.text in the overall document global text. */
		textOffset: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1InvalidRule(
	  /** Triggered rule. */
		rule: Option[Schema.GoogleCloudContentwarehouseV1Rule] = None,
	  /** Validation error on a parsed expression. */
		error: Option[String] = None
	)
	
	object GoogleCloudContentwarehouseV1TimeFilter {
		enum TimeFieldEnum extends Enum[TimeFieldEnum] { case TIME_FIELD_UNSPECIFIED, CREATE_TIME, UPDATE_TIME, DISPOSITION_TIME }
	}
	case class GoogleCloudContentwarehouseV1TimeFilter(
	  /** Specifies which time field to filter documents on. Defaults to TimeField.UPLOAD_TIME. */
		timeField: Option[Schema.GoogleCloudContentwarehouseV1TimeFilter.TimeFieldEnum] = None,
		timeRange: Option[Schema.GoogleTypeInterval] = None
	)
	
	case class GoogleLongrunningOperation(
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutTextBlock(
	  /** A text block could further have child blocks. Repeated blocks support further hierarchies and nested blocks. */
		blocks: Option[List[Schema.GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlock]] = None,
	  /** Type of the text in the block. Available options are: `paragraph`, `subtitle`, `heading-1`, `heading-2`, `heading-3`, `heading-4`, `heading-5`, `header`, `footer`. */
		`type`: Option[String] = None,
	  /** Text content stored in the block. */
		text: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1SearchDocumentsResponse(
	  /** The total number of matched documents which is available only if the client set SearchDocumentsRequest.require_total_size to `true` or set SearchDocumentsRequest.total_result_size to `ESTIMATED_SIZE` or `ACTUAL_SIZE`. Otherwise, the value will be `-1`. Typically a UI would handle this condition by displaying "of many", for example: "Displaying 10 of many". */
		totalSize: Option[Int] = None,
	  /** The document entities that match the specified SearchDocumentsRequest. */
		matchingDocuments: Option[List[Schema.GoogleCloudContentwarehouseV1SearchDocumentsResponseMatchingDocument]] = None,
	  /** The histogram results that match with the specified SearchDocumentsRequest.histogram_queries. */
		histogramQueryResults: Option[List[Schema.GoogleCloudContentwarehouseV1HistogramQueryResult]] = None,
	  /** The token that specifies the starting position of the next page of results. This field is empty if there are no more results. */
		nextPageToken: Option[String] = None,
	  /** Additional information for the API invocation, such as the request tracking id. */
		metadata: Option[Schema.GoogleCloudContentwarehouseV1ResponseMetadata] = None,
	  /** Experimental. Question answer from the query against the document. */
		questionAnswer: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1SynonymSetSynonym(
	  /** For example: sale, invoice, bill, order */
		words: Option[List[String]] = None
	)
	
	case class GoogleCloudContentwarehouseV1DateTimeArray(
	  /** List of datetime values. Both OffsetDateTime and ZonedDateTime are supported. */
		values: Option[List[Schema.GoogleTypeDateTime]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageImage(
	  /** Width of the image in pixels. */
		width: Option[Int] = None,
	  /** Height of the image in pixels. */
		height: Option[Int] = None,
	  /** Encoding [media type (MIME type)](https://www.iana.org/assignments/media-types/media-types.xhtml) for the image. */
		mimeType: Option[String] = None,
	  /** Raw byte content of the image. */
		content: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1ListDocumentSchemasResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The document schemas from the specified parent. */
		documentSchemas: Option[List[Schema.GoogleCloudContentwarehouseV1DocumentSchema]] = None
	)
	
	case class GoogleCloudContentwarehouseV1FloatTypeOptions(
	
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageAnchor(
	  /** One or more references to visual page elements */
		pageRefs: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageAnchorPageRef]] = None
	)
	
	case class GoogleCloudContentwarehouseV1beta1UpdateDocumentMetadata(
	
	)
	
	case class GoogleCloudContentwarehouseV1RunPipelineMetadataGcsIngestPipelineMetadata(
	  /** The input Cloud Storage folder in this pipeline. Format: `gs:///`. */
		inputPath: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1HistogramQueryResult(
	  /** A map from the values of the facet associated with distinct values to the number of matching entries with corresponding value. The key format is: &#42; (for string histogram) string values stored in the field. */
		histogram: Option[Map[String, String]] = None,
	  /** Requested histogram expression. */
		histogramQuery: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1Vertex(
	  /** Y coordinate (starts from the top of the image). */
		y: Option[Int] = None,
	  /** X coordinate. */
		x: Option[Int] = None
	)
	
	object GoogleCloudContentwarehouseV1HistogramQueryPropertyNameFilter {
		enum YAxisEnum extends Enum[YAxisEnum] { case HISTOGRAM_YAXIS_DOCUMENT, HISTOGRAM_YAXIS_PROPERTY }
	}
	case class GoogleCloudContentwarehouseV1HistogramQueryPropertyNameFilter(
	  /** By default, the y_axis is HISTOGRAM_YAXIS_DOCUMENT if this field is not set. */
		yAxis: Option[Schema.GoogleCloudContentwarehouseV1HistogramQueryPropertyNameFilter.YAxisEnum] = None,
	  /** This filter specifies the exact document schema(s) Document.document_schema_name to run histogram query against. It is optional. It will perform histogram for property names for all the document schemas if it is not set. At most 10 document schema names are allowed. Format: projects/{project_number}/locations/{location}/documentSchemas/{document_schema_id}. */
		documentSchemas: Option[List[String]] = None,
	  /** It is optional. It will perform histogram for all the property names if it is not set. The properties need to be defined with the is_filterable flag set to true and the name of the property should be in the format: "schemaId.propertyName". The property needs to be defined in the schema. Example: the schema id is abc. Then the name of property for property MORTGAGE_TYPE will be "abc.MORTGAGE_TYPE". */
		propertyNames: Option[List[String]] = None
	)
	
	case class GoogleCloudContentwarehouseV1RunPipelineMetadataIndividualDocumentStatus(
	  /** Document identifier of an existing document. */
		documentId: Option[String] = None,
	  /** The status processing the document. */
		status: Option[Schema.GoogleRpcStatus] = None
	)
	
	case class CloudAiPlatformTenantresourceTenantServiceAccountIdentity(
	  /** Output only. The email address of the generated service account. */
		serviceAccountEmail: Option[String] = None,
	  /** Input/Output [Required]. The service that the service account belongs to. (e.g. cloudbuild.googleapis.com for GCB service accounts) */
		serviceName: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1FetchAclResponse(
	  /** The IAM policy. */
		policy: Option[Schema.GoogleIamV1Policy] = None,
	  /** Additional information for the API invocation, such as the request tracking id. */
		metadata: Option[Schema.GoogleCloudContentwarehouseV1ResponseMetadata] = None
	)
	
	case class GoogleCloudContentwarehouseV1FloatArray(
	  /** List of float values. */
		values: Option[List[BigDecimal]] = None
	)
	
	case class GoogleCloudContentwarehouseV1AddToFolderAction(
	  /** Names of the folder under which new document is to be added. Format: projects/{project_number}/locations/{location}/documents/{document_id}. */
		folders: Option[List[String]] = None
	)
	
	case class GoogleCloudContentwarehouseV1ProcessorInfo(
	  /** The processor resource name. Format is `projects/{project}/locations/{location}/processors/{processor}`, or `projects/{project}/locations/{location}/processors/{processor}/processorVersions/{processorVersion}` */
		processorName: Option[String] = None,
	  /** The Document schema resource name. All documents processed by this processor will use this schema. Format: projects/{project_number}/locations/{location}/documentSchemas/{document_schema_id}. */
		schemaName: Option[String] = None,
	  /** The processor will process the documents with this document type. */
		documentType: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1Property(
	  /** Required. Must match the name of a PropertyDefinition in the DocumentSchema. */
		name: Option[String] = None,
	  /** Map property values. */
		mapProperty: Option[Schema.GoogleCloudContentwarehouseV1MapProperty] = None,
	  /** String/text property values. */
		textValues: Option[Schema.GoogleCloudContentwarehouseV1TextArray] = None,
	  /** Nested structured data property values. */
		propertyValues: Option[Schema.GoogleCloudContentwarehouseV1PropertyArray] = None,
	  /** Float property values. */
		floatValues: Option[Schema.GoogleCloudContentwarehouseV1FloatArray] = None,
	  /** Integer property values. */
		integerValues: Option[Schema.GoogleCloudContentwarehouseV1IntegerArray] = None,
	  /** Enum property values. */
		enumValues: Option[Schema.GoogleCloudContentwarehouseV1EnumArray] = None,
	  /** Timestamp property values. It is not supported by CMEK compliant deployment. */
		timestampValues: Option[Schema.GoogleCloudContentwarehouseV1TimestampArray] = None,
	  /** Date time property values. It is not supported by CMEK compliant deployment. */
		dateTimeValues: Option[Schema.GoogleCloudContentwarehouseV1DateTimeArray] = None
	)
	
	case class GoogleCloudContentwarehouseV1PropertyFilter(
	  /** The filter condition. The syntax for this expression is a subset of SQL syntax. Supported operators are: `=`, `!=`, `<`, `<=`, `>`, `>=`, and `~~` where the left of the operator is a property name and the right of the operator is a number or a quoted string. You must escape backslash (\\) and quote (\") characters. `~~` is the LIKE operator. The right of the operator must be a string. The only supported property data type for LIKE is text_values. It provides semantic search functionality by parsing, stemming and doing synonyms expansion against the input query. It matches if the property contains semantic similar content to the query. It is not regex matching or wildcard matching. For example, "property.company ~~ \"google\"" will match records whose property `property.compnay` have values like "Google Inc.", "Google LLC" or "Google Company". Supported functions are `LOWER([property_name])` to perform a case insensitive match and `EMPTY([property_name])` to filter on the existence of a key. Boolean expressions (AND/OR/NOT) are supported up to 3 levels of nesting (for example, "((A AND B AND C) OR NOT D) AND E"), a maximum of 100 comparisons or functions are allowed in the expression. The expression must be < 6000 bytes in length. Only properties that are marked filterable are allowed (PropertyDefinition.is_filterable). Property names do not need to be prefixed by the document schema id (as is the case with histograms), however property names will need to be prefixed by its parent hierarchy, if any. For example: top_property_name.sub_property_name. Sample Query: `(LOWER(driving_license)="class \"a\"" OR EMPTY(driving_license)) AND driving_years > 10` CMEK compliant deployment only supports: &#42; Operators: `=`, `<`, `<=`, `>`, and `>=`. &#42; Boolean expressions: AND and OR. */
		condition: Option[String] = None,
	  /** The Document schema name Document.document_schema_name. Format: projects/{project_number}/locations/{location}/documentSchemas/{document_schema_id}. */
		documentSchemaName: Option[String] = None
	)
	
	case class GoogleTypePostalAddress(
	  /** Optional. The recipient at the address. This field may, under certain circumstances, contain multiline information. For example, it might contain "care of" information. */
		recipients: Option[List[String]] = None,
	  /** Required. CLDR region code of the country/region of the address. This is never inferred and it is up to the user to ensure the value is correct. See https://cldr.unicode.org/ and https://www.unicode.org/cldr/charts/30/supplemental/territory_information.html for details. Example: "CH" for Switzerland. */
		regionCode: Option[String] = None,
	  /** Optional. The name of the organization at the address. */
		organization: Option[String] = None,
	  /** Optional. Additional, country-specific, sorting code. This is not used in most regions. Where it is used, the value is either a string like "CEDEX", optionally followed by a number (e.g. "CEDEX 7"), or just a number alone, representing the "sector code" (Jamaica), "delivery area indicator" (Malawi) or "post office indicator" (e.g. Côte d'Ivoire). */
		sortingCode: Option[String] = None,
	  /** Optional. BCP-47 language code of the contents of this address (if known). This is often the UI language of the input form or is expected to match one of the languages used in the address' country/region, or their transliterated equivalents. This can affect formatting in certain countries, but is not critical to the correctness of the data and will never affect any validation or other non-formatting related operations. If this value is not known, it should be omitted (rather than specifying a possibly incorrect default). Examples: "zh-Hant", "ja", "ja-Latn", "en". */
		languageCode: Option[String] = None,
	  /** Unstructured address lines describing the lower levels of an address. Because values in address_lines do not have type information and may sometimes contain multiple values in a single field (e.g. "Austin, TX"), it is important that the line order is clear. The order of address lines should be "envelope order" for the country/region of the address. In places where this can vary (e.g. Japan), address_language is used to make it explicit (e.g. "ja" for large-to-small ordering and "ja-Latn" or "en" for small-to-large). This way, the most specific line of an address can be selected based on the language. The minimum permitted structural representation of an address consists of a region_code with all remaining information placed in the address_lines. It would be possible to format such an address very approximately without geocoding, but no semantic reasoning could be made about any of the address components until it was at least partially resolved. Creating an address only containing a region_code and address_lines, and then geocoding is the recommended way to handle completely unstructured addresses (as opposed to guessing which parts of the address should be localities or administrative areas). */
		addressLines: Option[List[String]] = None,
	  /** Optional. Generally refers to the city/town portion of the address. Examples: US city, IT comune, UK post town. In regions of the world where localities are not well defined or do not fit into this structure well, leave locality empty and use address_lines. */
		locality: Option[String] = None,
	  /** The schema revision of the `PostalAddress`. This must be set to 0, which is the latest revision. All new revisions &#42;&#42;must&#42;&#42; be backward compatible with old revisions. */
		revision: Option[Int] = None,
	  /** Optional. Highest administrative subdivision which is used for postal addresses of a country or region. For example, this can be a state, a province, an oblast, or a prefecture. Specifically, for Spain this is the province and not the autonomous community (e.g. "Barcelona" and not "Catalonia"). Many countries don't use an administrative area in postal addresses. E.g. in Switzerland this should be left unpopulated. */
		administrativeArea: Option[String] = None,
	  /** Optional. Sublocality of the address. For example, this can be neighborhoods, boroughs, districts. */
		sublocality: Option[String] = None,
	  /** Optional. Postal code of the address. Not all countries use or require postal codes to be present, but where they are used, they may trigger additional validation with other parts of the address (e.g. state/zip validation in the U.S.A.). */
		postalCode: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1IngestPipelineConfig(
	  /** The Cloud Function resource name. The Cloud Function needs to live inside consumer project and is accessible to Document AI Warehouse P4SA. Only Cloud Functions V2 is supported. Cloud function execution should complete within 5 minutes or this file ingestion may fail due to timeout. Format: `https://{region}-{project_id}.cloudfunctions.net/{cloud_function}` The following keys are available the request json payload. &#42; display_name &#42; properties &#42; plain_text &#42; reference_id &#42; document_schema_name &#42; raw_document_path &#42; raw_document_file_type The following keys from the cloud function json response payload will be ingested to the Document AI Warehouse as part of Document proto content and/or related information. The original values will be overridden if any key is present in the response. &#42; display_name &#42; properties &#42; plain_text &#42; document_acl_policy &#42; folder */
		cloudFunction: Option[String] = None,
	  /** The document text extraction enabled flag. If the flag is set to true, DWH will perform text extraction on the raw document. */
		enableDocumentTextExtraction: Option[Boolean] = None,
	  /** The document level acl policy config. This refers to an Identity and Access (IAM) policy, which specifies access controls for all documents ingested by the pipeline. The role and members under the policy needs to be specified. The following roles are supported for document level acl control: &#42; roles/contentwarehouse.documentAdmin &#42; roles/contentwarehouse.documentEditor &#42; roles/contentwarehouse.documentViewer The following members are supported for document level acl control: &#42; user:user-email@example.com &#42; group:group-email@example.com Note that for documents searched with LLM, only single level user or group acl check is supported. */
		documentAclPolicy: Option[Schema.GoogleIamV1Policy] = None,
	  /** Optional. The name of the folder to which all ingested documents will be linked during ingestion process. Format is `projects/{project}/locations/{location}/documents/{folder_id}` */
		folder: Option[String] = None
	)
	
	object GoogleCloudContentwarehouseV1UpdateOptions {
		enum UpdateTypeEnum extends Enum[UpdateTypeEnum] { case UPDATE_TYPE_UNSPECIFIED, UPDATE_TYPE_REPLACE, UPDATE_TYPE_MERGE, UPDATE_TYPE_INSERT_PROPERTIES_BY_NAMES, UPDATE_TYPE_REPLACE_PROPERTIES_BY_NAMES, UPDATE_TYPE_DELETE_PROPERTIES_BY_NAMES, UPDATE_TYPE_MERGE_AND_REPLACE_OR_INSERT_PROPERTIES_BY_NAMES }
	}
	case class GoogleCloudContentwarehouseV1UpdateOptions(
	  /** Type for update. */
		updateType: Option[Schema.GoogleCloudContentwarehouseV1UpdateOptions.UpdateTypeEnum] = None,
	  /** Options for merging. */
		mergeFieldsOptions: Option[Schema.GoogleCloudContentwarehouseV1MergeFieldsOptions] = None,
	  /** Field mask for merging Document fields. For the `FieldMask` definition, see https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#fieldmask */
		updateMask: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1LockDocumentRequest(
	  /** The user information who locks the document. */
		lockingUser: Option[Schema.GoogleCloudContentwarehouseV1UserInfo] = None,
	  /** The collection the document connects to. */
		collectionId: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1ListSynonymSetsResponse(
	  /** The synonymSets from the specified parent. */
		synonymSets: Option[List[Schema.GoogleCloudContentwarehouseV1SynonymSet]] = None,
	  /** A page token, received from a previous `ListSynonymSets` call. Provide this to retrieve the subsequent page. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageVisualElement(
	  /** Type of the VisualElement. */
		`type`: Option[String] = None,
	  /** Layout for VisualElement. */
		layout: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None,
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None
	)
	
	case class GoogleCloudContentwarehouseV1ExportToCdwPipeline(
	  /** Ratio of training dataset split. When importing into Document AI Workbench, documents will be automatically split into training and test split category with the specified ratio. This field is required if doc_ai_dataset is set. */
		trainingSplitRatio: Option[BigDecimal] = None,
	  /** Optional. The CDW dataset resource name. This field is optional. If not set, the documents will be exported to Cloud Storage only. Format: projects/{project}/locations/{location}/processors/{processor}/dataset */
		docAiDataset: Option[String] = None,
	  /** The Cloud Storage folder path used to store the exported documents before being sent to CDW. Format: `gs:///`. */
		exportFolderPath: Option[String] = None,
	  /** The list of all the resource names of the documents to be processed. Format: projects/{project_number}/locations/{location}/documents/{document_id}. */
		documents: Option[List[String]] = None
	)
	
	case class CloudAiPlatformTenantresourceTenantProjectResource(
	  /** The configurations of a tenant project. */
		tenantProjectConfig: Option[Schema.CloudAiPlatformTenantresourceTenantProjectConfig] = None,
	  /** Input/Output [Required]. The tag that uniquely identifies a tenant project within a tenancy unit. Note: for the same tenant project tag, all tenant manager operations should be idempotent. */
		tag: Option[String] = None,
	  /** The CloudSQL instances that are provisioned under the tenant project. */
		cloudSqlInstances: Option[List[Schema.CloudAiPlatformTenantresourceCloudSqlInstanceConfig]] = None,
	  /** The service account identities (or enabled API service's P4SA) that are expclicitly created under the tenant project (before JIT provisioning during enabled API services). */
		tenantServiceAccounts: Option[List[Schema.CloudAiPlatformTenantresourceTenantServiceAccountIdentity]] = None,
	  /** The dynamic IAM bindings that are granted under the tenant project. Note: this should only add new bindings to the project if they don't exist and the existing bindings won't be affected. */
		iamPolicyBindings: Option[List[Schema.CloudAiPlatformTenantresourceIamPolicyBinding]] = None,
	  /** Output only. The tenant project ID that has been created. */
		tenantProjectId: Option[String] = None,
	  /** The Infra Spanner databases that are provisioned under the tenant project. Note: this is an experimental feature. */
		infraSpannerConfigs: Option[List[Schema.CloudAiPlatformTenantresourceInfraSpannerConfig]] = None,
	  /** The GCS buckets that are provisioned under the tenant project. */
		gcsBuckets: Option[List[Schema.CloudAiPlatformTenantresourceGcsBucketConfig]] = None,
	  /** Output only. The tenant project number that has been created. */
		tenantProjectNumber: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1WeightedSchemaProperty(
	  /** The property definition names in the schema. */
		propertyNames: Option[List[String]] = None,
	  /** The document schema name. */
		documentSchemaName: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1SearchDocumentsResponseMatchingDocument(
	  /** Return the 1-based page indices where those pages have one or more matched tokens. */
		matchedTokenPageIndices: Option[List[String]] = None,
	  /** Experimental. Additional result info if the question-answering feature is enabled. */
		qaResult: Option[Schema.GoogleCloudContentwarehouseV1QAResult] = None,
	  /** Contains snippets of text from the document full raw text that most closely match a search query's keywords, if available. All HTML tags in the original fields are stripped when returned in this field, and matching query keywords are enclosed in HTML bold tags. If the question-answering feature is enabled, this field will instead contain a snippet that answers the user's natural-language query. No HTML bold tags will be present, and highlights in the answer snippet can be found in QAResult.highlights. */
		searchTextSnippet: Option[String] = None,
	  /** Document that matches the specified SearchDocumentsRequest. This document only contains indexed metadata information. */
		document: Option[Schema.GoogleCloudContentwarehouseV1Document] = None
	)
	
	object GoogleCloudContentwarehouseV1PropertyDefinition {
		enum RetrievalImportanceEnum extends Enum[RetrievalImportanceEnum] { case RETRIEVAL_IMPORTANCE_UNSPECIFIED, HIGHEST, HIGHER, HIGH, MEDIUM, LOW, LOWEST }
	}
	case class GoogleCloudContentwarehouseV1PropertyDefinition(
	  /** Indicates that the property should be included in a global search. */
		isSearchable: Option[Boolean] = None,
	  /** Map property. */
		mapTypeOptions: Option[Schema.GoogleCloudContentwarehouseV1MapTypeOptions] = None,
	  /** The mapping information between this property to another schema source. */
		schemaSources: Option[List[Schema.GoogleCloudContentwarehouseV1PropertyDefinitionSchemaSource]] = None,
	  /** The display-name for the property, used for front-end. */
		displayName: Option[String] = None,
	  /** Text/string property. */
		textTypeOptions: Option[Schema.GoogleCloudContentwarehouseV1TextTypeOptions] = None,
	  /** Nested structured data property. */
		propertyTypeOptions: Option[Schema.GoogleCloudContentwarehouseV1PropertyTypeOptions] = None,
	  /** Whether the property can have multiple values. */
		isRepeatable: Option[Boolean] = None,
	  /** Integer property. */
		integerTypeOptions: Option[Schema.GoogleCloudContentwarehouseV1IntegerTypeOptions] = None,
	  /** Timestamp property. It is not supported by CMEK compliant deployment. */
		timestampTypeOptions: Option[Schema.GoogleCloudContentwarehouseV1TimestampTypeOptions] = None,
	  /** The retrieval importance of the property during search. */
		retrievalImportance: Option[Schema.GoogleCloudContentwarehouseV1PropertyDefinition.RetrievalImportanceEnum] = None,
	  /** Enum/categorical property. */
		enumTypeOptions: Option[Schema.GoogleCloudContentwarehouseV1EnumTypeOptions] = None,
	  /** Date time property. It is not supported by CMEK compliant deployment. */
		dateTimeTypeOptions: Option[Schema.GoogleCloudContentwarehouseV1DateTimeTypeOptions] = None,
	  /** Whether the property can be filtered. If this is a sub-property, all the parent properties must be marked filterable. */
		isFilterable: Option[Boolean] = None,
	  /** Required. The name of the metadata property. Must be unique within a document schema and is case insensitive. Names must be non-blank, start with a letter, and can contain alphanumeric characters and: /, :, -, _, and . */
		name: Option[String] = None,
	  /** Float property. */
		floatTypeOptions: Option[Schema.GoogleCloudContentwarehouseV1FloatTypeOptions] = None,
	  /** Whether the property is mandatory. Default is 'false', i.e. populating property value can be skipped. If 'true' then user must populate the value for this property. */
		isRequired: Option[Boolean] = None,
	  /** Whether the property is user supplied metadata. This out-of-the box placeholder setting can be used to tag derived properties. Its value and interpretation logic should be implemented by API user. */
		isMetadata: Option[Boolean] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentDocumentLayoutDocumentLayoutBlockLayoutPageSpan(
	  /** Page where block starts in the document. */
		pageStart: Option[Int] = None,
	  /** Page where block ends in the document. */
		pageEnd: Option[Int] = None
	)
	
	case class GoogleCloudDocumentaiV1BoundingPoly(
	  /** The bounding polygon normalized vertices. */
		normalizedVertices: Option[List[Schema.GoogleCloudDocumentaiV1NormalizedVertex]] = None,
	  /** The bounding polygon vertices. */
		vertices: Option[List[Schema.GoogleCloudDocumentaiV1Vertex]] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentEntityRelation(
	  /** Relationship description. */
		relation: Option[String] = None,
	  /** Subject entity id. */
		subjectId: Option[String] = None,
	  /** Object entity id. */
		objectId: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageParagraph(
	  /** Layout for Paragraph. */
		layout: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None,
	  /** A list of detected languages together with confidence. */
		detectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None,
	  /** The history of this annotation. */
		provenance: Option[Schema.GoogleCloudDocumentaiV1DocumentProvenance] = None
	)
	
	case class GoogleCloudContentwarehouseV1SetAclResponse(
	  /** The policy will be attached to a resource (e.g. projecct, document). */
		policy: Option[Schema.GoogleIamV1Policy] = None,
	  /** Additional information for the API invocation, such as the request tracking id. */
		metadata: Option[Schema.GoogleCloudContentwarehouseV1ResponseMetadata] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentChunkedDocumentChunkChunkPageSpan(
	  /** Page where chunk ends in the document. */
		pageEnd: Option[Int] = None,
	  /** Page where chunk starts in the document. */
		pageStart: Option[Int] = None
	)
	
	case class GoogleCloudContentwarehouseV1TimestampTypeOptions(
	
	)
	
	case class GoogleCloudDocumentaiV1DocumentTextAnchorTextSegment(
	  /** TextSegment half open end UTF-8 char index in the Document.text. */
		endIndex: Option[String] = None,
	  /** TextSegment start UTF-8 char index in the Document.text. */
		startIndex: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1DocumentPageFormField(
	  /** A list of detected languages for value together with confidence. */
		valueDetectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None,
	  /** Layout for the FormField name. e.g. `Address`, `Email`, `Grand total`, `Phone number`, etc. */
		fieldName: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None,
	  /** The history of this annotation. */
		provenance: Option[Schema.GoogleCloudDocumentaiV1DocumentProvenance] = None,
	  /** A list of detected languages for name together with confidence. */
		nameDetectedLanguages: Option[List[Schema.GoogleCloudDocumentaiV1DocumentPageDetectedLanguage]] = None,
	  /** Created for Labeling UI to export key text. If corrections were made to the text identified by the `field_name.text_anchor`, this field will contain the correction. */
		correctedKeyText: Option[String] = None,
	  /** Layout for the FormField value. */
		fieldValue: Option[Schema.GoogleCloudDocumentaiV1DocumentPageLayout] = None,
	  /** Created for Labeling UI to export value text. If corrections were made to the text identified by the `field_value.text_anchor`, this field will contain the correction. */
		correctedValueText: Option[String] = None,
	  /** If the value is non-textual, this field represents the type. Current valid values are: - blank (this indicates the `field_value` is normal text) - `unfilled_checkbox` - `filled_checkbox` */
		valueType: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1SynonymSet(
	  /** List of Synonyms for the context. */
		synonyms: Option[List[Schema.GoogleCloudContentwarehouseV1SynonymSetSynonym]] = None,
	  /** This is a freeform field. Example contexts can be "sales," "engineering," "real estate," "accounting," etc. The context can be supplied during search requests. */
		context: Option[String] = None,
	  /** The resource name of the SynonymSet This is mandatory for google.api.resource. Format: projects/{project_number}/locations/{location}/synonymSets/{context}. */
		name: Option[String] = None
	)
	
	case class CloudAiPlatformTenantresourceServiceAccountIdentity(
	  /** Output only. The service account email that has been created. */
		serviceAccountEmail: Option[String] = None,
	  /** Input/Output [Optional]. The tag that configures the service account, as defined in google3/configs/production/cdpush/acl-zanzibar-cloud-prod/activation_grants/activation_grants.gcl. Note: The default P4 service account has the empty tag. */
		tag: Option[String] = None
	)
	
	case class GoogleCloudDocumentaiV1NormalizedVertex(
	  /** X coordinate. */
		x: Option[BigDecimal] = None,
	  /** Y coordinate (starts from the top of the image). */
		y: Option[BigDecimal] = None
	)
	
	object GoogleCloudContentwarehouseV1AccessControlAction {
		enum OperationTypeEnum extends Enum[OperationTypeEnum] { case UNKNOWN, ADD_POLICY_BINDING, REMOVE_POLICY_BINDING, REPLACE_POLICY_BINDING }
	}
	case class GoogleCloudContentwarehouseV1AccessControlAction(
	  /** Represents the new policy from which bindings are added, removed or replaced based on the type of the operation. the policy is limited to a few 10s of KB. */
		policy: Option[Schema.GoogleIamV1Policy] = None,
	  /** Identifies the type of operation. */
		operationType: Option[Schema.GoogleCloudContentwarehouseV1AccessControlAction.OperationTypeEnum] = None
	)
	
	object GoogleCloudDocumentaiV1DocumentProvenance {
		enum TypeEnum extends Enum[TypeEnum] { case OPERATION_TYPE_UNSPECIFIED, ADD, REMOVE, UPDATE, REPLACE, EVAL_REQUESTED, EVAL_APPROVED, EVAL_SKIPPED }
	}
	case class GoogleCloudDocumentaiV1DocumentProvenance(
	  /** References to the original elements that are replaced. */
		parents: Option[List[Schema.GoogleCloudDocumentaiV1DocumentProvenanceParent]] = None,
	  /** The type of provenance operation. */
		`type`: Option[Schema.GoogleCloudDocumentaiV1DocumentProvenance.TypeEnum] = None,
	  /** The Id of this operation. Needs to be unique within the scope of the revision. */
		id: Option[Int] = None,
	  /** The index of the revision that produced this element. */
		revision: Option[Int] = None
	)
	
	case class GoogleIamV1AuditConfig(
	  /** Specifies a service that will be enabled for audit logging. For example, `storage.googleapis.com`, `cloudsql.googleapis.com`. `allServices` is a special value that covers all services. */
		service: Option[String] = None,
	  /** The configuration for logging of each type of permission. */
		auditLogConfigs: Option[List[Schema.GoogleIamV1AuditLogConfig]] = None
	)
	
	object GoogleCloudContentwarehouseV1ProjectStatus {
		enum AccessControlModeEnum extends Enum[AccessControlModeEnum] { case ACL_MODE_UNKNOWN, ACL_MODE_UNIVERSAL_ACCESS, ACL_MODE_DOCUMENT_LEVEL_ACCESS_CONTROL_BYOID, ACL_MODE_DOCUMENT_LEVEL_ACCESS_CONTROL_GCI }
		enum DatabaseTypeEnum extends Enum[DatabaseTypeEnum] { case DB_UNKNOWN, DB_INFRA_SPANNER, DB_CLOUD_SQL_POSTGRES }
		enum StateEnum extends Enum[StateEnum] { case PROJECT_STATE_UNSPECIFIED, PROJECT_STATE_PENDING, PROJECT_STATE_COMPLETED, PROJECT_STATE_FAILED, PROJECT_STATE_DELETING, PROJECT_STATE_DELETING_FAILED, PROJECT_STATE_DELETED, PROJECT_STATE_NOT_FOUND }
	}
	case class GoogleCloudContentwarehouseV1ProjectStatus(
	  /** Access control mode. */
		accessControlMode: Option[Schema.GoogleCloudContentwarehouseV1ProjectStatus.AccessControlModeEnum] = None,
	  /** If the qa is enabled on this project. */
		qaEnabled: Option[Boolean] = None,
	  /** The location of the queried project. */
		location: Option[String] = None,
	  /** Database type. */
		databaseType: Option[Schema.GoogleCloudContentwarehouseV1ProjectStatus.DatabaseTypeEnum] = None,
	  /** The default role for the person who create a document. */
		documentCreatorDefaultRole: Option[String] = None,
	  /** State of the project. */
		state: Option[Schema.GoogleCloudContentwarehouseV1ProjectStatus.StateEnum] = None
	)
	
	case class CloudAiPlatformTenantresourceTenantResource(
	  /** A list of P4 service accounts (go/p4sa) to provision or deprovision. */
		p4ServiceAccounts: Option[List[Schema.CloudAiPlatformTenantresourceServiceAccountIdentity]] = None,
	  /** A list of tenant projects and tenant resources to provision or deprovision. */
		tenantProjectResources: Option[List[Schema.CloudAiPlatformTenantresourceTenantProjectResource]] = None
	)
	
	case class GoogleCloudContentwarehouseV1RuleSet(
	  /** The resource name of the rule set. Managed internally. Format: projects/{project_number}/locations/{location}/ruleSet/{rule_set_id}. The name is ignored when creating a rule set. */
		name: Option[String] = None,
	  /** Source of the rules i.e., customer name. */
		source: Option[String] = None,
	  /** List of rules given by the customer. */
		rules: Option[List[Schema.GoogleCloudContentwarehouseV1Rule]] = None,
	  /** Short description of the rule-set. */
		description: Option[String] = None
	)
	
	case class GoogleCloudContentwarehouseV1GetDocumentRequest(
	  /** The meta information collected about the end user, used to enforce access control for the service. */
		requestMetadata: Option[Schema.GoogleCloudContentwarehouseV1RequestMetadata] = None
	)
}
