package com.boresjo.play.api.google.datafusion

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
		given putListSchemaVersion: Conversion[List[Schema.Version], Option[List[Schema.Version]]] = (fun: List[Schema.Version]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaVersionTypeEnum: Conversion[Schema.Version.TypeEnum, Option[Schema.Version.TypeEnum]] = (fun: Schema.Version.TypeEnum) => Option(fun)
		given putListSchemaInstance: Conversion[List[Schema.Instance], Option[List[Schema.Instance]]] = (fun: List[Schema.Instance]) => Option(fun)
		given putSchemaInstanceTypeEnum: Conversion[Schema.Instance.TypeEnum, Option[Schema.Instance.TypeEnum]] = (fun: Schema.Instance.TypeEnum) => Option(fun)
		given putSchemaNetworkConfig: Conversion[Schema.NetworkConfig, Option[Schema.NetworkConfig]] = (fun: Schema.NetworkConfig) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaInstanceStateEnum: Conversion[Schema.Instance.StateEnum, Option[Schema.Instance.StateEnum]] = (fun: Schema.Instance.StateEnum) => Option(fun)
		given putListSchemaAccelerator: Conversion[List[Schema.Accelerator], Option[List[Schema.Accelerator]]] = (fun: List[Schema.Accelerator]) => Option(fun)
		given putSchemaCryptoKeyConfig: Conversion[Schema.CryptoKeyConfig, Option[Schema.CryptoKeyConfig]] = (fun: Schema.CryptoKeyConfig) => Option(fun)
		given putListSchemaInstanceDisabledReasonEnum: Conversion[List[Schema.Instance.DisabledReasonEnum], Option[List[Schema.Instance.DisabledReasonEnum]]] = (fun: List[Schema.Instance.DisabledReasonEnum]) => Option(fun)
		given putSchemaEventPublishConfig: Conversion[Schema.EventPublishConfig, Option[Schema.EventPublishConfig]] = (fun: Schema.EventPublishConfig) => Option(fun)
		given putSchemaMaintenancePolicy: Conversion[Schema.MaintenancePolicy, Option[Schema.MaintenancePolicy]] = (fun: Schema.MaintenancePolicy) => Option(fun)
		given putSchemaNetworkConfigConnectionTypeEnum: Conversion[Schema.NetworkConfig.ConnectionTypeEnum, Option[Schema.NetworkConfig.ConnectionTypeEnum]] = (fun: Schema.NetworkConfig.ConnectionTypeEnum) => Option(fun)
		given putSchemaPrivateServiceConnectConfig: Conversion[Schema.PrivateServiceConnectConfig, Option[Schema.PrivateServiceConnectConfig]] = (fun: Schema.PrivateServiceConnectConfig) => Option(fun)
		given putSchemaAcceleratorAcceleratorTypeEnum: Conversion[Schema.Accelerator.AcceleratorTypeEnum, Option[Schema.Accelerator.AcceleratorTypeEnum]] = (fun: Schema.Accelerator.AcceleratorTypeEnum) => Option(fun)
		given putSchemaAcceleratorStateEnum: Conversion[Schema.Accelerator.StateEnum, Option[Schema.Accelerator.StateEnum]] = (fun: Schema.Accelerator.StateEnum) => Option(fun)
		given putSchemaMaintenanceWindow: Conversion[Schema.MaintenanceWindow, Option[Schema.MaintenanceWindow]] = (fun: Schema.MaintenanceWindow) => Option(fun)
		given putSchemaTimeWindow: Conversion[Schema.TimeWindow, Option[Schema.TimeWindow]] = (fun: Schema.TimeWindow) => Option(fun)
		given putSchemaRecurringTimeWindow: Conversion[Schema.RecurringTimeWindow, Option[Schema.RecurringTimeWindow]] = (fun: Schema.RecurringTimeWindow) => Option(fun)
		given putListSchemaDnsPeering: Conversion[List[Schema.DnsPeering], Option[List[Schema.DnsPeering]]] = (fun: List[Schema.DnsPeering]) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
		given putSchemaIsolationExpectations: Conversion[Schema.IsolationExpectations, Option[Schema.IsolationExpectations]] = (fun: Schema.IsolationExpectations) => Option(fun)
		given putListSchemaLocationData: Conversion[List[Schema.LocationData], Option[List[Schema.LocationData]]] = (fun: List[Schema.LocationData]) => Option(fun)
		given putListSchemaCloudAsset: Conversion[List[Schema.CloudAsset], Option[List[Schema.CloudAsset]]] = (fun: List[Schema.CloudAsset]) => Option(fun)
		given putListSchemaExtraParameter: Conversion[List[Schema.ExtraParameter], Option[List[Schema.ExtraParameter]]] = (fun: List[Schema.ExtraParameter]) => Option(fun)
		given putSchemaIsolationExpectationsZoneIsolationEnum: Conversion[Schema.IsolationExpectations.ZoneIsolationEnum, Option[Schema.IsolationExpectations.ZoneIsolationEnum]] = (fun: Schema.IsolationExpectations.ZoneIsolationEnum) => Option(fun)
		given putSchemaIsolationExpectationsZoneSeparationEnum: Conversion[Schema.IsolationExpectations.ZoneSeparationEnum, Option[Schema.IsolationExpectations.ZoneSeparationEnum]] = (fun: Schema.IsolationExpectations.ZoneSeparationEnum) => Option(fun)
		given putSchemaIsolationExpectationsZsOrgPolicyEnum: Conversion[Schema.IsolationExpectations.ZsOrgPolicyEnum, Option[Schema.IsolationExpectations.ZsOrgPolicyEnum]] = (fun: Schema.IsolationExpectations.ZsOrgPolicyEnum) => Option(fun)
		given putSchemaIsolationExpectationsZsRegionStateEnum: Conversion[Schema.IsolationExpectations.ZsRegionStateEnum, Option[Schema.IsolationExpectations.ZsRegionStateEnum]] = (fun: Schema.IsolationExpectations.ZsRegionStateEnum) => Option(fun)
		given putSchemaIsolationExpectationsZiOrgPolicyEnum: Conversion[Schema.IsolationExpectations.ZiOrgPolicyEnum, Option[Schema.IsolationExpectations.ZiOrgPolicyEnum]] = (fun: Schema.IsolationExpectations.ZiOrgPolicyEnum) => Option(fun)
		given putSchemaIsolationExpectationsZiRegionPolicyEnum: Conversion[Schema.IsolationExpectations.ZiRegionPolicyEnum, Option[Schema.IsolationExpectations.ZiRegionPolicyEnum]] = (fun: Schema.IsolationExpectations.ZiRegionPolicyEnum) => Option(fun)
		given putSchemaIsolationExpectationsZiRegionStateEnum: Conversion[Schema.IsolationExpectations.ZiRegionStateEnum, Option[Schema.IsolationExpectations.ZiRegionStateEnum]] = (fun: Schema.IsolationExpectations.ZiRegionStateEnum) => Option(fun)
		given putSchemaDirectLocationAssignment: Conversion[Schema.DirectLocationAssignment, Option[Schema.DirectLocationAssignment]] = (fun: Schema.DirectLocationAssignment) => Option(fun)
		given putSchemaSpannerLocation: Conversion[Schema.SpannerLocation, Option[Schema.SpannerLocation]] = (fun: Schema.SpannerLocation) => Option(fun)
		given putSchemaCloudAssetComposition: Conversion[Schema.CloudAssetComposition, Option[Schema.CloudAssetComposition]] = (fun: Schema.CloudAssetComposition) => Option(fun)
		given putSchemaTenantProjectProxy: Conversion[Schema.TenantProjectProxy, Option[Schema.TenantProjectProxy]] = (fun: Schema.TenantProjectProxy) => Option(fun)
		given putSchemaBlobstoreLocation: Conversion[Schema.BlobstoreLocation, Option[Schema.BlobstoreLocation]] = (fun: Schema.BlobstoreLocation) => Option(fun)
		given putSchemaPlacerLocation: Conversion[Schema.PlacerLocation, Option[Schema.PlacerLocation]] = (fun: Schema.PlacerLocation) => Option(fun)
		given putListSchemaLocationAssignment: Conversion[List[Schema.LocationAssignment], Option[List[Schema.LocationAssignment]]] = (fun: List[Schema.LocationAssignment]) => Option(fun)
		given putSchemaLocationAssignmentLocationTypeEnum: Conversion[Schema.LocationAssignment.LocationTypeEnum, Option[Schema.LocationAssignment.LocationTypeEnum]] = (fun: Schema.LocationAssignment.LocationTypeEnum) => Option(fun)
		given putSchemaRegionalMigDistributionPolicy: Conversion[Schema.RegionalMigDistributionPolicy, Option[Schema.RegionalMigDistributionPolicy]] = (fun: Schema.RegionalMigDistributionPolicy) => Option(fun)
		given putListSchemaZoneConfiguration: Conversion[List[Schema.ZoneConfiguration], Option[List[Schema.ZoneConfiguration]]] = (fun: List[Schema.ZoneConfiguration]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
