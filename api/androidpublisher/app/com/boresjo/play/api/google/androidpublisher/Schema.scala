package com.boresjo.play.api.google.androidpublisher

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object User {
		enum AccessStateEnum extends Enum[AccessStateEnum] { case ACCESS_STATE_UNSPECIFIED, INVITED, INVITATION_EXPIRED, ACCESS_GRANTED, ACCESS_EXPIRED }
		enum DeveloperAccountPermissionsEnum extends Enum[DeveloperAccountPermissionsEnum] { case DEVELOPER_LEVEL_PERMISSION_UNSPECIFIED, CAN_SEE_ALL_APPS, CAN_VIEW_FINANCIAL_DATA_GLOBAL, CAN_MANAGE_PERMISSIONS_GLOBAL, CAN_EDIT_GAMES_GLOBAL, CAN_PUBLISH_GAMES_GLOBAL, CAN_REPLY_TO_REVIEWS_GLOBAL, CAN_MANAGE_PUBLIC_APKS_GLOBAL, CAN_MANAGE_TRACK_APKS_GLOBAL, CAN_MANAGE_TRACK_USERS_GLOBAL, CAN_MANAGE_PUBLIC_LISTING_GLOBAL, CAN_MANAGE_DRAFT_APPS_GLOBAL, CAN_CREATE_MANAGED_PLAY_APPS_GLOBAL, CAN_CHANGE_MANAGED_PLAY_SETTING_GLOBAL, CAN_MANAGE_ORDERS_GLOBAL, CAN_MANAGE_APP_CONTENT_GLOBAL, CAN_VIEW_NON_FINANCIAL_DATA_GLOBAL, CAN_VIEW_APP_QUALITY_GLOBAL, CAN_MANAGE_DEEPLINKS_GLOBAL }
	}
	case class User(
	  /** Required. Resource name for this user, following the pattern "developers/{developer}/users/{email}". */
		name: Option[String] = None,
	  /** Immutable. The user's email address. */
		email: Option[String] = None,
	  /** Output only. The state of the user's access to the Play Console. */
		accessState: Option[Schema.User.AccessStateEnum] = None,
	  /** The time at which the user's access expires, if set. When setting this value, it must always be in the future. */
		expirationTime: Option[String] = None,
	  /** Output only. Whether there are more permissions for the user that are not represented here. This can happen if the caller does not have permission to manage all apps in the account. This is also `true` if this user is the account owner. If this field is `true`, it should be taken as a signal that this user cannot be fully managed via the API. That is, the API caller is not be able to manage all of the permissions this user holds, either because it doesn't know about them or because the user is the account owner. */
		partial: Option[Boolean] = None,
	  /** Permissions for the user which apply across the developer account. */
		developerAccountPermissions: Option[List[Schema.User.DeveloperAccountPermissionsEnum]] = None,
	  /** Output only. Per-app permissions for the user. */
		grants: Option[List[Schema.Grant]] = None
	)
	
	object Grant {
		enum AppLevelPermissionsEnum extends Enum[AppLevelPermissionsEnum] { case APP_LEVEL_PERMISSION_UNSPECIFIED, CAN_ACCESS_APP, CAN_VIEW_FINANCIAL_DATA, CAN_MANAGE_PERMISSIONS, CAN_REPLY_TO_REVIEWS, CAN_MANAGE_PUBLIC_APKS, CAN_MANAGE_TRACK_APKS, CAN_MANAGE_TRACK_USERS, CAN_MANAGE_PUBLIC_LISTING, CAN_MANAGE_DRAFT_APPS, CAN_MANAGE_ORDERS, CAN_MANAGE_APP_CONTENT, CAN_VIEW_NON_FINANCIAL_DATA, CAN_VIEW_APP_QUALITY, CAN_MANAGE_DEEPLINKS }
	}
	case class Grant(
	  /** Required. Resource name for this grant, following the pattern "developers/{developer}/users/{email}/grants/{package_name}". If this grant is for a draft app, the app ID will be used in this resource name instead of the package name. */
		name: Option[String] = None,
	  /** Immutable. The package name of the app. This will be empty for draft apps. */
		packageName: Option[String] = None,
	  /** The permissions granted to the user for this app. */
		appLevelPermissions: Option[List[Schema.Grant.AppLevelPermissionsEnum]] = None
	)
	
	case class ListUsersResponse(
	  /** The resulting users. */
		users: Option[List[Schema.User]] = None,
	  /** A token to pass to subsequent calls in order to retrieve subsequent results. This will not be set if there are no more results to return. */
		nextPageToken: Option[String] = None
	)
	
	case class Apk(
	  /** The version code of the APK, as specified in the manifest file. */
		versionCode: Option[Int] = None,
	  /** Information about the binary payload of this APK. */
		binary: Option[Schema.ApkBinary] = None
	)
	
	case class ApkBinary(
	  /** A sha1 hash of the APK payload, encoded as a hex string and matching the output of the sha1sum command. */
		sha1: Option[String] = None,
	  /** A sha256 hash of the APK payload, encoded as a hex string and matching the output of the sha256sum command. */
		sha256: Option[String] = None
	)
	
	case class ApksListResponse(
	  /** The kind of this response ("androidpublisher#apksListResponse"). */
		kind: Option[String] = None,
	  /** All APKs. */
		apks: Option[List[Schema.Apk]] = None
	)
	
	case class ApksAddExternallyHostedRequest(
	  /** The definition of the externally-hosted APK and where it is located. */
		externallyHostedApk: Option[Schema.ExternallyHostedApk] = None
	)
	
	case class ExternallyHostedApk(
	  /** The package name. */
		packageName: Option[String] = None,
	  /** The application label. */
		applicationLabel: Option[String] = None,
	  /** The version code of this APK. */
		versionCode: Option[Int] = None,
	  /** The version name of this APK. */
		versionName: Option[String] = None,
	  /** The file size in bytes of this APK. */
		fileSize: Option[String] = None,
	  /** The sha1 checksum of this APK, represented as a base64 encoded byte array. */
		fileSha1Base64: Option[String] = None,
	  /** The sha256 checksum of this APK, represented as a base64 encoded byte array. */
		fileSha256Base64: Option[String] = None,
	  /** The icon image from the APK, as a base64 encoded byte array. */
		iconBase64: Option[String] = None,
	  /** The minimum SDK targeted by this APK. */
		minimumSdk: Option[Int] = None,
	  /** A certificate (or array of certificates if a certificate-chain is used) used to sign this APK, represented as a base64 encoded byte array. */
		certificateBase64s: Option[List[String]] = None,
	  /** The URL at which the APK is hosted. This must be an https URL. */
		externallyHostedUrl: Option[String] = None,
	  /** The maximum SDK supported by this APK (optional). */
		maximumSdk: Option[Int] = None,
	  /** The native code environments supported by this APK (optional). */
		nativeCodes: Option[List[String]] = None,
	  /** The features required by this APK (optional). */
		usesFeatures: Option[List[String]] = None,
	  /** The permissions requested by this APK. */
		usesPermissions: Option[List[Schema.UsesPermission]] = None
	)
	
	case class UsesPermission(
	  /** The name of the permission requested. */
		name: Option[String] = None,
	  /** Optionally, the maximum SDK version for which the permission is required. */
		maxSdkVersion: Option[Int] = None
	)
	
	case class ApksAddExternallyHostedResponse(
	  /** The definition of the externally-hosted APK and where it is located. */
		externallyHostedApk: Option[Schema.ExternallyHostedApk] = None
	)
	
	case class CreateDraftAppRecoveryRequest(
	  /** Specifies targeting criteria for the recovery action such as regions, android sdk versions, app versions etc. */
		targeting: Option[Schema.Targeting] = None,
	  /** Action type is remote in-app update. As a consequence of this action, a downloadable recovery module is also created for testing purposes. */
		remoteInAppUpdate: Option[Schema.RemoteInAppUpdate] = None
	)
	
	case class Targeting(
	  /** Targeting is based on the user account region. */
		regions: Option[Schema.Regions] = None,
	  /** Targeting is based on android api levels of devices. */
		androidSdks: Option[Schema.AndroidSdks] = None,
	  /** All users are targeted. */
		allUsers: Option[Schema.AllUsers] = None,
	  /** Target version codes as a list. */
		versionList: Option[Schema.AppVersionList] = None,
	  /** Target version codes as a range. */
		versionRange: Option[Schema.AppVersionRange] = None
	)
	
	case class Regions(
	  /** Regions targeted by the recovery action. Region codes are ISO 3166 Alpha-2 country codes. For example, US stands for United States of America. See https://www.iso.org/iso-3166-country-codes.html for the complete list of country codes. */
		regionCode: Option[List[String]] = None
	)
	
	case class AndroidSdks(
	  /** Android api levels of devices targeted by recovery action. See https://developer.android.com/guide/topics/manifest/uses-sdk-element#ApiLevels for different api levels in android. */
		sdkLevels: Option[List[String]] = None
	)
	
	case class AllUsers(
	  /** Required. Set to true if all set of users are needed. */
		isAllUsersRequested: Option[Boolean] = None
	)
	
	case class AppVersionList(
	  /** List of app version codes. */
		versionCodes: Option[List[String]] = None
	)
	
	case class AppVersionRange(
	  /** Lowest app version in the range, inclusive. */
		versionCodeStart: Option[String] = None,
	  /** Highest app version in the range, inclusive. */
		versionCodeEnd: Option[String] = None
	)
	
	case class RemoteInAppUpdate(
	  /** Required. Set to true if Remote In-App Update action type is needed. */
		isRemoteInAppUpdateRequested: Option[Boolean] = None
	)
	
	object AppRecoveryAction {
		enum StatusEnum extends Enum[StatusEnum] { case RECOVERY_STATUS_UNSPECIFIED, RECOVERY_STATUS_ACTIVE, RECOVERY_STATUS_CANCELED, RECOVERY_STATUS_DRAFT, RECOVERY_STATUS_GENERATION_IN_PROGRESS, RECOVERY_STATUS_GENERATION_FAILED }
	}
	case class AppRecoveryAction(
	  /** ID corresponding to the app recovery action. */
		appRecoveryId: Option[String] = None,
	  /** The status of the recovery action. */
		status: Option[Schema.AppRecoveryAction.StatusEnum] = None,
	  /** Specifies targeting criteria for the recovery action such as regions, android sdk versions, app versions etc. */
		targeting: Option[Schema.Targeting] = None,
	  /** Timestamp of when the app recovery action is created by the developer. It is always set after creation of the recovery action. */
		createTime: Option[String] = None,
	  /** Timestamp of when the app recovery action is deployed to the users. Only set if the recovery action has been deployed. */
		deployTime: Option[String] = None,
	  /** Timestamp of when the app recovery action is canceled by the developer. Only set if the recovery action has been canceled. */
		cancelTime: Option[String] = None,
	  /** Timestamp of when the developer last updated recovery action. In case the action is cancelled, it corresponds to cancellation time. It is always set after creation of the recovery action. */
		lastUpdateTime: Option[String] = None,
	  /** Data about the remote in-app update action such as such as recovered user base, recoverable user base etc. Set only if the recovery action type is Remote In-App Update. */
		remoteInAppUpdateData: Option[Schema.RemoteInAppUpdateData] = None
	)
	
	case class RemoteInAppUpdateData(
	  /** Data related to the recovery action at bundle level. */
		remoteAppUpdateDataPerBundle: Option[List[Schema.RemoteInAppUpdateDataPerBundle]] = None
	)
	
	case class RemoteInAppUpdateDataPerBundle(
	  /** Version Code corresponding to the target bundle. */
		versionCode: Option[String] = None,
	  /** Total number of devices which have been rescued. */
		recoveredDeviceCount: Option[String] = None,
	  /** Total number of devices affected by this recovery action associated with bundle of the app. */
		totalDeviceCount: Option[String] = None
	)
	
	case class DeployAppRecoveryRequest(
	
	)
	
	case class DeployAppRecoveryResponse(
	
	)
	
	case class ListAppRecoveriesResponse(
	  /** List of recovery actions associated with the requested package name. */
		recoveryActions: Option[List[Schema.AppRecoveryAction]] = None
	)
	
	case class AddTargetingRequest(
	  /** Specifies targeting updates such as regions, android sdk versions etc. */
		targetingUpdate: Option[Schema.TargetingUpdate] = None
	)
	
	case class TargetingUpdate(
	  /** Additional regions are targeted by the recovery action. */
		regions: Option[Schema.Regions] = None,
	  /** Additional android sdk levels are targeted by the recovery action. */
		androidSdks: Option[Schema.AndroidSdks] = None,
	  /** All users are targeted. */
		allUsers: Option[Schema.AllUsers] = None
	)
	
	case class AddTargetingResponse(
	
	)
	
	case class CancelAppRecoveryRequest(
	
	)
	
	case class CancelAppRecoveryResponse(
	
	)
	
	case class BundlesListResponse(
	  /** The kind of this response ("androidpublisher#bundlesListResponse"). */
		kind: Option[String] = None,
	  /** All app bundles. */
		bundles: Option[List[Schema.Bundle]] = None
	)
	
	case class Bundle(
	  /** The version code of the Android App Bundle, as specified in the Android App Bundle's base module APK manifest file. */
		versionCode: Option[Int] = None,
	  /** A sha1 hash of the upload payload, encoded as a hex string and matching the output of the sha1sum command. */
		sha1: Option[String] = None,
	  /** A sha256 hash of the upload payload, encoded as a hex string and matching the output of the sha256sum command. */
		sha256: Option[String] = None
	)
	
	case class TrackCountryAvailability(
	  /** Whether this track's availability is synced with the default production track. See https://support.google.com/googleplay/android-developer/answer/7550024 for more information on syncing country availability with production. Note that if this is true, the returned "countries" and "rest_of_world" fields will reflect the values for the default production track. */
		syncWithProduction: Option[Boolean] = None,
	  /** A list of one or more countries where artifacts in this track are available. This list includes all countries that are targeted by the track, even if only specific carriers are targeted in that country. */
		countries: Option[List[Schema.TrackTargetedCountry]] = None,
	  /** Whether artifacts in this track are available to "rest of the world" countries. */
		restOfWorld: Option[Boolean] = None
	)
	
	case class TrackTargetedCountry(
	  /** The country to target, as a two-letter CLDR code. */
		countryCode: Option[String] = None
	)
	
	case class DeobfuscationFilesUploadResponse(
	  /** The uploaded Deobfuscation File configuration. */
		deobfuscationFile: Option[Schema.DeobfuscationFile] = None
	)
	
	object DeobfuscationFile {
		enum SymbolTypeEnum extends Enum[SymbolTypeEnum] { case deobfuscationFileTypeUnspecified, proguard, nativeCode }
	}
	case class DeobfuscationFile(
	  /** The type of the deobfuscation file. */
		symbolType: Option[Schema.DeobfuscationFile.SymbolTypeEnum] = None
	)
	
	case class AppDetails(
	  /** Default language code, in BCP 47 format (eg "en-US"). */
		defaultLanguage: Option[String] = None,
	  /** The user-visible website for this app. */
		contactWebsite: Option[String] = None,
	  /** The user-visible support email for this app. */
		contactEmail: Option[String] = None,
	  /** The user-visible support telephone number for this app. */
		contactPhone: Option[String] = None
	)
	
	case class DeviceTierConfig(
	  /** Output only. The device tier config ID. */
		deviceTierConfigId: Option[String] = None,
	  /** Definition of device groups for the app. */
		deviceGroups: Option[List[Schema.DeviceGroup]] = None,
	  /** Definition of the set of device tiers for the app. */
		deviceTierSet: Option[Schema.DeviceTierSet] = None,
	  /** Definition of user country sets for the app. */
		userCountrySets: Option[List[Schema.UserCountrySet]] = None
	)
	
	case class DeviceGroup(
	  /** The name of the group. */
		name: Option[String] = None,
	  /** Device selectors for this group. A device matching any of the selectors is included in this group. */
		deviceSelectors: Option[List[Schema.DeviceSelector]] = None
	)
	
	case class DeviceSelector(
	  /** Conditions on the device's RAM. */
		deviceRam: Option[Schema.DeviceRam] = None,
	  /** Device models included by this selector. */
		includedDeviceIds: Option[List[Schema.DeviceId]] = None,
	  /** Device models excluded by this selector, even if they match all other conditions. */
		excludedDeviceIds: Option[List[Schema.DeviceId]] = None,
	  /** A device needs to have all these system features to be included by the selector. */
		requiredSystemFeatures: Option[List[Schema.SystemFeature]] = None,
	  /** A device that has any of these system features is excluded by this selector, even if it matches all other conditions. */
		forbiddenSystemFeatures: Option[List[Schema.SystemFeature]] = None,
	  /** Optional. The SoCs included by this selector. Only works for Android S+ devices. */
		systemOnChips: Option[List[Schema.SystemOnChip]] = None
	)
	
	case class DeviceRam(
	  /** Minimum RAM in bytes (bound included). */
		minBytes: Option[String] = None,
	  /** Maximum RAM in bytes (bound excluded). */
		maxBytes: Option[String] = None
	)
	
	case class DeviceId(
	  /** Value of Build.BRAND. */
		buildBrand: Option[String] = None,
	  /** Value of Build.DEVICE. */
		buildDevice: Option[String] = None
	)
	
	case class SystemFeature(
	  /** The name of the feature. */
		name: Option[String] = None
	)
	
	case class SystemOnChip(
	  /** Required. The designer of the SoC, eg. "Google" Value of build property "ro.soc.manufacturer" https://developer.android.com/reference/android/os/Build#SOC_MANUFACTURER Required. */
		manufacturer: Option[String] = None,
	  /** Required. The model of the SoC, eg. "Tensor" Value of build property "ro.soc.model" https://developer.android.com/reference/android/os/Build#SOC_MODEL Required. */
		model: Option[String] = None
	)
	
	case class DeviceTierSet(
	  /** Device tiers belonging to the set. */
		deviceTiers: Option[List[Schema.DeviceTier]] = None
	)
	
	case class DeviceTier(
	  /** Groups of devices included in this tier. These groups must be defined explicitly under device_groups in this configuration. */
		deviceGroupNames: Option[List[String]] = None,
	  /** The priority level of the tier. Tiers are evaluated in descending order of level: the highest level tier has the highest priority. The highest tier matching a given device is selected for that device. You should use a contiguous range of levels for your tiers in a tier set; tier levels in a tier set must be unique. For instance, if your tier set has 4 tiers (including the global fallback), you should define tiers 1, 2 and 3 in this configuration. Note: tier 0 is implicitly defined as a global fallback and selected for devices that don't match any of the tiers explicitly defined here. You mustn't define level 0 explicitly in this configuration. */
		level: Option[Int] = None
	)
	
	case class UserCountrySet(
	  /** Country set name. */
		name: Option[String] = None,
	  /** List of country codes representing countries. A Country code is represented in ISO 3166 alpha-2 format. For Example:- "IT" for Italy, "GE" for Georgia. */
		countryCodes: Option[List[String]] = None
	)
	
	case class ListDeviceTierConfigsResponse(
	  /** Device tier configs created by the developer. */
		deviceTierConfigs: Option[List[Schema.DeviceTierConfig]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class AppEdit(
	  /** Output only. Identifier of the edit. Can be used in subsequent API calls. */
		id: Option[String] = None,
	  /** Output only. The time (as seconds since Epoch) at which the edit will expire and will be no longer valid for use. */
		expiryTimeSeconds: Option[String] = None
	)
	
	case class ExpansionFile(
	  /** If set, this APK's expansion file references another APK's expansion file. The file_size field will not be set. */
		referencesVersion: Option[Int] = None,
	  /** If set, this field indicates that this APK has an expansion file uploaded to it: this APK does not reference another APK's expansion file. The field's value is the size of the uploaded expansion file in bytes. */
		fileSize: Option[String] = None
	)
	
	case class ExpansionFilesUploadResponse(
	  /** The uploaded expansion file configuration. */
		expansionFile: Option[Schema.ExpansionFile] = None
	)
	
	object ExternalTransaction {
		enum TransactionStateEnum extends Enum[TransactionStateEnum] { case TRANSACTION_STATE_UNSPECIFIED, TRANSACTION_REPORTED, TRANSACTION_CANCELED }
	}
	case class ExternalTransaction(
	  /** Output only. The resource name of the external transaction. The package name of the application the inapp products were sold (for example, 'com.some.app'). */
		packageName: Option[String] = None,
	  /** Output only. The id of this transaction. All transaction ids under the same package name must be unique. Set when creating the external transaction. */
		externalTransactionId: Option[String] = None,
	  /** Required. The original transaction amount before taxes. This represents the pre-tax amount originally notified to Google before any refunds were applied. */
		originalPreTaxAmount: Option[Schema.Price] = None,
	  /** Required. The original tax amount. This represents the tax amount originally notified to Google before any refunds were applied. */
		originalTaxAmount: Option[Schema.Price] = None,
	  /** Output only. The current transaction amount before tax. This represents the current pre-tax amount including any refunds that may have been applied to this transaction. */
		currentPreTaxAmount: Option[Schema.Price] = None,
	  /** Output only. The current tax amount. This represents the current tax amount including any refunds that may have been applied to this transaction. */
		currentTaxAmount: Option[Schema.Price] = None,
	  /** Output only. If set, this transaction was a test purchase. Google will not charge for a test transaction. */
		testPurchase: Option[Schema.ExternalTransactionTestPurchase] = None,
	  /** This is a one-time transaction and not part of a subscription. */
		oneTimeTransaction: Option[Schema.OneTimeExternalTransaction] = None,
	  /** This transaction is part of a recurring series of transactions. */
		recurringTransaction: Option[Schema.RecurringExternalTransaction] = None,
	  /** Required. The time when the transaction was completed. */
		transactionTime: Option[String] = None,
	  /** Output only. The time when this transaction was created. This is the time when Google was notified of the transaction. */
		createTime: Option[String] = None,
	  /** Output only. The current state of the transaction. */
		transactionState: Option[Schema.ExternalTransaction.TransactionStateEnum] = None,
	  /** Required. User address for tax computation. */
		userTaxAddress: Option[Schema.ExternalTransactionAddress] = None,
	  /** Optional. The transaction program code, used to help determine service fee for eligible apps participating in partner programs. Developers participating in the Play Media Experience Program (https://play.google.com/console/about/programs/mediaprogram/) must provide the program code when reporting alternative billing transactions. If you are an eligible developer, please contact your BDM for more information on how to set this field. Note: this field can not be used for external offers transactions. */
		transactionProgramCode: Option[Int] = None
	)
	
	case class Price(
	  /** Price in 1/million of the currency base unit, represented as a string. */
		priceMicros: Option[String] = None,
	  /** 3 letter Currency code, as defined by ISO 4217. See java/com/google/common/money/CurrencyCode.java */
		currency: Option[String] = None
	)
	
	case class ExternalTransactionTestPurchase(
	
	)
	
	case class OneTimeExternalTransaction(
	  /** Input only. Provided during the call to Create. Retrieved from the client when the alternative billing flow is launched. */
		externalTransactionToken: Option[String] = None
	)
	
	object RecurringExternalTransaction {
		enum MigratedTransactionProgramEnum extends Enum[MigratedTransactionProgramEnum] { case EXTERNAL_TRANSACTION_PROGRAM_UNSPECIFIED, USER_CHOICE_BILLING, ALTERNATIVE_BILLING_ONLY }
	}
	case class RecurringExternalTransaction(
	  /** The external transaction id of the first transaction of this recurring series of transactions. For example, for a subscription this would be the transaction id of the first payment. Required when creating recurring external transactions. */
		initialExternalTransactionId: Option[String] = None,
	  /** Input only. Provided during the call to Create. Retrieved from the client when the alternative billing flow is launched. Required only for the initial purchase. */
		externalTransactionToken: Option[String] = None,
	  /** Input only. Provided during the call to Create. Must only be used when migrating a subscription from manual monthly reporting to automated reporting. */
		migratedTransactionProgram: Option[Schema.RecurringExternalTransaction.MigratedTransactionProgramEnum] = None,
	  /** Details of an external subscription. */
		externalSubscription: Option[Schema.ExternalSubscription] = None,
	  /** Details of a recurring external transaction product which doesn't belong to any other specific category. */
		otherRecurringProduct: Option[Schema.OtherRecurringProduct] = None
	)
	
	object ExternalSubscription {
		enum SubscriptionTypeEnum extends Enum[SubscriptionTypeEnum] { case SUBSCRIPTION_TYPE_UNSPECIFIED, RECURRING, PREPAID }
	}
	case class ExternalSubscription(
	  /** Required. The type of the external subscription. */
		subscriptionType: Option[Schema.ExternalSubscription.SubscriptionTypeEnum] = None
	)
	
	case class OtherRecurringProduct(
	
	)
	
	case class ExternalTransactionAddress(
	  /** Required. Two letter region code based on ISO-3166-1 Alpha-2 (UN region codes). */
		regionCode: Option[String] = None,
	  /** Optional. Top-level administrative subdivision of the country/region. Only required for transactions in India. Valid values are "ANDAMAN AND NICOBAR ISLANDS", "ANDHRA PRADESH", "ARUNACHAL PRADESH", "ASSAM", "BIHAR", "CHANDIGARH", "CHHATTISGARH", "DADRA AND NAGAR HAVELI", "DADRA AND NAGAR HAVELI AND DAMAN AND DIU", "DAMAN AND DIU", "DELHI", "GOA", "GUJARAT", "HARYANA", "HIMACHAL PRADESH", "JAMMU AND KASHMIR", "JHARKHAND", "KARNATAKA", "KERALA", "LADAKH", "LAKSHADWEEP", "MADHYA PRADESH", "MAHARASHTRA", "MANIPUR", "MEGHALAYA", "MIZORAM", "NAGALAND", "ODISHA", "PUDUCHERRY", "PUNJAB", "RAJASTHAN", "SIKKIM", "TAMIL NADU", "TELANGANA", "TRIPURA", "UTTAR PRADESH", "UTTARAKHAND", and "WEST BENGAL". */
		administrativeArea: Option[String] = None
	)
	
	case class RefundExternalTransactionRequest(
	  /** A partial refund. */
		partialRefund: Option[Schema.PartialRefund] = None,
	  /** A full-amount refund. */
		fullRefund: Option[Schema.FullRefund] = None,
	  /** Required. The time that the transaction was refunded. */
		refundTime: Option[String] = None
	)
	
	case class PartialRefund(
	  /** Required. A unique id distinguishing this partial refund. If the refund is successful, subsequent refunds with the same id will fail. Must be unique across refunds for one individual transaction. */
		refundId: Option[String] = None,
	  /** Required. The pre-tax amount of the partial refund. Should be less than the remaining pre-tax amount of the transaction. */
		refundPreTaxAmount: Option[Schema.Price] = None
	)
	
	case class FullRefund(
	
	)
	
	case class GeneratedApksListResponse(
	  /** All generated APKs, grouped by the APK signing key. */
		generatedApks: Option[List[Schema.GeneratedApksPerSigningKey]] = None
	)
	
	case class GeneratedApksPerSigningKey(
	  /** SHA256 hash of the APK signing public key certificate. */
		certificateSha256Hash: Option[String] = None,
	  /** List of generated split APKs, signed with a key corresponding to certificate_sha256_hash. */
		generatedSplitApks: Option[List[Schema.GeneratedSplitApk]] = None,
	  /** List of asset pack slices which will be served for this app bundle, signed with a key corresponding to certificate_sha256_hash. */
		generatedAssetPackSlices: Option[List[Schema.GeneratedAssetPackSlice]] = None,
	  /** List of generated standalone APKs, signed with a key corresponding to certificate_sha256_hash. */
		generatedStandaloneApks: Option[List[Schema.GeneratedStandaloneApk]] = None,
	  /** Generated universal APK, signed with a key corresponding to certificate_sha256_hash. This field is not set if no universal APK was generated for this signing key. */
		generatedUniversalApk: Option[Schema.GeneratedUniversalApk] = None,
	  /** Generated recovery apks for recovery actions signed with a key corresponding to certificate_sha256_hash. This includes all generated recovery APKs, also those in draft or cancelled state. This field is not set if no recovery actions were created for this signing key. */
		generatedRecoveryModules: Option[List[Schema.GeneratedRecoveryApk]] = None,
	  /** Contains targeting information about the generated apks. */
		targetingInfo: Option[Schema.TargetingInfo] = None
	)
	
	case class GeneratedSplitApk(
	  /** Download ID, which uniquely identifies the APK to download. Should be supplied to `generatedapks.download` method. */
		downloadId: Option[String] = None,
	  /** ID of the generated variant. */
		variantId: Option[Int] = None,
	  /** Name of the module that this APK belongs to. */
		moduleName: Option[String] = None,
	  /** Split ID. Empty for the main split of the base module. */
		splitId: Option[String] = None
	)
	
	case class GeneratedAssetPackSlice(
	  /** Download ID, which uniquely identifies the APK to download. Should be supplied to `generatedapks.download` method. */
		downloadId: Option[String] = None,
	  /** Name of the module that this asset slice belongs to. */
		moduleName: Option[String] = None,
	  /** Asset slice ID. */
		sliceId: Option[String] = None,
	  /** Asset module version. */
		version: Option[String] = None
	)
	
	case class GeneratedStandaloneApk(
	  /** Download ID, which uniquely identifies the APK to download. Should be supplied to `generatedapks.download` method. */
		downloadId: Option[String] = None,
	  /** ID of the generated variant. */
		variantId: Option[Int] = None
	)
	
	case class GeneratedUniversalApk(
	  /** Download ID, which uniquely identifies the APK to download. Should be supplied to `generatedapks.download` method. */
		downloadId: Option[String] = None
	)
	
	object GeneratedRecoveryApk {
		enum RecoveryStatusEnum extends Enum[RecoveryStatusEnum] { case RECOVERY_STATUS_UNSPECIFIED, RECOVERY_STATUS_ACTIVE, RECOVERY_STATUS_CANCELED, RECOVERY_STATUS_DRAFT, RECOVERY_STATUS_GENERATION_IN_PROGRESS, RECOVERY_STATUS_GENERATION_FAILED }
	}
	case class GeneratedRecoveryApk(
	  /** Download ID, which uniquely identifies the APK to download. Should be supplied to `generatedapks.download` method. */
		downloadId: Option[String] = None,
	  /** ID of the recovery action. */
		recoveryId: Option[String] = None,
	  /** The status of the recovery action corresponding to the recovery apk. */
		recoveryStatus: Option[Schema.GeneratedRecoveryApk.RecoveryStatusEnum] = None,
	  /** Name of the module which recovery apk belongs to. */
		moduleName: Option[String] = None
	)
	
	case class TargetingInfo(
	  /** The package name of this app. */
		packageName: Option[String] = None,
	  /** List of the created variants. */
		variant: Option[List[Schema.SplitApkVariant]] = None,
	  /** List of created asset slices. */
		assetSliceSet: Option[List[Schema.AssetSliceSet]] = None
	)
	
	case class SplitApkVariant(
	  /** Variant-level targeting. */
		targeting: Option[Schema.VariantTargeting] = None,
	  /** Set of APKs, one set per module. */
		apkSet: Option[List[Schema.ApkSet]] = None,
	  /** Number of the variant, starting at 0 (unless overridden). A device will receive APKs from the first variant that matches the device configuration, with higher variant numbers having priority over lower variant numbers. */
		variantNumber: Option[Int] = None
	)
	
	case class VariantTargeting(
	  /** The sdk version that the variant targets */
		sdkVersionTargeting: Option[Schema.SdkVersionTargeting] = None,
	  /** The abi that the variant targets */
		abiTargeting: Option[Schema.AbiTargeting] = None,
	  /** The screen densities that this variant supports */
		screenDensityTargeting: Option[Schema.ScreenDensityTargeting] = None,
	  /** Multi-api-level targeting */
		multiAbiTargeting: Option[Schema.MultiAbiTargeting] = None,
	  /** Texture-compression-format-level targeting */
		textureCompressionFormatTargeting: Option[Schema.TextureCompressionFormatTargeting] = None
	)
	
	case class SdkVersionTargeting(
	  /** Value of an sdk version. */
		value: Option[List[Schema.SdkVersion]] = None,
	  /** Targeting of other sibling directories that were in the Bundle. For main splits this is targeting of other main splits. */
		alternatives: Option[List[Schema.SdkVersion]] = None
	)
	
	case class SdkVersion(
	  /** Inclusive minimum value of an sdk version. */
		min: Option[Int] = None
	)
	
	case class AbiTargeting(
	  /** Value of an abi. */
		value: Option[List[Schema.Abi]] = None,
	  /** Targeting of other sibling directories that were in the Bundle. For main splits this is targeting of other main splits. */
		alternatives: Option[List[Schema.Abi]] = None
	)
	
	object Abi {
		enum AliasEnum extends Enum[AliasEnum] { case UNSPECIFIED_CPU_ARCHITECTURE, ARMEABI, ARMEABI_V7A, ARM64_V8A, X86, X86_64, RISCV64 }
	}
	case class Abi(
	  /** Alias for an abi. */
		alias: Option[Schema.Abi.AliasEnum] = None
	)
	
	case class ScreenDensityTargeting(
	  /** Value of a screen density. */
		value: Option[List[Schema.ScreenDensity]] = None,
	  /** Targeting of other sibling directories that were in the Bundle. For main splits this is targeting of other main splits. */
		alternatives: Option[List[Schema.ScreenDensity]] = None
	)
	
	object ScreenDensity {
		enum DensityAliasEnum extends Enum[DensityAliasEnum] { case DENSITY_UNSPECIFIED, NODPI, LDPI, MDPI, TVDPI, HDPI, XHDPI, XXHDPI, XXXHDPI }
	}
	case class ScreenDensity(
	  /** Alias for a screen density. */
		densityAlias: Option[Schema.ScreenDensity.DensityAliasEnum] = None,
	  /** Value for density dpi. */
		densityDpi: Option[Int] = None
	)
	
	case class MultiAbiTargeting(
	  /** Value of a multi abi. */
		value: Option[List[Schema.MultiAbi]] = None,
	  /** Targeting of other sibling directories that were in the Bundle. For main splits this is targeting of other main splits. */
		alternatives: Option[List[Schema.MultiAbi]] = None
	)
	
	case class MultiAbi(
	  /** A list of targeted ABIs, as represented by the Android Platform */
		abi: Option[List[Schema.Abi]] = None
	)
	
	case class TextureCompressionFormatTargeting(
	  /** The list of targeted TCFs. Should not be empty. */
		value: Option[List[Schema.TextureCompressionFormat]] = None,
	  /** List of alternative TCFs (TCFs targeted by the sibling splits). */
		alternatives: Option[List[Schema.TextureCompressionFormat]] = None
	)
	
	object TextureCompressionFormat {
		enum AliasEnum extends Enum[AliasEnum] { case UNSPECIFIED_TEXTURE_COMPRESSION_FORMAT, ETC1_RGB8, PALETTED, THREE_DC, ATC, LATC, DXT1, S3TC, PVRTC, ASTC, ETC2 }
	}
	case class TextureCompressionFormat(
	  /** Alias for texture compression format. */
		alias: Option[Schema.TextureCompressionFormat.AliasEnum] = None
	)
	
	case class ApkSet(
	  /** Metadata about the module represented by this ApkSet */
		moduleMetadata: Option[Schema.ModuleMetadata] = None,
	  /** Description of the generated apks. */
		apkDescription: Option[List[Schema.ApkDescription]] = None
	)
	
	object ModuleMetadata {
		enum ModuleTypeEnum extends Enum[ModuleTypeEnum] { case UNKNOWN_MODULE_TYPE, FEATURE_MODULE }
		enum DeliveryTypeEnum extends Enum[DeliveryTypeEnum] { case UNKNOWN_DELIVERY_TYPE, INSTALL_TIME, ON_DEMAND, FAST_FOLLOW }
	}
	case class ModuleMetadata(
	  /** Module name. */
		name: Option[String] = None,
	  /** Indicates the type of this feature module. */
		moduleType: Option[Schema.ModuleMetadata.ModuleTypeEnum] = None,
	  /** Indicates the delivery type (e.g. on-demand) of the module. */
		deliveryType: Option[Schema.ModuleMetadata.DeliveryTypeEnum] = None,
	  /** Names of the modules that this module directly depends on. Each module implicitly depends on the base module. */
		dependencies: Option[List[String]] = None,
	  /** The targeting that makes a conditional module installed. Relevant only for Split APKs. */
		targeting: Option[Schema.ModuleTargeting] = None
	)
	
	case class ModuleTargeting(
	  /** The sdk version that the variant targets */
		sdkVersionTargeting: Option[Schema.SdkVersionTargeting] = None,
	  /** Targeting for device features. */
		deviceFeatureTargeting: Option[List[Schema.DeviceFeatureTargeting]] = None,
	  /** Countries-level targeting */
		userCountriesTargeting: Option[Schema.UserCountriesTargeting] = None
	)
	
	case class DeviceFeatureTargeting(
	  /** Feature of the device. */
		requiredFeature: Option[Schema.DeviceFeature] = None
	)
	
	case class DeviceFeature(
	  /** Name of the feature. */
		featureName: Option[String] = None,
	  /** The feature version specified by android:glEsVersion or android:version in in the AndroidManifest. */
		featureVersion: Option[Int] = None
	)
	
	case class UserCountriesTargeting(
	  /** List of country codes in the two-letter CLDR territory format. */
		countryCodes: Option[List[String]] = None,
	  /** Indicates if the list above is exclusive. */
		exclude: Option[Boolean] = None
	)
	
	case class ApkDescription(
	  /** Apk-level targeting. */
		targeting: Option[Schema.ApkTargeting] = None,
	  /** Path of the Apk, will be in the following format: .apk where DownloadId is the ID used to download the apk using GeneratedApks.Download API. */
		path: Option[String] = None,
	  /** Set only for Split APKs. */
		splitApkMetadata: Option[Schema.SplitApkMetadata] = None,
	  /** Set only for standalone APKs. */
		standaloneApkMetadata: Option[Schema.StandaloneApkMetadata] = None,
	  /** Set only for Instant split APKs. */
		instantApkMetadata: Option[Schema.SplitApkMetadata] = None,
	  /** Set only for asset slices. */
		assetSliceMetadata: Option[Schema.SplitApkMetadata] = None
	)
	
	case class ApkTargeting(
	  /** The abi that the apk targets */
		abiTargeting: Option[Schema.AbiTargeting] = None,
	  /** The language that the apk targets */
		languageTargeting: Option[Schema.LanguageTargeting] = None,
	  /** The screen density that this apk supports. */
		screenDensityTargeting: Option[Schema.ScreenDensityTargeting] = None,
	  /** The sdk version that the apk targets */
		sdkVersionTargeting: Option[Schema.SdkVersionTargeting] = None,
	  /** Texture-compression-format-level targeting */
		textureCompressionFormatTargeting: Option[Schema.TextureCompressionFormatTargeting] = None,
	  /** Multi-api-level targeting. */
		multiAbiTargeting: Option[Schema.MultiAbiTargeting] = None
	)
	
	case class LanguageTargeting(
	  /** ISO-639: 2 or 3 letter language code. */
		value: Option[List[String]] = None,
	  /** Alternative languages. */
		alternatives: Option[List[String]] = None
	)
	
	case class SplitApkMetadata(
	  /** Id of the split. */
		splitId: Option[String] = None,
	  /** Indicates whether this APK is the main split of the module. */
		isMasterSplit: Option[Boolean] = None
	)
	
	case class StandaloneApkMetadata(
	  /** Names of the modules fused in this standalone APK. */
		fusedModuleName: Option[List[String]] = None
	)
	
	case class AssetSliceSet(
	  /** Module level metadata. */
		assetModuleMetadata: Option[Schema.AssetModuleMetadata] = None,
	  /** Asset slices. */
		apkDescription: Option[List[Schema.ApkDescription]] = None
	)
	
	object AssetModuleMetadata {
		enum DeliveryTypeEnum extends Enum[DeliveryTypeEnum] { case UNKNOWN_DELIVERY_TYPE, INSTALL_TIME, ON_DEMAND, FAST_FOLLOW }
	}
	case class AssetModuleMetadata(
	  /** Module name. */
		name: Option[String] = None,
	  /** Indicates the delivery type for persistent install. */
		deliveryType: Option[Schema.AssetModuleMetadata.DeliveryTypeEnum] = None
	)
	
	case class ImagesListResponse(
	  /** All listed Images. */
		images: Option[List[Schema.Image]] = None
	)
	
	case class Image(
	  /** A unique id representing this image. */
		id: Option[String] = None,
	  /** A URL that will serve a preview of the image. */
		url: Option[String] = None,
	  /** A sha1 hash of the image. */
		sha1: Option[String] = None,
	  /** A sha256 hash of the image. */
		sha256: Option[String] = None
	)
	
	case class ImagesDeleteAllResponse(
	  /** The deleted images. */
		deleted: Option[List[Schema.Image]] = None
	)
	
	case class ImagesUploadResponse(
	  /** The uploaded image. */
		image: Option[Schema.Image] = None
	)
	
	object InAppProduct {
		enum StatusEnum extends Enum[StatusEnum] { case statusUnspecified, active, inactive }
		enum PurchaseTypeEnum extends Enum[PurchaseTypeEnum] { case purchaseTypeUnspecified, managedUser, subscription }
	}
	case class InAppProduct(
	  /** Package name of the parent app. */
		packageName: Option[String] = None,
	  /** Stock-keeping-unit (SKU) of the product, unique within an app. */
		sku: Option[String] = None,
	  /** The status of the product, e.g. whether it's active. */
		status: Option[Schema.InAppProduct.StatusEnum] = None,
	  /** The type of the product, e.g. a recurring subscription. */
		purchaseType: Option[Schema.InAppProduct.PurchaseTypeEnum] = None,
	  /** Default price. Cannot be zero, as in-app products are never free. Always in the developer's Checkout merchant currency. */
		defaultPrice: Option[Schema.Price] = None,
	  /** Prices per buyer region. None of these can be zero, as in-app products are never free. Map key is region code, as defined by ISO 3166-2. */
		prices: Option[Map[String, Schema.Price]] = None,
	  /** List of localized title and description data. Map key is the language of the localized data, as defined by BCP-47, e.g. "en-US". */
		listings: Option[Map[String, Schema.InAppProductListing]] = None,
	  /** Default language of the localized data, as defined by BCP-47. e.g. "en-US". */
		defaultLanguage: Option[String] = None,
	  /** Subscription period, specified in ISO 8601 format. Acceptable values are P1W (one week), P1M (one month), P3M (three months), P6M (six months), and P1Y (one year). */
		subscriptionPeriod: Option[String] = None,
	  /** Trial period, specified in ISO 8601 format. Acceptable values are anything between P7D (seven days) and P999D (999 days). */
		trialPeriod: Option[String] = None,
	  /** Grace period of the subscription, specified in ISO 8601 format. Allows developers to give their subscribers a grace period when the payment for the new recurrence period is declined. Acceptable values are P0D (zero days), P3D (three days), P7D (seven days), P14D (14 days), and P30D (30 days). */
		gracePeriod: Option[String] = None,
	  /** Details about taxes and legal compliance. Only applicable to subscription products. */
		subscriptionTaxesAndComplianceSettings: Option[Schema.SubscriptionTaxAndComplianceSettings] = None,
	  /** Details about taxes and legal compliance. Only applicable to managed products. */
		managedProductTaxesAndComplianceSettings: Option[Schema.ManagedProductTaxAndComplianceSettings] = None
	)
	
	case class InAppProductListing(
	  /** Title for the store listing. */
		title: Option[String] = None,
	  /** Description for the store listing. */
		description: Option[String] = None,
	  /** Localized entitlement benefits for a subscription. */
		benefits: Option[List[String]] = None
	)
	
	object SubscriptionTaxAndComplianceSettings {
		enum EeaWithdrawalRightTypeEnum extends Enum[EeaWithdrawalRightTypeEnum] { case WITHDRAWAL_RIGHT_TYPE_UNSPECIFIED, WITHDRAWAL_RIGHT_DIGITAL_CONTENT, WITHDRAWAL_RIGHT_SERVICE }
	}
	case class SubscriptionTaxAndComplianceSettings(
	  /** Digital content or service classification for products distributed to users in the European Economic Area (EEA). The withdrawal regime under EEA consumer laws depends on this classification. Refer to the [Help Center article](https://support.google.com/googleplay/android-developer/answer/10463498) for more information. */
		eeaWithdrawalRightType: Option[Schema.SubscriptionTaxAndComplianceSettings.EeaWithdrawalRightTypeEnum] = None,
	  /** A mapping from region code to tax rate details. The keys are region codes as defined by Unicode's "CLDR". */
		taxRateInfoByRegionCode: Option[Map[String, Schema.RegionalTaxRateInfo]] = None,
	  /** Whether this subscription is declared as a product representing a tokenized digital asset. */
		isTokenizedDigitalAsset: Option[Boolean] = None
	)
	
	object RegionalTaxRateInfo {
		enum TaxTierEnum extends Enum[TaxTierEnum] { case TAX_TIER_UNSPECIFIED, TAX_TIER_BOOKS_1, TAX_TIER_NEWS_1, TAX_TIER_NEWS_2, TAX_TIER_MUSIC_OR_AUDIO_1, TAX_TIER_LIVE_OR_BROADCAST_1 }
		enum StreamingTaxTypeEnum extends Enum[StreamingTaxTypeEnum] { case STREAMING_TAX_TYPE_UNSPECIFIED, STREAMING_TAX_TYPE_TELCO_VIDEO_RENTAL, STREAMING_TAX_TYPE_TELCO_VIDEO_SALES, STREAMING_TAX_TYPE_TELCO_VIDEO_MULTI_CHANNEL, STREAMING_TAX_TYPE_TELCO_AUDIO_RENTAL, STREAMING_TAX_TYPE_TELCO_AUDIO_SALES, STREAMING_TAX_TYPE_TELCO_AUDIO_MULTI_CHANNEL }
	}
	case class RegionalTaxRateInfo(
	  /** Tax tier to specify reduced tax rate. Developers who sell digital news, magazines, newspapers, books, or audiobooks in various regions may be eligible for reduced tax rates. [Learn more](https://support.google.com/googleplay/android-developer/answer/10463498). */
		taxTier: Option[Schema.RegionalTaxRateInfo.TaxTierEnum] = None,
	  /** You must tell us if your app contains streaming products to correctly charge US state and local sales tax. Field only supported in the United States. */
		eligibleForStreamingServiceTaxRate: Option[Boolean] = None,
	  /** To collect communications or amusement taxes in the United States, choose the appropriate tax category. [Learn more](https://support.google.com/googleplay/android-developer/answer/10463498#streaming_tax). */
		streamingTaxType: Option[Schema.RegionalTaxRateInfo.StreamingTaxTypeEnum] = None
	)
	
	object ManagedProductTaxAndComplianceSettings {
		enum EeaWithdrawalRightTypeEnum extends Enum[EeaWithdrawalRightTypeEnum] { case WITHDRAWAL_RIGHT_TYPE_UNSPECIFIED, WITHDRAWAL_RIGHT_DIGITAL_CONTENT, WITHDRAWAL_RIGHT_SERVICE }
	}
	case class ManagedProductTaxAndComplianceSettings(
	  /** Digital content or service classification for products distributed to users in the European Economic Area (EEA). The withdrawal regime under EEA consumer laws depends on this classification. Refer to the [Help Center article](https://support.google.com/googleplay/android-developer/answer/10463498) for more information. */
		eeaWithdrawalRightType: Option[Schema.ManagedProductTaxAndComplianceSettings.EeaWithdrawalRightTypeEnum] = None,
	  /** A mapping from region code to tax rate details. The keys are region codes as defined by Unicode's "CLDR". */
		taxRateInfoByRegionCode: Option[Map[String, Schema.RegionalTaxRateInfo]] = None,
	  /** Whether this in-app product is declared as a product representing a tokenized digital asset. */
		isTokenizedDigitalAsset: Option[Boolean] = None
	)
	
	case class InappproductsBatchGetResponse(
	  /** The list of requested in-app products, in the same order as the request. */
		inappproduct: Option[List[Schema.InAppProduct]] = None
	)
	
	case class InappproductsListResponse(
	  /** The kind of this response ("androidpublisher#inappproductsListResponse"). */
		kind: Option[String] = None,
	  /** All in-app products. */
		inappproduct: Option[List[Schema.InAppProduct]] = None,
	  /** Pagination token, to handle a number of products that is over one page. */
		tokenPagination: Option[Schema.TokenPagination] = None,
	  /** Deprecated and unset. */
		pageInfo: Option[Schema.PageInfo] = None
	)
	
	case class TokenPagination(
	  /** Tokens to pass to the standard list field 'page_token'. Whenever available, tokens are preferred over manipulating start_index. */
		nextPageToken: Option[String] = None,
		previousPageToken: Option[String] = None
	)
	
	case class PageInfo(
	  /** Total number of results available on the backend ! The total number of results in the result set. */
		totalResults: Option[Int] = None,
	  /** Maximum number of results returned in one page. ! The number of results included in the API response. */
		resultPerPage: Option[Int] = None,
	  /** Index of the first result returned in the current page. */
		startIndex: Option[Int] = None
	)
	
	case class InappproductsBatchUpdateRequest(
	  /** Required. Individual update requests. At least one request is required. Can contain up to 100 requests. All requests must correspond to different in-app products. */
		requests: Option[List[Schema.InappproductsUpdateRequest]] = None
	)
	
	object InappproductsUpdateRequest {
		enum LatencyToleranceEnum extends Enum[LatencyToleranceEnum] { case PRODUCT_UPDATE_LATENCY_TOLERANCE_UNSPECIFIED, PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE, PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT }
	}
	case class InappproductsUpdateRequest(
	  /** Package name of the app. */
		packageName: Option[String] = None,
	  /** Unique identifier for the in-app product. */
		sku: Option[String] = None,
	  /** If true the prices for all regions targeted by the parent app that don't have a price specified for this in-app product will be auto converted to the target currency based on the default price. Defaults to false. */
		autoConvertMissingPrices: Option[Boolean] = None,
	  /** The new in-app product. */
		inappproduct: Option[Schema.InAppProduct] = None,
	  /** If set to true, and the in-app product with the given package_name and sku doesn't exist, the in-app product will be created. */
		allowMissing: Option[Boolean] = None,
	  /** Optional. The latency tolerance for the propagation of this product update. Defaults to latency-sensitive. */
		latencyTolerance: Option[Schema.InappproductsUpdateRequest.LatencyToleranceEnum] = None
	)
	
	case class InappproductsBatchUpdateResponse(
	  /** The updated or inserted in-app products. */
		inappproducts: Option[List[Schema.InAppProduct]] = None
	)
	
	case class InappproductsBatchDeleteRequest(
	  /** Individual delete requests. At least one request is required. Can contain up to 100 requests. All requests must correspond to different in-app products. */
		requests: Option[List[Schema.InappproductsDeleteRequest]] = None
	)
	
	object InappproductsDeleteRequest {
		enum LatencyToleranceEnum extends Enum[LatencyToleranceEnum] { case PRODUCT_UPDATE_LATENCY_TOLERANCE_UNSPECIFIED, PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE, PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT }
	}
	case class InappproductsDeleteRequest(
	  /** Package name of the app. */
		packageName: Option[String] = None,
	  /** Unique identifier for the in-app product. */
		sku: Option[String] = None,
	  /** Optional. The latency tolerance for the propagation of this product update. Defaults to latency-sensitive. */
		latencyTolerance: Option[Schema.InappproductsDeleteRequest.LatencyToleranceEnum] = None
	)
	
	case class InternalAppSharingArtifact(
	  /** The download URL generated for the uploaded artifact. Users that are authorized to download can follow the link to the Play Store app to install it. */
		downloadUrl: Option[String] = None,
	  /** The sha256 fingerprint of the certificate used to sign the generated artifact. */
		certificateFingerprint: Option[String] = None,
	  /** The sha256 hash of the artifact represented as a lowercase hexadecimal number, matching the output of the sha256sum command. */
		sha256: Option[String] = None
	)
	
	case class Listing(
	  /** Language localization code (a BCP-47 language tag; for example, "de-AT" for Austrian German). */
		language: Option[String] = None,
	  /** Localized title of the app. */
		title: Option[String] = None,
	  /** Full description of the app. */
		fullDescription: Option[String] = None,
	  /** Short description of the app. */
		shortDescription: Option[String] = None,
	  /** URL of a promotional YouTube video for the app. */
		video: Option[String] = None
	)
	
	case class ListingsListResponse(
	  /** The kind of this response ("androidpublisher#listingsListResponse"). */
		kind: Option[String] = None,
	  /** All localized listings. */
		listings: Option[List[Schema.Listing]] = None
	)
	
	case class SafetyLabelsUpdateRequest(
	  /** Required. Contents of the CSV file containing Data Safety responses. For the format of this file, see the Help Center documentation at https://support.google.com/googleplay/android-developer/answer/10787469?#zippy=%2Cunderstand-the-csv-format To download an up to date template, follow the steps at https://support.google.com/googleplay/android-developer/answer/10787469?#zippy=%2Cexport-to-a-csv-file */
		safetyLabels: Option[String] = None
	)
	
	case class SafetyLabelsUpdateResponse(
	
	)
	
	case class ConvertRegionPricesRequest(
	  /** The intital price to convert other regions from. Tax exclusive. */
		price: Option[Schema.Money] = None
	)
	
	case class Money(
	  /** The three-letter currency code defined in ISO 4217. */
		currencyCode: Option[String] = None,
	  /** The whole units of the amount. For example if `currencyCode` is `"USD"`, then 1 unit is one US dollar. */
		units: Option[String] = None,
	  /** Number of nano (10^-9) units of the amount. The value must be between -999,999,999 and +999,999,999 inclusive. If `units` is positive, `nanos` must be positive or zero. If `units` is zero, `nanos` can be positive, zero, or negative. If `units` is negative, `nanos` must be negative or zero. For example $-1.75 is represented as `units`=-1 and `nanos`=-750,000,000. */
		nanos: Option[Int] = None
	)
	
	case class ConvertRegionPricesResponse(
	  /** Map from region code to converted region price. */
		convertedRegionPrices: Option[Map[String, Schema.ConvertedRegionPrice]] = None,
	  /** Converted other regions prices in USD and EUR, to use for countries where Play doesn't support a country's local currency. */
		convertedOtherRegionsPrice: Option[Schema.ConvertedOtherRegionsPrice] = None
	)
	
	case class ConvertedRegionPrice(
	  /** The region code of the region. */
		regionCode: Option[String] = None,
	  /** The converted price tax inclusive. */
		price: Option[Schema.Money] = None,
	  /** The tax amount of the converted price. */
		taxAmount: Option[Schema.Money] = None
	)
	
	case class ConvertedOtherRegionsPrice(
	  /** Price in USD to use for the "Other regions" location exclusive of taxes. */
		usdPrice: Option[Schema.Money] = None,
	  /** Price in EUR to use for the "Other regions" location exclusive of taxes. */
		eurPrice: Option[Schema.Money] = None
	)
	
	case class ProductPurchase(
	  /** This kind represents an inappPurchase object in the androidpublisher service. */
		kind: Option[String] = None,
	  /** The time the product was purchased, in milliseconds since the epoch (Jan 1, 1970). */
		purchaseTimeMillis: Option[String] = None,
	  /** The purchase state of the order. Possible values are: 0. Purchased 1. Canceled 2. Pending */
		purchaseState: Option[Int] = None,
	  /** The consumption state of the inapp product. Possible values are: 0. Yet to be consumed 1. Consumed */
		consumptionState: Option[Int] = None,
	  /** A developer-specified string that contains supplemental information about an order. */
		developerPayload: Option[String] = None,
	  /** The order id associated with the purchase of the inapp product. */
		orderId: Option[String] = None,
	  /** The type of purchase of the inapp product. This field is only set if this purchase was not made using the standard in-app billing flow. Possible values are: 0. Test (i.e. purchased from a license testing account) 1. Promo (i.e. purchased using a promo code). Does not include Play Points purchases. 2. Rewarded (i.e. from watching a video ad instead of paying) */
		purchaseType: Option[Int] = None,
	  /** The acknowledgement state of the inapp product. Possible values are: 0. Yet to be acknowledged 1. Acknowledged */
		acknowledgementState: Option[Int] = None,
	  /** The purchase token generated to identify this purchase. May not be present. */
		purchaseToken: Option[String] = None,
	  /** The inapp product SKU. May not be present. */
		productId: Option[String] = None,
	  /** The quantity associated with the purchase of the inapp product. If not present, the quantity is 1. */
		quantity: Option[Int] = None,
	  /** An obfuscated version of the id that is uniquely associated with the user's account in your app. Only present if specified using https://developer.android.com/reference/com/android/billingclient/api/BillingFlowParams.Builder#setobfuscatedaccountid when the purchase was made. */
		obfuscatedExternalAccountId: Option[String] = None,
	  /** An obfuscated version of the id that is uniquely associated with the user's profile in your app. Only present if specified using https://developer.android.com/reference/com/android/billingclient/api/BillingFlowParams.Builder#setobfuscatedprofileid when the purchase was made. */
		obfuscatedExternalProfileId: Option[String] = None,
	  /** ISO 3166-1 alpha-2 billing region code of the user at the time the product was granted. */
		regionCode: Option[String] = None,
	  /** The quantity eligible for refund, i.e. quantity that hasn't been refunded. The value reflects quantity-based partial refunds and full refunds. */
		refundableQuantity: Option[Int] = None
	)
	
	case class ProductPurchasesAcknowledgeRequest(
	  /** Payload to attach to the purchase. */
		developerPayload: Option[String] = None
	)
	
	case class Review(
	  /** Unique identifier for this review. */
		reviewId: Option[String] = None,
	  /** The name of the user who wrote the review. */
		authorName: Option[String] = None,
	  /** A repeated field containing comments for the review. */
		comments: Option[List[Schema.Comment]] = None
	)
	
	case class Comment(
	  /** A comment from a user. */
		userComment: Option[Schema.UserComment] = None,
	  /** A comment from a developer. */
		developerComment: Option[Schema.DeveloperComment] = None
	)
	
	case class UserComment(
	  /** The content of the comment, i.e. review body. In some cases users have been able to write a review with separate title and body; in those cases the title and body are concatenated and separated by a tab character. */
		text: Option[String] = None,
	  /** The last time at which this comment was updated. */
		lastModified: Option[Schema.Timestamp] = None,
	  /** The star rating associated with the review, from 1 to 5. */
		starRating: Option[Int] = None,
	  /** Language code for the reviewer. This is taken from the device settings so is not guaranteed to match the language the review is written in. May be absent. */
		reviewerLanguage: Option[String] = None,
	  /** Codename for the reviewer's device, e.g. klte, flounder. May be absent. */
		device: Option[String] = None,
	  /** Integer Android SDK version of the user's device at the time the review was written, e.g. 23 is Marshmallow. May be absent. */
		androidOsVersion: Option[Int] = None,
	  /** Integer version code of the app as installed at the time the review was written. May be absent. */
		appVersionCode: Option[Int] = None,
	  /** String version name of the app as installed at the time the review was written. May be absent. */
		appVersionName: Option[String] = None,
	  /** Number of users who have given this review a thumbs up. */
		thumbsUpCount: Option[Int] = None,
	  /** Number of users who have given this review a thumbs down. */
		thumbsDownCount: Option[Int] = None,
	  /** Information about the characteristics of the user's device. */
		deviceMetadata: Option[Schema.DeviceMetadata] = None,
	  /** Untranslated text of the review, where the review was translated. If the review was not translated this is left blank. */
		originalText: Option[String] = None
	)
	
	case class Timestamp(
	  /** Represents seconds of UTC time since Unix epoch. */
		seconds: Option[String] = None,
	  /** Non-negative fractions of a second at nanosecond resolution. Must be from 0 to 999,999,999 inclusive. */
		nanos: Option[Int] = None
	)
	
	case class DeviceMetadata(
	  /** Device model name (e.g. Droid) */
		productName: Option[String] = None,
	  /** Device manufacturer (e.g. Motorola) */
		manufacturer: Option[String] = None,
	  /** Device class (e.g. tablet) */
		deviceClass: Option[String] = None,
	  /** Screen width in pixels */
		screenWidthPx: Option[Int] = None,
	  /** Screen height in pixels */
		screenHeightPx: Option[Int] = None,
	  /** Comma separated list of native platforms (e.g. "arm", "arm7") */
		nativePlatform: Option[String] = None,
	  /** Screen density in DPI */
		screenDensityDpi: Option[Int] = None,
	  /** OpenGL version */
		glEsVersion: Option[Int] = None,
	  /** Device CPU model, e.g. "MSM8974" */
		cpuModel: Option[String] = None,
	  /** Device CPU make, e.g. "Qualcomm" */
		cpuMake: Option[String] = None,
	  /** Device RAM in Megabytes, e.g. "2048" */
		ramMb: Option[Int] = None
	)
	
	case class DeveloperComment(
	  /** The content of the comment, i.e. reply body. */
		text: Option[String] = None,
	  /** The last time at which this comment was updated. */
		lastModified: Option[Schema.Timestamp] = None
	)
	
	case class ReviewsListResponse(
	  /** List of reviews. */
		reviews: Option[List[Schema.Review]] = None,
	  /** Pagination token, to handle a number of products that is over one page. */
		tokenPagination: Option[Schema.TokenPagination] = None,
	  /** Information about the current page. */
		pageInfo: Option[Schema.PageInfo] = None
	)
	
	case class ReviewsReplyRequest(
	  /** The text to set as the reply. Replies of more than approximately 350 characters will be rejected. HTML tags will be stripped. */
		replyText: Option[String] = None
	)
	
	case class ReviewsReplyResponse(
	  /** The result of replying/updating a reply to review. */
		result: Option[Schema.ReviewReplyResult] = None
	)
	
	case class ReviewReplyResult(
	  /** The reply text that was applied. */
		replyText: Option[String] = None,
	  /** The time at which the reply took effect. */
		lastEdited: Option[Schema.Timestamp] = None
	)
	
	case class SubscriptionPurchase(
	  /** This kind represents a subscriptionPurchase object in the androidpublisher service. */
		kind: Option[String] = None,
	  /** Time at which the subscription was granted, in milliseconds since the Epoch. */
		startTimeMillis: Option[String] = None,
	  /** Time at which the subscription will expire, in milliseconds since the Epoch. */
		expiryTimeMillis: Option[String] = None,
	  /** Time at which the subscription will be automatically resumed, in milliseconds since the Epoch. Only present if the user has requested to pause the subscription. */
		autoResumeTimeMillis: Option[String] = None,
	  /** Whether the subscription will automatically be renewed when it reaches its current expiry time. */
		autoRenewing: Option[Boolean] = None,
	  /** ISO 4217 currency code for the subscription price. For example, if the price is specified in British pounds sterling, price_currency_code is "GBP". */
		priceCurrencyCode: Option[String] = None,
	  /** Price of the subscription, For tax exclusive countries, the price doesn't include tax. For tax inclusive countries, the price includes tax. Price is expressed in micro-units, where 1,000,000 micro-units represents one unit of the currency. For example, if the subscription price is €1.99, price_amount_micros is 1990000. */
		priceAmountMicros: Option[String] = None,
	  /** Introductory price information of the subscription. This is only present when the subscription was purchased with an introductory price. This field does not indicate the subscription is currently in introductory price period. */
		introductoryPriceInfo: Option[Schema.IntroductoryPriceInfo] = None,
	  /** ISO 3166-1 alpha-2 billing country/region code of the user at the time the subscription was granted. */
		countryCode: Option[String] = None,
	  /** A developer-specified string that contains supplemental information about an order. */
		developerPayload: Option[String] = None,
	  /** The payment state of the subscription. Possible values are: 0. Payment pending 1. Payment received 2. Free trial 3. Pending deferred upgrade/downgrade Not present for canceled, expired subscriptions. */
		paymentState: Option[Int] = None,
	  /** The reason why a subscription was canceled or is not auto-renewing. Possible values are: 0. User canceled the subscription 1. Subscription was canceled by the system, for example because of a billing problem 2. Subscription was replaced with a new subscription 3. Subscription was canceled by the developer */
		cancelReason: Option[Int] = None,
	  /** The time at which the subscription was canceled by the user, in milliseconds since the epoch. Only present if cancelReason is 0. */
		userCancellationTimeMillis: Option[String] = None,
	  /** Information provided by the user when they complete the subscription cancellation flow (cancellation reason survey). */
		cancelSurveyResult: Option[Schema.SubscriptionCancelSurveyResult] = None,
	  /** The order id of the latest recurring order associated with the purchase of the subscription. If the subscription was canceled because payment was declined, this will be the order id from the payment declined order. */
		orderId: Option[String] = None,
	  /** The purchase token of the originating purchase if this subscription is one of the following: 0. Re-signup of a canceled but non-lapsed subscription 1. Upgrade/downgrade from a previous subscription For example, suppose a user originally signs up and you receive purchase token X, then the user cancels and goes through the resignup flow (before their subscription lapses) and you receive purchase token Y, and finally the user upgrades their subscription and you receive purchase token Z. If you call this API with purchase token Z, this field will be set to Y. If you call this API with purchase token Y, this field will be set to X. If you call this API with purchase token X, this field will not be set. */
		linkedPurchaseToken: Option[String] = None,
	  /** The type of purchase of the subscription. This field is only set if this purchase was not made using the standard in-app billing flow. Possible values are: 0. Test (i.e. purchased from a license testing account) 1. Promo (i.e. purchased using a promo code) */
		purchaseType: Option[Int] = None,
	  /** The latest price change information available. This is present only when there is an upcoming price change for the subscription yet to be applied. Once the subscription renews with the new price or the subscription is canceled, no price change information will be returned. */
		priceChange: Option[Schema.SubscriptionPriceChange] = None,
	  /** The profile name of the user when the subscription was purchased. Only present for purchases made with 'Subscribe with Google'. */
		profileName: Option[String] = None,
	  /** The email address of the user when the subscription was purchased. Only present for purchases made with 'Subscribe with Google'. */
		emailAddress: Option[String] = None,
	  /** The given name of the user when the subscription was purchased. Only present for purchases made with 'Subscribe with Google'. */
		givenName: Option[String] = None,
	  /** The family name of the user when the subscription was purchased. Only present for purchases made with 'Subscribe with Google'. */
		familyName: Option[String] = None,
	  /** The Google profile id of the user when the subscription was purchased. Only present for purchases made with 'Subscribe with Google'. */
		profileId: Option[String] = None,
	  /** The acknowledgement state of the subscription product. Possible values are: 0. Yet to be acknowledged 1. Acknowledged */
		acknowledgementState: Option[Int] = None,
	  /** User account identifier in the third-party service. Only present if account linking happened as part of the subscription purchase flow. */
		externalAccountId: Option[String] = None,
	  /** The type of promotion applied on this purchase. This field is only set if a promotion is applied when the subscription was purchased. Possible values are: 0. One time code 1. Vanity code */
		promotionType: Option[Int] = None,
	  /** The promotion code applied on this purchase. This field is only set if a vanity code promotion is applied when the subscription was purchased. */
		promotionCode: Option[String] = None,
	  /** An obfuscated version of the id that is uniquely associated with the user's account in your app. Present for the following purchases: &#42; If account linking happened as part of the subscription purchase flow. &#42; It was specified using https://developer.android.com/reference/com/android/billingclient/api/BillingFlowParams.Builder#setobfuscatedaccountid when the purchase was made. */
		obfuscatedExternalAccountId: Option[String] = None,
	  /** An obfuscated version of the id that is uniquely associated with the user's profile in your app. Only present if specified using https://developer.android.com/reference/com/android/billingclient/api/BillingFlowParams.Builder#setobfuscatedprofileid when the purchase was made. */
		obfuscatedExternalProfileId: Option[String] = None
	)
	
	case class IntroductoryPriceInfo(
	  /** ISO 4217 currency code for the introductory subscription price. For example, if the price is specified in British pounds sterling, price_currency_code is "GBP". */
		introductoryPriceCurrencyCode: Option[String] = None,
	  /** Introductory price of the subscription, not including tax. The currency is the same as price_currency_code. Price is expressed in micro-units, where 1,000,000 micro-units represents one unit of the currency. For example, if the subscription price is €1.99, price_amount_micros is 1990000. */
		introductoryPriceAmountMicros: Option[String] = None,
	  /** Introductory price period, specified in ISO 8601 format. Common values are (but not limited to) "P1W" (one week), "P1M" (one month), "P3M" (three months), "P6M" (six months), and "P1Y" (one year). */
		introductoryPricePeriod: Option[String] = None,
	  /** The number of billing period to offer introductory pricing. */
		introductoryPriceCycles: Option[Int] = None
	)
	
	case class SubscriptionCancelSurveyResult(
	  /** The cancellation reason the user chose in the survey. Possible values are: 0. Other 1. I don't use this service enough 2. Technical issues 3. Cost-related reasons 4. I found a better app */
		cancelSurveyReason: Option[Int] = None,
	  /** The customized input cancel reason from the user. Only present when cancelReason is 0. */
		userInputCancelReason: Option[String] = None
	)
	
	case class SubscriptionPriceChange(
	  /** The new price the subscription will renew with if the price change is accepted by the user. */
		newPrice: Option[Schema.Price] = None,
	  /** The current state of the price change. Possible values are: 0. Outstanding: State for a pending price change waiting for the user to agree. In this state, you can optionally seek confirmation from the user using the In-App API. 1. Accepted: State for an accepted price change that the subscription will renew with unless it's canceled. The price change takes effect on a future date when the subscription renews. Note that the change might not occur when the subscription is renewed next. */
		state: Option[Int] = None
	)
	
	case class SubscriptionPurchasesDeferRequest(
	  /** The information about the new desired expiry time for the subscription. */
		deferralInfo: Option[Schema.SubscriptionDeferralInfo] = None
	)
	
	case class SubscriptionDeferralInfo(
	  /** The expected expiry time for the subscription. If the current expiry time for the subscription is not the value specified here, the deferral will not occur. */
		expectedExpiryTimeMillis: Option[String] = None,
	  /** The desired next expiry time to assign to the subscription, in milliseconds since the Epoch. The given time must be later/greater than the current expiry time for the subscription. */
		desiredExpiryTimeMillis: Option[String] = None
	)
	
	case class SubscriptionPurchasesDeferResponse(
	  /** The new expiry time for the subscription in milliseconds since the Epoch. */
		newExpiryTimeMillis: Option[String] = None
	)
	
	case class SubscriptionPurchasesAcknowledgeRequest(
	  /** Payload to attach to the purchase. */
		developerPayload: Option[String] = None
	)
	
	object SubscriptionPurchaseV2 {
		enum SubscriptionStateEnum extends Enum[SubscriptionStateEnum] { case SUBSCRIPTION_STATE_UNSPECIFIED, SUBSCRIPTION_STATE_PENDING, SUBSCRIPTION_STATE_ACTIVE, SUBSCRIPTION_STATE_PAUSED, SUBSCRIPTION_STATE_IN_GRACE_PERIOD, SUBSCRIPTION_STATE_ON_HOLD, SUBSCRIPTION_STATE_CANCELED, SUBSCRIPTION_STATE_EXPIRED, SUBSCRIPTION_STATE_PENDING_PURCHASE_CANCELED }
		enum AcknowledgementStateEnum extends Enum[AcknowledgementStateEnum] { case ACKNOWLEDGEMENT_STATE_UNSPECIFIED, ACKNOWLEDGEMENT_STATE_PENDING, ACKNOWLEDGEMENT_STATE_ACKNOWLEDGED }
	}
	case class SubscriptionPurchaseV2(
	  /** This kind represents a SubscriptionPurchaseV2 object in the androidpublisher service. */
		kind: Option[String] = None,
	  /** Time at which the subscription was granted. Not set for pending subscriptions (subscription was created but awaiting payment during signup). */
		startTime: Option[String] = None,
	  /** ISO 3166-1 alpha-2 billing country/region code of the user at the time the subscription was granted. */
		regionCode: Option[String] = None,
	  /** The current state of the subscription. */
		subscriptionState: Option[Schema.SubscriptionPurchaseV2.SubscriptionStateEnum] = None,
	  /** The order id of the latest order associated with the purchase of the subscription. For autoRenewing subscription, this is the order id of signup order if it is not renewed yet, or the last recurring order id (success, pending, or declined order). For prepaid subscription, this is the order id associated with the queried purchase token. */
		latestOrderId: Option[String] = None,
	  /** The purchase token of the old subscription if this subscription is one of the following: &#42; Re-signup of a canceled but non-lapsed subscription &#42; Upgrade/downgrade from a previous subscription. &#42; Convert from prepaid to auto renewing subscription. &#42; Convert from an auto renewing subscription to prepaid. &#42; Topup a prepaid subscription. */
		linkedPurchaseToken: Option[String] = None,
	  /** Additional context around paused subscriptions. Only present if the subscription currently has subscription_state SUBSCRIPTION_STATE_PAUSED. */
		pausedStateContext: Option[Schema.PausedStateContext] = None,
	  /** Additional context around canceled subscriptions. Only present if the subscription currently has subscription_state SUBSCRIPTION_STATE_CANCELED or SUBSCRIPTION_STATE_EXPIRED. */
		canceledStateContext: Option[Schema.CanceledStateContext] = None,
	  /** Only present if this subscription purchase is a test purchase. */
		testPurchase: Option[Schema.TestPurchase] = None,
	  /** The acknowledgement state of the subscription. */
		acknowledgementState: Option[Schema.SubscriptionPurchaseV2.AcknowledgementStateEnum] = None,
	  /** User account identifier in the third-party service. */
		externalAccountIdentifiers: Option[Schema.ExternalAccountIdentifiers] = None,
	  /** User profile associated with purchases made with 'Subscribe with Google'. */
		subscribeWithGoogleInfo: Option[Schema.SubscribeWithGoogleInfo] = None,
	  /** Item-level info for a subscription purchase. The items in the same purchase should be either all with AutoRenewingPlan or all with PrepaidPlan. */
		lineItems: Option[List[Schema.SubscriptionPurchaseLineItem]] = None
	)
	
	case class PausedStateContext(
	  /** Time at which the subscription will be automatically resumed. */
		autoResumeTime: Option[String] = None
	)
	
	case class CanceledStateContext(
	  /** Subscription was canceled by user. */
		userInitiatedCancellation: Option[Schema.UserInitiatedCancellation] = None,
	  /** Subscription was canceled by the system, for example because of a billing problem. */
		systemInitiatedCancellation: Option[Schema.SystemInitiatedCancellation] = None,
	  /** Subscription was canceled by the developer. */
		developerInitiatedCancellation: Option[Schema.DeveloperInitiatedCancellation] = None,
	  /** Subscription was replaced by a new subscription. */
		replacementCancellation: Option[Schema.ReplacementCancellation] = None
	)
	
	case class UserInitiatedCancellation(
	  /** Information provided by the user when they complete the subscription cancellation flow (cancellation reason survey). */
		cancelSurveyResult: Option[Schema.CancelSurveyResult] = None,
	  /** The time at which the subscription was canceled by the user. The user might still have access to the subscription after this time. Use line_items.expiry_time to determine if a user still has access. */
		cancelTime: Option[String] = None
	)
	
	object CancelSurveyResult {
		enum ReasonEnum extends Enum[ReasonEnum] { case CANCEL_SURVEY_REASON_UNSPECIFIED, CANCEL_SURVEY_REASON_NOT_ENOUGH_USAGE, CANCEL_SURVEY_REASON_TECHNICAL_ISSUES, CANCEL_SURVEY_REASON_COST_RELATED, CANCEL_SURVEY_REASON_FOUND_BETTER_APP, CANCEL_SURVEY_REASON_OTHERS }
	}
	case class CancelSurveyResult(
	  /** The reason the user selected in the cancel survey. */
		reason: Option[Schema.CancelSurveyResult.ReasonEnum] = None,
	  /** Only set for CANCEL_SURVEY_REASON_OTHERS. This is the user's freeform response to the survey. */
		reasonUserInput: Option[String] = None
	)
	
	case class SystemInitiatedCancellation(
	
	)
	
	case class DeveloperInitiatedCancellation(
	
	)
	
	case class ReplacementCancellation(
	
	)
	
	case class TestPurchase(
	
	)
	
	case class ExternalAccountIdentifiers(
	  /** User account identifier in the third-party service. Only present if account linking happened as part of the subscription purchase flow. */
		externalAccountId: Option[String] = None,
	  /** An obfuscated version of the id that is uniquely associated with the user's account in your app. Present for the following purchases: &#42; If account linking happened as part of the subscription purchase flow. &#42; It was specified using https://developer.android.com/reference/com/android/billingclient/api/BillingFlowParams.Builder#setobfuscatedaccountid when the purchase was made. */
		obfuscatedExternalAccountId: Option[String] = None,
	  /** An obfuscated version of the id that is uniquely associated with the user's profile in your app. Only present if specified using https://developer.android.com/reference/com/android/billingclient/api/BillingFlowParams.Builder#setobfuscatedprofileid when the purchase was made. */
		obfuscatedExternalProfileId: Option[String] = None
	)
	
	case class SubscribeWithGoogleInfo(
	  /** The Google profile id of the user when the subscription was purchased. */
		profileId: Option[String] = None,
	  /** The profile name of the user when the subscription was purchased. */
		profileName: Option[String] = None,
	  /** The email address of the user when the subscription was purchased. */
		emailAddress: Option[String] = None,
	  /** The given name of the user when the subscription was purchased. */
		givenName: Option[String] = None,
	  /** The family name of the user when the subscription was purchased. */
		familyName: Option[String] = None
	)
	
	case class SubscriptionPurchaseLineItem(
	  /** The purchased product ID (for example, 'monthly001'). */
		productId: Option[String] = None,
	  /** Time at which the subscription expired or will expire unless the access is extended (ex. renews). */
		expiryTime: Option[String] = None,
	  /** The item is auto renewing. */
		autoRenewingPlan: Option[Schema.AutoRenewingPlan] = None,
	  /** The item is prepaid. */
		prepaidPlan: Option[Schema.PrepaidPlan] = None,
	  /** The offer details for this item. */
		offerDetails: Option[Schema.OfferDetails] = None,
	  /** Information for deferred item replacement. */
		deferredItemReplacement: Option[Schema.DeferredItemReplacement] = None
	)
	
	case class AutoRenewingPlan(
	  /** If the subscription is currently set to auto-renew, e.g. the user has not canceled the subscription */
		autoRenewEnabled: Option[Boolean] = None,
	  /** The information of the last price change for the item since subscription signup. */
		priceChangeDetails: Option[Schema.SubscriptionItemPriceChangeDetails] = None,
	  /** The installment plan commitment and state related info for the auto renewing plan. */
		installmentDetails: Option[Schema.InstallmentPlan] = None
	)
	
	object SubscriptionItemPriceChangeDetails {
		enum PriceChangeModeEnum extends Enum[PriceChangeModeEnum] { case PRICE_CHANGE_MODE_UNSPECIFIED, PRICE_DECREASE, PRICE_INCREASE, OPT_OUT_PRICE_INCREASE }
		enum PriceChangeStateEnum extends Enum[PriceChangeStateEnum] { case PRICE_CHANGE_STATE_UNSPECIFIED, OUTSTANDING, CONFIRMED, APPLIED }
	}
	case class SubscriptionItemPriceChangeDetails(
	  /** New recurring price for the subscription item. */
		newPrice: Option[Schema.Money] = None,
	  /** Price change mode specifies how the subscription item price is changing. */
		priceChangeMode: Option[Schema.SubscriptionItemPriceChangeDetails.PriceChangeModeEnum] = None,
	  /** State the price change is currently in. */
		priceChangeState: Option[Schema.SubscriptionItemPriceChangeDetails.PriceChangeStateEnum] = None,
	  /** The renewal time at which the price change will become effective for the user. This is subject to change(to a future time) due to cases where the renewal time shifts like pause. This field is only populated if the price change has not taken effect. */
		expectedNewPriceChargeTime: Option[String] = None
	)
	
	case class InstallmentPlan(
	  /** Total number of payments the user is initially committed for. */
		initialCommittedPaymentsCount: Option[Int] = None,
	  /** Total number of payments the user will be committed for after each commitment period. Empty means the installment plan will fall back to a normal auto-renew subscription after initial commitment. */
		subsequentCommittedPaymentsCount: Option[Int] = None,
	  /** Total number of committed payments remaining to be paid for in this renewal cycle. */
		remainingCommittedPaymentsCount: Option[Int] = None,
	  /** If present, this installment plan is pending to be canceled. The cancellation will happen only after the user finished all committed payments. */
		pendingCancellation: Option[Schema.PendingCancellation] = None
	)
	
	case class PendingCancellation(
	
	)
	
	case class PrepaidPlan(
	  /** If present, this is the time after which top up purchases are allowed for the prepaid plan. Will not be present for expired prepaid plans. */
		allowExtendAfterTime: Option[String] = None
	)
	
	case class OfferDetails(
	  /** The base plan ID. Present for all base plan and offers. */
		basePlanId: Option[String] = None,
	  /** The offer ID. Only present for discounted offers. */
		offerId: Option[String] = None,
	  /** The latest offer tags associated with the offer. It includes tags inherited from the base plan. */
		offerTags: Option[List[String]] = None
	)
	
	case class DeferredItemReplacement(
	  /** The product_id going to replace the existing product_id. */
		productId: Option[String] = None
	)
	
	case class RevokeSubscriptionPurchaseRequest(
	  /** Required. Additional details around the subscription revocation. */
		revocationContext: Option[Schema.RevocationContext] = None
	)
	
	case class RevocationContext(
	  /** Optional. Used when users should be refunded the full amount of the latest order of the subscription. */
		fullRefund: Option[Schema.RevocationContextFullRefund] = None,
	  /** Optional. Used when users should be refunded a prorated amount they paid for their subscription based on the amount of time remaining in a subscription. */
		proratedRefund: Option[Schema.RevocationContextProratedRefund] = None
	)
	
	case class RevocationContextFullRefund(
	
	)
	
	case class RevocationContextProratedRefund(
	
	)
	
	case class RevokeSubscriptionPurchaseResponse(
	
	)
	
	case class Subscription(
	  /** Immutable. Package name of the parent app. */
		packageName: Option[String] = None,
	  /** Immutable. Unique product ID of the product. Unique within the parent app. Product IDs must be composed of lower-case letters (a-z), numbers (0-9), underscores (_) and dots (.). It must start with a lower-case letter or number, and be between 1 and 40 (inclusive) characters in length. */
		productId: Option[String] = None,
	  /** The set of base plans for this subscription. Represents the prices and duration of the subscription if no other offers apply. */
		basePlans: Option[List[Schema.BasePlan]] = None,
	  /** Required. List of localized listings for this subscription. Must contain at least an entry for the default language of the parent app. */
		listings: Option[List[Schema.SubscriptionListing]] = None,
	  /** Output only. Deprecated: subscription archiving is not supported. */
		archived: Option[Boolean] = None,
	  /** Details about taxes and legal compliance. */
		taxAndComplianceSettings: Option[Schema.SubscriptionTaxAndComplianceSettings] = None,
	  /** Optional. Countries where the purchase of this subscription is restricted to payment methods registered in the same country. If empty, no payment location restrictions are imposed. */
		restrictedPaymentCountries: Option[Schema.RestrictedPaymentCountries] = None
	)
	
	object BasePlan {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, DRAFT, ACTIVE, INACTIVE }
	}
	case class BasePlan(
	  /** Required. Immutable. The unique identifier of this base plan. Must be unique within the subscription, and conform with RFC-1034. That is, this ID can only contain lower-case letters (a-z), numbers (0-9), and hyphens (-), and be at most 63 characters. */
		basePlanId: Option[String] = None,
	  /** Output only. The state of the base plan, i.e. whether it's active. Draft and inactive base plans can be activated or deleted. Active base plans can be made inactive. Inactive base plans can be canceled. This field cannot be changed by updating the resource. Use the dedicated endpoints instead. */
		state: Option[Schema.BasePlan.StateEnum] = None,
	  /** Set when the base plan automatically renews at a regular interval. */
		autoRenewingBasePlanType: Option[Schema.AutoRenewingBasePlanType] = None,
	  /** Set when the base plan does not automatically renew at the end of the billing period. */
		prepaidBasePlanType: Option[Schema.PrepaidBasePlanType] = None,
	  /** Set for installments base plans where a user is committed to a specified number of payments. */
		installmentsBasePlanType: Option[Schema.InstallmentsBasePlanType] = None,
	  /** Region-specific information for this base plan. */
		regionalConfigs: Option[List[Schema.RegionalBasePlanConfig]] = None,
	  /** List of up to 20 custom tags specified for this base plan, and returned to the app through the billing library. Subscription offers for this base plan will also receive these offer tags in the billing library. */
		offerTags: Option[List[Schema.OfferTag]] = None,
	  /** Pricing information for any new locations Play may launch in the future. If omitted, the BasePlan will not be automatically available any new locations Play may launch in the future. */
		otherRegionsConfig: Option[Schema.OtherRegionsBasePlanConfig] = None
	)
	
	object AutoRenewingBasePlanType {
		enum ResubscribeStateEnum extends Enum[ResubscribeStateEnum] { case RESUBSCRIBE_STATE_UNSPECIFIED, RESUBSCRIBE_STATE_ACTIVE, RESUBSCRIBE_STATE_INACTIVE }
		enum ProrationModeEnum extends Enum[ProrationModeEnum] { case SUBSCRIPTION_PRORATION_MODE_UNSPECIFIED, SUBSCRIPTION_PRORATION_MODE_CHARGE_ON_NEXT_BILLING_DATE, SUBSCRIPTION_PRORATION_MODE_CHARGE_FULL_PRICE_IMMEDIATELY }
	}
	case class AutoRenewingBasePlanType(
	  /** Required. Immutable. Subscription period, specified in ISO 8601 format. For a list of acceptable billing periods, refer to the help center. The duration is immutable after the base plan is created. */
		billingPeriodDuration: Option[String] = None,
	  /** Grace period of the subscription, specified in ISO 8601 format. Acceptable values are P0D (zero days), P3D (3 days), P7D (7 days), P14D (14 days), and P30D (30 days). If not specified, a default value will be used based on the recurring period duration. */
		gracePeriodDuration: Option[String] = None,
	  /** Optional. Account hold period of the subscription, specified in ISO 8601 format. Acceptable values must be in DAYS and in the range P0D (zero days) to P30D (30 days). If not specified, the default value is P30D (30 days). */
		accountHoldDuration: Option[String] = None,
	  /** Whether users should be able to resubscribe to this base plan in Google Play surfaces. Defaults to RESUBSCRIBE_STATE_ACTIVE if not specified. */
		resubscribeState: Option[Schema.AutoRenewingBasePlanType.ResubscribeStateEnum] = None,
	  /** The proration mode for the base plan determines what happens when a user switches to this plan from another base plan. If unspecified, defaults to CHARGE_ON_NEXT_BILLING_DATE. */
		prorationMode: Option[Schema.AutoRenewingBasePlanType.ProrationModeEnum] = None,
	  /** Whether the renewing base plan is backward compatible. The backward compatible base plan is returned by the Google Play Billing Library deprecated method querySkuDetailsAsync(). Only one renewing base plan can be marked as legacy compatible for a given subscription. */
		legacyCompatible: Option[Boolean] = None,
	  /** Subscription offer id which is legacy compatible. The backward compatible subscription offer is returned by the Google Play Billing Library deprecated method querySkuDetailsAsync(). Only one subscription offer can be marked as legacy compatible for a given renewing base plan. To have no Subscription offer as legacy compatible set this field as empty string. */
		legacyCompatibleSubscriptionOfferId: Option[String] = None
	)
	
	object PrepaidBasePlanType {
		enum TimeExtensionEnum extends Enum[TimeExtensionEnum] { case TIME_EXTENSION_UNSPECIFIED, TIME_EXTENSION_ACTIVE, TIME_EXTENSION_INACTIVE }
	}
	case class PrepaidBasePlanType(
	  /** Required. Immutable. Subscription period, specified in ISO 8601 format. For a list of acceptable billing periods, refer to the help center. The duration is immutable after the base plan is created. */
		billingPeriodDuration: Option[String] = None,
	  /** Whether users should be able to extend this prepaid base plan in Google Play surfaces. Defaults to TIME_EXTENSION_ACTIVE if not specified. */
		timeExtension: Option[Schema.PrepaidBasePlanType.TimeExtensionEnum] = None
	)
	
	object InstallmentsBasePlanType {
		enum RenewalTypeEnum extends Enum[RenewalTypeEnum] { case RENEWAL_TYPE_UNSPECIFIED, RENEWAL_TYPE_RENEWS_WITHOUT_COMMITMENT, RENEWAL_TYPE_RENEWS_WITH_COMMITMENT }
		enum ResubscribeStateEnum extends Enum[ResubscribeStateEnum] { case RESUBSCRIBE_STATE_UNSPECIFIED, RESUBSCRIBE_STATE_ACTIVE, RESUBSCRIBE_STATE_INACTIVE }
		enum ProrationModeEnum extends Enum[ProrationModeEnum] { case SUBSCRIPTION_PRORATION_MODE_UNSPECIFIED, SUBSCRIPTION_PRORATION_MODE_CHARGE_ON_NEXT_BILLING_DATE, SUBSCRIPTION_PRORATION_MODE_CHARGE_FULL_PRICE_IMMEDIATELY }
	}
	case class InstallmentsBasePlanType(
	  /** Required. Immutable. Subscription period, specified in ISO 8601 format. For a list of acceptable billing periods, refer to the help center. The duration is immutable after the base plan is created. */
		billingPeriodDuration: Option[String] = None,
	  /** Required. Immutable. The number of payments the user is committed to. It is immutable after the base plan is created. */
		committedPaymentsCount: Option[Int] = None,
	  /** Required. Immutable. Installments base plan renewal type. Determines the behavior at the end of the initial commitment. The renewal type is immutable after the base plan is created. */
		renewalType: Option[Schema.InstallmentsBasePlanType.RenewalTypeEnum] = None,
	  /** Grace period of the subscription, specified in ISO 8601 format. Acceptable values are P0D (zero days), P3D (3 days), P7D (7 days), P14D (14 days), and P30D (30 days). If not specified, a default value will be used based on the recurring period duration. */
		gracePeriodDuration: Option[String] = None,
	  /** Optional. Account hold period of the subscription, specified exclusively in days and in ISO 8601 format. Acceptable values are P0D (zero days) to P30D (30days). If not specified, the default value is P30D (30 days). */
		accountHoldDuration: Option[String] = None,
	  /** Whether users should be able to resubscribe to this base plan in Google Play surfaces. Defaults to RESUBSCRIBE_STATE_ACTIVE if not specified. */
		resubscribeState: Option[Schema.InstallmentsBasePlanType.ResubscribeStateEnum] = None,
	  /** The proration mode for the base plan determines what happens when a user switches to this plan from another base plan. If unspecified, defaults to CHARGE_ON_NEXT_BILLING_DATE. */
		prorationMode: Option[Schema.InstallmentsBasePlanType.ProrationModeEnum] = None
	)
	
	case class RegionalBasePlanConfig(
	  /** Required. Region code this configuration applies to, as defined by ISO 3166-2, e.g. "US". */
		regionCode: Option[String] = None,
	  /** Whether the base plan in the specified region is available for new subscribers. Existing subscribers will not have their subscription canceled if this value is set to false. If not specified, this will default to false. */
		newSubscriberAvailability: Option[Boolean] = None,
	  /** The price of the base plan in the specified region. Must be set if the base plan is available to new subscribers. Must be set in the currency that is linked to the specified region. */
		price: Option[Schema.Money] = None
	)
	
	case class OfferTag(
	  /** Must conform with RFC-1034. That is, this string can only contain lower-case letters (a-z), numbers (0-9), and hyphens (-), and be at most 20 characters. */
		tag: Option[String] = None
	)
	
	case class OtherRegionsBasePlanConfig(
	  /** Required. Price in USD to use for any new locations Play may launch in. */
		usdPrice: Option[Schema.Money] = None,
	  /** Required. Price in EUR to use for any new locations Play may launch in. */
		eurPrice: Option[Schema.Money] = None,
	  /** Whether the base plan is available for new subscribers in any new locations Play may launch in. If not specified, this will default to false. */
		newSubscriberAvailability: Option[Boolean] = None
	)
	
	case class SubscriptionListing(
	  /** Required. The language of this listing, as defined by BCP-47, e.g. "en-US". */
		languageCode: Option[String] = None,
	  /** Required. The title of this subscription in the language of this listing. Plain text. */
		title: Option[String] = None,
	  /** A list of benefits shown to the user on platforms such as the Play Store and in restoration flows in the language of this listing. Plain text. Ordered list of at most four benefits. */
		benefits: Option[List[String]] = None,
	  /** The description of this subscription in the language of this listing. Maximum length - 80 characters. Plain text. */
		description: Option[String] = None
	)
	
	case class RestrictedPaymentCountries(
	  /** Required. Region codes to impose payment restrictions on, as defined by ISO 3166-2, e.g. "US". */
		regionCodes: Option[List[String]] = None
	)
	
	case class BatchGetSubscriptionsResponse(
	  /** The list of requested subscriptions, in the same order as the request. */
		subscriptions: Option[List[Schema.Subscription]] = None
	)
	
	case class ListSubscriptionsResponse(
	  /** The subscriptions from the specified app. */
		subscriptions: Option[List[Schema.Subscription]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class BatchUpdateSubscriptionsRequest(
	  /** Required. A list of update requests of up to 100 elements. All requests must update different subscriptions. */
		requests: Option[List[Schema.UpdateSubscriptionRequest]] = None
	)
	
	object UpdateSubscriptionRequest {
		enum LatencyToleranceEnum extends Enum[LatencyToleranceEnum] { case PRODUCT_UPDATE_LATENCY_TOLERANCE_UNSPECIFIED, PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE, PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT }
	}
	case class UpdateSubscriptionRequest(
	  /** Required. The subscription to update. */
		subscription: Option[Schema.Subscription] = None,
	  /** Required. The list of fields to be updated. */
		updateMask: Option[String] = None,
	  /** Required. The version of the available regions being used for the subscription. */
		regionsVersion: Option[Schema.RegionsVersion] = None,
	  /** Optional. If set to true, and the subscription with the given package_name and product_id doesn't exist, the subscription will be created. If a new subscription is created, update_mask is ignored. */
		allowMissing: Option[Boolean] = None,
	  /** Optional. The latency tolerance for the propagation of this product update. Defaults to latency-sensitive. */
		latencyTolerance: Option[Schema.UpdateSubscriptionRequest.LatencyToleranceEnum] = None
	)
	
	case class RegionsVersion(
	  /** Required. A string representing the version of available regions being used for the specified resource. Regional prices for the resource have to be specified according to the information published in [this article](https://support.google.com/googleplay/android-developer/answer/10532353). Each time the supported locations substantially change, the version will be incremented. Using this field will ensure that creating and updating the resource with an older region's version and set of regional prices and currencies will succeed even though a new version is available. The latest version is 2022/02. */
		version: Option[String] = None
	)
	
	case class BatchUpdateSubscriptionsResponse(
	  /** The updated subscriptions list. */
		subscriptions: Option[List[Schema.Subscription]] = None
	)
	
	case class ArchiveSubscriptionRequest(
	
	)
	
	object ActivateBasePlanRequest {
		enum LatencyToleranceEnum extends Enum[LatencyToleranceEnum] { case PRODUCT_UPDATE_LATENCY_TOLERANCE_UNSPECIFIED, PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE, PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT }
	}
	case class ActivateBasePlanRequest(
	  /** Required. The parent app (package name) of the base plan to activate. */
		packageName: Option[String] = None,
	  /** Required. The parent subscription (ID) of the base plan to activate. */
		productId: Option[String] = None,
	  /** Required. The unique base plan ID of the base plan to activate. */
		basePlanId: Option[String] = None,
	  /** Optional. The latency tolerance for the propagation of this product update. Defaults to latency-sensitive. */
		latencyTolerance: Option[Schema.ActivateBasePlanRequest.LatencyToleranceEnum] = None
	)
	
	object DeactivateBasePlanRequest {
		enum LatencyToleranceEnum extends Enum[LatencyToleranceEnum] { case PRODUCT_UPDATE_LATENCY_TOLERANCE_UNSPECIFIED, PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE, PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT }
	}
	case class DeactivateBasePlanRequest(
	  /** Required. The parent app (package name) of the base plan to deactivate. */
		packageName: Option[String] = None,
	  /** Required. The parent subscription (ID) of the base plan to deactivate. */
		productId: Option[String] = None,
	  /** Required. The unique base plan ID of the base plan to deactivate. */
		basePlanId: Option[String] = None,
	  /** Optional. The latency tolerance for the propagation of this product update. Defaults to latency-sensitive. */
		latencyTolerance: Option[Schema.DeactivateBasePlanRequest.LatencyToleranceEnum] = None
	)
	
	case class BatchUpdateBasePlanStatesRequest(
	  /** Required. The update request list of up to 100 elements. All requests must update different base plans. */
		requests: Option[List[Schema.UpdateBasePlanStateRequest]] = None
	)
	
	case class UpdateBasePlanStateRequest(
	  /** Activates a base plan. Once activated, base plans will be available to new subscribers. */
		activateBasePlanRequest: Option[Schema.ActivateBasePlanRequest] = None,
	  /** Deactivates a base plan. Once deactivated, the base plan will become unavailable to new subscribers, but existing subscribers will maintain their subscription */
		deactivateBasePlanRequest: Option[Schema.DeactivateBasePlanRequest] = None
	)
	
	case class BatchUpdateBasePlanStatesResponse(
	  /** The list of updated subscriptions. This list will match the requests one to one, in the same order. */
		subscriptions: Option[List[Schema.Subscription]] = None
	)
	
	object MigrateBasePlanPricesRequest {
		enum LatencyToleranceEnum extends Enum[LatencyToleranceEnum] { case PRODUCT_UPDATE_LATENCY_TOLERANCE_UNSPECIFIED, PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE, PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT }
	}
	case class MigrateBasePlanPricesRequest(
	  /** Required. Package name of the parent app. Must be equal to the package_name field on the Subscription resource. */
		packageName: Option[String] = None,
	  /** Required. The ID of the subscription to update. Must be equal to the product_id field on the Subscription resource. */
		productId: Option[String] = None,
	  /** Required. The unique base plan ID of the base plan to update prices on. */
		basePlanId: Option[String] = None,
	  /** Required. The regional prices to update. */
		regionalPriceMigrations: Option[List[Schema.RegionalPriceMigrationConfig]] = None,
	  /** Required. The version of the available regions being used for the regional_price_migrations. */
		regionsVersion: Option[Schema.RegionsVersion] = None,
	  /** Optional. The latency tolerance for the propagation of this product update. Defaults to latency-sensitive. */
		latencyTolerance: Option[Schema.MigrateBasePlanPricesRequest.LatencyToleranceEnum] = None
	)
	
	object RegionalPriceMigrationConfig {
		enum PriceIncreaseTypeEnum extends Enum[PriceIncreaseTypeEnum] { case PRICE_INCREASE_TYPE_UNSPECIFIED, PRICE_INCREASE_TYPE_OPT_IN, PRICE_INCREASE_TYPE_OPT_OUT }
	}
	case class RegionalPriceMigrationConfig(
	  /** Required. Region code this configuration applies to, as defined by ISO 3166-2, e.g. "US". */
		regionCode: Option[String] = None,
	  /** Required. Subscribers in all legacy price cohorts before this time will be migrated to the current price. Subscribers in any newer price cohorts are unaffected. Affected subscribers will receive one or more notifications from Google Play about the price change. Price decreases occur at the subscriber's next billing date. Price increases occur at the subscriber's next billing date following a notification period that varies by region and price increase type. */
		oldestAllowedPriceVersionTime: Option[String] = None,
	  /** Optional. The requested type of price increase */
		priceIncreaseType: Option[Schema.RegionalPriceMigrationConfig.PriceIncreaseTypeEnum] = None
	)
	
	case class MigrateBasePlanPricesResponse(
	
	)
	
	case class BatchMigrateBasePlanPricesRequest(
	  /** Required. Up to 100 price migration requests. All requests must update different base plans. */
		requests: Option[List[Schema.MigrateBasePlanPricesRequest]] = None
	)
	
	case class BatchMigrateBasePlanPricesResponse(
	  /** Contains one response per requested price migration, in the same order as the request. */
		responses: Option[List[Schema.MigrateBasePlanPricesResponse]] = None
	)
	
	object SubscriptionOffer {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, DRAFT, ACTIVE, INACTIVE }
	}
	case class SubscriptionOffer(
	  /** Required. Immutable. The package name of the app the parent subscription belongs to. */
		packageName: Option[String] = None,
	  /** Required. Immutable. The ID of the parent subscription this offer belongs to. */
		productId: Option[String] = None,
	  /** Required. Immutable. The ID of the base plan to which this offer is an extension. */
		basePlanId: Option[String] = None,
	  /** Required. Immutable. Unique ID of this subscription offer. Must be unique within the base plan. */
		offerId: Option[String] = None,
	  /** Output only. The current state of this offer. Can be changed using Activate and Deactivate actions. NB: the base plan state supersedes this state, so an active offer may not be available if the base plan is not active. */
		state: Option[Schema.SubscriptionOffer.StateEnum] = None,
	  /** Required. The phases of this subscription offer. Must contain at least one and at most two entries. Users will always receive all these phases in the specified order. */
		phases: Option[List[Schema.SubscriptionOfferPhase]] = None,
	  /** The requirements that users need to fulfil to be eligible for this offer. Represents the requirements that Play will evaluate to decide whether an offer should be returned. Developers may further filter these offers themselves. */
		targeting: Option[Schema.SubscriptionOfferTargeting] = None,
	  /** Required. The region-specific configuration of this offer. Must contain at least one entry. */
		regionalConfigs: Option[List[Schema.RegionalSubscriptionOfferConfig]] = None,
	  /** The configuration for any new locations Play may launch in the future. */
		otherRegionsConfig: Option[Schema.OtherRegionsSubscriptionOfferConfig] = None,
	  /** List of up to 20 custom tags specified for this offer, and returned to the app through the billing library. */
		offerTags: Option[List[Schema.OfferTag]] = None
	)
	
	case class SubscriptionOfferPhase(
	  /** Required. The number of times this phase repeats. If this offer phase is not free, each recurrence charges the user the price of this offer phase. */
		recurrenceCount: Option[Int] = None,
	  /** Required. The duration of a single recurrence of this phase. Specified in ISO 8601 format. */
		duration: Option[String] = None,
	  /** Required. The region-specific configuration of this offer phase. This list must contain exactly one entry for each region for which the subscription offer has a regional config. */
		regionalConfigs: Option[List[Schema.RegionalSubscriptionOfferPhaseConfig]] = None,
	  /** Pricing information for any new locations Play may launch in. */
		otherRegionsConfig: Option[Schema.OtherRegionsSubscriptionOfferPhaseConfig] = None
	)
	
	case class RegionalSubscriptionOfferPhaseConfig(
	  /** Required. Immutable. The region to which this config applies. */
		regionCode: Option[String] = None,
	  /** The absolute price the user pays for this offer phase. The price must not be smaller than the minimum price allowed for this region. */
		price: Option[Schema.Money] = None,
	  /** The fraction of the base plan price prorated over the phase duration that the user pays for this offer phase. For example, if the base plan price for this region is $12 for a period of 1 year, then a 50% discount for a phase of a duration of 3 months would correspond to a price of $1.50. The discount must be specified as a fraction strictly larger than 0 and strictly smaller than 1. The resulting price will be rounded to the nearest billable unit (e.g. cents for USD). The relative discount is considered invalid if the discounted price ends up being smaller than the minimum price allowed in this region. */
		relativeDiscount: Option[BigDecimal] = None,
	  /** The absolute amount of money subtracted from the base plan price prorated over the phase duration that the user pays for this offer phase. For example, if the base plan price for this region is $12 for a period of 1 year, then a $1 absolute discount for a phase of a duration of 3 months would correspond to a price of $2. The resulting price may not be smaller than the minimum price allowed for this region. */
		absoluteDiscount: Option[Schema.Money] = None,
	  /** Set to specify this offer is free to obtain. */
		free: Option[Schema.RegionalSubscriptionOfferPhaseFreePriceOverride] = None
	)
	
	case class RegionalSubscriptionOfferPhaseFreePriceOverride(
	
	)
	
	case class OtherRegionsSubscriptionOfferPhaseConfig(
	  /** The absolute price the user pays for this offer phase. The price must not be smaller than the minimum price allowed for any new locations Play may launch in. */
		otherRegionsPrices: Option[Schema.OtherRegionsSubscriptionOfferPhasePrices] = None,
	  /** The fraction of the base plan price prorated over the phase duration that the user pays for this offer phase. For example, if the base plan price for this region is $12 for a period of 1 year, then a 50% discount for a phase of a duration of 3 months would correspond to a price of $1.50. The discount must be specified as a fraction strictly larger than 0 and strictly smaller than 1. The resulting price will be rounded to the nearest billable unit (e.g. cents for USD). The relative discount is considered invalid if the discounted price ends up being smaller than the minimum price allowed in any new locations Play may launch in. */
		relativeDiscount: Option[BigDecimal] = None,
	  /** The absolute amount of money subtracted from the base plan price prorated over the phase duration that the user pays for this offer phase. For example, if the base plan price for this region is $12 for a period of 1 year, then a $1 absolute discount for a phase of a duration of 3 months would correspond to a price of $2. The resulting price may not be smaller than the minimum price allowed for any new locations Play may launch in. */
		absoluteDiscounts: Option[Schema.OtherRegionsSubscriptionOfferPhasePrices] = None,
	  /** Set to specify this offer is free to obtain. */
		free: Option[Schema.OtherRegionsSubscriptionOfferPhaseFreePriceOverride] = None
	)
	
	case class OtherRegionsSubscriptionOfferPhasePrices(
	  /** Required. Price in USD to use for any new locations Play may launch in. */
		usdPrice: Option[Schema.Money] = None,
	  /** Required. Price in EUR to use for any new locations Play may launch in. */
		eurPrice: Option[Schema.Money] = None
	)
	
	case class OtherRegionsSubscriptionOfferPhaseFreePriceOverride(
	
	)
	
	case class SubscriptionOfferTargeting(
	  /** Offer targeting rule for new user acquisition. */
		acquisitionRule: Option[Schema.AcquisitionTargetingRule] = None,
	  /** Offer targeting rule for upgrading users' existing plans. */
		upgradeRule: Option[Schema.UpgradeTargetingRule] = None
	)
	
	case class AcquisitionTargetingRule(
	  /** Required. The scope of subscriptions this rule considers. Only allows "this subscription" and "any subscription in app". */
		scope: Option[Schema.TargetingRuleScope] = None
	)
	
	case class TargetingRuleScope(
	  /** The scope of the current targeting rule is the subscription in which this offer is defined. */
		thisSubscription: Option[Schema.TargetingRuleScopeThisSubscription] = None,
	  /** The scope of the current targeting rule is any subscription in the parent app. */
		anySubscriptionInApp: Option[Schema.TargetingRuleScopeAnySubscriptionInApp] = None,
	  /** The scope of the current targeting rule is the subscription with the specified subscription ID. Must be a subscription within the same parent app. */
		specificSubscriptionInApp: Option[String] = None
	)
	
	case class TargetingRuleScopeThisSubscription(
	
	)
	
	case class TargetingRuleScopeAnySubscriptionInApp(
	
	)
	
	case class UpgradeTargetingRule(
	  /** Limit this offer to only once per user. If set to true, a user can never be eligible for this offer again if they ever subscribed to this offer. */
		oncePerUser: Option[Boolean] = None,
	  /** Required. The scope of subscriptions this rule considers. Only allows "this subscription" and "specific subscription in app". */
		scope: Option[Schema.TargetingRuleScope] = None,
	  /** The specific billing period duration, specified in ISO 8601 format, that a user must be currently subscribed to to be eligible for this rule. If not specified, users subscribed to any billing period are matched. */
		billingPeriodDuration: Option[String] = None
	)
	
	case class RegionalSubscriptionOfferConfig(
	  /** Required. Immutable. Region code this configuration applies to, as defined by ISO 3166-2, e.g. "US". */
		regionCode: Option[String] = None,
	  /** Whether the subscription offer in the specified region is available for new subscribers. Existing subscribers will not have their subscription cancelled if this value is set to false. If not specified, this will default to false. */
		newSubscriberAvailability: Option[Boolean] = None
	)
	
	case class OtherRegionsSubscriptionOfferConfig(
	  /** Whether the subscription offer in any new locations Play may launch in the future. If not specified, this will default to false. */
		otherRegionsNewSubscriberAvailability: Option[Boolean] = None
	)
	
	case class BatchGetSubscriptionOffersRequest(
	  /** Required. A list of update requests of up to 100 elements. All requests must update different subscriptions. */
		requests: Option[List[Schema.GetSubscriptionOfferRequest]] = None
	)
	
	case class GetSubscriptionOfferRequest(
	  /** Required. The parent app (package name) of the offer to get. */
		packageName: Option[String] = None,
	  /** Required. The parent subscription (ID) of the offer to get. */
		productId: Option[String] = None,
	  /** Required. The parent base plan (ID) of the offer to get. */
		basePlanId: Option[String] = None,
	  /** Required. The unique offer ID of the offer to get. */
		offerId: Option[String] = None
	)
	
	case class BatchGetSubscriptionOffersResponse(
		subscriptionOffers: Option[List[Schema.SubscriptionOffer]] = None
	)
	
	case class ListSubscriptionOffersResponse(
	  /** The subscription offers from the specified subscription. */
		subscriptionOffers: Option[List[Schema.SubscriptionOffer]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class BatchUpdateSubscriptionOffersRequest(
	  /** Required. A list of update requests of up to 100 elements. All requests must update different subscription offers. */
		requests: Option[List[Schema.UpdateSubscriptionOfferRequest]] = None
	)
	
	object UpdateSubscriptionOfferRequest {
		enum LatencyToleranceEnum extends Enum[LatencyToleranceEnum] { case PRODUCT_UPDATE_LATENCY_TOLERANCE_UNSPECIFIED, PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE, PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT }
	}
	case class UpdateSubscriptionOfferRequest(
	  /** Required. The subscription offer to update. */
		subscriptionOffer: Option[Schema.SubscriptionOffer] = None,
	  /** Required. The list of fields to be updated. */
		updateMask: Option[String] = None,
	  /** Required. The version of the available regions being used for the subscription_offer. */
		regionsVersion: Option[Schema.RegionsVersion] = None,
	  /** Optional. If set to true, and the subscription offer with the given package_name, product_id, base_plan_id and offer_id doesn't exist, an offer will be created. If a new offer is created, update_mask is ignored. */
		allowMissing: Option[Boolean] = None,
	  /** Optional. The latency tolerance for the propagation of this product update. Defaults to latency-sensitive. */
		latencyTolerance: Option[Schema.UpdateSubscriptionOfferRequest.LatencyToleranceEnum] = None
	)
	
	case class BatchUpdateSubscriptionOffersResponse(
	  /** The updated subscription offers list. */
		subscriptionOffers: Option[List[Schema.SubscriptionOffer]] = None
	)
	
	object ActivateSubscriptionOfferRequest {
		enum LatencyToleranceEnum extends Enum[LatencyToleranceEnum] { case PRODUCT_UPDATE_LATENCY_TOLERANCE_UNSPECIFIED, PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE, PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT }
	}
	case class ActivateSubscriptionOfferRequest(
	  /** Required. The parent app (package name) of the offer to activate. */
		packageName: Option[String] = None,
	  /** Required. The parent subscription (ID) of the offer to activate. */
		productId: Option[String] = None,
	  /** Required. The parent base plan (ID) of the offer to activate. */
		basePlanId: Option[String] = None,
	  /** Required. The unique offer ID of the offer to activate. */
		offerId: Option[String] = None,
	  /** Optional. The latency tolerance for the propagation of this product update. Defaults to latency-sensitive. */
		latencyTolerance: Option[Schema.ActivateSubscriptionOfferRequest.LatencyToleranceEnum] = None
	)
	
	object DeactivateSubscriptionOfferRequest {
		enum LatencyToleranceEnum extends Enum[LatencyToleranceEnum] { case PRODUCT_UPDATE_LATENCY_TOLERANCE_UNSPECIFIED, PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_SENSITIVE, PRODUCT_UPDATE_LATENCY_TOLERANCE_LATENCY_TOLERANT }
	}
	case class DeactivateSubscriptionOfferRequest(
	  /** Required. The parent app (package name) of the offer to deactivate. */
		packageName: Option[String] = None,
	  /** Required. The parent subscription (ID) of the offer to deactivate. */
		productId: Option[String] = None,
	  /** Required. The parent base plan (ID) of the offer to deactivate. */
		basePlanId: Option[String] = None,
	  /** Required. The unique offer ID of the offer to deactivate. */
		offerId: Option[String] = None,
	  /** Optional. The latency tolerance for the propagation of this product update. Defaults to latency-sensitive. */
		latencyTolerance: Option[Schema.DeactivateSubscriptionOfferRequest.LatencyToleranceEnum] = None
	)
	
	case class BatchUpdateSubscriptionOfferStatesRequest(
	  /** Required. The update request list of up to 100 elements. All requests must update different offers. */
		requests: Option[List[Schema.UpdateSubscriptionOfferStateRequest]] = None
	)
	
	case class UpdateSubscriptionOfferStateRequest(
	  /** Activates an offer. Once activated, the offer will be available to new subscribers. */
		activateSubscriptionOfferRequest: Option[Schema.ActivateSubscriptionOfferRequest] = None,
	  /** Deactivates an offer. Once deactivated, the offer will become unavailable to new subscribers, but existing subscribers will maintain their subscription */
		deactivateSubscriptionOfferRequest: Option[Schema.DeactivateSubscriptionOfferRequest] = None
	)
	
	case class BatchUpdateSubscriptionOfferStatesResponse(
	  /** The updated subscription offers list. */
		subscriptionOffers: Option[List[Schema.SubscriptionOffer]] = None
	)
	
	case class Variant(
	  /** Output only. The ID of a previously created system APK variant. */
		variantId: Option[Int] = None,
	  /** The device spec used to generate the APK. */
		deviceSpec: Option[Schema.DeviceSpec] = None,
	  /** Optional. Options applied to the generated APK. */
		options: Option[Schema.SystemApkOptions] = None
	)
	
	case class DeviceSpec(
	  /** Supported ABI architectures in the order of preference. The values should be the string as reported by the platform, e.g. "armeabi-v7a", "x86_64". */
		supportedAbis: Option[List[String]] = None,
	  /** All installed locales represented as BCP-47 strings, e.g. "en-US". */
		supportedLocales: Option[List[String]] = None,
	  /** Screen dpi. */
		screenDensity: Option[Int] = None
	)
	
	case class SystemApkOptions(
	  /** Whether system APK was generated with uncompressed native libraries. */
		uncompressedNativeLibraries: Option[Boolean] = None,
	  /** Whether system APK was generated with uncompressed dex files. */
		uncompressedDexFiles: Option[Boolean] = None,
	  /** Whether to use the rotated key for signing the system APK. */
		rotated: Option[Boolean] = None
	)
	
	case class SystemApksListResponse(
	  /** All system APK variants created. */
		variants: Option[List[Schema.Variant]] = None
	)
	
	case class Testers(
	  /** All testing Google Groups, as email addresses. */
		googleGroups: Option[List[String]] = None
	)
	
	case class Track(
	  /** Identifier of the track. Form factor tracks have a special prefix as an identifier, for example `wear:production`, `automotive:production`. [More on track name](https://developers.google.com/android-publisher/tracks#ff-track-name) */
		track: Option[String] = None,
	  /** In a read request, represents all active releases in the track. In an update request, represents desired changes. */
		releases: Option[List[Schema.TrackRelease]] = None
	)
	
	object TrackRelease {
		enum StatusEnum extends Enum[StatusEnum] { case statusUnspecified, draft, inProgress, halted, completed }
	}
	case class TrackRelease(
	  /** The release name. Not required to be unique. If not set, the name is generated from the APK's version_name. If the release contains multiple APKs, the name is generated from the date. */
		name: Option[String] = None,
	  /** Version codes of all APKs in the release. Must include version codes to retain from previous releases. */
		versionCodes: Option[List[String]] = None,
	  /** A description of what is new in this release. */
		releaseNotes: Option[List[Schema.LocalizedText]] = None,
	  /** The status of the release. */
		status: Option[Schema.TrackRelease.StatusEnum] = None,
	  /** Fraction of users who are eligible for a staged release. 0 < fraction < 1. Can only be set when status is "inProgress" or "halted". */
		userFraction: Option[BigDecimal] = None,
	  /** Restricts a release to a specific set of countries. */
		countryTargeting: Option[Schema.CountryTargeting] = None,
	  /** In-app update priority of the release. All newly added APKs in the release will be considered at this priority. Can take values in the range [0, 5], with 5 the highest priority. Defaults to 0. in_app_update_priority can not be updated once the release is rolled out. See https://developer.android.com/guide/playcore/in-app-updates. */
		inAppUpdatePriority: Option[Int] = None
	)
	
	case class LocalizedText(
	  /** Language localization code (a BCP-47 language tag; for example, "de-AT" for Austrian German). */
		language: Option[String] = None,
	  /** The text in the given language. */
		text: Option[String] = None
	)
	
	case class CountryTargeting(
	  /** Countries to target, specified as two letter [CLDR codes](https://unicode.org/cldr/charts/latest/supplemental/territory_containment_un_m_49.html). */
		countries: Option[List[String]] = None,
	  /** Include "rest of world" as well as explicitly targeted countries. */
		includeRestOfWorld: Option[Boolean] = None
	)
	
	case class TracksListResponse(
	  /** The kind of this response ("androidpublisher#tracksListResponse"). */
		kind: Option[String] = None,
	  /** All tracks (including tracks with no releases). */
		tracks: Option[List[Schema.Track]] = None
	)
	
	object TrackConfig {
		enum TypeEnum extends Enum[TypeEnum] { case TRACK_TYPE_UNSPECIFIED, CLOSED_TESTING }
		enum FormFactorEnum extends Enum[FormFactorEnum] { case FORM_FACTOR_UNSPECIFIED, DEFAULT, WEAR, AUTOMOTIVE }
	}
	case class TrackConfig(
	  /** Required. Identifier of the new track. For default tracks, this field consists of the track alias only. Form factor tracks have a special prefix as an identifier, for example `wear:production`, `automotive:production`. This prefix must match the value of the `form_factor` field, if it is not a default track. [More on track name](https://developers.google.com/android-publisher/tracks#ff-track-name) */
		track: Option[String] = None,
	  /** Required. Type of the new track. Currently, the only supported value is closedTesting. */
		`type`: Option[Schema.TrackConfig.TypeEnum] = None,
	  /** Required. Form factor of the new track. Defaults to the default track. */
		formFactor: Option[Schema.TrackConfig.FormFactorEnum] = None
	)
	
	case class VoidedPurchasesListResponse(
	  /** General pagination information. */
		pageInfo: Option[Schema.PageInfo] = None,
	  /** Pagination information for token pagination. */
		tokenPagination: Option[Schema.TokenPagination] = None,
		voidedPurchases: Option[List[Schema.VoidedPurchase]] = None
	)
	
	case class VoidedPurchase(
	  /** This kind represents a voided purchase object in the androidpublisher service. */
		kind: Option[String] = None,
	  /** The token which uniquely identifies a one-time purchase or subscription. To uniquely identify subscription renewals use order_id (available starting from version 3 of the API). */
		purchaseToken: Option[String] = None,
	  /** The time at which the purchase was made, in milliseconds since the epoch (Jan 1, 1970). */
		purchaseTimeMillis: Option[String] = None,
	  /** The time at which the purchase was canceled/refunded/charged-back, in milliseconds since the epoch (Jan 1, 1970). */
		voidedTimeMillis: Option[String] = None,
	  /** The order id which uniquely identifies a one-time purchase, subscription purchase, or subscription renewal. */
		orderId: Option[String] = None,
	  /** The initiator of voided purchase, possible values are: 0. User 1. Developer 2. Google */
		voidedSource: Option[Int] = None,
	  /** The reason why the purchase was voided, possible values are: 0. Other 1. Remorse 2. Not_received 3. Defective 4. Accidental_purchase 5. Fraud 6. Friendly_fraud 7. Chargeback 8. Unacknowledged_purchase */
		voidedReason: Option[Int] = None,
	  /** The voided quantity as the result of a quantity-based partial refund. Voided purchases of quantity-based partial refunds may only be returned when includeQuantityBasedPartialRefund is set to true. */
		voidedQuantity: Option[Int] = None
	)
}
