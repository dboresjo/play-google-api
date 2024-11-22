package com.boresjo.play.api.google.playintegrity

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtEnvironmentDetails: Format[Schema.EnvironmentDetails] = Json.format[Schema.EnvironmentDetails]
	given fmtAppAccessRiskVerdict: Format[Schema.AppAccessRiskVerdict] = Json.format[Schema.AppAccessRiskVerdict]
	given fmtEnvironmentDetailsPlayProtectVerdictEnum: Format[Schema.EnvironmentDetails.PlayProtectVerdictEnum] = JsonEnumFormat[Schema.EnvironmentDetails.PlayProtectVerdictEnum]
	given fmtTestingDetails: Format[Schema.TestingDetails] = Json.format[Schema.TestingDetails]
	given fmtWriteDeviceRecallRequest: Format[Schema.WriteDeviceRecallRequest] = Json.format[Schema.WriteDeviceRecallRequest]
	given fmtValues: Format[Schema.Values] = Json.format[Schema.Values]
	given fmtAccountActivity: Format[Schema.AccountActivity] = Json.format[Schema.AccountActivity]
	given fmtAccountActivityActivityLevelEnum: Format[Schema.AccountActivity.ActivityLevelEnum] = JsonEnumFormat[Schema.AccountActivity.ActivityLevelEnum]
	given fmtAccountDetails: Format[Schema.AccountDetails] = Json.format[Schema.AccountDetails]
	given fmtAccountDetailsAppLicensingVerdictEnum: Format[Schema.AccountDetails.AppLicensingVerdictEnum] = JsonEnumFormat[Schema.AccountDetails.AppLicensingVerdictEnum]
	given fmtTokenPayloadExternal: Format[Schema.TokenPayloadExternal] = Json.format[Schema.TokenPayloadExternal]
	given fmtAppIntegrity: Format[Schema.AppIntegrity] = Json.format[Schema.AppIntegrity]
	given fmtRequestDetails: Format[Schema.RequestDetails] = Json.format[Schema.RequestDetails]
	given fmtDeviceIntegrity: Format[Schema.DeviceIntegrity] = Json.format[Schema.DeviceIntegrity]
	given fmtAppIntegrityAppRecognitionVerdictEnum: Format[Schema.AppIntegrity.AppRecognitionVerdictEnum] = JsonEnumFormat[Schema.AppIntegrity.AppRecognitionVerdictEnum]
	given fmtDecodeIntegrityTokenResponse: Format[Schema.DecodeIntegrityTokenResponse] = Json.format[Schema.DecodeIntegrityTokenResponse]
	given fmtDeviceRecall: Format[Schema.DeviceRecall] = Json.format[Schema.DeviceRecall]
	given fmtWriteDates: Format[Schema.WriteDates] = Json.format[Schema.WriteDates]
	given fmtAppAccessRiskVerdictPlayOrSystemAppsEnum: Format[Schema.AppAccessRiskVerdict.PlayOrSystemAppsEnum] = JsonEnumFormat[Schema.AppAccessRiskVerdict.PlayOrSystemAppsEnum]
	given fmtAppAccessRiskVerdictOtherAppsEnum: Format[Schema.AppAccessRiskVerdict.OtherAppsEnum] = JsonEnumFormat[Schema.AppAccessRiskVerdict.OtherAppsEnum]
	given fmtAppAccessRiskVerdictAppsDetectedEnum: Format[Schema.AppAccessRiskVerdict.AppsDetectedEnum] = JsonEnumFormat[Schema.AppAccessRiskVerdict.AppsDetectedEnum]
	given fmtWriteDeviceRecallResponse: Format[Schema.WriteDeviceRecallResponse] = Json.format[Schema.WriteDeviceRecallResponse]
	given fmtRecentDeviceActivity: Format[Schema.RecentDeviceActivity] = Json.format[Schema.RecentDeviceActivity]
	given fmtDeviceIntegrityDeviceRecognitionVerdictEnum: Format[Schema.DeviceIntegrity.DeviceRecognitionVerdictEnum] = JsonEnumFormat[Schema.DeviceIntegrity.DeviceRecognitionVerdictEnum]
	given fmtRecentDeviceActivityDeviceActivityLevelEnum: Format[Schema.RecentDeviceActivity.DeviceActivityLevelEnum] = JsonEnumFormat[Schema.RecentDeviceActivity.DeviceActivityLevelEnum]
	given fmtDecodeIntegrityTokenRequest: Format[Schema.DecodeIntegrityTokenRequest] = Json.format[Schema.DecodeIntegrityTokenRequest]
}
