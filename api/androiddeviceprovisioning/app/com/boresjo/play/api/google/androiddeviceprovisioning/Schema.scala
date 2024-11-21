package com.boresjo.play.api.google.androiddeviceprovisioning

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class DeviceMetadata(
	  /** Metadata entries recorded as key-value pairs. */
		entries: Option[Map[String, String]] = None
	)
	
	object ClaimDeviceRequest {
		enum SectionTypeEnum extends Enum[SectionTypeEnum] { case SECTION_TYPE_UNSPECIFIED, SECTION_TYPE_SIM_LOCK, SECTION_TYPE_ZERO_TOUCH }
	}
	case class ClaimDeviceRequest(
	  /** The Google Workspace customer ID. */
		googleWorkspaceCustomerId: Option[String] = None,
	  /** The ID of the customer for whom the device is being claimed. */
		customerId: Option[String] = None,
	  /** Optional. Must and can only be set when DeviceProvisioningSectionType is SECTION_TYPE_SIM_LOCK. The unique identifier of the SimLock profile. */
		simlockProfileId: Option[String] = None,
	  /** Optional. The ID of the configuration applied to the device section. */
		configurationId: Option[String] = None,
	  /** Required. The section type of the device's provisioning record. */
		sectionType: Option[Schema.ClaimDeviceRequest.SectionTypeEnum] = None,
	  /** Optional. Must and can only be set for Chrome OS devices. */
		preProvisioningToken: Option[String] = None,
	  /** Required. Required. The device identifier of the device to claim. */
		deviceIdentifier: Option[Schema.DeviceIdentifier] = None,
	  /** Optional. The metadata to attach to the device. */
		deviceMetadata: Option[Schema.DeviceMetadata] = None
	)
	
	case class ListCustomersResponse(
	  /** A token to retrieve the next page of results. Omitted if no further results are available. */
		nextPageToken: Option[String] = None,
	  /** List of customers related to this reseller partner. */
		customers: Option[List[Schema.Company]] = None,
	  /** The total count of items in the list irrespective of pagination. */
		totalSize: Option[Int] = None
	)
	
	case class CustomerListConfigurationsResponse(
	  /** The configurations. */
		configurations: Option[List[Schema.Configuration]] = None
	)
	
	case class CustomerListDevicesResponse(
	  /** The customer's devices. */
		devices: Option[List[Schema.Device]] = None,
	  /** A token used to access the next page of results. Omitted if no further results are available. */
		nextPageToken: Option[String] = None
	)
	
	case class DevicesLongRunningOperationResponse(
	  /** A summary of how many items in the operation the server processed successfully. Updated as the operation progresses. */
		successCount: Option[Int] = None,
	  /** The processing status for each device in the operation. One `PerDeviceStatus` per device. The list order matches the items in the original request. */
		perDeviceStatus: Option[List[Schema.OperationPerDevice]] = None
	)
	
	case class GetDeviceSimLockStateRequest(
	  /** Required. Required. The device identifier to search for. */
		deviceIdentifier: Option[Schema.DeviceIdentifier] = None
	)
	
	object PartnerClaim {
		enum SectionTypeEnum extends Enum[SectionTypeEnum] { case SECTION_TYPE_UNSPECIFIED, SECTION_TYPE_SIM_LOCK, SECTION_TYPE_ZERO_TOUCH }
	}
	case class PartnerClaim(
	  /** Optional. Must and can only be set for Chrome OS devices. */
		preProvisioningToken: Option[String] = None,
	  /** Optional. The ID of the configuration applied to the device section. */
		configurationId: Option[String] = None,
	  /** Required. Required. Device identifier of the device. */
		deviceIdentifier: Option[Schema.DeviceIdentifier] = None,
	  /** Required. The section type of the device's provisioning record. */
		sectionType: Option[Schema.PartnerClaim.SectionTypeEnum] = None,
	  /** The Google Workspace customer ID. */
		googleWorkspaceCustomerId: Option[String] = None,
	  /** The ID of the customer for whom the device is being claimed. */
		customerId: Option[String] = None,
	  /** Required. The metadata to attach to the device at claim. */
		deviceMetadata: Option[Schema.DeviceMetadata] = None,
	  /** Optional. Must and can only be set when DeviceProvisioningSectionType is SECTION_TYPE_SIM_LOCK. The unique identifier of the SimLock profile. */
		simlockProfileId: Option[String] = None
	)
	
	case class Status(
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class DeviceReference(
	  /** The hardware IDs of the device. */
		deviceIdentifier: Option[Schema.DeviceIdentifier] = None,
	  /** The ID of the device. */
		deviceId: Option[String] = None
	)
	
	object GetDeviceSimLockStateResponse {
		enum SimLockStateEnum extends Enum[SimLockStateEnum] { case SIM_LOCK_STATE_UNSPECIFIED, UNLOCKED, LOCKED_TO_PARTNER, LOCKED_TO_OTHER_PARTNER }
	}
	case class GetDeviceSimLockStateResponse(
		simLockState: Option[Schema.GetDeviceSimLockStateResponse.SimLockStateEnum] = None
	)
	
	case class GoogleWorkspaceAccount(
	  /** Output only. The pre-provisioning tokens previously used to claim devices. */
		preProvisioningTokens: Option[List[String]] = None,
	  /** Required. The customer ID. */
		customerId: Option[String] = None
	)
	
	object PartnerUnclaim {
		enum SectionTypeEnum extends Enum[SectionTypeEnum] { case SECTION_TYPE_UNSPECIFIED, SECTION_TYPE_SIM_LOCK, SECTION_TYPE_ZERO_TOUCH }
	}
	case class PartnerUnclaim(
	  /** Required. The section type of the device's provisioning record. */
		sectionType: Option[Schema.PartnerUnclaim.SectionTypeEnum] = None,
	  /** Required. Device identifier of the device. */
		deviceIdentifier: Option[Schema.DeviceIdentifier] = None,
	  /** Optional. The expiration time of the vacation unlock. */
		vacationModeExpireTime: Option[String] = None,
	  /** Required. Device ID of the device. */
		deviceId: Option[String] = None,
	  /** Optional. The duration of the vacation unlock starting from when the request is processed. (1 day is treated as 24 hours) */
		vacationModeDays: Option[Int] = None
	)
	
	case class Device(
	  /** Output only. The ID of the device. Assigned by the server. */
		deviceId: Option[String] = None,
	  /** Output only. The provisioning claims for a device. Devices claimed for zero-touch enrollment have a claim with the type `SECTION_TYPE_ZERO_TOUCH`. Call `partners.devices.unclaim` or `partners.devices.unclaimAsync` to remove the device from zero-touch enrollment. */
		claims: Option[List[Schema.DeviceClaim]] = None,
	  /** The metadata attached to the device. Structured as key-value pairs. To learn more, read [Device metadata](https://developers.google.com/zero-touch/guides/metadata). */
		deviceMetadata: Option[Schema.DeviceMetadata] = None,
	  /** The hardware IDs that identify a manufactured device. To learn more, read [Identifiers](https://developers.google.com/zero-touch/guides/identifiers). */
		deviceIdentifier: Option[Schema.DeviceIdentifier] = None,
	  /** Not available to resellers. */
		configuration: Option[String] = None,
	  /** Output only. The API resource name in the format `partners/[PARTNER_ID]/devices/[DEVICE_ID]`. Assigned by the server. */
		name: Option[String] = None
	)
	
	case class ClaimDevicesRequest(
	  /** Required. A list of device claims. */
		claims: Option[List[Schema.PartnerClaim]] = None
	)
	
	case class Empty(
	
	)
	
	case class ListVendorCustomersResponse(
	  /** A token to retrieve the next page of results. Omitted if no further results are available. */
		nextPageToken: Option[String] = None,
	  /** The total count of items in the list irrespective of pagination. */
		totalSize: Option[Int] = None,
	  /** List of customers of the vendor. */
		customers: Option[List[Schema.Company]] = None
	)
	
	case class CustomerUnclaimDeviceRequest(
	  /** Required. The device to unclaim. There are custom validations in UnclaimDeviceRequestValidator. */
		device: Option[Schema.DeviceReference] = None
	)
	
	case class CustomerRemoveConfigurationRequest(
	  /** Required. The device to remove the configuration from. There are custom validations in RemoveConfigurationRequestValidator */
		device: Option[Schema.DeviceReference] = None
	)
	
	case class Dpc(
	  /** Output only. The API resource name in the format `customers/[CUSTOMER_ID]/dpcs/[DPC_ID]`. Assigned by the server. To maintain a reference to a DPC across customer accounts, persist and match the last path component (`DPC_ID`). */
		name: Option[String] = None,
	  /** Output only. The DPC's Android application ID that looks like a Java package name. Zero-touch enrollment installs the DPC app onto a device using this identifier. */
		packageName: Option[String] = None,
	  /** Output only. The title of the DPC app in Google Play. For example, _Google Apps Device Policy_. Useful in an application's user interface. */
		dpcName: Option[String] = None
	)
	
	case class ClaimDeviceResponse(
	  /** The device ID of the claimed device. */
		deviceId: Option[String] = None,
	  /** The resource name of the device in the format `partners/[PARTNER_ID]/devices/[DEVICE_ID]`. */
		deviceName: Option[String] = None
	)
	
	case class UpdateDeviceMetadataRequest(
	  /** Required. The metadata to attach to the device. */
		deviceMetadata: Option[Schema.DeviceMetadata] = None
	)
	
	case class ListVendorsResponse(
	  /** The total count of items in the list irrespective of pagination. */
		totalSize: Option[Int] = None,
	  /** List of vendors of the reseller partner. Fields `name`, `companyId` and `companyName` are populated to the Company object. */
		vendors: Option[List[Schema.Company]] = None,
	  /** A token to retrieve the next page of results. Omitted if no further results are available. */
		nextPageToken: Option[String] = None
	)
	
	case class UpdateMetadataArguments(
	  /** Required. Device identifier. */
		deviceIdentifier: Option[Schema.DeviceIdentifier] = None,
	  /** Required. Device ID of the device. */
		deviceId: Option[String] = None,
	  /** Required. The metadata to update. */
		deviceMetadata: Option[Schema.DeviceMetadata] = None
	)
	
	case class CustomerListCustomersResponse(
	  /** A token used to access the next page of results. Omitted if no further results are available. */
		nextPageToken: Option[String] = None,
	  /** The customer accounts the calling user is a member of. */
		customers: Option[List[Schema.Company]] = None
	)
	
	case class OperationPerDevice(
	  /** A copy of the original device-claim request received by the server. */
		claim: Option[Schema.PartnerClaim] = None,
	  /** The processing result for each device. */
		result: Option[Schema.PerDeviceStatusInBatch] = None,
	  /** A copy of the original device-unclaim request received by the server. */
		unclaim: Option[Schema.PartnerUnclaim] = None,
	  /** A copy of the original metadata-update request received by the server. */
		updateMetadata: Option[Schema.UpdateMetadataArguments] = None
	)
	
	object DeviceIdentifier {
		enum DeviceTypeEnum extends Enum[DeviceTypeEnum] { case DEVICE_TYPE_UNSPECIFIED, DEVICE_TYPE_ANDROID, DEVICE_TYPE_CHROME_OS }
	}
	case class DeviceIdentifier(
	  /** The manufacturer's serial number for the device. This value might not be unique across different device models. */
		serialNumber: Option[String] = None,
	  /** The device model's name. Allowed values are listed in [Android models](/zero-touch/resources/manufacturer-names#model-names) and [Chrome OS models](https://support.google.com/chrome/a/answer/10130175#identify_compatible). */
		model: Option[String] = None,
	  /** An identifier provided by OEMs, carried through the production and sales process. Only applicable to Chrome OS devices. */
		chromeOsAttestedDeviceId: Option[String] = None,
	  /** The type of the device */
		deviceType: Option[Schema.DeviceIdentifier.DeviceTypeEnum] = None,
	  /** The device manufacturer’s name. Matches the device's built-in value returned from `android.os.Build.MANUFACTURER`. Allowed values are listed in [Android manufacturers](/zero-touch/resources/manufacturer-names#manufacturers-names). */
		manufacturer: Option[String] = None,
	  /** The device’s second IMEI number. */
		imei2: Option[String] = None,
	  /** The device’s MEID number. */
		meid: Option[String] = None,
	  /** The device’s IMEI number. Validated on input. */
		imei: Option[String] = None,
	  /** The device’s second MEID number. */
		meid2: Option[String] = None
	)
	
	object DevicesLongRunningOperationMetadata {
		enum ProcessingStatusEnum extends Enum[ProcessingStatusEnum] { case BATCH_PROCESS_STATUS_UNSPECIFIED, BATCH_PROCESS_PENDING, BATCH_PROCESS_IN_PROGRESS, BATCH_PROCESS_PROCESSED }
	}
	case class DevicesLongRunningOperationMetadata(
	  /** The processing status of the operation. */
		processingStatus: Option[Schema.DevicesLongRunningOperationMetadata.ProcessingStatusEnum] = None,
	  /** The number of metadata updates in the operation. This might be different from the number of updates in the request if the API can't parse some of the updates. */
		devicesCount: Option[Int] = None,
	  /** The processing progress of the operation. Measured as a number from 0 to 100. A value of 10O doesn't always mean the operation completed—check for the inclusion of a `done` field. */
		progress: Option[Int] = None
	)
	
	case class UnclaimDevicesRequest(
	  /** Required. The list of devices to unclaim. */
		unclaims: Option[List[Schema.PartnerUnclaim]] = None
	)
	
	object UnclaimDeviceRequest {
		enum SectionTypeEnum extends Enum[SectionTypeEnum] { case SECTION_TYPE_UNSPECIFIED, SECTION_TYPE_SIM_LOCK, SECTION_TYPE_ZERO_TOUCH }
	}
	case class UnclaimDeviceRequest(
	  /** The expiration time of the vacation unlock. */
		vacationModeExpireTime: Option[String] = None,
	  /** Required. The section type of the device's provisioning record. */
		sectionType: Option[Schema.UnclaimDeviceRequest.SectionTypeEnum] = None,
	  /** Required. The device identifier you used when you claimed this device. */
		deviceIdentifier: Option[Schema.DeviceIdentifier] = None,
	  /** Required. The device ID returned by `ClaimDevice`. */
		deviceId: Option[String] = None,
	  /** The duration of the vacation unlock starting from when the request is processed. (1 day is treated as 24 hours) */
		vacationModeDays: Option[Int] = None
	)
	
	case class FindDevicesByOwnerResponse(
	  /** The total count of items in the list irrespective of pagination. */
		totalSize: Option[Int] = None,
	  /** The customer's devices. */
		devices: Option[List[Schema.Device]] = None,
	  /** A token used to access the next page of results. Omitted if no further results are available. */
		nextPageToken: Option[String] = None
	)
	
	case class UpdateDeviceMetadataInBatchRequest(
	  /** Required. The list of metadata updates. */
		updates: Option[List[Schema.UpdateMetadataArguments]] = None
	)
	
	object FindDevicesByOwnerRequest {
		enum SectionTypeEnum extends Enum[SectionTypeEnum] { case SECTION_TYPE_UNSPECIFIED, SECTION_TYPE_SIM_LOCK, SECTION_TYPE_ZERO_TOUCH }
	}
	case class FindDevicesByOwnerRequest(
	  /** A token specifying which result page to return. */
		pageToken: Option[String] = None,
	  /** Required. The section type of the device's provisioning record. */
		sectionType: Option[Schema.FindDevicesByOwnerRequest.SectionTypeEnum] = None,
	  /** Required. The maximum number of devices to show in a page of results. Must be between 1 and 100 inclusive. */
		limit: Option[String] = None,
	  /** The list of IDs of Google Workspace accounts to search for. */
		googleWorkspaceCustomerId: Option[List[String]] = None,
	  /** The list of customer IDs to search for. */
		customerId: Option[List[String]] = None
	)
	
	case class FindDevicesByDeviceIdentifierResponse(
	  /** The total count of items in the list irrespective of pagination. */
		totalSize: Option[Int] = None,
	  /** Found devices. */
		devices: Option[List[Schema.Device]] = None,
	  /** A token used to access the next page of results. Omitted if no further results are available. */
		nextPageToken: Option[String] = None
	)
	
	case class CustomerListDpcsResponse(
	  /** The list of DPCs available to the customer that support zero-touch enrollment. */
		dpcs: Option[List[Schema.Dpc]] = None
	)
	
	case class CreateCustomerRequest(
	  /** Required. The company data to populate the new customer. Must contain a value for `companyName` and at least one `owner_email` that's associated with a Google Account. The values for `companyId` and `name` must be empty. */
		customer: Option[Schema.Company] = None
	)
	
	case class CustomerApplyConfigurationRequest(
	  /** Required. The device the configuration is applied to. There are custom validations in ApplyConfigurationRequestValidator */
		device: Option[Schema.DeviceReference] = None,
	  /** Required. The configuration applied to the device in the format `customers/[CUSTOMER_ID]/configurations/[CONFIGURATION_ID]`. */
		configuration: Option[String] = None
	)
	
	object Company {
		enum TermsStatusEnum extends Enum[TermsStatusEnum] { case TERMS_STATUS_UNSPECIFIED, TERMS_STATUS_NOT_ACCEPTED, TERMS_STATUS_ACCEPTED, TERMS_STATUS_STALE }
	}
	case class Company(
	  /** Required. The name of the company. For example _XYZ Corp_. Displayed to the company's employees in the zero-touch enrollment portal. */
		companyName: Option[String] = None,
	  /** Input only. The preferred locale of the customer represented as a BCP47 language code. This field is validated on input and requests containing unsupported language codes will be rejected. Supported language codes: Arabic (ar) Chinese (Hong Kong) (zh-HK) Chinese (Simplified) (zh-CN) Chinese (Traditional) (zh-TW) Czech (cs) Danish (da) Dutch (nl) English (UK) (en-GB) English (US) (en-US) Filipino (fil) Finnish (fi) French (fr) German (de) Hebrew (iw) Hindi (hi) Hungarian (hu) Indonesian (id) Italian (it) Japanese (ja) Korean (ko) Norwegian (Bokmal) (no) Polish (pl) Portuguese (Brazil) (pt-BR) Portuguese (Portugal) (pt-PT) Russian (ru) Spanish (es) Spanish (Latin America) (es-419) Swedish (sv) Thai (th) Turkish (tr) Ukrainian (uk) Vietnamese (vi) */
		languageCode: Option[String] = None,
	  /** Required. Input only. Email address of customer's users in the owner role. At least one `owner_email` is required. Owners share the same access as admins but can also add, delete, and edit your organization's portal users. */
		ownerEmails: Option[List[String]] = None,
	  /** Input only. If set to true, welcome email will not be sent to the customer. It is recommended to skip the welcome email if devices will be claimed with additional DEVICE_PROTECTION service, as the customer will receive separate emails at device claim time. This field is ignored if this is not a Zero-touch customer. */
		skipWelcomeEmail: Option[Boolean] = None,
	  /** Output only. The ID of the company. Assigned by the server. */
		companyId: Option[String] = None,
	  /** Output only. The Google Workspace account associated with this customer. Only used for customer Companies. */
		googleWorkspaceAccount: Option[Schema.GoogleWorkspaceAccount] = None,
	  /** Optional. Email address of customer's users in the admin role. Each email address must be associated with a Google Account. */
		adminEmails: Option[List[String]] = None,
	  /** Output only. Whether any user from the company has accepted the latest Terms of Service (ToS). See TermsStatus. */
		termsStatus: Option[Schema.Company.TermsStatusEnum] = None,
	  /** Output only. The API resource name of the company. The resource name is one of the following formats: &#42; `partners/[PARTNER_ID]/customers/[CUSTOMER_ID]` &#42; `partners/[PARTNER_ID]/vendors/[VENDOR_ID]` &#42; `partners/[PARTNER_ID]/vendors/[VENDOR_ID]/customers/[CUSTOMER_ID]` Assigned by the server. */
		name: Option[String] = None
	)
	
	case class Operation(
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** This field will contain a `DevicesLongRunningOperationResponse` object if the operation is created by `claimAsync`, `unclaimAsync`, or `updateMetadataAsync`. */
		response: Option[Map[String, JsValue]] = None,
	  /** This field will always be not set if the operation is created by `claimAsync`, `unclaimAsync`, or `updateMetadataAsync`. In this case, error information for each device is set in `response.perDeviceStatus.result.status`. */
		error: Option[Schema.Status] = None,
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** This field will contain a `DevicesLongRunningOperationMetadata` object if the operation is created by `claimAsync`, `unclaimAsync`, or `updateMetadataAsync`. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class Configuration(
	  /** Required. A short name that describes the configuration's purpose. For example, _Sales team_ or _Temporary employees_. The zero-touch enrollment portal displays this name to IT admins. */
		configurationName: Option[String] = None,
	  /** Required. Whether this is the default configuration that zero-touch enrollment applies to any new devices the organization purchases in the future. Only one customer configuration can be the default. Setting this value to `true`, changes the previous default configuration's `isDefault` value to `false`. */
		isDefault: Option[Boolean] = None,
	  /** Output only. The API resource name in the format `customers/[CUSTOMER_ID]/configurations/[CONFIGURATION_ID]`. Assigned by the server. */
		name: Option[String] = None,
	  /** Required. The name of the organization. Zero-touch enrollment shows this organization name to device users during device provisioning. */
		companyName: Option[String] = None,
	  /** Required. The resource name of the selected DPC (device policy controller) in the format `customers/[CUSTOMER_ID]/dpcs/&#42;`. To list the supported DPCs, call `customers.dpcs.list`. */
		dpcResourcePath: Option[String] = None,
	  /** Required. The telephone number that device users can call, using another device, to get help. Zero-touch enrollment shows this number to device users before device provisioning. Accepts numerals, spaces, the plus sign, hyphens, and parentheses. */
		contactPhone: Option[String] = None,
	  /** A message, containing one or two sentences, to help device users get help or give them more details about what’s happening to their device. Zero-touch enrollment shows this message before the device is provisioned. */
		customMessage: Option[String] = None,
	  /** Required. The email address that device users can contact to get help. Zero-touch enrollment shows this email address to device users before device provisioning. The value is validated on input. */
		contactEmail: Option[String] = None,
	  /** Output only. The ID of the configuration. Assigned by the server. */
		configurationId: Option[String] = None,
	  /** Optional. The timeout before forcing factory reset the device if the device doesn't go through provisioning in the setup wizard, usually due to lack of network connectivity during setup wizard. Ranges from 0-6 hours, with 2 hours being the default if unset. */
		forcedResetTime: Option[String] = None,
	  /** The JSON-formatted EMM provisioning extras that are passed to the DPC. */
		dpcExtras: Option[String] = None
	)
	
	object PerDeviceStatusInBatch {
		enum StatusEnum extends Enum[StatusEnum] { case SINGLE_DEVICE_STATUS_UNSPECIFIED, SINGLE_DEVICE_STATUS_UNKNOWN_ERROR, SINGLE_DEVICE_STATUS_OTHER_ERROR, SINGLE_DEVICE_STATUS_SUCCESS, SINGLE_DEVICE_STATUS_PERMISSION_DENIED, SINGLE_DEVICE_STATUS_INVALID_DEVICE_IDENTIFIER, SINGLE_DEVICE_STATUS_INVALID_SECTION_TYPE, SINGLE_DEVICE_STATUS_SECTION_NOT_YOURS, SINGLE_DEVICE_STATUS_INVALID_TOKEN, SINGLE_DEVICE_STATUS_REVOKED_TOKEN, SINGLE_DEVICE_STATUS_DEVICE_LIMIT_EXCEEDED }
	}
	case class PerDeviceStatusInBatch(
	  /** If processing succeeds, the device ID of the device. */
		deviceId: Option[String] = None,
	  /** If processing fails, a developer message explaining what went wrong. */
		errorMessage: Option[String] = None,
	  /** The result status of the device after processing. */
		status: Option[Schema.PerDeviceStatusInBatch.StatusEnum] = None,
	  /** If processing fails, the error type. */
		errorIdentifier: Option[String] = None
	)
	
	object DeviceClaim {
		enum SectionTypeEnum extends Enum[SectionTypeEnum] { case SECTION_TYPE_UNSPECIFIED, SECTION_TYPE_SIM_LOCK, SECTION_TYPE_ZERO_TOUCH }
		enum AdditionalServiceEnum extends Enum[AdditionalServiceEnum] { case ADDITIONAL_SERVICE_UNSPECIFIED, DEVICE_PROTECTION }
	}
	case class DeviceClaim(
	  /** The ID of the reseller that claimed the device. */
		resellerId: Option[String] = None,
	  /** Output only. The type of claim made on the device. */
		sectionType: Option[Schema.DeviceClaim.SectionTypeEnum] = None,
	  /** The ID of the Customer that purchased the device. */
		ownerCompanyId: Option[String] = None,
	  /** The ID of the Google Workspace account that owns the Chrome OS device. */
		googleWorkspaceCustomerId: Option[String] = None,
	  /** The timestamp when the device was put into ‘vacation mode’. This value is present iff the device is in 'vacation mode'. */
		vacationModeStartTime: Option[String] = None,
	  /** The timestamp when the device will exit ‘vacation mode’. This value is present iff the device is in 'vacation mode'. */
		vacationModeExpireTime: Option[String] = None,
	  /** The Additional service registered for the device. */
		additionalService: Option[Schema.DeviceClaim.AdditionalServiceEnum] = None
	)
	
	case class FindDevicesByDeviceIdentifierRequest(
	  /** Required. The maximum number of devices to show in a page of results. Must be between 1 and 100 inclusive. */
		limit: Option[String] = None,
	  /** Required. Required. The device identifier to search for. */
		deviceIdentifier: Option[Schema.DeviceIdentifier] = None,
	  /** A token specifying which result page to return. */
		pageToken: Option[String] = None
	)
}
