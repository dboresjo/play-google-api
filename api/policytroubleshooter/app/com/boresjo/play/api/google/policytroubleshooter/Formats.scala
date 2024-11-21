package com.boresjo.play.api.google.policytroubleshooter

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGoogleCloudPolicytroubleshooterV1ExplainedPolicy: Format[Schema.GoogleCloudPolicytroubleshooterV1ExplainedPolicy] = Json.format[Schema.GoogleCloudPolicytroubleshooterV1ExplainedPolicy]
	given fmtGoogleCloudPolicytroubleshooterV1ExplainedPolicyAccessEnum: Format[Schema.GoogleCloudPolicytroubleshooterV1ExplainedPolicy.AccessEnum] = JsonEnumFormat[Schema.GoogleCloudPolicytroubleshooterV1ExplainedPolicy.AccessEnum]
	given fmtGoogleCloudPolicytroubleshooterV1ExplainedPolicyRelevanceEnum: Format[Schema.GoogleCloudPolicytroubleshooterV1ExplainedPolicy.RelevanceEnum] = JsonEnumFormat[Schema.GoogleCloudPolicytroubleshooterV1ExplainedPolicy.RelevanceEnum]
	given fmtGoogleCloudPolicytroubleshooterV1BindingExplanation: Format[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation] = Json.format[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation]
	given fmtGoogleIamV1Policy: Format[Schema.GoogleIamV1Policy] = Json.format[Schema.GoogleIamV1Policy]
	given fmtGoogleCloudPolicytroubleshooterV1BindingExplanationRolePermissionEnum: Format[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.RolePermissionEnum] = JsonEnumFormat[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.RolePermissionEnum]
	given fmtGoogleTypeExpr: Format[Schema.GoogleTypeExpr] = Json.format[Schema.GoogleTypeExpr]
	given fmtGoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership: Format[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership] = Json.format[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership]
	given fmtGoogleCloudPolicytroubleshooterV1BindingExplanationRelevanceEnum: Format[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.RelevanceEnum] = JsonEnumFormat[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.RelevanceEnum]
	given fmtGoogleCloudPolicytroubleshooterV1BindingExplanationRolePermissionRelevanceEnum: Format[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.RolePermissionRelevanceEnum] = JsonEnumFormat[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.RolePermissionRelevanceEnum]
	given fmtGoogleCloudPolicytroubleshooterV1BindingExplanationAccessEnum: Format[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.AccessEnum] = JsonEnumFormat[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.AccessEnum]
	given fmtGoogleCloudPolicytroubleshooterV1AccessTuple: Format[Schema.GoogleCloudPolicytroubleshooterV1AccessTuple] = Json.format[Schema.GoogleCloudPolicytroubleshooterV1AccessTuple]
	given fmtGoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyResponse: Format[Schema.GoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyResponse] = Json.format[Schema.GoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyResponse]
	given fmtGoogleRpcStatus: Format[Schema.GoogleRpcStatus] = Json.format[Schema.GoogleRpcStatus]
	given fmtGoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyResponseAccessEnum: Format[Schema.GoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyResponse.AccessEnum] = JsonEnumFormat[Schema.GoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyResponse.AccessEnum]
	given fmtGoogleIamV1Binding: Format[Schema.GoogleIamV1Binding] = Json.format[Schema.GoogleIamV1Binding]
	given fmtGoogleIamV1AuditConfig: Format[Schema.GoogleIamV1AuditConfig] = Json.format[Schema.GoogleIamV1AuditConfig]
	given fmtGoogleIamV1AuditLogConfig: Format[Schema.GoogleIamV1AuditLogConfig] = Json.format[Schema.GoogleIamV1AuditLogConfig]
	given fmtGoogleIamV1AuditLogConfigLogTypeEnum: Format[Schema.GoogleIamV1AuditLogConfig.LogTypeEnum] = JsonEnumFormat[Schema.GoogleIamV1AuditLogConfig.LogTypeEnum]
	given fmtGoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyRequest: Format[Schema.GoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyRequest] = Json.format[Schema.GoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyRequest]
	given fmtGoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembershipRelevanceEnum: Format[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership.RelevanceEnum] = JsonEnumFormat[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership.RelevanceEnum]
	given fmtGoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembershipMembershipEnum: Format[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership.MembershipEnum] = JsonEnumFormat[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership.MembershipEnum]
}
