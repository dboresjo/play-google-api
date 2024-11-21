package com.boresjo.play.api.google.playcustomapp

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtOrganization: Format[Schema.Organization] = Json.format[Schema.Organization]
	given fmtCustomApp: Format[Schema.CustomApp] = Json.format[Schema.CustomApp]
}
