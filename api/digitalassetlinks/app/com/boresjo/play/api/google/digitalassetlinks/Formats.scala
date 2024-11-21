package com.boresjo.play.api.google.digitalassetlinks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtCheckResponse: Format[Schema.CheckResponse] = Json.format[Schema.CheckResponse]
	given fmtCheckResponseErrorCodeEnum: Format[Schema.CheckResponse.ErrorCodeEnum] = JsonEnumFormat[Schema.CheckResponse.ErrorCodeEnum]
	given fmtListResponse: Format[Schema.ListResponse] = Json.format[Schema.ListResponse]
	given fmtStatement: Format[Schema.Statement] = Json.format[Schema.Statement]
	given fmtListResponseErrorCodeEnum: Format[Schema.ListResponse.ErrorCodeEnum] = JsonEnumFormat[Schema.ListResponse.ErrorCodeEnum]
	given fmtAsset: Format[Schema.Asset] = Json.format[Schema.Asset]
	given fmtWebAsset: Format[Schema.WebAsset] = Json.format[Schema.WebAsset]
	given fmtAndroidAppAsset: Format[Schema.AndroidAppAsset] = Json.format[Schema.AndroidAppAsset]
	given fmtCertificateInfo: Format[Schema.CertificateInfo] = Json.format[Schema.CertificateInfo]
}
