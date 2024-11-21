package com.boresjo.play.api.google.acceleratedmobilepageurl

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtBatchGetAmpUrlsRequest: Format[Schema.BatchGetAmpUrlsRequest] = Json.format[Schema.BatchGetAmpUrlsRequest]
	given fmtBatchGetAmpUrlsRequestLookupStrategyEnum: Format[Schema.BatchGetAmpUrlsRequest.LookupStrategyEnum] = JsonEnumFormat[Schema.BatchGetAmpUrlsRequest.LookupStrategyEnum]
	given fmtBatchGetAmpUrlsResponse: Format[Schema.BatchGetAmpUrlsResponse] = Json.format[Schema.BatchGetAmpUrlsResponse]
	given fmtAmpUrl: Format[Schema.AmpUrl] = Json.format[Schema.AmpUrl]
	given fmtAmpUrlError: Format[Schema.AmpUrlError] = Json.format[Schema.AmpUrlError]
	given fmtAmpUrlErrorErrorCodeEnum: Format[Schema.AmpUrlError.ErrorCodeEnum] = JsonEnumFormat[Schema.AmpUrlError.ErrorCodeEnum]
}
