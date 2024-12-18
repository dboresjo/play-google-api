package com.boresjo.play.api.google.sqladmin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtOperationStatusEnum: Format[Schema.Operation.StatusEnum] = JsonEnumFormat[Schema.Operation.StatusEnum]
	given fmtOperationErrors: Format[Schema.OperationErrors] = Json.format[Schema.OperationErrors]
	given fmtApiWarning: Format[Schema.ApiWarning] = Json.format[Schema.ApiWarning]
	given fmtOperationOperationTypeEnum: Format[Schema.Operation.OperationTypeEnum] = JsonEnumFormat[Schema.Operation.OperationTypeEnum]
	given fmtImportContext: Format[Schema.ImportContext] = Json.format[Schema.ImportContext]
	given fmtExportContext: Format[Schema.ExportContext] = Json.format[Schema.ExportContext]
	given fmtBackupContext: Format[Schema.BackupContext] = Json.format[Schema.BackupContext]
	given fmtAcquireSsrsLeaseContext: Format[Schema.AcquireSsrsLeaseContext] = Json.format[Schema.AcquireSsrsLeaseContext]
	given fmtOperationError: Format[Schema.OperationError] = Json.format[Schema.OperationError]
	given fmtApiWarningCodeEnum: Format[Schema.ApiWarning.CodeEnum] = JsonEnumFormat[Schema.ApiWarning.CodeEnum]
	given fmtImportContextFileTypeEnum: Format[Schema.ImportContext.FileTypeEnum] = JsonEnumFormat[Schema.ImportContext.FileTypeEnum]
	given fmtImportContextCsvImportOptionsItem: Format[Schema.ImportContext.CsvImportOptionsItem] = Json.format[Schema.ImportContext.CsvImportOptionsItem]
	given fmtImportContextBakImportOptionsItem: Format[Schema.ImportContext.BakImportOptionsItem] = Json.format[Schema.ImportContext.BakImportOptionsItem]
	given fmtImportContextBakImportOptionsItemEncryptionOptionsItem: Format[Schema.ImportContext.BakImportOptionsItem.EncryptionOptionsItem] = Json.format[Schema.ImportContext.BakImportOptionsItem.EncryptionOptionsItem]
	given fmtImportContextBakImportOptionsItemBakTypeEnum: Format[Schema.ImportContext.BakImportOptionsItem.BakTypeEnum] = JsonEnumFormat[Schema.ImportContext.BakImportOptionsItem.BakTypeEnum]
	given fmtImportContextSqlImportOptionsItem: Format[Schema.ImportContext.SqlImportOptionsItem] = Json.format[Schema.ImportContext.SqlImportOptionsItem]
	given fmtImportContextSqlImportOptionsItemPostgresImportOptionsItem: Format[Schema.ImportContext.SqlImportOptionsItem.PostgresImportOptionsItem] = Json.format[Schema.ImportContext.SqlImportOptionsItem.PostgresImportOptionsItem]
	given fmtExportContextSqlExportOptionsItem: Format[Schema.ExportContext.SqlExportOptionsItem] = Json.format[Schema.ExportContext.SqlExportOptionsItem]
	given fmtExportContextSqlExportOptionsItemMysqlExportOptionsItem: Format[Schema.ExportContext.SqlExportOptionsItem.MysqlExportOptionsItem] = Json.format[Schema.ExportContext.SqlExportOptionsItem.MysqlExportOptionsItem]
	given fmtExportContextSqlExportOptionsItemPostgresExportOptionsItem: Format[Schema.ExportContext.SqlExportOptionsItem.PostgresExportOptionsItem] = Json.format[Schema.ExportContext.SqlExportOptionsItem.PostgresExportOptionsItem]
	given fmtExportContextCsvExportOptionsItem: Format[Schema.ExportContext.CsvExportOptionsItem] = Json.format[Schema.ExportContext.CsvExportOptionsItem]
	given fmtExportContextFileTypeEnum: Format[Schema.ExportContext.FileTypeEnum] = JsonEnumFormat[Schema.ExportContext.FileTypeEnum]
	given fmtExportContextBakExportOptionsItem: Format[Schema.ExportContext.BakExportOptionsItem] = Json.format[Schema.ExportContext.BakExportOptionsItem]
	given fmtExportContextBakExportOptionsItemBakTypeEnum: Format[Schema.ExportContext.BakExportOptionsItem.BakTypeEnum] = JsonEnumFormat[Schema.ExportContext.BakExportOptionsItem.BakTypeEnum]
	given fmtInstancesCloneRequest: Format[Schema.InstancesCloneRequest] = Json.format[Schema.InstancesCloneRequest]
	given fmtCloneContext: Format[Schema.CloneContext] = Json.format[Schema.CloneContext]
	given fmtBinLogCoordinates: Format[Schema.BinLogCoordinates] = Json.format[Schema.BinLogCoordinates]
	given fmtInstancesDemoteMasterRequest: Format[Schema.InstancesDemoteMasterRequest] = Json.format[Schema.InstancesDemoteMasterRequest]
	given fmtDemoteMasterContext: Format[Schema.DemoteMasterContext] = Json.format[Schema.DemoteMasterContext]
	given fmtDemoteMasterConfiguration: Format[Schema.DemoteMasterConfiguration] = Json.format[Schema.DemoteMasterConfiguration]
	given fmtDemoteMasterMySqlReplicaConfiguration: Format[Schema.DemoteMasterMySqlReplicaConfiguration] = Json.format[Schema.DemoteMasterMySqlReplicaConfiguration]
	given fmtInstancesDemoteRequest: Format[Schema.InstancesDemoteRequest] = Json.format[Schema.InstancesDemoteRequest]
	given fmtDemoteContext: Format[Schema.DemoteContext] = Json.format[Schema.DemoteContext]
	given fmtInstancesExportRequest: Format[Schema.InstancesExportRequest] = Json.format[Schema.InstancesExportRequest]
	given fmtInstancesFailoverRequest: Format[Schema.InstancesFailoverRequest] = Json.format[Schema.InstancesFailoverRequest]
	given fmtFailoverContext: Format[Schema.FailoverContext] = Json.format[Schema.FailoverContext]
	given fmtInstancesReencryptRequest: Format[Schema.InstancesReencryptRequest] = Json.format[Schema.InstancesReencryptRequest]
	given fmtBackupReencryptionConfig: Format[Schema.BackupReencryptionConfig] = Json.format[Schema.BackupReencryptionConfig]
	given fmtBackupReencryptionConfigBackupTypeEnum: Format[Schema.BackupReencryptionConfig.BackupTypeEnum] = JsonEnumFormat[Schema.BackupReencryptionConfig.BackupTypeEnum]
	given fmtDatabaseInstance: Format[Schema.DatabaseInstance] = Json.format[Schema.DatabaseInstance]
	given fmtDatabaseInstanceStateEnum: Format[Schema.DatabaseInstance.StateEnum] = JsonEnumFormat[Schema.DatabaseInstance.StateEnum]
	given fmtDatabaseInstanceDatabaseVersionEnum: Format[Schema.DatabaseInstance.DatabaseVersionEnum] = JsonEnumFormat[Schema.DatabaseInstance.DatabaseVersionEnum]
	given fmtSettings: Format[Schema.Settings] = Json.format[Schema.Settings]
	given fmtDatabaseInstanceFailoverReplicaItem: Format[Schema.DatabaseInstance.FailoverReplicaItem] = Json.format[Schema.DatabaseInstance.FailoverReplicaItem]
	given fmtIpMapping: Format[Schema.IpMapping] = Json.format[Schema.IpMapping]
	given fmtSslCert: Format[Schema.SslCert] = Json.format[Schema.SslCert]
	given fmtDatabaseInstanceInstanceTypeEnum: Format[Schema.DatabaseInstance.InstanceTypeEnum] = JsonEnumFormat[Schema.DatabaseInstance.InstanceTypeEnum]
	given fmtOnPremisesConfiguration: Format[Schema.OnPremisesConfiguration] = Json.format[Schema.OnPremisesConfiguration]
	given fmtReplicaConfiguration: Format[Schema.ReplicaConfiguration] = Json.format[Schema.ReplicaConfiguration]
	given fmtDatabaseInstanceBackendTypeEnum: Format[Schema.DatabaseInstance.BackendTypeEnum] = JsonEnumFormat[Schema.DatabaseInstance.BackendTypeEnum]
	given fmtDatabaseInstanceSuspensionReasonEnum: Format[Schema.DatabaseInstance.SuspensionReasonEnum] = JsonEnumFormat[Schema.DatabaseInstance.SuspensionReasonEnum]
	given fmtDiskEncryptionConfiguration: Format[Schema.DiskEncryptionConfiguration] = Json.format[Schema.DiskEncryptionConfiguration]
	given fmtDiskEncryptionStatus: Format[Schema.DiskEncryptionStatus] = Json.format[Schema.DiskEncryptionStatus]
	given fmtSqlScheduledMaintenance: Format[Schema.SqlScheduledMaintenance] = Json.format[Schema.SqlScheduledMaintenance]
	given fmtSqlOutOfDiskReport: Format[Schema.SqlOutOfDiskReport] = Json.format[Schema.SqlOutOfDiskReport]
	given fmtAvailableDatabaseVersion: Format[Schema.AvailableDatabaseVersion] = Json.format[Schema.AvailableDatabaseVersion]
	given fmtDatabaseInstanceSqlNetworkArchitectureEnum: Format[Schema.DatabaseInstance.SqlNetworkArchitectureEnum] = JsonEnumFormat[Schema.DatabaseInstance.SqlNetworkArchitectureEnum]
	given fmtReplicationCluster: Format[Schema.ReplicationCluster] = Json.format[Schema.ReplicationCluster]
	given fmtGeminiInstanceConfig: Format[Schema.GeminiInstanceConfig] = Json.format[Schema.GeminiInstanceConfig]
	given fmtSettingsAvailabilityTypeEnum: Format[Schema.Settings.AvailabilityTypeEnum] = JsonEnumFormat[Schema.Settings.AvailabilityTypeEnum]
	given fmtSettingsPricingPlanEnum: Format[Schema.Settings.PricingPlanEnum] = JsonEnumFormat[Schema.Settings.PricingPlanEnum]
	given fmtSettingsReplicationTypeEnum: Format[Schema.Settings.ReplicationTypeEnum] = JsonEnumFormat[Schema.Settings.ReplicationTypeEnum]
	given fmtSettingsActivationPolicyEnum: Format[Schema.Settings.ActivationPolicyEnum] = JsonEnumFormat[Schema.Settings.ActivationPolicyEnum]
	given fmtIpConfiguration: Format[Schema.IpConfiguration] = Json.format[Schema.IpConfiguration]
	given fmtLocationPreference: Format[Schema.LocationPreference] = Json.format[Schema.LocationPreference]
	given fmtDatabaseFlags: Format[Schema.DatabaseFlags] = Json.format[Schema.DatabaseFlags]
	given fmtSettingsDataDiskTypeEnum: Format[Schema.Settings.DataDiskTypeEnum] = JsonEnumFormat[Schema.Settings.DataDiskTypeEnum]
	given fmtMaintenanceWindow: Format[Schema.MaintenanceWindow] = Json.format[Schema.MaintenanceWindow]
	given fmtBackupConfiguration: Format[Schema.BackupConfiguration] = Json.format[Schema.BackupConfiguration]
	given fmtSqlActiveDirectoryConfig: Format[Schema.SqlActiveDirectoryConfig] = Json.format[Schema.SqlActiveDirectoryConfig]
	given fmtDenyMaintenancePeriod: Format[Schema.DenyMaintenancePeriod] = Json.format[Schema.DenyMaintenancePeriod]
	given fmtInsightsConfig: Format[Schema.InsightsConfig] = Json.format[Schema.InsightsConfig]
	given fmtPasswordValidationPolicy: Format[Schema.PasswordValidationPolicy] = Json.format[Schema.PasswordValidationPolicy]
	given fmtSqlServerAuditConfig: Format[Schema.SqlServerAuditConfig] = Json.format[Schema.SqlServerAuditConfig]
	given fmtSettingsEditionEnum: Format[Schema.Settings.EditionEnum] = JsonEnumFormat[Schema.Settings.EditionEnum]
	given fmtSettingsConnectorEnforcementEnum: Format[Schema.Settings.ConnectorEnforcementEnum] = JsonEnumFormat[Schema.Settings.ConnectorEnforcementEnum]
	given fmtAdvancedMachineFeatures: Format[Schema.AdvancedMachineFeatures] = Json.format[Schema.AdvancedMachineFeatures]
	given fmtDataCacheConfig: Format[Schema.DataCacheConfig] = Json.format[Schema.DataCacheConfig]
	given fmtAclEntry: Format[Schema.AclEntry] = Json.format[Schema.AclEntry]
	given fmtIpConfigurationSslModeEnum: Format[Schema.IpConfiguration.SslModeEnum] = JsonEnumFormat[Schema.IpConfiguration.SslModeEnum]
	given fmtPscConfig: Format[Schema.PscConfig] = Json.format[Schema.PscConfig]
	given fmtIpConfigurationServerCaModeEnum: Format[Schema.IpConfiguration.ServerCaModeEnum] = JsonEnumFormat[Schema.IpConfiguration.ServerCaModeEnum]
	given fmtPscAutoConnectionConfig: Format[Schema.PscAutoConnectionConfig] = Json.format[Schema.PscAutoConnectionConfig]
	given fmtMaintenanceWindowUpdateTrackEnum: Format[Schema.MaintenanceWindow.UpdateTrackEnum] = JsonEnumFormat[Schema.MaintenanceWindow.UpdateTrackEnum]
	given fmtBackupRetentionSettings: Format[Schema.BackupRetentionSettings] = Json.format[Schema.BackupRetentionSettings]
	given fmtBackupConfigurationTransactionalLogStorageStateEnum: Format[Schema.BackupConfiguration.TransactionalLogStorageStateEnum] = JsonEnumFormat[Schema.BackupConfiguration.TransactionalLogStorageStateEnum]
	given fmtBackupRetentionSettingsRetentionUnitEnum: Format[Schema.BackupRetentionSettings.RetentionUnitEnum] = JsonEnumFormat[Schema.BackupRetentionSettings.RetentionUnitEnum]
	given fmtPasswordValidationPolicyComplexityEnum: Format[Schema.PasswordValidationPolicy.ComplexityEnum] = JsonEnumFormat[Schema.PasswordValidationPolicy.ComplexityEnum]
	given fmtIpMappingTypeEnum: Format[Schema.IpMapping.TypeEnum] = JsonEnumFormat[Schema.IpMapping.TypeEnum]
	given fmtInstanceReference: Format[Schema.InstanceReference] = Json.format[Schema.InstanceReference]
	given fmtSelectedObjects: Format[Schema.SelectedObjects] = Json.format[Schema.SelectedObjects]
	given fmtOnPremisesConfigurationSslOptionEnum: Format[Schema.OnPremisesConfiguration.SslOptionEnum] = JsonEnumFormat[Schema.OnPremisesConfiguration.SslOptionEnum]
	given fmtMySqlReplicaConfiguration: Format[Schema.MySqlReplicaConfiguration] = Json.format[Schema.MySqlReplicaConfiguration]
	given fmtSqlOutOfDiskReportSqlOutOfDiskStateEnum: Format[Schema.SqlOutOfDiskReport.SqlOutOfDiskStateEnum] = JsonEnumFormat[Schema.SqlOutOfDiskReport.SqlOutOfDiskStateEnum]
	given fmtInstancesImportRequest: Format[Schema.InstancesImportRequest] = Json.format[Schema.InstancesImportRequest]
	given fmtInstancesListResponse: Format[Schema.InstancesListResponse] = Json.format[Schema.InstancesListResponse]
	given fmtInstancesListServerCasResponse: Format[Schema.InstancesListServerCasResponse] = Json.format[Schema.InstancesListServerCasResponse]
	given fmtInstancesListServerCertificatesResponse: Format[Schema.InstancesListServerCertificatesResponse] = Json.format[Schema.InstancesListServerCertificatesResponse]
	given fmtInstancesRestoreBackupRequest: Format[Schema.InstancesRestoreBackupRequest] = Json.format[Schema.InstancesRestoreBackupRequest]
	given fmtRestoreBackupContext: Format[Schema.RestoreBackupContext] = Json.format[Schema.RestoreBackupContext]
	given fmtInstancesRotateServerCaRequest: Format[Schema.InstancesRotateServerCaRequest] = Json.format[Schema.InstancesRotateServerCaRequest]
	given fmtRotateServerCaContext: Format[Schema.RotateServerCaContext] = Json.format[Schema.RotateServerCaContext]
	given fmtInstancesRotateServerCertificateRequest: Format[Schema.InstancesRotateServerCertificateRequest] = Json.format[Schema.InstancesRotateServerCertificateRequest]
	given fmtRotateServerCertificateContext: Format[Schema.RotateServerCertificateContext] = Json.format[Schema.RotateServerCertificateContext]
	given fmtInstancesTruncateLogRequest: Format[Schema.InstancesTruncateLogRequest] = Json.format[Schema.InstancesTruncateLogRequest]
	given fmtTruncateLogContext: Format[Schema.TruncateLogContext] = Json.format[Schema.TruncateLogContext]
	given fmtSslCertsCreateEphemeralRequest: Format[Schema.SslCertsCreateEphemeralRequest] = Json.format[Schema.SslCertsCreateEphemeralRequest]
	given fmtSqlInstancesRescheduleMaintenanceRequestBody: Format[Schema.SqlInstancesRescheduleMaintenanceRequestBody] = Json.format[Schema.SqlInstancesRescheduleMaintenanceRequestBody]
	given fmtReschedule: Format[Schema.Reschedule] = Json.format[Schema.Reschedule]
	given fmtRescheduleRescheduleTypeEnum: Format[Schema.Reschedule.RescheduleTypeEnum] = JsonEnumFormat[Schema.Reschedule.RescheduleTypeEnum]
	given fmtSqlInstancesVerifyExternalSyncSettingsRequest: Format[Schema.SqlInstancesVerifyExternalSyncSettingsRequest] = Json.format[Schema.SqlInstancesVerifyExternalSyncSettingsRequest]
	given fmtSqlInstancesVerifyExternalSyncSettingsRequestSyncModeEnum: Format[Schema.SqlInstancesVerifyExternalSyncSettingsRequest.SyncModeEnum] = JsonEnumFormat[Schema.SqlInstancesVerifyExternalSyncSettingsRequest.SyncModeEnum]
	given fmtMySqlSyncConfig: Format[Schema.MySqlSyncConfig] = Json.format[Schema.MySqlSyncConfig]
	given fmtSqlInstancesVerifyExternalSyncSettingsRequestMigrationTypeEnum: Format[Schema.SqlInstancesVerifyExternalSyncSettingsRequest.MigrationTypeEnum] = JsonEnumFormat[Schema.SqlInstancesVerifyExternalSyncSettingsRequest.MigrationTypeEnum]
	given fmtSqlInstancesVerifyExternalSyncSettingsRequestSyncParallelLevelEnum: Format[Schema.SqlInstancesVerifyExternalSyncSettingsRequest.SyncParallelLevelEnum] = JsonEnumFormat[Schema.SqlInstancesVerifyExternalSyncSettingsRequest.SyncParallelLevelEnum]
	given fmtExternalSyncSelectedObject: Format[Schema.ExternalSyncSelectedObject] = Json.format[Schema.ExternalSyncSelectedObject]
	given fmtSyncFlags: Format[Schema.SyncFlags] = Json.format[Schema.SyncFlags]
	given fmtSqlInstancesVerifyExternalSyncSettingsResponse: Format[Schema.SqlInstancesVerifyExternalSyncSettingsResponse] = Json.format[Schema.SqlInstancesVerifyExternalSyncSettingsResponse]
	given fmtSqlExternalSyncSettingError: Format[Schema.SqlExternalSyncSettingError] = Json.format[Schema.SqlExternalSyncSettingError]
	given fmtSqlExternalSyncSettingErrorTypeEnum: Format[Schema.SqlExternalSyncSettingError.TypeEnum] = JsonEnumFormat[Schema.SqlExternalSyncSettingError.TypeEnum]
	given fmtSqlInstancesStartExternalSyncRequest: Format[Schema.SqlInstancesStartExternalSyncRequest] = Json.format[Schema.SqlInstancesStartExternalSyncRequest]
	given fmtSqlInstancesStartExternalSyncRequestSyncModeEnum: Format[Schema.SqlInstancesStartExternalSyncRequest.SyncModeEnum] = JsonEnumFormat[Schema.SqlInstancesStartExternalSyncRequest.SyncModeEnum]
	given fmtSqlInstancesStartExternalSyncRequestSyncParallelLevelEnum: Format[Schema.SqlInstancesStartExternalSyncRequest.SyncParallelLevelEnum] = JsonEnumFormat[Schema.SqlInstancesStartExternalSyncRequest.SyncParallelLevelEnum]
	given fmtSqlInstancesStartExternalSyncRequestMigrationTypeEnum: Format[Schema.SqlInstancesStartExternalSyncRequest.MigrationTypeEnum] = JsonEnumFormat[Schema.SqlInstancesStartExternalSyncRequest.MigrationTypeEnum]
	given fmtPerformDiskShrinkContext: Format[Schema.PerformDiskShrinkContext] = Json.format[Schema.PerformDiskShrinkContext]
	given fmtSqlInstancesGetDiskShrinkConfigResponse: Format[Schema.SqlInstancesGetDiskShrinkConfigResponse] = Json.format[Schema.SqlInstancesGetDiskShrinkConfigResponse]
	given fmtSqlInstancesResetReplicaSizeRequest: Format[Schema.SqlInstancesResetReplicaSizeRequest] = Json.format[Schema.SqlInstancesResetReplicaSizeRequest]
	given fmtSqlInstancesGetLatestRecoveryTimeResponse: Format[Schema.SqlInstancesGetLatestRecoveryTimeResponse] = Json.format[Schema.SqlInstancesGetLatestRecoveryTimeResponse]
	given fmtInstancesAcquireSsrsLeaseRequest: Format[Schema.InstancesAcquireSsrsLeaseRequest] = Json.format[Schema.InstancesAcquireSsrsLeaseRequest]
	given fmtSqlInstancesAcquireSsrsLeaseResponse: Format[Schema.SqlInstancesAcquireSsrsLeaseResponse] = Json.format[Schema.SqlInstancesAcquireSsrsLeaseResponse]
	given fmtSqlInstancesReleaseSsrsLeaseResponse: Format[Schema.SqlInstancesReleaseSsrsLeaseResponse] = Json.format[Schema.SqlInstancesReleaseSsrsLeaseResponse]
	given fmtBackupRun: Format[Schema.BackupRun] = Json.format[Schema.BackupRun]
	given fmtBackupRunStatusEnum: Format[Schema.BackupRun.StatusEnum] = JsonEnumFormat[Schema.BackupRun.StatusEnum]
	given fmtBackupRunTypeEnum: Format[Schema.BackupRun.TypeEnum] = JsonEnumFormat[Schema.BackupRun.TypeEnum]
	given fmtBackupRunBackupKindEnum: Format[Schema.BackupRun.BackupKindEnum] = JsonEnumFormat[Schema.BackupRun.BackupKindEnum]
	given fmtBackupRunsListResponse: Format[Schema.BackupRunsListResponse] = Json.format[Schema.BackupRunsListResponse]
	given fmtConnectSettings: Format[Schema.ConnectSettings] = Json.format[Schema.ConnectSettings]
	given fmtConnectSettingsDatabaseVersionEnum: Format[Schema.ConnectSettings.DatabaseVersionEnum] = JsonEnumFormat[Schema.ConnectSettings.DatabaseVersionEnum]
	given fmtConnectSettingsBackendTypeEnum: Format[Schema.ConnectSettings.BackendTypeEnum] = JsonEnumFormat[Schema.ConnectSettings.BackendTypeEnum]
	given fmtConnectSettingsServerCaModeEnum: Format[Schema.ConnectSettings.ServerCaModeEnum] = JsonEnumFormat[Schema.ConnectSettings.ServerCaModeEnum]
	given fmtGenerateEphemeralCertRequest: Format[Schema.GenerateEphemeralCertRequest] = Json.format[Schema.GenerateEphemeralCertRequest]
	given fmtGenerateEphemeralCertResponse: Format[Schema.GenerateEphemeralCertResponse] = Json.format[Schema.GenerateEphemeralCertResponse]
	given fmtDatabase: Format[Schema.Database] = Json.format[Schema.Database]
	given fmtSqlServerDatabaseDetails: Format[Schema.SqlServerDatabaseDetails] = Json.format[Schema.SqlServerDatabaseDetails]
	given fmtDatabasesListResponse: Format[Schema.DatabasesListResponse] = Json.format[Schema.DatabasesListResponse]
	given fmtFlagsListResponse: Format[Schema.FlagsListResponse] = Json.format[Schema.FlagsListResponse]
	given fmtFlag: Format[Schema.Flag] = Json.format[Schema.Flag]
	given fmtFlagTypeEnum: Format[Schema.Flag.TypeEnum] = JsonEnumFormat[Schema.Flag.TypeEnum]
	given fmtFlagAppliesToEnum: Format[Schema.Flag.AppliesToEnum] = JsonEnumFormat[Schema.Flag.AppliesToEnum]
	given fmtOperationsListResponse: Format[Schema.OperationsListResponse] = Json.format[Schema.OperationsListResponse]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtSslCertsInsertRequest: Format[Schema.SslCertsInsertRequest] = Json.format[Schema.SslCertsInsertRequest]
	given fmtSslCertsInsertResponse: Format[Schema.SslCertsInsertResponse] = Json.format[Schema.SslCertsInsertResponse]
	given fmtSslCertDetail: Format[Schema.SslCertDetail] = Json.format[Schema.SslCertDetail]
	given fmtSslCertsListResponse: Format[Schema.SslCertsListResponse] = Json.format[Schema.SslCertsListResponse]
	given fmtTiersListResponse: Format[Schema.TiersListResponse] = Json.format[Schema.TiersListResponse]
	given fmtTier: Format[Schema.Tier] = Json.format[Schema.Tier]
	given fmtUser: Format[Schema.User] = Json.format[Schema.User]
	given fmtUserTypeEnum: Format[Schema.User.TypeEnum] = JsonEnumFormat[Schema.User.TypeEnum]
	given fmtSqlServerUserDetails: Format[Schema.SqlServerUserDetails] = Json.format[Schema.SqlServerUserDetails]
	given fmtUserPasswordValidationPolicy: Format[Schema.UserPasswordValidationPolicy] = Json.format[Schema.UserPasswordValidationPolicy]
	given fmtUserDualPasswordTypeEnum: Format[Schema.User.DualPasswordTypeEnum] = JsonEnumFormat[Schema.User.DualPasswordTypeEnum]
	given fmtPasswordStatus: Format[Schema.PasswordStatus] = Json.format[Schema.PasswordStatus]
	given fmtUsersListResponse: Format[Schema.UsersListResponse] = Json.format[Schema.UsersListResponse]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
}
