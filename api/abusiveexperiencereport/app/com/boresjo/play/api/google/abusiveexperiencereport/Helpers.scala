package com.boresjo.play.api.google.abusiveexperiencereport

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaSiteSummaryResponseAbusiveStatusEnum: Conversion[Schema.SiteSummaryResponse.AbusiveStatusEnum, Option[Schema.SiteSummaryResponse.AbusiveStatusEnum]] = (fun: Schema.SiteSummaryResponse.AbusiveStatusEnum) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaSiteSummaryResponseFilterStatusEnum: Conversion[Schema.SiteSummaryResponse.FilterStatusEnum, Option[Schema.SiteSummaryResponse.FilterStatusEnum]] = (fun: Schema.SiteSummaryResponse.FilterStatusEnum) => Option(fun)
		given putListSchemaSiteSummaryResponse: Conversion[List[Schema.SiteSummaryResponse], Option[List[Schema.SiteSummaryResponse]]] = (fun: List[Schema.SiteSummaryResponse]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
