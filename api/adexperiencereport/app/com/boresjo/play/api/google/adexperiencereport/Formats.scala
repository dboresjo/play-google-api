package com.boresjo.play.api.google.adexperiencereport

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtSiteSummaryResponse: Format[Schema.SiteSummaryResponse] = Json.format[Schema.SiteSummaryResponse]
	given fmtPlatformSummary: Format[Schema.PlatformSummary] = Json.format[Schema.PlatformSummary]
	given fmtPlatformSummaryRegionEnum: Format[Schema.PlatformSummary.RegionEnum] = JsonEnumFormat[Schema.PlatformSummary.RegionEnum]
	given fmtPlatformSummaryBetterAdsStatusEnum: Format[Schema.PlatformSummary.BetterAdsStatusEnum] = JsonEnumFormat[Schema.PlatformSummary.BetterAdsStatusEnum]
	given fmtPlatformSummaryFilterStatusEnum: Format[Schema.PlatformSummary.FilterStatusEnum] = JsonEnumFormat[Schema.PlatformSummary.FilterStatusEnum]
	given fmtViolatingSitesResponse: Format[Schema.ViolatingSitesResponse] = Json.format[Schema.ViolatingSitesResponse]
}
