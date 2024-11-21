package com.boresjo.play.api.google.reseller

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object Customer {
		enum CustomerTypeEnum extends Enum[CustomerTypeEnum] { case customerTypeUnspecified, domain, team }
	}
	case class Customer(
	  /** This property will always be returned in a response as the unique identifier generated by Google. In a request, this property can be either the primary domain or the unique identifier generated by Google. */
		customerId: Option[String] = None,
	  /** The customer's primary domain name string. `customerDomain` is required when creating a new customer. Do not include the `www` prefix in the domain when adding a customer. */
		customerDomain: Option[String] = None,
	  /** A customer's address information. Each field has a limit of 255 charcters. */
		postalAddress: Option[Schema.Address] = None,
	  /** Customer contact phone number. Must start with "+" followed by the country code. The rest of the number can be contiguous numbers or respect the phone local format conventions, but it must be a real phone number and not, for example, "123". This field is silently ignored if invalid. */
		phoneNumber: Option[String] = None,
	  /** Like the "Customer email" in the reseller tools, this email is the secondary contact used if something happens to the customer's service such as service outage or a security issue. This property is required when creating a new "domain" customer and should not use the same domain as `customerDomain`. The `alternateEmail` field is not necessary to create a "team" customer. */
		alternateEmail: Option[String] = None,
	  /** URL to customer's Admin console dashboard. The read-only URL is generated by the API service. This is used if your client application requires the customer to complete a task in the Admin console. */
		resourceUiUrl: Option[String] = None,
	  /** Whether the customer's primary domain has been verified. */
		customerDomainVerified: Option[Boolean] = None,
	  /** Identifies the resource as a customer. Value: `reseller#customer` */
		kind: Option[String] = Some("""reseller#customer"""),
	  /** Identifies the type of the customer. Acceptable values include: &#42; `domain`: Implies a domain-verified customer (default). &#42; `team`: Implies an email-verified customer. For more information, see [managed teams](https://support.google.com/a/users/answer/9939479). */
		customerType: Option[Schema.Customer.CustomerTypeEnum] = None,
	  /** The first admin details of the customer, present in case of TEAM customer. */
		primaryAdmin: Option[Schema.PrimaryAdmin] = None
	)
	
	case class Address(
	  /** For `countryCode` information, see the ISO 3166 country code elements. Verify that country is approved for resale of Google products. This property is required when creating a new customer. */
		countryCode: Option[String] = None,
	  /** Identifies the resource as a customer address. Value: `customers#address` */
		kind: Option[String] = Some("""customers#address"""),
	  /** An example of a `region` value is `CA` for the state of California. */
		region: Option[String] = None,
	  /** Line 2 of the address. */
		addressLine2: Option[String] = None,
	  /** An example of a `locality` value is the city of `San Francisco`. */
		locality: Option[String] = None,
	  /** Line 3 of the address. */
		addressLine3: Option[String] = None,
	  /** A `postalCode` example is a postal zip code such as `94043`. This property is required when creating a new customer. */
		postalCode: Option[String] = None,
	  /** A customer's physical address. An address can be composed of one to three lines. The `addressline2` and `addressLine3` are optional. */
		addressLine1: Option[String] = None,
	  /** The company or company division name. This is required. */
		organizationName: Option[String] = None,
	  /** The customer contact's name. This is required. */
		contactName: Option[String] = None
	)
	
	case class PrimaryAdmin(
	  /** The business email of the primary administrator of the customer. The email verification link is sent to this email address at the time of customer creation. Primary administrators have access to the customer's Admin Console, including the ability to invite and evict users and manage the administrative needs of the customer. */
		primaryEmail: Option[String] = None
	)
	
	case class ResellernotifyGetwatchdetailsResponse(
	  /** Topic name of the PubSub */
		topicName: Option[String] = None,
	  /** List of registered service accounts. */
		serviceAccountEmailAddresses: Option[List[String]] = None
	)
	
	case class ResellernotifyResource(
	  /** Topic name of the PubSub */
		topicName: Option[String] = None
	)
	
	object Subscription {
		object PlanItem {
			case class CommitmentIntervalItem(
			  /** An annual commitment plan's interval's `startTime` in milliseconds using UNIX Epoch format. See an example Epoch converter. */
				startTime: Option[String] = None,
			  /** An annual commitment plan's interval's `endTime` in milliseconds using the UNIX Epoch format. See an example Epoch converter. */
				endTime: Option[String] = None
			)
		}
		case class PlanItem(
		  /** The `planName` property is required. This is the name of the subscription's plan. For more information about the Google payment plans, see the API concepts. Possible values are: - `ANNUAL_MONTHLY_PAY` — The annual commitment plan with monthly payments. &#42;Caution: &#42;`ANNUAL_MONTHLY_PAY` is returned as `ANNUAL` in all API responses. - `ANNUAL_YEARLY_PAY` — The annual commitment plan with yearly payments - `FLEXIBLE` — The flexible plan - `TRIAL` — The 30-day free trial plan. A subscription in trial will be suspended after the 30th free day if no payment plan is assigned. Calling `changePlan` will assign a payment plan to a trial but will not activate the plan. A trial will automatically begin its assigned payment plan after its 30th free day or immediately after calling `startPaidService`. - `FREE` — The free plan is exclusive to the Cloud Identity SKU and does not incur any billing.  */
			planName: Option[String] = None,
		  /** The `isCommitmentPlan` property's boolean value identifies the plan as an annual commitment plan: - `true` — The subscription's plan is an annual commitment plan. - `false` — The plan is not an annual commitment plan.  */
			isCommitmentPlan: Option[Boolean] = None,
		  /** In this version of the API, annual commitment plan's interval is one year. &#42;Note: &#42;When `billingMethod` value is `OFFLINE`, the subscription property object `plan.commitmentInterval` is omitted in all API responses.  */
			commitmentInterval: Option[Schema.Subscription.PlanItem.CommitmentIntervalItem] = None
		)
		
		case class TrialSettingsItem(
		  /** Determines if a subscription's plan is in a 30-day free trial or not: - `true` — The plan is in trial. - `false` — The plan is not in trial.  */
			isInTrial: Option[Boolean] = None,
		  /** Date when the trial ends. The value is in milliseconds using the UNIX Epoch format. See an example Epoch converter. */
			trialEndTime: Option[String] = None
		)
		
		case class TransferInfoItem(
		  /** The time when transfer token or intent to transfer will expire. The time is in milliseconds using UNIX Epoch format. */
			transferabilityExpirationTime: Option[String] = None,
		  /** When inserting a subscription, this is the minimum number of seats listed in the transfer order for this product. For example, if the customer has 20 users, the reseller cannot place a transfer order of 15 seats. The minimum is 20 seats. */
			minimumTransferableSeats: Option[Int] = None,
		  /** The `skuId` of the current resold subscription. This is populated only when the customer has a subscription with a legacy SKU and the subscription resource is populated with the `skuId` of the SKU recommended for the transfer. */
			currentLegacySkuId: Option[String] = None
		)
	}
	case class Subscription(
	  /** This property will always be returned in a response as the unique identifier generated by Google. In a request, this property can be either the primary domain or the unique identifier generated by Google. */
		customerId: Option[String] = None,
	  /** The `subscriptionId` is the subscription identifier and is unique for each customer. This is a required property. Since a `subscriptionId` changes when a subscription is updated, we recommend not using this ID as a key for persistent data. Use the `subscriptionId` as described in retrieve all reseller subscriptions. */
		subscriptionId: Option[String] = None,
	  /** A required property. The `skuId` is a unique system identifier for a product's SKU assigned to a customer in the subscription. For products and SKUs available in this version of the API, see Product and SKU IDs. */
		skuId: Option[String] = None,
	  /** The `creationTime` property is the date when subscription was created. It is in milliseconds using the Epoch format. See an example Epoch converter. */
		creationTime: Option[String] = None,
	  /** Identifies the resource as a Subscription. Value: `reseller#subscription` */
		kind: Option[String] = Some("""reseller#subscription"""),
	  /** This is a required property. The number and limit of user seat licenses in the plan. */
		seats: Option[Schema.Seats] = None,
	  /** Renewal settings for the annual commitment plan. For more detailed information, see renewal options in the administrator help center. */
		renewalSettings: Option[Schema.RenewalSettings] = None,
	  /** This is an optional property. This purchase order (PO) information is for resellers to use for their company tracking usage. If a `purchaseOrderId` value is given it appears in the API responses and shows up in the invoice. The property accepts up to 80 plain text characters. */
		purchaseOrderId: Option[String] = None,
	  /** This is an optional property. */
		status: Option[String] = None,
	  /** URL to customer's Subscriptions page in the Admin console. The read-only URL is generated by the API service. This is used if your client application requires the customer to complete a task using the Subscriptions page in the Admin console. */
		resourceUiUrl: Option[String] = None,
	  /** Read-only field that returns the current billing method for a subscription. */
		billingMethod: Option[String] = None,
	  /** Read-only field containing an enumerable of all the current suspension reasons for a subscription. It is possible for a subscription to have many concurrent, overlapping suspension reasons. A subscription's `STATUS` is `SUSPENDED` until all pending suspensions are removed. Possible options include: - `PENDING_TOS_ACCEPTANCE` - The customer has not logged in and accepted the G Suite Resold Terms of Services. - `RENEWAL_WITH_TYPE_CANCEL` - The customer's commitment ended and their service was cancelled at the end of their term. - `RESELLER_INITIATED` - A manual suspension invoked by a Reseller. - `TRIAL_ENDED` - The customer's trial expired without a plan selected. - `OTHER` - The customer is suspended for an internal Google reason (e.g. abuse or otherwise).  */
		suspensionReasons: Option[List[String]] = None,
	  /** Primary domain name of the customer */
		customerDomain: Option[String] = None,
	  /** Google-issued code (100 char max) for discounted pricing on subscription plans. Deal code must be included in `insert` requests in order to receive discounted rate. This property is optional, regular pricing applies if left empty. */
		dealCode: Option[String] = None,
	  /** Read-only external display name for a product's SKU assigned to a customer in the subscription. SKU names are subject to change at Google's discretion. For products and SKUs available in this version of the API, see Product and SKU IDs. */
		skuName: Option[String] = None,
	  /** The `plan` property is required. In this version of the API, the G Suite plans are the flexible plan, annual commitment plan, and the 30-day free trial plan. For more information about the API"s payment plans, see the API concepts. */
		plan: Option[Schema.Subscription.PlanItem] = None,
	  /** The G Suite annual commitment and flexible payment plans can be in a 30-day free trial. For more information, see the API concepts. */
		trialSettings: Option[Schema.Subscription.TrialSettingsItem] = None,
	  /** Read-only transfer related information for the subscription. For more information, see retrieve transferable subscriptions for a customer. */
		transferInfo: Option[Schema.Subscription.TransferInfoItem] = None
	)
	
	case class Seats(
	  /** This is a required property and is exclusive to subscriptions with `ANNUAL_MONTHLY_PAY` and `ANNUAL_YEARLY_PAY` plans. This property sets the maximum number of licenses assignable to users on a subscription. The reseller can add more licenses, but once set, the `numberOfSeats` cannot be reduced until renewal. The reseller is invoiced based on the `numberOfSeats` value regardless of how many of these user licenses are assigned. &#42;Note: &#42;Google Workspace subscriptions automatically assign a license to every user. */
		numberOfSeats: Option[Int] = None,
	  /** This is a required property and is exclusive to subscriptions with `FLEXIBLE` or `TRIAL` plans. This property sets the maximum number of licensed users allowed on a subscription. This quantity can be increased up to the maximum limit defined in the reseller's contract. The minimum quantity is the current number of users in the customer account. &#42;Note: &#42;G Suite subscriptions automatically assign a license to every user. */
		maximumNumberOfSeats: Option[Int] = None,
	  /** Read-only field containing the current number of users that are assigned a license for the product defined in `skuId`. This field's value is equivalent to the numerical count of users returned by the Enterprise License Manager API method: [`listForProductAndSku`](/admin-sdk/licensing/v1/reference/licenseAssignments/listForProductAndSku). */
		licensedNumberOfSeats: Option[Int] = None,
	  /** Identifies the resource as a subscription seat setting. Value: `subscriptions#seats` */
		kind: Option[String] = Some("""subscriptions#seats""")
	)
	
	case class RenewalSettings(
	  /** Renewal settings for the annual commitment plan. For more detailed information, see renewal options in the administrator help center. When renewing a subscription, the `renewalType` is a required property. */
		renewalType: Option[String] = None,
	  /** Identifies the resource as a subscription renewal setting. Value: `subscriptions#renewalSettings` */
		kind: Option[String] = Some("""subscriptions#renewalSettings""")
	)
	
	case class ChangePlanRequest(
	  /** Identifies the resource as a subscription change plan request. Value: `subscriptions#changePlanRequest` */
		kind: Option[String] = Some("""subscriptions#changePlanRequest"""),
	  /** The `planName` property is required. This is the name of the subscription's payment plan. For more information about the Google payment plans, see API concepts. Possible values are: - `ANNUAL_MONTHLY_PAY` - The annual commitment plan with monthly payments &#42;Caution: &#42;`ANNUAL_MONTHLY_PAY` is returned as `ANNUAL` in all API responses. - `ANNUAL_YEARLY_PAY` - The annual commitment plan with yearly payments - `FLEXIBLE` - The flexible plan - `TRIAL` - The 30-day free trial plan  */
		planName: Option[String] = None,
	  /** This is a required property. The seats property is the number of user seat licenses. */
		seats: Option[Schema.Seats] = None,
	  /** This is an optional property. This purchase order (PO) information is for resellers to use for their company tracking usage. If a `purchaseOrderId` value is given it appears in the API responses and shows up in the invoice. The property accepts up to 80 plain text characters. */
		purchaseOrderId: Option[String] = None,
	  /** Google-issued code (100 char max) for discounted pricing on subscription plans. Deal code must be included in `changePlan` request in order to receive discounted rate. This property is optional. If a deal code has already been added to a subscription, this property may be left empty and the existing discounted rate will still apply (if not empty, only provide the deal code that is already present on the subscription). If a deal code has never been added to a subscription and this property is left blank, regular pricing will apply. */
		dealCode: Option[String] = None
	)
	
	case class Subscriptions(
	  /** Identifies the resource as a collection of subscriptions. Value: reseller#subscriptions */
		kind: Option[String] = Some("""reseller#subscriptions"""),
	  /** The subscriptions in this page of results. */
		subscriptions: Option[List[Schema.Subscription]] = None,
	  /** The continuation token, used to page through large result sets. Provide this value in a subsequent request to return the next page of results. */
		nextPageToken: Option[String] = None
	)
}