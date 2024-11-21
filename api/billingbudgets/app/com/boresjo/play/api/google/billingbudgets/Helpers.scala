package com.boresjo.play.api.google.billingbudgets

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaGoogleCloudBillingBudgetsV1Budget: Conversion[List[Schema.GoogleCloudBillingBudgetsV1Budget], Option[List[Schema.GoogleCloudBillingBudgetsV1Budget]]] = (fun: List[Schema.GoogleCloudBillingBudgetsV1Budget]) => Option(fun)
		given putSchemaGoogleTypeDate: Conversion[Schema.GoogleTypeDate, Option[Schema.GoogleTypeDate]] = (fun: Schema.GoogleTypeDate) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaGoogleCloudBillingBudgetsV1FilterCreditTypesTreatmentEnum: Conversion[Schema.GoogleCloudBillingBudgetsV1Filter.CreditTypesTreatmentEnum, Option[Schema.GoogleCloudBillingBudgetsV1Filter.CreditTypesTreatmentEnum]] = (fun: Schema.GoogleCloudBillingBudgetsV1Filter.CreditTypesTreatmentEnum) => Option(fun)
		given putMapStringListJsValue: Conversion[Map[String, List[JsValue]], Option[Map[String, List[JsValue]]]] = (fun: Map[String, List[JsValue]]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaGoogleCloudBillingBudgetsV1CustomPeriod: Conversion[Schema.GoogleCloudBillingBudgetsV1CustomPeriod, Option[Schema.GoogleCloudBillingBudgetsV1CustomPeriod]] = (fun: Schema.GoogleCloudBillingBudgetsV1CustomPeriod) => Option(fun)
		given putSchemaGoogleCloudBillingBudgetsV1FilterCalendarPeriodEnum: Conversion[Schema.GoogleCloudBillingBudgetsV1Filter.CalendarPeriodEnum, Option[Schema.GoogleCloudBillingBudgetsV1Filter.CalendarPeriodEnum]] = (fun: Schema.GoogleCloudBillingBudgetsV1Filter.CalendarPeriodEnum) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaGoogleCloudBillingBudgetsV1ThresholdRuleSpendBasisEnum: Conversion[Schema.GoogleCloudBillingBudgetsV1ThresholdRule.SpendBasisEnum, Option[Schema.GoogleCloudBillingBudgetsV1ThresholdRule.SpendBasisEnum]] = (fun: Schema.GoogleCloudBillingBudgetsV1ThresholdRule.SpendBasisEnum) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putSchemaGoogleCloudBillingBudgetsV1BudgetOwnershipScopeEnum: Conversion[Schema.GoogleCloudBillingBudgetsV1Budget.OwnershipScopeEnum, Option[Schema.GoogleCloudBillingBudgetsV1Budget.OwnershipScopeEnum]] = (fun: Schema.GoogleCloudBillingBudgetsV1Budget.OwnershipScopeEnum) => Option(fun)
		given putSchemaGoogleCloudBillingBudgetsV1Filter: Conversion[Schema.GoogleCloudBillingBudgetsV1Filter, Option[Schema.GoogleCloudBillingBudgetsV1Filter]] = (fun: Schema.GoogleCloudBillingBudgetsV1Filter) => Option(fun)
		given putSchemaGoogleCloudBillingBudgetsV1BudgetAmount: Conversion[Schema.GoogleCloudBillingBudgetsV1BudgetAmount, Option[Schema.GoogleCloudBillingBudgetsV1BudgetAmount]] = (fun: Schema.GoogleCloudBillingBudgetsV1BudgetAmount) => Option(fun)
		given putListSchemaGoogleCloudBillingBudgetsV1ThresholdRule: Conversion[List[Schema.GoogleCloudBillingBudgetsV1ThresholdRule], Option[List[Schema.GoogleCloudBillingBudgetsV1ThresholdRule]]] = (fun: List[Schema.GoogleCloudBillingBudgetsV1ThresholdRule]) => Option(fun)
		given putSchemaGoogleCloudBillingBudgetsV1NotificationsRule: Conversion[Schema.GoogleCloudBillingBudgetsV1NotificationsRule, Option[Schema.GoogleCloudBillingBudgetsV1NotificationsRule]] = (fun: Schema.GoogleCloudBillingBudgetsV1NotificationsRule) => Option(fun)
		given putSchemaGoogleCloudBillingBudgetsV1LastPeriodAmount: Conversion[Schema.GoogleCloudBillingBudgetsV1LastPeriodAmount, Option[Schema.GoogleCloudBillingBudgetsV1LastPeriodAmount]] = (fun: Schema.GoogleCloudBillingBudgetsV1LastPeriodAmount) => Option(fun)
		given putSchemaGoogleTypeMoney: Conversion[Schema.GoogleTypeMoney, Option[Schema.GoogleTypeMoney]] = (fun: Schema.GoogleTypeMoney) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
