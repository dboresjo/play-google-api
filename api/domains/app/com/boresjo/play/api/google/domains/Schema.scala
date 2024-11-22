package com.boresjo.play.api.google.domains

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.Operation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
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
	
	case class SearchDomainsResponse(
	  /** Results of the domain name search. */
		registerParameters: Option[List[Schema.RegisterParameters]] = None
	)
	
	object RegisterParameters {
		enum AvailabilityEnum extends Enum[AvailabilityEnum] { case AVAILABILITY_UNSPECIFIED, AVAILABLE, UNAVAILABLE, UNSUPPORTED, UNKNOWN }
		enum SupportedPrivacyEnum extends Enum[SupportedPrivacyEnum] { case CONTACT_PRIVACY_UNSPECIFIED, PUBLIC_CONTACT_DATA, PRIVATE_CONTACT_DATA, REDACTED_CONTACT_DATA }
		enum DomainNoticesEnum extends Enum[DomainNoticesEnum] { case DOMAIN_NOTICE_UNSPECIFIED, HSTS_PRELOADED }
	}
	case class RegisterParameters(
	  /** The domain name. Unicode domain names are expressed in Punycode format. */
		domainName: Option[String] = None,
	  /** Indicates whether the domain is available for registration. This value is accurate when obtained by calling `RetrieveRegisterParameters`, but is approximate when obtained by calling `SearchDomains`. */
		availability: Option[Schema.RegisterParameters.AvailabilityEnum] = None,
	  /** Contact privacy options that the domain supports. */
		supportedPrivacy: Option[List[Schema.RegisterParameters.SupportedPrivacyEnum]] = None,
	  /** Notices about special properties of the domain. */
		domainNotices: Option[List[Schema.RegisterParameters.DomainNoticesEnum]] = None,
	  /** Price to register or renew the domain for one year. */
		yearlyPrice: Option[Schema.Money] = None
	)
	
	case class Money(
	  /** The three-letter currency code defined in ISO 4217. */
		currencyCode: Option[String] = None,
	  /** The whole units of the amount. For example if `currencyCode` is `"USD"`, then 1 unit is one US dollar. */
		units: Option[String] = None,
	  /** Number of nano (10^-9) units of the amount. The value must be between -999,999,999 and +999,999,999 inclusive. If `units` is positive, `nanos` must be positive or zero. If `units` is zero, `nanos` can be positive, zero, or negative. If `units` is negative, `nanos` must be negative or zero. For example $-1.75 is represented as `units`=-1 and `nanos`=-750,000,000. */
		nanos: Option[Int] = None
	)
	
	case class RetrieveRegisterParametersResponse(
	  /** Parameters to use when calling the `RegisterDomain` method. */
		registerParameters: Option[Schema.RegisterParameters] = None
	)
	
	object RegisterDomainRequest {
		enum DomainNoticesEnum extends Enum[DomainNoticesEnum] { case DOMAIN_NOTICE_UNSPECIFIED, HSTS_PRELOADED }
		enum ContactNoticesEnum extends Enum[ContactNoticesEnum] { case CONTACT_NOTICE_UNSPECIFIED, PUBLIC_CONTACT_DATA_ACKNOWLEDGEMENT }
	}
	case class RegisterDomainRequest(
	  /** Required. The complete `Registration` resource to be created. */
		registration: Option[Schema.Registration] = None,
	  /** The list of domain notices that you acknowledge. Call `RetrieveRegisterParameters` to see the notices that need acknowledgement. */
		domainNotices: Option[List[Schema.RegisterDomainRequest.DomainNoticesEnum]] = None,
	  /** The list of contact notices that the caller acknowledges. The notices needed here depend on the values specified in `registration.contact_settings`. */
		contactNotices: Option[List[Schema.RegisterDomainRequest.ContactNoticesEnum]] = None,
	  /** Required. Yearly price to register or renew the domain. The value that should be put here can be obtained from RetrieveRegisterParameters or SearchDomains calls. */
		yearlyPrice: Option[Schema.Money] = None,
	  /** When true, only validation is performed, without actually registering the domain. Follows: https://cloud.google.com/apis/design/design_patterns#request_validation */
		validateOnly: Option[Boolean] = None
	)
	
	object Registration {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, REGISTRATION_PENDING, REGISTRATION_FAILED, TRANSFER_PENDING, TRANSFER_FAILED, IMPORT_PENDING, ACTIVE, SUSPENDED, EXPORTED, EXPIRED }
		enum IssuesEnum extends Enum[IssuesEnum] { case ISSUE_UNSPECIFIED, CONTACT_SUPPORT, UNVERIFIED_EMAIL, PROBLEM_WITH_BILLING, DNS_NOT_ACTIVATED }
		enum SupportedPrivacyEnum extends Enum[SupportedPrivacyEnum] { case CONTACT_PRIVACY_UNSPECIFIED, PUBLIC_CONTACT_DATA, PRIVATE_CONTACT_DATA, REDACTED_CONTACT_DATA }
		enum RegisterFailureReasonEnum extends Enum[RegisterFailureReasonEnum] { case REGISTER_FAILURE_REASON_UNSPECIFIED, REGISTER_FAILURE_REASON_UNKNOWN, DOMAIN_NOT_AVAILABLE, INVALID_CONTACTS }
		enum TransferFailureReasonEnum extends Enum[TransferFailureReasonEnum] { case TRANSFER_FAILURE_REASON_UNSPECIFIED, TRANSFER_FAILURE_REASON_UNKNOWN, EMAIL_CONFIRMATION_FAILURE, DOMAIN_NOT_REGISTERED, DOMAIN_HAS_TRANSFER_LOCK, INVALID_AUTHORIZATION_CODE, TRANSFER_CANCELLED, TRANSFER_REJECTED, INVALID_REGISTRANT_EMAIL_ADDRESS, DOMAIN_NOT_ELIGIBLE_FOR_TRANSFER, TRANSFER_ALREADY_PENDING }
		enum DomainPropertiesEnum extends Enum[DomainPropertiesEnum] { case DOMAIN_PROPERTY_UNSPECIFIED, TRANSFER_LOCK_UNSUPPORTED_BY_REGISTRY, REQUIRE_PUSH_TRANSFER }
	}
	case class Registration(
	  /** Output only. Name of the `Registration` resource, in the format `projects/&#42;/locations/&#42;/registrations/`. */
		name: Option[String] = None,
	  /** Required. Immutable. The domain name. Unicode domain names must be expressed in Punycode format. */
		domainName: Option[String] = None,
	  /** Output only. The creation timestamp of the `Registration` resource. */
		createTime: Option[String] = None,
	  /** Output only. The expiration timestamp of the `Registration`. */
		expireTime: Option[String] = None,
	  /** Output only. The state of the `Registration` */
		state: Option[Schema.Registration.StateEnum] = None,
	  /** Output only. The set of issues with the `Registration` that require attention. */
		issues: Option[List[Schema.Registration.IssuesEnum]] = None,
	  /** Set of labels associated with the `Registration`. */
		labels: Option[Map[String, String]] = None,
	  /** Settings for management of the `Registration`, including renewal, billing, and transfer. You cannot update these with the `UpdateRegistration` method. To update these settings, use the `ConfigureManagementSettings` method. */
		managementSettings: Option[Schema.ManagementSettings] = None,
	  /** Settings controlling the DNS configuration of the `Registration`. You cannot update these with the `UpdateRegistration` method. To update these settings, use the `ConfigureDnsSettings` method. */
		dnsSettings: Option[Schema.DnsSettings] = None,
	  /** Required. Settings for contact information linked to the `Registration`. You cannot update these with the `UpdateRegistration` method. To update these settings, use the `ConfigureContactSettings` method. */
		contactSettings: Option[Schema.ContactSettings] = None,
	  /** Output only. Pending contact settings for the `Registration`. Updates to the `contact_settings` field that change its `registrant_contact` or `privacy` fields require email confirmation by the `registrant_contact` before taking effect. This field is set only if there are pending updates to the `contact_settings` that have not been confirmed. To confirm the changes, the `registrant_contact` must follow the instructions in the email they receive. */
		pendingContactSettings: Option[Schema.ContactSettings] = None,
	  /** Output only. Set of options for the `contact_settings.privacy` field that this `Registration` supports. */
		supportedPrivacy: Option[List[Schema.Registration.SupportedPrivacyEnum]] = None,
	  /** Output only. The reason the domain registration failed. Only set for domains in REGISTRATION_FAILED state. */
		registerFailureReason: Option[Schema.Registration.RegisterFailureReasonEnum] = None,
	  /** Output only. Deprecated: For more information, see [Cloud Domains feature deprecation](https://cloud.google.com/domains/docs/deprecations/feature-deprecations). The reason the domain transfer failed. Only set for domains in TRANSFER_FAILED state. */
		transferFailureReason: Option[Schema.Registration.TransferFailureReasonEnum] = None,
	  /** Output only. Special properties of the domain. */
		domainProperties: Option[List[Schema.Registration.DomainPropertiesEnum]] = None
	)
	
	object ManagementSettings {
		enum RenewalMethodEnum extends Enum[RenewalMethodEnum] { case RENEWAL_METHOD_UNSPECIFIED, AUTOMATIC_RENEWAL, MANUAL_RENEWAL, RENEWAL_DISABLED }
		enum PreferredRenewalMethodEnum extends Enum[PreferredRenewalMethodEnum] { case RENEWAL_METHOD_UNSPECIFIED, AUTOMATIC_RENEWAL, MANUAL_RENEWAL, RENEWAL_DISABLED }
		enum TransferLockStateEnum extends Enum[TransferLockStateEnum] { case TRANSFER_LOCK_STATE_UNSPECIFIED, UNLOCKED, LOCKED }
		enum EffectiveTransferLockStateEnum extends Enum[EffectiveTransferLockStateEnum] { case TRANSFER_LOCK_STATE_UNSPECIFIED, UNLOCKED, LOCKED }
	}
	case class ManagementSettings(
	  /** Output only. The actual renewal method for this `Registration`. When `preferred_renewal_method` is set to `AUTOMATIC_RENEWAL`, the actual `renewal_method` can be equal to `RENEWAL_DISABLED`—for example, when there are problems with the billing account or reported domain abuse. In such cases, check the `issues` field on the `Registration`. After the problem is resolved, the `renewal_method` is automatically updated to `preferred_renewal_method` in a few hours. */
		renewalMethod: Option[Schema.ManagementSettings.RenewalMethodEnum] = None,
	  /** Optional. The desired renewal method for this `Registration`. The actual `renewal_method` is automatically updated to reflect this choice. If unset or equal to `RENEWAL_METHOD_UNSPECIFIED`, the actual `renewalMethod` is treated as if it were set to `AUTOMATIC_RENEWAL`. You cannot use `RENEWAL_DISABLED` during resource creation, and you can update the renewal status only when the `Registration` resource has state `ACTIVE` or `SUSPENDED`. When `preferred_renewal_method` is set to `AUTOMATIC_RENEWAL`, the actual `renewal_method` can be set to `RENEWAL_DISABLED` in case of problems with the billing account or reported domain abuse. In such cases, check the `issues` field on the `Registration`. After the problem is resolved, the `renewal_method` is automatically updated to `preferred_renewal_method` in a few hours. */
		preferredRenewalMethod: Option[Schema.ManagementSettings.PreferredRenewalMethodEnum] = None,
	  /** This is the desired transfer lock state for this `Registration`. A transfer lock controls whether the domain can be transferred to another registrar. The transfer lock state of the domain is returned in the `effective_transfer_lock_state` property. The transfer lock state values might be different for the following reasons: &#42; `transfer_lock_state` was updated only a short time ago. &#42; Domains with the `TRANSFER_LOCK_UNSUPPORTED_BY_REGISTRY` state are in the list of `domain_properties`. These domains are always in the `UNLOCKED` state. */
		transferLockState: Option[Schema.ManagementSettings.TransferLockStateEnum] = None,
	  /** Output only. The actual transfer lock state for this `Registration`. */
		effectiveTransferLockState: Option[Schema.ManagementSettings.EffectiveTransferLockStateEnum] = None
	)
	
	case class DnsSettings(
	  /** An arbitrary DNS provider identified by its name servers. */
		customDns: Option[Schema.CustomDns] = None,
	  /** Deprecated: For more information, see [Cloud Domains feature deprecation](https://cloud.google.com/domains/docs/deprecations/feature-deprecations). The free DNS zone provided by [Google Domains](https://domains.google/). */
		googleDomainsDns: Option[Schema.GoogleDomainsDns] = None,
	  /** The list of glue records for this `Registration`. Commonly empty. */
		glueRecords: Option[List[Schema.GlueRecord]] = None,
	  /** Output only. Indicates if this `Registration` has configured one of the following deprecated Google Domains DNS features: &#42; Domain forwarding (HTTP `301` and `302` response status codes), &#42; Email forwarding. See https://cloud.google.com/domains/docs/deprecations/feature-deprecations for more details. If any of these features is enabled call the `RetrieveGoogleDomainsForwardingConfig` method to get details about the feature's configuration. A forwarding configuration might not work correctly if required DNS records are not present in the domain's authoritative DNS Zone. */
		googleDomainsRedirectsDataAvailable: Option[Boolean] = None
	)
	
	case class CustomDns(
	  /** Required. A list of name servers that store the DNS zone for this domain. Each name server is a domain name, with Unicode domain names expressed in Punycode format. */
		nameServers: Option[List[String]] = None,
	  /** The list of DS records for this domain, which are used to enable DNSSEC. The domain's DNS provider can provide the values to set here. If this field is empty, DNSSEC is disabled. */
		dsRecords: Option[List[Schema.DsRecord]] = None
	)
	
	object DsRecord {
		enum AlgorithmEnum extends Enum[AlgorithmEnum] { case ALGORITHM_UNSPECIFIED, RSAMD5, DH, DSA, ECC, RSASHA1, DSANSEC3SHA1, RSASHA1NSEC3SHA1, RSASHA256, RSASHA512, ECCGOST, ECDSAP256SHA256, ECDSAP384SHA384, ED25519, ED448, INDIRECT, PRIVATEDNS, PRIVATEOID }
		enum DigestTypeEnum extends Enum[DigestTypeEnum] { case DIGEST_TYPE_UNSPECIFIED, SHA1, SHA256, GOST3411, SHA384 }
	}
	case class DsRecord(
	  /** The key tag of the record. Must be set in range 0 -- 65535. */
		keyTag: Option[Int] = None,
	  /** The algorithm used to generate the referenced DNSKEY. */
		algorithm: Option[Schema.DsRecord.AlgorithmEnum] = None,
	  /** The hash function used to generate the digest of the referenced DNSKEY. */
		digestType: Option[Schema.DsRecord.DigestTypeEnum] = None,
	  /** The digest generated from the referenced DNSKEY. */
		digest: Option[String] = None
	)
	
	object GoogleDomainsDns {
		enum DsStateEnum extends Enum[DsStateEnum] { case DS_STATE_UNSPECIFIED, DS_RECORDS_UNPUBLISHED, DS_RECORDS_PUBLISHED }
	}
	case class GoogleDomainsDns(
	  /** Output only. A list of name servers that store the DNS zone for this domain. Each name server is a domain name, with Unicode domain names expressed in Punycode format. This field is automatically populated with the name servers assigned to the Google Domains DNS zone. */
		nameServers: Option[List[String]] = None,
	  /** Required. The state of DS records for this domain. Used to enable or disable automatic DNSSEC. */
		dsState: Option[Schema.GoogleDomainsDns.DsStateEnum] = None,
	  /** Output only. The list of DS records published for this domain. The list is automatically populated when `ds_state` is `DS_RECORDS_PUBLISHED`, otherwise it remains empty. */
		dsRecords: Option[List[Schema.DsRecord]] = None
	)
	
	case class GlueRecord(
	  /** Required. Domain name of the host in Punycode format. */
		hostName: Option[String] = None,
	  /** List of IPv4 addresses corresponding to this host in the standard decimal format (e.g. `198.51.100.1`). At least one of `ipv4_address` and `ipv6_address` must be set. */
		ipv4Addresses: Option[List[String]] = None,
	  /** List of IPv6 addresses corresponding to this host in the standard hexadecimal format (e.g. `2001:db8::`). At least one of `ipv4_address` and `ipv6_address` must be set. */
		ipv6Addresses: Option[List[String]] = None
	)
	
	object ContactSettings {
		enum PrivacyEnum extends Enum[PrivacyEnum] { case CONTACT_PRIVACY_UNSPECIFIED, PUBLIC_CONTACT_DATA, PRIVATE_CONTACT_DATA, REDACTED_CONTACT_DATA }
	}
	case class ContactSettings(
	  /** Required. Privacy setting for the contacts associated with the `Registration`. */
		privacy: Option[Schema.ContactSettings.PrivacyEnum] = None,
	  /** Required. The registrant contact for the `Registration`. &#42;Caution: Anyone with access to this email address, phone number, and/or postal address can take control of the domain.&#42; &#42;Warning: For new `Registration`s, the registrant receives an email confirmation that they must complete within 15 days to avoid domain suspension.&#42; */
		registrantContact: Option[Schema.Contact] = None,
	  /** Required. The administrative contact for the `Registration`. */
		adminContact: Option[Schema.Contact] = None,
	  /** Required. The technical contact for the `Registration`. */
		technicalContact: Option[Schema.Contact] = None
	)
	
	case class Contact(
	  /** Required. Postal address of the contact. */
		postalAddress: Option[Schema.PostalAddress] = None,
	  /** Required. Email address of the contact. */
		email: Option[String] = None,
	  /** Required. Phone number of the contact in international format. For example, `"+1-800-555-0123"`. */
		phoneNumber: Option[String] = None,
	  /** Fax number of the contact in international format. For example, `"+1-800-555-0123"`. */
		faxNumber: Option[String] = None
	)
	
	case class PostalAddress(
	  /** The schema revision of the `PostalAddress`. This must be set to 0, which is the latest revision. All new revisions &#42;&#42;must&#42;&#42; be backward compatible with old revisions. */
		revision: Option[Int] = None,
	  /** Required. CLDR region code of the country/region of the address. This is never inferred and it is up to the user to ensure the value is correct. See https://cldr.unicode.org/ and https://www.unicode.org/cldr/charts/30/supplemental/territory_information.html for details. Example: "CH" for Switzerland. */
		regionCode: Option[String] = None,
	  /** Optional. BCP-47 language code of the contents of this address (if known). This is often the UI language of the input form or is expected to match one of the languages used in the address' country/region, or their transliterated equivalents. This can affect formatting in certain countries, but is not critical to the correctness of the data and will never affect any validation or other non-formatting related operations. If this value is not known, it should be omitted (rather than specifying a possibly incorrect default). Examples: "zh-Hant", "ja", "ja-Latn", "en". */
		languageCode: Option[String] = None,
	  /** Optional. Postal code of the address. Not all countries use or require postal codes to be present, but where they are used, they may trigger additional validation with other parts of the address (e.g. state/zip validation in the U.S.A.). */
		postalCode: Option[String] = None,
	  /** Optional. Additional, country-specific, sorting code. This is not used in most regions. Where it is used, the value is either a string like "CEDEX", optionally followed by a number (e.g. "CEDEX 7"), or just a number alone, representing the "sector code" (Jamaica), "delivery area indicator" (Malawi) or "post office indicator" (e.g. Côte d'Ivoire). */
		sortingCode: Option[String] = None,
	  /** Optional. Highest administrative subdivision which is used for postal addresses of a country or region. For example, this can be a state, a province, an oblast, or a prefecture. Specifically, for Spain this is the province and not the autonomous community (e.g. "Barcelona" and not "Catalonia"). Many countries don't use an administrative area in postal addresses. E.g. in Switzerland this should be left unpopulated. */
		administrativeArea: Option[String] = None,
	  /** Optional. Generally refers to the city/town portion of the address. Examples: US city, IT comune, UK post town. In regions of the world where localities are not well defined or do not fit into this structure well, leave locality empty and use address_lines. */
		locality: Option[String] = None,
	  /** Optional. Sublocality of the address. For example, this can be neighborhoods, boroughs, districts. */
		sublocality: Option[String] = None,
	  /** Unstructured address lines describing the lower levels of an address. Because values in address_lines do not have type information and may sometimes contain multiple values in a single field (e.g. "Austin, TX"), it is important that the line order is clear. The order of address lines should be "envelope order" for the country/region of the address. In places where this can vary (e.g. Japan), address_language is used to make it explicit (e.g. "ja" for large-to-small ordering and "ja-Latn" or "en" for small-to-large). This way, the most specific line of an address can be selected based on the language. The minimum permitted structural representation of an address consists of a region_code with all remaining information placed in the address_lines. It would be possible to format such an address very approximately without geocoding, but no semantic reasoning could be made about any of the address components until it was at least partially resolved. Creating an address only containing a region_code and address_lines, and then geocoding is the recommended way to handle completely unstructured addresses (as opposed to guessing which parts of the address should be localities or administrative areas). */
		addressLines: Option[List[String]] = None,
	  /** Optional. The recipient at the address. This field may, under certain circumstances, contain multiline information. For example, it might contain "care of" information. */
		recipients: Option[List[String]] = None,
	  /** Optional. The name of the organization at the address. */
		organization: Option[String] = None
	)
	
	case class RetrieveTransferParametersResponse(
	  /** Parameters to use when calling the `TransferDomain` method. */
		transferParameters: Option[Schema.TransferParameters] = None
	)
	
	object TransferParameters {
		enum TransferLockStateEnum extends Enum[TransferLockStateEnum] { case TRANSFER_LOCK_STATE_UNSPECIFIED, UNLOCKED, LOCKED }
		enum SupportedPrivacyEnum extends Enum[SupportedPrivacyEnum] { case CONTACT_PRIVACY_UNSPECIFIED, PUBLIC_CONTACT_DATA, PRIVATE_CONTACT_DATA, REDACTED_CONTACT_DATA }
	}
	case class TransferParameters(
	  /** The domain name. Unicode domain names are expressed in Punycode format. */
		domainName: Option[String] = None,
	  /** The registrar that currently manages the domain. */
		currentRegistrar: Option[String] = None,
	  /** The URL of the registrar that currently manages the domain. */
		currentRegistrarUri: Option[String] = None,
	  /** The name servers that currently store the configuration of the domain. */
		nameServers: Option[List[String]] = None,
	  /** Indicates whether the domain is protected by a transfer lock. For a transfer to succeed, this must show `UNLOCKED`. To unlock a domain, go to its current registrar. */
		transferLockState: Option[Schema.TransferParameters.TransferLockStateEnum] = None,
	  /** Contact privacy options that the domain supports. */
		supportedPrivacy: Option[List[Schema.TransferParameters.SupportedPrivacyEnum]] = None,
	  /** Price to transfer or renew the domain for one year. */
		yearlyPrice: Option[Schema.Money] = None
	)
	
	object TransferDomainRequest {
		enum ContactNoticesEnum extends Enum[ContactNoticesEnum] { case CONTACT_NOTICE_UNSPECIFIED, PUBLIC_CONTACT_DATA_ACKNOWLEDGEMENT }
	}
	case class TransferDomainRequest(
	  /** Required. The complete `Registration` resource to be created. You can leave `registration.dns_settings` unset to import the domain's current DNS configuration from its current registrar. Use this option only if you are sure that the domain's current DNS service does not cease upon transfer, as is often the case for DNS services provided for free by the registrar. */
		registration: Option[Schema.Registration] = None,
	  /** The list of contact notices that you acknowledge. The notices needed here depend on the values specified in `registration.contact_settings`. */
		contactNotices: Option[List[Schema.TransferDomainRequest.ContactNoticesEnum]] = None,
	  /** Required. Acknowledgement of the price to transfer or renew the domain for one year. Call `RetrieveTransferParameters` to obtain the price, which you must acknowledge. */
		yearlyPrice: Option[Schema.Money] = None,
	  /** The domain's transfer authorization code. You can obtain this from the domain's current registrar. */
		authorizationCode: Option[Schema.AuthorizationCode] = None,
	  /** Validate the request without actually transferring the domain. */
		validateOnly: Option[Boolean] = None
	)
	
	case class AuthorizationCode(
	  /** The Authorization Code in ASCII. It can be used to transfer the domain to or from another registrar. */
		code: Option[String] = None
	)
	
	case class RetrieveImportableDomainsResponse(
	  /** A list of domains that the calling user manages in Google Domains. */
		domains: Option[List[Schema.Domain]] = None,
	  /** When present, there are more results to retrieve. Set `page_token` to this value on a subsequent call to get the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object Domain {
		enum ResourceStateEnum extends Enum[ResourceStateEnum] { case RESOURCE_STATE_UNSPECIFIED, IMPORTABLE, UNSUPPORTED, SUSPENDED, EXPIRED, DELETED }
	}
	case class Domain(
	  /** The domain name. Unicode domain names are expressed in Punycode format. */
		domainName: Option[String] = None,
	  /** The state of this domain as a `Registration` resource. */
		resourceState: Option[Schema.Domain.ResourceStateEnum] = None,
	  /** Price to renew the domain for one year. Only set when `resource_state` is `IMPORTABLE`. */
		yearlyPrice: Option[Schema.Money] = None
	)
	
	case class ImportDomainRequest(
	  /** Required. The domain name. Unicode domain names must be expressed in Punycode format. */
		domainName: Option[String] = None,
	  /** Set of labels associated with the `Registration`. */
		labels: Option[Map[String, String]] = None
	)
	
	case class ListRegistrationsResponse(
	  /** A list of `Registration`s. */
		registrations: Option[List[Schema.Registration]] = None,
	  /** When present, there are more results to retrieve. Set `page_token` to this value on a subsequent call to get the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class ConfigureManagementSettingsRequest(
	  /** Fields of the `ManagementSettings` to update. */
		managementSettings: Option[Schema.ManagementSettings] = None,
	  /** Required. The field mask describing which fields to update as a comma-separated list. For example, if only the transfer lock is being updated, the `update_mask` is `"transfer_lock_state"`. */
		updateMask: Option[String] = None
	)
	
	case class ConfigureDnsSettingsRequest(
	  /** Fields of the `DnsSettings` to update. */
		dnsSettings: Option[Schema.DnsSettings] = None,
	  /** Required. The field mask describing which fields to update as a comma-separated list. For example, if only the name servers are being updated for an existing Custom DNS configuration, the `update_mask` is `"custom_dns.name_servers"`. When changing the DNS provider from one type to another, pass the new provider's field name as part of the field mask. For example, when changing from a Google Domains DNS configuration to a Custom DNS configuration, the `update_mask` is `"custom_dns"`. // */
		updateMask: Option[String] = None,
	  /** Validate the request without actually updating the DNS settings. */
		validateOnly: Option[Boolean] = None
	)
	
	case class RetrieveGoogleDomainsDnsRecordsResponse(
	  /** The resource record set resources (DNS Zone records). */
		rrset: Option[List[Schema.ResourceRecordSet]] = None,
	  /** When present, there are more results to retrieve. Set `page_token` to this value on a subsequent call to get the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class ResourceRecordSet(
	  /** For example, www.example.com. */
		name: Option[String] = None,
	  /** The identifier of a supported record type. See the list of Supported DNS record types. */
		`type`: Option[String] = None,
	  /** Number of seconds that this `ResourceRecordSet` can be cached by resolvers. */
		ttl: Option[Int] = None,
	  /** As defined in RFC 1035 (section 5) and RFC 1034 (section 3.6.1) -- see examples. */
		rrdata: Option[List[String]] = None,
	  /** As defined in RFC 4034 (section 3.2). */
		signatureRrdata: Option[List[String]] = None,
	  /** Configures dynamic query responses based on either the geo location of the querying user or a weighted round robin based routing policy. A valid `ResourceRecordSet` contains only `rrdata` (for static resolution) or a `routing_policy` (for dynamic resolution). */
		routingPolicy: Option[Schema.RRSetRoutingPolicy] = None
	)
	
	case class RRSetRoutingPolicy(
		geoPolicy: Option[Schema.GeoPolicy] = None,
		wrrPolicy: Option[Schema.WrrPolicy] = None,
		geo: Option[Schema.GeoPolicy] = None,
		wrr: Option[Schema.WrrPolicy] = None,
		primaryBackup: Option[Schema.PrimaryBackupPolicy] = None,
	  /** The selfLink attribute of the HealthCheck resource to use for this RRSetRoutingPolicy. https://cloud.google.com/compute/docs/reference/rest/v1/healthChecks */
		healthCheck: Option[String] = None
	)
	
	case class GeoPolicy(
	  /** The primary geo routing configuration. If there are multiple items with the same location, an error is returned instead. */
		item: Option[List[Schema.GeoPolicyItem]] = None,
	  /** Without fencing, if health check fails for all configured items in the current geo bucket, we failover to the next nearest geo bucket. With fencing, if health checking is enabled, as long as some targets in the current geo bucket are healthy, we return only the healthy targets. However, if all targets are unhealthy, we don't failover to the next nearest bucket; instead, we return all the items in the current bucket even when all targets are unhealthy. */
		enableFencing: Option[Boolean] = None
	)
	
	case class GeoPolicyItem(
	  /** The geo-location granularity is a GCP region. This location string should correspond to a GCP region. e.g. "us-east1", "southamerica-east1", "asia-east1", etc. */
		location: Option[String] = None,
		rrdata: Option[List[String]] = None,
	  /** DNSSEC generated signatures for all the `rrdata` within this item. If health checked targets are provided for DNSSEC enabled zones, there's a restriction of 1 IP address per item. */
		signatureRrdata: Option[List[String]] = None,
	  /** For A and AAAA types only. Endpoints to return in the query result only if they are healthy. These can be specified along with `rrdata` within this item. */
		healthCheckedTargets: Option[Schema.HealthCheckTargets] = None
	)
	
	case class HealthCheckTargets(
	  /** Configuration for internal load balancers to be health checked. */
		internalLoadBalancer: Option[List[Schema.LoadBalancerTarget]] = None,
	  /** The Internet IP addresses to be health checked. The format matches the format of ResourceRecordSet.rrdata as defined in RFC 1035 (section 5) and RFC 1034 (section 3.6.1) */
		externalEndpoints: Option[List[String]] = None
	)
	
	object LoadBalancerTarget {
		enum LoadBalancerTypeEnum extends Enum[LoadBalancerTypeEnum] { case NONE, GLOBAL_L7ILB, REGIONAL_L4ILB, REGIONAL_L7ILB }
		enum IpProtocolEnum extends Enum[IpProtocolEnum] { case UNDEFINED, TCP, UDP }
	}
	case class LoadBalancerTarget(
	  /** The type of load balancer specified by this target. This value must match the configuration of the load balancer located at the LoadBalancerTarget's IP address, port, and region. Use the following: - &#42;regionalL4ilb&#42;: for a regional internal passthrough Network Load Balancer. - &#42;regionalL7ilb&#42;: for a regional internal Application Load Balancer. - &#42;globalL7ilb&#42;: for a global internal Application Load Balancer.  */
		loadBalancerType: Option[Schema.LoadBalancerTarget.LoadBalancerTypeEnum] = None,
	  /** The frontend IP address of the load balancer to health check. */
		ipAddress: Option[String] = None,
	  /** The configured port of the load balancer. */
		port: Option[String] = None,
	  /** The protocol of the load balancer to health check. */
		ipProtocol: Option[Schema.LoadBalancerTarget.IpProtocolEnum] = None,
	  /** The fully qualified URL of the network that the load balancer is attached to. This should be formatted like `https://www.googleapis.com/compute/v1/projects/{project}/global/networks/{network}`. */
		networkUrl: Option[String] = None,
	  /** The project ID in which the load balancer is located. */
		project: Option[String] = None,
	  /** The region in which the load balancer is located. */
		region: Option[String] = None
	)
	
	case class WrrPolicy(
		item: Option[List[Schema.WrrPolicyItem]] = None
	)
	
	case class WrrPolicyItem(
	  /** The weight corresponding to this `WrrPolicyItem` object. When multiple `WrrPolicyItem` objects are configured, the probability of returning an `WrrPolicyItem` object's data is proportional to its weight relative to the sum of weights configured for all items. This weight must be non-negative. */
		weight: Option[BigDecimal] = None,
		rrdata: Option[List[String]] = None,
	  /** DNSSEC generated signatures for all the `rrdata` within this item. Note that if health checked targets are provided for DNSSEC enabled zones, there's a restriction of 1 IP address per item. */
		signatureRrdata: Option[List[String]] = None,
	  /** Endpoints that are health checked before making the routing decision. The unhealthy endpoints are omitted from the result. If all endpoints within a bucket are unhealthy, we choose a different bucket (sampled with respect to its weight) for responding. If DNSSEC is enabled for this zone, only one of `rrdata` or `health_checked_targets` can be set. */
		healthCheckedTargets: Option[Schema.HealthCheckTargets] = None
	)
	
	case class PrimaryBackupPolicy(
	  /** Endpoints that are health checked before making the routing decision. Unhealthy endpoints are omitted from the results. If all endpoints are unhealthy, we serve a response based on the `backup_geo_targets`. */
		primaryTargets: Option[Schema.HealthCheckTargets] = None,
	  /** Backup targets provide a regional failover policy for the otherwise global primary targets. If serving state is set to `BACKUP`, this policy essentially becomes a geo routing policy. */
		backupGeoTargets: Option[Schema.GeoPolicy] = None,
	  /** When serving state is `PRIMARY`, this field provides the option of sending a small percentage of the traffic to the backup targets. */
		trickleTraffic: Option[BigDecimal] = None
	)
	
	case class RetrieveGoogleDomainsForwardingConfigResponse(
	  /** The list of domain forwarding configurations. A forwarding configuration might not work correctly if the required DNS records are not present in the domain's authoritative DNS zone. */
		domainForwardings: Option[List[Schema.DomainForwarding]] = None,
	  /** The list of email forwarding configurations. A forwarding configuration might not work correctly if the required DNS records are not present in the domain's authoritative DNS zone. */
		emailForwardings: Option[List[Schema.EmailForwarding]] = None
	)
	
	object DomainForwarding {
		enum RedirectTypeEnum extends Enum[RedirectTypeEnum] { case REDIRECT_TYPE_UNSPECIFIED, TEMPORARY, PERMANENT }
	}
	case class DomainForwarding(
	  /** The subdomain of the registered domain that is being forwarded. E.g. `www.example.com`, `example.com` (i.e. the registered domain itself) or `&#42;.example.com` (i.e. all subdomains). */
		subdomain: Option[String] = None,
	  /** The target of the domain forwarding, i.e. the path to redirect the `subdomain` to. */
		targetUri: Option[String] = None,
	  /** The redirect type. */
		redirectType: Option[Schema.DomainForwarding.RedirectTypeEnum] = None,
	  /** If true, forwards the path after the domain name to the same path at the new address. */
		pathForwarding: Option[Boolean] = None,
	  /** If true, the forwarding works also over HTTPS. */
		sslEnabled: Option[Boolean] = None,
	  /** The PEM-encoded certificate chain. */
		pemCertificate: Option[String] = None
	)
	
	case class EmailForwarding(
	  /** An alias recipient email that forwards emails to the `target_email_address`. For example, `admin@example.com` or `&#42;@example.com` (wildcard alias forwards all the emails under the registered domain). */
		alias: Option[String] = None,
	  /** Target email that receives emails sent to the `alias`. */
		targetEmailAddress: Option[String] = None
	)
	
	object ConfigureContactSettingsRequest {
		enum ContactNoticesEnum extends Enum[ContactNoticesEnum] { case CONTACT_NOTICE_UNSPECIFIED, PUBLIC_CONTACT_DATA_ACKNOWLEDGEMENT }
	}
	case class ConfigureContactSettingsRequest(
	  /** Fields of the `ContactSettings` to update. */
		contactSettings: Option[Schema.ContactSettings] = None,
	  /** Required. The field mask describing which fields to update as a comma-separated list. For example, if only the registrant contact is being updated, the `update_mask` is `"registrant_contact"`. */
		updateMask: Option[String] = None,
	  /** The list of contact notices that the caller acknowledges. The notices needed here depend on the values specified in `contact_settings`. */
		contactNotices: Option[List[Schema.ConfigureContactSettingsRequest.ContactNoticesEnum]] = None,
	  /** Validate the request without actually updating the contact settings. */
		validateOnly: Option[Boolean] = None
	)
	
	case class ExportRegistrationRequest(
	
	)
	
	case class ResetAuthorizationCodeRequest(
	
	)
	
	case class InitiatePushTransferRequest(
	  /** Required. The Tag of the new registrar. Can be found at [List of registrars](https://nominet.uk/registrar-list/). */
		tag: Option[String] = None
	)
	
	case class RenewDomainRequest(
	  /** Required. Acknowledgement of the price to renew the domain for one year. To get the price, see [Cloud Domains pricing](https://cloud.google.com/domains/pricing). If not provided, the expected price is returned in the error message. */
		yearlyPrice: Option[Schema.Money] = None,
	  /** Optional. When true, only validation is performed, without actually renewing the domain. For more information, see [Request validation](https://cloud.google.com/apis/design/design_patterns#request_validation) */
		validateOnly: Option[Boolean] = None
	)
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None,
	  /** OPTIONAL: A FieldMask specifying which fields of the policy to modify. Only the fields in the mask will be modified. If no mask is provided, the following default mask is used: `paths: "bindings, etag"` */
		updateMask: Option[String] = None
	)
	
	case class Policy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.Binding]] = None,
	  /** Specifies cloud audit logging configuration for this policy. */
		auditConfigs: Option[List[Schema.AuditConfig]] = None,
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An `etag` is returned in the response to `getIamPolicy`, and systems are expected to put that etag in the request to `setIamPolicy` to ensure that their change will be applied to the same version of the policy. &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. */
		etag: Option[String] = None
	)
	
	case class Binding(
	  /** Role that is assigned to the list of `members`, or principals. For example, `roles/viewer`, `roles/editor`, or `roles/owner`. For an overview of the IAM roles and permissions, see the [IAM documentation](https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see [here](https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. `members` can have the following values: &#42; `allUsers`: A special identifier that represents anyone who is on the internet; with or without a Google account. &#42; `allAuthenticatedUsers`: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. &#42; `user:{emailid}`: An email address that represents a specific Google account. For example, `alice@example.com` . &#42; `serviceAccount:{emailid}`: An email address that represents a Google service account. For example, `my-other-app@appspot.gserviceaccount.com`. &#42; `serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]`: An identifier for a [Kubernetes service account](https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, `my-project.svc.id.goog[my-namespace/my-kubernetes-sa]`. &#42; `group:{emailid}`: An email address that represents a Google group. For example, `admins@example.com`. &#42; `domain:{domain}`: The G Suite domain (primary) that represents all the users of that domain. For example, `google.com` or `example.com`. &#42; `principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workforce identity pool. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}`: All workforce identities in a group. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All workforce identities with a specific attribute value. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;`: All identities in a workforce identity pool. &#42; `principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workload identity pool. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}`: A workload identity pool group. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All identities in a workload identity pool with a certain attribute. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;`: All identities in a workload identity pool. &#42; `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a user that has been recently deleted. For example, `alice@example.com?uid=123456789012345678901`. If the user is recovered, this value reverts to `user:{emailid}` and the recovered user retains the role in the binding. &#42; `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, `my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901`. If the service account is undeleted, this value reverts to `serviceAccount:{emailid}` and the undeleted service account retains the role in the binding. &#42; `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, `admins@example.com?uid=123456789012345678901`. If the group is recovered, this value reverts to `group:{emailid}` and the recovered group retains the role in the binding. &#42; `deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: Deleted single identity in a workforce identity pool. For example, `deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value`. */
		members: Option[List[String]] = None,
	  /** The condition that is associated with this binding. If the condition evaluates to `true`, then this binding applies to the current request. If the condition evaluates to `false`, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		condition: Option[Schema.Expr] = None
	)
	
	case class Expr(
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None,
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None,
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None
	)
	
	case class AuditConfig(
	  /** Specifies a service that will be enabled for audit logging. For example, `storage.googleapis.com`, `cloudsql.googleapis.com`. `allServices` is a special value that covers all services. */
		service: Option[String] = None,
	  /** The configuration for logging of each type of permission. */
		auditLogConfigs: Option[List[Schema.AuditLogConfig]] = None
	)
	
	object AuditLogConfig {
		enum LogTypeEnum extends Enum[LogTypeEnum] { case LOG_TYPE_UNSPECIFIED, ADMIN_READ, DATA_WRITE, DATA_READ }
	}
	case class AuditLogConfig(
	  /** The log type that this config enables. */
		logType: Option[Schema.AuditLogConfig.LogTypeEnum] = None,
	  /** Specifies the identities that do not cause logging for this type of permission. Follows the same format of Binding.members. */
		exemptedMembers: Option[List[String]] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class ListLocationsResponse(
	  /** A list of locations that matches the specified filter in the request. */
		locations: Option[List[Schema.Location]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Location(
	  /** Resource name for the location, which may vary between implementations. For example: `"projects/example-project/locations/us-east1"` */
		name: Option[String] = None,
	  /** The canonical id for this location. For example: `"us-east1"`. */
		locationId: Option[String] = None,
	  /** The friendly name for this location, typically a nearby city name. For example, "Tokyo". */
		displayName: Option[String] = None,
	  /** Cross-service attributes for the location. For example {"cloud.googleapis.com/region": "us-east1"} */
		labels: Option[Map[String, String]] = None,
	  /** Service-specific metadata. For example the available capacity at the given location. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class OperationMetadata(
	  /** The time the operation was created. */
		createTime: Option[String] = None,
	  /** The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Human-readable status of the operation, if any. */
		statusDetail: Option[String] = None,
	  /** API version used to start the operation. */
		apiVersion: Option[String] = None
	)
}
