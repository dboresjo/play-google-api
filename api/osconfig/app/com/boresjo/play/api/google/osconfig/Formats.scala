package com.boresjo.play.api.google.osconfig

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtProjectFeatureSettings: Format[Schema.ProjectFeatureSettings] = Json.format[Schema.ProjectFeatureSettings]
	given fmtProjectFeatureSettingsPatchAndConfigFeatureSetEnum: Format[Schema.ProjectFeatureSettings.PatchAndConfigFeatureSetEnum] = JsonEnumFormat[Schema.ProjectFeatureSettings.PatchAndConfigFeatureSetEnum]
	given fmtExecutePatchJobRequest: Format[Schema.ExecutePatchJobRequest] = Json.format[Schema.ExecutePatchJobRequest]
	given fmtPatchInstanceFilter: Format[Schema.PatchInstanceFilter] = Json.format[Schema.PatchInstanceFilter]
	given fmtPatchConfig: Format[Schema.PatchConfig] = Json.format[Schema.PatchConfig]
	given fmtPatchRollout: Format[Schema.PatchRollout] = Json.format[Schema.PatchRollout]
	given fmtPatchInstanceFilterGroupLabel: Format[Schema.PatchInstanceFilterGroupLabel] = Json.format[Schema.PatchInstanceFilterGroupLabel]
	given fmtPatchConfigRebootConfigEnum: Format[Schema.PatchConfig.RebootConfigEnum] = JsonEnumFormat[Schema.PatchConfig.RebootConfigEnum]
	given fmtAptSettings: Format[Schema.AptSettings] = Json.format[Schema.AptSettings]
	given fmtYumSettings: Format[Schema.YumSettings] = Json.format[Schema.YumSettings]
	given fmtGooSettings: Format[Schema.GooSettings] = Json.format[Schema.GooSettings]
	given fmtZypperSettings: Format[Schema.ZypperSettings] = Json.format[Schema.ZypperSettings]
	given fmtWindowsUpdateSettings: Format[Schema.WindowsUpdateSettings] = Json.format[Schema.WindowsUpdateSettings]
	given fmtExecStep: Format[Schema.ExecStep] = Json.format[Schema.ExecStep]
	given fmtAptSettingsTypeEnum: Format[Schema.AptSettings.TypeEnum] = JsonEnumFormat[Schema.AptSettings.TypeEnum]
	given fmtWindowsUpdateSettingsClassificationsEnum: Format[Schema.WindowsUpdateSettings.ClassificationsEnum] = JsonEnumFormat[Schema.WindowsUpdateSettings.ClassificationsEnum]
	given fmtExecStepConfig: Format[Schema.ExecStepConfig] = Json.format[Schema.ExecStepConfig]
	given fmtGcsObject: Format[Schema.GcsObject] = Json.format[Schema.GcsObject]
	given fmtExecStepConfigInterpreterEnum: Format[Schema.ExecStepConfig.InterpreterEnum] = JsonEnumFormat[Schema.ExecStepConfig.InterpreterEnum]
	given fmtPatchRolloutModeEnum: Format[Schema.PatchRollout.ModeEnum] = JsonEnumFormat[Schema.PatchRollout.ModeEnum]
	given fmtFixedOrPercent: Format[Schema.FixedOrPercent] = Json.format[Schema.FixedOrPercent]
	given fmtPatchJob: Format[Schema.PatchJob] = Json.format[Schema.PatchJob]
	given fmtPatchJobStateEnum: Format[Schema.PatchJob.StateEnum] = JsonEnumFormat[Schema.PatchJob.StateEnum]
	given fmtPatchJobInstanceDetailsSummary: Format[Schema.PatchJobInstanceDetailsSummary] = Json.format[Schema.PatchJobInstanceDetailsSummary]
	given fmtCancelPatchJobRequest: Format[Schema.CancelPatchJobRequest] = Json.format[Schema.CancelPatchJobRequest]
	given fmtListPatchJobsResponse: Format[Schema.ListPatchJobsResponse] = Json.format[Schema.ListPatchJobsResponse]
	given fmtListPatchJobInstanceDetailsResponse: Format[Schema.ListPatchJobInstanceDetailsResponse] = Json.format[Schema.ListPatchJobInstanceDetailsResponse]
	given fmtPatchJobInstanceDetails: Format[Schema.PatchJobInstanceDetails] = Json.format[Schema.PatchJobInstanceDetails]
	given fmtPatchJobInstanceDetailsStateEnum: Format[Schema.PatchJobInstanceDetails.StateEnum] = JsonEnumFormat[Schema.PatchJobInstanceDetails.StateEnum]
	given fmtPatchDeployment: Format[Schema.PatchDeployment] = Json.format[Schema.PatchDeployment]
	given fmtOneTimeSchedule: Format[Schema.OneTimeSchedule] = Json.format[Schema.OneTimeSchedule]
	given fmtRecurringSchedule: Format[Schema.RecurringSchedule] = Json.format[Schema.RecurringSchedule]
	given fmtPatchDeploymentStateEnum: Format[Schema.PatchDeployment.StateEnum] = JsonEnumFormat[Schema.PatchDeployment.StateEnum]
	given fmtTimeZone: Format[Schema.TimeZone] = Json.format[Schema.TimeZone]
	given fmtTimeOfDay: Format[Schema.TimeOfDay] = Json.format[Schema.TimeOfDay]
	given fmtRecurringScheduleFrequencyEnum: Format[Schema.RecurringSchedule.FrequencyEnum] = JsonEnumFormat[Schema.RecurringSchedule.FrequencyEnum]
	given fmtWeeklySchedule: Format[Schema.WeeklySchedule] = Json.format[Schema.WeeklySchedule]
	given fmtMonthlySchedule: Format[Schema.MonthlySchedule] = Json.format[Schema.MonthlySchedule]
	given fmtWeeklyScheduleDayOfWeekEnum: Format[Schema.WeeklySchedule.DayOfWeekEnum] = JsonEnumFormat[Schema.WeeklySchedule.DayOfWeekEnum]
	given fmtWeekDayOfMonth: Format[Schema.WeekDayOfMonth] = Json.format[Schema.WeekDayOfMonth]
	given fmtWeekDayOfMonthDayOfWeekEnum: Format[Schema.WeekDayOfMonth.DayOfWeekEnum] = JsonEnumFormat[Schema.WeekDayOfMonth.DayOfWeekEnum]
	given fmtListPatchDeploymentsResponse: Format[Schema.ListPatchDeploymentsResponse] = Json.format[Schema.ListPatchDeploymentsResponse]
	given fmtPausePatchDeploymentRequest: Format[Schema.PausePatchDeploymentRequest] = Json.format[Schema.PausePatchDeploymentRequest]
	given fmtResumePatchDeploymentRequest: Format[Schema.ResumePatchDeploymentRequest] = Json.format[Schema.ResumePatchDeploymentRequest]
	given fmtOSPolicyAssignment: Format[Schema.OSPolicyAssignment] = Json.format[Schema.OSPolicyAssignment]
	given fmtOSPolicy: Format[Schema.OSPolicy] = Json.format[Schema.OSPolicy]
	given fmtOSPolicyAssignmentInstanceFilter: Format[Schema.OSPolicyAssignmentInstanceFilter] = Json.format[Schema.OSPolicyAssignmentInstanceFilter]
	given fmtOSPolicyAssignmentRollout: Format[Schema.OSPolicyAssignmentRollout] = Json.format[Schema.OSPolicyAssignmentRollout]
	given fmtOSPolicyAssignmentRolloutStateEnum: Format[Schema.OSPolicyAssignment.RolloutStateEnum] = JsonEnumFormat[Schema.OSPolicyAssignment.RolloutStateEnum]
	given fmtOSPolicyModeEnum: Format[Schema.OSPolicy.ModeEnum] = JsonEnumFormat[Schema.OSPolicy.ModeEnum]
	given fmtOSPolicyResourceGroup: Format[Schema.OSPolicyResourceGroup] = Json.format[Schema.OSPolicyResourceGroup]
	given fmtOSPolicyInventoryFilter: Format[Schema.OSPolicyInventoryFilter] = Json.format[Schema.OSPolicyInventoryFilter]
	given fmtOSPolicyResource: Format[Schema.OSPolicyResource] = Json.format[Schema.OSPolicyResource]
	given fmtOSPolicyResourcePackageResource: Format[Schema.OSPolicyResourcePackageResource] = Json.format[Schema.OSPolicyResourcePackageResource]
	given fmtOSPolicyResourceRepositoryResource: Format[Schema.OSPolicyResourceRepositoryResource] = Json.format[Schema.OSPolicyResourceRepositoryResource]
	given fmtOSPolicyResourceExecResource: Format[Schema.OSPolicyResourceExecResource] = Json.format[Schema.OSPolicyResourceExecResource]
	given fmtOSPolicyResourceFileResource: Format[Schema.OSPolicyResourceFileResource] = Json.format[Schema.OSPolicyResourceFileResource]
	given fmtOSPolicyResourcePackageResourceDesiredStateEnum: Format[Schema.OSPolicyResourcePackageResource.DesiredStateEnum] = JsonEnumFormat[Schema.OSPolicyResourcePackageResource.DesiredStateEnum]
	given fmtOSPolicyResourcePackageResourceAPT: Format[Schema.OSPolicyResourcePackageResourceAPT] = Json.format[Schema.OSPolicyResourcePackageResourceAPT]
	given fmtOSPolicyResourcePackageResourceDeb: Format[Schema.OSPolicyResourcePackageResourceDeb] = Json.format[Schema.OSPolicyResourcePackageResourceDeb]
	given fmtOSPolicyResourcePackageResourceYUM: Format[Schema.OSPolicyResourcePackageResourceYUM] = Json.format[Schema.OSPolicyResourcePackageResourceYUM]
	given fmtOSPolicyResourcePackageResourceZypper: Format[Schema.OSPolicyResourcePackageResourceZypper] = Json.format[Schema.OSPolicyResourcePackageResourceZypper]
	given fmtOSPolicyResourcePackageResourceRPM: Format[Schema.OSPolicyResourcePackageResourceRPM] = Json.format[Schema.OSPolicyResourcePackageResourceRPM]
	given fmtOSPolicyResourcePackageResourceGooGet: Format[Schema.OSPolicyResourcePackageResourceGooGet] = Json.format[Schema.OSPolicyResourcePackageResourceGooGet]
	given fmtOSPolicyResourcePackageResourceMSI: Format[Schema.OSPolicyResourcePackageResourceMSI] = Json.format[Schema.OSPolicyResourcePackageResourceMSI]
	given fmtOSPolicyResourceFile: Format[Schema.OSPolicyResourceFile] = Json.format[Schema.OSPolicyResourceFile]
	given fmtOSPolicyResourceFileRemote: Format[Schema.OSPolicyResourceFileRemote] = Json.format[Schema.OSPolicyResourceFileRemote]
	given fmtOSPolicyResourceFileGcs: Format[Schema.OSPolicyResourceFileGcs] = Json.format[Schema.OSPolicyResourceFileGcs]
	given fmtOSPolicyResourceRepositoryResourceAptRepository: Format[Schema.OSPolicyResourceRepositoryResourceAptRepository] = Json.format[Schema.OSPolicyResourceRepositoryResourceAptRepository]
	given fmtOSPolicyResourceRepositoryResourceYumRepository: Format[Schema.OSPolicyResourceRepositoryResourceYumRepository] = Json.format[Schema.OSPolicyResourceRepositoryResourceYumRepository]
	given fmtOSPolicyResourceRepositoryResourceZypperRepository: Format[Schema.OSPolicyResourceRepositoryResourceZypperRepository] = Json.format[Schema.OSPolicyResourceRepositoryResourceZypperRepository]
	given fmtOSPolicyResourceRepositoryResourceGooRepository: Format[Schema.OSPolicyResourceRepositoryResourceGooRepository] = Json.format[Schema.OSPolicyResourceRepositoryResourceGooRepository]
	given fmtOSPolicyResourceRepositoryResourceAptRepositoryArchiveTypeEnum: Format[Schema.OSPolicyResourceRepositoryResourceAptRepository.ArchiveTypeEnum] = JsonEnumFormat[Schema.OSPolicyResourceRepositoryResourceAptRepository.ArchiveTypeEnum]
	given fmtOSPolicyResourceExecResourceExec: Format[Schema.OSPolicyResourceExecResourceExec] = Json.format[Schema.OSPolicyResourceExecResourceExec]
	given fmtOSPolicyResourceExecResourceExecInterpreterEnum: Format[Schema.OSPolicyResourceExecResourceExec.InterpreterEnum] = JsonEnumFormat[Schema.OSPolicyResourceExecResourceExec.InterpreterEnum]
	given fmtOSPolicyResourceFileResourceStateEnum: Format[Schema.OSPolicyResourceFileResource.StateEnum] = JsonEnumFormat[Schema.OSPolicyResourceFileResource.StateEnum]
	given fmtOSPolicyAssignmentLabelSet: Format[Schema.OSPolicyAssignmentLabelSet] = Json.format[Schema.OSPolicyAssignmentLabelSet]
	given fmtOSPolicyAssignmentInstanceFilterInventory: Format[Schema.OSPolicyAssignmentInstanceFilterInventory] = Json.format[Schema.OSPolicyAssignmentInstanceFilterInventory]
	given fmtListOSPolicyAssignmentsResponse: Format[Schema.ListOSPolicyAssignmentsResponse] = Json.format[Schema.ListOSPolicyAssignmentsResponse]
	given fmtListOSPolicyAssignmentRevisionsResponse: Format[Schema.ListOSPolicyAssignmentRevisionsResponse] = Json.format[Schema.ListOSPolicyAssignmentRevisionsResponse]
	given fmtOSPolicyAssignmentReport: Format[Schema.OSPolicyAssignmentReport] = Json.format[Schema.OSPolicyAssignmentReport]
	given fmtOSPolicyAssignmentReportOSPolicyCompliance: Format[Schema.OSPolicyAssignmentReportOSPolicyCompliance] = Json.format[Schema.OSPolicyAssignmentReportOSPolicyCompliance]
	given fmtOSPolicyAssignmentReportOSPolicyComplianceComplianceStateEnum: Format[Schema.OSPolicyAssignmentReportOSPolicyCompliance.ComplianceStateEnum] = JsonEnumFormat[Schema.OSPolicyAssignmentReportOSPolicyCompliance.ComplianceStateEnum]
	given fmtOSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceCompliance: Format[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceCompliance] = Json.format[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceCompliance]
	given fmtOSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceOSPolicyResourceConfigStep: Format[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceOSPolicyResourceConfigStep] = Json.format[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceOSPolicyResourceConfigStep]
	given fmtOSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceComplianceStateEnum: Format[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceCompliance.ComplianceStateEnum] = JsonEnumFormat[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceCompliance.ComplianceStateEnum]
	given fmtOSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceExecResourceOutput: Format[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceExecResourceOutput] = Json.format[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceExecResourceOutput]
	given fmtOSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceOSPolicyResourceConfigStepTypeEnum: Format[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceOSPolicyResourceConfigStep.TypeEnum] = JsonEnumFormat[Schema.OSPolicyAssignmentReportOSPolicyComplianceOSPolicyResourceComplianceOSPolicyResourceConfigStep.TypeEnum]
	given fmtListOSPolicyAssignmentReportsResponse: Format[Schema.ListOSPolicyAssignmentReportsResponse] = Json.format[Schema.ListOSPolicyAssignmentReportsResponse]
	given fmtInventory: Format[Schema.Inventory] = Json.format[Schema.Inventory]
	given fmtInventoryOsInfo: Format[Schema.InventoryOsInfo] = Json.format[Schema.InventoryOsInfo]
	given fmtInventoryItem: Format[Schema.InventoryItem] = Json.format[Schema.InventoryItem]
	given fmtInventoryItemOriginTypeEnum: Format[Schema.InventoryItem.OriginTypeEnum] = JsonEnumFormat[Schema.InventoryItem.OriginTypeEnum]
	given fmtInventoryItemTypeEnum: Format[Schema.InventoryItem.TypeEnum] = JsonEnumFormat[Schema.InventoryItem.TypeEnum]
	given fmtInventorySoftwarePackage: Format[Schema.InventorySoftwarePackage] = Json.format[Schema.InventorySoftwarePackage]
	given fmtInventoryVersionedPackage: Format[Schema.InventoryVersionedPackage] = Json.format[Schema.InventoryVersionedPackage]
	given fmtInventoryZypperPatch: Format[Schema.InventoryZypperPatch] = Json.format[Schema.InventoryZypperPatch]
	given fmtInventoryWindowsUpdatePackage: Format[Schema.InventoryWindowsUpdatePackage] = Json.format[Schema.InventoryWindowsUpdatePackage]
	given fmtInventoryWindowsQuickFixEngineeringPackage: Format[Schema.InventoryWindowsQuickFixEngineeringPackage] = Json.format[Schema.InventoryWindowsQuickFixEngineeringPackage]
	given fmtInventoryWindowsApplication: Format[Schema.InventoryWindowsApplication] = Json.format[Schema.InventoryWindowsApplication]
	given fmtInventoryWindowsUpdatePackageWindowsUpdateCategory: Format[Schema.InventoryWindowsUpdatePackageWindowsUpdateCategory] = Json.format[Schema.InventoryWindowsUpdatePackageWindowsUpdateCategory]
	given fmtDate: Format[Schema.Date] = Json.format[Schema.Date]
	given fmtListInventoriesResponse: Format[Schema.ListInventoriesResponse] = Json.format[Schema.ListInventoriesResponse]
	given fmtVulnerabilityReport: Format[Schema.VulnerabilityReport] = Json.format[Schema.VulnerabilityReport]
	given fmtVulnerabilityReportVulnerability: Format[Schema.VulnerabilityReportVulnerability] = Json.format[Schema.VulnerabilityReportVulnerability]
	given fmtVulnerabilityReportVulnerabilityDetails: Format[Schema.VulnerabilityReportVulnerabilityDetails] = Json.format[Schema.VulnerabilityReportVulnerabilityDetails]
	given fmtVulnerabilityReportVulnerabilityItem: Format[Schema.VulnerabilityReportVulnerabilityItem] = Json.format[Schema.VulnerabilityReportVulnerabilityItem]
	given fmtCVSSv3: Format[Schema.CVSSv3] = Json.format[Schema.CVSSv3]
	given fmtVulnerabilityReportVulnerabilityDetailsReference: Format[Schema.VulnerabilityReportVulnerabilityDetailsReference] = Json.format[Schema.VulnerabilityReportVulnerabilityDetailsReference]
	given fmtCVSSv3AttackVectorEnum: Format[Schema.CVSSv3.AttackVectorEnum] = JsonEnumFormat[Schema.CVSSv3.AttackVectorEnum]
	given fmtCVSSv3AttackComplexityEnum: Format[Schema.CVSSv3.AttackComplexityEnum] = JsonEnumFormat[Schema.CVSSv3.AttackComplexityEnum]
	given fmtCVSSv3PrivilegesRequiredEnum: Format[Schema.CVSSv3.PrivilegesRequiredEnum] = JsonEnumFormat[Schema.CVSSv3.PrivilegesRequiredEnum]
	given fmtCVSSv3UserInteractionEnum: Format[Schema.CVSSv3.UserInteractionEnum] = JsonEnumFormat[Schema.CVSSv3.UserInteractionEnum]
	given fmtCVSSv3ScopeEnum: Format[Schema.CVSSv3.ScopeEnum] = JsonEnumFormat[Schema.CVSSv3.ScopeEnum]
	given fmtCVSSv3ConfidentialityImpactEnum: Format[Schema.CVSSv3.ConfidentialityImpactEnum] = JsonEnumFormat[Schema.CVSSv3.ConfidentialityImpactEnum]
	given fmtCVSSv3IntegrityImpactEnum: Format[Schema.CVSSv3.IntegrityImpactEnum] = JsonEnumFormat[Schema.CVSSv3.IntegrityImpactEnum]
	given fmtCVSSv3AvailabilityImpactEnum: Format[Schema.CVSSv3.AvailabilityImpactEnum] = JsonEnumFormat[Schema.CVSSv3.AvailabilityImpactEnum]
	given fmtListVulnerabilityReportsResponse: Format[Schema.ListVulnerabilityReportsResponse] = Json.format[Schema.ListVulnerabilityReportsResponse]
	given fmtOSPolicyAssignmentOperationMetadata: Format[Schema.OSPolicyAssignmentOperationMetadata] = Json.format[Schema.OSPolicyAssignmentOperationMetadata]
	given fmtOSPolicyAssignmentOperationMetadataApiMethodEnum: Format[Schema.OSPolicyAssignmentOperationMetadata.ApiMethodEnum] = JsonEnumFormat[Schema.OSPolicyAssignmentOperationMetadata.ApiMethodEnum]
	given fmtOSPolicyAssignmentOperationMetadataRolloutStateEnum: Format[Schema.OSPolicyAssignmentOperationMetadata.RolloutStateEnum] = JsonEnumFormat[Schema.OSPolicyAssignmentOperationMetadata.RolloutStateEnum]
	given fmtGoogleCloudOsconfigV1OSPolicyAssignmentOperationMetadata: Format[Schema.GoogleCloudOsconfigV1__OSPolicyAssignmentOperationMetadata] = Json.format[Schema.GoogleCloudOsconfigV1__OSPolicyAssignmentOperationMetadata]
	given fmtGoogleCloudOsconfigV1OSPolicyAssignmentOperationMetadataApiMethodEnum: Format[Schema.GoogleCloudOsconfigV1__OSPolicyAssignmentOperationMetadata.ApiMethodEnum] = JsonEnumFormat[Schema.GoogleCloudOsconfigV1__OSPolicyAssignmentOperationMetadata.ApiMethodEnum]
	given fmtGoogleCloudOsconfigV1OSPolicyAssignmentOperationMetadataRolloutStateEnum: Format[Schema.GoogleCloudOsconfigV1__OSPolicyAssignmentOperationMetadata.RolloutStateEnum] = JsonEnumFormat[Schema.GoogleCloudOsconfigV1__OSPolicyAssignmentOperationMetadata.RolloutStateEnum]
	given fmtGoogleCloudOsconfigV2betaOperationMetadata: Format[Schema.GoogleCloudOsconfigV2beta__OperationMetadata] = Json.format[Schema.GoogleCloudOsconfigV2beta__OperationMetadata]
}
