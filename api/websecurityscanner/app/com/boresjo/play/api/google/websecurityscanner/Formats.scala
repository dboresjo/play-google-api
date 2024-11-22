package com.boresjo.play.api.google.websecurityscanner

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtVulnerableParameters: Format[Schema.VulnerableParameters] = Json.format[Schema.VulnerableParameters]
	given fmtViolatingResource: Format[Schema.ViolatingResource] = Json.format[Schema.ViolatingResource]
	given fmtHeader: Format[Schema.Header] = Json.format[Schema.Header]
	given fmtScanRun: Format[Schema.ScanRun] = Json.format[Schema.ScanRun]
	given fmtScanRunExecutionStateEnum: Format[Schema.ScanRun.ExecutionStateEnum] = JsonEnumFormat[Schema.ScanRun.ExecutionStateEnum]
	given fmtScanRunErrorTrace: Format[Schema.ScanRunErrorTrace] = Json.format[Schema.ScanRunErrorTrace]
	given fmtScanRunWarningTrace: Format[Schema.ScanRunWarningTrace] = Json.format[Schema.ScanRunWarningTrace]
	given fmtScanRunResultStateEnum: Format[Schema.ScanRun.ResultStateEnum] = JsonEnumFormat[Schema.ScanRun.ResultStateEnum]
	given fmtXss: Format[Schema.Xss] = Json.format[Schema.Xss]
	given fmtXssAttackVectorEnum: Format[Schema.Xss.AttackVectorEnum] = JsonEnumFormat[Schema.Xss.AttackVectorEnum]
	given fmtListFindingTypeStatsResponse: Format[Schema.ListFindingTypeStatsResponse] = Json.format[Schema.ListFindingTypeStatsResponse]
	given fmtFindingTypeStats: Format[Schema.FindingTypeStats] = Json.format[Schema.FindingTypeStats]
	given fmtAuthentication: Format[Schema.Authentication] = Json.format[Schema.Authentication]
	given fmtGoogleAccount: Format[Schema.GoogleAccount] = Json.format[Schema.GoogleAccount]
	given fmtCustomAccount: Format[Schema.CustomAccount] = Json.format[Schema.CustomAccount]
	given fmtIapCredential: Format[Schema.IapCredential] = Json.format[Schema.IapCredential]
	given fmtListFindingsResponse: Format[Schema.ListFindingsResponse] = Json.format[Schema.ListFindingsResponse]
	given fmtFinding: Format[Schema.Finding] = Json.format[Schema.Finding]
	given fmtSchedule: Format[Schema.Schedule] = Json.format[Schema.Schedule]
	given fmtScanConfig: Format[Schema.ScanConfig] = Json.format[Schema.ScanConfig]
	given fmtScanConfigRiskLevelEnum: Format[Schema.ScanConfig.RiskLevelEnum] = JsonEnumFormat[Schema.ScanConfig.RiskLevelEnum]
	given fmtScanConfigUserAgentEnum: Format[Schema.ScanConfig.UserAgentEnum] = JsonEnumFormat[Schema.ScanConfig.UserAgentEnum]
	given fmtScanConfigExportToSecurityCommandCenterEnum: Format[Schema.ScanConfig.ExportToSecurityCommandCenterEnum] = JsonEnumFormat[Schema.ScanConfig.ExportToSecurityCommandCenterEnum]
	given fmtScanConfigError: Format[Schema.ScanConfigError] = Json.format[Schema.ScanConfigError]
	given fmtScanRunErrorTraceCodeEnum: Format[Schema.ScanRunErrorTrace.CodeEnum] = JsonEnumFormat[Schema.ScanRunErrorTrace.CodeEnum]
	given fmtStartScanRunRequest: Format[Schema.StartScanRunRequest] = Json.format[Schema.StartScanRunRequest]
	given fmtCrawledUrl: Format[Schema.CrawledUrl] = Json.format[Schema.CrawledUrl]
	given fmtScanRunWarningTraceCodeEnum: Format[Schema.ScanRunWarningTrace.CodeEnum] = JsonEnumFormat[Schema.ScanRunWarningTrace.CodeEnum]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtIapTestServiceAccountInfo: Format[Schema.IapTestServiceAccountInfo] = Json.format[Schema.IapTestServiceAccountInfo]
	given fmtForm: Format[Schema.Form] = Json.format[Schema.Form]
	given fmtStopScanRunRequest: Format[Schema.StopScanRunRequest] = Json.format[Schema.StopScanRunRequest]
	given fmtListCrawledUrlsResponse: Format[Schema.ListCrawledUrlsResponse] = Json.format[Schema.ListCrawledUrlsResponse]
	given fmtListScanConfigsResponse: Format[Schema.ListScanConfigsResponse] = Json.format[Schema.ListScanConfigsResponse]
	given fmtScanConfigErrorCodeEnum: Format[Schema.ScanConfigError.CodeEnum] = JsonEnumFormat[Schema.ScanConfigError.CodeEnum]
	given fmtOutdatedLibrary: Format[Schema.OutdatedLibrary] = Json.format[Schema.OutdatedLibrary]
	given fmtXxe: Format[Schema.Xxe] = Json.format[Schema.Xxe]
	given fmtVulnerableHeaders: Format[Schema.VulnerableHeaders] = Json.format[Schema.VulnerableHeaders]
	given fmtFindingSeverityEnum: Format[Schema.Finding.SeverityEnum] = JsonEnumFormat[Schema.Finding.SeverityEnum]
	given fmtXxePayloadLocationEnum: Format[Schema.Xxe.PayloadLocationEnum] = JsonEnumFormat[Schema.Xxe.PayloadLocationEnum]
	given fmtListScanRunsResponse: Format[Schema.ListScanRunsResponse] = Json.format[Schema.ListScanRunsResponse]
}
