package com.boresjo.play.api.google.firebaseml

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
	given fmtModelOperationMetadata: Format[Schema.ModelOperationMetadata] = Json.format[Schema.ModelOperationMetadata]
	given fmtModelOperationMetadataBasicOperationStatusEnum: Format[Schema.ModelOperationMetadata.BasicOperationStatusEnum] = JsonEnumFormat[Schema.ModelOperationMetadata.BasicOperationStatusEnum]
}
