package com.boresjo.play.api.google.backupdr

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaManagementServer: Conversion[List[Schema.ManagementServer], Option[List[Schema.ManagementServer]]] = (fun: List[Schema.ManagementServer]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaManagementServerTypeEnum: Conversion[Schema.ManagementServer.TypeEnum, Option[Schema.ManagementServer.TypeEnum]] = (fun: Schema.ManagementServer.TypeEnum) => Option(fun)
		given putSchemaManagementURI: Conversion[Schema.ManagementURI, Option[Schema.ManagementURI]] = (fun: Schema.ManagementURI) => Option(fun)
		given putSchemaWorkforceIdentityBasedManagementURI: Conversion[Schema.WorkforceIdentityBasedManagementURI, Option[Schema.WorkforceIdentityBasedManagementURI]] = (fun: Schema.WorkforceIdentityBasedManagementURI) => Option(fun)
		given putSchemaManagementServerStateEnum: Conversion[Schema.ManagementServer.StateEnum, Option[Schema.ManagementServer.StateEnum]] = (fun: Schema.ManagementServer.StateEnum) => Option(fun)
		given putListSchemaNetworkConfig: Conversion[List[Schema.NetworkConfig], Option[List[Schema.NetworkConfig]]] = (fun: List[Schema.NetworkConfig]) => Option(fun)
		given putSchemaWorkforceIdentityBasedOAuth2ClientID: Conversion[Schema.WorkforceIdentityBasedOAuth2ClientID, Option[Schema.WorkforceIdentityBasedOAuth2ClientID]] = (fun: Schema.WorkforceIdentityBasedOAuth2ClientID) => Option(fun)
		given putSchemaNetworkConfigPeeringModeEnum: Conversion[Schema.NetworkConfig.PeeringModeEnum, Option[Schema.NetworkConfig.PeeringModeEnum]] = (fun: Schema.NetworkConfig.PeeringModeEnum) => Option(fun)
		given putSchemaBackupVaultStateEnum: Conversion[Schema.BackupVault.StateEnum, Option[Schema.BackupVault.StateEnum]] = (fun: Schema.BackupVault.StateEnum) => Option(fun)
		given putSchemaBackupVaultAccessRestrictionEnum: Conversion[Schema.BackupVault.AccessRestrictionEnum, Option[Schema.BackupVault.AccessRestrictionEnum]] = (fun: Schema.BackupVault.AccessRestrictionEnum) => Option(fun)
		given putListSchemaBackupVault: Conversion[List[Schema.BackupVault], Option[List[Schema.BackupVault]]] = (fun: List[Schema.BackupVault]) => Option(fun)
		given putListSchemaDataSource: Conversion[List[Schema.DataSource], Option[List[Schema.DataSource]]] = (fun: List[Schema.DataSource]) => Option(fun)
		given putSchemaDataSourceStateEnum: Conversion[Schema.DataSource.StateEnum, Option[Schema.DataSource.StateEnum]] = (fun: Schema.DataSource.StateEnum) => Option(fun)
		given putSchemaDataSourceConfigStateEnum: Conversion[Schema.DataSource.ConfigStateEnum, Option[Schema.DataSource.ConfigStateEnum]] = (fun: Schema.DataSource.ConfigStateEnum) => Option(fun)
		given putSchemaBackupConfigInfo: Conversion[Schema.BackupConfigInfo, Option[Schema.BackupConfigInfo]] = (fun: Schema.BackupConfigInfo) => Option(fun)
		given putSchemaDataSourceGcpResource: Conversion[Schema.DataSourceGcpResource, Option[Schema.DataSourceGcpResource]] = (fun: Schema.DataSourceGcpResource) => Option(fun)
		given putSchemaDataSourceBackupApplianceApplication: Conversion[Schema.DataSourceBackupApplianceApplication, Option[Schema.DataSourceBackupApplianceApplication]] = (fun: Schema.DataSourceBackupApplianceApplication) => Option(fun)
		given putSchemaBackupConfigInfoLastBackupStateEnum: Conversion[Schema.BackupConfigInfo.LastBackupStateEnum, Option[Schema.BackupConfigInfo.LastBackupStateEnum]] = (fun: Schema.BackupConfigInfo.LastBackupStateEnum) => Option(fun)
		given putSchemaGcpBackupConfig: Conversion[Schema.GcpBackupConfig, Option[Schema.GcpBackupConfig]] = (fun: Schema.GcpBackupConfig) => Option(fun)
		given putSchemaBackupApplianceBackupConfig: Conversion[Schema.BackupApplianceBackupConfig, Option[Schema.BackupApplianceBackupConfig]] = (fun: Schema.BackupApplianceBackupConfig) => Option(fun)
		given putSchemaComputeInstanceDataSourceProperties: Conversion[Schema.ComputeInstanceDataSourceProperties, Option[Schema.ComputeInstanceDataSourceProperties]] = (fun: Schema.ComputeInstanceDataSourceProperties) => Option(fun)
		given putSchemaSetInternalStatusRequestBackupConfigStateEnum: Conversion[Schema.SetInternalStatusRequest.BackupConfigStateEnum, Option[Schema.SetInternalStatusRequest.BackupConfigStateEnum]] = (fun: Schema.SetInternalStatusRequest.BackupConfigStateEnum) => Option(fun)
		given putListSchemaBackup: Conversion[List[Schema.Backup], Option[List[Schema.Backup]]] = (fun: List[Schema.Backup]) => Option(fun)
		given putSchemaBackupStateEnum: Conversion[Schema.Backup.StateEnum, Option[Schema.Backup.StateEnum]] = (fun: Schema.Backup.StateEnum) => Option(fun)
		given putListSchemaBackupLock: Conversion[List[Schema.BackupLock], Option[List[Schema.BackupLock]]] = (fun: List[Schema.BackupLock]) => Option(fun)
		given putSchemaComputeInstanceBackupProperties: Conversion[Schema.ComputeInstanceBackupProperties, Option[Schema.ComputeInstanceBackupProperties]] = (fun: Schema.ComputeInstanceBackupProperties) => Option(fun)
		given putSchemaBackupApplianceBackupProperties: Conversion[Schema.BackupApplianceBackupProperties, Option[Schema.BackupApplianceBackupProperties]] = (fun: Schema.BackupApplianceBackupProperties) => Option(fun)
		given putSchemaBackupBackupTypeEnum: Conversion[Schema.Backup.BackupTypeEnum, Option[Schema.Backup.BackupTypeEnum]] = (fun: Schema.Backup.BackupTypeEnum) => Option(fun)
		given putSchemaGCPBackupPlanInfo: Conversion[Schema.GCPBackupPlanInfo, Option[Schema.GCPBackupPlanInfo]] = (fun: Schema.GCPBackupPlanInfo) => Option(fun)
		given putSchemaBackupApplianceLockInfo: Conversion[Schema.BackupApplianceLockInfo, Option[Schema.BackupApplianceLockInfo]] = (fun: Schema.BackupApplianceLockInfo) => Option(fun)
		given putSchemaServiceLockInfo: Conversion[Schema.ServiceLockInfo, Option[Schema.ServiceLockInfo]] = (fun: Schema.ServiceLockInfo) => Option(fun)
		given putSchemaTags: Conversion[Schema.Tags, Option[Schema.Tags]] = (fun: Schema.Tags) => Option(fun)
		given putListSchemaNetworkInterface: Conversion[List[Schema.NetworkInterface], Option[List[Schema.NetworkInterface]]] = (fun: List[Schema.NetworkInterface]) => Option(fun)
		given putListSchemaAttachedDisk: Conversion[List[Schema.AttachedDisk], Option[List[Schema.AttachedDisk]]] = (fun: List[Schema.AttachedDisk]) => Option(fun)
		given putSchemaMetadata: Conversion[Schema.Metadata, Option[Schema.Metadata]] = (fun: Schema.Metadata) => Option(fun)
		given putListSchemaServiceAccount: Conversion[List[Schema.ServiceAccount], Option[List[Schema.ServiceAccount]]] = (fun: List[Schema.ServiceAccount]) => Option(fun)
		given putSchemaScheduling: Conversion[Schema.Scheduling, Option[Schema.Scheduling]] = (fun: Schema.Scheduling) => Option(fun)
		given putListSchemaAcceleratorConfig: Conversion[List[Schema.AcceleratorConfig], Option[List[Schema.AcceleratorConfig]]] = (fun: List[Schema.AcceleratorConfig]) => Option(fun)
		given putSchemaComputeInstanceBackupPropertiesKeyRevocationActionTypeEnum: Conversion[Schema.ComputeInstanceBackupProperties.KeyRevocationActionTypeEnum, Option[Schema.ComputeInstanceBackupProperties.KeyRevocationActionTypeEnum]] = (fun: Schema.ComputeInstanceBackupProperties.KeyRevocationActionTypeEnum) => Option(fun)
		given putListSchemaAccessConfig: Conversion[List[Schema.AccessConfig], Option[List[Schema.AccessConfig]]] = (fun: List[Schema.AccessConfig]) => Option(fun)
		given putListSchemaAliasIpRange: Conversion[List[Schema.AliasIpRange], Option[List[Schema.AliasIpRange]]] = (fun: List[Schema.AliasIpRange]) => Option(fun)
		given putSchemaNetworkInterfaceStackTypeEnum: Conversion[Schema.NetworkInterface.StackTypeEnum, Option[Schema.NetworkInterface.StackTypeEnum]] = (fun: Schema.NetworkInterface.StackTypeEnum) => Option(fun)
		given putSchemaNetworkInterfaceIpv6AccessTypeEnum: Conversion[Schema.NetworkInterface.Ipv6AccessTypeEnum, Option[Schema.NetworkInterface.Ipv6AccessTypeEnum]] = (fun: Schema.NetworkInterface.Ipv6AccessTypeEnum) => Option(fun)
		given putSchemaNetworkInterfaceNicTypeEnum: Conversion[Schema.NetworkInterface.NicTypeEnum, Option[Schema.NetworkInterface.NicTypeEnum]] = (fun: Schema.NetworkInterface.NicTypeEnum) => Option(fun)
		given putSchemaAccessConfigTypeEnum: Conversion[Schema.AccessConfig.TypeEnum, Option[Schema.AccessConfig.TypeEnum]] = (fun: Schema.AccessConfig.TypeEnum) => Option(fun)
		given putSchemaAccessConfigNetworkTierEnum: Conversion[Schema.AccessConfig.NetworkTierEnum, Option[Schema.AccessConfig.NetworkTierEnum]] = (fun: Schema.AccessConfig.NetworkTierEnum) => Option(fun)
		given putSchemaInitializeParams: Conversion[Schema.InitializeParams, Option[Schema.InitializeParams]] = (fun: Schema.InitializeParams) => Option(fun)
		given putSchemaAttachedDiskDiskTypeDeprecatedEnum: Conversion[Schema.AttachedDisk.DiskTypeDeprecatedEnum, Option[Schema.AttachedDisk.DiskTypeDeprecatedEnum]] = (fun: Schema.AttachedDisk.DiskTypeDeprecatedEnum) => Option(fun)
		given putSchemaAttachedDiskModeEnum: Conversion[Schema.AttachedDisk.ModeEnum, Option[Schema.AttachedDisk.ModeEnum]] = (fun: Schema.AttachedDisk.ModeEnum) => Option(fun)
		given putSchemaAttachedDiskDiskInterfaceEnum: Conversion[Schema.AttachedDisk.DiskInterfaceEnum, Option[Schema.AttachedDisk.DiskInterfaceEnum]] = (fun: Schema.AttachedDisk.DiskInterfaceEnum) => Option(fun)
		given putListSchemaGuestOsFeature: Conversion[List[Schema.GuestOsFeature], Option[List[Schema.GuestOsFeature]]] = (fun: List[Schema.GuestOsFeature]) => Option(fun)
		given putSchemaCustomerEncryptionKey: Conversion[Schema.CustomerEncryptionKey, Option[Schema.CustomerEncryptionKey]] = (fun: Schema.CustomerEncryptionKey) => Option(fun)
		given putSchemaAttachedDiskSavedStateEnum: Conversion[Schema.AttachedDisk.SavedStateEnum, Option[Schema.AttachedDisk.SavedStateEnum]] = (fun: Schema.AttachedDisk.SavedStateEnum) => Option(fun)
		given putSchemaAttachedDiskTypeEnum: Conversion[Schema.AttachedDisk.TypeEnum, Option[Schema.AttachedDisk.TypeEnum]] = (fun: Schema.AttachedDisk.TypeEnum) => Option(fun)
		given putSchemaGuestOsFeatureTypeEnum: Conversion[Schema.GuestOsFeature.TypeEnum, Option[Schema.GuestOsFeature.TypeEnum]] = (fun: Schema.GuestOsFeature.TypeEnum) => Option(fun)
		given putListSchemaEntry: Conversion[List[Schema.Entry], Option[List[Schema.Entry]]] = (fun: List[Schema.Entry]) => Option(fun)
		given putSchemaSchedulingOnHostMaintenanceEnum: Conversion[Schema.Scheduling.OnHostMaintenanceEnum, Option[Schema.Scheduling.OnHostMaintenanceEnum]] = (fun: Schema.Scheduling.OnHostMaintenanceEnum) => Option(fun)
		given putListSchemaNodeAffinity: Conversion[List[Schema.NodeAffinity], Option[List[Schema.NodeAffinity]]] = (fun: List[Schema.NodeAffinity]) => Option(fun)
		given putSchemaSchedulingProvisioningModelEnum: Conversion[Schema.Scheduling.ProvisioningModelEnum, Option[Schema.Scheduling.ProvisioningModelEnum]] = (fun: Schema.Scheduling.ProvisioningModelEnum) => Option(fun)
		given putSchemaSchedulingInstanceTerminationActionEnum: Conversion[Schema.Scheduling.InstanceTerminationActionEnum, Option[Schema.Scheduling.InstanceTerminationActionEnum]] = (fun: Schema.Scheduling.InstanceTerminationActionEnum) => Option(fun)
		given putSchemaSchedulingDuration: Conversion[Schema.SchedulingDuration, Option[Schema.SchedulingDuration]] = (fun: Schema.SchedulingDuration) => Option(fun)
		given putSchemaNodeAffinityOperatorEnum: Conversion[Schema.NodeAffinity.OperatorEnum, Option[Schema.NodeAffinity.OperatorEnum]] = (fun: Schema.NodeAffinity.OperatorEnum) => Option(fun)
		given putSchemaComputeInstanceTargetEnvironment: Conversion[Schema.ComputeInstanceTargetEnvironment, Option[Schema.ComputeInstanceTargetEnvironment]] = (fun: Schema.ComputeInstanceTargetEnvironment) => Option(fun)
		given putSchemaComputeInstanceRestoreProperties: Conversion[Schema.ComputeInstanceRestoreProperties, Option[Schema.ComputeInstanceRestoreProperties]] = (fun: Schema.ComputeInstanceRestoreProperties) => Option(fun)
		given putSchemaAdvancedMachineFeatures: Conversion[Schema.AdvancedMachineFeatures, Option[Schema.AdvancedMachineFeatures]] = (fun: Schema.AdvancedMachineFeatures) => Option(fun)
		given putSchemaConfidentialInstanceConfig: Conversion[Schema.ConfidentialInstanceConfig, Option[Schema.ConfidentialInstanceConfig]] = (fun: Schema.ConfidentialInstanceConfig) => Option(fun)
		given putSchemaDisplayDevice: Conversion[Schema.DisplayDevice, Option[Schema.DisplayDevice]] = (fun: Schema.DisplayDevice) => Option(fun)
		given putSchemaComputeInstanceRestorePropertiesKeyRevocationActionTypeEnum: Conversion[Schema.ComputeInstanceRestoreProperties.KeyRevocationActionTypeEnum, Option[Schema.ComputeInstanceRestoreProperties.KeyRevocationActionTypeEnum]] = (fun: Schema.ComputeInstanceRestoreProperties.KeyRevocationActionTypeEnum) => Option(fun)
		given putSchemaNetworkPerformanceConfig: Conversion[Schema.NetworkPerformanceConfig, Option[Schema.NetworkPerformanceConfig]] = (fun: Schema.NetworkPerformanceConfig) => Option(fun)
		given putSchemaInstanceParams: Conversion[Schema.InstanceParams, Option[Schema.InstanceParams]] = (fun: Schema.InstanceParams) => Option(fun)
		given putSchemaComputeInstanceRestorePropertiesPrivateIpv6GoogleAccessEnum: Conversion[Schema.ComputeInstanceRestoreProperties.PrivateIpv6GoogleAccessEnum, Option[Schema.ComputeInstanceRestoreProperties.PrivateIpv6GoogleAccessEnum]] = (fun: Schema.ComputeInstanceRestoreProperties.PrivateIpv6GoogleAccessEnum) => Option(fun)
		given putSchemaAllocationAffinity: Conversion[Schema.AllocationAffinity, Option[Schema.AllocationAffinity]] = (fun: Schema.AllocationAffinity) => Option(fun)
		given putSchemaNetworkPerformanceConfigTotalEgressBandwidthTierEnum: Conversion[Schema.NetworkPerformanceConfig.TotalEgressBandwidthTierEnum, Option[Schema.NetworkPerformanceConfig.TotalEgressBandwidthTierEnum]] = (fun: Schema.NetworkPerformanceConfig.TotalEgressBandwidthTierEnum) => Option(fun)
		given putSchemaAllocationAffinityConsumeReservationTypeEnum: Conversion[Schema.AllocationAffinity.ConsumeReservationTypeEnum, Option[Schema.AllocationAffinity.ConsumeReservationTypeEnum]] = (fun: Schema.AllocationAffinity.ConsumeReservationTypeEnum) => Option(fun)
		given putListSchemaBackupRule: Conversion[List[Schema.BackupRule], Option[List[Schema.BackupRule]]] = (fun: List[Schema.BackupRule]) => Option(fun)
		given putSchemaBackupPlanStateEnum: Conversion[Schema.BackupPlan.StateEnum, Option[Schema.BackupPlan.StateEnum]] = (fun: Schema.BackupPlan.StateEnum) => Option(fun)
		given putSchemaStandardSchedule: Conversion[Schema.StandardSchedule, Option[Schema.StandardSchedule]] = (fun: Schema.StandardSchedule) => Option(fun)
		given putSchemaStandardScheduleRecurrenceTypeEnum: Conversion[Schema.StandardSchedule.RecurrenceTypeEnum, Option[Schema.StandardSchedule.RecurrenceTypeEnum]] = (fun: Schema.StandardSchedule.RecurrenceTypeEnum) => Option(fun)
		given putListSchemaStandardScheduleDaysOfWeekEnum: Conversion[List[Schema.StandardSchedule.DaysOfWeekEnum], Option[List[Schema.StandardSchedule.DaysOfWeekEnum]]] = (fun: List[Schema.StandardSchedule.DaysOfWeekEnum]) => Option(fun)
		given putListInt: Conversion[List[Int], Option[List[Int]]] = (fun: List[Int]) => Option(fun)
		given putSchemaWeekDayOfMonth: Conversion[Schema.WeekDayOfMonth, Option[Schema.WeekDayOfMonth]] = (fun: Schema.WeekDayOfMonth) => Option(fun)
		given putListSchemaStandardScheduleMonthsEnum: Conversion[List[Schema.StandardSchedule.MonthsEnum], Option[List[Schema.StandardSchedule.MonthsEnum]]] = (fun: List[Schema.StandardSchedule.MonthsEnum]) => Option(fun)
		given putSchemaBackupWindow: Conversion[Schema.BackupWindow, Option[Schema.BackupWindow]] = (fun: Schema.BackupWindow) => Option(fun)
		given putSchemaWeekDayOfMonthWeekOfMonthEnum: Conversion[Schema.WeekDayOfMonth.WeekOfMonthEnum, Option[Schema.WeekDayOfMonth.WeekOfMonthEnum]] = (fun: Schema.WeekDayOfMonth.WeekOfMonthEnum) => Option(fun)
		given putSchemaWeekDayOfMonthDayOfWeekEnum: Conversion[Schema.WeekDayOfMonth.DayOfWeekEnum, Option[Schema.WeekDayOfMonth.DayOfWeekEnum]] = (fun: Schema.WeekDayOfMonth.DayOfWeekEnum) => Option(fun)
		given putListSchemaBackupPlan: Conversion[List[Schema.BackupPlan], Option[List[Schema.BackupPlan]]] = (fun: List[Schema.BackupPlan]) => Option(fun)
		given putSchemaBackupPlanAssociationStateEnum: Conversion[Schema.BackupPlanAssociation.StateEnum, Option[Schema.BackupPlanAssociation.StateEnum]] = (fun: Schema.BackupPlanAssociation.StateEnum) => Option(fun)
		given putListSchemaRuleConfigInfo: Conversion[List[Schema.RuleConfigInfo], Option[List[Schema.RuleConfigInfo]]] = (fun: List[Schema.RuleConfigInfo]) => Option(fun)
		given putSchemaRuleConfigInfoLastBackupStateEnum: Conversion[Schema.RuleConfigInfo.LastBackupStateEnum, Option[Schema.RuleConfigInfo.LastBackupStateEnum]] = (fun: Schema.RuleConfigInfo.LastBackupStateEnum) => Option(fun)
		given putListSchemaBackupPlanAssociation: Conversion[List[Schema.BackupPlanAssociation], Option[List[Schema.BackupPlanAssociation]]] = (fun: List[Schema.BackupPlanAssociation]) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putSchemaTargetResource: Conversion[Schema.TargetResource, Option[Schema.TargetResource]] = (fun: Schema.TargetResource) => Option(fun)
		given putSchemaGcpResource: Conversion[Schema.GcpResource, Option[Schema.GcpResource]] = (fun: Schema.GcpResource) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
