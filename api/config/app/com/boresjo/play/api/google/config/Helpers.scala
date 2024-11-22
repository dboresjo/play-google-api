package com.boresjo.play.api.google.config

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

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
		given putListSchemaDeployment: Conversion[List[Schema.Deployment], Option[List[Schema.Deployment]]] = (fun: List[Schema.Deployment]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaTerraformBlueprint: Conversion[Schema.TerraformBlueprint, Option[Schema.TerraformBlueprint]] = (fun: Schema.TerraformBlueprint) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaDeploymentStateEnum: Conversion[Schema.Deployment.StateEnum, Option[Schema.Deployment.StateEnum]] = (fun: Schema.Deployment.StateEnum) => Option(fun)
		given putSchemaDeploymentErrorCodeEnum: Conversion[Schema.Deployment.ErrorCodeEnum, Option[Schema.Deployment.ErrorCodeEnum]] = (fun: Schema.Deployment.ErrorCodeEnum) => Option(fun)
		given putSchemaApplyResults: Conversion[Schema.ApplyResults, Option[Schema.ApplyResults]] = (fun: Schema.ApplyResults) => Option(fun)
		given putListSchemaTerraformError: Conversion[List[Schema.TerraformError], Option[List[Schema.TerraformError]]] = (fun: List[Schema.TerraformError]) => Option(fun)
		given putSchemaDeploymentLockStateEnum: Conversion[Schema.Deployment.LockStateEnum, Option[Schema.Deployment.LockStateEnum]] = (fun: Schema.Deployment.LockStateEnum) => Option(fun)
		given putSchemaDeploymentQuotaValidationEnum: Conversion[Schema.Deployment.QuotaValidationEnum, Option[Schema.Deployment.QuotaValidationEnum]] = (fun: Schema.Deployment.QuotaValidationEnum) => Option(fun)
		given putSchemaGitSource: Conversion[Schema.GitSource, Option[Schema.GitSource]] = (fun: Schema.GitSource) => Option(fun)
		given putMapStringSchemaTerraformVariable: Conversion[Map[String, Schema.TerraformVariable], Option[Map[String, Schema.TerraformVariable]]] = (fun: Map[String, Schema.TerraformVariable]) => Option(fun)
		given putJsValue: Conversion[JsValue, Option[JsValue]] = (fun: JsValue) => Option(fun)
		given putMapStringSchemaTerraformOutput: Conversion[Map[String, Schema.TerraformOutput], Option[Map[String, Schema.TerraformOutput]]] = (fun: Map[String, Schema.TerraformOutput]) => Option(fun)
		given putListSchemaRevision: Conversion[List[Schema.Revision], Option[List[Schema.Revision]]] = (fun: List[Schema.Revision]) => Option(fun)
		given putSchemaRevisionActionEnum: Conversion[Schema.Revision.ActionEnum, Option[Schema.Revision.ActionEnum]] = (fun: Schema.Revision.ActionEnum) => Option(fun)
		given putSchemaRevisionStateEnum: Conversion[Schema.Revision.StateEnum, Option[Schema.Revision.StateEnum]] = (fun: Schema.Revision.StateEnum) => Option(fun)
		given putSchemaRevisionErrorCodeEnum: Conversion[Schema.Revision.ErrorCodeEnum, Option[Schema.Revision.ErrorCodeEnum]] = (fun: Schema.Revision.ErrorCodeEnum) => Option(fun)
		given putSchemaRevisionQuotaValidationEnum: Conversion[Schema.Revision.QuotaValidationEnum, Option[Schema.Revision.QuotaValidationEnum]] = (fun: Schema.Revision.QuotaValidationEnum) => Option(fun)
		given putSchemaResourceTerraformInfo: Conversion[Schema.ResourceTerraformInfo, Option[Schema.ResourceTerraformInfo]] = (fun: Schema.ResourceTerraformInfo) => Option(fun)
		given putMapStringSchemaResourceCAIInfo: Conversion[Map[String, Schema.ResourceCAIInfo], Option[Map[String, Schema.ResourceCAIInfo]]] = (fun: Map[String, Schema.ResourceCAIInfo]) => Option(fun)
		given putSchemaResourceIntentEnum: Conversion[Schema.Resource.IntentEnum, Option[Schema.Resource.IntentEnum]] = (fun: Schema.Resource.IntentEnum) => Option(fun)
		given putSchemaResourceStateEnum: Conversion[Schema.Resource.StateEnum, Option[Schema.Resource.StateEnum]] = (fun: Schema.Resource.StateEnum) => Option(fun)
		given putListSchemaResource: Conversion[List[Schema.Resource], Option[List[Schema.Resource]]] = (fun: List[Schema.Resource]) => Option(fun)
		given putSchemaPreviewStateEnum: Conversion[Schema.Preview.StateEnum, Option[Schema.Preview.StateEnum]] = (fun: Schema.Preview.StateEnum) => Option(fun)
		given putSchemaPreviewPreviewModeEnum: Conversion[Schema.Preview.PreviewModeEnum, Option[Schema.Preview.PreviewModeEnum]] = (fun: Schema.Preview.PreviewModeEnum) => Option(fun)
		given putSchemaPreviewErrorCodeEnum: Conversion[Schema.Preview.ErrorCodeEnum, Option[Schema.Preview.ErrorCodeEnum]] = (fun: Schema.Preview.ErrorCodeEnum) => Option(fun)
		given putSchemaPreviewArtifacts: Conversion[Schema.PreviewArtifacts, Option[Schema.PreviewArtifacts]] = (fun: Schema.PreviewArtifacts) => Option(fun)
		given putListSchemaPreview: Conversion[List[Schema.Preview], Option[List[Schema.Preview]]] = (fun: List[Schema.Preview]) => Option(fun)
		given putSchemaPreviewResult: Conversion[Schema.PreviewResult, Option[Schema.PreviewResult]] = (fun: Schema.PreviewResult) => Option(fun)
		given putListSchemaTerraformVersion: Conversion[List[Schema.TerraformVersion], Option[List[Schema.TerraformVersion]]] = (fun: List[Schema.TerraformVersion]) => Option(fun)
		given putSchemaTerraformVersionStateEnum: Conversion[Schema.TerraformVersion.StateEnum, Option[Schema.TerraformVersion.StateEnum]] = (fun: Schema.TerraformVersion.StateEnum) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
		given putSchemaDeploymentOperationMetadata: Conversion[Schema.DeploymentOperationMetadata, Option[Schema.DeploymentOperationMetadata]] = (fun: Schema.DeploymentOperationMetadata) => Option(fun)
		given putSchemaPreviewOperationMetadata: Conversion[Schema.PreviewOperationMetadata, Option[Schema.PreviewOperationMetadata]] = (fun: Schema.PreviewOperationMetadata) => Option(fun)
		given putSchemaDeploymentOperationMetadataStepEnum: Conversion[Schema.DeploymentOperationMetadata.StepEnum, Option[Schema.DeploymentOperationMetadata.StepEnum]] = (fun: Schema.DeploymentOperationMetadata.StepEnum) => Option(fun)
		given putSchemaPreviewOperationMetadataStepEnum: Conversion[Schema.PreviewOperationMetadata.StepEnum, Option[Schema.PreviewOperationMetadata.StepEnum]] = (fun: Schema.PreviewOperationMetadata.StepEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
