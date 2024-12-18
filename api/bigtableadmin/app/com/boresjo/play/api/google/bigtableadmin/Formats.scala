package com.boresjo.play.api.google.bigtableadmin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtCreateInstanceRequest: Format[Schema.CreateInstanceRequest] = Json.format[Schema.CreateInstanceRequest]
	given fmtInstance: Format[Schema.Instance] = Json.format[Schema.Instance]
	given fmtCluster: Format[Schema.Cluster] = Json.format[Schema.Cluster]
	given fmtInstanceStateEnum: Format[Schema.Instance.StateEnum] = JsonEnumFormat[Schema.Instance.StateEnum]
	given fmtInstanceTypeEnum: Format[Schema.Instance.TypeEnum] = JsonEnumFormat[Schema.Instance.TypeEnum]
	given fmtClusterStateEnum: Format[Schema.Cluster.StateEnum] = JsonEnumFormat[Schema.Cluster.StateEnum]
	given fmtClusterNodeScalingFactorEnum: Format[Schema.Cluster.NodeScalingFactorEnum] = JsonEnumFormat[Schema.Cluster.NodeScalingFactorEnum]
	given fmtClusterConfig: Format[Schema.ClusterConfig] = Json.format[Schema.ClusterConfig]
	given fmtClusterDefaultStorageTypeEnum: Format[Schema.Cluster.DefaultStorageTypeEnum] = JsonEnumFormat[Schema.Cluster.DefaultStorageTypeEnum]
	given fmtEncryptionConfig: Format[Schema.EncryptionConfig] = Json.format[Schema.EncryptionConfig]
	given fmtClusterAutoscalingConfig: Format[Schema.ClusterAutoscalingConfig] = Json.format[Schema.ClusterAutoscalingConfig]
	given fmtAutoscalingLimits: Format[Schema.AutoscalingLimits] = Json.format[Schema.AutoscalingLimits]
	given fmtAutoscalingTargets: Format[Schema.AutoscalingTargets] = Json.format[Schema.AutoscalingTargets]
	given fmtListInstancesResponse: Format[Schema.ListInstancesResponse] = Json.format[Schema.ListInstancesResponse]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtListClustersResponse: Format[Schema.ListClustersResponse] = Json.format[Schema.ListClustersResponse]
	given fmtAppProfile: Format[Schema.AppProfile] = Json.format[Schema.AppProfile]
	given fmtMultiClusterRoutingUseAny: Format[Schema.MultiClusterRoutingUseAny] = Json.format[Schema.MultiClusterRoutingUseAny]
	given fmtSingleClusterRouting: Format[Schema.SingleClusterRouting] = Json.format[Schema.SingleClusterRouting]
	given fmtAppProfilePriorityEnum: Format[Schema.AppProfile.PriorityEnum] = JsonEnumFormat[Schema.AppProfile.PriorityEnum]
	given fmtStandardIsolation: Format[Schema.StandardIsolation] = Json.format[Schema.StandardIsolation]
	given fmtDataBoostIsolationReadOnly: Format[Schema.DataBoostIsolationReadOnly] = Json.format[Schema.DataBoostIsolationReadOnly]
	given fmtRowAffinity: Format[Schema.RowAffinity] = Json.format[Schema.RowAffinity]
	given fmtStandardIsolationPriorityEnum: Format[Schema.StandardIsolation.PriorityEnum] = JsonEnumFormat[Schema.StandardIsolation.PriorityEnum]
	given fmtDataBoostIsolationReadOnlyComputeBillingOwnerEnum: Format[Schema.DataBoostIsolationReadOnly.ComputeBillingOwnerEnum] = JsonEnumFormat[Schema.DataBoostIsolationReadOnly.ComputeBillingOwnerEnum]
	given fmtListAppProfilesResponse: Format[Schema.ListAppProfilesResponse] = Json.format[Schema.ListAppProfilesResponse]
	given fmtGetIamPolicyRequest: Format[Schema.GetIamPolicyRequest] = Json.format[Schema.GetIamPolicyRequest]
	given fmtGetPolicyOptions: Format[Schema.GetPolicyOptions] = Json.format[Schema.GetPolicyOptions]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtBinding: Format[Schema.Binding] = Json.format[Schema.Binding]
	given fmtAuditConfig: Format[Schema.AuditConfig] = Json.format[Schema.AuditConfig]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtAuditLogConfig: Format[Schema.AuditLogConfig] = Json.format[Schema.AuditLogConfig]
	given fmtAuditLogConfigLogTypeEnum: Format[Schema.AuditLogConfig.LogTypeEnum] = JsonEnumFormat[Schema.AuditLogConfig.LogTypeEnum]
	given fmtSetIamPolicyRequest: Format[Schema.SetIamPolicyRequest] = Json.format[Schema.SetIamPolicyRequest]
	given fmtTestIamPermissionsRequest: Format[Schema.TestIamPermissionsRequest] = Json.format[Schema.TestIamPermissionsRequest]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
	given fmtListHotTabletsResponse: Format[Schema.ListHotTabletsResponse] = Json.format[Schema.ListHotTabletsResponse]
	given fmtHotTablet: Format[Schema.HotTablet] = Json.format[Schema.HotTablet]
	given fmtCreateTableRequest: Format[Schema.CreateTableRequest] = Json.format[Schema.CreateTableRequest]
	given fmtTable: Format[Schema.Table] = Json.format[Schema.Table]
	given fmtSplit: Format[Schema.Split] = Json.format[Schema.Split]
	given fmtClusterState: Format[Schema.ClusterState] = Json.format[Schema.ClusterState]
	given fmtColumnFamily: Format[Schema.ColumnFamily] = Json.format[Schema.ColumnFamily]
	given fmtTableGranularityEnum: Format[Schema.Table.GranularityEnum] = JsonEnumFormat[Schema.Table.GranularityEnum]
	given fmtRestoreInfo: Format[Schema.RestoreInfo] = Json.format[Schema.RestoreInfo]
	given fmtChangeStreamConfig: Format[Schema.ChangeStreamConfig] = Json.format[Schema.ChangeStreamConfig]
	given fmtTableStats: Format[Schema.TableStats] = Json.format[Schema.TableStats]
	given fmtAutomatedBackupPolicy: Format[Schema.AutomatedBackupPolicy] = Json.format[Schema.AutomatedBackupPolicy]
	given fmtClusterStateReplicationStateEnum: Format[Schema.ClusterState.ReplicationStateEnum] = JsonEnumFormat[Schema.ClusterState.ReplicationStateEnum]
	given fmtEncryptionInfo: Format[Schema.EncryptionInfo] = Json.format[Schema.EncryptionInfo]
	given fmtEncryptionInfoEncryptionTypeEnum: Format[Schema.EncryptionInfo.EncryptionTypeEnum] = JsonEnumFormat[Schema.EncryptionInfo.EncryptionTypeEnum]
	given fmtGcRule: Format[Schema.GcRule] = Json.format[Schema.GcRule]
	given fmtColumnFamilyStats: Format[Schema.ColumnFamilyStats] = Json.format[Schema.ColumnFamilyStats]
	given fmtType: Format[Schema.Type] = Json.format[Schema.Type]
	given fmtIntersection: Format[Schema.Intersection] = Json.format[Schema.Intersection]
	given fmtUnion: Format[Schema.Union] = Json.format[Schema.Union]
	given fmtGoogleBigtableAdminV2TypeBytes: Format[Schema.GoogleBigtableAdminV2TypeBytes] = Json.format[Schema.GoogleBigtableAdminV2TypeBytes]
	given fmtGoogleBigtableAdminV2TypeString: Format[Schema.GoogleBigtableAdminV2TypeString] = Json.format[Schema.GoogleBigtableAdminV2TypeString]
	given fmtGoogleBigtableAdminV2TypeInt64: Format[Schema.GoogleBigtableAdminV2TypeInt64] = Json.format[Schema.GoogleBigtableAdminV2TypeInt64]
	given fmtGoogleBigtableAdminV2TypeFloat32: Format[Schema.GoogleBigtableAdminV2TypeFloat32] = Json.format[Schema.GoogleBigtableAdminV2TypeFloat32]
	given fmtGoogleBigtableAdminV2TypeFloat64: Format[Schema.GoogleBigtableAdminV2TypeFloat64] = Json.format[Schema.GoogleBigtableAdminV2TypeFloat64]
	given fmtGoogleBigtableAdminV2TypeBool: Format[Schema.GoogleBigtableAdminV2TypeBool] = Json.format[Schema.GoogleBigtableAdminV2TypeBool]
	given fmtGoogleBigtableAdminV2TypeTimestamp: Format[Schema.GoogleBigtableAdminV2TypeTimestamp] = Json.format[Schema.GoogleBigtableAdminV2TypeTimestamp]
	given fmtGoogleBigtableAdminV2TypeDate: Format[Schema.GoogleBigtableAdminV2TypeDate] = Json.format[Schema.GoogleBigtableAdminV2TypeDate]
	given fmtGoogleBigtableAdminV2TypeAggregate: Format[Schema.GoogleBigtableAdminV2TypeAggregate] = Json.format[Schema.GoogleBigtableAdminV2TypeAggregate]
	given fmtGoogleBigtableAdminV2TypeStruct: Format[Schema.GoogleBigtableAdminV2TypeStruct] = Json.format[Schema.GoogleBigtableAdminV2TypeStruct]
	given fmtGoogleBigtableAdminV2TypeArray: Format[Schema.GoogleBigtableAdminV2TypeArray] = Json.format[Schema.GoogleBigtableAdminV2TypeArray]
	given fmtGoogleBigtableAdminV2TypeMap: Format[Schema.GoogleBigtableAdminV2TypeMap] = Json.format[Schema.GoogleBigtableAdminV2TypeMap]
	given fmtGoogleBigtableAdminV2TypeBytesEncoding: Format[Schema.GoogleBigtableAdminV2TypeBytesEncoding] = Json.format[Schema.GoogleBigtableAdminV2TypeBytesEncoding]
	given fmtGoogleBigtableAdminV2TypeBytesEncodingRaw: Format[Schema.GoogleBigtableAdminV2TypeBytesEncodingRaw] = Json.format[Schema.GoogleBigtableAdminV2TypeBytesEncodingRaw]
	given fmtGoogleBigtableAdminV2TypeStringEncoding: Format[Schema.GoogleBigtableAdminV2TypeStringEncoding] = Json.format[Schema.GoogleBigtableAdminV2TypeStringEncoding]
	given fmtGoogleBigtableAdminV2TypeStringEncodingUtf8Raw: Format[Schema.GoogleBigtableAdminV2TypeStringEncodingUtf8Raw] = Json.format[Schema.GoogleBigtableAdminV2TypeStringEncodingUtf8Raw]
	given fmtGoogleBigtableAdminV2TypeStringEncodingUtf8Bytes: Format[Schema.GoogleBigtableAdminV2TypeStringEncodingUtf8Bytes] = Json.format[Schema.GoogleBigtableAdminV2TypeStringEncodingUtf8Bytes]
	given fmtGoogleBigtableAdminV2TypeInt64Encoding: Format[Schema.GoogleBigtableAdminV2TypeInt64Encoding] = Json.format[Schema.GoogleBigtableAdminV2TypeInt64Encoding]
	given fmtGoogleBigtableAdminV2TypeInt64EncodingBigEndianBytes: Format[Schema.GoogleBigtableAdminV2TypeInt64EncodingBigEndianBytes] = Json.format[Schema.GoogleBigtableAdminV2TypeInt64EncodingBigEndianBytes]
	given fmtGoogleBigtableAdminV2TypeAggregateSum: Format[Schema.GoogleBigtableAdminV2TypeAggregateSum] = Json.format[Schema.GoogleBigtableAdminV2TypeAggregateSum]
	given fmtGoogleBigtableAdminV2TypeAggregateHyperLogLogPlusPlusUniqueCount: Format[Schema.GoogleBigtableAdminV2TypeAggregateHyperLogLogPlusPlusUniqueCount] = Json.format[Schema.GoogleBigtableAdminV2TypeAggregateHyperLogLogPlusPlusUniqueCount]
	given fmtGoogleBigtableAdminV2TypeAggregateMax: Format[Schema.GoogleBigtableAdminV2TypeAggregateMax] = Json.format[Schema.GoogleBigtableAdminV2TypeAggregateMax]
	given fmtGoogleBigtableAdminV2TypeAggregateMin: Format[Schema.GoogleBigtableAdminV2TypeAggregateMin] = Json.format[Schema.GoogleBigtableAdminV2TypeAggregateMin]
	given fmtGoogleBigtableAdminV2TypeStructField: Format[Schema.GoogleBigtableAdminV2TypeStructField] = Json.format[Schema.GoogleBigtableAdminV2TypeStructField]
	given fmtRestoreInfoSourceTypeEnum: Format[Schema.RestoreInfo.SourceTypeEnum] = JsonEnumFormat[Schema.RestoreInfo.SourceTypeEnum]
	given fmtBackupInfo: Format[Schema.BackupInfo] = Json.format[Schema.BackupInfo]
	given fmtListTablesResponse: Format[Schema.ListTablesResponse] = Json.format[Schema.ListTablesResponse]
	given fmtUndeleteTableRequest: Format[Schema.UndeleteTableRequest] = Json.format[Schema.UndeleteTableRequest]
	given fmtAuthorizedView: Format[Schema.AuthorizedView] = Json.format[Schema.AuthorizedView]
	given fmtGoogleBigtableAdminV2AuthorizedViewSubsetView: Format[Schema.GoogleBigtableAdminV2AuthorizedViewSubsetView] = Json.format[Schema.GoogleBigtableAdminV2AuthorizedViewSubsetView]
	given fmtGoogleBigtableAdminV2AuthorizedViewFamilySubsets: Format[Schema.GoogleBigtableAdminV2AuthorizedViewFamilySubsets] = Json.format[Schema.GoogleBigtableAdminV2AuthorizedViewFamilySubsets]
	given fmtListAuthorizedViewsResponse: Format[Schema.ListAuthorizedViewsResponse] = Json.format[Schema.ListAuthorizedViewsResponse]
	given fmtModifyColumnFamiliesRequest: Format[Schema.ModifyColumnFamiliesRequest] = Json.format[Schema.ModifyColumnFamiliesRequest]
	given fmtModification: Format[Schema.Modification] = Json.format[Schema.Modification]
	given fmtDropRowRangeRequest: Format[Schema.DropRowRangeRequest] = Json.format[Schema.DropRowRangeRequest]
	given fmtGenerateConsistencyTokenRequest: Format[Schema.GenerateConsistencyTokenRequest] = Json.format[Schema.GenerateConsistencyTokenRequest]
	given fmtGenerateConsistencyTokenResponse: Format[Schema.GenerateConsistencyTokenResponse] = Json.format[Schema.GenerateConsistencyTokenResponse]
	given fmtCheckConsistencyRequest: Format[Schema.CheckConsistencyRequest] = Json.format[Schema.CheckConsistencyRequest]
	given fmtStandardReadRemoteWrites: Format[Schema.StandardReadRemoteWrites] = Json.format[Schema.StandardReadRemoteWrites]
	given fmtDataBoostReadLocalWrites: Format[Schema.DataBoostReadLocalWrites] = Json.format[Schema.DataBoostReadLocalWrites]
	given fmtCheckConsistencyResponse: Format[Schema.CheckConsistencyResponse] = Json.format[Schema.CheckConsistencyResponse]
	given fmtBackup: Format[Schema.Backup] = Json.format[Schema.Backup]
	given fmtBackupStateEnum: Format[Schema.Backup.StateEnum] = JsonEnumFormat[Schema.Backup.StateEnum]
	given fmtBackupBackupTypeEnum: Format[Schema.Backup.BackupTypeEnum] = JsonEnumFormat[Schema.Backup.BackupTypeEnum]
	given fmtListBackupsResponse: Format[Schema.ListBackupsResponse] = Json.format[Schema.ListBackupsResponse]
	given fmtRestoreTableRequest: Format[Schema.RestoreTableRequest] = Json.format[Schema.RestoreTableRequest]
	given fmtCopyBackupRequest: Format[Schema.CopyBackupRequest] = Json.format[Schema.CopyBackupRequest]
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtCreateInstanceMetadata: Format[Schema.CreateInstanceMetadata] = Json.format[Schema.CreateInstanceMetadata]
	given fmtUpdateInstanceMetadata: Format[Schema.UpdateInstanceMetadata] = Json.format[Schema.UpdateInstanceMetadata]
	given fmtPartialUpdateInstanceRequest: Format[Schema.PartialUpdateInstanceRequest] = Json.format[Schema.PartialUpdateInstanceRequest]
	given fmtCreateClusterMetadata: Format[Schema.CreateClusterMetadata] = Json.format[Schema.CreateClusterMetadata]
	given fmtCreateClusterRequest: Format[Schema.CreateClusterRequest] = Json.format[Schema.CreateClusterRequest]
	given fmtTableProgress: Format[Schema.TableProgress] = Json.format[Schema.TableProgress]
	given fmtTableProgressStateEnum: Format[Schema.TableProgress.StateEnum] = JsonEnumFormat[Schema.TableProgress.StateEnum]
	given fmtPartialUpdateClusterMetadata: Format[Schema.PartialUpdateClusterMetadata] = Json.format[Schema.PartialUpdateClusterMetadata]
	given fmtPartialUpdateClusterRequest: Format[Schema.PartialUpdateClusterRequest] = Json.format[Schema.PartialUpdateClusterRequest]
	given fmtUpdateClusterMetadata: Format[Schema.UpdateClusterMetadata] = Json.format[Schema.UpdateClusterMetadata]
	given fmtUpdateAppProfileMetadata: Format[Schema.UpdateAppProfileMetadata] = Json.format[Schema.UpdateAppProfileMetadata]
	given fmtCreateBackupMetadata: Format[Schema.CreateBackupMetadata] = Json.format[Schema.CreateBackupMetadata]
	given fmtCreateAuthorizedViewMetadata: Format[Schema.CreateAuthorizedViewMetadata] = Json.format[Schema.CreateAuthorizedViewMetadata]
	given fmtCreateAuthorizedViewRequest: Format[Schema.CreateAuthorizedViewRequest] = Json.format[Schema.CreateAuthorizedViewRequest]
	given fmtCopyBackupMetadata: Format[Schema.CopyBackupMetadata] = Json.format[Schema.CopyBackupMetadata]
	given fmtOperationProgress: Format[Schema.OperationProgress] = Json.format[Schema.OperationProgress]
	given fmtRestoreTableMetadata: Format[Schema.RestoreTableMetadata] = Json.format[Schema.RestoreTableMetadata]
	given fmtRestoreTableMetadataSourceTypeEnum: Format[Schema.RestoreTableMetadata.SourceTypeEnum] = JsonEnumFormat[Schema.RestoreTableMetadata.SourceTypeEnum]
	given fmtOptimizeRestoredTableMetadata: Format[Schema.OptimizeRestoredTableMetadata] = Json.format[Schema.OptimizeRestoredTableMetadata]
	given fmtUndeleteTableMetadata: Format[Schema.UndeleteTableMetadata] = Json.format[Schema.UndeleteTableMetadata]
	given fmtUpdateTableMetadata: Format[Schema.UpdateTableMetadata] = Json.format[Schema.UpdateTableMetadata]
	given fmtUpdateAuthorizedViewMetadata: Format[Schema.UpdateAuthorizedViewMetadata] = Json.format[Schema.UpdateAuthorizedViewMetadata]
	given fmtUpdateAuthorizedViewRequest: Format[Schema.UpdateAuthorizedViewRequest] = Json.format[Schema.UpdateAuthorizedViewRequest]
}
