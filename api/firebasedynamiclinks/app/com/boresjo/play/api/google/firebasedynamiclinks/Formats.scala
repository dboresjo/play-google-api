package com.boresjo.play.api.google.firebasedynamiclinks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtCreateManagedShortLinkResponse: Format[Schema.CreateManagedShortLinkResponse] = Json.format[Schema.CreateManagedShortLinkResponse]
	given fmtDynamicLinkWarning: Format[Schema.DynamicLinkWarning] = Json.format[Schema.DynamicLinkWarning]
	given fmtManagedShortLink: Format[Schema.ManagedShortLink] = Json.format[Schema.ManagedShortLink]
	given fmtCreateShortDynamicLinkRequest: Format[Schema.CreateShortDynamicLinkRequest] = Json.format[Schema.CreateShortDynamicLinkRequest]
	given fmtSuffix: Format[Schema.Suffix] = Json.format[Schema.Suffix]
	given fmtDynamicLinkInfo: Format[Schema.DynamicLinkInfo] = Json.format[Schema.DynamicLinkInfo]
	given fmtCreateManagedShortLinkRequest: Format[Schema.CreateManagedShortLinkRequest] = Json.format[Schema.CreateManagedShortLinkRequest]
	given fmtGetIosReopenAttributionRequest: Format[Schema.GetIosReopenAttributionRequest] = Json.format[Schema.GetIosReopenAttributionRequest]
	given fmtNavigationInfo: Format[Schema.NavigationInfo] = Json.format[Schema.NavigationInfo]
	given fmtITunesConnectAnalytics: Format[Schema.ITunesConnectAnalytics] = Json.format[Schema.ITunesConnectAnalytics]
	given fmtGetIosPostInstallAttributionResponse: Format[Schema.GetIosPostInstallAttributionResponse] = Json.format[Schema.GetIosPostInstallAttributionResponse]
	given fmtGetIosPostInstallAttributionResponseRequestIpVersionEnum: Format[Schema.GetIosPostInstallAttributionResponse.RequestIpVersionEnum] = JsonEnumFormat[Schema.GetIosPostInstallAttributionResponse.RequestIpVersionEnum]
	given fmtGetIosPostInstallAttributionResponseAttributionConfidenceEnum: Format[Schema.GetIosPostInstallAttributionResponse.AttributionConfidenceEnum] = JsonEnumFormat[Schema.GetIosPostInstallAttributionResponse.AttributionConfidenceEnum]
	given fmtGetIosReopenAttributionResponse: Format[Schema.GetIosReopenAttributionResponse] = Json.format[Schema.GetIosReopenAttributionResponse]
	given fmtIosInfo: Format[Schema.IosInfo] = Json.format[Schema.IosInfo]
	given fmtDynamicLinkStats: Format[Schema.DynamicLinkStats] = Json.format[Schema.DynamicLinkStats]
	given fmtDynamicLinkEventStat: Format[Schema.DynamicLinkEventStat] = Json.format[Schema.DynamicLinkEventStat]
	given fmtManagedShortLinkVisibilityEnum: Format[Schema.ManagedShortLink.VisibilityEnum] = JsonEnumFormat[Schema.ManagedShortLink.VisibilityEnum]
	given fmtManagedShortLinkFlaggedAttributeEnum: Format[Schema.ManagedShortLink.FlaggedAttributeEnum] = JsonEnumFormat[Schema.ManagedShortLink.FlaggedAttributeEnum]
	given fmtAnalyticsInfo: Format[Schema.AnalyticsInfo] = Json.format[Schema.AnalyticsInfo]
	given fmtGooglePlayAnalytics: Format[Schema.GooglePlayAnalytics] = Json.format[Schema.GooglePlayAnalytics]
	given fmtAndroidInfo: Format[Schema.AndroidInfo] = Json.format[Schema.AndroidInfo]
	given fmtDynamicLinkWarningWarningCodeEnum: Format[Schema.DynamicLinkWarning.WarningCodeEnum] = JsonEnumFormat[Schema.DynamicLinkWarning.WarningCodeEnum]
	given fmtDesktopInfo: Format[Schema.DesktopInfo] = Json.format[Schema.DesktopInfo]
	given fmtGetIosPostInstallAttributionRequest: Format[Schema.GetIosPostInstallAttributionRequest] = Json.format[Schema.GetIosPostInstallAttributionRequest]
	given fmtDeviceInfo: Format[Schema.DeviceInfo] = Json.format[Schema.DeviceInfo]
	given fmtGetIosPostInstallAttributionRequestRetrievalMethodEnum: Format[Schema.GetIosPostInstallAttributionRequest.RetrievalMethodEnum] = JsonEnumFormat[Schema.GetIosPostInstallAttributionRequest.RetrievalMethodEnum]
	given fmtGetIosPostInstallAttributionRequestVisualStyleEnum: Format[Schema.GetIosPostInstallAttributionRequest.VisualStyleEnum] = JsonEnumFormat[Schema.GetIosPostInstallAttributionRequest.VisualStyleEnum]
	given fmtSuffixOptionEnum: Format[Schema.Suffix.OptionEnum] = JsonEnumFormat[Schema.Suffix.OptionEnum]
	given fmtSocialMetaTagInfo: Format[Schema.SocialMetaTagInfo] = Json.format[Schema.SocialMetaTagInfo]
	given fmtDynamicLinkEventStatPlatformEnum: Format[Schema.DynamicLinkEventStat.PlatformEnum] = JsonEnumFormat[Schema.DynamicLinkEventStat.PlatformEnum]
	given fmtDynamicLinkEventStatEventEnum: Format[Schema.DynamicLinkEventStat.EventEnum] = JsonEnumFormat[Schema.DynamicLinkEventStat.EventEnum]
	given fmtCreateShortDynamicLinkResponse: Format[Schema.CreateShortDynamicLinkResponse] = Json.format[Schema.CreateShortDynamicLinkResponse]
}
