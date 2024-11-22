package com.boresjo.play.api.google.looker

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
	given fmtListInstancesResponse: Format[Schema.ListInstancesResponse] = Json.format[Schema.ListInstancesResponse]
	given fmtInstance: Format[Schema.Instance] = Json.format[Schema.Instance]
	given fmtInstanceStateEnum: Format[Schema.Instance.StateEnum] = JsonEnumFormat[Schema.Instance.StateEnum]
	given fmtInstancePlatformEditionEnum: Format[Schema.Instance.PlatformEditionEnum] = JsonEnumFormat[Schema.Instance.PlatformEditionEnum]
	given fmtPscConfig: Format[Schema.PscConfig] = Json.format[Schema.PscConfig]
	given fmtMaintenanceWindow: Format[Schema.MaintenanceWindow] = Json.format[Schema.MaintenanceWindow]
	given fmtDenyMaintenancePeriod: Format[Schema.DenyMaintenancePeriod] = Json.format[Schema.DenyMaintenancePeriod]
	given fmtMaintenanceSchedule: Format[Schema.MaintenanceSchedule] = Json.format[Schema.MaintenanceSchedule]
	given fmtUserMetadata: Format[Schema.UserMetadata] = Json.format[Schema.UserMetadata]
	given fmtCustomDomain: Format[Schema.CustomDomain] = Json.format[Schema.CustomDomain]
	given fmtEncryptionConfig: Format[Schema.EncryptionConfig] = Json.format[Schema.EncryptionConfig]
	given fmtAdminSettings: Format[Schema.AdminSettings] = Json.format[Schema.AdminSettings]
	given fmtOAuthConfig: Format[Schema.OAuthConfig] = Json.format[Schema.OAuthConfig]
	given fmtServiceAttachment: Format[Schema.ServiceAttachment] = Json.format[Schema.ServiceAttachment]
	given fmtServiceAttachmentConnectionStatusEnum: Format[Schema.ServiceAttachment.ConnectionStatusEnum] = JsonEnumFormat[Schema.ServiceAttachment.ConnectionStatusEnum]
	given fmtMaintenanceWindowDayOfWeekEnum: Format[Schema.MaintenanceWindow.DayOfWeekEnum] = JsonEnumFormat[Schema.MaintenanceWindow.DayOfWeekEnum]
	given fmtTimeOfDay: Format[Schema.TimeOfDay] = Json.format[Schema.TimeOfDay]
	given fmtDate: Format[Schema.Date] = Json.format[Schema.Date]
	given fmtCustomDomainStateEnum: Format[Schema.CustomDomain.StateEnum] = JsonEnumFormat[Schema.CustomDomain.StateEnum]
	given fmtEncryptionConfigKmsKeyStateEnum: Format[Schema.EncryptionConfig.KmsKeyStateEnum] = JsonEnumFormat[Schema.EncryptionConfig.KmsKeyStateEnum]
	given fmtRestartInstanceRequest: Format[Schema.RestartInstanceRequest] = Json.format[Schema.RestartInstanceRequest]
	given fmtImportInstanceRequest: Format[Schema.ImportInstanceRequest] = Json.format[Schema.ImportInstanceRequest]
	given fmtExportInstanceRequest: Format[Schema.ExportInstanceRequest] = Json.format[Schema.ExportInstanceRequest]
	given fmtExportEncryptionConfig: Format[Schema.ExportEncryptionConfig] = Json.format[Schema.ExportEncryptionConfig]
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtSetIamPolicyRequest: Format[Schema.SetIamPolicyRequest] = Json.format[Schema.SetIamPolicyRequest]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtBinding: Format[Schema.Binding] = Json.format[Schema.Binding]
	given fmtAuditConfig: Format[Schema.AuditConfig] = Json.format[Schema.AuditConfig]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtAuditLogConfig: Format[Schema.AuditLogConfig] = Json.format[Schema.AuditLogConfig]
	given fmtAuditLogConfigLogTypeEnum: Format[Schema.AuditLogConfig.LogTypeEnum] = JsonEnumFormat[Schema.AuditLogConfig.LogTypeEnum]
	given fmtTestIamPermissionsRequest: Format[Schema.TestIamPermissionsRequest] = Json.format[Schema.TestIamPermissionsRequest]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
	given fmtExportMetadata: Format[Schema.ExportMetadata] = Json.format[Schema.ExportMetadata]
	given fmtExportMetadataEncryptionKey: Format[Schema.ExportMetadataEncryptionKey] = Json.format[Schema.ExportMetadataEncryptionKey]
	given fmtExportMetadataSourceEnum: Format[Schema.ExportMetadata.SourceEnum] = JsonEnumFormat[Schema.ExportMetadata.SourceEnum]
}
