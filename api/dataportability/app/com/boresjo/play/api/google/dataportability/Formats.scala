package com.boresjo.play.api.google.dataportability

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtResetAuthorizationRequest: Format[Schema.ResetAuthorizationRequest] = Json.format[Schema.ResetAuthorizationRequest]
	given fmtRetryPortabilityArchiveRequest: Format[Schema.RetryPortabilityArchiveRequest] = Json.format[Schema.RetryPortabilityArchiveRequest]
	given fmtInitiatePortabilityArchiveRequest: Format[Schema.InitiatePortabilityArchiveRequest] = Json.format[Schema.InitiatePortabilityArchiveRequest]
	given fmtRetryPortabilityArchiveResponse: Format[Schema.RetryPortabilityArchiveResponse] = Json.format[Schema.RetryPortabilityArchiveResponse]
	given fmtInitiatePortabilityArchiveResponse: Format[Schema.InitiatePortabilityArchiveResponse] = Json.format[Schema.InitiatePortabilityArchiveResponse]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtPortabilityArchiveState: Format[Schema.PortabilityArchiveState] = Json.format[Schema.PortabilityArchiveState]
	given fmtPortabilityArchiveStateStateEnum: Format[Schema.PortabilityArchiveState.StateEnum] = JsonEnumFormat[Schema.PortabilityArchiveState.StateEnum]
}
