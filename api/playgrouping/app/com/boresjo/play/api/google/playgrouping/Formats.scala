package com.boresjo.play.api.google.playgrouping

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtTag: Format[Schema.Tag] = Json.format[Schema.Tag]
	given fmtVerifyTokenRequest: Format[Schema.VerifyTokenRequest] = Json.format[Schema.VerifyTokenRequest]
	given fmtVerifyTokenResponse: Format[Schema.VerifyTokenResponse] = Json.format[Schema.VerifyTokenResponse]
	given fmtCreateOrUpdateTagsResponse: Format[Schema.CreateOrUpdateTagsResponse] = Json.format[Schema.CreateOrUpdateTagsResponse]
	given fmtCreateOrUpdateTagsRequest: Format[Schema.CreateOrUpdateTagsRequest] = Json.format[Schema.CreateOrUpdateTagsRequest]
}
