package com.boresjo.play.api.google.secretmanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putListSchemaSecret: Conversion[List[Schema.Secret], Option[List[Schema.Secret]]] = (fun: List[Schema.Secret]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaReplication: Conversion[Schema.Replication, Option[Schema.Replication]] = (fun: Schema.Replication) => Option(fun)
		given putListSchemaTopic: Conversion[List[Schema.Topic], Option[List[Schema.Topic]]] = (fun: List[Schema.Topic]) => Option(fun)
		given putSchemaRotation: Conversion[Schema.Rotation, Option[Schema.Rotation]] = (fun: Schema.Rotation) => Option(fun)
		given putSchemaCustomerManagedEncryption: Conversion[Schema.CustomerManagedEncryption, Option[Schema.CustomerManagedEncryption]] = (fun: Schema.CustomerManagedEncryption) => Option(fun)
		given putSchemaAutomatic: Conversion[Schema.Automatic, Option[Schema.Automatic]] = (fun: Schema.Automatic) => Option(fun)
		given putSchemaUserManaged: Conversion[Schema.UserManaged, Option[Schema.UserManaged]] = (fun: Schema.UserManaged) => Option(fun)
		given putListSchemaReplica: Conversion[List[Schema.Replica], Option[List[Schema.Replica]]] = (fun: List[Schema.Replica]) => Option(fun)
		given putSchemaSecretPayload: Conversion[Schema.SecretPayload, Option[Schema.SecretPayload]] = (fun: Schema.SecretPayload) => Option(fun)
		given putSchemaSecretVersionStateEnum: Conversion[Schema.SecretVersion.StateEnum, Option[Schema.SecretVersion.StateEnum]] = (fun: Schema.SecretVersion.StateEnum) => Option(fun)
		given putSchemaReplicationStatus: Conversion[Schema.ReplicationStatus, Option[Schema.ReplicationStatus]] = (fun: Schema.ReplicationStatus) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaCustomerManagedEncryptionStatus: Conversion[Schema.CustomerManagedEncryptionStatus, Option[Schema.CustomerManagedEncryptionStatus]] = (fun: Schema.CustomerManagedEncryptionStatus) => Option(fun)
		given putSchemaAutomaticStatus: Conversion[Schema.AutomaticStatus, Option[Schema.AutomaticStatus]] = (fun: Schema.AutomaticStatus) => Option(fun)
		given putSchemaUserManagedStatus: Conversion[Schema.UserManagedStatus, Option[Schema.UserManagedStatus]] = (fun: Schema.UserManagedStatus) => Option(fun)
		given putListSchemaReplicaStatus: Conversion[List[Schema.ReplicaStatus], Option[List[Schema.ReplicaStatus]]] = (fun: List[Schema.ReplicaStatus]) => Option(fun)
		given putListSchemaSecretVersion: Conversion[List[Schema.SecretVersion], Option[List[Schema.SecretVersion]]] = (fun: List[Schema.SecretVersion]) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
