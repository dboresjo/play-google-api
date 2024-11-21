package com.boresjo.play.api.google.cloudbuild

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putSchemaInstallationStateStageEnum: Conversion[Schema.InstallationState.StageEnum, Option[Schema.InstallationState.StageEnum]] = (fun: Schema.InstallationState.StageEnum) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaStepRefResolverEnum: Conversion[Schema.StepRef.ResolverEnum, Option[Schema.StepRef.ResolverEnum]] = (fun: Schema.StepRef.ResolverEnum) => Option(fun)
		given putListSchemaParam: Conversion[List[Schema.Param], Option[List[Schema.Param]]] = (fun: List[Schema.Param]) => Option(fun)
		given putSchemaParamValue: Conversion[Schema.ParamValue, Option[Schema.ParamValue]] = (fun: Schema.ParamValue) => Option(fun)
		given putListSchemaWhenExpression: Conversion[List[Schema.WhenExpression], Option[List[Schema.WhenExpression]]] = (fun: List[Schema.WhenExpression]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaUserCredential: Conversion[Schema.UserCredential, Option[Schema.UserCredential]] = (fun: Schema.UserCredential) => Option(fun)
		given putListSchemaWorkspaceBinding: Conversion[List[Schema.WorkspaceBinding], Option[List[Schema.WorkspaceBinding]]] = (fun: List[Schema.WorkspaceBinding]) => Option(fun)
		given putListSchemaChildStatusReference: Conversion[List[Schema.ChildStatusReference], Option[List[Schema.ChildStatusReference]]] = (fun: List[Schema.ChildStatusReference]) => Option(fun)
		given putSchemaWorker: Conversion[Schema.Worker, Option[Schema.Worker]] = (fun: Schema.Worker) => Option(fun)
		given putSchemaProvenance: Conversion[Schema.Provenance, Option[Schema.Provenance]] = (fun: Schema.Provenance) => Option(fun)
		given putListSchemaGoogleDevtoolsCloudbuildV2Condition: Conversion[List[Schema.GoogleDevtoolsCloudbuildV2Condition], Option[List[Schema.GoogleDevtoolsCloudbuildV2Condition]]] = (fun: List[Schema.GoogleDevtoolsCloudbuildV2Condition]) => Option(fun)
		given putListSchemaPipelineRunResult: Conversion[List[Schema.PipelineRunResult], Option[List[Schema.PipelineRunResult]]] = (fun: List[Schema.PipelineRunResult]) => Option(fun)
		given putSchemaPipelineSpec: Conversion[Schema.PipelineSpec, Option[Schema.PipelineSpec]] = (fun: Schema.PipelineSpec) => Option(fun)
		given putSchemaSecurity: Conversion[Schema.Security, Option[Schema.Security]] = (fun: Schema.Security) => Option(fun)
		given putSchemaPipelineRunPipelineRunStatusEnum: Conversion[Schema.PipelineRun.PipelineRunStatusEnum, Option[Schema.PipelineRun.PipelineRunStatusEnum]] = (fun: Schema.PipelineRun.PipelineRunStatusEnum) => Option(fun)
		given putSchemaPipelineRef: Conversion[Schema.PipelineRef, Option[Schema.PipelineRef]] = (fun: Schema.PipelineRef) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaTimeoutFields: Conversion[Schema.TimeoutFields, Option[Schema.TimeoutFields]] = (fun: Schema.TimeoutFields) => Option(fun)
		given putListSchemaSkippedTask: Conversion[List[Schema.SkippedTask], Option[List[Schema.SkippedTask]]] = (fun: List[Schema.SkippedTask]) => Option(fun)
		given putSchemaPropertySpecTypeEnum: Conversion[Schema.PropertySpec.TypeEnum, Option[Schema.PropertySpec.TypeEnum]] = (fun: Schema.PropertySpec.TypeEnum) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaOAuthCredential: Conversion[Schema.OAuthCredential, Option[Schema.OAuthCredential]] = (fun: Schema.OAuthCredential) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putSchemaGoogleDevtoolsCloudbuildV2ServiceDirectoryConfig: Conversion[Schema.GoogleDevtoolsCloudbuildV2ServiceDirectoryConfig, Option[Schema.GoogleDevtoolsCloudbuildV2ServiceDirectoryConfig]] = (fun: Schema.GoogleDevtoolsCloudbuildV2ServiceDirectoryConfig) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putSchemaParamSpecTypeEnum: Conversion[Schema.ParamSpec.TypeEnum, Option[Schema.ParamSpec.TypeEnum]] = (fun: Schema.ParamSpec.TypeEnum) => Option(fun)
		given putSchemaEmptyDirVolumeSource: Conversion[Schema.EmptyDirVolumeSource, Option[Schema.EmptyDirVolumeSource]] = (fun: Schema.EmptyDirVolumeSource) => Option(fun)
		given putListSchemaConnection: Conversion[List[Schema.Connection], Option[List[Schema.Connection]]] = (fun: List[Schema.Connection]) => Option(fun)
		given putListSchemaEnvVar: Conversion[List[Schema.EnvVar], Option[List[Schema.EnvVar]]] = (fun: List[Schema.EnvVar]) => Option(fun)
		given putListSchemaVolumeMount: Conversion[List[Schema.VolumeMount], Option[List[Schema.VolumeMount]]] = (fun: List[Schema.VolumeMount]) => Option(fun)
		given putSchemaStepRef: Conversion[Schema.StepRef, Option[Schema.StepRef]] = (fun: Schema.StepRef) => Option(fun)
		given putSchemaSecurityContext: Conversion[Schema.SecurityContext, Option[Schema.SecurityContext]] = (fun: Schema.SecurityContext) => Option(fun)
		given putSchemaStepOnErrorEnum: Conversion[Schema.Step.OnErrorEnum, Option[Schema.Step.OnErrorEnum]] = (fun: Schema.Step.OnErrorEnum) => Option(fun)
		given putSchemaParamValueTypeEnum: Conversion[Schema.ParamValue.TypeEnum, Option[Schema.ParamValue.TypeEnum]] = (fun: Schema.ParamValue.TypeEnum) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
		given putListSchemaPipelineResult: Conversion[List[Schema.PipelineResult], Option[List[Schema.PipelineResult]]] = (fun: List[Schema.PipelineResult]) => Option(fun)
		given putListSchemaPipelineTask: Conversion[List[Schema.PipelineTask], Option[List[Schema.PipelineTask]]] = (fun: List[Schema.PipelineTask]) => Option(fun)
		given putListSchemaPipelineWorkspaceDeclaration: Conversion[List[Schema.PipelineWorkspaceDeclaration], Option[List[Schema.PipelineWorkspaceDeclaration]]] = (fun: List[Schema.PipelineWorkspaceDeclaration]) => Option(fun)
		given putListSchemaParamSpec: Conversion[List[Schema.ParamSpec], Option[List[Schema.ParamSpec]]] = (fun: List[Schema.ParamSpec]) => Option(fun)
		given putListSchemaCreateRepositoryRequest: Conversion[List[Schema.CreateRepositoryRequest], Option[List[Schema.CreateRepositoryRequest]]] = (fun: List[Schema.CreateRepositoryRequest]) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putSchemaTaskSpec: Conversion[Schema.TaskSpec, Option[Schema.TaskSpec]] = (fun: Schema.TaskSpec) => Option(fun)
		given putSchemaGoogleDevtoolsCloudbuildV2ConditionStatusEnum: Conversion[Schema.GoogleDevtoolsCloudbuildV2Condition.StatusEnum, Option[Schema.GoogleDevtoolsCloudbuildV2Condition.StatusEnum]] = (fun: Schema.GoogleDevtoolsCloudbuildV2Condition.StatusEnum) => Option(fun)
		given putSchemaGoogleDevtoolsCloudbuildV2ConditionSeverityEnum: Conversion[Schema.GoogleDevtoolsCloudbuildV2Condition.SeverityEnum, Option[Schema.GoogleDevtoolsCloudbuildV2Condition.SeverityEnum]] = (fun: Schema.GoogleDevtoolsCloudbuildV2Condition.SeverityEnum) => Option(fun)
		given putSchemaEmbeddedTask: Conversion[Schema.EmbeddedTask, Option[Schema.EmbeddedTask]] = (fun: Schema.EmbeddedTask) => Option(fun)
		given putListSchemaWorkspacePipelineTaskBinding: Conversion[List[Schema.WorkspacePipelineTaskBinding], Option[List[Schema.WorkspacePipelineTaskBinding]]] = (fun: List[Schema.WorkspacePipelineTaskBinding]) => Option(fun)
		given putSchemaTaskRef: Conversion[Schema.TaskRef, Option[Schema.TaskRef]] = (fun: Schema.TaskRef) => Option(fun)
		given putSchemaBitbucketCloudConfig: Conversion[Schema.BitbucketCloudConfig, Option[Schema.BitbucketCloudConfig]] = (fun: Schema.BitbucketCloudConfig) => Option(fun)
		given putSchemaInstallationState: Conversion[Schema.InstallationState, Option[Schema.InstallationState]] = (fun: Schema.InstallationState) => Option(fun)
		given putSchemaBitbucketDataCenterConfig: Conversion[Schema.BitbucketDataCenterConfig, Option[Schema.BitbucketDataCenterConfig]] = (fun: Schema.BitbucketDataCenterConfig) => Option(fun)
		given putSchemaGoogleDevtoolsCloudbuildV2GitHubEnterpriseConfig: Conversion[Schema.GoogleDevtoolsCloudbuildV2GitHubEnterpriseConfig, Option[Schema.GoogleDevtoolsCloudbuildV2GitHubEnterpriseConfig]] = (fun: Schema.GoogleDevtoolsCloudbuildV2GitHubEnterpriseConfig) => Option(fun)
		given putSchemaGitHubConfig: Conversion[Schema.GitHubConfig, Option[Schema.GitHubConfig]] = (fun: Schema.GitHubConfig) => Option(fun)
		given putSchemaGoogleDevtoolsCloudbuildV2GitLabConfig: Conversion[Schema.GoogleDevtoolsCloudbuildV2GitLabConfig, Option[Schema.GoogleDevtoolsCloudbuildV2GitLabConfig]] = (fun: Schema.GoogleDevtoolsCloudbuildV2GitLabConfig) => Option(fun)
		given putSchemaPipelineRefResolverEnum: Conversion[Schema.PipelineRef.ResolverEnum, Option[Schema.PipelineRef.ResolverEnum]] = (fun: Schema.PipelineRef.ResolverEnum) => Option(fun)
		given putListSchemaRepository: Conversion[List[Schema.Repository], Option[List[Schema.Repository]]] = (fun: List[Schema.Repository]) => Option(fun)
		given putSchemaPipelineResultTypeEnum: Conversion[Schema.PipelineResult.TypeEnum, Option[Schema.PipelineResult.TypeEnum]] = (fun: Schema.PipelineResult.TypeEnum) => Option(fun)
		given putSchemaResultValue: Conversion[Schema.ResultValue, Option[Schema.ResultValue]] = (fun: Schema.ResultValue) => Option(fun)
		given putSchemaTaskResultTypeEnum: Conversion[Schema.TaskResult.TypeEnum, Option[Schema.TaskResult.TypeEnum]] = (fun: Schema.TaskResult.TypeEnum) => Option(fun)
		given putMapStringSchemaPropertySpec: Conversion[Map[String, Schema.PropertySpec], Option[Map[String, Schema.PropertySpec]]] = (fun: Map[String, Schema.PropertySpec]) => Option(fun)
		given putSchemaTaskRefResolverEnum: Conversion[Schema.TaskRef.ResolverEnum, Option[Schema.TaskRef.ResolverEnum]] = (fun: Schema.TaskRef.ResolverEnum) => Option(fun)
		given putSchemaWhenExpressionExpressionOperatorEnum: Conversion[Schema.WhenExpression.ExpressionOperatorEnum, Option[Schema.WhenExpression.ExpressionOperatorEnum]] = (fun: Schema.WhenExpression.ExpressionOperatorEnum) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaRepository: Conversion[Schema.Repository, Option[Schema.Repository]] = (fun: Schema.Repository) => Option(fun)
		given putSchemaProvenanceEnabledEnum: Conversion[Schema.Provenance.EnabledEnum, Option[Schema.Provenance.EnabledEnum]] = (fun: Schema.Provenance.EnabledEnum) => Option(fun)
		given putSchemaProvenanceStorageEnum: Conversion[Schema.Provenance.StorageEnum, Option[Schema.Provenance.StorageEnum]] = (fun: Schema.Provenance.StorageEnum) => Option(fun)
		given putSchemaProvenanceRegionEnum: Conversion[Schema.Provenance.RegionEnum, Option[Schema.Provenance.RegionEnum]] = (fun: Schema.Provenance.RegionEnum) => Option(fun)
		given putSchemaCapabilities: Conversion[Schema.Capabilities, Option[Schema.Capabilities]] = (fun: Schema.Capabilities) => Option(fun)
		given putListSchemaWorkspaceDeclaration: Conversion[List[Schema.WorkspaceDeclaration], Option[List[Schema.WorkspaceDeclaration]]] = (fun: List[Schema.WorkspaceDeclaration]) => Option(fun)
		given putListSchemaTaskResult: Conversion[List[Schema.TaskResult], Option[List[Schema.TaskResult]]] = (fun: List[Schema.TaskResult]) => Option(fun)
		given putListSchemaVolumeSource: Conversion[List[Schema.VolumeSource], Option[List[Schema.VolumeSource]]] = (fun: List[Schema.VolumeSource]) => Option(fun)
		given putListSchemaTaskSpecManagedSidecarsEnum: Conversion[List[Schema.TaskSpec.ManagedSidecarsEnum], Option[List[Schema.TaskSpec.ManagedSidecarsEnum]]] = (fun: List[Schema.TaskSpec.ManagedSidecarsEnum]) => Option(fun)
		given putListSchemaSidecar: Conversion[List[Schema.Sidecar], Option[List[Schema.Sidecar]]] = (fun: List[Schema.Sidecar]) => Option(fun)
		given putSchemaStepTemplate: Conversion[Schema.StepTemplate, Option[Schema.StepTemplate]] = (fun: Schema.StepTemplate) => Option(fun)
		given putListSchemaStep: Conversion[List[Schema.Step], Option[List[Schema.Step]]] = (fun: List[Schema.Step]) => Option(fun)
		given putSchemaExecAction: Conversion[Schema.ExecAction, Option[Schema.ExecAction]] = (fun: Schema.ExecAction) => Option(fun)
		given putSchemaSecurityPrivilegeModeEnum: Conversion[Schema.Security.PrivilegeModeEnum, Option[Schema.Security.PrivilegeModeEnum]] = (fun: Schema.Security.PrivilegeModeEnum) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putSchemaChildStatusReferenceTypeEnum: Conversion[Schema.ChildStatusReference.TypeEnum, Option[Schema.ChildStatusReference.TypeEnum]] = (fun: Schema.ChildStatusReference.TypeEnum) => Option(fun)
		given putSchemaResultValueTypeEnum: Conversion[Schema.ResultValue.TypeEnum, Option[Schema.ResultValue.TypeEnum]] = (fun: Schema.ResultValue.TypeEnum) => Option(fun)
		given putSchemaProbe: Conversion[Schema.Probe, Option[Schema.Probe]] = (fun: Schema.Probe) => Option(fun)
		given putSchemaSecretVolumeSource: Conversion[Schema.SecretVolumeSource, Option[Schema.SecretVolumeSource]] = (fun: Schema.SecretVolumeSource) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}