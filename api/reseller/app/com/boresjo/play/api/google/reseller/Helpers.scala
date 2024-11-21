package com.boresjo.play.api.google.reseller

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaAddress: Conversion[Schema.Address, Option[Schema.Address]] = (fun: Schema.Address) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaCustomerCustomerTypeEnum: Conversion[Schema.Customer.CustomerTypeEnum, Option[Schema.Customer.CustomerTypeEnum]] = (fun: Schema.Customer.CustomerTypeEnum) => Option(fun)
		given putSchemaPrimaryAdmin: Conversion[Schema.PrimaryAdmin, Option[Schema.PrimaryAdmin]] = (fun: Schema.PrimaryAdmin) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaSeats: Conversion[Schema.Seats, Option[Schema.Seats]] = (fun: Schema.Seats) => Option(fun)
		given putSchemaRenewalSettings: Conversion[Schema.RenewalSettings, Option[Schema.RenewalSettings]] = (fun: Schema.RenewalSettings) => Option(fun)
		given putSchemaSubscriptionPlanItem: Conversion[Schema.Subscription.PlanItem, Option[Schema.Subscription.PlanItem]] = (fun: Schema.Subscription.PlanItem) => Option(fun)
		given putSchemaSubscriptionTrialSettingsItem: Conversion[Schema.Subscription.TrialSettingsItem, Option[Schema.Subscription.TrialSettingsItem]] = (fun: Schema.Subscription.TrialSettingsItem) => Option(fun)
		given putSchemaSubscriptionTransferInfoItem: Conversion[Schema.Subscription.TransferInfoItem, Option[Schema.Subscription.TransferInfoItem]] = (fun: Schema.Subscription.TransferInfoItem) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaSubscription: Conversion[List[Schema.Subscription], Option[List[Schema.Subscription]]] = (fun: List[Schema.Subscription]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
