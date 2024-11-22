package com.boresjo.play.api.google.file

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
		given putListSchemaInstance: Conversion[List[Schema.Instance], Option[List[Schema.Instance]]] = (fun: List[Schema.Instance]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaInstanceStateEnum: Conversion[Schema.Instance.StateEnum, Option[Schema.Instance.StateEnum]] = (fun: Schema.Instance.StateEnum) => Option(fun)
		given putSchemaInstanceTierEnum: Conversion[Schema.Instance.TierEnum, Option[Schema.Instance.TierEnum]] = (fun: Schema.Instance.TierEnum) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putListSchemaFileShareConfig: Conversion[List[Schema.FileShareConfig], Option[List[Schema.FileShareConfig]]] = (fun: List[Schema.FileShareConfig]) => Option(fun)
		given putListSchemaNetworkConfig: Conversion[List[Schema.NetworkConfig], Option[List[Schema.NetworkConfig]]] = (fun: List[Schema.NetworkConfig]) => Option(fun)
		given putListSchemaInstanceSuspensionReasonsEnum: Conversion[List[Schema.Instance.SuspensionReasonsEnum], Option[List[Schema.Instance.SuspensionReasonsEnum]]] = (fun: List[Schema.Instance.SuspensionReasonsEnum]) => Option(fun)
		given putSchemaReplication: Conversion[Schema.Replication, Option[Schema.Replication]] = (fun: Schema.Replication) => Option(fun)
		given putSchemaInstanceProtocolEnum: Conversion[Schema.Instance.ProtocolEnum, Option[Schema.Instance.ProtocolEnum]] = (fun: Schema.Instance.ProtocolEnum) => Option(fun)
		given putSchemaPerformanceConfig: Conversion[Schema.PerformanceConfig, Option[Schema.PerformanceConfig]] = (fun: Schema.PerformanceConfig) => Option(fun)
		given putSchemaPerformanceLimits: Conversion[Schema.PerformanceLimits, Option[Schema.PerformanceLimits]] = (fun: Schema.PerformanceLimits) => Option(fun)
		given putListSchemaNfsExportOptions: Conversion[List[Schema.NfsExportOptions], Option[List[Schema.NfsExportOptions]]] = (fun: List[Schema.NfsExportOptions]) => Option(fun)
		given putSchemaNfsExportOptionsAccessModeEnum: Conversion[Schema.NfsExportOptions.AccessModeEnum, Option[Schema.NfsExportOptions.AccessModeEnum]] = (fun: Schema.NfsExportOptions.AccessModeEnum) => Option(fun)
		given putSchemaNfsExportOptionsSquashModeEnum: Conversion[Schema.NfsExportOptions.SquashModeEnum, Option[Schema.NfsExportOptions.SquashModeEnum]] = (fun: Schema.NfsExportOptions.SquashModeEnum) => Option(fun)
		given putListSchemaNetworkConfigModesEnum: Conversion[List[Schema.NetworkConfig.ModesEnum], Option[List[Schema.NetworkConfig.ModesEnum]]] = (fun: List[Schema.NetworkConfig.ModesEnum]) => Option(fun)
		given putSchemaNetworkConfigConnectModeEnum: Conversion[Schema.NetworkConfig.ConnectModeEnum, Option[Schema.NetworkConfig.ConnectModeEnum]] = (fun: Schema.NetworkConfig.ConnectModeEnum) => Option(fun)
		given putSchemaReplicationRoleEnum: Conversion[Schema.Replication.RoleEnum, Option[Schema.Replication.RoleEnum]] = (fun: Schema.Replication.RoleEnum) => Option(fun)
		given putListSchemaReplicaConfig: Conversion[List[Schema.ReplicaConfig], Option[List[Schema.ReplicaConfig]]] = (fun: List[Schema.ReplicaConfig]) => Option(fun)
		given putSchemaReplicaConfigStateEnum: Conversion[Schema.ReplicaConfig.StateEnum, Option[Schema.ReplicaConfig.StateEnum]] = (fun: Schema.ReplicaConfig.StateEnum) => Option(fun)
		given putListSchemaReplicaConfigStateReasonsEnum: Conversion[List[Schema.ReplicaConfig.StateReasonsEnum], Option[List[Schema.ReplicaConfig.StateReasonsEnum]]] = (fun: List[Schema.ReplicaConfig.StateReasonsEnum]) => Option(fun)
		given putSchemaIOPSPerTB: Conversion[Schema.IOPSPerTB, Option[Schema.IOPSPerTB]] = (fun: Schema.IOPSPerTB) => Option(fun)
		given putSchemaFixedIOPS: Conversion[Schema.FixedIOPS, Option[Schema.FixedIOPS]] = (fun: Schema.FixedIOPS) => Option(fun)
		given putListSchemaSnapshot: Conversion[List[Schema.Snapshot], Option[List[Schema.Snapshot]]] = (fun: List[Schema.Snapshot]) => Option(fun)
		given putSchemaSnapshotStateEnum: Conversion[Schema.Snapshot.StateEnum, Option[Schema.Snapshot.StateEnum]] = (fun: Schema.Snapshot.StateEnum) => Option(fun)
		given putListSchemaBackup: Conversion[List[Schema.Backup], Option[List[Schema.Backup]]] = (fun: List[Schema.Backup]) => Option(fun)
		given putSchemaBackupStateEnum: Conversion[Schema.Backup.StateEnum, Option[Schema.Backup.StateEnum]] = (fun: Schema.Backup.StateEnum) => Option(fun)
		given putSchemaBackupSourceInstanceTierEnum: Conversion[Schema.Backup.SourceInstanceTierEnum, Option[Schema.Backup.SourceInstanceTierEnum]] = (fun: Schema.Backup.SourceInstanceTierEnum) => Option(fun)
		given putSchemaBackupFileSystemProtocolEnum: Conversion[Schema.Backup.FileSystemProtocolEnum, Option[Schema.Backup.FileSystemProtocolEnum]] = (fun: Schema.Backup.FileSystemProtocolEnum) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putSchemaGoogleCloudSaasacceleratorManagementProvidersV1InstanceStateEnum: Conversion[Schema.GoogleCloudSaasacceleratorManagementProvidersV1Instance.StateEnum, Option[Schema.GoogleCloudSaasacceleratorManagementProvidersV1Instance.StateEnum]] = (fun: Schema.GoogleCloudSaasacceleratorManagementProvidersV1Instance.StateEnum) => Option(fun)
		given putListSchemaGoogleCloudSaasacceleratorManagementProvidersV1ProvisionedResource: Conversion[List[Schema.GoogleCloudSaasacceleratorManagementProvidersV1ProvisionedResource], Option[List[Schema.GoogleCloudSaasacceleratorManagementProvidersV1ProvisionedResource]]] = (fun: List[Schema.GoogleCloudSaasacceleratorManagementProvidersV1ProvisionedResource]) => Option(fun)
		given putSchemaGoogleCloudSaasacceleratorManagementProvidersV1SloMetadata: Conversion[Schema.GoogleCloudSaasacceleratorManagementProvidersV1SloMetadata, Option[Schema.GoogleCloudSaasacceleratorManagementProvidersV1SloMetadata]] = (fun: Schema.GoogleCloudSaasacceleratorManagementProvidersV1SloMetadata) => Option(fun)
		given putMapStringSchemaGoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSchedule: Conversion[Map[String, Schema.GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSchedule], Option[Map[String, Schema.GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSchedule]]] = (fun: Map[String, Schema.GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSchedule]) => Option(fun)
		given putSchemaGoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSettings: Conversion[Schema.GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSettings, Option[Schema.GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSettings]] = (fun: Schema.GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSettings) => Option(fun)
		given putMapStringSchemaGoogleCloudSaasacceleratorManagementProvidersV1NotificationParameter: Conversion[Map[String, Schema.GoogleCloudSaasacceleratorManagementProvidersV1NotificationParameter], Option[Map[String, Schema.GoogleCloudSaasacceleratorManagementProvidersV1NotificationParameter]]] = (fun: Map[String, Schema.GoogleCloudSaasacceleratorManagementProvidersV1NotificationParameter]) => Option(fun)
		given putListSchemaGoogleCloudSaasacceleratorManagementProvidersV1NodeSloMetadata: Conversion[List[Schema.GoogleCloudSaasacceleratorManagementProvidersV1NodeSloMetadata], Option[List[Schema.GoogleCloudSaasacceleratorManagementProvidersV1NodeSloMetadata]]] = (fun: List[Schema.GoogleCloudSaasacceleratorManagementProvidersV1NodeSloMetadata]) => Option(fun)
		given putSchemaGoogleCloudSaasacceleratorManagementProvidersV1PerSliSloEligibility: Conversion[Schema.GoogleCloudSaasacceleratorManagementProvidersV1PerSliSloEligibility, Option[Schema.GoogleCloudSaasacceleratorManagementProvidersV1PerSliSloEligibility]] = (fun: Schema.GoogleCloudSaasacceleratorManagementProvidersV1PerSliSloEligibility) => Option(fun)
		given putMapStringSchemaGoogleCloudSaasacceleratorManagementProvidersV1SloEligibility: Conversion[Map[String, Schema.GoogleCloudSaasacceleratorManagementProvidersV1SloEligibility], Option[Map[String, Schema.GoogleCloudSaasacceleratorManagementProvidersV1SloEligibility]]] = (fun: Map[String, Schema.GoogleCloudSaasacceleratorManagementProvidersV1SloEligibility]) => Option(fun)
		given putMapStringSchemaMaintenancePolicy: Conversion[Map[String, Schema.MaintenancePolicy], Option[Map[String, Schema.MaintenancePolicy]]] = (fun: Map[String, Schema.MaintenancePolicy]) => Option(fun)
		given putSchemaMaintenancePolicyStateEnum: Conversion[Schema.MaintenancePolicy.StateEnum, Option[Schema.MaintenancePolicy.StateEnum]] = (fun: Schema.MaintenancePolicy.StateEnum) => Option(fun)
		given putSchemaUpdatePolicy: Conversion[Schema.UpdatePolicy, Option[Schema.UpdatePolicy]] = (fun: Schema.UpdatePolicy) => Option(fun)
		given putSchemaMaintenanceWindow: Conversion[Schema.MaintenanceWindow, Option[Schema.MaintenanceWindow]] = (fun: Schema.MaintenanceWindow) => Option(fun)
		given putSchemaUpdatePolicyChannelEnum: Conversion[Schema.UpdatePolicy.ChannelEnum, Option[Schema.UpdatePolicy.ChannelEnum]] = (fun: Schema.UpdatePolicy.ChannelEnum) => Option(fun)
		given putListSchemaDenyMaintenancePeriod: Conversion[List[Schema.DenyMaintenancePeriod], Option[List[Schema.DenyMaintenancePeriod]]] = (fun: List[Schema.DenyMaintenancePeriod]) => Option(fun)
		given putSchemaDailyCycle: Conversion[Schema.DailyCycle, Option[Schema.DailyCycle]] = (fun: Schema.DailyCycle) => Option(fun)
		given putSchemaWeeklyCycle: Conversion[Schema.WeeklyCycle, Option[Schema.WeeklyCycle]] = (fun: Schema.WeeklyCycle) => Option(fun)
		given putSchemaTimeOfDay: Conversion[Schema.TimeOfDay, Option[Schema.TimeOfDay]] = (fun: Schema.TimeOfDay) => Option(fun)
		given putListSchemaSchedule: Conversion[List[Schema.Schedule], Option[List[Schema.Schedule]]] = (fun: List[Schema.Schedule]) => Option(fun)
		given putSchemaScheduleDayEnum: Conversion[Schema.Schedule.DayEnum, Option[Schema.Schedule.DayEnum]] = (fun: Schema.Schedule.DayEnum) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
