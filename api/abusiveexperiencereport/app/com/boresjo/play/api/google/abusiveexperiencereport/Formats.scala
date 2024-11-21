package com.boresjo.play.api.google.abusiveexperiencereport

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtSiteSummaryResponse: Format[Schema.SiteSummaryResponse] = Json.format[Schema.SiteSummaryResponse]
	given fmtSiteSummaryResponseAbusiveStatusEnum: Format[Schema.SiteSummaryResponse.AbusiveStatusEnum] = JsonEnumFormat[Schema.SiteSummaryResponse.AbusiveStatusEnum]
	given fmtSiteSummaryResponseFilterStatusEnum: Format[Schema.SiteSummaryResponse.FilterStatusEnum] = JsonEnumFormat[Schema.SiteSummaryResponse.FilterStatusEnum]
	given fmtViolatingSitesResponse: Format[Schema.ViolatingSitesResponse] = Json.format[Schema.ViolatingSitesResponse]
}
