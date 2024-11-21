package com.boresjo.play.api.google.playintegrity

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putSchemaAppAccessRiskVerdict: Conversion[Schema.AppAccessRiskVerdict, Option[Schema.AppAccessRiskVerdict]] = (fun: Schema.AppAccessRiskVerdict) => Option(fun)
		given putSchemaEnvironmentDetailsPlayProtectVerdictEnum: Conversion[Schema.EnvironmentDetails.PlayProtectVerdictEnum, Option[Schema.EnvironmentDetails.PlayProtectVerdictEnum]] = (fun: Schema.EnvironmentDetails.PlayProtectVerdictEnum) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaValues: Conversion[Schema.Values, Option[Schema.Values]] = (fun: Schema.Values) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaAccountActivityActivityLevelEnum: Conversion[Schema.AccountActivity.ActivityLevelEnum, Option[Schema.AccountActivity.ActivityLevelEnum]] = (fun: Schema.AccountActivity.ActivityLevelEnum) => Option(fun)
		given putSchemaAccountActivity: Conversion[Schema.AccountActivity, Option[Schema.AccountActivity]] = (fun: Schema.AccountActivity) => Option(fun)
		given putSchemaAccountDetailsAppLicensingVerdictEnum: Conversion[Schema.AccountDetails.AppLicensingVerdictEnum, Option[Schema.AccountDetails.AppLicensingVerdictEnum]] = (fun: Schema.AccountDetails.AppLicensingVerdictEnum) => Option(fun)
		given putSchemaEnvironmentDetails: Conversion[Schema.EnvironmentDetails, Option[Schema.EnvironmentDetails]] = (fun: Schema.EnvironmentDetails) => Option(fun)
		given putSchemaAppIntegrity: Conversion[Schema.AppIntegrity, Option[Schema.AppIntegrity]] = (fun: Schema.AppIntegrity) => Option(fun)
		given putSchemaRequestDetails: Conversion[Schema.RequestDetails, Option[Schema.RequestDetails]] = (fun: Schema.RequestDetails) => Option(fun)
		given putSchemaTestingDetails: Conversion[Schema.TestingDetails, Option[Schema.TestingDetails]] = (fun: Schema.TestingDetails) => Option(fun)
		given putSchemaDeviceIntegrity: Conversion[Schema.DeviceIntegrity, Option[Schema.DeviceIntegrity]] = (fun: Schema.DeviceIntegrity) => Option(fun)
		given putSchemaAccountDetails: Conversion[Schema.AccountDetails, Option[Schema.AccountDetails]] = (fun: Schema.AccountDetails) => Option(fun)
		given putSchemaAppIntegrityAppRecognitionVerdictEnum: Conversion[Schema.AppIntegrity.AppRecognitionVerdictEnum, Option[Schema.AppIntegrity.AppRecognitionVerdictEnum]] = (fun: Schema.AppIntegrity.AppRecognitionVerdictEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaTokenPayloadExternal: Conversion[Schema.TokenPayloadExternal, Option[Schema.TokenPayloadExternal]] = (fun: Schema.TokenPayloadExternal) => Option(fun)
		given putSchemaWriteDates: Conversion[Schema.WriteDates, Option[Schema.WriteDates]] = (fun: Schema.WriteDates) => Option(fun)
		given putSchemaAppAccessRiskVerdictPlayOrSystemAppsEnum: Conversion[Schema.AppAccessRiskVerdict.PlayOrSystemAppsEnum, Option[Schema.AppAccessRiskVerdict.PlayOrSystemAppsEnum]] = (fun: Schema.AppAccessRiskVerdict.PlayOrSystemAppsEnum) => Option(fun)
		given putSchemaAppAccessRiskVerdictOtherAppsEnum: Conversion[Schema.AppAccessRiskVerdict.OtherAppsEnum, Option[Schema.AppAccessRiskVerdict.OtherAppsEnum]] = (fun: Schema.AppAccessRiskVerdict.OtherAppsEnum) => Option(fun)
		given putListSchemaAppAccessRiskVerdictAppsDetectedEnum: Conversion[List[Schema.AppAccessRiskVerdict.AppsDetectedEnum], Option[List[Schema.AppAccessRiskVerdict.AppsDetectedEnum]]] = (fun: List[Schema.AppAccessRiskVerdict.AppsDetectedEnum]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaDeviceRecall: Conversion[Schema.DeviceRecall, Option[Schema.DeviceRecall]] = (fun: Schema.DeviceRecall) => Option(fun)
		given putSchemaRecentDeviceActivity: Conversion[Schema.RecentDeviceActivity, Option[Schema.RecentDeviceActivity]] = (fun: Schema.RecentDeviceActivity) => Option(fun)
		given putListSchemaDeviceIntegrityDeviceRecognitionVerdictEnum: Conversion[List[Schema.DeviceIntegrity.DeviceRecognitionVerdictEnum], Option[List[Schema.DeviceIntegrity.DeviceRecognitionVerdictEnum]]] = (fun: List[Schema.DeviceIntegrity.DeviceRecognitionVerdictEnum]) => Option(fun)
		given putSchemaRecentDeviceActivityDeviceActivityLevelEnum: Conversion[Schema.RecentDeviceActivity.DeviceActivityLevelEnum, Option[Schema.RecentDeviceActivity.DeviceActivityLevelEnum]] = (fun: Schema.RecentDeviceActivity.DeviceActivityLevelEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
