package com.boresjo.play.api.google.composer

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
		given putSchemaEnvironmentConfig: Conversion[Schema.EnvironmentConfig, Option[Schema.EnvironmentConfig]] = (fun: Schema.EnvironmentConfig) => Option(fun)
		given putSchemaEnvironmentStateEnum: Conversion[Schema.Environment.StateEnum, Option[Schema.Environment.StateEnum]] = (fun: Schema.Environment.StateEnum) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaStorageConfig: Conversion[Schema.StorageConfig, Option[Schema.StorageConfig]] = (fun: Schema.StorageConfig) => Option(fun)
		given putSchemaSoftwareConfig: Conversion[Schema.SoftwareConfig, Option[Schema.SoftwareConfig]] = (fun: Schema.SoftwareConfig) => Option(fun)
		given putSchemaNodeConfig: Conversion[Schema.NodeConfig, Option[Schema.NodeConfig]] = (fun: Schema.NodeConfig) => Option(fun)
		given putSchemaPrivateEnvironmentConfig: Conversion[Schema.PrivateEnvironmentConfig, Option[Schema.PrivateEnvironmentConfig]] = (fun: Schema.PrivateEnvironmentConfig) => Option(fun)
		given putSchemaWebServerNetworkAccessControl: Conversion[Schema.WebServerNetworkAccessControl, Option[Schema.WebServerNetworkAccessControl]] = (fun: Schema.WebServerNetworkAccessControl) => Option(fun)
		given putSchemaDatabaseConfig: Conversion[Schema.DatabaseConfig, Option[Schema.DatabaseConfig]] = (fun: Schema.DatabaseConfig) => Option(fun)
		given putSchemaWebServerConfig: Conversion[Schema.WebServerConfig, Option[Schema.WebServerConfig]] = (fun: Schema.WebServerConfig) => Option(fun)
		given putSchemaEncryptionConfig: Conversion[Schema.EncryptionConfig, Option[Schema.EncryptionConfig]] = (fun: Schema.EncryptionConfig) => Option(fun)
		given putSchemaMaintenanceWindow: Conversion[Schema.MaintenanceWindow, Option[Schema.MaintenanceWindow]] = (fun: Schema.MaintenanceWindow) => Option(fun)
		given putSchemaWorkloadsConfig: Conversion[Schema.WorkloadsConfig, Option[Schema.WorkloadsConfig]] = (fun: Schema.WorkloadsConfig) => Option(fun)
		given putSchemaEnvironmentConfigEnvironmentSizeEnum: Conversion[Schema.EnvironmentConfig.EnvironmentSizeEnum, Option[Schema.EnvironmentConfig.EnvironmentSizeEnum]] = (fun: Schema.EnvironmentConfig.EnvironmentSizeEnum) => Option(fun)
		given putSchemaMasterAuthorizedNetworksConfig: Conversion[Schema.MasterAuthorizedNetworksConfig, Option[Schema.MasterAuthorizedNetworksConfig]] = (fun: Schema.MasterAuthorizedNetworksConfig) => Option(fun)
		given putSchemaRecoveryConfig: Conversion[Schema.RecoveryConfig, Option[Schema.RecoveryConfig]] = (fun: Schema.RecoveryConfig) => Option(fun)
		given putSchemaEnvironmentConfigResilienceModeEnum: Conversion[Schema.EnvironmentConfig.ResilienceModeEnum, Option[Schema.EnvironmentConfig.ResilienceModeEnum]] = (fun: Schema.EnvironmentConfig.ResilienceModeEnum) => Option(fun)
		given putSchemaDataRetentionConfig: Conversion[Schema.DataRetentionConfig, Option[Schema.DataRetentionConfig]] = (fun: Schema.DataRetentionConfig) => Option(fun)
		given putSchemaCloudDataLineageIntegration: Conversion[Schema.CloudDataLineageIntegration, Option[Schema.CloudDataLineageIntegration]] = (fun: Schema.CloudDataLineageIntegration) => Option(fun)
		given putSchemaSoftwareConfigWebServerPluginsModeEnum: Conversion[Schema.SoftwareConfig.WebServerPluginsModeEnum, Option[Schema.SoftwareConfig.WebServerPluginsModeEnum]] = (fun: Schema.SoftwareConfig.WebServerPluginsModeEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaIPAllocationPolicy: Conversion[Schema.IPAllocationPolicy, Option[Schema.IPAllocationPolicy]] = (fun: Schema.IPAllocationPolicy) => Option(fun)
		given putSchemaPrivateClusterConfig: Conversion[Schema.PrivateClusterConfig, Option[Schema.PrivateClusterConfig]] = (fun: Schema.PrivateClusterConfig) => Option(fun)
		given putSchemaNetworkingConfig: Conversion[Schema.NetworkingConfig, Option[Schema.NetworkingConfig]] = (fun: Schema.NetworkingConfig) => Option(fun)
		given putSchemaNetworkingConfigConnectionTypeEnum: Conversion[Schema.NetworkingConfig.ConnectionTypeEnum, Option[Schema.NetworkingConfig.ConnectionTypeEnum]] = (fun: Schema.NetworkingConfig.ConnectionTypeEnum) => Option(fun)
		given putListSchemaAllowedIpRange: Conversion[List[Schema.AllowedIpRange], Option[List[Schema.AllowedIpRange]]] = (fun: List[Schema.AllowedIpRange]) => Option(fun)
		given putSchemaSchedulerResource: Conversion[Schema.SchedulerResource, Option[Schema.SchedulerResource]] = (fun: Schema.SchedulerResource) => Option(fun)
		given putSchemaWebServerResource: Conversion[Schema.WebServerResource, Option[Schema.WebServerResource]] = (fun: Schema.WebServerResource) => Option(fun)
		given putSchemaWorkerResource: Conversion[Schema.WorkerResource, Option[Schema.WorkerResource]] = (fun: Schema.WorkerResource) => Option(fun)
		given putSchemaTriggererResource: Conversion[Schema.TriggererResource, Option[Schema.TriggererResource]] = (fun: Schema.TriggererResource) => Option(fun)
		given putSchemaDagProcessorResource: Conversion[Schema.DagProcessorResource, Option[Schema.DagProcessorResource]] = (fun: Schema.DagProcessorResource) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListSchemaCidrBlock: Conversion[List[Schema.CidrBlock], Option[List[Schema.CidrBlock]]] = (fun: List[Schema.CidrBlock]) => Option(fun)
		given putSchemaScheduledSnapshotsConfig: Conversion[Schema.ScheduledSnapshotsConfig, Option[Schema.ScheduledSnapshotsConfig]] = (fun: Schema.ScheduledSnapshotsConfig) => Option(fun)
		given putSchemaAirflowMetadataRetentionPolicyConfig: Conversion[Schema.AirflowMetadataRetentionPolicyConfig, Option[Schema.AirflowMetadataRetentionPolicyConfig]] = (fun: Schema.AirflowMetadataRetentionPolicyConfig) => Option(fun)
		given putSchemaTaskLogsRetentionConfig: Conversion[Schema.TaskLogsRetentionConfig, Option[Schema.TaskLogsRetentionConfig]] = (fun: Schema.TaskLogsRetentionConfig) => Option(fun)
		given putSchemaAirflowMetadataRetentionPolicyConfigRetentionModeEnum: Conversion[Schema.AirflowMetadataRetentionPolicyConfig.RetentionModeEnum, Option[Schema.AirflowMetadataRetentionPolicyConfig.RetentionModeEnum]] = (fun: Schema.AirflowMetadataRetentionPolicyConfig.RetentionModeEnum) => Option(fun)
		given putSchemaTaskLogsRetentionConfigStorageModeEnum: Conversion[Schema.TaskLogsRetentionConfig.StorageModeEnum, Option[Schema.TaskLogsRetentionConfig.StorageModeEnum]] = (fun: Schema.TaskLogsRetentionConfig.StorageModeEnum) => Option(fun)
		given putListSchemaEnvironment: Conversion[List[Schema.Environment], Option[List[Schema.Environment]]] = (fun: List[Schema.Environment]) => Option(fun)
		given putListSchemaLine: Conversion[List[Schema.Line], Option[List[Schema.Line]]] = (fun: List[Schema.Line]) => Option(fun)
		given putSchemaExitInfo: Conversion[Schema.ExitInfo, Option[Schema.ExitInfo]] = (fun: Schema.ExitInfo) => Option(fun)
		given putListSchemaComposerWorkload: Conversion[List[Schema.ComposerWorkload], Option[List[Schema.ComposerWorkload]]] = (fun: List[Schema.ComposerWorkload]) => Option(fun)
		given putSchemaComposerWorkloadTypeEnum: Conversion[Schema.ComposerWorkload.TypeEnum, Option[Schema.ComposerWorkload.TypeEnum]] = (fun: Schema.ComposerWorkload.TypeEnum) => Option(fun)
		given putSchemaComposerWorkloadStatus: Conversion[Schema.ComposerWorkloadStatus, Option[Schema.ComposerWorkloadStatus]] = (fun: Schema.ComposerWorkloadStatus) => Option(fun)
		given putSchemaComposerWorkloadStatusStateEnum: Conversion[Schema.ComposerWorkloadStatus.StateEnum, Option[Schema.ComposerWorkloadStatus.StateEnum]] = (fun: Schema.ComposerWorkloadStatus.StateEnum) => Option(fun)
		given putListSchemaUserWorkloadsSecret: Conversion[List[Schema.UserWorkloadsSecret], Option[List[Schema.UserWorkloadsSecret]]] = (fun: List[Schema.UserWorkloadsSecret]) => Option(fun)
		given putListSchemaUserWorkloadsConfigMap: Conversion[List[Schema.UserWorkloadsConfigMap], Option[List[Schema.UserWorkloadsConfigMap]]] = (fun: List[Schema.UserWorkloadsConfigMap]) => Option(fun)
		given putListSchemaImageVersion: Conversion[List[Schema.ImageVersion], Option[List[Schema.ImageVersion]]] = (fun: List[Schema.ImageVersion]) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
		given putSchemaOperationMetadataStateEnum: Conversion[Schema.OperationMetadata.StateEnum, Option[Schema.OperationMetadata.StateEnum]] = (fun: Schema.OperationMetadata.StateEnum) => Option(fun)
		given putSchemaOperationMetadataOperationTypeEnum: Conversion[Schema.OperationMetadata.OperationTypeEnum, Option[Schema.OperationMetadata.OperationTypeEnum]] = (fun: Schema.OperationMetadata.OperationTypeEnum) => Option(fun)
		given putSchemaCheckUpgradeResponseContainsPypiModulesConflictEnum: Conversion[Schema.CheckUpgradeResponse.ContainsPypiModulesConflictEnum, Option[Schema.CheckUpgradeResponse.ContainsPypiModulesConflictEnum]] = (fun: Schema.CheckUpgradeResponse.ContainsPypiModulesConflictEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
