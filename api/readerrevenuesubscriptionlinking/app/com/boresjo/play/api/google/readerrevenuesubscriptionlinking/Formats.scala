package com.boresjo.play.api.google.readerrevenuesubscriptionlinking

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtEntitlement: Format[Schema.Entitlement] = Json.format[Schema.Entitlement]
	given fmtDeleteReaderResponse: Format[Schema.DeleteReaderResponse] = Json.format[Schema.DeleteReaderResponse]
	given fmtReaderEntitlements: Format[Schema.ReaderEntitlements] = Json.format[Schema.ReaderEntitlements]
	given fmtReader: Format[Schema.Reader] = Json.format[Schema.Reader]
}
