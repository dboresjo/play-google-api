package com.boresjo.play.api.google.paymentsresellersubscription

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleCloudPaymentsResellerSubscriptionV1Location(
	  /** 2-letter ISO region code for current content region. Ex. “US” Please refers to: https://en.wikipedia.org/wiki/ISO_3166-1 */
		regionCode: Option[String] = None,
	  /** The postal code this location refers to. Ex. "94043" */
		postalCode: Option[String] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1UserSession(
	  /** Output only. The time at which the user session expires. */
		expireTime: Option[String] = None,
	  /** Output only. The encrypted token of the user session, including the information of the user's intent and request. This token should be provided when redirecting the user to Google. */
		token: Option[String] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1FindEligiblePromotionsRequest(
	  /** Optional. Specifies the filters for the promotion results. The syntax is defined in https://google.aip.dev/160 with the following caveats: 1. Only the following features are supported: - Logical operator `AND` - Comparison operator `=` (no wildcards `&#42;`) - Traversal operator `.` - Has operator `:` (no wildcards `&#42;`) 2. Only the following fields are supported: - `applicableProducts` - `regionCodes` - `youtubePayload.partnerEligibilityId` - `youtubePayload.postalCode` 3. Unless explicitly mentioned above, other features are not supported. Example: `applicableProducts:partners/partner1/products/product1 AND regionCodes:US AND youtubePayload.postalCode=94043 AND youtubePayload.partnerEligibilityId=eligibility-id` */
		filter: Option[String] = None,
	  /** Optional. A page token, received from a previous `ListPromotions` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListPromotions` must match the call that provided the page token. */
		pageToken: Option[String] = None,
	  /** Optional. The maximum number of promotions to return. The service may return fewer than this value. If unspecified, at most 50 products will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000. */
		pageSize: Option[Int] = None
	)
	
	object GoogleCloudPaymentsResellerSubscriptionV1CancelSubscriptionRequest {
		enum CancellationReasonEnum extends Enum[CancellationReasonEnum] { case CANCELLATION_REASON_UNSPECIFIED, CANCELLATION_REASON_FRAUD, CANCELLATION_REASON_REMORSE, CANCELLATION_REASON_ACCIDENTAL_PURCHASE, CANCELLATION_REASON_PAST_DUE, CANCELLATION_REASON_ACCOUNT_CLOSED, CANCELLATION_REASON_UPGRADE_DOWNGRADE, CANCELLATION_REASON_USER_DELINQUENCY, CANCELLATION_REASON_SYSTEM_ERROR, CANCELLATION_REASON_SYSTEM_CANCEL, CANCELLATION_REASON_OTHER }
	}
	case class GoogleCloudPaymentsResellerSubscriptionV1CancelSubscriptionRequest(
	  /** Optional. If true, Google will cancel the subscription immediately, and may or may not (based on the contract) issue a prorated refund for the remainder of the billing cycle. Otherwise, Google defers the cancelation at renewal_time, and will not issue a refund. - YouTube subscriptions must use this option currently. However, the user will still have access to the subscription until the end of the billing cycle. */
		cancelImmediately: Option[Boolean] = None,
	  /** Specifies the reason for the cancellation. */
		cancellationReason: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1CancelSubscriptionRequest.CancellationReasonEnum] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1ProductBundleDetailsBundleElement(
	  /** Required. Output only. Product resource name that identifies the bundle element. The format is 'partners/{partner_id}/products/{product_id}'. */
		product: Option[String] = None
	)
	
	object GoogleCloudPaymentsResellerSubscriptionV1SubscriptionUpgradeDowngradeDetails {
		enum BillingCycleSpecEnum extends Enum[BillingCycleSpecEnum] { case BILLING_CYCLE_SPEC_UNSPECIFIED, BILLING_CYCLE_SPEC_ALIGN_WITH_PREVIOUS_SUBSCRIPTION, BILLING_CYCLE_SPEC_START_IMMEDIATELY }
	}
	case class GoogleCloudPaymentsResellerSubscriptionV1SubscriptionUpgradeDowngradeDetails(
	  /** Required. The previous subscription id to be replaced. This is not the full resource name, use the subscription_id segment only. */
		previousSubscriptionId: Option[String] = None,
	  /** Required. Specifies the billing cycle spec for the new upgraded/downgraded subscription. */
		billingCycleSpec: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1SubscriptionUpgradeDowngradeDetails.BillingCycleSpecEnum] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1CreateSubscriptionIntent(
	  /** Required. The Subscription to be created. */
		subscription: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1Subscription] = None,
	  /** Required. Identifies the subscription resource on the Partner side. The value is restricted to 63 ASCII characters at the maximum. If a subscription was previously created with the same subscription_id, we will directly return that one. */
		subscriptionId: Option[String] = None,
	  /** Required. The parent resource name, which is the identifier of the partner. */
		parent: Option[String] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1ExtendSubscriptionResponse(
	  /** The time at which the subscription is expected to be extended, in ISO 8061 format. UTC timezone. Example, "cycleEndTime":"2019-08-31T17:28:54.564Z" */
		cycleEndTime: Option[String] = None,
	  /** End of the free trial period, in ISO 8061 format. UTC timezone. Example, "freeTrialEndTime":"2019-08-31T17:28:54.564Z" This time will be set the same as initial subscription creation time if no free trial period is offered to the partner. */
		freeTrialEndTime: Option[String] = None,
	  /** Output only. The time at which the subscription is expected to be renewed by Google - a new charge will be incurred and the service entitlement will be renewed. A non-immediate cancellation will take place at this time too, before which, the service entitlement for the end user will remain valid. UTC timezone in ISO 8061 format. For example: "2019-08-31T17:28:54.564Z" */
		renewalTime: Option[String] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1FiniteBillingCycleDetails(
	  /** Required. The number of a subscription line item billing cycles after which billing will stop automatically. */
		billingCycleCountLimit: Option[String] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1SubscriptionLineItemOneTimeRecurrenceDetails(
	  /** Output only. The service period of the ONE_TIME line item. */
		servicePeriod: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1ServicePeriod] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1UndoCancelSubscriptionRequest(
	
	)
	
	object GoogleCloudPaymentsResellerSubscriptionV1Subscription {
		enum ProcessingStateEnum extends Enum[ProcessingStateEnum] { case PROCESSING_STATE_UNSPECIFIED, PROCESSING_STATE_CANCELLING, PROCESSING_STATE_RECURRING }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STATE_CREATED, STATE_ACTIVE, STATE_CANCELLED, STATE_IN_GRACE_PERIOD, STATE_CANCEL_AT_END_OF_CYCLE, STATE_SUSPENDED }
	}
	case class GoogleCloudPaymentsResellerSubscriptionV1Subscription(
	  /** Output only. Describes the processing state of the subscription. See more details at [the lifecycle of a subscription](/payments/reseller/subscription/reference/index/Receive.Notifications#payments-subscription-lifecycle). */
		processingState: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1Subscription.ProcessingStateEnum] = None,
	  /** Output only. Describes the state of the subscription. See more details at [the lifecycle of a subscription](/payments/reseller/subscription/reference/index/Receive.Notifications#payments-subscription-lifecycle). */
		state: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1Subscription.StateEnum] = None,
	  /** Output only. Indicates if the subscription is entitled to the end user. */
		endUserEntitled: Option[Boolean] = None,
	  /** Output only. The time at which the subscription is expected to be renewed by Google - a new charge will be incurred and the service entitlement will be renewed. A non-immediate cancellation will take place at this time too, before which, the service entitlement for the end user will remain valid. UTC timezone in ISO 8061 format. For example: "2019-08-31T17:28:54.564Z" */
		renewalTime: Option[String] = None,
	  /** Required. The line items of the subscription. */
		lineItems: Option[List[Schema.GoogleCloudPaymentsResellerSubscriptionV1SubscriptionLineItem]] = None,
	  /** Identifier. Resource name of the subscription. It will have the format of "partners/{partner_id}/subscriptions/{subscription_id}". This is available for authorizeAddon, but otherwise is response only. */
		name: Option[String] = None,
	  /** Output only. System generated timestamp when the subscription is most recently updated. UTC timezone. */
		updateTime: Option[String] = None,
	  /** Output only. End of the free trial period, in ISO 8061 format. For example, "2019-08-31T17:28:54.564Z". It will be set the same as createTime if no free trial promotion is specified. */
		freeTrialEndTime: Option[String] = None,
	  /** Output only. Describes the details of a cancelled subscription. Only applicable to subscription of state `STATE_CANCELLED`. */
		cancellationDetails: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1SubscriptionCancellationDetails] = None,
	  /** Required. The location that the service is provided as indicated by the partner. */
		serviceLocation: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1Location] = None,
	  /** Required. Identifier of the end-user in partner’s system. The value is restricted to 63 ASCII characters at the maximum. */
		partnerUserToken: Option[String] = None,
	  /** Optional. Subscription-level promotions. Only free trial is supported on this level. It determines the first renewal time of the subscription to be the end of the free trial period. Specify the promotion resource name only when used as input. */
		promotionSpecs: Option[List[Schema.GoogleCloudPaymentsResellerSubscriptionV1SubscriptionPromotionSpec]] = None,
	  /** Optional. The timestamp when the user transaction was made with the Partner. Specify for the case of "bundle with choice", and it must be before the provision_time (when the user makes a selection). */
		purchaseTime: Option[String] = None,
	  /** Optional. Details about the previous subscription that this new subscription upgrades/downgrades from. Only populated if this subscription is an upgrade/downgrade from another subscription. */
		upgradeDowngradeDetails: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1SubscriptionUpgradeDowngradeDetails] = None,
	  /** Output only. System generated timestamp when the subscription is created. UTC timezone. */
		createTime: Option[String] = None,
	  /** Output only. The time at which the subscription is expected to be extended, in ISO 8061 format. UTC timezone. For example: "2019-08-31T17:28:54.564Z" */
		cycleEndTime: Option[String] = None,
	  /** Optional. Deprecated: consider using the top-level `promotion_specs` as the input. Optional. Resource name that identifies one or more promotions that can be applied on the product. A typical promotion for a subscription is Free trial. The format will be 'partners/{partner_id}/promotions/{promotion_id}'. */
		promotions: Option[List[String]] = None,
	  /** Output only. The place where partners should redirect the end-user to after creation. This field might also be populated when creation failed. However, Partners should always prepare a default URL to redirect the user in case this field is empty. */
		redirectUri: Option[String] = None,
	  /** Optional. Deprecated: consider using `line_items` as the input. Required. Resource name that identifies the purchased products. The format will be 'partners/{partner_id}/products/{product_id}'. */
		products: Option[List[String]] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1EntitleSubscriptionRequestLineItemEntitlementDetails(
	  /** Optional. Only applicable if the line item corresponds to a hard bundle. Product resource names that identify the bundle elements to be entitled in the line item. If unspecified, all bundle elements will be entitled. The format is 'partners/{partner_id}/products/{product_id}'. */
		products: Option[List[String]] = None,
	  /** Required. The index of the line item to be entitled. */
		lineItemIndex: Option[Int] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1UndoCancelSubscriptionResponse(
	  /** The updated subscription resource. */
		subscription: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1Subscription] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1ProductPayload(
	  /** Payload specific to Youtube products. */
		youtubePayload: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1YoutubePayload] = None,
	  /** Product-specific payloads. Payload specific to Google One products. */
		googleOnePayload: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1GoogleOnePayload] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1CancelSubscriptionResponse(
	  /** The cancelled subscription resource. */
		subscription: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1Subscription] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1EntitleSubscriptionResponse(
	  /** The subscription that has user linked to it. */
		subscription: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1Subscription] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1ExtendSubscriptionRequest(
	  /** Required. Specifies details of the extension. Currently, the duration of the extension must be exactly one billing cycle of the original subscription. */
		extension: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1Extension] = None,
	  /** Required. Restricted to 36 ASCII characters. A random UUID is recommended. The idempotency key for the request. The ID generation logic is controlled by the partner. request_id should be the same as on retries of the same request. A different request_id must be used for a extension of a different cycle. */
		requestId: Option[String] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1ProductPriceConfig(
	  /** Output only. 2-letter ISO region code where the product is available in. Ex. "US". */
		regionCode: Option[String] = None,
	  /** Output only. The price in the region. */
		amount: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1Amount] = None
	)
	
	case class SubscriptionLineItemBundleDetails(
	  /** Output only. The details for each element in the hard bundle. */
		bundleElementDetails: Option[List[Schema.GoogleCloudPaymentsResellerSubscriptionV1SubscriptionLineItemBundleDetailsBundleElementDetails]] = None
	)
	
	object GoogleCloudPaymentsResellerSubscriptionV1YoutubePayload {
		enum PartnerPlanTypeEnum extends Enum[PartnerPlanTypeEnum] { case PARTNER_PLAN_TYPE_UNSPECIFIED, PARTNER_PLAN_TYPE_STANDALONE, PARTNER_PLAN_TYPE_HARD_BUNDLE, PARTNER_PLAN_TYPE_SOFT_BUNDLE }
	}
	case class GoogleCloudPaymentsResellerSubscriptionV1YoutubePayload(
	  /** Output only. The access expiration time for this line item. */
		accessEndTime: Option[String] = None,
	  /** Optional. Specifies the plan type offered to the end user by the partner. */
		partnerPlanType: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1YoutubePayload.PartnerPlanTypeEnum] = None,
	  /** The list of eligibility_ids which are applicable for the line item. */
		partnerEligibilityIds: Option[List[String]] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1Extension(
	  /** Required. Specifies the period of access the subscription should grant. */
		duration: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1Duration] = None,
	  /** Required. Identifier of the end-user in partner’s system. */
		partnerUserToken: Option[String] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1EntitleSubscriptionRequest(
	  /** Optional. The line items to be entitled. If unspecified, all line items will be entitled. */
		lineItemEntitlementDetails: Option[List[Schema.GoogleCloudPaymentsResellerSubscriptionV1EntitleSubscriptionRequestLineItemEntitlementDetails]] = None
	)
	
	object GoogleCloudPaymentsResellerSubscriptionV1GoogleOnePayload {
		enum OfferingEnum extends Enum[OfferingEnum] { case OFFERING_UNSPECIFIED, OFFERING_VAS_BUNDLE, OFFERING_VAS_STANDALONE, OFFERING_HARD_BUNDLE, OFFERING_SOFT_BUNDLE }
		enum SalesChannelEnum extends Enum[SalesChannelEnum] { case CHANNEL_UNSPECIFIED, CHANNEL_RETAIL, CHANNEL_ONLINE_WEB, CHANNEL_ONLINE_ANDROID_APP, CHANNEL_ONLINE_IOS_APP }
	}
	case class GoogleCloudPaymentsResellerSubscriptionV1GoogleOnePayload(
	  /** The identifier for the partner store where the subscription was sold. */
		storeId: Option[String] = None,
	  /** The type of offering the subscription was sold by the partner. e.g. VAS. */
		offering: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1GoogleOnePayload.OfferingEnum] = None,
	  /** Campaign attributed to sales of this subscription. */
		campaigns: Option[List[String]] = None,
	  /** The type of sales channel through which the subscription was sold. */
		salesChannel: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1GoogleOnePayload.SalesChannelEnum] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1FindEligiblePromotionsResponse(
	  /** The promotions for the current user. */
		promotions: Option[List[Schema.GoogleCloudPaymentsResellerSubscriptionV1Promotion]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is empty, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudPaymentsResellerSubscriptionV1SubscriptionPromotionSpec {
		enum TypeEnum extends Enum[TypeEnum] { case PROMOTION_TYPE_UNSPECIFIED, PROMOTION_TYPE_FREE_TRIAL, PROMOTION_TYPE_INTRODUCTORY_PRICING }
	}
	case class GoogleCloudPaymentsResellerSubscriptionV1SubscriptionPromotionSpec(
	  /** Output only. The type of the promotion for the spec. */
		`type`: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1SubscriptionPromotionSpec.TypeEnum] = None,
	  /** Required. Promotion resource name that identifies a promotion. The format is 'partners/{partner_id}/promotions/{promotion_id}'. */
		promotion: Option[String] = None,
	  /** Output only. The duration of the free trial if the promotion is of type FREE_TRIAL. */
		freeTrialDuration: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1Duration] = None,
	  /** Output only. The details of the introductory pricing spec if the promotion is of type INTRODUCTORY_PRICING. */
		introductoryPricingDetails: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1PromotionIntroductoryPricingDetails] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1IntentPayload(
	  /** The request to entitle a subscription. */
		entitleIntent: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1EntitleSubscriptionIntent] = None,
	  /** The request to create a subscription. */
		createIntent: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1CreateSubscriptionIntent] = None
	)
	
	case class GoogleTypeLocalizedText(
	  /** The text's BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None,
	  /** Localized string in the language corresponding to language_code below. */
		text: Option[String] = None
	)
	
	object GoogleCloudPaymentsResellerSubscriptionV1Product {
		enum ProductTypeEnum extends Enum[ProductTypeEnum] { case PRODUCT_TYPE_UNSPECIFIED, PRODUCT_TYPE_SUBSCRIPTION, PRODUCT_TYPE_BUNDLE_SUBSCRIPTION }
	}
	case class GoogleCloudPaymentsResellerSubscriptionV1Product(
	  /** Output only. 2-letter ISO region code where the product is available in. Ex. "US" Please refers to: https://en.wikipedia.org/wiki/ISO_3166-1 */
		regionCodes: Option[List[String]] = None,
	  /** Output only. Price configs for the product in the available regions. */
		priceConfigs: Option[List[Schema.GoogleCloudPaymentsResellerSubscriptionV1ProductPriceConfig]] = None,
	  /** Identifier. Response only. Resource name of the product. It will have the format of "partners/{partner_id}/products/{product_id}" */
		name: Option[String] = None,
	  /** Output only. Specifies the length of the billing cycle of the subscription. */
		subscriptionBillingCycleDuration: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1Duration] = None,
	  /** Optional. Details for a subscription line item with finite billing cycles. If unset, the line item will be charged indefinitely. */
		finiteBillingCycleDetails: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1FiniteBillingCycleDetails] = None,
	  /** Output only. Localized human readable name of the product. */
		titles: Option[List[Schema.GoogleTypeLocalizedText]] = None,
	  /** Output only. Output Only. Specifies the details for a bundle product. */
		bundleDetails: Option[Schema.ProductBundleDetails] = None,
	  /** Output only. Output Only. Specifies the type of the product. */
		productType: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1Product.ProductTypeEnum] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1ListProductsResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is empty, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The products for the specified partner. */
		products: Option[List[Schema.GoogleCloudPaymentsResellerSubscriptionV1Product]] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1GenerateUserSessionRequest(
	  /** The user intent to generate the user session. */
		intentPayload: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1IntentPayload] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1PromotionIntroductoryPricingDetailsIntroductoryPricingSpec(
	  /** Output only. 2-letter ISO region code where the product is available in. Ex. "US". */
		regionCode: Option[String] = None,
	  /** Output only. Output Only. The duration of an introductory offer in billing cycles. */
		recurrenceCount: Option[Int] = None,
	  /** Output only. The discount amount. The value is positive. */
		discountAmount: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1Amount] = None,
	  /** Output only. The discount percentage in micros. For example, 50,000 represents 5%. */
		discountRatioMicros: Option[String] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1PromotionIntroductoryPricingDetails(
	  /** Output only. Specifies the introductory pricing periods. */
		introductoryPricingSpecs: Option[List[Schema.GoogleCloudPaymentsResellerSubscriptionV1PromotionIntroductoryPricingDetailsIntroductoryPricingSpec]] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1GenerateUserSessionResponse(
	  /** The generated user session. The token size is proportional to the size of the intent payload. */
		userSession: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1UserSession] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1Amount(
	  /** Required. Amount in micros (1_000_000 micros = 1 currency unit) */
		amountMicros: Option[String] = None,
	  /** Required. Currency codes in accordance with [ISO-4217 Currency Codes] (https://en.wikipedia.org/wiki/ISO_4217). For example, USD. */
		currencyCode: Option[String] = None
	)
	
	object ProductBundleDetails {
		enum EntitlementModeEnum extends Enum[EntitlementModeEnum] { case ENTITLEMENT_MODE_UNSPECIFIED, ENTITLEMENT_MODE_FULL, ENTITLEMENT_MODE_INCREMENTAL }
	}
	case class ProductBundleDetails(
	  /** The entitlement mode of the bundle product. */
		entitlementMode: Option[Schema.ProductBundleDetails.EntitlementModeEnum] = None,
	  /** The individual products that are included in the bundle. */
		bundleElements: Option[List[Schema.GoogleCloudPaymentsResellerSubscriptionV1ProductBundleDetailsBundleElement]] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1SubscriptionLineItemBundleDetailsBundleElementDetails(
	  /** Output only. Product resource name that identifies the bundle element. The format is 'partners/{partner_id}/products/{product_id}'. */
		product: Option[String] = None,
	  /** Output only. The time when this product is linked to an end user. */
		userAccountLinkedTime: Option[String] = None
	)
	
	object GoogleCloudPaymentsResellerSubscriptionV1SubscriptionCancellationDetails {
		enum ReasonEnum extends Enum[ReasonEnum] { case CANCELLATION_REASON_UNSPECIFIED, CANCELLATION_REASON_FRAUD, CANCELLATION_REASON_REMORSE, CANCELLATION_REASON_ACCIDENTAL_PURCHASE, CANCELLATION_REASON_PAST_DUE, CANCELLATION_REASON_ACCOUNT_CLOSED, CANCELLATION_REASON_UPGRADE_DOWNGRADE, CANCELLATION_REASON_USER_DELINQUENCY, CANCELLATION_REASON_SYSTEM_ERROR, CANCELLATION_REASON_SYSTEM_CANCEL, CANCELLATION_REASON_OTHER }
	}
	case class GoogleCloudPaymentsResellerSubscriptionV1SubscriptionCancellationDetails(
	  /** Output only. The reason of the cancellation. */
		reason: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1SubscriptionCancellationDetails.ReasonEnum] = None
	)
	
	object GoogleCloudPaymentsResellerSubscriptionV1Promotion {
		enum PromotionTypeEnum extends Enum[PromotionTypeEnum] { case PROMOTION_TYPE_UNSPECIFIED, PROMOTION_TYPE_FREE_TRIAL, PROMOTION_TYPE_INTRODUCTORY_PRICING }
	}
	case class GoogleCloudPaymentsResellerSubscriptionV1Promotion(
	  /** Output only. 2-letter ISO region code where the promotion is available in. Ex. "US" Please refers to: https://en.wikipedia.org/wiki/ISO_3166-1 */
		regionCodes: Option[List[String]] = None,
	  /** Identifier. Response only. Resource name of the subscription promotion. It will have the format of "partners/{partner_id}/promotion/{promotion_id}" */
		name: Option[String] = None,
	  /** Output only. The product ids this promotion can be applied to. */
		applicableProducts: Option[List[String]] = None,
	  /** Optional. Specifies the start time (inclusive) of the period that the promotion is available in. */
		startTime: Option[String] = None,
	  /** Output only. Localized human readable name of the promotion. */
		titles: Option[List[Schema.GoogleTypeLocalizedText]] = None,
	  /** Optional. Specifies the end time (exclusive) of the period that the promotion is available in. If unset, the promotion is available indefinitely. */
		endTime: Option[String] = None,
	  /** Optional. Specifies the duration of the free trial of the subscription when promotion_type is PROMOTION_TYPE_FREE_TRIAL */
		freeTrialDuration: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1Duration] = None,
	  /** Optional. Specifies the introductory pricing details when the promotion_type is PROMOTION_TYPE_INTRODUCTORY_PRICING. */
		introductoryPricingDetails: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1PromotionIntroductoryPricingDetails] = None,
	  /** Output only. Output Only. Specifies the type of the promotion. */
		promotionType: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1Promotion.PromotionTypeEnum] = None
	)
	
	object GoogleCloudPaymentsResellerSubscriptionV1SubscriptionLineItem {
		enum RecurrenceTypeEnum extends Enum[RecurrenceTypeEnum] { case LINE_ITEM_RECURRENCE_TYPE_UNSPECIFIED, LINE_ITEM_RECURRENCE_TYPE_PERIODIC, LINE_ITEM_RECURRENCE_TYPE_ONE_TIME }
		enum StateEnum extends Enum[StateEnum] { case LINE_ITEM_STATE_UNSPECIFIED, LINE_ITEM_STATE_ACTIVE, LINE_ITEM_STATE_INACTIVE, LINE_ITEM_STATE_NEW, LINE_ITEM_STATE_ACTIVATING, LINE_ITEM_STATE_DEACTIVATING, LINE_ITEM_STATE_WAITING_TO_DEACTIVATE, LINE_ITEM_STATE_OFF_CYCLE_CHARGING }
	}
	case class GoogleCloudPaymentsResellerSubscriptionV1SubscriptionLineItem(
	  /** Output only. The price of the product/service in this line item. The amount could be the wholesale price, or it can include a cost of sale based on the contract. */
		amount: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1Amount] = None,
	  /** Output only. The recurrence type of the line item. */
		recurrenceType: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1SubscriptionLineItem.RecurrenceTypeEnum] = None,
	  /** Optional. Details for a subscription line item with finite billing cycles. If unset, the line item will be charged indefinitely. Used only with LINE_ITEM_RECURRENCE_TYPE_PERIODIC. */
		finiteBillingCycleDetails: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1FiniteBillingCycleDetails] = None,
	  /** Optional. Product specific payload for this line item. */
		productPayload: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1ProductPayload] = None,
	  /** Required. Product resource name that identifies one the line item The format is 'partners/{partner_id}/products/{product_id}'. */
		product: Option[String] = None,
	  /** Output only. The state of the line item. */
		state: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1SubscriptionLineItem.StateEnum] = None,
	  /** Output only. The bundle details for the line item. Only populated if the line item corresponds to a hard bundle. */
		bundleDetails: Option[Schema.SubscriptionLineItemBundleDetails] = None,
	  /** Output only. Details only set for a ONE_TIME recurrence line item. */
		oneTimeRecurrenceDetails: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1SubscriptionLineItemOneTimeRecurrenceDetails] = None,
	  /** Output only. A unique index of the subscription line item. */
		lineItemIndex: Option[Int] = None,
	  /** Output only. The free trial end time will be populated after the line item is successfully processed. End time of the line item free trial period, in ISO 8061 format. For example, "2019-08-31T17:28:54.564Z". It will be set the same as createTime if no free trial promotion is specified. */
		lineItemFreeTrialEndTime: Option[String] = None,
	  /** Output only. Description of this line item. */
		description: Option[String] = None,
	  /** Optional. The promotions applied on the line item. It can be: - an introductory pricing promotion. - a free trial promotion. This feature is not enabled. If used, the request will be rejected. When used as input in Create or Provision API, specify its resource name only. */
		lineItemPromotionSpecs: Option[List[Schema.GoogleCloudPaymentsResellerSubscriptionV1SubscriptionPromotionSpec]] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1EntitleSubscriptionIntent(
	  /** Required. The name of the subscription resource that is entitled to the current end user. */
		name: Option[String] = None
	)
	
	object GoogleCloudPaymentsResellerSubscriptionV1Duration {
		enum UnitEnum extends Enum[UnitEnum] { case UNIT_UNSPECIFIED, MONTH, DAY, HOUR }
	}
	case class GoogleCloudPaymentsResellerSubscriptionV1Duration(
	  /** The unit used for the duration */
		unit: Option[Schema.GoogleCloudPaymentsResellerSubscriptionV1Duration.UnitEnum] = None,
	  /** number of duration units to be included. */
		count: Option[Int] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1ServicePeriod(
	  /** Required. The start time of the service period. Time is inclusive. */
		startTime: Option[String] = None,
	  /** Optional. The end time of the service period. Time is exclusive. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudPaymentsResellerSubscriptionV1ListPromotionsResponse(
	  /** The promotions for the specified partner. */
		promotions: Option[List[Schema.GoogleCloudPaymentsResellerSubscriptionV1Promotion]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is empty, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
}
