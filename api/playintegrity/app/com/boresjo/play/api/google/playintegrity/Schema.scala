package com.boresjo.play.api.google.playintegrity

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object EnvironmentDetails {
		enum PlayProtectVerdictEnum extends Enum[PlayProtectVerdictEnum] { case PLAY_PROTECT_VERDICT_UNSPECIFIED, UNEVALUATED, NO_ISSUES, NO_DATA, MEDIUM_RISK, HIGH_RISK, POSSIBLE_RISK }
	}
	case class EnvironmentDetails(
	  /** The evaluation of the App Access Risk verdicts. */
		appAccessRiskVerdict: Option[Schema.AppAccessRiskVerdict] = None,
	  /** The evaluation of Play Protect verdict. */
		playProtectVerdict: Option[Schema.EnvironmentDetails.PlayProtectVerdictEnum] = None
	)
	
	case class TestingDetails(
	  /** Required. Indicates that the information contained in this payload is a testing response that is statically overridden for a tester. */
		isTestingResponse: Option[Boolean] = None
	)
	
	case class WriteDeviceRecallRequest(
	  /** Required. The new values for the device recall bits to be written. */
		newValues: Option[Schema.Values] = None,
	  /** Required. Integrity token obtained from calling Play Integrity API. */
		integrityToken: Option[String] = None
	)
	
	object AccountActivity {
		enum ActivityLevelEnum extends Enum[ActivityLevelEnum] { case ACTIVITY_LEVEL_UNSPECIFIED, UNEVALUATED, UNUSUAL, UNKNOWN, TYPICAL_BASIC, TYPICAL_STRONG }
	}
	case class AccountActivity(
	  /** Required. Indicates the activity level of the account. */
		activityLevel: Option[Schema.AccountActivity.ActivityLevelEnum] = None
	)
	
	object AccountDetails {
		enum AppLicensingVerdictEnum extends Enum[AppLicensingVerdictEnum] { case UNKNOWN, LICENSED, UNLICENSED, UNEVALUATED }
	}
	case class AccountDetails(
	  /** (Restricted Access) Details about the account activity for the user in the scope. */
		accountActivity: Option[Schema.AccountActivity] = None,
	  /** Required. Details about the licensing status of the user for the app in the scope. */
		appLicensingVerdict: Option[Schema.AccountDetails.AppLicensingVerdictEnum] = None
	)
	
	case class TokenPayloadExternal(
	  /** Details of the environment Play Integrity API runs in. */
		environmentDetails: Option[Schema.EnvironmentDetails] = None,
	  /** Required. Details about the application integrity. */
		appIntegrity: Option[Schema.AppIntegrity] = None,
	  /** Required. Details about the integrity request. */
		requestDetails: Option[Schema.RequestDetails] = None,
	  /** Indicates that this payload is generated for testing purposes and contains any additional data that is linked with testing status. */
		testingDetails: Option[Schema.TestingDetails] = None,
	  /** Required. Details about the device integrity. */
		deviceIntegrity: Option[Schema.DeviceIntegrity] = None,
	  /** Required. Details about the Play Store account. */
		accountDetails: Option[Schema.AccountDetails] = None
	)
	
	object AppIntegrity {
		enum AppRecognitionVerdictEnum extends Enum[AppRecognitionVerdictEnum] { case UNKNOWN, PLAY_RECOGNIZED, UNRECOGNIZED_VERSION, UNEVALUATED }
	}
	case class AppIntegrity(
	  /** Required. Details about the app recognition verdict */
		appRecognitionVerdict: Option[Schema.AppIntegrity.AppRecognitionVerdictEnum] = None,
	  /** The SHA256 hash of the requesting app's signing certificates (base64 web-safe encoded). Set iff app_recognition_verdict != UNEVALUATED. */
		certificateSha256Digest: Option[List[String]] = None,
	  /** Version code of the application. Set iff app_recognition_verdict != UNEVALUATED. */
		versionCode: Option[String] = None,
	  /** Package name of the application under attestation. Set iff app_recognition_verdict != UNEVALUATED. */
		packageName: Option[String] = None
	)
	
	case class DecodeIntegrityTokenResponse(
	  /** Plain token payload generated from the decoded integrity token. */
		tokenPayloadExternal: Option[Schema.TokenPayloadExternal] = None
	)
	
	case class DeviceRecall(
	  /** Required. Contains the recall bits values. */
		values: Option[Schema.Values] = None,
	  /** Required. Contains the recall bits write dates. */
		writeDates: Option[Schema.WriteDates] = None
	)
	
	object AppAccessRiskVerdict {
		enum PlayOrSystemAppsEnum extends Enum[PlayOrSystemAppsEnum] { case UNKNOWN, UNEVALUATED, NOT_INSTALLED, INSTALLED, CAPTURING, CONTROLLING }
		enum OtherAppsEnum extends Enum[OtherAppsEnum] { case UNKNOWN, UNEVALUATED, NOT_INSTALLED, INSTALLED, CAPTURING, CONTROLLING }
		enum AppsDetectedEnum extends Enum[AppsDetectedEnum] { case APPS_DETECTED_UNSPECIFIED, KNOWN_INSTALLED, KNOWN_CAPTURING, KNOWN_OVERLAYS, KNOWN_CONTROLLING, UNKNOWN_INSTALLED, UNKNOWN_CAPTURING, UNKNOWN_OVERLAYS, UNKNOWN_CONTROLLING }
	}
	case class AppAccessRiskVerdict(
	  /** Deprecated: this field will be removed, please use apps_detected instead. App access risk verdict related to apps that are not installed by the Google Play Store, and are not preloaded on the system image by the device manufacturer. */
		playOrSystemApps: Option[Schema.AppAccessRiskVerdict.PlayOrSystemAppsEnum] = None,
	  /** Deprecated: this field will be removed, please use apps_detected instead. App access risk verdict related to apps that are not installed by Google Play, and are not preloaded on the system image by the device manufacturer. */
		otherApps: Option[Schema.AppAccessRiskVerdict.OtherAppsEnum] = None,
	  /** List of detected app types signalled for App Access Risk. */
		appsDetected: Option[List[Schema.AppAccessRiskVerdict.AppsDetectedEnum]] = None
	)
	
	case class WriteDeviceRecallResponse(
	
	)
	
	case class WriteDates(
	  /** Optional. Write time in YYYYMM format (in UTC, e.g. 202402) for the third bit. Note that this value won't be set if the third bit is false. */
		yyyymmThird: Option[Int] = None,
	  /** Optional. Write time in YYYYMM format (in UTC, e.g. 202402) for the first bit. Note that this value won't be set if the first bit is false. */
		yyyymmFirst: Option[Int] = None,
	  /** Optional. Write time in YYYYMM format (in UTC, e.g. 202402) for the second bit. Note that this value won't be set if the second bit is false. */
		yyyymmSecond: Option[Int] = None
	)
	
	object DeviceIntegrity {
		enum DeviceRecognitionVerdictEnum extends Enum[DeviceRecognitionVerdictEnum] { case UNKNOWN, MEETS_BASIC_INTEGRITY, MEETS_DEVICE_INTEGRITY, MEETS_STRONG_INTEGRITY, MEETS_VIRTUAL_INTEGRITY }
	}
	case class DeviceIntegrity(
	  /** Details about the device recall bits set by the developer. */
		deviceRecall: Option[Schema.DeviceRecall] = None,
	  /** Details about the device activity of the device the app is running on. */
		recentDeviceActivity: Option[Schema.RecentDeviceActivity] = None,
	  /** Details about the integrity of the device the app is running on. */
		deviceRecognitionVerdict: Option[List[Schema.DeviceIntegrity.DeviceRecognitionVerdictEnum]] = None
	)
	
	case class Values(
	  /** Required. Third recall bit value. */
		bitThird: Option[Boolean] = None,
	  /** Required. First recall bit value. */
		bitFirst: Option[Boolean] = None,
	  /** Required. Second recall bit value. */
		bitSecond: Option[Boolean] = None
	)
	
	object RecentDeviceActivity {
		enum DeviceActivityLevelEnum extends Enum[DeviceActivityLevelEnum] { case DEVICE_ACTIVITY_LEVEL_UNSPECIFIED, UNEVALUATED, LEVEL_1, LEVEL_2, LEVEL_3, LEVEL_4 }
	}
	case class RecentDeviceActivity(
	  /** Required. Indicates the activity level of the device. */
		deviceActivityLevel: Option[Schema.RecentDeviceActivity.DeviceActivityLevelEnum] = None
	)
	
	case class DecodeIntegrityTokenRequest(
	  /** Encoded integrity token. */
		integrityToken: Option[String] = None
	)
	
	case class RequestDetails(
	  /** Nonce that was provided in the request (which is base64 web-safe no-wrap). */
		nonce: Option[String] = None,
	  /** Required. Timestamp, in milliseconds, of the integrity application request. */
		timestampMillis: Option[String] = None,
	  /** Request hash that was provided in the request. */
		requestHash: Option[String] = None,
	  /** Required. Application package name this attestation was requested for. Note: This field makes no guarantees or promises on the caller integrity. For details on application integrity, check application_integrity. */
		requestPackageName: Option[String] = None
	)
}
