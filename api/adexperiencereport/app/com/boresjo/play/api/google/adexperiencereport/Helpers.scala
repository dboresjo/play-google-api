package com.boresjo.play.api.google.adexperiencereport

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaPlatformSummary: Conversion[Schema.PlatformSummary, Option[Schema.PlatformSummary]] = (fun: Schema.PlatformSummary) => Option(fun)
		given putListSchemaPlatformSummaryRegionEnum: Conversion[List[Schema.PlatformSummary.RegionEnum], Option[List[Schema.PlatformSummary.RegionEnum]]] = (fun: List[Schema.PlatformSummary.RegionEnum]) => Option(fun)
		given putSchemaPlatformSummaryBetterAdsStatusEnum: Conversion[Schema.PlatformSummary.BetterAdsStatusEnum, Option[Schema.PlatformSummary.BetterAdsStatusEnum]] = (fun: Schema.PlatformSummary.BetterAdsStatusEnum) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaPlatformSummaryFilterStatusEnum: Conversion[Schema.PlatformSummary.FilterStatusEnum, Option[Schema.PlatformSummary.FilterStatusEnum]] = (fun: Schema.PlatformSummary.FilterStatusEnum) => Option(fun)
		given putListSchemaSiteSummaryResponse: Conversion[List[Schema.SiteSummaryResponse], Option[List[Schema.SiteSummaryResponse]]] = (fun: List[Schema.SiteSummaryResponse]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
