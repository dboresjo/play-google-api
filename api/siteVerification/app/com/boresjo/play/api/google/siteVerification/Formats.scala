package com.boresjo.play.api.google.siteVerification

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtSiteVerificationWebResourceGettokenRequest: Format[Schema.SiteVerificationWebResourceGettokenRequest] = Json.format[Schema.SiteVerificationWebResourceGettokenRequest]
	given fmtSiteVerificationWebResourceGettokenRequestSiteItem: Format[Schema.SiteVerificationWebResourceGettokenRequest.SiteItem] = Json.format[Schema.SiteVerificationWebResourceGettokenRequest.SiteItem]
	given fmtSiteVerificationWebResourceGettokenResponse: Format[Schema.SiteVerificationWebResourceGettokenResponse] = Json.format[Schema.SiteVerificationWebResourceGettokenResponse]
	given fmtSiteVerificationWebResourceListResponse: Format[Schema.SiteVerificationWebResourceListResponse] = Json.format[Schema.SiteVerificationWebResourceListResponse]
	given fmtSiteVerificationWebResourceResource: Format[Schema.SiteVerificationWebResourceResource] = Json.format[Schema.SiteVerificationWebResourceResource]
	given fmtSiteVerificationWebResourceResourceSiteItem: Format[Schema.SiteVerificationWebResourceResource.SiteItem] = Json.format[Schema.SiteVerificationWebResourceResource.SiteItem]
}
