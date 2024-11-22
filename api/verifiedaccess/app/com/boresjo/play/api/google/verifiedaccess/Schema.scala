package com.boresjo.play.api.google.verifiedaccess

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class Empty(
	
	)
	
	case class Challenge(
	  /** Generated challenge, the bytes representation of SignedData. */
		challenge: Option[String] = None
	)
	
	case class VerifyChallengeResponseRequest(
	  /** Required. The generated response to the challenge, the bytes representation of SignedData. */
		challengeResponse: Option[String] = None,
	  /** Optional. Service can optionally provide identity information about the device or user associated with the key. For an EMK, this value is the enrolled domain. For an EUK, this value is the user's email address. If present, this value will be checked against contents of the response, and verification will fail if there is no match. */
		expectedIdentity: Option[String] = None
	)
	
	object VerifyChallengeResponseResult {
		enum KeyTrustLevelEnum extends Enum[KeyTrustLevelEnum] { case KEY_TRUST_LEVEL_UNSPECIFIED, CHROME_OS_VERIFIED_MODE, CHROME_OS_DEVELOPER_MODE, CHROME_BROWSER_HW_KEY, CHROME_BROWSER_OS_KEY, CHROME_BROWSER_NO_KEY }
		enum ProfileKeyTrustLevelEnum extends Enum[ProfileKeyTrustLevelEnum] { case KEY_TRUST_LEVEL_UNSPECIFIED, CHROME_OS_VERIFIED_MODE, CHROME_OS_DEVELOPER_MODE, CHROME_BROWSER_HW_KEY, CHROME_BROWSER_OS_KEY, CHROME_BROWSER_NO_KEY }
	}
	case class VerifyChallengeResponseResult(
	  /** Device permanent id is returned in this field (for the machine response only). */
		devicePermanentId: Option[String] = None,
	  /** Virtual device id of the device. The definition of virtual device id is platform-specific. */
		virtualDeviceId: Option[String] = None,
	  /** Unique customer id that this device belongs to, as defined by the Google Admin SDK at https://developers.google.com/admin-sdk/directory/v1/guides/manage-customers */
		customerId: Option[String] = None,
	  /** Certificate Signing Request (in the SPKAC format, base64 encoded) is returned in this field. This field will be set only if device has included CSR in its challenge response. (the option to include CSR is now available for both user and machine responses) */
		signedPublicKeyAndChallenge: Option[String] = None,
	  /** Deprecated. Device signal in json string representation. Prefer using `device_signals` instead. */
		deviceSignal: Option[String] = None,
	  /** Device signals. */
		deviceSignals: Option[Schema.DeviceSignals] = None,
	  /** Device attested key trust level. */
		keyTrustLevel: Option[Schema.VerifyChallengeResponseResult.KeyTrustLevelEnum] = None,
	  /** Unique customer id that this profile belongs to, as defined by the Google Admin SDK at https://developers.google.com/admin-sdk/directory/v1/guides/manage-customers */
		profileCustomerId: Option[String] = None,
	  /** The ID of a profile on the device. */
		virtualProfileId: Option[String] = None,
	  /** Profile attested key trust level. */
		profileKeyTrustLevel: Option[Schema.VerifyChallengeResponseResult.ProfileKeyTrustLevelEnum] = None,
	  /** Attested device ID (ADID). */
		attestedDeviceId: Option[String] = None,
	  /** Device enrollment id for ChromeOS devices. */
		deviceEnrollmentId: Option[String] = None
	)
	
	object DeviceSignals {
		enum OperatingSystemEnum extends Enum[OperatingSystemEnum] { case OPERATING_SYSTEM_UNSPECIFIED, CHROME_OS, CHROMIUM_OS, WINDOWS, MAC_OS_X, LINUX }
		enum DiskEncryptionEnum extends Enum[DiskEncryptionEnum] { case DISK_ENCRYPTION_UNSPECIFIED, DISK_ENCRYPTION_UNKNOWN, DISK_ENCRYPTION_DISABLED, DISK_ENCRYPTION_ENCRYPTED }
		enum OsFirewallEnum extends Enum[OsFirewallEnum] { case OS_FIREWALL_UNSPECIFIED, OS_FIREWALL_UNKNOWN, OS_FIREWALL_DISABLED, OS_FIREWALL_ENABLED }
		enum ScreenLockSecuredEnum extends Enum[ScreenLockSecuredEnum] { case SCREEN_LOCK_SECURED_UNSPECIFIED, SCREEN_LOCK_SECURED_UNKNOWN, SCREEN_LOCK_SECURED_DISABLED, SCREEN_LOCK_SECURED_ENABLED }
		enum SecureBootModeEnum extends Enum[SecureBootModeEnum] { case SECURE_BOOT_MODE_UNSPECIFIED, SECURE_BOOT_MODE_UNKNOWN, SECURE_BOOT_MODE_DISABLED, SECURE_BOOT_MODE_ENABLED }
		enum SafeBrowsingProtectionLevelEnum extends Enum[SafeBrowsingProtectionLevelEnum] { case SAFE_BROWSING_PROTECTION_LEVEL_UNSPECIFIED, INACTIVE, STANDARD, ENHANCED }
		enum PasswordProtectionWarningTriggerEnum extends Enum[PasswordProtectionWarningTriggerEnum] { case PASSWORD_PROTECTION_WARNING_TRIGGER_UNSPECIFIED, POLICY_UNSET, PASSWORD_PROTECTION_OFF, PASSWORD_REUSE, PHISHING_REUSE }
		enum RealtimeUrlCheckModeEnum extends Enum[RealtimeUrlCheckModeEnum] { case REALTIME_URL_CHECK_MODE_UNSPECIFIED, REALTIME_URL_CHECK_MODE_DISABLED, REALTIME_URL_CHECK_MODE_ENABLED_MAIN_FRAME }
		enum TriggerEnum extends Enum[TriggerEnum] { case TRIGGER_UNSPECIFIED, TRIGGER_BROWSER_NAVIGATION, TRIGGER_LOGIN_SCREEN }
	}
	case class DeviceSignals(
	  /** The name of the device's manufacturer. */
		deviceManufacturer: Option[String] = None,
	  /** The name of the device's model. */
		deviceModel: Option[String] = None,
	  /** The type of the Operating System currently running on the device. */
		operatingSystem: Option[Schema.DeviceSignals.OperatingSystemEnum] = None,
	  /** The current version of the Operating System. On Windows and linux, the value will also include the security patch information. */
		osVersion: Option[String] = None,
	  /** The display name of the device, as defined by the user. */
		displayName: Option[String] = None,
	  /** The encryption state of the disk. On ChromeOS, the main disk is always ENCRYPTED. */
		diskEncryption: Option[Schema.DeviceSignals.DiskEncryptionEnum] = None,
	  /** The serial number of the device. On Windows, this represents the BIOS's serial number. Not available on most Linux distributions. */
		serialNumber: Option[String] = None,
	  /** The state of the OS level firewall. On ChromeOS, the value will always be ENABLED on regular devices and UNKNOWN on devices in developer mode. The signal is currently not available on MacOS 15 (Sequoia) and later. */
		osFirewall: Option[Schema.DeviceSignals.OsFirewallEnum] = None,
	  /** List of the addesses of all OS level DNS servers configured in the device's network settings. */
		systemDnsServers: Option[List[String]] = None,
	  /** Hostname of the device. */
		hostname: Option[String] = None,
	  /** MAC addresses of the device. */
		macAddresses: Option[List[String]] = None,
	  /** The state of the Screen Lock password protection. On ChromeOS, this value will always be ENABLED as there is not way to disable requiring a password or pin when unlocking the device. */
		screenLockSecured: Option[Schema.DeviceSignals.ScreenLockSecuredEnum] = None,
	  /** Value of the AllowScreenLock policy on the device. See https://chromeenterprise.google/policies/?policy=AllowScreenLock for more details. Available on ChromeOS only. */
		allowScreenLock: Option[Boolean] = None,
	  /** International Mobile Equipment Identity (IMEI) of the device. Available on ChromeOS only. */
		imei: Option[List[String]] = None,
	  /** Mobile Equipment Identifier (MEID) of the device. Available on ChromeOS only. */
		meid: Option[List[String]] = None,
	  /** Whether the device's startup software has its Secure Boot feature enabled. Available on Windows only. */
		secureBootMode: Option[Schema.DeviceSignals.SecureBootModeEnum] = None,
	  /** Windows domain that the current machine has joined. Available on Windows only. */
		windowsMachineDomain: Option[String] = None,
	  /** Windows domain for the current OS user. Available on Windows only. */
		windowsUserDomain: Option[String] = None,
	  /** Enrollment domain of the customer which is currently managing the device. */
		deviceEnrollmentDomain: Option[String] = None,
	  /** Current version of the Chrome browser which generated this set of signals. Example value: "107.0.5286.0". */
		browserVersion: Option[String] = None,
	  /** Affiliation IDs of the organizations that are affiliated with the organization that is currently managing the device. When the sets of device and profile affiliation IDs overlap, it means that the organizations managing the device and user are affiliated. To learn more about user affiliation, visit https://support.google.com/chrome/a/answer/12801245?ref_topic=9027936. */
		deviceAffiliationIds: Option[List[String]] = None,
	  /** Affiliation IDs of the organizations that are affiliated with the organization that is currently managing the Chrome Profile’s user or ChromeOS user. */
		profileAffiliationIds: Option[List[String]] = None,
	  /** Whether Chrome's built-in DNS client is used. The OS DNS client is otherwise used. This value may be controlled by an enterprise policy: https://chromeenterprise.google/policies/#BuiltInDnsClientEnabled. */
		builtInDnsClientEnabled: Option[Boolean] = None,
	  /** Whether access to the Chrome Remote Desktop application is blocked via a policy. */
		chromeRemoteDesktopAppBlocked: Option[Boolean] = None,
	  /** Safe Browsing Protection Level. That setting may be controlled by an enterprise policy: https://chromeenterprise.google/policies/#SafeBrowsingProtectionLevel. */
		safeBrowsingProtectionLevel: Option[Schema.DeviceSignals.SafeBrowsingProtectionLevelEnum] = None,
	  /** Whether the Site Isolation (a.k.a Site Per Process) setting is enabled. That setting may be controlled by an enterprise policy: https://chromeenterprise.google/policies/#SitePerProcess */
		siteIsolationEnabled: Option[Boolean] = None,
	  /** Whether the Password Protection Warning feature is enabled or not. Password protection alerts users when they reuse their protected password on potentially suspicious sites. This setting is controlled by an enterprise policy: https://chromeenterprise.google/policies/#PasswordProtectionWarningTrigger. Note that the policy unset does not have the same effects as having the policy explicitly set to `PASSWORD_PROTECTION_OFF`. */
		passwordProtectionWarningTrigger: Option[Schema.DeviceSignals.PasswordProtectionWarningTriggerEnum] = None,
	  /** Whether Enterprise-grade (i.e. custom) unsafe URL scanning is enabled or not. This setting may be controlled by an enterprise policy: https://chromeenterprise.google/policies/#EnterpriseRealTimeUrlCheckMode */
		realtimeUrlCheckMode: Option[Schema.DeviceSignals.RealtimeUrlCheckModeEnum] = None,
	  /** Whether Chrome is blocking third-party software injection or not. This setting may be controlled by an enterprise policy: https://chromeenterprise.google/policies/?policy=ThirdPartyBlockingEnabled. Available on Windows only. */
		thirdPartyBlockingEnabled: Option[Boolean] = None,
	  /** Crowdstrike agent properties installed on the device, if any. Available on Windows and MacOS only. */
		crowdStrikeAgent: Option[Schema.CrowdStrikeAgent] = None,
	  /** The trigger which generated this set of signals. */
		trigger: Option[Schema.DeviceSignals.TriggerEnum] = None,
	  /** Enrollment domain of the customer which is currently managing the profile. */
		profileEnrollmentDomain: Option[String] = None
	)
	
	case class CrowdStrikeAgent(
	  /** The Agent ID of the Crowdstrike agent. */
		agentId: Option[String] = None,
	  /** The Customer ID to which the agent belongs to. */
		customerId: Option[String] = None
	)
}
