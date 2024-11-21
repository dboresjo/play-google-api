package com.boresjo.play.api.google.policytroubleshooter

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putSchemaGoogleCloudPolicytroubleshooterV1ExplainedPolicyAccessEnum: Conversion[Schema.GoogleCloudPolicytroubleshooterV1ExplainedPolicy.AccessEnum, Option[Schema.GoogleCloudPolicytroubleshooterV1ExplainedPolicy.AccessEnum]] = (fun: Schema.GoogleCloudPolicytroubleshooterV1ExplainedPolicy.AccessEnum) => Option(fun)
		given putSchemaGoogleCloudPolicytroubleshooterV1ExplainedPolicyRelevanceEnum: Conversion[Schema.GoogleCloudPolicytroubleshooterV1ExplainedPolicy.RelevanceEnum, Option[Schema.GoogleCloudPolicytroubleshooterV1ExplainedPolicy.RelevanceEnum]] = (fun: Schema.GoogleCloudPolicytroubleshooterV1ExplainedPolicy.RelevanceEnum) => Option(fun)
		given putListSchemaGoogleCloudPolicytroubleshooterV1BindingExplanation: Conversion[List[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation], Option[List[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation]]] = (fun: List[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaGoogleIamV1Policy: Conversion[Schema.GoogleIamV1Policy, Option[Schema.GoogleIamV1Policy]] = (fun: Schema.GoogleIamV1Policy) => Option(fun)
		given putSchemaGoogleCloudPolicytroubleshooterV1BindingExplanationRolePermissionEnum: Conversion[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.RolePermissionEnum, Option[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.RolePermissionEnum]] = (fun: Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.RolePermissionEnum) => Option(fun)
		given putSchemaGoogleTypeExpr: Conversion[Schema.GoogleTypeExpr, Option[Schema.GoogleTypeExpr]] = (fun: Schema.GoogleTypeExpr) => Option(fun)
		given putMapStringSchemaGoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership: Conversion[Map[String, Schema.GoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership], Option[Map[String, Schema.GoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership]]] = (fun: Map[String, Schema.GoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership]) => Option(fun)
		given putSchemaGoogleCloudPolicytroubleshooterV1BindingExplanationRelevanceEnum: Conversion[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.RelevanceEnum, Option[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.RelevanceEnum]] = (fun: Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.RelevanceEnum) => Option(fun)
		given putSchemaGoogleCloudPolicytroubleshooterV1BindingExplanationRolePermissionRelevanceEnum: Conversion[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.RolePermissionRelevanceEnum, Option[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.RolePermissionRelevanceEnum]] = (fun: Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.RolePermissionRelevanceEnum) => Option(fun)
		given putSchemaGoogleCloudPolicytroubleshooterV1BindingExplanationAccessEnum: Conversion[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.AccessEnum, Option[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.AccessEnum]] = (fun: Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.AccessEnum) => Option(fun)
		given putListSchemaGoogleRpcStatus: Conversion[List[Schema.GoogleRpcStatus], Option[List[Schema.GoogleRpcStatus]]] = (fun: List[Schema.GoogleRpcStatus]) => Option(fun)
		given putSchemaGoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyResponseAccessEnum: Conversion[Schema.GoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyResponse.AccessEnum, Option[Schema.GoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyResponse.AccessEnum]] = (fun: Schema.GoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyResponse.AccessEnum) => Option(fun)
		given putListSchemaGoogleCloudPolicytroubleshooterV1ExplainedPolicy: Conversion[List[Schema.GoogleCloudPolicytroubleshooterV1ExplainedPolicy], Option[List[Schema.GoogleCloudPolicytroubleshooterV1ExplainedPolicy]]] = (fun: List[Schema.GoogleCloudPolicytroubleshooterV1ExplainedPolicy]) => Option(fun)
		given putListSchemaGoogleIamV1Binding: Conversion[List[Schema.GoogleIamV1Binding], Option[List[Schema.GoogleIamV1Binding]]] = (fun: List[Schema.GoogleIamV1Binding]) => Option(fun)
		given putListSchemaGoogleIamV1AuditConfig: Conversion[List[Schema.GoogleIamV1AuditConfig], Option[List[Schema.GoogleIamV1AuditConfig]]] = (fun: List[Schema.GoogleIamV1AuditConfig]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaGoogleIamV1AuditLogConfigLogTypeEnum: Conversion[Schema.GoogleIamV1AuditLogConfig.LogTypeEnum, Option[Schema.GoogleIamV1AuditLogConfig.LogTypeEnum]] = (fun: Schema.GoogleIamV1AuditLogConfig.LogTypeEnum) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaGoogleIamV1AuditLogConfig: Conversion[List[Schema.GoogleIamV1AuditLogConfig], Option[List[Schema.GoogleIamV1AuditLogConfig]]] = (fun: List[Schema.GoogleIamV1AuditLogConfig]) => Option(fun)
		given putSchemaGoogleCloudPolicytroubleshooterV1AccessTuple: Conversion[Schema.GoogleCloudPolicytroubleshooterV1AccessTuple, Option[Schema.GoogleCloudPolicytroubleshooterV1AccessTuple]] = (fun: Schema.GoogleCloudPolicytroubleshooterV1AccessTuple) => Option(fun)
		given putSchemaGoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembershipRelevanceEnum: Conversion[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership.RelevanceEnum, Option[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership.RelevanceEnum]] = (fun: Schema.GoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership.RelevanceEnum) => Option(fun)
		given putSchemaGoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembershipMembershipEnum: Conversion[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership.MembershipEnum, Option[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership.MembershipEnum]] = (fun: Schema.GoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership.MembershipEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
