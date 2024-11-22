package com.boresjo.play.api.google.marketingplatformadmin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaAnalyticsAccountLink: Conversion[List[Schema.AnalyticsAccountLink], Option[List[Schema.AnalyticsAccountLink]]] = (fun: List[Schema.AnalyticsAccountLink]) => Option(fun)
		given putSchemaSetPropertyServiceLevelRequestServiceLevelEnum: Conversion[Schema.SetPropertyServiceLevelRequest.ServiceLevelEnum, Option[Schema.SetPropertyServiceLevelRequest.ServiceLevelEnum]] = (fun: Schema.SetPropertyServiceLevelRequest.ServiceLevelEnum) => Option(fun)
		given putSchemaAnalyticsAccountLinkLinkVerificationStateEnum: Conversion[Schema.AnalyticsAccountLink.LinkVerificationStateEnum, Option[Schema.AnalyticsAccountLink.LinkVerificationStateEnum]] = (fun: Schema.AnalyticsAccountLink.LinkVerificationStateEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
