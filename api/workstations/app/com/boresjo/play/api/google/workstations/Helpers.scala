package com.boresjo.play.api.google.workstations

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaPrivateClusterConfig: Conversion[Schema.PrivateClusterConfig, Option[Schema.PrivateClusterConfig]] = (fun: Schema.PrivateClusterConfig) => Option(fun)
		given putSchemaDomainConfig: Conversion[Schema.DomainConfig, Option[Schema.DomainConfig]] = (fun: Schema.DomainConfig) => Option(fun)
		given putListSchemaStatus: Conversion[List[Schema.Status], Option[List[Schema.Status]]] = (fun: List[Schema.Status]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaWorkstationCluster: Conversion[List[Schema.WorkstationCluster], Option[List[Schema.WorkstationCluster]]] = (fun: List[Schema.WorkstationCluster]) => Option(fun)
		given putSchemaHost: Conversion[Schema.Host, Option[Schema.Host]] = (fun: Schema.Host) => Option(fun)
		given putListSchemaPersistentDirectory: Conversion[List[Schema.PersistentDirectory], Option[List[Schema.PersistentDirectory]]] = (fun: List[Schema.PersistentDirectory]) => Option(fun)
		given putListSchemaEphemeralDirectory: Conversion[List[Schema.EphemeralDirectory], Option[List[Schema.EphemeralDirectory]]] = (fun: List[Schema.EphemeralDirectory]) => Option(fun)
		given putSchemaContainer: Conversion[Schema.Container, Option[Schema.Container]] = (fun: Schema.Container) => Option(fun)
		given putSchemaCustomerEncryptionKey: Conversion[Schema.CustomerEncryptionKey, Option[Schema.CustomerEncryptionKey]] = (fun: Schema.CustomerEncryptionKey) => Option(fun)
		given putListSchemaReadinessCheck: Conversion[List[Schema.ReadinessCheck], Option[List[Schema.ReadinessCheck]]] = (fun: List[Schema.ReadinessCheck]) => Option(fun)
		given putListSchemaPortRange: Conversion[List[Schema.PortRange], Option[List[Schema.PortRange]]] = (fun: List[Schema.PortRange]) => Option(fun)
		given putSchemaGceInstance: Conversion[Schema.GceInstance, Option[Schema.GceInstance]] = (fun: Schema.GceInstance) => Option(fun)
		given putSchemaGceShieldedInstanceConfig: Conversion[Schema.GceShieldedInstanceConfig, Option[Schema.GceShieldedInstanceConfig]] = (fun: Schema.GceShieldedInstanceConfig) => Option(fun)
		given putSchemaGceConfidentialInstanceConfig: Conversion[Schema.GceConfidentialInstanceConfig, Option[Schema.GceConfidentialInstanceConfig]] = (fun: Schema.GceConfidentialInstanceConfig) => Option(fun)
		given putListSchemaAccelerator: Conversion[List[Schema.Accelerator], Option[List[Schema.Accelerator]]] = (fun: List[Schema.Accelerator]) => Option(fun)
		given putListSchemaBoostConfig: Conversion[List[Schema.BoostConfig], Option[List[Schema.BoostConfig]]] = (fun: List[Schema.BoostConfig]) => Option(fun)
		given putSchemaGceRegionalPersistentDisk: Conversion[Schema.GceRegionalPersistentDisk, Option[Schema.GceRegionalPersistentDisk]] = (fun: Schema.GceRegionalPersistentDisk) => Option(fun)
		given putSchemaGceRegionalPersistentDiskReclaimPolicyEnum: Conversion[Schema.GceRegionalPersistentDisk.ReclaimPolicyEnum, Option[Schema.GceRegionalPersistentDisk.ReclaimPolicyEnum]] = (fun: Schema.GceRegionalPersistentDisk.ReclaimPolicyEnum) => Option(fun)
		given putSchemaGcePersistentDisk: Conversion[Schema.GcePersistentDisk, Option[Schema.GcePersistentDisk]] = (fun: Schema.GcePersistentDisk) => Option(fun)
		given putListSchemaWorkstationConfig: Conversion[List[Schema.WorkstationConfig], Option[List[Schema.WorkstationConfig]]] = (fun: List[Schema.WorkstationConfig]) => Option(fun)
		given putSchemaWorkstationStateEnum: Conversion[Schema.Workstation.StateEnum, Option[Schema.Workstation.StateEnum]] = (fun: Schema.Workstation.StateEnum) => Option(fun)
		given putListSchemaWorkstation: Conversion[List[Schema.Workstation], Option[List[Schema.Workstation]]] = (fun: List[Schema.Workstation]) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
