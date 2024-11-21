package com.boresjo.play.api.google.workspaceevents

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtReactivateSubscriptionRequest: Format[Schema.ReactivateSubscriptionRequest] = Json.format[Schema.ReactivateSubscriptionRequest]
	given fmtListSubscriptionsResponse: Format[Schema.ListSubscriptionsResponse] = Json.format[Schema.ListSubscriptionsResponse]
	given fmtSubscription: Format[Schema.Subscription] = Json.format[Schema.Subscription]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtPayloadOptions: Format[Schema.PayloadOptions] = Json.format[Schema.PayloadOptions]
	given fmtNotificationEndpoint: Format[Schema.NotificationEndpoint] = Json.format[Schema.NotificationEndpoint]
	given fmtSubscriptionStateEnum: Format[Schema.Subscription.StateEnum] = JsonEnumFormat[Schema.Subscription.StateEnum]
	given fmtSubscriptionSuspensionReasonEnum: Format[Schema.Subscription.SuspensionReasonEnum] = JsonEnumFormat[Schema.Subscription.SuspensionReasonEnum]
}
