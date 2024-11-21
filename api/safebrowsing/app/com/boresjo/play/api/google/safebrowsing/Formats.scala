package com.boresjo.play.api.google.safebrowsing

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGoogleSecuritySafebrowsingV5FullHashFullHashDetail: Format[Schema.GoogleSecuritySafebrowsingV5FullHashFullHashDetail] = Json.format[Schema.GoogleSecuritySafebrowsingV5FullHashFullHashDetail]
	given fmtGoogleSecuritySafebrowsingV5FullHashFullHashDetailThreatTypeEnum: Format[Schema.GoogleSecuritySafebrowsingV5FullHashFullHashDetail.ThreatTypeEnum] = JsonEnumFormat[Schema.GoogleSecuritySafebrowsingV5FullHashFullHashDetail.ThreatTypeEnum]
	given fmtGoogleSecuritySafebrowsingV5FullHashFullHashDetailAttributesEnum: Format[Schema.GoogleSecuritySafebrowsingV5FullHashFullHashDetail.AttributesEnum] = JsonEnumFormat[Schema.GoogleSecuritySafebrowsingV5FullHashFullHashDetail.AttributesEnum]
	given fmtGoogleSecuritySafebrowsingV5SearchHashesResponse: Format[Schema.GoogleSecuritySafebrowsingV5SearchHashesResponse] = Json.format[Schema.GoogleSecuritySafebrowsingV5SearchHashesResponse]
	given fmtGoogleSecuritySafebrowsingV5FullHash: Format[Schema.GoogleSecuritySafebrowsingV5FullHash] = Json.format[Schema.GoogleSecuritySafebrowsingV5FullHash]
}
