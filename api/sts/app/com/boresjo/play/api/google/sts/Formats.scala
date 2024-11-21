package com.boresjo.play.api.google.sts

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGoogleIdentityStsV1ExchangeTokenRequest: Format[Schema.GoogleIdentityStsV1ExchangeTokenRequest] = Json.format[Schema.GoogleIdentityStsV1ExchangeTokenRequest]
	given fmtGoogleIdentityStsV1ExchangeTokenResponse: Format[Schema.GoogleIdentityStsV1ExchangeTokenResponse] = Json.format[Schema.GoogleIdentityStsV1ExchangeTokenResponse]
	given fmtGoogleIamV1Binding: Format[Schema.GoogleIamV1Binding] = Json.format[Schema.GoogleIamV1Binding]
	given fmtGoogleTypeExpr: Format[Schema.GoogleTypeExpr] = Json.format[Schema.GoogleTypeExpr]
	given fmtGoogleIdentityStsV1Options: Format[Schema.GoogleIdentityStsV1Options] = Json.format[Schema.GoogleIdentityStsV1Options]
	given fmtGoogleIdentityStsV1AccessBoundary: Format[Schema.GoogleIdentityStsV1AccessBoundary] = Json.format[Schema.GoogleIdentityStsV1AccessBoundary]
	given fmtGoogleIdentityStsV1AccessBoundaryRule: Format[Schema.GoogleIdentityStsV1AccessBoundaryRule] = Json.format[Schema.GoogleIdentityStsV1AccessBoundaryRule]
	given fmtGoogleIdentityStsV1betaOptions: Format[Schema.GoogleIdentityStsV1betaOptions] = Json.format[Schema.GoogleIdentityStsV1betaOptions]
	given fmtGoogleIdentityStsV1betaAccessBoundary: Format[Schema.GoogleIdentityStsV1betaAccessBoundary] = Json.format[Schema.GoogleIdentityStsV1betaAccessBoundary]
	given fmtGoogleIdentityStsV1betaAccessBoundaryRule: Format[Schema.GoogleIdentityStsV1betaAccessBoundaryRule] = Json.format[Schema.GoogleIdentityStsV1betaAccessBoundaryRule]
}
