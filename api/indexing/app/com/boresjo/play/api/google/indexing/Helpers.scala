package com.boresjo.play.api.google.indexing

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaUrlNotificationTypeEnum: Conversion[Schema.UrlNotification.TypeEnum, Option[Schema.UrlNotification.TypeEnum]] = (fun: Schema.UrlNotification.TypeEnum) => Option(fun)
		given putSchemaUrlNotificationMetadata: Conversion[Schema.UrlNotificationMetadata, Option[Schema.UrlNotificationMetadata]] = (fun: Schema.UrlNotificationMetadata) => Option(fun)
		given putSchemaUrlNotification: Conversion[Schema.UrlNotification, Option[Schema.UrlNotification]] = (fun: Schema.UrlNotification) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
