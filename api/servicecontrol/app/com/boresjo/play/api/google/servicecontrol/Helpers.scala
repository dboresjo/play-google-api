package com.boresjo.play.api.google.servicecontrol

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaAttributeContext: Conversion[Schema.AttributeContext, Option[Schema.AttributeContext]] = (fun: Schema.AttributeContext) => Option(fun)
		given putListSchemaResourceInfo: Conversion[List[Schema.ResourceInfo], Option[List[Schema.ResourceInfo]]] = (fun: List[Schema.ResourceInfo]) => Option(fun)
		given putSchemaPeer: Conversion[Schema.Peer, Option[Schema.Peer]] = (fun: Schema.Peer) => Option(fun)
		given putSchemaRequest: Conversion[Schema.Request, Option[Schema.Request]] = (fun: Schema.Request) => Option(fun)
		given putSchemaResponse: Conversion[Schema.Response, Option[Schema.Response]] = (fun: Schema.Response) => Option(fun)
		given putSchemaResource: Conversion[Schema.Resource, Option[Schema.Resource]] = (fun: Schema.Resource) => Option(fun)
		given putSchemaApi: Conversion[Schema.Api, Option[Schema.Api]] = (fun: Schema.Api) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaAuth: Conversion[Schema.Auth, Option[Schema.Auth]] = (fun: Schema.Auth) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaAttributeContext: Conversion[List[Schema.AttributeContext], Option[List[Schema.AttributeContext]]] = (fun: List[Schema.AttributeContext]) => Option(fun)
		given putSchemaResourceLocation: Conversion[Schema.ResourceLocation, Option[Schema.ResourceLocation]] = (fun: Schema.ResourceLocation) => Option(fun)
		given putSchemaAuthenticationInfo: Conversion[Schema.AuthenticationInfo, Option[Schema.AuthenticationInfo]] = (fun: Schema.AuthenticationInfo) => Option(fun)
		given putListSchemaAuthorizationInfo: Conversion[List[Schema.AuthorizationInfo], Option[List[Schema.AuthorizationInfo]]] = (fun: List[Schema.AuthorizationInfo]) => Option(fun)
		given putSchemaPolicyViolationInfo: Conversion[Schema.PolicyViolationInfo, Option[Schema.PolicyViolationInfo]] = (fun: Schema.PolicyViolationInfo) => Option(fun)
		given putSchemaRequestMetadata: Conversion[Schema.RequestMetadata, Option[Schema.RequestMetadata]] = (fun: Schema.RequestMetadata) => Option(fun)
		given putListSchemaServiceAccountDelegationInfo: Conversion[List[Schema.ServiceAccountDelegationInfo], Option[List[Schema.ServiceAccountDelegationInfo]]] = (fun: List[Schema.ServiceAccountDelegationInfo]) => Option(fun)
		given putSchemaServiceDelegationHistory: Conversion[Schema.ServiceDelegationHistory, Option[Schema.ServiceDelegationHistory]] = (fun: Schema.ServiceDelegationHistory) => Option(fun)
		given putSchemaFirstPartyPrincipal: Conversion[Schema.FirstPartyPrincipal, Option[Schema.FirstPartyPrincipal]] = (fun: Schema.FirstPartyPrincipal) => Option(fun)
		given putSchemaThirdPartyPrincipal: Conversion[Schema.ThirdPartyPrincipal, Option[Schema.ThirdPartyPrincipal]] = (fun: Schema.ThirdPartyPrincipal) => Option(fun)
		given putListSchemaServiceMetadata: Conversion[List[Schema.ServiceMetadata], Option[List[Schema.ServiceMetadata]]] = (fun: List[Schema.ServiceMetadata]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaAuthorizationInfoPermissionTypeEnum: Conversion[Schema.AuthorizationInfo.PermissionTypeEnum, Option[Schema.AuthorizationInfo.PermissionTypeEnum]] = (fun: Schema.AuthorizationInfo.PermissionTypeEnum) => Option(fun)
		given putSchemaOrgPolicyViolationInfo: Conversion[Schema.OrgPolicyViolationInfo, Option[Schema.OrgPolicyViolationInfo]] = (fun: Schema.OrgPolicyViolationInfo) => Option(fun)
		given putListSchemaViolationInfo: Conversion[List[Schema.ViolationInfo], Option[List[Schema.ViolationInfo]]] = (fun: List[Schema.ViolationInfo]) => Option(fun)
		given putSchemaViolationInfoPolicyTypeEnum: Conversion[Schema.ViolationInfo.PolicyTypeEnum, Option[Schema.ViolationInfo.PolicyTypeEnum]] = (fun: Schema.ViolationInfo.PolicyTypeEnum) => Option(fun)
		given putSchemaV2LogEntrySeverityEnum: Conversion[Schema.V2LogEntry.SeverityEnum, Option[Schema.V2LogEntry.SeverityEnum]] = (fun: Schema.V2LogEntry.SeverityEnum) => Option(fun)
		given putSchemaV2HttpRequest: Conversion[Schema.V2HttpRequest, Option[Schema.V2HttpRequest]] = (fun: Schema.V2HttpRequest) => Option(fun)
		given putSchemaV2LogEntryOperation: Conversion[Schema.V2LogEntryOperation, Option[Schema.V2LogEntryOperation]] = (fun: Schema.V2LogEntryOperation) => Option(fun)
		given putSchemaV2LogEntrySourceLocation: Conversion[Schema.V2LogEntrySourceLocation, Option[Schema.V2LogEntrySourceLocation]] = (fun: Schema.V2LogEntrySourceLocation) => Option(fun)
		given putSchemaV2ResourceEventTypeEnum: Conversion[Schema.V2ResourceEvent.TypeEnum, Option[Schema.V2ResourceEvent.TypeEnum]] = (fun: Schema.V2ResourceEvent.TypeEnum) => Option(fun)
		given putSchemaV2ResourceEventPathEnum: Conversion[Schema.V2ResourceEvent.PathEnum, Option[Schema.V2ResourceEvent.PathEnum]] = (fun: Schema.V2ResourceEvent.PathEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
