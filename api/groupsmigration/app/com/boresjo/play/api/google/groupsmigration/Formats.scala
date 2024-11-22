package com.boresjo.play.api.google.groupsmigration

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGroups: Format[Schema.Groups] = Json.format[Schema.Groups]
}
