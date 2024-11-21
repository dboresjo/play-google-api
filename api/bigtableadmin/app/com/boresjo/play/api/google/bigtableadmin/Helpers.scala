package com.boresjo.play.api.google.bigtableadmin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

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
		given putSchemaInstance: Conversion[Schema.Instance, Option[Schema.Instance]] = (fun: Schema.Instance) => Option(fun)
		given putMapStringSchemaCluster: Conversion[Map[String, Schema.Cluster], Option[Map[String, Schema.Cluster]]] = (fun: Map[String, Schema.Cluster]) => Option(fun)
		given putSchemaInstanceStateEnum: Conversion[Schema.Instance.StateEnum, Option[Schema.Instance.StateEnum]] = (fun: Schema.Instance.StateEnum) => Option(fun)
		given putSchemaInstanceTypeEnum: Conversion[Schema.Instance.TypeEnum, Option[Schema.Instance.TypeEnum]] = (fun: Schema.Instance.TypeEnum) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaClusterStateEnum: Conversion[Schema.Cluster.StateEnum, Option[Schema.Cluster.StateEnum]] = (fun: Schema.Cluster.StateEnum) => Option(fun)
		given putSchemaClusterNodeScalingFactorEnum: Conversion[Schema.Cluster.NodeScalingFactorEnum, Option[Schema.Cluster.NodeScalingFactorEnum]] = (fun: Schema.Cluster.NodeScalingFactorEnum) => Option(fun)
		given putSchemaClusterConfig: Conversion[Schema.ClusterConfig, Option[Schema.ClusterConfig]] = (fun: Schema.ClusterConfig) => Option(fun)
		given putSchemaClusterDefaultStorageTypeEnum: Conversion[Schema.Cluster.DefaultStorageTypeEnum, Option[Schema.Cluster.DefaultStorageTypeEnum]] = (fun: Schema.Cluster.DefaultStorageTypeEnum) => Option(fun)
		given putSchemaEncryptionConfig: Conversion[Schema.EncryptionConfig, Option[Schema.EncryptionConfig]] = (fun: Schema.EncryptionConfig) => Option(fun)
		given putSchemaClusterAutoscalingConfig: Conversion[Schema.ClusterAutoscalingConfig, Option[Schema.ClusterAutoscalingConfig]] = (fun: Schema.ClusterAutoscalingConfig) => Option(fun)
		given putSchemaAutoscalingLimits: Conversion[Schema.AutoscalingLimits, Option[Schema.AutoscalingLimits]] = (fun: Schema.AutoscalingLimits) => Option(fun)
		given putSchemaAutoscalingTargets: Conversion[Schema.AutoscalingTargets, Option[Schema.AutoscalingTargets]] = (fun: Schema.AutoscalingTargets) => Option(fun)
		given putListSchemaInstance: Conversion[List[Schema.Instance], Option[List[Schema.Instance]]] = (fun: List[Schema.Instance]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaCluster: Conversion[List[Schema.Cluster], Option[List[Schema.Cluster]]] = (fun: List[Schema.Cluster]) => Option(fun)
		given putSchemaMultiClusterRoutingUseAny: Conversion[Schema.MultiClusterRoutingUseAny, Option[Schema.MultiClusterRoutingUseAny]] = (fun: Schema.MultiClusterRoutingUseAny) => Option(fun)
		given putSchemaSingleClusterRouting: Conversion[Schema.SingleClusterRouting, Option[Schema.SingleClusterRouting]] = (fun: Schema.SingleClusterRouting) => Option(fun)
		given putSchemaAppProfilePriorityEnum: Conversion[Schema.AppProfile.PriorityEnum, Option[Schema.AppProfile.PriorityEnum]] = (fun: Schema.AppProfile.PriorityEnum) => Option(fun)
		given putSchemaStandardIsolation: Conversion[Schema.StandardIsolation, Option[Schema.StandardIsolation]] = (fun: Schema.StandardIsolation) => Option(fun)
		given putSchemaDataBoostIsolationReadOnly: Conversion[Schema.DataBoostIsolationReadOnly, Option[Schema.DataBoostIsolationReadOnly]] = (fun: Schema.DataBoostIsolationReadOnly) => Option(fun)
		given putSchemaRowAffinity: Conversion[Schema.RowAffinity, Option[Schema.RowAffinity]] = (fun: Schema.RowAffinity) => Option(fun)
		given putSchemaStandardIsolationPriorityEnum: Conversion[Schema.StandardIsolation.PriorityEnum, Option[Schema.StandardIsolation.PriorityEnum]] = (fun: Schema.StandardIsolation.PriorityEnum) => Option(fun)
		given putSchemaDataBoostIsolationReadOnlyComputeBillingOwnerEnum: Conversion[Schema.DataBoostIsolationReadOnly.ComputeBillingOwnerEnum, Option[Schema.DataBoostIsolationReadOnly.ComputeBillingOwnerEnum]] = (fun: Schema.DataBoostIsolationReadOnly.ComputeBillingOwnerEnum) => Option(fun)
		given putListSchemaAppProfile: Conversion[List[Schema.AppProfile], Option[List[Schema.AppProfile]]] = (fun: List[Schema.AppProfile]) => Option(fun)
		given putSchemaGetPolicyOptions: Conversion[Schema.GetPolicyOptions, Option[Schema.GetPolicyOptions]] = (fun: Schema.GetPolicyOptions) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaHotTablet: Conversion[List[Schema.HotTablet], Option[List[Schema.HotTablet]]] = (fun: List[Schema.HotTablet]) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putSchemaTable: Conversion[Schema.Table, Option[Schema.Table]] = (fun: Schema.Table) => Option(fun)
		given putListSchemaSplit: Conversion[List[Schema.Split], Option[List[Schema.Split]]] = (fun: List[Schema.Split]) => Option(fun)
		given putMapStringSchemaClusterState: Conversion[Map[String, Schema.ClusterState], Option[Map[String, Schema.ClusterState]]] = (fun: Map[String, Schema.ClusterState]) => Option(fun)
		given putMapStringSchemaColumnFamily: Conversion[Map[String, Schema.ColumnFamily], Option[Map[String, Schema.ColumnFamily]]] = (fun: Map[String, Schema.ColumnFamily]) => Option(fun)
		given putSchemaTableGranularityEnum: Conversion[Schema.Table.GranularityEnum, Option[Schema.Table.GranularityEnum]] = (fun: Schema.Table.GranularityEnum) => Option(fun)
		given putSchemaRestoreInfo: Conversion[Schema.RestoreInfo, Option[Schema.RestoreInfo]] = (fun: Schema.RestoreInfo) => Option(fun)
		given putSchemaChangeStreamConfig: Conversion[Schema.ChangeStreamConfig, Option[Schema.ChangeStreamConfig]] = (fun: Schema.ChangeStreamConfig) => Option(fun)
		given putSchemaTableStats: Conversion[Schema.TableStats, Option[Schema.TableStats]] = (fun: Schema.TableStats) => Option(fun)
		given putSchemaAutomatedBackupPolicy: Conversion[Schema.AutomatedBackupPolicy, Option[Schema.AutomatedBackupPolicy]] = (fun: Schema.AutomatedBackupPolicy) => Option(fun)
		given putSchemaClusterStateReplicationStateEnum: Conversion[Schema.ClusterState.ReplicationStateEnum, Option[Schema.ClusterState.ReplicationStateEnum]] = (fun: Schema.ClusterState.ReplicationStateEnum) => Option(fun)
		given putListSchemaEncryptionInfo: Conversion[List[Schema.EncryptionInfo], Option[List[Schema.EncryptionInfo]]] = (fun: List[Schema.EncryptionInfo]) => Option(fun)
		given putSchemaEncryptionInfoEncryptionTypeEnum: Conversion[Schema.EncryptionInfo.EncryptionTypeEnum, Option[Schema.EncryptionInfo.EncryptionTypeEnum]] = (fun: Schema.EncryptionInfo.EncryptionTypeEnum) => Option(fun)
		given putSchemaGcRule: Conversion[Schema.GcRule, Option[Schema.GcRule]] = (fun: Schema.GcRule) => Option(fun)
		given putSchemaColumnFamilyStats: Conversion[Schema.ColumnFamilyStats, Option[Schema.ColumnFamilyStats]] = (fun: Schema.ColumnFamilyStats) => Option(fun)
		given putSchemaType: Conversion[Schema.Type, Option[Schema.Type]] = (fun: Schema.Type) => Option(fun)
		given putSchemaIntersection: Conversion[Schema.Intersection, Option[Schema.Intersection]] = (fun: Schema.Intersection) => Option(fun)
		given putSchemaUnion: Conversion[Schema.Union, Option[Schema.Union]] = (fun: Schema.Union) => Option(fun)
		given putListSchemaGcRule: Conversion[List[Schema.GcRule], Option[List[Schema.GcRule]]] = (fun: List[Schema.GcRule]) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeBytes: Conversion[Schema.GoogleBigtableAdminV2TypeBytes, Option[Schema.GoogleBigtableAdminV2TypeBytes]] = (fun: Schema.GoogleBigtableAdminV2TypeBytes) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeString: Conversion[Schema.GoogleBigtableAdminV2TypeString, Option[Schema.GoogleBigtableAdminV2TypeString]] = (fun: Schema.GoogleBigtableAdminV2TypeString) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeInt64: Conversion[Schema.GoogleBigtableAdminV2TypeInt64, Option[Schema.GoogleBigtableAdminV2TypeInt64]] = (fun: Schema.GoogleBigtableAdminV2TypeInt64) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeFloat32: Conversion[Schema.GoogleBigtableAdminV2TypeFloat32, Option[Schema.GoogleBigtableAdminV2TypeFloat32]] = (fun: Schema.GoogleBigtableAdminV2TypeFloat32) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeFloat64: Conversion[Schema.GoogleBigtableAdminV2TypeFloat64, Option[Schema.GoogleBigtableAdminV2TypeFloat64]] = (fun: Schema.GoogleBigtableAdminV2TypeFloat64) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeBool: Conversion[Schema.GoogleBigtableAdminV2TypeBool, Option[Schema.GoogleBigtableAdminV2TypeBool]] = (fun: Schema.GoogleBigtableAdminV2TypeBool) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeTimestamp: Conversion[Schema.GoogleBigtableAdminV2TypeTimestamp, Option[Schema.GoogleBigtableAdminV2TypeTimestamp]] = (fun: Schema.GoogleBigtableAdminV2TypeTimestamp) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeDate: Conversion[Schema.GoogleBigtableAdminV2TypeDate, Option[Schema.GoogleBigtableAdminV2TypeDate]] = (fun: Schema.GoogleBigtableAdminV2TypeDate) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeAggregate: Conversion[Schema.GoogleBigtableAdminV2TypeAggregate, Option[Schema.GoogleBigtableAdminV2TypeAggregate]] = (fun: Schema.GoogleBigtableAdminV2TypeAggregate) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeStruct: Conversion[Schema.GoogleBigtableAdminV2TypeStruct, Option[Schema.GoogleBigtableAdminV2TypeStruct]] = (fun: Schema.GoogleBigtableAdminV2TypeStruct) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeArray: Conversion[Schema.GoogleBigtableAdminV2TypeArray, Option[Schema.GoogleBigtableAdminV2TypeArray]] = (fun: Schema.GoogleBigtableAdminV2TypeArray) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeMap: Conversion[Schema.GoogleBigtableAdminV2TypeMap, Option[Schema.GoogleBigtableAdminV2TypeMap]] = (fun: Schema.GoogleBigtableAdminV2TypeMap) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeBytesEncoding: Conversion[Schema.GoogleBigtableAdminV2TypeBytesEncoding, Option[Schema.GoogleBigtableAdminV2TypeBytesEncoding]] = (fun: Schema.GoogleBigtableAdminV2TypeBytesEncoding) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeBytesEncodingRaw: Conversion[Schema.GoogleBigtableAdminV2TypeBytesEncodingRaw, Option[Schema.GoogleBigtableAdminV2TypeBytesEncodingRaw]] = (fun: Schema.GoogleBigtableAdminV2TypeBytesEncodingRaw) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeStringEncoding: Conversion[Schema.GoogleBigtableAdminV2TypeStringEncoding, Option[Schema.GoogleBigtableAdminV2TypeStringEncoding]] = (fun: Schema.GoogleBigtableAdminV2TypeStringEncoding) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeStringEncodingUtf8Raw: Conversion[Schema.GoogleBigtableAdminV2TypeStringEncodingUtf8Raw, Option[Schema.GoogleBigtableAdminV2TypeStringEncodingUtf8Raw]] = (fun: Schema.GoogleBigtableAdminV2TypeStringEncodingUtf8Raw) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeStringEncodingUtf8Bytes: Conversion[Schema.GoogleBigtableAdminV2TypeStringEncodingUtf8Bytes, Option[Schema.GoogleBigtableAdminV2TypeStringEncodingUtf8Bytes]] = (fun: Schema.GoogleBigtableAdminV2TypeStringEncodingUtf8Bytes) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeInt64Encoding: Conversion[Schema.GoogleBigtableAdminV2TypeInt64Encoding, Option[Schema.GoogleBigtableAdminV2TypeInt64Encoding]] = (fun: Schema.GoogleBigtableAdminV2TypeInt64Encoding) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeInt64EncodingBigEndianBytes: Conversion[Schema.GoogleBigtableAdminV2TypeInt64EncodingBigEndianBytes, Option[Schema.GoogleBigtableAdminV2TypeInt64EncodingBigEndianBytes]] = (fun: Schema.GoogleBigtableAdminV2TypeInt64EncodingBigEndianBytes) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeAggregateSum: Conversion[Schema.GoogleBigtableAdminV2TypeAggregateSum, Option[Schema.GoogleBigtableAdminV2TypeAggregateSum]] = (fun: Schema.GoogleBigtableAdminV2TypeAggregateSum) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeAggregateHyperLogLogPlusPlusUniqueCount: Conversion[Schema.GoogleBigtableAdminV2TypeAggregateHyperLogLogPlusPlusUniqueCount, Option[Schema.GoogleBigtableAdminV2TypeAggregateHyperLogLogPlusPlusUniqueCount]] = (fun: Schema.GoogleBigtableAdminV2TypeAggregateHyperLogLogPlusPlusUniqueCount) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeAggregateMax: Conversion[Schema.GoogleBigtableAdminV2TypeAggregateMax, Option[Schema.GoogleBigtableAdminV2TypeAggregateMax]] = (fun: Schema.GoogleBigtableAdminV2TypeAggregateMax) => Option(fun)
		given putSchemaGoogleBigtableAdminV2TypeAggregateMin: Conversion[Schema.GoogleBigtableAdminV2TypeAggregateMin, Option[Schema.GoogleBigtableAdminV2TypeAggregateMin]] = (fun: Schema.GoogleBigtableAdminV2TypeAggregateMin) => Option(fun)
		given putListSchemaGoogleBigtableAdminV2TypeStructField: Conversion[List[Schema.GoogleBigtableAdminV2TypeStructField], Option[List[Schema.GoogleBigtableAdminV2TypeStructField]]] = (fun: List[Schema.GoogleBigtableAdminV2TypeStructField]) => Option(fun)
		given putSchemaRestoreInfoSourceTypeEnum: Conversion[Schema.RestoreInfo.SourceTypeEnum, Option[Schema.RestoreInfo.SourceTypeEnum]] = (fun: Schema.RestoreInfo.SourceTypeEnum) => Option(fun)
		given putSchemaBackupInfo: Conversion[Schema.BackupInfo, Option[Schema.BackupInfo]] = (fun: Schema.BackupInfo) => Option(fun)
		given putListSchemaTable: Conversion[List[Schema.Table], Option[List[Schema.Table]]] = (fun: List[Schema.Table]) => Option(fun)
		given putSchemaGoogleBigtableAdminV2AuthorizedViewSubsetView: Conversion[Schema.GoogleBigtableAdminV2AuthorizedViewSubsetView, Option[Schema.GoogleBigtableAdminV2AuthorizedViewSubsetView]] = (fun: Schema.GoogleBigtableAdminV2AuthorizedViewSubsetView) => Option(fun)
		given putMapStringSchemaGoogleBigtableAdminV2AuthorizedViewFamilySubsets: Conversion[Map[String, Schema.GoogleBigtableAdminV2AuthorizedViewFamilySubsets], Option[Map[String, Schema.GoogleBigtableAdminV2AuthorizedViewFamilySubsets]]] = (fun: Map[String, Schema.GoogleBigtableAdminV2AuthorizedViewFamilySubsets]) => Option(fun)
		given putListSchemaAuthorizedView: Conversion[List[Schema.AuthorizedView], Option[List[Schema.AuthorizedView]]] = (fun: List[Schema.AuthorizedView]) => Option(fun)
		given putListSchemaModification: Conversion[List[Schema.Modification], Option[List[Schema.Modification]]] = (fun: List[Schema.Modification]) => Option(fun)
		given putSchemaColumnFamily: Conversion[Schema.ColumnFamily, Option[Schema.ColumnFamily]] = (fun: Schema.ColumnFamily) => Option(fun)
		given putSchemaStandardReadRemoteWrites: Conversion[Schema.StandardReadRemoteWrites, Option[Schema.StandardReadRemoteWrites]] = (fun: Schema.StandardReadRemoteWrites) => Option(fun)
		given putSchemaDataBoostReadLocalWrites: Conversion[Schema.DataBoostReadLocalWrites, Option[Schema.DataBoostReadLocalWrites]] = (fun: Schema.DataBoostReadLocalWrites) => Option(fun)
		given putSchemaBackupStateEnum: Conversion[Schema.Backup.StateEnum, Option[Schema.Backup.StateEnum]] = (fun: Schema.Backup.StateEnum) => Option(fun)
		given putSchemaEncryptionInfo: Conversion[Schema.EncryptionInfo, Option[Schema.EncryptionInfo]] = (fun: Schema.EncryptionInfo) => Option(fun)
		given putSchemaBackupBackupTypeEnum: Conversion[Schema.Backup.BackupTypeEnum, Option[Schema.Backup.BackupTypeEnum]] = (fun: Schema.Backup.BackupTypeEnum) => Option(fun)
		given putListSchemaBackup: Conversion[List[Schema.Backup], Option[List[Schema.Backup]]] = (fun: List[Schema.Backup]) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putSchemaCreateInstanceRequest: Conversion[Schema.CreateInstanceRequest, Option[Schema.CreateInstanceRequest]] = (fun: Schema.CreateInstanceRequest) => Option(fun)
		given putSchemaPartialUpdateInstanceRequest: Conversion[Schema.PartialUpdateInstanceRequest, Option[Schema.PartialUpdateInstanceRequest]] = (fun: Schema.PartialUpdateInstanceRequest) => Option(fun)
		given putSchemaCreateClusterRequest: Conversion[Schema.CreateClusterRequest, Option[Schema.CreateClusterRequest]] = (fun: Schema.CreateClusterRequest) => Option(fun)
		given putMapStringSchemaTableProgress: Conversion[Map[String, Schema.TableProgress], Option[Map[String, Schema.TableProgress]]] = (fun: Map[String, Schema.TableProgress]) => Option(fun)
		given putSchemaCluster: Conversion[Schema.Cluster, Option[Schema.Cluster]] = (fun: Schema.Cluster) => Option(fun)
		given putSchemaTableProgressStateEnum: Conversion[Schema.TableProgress.StateEnum, Option[Schema.TableProgress.StateEnum]] = (fun: Schema.TableProgress.StateEnum) => Option(fun)
		given putSchemaPartialUpdateClusterRequest: Conversion[Schema.PartialUpdateClusterRequest, Option[Schema.PartialUpdateClusterRequest]] = (fun: Schema.PartialUpdateClusterRequest) => Option(fun)
		given putSchemaCreateAuthorizedViewRequest: Conversion[Schema.CreateAuthorizedViewRequest, Option[Schema.CreateAuthorizedViewRequest]] = (fun: Schema.CreateAuthorizedViewRequest) => Option(fun)
		given putSchemaAuthorizedView: Conversion[Schema.AuthorizedView, Option[Schema.AuthorizedView]] = (fun: Schema.AuthorizedView) => Option(fun)
		given putSchemaOperationProgress: Conversion[Schema.OperationProgress, Option[Schema.OperationProgress]] = (fun: Schema.OperationProgress) => Option(fun)
		given putSchemaRestoreTableMetadataSourceTypeEnum: Conversion[Schema.RestoreTableMetadata.SourceTypeEnum, Option[Schema.RestoreTableMetadata.SourceTypeEnum]] = (fun: Schema.RestoreTableMetadata.SourceTypeEnum) => Option(fun)
		given putSchemaUpdateAuthorizedViewRequest: Conversion[Schema.UpdateAuthorizedViewRequest, Option[Schema.UpdateAuthorizedViewRequest]] = (fun: Schema.UpdateAuthorizedViewRequest) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}