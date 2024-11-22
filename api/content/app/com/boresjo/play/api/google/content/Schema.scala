package com.boresjo.play.api.google.content

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class AccountsAuthInfoResponse(
	  /** The account identifiers corresponding to the authenticated user. - For an individual account: only the merchant ID is defined - For an aggregator: only the aggregator ID is defined - For a subaccount of an MCA: both the merchant ID and the aggregator ID are defined.  */
		accountIdentifiers: Option[List[Schema.AccountIdentifier]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#accountsAuthInfoResponse`". */
		kind: Option[String] = None
	)
	
	case class AccountIdentifier(
	  /** The aggregator ID, set for aggregators and subaccounts (in that case, it represents the aggregator of the subaccount). */
		aggregatorId: Option[String] = None,
	  /** The merchant account ID, set for individual accounts and subaccounts. */
		merchantId: Option[String] = None
	)
	
	case class AccountsClaimWebsiteResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#accountsClaimWebsiteResponse`". */
		kind: Option[String] = None
	)
	
	case class AccountsCustomBatchRequest(
	  /** The request entries to be processed in the batch. */
		entries: Option[List[Schema.AccountsCustomBatchRequestEntry]] = None
	)
	
	case class AccountsCustomBatchRequestEntry(
	  /** An entry ID, unique within the batch request. */
		batchId: Option[Int] = None,
	  /** The ID of the managing account. */
		merchantId: Option[String] = None,
	  /** The method of the batch entry. Acceptable values are: - "`claimWebsite`" - "`delete`" - "`get`" - "`insert`" - "`link`" - "`update`"  */
		method: Option[String] = None,
	  /** The ID of the targeted account. Only defined if the method is not `insert`. */
		accountId: Option[String] = None,
	  /** The account to create or update. Only defined if the method is `insert` or `update`. */
		account: Option[Schema.Account] = None,
	  /** Only applicable if the method is `claimwebsite`. Indicates whether or not to take the claim from another account in case there is a conflict. */
		overwrite: Option[Boolean] = None,
	  /** Whether the account should be deleted if the account has offers. Only applicable if the method is `delete`. */
		force: Option[Boolean] = None,
	  /** Details about the `link` request. */
		linkRequest: Option[Schema.AccountsCustomBatchRequestEntryLinkRequest] = None,
	  /** Controls which fields are visible. Only applicable if the method is 'get'. */
		view: Option[String] = None,
	  /** Label IDs for the 'updatelabels' request. */
		labelIds: Option[List[String]] = None
	)
	
	case class Account(
	  /** Required. Display name for the account. */
		name: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#account`". */
		kind: Option[String] = None,
	  /** The merchant's website. */
		websiteUrl: Option[String] = None,
	  /** Indicates whether the merchant sells adult content. */
		adultContent: Option[Boolean] = None,
	  /** Client-specific, locally-unique, internal ID for the child account. */
		sellerId: Option[String] = None,
	  /** Users with access to the account. Every account (except for subaccounts) must have at least one admin user. */
		users: Option[List[Schema.AccountUser]] = None,
	  /** Required. 64-bit Merchant Center account ID. */
		id: Option[String] = None,
	  /** Linked YouTube channels that are active or pending approval. To create a new link request, add a new link with status `active` to the list. It will remain in a `pending` state until approved or rejected in the YT Creator Studio interface. To delete an active link, or to cancel a link request, remove it from the list. */
		youtubeChannelLinks: Option[List[Schema.AccountYouTubeChannelLink]] = None,
	  /** The Business Profile which is linked or in the process of being linked with the Merchant Center account. */
		googleMyBusinessLink: Option[Schema.AccountGoogleMyBusinessLink] = None,
	  /** The business information of the account. */
		businessInformation: Option[Schema.AccountBusinessInformation] = None,
	  /** The business identity attributes can be used to self-declare attributes that let customers know more about your business. */
		businessIdentity: Option[Schema.AccountBusinessIdentity] = None,
	  /** The automatic improvements of the account can be used to automatically update items, improve images and shipping. Each section inside AutomaticImprovements is updated separately. */
		automaticImprovements: Option[Schema.AccountAutomaticImprovements] = None,
	  /** Linked Ads accounts that are active or pending approval. To create a new link request, add a new link with status `active` to the list. It will remain in a `pending` state until approved or rejected either in the Ads interface or through the Google Ads API. To delete an active link, or to cancel a link request, remove it from the list. */
		adsLinks: Option[List[Schema.AccountAdsLink]] = None,
	  /** ID of CSS the account belongs to. */
		cssId: Option[String] = None,
	  /** Manually created label IDs that are assigned to the account by CSS. */
		labelIds: Option[List[String]] = None,
	  /** Output only. How the account is managed. Acceptable values are: - "`manual`" - "`automatic`"  */
		accountManagement: Option[String] = None,
	  /** Automatically created label IDs that are assigned to the account by CSS Center. */
		automaticLabelIds: Option[List[String]] = None,
	  /** Settings for conversion tracking. */
		conversionSettings: Option[Schema.AccountConversionSettings] = None
	)
	
	case class AccountUser(
	  /** User's email address. */
		emailAddress: Option[String] = None,
	  /** Whether user is an admin. */
		admin: Option[Boolean] = None,
	  /** Whether user is an order manager. */
		orderManager: Option[Boolean] = None,
	  /** Whether user can manage payment settings. */
		paymentsManager: Option[Boolean] = None,
	  /** Whether user can access payment statements. */
		paymentsAnalyst: Option[Boolean] = None,
	  /** Whether user is a reporting manager. This role is equivalent to the Performance and insights role in Merchant Center. */
		reportingManager: Option[Boolean] = None
	)
	
	case class AccountYouTubeChannelLink(
	  /** Channel ID. */
		channelId: Option[String] = None,
	  /** Status of the link between this Merchant Center account and the YouTube channel. Upon retrieval, it represents the actual status of the link and can be either `active` if it was approved in YT Creator Studio or `pending` if it's pending approval. Upon insertion, it represents the &#42;intended&#42; status of the link. Re-uploading a link with status `active` when it's still pending or with status `pending` when it's already active will have no effect: the status will remain unchanged. Re-uploading a link with deprecated status `inactive` is equivalent to not submitting the link at all and will delete the link if it was active or cancel the link request if it was pending. */
		status: Option[String] = None
	)
	
	case class AccountGoogleMyBusinessLink(
	  /** The Business Profile email address of a specific account within a Business Profile. A sample account within a Business Profile could be a business account with set of locations, managed under the Business Profile. */
		gmbEmail: Option[String] = None,
	  /** Status of the link between this Merchant Center account and the Business Profile. Acceptable values are: - "`active`" - "`pending`"  */
		status: Option[String] = None,
	  /** The ID of the Business Profile. If this is provided, then `gmbEmail` is ignored. The value of this field should match the `accountId` used by the Business Profile API. */
		gmbAccountId: Option[String] = None
	)
	
	case class AccountBusinessInformation(
	  /** The address of the business. Use `\n` to add a second address line. */
		address: Option[Schema.AccountAddress] = None,
	  /** The phone number of the business in [E.164](https://en.wikipedia.org/wiki/E.164) format. This can only be updated if a verified phone number is not already set. To replace a verified phone number use the `Accounts.requestphoneverification` and `Accounts.verifyphonenumber`. */
		phoneNumber: Option[String] = None,
	  /** Verification status of the phone number of the business. This status is read only and can be updated only by successful phone verification. Acceptable values are: - "`verified`" - "`unverified`"  */
		phoneVerificationStatus: Option[String] = None,
	  /** The customer service information of the business. */
		customerService: Option[Schema.AccountCustomerService] = None,
	  /** The 10-digit [Korean business registration number](https://support.google.com/merchants/answer/9037766) separated with dashes in the format: XXX-XX-XXXXX. This field will only be updated if explicitly set. */
		koreanBusinessRegistrationNumber: Option[String] = None
	)
	
	case class AccountAddress(
	  /** Street-level part of the address. Use `\n` to add a second line. */
		streetAddress: Option[String] = None,
	  /** City, town or commune. May also include dependent localities or sublocalities (for example, neighborhoods or suburbs). */
		locality: Option[String] = None,
	  /** Top-level administrative subdivision of the country. For example, a state like California ("CA") or a province like Quebec ("QC"). */
		region: Option[String] = None,
	  /** Postal code or ZIP (for example, "94043"). */
		postalCode: Option[String] = None,
	  /** CLDR country code (for example, "US"). All MCA sub-accounts inherit the country of their parent MCA by default, however the country can be updated for individual sub-accounts. */
		country: Option[String] = None
	)
	
	case class AccountCustomerService(
	  /** Customer service URL. */
		url: Option[String] = None,
	  /** Customer service email. */
		email: Option[String] = None,
	  /** Customer service phone number. */
		phoneNumber: Option[String] = None
	)
	
	case class AccountBusinessIdentity(
	  /** Specifies whether the business identifies itself as being black-owned. This optional field is only available for merchants with a business country set to "US". This field is not allowed for marketplaces or marketplace sellers. */
		blackOwned: Option[Schema.AccountIdentityType] = None,
	  /** Specifies whether the business identifies itself as being women-owned. This optional field is only available for merchants with a business country set to "US". This field is not allowed for marketplaces or marketplace sellers. */
		womenOwned: Option[Schema.AccountIdentityType] = None,
	  /** Specifies whether the business identifies itself as being veteran-owned. This optional field is only available for merchants with a business country set to "US". This field is not allowed for marketplaces or marketplace sellers. */
		veteranOwned: Option[Schema.AccountIdentityType] = None,
	  /** Specifies whether the business identifies itself as being latino-owned. This optional field is only available for merchants with a business country set to "US". This field is not allowed for marketplaces or marketplace sellers. */
		latinoOwned: Option[Schema.AccountIdentityType] = None,
	  /** Specifies whether the business identifies itself as a small business. This optional field is only available for merchants with a business country set to "US". It is also not allowed for marketplaces, but it is allowed to marketplace sellers. */
		smallBusiness: Option[Schema.AccountIdentityType] = None,
	  /** Required. By setting this field, your business may be included in promotions for all the selected attributes. If you clear this option, it won't affect your identification with any of the attributes. For this field to be set, the merchant must self identify with at least one of the `AccountIdentityType`. If none are included, the request will be considered invalid. */
		includeForPromotions: Option[Boolean] = None
	)
	
	case class AccountIdentityType(
	  /** Optional. Indicates that the business identifies itself with a given identity type. Setting this field does not automatically mean eligibility for promotions. */
		selfIdentified: Option[Boolean] = None
	)
	
	case class AccountAutomaticImprovements(
	  /** Turning on [item updates](https://support.google.com/merchants/answer/3246284) allows Google to automatically update items for you. When item updates are on, Google uses the structured data markup on the website and advanced data extractors to update the price and availability of the items. When the item updates are off, items with mismatched data aren't shown. This field is only updated (cleared) if provided. */
		itemUpdates: Option[Schema.AccountItemUpdates] = None,
	  /** This improvement will attempt to automatically correct submitted images if they don't meet the [image requirements](https://support.google.com/merchants/answer/6324350), for example, removing overlays. If successful, the image will be replaced and approved. This improvement is only applied to images of disapproved offers. For more information see: [Automatic image improvements](https://support.google.com/merchants/answer/9242973) This field is only updated (cleared) if provided. */
		imageImprovements: Option[Schema.AccountImageImprovements] = None,
	  /** Not available for MCAs [accounts](https://support.google.com/merchants/answer/188487). By turning on [automatic shipping improvements](https://support.google.com/merchants/answer/10027038), you are allowing Google to improve the accuracy of your delivery times shown to shoppers using Google. More accurate delivery times, especially when faster, typically lead to better conversion rates. Google will improve your estimated delivery times based on various factors: - Delivery address of an order - Current handling time and shipping time settings - Estimated weekdays or business days - Parcel tracking data This field is only updated (cleared) if provided. */
		shippingImprovements: Option[Schema.AccountShippingImprovements] = None
	)
	
	case class AccountItemUpdates(
	  /** Determines which attributes of the items should be automatically updated. If this field is not present, then the settings will be deleted. If there are no settings for subaccount, they are inherited from aggregator. */
		accountItemUpdatesSettings: Option[Schema.AccountItemUpdatesSettings] = None,
	  /** Output only. The effective value of allow_price_updates. If account_item_updates_settings is present, then this value is the same. Otherwise, it represents the inherited value of the parent account. Read-only. */
		effectiveAllowPriceUpdates: Option[Boolean] = None,
	  /** Output only. The effective value of allow_availability_updates. If account_item_updates_settings is present, then this value is the same. Otherwise, it represents the inherited value of the parent account. Read-only. */
		effectiveAllowAvailabilityUpdates: Option[Boolean] = None,
	  /** Output only. The effective value of allow_strict_availability_updates. If account_item_updates_settings is present, then this value is the same. Otherwise, it represents the inherited value of the parent account. Read-only. */
		effectiveAllowStrictAvailabilityUpdates: Option[Boolean] = None,
	  /** Output only. The effective value of allow_condition_updates. If account_item_updates_settings is present, then this value is the same. Otherwise, it represents the inherited value of the parent account. Read-only. */
		effectiveAllowConditionUpdates: Option[Boolean] = None
	)
	
	case class AccountItemUpdatesSettings(
	  /** If price updates are enabled, Google always updates the active price with the crawled information. */
		allowPriceUpdates: Option[Boolean] = None,
	  /** If availability updates are enabled, any previous availability values get overwritten if Google finds an out-of-stock annotation on the offer's page. If additionally `allow_availability_updates` field is set to true, values get overwritten if Google finds an in-stock annotation on the offer’s page. */
		allowAvailabilityUpdates: Option[Boolean] = None,
	  /** If allow_availability_updates is enabled, items are automatically updated in all your Shopping target countries. By default, availability updates will only be applied to items that are 'out of stock' on your website but 'in stock' on Shopping. Set this to true to also update items that are 'in stock' on your website, but 'out of stock' on Google Shopping. In order for this field to have an effect, you must also allow availability updates. */
		allowStrictAvailabilityUpdates: Option[Boolean] = None,
	  /** If condition updates are enabled, Google always updates item condition with the condition detected from the details of your product. */
		allowConditionUpdates: Option[Boolean] = None
	)
	
	case class AccountImageImprovements(
	  /** Determines how the images should be automatically updated. If this field is not present, then the settings will be deleted. If there are no settings for subaccount, they are inherited from aggregator. */
		accountImageImprovementsSettings: Option[Schema.AccountImageImprovementsSettings] = None,
	  /** Output only. The effective value of allow_automatic_image_improvements. If account_image_improvements_settings is present, then this value is the same. Otherwise, it represents the inherited value of the parent account. Read-only. */
		effectiveAllowAutomaticImageImprovements: Option[Boolean] = None
	)
	
	case class AccountImageImprovementsSettings(
	  /** Enables automatic image improvements. */
		allowAutomaticImageImprovements: Option[Boolean] = None
	)
	
	case class AccountShippingImprovements(
	  /** Enables automatic shipping improvements. */
		allowShippingImprovements: Option[Boolean] = None
	)
	
	case class AccountAdsLink(
	  /** Customer ID of the Ads account. */
		adsId: Option[String] = None,
	  /** Status of the link between this Merchant Center account and the Ads account. Upon retrieval, it represents the actual status of the link and can be either `active` if it was approved in Google Ads or `pending` if it's pending approval. Upon insertion, it represents the &#42;intended&#42; status of the link. Re-uploading a link with status `active` when it's still pending or with status `pending` when it's already active will have no effect: the status will remain unchanged. Re-uploading a link with deprecated status `inactive` is equivalent to not submitting the link at all and will delete the link if it was active or cancel the link request if it was pending. Acceptable values are: - "`active`" - "`pending`"  */
		status: Option[String] = None
	)
	
	case class AccountConversionSettings(
	  /** When enabled, free listing URLs have a parameter to enable conversion tracking for products owned by the current merchant account. See [auto-tagging](https://support.google.com/merchants/answer/11127659). */
		freeListingsAutoTaggingEnabled: Option[Boolean] = None
	)
	
	case class AccountsCustomBatchRequestEntryLinkRequest(
	  /** Action to perform for this link. The `"request"` action is only available to select merchants. Acceptable values are: - "`approve`" - "`remove`" - "`request`"  */
		action: Option[String] = None,
	  /** The ID of the linked account. */
		linkedAccountId: Option[String] = None,
	  /** Type of the link between the two accounts. Acceptable values are: - "`channelPartner`" - "`eCommercePlatform`" - "`paymentServiceProvider`" - "`localProductManager`"  */
		linkType: Option[String] = None,
	  /** Provided services. Acceptable values are: - "`shoppingAdsProductManagement`" - "`shoppingActionsProductManagement`" - "`shoppingActionsOrderManagement`" - "`paymentProcessing`" - "`localProductManagement`"  */
		services: Option[List[String]] = None
	)
	
	case class AccountsCustomBatchResponse(
	  /** The result of the execution of the batch requests. */
		entries: Option[List[Schema.AccountsCustomBatchResponseEntry]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#accountsCustomBatchResponse`". */
		kind: Option[String] = None
	)
	
	case class AccountsCustomBatchResponseEntry(
	  /** The ID of the request entry this entry responds to. */
		batchId: Option[Int] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#accountsCustomBatchResponseEntry`" */
		kind: Option[String] = None,
	  /** The retrieved, created, or updated account. Not defined if the method was `delete`, `claimwebsite` or `link`. */
		account: Option[Schema.Account] = None,
	  /** A list of errors for failed custombatch entries. &#42;Note:&#42; Schema errors fail the whole request. */
		errors: Option[Schema.Errors] = None
	)
	
	case class Errors(
	  /** A list of errors. */
		errors: Option[List[Schema.Error]] = None,
	  /** The HTTP status of the first error in `errors`. */
		code: Option[Int] = None,
	  /** The message of the first error in `errors`. */
		message: Option[String] = None
	)
	
	case class Error(
	  /** The domain of the error. */
		domain: Option[String] = None,
	  /** The error code. */
		reason: Option[String] = None,
	  /** A description of the error. */
		message: Option[String] = None
	)
	
	case class AccountsLinkRequest(
	  /** Action to perform for this link. The `"request"` action is only available to select merchants. Acceptable values are: - "`approve`" - "`remove`" - "`request`"  */
		action: Option[String] = None,
	  /** The ID of the linked account. */
		linkedAccountId: Option[String] = None,
	  /** Type of the link between the two accounts. Acceptable values are: - "`channelPartner`" - "`eCommercePlatform`" - "`paymentServiceProvider`"  */
		linkType: Option[String] = None,
	  /**  Acceptable values are: - "`shoppingAdsProductManagement`" - "`shoppingActionsProductManagement`" - "`shoppingActionsOrderManagement`" - "`paymentProcessing`"  */
		services: Option[List[String]] = None,
	  /** Additional information required for `paymentServiceProvider` link type. */
		paymentServiceProviderLinkInfo: Option[Schema.PaymentServiceProviderLinkInfo] = None,
	  /** Additional information required for `eCommercePlatform` link type. */
		eCommercePlatformLinkInfo: Option[Schema.ECommercePlatformLinkInfo] = None
	)
	
	case class PaymentServiceProviderLinkInfo(
	  /** The id used by the third party service provider to identify the merchant. */
		externalAccountId: Option[String] = None,
	  /** The business country of the merchant account as identified by the third party service provider. */
		externalAccountBusinessCountry: Option[String] = None
	)
	
	case class ECommercePlatformLinkInfo(
	  /** The id used by the third party service provider to identify the merchant. */
		externalAccountId: Option[String] = None
	)
	
	case class AccountsLinkResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#accountsLinkResponse`". */
		kind: Option[String] = None
	)
	
	case class AccountsListResponse(
	  /** The token for the retrieval of the next page of accounts. */
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#accountsListResponse`". */
		kind: Option[String] = None,
		resources: Option[List[Schema.Account]] = None
	)
	
	case class AccountsListLinksResponse(
	  /** The token for the retrieval of the next page of links. */
		nextPageToken: Option[String] = None,
	  /** The list of available links. */
		links: Option[List[Schema.LinkedAccount]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#accountsListLinksResponse`". */
		kind: Option[String] = None
	)
	
	case class LinkedAccount(
	  /** The ID of the linked account. */
		linkedAccountId: Option[String] = None,
	  /** List of provided services. */
		services: Option[List[Schema.LinkService]] = None
	)
	
	case class LinkService(
	  /** Service provided to or by the linked account. Acceptable values are: - "`shoppingActionsOrderManagement`" - "`shoppingActionsProductManagement`" - "`shoppingAdsProductManagement`" - "`paymentProcessing`"  */
		service: Option[String] = None,
	  /** Status of the link Acceptable values are: - "`active`" - "`inactive`" - "`pending`"  */
		status: Option[String] = None
	)
	
	case class AccountsUpdateLabelsRequest(
	  /** The IDs of labels that should be assigned to the account. */
		labelIds: Option[List[String]] = None
	)
	
	case class AccountsUpdateLabelsResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#accountsUpdateLabelsResponse`". */
		kind: Option[String] = None
	)
	
	case class AccountstatusesCustomBatchRequest(
	  /** The request entries to be processed in the batch. */
		entries: Option[List[Schema.AccountstatusesCustomBatchRequestEntry]] = None
	)
	
	case class AccountstatusesCustomBatchRequestEntry(
	  /** An entry ID, unique within the batch request. */
		batchId: Option[Int] = None,
	  /** The ID of the managing account. */
		merchantId: Option[String] = None,
	  /** The method of the batch entry. Acceptable values are: - "`get`"  */
		method: Option[String] = None,
	  /** The ID of the (sub-)account whose status to get. */
		accountId: Option[String] = None,
	  /** If set, only issues for the specified destinations are returned, otherwise only issues for the Shopping destination. */
		destinations: Option[List[String]] = None
	)
	
	case class AccountstatusesCustomBatchResponse(
	  /** The result of the execution of the batch requests. */
		entries: Option[List[Schema.AccountstatusesCustomBatchResponseEntry]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#accountstatusesCustomBatchResponse`". */
		kind: Option[String] = None
	)
	
	case class AccountstatusesCustomBatchResponseEntry(
	  /** The ID of the request entry this entry responds to. */
		batchId: Option[Int] = None,
	  /** The requested account status. Defined if and only if the request was successful. */
		accountStatus: Option[Schema.AccountStatus] = None,
	  /** A list of errors for failed custombatch entries. &#42;Note:&#42; Schema errors fail the whole request. */
		errors: Option[Schema.Errors] = None
	)
	
	case class AccountStatus(
	  /** The ID of the account for which the status is reported. */
		accountId: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#accountStatus`" */
		kind: Option[String] = None,
	  /** Whether the account's website is claimed or not. */
		websiteClaimed: Option[Boolean] = None,
	  /** A list of account level issues. */
		accountLevelIssues: Option[List[Schema.AccountStatusAccountLevelIssue]] = None,
	  /** List of product-related data by channel, destination, and country. Data in this field may be delayed by up to 30 minutes. */
		products: Option[List[Schema.AccountStatusProducts]] = None,
	  /** How the account is managed. Acceptable values are: - "`manual`" - "`automatic`"  */
		accountManagement: Option[String] = None
	)
	
	case class AccountStatusAccountLevelIssue(
	  /** Issue identifier. */
		id: Option[String] = None,
	  /** Short description of the issue. */
		title: Option[String] = None,
	  /** Country for which this issue is reported. */
		country: Option[String] = None,
	  /** Severity of the issue. Acceptable values are: - "`critical`" - "`error`" - "`suggestion`"  */
		severity: Option[String] = None,
	  /** Additional details about the issue. */
		detail: Option[String] = None,
	  /** The destination the issue applies to. If this field is empty then the issue applies to all available destinations. */
		destination: Option[String] = None,
	  /** The URL of a web page to help resolving this issue. */
		documentation: Option[String] = None
	)
	
	case class AccountStatusProducts(
	  /** The channel the data applies to. Acceptable values are: - "`local`" - "`online`"  */
		channel: Option[String] = None,
	  /** The destination the data applies to. */
		destination: Option[String] = None,
	  /** The country the data applies to. */
		country: Option[String] = None,
	  /** Aggregated product statistics. */
		statistics: Option[Schema.AccountStatusStatistics] = None,
	  /** List of item-level issues. */
		itemLevelIssues: Option[List[Schema.AccountStatusItemLevelIssue]] = None
	)
	
	case class AccountStatusStatistics(
	  /** Number of active offers. */
		active: Option[String] = None,
	  /** Number of pending offers. */
		pending: Option[String] = None,
	  /** Number of disapproved offers. */
		disapproved: Option[String] = None,
	  /** Number of expiring offers. */
		expiring: Option[String] = None
	)
	
	case class AccountStatusItemLevelIssue(
	  /** The error code of the issue. */
		code: Option[String] = None,
	  /** How this issue affects serving of the offer. */
		servability: Option[String] = None,
	  /** Whether the issue can be resolved by the merchant. */
		resolution: Option[String] = None,
	  /** The attribute's name, if the issue is caused by a single attribute. */
		attributeName: Option[String] = None,
	  /** A short issue description in English. */
		description: Option[String] = None,
	  /** A detailed issue description in English. */
		detail: Option[String] = None,
	  /** The URL of a web page to help with resolving this issue. */
		documentation: Option[String] = None,
	  /** Number of items with this issue. */
		numItems: Option[String] = None
	)
	
	case class AccountstatusesListResponse(
	  /** The token for the retrieval of the next page of account statuses. */
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#accountstatusesListResponse`". */
		kind: Option[String] = None,
		resources: Option[List[Schema.AccountStatus]] = None
	)
	
	case class AccounttaxCustomBatchRequest(
	  /** The request entries to be processed in the batch. */
		entries: Option[List[Schema.AccounttaxCustomBatchRequestEntry]] = None
	)
	
	case class AccounttaxCustomBatchRequestEntry(
	  /** An entry ID, unique within the batch request. */
		batchId: Option[Int] = None,
	  /** The ID of the managing account. */
		merchantId: Option[String] = None,
	  /** The method of the batch entry. Acceptable values are: - "`get`" - "`update`"  */
		method: Option[String] = None,
	  /** The ID of the account for which to get/update account tax settings. */
		accountId: Option[String] = None,
	  /** The account tax settings to update. Only defined if the method is `update`. */
		accountTax: Option[Schema.AccountTax] = None
	)
	
	case class AccountTax(
	  /** Required. The ID of the account to which these account tax settings belong. */
		accountId: Option[String] = None,
	  /** Tax rules. Updating the tax rules will enable "US" taxes (not reversible). Defining no rules is equivalent to not charging tax at all. */
		rules: Option[List[Schema.AccountTaxTaxRule]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#accountTax`". */
		kind: Option[String] = None
	)
	
	case class AccountTaxTaxRule(
	  /** Country code in which tax is applicable. */
		country: Option[String] = None,
	  /** Required. State (or province) is which the tax is applicable, described by its location ID (also called criteria ID). */
		locationId: Option[String] = None,
	  /** Whether the tax rate is taken from a global tax table or specified explicitly. */
		useGlobalRate: Option[Boolean] = None,
	  /** Explicit tax rate in percent, represented as a floating point number without the percentage character. Must not be negative. */
		ratePercent: Option[String] = None,
	  /** If true, shipping charges are also taxed. */
		shippingTaxed: Option[Boolean] = None
	)
	
	case class AccounttaxCustomBatchResponse(
	  /** The result of the execution of the batch requests. */
		entries: Option[List[Schema.AccounttaxCustomBatchResponseEntry]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#accounttaxCustomBatchResponse`". */
		kind: Option[String] = None
	)
	
	case class AccounttaxCustomBatchResponseEntry(
	  /** The ID of the request entry this entry responds to. */
		batchId: Option[Int] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#accounttaxCustomBatchResponseEntry`" */
		kind: Option[String] = None,
	  /** The retrieved or updated account tax settings. */
		accountTax: Option[Schema.AccountTax] = None,
	  /** A list of errors for failed custombatch entries. &#42;Note:&#42; Schema errors fail the whole request. */
		errors: Option[Schema.Errors] = None
	)
	
	case class AccounttaxListResponse(
	  /** The token for the retrieval of the next page of account tax settings. */
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#accounttaxListResponse`". */
		kind: Option[String] = None,
		resources: Option[List[Schema.AccountTax]] = None
	)
	
	case class DatafeedsCustomBatchRequest(
	  /** The request entries to be processed in the batch. */
		entries: Option[List[Schema.DatafeedsCustomBatchRequestEntry]] = None
	)
	
	case class DatafeedsCustomBatchRequestEntry(
	  /** An entry ID, unique within the batch request. */
		batchId: Option[Int] = None,
	  /** The ID of the managing account. */
		merchantId: Option[String] = None,
	  /** The method of the batch entry. Acceptable values are: - "`delete`" - "`fetchNow`" - "`get`" - "`insert`" - "`update`"  */
		method: Option[String] = None,
	  /** The ID of the data feed to get, delete or fetch. */
		datafeedId: Option[String] = None,
	  /** The data feed to insert. */
		datafeed: Option[Schema.Datafeed] = None
	)
	
	case class Datafeed(
	  /** Required for update. The ID of the data feed. */
		id: Option[String] = None,
	  /** Required for insert. A descriptive name of the data feed. */
		name: Option[String] = None,
	  /** Required. The type of data feed. For product inventory feeds, only feeds for local stores, not online stores, are supported. Acceptable values are: - "`local products`" - "`product inventory`" - "`products`"  */
		contentType: Option[String] = None,
	  /** The two-letter ISO 639-1 language in which the attributes are defined in the data feed. */
		attributeLanguage: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#datafeed`" */
		kind: Option[String] = None,
	  /** Required. The filename of the feed. All feeds must have a unique file name. */
		fileName: Option[String] = None,
	  /** Fetch schedule for the feed file. */
		fetchSchedule: Option[Schema.DatafeedFetchSchedule] = None,
	  /** Format of the feed file. */
		format: Option[Schema.DatafeedFormat] = None,
	  /** The targets this feed should apply to (country, language, destinations). */
		targets: Option[List[Schema.DatafeedTarget]] = None
	)
	
	case class DatafeedFetchSchedule(
	  /** The day of the month the feed file should be fetched (1-31). */
		dayOfMonth: Option[Int] = None,
	  /** The day of the week the feed file should be fetched. Acceptable values are: - "`monday`" - "`tuesday`" - "`wednesday`" - "`thursday`" - "`friday`" - "`saturday`" - "`sunday`"  */
		weekday: Option[String] = None,
	  /** The hour of the day the feed file should be fetched (0-23). */
		hour: Option[Int] = None,
	  /** Time zone used for schedule. UTC by default. For example, "America/Los_Angeles". */
		timeZone: Option[String] = None,
	  /** The URL where the feed file can be fetched. Google Merchant Center will support automatic scheduled uploads using the HTTP, HTTPS, FTP, or SFTP protocols, so the value will need to be a valid link using one of those four protocols. */
		fetchUrl: Option[String] = None,
	  /** An optional user name for fetch_url. */
		username: Option[String] = None,
	  /** An optional password for fetch_url. */
		password: Option[String] = None,
	  /** The minute of the hour the feed file should be fetched (0-59). Read-only. */
		minuteOfHour: Option[Int] = None,
	  /** Whether the scheduled fetch is paused or not. */
		paused: Option[Boolean] = None
	)
	
	case class DatafeedFormat(
	  /** Character encoding scheme of the data feed. If not specified, the encoding will be auto-detected. Acceptable values are: - "`latin-1`" - "`utf-16be`" - "`utf-16le`" - "`utf-8`" - "`windows-1252`"  */
		fileEncoding: Option[String] = None,
	  /** Delimiter for the separation of values in a delimiter-separated values feed. If not specified, the delimiter will be auto-detected. Ignored for non-DSV data feeds. Acceptable values are: - "`pipe`" - "`tab`" - "`tilde`"  */
		columnDelimiter: Option[String] = None,
	  /** Specifies how double quotes are interpreted. If not specified, the mode will be auto-detected. Ignored for non-DSV data feeds. Acceptable values are: - "`normal character`" - "`value quoting`"  */
		quotingMode: Option[String] = None
	)
	
	case class DatafeedTarget(
	  /** Deprecated. Use `feedLabel` instead. The country where the items in the feed will be included in the search index, represented as a CLDR territory code. */
		country: Option[String] = None,
	  /** Feed label for the DatafeedTarget. Either `country` or `feedLabel` is required. If both `feedLabel` and `country` is specified, the values must match. Must be less than or equal to 20 uppercase letters (A-Z), numbers (0-9), and dashes (-). */
		feedLabel: Option[String] = None,
	  /** The countries where the items may be displayed. Represented as a CLDR territory code. Will be ignored for "product inventory" feeds. */
		targetCountries: Option[List[String]] = None,
	  /** The two-letter ISO 639-1 language of the items in the feed. Must be a valid language for `targets[].country`. */
		language: Option[String] = None,
	  /** The list of [destinations to include](//support.google.com/merchants/answer/7501026) for this target (corresponds to checked check boxes in Merchant Center). Default destinations are always included unless provided in `excludedDestinations`. */
		includedDestinations: Option[List[String]] = None,
	  /** The list of [destinations to exclude](//support.google.com/merchants/answer/6324486) for this target (corresponds to cleared check boxes in Merchant Center). Products that are excluded from all destinations for more than 7 days are automatically deleted. */
		excludedDestinations: Option[List[String]] = None
	)
	
	case class DatafeedsCustomBatchResponse(
	  /** The result of the execution of the batch requests. */
		entries: Option[List[Schema.DatafeedsCustomBatchResponseEntry]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#datafeedsCustomBatchResponse`". */
		kind: Option[String] = None
	)
	
	case class DatafeedsCustomBatchResponseEntry(
	  /** The ID of the request entry this entry responds to. */
		batchId: Option[Int] = None,
	  /** The requested data feed. Defined if and only if the request was successful. */
		datafeed: Option[Schema.Datafeed] = None,
	  /** A list of errors for failed custombatch entries. &#42;Note:&#42; Schema errors fail the whole request. */
		errors: Option[Schema.Errors] = None
	)
	
	case class DatafeedsFetchNowResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#datafeedsFetchNowResponse`". */
		kind: Option[String] = None
	)
	
	case class DatafeedsListResponse(
	  /** The token for the retrieval of the next page of datafeeds. */
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#datafeedsListResponse`". */
		kind: Option[String] = None,
		resources: Option[List[Schema.Datafeed]] = None
	)
	
	case class DatafeedstatusesCustomBatchRequest(
	  /** The request entries to be processed in the batch. */
		entries: Option[List[Schema.DatafeedstatusesCustomBatchRequestEntry]] = None
	)
	
	case class DatafeedstatusesCustomBatchRequestEntry(
	  /** An entry ID, unique within the batch request. */
		batchId: Option[Int] = None,
	  /** The ID of the managing account. */
		merchantId: Option[String] = None,
	  /** The method of the batch entry. Acceptable values are: - "`get`"  */
		method: Option[String] = None,
	  /** The ID of the data feed to get. */
		datafeedId: Option[String] = None,
	  /** Deprecated. Use `feedLabel` instead. The country to get the datafeed status for. If this parameter is provided, then `language` must also be provided. Note that for multi-target datafeeds this parameter is required. */
		country: Option[String] = None,
	  /** The feed label to get the datafeed status for. If this parameter is provided, then `language` must also be provided. Note that for multi-target datafeeds this parameter is required. */
		feedLabel: Option[String] = None,
	  /** The language to get the datafeed status for. If this parameter is provided then `country` must also be provided. Note that for multi-target datafeeds this parameter is required. */
		language: Option[String] = None
	)
	
	case class DatafeedstatusesCustomBatchResponse(
	  /** The result of the execution of the batch requests. */
		entries: Option[List[Schema.DatafeedstatusesCustomBatchResponseEntry]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#datafeedstatusesCustomBatchResponse`". */
		kind: Option[String] = None
	)
	
	case class DatafeedstatusesCustomBatchResponseEntry(
	  /** The ID of the request entry this entry responds to. */
		batchId: Option[Int] = None,
	  /** The requested data feed status. Defined if and only if the request was successful. */
		datafeedStatus: Option[Schema.DatafeedStatus] = None,
	  /** A list of errors for failed custombatch entries. &#42;Note:&#42; Schema errors fail the whole request. */
		errors: Option[Schema.Errors] = None
	)
	
	case class DatafeedStatus(
	  /** The ID of the feed for which the status is reported. */
		datafeedId: Option[String] = None,
	  /** The processing status of the feed. Acceptable values are: - "`"`failure`": The feed could not be processed or all items had errors.`" - "`in progress`": The feed is being processed. - "`none`": The feed has not yet been processed. For example, a feed that has never been uploaded will have this processing status. - "`success`": The feed was processed successfully, though some items might have had errors.  */
		processingStatus: Option[String] = None,
	  /** The list of errors occurring in the feed. */
		errors: Option[List[Schema.DatafeedStatusError]] = None,
	  /** The list of errors occurring in the feed. */
		warnings: Option[List[Schema.DatafeedStatusError]] = None,
	  /** The number of items in the feed that were processed. */
		itemsTotal: Option[String] = None,
	  /** The number of items in the feed that were valid. */
		itemsValid: Option[String] = None,
	  /** The last date at which the feed was uploaded. */
		lastUploadDate: Option[String] = None,
	  /** The country for which the status is reported, represented as a CLDR territory code. */
		country: Option[String] = None,
	  /** The feed label status is reported for. */
		feedLabel: Option[String] = None,
	  /** The two-letter ISO 639-1 language for which the status is reported. */
		language: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#datafeedStatus`" */
		kind: Option[String] = None
	)
	
	case class DatafeedStatusError(
	  /** The code of the error, for example, "validation/invalid_value". */
		code: Option[String] = None,
	  /** The number of occurrences of the error in the feed. */
		count: Option[String] = None,
	  /** The error message, for example, "Invalid price". */
		message: Option[String] = None,
	  /** A list of example occurrences of the error, grouped by product. */
		examples: Option[List[Schema.DatafeedStatusExample]] = None
	)
	
	case class DatafeedStatusExample(
	  /** Line number in the data feed where the example is found. */
		lineNumber: Option[String] = None,
	  /** The ID of the example item. */
		itemId: Option[String] = None,
	  /** The problematic value. */
		value: Option[String] = None
	)
	
	case class DatafeedstatusesListResponse(
	  /** The token for the retrieval of the next page of datafeed statuses. */
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#datafeedstatusesListResponse`". */
		kind: Option[String] = None,
		resources: Option[List[Schema.DatafeedStatus]] = None
	)
	
	case class LiasettingsCustomBatchRequest(
	  /** The request entries to be processed in the batch. */
		entries: Option[List[Schema.LiasettingsCustomBatchRequestEntry]] = None
	)
	
	case class LiasettingsCustomBatchRequestEntry(
	  /** An entry ID, unique within the batch request. */
		batchId: Option[Int] = None,
	  /** The ID of the managing account. */
		merchantId: Option[String] = None,
	  /** The method of the batch entry. Acceptable values are: - "`get`" - "`getAccessibleGmbAccounts`" - "`requestGmbAccess`" - "`requestInventoryVerification`" - "`setInventoryVerificationContact`" - "`update`"  */
		method: Option[String] = None,
	  /** The ID of the account for which to get/update account LIA settings. */
		accountId: Option[String] = None,
	  /** The country code. Required only for RequestInventoryVerification. */
		country: Option[String] = None,
	  /** The account Lia settings to update. Only defined if the method is `update`. */
		liaSettings: Option[Schema.LiaSettings] = None,
	  /** The Business Profile. Required only for RequestGmbAccess. */
		gmbEmail: Option[String] = None,
	  /** Inventory validation contact name. Required only for SetInventoryValidationContact. */
		contactName: Option[String] = None,
	  /** Inventory validation contact email. Required only for SetInventoryValidationContact. */
		contactEmail: Option[String] = None,
	  /** The ID of POS data provider. Required only for SetPosProvider. */
		posDataProviderId: Option[String] = None,
	  /** The account ID by which this merchant is known to the POS provider. */
		posExternalAccountId: Option[String] = None,
	  /** The omnichannel experience for a country. Required only for SetOmnichannelExperience. */
		omnichannelExperience: Option[Schema.LiaOmnichannelExperience] = None
	)
	
	case class LiaSettings(
	  /** The ID of the account to which these LIA settings belong. Ignored upon update, always present in get request responses. */
		accountId: Option[String] = None,
	  /** The LIA settings for each country. */
		countrySettings: Option[List[Schema.LiaCountrySettings]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#liaSettings`" */
		kind: Option[String] = None
	)
	
	case class LiaCountrySettings(
	  /** Required. CLDR country code (for example, "US"). */
		country: Option[String] = None,
	  /** LIA inventory verification settings. */
		inventory: Option[Schema.LiaInventorySettings] = None,
	  /** LIA "On Display To Order" settings. */
		onDisplayToOrder: Option[Schema.LiaOnDisplayToOrderSettings] = None,
	  /** The status of the "Merchant hosted local storefront" feature. */
		hostedLocalStorefrontActive: Option[Boolean] = None,
	  /** The status of the "Store pickup" feature. */
		storePickupActive: Option[Boolean] = None,
	  /** The settings for the About page. */
		about: Option[Schema.LiaAboutPageSettings] = None,
	  /** The POS data provider linked with this country. */
		posDataProvider: Option[Schema.LiaPosDataProvider] = None,
	  /** The omnichannel experience configured for this country. */
		omnichannelExperience: Option[Schema.LiaOmnichannelExperience] = None
	)
	
	case class LiaInventorySettings(
	  /** The status of the inventory verification process. Acceptable values are: - "`active`" - "`inactive`" - "`pending`"  */
		status: Option[String] = None,
	  /** The name of the contact for the inventory verification process. */
		inventoryVerificationContactName: Option[String] = None,
	  /** The email of the contact for the inventory verification process. */
		inventoryVerificationContactEmail: Option[String] = None,
	  /** The status of the verification contact. Acceptable values are: - "`active`" - "`inactive`" - "`pending`"  */
		inventoryVerificationContactStatus: Option[String] = None
	)
	
	case class LiaOnDisplayToOrderSettings(
	  /** The status of the ?On display to order? feature. Acceptable values are: - "`active`" - "`inactive`" - "`pending`"  */
		status: Option[String] = None,
	  /** Shipping cost and policy URL. */
		shippingCostPolicyUrl: Option[String] = None
	)
	
	case class LiaAboutPageSettings(
	  /** The status of the verification process for the About page. Supported values are: - "`active`" - "`inactive`" - "`pending`"  */
		status: Option[String] = None,
	  /** The URL for the About page. */
		url: Option[String] = None
	)
	
	case class LiaPosDataProvider(
	  /** The ID of the POS data provider. */
		posDataProviderId: Option[String] = None,
	  /** The account ID by which this merchant is known to the POS data provider. */
		posExternalAccountId: Option[String] = None
	)
	
	case class LiaOmnichannelExperience(
	  /** The CLDR country code (for example, "US"). */
		country: Option[String] = None,
	  /** The Local Store Front (LSF) type for this country. Acceptable values are: - "`ghlsf`" (Google-Hosted Local Store Front) - "`mhlsfBasic`" (Merchant-Hosted Local Store Front Basic) - "`mhlsfFull`" (Merchant-Hosted Local Store Front Full) More details about these types can be found here. */
		lsfType: Option[String] = None,
	  /** The Pickup types for this country. Acceptable values are: - "`pickupToday`" - "`pickupLater`"  */
		pickupTypes: Option[List[String]] = None
	)
	
	case class LiasettingsCustomBatchResponse(
	  /** The result of the execution of the batch requests. */
		entries: Option[List[Schema.LiasettingsCustomBatchResponseEntry]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#liasettingsCustomBatchResponse`". */
		kind: Option[String] = None
	)
	
	case class LiasettingsCustomBatchResponseEntry(
	  /** The ID of the request entry to which this entry responds. */
		batchId: Option[Int] = None,
	  /** The retrieved or updated Lia settings. */
		liaSettings: Option[Schema.LiaSettings] = None,
	  /** A list of errors defined if, and only if, the request failed. */
		errors: Option[Schema.Errors] = None,
	  /** The list of accessible Business Profiles. */
		gmbAccounts: Option[Schema.GmbAccounts] = None,
	  /** The list of POS data providers. */
		posDataProviders: Option[List[Schema.PosDataProviders]] = None,
	  /** The updated omnichannel experience for a country. */
		omnichannelExperience: Option[Schema.LiaOmnichannelExperience] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#liasettingsCustomBatchResponseEntry`" */
		kind: Option[String] = None
	)
	
	case class GmbAccounts(
	  /** The ID of the Merchant Center account. */
		accountId: Option[String] = None,
	  /** A list of Business Profiles which are available to the merchant. */
		gmbAccounts: Option[List[Schema.GmbAccountsGmbAccount]] = None
	)
	
	case class GmbAccountsGmbAccount(
	  /** The type of the Business Profile (User or Business). */
		`type`: Option[String] = None,
	  /** The email which identifies the Business Profile. */
		email: Option[String] = None,
	  /** The name of the Business Profile. */
		name: Option[String] = None,
	  /** Number of listings under this account. */
		listingCount: Option[String] = None
	)
	
	case class PosDataProviders(
	  /** Country code. */
		country: Option[String] = None,
	  /** A list of POS data providers. */
		posDataProviders: Option[List[Schema.PosDataProvidersPosDataProvider]] = None
	)
	
	case class PosDataProvidersPosDataProvider(
	  /** The ID of the account. */
		providerId: Option[String] = None,
	  /** The display name of Pos data Provider. */
		displayName: Option[String] = None,
	  /** The full name of this POS data Provider. */
		fullName: Option[String] = None
	)
	
	case class LiasettingsGetAccessibleGmbAccountsResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#liasettingsGetAccessibleGmbAccountsResponse`". */
		kind: Option[String] = None,
	  /** The ID of the Merchant Center account. */
		accountId: Option[String] = None,
	  /** A list of Business Profiles which are available to the merchant. */
		gmbAccounts: Option[List[Schema.GmbAccountsGmbAccount]] = None
	)
	
	case class LiasettingsListResponse(
	  /** The token for the retrieval of the next page of LIA settings. */
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#liasettingsListResponse`". */
		kind: Option[String] = None,
		resources: Option[List[Schema.LiaSettings]] = None
	)
	
	case class LiasettingsListPosDataProvidersResponse(
	  /** The list of POS data providers for each eligible country */
		posDataProviders: Option[List[Schema.PosDataProviders]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#liasettingsListPosDataProvidersResponse`". */
		kind: Option[String] = None
	)
	
	case class LiasettingsRequestGmbAccessResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#liasettingsRequestGmbAccessResponse`". */
		kind: Option[String] = None
	)
	
	case class LiasettingsRequestInventoryVerificationResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#liasettingsRequestInventoryVerificationResponse`". */
		kind: Option[String] = None
	)
	
	case class LiasettingsSetInventoryVerificationContactResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#liasettingsSetInventoryVerificationContactResponse`". */
		kind: Option[String] = None
	)
	
	case class LiasettingsSetPosDataProviderResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#liasettingsSetPosDataProviderResponse`". */
		kind: Option[String] = None
	)
	
	case class LocalinventoryCustomBatchRequest(
	  /** The request entries to be processed in the batch. */
		entries: Option[List[Schema.LocalinventoryCustomBatchRequestEntry]] = None
	)
	
	case class LocalinventoryCustomBatchRequestEntry(
	  /** An entry ID, unique within the batch request. */
		batchId: Option[Int] = None,
	  /** The ID of the managing account. */
		merchantId: Option[String] = None,
	  /** Method of the batch request entry. Acceptable values are: - "`insert`"  */
		method: Option[String] = None,
	  /** The ID of the product for which to update local inventory. */
		productId: Option[String] = None,
	  /** Local inventory of the product. */
		localInventory: Option[Schema.LocalInventory] = None
	)
	
	case class LocalInventory(
	  /** Required. The store code of this local inventory resource. */
		storeCode: Option[String] = None,
	  /** The price of the product. */
		price: Option[Schema.Price] = None,
	  /** The sale price of the product. Mandatory if `sale_price_effective_date` is defined. */
		salePrice: Option[Schema.Price] = None,
	  /** A date range represented by a pair of ISO 8601 dates separated by a space, comma, or slash. Both dates may be specified as 'null' if undecided. */
		salePriceEffectiveDate: Option[String] = None,
	  /** The availability of the product. For accepted attribute values, see the local product inventory feed specification. */
		availability: Option[String] = None,
	  /** The quantity of the product. Must be nonnegative. */
		quantity: Option[Int] = None,
	  /** The supported pickup method for this offer. Unless the value is "not supported", this field must be submitted together with `pickupSla`. For accepted attribute values, see the local product inventory feed specification. */
		pickupMethod: Option[String] = None,
	  /** The expected date that an order will be ready for pickup relative to the order date. Must be submitted together with `pickupMethod`. For accepted attribute values, see the local product inventory feed specification. */
		pickupSla: Option[String] = None,
	  /** The in-store product location. */
		instoreProductLocation: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#localInventory`" */
		kind: Option[String] = None,
	  /** A list of custom (merchant-provided) attributes. Can also be used to submit any attribute of the feed specification in its generic form, for example, `{ "name": "size type", "value": "regular" }`. */
		customAttributes: Option[List[Schema.CustomAttribute]] = None
	)
	
	case class Price(
	  /** The price represented as a number. */
		value: Option[String] = None,
	  /** The currency of the price. */
		currency: Option[String] = None
	)
	
	case class CustomAttribute(
	  /** The name of the attribute. Underscores will be replaced by spaces upon insertion. */
		name: Option[String] = None,
	  /** The value of the attribute. */
		value: Option[String] = None,
	  /** Subattributes within this attribute group. Exactly one of value or groupValues must be provided. */
		groupValues: Option[List[Schema.CustomAttribute]] = None
	)
	
	case class LocalinventoryCustomBatchResponse(
	  /** The result of the execution of the batch requests. */
		entries: Option[List[Schema.LocalinventoryCustomBatchResponseEntry]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#localinventoryCustomBatchResponse`". */
		kind: Option[String] = None
	)
	
	case class LocalinventoryCustomBatchResponseEntry(
	  /** The ID of the request entry this entry responds to. */
		batchId: Option[Int] = None,
	  /** A list of errors for failed custombatch entries. &#42;Note:&#42; Schema errors fail the whole request. */
		errors: Option[Schema.Errors] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#localinventoryCustomBatchResponseEntry`" */
		kind: Option[String] = None
	)
	
	case class PosCustomBatchRequest(
	  /** The request entries to be processed in the batch. */
		entries: Option[List[Schema.PosCustomBatchRequestEntry]] = None
	)
	
	case class PosCustomBatchRequestEntry(
	  /** An entry ID, unique within the batch request. */
		batchId: Option[Int] = None,
	  /** The ID of the POS data provider. */
		merchantId: Option[String] = None,
	  /** The method of the batch entry. Acceptable values are: - "`delete`" - "`get`" - "`insert`" - "`inventory`" - "`sale`"  */
		method: Option[String] = None,
	  /** The ID of the account for which to get/submit data. */
		targetMerchantId: Option[String] = None,
	  /** The store code. This should be set only if the method is `delete` or `get`. */
		storeCode: Option[String] = None,
	  /** The store information to submit. This should be set only if the method is `insert`. */
		store: Option[Schema.PosStore] = None,
	  /** The inventory to submit. This should be set only if the method is `inventory`. */
		inventory: Option[Schema.PosInventory] = None,
	  /** The sale information to submit. This should be set only if the method is `sale`. */
		sale: Option[Schema.PosSale] = None
	)
	
	case class PosStore(
	  /** Required. A store identifier that is unique for the given merchant. */
		storeCode: Option[String] = None,
	  /** Required. The street address of the store. */
		storeAddress: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#posStore`" */
		kind: Option[String] = None,
	  /** The merchant or store name. */
		storeName: Option[String] = None,
	  /** The store phone number. */
		phoneNumber: Option[String] = None,
	  /** The website url for the store or merchant. */
		websiteUrl: Option[String] = None,
	  /** The business type of the store. */
		gcidCategory: Option[List[String]] = None,
	  /** The Google Place Id of the store location. */
		placeId: Option[String] = None,
	  /** Output only. The matching status of POS store and Google Business Profile store. Possible values are: - "`matched`": The POS store is successfully matched with the Google Business Profile store. - "`failed`": The POS store is not matched with the Google Business Profile store. See matching_status_hint for further details. Note that there is up to 48 hours propagation delay for changes in Merchant Center (e.g. creation of new account, accounts linking) and Google Business Profile (e.g. store address update) which may affect the matching status. In such cases, after a delay call [pos.list](https://developers.google.com/shopping-content/reference/rest/v2.1/pos/list) to retrieve the updated matching status.  */
		matchingStatus: Option[String] = None,
	  /** Output only. The hint of why the matching has failed. This is only set when matching_status=failed. Possible values are: - "`linked-store-not-found`": There aren't any Google Business Profile stores available for matching. Connect your Merchant Center account with the Google Business Profile account. Or add a new Google Business Profile store corresponding to the POS store. - "`store-match-not-found`": The provided POS store couldn't be matched to any of the connected Google Business Profile stores. Merchant Center account is connected correctly and stores are available on Google Business Profile, but POS store location address does not match with Google Business Profile stores' addresses. Update POS store address or Google Business Profile store address to match correctly. - "`store-match-unverified`": The provided POS store couldn't be matched to any of the connected Google Business Profile stores, as the matched Google Business Profile store is unverified. Go through the Google Business Profile verification process to match correctly.  */
		matchingStatusHint: Option[String] = None
	)
	
	case class PosInventory(
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#posInventory`" */
		kind: Option[String] = None,
	  /** Required. The identifier of the merchant's store. Either a `storeCode` inserted through the API or the code of the store in a Business Profile. */
		storeCode: Option[String] = None,
	  /** Required. A unique identifier for the item. */
		itemId: Option[String] = None,
	  /** Required. The CLDR territory code for the item. */
		targetCountry: Option[String] = None,
	  /** Required. The two-letter ISO 639-1 language code for the item. */
		contentLanguage: Option[String] = None,
	  /** Global Trade Item Number. */
		gtin: Option[String] = None,
	  /** Required. The current price of the item. */
		price: Option[Schema.Price] = None,
	  /** Required. The available quantity of the item. */
		quantity: Option[String] = None,
	  /** Required. The inventory timestamp, in ISO 8601 format. */
		timestamp: Option[String] = None,
	  /** Optional. Supported pickup method for this offer. Unless the value is "not supported", this field must be submitted together with `pickupSla`. For accepted attribute values, see the [local product inventory feed specification](https://support.google.com/merchants/answer/3061342). */
		pickupMethod: Option[String] = None,
	  /** Optional. Expected date that an order will be ready for pickup relative to the order date. Must be submitted together with `pickupMethod`. For accepted attribute values, see the [local product inventory feed specification](https://support.google.com/merchants/answer/3061342). */
		pickupSla: Option[String] = None
	)
	
	case class PosSale(
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#posSale`" */
		kind: Option[String] = None,
	  /** Required. The identifier of the merchant's store. Either a `storeCode` inserted through the API or the code of the store in a Business Profile. */
		storeCode: Option[String] = None,
	  /** Required. A unique identifier for the item. */
		itemId: Option[String] = None,
	  /** Required. The CLDR territory code for the item. */
		targetCountry: Option[String] = None,
	  /** Required. The two-letter ISO 639-1 language code for the item. */
		contentLanguage: Option[String] = None,
	  /** Global Trade Item Number. */
		gtin: Option[String] = None,
	  /** Required. The price of the item. */
		price: Option[Schema.Price] = None,
	  /** Required. The relative change of the available quantity. Negative for items returned. */
		quantity: Option[String] = None,
	  /** Required. The inventory timestamp, in ISO 8601 format. */
		timestamp: Option[String] = None,
	  /** A unique ID to group items from the same sale event. */
		saleId: Option[String] = None
	)
	
	case class PosCustomBatchResponse(
	  /** The result of the execution of the batch requests. */
		entries: Option[List[Schema.PosCustomBatchResponseEntry]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#posCustomBatchResponse`". */
		kind: Option[String] = None
	)
	
	case class PosCustomBatchResponseEntry(
	  /** The ID of the request entry to which this entry responds. */
		batchId: Option[Int] = None,
	  /** The retrieved or updated store information. */
		store: Option[Schema.PosStore] = None,
	  /** A list of errors defined if, and only if, the request failed. */
		errors: Option[Schema.Errors] = None,
	  /** The updated inventory information. */
		inventory: Option[Schema.PosInventory] = None,
	  /** The updated sale information. */
		sale: Option[Schema.PosSale] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#posCustomBatchResponseEntry`" */
		kind: Option[String] = None
	)
	
	case class PosInventoryRequest(
	  /** Required. The identifier of the merchant's store. Either a `storeCode` inserted through the API or the code of the store in a Business Profile. */
		storeCode: Option[String] = None,
	  /** Required. A unique identifier for the item. */
		itemId: Option[String] = None,
	  /** Required. The CLDR territory code for the item. */
		targetCountry: Option[String] = None,
	  /** Required. The two-letter ISO 639-1 language code for the item. */
		contentLanguage: Option[String] = None,
	  /** Global Trade Item Number. */
		gtin: Option[String] = None,
	  /** Required. The current price of the item. */
		price: Option[Schema.Price] = None,
	  /** Required. The available quantity of the item. */
		quantity: Option[String] = None,
	  /** Required. The inventory timestamp, in ISO 8601 format. */
		timestamp: Option[String] = None,
	  /** Optional. Supported pickup method for this offer. Unless the value is "not supported", this field must be submitted together with `pickupSla`. For accepted attribute values, see the [local product inventory feed specification](https://support.google.com/merchants/answer/3061342). */
		pickupMethod: Option[String] = None,
	  /** Optional. Expected date that an order will be ready for pickup relative to the order date. Must be submitted together with `pickupMethod`. For accepted attribute values, see the [local product inventory feed specification](https://support.google.com/merchants/answer/3061342). */
		pickupSla: Option[String] = None
	)
	
	case class PosInventoryResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#posInventoryResponse`". */
		kind: Option[String] = None,
	  /** Required. The identifier of the merchant's store. Either a `storeCode` inserted through the API or the code of the store in a Business Profile. */
		storeCode: Option[String] = None,
	  /** Required. A unique identifier for the item. */
		itemId: Option[String] = None,
	  /** Required. The CLDR territory code for the item. */
		targetCountry: Option[String] = None,
	  /** Required. The two-letter ISO 639-1 language code for the item. */
		contentLanguage: Option[String] = None,
	  /** Global Trade Item Number. */
		gtin: Option[String] = None,
	  /** Required. The current price of the item. */
		price: Option[Schema.Price] = None,
	  /** Required. The available quantity of the item. */
		quantity: Option[String] = None,
	  /** Required. The inventory timestamp, in ISO 8601 format. */
		timestamp: Option[String] = None,
	  /** Optional. Supported pickup method for this offer. Unless the value is "not supported", this field must be submitted together with `pickupSla`. For accepted attribute values, see the [local product inventory feed specification](https://support.google.com/merchants/answer/3061342). */
		pickupMethod: Option[String] = None,
	  /** Optional. Expected date that an order will be ready for pickup relative to the order date. Must be submitted together with `pickupMethod`. For accepted attribute values, see the [local product inventory feed specification](https://support.google.com/merchants/answer/3061342). */
		pickupSla: Option[String] = None
	)
	
	case class PosListResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#posListResponse`". */
		kind: Option[String] = None,
		resources: Option[List[Schema.PosStore]] = None
	)
	
	case class PosSaleRequest(
	  /** Required. The identifier of the merchant's store. Either a `storeCode` inserted through the API or the code of the store in a Business Profile. */
		storeCode: Option[String] = None,
	  /** Required. A unique identifier for the item. */
		itemId: Option[String] = None,
	  /** Required. The CLDR territory code for the item. */
		targetCountry: Option[String] = None,
	  /** Required. The two-letter ISO 639-1 language code for the item. */
		contentLanguage: Option[String] = None,
	  /** Global Trade Item Number. */
		gtin: Option[String] = None,
	  /** Required. The price of the item. */
		price: Option[Schema.Price] = None,
	  /** Required. The relative change of the available quantity. Negative for items returned. */
		quantity: Option[String] = None,
	  /** Required. The inventory timestamp, in ISO 8601 format. */
		timestamp: Option[String] = None,
	  /** A unique ID to group items from the same sale event. */
		saleId: Option[String] = None
	)
	
	case class PosSaleResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#posSaleResponse`". */
		kind: Option[String] = None,
	  /** Required. The identifier of the merchant's store. Either a `storeCode` inserted through the API or the code of the store in a Business Profile. */
		storeCode: Option[String] = None,
	  /** Required. A unique identifier for the item. */
		itemId: Option[String] = None,
	  /** Required. The CLDR territory code for the item. */
		targetCountry: Option[String] = None,
	  /** Required. The two-letter ISO 639-1 language code for the item. */
		contentLanguage: Option[String] = None,
	  /** Global Trade Item Number. */
		gtin: Option[String] = None,
	  /** Required. The price of the item. */
		price: Option[Schema.Price] = None,
	  /** Required. The relative change of the available quantity. Negative for items returned. */
		quantity: Option[String] = None,
	  /** Required. The inventory timestamp, in ISO 8601 format. */
		timestamp: Option[String] = None,
	  /** A unique ID to group items from the same sale event. */
		saleId: Option[String] = None
	)
	
	case class ProductsCustomBatchRequest(
	  /** The request entries to be processed in the batch. */
		entries: Option[List[Schema.ProductsCustomBatchRequestEntry]] = None
	)
	
	case class ProductsCustomBatchRequestEntry(
	  /** An entry ID, unique within the batch request. */
		batchId: Option[Int] = None,
	  /** The ID of the managing account. */
		merchantId: Option[String] = None,
	  /** The method of the batch entry. Acceptable values are: - "`delete`" - "`get`" - "`insert`" - "`update`"  */
		method: Option[String] = None,
	  /** The ID of the product to get or mutate. Only defined if the method is `get`, `delete`, or `update`. */
		productId: Option[String] = None,
	  /** The product to insert or update. Only required if the method is `insert` or `update`. If the `update` method is used with `updateMask` only to delete a field, then this isn't required. For example, setting `salePrice` on the `updateMask` and not providing a `product` will result in an existing sale price on the product specified by `productId` being deleted. */
		product: Option[Schema.Product] = None,
	  /** The Content API Supplemental Feed ID. If present then product insertion or deletion applies to a supplemental feed instead of primary Content API feed. */
		feedId: Option[String] = None,
	  /** The comma-separated list of product attributes to be updated. Example: `"title,salePrice"`. Attributes specified in the update mask without a value specified in the body will be deleted from the product. &#42;You must specify the update mask to delete attributes.&#42; Only top-level product attributes can be updated. If not defined, product attributes with set values will be updated and other attributes will stay unchanged. Only defined if the method is `update`. */
		updateMask: Option[String] = None
	)
	
	case class Product(
	  /** The REST ID of the product. Content API methods that operate on products take this as their `productId` parameter. The REST ID for a product has one of the 2 forms channel:contentLanguage: targetCountry: offerId or channel:contentLanguage:feedLabel: offerId. */
		id: Option[String] = None,
	  /** Required. A unique identifier for the item. Leading and trailing whitespaces are stripped and multiple whitespaces are replaced by a single whitespace upon submission. Only valid unicode characters are accepted. See the products feed specification for details. &#42;Note:&#42; Content API methods that operate on products take the REST ID of the product, &#42;not&#42; this identifier. */
		offerId: Option[String] = None,
	  /** Title of the item. */
		title: Option[String] = None,
	  /** Description of the item. */
		description: Option[String] = None,
	  /** URL directly linking to your item's page on your website. */
		link: Option[String] = None,
	  /** URL of an image of the item. */
		imageLink: Option[String] = None,
	  /** Additional URLs of images of the item. */
		additionalImageLinks: Option[List[String]] = None,
	  /** Additional URLs of lifestyle images of the item. Used to explicitly identify images that showcase your item in a real-world context. See the Help Center article for more information. */
		lifestyleImageLinks: Option[List[String]] = None,
	  /** Required. The two-letter ISO 639-1 language code for the item. */
		contentLanguage: Option[String] = None,
	  /** Required. The CLDR territory code for the item's country of sale. */
		targetCountry: Option[String] = None,
	  /** Feed label for the item. Either `targetCountry` or `feedLabel` is required. Must be less than or equal to 20 uppercase letters (A-Z), numbers (0-9), and dashes (-). */
		feedLabel: Option[String] = None,
	  /** Required. The item's channel (online or local). Acceptable values are: - "`local`" - "`online`"  */
		channel: Option[String] = None,
	  /** Date on which the item should expire, as specified upon insertion, in ISO 8601 format. The actual expiration date in Google Shopping is exposed in `productstatuses` as `googleExpirationDate` and might be earlier if `expirationDate` is too far in the future. */
		expirationDate: Option[String] = None,
	  /** The date time when an offer becomes visible in search results across Google’s YouTube surfaces, in [ISO 8601](http://en.wikipedia.org/wiki/ISO_8601) format. See [Disclosure date](https://support.google.com/merchants/answer/13034208) for more information. */
		disclosureDate: Option[String] = None,
	  /** Should be set to true if the item is targeted towards adults. */
		adult: Option[Boolean] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#product`" */
		kind: Option[String] = None,
	  /** Brand of the item. */
		brand: Option[String] = None,
	  /** Color of the item. */
		color: Option[String] = None,
	  /** Google's category of the item (see [Google product taxonomy](https://support.google.com/merchants/answer/1705911)). When querying products, this field will contain the user provided value. There is currently no way to get back the auto assigned google product categories through the API. */
		googleProductCategory: Option[String] = None,
	  /** Global Trade Item Number (GTIN) of the item. */
		gtin: Option[String] = None,
	  /** Shared identifier for all variants of the same product. */
		itemGroupId: Option[String] = None,
	  /** The material of which the item is made. */
		material: Option[String] = None,
	  /** Manufacturer Part Number (MPN) of the item. */
		mpn: Option[String] = None,
	  /** The item's pattern (for example, polka dots). */
		pattern: Option[String] = None,
	  /** Price of the item. */
		price: Option[Schema.Price] = None,
	  /** Advertised sale price of the item. */
		salePrice: Option[Schema.Price] = None,
	  /** Date range during which the item is on sale (see product data specification ). */
		salePriceEffectiveDate: Option[String] = None,
	  /** The height of the product in the units provided. The value must be between 0 (exclusive) and 3000 (inclusive). */
		productHeight: Option[Schema.ProductDimension] = None,
	  /** The length of the product in the units provided. The value must be between 0 (exclusive) and 3000 (inclusive). */
		productLength: Option[Schema.ProductDimension] = None,
	  /** The width of the product in the units provided. The value must be between 0 (exclusive) and 3000 (inclusive). */
		productWidth: Option[Schema.ProductDimension] = None,
	  /** The weight of the product in the units provided. The value must be between 0 (exclusive) and 2000 (inclusive). */
		productWeight: Option[Schema.ProductWeight] = None,
	  /** Shipping rules. */
		shipping: Option[List[Schema.ProductShipping]] = None,
	  /** Optional. Conditions to be met for a product to have free shipping. */
		freeShippingThreshold: Option[List[Schema.FreeShippingThreshold]] = None,
	  /** Weight of the item for shipping. */
		shippingWeight: Option[Schema.ProductShippingWeight] = None,
	  /** Size of the item. Only one value is allowed. For variants with different sizes, insert a separate product for each size with the same `itemGroupId` value (see size definition). */
		sizes: Option[List[String]] = None,
	  /** Tax information. */
		taxes: Option[List[Schema.ProductTax]] = None,
	  /** A list of custom (merchant-provided) attributes. It can also be used for submitting any attribute of the feed specification in its generic form (for example, `{ "name": "size type", "value": "regular" }`). This is useful for submitting attributes not explicitly exposed by the API, such as additional attributes used for Buy on Google (formerly known as Shopping Actions). */
		customAttributes: Option[List[Schema.CustomAttribute]] = None,
	  /** False when the item does not have unique product identifiers appropriate to its category, such as GTIN, MPN, and brand. Required according to the Unique Product Identifier Rules for all target countries except for Canada. */
		identifierExists: Option[Boolean] = None,
	  /** Number and amount of installments to pay for an item. */
		installment: Option[Schema.Installment] = None,
	  /** Loyalty program information that is used to surface loyalty benefits ( for example, better pricing, points, etc) to the user of this item. This signular field points to the latest uploaded loyalty program info. This field will be deprecated in the coming weeks and should not be used in favor of the plural 'LoyaltyProgram' field below. */
		loyaltyProgram: Option[Schema.LoyaltyProgram] = None,
	  /** Optional. A list of loyalty program information that is used to surface loyalty benefits (for example, better pricing, points, etc) to the user of this item. */
		loyaltyPrograms: Option[List[Schema.LoyaltyProgram]] = None,
	  /** The number of identical products in a merchant-defined multipack. */
		multipack: Option[String] = None,
	  /** Custom label 0 for custom grouping of items in a Shopping campaign. */
		customLabel0: Option[String] = None,
	  /** Custom label 1 for custom grouping of items in a Shopping campaign. */
		customLabel1: Option[String] = None,
	  /** Custom label 2 for custom grouping of items in a Shopping campaign. */
		customLabel2: Option[String] = None,
	  /** Custom label 3 for custom grouping of items in a Shopping campaign. */
		customLabel3: Option[String] = None,
	  /** Custom label 4 for custom grouping of items in a Shopping campaign. */
		customLabel4: Option[String] = None,
	  /** Whether the item is a merchant-defined bundle. A bundle is a custom grouping of different products sold by a merchant for a single price. */
		isBundle: Option[Boolean] = None,
	  /** URL for the mobile-optimized version of your item's landing page. */
		mobileLink: Option[String] = None,
	  /** The day a pre-ordered product becomes available for delivery, in ISO 8601 format. */
		availabilityDate: Option[String] = None,
	  /** The shipping label of the product, used to group product in account-level shipping rules. */
		shippingLabel: Option[String] = None,
	  /** The measure and dimension of an item. */
		unitPricingMeasure: Option[Schema.ProductUnitPricingMeasure] = None,
	  /** The preference of the denominator of the unit price. */
		unitPricingBaseMeasure: Option[Schema.ProductUnitPricingBaseMeasure] = None,
	  /** Length of the item for shipping. */
		shippingLength: Option[Schema.ProductShippingDimension] = None,
	  /** Width of the item for shipping. */
		shippingWidth: Option[Schema.ProductShippingDimension] = None,
	  /** Height of the item for shipping. */
		shippingHeight: Option[Schema.ProductShippingDimension] = None,
	  /** An identifier for an item for dynamic remarketing campaigns. */
		displayAdsId: Option[String] = None,
	  /** Advertiser-specified recommendations. */
		displayAdsSimilarIds: Option[List[String]] = None,
	  /** Title of an item for dynamic remarketing campaigns. */
		displayAdsTitle: Option[String] = None,
	  /** URL directly to your item's landing page for dynamic remarketing campaigns. */
		displayAdsLink: Option[String] = None,
	  /** Offer margin for dynamic remarketing campaigns. */
		displayAdsValue: Option[BigDecimal] = None,
	  /** The quantity of the product that is available for selling on Google. Supported only for online products. */
		sellOnGoogleQuantity: Option[String] = None,
	  /** The unique ID of a promotion. */
		promotionIds: Option[List[String]] = None,
	  /** Maximal product handling time (in business days). */
		maxHandlingTime: Option[String] = None,
	  /** Minimal product handling time (in business days). */
		minHandlingTime: Option[String] = None,
	  /** Cost of goods sold. Used for gross profit reporting. */
		costOfGoodsSold: Option[Schema.Price] = None,
	  /** A safeguard in the [Automated Discounts](//support.google.com/merchants/answer/10295759) and [Dynamic Promotions](//support.google.com/merchants/answer/13949249) projects, ensuring that discounts on merchants' offers do not fall below this value, thereby preserving the offer's value and profitability. */
		autoPricingMinPrice: Option[Schema.Price] = None,
	  /** Output only. The source of the offer, that is, how the offer was created. Acceptable values are: - "`api`" - "`crawl`" - "`feed`"  */
		source: Option[String] = None,
	  /** The list of [destinations to include](//support.google.com/merchants/answer/7501026) for this target (corresponds to checked check boxes in Merchant Center). Default destinations are always included unless provided in `excludedDestinations`. */
		includedDestinations: Option[List[String]] = None,
	  /** The list of [destinations to exclude](//support.google.com/merchants/answer/6324486) for this target (corresponds to cleared check boxes in Merchant Center). Products that are excluded from all destinations for more than 7 days are automatically deleted. */
		excludedDestinations: Option[List[String]] = None,
	  /** Used to group items in an arbitrary way. Only for CPA%, discouraged otherwise. */
		adsGrouping: Option[String] = None,
	  /** Similar to ads_grouping, but only works on CPC. */
		adsLabels: Option[List[String]] = None,
	  /** Allows advertisers to override the item URL when the product is shown within the context of Product Ads. */
		adsRedirect: Option[String] = None,
	  /** Categories of the item (formatted as in product data specification). */
		productTypes: Option[List[String]] = None,
	  /** Target age group of the item. */
		ageGroup: Option[String] = None,
	  /** Availability status of the item. */
		availability: Option[String] = None,
	  /** Condition or state of the item. */
		condition: Option[String] = None,
	  /** Target gender of the item. */
		gender: Option[String] = None,
	  /** System in which the size is specified. Recommended for apparel items. */
		sizeSystem: Option[String] = None,
	  /** The cut of the item. Recommended for apparel items. */
		sizeType: Option[String] = None,
	  /** Additional cut of the item. Used together with size_type to represent combined size types for apparel items. */
		additionalSizeType: Option[String] = None,
	  /** The energy efficiency class as defined in EU directive 2010/30/EU. */
		energyEfficiencyClass: Option[String] = None,
	  /** The energy efficiency class as defined in EU directive 2010/30/EU. */
		minEnergyEfficiencyClass: Option[String] = None,
	  /** The energy efficiency class as defined in EU directive 2010/30/EU. */
		maxEnergyEfficiencyClass: Option[String] = None,
	  /** The tax category of the product, used to configure detailed tax nexus in account-level tax settings. */
		taxCategory: Option[String] = None,
	  /** The transit time label of the product, used to group product in account-level transit time tables. */
		transitTimeLabel: Option[String] = None,
	  /** List of country codes (ISO 3166-1 alpha-2) to exclude the offer from Shopping Ads destination. Countries from this list are removed from countries configured in MC feed settings. */
		shoppingAdsExcludedCountries: Option[List[String]] = None,
	  /** The pick up option for the item. Acceptable values are: - "`buy`" - "`reserve`" - "`ship to store`" - "`not supported`"  */
		pickupMethod: Option[String] = None,
	  /** Item store pickup timeline. Acceptable values are: - "`same day`" - "`next day`" - "`2-day`" - "`3-day`" - "`4-day`" - "`5-day`" - "`6-day`" - "`7-day`" - "`multi-week`"  */
		pickupSla: Option[String] = None,
	  /** URL template for merchant hosted local storefront. */
		linkTemplate: Option[String] = None,
	  /** URL template for merchant hosted local storefront optimized for mobile devices. */
		mobileLinkTemplate: Option[String] = None,
	  /** Technical specification or additional product details. */
		productDetails: Option[List[Schema.ProductProductDetail]] = None,
	  /** Bullet points describing the most relevant highlights of a product. */
		productHighlights: Option[List[String]] = None,
	  /** Number of periods (months or years) and amount of payment per period for an item with an associated subscription contract. */
		subscriptionCost: Option[Schema.ProductSubscriptionCost] = None,
	  /** URL for the canonical version of your item's landing page. */
		canonicalLink: Option[String] = None,
	  /** Required for multi-seller accounts. Use this attribute if you're a marketplace uploading products for various sellers to your multi-seller account. */
		externalSellerId: Option[String] = None,
	  /** Publication of this item should be temporarily paused. Acceptable values are: - "`ads`"  */
		pause: Option[String] = None,
	  /** URL of the 3D model of the item to provide more visuals. */
		virtualModelLink: Option[String] = None,
	  /** Extra fields to export to the Cloud Retail program. */
		cloudExportAdditionalProperties: Option[List[Schema.CloudExportAdditionalProperties]] = None,
	  /** Product [certification](https://support.google.com/merchants/answer/13528839), introduced for EU energy efficiency labeling compliance using the [EU EPREL](https://eprel.ec.europa.eu/screen/home) database. */
		certifications: Option[List[Schema.ProductCertification]] = None,
	  /** Structured title, for algorithmically (AI)-generated titles. */
		structuredTitle: Option[Schema.ProductStructuredTitle] = None,
	  /** Structured description, for algorithmically (AI)-generated descriptions. */
		structuredDescription: Option[Schema.ProductStructuredDescription] = None
	)
	
	case class ProductDimension(
	  /** Required. The length value represented as a number. The value can have a maximum precision of four decimal places. */
		value: Option[BigDecimal] = None,
	  /** Required. The length units. Acceptable values are: - "`in`" - "`cm`"  */
		unit: Option[String] = None
	)
	
	case class ProductWeight(
	  /** Required. The weight represented as a number. The weight can have a maximum precision of four decimal places. */
		value: Option[BigDecimal] = None,
	  /** Required. The weight unit. Acceptable values are: - "`g`" - "`kg`" - "`oz`" - "`lb`"  */
		unit: Option[String] = None
	)
	
	case class ProductShipping(
	  /** Fixed shipping price, represented as a number. */
		price: Option[Schema.Price] = None,
	  /** The CLDR territory code of the country to which an item will ship. */
		country: Option[String] = None,
	  /** The geographic region to which a shipping rate applies. */
		region: Option[String] = None,
	  /** A free-form description of the service class or delivery speed. */
		service: Option[String] = None,
	  /** The numeric ID of a location that the shipping rate applies to as defined in the Google Ads API. */
		locationId: Option[String] = None,
	  /** The location where the shipping is applicable, represented by a location group name. */
		locationGroupName: Option[String] = None,
	  /** The postal code range that the shipping rate applies to, represented by a postal code, a postal code prefix followed by a &#42; wildcard, a range between two postal codes or two postal code prefixes of equal length. */
		postalCode: Option[String] = None,
	  /** Minimum handling time (inclusive) between when the order is received and shipped in business days. 0 means that the order is shipped on the same day as it's received if it happens before the cut-off time. minHandlingTime can only be present together with maxHandlingTime; but it's not required if maxHandlingTime is present. */
		minHandlingTime: Option[String] = None,
	  /** Maximum handling time (inclusive) between when the order is received and shipped in business days. 0 means that the order is shipped on the same day as it's received if it happens before the cut-off time. Both maxHandlingTime and maxTransitTime are required if providing shipping speeds. */
		maxHandlingTime: Option[String] = None,
	  /** Minimum transit time (inclusive) between when the order has shipped and when it's delivered in business days. 0 means that the order is delivered on the same day as it ships. minTransitTime can only be present together with maxTransitTime; but it's not required if maxTransitTime is present. */
		minTransitTime: Option[String] = None,
	  /** Maximum transit time (inclusive) between when the order has shipped and when it's delivered in business days. 0 means that the order is delivered on the same day as it ships. Both maxHandlingTime and maxTransitTime are required if providing shipping speeds. */
		maxTransitTime: Option[String] = None
	)
	
	case class FreeShippingThreshold(
	  /** Required. The [CLDR territory code](http://www.unicode.org/repos/cldr/tags/latest/common/main/en.xml) of the country to which an item will ship. */
		country: Option[String] = None,
	  /** Required. The minimum product price for the shipping cost to become free. Represented as a number. */
		priceThreshold: Option[Schema.Price] = None
	)
	
	case class ProductShippingWeight(
	  /** The weight of the product used to calculate the shipping cost of the item. */
		value: Option[BigDecimal] = None,
	  /** The unit of value. */
		unit: Option[String] = None
	)
	
	case class ProductTax(
	  /** The percentage of tax rate that applies to the item price. */
		rate: Option[BigDecimal] = None,
	  /** The country within which the item is taxed, specified as a CLDR territory code. */
		country: Option[String] = None,
	  /** The geographic region to which the tax rate applies. */
		region: Option[String] = None,
	  /** Should be set to true if tax is charged on shipping. */
		taxShip: Option[Boolean] = None,
	  /** The numeric ID of a location that the tax rate applies to as defined in the Google Ads API. */
		locationId: Option[String] = None,
	  /** The postal code range that the tax rate applies to, represented by a ZIP code, a ZIP code prefix using &#42; wildcard, a range between two ZIP codes or two ZIP code prefixes of equal length. Examples: 94114, 94&#42;, 94002-95460, 94&#42;-95&#42;. */
		postalCode: Option[String] = None
	)
	
	case class Installment(
	  /** The number of installments the buyer has to pay. */
		months: Option[String] = None,
	  /** The amount the buyer has to pay per month. */
		amount: Option[Schema.Price] = None,
	  /** Optional. The initial down payment amount the buyer has to pay. */
		downpayment: Option[Schema.Price] = None,
	  /** Optional. Type of installment payments. Supported values are: - "`finance`" - "`lease`"  */
		creditType: Option[String] = None
	)
	
	case class LoyaltyProgram(
	  /** Required. The label of the loyalty program. This is an internal label that uniquely identifies the relationship between a merchant entity and a loyalty program entity. It must be provided so that system can associate the assets below (for example, price and points) with a merchant. The corresponding program must be linked to the merchant account. */
		programLabel: Option[String] = None,
	  /** Required. The label of the tier within the loyalty program. Must match one of the labels within the program. */
		tierLabel: Option[String] = None,
	  /** Optional. The price for members of the given tier (instant discount price). Must be smaller or equal to the regular price. */
		price: Option[Schema.Price] = None,
	  /** Optional. The cashback that can be used for future purchases. */
		cashbackForFutureUse: Option[Schema.Price] = None,
	  /** Optional. The amount of loyalty points earned on a purchase. */
		loyaltyPoints: Option[String] = None,
	  /** Optional. A date range during which the item is eligible for member price. If not specified, the member price is always applicable. The date range is represented by a pair of ISO 8601 dates separated by a space, comma, or slash. */
		memberPriceEffectiveDate: Option[String] = None
	)
	
	case class ProductUnitPricingMeasure(
	  /** The measure of an item. */
		value: Option[BigDecimal] = None,
	  /** The unit of the measure. */
		unit: Option[String] = None
	)
	
	case class ProductUnitPricingBaseMeasure(
	  /** The denominator of the unit price. */
		value: Option[String] = None,
	  /** The unit of the denominator. */
		unit: Option[String] = None
	)
	
	case class ProductShippingDimension(
	  /** The dimension of the product used to calculate the shipping cost of the item. */
		value: Option[BigDecimal] = None,
	  /** The unit of value. */
		unit: Option[String] = None
	)
	
	case class ProductProductDetail(
	  /** The section header used to group a set of product details. */
		sectionName: Option[String] = None,
	  /** The name of the product detail. */
		attributeName: Option[String] = None,
	  /** The value of the product detail. */
		attributeValue: Option[String] = None
	)
	
	case class ProductSubscriptionCost(
	  /** The type of subscription period. - "`month`" - "`year`"  */
		period: Option[String] = None,
	  /** The number of subscription periods the buyer has to pay. */
		periodLength: Option[String] = None,
	  /** The amount the buyer has to pay per subscription period. */
		amount: Option[Schema.Price] = None
	)
	
	case class CloudExportAdditionalProperties(
	  /** Name of the given property. For example, "Screen-Resolution" for a TV product. Maximum string size is 256 characters. */
		propertyName: Option[String] = None,
	  /** Text value of the given property. For example, "8K(UHD)" could be a text value for a TV product. Maximum number of specified values for this field is 400. Values are stored in an arbitrary but consistent order. Maximum string size is 256 characters. */
		textValue: Option[List[String]] = None,
	  /** Boolean value of the given property. For example for a TV product, "True" or "False" if the screen is UHD. */
		boolValue: Option[Boolean] = None,
	  /** Integer values of the given property. For example, 1080 for a screen resolution of a TV product. Maximum number of specified values for this field is 400. Values are stored in an arbitrary but consistent order. */
		intValue: Option[List[String]] = None,
	  /** Float values of the given property. For example for a TV product 1.2345. Maximum number of specified values for this field is 400. Values are stored in an arbitrary but consistent order. */
		floatValue: Option[List[BigDecimal]] = None,
	  /** Minimum float value of the given property. For example for a TV product 1.00. */
		minValue: Option[BigDecimal] = None,
	  /** Maximum float value of the given property. For example for a TV product 100.00. */
		maxValue: Option[BigDecimal] = None,
	  /** Unit of the given property. For example, "Pixels" for a TV product. Maximum string size is 256 bytes. */
		unitCode: Option[String] = None
	)
	
	case class ProductCertification(
	  /** The certification authority, for example "European_Commission". Maximum length is 2000 characters. */
		certificationAuthority: Option[String] = None,
	  /** The name of the certification, for example "EPREL". Maximum length is 2000 characters. */
		certificationName: Option[String] = None,
	  /** The certification code, for eaxample "123456". Maximum length is 2000 characters. */
		certificationCode: Option[String] = None,
	  /** The certification value (also known as class, level or grade), for example "A+", "C", "gold". Maximum length is 2000 characters. */
		certificationValue: Option[String] = None
	)
	
	case class ProductStructuredTitle(
	  /** Optional. The digital source type. Acceptable values are: - "`trained_algorithmic_media`" - "`default`"  */
		digitalSourceType: Option[String] = None,
	  /** Required. The title text. Maximum length is 150 characters. */
		content: Option[String] = None
	)
	
	case class ProductStructuredDescription(
	  /** Optional. The digital source type. Acceptable values are: - "`trained_algorithmic_media`" - "`default`"  */
		digitalSourceType: Option[String] = None,
	  /** Required. The description text. Maximum length is 5000 characters. */
		content: Option[String] = None
	)
	
	case class ProductsCustomBatchResponse(
	  /** The result of the execution of the batch requests. */
		entries: Option[List[Schema.ProductsCustomBatchResponseEntry]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#productsCustomBatchResponse`". */
		kind: Option[String] = None
	)
	
	case class ProductsCustomBatchResponseEntry(
	  /** The ID of the request entry this entry responds to. */
		batchId: Option[Int] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#productsCustomBatchResponseEntry`" */
		kind: Option[String] = None,
	  /** The inserted product. Only defined if the method is `insert` and if the request was successful. */
		product: Option[Schema.Product] = None,
	  /** A list of errors for failed custombatch entries. &#42;Note:&#42; Schema errors fail the whole request. */
		errors: Option[Schema.Errors] = None
	)
	
	case class ProductsListResponse(
	  /** The token for the retrieval of the next page of products. */
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#productsListResponse`". */
		kind: Option[String] = None,
		resources: Option[List[Schema.Product]] = None
	)
	
	case class ProductstatusesCustomBatchRequest(
	  /** The request entries to be processed in the batch. */
		entries: Option[List[Schema.ProductstatusesCustomBatchRequestEntry]] = None
	)
	
	case class ProductstatusesCustomBatchRequestEntry(
	  /** An entry ID, unique within the batch request. */
		batchId: Option[Int] = None,
	  /** The ID of the managing account. */
		merchantId: Option[String] = None,
	  /** The method of the batch entry. Acceptable values are: - "`get`"  */
		method: Option[String] = None,
	  /** The ID of the product whose status to get. */
		productId: Option[String] = None,
	  /** Deprecated: Setting this field has no effect and attributes are never included. */
		includeAttributes: Option[Boolean] = None,
	  /** If set, only issues for the specified destinations are returned, otherwise only issues for the Shopping destination. */
		destinations: Option[List[String]] = None
	)
	
	case class ProductstatusesCustomBatchResponse(
	  /** The result of the execution of the batch requests. */
		entries: Option[List[Schema.ProductstatusesCustomBatchResponseEntry]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#productstatusesCustomBatchResponse`". */
		kind: Option[String] = None
	)
	
	case class ProductstatusesCustomBatchResponseEntry(
	  /** The ID of the request entry this entry responds to. */
		batchId: Option[Int] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#productstatusesCustomBatchResponseEntry`" */
		kind: Option[String] = None,
	  /** The requested product status. Only defined if the request was successful. */
		productStatus: Option[Schema.ProductStatus] = None,
	  /** A list of errors for failed custombatch entries. &#42;Note:&#42; Schema errors fail the whole request. */
		errors: Option[Schema.Errors] = None
	)
	
	case class ProductStatus(
	  /** The ID of the product for which status is reported. */
		productId: Option[String] = None,
	  /** The title of the product. */
		title: Option[String] = None,
	  /** The link to the product. */
		link: Option[String] = None,
	  /** The intended destinations for the product. */
		destinationStatuses: Option[List[Schema.ProductStatusDestinationStatus]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#productStatus`" */
		kind: Option[String] = None,
	  /** Date on which the item has been created, in ISO 8601 format. */
		creationDate: Option[String] = None,
	  /** Date on which the item has been last updated, in ISO 8601 format. */
		lastUpdateDate: Option[String] = None,
	  /** Date on which the item expires in Google Shopping, in ISO 8601 format. */
		googleExpirationDate: Option[String] = None,
	  /** A list of all issues associated with the product. */
		itemLevelIssues: Option[List[Schema.ProductStatusItemLevelIssue]] = None
	)
	
	case class ProductStatusDestinationStatus(
	  /** The name of the destination */
		destination: Option[String] = None,
	  /** Deprecated. Destination approval status in `targetCountry` of the offer. */
		status: Option[String] = None,
	  /** List of country codes (ISO 3166-1 alpha-2) where the offer is approved. */
		approvedCountries: Option[List[String]] = None,
	  /** List of country codes (ISO 3166-1 alpha-2) where the offer is pending approval. */
		pendingCountries: Option[List[String]] = None,
	  /** List of country codes (ISO 3166-1 alpha-2) where the offer is disapproved. */
		disapprovedCountries: Option[List[String]] = None
	)
	
	case class ProductStatusItemLevelIssue(
	  /** The error code of the issue. */
		code: Option[String] = None,
	  /** How this issue affects serving of the offer. */
		servability: Option[String] = None,
	  /** Whether the issue can be resolved by the merchant. */
		resolution: Option[String] = None,
	  /** The attribute's name, if the issue is caused by a single attribute. */
		attributeName: Option[String] = None,
	  /** The destination the issue applies to. */
		destination: Option[String] = None,
	  /** A short issue description in English. */
		description: Option[String] = None,
	  /** A detailed issue description in English. */
		detail: Option[String] = None,
	  /** The URL of a web page to help with resolving this issue. */
		documentation: Option[String] = None,
	  /** List of country codes (ISO 3166-1 alpha-2) where issue applies to the offer. */
		applicableCountries: Option[List[String]] = None
	)
	
	case class ProductstatusesListResponse(
	  /** The token for the retrieval of the next page of products statuses. */
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#productstatusesListResponse`". */
		kind: Option[String] = None,
		resources: Option[List[Schema.ProductStatus]] = None
	)
	
	case class PubsubNotificationSettings(
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#pubsubNotificationSettings`" */
		kind: Option[String] = None,
	  /** Cloud pub/sub topic to which notifications are sent (read-only). */
		cloudTopicName: Option[String] = None,
	  /** List of event types. Acceptable values are: - "`orderPendingShipment`"  */
		registeredEvents: Option[List[String]] = None
	)
	
	case class RegionalinventoryCustomBatchRequest(
	  /** The request entries to be processed in the batch. */
		entries: Option[List[Schema.RegionalinventoryCustomBatchRequestEntry]] = None
	)
	
	case class RegionalinventoryCustomBatchRequestEntry(
	  /** An entry ID, unique within the batch request. */
		batchId: Option[Int] = None,
	  /** The ID of the managing account. */
		merchantId: Option[String] = None,
	  /** Method of the batch request entry. Acceptable values are: - "`insert`"  */
		method: Option[String] = None,
	  /** The ID of the product for which to update price and availability. */
		productId: Option[String] = None,
	  /** Price and availability of the product. */
		regionalInventory: Option[Schema.RegionalInventory] = None
	)
	
	case class RegionalInventory(
	  /** The ID uniquely identifying each region. */
		regionId: Option[String] = None,
	  /** The price of the product. */
		price: Option[Schema.Price] = None,
	  /** The sale price of the product. Mandatory if `sale_price_effective_date` is defined. */
		salePrice: Option[Schema.Price] = None,
	  /** A date range represented by a pair of ISO 8601 dates separated by a space, comma, or slash. Both dates might be specified as 'null' if undecided. */
		salePriceEffectiveDate: Option[String] = None,
	  /** The availability of the product. */
		availability: Option[String] = None,
	  /** A list of custom (merchant-provided) attributes. It can also be used for submitting any attribute of the feed specification in its generic form. */
		customAttributes: Option[List[Schema.CustomAttribute]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#regionalInventory`". */
		kind: Option[String] = None
	)
	
	case class RegionalinventoryCustomBatchResponse(
	  /** The result of the execution of the batch requests. */
		entries: Option[List[Schema.RegionalinventoryCustomBatchResponseEntry]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#regionalinventoryCustomBatchResponse`". */
		kind: Option[String] = None
	)
	
	case class RegionalinventoryCustomBatchResponseEntry(
	  /** The ID of the request entry this entry responds to. */
		batchId: Option[Int] = None,
	  /** A list of errors for failed custombatch entries. &#42;Note:&#42; Schema errors fail the whole request. */
		errors: Option[Schema.Errors] = None,
	  /** Price and availability of the product. */
		regionalInventory: Option[Schema.RegionalInventory] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#regionalinventoryCustomBatchResponseEntry`". */
		kind: Option[String] = None
	)
	
	case class ReturnaddressCustomBatchRequest(
	  /** The request entries to be processed in the batch. */
		entries: Option[List[Schema.ReturnaddressCustomBatchRequestEntry]] = None
	)
	
	case class ReturnaddressCustomBatchRequestEntry(
	  /** An entry ID, unique within the batch request. */
		batchId: Option[Int] = None,
	  /** The Merchant Center account ID. */
		merchantId: Option[String] = None,
	  /** Method of the batch request entry. Acceptable values are: - "`delete`" - "`get`" - "`insert`"  */
		method: Option[String] = None,
	  /** The return address ID. This should be set only if the method is `delete` or `get`. */
		returnAddressId: Option[String] = None,
	  /** The return address to submit. This should be set only if the method is `insert`. */
		returnAddress: Option[Schema.ReturnAddress] = None
	)
	
	case class ReturnAddress(
	  /** Return address ID generated by Google. */
		returnAddressId: Option[String] = None,
	  /** Required. The user-defined label of the return address. For the default address, use the label "default". */
		label: Option[String] = None,
	  /** Required. The country of sale where the return address is applicable. */
		country: Option[String] = None,
	  /** Required. The address. */
		address: Option[Schema.ReturnAddressAddress] = None,
	  /** Required. The merchant's contact phone number regarding the return. */
		phoneNumber: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#returnAddress`" */
		kind: Option[String] = None
	)
	
	case class ReturnAddressAddress(
	  /** Name of the recipient to address returns to. */
		recipientName: Option[String] = None,
	  /** Street-level part of the address. May be up to two lines, each line specified as an array element. */
		streetAddress: Option[List[String]] = None,
	  /** City, town or commune. May also include dependent localities or sublocalities (for example, neighborhoods or suburbs). */
		locality: Option[String] = None,
	  /** Top-level administrative subdivision of the country. For example, a state like California ("CA") or a province like Quebec ("QC"). */
		region: Option[String] = None,
	  /** Postal code or ZIP (for example, "94043"). */
		postalCode: Option[String] = None,
	  /** CLDR country code (for example, "US"). */
		country: Option[String] = None
	)
	
	case class ReturnaddressCustomBatchResponse(
	  /** The result of the execution of the batch requests. */
		entries: Option[List[Schema.ReturnaddressCustomBatchResponseEntry]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#returnaddressCustomBatchResponse`". */
		kind: Option[String] = None
	)
	
	case class ReturnaddressCustomBatchResponseEntry(
	  /** The ID of the request entry to which this entry responds. */
		batchId: Option[Int] = None,
	  /** The retrieved return address. */
		returnAddress: Option[Schema.ReturnAddress] = None,
	  /** A list of errors defined if, and only if, the request failed. */
		errors: Option[Schema.Errors] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#returnaddressCustomBatchResponseEntry`" */
		kind: Option[String] = None
	)
	
	case class ReturnaddressListResponse(
	  /** The token for the retrieval of the next page of addresses. */
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#returnaddressListResponse`". */
		kind: Option[String] = None,
		resources: Option[List[Schema.ReturnAddress]] = None
	)
	
	case class ReturnpolicyCustomBatchRequest(
	  /** The request entries to be processed in the batch. */
		entries: Option[List[Schema.ReturnpolicyCustomBatchRequestEntry]] = None
	)
	
	case class ReturnpolicyCustomBatchRequestEntry(
	  /** An entry ID, unique within the batch request. */
		batchId: Option[Int] = None,
	  /** The Merchant Center account ID. */
		merchantId: Option[String] = None,
	  /** Method of the batch request entry. Acceptable values are: - "`delete`" - "`get`" - "`insert`"  */
		method: Option[String] = None,
	  /** The return policy ID. This should be set only if the method is `delete` or `get`. */
		returnPolicyId: Option[String] = None,
	  /** The return policy to submit. This should be set only if the method is `insert`. */
		returnPolicy: Option[Schema.ReturnPolicy] = None
	)
	
	case class ReturnPolicy(
	  /** Return policy ID generated by Google. */
		returnPolicyId: Option[String] = None,
	  /** Required. The user-defined label of the return policy. For the default policy, use the label "default". */
		label: Option[String] = None,
	  /** Required. The country of sale where the return policy is applicable. */
		country: Option[String] = None,
	  /** Required. The name of the policy as shown in Merchant Center. */
		name: Option[String] = None,
	  /** Required. The policy. */
		policy: Option[Schema.ReturnPolicyPolicy] = None,
	  /** An optional list of seasonal overrides. */
		seasonalOverrides: Option[List[Schema.ReturnPolicySeasonalOverride]] = None,
	  /** Return reasons that will incur return fees. */
		nonFreeReturnReasons: Option[List[String]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#returnPolicy`" */
		kind: Option[String] = None,
	  /** The return shipping fee that will apply to non free return reasons. */
		returnShippingFee: Option[Schema.Price] = None
	)
	
	case class ReturnPolicyPolicy(
	  /** Policy type. Use "lastReturnDate" for seasonal overrides only. Note that for most items a minimum of 30 days is required for returns. Exceptions may be made for electronics or non-returnable items such as food, perishables, and living things. A policy of less than 30 days can only be applied to those items. Acceptable values are: - "`lastReturnDate`" - "`lifetimeReturns`" - "`noReturns`" - "`numberOfDaysAfterDelivery`"  */
		`type`: Option[String] = None,
	  /** The number of days items can be returned after delivery, where one day is defined to be 24 hours after the delivery timestamp. When specifying the return window like this, set the policy type to "numberOfDaysAfterDelivery". Acceptable values are 30, 45, 60, 90, 100, 180, 270 and 365 for the default policy. Additional policies further allow 14, 15, 21 and 28 days, but note that for most items a minimum of 30 days is required for returns. Exceptions may be made for electronics. A policy of less than 30 days can only be applied to those items. */
		numberOfDays: Option[String] = None,
	  /** Required. Last day for returning the items. In ISO 8601 format. When specifying the return window like this, set the policy type to "lastReturnDate". Use this for seasonal overrides only. */
		lastReturnDate: Option[String] = None
	)
	
	case class ReturnPolicySeasonalOverride(
	  /** Required. The name of the seasonal override as shown in Merchant Center. */
		name: Option[String] = None,
	  /** Required. First day on which the override applies. In ISO 8601 format. */
		startDate: Option[String] = None,
	  /** Required. Last day on which the override applies. In ISO 8601 format. */
		endDate: Option[String] = None,
	  /** Required. The policy which is in effect during that time. */
		policy: Option[Schema.ReturnPolicyPolicy] = None
	)
	
	case class ReturnpolicyCustomBatchResponse(
	  /** The result of the execution of the batch requests. */
		entries: Option[List[Schema.ReturnpolicyCustomBatchResponseEntry]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#returnpolicyCustomBatchResponse`". */
		kind: Option[String] = None
	)
	
	case class ReturnpolicyCustomBatchResponseEntry(
	  /** The ID of the request entry to which this entry responds. */
		batchId: Option[Int] = None,
	  /** The retrieved return policy. */
		returnPolicy: Option[Schema.ReturnPolicy] = None,
	  /** A list of errors defined if, and only if, the request failed. */
		errors: Option[Schema.Errors] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#returnpolicyCustomBatchResponseEntry`" */
		kind: Option[String] = None
	)
	
	case class ReturnpolicyListResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#returnpolicyListResponse`". */
		kind: Option[String] = None,
		resources: Option[List[Schema.ReturnPolicy]] = None
	)
	
	case class ShippingsettingsCustomBatchRequest(
	  /** The request entries to be processed in the batch. */
		entries: Option[List[Schema.ShippingsettingsCustomBatchRequestEntry]] = None
	)
	
	case class ShippingsettingsCustomBatchRequestEntry(
	  /** An entry ID, unique within the batch request. */
		batchId: Option[Int] = None,
	  /** The ID of the managing account. */
		merchantId: Option[String] = None,
	  /** The method of the batch entry. Acceptable values are: - "`get`" - "`update`"  */
		method: Option[String] = None,
	  /** The ID of the account for which to get/update account shipping settings. */
		accountId: Option[String] = None,
	  /** The account shipping settings to update. Only defined if the method is `update`. */
		shippingSettings: Option[Schema.ShippingSettings] = None
	)
	
	case class ShippingSettings(
	  /** The ID of the account to which these account shipping settings belong. Ignored upon update, always present in get request responses. */
		accountId: Option[String] = None,
	  /** The target account's list of services. Optional. */
		services: Option[List[Schema.Service]] = None,
	  /** A list of postal code groups that can be referred to in `services`. Optional. */
		postalCodeGroups: Option[List[Schema.PostalCodeGroup]] = None,
	  /** Optional. A list of warehouses which can be referred to in `services`. */
		warehouses: Option[List[Schema.Warehouse]] = None
	)
	
	case class Service(
	  /** Free-form name of the service. Must be unique within target account. Required. */
		name: Option[String] = None,
	  /** The CLDR territory code of the country to which the service applies. Required. */
		deliveryCountry: Option[String] = None,
	  /** The CLDR code of the currency to which this service applies. Must match that of the prices in rate groups. */
		currency: Option[String] = None,
	  /** Time spent in various aspects from order to the delivery of the product. Required. */
		deliveryTime: Option[Schema.DeliveryTime] = None,
	  /** Shipping rate group definitions. Only the last one is allowed to have an empty `applicableShippingLabels`, which means "everything else". The other `applicableShippingLabels` must not overlap. */
		rateGroups: Option[List[Schema.RateGroup]] = None,
	  /** A boolean exposing the active status of the shipping service. Required. */
		active: Option[Boolean] = None,
	  /** Minimum order value for this service. If set, indicates that customers will have to spend at least this amount. All prices within a service must have the same currency. Cannot be set together with minimum_order_value_table. */
		minimumOrderValue: Option[Schema.Price] = None,
	  /** Eligibility for this service. Acceptable values are: - "`All scenarios`" - "`All scenarios except Shopping Actions`" - "`Shopping Actions`"  */
		eligibility: Option[String] = None,
	  /** Type of locations this service ships orders to. Acceptable values are: - "`delivery`" - "`pickup` (deprecated)" - "`local_delivery`" - "`collection_point`"  */
		shipmentType: Option[String] = None,
	  /** The carrier-service pair delivering items to collection points. The list of supported pickup services can be retrieved through the `getSupportedPickupServices` method. Required if and only if the service delivery type is `pickup`. */
		pickupService: Option[Schema.PickupCarrierService] = None,
	  /** Table of per store minimum order values for the pickup fulfillment type. Cannot be set together with minimum_order_value. */
		minimumOrderValueTable: Option[Schema.MinimumOrderValueTable] = None,
	  /** A list of stores your products are delivered from. This is only available for the local delivery shipment type. */
		storeConfig: Option[Schema.ServiceStoreConfig] = None
	)
	
	case class DeliveryTime(
	  /** Minimum number of business days that are spent in transit. 0 means same day delivery, 1 means next day delivery. Either `{min,max}TransitTimeInDays` or `transitTimeTable` must be set, but not both. */
		minTransitTimeInDays: Option[Int] = None,
	  /** Maximum number of business days that are spent in transit. 0 means same day delivery, 1 means next day delivery. Must be greater than or equal to `minTransitTimeInDays`. */
		maxTransitTimeInDays: Option[Int] = None,
	  /** Holiday cutoff definitions. If configured, they specify order cutoff times for holiday-specific shipping. */
		holidayCutoffs: Option[List[Schema.HolidayCutoff]] = None,
	  /** Business days cutoff time definition. If not configured, the cutoff time will be defaulted to 8AM PST. If local delivery, use Service.StoreConfig.CutoffConfig. */
		cutoffTime: Option[Schema.CutoffTime] = None,
	  /** Minimum number of business days spent before an order is shipped. 0 means same day shipped, 1 means next day shipped. */
		minHandlingTimeInDays: Option[Int] = None,
	  /** Maximum number of business days spent before an order is shipped. 0 means same day shipped, 1 means next day shipped. Must be greater than or equal to `minHandlingTimeInDays`. */
		maxHandlingTimeInDays: Option[Int] = None,
	  /** Transit time table, number of business days spent in transit based on row and column dimensions. Either `{min,max}TransitTimeInDays` or `transitTimeTable` can be set, but not both. */
		transitTimeTable: Option[Schema.TransitTable] = None,
	  /** The business days during which orders can be handled. If not provided, Monday to Friday business days will be assumed. */
		handlingBusinessDayConfig: Option[Schema.BusinessDayConfig] = None,
	  /** The business days during which orders can be in-transit. If not provided, Monday to Friday business days will be assumed. */
		transitBusinessDayConfig: Option[Schema.BusinessDayConfig] = None,
	  /** Indicates that the delivery time should be calculated per warehouse (shipping origin location) based on the settings of the selected carrier. When set, no other transit time related field in DeliveryTime should be set. */
		warehouseBasedDeliveryTimes: Option[List[Schema.WarehouseBasedDeliveryTime]] = None
	)
	
	case class HolidayCutoff(
	  /** Unique identifier for the holiday. Required. */
		holidayId: Option[String] = None,
	  /** Date of the order deadline, in ISO 8601 format. For example, "2016-11-29" for 29th November 2016. Required. */
		deadlineDate: Option[String] = None,
	  /** Hour of the day on the deadline date until which the order has to be placed to qualify for the delivery guarantee. Possible values are: 0 (midnight), 1, ..., 12 (noon), 13, ..., 23. Required. */
		deadlineHour: Option[Int] = None,
	  /** Timezone identifier for the deadline hour (for example, "Europe/Zurich"). List of identifiers. Required. */
		deadlineTimezone: Option[String] = None,
	  /** Date on which the deadline will become visible to consumers in ISO 8601 format. For example, "2016-10-31" for 31st October 2016. Required. */
		visibleFromDate: Option[String] = None
	)
	
	case class CutoffTime(
	  /** Hour of the cutoff time until which an order has to be placed to be processed in the same day. Required. */
		hour: Option[Int] = None,
	  /** Minute of the cutoff time until which an order has to be placed to be processed in the same day. Required. */
		minute: Option[Int] = None,
	  /** Timezone identifier for the cutoff time (for example, "Europe/Zurich"). List of identifiers. Required. */
		timezone: Option[String] = None
	)
	
	case class TransitTable(
	  /** A list of postal group names. The last value can be `"all other locations"`. Example: `["zone 1", "zone 2", "all other locations"]`. The referred postal code groups must match the delivery country of the service. */
		postalCodeGroupNames: Option[List[String]] = None,
	  /** A list of transit time labels. The last value can be `"all other labels"`. Example: `["food", "electronics", "all other labels"]`. */
		transitTimeLabels: Option[List[String]] = None,
		rows: Option[List[Schema.TransitTableTransitTimeRow]] = None
	)
	
	case class TransitTableTransitTimeRow(
		values: Option[List[Schema.TransitTableTransitTimeRowTransitTimeValue]] = None
	)
	
	case class TransitTableTransitTimeRowTransitTimeValue(
	  /** Transit time range (min-max) in business days. 0 means same day delivery, 1 means next day delivery. */
		minTransitTimeInDays: Option[Int] = None,
	  /** Must be greater than or equal to `minTransitTimeInDays`. */
		maxTransitTimeInDays: Option[Int] = None
	)
	
	case class BusinessDayConfig(
	  /** Regular business days, such as '"monday"'. May not be empty. */
		businessDays: Option[List[String]] = None
	)
	
	case class WarehouseBasedDeliveryTime(
	  /** Required. Carrier, such as `"UPS"` or `"Fedex"`. The list of supported carriers can be retrieved through the `listSupportedCarriers` method. */
		carrier: Option[String] = None,
	  /** Required. Carrier service, such as `"ground"` or `"2 days"`. The list of supported services for a carrier can be retrieved through the `listSupportedCarriers` method. The name of the service must be in the eddSupportedServices list. */
		carrierService: Option[String] = None,
	  /** The name of the warehouse. Warehouse name need to be matched with name. If warehouseName is set, the below fields will be ignored. The warehouse info will be read from warehouse. */
		warehouseName: Option[String] = None,
	  /** Shipping origin. */
		originPostalCode: Option[String] = None,
	  /** Shipping origin's street address. */
		originStreetAddress: Option[String] = None,
	  /** Shipping origin's city. */
		originCity: Option[String] = None,
	  /** Shipping origin's state. */
		originAdministrativeArea: Option[String] = None,
	  /** Shipping origin's country represented as a [CLDR territory code](https://github.com/unicode-org/cldr/blob/latest/common/main/en.xml). */
		originCountry: Option[String] = None
	)
	
	case class RateGroup(
	  /** A list of shipping labels defining the products to which this rate group applies to. This is a disjunction: only one of the labels has to match for the rate group to apply. May only be empty for the last rate group of a service. Required. */
		applicableShippingLabels: Option[List[String]] = None,
	  /** The value of the rate group (for example, flat rate $10). Can only be set if `mainTable` and `subtables` are not set. */
		singleValue: Option[Schema.Value] = None,
	  /** A table defining the rate group, when `singleValue` is not expressive enough. Can only be set if `singleValue` is not set. */
		mainTable: Option[Schema.Table] = None,
	  /** A list of subtables referred to by `mainTable`. Can only be set if `mainTable` is set. */
		subtables: Option[List[Schema.Table]] = None,
	  /** A list of carrier rates that can be referred to by `mainTable` or `singleValue`. */
		carrierRates: Option[List[Schema.CarrierRate]] = None,
	  /** Name of the rate group. Optional. If set has to be unique within shipping service. */
		name: Option[String] = None
	)
	
	case class Value(
	  /** If true, then the product can't ship. Must be true when set, can only be set if all other fields are not set. */
		noShipping: Option[Boolean] = None,
	  /** A flat rate. Can only be set if all other fields are not set. */
		flatRate: Option[Schema.Price] = None,
	  /** A percentage of the price represented as a number in decimal notation (for example, `"5.4"`). Can only be set if all other fields are not set. */
		pricePercentage: Option[String] = None,
	  /** The name of a carrier rate referring to a carrier rate defined in the same rate group. Can only be set if all other fields are not set. */
		carrierRateName: Option[String] = None,
	  /** The name of a subtable. Can only be set in table cells (not for single values), and only if all other fields are not set. */
		subtableName: Option[String] = None
	)
	
	case class Table(
	  /** Name of the table. Required for subtables, ignored for the main table. */
		name: Option[String] = None,
	  /** Headers of the table's rows. Required. */
		rowHeaders: Option[Schema.Headers] = None,
	  /** Headers of the table's columns. Optional: if not set then the table has only one dimension. */
		columnHeaders: Option[Schema.Headers] = None,
	  /** The list of rows that constitute the table. Must have the same length as `rowHeaders`. Required. */
		rows: Option[List[Schema.Row]] = None
	)
	
	case class Headers(
	  /** A list of inclusive order price upper bounds. The last price's value can be `"infinity"`. For example `[{"value": "10", "currency": "USD"}, {"value": "500", "currency": "USD"}, {"value": "infinity", "currency": "USD"}]` represents the headers "<= $10", "<= $500", and "> $500". All prices within a service must have the same currency. Must be non-empty. Can only be set if all other fields are not set. */
		prices: Option[List[Schema.Price]] = None,
	  /** A list of inclusive order weight upper bounds. The last weight's value can be `"infinity"`. For example `[{"value": "10", "unit": "kg"}, {"value": "50", "unit": "kg"}, {"value": "infinity", "unit": "kg"}]` represents the headers "<= 10kg", "<= 50kg", and "> 50kg". All weights within a service must have the same unit. Must be non-empty. Can only be set if all other fields are not set. */
		weights: Option[List[Schema.Weight]] = None,
	  /** A list of inclusive number of items upper bounds. The last value can be `"infinity"`. For example `["10", "50", "infinity"]` represents the headers "<= 10 items", "<= 50 items", and "> 50 items". Must be non-empty. Can only be set if all other fields are not set. */
		numberOfItems: Option[List[String]] = None,
	  /** A list of postal group names. The last value can be `"all other locations"`. Example: `["zone 1", "zone 2", "all other locations"]`. The referred postal code groups must match the delivery country of the service. Must be non-empty. Can only be set if all other fields are not set. */
		postalCodeGroupNames: Option[List[String]] = None,
	  /** A list of location ID sets. Must be non-empty. Can only be set if all other fields are not set. */
		locations: Option[List[Schema.LocationIdSet]] = None
	)
	
	case class Weight(
	  /** Required. The weight represented as a number. The weight can have a maximum precision of four decimal places. */
		value: Option[String] = None,
	  /** Required. The weight unit. Acceptable values are: - "`kg`" - "`lb`"  */
		unit: Option[String] = None
	)
	
	case class LocationIdSet(
	  /** A non-empty list of location IDs. They must all be of the same location type (for example, state). */
		locationIds: Option[List[String]] = None
	)
	
	case class Row(
	  /** The list of cells that constitute the row. Must have the same length as `columnHeaders` for two-dimensional tables, a length of 1 for one-dimensional tables. Required. */
		cells: Option[List[Schema.Value]] = None
	)
	
	case class CarrierRate(
	  /** Name of the carrier rate. Must be unique per rate group. Required. */
		name: Option[String] = None,
	  /** Carrier service, such as `"UPS"` or `"Fedex"`. The list of supported carriers can be retrieved through the `getSupportedCarriers` method. Required. */
		carrierName: Option[String] = None,
	  /** Carrier service, such as `"ground"` or `"2 days"`. The list of supported services for a carrier can be retrieved through the `getSupportedCarriers` method. Required. */
		carrierService: Option[String] = None,
	  /** Shipping origin for this carrier rate. Required. */
		originPostalCode: Option[String] = None,
	  /** Multiplicative shipping rate modifier as a number in decimal notation. Can be negative. For example `"5.4"` increases the rate by 5.4%, `"-3"` decreases the rate by 3%. Optional. */
		percentageAdjustment: Option[String] = None,
	  /** Additive shipping rate modifier. Can be negative. For example `{ "value": "1", "currency" : "USD" }` adds $1 to the rate, `{ "value": "-3", "currency" : "USD" }` removes $3 from the rate. Optional. */
		flatAdjustment: Option[Schema.Price] = None
	)
	
	case class PickupCarrierService(
	  /** The name of the pickup carrier (for example, `"UPS"`). Required. */
		carrierName: Option[String] = None,
	  /** The name of the pickup service (for example, `"Access point"`). Required. */
		serviceName: Option[String] = None
	)
	
	case class MinimumOrderValueTable(
		storeCodeSetWithMovs: Option[List[Schema.MinimumOrderValueTableStoreCodeSetWithMov]] = None
	)
	
	case class MinimumOrderValueTableStoreCodeSetWithMov(
	  /** A list of unique store codes or empty for the catch all. */
		storeCodes: Option[List[String]] = None,
	  /** The minimum order value for the given stores. */
		value: Option[Schema.Price] = None
	)
	
	case class ServiceStoreConfig(
	  /** Indicates whether all stores listed by this merchant provide local delivery or not. Acceptable values are `all stores` and `selected stores` */
		storeServiceType: Option[String] = None,
	  /** A list of store codes that provide local delivery. If empty, then `store_service_type` must be `all_stores`, or an error is thrown. If not empty, then `store_service_type` must be `selected_stores`, or an error is thrown. */
		storeCodes: Option[List[String]] = None,
	  /** Time local delivery ends for the day. This can be either `local_cutoff_time` or `store_close_offset_hours`, if both are provided an error is thrown. */
		cutoffConfig: Option[Schema.ServiceStoreConfigCutoffConfig] = None,
	  /** Maximum delivery radius. Only needed for local delivery fulfillment type. */
		serviceRadius: Option[Schema.Distance] = None
	)
	
	case class ServiceStoreConfigCutoffConfig(
	  /** Time in hours and minutes in the local timezone when local delivery ends. */
		localCutoffTime: Option[Schema.ServiceStoreConfigCutoffConfigLocalCutoffTime] = None,
	  /** Represents cutoff time as the number of hours before store closing. Mutually exclusive with other fields (hour and minute). */
		storeCloseOffsetHours: Option[String] = None,
	  /** Merchants can opt-out of showing n+1 day local delivery when they have a shipping service configured to n day local delivery. For example, if the shipping service defines same-day delivery, and it's past the cut-off, setting this field to `true` results in the calculated shipping service rate returning `NO_DELIVERY_POST_CUTOFF`. In the same example, setting this field to `false` results in the calculated shipping time being one day. This is only for local delivery. */
		noDeliveryPostCutoff: Option[Boolean] = None
	)
	
	case class ServiceStoreConfigCutoffConfigLocalCutoffTime(
	  /** Hour local delivery orders must be placed by to process the same day. */
		hour: Option[String] = None,
	  /** Minute local delivery orders must be placed by to process the same day. */
		minute: Option[String] = None
	)
	
	case class Distance(
	  /** The distance represented as a number. */
		value: Option[String] = None,
	  /** The distance unit. Acceptable values are `None`, `Miles`, and `Kilometers`. */
		unit: Option[String] = None
	)
	
	case class PostalCodeGroup(
	  /** The name of the postal code group, referred to in headers. Required. */
		name: Option[String] = None,
	  /** The CLDR territory code of the country the postal code group applies to. Required. */
		country: Option[String] = None,
	  /** A range of postal codes. Required. */
		postalCodeRanges: Option[List[Schema.PostalCodeRange]] = None
	)
	
	case class PostalCodeRange(
	  /** A postal code or a pattern of the form `prefix&#42;` denoting the inclusive lower bound of the range defining the area. Examples values: `"94108"`, `"9410&#42;"`, `"9&#42;"`. Required. */
		postalCodeRangeBegin: Option[String] = None,
	  /** A postal code or a pattern of the form `prefix&#42;` denoting the inclusive upper bound of the range defining the area. It must have the same length as `postalCodeRangeBegin`: if `postalCodeRangeBegin` is a postal code then `postalCodeRangeEnd` must be a postal code too; if `postalCodeRangeBegin` is a pattern then `postalCodeRangeEnd` must be a pattern with the same prefix length. Optional: if not set, then the area is defined as being all the postal codes matching `postalCodeRangeBegin`. */
		postalCodeRangeEnd: Option[String] = None
	)
	
	case class Warehouse(
	  /** Required. The name of the warehouse. Must be unique within account. */
		name: Option[String] = None,
	  /** Required. Shipping address of the warehouse. */
		shippingAddress: Option[Schema.Address] = None,
	  /** Required. The latest time of day that an order can be accepted and begin processing. Later orders will be processed in the next day. The time is based on the warehouse postal code. */
		cutoffTime: Option[Schema.WarehouseCutoffTime] = None,
	  /** Required. The number of days it takes for this warehouse to pack up and ship an item. This is on the warehouse level, but can be overridden on the offer level based on the attributes of an item. */
		handlingDays: Option[String] = None,
	  /** Business days of the warehouse. If not set, will be Monday to Friday by default. */
		businessDayConfig: Option[Schema.BusinessDayConfig] = None
	)
	
	case class Address(
	  /** Street-level part of the address. Use `\n` to add a second line. */
		streetAddress: Option[String] = None,
	  /** Required. City, town or commune. May also include dependent localities or sublocalities (for example, neighborhoods or suburbs). */
		city: Option[String] = None,
	  /** Required. Top-level administrative subdivision of the country. For example, a state like California ("CA") or a province like Quebec ("QC"). */
		administrativeArea: Option[String] = None,
	  /** Required. Postal code or ZIP (for example, "94043"). */
		postalCode: Option[String] = None,
	  /** Required. [CLDR country code](https://github.com/unicode-org/cldr/blob/latest/common/main/en.xml) (for example, "US"). */
		country: Option[String] = None
	)
	
	case class WarehouseCutoffTime(
	  /** Required. Hour (24-hour clock) of the cutoff time until which an order has to be placed to be processed in the same day by the warehouse. Hour is based on the timezone of warehouse. */
		hour: Option[Int] = None,
	  /** Required. Minute of the cutoff time until which an order has to be placed to be processed in the same day by the warehouse. Minute is based on the timezone of warehouse. */
		minute: Option[Int] = None
	)
	
	case class ShippingsettingsCustomBatchResponse(
	  /** The result of the execution of the batch requests. */
		entries: Option[List[Schema.ShippingsettingsCustomBatchResponseEntry]] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#shippingsettingsCustomBatchResponse`". */
		kind: Option[String] = None
	)
	
	case class ShippingsettingsCustomBatchResponseEntry(
	  /** The ID of the request entry to which this entry responds. */
		batchId: Option[Int] = None,
	  /** The retrieved or updated account shipping settings. */
		shippingSettings: Option[Schema.ShippingSettings] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#shippingsettingsCustomBatchResponseEntry`" */
		kind: Option[String] = None,
	  /** A list of errors for failed custombatch entries. &#42;Note:&#42; Schema errors fail the whole request. */
		errors: Option[Schema.Errors] = None
	)
	
	case class ShippingsettingsGetSupportedCarriersResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#shippingsettingsGetSupportedCarriersResponse`". */
		kind: Option[String] = None,
	  /** A list of supported carriers. May be empty. */
		carriers: Option[List[Schema.CarriersCarrier]] = None
	)
	
	case class CarriersCarrier(
	  /** The name of the carrier (for example, `"UPS"`). Always present. */
		name: Option[String] = None,
	  /** The CLDR country code of the carrier (for example, "US"). Always present. */
		country: Option[String] = None,
	  /** A list of supported services (for example, `"ground"`) for that carrier. Contains at least one service. This is the list of valid values for CarrierRate.carrierService. */
		services: Option[List[String]] = None,
	  /** A list of services supported for EDD (Estimated Delivery Date) calculation. This is the list of valid values for WarehouseBasedDeliveryTime.carrierService. */
		eddServices: Option[List[String]] = None
	)
	
	case class ShippingsettingsGetSupportedHolidaysResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#shippingsettingsGetSupportedHolidaysResponse`". */
		kind: Option[String] = None,
	  /** A list of holidays applicable for delivery guarantees. May be empty. */
		holidays: Option[List[Schema.HolidaysHoliday]] = None
	)
	
	case class HolidaysHoliday(
	  /** Unique identifier for the holiday to be used when configuring holiday cutoffs. Always present. */
		id: Option[String] = None,
	  /** The CLDR territory code of the country in which the holiday is available. For example, "US", "DE", "GB". A holiday cutoff can only be configured in a shipping settings service with matching delivery country. Always present. */
		countryCode: Option[String] = None,
	  /** The holiday type. Always present. Acceptable values are: - "`Christmas`" - "`Easter`" - "`Father's Day`" - "`Halloween`" - "`Independence Day (USA)`" - "`Mother's Day`" - "`Thanksgiving`" - "`Valentine's Day`"  */
		`type`: Option[String] = None,
	  /** Date of the holiday, in ISO 8601 format. For example, "2016-12-25" for Christmas 2016. Always present. */
		date: Option[String] = None,
	  /** Date on which the order has to arrive at the customer's, in ISO 8601 format. For example, "2016-12-24" for 24th December 2016. Always present. */
		deliveryGuaranteeDate: Option[String] = None,
	  /** Hour of the day in the delivery location's timezone on the guaranteed delivery date by which the order has to arrive at the customer's. Possible values are: 0 (midnight), 1, ..., 12 (noon), 13, ..., 23. Always present. */
		deliveryGuaranteeHour: Option[String] = None
	)
	
	case class ShippingsettingsGetSupportedPickupServicesResponse(
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#shippingsettingsGetSupportedPickupServicesResponse`". */
		kind: Option[String] = None,
	  /** A list of supported pickup services. May be empty. */
		pickupServices: Option[List[Schema.PickupServicesPickupService]] = None
	)
	
	case class PickupServicesPickupService(
	  /** The CLDR country code of the carrier (for example, "US"). Always present. */
		country: Option[String] = None,
	  /** The name of the carrier (for example, `"UPS"`). Always present. */
		carrierName: Option[String] = None,
	  /** The name of the pickup service (for example, `"Access point"`). Always present. */
		serviceName: Option[String] = None
	)
	
	case class ShippingsettingsListResponse(
	  /** The token for the retrieval of the next page of shipping settings. */
		nextPageToken: Option[String] = None,
	  /** Identifies what kind of resource this is. Value: the fixed string "`content#shippingsettingsListResponse`". */
		kind: Option[String] = None,
		resources: Option[List[Schema.ShippingSettings]] = None
	)
	
	object AccountCredentials {
		enum PurposeEnum extends Enum[PurposeEnum] { case ACCOUNT_CREDENTIALS_PURPOSE_UNSPECIFIED, SHOPIFY_ORDER_MANAGEMENT, SHOPIFY_INTEGRATION }
	}
	case class AccountCredentials(
	  /** Indicates to Google how Google should use these OAuth tokens. */
		purpose: Option[Schema.AccountCredentials.PurposeEnum] = None,
	  /** An OAuth access token. */
		accessToken: Option[String] = None,
	  /** The amount of time, in seconds, after which the access token is no longer valid. */
		expiresIn: Option[String] = None
	)
	
	object RequestPhoneVerificationRequest {
		enum PhoneVerificationMethodEnum extends Enum[PhoneVerificationMethodEnum] { case PHONE_VERIFICATION_METHOD_UNSPECIFIED, SMS, PHONE_CALL }
	}
	case class RequestPhoneVerificationRequest(
	  /** Required. Two letter country code for the phone number, for example `CA` for Canadian numbers. See the [ISO 3166-1 alpha-2](https://wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements) officially assigned codes. */
		phoneRegionCode: Option[String] = None,
	  /** Phone number to be verified. */
		phoneNumber: Option[String] = None,
	  /** Verification method to receive verification code. */
		phoneVerificationMethod: Option[Schema.RequestPhoneVerificationRequest.PhoneVerificationMethodEnum] = None,
	  /** Language code [IETF BCP 47 syntax](https://tools.ietf.org/html/bcp47) (for example, en-US). Language code is used to provide localized `SMS` and `PHONE_CALL`. Default language used is en-US if not provided. */
		languageCode: Option[String] = None
	)
	
	case class RequestPhoneVerificationResponse(
	  /** The verification ID to use in subsequent calls to `verifyphonenumber`. */
		verificationId: Option[String] = None
	)
	
	object VerifyPhoneNumberRequest {
		enum PhoneVerificationMethodEnum extends Enum[PhoneVerificationMethodEnum] { case PHONE_VERIFICATION_METHOD_UNSPECIFIED, SMS, PHONE_CALL }
	}
	case class VerifyPhoneNumberRequest(
	  /** The verification ID returned by `requestphoneverification`. */
		verificationId: Option[String] = None,
	  /** The verification code that was sent to the phone number for validation. */
		verificationCode: Option[String] = None,
	  /** Verification method used to receive verification code. */
		phoneVerificationMethod: Option[Schema.VerifyPhoneNumberRequest.PhoneVerificationMethodEnum] = None
	)
	
	case class VerifyPhoneNumberResponse(
	  /** Verified phone number if verification is successful. This phone number can only be replaced by another verified phone number. */
		verifiedPhoneNumber: Option[String] = None
	)
	
	case class ListAccountLabelsResponse(
	  /** The labels from the specified account. */
		accountLabels: Option[List[Schema.AccountLabel]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object AccountLabel {
		enum LabelTypeEnum extends Enum[LabelTypeEnum] { case LABEL_TYPE_UNSPECIFIED, MANUAL, AUTOMATIC }
	}
	case class AccountLabel(
	  /** Output only. The ID of the label. */
		labelId: Option[String] = None,
	  /** Immutable. The ID of account this label belongs to. */
		accountId: Option[String] = None,
	  /** The display name of this label. */
		name: Option[String] = None,
	  /** The description of this label. */
		description: Option[String] = None,
	  /** Output only. The type of this label. */
		labelType: Option[Schema.AccountLabel.LabelTypeEnum] = None
	)
	
	case class Collection(
	  /** Required. The REST ID of the collection. Content API methods that operate on collections take this as their collectionId parameter. The REST ID for a collection is of the form collectionId. [id attribute](https://support.google.com/merchants/answer/9649290) */
		id: Option[String] = None,
	  /** The language of a collection and the language of any featured products linked to the collection. [language attribute](https://support.google.com/merchants/answer/9673781) */
		language: Option[String] = None,
	  /** [product_country attribute](https://support.google.com/merchants/answer/9674155) */
		productCountry: Option[String] = None,
	  /** The URL of a collection’s image. [image_link attribute](https://support.google.com/merchants/answer/9703236) */
		imageLink: Option[List[String]] = None,
	  /** This identifies one or more products associated with the collection. Used as a lookup to the corresponding product ID in your product feeds. Provide a maximum of 100 featuredProduct (for collections). Provide up to 10 featuredProduct (for Shoppable Images only) with ID and X and Y coordinates. [featured_product attribute](https://support.google.com/merchants/answer/9703736) */
		featuredProduct: Option[List[Schema.CollectionFeaturedProduct]] = None,
	  /** A collection’s landing page. URL directly linking to your collection's page on your website. [link attribute](https://support.google.com/merchants/answer/9673983) */
		link: Option[String] = None,
	  /** A collection’s mobile-optimized landing page when you have a different URL for mobile and desktop traffic. [mobile_link attribute](https://support.google.com/merchants/answer/9646123) */
		mobileLink: Option[String] = None,
	  /** Your collection's name. [headline attribute](https://support.google.com/merchants/answer/9673580) */
		headline: Option[List[String]] = None,
	  /** Label that you assign to a collection to help organize bidding and reporting in Shopping campaigns. [Custom label](https://support.google.com/merchants/answer/9674217) */
		customLabel0: Option[String] = None,
	  /** Label that you assign to a collection to help organize bidding and reporting in Shopping campaigns. */
		customLabel1: Option[String] = None,
	  /** Label that you assign to a collection to help organize bidding and reporting in Shopping campaigns. */
		customLabel2: Option[String] = None,
	  /** Label that you assign to a collection to help organize bidding and reporting in Shopping campaigns. */
		customLabel3: Option[String] = None,
	  /** Label that you assign to a collection to help organize bidding and reporting in Shopping campaigns. */
		customLabel4: Option[String] = None
	)
	
	case class CollectionFeaturedProduct(
	  /** The unique identifier for the product item. */
		offerId: Option[String] = None,
	  /** Required. X-coordinate of the product callout on the Shoppable Image. */
		x: Option[BigDecimal] = None,
	  /** Required. Y-coordinate of the product callout on the Shoppable Image. */
		y: Option[BigDecimal] = None
	)
	
	case class ListCollectionsResponse(
	  /** The collections listed. */
		resources: Option[List[Schema.Collection]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class ListMethodQuotasResponse(
	  /** The current quota usage and limits per each method. */
		methodQuotas: Option[List[Schema.MethodQuota]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class MethodQuota(
	  /** Output only. The method name, for example `products.list`. Method name does not contain version because quota can be shared between different API versions of the same method. */
		method: Option[String] = None,
	  /** Output only. The current quota usage, meaning the number of calls already made to the method per day. Usage is reset every day at 12 PM midday UTC. */
		quotaUsage: Option[String] = None,
	  /** Output only. The maximum number of calls allowed per day for the method. */
		quotaLimit: Option[String] = None,
	  /** Output only. The maximum number of calls allowed per minute for the method. */
		quotaMinuteLimit: Option[String] = None
	)
	
	case class CollectionStatus(
	  /** Required. The ID of the collection for which status is reported. */
		id: Option[String] = None,
	  /** The intended destinations for the collection. */
		destinationStatuses: Option[List[Schema.CollectionStatusDestinationStatus]] = None,
	  /** Date on which the collection has been created in [ISO 8601](http://en.wikipedia.org/wiki/ISO_8601) format: Date, time, and offset, for example "2020-01-02T09:00:00+01:00" or "2020-01-02T09:00:00Z" */
		creationDate: Option[String] = None,
	  /** Date on which the collection has been last updated in [ISO 8601](http://en.wikipedia.org/wiki/ISO_8601) format: Date, time, and offset, for example "2020-01-02T09:00:00+01:00" or "2020-01-02T09:00:00Z" */
		lastUpdateDate: Option[String] = None,
	  /** A list of all issues associated with the collection. */
		collectionLevelIssuses: Option[List[Schema.CollectionStatusItemLevelIssue]] = None
	)
	
	case class CollectionStatusDestinationStatus(
	  /** The name of the destination */
		destination: Option[String] = None,
	  /** The status for the specified destination in the collections target country. */
		status: Option[String] = None,
	  /** Country codes (ISO 3166-1 alpha-2) where the collection is approved. */
		approvedCountries: Option[List[String]] = None,
	  /** Country codes (ISO 3166-1 alpha-2) where the collection is pending approval. */
		pendingCountries: Option[List[String]] = None,
	  /** Country codes (ISO 3166-1 alpha-2) where the collection is disapproved. */
		disapprovedCountries: Option[List[String]] = None
	)
	
	case class CollectionStatusItemLevelIssue(
	  /** The error code of the issue. */
		code: Option[String] = None,
	  /** How this issue affects the serving of the collection. */
		servability: Option[String] = None,
	  /** Whether the issue can be resolved by the merchant. */
		resolution: Option[String] = None,
	  /** The attribute's name, if the issue is caused by a single attribute. */
		attributeName: Option[String] = None,
	  /** The destination the issue applies to. */
		destination: Option[String] = None,
	  /** A short issue description in English. */
		description: Option[String] = None,
	  /** A detailed issue description in English. */
		detail: Option[String] = None,
	  /** The URL of a web page to help with resolving this issue. */
		documentation: Option[String] = None,
	  /** Country codes (ISO 3166-1 alpha-2) where issue applies to the offer. */
		applicableCountries: Option[List[String]] = None
	)
	
	case class ListCollectionStatusesResponse(
	  /** The collectionstatuses listed. */
		resources: Option[List[Schema.CollectionStatus]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object ConversionSource {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, ARCHIVED, PENDING }
	}
	case class ConversionSource(
	  /** Output only. Generated by the Content API upon creation of a new `ConversionSource`. Format: [a-z]{4}:.+ The four characters before the colon represent the type of conversio source. Content after the colon represents the ID of the conversion source within that type. The ID of two different conversion sources might be the same across different types. The following type prefixes are supported: - galk: For GoogleAnalyticsLink sources. - mcdn: For MerchantCenterDestination sources. */
		conversionSourceId: Option[String] = None,
	  /** Immutable. Conversion Source of type "Link to Google Analytics Property". */
		googleAnalyticsLink: Option[Schema.GoogleAnalyticsLink] = None,
	  /** Conversion Source of type "Merchant Center Tag Destination". */
		merchantCenterDestination: Option[Schema.MerchantCenterDestination] = None,
	  /** Output only. Current state of this conversion source. Can't be edited through the API. */
		state: Option[Schema.ConversionSource.StateEnum] = None,
	  /** Output only. The time when an archived conversion source becomes permanently deleted and is no longer available to undelete. */
		expireTime: Option[String] = None
	)
	
	case class GoogleAnalyticsLink(
	  /** Required. Immutable. ID of the Google Analytics property the merchant is linked to. */
		propertyId: Option[String] = None,
	  /** Output only. Attribution settings for the linked Google Analytics property. */
		attributionSettings: Option[Schema.AttributionSettings] = None,
	  /** Output only. Name of the Google Analytics property the merchant is linked to. */
		propertyName: Option[String] = None
	)
	
	object AttributionSettings {
		enum AttributionModelEnum extends Enum[AttributionModelEnum] { case ATTRIBUTION_MODEL_UNSPECIFIED, CROSS_CHANNEL_LAST_CLICK, ADS_PREFERRED_LAST_CLICK, CROSS_CHANNEL_DATA_DRIVEN, CROSS_CHANNEL_FIRST_CLICK, CROSS_CHANNEL_LINEAR, CROSS_CHANNEL_POSITION_BASED, CROSS_CHANNEL_TIME_DECAY }
	}
	case class AttributionSettings(
	  /** Required. Lookback windows (in days) used for attribution in this source. Supported values are 7, 30, 40. */
		attributionLookbackWindowInDays: Option[Int] = None,
	  /** Required. Attribution model. */
		attributionModel: Option[Schema.AttributionSettings.AttributionModelEnum] = None,
	  /** Immutable. Unordered list. List of different conversion types a conversion event can be classified as. A standard "purchase" type will be automatically created if this list is empty at creation time. */
		conversionType: Option[List[Schema.AttributionSettingsConversionType]] = None
	)
	
	case class AttributionSettingsConversionType(
	  /** Output only. Conversion event name, as it'll be reported by the client. */
		name: Option[String] = None,
	  /** Output only. Option indicating if the type should be included in Merchant Center reporting. */
		includeInReporting: Option[Boolean] = None
	)
	
	case class MerchantCenterDestination(
	  /** Output only. Merchant Center Destination ID. */
		destinationId: Option[String] = None,
	  /** Required. Attribution settings being used for the Merchant Center Destination. */
		attributionSettings: Option[Schema.AttributionSettings] = None,
	  /** Required. Merchant-specified display name for the destination. This is the name that identifies the conversion source within the Merchant Center UI. Limited to 64 characters. */
		displayName: Option[String] = None,
	  /** Required. Three-letter currency code (ISO 4217). The currency code defines in which currency the conversions sent to this destination will be reported in Merchant Center. */
		currencyCode: Option[String] = None
	)
	
	case class UndeleteConversionSourceRequest(
	
	)
	
	case class ListConversionSourcesResponse(
	  /** List of conversion sources. */
		conversionSources: Option[List[Schema.ConversionSource]] = None,
	  /** Token to be used to fetch the next results page. */
		nextPageToken: Option[String] = None
	)
	
	object FreeListingsProgramStatus {
		enum GlobalStateEnum extends Enum[GlobalStateEnum] { case PROGRAM_STATE_UNSPECIFIED, NOT_ENABLED, NO_OFFERS_UPLOADED, ENABLED }
	}
	case class FreeListingsProgramStatus(
	  /** State of the program. `ENABLED` if there are offers for at least one region. */
		globalState: Option[Schema.FreeListingsProgramStatus.GlobalStateEnum] = None,
	  /** Status of the program in each region. Regions with the same status and review eligibility are grouped together in `regionCodes`. */
		regionStatuses: Option[List[Schema.FreeListingsProgramStatusRegionStatus]] = None
	)
	
	object FreeListingsProgramStatusRegionStatus {
		enum EligibilityStatusEnum extends Enum[EligibilityStatusEnum] { case STATE_UNSPECIFIED, APPROVED, DISAPPROVED, WARNING, UNDER_REVIEW, PENDING_REVIEW, ONBOARDING }
		enum ReviewEligibilityStatusEnum extends Enum[ReviewEligibilityStatusEnum] { case REVIEW_ELIGIBILITY_UNSPECIFIED, ELIGIBLE, INELIGIBLE }
		enum ReviewIneligibilityReasonEnum extends Enum[ReviewIneligibilityReasonEnum] { case REVIEW_INELIGIBILITY_REASON_UNSPECIFIED, ONBOARDING_ISSUES, NOT_ENOUGH_OFFERS, IN_COOLDOWN_PERIOD, ALREADY_UNDER_REVIEW, NO_REVIEW_REQUIRED, WILL_BE_REVIEWED_AUTOMATICALLY, IS_RETIRED, ALREADY_REVIEWED }
	}
	case class FreeListingsProgramStatusRegionStatus(
	  /** The two-letter [ISO 3166-1 alpha-2](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2) codes for all the regions with the same `eligibilityStatus` and `reviewEligibility`. */
		regionCodes: Option[List[String]] = None,
	  /** Eligibility status of the standard free listing program. */
		eligibilityStatus: Option[Schema.FreeListingsProgramStatusRegionStatus.EligibilityStatusEnum] = None,
	  /** Date by which eligibilityStatus will go from `WARNING` to `DISAPPROVED`. Only visible when your eligibilityStatus is WARNING. In [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format: `YYYY-MM-DD`. */
		disapprovalDate: Option[String] = None,
	  /** If a program is eligible for review in a specific region. Only visible if `eligibilityStatus` is `DISAPPROVED`. */
		reviewEligibilityStatus: Option[Schema.FreeListingsProgramStatusRegionStatus.ReviewEligibilityStatusEnum] = None,
	  /** Review ineligibility reason if account is not eligible for review. */
		reviewIneligibilityReason: Option[Schema.FreeListingsProgramStatusRegionStatus.ReviewIneligibilityReasonEnum] = None,
	  /** Reason a program in a specific region isn’t eligible for review. Only visible if `reviewEligibilityStatus` is `INELIGIBLE`. */
		reviewIneligibilityReasonDescription: Option[String] = None,
	  /** Issues evaluated in the review process. Fix all issues before requesting a review. */
		reviewIssues: Option[List[String]] = None,
	  /** Issues that must be fixed to be eligible for review. */
		onboardingIssues: Option[List[String]] = None,
	  /** Additional information for ineligibility. If `reviewIneligibilityReason` is `IN_COOLDOWN_PERIOD`, a timestamp for the end of the cooldown period is provided. */
		reviewIneligibilityReasonDetails: Option[Schema.FreeListingsProgramStatusReviewIneligibilityReasonDetails] = None
	)
	
	case class FreeListingsProgramStatusReviewIneligibilityReasonDetails(
	  /** This timestamp represents end of cooldown period for review ineligbility reason `IN_COOLDOWN_PERIOD`. */
		cooldownTime: Option[String] = None
	)
	
	object CheckoutSettings {
		enum EnrollmentStateEnum extends Enum[EnrollmentStateEnum] { case CHECKOUT_ON_MERCHANT_ENROLLMENT_STATE_UNSPECIFIED, INACTIVE, ENROLLED, OPT_OUT }
		enum ReviewStateEnum extends Enum[ReviewStateEnum] { case CHECKOUT_ON_MERCHANT_REVIEW_STATE_UNSPECIFIED, IN_REVIEW, APPROVED, DISAPPROVED }
		enum EffectiveEnrollmentStateEnum extends Enum[EffectiveEnrollmentStateEnum] { case CHECKOUT_ON_MERCHANT_ENROLLMENT_STATE_UNSPECIFIED, INACTIVE, ENROLLED, OPT_OUT }
		enum EffectiveReviewStateEnum extends Enum[EffectiveReviewStateEnum] { case CHECKOUT_ON_MERCHANT_REVIEW_STATE_UNSPECIFIED, IN_REVIEW, APPROVED, DISAPPROVED }
	}
	case class CheckoutSettings(
	  /** Required. The ID of the account. */
		merchantId: Option[String] = None,
	  /** URL settings for cart or checkout URL. */
		uriSettings: Option[Schema.UrlSettings] = None,
	  /** Output only. Reflects the merchant enrollment state in `Checkout` feature. */
		enrollmentState: Option[Schema.CheckoutSettings.EnrollmentStateEnum] = None,
	  /** Output only. Reflects the merchant review state in `Checkout` feature. This is set based on the data quality reviews of the URL provided by the merchant. A merchant with enrollment state as `ENROLLED` can be in the following review states: `IN_REVIEW`, `APPROVED` or `DISAPPROVED`. A merchant must be in an enrollment_state of `ENROLLED` before a review can begin for the merchant. */
		reviewState: Option[Schema.CheckoutSettings.ReviewStateEnum] = None,
	  /** The effective value of `url_settings` for a given merchant ID. If account level settings are present then this value will be a copy of the account level settings. Otherwise, it will have the value of the parent account. */
		effectiveUriSettings: Option[Schema.UrlSettings] = None,
	  /** Output only. The effective value of enrollment state for a given merchant ID. If account level settings are present then this value will be a copy of the account level settings. Otherwise, it will have the value of the parent account. */
		effectiveEnrollmentState: Option[Schema.CheckoutSettings.EffectiveEnrollmentStateEnum] = None,
	  /** Output only. The effective value of review state for a given merchant ID. If account level settings are present then this value will be a copy of the account level settings. Otherwise, it will have the value of the parent account. */
		effectiveReviewState: Option[Schema.CheckoutSettings.EffectiveReviewStateEnum] = None
	)
	
	case class UrlSettings(
	  /** URL template when the placeholders are expanded will redirect the buyer to the merchant checkout page with the item in the cart. */
		checkoutUriTemplate: Option[String] = None,
	  /** URL template when the placeholders are expanded will redirect the buyer to the cart page on the merchant website with the selected item in cart. */
		cartUriTemplate: Option[String] = None
	)
	
	case class InsertCheckoutSettingsRequest(
	  /** Required. The `UrlSettings` for the request. The presence of URL settings indicates `Checkout` enrollment. */
		uriSettings: Option[Schema.UrlSettings] = None
	)
	
	case class RequestReviewFreeListingsRequest(
	  /** The code [ISO 3166-1 alpha-2](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2) of the country for which review is to be requested. */
		regionCode: Option[String] = None
	)
	
	object ShoppingAdsProgramStatus {
		enum GlobalStateEnum extends Enum[GlobalStateEnum] { case PROGRAM_STATE_UNSPECIFIED, NOT_ENABLED, NO_OFFERS_UPLOADED, ENABLED }
	}
	case class ShoppingAdsProgramStatus(
	  /** State of the program. `ENABLED` if there are offers for at least one region. */
		globalState: Option[Schema.ShoppingAdsProgramStatus.GlobalStateEnum] = None,
	  /** Status of the program in each region. Regions with the same status and review eligibility are grouped together in `regionCodes`. */
		regionStatuses: Option[List[Schema.ShoppingAdsProgramStatusRegionStatus]] = None
	)
	
	object ShoppingAdsProgramStatusRegionStatus {
		enum EligibilityStatusEnum extends Enum[EligibilityStatusEnum] { case STATE_UNSPECIFIED, APPROVED, DISAPPROVED, WARNING, UNDER_REVIEW, PENDING_REVIEW, ONBOARDING }
		enum ReviewEligibilityStatusEnum extends Enum[ReviewEligibilityStatusEnum] { case REVIEW_ELIGIBILITY_UNSPECIFIED, ELIGIBLE, INELIGIBLE }
		enum ReviewIneligibilityReasonEnum extends Enum[ReviewIneligibilityReasonEnum] { case REVIEW_INELIGIBILITY_REASON_UNSPECIFIED, ONBOARDING_ISSUES, NOT_ENOUGH_OFFERS, IN_COOLDOWN_PERIOD, ALREADY_UNDER_REVIEW, NO_REVIEW_REQUIRED, WILL_BE_REVIEWED_AUTOMATICALLY, IS_RETIRED, ALREADY_REVIEWED }
	}
	case class ShoppingAdsProgramStatusRegionStatus(
	  /** The two-letter [ISO 3166-1 alpha-2](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2) codes for all the regions with the same `eligibilityStatus` and `reviewEligibility`. */
		regionCodes: Option[List[String]] = None,
	  /** Eligibility status of the Shopping Ads program. */
		eligibilityStatus: Option[Schema.ShoppingAdsProgramStatusRegionStatus.EligibilityStatusEnum] = None,
	  /** Date by which eligibilityStatus will go from `WARNING` to `DISAPPROVED`. Only visible when your eligibilityStatus is WARNING. In [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format: `YYYY-MM-DD`. */
		disapprovalDate: Option[String] = None,
	  /** If a program is eligible for review in a specific region. Only visible if `eligibilityStatus` is `DISAPPROVED`. */
		reviewEligibilityStatus: Option[Schema.ShoppingAdsProgramStatusRegionStatus.ReviewEligibilityStatusEnum] = None,
	  /** Review ineligibility reason if account is not eligible for review. */
		reviewIneligibilityReason: Option[Schema.ShoppingAdsProgramStatusRegionStatus.ReviewIneligibilityReasonEnum] = None,
	  /** Reason a program in a specific region isn’t eligible for review. Only visible if `reviewEligibilityStatus` is `INELIGIBLE`. */
		reviewIneligibilityReasonDescription: Option[String] = None,
	  /** Issues evaluated in the review process. Fix all issues before requesting a review. */
		reviewIssues: Option[List[String]] = None,
	  /** Issues that must be fixed to be eligible for review. */
		onboardingIssues: Option[List[String]] = None,
	  /** Additional information for ineligibility. If `reviewIneligibilityReason` is `IN_COOLDOWN_PERIOD`, a timestamp for the end of the cooldown period is provided. */
		reviewIneligibilityReasonDetails: Option[Schema.ShoppingAdsProgramStatusReviewIneligibilityReasonDetails] = None
	)
	
	case class ShoppingAdsProgramStatusReviewIneligibilityReasonDetails(
	  /** This timestamp represents end of cooldown period for review ineligbility reason `IN_COOLDOWN_PERIOD`. */
		cooldownTime: Option[String] = None
	)
	
	case class RequestReviewShoppingAdsRequest(
	  /** The code [ISO 3166-1 alpha-2](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2) of the country for which review is to be requested. */
		regionCode: Option[String] = None
	)
	
	case class ListCssesResponse(
	  /** The CSS domains affiliated with the specified CSS group. */
		csses: Option[List[Schema.Css]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class Css(
	  /** Output only. Immutable. The CSS domain ID. */
		cssDomainId: Option[String] = None,
	  /** Output only. Immutable. The CSS domain's full name. */
		fullName: Option[String] = None,
	  /** Output only. Immutable. The CSS domain's display name, used when space is constrained. */
		displayName: Option[String] = None,
	  /** Output only. Immutable. The CSS domain's homepage. */
		homepageUri: Option[String] = None,
	  /** Output only. Immutable. The ID of the CSS group this CSS domain is affiliated with. Only populated for CSS group users. */
		cssGroupId: Option[String] = None,
	  /** A list of label IDs that are assigned to this CSS domain by its CSS group. Only populated for CSS group users. */
		labelIds: Option[List[String]] = None
	)
	
	case class LabelIds(
	  /** The list of label IDs. */
		labelIds: Option[List[String]] = None
	)
	
	object AccountReturnCarrier {
		enum CarrierCodeEnum extends Enum[CarrierCodeEnum] { case CARRIER_CODE_UNSPECIFIED, FEDEX, UPS }
	}
	case class AccountReturnCarrier(
	  /** Output only. Immutable. The Google-provided unique carrier ID, used to update the resource. */
		carrierAccountId: Option[String] = None,
	  /** Number of the carrier account. */
		carrierAccountNumber: Option[String] = None,
	  /** Name of the carrier account. */
		carrierAccountName: Option[String] = None,
	  /** The carrier code enum. Accepts the values FEDEX or UPS. */
		carrierCode: Option[Schema.AccountReturnCarrier.CarrierCodeEnum] = None
	)
	
	case class ListAccountReturnCarrierResponse(
	  /** List of all available account return carriers for the merchant. */
		accountReturnCarriers: Option[List[Schema.AccountReturnCarrier]] = None
	)
	
	case class SearchRequest(
	  /** Required. Query that defines performance metrics to retrieve and dimensions according to which the metrics are to be segmented. For details on how to construct your query, see the [Query Language guide](https://developers.google.com/shopping-content/guides/reports/query-language/overview). */
		query: Option[String] = None,
	  /** Number of ReportRows to retrieve in a single page. Defaults to 1000. Values above 5000 are coerced to 5000. */
		pageSize: Option[Int] = None,
	  /** Token of the page to retrieve. If not specified, the first page of results is returned. In order to request the next page of results, the value obtained from `next_page_token` in the previous response should be used. */
		pageToken: Option[String] = None
	)
	
	case class SearchResponse(
	  /** Rows that matched the search query. */
		results: Option[List[Schema.ReportRow]] = None,
	  /** Token which can be sent as `page_token` to retrieve the next page. If omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class ReportRow(
	  /** Segmentation dimensions requested by the merchant in the query. Dimension values are only set for dimensions requested explicitly in the query. */
		segments: Option[Schema.Segments] = None,
	  /** Metrics requested by the merchant in the query. Metric values are only set for metrics requested explicitly in the query. */
		metrics: Option[Schema.Metrics] = None,
	  /** Product fields requested by the merchant in the query. Field values are only set if the merchant queries `ProductView`. */
		productView: Option[Schema.ProductView] = None,
	  /** Product cluster fields requested by the merchant in the query. Field values are only set if the merchant queries `BestSellersProductClusterView`. */
		productCluster: Option[Schema.ProductCluster] = None,
	  /** Brand fields requested by the merchant in the query. Field values are only set if the merchant queries `BestSellersBrandView`. */
		brand: Option[Schema.Brand] = None,
	  /** Best sellers fields requested by the merchant in the query. Field values are only set if the merchant queries `BestSellersProductClusterView` or `BestSellersBrandView`. */
		bestSellers: Option[Schema.BestSellers] = None,
	  /** Price competitiveness fields requested by the merchant in the query. Field values are only set if the merchant queries `PriceCompetitivenessProductView`. */
		priceCompetitiveness: Option[Schema.PriceCompetitiveness] = None,
	  /** Price insights fields requested by the merchant in the query. Field values are only set if the merchant queries `PriceInsightsProductView`. */
		priceInsights: Option[Schema.PriceInsights] = None,
	  /** Competitive visibility fields requested by the merchant in the query. Field values are only set if the merchant queries `CompetitiveVisibilityTopMerchantView`, `CompetitiveVisibilityBenchmarkView` or `CompetitiveVisibilityCompetitorView`. */
		competitiveVisibility: Option[Schema.CompetitiveVisibility] = None,
	  /** [Topic trends](https://support.google.com/merchants/answer/13542370) fields requested by the merchant in the query. Field values are only set if the merchant queries `TopicTrendsView`. */
		topicTrends: Option[Schema.TopicTrends] = None
	)
	
	object Segments {
		enum ProgramEnum extends Enum[ProgramEnum] { case PROGRAM_UNSPECIFIED, SHOPPING_ADS, FREE_PRODUCT_LISTING, FREE_LOCAL_PRODUCT_LISTING, BUY_ON_GOOGLE_LISTING }
	}
	case class Segments(
	  /** Program to which metrics apply, for example, Free Product Listing. */
		program: Option[Schema.Segments.ProgramEnum] = None,
	  /** Date in the merchant timezone to which metrics apply. */
		date: Option[Schema.Date] = None,
	  /** First day of the week (Monday) of the metrics date in the merchant timezone. */
		week: Option[Schema.Date] = None,
	  /** Code of the country where the customer is located at the time of the event. Represented in the ISO 3166 format. If the customer country cannot be determined, a special 'ZZ' code is returned. */
		customerCountryCode: Option[String] = None,
	  /** Currency in which price metrics are represented, for example, if you select `ordered_item_sales_micros`, the returned value will be represented by this currency. */
		currencyCode: Option[String] = None,
	  /** Merchant-provided id of the product. */
		offerId: Option[String] = None,
	  /** Title of the product. */
		title: Option[String] = None,
	  /** Brand of the product. */
		brand: Option[String] = None,
	  /** [Product category (1st level)](https://developers.google.com/shopping-content/guides/reports/segmentation#category_and_product_type) in Google's product taxonomy. */
		categoryL1: Option[String] = None,
	  /** [Product category (2nd level)](https://developers.google.com/shopping-content/guides/reports/segmentation#category_and_product_type) in Google's product taxonomy. */
		categoryL2: Option[String] = None,
	  /** [Product category (3rd level)](https://developers.google.com/shopping-content/guides/reports/segmentation#category_and_product_type) in Google's product taxonomy. */
		categoryL3: Option[String] = None,
	  /** [Product category (4th level)](https://developers.google.com/shopping-content/guides/reports/segmentation#category_and_product_type) in Google's product taxonomy. */
		categoryL4: Option[String] = None,
	  /** [Product category (5th level)](https://developers.google.com/shopping-content/guides/reports/segmentation#category_and_product_type) in Google's product taxonomy. */
		categoryL5: Option[String] = None,
	  /** [Product type (1st level)](https://developers.google.com/shopping-content/guides/reports/segmentation#category_and_product_type) in merchant's own product taxonomy. */
		productTypeL1: Option[String] = None,
	  /** [Product type (2nd level)](https://developers.google.com/shopping-content/guides/reports/segmentation#category_and_product_type) in merchant's own product taxonomy. */
		productTypeL2: Option[String] = None,
	  /** [Product type (3rd level)](https://developers.google.com/shopping-content/guides/reports/segmentation#category_and_product_type) in merchant's own product taxonomy. */
		productTypeL3: Option[String] = None,
	  /** [Product type (4th level)](https://developers.google.com/shopping-content/guides/reports/segmentation#category_and_product_type) in merchant's own product taxonomy. */
		productTypeL4: Option[String] = None,
	  /** [Product type (5th level)](https://developers.google.com/shopping-content/guides/reports/segmentation#category_and_product_type) in merchant's own product taxonomy. */
		productTypeL5: Option[String] = None,
	  /** Custom label 0 for custom grouping of products. */
		customLabel0: Option[String] = None,
	  /** Custom label 1 for custom grouping of products. */
		customLabel1: Option[String] = None,
	  /** Custom label 2 for custom grouping of products. */
		customLabel2: Option[String] = None,
	  /** Custom label 3 for custom grouping of products. */
		customLabel3: Option[String] = None,
	  /** Custom label 4 for custom grouping of products. */
		customLabel4: Option[String] = None
	)
	
	case class Date(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class Metrics(
	  /** Number of clicks. */
		clicks: Option[String] = None,
	  /** Number of times merchant's products are shown. */
		impressions: Option[String] = None,
	  /** Click-through rate - the number of clicks merchant's products receive (clicks) divided by the number of times the products are shown (impressions). */
		ctr: Option[BigDecimal] = None,
	  /** Number of conversions attributed to the product, reported on the conversion date. Depending on the attribution model, a conversion might be distributed across multiple clicks, where each click gets its own credit assigned. This metric is a sum of all such credits. The metric is currently available only for the `FREE_PRODUCT_LISTING` program. */
		conversions: Option[BigDecimal] = None,
	  /** Value of conversions in micros (1 millionth of a standard unit, 1 USD = 1000000 micros) attributed to the product, reported on the conversion date. The metric is currently available only for the `FREE_PRODUCT_LISTING` program. The currency of the returned value is stored in the currency_code segment. If this metric is selected, 'segments.currency_code' is automatically added to the SELECT clause in the search query (unless it is explicitly selected by the user) and the currency_code segment is populated in the response. */
		conversionValueMicros: Option[String] = None,
	  /** Number of conversions divided by the number of clicks, reported on the impression date. The metric is currently available only for the `FREE_PRODUCT_LISTING` program. */
		conversionRate: Option[BigDecimal] = None,
	  /** &#42;Deprecated&#42;: This field is no longer supported and retrieving it returns 0 starting from May 2024. Number of placed orders. Excludes customer cancellations that happened within 30 minutes of placing the order. &#42;&#42;This metric cannot be segmented by product dimensions and customer_country_code.&#42;&#42; */
		orders: Option[String] = None,
	  /** &#42;Deprecated&#42;: This field is no longer supported and retrieving it returns 0 starting from May 2024. Number of fully shipped orders, reported on the last shipment date. &#42;&#42;This metric cannot be segmented by product dimensions and customer_country_code.&#42;&#42; */
		shippedOrders: Option[String] = None,
	  /** &#42;Deprecated&#42;: This field is no longer supported and retrieving it returns 0 starting from May 2024. Number of orders not shipped or partially shipped up until the end of the queried day. If a multi-day period is specified in the search query, the returned value is the average number of unshipped orders over the days in the queried period. &#42;&#42;This metric cannot be segmented by product dimensions and customer_country_code.&#42;&#42; */
		unshippedOrders: Option[BigDecimal] = None,
	  /** &#42;Deprecated&#42;: This field is no longer supported and retrieving it returns 0 starting from May 2024. Average number of days between an order being placed and the order being fully shipped, reported on the last shipment date. &#42;&#42;This metric cannot be segmented by product dimensions and customer_country_code.&#42;&#42; */
		daysToShip: Option[BigDecimal] = None,
	  /** &#42;Deprecated&#42;: This field is no longer supported and retrieving it returns 0 starting from May 2024. Average order size - the average number of items in an order. &#42;&#42;This metric cannot be segmented by product dimensions and customer_country_code.&#42;&#42; */
		aos: Option[BigDecimal] = None,
	  /** &#42;Deprecated&#42;: This field is no longer supported and retrieving it returns 0 starting from May 2024. Average order value in micros (1 millionth of a standard unit, 1 USD = 1000000 micros) - the average value (total price of items) of all placed orders. The currency of the returned value is stored in the currency_code segment. If this metric is selected, 'segments.currency_code' is automatically added to the SELECT clause in the search query (unless it is explicitly selected by the user) and the currency_code segment is populated in the response. &#42;&#42;This metric cannot be segmented by product dimensions and customer_country_code.&#42;&#42; */
		aovMicros: Option[BigDecimal] = None,
	  /** &#42;Deprecated&#42;: This field is no longer supported and retrieving it returns 0 starting from May 2024. Number of ordered items. Excludes customer cancellations that happened within 30 minutes of placing the order. &#42;&#42;This metric cannot be segmented by customer_country_code.&#42;&#42; */
		orderedItems: Option[String] = None,
	  /** &#42;Deprecated&#42;: This field is no longer supported and retrieving it returns 0 starting from May 2024. Total price of ordered items in micros (1 millionth of a standard unit, 1 USD = 1000000 micros). Excludes shipping, taxes (US only), and customer cancellations that happened within 30 minutes of placing the order. The currency of the returned value is stored in the currency_code segment. If this metric is selected, 'segments.currency_code' is automatically added to the SELECT clause in the search query (unless it is explicitly selected by the user) and the currency_code segment is populated in the response. &#42;&#42;This metric cannot be segmented by customer_country_code.&#42;&#42; */
		orderedItemSalesMicros: Option[String] = None,
	  /** &#42;Deprecated&#42;: This field is no longer supported and retrieving it returns 0 starting from May 2024. Number of shipped items, reported on the shipment date. &#42;&#42;This metric cannot be segmented by customer_country_code.&#42;&#42; */
		shippedItems: Option[String] = None,
	  /** &#42;Deprecated&#42;: This field is no longer supported and retrieving it returns 0 starting from May 2024. Number of ordered items not shipped up until the end of the queried day. If a multi-day period is specified in the search query, the returned value is the average number of unshipped items over the days in the queried period. &#42;&#42;This metric cannot be segmented by customer_country_code.&#42;&#42; */
		unshippedItems: Option[BigDecimal] = None,
	  /** &#42;Deprecated&#42;: This field is no longer supported and retrieving it returns 0 starting from May 2024. Total price of shipped items in micros (1 millionth of a standard unit, 1 USD = 1000000 micros), reported on the order date. Excludes shipping and taxes (US only). The currency of the returned value is stored in the currency_code segment. If this metric is selected, 'segments.currency_code' is automatically added to the SELECT clause in the search query (unless it is explicitly selected by the user) and the currency_code segment is populated in the response. &#42;&#42;This metric cannot be segmented by customer_country_code.&#42;&#42; */
		shippedItemSalesMicros: Option[String] = None,
	  /** &#42;Deprecated&#42;: This field is no longer supported and retrieving it returns 0 starting from May 2024. Number of ordered items canceled by the merchant, reported on the order date. &#42;&#42;This metric cannot be segmented by customer_country_code.&#42;&#42; */
		rejectedItems: Option[String] = None,
	  /** &#42;Deprecated&#42;: This field is no longer supported and retrieving it returns 0 starting from May 2024. Average number of days between an item being ordered and the item being &#42;&#42;This metric cannot be segmented by customer_country_code.&#42;&#42; */
		itemDaysToShip: Option[BigDecimal] = None,
	  /** &#42;Deprecated&#42;: This field is no longer supported and retrieving it returns 0 starting from May 2024. Percentage of shipped items in relation to all finalized items (shipped or rejected by the merchant; unshipped items are not taken into account), reported on the order date. Item fill rate is lowered by merchant rejections. &#42;&#42;This metric cannot be segmented by customer_country_code.&#42;&#42; */
		itemFillRate: Option[BigDecimal] = None,
	  /** &#42;Deprecated&#42;: This field is no longer supported and retrieving it returns 0 starting from May 2024. Number of ordered items sent back for return, reported on the date when the merchant accepted the return. &#42;&#42;This metric cannot be segmented by customer_country_code.&#42;&#42; */
		returnedItems: Option[String] = None,
	  /** &#42;Deprecated&#42;: This field is no longer supported and retrieving it returns 0 starting from May 2024. Total price of ordered items sent back for return in micros (1 millionth of a standard unit, 1 USD = 1000000 micros), reported on the date when the merchant accepted the return. The currency of the returned value is stored in the currency_code segment. If this metric is selected, 'segments.currency_code' is automatically added to the SELECT clause in the search query (unless it is explicitly selected by the user) and the currency_code segment is populated in the response. &#42;&#42;This metric cannot be segmented by customer_country_code.&#42;&#42; */
		returnsMicros: Option[String] = None,
	  /** &#42;Deprecated&#42;: This field is no longer supported and retrieving it returns 0 starting from May 2024. Total price of returned items divided by the total price of shipped items, reported on the order date. If this metric is selected, 'segments.currency_code' is automatically added to the SELECT clause in the search query (unless it is explicitly selected by the user) and the currency_code segment is populated in the response. &#42;&#42;This metric cannot be segmented by customer_country_code.&#42;&#42; */
		returnRate: Option[BigDecimal] = None
	)
	
	object ProductView {
		enum ChannelEnum extends Enum[ChannelEnum] { case CHANNEL_UNSPECIFIED, LOCAL, ONLINE }
		enum AggregatedDestinationStatusEnum extends Enum[AggregatedDestinationStatusEnum] { case AGGREGATED_STATUS_UNSPECIFIED, NOT_ELIGIBLE_OR_DISAPPROVED, PENDING, ELIGIBLE_LIMITED, ELIGIBLE }
		enum ClickPotentialEnum extends Enum[ClickPotentialEnum] { case CLICK_POTENTIAL_UNSPECIFIED, LOW, MEDIUM, HIGH }
	}
	case class ProductView(
	  /** The REST ID of the product, in the form of channel:contentLanguage:targetCountry:offerId. Content API methods that operate on products take this as their productId parameter. Should always be included in the SELECT clause. */
		id: Option[String] = None,
	  /** Merchant-provided id of the product. */
		offerId: Option[String] = None,
	  /** Title of the product. */
		title: Option[String] = None,
	  /** Brand of the product. */
		brand: Option[String] = None,
	  /** First level of the product category in [Google's product taxonomy](https://support.google.com/merchants/answer/6324436). */
		categoryL1: Option[String] = None,
	  /** Second level of the product category in [Google's product taxonomy](https://support.google.com/merchants/answer/6324436). */
		categoryL2: Option[String] = None,
	  /** Third level of the product category in [Google's product taxonomy](https://support.google.com/merchants/answer/6324436). */
		categoryL3: Option[String] = None,
	  /** Fourth level of the product category in [Google's product taxonomy](https://support.google.com/merchants/answer/6324436). */
		categoryL4: Option[String] = None,
	  /** Fifth level of the product category in [Google's product taxonomy](https://support.google.com/merchants/answer/6324436). */
		categoryL5: Option[String] = None,
	  /** First level of the product type in merchant's own [product taxonomy](https://support.google.com/merchants/answer/6324436). */
		productTypeL1: Option[String] = None,
	  /** Second level of the product type in merchant's own [product taxonomy](https://support.google.com/merchants/answer/6324436). */
		productTypeL2: Option[String] = None,
	  /** Third level of the product type in merchant's own [product taxonomy](https://support.google.com/merchants/answer/6324436). */
		productTypeL3: Option[String] = None,
	  /** Fourth level of the product type in merchant's own [product taxonomy](https://support.google.com/merchants/answer/6324436). */
		productTypeL4: Option[String] = None,
	  /** Fifth level of the product type in merchant's own [product taxonomy](https://support.google.com/merchants/answer/6324436). */
		productTypeL5: Option[String] = None,
	  /** Product price currency code (for example, ISO 4217). Absent if product price is not available. */
		currencyCode: Option[String] = None,
	  /** Product price specified as micros (1 millionth of a standard unit, 1 USD = 1000000 micros) in the product currency. Absent in case the information about the price of the product is not available. */
		priceMicros: Option[String] = None,
	  /** Language code of the product in BCP 47 format. */
		languageCode: Option[String] = None,
	  /** Condition of the product. */
		condition: Option[String] = None,
	  /** Channel of the product (online versus local). */
		channel: Option[Schema.ProductView.ChannelEnum] = None,
	  /** Availability of the product. */
		availability: Option[String] = None,
	  /** The normalized shipping label specified in the feed */
		shippingLabel: Option[String] = None,
	  /** GTIN of the product. */
		gtin: Option[List[String]] = None,
	  /** Item group ID provided by the merchant for grouping variants together. */
		itemGroupId: Option[String] = None,
	  /** The time the merchant created the product in timestamp seconds. */
		creationTime: Option[String] = None,
	  /** Expiration date for the product. Specified on insertion. */
		expirationDate: Option[Schema.Date] = None,
	  /** Aggregated destination status. */
		aggregatedDestinationStatus: Option[Schema.ProductView.AggregatedDestinationStatusEnum] = None,
	  /** List of item issues for the product. */
		itemIssues: Option[List[Schema.ProductViewItemIssue]] = None,
	  /** Estimated performance potential compared to highest performing products of the merchant. */
		clickPotential: Option[Schema.ProductView.ClickPotentialEnum] = None,
	  /** Rank of the product based on its click potential. A product with `click_potential_rank` 1 has the highest click potential among the merchant's products that fulfill the search query conditions. */
		clickPotentialRank: Option[String] = None
	)
	
	object ProductViewItemIssue {
		enum ResolutionEnum extends Enum[ResolutionEnum] { case UNKNOWN, MERCHANT_ACTION, PENDING_PROCESSING }
	}
	case class ProductViewItemIssue(
	  /** Item issue type. */
		issueType: Option[Schema.ProductViewItemIssueItemIssueType] = None,
	  /** Item issue severity. */
		severity: Option[Schema.ProductViewItemIssueItemIssueSeverity] = None,
	  /** Item issue resolution. */
		resolution: Option[Schema.ProductViewItemIssue.ResolutionEnum] = None
	)
	
	case class ProductViewItemIssueItemIssueType(
	  /** Canonical attribute name for attribute-specific issues. */
		canonicalAttribute: Option[String] = None,
	  /** Error code of the issue. */
		code: Option[String] = None
	)
	
	object ProductViewItemIssueItemIssueSeverity {
		enum AggregatedSeverityEnum extends Enum[AggregatedSeverityEnum] { case AGGREGATED_ISSUE_SEVERITY_UNSPECIFIED, DISAPPROVED, DEMOTED, PENDING }
	}
	case class ProductViewItemIssueItemIssueSeverity(
	  /** Item issue severity for every destination. */
		severityPerDestination: Option[List[Schema.ProductViewItemIssueIssueSeverityPerDestination]] = None,
	  /** Severity of an issue aggregated for destination. */
		aggregatedSeverity: Option[Schema.ProductViewItemIssueItemIssueSeverity.AggregatedSeverityEnum] = None
	)
	
	case class ProductViewItemIssueIssueSeverityPerDestination(
	  /** Issue destination. */
		destination: Option[String] = None,
	  /** List of disapproved countries in the destination. */
		disapprovedCountries: Option[List[String]] = None,
	  /** List of demoted countries in the destination. */
		demotedCountries: Option[List[String]] = None
	)
	
	object ProductCluster {
		enum InventoryStatusEnum extends Enum[InventoryStatusEnum] { case INVENTORY_STATUS_UNSPECIFIED, IN_STOCK, OUT_OF_STOCK, NOT_IN_INVENTORY }
		enum BrandInventoryStatusEnum extends Enum[BrandInventoryStatusEnum] { case INVENTORY_STATUS_UNSPECIFIED, IN_STOCK, OUT_OF_STOCK, NOT_IN_INVENTORY }
	}
	case class ProductCluster(
	  /** Title of the product cluster. */
		title: Option[String] = None,
	  /** Brand of the product cluster. */
		brand: Option[String] = None,
	  /** Product category (1st level) of the product cluster, represented in Google's product taxonomy. */
		categoryL1: Option[String] = None,
	  /** Product category (2nd level) of the product cluster, represented in Google's product taxonomy. */
		categoryL2: Option[String] = None,
	  /** Product category (3rd level) of the product cluster, represented in Google's product taxonomy. */
		categoryL3: Option[String] = None,
	  /** Product category (4th level) of the product cluster, represented in Google's product taxonomy. */
		categoryL4: Option[String] = None,
	  /** Product category (5th level) of the product cluster, represented in Google's product taxonomy. */
		categoryL5: Option[String] = None,
	  /** GTINs of example variants of the product cluster. */
		variantGtins: Option[List[String]] = None,
	  /** Tells whether the product cluster is `IN_STOCK` in your product feed across multiple countries, `OUT_OF_STOCK` in your product feed, or `NOT_IN_INVENTORY` at all. The field doesn't take the Best Sellers report country filter into account. */
		inventoryStatus: Option[Schema.ProductCluster.InventoryStatusEnum] = None,
	  /** Tells if there is at least one product of the brand currently `IN_STOCK` in your product feed across multiple countries, all products are `OUT_OF_STOCK` in your product feed, or `NOT_IN_INVENTORY`. The field doesn't take the Best Sellers report country filter into account. */
		brandInventoryStatus: Option[Schema.ProductCluster.BrandInventoryStatusEnum] = None
	)
	
	case class Brand(
	  /** Name of the brand. */
		name: Option[String] = None
	)
	
	object BestSellers {
		enum ReportGranularityEnum extends Enum[ReportGranularityEnum] { case REPORT_GRANULARITY_UNSPECIFIED, WEEKLY, MONTHLY }
		enum RelativeDemandEnum extends Enum[RelativeDemandEnum] { case RELATIVE_DEMAND_UNSPECIFIED, VERY_LOW, LOW, MEDIUM, HIGH, VERY_HIGH }
		enum PreviousRelativeDemandEnum extends Enum[PreviousRelativeDemandEnum] { case RELATIVE_DEMAND_UNSPECIFIED, VERY_LOW, LOW, MEDIUM, HIGH, VERY_HIGH }
		enum RelativeDemandChangeEnum extends Enum[RelativeDemandChangeEnum] { case RELATIVE_DEMAND_CHANGE_TYPE_UNSPECIFIED, SINKER, FLAT, RISER }
	}
	case class BestSellers(
	  /** Report date. The value of this field can only be one of the following: &#42; The first day of the week (Monday) for weekly reports. &#42; The first day of the month for monthly reports. If a `WHERE` condition on `best_sellers.report_date` is not specified in the query, the latest available weekly or monthly report is returned. */
		reportDate: Option[Schema.Date] = None,
	  /** Granularity of the report. The ranking can be done over a week or a month timeframe. A `WHERE` condition on `best_sellers.report_granularity` is required in the query. */
		reportGranularity: Option[Schema.BestSellers.ReportGranularityEnum] = None,
	  /** Country where the ranking is calculated. A `WHERE` condition on `best_sellers.country_code` is required in the query. */
		countryCode: Option[String] = None,
	  /** Google product category ID to calculate the ranking for, represented in [Google's product taxonomy](https://support.google.com/merchants/answer/6324436). If a `WHERE` condition on `best_sellers.category_id` is not specified in the query, rankings for all top-level categories are returned. */
		categoryId: Option[String] = None,
	  /** Popularity on Shopping ads and free listings, in the selected category and country, based on the estimated number of units sold. */
		rank: Option[String] = None,
	  /** Popularity rank in the previous week or month. */
		previousRank: Option[String] = None,
	  /** Estimated demand in relation to the item with the highest popularity rank in the same category and country. */
		relativeDemand: Option[Schema.BestSellers.RelativeDemandEnum] = None,
	  /** Estimated demand in relation to the item with the highest popularity rank in the same category and country in the previous week or month. */
		previousRelativeDemand: Option[Schema.BestSellers.PreviousRelativeDemandEnum] = None,
	  /** Change in the estimated demand. Whether it rose, sank or remained flat. */
		relativeDemandChange: Option[Schema.BestSellers.RelativeDemandChangeEnum] = None
	)
	
	case class PriceCompetitiveness(
	  /** The country of the price benchmark (ISO 3166 code). */
		countryCode: Option[String] = None,
	  /** The latest available price benchmark in micros (1 millionth of a standard unit, 1 USD = 1000000 micros) for the product's catalog in the benchmark country. */
		benchmarkPriceMicros: Option[String] = None,
	  /** The price benchmark currency (ISO 4217 code). */
		benchmarkPriceCurrencyCode: Option[String] = None
	)
	
	object PriceInsights {
		enum EffectivenessEnum extends Enum[EffectivenessEnum] { case EFFECTIVENESS_UNSPECIFIED, LOW, MEDIUM, HIGH }
	}
	case class PriceInsights(
	  /** The latest suggested price in micros (1 millionth of a standard unit, 1 USD = 1000000 micros) for the product. */
		suggestedPriceMicros: Option[String] = None,
	  /** The suggested price currency (ISO 4217 code). */
		suggestedPriceCurrencyCode: Option[String] = None,
	  /** The predicted change in impressions as a fraction after introducing the suggested price compared to current active price. For example, 0.05 is a 5% predicted increase in impressions. */
		predictedImpressionsChangeFraction: Option[BigDecimal] = None,
	  /** The predicted change in clicks as a fraction after introducing the suggested price compared to current active price. For example, 0.05 is a 5% predicted increase in clicks. */
		predictedClicksChangeFraction: Option[BigDecimal] = None,
	  /** The predicted change in conversions as a fraction after introducing the suggested price compared to current active price. For example, 0.05 is a 5% predicted increase in conversions). */
		predictedConversionsChangeFraction: Option[BigDecimal] = None,
	  /** &#42;Deprecated&#42;: This field is no longer supported and will start returning 0. The predicted change in gross profit as a fraction after introducing the suggested price compared to current active price. For example, 0.05 is a 5% predicted increase in gross profit. */
		predictedGrossProfitChangeFraction: Option[BigDecimal] = None,
	  /** &#42;Deprecated&#42;: This field is no longer supported and will start returning 0. The predicted change in gross profit in micros (1 millionth of a standard unit, 1 USD = 1000000 micros) after introducing the suggested price for a month compared to current active price. */
		predictedMonthlyGrossProfitChangeMicros: Option[String] = None,
	  /** &#42;Deprecated&#42;: This field is no longer supported and will start returning USD for all requests. The predicted monthly gross profit change currency (ISO 4217 code). */
		predictedMonthlyGrossProfitChangeCurrencyCode: Option[String] = None,
	  /** The predicted effectiveness of applying the price suggestion, bucketed. */
		effectiveness: Option[Schema.PriceInsights.EffectivenessEnum] = None
	)
	
	object CompetitiveVisibility {
		enum TrafficSourceEnum extends Enum[TrafficSourceEnum] { case UNKNOWN, ORGANIC, ADS, ALL }
	}
	case class CompetitiveVisibility(
	  /** Date of this row. Available only in `CompetitiveVisibilityBenchmarkView` and `CompetitiveVisibilityCompetitorView`. Required in the `SELECT` clause for `CompetitiveVisibilityMarketBenchmarkView`. */
		date: Option[Schema.Date] = None,
	  /** Domain of your competitor or your domain, if 'is_your_domain' is true. Available only in `CompetitiveVisibilityTopMerchantView` and `CompetitiveVisibilityCompetitorView`. Required in the `SELECT` clause for `CompetitiveVisibilityTopMerchantView` and `CompetitiveVisibilityCompetitorView`. Cannot be filtered on in the 'WHERE' clause. */
		domain: Option[String] = None,
	  /** True if this row contains data for your domain. Available only in `CompetitiveVisibilityTopMerchantView` and `CompetitiveVisibilityCompetitorView`. Cannot be filtered on in the 'WHERE' clause. */
		isYourDomain: Option[Boolean] = None,
	  /** The country where impression appeared. Required in the `SELECT` clause. A `WHERE` condition on `competitive_visibility.country_code` is required in the query. */
		countryCode: Option[String] = None,
	  /** Google product category ID to calculate the report for, represented in [Google's product taxonomy](https://support.google.com/merchants/answer/6324436). Required in the `SELECT` clause. A `WHERE` condition on `competitive_visibility.category_id` is required in the query. */
		categoryId: Option[String] = None,
	  /** Type of impression listing. Required in the `SELECT` clause. Cannot be filtered on in the 'WHERE' clause. */
		trafficSource: Option[Schema.CompetitiveVisibility.TrafficSourceEnum] = None,
	  /** Position of the domain in the top merchants ranking for the selected keys (`date`, `category_id`, `country_code`, `listing_type`) based on impressions. 1 is the highest. Available only in `CompetitiveVisibilityTopMerchantView` and `CompetitiveVisibilityCompetitorView`. Cannot be filtered on in the 'WHERE' clause. */
		rank: Option[String] = None,
	  /** [Ads / organic ratio] (https://support.google.com/merchants/answer/11366442#zippy=%2Cadsfree-ratio) shows how often a merchant receives impressions from Shopping ads compared to organic traffic. The number is rounded and bucketed. Available only in `CompetitiveVisibilityTopMerchantView` and `CompetitiveVisibilityCompetitorView`. Cannot be filtered on in the 'WHERE' clause. */
		adsOrganicRatio: Option[BigDecimal] = None,
	  /** Page overlap rate describes how frequently competing retailers’ offers are shown together with your offers on the same page. Available only in `CompetitiveVisibilityTopMerchantView` and `CompetitiveVisibilityCompetitorView`. Cannot be filtered on in the 'WHERE' clause. */
		pageOverlapRate: Option[BigDecimal] = None,
	  /** Higher position rate shows how often a competitor’s offer got placed in a higher position on the page than your offer. Available only in `CompetitiveVisibilityTopMerchantView` and `CompetitiveVisibilityCompetitorView`. Cannot be filtered on in the 'WHERE' clause. */
		higherPositionRate: Option[BigDecimal] = None,
	  /** Relative visibility shows how often your competitors’ offers are shown compared to your offers. In other words, this is the number of displayed impressions of a competitor retailer divided by the number of your displayed impressions during a selected time range for a selected product category and country. Available only in `CompetitiveVisibilityCompetitorView`. Cannot be filtered on in the 'WHERE' clause. */
		relativeVisibility: Option[BigDecimal] = None,
	  /** Change in visibility based on impressions for your domain with respect to the start of the selected time range (or first day with non-zero impressions). Available only in `CompetitiveVisibilityBenchmarkView`. Cannot be filtered on in the 'WHERE' clause. */
		yourDomainVisibilityTrend: Option[BigDecimal] = None,
	  /** Change in visibility based on impressions with respect to the start of the selected time range (or first day with non-zero impressions) for a combined set of merchants with highest visibility approximating the market. Available only in `CompetitiveVisibilityBenchmarkView`. Cannot be filtered on in the 'WHERE' clause. */
		categoryBenchmarkVisibilityTrend: Option[BigDecimal] = None
	)
	
	case class TopicTrends(
	  /** Country trends are calculated for. Must be a two-letter country code (ISO 3166-1-alpha-2 code), for example, `“US”`. */
		customerCountryCode: Option[String] = None,
	  /** Google-provided topic trends are calculated for. Only top eight topics are returned. Topic is what shoppers are searching for on Google, grouped by the same concept. */
		topic: Option[String] = None,
	  /** Date the trend score was retrieved. */
		date: Option[Schema.Date] = None,
	  /** Daily search interest, normalized to the time and country to make comparisons easier, with 100 representing peak popularity (from 0 to 100) for the requested time period and location. */
		searchInterest: Option[BigDecimal] = None,
	  /** Search interest in the last 7 days, with the same normalization as search_interest. This field is only present for a past date. */
		last7DaysSearchInterest: Option[BigDecimal] = None,
	  /** Search interest in the last 30 days, with the same normalization as search_interest. This field is only present for a past date. */
		last30DaysSearchInterest: Option[BigDecimal] = None,
	  /** Search interest in the last 90 days, with the same normalization as search_interest. This field is only present for a past date. */
		last90DaysSearchInterest: Option[BigDecimal] = None,
	  /** Search interest in the last 120 days, with the same normalization as search_interest. This field is only present for a past date. */
		last120DaysSearchInterest: Option[BigDecimal] = None,
	  /** Estimated search interest in the next 7 days, with the same normalization as search_interest. This field is only present for a future date. */
		next7DaysSearchInterest: Option[BigDecimal] = None
	)
	
	object RenderAccountIssuesRequestPayload {
		enum ContentOptionEnum extends Enum[ContentOptionEnum] { case CONTENT_OPTION_UNSPECIFIED, PRE_RENDERED_HTML }
		enum UserInputActionOptionEnum extends Enum[UserInputActionOptionEnum] { case USER_INPUT_ACTION_RENDERING_OPTION_UNSPECIFIED, REDIRECT_TO_MERCHANT_CENTER, BUILT_IN_USER_INPUT_ACTIONS }
	}
	case class RenderAccountIssuesRequestPayload(
	  /** Optional. How the detailed content should be returned. Default option is to return the content as a pre-rendered HTML text. */
		contentOption: Option[Schema.RenderAccountIssuesRequestPayload.ContentOptionEnum] = None,
	  /** Optional. How actions with user input form should be handled. If not provided, actions will be returned as links that points merchant to Merchant Center where they can request the action. */
		userInputActionOption: Option[Schema.RenderAccountIssuesRequestPayload.UserInputActionOptionEnum] = None
	)
	
	case class RenderAccountIssuesResponse(
	  /** List of account issues for a given account. This list can be shown with compressed, expandable items. In the compressed form, the title and impact should be shown for each issue. Once the issue is expanded, the detailed content and available actions should be rendered. */
		issues: Option[List[Schema.AccountIssue]] = None,
	  /** The Alternate Dispute Resolution (ADR) contains a link to a page where merchant can bring their appeal to an [external body](https://support.google.com/european-union-digital-services-act-redress-options/answer/13535501). If the ADR is present, it MUST be available to the merchant on the page that shows the list with their account issues. */
		alternateDisputeResolution: Option[Schema.AlternateDisputeResolution] = None
	)
	
	case class AccountIssue(
	  /** Title of the issue. */
		title: Option[String] = None,
	  /** Clarifies the severity of the issue. The summarizing message, if present, should be shown right under the title for each issue. It helps merchants to quickly understand the impact of the issue. The detailed breakdown helps the merchant to fully understand the impact of the issue. It can be rendered as dialog that opens when the merchant mouse over the summarized impact statement. Issues with different severity can be styled differently. They may use a different color or icon to signal the difference between `ERROR`, `WARNING` and `INFO`. */
		impact: Option[Schema.AccountIssueImpact] = None,
	  /** Details of the issue as a pre-rendered HTML. HTML elements contain CSS classes that can be used to customize the style of the content. Always sanitize the HTML before embedding it directly to your application. The sanitizer needs to allow basic HTML tags, such as: `div`, `span`, `p`, `a`, `ul`, `li`, `table`, `tr`, `td`. For example, you can use [DOMPurify](https://www.npmjs.com/package/dompurify). CSS classes: &#42; `issue-detail` - top level container for the detail of the issue &#42; `callout-banners` - section of the `issue-detail` with callout banners &#42; `callout-banner` - single callout banner, inside `callout-banners` &#42; `callout-banner-info` - callout with important information (default) &#42; `callout-banner-warning` - callout with a warning &#42; `callout-banner-error` - callout informing about an error (most severe) &#42; `issue-content` - section of the `issue-detail`, contains multiple `content-element` &#42; `content-element` - content element such as a list, link or paragraph, inside `issue-content` &#42; `root-causes` - unordered list with items describing root causes of the issue, inside `issue-content` &#42; `root-causes-intro` - intro text before the `root-causes` list, inside `issue-content` &#42; `segment` - section of the text, `span` inside paragraph &#42; `segment-attribute` - section of the text that represents a product attribute, for example 'image\_link' &#42; `segment-literal` - section of the text that contains a special value, for example '0-1000 kg' &#42; `segment-bold` - section of the text that should be rendered as bold &#42; `segment-italic` - section of the text that should be rendered as italic &#42; `tooltip` - used on paragraphs that should be rendered with a tooltip. A section of the text in such a paragraph will have a class `tooltip-text` and is intended to be shown in a mouse over dialog. If the style is not used, the `tooltip-text` section would be shown on a new line, after the main part of the text. &#42; `tooltip-text` - marks a section of the text within a `tooltip`, that is intended to be shown in a mouse over dialog. &#42; `tooltip-icon` - marks a section of the text within a `tooltip`, that can be replaced with a tooltip icon, for example '?' or 'i'. By default, this section contains a `br` tag, that is separating the main text and the tooltip text when the style is not used. &#42; `tooltip-style-question` - the tooltip shows helpful information, can use the '?' as an icon. &#42; `tooltip-style-info` - the tooltip adds additional information fitting to the context, can use the 'i' as an icon. &#42; `content-moderation` - marks the paragraph that explains how the issue was identified. &#42; `new-element` - Present for new elements added to the pre-rendered content in the future. To make sure that a new content element does not break your style, you can hide everything with this class. */
		prerenderedContent: Option[String] = None,
	  /** A list of actionable steps that can be executed to solve the issue. An example is requesting a re-review or providing arguments when merchant disagrees with the issue. Actions that are supported in (your) third-party application can be rendered as buttons and should be available to merchant when they expand the issue. */
		actions: Option[List[Schema.Action]] = None
	)
	
	object AccountIssueImpact {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, ERROR, WARNING, INFO }
	}
	case class AccountIssueImpact(
	  /** Optional. Message summarizing the overall impact of the issue. If present, it should be rendered to the merchant. For example: "Disapproves 90k offers in 25 countries" */
		message: Option[String] = None,
	  /** The severity of the issue. */
		severity: Option[Schema.AccountIssueImpact.SeverityEnum] = None,
	  /** Detailed impact breakdown. Explains the types of restriction the issue has in different shopping destinations and territory. If present, it should be rendered to the merchant. Can be shown as a mouse over dropdown or a dialog. Each breakdown item represents a group of regions with the same impact details. */
		breakdowns: Option[List[Schema.Breakdown]] = None
	)
	
	case class Breakdown(
	  /** Lists of regions. Should be rendered as a title for this group of details. The full list should be shown to merchant. If the list is too long, it is recommended to make it expandable. */
		regions: Option[List[Schema.BreakdownRegion]] = None,
	  /** Human readable, localized description of issue's effect on different targets. Should be rendered as a list. For example: &#42; "Products not showing in ads" &#42; "Products not showing organically" */
		details: Option[List[String]] = None
	)
	
	case class BreakdownRegion(
	  /** The [CLDR territory code] (http://www.unicode.org/repos/cldr/tags/latest/common/main/en.xml) */
		code: Option[String] = None,
	  /** The localized name of the region. For region with code='001' the value is 'All countries' or the equivalent in other languages. */
		name: Option[String] = None
	)
	
	case class Action(
	  /** Action implemented and performed in (your) third-party application. The application should point the merchant to the place, where they can access the corresponding functionality or provide instructions, if the specific functionality is not available. */
		builtinSimpleAction: Option[Schema.BuiltInSimpleAction] = None,
	  /** Action that is implemented and performed outside of (your) third-party application. The application needs to redirect the merchant to the external location where they can perform the action. */
		externalAction: Option[Schema.ExternalAction] = None,
	  /** Action implemented and performed in (your) third-party application. The application needs to show an additional content and input form to the merchant as specified for given action. They can trigger the action only when they provided all required inputs. */
		builtinUserInputAction: Option[Schema.BuiltInUserInputAction] = None,
	  /** Label of the action button. */
		buttonLabel: Option[String] = None,
	  /** Controlling whether the button is active or disabled. The value is 'false' when the action was already requested or is not available. If the action is not available then a reason will be present. If (your) third-party application shows a disabled button for action that is not available, then it should also show reasons. */
		isAvailable: Option[Boolean] = None,
	  /** List of reasons why the action is not available. The list of reasons is empty if the action is available. If there is only one reason, it can be displayed next to the disabled button. If there are more reasons, all of them should be displayed, for example in a pop-up dialog. */
		reasons: Option[List[Schema.ActionReason]] = None
	)
	
	object BuiltInSimpleAction {
		enum TypeEnum extends Enum[TypeEnum] { case BUILT_IN_SIMPLE_ACTION_TYPE_UNSPECIFIED, VERIFY_PHONE, CLAIM_WEBSITE, ADD_PRODUCTS, ADD_CONTACT_INFO, LINK_ADS_ACCOUNT, ADD_BUSINESS_REGISTRATION_NUMBER, EDIT_ITEM_ATTRIBUTE, FIX_ACCOUNT_ISSUE, SHOW_ADDITIONAL_CONTENT }
	}
	case class BuiltInSimpleAction(
	  /** The type of action that represents a functionality that is expected to be available in third-party application. */
		`type`: Option[Schema.BuiltInSimpleAction.TypeEnum] = None,
	  /** The attribute that needs to be updated. Present when the type is `EDIT_ITEM_ATTRIBUTE`. This field contains a code for attribute, represented in snake_case. You can find a list of product's attributes, with their codes [here](https://support.google.com/merchants/answer/7052112). */
		attributeCode: Option[String] = None,
	  /** Long text from an external source that should be available to the merchant. Present when the type is `SHOW_ADDITIONAL_CONTENT`. */
		additionalContent: Option[Schema.BuiltInSimpleActionAdditionalContent] = None
	)
	
	case class BuiltInSimpleActionAdditionalContent(
	  /** Title of the additional content; */
		title: Option[String] = None,
	  /** Long text organized into paragraphs. */
		paragraphs: Option[List[String]] = None
	)
	
	object ExternalAction {
		enum TypeEnum extends Enum[TypeEnum] { case EXTERNAL_ACTION_TYPE_UNSPECIFIED, REVIEW_PRODUCT_ISSUE_IN_MERCHANT_CENTER, REVIEW_ACCOUNT_ISSUE_IN_MERCHANT_CENTER, LEGAL_APPEAL_IN_HELP_CENTER, VERIFY_IDENTITY_IN_MERCHANT_CENTER }
	}
	case class ExternalAction(
	  /** The type of external action. */
		`type`: Option[Schema.ExternalAction.TypeEnum] = None,
	  /** URL to external system, for example Merchant Center, where the merchant can perform the action. */
		uri: Option[String] = None
	)
	
	case class BuiltInUserInputAction(
	  /** Internal details. Not for display but need to be sent back when triggering the action. */
		actionContext: Option[String] = None,
	  /** Actions may provide multiple different flows. Merchant selects one that fits best to their intent. Selecting the flow is the first step in user's interaction with the action. It affects what input fields will be available and required and also how the request will be processed. */
		flows: Option[List[Schema.ActionFlow]] = None
	)
	
	case class ActionFlow(
	  /** Not for display but need to be sent back for the selected action flow. */
		id: Option[String] = None,
	  /** Text value describing the intent for the action flow. It can be used as an input label if merchant needs to pick one of multiple flows. For example: "I disagree with the issue" */
		label: Option[String] = None,
	  /** A list of input fields. */
		inputs: Option[List[Schema.InputField]] = None,
	  /** Title of the request dialog. For example: "Before you request a review" */
		dialogTitle: Option[String] = None,
	  /** Message displayed in the request dialog. For example: "Make sure you've fixed all your country-specific issues. If not, you may have to wait 7 days to request another review". There may be an more information to be shown in a tooltip. */
		dialogMessage: Option[Schema.TextWithTooltip] = None,
	  /** Important message to be highlighted in the request dialog. For example: "You can only request a review for disagreeing with this issue once. If it's not approved, you'll need to fix the issue and wait a few days before you can request another review." */
		dialogCallout: Option[Schema.Callout] = None,
	  /** Label for the button to trigger the action from the action dialog. For example: "Request review" */
		dialogButtonLabel: Option[String] = None
	)
	
	case class InputField(
	  /** Not for display but need to be sent back for the given input field. */
		id: Option[String] = None,
	  /** Input field label. There may be more information to be shown in a tooltip. */
		label: Option[Schema.TextWithTooltip] = None,
	  /** Whether the field is required. The action button needs to stay disabled till values for all required fields are provided. */
		required: Option[Boolean] = None,
	  /** Input field to provide text information. Corresponds to the [html input type=text](https://www.w3.org/TR/2012/WD-html-markup-20121025/input.text.html#input.text) or [html textarea](https://www.w3.org/TR/2012/WD-html-markup-20121025/textarea.html#textarea). */
		textInput: Option[Schema.InputFieldTextInput] = None,
	  /** Input field to select one of the offered choices. Corresponds to the [html input type=radio](https://www.w3.org/TR/2012/WD-html-markup-20121025/input.radio.html#input.radio). */
		choiceInput: Option[Schema.InputFieldChoiceInput] = None,
	  /** Input field to provide a boolean value. Corresponds to the [html input type=checkbox](https://www.w3.org/TR/2012/WD-html-markup-20121025/input.checkbox.html#input.checkbox). */
		checkboxInput: Option[Schema.InputFieldCheckboxInput] = None
	)
	
	object TextWithTooltip {
		enum TooltipIconStyleEnum extends Enum[TooltipIconStyleEnum] { case TOOLTIP_ICON_STYLE_UNSPECIFIED, INFO, QUESTION }
	}
	case class TextWithTooltip(
	  /** Value of the message as a simple text. */
		simpleValue: Option[String] = None,
	  /** Value of the tooltip as a simple text. */
		simpleTooltipValue: Option[String] = None,
	  /** The suggested type of an icon for tooltip, if a tooltip is present. */
		tooltipIconStyle: Option[Schema.TextWithTooltip.TooltipIconStyleEnum] = None
	)
	
	object InputFieldTextInput {
		enum TypeEnum extends Enum[TypeEnum] { case TEXT_INPUT_TYPE_UNSPECIFIED, GENERIC_SHORT_TEXT, GENERIC_LONG_TEXT }
	}
	case class InputFieldTextInput(
	  /** Type of the text input */
		`type`: Option[Schema.InputFieldTextInput.TypeEnum] = None,
	  /** Additional info regarding the field to be displayed to merchant. For example, warning to not include personal identifiable information. There may be more information to be shown in a tooltip. */
		additionalInfo: Option[Schema.TextWithTooltip] = None,
	  /** Information about the required format. If present, it should be shown close to the input field to help merchants to provide a correct value. For example: "VAT numbers should be in a format similar to SK9999999999" */
		formatInfo: Option[String] = None,
	  /** Text to be used as the [aria-label](https://www.w3.org/TR/WCAG20-TECHS/ARIA14.html) for the input. */
		ariaLabel: Option[String] = None
	)
	
	case class InputFieldChoiceInput(
	  /** A list of choices. Only one option can be selected. */
		options: Option[List[Schema.InputFieldChoiceInputChoiceInputOption]] = None
	)
	
	case class InputFieldChoiceInputChoiceInputOption(
	  /** Not for display but need to be sent back for the selected choice option. */
		id: Option[String] = None,
	  /** Short description of the choice option. There may be more information to be shown as a tooltip. */
		label: Option[Schema.TextWithTooltip] = None,
	  /** Input that should be displayed when this option is selected. The additional input will not contain a `ChoiceInput`. */
		additionalInput: Option[Schema.InputField] = None
	)
	
	case class InputFieldCheckboxInput(
	
	)
	
	object Callout {
		enum StyleHintEnum extends Enum[StyleHintEnum] { case CALLOUT_STYLE_HINT_UNSPECIFIED, ERROR, WARNING, INFO }
	}
	case class Callout(
	  /** Can be used to render messages with different severity in different styles. Snippets off all types contain important information that should be displayed to merchants. */
		styleHint: Option[Schema.Callout.StyleHintEnum] = None,
	  /** A full message that needs to be shown to the merchant. */
		fullMessage: Option[Schema.TextWithTooltip] = None
	)
	
	case class ActionReason(
	  /** Messages summarizing the reason, why the action is not available. For example: "Review requested on Jan 03. Review requests can take a few days to complete." */
		message: Option[String] = None,
	  /** Detailed explanation of the reason. Should be displayed as a hint if present. */
		detail: Option[String] = None,
	  /** Optional. An action that needs to be performed to solve the problem represented by this reason. This action will always be available. Should be rendered as a link or button next to the summarizing message. For example, the review may be available only once merchant configure all required attributes. In such a situation this action can be a link to the form, where they can fill the missing attribute to unblock the main action. */
		action: Option[Schema.Action] = None
	)
	
	case class AlternateDisputeResolution(
	  /** The URL pointing to a page, where merchant can request alternative dispute resolution with an [external body](https://support.google.com/european-union-digital-services-act-redress-options/answer/13535501). */
		uri: Option[String] = None,
	  /** The label for the alternate dispute resolution link. */
		label: Option[String] = None
	)
	
	object RenderProductIssuesRequestPayload {
		enum ContentOptionEnum extends Enum[ContentOptionEnum] { case CONTENT_OPTION_UNSPECIFIED, PRE_RENDERED_HTML }
		enum UserInputActionOptionEnum extends Enum[UserInputActionOptionEnum] { case USER_INPUT_ACTION_RENDERING_OPTION_UNSPECIFIED, REDIRECT_TO_MERCHANT_CENTER, BUILT_IN_USER_INPUT_ACTIONS }
	}
	case class RenderProductIssuesRequestPayload(
	  /** Optional. How the detailed content should be returned. Default option is to return the content as a pre-rendered HTML text. */
		contentOption: Option[Schema.RenderProductIssuesRequestPayload.ContentOptionEnum] = None,
	  /** Optional. How actions with user input form should be handled. If not provided, actions will be returned as links that points merchant to Merchant Center where they can request the action. */
		userInputActionOption: Option[Schema.RenderProductIssuesRequestPayload.UserInputActionOptionEnum] = None
	)
	
	case class RenderProductIssuesResponse(
	  /** List of issues for a given product. This list can be shown with compressed, expandable items. In the compressed form, the title and impact should be shown for each issue. Once the issue is expanded, the detailed content and available actions should be rendered. */
		issues: Option[List[Schema.ProductIssue]] = None,
	  /** The Alternate Dispute Resolution (ADR) contains a link to a page where merchant can bring their appeal to an [external body](https://support.google.com/european-union-digital-services-act-redress-options/answer/13535501). If present, the link should be shown on the same page as the list of issues. */
		alternateDisputeResolution: Option[Schema.AlternateDisputeResolution] = None
	)
	
	case class ProductIssue(
	  /** Title of the issue. */
		title: Option[String] = None,
	  /** Clarifies the severity of the issue. The summarizing message, if present, should be shown right under the title for each issue. It helps merchants to quickly understand the impact of the issue. The detailed breakdown helps the merchant to fully understand the impact of the issue. It can be rendered as dialog that opens when the merchant mouse over the summarized impact statement. Issues with different severity can be styled differently. They may use a different color or icon to signal the difference between `ERROR`, `WARNING` and `INFO`. */
		impact: Option[Schema.ProductIssueImpact] = None,
	  /** Details of the issue as a pre-rendered HTML. HTML elements contain CSS classes that can be used to customize the style of the content. Always sanitize the HTML before embedding it directly to your application. The sanitizer needs to allow basic HTML tags, such as: `div`, `span`, `p`, `a`, `ul`, `li`, `table`, `tr`, `td`. For example, you can use [DOMPurify](https://www.npmjs.com/package/dompurify). CSS classes: &#42; `issue-detail` - top level container for the detail of the issue &#42; `callout-banners` - section of the `issue-detail` with callout banners &#42; `callout-banner` - single callout banner, inside `callout-banners` &#42; `callout-banner-info` - callout with important information (default) &#42; `callout-banner-warning` - callout with a warning &#42; `callout-banner-error` - callout informing about an error (most severe) &#42; `issue-content` - section of the `issue-detail`, contains multiple `content-element` &#42; `content-element` - content element such as a list, link or paragraph, inside `issue-content` &#42; `root-causes` - unordered list with items describing root causes of the issue, inside `issue-content` &#42; `root-causes-intro` - intro text before the `root-causes` list, inside `issue-content` &#42; `segment` - section of the text, `span` inside paragraph &#42; `segment-attribute` - section of the text that represents a product attribute, for example 'image\_link' &#42; `segment-literal` - section of the text that contains a special value, for example '0-1000 kg' &#42; `segment-bold` - section of the text that should be rendered as bold &#42; `segment-italic` - section of the text that should be rendered as italic &#42; `tooltip` - used on paragraphs that should be rendered with a tooltip. A section of the text in such a paragraph will have a class `tooltip-text` and is intended to be shown in a mouse over dialog. If the style is not used, the `tooltip-text` section would be shown on a new line, after the main part of the text. &#42; `tooltip-text` - marks a section of the text within a `tooltip`, that is intended to be shown in a mouse over dialog. &#42; `tooltip-icon` - marks a section of the text within a `tooltip`, that can be replaced with a tooltip icon, for example '?' or 'i'. By default, this section contains a `br` tag, that is separating the main text and the tooltip text when the style is not used. &#42; `tooltip-style-question` - the tooltip shows helpful information, can use the '?' as an icon. &#42; `tooltip-style-info` - the tooltip adds additional information fitting to the context, can use the 'i' as an icon. &#42; `content-moderation` - marks the paragraph that explains how the issue was identified. &#42; `list-intro` - marks the paragraph that contains an intro for a list. This paragraph will be always followed by a list. &#42; `new-element` - Present for new elements added to the pre-rendered content in the future. To make sure that a new content element does not break your style, you can hide everything with this class. */
		prerenderedContent: Option[String] = None,
	  /** A list of actionable steps that can be executed to solve the issue. An example is requesting a re-review or providing arguments when merchant disagrees with the issue. Actions that are supported in (your) third-party application can be rendered as buttons and should be available to merchant when they expand the issue. */
		actions: Option[List[Schema.Action]] = None
	)
	
	object ProductIssueImpact {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, ERROR, WARNING, INFO }
	}
	case class ProductIssueImpact(
	  /** Optional. Message summarizing the overall impact of the issue. If present, it should be rendered to the merchant. For example: "Limits visibility in France" */
		message: Option[String] = None,
	  /** The severity of the issue. */
		severity: Option[Schema.ProductIssueImpact.SeverityEnum] = None,
	  /** Detailed impact breakdown. Explains the types of restriction the issue has in different shopping destinations and territory. If present, it should be rendered to the merchant. Can be shown as a mouse over dropdown or a dialog. Each breakdown item represents a group of regions with the same impact details. */
		breakdowns: Option[List[Schema.Breakdown]] = None
	)
	
	case class TriggerActionPayload(
	  /** Required. The context from the selected action. The value is obtained from rendered issues and needs to be sent back to identify the action that is being triggered. */
		actionContext: Option[String] = None,
	  /** Required. Input provided by the merchant. */
		actionInput: Option[Schema.ActionInput] = None
	)
	
	case class ActionInput(
	  /** Required. Id of the selected action flow. */
		actionFlowId: Option[String] = None,
	  /** Required. Values for input fields. */
		inputValues: Option[List[Schema.InputValue]] = None
	)
	
	case class InputValue(
	  /** Required. Id of the corresponding input field. */
		inputFieldId: Option[String] = None,
	  /** Value for text input field. */
		textInputValue: Option[Schema.InputValueTextInputValue] = None,
	  /** Value for choice input field. */
		choiceInputValue: Option[Schema.InputValueChoiceInputValue] = None,
	  /** Value for checkbox input field. */
		checkboxInputValue: Option[Schema.InputValueCheckboxInputValue] = None
	)
	
	case class InputValueTextInputValue(
	  /** Required. Text provided by the merchant. */
		value: Option[String] = None
	)
	
	case class InputValueChoiceInputValue(
	  /** Required. Id of the option that was selected by the merchant. */
		choiceInputOptionId: Option[String] = None
	)
	
	case class InputValueCheckboxInputValue(
	  /** Required. True if the merchant checked the box field. False otherwise. */
		value: Option[Boolean] = None
	)
	
	case class TriggerActionResponse(
	  /** The message for merchant. */
		message: Option[String] = None
	)
	
	case class Region(
	  /** Output only. Immutable. The ID uniquely identifying each region. */
		regionId: Option[String] = None,
	  /** Output only. Immutable. Merchant that owns the region. */
		merchantId: Option[String] = None,
	  /** The display name of the region. */
		displayName: Option[String] = None,
	  /** A list of postal codes that defines the region area. */
		postalCodeArea: Option[Schema.RegionPostalCodeArea] = None,
	  /** A list of geotargets that defines the region area. */
		geotargetArea: Option[Schema.RegionGeoTargetArea] = None,
	  /** Output only. Indicates if the region is eligible to use in the Regional Inventory configuration. */
		regionalInventoryEligible: Option[Boolean] = None,
	  /** Output only. Indicates if the region is eligible to use in the Shipping Services configuration. */
		shippingEligible: Option[Boolean] = None
	)
	
	case class RegionPostalCodeArea(
	  /** Required. CLDR territory code or the country the postal code group applies to. */
		regionCode: Option[String] = None,
	  /** Required. A range of postal codes. */
		postalCodes: Option[List[Schema.RegionPostalCodeAreaPostalCodeRange]] = None
	)
	
	case class RegionPostalCodeAreaPostalCodeRange(
	  /** Required. A postal code or a pattern of the form prefix&#42; denoting the inclusive lower bound of the range defining the area. Examples values: "94108", "9410&#42;", "9&#42;". */
		begin: Option[String] = None,
	  /** Optional. A postal code or a pattern of the form prefix&#42; denoting the inclusive upper bound of the range defining the area. It must have the same length as postalCodeRangeBegin: if postalCodeRangeBegin is a postal code then postalCodeRangeEnd must be a postal code too; if postalCodeRangeBegin is a pattern then postalCodeRangeEnd must be a pattern with the same prefix length. Optional: if not set, then the area is defined as being all the postal codes matching postalCodeRangeBegin. */
		end: Option[String] = None
	)
	
	case class RegionGeoTargetArea(
	  /** Required. A non-empty list of [location IDs](https://developers.google.com/adwords/api/docs/appendix/geotargeting). They must all be of the same location type (e.g., state). */
		geotargetCriteriaIds: Option[List[String]] = None
	)
	
	case class ListRegionsResponse(
	  /** The regions from the specified merchant. */
		regions: Option[List[Schema.Region]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object Promotion {
		enum ProductApplicabilityEnum extends Enum[ProductApplicabilityEnum] { case PRODUCT_APPLICABILITY_UNSPECIFIED, ALL_PRODUCTS, SPECIFIC_PRODUCTS }
		enum OfferTypeEnum extends Enum[OfferTypeEnum] { case OFFER_TYPE_UNSPECIFIED, NO_CODE, GENERIC_CODE }
		enum RedemptionChannelEnum extends Enum[RedemptionChannelEnum] { case REDEMPTION_CHANNEL_UNSPECIFIED, IN_STORE, ONLINE }
		enum CouponValueTypeEnum extends Enum[CouponValueTypeEnum] { case COUPON_VALUE_TYPE_UNSPECIFIED, MONEY_OFF, PERCENT_OFF, BUY_M_GET_N_MONEY_OFF, BUY_M_GET_N_PERCENT_OFF, BUY_M_GET_MONEY_OFF, BUY_M_GET_PERCENT_OFF, FREE_GIFT, FREE_GIFT_WITH_VALUE, FREE_GIFT_WITH_ITEM_ID, FREE_SHIPPING_STANDARD, FREE_SHIPPING_OVERNIGHT, FREE_SHIPPING_TWO_DAY }
		enum StoreApplicabilityEnum extends Enum[StoreApplicabilityEnum] { case STORE_APPLICABILITY_UNSPECIFIED, ALL_STORES, SPECIFIC_STORES }
	}
	case class Promotion(
	  /** Output only. The REST promotion ID to uniquely identify the promotion. Content API methods that operate on promotions take this as their `promotionId` parameter. The REST ID for a promotion is of the form channel:contentLanguage:targetCountry:promotionId The `channel` field has a value of `"online"`, `"in_store"`, or `"online_in_store"`. */
		id: Option[String] = None,
	  /** Required. The target country used as part of the unique identifier. Can be `AU`, `CA`, `DE`, `FR`, `GB`, `IN`, `US`, `BR`, `ES`, `NL`, `JP`, `IT` or `KR`. */
		targetCountry: Option[String] = None,
	  /** Required. The content language used as part of the unique identifier. `en` content language is available for all target countries. `fr` content language is available for `CA` and `FR` target countries. `de` content language is available for `DE` target country. `nl` content language is available for `NL` target country. `it` content language is available for `IT` target country. `pt` content language is available for `BR` target country. `ja` content language is available for `JP` target country. `ko` content language is available for `KR` target country. */
		contentLanguage: Option[String] = None,
	  /** Required. The user provided promotion ID to uniquely identify the promotion. */
		promotionId: Option[String] = None,
	  /** Required. Applicability of the promotion to either all products or only specific products. */
		productApplicability: Option[Schema.Promotion.ProductApplicabilityEnum] = None,
	  /** Required. Type of the promotion. */
		offerType: Option[Schema.Promotion.OfferTypeEnum] = None,
	  /** Generic redemption code for the promotion. To be used with the `offerType` field. */
		genericRedemptionCode: Option[String] = None,
	  /** Required. Long title for the promotion. */
		longTitle: Option[String] = None,
	  /** String representation of the promotion effective dates. Deprecated. Use `promotion_effective_time_period` instead. */
		promotionEffectiveDates: Option[String] = None,
	  /** String representation of the promotion display dates. Deprecated. Use `promotion_display_time_period` instead. */
		promotionDisplayDates: Option[String] = None,
	  /** Required. Redemption channel for the promotion. At least one channel is required. */
		redemptionChannel: Option[List[Schema.Promotion.RedemptionChannelEnum]] = None,
	  /** Required. Coupon value type for the promotion. */
		couponValueType: Option[Schema.Promotion.CouponValueTypeEnum] = None,
	  /** Destination ID for the promotion. */
		promotionDestinationIds: Option[List[String]] = None,
	  /** Product filter by item ID for the promotion. */
		itemId: Option[List[String]] = None,
	  /** Product filter by brand for the promotion. */
		brand: Option[List[String]] = None,
	  /** Product filter by item group ID for the promotion. */
		itemGroupId: Option[List[String]] = None,
	  /** Product filter by product type for the promotion. */
		productType: Option[List[String]] = None,
	  /** Product filter by item ID exclusion for the promotion. */
		itemIdExclusion: Option[List[String]] = None,
	  /** Product filter by brand exclusion for the promotion. */
		brandExclusion: Option[List[String]] = None,
	  /** Product filter by item group ID exclusion for the promotion. */
		itemGroupIdExclusion: Option[List[String]] = None,
	  /** Product filter by product type exclusion for the promotion. */
		productTypeExclusion: Option[List[String]] = None,
	  /** Minimum purchase amount for the promotion. */
		minimumPurchaseAmount: Option[Schema.PriceAmount] = None,
	  /** Minimum purchase quantity for the promotion. */
		minimumPurchaseQuantity: Option[Int] = None,
	  /** Maximum purchase quantity for the promotion. */
		limitQuantity: Option[Int] = None,
	  /** Maximum purchase value for the promotion. */
		limitValue: Option[Schema.PriceAmount] = None,
	  /** The percentage discount offered in the promotion. */
		percentOff: Option[Int] = None,
	  /** The money off amount offered in the promotion. */
		moneyOffAmount: Option[Schema.PriceAmount] = None,
	  /** The number of items discounted in the promotion. */
		getThisQuantityDiscounted: Option[Int] = None,
	  /** Free gift value for the promotion. */
		freeGiftValue: Option[Schema.PriceAmount] = None,
	  /** Free gift description for the promotion. */
		freeGiftDescription: Option[String] = None,
	  /** Free gift item ID for the promotion. */
		freeGiftItemId: Option[String] = None,
	  /** Shipping service names for the promotion. */
		shippingServiceNames: Option[List[String]] = None,
	  /** Cost cap for the promotion. */
		moneyBudget: Option[Schema.PriceAmount] = None,
	  /** Order limit for the promotion. */
		orderLimit: Option[Int] = None,
	  /** Required. `TimePeriod` representation of the promotion's effective dates. */
		promotionEffectiveTimePeriod: Option[Schema.TimePeriod] = None,
	  /** `TimePeriod` representation of the promotion's display dates. */
		promotionDisplayTimePeriod: Option[Schema.TimePeriod] = None,
	  /** Whether the promotion applies to all stores, or only specified stores. Local Inventory ads promotions throw an error if no store applicability is included. An INVALID_ARGUMENT error is thrown if store_applicability is set to ALL_STORES and store_code or score_code_exclusion is set to a value. */
		storeApplicability: Option[Schema.Promotion.StoreApplicabilityEnum] = None,
	  /** Store codes to include for the promotion. */
		storeCode: Option[List[String]] = None,
	  /** Store codes to exclude for the promotion. */
		storeCodeExclusion: Option[List[String]] = None,
	  /** URL to the page on the merchant's site where the promotion shows. Local Inventory ads promotions throw an error if no promo url is included. URL is used to confirm that the promotion is valid and can be redeemed. */
		promotionUrl: Option[String] = None,
	  /** Output only. The current status of the promotion. */
		promotionStatus: Option[Schema.PromotionPromotionStatus] = None
	)
	
	case class PriceAmount(
	  /** The price represented as a number. */
		value: Option[String] = None,
	  /** The currency of the price. */
		currency: Option[String] = None
	)
	
	case class TimePeriod(
	  /** The starting timestamp. */
		startTime: Option[String] = None,
	  /** The ending timestamp. */
		endTime: Option[String] = None
	)
	
	case class PromotionPromotionStatus(
	  /** The intended destinations for the promotion. */
		destinationStatuses: Option[List[Schema.PromotionPromotionStatusDestinationStatus]] = None,
	  /** A list of issues associated with the promotion. */
		promotionIssue: Option[List[Schema.PromotionPromotionStatusPromotionIssue]] = None,
	  /** Date on which the promotion has been created in [ISO 8601](http://en.wikipedia.org/wiki/ISO_8601) format: Date, time, and offset, for example "2020-01-02T09:00:00+01:00" or "2020-01-02T09:00:00Z" */
		creationDate: Option[String] = None,
	  /** Date on which the promotion status has been last updated in [ISO 8601](http://en.wikipedia.org/wiki/ISO_8601) format: Date, time, and offset, for example "2020-01-02T09:00:00+01:00" or "2020-01-02T09:00:00Z" */
		lastUpdateDate: Option[String] = None
	)
	
	object PromotionPromotionStatusDestinationStatus {
		enum StatusEnum extends Enum[StatusEnum] { case STATE_UNSPECIFIED, IN_REVIEW, REJECTED, LIVE, STOPPED, EXPIRED, PENDING }
	}
	case class PromotionPromotionStatusDestinationStatus(
	  /** The name of the destination. */
		destination: Option[String] = None,
	  /** The status for the specified destination. */
		status: Option[Schema.PromotionPromotionStatusDestinationStatus.StatusEnum] = None
	)
	
	case class PromotionPromotionStatusPromotionIssue(
	  /** Code of the issue. */
		code: Option[String] = None,
	  /** Explanation of the issue. */
		detail: Option[String] = None
	)
	
	case class ListPromotionResponse(
	  /** List of all available promotions for the merchant. */
		promotions: Option[List[Schema.Promotion]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GenerateRecommendationsResponse(
	  /** Recommendations generated for a request. */
		recommendations: Option[List[Schema.Recommendation]] = None,
	  /** Output only. Response token is a string created for each `GenerateRecommendationsResponse`. This token doesn't expire, and is globally unique. This token must be used when reporting interactions for recommendations. */
		responseToken: Option[String] = None
	)
	
	case class Recommendation(
	  /** Output only. Type of the recommendation. List of currently available recommendation types: - OPPORTUNITY_CREATE_NEW_COLLECTION - OPPORTUNITY_CREATE_EMAIL_CAMPAIGN */
		`type`: Option[String] = None,
	  /** Optional. Subtype of the recommendations. Only applicable when multiple recommendations can be generated per type, and is used as an identifier of recommendation under the same recommendation type. */
		subType: Option[String] = None,
	  /** Optional. Localized recommendation name. The localization uses the {@link `GenerateRecommendationsRequest.language_code`} field in {@link `GenerateRecommendationsRequest`} requests. */
		recommendationName: Option[String] = None,
	  /** Optional. Localized Recommendation Title. Localization uses the {@link `GenerateRecommendationsRequest.language_code`} field in {@link `GenerateRecommendationsRequest`} requests. */
		title: Option[String] = None,
	  /** Optional. Localized recommendation description. The localization the {@link `GenerateRecommendationsRequest.language_code`} field in {@link `GenerateRecommendationsRequest`} requests. */
		defaultDescription: Option[String] = None,
	  /** Output only. List of additional localized descriptions for a recommendation. Localication uses the `languageCode` field in `GenerateRecommendations` requests. Not all description types are guaranteed to be present and we recommend to rely on default description. */
		additionalDescriptions: Option[List[Schema.RecommendationDescription]] = None,
	  /** Optional. Default CTA of the recommendation. */
		defaultCallToAction: Option[Schema.RecommendationCallToAction] = None,
	  /** Output only. CTAs of this recommendation. Repeated. */
		additionalCallToAction: Option[List[Schema.RecommendationCallToAction]] = None,
	  /** Optional. Indicates whether a user needs to pay when they complete the user journey suggested by the recommendation. */
		paid: Option[Boolean] = None,
	  /** Output only. Any creatives attached to the recommendation. Repeated. */
		creative: Option[List[Schema.RecommendationCreative]] = None,
	  /** Optional. A numerical score of the impact from the recommendation's description. For example, a recommendation might suggest an upward trend in sales for a certain product. Higher number means larger impact. */
		numericalImpact: Option[Int] = None
	)
	
	object RecommendationDescription {
		enum TypeEnum extends Enum[TypeEnum] { case DESCRIPTION_TYPE_UNSPECIFIED, SHORT, LONG }
	}
	case class RecommendationDescription(
	  /** Output only. Text of the description. */
		text: Option[String] = None,
	  /** Output only. Type of the description. */
		`type`: Option[Schema.RecommendationDescription.TypeEnum] = None
	)
	
	case class RecommendationCallToAction(
	  /** Output only. Localized text of the CTA. Optional. */
		localizedText: Option[String] = None,
	  /** Output only. Intent of the action. This value describes the intent (for example, `OPEN_CREATE_EMAIL_CAMPAIGN_FLOW`) and can vary from recommendation to recommendation. This value can change over time for the same recommendation. Currently available intent values: - OPEN_CREATE_EMAIL_CAMPAIGN_FLOW: Opens a user journey where they can create a marketing email campaign. (No default URL) - OPEN_CREATE_COLLECTION_TAB: Opens a user journey where they can [create a collection](https://support.google.com/merchants/answer/9703228) for their Merchant account. (No default URL) */
		intent: Option[String] = None,
	  /** Optional. URL of the CTA. This field will only be set for some recommendations where there is a suggested landing URL. Otherwise it will be set to an empty string. We recommend developers to use their own custom landing page according to the description of the intent field above when this uri field is empty. */
		uri: Option[String] = None
	)
	
	object RecommendationCreative {
		enum TypeEnum extends Enum[TypeEnum] { case CREATIVE_TYPE_UNSPECIFIED, VIDEO, PHOTO }
	}
	case class RecommendationCreative(
	  /** URL of the creative. */
		uri: Option[String] = None,
	  /** Type of the creative. */
		`type`: Option[Schema.RecommendationCreative.TypeEnum] = None
	)
	
	object ReportInteractionRequest {
		enum InteractionTypeEnum extends Enum[InteractionTypeEnum] { case INTERACTION_TYPE_UNSPECIFIED, INTERACTION_DISMISS, INTERACTION_CLICK }
	}
	case class ReportInteractionRequest(
	  /** Required. Type of the interaction that is reported, for example INTERACTION_CLICK. */
		interactionType: Option[Schema.ReportInteractionRequest.InteractionTypeEnum] = None,
	  /** Required. Token of the response when recommendation was returned. */
		responseToken: Option[String] = None,
	  /** Required. Type of the recommendations on which this interaction happened. This field must be set only to the value that is returned by {@link `GenerateRecommendationsResponse`} call. */
		`type`: Option[String] = None,
	  /** Optional. Subtype of the recommendations this interaction happened on. This field must be set only to the value that is returned by {@link `RecommendationsService.GenerateRecommendations`} call. */
		subtype: Option[String] = None
	)
	
	object ReturnPolicyOnline {
		enum ReturnMethodsEnum extends Enum[ReturnMethodsEnum] { case RETURN_METHOD_UNSPECIFIED, BY_MAIL, IN_STORE, AT_A_KIOSK }
		enum ItemConditionsEnum extends Enum[ItemConditionsEnum] { case ITEM_CONDITION_UNSPECIFIED, NEW, USED }
	}
	case class ReturnPolicyOnline(
	  /** Output only. Return policy ID generated by Google. */
		returnPolicyId: Option[String] = None,
	  /** The unique user-defined label of the return policy. The same label cannot be used in different return policies for the same country. Policies with the label 'default' will apply to all products, unless a product specifies a return_policy_label attribute. */
		label: Option[String] = None,
	  /** The countries of sale where the return policy is applicable. The values must be a valid 2 letter ISO 3166 code, e.g. "US". */
		countries: Option[List[String]] = None,
	  /** The name of the policy as shown in Merchant Center. */
		name: Option[String] = None,
	  /** The return policy. */
		policy: Option[Schema.ReturnPolicyOnlinePolicy] = None,
	  /** The restocking fee that applies to all return reason categories. This would be treated as a free restocking fee if the value is not set. */
		restockingFee: Option[Schema.ReturnPolicyOnlineRestockingFee] = None,
	  /** The return methods of how customers can return an item. This value is required to not be empty unless the type of return policy is noReturns. */
		returnMethods: Option[List[Schema.ReturnPolicyOnline.ReturnMethodsEnum]] = None,
	  /** The item conditions that are accepted for returns. This is required to not be empty unless the type of return policy is noReturns. */
		itemConditions: Option[List[Schema.ReturnPolicyOnline.ItemConditionsEnum]] = None,
	  /** The return reason category information. This required to not be empty unless the type of return policy is noReturns. */
		returnReasonCategoryInfo: Option[List[Schema.ReturnPolicyOnlineReturnReasonCategoryInfo]] = None,
	  /** The return policy uri. This can used by Google to do a sanity check for the policy. */
		returnPolicyUri: Option[String] = None
	)
	
	object ReturnPolicyOnlinePolicy {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, NUMBER_OF_DAYS_AFTER_DELIVERY, NO_RETURNS, LIFETIME_RETURNS }
	}
	case class ReturnPolicyOnlinePolicy(
	  /** Policy type. */
		`type`: Option[Schema.ReturnPolicyOnlinePolicy.TypeEnum] = None,
	  /** The number of days items can be returned after delivery, where one day is defined to be 24 hours after the delivery timestamp. Required for `numberOfDaysAfterDelivery` returns. */
		days: Option[String] = None
	)
	
	case class ReturnPolicyOnlineRestockingFee(
	  /** Fixed restocking fee. */
		fixedFee: Option[Schema.PriceAmount] = None,
	  /** Percent of total price in micros. 15,000,000 means 15% of the total price would be charged. */
		microPercent: Option[Int] = None
	)
	
	object ReturnPolicyOnlineReturnReasonCategoryInfo {
		enum ReturnReasonCategoryEnum extends Enum[ReturnReasonCategoryEnum] { case RETURN_REASON_CATEGORY_UNSPECIFIED, BUYER_REMORSE, ITEM_DEFECT }
		enum ReturnLabelSourceEnum extends Enum[ReturnLabelSourceEnum] { case RETURN_LABEL_SOURCE_UNSPECIFIED, DOWNLOAD_AND_PRINT, IN_THE_BOX, CUSTOMER_RESPONSIBILITY }
	}
	case class ReturnPolicyOnlineReturnReasonCategoryInfo(
	  /** The return reason category. */
		returnReasonCategory: Option[Schema.ReturnPolicyOnlineReturnReasonCategoryInfo.ReturnReasonCategoryEnum] = None,
	  /** The corresponding return label source. */
		returnLabelSource: Option[Schema.ReturnPolicyOnlineReturnReasonCategoryInfo.ReturnLabelSourceEnum] = None,
	  /** The corresponding return shipping fee. This is only applicable when returnLabelSource is not the customer's responsibility. */
		returnShippingFee: Option[Schema.ReturnPolicyOnlineReturnShippingFee] = None
	)
	
	object ReturnPolicyOnlineReturnShippingFee {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, FIXED, CUSTOMER_PAYING_ACTUAL_FEE }
	}
	case class ReturnPolicyOnlineReturnShippingFee(
	  /** Type of return shipping fee. */
		`type`: Option[Schema.ReturnPolicyOnlineReturnShippingFee.TypeEnum] = None,
	  /** Fixed return shipping fee amount. This value is only applicable when type is FIXED. We will treat the return shipping fee as free if type is FIXED and this value is not set. */
		fixedFee: Option[Schema.PriceAmount] = None
	)
	
	case class ListReturnPolicyOnlineResponse(
	  /** The retrieved return policies. */
		returnPolicies: Option[List[Schema.ReturnPolicyOnline]] = None
	)
	
	case class OrderTrackingSignal(
	  /** Output only. The ID that uniquely identifies this order tracking signal. */
		orderTrackingSignalId: Option[String] = None,
	  /** The Google merchant ID of this order tracking signal. This value is optional. If left unset, the caller's merchant ID is used. You must request access in order to provide data on behalf of another merchant. For more information, see [Submitting Order Tracking Signals](/shopping-content/guides/order-tracking-signals). */
		merchantId: Option[String] = None,
	  /** Required. The time when the order was created on the merchant side. Include the year and timezone string, if available. */
		orderCreatedTime: Option[Schema.DateTime] = None,
	  /** Required. The ID of the order on the merchant side. This field will be hashed in returned OrderTrackingSignal creation response. */
		orderId: Option[String] = None,
	  /** The shipping information for the order. */
		shippingInfo: Option[List[Schema.OrderTrackingSignalShippingInfo]] = None,
	  /** Information about line items in the order. */
		lineItems: Option[List[Schema.OrderTrackingSignalLineItemDetails]] = None,
	  /** The mapping of the line items to the shipment information. */
		shipmentLineItemMapping: Option[List[Schema.OrderTrackingSignalShipmentLineItemMapping]] = None,
	  /** The shipping fee of the order; this value should be set to zero in the case of free shipping. */
		customerShippingFee: Option[Schema.PriceAmount] = None,
	  /** Required. The delivery postal code, as a continuous string without spaces or dashes, e.g. "95016". This field will be anonymized in returned OrderTrackingSignal creation response. */
		deliveryPostalCode: Option[String] = None,
	  /** Required. The [CLDR territory code] (http://www.unicode.org/repos/cldr/tags/latest/common/main/en.xml) for the shipping destination. */
		deliveryRegionCode: Option[String] = None
	)
	
	case class DateTime(
	  /** Optional. Year of date. Must be from 1 to 9999, or 0 if specifying a datetime without a year. */
		year: Option[Int] = None,
	  /** Optional. Month of year. Must be from 1 to 12, or 0 if specifying a datetime without a month. */
		month: Option[Int] = None,
	  /** Optional. Day of month. Must be from 1 to 31 and valid for the year and month, or 0 if specifying a datetime without a day. */
		day: Option[Int] = None,
	  /** Optional. Hours of day in 24 hour format. Should be from 0 to 23, defaults to 0 (midnight). An API may choose to allow the value "24:00:00" for scenarios like business closing time. */
		hours: Option[Int] = None,
	  /** Optional. Minutes of hour of day. Must be from 0 to 59, defaults to 0. */
		minutes: Option[Int] = None,
	  /** Optional. Seconds of minutes of the time. Must normally be from 0 to 59, defaults to 0. An API may allow the value 60 if it allows leap-seconds. */
		seconds: Option[Int] = None,
	  /** Optional. Fractions of seconds in nanoseconds. Must be from 0 to 999,999,999, defaults to 0. */
		nanos: Option[Int] = None,
	  /** UTC offset. Must be whole seconds, between -18 hours and +18 hours. For example, a UTC offset of -4:00 would be represented as { seconds: -14400 }. */
		utcOffset: Option[String] = None,
	  /** Time zone. */
		timeZone: Option[Schema.TimeZone] = None
	)
	
	case class TimeZone(
	  /** IANA Time Zone Database time zone, e.g. "America/New_York". */
		id: Option[String] = None,
	  /** Optional. IANA Time Zone Database version number, e.g. "2019a". */
		version: Option[String] = None
	)
	
	object OrderTrackingSignalShippingInfo {
		enum ShippingStatusEnum extends Enum[ShippingStatusEnum] { case SHIPPING_STATE_UNSPECIFIED, SHIPPED, DELIVERED }
	}
	case class OrderTrackingSignalShippingInfo(
	  /** Required. The shipment ID. This field will be hashed in returned OrderTrackingSignal creation response. */
		shipmentId: Option[String] = None,
	  /** The tracking ID of the shipment. This field is required if one of the following fields is absent: earliest_delivery_promise_time, latest_delivery_promise_time, and actual_delivery_time. */
		trackingId: Option[String] = None,
	  /** The name of the shipping carrier for the delivery. This field is required if one of the following fields is absent: earliest_delivery_promise_time, latest_delivery_promise_time, and actual_delivery_time. */
		carrierName: Option[String] = None,
	  /** The service type for fulfillment, e.g., GROUND, FIRST_CLASS, etc. */
		carrierServiceName: Option[String] = None,
	  /** The time when the shipment was shipped. Include the year and timezone string, if available. */
		shippedTime: Option[Schema.DateTime] = None,
	  /** The earliest delivery promised time. Include the year and timezone string, if available. This field is required, if one of the following fields is absent: tracking_id or carrier_name. */
		earliestDeliveryPromiseTime: Option[Schema.DateTime] = None,
	  /** The latest delivery promised time. Include the year and timezone string, if available. This field is required, if one of the following fields is absent: tracking_id or carrier_name. */
		latestDeliveryPromiseTime: Option[Schema.DateTime] = None,
	  /** The time when the shipment was actually delivered. Include the year and timezone string, if available. This field is required, if one of the following fields is absent: tracking_id or carrier_name. */
		actualDeliveryTime: Option[Schema.DateTime] = None,
	  /** The status of the shipment. */
		shippingStatus: Option[Schema.OrderTrackingSignalShippingInfo.ShippingStatusEnum] = None,
	  /** The origin postal code, as a continuous string without spaces or dashes, e.g. "95016". This field will be anonymized in returned OrderTrackingSignal creation response. */
		originPostalCode: Option[String] = None,
	  /** The [CLDR territory code] (http://www.unicode.org/repos/cldr/tags/latest/common/main/en.xml) for the shipping origin. */
		originRegionCode: Option[String] = None
	)
	
	case class OrderTrackingSignalLineItemDetails(
	  /** Required. The ID for this line item. */
		lineItemId: Option[String] = None,
	  /** Required. The Content API REST ID of the product, in the form channel:contentLanguage:targetCountry:offerId. */
		productId: Option[String] = None,
	  /** The Global Trade Item Number. */
		gtin: Option[String] = None,
	  /** The manufacturer part number. */
		mpn: Option[String] = None,
	  /** Universal product code for this item (deprecated: Please use GTIN instead). */
		upc: Option[String] = None,
	  /** Merchant SKU for this item (deprecated). */
		sku: Option[String] = None,
	  /** Plain text description of this product (deprecated: Please use product_title instead). */
		productDescription: Option[String] = None,
	  /** Plain text title of this product. */
		productTitle: Option[String] = None,
	  /** Brand of the product. */
		brand: Option[String] = None,
	  /** The quantity of the line item in the order. */
		quantity: Option[String] = None
	)
	
	case class OrderTrackingSignalShipmentLineItemMapping(
	  /** Required. The shipment ID. This field will be hashed in returned OrderTrackingSignal creation response. */
		shipmentId: Option[String] = None,
	  /** Required. The line item ID. */
		lineItemId: Option[String] = None,
	  /** The line item quantity in the shipment. */
		quantity: Option[String] = None
	)
	
	case class ProductDeliveryTime(
	  /** Required. The `id` of the product. */
		productId: Option[Schema.ProductId] = None,
	  /** Required. A set of associations between `DeliveryArea` and `DeliveryTime` entries. The total number of `areaDeliveryTimes` can be at most 100. */
		areaDeliveryTimes: Option[List[Schema.ProductDeliveryTimeAreaDeliveryTime]] = None
	)
	
	case class ProductId(
	  /** The Content API ID of the product, in the form `channel:contentLanguage:targetCountry:offerId`. */
		productId: Option[String] = None
	)
	
	case class ProductDeliveryTimeAreaDeliveryTime(
	  /** Required. The delivery area associated with `deliveryTime` for this product. */
		deliveryArea: Option[Schema.DeliveryArea] = None,
	  /** Required. The delivery time associated with `deliveryArea` for this product. */
		deliveryTime: Option[Schema.ProductDeliveryTimeAreaDeliveryTimeDeliveryTime] = None
	)
	
	case class DeliveryArea(
	  /** Required. The country that the product can be delivered to. Submit a [unicode CLDR region](http://www.unicode.org/repos/cldr/tags/latest/common/main/en.xml) such as `US` or `CH`. */
		countryCode: Option[String] = None,
	  /** A postal code, postal code range or postal code prefix that defines this area. Limited to US and AUS. */
		postalCodeRange: Option[Schema.DeliveryAreaPostalCodeRange] = None,
	  /** A state, territory, or prefecture. This is supported for the United States, Australia, and Japan. Provide a subdivision code from the ISO 3166-2 code tables ([US](https://en.wikipedia.org/wiki/ISO_3166-2:US), [AU](https://en.wikipedia.org/wiki/ISO_3166-2:AU), or [JP](https://en.wikipedia.org/wiki/ISO_3166-2:JP)) without country prefix (for example, `"NY"`, `"NSW"`, `"03"`). */
		regionCode: Option[String] = None
	)
	
	case class DeliveryAreaPostalCodeRange(
	  /** Required. A postal code or a pattern of the form prefix&#42; denoting the inclusive lower bound of the range defining the area. Examples values: `"94108"`, `"9410&#42;"`, `"9&#42;"`. */
		firstPostalCode: Option[String] = None,
	  /** A postal code or a pattern of the form prefix&#42; denoting the inclusive upper bound of the range defining the area (for example [070&#42; - 078&#42;] results in the range [07000 - 07899]). It must have the same length as `firstPostalCode`: if `firstPostalCode` is a postal code then `lastPostalCode` must be a postal code too; if firstPostalCode is a pattern then `lastPostalCode` must be a pattern with the same prefix length. Ignored if not set, then the area is defined as being all the postal codes matching `firstPostalCode`. */
		lastPostalCode: Option[String] = None
	)
	
	case class ProductDeliveryTimeAreaDeliveryTimeDeliveryTime(
	  /** Required. The minimum number of business days (inclusive) between when an order is placed and when the product ships. If a product ships in the same day, set this value to 0. */
		minHandlingTimeDays: Option[Int] = None,
	  /** Required. The maximum number of business days (inclusive) between when an order is placed and when the product ships. If a product ships in the same day, set this value to 0. */
		maxHandlingTimeDays: Option[Int] = None,
	  /** Required. The minimum number of business days (inclusive) between when the product ships and when the product is delivered. */
		minTransitTimeDays: Option[Int] = None,
	  /** Required. The maximum number of business days (inclusive) between when the product ships and when the product is delivered. */
		maxTransitTimeDays: Option[Int] = None
	)
}
