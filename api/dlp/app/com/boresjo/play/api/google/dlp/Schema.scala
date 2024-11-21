package com.boresjo.play.api.google.dlp

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GooglePrivacyDlpV2Manual(
	
	)
	
	case class GooglePrivacyDlpV2ListStoredInfoTypesResponse(
	  /** If the next page is available then the next page token to be used in the following ListStoredInfoTypes request. */
		nextPageToken: Option[String] = None,
	  /** List of storedInfoTypes, up to page_size in ListStoredInfoTypesRequest. */
		storedInfoTypes: Option[List[Schema.GooglePrivacyDlpV2StoredInfoType]] = None
	)
	
	case class GooglePrivacyDlpV2BigQueryTableCollection(
	  /** A collection of regular expressions to match a BigQuery table against. */
		includeRegexes: Option[Schema.GooglePrivacyDlpV2BigQueryRegexes] = None
	)
	
	case class GooglePrivacyDlpV2AuxiliaryTable(
	  /** Required. Auxiliary table location. */
		table: Option[Schema.GooglePrivacyDlpV2BigQueryTable] = None,
	  /** Required. The relative frequency column must contain a floating-point number between 0 and 1 (inclusive). Null values are assumed to be zero. */
		relativeFrequency: Option[Schema.GooglePrivacyDlpV2FieldId] = None,
	  /** Required. Quasi-identifier columns. */
		quasiIds: Option[List[Schema.GooglePrivacyDlpV2QuasiIdField]] = None
	)
	
	case class GooglePrivacyDlpV2OtherCloudDiscoveryStartingLocation(
	  /** The AWS starting location for discovery. */
		awsLocation: Option[Schema.GooglePrivacyDlpV2AwsDiscoveryStartingLocation] = None
	)
	
	case class GooglePrivacyDlpV2InspectTemplate(
	  /** The core content of the template. Configuration of the scanning process. */
		inspectConfig: Option[Schema.GooglePrivacyDlpV2InspectConfig] = None,
	  /** Output only. The creation timestamp of an inspectTemplate. */
		createTime: Option[String] = None,
	  /** Display name (max 256 chars). */
		displayName: Option[String] = None,
	  /** Short description (max 256 chars). */
		description: Option[String] = None,
	  /** Output only. The template name. The template will have one of the following formats: `projects/PROJECT_ID/inspectTemplates/TEMPLATE_ID` OR `organizations/ORGANIZATION_ID/inspectTemplates/TEMPLATE_ID`; */
		name: Option[String] = None,
	  /** Output only. The last update timestamp of an inspectTemplate. */
		updateTime: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2ReidentifyContentRequest(
	  /** Configuration for the inspector. */
		inspectConfig: Option[Schema.GooglePrivacyDlpV2InspectConfig] = None,
	  /** The item to re-identify. Will be treated as text. */
		item: Option[Schema.GooglePrivacyDlpV2ContentItem] = None,
	  /** Deprecated. This field has no effect. */
		locationId: Option[String] = None,
	  /** Template to use. References an instance of `DeidentifyTemplate`. Any configuration directly specified in `reidentify_config` or `inspect_config` will override those set in the template. The `DeidentifyTemplate` used must include only reversible transformations. Singular fields that are set in this request will replace their corresponding fields in the template. Repeated fields are appended. Singular sub-messages and groups are recursively merged. */
		reidentifyTemplateName: Option[String] = None,
	  /** Configuration for the re-identification of the content item. This field shares the same proto message type that is used for de-identification, however its usage here is for the reversal of the previous de-identification. Re-identification is performed by examining the transformations used to de-identify the items and executing the reverse. This requires that only reversible transformations be provided here. The reversible transformations are: - `CryptoDeterministicConfig` - `CryptoReplaceFfxFpeConfig` */
		reidentifyConfig: Option[Schema.GooglePrivacyDlpV2DeidentifyConfig] = None,
	  /** Template to use. Any configuration directly specified in `inspect_config` will override those set in the template. Singular fields that are set in this request will replace their corresponding fields in the template. Repeated fields are appended. Singular sub-messages and groups are recursively merged. */
		inspectTemplateName: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2KAnonymityEquivalenceClass(
	  /** Size of the equivalence class, for example number of rows with the above set of values. */
		equivalenceClassSize: Option[String] = None,
	  /** Set of values defining the equivalence class. One value per quasi-identifier column in the original KAnonymity metric message. The order is always the same as the original request. */
		quasiIdsValues: Option[List[Schema.GooglePrivacyDlpV2Value]] = None
	)
	
	case class GooglePrivacyDlpV2TransformationErrorHandling(
	  /** Throw an error */
		throwError: Option[Schema.GooglePrivacyDlpV2ThrowError] = None,
	  /** Ignore errors */
		leaveUntransformed: Option[Schema.GooglePrivacyDlpV2LeaveUntransformed] = None
	)
	
	case class GooglePrivacyDlpV2UpdateConnectionRequest(
	  /** Optional. Mask to control which fields get updated. */
		updateMask: Option[String] = None,
	  /** Required. The connection with new values for the relevant fields. */
		connection: Option[Schema.GooglePrivacyDlpV2Connection] = None
	)
	
	case class GooglePrivacyDlpV2PrivacyMetric(
	  /** Categorical stats */
		categoricalStatsConfig: Option[Schema.GooglePrivacyDlpV2CategoricalStatsConfig] = None,
	  /** k-map */
		kMapEstimationConfig: Option[Schema.GooglePrivacyDlpV2KMapEstimationConfig] = None,
	  /** l-diversity */
		lDiversityConfig: Option[Schema.GooglePrivacyDlpV2LDiversityConfig] = None,
	  /** delta-presence */
		deltaPresenceEstimationConfig: Option[Schema.GooglePrivacyDlpV2DeltaPresenceEstimationConfig] = None,
	  /** Numerical stats */
		numericalStatsConfig: Option[Schema.GooglePrivacyDlpV2NumericalStatsConfig] = None,
	  /** K-anonymity */
		kAnonymityConfig: Option[Schema.GooglePrivacyDlpV2KAnonymityConfig] = None
	)
	
	case class GooglePrivacyDlpV2DatabaseResourceRegexes(
	  /** A group of regular expression patterns to match against one or more database resources. Maximum of 100 entries. The sum of all regular expression's length can't exceed 10 KiB. */
		patterns: Option[List[Schema.GooglePrivacyDlpV2DatabaseResourceRegex]] = None
	)
	
	case class GooglePrivacyDlpV2ExcludeInfoTypes(
	  /** InfoType list in ExclusionRule rule drops a finding when it overlaps or contained within with a finding of an infoType from this list. For example, for `InspectionRuleSet.info_types` containing "PHONE_NUMBER"` and `exclusion_rule` containing `exclude_info_types.info_types` with "EMAIL_ADDRESS" the phone number findings are dropped if they overlap with EMAIL_ADDRESS finding. That leads to "555-222-2222@example.org" to generate only a single finding, namely email address. */
		infoTypes: Option[List[Schema.GooglePrivacyDlpV2InfoType]] = None
	)
	
	object GooglePrivacyDlpV2InspectConfig {
		enum ContentOptionsEnum extends Enum[ContentOptionsEnum] { case CONTENT_UNSPECIFIED, CONTENT_TEXT, CONTENT_IMAGE }
		enum MinLikelihoodEnum extends Enum[MinLikelihoodEnum] { case LIKELIHOOD_UNSPECIFIED, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
	}
	case class GooglePrivacyDlpV2InspectConfig(
	  /** CustomInfoTypes provided by the user. See https://cloud.google.com/sensitive-data-protection/docs/creating-custom-infotypes to learn more. */
		customInfoTypes: Option[List[Schema.GooglePrivacyDlpV2CustomInfoType]] = None,
	  /** Minimum likelihood per infotype. For each infotype, a user can specify a minimum likelihood. The system only returns a finding if its likelihood is above this threshold. If this field is not set, the system uses the InspectConfig min_likelihood. */
		minLikelihoodPerInfoType: Option[List[Schema.GooglePrivacyDlpV2InfoTypeLikelihood]] = None,
	  /** Set of rules to apply to the findings for this InspectConfig. Exclusion rules, contained in the set are executed in the end, other rules are executed in the order they are specified for each info type. */
		ruleSet: Option[List[Schema.GooglePrivacyDlpV2InspectionRuleSet]] = None,
	  /** When true, excludes type information of the findings. This is not used for data profiling. */
		excludeInfoTypes: Option[Boolean] = None,
	  /** When true, a contextual quote from the data that triggered a finding is included in the response; see Finding.quote. This is not used for data profiling. */
		includeQuote: Option[Boolean] = None,
	  /** Configuration to control the number of findings returned. This is not used for data profiling. When redacting sensitive data from images, finding limits don't apply. They can cause unexpected or inconsistent results, where only some data is redacted. Don't include finding limits in RedactImage requests. Otherwise, Cloud DLP returns an error. When set within an InspectJobConfig, the specified maximum values aren't hard limits. If an inspection job reaches these limits, the job ends gradually, not abruptly. Therefore, the actual number of findings that Cloud DLP returns can be multiple times higher than these maximum values. */
		limits: Option[Schema.GooglePrivacyDlpV2FindingLimits] = None,
	  /** Restricts what info_types to look for. The values must correspond to InfoType values returned by ListInfoTypes or listed at https://cloud.google.com/sensitive-data-protection/docs/infotypes-reference. When no InfoTypes or CustomInfoTypes are specified in a request, the system may automatically choose a default list of detectors to run, which may change over time. If you need precise control and predictability as to what detectors are run you should specify specific InfoTypes listed in the reference, otherwise a default list will be used, which may change over time. */
		infoTypes: Option[List[Schema.GooglePrivacyDlpV2InfoType]] = None,
	  /** Deprecated and unused. */
		contentOptions: Option[List[Schema.GooglePrivacyDlpV2InspectConfig.ContentOptionsEnum]] = None,
	  /** Only returns findings equal to or above this threshold. The default is POSSIBLE. In general, the highest likelihood setting yields the fewest findings in results and the lowest chance of a false positive. For more information, see [Match likelihood](https://cloud.google.com/sensitive-data-protection/docs/likelihood). */
		minLikelihood: Option[Schema.GooglePrivacyDlpV2InspectConfig.MinLikelihoodEnum] = None
	)
	
	case class GooglePrivacyDlpV2AllInfoTypes(
	
	)
	
	case class GooglePrivacyDlpV2Disabled(
	
	)
	
	case class GooglePrivacyDlpV2ProfileStatus(
	  /** Time when the profile generation status was updated */
		timestamp: Option[String] = None,
	  /** Profiling status code and optional message. The `status.code` value is 0 (default value) for OK. */
		status: Option[Schema.GoogleRpcStatus] = None
	)
	
	case class GooglePrivacyDlpV2TagValue(
	  /** The namespaced name for the tag value to attach to resources. Must be in the format `{parent_id}/{tag_key_short_name}/{short_name}`, for example, "123456/environment/prod". */
		namespacedValue: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2ListColumnDataProfilesResponse(
	  /** The next page token. */
		nextPageToken: Option[String] = None,
	  /** List of data profiles. */
		columnDataProfiles: Option[List[Schema.GooglePrivacyDlpV2ColumnDataProfile]] = None
	)
	
	case class GooglePrivacyDlpV2SelectedInfoTypes(
	  /** Required. InfoTypes to apply the transformation to. Required. Provided InfoType must be unique within the ImageTransformations message. */
		infoTypes: Option[List[Schema.GooglePrivacyDlpV2InfoType]] = None
	)
	
	case class GooglePrivacyDlpV2FieldId(
	  /** Name describing the field. */
		name: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2ListInspectTemplatesResponse(
	  /** List of inspectTemplates, up to page_size in ListInspectTemplatesRequest. */
		inspectTemplates: Option[List[Schema.GooglePrivacyDlpV2InspectTemplate]] = None,
	  /** If the next page is available then the next page token to be used in the following ListInspectTemplates request. */
		nextPageToken: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2RequestedDeidentifyOptions(
	  /** Snapshot of the state of the image transformation `DeidentifyTemplate` from the `Deidentify` action at the time this job was run. */
		snapshotImageRedactTemplate: Option[Schema.GooglePrivacyDlpV2DeidentifyTemplate] = None,
	  /** Snapshot of the state of the `DeidentifyTemplate` from the Deidentify action at the time this job was run. */
		snapshotDeidentifyTemplate: Option[Schema.GooglePrivacyDlpV2DeidentifyTemplate] = None,
	  /** Snapshot of the state of the structured `DeidentifyTemplate` from the `Deidentify` action at the time this job was run. */
		snapshotStructuredDeidentifyTemplate: Option[Schema.GooglePrivacyDlpV2DeidentifyTemplate] = None
	)
	
	case class GooglePrivacyDlpV2ReidentifyContentResponse(
	  /** An overview of the changes that were made to the `item`. */
		overview: Option[Schema.GooglePrivacyDlpV2TransformationOverview] = None,
	  /** The re-identified item. */
		item: Option[Schema.GooglePrivacyDlpV2ContentItem] = None
	)
	
	object GooglePrivacyDlpV2TransformationResultStatus {
		enum ResultStatusTypeEnum extends Enum[ResultStatusTypeEnum] { case STATE_TYPE_UNSPECIFIED, INVALID_TRANSFORM, BIGQUERY_MAX_ROW_SIZE_EXCEEDED, METADATA_UNRETRIEVABLE, SUCCESS }
	}
	case class GooglePrivacyDlpV2TransformationResultStatus(
	  /** Detailed error codes and messages */
		details: Option[Schema.GoogleRpcStatus] = None,
	  /** Transformation result status type, this will be either SUCCESS, or it will be the reason for why the transformation was not completely successful. */
		resultStatusType: Option[Schema.GooglePrivacyDlpV2TransformationResultStatus.ResultStatusTypeEnum] = None
	)
	
	case class GooglePrivacyDlpV2ActivateJobTriggerRequest(
	
	)
	
	case class GooglePrivacyDlpV2SearchConnectionsResponse(
	  /** Token to retrieve the next page of results. An empty value means there are no more results. */
		nextPageToken: Option[String] = None,
	  /** List of connections that match the search query. Note that only a subset of the fields will be populated, and only "name" is guaranteed to be set. For full details of a Connection, call GetConnection with the name. */
		connections: Option[List[Schema.GooglePrivacyDlpV2Connection]] = None
	)
	
	object GooglePrivacyDlpV2Finding {
		enum LikelihoodEnum extends Enum[LikelihoodEnum] { case LIKELIHOOD_UNSPECIFIED, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
	}
	case class GooglePrivacyDlpV2Finding(
	  /** The type of content that might have been found. Provided if `excluded_types` is false. */
		infoType: Option[Schema.GooglePrivacyDlpV2InfoType] = None,
	  /** Resource name in format projects/{project}/locations/{location}/findings/{finding} Populated only when viewing persisted findings. */
		name: Option[String] = None,
	  /** Confidence of how likely it is that the `info_type` is correct. */
		likelihood: Option[Schema.GooglePrivacyDlpV2Finding.LikelihoodEnum] = None,
	  /** The job that stored the finding. */
		resourceName: Option[String] = None,
	  /** The unique finding id. */
		findingId: Option[String] = None,
	  /** Timestamp when finding was detected. */
		createTime: Option[String] = None,
	  /** Job trigger name, if applicable, for this finding. */
		triggerName: Option[String] = None,
	  /** The labels associated with this `Finding`. Label keys must be between 1 and 63 characters long and must conform to the following regular expression: `[a-z]([-a-z0-9]&#42;[a-z0-9])?`. Label values must be between 0 and 63 characters long and must conform to the regular expression `([a-z]([-a-z0-9]&#42;[a-z0-9])?)?`. No more than 10 labels can be associated with a given finding. Examples: &#42; `"environment" : "production"` &#42; `"pipeline" : "etl"` */
		labels: Option[Map[String, String]] = None,
	  /** Contains data parsed from quotes. Only populated if include_quote was set to true and a supported infoType was requested. Currently supported infoTypes: DATE, DATE_OF_BIRTH and TIME. */
		quoteInfo: Option[Schema.GooglePrivacyDlpV2QuoteInfo] = None,
	  /** The job that stored the finding. */
		jobName: Option[String] = None,
	  /** Where the content was found. */
		location: Option[Schema.GooglePrivacyDlpV2Location] = None,
	  /** Time the job started that produced this finding. */
		jobCreateTime: Option[String] = None,
	  /** The content that was found. Even if the content is not textual, it may be converted to a textual representation here. Provided if `include_quote` is true and the finding is less than or equal to 4096 bytes long. If the finding exceeds 4096 bytes in length, the quote may be omitted. */
		quote: Option[String] = None
	)
	
	object GooglePrivacyDlpV2ExclusionRule {
		enum MatchingTypeEnum extends Enum[MatchingTypeEnum] { case MATCHING_TYPE_UNSPECIFIED, MATCHING_TYPE_FULL_MATCH, MATCHING_TYPE_PARTIAL_MATCH, MATCHING_TYPE_INVERSE_MATCH }
	}
	case class GooglePrivacyDlpV2ExclusionRule(
	  /** Regular expression which defines the rule. */
		regex: Option[Schema.GooglePrivacyDlpV2Regex] = None,
	  /** Drop if the hotword rule is contained in the proximate context. For tabular data, the context includes the column name. */
		excludeByHotword: Option[Schema.GooglePrivacyDlpV2ExcludeByHotword] = None,
	  /** Set of infoTypes for which findings would affect this rule. */
		excludeInfoTypes: Option[Schema.GooglePrivacyDlpV2ExcludeInfoTypes] = None,
	  /** Dictionary which defines the rule. */
		dictionary: Option[Schema.GooglePrivacyDlpV2Dictionary] = None,
	  /** How the rule is applied, see MatchingType documentation for details. */
		matchingType: Option[Schema.GooglePrivacyDlpV2ExclusionRule.MatchingTypeEnum] = None
	)
	
	case class GooglePrivacyDlpV2CreateJobTriggerRequest(
	  /** The trigger id can contain uppercase and lowercase letters, numbers, and hyphens; that is, it must match the regular expression: `[a-zA-Z\d-_]+`. The maximum length is 100 characters. Can be empty to allow the system to generate one. */
		triggerId: Option[String] = None,
	  /** Deprecated. This field has no effect. */
		locationId: Option[String] = None,
	  /** Required. The JobTrigger to create. */
		jobTrigger: Option[Schema.GooglePrivacyDlpV2JobTrigger] = None
	)
	
	case class GooglePrivacyDlpV2UpdateStoredInfoTypeRequest(
	  /** Updated configuration for the storedInfoType. If not provided, a new version of the storedInfoType will be created with the existing configuration. */
		config: Option[Schema.GooglePrivacyDlpV2StoredInfoTypeConfig] = None,
	  /** Mask to control which fields get updated. */
		updateMask: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2CryptoHashConfig(
	  /** The key used by the hash function. */
		cryptoKey: Option[Schema.GooglePrivacyDlpV2CryptoKey] = None
	)
	
	case class GooglePrivacyDlpV2FileSet(
	  /** The regex-filtered set of files to scan. Exactly one of `url` or `regex_file_set` must be set. */
		regexFileSet: Option[Schema.GooglePrivacyDlpV2CloudStorageRegexFileSet] = None,
	  /** The Cloud Storage url of the file(s) to scan, in the format `gs:///`. Trailing wildcard in the path is allowed. If the url ends in a trailing slash, the bucket or directory represented by the url will be scanned non-recursively (content in sub-directories will not be scanned). This means that `gs://mybucket/` is equivalent to `gs://mybucket/&#42;`, and `gs://mybucket/directory/` is equivalent to `gs://mybucket/directory/&#42;`. Exactly one of `url` or `regex_file_set` must be set. */
		url: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2RecordLocation(
	  /** Key of the finding. */
		recordKey: Option[Schema.GooglePrivacyDlpV2RecordKey] = None,
	  /** Field id of the field containing the finding. */
		fieldId: Option[Schema.GooglePrivacyDlpV2FieldId] = None,
	  /** Location within a `ContentItem.Table`. */
		tableLocation: Option[Schema.GooglePrivacyDlpV2TableLocation] = None
	)
	
	case class GooglePrivacyDlpV2CreateDlpJobRequest(
	  /** An inspection job scans a storage repository for InfoTypes. */
		inspectJob: Option[Schema.GooglePrivacyDlpV2InspectJobConfig] = None,
	  /** Deprecated. This field has no effect. */
		locationId: Option[String] = None,
	  /** The job id can contain uppercase and lowercase letters, numbers, and hyphens; that is, it must match the regular expression: `[a-zA-Z\d-_]+`. The maximum length is 100 characters. Can be empty to allow the system to generate one. */
		jobId: Option[String] = None,
	  /** A risk analysis job calculates re-identification risk metrics for a BigQuery table. */
		riskJob: Option[Schema.GooglePrivacyDlpV2RiskAnalysisJobConfig] = None
	)
	
	case class GooglePrivacyDlpV2LargeCustomDictionaryStats(
	  /** Approximate number of distinct phrases in the dictionary. */
		approxNumPhrases: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2Action(
	  /** Sends an email when the job completes. The email goes to IAM project owners and technical [Essential Contacts](https://cloud.google.com/resource-manager/docs/managing-notification-contacts). */
		jobNotificationEmails: Option[Schema.GooglePrivacyDlpV2JobNotificationEmails] = None,
	  /** Publish a notification to a Pub/Sub topic. */
		pubSub: Option[Schema.GooglePrivacyDlpV2PublishToPubSub] = None,
	  /** Enable Stackdriver metric dlp.googleapis.com/finding_count. */
		publishToStackdriver: Option[Schema.GooglePrivacyDlpV2PublishToStackdriver] = None,
	  /** Publish findings to Cloud Datahub. */
		publishFindingsToCloudDataCatalog: Option[Schema.GooglePrivacyDlpV2PublishFindingsToCloudDataCatalog] = None,
	  /** Publish summary to Cloud Security Command Center (Alpha). */
		publishSummaryToCscc: Option[Schema.GooglePrivacyDlpV2PublishSummaryToCscc] = None,
	  /** Save resulting findings in a provided location. */
		saveFindings: Option[Schema.GooglePrivacyDlpV2SaveFindings] = None,
	  /** Create a de-identified copy of the input data. */
		deidentify: Option[Schema.GooglePrivacyDlpV2Deidentify] = None
	)
	
	object GooglePrivacyDlpV2CloudStorageOptions {
		enum SampleMethodEnum extends Enum[SampleMethodEnum] { case SAMPLE_METHOD_UNSPECIFIED, TOP, RANDOM_START }
		enum FileTypesEnum extends Enum[FileTypesEnum] { case FILE_TYPE_UNSPECIFIED, BINARY_FILE, TEXT_FILE, IMAGE, WORD, PDF, AVRO, CSV, TSV, POWERPOINT, EXCEL }
	}
	case class GooglePrivacyDlpV2CloudStorageOptions(
	  /** How to sample the data. */
		sampleMethod: Option[Schema.GooglePrivacyDlpV2CloudStorageOptions.SampleMethodEnum] = None,
	  /** Limits the number of files to scan to this percentage of the input FileSet. Number of files scanned is rounded down. Must be between 0 and 100, inclusively. Both 0 and 100 means no limit. Defaults to 0. */
		filesLimitPercent: Option[Int] = None,
	  /** Max number of bytes to scan from a file. If a scanned file's size is bigger than this value then the rest of the bytes are omitted. Only one of `bytes_limit_per_file` and `bytes_limit_per_file_percent` can be specified. This field can't be set if de-identification is requested. For certain file types, setting this field has no effect. For more information, see [Limits on bytes scanned per file](https://cloud.google.com/sensitive-data-protection/docs/supported-file-types#max-byte-size-per-file). */
		bytesLimitPerFile: Option[String] = None,
	  /** List of file type groups to include in the scan. If empty, all files are scanned and available data format processors are applied. In addition, the binary content of the selected files is always scanned as well. Images are scanned only as binary if the specified region does not support image inspection and no file_types were specified. Image inspection is restricted to 'global', 'us', 'asia', and 'europe'. */
		fileTypes: Option[List[Schema.GooglePrivacyDlpV2CloudStorageOptions.FileTypesEnum]] = None,
	  /** The set of one or more files to scan. */
		fileSet: Option[Schema.GooglePrivacyDlpV2FileSet] = None,
	  /** Max percentage of bytes to scan from a file. The rest are omitted. The number of bytes scanned is rounded down. Must be between 0 and 100, inclusively. Both 0 and 100 means no limit. Defaults to 0. Only one of bytes_limit_per_file and bytes_limit_per_file_percent can be specified. This field can't be set if de-identification is requested. For certain file types, setting this field has no effect. For more information, see [Limits on bytes scanned per file](https://cloud.google.com/sensitive-data-protection/docs/supported-file-types#max-byte-size-per-file). */
		bytesLimitPerFilePercent: Option[Int] = None
	)
	
	object GooglePrivacyDlpV2LikelihoodAdjustment {
		enum FixedLikelihoodEnum extends Enum[FixedLikelihoodEnum] { case LIKELIHOOD_UNSPECIFIED, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
	}
	case class GooglePrivacyDlpV2LikelihoodAdjustment(
	  /** Set the likelihood of a finding to a fixed value. */
		fixedLikelihood: Option[Schema.GooglePrivacyDlpV2LikelihoodAdjustment.FixedLikelihoodEnum] = None,
	  /** Increase or decrease the likelihood by the specified number of levels. For example, if a finding would be `POSSIBLE` without the detection rule and `relative_likelihood` is 1, then it is upgraded to `LIKELY`, while a value of -1 would downgrade it to `UNLIKELY`. Likelihood may never drop below `VERY_UNLIKELY` or exceed `VERY_LIKELY`, so applying an adjustment of 1 followed by an adjustment of -1 when base likelihood is `VERY_LIKELY` will result in a final likelihood of `LIKELY`. */
		relativeLikelihood: Option[Int] = None
	)
	
	object GooglePrivacyDlpV2Connection {
		enum StateEnum extends Enum[StateEnum] { case CONNECTION_STATE_UNSPECIFIED, MISSING_CREDENTIALS, AVAILABLE, ERROR }
	}
	case class GooglePrivacyDlpV2Connection(
	  /** Output only. Name of the connection: `projects/{project}/locations/{location}/connections/{name}`. */
		name: Option[String] = None,
	  /** Output only. Set if status == ERROR, to provide additional details. Will store the last 10 errors sorted with the most recent first. */
		errors: Option[List[Schema.GooglePrivacyDlpV2Error]] = None,
	  /** Connect to a Cloud SQL instance. */
		cloudSql: Option[Schema.GooglePrivacyDlpV2CloudSqlProperties] = None,
	  /** Required. The connection's state in its lifecycle. */
		state: Option[Schema.GooglePrivacyDlpV2Connection.StateEnum] = None
	)
	
	case class GooglePrivacyDlpV2PublishFindingsToCloudDataCatalog(
	
	)
	
	case class GooglePrivacyDlpV2RiskAnalysisJobConfig(
	  /** Privacy metric to compute. */
		privacyMetric: Option[Schema.GooglePrivacyDlpV2PrivacyMetric] = None,
	  /** Actions to execute at the completion of the job. Are executed in the order provided. */
		actions: Option[List[Schema.GooglePrivacyDlpV2Action]] = None,
	  /** Input dataset to compute metrics over. */
		sourceTable: Option[Schema.GooglePrivacyDlpV2BigQueryTable] = None
	)
	
	case class GooglePrivacyDlpV2OtherCloudDiscoveryTarget(
	  /** Disable profiling for resources that match this filter. */
		disabled: Option[Schema.GooglePrivacyDlpV2Disabled] = None,
	  /** Required. The type of data profiles generated by this discovery target. Supported values are: &#42; aws/s3/bucket */
		dataSourceType: Option[Schema.GooglePrivacyDlpV2DataSourceType] = None,
	  /** Optional. In addition to matching the filter, these conditions must be true before a profile is generated. */
		conditions: Option[Schema.GooglePrivacyDlpV2DiscoveryOtherCloudConditions] = None,
	  /** Required. The resources that the discovery cadence applies to. The first target with a matching filter will be the one to apply to a resource. */
		filter: Option[Schema.GooglePrivacyDlpV2DiscoveryOtherCloudFilter] = None,
	  /** How often and when to update data profiles. New resources that match both the filter and conditions are scanned as quickly as possible depending on system capacity. */
		generationCadence: Option[Schema.GooglePrivacyDlpV2DiscoveryOtherCloudGenerationCadence] = None
	)
	
	object GooglePrivacyDlpV2AmazonS3BucketConditions {
		enum BucketTypesEnum extends Enum[BucketTypesEnum] { case TYPE_UNSPECIFIED, TYPE_ALL_SUPPORTED, TYPE_GENERAL_PURPOSE }
		enum ObjectStorageClassesEnum extends Enum[ObjectStorageClassesEnum] { case UNSPECIFIED, ALL_SUPPORTED_CLASSES, STANDARD, STANDARD_INFREQUENT_ACCESS, GLACIER_INSTANT_RETRIEVAL, INTELLIGENT_TIERING }
	}
	case class GooglePrivacyDlpV2AmazonS3BucketConditions(
	  /** Optional. Bucket types that should be profiled. Optional. Defaults to TYPE_ALL_SUPPORTED if unspecified. */
		bucketTypes: Option[List[Schema.GooglePrivacyDlpV2AmazonS3BucketConditions.BucketTypesEnum]] = None,
	  /** Optional. Object classes that should be profiled. Optional. Defaults to ALL_SUPPORTED_CLASSES if unspecified. */
		objectStorageClasses: Option[List[Schema.GooglePrivacyDlpV2AmazonS3BucketConditions.ObjectStorageClassesEnum]] = None
	)
	
	case class GooglePrivacyDlpV2StatisticalTable(
	  /** Required. The relative frequency column must contain a floating-point number between 0 and 1 (inclusive). Null values are assumed to be zero. */
		relativeFrequency: Option[Schema.GooglePrivacyDlpV2FieldId] = None,
	  /** Required. Auxiliary table location. */
		table: Option[Schema.GooglePrivacyDlpV2BigQueryTable] = None,
	  /** Required. Quasi-identifier columns. */
		quasiIds: Option[List[Schema.GooglePrivacyDlpV2QuasiIdentifierField]] = None
	)
	
	case class GooglePrivacyDlpV2HybridInspectStatistics(
	  /** The number of hybrid inspection requests aborted because the job ran out of quota or was ended before they could be processed. */
		abortedCount: Option[String] = None,
	  /** The number of hybrid inspection requests processed within this job. */
		processedCount: Option[String] = None,
	  /** The number of hybrid requests currently being processed. Only populated when called via method `getDlpJob`. A burst of traffic may cause hybrid inspect requests to be enqueued. Processing will take place as quickly as possible, but resource limitations may impact how long a request is enqueued for. */
		pendingCount: Option[String] = None
	)
	
	object GooglePrivacyDlpV2DataRiskLevel {
		enum ScoreEnum extends Enum[ScoreEnum] { case RISK_SCORE_UNSPECIFIED, RISK_LOW, RISK_UNKNOWN, RISK_MODERATE, RISK_HIGH }
	}
	case class GooglePrivacyDlpV2DataRiskLevel(
	  /** The score applied to the resource. */
		score: Option[Schema.GooglePrivacyDlpV2DataRiskLevel.ScoreEnum] = None
	)
	
	case class GooglePrivacyDlpV2CategoricalStatsResult(
	  /** Histogram of value frequencies in the column. */
		valueFrequencyHistogramBuckets: Option[List[Schema.GooglePrivacyDlpV2CategoricalStatsHistogramBucket]] = None
	)
	
	case class GooglePrivacyDlpV2CloudStoragePath(
	  /** A URL representing a file or path (no wildcards) in Cloud Storage. Example: `gs://[BUCKET_NAME]/dictionary.txt` */
		path: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2InspectionRule(
	  /** Hotword-based detection rule. */
		hotwordRule: Option[Schema.GooglePrivacyDlpV2HotwordRule] = None,
	  /** Exclusion rule. */
		exclusionRule: Option[Schema.GooglePrivacyDlpV2ExclusionRule] = None
	)
	
	case class GooglePrivacyDlpV2RequestedOptions(
	  /** If run with an InspectTemplate, a snapshot of its state at the time of this run. */
		snapshotInspectTemplate: Option[Schema.GooglePrivacyDlpV2InspectTemplate] = None,
	  /** Inspect config. */
		jobConfig: Option[Schema.GooglePrivacyDlpV2InspectJobConfig] = None
	)
	
	case class GooglePrivacyDlpV2RequestedRiskAnalysisOptions(
	  /** The job config for the risk job. */
		jobConfig: Option[Schema.GooglePrivacyDlpV2RiskAnalysisJobConfig] = None
	)
	
	case class GooglePrivacyDlpV2UpdateDeidentifyTemplateRequest(
	  /** New DeidentifyTemplate value. */
		deidentifyTemplate: Option[Schema.GooglePrivacyDlpV2DeidentifyTemplate] = None,
	  /** Mask to control which fields get updated. */
		updateMask: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2KMapEstimationConfig(
	  /** ISO 3166-1 alpha-2 region code to use in the statistical modeling. Set if no column is tagged with a region-specific InfoType (like US_ZIP_5) or a region code. */
		regionCode: Option[String] = None,
	  /** Several auxiliary tables can be used in the analysis. Each custom_tag used to tag a quasi-identifiers column must appear in exactly one column of one auxiliary table. */
		auxiliaryTables: Option[List[Schema.GooglePrivacyDlpV2AuxiliaryTable]] = None,
	  /** Required. Fields considered to be quasi-identifiers. No two columns can have the same tag. */
		quasiIds: Option[List[Schema.GooglePrivacyDlpV2TaggedField]] = None
	)
	
	case class GooglePrivacyDlpV2Table(
	  /** Rows of the table. */
		rows: Option[List[Schema.GooglePrivacyDlpV2Row]] = None,
	  /** Headers of the table. */
		headers: Option[List[Schema.GooglePrivacyDlpV2FieldId]] = None
	)
	
	case class GooglePrivacyDlpV2OrgConfig(
	  /** The data to scan: folder, org, or project */
		location: Option[Schema.GooglePrivacyDlpV2DiscoveryStartingLocation] = None,
	  /** The project that will run the scan. The DLP service account that exists within this project must have access to all resources that are profiled, and the DLP API must be enabled. */
		projectId: Option[String] = None
	)
	
	object GooglePrivacyDlpV2DataProfilePubSubMessage {
		enum EventEnum extends Enum[EventEnum] { case EVENT_TYPE_UNSPECIFIED, NEW_PROFILE, CHANGED_PROFILE, SCORE_INCREASED, ERROR_CHANGED }
	}
	case class GooglePrivacyDlpV2DataProfilePubSubMessage(
	  /** The event that caused the Pub/Sub message to be sent. */
		event: Option[Schema.GooglePrivacyDlpV2DataProfilePubSubMessage.EventEnum] = None,
	  /** If `DetailLevel` is `FILE_STORE_PROFILE` this will be fully populated. Otherwise, if `DetailLevel` is `RESOURCE_NAME`, then only `name` and `file_store_path` will be populated. */
		fileStoreProfile: Option[Schema.GooglePrivacyDlpV2FileStoreDataProfile] = None,
	  /** If `DetailLevel` is `TABLE_PROFILE` this will be fully populated. Otherwise, if `DetailLevel` is `RESOURCE_NAME`, then only `name` and `full_resource` will be populated. */
		profile: Option[Schema.GooglePrivacyDlpV2TableDataProfile] = None
	)
	
	case class GooglePrivacyDlpV2ReplaceDictionaryConfig(
	  /** A list of words to select from for random replacement. The [limits](https://cloud.google.com/sensitive-data-protection/limits) page contains details about the size limits of dictionaries. */
		wordList: Option[Schema.GooglePrivacyDlpV2WordList] = None
	)
	
	case class GooglePrivacyDlpV2RecordCondition(
	  /** An expression. */
		expressions: Option[Schema.GooglePrivacyDlpV2Expressions] = None
	)
	
	case class GooglePrivacyDlpV2HotwordRule(
	  /** Range of characters within which the entire hotword must reside. The total length of the window cannot exceed 1000 characters. The finding itself will be included in the window, so that hotwords can be used to match substrings of the finding itself. Suppose you want Cloud DLP to promote the likelihood of the phone number regex "\(\d{3}\) \d{3}-\d{4}" if the area code is known to be the area code of a company's office. In this case, use the hotword regex "\(xxx\)", where "xxx" is the area code in question. For tabular data, if you want to modify the likelihood of an entire column of findngs, see [Hotword example: Set the match likelihood of a table column] (https://cloud.google.com/sensitive-data-protection/docs/creating-custom-infotypes-likelihood#match-column-values). */
		proximity: Option[Schema.GooglePrivacyDlpV2Proximity] = None,
	  /** Likelihood adjustment to apply to all matching findings. */
		likelihoodAdjustment: Option[Schema.GooglePrivacyDlpV2LikelihoodAdjustment] = None,
	  /** Regular expression pattern defining what qualifies as a hotword. */
		hotwordRegex: Option[Schema.GooglePrivacyDlpV2Regex] = None
	)
	
	case class GooglePrivacyDlpV2Bucket(
	  /** Upper bound of the range, exclusive; type must match min. */
		max: Option[Schema.GooglePrivacyDlpV2Value] = None,
	  /** Lower bound of the range, inclusive. Type should be the same as max if used. */
		min: Option[Schema.GooglePrivacyDlpV2Value] = None,
	  /** Required. Replacement value for this bucket. */
		replacementValue: Option[Schema.GooglePrivacyDlpV2Value] = None
	)
	
	case class GooglePrivacyDlpV2RedactConfig(
	
	)
	
	case class GooglePrivacyDlpV2OtherCloudResourceRegex(
	  /** Regex for Amazon S3 buckets. */
		amazonS3BucketRegex: Option[Schema.GooglePrivacyDlpV2AmazonS3BucketRegex] = None
	)
	
	case class GooglePrivacyDlpV2DiscoveryCloudSqlFilter(
	  /** Catch-all. This should always be the last target in the list because anything above it will apply first. Should only appear once in a configuration. If none is specified, a default one will be added automatically. */
		others: Option[Schema.GooglePrivacyDlpV2AllOtherDatabaseResources] = None,
	  /** A specific set of database resources for this filter to apply to. */
		collection: Option[Schema.GooglePrivacyDlpV2DatabaseResourceCollection] = None,
	  /** The database resource to scan. Targets including this can only include one target (the target with this database resource reference). */
		databaseResourceReference: Option[Schema.GooglePrivacyDlpV2DatabaseResourceReference] = None
	)
	
	object GooglePrivacyDlpV2SensitivityScore {
		enum ScoreEnum extends Enum[ScoreEnum] { case SENSITIVITY_SCORE_UNSPECIFIED, SENSITIVITY_LOW, SENSITIVITY_UNKNOWN, SENSITIVITY_MODERATE, SENSITIVITY_HIGH }
	}
	case class GooglePrivacyDlpV2SensitivityScore(
	  /** The sensitivity score applied to the resource. */
		score: Option[Schema.GooglePrivacyDlpV2SensitivityScore.ScoreEnum] = None
	)
	
	case class GooglePrivacyDlpV2SecretManagerCredential(
	  /** Required. The name of the Secret Manager resource that stores the password, in the form `projects/project-id/secrets/secret-name/versions/version`. */
		passwordSecretVersionName: Option[String] = None,
	  /** Required. The username. */
		username: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2CreateInspectTemplateRequest(
	  /** Required. The InspectTemplate to create. */
		inspectTemplate: Option[Schema.GooglePrivacyDlpV2InspectTemplate] = None,
	  /** Deprecated. This field has no effect. */
		locationId: Option[String] = None,
	  /** The template id can contain uppercase and lowercase letters, numbers, and hyphens; that is, it must match the regular expression: `[a-zA-Z\d-_]+`. The maximum length is 100 characters. Can be empty to allow the system to generate one. */
		templateId: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2StoredInfoType(
	  /** Current version of the stored info type. */
		currentVersion: Option[Schema.GooglePrivacyDlpV2StoredInfoTypeVersion] = None,
	  /** Pending versions of the stored info type. Empty if no versions are pending. */
		pendingVersions: Option[List[Schema.GooglePrivacyDlpV2StoredInfoTypeVersion]] = None,
	  /** Resource name. */
		name: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2BigQueryTable(
	  /** Name of the table. */
		tableId: Option[String] = None,
	  /** The Google Cloud project ID of the project containing the table. If omitted, project ID is inferred from the API call. */
		projectId: Option[String] = None,
	  /** Dataset ID of the table. */
		datasetId: Option[String] = None
	)
	
	object GooglePrivacyDlpV2BigQueryTableTypes {
		enum TypesEnum extends Enum[TypesEnum] { case BIG_QUERY_TABLE_TYPE_UNSPECIFIED, BIG_QUERY_TABLE_TYPE_TABLE, BIG_QUERY_TABLE_TYPE_EXTERNAL_BIG_LAKE, BIG_QUERY_TABLE_TYPE_SNAPSHOT }
	}
	case class GooglePrivacyDlpV2BigQueryTableTypes(
	  /** A set of BigQuery table types. */
		types: Option[List[Schema.GooglePrivacyDlpV2BigQueryTableTypes.TypesEnum]] = None
	)
	
	case class GooglePrivacyDlpV2ImageTransformations(
	  /** List of transforms to make. */
		transforms: Option[List[Schema.GooglePrivacyDlpV2ImageTransformation]] = None
	)
	
	case class GooglePrivacyDlpV2ReplaceValueConfig(
	  /** Value to replace it with. */
		newValue: Option[Schema.GooglePrivacyDlpV2Value] = None
	)
	
	case class GooglePrivacyDlpV2OtherCloudResourceCollection(
	  /** A collection of regular expressions to match a resource against. */
		includeRegexes: Option[Schema.GooglePrivacyDlpV2OtherCloudResourceRegexes] = None
	)
	
	case class GooglePrivacyDlpV2BigQueryRegex(
	  /** For organizations, if unset, will match all projects. Has no effect for data profile configurations created within a project. */
		projectIdRegex: Option[String] = None,
	  /** If unset, this property matches all tables. */
		tableIdRegex: Option[String] = None,
	  /** If unset, this property matches all datasets. */
		datasetIdRegex: Option[String] = None
	)
	
	object GooglePrivacyDlpV2DlpJob {
		enum TypeEnum extends Enum[TypeEnum] { case DLP_JOB_TYPE_UNSPECIFIED, INSPECT_JOB, RISK_ANALYSIS_JOB }
		enum StateEnum extends Enum[StateEnum] { case JOB_STATE_UNSPECIFIED, PENDING, RUNNING, DONE, CANCELED, FAILED, ACTIVE }
	}
	case class GooglePrivacyDlpV2DlpJob(
	  /** Time when the job was created. */
		createTime: Option[String] = None,
	  /** Time when the job started. */
		startTime: Option[String] = None,
	  /** Time when the job was last modified by the system. */
		lastModified: Option[String] = None,
	  /** The server-assigned name. */
		name: Option[String] = None,
	  /** Results from inspecting a data source. */
		inspectDetails: Option[Schema.GooglePrivacyDlpV2InspectDataSourceDetails] = None,
	  /** If created by a job trigger, the resource name of the trigger that instantiated the job. */
		jobTriggerName: Option[String] = None,
	  /** Results from analyzing risk of a data source. */
		riskDetails: Option[Schema.GooglePrivacyDlpV2AnalyzeDataSourceRiskDetails] = None,
	  /** The type of job. */
		`type`: Option[Schema.GooglePrivacyDlpV2DlpJob.TypeEnum] = None,
	  /** State of a job. */
		state: Option[Schema.GooglePrivacyDlpV2DlpJob.StateEnum] = None,
	  /** A stream of errors encountered running the job. */
		errors: Option[List[Schema.GooglePrivacyDlpV2Error]] = None,
	  /** Events that should occur after the job has completed. */
		actionDetails: Option[List[Schema.GooglePrivacyDlpV2ActionDetails]] = None,
	  /** Time when the job finished. */
		endTime: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2CategoricalStatsHistogramBucket(
	  /** Total number of values in this bucket. */
		bucketSize: Option[String] = None,
	  /** Total number of distinct values in this bucket. */
		bucketValueCount: Option[String] = None,
	  /** Lower bound on the value frequency of the values in this bucket. */
		valueFrequencyLowerBound: Option[String] = None,
	  /** Upper bound on the value frequency of the values in this bucket. */
		valueFrequencyUpperBound: Option[String] = None,
	  /** Sample of value frequencies in this bucket. The total number of values returned per bucket is capped at 20. */
		bucketValues: Option[List[Schema.GooglePrivacyDlpV2ValueFrequency]] = None
	)
	
	case class GooglePrivacyDlpV2DeltaPresenceEstimationConfig(
	  /** Required. Fields considered to be quasi-identifiers. No two fields can have the same tag. */
		quasiIds: Option[List[Schema.GooglePrivacyDlpV2QuasiId]] = None,
	  /** ISO 3166-1 alpha-2 region code to use in the statistical modeling. Set if no column is tagged with a region-specific InfoType (like US_ZIP_5) or a region code. */
		regionCode: Option[String] = None,
	  /** Several auxiliary tables can be used in the analysis. Each custom_tag used to tag a quasi-identifiers field must appear in exactly one field of one auxiliary table. */
		auxiliaryTables: Option[List[Schema.GooglePrivacyDlpV2StatisticalTable]] = None
	)
	
	case class GooglePrivacyDlpV2InspectContentResponse(
	  /** The findings. */
		result: Option[Schema.GooglePrivacyDlpV2InspectResult] = None
	)
	
	case class GooglePrivacyDlpV2BoundingBox(
	  /** Top coordinate of the bounding box. (0,0) is upper left. */
		top: Option[Int] = None,
	  /** Width of the bounding box in pixels. */
		width: Option[Int] = None,
	  /** Height of the bounding box in pixels. */
		height: Option[Int] = None,
	  /** Left coordinate of the bounding box. (0,0) is upper left. */
		left: Option[Int] = None
	)
	
	case class GooglePrivacyDlpV2CloudSqlIamCredential(
	
	)
	
	case class GooglePrivacyDlpV2FieldTransformation(
	  /** Only apply the transformation if the condition evaluates to true for the given `RecordCondition`. The conditions are allowed to reference fields that are not used in the actual transformation. Example Use Cases: - Apply a different bucket transformation to an age column if the zip code column for the same record is within a specific range. - Redact a field if the date of birth field is greater than 85. */
		condition: Option[Schema.GooglePrivacyDlpV2RecordCondition] = None,
	  /** Required. Input field(s) to apply the transformation to. When you have columns that reference their position within a list, omit the index from the FieldId. FieldId name matching ignores the index. For example, instead of "contact.nums[0].type", use "contact.nums.type". */
		fields: Option[List[Schema.GooglePrivacyDlpV2FieldId]] = None,
	  /** Treat the contents of the field as free text, and selectively transform content that matches an `InfoType`. */
		infoTypeTransformations: Option[Schema.GooglePrivacyDlpV2InfoTypeTransformations] = None,
	  /** Apply the transformation to the entire field. */
		primitiveTransformation: Option[Schema.GooglePrivacyDlpV2PrimitiveTransformation] = None
	)
	
	case class GooglePrivacyDlpV2InfoTypeTransformation(
	  /** Required. Primitive transformation to apply to the infoType. */
		primitiveTransformation: Option[Schema.GooglePrivacyDlpV2PrimitiveTransformation] = None,
	  /** InfoTypes to apply the transformation to. An empty list will cause this transformation to apply to all findings that correspond to infoTypes that were requested in `InspectConfig`. */
		infoTypes: Option[List[Schema.GooglePrivacyDlpV2InfoType]] = None
	)
	
	case class GooglePrivacyDlpV2ExcludeByHotword(
	  /** Regular expression pattern defining what qualifies as a hotword. */
		hotwordRegex: Option[Schema.GooglePrivacyDlpV2Regex] = None,
	  /** Range of characters within which the entire hotword must reside. The total length of the window cannot exceed 1000 characters. The windowBefore property in proximity should be set to 1 if the hotword needs to be included in a column header. */
		proximity: Option[Schema.GooglePrivacyDlpV2Proximity] = None
	)
	
	case class GooglePrivacyDlpV2InspectDataSourceDetails(
	  /** The configuration used for this job. */
		requestedOptions: Option[Schema.GooglePrivacyDlpV2RequestedOptions] = None,
	  /** A summary of the outcome of this inspection job. */
		result: Option[Schema.GooglePrivacyDlpV2Result] = None
	)
	
	case class GooglePrivacyDlpV2StoredType(
	  /** Timestamp indicating when the version of the `StoredInfoType` used for inspection was created. Output-only field, populated by the system. */
		createTime: Option[String] = None,
	  /** Resource name of the requested `StoredInfoType`, for example `organizations/433245324/storedInfoTypes/432452342` or `projects/project-id/storedInfoTypes/432452342`. */
		name: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2CancelDlpJobRequest(
	
	)
	
	case class GooglePrivacyDlpV2StorageMetadataLabel(
	  /** Label name. */
		key: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2DatabaseResourceReference(
	  /** Required. Name of a database resource, for example, a table within the database. */
		databaseResource: Option[String] = None,
	  /** Required. Name of a database within the instance. */
		database: Option[String] = None,
	  /** Required. The instance where this resource is located. For example: Cloud SQL instance ID. */
		instance: Option[String] = None,
	  /** Required. If within a project-level config, then this must match the config's project ID. */
		projectId: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2TransformationDetailsStorageConfig(
	  /** The BigQuery table in which to store the output. This may be an existing table or in a new table in an existing dataset. If table_id is not set a new one will be generated for you with the following format: dlp_googleapis_transformation_details_yyyy_mm_dd_[dlp_job_id]. Pacific time zone will be used for generating the date details. */
		table: Option[Schema.GooglePrivacyDlpV2BigQueryTable] = None
	)
	
	case class GooglePrivacyDlpV2AwsAccount(
	  /** Required. AWS account ID. */
		accountId: Option[String] = None
	)
	
	object GooglePrivacyDlpV2DateTime {
		enum DayOfWeekEnum extends Enum[DayOfWeekEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class GooglePrivacyDlpV2DateTime(
	  /** Time zone */
		timeZone: Option[Schema.GooglePrivacyDlpV2TimeZone] = None,
	  /** Time of day */
		time: Option[Schema.GoogleTypeTimeOfDay] = None,
	  /** Day of week */
		dayOfWeek: Option[Schema.GooglePrivacyDlpV2DateTime.DayOfWeekEnum] = None,
	  /** One or more of the following must be set. Must be a valid date or time value. */
		date: Option[Schema.GoogleTypeDate] = None
	)
	
	object GooglePrivacyDlpV2TransformationLocation {
		enum ContainerTypeEnum extends Enum[ContainerTypeEnum] { case TRANSFORM_UNKNOWN_CONTAINER, TRANSFORM_BODY, TRANSFORM_METADATA, TRANSFORM_TABLE }
	}
	case class GooglePrivacyDlpV2TransformationLocation(
	  /** For infotype transformations, link to the corresponding findings ID so that location information does not need to be duplicated. Each findings ID correlates to an entry in the findings output table, this table only gets created when users specify to save findings (add the save findings action to the request). */
		findingId: Option[String] = None,
	  /** For record transformations, provide a field and container information. */
		recordTransformation: Option[Schema.GooglePrivacyDlpV2RecordTransformation] = None,
	  /** Information about the functionality of the container where this finding occurred, if available. */
		containerType: Option[Schema.GooglePrivacyDlpV2TransformationLocation.ContainerTypeEnum] = None
	)
	
	case class GooglePrivacyDlpV2ListProjectDataProfilesResponse(
	  /** The next page token. */
		nextPageToken: Option[String] = None,
	  /** List of data profiles. */
		projectDataProfiles: Option[List[Schema.GooglePrivacyDlpV2ProjectDataProfile]] = None
	)
	
	case class GooglePrivacyDlpV2DiscoveryOtherCloudConditions(
	  /** Amazon S3 bucket conditions. */
		amazonS3BucketConditions: Option[Schema.GooglePrivacyDlpV2AmazonS3BucketConditions] = None,
	  /** Minimum age a resource must be before Cloud DLP can profile it. Value must be 1 hour or greater. */
		minAge: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2AllOtherDatabaseResources(
	
	)
	
	case class GooglePrivacyDlpV2CharacterMaskConfig(
	  /** Mask characters in reverse order. For example, if `masking_character` is `0`, `number_to_mask` is `14`, and `reverse_order` is `false`, then the input string `1234-5678-9012-3456` is masked as `00000000000000-3456`. If `masking_character` is `&#42;`, `number_to_mask` is `3`, and `reverse_order` is `true`, then the string `12345` is masked as `12&#42;&#42;&#42;`. */
		reverseOrder: Option[Boolean] = None,
	  /** Number of characters to mask. If not set, all matching chars will be masked. Skipped characters do not count towards this tally. If `number_to_mask` is negative, this denotes inverse masking. Cloud DLP masks all but a number of characters. For example, suppose you have the following values: - `masking_character` is `&#42;` - `number_to_mask` is `-4` - `reverse_order` is `false` - `CharsToIgnore` includes `-` - Input string is `1234-5678-9012-3456` The resulting de-identified string is `&#42;&#42;&#42;&#42;-&#42;&#42;&#42;&#42;-&#42;&#42;&#42;&#42;-3456`. Cloud DLP masks all but the last four characters. If `reverse_order` is `true`, all but the first four characters are masked as `1234-&#42;&#42;&#42;&#42;-&#42;&#42;&#42;&#42;-&#42;&#42;&#42;&#42;`. */
		numberToMask: Option[Int] = None,
	  /** Character to use to mask the sensitive values—for example, `&#42;` for an alphabetic string such as a name, or `0` for a numeric string such as ZIP code or credit card number. This string must have a length of 1. If not supplied, this value defaults to `&#42;` for strings, and `0` for digits. */
		maskingCharacter: Option[String] = None,
	  /** When masking a string, items in this list will be skipped when replacing characters. For example, if the input string is `555-555-5555` and you instruct Cloud DLP to skip `-` and mask 5 characters with `&#42;`, Cloud DLP returns `&#42;&#42;&#42;-&#42;&#42;5-5555`. */
		charactersToIgnore: Option[List[Schema.GooglePrivacyDlpV2CharsToIgnore]] = None
	)
	
	case class GooglePrivacyDlpV2InfoTypeTransformations(
	  /** Required. Transformation for each infoType. Cannot specify more than one for a given infoType. */
		transformations: Option[List[Schema.GooglePrivacyDlpV2InfoTypeTransformation]] = None
	)
	
	case class GooglePrivacyDlpV2BigQueryKey(
	  /** Row number inferred at the time the table was scanned. This value is nondeterministic, cannot be queried, and may be null for inspection jobs. To locate findings within a table, specify `inspect_job.storage_config.big_query_options.identifying_fields` in `CreateDlpJobRequest`. */
		rowNumber: Option[String] = None,
	  /** Complete BigQuery table reference. */
		tableReference: Option[Schema.GooglePrivacyDlpV2BigQueryTable] = None
	)
	
	case class GooglePrivacyDlpV2DataProfileLocation(
	  /** The ID of the folder within an organization to scan. */
		folderId: Option[String] = None,
	  /** The ID of an organization to scan. */
		organizationId: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2AllOtherResources(
	
	)
	
	case class GooglePrivacyDlpV2InfoTypeSummary(
	  /** The infoType. */
		infoType: Option[Schema.GooglePrivacyDlpV2InfoType] = None,
	  /** Not populated for predicted infotypes. */
		estimatedPrevalence: Option[Int] = None
	)
	
	object GooglePrivacyDlpV2DiscoveryGenerationCadence {
		enum RefreshFrequencyEnum extends Enum[RefreshFrequencyEnum] { case UPDATE_FREQUENCY_UNSPECIFIED, UPDATE_FREQUENCY_NEVER, UPDATE_FREQUENCY_DAILY, UPDATE_FREQUENCY_MONTHLY }
	}
	case class GooglePrivacyDlpV2DiscoveryGenerationCadence(
	  /** Governs when to update data profiles when a schema is modified. */
		schemaModifiedCadence: Option[Schema.GooglePrivacyDlpV2DiscoverySchemaModifiedCadence] = None,
	  /** Governs when to update data profiles when a table is modified. */
		tableModifiedCadence: Option[Schema.GooglePrivacyDlpV2DiscoveryTableModifiedCadence] = None,
	  /** Frequency at which profiles should be updated, regardless of whether the underlying resource has changed. Defaults to never. */
		refreshFrequency: Option[Schema.GooglePrivacyDlpV2DiscoveryGenerationCadence.RefreshFrequencyEnum] = None,
	  /** Governs when to update data profiles when the inspection rules defined by the `InspectTemplate` change. If not set, changing the template will not cause a data profile to update. */
		inspectTemplateModifiedCadence: Option[Schema.GooglePrivacyDlpV2DiscoveryInspectTemplateModifiedCadence] = None
	)
	
	object GooglePrivacyDlpV2FileStoreDataProfile {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RUNNING, DONE }
		enum ResourceVisibilityEnum extends Enum[ResourceVisibilityEnum] { case RESOURCE_VISIBILITY_UNSPECIFIED, RESOURCE_VISIBILITY_PUBLIC, RESOURCE_VISIBILITY_INCONCLUSIVE, RESOURCE_VISIBILITY_RESTRICTED }
	}
	case class GooglePrivacyDlpV2FileStoreDataProfile(
	  /** The last time the profile was generated. */
		profileLastGenerated: Option[String] = None,
	  /** FileClusterSummary per each cluster. */
		fileClusterSummaries: Option[List[Schema.GooglePrivacyDlpV2FileClusterSummary]] = None,
	  /** The location type of the bucket (region, dual-region, multi-region, etc). If dual-region, expect data_storage_locations to be populated. */
		locationType: Option[String] = None,
	  /** The file store path. &#42; Cloud Storage: `gs://{bucket}` &#42; Amazon S3: `s3://{bucket}` */
		fileStorePath: Option[String] = None,
	  /** Success or error status from the most recent profile generation attempt. May be empty if the profile is still being generated. */
		profileStatus: Option[Schema.GooglePrivacyDlpV2ProfileStatus] = None,
	  /** The name of the profile. */
		name: Option[String] = None,
	  /** The sensitivity score of this resource. */
		sensitivityScore: Option[Schema.GooglePrivacyDlpV2SensitivityScore] = None,
	  /** The snapshot of the configurations used to generate the profile. */
		configSnapshot: Option[Schema.GooglePrivacyDlpV2DataProfileConfigSnapshot] = None,
	  /** The resource name of the project data profile for this file store. */
		projectDataProfile: Option[String] = None,
	  /** State of a profile. */
		state: Option[Schema.GooglePrivacyDlpV2FileStoreDataProfile.StateEnum] = None,
	  /** The labels applied to the resource at the time the profile was generated. */
		resourceLabels: Option[Map[String, String]] = None,
	  /** The file store does not have any files. */
		fileStoreIsEmpty: Option[Boolean] = None,
	  /** How broadly a resource has been shared. */
		resourceVisibility: Option[Schema.GooglePrivacyDlpV2FileStoreDataProfile.ResourceVisibilityEnum] = None,
	  /** The Google Cloud project ID that owns the resource. For Amazon S3 buckets, this is the AWS Account Id. */
		projectId: Option[String] = None,
	  /** InfoTypes detected in this file store. */
		fileStoreInfoTypeSummaries: Option[List[Schema.GooglePrivacyDlpV2FileStoreInfoTypeSummary]] = None,
	  /** For resources that have multiple storage locations, these are those regions. For Cloud Storage this is the list of regions chosen for dual-region storage. `file_store_location` will normally be the corresponding multi-region for the list of individual locations. The first region is always picked as the processing and storage location for the data profile. */
		dataStorageLocations: Option[List[String]] = None,
	  /** The resource name of the resource profiled. https://cloud.google.com/apis/design/resource_names#full_resource_name Example format of an S3 bucket full resource name: `//cloudasset.googleapis.com/organizations/{org_id}/otherCloudConnections/aws/arn:aws:s3:::{bucket_name}` */
		fullResource: Option[String] = None,
	  /** The location of the file store. &#42; Cloud Storage: https://cloud.google.com/storage/docs/locations#available-locations &#42; Amazon S3: https://docs.aws.amazon.com/general/latest/gr/rande.html#regional-endpoints */
		fileStoreLocation: Option[String] = None,
	  /** The time the file store was last modified. */
		lastModifiedTime: Option[String] = None,
	  /** The time the file store was first created. */
		createTime: Option[String] = None,
	  /** The data risk level of this resource. */
		dataRiskLevel: Option[Schema.GooglePrivacyDlpV2DataRiskLevel] = None,
	  /** Attributes of the resource being profiled. Currently used attributes: &#42; customer_managed_encryption: boolean - true: the resource is encrypted with a customer-managed key. - false: the resource is encrypted with a provider-managed key. */
		resourceAttributes: Option[Map[String, Schema.GooglePrivacyDlpV2Value]] = None,
	  /** The resource type that was profiled. */
		dataSourceType: Option[Schema.GooglePrivacyDlpV2DataSourceType] = None
	)
	
	case class GooglePrivacyDlpV2FileStoreInfoTypeSummary(
	  /** The InfoType seen. */
		infoType: Option[Schema.GooglePrivacyDlpV2InfoType] = None
	)
	
	object GooglePrivacyDlpV2DiscoveryCloudSqlGenerationCadence {
		enum RefreshFrequencyEnum extends Enum[RefreshFrequencyEnum] { case UPDATE_FREQUENCY_UNSPECIFIED, UPDATE_FREQUENCY_NEVER, UPDATE_FREQUENCY_DAILY, UPDATE_FREQUENCY_MONTHLY }
	}
	case class GooglePrivacyDlpV2DiscoveryCloudSqlGenerationCadence(
	  /** When to reprofile if the schema has changed. */
		schemaModifiedCadence: Option[Schema.GooglePrivacyDlpV2SchemaModifiedCadence] = None,
	  /** Governs when to update data profiles when the inspection rules defined by the `InspectTemplate` change. If not set, changing the template will not cause a data profile to update. */
		inspectTemplateModifiedCadence: Option[Schema.GooglePrivacyDlpV2DiscoveryInspectTemplateModifiedCadence] = None,
	  /** Data changes (non-schema changes) in Cloud SQL tables can't trigger reprofiling. If you set this field, profiles are refreshed at this frequency regardless of whether the underlying tables have changed. Defaults to never. */
		refreshFrequency: Option[Schema.GooglePrivacyDlpV2DiscoveryCloudSqlGenerationCadence.RefreshFrequencyEnum] = None
	)
	
	case class GooglePrivacyDlpV2JobNotificationEmails(
	
	)
	
	case class GooglePrivacyDlpV2EntityId(
	  /** Composite key indicating which field contains the entity identifier. */
		field: Option[Schema.GooglePrivacyDlpV2FieldId] = None
	)
	
	case class GooglePrivacyDlpV2ImageTransformation(
	  /** Apply transformation to all text that doesn't match an infoType. Only one instance is allowed within the ImageTransformations message. */
		allText: Option[Schema.GooglePrivacyDlpV2AllText] = None,
	  /** Apply transformation to the selected info_types. */
		selectedInfoTypes: Option[Schema.GooglePrivacyDlpV2SelectedInfoTypes] = None,
	  /** The color to use when redacting content from an image. If not specified, the default is black. */
		redactionColor: Option[Schema.GooglePrivacyDlpV2Color] = None,
	  /** Apply transformation to all findings not specified in other ImageTransformation's selected_info_types. Only one instance is allowed within the ImageTransformations message. */
		allInfoTypes: Option[Schema.GooglePrivacyDlpV2AllInfoTypes] = None
	)
	
	case class GooglePrivacyDlpV2CryptoDeterministicConfig(
	  /** The custom info type to annotate the surrogate with. This annotation will be applied to the surrogate by prefixing it with the name of the custom info type followed by the number of characters comprising the surrogate. The following scheme defines the format: {info type name}({surrogate character count}):{surrogate} For example, if the name of custom info type is 'MY_TOKEN_INFO_TYPE' and the surrogate is 'abc', the full replacement value will be: 'MY_TOKEN_INFO_TYPE(3):abc' This annotation identifies the surrogate when inspecting content using the custom info type 'Surrogate'. This facilitates reversal of the surrogate when it occurs in free text. Note: For record transformations where the entire cell in a table is being transformed, surrogates are not mandatory. Surrogates are used to denote the location of the token and are necessary for re-identification in free form text. In order for inspection to work properly, the name of this info type must not occur naturally anywhere in your data; otherwise, inspection may either - reverse a surrogate that does not correspond to an actual identifier - be unable to parse the surrogate and result in an error Therefore, choose your custom info type name carefully after considering what your data looks like. One way to select a name that has a high chance of yielding reliable detection is to include one or more unicode characters that are highly improbable to exist in your data. For example, assuming your data is entered from a regular ASCII keyboard, the symbol with the hex code point 29DD might be used like so: ⧝MY_TOKEN_TYPE. */
		surrogateInfoType: Option[Schema.GooglePrivacyDlpV2InfoType] = None,
	  /** A context may be used for higher security and maintaining referential integrity such that the same identifier in two different contexts will be given a distinct surrogate. The context is appended to plaintext value being encrypted. On decryption the provided context is validated against the value used during encryption. If a context was provided during encryption, same context must be provided during decryption as well. If the context is not set, plaintext would be used as is for encryption. If the context is set but: 1. there is no record present when transforming a given value or 2. the field is not present when transforming a given value, plaintext would be used as is for encryption. Note that case (1) is expected when an `InfoTypeTransformation` is applied to both structured and unstructured `ContentItem`s. */
		context: Option[Schema.GooglePrivacyDlpV2FieldId] = None,
	  /** The key used by the encryption function. For deterministic encryption using AES-SIV, the provided key is internally expanded to 64 bytes prior to use. */
		cryptoKey: Option[Schema.GooglePrivacyDlpV2CryptoKey] = None
	)
	
	case class GooglePrivacyDlpV2TimespanConfig(
	  /** Specification of the field containing the timestamp of scanned items. Used for data sources like Datastore and BigQuery. &#42;&#42;For BigQuery&#42;&#42; If this value is not specified and the table was modified between the given start and end times, the entire table will be scanned. If this value is specified, then rows are filtered based on the given start and end times. Rows with a `NULL` value in the provided BigQuery column are skipped. Valid data types of the provided BigQuery column are: `INTEGER`, `DATE`, `TIMESTAMP`, and `DATETIME`. If your BigQuery table is [partitioned at ingestion time](https://cloud.google.com/bigquery/docs/partitioned-tables#ingestion_time), you can use any of the following pseudo-columns as your timestamp field. When used with Cloud DLP, these pseudo-column names are case sensitive. - `_PARTITIONTIME` - `_PARTITIONDATE` - `_PARTITION_LOAD_TIME` &#42;&#42;For Datastore&#42;&#42; If this value is specified, then entities are filtered based on the given start and end times. If an entity does not contain the provided timestamp property or contains empty or invalid values, then it is included. Valid data types of the provided timestamp property are: `TIMESTAMP`. See the [known issue](https://cloud.google.com/sensitive-data-protection/docs/known-issues#bq-timespan) related to this operation. */
		timestampField: Option[Schema.GooglePrivacyDlpV2FieldId] = None,
	  /** Exclude files, tables, or rows older than this value. If not set, no lower time limit is applied. */
		startTime: Option[String] = None,
	  /** When the job is started by a JobTrigger we will automatically figure out a valid start_time to avoid scanning files that have not been modified since the last time the JobTrigger executed. This will be based on the time of the execution of the last run of the JobTrigger or the timespan end_time used in the last run of the JobTrigger. &#42;&#42;For BigQuery&#42;&#42; Inspect jobs triggered by automatic population will scan data that is at least three hours old when the job starts. This is because streaming buffer rows are not read during inspection and reading up to the current timestamp will result in skipped rows. See the [known issue](https://cloud.google.com/sensitive-data-protection/docs/known-issues#recently-streamed-data) related to this operation. */
		enableAutoPopulationOfTimespanConfig: Option[Boolean] = None,
	  /** Exclude files, tables, or rows newer than this value. If not set, no upper time limit is applied. */
		endTime: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2FileClusterSummary(
	  /** True if no files exist in this cluster. If the bucket had more files than could be listed, this will be false even if no files for this cluster were seen and file_extensions_seen is empty. */
		noFilesExist: Option[Boolean] = None,
	  /** The file cluster type. */
		fileClusterType: Option[Schema.GooglePrivacyDlpV2FileClusterType] = None,
	  /** The sensitivity score of this cluster. The score will be SENSITIVITY_LOW if nothing has been scanned. */
		sensitivityScore: Option[Schema.GooglePrivacyDlpV2SensitivityScore] = None,
	  /** A sample of file types scanned in this cluster. Empty if no files were scanned. File extensions can be derived from the file name or the file content. */
		fileExtensionsScanned: Option[List[Schema.GooglePrivacyDlpV2FileExtensionInfo]] = None,
	  /** A list of errors detected while scanning this cluster. The list is truncated to 10 per cluster. */
		errors: Option[List[Schema.GooglePrivacyDlpV2Error]] = None,
	  /** A sample of file types seen in this cluster. Empty if no files were seen. File extensions can be derived from the file name or the file content. */
		fileExtensionsSeen: Option[List[Schema.GooglePrivacyDlpV2FileExtensionInfo]] = None,
	  /** InfoTypes detected in this cluster. */
		fileStoreInfoTypeSummaries: Option[List[Schema.GooglePrivacyDlpV2FileStoreInfoTypeSummary]] = None,
	  /** The data risk level of this cluster. RISK_LOW if nothing has been scanned. */
		dataRiskLevel: Option[Schema.GooglePrivacyDlpV2DataRiskLevel] = None
	)
	
	case class GooglePrivacyDlpV2ProjectDataProfile(
	  /** The data risk level of this project. */
		dataRiskLevel: Option[Schema.GooglePrivacyDlpV2DataRiskLevel] = None,
	  /** Success or error status of the last attempt to profile the project. */
		profileStatus: Option[Schema.GooglePrivacyDlpV2ProfileStatus] = None,
	  /** The resource name of the profile. */
		name: Option[String] = None,
	  /** The number of table data profiles generated for this project. */
		tableDataProfileCount: Option[String] = None,
	  /** The sensitivity score of this project. */
		sensitivityScore: Option[Schema.GooglePrivacyDlpV2SensitivityScore] = None,
	  /** The number of file store data profiles generated for this project. */
		fileStoreDataProfileCount: Option[String] = None,
	  /** The last time the profile was generated. */
		profileLastGenerated: Option[String] = None,
	  /** Project ID or account that was profiled. */
		projectId: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2DiscoveryCloudStorageFilter(
	  /** Optional. A specific set of buckets for this filter to apply to. */
		collection: Option[Schema.GooglePrivacyDlpV2FileStoreCollection] = None,
	  /** Optional. Catch-all. This should always be the last target in the list because anything above it will apply first. Should only appear once in a configuration. If none is specified, a default one will be added automatically. */
		others: Option[Schema.GooglePrivacyDlpV2AllOtherResources] = None,
	  /** Optional. The bucket to scan. Targets including this can only include one target (the target with this bucket). This enables profiling the contents of a single bucket, while the other options allow for easy profiling of many bucets within a project or an organization. */
		cloudStorageResourceReference: Option[Schema.GooglePrivacyDlpV2CloudStorageResourceReference] = None
	)
	
	case class GooglePrivacyDlpV2Conditions(
	  /** A collection of conditions. */
		conditions: Option[List[Schema.GooglePrivacyDlpV2Condition]] = None
	)
	
	case class GooglePrivacyDlpV2CloudSqlDiscoveryTarget(
	  /** In addition to matching the filter, these conditions must be true before a profile is generated. */
		conditions: Option[Schema.GooglePrivacyDlpV2DiscoveryCloudSqlConditions] = None,
	  /** How often and when to update profiles. New tables that match both the filter and conditions are scanned as quickly as possible depending on system capacity. */
		generationCadence: Option[Schema.GooglePrivacyDlpV2DiscoveryCloudSqlGenerationCadence] = None,
	  /** Required. The tables the discovery cadence applies to. The first target with a matching filter will be the one to apply to a table. */
		filter: Option[Schema.GooglePrivacyDlpV2DiscoveryCloudSqlFilter] = None,
	  /** Disable profiling for database resources that match this filter. */
		disabled: Option[Schema.GooglePrivacyDlpV2Disabled] = None
	)
	
	case class GooglePrivacyDlpV2NumericalStatsConfig(
	  /** Field to compute numerical stats on. Supported types are integer, float, date, datetime, timestamp, time. */
		field: Option[Schema.GooglePrivacyDlpV2FieldId] = None
	)
	
	object GooglePrivacyDlpV2TransformationDescription {
		enum TypeEnum extends Enum[TypeEnum] { case TRANSFORMATION_TYPE_UNSPECIFIED, RECORD_SUPPRESSION, REPLACE_VALUE, REPLACE_DICTIONARY, REDACT, CHARACTER_MASK, CRYPTO_REPLACE_FFX_FPE, FIXED_SIZE_BUCKETING, BUCKETING, REPLACE_WITH_INFO_TYPE, TIME_PART, CRYPTO_HASH, DATE_SHIFT, CRYPTO_DETERMINISTIC_CONFIG, REDACT_IMAGE }
	}
	case class GooglePrivacyDlpV2TransformationDescription(
	  /** Set if the transformation was limited to a specific `InfoType`. */
		infoType: Option[Schema.GooglePrivacyDlpV2InfoType] = None,
	  /** The transformation type. */
		`type`: Option[Schema.GooglePrivacyDlpV2TransformationDescription.TypeEnum] = None,
	  /** A human-readable string representation of the `RecordCondition` corresponding to this transformation. Set if a `RecordCondition` was used to determine whether or not to apply this transformation. Examples: &#42; (age_field > 85) &#42; (age_field <= 18) &#42; (zip_field exists) &#42; (zip_field == 01234) && (city_field != "Springville") &#42; (zip_field == 01234) && (age_field <= 18) && (city_field exists) */
		condition: Option[String] = None,
	  /** A description of the transformation. This is empty for a RECORD_SUPPRESSION, or is the output of calling toString() on the `PrimitiveTransformation` protocol buffer message for any other type of transformation. */
		description: Option[String] = None
	)
	
	object GooglePrivacyDlpV2SummaryResult {
		enum CodeEnum extends Enum[CodeEnum] { case TRANSFORMATION_RESULT_CODE_UNSPECIFIED, SUCCESS, ERROR }
	}
	case class GooglePrivacyDlpV2SummaryResult(
	  /** Number of transformations counted by this result. */
		count: Option[String] = None,
	  /** A place for warnings or errors to show up if a transformation didn't work as expected. */
		details: Option[String] = None,
	  /** Outcome of the transformation. */
		code: Option[Schema.GooglePrivacyDlpV2SummaryResult.CodeEnum] = None
	)
	
	case class GooglePrivacyDlpV2LeaveUntransformed(
	
	)
	
	case class GooglePrivacyDlpV2DocumentLocation(
	  /** Offset of the line, from the beginning of the file, where the finding is located. */
		fileOffset: Option[String] = None
	)
	
	object GooglePrivacyDlpV2DiscoveryTableModifiedCadence {
		enum TypesEnum extends Enum[TypesEnum] { case TABLE_MODIFICATION_UNSPECIFIED, TABLE_MODIFIED_TIMESTAMP }
		enum FrequencyEnum extends Enum[FrequencyEnum] { case UPDATE_FREQUENCY_UNSPECIFIED, UPDATE_FREQUENCY_NEVER, UPDATE_FREQUENCY_DAILY, UPDATE_FREQUENCY_MONTHLY }
	}
	case class GooglePrivacyDlpV2DiscoveryTableModifiedCadence(
	  /** The type of events to consider when deciding if the table has been modified and should have the profile updated. Defaults to MODIFIED_TIMESTAMP. */
		types: Option[List[Schema.GooglePrivacyDlpV2DiscoveryTableModifiedCadence.TypesEnum]] = None,
	  /** How frequently data profiles can be updated when tables are modified. Defaults to never. */
		frequency: Option[Schema.GooglePrivacyDlpV2DiscoveryTableModifiedCadence.FrequencyEnum] = None
	)
	
	case class GooglePrivacyDlpV2StoredInfoTypeStats(
	  /** StoredInfoType where findings are defined by a dictionary of phrases. */
		largeCustomDictionary: Option[Schema.GooglePrivacyDlpV2LargeCustomDictionaryStats] = None
	)
	
	case class GooglePrivacyDlpV2OtherCloudSingleResourceReference(
	  /** Amazon S3 bucket. */
		amazonS3Bucket: Option[Schema.GooglePrivacyDlpV2AmazonS3Bucket] = None
	)
	
	case class GooglePrivacyDlpV2TransformationConfig(
	  /** Structured de-identify template. If this template is specified, it will serve as the de-identify template for structured content such as delimited files and tables. If this template is not set but the `deidentify_template` is set, then `deidentify_template` will also apply to the structured content. If neither template is set, a default `ReplaceWithInfoTypeConfig` will be used to de-identify structured content. */
		structuredDeidentifyTemplate: Option[String] = None,
	  /** Image redact template. If this template is specified, it will serve as the de-identify template for images. If this template is not set, all findings in the image will be redacted with a black box. */
		imageRedactTemplate: Option[String] = None,
	  /** De-identify template. If this template is specified, it will serve as the default de-identify template. This template cannot contain `record_transformations` since it can be used for unstructured content such as free-form text files. If this template is not set, a default `ReplaceWithInfoTypeConfig` will be used to de-identify unstructured content. */
		deidentifyTemplate: Option[String] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GooglePrivacyDlpV2QuoteInfo(
	  /** The date time indicated by the quote. */
		dateTime: Option[Schema.GooglePrivacyDlpV2DateTime] = None
	)
	
	case class GooglePrivacyDlpV2DatabaseResourceCollection(
	  /** A collection of regular expressions to match a database resource against. */
		includeRegexes: Option[Schema.GooglePrivacyDlpV2DatabaseResourceRegexes] = None
	)
	
	object GooglePrivacyDlpV2JobTrigger {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNSPECIFIED, HEALTHY, PAUSED, CANCELLED }
	}
	case class GooglePrivacyDlpV2JobTrigger(
	  /** Unique resource name for the triggeredJob, assigned by the service when the triggeredJob is created, for example `projects/dlp-test-project/jobTriggers/53234423`. */
		name: Option[String] = None,
	  /** Output only. The creation timestamp of a triggeredJob. */
		createTime: Option[String] = None,
	  /** For inspect jobs, a snapshot of the configuration. */
		inspectJob: Option[Schema.GooglePrivacyDlpV2InspectJobConfig] = None,
	  /** User provided description (max 256 chars) */
		description: Option[String] = None,
	  /** Required. A status for this trigger. */
		status: Option[Schema.GooglePrivacyDlpV2JobTrigger.StatusEnum] = None,
	  /** Output only. The last update timestamp of a triggeredJob. */
		updateTime: Option[String] = None,
	  /** Output only. A stream of errors encountered when the trigger was activated. Repeated errors may result in the JobTrigger automatically being paused. Will return the last 100 errors. Whenever the JobTrigger is modified this list will be cleared. */
		errors: Option[List[Schema.GooglePrivacyDlpV2Error]] = None,
	  /** Output only. The timestamp of the last time this trigger executed. */
		lastRunTime: Option[String] = None,
	  /** Display name (max 100 chars) */
		displayName: Option[String] = None,
	  /** A list of triggers which will be OR'ed together. Only one in the list needs to trigger for a job to be started. The list may contain only a single Schedule trigger and must have at least one object. */
		triggers: Option[List[Schema.GooglePrivacyDlpV2Trigger]] = None
	)
	
	object GooglePrivacyDlpV2SchemaModifiedCadence {
		enum FrequencyEnum extends Enum[FrequencyEnum] { case UPDATE_FREQUENCY_UNSPECIFIED, UPDATE_FREQUENCY_NEVER, UPDATE_FREQUENCY_DAILY, UPDATE_FREQUENCY_MONTHLY }
		enum TypesEnum extends Enum[TypesEnum] { case SQL_SCHEMA_MODIFICATION_UNSPECIFIED, NEW_COLUMNS, REMOVED_COLUMNS }
	}
	case class GooglePrivacyDlpV2SchemaModifiedCadence(
	  /** Frequency to regenerate data profiles when the schema is modified. Defaults to monthly. */
		frequency: Option[Schema.GooglePrivacyDlpV2SchemaModifiedCadence.FrequencyEnum] = None,
	  /** The types of schema modifications to consider. Defaults to NEW_COLUMNS. */
		types: Option[List[Schema.GooglePrivacyDlpV2SchemaModifiedCadence.TypesEnum]] = None
	)
	
	case class GooglePrivacyDlpV2FileStoreRegex(
	  /** Optional. Regex for Cloud Storage. */
		cloudStorageRegex: Option[Schema.GooglePrivacyDlpV2CloudStorageRegex] = None
	)
	
	object GooglePrivacyDlpV2DiscoveryOtherCloudGenerationCadence {
		enum RefreshFrequencyEnum extends Enum[RefreshFrequencyEnum] { case UPDATE_FREQUENCY_UNSPECIFIED, UPDATE_FREQUENCY_NEVER, UPDATE_FREQUENCY_DAILY, UPDATE_FREQUENCY_MONTHLY }
	}
	case class GooglePrivacyDlpV2DiscoveryOtherCloudGenerationCadence(
	  /** Optional. Frequency to update profiles regardless of whether the underlying resource has changes. Defaults to never. */
		refreshFrequency: Option[Schema.GooglePrivacyDlpV2DiscoveryOtherCloudGenerationCadence.RefreshFrequencyEnum] = None,
	  /** Optional. Governs when to update data profiles when the inspection rules defined by the `InspectTemplate` change. If not set, changing the template will not cause a data profile to update. */
		inspectTemplateModifiedCadence: Option[Schema.GooglePrivacyDlpV2DiscoveryInspectTemplateModifiedCadence] = None
	)
	
	case class GooglePrivacyDlpV2TaggedField(
	  /** A column can be tagged with a InfoType to use the relevant public dataset as a statistical model of population, if available. We currently support US ZIP codes, region codes, ages and genders. To programmatically obtain the list of supported InfoTypes, use ListInfoTypes with the supported_by=RISK_ANALYSIS filter. */
		infoType: Option[Schema.GooglePrivacyDlpV2InfoType] = None,
	  /** If no semantic tag is indicated, we infer the statistical model from the distribution of values in the input data */
		inferred: Option[Schema.GoogleProtobufEmpty] = None,
	  /** A column can be tagged with a custom tag. In this case, the user must indicate an auxiliary table that contains statistical information on the possible values of this column (below). */
		customTag: Option[String] = None,
	  /** Required. Identifies the column. */
		field: Option[Schema.GooglePrivacyDlpV2FieldId] = None
	)
	
	case class GooglePrivacyDlpV2KMapEstimationQuasiIdValues(
	  /** The quasi-identifier values. */
		quasiIdsValues: Option[List[Schema.GooglePrivacyDlpV2Value]] = None,
	  /** The estimated anonymity for these quasi-identifier values. */
		estimatedAnonymity: Option[String] = None
	)
	
	object GooglePrivacyDlpV2Expressions {
		enum LogicalOperatorEnum extends Enum[LogicalOperatorEnum] { case LOGICAL_OPERATOR_UNSPECIFIED, AND }
	}
	case class GooglePrivacyDlpV2Expressions(
	  /** The operator to apply to the result of conditions. Default and currently only supported value is `AND`. */
		logicalOperator: Option[Schema.GooglePrivacyDlpV2Expressions.LogicalOperatorEnum] = None,
	  /** Conditions to apply to the expression. */
		conditions: Option[Schema.GooglePrivacyDlpV2Conditions] = None
	)
	
	case class GooglePrivacyDlpV2HybridContentItem(
	  /** The item to inspect. */
		item: Option[Schema.GooglePrivacyDlpV2ContentItem] = None,
	  /** Supplementary information that will be added to each finding. */
		findingDetails: Option[Schema.GooglePrivacyDlpV2HybridFindingDetails] = None
	)
	
	case class GooglePrivacyDlpV2ImageLocation(
	  /** Bounding boxes locating the pixels within the image containing the finding. */
		boundingBoxes: Option[List[Schema.GooglePrivacyDlpV2BoundingBox]] = None
	)
	
	case class GooglePrivacyDlpV2SurrogateType(
	
	)
	
	case class GooglePrivacyDlpV2AllText(
	
	)
	
	object GooglePrivacyDlpV2ColumnDataProfile {
		enum EstimatedUniquenessScoreEnum extends Enum[EstimatedUniquenessScoreEnum] { case UNIQUENESS_SCORE_LEVEL_UNSPECIFIED, UNIQUENESS_SCORE_LOW, UNIQUENESS_SCORE_MEDIUM, UNIQUENESS_SCORE_HIGH }
		enum EstimatedNullPercentageEnum extends Enum[EstimatedNullPercentageEnum] { case NULL_PERCENTAGE_LEVEL_UNSPECIFIED, NULL_PERCENTAGE_VERY_LOW, NULL_PERCENTAGE_LOW, NULL_PERCENTAGE_MEDIUM, NULL_PERCENTAGE_HIGH }
		enum ColumnTypeEnum extends Enum[ColumnTypeEnum] { case COLUMN_DATA_TYPE_UNSPECIFIED, TYPE_INT64, TYPE_BOOL, TYPE_FLOAT64, TYPE_STRING, TYPE_BYTES, TYPE_TIMESTAMP, TYPE_DATE, TYPE_TIME, TYPE_DATETIME, TYPE_GEOGRAPHY, TYPE_NUMERIC, TYPE_RECORD, TYPE_BIGNUMERIC, TYPE_JSON, TYPE_INTERVAL, TYPE_RANGE_DATE, TYPE_RANGE_DATETIME, TYPE_RANGE_TIMESTAMP }
		enum PolicyStateEnum extends Enum[PolicyStateEnum] { case COLUMN_POLICY_STATE_UNSPECIFIED, COLUMN_POLICY_TAGGED }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RUNNING, DONE }
	}
	case class GooglePrivacyDlpV2ColumnDataProfile(
	  /** The name of the profile. */
		name: Option[String] = None,
	  /** The resource name of the table data profile. */
		tableDataProfile: Option[String] = None,
	  /** The sensitivity of this column. */
		sensitivityScore: Option[Schema.GooglePrivacyDlpV2SensitivityScore] = None,
	  /** The name of the column. */
		column: Option[String] = None,
	  /** Approximate uniqueness of the column. */
		estimatedUniquenessScore: Option[Schema.GooglePrivacyDlpV2ColumnDataProfile.EstimatedUniquenessScoreEnum] = None,
	  /** The resource name of the resource this column is within. */
		tableFullResource: Option[String] = None,
	  /** The data risk level for this column. */
		dataRiskLevel: Option[Schema.GooglePrivacyDlpV2DataRiskLevel] = None,
	  /** If supported, the location where the dataset's data is stored. See https://cloud.google.com/bigquery/docs/locations for supported BigQuery locations. */
		datasetLocation: Option[String] = None,
	  /** Success or error status from the most recent profile generation attempt. May be empty if the profile is still being generated. */
		profileStatus: Option[Schema.GooglePrivacyDlpV2ProfileStatus] = None,
	  /** Approximate percentage of entries being null in the column. */
		estimatedNullPercentage: Option[Schema.GooglePrivacyDlpV2ColumnDataProfile.EstimatedNullPercentageEnum] = None,
	  /** The data type of a given column. */
		columnType: Option[Schema.GooglePrivacyDlpV2ColumnDataProfile.ColumnTypeEnum] = None,
	  /** Indicates if a policy tag has been applied to the column. */
		policyState: Option[Schema.GooglePrivacyDlpV2ColumnDataProfile.PolicyStateEnum] = None,
	  /** If it's been determined this column can be identified as a single type, this will be set. Otherwise the column either has unidentifiable content or mixed types. */
		columnInfoType: Option[Schema.GooglePrivacyDlpV2InfoTypeSummary] = None,
	  /** Other types found within this column. List will be unordered. */
		otherMatches: Option[List[Schema.GooglePrivacyDlpV2OtherInfoTypeSummary]] = None,
	  /** The last time the profile was generated. */
		profileLastGenerated: Option[String] = None,
	  /** The BigQuery dataset ID, if the resource profiled is a BigQuery table. */
		datasetId: Option[String] = None,
	  /** The table ID. */
		tableId: Option[String] = None,
	  /** The Google Cloud project ID that owns the profiled resource. */
		datasetProjectId: Option[String] = None,
	  /** State of a profile. */
		state: Option[Schema.GooglePrivacyDlpV2ColumnDataProfile.StateEnum] = None,
	  /** The likelihood that this column contains free-form text. A value close to 1 may indicate the column is likely to contain free-form or natural language text. Range in 0-1. */
		freeTextScore: Option[BigDecimal] = None
	)
	
	case class GooglePrivacyDlpV2DataProfileBigQueryRowSchema(
	  /** Column data profile column */
		columnProfile: Option[Schema.GooglePrivacyDlpV2ColumnDataProfile] = None,
	  /** File store data profile column. */
		fileStoreProfile: Option[Schema.GooglePrivacyDlpV2FileStoreDataProfile] = None,
	  /** Table data profile column */
		tableProfile: Option[Schema.GooglePrivacyDlpV2TableDataProfile] = None
	)
	
	case class GooglePrivacyDlpV2CategoricalStatsConfig(
	  /** Field to compute categorical stats on. All column types are supported except for arrays and structs. However, it may be more informative to use NumericalStats when the field type is supported, depending on the data. */
		field: Option[Schema.GooglePrivacyDlpV2FieldId] = None
	)
	
	case class GooglePrivacyDlpV2DeidentifyTemplate(
	  /** Output only. The template name. The template will have one of the following formats: `projects/PROJECT_ID/deidentifyTemplates/TEMPLATE_ID` OR `organizations/ORGANIZATION_ID/deidentifyTemplates/TEMPLATE_ID` */
		name: Option[String] = None,
	  /** Short description (max 256 chars). */
		description: Option[String] = None,
	  /** Output only. The creation timestamp of an inspectTemplate. */
		createTime: Option[String] = None,
	  /** The core content of the template. */
		deidentifyConfig: Option[Schema.GooglePrivacyDlpV2DeidentifyConfig] = None,
	  /** Display name (max 256 chars). */
		displayName: Option[String] = None,
	  /** Output only. The last update timestamp of an inspectTemplate. */
		updateTime: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2LDiversityEquivalenceClass(
	  /** Quasi-identifier values defining the k-anonymity equivalence class. The order is always the same as the original request. */
		quasiIdsValues: Option[List[Schema.GooglePrivacyDlpV2Value]] = None,
	  /** Estimated frequencies of top sensitive values. */
		topSensitiveValues: Option[List[Schema.GooglePrivacyDlpV2ValueFrequency]] = None,
	  /** Number of distinct sensitive values in this equivalence class. */
		numDistinctSensitiveValues: Option[String] = None,
	  /** Size of the k-anonymity equivalence class. */
		equivalenceClassSize: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2InfoTypeLimit(
	  /** Type of information the findings limit applies to. Only one limit per info_type should be provided. If InfoTypeLimit does not have an info_type, the DLP API applies the limit against all info_types that are found but not specified in another InfoTypeLimit. */
		infoType: Option[Schema.GooglePrivacyDlpV2InfoType] = None,
	  /** Max findings limit for the given infoType. */
		maxFindings: Option[Int] = None
	)
	
	case class GooglePrivacyDlpV2DeidentifyDataSourceStats(
	  /** Number of errors encountered while trying to apply transformations. */
		transformationErrorCount: Option[String] = None,
	  /** Total size in bytes that were transformed in some way. */
		transformedBytes: Option[String] = None,
	  /** Number of successfully applied transformations. */
		transformationCount: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2DeidentifyDataSourceDetails(
	  /** Stats about the de-identification operation. */
		deidentifyStats: Option[Schema.GooglePrivacyDlpV2DeidentifyDataSourceStats] = None,
	  /** De-identification config used for the request. */
		requestedOptions: Option[Schema.GooglePrivacyDlpV2RequestedDeidentifyOptions] = None
	)
	
	object GooglePrivacyDlpV2PubSubNotification {
		enum EventEnum extends Enum[EventEnum] { case EVENT_TYPE_UNSPECIFIED, NEW_PROFILE, CHANGED_PROFILE, SCORE_INCREASED, ERROR_CHANGED }
		enum DetailOfMessageEnum extends Enum[DetailOfMessageEnum] { case DETAIL_LEVEL_UNSPECIFIED, TABLE_PROFILE, RESOURCE_NAME, FILE_STORE_PROFILE }
	}
	case class GooglePrivacyDlpV2PubSubNotification(
	  /** The type of event that triggers a Pub/Sub. At most one `PubSubNotification` per EventType is permitted. */
		event: Option[Schema.GooglePrivacyDlpV2PubSubNotification.EventEnum] = None,
	  /** How much data to include in the Pub/Sub message. If the user wishes to limit the size of the message, they can use resource_name and fetch the profile fields they wish to. Per table profile (not per column). */
		detailOfMessage: Option[Schema.GooglePrivacyDlpV2PubSubNotification.DetailOfMessageEnum] = None,
	  /** Cloud Pub/Sub topic to send notifications to. Format is projects/{project}/topics/{topic}. */
		topic: Option[String] = None,
	  /** Conditions (e.g., data risk or sensitivity level) for triggering a Pub/Sub. */
		pubsubCondition: Option[Schema.GooglePrivacyDlpV2DataProfilePubSubCondition] = None
	)
	
	case class GooglePrivacyDlpV2Location(
	  /** Information about the container where this finding occurred, if available. */
		container: Option[Schema.GooglePrivacyDlpV2Container] = None,
	  /** Zero-based byte offsets delimiting the finding. These are relative to the finding's containing element. Note that when the content is not textual, this references the UTF-8 encoded textual representation of the content. Omitted if content is an image. */
		byteRange: Option[Schema.GooglePrivacyDlpV2Range] = None,
	  /** Unicode character offsets delimiting the finding. These are relative to the finding's containing element. Provided when the content is text. */
		codepointRange: Option[Schema.GooglePrivacyDlpV2Range] = None,
	  /** List of nested objects pointing to the precise location of the finding within the file or record. */
		contentLocations: Option[List[Schema.GooglePrivacyDlpV2ContentLocation]] = None
	)
	
	case class GooglePrivacyDlpV2BigQueryDiscoveryTarget(
	  /** How often and when to update profiles. New tables that match both the filter and conditions are scanned as quickly as possible depending on system capacity. */
		cadence: Option[Schema.GooglePrivacyDlpV2DiscoveryGenerationCadence] = None,
	  /** In addition to matching the filter, these conditions must be true before a profile is generated. */
		conditions: Option[Schema.GooglePrivacyDlpV2DiscoveryBigQueryConditions] = None,
	  /** Tables that match this filter will not have profiles created. */
		disabled: Option[Schema.GooglePrivacyDlpV2Disabled] = None,
	  /** Required. The tables the discovery cadence applies to. The first target with a matching filter will be the one to apply to a table. */
		filter: Option[Schema.GooglePrivacyDlpV2DiscoveryBigQueryFilter] = None
	)
	
	case class GooglePrivacyDlpV2ListJobTriggersResponse(
	  /** List of triggeredJobs, up to page_size in ListJobTriggersRequest. */
		jobTriggers: Option[List[Schema.GooglePrivacyDlpV2JobTrigger]] = None,
	  /** If the next page is available then this value is the next page token to be used in the following ListJobTriggers request. */
		nextPageToken: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2UpdateInspectTemplateRequest(
	  /** Mask to control which fields get updated. */
		updateMask: Option[String] = None,
	  /** New InspectTemplate value. */
		inspectTemplate: Option[Schema.GooglePrivacyDlpV2InspectTemplate] = None
	)
	
	case class GooglePrivacyDlpV2LDiversityResult(
	  /** Histogram of l-diversity equivalence class sensitive value frequencies. */
		sensitiveValueFrequencyHistogramBuckets: Option[List[Schema.GooglePrivacyDlpV2LDiversityHistogramBucket]] = None
	)
	
	case class GooglePrivacyDlpV2CloudStorageFileSet(
	  /** The url, in the format `gs:///`. Trailing wildcard in the path is allowed. */
		url: Option[String] = None
	)
	
	object GooglePrivacyDlpV2DiscoveryCloudStorageConditions {
		enum IncludedObjectAttributesEnum extends Enum[IncludedObjectAttributesEnum] { case CLOUD_STORAGE_OBJECT_ATTRIBUTE_UNSPECIFIED, ALL_SUPPORTED_OBJECTS, STANDARD, NEARLINE, COLDLINE, ARCHIVE, REGIONAL, MULTI_REGIONAL, DURABLE_REDUCED_AVAILABILITY }
		enum IncludedBucketAttributesEnum extends Enum[IncludedBucketAttributesEnum] { case CLOUD_STORAGE_BUCKET_ATTRIBUTE_UNSPECIFIED, ALL_SUPPORTED_BUCKETS, AUTOCLASS_DISABLED, AUTOCLASS_ENABLED }
	}
	case class GooglePrivacyDlpV2DiscoveryCloudStorageConditions(
	  /** Required. Only objects with the specified attributes will be scanned. If an object has one of the specified attributes but is inside an excluded bucket, it will not be scanned. Defaults to [ALL_SUPPORTED_OBJECTS]. A profile will be created even if no objects match the included_object_attributes. */
		includedObjectAttributes: Option[List[Schema.GooglePrivacyDlpV2DiscoveryCloudStorageConditions.IncludedObjectAttributesEnum]] = None,
	  /** Required. Only objects with the specified attributes will be scanned. Defaults to [ALL_SUPPORTED_BUCKETS] if unset. */
		includedBucketAttributes: Option[List[Schema.GooglePrivacyDlpV2DiscoveryCloudStorageConditions.IncludedBucketAttributesEnum]] = None
	)
	
	case class GooglePrivacyDlpV2KmsWrappedCryptoKey(
	  /** Required. The wrapped data crypto key. */
		wrappedKey: Option[String] = None,
	  /** Required. The resource name of the KMS CryptoKey to use for unwrapping. */
		cryptoKeyName: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2QuasiId(
	  /** A column can be tagged with a InfoType to use the relevant public dataset as a statistical model of population, if available. We currently support US ZIP codes, region codes, ages and genders. To programmatically obtain the list of supported InfoTypes, use ListInfoTypes with the supported_by=RISK_ANALYSIS filter. */
		infoType: Option[Schema.GooglePrivacyDlpV2InfoType] = None,
	  /** A column can be tagged with a custom tag. In this case, the user must indicate an auxiliary table that contains statistical information on the possible values of this column (below). */
		customTag: Option[String] = None,
	  /** Required. Identifies the column. */
		field: Option[Schema.GooglePrivacyDlpV2FieldId] = None,
	  /** If no semantic tag is indicated, we infer the statistical model from the distribution of values in the input data */
		inferred: Option[Schema.GoogleProtobufEmpty] = None
	)
	
	case class GooglePrivacyDlpV2InfoType(
	  /** Optional custom sensitivity for this InfoType. This only applies to data profiling. */
		sensitivityScore: Option[Schema.GooglePrivacyDlpV2SensitivityScore] = None,
	  /** Optional version name for this InfoType. */
		version: Option[String] = None,
	  /** Name of the information type. Either a name of your choosing when creating a CustomInfoType, or one of the names listed at https://cloud.google.com/sensitive-data-protection/docs/infotypes-reference when specifying a built-in type. When sending Cloud DLP results to Data Catalog, infoType names should conform to the pattern `[A-Za-z0-9$_-]{1,64}`. */
		name: Option[String] = None
	)
	
	object GooglePrivacyDlpV2DiscoverySchemaModifiedCadence {
		enum FrequencyEnum extends Enum[FrequencyEnum] { case UPDATE_FREQUENCY_UNSPECIFIED, UPDATE_FREQUENCY_NEVER, UPDATE_FREQUENCY_DAILY, UPDATE_FREQUENCY_MONTHLY }
		enum TypesEnum extends Enum[TypesEnum] { case SCHEMA_MODIFICATION_UNSPECIFIED, SCHEMA_NEW_COLUMNS, SCHEMA_REMOVED_COLUMNS }
	}
	case class GooglePrivacyDlpV2DiscoverySchemaModifiedCadence(
	  /** How frequently profiles may be updated when schemas are modified. Defaults to monthly. */
		frequency: Option[Schema.GooglePrivacyDlpV2DiscoverySchemaModifiedCadence.FrequencyEnum] = None,
	  /** The type of events to consider when deciding if the table's schema has been modified and should have the profile updated. Defaults to NEW_COLUMNS. */
		types: Option[List[Schema.GooglePrivacyDlpV2DiscoverySchemaModifiedCadence.TypesEnum]] = None
	)
	
	case class GooglePrivacyDlpV2LDiversityConfig(
	  /** Sensitive field for computing the l-value. */
		sensitiveAttribute: Option[Schema.GooglePrivacyDlpV2FieldId] = None,
	  /** Set of quasi-identifiers indicating how equivalence classes are defined for the l-diversity computation. When multiple fields are specified, they are considered a single composite key. */
		quasiIds: Option[List[Schema.GooglePrivacyDlpV2FieldId]] = None
	)
	
	case class GooglePrivacyDlpV2Container(
	  /** Findings container version, if available ("generation" for Cloud Storage). */
		version: Option[String] = None,
	  /** A string representation of the full container name. Examples: - BigQuery: 'Project:DataSetId.TableId' - Cloud Storage: 'gs://Bucket/folders/filename.txt' */
		fullPath: Option[String] = None,
	  /** Findings container modification timestamp, if applicable. For Cloud Storage, this field contains the last file modification timestamp. For a BigQuery table, this field contains the last_modified_time property. For Datastore, this field isn't populated. */
		updateTime: Option[String] = None,
	  /** Container type, for example BigQuery or Cloud Storage. */
		`type`: Option[String] = None,
	  /** The root of the container. Examples: - For BigQuery table `project_id:dataset_id.table_id`, the root is `dataset_id` - For Cloud Storage file `gs://bucket/folder/filename.txt`, the root is `gs://bucket` */
		rootPath: Option[String] = None,
	  /** Project where the finding was found. Can be different from the project that owns the finding. */
		projectId: Option[String] = None,
	  /** The rest of the path after the root. Examples: - For BigQuery table `project_id:dataset_id.table_id`, the relative path is `table_id` - For Cloud Storage file `gs://bucket/folder/filename.txt`, the relative path is `folder/filename.txt` */
		relativePath: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2InspectionRuleSet(
	  /** List of infoTypes this rule set is applied to. */
		infoTypes: Option[List[Schema.GooglePrivacyDlpV2InfoType]] = None,
	  /** Set of rules to be applied to infoTypes. The rules are applied in order. */
		rules: Option[List[Schema.GooglePrivacyDlpV2InspectionRule]] = None
	)
	
	case class GooglePrivacyDlpV2Row(
	  /** Individual cells. */
		values: Option[List[Schema.GooglePrivacyDlpV2Value]] = None
	)
	
	case class GooglePrivacyDlpV2CryptoKey(
	  /** Transient crypto key */
		transient: Option[Schema.GooglePrivacyDlpV2TransientCryptoKey] = None,
	  /** Unwrapped crypto key */
		unwrapped: Option[Schema.GooglePrivacyDlpV2UnwrappedCryptoKey] = None,
	  /** Key wrapped using Cloud KMS */
		kmsWrapped: Option[Schema.GooglePrivacyDlpV2KmsWrappedCryptoKey] = None
	)
	
	case class GooglePrivacyDlpV2ThrowError(
	
	)
	
	case class GooglePrivacyDlpV2QuasiIdentifierField(
	  /** A column can be tagged with a custom tag. In this case, the user must indicate an auxiliary table that contains statistical information on the possible values of this column (below). */
		customTag: Option[String] = None,
	  /** Identifies the column. */
		field: Option[Schema.GooglePrivacyDlpV2FieldId] = None
	)
	
	case class GooglePrivacyDlpV2PrimitiveTransformation(
	  /** Ffx-Fpe */
		cryptoReplaceFfxFpeConfig: Option[Schema.GooglePrivacyDlpV2CryptoReplaceFfxFpeConfig] = None,
	  /** Time extraction */
		timePartConfig: Option[Schema.GooglePrivacyDlpV2TimePartConfig] = None,
	  /** Mask */
		characterMaskConfig: Option[Schema.GooglePrivacyDlpV2CharacterMaskConfig] = None,
	  /** Redact */
		redactConfig: Option[Schema.GooglePrivacyDlpV2RedactConfig] = None,
	  /** Date Shift */
		dateShiftConfig: Option[Schema.GooglePrivacyDlpV2DateShiftConfig] = None,
	  /** Replace with infotype */
		replaceWithInfoTypeConfig: Option[Schema.GooglePrivacyDlpV2ReplaceWithInfoTypeConfig] = None,
	  /** Fixed size bucketing */
		fixedSizeBucketingConfig: Option[Schema.GooglePrivacyDlpV2FixedSizeBucketingConfig] = None,
	  /** Deterministic Crypto */
		cryptoDeterministicConfig: Option[Schema.GooglePrivacyDlpV2CryptoDeterministicConfig] = None,
	  /** Replace with a specified value. */
		replaceConfig: Option[Schema.GooglePrivacyDlpV2ReplaceValueConfig] = None,
	  /** Bucketing */
		bucketingConfig: Option[Schema.GooglePrivacyDlpV2BucketingConfig] = None,
	  /** Crypto */
		cryptoHashConfig: Option[Schema.GooglePrivacyDlpV2CryptoHashConfig] = None,
	  /** Replace with a value randomly drawn (with replacement) from a dictionary. */
		replaceDictionaryConfig: Option[Schema.GooglePrivacyDlpV2ReplaceDictionaryConfig] = None
	)
	
	object GooglePrivacyDlpV2TagResources {
		enum ProfileGenerationsToTagEnum extends Enum[ProfileGenerationsToTagEnum] { case PROFILE_GENERATION_UNSPECIFIED, PROFILE_GENERATION_NEW, PROFILE_GENERATION_UPDATE }
	}
	case class GooglePrivacyDlpV2TagResources(
	  /** Whether applying a tag to a resource should lower the risk of the profile for that resource. For example, in conjunction with an [IAM deny policy](https://cloud.google.com/iam/docs/deny-overview), you can deny all principals a permission if a tag value is present, mitigating the risk of the resource. This also lowers the data risk of resources at the lower levels of the resource hierarchy. For example, reducing the data risk of a table data profile also reduces the data risk of the constituent column data profiles. */
		lowerDataRiskToLow: Option[Boolean] = None,
	  /** The profile generations for which the tag should be attached to resources. If you attach a tag to only new profiles, then if the sensitivity score of a profile subsequently changes, its tag doesn't change. By default, this field includes only new profiles. To include both new and updated profiles for tagging, this field should explicitly include both `PROFILE_GENERATION_NEW` and `PROFILE_GENERATION_UPDATE`. */
		profileGenerationsToTag: Option[List[Schema.GooglePrivacyDlpV2TagResources.ProfileGenerationsToTagEnum]] = None,
	  /** The tags to associate with different conditions. */
		tagConditions: Option[List[Schema.GooglePrivacyDlpV2TagCondition]] = None
	)
	
	case class GooglePrivacyDlpV2Range(
	  /** Index of the first character of the range (inclusive). */
		start: Option[String] = None,
	  /** Index of the last character of the range (exclusive). */
		end: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2KAnonymityHistogramBucket(
	  /** Sample of equivalence classes in this bucket. The total number of classes returned per bucket is capped at 20. */
		bucketValues: Option[List[Schema.GooglePrivacyDlpV2KAnonymityEquivalenceClass]] = None,
	  /** Total number of distinct equivalence classes in this bucket. */
		bucketValueCount: Option[String] = None,
	  /** Upper bound on the size of the equivalence classes in this bucket. */
		equivalenceClassSizeUpperBound: Option[String] = None,
	  /** Lower bound on the size of the equivalence classes in this bucket. */
		equivalenceClassSizeLowerBound: Option[String] = None,
	  /** Total number of equivalence classes in this bucket. */
		bucketSize: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2DiscoveryStartingLocation(
	  /** The ID of the folder within an organization to be scanned. */
		folderId: Option[String] = None,
	  /** The ID of an organization to scan. */
		organizationId: Option[String] = None
	)
	
	object GooglePrivacyDlpV2OutputStorageConfig {
		enum OutputSchemaEnum extends Enum[OutputSchemaEnum] { case OUTPUT_SCHEMA_UNSPECIFIED, BASIC_COLUMNS, GCS_COLUMNS, DATASTORE_COLUMNS, BIG_QUERY_COLUMNS, ALL_COLUMNS }
	}
	case class GooglePrivacyDlpV2OutputStorageConfig(
	  /** Store findings in an existing table or a new table in an existing dataset. If table_id is not set a new one will be generated for you with the following format: dlp_googleapis_yyyy_mm_dd_[dlp_job_id]. Pacific time zone will be used for generating the date details. For Inspect, each column in an existing output table must have the same name, type, and mode of a field in the `Finding` object. For Risk, an existing output table should be the output of a previous Risk analysis job run on the same source table, with the same privacy metric and quasi-identifiers. Risk jobs that analyze the same table but compute a different privacy metric, or use different sets of quasi-identifiers, cannot store their results in the same table. */
		table: Option[Schema.GooglePrivacyDlpV2BigQueryTable] = None,
	  /** Schema used for writing the findings for Inspect jobs. This field is only used for Inspect and must be unspecified for Risk jobs. Columns are derived from the `Finding` object. If appending to an existing table, any columns from the predefined schema that are missing will be added. No columns in the existing table will be deleted. If unspecified, then all available columns will be used for a new table or an (existing) table with no schema, and no changes will be made to an existing table that has a schema. Only for use with external storage. */
		outputSchema: Option[Schema.GooglePrivacyDlpV2OutputStorageConfig.OutputSchemaEnum] = None
	)
	
	object GooglePrivacyDlpV2BigQueryOptions {
		enum SampleMethodEnum extends Enum[SampleMethodEnum] { case SAMPLE_METHOD_UNSPECIFIED, TOP, RANDOM_START }
	}
	case class GooglePrivacyDlpV2BigQueryOptions(
	  /** Table fields that may uniquely identify a row within the table. When `actions.saveFindings.outputConfig.table` is specified, the values of columns specified here are available in the output table under `location.content_locations.record_location.record_key.id_values`. Nested fields such as `person.birthdate.year` are allowed. */
		identifyingFields: Option[List[Schema.GooglePrivacyDlpV2FieldId]] = None,
	  /** References to fields excluded from scanning. This allows you to skip inspection of entire columns which you know have no findings. When inspecting a table, we recommend that you inspect all columns. Otherwise, findings might be affected because hints from excluded columns will not be used. */
		excludedFields: Option[List[Schema.GooglePrivacyDlpV2FieldId]] = None,
	  /** Max percentage of rows to scan. The rest are omitted. The number of rows scanned is rounded down. Must be between 0 and 100, inclusively. Both 0 and 100 means no limit. Defaults to 0. Only one of rows_limit and rows_limit_percent can be specified. Cannot be used in conjunction with TimespanConfig. Caution: A [known issue](https://cloud.google.com/sensitive-data-protection/docs/known-issues#bq-sampling) is causing the `rowsLimitPercent` field to behave unexpectedly. We recommend using `rowsLimit` instead. */
		rowsLimitPercent: Option[Int] = None,
	  /** Complete BigQuery table reference. */
		tableReference: Option[Schema.GooglePrivacyDlpV2BigQueryTable] = None,
	  /** How to sample the data. */
		sampleMethod: Option[Schema.GooglePrivacyDlpV2BigQueryOptions.SampleMethodEnum] = None,
	  /** Max number of rows to scan. If the table has more rows than this value, the rest of the rows are omitted. If not set, or if set to 0, all rows will be scanned. Only one of rows_limit and rows_limit_percent can be specified. Cannot be used in conjunction with TimespanConfig. */
		rowsLimit: Option[String] = None,
	  /** Limit scanning only to these fields. When inspecting a table, we recommend that you inspect all columns. Otherwise, findings might be affected because hints from excluded columns will not be used. */
		includedFields: Option[List[Schema.GooglePrivacyDlpV2FieldId]] = None
	)
	
	object GooglePrivacyDlpV2InfoTypeCategory {
		enum TypeCategoryEnum extends Enum[TypeCategoryEnum] { case TYPE_UNSPECIFIED, PII, SPII, DEMOGRAPHIC, CREDENTIAL, GOVERNMENT_ID, DOCUMENT, CONTEXTUAL_INFORMATION }
		enum IndustryCategoryEnum extends Enum[IndustryCategoryEnum] { case INDUSTRY_UNSPECIFIED, FINANCE, HEALTH, TELECOMMUNICATIONS }
		enum LocationCategoryEnum extends Enum[LocationCategoryEnum] { case LOCATION_UNSPECIFIED, GLOBAL, ARGENTINA, ARMENIA, AUSTRALIA, AZERBAIJAN, BELARUS, BELGIUM, BRAZIL, CANADA, CHILE, CHINA, COLOMBIA, CROATIA, DENMARK, FRANCE, FINLAND, GERMANY, HONG_KONG, INDIA, INDONESIA, IRELAND, ISRAEL, ITALY, JAPAN, KAZAKHSTAN, KOREA, MEXICO, THE_NETHERLANDS, NEW_ZEALAND, NORWAY, PARAGUAY, PERU, POLAND, PORTUGAL, RUSSIA, SINGAPORE, SOUTH_AFRICA, SPAIN, SWEDEN, SWITZERLAND, TAIWAN, THAILAND, TURKEY, UKRAINE, UNITED_KINGDOM, UNITED_STATES, URUGUAY, UZBEKISTAN, VENEZUELA, INTERNAL }
	}
	case class GooglePrivacyDlpV2InfoTypeCategory(
	  /** The class of identifiers where this infoType belongs */
		typeCategory: Option[Schema.GooglePrivacyDlpV2InfoTypeCategory.TypeCategoryEnum] = None,
	  /** The group of relevant businesses where this infoType is commonly used */
		industryCategory: Option[Schema.GooglePrivacyDlpV2InfoTypeCategory.IndustryCategoryEnum] = None,
	  /** The region or country that issued the ID or document represented by the infoType. */
		locationCategory: Option[Schema.GooglePrivacyDlpV2InfoTypeCategory.LocationCategoryEnum] = None
	)
	
	case class GooglePrivacyDlpV2ActionDetails(
	  /** Outcome of a de-identification action. */
		deidentifyDetails: Option[Schema.GooglePrivacyDlpV2DeidentifyDataSourceDetails] = None
	)
	
	case class GooglePrivacyDlpV2WordList(
	  /** Words or phrases defining the dictionary. The dictionary must contain at least one phrase and every phrase must contain at least 2 characters that are letters or digits. [required] */
		words: Option[List[String]] = None
	)
	
	case class GooglePrivacyDlpV2DataProfileAction(
	  /** Publishes findings to Security Command Center for each data profile. */
		publishToScc: Option[Schema.GooglePrivacyDlpV2PublishToSecurityCommandCenter] = None,
	  /** Publishes generated data profiles to Google Security Operations. For more information, see [Use Sensitive Data Protection data in context-aware analytics](https://cloud.google.com/chronicle/docs/detection/usecase-dlp-high-risk-user-download). */
		publishToChronicle: Option[Schema.GooglePrivacyDlpV2PublishToChronicle] = None,
	  /** Publish a message into the Pub/Sub topic. */
		pubSubNotification: Option[Schema.GooglePrivacyDlpV2PubSubNotification] = None,
	  /** Tags the profiled resources with the specified tag values. */
		tagResources: Option[Schema.GooglePrivacyDlpV2TagResources] = None,
	  /** Export data profiles into a provided location. */
		exportData: Option[Schema.GooglePrivacyDlpV2Export] = None
	)
	
	case class GooglePrivacyDlpV2AmazonS3Bucket(
	  /** The AWS account. */
		awsAccount: Option[Schema.GooglePrivacyDlpV2AwsAccount] = None,
	  /** Required. The bucket name. */
		bucketName: Option[String] = None
	)
	
	object GooglePrivacyDlpV2FileClusterType {
		enum ClusterEnum extends Enum[ClusterEnum] { case CLUSTER_UNSPECIFIED, CLUSTER_UNKNOWN, CLUSTER_TEXT, CLUSTER_STRUCTURED_DATA, CLUSTER_SOURCE_CODE, CLUSTER_RICH_DOCUMENT, CLUSTER_IMAGE, CLUSTER_ARCHIVE, CLUSTER_MULTIMEDIA, CLUSTER_EXECUTABLE, CLUSTER_AI_MODEL }
	}
	case class GooglePrivacyDlpV2FileClusterType(
	  /** Cluster type. */
		cluster: Option[Schema.GooglePrivacyDlpV2FileClusterType.ClusterEnum] = None
	)
	
	case class GooglePrivacyDlpV2TransformationOverview(
	  /** Transformations applied to the dataset. */
		transformationSummaries: Option[List[Schema.GooglePrivacyDlpV2TransformationSummary]] = None,
	  /** Total size in bytes that were transformed in some way. */
		transformedBytes: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2Result(
	  /** Total size in bytes that were processed. */
		processedBytes: Option[String] = None,
	  /** Statistics of how many instances of each info type were found during inspect job. */
		infoTypeStats: Option[List[Schema.GooglePrivacyDlpV2InfoTypeStats]] = None,
	  /** Statistics related to the processing of hybrid inspect. */
		hybridStats: Option[Schema.GooglePrivacyDlpV2HybridInspectStatistics] = None,
	  /** Estimate of the number of bytes to process. */
		totalEstimatedBytes: Option[String] = None,
	  /** Number of rows scanned after sampling and time filtering (applicable for row based stores such as BigQuery). */
		numRowsProcessed: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2InspectResult(
	  /** If true, then this item might have more findings than were returned, and the findings returned are an arbitrary subset of all findings. The findings list might be truncated because the input items were too large, or because the server reached the maximum amount of resources allowed for a single API call. For best results, divide the input into smaller batches. */
		findingsTruncated: Option[Boolean] = None,
	  /** List of findings for an item. */
		findings: Option[List[Schema.GooglePrivacyDlpV2Finding]] = None
	)
	
	case class GooglePrivacyDlpV2UpdateDiscoveryConfigRequest(
	  /** Mask to control which fields get updated. */
		updateMask: Option[String] = None,
	  /** Required. New DiscoveryConfig value. */
		discoveryConfig: Option[Schema.GooglePrivacyDlpV2DiscoveryConfig] = None
	)
	
	case class GooglePrivacyDlpV2KMapEstimationHistogramBucket(
	  /** Total number of distinct quasi-identifier tuple values in this bucket. */
		bucketValueCount: Option[String] = None,
	  /** Always greater than or equal to min_anonymity. */
		maxAnonymity: Option[String] = None,
	  /** Sample of quasi-identifier tuple values in this bucket. The total number of classes returned per bucket is capped at 20. */
		bucketValues: Option[List[Schema.GooglePrivacyDlpV2KMapEstimationQuasiIdValues]] = None,
	  /** Number of records within these anonymity bounds. */
		bucketSize: Option[String] = None,
	  /** Always positive. */
		minAnonymity: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2UpdateJobTriggerRequest(
	  /** Mask to control which fields get updated. */
		updateMask: Option[String] = None,
	  /** New JobTrigger value. */
		jobTrigger: Option[Schema.GooglePrivacyDlpV2JobTrigger] = None
	)
	
	case class GoogleRpcStatus(
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None
	)
	
	case class GooglePrivacyDlpV2BucketingConfig(
	  /** Set of buckets. Ranges must be non-overlapping. */
		buckets: Option[List[Schema.GooglePrivacyDlpV2Bucket]] = None
	)
	
	object GooglePrivacyDlpV2CustomInfoType {
		enum LikelihoodEnum extends Enum[LikelihoodEnum] { case LIKELIHOOD_UNSPECIFIED, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
		enum ExclusionTypeEnum extends Enum[ExclusionTypeEnum] { case EXCLUSION_TYPE_UNSPECIFIED, EXCLUSION_TYPE_EXCLUDE }
	}
	case class GooglePrivacyDlpV2CustomInfoType(
	  /** Set of detection rules to apply to all findings of this CustomInfoType. Rules are applied in order that they are specified. Not supported for the `surrogate_type` CustomInfoType. */
		detectionRules: Option[List[Schema.GooglePrivacyDlpV2DetectionRule]] = None,
	  /** Likelihood to return for this CustomInfoType. This base value can be altered by a detection rule if the finding meets the criteria specified by the rule. Defaults to `VERY_LIKELY` if not specified. */
		likelihood: Option[Schema.GooglePrivacyDlpV2CustomInfoType.LikelihoodEnum] = None,
	  /** If set to EXCLUSION_TYPE_EXCLUDE this infoType will not cause a finding to be returned. It still can be used for rules matching. */
		exclusionType: Option[Schema.GooglePrivacyDlpV2CustomInfoType.ExclusionTypeEnum] = None,
	  /** Regular expression based CustomInfoType. */
		regex: Option[Schema.GooglePrivacyDlpV2Regex] = None,
	  /** Load an existing `StoredInfoType` resource for use in `InspectDataSource`. Not currently supported in `InspectContent`. */
		storedType: Option[Schema.GooglePrivacyDlpV2StoredType] = None,
	  /** Message for detecting output from deidentification transformations that support reversing. */
		surrogateType: Option[Schema.GooglePrivacyDlpV2SurrogateType] = None,
	  /** Sensitivity for this CustomInfoType. If this CustomInfoType extends an existing InfoType, the sensitivity here will take precedence over that of the original InfoType. If unset for a CustomInfoType, it will default to HIGH. This only applies to data profiling. */
		sensitivityScore: Option[Schema.GooglePrivacyDlpV2SensitivityScore] = None,
	  /** CustomInfoType can either be a new infoType, or an extension of built-in infoType, when the name matches one of existing infoTypes and that infoType is specified in `InspectContent.info_types` field. Specifying the latter adds findings to the one detected by the system. If built-in info type is not specified in `InspectContent.info_types` list then the name is treated as a custom info type. */
		infoType: Option[Schema.GooglePrivacyDlpV2InfoType] = None,
	  /** A list of phrases to detect as a CustomInfoType. */
		dictionary: Option[Schema.GooglePrivacyDlpV2Dictionary] = None
	)
	
	case class GooglePrivacyDlpV2ListFileStoreDataProfilesResponse(
	  /** List of data profiles. */
		fileStoreDataProfiles: Option[List[Schema.GooglePrivacyDlpV2FileStoreDataProfile]] = None,
	  /** The next page token. */
		nextPageToken: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2SaveFindings(
	  /** Location to store findings outside of DLP. */
		outputConfig: Option[Schema.GooglePrivacyDlpV2OutputStorageConfig] = None
	)
	
	object GooglePrivacyDlpV2Error {
		enum ExtraInfoEnum extends Enum[ExtraInfoEnum] { case ERROR_INFO_UNSPECIFIED, IMAGE_SCAN_UNAVAILABLE_IN_REGION, FILE_STORE_CLUSTER_UNSUPPORTED }
	}
	case class GooglePrivacyDlpV2Error(
	  /** The times the error occurred. List includes the oldest timestamp and the last 9 timestamps. */
		timestamps: Option[List[String]] = None,
	  /** Additional information about the error. */
		extraInfo: Option[Schema.GooglePrivacyDlpV2Error.ExtraInfoEnum] = None,
	  /** Detailed error codes and messages. */
		details: Option[Schema.GoogleRpcStatus] = None
	)
	
	object GooglePrivacyDlpV2CryptoReplaceFfxFpeConfig {
		enum CommonAlphabetEnum extends Enum[CommonAlphabetEnum] { case FFX_COMMON_NATIVE_ALPHABET_UNSPECIFIED, NUMERIC, HEXADECIMAL, UPPER_CASE_ALPHA_NUMERIC, ALPHA_NUMERIC }
	}
	case class GooglePrivacyDlpV2CryptoReplaceFfxFpeConfig(
	  /** The 'tweak', a context may be used for higher security since the same identifier in two different contexts won't be given the same surrogate. If the context is not set, a default tweak will be used. If the context is set but: 1. there is no record present when transforming a given value or 1. the field is not present when transforming a given value, a default tweak will be used. Note that case (1) is expected when an `InfoTypeTransformation` is applied to both structured and unstructured `ContentItem`s. Currently, the referenced field may be of value type integer or string. The tweak is constructed as a sequence of bytes in big endian byte order such that: - a 64 bit integer is encoded followed by a single byte of value 1 - a string is encoded in UTF-8 format followed by a single byte of value 2 */
		context: Option[Schema.GooglePrivacyDlpV2FieldId] = None,
	  /** Common alphabets. */
		commonAlphabet: Option[Schema.GooglePrivacyDlpV2CryptoReplaceFfxFpeConfig.CommonAlphabetEnum] = None,
	  /** The native way to select the alphabet. Must be in the range [2, 95]. */
		radix: Option[Int] = None,
	  /** Required. The key used by the encryption algorithm. */
		cryptoKey: Option[Schema.GooglePrivacyDlpV2CryptoKey] = None,
	  /** This is supported by mapping these to the alphanumeric characters that the FFX mode natively supports. This happens before/after encryption/decryption. Each character listed must appear only once. Number of characters must be in the range [2, 95]. This must be encoded as ASCII. The order of characters does not matter. The full list of allowed characters is: ``0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz~`!@#$%^&&#42;()_-+={[}]|\:;"'<,>.?/`` */
		customAlphabet: Option[String] = None,
	  /** The custom infoType to annotate the surrogate with. This annotation will be applied to the surrogate by prefixing it with the name of the custom infoType followed by the number of characters comprising the surrogate. The following scheme defines the format: info_type_name(surrogate_character_count):surrogate For example, if the name of custom infoType is 'MY_TOKEN_INFO_TYPE' and the surrogate is 'abc', the full replacement value will be: 'MY_TOKEN_INFO_TYPE(3):abc' This annotation identifies the surrogate when inspecting content using the custom infoType [`SurrogateType`](https://cloud.google.com/sensitive-data-protection/docs/reference/rest/v2/InspectConfig#surrogatetype). This facilitates reversal of the surrogate when it occurs in free text. In order for inspection to work properly, the name of this infoType must not occur naturally anywhere in your data; otherwise, inspection may find a surrogate that does not correspond to an actual identifier. Therefore, choose your custom infoType name carefully after considering what your data looks like. One way to select a name that has a high chance of yielding reliable detection is to include one or more unicode characters that are highly improbable to exist in your data. For example, assuming your data is entered from a regular ASCII keyboard, the symbol with the hex code point 29DD might be used like so: ⧝MY_TOKEN_TYPE */
		surrogateInfoType: Option[Schema.GooglePrivacyDlpV2InfoType] = None
	)
	
	case class GooglePrivacyDlpV2DatastoreKey(
	  /** Datastore entity key. */
		entityKey: Option[Schema.GooglePrivacyDlpV2Key] = None
	)
	
	case class GooglePrivacyDlpV2OrConditions(
	  /** Minimum age a table must have before Cloud DLP can profile it. Value must be 1 hour or greater. */
		minAge: Option[String] = None,
	  /** Minimum number of rows that should be present before Cloud DLP profiles a table */
		minRowCount: Option[Int] = None
	)
	
	object GooglePrivacyDlpV2ByteContentItem {
		enum TypeEnum extends Enum[TypeEnum] { case BYTES_TYPE_UNSPECIFIED, IMAGE, IMAGE_JPEG, IMAGE_BMP, IMAGE_PNG, IMAGE_SVG, TEXT_UTF8, WORD_DOCUMENT, PDF, POWERPOINT_DOCUMENT, EXCEL_DOCUMENT, AVRO, CSV, TSV, AUDIO, VIDEO, EXECUTABLE, AI_MODEL }
	}
	case class GooglePrivacyDlpV2ByteContentItem(
	  /** The type of data stored in the bytes string. Default will be TEXT_UTF8. */
		`type`: Option[Schema.GooglePrivacyDlpV2ByteContentItem.TypeEnum] = None,
	  /** Content data to inspect or redact. */
		data: Option[String] = None
	)
	
	object GooglePrivacyDlpV2DiscoveryInspectTemplateModifiedCadence {
		enum FrequencyEnum extends Enum[FrequencyEnum] { case UPDATE_FREQUENCY_UNSPECIFIED, UPDATE_FREQUENCY_NEVER, UPDATE_FREQUENCY_DAILY, UPDATE_FREQUENCY_MONTHLY }
	}
	case class GooglePrivacyDlpV2DiscoveryInspectTemplateModifiedCadence(
	  /** How frequently data profiles can be updated when the template is modified. Defaults to never. */
		frequency: Option[Schema.GooglePrivacyDlpV2DiscoveryInspectTemplateModifiedCadence.FrequencyEnum] = None
	)
	
	case class GooglePrivacyDlpV2RedactImageResponse(
	  /** If an image was being inspected and the InspectConfig's include_quote was set to true, then this field will include all text, if any, that was found in the image. */
		extractedText: Option[String] = None,
	  /** The findings. Populated when include_findings in the request is true. */
		inspectResult: Option[Schema.GooglePrivacyDlpV2InspectResult] = None,
	  /** The redacted image. The type will be the same as the original image. */
		redactedImage: Option[String] = None
	)
	
	object GooglePrivacyDlpV2StoredInfoTypeVersion {
		enum StateEnum extends Enum[StateEnum] { case STORED_INFO_TYPE_STATE_UNSPECIFIED, PENDING, READY, FAILED, INVALID }
	}
	case class GooglePrivacyDlpV2StoredInfoTypeVersion(
	  /** Statistics about this storedInfoType version. */
		stats: Option[Schema.GooglePrivacyDlpV2StoredInfoTypeStats] = None,
	  /** Errors that occurred when creating this storedInfoType version, or anomalies detected in the storedInfoType data that render it unusable. Only the five most recent errors will be displayed, with the most recent error appearing first. For example, some of the data for stored custom dictionaries is put in the user's Cloud Storage bucket, and if this data is modified or deleted by the user or another system, the dictionary becomes invalid. If any errors occur, fix the problem indicated by the error message and use the UpdateStoredInfoType API method to create another version of the storedInfoType to continue using it, reusing the same `config` if it was not the source of the error. */
		errors: Option[List[Schema.GooglePrivacyDlpV2Error]] = None,
	  /** StoredInfoType configuration. */
		config: Option[Schema.GooglePrivacyDlpV2StoredInfoTypeConfig] = None,
	  /** Stored info type version state. Read-only, updated by the system during dictionary creation. */
		state: Option[Schema.GooglePrivacyDlpV2StoredInfoTypeVersion.StateEnum] = None,
	  /** Create timestamp of the version. Read-only, determined by the system when the version is created. */
		createTime: Option[String] = None
	)
	
	object GooglePrivacyDlpV2PubSubExpressions {
		enum LogicalOperatorEnum extends Enum[LogicalOperatorEnum] { case LOGICAL_OPERATOR_UNSPECIFIED, OR, AND }
	}
	case class GooglePrivacyDlpV2PubSubExpressions(
	  /** Conditions to apply to the expression. */
		conditions: Option[List[Schema.GooglePrivacyDlpV2PubSubCondition]] = None,
	  /** The operator to apply to the collection of conditions. */
		logicalOperator: Option[Schema.GooglePrivacyDlpV2PubSubExpressions.LogicalOperatorEnum] = None
	)
	
	case class GooglePrivacyDlpV2Key(
	  /** The entity path. An entity path consists of one or more elements composed of a kind and a string or numerical identifier, which identify entities. The first element identifies a _root entity_, the second element identifies a _child_ of the root entity, the third element identifies a child of the second entity, and so forth. The entities identified by all prefixes of the path are called the element's _ancestors_. A path can never be empty, and a path can have at most 100 elements. */
		path: Option[List[Schema.GooglePrivacyDlpV2PathElement]] = None,
	  /** Entities are partitioned into subsets, currently identified by a project ID and namespace ID. Queries are scoped to a single partition. */
		partitionId: Option[Schema.GooglePrivacyDlpV2PartitionId] = None
	)
	
	case class GooglePrivacyDlpV2ListConnectionsResponse(
	  /** List of connections. */
		connections: Option[List[Schema.GooglePrivacyDlpV2Connection]] = None,
	  /** Token to retrieve the next page of results. An empty value means there are no more results. */
		nextPageToken: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2ListDeidentifyTemplatesResponse(
	  /** If the next page is available then the next page token to be used in the following ListDeidentifyTemplates request. */
		nextPageToken: Option[String] = None,
	  /** List of deidentify templates, up to page_size in ListDeidentifyTemplatesRequest. */
		deidentifyTemplates: Option[List[Schema.GooglePrivacyDlpV2DeidentifyTemplate]] = None
	)
	
	case class GooglePrivacyDlpV2ReplaceWithInfoTypeConfig(
	
	)
	
	case class GooglePrivacyDlpV2PublishToStackdriver(
	
	)
	
	case class GooglePrivacyDlpV2PublishToChronicle(
	
	)
	
	case class GooglePrivacyDlpV2TagCondition(
	  /** Conditions attaching the tag to a resource on its profile having this sensitivity score. */
		sensitivityScore: Option[Schema.GooglePrivacyDlpV2SensitivityScore] = None,
	  /** The tag value to attach to resources. */
		tag: Option[Schema.GooglePrivacyDlpV2TagValue] = None
	)
	
	case class GooglePrivacyDlpV2Schedule(
	  /** With this option a job is started on a regular periodic basis. For example: every day (86400 seconds). A scheduled start time will be skipped if the previous execution has not ended when its scheduled time occurs. This value must be set to a time duration greater than or equal to 1 day and can be no longer than 60 days. */
		recurrencePeriodDuration: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2KMapEstimationResult(
	  /** The intervals [min_anonymity, max_anonymity] do not overlap. If a value doesn't correspond to any such interval, the associated frequency is zero. For example, the following records: {min_anonymity: 1, max_anonymity: 1, frequency: 17} {min_anonymity: 2, max_anonymity: 3, frequency: 42} {min_anonymity: 5, max_anonymity: 10, frequency: 99} mean that there are no record with an estimated anonymity of 4, 5, or larger than 10. */
		kMapEstimationHistogram: Option[List[Schema.GooglePrivacyDlpV2KMapEstimationHistogramBucket]] = None
	)
	
	case class GooglePrivacyDlpV2PublishSummaryToCscc(
	
	)
	
	case class GooglePrivacyDlpV2ContentItem(
	  /** Structured content for inspection. See https://cloud.google.com/sensitive-data-protection/docs/inspecting-text#inspecting_a_table to learn more. */
		table: Option[Schema.GooglePrivacyDlpV2Table] = None,
	  /** Content data to inspect or redact. Replaces `type` and `data`. */
		byteItem: Option[Schema.GooglePrivacyDlpV2ByteContentItem] = None,
	  /** String data to inspect or redact. */
		value: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2CreateDeidentifyTemplateRequest(
	  /** The template id can contain uppercase and lowercase letters, numbers, and hyphens; that is, it must match the regular expression: `[a-zA-Z\d-_]+`. The maximum length is 100 characters. Can be empty to allow the system to generate one. */
		templateId: Option[String] = None,
	  /** Deprecated. This field has no effect. */
		locationId: Option[String] = None,
	  /** Required. The DeidentifyTemplate to create. */
		deidentifyTemplate: Option[Schema.GooglePrivacyDlpV2DeidentifyTemplate] = None
	)
	
	case class GooglePrivacyDlpV2ListTableDataProfilesResponse(
	  /** The next page token. */
		nextPageToken: Option[String] = None,
	  /** List of data profiles. */
		tableDataProfiles: Option[List[Schema.GooglePrivacyDlpV2TableDataProfile]] = None
	)
	
	case class GooglePrivacyDlpV2DeltaPresenceEstimationHistogramBucket(
	  /** Sample of quasi-identifier tuple values in this bucket. The total number of classes returned per bucket is capped at 20. */
		bucketValues: Option[List[Schema.GooglePrivacyDlpV2DeltaPresenceEstimationQuasiIdValues]] = None,
	  /** Total number of distinct quasi-identifier tuple values in this bucket. */
		bucketValueCount: Option[String] = None,
	  /** Number of records within these probability bounds. */
		bucketSize: Option[String] = None,
	  /** Between 0 and 1. */
		minProbability: Option[BigDecimal] = None,
	  /** Always greater than or equal to min_probability. */
		maxProbability: Option[BigDecimal] = None
	)
	
	case class GooglePrivacyDlpV2RecordTransformation(
	  /** Container version, if available ("generation" for Cloud Storage). */
		containerVersion: Option[String] = None,
	  /** Findings container modification timestamp, if applicable. */
		containerTimestamp: Option[String] = None,
	  /** For record transformations, provide a field. */
		fieldId: Option[Schema.GooglePrivacyDlpV2FieldId] = None
	)
	
	case class GooglePrivacyDlpV2FixedSizeBucketingConfig(
	  /** Required. Lower bound value of buckets. All values less than `lower_bound` are grouped together into a single bucket; for example if `lower_bound` = 10, then all values less than 10 are replaced with the value "-10". */
		lowerBound: Option[Schema.GooglePrivacyDlpV2Value] = None,
	  /** Required. Size of each bucket (except for minimum and maximum buckets). So if `lower_bound` = 10, `upper_bound` = 89, and `bucket_size` = 10, then the following buckets would be used: -10, 10-20, 20-30, 30-40, 40-50, 50-60, 60-70, 70-80, 80-89, 89+. Precision up to 2 decimals works. */
		bucketSize: Option[BigDecimal] = None,
	  /** Required. Upper bound value of buckets. All values greater than upper_bound are grouped together into a single bucket; for example if `upper_bound` = 89, then all values greater than 89 are replaced with the value "89+". */
		upperBound: Option[Schema.GooglePrivacyDlpV2Value] = None
	)
	
	object GooglePrivacyDlpV2CloudSqlProperties {
		enum DatabaseEngineEnum extends Enum[DatabaseEngineEnum] { case DATABASE_ENGINE_UNKNOWN, DATABASE_ENGINE_MYSQL, DATABASE_ENGINE_POSTGRES }
	}
	case class GooglePrivacyDlpV2CloudSqlProperties(
	  /** Required. The database engine used by the Cloud SQL instance that this connection configures. */
		databaseEngine: Option[Schema.GooglePrivacyDlpV2CloudSqlProperties.DatabaseEngineEnum] = None,
	  /** A username and password stored in Secret Manager. */
		usernamePassword: Option[Schema.GooglePrivacyDlpV2SecretManagerCredential] = None,
	  /** Optional. Immutable. The Cloud SQL instance for which the connection is defined. Only one connection per instance is allowed. This can only be set at creation time, and cannot be updated. It is an error to use a connection_name from different project or region than the one that holds the connection. For example, a Connection resource for Cloud SQL connection_name `project-id:us-central1:sql-instance` must be created under the parent `projects/project-id/locations/us-central1` */
		connectionName: Option[String] = None,
	  /** Built-in IAM authentication (must be configured in Cloud SQL). */
		cloudSqlIam: Option[Schema.GooglePrivacyDlpV2CloudSqlIamCredential] = None,
	  /** Required. The DLP API will limit its connections to max_connections. Must be 2 or greater. */
		maxConnections: Option[Int] = None
	)
	
	case class GooglePrivacyDlpV2KindExpression(
	  /** The name of the kind. */
		name: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2DiscoveryBigQueryFilter(
	  /** A specific set of tables for this filter to apply to. A table collection must be specified in only one filter per config. If a table id or dataset is empty, Cloud DLP assumes all tables in that collection must be profiled. Must specify a project ID. */
		tables: Option[Schema.GooglePrivacyDlpV2BigQueryTableCollection] = None,
	  /** Catch-all. This should always be the last filter in the list because anything above it will apply first. Should only appear once in a configuration. If none is specified, a default one will be added automatically. */
		otherTables: Option[Schema.GooglePrivacyDlpV2AllOtherBigQueryTables] = None,
	  /** The table to scan. Discovery configurations including this can only include one DiscoveryTarget (the DiscoveryTarget with this TableReference). */
		tableReference: Option[Schema.GooglePrivacyDlpV2TableReference] = None
	)
	
	case class GooglePrivacyDlpV2SecretsDiscoveryTarget(
	
	)
	
	case class GoogleTypeDate(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None
	)
	
	case class GooglePrivacyDlpV2InspectJobConfig(
	  /** How and what to scan for. */
		inspectConfig: Option[Schema.GooglePrivacyDlpV2InspectConfig] = None,
	  /** The data to scan. */
		storageConfig: Option[Schema.GooglePrivacyDlpV2StorageConfig] = None,
	  /** If provided, will be used as the default for all values in InspectConfig. `inspect_config` will be merged into the values persisted as part of the template. */
		inspectTemplateName: Option[String] = None,
	  /** Actions to execute at the completion of the job. */
		actions: Option[List[Schema.GooglePrivacyDlpV2Action]] = None
	)
	
	case class GooglePrivacyDlpV2ListDlpJobsResponse(
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None,
	  /** A list of DlpJobs that matches the specified filter in the request. */
		jobs: Option[List[Schema.GooglePrivacyDlpV2DlpJob]] = None
	)
	
	case class GooglePrivacyDlpV2DetectionRule(
	  /** Hotword-based detection rule. */
		hotwordRule: Option[Schema.GooglePrivacyDlpV2HotwordRule] = None
	)
	
	case class GooglePrivacyDlpV2UnwrappedCryptoKey(
	  /** Required. A 128/192/256 bit key. */
		key: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2AmazonS3BucketRegex(
	  /** Optional. Regex to test the bucket name against. If empty, all buckets match. */
		bucketNameRegex: Option[String] = None,
	  /** The AWS account regex. */
		awsAccountRegex: Option[Schema.GooglePrivacyDlpV2AwsAccountRegex] = None
	)
	
	case class GooglePrivacyDlpV2BigQueryRegexes(
	  /** A single BigQuery regular expression pattern to match against one or more tables, datasets, or projects that contain BigQuery tables. */
		patterns: Option[List[Schema.GooglePrivacyDlpV2BigQueryRegex]] = None
	)
	
	object GooglePrivacyDlpV2MetadataLocation {
		enum TypeEnum extends Enum[TypeEnum] { case METADATATYPE_UNSPECIFIED, STORAGE_METADATA }
	}
	case class GooglePrivacyDlpV2MetadataLocation(
	  /** Type of metadata containing the finding. */
		`type`: Option[Schema.GooglePrivacyDlpV2MetadataLocation.TypeEnum] = None,
	  /** Storage metadata. */
		storageLabel: Option[Schema.GooglePrivacyDlpV2StorageMetadataLabel] = None
	)
	
	case class GooglePrivacyDlpV2PublishToPubSub(
	  /** Cloud Pub/Sub topic to send notifications to. The topic must have given publishing access rights to the DLP API service account executing the long running DlpJob sending the notifications. Format is projects/{project}/topics/{topic}. */
		topic: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2DiscoveryOtherCloudFilter(
	  /** The resource to scan. Configs using this filter can only have one target (the target with this single resource reference). */
		singleResource: Option[Schema.GooglePrivacyDlpV2OtherCloudSingleResourceReference] = None,
	  /** A collection of resources for this filter to apply to. */
		collection: Option[Schema.GooglePrivacyDlpV2OtherCloudResourceCollection] = None,
	  /** Optional. Catch-all. This should always be the last target in the list because anything above it will apply first. Should only appear once in a configuration. If none is specified, a default one will be added automatically. */
		others: Option[Schema.GooglePrivacyDlpV2AllOtherResources] = None
	)
	
	case class GooglePrivacyDlpV2DiscoveryFileStoreConditions(
	  /** Optional. Minimum age a file store must have. If set, the value must be 1 hour or greater. */
		minAge: Option[String] = None,
	  /** Optional. File store must have been created after this date. Used to avoid backfilling. */
		createdAfter: Option[String] = None,
	  /** Optional. Cloud Storage conditions. */
		cloudStorageConditions: Option[Schema.GooglePrivacyDlpV2DiscoveryCloudStorageConditions] = None
	)
	
	case class GooglePrivacyDlpV2Color(
	  /** The amount of green in the color as a value in the interval [0, 1]. */
		green: Option[BigDecimal] = None,
	  /** The amount of blue in the color as a value in the interval [0, 1]. */
		blue: Option[BigDecimal] = None,
	  /** The amount of red in the color as a value in the interval [0, 1]. */
		red: Option[BigDecimal] = None
	)
	
	case class GooglePrivacyDlpV2RecordSuppression(
	  /** A condition that when it evaluates to true will result in the record being evaluated to be suppressed from the transformed content. */
		condition: Option[Schema.GooglePrivacyDlpV2RecordCondition] = None
	)
	
	case class GooglePrivacyDlpV2DateShiftConfig(
	  /** Required. For example, -5 means shift date to at most 5 days back in the past. */
		lowerBoundDays: Option[Int] = None,
	  /** Points to the field that contains the context, for example, an entity id. If set, must also set cryptoKey. If set, shift will be consistent for the given context. */
		context: Option[Schema.GooglePrivacyDlpV2FieldId] = None,
	  /** Causes the shift to be computed based on this key and the context. This results in the same shift for the same context and crypto_key. If set, must also set context. Can only be applied to table items. */
		cryptoKey: Option[Schema.GooglePrivacyDlpV2CryptoKey] = None,
	  /** Required. Range of shift in days. Actual shift will be selected at random within this range (inclusive ends). Negative means shift to earlier in time. Must not be more than 365250 days (1000 years) each direction. For example, 3 means shift date to at most 3 days into the future. */
		upperBoundDays: Option[Int] = None
	)
	
	case class GooglePrivacyDlpV2DeidentifyContentResponse(
	  /** The de-identified item. */
		item: Option[Schema.GooglePrivacyDlpV2ContentItem] = None,
	  /** An overview of the changes that were made on the `item`. */
		overview: Option[Schema.GooglePrivacyDlpV2TransformationOverview] = None
	)
	
	object GooglePrivacyDlpV2DiscoveryCloudSqlConditions {
		enum DatabaseEnginesEnum extends Enum[DatabaseEnginesEnum] { case DATABASE_ENGINE_UNSPECIFIED, ALL_SUPPORTED_DATABASE_ENGINES, MYSQL, POSTGRES }
		enum TypesEnum extends Enum[TypesEnum] { case DATABASE_RESOURCE_TYPE_UNSPECIFIED, DATABASE_RESOURCE_TYPE_ALL_SUPPORTED_TYPES, DATABASE_RESOURCE_TYPE_TABLE }
	}
	case class GooglePrivacyDlpV2DiscoveryCloudSqlConditions(
	  /** Optional. Database engines that should be profiled. Optional. Defaults to ALL_SUPPORTED_DATABASE_ENGINES if unspecified. */
		databaseEngines: Option[List[Schema.GooglePrivacyDlpV2DiscoveryCloudSqlConditions.DatabaseEnginesEnum]] = None,
	  /** Data profiles will only be generated for the database resource types specified in this field. If not specified, defaults to [DATABASE_RESOURCE_TYPE_ALL_SUPPORTED_TYPES]. */
		types: Option[List[Schema.GooglePrivacyDlpV2DiscoveryCloudSqlConditions.TypesEnum]] = None
	)
	
	case class GooglePrivacyDlpV2AwsAccountRegex(
	  /** Optional. Regex to test the AWS account ID against. If empty, all accounts match. */
		accountIdRegex: Option[String] = None
	)
	
	object GooglePrivacyDlpV2Value {
		enum DayOfWeekValueEnum extends Enum[DayOfWeekValueEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class GooglePrivacyDlpV2Value(
	  /** float */
		floatValue: Option[BigDecimal] = None,
	  /** boolean */
		booleanValue: Option[Boolean] = None,
	  /** time of day */
		timeValue: Option[Schema.GoogleTypeTimeOfDay] = None,
	  /** timestamp */
		timestampValue: Option[String] = None,
	  /** date */
		dateValue: Option[Schema.GoogleTypeDate] = None,
	  /** string */
		stringValue: Option[String] = None,
	  /** day of week */
		dayOfWeekValue: Option[Schema.GooglePrivacyDlpV2Value.DayOfWeekValueEnum] = None,
	  /** integer */
		integerValue: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2TransformationSummary(
	  /** The field transformation that was applied. If multiple field transformations are requested for a single field, this list will contain all of them; otherwise, only one is supplied. */
		fieldTransformations: Option[List[Schema.GooglePrivacyDlpV2FieldTransformation]] = None,
	  /** Total size in bytes that were transformed in some way. */
		transformedBytes: Option[String] = None,
	  /** Collection of all transformations that took place or had an error. */
		results: Option[List[Schema.GooglePrivacyDlpV2SummaryResult]] = None,
	  /** The specific suppression option these stats apply to. */
		recordSuppress: Option[Schema.GooglePrivacyDlpV2RecordSuppression] = None,
	  /** Set if the transformation was limited to a specific FieldId. */
		field: Option[Schema.GooglePrivacyDlpV2FieldId] = None,
	  /** Set if the transformation was limited to a specific InfoType. */
		infoType: Option[Schema.GooglePrivacyDlpV2InfoType] = None,
	  /** The specific transformation these stats apply to. */
		transformation: Option[Schema.GooglePrivacyDlpV2PrimitiveTransformation] = None
	)
	
	object GooglePrivacyDlpV2Deidentify {
		enum FileTypesToTransformEnum extends Enum[FileTypesToTransformEnum] { case FILE_TYPE_UNSPECIFIED, BINARY_FILE, TEXT_FILE, IMAGE, WORD, PDF, AVRO, CSV, TSV, POWERPOINT, EXCEL }
	}
	case class GooglePrivacyDlpV2Deidentify(
	  /** User specified deidentify templates and configs for structured, unstructured, and image files. */
		transformationConfig: Option[Schema.GooglePrivacyDlpV2TransformationConfig] = None,
	  /** List of user-specified file type groups to transform. If specified, only the files with these file types will be transformed. If empty, all supported files will be transformed. Supported types may be automatically added over time. If a file type is set in this field that isn't supported by the Deidentify action then the job will fail and will not be successfully created/started. Currently the only file types supported are: IMAGES, TEXT_FILES, CSV, TSV. */
		fileTypesToTransform: Option[List[Schema.GooglePrivacyDlpV2Deidentify.FileTypesToTransformEnum]] = None,
	  /** Required. User settable Cloud Storage bucket and folders to store de-identified files. This field must be set for Cloud Storage deidentification. The output Cloud Storage bucket must be different from the input bucket. De-identified files will overwrite files in the output path. Form of: gs://bucket/folder/ or gs://bucket */
		cloudStorageOutput: Option[String] = None,
	  /** Config for storing transformation details. This is separate from the de-identified content, and contains metadata about the successful transformations and/or failures that occurred while de-identifying. This needs to be set in order for users to access information about the status of each transformation (see TransformationDetails message for more information about what is noted). */
		transformationDetailsStorageConfig: Option[Schema.GooglePrivacyDlpV2TransformationDetailsStorageConfig] = None
	)
	
	object GooglePrivacyDlpV2TimePartConfig {
		enum PartToExtractEnum extends Enum[PartToExtractEnum] { case TIME_PART_UNSPECIFIED, YEAR, MONTH, DAY_OF_MONTH, DAY_OF_WEEK, WEEK_OF_YEAR, HOUR_OF_DAY }
	}
	case class GooglePrivacyDlpV2TimePartConfig(
	  /** The part of the time to keep. */
		partToExtract: Option[Schema.GooglePrivacyDlpV2TimePartConfig.PartToExtractEnum] = None
	)
	
	case class GooglePrivacyDlpV2ListDiscoveryConfigsResponse(
	  /** If the next page is available then this value is the next page token to be used in the following ListDiscoveryConfigs request. */
		nextPageToken: Option[String] = None,
	  /** List of configs, up to page_size in ListDiscoveryConfigsRequest. */
		discoveryConfigs: Option[List[Schema.GooglePrivacyDlpV2DiscoveryConfig]] = None
	)
	
	case class GooglePrivacyDlpV2DataProfileConfigSnapshot(
	  /** A copy of the configuration used to generate this profile. This is deprecated, and the DiscoveryConfig field is preferred moving forward. DataProfileJobConfig will still be written here for Discovery in BigQuery for backwards compatibility, but will not be updated with new fields, while DiscoveryConfig will. */
		dataProfileJob: Option[Schema.GooglePrivacyDlpV2DataProfileJobConfig] = None,
	  /** Name of the inspection template used to generate this profile */
		inspectTemplateName: Option[String] = None,
	  /** A copy of the inspection config used to generate this profile. This is a copy of the inspect_template specified in `DataProfileJobConfig`. */
		inspectConfig: Option[Schema.GooglePrivacyDlpV2InspectConfig] = None,
	  /** A copy of the configuration used to generate this profile. */
		discoveryConfig: Option[Schema.GooglePrivacyDlpV2DiscoveryConfig] = None,
	  /** Timestamp when the template was modified */
		inspectTemplateModifiedTime: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2StorageConfig(
	  /** Google Cloud Datastore options. */
		datastoreOptions: Option[Schema.GooglePrivacyDlpV2DatastoreOptions] = None,
	  /** Configuration of the timespan of the items to include in scanning. */
		timespanConfig: Option[Schema.GooglePrivacyDlpV2TimespanConfig] = None,
	  /** BigQuery options. */
		bigQueryOptions: Option[Schema.GooglePrivacyDlpV2BigQueryOptions] = None,
	  /** Hybrid inspection options. */
		hybridOptions: Option[Schema.GooglePrivacyDlpV2HybridOptions] = None,
	  /** Cloud Storage options. */
		cloudStorageOptions: Option[Schema.GooglePrivacyDlpV2CloudStorageOptions] = None
	)
	
	case class GooglePrivacyDlpV2Trigger(
	  /** For use with hybrid jobs. Jobs must be manually created and finished. */
		manual: Option[Schema.GooglePrivacyDlpV2Manual] = None,
	  /** Create a job on a repeating basis based on the elapse of time. */
		schedule: Option[Schema.GooglePrivacyDlpV2Schedule] = None
	)
	
	case class GooglePrivacyDlpV2PublishToSecurityCommandCenter(
	
	)
	
	object GooglePrivacyDlpV2TableDataProfile {
		enum EncryptionStatusEnum extends Enum[EncryptionStatusEnum] { case ENCRYPTION_STATUS_UNSPECIFIED, ENCRYPTION_GOOGLE_MANAGED, ENCRYPTION_CUSTOMER_MANAGED }
		enum ResourceVisibilityEnum extends Enum[ResourceVisibilityEnum] { case RESOURCE_VISIBILITY_UNSPECIFIED, RESOURCE_VISIBILITY_PUBLIC, RESOURCE_VISIBILITY_INCONCLUSIVE, RESOURCE_VISIBILITY_RESTRICTED }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RUNNING, DONE }
	}
	case class GooglePrivacyDlpV2TableDataProfile(
	  /** The number of columns profiled in the table. */
		scannedColumnCount: Option[String] = None,
	  /** The Google Cloud project ID that owns the resource. */
		datasetProjectId: Option[String] = None,
	  /** The time when this table was last modified */
		lastModifiedTime: Option[String] = None,
	  /** The name of the profile. */
		name: Option[String] = None,
	  /** How the table is encrypted. */
		encryptionStatus: Option[Schema.GooglePrivacyDlpV2TableDataProfile.EncryptionStatusEnum] = None,
	  /** The labels applied to the resource at the time the profile was generated. */
		resourceLabels: Option[Map[String, String]] = None,
	  /** How broadly a resource has been shared. */
		resourceVisibility: Option[Schema.GooglePrivacyDlpV2TableDataProfile.ResourceVisibilityEnum] = None,
	  /** Success or error status from the most recent profile generation attempt. May be empty if the profile is still being generated. */
		profileStatus: Option[Schema.GooglePrivacyDlpV2ProfileStatus] = None,
	  /** The table ID. */
		tableId: Option[String] = None,
	  /** The resource type that was profiled. */
		dataSourceType: Option[Schema.GooglePrivacyDlpV2DataSourceType] = None,
	  /** The number of columns skipped in the table because of an error. */
		failedColumnCount: Option[String] = None,
	  /** The time at which the table was created. */
		createTime: Option[String] = None,
	  /** Other infoTypes found in this table's data. */
		otherInfoTypes: Option[List[Schema.GooglePrivacyDlpV2OtherInfoTypeSummary]] = None,
	  /** Number of rows in the table when the profile was generated. This will not be populated for BigLake tables. */
		rowCount: Option[String] = None,
	  /** If supported, the location where the dataset's data is stored. See https://cloud.google.com/bigquery/docs/locations for supported locations. */
		datasetLocation: Option[String] = None,
	  /** The snapshot of the configurations used to generate the profile. */
		configSnapshot: Option[Schema.GooglePrivacyDlpV2DataProfileConfigSnapshot] = None,
	  /** The sensitivity score of this table. */
		sensitivityScore: Option[Schema.GooglePrivacyDlpV2SensitivityScore] = None,
	  /** State of a profile. */
		state: Option[Schema.GooglePrivacyDlpV2TableDataProfile.StateEnum] = None,
	  /** The size of the table when the profile was generated. */
		tableSizeBytes: Option[String] = None,
	  /** The resource name of the project data profile for this table. */
		projectDataProfile: Option[String] = None,
	  /** The Cloud Asset Inventory resource that was profiled in order to generate this TableDataProfile. https://cloud.google.com/apis/design/resource_names#full_resource_name */
		fullResource: Option[String] = None,
	  /** Optional. The time when this table expires. */
		expirationTime: Option[String] = None,
	  /** The data risk level of this table. */
		dataRiskLevel: Option[Schema.GooglePrivacyDlpV2DataRiskLevel] = None,
	  /** If the resource is BigQuery, the dataset ID. */
		datasetId: Option[String] = None,
	  /** The infoTypes predicted from this table's data. */
		predictedInfoTypes: Option[List[Schema.GooglePrivacyDlpV2InfoTypeSummary]] = None,
	  /** The last time the profile was generated. */
		profileLastGenerated: Option[String] = None
	)
	
	case class GoogleTypeTimeOfDay(
	  /** Seconds of a minute. Must be greater than or equal to 0 and typically must be less than or equal to 59. An API may allow the value 60 if it allows leap-seconds. */
		seconds: Option[Int] = None,
	  /** Fractions of seconds, in nanoseconds. Must be greater than or equal to 0 and less than or equal to 999,999,999. */
		nanos: Option[Int] = None,
	  /** Hours of a day in 24 hour format. Must be greater than or equal to 0 and typically must be less than or equal to 23. An API may choose to allow the value "24:00:00" for scenarios like business closing time. */
		hours: Option[Int] = None,
	  /** Minutes of an hour. Must be greater than or equal to 0 and less than or equal to 59. */
		minutes: Option[Int] = None
	)
	
	case class GooglePrivacyDlpV2TimeZone(
	  /** Set only if the offset can be determined. Positive for time ahead of UTC. E.g. For "UTC-9", this value is -540. */
		offsetMinutes: Option[Int] = None
	)
	
	object GooglePrivacyDlpV2Condition {
		enum OperatorEnum extends Enum[OperatorEnum] { case RELATIONAL_OPERATOR_UNSPECIFIED, EQUAL_TO, NOT_EQUAL_TO, GREATER_THAN, LESS_THAN, GREATER_THAN_OR_EQUALS, LESS_THAN_OR_EQUALS, EXISTS }
	}
	case class GooglePrivacyDlpV2Condition(
	  /** Required. Operator used to compare the field or infoType to the value. */
		operator: Option[Schema.GooglePrivacyDlpV2Condition.OperatorEnum] = None,
	  /** Value to compare against. [Mandatory, except for `EXISTS` tests.] */
		value: Option[Schema.GooglePrivacyDlpV2Value] = None,
	  /** Required. Field within the record this condition is evaluated against. */
		field: Option[Schema.GooglePrivacyDlpV2FieldId] = None
	)
	
	case class GooglePrivacyDlpV2DiscoveryTarget(
	  /** BigQuery target for Discovery. The first target to match a table will be the one applied. */
		bigQueryTarget: Option[Schema.GooglePrivacyDlpV2BigQueryDiscoveryTarget] = None,
	  /** Cloud SQL target for Discovery. The first target to match a table will be the one applied. */
		cloudSqlTarget: Option[Schema.GooglePrivacyDlpV2CloudSqlDiscoveryTarget] = None,
	  /** Discovery target that looks for credentials and secrets stored in cloud resource metadata and reports them as vulnerabilities to Security Command Center. Only one target of this type is allowed. */
		secretsTarget: Option[Schema.GooglePrivacyDlpV2SecretsDiscoveryTarget] = None,
	  /** Cloud Storage target for Discovery. The first target to match a table will be the one applied. */
		cloudStorageTarget: Option[Schema.GooglePrivacyDlpV2CloudStorageDiscoveryTarget] = None,
	  /** Other clouds target for discovery. The first target to match a resource will be the one applied. */
		otherCloudTarget: Option[Schema.GooglePrivacyDlpV2OtherCloudDiscoveryTarget] = None
	)
	
	case class GooglePrivacyDlpV2OtherInfoTypeSummary(
	  /** Whether this infoType was excluded from sensitivity and risk analysis due to factors such as low prevalence (subject to change). */
		excludedFromAnalysis: Option[Boolean] = None,
	  /** The other infoType. */
		infoType: Option[Schema.GooglePrivacyDlpV2InfoType] = None,
	  /** Approximate percentage of non-null rows that contained data detected by this infotype. */
		estimatedPrevalence: Option[Int] = None
	)
	
	case class GooglePrivacyDlpV2KAnonymityConfig(
	  /** Set of fields to compute k-anonymity over. When multiple fields are specified, they are considered a single composite key. Structs and repeated data types are not supported; however, nested fields are supported so long as they are not structs themselves or nested within a repeated field. */
		quasiIds: Option[List[Schema.GooglePrivacyDlpV2FieldId]] = None,
	  /** Message indicating that multiple rows might be associated to a single individual. If the same entity_id is associated to multiple quasi-identifier tuples over distinct rows, we consider the entire collection of tuples as the composite quasi-identifier. This collection is a multiset: the order in which the different tuples appear in the dataset is ignored, but their frequency is taken into account. Important note: a maximum of 1000 rows can be associated to a single entity ID. If more rows are associated with the same entity ID, some might be ignored. */
		entityId: Option[Schema.GooglePrivacyDlpV2EntityId] = None
	)
	
	case class GooglePrivacyDlpV2CreateDiscoveryConfigRequest(
	  /** The config ID can contain uppercase and lowercase letters, numbers, and hyphens; that is, it must match the regular expression: `[a-zA-Z\d-_]+`. The maximum length is 100 characters. Can be empty to allow the system to generate one. */
		configId: Option[String] = None,
	  /** Required. The DiscoveryConfig to create. */
		discoveryConfig: Option[Schema.GooglePrivacyDlpV2DiscoveryConfig] = None
	)
	
	case class GooglePrivacyDlpV2FinishDlpJobRequest(
	
	)
	
	case class GooglePrivacyDlpV2AnalyzeDataSourceRiskDetails(
	  /** Delta-presence result */
		deltaPresenceEstimationResult: Option[Schema.GooglePrivacyDlpV2DeltaPresenceEstimationResult] = None,
	  /** Privacy metric to compute. */
		requestedPrivacyMetric: Option[Schema.GooglePrivacyDlpV2PrivacyMetric] = None,
	  /** Input dataset to compute metrics over. */
		requestedSourceTable: Option[Schema.GooglePrivacyDlpV2BigQueryTable] = None,
	  /** The configuration used for this job. */
		requestedOptions: Option[Schema.GooglePrivacyDlpV2RequestedRiskAnalysisOptions] = None,
	  /** Categorical stats result */
		categoricalStatsResult: Option[Schema.GooglePrivacyDlpV2CategoricalStatsResult] = None,
	  /** Numerical stats result */
		numericalStatsResult: Option[Schema.GooglePrivacyDlpV2NumericalStatsResult] = None,
	  /** L-divesity result */
		lDiversityResult: Option[Schema.GooglePrivacyDlpV2LDiversityResult] = None,
	  /** K-map result */
		kMapEstimationResult: Option[Schema.GooglePrivacyDlpV2KMapEstimationResult] = None,
	  /** K-anonymity result */
		kAnonymityResult: Option[Schema.GooglePrivacyDlpV2KAnonymityResult] = None
	)
	
	case class GooglePrivacyDlpV2CloudStorageRegexFileSet(
	  /** A list of regular expressions matching file paths to exclude. All files in the bucket that match at least one of these regular expressions will be excluded from the scan. Regular expressions use RE2 [syntax](https://github.com/google/re2/wiki/Syntax); a guide can be found under the google/re2 repository on GitHub. */
		excludeRegex: Option[List[String]] = None,
	  /** The name of a Cloud Storage bucket. Required. */
		bucketName: Option[String] = None,
	  /** A list of regular expressions matching file paths to include. All files in the bucket that match at least one of these regular expressions will be included in the set of files, except for those that also match an item in `exclude_regex`. Leaving this field empty will match all files by default (this is equivalent to including `.&#42;` in the list). Regular expressions use RE2 [syntax](https://github.com/google/re2/wiki/Syntax); a guide can be found under the google/re2 repository on GitHub. */
		includeRegex: Option[List[String]] = None
	)
	
	case class GooglePrivacyDlpV2BigQueryField(
	  /** Designated field in the BigQuery table. */
		field: Option[Schema.GooglePrivacyDlpV2FieldId] = None,
	  /** Source table of the field. */
		table: Option[Schema.GooglePrivacyDlpV2BigQueryTable] = None
	)
	
	case class GooglePrivacyDlpV2DatastoreOptions(
	  /** The kind to process. */
		kind: Option[Schema.GooglePrivacyDlpV2KindExpression] = None,
	  /** A partition ID identifies a grouping of entities. The grouping is always by project and namespace, however the namespace ID may be empty. */
		partitionId: Option[Schema.GooglePrivacyDlpV2PartitionId] = None
	)
	
	case class GooglePrivacyDlpV2AllOtherBigQueryTables(
	
	)
	
	case class GooglePrivacyDlpV2FileStoreCollection(
	  /** Optional. A collection of regular expressions to match a file store against. */
		includeRegexes: Option[Schema.GooglePrivacyDlpV2FileStoreRegexes] = None
	)
	
	case class GooglePrivacyDlpV2OtherCloudResourceRegexes(
	  /** A group of regular expression patterns to match against one or more resources. Maximum of 100 entries. The sum of all regular expression's length can't exceed 10 KiB. */
		patterns: Option[List[Schema.GooglePrivacyDlpV2OtherCloudResourceRegex]] = None
	)
	
	case class GooglePrivacyDlpV2DeltaPresenceEstimationQuasiIdValues(
	  /** The estimated probability that a given individual sharing these quasi-identifier values is in the dataset. This value, typically called δ, is the ratio between the number of records in the dataset with these quasi-identifier values, and the total number of individuals (inside &#42;and&#42; outside the dataset) with these quasi-identifier values. For example, if there are 15 individuals in the dataset who share the same quasi-identifier values, and an estimated 100 people in the entire population with these values, then δ is 0.15. */
		estimatedProbability: Option[BigDecimal] = None,
	  /** The quasi-identifier values. */
		quasiIdsValues: Option[List[Schema.GooglePrivacyDlpV2Value]] = None
	)
	
	case class GooglePrivacyDlpV2TableOptions(
	  /** The columns that are the primary keys for table objects included in ContentItem. A copy of this cell's value will stored alongside alongside each finding so that the finding can be traced to the specific row it came from. No more than 3 may be provided. */
		identifyingFields: Option[List[Schema.GooglePrivacyDlpV2FieldId]] = None
	)
	
	case class GooglePrivacyDlpV2Proximity(
	  /** Number of characters after the finding to consider. */
		windowAfter: Option[Int] = None,
	  /** Number of characters before the finding to consider. For tabular data, if you want to modify the likelihood of an entire column of findngs, set this to 1. For more information, see [Hotword example: Set the match likelihood of a table column] (https://cloud.google.com/sensitive-data-protection/docs/creating-custom-infotypes-likelihood#match-column-values). */
		windowBefore: Option[Int] = None
	)
	
	case class GooglePrivacyDlpV2InfoTypeStats(
	  /** The type of finding this stat is for. */
		infoType: Option[Schema.GooglePrivacyDlpV2InfoType] = None,
	  /** Number of findings for this infoType. */
		count: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2DataSourceType(
	  /** Output only. An identifying string to the type of resource being profiled. Current values: &#42; google/bigquery/table &#42; google/project &#42; google/sql/table &#42; google/gcs/bucket */
		dataSource: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2QuasiIdField(
	  /** Identifies the column. */
		field: Option[Schema.GooglePrivacyDlpV2FieldId] = None,
	  /** A auxiliary field. */
		customTag: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2ContentLocation(
	  /** Location within an image's pixels. */
		imageLocation: Option[Schema.GooglePrivacyDlpV2ImageLocation] = None,
	  /** Finding container version, if available ("generation" for Cloud Storage). */
		containerVersion: Option[String] = None,
	  /** Finding container modification timestamp, if applicable. For Cloud Storage, this field contains the last file modification timestamp. For a BigQuery table, this field contains the last_modified_time property. For Datastore, this field isn't populated. */
		containerTimestamp: Option[String] = None,
	  /** Location within a row or record of a database table. */
		recordLocation: Option[Schema.GooglePrivacyDlpV2RecordLocation] = None,
	  /** Name of the container where the finding is located. The top level name is the source file name or table name. Names of some common storage containers are formatted as follows: &#42; BigQuery tables: `{project_id}:{dataset_id}.{table_id}` &#42; Cloud Storage files: `gs://{bucket}/{path}` &#42; Datastore namespace: {namespace} Nested names could be absent if the embedded object has no string identifier (for example, an image contained within a document). */
		containerName: Option[String] = None,
	  /** Location within the metadata for inspected content. */
		metadataLocation: Option[Schema.GooglePrivacyDlpV2MetadataLocation] = None,
	  /** Location data for document files. */
		documentLocation: Option[Schema.GooglePrivacyDlpV2DocumentLocation] = None
	)
	
	case class GooglePrivacyDlpV2Dictionary(
	  /** Newline-delimited file of words in Cloud Storage. Only a single file is accepted. */
		cloudStoragePath: Option[Schema.GooglePrivacyDlpV2CloudStoragePath] = None,
	  /** List of words or phrases to search for. */
		wordList: Option[Schema.GooglePrivacyDlpV2WordList] = None
	)
	
	case class GooglePrivacyDlpV2HybridInspectResponse(
	
	)
	
	case class GooglePrivacyDlpV2FindingLimits(
	  /** Configuration of findings limit given for specified infoTypes. */
		maxFindingsPerInfoType: Option[List[Schema.GooglePrivacyDlpV2InfoTypeLimit]] = None,
	  /** Max number of findings that are returned per request or job. If you set this field in an InspectContentRequest, the resulting maximum value is the value that you set or 3,000, whichever is lower. This value isn't a hard limit. If an inspection reaches this limit, the inspection ends gradually, not abruptly. Therefore, the actual number of findings that Cloud DLP returns can be multiple times higher than this value. */
		maxFindingsPerRequest: Option[Int] = None,
	  /** Max number of findings that are returned for each item scanned. When set within an InspectContentRequest, this field is ignored. This value isn't a hard limit. If the number of findings for an item reaches this limit, the inspection of that item ends gradually, not abruptly. Therefore, the actual number of findings that Cloud DLP returns for the item can be multiple times higher than this value. */
		maxFindingsPerItem: Option[Int] = None
	)
	
	object GooglePrivacyDlpV2DiscoveryBigQueryConditions {
		enum TypeCollectionEnum extends Enum[TypeCollectionEnum] { case BIG_QUERY_COLLECTION_UNSPECIFIED, BIG_QUERY_COLLECTION_ALL_TYPES, BIG_QUERY_COLLECTION_ONLY_SUPPORTED_TYPES }
	}
	case class GooglePrivacyDlpV2DiscoveryBigQueryConditions(
	  /** Restrict discovery to categories of table types. */
		typeCollection: Option[Schema.GooglePrivacyDlpV2DiscoveryBigQueryConditions.TypeCollectionEnum] = None,
	  /** At least one of the conditions must be true for a table to be scanned. */
		orConditions: Option[Schema.GooglePrivacyDlpV2OrConditions] = None,
	  /** BigQuery table must have been created after this date. Used to avoid backfilling. */
		createdAfter: Option[String] = None,
	  /** Restrict discovery to specific table types. */
		types: Option[Schema.GooglePrivacyDlpV2BigQueryTableTypes] = None
	)
	
	object GooglePrivacyDlpV2DiscoveryCloudStorageGenerationCadence {
		enum RefreshFrequencyEnum extends Enum[RefreshFrequencyEnum] { case UPDATE_FREQUENCY_UNSPECIFIED, UPDATE_FREQUENCY_NEVER, UPDATE_FREQUENCY_DAILY, UPDATE_FREQUENCY_MONTHLY }
	}
	case class GooglePrivacyDlpV2DiscoveryCloudStorageGenerationCadence(
	  /** Optional. Data changes in Cloud Storage can't trigger reprofiling. If you set this field, profiles are refreshed at this frequency regardless of whether the underlying buckets have changed. Defaults to never. */
		refreshFrequency: Option[Schema.GooglePrivacyDlpV2DiscoveryCloudStorageGenerationCadence.RefreshFrequencyEnum] = None,
	  /** Optional. Governs when to update data profiles when the inspection rules defined by the `InspectTemplate` change. If not set, changing the template will not cause a data profile to update. */
		inspectTemplateModifiedCadence: Option[Schema.GooglePrivacyDlpV2DiscoveryInspectTemplateModifiedCadence] = None
	)
	
	case class GooglePrivacyDlpV2TableLocation(
	  /** The zero-based index of the row where the finding is located. Only populated for resources that have a natural ordering, not BigQuery. In BigQuery, to identify the row a finding came from, populate BigQueryOptions.identifying_fields with your primary key column names and when you store the findings the value of those columns will be stored inside of Finding. */
		rowIndex: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2AwsDiscoveryStartingLocation(
	  /** The AWS account ID that this discovery config applies to. Within an AWS organization, you can find the AWS account ID inside an AWS account ARN. Example: arn:{partition}:organizations::{management_account_id}:account/{org_id}/{account_id} */
		accountId: Option[String] = None,
	  /** All AWS assets stored in Asset Inventory that didn't match other AWS discovery configs. */
		allAssetInventoryAssets: Option[Boolean] = None
	)
	
	case class GooglePrivacyDlpV2FileExtensionInfo(
	  /** The file extension if set. (aka .pdf, .jpg, .txt) */
		fileExtension: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2TransformationDetails(
	  /** The precise location of the transformed content in the original container. */
		transformationLocation: Option[Schema.GooglePrivacyDlpV2TransformationLocation] = None,
	  /** The number of bytes that were transformed. If transformation was unsuccessful or did not take place because there was no content to transform, this will be zero. */
		transformedBytes: Option[String] = None,
	  /** The top level name of the container where the transformation is located (this will be the source file name or table name). */
		containerName: Option[String] = None,
	  /** The name of the job that completed the transformation. */
		resourceName: Option[String] = None,
	  /** Description of transformation. This would only contain more than one element if there were multiple matching transformations and which one to apply was ambiguous. Not set for states that contain no transformation, currently only state that contains no transformation is TransformationResultStateType.METADATA_UNRETRIEVABLE. */
		transformation: Option[List[Schema.GooglePrivacyDlpV2TransformationDescription]] = None,
	  /** Status of the transformation, if transformation was not successful, this will specify what caused it to fail, otherwise it will show that the transformation was successful. */
		statusDetails: Option[Schema.GooglePrivacyDlpV2TransformationResultStatus] = None
	)
	
	case class GooglePrivacyDlpV2TransientCryptoKey(
	  /** Required. Name of the key. This is an arbitrary string used to differentiate different keys. A unique key is generated per name: two separate `TransientCryptoKey` protos share the same generated key if their names are the same. When the data crypto key is generated, this name is not used in any way (repeating the api call will result in a different key being generated). */
		name: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2FileStoreRegexes(
	  /** Required. The group of regular expression patterns to match against one or more file stores. Maximum of 100 entries. The sum of all regular expression's length can't exceed 10 KiB. */
		patterns: Option[List[Schema.GooglePrivacyDlpV2FileStoreRegex]] = None
	)
	
	case class GooglePrivacyDlpV2HybridInspectDlpJobRequest(
	  /** The item to inspect. */
		hybridItem: Option[Schema.GooglePrivacyDlpV2HybridContentItem] = None
	)
	
	object GooglePrivacyDlpV2InfoTypeDescription {
		enum SupportedByEnum extends Enum[SupportedByEnum] { case ENUM_TYPE_UNSPECIFIED, INSPECT, RISK_ANALYSIS }
	}
	case class GooglePrivacyDlpV2InfoTypeDescription(
	  /** Internal name of the infoType. */
		name: Option[String] = None,
	  /** Human readable form of the infoType name. */
		displayName: Option[String] = None,
	  /** The default sensitivity of the infoType. */
		sensitivityScore: Option[Schema.GooglePrivacyDlpV2SensitivityScore] = None,
	  /** A list of available versions for the infotype. */
		versions: Option[List[Schema.GooglePrivacyDlpV2VersionDescription]] = None,
	  /** Description of the infotype. Translated when language is provided in the request. */
		description: Option[String] = None,
	  /** Which parts of the API supports this InfoType. */
		supportedBy: Option[List[Schema.GooglePrivacyDlpV2InfoTypeDescription.SupportedByEnum]] = None,
	  /** The category of the infoType. */
		categories: Option[List[Schema.GooglePrivacyDlpV2InfoTypeCategory]] = None
	)
	
	case class GooglePrivacyDlpV2NumericalStatsResult(
	  /** List of 99 values that partition the set of field values into 100 equal sized buckets. */
		quantileValues: Option[List[Schema.GooglePrivacyDlpV2Value]] = None,
	  /** Maximum value appearing in the column. */
		maxValue: Option[Schema.GooglePrivacyDlpV2Value] = None,
	  /** Minimum value appearing in the column. */
		minValue: Option[Schema.GooglePrivacyDlpV2Value] = None
	)
	
	case class GooglePrivacyDlpV2DatabaseResourceRegex(
	  /** Regex to test the database name against. If empty, all databases match. */
		databaseRegex: Option[String] = None,
	  /** For organizations, if unset, will match all projects. Has no effect for configurations created within a project. */
		projectIdRegex: Option[String] = None,
	  /** Regex to test the database resource's name against. An example of a database resource name is a table's name. Other database resource names like view names could be included in the future. If empty, all database resources match. */
		databaseResourceNameRegex: Option[String] = None,
	  /** Regex to test the instance name against. If empty, all instances match. */
		instanceRegex: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2DataProfilePubSubCondition(
	  /** An expression. */
		expressions: Option[Schema.GooglePrivacyDlpV2PubSubExpressions] = None
	)
	
	case class GooglePrivacyDlpV2DataProfileJobConfig(
	  /** Actions to execute at the completion of the job. */
		dataProfileActions: Option[List[Schema.GooglePrivacyDlpV2DataProfileAction]] = None,
	  /** The data to scan. */
		location: Option[Schema.GooglePrivacyDlpV2DataProfileLocation] = None,
	  /** Detection logic for profile generation. Not all template features are used by profiles. FindingLimits, include_quote and exclude_info_types have no impact on data profiling. Multiple templates may be provided if there is data in multiple regions. At most one template must be specified per-region (including "global"). Each region is scanned using the applicable template. If no region-specific template is specified, but a "global" template is specified, it will be copied to that region and used instead. If no global or region-specific template is provided for a region with data, that region's data will not be scanned. For more information, see https://cloud.google.com/sensitive-data-protection/docs/data-profiles#data-residency. */
		inspectTemplates: Option[List[String]] = None,
	  /** Must be set only when scanning other clouds. */
		otherCloudStartingLocation: Option[Schema.GooglePrivacyDlpV2OtherCloudDiscoveryStartingLocation] = None,
	  /** The project that will run the scan. The DLP service account that exists within this project must have access to all resources that are profiled, and the DLP API must be enabled. */
		projectId: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2HybridFindingDetails(
	  /** Offset of the row for tables. Populate if the row(s) being scanned are part of a bigger dataset and you want to keep track of their absolute position. */
		rowOffset: Option[String] = None,
	  /** Labels to represent user provided metadata about the data being inspected. If configured by the job, some key values may be required. The labels associated with `Finding`'s produced by hybrid inspection. Label keys must be between 1 and 63 characters long and must conform to the following regular expression: `[a-z]([-a-z0-9]&#42;[a-z0-9])?`. Label values must be between 0 and 63 characters long and must conform to the regular expression `([a-z]([-a-z0-9]&#42;[a-z0-9])?)?`. No more than 10 labels can be associated with a given finding. Examples: &#42; `"environment" : "production"` &#42; `"pipeline" : "etl"` */
		labels: Option[Map[String, String]] = None,
	  /** If the container is a table, additional information to make findings meaningful such as the columns that are primary keys. If not known ahead of time, can also be set within each inspect hybrid call and the two will be merged. Note that identifying_fields will only be stored to BigQuery, and only if the BigQuery action has been included. */
		tableOptions: Option[Schema.GooglePrivacyDlpV2TableOptions] = None,
	  /** Details about the container where the content being inspected is from. */
		containerDetails: Option[Schema.GooglePrivacyDlpV2Container] = None,
	  /** Offset in bytes of the line, from the beginning of the file, where the finding is located. Populate if the item being scanned is only part of a bigger item, such as a shard of a file and you want to track the absolute position of the finding. */
		fileOffset: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2TableReference(
	  /** Name of the table. */
		tableId: Option[String] = None,
	  /** Dataset ID of the table. */
		datasetId: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2ValueFrequency(
	  /** How many times the value is contained in the field. */
		count: Option[String] = None,
	  /** A value contained in the field in question. */
		value: Option[Schema.GooglePrivacyDlpV2Value] = None
	)
	
	case class GooglePrivacyDlpV2InspectContentRequest(
	  /** Template to use. Any configuration directly specified in inspect_config will override those set in the template. Singular fields that are set in this request will replace their corresponding fields in the template. Repeated fields are appended. Singular sub-messages and groups are recursively merged. */
		inspectTemplateName: Option[String] = None,
	  /** The item to inspect. */
		item: Option[Schema.GooglePrivacyDlpV2ContentItem] = None,
	  /** Configuration for the inspector. What specified here will override the template referenced by the inspect_template_name argument. */
		inspectConfig: Option[Schema.GooglePrivacyDlpV2InspectConfig] = None,
	  /** Deprecated. This field has no effect. */
		locationId: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2RedactImageRequest(
	  /** The configuration for specifying what content to redact from images. */
		imageRedactionConfigs: Option[List[Schema.GooglePrivacyDlpV2ImageRedactionConfig]] = None,
	  /** Whether the response should include findings along with the redacted image. */
		includeFindings: Option[Boolean] = None,
	  /** The content must be PNG, JPEG, SVG or BMP. */
		byteItem: Option[Schema.GooglePrivacyDlpV2ByteContentItem] = None,
	  /** Deprecated. This field has no effect. */
		locationId: Option[String] = None,
	  /** Configuration for the inspector. */
		inspectConfig: Option[Schema.GooglePrivacyDlpV2InspectConfig] = None
	)
	
	object GooglePrivacyDlpV2InfoTypeLikelihood {
		enum MinLikelihoodEnum extends Enum[MinLikelihoodEnum] { case LIKELIHOOD_UNSPECIFIED, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY }
	}
	case class GooglePrivacyDlpV2InfoTypeLikelihood(
	  /** Type of information the likelihood threshold applies to. Only one likelihood per info_type should be provided. If InfoTypeLikelihood does not have an info_type, the configuration fails. */
		infoType: Option[Schema.GooglePrivacyDlpV2InfoType] = None,
	  /** Only returns findings equal to or above this threshold. This field is required or else the configuration fails. */
		minLikelihood: Option[Schema.GooglePrivacyDlpV2InfoTypeLikelihood.MinLikelihoodEnum] = None
	)
	
	object GooglePrivacyDlpV2CharsToIgnore {
		enum CommonCharactersToIgnoreEnum extends Enum[CommonCharactersToIgnoreEnum] { case COMMON_CHARS_TO_IGNORE_UNSPECIFIED, NUMERIC, ALPHA_UPPER_CASE, ALPHA_LOWER_CASE, PUNCTUATION, WHITESPACE }
	}
	case class GooglePrivacyDlpV2CharsToIgnore(
	  /** Common characters to not transform when masking. Useful to avoid removing punctuation. */
		commonCharactersToIgnore: Option[Schema.GooglePrivacyDlpV2CharsToIgnore.CommonCharactersToIgnoreEnum] = None,
	  /** Characters to not transform when masking. */
		charactersToSkip: Option[String] = None
	)
	
	object GooglePrivacyDlpV2DiscoveryConfig {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNSPECIFIED, RUNNING, PAUSED }
	}
	case class GooglePrivacyDlpV2DiscoveryConfig(
	  /** Output only. The timestamp of the last time this config was executed. */
		lastRunTime: Option[String] = None,
	  /** Actions to execute at the completion of scanning. */
		actions: Option[List[Schema.GooglePrivacyDlpV2DataProfileAction]] = None,
	  /** Must be set only when scanning other clouds. */
		otherCloudStartingLocation: Option[Schema.GooglePrivacyDlpV2OtherCloudDiscoveryStartingLocation] = None,
	  /** Detection logic for profile generation. Not all template features are used by Discovery. FindingLimits, include_quote and exclude_info_types have no impact on Discovery. Multiple templates may be provided if there is data in multiple regions. At most one template must be specified per-region (including "global"). Each region is scanned using the applicable template. If no region-specific template is specified, but a "global" template is specified, it will be copied to that region and used instead. If no global or region-specific template is provided for a region with data, that region's data will not be scanned. For more information, see https://cloud.google.com/sensitive-data-protection/docs/data-profiles#data-residency. */
		inspectTemplates: Option[List[String]] = None,
	  /** Output only. A stream of errors encountered when the config was activated. Repeated errors may result in the config automatically being paused. Output only field. Will return the last 100 errors. Whenever the config is modified this list will be cleared. */
		errors: Option[List[Schema.GooglePrivacyDlpV2Error]] = None,
	  /** Unique resource name for the DiscoveryConfig, assigned by the service when the DiscoveryConfig is created, for example `projects/dlp-test-project/locations/global/discoveryConfigs/53234423`. */
		name: Option[String] = None,
	  /** Output only. The creation timestamp of a DiscoveryConfig. */
		createTime: Option[String] = None,
	  /** Display name (max 100 chars) */
		displayName: Option[String] = None,
	  /** Output only. The last update timestamp of a DiscoveryConfig. */
		updateTime: Option[String] = None,
	  /** Target to match against for determining what to scan and how frequently. */
		targets: Option[List[Schema.GooglePrivacyDlpV2DiscoveryTarget]] = None,
	  /** Required. A status for this configuration. */
		status: Option[Schema.GooglePrivacyDlpV2DiscoveryConfig.StatusEnum] = None,
	  /** Only set when the parent is an org. */
		orgConfig: Option[Schema.GooglePrivacyDlpV2OrgConfig] = None
	)
	
	case class GooglePrivacyDlpV2CloudStorageResourceReference(
	  /** Required. If within a project-level config, then this must match the config's project id. */
		projectId: Option[String] = None,
	  /** Required. The bucket to scan. */
		bucketName: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2KAnonymityResult(
	  /** Histogram of k-anonymity equivalence classes. */
		equivalenceClassHistogramBuckets: Option[List[Schema.GooglePrivacyDlpV2KAnonymityHistogramBucket]] = None
	)
	
	case class GooglePrivacyDlpV2CreateStoredInfoTypeRequest(
	  /** The storedInfoType ID can contain uppercase and lowercase letters, numbers, and hyphens; that is, it must match the regular expression: `[a-zA-Z\d-_]+`. The maximum length is 100 characters. Can be empty to allow the system to generate one. */
		storedInfoTypeId: Option[String] = None,
	  /** Required. Configuration of the storedInfoType to create. */
		config: Option[Schema.GooglePrivacyDlpV2StoredInfoTypeConfig] = None,
	  /** Deprecated. This field has no effect. */
		locationId: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2DeidentifyContentRequest(
	  /** Template to use. Any configuration directly specified in deidentify_config will override those set in the template. Singular fields that are set in this request will replace their corresponding fields in the template. Repeated fields are appended. Singular sub-messages and groups are recursively merged. */
		deidentifyTemplateName: Option[String] = None,
	  /** The item to de-identify. Will be treated as text. This value must be of type Table if your deidentify_config is a RecordTransformations object. */
		item: Option[Schema.GooglePrivacyDlpV2ContentItem] = None,
	  /** Configuration for the de-identification of the content item. Items specified here will override the template referenced by the deidentify_template_name argument. */
		deidentifyConfig: Option[Schema.GooglePrivacyDlpV2DeidentifyConfig] = None,
	  /** Template to use. Any configuration directly specified in inspect_config will override those set in the template. Singular fields that are set in this request will replace their corresponding fields in the template. Repeated fields are appended. Singular sub-messages and groups are recursively merged. */
		inspectTemplateName: Option[String] = None,
	  /** Deprecated. This field has no effect. */
		locationId: Option[String] = None,
	  /** Configuration for the inspector. Items specified here will override the template referenced by the inspect_template_name argument. */
		inspectConfig: Option[Schema.GooglePrivacyDlpV2InspectConfig] = None
	)
	
	case class GooglePrivacyDlpV2DeidentifyConfig(
	  /** Mode for handling transformation errors. If left unspecified, the default mode is `TransformationErrorHandling.ThrowError`. */
		transformationErrorHandling: Option[Schema.GooglePrivacyDlpV2TransformationErrorHandling] = None,
	  /** Treat the dataset as structured. Transformations can be applied to specific locations within structured datasets, such as transforming a column within a table. */
		recordTransformations: Option[Schema.GooglePrivacyDlpV2RecordTransformations] = None,
	  /** Treat the dataset as free-form text and apply the same free text transformation everywhere. */
		infoTypeTransformations: Option[Schema.GooglePrivacyDlpV2InfoTypeTransformations] = None,
	  /** Treat the dataset as an image and redact. */
		imageTransformations: Option[Schema.GooglePrivacyDlpV2ImageTransformations] = None
	)
	
	case class GooglePrivacyDlpV2HybridOptions(
	  /** If the container is a table, additional information to make findings meaningful such as the columns that are primary keys. */
		tableOptions: Option[Schema.GooglePrivacyDlpV2TableOptions] = None,
	  /** To organize findings, these labels will be added to each finding. Label keys must be between 1 and 63 characters long and must conform to the following regular expression: `[a-z]([-a-z0-9]&#42;[a-z0-9])?`. Label values must be between 0 and 63 characters long and must conform to the regular expression `([a-z]([-a-z0-9]&#42;[a-z0-9])?)?`. No more than 10 labels can be associated with a given finding. Examples: &#42; `"environment" : "production"` &#42; `"pipeline" : "etl"` */
		labels: Option[Map[String, String]] = None,
	  /** These are labels that each inspection request must include within their 'finding_labels' map. Request may contain others, but any missing one of these will be rejected. Label keys must be between 1 and 63 characters long and must conform to the following regular expression: `[a-z]([-a-z0-9]&#42;[a-z0-9])?`. No more than 10 keys can be required. */
		requiredFindingLabelKeys: Option[List[String]] = None,
	  /** A short description of where the data is coming from. Will be stored once in the job. 256 max length. */
		description: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2VersionDescription(
	  /** Name of the version */
		version: Option[String] = None,
	  /** Description of the version. */
		description: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2Export(
	  /** Store all table and column profiles in an existing table or a new table in an existing dataset. Each re-generation will result in new rows in BigQuery. Data is inserted using [streaming insert](https://cloud.google.com/blog/products/bigquery/life-of-a-bigquery-streaming-insert) and so data may be in the buffer for a period of time after the profile has finished. The Pub/Sub notification is sent before the streaming buffer is guaranteed to be written, so data may not be instantly visible to queries by the time your topic receives the Pub/Sub notification. */
		profileTable: Option[Schema.GooglePrivacyDlpV2BigQueryTable] = None
	)
	
	case class GooglePrivacyDlpV2LargeCustomDictionaryConfig(
	  /** Location to store dictionary artifacts in Cloud Storage. These files will only be accessible by project owners and the DLP API. If any of these artifacts are modified, the dictionary is considered invalid and can no longer be used. */
		outputPath: Option[Schema.GooglePrivacyDlpV2CloudStoragePath] = None,
	  /** Field in a BigQuery table where each cell represents a dictionary phrase. */
		bigQueryField: Option[Schema.GooglePrivacyDlpV2BigQueryField] = None,
	  /** Set of files containing newline-delimited lists of dictionary phrases. */
		cloudStorageFileSet: Option[Schema.GooglePrivacyDlpV2CloudStorageFileSet] = None
	)
	
	case class GooglePrivacyDlpV2DeltaPresenceEstimationResult(
	  /** The intervals [min_probability, max_probability) do not overlap. If a value doesn't correspond to any such interval, the associated frequency is zero. For example, the following records: {min_probability: 0, max_probability: 0.1, frequency: 17} {min_probability: 0.2, max_probability: 0.3, frequency: 42} {min_probability: 0.3, max_probability: 0.4, frequency: 99} mean that there are no record with an estimated probability in [0.1, 0.2) nor larger or equal to 0.4. */
		deltaPresenceEstimationHistogram: Option[List[Schema.GooglePrivacyDlpV2DeltaPresenceEstimationHistogramBucket]] = None
	)
	
	case class GooglePrivacyDlpV2CloudStorageDiscoveryTarget(
	  /** Optional. How often and when to update profiles. New buckets that match both the filter and conditions are scanned as quickly as possible depending on system capacity. */
		generationCadence: Option[Schema.GooglePrivacyDlpV2DiscoveryCloudStorageGenerationCadence] = None,
	  /** Required. The buckets the generation_cadence applies to. The first target with a matching filter will be the one to apply to a bucket. */
		filter: Option[Schema.GooglePrivacyDlpV2DiscoveryCloudStorageFilter] = None,
	  /** Optional. In addition to matching the filter, these conditions must be true before a profile is generated. */
		conditions: Option[Schema.GooglePrivacyDlpV2DiscoveryFileStoreConditions] = None,
	  /** Optional. Disable profiling for buckets that match this filter. */
		disabled: Option[Schema.GooglePrivacyDlpV2Disabled] = None
	)
	
	case class GooglePrivacyDlpV2ImageRedactionConfig(
	  /** If true, all text found in the image, regardless whether it matches an info_type, is redacted. Only one should be provided. */
		redactAllText: Option[Boolean] = None,
	  /** The color to use when redacting content from an image. If not specified, the default is black. */
		redactionColor: Option[Schema.GooglePrivacyDlpV2Color] = None,
	  /** Only one per info_type should be provided per request. If not specified, and redact_all_text is false, the DLP API will redact all text that it matches against all info_types that are found, but not specified in another ImageRedactionConfig. */
		infoType: Option[Schema.GooglePrivacyDlpV2InfoType] = None
	)
	
	case class GooglePrivacyDlpV2ListInfoTypesResponse(
	  /** Set of sensitive infoTypes. */
		infoTypes: Option[List[Schema.GooglePrivacyDlpV2InfoTypeDescription]] = None
	)
	
	case class GooglePrivacyDlpV2PathElement(
	  /** The kind of the entity. A kind matching regex `__.&#42;__` is reserved/read-only. A kind must not contain more than 1500 bytes when UTF-8 encoded. Cannot be `""`. */
		kind: Option[String] = None,
	  /** The name of the entity. A name matching regex `__.&#42;__` is reserved/read-only. A name must not be more than 1500 bytes when UTF-8 encoded. Cannot be `""`. */
		name: Option[String] = None,
	  /** The auto-allocated ID of the entity. Never equal to zero. Values less than zero are discouraged and may not be supported in the future. */
		id: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2CreateConnectionRequest(
	  /** Required. The connection resource. */
		connection: Option[Schema.GooglePrivacyDlpV2Connection] = None
	)
	
	case class GooglePrivacyDlpV2StoredInfoTypeConfig(
	  /** Store dictionary-based CustomInfoType. */
		dictionary: Option[Schema.GooglePrivacyDlpV2Dictionary] = None,
	  /** Display name of the StoredInfoType (max 256 characters). */
		displayName: Option[String] = None,
	  /** Store regular expression-based StoredInfoType. */
		regex: Option[Schema.GooglePrivacyDlpV2Regex] = None,
	  /** StoredInfoType where findings are defined by a dictionary of phrases. */
		largeCustomDictionary: Option[Schema.GooglePrivacyDlpV2LargeCustomDictionaryConfig] = None,
	  /** Description of the StoredInfoType (max 256 characters). */
		description: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2PartitionId(
	  /** If not empty, the ID of the namespace to which the entities belong. */
		namespaceId: Option[String] = None,
	  /** The ID of the project to which the entities belong. */
		projectId: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2LDiversityHistogramBucket(
	  /** Total number of distinct equivalence classes in this bucket. */
		bucketValueCount: Option[String] = None,
	  /** Upper bound on the sensitive value frequencies of the equivalence classes in this bucket. */
		sensitiveValueFrequencyUpperBound: Option[String] = None,
	  /** Sample of equivalence classes in this bucket. The total number of classes returned per bucket is capped at 20. */
		bucketValues: Option[List[Schema.GooglePrivacyDlpV2LDiversityEquivalenceClass]] = None,
	  /** Total number of equivalence classes in this bucket. */
		bucketSize: Option[String] = None,
	  /** Lower bound on the sensitive value frequencies of the equivalence classes in this bucket. */
		sensitiveValueFrequencyLowerBound: Option[String] = None
	)
	
	case class GooglePrivacyDlpV2HybridInspectJobTriggerRequest(
	  /** The item to inspect. */
		hybridItem: Option[Schema.GooglePrivacyDlpV2HybridContentItem] = None
	)
	
	object GooglePrivacyDlpV2PubSubCondition {
		enum MinimumRiskScoreEnum extends Enum[MinimumRiskScoreEnum] { case PROFILE_SCORE_BUCKET_UNSPECIFIED, HIGH, MEDIUM_OR_HIGH }
		enum MinimumSensitivityScoreEnum extends Enum[MinimumSensitivityScoreEnum] { case PROFILE_SCORE_BUCKET_UNSPECIFIED, HIGH, MEDIUM_OR_HIGH }
	}
	case class GooglePrivacyDlpV2PubSubCondition(
	  /** The minimum data risk score that triggers the condition. */
		minimumRiskScore: Option[Schema.GooglePrivacyDlpV2PubSubCondition.MinimumRiskScoreEnum] = None,
	  /** The minimum sensitivity level that triggers the condition. */
		minimumSensitivityScore: Option[Schema.GooglePrivacyDlpV2PubSubCondition.MinimumSensitivityScoreEnum] = None
	)
	
	case class GooglePrivacyDlpV2Regex(
	  /** Pattern defining the regular expression. Its syntax (https://github.com/google/re2/wiki/Syntax) can be found under the google/re2 repository on GitHub. */
		pattern: Option[String] = None,
	  /** The index of the submatch to extract as findings. When not specified, the entire match is returned. No more than 3 may be included. */
		groupIndexes: Option[List[Int]] = None
	)
	
	case class GooglePrivacyDlpV2RecordKey(
	  /** BigQuery key */
		datastoreKey: Option[Schema.GooglePrivacyDlpV2DatastoreKey] = None,
	  /** Datastore key */
		bigQueryKey: Option[Schema.GooglePrivacyDlpV2BigQueryKey] = None,
	  /** Values of identifying columns in the given row. Order of values matches the order of `identifying_fields` specified in the scanning request. */
		idValues: Option[List[String]] = None
	)
	
	case class GooglePrivacyDlpV2RecordTransformations(
	  /** Configuration defining which records get suppressed entirely. Records that match any suppression rule are omitted from the output. */
		recordSuppressions: Option[List[Schema.GooglePrivacyDlpV2RecordSuppression]] = None,
	  /** Transform the record by applying various field transformations. */
		fieldTransformations: Option[List[Schema.GooglePrivacyDlpV2FieldTransformation]] = None
	)
	
	case class GooglePrivacyDlpV2CloudStorageRegex(
	  /** Optional. Regex to test the bucket name against. If empty, all buckets match. Example: "marketing2021" or "(marketing)\d{4}" will both match the bucket gs://marketing2021 */
		bucketNameRegex: Option[String] = None,
	  /** Optional. For organizations, if unset, will match all projects. */
		projectIdRegex: Option[String] = None
	)
}
