package com.boresjo.play.api.google.iam

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGoogleLongrunningOperation: Format[Schema.GoogleLongrunningOperation] = Json.format[Schema.GoogleLongrunningOperation]
	given fmtGoogleRpcStatus: Format[Schema.GoogleRpcStatus] = Json.format[Schema.GoogleRpcStatus]
	given fmtGoogleIamV2ListPoliciesResponse: Format[Schema.GoogleIamV2ListPoliciesResponse] = Json.format[Schema.GoogleIamV2ListPoliciesResponse]
	given fmtGoogleIamV2Policy: Format[Schema.GoogleIamV2Policy] = Json.format[Schema.GoogleIamV2Policy]
	given fmtGoogleIamV2PolicyRule: Format[Schema.GoogleIamV2PolicyRule] = Json.format[Schema.GoogleIamV2PolicyRule]
	given fmtGoogleIamV2DenyRule: Format[Schema.GoogleIamV2DenyRule] = Json.format[Schema.GoogleIamV2DenyRule]
	given fmtGoogleTypeExpr: Format[Schema.GoogleTypeExpr] = Json.format[Schema.GoogleTypeExpr]
	given fmtGoogleIamAdminV1AuditData: Format[Schema.GoogleIamAdminV1AuditData] = Json.format[Schema.GoogleIamAdminV1AuditData]
	given fmtGoogleIamAdminV1AuditDataPermissionDelta: Format[Schema.GoogleIamAdminV1AuditDataPermissionDelta] = Json.format[Schema.GoogleIamAdminV1AuditDataPermissionDelta]
	given fmtGoogleIamV1LoggingAuditData: Format[Schema.GoogleIamV1LoggingAuditData] = Json.format[Schema.GoogleIamV1LoggingAuditData]
	given fmtGoogleIamV1PolicyDelta: Format[Schema.GoogleIamV1PolicyDelta] = Json.format[Schema.GoogleIamV1PolicyDelta]
	given fmtGoogleIamV1BindingDelta: Format[Schema.GoogleIamV1BindingDelta] = Json.format[Schema.GoogleIamV1BindingDelta]
	given fmtGoogleIamV1BindingDeltaActionEnum: Format[Schema.GoogleIamV1BindingDelta.ActionEnum] = JsonEnumFormat[Schema.GoogleIamV1BindingDelta.ActionEnum]
	given fmtGoogleIamV2PolicyOperationMetadata: Format[Schema.GoogleIamV2PolicyOperationMetadata] = Json.format[Schema.GoogleIamV2PolicyOperationMetadata]
	given fmtGoogleIamV1betaWorkloadIdentityPoolOperationMetadata: Format[Schema.GoogleIamV1betaWorkloadIdentityPoolOperationMetadata] = Json.format[Schema.GoogleIamV1betaWorkloadIdentityPoolOperationMetadata]
	given fmtGoogleCloudCommonOperationMetadata: Format[Schema.GoogleCloudCommonOperationMetadata] = Json.format[Schema.GoogleCloudCommonOperationMetadata]
	given fmtGoogleIamV3mainOperationMetadata: Format[Schema.GoogleIamV3mainOperationMetadata] = Json.format[Schema.GoogleIamV3mainOperationMetadata]
	given fmtGoogleIamV3alphaOperationMetadata: Format[Schema.GoogleIamV3alphaOperationMetadata] = Json.format[Schema.GoogleIamV3alphaOperationMetadata]
	given fmtGoogleIamV3betaOperationMetadata: Format[Schema.GoogleIamV3betaOperationMetadata] = Json.format[Schema.GoogleIamV3betaOperationMetadata]
	given fmtGoogleIamV3OperationMetadata: Format[Schema.GoogleIamV3OperationMetadata] = Json.format[Schema.GoogleIamV3OperationMetadata]
	given fmtCloudControl2SharedOperationsReconciliationOperationMetadata: Format[Schema.CloudControl2SharedOperationsReconciliationOperationMetadata] = Json.format[Schema.CloudControl2SharedOperationsReconciliationOperationMetadata]
	given fmtCloudControl2SharedOperationsReconciliationOperationMetadataExclusiveActionEnum: Format[Schema.CloudControl2SharedOperationsReconciliationOperationMetadata.ExclusiveActionEnum] = JsonEnumFormat[Schema.CloudControl2SharedOperationsReconciliationOperationMetadata.ExclusiveActionEnum]
}
