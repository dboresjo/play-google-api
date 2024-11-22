package com.boresjo.play.api.google.oracledatabase

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
		given putListSchemaCloudExadataInfrastructure: Conversion[List[Schema.CloudExadataInfrastructure], Option[List[Schema.CloudExadataInfrastructure]]] = (fun: List[Schema.CloudExadataInfrastructure]) => Option(fun)
		given putSchemaCloudExadataInfrastructureProperties: Conversion[Schema.CloudExadataInfrastructureProperties, Option[Schema.CloudExadataInfrastructureProperties]] = (fun: Schema.CloudExadataInfrastructureProperties) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaMaintenanceWindow: Conversion[Schema.MaintenanceWindow, Option[Schema.MaintenanceWindow]] = (fun: Schema.MaintenanceWindow) => Option(fun)
		given putSchemaCloudExadataInfrastructurePropertiesStateEnum: Conversion[Schema.CloudExadataInfrastructureProperties.StateEnum, Option[Schema.CloudExadataInfrastructureProperties.StateEnum]] = (fun: Schema.CloudExadataInfrastructureProperties.StateEnum) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListSchemaCustomerContact: Conversion[List[Schema.CustomerContact], Option[List[Schema.CustomerContact]]] = (fun: List[Schema.CustomerContact]) => Option(fun)
		given putSchemaMaintenanceWindowPreferenceEnum: Conversion[Schema.MaintenanceWindow.PreferenceEnum, Option[Schema.MaintenanceWindow.PreferenceEnum]] = (fun: Schema.MaintenanceWindow.PreferenceEnum) => Option(fun)
		given putListSchemaMaintenanceWindowMonthsEnum: Conversion[List[Schema.MaintenanceWindow.MonthsEnum], Option[List[Schema.MaintenanceWindow.MonthsEnum]]] = (fun: List[Schema.MaintenanceWindow.MonthsEnum]) => Option(fun)
		given putListInt: Conversion[List[Int], Option[List[Int]]] = (fun: List[Int]) => Option(fun)
		given putListSchemaMaintenanceWindowDaysOfWeekEnum: Conversion[List[Schema.MaintenanceWindow.DaysOfWeekEnum], Option[List[Schema.MaintenanceWindow.DaysOfWeekEnum]]] = (fun: List[Schema.MaintenanceWindow.DaysOfWeekEnum]) => Option(fun)
		given putSchemaMaintenanceWindowPatchingModeEnum: Conversion[Schema.MaintenanceWindow.PatchingModeEnum, Option[Schema.MaintenanceWindow.PatchingModeEnum]] = (fun: Schema.MaintenanceWindow.PatchingModeEnum) => Option(fun)
		given putListSchemaCloudVmCluster: Conversion[List[Schema.CloudVmCluster], Option[List[Schema.CloudVmCluster]]] = (fun: List[Schema.CloudVmCluster]) => Option(fun)
		given putSchemaCloudVmClusterProperties: Conversion[Schema.CloudVmClusterProperties, Option[Schema.CloudVmClusterProperties]] = (fun: Schema.CloudVmClusterProperties) => Option(fun)
		given putSchemaCloudVmClusterPropertiesLicenseTypeEnum: Conversion[Schema.CloudVmClusterProperties.LicenseTypeEnum, Option[Schema.CloudVmClusterProperties.LicenseTypeEnum]] = (fun: Schema.CloudVmClusterProperties.LicenseTypeEnum) => Option(fun)
		given putSchemaTimeZone: Conversion[Schema.TimeZone, Option[Schema.TimeZone]] = (fun: Schema.TimeZone) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaCloudVmClusterPropertiesDiskRedundancyEnum: Conversion[Schema.CloudVmClusterProperties.DiskRedundancyEnum, Option[Schema.CloudVmClusterProperties.DiskRedundancyEnum]] = (fun: Schema.CloudVmClusterProperties.DiskRedundancyEnum) => Option(fun)
		given putSchemaDataCollectionOptions: Conversion[Schema.DataCollectionOptions, Option[Schema.DataCollectionOptions]] = (fun: Schema.DataCollectionOptions) => Option(fun)
		given putSchemaCloudVmClusterPropertiesStateEnum: Conversion[Schema.CloudVmClusterProperties.StateEnum, Option[Schema.CloudVmClusterProperties.StateEnum]] = (fun: Schema.CloudVmClusterProperties.StateEnum) => Option(fun)
		given putListSchemaEntitlement: Conversion[List[Schema.Entitlement], Option[List[Schema.Entitlement]]] = (fun: List[Schema.Entitlement]) => Option(fun)
		given putSchemaCloudAccountDetails: Conversion[Schema.CloudAccountDetails, Option[Schema.CloudAccountDetails]] = (fun: Schema.CloudAccountDetails) => Option(fun)
		given putSchemaEntitlementStateEnum: Conversion[Schema.Entitlement.StateEnum, Option[Schema.Entitlement.StateEnum]] = (fun: Schema.Entitlement.StateEnum) => Option(fun)
		given putListSchemaDbServer: Conversion[List[Schema.DbServer], Option[List[Schema.DbServer]]] = (fun: List[Schema.DbServer]) => Option(fun)
		given putSchemaDbServerProperties: Conversion[Schema.DbServerProperties, Option[Schema.DbServerProperties]] = (fun: Schema.DbServerProperties) => Option(fun)
		given putSchemaDbServerPropertiesStateEnum: Conversion[Schema.DbServerProperties.StateEnum, Option[Schema.DbServerProperties.StateEnum]] = (fun: Schema.DbServerProperties.StateEnum) => Option(fun)
		given putListSchemaDbNode: Conversion[List[Schema.DbNode], Option[List[Schema.DbNode]]] = (fun: List[Schema.DbNode]) => Option(fun)
		given putSchemaDbNodeProperties: Conversion[Schema.DbNodeProperties, Option[Schema.DbNodeProperties]] = (fun: Schema.DbNodeProperties) => Option(fun)
		given putSchemaDbNodePropertiesStateEnum: Conversion[Schema.DbNodeProperties.StateEnum, Option[Schema.DbNodeProperties.StateEnum]] = (fun: Schema.DbNodeProperties.StateEnum) => Option(fun)
		given putListSchemaGiVersion: Conversion[List[Schema.GiVersion], Option[List[Schema.GiVersion]]] = (fun: List[Schema.GiVersion]) => Option(fun)
		given putListSchemaDbSystemShape: Conversion[List[Schema.DbSystemShape], Option[List[Schema.DbSystemShape]]] = (fun: List[Schema.DbSystemShape]) => Option(fun)
		given putListSchemaAutonomousDatabase: Conversion[List[Schema.AutonomousDatabase], Option[List[Schema.AutonomousDatabase]]] = (fun: List[Schema.AutonomousDatabase]) => Option(fun)
		given putSchemaAutonomousDatabaseProperties: Conversion[Schema.AutonomousDatabaseProperties, Option[Schema.AutonomousDatabaseProperties]] = (fun: Schema.AutonomousDatabaseProperties) => Option(fun)
		given putSchemaAutonomousDatabasePropertiesDbWorkloadEnum: Conversion[Schema.AutonomousDatabaseProperties.DbWorkloadEnum, Option[Schema.AutonomousDatabaseProperties.DbWorkloadEnum]] = (fun: Schema.AutonomousDatabaseProperties.DbWorkloadEnum) => Option(fun)
		given putSchemaAutonomousDatabasePropertiesDbEditionEnum: Conversion[Schema.AutonomousDatabaseProperties.DbEditionEnum, Option[Schema.AutonomousDatabaseProperties.DbEditionEnum]] = (fun: Schema.AutonomousDatabaseProperties.DbEditionEnum) => Option(fun)
		given putSchemaAutonomousDatabasePropertiesLicenseTypeEnum: Conversion[Schema.AutonomousDatabaseProperties.LicenseTypeEnum, Option[Schema.AutonomousDatabaseProperties.LicenseTypeEnum]] = (fun: Schema.AutonomousDatabaseProperties.LicenseTypeEnum) => Option(fun)
		given putSchemaAutonomousDatabasePropertiesMaintenanceScheduleTypeEnum: Conversion[Schema.AutonomousDatabaseProperties.MaintenanceScheduleTypeEnum, Option[Schema.AutonomousDatabaseProperties.MaintenanceScheduleTypeEnum]] = (fun: Schema.AutonomousDatabaseProperties.MaintenanceScheduleTypeEnum) => Option(fun)
		given putSchemaAutonomousDatabaseApex: Conversion[Schema.AutonomousDatabaseApex, Option[Schema.AutonomousDatabaseApex]] = (fun: Schema.AutonomousDatabaseApex) => Option(fun)
		given putSchemaAutonomousDatabasePropertiesStateEnum: Conversion[Schema.AutonomousDatabaseProperties.StateEnum, Option[Schema.AutonomousDatabaseProperties.StateEnum]] = (fun: Schema.AutonomousDatabaseProperties.StateEnum) => Option(fun)
		given putSchemaAutonomousDatabaseConnectionStrings: Conversion[Schema.AutonomousDatabaseConnectionStrings, Option[Schema.AutonomousDatabaseConnectionStrings]] = (fun: Schema.AutonomousDatabaseConnectionStrings) => Option(fun)
		given putSchemaAutonomousDatabaseConnectionUrls: Conversion[Schema.AutonomousDatabaseConnectionUrls, Option[Schema.AutonomousDatabaseConnectionUrls]] = (fun: Schema.AutonomousDatabaseConnectionUrls) => Option(fun)
		given putSchemaAutonomousDatabaseStandbySummary: Conversion[Schema.AutonomousDatabaseStandbySummary, Option[Schema.AutonomousDatabaseStandbySummary]] = (fun: Schema.AutonomousDatabaseStandbySummary) => Option(fun)
		given putSchemaAutonomousDatabasePropertiesLocalDisasterRecoveryTypeEnum: Conversion[Schema.AutonomousDatabaseProperties.LocalDisasterRecoveryTypeEnum, Option[Schema.AutonomousDatabaseProperties.LocalDisasterRecoveryTypeEnum]] = (fun: Schema.AutonomousDatabaseProperties.LocalDisasterRecoveryTypeEnum) => Option(fun)
		given putSchemaAutonomousDatabasePropertiesDataSafeStateEnum: Conversion[Schema.AutonomousDatabaseProperties.DataSafeStateEnum, Option[Schema.AutonomousDatabaseProperties.DataSafeStateEnum]] = (fun: Schema.AutonomousDatabaseProperties.DataSafeStateEnum) => Option(fun)
		given putSchemaAutonomousDatabasePropertiesDatabaseManagementStateEnum: Conversion[Schema.AutonomousDatabaseProperties.DatabaseManagementStateEnum, Option[Schema.AutonomousDatabaseProperties.DatabaseManagementStateEnum]] = (fun: Schema.AutonomousDatabaseProperties.DatabaseManagementStateEnum) => Option(fun)
		given putSchemaAutonomousDatabasePropertiesOpenModeEnum: Conversion[Schema.AutonomousDatabaseProperties.OpenModeEnum, Option[Schema.AutonomousDatabaseProperties.OpenModeEnum]] = (fun: Schema.AutonomousDatabaseProperties.OpenModeEnum) => Option(fun)
		given putSchemaAutonomousDatabasePropertiesOperationsInsightsStateEnum: Conversion[Schema.AutonomousDatabaseProperties.OperationsInsightsStateEnum, Option[Schema.AutonomousDatabaseProperties.OperationsInsightsStateEnum]] = (fun: Schema.AutonomousDatabaseProperties.OperationsInsightsStateEnum) => Option(fun)
		given putSchemaAutonomousDatabasePropertiesPermissionLevelEnum: Conversion[Schema.AutonomousDatabaseProperties.PermissionLevelEnum, Option[Schema.AutonomousDatabaseProperties.PermissionLevelEnum]] = (fun: Schema.AutonomousDatabaseProperties.PermissionLevelEnum) => Option(fun)
		given putSchemaAutonomousDatabasePropertiesRefreshableModeEnum: Conversion[Schema.AutonomousDatabaseProperties.RefreshableModeEnum, Option[Schema.AutonomousDatabaseProperties.RefreshableModeEnum]] = (fun: Schema.AutonomousDatabaseProperties.RefreshableModeEnum) => Option(fun)
		given putSchemaAutonomousDatabasePropertiesRefreshableStateEnum: Conversion[Schema.AutonomousDatabaseProperties.RefreshableStateEnum, Option[Schema.AutonomousDatabaseProperties.RefreshableStateEnum]] = (fun: Schema.AutonomousDatabaseProperties.RefreshableStateEnum) => Option(fun)
		given putSchemaAutonomousDatabasePropertiesRoleEnum: Conversion[Schema.AutonomousDatabaseProperties.RoleEnum, Option[Schema.AutonomousDatabaseProperties.RoleEnum]] = (fun: Schema.AutonomousDatabaseProperties.RoleEnum) => Option(fun)
		given putListSchemaScheduledOperationDetails: Conversion[List[Schema.ScheduledOperationDetails], Option[List[Schema.ScheduledOperationDetails]]] = (fun: List[Schema.ScheduledOperationDetails]) => Option(fun)
		given putSchemaAllConnectionStrings: Conversion[Schema.AllConnectionStrings, Option[Schema.AllConnectionStrings]] = (fun: Schema.AllConnectionStrings) => Option(fun)
		given putListSchemaDatabaseConnectionStringProfile: Conversion[List[Schema.DatabaseConnectionStringProfile], Option[List[Schema.DatabaseConnectionStringProfile]]] = (fun: List[Schema.DatabaseConnectionStringProfile]) => Option(fun)
		given putSchemaDatabaseConnectionStringProfileConsumerGroupEnum: Conversion[Schema.DatabaseConnectionStringProfile.ConsumerGroupEnum, Option[Schema.DatabaseConnectionStringProfile.ConsumerGroupEnum]] = (fun: Schema.DatabaseConnectionStringProfile.ConsumerGroupEnum) => Option(fun)
		given putSchemaDatabaseConnectionStringProfileHostFormatEnum: Conversion[Schema.DatabaseConnectionStringProfile.HostFormatEnum, Option[Schema.DatabaseConnectionStringProfile.HostFormatEnum]] = (fun: Schema.DatabaseConnectionStringProfile.HostFormatEnum) => Option(fun)
		given putSchemaDatabaseConnectionStringProfileProtocolEnum: Conversion[Schema.DatabaseConnectionStringProfile.ProtocolEnum, Option[Schema.DatabaseConnectionStringProfile.ProtocolEnum]] = (fun: Schema.DatabaseConnectionStringProfile.ProtocolEnum) => Option(fun)
		given putSchemaDatabaseConnectionStringProfileSessionModeEnum: Conversion[Schema.DatabaseConnectionStringProfile.SessionModeEnum, Option[Schema.DatabaseConnectionStringProfile.SessionModeEnum]] = (fun: Schema.DatabaseConnectionStringProfile.SessionModeEnum) => Option(fun)
		given putSchemaDatabaseConnectionStringProfileSyntaxFormatEnum: Conversion[Schema.DatabaseConnectionStringProfile.SyntaxFormatEnum, Option[Schema.DatabaseConnectionStringProfile.SyntaxFormatEnum]] = (fun: Schema.DatabaseConnectionStringProfile.SyntaxFormatEnum) => Option(fun)
		given putSchemaDatabaseConnectionStringProfileTlsAuthenticationEnum: Conversion[Schema.DatabaseConnectionStringProfile.TlsAuthenticationEnum, Option[Schema.DatabaseConnectionStringProfile.TlsAuthenticationEnum]] = (fun: Schema.DatabaseConnectionStringProfile.TlsAuthenticationEnum) => Option(fun)
		given putSchemaAutonomousDatabaseStandbySummaryStateEnum: Conversion[Schema.AutonomousDatabaseStandbySummary.StateEnum, Option[Schema.AutonomousDatabaseStandbySummary.StateEnum]] = (fun: Schema.AutonomousDatabaseStandbySummary.StateEnum) => Option(fun)
		given putSchemaScheduledOperationDetailsDayOfWeekEnum: Conversion[Schema.ScheduledOperationDetails.DayOfWeekEnum, Option[Schema.ScheduledOperationDetails.DayOfWeekEnum]] = (fun: Schema.ScheduledOperationDetails.DayOfWeekEnum) => Option(fun)
		given putSchemaTimeOfDay: Conversion[Schema.TimeOfDay, Option[Schema.TimeOfDay]] = (fun: Schema.TimeOfDay) => Option(fun)
		given putSchemaGenerateAutonomousDatabaseWalletRequestTypeEnum: Conversion[Schema.GenerateAutonomousDatabaseWalletRequest.TypeEnum, Option[Schema.GenerateAutonomousDatabaseWalletRequest.TypeEnum]] = (fun: Schema.GenerateAutonomousDatabaseWalletRequest.TypeEnum) => Option(fun)
		given putListSchemaAutonomousDbVersion: Conversion[List[Schema.AutonomousDbVersion], Option[List[Schema.AutonomousDbVersion]]] = (fun: List[Schema.AutonomousDbVersion]) => Option(fun)
		given putSchemaAutonomousDbVersionDbWorkloadEnum: Conversion[Schema.AutonomousDbVersion.DbWorkloadEnum, Option[Schema.AutonomousDbVersion.DbWorkloadEnum]] = (fun: Schema.AutonomousDbVersion.DbWorkloadEnum) => Option(fun)
		given putListSchemaAutonomousDatabaseCharacterSet: Conversion[List[Schema.AutonomousDatabaseCharacterSet], Option[List[Schema.AutonomousDatabaseCharacterSet]]] = (fun: List[Schema.AutonomousDatabaseCharacterSet]) => Option(fun)
		given putSchemaAutonomousDatabaseCharacterSetCharacterSetTypeEnum: Conversion[Schema.AutonomousDatabaseCharacterSet.CharacterSetTypeEnum, Option[Schema.AutonomousDatabaseCharacterSet.CharacterSetTypeEnum]] = (fun: Schema.AutonomousDatabaseCharacterSet.CharacterSetTypeEnum) => Option(fun)
		given putListSchemaAutonomousDatabaseBackup: Conversion[List[Schema.AutonomousDatabaseBackup], Option[List[Schema.AutonomousDatabaseBackup]]] = (fun: List[Schema.AutonomousDatabaseBackup]) => Option(fun)
		given putSchemaAutonomousDatabaseBackupProperties: Conversion[Schema.AutonomousDatabaseBackupProperties, Option[Schema.AutonomousDatabaseBackupProperties]] = (fun: Schema.AutonomousDatabaseBackupProperties) => Option(fun)
		given putSchemaAutonomousDatabaseBackupPropertiesLifecycleStateEnum: Conversion[Schema.AutonomousDatabaseBackupProperties.LifecycleStateEnum, Option[Schema.AutonomousDatabaseBackupProperties.LifecycleStateEnum]] = (fun: Schema.AutonomousDatabaseBackupProperties.LifecycleStateEnum) => Option(fun)
		given putSchemaAutonomousDatabaseBackupPropertiesTypeEnum: Conversion[Schema.AutonomousDatabaseBackupProperties.TypeEnum, Option[Schema.AutonomousDatabaseBackupProperties.TypeEnum]] = (fun: Schema.AutonomousDatabaseBackupProperties.TypeEnum) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
