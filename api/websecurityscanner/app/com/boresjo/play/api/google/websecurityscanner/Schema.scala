package com.boresjo.play.api.google.websecurityscanner

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class VulnerableParameters(
	  /** The vulnerable parameter names. */
		parameterNames: Option[List[String]] = None
	)
	
	case class ViolatingResource(
	  /** URL of this violating resource. */
		resourceUrl: Option[String] = None,
	  /** The MIME type of this resource. */
		contentType: Option[String] = None
	)
	
	case class Header(
	  /** Header value. */
		value: Option[String] = None,
	  /** Header name. */
		name: Option[String] = None
	)
	
	object ScanRun {
		enum ExecutionStateEnum extends Enum[ExecutionStateEnum] { case EXECUTION_STATE_UNSPECIFIED, QUEUED, SCANNING, FINISHED }
		enum ResultStateEnum extends Enum[ResultStateEnum] { case RESULT_STATE_UNSPECIFIED, SUCCESS, ERROR, KILLED }
	}
	case class ScanRun(
	  /** Output only. Whether the scan run has found any vulnerabilities. */
		hasVulnerabilities: Option[Boolean] = None,
	  /** Output only. The execution state of the ScanRun. */
		executionState: Option[Schema.ScanRun.ExecutionStateEnum] = None,
	  /** Output only. The number of URLs crawled during this ScanRun. If the scan is in progress, the value represents the number of URLs crawled up to now. */
		urlsCrawledCount: Option[String] = None,
	  /** Output only. The number of URLs tested during this ScanRun. If the scan is in progress, the value represents the number of URLs tested up to now. The number of URLs tested is usually larger than the number URLS crawled because typically a crawled URL is tested with multiple test payloads. */
		urlsTestedCount: Option[String] = None,
	  /** Output only. If result_state is an ERROR, this field provides the primary reason for scan's termination and more details, if such are available. */
		errorTrace: Option[Schema.ScanRunErrorTrace] = None,
	  /** Output only. The time at which the ScanRun reached termination state - that the ScanRun is either finished or stopped by user. */
		endTime: Option[String] = None,
	  /** Output only. The time at which the ScanRun started. */
		startTime: Option[String] = None,
	  /** Output only. A list of warnings, if such are encountered during this scan run. */
		warningTraces: Option[List[Schema.ScanRunWarningTrace]] = None,
	  /** Output only. The resource name of the ScanRun. The name follows the format of 'projects/{projectId}/scanConfigs/{scanConfigId}/scanRuns/{scanRunId}'. The ScanRun IDs are generated by the system. */
		name: Option[String] = None,
	  /** Output only. The result state of the ScanRun. This field is only available after the execution state reaches "FINISHED". */
		resultState: Option[Schema.ScanRun.ResultStateEnum] = None,
	  /** Output only. The percentage of total completion ranging from 0 to 100. If the scan is in queue, the value is 0. If the scan is running, the value ranges from 0 to 100. If the scan is finished, the value is 100. */
		progressPercent: Option[Int] = None
	)
	
	object Xss {
		enum AttackVectorEnum extends Enum[AttackVectorEnum] { case ATTACK_VECTOR_UNSPECIFIED, LOCAL_STORAGE, SESSION_STORAGE, WINDOW_NAME, REFERRER, FORM_INPUT, COOKIE, POST_MESSAGE, GET_PARAMETERS, URL_FRAGMENT, HTML_COMMENT, POST_PARAMETERS, PROTOCOL, STORED_XSS, SAME_ORIGIN, USER_CONTROLLABLE_URL }
	}
	case class Xss(
	  /** An error message generated by a javascript breakage. */
		errorMessage: Option[String] = None,
	  /** Stack traces leading to the point where the XSS occurred. */
		stackTraces: Option[List[String]] = None,
	  /** The reproduction url for the seeding POST request of a Stored XSS. */
		storedXssSeedingUrl: Option[String] = None,
	  /** The attack vector of the payload triggering this XSS. */
		attackVector: Option[Schema.Xss.AttackVectorEnum] = None
	)
	
	case class ListFindingTypeStatsResponse(
	  /** The list of FindingTypeStats returned. */
		findingTypeStats: Option[List[Schema.FindingTypeStats]] = None
	)
	
	case class Authentication(
	  /** Authentication using a Google account. */
		googleAccount: Option[Schema.GoogleAccount] = None,
	  /** Authentication using a custom account. */
		customAccount: Option[Schema.CustomAccount] = None,
	  /** Authentication using Identity-Aware-Proxy (IAP). */
		iapCredential: Option[Schema.IapCredential] = None
	)
	
	case class ListFindingsResponse(
	  /** The list of Findings returned. */
		findings: Option[List[Schema.Finding]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class Schedule(
	  /** A timestamp indicates when the next run will be scheduled. The value is refreshed by the server after each run. If unspecified, it will default to current server time, which means the scan will be scheduled to start immediately. */
		scheduleTime: Option[String] = None,
	  /** Required. The duration of time between executions in days. */
		intervalDurationDays: Option[Int] = None
	)
	
	object ScanConfig {
		enum RiskLevelEnum extends Enum[RiskLevelEnum] { case RISK_LEVEL_UNSPECIFIED, NORMAL, LOW }
		enum UserAgentEnum extends Enum[UserAgentEnum] { case USER_AGENT_UNSPECIFIED, CHROME_LINUX, CHROME_ANDROID, SAFARI_IPHONE }
		enum ExportToSecurityCommandCenterEnum extends Enum[ExportToSecurityCommandCenterEnum] { case EXPORT_TO_SECURITY_COMMAND_CENTER_UNSPECIFIED, ENABLED, DISABLED }
	}
	case class ScanConfig(
	  /** Whether the scan config is managed by Web Security Scanner, output only. */
		managedScan: Option[Boolean] = None,
	  /** The resource name of the ScanConfig. The name follows the format of 'projects/{projectId}/scanConfigs/{scanConfigId}'. The ScanConfig IDs are generated by the system. */
		name: Option[String] = None,
	  /** The schedule of the ScanConfig. */
		schedule: Option[Schema.Schedule] = None,
	  /** Required. The user provided display name of the ScanConfig. */
		displayName: Option[String] = None,
	  /** The maximum QPS during scanning. A valid value ranges from 5 to 20 inclusively. If the field is unspecified or its value is set 0, server will default to 15. Other values outside of [5, 20] range will be rejected with INVALID_ARGUMENT error. */
		maxQps: Option[Int] = None,
	  /** The risk level selected for the scan */
		riskLevel: Option[Schema.ScanConfig.RiskLevelEnum] = None,
	  /** Required. The starting URLs from which the scanner finds site pages. */
		startingUrls: Option[List[String]] = None,
	  /** The user agent used during scanning. */
		userAgent: Option[Schema.ScanConfig.UserAgentEnum] = None,
	  /** The excluded URL patterns as described in https://cloud.google.com/security-command-center/docs/how-to-use-web-security-scanner#excluding_urls */
		blacklistPatterns: Option[List[String]] = None,
	  /** Whether the scan configuration has enabled static IP address scan feature. If enabled, the scanner will access applications from static IP addresses. */
		staticIpScan: Option[Boolean] = None,
	  /** Controls export of scan configurations and results to Security Command Center. */
		exportToSecurityCommandCenter: Option[Schema.ScanConfig.ExportToSecurityCommandCenterEnum] = None,
	  /** Whether to keep scanning even if most requests return HTTP error codes. */
		ignoreHttpStatusErrors: Option[Boolean] = None,
	  /** The authentication configuration. If specified, service will use the authentication configuration during scanning. */
		authentication: Option[Schema.Authentication] = None
	)
	
	object ScanRunErrorTrace {
		enum CodeEnum extends Enum[CodeEnum] { case CODE_UNSPECIFIED, INTERNAL_ERROR, SCAN_CONFIG_ISSUE, AUTHENTICATION_CONFIG_ISSUE, TIMED_OUT_WHILE_SCANNING, TOO_MANY_REDIRECTS, TOO_MANY_HTTP_ERRORS, STARTING_URLS_CRAWL_HTTP_ERRORS }
	}
	case class ScanRunErrorTrace(
	  /** Output only. If the scan encounters SCAN_CONFIG_ISSUE error, this field has the error message encountered during scan configuration validation that is performed before each scan run. */
		scanConfigError: Option[Schema.ScanConfigError] = None,
	  /** Output only. If the scan encounters TOO_MANY_HTTP_ERRORS, this field indicates the most common HTTP error code, if such is available. For example, if this code is 404, the scan has encountered too many NOT_FOUND responses. */
		mostCommonHttpErrorCode: Option[Int] = None,
	  /** Output only. Indicates the error reason code. */
		code: Option[Schema.ScanRunErrorTrace.CodeEnum] = None
	)
	
	case class StartScanRunRequest(
	
	)
	
	case class CrawledUrl(
	  /** Output only. The URL that was crawled. */
		url: Option[String] = None,
	  /** Output only. The http method of the request that was used to visit the URL, in uppercase. */
		httpMethod: Option[String] = None,
	  /** Output only. The body of the request that was used to visit the URL. */
		body: Option[String] = None
	)
	
	object ScanRunWarningTrace {
		enum CodeEnum extends Enum[CodeEnum] { case CODE_UNSPECIFIED, INSUFFICIENT_CRAWL_RESULTS, TOO_MANY_CRAWL_RESULTS, TOO_MANY_FUZZ_TASKS, BLOCKED_BY_IAP, NO_STARTING_URL_FOUND_FOR_MANAGED_SCAN }
	}
	case class ScanRunWarningTrace(
	  /** Output only. Indicates the warning code. */
		code: Option[Schema.ScanRunWarningTrace.CodeEnum] = None
	)
	
	case class Empty(
	
	)
	
	case class GoogleAccount(
	  /** Required. Input only. The password of the Google account. The credential is stored encrypted and not returned in any response nor included in audit logs. */
		password: Option[String] = None,
	  /** Required. The user name of the Google account. */
		username: Option[String] = None
	)
	
	case class IapTestServiceAccountInfo(
	  /** Required. Describes OAuth2 client id of resources protected by Identity-Aware-Proxy (IAP). */
		targetAudienceClientId: Option[String] = None
	)
	
	case class Form(
	  /** ! The names of form fields related to the vulnerability. */
		fields: Option[List[String]] = None,
	  /** ! The URI where to send the form when it's submitted. */
		actionUri: Option[String] = None
	)
	
	case class IapCredential(
	  /** Authentication configuration when Web-Security-Scanner service account is added in Identity-Aware-Proxy (IAP) access policies. */
		iapTestServiceAccountInfo: Option[Schema.IapTestServiceAccountInfo] = None
	)
	
	case class StopScanRunRequest(
	
	)
	
	case class CustomAccount(
	  /** Required. The login form URL of the website. */
		loginUrl: Option[String] = None,
	  /** Required. Input only. The password of the custom account. The credential is stored encrypted and not returned in any response nor included in audit logs. */
		password: Option[String] = None,
	  /** Required. The user name of the custom account. */
		username: Option[String] = None
	)
	
	case class ListCrawledUrlsResponse(
	  /** The list of CrawledUrls returned. */
		crawledUrls: Option[List[Schema.CrawledUrl]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class ListScanConfigsResponse(
	  /** The list of ScanConfigs returned. */
		scanConfigs: Option[List[Schema.ScanConfig]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	object ScanConfigError {
		enum CodeEnum extends Enum[CodeEnum] { case CODE_UNSPECIFIED, OK, INTERNAL_ERROR, APPENGINE_API_BACKEND_ERROR, APPENGINE_API_NOT_ACCESSIBLE, APPENGINE_DEFAULT_HOST_MISSING, CANNOT_USE_GOOGLE_COM_ACCOUNT, CANNOT_USE_OWNER_ACCOUNT, COMPUTE_API_BACKEND_ERROR, COMPUTE_API_NOT_ACCESSIBLE, CUSTOM_LOGIN_URL_DOES_NOT_BELONG_TO_CURRENT_PROJECT, CUSTOM_LOGIN_URL_MALFORMED, CUSTOM_LOGIN_URL_MAPPED_TO_NON_ROUTABLE_ADDRESS, CUSTOM_LOGIN_URL_MAPPED_TO_UNRESERVED_ADDRESS, CUSTOM_LOGIN_URL_HAS_NON_ROUTABLE_IP_ADDRESS, CUSTOM_LOGIN_URL_HAS_UNRESERVED_IP_ADDRESS, DUPLICATE_SCAN_NAME, INVALID_FIELD_VALUE, FAILED_TO_AUTHENTICATE_TO_TARGET, FINDING_TYPE_UNSPECIFIED, FORBIDDEN_TO_SCAN_COMPUTE, FORBIDDEN_UPDATE_TO_MANAGED_SCAN, MALFORMED_FILTER, MALFORMED_RESOURCE_NAME, PROJECT_INACTIVE, REQUIRED_FIELD, RESOURCE_NAME_INCONSISTENT, SCAN_ALREADY_RUNNING, SCAN_NOT_RUNNING, SEED_URL_DOES_NOT_BELONG_TO_CURRENT_PROJECT, SEED_URL_MALFORMED, SEED_URL_MAPPED_TO_NON_ROUTABLE_ADDRESS, SEED_URL_MAPPED_TO_UNRESERVED_ADDRESS, SEED_URL_HAS_NON_ROUTABLE_IP_ADDRESS, SEED_URL_HAS_UNRESERVED_IP_ADDRESS, SERVICE_ACCOUNT_NOT_CONFIGURED, TOO_MANY_SCANS, UNABLE_TO_RESOLVE_PROJECT_INFO, UNSUPPORTED_BLACKLIST_PATTERN_FORMAT, UNSUPPORTED_FILTER, UNSUPPORTED_FINDING_TYPE, UNSUPPORTED_URL_SCHEME, CLOUD_ASSET_INVENTORY_ASSET_NOT_FOUND }
	}
	case class ScanConfigError(
	  /** Output only. Indicates the full name of the ScanConfig field that triggers this error, for example "scan_config.max_qps". This field is provided for troubleshooting purposes only and its actual value can change in the future. */
		fieldName: Option[String] = None,
	  /** Output only. Indicates the reason code for a configuration failure. */
		code: Option[Schema.ScanConfigError.CodeEnum] = None
	)
	
	object Finding {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, CRITICAL, HIGH, MEDIUM, LOW }
	}
	case class Finding(
	  /** Output only. An addon containing information reported for a vulnerability with an HTML form, if any. */
		form: Option[Schema.Form] = None,
	  /** Output only. The URL where the browser lands when the vulnerability is detected. */
		finalUrl: Option[String] = None,
	  /** Output only. The body of the request that triggered the vulnerability. */
		body: Option[String] = None,
	  /** Output only. The URL produced by the server-side fuzzer and used in the request that triggered the vulnerability. */
		fuzzedUrl: Option[String] = None,
	  /** Output only. An addon containing information reported for an XSS, if any. */
		xss: Option[Schema.Xss] = None,
	  /** Output only. An addon containing information about outdated libraries. */
		outdatedLibrary: Option[Schema.OutdatedLibrary] = None,
	  /** Output only. The description of the vulnerability. */
		description: Option[String] = None,
	  /** Output only. The URL containing human-readable payload that user can leverage to reproduce the vulnerability. */
		reproductionUrl: Option[String] = None,
	  /** Output only. An addon containing information about request parameters which were found to be vulnerable. */
		vulnerableParameters: Option[Schema.VulnerableParameters] = None,
	  /** Output only. An addon containing information reported for an XXE, if any. */
		xxe: Option[Schema.Xxe] = None,
	  /** Output only. If the vulnerability was originated from nested IFrame, the immediate parent IFrame is reported. */
		frameUrl: Option[String] = None,
	  /** Output only. The http method of the request that triggered the vulnerability, in uppercase. */
		httpMethod: Option[String] = None,
	  /** Output only. The resource name of the Finding. The name follows the format of 'projects/{projectId}/scanConfigs/{scanConfigId}/scanruns/{scanRunId}/findings/{findingId}'. The finding IDs are generated by the system. */
		name: Option[String] = None,
	  /** Output only. An addon containing information about vulnerable or missing HTTP headers. */
		vulnerableHeaders: Option[Schema.VulnerableHeaders] = None,
	  /** Output only. The tracking ID uniquely identifies a vulnerability instance across multiple ScanRuns. */
		trackingId: Option[String] = None,
	  /** Output only. The type of the Finding. Detailed and up-to-date information on findings can be found here: https://cloud.google.com/security-command-center/docs/how-to-remediate-web-security-scanner-findings */
		findingType: Option[String] = None,
	  /** Output only. An addon containing detailed information regarding any resource causing the vulnerability such as JavaScript sources, image, audio files, etc. */
		violatingResource: Option[Schema.ViolatingResource] = None,
	  /** Output only. The severity level of the reported vulnerability. */
		severity: Option[Schema.Finding.SeverityEnum] = None
	)
	
	case class FindingTypeStats(
	  /** Output only. The finding type associated with the stats. */
		findingType: Option[String] = None,
	  /** Output only. The count of findings belonging to this finding type. */
		findingCount: Option[Int] = None
	)
	
	case class VulnerableHeaders(
	  /** List of vulnerable headers. */
		headers: Option[List[Schema.Header]] = None,
	  /** List of missing headers. */
		missingHeaders: Option[List[Schema.Header]] = None
	)
	
	object Xxe {
		enum PayloadLocationEnum extends Enum[PayloadLocationEnum] { case LOCATION_UNSPECIFIED, COMPLETE_REQUEST_BODY }
	}
	case class Xxe(
	  /** Location within the request where the payload was placed. */
		payloadLocation: Option[Schema.Xxe.PayloadLocationEnum] = None,
	  /** The XML string that triggered the XXE vulnerability. Non-payload values might be redacted. */
		payloadValue: Option[String] = None
	)
	
	case class ListScanRunsResponse(
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** The list of ScanRuns returned. */
		scanRuns: Option[List[Schema.ScanRun]] = None
	)
	
	case class OutdatedLibrary(
	  /** URLs to learn more information about the vulnerabilities in the library. */
		learnMoreUrls: Option[List[String]] = None,
	  /** The version number. */
		version: Option[String] = None,
	  /** The name of the outdated library. */
		libraryName: Option[String] = None
	)
}
