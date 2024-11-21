package com.boresjo.play.api.google.composer

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
	given fmtEnvironment: Format[Schema.Environment] = Json.format[Schema.Environment]
	given fmtEnvironmentConfig: Format[Schema.EnvironmentConfig] = Json.format[Schema.EnvironmentConfig]
	given fmtEnvironmentStateEnum: Format[Schema.Environment.StateEnum] = JsonEnumFormat[Schema.Environment.StateEnum]
	given fmtStorageConfig: Format[Schema.StorageConfig] = Json.format[Schema.StorageConfig]
	given fmtSoftwareConfig: Format[Schema.SoftwareConfig] = Json.format[Schema.SoftwareConfig]
	given fmtNodeConfig: Format[Schema.NodeConfig] = Json.format[Schema.NodeConfig]
	given fmtPrivateEnvironmentConfig: Format[Schema.PrivateEnvironmentConfig] = Json.format[Schema.PrivateEnvironmentConfig]
	given fmtWebServerNetworkAccessControl: Format[Schema.WebServerNetworkAccessControl] = Json.format[Schema.WebServerNetworkAccessControl]
	given fmtDatabaseConfig: Format[Schema.DatabaseConfig] = Json.format[Schema.DatabaseConfig]
	given fmtWebServerConfig: Format[Schema.WebServerConfig] = Json.format[Schema.WebServerConfig]
	given fmtEncryptionConfig: Format[Schema.EncryptionConfig] = Json.format[Schema.EncryptionConfig]
	given fmtMaintenanceWindow: Format[Schema.MaintenanceWindow] = Json.format[Schema.MaintenanceWindow]
	given fmtWorkloadsConfig: Format[Schema.WorkloadsConfig] = Json.format[Schema.WorkloadsConfig]
	given fmtEnvironmentConfigEnvironmentSizeEnum: Format[Schema.EnvironmentConfig.EnvironmentSizeEnum] = JsonEnumFormat[Schema.EnvironmentConfig.EnvironmentSizeEnum]
	given fmtMasterAuthorizedNetworksConfig: Format[Schema.MasterAuthorizedNetworksConfig] = Json.format[Schema.MasterAuthorizedNetworksConfig]
	given fmtRecoveryConfig: Format[Schema.RecoveryConfig] = Json.format[Schema.RecoveryConfig]
	given fmtEnvironmentConfigResilienceModeEnum: Format[Schema.EnvironmentConfig.ResilienceModeEnum] = JsonEnumFormat[Schema.EnvironmentConfig.ResilienceModeEnum]
	given fmtDataRetentionConfig: Format[Schema.DataRetentionConfig] = Json.format[Schema.DataRetentionConfig]
	given fmtCloudDataLineageIntegration: Format[Schema.CloudDataLineageIntegration] = Json.format[Schema.CloudDataLineageIntegration]
	given fmtSoftwareConfigWebServerPluginsModeEnum: Format[Schema.SoftwareConfig.WebServerPluginsModeEnum] = JsonEnumFormat[Schema.SoftwareConfig.WebServerPluginsModeEnum]
	given fmtIPAllocationPolicy: Format[Schema.IPAllocationPolicy] = Json.format[Schema.IPAllocationPolicy]
	given fmtPrivateClusterConfig: Format[Schema.PrivateClusterConfig] = Json.format[Schema.PrivateClusterConfig]
	given fmtNetworkingConfig: Format[Schema.NetworkingConfig] = Json.format[Schema.NetworkingConfig]
	given fmtNetworkingConfigConnectionTypeEnum: Format[Schema.NetworkingConfig.ConnectionTypeEnum] = JsonEnumFormat[Schema.NetworkingConfig.ConnectionTypeEnum]
	given fmtAllowedIpRange: Format[Schema.AllowedIpRange] = Json.format[Schema.AllowedIpRange]
	given fmtSchedulerResource: Format[Schema.SchedulerResource] = Json.format[Schema.SchedulerResource]
	given fmtWebServerResource: Format[Schema.WebServerResource] = Json.format[Schema.WebServerResource]
	given fmtWorkerResource: Format[Schema.WorkerResource] = Json.format[Schema.WorkerResource]
	given fmtTriggererResource: Format[Schema.TriggererResource] = Json.format[Schema.TriggererResource]
	given fmtDagProcessorResource: Format[Schema.DagProcessorResource] = Json.format[Schema.DagProcessorResource]
	given fmtCidrBlock: Format[Schema.CidrBlock] = Json.format[Schema.CidrBlock]
	given fmtScheduledSnapshotsConfig: Format[Schema.ScheduledSnapshotsConfig] = Json.format[Schema.ScheduledSnapshotsConfig]
	given fmtAirflowMetadataRetentionPolicyConfig: Format[Schema.AirflowMetadataRetentionPolicyConfig] = Json.format[Schema.AirflowMetadataRetentionPolicyConfig]
	given fmtTaskLogsRetentionConfig: Format[Schema.TaskLogsRetentionConfig] = Json.format[Schema.TaskLogsRetentionConfig]
	given fmtAirflowMetadataRetentionPolicyConfigRetentionModeEnum: Format[Schema.AirflowMetadataRetentionPolicyConfig.RetentionModeEnum] = JsonEnumFormat[Schema.AirflowMetadataRetentionPolicyConfig.RetentionModeEnum]
	given fmtTaskLogsRetentionConfigStorageModeEnum: Format[Schema.TaskLogsRetentionConfig.StorageModeEnum] = JsonEnumFormat[Schema.TaskLogsRetentionConfig.StorageModeEnum]
	given fmtListEnvironmentsResponse: Format[Schema.ListEnvironmentsResponse] = Json.format[Schema.ListEnvironmentsResponse]
	given fmtExecuteAirflowCommandRequest: Format[Schema.ExecuteAirflowCommandRequest] = Json.format[Schema.ExecuteAirflowCommandRequest]
	given fmtExecuteAirflowCommandResponse: Format[Schema.ExecuteAirflowCommandResponse] = Json.format[Schema.ExecuteAirflowCommandResponse]
	given fmtStopAirflowCommandRequest: Format[Schema.StopAirflowCommandRequest] = Json.format[Schema.StopAirflowCommandRequest]
	given fmtStopAirflowCommandResponse: Format[Schema.StopAirflowCommandResponse] = Json.format[Schema.StopAirflowCommandResponse]
	given fmtPollAirflowCommandRequest: Format[Schema.PollAirflowCommandRequest] = Json.format[Schema.PollAirflowCommandRequest]
	given fmtPollAirflowCommandResponse: Format[Schema.PollAirflowCommandResponse] = Json.format[Schema.PollAirflowCommandResponse]
	given fmtLine: Format[Schema.Line] = Json.format[Schema.Line]
	given fmtExitInfo: Format[Schema.ExitInfo] = Json.format[Schema.ExitInfo]
	given fmtListWorkloadsResponse: Format[Schema.ListWorkloadsResponse] = Json.format[Schema.ListWorkloadsResponse]
	given fmtComposerWorkload: Format[Schema.ComposerWorkload] = Json.format[Schema.ComposerWorkload]
	given fmtComposerWorkloadTypeEnum: Format[Schema.ComposerWorkload.TypeEnum] = JsonEnumFormat[Schema.ComposerWorkload.TypeEnum]
	given fmtComposerWorkloadStatus: Format[Schema.ComposerWorkloadStatus] = Json.format[Schema.ComposerWorkloadStatus]
	given fmtComposerWorkloadStatusStateEnum: Format[Schema.ComposerWorkloadStatus.StateEnum] = JsonEnumFormat[Schema.ComposerWorkloadStatus.StateEnum]
	given fmtCheckUpgradeRequest: Format[Schema.CheckUpgradeRequest] = Json.format[Schema.CheckUpgradeRequest]
	given fmtUserWorkloadsSecret: Format[Schema.UserWorkloadsSecret] = Json.format[Schema.UserWorkloadsSecret]
	given fmtListUserWorkloadsSecretsResponse: Format[Schema.ListUserWorkloadsSecretsResponse] = Json.format[Schema.ListUserWorkloadsSecretsResponse]
	given fmtUserWorkloadsConfigMap: Format[Schema.UserWorkloadsConfigMap] = Json.format[Schema.UserWorkloadsConfigMap]
	given fmtListUserWorkloadsConfigMapsResponse: Format[Schema.ListUserWorkloadsConfigMapsResponse] = Json.format[Schema.ListUserWorkloadsConfigMapsResponse]
	given fmtSaveSnapshotRequest: Format[Schema.SaveSnapshotRequest] = Json.format[Schema.SaveSnapshotRequest]
	given fmtLoadSnapshotRequest: Format[Schema.LoadSnapshotRequest] = Json.format[Schema.LoadSnapshotRequest]
	given fmtDatabaseFailoverRequest: Format[Schema.DatabaseFailoverRequest] = Json.format[Schema.DatabaseFailoverRequest]
	given fmtFetchDatabasePropertiesResponse: Format[Schema.FetchDatabasePropertiesResponse] = Json.format[Schema.FetchDatabasePropertiesResponse]
	given fmtListImageVersionsResponse: Format[Schema.ListImageVersionsResponse] = Json.format[Schema.ListImageVersionsResponse]
	given fmtImageVersion: Format[Schema.ImageVersion] = Json.format[Schema.ImageVersion]
	given fmtDate: Format[Schema.Date] = Json.format[Schema.Date]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
	given fmtOperationMetadataStateEnum: Format[Schema.OperationMetadata.StateEnum] = JsonEnumFormat[Schema.OperationMetadata.StateEnum]
	given fmtOperationMetadataOperationTypeEnum: Format[Schema.OperationMetadata.OperationTypeEnum] = JsonEnumFormat[Schema.OperationMetadata.OperationTypeEnum]
	given fmtCheckUpgradeResponse: Format[Schema.CheckUpgradeResponse] = Json.format[Schema.CheckUpgradeResponse]
	given fmtCheckUpgradeResponseContainsPypiModulesConflictEnum: Format[Schema.CheckUpgradeResponse.ContainsPypiModulesConflictEnum] = JsonEnumFormat[Schema.CheckUpgradeResponse.ContainsPypiModulesConflictEnum]
	given fmtSaveSnapshotResponse: Format[Schema.SaveSnapshotResponse] = Json.format[Schema.SaveSnapshotResponse]
	given fmtLoadSnapshotResponse: Format[Schema.LoadSnapshotResponse] = Json.format[Schema.LoadSnapshotResponse]
	given fmtDatabaseFailoverResponse: Format[Schema.DatabaseFailoverResponse] = Json.format[Schema.DatabaseFailoverResponse]
}