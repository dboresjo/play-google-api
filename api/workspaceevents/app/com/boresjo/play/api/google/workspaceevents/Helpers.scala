package com.boresjo.play.api.google.workspaceevents

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaSubscription: Conversion[List[Schema.Subscription], Option[List[Schema.Subscription]]] = (fun: List[Schema.Subscription]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaPayloadOptions: Conversion[Schema.PayloadOptions, Option[Schema.PayloadOptions]] = (fun: Schema.PayloadOptions) => Option(fun)
		given putSchemaSubscriptionStateEnum: Conversion[Schema.Subscription.StateEnum, Option[Schema.Subscription.StateEnum]] = (fun: Schema.Subscription.StateEnum) => Option(fun)
		given putSchemaNotificationEndpoint: Conversion[Schema.NotificationEndpoint, Option[Schema.NotificationEndpoint]] = (fun: Schema.NotificationEndpoint) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaSubscriptionSuspensionReasonEnum: Conversion[Schema.Subscription.SuspensionReasonEnum, Option[Schema.Subscription.SuspensionReasonEnum]] = (fun: Schema.Subscription.SuspensionReasonEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
