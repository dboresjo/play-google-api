package com.boresjo.play.api.google.iap

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtSetIamPolicyRequest: Format[Schema.SetIamPolicyRequest] = Json.format[Schema.SetIamPolicyRequest]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtBinding: Format[Schema.Binding] = Json.format[Schema.Binding]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtGetIamPolicyRequest: Format[Schema.GetIamPolicyRequest] = Json.format[Schema.GetIamPolicyRequest]
	given fmtGetPolicyOptions: Format[Schema.GetPolicyOptions] = Json.format[Schema.GetPolicyOptions]
	given fmtTestIamPermissionsRequest: Format[Schema.TestIamPermissionsRequest] = Json.format[Schema.TestIamPermissionsRequest]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
	given fmtIapSettings: Format[Schema.IapSettings] = Json.format[Schema.IapSettings]
	given fmtAccessSettings: Format[Schema.AccessSettings] = Json.format[Schema.AccessSettings]
	given fmtApplicationSettings: Format[Schema.ApplicationSettings] = Json.format[Schema.ApplicationSettings]
	given fmtGcipSettings: Format[Schema.GcipSettings] = Json.format[Schema.GcipSettings]
	given fmtCorsSettings: Format[Schema.CorsSettings] = Json.format[Schema.CorsSettings]
	given fmtOAuthSettings: Format[Schema.OAuthSettings] = Json.format[Schema.OAuthSettings]
	given fmtPolicyDelegationSettings: Format[Schema.PolicyDelegationSettings] = Json.format[Schema.PolicyDelegationSettings]
	given fmtReauthSettings: Format[Schema.ReauthSettings] = Json.format[Schema.ReauthSettings]
	given fmtAllowedDomainsSettings: Format[Schema.AllowedDomainsSettings] = Json.format[Schema.AllowedDomainsSettings]
	given fmtWorkforceIdentitySettings: Format[Schema.WorkforceIdentitySettings] = Json.format[Schema.WorkforceIdentitySettings]
	given fmtAccessSettingsIdentitySourcesEnum: Format[Schema.AccessSettings.IdentitySourcesEnum] = JsonEnumFormat[Schema.AccessSettings.IdentitySourcesEnum]
	given fmtResource: Format[Schema.Resource] = Json.format[Schema.Resource]
	given fmtPolicyName: Format[Schema.PolicyName] = Json.format[Schema.PolicyName]
	given fmtNextStateOfTags: Format[Schema.NextStateOfTags] = Json.format[Schema.NextStateOfTags]
	given fmtTagsFullState: Format[Schema.TagsFullState] = Json.format[Schema.TagsFullState]
	given fmtTagsPartialState: Format[Schema.TagsPartialState] = Json.format[Schema.TagsPartialState]
	given fmtTagsFullStateForChildResource: Format[Schema.TagsFullStateForChildResource] = Json.format[Schema.TagsFullStateForChildResource]
	given fmtReauthSettingsMethodEnum: Format[Schema.ReauthSettings.MethodEnum] = JsonEnumFormat[Schema.ReauthSettings.MethodEnum]
	given fmtReauthSettingsPolicyTypeEnum: Format[Schema.ReauthSettings.PolicyTypeEnum] = JsonEnumFormat[Schema.ReauthSettings.PolicyTypeEnum]
	given fmtOAuth2: Format[Schema.OAuth2] = Json.format[Schema.OAuth2]
	given fmtCsmSettings: Format[Schema.CsmSettings] = Json.format[Schema.CsmSettings]
	given fmtAccessDeniedPageSettings: Format[Schema.AccessDeniedPageSettings] = Json.format[Schema.AccessDeniedPageSettings]
	given fmtAttributePropagationSettings: Format[Schema.AttributePropagationSettings] = Json.format[Schema.AttributePropagationSettings]
	given fmtAttributePropagationSettingsOutputCredentialsEnum: Format[Schema.AttributePropagationSettings.OutputCredentialsEnum] = JsonEnumFormat[Schema.AttributePropagationSettings.OutputCredentialsEnum]
	given fmtValidateIapAttributeExpressionResponse: Format[Schema.ValidateIapAttributeExpressionResponse] = Json.format[Schema.ValidateIapAttributeExpressionResponse]
	given fmtListTunnelDestGroupsResponse: Format[Schema.ListTunnelDestGroupsResponse] = Json.format[Schema.ListTunnelDestGroupsResponse]
	given fmtTunnelDestGroup: Format[Schema.TunnelDestGroup] = Json.format[Schema.TunnelDestGroup]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtListBrandsResponse: Format[Schema.ListBrandsResponse] = Json.format[Schema.ListBrandsResponse]
	given fmtBrand: Format[Schema.Brand] = Json.format[Schema.Brand]
	given fmtIdentityAwareProxyClient: Format[Schema.IdentityAwareProxyClient] = Json.format[Schema.IdentityAwareProxyClient]
	given fmtListIdentityAwareProxyClientsResponse: Format[Schema.ListIdentityAwareProxyClientsResponse] = Json.format[Schema.ListIdentityAwareProxyClientsResponse]
	given fmtResetIdentityAwareProxyClientSecretRequest: Format[Schema.ResetIdentityAwareProxyClientSecretRequest] = Json.format[Schema.ResetIdentityAwareProxyClientSecretRequest]
}
