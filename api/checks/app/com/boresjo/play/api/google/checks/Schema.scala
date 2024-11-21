package com.boresjo.play.api.google.checks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleChecksReportV1alphaCheckEndpointRestrictionViolationEvidenceEndpointDetails(
	  /** The endpoint in violation. */
		endpoint: Option[Schema.GoogleChecksReportV1alphaEndpoint] = None
	)
	
	case class GoogleChecksReportV1alphaPolicyFragment(
	  /** HTML content. */
		htmlContent: Option[String] = None,
	  /** Policy URL. */
		sourceUri: Option[String] = None
	)
	
	case class GoogleChecksReportV1alphaCheckPermissionRestrictionViolationEvidence(
	  /** Permissions in violation. */
		permissionDetails: Option[List[Schema.GoogleChecksReportV1alphaCheckPermissionRestrictionViolationEvidencePermissionDetails]] = None
	)
	
	case class GoogleChecksAccountV1alphaApp(
	  /** The app's title. */
		title: Option[String] = None,
	  /** The resource name of the app. Example: `accounts/123/apps/456` */
		name: Option[String] = None
	)
	
	case class GoogleChecksAisafetyV1alphaClassifyContentRequestInputContent(
	  /** Content in text format. */
		textInput: Option[Schema.GoogleChecksAisafetyV1alphaTextInput] = None
	)
	
	case class GoogleChecksReportV1alphaCheckSdkEvidence(
	  /** The SDK that was found in your app. */
		sdk: Option[Schema.GoogleChecksReportV1alphaSdk] = None
	)
	
	case class GoogleChecksReportV1alphaDataMonitoringEndpointResult(
	  /** The number of times this endpoint was contacted by your app. */
		hitCount: Option[Int] = None,
	  /** The endpoint that was contacted by your app. */
		endpoint: Option[Schema.GoogleChecksReportV1alphaEndpoint] = None,
	  /** Metadata about the result. */
		metadata: Option[Schema.GoogleChecksReportV1alphaDataMonitoringResultMetadata] = None
	)
	
	case class GoogleChecksReportV1alphaDataMonitoringSdkResult(
	  /** Metadata about the result. */
		metadata: Option[Schema.GoogleChecksReportV1alphaDataMonitoringResultMetadata] = None,
	  /** The SDK that was found in your app. */
		sdk: Option[Schema.GoogleChecksReportV1alphaSdk] = None
	)
	
	case class ListOperationsResponse(
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None,
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.Operation]] = None
	)
	
	case class GoogleChecksReportV1alphaCheckSdkRestrictionViolationEvidence(
	  /** SDKs in violation. */
		sdkDetails: Option[List[Schema.GoogleChecksReportV1alphaCheckSdkRestrictionViolationEvidenceSdkDetails]] = None
	)
	
	case class GoogleChecksReportV1alphaCheckEvidence(
	  /** Evidence concerning SDK issues. */
		sdkIssues: Option[List[Schema.GoogleChecksReportV1alphaCheckSdkIssueEvidence]] = None,
	  /** Evidence concerning endpoints that were contacted by your app. */
		endpoints: Option[List[Schema.GoogleChecksReportV1alphaCheckEndpointEvidence]] = None,
	  /** Evidence collected from your privacy policy(s). */
		privacyPolicyTexts: Option[List[Schema.GoogleChecksReportV1alphaCheckPrivacyPolicyTextEvidence]] = None,
	  /** Evidence concerning permissions that were found in your app. */
		permissions: Option[List[Schema.GoogleChecksReportV1alphaCheckPermissionEvidence]] = None,
	  /** Evidence concerning SDKs that were found in your app. */
		sdks: Option[List[Schema.GoogleChecksReportV1alphaCheckSdkEvidence]] = None,
	  /** Evidence concerning data security. */
		dataSecurity: Option[Schema.GoogleChecksReportV1alphaCheckDataSecurityEvidence] = None,
	  /** Evidence collected from permission restriction violation analysis. */
		permissionRestrictionViolations: Option[List[Schema.GoogleChecksReportV1alphaCheckPermissionRestrictionViolationEvidence]] = None,
	  /** Evidence collected from SDK restriction violation analysis. */
		sdkRestrictionViolations: Option[List[Schema.GoogleChecksReportV1alphaCheckSdkRestrictionViolationEvidence]] = None,
	  /** Evidence concerning data types found in your app. */
		dataTypes: Option[List[Schema.GoogleChecksReportV1alphaCheckDataTypeEvidence]] = None,
	  /** Evidence collected from endpoint restriction violation analysis. */
		endpointRestrictionViolations: Option[List[Schema.GoogleChecksReportV1alphaCheckEndpointRestrictionViolationEvidence]] = None
	)
	
	case class GoogleChecksAisafetyV1alphaClassifyContentResponse(
	  /** Results of the classification for each policy. */
		policyResults: Option[List[Schema.GoogleChecksAisafetyV1alphaClassifyContentResponsePolicyResult]] = None
	)
	
	case class GoogleChecksReportV1alphaPermission(
	  /** Permission identifier. */
		id: Option[String] = None
	)
	
	case class GoogleChecksReportV1alphaCheckPrivacyPolicyTextEvidence(
	  /** The privacy policy fragment that was used during the check. */
		policyFragment: Option[Schema.GoogleChecksReportV1alphaPolicyFragment] = None
	)
	
	case class CancelOperationRequest(
	
	)
	
	case class WaitOperationRequest(
	  /** The maximum duration to wait before timing out. If left blank, the wait will be at most the time permitted by the underlying HTTP/RPC protocol. If RPC context deadline is also specified, the shorter one will be used. */
		timeout: Option[String] = None
	)
	
	object GoogleChecksAisafetyV1alphaClassifyContentResponsePolicyResult {
		enum ViolationResultEnum extends Enum[ViolationResultEnum] { case VIOLATION_RESULT_UNSPECIFIED, VIOLATIVE, NON_VIOLATIVE, CLASSIFICATION_ERROR }
		enum PolicyTypeEnum extends Enum[PolicyTypeEnum] { case POLICY_TYPE_UNSPECIFIED, DANGEROUS_CONTENT, PII_SOLICITING_RECITING, HARASSMENT, SEXUALLY_EXPLICIT, HATE_SPEECH, MEDICAL_INFO, VIOLENCE_AND_GORE, OBSCENITY_AND_PROFANITY }
	}
	case class GoogleChecksAisafetyV1alphaClassifyContentResponsePolicyResult(
	  /** Result of the classification for the policy. */
		violationResult: Option[Schema.GoogleChecksAisafetyV1alphaClassifyContentResponsePolicyResult.ViolationResultEnum] = None,
	  /** Type of the policy. */
		policyType: Option[Schema.GoogleChecksAisafetyV1alphaClassifyContentResponsePolicyResult.PolicyTypeEnum] = None,
	  /** Final score for the results of this policy. */
		score: Option[BigDecimal] = None
	)
	
	case class GoogleChecksAisafetyV1alphaClassifyContentRequestContext(
	  /** Optional. Prompt that generated the model response. */
		prompt: Option[String] = None
	)
	
	object GoogleChecksReportV1alphaCheckCitation {
		enum TypeEnum extends Enum[TypeEnum] { case CITATION_TYPE_UNSPECIFIED, COPPA, GDPR, FERPA, CAL_OPPA, CCPA, SOPIPA, LGPD, CPRA, VCDPA, GOOGLE_PLAY_POLICY, APP_STORE_POLICY, CPA, CTDPA, UCPA, PIPEDA, ALBERTA_PIPA, QUEBEC_ACT, QUEBEC_BILL_64, CHINA_PIPL, SOUTH_KOREA_PIPA, SOUTH_AFRICA_POPIA, JAPAN_APPI, INDIA_DPDPA, OCPA, TDPSA, MCDPA }
	}
	case class GoogleChecksReportV1alphaCheckCitation(
	  /** Citation type. */
		`type`: Option[Schema.GoogleChecksReportV1alphaCheckCitation.TypeEnum] = None
	)
	
	object GoogleChecksReportV1alphaCheckStateMetadata {
		enum BadgesEnum extends Enum[BadgesEnum] { case CHECK_STATE_BADGE_UNSPECIFIED, NEWLY_FAILING, RECENTLY_FAILING, RESOLVED }
	}
	case class GoogleChecksReportV1alphaCheckStateMetadata(
	  /** The time when the check first started failing. */
		firstFailingTime: Option[String] = None,
	  /** Indicators related to the check state. */
		badges: Option[List[Schema.GoogleChecksReportV1alphaCheckStateMetadata.BadgesEnum]] = None,
	  /** The last time the check failed. */
		lastFailingTime: Option[String] = None
	)
	
	case class GoogleChecksReportV1alphaCheckDataSecurityEvidence(
	  /** Evidence related to data in transit. */
		dataInTransitInfo: Option[List[Schema.GoogleChecksReportV1alphaCheckDataSecurityEvidenceDataInTransitInfo]] = None
	)
	
	case class GoogleChecksReportV1alphaListReportsResponse(
	  /** The reports for the specified app. */
		reports: Option[List[Schema.GoogleChecksReportV1alphaReport]] = None,
	  /** A token which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleChecksReportV1alphaDataMonitoring(
	  /** Data types that your app shares or collects. */
		dataTypes: Option[List[Schema.GoogleChecksReportV1alphaDataMonitoringDataTypeResult]] = None,
	  /** Permissions that your app uses. */
		permissions: Option[List[Schema.GoogleChecksReportV1alphaDataMonitoringPermissionResult]] = None,
	  /** SDKs that your app uses. */
		sdks: Option[List[Schema.GoogleChecksReportV1alphaDataMonitoringSdkResult]] = None,
	  /** Endpoints that were found by dynamic analysis of your app. */
		endpoints: Option[List[Schema.GoogleChecksReportV1alphaDataMonitoringEndpointResult]] = None
	)
	
	object GoogleChecksReportV1alphaCheck {
		enum SeverityEnum extends Enum[SeverityEnum] { case CHECK_SEVERITY_UNSPECIFIED, PRIORITY, POTENTIAL, OPPORTUNITY }
		enum StateEnum extends Enum[StateEnum] { case CHECK_STATE_UNSPECIFIED, PASSED, FAILED, UNCHECKED }
		enum TypeEnum extends Enum[TypeEnum] { case CHECK_TYPE_UNSPECIFIED, STORE_LISTING_PRIVACY_POLICY_LINK_PRESENT, PRIVACY_POLICY_UPDATE_DATE_RECENT, PRIVACY_POLICY_GDPR_GENERAL_RULES, PRIVACY_POLICY_CCPA_GENERAL_RULES, PRIVACY_POLICY_COLLECTION_CATEGORIES_DATA_NOTICE, PRIVACY_POLICY_PROCESSING_PURPOSE_DATA_NOTICE, PRIVACY_POLICY_SHARING_CATEGORIES_DATA_NOTICE, PRIVACY_POLICY_DATA_RETENTION_NOTICE, PRIVACY_POLICY_CONTACT_DETAILS_NOTICE, PRIVACY_POLICY_CHILDREN_GENERAL_RULES, PRIVACY_POLICY_DATA_TYPE_PHONE_NUMBER, PRIVACY_POLICY_DATA_TYPE_USER_ACCOUNT_INFO, PRIVACY_POLICY_DATA_TYPE_PRECISE_LOCATION, PRIVACY_POLICY_DATA_TYPE_DEVICE_ID, PRIVACY_POLICY_DATA_TYPE_APPS_ON_DEVICE, PRIVACY_POLICY_DATA_TYPE_CONTACTS, PRIVACY_POLICY_DATA_TYPE_TEXT_MESSAGES, PRIVACY_POLICY_DATA_TYPE_PII, PRIVACY_POLICY_DATA_TYPE_PII_CATEGORIES, PRIVACY_POLICY_DATA_TYPE_HEALTH_AND_BIOMETRIC, PRIVACY_POLICY_BRAZIL_LGPD_GENERAL_RULES, PRIVACY_POLICY_VIRGINIA_VCDPA_GENERAL_RULES, PRIVACY_POLICY_AFFILIATION_MENTION, PRIVACY_POLICY_RIGHT_TO_DELETE_NOTICE, PRIVACY_POLICY_RIGHT_TO_ACCESS_NOTICE, PRIVACY_POLICY_RIGHT_TO_RECTIFICATION_NOTICE, PRIVACY_POLICY_RIGHT_TO_KNOW_ABOUT_SELLING_NOTICE, PRIVACY_POLICY_RIGHT_TO_KNOW_ABOUT_SHARING_NOTICE, PRIVACY_POLICY_RIGHT_TO_OPT_OUT_FROM_SELLING_NOTICE, PRIVACY_POLICY_METHOD_TO_OPT_OUT_FROM_SELLING_OR_SHARING_NOTICE, PRIVACY_POLICY_DATA_CONTROLLER_IDENTITY, PRIVACY_POLICY_DPO_CONTACT_DETAILS, PRIVACY_POLICY_RIGHT_TO_LODGE_A_COMPLAINT, PRIVACY_POLICY_LEGAL_BASIS, PRIVACY_POLICY_CHILDREN_INFO_COLLECTION, PRIVACY_POLICY_CHILDREN_INFO_USAGE_PURPOSES, PRIVACY_POLICY_CHILDREN_INFO_DISCLOSURE_PRACTICES, PRIVACY_POLICY_CHILDREN_INFO_PUBLICITY, PRIVACY_POLICY_PARENTS_METHOD_OF_INFO_DELETION, PRIVACY_POLICY_PARENTS_METHOD_TO_INFO_REVIEW, PRIVACY_POLICY_PARENTS_METHOD_TO_STOP_FURTHER_INFO_COLLECTION_USE, PRIVACY_POLICY_PARENTS_RIGHT_TO_INFO_DELETION, PRIVACY_POLICY_PARENTS_RIGHT_TO_INFO_REVIEW, PRIVACY_POLICY_PARENTS_RIGHT_TO_STOP_FURTHER_INFO_COLLECTION_USE, PRIVACY_POLICY_PSL_APPROXIMATE_LOCATION, PRIVACY_POLICY_PSL_PRECISE_LOCATION, PRIVACY_POLICY_PSL_NAME, PRIVACY_POLICY_PSL_EMAIL_ADDRESS, PRIVACY_POLICY_PSL_USER_IDENTIFIERS, PRIVACY_POLICY_PSL_ADDRESS, PRIVACY_POLICY_PSL_PHONE_NUMBER, PRIVACY_POLICY_PSL_RACE_AND_ETHNICITY, PRIVACY_POLICY_PSL_CREDIT_SCORE, PRIVACY_POLICY_PSL_PURCHASE_HISTORY, PRIVACY_POLICY_PSL_HEALTH_INFO, PRIVACY_POLICY_PSL_FITNESS_INFO, PRIVACY_POLICY_PSL_EMAIL_MESSAGES, PRIVACY_POLICY_PSL_TEXT_MESSAGES, PRIVACY_POLICY_PSL_PHOTOS, PRIVACY_POLICY_PSL_VIDEOS, PRIVACY_POLICY_PSL_MUSIC_FILES, PRIVACY_POLICY_PSL_VOICE_OR_SOUND_RECORDINGS, PRIVACY_POLICY_PSL_FILES_AND_DOCS, PRIVACY_POLICY_PSL_CALENDAR_EVENTS, PRIVACY_POLICY_PSL_CONTACTS, PRIVACY_POLICY_PSL_APP_INTERACTIONS, PRIVACY_POLICY_PSL_IN_APP_SEARCH_HISTORY, PRIVACY_POLICY_PSL_WEB_BROWSING_HISTORY, PRIVACY_POLICY_PSL_INSTALLED_APPS, PRIVACY_POLICY_PSL_CRASH_LOGS, PRIVACY_POLICY_PSL_DIAGNOSTICS, PRIVACY_POLICY_PSL_DEVICE_OR_OTHER_IDS, DATA_MONITORING_NEW_ENDPOINT, DATA_MONITORING_NEW_PERMISSION, DATA_MONITORING_NEW_DATA_TYPE, DATA_MONITORING_NEW_SDK, DATA_MONITORING_ENCRYPTION, DATA_MONITORING_NEW_DATA_TYPE_VERSION_DIFF, DATA_MONITORING_NEW_ENDPOINT_VERSION_DIFF, DATA_MONITORING_NEW_PERMISSION_VERSION_DIFF, DATA_MONITORING_NEW_SDK_VERSION_DIFF, DATA_MONITORING_SDKS_DENYLIST_VIOLATION, DATA_MONITORING_PERMISSIONS_DENYLIST_VIOLATION, DATA_MONITORING_ENDPOINTS_DENYLIST_VIOLATION, DATA_MONITORING_OUTDATED_SDK_VERSION, DATA_MONITORING_CRITICAL_SDK_ISSUE, PRIVACY_POLICY_DATA_TYPE_SENSITIVE_INFO, DATA_MONITORING_PII_LOGCAT_LEAK, DATA_MONITORING_MINIMIZE_PERMISSION_MEDIA, DATA_MONITORING_MINIMIZE_PERMISSION_CAMERA, DATA_MONITORING_MINIMIZE_PERMISSION_DOCUMENTS }
	}
	case class GoogleChecksReportV1alphaCheck(
	  /** Regions that are impacted by the check. For more info, see https://google.aip.dev/143#countries-and-regions. */
		regionCodes: Option[List[String]] = None,
	  /** Regulations and policies that serve as the legal basis for the check. */
		citations: Option[List[Schema.GoogleChecksReportV1alphaCheckCitation]] = None,
	  /** Additional information about the check state in relation to past reports. */
		stateMetadata: Option[Schema.GoogleChecksReportV1alphaCheckStateMetadata] = None,
	  /** The urgency or risk level of the check. */
		severity: Option[Schema.GoogleChecksReportV1alphaCheck.SeverityEnum] = None,
	  /** The result after running the check. */
		state: Option[Schema.GoogleChecksReportV1alphaCheck.StateEnum] = None,
	  /** The type of check that was run. A type will only appear once in a report's list of checks. */
		`type`: Option[Schema.GoogleChecksReportV1alphaCheck.TypeEnum] = None,
	  /** Evidence that substantiates the check result. */
		evidence: Option[Schema.GoogleChecksReportV1alphaCheckEvidence] = None
	)
	
	object GoogleChecksReportV1alphaAnalyzeUploadRequest {
		enum AppBinaryFileTypeEnum extends Enum[AppBinaryFileTypeEnum] { case APP_BINARY_FILE_TYPE_UNSPECIFIED, ANDROID_APK, ANDROID_AAB, IOS_IPA }
	}
	case class GoogleChecksReportV1alphaAnalyzeUploadRequest(
	  /** Optional. Git commit hash or changelist number associated with the upload. */
		codeReferenceId: Option[String] = None,
	  /** Optional. The type of the uploaded app binary. If not provided, the server assumes APK file for Android and IPA file for iOS. */
		appBinaryFileType: Option[Schema.GoogleChecksReportV1alphaAnalyzeUploadRequest.AppBinaryFileTypeEnum] = None
	)
	
	case class GoogleChecksReportV1alphaCheckEndpointEvidence(
	  /** The endpoint that was contacted by your app. */
		endpoint: Option[Schema.GoogleChecksReportV1alphaEndpoint] = None
	)
	
	case class GoogleChecksAisafetyV1alphaTextInput(
	  /** Optional. Language of the text in ISO 639-1 format. If the language is invalid or not specified, the system will try to detect it. */
		languageCode: Option[String] = None,
	  /** Actual piece of text to be classified. */
		content: Option[String] = None
	)
	
	case class GoogleChecksReportV1alphaDataTypeEndpointEvidenceAttributedSdk(
	  /** SDK that is attributed to the exfiltration. */
		sdk: Option[Schema.GoogleChecksReportV1alphaSdk] = None
	)
	
	case class GoogleChecksReportV1alphaCheckEndpointRestrictionViolationEvidence(
	  /** Endpoints in violation. */
		endpointDetails: Option[List[Schema.GoogleChecksReportV1alphaCheckEndpointRestrictionViolationEvidenceEndpointDetails]] = None
	)
	
	case class GoogleChecksReportV1alphaEndpoint(
	  /** Domain name (e.g. ads.google.com). */
		domain: Option[String] = None
	)
	
	object GoogleChecksReportV1alphaDataMonitoringResultMetadata {
		enum BadgesEnum extends Enum[BadgesEnum] { case DATA_MONITORING_RESULT_BADGE_UNSPECIFIED, NEW }
	}
	case class GoogleChecksReportV1alphaDataMonitoringResultMetadata(
	  /** The timestamp when this result was first detected within the last 8 weeks. If not set, it wasn't detected within the last 8 weeks. */
		firstDetectedTime: Option[String] = None,
	  /** Your app's version name when this result was last detected within the last 8 weeks. If not set, it wasn't detected within the last 8 weeks. */
		lastDetectedAppVersion: Option[String] = None,
	  /** Badges that apply to this result. */
		badges: Option[List[Schema.GoogleChecksReportV1alphaDataMonitoringResultMetadata.BadgesEnum]] = None,
	  /** The timestamp when this result was last detected within the last 8 weeks. If not set, it wasn't detected within the last 8 weeks. */
		lastDetectedTime: Option[String] = None
	)
	
	object GoogleChecksReportV1alphaCheckDataTypeEvidence {
		enum DataTypeEnum extends Enum[DataTypeEnum] { case DATA_TYPE_UNSPECIFIED, DATA_TYPE_APPROXIMATE_LOCATION, DATA_TYPE_PRECISE_LOCATION, DATA_TYPE_PERSONAL_NAME, DATA_TYPE_EMAIL_ADDRESS, DATA_TYPE_USER_IDS, DATA_TYPE_PHYSICAL_ADDRESS, DATA_TYPE_PHONE_NUMBER, DATA_TYPE_RACE_AND_ETHNICITY, DATA_TYPE_POLITICAL_OR_RELIGIOUS_BELIEFS, DATA_TYPE_SEXUAL_ORIENTATION, DATA_TYPE_OTHER_PERSONAL_INFO, DATA_TYPE_PAYMENT_INFO, DATA_TYPE_PURCHASE_HISTORY, DATA_TYPE_CREDIT_SCORE, DATA_TYPE_OTHER_FINANCIAL_INFO, DATA_TYPE_HEALTH_INFO, DATA_TYPE_FITNESS_INFO, DATA_TYPE_EMAILS, DATA_TYPE_TEXT_MESSAGES, DATA_TYPE_OTHER_IN_APP_MESSAGES, DATA_TYPE_PHOTOS, DATA_TYPE_VIDEOS, DATA_TYPE_VOICE_OR_SOUND_RECORDINGS, DATA_TYPE_MUSIC_FILES, DATA_TYPE_OTHER_AUDIO_FILES, DATA_TYPE_FILES_AND_DOCS, DATA_TYPE_CALENDAR_EVENTS, DATA_TYPE_CONTACTS, DATA_TYPE_APP_INTERACTIONS, DATA_TYPE_IN_APP_SEARCH_HISTORY, DATA_TYPE_INSTALLED_APPS, DATA_TYPE_OTHER_USER_GENERATED_CONTENT, DATA_TYPE_OTHER_ACTIONS, DATA_TYPE_WEB_BROWSING_HISTORY, DATA_TYPE_CRASH_LOGS, DATA_TYPE_PERFORMANCE_DIAGNOSTICS, DATA_TYPE_OTHER_APP_PERFORMANCE_DATA, DATA_TYPE_DEVICE_OR_OTHER_IDS }
	}
	case class GoogleChecksReportV1alphaCheckDataTypeEvidence(
	  /** Evidence collected about the data type. */
		dataTypeEvidence: Option[Schema.GoogleChecksReportV1alphaDataTypeEvidence] = None,
	  /** The data type that was found in your app. */
		dataType: Option[Schema.GoogleChecksReportV1alphaCheckDataTypeEvidence.DataTypeEnum] = None
	)
	
	object GoogleChecksReportV1alphaDataMonitoringDataTypeResult {
		enum DataTypeEnum extends Enum[DataTypeEnum] { case DATA_TYPE_UNSPECIFIED, DATA_TYPE_APPROXIMATE_LOCATION, DATA_TYPE_PRECISE_LOCATION, DATA_TYPE_PERSONAL_NAME, DATA_TYPE_EMAIL_ADDRESS, DATA_TYPE_USER_IDS, DATA_TYPE_PHYSICAL_ADDRESS, DATA_TYPE_PHONE_NUMBER, DATA_TYPE_RACE_AND_ETHNICITY, DATA_TYPE_POLITICAL_OR_RELIGIOUS_BELIEFS, DATA_TYPE_SEXUAL_ORIENTATION, DATA_TYPE_OTHER_PERSONAL_INFO, DATA_TYPE_PAYMENT_INFO, DATA_TYPE_PURCHASE_HISTORY, DATA_TYPE_CREDIT_SCORE, DATA_TYPE_OTHER_FINANCIAL_INFO, DATA_TYPE_HEALTH_INFO, DATA_TYPE_FITNESS_INFO, DATA_TYPE_EMAILS, DATA_TYPE_TEXT_MESSAGES, DATA_TYPE_OTHER_IN_APP_MESSAGES, DATA_TYPE_PHOTOS, DATA_TYPE_VIDEOS, DATA_TYPE_VOICE_OR_SOUND_RECORDINGS, DATA_TYPE_MUSIC_FILES, DATA_TYPE_OTHER_AUDIO_FILES, DATA_TYPE_FILES_AND_DOCS, DATA_TYPE_CALENDAR_EVENTS, DATA_TYPE_CONTACTS, DATA_TYPE_APP_INTERACTIONS, DATA_TYPE_IN_APP_SEARCH_HISTORY, DATA_TYPE_INSTALLED_APPS, DATA_TYPE_OTHER_USER_GENERATED_CONTENT, DATA_TYPE_OTHER_ACTIONS, DATA_TYPE_WEB_BROWSING_HISTORY, DATA_TYPE_CRASH_LOGS, DATA_TYPE_PERFORMANCE_DIAGNOSTICS, DATA_TYPE_OTHER_APP_PERFORMANCE_DATA, DATA_TYPE_DEVICE_OR_OTHER_IDS }
	}
	case class GoogleChecksReportV1alphaDataMonitoringDataTypeResult(
	  /** The data type that was shared or collected by your app. */
		dataType: Option[Schema.GoogleChecksReportV1alphaDataMonitoringDataTypeResult.DataTypeEnum] = None,
	  /** Evidence collected about the data type. */
		dataTypeEvidence: Option[Schema.GoogleChecksReportV1alphaDataTypeEvidence] = None,
	  /** Metadata about the result. */
		metadata: Option[Schema.GoogleChecksReportV1alphaDataMonitoringResultMetadata] = None
	)
	
	case class GoogleChecksReportV1alphaCheckPermissionRestrictionViolationEvidencePermissionDetails(
	  /** The permission in violation. */
		permission: Option[Schema.GoogleChecksReportV1alphaPermission] = None
	)
	
	case class Operation(
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleChecksReportV1alphaCheckPermissionEvidence(
	  /** The permission that was found in your app. */
		permission: Option[Schema.GoogleChecksReportV1alphaPermission] = None
	)
	
	case class GoogleChecksReportV1alphaReport(
	  /** Resource name of the report. */
		name: Option[String] = None,
	  /** Information related to data monitoring. */
		dataMonitoring: Option[Schema.GoogleChecksReportV1alphaDataMonitoring] = None,
	  /** A URL to view results. */
		resultsUri: Option[String] = None,
	  /** List of checks that were run on the app bundle. */
		checks: Option[List[Schema.GoogleChecksReportV1alphaCheck]] = None,
	  /** Information about the analyzed app bundle. */
		appBundle: Option[Schema.GoogleChecksReportV1alphaAppBundle] = None
	)
	
	case class GoogleChecksReportV1alphaDataMonitoringPermissionResult(
	  /** The permission that was found in your app. */
		permission: Option[Schema.GoogleChecksReportV1alphaPermission] = None,
	  /** Metadata about the result. */
		metadata: Option[Schema.GoogleChecksReportV1alphaDataMonitoringResultMetadata] = None
	)
	
	case class GoogleChecksReportV1alphaCheckDataSecurityEvidenceDataInTransitInfo(
	  /** The URL contacted by your app. This includes the protocol, domain, and URL parameters. */
		uri: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class Status(
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None,
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None
	)
	
	case class GoogleChecksReportV1alphaCheckSdkIssueEvidence(
	  /** The SDK version. */
		sdkVersion: Option[String] = None,
	  /** The SDK with an issue. */
		sdk: Option[Schema.GoogleChecksReportV1alphaSdk] = None
	)
	
	object GoogleChecksReportV1alphaAppBundle {
		enum ReleaseTypeEnum extends Enum[ReleaseTypeEnum] { case APP_BUNDLE_RELEASE_TYPE_UNSPECIFIED, PUBLIC, PRE_RELEASE }
	}
	case class GoogleChecksReportV1alphaAppBundle(
	  /** Git commit hash or changelist number associated with the release. */
		codeReferenceId: Option[String] = None,
	  /** Unique id of the bundle. For example: "com.google.Gmail". */
		bundleId: Option[String] = None,
	  /** The version used throughout the operating system and store to identify the build such as the Android `versionCode` or iOS `CFBundleVersion`. */
		versionId: Option[String] = None,
	  /** Identifies the type of release. */
		releaseType: Option[Schema.GoogleChecksReportV1alphaAppBundle.ReleaseTypeEnum] = None,
	  /** The user-visible version of the bundle such as the Android `versionName` or iOS `CFBundleShortVersionString`. For example: "7.21.1". */
		version: Option[String] = None
	)
	
	object GoogleChecksReportV1alphaDataTypeEndpointEvidence {
		enum ExfiltratedDataTypeEnum extends Enum[ExfiltratedDataTypeEnum] { case EXFILTRATED_DATA_TYPE_UNSPECIFIED, EXFILTRATED_DATA_TYPE_PHONE_NUMBER, EXFILTRATED_DATA_TYPE_PRECISE_LOCATION, EXFILTRATED_DATA_TYPE_CONTACT_NAME, EXFILTRATED_DATA_TYPE_CONTACT_EMAIL, EXFILTRATED_DATA_TYPE_CONTACT_PHONE_NUMBER, EXFILTRATED_DATA_TYPE_INCOMING_TEXT_NUMBER, EXFILTRATED_DATA_TYPE_INCOMING_TEXT_MESSAGE, EXFILTRATED_DATA_TYPE_OUTGOING_TEXT_NUMBER, EXFILTRATED_DATA_TYPE_OUTGOING_TEXT_MESSAGE, EXFILTRATED_DATA_TYPE_ADVERTISING_ID, EXFILTRATED_DATA_TYPE_ANDROID_ID, EXFILTRATED_DATA_TYPE_IMEI, EXFILTRATED_DATA_TYPE_IMSI, EXFILTRATED_DATA_TYPE_SIM_SERIAL_NUMBER, EXFILTRATED_DATA_TYPE_SSID, EXFILTRATED_DATA_TYPE_ACCOUNT, EXFILTRATED_DATA_TYPE_EXTERNAL_ACCOUNT, EXFILTRATED_DATA_TYPE_INSTALLED_PACKAGES }
	}
	case class GoogleChecksReportV1alphaDataTypeEndpointEvidence(
	  /** Endpoints the data type was sent to. */
		endpointDetails: Option[List[Schema.GoogleChecksReportV1alphaDataTypeEndpointEvidenceEndpointDetails]] = None,
	  /** Set of SDKs that are attributed to the exfiltration. */
		attributedSdks: Option[List[Schema.GoogleChecksReportV1alphaDataTypeEndpointEvidenceAttributedSdk]] = None,
	  /** Type of data that was exfiltrated. */
		exfiltratedDataType: Option[Schema.GoogleChecksReportV1alphaDataTypeEndpointEvidence.ExfiltratedDataTypeEnum] = None
	)
	
	case class GoogleChecksReportV1alphaDataTypePermissionEvidence(
	  /** Permission declared by your app. */
		permission: Option[Schema.GoogleChecksReportV1alphaPermission] = None
	)
	
	case class GoogleChecksReportV1alphaDataTypePrivacyPolicyTextEvidence(
	  /** The privacy policy fragment that implies collection of the data type. */
		policyFragment: Option[Schema.GoogleChecksReportV1alphaPolicyFragment] = None
	)
	
	object GoogleChecksAisafetyV1alphaClassifyContentRequest {
		enum ClassifierVersionEnum extends Enum[ClassifierVersionEnum] { case CLASSIFIER_VERSION_UNSPECIFIED, STABLE, LATEST }
	}
	case class GoogleChecksAisafetyV1alphaClassifyContentRequest(
	  /** Required. List of policies to classify against. */
		policies: Option[List[Schema.GoogleChecksAisafetyV1alphaClassifyContentRequestPolicyConfig]] = None,
	  /** Optional. Context about the input that will be used to help on the classification. */
		context: Option[Schema.GoogleChecksAisafetyV1alphaClassifyContentRequestContext] = None,
	  /** Optional. Version of the classifier to use. If not specified, the latest version will be used. */
		classifierVersion: Option[Schema.GoogleChecksAisafetyV1alphaClassifyContentRequest.ClassifierVersionEnum] = None,
	  /** Required. Content to be classified. */
		input: Option[Schema.GoogleChecksAisafetyV1alphaClassifyContentRequestInputContent] = None
	)
	
	object GoogleChecksAisafetyV1alphaClassifyContentRequestPolicyConfig {
		enum PolicyTypeEnum extends Enum[PolicyTypeEnum] { case POLICY_TYPE_UNSPECIFIED, DANGEROUS_CONTENT, PII_SOLICITING_RECITING, HARASSMENT, SEXUALLY_EXPLICIT, HATE_SPEECH, MEDICAL_INFO, VIOLENCE_AND_GORE, OBSCENITY_AND_PROFANITY }
	}
	case class GoogleChecksAisafetyV1alphaClassifyContentRequestPolicyConfig(
	  /** Optional. Score threshold to use when deciding if the content is violative or non-violative. If not specified, the default 0.5 threshold for the policy will be used. */
		threshold: Option[BigDecimal] = None,
	  /** Required. Type of the policy. */
		policyType: Option[Schema.GoogleChecksAisafetyV1alphaClassifyContentRequestPolicyConfig.PolicyTypeEnum] = None
	)
	
	case class GoogleChecksReportV1alphaDataTypeEndpointEvidenceEndpointDetails(
	  /** Endpoint the data type was sent to. */
		endpoint: Option[Schema.GoogleChecksReportV1alphaEndpoint] = None
	)
	
	case class GoogleChecksReportV1alphaCheckSdkRestrictionViolationEvidenceSdkDetails(
	  /** The SDK in violation. */
		sdk: Option[Schema.GoogleChecksReportV1alphaSdk] = None
	)
	
	case class GoogleChecksReportV1alphaDataTypeEvidence(
	  /** List of endpoints the data type was sent to. */
		endpoints: Option[List[Schema.GoogleChecksReportV1alphaDataTypeEndpointEvidence]] = None,
	  /** List of privacy policy texts that imply collection of the data type. */
		privacyPolicyTexts: Option[List[Schema.GoogleChecksReportV1alphaDataTypePrivacyPolicyTextEvidence]] = None,
	  /** List of included permissions that imply collection of the data type. */
		permissions: Option[List[Schema.GoogleChecksReportV1alphaDataTypePermissionEvidence]] = None
	)
	
	case class GoogleChecksAccountV1alphaListAppsResponse(
	  /** A token which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The apps. */
		apps: Option[List[Schema.GoogleChecksAccountV1alphaApp]] = None
	)
	
	case class GoogleChecksReportV1alphaSdk(
	  /** SDK identifier. */
		id: Option[String] = None
	)
}
