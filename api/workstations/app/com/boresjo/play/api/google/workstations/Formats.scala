package com.boresjo.play.api.google.workstations

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
	given fmtGoogleProtobufEmpty: Format[Schema.GoogleProtobufEmpty] = Json.format[Schema.GoogleProtobufEmpty]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtWorkstationCluster: Format[Schema.WorkstationCluster] = Json.format[Schema.WorkstationCluster]
	given fmtPrivateClusterConfig: Format[Schema.PrivateClusterConfig] = Json.format[Schema.PrivateClusterConfig]
	given fmtDomainConfig: Format[Schema.DomainConfig] = Json.format[Schema.DomainConfig]
	given fmtListWorkstationClustersResponse: Format[Schema.ListWorkstationClustersResponse] = Json.format[Schema.ListWorkstationClustersResponse]
	given fmtWorkstationConfig: Format[Schema.WorkstationConfig] = Json.format[Schema.WorkstationConfig]
	given fmtHost: Format[Schema.Host] = Json.format[Schema.Host]
	given fmtPersistentDirectory: Format[Schema.PersistentDirectory] = Json.format[Schema.PersistentDirectory]
	given fmtEphemeralDirectory: Format[Schema.EphemeralDirectory] = Json.format[Schema.EphemeralDirectory]
	given fmtContainer: Format[Schema.Container] = Json.format[Schema.Container]
	given fmtCustomerEncryptionKey: Format[Schema.CustomerEncryptionKey] = Json.format[Schema.CustomerEncryptionKey]
	given fmtReadinessCheck: Format[Schema.ReadinessCheck] = Json.format[Schema.ReadinessCheck]
	given fmtPortRange: Format[Schema.PortRange] = Json.format[Schema.PortRange]
	given fmtGceInstance: Format[Schema.GceInstance] = Json.format[Schema.GceInstance]
	given fmtGceShieldedInstanceConfig: Format[Schema.GceShieldedInstanceConfig] = Json.format[Schema.GceShieldedInstanceConfig]
	given fmtGceConfidentialInstanceConfig: Format[Schema.GceConfidentialInstanceConfig] = Json.format[Schema.GceConfidentialInstanceConfig]
	given fmtAccelerator: Format[Schema.Accelerator] = Json.format[Schema.Accelerator]
	given fmtBoostConfig: Format[Schema.BoostConfig] = Json.format[Schema.BoostConfig]
	given fmtGceRegionalPersistentDisk: Format[Schema.GceRegionalPersistentDisk] = Json.format[Schema.GceRegionalPersistentDisk]
	given fmtGceRegionalPersistentDiskReclaimPolicyEnum: Format[Schema.GceRegionalPersistentDisk.ReclaimPolicyEnum] = JsonEnumFormat[Schema.GceRegionalPersistentDisk.ReclaimPolicyEnum]
	given fmtGcePersistentDisk: Format[Schema.GcePersistentDisk] = Json.format[Schema.GcePersistentDisk]
	given fmtListWorkstationConfigsResponse: Format[Schema.ListWorkstationConfigsResponse] = Json.format[Schema.ListWorkstationConfigsResponse]
	given fmtListUsableWorkstationConfigsResponse: Format[Schema.ListUsableWorkstationConfigsResponse] = Json.format[Schema.ListUsableWorkstationConfigsResponse]
	given fmtWorkstation: Format[Schema.Workstation] = Json.format[Schema.Workstation]
	given fmtWorkstationStateEnum: Format[Schema.Workstation.StateEnum] = JsonEnumFormat[Schema.Workstation.StateEnum]
	given fmtListWorkstationsResponse: Format[Schema.ListWorkstationsResponse] = Json.format[Schema.ListWorkstationsResponse]
	given fmtListUsableWorkstationsResponse: Format[Schema.ListUsableWorkstationsResponse] = Json.format[Schema.ListUsableWorkstationsResponse]
	given fmtStartWorkstationRequest: Format[Schema.StartWorkstationRequest] = Json.format[Schema.StartWorkstationRequest]
	given fmtStopWorkstationRequest: Format[Schema.StopWorkstationRequest] = Json.format[Schema.StopWorkstationRequest]
	given fmtGenerateAccessTokenRequest: Format[Schema.GenerateAccessTokenRequest] = Json.format[Schema.GenerateAccessTokenRequest]
	given fmtGenerateAccessTokenResponse: Format[Schema.GenerateAccessTokenResponse] = Json.format[Schema.GenerateAccessTokenResponse]
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
}
