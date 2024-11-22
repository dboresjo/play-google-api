package com.boresjo.play.api.google.webfonts

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtWebfontList: Format[Schema.WebfontList] = Json.format[Schema.WebfontList]
	given fmtWebfont: Format[Schema.Webfont] = Json.format[Schema.Webfont]
	given fmtAxis: Format[Schema.Axis] = Json.format[Schema.Axis]
}
