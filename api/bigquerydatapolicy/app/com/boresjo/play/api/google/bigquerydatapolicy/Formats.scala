package com.boresjo.play.api.google.bigquerydatapolicy

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtDataPolicy: Format[Schema.DataPolicy] = Json.format[Schema.DataPolicy]
	given fmtDataMaskingPolicy: Format[Schema.DataMaskingPolicy] = Json.format[Schema.DataMaskingPolicy]
	given fmtDataPolicyDataPolicyTypeEnum: Format[Schema.DataPolicy.DataPolicyTypeEnum] = JsonEnumFormat[Schema.DataPolicy.DataPolicyTypeEnum]
	given fmtDataMaskingPolicyPredefinedExpressionEnum: Format[Schema.DataMaskingPolicy.PredefinedExpressionEnum] = JsonEnumFormat[Schema.DataMaskingPolicy.PredefinedExpressionEnum]
	given fmtRenameDataPolicyRequest: Format[Schema.RenameDataPolicyRequest] = Json.format[Schema.RenameDataPolicyRequest]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtListDataPoliciesResponse: Format[Schema.ListDataPoliciesResponse] = Json.format[Schema.ListDataPoliciesResponse]
	given fmtGetIamPolicyRequest: Format[Schema.GetIamPolicyRequest] = Json.format[Schema.GetIamPolicyRequest]
	given fmtGetPolicyOptions: Format[Schema.GetPolicyOptions] = Json.format[Schema.GetPolicyOptions]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtBinding: Format[Schema.Binding] = Json.format[Schema.Binding]
	given fmtAuditConfig: Format[Schema.AuditConfig] = Json.format[Schema.AuditConfig]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtAuditLogConfig: Format[Schema.AuditLogConfig] = Json.format[Schema.AuditLogConfig]
	given fmtAuditLogConfigLogTypeEnum: Format[Schema.AuditLogConfig.LogTypeEnum] = JsonEnumFormat[Schema.AuditLogConfig.LogTypeEnum]
	given fmtSetIamPolicyRequest: Format[Schema.SetIamPolicyRequest] = Json.format[Schema.SetIamPolicyRequest]
	given fmtTestIamPermissionsRequest: Format[Schema.TestIamPermissionsRequest] = Json.format[Schema.TestIamPermissionsRequest]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
}
