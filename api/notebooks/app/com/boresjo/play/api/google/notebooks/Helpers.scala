package com.boresjo.play.api.google.notebooks

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
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaInstance: Conversion[List[Schema.Instance], Option[List[Schema.Instance]]] = (fun: List[Schema.Instance]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaGceSetup: Conversion[Schema.GceSetup, Option[Schema.GceSetup]] = (fun: Schema.GceSetup) => Option(fun)
		given putSchemaInstanceStateEnum: Conversion[Schema.Instance.StateEnum, Option[Schema.Instance.StateEnum]] = (fun: Schema.Instance.StateEnum) => Option(fun)
		given putListSchemaUpgradeHistoryEntry: Conversion[List[Schema.UpgradeHistoryEntry], Option[List[Schema.UpgradeHistoryEntry]]] = (fun: List[Schema.UpgradeHistoryEntry]) => Option(fun)
		given putSchemaInstanceHealthStateEnum: Conversion[Schema.Instance.HealthStateEnum, Option[Schema.Instance.HealthStateEnum]] = (fun: Schema.Instance.HealthStateEnum) => Option(fun)
		given putListSchemaAcceleratorConfig: Conversion[List[Schema.AcceleratorConfig], Option[List[Schema.AcceleratorConfig]]] = (fun: List[Schema.AcceleratorConfig]) => Option(fun)
		given putListSchemaServiceAccount: Conversion[List[Schema.ServiceAccount], Option[List[Schema.ServiceAccount]]] = (fun: List[Schema.ServiceAccount]) => Option(fun)
		given putSchemaVmImage: Conversion[Schema.VmImage, Option[Schema.VmImage]] = (fun: Schema.VmImage) => Option(fun)
		given putSchemaContainerImage: Conversion[Schema.ContainerImage, Option[Schema.ContainerImage]] = (fun: Schema.ContainerImage) => Option(fun)
		given putSchemaBootDisk: Conversion[Schema.BootDisk, Option[Schema.BootDisk]] = (fun: Schema.BootDisk) => Option(fun)
		given putListSchemaDataDisk: Conversion[List[Schema.DataDisk], Option[List[Schema.DataDisk]]] = (fun: List[Schema.DataDisk]) => Option(fun)
		given putSchemaShieldedInstanceConfig: Conversion[Schema.ShieldedInstanceConfig, Option[Schema.ShieldedInstanceConfig]] = (fun: Schema.ShieldedInstanceConfig) => Option(fun)
		given putListSchemaNetworkInterface: Conversion[List[Schema.NetworkInterface], Option[List[Schema.NetworkInterface]]] = (fun: List[Schema.NetworkInterface]) => Option(fun)
		given putSchemaGPUDriverConfig: Conversion[Schema.GPUDriverConfig, Option[Schema.GPUDriverConfig]] = (fun: Schema.GPUDriverConfig) => Option(fun)
		given putSchemaAcceleratorConfigTypeEnum: Conversion[Schema.AcceleratorConfig.TypeEnum, Option[Schema.AcceleratorConfig.TypeEnum]] = (fun: Schema.AcceleratorConfig.TypeEnum) => Option(fun)
		given putSchemaBootDiskDiskTypeEnum: Conversion[Schema.BootDisk.DiskTypeEnum, Option[Schema.BootDisk.DiskTypeEnum]] = (fun: Schema.BootDisk.DiskTypeEnum) => Option(fun)
		given putSchemaBootDiskDiskEncryptionEnum: Conversion[Schema.BootDisk.DiskEncryptionEnum, Option[Schema.BootDisk.DiskEncryptionEnum]] = (fun: Schema.BootDisk.DiskEncryptionEnum) => Option(fun)
		given putSchemaDataDiskDiskTypeEnum: Conversion[Schema.DataDisk.DiskTypeEnum, Option[Schema.DataDisk.DiskTypeEnum]] = (fun: Schema.DataDisk.DiskTypeEnum) => Option(fun)
		given putSchemaDataDiskDiskEncryptionEnum: Conversion[Schema.DataDisk.DiskEncryptionEnum, Option[Schema.DataDisk.DiskEncryptionEnum]] = (fun: Schema.DataDisk.DiskEncryptionEnum) => Option(fun)
		given putSchemaNetworkInterfaceNicTypeEnum: Conversion[Schema.NetworkInterface.NicTypeEnum, Option[Schema.NetworkInterface.NicTypeEnum]] = (fun: Schema.NetworkInterface.NicTypeEnum) => Option(fun)
		given putListSchemaAccessConfig: Conversion[List[Schema.AccessConfig], Option[List[Schema.AccessConfig]]] = (fun: List[Schema.AccessConfig]) => Option(fun)
		given putSchemaUpgradeHistoryEntryStateEnum: Conversion[Schema.UpgradeHistoryEntry.StateEnum, Option[Schema.UpgradeHistoryEntry.StateEnum]] = (fun: Schema.UpgradeHistoryEntry.StateEnum) => Option(fun)
		given putSchemaUpgradeHistoryEntryActionEnum: Conversion[Schema.UpgradeHistoryEntry.ActionEnum, Option[Schema.UpgradeHistoryEntry.ActionEnum]] = (fun: Schema.UpgradeHistoryEntry.ActionEnum) => Option(fun)
		given putSchemaDataDisk: Conversion[Schema.DataDisk, Option[Schema.DataDisk]] = (fun: Schema.DataDisk) => Option(fun)
		given putSchemaDiagnosticConfig: Conversion[Schema.DiagnosticConfig, Option[Schema.DiagnosticConfig]] = (fun: Schema.DiagnosticConfig) => Option(fun)
		given putSchemaDefaultValues: Conversion[Schema.DefaultValues, Option[Schema.DefaultValues]] = (fun: Schema.DefaultValues) => Option(fun)
		given putSchemaSupportedValues: Conversion[Schema.SupportedValues, Option[Schema.SupportedValues]] = (fun: Schema.SupportedValues) => Option(fun)
		given putListSchemaImageRelease: Conversion[List[Schema.ImageRelease], Option[List[Schema.ImageRelease]]] = (fun: List[Schema.ImageRelease]) => Option(fun)
		given putSchemaSnapshot: Conversion[Schema.Snapshot, Option[Schema.Snapshot]] = (fun: Schema.Snapshot) => Option(fun)
		given putSchemaEvent: Conversion[Schema.Event, Option[Schema.Event]] = (fun: Schema.Event) => Option(fun)
		given putSchemaEventTypeEnum: Conversion[Schema.Event.TypeEnum, Option[Schema.Event.TypeEnum]] = (fun: Schema.Event.TypeEnum) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
