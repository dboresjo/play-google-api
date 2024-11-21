package com.boresjo.play.api.google.cloudbilling

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class BillingAccount(
	  /** Output only. The resource name of the billing account. The resource name has the form `billingAccounts/{billing_account_id}`. For example, `billingAccounts/012345-567890-ABCDEF` would be the resource name for billing account `012345-567890-ABCDEF`. */
		name: Option[String] = None,
	  /** Output only. True if the billing account is open, and will therefore be charged for any usage on associated projects. False if the billing account is closed, and therefore projects associated with it are unable to use paid services. */
		open: Option[Boolean] = None,
	  /** The display name given to the billing account, such as `My Billing Account`. This name is displayed in the Google Cloud Console. */
		displayName: Option[String] = None,
	  /** If this account is a [subaccount](https://cloud.google.com/billing/docs/concepts), then this will be the resource name of the parent billing account that it is being resold through. Otherwise this will be empty. */
		masterBillingAccount: Option[String] = None,
	  /** Output only. The billing account's parent resource identifier. Use the `MoveBillingAccount` method to update the account's parent resource if it is a organization. Format: - `organizations/{organization_id}`, for example, `organizations/12345678` - `billingAccounts/{billing_account_id}`, for example, `billingAccounts/012345-567890-ABCDEF` */
		parent: Option[String] = None,
	  /** Optional. The currency in which the billing account is billed and charged, represented as an ISO 4217 code such as `USD`. Billing account currency is determined at the time of billing account creation and cannot be updated subsequently, so this field should not be set on update requests. In addition, a subaccount always matches the currency of its parent billing account, so this field should not be set on subaccount creation requests. Clients can read this field to determine the currency of an existing billing account. */
		currencyCode: Option[String] = None
	)
	
	case class ListBillingAccountsResponse(
	  /** A list of billing accounts. */
		billingAccounts: Option[List[Schema.BillingAccount]] = None,
	  /** A token to retrieve the next page of results. To retrieve the next page, call `ListBillingAccounts` again with the `page_token` field set to this value. This field is empty if there are no more results to retrieve. */
		nextPageToken: Option[String] = None
	)
	
	case class ListProjectBillingInfoResponse(
	  /** A list of `ProjectBillingInfo` resources representing the projects associated with the billing account. */
		projectBillingInfo: Option[List[Schema.ProjectBillingInfo]] = None,
	  /** A token to retrieve the next page of results. To retrieve the next page, call `ListProjectBillingInfo` again with the `page_token` field set to this value. This field is empty if there are no more results to retrieve. */
		nextPageToken: Option[String] = None
	)
	
	case class ProjectBillingInfo(
	  /** Output only. The resource name for the `ProjectBillingInfo`; has the form `projects/{project_id}/billingInfo`. For example, the resource name for the billing information for project `tokyo-rain-123` would be `projects/tokyo-rain-123/billingInfo`. */
		name: Option[String] = None,
	  /** Output only. The ID of the project that this `ProjectBillingInfo` represents, such as `tokyo-rain-123`. This is a convenience field so that you don't need to parse the `name` field to obtain a project ID. */
		projectId: Option[String] = None,
	  /** The resource name of the billing account associated with the project, if any. For example, `billingAccounts/012345-567890-ABCDEF`. */
		billingAccountName: Option[String] = None,
	  /** Output only. True if the project is associated with an open billing account, to which usage on the project is charged. False if the project is associated with a closed billing account, or no billing account at all, and therefore cannot use paid services. */
		billingEnabled: Option[Boolean] = None
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
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None,
	  /** OPTIONAL: A FieldMask specifying which fields of the policy to modify. Only the fields in the mask will be modified. If no mask is provided, the following default mask is used: `paths: "bindings, etag"` */
		updateMask: Option[String] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class MoveBillingAccountRequest(
	  /** Required. The resource name of the Organization to move the billing account under. Must be of the form `organizations/{organization_id}`. */
		destinationParent: Option[String] = None
	)
	
	case class ListServicesResponse(
	  /** A list of services. */
		services: Option[List[Schema.Service]] = None,
	  /** A token to retrieve the next page of results. To retrieve the next page, call `ListServices` again with the `page_token` field set to this value. This field is empty if there are no more results to retrieve. */
		nextPageToken: Option[String] = None
	)
	
	case class Service(
	  /** The resource name for the service. Example: "services/6F81-5844-456A" */
		name: Option[String] = None,
	  /** The identifier for the service. Example: "6F81-5844-456A" */
		serviceId: Option[String] = None,
	  /** A human readable display name for this service. */
		displayName: Option[String] = None,
	  /** The business under which the service is offered. Ex. "businessEntities/GCP", "businessEntities/Maps" */
		businessEntityName: Option[String] = None
	)
	
	case class ListSkusResponse(
	  /** The list of public SKUs of the given service. */
		skus: Option[List[Schema.Sku]] = None,
	  /** A token to retrieve the next page of results. To retrieve the next page, call `ListSkus` again with the `page_token` field set to this value. This field is empty if there are no more results to retrieve. */
		nextPageToken: Option[String] = None
	)
	
	case class Sku(
	  /** The resource name for the SKU. Example: "services/6F81-5844-456A/skus/D041-B8A1-6E0B" */
		name: Option[String] = None,
	  /** The identifier for the SKU. Example: "D041-B8A1-6E0B" */
		skuId: Option[String] = None,
	  /** A human readable description of the SKU, has a maximum length of 256 characters. */
		description: Option[String] = None,
	  /** The category hierarchy of this SKU, purely for organizational purpose. */
		category: Option[Schema.Category] = None,
	  /** List of service regions this SKU is offered at. Example: "asia-east1" Service regions can be found at https://cloud.google.com/about/locations/ */
		serviceRegions: Option[List[String]] = None,
	  /** A timeline of pricing info for this SKU in chronological order. */
		pricingInfo: Option[List[Schema.PricingInfo]] = None,
	  /** Identifies the service provider. This is 'Google' for first party services in Google Cloud Platform. */
		serviceProviderName: Option[String] = None,
	  /** The geographic taxonomy for this sku. */
		geoTaxonomy: Option[Schema.GeoTaxonomy] = None
	)
	
	case class Category(
	  /** The display name of the service this SKU belongs to. */
		serviceDisplayName: Option[String] = None,
	  /** The type of product the SKU refers to. Example: "Compute", "Storage", "Network", "ApplicationServices" etc. */
		resourceFamily: Option[String] = None,
	  /** A group classification for related SKUs. Example: "RAM", "GPU", "Prediction", "Ops", "GoogleEgress" etc. */
		resourceGroup: Option[String] = None,
	  /** Represents how the SKU is consumed. Example: "OnDemand", "Preemptible", "Commit1Mo", "Commit1Yr" etc. */
		usageType: Option[String] = None
	)
	
	case class PricingInfo(
	  /** The timestamp from which this pricing was effective within the requested time range. This is guaranteed to be greater than or equal to the start_time field in the request and less than the end_time field in the request. If a time range was not specified in the request this field will be equivalent to a time within the last 12 hours, indicating the latest pricing info. */
		effectiveTime: Option[String] = None,
	  /** An optional human readable summary of the pricing information, has a maximum length of 256 characters. */
		summary: Option[String] = None,
	  /** Expresses the pricing formula. See `PricingExpression` for an example. */
		pricingExpression: Option[Schema.PricingExpression] = None,
	  /** Aggregation Info. This can be left unspecified if the pricing expression doesn't require aggregation. */
		aggregationInfo: Option[Schema.AggregationInfo] = None,
	  /** Conversion rate used for currency conversion, from USD to the currency specified in the request. This includes any surcharge collected for billing in non USD currency. If a currency is not specified in the request this defaults to 1.0. Example: USD &#42; currency_conversion_rate = JPY */
		currencyConversionRate: Option[BigDecimal] = None
	)
	
	case class PricingExpression(
	  /** The short hand for unit of usage this pricing is specified in. Example: usage_unit of "GiBy" means that usage is specified in "Gibi Byte". */
		usageUnit: Option[String] = None,
	  /** The recommended quantity of units for displaying pricing info. When displaying pricing info it is recommended to display: (unit_price &#42; display_quantity) per display_quantity usage_unit. This field does not affect the pricing formula and is for display purposes only. Example: If the unit_price is "0.0001 USD", the usage_unit is "GB" and the display_quantity is "1000" then the recommended way of displaying the pricing info is "0.10 USD per 1000 GB" */
		displayQuantity: Option[BigDecimal] = None,
	  /** The list of tiered rates for this pricing. The total cost is computed by applying each of the tiered rates on usage. This repeated list is sorted by ascending order of start_usage_amount. */
		tieredRates: Option[List[Schema.TierRate]] = None,
	  /** The unit of usage in human readable form. Example: "gibi byte". */
		usageUnitDescription: Option[String] = None,
	  /** The base unit for the SKU which is the unit used in usage exports. Example: "By" */
		baseUnit: Option[String] = None,
	  /** The base unit in human readable form. Example: "byte". */
		baseUnitDescription: Option[String] = None,
	  /** Conversion factor for converting from price per usage_unit to price per base_unit, and start_usage_amount to start_usage_amount in base_unit. unit_price / base_unit_conversion_factor = price per base_unit. start_usage_amount &#42; base_unit_conversion_factor = start_usage_amount in base_unit. */
		baseUnitConversionFactor: Option[BigDecimal] = None
	)
	
	case class TierRate(
	  /** Usage is priced at this rate only after this amount. Example: start_usage_amount of 10 indicates that the usage will be priced at the unit_price after the first 10 usage_units. */
		startUsageAmount: Option[BigDecimal] = None,
	  /** The price per unit of usage. Example: unit_price of amount $10 indicates that each unit will cost $10. */
		unitPrice: Option[Schema.Money] = None
	)
	
	case class Money(
	  /** The three-letter currency code defined in ISO 4217. */
		currencyCode: Option[String] = None,
	  /** The whole units of the amount. For example if `currencyCode` is `"USD"`, then 1 unit is one US dollar. */
		units: Option[String] = None,
	  /** Number of nano (10^-9) units of the amount. The value must be between -999,999,999 and +999,999,999 inclusive. If `units` is positive, `nanos` must be positive or zero. If `units` is zero, `nanos` can be positive, zero, or negative. If `units` is negative, `nanos` must be negative or zero. For example $-1.75 is represented as `units`=-1 and `nanos`=-750,000,000. */
		nanos: Option[Int] = None
	)
	
	object AggregationInfo {
		enum AggregationLevelEnum extends Enum[AggregationLevelEnum] { case AGGREGATION_LEVEL_UNSPECIFIED, ACCOUNT, PROJECT }
		enum AggregationIntervalEnum extends Enum[AggregationIntervalEnum] { case AGGREGATION_INTERVAL_UNSPECIFIED, DAILY, MONTHLY }
	}
	case class AggregationInfo(
		aggregationLevel: Option[Schema.AggregationInfo.AggregationLevelEnum] = None,
		aggregationInterval: Option[Schema.AggregationInfo.AggregationIntervalEnum] = None,
	  /** The number of intervals to aggregate over. Example: If aggregation_level is "DAILY" and aggregation_count is 14, aggregation will be over 14 days. */
		aggregationCount: Option[Int] = None
	)
	
	object GeoTaxonomy {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, GLOBAL, REGIONAL, MULTI_REGIONAL }
	}
	case class GeoTaxonomy(
	  /** The type of Geo Taxonomy: GLOBAL, REGIONAL, or MULTI_REGIONAL. */
		`type`: Option[Schema.GeoTaxonomy.TypeEnum] = None,
	  /** The list of regions associated with a sku. Empty for Global skus, which are associated with all Google Cloud regions. */
		regions: Option[List[String]] = None
	)
}
