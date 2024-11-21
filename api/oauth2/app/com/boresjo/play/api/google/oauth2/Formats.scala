package com.boresjo.play.api.google.oauth2

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtTokeninfo: Format[Schema.Tokeninfo] = Json.format[Schema.Tokeninfo]
	given fmtUserinfo: Format[Schema.Userinfo] = Json.format[Schema.Userinfo]
}
