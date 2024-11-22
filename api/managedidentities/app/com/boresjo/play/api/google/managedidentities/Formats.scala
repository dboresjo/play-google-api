package com.boresjo.play.api.google.managedidentities

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

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
	given fmtDomain: Format[Schema.Domain] = Json.format[Schema.Domain]
	given fmtDomainStateEnum: Format[Schema.Domain.StateEnum] = JsonEnumFormat[Schema.Domain.StateEnum]
	given fmtTrust: Format[Schema.Trust] = Json.format[Schema.Trust]
	given fmtTrustTrustTypeEnum: Format[Schema.Trust.TrustTypeEnum] = JsonEnumFormat[Schema.Trust.TrustTypeEnum]
	given fmtTrustTrustDirectionEnum: Format[Schema.Trust.TrustDirectionEnum] = JsonEnumFormat[Schema.Trust.TrustDirectionEnum]
	given fmtTrustStateEnum: Format[Schema.Trust.StateEnum] = JsonEnumFormat[Schema.Trust.StateEnum]
	given fmtResetAdminPasswordRequest: Format[Schema.ResetAdminPasswordRequest] = Json.format[Schema.ResetAdminPasswordRequest]
	given fmtResetAdminPasswordResponse: Format[Schema.ResetAdminPasswordResponse] = Json.format[Schema.ResetAdminPasswordResponse]
	given fmtListDomainsResponse: Format[Schema.ListDomainsResponse] = Json.format[Schema.ListDomainsResponse]
	given fmtDomainJoinMachineRequest: Format[Schema.DomainJoinMachineRequest] = Json.format[Schema.DomainJoinMachineRequest]
	given fmtDomainJoinMachineResponse: Format[Schema.DomainJoinMachineResponse] = Json.format[Schema.DomainJoinMachineResponse]
	given fmtRestoreDomainRequest: Format[Schema.RestoreDomainRequest] = Json.format[Schema.RestoreDomainRequest]
	given fmtAttachTrustRequest: Format[Schema.AttachTrustRequest] = Json.format[Schema.AttachTrustRequest]
	given fmtReconfigureTrustRequest: Format[Schema.ReconfigureTrustRequest] = Json.format[Schema.ReconfigureTrustRequest]
	given fmtDetachTrustRequest: Format[Schema.DetachTrustRequest] = Json.format[Schema.DetachTrustRequest]
	given fmtValidateTrustRequest: Format[Schema.ValidateTrustRequest] = Json.format[Schema.ValidateTrustRequest]
	given fmtLDAPSSettings: Format[Schema.LDAPSSettings] = Json.format[Schema.LDAPSSettings]
	given fmtCertificate: Format[Schema.Certificate] = Json.format[Schema.Certificate]
	given fmtLDAPSSettingsStateEnum: Format[Schema.LDAPSSettings.StateEnum] = JsonEnumFormat[Schema.LDAPSSettings.StateEnum]
	given fmtPeering: Format[Schema.Peering] = Json.format[Schema.Peering]
	given fmtPeeringStateEnum: Format[Schema.Peering.StateEnum] = JsonEnumFormat[Schema.Peering.StateEnum]
	given fmtListPeeringsResponse: Format[Schema.ListPeeringsResponse] = Json.format[Schema.ListPeeringsResponse]
	given fmtBackup: Format[Schema.Backup] = Json.format[Schema.Backup]
	given fmtBackupTypeEnum: Format[Schema.Backup.TypeEnum] = JsonEnumFormat[Schema.Backup.TypeEnum]
	given fmtBackupStateEnum: Format[Schema.Backup.StateEnum] = JsonEnumFormat[Schema.Backup.StateEnum]
	given fmtListBackupsResponse: Format[Schema.ListBackupsResponse] = Json.format[Schema.ListBackupsResponse]
	given fmtListSqlIntegrationsResponse: Format[Schema.ListSqlIntegrationsResponse] = Json.format[Schema.ListSqlIntegrationsResponse]
	given fmtSqlIntegration: Format[Schema.SqlIntegration] = Json.format[Schema.SqlIntegration]
	given fmtSqlIntegrationStateEnum: Format[Schema.SqlIntegration.StateEnum] = JsonEnumFormat[Schema.SqlIntegration.StateEnum]
	given fmtExtendSchemaRequest: Format[Schema.ExtendSchemaRequest] = Json.format[Schema.ExtendSchemaRequest]
	given fmtEnableMigrationRequest: Format[Schema.EnableMigrationRequest] = Json.format[Schema.EnableMigrationRequest]
	given fmtOnPremDomainDetails: Format[Schema.OnPremDomainDetails] = Json.format[Schema.OnPremDomainDetails]
	given fmtDisableMigrationRequest: Format[Schema.DisableMigrationRequest] = Json.format[Schema.DisableMigrationRequest]
	given fmtCheckMigrationPermissionRequest: Format[Schema.CheckMigrationPermissionRequest] = Json.format[Schema.CheckMigrationPermissionRequest]
	given fmtCheckMigrationPermissionResponse: Format[Schema.CheckMigrationPermissionResponse] = Json.format[Schema.CheckMigrationPermissionResponse]
	given fmtCheckMigrationPermissionResponseStateEnum: Format[Schema.CheckMigrationPermissionResponse.StateEnum] = JsonEnumFormat[Schema.CheckMigrationPermissionResponse.StateEnum]
	given fmtOnPremDomainSIDDetails: Format[Schema.OnPremDomainSIDDetails] = Json.format[Schema.OnPremDomainSIDDetails]
	given fmtOnPremDomainSIDDetailsSidFilteringStateEnum: Format[Schema.OnPremDomainSIDDetails.SidFilteringStateEnum] = JsonEnumFormat[Schema.OnPremDomainSIDDetails.SidFilteringStateEnum]
	given fmtSetIamPolicyRequest: Format[Schema.SetIamPolicyRequest] = Json.format[Schema.SetIamPolicyRequest]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtBinding: Format[Schema.Binding] = Json.format[Schema.Binding]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtTestIamPermissionsRequest: Format[Schema.TestIamPermissionsRequest] = Json.format[Schema.TestIamPermissionsRequest]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
	given fmtGoogleCloudManagedidentitiesV1OpMetadata: Format[Schema.GoogleCloudManagedidentitiesV1OpMetadata] = Json.format[Schema.GoogleCloudManagedidentitiesV1OpMetadata]
	given fmtGoogleCloudManagedidentitiesV1alpha1OpMetadata: Format[Schema.GoogleCloudManagedidentitiesV1alpha1OpMetadata] = Json.format[Schema.GoogleCloudManagedidentitiesV1alpha1OpMetadata]
	given fmtGoogleCloudManagedidentitiesV1beta1OpMetadata: Format[Schema.GoogleCloudManagedidentitiesV1beta1OpMetadata] = Json.format[Schema.GoogleCloudManagedidentitiesV1beta1OpMetadata]
	given fmtGoogleCloudSaasacceleratorManagementProvidersV1Instance: Format[Schema.GoogleCloudSaasacceleratorManagementProvidersV1Instance] = Json.format[Schema.GoogleCloudSaasacceleratorManagementProvidersV1Instance]
	given fmtGoogleCloudSaasacceleratorManagementProvidersV1InstanceStateEnum: Format[Schema.GoogleCloudSaasacceleratorManagementProvidersV1Instance.StateEnum] = JsonEnumFormat[Schema.GoogleCloudSaasacceleratorManagementProvidersV1Instance.StateEnum]
	given fmtGoogleCloudSaasacceleratorManagementProvidersV1ProvisionedResource: Format[Schema.GoogleCloudSaasacceleratorManagementProvidersV1ProvisionedResource] = Json.format[Schema.GoogleCloudSaasacceleratorManagementProvidersV1ProvisionedResource]
	given fmtGoogleCloudSaasacceleratorManagementProvidersV1SloMetadata: Format[Schema.GoogleCloudSaasacceleratorManagementProvidersV1SloMetadata] = Json.format[Schema.GoogleCloudSaasacceleratorManagementProvidersV1SloMetadata]
	given fmtGoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSchedule: Format[Schema.GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSchedule] = Json.format[Schema.GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSchedule]
	given fmtGoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSettings: Format[Schema.GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSettings] = Json.format[Schema.GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSettings]
	given fmtGoogleCloudSaasacceleratorManagementProvidersV1NotificationParameter: Format[Schema.GoogleCloudSaasacceleratorManagementProvidersV1NotificationParameter] = Json.format[Schema.GoogleCloudSaasacceleratorManagementProvidersV1NotificationParameter]
	given fmtGoogleCloudSaasacceleratorManagementProvidersV1NodeSloMetadata: Format[Schema.GoogleCloudSaasacceleratorManagementProvidersV1NodeSloMetadata] = Json.format[Schema.GoogleCloudSaasacceleratorManagementProvidersV1NodeSloMetadata]
	given fmtGoogleCloudSaasacceleratorManagementProvidersV1PerSliSloEligibility: Format[Schema.GoogleCloudSaasacceleratorManagementProvidersV1PerSliSloEligibility] = Json.format[Schema.GoogleCloudSaasacceleratorManagementProvidersV1PerSliSloEligibility]
	given fmtGoogleCloudSaasacceleratorManagementProvidersV1SloEligibility: Format[Schema.GoogleCloudSaasacceleratorManagementProvidersV1SloEligibility] = Json.format[Schema.GoogleCloudSaasacceleratorManagementProvidersV1SloEligibility]
	given fmtMaintenancePolicy: Format[Schema.MaintenancePolicy] = Json.format[Schema.MaintenancePolicy]
	given fmtMaintenancePolicyStateEnum: Format[Schema.MaintenancePolicy.StateEnum] = JsonEnumFormat[Schema.MaintenancePolicy.StateEnum]
	given fmtUpdatePolicy: Format[Schema.UpdatePolicy] = Json.format[Schema.UpdatePolicy]
	given fmtMaintenanceWindow: Format[Schema.MaintenanceWindow] = Json.format[Schema.MaintenanceWindow]
	given fmtUpdatePolicyChannelEnum: Format[Schema.UpdatePolicy.ChannelEnum] = JsonEnumFormat[Schema.UpdatePolicy.ChannelEnum]
	given fmtDenyMaintenancePeriod: Format[Schema.DenyMaintenancePeriod] = Json.format[Schema.DenyMaintenancePeriod]
	given fmtDailyCycle: Format[Schema.DailyCycle] = Json.format[Schema.DailyCycle]
	given fmtWeeklyCycle: Format[Schema.WeeklyCycle] = Json.format[Schema.WeeklyCycle]
	given fmtTimeOfDay: Format[Schema.TimeOfDay] = Json.format[Schema.TimeOfDay]
	given fmtSchedule: Format[Schema.Schedule] = Json.format[Schema.Schedule]
	given fmtScheduleDayEnum: Format[Schema.Schedule.DayEnum] = JsonEnumFormat[Schema.Schedule.DayEnum]
	given fmtDate: Format[Schema.Date] = Json.format[Schema.Date]
}
