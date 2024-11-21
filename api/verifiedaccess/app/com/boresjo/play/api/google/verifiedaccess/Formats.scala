package com.boresjo.play.api.google.verifiedaccess

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtChallenge: Format[Schema.Challenge] = Json.format[Schema.Challenge]
	given fmtVerifyChallengeResponseRequest: Format[Schema.VerifyChallengeResponseRequest] = Json.format[Schema.VerifyChallengeResponseRequest]
	given fmtVerifyChallengeResponseResult: Format[Schema.VerifyChallengeResponseResult] = Json.format[Schema.VerifyChallengeResponseResult]
	given fmtDeviceSignals: Format[Schema.DeviceSignals] = Json.format[Schema.DeviceSignals]
	given fmtVerifyChallengeResponseResultKeyTrustLevelEnum: Format[Schema.VerifyChallengeResponseResult.KeyTrustLevelEnum] = JsonEnumFormat[Schema.VerifyChallengeResponseResult.KeyTrustLevelEnum]
	given fmtVerifyChallengeResponseResultProfileKeyTrustLevelEnum: Format[Schema.VerifyChallengeResponseResult.ProfileKeyTrustLevelEnum] = JsonEnumFormat[Schema.VerifyChallengeResponseResult.ProfileKeyTrustLevelEnum]
	given fmtDeviceSignalsOperatingSystemEnum: Format[Schema.DeviceSignals.OperatingSystemEnum] = JsonEnumFormat[Schema.DeviceSignals.OperatingSystemEnum]
	given fmtDeviceSignalsDiskEncryptionEnum: Format[Schema.DeviceSignals.DiskEncryptionEnum] = JsonEnumFormat[Schema.DeviceSignals.DiskEncryptionEnum]
	given fmtDeviceSignalsOsFirewallEnum: Format[Schema.DeviceSignals.OsFirewallEnum] = JsonEnumFormat[Schema.DeviceSignals.OsFirewallEnum]
	given fmtDeviceSignalsScreenLockSecuredEnum: Format[Schema.DeviceSignals.ScreenLockSecuredEnum] = JsonEnumFormat[Schema.DeviceSignals.ScreenLockSecuredEnum]
	given fmtDeviceSignalsSecureBootModeEnum: Format[Schema.DeviceSignals.SecureBootModeEnum] = JsonEnumFormat[Schema.DeviceSignals.SecureBootModeEnum]
	given fmtDeviceSignalsSafeBrowsingProtectionLevelEnum: Format[Schema.DeviceSignals.SafeBrowsingProtectionLevelEnum] = JsonEnumFormat[Schema.DeviceSignals.SafeBrowsingProtectionLevelEnum]
	given fmtDeviceSignalsPasswordProtectionWarningTriggerEnum: Format[Schema.DeviceSignals.PasswordProtectionWarningTriggerEnum] = JsonEnumFormat[Schema.DeviceSignals.PasswordProtectionWarningTriggerEnum]
	given fmtDeviceSignalsRealtimeUrlCheckModeEnum: Format[Schema.DeviceSignals.RealtimeUrlCheckModeEnum] = JsonEnumFormat[Schema.DeviceSignals.RealtimeUrlCheckModeEnum]
	given fmtCrowdStrikeAgent: Format[Schema.CrowdStrikeAgent] = Json.format[Schema.CrowdStrikeAgent]
	given fmtDeviceSignalsTriggerEnum: Format[Schema.DeviceSignals.TriggerEnum] = JsonEnumFormat[Schema.DeviceSignals.TriggerEnum]
}
