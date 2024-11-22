package com.boresjo.play.api.google.accesscontextmanager

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
	given fmtListAccessPoliciesResponse: Format[Schema.ListAccessPoliciesResponse] = Json.format[Schema.ListAccessPoliciesResponse]
	given fmtAccessPolicy: Format[Schema.AccessPolicy] = Json.format[Schema.AccessPolicy]
	given fmtListAccessLevelsResponse: Format[Schema.ListAccessLevelsResponse] = Json.format[Schema.ListAccessLevelsResponse]
	given fmtAccessLevel: Format[Schema.AccessLevel] = Json.format[Schema.AccessLevel]
	given fmtBasicLevel: Format[Schema.BasicLevel] = Json.format[Schema.BasicLevel]
	given fmtCustomLevel: Format[Schema.CustomLevel] = Json.format[Schema.CustomLevel]
	given fmtCondition: Format[Schema.Condition] = Json.format[Schema.Condition]
	given fmtBasicLevelCombiningFunctionEnum: Format[Schema.BasicLevel.CombiningFunctionEnum] = JsonEnumFormat[Schema.BasicLevel.CombiningFunctionEnum]
	given fmtDevicePolicy: Format[Schema.DevicePolicy] = Json.format[Schema.DevicePolicy]
	given fmtVpcNetworkSource: Format[Schema.VpcNetworkSource] = Json.format[Schema.VpcNetworkSource]
	given fmtDevicePolicyAllowedEncryptionStatusesEnum: Format[Schema.DevicePolicy.AllowedEncryptionStatusesEnum] = JsonEnumFormat[Schema.DevicePolicy.AllowedEncryptionStatusesEnum]
	given fmtOsConstraint: Format[Schema.OsConstraint] = Json.format[Schema.OsConstraint]
	given fmtDevicePolicyAllowedDeviceManagementLevelsEnum: Format[Schema.DevicePolicy.AllowedDeviceManagementLevelsEnum] = JsonEnumFormat[Schema.DevicePolicy.AllowedDeviceManagementLevelsEnum]
	given fmtOsConstraintOsTypeEnum: Format[Schema.OsConstraint.OsTypeEnum] = JsonEnumFormat[Schema.OsConstraint.OsTypeEnum]
	given fmtVpcSubNetwork: Format[Schema.VpcSubNetwork] = Json.format[Schema.VpcSubNetwork]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtReplaceAccessLevelsRequest: Format[Schema.ReplaceAccessLevelsRequest] = Json.format[Schema.ReplaceAccessLevelsRequest]
	given fmtListServicePerimetersResponse: Format[Schema.ListServicePerimetersResponse] = Json.format[Schema.ListServicePerimetersResponse]
	given fmtServicePerimeter: Format[Schema.ServicePerimeter] = Json.format[Schema.ServicePerimeter]
	given fmtServicePerimeterPerimeterTypeEnum: Format[Schema.ServicePerimeter.PerimeterTypeEnum] = JsonEnumFormat[Schema.ServicePerimeter.PerimeterTypeEnum]
	given fmtServicePerimeterConfig: Format[Schema.ServicePerimeterConfig] = Json.format[Schema.ServicePerimeterConfig]
	given fmtVpcAccessibleServices: Format[Schema.VpcAccessibleServices] = Json.format[Schema.VpcAccessibleServices]
	given fmtIngressPolicy: Format[Schema.IngressPolicy] = Json.format[Schema.IngressPolicy]
	given fmtEgressPolicy: Format[Schema.EgressPolicy] = Json.format[Schema.EgressPolicy]
	given fmtIngressFrom: Format[Schema.IngressFrom] = Json.format[Schema.IngressFrom]
	given fmtIngressTo: Format[Schema.IngressTo] = Json.format[Schema.IngressTo]
	given fmtIngressSource: Format[Schema.IngressSource] = Json.format[Schema.IngressSource]
	given fmtIngressFromIdentityTypeEnum: Format[Schema.IngressFrom.IdentityTypeEnum] = JsonEnumFormat[Schema.IngressFrom.IdentityTypeEnum]
	given fmtApiOperation: Format[Schema.ApiOperation] = Json.format[Schema.ApiOperation]
	given fmtMethodSelector: Format[Schema.MethodSelector] = Json.format[Schema.MethodSelector]
	given fmtEgressFrom: Format[Schema.EgressFrom] = Json.format[Schema.EgressFrom]
	given fmtEgressTo: Format[Schema.EgressTo] = Json.format[Schema.EgressTo]
	given fmtEgressFromIdentityTypeEnum: Format[Schema.EgressFrom.IdentityTypeEnum] = JsonEnumFormat[Schema.EgressFrom.IdentityTypeEnum]
	given fmtEgressSource: Format[Schema.EgressSource] = Json.format[Schema.EgressSource]
	given fmtEgressFromSourceRestrictionEnum: Format[Schema.EgressFrom.SourceRestrictionEnum] = JsonEnumFormat[Schema.EgressFrom.SourceRestrictionEnum]
	given fmtReplaceServicePerimetersRequest: Format[Schema.ReplaceServicePerimetersRequest] = Json.format[Schema.ReplaceServicePerimetersRequest]
	given fmtCommitServicePerimetersRequest: Format[Schema.CommitServicePerimetersRequest] = Json.format[Schema.CommitServicePerimetersRequest]
	given fmtListSupportedServicesResponse: Format[Schema.ListSupportedServicesResponse] = Json.format[Schema.ListSupportedServicesResponse]
	given fmtSupportedService: Format[Schema.SupportedService] = Json.format[Schema.SupportedService]
	given fmtSupportedServiceSupportStageEnum: Format[Schema.SupportedService.SupportStageEnum] = JsonEnumFormat[Schema.SupportedService.SupportStageEnum]
	given fmtSupportedServiceServiceSupportStageEnum: Format[Schema.SupportedService.ServiceSupportStageEnum] = JsonEnumFormat[Schema.SupportedService.ServiceSupportStageEnum]
	given fmtListGcpUserAccessBindingsResponse: Format[Schema.ListGcpUserAccessBindingsResponse] = Json.format[Schema.ListGcpUserAccessBindingsResponse]
	given fmtGcpUserAccessBinding: Format[Schema.GcpUserAccessBinding] = Json.format[Schema.GcpUserAccessBinding]
	given fmtSessionSettings: Format[Schema.SessionSettings] = Json.format[Schema.SessionSettings]
	given fmtApplication: Format[Schema.Application] = Json.format[Schema.Application]
	given fmtScopedAccessSettings: Format[Schema.ScopedAccessSettings] = Json.format[Schema.ScopedAccessSettings]
	given fmtSessionSettingsSessionReauthMethodEnum: Format[Schema.SessionSettings.SessionReauthMethodEnum] = JsonEnumFormat[Schema.SessionSettings.SessionReauthMethodEnum]
	given fmtAccessScope: Format[Schema.AccessScope] = Json.format[Schema.AccessScope]
	given fmtAccessSettings: Format[Schema.AccessSettings] = Json.format[Schema.AccessSettings]
	given fmtClientScope: Format[Schema.ClientScope] = Json.format[Schema.ClientScope]
	given fmtSetIamPolicyRequest: Format[Schema.SetIamPolicyRequest] = Json.format[Schema.SetIamPolicyRequest]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtBinding: Format[Schema.Binding] = Json.format[Schema.Binding]
	given fmtAuditConfig: Format[Schema.AuditConfig] = Json.format[Schema.AuditConfig]
	given fmtAuditLogConfig: Format[Schema.AuditLogConfig] = Json.format[Schema.AuditLogConfig]
	given fmtAuditLogConfigLogTypeEnum: Format[Schema.AuditLogConfig.LogTypeEnum] = JsonEnumFormat[Schema.AuditLogConfig.LogTypeEnum]
	given fmtGetIamPolicyRequest: Format[Schema.GetIamPolicyRequest] = Json.format[Schema.GetIamPolicyRequest]
	given fmtGetPolicyOptions: Format[Schema.GetPolicyOptions] = Json.format[Schema.GetPolicyOptions]
	given fmtTestIamPermissionsRequest: Format[Schema.TestIamPermissionsRequest] = Json.format[Schema.TestIamPermissionsRequest]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
	given fmtListAuthorizedOrgsDescsResponse: Format[Schema.ListAuthorizedOrgsDescsResponse] = Json.format[Schema.ListAuthorizedOrgsDescsResponse]
	given fmtAuthorizedOrgsDesc: Format[Schema.AuthorizedOrgsDesc] = Json.format[Schema.AuthorizedOrgsDesc]
	given fmtAuthorizedOrgsDescAuthorizationTypeEnum: Format[Schema.AuthorizedOrgsDesc.AuthorizationTypeEnum] = JsonEnumFormat[Schema.AuthorizedOrgsDesc.AuthorizationTypeEnum]
	given fmtAuthorizedOrgsDescAssetTypeEnum: Format[Schema.AuthorizedOrgsDesc.AssetTypeEnum] = JsonEnumFormat[Schema.AuthorizedOrgsDesc.AssetTypeEnum]
	given fmtAuthorizedOrgsDescAuthorizationDirectionEnum: Format[Schema.AuthorizedOrgsDesc.AuthorizationDirectionEnum] = JsonEnumFormat[Schema.AuthorizedOrgsDesc.AuthorizationDirectionEnum]
	given fmtReplaceAccessLevelsResponse: Format[Schema.ReplaceAccessLevelsResponse] = Json.format[Schema.ReplaceAccessLevelsResponse]
	given fmtReplaceServicePerimetersResponse: Format[Schema.ReplaceServicePerimetersResponse] = Json.format[Schema.ReplaceServicePerimetersResponse]
	given fmtCommitServicePerimetersResponse: Format[Schema.CommitServicePerimetersResponse] = Json.format[Schema.CommitServicePerimetersResponse]
	given fmtGcpUserAccessBindingOperationMetadata: Format[Schema.GcpUserAccessBindingOperationMetadata] = Json.format[Schema.GcpUserAccessBindingOperationMetadata]
	given fmtAccessContextManagerOperationMetadata: Format[Schema.AccessContextManagerOperationMetadata] = Json.format[Schema.AccessContextManagerOperationMetadata]
}
