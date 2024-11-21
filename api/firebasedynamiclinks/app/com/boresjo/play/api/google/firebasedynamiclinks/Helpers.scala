package com.boresjo.play.api.google.firebasedynamiclinks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaDynamicLinkWarning: Conversion[List[Schema.DynamicLinkWarning], Option[List[Schema.DynamicLinkWarning]]] = (fun: List[Schema.DynamicLinkWarning]) => Option(fun)
		given putSchemaManagedShortLink: Conversion[Schema.ManagedShortLink, Option[Schema.ManagedShortLink]] = (fun: Schema.ManagedShortLink) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaSuffix: Conversion[Schema.Suffix, Option[Schema.Suffix]] = (fun: Schema.Suffix) => Option(fun)
		given putSchemaDynamicLinkInfo: Conversion[Schema.DynamicLinkInfo, Option[Schema.DynamicLinkInfo]] = (fun: Schema.DynamicLinkInfo) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaGetIosPostInstallAttributionResponseRequestIpVersionEnum: Conversion[Schema.GetIosPostInstallAttributionResponse.RequestIpVersionEnum, Option[Schema.GetIosPostInstallAttributionResponse.RequestIpVersionEnum]] = (fun: Schema.GetIosPostInstallAttributionResponse.RequestIpVersionEnum) => Option(fun)
		given putSchemaGetIosPostInstallAttributionResponseAttributionConfidenceEnum: Conversion[Schema.GetIosPostInstallAttributionResponse.AttributionConfidenceEnum, Option[Schema.GetIosPostInstallAttributionResponse.AttributionConfidenceEnum]] = (fun: Schema.GetIosPostInstallAttributionResponse.AttributionConfidenceEnum) => Option(fun)
		given putListSchemaDynamicLinkEventStat: Conversion[List[Schema.DynamicLinkEventStat], Option[List[Schema.DynamicLinkEventStat]]] = (fun: List[Schema.DynamicLinkEventStat]) => Option(fun)
		given putSchemaManagedShortLinkVisibilityEnum: Conversion[Schema.ManagedShortLink.VisibilityEnum, Option[Schema.ManagedShortLink.VisibilityEnum]] = (fun: Schema.ManagedShortLink.VisibilityEnum) => Option(fun)
		given putListSchemaManagedShortLinkFlaggedAttributeEnum: Conversion[List[Schema.ManagedShortLink.FlaggedAttributeEnum], Option[List[Schema.ManagedShortLink.FlaggedAttributeEnum]]] = (fun: List[Schema.ManagedShortLink.FlaggedAttributeEnum]) => Option(fun)
		given putSchemaITunesConnectAnalytics: Conversion[Schema.ITunesConnectAnalytics, Option[Schema.ITunesConnectAnalytics]] = (fun: Schema.ITunesConnectAnalytics) => Option(fun)
		given putSchemaGooglePlayAnalytics: Conversion[Schema.GooglePlayAnalytics, Option[Schema.GooglePlayAnalytics]] = (fun: Schema.GooglePlayAnalytics) => Option(fun)
		given putSchemaDynamicLinkWarningWarningCodeEnum: Conversion[Schema.DynamicLinkWarning.WarningCodeEnum, Option[Schema.DynamicLinkWarning.WarningCodeEnum]] = (fun: Schema.DynamicLinkWarning.WarningCodeEnum) => Option(fun)
		given putSchemaDeviceInfo: Conversion[Schema.DeviceInfo, Option[Schema.DeviceInfo]] = (fun: Schema.DeviceInfo) => Option(fun)
		given putSchemaGetIosPostInstallAttributionRequestRetrievalMethodEnum: Conversion[Schema.GetIosPostInstallAttributionRequest.RetrievalMethodEnum, Option[Schema.GetIosPostInstallAttributionRequest.RetrievalMethodEnum]] = (fun: Schema.GetIosPostInstallAttributionRequest.RetrievalMethodEnum) => Option(fun)
		given putSchemaGetIosPostInstallAttributionRequestVisualStyleEnum: Conversion[Schema.GetIosPostInstallAttributionRequest.VisualStyleEnum, Option[Schema.GetIosPostInstallAttributionRequest.VisualStyleEnum]] = (fun: Schema.GetIosPostInstallAttributionRequest.VisualStyleEnum) => Option(fun)
		given putSchemaSuffixOptionEnum: Conversion[Schema.Suffix.OptionEnum, Option[Schema.Suffix.OptionEnum]] = (fun: Schema.Suffix.OptionEnum) => Option(fun)
		given putSchemaDesktopInfo: Conversion[Schema.DesktopInfo, Option[Schema.DesktopInfo]] = (fun: Schema.DesktopInfo) => Option(fun)
		given putSchemaAnalyticsInfo: Conversion[Schema.AnalyticsInfo, Option[Schema.AnalyticsInfo]] = (fun: Schema.AnalyticsInfo) => Option(fun)
		given putSchemaAndroidInfo: Conversion[Schema.AndroidInfo, Option[Schema.AndroidInfo]] = (fun: Schema.AndroidInfo) => Option(fun)
		given putSchemaNavigationInfo: Conversion[Schema.NavigationInfo, Option[Schema.NavigationInfo]] = (fun: Schema.NavigationInfo) => Option(fun)
		given putSchemaSocialMetaTagInfo: Conversion[Schema.SocialMetaTagInfo, Option[Schema.SocialMetaTagInfo]] = (fun: Schema.SocialMetaTagInfo) => Option(fun)
		given putSchemaIosInfo: Conversion[Schema.IosInfo, Option[Schema.IosInfo]] = (fun: Schema.IosInfo) => Option(fun)
		given putSchemaDynamicLinkEventStatPlatformEnum: Conversion[Schema.DynamicLinkEventStat.PlatformEnum, Option[Schema.DynamicLinkEventStat.PlatformEnum]] = (fun: Schema.DynamicLinkEventStat.PlatformEnum) => Option(fun)
		given putSchemaDynamicLinkEventStatEventEnum: Conversion[Schema.DynamicLinkEventStat.EventEnum, Option[Schema.DynamicLinkEventStat.EventEnum]] = (fun: Schema.DynamicLinkEventStat.EventEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
