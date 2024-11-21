package com.boresjo.play.api.google.netapp

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaStoragePool: Conversion[List[Schema.StoragePool], Option[List[Schema.StoragePool]]] = (fun: List[Schema.StoragePool]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaStoragePoolServiceLevelEnum: Conversion[Schema.StoragePool.ServiceLevelEnum, Option[Schema.StoragePool.ServiceLevelEnum]] = (fun: Schema.StoragePool.ServiceLevelEnum) => Option(fun)
		given putSchemaStoragePoolStateEnum: Conversion[Schema.StoragePool.StateEnum, Option[Schema.StoragePool.StateEnum]] = (fun: Schema.StoragePool.StateEnum) => Option(fun)
		given putSchemaStoragePoolEncryptionTypeEnum: Conversion[Schema.StoragePool.EncryptionTypeEnum, Option[Schema.StoragePool.EncryptionTypeEnum]] = (fun: Schema.StoragePool.EncryptionTypeEnum) => Option(fun)
		given putListSchemaVolume: Conversion[List[Schema.Volume], Option[List[Schema.Volume]]] = (fun: List[Schema.Volume]) => Option(fun)
		given putSchemaVolumeStateEnum: Conversion[Schema.Volume.StateEnum, Option[Schema.Volume.StateEnum]] = (fun: Schema.Volume.StateEnum) => Option(fun)
		given putSchemaVolumeServiceLevelEnum: Conversion[Schema.Volume.ServiceLevelEnum, Option[Schema.Volume.ServiceLevelEnum]] = (fun: Schema.Volume.ServiceLevelEnum) => Option(fun)
		given putSchemaExportPolicy: Conversion[Schema.ExportPolicy, Option[Schema.ExportPolicy]] = (fun: Schema.ExportPolicy) => Option(fun)
		given putListSchemaVolumeProtocolsEnum: Conversion[List[Schema.Volume.ProtocolsEnum], Option[List[Schema.Volume.ProtocolsEnum]]] = (fun: List[Schema.Volume.ProtocolsEnum]) => Option(fun)
		given putListSchemaVolumeSmbSettingsEnum: Conversion[List[Schema.Volume.SmbSettingsEnum], Option[List[Schema.Volume.SmbSettingsEnum]]] = (fun: List[Schema.Volume.SmbSettingsEnum]) => Option(fun)
		given putListSchemaMountOption: Conversion[List[Schema.MountOption], Option[List[Schema.MountOption]]] = (fun: List[Schema.MountOption]) => Option(fun)
		given putSchemaSnapshotPolicy: Conversion[Schema.SnapshotPolicy, Option[Schema.SnapshotPolicy]] = (fun: Schema.SnapshotPolicy) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putSchemaVolumeSecurityStyleEnum: Conversion[Schema.Volume.SecurityStyleEnum, Option[Schema.Volume.SecurityStyleEnum]] = (fun: Schema.Volume.SecurityStyleEnum) => Option(fun)
		given putSchemaRestoreParameters: Conversion[Schema.RestoreParameters, Option[Schema.RestoreParameters]] = (fun: Schema.RestoreParameters) => Option(fun)
		given putSchemaVolumeEncryptionTypeEnum: Conversion[Schema.Volume.EncryptionTypeEnum, Option[Schema.Volume.EncryptionTypeEnum]] = (fun: Schema.Volume.EncryptionTypeEnum) => Option(fun)
		given putSchemaBackupConfig: Conversion[Schema.BackupConfig, Option[Schema.BackupConfig]] = (fun: Schema.BackupConfig) => Option(fun)
		given putListSchemaVolumeRestrictedActionsEnum: Conversion[List[Schema.Volume.RestrictedActionsEnum], Option[List[Schema.Volume.RestrictedActionsEnum]]] = (fun: List[Schema.Volume.RestrictedActionsEnum]) => Option(fun)
		given putSchemaTieringPolicy: Conversion[Schema.TieringPolicy, Option[Schema.TieringPolicy]] = (fun: Schema.TieringPolicy) => Option(fun)
		given putSchemaHybridReplicationParameters: Conversion[Schema.HybridReplicationParameters, Option[Schema.HybridReplicationParameters]] = (fun: Schema.HybridReplicationParameters) => Option(fun)
		given putListSchemaSimpleExportPolicyRule: Conversion[List[Schema.SimpleExportPolicyRule], Option[List[Schema.SimpleExportPolicyRule]]] = (fun: List[Schema.SimpleExportPolicyRule]) => Option(fun)
		given putSchemaSimpleExportPolicyRuleAccessTypeEnum: Conversion[Schema.SimpleExportPolicyRule.AccessTypeEnum, Option[Schema.SimpleExportPolicyRule.AccessTypeEnum]] = (fun: Schema.SimpleExportPolicyRule.AccessTypeEnum) => Option(fun)
		given putSchemaMountOptionProtocolEnum: Conversion[Schema.MountOption.ProtocolEnum, Option[Schema.MountOption.ProtocolEnum]] = (fun: Schema.MountOption.ProtocolEnum) => Option(fun)
		given putSchemaHourlySchedule: Conversion[Schema.HourlySchedule, Option[Schema.HourlySchedule]] = (fun: Schema.HourlySchedule) => Option(fun)
		given putSchemaDailySchedule: Conversion[Schema.DailySchedule, Option[Schema.DailySchedule]] = (fun: Schema.DailySchedule) => Option(fun)
		given putSchemaWeeklySchedule: Conversion[Schema.WeeklySchedule, Option[Schema.WeeklySchedule]] = (fun: Schema.WeeklySchedule) => Option(fun)
		given putSchemaMonthlySchedule: Conversion[Schema.MonthlySchedule, Option[Schema.MonthlySchedule]] = (fun: Schema.MonthlySchedule) => Option(fun)
		given putSchemaTieringPolicyTierActionEnum: Conversion[Schema.TieringPolicy.TierActionEnum, Option[Schema.TieringPolicy.TierActionEnum]] = (fun: Schema.TieringPolicy.TierActionEnum) => Option(fun)
		given putListSchemaSnapshot: Conversion[List[Schema.Snapshot], Option[List[Schema.Snapshot]]] = (fun: List[Schema.Snapshot]) => Option(fun)
		given putSchemaSnapshotStateEnum: Conversion[Schema.Snapshot.StateEnum, Option[Schema.Snapshot.StateEnum]] = (fun: Schema.Snapshot.StateEnum) => Option(fun)
		given putListSchemaActiveDirectory: Conversion[List[Schema.ActiveDirectory], Option[List[Schema.ActiveDirectory]]] = (fun: List[Schema.ActiveDirectory]) => Option(fun)
		given putSchemaActiveDirectoryStateEnum: Conversion[Schema.ActiveDirectory.StateEnum, Option[Schema.ActiveDirectory.StateEnum]] = (fun: Schema.ActiveDirectory.StateEnum) => Option(fun)
		given putListSchemaKmsConfig: Conversion[List[Schema.KmsConfig], Option[List[Schema.KmsConfig]]] = (fun: List[Schema.KmsConfig]) => Option(fun)
		given putSchemaKmsConfigStateEnum: Conversion[Schema.KmsConfig.StateEnum, Option[Schema.KmsConfig.StateEnum]] = (fun: Schema.KmsConfig.StateEnum) => Option(fun)
		given putListSchemaReplication: Conversion[List[Schema.Replication], Option[List[Schema.Replication]]] = (fun: List[Schema.Replication]) => Option(fun)
		given putSchemaReplicationStateEnum: Conversion[Schema.Replication.StateEnum, Option[Schema.Replication.StateEnum]] = (fun: Schema.Replication.StateEnum) => Option(fun)
		given putSchemaReplicationRoleEnum: Conversion[Schema.Replication.RoleEnum, Option[Schema.Replication.RoleEnum]] = (fun: Schema.Replication.RoleEnum) => Option(fun)
		given putSchemaReplicationReplicationScheduleEnum: Conversion[Schema.Replication.ReplicationScheduleEnum, Option[Schema.Replication.ReplicationScheduleEnum]] = (fun: Schema.Replication.ReplicationScheduleEnum) => Option(fun)
		given putSchemaReplicationMirrorStateEnum: Conversion[Schema.Replication.MirrorStateEnum, Option[Schema.Replication.MirrorStateEnum]] = (fun: Schema.Replication.MirrorStateEnum) => Option(fun)
		given putSchemaTransferStats: Conversion[Schema.TransferStats, Option[Schema.TransferStats]] = (fun: Schema.TransferStats) => Option(fun)
		given putSchemaDestinationVolumeParameters: Conversion[Schema.DestinationVolumeParameters, Option[Schema.DestinationVolumeParameters]] = (fun: Schema.DestinationVolumeParameters) => Option(fun)
		given putSchemaHybridPeeringDetails: Conversion[Schema.HybridPeeringDetails, Option[Schema.HybridPeeringDetails]] = (fun: Schema.HybridPeeringDetails) => Option(fun)
		given putSchemaReplicationHybridReplicationTypeEnum: Conversion[Schema.Replication.HybridReplicationTypeEnum, Option[Schema.Replication.HybridReplicationTypeEnum]] = (fun: Schema.Replication.HybridReplicationTypeEnum) => Option(fun)
		given putSchemaBackupVaultStateEnum: Conversion[Schema.BackupVault.StateEnum, Option[Schema.BackupVault.StateEnum]] = (fun: Schema.BackupVault.StateEnum) => Option(fun)
		given putListSchemaBackupVault: Conversion[List[Schema.BackupVault], Option[List[Schema.BackupVault]]] = (fun: List[Schema.BackupVault]) => Option(fun)
		given putSchemaBackupStateEnum: Conversion[Schema.Backup.StateEnum, Option[Schema.Backup.StateEnum]] = (fun: Schema.Backup.StateEnum) => Option(fun)
		given putSchemaBackupBackupTypeEnum: Conversion[Schema.Backup.BackupTypeEnum, Option[Schema.Backup.BackupTypeEnum]] = (fun: Schema.Backup.BackupTypeEnum) => Option(fun)
		given putListSchemaBackup: Conversion[List[Schema.Backup], Option[List[Schema.Backup]]] = (fun: List[Schema.Backup]) => Option(fun)
		given putSchemaBackupPolicyStateEnum: Conversion[Schema.BackupPolicy.StateEnum, Option[Schema.BackupPolicy.StateEnum]] = (fun: Schema.BackupPolicy.StateEnum) => Option(fun)
		given putListSchemaBackupPolicy: Conversion[List[Schema.BackupPolicy], Option[List[Schema.BackupPolicy]]] = (fun: List[Schema.BackupPolicy]) => Option(fun)
		given putListSchemaQuotaRule: Conversion[List[Schema.QuotaRule], Option[List[Schema.QuotaRule]]] = (fun: List[Schema.QuotaRule]) => Option(fun)
		given putSchemaQuotaRuleTypeEnum: Conversion[Schema.QuotaRule.TypeEnum, Option[Schema.QuotaRule.TypeEnum]] = (fun: Schema.QuotaRule.TypeEnum) => Option(fun)
		given putSchemaQuotaRuleStateEnum: Conversion[Schema.QuotaRule.StateEnum, Option[Schema.QuotaRule.StateEnum]] = (fun: Schema.QuotaRule.StateEnum) => Option(fun)
		given putListSchemaLocationMetadataSupportedServiceLevelsEnum: Conversion[List[Schema.LocationMetadata.SupportedServiceLevelsEnum], Option[List[Schema.LocationMetadata.SupportedServiceLevelsEnum]]] = (fun: List[Schema.LocationMetadata.SupportedServiceLevelsEnum]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
