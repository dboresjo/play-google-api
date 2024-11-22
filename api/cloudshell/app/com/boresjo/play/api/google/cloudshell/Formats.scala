package com.boresjo.play.api.google.cloudshell

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtEnvironment: Format[Schema.Environment] = Json.format[Schema.Environment]
	given fmtEnvironmentStateEnum: Format[Schema.Environment.StateEnum] = JsonEnumFormat[Schema.Environment.StateEnum]
	given fmtStartEnvironmentRequest: Format[Schema.StartEnvironmentRequest] = Json.format[Schema.StartEnvironmentRequest]
	given fmtAuthorizeEnvironmentRequest: Format[Schema.AuthorizeEnvironmentRequest] = Json.format[Schema.AuthorizeEnvironmentRequest]
	given fmtAddPublicKeyRequest: Format[Schema.AddPublicKeyRequest] = Json.format[Schema.AddPublicKeyRequest]
	given fmtRemovePublicKeyRequest: Format[Schema.RemovePublicKeyRequest] = Json.format[Schema.RemovePublicKeyRequest]
	given fmtAddPublicKeyMetadata: Format[Schema.AddPublicKeyMetadata] = Json.format[Schema.AddPublicKeyMetadata]
	given fmtAddPublicKeyResponse: Format[Schema.AddPublicKeyResponse] = Json.format[Schema.AddPublicKeyResponse]
	given fmtAuthorizeEnvironmentMetadata: Format[Schema.AuthorizeEnvironmentMetadata] = Json.format[Schema.AuthorizeEnvironmentMetadata]
	given fmtAuthorizeEnvironmentResponse: Format[Schema.AuthorizeEnvironmentResponse] = Json.format[Schema.AuthorizeEnvironmentResponse]
	given fmtCreateEnvironmentMetadata: Format[Schema.CreateEnvironmentMetadata] = Json.format[Schema.CreateEnvironmentMetadata]
	given fmtDeleteEnvironmentMetadata: Format[Schema.DeleteEnvironmentMetadata] = Json.format[Schema.DeleteEnvironmentMetadata]
	given fmtRemovePublicKeyMetadata: Format[Schema.RemovePublicKeyMetadata] = Json.format[Schema.RemovePublicKeyMetadata]
	given fmtRemovePublicKeyResponse: Format[Schema.RemovePublicKeyResponse] = Json.format[Schema.RemovePublicKeyResponse]
	given fmtStartEnvironmentMetadata: Format[Schema.StartEnvironmentMetadata] = Json.format[Schema.StartEnvironmentMetadata]
	given fmtStartEnvironmentMetadataStateEnum: Format[Schema.StartEnvironmentMetadata.StateEnum] = JsonEnumFormat[Schema.StartEnvironmentMetadata.StateEnum]
	given fmtStartEnvironmentResponse: Format[Schema.StartEnvironmentResponse] = Json.format[Schema.StartEnvironmentResponse]
}
