package com.boresjo.play.api.google.config

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtListDeploymentsResponse: Format[Schema.ListDeploymentsResponse] = Json.format[Schema.ListDeploymentsResponse]
	given fmtDeployment: Format[Schema.Deployment] = Json.format[Schema.Deployment]
	given fmtTerraformBlueprint: Format[Schema.TerraformBlueprint] = Json.format[Schema.TerraformBlueprint]
	given fmtDeploymentStateEnum: Format[Schema.Deployment.StateEnum] = JsonEnumFormat[Schema.Deployment.StateEnum]
	given fmtDeploymentErrorCodeEnum: Format[Schema.Deployment.ErrorCodeEnum] = JsonEnumFormat[Schema.Deployment.ErrorCodeEnum]
	given fmtApplyResults: Format[Schema.ApplyResults] = Json.format[Schema.ApplyResults]
	given fmtTerraformError: Format[Schema.TerraformError] = Json.format[Schema.TerraformError]
	given fmtDeploymentLockStateEnum: Format[Schema.Deployment.LockStateEnum] = JsonEnumFormat[Schema.Deployment.LockStateEnum]
	given fmtDeploymentQuotaValidationEnum: Format[Schema.Deployment.QuotaValidationEnum] = JsonEnumFormat[Schema.Deployment.QuotaValidationEnum]
	given fmtGitSource: Format[Schema.GitSource] = Json.format[Schema.GitSource]
	given fmtTerraformVariable: Format[Schema.TerraformVariable] = Json.format[Schema.TerraformVariable]
	given fmtTerraformOutput: Format[Schema.TerraformOutput] = Json.format[Schema.TerraformOutput]
	given fmtListRevisionsResponse: Format[Schema.ListRevisionsResponse] = Json.format[Schema.ListRevisionsResponse]
	given fmtRevision: Format[Schema.Revision] = Json.format[Schema.Revision]
	given fmtRevisionActionEnum: Format[Schema.Revision.ActionEnum] = JsonEnumFormat[Schema.Revision.ActionEnum]
	given fmtRevisionStateEnum: Format[Schema.Revision.StateEnum] = JsonEnumFormat[Schema.Revision.StateEnum]
	given fmtRevisionErrorCodeEnum: Format[Schema.Revision.ErrorCodeEnum] = JsonEnumFormat[Schema.Revision.ErrorCodeEnum]
	given fmtRevisionQuotaValidationEnum: Format[Schema.Revision.QuotaValidationEnum] = JsonEnumFormat[Schema.Revision.QuotaValidationEnum]
	given fmtResource: Format[Schema.Resource] = Json.format[Schema.Resource]
	given fmtResourceTerraformInfo: Format[Schema.ResourceTerraformInfo] = Json.format[Schema.ResourceTerraformInfo]
	given fmtResourceCAIInfo: Format[Schema.ResourceCAIInfo] = Json.format[Schema.ResourceCAIInfo]
	given fmtResourceIntentEnum: Format[Schema.Resource.IntentEnum] = JsonEnumFormat[Schema.Resource.IntentEnum]
	given fmtResourceStateEnum: Format[Schema.Resource.StateEnum] = JsonEnumFormat[Schema.Resource.StateEnum]
	given fmtListResourcesResponse: Format[Schema.ListResourcesResponse] = Json.format[Schema.ListResourcesResponse]
	given fmtExportDeploymentStatefileRequest: Format[Schema.ExportDeploymentStatefileRequest] = Json.format[Schema.ExportDeploymentStatefileRequest]
	given fmtStatefile: Format[Schema.Statefile] = Json.format[Schema.Statefile]
	given fmtExportRevisionStatefileRequest: Format[Schema.ExportRevisionStatefileRequest] = Json.format[Schema.ExportRevisionStatefileRequest]
	given fmtImportStatefileRequest: Format[Schema.ImportStatefileRequest] = Json.format[Schema.ImportStatefileRequest]
	given fmtDeleteStatefileRequest: Format[Schema.DeleteStatefileRequest] = Json.format[Schema.DeleteStatefileRequest]
	given fmtLockDeploymentRequest: Format[Schema.LockDeploymentRequest] = Json.format[Schema.LockDeploymentRequest]
	given fmtUnlockDeploymentRequest: Format[Schema.UnlockDeploymentRequest] = Json.format[Schema.UnlockDeploymentRequest]
	given fmtLockInfo: Format[Schema.LockInfo] = Json.format[Schema.LockInfo]
	given fmtPreview: Format[Schema.Preview] = Json.format[Schema.Preview]
	given fmtPreviewStateEnum: Format[Schema.Preview.StateEnum] = JsonEnumFormat[Schema.Preview.StateEnum]
	given fmtPreviewPreviewModeEnum: Format[Schema.Preview.PreviewModeEnum] = JsonEnumFormat[Schema.Preview.PreviewModeEnum]
	given fmtPreviewErrorCodeEnum: Format[Schema.Preview.ErrorCodeEnum] = JsonEnumFormat[Schema.Preview.ErrorCodeEnum]
	given fmtPreviewArtifacts: Format[Schema.PreviewArtifacts] = Json.format[Schema.PreviewArtifacts]
	given fmtListPreviewsResponse: Format[Schema.ListPreviewsResponse] = Json.format[Schema.ListPreviewsResponse]
	given fmtExportPreviewResultRequest: Format[Schema.ExportPreviewResultRequest] = Json.format[Schema.ExportPreviewResultRequest]
	given fmtExportPreviewResultResponse: Format[Schema.ExportPreviewResultResponse] = Json.format[Schema.ExportPreviewResultResponse]
	given fmtPreviewResult: Format[Schema.PreviewResult] = Json.format[Schema.PreviewResult]
	given fmtListTerraformVersionsResponse: Format[Schema.ListTerraformVersionsResponse] = Json.format[Schema.ListTerraformVersionsResponse]
	given fmtTerraformVersion: Format[Schema.TerraformVersion] = Json.format[Schema.TerraformVersion]
	given fmtTerraformVersionStateEnum: Format[Schema.TerraformVersion.StateEnum] = JsonEnumFormat[Schema.TerraformVersion.StateEnum]
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtSetIamPolicyRequest: Format[Schema.SetIamPolicyRequest] = Json.format[Schema.SetIamPolicyRequest]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtBinding: Format[Schema.Binding] = Json.format[Schema.Binding]
	given fmtAuditConfig: Format[Schema.AuditConfig] = Json.format[Schema.AuditConfig]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtAuditLogConfig: Format[Schema.AuditLogConfig] = Json.format[Schema.AuditLogConfig]
	given fmtAuditLogConfigLogTypeEnum: Format[Schema.AuditLogConfig.LogTypeEnum] = JsonEnumFormat[Schema.AuditLogConfig.LogTypeEnum]
	given fmtTestIamPermissionsRequest: Format[Schema.TestIamPermissionsRequest] = Json.format[Schema.TestIamPermissionsRequest]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
	given fmtDeploymentOperationMetadata: Format[Schema.DeploymentOperationMetadata] = Json.format[Schema.DeploymentOperationMetadata]
	given fmtPreviewOperationMetadata: Format[Schema.PreviewOperationMetadata] = Json.format[Schema.PreviewOperationMetadata]
	given fmtDeploymentOperationMetadataStepEnum: Format[Schema.DeploymentOperationMetadata.StepEnum] = JsonEnumFormat[Schema.DeploymentOperationMetadata.StepEnum]
	given fmtPreviewOperationMetadataStepEnum: Format[Schema.PreviewOperationMetadata.StepEnum] = JsonEnumFormat[Schema.PreviewOperationMetadata.StepEnum]
}