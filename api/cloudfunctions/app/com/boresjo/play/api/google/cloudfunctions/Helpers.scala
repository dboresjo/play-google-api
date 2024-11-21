package com.boresjo.play.api.google.cloudfunctions

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
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
		given putSchemaBuildConfig: Conversion[Schema.BuildConfig, Option[Schema.BuildConfig]] = (fun: Schema.BuildConfig) => Option(fun)
		given putSchemaServiceConfig: Conversion[Schema.ServiceConfig, Option[Schema.ServiceConfig]] = (fun: Schema.ServiceConfig) => Option(fun)
		given putSchemaEventTrigger: Conversion[Schema.EventTrigger, Option[Schema.EventTrigger]] = (fun: Schema.EventTrigger) => Option(fun)
		given putSchemaFunctionStateEnum: Conversion[Schema.Function.StateEnum, Option[Schema.Function.StateEnum]] = (fun: Schema.Function.StateEnum) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putListSchemaGoogleCloudFunctionsV2StateMessage: Conversion[List[Schema.GoogleCloudFunctionsV2StateMessage], Option[List[Schema.GoogleCloudFunctionsV2StateMessage]]] = (fun: List[Schema.GoogleCloudFunctionsV2StateMessage]) => Option(fun)
		given putSchemaFunctionEnvironmentEnum: Conversion[Schema.Function.EnvironmentEnum, Option[Schema.Function.EnvironmentEnum]] = (fun: Schema.Function.EnvironmentEnum) => Option(fun)
		given putSchemaUpgradeInfo: Conversion[Schema.UpgradeInfo, Option[Schema.UpgradeInfo]] = (fun: Schema.UpgradeInfo) => Option(fun)
		given putSchemaAutomaticUpdatePolicy: Conversion[Schema.AutomaticUpdatePolicy, Option[Schema.AutomaticUpdatePolicy]] = (fun: Schema.AutomaticUpdatePolicy) => Option(fun)
		given putSchemaOnDeployUpdatePolicy: Conversion[Schema.OnDeployUpdatePolicy, Option[Schema.OnDeployUpdatePolicy]] = (fun: Schema.OnDeployUpdatePolicy) => Option(fun)
		given putSchemaSource: Conversion[Schema.Source, Option[Schema.Source]] = (fun: Schema.Source) => Option(fun)
		given putSchemaSourceProvenance: Conversion[Schema.SourceProvenance, Option[Schema.SourceProvenance]] = (fun: Schema.SourceProvenance) => Option(fun)
		given putSchemaBuildConfigDockerRegistryEnum: Conversion[Schema.BuildConfig.DockerRegistryEnum, Option[Schema.BuildConfig.DockerRegistryEnum]] = (fun: Schema.BuildConfig.DockerRegistryEnum) => Option(fun)
		given putSchemaStorageSource: Conversion[Schema.StorageSource, Option[Schema.StorageSource]] = (fun: Schema.StorageSource) => Option(fun)
		given putSchemaRepoSource: Conversion[Schema.RepoSource, Option[Schema.RepoSource]] = (fun: Schema.RepoSource) => Option(fun)
		given putSchemaServiceConfigVpcConnectorEgressSettingsEnum: Conversion[Schema.ServiceConfig.VpcConnectorEgressSettingsEnum, Option[Schema.ServiceConfig.VpcConnectorEgressSettingsEnum]] = (fun: Schema.ServiceConfig.VpcConnectorEgressSettingsEnum) => Option(fun)
		given putSchemaServiceConfigIngressSettingsEnum: Conversion[Schema.ServiceConfig.IngressSettingsEnum, Option[Schema.ServiceConfig.IngressSettingsEnum]] = (fun: Schema.ServiceConfig.IngressSettingsEnum) => Option(fun)
		given putListSchemaSecretEnvVar: Conversion[List[Schema.SecretEnvVar], Option[List[Schema.SecretEnvVar]]] = (fun: List[Schema.SecretEnvVar]) => Option(fun)
		given putListSchemaSecretVolume: Conversion[List[Schema.SecretVolume], Option[List[Schema.SecretVolume]]] = (fun: List[Schema.SecretVolume]) => Option(fun)
		given putSchemaServiceConfigSecurityLevelEnum: Conversion[Schema.ServiceConfig.SecurityLevelEnum, Option[Schema.ServiceConfig.SecurityLevelEnum]] = (fun: Schema.ServiceConfig.SecurityLevelEnum) => Option(fun)
		given putListSchemaSecretVersion: Conversion[List[Schema.SecretVersion], Option[List[Schema.SecretVersion]]] = (fun: List[Schema.SecretVersion]) => Option(fun)
		given putListSchemaEventFilter: Conversion[List[Schema.EventFilter], Option[List[Schema.EventFilter]]] = (fun: List[Schema.EventFilter]) => Option(fun)
		given putSchemaEventTriggerRetryPolicyEnum: Conversion[Schema.EventTrigger.RetryPolicyEnum, Option[Schema.EventTrigger.RetryPolicyEnum]] = (fun: Schema.EventTrigger.RetryPolicyEnum) => Option(fun)
		given putSchemaGoogleCloudFunctionsV2StateMessageSeverityEnum: Conversion[Schema.GoogleCloudFunctionsV2StateMessage.SeverityEnum, Option[Schema.GoogleCloudFunctionsV2StateMessage.SeverityEnum]] = (fun: Schema.GoogleCloudFunctionsV2StateMessage.SeverityEnum) => Option(fun)
		given putSchemaUpgradeInfoUpgradeStateEnum: Conversion[Schema.UpgradeInfo.UpgradeStateEnum, Option[Schema.UpgradeInfo.UpgradeStateEnum]] = (fun: Schema.UpgradeInfo.UpgradeStateEnum) => Option(fun)
		given putListSchemaFunction: Conversion[List[Schema.Function], Option[List[Schema.Function]]] = (fun: List[Schema.Function]) => Option(fun)
		given putSchemaGenerateUploadUrlRequestEnvironmentEnum: Conversion[Schema.GenerateUploadUrlRequest.EnvironmentEnum, Option[Schema.GenerateUploadUrlRequest.EnvironmentEnum]] = (fun: Schema.GenerateUploadUrlRequest.EnvironmentEnum) => Option(fun)
		given putListSchemaRuntime: Conversion[List[Schema.Runtime], Option[List[Schema.Runtime]]] = (fun: List[Schema.Runtime]) => Option(fun)
		given putSchemaRuntimeStageEnum: Conversion[Schema.Runtime.StageEnum, Option[Schema.Runtime.StageEnum]] = (fun: Schema.Runtime.StageEnum) => Option(fun)
		given putSchemaRuntimeEnvironmentEnum: Conversion[Schema.Runtime.EnvironmentEnum, Option[Schema.Runtime.EnvironmentEnum]] = (fun: Schema.Runtime.EnvironmentEnum) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putSchemaOperationMetadataV1TypeEnum: Conversion[Schema.OperationMetadataV1.TypeEnum, Option[Schema.OperationMetadataV1.TypeEnum]] = (fun: Schema.OperationMetadataV1.TypeEnum) => Option(fun)
		given putListSchemaGoogleCloudFunctionsV2Stage: Conversion[List[Schema.GoogleCloudFunctionsV2Stage], Option[List[Schema.GoogleCloudFunctionsV2Stage]]] = (fun: List[Schema.GoogleCloudFunctionsV2Stage]) => Option(fun)
		given putSchemaGoogleCloudFunctionsV2OperationMetadataOperationTypeEnum: Conversion[Schema.GoogleCloudFunctionsV2OperationMetadata.OperationTypeEnum, Option[Schema.GoogleCloudFunctionsV2OperationMetadata.OperationTypeEnum]] = (fun: Schema.GoogleCloudFunctionsV2OperationMetadata.OperationTypeEnum) => Option(fun)
		given putSchemaGoogleCloudFunctionsV2StageNameEnum: Conversion[Schema.GoogleCloudFunctionsV2Stage.NameEnum, Option[Schema.GoogleCloudFunctionsV2Stage.NameEnum]] = (fun: Schema.GoogleCloudFunctionsV2Stage.NameEnum) => Option(fun)
		given putSchemaGoogleCloudFunctionsV2StageStateEnum: Conversion[Schema.GoogleCloudFunctionsV2Stage.StateEnum, Option[Schema.GoogleCloudFunctionsV2Stage.StateEnum]] = (fun: Schema.GoogleCloudFunctionsV2Stage.StateEnum) => Option(fun)
		given putListSchemaGoogleCloudFunctionsV2LocationMetadataEnvironmentsEnum: Conversion[List[Schema.GoogleCloudFunctionsV2LocationMetadata.EnvironmentsEnum], Option[List[Schema.GoogleCloudFunctionsV2LocationMetadata.EnvironmentsEnum]]] = (fun: List[Schema.GoogleCloudFunctionsV2LocationMetadata.EnvironmentsEnum]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
