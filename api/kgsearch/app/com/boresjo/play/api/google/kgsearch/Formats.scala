package com.boresjo.play.api.google.kgsearch

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtSearchResponse: Format[Schema.SearchResponse] = Json.format[Schema.SearchResponse]
}
