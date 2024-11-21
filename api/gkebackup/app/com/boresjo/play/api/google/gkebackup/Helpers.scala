package com.boresjo.play.api.google.gkebackup

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaGoogleLongrunningOperation: Conversion[List[Schema.GoogleLongrunningOperation], Option[List[Schema.GoogleLongrunningOperation]]] = (fun: List[Schema.GoogleLongrunningOperation]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaGoogleRpcStatus: Conversion[Schema.GoogleRpcStatus, Option[Schema.GoogleRpcStatus]] = (fun: Schema.GoogleRpcStatus) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaRetentionPolicy: Conversion[Schema.RetentionPolicy, Option[Schema.RetentionPolicy]] = (fun: Schema.RetentionPolicy) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaSchedule: Conversion[Schema.Schedule, Option[Schema.Schedule]] = (fun: Schema.Schedule) => Option(fun)
		given putSchemaBackupConfig: Conversion[Schema.BackupConfig, Option[Schema.BackupConfig]] = (fun: Schema.BackupConfig) => Option(fun)
		given putSchemaBackupPlanStateEnum: Conversion[Schema.BackupPlan.StateEnum, Option[Schema.BackupPlan.StateEnum]] = (fun: Schema.BackupPlan.StateEnum) => Option(fun)
		given putSchemaRpoConfig: Conversion[Schema.RpoConfig, Option[Schema.RpoConfig]] = (fun: Schema.RpoConfig) => Option(fun)
		given putListSchemaExclusionWindow: Conversion[List[Schema.ExclusionWindow], Option[List[Schema.ExclusionWindow]]] = (fun: List[Schema.ExclusionWindow]) => Option(fun)
		given putSchemaTimeOfDay: Conversion[Schema.TimeOfDay, Option[Schema.TimeOfDay]] = (fun: Schema.TimeOfDay) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
		given putSchemaDayOfWeekList: Conversion[Schema.DayOfWeekList, Option[Schema.DayOfWeekList]] = (fun: Schema.DayOfWeekList) => Option(fun)
		given putListSchemaDayOfWeekListDaysOfWeekEnum: Conversion[List[Schema.DayOfWeekList.DaysOfWeekEnum], Option[List[Schema.DayOfWeekList.DaysOfWeekEnum]]] = (fun: List[Schema.DayOfWeekList.DaysOfWeekEnum]) => Option(fun)
		given putSchemaNamespaces: Conversion[Schema.Namespaces, Option[Schema.Namespaces]] = (fun: Schema.Namespaces) => Option(fun)
		given putSchemaNamespacedNames: Conversion[Schema.NamespacedNames, Option[Schema.NamespacedNames]] = (fun: Schema.NamespacedNames) => Option(fun)
		given putSchemaEncryptionKey: Conversion[Schema.EncryptionKey, Option[Schema.EncryptionKey]] = (fun: Schema.EncryptionKey) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaNamespacedName: Conversion[List[Schema.NamespacedName], Option[List[Schema.NamespacedName]]] = (fun: List[Schema.NamespacedName]) => Option(fun)
		given putListSchemaBackupPlan: Conversion[List[Schema.BackupPlan], Option[List[Schema.BackupPlan]]] = (fun: List[Schema.BackupPlan]) => Option(fun)
		given putSchemaClusterMetadata: Conversion[Schema.ClusterMetadata, Option[Schema.ClusterMetadata]] = (fun: Schema.ClusterMetadata) => Option(fun)
		given putSchemaBackupStateEnum: Conversion[Schema.Backup.StateEnum, Option[Schema.Backup.StateEnum]] = (fun: Schema.Backup.StateEnum) => Option(fun)
		given putListSchemaBackup: Conversion[List[Schema.Backup], Option[List[Schema.Backup]]] = (fun: List[Schema.Backup]) => Option(fun)
		given putListSchemaVolumeBackup: Conversion[List[Schema.VolumeBackup], Option[List[Schema.VolumeBackup]]] = (fun: List[Schema.VolumeBackup]) => Option(fun)
		given putSchemaNamespacedName: Conversion[Schema.NamespacedName, Option[Schema.NamespacedName]] = (fun: Schema.NamespacedName) => Option(fun)
		given putSchemaVolumeBackupFormatEnum: Conversion[Schema.VolumeBackup.FormatEnum, Option[Schema.VolumeBackup.FormatEnum]] = (fun: Schema.VolumeBackup.FormatEnum) => Option(fun)
		given putSchemaVolumeBackupStateEnum: Conversion[Schema.VolumeBackup.StateEnum, Option[Schema.VolumeBackup.StateEnum]] = (fun: Schema.VolumeBackup.StateEnum) => Option(fun)
		given putSchemaRestoreConfig: Conversion[Schema.RestoreConfig, Option[Schema.RestoreConfig]] = (fun: Schema.RestoreConfig) => Option(fun)
		given putSchemaRestorePlanStateEnum: Conversion[Schema.RestorePlan.StateEnum, Option[Schema.RestorePlan.StateEnum]] = (fun: Schema.RestorePlan.StateEnum) => Option(fun)
		given putSchemaRestoreConfigVolumeDataRestorePolicyEnum: Conversion[Schema.RestoreConfig.VolumeDataRestorePolicyEnum, Option[Schema.RestoreConfig.VolumeDataRestorePolicyEnum]] = (fun: Schema.RestoreConfig.VolumeDataRestorePolicyEnum) => Option(fun)
		given putSchemaRestoreConfigClusterResourceConflictPolicyEnum: Conversion[Schema.RestoreConfig.ClusterResourceConflictPolicyEnum, Option[Schema.RestoreConfig.ClusterResourceConflictPolicyEnum]] = (fun: Schema.RestoreConfig.ClusterResourceConflictPolicyEnum) => Option(fun)
		given putSchemaRestoreConfigNamespacedResourceRestoreModeEnum: Conversion[Schema.RestoreConfig.NamespacedResourceRestoreModeEnum, Option[Schema.RestoreConfig.NamespacedResourceRestoreModeEnum]] = (fun: Schema.RestoreConfig.NamespacedResourceRestoreModeEnum) => Option(fun)
		given putSchemaClusterResourceRestoreScope: Conversion[Schema.ClusterResourceRestoreScope, Option[Schema.ClusterResourceRestoreScope]] = (fun: Schema.ClusterResourceRestoreScope) => Option(fun)
		given putListSchemaSubstitutionRule: Conversion[List[Schema.SubstitutionRule], Option[List[Schema.SubstitutionRule]]] = (fun: List[Schema.SubstitutionRule]) => Option(fun)
		given putListSchemaTransformationRule: Conversion[List[Schema.TransformationRule], Option[List[Schema.TransformationRule]]] = (fun: List[Schema.TransformationRule]) => Option(fun)
		given putListSchemaVolumeDataRestorePolicyBinding: Conversion[List[Schema.VolumeDataRestorePolicyBinding], Option[List[Schema.VolumeDataRestorePolicyBinding]]] = (fun: List[Schema.VolumeDataRestorePolicyBinding]) => Option(fun)
		given putSchemaRestoreOrder: Conversion[Schema.RestoreOrder, Option[Schema.RestoreOrder]] = (fun: Schema.RestoreOrder) => Option(fun)
		given putListSchemaGroupKind: Conversion[List[Schema.GroupKind], Option[List[Schema.GroupKind]]] = (fun: List[Schema.GroupKind]) => Option(fun)
		given putListSchemaTransformationRuleAction: Conversion[List[Schema.TransformationRuleAction], Option[List[Schema.TransformationRuleAction]]] = (fun: List[Schema.TransformationRuleAction]) => Option(fun)
		given putSchemaResourceFilter: Conversion[Schema.ResourceFilter, Option[Schema.ResourceFilter]] = (fun: Schema.ResourceFilter) => Option(fun)
		given putSchemaTransformationRuleActionOpEnum: Conversion[Schema.TransformationRuleAction.OpEnum, Option[Schema.TransformationRuleAction.OpEnum]] = (fun: Schema.TransformationRuleAction.OpEnum) => Option(fun)
		given putSchemaVolumeDataRestorePolicyBindingPolicyEnum: Conversion[Schema.VolumeDataRestorePolicyBinding.PolicyEnum, Option[Schema.VolumeDataRestorePolicyBinding.PolicyEnum]] = (fun: Schema.VolumeDataRestorePolicyBinding.PolicyEnum) => Option(fun)
		given putSchemaVolumeDataRestorePolicyBindingVolumeTypeEnum: Conversion[Schema.VolumeDataRestorePolicyBinding.VolumeTypeEnum, Option[Schema.VolumeDataRestorePolicyBinding.VolumeTypeEnum]] = (fun: Schema.VolumeDataRestorePolicyBinding.VolumeTypeEnum) => Option(fun)
		given putListSchemaGroupKindDependency: Conversion[List[Schema.GroupKindDependency], Option[List[Schema.GroupKindDependency]]] = (fun: List[Schema.GroupKindDependency]) => Option(fun)
		given putSchemaGroupKind: Conversion[Schema.GroupKind, Option[Schema.GroupKind]] = (fun: Schema.GroupKind) => Option(fun)
		given putListSchemaRestorePlan: Conversion[List[Schema.RestorePlan], Option[List[Schema.RestorePlan]]] = (fun: List[Schema.RestorePlan]) => Option(fun)
		given putSchemaRestoreStateEnum: Conversion[Schema.Restore.StateEnum, Option[Schema.Restore.StateEnum]] = (fun: Schema.Restore.StateEnum) => Option(fun)
		given putSchemaFilter: Conversion[Schema.Filter, Option[Schema.Filter]] = (fun: Schema.Filter) => Option(fun)
		given putListSchemaVolumeDataRestorePolicyOverride: Conversion[List[Schema.VolumeDataRestorePolicyOverride], Option[List[Schema.VolumeDataRestorePolicyOverride]]] = (fun: List[Schema.VolumeDataRestorePolicyOverride]) => Option(fun)
		given putListSchemaResourceSelector: Conversion[List[Schema.ResourceSelector], Option[List[Schema.ResourceSelector]]] = (fun: List[Schema.ResourceSelector]) => Option(fun)
		given putSchemaVolumeDataRestorePolicyOverridePolicyEnum: Conversion[Schema.VolumeDataRestorePolicyOverride.PolicyEnum, Option[Schema.VolumeDataRestorePolicyOverride.PolicyEnum]] = (fun: Schema.VolumeDataRestorePolicyOverride.PolicyEnum) => Option(fun)
		given putListSchemaRestore: Conversion[List[Schema.Restore], Option[List[Schema.Restore]]] = (fun: List[Schema.Restore]) => Option(fun)
		given putListSchemaVolumeRestore: Conversion[List[Schema.VolumeRestore], Option[List[Schema.VolumeRestore]]] = (fun: List[Schema.VolumeRestore]) => Option(fun)
		given putSchemaVolumeRestoreVolumeTypeEnum: Conversion[Schema.VolumeRestore.VolumeTypeEnum, Option[Schema.VolumeRestore.VolumeTypeEnum]] = (fun: Schema.VolumeRestore.VolumeTypeEnum) => Option(fun)
		given putSchemaVolumeRestoreStateEnum: Conversion[Schema.VolumeRestore.StateEnum, Option[Schema.VolumeRestore.StateEnum]] = (fun: Schema.VolumeRestore.StateEnum) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
