package com.boresjo.play.api.google.iap

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putSchemaGetPolicyOptions: Conversion[Schema.GetPolicyOptions, Option[Schema.GetPolicyOptions]] = (fun: Schema.GetPolicyOptions) => Option(fun)
		given putSchemaAccessSettings: Conversion[Schema.AccessSettings, Option[Schema.AccessSettings]] = (fun: Schema.AccessSettings) => Option(fun)
		given putSchemaApplicationSettings: Conversion[Schema.ApplicationSettings, Option[Schema.ApplicationSettings]] = (fun: Schema.ApplicationSettings) => Option(fun)
		given putSchemaGcipSettings: Conversion[Schema.GcipSettings, Option[Schema.GcipSettings]] = (fun: Schema.GcipSettings) => Option(fun)
		given putSchemaCorsSettings: Conversion[Schema.CorsSettings, Option[Schema.CorsSettings]] = (fun: Schema.CorsSettings) => Option(fun)
		given putSchemaOAuthSettings: Conversion[Schema.OAuthSettings, Option[Schema.OAuthSettings]] = (fun: Schema.OAuthSettings) => Option(fun)
		given putSchemaPolicyDelegationSettings: Conversion[Schema.PolicyDelegationSettings, Option[Schema.PolicyDelegationSettings]] = (fun: Schema.PolicyDelegationSettings) => Option(fun)
		given putSchemaReauthSettings: Conversion[Schema.ReauthSettings, Option[Schema.ReauthSettings]] = (fun: Schema.ReauthSettings) => Option(fun)
		given putSchemaAllowedDomainsSettings: Conversion[Schema.AllowedDomainsSettings, Option[Schema.AllowedDomainsSettings]] = (fun: Schema.AllowedDomainsSettings) => Option(fun)
		given putSchemaWorkforceIdentitySettings: Conversion[Schema.WorkforceIdentitySettings, Option[Schema.WorkforceIdentitySettings]] = (fun: Schema.WorkforceIdentitySettings) => Option(fun)
		given putListSchemaAccessSettingsIdentitySourcesEnum: Conversion[List[Schema.AccessSettings.IdentitySourcesEnum], Option[List[Schema.AccessSettings.IdentitySourcesEnum]]] = (fun: List[Schema.AccessSettings.IdentitySourcesEnum]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaResource: Conversion[Schema.Resource, Option[Schema.Resource]] = (fun: Schema.Resource) => Option(fun)
		given putSchemaPolicyName: Conversion[Schema.PolicyName, Option[Schema.PolicyName]] = (fun: Schema.PolicyName) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaNextStateOfTags: Conversion[Schema.NextStateOfTags, Option[Schema.NextStateOfTags]] = (fun: Schema.NextStateOfTags) => Option(fun)
		given putSchemaTagsFullState: Conversion[Schema.TagsFullState, Option[Schema.TagsFullState]] = (fun: Schema.TagsFullState) => Option(fun)
		given putSchemaTagsPartialState: Conversion[Schema.TagsPartialState, Option[Schema.TagsPartialState]] = (fun: Schema.TagsPartialState) => Option(fun)
		given putSchemaTagsFullStateForChildResource: Conversion[Schema.TagsFullStateForChildResource, Option[Schema.TagsFullStateForChildResource]] = (fun: Schema.TagsFullStateForChildResource) => Option(fun)
		given putSchemaReauthSettingsMethodEnum: Conversion[Schema.ReauthSettings.MethodEnum, Option[Schema.ReauthSettings.MethodEnum]] = (fun: Schema.ReauthSettings.MethodEnum) => Option(fun)
		given putSchemaReauthSettingsPolicyTypeEnum: Conversion[Schema.ReauthSettings.PolicyTypeEnum, Option[Schema.ReauthSettings.PolicyTypeEnum]] = (fun: Schema.ReauthSettings.PolicyTypeEnum) => Option(fun)
		given putSchemaOAuth2: Conversion[Schema.OAuth2, Option[Schema.OAuth2]] = (fun: Schema.OAuth2) => Option(fun)
		given putSchemaCsmSettings: Conversion[Schema.CsmSettings, Option[Schema.CsmSettings]] = (fun: Schema.CsmSettings) => Option(fun)
		given putSchemaAccessDeniedPageSettings: Conversion[Schema.AccessDeniedPageSettings, Option[Schema.AccessDeniedPageSettings]] = (fun: Schema.AccessDeniedPageSettings) => Option(fun)
		given putSchemaAttributePropagationSettings: Conversion[Schema.AttributePropagationSettings, Option[Schema.AttributePropagationSettings]] = (fun: Schema.AttributePropagationSettings) => Option(fun)
		given putListSchemaAttributePropagationSettingsOutputCredentialsEnum: Conversion[List[Schema.AttributePropagationSettings.OutputCredentialsEnum], Option[List[Schema.AttributePropagationSettings.OutputCredentialsEnum]]] = (fun: List[Schema.AttributePropagationSettings.OutputCredentialsEnum]) => Option(fun)
		given putListSchemaTunnelDestGroup: Conversion[List[Schema.TunnelDestGroup], Option[List[Schema.TunnelDestGroup]]] = (fun: List[Schema.TunnelDestGroup]) => Option(fun)
		given putListSchemaBrand: Conversion[List[Schema.Brand], Option[List[Schema.Brand]]] = (fun: List[Schema.Brand]) => Option(fun)
		given putListSchemaIdentityAwareProxyClient: Conversion[List[Schema.IdentityAwareProxyClient], Option[List[Schema.IdentityAwareProxyClient]]] = (fun: List[Schema.IdentityAwareProxyClient]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
