package com.boresjo.play.api.google.datamigration

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtListMigrationJobsResponse: Format[Schema.ListMigrationJobsResponse] = Json.format[Schema.ListMigrationJobsResponse]
	given fmtMigrationJob: Format[Schema.MigrationJob] = Json.format[Schema.MigrationJob]
	given fmtMigrationJobStateEnum: Format[Schema.MigrationJob.StateEnum] = JsonEnumFormat[Schema.MigrationJob.StateEnum]
	given fmtMigrationJobPhaseEnum: Format[Schema.MigrationJob.PhaseEnum] = JsonEnumFormat[Schema.MigrationJob.PhaseEnum]
	given fmtMigrationJobTypeEnum: Format[Schema.MigrationJob.TypeEnum] = JsonEnumFormat[Schema.MigrationJob.TypeEnum]
	given fmtDumpFlags: Format[Schema.DumpFlags] = Json.format[Schema.DumpFlags]
	given fmtReverseSshConnectivity: Format[Schema.ReverseSshConnectivity] = Json.format[Schema.ReverseSshConnectivity]
	given fmtVpcPeeringConnectivity: Format[Schema.VpcPeeringConnectivity] = Json.format[Schema.VpcPeeringConnectivity]
	given fmtStaticIpConnectivity: Format[Schema.StaticIpConnectivity] = Json.format[Schema.StaticIpConnectivity]
	given fmtDatabaseType: Format[Schema.DatabaseType] = Json.format[Schema.DatabaseType]
	given fmtConversionWorkspaceInfo: Format[Schema.ConversionWorkspaceInfo] = Json.format[Schema.ConversionWorkspaceInfo]
	given fmtPerformanceConfig: Format[Schema.PerformanceConfig] = Json.format[Schema.PerformanceConfig]
	given fmtSqlServerHomogeneousMigrationJobConfig: Format[Schema.SqlServerHomogeneousMigrationJobConfig] = Json.format[Schema.SqlServerHomogeneousMigrationJobConfig]
	given fmtMigrationJobDumpTypeEnum: Format[Schema.MigrationJob.DumpTypeEnum] = JsonEnumFormat[Schema.MigrationJob.DumpTypeEnum]
	given fmtOracleToPostgresConfig: Format[Schema.OracleToPostgresConfig] = Json.format[Schema.OracleToPostgresConfig]
	given fmtDumpFlag: Format[Schema.DumpFlag] = Json.format[Schema.DumpFlag]
	given fmtDatabaseTypeProviderEnum: Format[Schema.DatabaseType.ProviderEnum] = JsonEnumFormat[Schema.DatabaseType.ProviderEnum]
	given fmtDatabaseTypeEngineEnum: Format[Schema.DatabaseType.EngineEnum] = JsonEnumFormat[Schema.DatabaseType.EngineEnum]
	given fmtPerformanceConfigDumpParallelLevelEnum: Format[Schema.PerformanceConfig.DumpParallelLevelEnum] = JsonEnumFormat[Schema.PerformanceConfig.DumpParallelLevelEnum]
	given fmtSqlServerDatabaseBackup: Format[Schema.SqlServerDatabaseBackup] = Json.format[Schema.SqlServerDatabaseBackup]
	given fmtSqlServerEncryptionOptions: Format[Schema.SqlServerEncryptionOptions] = Json.format[Schema.SqlServerEncryptionOptions]
	given fmtOracleSourceConfig: Format[Schema.OracleSourceConfig] = Json.format[Schema.OracleSourceConfig]
	given fmtPostgresDestinationConfig: Format[Schema.PostgresDestinationConfig] = Json.format[Schema.PostgresDestinationConfig]
	given fmtLogMiner: Format[Schema.LogMiner] = Json.format[Schema.LogMiner]
	given fmtBinaryLogParser: Format[Schema.BinaryLogParser] = Json.format[Schema.BinaryLogParser]
	given fmtOracleAsmLogFileAccess: Format[Schema.OracleAsmLogFileAccess] = Json.format[Schema.OracleAsmLogFileAccess]
	given fmtLogFileDirectories: Format[Schema.LogFileDirectories] = Json.format[Schema.LogFileDirectories]
	given fmtStartMigrationJobRequest: Format[Schema.StartMigrationJobRequest] = Json.format[Schema.StartMigrationJobRequest]
	given fmtStopMigrationJobRequest: Format[Schema.StopMigrationJobRequest] = Json.format[Schema.StopMigrationJobRequest]
	given fmtResumeMigrationJobRequest: Format[Schema.ResumeMigrationJobRequest] = Json.format[Schema.ResumeMigrationJobRequest]
	given fmtPromoteMigrationJobRequest: Format[Schema.PromoteMigrationJobRequest] = Json.format[Schema.PromoteMigrationJobRequest]
	given fmtDemoteDestinationRequest: Format[Schema.DemoteDestinationRequest] = Json.format[Schema.DemoteDestinationRequest]
	given fmtVerifyMigrationJobRequest: Format[Schema.VerifyMigrationJobRequest] = Json.format[Schema.VerifyMigrationJobRequest]
	given fmtRestartMigrationJobRequest: Format[Schema.RestartMigrationJobRequest] = Json.format[Schema.RestartMigrationJobRequest]
	given fmtGenerateSshScriptRequest: Format[Schema.GenerateSshScriptRequest] = Json.format[Schema.GenerateSshScriptRequest]
	given fmtVmCreationConfig: Format[Schema.VmCreationConfig] = Json.format[Schema.VmCreationConfig]
	given fmtVmSelectionConfig: Format[Schema.VmSelectionConfig] = Json.format[Schema.VmSelectionConfig]
	given fmtSshScript: Format[Schema.SshScript] = Json.format[Schema.SshScript]
	given fmtGenerateTcpProxyScriptRequest: Format[Schema.GenerateTcpProxyScriptRequest] = Json.format[Schema.GenerateTcpProxyScriptRequest]
	given fmtTcpProxyScript: Format[Schema.TcpProxyScript] = Json.format[Schema.TcpProxyScript]
	given fmtListConnectionProfilesResponse: Format[Schema.ListConnectionProfilesResponse] = Json.format[Schema.ListConnectionProfilesResponse]
	given fmtConnectionProfile: Format[Schema.ConnectionProfile] = Json.format[Schema.ConnectionProfile]
	given fmtConnectionProfileStateEnum: Format[Schema.ConnectionProfile.StateEnum] = JsonEnumFormat[Schema.ConnectionProfile.StateEnum]
	given fmtConnectionProfileRoleEnum: Format[Schema.ConnectionProfile.RoleEnum] = JsonEnumFormat[Schema.ConnectionProfile.RoleEnum]
	given fmtMySqlConnectionProfile: Format[Schema.MySqlConnectionProfile] = Json.format[Schema.MySqlConnectionProfile]
	given fmtPostgreSqlConnectionProfile: Format[Schema.PostgreSqlConnectionProfile] = Json.format[Schema.PostgreSqlConnectionProfile]
	given fmtSqlServerConnectionProfile: Format[Schema.SqlServerConnectionProfile] = Json.format[Schema.SqlServerConnectionProfile]
	given fmtOracleConnectionProfile: Format[Schema.OracleConnectionProfile] = Json.format[Schema.OracleConnectionProfile]
	given fmtCloudSqlConnectionProfile: Format[Schema.CloudSqlConnectionProfile] = Json.format[Schema.CloudSqlConnectionProfile]
	given fmtAlloyDbConnectionProfile: Format[Schema.AlloyDbConnectionProfile] = Json.format[Schema.AlloyDbConnectionProfile]
	given fmtConnectionProfileProviderEnum: Format[Schema.ConnectionProfile.ProviderEnum] = JsonEnumFormat[Schema.ConnectionProfile.ProviderEnum]
	given fmtSslConfig: Format[Schema.SslConfig] = Json.format[Schema.SslConfig]
	given fmtSslConfigTypeEnum: Format[Schema.SslConfig.TypeEnum] = JsonEnumFormat[Schema.SslConfig.TypeEnum]
	given fmtPostgreSqlConnectionProfileNetworkArchitectureEnum: Format[Schema.PostgreSqlConnectionProfile.NetworkArchitectureEnum] = JsonEnumFormat[Schema.PostgreSqlConnectionProfile.NetworkArchitectureEnum]
	given fmtPrivateServiceConnectConnectivity: Format[Schema.PrivateServiceConnectConnectivity] = Json.format[Schema.PrivateServiceConnectConnectivity]
	given fmtSqlServerBackups: Format[Schema.SqlServerBackups] = Json.format[Schema.SqlServerBackups]
	given fmtForwardSshTunnelConnectivity: Format[Schema.ForwardSshTunnelConnectivity] = Json.format[Schema.ForwardSshTunnelConnectivity]
	given fmtPrivateConnectivity: Format[Schema.PrivateConnectivity] = Json.format[Schema.PrivateConnectivity]
	given fmtStaticServiceIpConnectivity: Format[Schema.StaticServiceIpConnectivity] = Json.format[Schema.StaticServiceIpConnectivity]
	given fmtOracleAsmConfig: Format[Schema.OracleAsmConfig] = Json.format[Schema.OracleAsmConfig]
	given fmtCloudSqlSettings: Format[Schema.CloudSqlSettings] = Json.format[Schema.CloudSqlSettings]
	given fmtCloudSqlSettingsDatabaseVersionEnum: Format[Schema.CloudSqlSettings.DatabaseVersionEnum] = JsonEnumFormat[Schema.CloudSqlSettings.DatabaseVersionEnum]
	given fmtCloudSqlSettingsActivationPolicyEnum: Format[Schema.CloudSqlSettings.ActivationPolicyEnum] = JsonEnumFormat[Schema.CloudSqlSettings.ActivationPolicyEnum]
	given fmtSqlIpConfig: Format[Schema.SqlIpConfig] = Json.format[Schema.SqlIpConfig]
	given fmtCloudSqlSettingsDataDiskTypeEnum: Format[Schema.CloudSqlSettings.DataDiskTypeEnum] = JsonEnumFormat[Schema.CloudSqlSettings.DataDiskTypeEnum]
	given fmtCloudSqlSettingsAvailabilityTypeEnum: Format[Schema.CloudSqlSettings.AvailabilityTypeEnum] = JsonEnumFormat[Schema.CloudSqlSettings.AvailabilityTypeEnum]
	given fmtCloudSqlSettingsEditionEnum: Format[Schema.CloudSqlSettings.EditionEnum] = JsonEnumFormat[Schema.CloudSqlSettings.EditionEnum]
	given fmtDataCacheConfig: Format[Schema.DataCacheConfig] = Json.format[Schema.DataCacheConfig]
	given fmtSqlAclEntry: Format[Schema.SqlAclEntry] = Json.format[Schema.SqlAclEntry]
	given fmtAlloyDbSettings: Format[Schema.AlloyDbSettings] = Json.format[Schema.AlloyDbSettings]
	given fmtUserPassword: Format[Schema.UserPassword] = Json.format[Schema.UserPassword]
	given fmtPrimaryInstanceSettings: Format[Schema.PrimaryInstanceSettings] = Json.format[Schema.PrimaryInstanceSettings]
	given fmtEncryptionConfig: Format[Schema.EncryptionConfig] = Json.format[Schema.EncryptionConfig]
	given fmtAlloyDbSettingsDatabaseVersionEnum: Format[Schema.AlloyDbSettings.DatabaseVersionEnum] = JsonEnumFormat[Schema.AlloyDbSettings.DatabaseVersionEnum]
	given fmtMachineConfig: Format[Schema.MachineConfig] = Json.format[Schema.MachineConfig]
	given fmtInstanceNetworkConfig: Format[Schema.InstanceNetworkConfig] = Json.format[Schema.InstanceNetworkConfig]
	given fmtAuthorizedNetwork: Format[Schema.AuthorizedNetwork] = Json.format[Schema.AuthorizedNetwork]
	given fmtPrivateConnection: Format[Schema.PrivateConnection] = Json.format[Schema.PrivateConnection]
	given fmtPrivateConnectionStateEnum: Format[Schema.PrivateConnection.StateEnum] = JsonEnumFormat[Schema.PrivateConnection.StateEnum]
	given fmtVpcPeeringConfig: Format[Schema.VpcPeeringConfig] = Json.format[Schema.VpcPeeringConfig]
	given fmtListPrivateConnectionsResponse: Format[Schema.ListPrivateConnectionsResponse] = Json.format[Schema.ListPrivateConnectionsResponse]
	given fmtConversionWorkspace: Format[Schema.ConversionWorkspace] = Json.format[Schema.ConversionWorkspace]
	given fmtDatabaseEngineInfo: Format[Schema.DatabaseEngineInfo] = Json.format[Schema.DatabaseEngineInfo]
	given fmtDatabaseEngineInfoEngineEnum: Format[Schema.DatabaseEngineInfo.EngineEnum] = JsonEnumFormat[Schema.DatabaseEngineInfo.EngineEnum]
	given fmtListConversionWorkspacesResponse: Format[Schema.ListConversionWorkspacesResponse] = Json.format[Schema.ListConversionWorkspacesResponse]
	given fmtMappingRule: Format[Schema.MappingRule] = Json.format[Schema.MappingRule]
	given fmtMappingRuleStateEnum: Format[Schema.MappingRule.StateEnum] = JsonEnumFormat[Schema.MappingRule.StateEnum]
	given fmtMappingRuleRuleScopeEnum: Format[Schema.MappingRule.RuleScopeEnum] = JsonEnumFormat[Schema.MappingRule.RuleScopeEnum]
	given fmtMappingRuleFilter: Format[Schema.MappingRuleFilter] = Json.format[Schema.MappingRuleFilter]
	given fmtSingleEntityRename: Format[Schema.SingleEntityRename] = Json.format[Schema.SingleEntityRename]
	given fmtMultiEntityRename: Format[Schema.MultiEntityRename] = Json.format[Schema.MultiEntityRename]
	given fmtEntityMove: Format[Schema.EntityMove] = Json.format[Schema.EntityMove]
	given fmtSingleColumnChange: Format[Schema.SingleColumnChange] = Json.format[Schema.SingleColumnChange]
	given fmtMultiColumnDatatypeChange: Format[Schema.MultiColumnDatatypeChange] = Json.format[Schema.MultiColumnDatatypeChange]
	given fmtConditionalColumnSetValue: Format[Schema.ConditionalColumnSetValue] = Json.format[Schema.ConditionalColumnSetValue]
	given fmtConvertRowIdToColumn: Format[Schema.ConvertRowIdToColumn] = Json.format[Schema.ConvertRowIdToColumn]
	given fmtSetTablePrimaryKey: Format[Schema.SetTablePrimaryKey] = Json.format[Schema.SetTablePrimaryKey]
	given fmtSinglePackageChange: Format[Schema.SinglePackageChange] = Json.format[Schema.SinglePackageChange]
	given fmtSourceSqlChange: Format[Schema.SourceSqlChange] = Json.format[Schema.SourceSqlChange]
	given fmtFilterTableColumns: Format[Schema.FilterTableColumns] = Json.format[Schema.FilterTableColumns]
	given fmtMultiEntityRenameSourceNameTransformationEnum: Format[Schema.MultiEntityRename.SourceNameTransformationEnum] = JsonEnumFormat[Schema.MultiEntityRename.SourceNameTransformationEnum]
	given fmtSourceTextFilter: Format[Schema.SourceTextFilter] = Json.format[Schema.SourceTextFilter]
	given fmtSourceNumericFilter: Format[Schema.SourceNumericFilter] = Json.format[Schema.SourceNumericFilter]
	given fmtSourceNumericFilterNumericFilterOptionEnum: Format[Schema.SourceNumericFilter.NumericFilterOptionEnum] = JsonEnumFormat[Schema.SourceNumericFilter.NumericFilterOptionEnum]
	given fmtValueTransformation: Format[Schema.ValueTransformation] = Json.format[Schema.ValueTransformation]
	given fmtValueListFilter: Format[Schema.ValueListFilter] = Json.format[Schema.ValueListFilter]
	given fmtIntComparisonFilter: Format[Schema.IntComparisonFilter] = Json.format[Schema.IntComparisonFilter]
	given fmtDoubleComparisonFilter: Format[Schema.DoubleComparisonFilter] = Json.format[Schema.DoubleComparisonFilter]
	given fmtAssignSpecificValue: Format[Schema.AssignSpecificValue] = Json.format[Schema.AssignSpecificValue]
	given fmtRoundToScale: Format[Schema.RoundToScale] = Json.format[Schema.RoundToScale]
	given fmtApplyHash: Format[Schema.ApplyHash] = Json.format[Schema.ApplyHash]
	given fmtValueListFilterValuePresentListEnum: Format[Schema.ValueListFilter.ValuePresentListEnum] = JsonEnumFormat[Schema.ValueListFilter.ValuePresentListEnum]
	given fmtIntComparisonFilterValueComparisonEnum: Format[Schema.IntComparisonFilter.ValueComparisonEnum] = JsonEnumFormat[Schema.IntComparisonFilter.ValueComparisonEnum]
	given fmtDoubleComparisonFilterValueComparisonEnum: Format[Schema.DoubleComparisonFilter.ValueComparisonEnum] = JsonEnumFormat[Schema.DoubleComparisonFilter.ValueComparisonEnum]
	given fmtListMappingRulesResponse: Format[Schema.ListMappingRulesResponse] = Json.format[Schema.ListMappingRulesResponse]
	given fmtSeedConversionWorkspaceRequest: Format[Schema.SeedConversionWorkspaceRequest] = Json.format[Schema.SeedConversionWorkspaceRequest]
	given fmtImportMappingRulesRequest: Format[Schema.ImportMappingRulesRequest] = Json.format[Schema.ImportMappingRulesRequest]
	given fmtImportMappingRulesRequestRulesFormatEnum: Format[Schema.ImportMappingRulesRequest.RulesFormatEnum] = JsonEnumFormat[Schema.ImportMappingRulesRequest.RulesFormatEnum]
	given fmtRulesFile: Format[Schema.RulesFile] = Json.format[Schema.RulesFile]
	given fmtConvertConversionWorkspaceRequest: Format[Schema.ConvertConversionWorkspaceRequest] = Json.format[Schema.ConvertConversionWorkspaceRequest]
	given fmtCommitConversionWorkspaceRequest: Format[Schema.CommitConversionWorkspaceRequest] = Json.format[Schema.CommitConversionWorkspaceRequest]
	given fmtRollbackConversionWorkspaceRequest: Format[Schema.RollbackConversionWorkspaceRequest] = Json.format[Schema.RollbackConversionWorkspaceRequest]
	given fmtApplyConversionWorkspaceRequest: Format[Schema.ApplyConversionWorkspaceRequest] = Json.format[Schema.ApplyConversionWorkspaceRequest]
	given fmtDescribeDatabaseEntitiesResponse: Format[Schema.DescribeDatabaseEntitiesResponse] = Json.format[Schema.DescribeDatabaseEntitiesResponse]
	given fmtDatabaseEntity: Format[Schema.DatabaseEntity] = Json.format[Schema.DatabaseEntity]
	given fmtDatabaseEntityTreeEnum: Format[Schema.DatabaseEntity.TreeEnum] = JsonEnumFormat[Schema.DatabaseEntity.TreeEnum]
	given fmtDatabaseEntityEntityTypeEnum: Format[Schema.DatabaseEntity.EntityTypeEnum] = JsonEnumFormat[Schema.DatabaseEntity.EntityTypeEnum]
	given fmtEntityMapping: Format[Schema.EntityMapping] = Json.format[Schema.EntityMapping]
	given fmtEntityDdl: Format[Schema.EntityDdl] = Json.format[Schema.EntityDdl]
	given fmtEntityIssue: Format[Schema.EntityIssue] = Json.format[Schema.EntityIssue]
	given fmtDatabaseInstanceEntity: Format[Schema.DatabaseInstanceEntity] = Json.format[Schema.DatabaseInstanceEntity]
	given fmtSchemaEntity: Format[Schema.SchemaEntity] = Json.format[Schema.SchemaEntity]
	given fmtTableEntity: Format[Schema.TableEntity] = Json.format[Schema.TableEntity]
	given fmtViewEntity: Format[Schema.ViewEntity] = Json.format[Schema.ViewEntity]
	given fmtSequenceEntity: Format[Schema.SequenceEntity] = Json.format[Schema.SequenceEntity]
	given fmtStoredProcedureEntity: Format[Schema.StoredProcedureEntity] = Json.format[Schema.StoredProcedureEntity]
	given fmtFunctionEntity: Format[Schema.FunctionEntity] = Json.format[Schema.FunctionEntity]
	given fmtSynonymEntity: Format[Schema.SynonymEntity] = Json.format[Schema.SynonymEntity]
	given fmtPackageEntity: Format[Schema.PackageEntity] = Json.format[Schema.PackageEntity]
	given fmtUDTEntity: Format[Schema.UDTEntity] = Json.format[Schema.UDTEntity]
	given fmtMaterializedViewEntity: Format[Schema.MaterializedViewEntity] = Json.format[Schema.MaterializedViewEntity]
	given fmtEntityMappingSourceTypeEnum: Format[Schema.EntityMapping.SourceTypeEnum] = JsonEnumFormat[Schema.EntityMapping.SourceTypeEnum]
	given fmtEntityMappingDraftTypeEnum: Format[Schema.EntityMapping.DraftTypeEnum] = JsonEnumFormat[Schema.EntityMapping.DraftTypeEnum]
	given fmtEntityMappingLogEntry: Format[Schema.EntityMappingLogEntry] = Json.format[Schema.EntityMappingLogEntry]
	given fmtEntityDdlEntityTypeEnum: Format[Schema.EntityDdl.EntityTypeEnum] = JsonEnumFormat[Schema.EntityDdl.EntityTypeEnum]
	given fmtEntityIssueTypeEnum: Format[Schema.EntityIssue.TypeEnum] = JsonEnumFormat[Schema.EntityIssue.TypeEnum]
	given fmtEntityIssueSeverityEnum: Format[Schema.EntityIssue.SeverityEnum] = JsonEnumFormat[Schema.EntityIssue.SeverityEnum]
	given fmtPosition: Format[Schema.Position] = Json.format[Schema.Position]
	given fmtEntityIssueEntityTypeEnum: Format[Schema.EntityIssue.EntityTypeEnum] = JsonEnumFormat[Schema.EntityIssue.EntityTypeEnum]
	given fmtColumnEntity: Format[Schema.ColumnEntity] = Json.format[Schema.ColumnEntity]
	given fmtConstraintEntity: Format[Schema.ConstraintEntity] = Json.format[Schema.ConstraintEntity]
	given fmtIndexEntity: Format[Schema.IndexEntity] = Json.format[Schema.IndexEntity]
	given fmtTriggerEntity: Format[Schema.TriggerEntity] = Json.format[Schema.TriggerEntity]
	given fmtSynonymEntitySourceTypeEnum: Format[Schema.SynonymEntity.SourceTypeEnum] = JsonEnumFormat[Schema.SynonymEntity.SourceTypeEnum]
	given fmtSearchBackgroundJobsResponse: Format[Schema.SearchBackgroundJobsResponse] = Json.format[Schema.SearchBackgroundJobsResponse]
	given fmtBackgroundJobLogEntry: Format[Schema.BackgroundJobLogEntry] = Json.format[Schema.BackgroundJobLogEntry]
	given fmtBackgroundJobLogEntryJobTypeEnum: Format[Schema.BackgroundJobLogEntry.JobTypeEnum] = JsonEnumFormat[Schema.BackgroundJobLogEntry.JobTypeEnum]
	given fmtBackgroundJobLogEntryCompletionStateEnum: Format[Schema.BackgroundJobLogEntry.CompletionStateEnum] = JsonEnumFormat[Schema.BackgroundJobLogEntry.CompletionStateEnum]
	given fmtSeedJobDetails: Format[Schema.SeedJobDetails] = Json.format[Schema.SeedJobDetails]
	given fmtImportRulesJobDetails: Format[Schema.ImportRulesJobDetails] = Json.format[Schema.ImportRulesJobDetails]
	given fmtConvertJobDetails: Format[Schema.ConvertJobDetails] = Json.format[Schema.ConvertJobDetails]
	given fmtApplyJobDetails: Format[Schema.ApplyJobDetails] = Json.format[Schema.ApplyJobDetails]
	given fmtImportRulesJobDetailsFileFormatEnum: Format[Schema.ImportRulesJobDetails.FileFormatEnum] = JsonEnumFormat[Schema.ImportRulesJobDetails.FileFormatEnum]
	given fmtDescribeConversionWorkspaceRevisionsResponse: Format[Schema.DescribeConversionWorkspaceRevisionsResponse] = Json.format[Schema.DescribeConversionWorkspaceRevisionsResponse]
	given fmtFetchStaticIpsResponse: Format[Schema.FetchStaticIpsResponse] = Json.format[Schema.FetchStaticIpsResponse]
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
	given fmtGoogleCloudClouddmsV1OperationMetadata: Format[Schema.GoogleCloudClouddmsV1OperationMetadata] = Json.format[Schema.GoogleCloudClouddmsV1OperationMetadata]
	given fmtMigrationJobVerificationError: Format[Schema.MigrationJobVerificationError] = Json.format[Schema.MigrationJobVerificationError]
	given fmtMigrationJobVerificationErrorErrorCodeEnum: Format[Schema.MigrationJobVerificationError.ErrorCodeEnum] = JsonEnumFormat[Schema.MigrationJobVerificationError.ErrorCodeEnum]
}
