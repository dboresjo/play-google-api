package com.boresjo.play.api.google.adsenseplatform

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtAccount: Format[Schema.Account] = Json.format[Schema.Account]
	given fmtAccountStateEnum: Format[Schema.Account.StateEnum] = JsonEnumFormat[Schema.Account.StateEnum]
	given fmtTimeZone: Format[Schema.TimeZone] = Json.format[Schema.TimeZone]
	given fmtLookupAccountResponse: Format[Schema.LookupAccountResponse] = Json.format[Schema.LookupAccountResponse]
	given fmtListAccountsResponse: Format[Schema.ListAccountsResponse] = Json.format[Schema.ListAccountsResponse]
	given fmtCloseAccountRequest: Format[Schema.CloseAccountRequest] = Json.format[Schema.CloseAccountRequest]
	given fmtCloseAccountResponse: Format[Schema.CloseAccountResponse] = Json.format[Schema.CloseAccountResponse]
	given fmtEvent: Format[Schema.Event] = Json.format[Schema.Event]
	given fmtEventEventTypeEnum: Format[Schema.Event.EventTypeEnum] = JsonEnumFormat[Schema.Event.EventTypeEnum]
	given fmtEventInfo: Format[Schema.EventInfo] = Json.format[Schema.EventInfo]
	given fmtAddress: Format[Schema.Address] = Json.format[Schema.Address]
	given fmtSite: Format[Schema.Site] = Json.format[Schema.Site]
	given fmtSiteStateEnum: Format[Schema.Site.StateEnum] = JsonEnumFormat[Schema.Site.StateEnum]
	given fmtListSitesResponse: Format[Schema.ListSitesResponse] = Json.format[Schema.ListSitesResponse]
	given fmtRequestSiteReviewResponse: Format[Schema.RequestSiteReviewResponse] = Json.format[Schema.RequestSiteReviewResponse]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
}
