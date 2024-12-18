package com.boresjo.play.api.google.sqladmin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaOperationStatusEnum: Conversion[Schema.Operation.StatusEnum, Option[Schema.Operation.StatusEnum]] = (fun: Schema.Operation.StatusEnum) => Option(fun)
		given putSchemaOperationErrors: Conversion[Schema.OperationErrors, Option[Schema.OperationErrors]] = (fun: Schema.OperationErrors) => Option(fun)
		given putSchemaApiWarning: Conversion[Schema.ApiWarning, Option[Schema.ApiWarning]] = (fun: Schema.ApiWarning) => Option(fun)
		given putSchemaOperationOperationTypeEnum: Conversion[Schema.Operation.OperationTypeEnum, Option[Schema.Operation.OperationTypeEnum]] = (fun: Schema.Operation.OperationTypeEnum) => Option(fun)
		given putSchemaImportContext: Conversion[Schema.ImportContext, Option[Schema.ImportContext]] = (fun: Schema.ImportContext) => Option(fun)
		given putSchemaExportContext: Conversion[Schema.ExportContext, Option[Schema.ExportContext]] = (fun: Schema.ExportContext) => Option(fun)
		given putSchemaBackupContext: Conversion[Schema.BackupContext, Option[Schema.BackupContext]] = (fun: Schema.BackupContext) => Option(fun)
		given putSchemaAcquireSsrsLeaseContext: Conversion[Schema.AcquireSsrsLeaseContext, Option[Schema.AcquireSsrsLeaseContext]] = (fun: Schema.AcquireSsrsLeaseContext) => Option(fun)
		given putListSchemaOperationError: Conversion[List[Schema.OperationError], Option[List[Schema.OperationError]]] = (fun: List[Schema.OperationError]) => Option(fun)
		given putSchemaApiWarningCodeEnum: Conversion[Schema.ApiWarning.CodeEnum, Option[Schema.ApiWarning.CodeEnum]] = (fun: Schema.ApiWarning.CodeEnum) => Option(fun)
		given putSchemaImportContextFileTypeEnum: Conversion[Schema.ImportContext.FileTypeEnum, Option[Schema.ImportContext.FileTypeEnum]] = (fun: Schema.ImportContext.FileTypeEnum) => Option(fun)
		given putSchemaImportContextCsvImportOptionsItem: Conversion[Schema.ImportContext.CsvImportOptionsItem, Option[Schema.ImportContext.CsvImportOptionsItem]] = (fun: Schema.ImportContext.CsvImportOptionsItem) => Option(fun)
		given putSchemaImportContextBakImportOptionsItem: Conversion[Schema.ImportContext.BakImportOptionsItem, Option[Schema.ImportContext.BakImportOptionsItem]] = (fun: Schema.ImportContext.BakImportOptionsItem) => Option(fun)
		given putSchemaImportContextSqlImportOptionsItem: Conversion[Schema.ImportContext.SqlImportOptionsItem, Option[Schema.ImportContext.SqlImportOptionsItem]] = (fun: Schema.ImportContext.SqlImportOptionsItem) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaExportContextSqlExportOptionsItem: Conversion[Schema.ExportContext.SqlExportOptionsItem, Option[Schema.ExportContext.SqlExportOptionsItem]] = (fun: Schema.ExportContext.SqlExportOptionsItem) => Option(fun)
		given putSchemaExportContextCsvExportOptionsItem: Conversion[Schema.ExportContext.CsvExportOptionsItem, Option[Schema.ExportContext.CsvExportOptionsItem]] = (fun: Schema.ExportContext.CsvExportOptionsItem) => Option(fun)
		given putSchemaExportContextFileTypeEnum: Conversion[Schema.ExportContext.FileTypeEnum, Option[Schema.ExportContext.FileTypeEnum]] = (fun: Schema.ExportContext.FileTypeEnum) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaExportContextBakExportOptionsItem: Conversion[Schema.ExportContext.BakExportOptionsItem, Option[Schema.ExportContext.BakExportOptionsItem]] = (fun: Schema.ExportContext.BakExportOptionsItem) => Option(fun)
		given putSchemaCloneContext: Conversion[Schema.CloneContext, Option[Schema.CloneContext]] = (fun: Schema.CloneContext) => Option(fun)
		given putSchemaBinLogCoordinates: Conversion[Schema.BinLogCoordinates, Option[Schema.BinLogCoordinates]] = (fun: Schema.BinLogCoordinates) => Option(fun)
		given putSchemaDemoteMasterContext: Conversion[Schema.DemoteMasterContext, Option[Schema.DemoteMasterContext]] = (fun: Schema.DemoteMasterContext) => Option(fun)
		given putSchemaDemoteMasterConfiguration: Conversion[Schema.DemoteMasterConfiguration, Option[Schema.DemoteMasterConfiguration]] = (fun: Schema.DemoteMasterConfiguration) => Option(fun)
		given putSchemaDemoteMasterMySqlReplicaConfiguration: Conversion[Schema.DemoteMasterMySqlReplicaConfiguration, Option[Schema.DemoteMasterMySqlReplicaConfiguration]] = (fun: Schema.DemoteMasterMySqlReplicaConfiguration) => Option(fun)
		given putSchemaDemoteContext: Conversion[Schema.DemoteContext, Option[Schema.DemoteContext]] = (fun: Schema.DemoteContext) => Option(fun)
		given putSchemaFailoverContext: Conversion[Schema.FailoverContext, Option[Schema.FailoverContext]] = (fun: Schema.FailoverContext) => Option(fun)
		given putSchemaBackupReencryptionConfig: Conversion[Schema.BackupReencryptionConfig, Option[Schema.BackupReencryptionConfig]] = (fun: Schema.BackupReencryptionConfig) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaBackupReencryptionConfigBackupTypeEnum: Conversion[Schema.BackupReencryptionConfig.BackupTypeEnum, Option[Schema.BackupReencryptionConfig.BackupTypeEnum]] = (fun: Schema.BackupReencryptionConfig.BackupTypeEnum) => Option(fun)
		given putSchemaDatabaseInstanceStateEnum: Conversion[Schema.DatabaseInstance.StateEnum, Option[Schema.DatabaseInstance.StateEnum]] = (fun: Schema.DatabaseInstance.StateEnum) => Option(fun)
		given putSchemaDatabaseInstanceDatabaseVersionEnum: Conversion[Schema.DatabaseInstance.DatabaseVersionEnum, Option[Schema.DatabaseInstance.DatabaseVersionEnum]] = (fun: Schema.DatabaseInstance.DatabaseVersionEnum) => Option(fun)
		given putSchemaSettings: Conversion[Schema.Settings, Option[Schema.Settings]] = (fun: Schema.Settings) => Option(fun)
		given putSchemaDatabaseInstanceFailoverReplicaItem: Conversion[Schema.DatabaseInstance.FailoverReplicaItem, Option[Schema.DatabaseInstance.FailoverReplicaItem]] = (fun: Schema.DatabaseInstance.FailoverReplicaItem) => Option(fun)
		given putListSchemaIpMapping: Conversion[List[Schema.IpMapping], Option[List[Schema.IpMapping]]] = (fun: List[Schema.IpMapping]) => Option(fun)
		given putSchemaSslCert: Conversion[Schema.SslCert, Option[Schema.SslCert]] = (fun: Schema.SslCert) => Option(fun)
		given putSchemaDatabaseInstanceInstanceTypeEnum: Conversion[Schema.DatabaseInstance.InstanceTypeEnum, Option[Schema.DatabaseInstance.InstanceTypeEnum]] = (fun: Schema.DatabaseInstance.InstanceTypeEnum) => Option(fun)
		given putSchemaOnPremisesConfiguration: Conversion[Schema.OnPremisesConfiguration, Option[Schema.OnPremisesConfiguration]] = (fun: Schema.OnPremisesConfiguration) => Option(fun)
		given putSchemaReplicaConfiguration: Conversion[Schema.ReplicaConfiguration, Option[Schema.ReplicaConfiguration]] = (fun: Schema.ReplicaConfiguration) => Option(fun)
		given putSchemaDatabaseInstanceBackendTypeEnum: Conversion[Schema.DatabaseInstance.BackendTypeEnum, Option[Schema.DatabaseInstance.BackendTypeEnum]] = (fun: Schema.DatabaseInstance.BackendTypeEnum) => Option(fun)
		given putListSchemaDatabaseInstanceSuspensionReasonEnum: Conversion[List[Schema.DatabaseInstance.SuspensionReasonEnum], Option[List[Schema.DatabaseInstance.SuspensionReasonEnum]]] = (fun: List[Schema.DatabaseInstance.SuspensionReasonEnum]) => Option(fun)
		given putSchemaDiskEncryptionConfiguration: Conversion[Schema.DiskEncryptionConfiguration, Option[Schema.DiskEncryptionConfiguration]] = (fun: Schema.DiskEncryptionConfiguration) => Option(fun)
		given putSchemaDiskEncryptionStatus: Conversion[Schema.DiskEncryptionStatus, Option[Schema.DiskEncryptionStatus]] = (fun: Schema.DiskEncryptionStatus) => Option(fun)
		given putSchemaSqlScheduledMaintenance: Conversion[Schema.SqlScheduledMaintenance, Option[Schema.SqlScheduledMaintenance]] = (fun: Schema.SqlScheduledMaintenance) => Option(fun)
		given putSchemaSqlOutOfDiskReport: Conversion[Schema.SqlOutOfDiskReport, Option[Schema.SqlOutOfDiskReport]] = (fun: Schema.SqlOutOfDiskReport) => Option(fun)
		given putListSchemaAvailableDatabaseVersion: Conversion[List[Schema.AvailableDatabaseVersion], Option[List[Schema.AvailableDatabaseVersion]]] = (fun: List[Schema.AvailableDatabaseVersion]) => Option(fun)
		given putSchemaDatabaseInstanceSqlNetworkArchitectureEnum: Conversion[Schema.DatabaseInstance.SqlNetworkArchitectureEnum, Option[Schema.DatabaseInstance.SqlNetworkArchitectureEnum]] = (fun: Schema.DatabaseInstance.SqlNetworkArchitectureEnum) => Option(fun)
		given putSchemaReplicationCluster: Conversion[Schema.ReplicationCluster, Option[Schema.ReplicationCluster]] = (fun: Schema.ReplicationCluster) => Option(fun)
		given putSchemaGeminiInstanceConfig: Conversion[Schema.GeminiInstanceConfig, Option[Schema.GeminiInstanceConfig]] = (fun: Schema.GeminiInstanceConfig) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaSettingsAvailabilityTypeEnum: Conversion[Schema.Settings.AvailabilityTypeEnum, Option[Schema.Settings.AvailabilityTypeEnum]] = (fun: Schema.Settings.AvailabilityTypeEnum) => Option(fun)
		given putSchemaSettingsPricingPlanEnum: Conversion[Schema.Settings.PricingPlanEnum, Option[Schema.Settings.PricingPlanEnum]] = (fun: Schema.Settings.PricingPlanEnum) => Option(fun)
		given putSchemaSettingsReplicationTypeEnum: Conversion[Schema.Settings.ReplicationTypeEnum, Option[Schema.Settings.ReplicationTypeEnum]] = (fun: Schema.Settings.ReplicationTypeEnum) => Option(fun)
		given putSchemaSettingsActivationPolicyEnum: Conversion[Schema.Settings.ActivationPolicyEnum, Option[Schema.Settings.ActivationPolicyEnum]] = (fun: Schema.Settings.ActivationPolicyEnum) => Option(fun)
		given putSchemaIpConfiguration: Conversion[Schema.IpConfiguration, Option[Schema.IpConfiguration]] = (fun: Schema.IpConfiguration) => Option(fun)
		given putSchemaLocationPreference: Conversion[Schema.LocationPreference, Option[Schema.LocationPreference]] = (fun: Schema.LocationPreference) => Option(fun)
		given putListSchemaDatabaseFlags: Conversion[List[Schema.DatabaseFlags], Option[List[Schema.DatabaseFlags]]] = (fun: List[Schema.DatabaseFlags]) => Option(fun)
		given putSchemaSettingsDataDiskTypeEnum: Conversion[Schema.Settings.DataDiskTypeEnum, Option[Schema.Settings.DataDiskTypeEnum]] = (fun: Schema.Settings.DataDiskTypeEnum) => Option(fun)
		given putSchemaMaintenanceWindow: Conversion[Schema.MaintenanceWindow, Option[Schema.MaintenanceWindow]] = (fun: Schema.MaintenanceWindow) => Option(fun)
		given putSchemaBackupConfiguration: Conversion[Schema.BackupConfiguration, Option[Schema.BackupConfiguration]] = (fun: Schema.BackupConfiguration) => Option(fun)
		given putSchemaSqlActiveDirectoryConfig: Conversion[Schema.SqlActiveDirectoryConfig, Option[Schema.SqlActiveDirectoryConfig]] = (fun: Schema.SqlActiveDirectoryConfig) => Option(fun)
		given putListSchemaDenyMaintenancePeriod: Conversion[List[Schema.DenyMaintenancePeriod], Option[List[Schema.DenyMaintenancePeriod]]] = (fun: List[Schema.DenyMaintenancePeriod]) => Option(fun)
		given putSchemaInsightsConfig: Conversion[Schema.InsightsConfig, Option[Schema.InsightsConfig]] = (fun: Schema.InsightsConfig) => Option(fun)
		given putSchemaPasswordValidationPolicy: Conversion[Schema.PasswordValidationPolicy, Option[Schema.PasswordValidationPolicy]] = (fun: Schema.PasswordValidationPolicy) => Option(fun)
		given putSchemaSqlServerAuditConfig: Conversion[Schema.SqlServerAuditConfig, Option[Schema.SqlServerAuditConfig]] = (fun: Schema.SqlServerAuditConfig) => Option(fun)
		given putSchemaSettingsEditionEnum: Conversion[Schema.Settings.EditionEnum, Option[Schema.Settings.EditionEnum]] = (fun: Schema.Settings.EditionEnum) => Option(fun)
		given putSchemaSettingsConnectorEnforcementEnum: Conversion[Schema.Settings.ConnectorEnforcementEnum, Option[Schema.Settings.ConnectorEnforcementEnum]] = (fun: Schema.Settings.ConnectorEnforcementEnum) => Option(fun)
		given putSchemaAdvancedMachineFeatures: Conversion[Schema.AdvancedMachineFeatures, Option[Schema.AdvancedMachineFeatures]] = (fun: Schema.AdvancedMachineFeatures) => Option(fun)
		given putSchemaDataCacheConfig: Conversion[Schema.DataCacheConfig, Option[Schema.DataCacheConfig]] = (fun: Schema.DataCacheConfig) => Option(fun)
		given putListSchemaAclEntry: Conversion[List[Schema.AclEntry], Option[List[Schema.AclEntry]]] = (fun: List[Schema.AclEntry]) => Option(fun)
		given putSchemaIpConfigurationSslModeEnum: Conversion[Schema.IpConfiguration.SslModeEnum, Option[Schema.IpConfiguration.SslModeEnum]] = (fun: Schema.IpConfiguration.SslModeEnum) => Option(fun)
		given putSchemaPscConfig: Conversion[Schema.PscConfig, Option[Schema.PscConfig]] = (fun: Schema.PscConfig) => Option(fun)
		given putSchemaIpConfigurationServerCaModeEnum: Conversion[Schema.IpConfiguration.ServerCaModeEnum, Option[Schema.IpConfiguration.ServerCaModeEnum]] = (fun: Schema.IpConfiguration.ServerCaModeEnum) => Option(fun)
		given putListSchemaPscAutoConnectionConfig: Conversion[List[Schema.PscAutoConnectionConfig], Option[List[Schema.PscAutoConnectionConfig]]] = (fun: List[Schema.PscAutoConnectionConfig]) => Option(fun)
		given putSchemaMaintenanceWindowUpdateTrackEnum: Conversion[Schema.MaintenanceWindow.UpdateTrackEnum, Option[Schema.MaintenanceWindow.UpdateTrackEnum]] = (fun: Schema.MaintenanceWindow.UpdateTrackEnum) => Option(fun)
		given putSchemaBackupRetentionSettings: Conversion[Schema.BackupRetentionSettings, Option[Schema.BackupRetentionSettings]] = (fun: Schema.BackupRetentionSettings) => Option(fun)
		given putSchemaBackupConfigurationTransactionalLogStorageStateEnum: Conversion[Schema.BackupConfiguration.TransactionalLogStorageStateEnum, Option[Schema.BackupConfiguration.TransactionalLogStorageStateEnum]] = (fun: Schema.BackupConfiguration.TransactionalLogStorageStateEnum) => Option(fun)
		given putSchemaBackupRetentionSettingsRetentionUnitEnum: Conversion[Schema.BackupRetentionSettings.RetentionUnitEnum, Option[Schema.BackupRetentionSettings.RetentionUnitEnum]] = (fun: Schema.BackupRetentionSettings.RetentionUnitEnum) => Option(fun)
		given putSchemaPasswordValidationPolicyComplexityEnum: Conversion[Schema.PasswordValidationPolicy.ComplexityEnum, Option[Schema.PasswordValidationPolicy.ComplexityEnum]] = (fun: Schema.PasswordValidationPolicy.ComplexityEnum) => Option(fun)
		given putSchemaIpMappingTypeEnum: Conversion[Schema.IpMapping.TypeEnum, Option[Schema.IpMapping.TypeEnum]] = (fun: Schema.IpMapping.TypeEnum) => Option(fun)
		given putSchemaInstanceReference: Conversion[Schema.InstanceReference, Option[Schema.InstanceReference]] = (fun: Schema.InstanceReference) => Option(fun)
		given putListSchemaSelectedObjects: Conversion[List[Schema.SelectedObjects], Option[List[Schema.SelectedObjects]]] = (fun: List[Schema.SelectedObjects]) => Option(fun)
		given putSchemaOnPremisesConfigurationSslOptionEnum: Conversion[Schema.OnPremisesConfiguration.SslOptionEnum, Option[Schema.OnPremisesConfiguration.SslOptionEnum]] = (fun: Schema.OnPremisesConfiguration.SslOptionEnum) => Option(fun)
		given putSchemaMySqlReplicaConfiguration: Conversion[Schema.MySqlReplicaConfiguration, Option[Schema.MySqlReplicaConfiguration]] = (fun: Schema.MySqlReplicaConfiguration) => Option(fun)
		given putSchemaSqlOutOfDiskReportSqlOutOfDiskStateEnum: Conversion[Schema.SqlOutOfDiskReport.SqlOutOfDiskStateEnum, Option[Schema.SqlOutOfDiskReport.SqlOutOfDiskStateEnum]] = (fun: Schema.SqlOutOfDiskReport.SqlOutOfDiskStateEnum) => Option(fun)
		given putListSchemaApiWarning: Conversion[List[Schema.ApiWarning], Option[List[Schema.ApiWarning]]] = (fun: List[Schema.ApiWarning]) => Option(fun)
		given putListSchemaDatabaseInstance: Conversion[List[Schema.DatabaseInstance], Option[List[Schema.DatabaseInstance]]] = (fun: List[Schema.DatabaseInstance]) => Option(fun)
		given putListSchemaSslCert: Conversion[List[Schema.SslCert], Option[List[Schema.SslCert]]] = (fun: List[Schema.SslCert]) => Option(fun)
		given putSchemaRestoreBackupContext: Conversion[Schema.RestoreBackupContext, Option[Schema.RestoreBackupContext]] = (fun: Schema.RestoreBackupContext) => Option(fun)
		given putSchemaRotateServerCaContext: Conversion[Schema.RotateServerCaContext, Option[Schema.RotateServerCaContext]] = (fun: Schema.RotateServerCaContext) => Option(fun)
		given putSchemaRotateServerCertificateContext: Conversion[Schema.RotateServerCertificateContext, Option[Schema.RotateServerCertificateContext]] = (fun: Schema.RotateServerCertificateContext) => Option(fun)
		given putSchemaTruncateLogContext: Conversion[Schema.TruncateLogContext, Option[Schema.TruncateLogContext]] = (fun: Schema.TruncateLogContext) => Option(fun)
		given putSchemaReschedule: Conversion[Schema.Reschedule, Option[Schema.Reschedule]] = (fun: Schema.Reschedule) => Option(fun)
		given putSchemaRescheduleRescheduleTypeEnum: Conversion[Schema.Reschedule.RescheduleTypeEnum, Option[Schema.Reschedule.RescheduleTypeEnum]] = (fun: Schema.Reschedule.RescheduleTypeEnum) => Option(fun)
		given putSchemaSqlInstancesVerifyExternalSyncSettingsRequestSyncModeEnum: Conversion[Schema.SqlInstancesVerifyExternalSyncSettingsRequest.SyncModeEnum, Option[Schema.SqlInstancesVerifyExternalSyncSettingsRequest.SyncModeEnum]] = (fun: Schema.SqlInstancesVerifyExternalSyncSettingsRequest.SyncModeEnum) => Option(fun)
		given putSchemaMySqlSyncConfig: Conversion[Schema.MySqlSyncConfig, Option[Schema.MySqlSyncConfig]] = (fun: Schema.MySqlSyncConfig) => Option(fun)
		given putSchemaSqlInstancesVerifyExternalSyncSettingsRequestMigrationTypeEnum: Conversion[Schema.SqlInstancesVerifyExternalSyncSettingsRequest.MigrationTypeEnum, Option[Schema.SqlInstancesVerifyExternalSyncSettingsRequest.MigrationTypeEnum]] = (fun: Schema.SqlInstancesVerifyExternalSyncSettingsRequest.MigrationTypeEnum) => Option(fun)
		given putSchemaSqlInstancesVerifyExternalSyncSettingsRequestSyncParallelLevelEnum: Conversion[Schema.SqlInstancesVerifyExternalSyncSettingsRequest.SyncParallelLevelEnum, Option[Schema.SqlInstancesVerifyExternalSyncSettingsRequest.SyncParallelLevelEnum]] = (fun: Schema.SqlInstancesVerifyExternalSyncSettingsRequest.SyncParallelLevelEnum) => Option(fun)
		given putListSchemaExternalSyncSelectedObject: Conversion[List[Schema.ExternalSyncSelectedObject], Option[List[Schema.ExternalSyncSelectedObject]]] = (fun: List[Schema.ExternalSyncSelectedObject]) => Option(fun)
		given putListSchemaSyncFlags: Conversion[List[Schema.SyncFlags], Option[List[Schema.SyncFlags]]] = (fun: List[Schema.SyncFlags]) => Option(fun)
		given putListSchemaSqlExternalSyncSettingError: Conversion[List[Schema.SqlExternalSyncSettingError], Option[List[Schema.SqlExternalSyncSettingError]]] = (fun: List[Schema.SqlExternalSyncSettingError]) => Option(fun)
		given putSchemaSqlExternalSyncSettingErrorTypeEnum: Conversion[Schema.SqlExternalSyncSettingError.TypeEnum, Option[Schema.SqlExternalSyncSettingError.TypeEnum]] = (fun: Schema.SqlExternalSyncSettingError.TypeEnum) => Option(fun)
		given putSchemaSqlInstancesStartExternalSyncRequestSyncModeEnum: Conversion[Schema.SqlInstancesStartExternalSyncRequest.SyncModeEnum, Option[Schema.SqlInstancesStartExternalSyncRequest.SyncModeEnum]] = (fun: Schema.SqlInstancesStartExternalSyncRequest.SyncModeEnum) => Option(fun)
		given putSchemaSqlInstancesStartExternalSyncRequestSyncParallelLevelEnum: Conversion[Schema.SqlInstancesStartExternalSyncRequest.SyncParallelLevelEnum, Option[Schema.SqlInstancesStartExternalSyncRequest.SyncParallelLevelEnum]] = (fun: Schema.SqlInstancesStartExternalSyncRequest.SyncParallelLevelEnum) => Option(fun)
		given putSchemaSqlInstancesStartExternalSyncRequestMigrationTypeEnum: Conversion[Schema.SqlInstancesStartExternalSyncRequest.MigrationTypeEnum, Option[Schema.SqlInstancesStartExternalSyncRequest.MigrationTypeEnum]] = (fun: Schema.SqlInstancesStartExternalSyncRequest.MigrationTypeEnum) => Option(fun)
		given putSchemaBackupRunStatusEnum: Conversion[Schema.BackupRun.StatusEnum, Option[Schema.BackupRun.StatusEnum]] = (fun: Schema.BackupRun.StatusEnum) => Option(fun)
		given putSchemaOperationError: Conversion[Schema.OperationError, Option[Schema.OperationError]] = (fun: Schema.OperationError) => Option(fun)
		given putSchemaBackupRunTypeEnum: Conversion[Schema.BackupRun.TypeEnum, Option[Schema.BackupRun.TypeEnum]] = (fun: Schema.BackupRun.TypeEnum) => Option(fun)
		given putSchemaBackupRunBackupKindEnum: Conversion[Schema.BackupRun.BackupKindEnum, Option[Schema.BackupRun.BackupKindEnum]] = (fun: Schema.BackupRun.BackupKindEnum) => Option(fun)
		given putListSchemaBackupRun: Conversion[List[Schema.BackupRun], Option[List[Schema.BackupRun]]] = (fun: List[Schema.BackupRun]) => Option(fun)
		given putSchemaConnectSettingsDatabaseVersionEnum: Conversion[Schema.ConnectSettings.DatabaseVersionEnum, Option[Schema.ConnectSettings.DatabaseVersionEnum]] = (fun: Schema.ConnectSettings.DatabaseVersionEnum) => Option(fun)
		given putSchemaConnectSettingsBackendTypeEnum: Conversion[Schema.ConnectSettings.BackendTypeEnum, Option[Schema.ConnectSettings.BackendTypeEnum]] = (fun: Schema.ConnectSettings.BackendTypeEnum) => Option(fun)
		given putSchemaConnectSettingsServerCaModeEnum: Conversion[Schema.ConnectSettings.ServerCaModeEnum, Option[Schema.ConnectSettings.ServerCaModeEnum]] = (fun: Schema.ConnectSettings.ServerCaModeEnum) => Option(fun)
		given putSchemaSqlServerDatabaseDetails: Conversion[Schema.SqlServerDatabaseDetails, Option[Schema.SqlServerDatabaseDetails]] = (fun: Schema.SqlServerDatabaseDetails) => Option(fun)
		given putListSchemaDatabase: Conversion[List[Schema.Database], Option[List[Schema.Database]]] = (fun: List[Schema.Database]) => Option(fun)
		given putListSchemaFlag: Conversion[List[Schema.Flag], Option[List[Schema.Flag]]] = (fun: List[Schema.Flag]) => Option(fun)
		given putSchemaFlagTypeEnum: Conversion[Schema.Flag.TypeEnum, Option[Schema.Flag.TypeEnum]] = (fun: Schema.Flag.TypeEnum) => Option(fun)
		given putListSchemaFlagAppliesToEnum: Conversion[List[Schema.Flag.AppliesToEnum], Option[List[Schema.Flag.AppliesToEnum]]] = (fun: List[Schema.Flag.AppliesToEnum]) => Option(fun)
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putSchemaOperation: Conversion[Schema.Operation, Option[Schema.Operation]] = (fun: Schema.Operation) => Option(fun)
		given putSchemaSslCertDetail: Conversion[Schema.SslCertDetail, Option[Schema.SslCertDetail]] = (fun: Schema.SslCertDetail) => Option(fun)
		given putListSchemaTier: Conversion[List[Schema.Tier], Option[List[Schema.Tier]]] = (fun: List[Schema.Tier]) => Option(fun)
		given putSchemaUserTypeEnum: Conversion[Schema.User.TypeEnum, Option[Schema.User.TypeEnum]] = (fun: Schema.User.TypeEnum) => Option(fun)
		given putSchemaSqlServerUserDetails: Conversion[Schema.SqlServerUserDetails, Option[Schema.SqlServerUserDetails]] = (fun: Schema.SqlServerUserDetails) => Option(fun)
		given putSchemaUserPasswordValidationPolicy: Conversion[Schema.UserPasswordValidationPolicy, Option[Schema.UserPasswordValidationPolicy]] = (fun: Schema.UserPasswordValidationPolicy) => Option(fun)
		given putSchemaUserDualPasswordTypeEnum: Conversion[Schema.User.DualPasswordTypeEnum, Option[Schema.User.DualPasswordTypeEnum]] = (fun: Schema.User.DualPasswordTypeEnum) => Option(fun)
		given putSchemaPasswordStatus: Conversion[Schema.PasswordStatus, Option[Schema.PasswordStatus]] = (fun: Schema.PasswordStatus) => Option(fun)
		given putListSchemaUser: Conversion[List[Schema.User], Option[List[Schema.User]]] = (fun: List[Schema.User]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
