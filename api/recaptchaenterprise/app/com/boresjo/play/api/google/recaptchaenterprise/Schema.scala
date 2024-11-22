package com.boresjo.play.api.google.recaptchaenterprise

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleCloudRecaptchaenterpriseV1TransactionDataGatewayInfo(
	  /** Optional. Gateway response code describing the state of the transaction. */
		gatewayResponseCode: Option[String] = None,
	  /** Optional. AVS response code from the gateway (available only when reCAPTCHA Enterprise is called after authorization). */
		avsResponseCode: Option[String] = None,
	  /** Optional. Name of the gateway service (for example, stripe, square, paypal). */
		name: Option[String] = None,
	  /** Optional. CVV response code from the gateway (available only when reCAPTCHA Enterprise is called after authorization). */
		cvvResponseCode: Option[String] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1RetrieveLegacySecretKeyResponse(
	  /** The secret key (also known as shared secret) authorizes communication between your application backend and the reCAPTCHA Enterprise server to create an assessment. The secret key needs to be kept safe for security purposes. */
		legacySecretKey: Option[String] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1FirewallActionRedirectAction(
	
	)
	
	case class GoogleCloudRecaptchaenterpriseV1UserId(
	  /** Optional. A unique username, if different from all the other identifiers and `account_id` that are provided. Can be a unique login handle or display name for a user. */
		username: Option[String] = None,
	  /** Optional. An email address. */
		email: Option[String] = None,
	  /** Optional. A phone number. Should use the E.164 format. */
		phoneNumber: Option[String] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1Key(
	  /** Optional. See [Creating and managing labels] (https://cloud.google.com/recaptcha/docs/labels). */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Settings for WAF */
		wafSettings: Option[Schema.GoogleCloudRecaptchaenterpriseV1WafSettings] = None,
	  /** Required. Human-readable display name of this key. Modifiable by user. */
		displayName: Option[String] = None,
	  /** Optional. Options for user acceptance testing. */
		testingOptions: Option[Schema.GoogleCloudRecaptchaenterpriseV1TestingOptions] = None,
	  /** Identifier. The resource name for the Key in the format `projects/{project}/keys/{key}`. */
		name: Option[String] = None,
	  /** Output only. The timestamp corresponding to the creation of this key. */
		createTime: Option[String] = None,
	  /** Settings for keys that can be used by iOS apps. */
		iosSettings: Option[Schema.GoogleCloudRecaptchaenterpriseV1IOSKeySettings] = None,
	  /** Settings for keys that can be used by reCAPTCHA Express. */
		expressSettings: Option[Schema.GoogleCloudRecaptchaenterpriseV1ExpressKeySettings] = None,
	  /** Settings for keys that can be used by Android apps. */
		androidSettings: Option[Schema.GoogleCloudRecaptchaenterpriseV1AndroidKeySettings] = None,
	  /** Settings for keys that can be used by websites. */
		webSettings: Option[Schema.GoogleCloudRecaptchaenterpriseV1WebKeySettings] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1TransactionDataAddress(
	  /** Optional. The postal or ZIP code of the address. */
		postalCode: Option[String] = None,
	  /** Optional. The recipient name, potentially including information such as "care of". */
		recipient: Option[String] = None,
	  /** Optional. The first lines of the address. The first line generally contains the street name and number, and further lines may include information such as an apartment number. */
		address: Option[List[String]] = None,
	  /** Optional. The state, province, or otherwise administrative area of the address. */
		administrativeArea: Option[String] = None,
	  /** Optional. The CLDR country/region of the address. */
		regionCode: Option[String] = None,
	  /** Optional. The town/city of the address. */
		locality: Option[String] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1FraudPreventionAssessmentBehavioralTrustVerdict(
	  /** Output only. Probability of this transaction attempt being executed in a behaviorally trustworthy way. Values are from 0.0 (lowest) to 1.0 (highest). */
		trust: Option[BigDecimal] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1Metrics(
	  /** Output only. Identifier. The name of the metrics, in the format `projects/{project}/keys/{key}/metrics`. */
		name: Option[String] = None,
	  /** Metrics are continuous and in order by dates, and in the granularity of day. All Key types should have score-based data. */
		scoreMetrics: Option[List[Schema.GoogleCloudRecaptchaenterpriseV1ScoreMetrics]] = None,
	  /** Inclusive start time aligned to a day (UTC). */
		startTime: Option[String] = None,
	  /** Metrics are continuous and in order by dates, and in the granularity of day. Only challenge-based keys (CHECKBOX, INVISIBLE) have challenge-based data. */
		challengeMetrics: Option[List[Schema.GoogleCloudRecaptchaenterpriseV1ChallengeMetrics]] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1AnnotateAssessmentResponse(
	
	)
	
	case class GoogleCloudRecaptchaenterpriseV1ListFirewallPoliciesResponse(
	  /** Token to retrieve the next page of results. It is set to empty if no policies remain in results. */
		nextPageToken: Option[String] = None,
	  /** Policy details. */
		firewallPolicies: Option[List[Schema.GoogleCloudRecaptchaenterpriseV1FirewallPolicy]] = None
	)
	
	object GoogleCloudRecaptchaenterpriseV1RiskAnalysis {
		enum ChallengeEnum extends Enum[ChallengeEnum] { case CHALLENGE_UNSPECIFIED, NOCAPTCHA, PASSED, FAILED }
		enum ReasonsEnum extends Enum[ReasonsEnum] { case CLASSIFICATION_REASON_UNSPECIFIED, AUTOMATION, UNEXPECTED_ENVIRONMENT, TOO_MUCH_TRAFFIC, UNEXPECTED_USAGE_PATTERNS, LOW_CONFIDENCE_SCORE, SUSPECTED_CARDING, SUSPECTED_CHARGEBACK }
	}
	case class GoogleCloudRecaptchaenterpriseV1RiskAnalysis(
	  /** Output only. Extended verdict reasons to be used for experimentation only. The set of possible reasons is subject to change. */
		extendedVerdictReasons: Option[List[String]] = None,
	  /** Output only. Challenge information for SCORE_AND_CHALLENGE and INVISIBLE keys */
		challenge: Option[Schema.GoogleCloudRecaptchaenterpriseV1RiskAnalysis.ChallengeEnum] = None,
	  /** Output only. Reasons contributing to the risk analysis verdict. */
		reasons: Option[List[Schema.GoogleCloudRecaptchaenterpriseV1RiskAnalysis.ReasonsEnum]] = None,
	  /** Output only. Legitimate event score from 0.0 to 1.0. (1.0 means very likely legitimate traffic while 0.0 means very likely non-legitimate traffic). */
		score: Option[BigDecimal] = None
	)
	
	object GoogleCloudRecaptchaenterpriseV1TestingOptions {
		enum TestingChallengeEnum extends Enum[TestingChallengeEnum] { case TESTING_CHALLENGE_UNSPECIFIED, NOCAPTCHA, UNSOLVABLE_CHALLENGE }
	}
	case class GoogleCloudRecaptchaenterpriseV1TestingOptions(
	  /** Optional. For challenge-based keys only (CHECKBOX, INVISIBLE), all challenge requests for this site return nocaptcha if NOCAPTCHA, or an unsolvable challenge if CHALLENGE. */
		testingChallenge: Option[Schema.GoogleCloudRecaptchaenterpriseV1TestingOptions.TestingChallengeEnum] = None,
	  /** Optional. All assessments for this Key return this score. Must be between 0 (likely not legitimate) and 1 (likely legitimate) inclusive. */
		testingScore: Option[BigDecimal] = None
	)
	
	object GoogleCloudRecaptchaenterpriseV1TransactionEvent {
		enum EventTypeEnum extends Enum[EventTypeEnum] { case TRANSACTION_EVENT_TYPE_UNSPECIFIED, MERCHANT_APPROVE, MERCHANT_DENY, MANUAL_REVIEW, AUTHORIZATION, AUTHORIZATION_DECLINE, PAYMENT_CAPTURE, PAYMENT_CAPTURE_DECLINE, CANCEL, CHARGEBACK_INQUIRY, CHARGEBACK_ALERT, FRAUD_NOTIFICATION, CHARGEBACK, CHARGEBACK_REPRESENTMENT, CHARGEBACK_REVERSE, REFUND_REQUEST, REFUND_DECLINE, REFUND, REFUND_REVERSE }
	}
	case class GoogleCloudRecaptchaenterpriseV1TransactionEvent(
	  /** Optional. The value that corresponds with this transaction event, if one exists. For example, a refund event where $5.00 was refunded. Currency is obtained from the original transaction data. */
		value: Option[BigDecimal] = None,
	  /** Optional. The type of this transaction event. */
		eventType: Option[Schema.GoogleCloudRecaptchaenterpriseV1TransactionEvent.EventTypeEnum] = None,
	  /** Optional. Timestamp when this transaction event occurred; otherwise assumed to be the time of the API call. */
		eventTime: Option[String] = None,
	  /** Optional. The reason or standardized code that corresponds with this transaction event, if one exists. For example, a CHARGEBACK event with code 6005. */
		reason: Option[String] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1FirewallActionSubstituteAction(
	  /** Optional. The address to redirect to. The target is a relative path in the current host. Example: "/blog/404.html". */
		path: Option[String] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1AppleDeveloperId(
	  /** Required. The Apple developer key ID (10-character string). */
		keyId: Option[String] = None,
	  /** Required. The Apple team ID (10-character string) owning the provisioning profile used to build your application. */
		teamId: Option[String] = None,
	  /** Required. Input only. A private key (downloaded as a text file with a .p8 file extension) generated for your Apple Developer account. Ensure that Apple DeviceCheck is enabled for the private key. */
		privateKey: Option[String] = None
	)
	
	object GoogleCloudRecaptchaenterpriseV1IpOverrideData {
		enum OverrideTypeEnum extends Enum[OverrideTypeEnum] { case OVERRIDE_TYPE_UNSPECIFIED, ALLOW }
	}
	case class GoogleCloudRecaptchaenterpriseV1IpOverrideData(
	  /** Required. The IP address to override (can be IPv4, IPv6 or CIDR). The IP override must be a valid IPv4 or IPv6 address, or a CIDR range. The IP override must be a public IP address. Example of IPv4: 168.192.5.6 Example of IPv6: 2001:0000:130F:0000:0000:09C0:876A:130B Example of IPv4 with CIDR: 168.192.5.0/24 Example of IPv6 with CIDR: 2001:0DB8:1234::/48 */
		ip: Option[String] = None,
	  /** Required. Describes the type of IP override. */
		overrideType: Option[Schema.GoogleCloudRecaptchaenterpriseV1IpOverrideData.OverrideTypeEnum] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1FraudSignals(
	  /** Output only. Signals describing the end user in this transaction. */
		userSignals: Option[Schema.GoogleCloudRecaptchaenterpriseV1FraudSignalsUserSignals] = None,
	  /** Output only. Signals describing the payment card or cards used in this transaction. */
		cardSignals: Option[Schema.GoogleCloudRecaptchaenterpriseV1FraudSignalsCardSignals] = None
	)
	
	object GoogleCloudRecaptchaenterpriseV1AnnotateAssessmentRequest {
		enum ReasonsEnum extends Enum[ReasonsEnum] { case REASON_UNSPECIFIED, CHARGEBACK, CHARGEBACK_FRAUD, CHARGEBACK_DISPUTE, REFUND, REFUND_FRAUD, TRANSACTION_ACCEPTED, TRANSACTION_DECLINED, PAYMENT_HEURISTICS, INITIATED_TWO_FACTOR, PASSED_TWO_FACTOR, FAILED_TWO_FACTOR, CORRECT_PASSWORD, INCORRECT_PASSWORD, SOCIAL_SPAM }
		enum AnnotationEnum extends Enum[AnnotationEnum] { case ANNOTATION_UNSPECIFIED, LEGITIMATE, FRAUDULENT, PASSWORD_CORRECT, PASSWORD_INCORRECT }
	}
	case class GoogleCloudRecaptchaenterpriseV1AnnotateAssessmentRequest(
	  /** Optional. Reasons for the annotation that are assigned to the event. */
		reasons: Option[List[Schema.GoogleCloudRecaptchaenterpriseV1AnnotateAssessmentRequest.ReasonsEnum]] = None,
	  /** Optional. The annotation that is assigned to the Event. This field can be left empty to provide reasons that apply to an event without concluding whether the event is legitimate or fraudulent. */
		annotation: Option[Schema.GoogleCloudRecaptchaenterpriseV1AnnotateAssessmentRequest.AnnotationEnum] = None,
	  /** Optional. If the assessment is part of a payment transaction, provide details on payment lifecycle events that occur in the transaction. */
		transactionEvent: Option[Schema.GoogleCloudRecaptchaenterpriseV1TransactionEvent] = None,
	  /** Optional. A stable hashed account identifier to apply to the assessment. This is an alternative to setting `hashed_account_id` in `CreateAssessment`, for example when a stable account identifier is not yet known in the initial request. */
		hashedAccountId: Option[String] = None,
	  /** Optional. A stable account identifier to apply to the assessment. This is an alternative to setting `account_id` in `CreateAssessment`, for example when a stable account identifier is not yet known in the initial request. */
		accountId: Option[String] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1Assessment(
	  /** Output only. Fraud Signals specific to the users involved in a payment transaction. */
		fraudSignals: Option[Schema.GoogleCloudRecaptchaenterpriseV1FraudSignals] = None,
	  /** Output only. Assessment returned when a site key, a token, and a phone number as `user_id` are provided. Account defender and SMS toll fraud protection need to be enabled. */
		phoneFraudAssessment: Option[Schema.GoogleCloudRecaptchaenterpriseV1PhoneFraudAssessment] = None,
	  /** Output only. Assessment returned when firewall policies belonging to the project are evaluated using the field firewall_policy_evaluation. */
		firewallPolicyAssessment: Option[Schema.GoogleCloudRecaptchaenterpriseV1FirewallPolicyAssessment] = None,
	  /** Output only. Assessment returned by account defender when an account identifier is provided. */
		accountDefenderAssessment: Option[Schema.GoogleCloudRecaptchaenterpriseV1AccountDefenderAssessment] = None,
	  /** Output only. Identifier. The resource name for the Assessment in the format `projects/{project}/assessments/{assessment}`. */
		name: Option[String] = None,
	  /** Optional. The event being assessed. */
		event: Option[Schema.GoogleCloudRecaptchaenterpriseV1Event] = None,
	  /** Output only. The risk analysis result for the event being assessed. */
		riskAnalysis: Option[Schema.GoogleCloudRecaptchaenterpriseV1RiskAnalysis] = None,
	  /** Optional. Account verification information for identity verification. The assessment event must include a token and site key to use this feature. */
		accountVerification: Option[Schema.GoogleCloudRecaptchaenterpriseV1AccountVerificationInfo] = None,
	  /** Optional. The environment creating the assessment. This describes your environment (the system invoking CreateAssessment), NOT the environment of your user. */
		assessmentEnvironment: Option[Schema.GoogleCloudRecaptchaenterpriseV1AssessmentEnvironment] = None,
	  /** Output only. Properties of the provided event token. */
		tokenProperties: Option[Schema.GoogleCloudRecaptchaenterpriseV1TokenProperties] = None,
	  /** Optional. The private password leak verification field contains the parameters that are used to to check for leaks privately without sharing user credentials. */
		privatePasswordLeakVerification: Option[Schema.GoogleCloudRecaptchaenterpriseV1PrivatePasswordLeakVerification] = None,
	  /** Output only. Assessment returned by Fraud Prevention when TransactionData is provided. */
		fraudPreventionAssessment: Option[Schema.GoogleCloudRecaptchaenterpriseV1FraudPreventionAssessment] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1ScoreMetrics(
	  /** Aggregated score metrics for all traffic. */
		overallMetrics: Option[Schema.GoogleCloudRecaptchaenterpriseV1ScoreDistribution] = None,
	  /** Action-based metrics. The map key is the action name which specified by the site owners at time of the "execute" client-side call. */
		actionMetrics: Option[Map[String, Schema.GoogleCloudRecaptchaenterpriseV1ScoreDistribution]] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleCloudRecaptchaenterpriseV1UserInfo(
	  /** Optional. Identifiers associated with this user or request. */
		userIds: Option[List[Schema.GoogleCloudRecaptchaenterpriseV1UserId]] = None,
	  /** Optional. For logged-in requests or login/registration requests, the unique account identifier associated with this user. You can use the username if it is stable (meaning it is the same for every request associated with the same user), or any stable user ID of your choice. Leave blank for non logged-in actions or guest checkout. */
		accountId: Option[String] = None,
	  /** Optional. Creation time for this account associated with this user. Leave blank for non logged-in actions, guest checkout, or when there is no account associated with the current user. */
		createAccountTime: Option[String] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1PrivatePasswordLeakVerification(
	  /** Optional. Encrypted Scrypt hash of the canonicalized username+password. It is re-encrypted by the server and returned through `reencrypted_user_credentials_hash`. */
		encryptedUserCredentialsHash: Option[String] = None,
	  /** Output only. Corresponds to the re-encryption of the `encrypted_user_credentials_hash` field. It is used to match potential password leaks within `encrypted_leak_match_prefixes`. */
		reencryptedUserCredentialsHash: Option[String] = None,
	  /** Required. Exactly 26-bit prefix of the SHA-256 hash of the canonicalized username. It is used to look up password leaks associated with that hash prefix. */
		lookupHashPrefix: Option[String] = None,
	  /** Output only. List of prefixes of the encrypted potential password leaks that matched the given parameters. They must be compared with the client-side decryption prefix of `reencrypted_user_credentials_hash` */
		encryptedLeakMatchPrefixes: Option[List[String]] = None
	)
	
	object GoogleCloudRecaptchaenterpriseV1WafSettings {
		enum WafFeatureEnum extends Enum[WafFeatureEnum] { case WAF_FEATURE_UNSPECIFIED, CHALLENGE_PAGE, SESSION_TOKEN, ACTION_TOKEN, EXPRESS }
		enum WafServiceEnum extends Enum[WafServiceEnum] { case WAF_SERVICE_UNSPECIFIED, CA, FASTLY, CLOUDFLARE, AKAMAI }
	}
	case class GoogleCloudRecaptchaenterpriseV1WafSettings(
	  /** Required. The WAF feature for which this key is enabled. */
		wafFeature: Option[Schema.GoogleCloudRecaptchaenterpriseV1WafSettings.WafFeatureEnum] = None,
	  /** Required. The WAF service that uses this key. */
		wafService: Option[Schema.GoogleCloudRecaptchaenterpriseV1WafSettings.WafServiceEnum] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1IOSKeySettings(
	  /** Optional. If set to true, allowed_bundle_ids are not enforced. */
		allowAllBundleIds: Option[Boolean] = None,
	  /** Optional. iOS bundle ids of apps allowed to use the key. Example: 'com.companyname.productname.appname' */
		allowedBundleIds: Option[List[String]] = None,
	  /** Optional. Apple Developer account details for the app that is protected by the reCAPTCHA Key. reCAPTCHA leverages platform-specific checks like Apple App Attest and Apple DeviceCheck to protect your app from abuse. Providing these fields allows reCAPTCHA to get a better assessment of the integrity of your app. */
		appleDeveloperId: Option[Schema.GoogleCloudRecaptchaenterpriseV1AppleDeveloperId] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1FirewallActionIncludeRecaptchaScriptAction(
	
	)
	
	object GoogleCloudRecaptchaenterpriseV1FraudSignalsCardSignals {
		enum CardLabelsEnum extends Enum[CardLabelsEnum] { case CARD_LABEL_UNSPECIFIED, PREPAID, VIRTUAL, UNEXPECTED_LOCATION }
	}
	case class GoogleCloudRecaptchaenterpriseV1FraudSignalsCardSignals(
	  /** Output only. The labels for the payment card in this transaction. */
		cardLabels: Option[List[Schema.GoogleCloudRecaptchaenterpriseV1FraudSignalsCardSignals.CardLabelsEnum]] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1RemoveIpOverrideRequest(
	  /** Required. IP override to be removed from the key. */
		ipOverrideData: Option[Schema.GoogleCloudRecaptchaenterpriseV1IpOverrideData] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1RelatedAccountGroup(
	  /** Required. Identifier. The resource name for the related account group in the format `projects/{project}/relatedaccountgroups/{related_account_group}`. */
		name: Option[String] = None
	)
	
	object GoogleCloudRecaptchaenterpriseV1Event {
		enum FraudPreventionEnum extends Enum[FraudPreventionEnum] { case FRAUD_PREVENTION_UNSPECIFIED, ENABLED, DISABLED }
	}
	case class GoogleCloudRecaptchaenterpriseV1Event(
	  /** Optional. Flag for a reCAPTCHA express request for an assessment without a token. If enabled, `site_key` must reference an Express site key. */
		express: Option[Boolean] = None,
	  /** Optional. The URI resource the user requested that triggered an assessment. */
		requestedUri: Option[String] = None,
	  /** Optional. The user response token provided by the reCAPTCHA Enterprise client-side integration on your site. */
		token: Option[String] = None,
	  /** Optional. The site key that was used to invoke reCAPTCHA Enterprise on your site and generate the token. */
		siteKey: Option[String] = None,
	  /** Optional. JA3 fingerprint for SSL clients. */
		ja3: Option[String] = None,
	  /** Optional. The user agent present in the request from the user's device related to this event. */
		userAgent: Option[String] = None,
	  /** Optional. The expected action for this type of event. This should be the same action provided at token generation time on client-side platforms already integrated with recaptcha enterprise. */
		expectedAction: Option[String] = None,
	  /** Optional. Flag for enabling firewall policy config assessment. If this flag is enabled, the firewall policy is evaluated and a suggested firewall action is returned in the response. */
		firewallPolicyEvaluation: Option[Boolean] = None,
	  /** Optional. The Fraud Prevention setting for this assessment. */
		fraudPrevention: Option[Schema.GoogleCloudRecaptchaenterpriseV1Event.FraudPreventionEnum] = None,
	  /** Optional. Data describing a payment transaction to be assessed. Sending this data enables reCAPTCHA Enterprise Fraud Prevention and the FraudPreventionAssessment component in the response. */
		transactionData: Option[Schema.GoogleCloudRecaptchaenterpriseV1TransactionData] = None,
	  /** Optional. Flag for running WAF token assessment. If enabled, the token must be specified, and have been created by a WAF-enabled key. */
		wafTokenAssessment: Option[Boolean] = None,
	  /** Optional. Deprecated: use `user_info.account_id` instead. Unique stable hashed user identifier for the request. The identifier must be hashed using hmac-sha256 with stable secret. */
		hashedAccountId: Option[String] = None,
	  /** Optional. The IP address in the request from the user's device related to this event. */
		userIpAddress: Option[String] = None,
	  /** Optional. Information about the user that generates this event, when they can be identified. They are often identified through the use of an account for logged-in requests or login/registration requests, or by providing user identifiers for guest actions like checkout. */
		userInfo: Option[Schema.GoogleCloudRecaptchaenterpriseV1UserInfo] = None,
	  /** Optional. HTTP header information about the request. */
		headers: Option[List[String]] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1MigrateKeyRequest(
	  /** Optional. If true, skips the billing check. A reCAPTCHA Enterprise key or migrated key behaves differently than a reCAPTCHA (non-Enterprise version) key when you reach a quota limit (see https://cloud.google.com/recaptcha/quotas#quota_limit). To avoid any disruption of your usage, we check that a billing account is present. If your usage of reCAPTCHA is under the free quota, you can safely skip the billing check and proceed with the migration. See https://cloud.google.com/recaptcha/docs/billing-information. */
		skipBillingCheck: Option[Boolean] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1TransactionData(
	  /** Optional. The Bank Identification Number - generally the first 6 or 8 digits of the card. */
		cardBin: Option[String] = None,
	  /** Unique identifier for the transaction. This custom identifier can be used to reference this transaction in the future, for example, labeling a refund or chargeback event. Two attempts at the same transaction should use the same transaction id. */
		transactionId: Option[String] = None,
	  /** Optional. The payment method for the transaction. The allowed values are: &#42; credit-card &#42; debit-card &#42; gift-card &#42; processor-{name} (If a third-party is used, for example, processor-paypal) &#42; custom-{name} (If an alternative method is used, for example, custom-crypto) */
		paymentMethod: Option[String] = None,
	  /** Optional. Address associated with the payment method when applicable. */
		billingAddress: Option[Schema.GoogleCloudRecaptchaenterpriseV1TransactionDataAddress] = None,
	  /** Optional. The last four digits of the card. */
		cardLastFour: Option[String] = None,
	  /** Optional. The value of shipping in the specified currency. 0 for free or no shipping. */
		shippingValue: Option[BigDecimal] = None,
	  /** Optional. Destination address if this transaction involves shipping a physical item. */
		shippingAddress: Option[Schema.GoogleCloudRecaptchaenterpriseV1TransactionDataAddress] = None,
	  /** Optional. Information about the user paying/initiating the transaction. */
		user: Option[Schema.GoogleCloudRecaptchaenterpriseV1TransactionDataUser] = None,
	  /** Optional. Information about the payment gateway's response to the transaction. */
		gatewayInfo: Option[Schema.GoogleCloudRecaptchaenterpriseV1TransactionDataGatewayInfo] = None,
	  /** Optional. The currency code in ISO-4217 format. */
		currencyCode: Option[String] = None,
	  /** Optional. Items purchased in this transaction. */
		items: Option[List[Schema.GoogleCloudRecaptchaenterpriseV1TransactionDataItem]] = None,
	  /** Optional. The decimal value of the transaction in the specified currency. */
		value: Option[BigDecimal] = None,
	  /** Optional. Information about the user or users fulfilling the transaction. */
		merchants: Option[List[Schema.GoogleCloudRecaptchaenterpriseV1TransactionDataUser]] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1FirewallPolicy(
	  /** Optional. The path for which this policy applies, specified as a glob pattern. For more information on glob, see the [manual page](https://man7.org/linux/man-pages/man7/glob.7.html). A path has a max length of 200 characters. */
		path: Option[String] = None,
	  /** Optional. A CEL (Common Expression Language) conditional expression that specifies if this policy applies to an incoming user request. If this condition evaluates to true and the requested path matched the path pattern, the associated actions should be executed by the caller. The condition string is checked for CEL syntax correctness on creation. For more information, see the [CEL spec](https://github.com/google/cel-spec) and its [language definition](https://github.com/google/cel-spec/blob/master/doc/langdef.md). A condition has a max length of 500 characters. */
		condition: Option[String] = None,
	  /** Identifier. The resource name for the FirewallPolicy in the format `projects/{project}/firewallpolicies/{firewallpolicy}`. */
		name: Option[String] = None,
	  /** Optional. The actions that the caller should take regarding user access. There should be at most one terminal action. A terminal action is any action that forces a response, such as `AllowAction`, `BlockAction` or `SubstituteAction`. Zero or more non-terminal actions such as `SetHeader` might be specified. A single policy can contain up to 16 actions. */
		actions: Option[List[Schema.GoogleCloudRecaptchaenterpriseV1FirewallAction]] = None,
	  /** Optional. A description of what this policy aims to achieve, for convenience purposes. The description can at most include 256 UTF-8 characters. */
		description: Option[String] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1SearchRelatedAccountGroupMembershipsResponse(
	  /** The queried memberships. */
		relatedAccountGroupMemberships: Option[List[Schema.GoogleCloudRecaptchaenterpriseV1RelatedAccountGroupMembership]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1ListIpOverridesResponse(
	  /** Token to retrieve the next page of results. If this field is empty, no keys remain in the results. */
		nextPageToken: Option[String] = None,
	  /** IP Overrides details. */
		ipOverrides: Option[List[Schema.GoogleCloudRecaptchaenterpriseV1IpOverrideData]] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1AndroidKeySettings(
	  /** Optional. Android package names of apps allowed to use the key. Example: 'com.companyname.appname' */
		allowedPackageNames: Option[List[String]] = None,
	  /** Optional. Set to true for keys that are used in an Android application that is available for download in app stores in addition to the Google Play Store. */
		supportNonGoogleAppStoreDistribution: Option[Boolean] = None,
	  /** Optional. If set to true, allowed_package_names are not enforced. */
		allowAllPackageNames: Option[Boolean] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1ListRelatedAccountGroupMembershipsResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The memberships listed by the query. */
		relatedAccountGroupMemberships: Option[List[Schema.GoogleCloudRecaptchaenterpriseV1RelatedAccountGroupMembership]] = None
	)
	
	case class GoogleRpcStatus(
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None,
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1FraudPreventionAssessmentCardTestingVerdict(
	  /** Output only. Probability of this transaction attempt being part of a card testing attack. Values are from 0.0 (lowest) to 1.0 (highest). */
		risk: Option[BigDecimal] = None
	)
	
	object GoogleCloudRecaptchaenterpriseV1WebKeySettings {
		enum IntegrationTypeEnum extends Enum[IntegrationTypeEnum] { case INTEGRATION_TYPE_UNSPECIFIED, SCORE, CHECKBOX, INVISIBLE }
		enum ChallengeSecurityPreferenceEnum extends Enum[ChallengeSecurityPreferenceEnum] { case CHALLENGE_SECURITY_PREFERENCE_UNSPECIFIED, USABILITY, BALANCE, SECURITY }
	}
	case class GoogleCloudRecaptchaenterpriseV1WebKeySettings(
	  /** Optional. If set to true, the key can be used on AMP (Accelerated Mobile Pages) websites. This is supported only for the SCORE integration type. */
		allowAmpTraffic: Option[Boolean] = None,
	  /** Required. Describes how this key is integrated with the website. */
		integrationType: Option[Schema.GoogleCloudRecaptchaenterpriseV1WebKeySettings.IntegrationTypeEnum] = None,
	  /** Optional. If set to true, it means allowed_domains are not enforced. */
		allowAllDomains: Option[Boolean] = None,
	  /** Optional. Settings for the frequency and difficulty at which this key triggers captcha challenges. This should only be specified for IntegrationTypes CHECKBOX and INVISIBLE and SCORE_AND_CHALLENGE. */
		challengeSecurityPreference: Option[Schema.GoogleCloudRecaptchaenterpriseV1WebKeySettings.ChallengeSecurityPreferenceEnum] = None,
	  /** Optional. Domains or subdomains of websites allowed to use the key. All subdomains of an allowed domain are automatically allowed. A valid domain requires a host and must not include any path, port, query or fragment. Examples: 'example.com' or 'subdomain.example.com' */
		allowedDomains: Option[List[String]] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1TransactionDataItem(
	  /** Optional. The full name of the item. */
		name: Option[String] = None,
	  /** Optional. When a merchant is specified, its corresponding account_id. Necessary to populate marketplace-style transactions. */
		merchantAccountId: Option[String] = None,
	  /** Optional. The value per item that the user is paying, in the transaction currency, after discounts. */
		value: Option[BigDecimal] = None,
	  /** Optional. The quantity of this item that is being purchased. */
		quantity: Option[String] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1AddIpOverrideResponse(
	
	)
	
	case class GoogleCloudRecaptchaenterpriseV1ListKeysResponse(
	  /** Token to retrieve the next page of results. It is set to empty if no keys remain in results. */
		nextPageToken: Option[String] = None,
	  /** Key details. */
		keys: Option[List[Schema.GoogleCloudRecaptchaenterpriseV1Key]] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1PhoneFraudAssessment(
	  /** Output only. Assessment of this phone event for risk of SMS toll fraud. */
		smsTollFraudVerdict: Option[Schema.GoogleCloudRecaptchaenterpriseV1SmsTollFraudVerdict] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1FirewallActionAllowAction(
	
	)
	
	case class GoogleCloudRecaptchaenterpriseV1FirewallPolicyAssessment(
	  /** Output only. If the processing of a policy config fails, an error is populated and the firewall_policy is left empty. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** Output only. The policy that matched the request. If more than one policy may match, this is the first match. If no policy matches the incoming request, the policy field is left empty. */
		firewallPolicy: Option[Schema.GoogleCloudRecaptchaenterpriseV1FirewallPolicy] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1ReorderFirewallPoliciesRequest(
	  /** Required. A list containing all policy names, in the new order. Each name is in the format `projects/{project}/firewallpolicies/{firewallpolicy}`. */
		names: Option[List[String]] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1EndpointVerificationInfo(
	  /** Email address for which to trigger a verification request. */
		emailAddress: Option[String] = None,
	  /** Phone number for which to trigger a verification request. Should be given in E.164 format. */
		phoneNumber: Option[String] = None,
	  /** Output only. Token to provide to the client to trigger endpoint verification. It must be used within 15 minutes. */
		requestToken: Option[String] = None,
	  /** Output only. Timestamp of the last successful verification for the endpoint, if any. */
		lastVerificationTime: Option[String] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1AddIpOverrideRequest(
	  /** Required. IP override added to the key. */
		ipOverrideData: Option[Schema.GoogleCloudRecaptchaenterpriseV1IpOverrideData] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1TransactionDataUser(
	  /** Optional. The phone number of the user, with country code. */
		phoneNumber: Option[String] = None,
	  /** Optional. Whether the phone number has been verified to be accessible by the user (OTP or similar). */
		phoneVerified: Option[Boolean] = None,
	  /** Optional. The email address of the user. */
		email: Option[String] = None,
	  /** Optional. Whether the email has been verified to be accessible by the user (OTP or similar). */
		emailVerified: Option[Boolean] = None,
	  /** Optional. Unique account identifier for this user. If using account defender, this should match the hashed_account_id field. Otherwise, a unique and persistent identifier for this account. */
		accountId: Option[String] = None,
	  /** Optional. The epoch milliseconds of the user's account creation. */
		creationMs: Option[String] = None
	)
	
	object GoogleCloudRecaptchaenterpriseV1SmsTollFraudVerdict {
		enum ReasonsEnum extends Enum[ReasonsEnum] { case SMS_TOLL_FRAUD_REASON_UNSPECIFIED, INVALID_PHONE_NUMBER }
	}
	case class GoogleCloudRecaptchaenterpriseV1SmsTollFraudVerdict(
	  /** Output only. Probability of an SMS event being fraudulent. Values are from 0.0 (lowest) to 1.0 (highest). */
		risk: Option[BigDecimal] = None,
	  /** Output only. Reasons contributing to the SMS toll fraud verdict. */
		reasons: Option[List[Schema.GoogleCloudRecaptchaenterpriseV1SmsTollFraudVerdict.ReasonsEnum]] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1ExpressKeySettings(
	
	)
	
	case class GoogleCloudRecaptchaenterpriseV1ScoreDistribution(
	  /** Map key is score value multiplied by 100. The scores are discrete values between [0, 1]. The maximum number of buckets is on order of a few dozen, but typically much lower (ie. 10). */
		scoreBuckets: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1FraudPreventionAssessmentStolenInstrumentVerdict(
	  /** Output only. Probability of this transaction being executed with a stolen instrument. Values are from 0.0 (lowest) to 1.0 (highest). */
		risk: Option[BigDecimal] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1FraudSignalsUserSignals(
	  /** Output only. Likelihood (from 0.0 to 1.0) this user includes synthetic components in their identity, such as a randomly generated email address, temporary phone number, or fake shipping address. */
		syntheticRisk: Option[BigDecimal] = None,
	  /** Output only. This user (based on email, phone, and other identifiers) has been seen on the internet for at least this number of days. */
		activeDaysLowerBound: Option[Int] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1FraudPreventionAssessment(
	  /** Output only. Assessment of this transaction for risk of a stolen instrument. */
		stolenInstrumentVerdict: Option[Schema.GoogleCloudRecaptchaenterpriseV1FraudPreventionAssessmentStolenInstrumentVerdict] = None,
	  /** Output only. Probability of this transaction being fraudulent. Summarizes the combined risk of attack vectors below. Values are from 0.0 (lowest) to 1.0 (highest). */
		transactionRisk: Option[BigDecimal] = None,
	  /** Output only. Assessment of this transaction for risk of being part of a card testing attack. */
		cardTestingVerdict: Option[Schema.GoogleCloudRecaptchaenterpriseV1FraudPreventionAssessmentCardTestingVerdict] = None,
	  /** Output only. Assessment of this transaction for behavioral trust. */
		behavioralTrustVerdict: Option[Schema.GoogleCloudRecaptchaenterpriseV1FraudPreventionAssessmentBehavioralTrustVerdict] = None
	)
	
	object GoogleCloudRecaptchaenterpriseV1AccountDefenderAssessment {
		enum LabelsEnum extends Enum[LabelsEnum] { case ACCOUNT_DEFENDER_LABEL_UNSPECIFIED, PROFILE_MATCH, SUSPICIOUS_LOGIN_ACTIVITY, SUSPICIOUS_ACCOUNT_CREATION, RELATED_ACCOUNTS_NUMBER_HIGH }
	}
	case class GoogleCloudRecaptchaenterpriseV1AccountDefenderAssessment(
	  /** Output only. Labels for this request. */
		labels: Option[List[Schema.GoogleCloudRecaptchaenterpriseV1AccountDefenderAssessment.LabelsEnum]] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1RelatedAccountGroupMembership(
	  /** Deprecated: use `account_id` instead. The unique stable hashed account identifier of the member. The identifier corresponds to a `hashed_account_id` provided in a previous `CreateAssessment` or `AnnotateAssessment` call. */
		hashedAccountId: Option[String] = None,
	  /** Required. Identifier. The resource name for this membership in the format `projects/{project}/relatedaccountgroups/{relatedaccountgroup}/memberships/{membership}`. */
		name: Option[String] = None,
	  /** The unique stable account identifier of the member. The identifier corresponds to an `account_id` provided in a previous `CreateAssessment` or `AnnotateAssessment` call. */
		accountId: Option[String] = None
	)
	
	object GoogleCloudRecaptchaenterpriseV1TokenProperties {
		enum InvalidReasonEnum extends Enum[InvalidReasonEnum] { case INVALID_REASON_UNSPECIFIED, UNKNOWN_INVALID_REASON, MALFORMED, EXPIRED, DUPE, MISSING, BROWSER_ERROR }
	}
	case class GoogleCloudRecaptchaenterpriseV1TokenProperties(
	  /** Output only. Action name provided at token generation. */
		action: Option[String] = None,
	  /** Output only. The name of the Android package with which the token was generated (Android keys only). */
		androidPackageName: Option[String] = None,
	  /** Output only. The ID of the iOS bundle with which the token was generated (iOS keys only). */
		iosBundleId: Option[String] = None,
	  /** Output only. Reason associated with the response when valid = false. */
		invalidReason: Option[Schema.GoogleCloudRecaptchaenterpriseV1TokenProperties.InvalidReasonEnum] = None,
	  /** Output only. The timestamp corresponding to the generation of the token. */
		createTime: Option[String] = None,
	  /** Output only. Whether the provided user response token is valid. When valid = false, the reason could be specified in invalid_reason or it could also be due to a user failing to solve a challenge or a sitekey mismatch (i.e the sitekey used to generate the token was different than the one specified in the assessment). */
		valid: Option[Boolean] = None,
	  /** Output only. The hostname of the page on which the token was generated (Web keys only). */
		hostname: Option[String] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1AssessmentEnvironment(
	  /** Optional. The version of the client module. For example, "1.0.0". */
		version: Option[String] = None,
	  /** Optional. Identifies the client module initiating the CreateAssessment request. This can be the link to the client module's project. Examples include: - "github.com/GoogleCloudPlatform/recaptcha-enterprise-google-tag-manager" - "cloud.google.com/recaptcha/docs/implement-waf-akamai" - "cloud.google.com/recaptcha/docs/implement-waf-cloudflare" - "wordpress.org/plugins/recaptcha-something" */
		client: Option[String] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1ListRelatedAccountGroupsResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The groups of related accounts listed by the query. */
		relatedAccountGroups: Option[List[Schema.GoogleCloudRecaptchaenterpriseV1RelatedAccountGroup]] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1ChallengeMetrics(
	  /** Count of nocaptchas (successful verification without a challenge) issued. */
		nocaptchaCount: Option[String] = None,
	  /** Count of submitted challenge solutions that were incorrect or otherwise deemed suspicious such that a subsequent challenge was triggered. */
		failedCount: Option[String] = None,
	  /** Count of reCAPTCHA checkboxes or badges rendered. This is mostly equivalent to a count of pageloads for pages that include reCAPTCHA. */
		pageloadCount: Option[String] = None,
	  /** Count of nocaptchas (successful verification without a challenge) plus submitted challenge solutions that were correct and resulted in verification. */
		passedCount: Option[String] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1FirewallAction(
	  /** This action transparently serves a different page to an offending user. */
		substitute: Option[Schema.GoogleCloudRecaptchaenterpriseV1FirewallActionSubstituteAction] = None,
	  /** This action denies access to a given page. The user gets an HTTP error code. */
		block: Option[Schema.GoogleCloudRecaptchaenterpriseV1FirewallActionBlockAction] = None,
	  /** The user request did not match any policy and should be allowed access to the requested resource. */
		allow: Option[Schema.GoogleCloudRecaptchaenterpriseV1FirewallActionAllowAction] = None,
	  /** This action injects reCAPTCHA JavaScript code into the HTML page returned by the site backend. */
		includeRecaptchaScript: Option[Schema.GoogleCloudRecaptchaenterpriseV1FirewallActionIncludeRecaptchaScriptAction] = None,
	  /** This action sets a custom header but allow the request to continue to the customer backend. */
		setHeader: Option[Schema.GoogleCloudRecaptchaenterpriseV1FirewallActionSetHeaderAction] = None,
	  /** This action redirects the request to a reCAPTCHA interstitial to attach a token. */
		redirect: Option[Schema.GoogleCloudRecaptchaenterpriseV1FirewallActionRedirectAction] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1RemoveIpOverrideResponse(
	
	)
	
	case class GoogleCloudRecaptchaenterpriseV1SearchRelatedAccountGroupMembershipsRequest(
	  /** Optional. Deprecated: use `account_id` instead. The unique stable hashed account identifier used to search connections. The identifier should correspond to a `hashed_account_id` provided in a previous `CreateAssessment` or `AnnotateAssessment` call. Either hashed_account_id or account_id must be set, but not both. */
		hashedAccountId: Option[String] = None,
	  /** Optional. The unique stable account identifier used to search connections. The identifier should correspond to an `account_id` provided in a previous `CreateAssessment` or `AnnotateAssessment` call. Either hashed_account_id or account_id must be set, but not both. */
		accountId: Option[String] = None,
	  /** Optional. A page token, received from a previous `SearchRelatedAccountGroupMemberships` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `SearchRelatedAccountGroupMemberships` must match the call that provided the page token. */
		pageToken: Option[String] = None,
	  /** Optional. The maximum number of groups to return. The service might return fewer than this value. If unspecified, at most 50 groups are returned. The maximum value is 1000; values above 1000 are coerced to 1000. */
		pageSize: Option[Int] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1FirewallActionSetHeaderAction(
	  /** Optional. The header key to set in the request to the backend server. */
		key: Option[String] = None,
	  /** Optional. The header value to set in the request to the backend server. */
		value: Option[String] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1ReorderFirewallPoliciesResponse(
	
	)
	
	object GoogleCloudRecaptchaenterpriseV1AccountVerificationInfo {
		enum LatestVerificationResultEnum extends Enum[LatestVerificationResultEnum] { case RESULT_UNSPECIFIED, SUCCESS_USER_VERIFIED, ERROR_USER_NOT_VERIFIED, ERROR_SITE_ONBOARDING_INCOMPLETE, ERROR_RECIPIENT_NOT_ALLOWED, ERROR_RECIPIENT_ABUSE_LIMIT_EXHAUSTED, ERROR_CRITICAL_INTERNAL, ERROR_CUSTOMER_QUOTA_EXHAUSTED, ERROR_VERIFICATION_BYPASSED, ERROR_VERDICT_MISMATCH }
	}
	case class GoogleCloudRecaptchaenterpriseV1AccountVerificationInfo(
	  /** Output only. Result of the latest account verification challenge. */
		latestVerificationResult: Option[Schema.GoogleCloudRecaptchaenterpriseV1AccountVerificationInfo.LatestVerificationResultEnum] = None,
	  /** Username of the account that is being verified. Deprecated. Customers should now provide the `account_id` field in `event.user_info`. */
		username: Option[String] = None,
	  /** Optional. Endpoints that can be used for identity verification. */
		endpoints: Option[List[Schema.GoogleCloudRecaptchaenterpriseV1EndpointVerificationInfo]] = None,
	  /** Optional. Language code preference for the verification message, set as a IETF BCP 47 language code. */
		languageCode: Option[String] = None
	)
	
	case class GoogleCloudRecaptchaenterpriseV1FirewallActionBlockAction(
	
	)
}
