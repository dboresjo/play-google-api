package com.boresjo.play.api.google.notebooks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtListInstancesResponse: Format[Schema.ListInstancesResponse] = Json.format[Schema.ListInstancesResponse]
	given fmtInstance: Format[Schema.Instance] = Json.format[Schema.Instance]
	given fmtGceSetup: Format[Schema.GceSetup] = Json.format[Schema.GceSetup]
	given fmtInstanceStateEnum: Format[Schema.Instance.StateEnum] = JsonEnumFormat[Schema.Instance.StateEnum]
	given fmtUpgradeHistoryEntry: Format[Schema.UpgradeHistoryEntry] = Json.format[Schema.UpgradeHistoryEntry]
	given fmtInstanceHealthStateEnum: Format[Schema.Instance.HealthStateEnum] = JsonEnumFormat[Schema.Instance.HealthStateEnum]
	given fmtAcceleratorConfig: Format[Schema.AcceleratorConfig] = Json.format[Schema.AcceleratorConfig]
	given fmtServiceAccount: Format[Schema.ServiceAccount] = Json.format[Schema.ServiceAccount]
	given fmtVmImage: Format[Schema.VmImage] = Json.format[Schema.VmImage]
	given fmtContainerImage: Format[Schema.ContainerImage] = Json.format[Schema.ContainerImage]
	given fmtBootDisk: Format[Schema.BootDisk] = Json.format[Schema.BootDisk]
	given fmtDataDisk: Format[Schema.DataDisk] = Json.format[Schema.DataDisk]
	given fmtShieldedInstanceConfig: Format[Schema.ShieldedInstanceConfig] = Json.format[Schema.ShieldedInstanceConfig]
	given fmtNetworkInterface: Format[Schema.NetworkInterface] = Json.format[Schema.NetworkInterface]
	given fmtGPUDriverConfig: Format[Schema.GPUDriverConfig] = Json.format[Schema.GPUDriverConfig]
	given fmtAcceleratorConfigTypeEnum: Format[Schema.AcceleratorConfig.TypeEnum] = JsonEnumFormat[Schema.AcceleratorConfig.TypeEnum]
	given fmtBootDiskDiskTypeEnum: Format[Schema.BootDisk.DiskTypeEnum] = JsonEnumFormat[Schema.BootDisk.DiskTypeEnum]
	given fmtBootDiskDiskEncryptionEnum: Format[Schema.BootDisk.DiskEncryptionEnum] = JsonEnumFormat[Schema.BootDisk.DiskEncryptionEnum]
	given fmtDataDiskDiskTypeEnum: Format[Schema.DataDisk.DiskTypeEnum] = JsonEnumFormat[Schema.DataDisk.DiskTypeEnum]
	given fmtDataDiskDiskEncryptionEnum: Format[Schema.DataDisk.DiskEncryptionEnum] = JsonEnumFormat[Schema.DataDisk.DiskEncryptionEnum]
	given fmtNetworkInterfaceNicTypeEnum: Format[Schema.NetworkInterface.NicTypeEnum] = JsonEnumFormat[Schema.NetworkInterface.NicTypeEnum]
	given fmtAccessConfig: Format[Schema.AccessConfig] = Json.format[Schema.AccessConfig]
	given fmtUpgradeHistoryEntryStateEnum: Format[Schema.UpgradeHistoryEntry.StateEnum] = JsonEnumFormat[Schema.UpgradeHistoryEntry.StateEnum]
	given fmtUpgradeHistoryEntryActionEnum: Format[Schema.UpgradeHistoryEntry.ActionEnum] = JsonEnumFormat[Schema.UpgradeHistoryEntry.ActionEnum]
	given fmtStartInstanceRequest: Format[Schema.StartInstanceRequest] = Json.format[Schema.StartInstanceRequest]
	given fmtStopInstanceRequest: Format[Schema.StopInstanceRequest] = Json.format[Schema.StopInstanceRequest]
	given fmtResetInstanceRequest: Format[Schema.ResetInstanceRequest] = Json.format[Schema.ResetInstanceRequest]
	given fmtCheckInstanceUpgradabilityResponse: Format[Schema.CheckInstanceUpgradabilityResponse] = Json.format[Schema.CheckInstanceUpgradabilityResponse]
	given fmtUpgradeInstanceRequest: Format[Schema.UpgradeInstanceRequest] = Json.format[Schema.UpgradeInstanceRequest]
	given fmtResizeDiskRequest: Format[Schema.ResizeDiskRequest] = Json.format[Schema.ResizeDiskRequest]
	given fmtRollbackInstanceRequest: Format[Schema.RollbackInstanceRequest] = Json.format[Schema.RollbackInstanceRequest]
	given fmtDiagnoseInstanceRequest: Format[Schema.DiagnoseInstanceRequest] = Json.format[Schema.DiagnoseInstanceRequest]
	given fmtDiagnosticConfig: Format[Schema.DiagnosticConfig] = Json.format[Schema.DiagnosticConfig]
	given fmtConfig: Format[Schema.Config] = Json.format[Schema.Config]
	given fmtDefaultValues: Format[Schema.DefaultValues] = Json.format[Schema.DefaultValues]
	given fmtSupportedValues: Format[Schema.SupportedValues] = Json.format[Schema.SupportedValues]
	given fmtImageRelease: Format[Schema.ImageRelease] = Json.format[Schema.ImageRelease]
	given fmtRestoreInstanceRequest: Format[Schema.RestoreInstanceRequest] = Json.format[Schema.RestoreInstanceRequest]
	given fmtSnapshot: Format[Schema.Snapshot] = Json.format[Schema.Snapshot]
	given fmtReportInstanceInfoSystemRequest: Format[Schema.ReportInstanceInfoSystemRequest] = Json.format[Schema.ReportInstanceInfoSystemRequest]
	given fmtEvent: Format[Schema.Event] = Json.format[Schema.Event]
	given fmtEventTypeEnum: Format[Schema.Event.TypeEnum] = JsonEnumFormat[Schema.Event.TypeEnum]
	given fmtUpgradeInstanceSystemRequest: Format[Schema.UpgradeInstanceSystemRequest] = Json.format[Schema.UpgradeInstanceSystemRequest]
	given fmtSetIamPolicyRequest: Format[Schema.SetIamPolicyRequest] = Json.format[Schema.SetIamPolicyRequest]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtBinding: Format[Schema.Binding] = Json.format[Schema.Binding]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtTestIamPermissionsRequest: Format[Schema.TestIamPermissionsRequest] = Json.format[Schema.TestIamPermissionsRequest]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
}