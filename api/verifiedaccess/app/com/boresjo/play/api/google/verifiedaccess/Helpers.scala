package com.boresjo.play.api.google.verifiedaccess

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaDeviceSignals: Conversion[Schema.DeviceSignals, Option[Schema.DeviceSignals]] = (fun: Schema.DeviceSignals) => Option(fun)
		given putSchemaVerifyChallengeResponseResultKeyTrustLevelEnum: Conversion[Schema.VerifyChallengeResponseResult.KeyTrustLevelEnum, Option[Schema.VerifyChallengeResponseResult.KeyTrustLevelEnum]] = (fun: Schema.VerifyChallengeResponseResult.KeyTrustLevelEnum) => Option(fun)
		given putSchemaVerifyChallengeResponseResultProfileKeyTrustLevelEnum: Conversion[Schema.VerifyChallengeResponseResult.ProfileKeyTrustLevelEnum, Option[Schema.VerifyChallengeResponseResult.ProfileKeyTrustLevelEnum]] = (fun: Schema.VerifyChallengeResponseResult.ProfileKeyTrustLevelEnum) => Option(fun)
		given putSchemaDeviceSignalsOperatingSystemEnum: Conversion[Schema.DeviceSignals.OperatingSystemEnum, Option[Schema.DeviceSignals.OperatingSystemEnum]] = (fun: Schema.DeviceSignals.OperatingSystemEnum) => Option(fun)
		given putSchemaDeviceSignalsDiskEncryptionEnum: Conversion[Schema.DeviceSignals.DiskEncryptionEnum, Option[Schema.DeviceSignals.DiskEncryptionEnum]] = (fun: Schema.DeviceSignals.DiskEncryptionEnum) => Option(fun)
		given putSchemaDeviceSignalsOsFirewallEnum: Conversion[Schema.DeviceSignals.OsFirewallEnum, Option[Schema.DeviceSignals.OsFirewallEnum]] = (fun: Schema.DeviceSignals.OsFirewallEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaDeviceSignalsScreenLockSecuredEnum: Conversion[Schema.DeviceSignals.ScreenLockSecuredEnum, Option[Schema.DeviceSignals.ScreenLockSecuredEnum]] = (fun: Schema.DeviceSignals.ScreenLockSecuredEnum) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaDeviceSignalsSecureBootModeEnum: Conversion[Schema.DeviceSignals.SecureBootModeEnum, Option[Schema.DeviceSignals.SecureBootModeEnum]] = (fun: Schema.DeviceSignals.SecureBootModeEnum) => Option(fun)
		given putSchemaDeviceSignalsSafeBrowsingProtectionLevelEnum: Conversion[Schema.DeviceSignals.SafeBrowsingProtectionLevelEnum, Option[Schema.DeviceSignals.SafeBrowsingProtectionLevelEnum]] = (fun: Schema.DeviceSignals.SafeBrowsingProtectionLevelEnum) => Option(fun)
		given putSchemaDeviceSignalsPasswordProtectionWarningTriggerEnum: Conversion[Schema.DeviceSignals.PasswordProtectionWarningTriggerEnum, Option[Schema.DeviceSignals.PasswordProtectionWarningTriggerEnum]] = (fun: Schema.DeviceSignals.PasswordProtectionWarningTriggerEnum) => Option(fun)
		given putSchemaDeviceSignalsRealtimeUrlCheckModeEnum: Conversion[Schema.DeviceSignals.RealtimeUrlCheckModeEnum, Option[Schema.DeviceSignals.RealtimeUrlCheckModeEnum]] = (fun: Schema.DeviceSignals.RealtimeUrlCheckModeEnum) => Option(fun)
		given putSchemaCrowdStrikeAgent: Conversion[Schema.CrowdStrikeAgent, Option[Schema.CrowdStrikeAgent]] = (fun: Schema.CrowdStrikeAgent) => Option(fun)
		given putSchemaDeviceSignalsTriggerEnum: Conversion[Schema.DeviceSignals.TriggerEnum, Option[Schema.DeviceSignals.TriggerEnum]] = (fun: Schema.DeviceSignals.TriggerEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
