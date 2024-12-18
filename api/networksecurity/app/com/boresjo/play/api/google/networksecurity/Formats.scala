package com.boresjo.play.api.google.networksecurity

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
	given fmtListAddressGroupsResponse: Format[Schema.ListAddressGroupsResponse] = Json.format[Schema.ListAddressGroupsResponse]
	given fmtAddressGroup: Format[Schema.AddressGroup] = Json.format[Schema.AddressGroup]
	given fmtAddressGroupTypeEnum: Format[Schema.AddressGroup.TypeEnum] = JsonEnumFormat[Schema.AddressGroup.TypeEnum]
	given fmtAddressGroupPurposeEnum: Format[Schema.AddressGroup.PurposeEnum] = JsonEnumFormat[Schema.AddressGroup.PurposeEnum]
	given fmtAddAddressGroupItemsRequest: Format[Schema.AddAddressGroupItemsRequest] = Json.format[Schema.AddAddressGroupItemsRequest]
	given fmtRemoveAddressGroupItemsRequest: Format[Schema.RemoveAddressGroupItemsRequest] = Json.format[Schema.RemoveAddressGroupItemsRequest]
	given fmtCloneAddressGroupItemsRequest: Format[Schema.CloneAddressGroupItemsRequest] = Json.format[Schema.CloneAddressGroupItemsRequest]
	given fmtListAddressGroupReferencesResponse: Format[Schema.ListAddressGroupReferencesResponse] = Json.format[Schema.ListAddressGroupReferencesResponse]
	given fmtListAddressGroupReferencesResponseAddressGroupReference: Format[Schema.ListAddressGroupReferencesResponseAddressGroupReference] = Json.format[Schema.ListAddressGroupReferencesResponseAddressGroupReference]
	given fmtListFirewallEndpointsResponse: Format[Schema.ListFirewallEndpointsResponse] = Json.format[Schema.ListFirewallEndpointsResponse]
	given fmtFirewallEndpoint: Format[Schema.FirewallEndpoint] = Json.format[Schema.FirewallEndpoint]
	given fmtFirewallEndpointStateEnum: Format[Schema.FirewallEndpoint.StateEnum] = JsonEnumFormat[Schema.FirewallEndpoint.StateEnum]
	given fmtFirewallEndpointAssociationReference: Format[Schema.FirewallEndpointAssociationReference] = Json.format[Schema.FirewallEndpointAssociationReference]
	given fmtListFirewallEndpointAssociationsResponse: Format[Schema.ListFirewallEndpointAssociationsResponse] = Json.format[Schema.ListFirewallEndpointAssociationsResponse]
	given fmtFirewallEndpointAssociation: Format[Schema.FirewallEndpointAssociation] = Json.format[Schema.FirewallEndpointAssociation]
	given fmtFirewallEndpointAssociationStateEnum: Format[Schema.FirewallEndpointAssociation.StateEnum] = JsonEnumFormat[Schema.FirewallEndpointAssociation.StateEnum]
	given fmtListAuthorizationPoliciesResponse: Format[Schema.ListAuthorizationPoliciesResponse] = Json.format[Schema.ListAuthorizationPoliciesResponse]
	given fmtAuthorizationPolicy: Format[Schema.AuthorizationPolicy] = Json.format[Schema.AuthorizationPolicy]
	given fmtAuthorizationPolicyActionEnum: Format[Schema.AuthorizationPolicy.ActionEnum] = JsonEnumFormat[Schema.AuthorizationPolicy.ActionEnum]
	given fmtRule: Format[Schema.Rule] = Json.format[Schema.Rule]
	given fmtSource: Format[Schema.Source] = Json.format[Schema.Source]
	given fmtDestination: Format[Schema.Destination] = Json.format[Schema.Destination]
	given fmtHttpHeaderMatch: Format[Schema.HttpHeaderMatch] = Json.format[Schema.HttpHeaderMatch]
	given fmtListServerTlsPoliciesResponse: Format[Schema.ListServerTlsPoliciesResponse] = Json.format[Schema.ListServerTlsPoliciesResponse]
	given fmtServerTlsPolicy: Format[Schema.ServerTlsPolicy] = Json.format[Schema.ServerTlsPolicy]
	given fmtGoogleCloudNetworksecurityV1CertificateProvider: Format[Schema.GoogleCloudNetworksecurityV1CertificateProvider] = Json.format[Schema.GoogleCloudNetworksecurityV1CertificateProvider]
	given fmtMTLSPolicy: Format[Schema.MTLSPolicy] = Json.format[Schema.MTLSPolicy]
	given fmtGoogleCloudNetworksecurityV1GrpcEndpoint: Format[Schema.GoogleCloudNetworksecurityV1GrpcEndpoint] = Json.format[Schema.GoogleCloudNetworksecurityV1GrpcEndpoint]
	given fmtCertificateProviderInstance: Format[Schema.CertificateProviderInstance] = Json.format[Schema.CertificateProviderInstance]
	given fmtMTLSPolicyClientValidationModeEnum: Format[Schema.MTLSPolicy.ClientValidationModeEnum] = JsonEnumFormat[Schema.MTLSPolicy.ClientValidationModeEnum]
	given fmtValidationCA: Format[Schema.ValidationCA] = Json.format[Schema.ValidationCA]
	given fmtListClientTlsPoliciesResponse: Format[Schema.ListClientTlsPoliciesResponse] = Json.format[Schema.ListClientTlsPoliciesResponse]
	given fmtClientTlsPolicy: Format[Schema.ClientTlsPolicy] = Json.format[Schema.ClientTlsPolicy]
	given fmtListGatewaySecurityPoliciesResponse: Format[Schema.ListGatewaySecurityPoliciesResponse] = Json.format[Schema.ListGatewaySecurityPoliciesResponse]
	given fmtGatewaySecurityPolicy: Format[Schema.GatewaySecurityPolicy] = Json.format[Schema.GatewaySecurityPolicy]
	given fmtListGatewaySecurityPolicyRulesResponse: Format[Schema.ListGatewaySecurityPolicyRulesResponse] = Json.format[Schema.ListGatewaySecurityPolicyRulesResponse]
	given fmtGatewaySecurityPolicyRule: Format[Schema.GatewaySecurityPolicyRule] = Json.format[Schema.GatewaySecurityPolicyRule]
	given fmtGatewaySecurityPolicyRuleBasicProfileEnum: Format[Schema.GatewaySecurityPolicyRule.BasicProfileEnum] = JsonEnumFormat[Schema.GatewaySecurityPolicyRule.BasicProfileEnum]
	given fmtListUrlListsResponse: Format[Schema.ListUrlListsResponse] = Json.format[Schema.ListUrlListsResponse]
	given fmtUrlList: Format[Schema.UrlList] = Json.format[Schema.UrlList]
	given fmtListTlsInspectionPoliciesResponse: Format[Schema.ListTlsInspectionPoliciesResponse] = Json.format[Schema.ListTlsInspectionPoliciesResponse]
	given fmtTlsInspectionPolicy: Format[Schema.TlsInspectionPolicy] = Json.format[Schema.TlsInspectionPolicy]
	given fmtTlsInspectionPolicyMinTlsVersionEnum: Format[Schema.TlsInspectionPolicy.MinTlsVersionEnum] = JsonEnumFormat[Schema.TlsInspectionPolicy.MinTlsVersionEnum]
	given fmtTlsInspectionPolicyTlsFeatureProfileEnum: Format[Schema.TlsInspectionPolicy.TlsFeatureProfileEnum] = JsonEnumFormat[Schema.TlsInspectionPolicy.TlsFeatureProfileEnum]
	given fmtListAuthzPoliciesResponse: Format[Schema.ListAuthzPoliciesResponse] = Json.format[Schema.ListAuthzPoliciesResponse]
	given fmtAuthzPolicy: Format[Schema.AuthzPolicy] = Json.format[Schema.AuthzPolicy]
	given fmtAuthzPolicyTarget: Format[Schema.AuthzPolicyTarget] = Json.format[Schema.AuthzPolicyTarget]
	given fmtAuthzPolicyAuthzRule: Format[Schema.AuthzPolicyAuthzRule] = Json.format[Schema.AuthzPolicyAuthzRule]
	given fmtAuthzPolicyActionEnum: Format[Schema.AuthzPolicy.ActionEnum] = JsonEnumFormat[Schema.AuthzPolicy.ActionEnum]
	given fmtAuthzPolicyCustomProvider: Format[Schema.AuthzPolicyCustomProvider] = Json.format[Schema.AuthzPolicyCustomProvider]
	given fmtAuthzPolicyTargetLoadBalancingSchemeEnum: Format[Schema.AuthzPolicyTarget.LoadBalancingSchemeEnum] = JsonEnumFormat[Schema.AuthzPolicyTarget.LoadBalancingSchemeEnum]
	given fmtAuthzPolicyAuthzRuleFrom: Format[Schema.AuthzPolicyAuthzRuleFrom] = Json.format[Schema.AuthzPolicyAuthzRuleFrom]
	given fmtAuthzPolicyAuthzRuleTo: Format[Schema.AuthzPolicyAuthzRuleTo] = Json.format[Schema.AuthzPolicyAuthzRuleTo]
	given fmtAuthzPolicyAuthzRuleFromRequestSource: Format[Schema.AuthzPolicyAuthzRuleFromRequestSource] = Json.format[Schema.AuthzPolicyAuthzRuleFromRequestSource]
	given fmtAuthzPolicyAuthzRuleStringMatch: Format[Schema.AuthzPolicyAuthzRuleStringMatch] = Json.format[Schema.AuthzPolicyAuthzRuleStringMatch]
	given fmtAuthzPolicyAuthzRuleRequestResource: Format[Schema.AuthzPolicyAuthzRuleRequestResource] = Json.format[Schema.AuthzPolicyAuthzRuleRequestResource]
	given fmtAuthzPolicyAuthzRuleRequestResourceTagValueIdSet: Format[Schema.AuthzPolicyAuthzRuleRequestResourceTagValueIdSet] = Json.format[Schema.AuthzPolicyAuthzRuleRequestResourceTagValueIdSet]
	given fmtAuthzPolicyAuthzRuleToRequestOperation: Format[Schema.AuthzPolicyAuthzRuleToRequestOperation] = Json.format[Schema.AuthzPolicyAuthzRuleToRequestOperation]
	given fmtAuthzPolicyAuthzRuleToRequestOperationHeaderSet: Format[Schema.AuthzPolicyAuthzRuleToRequestOperationHeaderSet] = Json.format[Schema.AuthzPolicyAuthzRuleToRequestOperationHeaderSet]
	given fmtAuthzPolicyAuthzRuleHeaderMatch: Format[Schema.AuthzPolicyAuthzRuleHeaderMatch] = Json.format[Schema.AuthzPolicyAuthzRuleHeaderMatch]
	given fmtAuthzPolicyCustomProviderCloudIap: Format[Schema.AuthzPolicyCustomProviderCloudIap] = Json.format[Schema.AuthzPolicyCustomProviderCloudIap]
	given fmtAuthzPolicyCustomProviderAuthzExtension: Format[Schema.AuthzPolicyCustomProviderAuthzExtension] = Json.format[Schema.AuthzPolicyCustomProviderAuthzExtension]
	given fmtListSecurityProfileGroupsResponse: Format[Schema.ListSecurityProfileGroupsResponse] = Json.format[Schema.ListSecurityProfileGroupsResponse]
	given fmtSecurityProfileGroup: Format[Schema.SecurityProfileGroup] = Json.format[Schema.SecurityProfileGroup]
	given fmtListSecurityProfilesResponse: Format[Schema.ListSecurityProfilesResponse] = Json.format[Schema.ListSecurityProfilesResponse]
	given fmtSecurityProfile: Format[Schema.SecurityProfile] = Json.format[Schema.SecurityProfile]
	given fmtThreatPreventionProfile: Format[Schema.ThreatPreventionProfile] = Json.format[Schema.ThreatPreventionProfile]
	given fmtCustomMirroringProfile: Format[Schema.CustomMirroringProfile] = Json.format[Schema.CustomMirroringProfile]
	given fmtSecurityProfileTypeEnum: Format[Schema.SecurityProfile.TypeEnum] = JsonEnumFormat[Schema.SecurityProfile.TypeEnum]
	given fmtSeverityOverride: Format[Schema.SeverityOverride] = Json.format[Schema.SeverityOverride]
	given fmtThreatOverride: Format[Schema.ThreatOverride] = Json.format[Schema.ThreatOverride]
	given fmtSeverityOverrideSeverityEnum: Format[Schema.SeverityOverride.SeverityEnum] = JsonEnumFormat[Schema.SeverityOverride.SeverityEnum]
	given fmtSeverityOverrideActionEnum: Format[Schema.SeverityOverride.ActionEnum] = JsonEnumFormat[Schema.SeverityOverride.ActionEnum]
	given fmtThreatOverrideTypeEnum: Format[Schema.ThreatOverride.TypeEnum] = JsonEnumFormat[Schema.ThreatOverride.TypeEnum]
	given fmtThreatOverrideActionEnum: Format[Schema.ThreatOverride.ActionEnum] = JsonEnumFormat[Schema.ThreatOverride.ActionEnum]
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtGoogleIamV1SetIamPolicyRequest: Format[Schema.GoogleIamV1SetIamPolicyRequest] = Json.format[Schema.GoogleIamV1SetIamPolicyRequest]
	given fmtGoogleIamV1Policy: Format[Schema.GoogleIamV1Policy] = Json.format[Schema.GoogleIamV1Policy]
	given fmtGoogleIamV1Binding: Format[Schema.GoogleIamV1Binding] = Json.format[Schema.GoogleIamV1Binding]
	given fmtGoogleIamV1AuditConfig: Format[Schema.GoogleIamV1AuditConfig] = Json.format[Schema.GoogleIamV1AuditConfig]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtGoogleIamV1AuditLogConfig: Format[Schema.GoogleIamV1AuditLogConfig] = Json.format[Schema.GoogleIamV1AuditLogConfig]
	given fmtGoogleIamV1AuditLogConfigLogTypeEnum: Format[Schema.GoogleIamV1AuditLogConfig.LogTypeEnum] = JsonEnumFormat[Schema.GoogleIamV1AuditLogConfig.LogTypeEnum]
	given fmtGoogleIamV1TestIamPermissionsRequest: Format[Schema.GoogleIamV1TestIamPermissionsRequest] = Json.format[Schema.GoogleIamV1TestIamPermissionsRequest]
	given fmtGoogleIamV1TestIamPermissionsResponse: Format[Schema.GoogleIamV1TestIamPermissionsResponse] = Json.format[Schema.GoogleIamV1TestIamPermissionsResponse]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
}
