package com.boresjo.play.api.google.billingbudgets

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleCloudBillingBudgetsV1ListBudgetsResponse(
	  /** If not empty, indicates that there may be more budgets that match the request; this value should be passed in a new `ListBudgetsRequest`. */
		nextPageToken: Option[String] = None,
	  /** List of the budgets owned by the requested billing account. */
		budgets: Option[List[Schema.GoogleCloudBillingBudgetsV1Budget]] = None
	)
	
	case class GoogleCloudBillingBudgetsV1CustomPeriod(
	  /** Required. The start date must be after January 1, 2017. */
		startDate: Option[Schema.GoogleTypeDate] = None,
	  /** Optional. The end date of the time period. Budgets with elapsed end date won't be processed. If unset, specifies to track all usage incurred since the start_date. */
		endDate: Option[Schema.GoogleTypeDate] = None
	)
	
	case class GoogleTypeDate(
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None,
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None
	)
	
	object GoogleCloudBillingBudgetsV1Filter {
		enum CreditTypesTreatmentEnum extends Enum[CreditTypesTreatmentEnum] { case CREDIT_TYPES_TREATMENT_UNSPECIFIED, INCLUDE_ALL_CREDITS, EXCLUDE_ALL_CREDITS, INCLUDE_SPECIFIED_CREDITS }
		enum CalendarPeriodEnum extends Enum[CalendarPeriodEnum] { case CALENDAR_PERIOD_UNSPECIFIED, MONTH, QUARTER, YEAR }
	}
	case class GoogleCloudBillingBudgetsV1Filter(
	  /** Optional. If not set, default behavior is `INCLUDE_ALL_CREDITS`. */
		creditTypesTreatment: Option[Schema.GoogleCloudBillingBudgetsV1Filter.CreditTypesTreatmentEnum] = None,
	  /** Optional. A single label and value pair specifying that usage from only this set of labeled resources should be included in the budget. If omitted, the report includes all labeled and unlabeled usage. An object containing a single `"key": value` pair. Example: `{ "name": "wrench" }`. _Currently, multiple entries or multiple values per entry are not allowed._ */
		labels: Option[Map[String, List[JsValue]]] = None,
	  /** Optional. If Filter.credit_types_treatment is INCLUDE_SPECIFIED_CREDITS, this is a list of credit types to be subtracted from gross cost to determine the spend for threshold calculations. See [a list of acceptable credit type values](https://cloud.google.com/billing/docs/how-to/export-data-bigquery-tables#credits-type). If Filter.credit_types_treatment is &#42;&#42;not&#42;&#42; INCLUDE_SPECIFIED_CREDITS, this field must be empty. */
		creditTypes: Option[List[String]] = None,
	  /** Optional. Specifies to track usage from any start date (required) to any end date (optional). This time period is static, it does not recur. */
		customPeriod: Option[Schema.GoogleCloudBillingBudgetsV1CustomPeriod] = None,
	  /** Optional. A set of services of the form `services/{service_id}`, specifying that usage from only this set of services should be included in the budget. If omitted, the report includes usage for all the services. The service names are available through the Catalog API: https://cloud.google.com/billing/v1/how-tos/catalog-api. */
		services: Option[List[String]] = None,
	  /** Optional. A set of projects of the form `projects/{project}`, specifying that usage from only this set of projects should be included in the budget. If omitted, the report includes all usage for the billing account, regardless of which project the usage occurred on. */
		projects: Option[List[String]] = None,
	  /** Optional. Specifies to track usage for recurring calendar period. For example, assume that CalendarPeriod.QUARTER is set. The budget tracks usage from April 1 to June 30, when the current calendar month is April, May, June. After that, it tracks usage from July 1 to September 30 when the current calendar month is July, August, September, so on. */
		calendarPeriod: Option[Schema.GoogleCloudBillingBudgetsV1Filter.CalendarPeriodEnum] = None,
	  /** Optional. A set of subaccounts of the form `billingAccounts/{account_id}`, specifying that usage from only this set of subaccounts should be included in the budget. If a subaccount is set to the name of the parent account, usage from the parent account is included. If the field is omitted, the report includes usage from the parent account and all subaccounts, if they exist. */
		subaccounts: Option[List[String]] = None,
	  /** Optional. A set of folder and organization names of the form `folders/{folderId}` or `organizations/{organizationId}`, specifying that usage from only this set of folders and organizations should be included in the budget. If omitted, the budget includes all usage that the billing account pays for. If the folder or organization contains projects that are paid for by a different Cloud Billing account, the budget &#42;doesn't&#42; apply to those projects. */
		resourceAncestors: Option[List[String]] = None
	)
	
	case class GoogleCloudBillingBudgetsV1LastPeriodAmount(
	
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleTypeMoney(
	  /** The whole units of the amount. For example if `currencyCode` is `"USD"`, then 1 unit is one US dollar. */
		units: Option[String] = None,
	  /** Number of nano (10^-9) units of the amount. The value must be between -999,999,999 and +999,999,999 inclusive. If `units` is positive, `nanos` must be positive or zero. If `units` is zero, `nanos` can be positive, zero, or negative. If `units` is negative, `nanos` must be negative or zero. For example $-1.75 is represented as `units`=-1 and `nanos`=-750,000,000. */
		nanos: Option[Int] = None,
	  /** The three-letter currency code defined in ISO 4217. */
		currencyCode: Option[String] = None
	)
	
	case class GoogleCloudBillingBudgetsV1NotificationsRule(
	  /** Optional. When set to true, disables default notifications sent when a threshold is exceeded. Default notifications are sent to those with Billing Account Administrator and Billing Account User IAM roles for the target account. */
		disableDefaultIamRecipients: Option[Boolean] = None,
	  /** Optional. When set to true, and when the budget has a single project configured, notifications will be sent to project level recipients of that project. This field will be ignored if the budget has multiple or no project configured. Currently, project level recipients are the users with `Owner` role on a cloud project. */
		enableProjectLevelRecipients: Option[Boolean] = None,
	  /** Optional. Email targets to send notifications to when a threshold is exceeded. This is in addition to the `DefaultIamRecipients` who receive alert emails based on their billing account IAM role. The value is the full REST resource name of a Cloud Monitoring email notification channel with the form `projects/{project_id}/notificationChannels/{channel_id}`. A maximum of 5 email notifications are allowed. To customize budget alert email recipients with monitoring notification channels, you _must create the monitoring notification channels before you link them to a budget_. For guidance on setting up notification channels to use with budgets, see [Customize budget alert email recipients](https://cloud.google.com/billing/docs/how-to/budgets-notification-recipients). For Cloud Billing budget alerts, you _must use email notification channels_. The other types of notification channels are _not_ supported, such as Slack, SMS, or PagerDuty. If you want to [send budget notifications to Slack](https://cloud.google.com/billing/docs/how-to/notify#send_notifications_to_slack), use a pubsubTopic and configure [programmatic notifications](https://cloud.google.com/billing/docs/how-to/budgets-programmatic-notifications). */
		monitoringNotificationChannels: Option[List[String]] = None,
	  /** Optional. The name of the Pub/Sub topic where budget-related messages are published, in the form `projects/{project_id}/topics/{topic_id}`. Updates are sent to the topic at regular intervals; the timing of the updates is not dependent on the [threshold rules](#thresholdrule) you've set. Note that if you want your [Pub/Sub JSON object](https://cloud.google.com/billing/docs/how-to/budgets-programmatic-notifications#notification_format) to contain data for `alertThresholdExceeded`, you need at least one [alert threshold rule](#thresholdrule). When you set threshold rules, you must also enable at least one of the email notification options, either using the default IAM recipients or Cloud Monitoring email notification channels. To use Pub/Sub topics with budgets, you must do the following: 1. Create the Pub/Sub topic before connecting it to your budget. For guidance, see [Manage programmatic budget alert notifications](https://cloud.google.com/billing/docs/how-to/budgets-programmatic-notifications). 2. Grant the API caller the `pubsub.topics.setIamPolicy` permission on the Pub/Sub topic. If not set, the API call fails with PERMISSION_DENIED. For additional details on Pub/Sub roles and permissions, see [Permissions required for this task](https://cloud.google.com/billing/docs/how-to/budgets-programmatic-notifications#permissions_required_for_this_task). */
		pubsubTopic: Option[String] = None,
	  /** Optional. Required when NotificationsRule.pubsub_topic is set. The schema version of the notification sent to NotificationsRule.pubsub_topic. Only "1.0" is accepted. It represents the JSON schema as defined in https://cloud.google.com/billing/docs/how-to/budgets-programmatic-notifications#notification_format. */
		schemaVersion: Option[String] = None
	)
	
	object GoogleCloudBillingBudgetsV1ThresholdRule {
		enum SpendBasisEnum extends Enum[SpendBasisEnum] { case BASIS_UNSPECIFIED, CURRENT_SPEND, FORECASTED_SPEND }
	}
	case class GoogleCloudBillingBudgetsV1ThresholdRule(
	  /** Optional. The type of basis used to determine if spend has passed the threshold. Behavior defaults to CURRENT_SPEND if not set. */
		spendBasis: Option[Schema.GoogleCloudBillingBudgetsV1ThresholdRule.SpendBasisEnum] = None,
	  /** Required. Send an alert when this threshold is exceeded. This is a 1.0-based percentage, so 0.5 = 50%. Validation: non-negative number. */
		thresholdPercent: Option[BigDecimal] = None
	)
	
	object GoogleCloudBillingBudgetsV1Budget {
		enum OwnershipScopeEnum extends Enum[OwnershipScopeEnum] { case OWNERSHIP_SCOPE_UNSPECIFIED, ALL_USERS, BILLING_ACCOUNT }
	}
	case class GoogleCloudBillingBudgetsV1Budget(
		ownershipScope: Option[Schema.GoogleCloudBillingBudgetsV1Budget.OwnershipScopeEnum] = None,
	  /** Optional. Filters that define which resources are used to compute the actual spend against the budget amount, such as projects, services, and the budget's time period, as well as other filters. */
		budgetFilter: Option[Schema.GoogleCloudBillingBudgetsV1Filter] = None,
	  /** Optional. Etag to validate that the object is unchanged for a read-modify-write operation. An empty etag causes an update to overwrite other changes. */
		etag: Option[String] = None,
	  /** Required. Budgeted amount. */
		amount: Option[Schema.GoogleCloudBillingBudgetsV1BudgetAmount] = None,
	  /** Optional. Rules that trigger alerts (notifications of thresholds being crossed) when spend exceeds the specified percentages of the budget. Optional for `pubsubTopic` notifications. Required if using email notifications. */
		thresholdRules: Option[List[Schema.GoogleCloudBillingBudgetsV1ThresholdRule]] = None,
	  /** Output only. Resource name of the budget. The resource name implies the scope of a budget. Values are of the form `billingAccounts/{billingAccountId}/budgets/{budgetId}`. */
		name: Option[String] = None,
	  /** User data for display name in UI. The name must be less than or equal to 60 characters. */
		displayName: Option[String] = None,
	  /** Optional. Rules to apply to notifications sent based on budget spend and thresholds. */
		notificationsRule: Option[Schema.GoogleCloudBillingBudgetsV1NotificationsRule] = None
	)
	
	case class GoogleCloudBillingBudgetsV1BudgetAmount(
	  /** Use the last period's actual spend as the budget for the present period. LastPeriodAmount can only be set when the budget's time period is a Filter.calendar_period. It cannot be set in combination with Filter.custom_period. */
		lastPeriodAmount: Option[Schema.GoogleCloudBillingBudgetsV1LastPeriodAmount] = None,
	  /** A specified amount to use as the budget. `currency_code` is optional. If specified when creating a budget, it must match the currency of the billing account. If specified when updating a budget, it must match the currency_code of the existing budget. The `currency_code` is provided on output. */
		specifiedAmount: Option[Schema.GoogleTypeMoney] = None
	)
}
