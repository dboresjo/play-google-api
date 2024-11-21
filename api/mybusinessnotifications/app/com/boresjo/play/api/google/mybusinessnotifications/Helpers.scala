package com.boresjo.play.api.google.mybusinessnotifications

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaNotificationSettingNotificationTypesEnum: Conversion[List[Schema.NotificationSetting.NotificationTypesEnum], Option[List[Schema.NotificationSetting.NotificationTypesEnum]]] = (fun: List[Schema.NotificationSetting.NotificationTypesEnum]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
