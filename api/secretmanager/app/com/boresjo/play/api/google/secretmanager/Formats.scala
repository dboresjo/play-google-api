package com.boresjo.play.api.google.secretmanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtListSecretsResponse: Format[Schema.ListSecretsResponse] = Json.format[Schema.ListSecretsResponse]
	given fmtSecret: Format[Schema.Secret] = Json.format[Schema.Secret]
	given fmtReplication: Format[Schema.Replication] = Json.format[Schema.Replication]
	given fmtTopic: Format[Schema.Topic] = Json.format[Schema.Topic]
	given fmtRotation: Format[Schema.Rotation] = Json.format[Schema.Rotation]
	given fmtCustomerManagedEncryption: Format[Schema.CustomerManagedEncryption] = Json.format[Schema.CustomerManagedEncryption]
	given fmtAutomatic: Format[Schema.Automatic] = Json.format[Schema.Automatic]
	given fmtUserManaged: Format[Schema.UserManaged] = Json.format[Schema.UserManaged]
	given fmtReplica: Format[Schema.Replica] = Json.format[Schema.Replica]
	given fmtAddSecretVersionRequest: Format[Schema.AddSecretVersionRequest] = Json.format[Schema.AddSecretVersionRequest]
	given fmtSecretPayload: Format[Schema.SecretPayload] = Json.format[Schema.SecretPayload]
	given fmtSecretVersion: Format[Schema.SecretVersion] = Json.format[Schema.SecretVersion]
	given fmtSecretVersionStateEnum: Format[Schema.SecretVersion.StateEnum] = JsonEnumFormat[Schema.SecretVersion.StateEnum]
	given fmtReplicationStatus: Format[Schema.ReplicationStatus] = Json.format[Schema.ReplicationStatus]
	given fmtCustomerManagedEncryptionStatus: Format[Schema.CustomerManagedEncryptionStatus] = Json.format[Schema.CustomerManagedEncryptionStatus]
	given fmtAutomaticStatus: Format[Schema.AutomaticStatus] = Json.format[Schema.AutomaticStatus]
	given fmtUserManagedStatus: Format[Schema.UserManagedStatus] = Json.format[Schema.UserManagedStatus]
	given fmtReplicaStatus: Format[Schema.ReplicaStatus] = Json.format[Schema.ReplicaStatus]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtListSecretVersionsResponse: Format[Schema.ListSecretVersionsResponse] = Json.format[Schema.ListSecretVersionsResponse]
	given fmtAccessSecretVersionResponse: Format[Schema.AccessSecretVersionResponse] = Json.format[Schema.AccessSecretVersionResponse]
	given fmtDisableSecretVersionRequest: Format[Schema.DisableSecretVersionRequest] = Json.format[Schema.DisableSecretVersionRequest]
	given fmtEnableSecretVersionRequest: Format[Schema.EnableSecretVersionRequest] = Json.format[Schema.EnableSecretVersionRequest]
	given fmtDestroySecretVersionRequest: Format[Schema.DestroySecretVersionRequest] = Json.format[Schema.DestroySecretVersionRequest]
	given fmtSetIamPolicyRequest: Format[Schema.SetIamPolicyRequest] = Json.format[Schema.SetIamPolicyRequest]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtBinding: Format[Schema.Binding] = Json.format[Schema.Binding]
	given fmtAuditConfig: Format[Schema.AuditConfig] = Json.format[Schema.AuditConfig]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtAuditLogConfig: Format[Schema.AuditLogConfig] = Json.format[Schema.AuditLogConfig]
	given fmtAuditLogConfigLogTypeEnum: Format[Schema.AuditLogConfig.LogTypeEnum] = JsonEnumFormat[Schema.AuditLogConfig.LogTypeEnum]
	given fmtTestIamPermissionsRequest: Format[Schema.TestIamPermissionsRequest] = Json.format[Schema.TestIamPermissionsRequest]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
}
