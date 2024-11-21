package com.boresjo.play.api.google.apigateway

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtApigatewayListOperationsResponse: Format[Schema.ApigatewayListOperationsResponse] = Json.format[Schema.ApigatewayListOperationsResponse]
	given fmtApigatewayOperation: Format[Schema.ApigatewayOperation] = Json.format[Schema.ApigatewayOperation]
	given fmtApigatewayStatus: Format[Schema.ApigatewayStatus] = Json.format[Schema.ApigatewayStatus]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtApigatewayCancelOperationRequest: Format[Schema.ApigatewayCancelOperationRequest] = Json.format[Schema.ApigatewayCancelOperationRequest]
	given fmtApigatewayListGatewaysResponse: Format[Schema.ApigatewayListGatewaysResponse] = Json.format[Schema.ApigatewayListGatewaysResponse]
	given fmtApigatewayGateway: Format[Schema.ApigatewayGateway] = Json.format[Schema.ApigatewayGateway]
	given fmtApigatewayGatewayStateEnum: Format[Schema.ApigatewayGateway.StateEnum] = JsonEnumFormat[Schema.ApigatewayGateway.StateEnum]
	given fmtApigatewayListApisResponse: Format[Schema.ApigatewayListApisResponse] = Json.format[Schema.ApigatewayListApisResponse]
	given fmtApigatewayApi: Format[Schema.ApigatewayApi] = Json.format[Schema.ApigatewayApi]
	given fmtApigatewayApiStateEnum: Format[Schema.ApigatewayApi.StateEnum] = JsonEnumFormat[Schema.ApigatewayApi.StateEnum]
	given fmtApigatewayListApiConfigsResponse: Format[Schema.ApigatewayListApiConfigsResponse] = Json.format[Schema.ApigatewayListApiConfigsResponse]
	given fmtApigatewayApiConfig: Format[Schema.ApigatewayApiConfig] = Json.format[Schema.ApigatewayApiConfig]
	given fmtApigatewayApiConfigStateEnum: Format[Schema.ApigatewayApiConfig.StateEnum] = JsonEnumFormat[Schema.ApigatewayApiConfig.StateEnum]
	given fmtApigatewayApiConfigOpenApiDocument: Format[Schema.ApigatewayApiConfigOpenApiDocument] = Json.format[Schema.ApigatewayApiConfigOpenApiDocument]
	given fmtApigatewayApiConfigGrpcServiceDefinition: Format[Schema.ApigatewayApiConfigGrpcServiceDefinition] = Json.format[Schema.ApigatewayApiConfigGrpcServiceDefinition]
	given fmtApigatewayApiConfigFile: Format[Schema.ApigatewayApiConfigFile] = Json.format[Schema.ApigatewayApiConfigFile]
	given fmtApigatewayListLocationsResponse: Format[Schema.ApigatewayListLocationsResponse] = Json.format[Schema.ApigatewayListLocationsResponse]
	given fmtApigatewayLocation: Format[Schema.ApigatewayLocation] = Json.format[Schema.ApigatewayLocation]
	given fmtApigatewaySetIamPolicyRequest: Format[Schema.ApigatewaySetIamPolicyRequest] = Json.format[Schema.ApigatewaySetIamPolicyRequest]
	given fmtApigatewayPolicy: Format[Schema.ApigatewayPolicy] = Json.format[Schema.ApigatewayPolicy]
	given fmtApigatewayBinding: Format[Schema.ApigatewayBinding] = Json.format[Schema.ApigatewayBinding]
	given fmtApigatewayAuditConfig: Format[Schema.ApigatewayAuditConfig] = Json.format[Schema.ApigatewayAuditConfig]
	given fmtApigatewayExpr: Format[Schema.ApigatewayExpr] = Json.format[Schema.ApigatewayExpr]
	given fmtApigatewayAuditLogConfig: Format[Schema.ApigatewayAuditLogConfig] = Json.format[Schema.ApigatewayAuditLogConfig]
	given fmtApigatewayAuditLogConfigLogTypeEnum: Format[Schema.ApigatewayAuditLogConfig.LogTypeEnum] = JsonEnumFormat[Schema.ApigatewayAuditLogConfig.LogTypeEnum]
	given fmtApigatewayTestIamPermissionsRequest: Format[Schema.ApigatewayTestIamPermissionsRequest] = Json.format[Schema.ApigatewayTestIamPermissionsRequest]
	given fmtApigatewayTestIamPermissionsResponse: Format[Schema.ApigatewayTestIamPermissionsResponse] = Json.format[Schema.ApigatewayTestIamPermissionsResponse]
	given fmtApigatewayOperationMetadata: Format[Schema.ApigatewayOperationMetadata] = Json.format[Schema.ApigatewayOperationMetadata]
	given fmtApigatewayOperationMetadataDiagnostic: Format[Schema.ApigatewayOperationMetadataDiagnostic] = Json.format[Schema.ApigatewayOperationMetadataDiagnostic]
}
