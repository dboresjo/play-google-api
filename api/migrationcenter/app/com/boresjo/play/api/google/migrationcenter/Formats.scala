package com.boresjo.play.api.google.migrationcenter

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtListAssetsResponse: Format[Schema.ListAssetsResponse] = Json.format[Schema.ListAssetsResponse]
	given fmtAsset: Format[Schema.Asset] = Json.format[Schema.Asset]
	given fmtMachineDetails: Format[Schema.MachineDetails] = Json.format[Schema.MachineDetails]
	given fmtInsightList: Format[Schema.InsightList] = Json.format[Schema.InsightList]
	given fmtAssetPerformanceData: Format[Schema.AssetPerformanceData] = Json.format[Schema.AssetPerformanceData]
	given fmtMachineDetailsPowerStateEnum: Format[Schema.MachineDetails.PowerStateEnum] = JsonEnumFormat[Schema.MachineDetails.PowerStateEnum]
	given fmtMachineArchitectureDetails: Format[Schema.MachineArchitectureDetails] = Json.format[Schema.MachineArchitectureDetails]
	given fmtGuestOsDetails: Format[Schema.GuestOsDetails] = Json.format[Schema.GuestOsDetails]
	given fmtMachineNetworkDetails: Format[Schema.MachineNetworkDetails] = Json.format[Schema.MachineNetworkDetails]
	given fmtMachineDiskDetails: Format[Schema.MachineDiskDetails] = Json.format[Schema.MachineDiskDetails]
	given fmtPlatformDetails: Format[Schema.PlatformDetails] = Json.format[Schema.PlatformDetails]
	given fmtBiosDetails: Format[Schema.BiosDetails] = Json.format[Schema.BiosDetails]
	given fmtMachineArchitectureDetailsFirmwareTypeEnum: Format[Schema.MachineArchitectureDetails.FirmwareTypeEnum] = JsonEnumFormat[Schema.MachineArchitectureDetails.FirmwareTypeEnum]
	given fmtMachineArchitectureDetailsHyperthreadingEnum: Format[Schema.MachineArchitectureDetails.HyperthreadingEnum] = JsonEnumFormat[Schema.MachineArchitectureDetails.HyperthreadingEnum]
	given fmtDate: Format[Schema.Date] = Json.format[Schema.Date]
	given fmtGuestOsDetailsFamilyEnum: Format[Schema.GuestOsDetails.FamilyEnum] = JsonEnumFormat[Schema.GuestOsDetails.FamilyEnum]
	given fmtGuestConfigDetails: Format[Schema.GuestConfigDetails] = Json.format[Schema.GuestConfigDetails]
	given fmtGuestRuntimeDetails: Format[Schema.GuestRuntimeDetails] = Json.format[Schema.GuestRuntimeDetails]
	given fmtFstabEntryList: Format[Schema.FstabEntryList] = Json.format[Schema.FstabEntryList]
	given fmtHostsEntryList: Format[Schema.HostsEntryList] = Json.format[Schema.HostsEntryList]
	given fmtNfsExportList: Format[Schema.NfsExportList] = Json.format[Schema.NfsExportList]
	given fmtGuestConfigDetailsSelinuxModeEnum: Format[Schema.GuestConfigDetails.SelinuxModeEnum] = JsonEnumFormat[Schema.GuestConfigDetails.SelinuxModeEnum]
	given fmtFstabEntry: Format[Schema.FstabEntry] = Json.format[Schema.FstabEntry]
	given fmtHostsEntry: Format[Schema.HostsEntry] = Json.format[Schema.HostsEntry]
	given fmtNfsExport: Format[Schema.NfsExport] = Json.format[Schema.NfsExport]
	given fmtRunningServiceList: Format[Schema.RunningServiceList] = Json.format[Schema.RunningServiceList]
	given fmtRunningProcessList: Format[Schema.RunningProcessList] = Json.format[Schema.RunningProcessList]
	given fmtRuntimeNetworkInfo: Format[Schema.RuntimeNetworkInfo] = Json.format[Schema.RuntimeNetworkInfo]
	given fmtGuestInstalledApplicationList: Format[Schema.GuestInstalledApplicationList] = Json.format[Schema.GuestInstalledApplicationList]
	given fmtOpenFileList: Format[Schema.OpenFileList] = Json.format[Schema.OpenFileList]
	given fmtRunningService: Format[Schema.RunningService] = Json.format[Schema.RunningService]
	given fmtRunningServiceStateEnum: Format[Schema.RunningService.StateEnum] = JsonEnumFormat[Schema.RunningService.StateEnum]
	given fmtRunningServiceStartModeEnum: Format[Schema.RunningService.StartModeEnum] = JsonEnumFormat[Schema.RunningService.StartModeEnum]
	given fmtRunningProcess: Format[Schema.RunningProcess] = Json.format[Schema.RunningProcess]
	given fmtNetworkConnectionList: Format[Schema.NetworkConnectionList] = Json.format[Schema.NetworkConnectionList]
	given fmtNetworkConnection: Format[Schema.NetworkConnection] = Json.format[Schema.NetworkConnection]
	given fmtNetworkConnectionStateEnum: Format[Schema.NetworkConnection.StateEnum] = JsonEnumFormat[Schema.NetworkConnection.StateEnum]
	given fmtGuestInstalledApplication: Format[Schema.GuestInstalledApplication] = Json.format[Schema.GuestInstalledApplication]
	given fmtOpenFileDetails: Format[Schema.OpenFileDetails] = Json.format[Schema.OpenFileDetails]
	given fmtNetworkAdapterList: Format[Schema.NetworkAdapterList] = Json.format[Schema.NetworkAdapterList]
	given fmtNetworkAdapterDetails: Format[Schema.NetworkAdapterDetails] = Json.format[Schema.NetworkAdapterDetails]
	given fmtNetworkAddressList: Format[Schema.NetworkAddressList] = Json.format[Schema.NetworkAddressList]
	given fmtNetworkAddress: Format[Schema.NetworkAddress] = Json.format[Schema.NetworkAddress]
	given fmtNetworkAddressAssignmentEnum: Format[Schema.NetworkAddress.AssignmentEnum] = JsonEnumFormat[Schema.NetworkAddress.AssignmentEnum]
	given fmtDiskEntryList: Format[Schema.DiskEntryList] = Json.format[Schema.DiskEntryList]
	given fmtDiskEntry: Format[Schema.DiskEntry] = Json.format[Schema.DiskEntry]
	given fmtDiskEntryInterfaceTypeEnum: Format[Schema.DiskEntry.InterfaceTypeEnum] = JsonEnumFormat[Schema.DiskEntry.InterfaceTypeEnum]
	given fmtDiskPartitionList: Format[Schema.DiskPartitionList] = Json.format[Schema.DiskPartitionList]
	given fmtVmwareDiskConfig: Format[Schema.VmwareDiskConfig] = Json.format[Schema.VmwareDiskConfig]
	given fmtDiskPartition: Format[Schema.DiskPartition] = Json.format[Schema.DiskPartition]
	given fmtVmwareDiskConfigBackingTypeEnum: Format[Schema.VmwareDiskConfig.BackingTypeEnum] = JsonEnumFormat[Schema.VmwareDiskConfig.BackingTypeEnum]
	given fmtVmwareDiskConfigVmdkModeEnum: Format[Schema.VmwareDiskConfig.VmdkModeEnum] = JsonEnumFormat[Schema.VmwareDiskConfig.VmdkModeEnum]
	given fmtVmwareDiskConfigRdmCompatibilityEnum: Format[Schema.VmwareDiskConfig.RdmCompatibilityEnum] = JsonEnumFormat[Schema.VmwareDiskConfig.RdmCompatibilityEnum]
	given fmtVmwarePlatformDetails: Format[Schema.VmwarePlatformDetails] = Json.format[Schema.VmwarePlatformDetails]
	given fmtAwsEc2PlatformDetails: Format[Schema.AwsEc2PlatformDetails] = Json.format[Schema.AwsEc2PlatformDetails]
	given fmtAzureVmPlatformDetails: Format[Schema.AzureVmPlatformDetails] = Json.format[Schema.AzureVmPlatformDetails]
	given fmtGenericPlatformDetails: Format[Schema.GenericPlatformDetails] = Json.format[Schema.GenericPlatformDetails]
	given fmtPhysicalPlatformDetails: Format[Schema.PhysicalPlatformDetails] = Json.format[Schema.PhysicalPlatformDetails]
	given fmtVmwarePlatformDetailsEsxHyperthreadingEnum: Format[Schema.VmwarePlatformDetails.EsxHyperthreadingEnum] = JsonEnumFormat[Schema.VmwarePlatformDetails.EsxHyperthreadingEnum]
	given fmtAwsEc2PlatformDetailsHyperthreadingEnum: Format[Schema.AwsEc2PlatformDetails.HyperthreadingEnum] = JsonEnumFormat[Schema.AwsEc2PlatformDetails.HyperthreadingEnum]
	given fmtAzureVmPlatformDetailsHyperthreadingEnum: Format[Schema.AzureVmPlatformDetails.HyperthreadingEnum] = JsonEnumFormat[Schema.AzureVmPlatformDetails.HyperthreadingEnum]
	given fmtGenericPlatformDetailsHyperthreadingEnum: Format[Schema.GenericPlatformDetails.HyperthreadingEnum] = JsonEnumFormat[Schema.GenericPlatformDetails.HyperthreadingEnum]
	given fmtPhysicalPlatformDetailsHyperthreadingEnum: Format[Schema.PhysicalPlatformDetails.HyperthreadingEnum] = JsonEnumFormat[Schema.PhysicalPlatformDetails.HyperthreadingEnum]
	given fmtInsight: Format[Schema.Insight] = Json.format[Schema.Insight]
	given fmtMigrationInsight: Format[Schema.MigrationInsight] = Json.format[Schema.MigrationInsight]
	given fmtGenericInsight: Format[Schema.GenericInsight] = Json.format[Schema.GenericInsight]
	given fmtFitDescriptor: Format[Schema.FitDescriptor] = Json.format[Schema.FitDescriptor]
	given fmtComputeEngineMigrationTarget: Format[Schema.ComputeEngineMigrationTarget] = Json.format[Schema.ComputeEngineMigrationTarget]
	given fmtFitDescriptorFitLevelEnum: Format[Schema.FitDescriptor.FitLevelEnum] = JsonEnumFormat[Schema.FitDescriptor.FitLevelEnum]
	given fmtComputeEngineShapeDescriptor: Format[Schema.ComputeEngineShapeDescriptor] = Json.format[Schema.ComputeEngineShapeDescriptor]
	given fmtComputeStorageDescriptor: Format[Schema.ComputeStorageDescriptor] = Json.format[Schema.ComputeStorageDescriptor]
	given fmtComputeStorageDescriptorTypeEnum: Format[Schema.ComputeStorageDescriptor.TypeEnum] = JsonEnumFormat[Schema.ComputeStorageDescriptor.TypeEnum]
	given fmtDailyResourceUsageAggregation: Format[Schema.DailyResourceUsageAggregation] = Json.format[Schema.DailyResourceUsageAggregation]
	given fmtDailyResourceUsageAggregationCPU: Format[Schema.DailyResourceUsageAggregationCPU] = Json.format[Schema.DailyResourceUsageAggregationCPU]
	given fmtDailyResourceUsageAggregationMemory: Format[Schema.DailyResourceUsageAggregationMemory] = Json.format[Schema.DailyResourceUsageAggregationMemory]
	given fmtDailyResourceUsageAggregationNetwork: Format[Schema.DailyResourceUsageAggregationNetwork] = Json.format[Schema.DailyResourceUsageAggregationNetwork]
	given fmtDailyResourceUsageAggregationDisk: Format[Schema.DailyResourceUsageAggregationDisk] = Json.format[Schema.DailyResourceUsageAggregationDisk]
	given fmtDailyResourceUsageAggregationStats: Format[Schema.DailyResourceUsageAggregationStats] = Json.format[Schema.DailyResourceUsageAggregationStats]
	given fmtBatchUpdateAssetsRequest: Format[Schema.BatchUpdateAssetsRequest] = Json.format[Schema.BatchUpdateAssetsRequest]
	given fmtUpdateAssetRequest: Format[Schema.UpdateAssetRequest] = Json.format[Schema.UpdateAssetRequest]
	given fmtBatchUpdateAssetsResponse: Format[Schema.BatchUpdateAssetsResponse] = Json.format[Schema.BatchUpdateAssetsResponse]
	given fmtBatchDeleteAssetsRequest: Format[Schema.BatchDeleteAssetsRequest] = Json.format[Schema.BatchDeleteAssetsRequest]
	given fmtFrames: Format[Schema.Frames] = Json.format[Schema.Frames]
	given fmtAssetFrame: Format[Schema.AssetFrame] = Json.format[Schema.AssetFrame]
	given fmtPerformanceSample: Format[Schema.PerformanceSample] = Json.format[Schema.PerformanceSample]
	given fmtAssetFrameCollectionTypeEnum: Format[Schema.AssetFrame.CollectionTypeEnum] = JsonEnumFormat[Schema.AssetFrame.CollectionTypeEnum]
	given fmtMemoryUsageSample: Format[Schema.MemoryUsageSample] = Json.format[Schema.MemoryUsageSample]
	given fmtCpuUsageSample: Format[Schema.CpuUsageSample] = Json.format[Schema.CpuUsageSample]
	given fmtNetworkUsageSample: Format[Schema.NetworkUsageSample] = Json.format[Schema.NetworkUsageSample]
	given fmtDiskUsageSample: Format[Schema.DiskUsageSample] = Json.format[Schema.DiskUsageSample]
	given fmtReportAssetFramesResponse: Format[Schema.ReportAssetFramesResponse] = Json.format[Schema.ReportAssetFramesResponse]
	given fmtAggregateAssetsValuesRequest: Format[Schema.AggregateAssetsValuesRequest] = Json.format[Schema.AggregateAssetsValuesRequest]
	given fmtAggregation: Format[Schema.Aggregation] = Json.format[Schema.Aggregation]
	given fmtAggregationCount: Format[Schema.AggregationCount] = Json.format[Schema.AggregationCount]
	given fmtAggregationSum: Format[Schema.AggregationSum] = Json.format[Schema.AggregationSum]
	given fmtAggregationHistogram: Format[Schema.AggregationHistogram] = Json.format[Schema.AggregationHistogram]
	given fmtAggregationFrequency: Format[Schema.AggregationFrequency] = Json.format[Schema.AggregationFrequency]
	given fmtAggregateAssetsValuesResponse: Format[Schema.AggregateAssetsValuesResponse] = Json.format[Schema.AggregateAssetsValuesResponse]
	given fmtAggregationResult: Format[Schema.AggregationResult] = Json.format[Schema.AggregationResult]
	given fmtAggregationResultCount: Format[Schema.AggregationResultCount] = Json.format[Schema.AggregationResultCount]
	given fmtAggregationResultSum: Format[Schema.AggregationResultSum] = Json.format[Schema.AggregationResultSum]
	given fmtAggregationResultHistogram: Format[Schema.AggregationResultHistogram] = Json.format[Schema.AggregationResultHistogram]
	given fmtAggregationResultFrequency: Format[Schema.AggregationResultFrequency] = Json.format[Schema.AggregationResultFrequency]
	given fmtAggregationResultHistogramBucket: Format[Schema.AggregationResultHistogramBucket] = Json.format[Schema.AggregationResultHistogramBucket]
	given fmtImportJob: Format[Schema.ImportJob] = Json.format[Schema.ImportJob]
	given fmtImportJobStateEnum: Format[Schema.ImportJob.StateEnum] = JsonEnumFormat[Schema.ImportJob.StateEnum]
	given fmtValidationReport: Format[Schema.ValidationReport] = Json.format[Schema.ValidationReport]
	given fmtExecutionReport: Format[Schema.ExecutionReport] = Json.format[Schema.ExecutionReport]
	given fmtFileValidationReport: Format[Schema.FileValidationReport] = Json.format[Schema.FileValidationReport]
	given fmtImportError: Format[Schema.ImportError] = Json.format[Schema.ImportError]
	given fmtImportRowError: Format[Schema.ImportRowError] = Json.format[Schema.ImportRowError]
	given fmtImportRowErrorCsvErrorDetails: Format[Schema.ImportRowErrorCsvErrorDetails] = Json.format[Schema.ImportRowErrorCsvErrorDetails]
	given fmtImportRowErrorXlsxErrorDetails: Format[Schema.ImportRowErrorXlsxErrorDetails] = Json.format[Schema.ImportRowErrorXlsxErrorDetails]
	given fmtImportErrorSeverityEnum: Format[Schema.ImportError.SeverityEnum] = JsonEnumFormat[Schema.ImportError.SeverityEnum]
	given fmtListImportJobsResponse: Format[Schema.ListImportJobsResponse] = Json.format[Schema.ListImportJobsResponse]
	given fmtValidateImportJobRequest: Format[Schema.ValidateImportJobRequest] = Json.format[Schema.ValidateImportJobRequest]
	given fmtRunImportJobRequest: Format[Schema.RunImportJobRequest] = Json.format[Schema.RunImportJobRequest]
	given fmtImportDataFile: Format[Schema.ImportDataFile] = Json.format[Schema.ImportDataFile]
	given fmtImportDataFileFormatEnum: Format[Schema.ImportDataFile.FormatEnum] = JsonEnumFormat[Schema.ImportDataFile.FormatEnum]
	given fmtImportDataFileStateEnum: Format[Schema.ImportDataFile.StateEnum] = JsonEnumFormat[Schema.ImportDataFile.StateEnum]
	given fmtUploadFileInfo: Format[Schema.UploadFileInfo] = Json.format[Schema.UploadFileInfo]
	given fmtListImportDataFilesResponse: Format[Schema.ListImportDataFilesResponse] = Json.format[Schema.ListImportDataFilesResponse]
	given fmtListGroupsResponse: Format[Schema.ListGroupsResponse] = Json.format[Schema.ListGroupsResponse]
	given fmtGroup: Format[Schema.Group] = Json.format[Schema.Group]
	given fmtAddAssetsToGroupRequest: Format[Schema.AddAssetsToGroupRequest] = Json.format[Schema.AddAssetsToGroupRequest]
	given fmtAssetList: Format[Schema.AssetList] = Json.format[Schema.AssetList]
	given fmtRemoveAssetsFromGroupRequest: Format[Schema.RemoveAssetsFromGroupRequest] = Json.format[Schema.RemoveAssetsFromGroupRequest]
	given fmtListErrorFramesResponse: Format[Schema.ListErrorFramesResponse] = Json.format[Schema.ListErrorFramesResponse]
	given fmtErrorFrame: Format[Schema.ErrorFrame] = Json.format[Schema.ErrorFrame]
	given fmtFrameViolationEntry: Format[Schema.FrameViolationEntry] = Json.format[Schema.FrameViolationEntry]
	given fmtListSourcesResponse: Format[Schema.ListSourcesResponse] = Json.format[Schema.ListSourcesResponse]
	given fmtSource: Format[Schema.Source] = Json.format[Schema.Source]
	given fmtSourceTypeEnum: Format[Schema.Source.TypeEnum] = JsonEnumFormat[Schema.Source.TypeEnum]
	given fmtSourceStateEnum: Format[Schema.Source.StateEnum] = JsonEnumFormat[Schema.Source.StateEnum]
	given fmtListPreferenceSetsResponse: Format[Schema.ListPreferenceSetsResponse] = Json.format[Schema.ListPreferenceSetsResponse]
	given fmtPreferenceSet: Format[Schema.PreferenceSet] = Json.format[Schema.PreferenceSet]
	given fmtVirtualMachinePreferences: Format[Schema.VirtualMachinePreferences] = Json.format[Schema.VirtualMachinePreferences]
	given fmtVirtualMachinePreferencesTargetProductEnum: Format[Schema.VirtualMachinePreferences.TargetProductEnum] = JsonEnumFormat[Schema.VirtualMachinePreferences.TargetProductEnum]
	given fmtRegionPreferences: Format[Schema.RegionPreferences] = Json.format[Schema.RegionPreferences]
	given fmtVirtualMachinePreferencesCommitmentPlanEnum: Format[Schema.VirtualMachinePreferences.CommitmentPlanEnum] = JsonEnumFormat[Schema.VirtualMachinePreferences.CommitmentPlanEnum]
	given fmtVirtualMachinePreferencesSizingOptimizationStrategyEnum: Format[Schema.VirtualMachinePreferences.SizingOptimizationStrategyEnum] = JsonEnumFormat[Schema.VirtualMachinePreferences.SizingOptimizationStrategyEnum]
	given fmtComputeEnginePreferences: Format[Schema.ComputeEnginePreferences] = Json.format[Schema.ComputeEnginePreferences]
	given fmtVmwareEnginePreferences: Format[Schema.VmwareEnginePreferences] = Json.format[Schema.VmwareEnginePreferences]
	given fmtSoleTenancyPreferences: Format[Schema.SoleTenancyPreferences] = Json.format[Schema.SoleTenancyPreferences]
	given fmtComputeEnginePreferencesPersistentDiskTypeEnum: Format[Schema.ComputeEnginePreferences.PersistentDiskTypeEnum] = JsonEnumFormat[Schema.ComputeEnginePreferences.PersistentDiskTypeEnum]
	given fmtMachinePreferences: Format[Schema.MachinePreferences] = Json.format[Schema.MachinePreferences]
	given fmtComputeEnginePreferencesLicenseTypeEnum: Format[Schema.ComputeEnginePreferences.LicenseTypeEnum] = JsonEnumFormat[Schema.ComputeEnginePreferences.LicenseTypeEnum]
	given fmtMachineSeries: Format[Schema.MachineSeries] = Json.format[Schema.MachineSeries]
	given fmtVmwareEnginePreferencesCommitmentPlanEnum: Format[Schema.VmwareEnginePreferences.CommitmentPlanEnum] = JsonEnumFormat[Schema.VmwareEnginePreferences.CommitmentPlanEnum]
	given fmtSoleTenancyPreferencesHostMaintenancePolicyEnum: Format[Schema.SoleTenancyPreferences.HostMaintenancePolicyEnum] = JsonEnumFormat[Schema.SoleTenancyPreferences.HostMaintenancePolicyEnum]
	given fmtSoleTenancyPreferencesCommitmentPlanEnum: Format[Schema.SoleTenancyPreferences.CommitmentPlanEnum] = JsonEnumFormat[Schema.SoleTenancyPreferences.CommitmentPlanEnum]
	given fmtSoleTenantNodeType: Format[Schema.SoleTenantNodeType] = Json.format[Schema.SoleTenantNodeType]
	given fmtSettings: Format[Schema.Settings] = Json.format[Schema.Settings]
	given fmtReportConfig: Format[Schema.ReportConfig] = Json.format[Schema.ReportConfig]
	given fmtReportConfigGroupPreferenceSetAssignment: Format[Schema.ReportConfigGroupPreferenceSetAssignment] = Json.format[Schema.ReportConfigGroupPreferenceSetAssignment]
	given fmtListReportConfigsResponse: Format[Schema.ListReportConfigsResponse] = Json.format[Schema.ListReportConfigsResponse]
	given fmtReport: Format[Schema.Report] = Json.format[Schema.Report]
	given fmtReportTypeEnum: Format[Schema.Report.TypeEnum] = JsonEnumFormat[Schema.Report.TypeEnum]
	given fmtReportStateEnum: Format[Schema.Report.StateEnum] = JsonEnumFormat[Schema.Report.StateEnum]
	given fmtReportSummary: Format[Schema.ReportSummary] = Json.format[Schema.ReportSummary]
	given fmtReportSummaryAssetAggregateStats: Format[Schema.ReportSummaryAssetAggregateStats] = Json.format[Schema.ReportSummaryAssetAggregateStats]
	given fmtReportSummaryGroupFinding: Format[Schema.ReportSummaryGroupFinding] = Json.format[Schema.ReportSummaryGroupFinding]
	given fmtReportSummaryUtilizationChartData: Format[Schema.ReportSummaryUtilizationChartData] = Json.format[Schema.ReportSummaryUtilizationChartData]
	given fmtReportSummaryChartData: Format[Schema.ReportSummaryChartData] = Json.format[Schema.ReportSummaryChartData]
	given fmtReportSummaryHistogramChartData: Format[Schema.ReportSummaryHistogramChartData] = Json.format[Schema.ReportSummaryHistogramChartData]
	given fmtReportSummaryChartDataDataPoint: Format[Schema.ReportSummaryChartDataDataPoint] = Json.format[Schema.ReportSummaryChartDataDataPoint]
	given fmtReportSummaryHistogramChartDataBucket: Format[Schema.ReportSummaryHistogramChartDataBucket] = Json.format[Schema.ReportSummaryHistogramChartDataBucket]
	given fmtReportSummaryGroupPreferenceSetFinding: Format[Schema.ReportSummaryGroupPreferenceSetFinding] = Json.format[Schema.ReportSummaryGroupPreferenceSetFinding]
	given fmtMoney: Format[Schema.Money] = Json.format[Schema.Money]
	given fmtReportSummaryComputeEngineFinding: Format[Schema.ReportSummaryComputeEngineFinding] = Json.format[Schema.ReportSummaryComputeEngineFinding]
	given fmtReportSummaryVmwareEngineFinding: Format[Schema.ReportSummaryVmwareEngineFinding] = Json.format[Schema.ReportSummaryVmwareEngineFinding]
	given fmtReportSummarySoleTenantFinding: Format[Schema.ReportSummarySoleTenantFinding] = Json.format[Schema.ReportSummarySoleTenantFinding]
	given fmtReportSummaryMachineSeriesAllocation: Format[Schema.ReportSummaryMachineSeriesAllocation] = Json.format[Schema.ReportSummaryMachineSeriesAllocation]
	given fmtReportSummaryComputeEngineFindingAllocatedDiskTypesEnum: Format[Schema.ReportSummaryComputeEngineFinding.AllocatedDiskTypesEnum] = JsonEnumFormat[Schema.ReportSummaryComputeEngineFinding.AllocatedDiskTypesEnum]
	given fmtReportSummaryVmwareNodeAllocation: Format[Schema.ReportSummaryVmwareNodeAllocation] = Json.format[Schema.ReportSummaryVmwareNodeAllocation]
	given fmtReportSummaryVmwareNode: Format[Schema.ReportSummaryVmwareNode] = Json.format[Schema.ReportSummaryVmwareNode]
	given fmtReportSummarySoleTenantNodeAllocation: Format[Schema.ReportSummarySoleTenantNodeAllocation] = Json.format[Schema.ReportSummarySoleTenantNodeAllocation]
	given fmtListReportsResponse: Format[Schema.ListReportsResponse] = Json.format[Schema.ListReportsResponse]
	given fmtDiscoveryClient: Format[Schema.DiscoveryClient] = Json.format[Schema.DiscoveryClient]
	given fmtDiscoveryClientStateEnum: Format[Schema.DiscoveryClient.StateEnum] = JsonEnumFormat[Schema.DiscoveryClient.StateEnum]
	given fmtListDiscoveryClientsResponse: Format[Schema.ListDiscoveryClientsResponse] = Json.format[Schema.ListDiscoveryClientsResponse]
	given fmtSendDiscoveryClientHeartbeatRequest: Format[Schema.SendDiscoveryClientHeartbeatRequest] = Json.format[Schema.SendDiscoveryClientHeartbeatRequest]
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
}
