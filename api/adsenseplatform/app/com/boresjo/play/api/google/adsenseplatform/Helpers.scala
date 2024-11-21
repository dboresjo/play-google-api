package com.boresjo.play.api.google.adsenseplatform

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaAccountStateEnum: Conversion[Schema.Account.StateEnum, Option[Schema.Account.StateEnum]] = (fun: Schema.Account.StateEnum) => Option(fun)
		given putSchemaTimeZone: Conversion[Schema.TimeZone, Option[Schema.TimeZone]] = (fun: Schema.TimeZone) => Option(fun)
		given putListSchemaAccount: Conversion[List[Schema.Account], Option[List[Schema.Account]]] = (fun: List[Schema.Account]) => Option(fun)
		given putSchemaEventEventTypeEnum: Conversion[Schema.Event.EventTypeEnum, Option[Schema.Event.EventTypeEnum]] = (fun: Schema.Event.EventTypeEnum) => Option(fun)
		given putSchemaEventInfo: Conversion[Schema.EventInfo, Option[Schema.EventInfo]] = (fun: Schema.EventInfo) => Option(fun)
		given putSchemaAddress: Conversion[Schema.Address, Option[Schema.Address]] = (fun: Schema.Address) => Option(fun)
		given putSchemaSiteStateEnum: Conversion[Schema.Site.StateEnum, Option[Schema.Site.StateEnum]] = (fun: Schema.Site.StateEnum) => Option(fun)
		given putListSchemaSite: Conversion[List[Schema.Site], Option[List[Schema.Site]]] = (fun: List[Schema.Site]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
