package com.boresjo.play.api.google.managedidentities

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

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
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaDomainStateEnum: Conversion[Schema.Domain.StateEnum, Option[Schema.Domain.StateEnum]] = (fun: Schema.Domain.StateEnum) => Option(fun)
		given putListSchemaTrust: Conversion[List[Schema.Trust], Option[List[Schema.Trust]]] = (fun: List[Schema.Trust]) => Option(fun)
		given putSchemaTrustTrustTypeEnum: Conversion[Schema.Trust.TrustTypeEnum, Option[Schema.Trust.TrustTypeEnum]] = (fun: Schema.Trust.TrustTypeEnum) => Option(fun)
		given putSchemaTrustTrustDirectionEnum: Conversion[Schema.Trust.TrustDirectionEnum, Option[Schema.Trust.TrustDirectionEnum]] = (fun: Schema.Trust.TrustDirectionEnum) => Option(fun)
		given putSchemaTrustStateEnum: Conversion[Schema.Trust.StateEnum, Option[Schema.Trust.StateEnum]] = (fun: Schema.Trust.StateEnum) => Option(fun)
		given putListSchemaDomain: Conversion[List[Schema.Domain], Option[List[Schema.Domain]]] = (fun: List[Schema.Domain]) => Option(fun)
		given putSchemaTrust: Conversion[Schema.Trust, Option[Schema.Trust]] = (fun: Schema.Trust) => Option(fun)
		given putSchemaCertificate: Conversion[Schema.Certificate, Option[Schema.Certificate]] = (fun: Schema.Certificate) => Option(fun)
		given putSchemaLDAPSSettingsStateEnum: Conversion[Schema.LDAPSSettings.StateEnum, Option[Schema.LDAPSSettings.StateEnum]] = (fun: Schema.LDAPSSettings.StateEnum) => Option(fun)
		given putSchemaPeeringStateEnum: Conversion[Schema.Peering.StateEnum, Option[Schema.Peering.StateEnum]] = (fun: Schema.Peering.StateEnum) => Option(fun)
		given putListSchemaPeering: Conversion[List[Schema.Peering], Option[List[Schema.Peering]]] = (fun: List[Schema.Peering]) => Option(fun)
		given putSchemaBackupTypeEnum: Conversion[Schema.Backup.TypeEnum, Option[Schema.Backup.TypeEnum]] = (fun: Schema.Backup.TypeEnum) => Option(fun)
		given putSchemaBackupStateEnum: Conversion[Schema.Backup.StateEnum, Option[Schema.Backup.StateEnum]] = (fun: Schema.Backup.StateEnum) => Option(fun)
		given putListSchemaBackup: Conversion[List[Schema.Backup], Option[List[Schema.Backup]]] = (fun: List[Schema.Backup]) => Option(fun)
		given putListSchemaSqlIntegration: Conversion[List[Schema.SqlIntegration], Option[List[Schema.SqlIntegration]]] = (fun: List[Schema.SqlIntegration]) => Option(fun)
		given putSchemaSqlIntegrationStateEnum: Conversion[Schema.SqlIntegration.StateEnum, Option[Schema.SqlIntegration.StateEnum]] = (fun: Schema.SqlIntegration.StateEnum) => Option(fun)
		given putListSchemaOnPremDomainDetails: Conversion[List[Schema.OnPremDomainDetails], Option[List[Schema.OnPremDomainDetails]]] = (fun: List[Schema.OnPremDomainDetails]) => Option(fun)
		given putSchemaCheckMigrationPermissionResponseStateEnum: Conversion[Schema.CheckMigrationPermissionResponse.StateEnum, Option[Schema.CheckMigrationPermissionResponse.StateEnum]] = (fun: Schema.CheckMigrationPermissionResponse.StateEnum) => Option(fun)
		given putListSchemaOnPremDomainSIDDetails: Conversion[List[Schema.OnPremDomainSIDDetails], Option[List[Schema.OnPremDomainSIDDetails]]] = (fun: List[Schema.OnPremDomainSIDDetails]) => Option(fun)
		given putSchemaOnPremDomainSIDDetailsSidFilteringStateEnum: Conversion[Schema.OnPremDomainSIDDetails.SidFilteringStateEnum, Option[Schema.OnPremDomainSIDDetails.SidFilteringStateEnum]] = (fun: Schema.OnPremDomainSIDDetails.SidFilteringStateEnum) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
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
