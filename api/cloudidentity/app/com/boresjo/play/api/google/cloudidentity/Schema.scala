package com.boresjo.play.api.google.cloudidentity

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object GoogleAppsCloudidentityDevicesV1Device {
		enum OwnerTypeEnum extends Enum[OwnerTypeEnum] { case DEVICE_OWNERSHIP_UNSPECIFIED, COMPANY, BYOD }
		enum DeviceTypeEnum extends Enum[DeviceTypeEnum] { case DEVICE_TYPE_UNSPECIFIED, ANDROID, IOS, GOOGLE_SYNC, WINDOWS, MAC_OS, LINUX, CHROME_OS }
		enum EncryptionStateEnum extends Enum[EncryptionStateEnum] { case ENCRYPTION_STATE_UNSPECIFIED, UNSUPPORTED_BY_DEVICE, ENCRYPTED, NOT_ENCRYPTED }
		enum ManagementStateEnum extends Enum[ManagementStateEnum] { case MANAGEMENT_STATE_UNSPECIFIED, APPROVED, BLOCKED, PENDING, UNPROVISIONED, WIPING, WIPED }
		enum CompromisedStateEnum extends Enum[CompromisedStateEnum] { case COMPROMISED_STATE_UNSPECIFIED, COMPROMISED, UNCOMPROMISED }
	}
	case class GoogleAppsCloudidentityDevicesV1Device(
	  /** Output only. [Resource name](https://cloud.google.com/apis/design/resource_names) of the Device in format: `devices/{device}`, where device is the unique id assigned to the Device. */
		name: Option[String] = None,
	  /** Output only. When the Company-Owned device was imported. This field is empty for BYOD devices. */
		createTime: Option[String] = None,
	  /** Most recent time when device synced with this service. */
		lastSyncTime: Option[String] = None,
	  /** Output only. Whether the device is owned by the company or an individual */
		ownerType: Option[Schema.GoogleAppsCloudidentityDevicesV1Device.OwnerTypeEnum] = None,
	  /** Output only. Model name of device. Example: Pixel 3. */
		model: Option[String] = None,
	  /** Output only. OS version of the device. Example: Android 8.1.0. */
		osVersion: Option[String] = None,
	  /** Output only. Type of device. */
		deviceType: Option[Schema.GoogleAppsCloudidentityDevicesV1Device.DeviceTypeEnum] = None,
	  /** Serial Number of device. Example: HT82V1A01076. */
		serialNumber: Option[String] = None,
	  /** Asset tag of the device. */
		assetTag: Option[String] = None,
	  /** Output only. IMEI number of device if GSM device; empty otherwise. */
		imei: Option[String] = None,
	  /** Output only. MEID number of device if CDMA device; empty otherwise. */
		meid: Option[String] = None,
	  /** WiFi MAC addresses of device. */
		wifiMacAddresses: Option[List[String]] = None,
	  /** Output only. Mobile or network operator of device, if available. */
		networkOperator: Option[String] = None,
	  /** Output only. Device manufacturer. Example: Motorola. */
		manufacturer: Option[String] = None,
	  /** Output only. OS release version. Example: 6.0. */
		releaseVersion: Option[String] = None,
	  /** Output only. Device brand. Example: Samsung. */
		brand: Option[String] = None,
	  /** Output only. Build number of the device. */
		buildNumber: Option[String] = None,
	  /** Output only. Kernel version of the device. */
		kernelVersion: Option[String] = None,
	  /** Output only. Baseband version of the device. */
		basebandVersion: Option[String] = None,
	  /** Output only. Whether developer options is enabled on device. */
		enabledDeveloperOptions: Option[Boolean] = None,
	  /** Output only. Domain name for Google accounts on device. Type for other accounts on device. On Android, will only be populated if |ownership_privilege| is |PROFILE_OWNER| or |DEVICE_OWNER|. Does not include the account signed in to the device policy app if that account's domain has only one account. Examples: "com.example", "xyz.com". */
		otherAccounts: Option[List[String]] = None,
	  /** Output only. Whether USB debugging is enabled on device. */
		enabledUsbDebugging: Option[Boolean] = None,
	  /** Output only. OS security patch update time on device. */
		securityPatchTime: Option[String] = None,
	  /** Output only. Device bootloader version. Example: 0.6.7. */
		bootloaderVersion: Option[String] = None,
	  /** Output only. Device encryption state. */
		encryptionState: Option[Schema.GoogleAppsCloudidentityDevicesV1Device.EncryptionStateEnum] = None,
	  /** Output only. Attributes specific to Android devices. */
		androidSpecificAttributes: Option[Schema.GoogleAppsCloudidentityDevicesV1AndroidAttributes] = None,
	  /** Output only. Management state of the device */
		managementState: Option[Schema.GoogleAppsCloudidentityDevicesV1Device.ManagementStateEnum] = None,
	  /** Output only. Represents whether the Device is compromised. */
		compromisedState: Option[Schema.GoogleAppsCloudidentityDevicesV1Device.CompromisedStateEnum] = None,
	  /** Unique identifier for the device. */
		deviceId: Option[String] = None,
	  /** Output only. Unified device id of the device. */
		unifiedDeviceId: Option[String] = None,
	  /** Output only. Attributes specific to [Endpoint Verification](https://cloud.google.com/endpoint-verification/docs/overview) devices. */
		endpointVerificationSpecificAttributes: Option[Schema.GoogleAppsCloudidentityDevicesV1EndpointVerificationSpecificAttributes] = None,
	  /** Host name of the device. */
		hostname: Option[String] = None
	)
	
	object GoogleAppsCloudidentityDevicesV1AndroidAttributes {
		enum OwnershipPrivilegeEnum extends Enum[OwnershipPrivilegeEnum] { case OWNERSHIP_PRIVILEGE_UNSPECIFIED, DEVICE_ADMINISTRATOR, PROFILE_OWNER, DEVICE_OWNER }
	}
	case class GoogleAppsCloudidentityDevicesV1AndroidAttributes(
	  /** Whether applications from unknown sources can be installed on device. */
		enabledUnknownSources: Option[Boolean] = None,
	  /** Whether device supports Android work profiles. If false, this service will not block access to corp data even if an administrator turns on the "Enforce Work Profile" policy. */
		supportsWorkProfile: Option[Boolean] = None,
	  /** Whether this account is on an owner/primary profile. For phones, only true for owner profiles. Android 4+ devices can have secondary or restricted user profiles. */
		ownerProfileAccount: Option[Boolean] = None,
	  /** Ownership privileges on device. */
		ownershipPrivilege: Option[Schema.GoogleAppsCloudidentityDevicesV1AndroidAttributes.OwnershipPrivilegeEnum] = None,
	  /** Whether Android verified boot status is GREEN. */
		verifiedBoot: Option[Boolean] = None,
	  /** Whether the device passes Android CTS compliance. */
		ctsProfileMatch: Option[Boolean] = None,
	  /** Whether Google Play Protect Verify Apps is enabled. */
		verifyAppsEnabled: Option[Boolean] = None,
	  /** Whether any potentially harmful apps were detected on the device. */
		hasPotentiallyHarmfulApps: Option[Boolean] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1EndpointVerificationSpecificAttributes(
	  /** Details of certificates. */
		certificateAttributes: Option[List[Schema.GoogleAppsCloudidentityDevicesV1CertificateAttributes]] = None,
	  /** Details of browser profiles reported by Endpoint Verification. */
		browserAttributes: Option[List[Schema.GoogleAppsCloudidentityDevicesV1BrowserAttributes]] = None,
	  /** [Additional signals](https://cloud.google.com/endpoint-verification/docs/device-information) reported by Endpoint Verification. It includes the following attributes: &#42; Non-configurable attributes: hotfixes, av_installed, av_enabled, windows_domain_name, is_os_native_firewall_enabled, and is_secure_boot_enabled. &#42; [Configurable attributes](https://cloud.google.com/endpoint-verification/docs/collect-config-attributes): file, folder, and binary attributes; registry entries; and properties in a plist. */
		additionalSignals: Option[Map[String, JsValue]] = None
	)
	
	object GoogleAppsCloudidentityDevicesV1CertificateAttributes {
		enum ValidationStateEnum extends Enum[ValidationStateEnum] { case CERTIFICATE_VALIDATION_STATE_UNSPECIFIED, VALIDATION_SUCCESSFUL, VALIDATION_FAILED }
	}
	case class GoogleAppsCloudidentityDevicesV1CertificateAttributes(
	  /** The encoded certificate fingerprint. */
		fingerprint: Option[String] = None,
	  /** The certificate thumbprint. */
		thumbprint: Option[String] = None,
	  /** Output only. Validation state of this certificate. */
		validationState: Option[Schema.GoogleAppsCloudidentityDevicesV1CertificateAttributes.ValidationStateEnum] = None,
	  /** Serial number of the certificate, Example: "123456789". */
		serialNumber: Option[String] = None,
	  /** Certificate not valid before this timestamp. */
		validityStartTime: Option[String] = None,
	  /** Certificate not valid at or after this timestamp. */
		validityExpirationTime: Option[String] = None,
	  /** The name of the issuer of this certificate. */
		issuer: Option[String] = None,
	  /** The subject name of this certificate. */
		subject: Option[String] = None,
	  /** The X.509 extension for CertificateTemplate. */
		certificateTemplate: Option[Schema.GoogleAppsCloudidentityDevicesV1CertificateTemplate] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1CertificateTemplate(
	  /** The template id of the template. Example: "1.3.6.1.4.1.311.21.8.15608621.11768144.5720724.16068415.6889630.81.2472537.7784047". */
		id: Option[String] = None,
	  /** The Major version of the template. Example: 100. */
		majorVersion: Option[Int] = None,
	  /** The minor version of the template. Example: 12. */
		minorVersion: Option[Int] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1BrowserAttributes(
	  /** Timestamp in milliseconds since the Unix epoch when the profile/gcm id was last synced. */
		lastProfileSyncTime: Option[String] = None,
	  /** Represents the current state of the [Chrome browser attributes](https://cloud.google.com/access-context-manager/docs/browser-attributes) sent by the [Endpoint Verification extension](https://chromewebstore.google.com/detail/endpoint-verification/callobklhcbilhphinckomhgkigmfocg?pli=1). */
		chromeBrowserInfo: Option[Schema.GoogleAppsCloudidentityDevicesV1BrowserInfo] = None,
	  /** Chrome profile ID that is exposed by the Chrome API. It is unique for each device. */
		chromeProfileId: Option[String] = None
	)
	
	object GoogleAppsCloudidentityDevicesV1BrowserInfo {
		enum BrowserManagementStateEnum extends Enum[BrowserManagementStateEnum] { case UNSPECIFIED, UNMANAGED, MANAGED_BY_OTHER_DOMAIN, PROFILE_MANAGED, BROWSER_MANAGED }
		enum SafeBrowsingProtectionLevelEnum extends Enum[SafeBrowsingProtectionLevelEnum] { case SAFE_BROWSING_LEVEL_UNSPECIFIED, DISABLED, STANDARD, ENHANCED }
		enum PasswordProtectionWarningTriggerEnum extends Enum[PasswordProtectionWarningTriggerEnum] { case PASSWORD_PROTECTION_TRIGGER_UNSPECIFIED, PROTECTION_OFF, PASSWORD_REUSE, PHISHING_REUSE }
	}
	case class GoogleAppsCloudidentityDevicesV1BrowserInfo(
	  /** Version of the request initiating browser. E.g. `91.0.4442.4`. */
		browserVersion: Option[String] = None,
	  /** Output only. Browser's management state. */
		browserManagementState: Option[Schema.GoogleAppsCloudidentityDevicesV1BrowserInfo.BrowserManagementStateEnum] = None,
	  /** Current state of [file upload analysis](https://chromeenterprise.google/policies/#OnFileAttachedEnterpriseConnector). Set to true if provider list from Chrome is non-empty. */
		isFileUploadAnalysisEnabled: Option[Boolean] = None,
	  /** Current state of [file download analysis](https://chromeenterprise.google/policies/#OnFileDownloadedEnterpriseConnector). Set to true if provider list from Chrome is non-empty. */
		isFileDownloadAnalysisEnabled: Option[Boolean] = None,
	  /** Current state of [bulk data analysis](https://chromeenterprise.google/policies/#OnBulkDataEntryEnterpriseConnector). Set to true if provider list from Chrome is non-empty. */
		isBulkDataEntryAnalysisEnabled: Option[Boolean] = None,
	  /** Current state of [security event analysis](https://chromeenterprise.google/policies/#OnSecurityEventEnterpriseConnector). Set to true if provider list from Chrome is non-empty. */
		isSecurityEventAnalysisEnabled: Option[Boolean] = None,
	  /** Current state of [real-time URL check](https://chromeenterprise.google/policies/#EnterpriseRealTimeUrlCheckMode). Set to true if provider list from Chrome is non-empty. */
		isRealtimeUrlCheckEnabled: Option[Boolean] = None,
	  /** Current state of [Safe Browsing protection level](https://chromeenterprise.google/policies/#SafeBrowsingProtectionLevel). */
		safeBrowsingProtectionLevel: Option[Schema.GoogleAppsCloudidentityDevicesV1BrowserInfo.SafeBrowsingProtectionLevelEnum] = None,
	  /** Current state of [site isolation](https://chromeenterprise.google/policies/?policy=IsolateOrigins). */
		isSiteIsolationEnabled: Option[Boolean] = None,
	  /** Current state of [built-in DNS client](https://chromeenterprise.google/policies/#BuiltInDnsClientEnabled). */
		isBuiltInDnsClientEnabled: Option[Boolean] = None,
	  /** Current state of [password protection trigger](https://chromeenterprise.google/policies/#PasswordProtectionWarningTrigger). */
		passwordProtectionWarningTrigger: Option[Schema.GoogleAppsCloudidentityDevicesV1BrowserInfo.PasswordProtectionWarningTriggerEnum] = None,
	  /** Current state of [Chrome Remote Desktop app](https://chromeenterprise.google/policies/#URLBlocklist). */
		isChromeRemoteDesktopAppBlocked: Option[Boolean] = None,
	  /** Current state of [Chrome Cleanup](https://chromeenterprise.google/policies/#ChromeCleanupEnabled). */
		isChromeCleanupEnabled: Option[Boolean] = None,
	  /** Current state of [third-party blocking](https://chromeenterprise.google/policies/#ThirdPartyBlockingEnabled). */
		isThirdPartyBlockingEnabled: Option[Boolean] = None
	)
	
	case class Operation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1ListDevicesResponse(
	  /** Devices meeting the list restrictions. */
		devices: Option[List[Schema.GoogleAppsCloudidentityDevicesV1Device]] = None,
	  /** Token to retrieve the next page of results. Empty if there are no more results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1WipeDeviceRequest(
	  /** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
		customer: Option[String] = None,
	  /** Optional. Specifies if a user is able to factory reset a device after a Device Wipe. On iOS, this is called "Activation Lock", while on Android, this is known as "Factory Reset Protection". If true, this protection will be removed from the device, so that a user can successfully factory reset. If false, the setting is untouched on the device. */
		removeResetLock: Option[Boolean] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1CancelWipeDeviceRequest(
	  /** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
		customer: Option[String] = None
	)
	
	object GoogleAppsCloudidentityDevicesV1DeviceUser {
		enum ManagementStateEnum extends Enum[ManagementStateEnum] { case MANAGEMENT_STATE_UNSPECIFIED, WIPING, WIPED, APPROVED, BLOCKED, PENDING_APPROVAL, UNENROLLED }
		enum CompromisedStateEnum extends Enum[CompromisedStateEnum] { case COMPROMISED_STATE_UNSPECIFIED, COMPROMISED, NOT_COMPROMISED }
		enum PasswordStateEnum extends Enum[PasswordStateEnum] { case PASSWORD_STATE_UNSPECIFIED, PASSWORD_SET, PASSWORD_NOT_SET }
	}
	case class GoogleAppsCloudidentityDevicesV1DeviceUser(
	  /** Output only. [Resource name](https://cloud.google.com/apis/design/resource_names) of the DeviceUser in format: `devices/{device}/deviceUsers/{device_user}`, where `device_user` uniquely identifies a user's use of a device. */
		name: Option[String] = None,
	  /** Email address of the user registered on the device. */
		userEmail: Option[String] = None,
	  /** Output only. Management state of the user on the device. */
		managementState: Option[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser.ManagementStateEnum] = None,
	  /** Output only. Most recent time when user registered with this service. */
		firstSyncTime: Option[String] = None,
	  /** Output only. Last time when user synced with policies. */
		lastSyncTime: Option[String] = None,
	  /** Output only. User agent on the device for this specific user */
		userAgent: Option[String] = None,
	  /** Output only. Default locale used on device, in IETF BCP-47 format. */
		languageCode: Option[String] = None,
	  /** Compromised State of the DeviceUser object */
		compromisedState: Option[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser.CompromisedStateEnum] = None,
	  /** Password state of the DeviceUser object */
		passwordState: Option[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser.PasswordStateEnum] = None,
	  /** When the user first signed in to the device */
		createTime: Option[String] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1ListDeviceUsersResponse(
	  /** Devices meeting the list restrictions. */
		deviceUsers: Option[List[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser]] = None,
	  /** Token to retrieve the next page of results. Empty if there are no more results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1LookupSelfDeviceUsersResponse(
	  /** [Resource names](https://cloud.google.com/apis/design/resource_names) of the DeviceUsers in the format: `devices/{device}/deviceUsers/{user_resource}`, where device is the unique ID assigned to a Device and user_resource is the unique user ID */
		names: Option[List[String]] = None,
	  /** The customer resource name that may be passed back to other Devices API methods such as List, Get, etc. */
		customer: Option[String] = None,
	  /** Token to retrieve the next page of results. Empty if there are no more results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1ApproveDeviceUserRequest(
	  /** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
		customer: Option[String] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1BlockDeviceUserRequest(
	  /** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
		customer: Option[String] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1WipeDeviceUserRequest(
	  /** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
		customer: Option[String] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1CancelWipeDeviceUserRequest(
	  /** Optional. [Resource name](https://cloud.google.com/apis/design/resource_names) of the customer. If you're using this API for your own organization, use `customers/my_customer` If you're using this API to manage another organization, use `customers/{customer}`, where customer is the customer to whom the device belongs. */
		customer: Option[String] = None
	)
	
	object GoogleAppsCloudidentityDevicesV1ClientState {
		enum HealthScoreEnum extends Enum[HealthScoreEnum] { case HEALTH_SCORE_UNSPECIFIED, VERY_POOR, POOR, NEUTRAL, GOOD, VERY_GOOD }
		enum ManagedEnum extends Enum[ManagedEnum] { case MANAGED_STATE_UNSPECIFIED, MANAGED, UNMANAGED }
		enum ComplianceStateEnum extends Enum[ComplianceStateEnum] { case COMPLIANCE_STATE_UNSPECIFIED, COMPLIANT, NON_COMPLIANT }
		enum OwnerTypeEnum extends Enum[OwnerTypeEnum] { case OWNER_TYPE_UNSPECIFIED, OWNER_TYPE_CUSTOMER, OWNER_TYPE_PARTNER }
	}
	case class GoogleAppsCloudidentityDevicesV1ClientState(
	  /** Output only. [Resource name](https://cloud.google.com/apis/design/resource_names) of the ClientState in format: `devices/{device}/deviceUsers/{device_user}/clientState/{partner}`, where partner corresponds to the partner storing the data. For partners belonging to the "BeyondCorp Alliance", this is the partner ID specified to you by Google. For all other callers, this is a string of the form: `{customer}-suffix`, where `customer` is your customer ID. The &#42;suffix&#42; is any string the caller specifies. This string will be displayed verbatim in the administration console. This suffix is used in setting up Custom Access Levels in Context-Aware Access. Your organization's customer ID can be obtained from the URL: `GET https://www.googleapis.com/admin/directory/v1/customers/my_customer` The `id` field in the response contains the customer ID starting with the letter 'C'. The customer ID to be used in this API is the string after the letter 'C' (not including 'C') */
		name: Option[String] = None,
	  /** Output only. The time the client state data was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the client state data was last updated. */
		lastUpdateTime: Option[String] = None,
	  /** The token that needs to be passed back for concurrency control in updates. Token needs to be passed back in UpdateRequest */
		etag: Option[String] = None,
	  /** This field may be used to store a unique identifier for the API resource within which these CustomAttributes are a field. */
		customId: Option[String] = None,
	  /** The caller can specify asset tags for this resource */
		assetTags: Option[List[String]] = None,
	  /** The Health score of the resource. The Health score is the callers specification of the condition of the device from a usability point of view. For example, a third-party device management provider may specify a health score based on its compliance with organizational policies. */
		healthScore: Option[Schema.GoogleAppsCloudidentityDevicesV1ClientState.HealthScoreEnum] = None,
	  /** A descriptive cause of the health score. */
		scoreReason: Option[String] = None,
	  /** The management state of the resource as specified by the API client. */
		managed: Option[Schema.GoogleAppsCloudidentityDevicesV1ClientState.ManagedEnum] = None,
	  /** The compliance state of the resource as specified by the API client. */
		complianceState: Option[Schema.GoogleAppsCloudidentityDevicesV1ClientState.ComplianceStateEnum] = None,
	  /** The map of key-value attributes stored by callers specific to a device. The total serialized length of this map may not exceed 10KB. No limit is placed on the number of attributes in a map. */
		keyValuePairs: Option[Map[String, Schema.GoogleAppsCloudidentityDevicesV1CustomAttributeValue]] = None,
	  /** Output only. The owner of the ClientState */
		ownerType: Option[Schema.GoogleAppsCloudidentityDevicesV1ClientState.OwnerTypeEnum] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1CustomAttributeValue(
	  /** Represents a double value. */
		numberValue: Option[BigDecimal] = None,
	  /** Represents a string value. */
		stringValue: Option[String] = None,
	  /** Represents a boolean value. */
		boolValue: Option[Boolean] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1ListClientStatesResponse(
	  /** Client states meeting the list restrictions. */
		clientStates: Option[List[Schema.GoogleAppsCloudidentityDevicesV1ClientState]] = None,
	  /** Token to retrieve the next page of results. Empty if there are no more results. */
		nextPageToken: Option[String] = None
	)
	
	case class Group(
	  /** Output only. The [resource name](https://cloud.google.com/apis/design/resource_names) of the `Group`. Shall be of the form `groups/{group}`. */
		name: Option[String] = None,
	  /** Required. The `EntityKey` of the `Group`. */
		groupKey: Option[Schema.EntityKey] = None,
	  /** Output only. Additional group keys associated with the Group. */
		additionalGroupKeys: Option[List[Schema.EntityKey]] = None,
	  /** Required. Immutable. The resource name of the entity under which this `Group` resides in the Cloud Identity resource hierarchy. Must be of the form `identitysources/{identity_source}` for external [identity-mapped groups](https://support.google.com/a/answer/9039510) or `customers/{customer_id}` for Google Groups. The `customer_id` must begin with "C" (for example, 'C046psxkn'). [Find your customer ID.] (https://support.google.com/cloudidentity/answer/10070793) */
		parent: Option[String] = None,
	  /** The display name of the `Group`. */
		displayName: Option[String] = None,
	  /** An extended description to help users determine the purpose of a `Group`. Must not be longer than 4,096 characters. */
		description: Option[String] = None,
	  /** Output only. The time when the `Group` was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the `Group` was last updated. */
		updateTime: Option[String] = None,
	  /** Required. One or more label entries that apply to the Group. Currently supported labels contain a key with an empty value. Google Groups are the default type of group and have a label with a key of `cloudidentity.googleapis.com/groups.discussion_forum` and an empty value. Existing Google Groups can have an additional label with a key of `cloudidentity.googleapis.com/groups.security` and an empty value added to them. &#42;&#42;This is an immutable change and the security label cannot be removed once added.&#42;&#42; Dynamic groups have a label with a key of `cloudidentity.googleapis.com/groups.dynamic`. Identity-mapped groups for Cloud Search have a label with a key of `system/groups/external` and an empty value. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Dynamic group metadata like queries and status. */
		dynamicGroupMetadata: Option[Schema.DynamicGroupMetadata] = None
	)
	
	case class EntityKey(
	  /** The ID of the entity. For Google-managed entities, the `id` should be the email address of an existing group or user. Email addresses need to adhere to [name guidelines for users and groups](https://support.google.com/a/answer/9193374). For external-identity-mapped entities, the `id` must be a string conforming to the Identity Source's requirements. Must be unique within a `namespace`. */
		id: Option[String] = None,
	  /** The namespace in which the entity exists. If not specified, the `EntityKey` represents a Google-managed entity such as a Google user or a Google Group. If specified, the `EntityKey` represents an external-identity-mapped group. The namespace must correspond to an identity source created in Admin Console and must be in the form of `identitysources/{identity_source}`. */
		namespace: Option[String] = None
	)
	
	case class DynamicGroupMetadata(
	  /** Memberships will be the union of all queries. Only one entry with USER resource is currently supported. Customers can create up to 500 dynamic groups. */
		queries: Option[List[Schema.DynamicGroupQuery]] = None,
	  /** Output only. Status of the dynamic group. */
		status: Option[Schema.DynamicGroupStatus] = None
	)
	
	object DynamicGroupQuery {
		enum ResourceTypeEnum extends Enum[ResourceTypeEnum] { case RESOURCE_TYPE_UNSPECIFIED, USER }
	}
	case class DynamicGroupQuery(
	  /** Resource type for the Dynamic Group Query */
		resourceType: Option[Schema.DynamicGroupQuery.ResourceTypeEnum] = None,
	  /** Query that determines the memberships of the dynamic group. Examples: All users with at least one `organizations.department` of engineering. `user.organizations.exists(org, org.department=='engineering')` All users with at least one location that has `area` of `foo` and `building_id` of `bar`. `user.locations.exists(loc, loc.area=='foo' && loc.building_id=='bar')` All users with any variation of the name John Doe (case-insensitive queries add `equalsIgnoreCase()` to the value being queried). `user.name.value.equalsIgnoreCase('jOhn DoE')` */
		query: Option[String] = None
	)
	
	object DynamicGroupStatus {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNSPECIFIED, UP_TO_DATE, UPDATING_MEMBERSHIPS, INVALID_QUERY }
	}
	case class DynamicGroupStatus(
	  /** Status of the dynamic group. */
		status: Option[Schema.DynamicGroupStatus.StatusEnum] = None,
	  /** The latest time at which the dynamic group is guaranteed to be in the given status. If status is `UP_TO_DATE`, the latest time at which the dynamic group was confirmed to be up-to-date. If status is `UPDATING_MEMBERSHIPS`, the time at which dynamic group was created. */
		statusTime: Option[String] = None
	)
	
	case class SecuritySettings(
	  /** Output only. The resource name of the security settings. Shall be of the form `groups/{group_id}/securitySettings`. */
		name: Option[String] = None,
	  /** The Member Restriction value */
		memberRestriction: Option[Schema.MemberRestriction] = None
	)
	
	case class MemberRestriction(
	  /** Member Restriction as defined by CEL expression. Supported restrictions are: `member.customer_id` and `member.type`. Valid values for `member.type` are `1`, `2` and `3`. They correspond to USER, SERVICE_ACCOUNT, and GROUP respectively. The value for `member.customer_id` only supports `groupCustomerId()` currently which means the customer id of the group will be used for restriction. Supported operators are `&&`, `||` and `==`, corresponding to AND, OR, and EQUAL. Examples: Allow only service accounts of given customer to be members. `member.type == 2 && member.customer_id == groupCustomerId()` Allow only users or groups to be members. `member.type == 1 || member.type == 3` */
		query: Option[String] = None,
	  /** The evaluated state of this restriction on a group. */
		evaluation: Option[Schema.RestrictionEvaluation] = None
	)
	
	object RestrictionEvaluation {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, EVALUATING, COMPLIANT, FORWARD_COMPLIANT, NON_COMPLIANT }
	}
	case class RestrictionEvaluation(
	  /** Output only. The current state of the restriction */
		state: Option[Schema.RestrictionEvaluation.StateEnum] = None
	)
	
	case class LookupGroupNameResponse(
	  /** The [resource name](https://cloud.google.com/apis/design/resource_names) of the looked-up `Group`. */
		name: Option[String] = None
	)
	
	case class SearchGroupsResponse(
	  /** The `Group` resources that match the search query. */
		groups: Option[List[Schema.Group]] = None,
	  /** A continuation token to retrieve the next page of results, or empty if there are no more results available. */
		nextPageToken: Option[String] = None
	)
	
	case class ListGroupsResponse(
	  /** Groups returned in response to list request. The results are not sorted. */
		groups: Option[List[Schema.Group]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results available for listing. */
		nextPageToken: Option[String] = None
	)
	
	object Membership {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, USER, SERVICE_ACCOUNT, GROUP, SHARED_DRIVE, CBCM_BROWSER, OTHER }
		enum DeliverySettingEnum extends Enum[DeliverySettingEnum] { case DELIVERY_SETTING_UNSPECIFIED, ALL_MAIL, DIGEST, DAILY, NONE, DISABLED }
	}
	case class Membership(
	  /** Output only. The [resource name](https://cloud.google.com/apis/design/resource_names) of the `Membership`. Shall be of the form `groups/{group}/memberships/{membership}`. */
		name: Option[String] = None,
	  /** Required. Immutable. The `EntityKey` of the member. */
		preferredMemberKey: Option[Schema.EntityKey] = None,
	  /** Output only. The time when the `Membership` was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the `Membership` was last updated. */
		updateTime: Option[String] = None,
	  /** The `MembershipRole`s that apply to the `Membership`. If unspecified, defaults to a single `MembershipRole` with `name` `MEMBER`. Must not contain duplicate `MembershipRole`s with the same `name`. */
		roles: Option[List[Schema.MembershipRole]] = None,
	  /** Output only. The type of the membership. */
		`type`: Option[Schema.Membership.TypeEnum] = None,
	  /** Output only. Delivery setting associated with the membership. */
		deliverySetting: Option[Schema.Membership.DeliverySettingEnum] = None
	)
	
	case class MembershipRole(
	  /** The name of the `MembershipRole`. Must be one of `OWNER`, `MANAGER`, `MEMBER`. */
		name: Option[String] = None,
	  /** The expiry details of the `MembershipRole`. Expiry details are only supported for `MEMBER` `MembershipRoles`. May be set if `name` is `MEMBER`. Must not be set if `name` is any other value. */
		expiryDetail: Option[Schema.ExpiryDetail] = None,
	  /** Evaluations of restrictions applied to parent group on this membership. */
		restrictionEvaluations: Option[Schema.RestrictionEvaluations] = None
	)
	
	case class ExpiryDetail(
	  /** The time at which the `MembershipRole` will expire. */
		expireTime: Option[String] = None
	)
	
	case class RestrictionEvaluations(
	  /** Evaluation of the member restriction applied to this membership. Empty if the user lacks permission to view the restriction evaluation. */
		memberRestrictionEvaluation: Option[Schema.MembershipRoleRestrictionEvaluation] = None
	)
	
	object MembershipRoleRestrictionEvaluation {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, COMPLIANT, FORWARD_COMPLIANT, NON_COMPLIANT, EVALUATING }
	}
	case class MembershipRoleRestrictionEvaluation(
	  /** Output only. The current state of the restriction */
		state: Option[Schema.MembershipRoleRestrictionEvaluation.StateEnum] = None
	)
	
	case class LookupMembershipNameResponse(
	  /** The [resource name](https://cloud.google.com/apis/design/resource_names) of the looked-up `Membership`. Must be of the form `groups/{group}/memberships/{membership}`. */
		name: Option[String] = None
	)
	
	case class ListMembershipsResponse(
	  /** The `Membership`s under the specified `parent`. */
		memberships: Option[List[Schema.Membership]] = None,
	  /** A continuation token to retrieve the next page of results, or empty if there are no more results available. */
		nextPageToken: Option[String] = None
	)
	
	case class ModifyMembershipRolesRequest(
	  /** The `MembershipRole`s to be added. Adding or removing roles in the same request as updating roles is not supported. Must not be set if `update_roles_params` is set. */
		addRoles: Option[List[Schema.MembershipRole]] = None,
	  /** The `name`s of the `MembershipRole`s to be removed. Adding or removing roles in the same request as updating roles is not supported. It is not possible to remove the `MEMBER` `MembershipRole`. If you wish to delete a `Membership`, call MembershipsService.DeleteMembership instead. Must not contain `MEMBER`. Must not be set if `update_roles_params` is set. */
		removeRoles: Option[List[String]] = None,
	  /** The `MembershipRole`s to be updated. Updating roles in the same request as adding or removing roles is not supported. Must not be set if either `add_roles` or `remove_roles` is set. */
		updateRolesParams: Option[List[Schema.UpdateMembershipRolesParams]] = None
	)
	
	case class UpdateMembershipRolesParams(
	  /** The fully-qualified names of fields to update. May only contain the field `expiry_detail.expire_time`. */
		fieldMask: Option[String] = None,
	  /** The `MembershipRole`s to be updated. Only `MEMBER` `MembershipRole` can currently be updated. */
		membershipRole: Option[Schema.MembershipRole] = None
	)
	
	case class ModifyMembershipRolesResponse(
	  /** The `Membership` resource after modifying its `MembershipRole`s. */
		membership: Option[Schema.Membership] = None
	)
	
	case class SearchTransitiveMembershipsResponse(
	  /** List of transitive members satisfying the query. */
		memberships: Option[List[Schema.MemberRelation]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results. */
		nextPageToken: Option[String] = None
	)
	
	object MemberRelation {
		enum RelationTypeEnum extends Enum[RelationTypeEnum] { case RELATION_TYPE_UNSPECIFIED, DIRECT, INDIRECT, DIRECT_AND_INDIRECT }
	}
	case class MemberRelation(
	  /** Entity key has an id and a namespace. In case of discussion forums, the id will be an email address without a namespace. */
		preferredMemberKey: Option[List[Schema.EntityKey]] = None,
	  /** Resource name for this member. */
		member: Option[String] = None,
	  /** The membership role details (i.e name of role and expiry time). */
		roles: Option[List[Schema.TransitiveMembershipRole]] = None,
	  /** The relation between the group and the transitive member. */
		relationType: Option[Schema.MemberRelation.RelationTypeEnum] = None
	)
	
	case class TransitiveMembershipRole(
	  /** TransitiveMembershipRole in string format. Currently supported TransitiveMembershipRoles: `"MEMBER"`, `"OWNER"`, and `"MANAGER"`. */
		role: Option[String] = None
	)
	
	case class SearchTransitiveGroupsResponse(
	  /** List of transitive groups satisfying the query. */
		memberships: Option[List[Schema.GroupRelation]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results available for listing. */
		nextPageToken: Option[String] = None
	)
	
	object GroupRelation {
		enum RelationTypeEnum extends Enum[RelationTypeEnum] { case RELATION_TYPE_UNSPECIFIED, DIRECT, INDIRECT, DIRECT_AND_INDIRECT }
	}
	case class GroupRelation(
	  /** Entity key has an id and a namespace. In case of discussion forums, the id will be an email address without a namespace. */
		groupKey: Option[Schema.EntityKey] = None,
	  /** Resource name for this group. */
		group: Option[String] = None,
	  /** Display name for this group. */
		displayName: Option[String] = None,
	  /** Membership roles of the member for the group. */
		roles: Option[List[Schema.TransitiveMembershipRole]] = None,
	  /** The relation between the member and the transitive group. */
		relationType: Option[Schema.GroupRelation.RelationTypeEnum] = None,
	  /** Labels for Group resource. */
		labels: Option[Map[String, String]] = None
	)
	
	case class CheckTransitiveMembershipResponse(
	  /** Response does not include the possible roles of a member since the behavior of this rpc is not all-or-nothing unlike the other rpcs. So, it may not be possible to list all the roles definitively, due to possible lack of authorization in some of the paths. */
		hasMembership: Option[Boolean] = None
	)
	
	case class SearchDirectGroupsResponse(
	  /** List of direct groups satisfying the query. */
		memberships: Option[List[Schema.MembershipRelation]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results available for listing. */
		nextPageToken: Option[String] = None
	)
	
	case class MembershipRelation(
	  /** The [resource name](https://cloud.google.com/apis/design/resource_names) of the `Membership`. Shall be of the form `groups/{group_id}/memberships/{membership_id}`. */
		membership: Option[String] = None,
	  /** The `MembershipRole`s that apply to the `Membership`. */
		roles: Option[List[Schema.MembershipRole]] = None,
	  /** The [resource name](https://cloud.google.com/apis/design/resource_names) of the `Group`. Shall be of the form `groups/{group_id}`. */
		group: Option[String] = None,
	  /** The `EntityKey` of the `Group`. */
		groupKey: Option[Schema.EntityKey] = None,
	  /** The display name of the `Group`. */
		displayName: Option[String] = None,
	  /** One or more label entries that apply to the Group. Currently supported labels contain a key with an empty value. */
		labels: Option[Map[String, String]] = None,
	  /** An extended description to help users determine the purpose of a `Group`. */
		description: Option[String] = None
	)
	
	case class InboundSamlSsoProfile(
	  /** Output only. [Resource name](https://cloud.google.com/apis/design/resource_names) of the SAML SSO profile. */
		name: Option[String] = None,
	  /** Immutable. The customer. For example: `customers/C0123abc`. */
		customer: Option[String] = None,
	  /** Human-readable name of the SAML SSO profile. */
		displayName: Option[String] = None,
	  /** SAML identity provider configuration. */
		idpConfig: Option[Schema.SamlIdpConfig] = None,
	  /** SAML service provider configuration for this SAML SSO profile. These are the service provider details provided by Google that should be configured on the corresponding identity provider. */
		spConfig: Option[Schema.SamlSpConfig] = None
	)
	
	case class SamlIdpConfig(
	  /** Required. The SAML &#42;&#42;Entity ID&#42;&#42; of the identity provider. */
		entityId: Option[String] = None,
	  /** Required. The `SingleSignOnService` endpoint location (sign-in page URL) of the identity provider. This is the URL where the `AuthnRequest` will be sent. Must use `HTTPS`. Assumed to accept the `HTTP-Redirect` binding. */
		singleSignOnServiceUri: Option[String] = None,
	  /** The &#42;&#42;Logout Redirect URL&#42;&#42; (sign-out page URL) of the identity provider. When a user clicks the sign-out link on a Google page, they will be redirected to this URL. This is a pure redirect with no attached SAML `LogoutRequest` i.e. SAML single logout is not supported. Must use `HTTPS`. */
		logoutRedirectUri: Option[String] = None,
	  /** The &#42;&#42;Change Password URL&#42;&#42; of the identity provider. Users will be sent to this URL when changing their passwords at `myaccount.google.com`. This takes precedence over the change password URL configured at customer-level. Must use `HTTPS`. */
		changePasswordUri: Option[String] = None
	)
	
	case class SamlSpConfig(
	  /** Output only. The SAML &#42;&#42;Entity ID&#42;&#42; for this service provider. */
		entityId: Option[String] = None,
	  /** Output only. The SAML &#42;&#42;Assertion Consumer Service (ACS) URL&#42;&#42; to be used for the IDP-initiated login. Assumed to accept response messages via the `HTTP-POST` binding. */
		assertionConsumerServiceUri: Option[String] = None
	)
	
	case class ListInboundSamlSsoProfilesResponse(
	  /** List of InboundSamlSsoProfiles. */
		inboundSamlSsoProfiles: Option[List[Schema.InboundSamlSsoProfile]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class IdpCredential(
	  /** Output only. Information of a RSA public key. */
		rsaKeyInfo: Option[Schema.RsaPublicKeyInfo] = None,
	  /** Output only. Information of a DSA public key. */
		dsaKeyInfo: Option[Schema.DsaPublicKeyInfo] = None,
	  /** Output only. [Resource name](https://cloud.google.com/apis/design/resource_names) of the credential. */
		name: Option[String] = None,
	  /** Output only. Time when the `IdpCredential` was last updated. */
		updateTime: Option[String] = None
	)
	
	case class RsaPublicKeyInfo(
	  /** Key size in bits (size of the modulus). */
		keySize: Option[Int] = None
	)
	
	case class DsaPublicKeyInfo(
	  /** Key size in bits (size of parameter P). */
		keySize: Option[Int] = None
	)
	
	case class ListIdpCredentialsResponse(
	  /** The IdpCredentials from the specified InboundSamlSsoProfile. */
		idpCredentials: Option[List[Schema.IdpCredential]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class AddIdpCredentialRequest(
	  /** PEM encoded x509 certificate containing the public key for verifying IdP signatures. */
		pemData: Option[String] = None
	)
	
	object InboundSsoAssignment {
		enum SsoModeEnum extends Enum[SsoModeEnum] { case SSO_MODE_UNSPECIFIED, SSO_OFF, SAML_SSO, DOMAIN_WIDE_SAML_IF_ENABLED }
	}
	case class InboundSsoAssignment(
	  /** Immutable. Must be of the form `groups/{group}`. */
		targetGroup: Option[String] = None,
	  /** Immutable. Must be of the form `orgUnits/{org_unit}`. */
		targetOrgUnit: Option[String] = None,
	  /** Output only. [Resource name](https://cloud.google.com/apis/design/resource_names) of the Inbound SSO Assignment. */
		name: Option[String] = None,
	  /** Immutable. The customer. For example: `customers/C0123abc`. */
		customer: Option[String] = None,
	  /** Must be zero (which is the default value so it can be omitted) for assignments with `target_org_unit` set and must be greater-than-or-equal-to one for assignments with `target_group` set. */
		rank: Option[Int] = None,
	  /** Inbound SSO behavior. */
		ssoMode: Option[Schema.InboundSsoAssignment.SsoModeEnum] = None,
	  /** SAML SSO details. Must be set if and only if `sso_mode` is set to `SAML_SSO`. */
		samlSsoInfo: Option[Schema.SamlSsoInfo] = None,
	  /** Assertions about users assigned to an IdP will always be accepted from that IdP. This controls whether/when Google should redirect a user to the IdP. Unset (defaults) is the recommended configuration. */
		signInBehavior: Option[Schema.SignInBehavior] = None
	)
	
	case class SamlSsoInfo(
	  /** Required. Name of the `InboundSamlSsoProfile` to use. Must be of the form `inboundSamlSsoProfiles/{inbound_saml_sso_profile}`.  */
		inboundSamlSsoProfile: Option[String] = None
	)
	
	object SignInBehavior {
		enum RedirectConditionEnum extends Enum[RedirectConditionEnum] { case REDIRECT_CONDITION_UNSPECIFIED, NEVER }
	}
	case class SignInBehavior(
	  /** When to redirect sign-ins to the IdP. */
		redirectCondition: Option[Schema.SignInBehavior.RedirectConditionEnum] = None
	)
	
	case class ListInboundSsoAssignmentsResponse(
	  /** The assignments. */
		inboundSsoAssignments: Option[List[Schema.InboundSsoAssignment]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object UserInvitation {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, NOT_YET_SENT, INVITED, ACCEPTED, DECLINED }
	}
	case class UserInvitation(
	  /** Shall be of the form `customers/{customer}/userinvitations/{user_email_address}`. */
		name: Option[String] = None,
	  /** State of the `UserInvitation`. */
		state: Option[Schema.UserInvitation.StateEnum] = None,
	  /** Number of invitation emails sent to the user. */
		mailsSentCount: Option[String] = None,
	  /** Time when the `UserInvitation` was last updated. */
		updateTime: Option[String] = None
	)
	
	case class ListUserInvitationsResponse(
	  /** The list of UserInvitation resources. */
		userInvitations: Option[List[Schema.UserInvitation]] = None,
	  /** The token for the next page. If not empty, indicates that there may be more `UserInvitation` resources that match the listing request; this value can be used in a subsequent ListUserInvitationsRequest to get continued results with the current list call. */
		nextPageToken: Option[String] = None
	)
	
	case class SendUserInvitationRequest(
	
	)
	
	case class CancelUserInvitationRequest(
	
	)
	
	case class IsInvitableUserResponse(
	  /** Returns true if the email address is invitable. */
		isInvitableUser: Option[Boolean] = None
	)
	
	case class GetMembershipGraphResponse(
	  /** The membership graph's path information represented as an adjacency list. */
		adjacencyList: Option[List[Schema.MembershipAdjacencyList]] = None,
	  /** The resources representing each group in the adjacency list. Each group in this list can be correlated to a 'group' of the MembershipAdjacencyList using the 'name' of the Group resource. */
		groups: Option[List[Schema.Group]] = None
	)
	
	case class MembershipAdjacencyList(
	  /** Resource name of the group that the members belong to. */
		group: Option[String] = None,
	  /** Each edge contains information about the member that belongs to this group. Note: Fields returned here will help identify the specific Membership resource (e.g name, preferred_member_key and role), but may not be a comprehensive list of all fields. */
		edges: Option[List[Schema.Membership]] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1ApproveDeviceUserResponse(
	  /** Resultant DeviceUser object for the action. */
		deviceUser: Option[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1BlockDeviceUserResponse(
	  /** Resultant DeviceUser object for the action. */
		deviceUser: Option[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1WipeDeviceUserResponse(
	  /** Resultant DeviceUser object for the action. */
		deviceUser: Option[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1CancelWipeDeviceUserResponse(
	  /** Resultant DeviceUser object for the action. */
		deviceUser: Option[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1WipeDeviceResponse(
	  /** Resultant Device object for the action. Note that asset tags will not be returned in the device object. */
		device: Option[Schema.GoogleAppsCloudidentityDevicesV1Device] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1CancelWipeDeviceResponse(
	  /** Resultant Device object for the action. Note that asset tags will not be returned in the device object. */
		device: Option[Schema.GoogleAppsCloudidentityDevicesV1Device] = None
	)
	
	case class GoogleAppsCloudidentityDevicesV1CreateDeviceMetadata(
	
	)
	
	case class GoogleAppsCloudidentityDevicesV1DeleteDeviceMetadata(
	
	)
	
	case class GoogleAppsCloudidentityDevicesV1UpdateDeviceMetadata(
	
	)
	
	case class GoogleAppsCloudidentityDevicesV1WipeDeviceMetadata(
	
	)
	
	case class GoogleAppsCloudidentityDevicesV1CancelWipeDeviceMetadata(
	
	)
	
	case class GoogleAppsCloudidentityDevicesV1DeleteDeviceUserMetadata(
	
	)
	
	case class GoogleAppsCloudidentityDevicesV1ApproveDeviceUserMetadata(
	
	)
	
	case class GoogleAppsCloudidentityDevicesV1SignoutDeviceUserMetadata(
	
	)
	
	case class GoogleAppsCloudidentityDevicesV1BlockDeviceUserMetadata(
	
	)
	
	case class GoogleAppsCloudidentityDevicesV1WipeDeviceUserMetadata(
	
	)
	
	case class GoogleAppsCloudidentityDevicesV1CancelWipeDeviceUserMetadata(
	
	)
	
	case class GoogleAppsCloudidentityDevicesV1ListEndpointAppsMetadata(
	
	)
	
	case class GoogleAppsCloudidentityDevicesV1UpdateClientStateMetadata(
	
	)
	
	case class CreateGroupMetadata(
	
	)
	
	case class DeleteGroupMetadata(
	
	)
	
	case class UpdateGroupMetadata(
	
	)
	
	case class CreateMembershipMetadata(
	
	)
	
	case class DeleteMembershipMetadata(
	
	)
	
	case class UpdateMembershipMetadata(
	
	)
	
	case class GetMembershipGraphMetadata(
	
	)
	
	case class AddIdpCredentialOperationMetadata(
	  /** State of this Operation Will be "awaiting-multi-party-approval" when the operation is deferred due to the target customer having enabled [Multi-party approval for sensitive actions](https://support.google.com/a/answer/13790448). */
		state: Option[String] = None
	)
	
	case class CreateInboundSamlSsoProfileOperationMetadata(
	  /** State of this Operation Will be "awaiting-multi-party-approval" when the operation is deferred due to the target customer having enabled [Multi-party approval for sensitive actions](https://support.google.com/a/answer/13790448). */
		state: Option[String] = None
	)
	
	case class DeleteIdpCredentialOperationMetadata(
	
	)
	
	case class DeleteInboundSamlSsoProfileOperationMetadata(
	
	)
	
	case class UpdateInboundSamlSsoProfileOperationMetadata(
	  /** State of this Operation Will be "awaiting-multi-party-approval" when the operation is deferred due to the target customer having enabled [Multi-party approval for sensitive actions](https://support.google.com/a/answer/13790448). */
		state: Option[String] = None
	)
	
	case class CreateInboundSsoAssignmentOperationMetadata(
	
	)
	
	case class DeleteInboundSsoAssignmentOperationMetadata(
	
	)
	
	case class UpdateInboundSsoAssignmentOperationMetadata(
	
	)
}
