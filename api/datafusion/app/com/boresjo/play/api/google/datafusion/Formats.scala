package com.boresjo.play.api.google.datafusion

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
	given fmtListAvailableVersionsResponse: Format[Schema.ListAvailableVersionsResponse] = Json.format[Schema.ListAvailableVersionsResponse]
	given fmtVersion: Format[Schema.Version] = Json.format[Schema.Version]
	given fmtVersionTypeEnum: Format[Schema.Version.TypeEnum] = JsonEnumFormat[Schema.Version.TypeEnum]
	given fmtListInstancesResponse: Format[Schema.ListInstancesResponse] = Json.format[Schema.ListInstancesResponse]
	given fmtInstance: Format[Schema.Instance] = Json.format[Schema.Instance]
	given fmtInstanceTypeEnum: Format[Schema.Instance.TypeEnum] = JsonEnumFormat[Schema.Instance.TypeEnum]
	given fmtNetworkConfig: Format[Schema.NetworkConfig] = Json.format[Schema.NetworkConfig]
	given fmtInstanceStateEnum: Format[Schema.Instance.StateEnum] = JsonEnumFormat[Schema.Instance.StateEnum]
	given fmtAccelerator: Format[Schema.Accelerator] = Json.format[Schema.Accelerator]
	given fmtCryptoKeyConfig: Format[Schema.CryptoKeyConfig] = Json.format[Schema.CryptoKeyConfig]
	given fmtInstanceDisabledReasonEnum: Format[Schema.Instance.DisabledReasonEnum] = JsonEnumFormat[Schema.Instance.DisabledReasonEnum]
	given fmtEventPublishConfig: Format[Schema.EventPublishConfig] = Json.format[Schema.EventPublishConfig]
	given fmtMaintenancePolicy: Format[Schema.MaintenancePolicy] = Json.format[Schema.MaintenancePolicy]
	given fmtNetworkConfigConnectionTypeEnum: Format[Schema.NetworkConfig.ConnectionTypeEnum] = JsonEnumFormat[Schema.NetworkConfig.ConnectionTypeEnum]
	given fmtPrivateServiceConnectConfig: Format[Schema.PrivateServiceConnectConfig] = Json.format[Schema.PrivateServiceConnectConfig]
	given fmtAcceleratorAcceleratorTypeEnum: Format[Schema.Accelerator.AcceleratorTypeEnum] = JsonEnumFormat[Schema.Accelerator.AcceleratorTypeEnum]
	given fmtAcceleratorStateEnum: Format[Schema.Accelerator.StateEnum] = JsonEnumFormat[Schema.Accelerator.StateEnum]
	given fmtMaintenanceWindow: Format[Schema.MaintenanceWindow] = Json.format[Schema.MaintenanceWindow]
	given fmtTimeWindow: Format[Schema.TimeWindow] = Json.format[Schema.TimeWindow]
	given fmtRecurringTimeWindow: Format[Schema.RecurringTimeWindow] = Json.format[Schema.RecurringTimeWindow]
	given fmtRestartInstanceRequest: Format[Schema.RestartInstanceRequest] = Json.format[Schema.RestartInstanceRequest]
	given fmtDnsPeering: Format[Schema.DnsPeering] = Json.format[Schema.DnsPeering]
	given fmtListDnsPeeringsResponse: Format[Schema.ListDnsPeeringsResponse] = Json.format[Schema.ListDnsPeeringsResponse]
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
	given fmtAssetLocation: Format[Schema.AssetLocation] = Json.format[Schema.AssetLocation]
	given fmtIsolationExpectations: Format[Schema.IsolationExpectations] = Json.format[Schema.IsolationExpectations]
	given fmtLocationData: Format[Schema.LocationData] = Json.format[Schema.LocationData]
	given fmtCloudAsset: Format[Schema.CloudAsset] = Json.format[Schema.CloudAsset]
	given fmtExtraParameter: Format[Schema.ExtraParameter] = Json.format[Schema.ExtraParameter]
	given fmtIsolationExpectationsZoneIsolationEnum: Format[Schema.IsolationExpectations.ZoneIsolationEnum] = JsonEnumFormat[Schema.IsolationExpectations.ZoneIsolationEnum]
	given fmtIsolationExpectationsZoneSeparationEnum: Format[Schema.IsolationExpectations.ZoneSeparationEnum] = JsonEnumFormat[Schema.IsolationExpectations.ZoneSeparationEnum]
	given fmtIsolationExpectationsZsOrgPolicyEnum: Format[Schema.IsolationExpectations.ZsOrgPolicyEnum] = JsonEnumFormat[Schema.IsolationExpectations.ZsOrgPolicyEnum]
	given fmtIsolationExpectationsZsRegionStateEnum: Format[Schema.IsolationExpectations.ZsRegionStateEnum] = JsonEnumFormat[Schema.IsolationExpectations.ZsRegionStateEnum]
	given fmtIsolationExpectationsZiOrgPolicyEnum: Format[Schema.IsolationExpectations.ZiOrgPolicyEnum] = JsonEnumFormat[Schema.IsolationExpectations.ZiOrgPolicyEnum]
	given fmtIsolationExpectationsZiRegionPolicyEnum: Format[Schema.IsolationExpectations.ZiRegionPolicyEnum] = JsonEnumFormat[Schema.IsolationExpectations.ZiRegionPolicyEnum]
	given fmtIsolationExpectationsZiRegionStateEnum: Format[Schema.IsolationExpectations.ZiRegionStateEnum] = JsonEnumFormat[Schema.IsolationExpectations.ZiRegionStateEnum]
	given fmtDirectLocationAssignment: Format[Schema.DirectLocationAssignment] = Json.format[Schema.DirectLocationAssignment]
	given fmtSpannerLocation: Format[Schema.SpannerLocation] = Json.format[Schema.SpannerLocation]
	given fmtCloudAssetComposition: Format[Schema.CloudAssetComposition] = Json.format[Schema.CloudAssetComposition]
	given fmtTenantProjectProxy: Format[Schema.TenantProjectProxy] = Json.format[Schema.TenantProjectProxy]
	given fmtBlobstoreLocation: Format[Schema.BlobstoreLocation] = Json.format[Schema.BlobstoreLocation]
	given fmtPlacerLocation: Format[Schema.PlacerLocation] = Json.format[Schema.PlacerLocation]
	given fmtLocationAssignment: Format[Schema.LocationAssignment] = Json.format[Schema.LocationAssignment]
	given fmtLocationAssignmentLocationTypeEnum: Format[Schema.LocationAssignment.LocationTypeEnum] = JsonEnumFormat[Schema.LocationAssignment.LocationTypeEnum]
	given fmtRegionalMigDistributionPolicy: Format[Schema.RegionalMigDistributionPolicy] = Json.format[Schema.RegionalMigDistributionPolicy]
	given fmtZoneConfiguration: Format[Schema.ZoneConfiguration] = Json.format[Schema.ZoneConfiguration]
}
