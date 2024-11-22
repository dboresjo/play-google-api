package com.boresjo.play.api.google.adsense

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtAccount: Format[Schema.Account] = Json.format[Schema.Account]
	given fmtTimeZone: Format[Schema.TimeZone] = Json.format[Schema.TimeZone]
	given fmtAccountStateEnum: Format[Schema.Account.StateEnum] = JsonEnumFormat[Schema.Account.StateEnum]
	given fmtListAccountsResponse: Format[Schema.ListAccountsResponse] = Json.format[Schema.ListAccountsResponse]
	given fmtListChildAccountsResponse: Format[Schema.ListChildAccountsResponse] = Json.format[Schema.ListChildAccountsResponse]
	given fmtAdBlockingRecoveryTag: Format[Schema.AdBlockingRecoveryTag] = Json.format[Schema.AdBlockingRecoveryTag]
	given fmtListAdClientsResponse: Format[Schema.ListAdClientsResponse] = Json.format[Schema.ListAdClientsResponse]
	given fmtAdClient: Format[Schema.AdClient] = Json.format[Schema.AdClient]
	given fmtAdClientStateEnum: Format[Schema.AdClient.StateEnum] = JsonEnumFormat[Schema.AdClient.StateEnum]
	given fmtAdClientAdCode: Format[Schema.AdClientAdCode] = Json.format[Schema.AdClientAdCode]
	given fmtAdUnit: Format[Schema.AdUnit] = Json.format[Schema.AdUnit]
	given fmtAdUnitStateEnum: Format[Schema.AdUnit.StateEnum] = JsonEnumFormat[Schema.AdUnit.StateEnum]
	given fmtContentAdsSettings: Format[Schema.ContentAdsSettings] = Json.format[Schema.ContentAdsSettings]
	given fmtContentAdsSettingsTypeEnum: Format[Schema.ContentAdsSettings.TypeEnum] = JsonEnumFormat[Schema.ContentAdsSettings.TypeEnum]
	given fmtListAdUnitsResponse: Format[Schema.ListAdUnitsResponse] = Json.format[Schema.ListAdUnitsResponse]
	given fmtAdUnitAdCode: Format[Schema.AdUnitAdCode] = Json.format[Schema.AdUnitAdCode]
	given fmtListLinkedAdUnitsResponse: Format[Schema.ListLinkedAdUnitsResponse] = Json.format[Schema.ListLinkedAdUnitsResponse]
	given fmtListAlertsResponse: Format[Schema.ListAlertsResponse] = Json.format[Schema.ListAlertsResponse]
	given fmtAlert: Format[Schema.Alert] = Json.format[Schema.Alert]
	given fmtAlertSeverityEnum: Format[Schema.Alert.SeverityEnum] = JsonEnumFormat[Schema.Alert.SeverityEnum]
	given fmtCustomChannel: Format[Schema.CustomChannel] = Json.format[Schema.CustomChannel]
	given fmtListCustomChannelsResponse: Format[Schema.ListCustomChannelsResponse] = Json.format[Schema.ListCustomChannelsResponse]
	given fmtListLinkedCustomChannelsResponse: Format[Schema.ListLinkedCustomChannelsResponse] = Json.format[Schema.ListLinkedCustomChannelsResponse]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtListPaymentsResponse: Format[Schema.ListPaymentsResponse] = Json.format[Schema.ListPaymentsResponse]
	given fmtPayment: Format[Schema.Payment] = Json.format[Schema.Payment]
	given fmtDate: Format[Schema.Date] = Json.format[Schema.Date]
	given fmtPolicyIssue: Format[Schema.PolicyIssue] = Json.format[Schema.PolicyIssue]
	given fmtPolicyIssueEntityTypeEnum: Format[Schema.PolicyIssue.EntityTypeEnum] = JsonEnumFormat[Schema.PolicyIssue.EntityTypeEnum]
	given fmtPolicyTopic: Format[Schema.PolicyTopic] = Json.format[Schema.PolicyTopic]
	given fmtPolicyIssueActionEnum: Format[Schema.PolicyIssue.ActionEnum] = JsonEnumFormat[Schema.PolicyIssue.ActionEnum]
	given fmtListPolicyIssuesResponse: Format[Schema.ListPolicyIssuesResponse] = Json.format[Schema.ListPolicyIssuesResponse]
	given fmtReportResult: Format[Schema.ReportResult] = Json.format[Schema.ReportResult]
	given fmtHeader: Format[Schema.Header] = Json.format[Schema.Header]
	given fmtRow: Format[Schema.Row] = Json.format[Schema.Row]
	given fmtHeaderTypeEnum: Format[Schema.Header.TypeEnum] = JsonEnumFormat[Schema.Header.TypeEnum]
	given fmtCell: Format[Schema.Cell] = Json.format[Schema.Cell]
	given fmtHttpBody: Format[Schema.HttpBody] = Json.format[Schema.HttpBody]
	given fmtSavedReport: Format[Schema.SavedReport] = Json.format[Schema.SavedReport]
	given fmtListSavedReportsResponse: Format[Schema.ListSavedReportsResponse] = Json.format[Schema.ListSavedReportsResponse]
	given fmtSite: Format[Schema.Site] = Json.format[Schema.Site]
	given fmtSiteStateEnum: Format[Schema.Site.StateEnum] = JsonEnumFormat[Schema.Site.StateEnum]
	given fmtListSitesResponse: Format[Schema.ListSitesResponse] = Json.format[Schema.ListSitesResponse]
	given fmtUrlChannel: Format[Schema.UrlChannel] = Json.format[Schema.UrlChannel]
	given fmtListUrlChannelsResponse: Format[Schema.ListUrlChannelsResponse] = Json.format[Schema.ListUrlChannelsResponse]
}
