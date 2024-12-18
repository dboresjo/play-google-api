package com.boresjo.play.api.google.netapp

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtGoogleProtobufEmpty: Format[Schema.GoogleProtobufEmpty] = Json.format[Schema.GoogleProtobufEmpty]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtListStoragePoolsResponse: Format[Schema.ListStoragePoolsResponse] = Json.format[Schema.ListStoragePoolsResponse]
	given fmtStoragePool: Format[Schema.StoragePool] = Json.format[Schema.StoragePool]
	given fmtStoragePoolServiceLevelEnum: Format[Schema.StoragePool.ServiceLevelEnum] = JsonEnumFormat[Schema.StoragePool.ServiceLevelEnum]
	given fmtStoragePoolStateEnum: Format[Schema.StoragePool.StateEnum] = JsonEnumFormat[Schema.StoragePool.StateEnum]
	given fmtStoragePoolEncryptionTypeEnum: Format[Schema.StoragePool.EncryptionTypeEnum] = JsonEnumFormat[Schema.StoragePool.EncryptionTypeEnum]
	given fmtSwitchActiveReplicaZoneRequest: Format[Schema.SwitchActiveReplicaZoneRequest] = Json.format[Schema.SwitchActiveReplicaZoneRequest]
	given fmtListVolumesResponse: Format[Schema.ListVolumesResponse] = Json.format[Schema.ListVolumesResponse]
	given fmtVolume: Format[Schema.Volume] = Json.format[Schema.Volume]
	given fmtVolumeStateEnum: Format[Schema.Volume.StateEnum] = JsonEnumFormat[Schema.Volume.StateEnum]
	given fmtVolumeServiceLevelEnum: Format[Schema.Volume.ServiceLevelEnum] = JsonEnumFormat[Schema.Volume.ServiceLevelEnum]
	given fmtExportPolicy: Format[Schema.ExportPolicy] = Json.format[Schema.ExportPolicy]
	given fmtVolumeProtocolsEnum: Format[Schema.Volume.ProtocolsEnum] = JsonEnumFormat[Schema.Volume.ProtocolsEnum]
	given fmtVolumeSmbSettingsEnum: Format[Schema.Volume.SmbSettingsEnum] = JsonEnumFormat[Schema.Volume.SmbSettingsEnum]
	given fmtMountOption: Format[Schema.MountOption] = Json.format[Schema.MountOption]
	given fmtSnapshotPolicy: Format[Schema.SnapshotPolicy] = Json.format[Schema.SnapshotPolicy]
	given fmtVolumeSecurityStyleEnum: Format[Schema.Volume.SecurityStyleEnum] = JsonEnumFormat[Schema.Volume.SecurityStyleEnum]
	given fmtRestoreParameters: Format[Schema.RestoreParameters] = Json.format[Schema.RestoreParameters]
	given fmtVolumeEncryptionTypeEnum: Format[Schema.Volume.EncryptionTypeEnum] = JsonEnumFormat[Schema.Volume.EncryptionTypeEnum]
	given fmtBackupConfig: Format[Schema.BackupConfig] = Json.format[Schema.BackupConfig]
	given fmtVolumeRestrictedActionsEnum: Format[Schema.Volume.RestrictedActionsEnum] = JsonEnumFormat[Schema.Volume.RestrictedActionsEnum]
	given fmtTieringPolicy: Format[Schema.TieringPolicy] = Json.format[Schema.TieringPolicy]
	given fmtHybridReplicationParameters: Format[Schema.HybridReplicationParameters] = Json.format[Schema.HybridReplicationParameters]
	given fmtSimpleExportPolicyRule: Format[Schema.SimpleExportPolicyRule] = Json.format[Schema.SimpleExportPolicyRule]
	given fmtSimpleExportPolicyRuleAccessTypeEnum: Format[Schema.SimpleExportPolicyRule.AccessTypeEnum] = JsonEnumFormat[Schema.SimpleExportPolicyRule.AccessTypeEnum]
	given fmtMountOptionProtocolEnum: Format[Schema.MountOption.ProtocolEnum] = JsonEnumFormat[Schema.MountOption.ProtocolEnum]
	given fmtHourlySchedule: Format[Schema.HourlySchedule] = Json.format[Schema.HourlySchedule]
	given fmtDailySchedule: Format[Schema.DailySchedule] = Json.format[Schema.DailySchedule]
	given fmtWeeklySchedule: Format[Schema.WeeklySchedule] = Json.format[Schema.WeeklySchedule]
	given fmtMonthlySchedule: Format[Schema.MonthlySchedule] = Json.format[Schema.MonthlySchedule]
	given fmtTieringPolicyTierActionEnum: Format[Schema.TieringPolicy.TierActionEnum] = JsonEnumFormat[Schema.TieringPolicy.TierActionEnum]
	given fmtRevertVolumeRequest: Format[Schema.RevertVolumeRequest] = Json.format[Schema.RevertVolumeRequest]
	given fmtListSnapshotsResponse: Format[Schema.ListSnapshotsResponse] = Json.format[Schema.ListSnapshotsResponse]
	given fmtSnapshot: Format[Schema.Snapshot] = Json.format[Schema.Snapshot]
	given fmtSnapshotStateEnum: Format[Schema.Snapshot.StateEnum] = JsonEnumFormat[Schema.Snapshot.StateEnum]
	given fmtListActiveDirectoriesResponse: Format[Schema.ListActiveDirectoriesResponse] = Json.format[Schema.ListActiveDirectoriesResponse]
	given fmtActiveDirectory: Format[Schema.ActiveDirectory] = Json.format[Schema.ActiveDirectory]
	given fmtActiveDirectoryStateEnum: Format[Schema.ActiveDirectory.StateEnum] = JsonEnumFormat[Schema.ActiveDirectory.StateEnum]
	given fmtListKmsConfigsResponse: Format[Schema.ListKmsConfigsResponse] = Json.format[Schema.ListKmsConfigsResponse]
	given fmtKmsConfig: Format[Schema.KmsConfig] = Json.format[Schema.KmsConfig]
	given fmtKmsConfigStateEnum: Format[Schema.KmsConfig.StateEnum] = JsonEnumFormat[Schema.KmsConfig.StateEnum]
	given fmtEncryptVolumesRequest: Format[Schema.EncryptVolumesRequest] = Json.format[Schema.EncryptVolumesRequest]
	given fmtVerifyKmsConfigRequest: Format[Schema.VerifyKmsConfigRequest] = Json.format[Schema.VerifyKmsConfigRequest]
	given fmtVerifyKmsConfigResponse: Format[Schema.VerifyKmsConfigResponse] = Json.format[Schema.VerifyKmsConfigResponse]
	given fmtListReplicationsResponse: Format[Schema.ListReplicationsResponse] = Json.format[Schema.ListReplicationsResponse]
	given fmtReplication: Format[Schema.Replication] = Json.format[Schema.Replication]
	given fmtReplicationStateEnum: Format[Schema.Replication.StateEnum] = JsonEnumFormat[Schema.Replication.StateEnum]
	given fmtReplicationRoleEnum: Format[Schema.Replication.RoleEnum] = JsonEnumFormat[Schema.Replication.RoleEnum]
	given fmtReplicationReplicationScheduleEnum: Format[Schema.Replication.ReplicationScheduleEnum] = JsonEnumFormat[Schema.Replication.ReplicationScheduleEnum]
	given fmtReplicationMirrorStateEnum: Format[Schema.Replication.MirrorStateEnum] = JsonEnumFormat[Schema.Replication.MirrorStateEnum]
	given fmtTransferStats: Format[Schema.TransferStats] = Json.format[Schema.TransferStats]
	given fmtDestinationVolumeParameters: Format[Schema.DestinationVolumeParameters] = Json.format[Schema.DestinationVolumeParameters]
	given fmtHybridPeeringDetails: Format[Schema.HybridPeeringDetails] = Json.format[Schema.HybridPeeringDetails]
	given fmtReplicationHybridReplicationTypeEnum: Format[Schema.Replication.HybridReplicationTypeEnum] = JsonEnumFormat[Schema.Replication.HybridReplicationTypeEnum]
	given fmtStopReplicationRequest: Format[Schema.StopReplicationRequest] = Json.format[Schema.StopReplicationRequest]
	given fmtResumeReplicationRequest: Format[Schema.ResumeReplicationRequest] = Json.format[Schema.ResumeReplicationRequest]
	given fmtReverseReplicationDirectionRequest: Format[Schema.ReverseReplicationDirectionRequest] = Json.format[Schema.ReverseReplicationDirectionRequest]
	given fmtEstablishPeeringRequest: Format[Schema.EstablishPeeringRequest] = Json.format[Schema.EstablishPeeringRequest]
	given fmtSyncReplicationRequest: Format[Schema.SyncReplicationRequest] = Json.format[Schema.SyncReplicationRequest]
	given fmtBackupVault: Format[Schema.BackupVault] = Json.format[Schema.BackupVault]
	given fmtBackupVaultStateEnum: Format[Schema.BackupVault.StateEnum] = JsonEnumFormat[Schema.BackupVault.StateEnum]
	given fmtListBackupVaultsResponse: Format[Schema.ListBackupVaultsResponse] = Json.format[Schema.ListBackupVaultsResponse]
	given fmtBackup: Format[Schema.Backup] = Json.format[Schema.Backup]
	given fmtBackupStateEnum: Format[Schema.Backup.StateEnum] = JsonEnumFormat[Schema.Backup.StateEnum]
	given fmtBackupBackupTypeEnum: Format[Schema.Backup.BackupTypeEnum] = JsonEnumFormat[Schema.Backup.BackupTypeEnum]
	given fmtListBackupsResponse: Format[Schema.ListBackupsResponse] = Json.format[Schema.ListBackupsResponse]
	given fmtBackupPolicy: Format[Schema.BackupPolicy] = Json.format[Schema.BackupPolicy]
	given fmtBackupPolicyStateEnum: Format[Schema.BackupPolicy.StateEnum] = JsonEnumFormat[Schema.BackupPolicy.StateEnum]
	given fmtListBackupPoliciesResponse: Format[Schema.ListBackupPoliciesResponse] = Json.format[Schema.ListBackupPoliciesResponse]
	given fmtListQuotaRulesResponse: Format[Schema.ListQuotaRulesResponse] = Json.format[Schema.ListQuotaRulesResponse]
	given fmtQuotaRule: Format[Schema.QuotaRule] = Json.format[Schema.QuotaRule]
	given fmtQuotaRuleTypeEnum: Format[Schema.QuotaRule.TypeEnum] = JsonEnumFormat[Schema.QuotaRule.TypeEnum]
	given fmtQuotaRuleStateEnum: Format[Schema.QuotaRule.StateEnum] = JsonEnumFormat[Schema.QuotaRule.StateEnum]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
	given fmtLocationMetadata: Format[Schema.LocationMetadata] = Json.format[Schema.LocationMetadata]
	given fmtLocationMetadataSupportedServiceLevelsEnum: Format[Schema.LocationMetadata.SupportedServiceLevelsEnum] = JsonEnumFormat[Schema.LocationMetadata.SupportedServiceLevelsEnum]
}
