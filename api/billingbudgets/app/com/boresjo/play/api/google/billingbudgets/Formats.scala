package com.boresjo.play.api.google.billingbudgets

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGoogleCloudBillingBudgetsV1ListBudgetsResponse: Format[Schema.GoogleCloudBillingBudgetsV1ListBudgetsResponse] = Json.format[Schema.GoogleCloudBillingBudgetsV1ListBudgetsResponse]
	given fmtGoogleCloudBillingBudgetsV1Budget: Format[Schema.GoogleCloudBillingBudgetsV1Budget] = Json.format[Schema.GoogleCloudBillingBudgetsV1Budget]
	given fmtGoogleCloudBillingBudgetsV1CustomPeriod: Format[Schema.GoogleCloudBillingBudgetsV1CustomPeriod] = Json.format[Schema.GoogleCloudBillingBudgetsV1CustomPeriod]
	given fmtGoogleTypeDate: Format[Schema.GoogleTypeDate] = Json.format[Schema.GoogleTypeDate]
	given fmtGoogleCloudBillingBudgetsV1Filter: Format[Schema.GoogleCloudBillingBudgetsV1Filter] = Json.format[Schema.GoogleCloudBillingBudgetsV1Filter]
	given fmtGoogleCloudBillingBudgetsV1FilterCreditTypesTreatmentEnum: Format[Schema.GoogleCloudBillingBudgetsV1Filter.CreditTypesTreatmentEnum] = JsonEnumFormat[Schema.GoogleCloudBillingBudgetsV1Filter.CreditTypesTreatmentEnum]
	given fmtGoogleCloudBillingBudgetsV1FilterCalendarPeriodEnum: Format[Schema.GoogleCloudBillingBudgetsV1Filter.CalendarPeriodEnum] = JsonEnumFormat[Schema.GoogleCloudBillingBudgetsV1Filter.CalendarPeriodEnum]
	given fmtGoogleCloudBillingBudgetsV1LastPeriodAmount: Format[Schema.GoogleCloudBillingBudgetsV1LastPeriodAmount] = Json.format[Schema.GoogleCloudBillingBudgetsV1LastPeriodAmount]
	given fmtGoogleProtobufEmpty: Format[Schema.GoogleProtobufEmpty] = Json.format[Schema.GoogleProtobufEmpty]
	given fmtGoogleTypeMoney: Format[Schema.GoogleTypeMoney] = Json.format[Schema.GoogleTypeMoney]
	given fmtGoogleCloudBillingBudgetsV1NotificationsRule: Format[Schema.GoogleCloudBillingBudgetsV1NotificationsRule] = Json.format[Schema.GoogleCloudBillingBudgetsV1NotificationsRule]
	given fmtGoogleCloudBillingBudgetsV1ThresholdRule: Format[Schema.GoogleCloudBillingBudgetsV1ThresholdRule] = Json.format[Schema.GoogleCloudBillingBudgetsV1ThresholdRule]
	given fmtGoogleCloudBillingBudgetsV1ThresholdRuleSpendBasisEnum: Format[Schema.GoogleCloudBillingBudgetsV1ThresholdRule.SpendBasisEnum] = JsonEnumFormat[Schema.GoogleCloudBillingBudgetsV1ThresholdRule.SpendBasisEnum]
	given fmtGoogleCloudBillingBudgetsV1BudgetOwnershipScopeEnum: Format[Schema.GoogleCloudBillingBudgetsV1Budget.OwnershipScopeEnum] = JsonEnumFormat[Schema.GoogleCloudBillingBudgetsV1Budget.OwnershipScopeEnum]
	given fmtGoogleCloudBillingBudgetsV1BudgetAmount: Format[Schema.GoogleCloudBillingBudgetsV1BudgetAmount] = Json.format[Schema.GoogleCloudBillingBudgetsV1BudgetAmount]
}
