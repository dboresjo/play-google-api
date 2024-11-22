package com.boresjo.play.api.google.reseller

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtCustomer: Format[Schema.Customer] = Json.format[Schema.Customer]
	given fmtAddress: Format[Schema.Address] = Json.format[Schema.Address]
	given fmtCustomerCustomerTypeEnum: Format[Schema.Customer.CustomerTypeEnum] = JsonEnumFormat[Schema.Customer.CustomerTypeEnum]
	given fmtPrimaryAdmin: Format[Schema.PrimaryAdmin] = Json.format[Schema.PrimaryAdmin]
	given fmtResellernotifyGetwatchdetailsResponse: Format[Schema.ResellernotifyGetwatchdetailsResponse] = Json.format[Schema.ResellernotifyGetwatchdetailsResponse]
	given fmtResellernotifyResource: Format[Schema.ResellernotifyResource] = Json.format[Schema.ResellernotifyResource]
	given fmtSubscription: Format[Schema.Subscription] = Json.format[Schema.Subscription]
	given fmtSeats: Format[Schema.Seats] = Json.format[Schema.Seats]
	given fmtRenewalSettings: Format[Schema.RenewalSettings] = Json.format[Schema.RenewalSettings]
	given fmtSubscriptionPlanItem: Format[Schema.Subscription.PlanItem] = Json.format[Schema.Subscription.PlanItem]
	given fmtSubscriptionPlanItemCommitmentIntervalItem: Format[Schema.Subscription.PlanItem.CommitmentIntervalItem] = Json.format[Schema.Subscription.PlanItem.CommitmentIntervalItem]
	given fmtSubscriptionTrialSettingsItem: Format[Schema.Subscription.TrialSettingsItem] = Json.format[Schema.Subscription.TrialSettingsItem]
	given fmtSubscriptionTransferInfoItem: Format[Schema.Subscription.TransferInfoItem] = Json.format[Schema.Subscription.TransferInfoItem]
	given fmtChangePlanRequest: Format[Schema.ChangePlanRequest] = Json.format[Schema.ChangePlanRequest]
	given fmtSubscriptions: Format[Schema.Subscriptions] = Json.format[Schema.Subscriptions]
}
