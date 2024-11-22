package com.boresjo.play.api.google.marketingplatformadmin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListAnalyticsAccountLinksResponse: Format[Schema.ListAnalyticsAccountLinksResponse] = Json.format[Schema.ListAnalyticsAccountLinksResponse]
	given fmtAnalyticsAccountLink: Format[Schema.AnalyticsAccountLink] = Json.format[Schema.AnalyticsAccountLink]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtSetPropertyServiceLevelRequest: Format[Schema.SetPropertyServiceLevelRequest] = Json.format[Schema.SetPropertyServiceLevelRequest]
	given fmtSetPropertyServiceLevelRequestServiceLevelEnum: Format[Schema.SetPropertyServiceLevelRequest.ServiceLevelEnum] = JsonEnumFormat[Schema.SetPropertyServiceLevelRequest.ServiceLevelEnum]
	given fmtOrganization: Format[Schema.Organization] = Json.format[Schema.Organization]
	given fmtSetPropertyServiceLevelResponse: Format[Schema.SetPropertyServiceLevelResponse] = Json.format[Schema.SetPropertyServiceLevelResponse]
	given fmtAnalyticsAccountLinkLinkVerificationStateEnum: Format[Schema.AnalyticsAccountLink.LinkVerificationStateEnum] = JsonEnumFormat[Schema.AnalyticsAccountLink.LinkVerificationStateEnum]
}
