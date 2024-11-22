package com.boresjo.play.api.google.metastore

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
		given putListSchemaFederation: Conversion[List[Schema.Federation], Option[List[Schema.Federation]]] = (fun: List[Schema.Federation]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putMapStringSchemaBackendMetastore: Conversion[Map[String, Schema.BackendMetastore], Option[Map[String, Schema.BackendMetastore]]] = (fun: Map[String, Schema.BackendMetastore]) => Option(fun)
		given putSchemaFederationStateEnum: Conversion[Schema.Federation.StateEnum, Option[Schema.Federation.StateEnum]] = (fun: Schema.Federation.StateEnum) => Option(fun)
		given putSchemaBackendMetastoreMetastoreTypeEnum: Conversion[Schema.BackendMetastore.MetastoreTypeEnum, Option[Schema.BackendMetastore.MetastoreTypeEnum]] = (fun: Schema.BackendMetastore.MetastoreTypeEnum) => Option(fun)
		given putListSchemaService: Conversion[List[Schema.Service], Option[List[Schema.Service]]] = (fun: List[Schema.Service]) => Option(fun)
		given putSchemaHiveMetastoreConfig: Conversion[Schema.HiveMetastoreConfig, Option[Schema.HiveMetastoreConfig]] = (fun: Schema.HiveMetastoreConfig) => Option(fun)
		given putSchemaServiceStateEnum: Conversion[Schema.Service.StateEnum, Option[Schema.Service.StateEnum]] = (fun: Schema.Service.StateEnum) => Option(fun)
		given putSchemaServiceTierEnum: Conversion[Schema.Service.TierEnum, Option[Schema.Service.TierEnum]] = (fun: Schema.Service.TierEnum) => Option(fun)
		given putSchemaMetadataIntegration: Conversion[Schema.MetadataIntegration, Option[Schema.MetadataIntegration]] = (fun: Schema.MetadataIntegration) => Option(fun)
		given putSchemaMaintenanceWindow: Conversion[Schema.MaintenanceWindow, Option[Schema.MaintenanceWindow]] = (fun: Schema.MaintenanceWindow) => Option(fun)
		given putSchemaMetadataManagementActivity: Conversion[Schema.MetadataManagementActivity, Option[Schema.MetadataManagementActivity]] = (fun: Schema.MetadataManagementActivity) => Option(fun)
		given putSchemaServiceReleaseChannelEnum: Conversion[Schema.Service.ReleaseChannelEnum, Option[Schema.Service.ReleaseChannelEnum]] = (fun: Schema.Service.ReleaseChannelEnum) => Option(fun)
		given putSchemaEncryptionConfig: Conversion[Schema.EncryptionConfig, Option[Schema.EncryptionConfig]] = (fun: Schema.EncryptionConfig) => Option(fun)
		given putSchemaNetworkConfig: Conversion[Schema.NetworkConfig, Option[Schema.NetworkConfig]] = (fun: Schema.NetworkConfig) => Option(fun)
		given putSchemaServiceDatabaseTypeEnum: Conversion[Schema.Service.DatabaseTypeEnum, Option[Schema.Service.DatabaseTypeEnum]] = (fun: Schema.Service.DatabaseTypeEnum) => Option(fun)
		given putSchemaTelemetryConfig: Conversion[Schema.TelemetryConfig, Option[Schema.TelemetryConfig]] = (fun: Schema.TelemetryConfig) => Option(fun)
		given putSchemaScalingConfig: Conversion[Schema.ScalingConfig, Option[Schema.ScalingConfig]] = (fun: Schema.ScalingConfig) => Option(fun)
		given putSchemaScheduledBackup: Conversion[Schema.ScheduledBackup, Option[Schema.ScheduledBackup]] = (fun: Schema.ScheduledBackup) => Option(fun)
		given putSchemaKerberosConfig: Conversion[Schema.KerberosConfig, Option[Schema.KerberosConfig]] = (fun: Schema.KerberosConfig) => Option(fun)
		given putSchemaHiveMetastoreConfigEndpointProtocolEnum: Conversion[Schema.HiveMetastoreConfig.EndpointProtocolEnum, Option[Schema.HiveMetastoreConfig.EndpointProtocolEnum]] = (fun: Schema.HiveMetastoreConfig.EndpointProtocolEnum) => Option(fun)
		given putMapStringSchemaAuxiliaryVersionConfig: Conversion[Map[String, Schema.AuxiliaryVersionConfig], Option[Map[String, Schema.AuxiliaryVersionConfig]]] = (fun: Map[String, Schema.AuxiliaryVersionConfig]) => Option(fun)
		given putSchemaSecret: Conversion[Schema.Secret, Option[Schema.Secret]] = (fun: Schema.Secret) => Option(fun)
		given putListSchemaConsumer: Conversion[List[Schema.Consumer], Option[List[Schema.Consumer]]] = (fun: List[Schema.Consumer]) => Option(fun)
		given putSchemaDataCatalogConfig: Conversion[Schema.DataCatalogConfig, Option[Schema.DataCatalogConfig]] = (fun: Schema.DataCatalogConfig) => Option(fun)
		given putSchemaMaintenanceWindowDayOfWeekEnum: Conversion[Schema.MaintenanceWindow.DayOfWeekEnum, Option[Schema.MaintenanceWindow.DayOfWeekEnum]] = (fun: Schema.MaintenanceWindow.DayOfWeekEnum) => Option(fun)
		given putListSchemaMetadataExport: Conversion[List[Schema.MetadataExport], Option[List[Schema.MetadataExport]]] = (fun: List[Schema.MetadataExport]) => Option(fun)
		given putListSchemaRestore: Conversion[List[Schema.Restore], Option[List[Schema.Restore]]] = (fun: List[Schema.Restore]) => Option(fun)
		given putSchemaMetadataExportStateEnum: Conversion[Schema.MetadataExport.StateEnum, Option[Schema.MetadataExport.StateEnum]] = (fun: Schema.MetadataExport.StateEnum) => Option(fun)
		given putSchemaMetadataExportDatabaseDumpTypeEnum: Conversion[Schema.MetadataExport.DatabaseDumpTypeEnum, Option[Schema.MetadataExport.DatabaseDumpTypeEnum]] = (fun: Schema.MetadataExport.DatabaseDumpTypeEnum) => Option(fun)
		given putSchemaRestoreStateEnum: Conversion[Schema.Restore.StateEnum, Option[Schema.Restore.StateEnum]] = (fun: Schema.Restore.StateEnum) => Option(fun)
		given putSchemaRestoreTypeEnum: Conversion[Schema.Restore.TypeEnum, Option[Schema.Restore.TypeEnum]] = (fun: Schema.Restore.TypeEnum) => Option(fun)
		given putSchemaTelemetryConfigLogFormatEnum: Conversion[Schema.TelemetryConfig.LogFormatEnum, Option[Schema.TelemetryConfig.LogFormatEnum]] = (fun: Schema.TelemetryConfig.LogFormatEnum) => Option(fun)
		given putSchemaScalingConfigInstanceSizeEnum: Conversion[Schema.ScalingConfig.InstanceSizeEnum, Option[Schema.ScalingConfig.InstanceSizeEnum]] = (fun: Schema.ScalingConfig.InstanceSizeEnum) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putSchemaAutoscalingConfig: Conversion[Schema.AutoscalingConfig, Option[Schema.AutoscalingConfig]] = (fun: Schema.AutoscalingConfig) => Option(fun)
		given putSchemaLimitConfig: Conversion[Schema.LimitConfig, Option[Schema.LimitConfig]] = (fun: Schema.LimitConfig) => Option(fun)
		given putSchemaLatestBackup: Conversion[Schema.LatestBackup, Option[Schema.LatestBackup]] = (fun: Schema.LatestBackup) => Option(fun)
		given putSchemaLatestBackupStateEnum: Conversion[Schema.LatestBackup.StateEnum, Option[Schema.LatestBackup.StateEnum]] = (fun: Schema.LatestBackup.StateEnum) => Option(fun)
		given putListSchemaMetadataImport: Conversion[List[Schema.MetadataImport], Option[List[Schema.MetadataImport]]] = (fun: List[Schema.MetadataImport]) => Option(fun)
		given putSchemaDatabaseDump: Conversion[Schema.DatabaseDump, Option[Schema.DatabaseDump]] = (fun: Schema.DatabaseDump) => Option(fun)
		given putSchemaMetadataImportStateEnum: Conversion[Schema.MetadataImport.StateEnum, Option[Schema.MetadataImport.StateEnum]] = (fun: Schema.MetadataImport.StateEnum) => Option(fun)
		given putSchemaDatabaseDumpDatabaseTypeEnum: Conversion[Schema.DatabaseDump.DatabaseTypeEnum, Option[Schema.DatabaseDump.DatabaseTypeEnum]] = (fun: Schema.DatabaseDump.DatabaseTypeEnum) => Option(fun)
		given putSchemaDatabaseDumpTypeEnum: Conversion[Schema.DatabaseDump.TypeEnum, Option[Schema.DatabaseDump.TypeEnum]] = (fun: Schema.DatabaseDump.TypeEnum) => Option(fun)
		given putSchemaExportMetadataRequestDatabaseDumpTypeEnum: Conversion[Schema.ExportMetadataRequest.DatabaseDumpTypeEnum, Option[Schema.ExportMetadataRequest.DatabaseDumpTypeEnum]] = (fun: Schema.ExportMetadataRequest.DatabaseDumpTypeEnum) => Option(fun)
		given putSchemaRestoreServiceRequestRestoreTypeEnum: Conversion[Schema.RestoreServiceRequest.RestoreTypeEnum, Option[Schema.RestoreServiceRequest.RestoreTypeEnum]] = (fun: Schema.RestoreServiceRequest.RestoreTypeEnum) => Option(fun)
		given putListSchemaBackup: Conversion[List[Schema.Backup], Option[List[Schema.Backup]]] = (fun: List[Schema.Backup]) => Option(fun)
		given putSchemaBackupStateEnum: Conversion[Schema.Backup.StateEnum, Option[Schema.Backup.StateEnum]] = (fun: Schema.Backup.StateEnum) => Option(fun)
		given putSchemaService: Conversion[Schema.Service, Option[Schema.Service]] = (fun: Schema.Service) => Option(fun)
		given putSchemaMigrationExecution: Conversion[Schema.MigrationExecution, Option[Schema.MigrationExecution]] = (fun: Schema.MigrationExecution) => Option(fun)
		given putSchemaCloudSQLMigrationConfig: Conversion[Schema.CloudSQLMigrationConfig, Option[Schema.CloudSQLMigrationConfig]] = (fun: Schema.CloudSQLMigrationConfig) => Option(fun)
		given putSchemaMigrationExecutionStateEnum: Conversion[Schema.MigrationExecution.StateEnum, Option[Schema.MigrationExecution.StateEnum]] = (fun: Schema.MigrationExecution.StateEnum) => Option(fun)
		given putSchemaMigrationExecutionPhaseEnum: Conversion[Schema.MigrationExecution.PhaseEnum, Option[Schema.MigrationExecution.PhaseEnum]] = (fun: Schema.MigrationExecution.PhaseEnum) => Option(fun)
		given putSchemaCdcConfig: Conversion[Schema.CdcConfig, Option[Schema.CdcConfig]] = (fun: Schema.CdcConfig) => Option(fun)
		given putSchemaCloudSQLConnectionConfig: Conversion[Schema.CloudSQLConnectionConfig, Option[Schema.CloudSQLConnectionConfig]] = (fun: Schema.CloudSQLConnectionConfig) => Option(fun)
		given putListSchemaMigrationExecution: Conversion[List[Schema.MigrationExecution], Option[List[Schema.MigrationExecution]]] = (fun: List[Schema.MigrationExecution]) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
		given putListSchemaHiveMetastoreVersion: Conversion[List[Schema.HiveMetastoreVersion], Option[List[Schema.HiveMetastoreVersion]]] = (fun: List[Schema.HiveMetastoreVersion]) => Option(fun)
		given putSchemaMultiRegionMetadata: Conversion[Schema.MultiRegionMetadata, Option[Schema.MultiRegionMetadata]] = (fun: Schema.MultiRegionMetadata) => Option(fun)
		given putListSchemaCustomRegionMetadata: Conversion[List[Schema.CustomRegionMetadata], Option[List[Schema.CustomRegionMetadata]]] = (fun: List[Schema.CustomRegionMetadata]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
