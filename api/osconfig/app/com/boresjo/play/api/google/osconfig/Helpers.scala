package com.boresjo.play.api.google.osconfig

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaProjectFeatureSettingsPatchAndConfigFeatureSetEnum: Conversion[Schema.ProjectFeatureSettings.PatchAndConfigFeatureSetEnum, Option[Schema.ProjectFeatureSettings.PatchAndConfigFeatureSetEnum]] = (fun: Schema.ProjectFeatureSettings.PatchAndConfigFeatureSetEnum) => Option(fun)
		given putSchemaPatchInstanceFilter: Conversion[Schema.PatchInstanceFilter, Option[Schema.PatchInstanceFilter]] = (fun: Schema.PatchInstanceFilter) => Option(fun)
		given putSchemaPatchConfig: Conversion[Schema.PatchConfig, Option[Schema.PatchConfig]] = (fun: Schema.PatchConfig) => Option(fun)
		given putSchemaPatchRollout: Conversion[Schema.PatchRollout, Option[Schema.PatchRollout]] = (fun: Schema.PatchRollout) => Option(fun)
		given putListSchemaPatchInstanceFilterGroupLabel: Conversion[List[Schema.PatchInstanceFilterGroupLabel], Option[List[Schema.PatchInstanceFilterGroupLabel]]] = (fun: List[Schema.PatchInstanceFilterGroupLabel]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaPatchConfigRebootConfigEnum: Conversion[Schema.PatchConfig.RebootConfigEnum, Option[Schema.PatchConfig.RebootConfigEnum]] = (fun: Schema.PatchConfig.RebootConfigEnum) => Option(fun)
		given putSchemaAptSettings: Conversion[Schema.AptSettings, Option[Schema.AptSettings]] = (fun: Schema.AptSettings) => Option(fun)
		given putSchemaYumSettings: Conversion[Schema.YumSettings, Option[Schema.YumSettings]] = (fun: Schema.YumSettings) => Option(fun)
		given putSchemaGooSettings: Conversion[Schema.GooSettings, Option[Schema.GooSettings]] = (fun: Schema.GooSettings) => Option(fun)
		given putSchemaZypperSettings: Conversion[Schema.ZypperSettings, Option[Schema.ZypperSettings]] = (fun: Schema.ZypperSettings) => Option(fun)
		given putSchemaWindowsUpdateSettings: Conversion[Schema.WindowsUpdateSettings, Option[Schema.WindowsUpdateSettings]] = (fun: Schema.WindowsUpdateSettings) => Option(fun)
		given putSchemaExecStep: Conversion[Schema.ExecStep, Option[Schema.ExecStep]] = (fun: Schema.ExecStep) => Option(fun)
		given putSchemaAptSettingsTypeEnum: Conversion[Schema.AptSettings.TypeEnum, Option[Schema.AptSettings.TypeEnum]] = (fun: Schema.AptSettings.TypeEnum) => Option(fun)
		given putListSchemaWindowsUpdateSettingsClassificationsEnum: Conversion[List[Schema.WindowsUpdateSettings.ClassificationsEnum], Option[List[Schema.WindowsUpdateSettings.ClassificationsEnum]]] = (fun: List[Schema.WindowsUpdateSettings.ClassificationsEnum]) => Option(fun)
		given putSchemaExecStepConfig: Conversion[Schema.ExecStepConfig, Option[Schema.ExecStepConfig]] = (fun: Schema.ExecStepConfig) => Option(fun)
		given putSchemaGcsObject: Conversion[Schema.GcsObject, Option[Schema.GcsObject]] = (fun: Schema.GcsObject) => Option(fun)
		given putListInt: Conversion[List[Int], Option[List[Int]]] = (fun: List[Int]) => Option(fun)
		given putSchemaExecStepConfigInterpreterEnum: Conversion[Schema.ExecStepConfig.InterpreterEnum, Option[Schema.ExecStepConfig.InterpreterEnum]] = (fun: Schema.ExecStepConfig.InterpreterEnum) => Option(fun)
		given putSchemaPatchRolloutModeEnum: Conversion[Schema.PatchRollout.ModeEnum, Option[Schema.PatchRollout.ModeEnum]] = (fun: Schema.PatchRollout.ModeEnum) => Option(fun)
		given putSchemaFixedOrPercent: Conversion[Schema.FixedOrPercent, Option[Schema.FixedOrPercent]] = (fun: Schema.FixedOrPercent) => Option(fun)
		given putSchemaPatchJobStateEnum: Conversion[Schema.PatchJob.StateEnum, Option[Schema.PatchJob.StateEnum]] = (fun: Schema.PatchJob.StateEnum) => Option(fun)
		given putSchemaPatchJobInstanceDetailsSummary: Conversion[Schema.PatchJobInstanceDetailsSummary, Option[Schema.PatchJobInstanceDetailsSummary]] = (fun: Schema.PatchJobInstanceDetailsSummary) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListSchemaPatchJob: Conversion[List[Schema.PatchJob], Option[List[Schema.PatchJob]]] = (fun: List[Schema.PatchJob]) => Option(fun)
		given putListSchemaPatchJobInstanceDetails: Conversion[List[Schema.PatchJobInstanceDetails], Option[List[Schema.PatchJobInstanceDetails]]] = (fun: List[Schema.PatchJobInstanceDetails]) => Option(fun)
		given putSchemaPatchJobInstanceDetailsStateEnum: Conversion[Schema.PatchJobInstanceDetails.StateEnum, Option[Schema.PatchJobInstanceDetails.StateEnum]] = (fun: Schema.PatchJobInstanceDetails.StateEnum) => Option(fun)
		given putSchemaOneTimeSchedule: Conversion[Schema.OneTimeSchedule, Option[Schema.OneTimeSchedule]] = (fun: Schema.OneTimeSchedule) => Option(fun)
		given putSchemaRecurringSchedule: Conversion[Schema.RecurringSchedule, Option[Schema.RecurringSchedule]] = (fun: Schema.RecurringSchedule) => Option(fun)
		given putSchemaPatchDeploymentStateEnum: Conversion[Schema.PatchDeployment.StateEnum, Option[Schema.PatchDeployment.StateEnum]] = (fun: Schema.PatchDeployment.StateEnum) => Option(fun)
		given putSchemaTimeZone: Conversion[Schema.TimeZone, Option[Schema.TimeZone]] = (fun: Schema.TimeZone) => Option(fun)
		given putSchemaTimeOfDay: Conversion[Schema.TimeOfDay, Option[Schema.TimeOfDay]] = (fun: Schema.TimeOfDay) => Option(fun)
		given putSchemaRecurringScheduleFrequencyEnum: Conversion[Schema.RecurringSchedule.FrequencyEnum, Option[Schema.RecurringSchedule.FrequencyEnum]] = (fun: Schema.RecurringSchedule.FrequencyEnum) => Option(fun)
		given putSchemaWeeklySchedule: Conversion[Schema.WeeklySchedule, Option[Schema.WeeklySchedule]] = (fun: Schema.WeeklySchedule) => Option(fun)
		given putSchemaMonthlySchedule: Conversion[Schema.MonthlySchedule, Option[Schema.MonthlySchedule]] = (fun: Schema.MonthlySchedule) => Option(fun)
		given putSchemaWeeklyScheduleDayOfWeekEnum: Conversion[Schema.WeeklySchedule.DayOfWeekEnum, Option[Schema.WeeklySchedule.DayOfWeekEnum]] = (fun: Schema.WeeklySchedule.DayOfWeekEnum) => Option(fun)
		given putSchemaWeekDayOfMonth: Conversion[Schema.WeekDayOfMonth, Option[Schema.WeekDayOfMonth]] = (fun: Schema.WeekDayOfMonth) => Option(fun)
		given putSchemaWeekDayOfMonthDayOfWeekEnum: Conversion[Schema.WeekDayOfMonth.DayOfWeekEnum, Option[Schema.WeekDayOfMonth.DayOfWeekEnum]] = (fun: Schema.WeekDayOfMonth.DayOfWeekEnum) => Option(fun)
		given putListSchemaPatchDeployment: Conversion[List[Schema.PatchDeployment], Option[List[Schema.PatchDeployment]]] = (fun: List[Schema.PatchDeployment]) => Option(fun)
		given putListSchemaOSPolicy: Conversion[List[Schema.OSPolicy], Option[List[Schema.OSPolicy]]] = (fun: List[Schema.OSPolicy]) => Option(fun)
		given putSchemaOSPolicyAssignmentInstanceFilter: Conversion[Schema.OSPolicyAssignmentInstanceFilter, Option[Schema.OSPolicyAssignmentInstanceFilter]] = (fun: Schema.OSPolicyAssignmentInstanceFilter) => Option(fun)
		given putSchemaOSPolicyAssignmentRollout: Conversion[Schema.OSPolicyAssignmentRollout, Option[Schema.OSPolicyAssignmentRollout]] = (fun: Schema.OSPolicyAssignmentRollout) => Option(fun)
		given putSchemaOSPolicyAssignmentRolloutStateEnum: Conversion[Schema.OSPolicyAssignment.RolloutStateEnum, Option[Schema.OSPolicyAssignment.RolloutStateEnum]] = (fun: Schema.OSPolicyAssignment.RolloutStateEnum) => Option(fun)
		given putSchemaOSPolicyModeEnum: Conversion[Schema.OSPolicy.ModeEnum, Option[Schema.OSPolicy.ModeEnum]] = (fun: Schema.OSPolicy.ModeEnum) => Option(fun)
		given putListSchemaOSPolicyResourceGroup: Conversion[List[Schema.OSPolicyResourceGroup], Option[List[Schema.OSPolicyResourceGroup]]] = (fun: List[Schema.OSPolicyResourceGroup]) => Option(fun)
		given putListSchemaOSPolicyInventoryFilter: Conversion[List[Schema.OSPolicyInventoryFilter], Option[List[Schema.OSPolicyInventoryFilter]]] = (fun: List[Schema.OSPolicyInventoryFilter]) => Option(fun)
		given putListSchemaOSPolicyResource: Conversion[List[Schema.OSPolicyResource], Option[List[Schema.OSPolicyResource]]] = (fun: List[Schema.OSPolicyResource]) => Option(fun)
		given putSchemaOSPolicyResourcePackageResource: Conversion[Schema.OSPolicyResourcePackageResource, Option[Schema.OSPolicyResourcePackageResource]] = (fun: Schema.OSPolicyResourcePackageResource) => Option(fun)
		given putSchemaOSPolicyResourceRepositoryResource: Conversion[Schema.OSPolicyResourceRepositoryResource, Option[Schema.OSPolicyResourceRepositoryResource]] = (fun: Schema.OSPolicyResourceRepositoryResource) => Option(fun)
		given putSchemaOSPolicyResourceExecResource: Conversion[Schema.OSPolicyResourceExecResource, Option[Schema.OSPolicyResourceExecResource]] = (fun: Schema.OSPolicyResourceExecResource) => Option(fun)
		given putSchemaOSPolicyResourceFileResource: Conversion[Schema.OSPolicyResourceFileResource, Option[Schema.OSPolicyResourceFileResource]] = (fun: Schema.OSPolicyResourceFileResource) => Option(fun)
		given putSchemaOSPolicyResourcePackageResourceDesiredStateEnum: Conversion[Schema.OSPolicyResourcePackageResource.DesiredStateEnum, Option[Schema.OSPolicyResourcePackageResource.DesiredStateEnum]] = (fun: Schema.OSPolicyResourcePackageResource.DesiredStateEnum) => Option(fun)
		given putSchemaOSPolicyResourcePackageResourceAPT: Conversion[Schema.OSPolicyResourcePackageResourceAPT, Option[Schema.OSPolicyResourcePackageResourceAPT]] = (fun: Schema.OSPolicyResourcePackageResourceAPT) => Option(fun)
		given putSchemaOSPolicyResourcePackageResourceDeb: Conversion[Schema.OSPolicyResourcePackageResourceDeb, Option[Schema.OSPolicyResourcePackageResourceDeb]] = (fun: Schema.OSPolicyResourcePackageResourceDeb) => Option(fun)
		given putSchemaOSPolicyResourcePackageResourceYUM: Conversion[Schema.OSPolicyResourcePackageResourceYUM, Option[Schema.OSPolicyResourcePackageResourceYUM]] = (fun: Schema.OSPolicyResourcePackageResourceYUM) => Option(fun)
		given putSchemaOSPolicyResourcePackageResourceZypper: Conversion[Schema.OSPolicyResourcePackageResourceZypper, Option[Schema.OSPolicyResourcePackageResourceZypper]] = (fun: Schema.OSPolicyResourcePackageResourceZypper) => Option(fun)
		given putSchemaOSPolicyResourcePackageResourceRPM: Conversion[Schema.OSPolicyResourcePackageResourceRPM, Option[Schema.OSPolicyResourcePackageResourceRPM]] = (fun: Schema.OSPolicyResourcePackageResourceRPM) => Option(fun)
		given putSchemaOSPolicyResourcePackageResourceGooGet: Conversion[Schema.OSPolicyResourcePackageResourceGooGet, Option[Schema.OSPolicyResourcePackageResourceGooGet]] = (fun: Schema.OSPolicyResourcePackageResourceGooGet) => Option(fun)
		given putSchemaOSPolicyResourcePackageResourceMSI: Conversion[Schema.OSPolicyResourcePackageResourceMSI, Option[Schema.OSPolicyResourcePackageResourceMSI]] = (fun: Schema.OSPolicyResourcePackageResourceMSI) => Option(fun)
		given putSchemaOSPolicyResourceFile: Conversion[Schema.OSPolicyResourceFile, Option[Schema.OSPolicyResourceFile]] = (fun: Schema.OSPolicyResourceFile) => Option(fun)
		given putSchemaOSPolicyResourceFileRemote: Conversion[Schema.OSPolicyResourceFileRemote, Option[Schema.OSPolicyResourceFileRemote]] = (fun: Schema.OSPolicyResourceFileRemote) => Option(fun)
		given putSchemaOSPolicyResourceFileGcs: Conversion[Schema.OSPolicyResourceFileGcs, Option[Schema.OSPolicyResourceFileGcs]] = (fun: Schema.OSPolicyResourceFileGcs) => Option(fun)
		given putSchemaOSPolicyResourceRepositoryResourceAptRepository: Conversion[Schema.OSPolicyResourceRepositoryResourceAptRepository, Option[Schema.OSPolicyResourceRepositoryResourceAptRepository]] = (fun: Schema.OSPolicyResourceRepositoryResourceAptRepository) => Option(fun)
		given putSchemaOSPolicyResourceRepositoryResourceYumRepository: Conversion[Schema.OSPolicyResourceRepositoryResourceYumRepository, Option[Schema.OSPolicyResourceRepositoryResourceYumRepository]] = (fun: Schema.OSPolicyResourceRepositoryResourceYumRepository) => Option(fun)
		given putSchemaOSPolicyResourceRepositoryResourceZypperRepository: Conversion[Schema.OSPolicyResourceRepositoryResourceZypperRepository, Option[Schema.OSPolicyResourceRepositoryResourceZypperRepository]] = (fun: Schema.OSPolicyResourceRepositoryResourceZypperRepository) => Option(fun)
		given putSchemaOSPolicyResourceRepositoryResourceGooRepository: Conversion[Schema.OSPolicyResourceRepositoryResourceGooRepository, Option[Schema.OSPolicyResourceRepositoryResourceGooRepository]] = (fun: Schema.OSPolicyResourceRepositoryResourceGooRepository) => Option(fun)
		given putSchemaOSPolicyResourceRepositoryResourceAptRepositoryArchiveTypeEnum: Conversion[Schema.OSPolicyResourceRepositoryResourceAptRepository.ArchiveTypeEnum, Option[Schema.OSPolicyResourceRepositoryResourceAptRepository.ArchiveTypeEnum]] = (fun: Schema.OSPolicyResourceRepositoryResourceAptRepository.ArchiveTypeEnum) => Option(fun)
		given putSchemaOSPolicyResourceExecResourceExec: Conversion[Schema.OSPolicyResourceExecResourceExec, Option[Schema.OSPolicyResourceExecResourceExec]] = (fun: Schema.OSPolicyResourceExecResourceExec) => Option(fun)
		given putSchemaOSPolicyResourceExecResourceExecInterpreterEnum: Conversion[Schema.OSPolicyResourceExecResourceExec.InterpreterEnum, Option[Schema.OSPolicyResourceExecResourceExec.InterpreterEnum]] = (fun: Schema.OSPolicyResourceExecResourceExec.InterpreterEnum) => Option(fun)
		given putSchemaOSPolicyResourceFileResourceStateEnum: Conversion[Schema.OSPolicyResourceFileResource.StateEnum, Option[Schema.OSPolicyResourceFileResource.StateEnum]] = (fun: Schema.OSPolicyResourceFileResource.StateEnum) => Option(fun)
		given putListSchemaOSPolicyAssignmentLabelSet: Conversion[List[Schema.OSPolicyAssignmentLabelSet], Option[List[Schema.OSPolicyAssignmentLabelSet]]] = (fun: List[Schema.OSPolicyAssignmentLabelSet]) => Option(fun)
		given putListSchemaOSPolicyAssignmentInstanceFilterInventory: Conversion[List[Schema.OSPolicyAssignmentInstanceFilterInventory], Option[List[Schema.OSPolicyAssignmentInstanceFilterInventory]]] = (fun: List[Schema.OSPolicyAssignmentInstanceFilterInventory]) => Option(fun)
		given putListSchemaOSPolicyAssignment: Conversion[List[Schema.OSPolicyAssignment], Option[List[Schema.OSPolicyAssignment]]] = (fun: List[Schema.OSPolicyAssignment]) => Option(fun)
		given putListSchemaOSPolicyAssignmentReportOSPolicyCompliance: Conversion[List[Schema.OSPolicyAssignmentReportOSPolicyCompliance], Option[List[Schema.OSPolicyAssignmentReportOSPolicyCompliance]]] = (fun: List[Schema.OSPolicyAssignmentReportOSPolicyCompliance]) => Option(fun)
		given putSchemaOSPolicyAssignmentReportOSPolicyComplianceComplianceStateEnum: Conversion[Schema.OSPolicyAssignmentReportOSPolicyCompliance.ComplianceStateEnum, Option[Schema.OSPolicyAssignmentReportOSPolicyCompliance.ComplianceStateEnum]] = (fun: Schema.OSPolicyAssignmentReportOSPolicyCompliance.ComplianceStateEnum) => Option(fun)
		given putListSchemaOSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceCompliance: Conversion[List[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceCompliance], Option[List[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceCompliance]]] = (fun: List[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceCompliance]) => Option(fun)
		given putListSchemaOSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceOSPolicyResourceConfigStep: Conversion[List[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceOSPolicyResourceConfigStep], Option[List[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceOSPolicyResourceConfigStep]]] = (fun: List[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceOSPolicyResourceConfigStep]) => Option(fun)
		given putSchemaOSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceComplianceStateEnum: Conversion[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceCompliance.ComplianceStateEnum, Option[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceCompliance.ComplianceStateEnum]] = (fun: Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceCompliance.ComplianceStateEnum) => Option(fun)
		given putSchemaOSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceExecResourceOutput: Conversion[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceExecResourceOutput, Option[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceExecResourceOutput]] = (fun: Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceExecResourceOutput) => Option(fun)
		given putSchemaOSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceOSPolicyResourceConfigStepTypeEnum: Conversion[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceOSPolicyResourceConfigStep.TypeEnum, Option[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceOSPolicyResourceConfigStep.TypeEnum]] = (fun: Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceOSPolicyResourceConfigStep.TypeEnum) => Option(fun)
		given putListSchemaOSPolicyAssignmentReport: Conversion[List[Schema.OSPolicyAssignmentReport], Option[List[Schema.OSPolicyAssignmentReport]]] = (fun: List[Schema.OSPolicyAssignmentReport]) => Option(fun)
		given putSchemaInventoryOsInfo: Conversion[Schema.InventoryOsInfo, Option[Schema.InventoryOsInfo]] = (fun: Schema.InventoryOsInfo) => Option(fun)
		given putMapStringSchemaInventoryItem: Conversion[Map[String, Schema.InventoryItem], Option[Map[String, Schema.InventoryItem]]] = (fun: Map[String, Schema.InventoryItem]) => Option(fun)
		given putSchemaInventoryItemOriginTypeEnum: Conversion[Schema.InventoryItem.OriginTypeEnum, Option[Schema.InventoryItem.OriginTypeEnum]] = (fun: Schema.InventoryItem.OriginTypeEnum) => Option(fun)
		given putSchemaInventoryItemTypeEnum: Conversion[Schema.InventoryItem.TypeEnum, Option[Schema.InventoryItem.TypeEnum]] = (fun: Schema.InventoryItem.TypeEnum) => Option(fun)
		given putSchemaInventorySoftwarePackage: Conversion[Schema.InventorySoftwarePackage, Option[Schema.InventorySoftwarePackage]] = (fun: Schema.InventorySoftwarePackage) => Option(fun)
		given putSchemaInventoryVersionedPackage: Conversion[Schema.InventoryVersionedPackage, Option[Schema.InventoryVersionedPackage]] = (fun: Schema.InventoryVersionedPackage) => Option(fun)
		given putSchemaInventoryZypperPatch: Conversion[Schema.InventoryZypperPatch, Option[Schema.InventoryZypperPatch]] = (fun: Schema.InventoryZypperPatch) => Option(fun)
		given putSchemaInventoryWindowsUpdatePackage: Conversion[Schema.InventoryWindowsUpdatePackage, Option[Schema.InventoryWindowsUpdatePackage]] = (fun: Schema.InventoryWindowsUpdatePackage) => Option(fun)
		given putSchemaInventoryWindowsQuickFixEngineeringPackage: Conversion[Schema.InventoryWindowsQuickFixEngineeringPackage, Option[Schema.InventoryWindowsQuickFixEngineeringPackage]] = (fun: Schema.InventoryWindowsQuickFixEngineeringPackage) => Option(fun)
		given putSchemaInventoryWindowsApplication: Conversion[Schema.InventoryWindowsApplication, Option[Schema.InventoryWindowsApplication]] = (fun: Schema.InventoryWindowsApplication) => Option(fun)
		given putListSchemaInventoryWindowsUpdatePackageWindowsUpdateCategory: Conversion[List[Schema.InventoryWindowsUpdatePackageWindowsUpdateCategory], Option[List[Schema.InventoryWindowsUpdatePackageWindowsUpdateCategory]]] = (fun: List[Schema.InventoryWindowsUpdatePackageWindowsUpdateCategory]) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
		given putListSchemaInventory: Conversion[List[Schema.Inventory], Option[List[Schema.Inventory]]] = (fun: List[Schema.Inventory]) => Option(fun)
		given putListSchemaVulnerabilityReportVulnerability: Conversion[List[Schema.VulnerabilityReportVulnerability], Option[List[Schema.VulnerabilityReportVulnerability]]] = (fun: List[Schema.VulnerabilityReportVulnerability]) => Option(fun)
		given putSchemaVulnerabilityReportVulnerabilityDetails: Conversion[Schema.VulnerabilityReportVulnerabilityDetails, Option[Schema.VulnerabilityReportVulnerabilityDetails]] = (fun: Schema.VulnerabilityReportVulnerabilityDetails) => Option(fun)
		given putListSchemaVulnerabilityReportVulnerabilityItem: Conversion[List[Schema.VulnerabilityReportVulnerabilityItem], Option[List[Schema.VulnerabilityReportVulnerabilityItem]]] = (fun: List[Schema.VulnerabilityReportVulnerabilityItem]) => Option(fun)
		given putSchemaCVSSv3: Conversion[Schema.CVSSv3, Option[Schema.CVSSv3]] = (fun: Schema.CVSSv3) => Option(fun)
		given putListSchemaVulnerabilityReportVulnerabilityDetailsReference: Conversion[List[Schema.VulnerabilityReportVulnerabilityDetailsReference], Option[List[Schema.VulnerabilityReportVulnerabilityDetailsReference]]] = (fun: List[Schema.VulnerabilityReportVulnerabilityDetailsReference]) => Option(fun)
		given putSchemaCVSSv3AttackVectorEnum: Conversion[Schema.CVSSv3.AttackVectorEnum, Option[Schema.CVSSv3.AttackVectorEnum]] = (fun: Schema.CVSSv3.AttackVectorEnum) => Option(fun)
		given putSchemaCVSSv3AttackComplexityEnum: Conversion[Schema.CVSSv3.AttackComplexityEnum, Option[Schema.CVSSv3.AttackComplexityEnum]] = (fun: Schema.CVSSv3.AttackComplexityEnum) => Option(fun)
		given putSchemaCVSSv3PrivilegesRequiredEnum: Conversion[Schema.CVSSv3.PrivilegesRequiredEnum, Option[Schema.CVSSv3.PrivilegesRequiredEnum]] = (fun: Schema.CVSSv3.PrivilegesRequiredEnum) => Option(fun)
		given putSchemaCVSSv3UserInteractionEnum: Conversion[Schema.CVSSv3.UserInteractionEnum, Option[Schema.CVSSv3.UserInteractionEnum]] = (fun: Schema.CVSSv3.UserInteractionEnum) => Option(fun)
		given putSchemaCVSSv3ScopeEnum: Conversion[Schema.CVSSv3.ScopeEnum, Option[Schema.CVSSv3.ScopeEnum]] = (fun: Schema.CVSSv3.ScopeEnum) => Option(fun)
		given putSchemaCVSSv3ConfidentialityImpactEnum: Conversion[Schema.CVSSv3.ConfidentialityImpactEnum, Option[Schema.CVSSv3.ConfidentialityImpactEnum]] = (fun: Schema.CVSSv3.ConfidentialityImpactEnum) => Option(fun)
		given putSchemaCVSSv3IntegrityImpactEnum: Conversion[Schema.CVSSv3.IntegrityImpactEnum, Option[Schema.CVSSv3.IntegrityImpactEnum]] = (fun: Schema.CVSSv3.IntegrityImpactEnum) => Option(fun)
		given putSchemaCVSSv3AvailabilityImpactEnum: Conversion[Schema.CVSSv3.AvailabilityImpactEnum, Option[Schema.CVSSv3.AvailabilityImpactEnum]] = (fun: Schema.CVSSv3.AvailabilityImpactEnum) => Option(fun)
		given putListSchemaVulnerabilityReport: Conversion[List[Schema.VulnerabilityReport], Option[List[Schema.VulnerabilityReport]]] = (fun: List[Schema.VulnerabilityReport]) => Option(fun)
		given putSchemaOSPolicyAssignmentOperationMetadataApiMethodEnum: Conversion[Schema.OSPolicyAssignmentOperationMetadata.ApiMethodEnum, Option[Schema.OSPolicyAssignmentOperationMetadata.ApiMethodEnum]] = (fun: Schema.OSPolicyAssignmentOperationMetadata.ApiMethodEnum) => Option(fun)
		given putSchemaOSPolicyAssignmentOperationMetadataRolloutStateEnum: Conversion[Schema.OSPolicyAssignmentOperationMetadata.RolloutStateEnum, Option[Schema.OSPolicyAssignmentOperationMetadata.RolloutStateEnum]] = (fun: Schema.OSPolicyAssignmentOperationMetadata.RolloutStateEnum) => Option(fun)
		given putSchemaGoogleCloudOsconfigV1OSPolicyAssignmentOperationMetadataApiMethodEnum: Conversion[Schema.GoogleCloudOsconfigV1__OSPolicyAssignmentOperationMetadata.ApiMethodEnum, Option[Schema.GoogleCloudOsconfigV1__OSPolicyAssignmentOperationMetadata.ApiMethodEnum]] = (fun: Schema.GoogleCloudOsconfigV1__OSPolicyAssignmentOperationMetadata.ApiMethodEnum) => Option(fun)
		given putSchemaGoogleCloudOsconfigV1OSPolicyAssignmentOperationMetadataRolloutStateEnum: Conversion[Schema.GoogleCloudOsconfigV1__OSPolicyAssignmentOperationMetadata.RolloutStateEnum, Option[Schema.GoogleCloudOsconfigV1__OSPolicyAssignmentOperationMetadata.RolloutStateEnum]] = (fun: Schema.GoogleCloudOsconfigV1__OSPolicyAssignmentOperationMetadata.RolloutStateEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
