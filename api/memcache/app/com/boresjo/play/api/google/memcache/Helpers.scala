package com.boresjo.play.api.google.memcache

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
		given putListSchemaInstance: Conversion[List[Schema.Instance], Option[List[Schema.Instance]]] = (fun: List[Schema.Instance]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaNodeConfig: Conversion[Schema.NodeConfig, Option[Schema.NodeConfig]] = (fun: Schema.NodeConfig) => Option(fun)
		given putSchemaInstanceMemcacheVersionEnum: Conversion[Schema.Instance.MemcacheVersionEnum, Option[Schema.Instance.MemcacheVersionEnum]] = (fun: Schema.Instance.MemcacheVersionEnum) => Option(fun)
		given putSchemaMemcacheParameters: Conversion[Schema.MemcacheParameters, Option[Schema.MemcacheParameters]] = (fun: Schema.MemcacheParameters) => Option(fun)
		given putListSchemaNode: Conversion[List[Schema.Node], Option[List[Schema.Node]]] = (fun: List[Schema.Node]) => Option(fun)
		given putSchemaInstanceStateEnum: Conversion[Schema.Instance.StateEnum, Option[Schema.Instance.StateEnum]] = (fun: Schema.Instance.StateEnum) => Option(fun)
		given putListSchemaInstanceMessage: Conversion[List[Schema.InstanceMessage], Option[List[Schema.InstanceMessage]]] = (fun: List[Schema.InstanceMessage]) => Option(fun)
		given putSchemaGoogleCloudMemcacheV1MaintenancePolicy: Conversion[Schema.GoogleCloudMemcacheV1MaintenancePolicy, Option[Schema.GoogleCloudMemcacheV1MaintenancePolicy]] = (fun: Schema.GoogleCloudMemcacheV1MaintenancePolicy) => Option(fun)
		given putSchemaMaintenanceSchedule: Conversion[Schema.MaintenanceSchedule, Option[Schema.MaintenanceSchedule]] = (fun: Schema.MaintenanceSchedule) => Option(fun)
		given putSchemaNodeStateEnum: Conversion[Schema.Node.StateEnum, Option[Schema.Node.StateEnum]] = (fun: Schema.Node.StateEnum) => Option(fun)
		given putSchemaNodeMemcacheVersionEnum: Conversion[Schema.Node.MemcacheVersionEnum, Option[Schema.Node.MemcacheVersionEnum]] = (fun: Schema.Node.MemcacheVersionEnum) => Option(fun)
		given putSchemaInstanceMessageCodeEnum: Conversion[Schema.InstanceMessage.CodeEnum, Option[Schema.InstanceMessage.CodeEnum]] = (fun: Schema.InstanceMessage.CodeEnum) => Option(fun)
		given putListSchemaWeeklyMaintenanceWindow: Conversion[List[Schema.WeeklyMaintenanceWindow], Option[List[Schema.WeeklyMaintenanceWindow]]] = (fun: List[Schema.WeeklyMaintenanceWindow]) => Option(fun)
		given putSchemaWeeklyMaintenanceWindowDayEnum: Conversion[Schema.WeeklyMaintenanceWindow.DayEnum, Option[Schema.WeeklyMaintenanceWindow.DayEnum]] = (fun: Schema.WeeklyMaintenanceWindow.DayEnum) => Option(fun)
		given putSchemaTimeOfDay: Conversion[Schema.TimeOfDay, Option[Schema.TimeOfDay]] = (fun: Schema.TimeOfDay) => Option(fun)
		given putSchemaRescheduleMaintenanceRequestRescheduleTypeEnum: Conversion[Schema.RescheduleMaintenanceRequest.RescheduleTypeEnum, Option[Schema.RescheduleMaintenanceRequest.RescheduleTypeEnum]] = (fun: Schema.RescheduleMaintenanceRequest.RescheduleTypeEnum) => Option(fun)
		given putSchemaGoogleCloudMemcacheV1UpgradeInstanceRequestMemcacheVersionEnum: Conversion[Schema.GoogleCloudMemcacheV1UpgradeInstanceRequest.MemcacheVersionEnum, Option[Schema.GoogleCloudMemcacheV1UpgradeInstanceRequest.MemcacheVersionEnum]] = (fun: Schema.GoogleCloudMemcacheV1UpgradeInstanceRequest.MemcacheVersionEnum) => Option(fun)
		given putMapStringSchemaGoogleCloudMemcacheV1ZoneMetadata: Conversion[Map[String, Schema.GoogleCloudMemcacheV1ZoneMetadata], Option[Map[String, Schema.GoogleCloudMemcacheV1ZoneMetadata]]] = (fun: Map[String, Schema.GoogleCloudMemcacheV1ZoneMetadata]) => Option(fun)
		given putMapStringSchemaZoneMetadata: Conversion[Map[String, Schema.ZoneMetadata], Option[Map[String, Schema.ZoneMetadata]]] = (fun: Map[String, Schema.ZoneMetadata]) => Option(fun)
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
		given putListSchemaSchedule: Conversion[List[Schema.Schedule], Option[List[Schema.Schedule]]] = (fun: List[Schema.Schedule]) => Option(fun)
		given putSchemaScheduleDayEnum: Conversion[Schema.Schedule.DayEnum, Option[Schema.Schedule.DayEnum]] = (fun: Schema.Schedule.DayEnum) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
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
		given putSchemaRequirementOverride: Conversion[Schema.RequirementOverride, Option[Schema.RequirementOverride]] = (fun: Schema.RequirementOverride) => Option(fun)
		given putSchemaRequirementOverrideZiOverrideEnum: Conversion[Schema.RequirementOverride.ZiOverrideEnum, Option[Schema.RequirementOverride.ZiOverrideEnum]] = (fun: Schema.RequirementOverride.ZiOverrideEnum) => Option(fun)
		given putSchemaRequirementOverrideZsOverrideEnum: Conversion[Schema.RequirementOverride.ZsOverrideEnum, Option[Schema.RequirementOverride.ZsOverrideEnum]] = (fun: Schema.RequirementOverride.ZsOverrideEnum) => Option(fun)
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