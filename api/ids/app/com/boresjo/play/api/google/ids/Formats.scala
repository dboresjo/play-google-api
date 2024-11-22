package com.boresjo.play.api.google.ids

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
	given fmtListEndpointsResponse: Format[Schema.ListEndpointsResponse] = Json.format[Schema.ListEndpointsResponse]
	given fmtEndpoint: Format[Schema.Endpoint] = Json.format[Schema.Endpoint]
	given fmtEndpointSeverityEnum: Format[Schema.Endpoint.SeverityEnum] = JsonEnumFormat[Schema.Endpoint.SeverityEnum]
	given fmtEndpointStateEnum: Format[Schema.Endpoint.StateEnum] = JsonEnumFormat[Schema.Endpoint.StateEnum]
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtSetIamPolicyRequest: Format[Schema.SetIamPolicyRequest] = Json.format[Schema.SetIamPolicyRequest]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtBinding: Format[Schema.Binding] = Json.format[Schema.Binding]
	given fmtAuditConfig: Format[Schema.AuditConfig] = Json.format[Schema.AuditConfig]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtAuditLogConfig: Format[Schema.AuditLogConfig] = Json.format[Schema.AuditLogConfig]
	given fmtAuditLogConfigLogTypeEnum: Format[Schema.AuditLogConfig.LogTypeEnum] = JsonEnumFormat[Schema.AuditLogConfig.LogTypeEnum]
	given fmtTestIamPermissionsRequest: Format[Schema.TestIamPermissionsRequest] = Json.format[Schema.TestIamPermissionsRequest]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
}
