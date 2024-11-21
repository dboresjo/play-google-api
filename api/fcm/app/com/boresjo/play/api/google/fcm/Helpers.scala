package com.boresjo.play.api.google.fcm

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaMessage: Conversion[Schema.Message, Option[Schema.Message]] = (fun: Schema.Message) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaNotification: Conversion[Schema.Notification, Option[Schema.Notification]] = (fun: Schema.Notification) => Option(fun)
		given putSchemaAndroidConfig: Conversion[Schema.AndroidConfig, Option[Schema.AndroidConfig]] = (fun: Schema.AndroidConfig) => Option(fun)
		given putSchemaWebpushConfig: Conversion[Schema.WebpushConfig, Option[Schema.WebpushConfig]] = (fun: Schema.WebpushConfig) => Option(fun)
		given putSchemaApnsConfig: Conversion[Schema.ApnsConfig, Option[Schema.ApnsConfig]] = (fun: Schema.ApnsConfig) => Option(fun)
		given putSchemaFcmOptions: Conversion[Schema.FcmOptions, Option[Schema.FcmOptions]] = (fun: Schema.FcmOptions) => Option(fun)
		given putSchemaAndroidConfigPriorityEnum: Conversion[Schema.AndroidConfig.PriorityEnum, Option[Schema.AndroidConfig.PriorityEnum]] = (fun: Schema.AndroidConfig.PriorityEnum) => Option(fun)
		given putSchemaAndroidNotification: Conversion[Schema.AndroidNotification, Option[Schema.AndroidNotification]] = (fun: Schema.AndroidNotification) => Option(fun)
		given putSchemaAndroidFcmOptions: Conversion[Schema.AndroidFcmOptions, Option[Schema.AndroidFcmOptions]] = (fun: Schema.AndroidFcmOptions) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaAndroidNotificationNotificationPriorityEnum: Conversion[Schema.AndroidNotification.NotificationPriorityEnum, Option[Schema.AndroidNotification.NotificationPriorityEnum]] = (fun: Schema.AndroidNotification.NotificationPriorityEnum) => Option(fun)
		given putSchemaAndroidNotificationVisibilityEnum: Conversion[Schema.AndroidNotification.VisibilityEnum, Option[Schema.AndroidNotification.VisibilityEnum]] = (fun: Schema.AndroidNotification.VisibilityEnum) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaLightSettings: Conversion[Schema.LightSettings, Option[Schema.LightSettings]] = (fun: Schema.LightSettings) => Option(fun)
		given putSchemaAndroidNotificationProxyEnum: Conversion[Schema.AndroidNotification.ProxyEnum, Option[Schema.AndroidNotification.ProxyEnum]] = (fun: Schema.AndroidNotification.ProxyEnum) => Option(fun)
		given putSchemaColor: Conversion[Schema.Color, Option[Schema.Color]] = (fun: Schema.Color) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaWebpushFcmOptions: Conversion[Schema.WebpushFcmOptions, Option[Schema.WebpushFcmOptions]] = (fun: Schema.WebpushFcmOptions) => Option(fun)
		given putSchemaApnsFcmOptions: Conversion[Schema.ApnsFcmOptions, Option[Schema.ApnsFcmOptions]] = (fun: Schema.ApnsFcmOptions) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
