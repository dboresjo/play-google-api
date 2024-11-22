package com.boresjo.play.api.google.androidenterprise

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class DevicesListResponse(
	  /** A managed device. */
		device: Option[List[Schema.Device]] = None
	)
	
	object Device {
		enum ManagementTypeEnum extends Enum[ManagementTypeEnum] { case managedDevice, managedProfile, containerApp, unmanagedProfile }
	}
	case class Device(
	  /** The Google Play Services Android ID for the device encoded as a lowercase hex string. For example, "123456789abcdef0". */
		androidId: Option[String] = None,
	  /** Identifies the extent to which the device is controlled by a managed Google Play EMM in various deployment configurations. Possible values include: - "managedDevice", a device that has the EMM's device policy controller (DPC) as the device owner. - "managedProfile", a device that has a profile managed by the DPC (DPC is profile owner) in addition to a separate, personal profile that is unavailable to the DPC. - "containerApp", no longer used (deprecated). - "unmanagedProfile", a device that has been allowed (by the domain's admin, using the Admin Console to enable the privilege) to use managed Google Play, but the profile is itself not owned by a DPC.  */
		managementType: Option[Schema.Device.ManagementTypeEnum] = None,
	  /** The policy enforced on the device. */
		policy: Option[Schema.Policy] = None,
	  /** The device report updated with the latest app states. */
		report: Option[Schema.DeviceReport] = None,
	  /** The build fingerprint of the device if known. */
		latestBuildFingerprint: Option[String] = None,
	  /** API compatibility version. */
		sdkVersion: Option[Int] = None,
	  /** The manufacturer of the device. This comes from android.os.Build.MANUFACTURER. */
		maker: Option[String] = None,
	  /** The model name of the device. This comes from android.os.Build.MODEL. */
		model: Option[String] = None,
	  /** The internal hardware codename of the device. This comes from android.os.Build.DEVICE. (field named "device" per logs/wireless/android/android_checkin.proto) */
		device: Option[String] = None,
	  /** The product name of the device. This comes from android.os.Build.PRODUCT. */
		product: Option[String] = None,
	  /** Retail brand for the device, if set. See android.os.Build.BRAND */
		retailBrand: Option[String] = None
	)
	
	object Policy {
		enum AutoUpdatePolicyEnum extends Enum[AutoUpdatePolicyEnum] { case autoUpdatePolicyUnspecified, choiceToTheUser, never, wifiOnly, always }
		enum ProductAvailabilityPolicyEnum extends Enum[ProductAvailabilityPolicyEnum] { case productAvailabilityPolicyUnspecified, whitelist, all }
		enum DeviceReportPolicyEnum extends Enum[DeviceReportPolicyEnum] { case deviceReportPolicyUnspecified, deviceReportDisabled, deviceReportEnabled }
	}
	case class Policy(
	  /** Controls when automatic app updates on the device can be applied. Recommended alternative: autoUpdateMode which is set per app, provides greater flexibility around update frequency. When autoUpdateMode is set to AUTO_UPDATE_POSTPONED or AUTO_UPDATE_HIGH_PRIORITY, autoUpdatePolicy has no effect. - choiceToTheUser allows the device's user to configure the app update policy. - always enables auto updates. - never disables auto updates. - wifiOnly enables auto updates only when the device is connected to wifi. &#42;Important:&#42; Changes to app update policies don't affect updates that are in progress. Any policy changes will apply to subsequent app updates.  */
		autoUpdatePolicy: Option[Schema.Policy.AutoUpdatePolicyEnum] = None,
	  /** The availability granted to the device for the specified products. "all" gives the device access to all products, regardless of approval status. "all" does not enable automatic visibility of "alpha" or "beta" tracks. "whitelist" grants the device access the products specified in productPolicy[]. Only products that are approved or products that were previously approved (products with revoked approval) by the enterprise can be whitelisted. If no value is provided, the availability set at the user level is applied by default. */
		productAvailabilityPolicy: Option[Schema.Policy.ProductAvailabilityPolicyEnum] = None,
	  /** The list of product policies. The productAvailabilityPolicy needs to be set to WHITELIST or ALL for the product policies to be applied. */
		productPolicy: Option[List[Schema.ProductPolicy]] = None,
	  /** The maintenance window defining when apps running in the foreground should be updated. */
		maintenanceWindow: Option[Schema.MaintenanceWindow] = None,
	  /** Whether the device reports app states to the EMM. The default value is "deviceReportDisabled". */
		deviceReportPolicy: Option[Schema.Policy.DeviceReportPolicyEnum] = None,
	  /** An identifier for the policy that will be passed with the app install feedback sent from the Play Store. */
		policyId: Option[String] = None
	)
	
	object ProductPolicy {
		enum TracksEnum extends Enum[TracksEnum] { case appTrackUnspecified, production, beta, alpha }
		enum AutoUpdateModeEnum extends Enum[AutoUpdateModeEnum] { case autoUpdateModeUnspecified, autoUpdateDefault, autoUpdatePostponed, autoUpdateHighPriority }
	}
	case class ProductPolicy(
	  /** The ID of the product. For example, "app:com.google.android.gm". */
		productId: Option[String] = None,
	  /** Deprecated. Use trackIds instead. */
		tracks: Option[List[Schema.ProductPolicy.TracksEnum]] = None,
	  /** Grants the device visibility to the specified product release track(s), identified by trackIds. The list of release tracks of a product can be obtained by calling Products.Get. */
		trackIds: Option[List[String]] = None,
	  /** The managed configuration for the product. */
		managedConfiguration: Option[Schema.ManagedConfiguration] = None,
	  /** The auto-install policy for the product. */
		autoInstallPolicy: Option[Schema.AutoInstallPolicy] = None,
	  /** The auto-update mode for the product. When autoUpdateMode is used, it always takes precedence over the user's choice. So when a user makes changes to the device settings manually, these changes are ignored. */
		autoUpdateMode: Option[Schema.ProductPolicy.AutoUpdateModeEnum] = None,
	  /** An authentication URL configuration for the authenticator app of an identity provider. This helps to launch the identity provider's authenticator app during the authentication happening in a private app using Android WebView. Authenticator app should already be the default handler for the authentication url on the device. */
		enterpriseAuthenticationAppLinkConfigs: Option[List[Schema.EnterpriseAuthenticationAppLinkConfig]] = None
	)
	
	case class ManagedConfiguration(
	  /** Deprecated. */
		kind: Option[String] = None,
	  /** The ID of the product that the managed configuration is for, e.g. "app:com.google.android.gm". */
		productId: Option[String] = None,
	  /** The set of managed properties for this configuration. */
		managedProperty: Option[List[Schema.ManagedProperty]] = None,
	  /** Contains the ID of the managed configuration profile and the set of configuration variables (if any) defined for the user. */
		configurationVariables: Option[Schema.ConfigurationVariables] = None
	)
	
	case class ManagedProperty(
	  /** The unique key that identifies the property. */
		key: Option[String] = None,
	  /** The boolean value - this will only be present if type of the property is bool. */
		valueBool: Option[Boolean] = None,
	  /** The integer value - this will only be present if type of the property is integer. */
		valueInteger: Option[Int] = None,
	  /** The string value - this will only be present if type of the property is string, choice or hidden. */
		valueString: Option[String] = None,
	  /** The list of string values - this will only be present if type of the property is multiselect. */
		valueStringArray: Option[List[String]] = None,
	  /** The bundle of managed properties - this will only be present if type of the property is bundle. */
		valueBundle: Option[Schema.ManagedPropertyBundle] = None,
	  /** The list of bundles of properties - this will only be present if type of the property is bundle_array. */
		valueBundleArray: Option[List[Schema.ManagedPropertyBundle]] = None
	)
	
	case class ManagedPropertyBundle(
	  /** The list of managed properties. */
		managedProperty: Option[List[Schema.ManagedProperty]] = None
	)
	
	case class ConfigurationVariables(
	  /** The ID of the managed configurations settings. */
		mcmId: Option[String] = None,
	  /** The variable set that is attributed to the user. */
		variableSet: Option[List[Schema.VariableSet]] = None
	)
	
	case class VariableSet(
	  /** The placeholder string; defined by EMM. */
		placeholder: Option[String] = None,
	  /** The value of the placeholder, specific to the user. */
		userValue: Option[String] = None
	)
	
	object AutoInstallPolicy {
		enum AutoInstallModeEnum extends Enum[AutoInstallModeEnum] { case autoInstallModeUnspecified, doNotAutoInstall, autoInstallOnce, forceAutoInstall }
	}
	case class AutoInstallPolicy(
	  /** The auto-install mode. If unset, defaults to "doNotAutoInstall". An app is automatically installed regardless of a set maintenance window. */
		autoInstallMode: Option[Schema.AutoInstallPolicy.AutoInstallModeEnum] = None,
	  /** The priority of the install, as an unsigned integer. A lower number means higher priority. */
		autoInstallPriority: Option[Int] = None,
	  /** The constraints for auto-installing the app. You can specify a maximum of one constraint. */
		autoInstallConstraint: Option[List[Schema.AutoInstallConstraint]] = None,
	  /** The minimum version of the app. If a lower version of the app is installed, then the app will be auto-updated according to the auto-install constraints, instead of waiting for the regular auto-update. You can set a minimum version code for at most 20 apps per device. */
		minimumVersionCode: Option[Int] = None
	)
	
	object AutoInstallConstraint {
		enum NetworkTypeConstraintEnum extends Enum[NetworkTypeConstraintEnum] { case networkTypeConstraintUnspecified, anyNetwork, unmeteredNetwork }
		enum ChargingStateConstraintEnum extends Enum[ChargingStateConstraintEnum] { case chargingStateConstraintUnspecified, chargingNotRequired, chargingRequired }
		enum DeviceIdleStateConstraintEnum extends Enum[DeviceIdleStateConstraintEnum] { case deviceIdleStateConstraintUnspecified, deviceIdleNotRequired, deviceIdleRequired }
	}
	case class AutoInstallConstraint(
	  /** Network type constraint. */
		networkTypeConstraint: Option[Schema.AutoInstallConstraint.NetworkTypeConstraintEnum] = None,
	  /** Charging state constraint. */
		chargingStateConstraint: Option[Schema.AutoInstallConstraint.ChargingStateConstraintEnum] = None,
	  /** Device idle state constraint. */
		deviceIdleStateConstraint: Option[Schema.AutoInstallConstraint.DeviceIdleStateConstraintEnum] = None
	)
	
	case class EnterpriseAuthenticationAppLinkConfig(
	  /** An authentication url. */
		uri: Option[String] = None
	)
	
	case class MaintenanceWindow(
	  /** Start time of the maintenance window, in milliseconds after midnight on the device. Windows can span midnight. */
		startTimeAfterMidnightMs: Option[String] = None,
	  /** Duration of the maintenance window, in milliseconds. The duration must be between 30 minutes and 24 hours (inclusive). */
		durationMs: Option[String] = None
	)
	
	case class DeviceReport(
	  /** The timestamp of the last report update in milliseconds since epoch. This field will always be present. */
		lastUpdatedTimestampMillis: Option[String] = None,
	  /** List of app states set by managed apps on the device. App states are defined by the app's developers. This field will always be present. */
		appState: Option[List[Schema.AppState]] = None
	)
	
	case class AppState(
	  /** The package name of the app. This field will always be present. */
		packageName: Option[String] = None,
	  /** List of keyed app states. This field will always be present. */
		keyedAppState: Option[List[Schema.KeyedAppState]] = None
	)
	
	object KeyedAppState {
		enum SeverityEnum extends Enum[SeverityEnum] { case severityUnknown, severityInfo, severityError }
	}
	case class KeyedAppState(
	  /** Key indicating what the app is providing a state for. The content of the key is set by the app's developer. To prevent XSS, we recommend removing any HTML from the key before displaying it. This field will always be present. */
		key: Option[String] = None,
	  /** Timestamp of when the app set the state in milliseconds since epoch. This field will always be present. */
		stateTimestampMillis: Option[String] = None,
	  /** Severity of the app state. This field will always be present. */
		severity: Option[Schema.KeyedAppState.SeverityEnum] = None,
	  /** Free-form, human-readable message describing the app state. For example, an error message. To prevent XSS, we recommend removing any HTML from the message before displaying it. */
		message: Option[String] = None,
	  /** Additional field intended for machine-readable data. For example, a number or JSON object. To prevent XSS, we recommend removing any HTML from the data before displaying it. */
		data: Option[String] = None
	)
	
	object DeviceState {
		enum AccountStateEnum extends Enum[AccountStateEnum] { case enabled, disabled }
	}
	case class DeviceState(
	  /** The state of the Google account on the device. "enabled" indicates that the Google account on the device can be used to access Google services (including Google Play), while "disabled" means that it cannot. A new device is initially in the "disabled" state. */
		accountState: Option[Schema.DeviceState.AccountStateEnum] = None
	)
	
	case class EnterprisesListResponse(
	  /** An enterprise. */
		enterprise: Option[List[Schema.Enterprise]] = None
	)
	
	case class Enterprise(
	  /** The unique ID for the enterprise. */
		id: Option[String] = None,
	  /** The enterprise's primary domain, such as "example.com". */
		primaryDomain: Option[String] = None,
	  /** The name of the enterprise, for example, "Example, Inc". */
		name: Option[String] = None,
	  /** Admins of the enterprise. This is only supported for enterprises created via the EMM-initiated flow. */
		administrator: Option[List[Schema.Administrator]] = None,
	  /** Output only. Settings for Google-provided user authentication. */
		googleAuthenticationSettings: Option[Schema.GoogleAuthenticationSettings] = None
	)
	
	case class Administrator(
	  /** The admin's email address. */
		email: Option[String] = None
	)
	
	object GoogleAuthenticationSettings {
		enum GoogleAuthenticationRequiredEnum extends Enum[GoogleAuthenticationRequiredEnum] { case googleAuthenticationRequiredUnspecified, notRequired, required }
		enum DedicatedDevicesAllowedEnum extends Enum[DedicatedDevicesAllowedEnum] { case dedicatedDevicesAllowedUnspecified, disallowed, allowed }
	}
	case class GoogleAuthenticationSettings(
	  /** Whether Google authentication is required. */
		googleAuthenticationRequired: Option[Schema.GoogleAuthenticationSettings.GoogleAuthenticationRequiredEnum] = None,
	  /** Whether dedicated devices are allowed. */
		dedicatedDevicesAllowed: Option[Schema.GoogleAuthenticationSettings.DedicatedDevicesAllowedEnum] = None
	)
	
	case class EnterpriseAccount(
	  /** The email address of the service account. */
		accountEmail: Option[String] = None
	)
	
	case class EnterprisesSendTestPushNotificationResponse(
	  /** The name of the Cloud Pub/Sub topic to which notifications for this enterprise's enrolled account will be sent. */
		topicName: Option[String] = None,
	  /** The message ID of the test push notification that was sent. */
		messageId: Option[String] = None
	)
	
	case class NotificationSet(
	  /** The notification set ID, required to mark the notification as received with the Enterprises.AcknowledgeNotification API. This will be omitted if no notifications are present. */
		notificationSetId: Option[String] = None,
	  /** The notifications received, or empty if no notifications are present. */
		notification: Option[List[Schema.Notification]] = None
	)
	
	object Notification {
		enum NotificationTypeEnum extends Enum[NotificationTypeEnum] { case unknown, testNotification, productApproval, installFailure, appUpdate, newPermissions, appRestricionsSchemaChange, productAvailabilityChange, newDevice, deviceReportUpdate }
	}
	case class Notification(
	  /** The ID of the enterprise for which the notification is sent. This will always be present. */
		enterpriseId: Option[String] = None,
	  /** The time when the notification was published in milliseconds since 1970-01-01T00:00:00Z. This will always be present. */
		timestampMillis: Option[String] = None,
	  /** Type of the notification. */
		notificationType: Option[Schema.Notification.NotificationTypeEnum] = None,
	  /** Notifications about changes to a product's approval status. */
		productApprovalEvent: Option[Schema.ProductApprovalEvent] = None,
	  /** Notifications about an app installation failure. */
		installFailureEvent: Option[Schema.InstallFailureEvent] = None,
	  /** Notifications about app updates. */
		appUpdateEvent: Option[Schema.AppUpdateEvent] = None,
	  /** Notifications about new app permissions. */
		newPermissionsEvent: Option[Schema.NewPermissionsEvent] = None,
	  /** Notifications about new app restrictions schema changes. */
		appRestrictionsSchemaChangeEvent: Option[Schema.AppRestrictionsSchemaChangeEvent] = None,
	  /** Notifications about product availability changes. */
		productAvailabilityChangeEvent: Option[Schema.ProductAvailabilityChangeEvent] = None,
	  /** Notifications about new devices. */
		newDeviceEvent: Option[Schema.NewDeviceEvent] = None,
	  /** Notifications about device report updates. */
		deviceReportUpdateEvent: Option[Schema.DeviceReportUpdateEvent] = None
	)
	
	object ProductApprovalEvent {
		enum ApprovedEnum extends Enum[ApprovedEnum] { case unknown, approved, unapproved }
	}
	case class ProductApprovalEvent(
	  /** The id of the product (e.g. "app:com.google.android.gm") for which the approval status has changed. This field will always be present. */
		productId: Option[String] = None,
	  /** Whether the product was approved or unapproved. This field will always be present. */
		approved: Option[Schema.ProductApprovalEvent.ApprovedEnum] = None
	)
	
	object InstallFailureEvent {
		enum FailureReasonEnum extends Enum[FailureReasonEnum] { case unknown, timeout }
	}
	case class InstallFailureEvent(
	  /** The id of the product (e.g. "app:com.google.android.gm") for which the install failure event occured. This field will always be present. */
		productId: Option[String] = None,
	  /** The Android ID of the device. This field will always be present. */
		deviceId: Option[String] = None,
	  /** The ID of the user. This field will always be present. */
		userId: Option[String] = None,
	  /** The reason for the installation failure. This field will always be present. */
		failureReason: Option[Schema.InstallFailureEvent.FailureReasonEnum] = None,
	  /** Additional details on the failure if applicable. */
		failureDetails: Option[String] = None
	)
	
	case class AppUpdateEvent(
	  /** The id of the product (e.g. "app:com.google.android.gm") that was updated. This field will always be present. */
		productId: Option[String] = None
	)
	
	case class NewPermissionsEvent(
	  /** The id of the product (e.g. "app:com.google.android.gm") for which new permissions were added. This field will always be present. */
		productId: Option[String] = None,
	  /** The set of permissions that the app is currently requesting. Use Permissions.Get on the EMM API to retrieve details about these permissions. */
		requestedPermissions: Option[List[String]] = None,
	  /** The set of permissions that the enterprise admin has already approved for this application. Use Permissions.Get on the EMM API to retrieve details about these permissions. */
		approvedPermissions: Option[List[String]] = None
	)
	
	case class AppRestrictionsSchemaChangeEvent(
	  /** The id of the product (e.g. "app:com.google.android.gm") for which the app restriction schema changed. This field will always be present. */
		productId: Option[String] = None
	)
	
	object ProductAvailabilityChangeEvent {
		enum AvailabilityStatusEnum extends Enum[AvailabilityStatusEnum] { case unknown, available, removed, unpublished }
	}
	case class ProductAvailabilityChangeEvent(
	  /** The id of the product (e.g. "app:com.google.android.gm") for which the product availability changed. This field will always be present. */
		productId: Option[String] = None,
	  /** The new state of the product. This field will always be present. */
		availabilityStatus: Option[Schema.ProductAvailabilityChangeEvent.AvailabilityStatusEnum] = None
	)
	
	object NewDeviceEvent {
		enum ManagementTypeEnum extends Enum[ManagementTypeEnum] { case managedDevice, managedProfile }
	}
	case class NewDeviceEvent(
	  /** The ID of the user. This field will always be present. */
		userId: Option[String] = None,
	  /** The Android ID of the device. This field will always be present. */
		deviceId: Option[String] = None,
	  /** Identifies the extent to which the device is controlled by an Android EMM in various deployment configurations. Possible values include: - "managedDevice", a device where the DPC is set as device owner, - "managedProfile", a device where the DPC is set as profile owner.  */
		managementType: Option[Schema.NewDeviceEvent.ManagementTypeEnum] = None,
	  /** Policy app on the device. */
		dpcPackageName: Option[String] = None
	)
	
	case class DeviceReportUpdateEvent(
	  /** The ID of the user. This field will always be present. */
		userId: Option[String] = None,
	  /** The Android ID of the device. This field will always be present. */
		deviceId: Option[String] = None,
	  /** The device report updated with the latest app states. This field will always be present. */
		report: Option[Schema.DeviceReport] = None
	)
	
	object StoreLayout {
		enum StoreLayoutTypeEnum extends Enum[StoreLayoutTypeEnum] { case unknown, basic, custom }
	}
	case class StoreLayout(
	  /** The ID of the store page to be used as the homepage. The homepage is the first page shown in the managed Google Play Store. Not specifying a homepage is equivalent to setting the store layout type to "basic". */
		homepageId: Option[String] = None,
	  /** The store layout type. By default, this value is set to "basic" if the homepageId field is not set, and to "custom" otherwise. If set to "basic", the layout will consist of all approved apps that have been whitelisted for the user. */
		storeLayoutType: Option[Schema.StoreLayout.StoreLayoutTypeEnum] = None
	)
	
	case class SignupInfo(
	  /** Deprecated. */
		kind: Option[String] = None,
	  /** A URL under which the Admin can sign up for an enterprise. The page pointed to cannot be rendered in an iframe. */
		url: Option[String] = None,
	  /** An opaque token that will be required, along with the Enterprise Token, for obtaining the enterprise resource from CompleteSignup. */
		completionToken: Option[String] = None
	)
	
	case class ServiceAccount(
	  /** The account name of the service account, in the form of an email address. Assigned by the server. */
		name: Option[String] = None,
	  /** Credentials that can be used to authenticate as this ServiceAccount. */
		key: Option[Schema.ServiceAccountKey] = None
	)
	
	object ServiceAccountKey {
		enum TypeEnum extends Enum[TypeEnum] { case googleCredentials, pkcs12 }
	}
	case class ServiceAccountKey(
	  /** An opaque, unique identifier for this ServiceAccountKey. Assigned by the server. */
		id: Option[String] = None,
	  /** The file format of the generated key data. */
		`type`: Option[Schema.ServiceAccountKey.TypeEnum] = None,
	  /** The body of the private key credentials file, in string format. This is only populated when the ServiceAccountKey is created, and is not stored by Google. */
		data: Option[String] = None,
	  /** Public key data for the credentials file. This is an X.509 cert. If you are using the googleCredentials key type, this is identical to the cert that can be retrieved by using the X.509 cert url inside of the credentials file. */
		publicData: Option[String] = None
	)
	
	object AdministratorWebTokenSpec {
		enum PermissionEnum extends Enum[PermissionEnum] { case unknown, approveApps, manageMcm }
	}
	case class AdministratorWebTokenSpec(
	  /** Deprecated. Use PlaySearch.approveApps. */
		permission: Option[List[Schema.AdministratorWebTokenSpec.PermissionEnum]] = None,
	  /** The URI of the parent frame hosting the iframe. To prevent XSS, the iframe may not be hosted at other URIs. This URI must be https. Use whitespaces to separate multiple parent URIs. */
		parent: Option[String] = None,
	  /** Options for displaying the managed Play Search apps page. */
		playSearch: Option[Schema.AdministratorWebTokenSpecPlaySearch] = None,
	  /** Options for displaying the Private Apps page. */
		privateApps: Option[Schema.AdministratorWebTokenSpecPrivateApps] = None,
	  /** Options for displaying the Web Apps page. */
		webApps: Option[Schema.AdministratorWebTokenSpecWebApps] = None,
	  /** Options for displaying the Organize apps page. */
		storeBuilder: Option[Schema.AdministratorWebTokenSpecStoreBuilder] = None,
	  /** Options for displaying the Managed Configuration page. */
		managedConfigurations: Option[Schema.AdministratorWebTokenSpecManagedConfigurations] = None,
	  /** Options for displaying the Zero Touch page. */
		zeroTouch: Option[Schema.AdministratorWebTokenSpecZeroTouch] = None
	)
	
	case class AdministratorWebTokenSpecPlaySearch(
	  /** Whether the managed Play Search apps page is displayed. Default is true. */
		enabled: Option[Boolean] = None,
	  /** Allow access to the iframe in approve mode. Default is false. */
		approveApps: Option[Boolean] = None
	)
	
	case class AdministratorWebTokenSpecPrivateApps(
	  /** Whether the Private Apps page is displayed. Default is true. */
		enabled: Option[Boolean] = None
	)
	
	case class AdministratorWebTokenSpecWebApps(
	  /** Whether the Web Apps page is displayed. Default is true. */
		enabled: Option[Boolean] = None
	)
	
	case class AdministratorWebTokenSpecStoreBuilder(
	  /** Whether the Organize apps page is displayed. Default is true. */
		enabled: Option[Boolean] = None
	)
	
	case class AdministratorWebTokenSpecManagedConfigurations(
	  /** Whether the Managed Configuration page is displayed. Default is true. */
		enabled: Option[Boolean] = None
	)
	
	case class AdministratorWebTokenSpecZeroTouch(
	  /** Whether zero-touch embedded UI is usable with this token. If enabled, the admin can link zero-touch customers to this enterprise. */
		enabled: Option[Boolean] = None
	)
	
	case class AdministratorWebToken(
	  /** An opaque token to be passed to the Play front-end to generate an iframe. */
		token: Option[String] = None
	)
	
	case class CreateEnrollmentTokenResponse(
	  /** Deprecated: Use token instead. This field will be removed in the future. */
		enrollmentToken: Option[String] = None,
	  /** [Required] The created enrollment token. */
		token: Option[Schema.EnrollmentToken] = None
	)
	
	object EnrollmentToken {
		enum EnrollmentTokenTypeEnum extends Enum[EnrollmentTokenTypeEnum] { case enrollmentTokenTypeUnspecified, userlessDevice, userDevice }
	}
	case class EnrollmentToken(
	  /** The token value that's passed to the device and authorizes the device to enroll. This is a read-only field generated by the server. */
		token: Option[String] = None,
	  /** [Required] The type of the enrollment token. */
		enrollmentTokenType: Option[Schema.EnrollmentToken.EnrollmentTokenTypeEnum] = None,
	  /** [Optional] The length of time the enrollment token is valid, ranging from 1 minute to [`Durations.MAX_VALUE`](https://developers.google.com/protocol-buffers/docs/reference/java/com/google/protobuf/util/Durations.html#MAX_VALUE), approximately 10,000 years. If not specified, the default duration is 1 hour. */
		duration: Option[String] = None
	)
	
	case class EntitlementsListResponse(
	  /** An entitlement of a user to a product (e.g. an app). For example, a free app that they have installed, or a paid app that they have been allocated a license to. */
		entitlement: Option[List[Schema.Entitlement]] = None
	)
	
	object Entitlement {
		enum ReasonEnum extends Enum[ReasonEnum] { case free, groupLicense, userPurchase }
	}
	case class Entitlement(
	  /** The ID of the product that the entitlement is for. For example, "app:com.google.android.gm". */
		productId: Option[String] = None,
	  /** The reason for the entitlement. For example, "free" for free apps. This property is temporary: it will be replaced by the acquisition kind field of group licenses. */
		reason: Option[Schema.Entitlement.ReasonEnum] = None
	)
	
	case class GroupLicenseUsersListResponse(
	  /** A user of an enterprise. */
		user: Option[List[Schema.User]] = None
	)
	
	object User {
		enum ManagementTypeEnum extends Enum[ManagementTypeEnum] { case googleManaged, emmManaged }
		enum AccountTypeEnum extends Enum[AccountTypeEnum] { case deviceAccount, userAccount }
	}
	case class User(
	  /** The unique ID for the user. */
		id: Option[String] = None,
	  /** The entity that manages the user. With googleManaged users, the source of truth is Google so EMMs have to make sure a Google Account exists for the user. With emmManaged users, the EMM is in charge. */
		managementType: Option[Schema.User.ManagementTypeEnum] = None,
	  /** The type of account that this user represents. A userAccount can be installed on multiple devices, but a deviceAccount is specific to a single device. An EMM-managed user (emmManaged) can be either type (userAccount, deviceAccount), but a Google-managed user (googleManaged) is always a userAccount. */
		accountType: Option[Schema.User.AccountTypeEnum] = None,
	  /** The user's primary email address, for example, "jsmith@example.com". Will always be set for Google managed users and not set for EMM managed users. */
		primaryEmail: Option[String] = None,
	  /** A unique identifier you create for this user, such as "user342" or "asset#44418". Do not use personally identifiable information (PII) for this property. Must always be set for EMM-managed users. Not set for Google-managed users. */
		accountIdentifier: Option[String] = None,
	  /** The name that will appear in user interfaces. Setting this property is optional when creating EMM-managed users. If you do set this property, use something generic about the organization (such as "Example, Inc.") or your name (as EMM). Not used for Google-managed user accounts. @mutable androidenterprise.users.update */
		displayName: Option[String] = None
	)
	
	case class GroupLicensesListResponse(
	  /** A group license for a product approved for use in the enterprise. */
		groupLicense: Option[List[Schema.GroupLicense]] = None
	)
	
	object GroupLicense {
		enum AcquisitionKindEnum extends Enum[AcquisitionKindEnum] { case free, bulkPurchase }
		enum ApprovalEnum extends Enum[ApprovalEnum] { case approved, unapproved }
		enum PermissionsEnum extends Enum[PermissionsEnum] { case currentApproved, needsReapproval, allCurrentAndFutureApproved }
	}
	case class GroupLicense(
	  /** The ID of the product that the license is for. For example, "app:com.google.android.gm". */
		productId: Option[String] = None,
	  /** The number of purchased licenses (possibly in multiple purchases). If this field is omitted, then there is no limit on the number of licenses that can be provisioned (for example, if the acquisition kind is "free"). */
		numPurchased: Option[Int] = None,
	  /** The total number of provisioned licenses for this product. Returned by read operations, but ignored in write operations. */
		numProvisioned: Option[Int] = None,
	  /** How this group license was acquired. "bulkPurchase" means that this Grouplicenses resource was created because the enterprise purchased licenses for this product; otherwise, the value is "free" (for free products). */
		acquisitionKind: Option[Schema.GroupLicense.AcquisitionKindEnum] = None,
	  /** Whether the product to which this group license relates is currently approved by the enterprise. Products are approved when a group license is first created, but this approval may be revoked by an enterprise admin via Google Play. Unapproved products will not be visible to end users in collections, and new entitlements to them should not normally be created. */
		approval: Option[Schema.GroupLicense.ApprovalEnum] = None,
	  /** The permission approval status of the product. This field is only set if the product is approved. Possible states are: - "currentApproved", the current set of permissions is approved, but additional permissions will require the administrator to reapprove the product (If the product was approved without specifying the approved permissions setting, then this is the default behavior.), - "needsReapproval", the product has unapproved permissions. No additional product licenses can be assigned until the product is reapproved, - "allCurrentAndFutureApproved", the current permissions are approved and any future permission updates will be automatically approved without administrator review.  */
		permissions: Option[Schema.GroupLicense.PermissionsEnum] = None
	)
	
	case class InstallsListResponse(
	  /** An installation of an app for a user on a specific device. The existence of an install implies that the user must have an entitlement to the app. */
		install: Option[List[Schema.Install]] = None
	)
	
	object Install {
		enum InstallStateEnum extends Enum[InstallStateEnum] { case installed, installPending }
	}
	case class Install(
	  /** The ID of the product that the install is for. For example, "app:com.google.android.gm". */
		productId: Option[String] = None,
	  /** The version of the installed product. Guaranteed to be set only if the install state is "installed". */
		versionCode: Option[Int] = None,
	  /** Install state. The state "installPending" means that an install request has recently been made and download to the device is in progress. The state "installed" means that the app has been installed. This field is read-only. */
		installState: Option[Schema.Install.InstallStateEnum] = None
	)
	
	case class ManagedConfigurationsForDeviceListResponse(
	  /** A managed configuration for an app on a specific device. */
		managedConfigurationForDevice: Option[List[Schema.ManagedConfiguration]] = None
	)
	
	case class ManagedConfigurationsForUserListResponse(
	  /** A managed configuration for an app for a specific user. */
		managedConfigurationForUser: Option[List[Schema.ManagedConfiguration]] = None
	)
	
	case class ManagedConfigurationsSettingsListResponse(
	  /** A managed configurations settings for an app that may be assigned to a group of users in an enterprise. */
		managedConfigurationsSettings: Option[List[Schema.ManagedConfigurationsSettings]] = None
	)
	
	case class ManagedConfigurationsSettings(
	  /** The ID of the managed configurations settings. */
		mcmId: Option[String] = None,
	  /** The name of the managed configurations settings. */
		name: Option[String] = None,
	  /** The last updated time of the managed configuration settings in milliseconds since 1970-01-01T00:00:00Z. */
		lastUpdatedTimestampMillis: Option[String] = None
	)
	
	case class Permission(
	  /** An opaque string uniquely identifying the permission. */
		permissionId: Option[String] = None,
	  /** The name of the permission. */
		name: Option[String] = None,
	  /** A longer description of the Permissions resource, giving more details of what it affects. */
		description: Option[String] = None
	)
	
	object Product {
		enum DistributionChannelEnum extends Enum[DistributionChannelEnum] { case publicGoogleHosted, privateGoogleHosted, privateSelfHosted }
		enum ProductPricingEnum extends Enum[ProductPricingEnum] { case unknown, free, freeWithInAppPurchase, paid }
		enum AvailableTracksEnum extends Enum[AvailableTracksEnum] { case appTrackUnspecified, production, beta, alpha }
		enum ContentRatingEnum extends Enum[ContentRatingEnum] { case ratingUnknown, all, preTeen, teen, mature }
		enum FeaturesEnum extends Enum[FeaturesEnum] { case featureUnknown, vpnApp }
	}
	case class Product(
	  /** A string of the form &#42;app:<package name>&#42;. For example, app:com.google.android.gm represents the Gmail app. */
		productId: Option[String] = None,
	  /** The name of the product. */
		title: Option[String] = None,
	  /** The name of the author of the product (for example, the app developer). */
		authorName: Option[String] = None,
	  /** A link to an image that can be used as an icon for the product. This image is suitable for use at up to 512px x 512px. */
		iconUrl: Option[String] = None,
	  /** A link to a smaller image that can be used as an icon for the product. This image is suitable for use at up to 128px x 128px. */
		smallIconUrl: Option[String] = None,
	  /** A link to the (consumer) Google Play details page for the product. */
		detailsUrl: Option[String] = None,
	  /** A link to the managed Google Play details page for the product, for use by an Enterprise admin. */
		workDetailsUrl: Option[String] = None,
	  /** Deprecated. */
		requiresContainerApp: Option[Boolean] = None,
	  /** App versions currently available for this product. */
		appVersion: Option[List[Schema.AppVersion]] = None,
	  /** How and to whom the package is made available. The value publicGoogleHosted means that the package is available through the Play store and not restricted to a specific enterprise. The value privateGoogleHosted means that the package is a private app (restricted to an enterprise) but hosted by Google. The value privateSelfHosted means that the package is a private app (restricted to an enterprise) and is privately hosted. */
		distributionChannel: Option[Schema.Product.DistributionChannelEnum] = None,
	  /** Whether this product is free, free with in-app purchases, or paid. If the pricing is unknown, this means the product is not generally available anymore (even though it might still be available to people who own it). */
		productPricing: Option[Schema.Product.ProductPricingEnum] = None,
	  /** The certificate used to sign this product. */
		signingCertificate: Option[Schema.ProductSigningCertificate] = None,
	  /** Deprecated, use appTracks instead. */
		availableTracks: Option[List[Schema.Product.AvailableTracksEnum]] = None,
	  /** The tracks visible to the enterprise. */
		appTracks: Option[List[Schema.TrackInfo]] = None,
	  /** The localized promotional description, if available. */
		description: Option[String] = None,
	  /** The localized full app store description, if available. */
		fullDescription: Option[String] = None,
	  /** A list of screenshot links representing the app. */
		screenshotUrls: Option[List[String]] = None,
	  /** The app category (e.g. RACING, SOCIAL, etc.) */
		category: Option[String] = None,
	  /** A description of the recent changes made to the app. */
		recentChanges: Option[String] = None,
	  /** The minimum Android SDK necessary to run the app. */
		minAndroidSdkVersion: Option[Int] = None,
	  /** The content rating for this app. */
		contentRating: Option[Schema.Product.ContentRatingEnum] = None,
	  /** The approximate time (within 7 days) the app was last published, expressed in milliseconds since epoch. */
		lastUpdatedTimestampMillis: Option[String] = None,
	  /** A list of permissions required by the app. */
		permissions: Option[List[Schema.ProductPermission]] = None,
	  /** The countries which this app is available in. */
		availableCountries: Option[List[String]] = None,
	  /** Noteworthy features (if any) of this product. */
		features: Option[List[Schema.Product.FeaturesEnum]] = None,
	  /** The app restriction schema */
		appRestrictionsSchema: Option[Schema.AppRestrictionsSchema] = None
	)
	
	object AppVersion {
		enum TrackEnum extends Enum[TrackEnum] { case appTrackUnspecified, production, beta, alpha }
	}
	case class AppVersion(
	  /** The string used in the Play store by the app developer to identify the version. The string is not necessarily unique or localized (for example, the string could be "1.4"). */
		versionString: Option[String] = None,
	  /** Unique increasing identifier for the app version. */
		versionCode: Option[Int] = None,
	  /** Deprecated, use trackId instead. */
		track: Option[Schema.AppVersion.TrackEnum] = None,
	  /** The SDK version this app targets, as specified in the manifest of the APK. See http://developer.android.com/guide/topics/manifest/uses-sdk-element.html */
		targetSdkVersion: Option[Int] = None,
	  /** Track ids that the app version is published in. Replaces the track field (deprecated), but doesn't include the production track (see isProduction instead). */
		trackId: Option[List[String]] = None,
	  /** True if this version is a production APK. */
		isProduction: Option[Boolean] = None
	)
	
	case class ProductSigningCertificate(
	  /** The base64 urlsafe encoded SHA2-256 hash of the certificate. */
		certificateHashSha256: Option[String] = None,
	  /** The base64 urlsafe encoded SHA1 hash of the certificate. (This field is deprecated in favor of SHA2-256. It should not be used and may be removed at any time.) */
		certificateHashSha1: Option[String] = None
	)
	
	case class TrackInfo(
	  /** Unmodifiable, unique track identifier. This identifier is the releaseTrackId in the url of the play developer console page that displays the track information. */
		trackId: Option[String] = None,
	  /** A modifiable name for a track. This is the visible name in the play developer console. */
		trackAlias: Option[String] = None
	)
	
	object ProductPermission {
		enum StateEnum extends Enum[StateEnum] { case required, accepted }
	}
	case class ProductPermission(
	  /** An opaque string uniquely identifying the permission. */
		permissionId: Option[String] = None,
	  /** Whether the permission has been accepted or not. */
		state: Option[Schema.ProductPermission.StateEnum] = None
	)
	
	case class AppRestrictionsSchema(
	  /** Deprecated. */
		kind: Option[String] = None,
	  /** The set of restrictions that make up this schema. */
		restrictions: Option[List[Schema.AppRestrictionsSchemaRestriction]] = None
	)
	
	object AppRestrictionsSchemaRestriction {
		enum RestrictionTypeEnum extends Enum[RestrictionTypeEnum] { case bool, string, integer, choice, multiselect, hidden, bundle, bundleArray }
	}
	case class AppRestrictionsSchemaRestriction(
	  /** The unique key that the product uses to identify the restriction, e.g. "com.google.android.gm.fieldname". */
		key: Option[String] = None,
	  /** The name of the restriction. */
		title: Option[String] = None,
	  /** The type of the restriction. */
		restrictionType: Option[Schema.AppRestrictionsSchemaRestriction.RestrictionTypeEnum] = None,
	  /** A longer description of the restriction, giving more detail of what it affects. */
		description: Option[String] = None,
	  /** For choice or multiselect restrictions, the list of possible entries' human-readable names. */
		entry: Option[List[String]] = None,
	  /** For choice or multiselect restrictions, the list of possible entries' machine-readable values. These values should be used in the configuration, either as a single string value for a choice restriction or in a stringArray for a multiselect restriction. */
		entryValue: Option[List[String]] = None,
	  /** The default value of the restriction. bundle and bundleArray restrictions never have a default value. */
		defaultValue: Option[Schema.AppRestrictionsSchemaRestrictionRestrictionValue] = None,
	  /** For bundle or bundleArray restrictions, the list of nested restrictions. A bundle restriction is always nested within a bundleArray restriction, and a bundleArray restriction is at most two levels deep. */
		nestedRestriction: Option[List[Schema.AppRestrictionsSchemaRestriction]] = None
	)
	
	object AppRestrictionsSchemaRestrictionRestrictionValue {
		enum TypeEnum extends Enum[TypeEnum] { case bool, string, integer, choice, multiselect, hidden, bundle, bundleArray }
	}
	case class AppRestrictionsSchemaRestrictionRestrictionValue(
	  /** The type of the value being provided. */
		`type`: Option[Schema.AppRestrictionsSchemaRestrictionRestrictionValue.TypeEnum] = None,
	  /** The boolean value - this will only be present if type is bool. */
		valueBool: Option[Boolean] = None,
	  /** The string value - this will be present for types string, choice and hidden. */
		valueString: Option[String] = None,
	  /** The integer value - this will only be present if type is integer. */
		valueInteger: Option[Int] = None,
	  /** The list of string values - this will only be present if type is multiselect. */
		valueMultiselect: Option[List[String]] = None
	)
	
	case class ProductsListResponse(
	  /** General pagination information. */
		pageInfo: Option[Schema.PageInfo] = None,
	  /** Pagination information for token pagination. */
		tokenPagination: Option[Schema.TokenPagination] = None,
	  /** Information about a product (e.g. an app) in the Google Play store, for display to an enterprise admin. */
		product: Option[List[Schema.Product]] = None
	)
	
	case class PageInfo(
	  /** Total number of results available on the backend ! The total number of results in the result set. */
		totalResults: Option[Int] = None,
	  /** Maximum number of results returned in one page. ! The number of results included in the API response. */
		resultPerPage: Option[Int] = None,
	  /** Index of the first result returned in the current page. */
		startIndex: Option[Int] = None
	)
	
	case class TokenPagination(
	  /** Tokens to pass to the standard list field 'page_token'. Whenever available, tokens are preferred over manipulating start_index. */
		nextPageToken: Option[String] = None,
		previousPageToken: Option[String] = None
	)
	
	case class ProductPermissions(
	  /** The ID of the app that the permissions relate to, e.g. "app:com.google.android.gm". */
		productId: Option[String] = None,
	  /** The permissions required by the app. */
		permission: Option[List[Schema.ProductPermission]] = None
	)
	
	case class ProductsGenerateApprovalUrlResponse(
	  /** A URL that can be rendered in an iframe to display the permissions (if any) of a product. This URL can be used to approve the product only once and only within 24 hours of being generated, using the Products.approve call. If the product is currently unapproved and has no permissions, this URL will point to an empty page. If the product is currently approved, a URL will only be generated if that product has added permissions since it was last approved, and the URL will only display those new permissions that have not yet been accepted. */
		url: Option[String] = None
	)
	
	object ProductsApproveRequest {
		enum ApprovedPermissionsEnum extends Enum[ApprovedPermissionsEnum] { case currentPermissionsOnly, allPermissions }
	}
	case class ProductsApproveRequest(
	  /** The approval URL that was shown to the user. Only the permissions shown to the user with that URL will be accepted, which may not be the product's entire set of permissions. For example, the URL may only display new permissions from an update after the product was approved, or not include new permissions if the product was updated since the URL was generated. */
		approvalUrlInfo: Option[Schema.ApprovalUrlInfo] = None,
	  /** Sets how new permission requests for the product are handled. "allPermissions" automatically approves all current and future permissions for the product. "currentPermissionsOnly" approves the current set of permissions for the product, but any future permissions added through updates will require manual reapproval. If not specified, only the current set of permissions will be approved. */
		approvedPermissions: Option[Schema.ProductsApproveRequest.ApprovedPermissionsEnum] = None
	)
	
	case class ApprovalUrlInfo(
	  /** A URL that displays a product's permissions and that can also be used to approve the product with the Products.approve call. */
		approvalUrl: Option[String] = None
	)
	
	case class ServiceAccountKeysListResponse(
	  /** The service account credentials. */
		serviceAccountKey: Option[List[Schema.ServiceAccountKey]] = None
	)
	
	case class StoreLayoutClustersListResponse(
	  /** A store cluster of an enterprise. */
		cluster: Option[List[Schema.StoreCluster]] = None
	)
	
	case class StoreCluster(
	  /** Unique ID of this cluster. Assigned by the server. Immutable once assigned. */
		id: Option[String] = None,
	  /** Ordered list of localized strings giving the name of this page. The text displayed is the one that best matches the user locale, or the first entry if there is no good match. There needs to be at least one entry. */
		name: Option[List[Schema.LocalizedText]] = None,
	  /** List of products in the order they are displayed in the cluster. There should not be duplicates within a cluster. */
		productId: Option[List[String]] = None,
	  /** String (US-ASCII only) used to determine order of this cluster within the parent page's elements. Page elements are sorted in lexicographic order of this field. Duplicated values are allowed, but ordering between elements with duplicate order is undefined. The value of this field is never visible to a user, it is used solely for the purpose of defining an ordering. Maximum length is 256 characters. */
		orderInPage: Option[String] = None
	)
	
	case class LocalizedText(
	  /** The BCP47 tag for a locale. (e.g. "en-US", "de"). */
		locale: Option[String] = None,
	  /** The text localized in the associated locale. */
		text: Option[String] = None
	)
	
	case class StoreLayoutPagesListResponse(
	  /** A store page of an enterprise. */
		page: Option[List[Schema.StorePage]] = None
	)
	
	case class StorePage(
	  /** Unique ID of this page. Assigned by the server. Immutable once assigned. */
		id: Option[String] = None,
	  /** Ordered list of localized strings giving the name of this page. The text displayed is the one that best matches the user locale, or the first entry if there is no good match. There needs to be at least one entry. */
		name: Option[List[Schema.LocalizedText]] = None,
	  /** Ordered list of pages a user should be able to reach from this page. The list can't include this page. It is recommended that the basic pages are created first, before adding the links between pages. The API doesn't verify that the pages exist or the pages are reachable. */
		link: Option[List[String]] = None
	)
	
	case class UsersListResponse(
	  /** A user of an enterprise. */
		user: Option[List[Schema.User]] = None
	)
	
	case class AuthenticationToken(
	  /** The authentication token to be passed to the device policy client on the device where it can be used to provision the account for which this token was generated. */
		token: Option[String] = None
	)
	
	object ProductSet {
		enum ProductSetBehaviorEnum extends Enum[ProductSetBehaviorEnum] { case unknown, whitelist, includeAll, allApproved }
	}
	case class ProductSet(
	  /** The list of product IDs making up the set of products. */
		productId: Option[List[String]] = None,
	  /** The interpretation of this product set. "unknown" should never be sent and is ignored if received. "whitelist" means that the user is entitled to access the product set. "includeAll" means that all products are accessible, including products that are approved, products with revoked approval, and products that have never been approved. "allApproved" means that the user is entitled to access all products that are approved for the enterprise. If the value is "allApproved" or "includeAll", the productId field is ignored. If no value is provided, it is interpreted as "whitelist" for backwards compatibility. Further "allApproved" or "includeAll" does not enable automatic visibility of "alpha" or "beta" tracks for Android app. Use ProductVisibility to enable "alpha" or "beta" tracks per user. */
		productSetBehavior: Option[Schema.ProductSet.ProductSetBehaviorEnum] = None,
	  /** Additional list of product IDs making up the product set. Unlike the productID array, in this list It's possible to specify which tracks (alpha, beta, production) of a product are visible to the user. See ProductVisibility and its fields for more information. Specifying the same product ID both here and in the productId array is not allowed and it will result in an error. */
		productVisibility: Option[List[Schema.ProductVisibility]] = None
	)
	
	object ProductVisibility {
		enum TracksEnum extends Enum[TracksEnum] { case appTrackUnspecified, production, beta, alpha }
	}
	case class ProductVisibility(
	  /** The product ID to make visible to the user. Required for each item in the productVisibility list. */
		productId: Option[String] = None,
	  /** Deprecated. Use trackIds instead. */
		tracks: Option[List[Schema.ProductVisibility.TracksEnum]] = None,
	  /** Grants the user visibility to the specified product track(s), identified by trackIds. */
		trackIds: Option[List[String]] = None
	)
	
	object WebApp {
		enum DisplayModeEnum extends Enum[DisplayModeEnum] { case displayModeUnspecified, minimalUi, standalone, fullScreen }
	}
	case class WebApp(
	  /** The ID of the application. A string of the form "app:<package name>" where the package name always starts with the prefix "com.google.enterprise.webapp." followed by a random id. */
		webAppId: Option[String] = None,
	  /** The title of the web app as displayed to the user (e.g., amongst a list of other applications, or as a label for an icon). */
		title: Option[String] = None,
	  /** The start URL, i.e. the URL that should load when the user opens the application. */
		startUrl: Option[String] = None,
	  /** A list of icons representing this website. If absent, a default icon (for create) or the current icon (for update) will be used. */
		icons: Option[List[Schema.WebAppIcon]] = None,
	  /** The display mode of the web app. Possible values include: - "minimalUi", the device's status bar, navigation bar, the app's URL, and a refresh button are visible when the app is open. For HTTP URLs, you can only select this option. - "standalone", the device's status bar and navigation bar are visible when the app is open. - "fullScreen", the app opens in full screen mode, hiding the device's status and navigation bars. All browser UI elements, page URL, system status bar and back button are not visible, and the web app takes up the entirety of the available display area.  */
		displayMode: Option[Schema.WebApp.DisplayModeEnum] = None,
	  /** The current version of the app. Note that the version can automatically increase during the lifetime of the web app, while Google does internal housekeeping to keep the web app up-to-date. */
		versionCode: Option[String] = None,
	  /** A flag whether the app has been published to the Play store yet. */
		isPublished: Option[Boolean] = None
	)
	
	case class WebAppIcon(
	  /** The actual bytes of the image in a base64url encoded string (c.f. RFC4648, section 5 "Base 64 Encoding with URL and Filename Safe Alphabet"). - The image type can be png or jpg. - The image should ideally be square. - The image should ideally have a size of 512x512.  */
		imageData: Option[String] = None
	)
	
	case class WebAppsListResponse(
	  /** The manifest describing a web app. */
		webApp: Option[List[Schema.WebApp]] = None
	)
}
