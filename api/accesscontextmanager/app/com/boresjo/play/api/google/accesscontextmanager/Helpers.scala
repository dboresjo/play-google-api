package com.boresjo.play.api.google.accesscontextmanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaAccessPolicy: Conversion[List[Schema.AccessPolicy], Option[List[Schema.AccessPolicy]]] = (fun: List[Schema.AccessPolicy]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaAccessLevel: Conversion[List[Schema.AccessLevel], Option[List[Schema.AccessLevel]]] = (fun: List[Schema.AccessLevel]) => Option(fun)
		given putSchemaBasicLevel: Conversion[Schema.BasicLevel, Option[Schema.BasicLevel]] = (fun: Schema.BasicLevel) => Option(fun)
		given putSchemaCustomLevel: Conversion[Schema.CustomLevel, Option[Schema.CustomLevel]] = (fun: Schema.CustomLevel) => Option(fun)
		given putListSchemaCondition: Conversion[List[Schema.Condition], Option[List[Schema.Condition]]] = (fun: List[Schema.Condition]) => Option(fun)
		given putSchemaBasicLevelCombiningFunctionEnum: Conversion[Schema.BasicLevel.CombiningFunctionEnum, Option[Schema.BasicLevel.CombiningFunctionEnum]] = (fun: Schema.BasicLevel.CombiningFunctionEnum) => Option(fun)
		given putSchemaDevicePolicy: Conversion[Schema.DevicePolicy, Option[Schema.DevicePolicy]] = (fun: Schema.DevicePolicy) => Option(fun)
		given putListSchemaVpcNetworkSource: Conversion[List[Schema.VpcNetworkSource], Option[List[Schema.VpcNetworkSource]]] = (fun: List[Schema.VpcNetworkSource]) => Option(fun)
		given putListSchemaDevicePolicyAllowedEncryptionStatusesEnum: Conversion[List[Schema.DevicePolicy.AllowedEncryptionStatusesEnum], Option[List[Schema.DevicePolicy.AllowedEncryptionStatusesEnum]]] = (fun: List[Schema.DevicePolicy.AllowedEncryptionStatusesEnum]) => Option(fun)
		given putListSchemaOsConstraint: Conversion[List[Schema.OsConstraint], Option[List[Schema.OsConstraint]]] = (fun: List[Schema.OsConstraint]) => Option(fun)
		given putListSchemaDevicePolicyAllowedDeviceManagementLevelsEnum: Conversion[List[Schema.DevicePolicy.AllowedDeviceManagementLevelsEnum], Option[List[Schema.DevicePolicy.AllowedDeviceManagementLevelsEnum]]] = (fun: List[Schema.DevicePolicy.AllowedDeviceManagementLevelsEnum]) => Option(fun)
		given putSchemaOsConstraintOsTypeEnum: Conversion[Schema.OsConstraint.OsTypeEnum, Option[Schema.OsConstraint.OsTypeEnum]] = (fun: Schema.OsConstraint.OsTypeEnum) => Option(fun)
		given putSchemaVpcSubNetwork: Conversion[Schema.VpcSubNetwork, Option[Schema.VpcSubNetwork]] = (fun: Schema.VpcSubNetwork) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaServicePerimeter: Conversion[List[Schema.ServicePerimeter], Option[List[Schema.ServicePerimeter]]] = (fun: List[Schema.ServicePerimeter]) => Option(fun)
		given putSchemaServicePerimeterPerimeterTypeEnum: Conversion[Schema.ServicePerimeter.PerimeterTypeEnum, Option[Schema.ServicePerimeter.PerimeterTypeEnum]] = (fun: Schema.ServicePerimeter.PerimeterTypeEnum) => Option(fun)
		given putSchemaServicePerimeterConfig: Conversion[Schema.ServicePerimeterConfig, Option[Schema.ServicePerimeterConfig]] = (fun: Schema.ServicePerimeterConfig) => Option(fun)
		given putSchemaVpcAccessibleServices: Conversion[Schema.VpcAccessibleServices, Option[Schema.VpcAccessibleServices]] = (fun: Schema.VpcAccessibleServices) => Option(fun)
		given putListSchemaIngressPolicy: Conversion[List[Schema.IngressPolicy], Option[List[Schema.IngressPolicy]]] = (fun: List[Schema.IngressPolicy]) => Option(fun)
		given putListSchemaEgressPolicy: Conversion[List[Schema.EgressPolicy], Option[List[Schema.EgressPolicy]]] = (fun: List[Schema.EgressPolicy]) => Option(fun)
		given putSchemaIngressFrom: Conversion[Schema.IngressFrom, Option[Schema.IngressFrom]] = (fun: Schema.IngressFrom) => Option(fun)
		given putSchemaIngressTo: Conversion[Schema.IngressTo, Option[Schema.IngressTo]] = (fun: Schema.IngressTo) => Option(fun)
		given putListSchemaIngressSource: Conversion[List[Schema.IngressSource], Option[List[Schema.IngressSource]]] = (fun: List[Schema.IngressSource]) => Option(fun)
		given putSchemaIngressFromIdentityTypeEnum: Conversion[Schema.IngressFrom.IdentityTypeEnum, Option[Schema.IngressFrom.IdentityTypeEnum]] = (fun: Schema.IngressFrom.IdentityTypeEnum) => Option(fun)
		given putListSchemaApiOperation: Conversion[List[Schema.ApiOperation], Option[List[Schema.ApiOperation]]] = (fun: List[Schema.ApiOperation]) => Option(fun)
		given putListSchemaMethodSelector: Conversion[List[Schema.MethodSelector], Option[List[Schema.MethodSelector]]] = (fun: List[Schema.MethodSelector]) => Option(fun)
		given putSchemaEgressFrom: Conversion[Schema.EgressFrom, Option[Schema.EgressFrom]] = (fun: Schema.EgressFrom) => Option(fun)
		given putSchemaEgressTo: Conversion[Schema.EgressTo, Option[Schema.EgressTo]] = (fun: Schema.EgressTo) => Option(fun)
		given putSchemaEgressFromIdentityTypeEnum: Conversion[Schema.EgressFrom.IdentityTypeEnum, Option[Schema.EgressFrom.IdentityTypeEnum]] = (fun: Schema.EgressFrom.IdentityTypeEnum) => Option(fun)
		given putListSchemaEgressSource: Conversion[List[Schema.EgressSource], Option[List[Schema.EgressSource]]] = (fun: List[Schema.EgressSource]) => Option(fun)
		given putSchemaEgressFromSourceRestrictionEnum: Conversion[Schema.EgressFrom.SourceRestrictionEnum, Option[Schema.EgressFrom.SourceRestrictionEnum]] = (fun: Schema.EgressFrom.SourceRestrictionEnum) => Option(fun)
		given putListSchemaSupportedService: Conversion[List[Schema.SupportedService], Option[List[Schema.SupportedService]]] = (fun: List[Schema.SupportedService]) => Option(fun)
		given putSchemaSupportedServiceSupportStageEnum: Conversion[Schema.SupportedService.SupportStageEnum, Option[Schema.SupportedService.SupportStageEnum]] = (fun: Schema.SupportedService.SupportStageEnum) => Option(fun)
		given putSchemaSupportedServiceServiceSupportStageEnum: Conversion[Schema.SupportedService.ServiceSupportStageEnum, Option[Schema.SupportedService.ServiceSupportStageEnum]] = (fun: Schema.SupportedService.ServiceSupportStageEnum) => Option(fun)
		given putListSchemaGcpUserAccessBinding: Conversion[List[Schema.GcpUserAccessBinding], Option[List[Schema.GcpUserAccessBinding]]] = (fun: List[Schema.GcpUserAccessBinding]) => Option(fun)
		given putSchemaSessionSettings: Conversion[Schema.SessionSettings, Option[Schema.SessionSettings]] = (fun: Schema.SessionSettings) => Option(fun)
		given putListSchemaApplication: Conversion[List[Schema.Application], Option[List[Schema.Application]]] = (fun: List[Schema.Application]) => Option(fun)
		given putListSchemaScopedAccessSettings: Conversion[List[Schema.ScopedAccessSettings], Option[List[Schema.ScopedAccessSettings]]] = (fun: List[Schema.ScopedAccessSettings]) => Option(fun)
		given putSchemaSessionSettingsSessionReauthMethodEnum: Conversion[Schema.SessionSettings.SessionReauthMethodEnum, Option[Schema.SessionSettings.SessionReauthMethodEnum]] = (fun: Schema.SessionSettings.SessionReauthMethodEnum) => Option(fun)
		given putSchemaAccessScope: Conversion[Schema.AccessScope, Option[Schema.AccessScope]] = (fun: Schema.AccessScope) => Option(fun)
		given putSchemaAccessSettings: Conversion[Schema.AccessSettings, Option[Schema.AccessSettings]] = (fun: Schema.AccessSettings) => Option(fun)
		given putSchemaClientScope: Conversion[Schema.ClientScope, Option[Schema.ClientScope]] = (fun: Schema.ClientScope) => Option(fun)
		given putSchemaApplication: Conversion[Schema.Application, Option[Schema.Application]] = (fun: Schema.Application) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
		given putSchemaGetPolicyOptions: Conversion[Schema.GetPolicyOptions, Option[Schema.GetPolicyOptions]] = (fun: Schema.GetPolicyOptions) => Option(fun)
		given putListSchemaAuthorizedOrgsDesc: Conversion[List[Schema.AuthorizedOrgsDesc], Option[List[Schema.AuthorizedOrgsDesc]]] = (fun: List[Schema.AuthorizedOrgsDesc]) => Option(fun)
		given putSchemaAuthorizedOrgsDescAuthorizationTypeEnum: Conversion[Schema.AuthorizedOrgsDesc.AuthorizationTypeEnum, Option[Schema.AuthorizedOrgsDesc.AuthorizationTypeEnum]] = (fun: Schema.AuthorizedOrgsDesc.AuthorizationTypeEnum) => Option(fun)
		given putSchemaAuthorizedOrgsDescAssetTypeEnum: Conversion[Schema.AuthorizedOrgsDesc.AssetTypeEnum, Option[Schema.AuthorizedOrgsDesc.AssetTypeEnum]] = (fun: Schema.AuthorizedOrgsDesc.AssetTypeEnum) => Option(fun)
		given putSchemaAuthorizedOrgsDescAuthorizationDirectionEnum: Conversion[Schema.AuthorizedOrgsDesc.AuthorizationDirectionEnum, Option[Schema.AuthorizedOrgsDesc.AuthorizationDirectionEnum]] = (fun: Schema.AuthorizedOrgsDesc.AuthorizationDirectionEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
