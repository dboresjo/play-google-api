package com.boresjo.play.api.google.publicca

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtExternalAccountKey: Format[Schema.ExternalAccountKey] = Json.format[Schema.ExternalAccountKey]
}
