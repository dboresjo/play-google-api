package com.boresjo.play.api.google.alloydb

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
		given putListSchemaCluster: Conversion[List[Schema.Cluster], Option[List[Schema.Cluster]]] = (fun: List[Schema.Cluster]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaBackupSource: Conversion[Schema.BackupSource, Option[Schema.BackupSource]] = (fun: Schema.BackupSource) => Option(fun)
		given putSchemaMigrationSource: Conversion[Schema.MigrationSource, Option[Schema.MigrationSource]] = (fun: Schema.MigrationSource) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaClusterStateEnum: Conversion[Schema.Cluster.StateEnum, Option[Schema.Cluster.StateEnum]] = (fun: Schema.Cluster.StateEnum) => Option(fun)
		given putSchemaClusterClusterTypeEnum: Conversion[Schema.Cluster.ClusterTypeEnum, Option[Schema.Cluster.ClusterTypeEnum]] = (fun: Schema.Cluster.ClusterTypeEnum) => Option(fun)
		given putSchemaClusterDatabaseVersionEnum: Conversion[Schema.Cluster.DatabaseVersionEnum, Option[Schema.Cluster.DatabaseVersionEnum]] = (fun: Schema.Cluster.DatabaseVersionEnum) => Option(fun)
		given putSchemaNetworkConfig: Conversion[Schema.NetworkConfig, Option[Schema.NetworkConfig]] = (fun: Schema.NetworkConfig) => Option(fun)
		given putSchemaUserPassword: Conversion[Schema.UserPassword, Option[Schema.UserPassword]] = (fun: Schema.UserPassword) => Option(fun)
		given putSchemaAutomatedBackupPolicy: Conversion[Schema.AutomatedBackupPolicy, Option[Schema.AutomatedBackupPolicy]] = (fun: Schema.AutomatedBackupPolicy) => Option(fun)
		given putSchemaSslConfig: Conversion[Schema.SslConfig, Option[Schema.SslConfig]] = (fun: Schema.SslConfig) => Option(fun)
		given putSchemaEncryptionConfig: Conversion[Schema.EncryptionConfig, Option[Schema.EncryptionConfig]] = (fun: Schema.EncryptionConfig) => Option(fun)
		given putSchemaEncryptionInfo: Conversion[Schema.EncryptionInfo, Option[Schema.EncryptionInfo]] = (fun: Schema.EncryptionInfo) => Option(fun)
		given putSchemaContinuousBackupConfig: Conversion[Schema.ContinuousBackupConfig, Option[Schema.ContinuousBackupConfig]] = (fun: Schema.ContinuousBackupConfig) => Option(fun)
		given putSchemaContinuousBackupInfo: Conversion[Schema.ContinuousBackupInfo, Option[Schema.ContinuousBackupInfo]] = (fun: Schema.ContinuousBackupInfo) => Option(fun)
		given putSchemaSecondaryConfig: Conversion[Schema.SecondaryConfig, Option[Schema.SecondaryConfig]] = (fun: Schema.SecondaryConfig) => Option(fun)
		given putSchemaPrimaryConfig: Conversion[Schema.PrimaryConfig, Option[Schema.PrimaryConfig]] = (fun: Schema.PrimaryConfig) => Option(fun)
		given putSchemaPscConfig: Conversion[Schema.PscConfig, Option[Schema.PscConfig]] = (fun: Schema.PscConfig) => Option(fun)
		given putSchemaMaintenanceUpdatePolicy: Conversion[Schema.MaintenanceUpdatePolicy, Option[Schema.MaintenanceUpdatePolicy]] = (fun: Schema.MaintenanceUpdatePolicy) => Option(fun)
		given putSchemaMaintenanceSchedule: Conversion[Schema.MaintenanceSchedule, Option[Schema.MaintenanceSchedule]] = (fun: Schema.MaintenanceSchedule) => Option(fun)
		given putSchemaClusterSubscriptionTypeEnum: Conversion[Schema.Cluster.SubscriptionTypeEnum, Option[Schema.Cluster.SubscriptionTypeEnum]] = (fun: Schema.Cluster.SubscriptionTypeEnum) => Option(fun)
		given putSchemaTrialMetadata: Conversion[Schema.TrialMetadata, Option[Schema.TrialMetadata]] = (fun: Schema.TrialMetadata) => Option(fun)
		given putSchemaMigrationSourceSourceTypeEnum: Conversion[Schema.MigrationSource.SourceTypeEnum, Option[Schema.MigrationSource.SourceTypeEnum]] = (fun: Schema.MigrationSource.SourceTypeEnum) => Option(fun)
		given putSchemaWeeklySchedule: Conversion[Schema.WeeklySchedule, Option[Schema.WeeklySchedule]] = (fun: Schema.WeeklySchedule) => Option(fun)
		given putSchemaTimeBasedRetention: Conversion[Schema.TimeBasedRetention, Option[Schema.TimeBasedRetention]] = (fun: Schema.TimeBasedRetention) => Option(fun)
		given putSchemaQuantityBasedRetention: Conversion[Schema.QuantityBasedRetention, Option[Schema.QuantityBasedRetention]] = (fun: Schema.QuantityBasedRetention) => Option(fun)
		given putListSchemaGoogleTypeTimeOfDay: Conversion[List[Schema.GoogleTypeTimeOfDay], Option[List[Schema.GoogleTypeTimeOfDay]]] = (fun: List[Schema.GoogleTypeTimeOfDay]) => Option(fun)
		given putListSchemaWeeklyScheduleDaysOfWeekEnum: Conversion[List[Schema.WeeklySchedule.DaysOfWeekEnum], Option[List[Schema.WeeklySchedule.DaysOfWeekEnum]]] = (fun: List[Schema.WeeklySchedule.DaysOfWeekEnum]) => Option(fun)
		given putSchemaSslConfigSslModeEnum: Conversion[Schema.SslConfig.SslModeEnum, Option[Schema.SslConfig.SslModeEnum]] = (fun: Schema.SslConfig.SslModeEnum) => Option(fun)
		given putSchemaSslConfigCaSourceEnum: Conversion[Schema.SslConfig.CaSourceEnum, Option[Schema.SslConfig.CaSourceEnum]] = (fun: Schema.SslConfig.CaSourceEnum) => Option(fun)
		given putSchemaEncryptionInfoEncryptionTypeEnum: Conversion[Schema.EncryptionInfo.EncryptionTypeEnum, Option[Schema.EncryptionInfo.EncryptionTypeEnum]] = (fun: Schema.EncryptionInfo.EncryptionTypeEnum) => Option(fun)
		given putListSchemaContinuousBackupInfoScheduleEnum: Conversion[List[Schema.ContinuousBackupInfo.ScheduleEnum], Option[List[Schema.ContinuousBackupInfo.ScheduleEnum]]] = (fun: List[Schema.ContinuousBackupInfo.ScheduleEnum]) => Option(fun)
		given putListSchemaMaintenanceWindow: Conversion[List[Schema.MaintenanceWindow], Option[List[Schema.MaintenanceWindow]]] = (fun: List[Schema.MaintenanceWindow]) => Option(fun)
		given putSchemaMaintenanceWindowDayEnum: Conversion[Schema.MaintenanceWindow.DayEnum, Option[Schema.MaintenanceWindow.DayEnum]] = (fun: Schema.MaintenanceWindow.DayEnum) => Option(fun)
		given putSchemaGoogleTypeTimeOfDay: Conversion[Schema.GoogleTypeTimeOfDay, Option[Schema.GoogleTypeTimeOfDay]] = (fun: Schema.GoogleTypeTimeOfDay) => Option(fun)
		given putSchemaGcsDestination: Conversion[Schema.GcsDestination, Option[Schema.GcsDestination]] = (fun: Schema.GcsDestination) => Option(fun)
		given putSchemaCsvExportOptions: Conversion[Schema.CsvExportOptions, Option[Schema.CsvExportOptions]] = (fun: Schema.CsvExportOptions) => Option(fun)
		given putSchemaUpgradeClusterRequestVersionEnum: Conversion[Schema.UpgradeClusterRequest.VersionEnum, Option[Schema.UpgradeClusterRequest.VersionEnum]] = (fun: Schema.UpgradeClusterRequest.VersionEnum) => Option(fun)
		given putSchemaContinuousBackupSource: Conversion[Schema.ContinuousBackupSource, Option[Schema.ContinuousBackupSource]] = (fun: Schema.ContinuousBackupSource) => Option(fun)
		given putSchemaCluster: Conversion[Schema.Cluster, Option[Schema.Cluster]] = (fun: Schema.Cluster) => Option(fun)
		given putListSchemaInstance: Conversion[List[Schema.Instance], Option[List[Schema.Instance]]] = (fun: List[Schema.Instance]) => Option(fun)
		given putSchemaInstanceStateEnum: Conversion[Schema.Instance.StateEnum, Option[Schema.Instance.StateEnum]] = (fun: Schema.Instance.StateEnum) => Option(fun)
		given putSchemaInstanceInstanceTypeEnum: Conversion[Schema.Instance.InstanceTypeEnum, Option[Schema.Instance.InstanceTypeEnum]] = (fun: Schema.Instance.InstanceTypeEnum) => Option(fun)
		given putSchemaMachineConfig: Conversion[Schema.MachineConfig, Option[Schema.MachineConfig]] = (fun: Schema.MachineConfig) => Option(fun)
		given putSchemaInstanceAvailabilityTypeEnum: Conversion[Schema.Instance.AvailabilityTypeEnum, Option[Schema.Instance.AvailabilityTypeEnum]] = (fun: Schema.Instance.AvailabilityTypeEnum) => Option(fun)
		given putSchemaNode: Conversion[Schema.Node, Option[Schema.Node]] = (fun: Schema.Node) => Option(fun)
		given putListSchemaNode: Conversion[List[Schema.Node], Option[List[Schema.Node]]] = (fun: List[Schema.Node]) => Option(fun)
		given putSchemaQueryInsightsInstanceConfig: Conversion[Schema.QueryInsightsInstanceConfig, Option[Schema.QueryInsightsInstanceConfig]] = (fun: Schema.QueryInsightsInstanceConfig) => Option(fun)
		given putSchemaReadPoolConfig: Conversion[Schema.ReadPoolConfig, Option[Schema.ReadPoolConfig]] = (fun: Schema.ReadPoolConfig) => Option(fun)
		given putSchemaClientConnectionConfig: Conversion[Schema.ClientConnectionConfig, Option[Schema.ClientConnectionConfig]] = (fun: Schema.ClientConnectionConfig) => Option(fun)
		given putSchemaPscInstanceConfig: Conversion[Schema.PscInstanceConfig, Option[Schema.PscInstanceConfig]] = (fun: Schema.PscInstanceConfig) => Option(fun)
		given putSchemaInstanceNetworkConfig: Conversion[Schema.InstanceNetworkConfig, Option[Schema.InstanceNetworkConfig]] = (fun: Schema.InstanceNetworkConfig) => Option(fun)
		given putListSchemaAuthorizedNetwork: Conversion[List[Schema.AuthorizedNetwork], Option[List[Schema.AuthorizedNetwork]]] = (fun: List[Schema.AuthorizedNetwork]) => Option(fun)
		given putSchemaInjectFaultRequestFaultTypeEnum: Conversion[Schema.InjectFaultRequest.FaultTypeEnum, Option[Schema.InjectFaultRequest.FaultTypeEnum]] = (fun: Schema.InjectFaultRequest.FaultTypeEnum) => Option(fun)
		given putListSchemaBackup: Conversion[List[Schema.Backup], Option[List[Schema.Backup]]] = (fun: List[Schema.Backup]) => Option(fun)
		given putSchemaBackupStateEnum: Conversion[Schema.Backup.StateEnum, Option[Schema.Backup.StateEnum]] = (fun: Schema.Backup.StateEnum) => Option(fun)
		given putSchemaBackupTypeEnum: Conversion[Schema.Backup.TypeEnum, Option[Schema.Backup.TypeEnum]] = (fun: Schema.Backup.TypeEnum) => Option(fun)
		given putSchemaQuantityBasedExpiry: Conversion[Schema.QuantityBasedExpiry, Option[Schema.QuantityBasedExpiry]] = (fun: Schema.QuantityBasedExpiry) => Option(fun)
		given putSchemaBackupDatabaseVersionEnum: Conversion[Schema.Backup.DatabaseVersionEnum, Option[Schema.Backup.DatabaseVersionEnum]] = (fun: Schema.Backup.DatabaseVersionEnum) => Option(fun)
		given putListSchemaSupportedDatabaseFlag: Conversion[List[Schema.SupportedDatabaseFlag], Option[List[Schema.SupportedDatabaseFlag]]] = (fun: List[Schema.SupportedDatabaseFlag]) => Option(fun)
		given putSchemaStringRestrictions: Conversion[Schema.StringRestrictions, Option[Schema.StringRestrictions]] = (fun: Schema.StringRestrictions) => Option(fun)
		given putSchemaIntegerRestrictions: Conversion[Schema.IntegerRestrictions, Option[Schema.IntegerRestrictions]] = (fun: Schema.IntegerRestrictions) => Option(fun)
		given putSchemaSupportedDatabaseFlagValueTypeEnum: Conversion[Schema.SupportedDatabaseFlag.ValueTypeEnum, Option[Schema.SupportedDatabaseFlag.ValueTypeEnum]] = (fun: Schema.SupportedDatabaseFlag.ValueTypeEnum) => Option(fun)
		given putListSchemaSupportedDatabaseFlagSupportedDbVersionsEnum: Conversion[List[Schema.SupportedDatabaseFlag.SupportedDbVersionsEnum], Option[List[Schema.SupportedDatabaseFlag.SupportedDbVersionsEnum]]] = (fun: List[Schema.SupportedDatabaseFlag.SupportedDbVersionsEnum]) => Option(fun)
		given putListSchemaUser: Conversion[List[Schema.User], Option[List[Schema.User]]] = (fun: List[Schema.User]) => Option(fun)
		given putSchemaUserUserTypeEnum: Conversion[Schema.User.UserTypeEnum, Option[Schema.User.UserTypeEnum]] = (fun: Schema.User.UserTypeEnum) => Option(fun)
		given putListSchemaGoogleCloudLocationLocation: Conversion[List[Schema.GoogleCloudLocationLocation], Option[List[Schema.GoogleCloudLocationLocation]]] = (fun: List[Schema.GoogleCloudLocationLocation]) => Option(fun)
		given putSchemaUpgradeClusterResponseStatusEnum: Conversion[Schema.UpgradeClusterResponse.StatusEnum, Option[Schema.UpgradeClusterResponse.StatusEnum]] = (fun: Schema.UpgradeClusterResponse.StatusEnum) => Option(fun)
		given putListSchemaClusterUpgradeDetails: Conversion[List[Schema.ClusterUpgradeDetails], Option[List[Schema.ClusterUpgradeDetails]]] = (fun: List[Schema.ClusterUpgradeDetails]) => Option(fun)
		given putSchemaClusterUpgradeDetailsUpgradeStatusEnum: Conversion[Schema.ClusterUpgradeDetails.UpgradeStatusEnum, Option[Schema.ClusterUpgradeDetails.UpgradeStatusEnum]] = (fun: Schema.ClusterUpgradeDetails.UpgradeStatusEnum) => Option(fun)
		given putSchemaClusterUpgradeDetailsClusterTypeEnum: Conversion[Schema.ClusterUpgradeDetails.ClusterTypeEnum, Option[Schema.ClusterUpgradeDetails.ClusterTypeEnum]] = (fun: Schema.ClusterUpgradeDetails.ClusterTypeEnum) => Option(fun)
		given putSchemaClusterUpgradeDetailsDatabaseVersionEnum: Conversion[Schema.ClusterUpgradeDetails.DatabaseVersionEnum, Option[Schema.ClusterUpgradeDetails.DatabaseVersionEnum]] = (fun: Schema.ClusterUpgradeDetails.DatabaseVersionEnum) => Option(fun)
		given putListSchemaStageInfo: Conversion[List[Schema.StageInfo], Option[List[Schema.StageInfo]]] = (fun: List[Schema.StageInfo]) => Option(fun)
		given putListSchemaInstanceUpgradeDetails: Conversion[List[Schema.InstanceUpgradeDetails], Option[List[Schema.InstanceUpgradeDetails]]] = (fun: List[Schema.InstanceUpgradeDetails]) => Option(fun)
		given putSchemaStageInfoStageEnum: Conversion[Schema.StageInfo.StageEnum, Option[Schema.StageInfo.StageEnum]] = (fun: Schema.StageInfo.StageEnum) => Option(fun)
		given putSchemaStageInfoStatusEnum: Conversion[Schema.StageInfo.StatusEnum, Option[Schema.StageInfo.StatusEnum]] = (fun: Schema.StageInfo.StatusEnum) => Option(fun)
		given putSchemaInstanceUpgradeDetailsUpgradeStatusEnum: Conversion[Schema.InstanceUpgradeDetails.UpgradeStatusEnum, Option[Schema.InstanceUpgradeDetails.UpgradeStatusEnum]] = (fun: Schema.InstanceUpgradeDetails.UpgradeStatusEnum) => Option(fun)
		given putSchemaInstanceUpgradeDetailsInstanceTypeEnum: Conversion[Schema.InstanceUpgradeDetails.InstanceTypeEnum, Option[Schema.InstanceUpgradeDetails.InstanceTypeEnum]] = (fun: Schema.InstanceUpgradeDetails.InstanceTypeEnum) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainDatabaseResourceId: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceId, Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceId]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceId) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainDatabaseResourceFeedFeedTypeEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceFeed.FeedTypeEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceFeed.FeedTypeEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceFeed.FeedTypeEnum) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata, Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData, Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainDatabaseResourceRecommendationSignalData: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceRecommendationSignalData, Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceRecommendationSignalData]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceRecommendationSignalData) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainObservabilityMetricData: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainObservabilityMetricData, Option[Schema.StorageDatabasecenterPartnerapiV1mainObservabilityMetricData]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainObservabilityMetricData) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainDatabaseResourceIdProviderEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceId.ProviderEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceId.ProviderEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceId.ProviderEnum) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadataExpectedStateEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata.ExpectedStateEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata.ExpectedStateEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata.ExpectedStateEnum) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadataCurrentStateEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata.CurrentStateEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata.CurrentStateEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata.CurrentStateEnum) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadataInstanceTypeEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata.InstanceTypeEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata.InstanceTypeEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata.InstanceTypeEnum) => Option(fun)
		given putSchemaStorageDatabasecenterProtoCommonProduct: Conversion[Schema.StorageDatabasecenterProtoCommonProduct, Option[Schema.StorageDatabasecenterProtoCommonProduct]] = (fun: Schema.StorageDatabasecenterProtoCommonProduct) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainAvailabilityConfiguration: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainAvailabilityConfiguration, Option[Schema.StorageDatabasecenterPartnerapiV1mainAvailabilityConfiguration]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainAvailabilityConfiguration) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainBackupConfiguration: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainBackupConfiguration, Option[Schema.StorageDatabasecenterPartnerapiV1mainBackupConfiguration]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainBackupConfiguration) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainBackupRun: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainBackupRun, Option[Schema.StorageDatabasecenterPartnerapiV1mainBackupRun]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainBackupRun) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainCustomMetadataData: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainCustomMetadataData, Option[Schema.StorageDatabasecenterPartnerapiV1mainCustomMetadataData]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainCustomMetadataData) => Option(fun)
		given putListSchemaStorageDatabasecenterPartnerapiV1mainEntitlement: Conversion[List[Schema.StorageDatabasecenterPartnerapiV1mainEntitlement], Option[List[Schema.StorageDatabasecenterPartnerapiV1mainEntitlement]]] = (fun: List[Schema.StorageDatabasecenterPartnerapiV1mainEntitlement]) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainUserLabels: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainUserLabels, Option[Schema.StorageDatabasecenterPartnerapiV1mainUserLabels]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainUserLabels) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainMachineConfiguration: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainMachineConfiguration, Option[Schema.StorageDatabasecenterPartnerapiV1mainMachineConfiguration]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainMachineConfiguration) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainTags: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainTags, Option[Schema.StorageDatabasecenterPartnerapiV1mainTags]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainTags) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadataEditionEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata.EditionEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata.EditionEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceMetadata.EditionEnum) => Option(fun)
		given putSchemaStorageDatabasecenterProtoCommonProductTypeEnum: Conversion[Schema.StorageDatabasecenterProtoCommonProduct.TypeEnum, Option[Schema.StorageDatabasecenterProtoCommonProduct.TypeEnum]] = (fun: Schema.StorageDatabasecenterProtoCommonProduct.TypeEnum) => Option(fun)
		given putSchemaStorageDatabasecenterProtoCommonProductEngineEnum: Conversion[Schema.StorageDatabasecenterProtoCommonProduct.EngineEnum, Option[Schema.StorageDatabasecenterProtoCommonProduct.EngineEnum]] = (fun: Schema.StorageDatabasecenterProtoCommonProduct.EngineEnum) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainAvailabilityConfigurationAvailabilityTypeEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainAvailabilityConfiguration.AvailabilityTypeEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainAvailabilityConfiguration.AvailabilityTypeEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainAvailabilityConfiguration.AvailabilityTypeEnum) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainRetentionSettings: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainRetentionSettings, Option[Schema.StorageDatabasecenterPartnerapiV1mainRetentionSettings]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainRetentionSettings) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainRetentionSettingsRetentionUnitEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainRetentionSettings.RetentionUnitEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainRetentionSettings.RetentionUnitEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainRetentionSettings.RetentionUnitEnum) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainBackupRunStatusEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainBackupRun.StatusEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainBackupRun.StatusEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainBackupRun.StatusEnum) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainOperationError: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainOperationError, Option[Schema.StorageDatabasecenterPartnerapiV1mainOperationError]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainOperationError) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainOperationErrorErrorTypeEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainOperationError.ErrorTypeEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainOperationError.ErrorTypeEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainOperationError.ErrorTypeEnum) => Option(fun)
		given putListSchemaStorageDatabasecenterPartnerapiV1mainInternalResourceMetadata: Conversion[List[Schema.StorageDatabasecenterPartnerapiV1mainInternalResourceMetadata], Option[List[Schema.StorageDatabasecenterPartnerapiV1mainInternalResourceMetadata]]] = (fun: List[Schema.StorageDatabasecenterPartnerapiV1mainInternalResourceMetadata]) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainEntitlementTypeEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainEntitlement.TypeEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainEntitlement.TypeEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainEntitlement.TypeEnum) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainEntitlementEntitlementStateEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainEntitlement.EntitlementStateEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainEntitlement.EntitlementStateEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainEntitlement.EntitlementStateEnum) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalDataProviderEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData.ProviderEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData.ProviderEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData.ProviderEnum) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalDataStateEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData.StateEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData.StateEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData.StateEnum) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalDataSignalClassEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData.SignalClassEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData.SignalClassEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData.SignalClassEnum) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalDataSignalSeverityEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData.SignalSeverityEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData.SignalSeverityEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData.SignalSeverityEnum) => Option(fun)
		given putListSchemaStorageDatabasecenterPartnerapiV1mainCompliance: Conversion[List[Schema.StorageDatabasecenterPartnerapiV1mainCompliance], Option[List[Schema.StorageDatabasecenterPartnerapiV1mainCompliance]]] = (fun: List[Schema.StorageDatabasecenterPartnerapiV1mainCompliance]) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalDataSignalTypeEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData.SignalTypeEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData.SignalTypeEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceHealthSignalData.SignalTypeEnum) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainDatabaseResourceRecommendationSignalDataSignalTypeEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceRecommendationSignalData.SignalTypeEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceRecommendationSignalData.SignalTypeEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceRecommendationSignalData.SignalTypeEnum) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainDatabaseResourceRecommendationSignalDataRecommendationStateEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceRecommendationSignalData.RecommendationStateEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceRecommendationSignalData.RecommendationStateEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainDatabaseResourceRecommendationSignalData.RecommendationStateEnum) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainObservabilityMetricDataMetricTypeEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainObservabilityMetricData.MetricTypeEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainObservabilityMetricData.MetricTypeEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainObservabilityMetricData.MetricTypeEnum) => Option(fun)
		given putSchemaStorageDatabasecenterPartnerapiV1mainObservabilityMetricDataAggregationTypeEnum: Conversion[Schema.StorageDatabasecenterPartnerapiV1mainObservabilityMetricData.AggregationTypeEnum, Option[Schema.StorageDatabasecenterPartnerapiV1mainObservabilityMetricData.AggregationTypeEnum]] = (fun: Schema.StorageDatabasecenterPartnerapiV1mainObservabilityMetricData.AggregationTypeEnum) => Option(fun)
		given putSchemaStorageDatabasecenterProtoCommonTypedValue: Conversion[Schema.StorageDatabasecenterProtoCommonTypedValue, Option[Schema.StorageDatabasecenterProtoCommonTypedValue]] = (fun: Schema.StorageDatabasecenterProtoCommonTypedValue) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putSchemaCloudControl2SharedOperationsReconciliationOperationMetadataExclusiveActionEnum: Conversion[Schema.CloudControl2SharedOperationsReconciliationOperationMetadata.ExclusiveActionEnum, Option[Schema.CloudControl2SharedOperationsReconciliationOperationMetadata.ExclusiveActionEnum]] = (fun: Schema.CloudControl2SharedOperationsReconciliationOperationMetadata.ExclusiveActionEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
