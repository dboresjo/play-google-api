package com.boresjo.play.api.google.iamcredentials

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGenerateAccessTokenRequest: Format[Schema.GenerateAccessTokenRequest] = Json.format[Schema.GenerateAccessTokenRequest]
	given fmtGenerateAccessTokenResponse: Format[Schema.GenerateAccessTokenResponse] = Json.format[Schema.GenerateAccessTokenResponse]
	given fmtGenerateIdTokenRequest: Format[Schema.GenerateIdTokenRequest] = Json.format[Schema.GenerateIdTokenRequest]
	given fmtGenerateIdTokenResponse: Format[Schema.GenerateIdTokenResponse] = Json.format[Schema.GenerateIdTokenResponse]
	given fmtSignBlobRequest: Format[Schema.SignBlobRequest] = Json.format[Schema.SignBlobRequest]
	given fmtSignBlobResponse: Format[Schema.SignBlobResponse] = Json.format[Schema.SignBlobResponse]
	given fmtSignJwtRequest: Format[Schema.SignJwtRequest] = Json.format[Schema.SignJwtRequest]
	given fmtSignJwtResponse: Format[Schema.SignJwtResponse] = Json.format[Schema.SignJwtResponse]
	given fmtServiceAccountAllowedLocations: Format[Schema.ServiceAccountAllowedLocations] = Json.format[Schema.ServiceAccountAllowedLocations]
}
