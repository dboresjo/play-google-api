package com.boresjo.play.api.google.tpu

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
		given putListSchemaNode: Conversion[List[Schema.Node], Option[List[Schema.Node]]] = (fun: List[Schema.Node]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaNodeStateEnum: Conversion[Schema.Node.StateEnum, Option[Schema.Node.StateEnum]] = (fun: Schema.Node.StateEnum) => Option(fun)
		given putSchemaNetworkConfig: Conversion[Schema.NetworkConfig, Option[Schema.NetworkConfig]] = (fun: Schema.NetworkConfig) => Option(fun)
		given putSchemaServiceAccount: Conversion[Schema.ServiceAccount, Option[Schema.ServiceAccount]] = (fun: Schema.ServiceAccount) => Option(fun)
		given putSchemaSchedulingConfig: Conversion[Schema.SchedulingConfig, Option[Schema.SchedulingConfig]] = (fun: Schema.SchedulingConfig) => Option(fun)
		given putListSchemaNetworkEndpoint: Conversion[List[Schema.NetworkEndpoint], Option[List[Schema.NetworkEndpoint]]] = (fun: List[Schema.NetworkEndpoint]) => Option(fun)
		given putSchemaNodeHealthEnum: Conversion[Schema.Node.HealthEnum, Option[Schema.Node.HealthEnum]] = (fun: Schema.Node.HealthEnum) => Option(fun)
		given putListSchemaAttachedDisk: Conversion[List[Schema.AttachedDisk], Option[List[Schema.AttachedDisk]]] = (fun: List[Schema.AttachedDisk]) => Option(fun)
		given putSchemaNodeApiVersionEnum: Conversion[Schema.Node.ApiVersionEnum, Option[Schema.Node.ApiVersionEnum]] = (fun: Schema.Node.ApiVersionEnum) => Option(fun)
		given putListSchemaSymptom: Conversion[List[Schema.Symptom], Option[List[Schema.Symptom]]] = (fun: List[Schema.Symptom]) => Option(fun)
		given putSchemaShieldedInstanceConfig: Conversion[Schema.ShieldedInstanceConfig, Option[Schema.ShieldedInstanceConfig]] = (fun: Schema.ShieldedInstanceConfig) => Option(fun)
		given putSchemaAcceleratorConfig: Conversion[Schema.AcceleratorConfig, Option[Schema.AcceleratorConfig]] = (fun: Schema.AcceleratorConfig) => Option(fun)
		given putSchemaAccessConfig: Conversion[Schema.AccessConfig, Option[Schema.AccessConfig]] = (fun: Schema.AccessConfig) => Option(fun)
		given putSchemaAttachedDiskModeEnum: Conversion[Schema.AttachedDisk.ModeEnum, Option[Schema.AttachedDisk.ModeEnum]] = (fun: Schema.AttachedDisk.ModeEnum) => Option(fun)
		given putSchemaSymptomSymptomTypeEnum: Conversion[Schema.Symptom.SymptomTypeEnum, Option[Schema.Symptom.SymptomTypeEnum]] = (fun: Schema.Symptom.SymptomTypeEnum) => Option(fun)
		given putSchemaAcceleratorConfigTypeEnum: Conversion[Schema.AcceleratorConfig.TypeEnum, Option[Schema.AcceleratorConfig.TypeEnum]] = (fun: Schema.AcceleratorConfig.TypeEnum) => Option(fun)
		given putListSchemaQueuedResource: Conversion[List[Schema.QueuedResource], Option[List[Schema.QueuedResource]]] = (fun: List[Schema.QueuedResource]) => Option(fun)
		given putSchemaTpu: Conversion[Schema.Tpu, Option[Schema.Tpu]] = (fun: Schema.Tpu) => Option(fun)
		given putSchemaSpot: Conversion[Schema.Spot, Option[Schema.Spot]] = (fun: Schema.Spot) => Option(fun)
		given putSchemaGuaranteed: Conversion[Schema.Guaranteed, Option[Schema.Guaranteed]] = (fun: Schema.Guaranteed) => Option(fun)
		given putSchemaQueueingPolicy: Conversion[Schema.QueueingPolicy, Option[Schema.QueueingPolicy]] = (fun: Schema.QueueingPolicy) => Option(fun)
		given putSchemaQueuedResourceState: Conversion[Schema.QueuedResourceState, Option[Schema.QueuedResourceState]] = (fun: Schema.QueuedResourceState) => Option(fun)
		given putListSchemaNodeSpec: Conversion[List[Schema.NodeSpec], Option[List[Schema.NodeSpec]]] = (fun: List[Schema.NodeSpec]) => Option(fun)
		given putSchemaMultisliceParams: Conversion[Schema.MultisliceParams, Option[Schema.MultisliceParams]] = (fun: Schema.MultisliceParams) => Option(fun)
		given putSchemaNode: Conversion[Schema.Node, Option[Schema.Node]] = (fun: Schema.Node) => Option(fun)
		given putSchemaInterval: Conversion[Schema.Interval, Option[Schema.Interval]] = (fun: Schema.Interval) => Option(fun)
		given putSchemaQueuedResourceStateStateEnum: Conversion[Schema.QueuedResourceState.StateEnum, Option[Schema.QueuedResourceState.StateEnum]] = (fun: Schema.QueuedResourceState.StateEnum) => Option(fun)
		given putSchemaCreatingData: Conversion[Schema.CreatingData, Option[Schema.CreatingData]] = (fun: Schema.CreatingData) => Option(fun)
		given putSchemaAcceptedData: Conversion[Schema.AcceptedData, Option[Schema.AcceptedData]] = (fun: Schema.AcceptedData) => Option(fun)
		given putSchemaProvisioningData: Conversion[Schema.ProvisioningData, Option[Schema.ProvisioningData]] = (fun: Schema.ProvisioningData) => Option(fun)
		given putSchemaFailedData: Conversion[Schema.FailedData, Option[Schema.FailedData]] = (fun: Schema.FailedData) => Option(fun)
		given putSchemaDeletingData: Conversion[Schema.DeletingData, Option[Schema.DeletingData]] = (fun: Schema.DeletingData) => Option(fun)
		given putSchemaActiveData: Conversion[Schema.ActiveData, Option[Schema.ActiveData]] = (fun: Schema.ActiveData) => Option(fun)
		given putSchemaSuspendingData: Conversion[Schema.SuspendingData, Option[Schema.SuspendingData]] = (fun: Schema.SuspendingData) => Option(fun)
		given putSchemaSuspendedData: Conversion[Schema.SuspendedData, Option[Schema.SuspendedData]] = (fun: Schema.SuspendedData) => Option(fun)
		given putSchemaQueuedResourceStateStateInitiatorEnum: Conversion[Schema.QueuedResourceState.StateInitiatorEnum, Option[Schema.QueuedResourceState.StateInitiatorEnum]] = (fun: Schema.QueuedResourceState.StateInitiatorEnum) => Option(fun)
		given putSchemaServiceIdentity: Conversion[Schema.ServiceIdentity, Option[Schema.ServiceIdentity]] = (fun: Schema.ServiceIdentity) => Option(fun)
		given putListSchemaAcceleratorType: Conversion[List[Schema.AcceleratorType], Option[List[Schema.AcceleratorType]]] = (fun: List[Schema.AcceleratorType]) => Option(fun)
		given putListSchemaAcceleratorConfig: Conversion[List[Schema.AcceleratorConfig], Option[List[Schema.AcceleratorConfig]]] = (fun: List[Schema.AcceleratorConfig]) => Option(fun)
		given putListSchemaRuntimeVersion: Conversion[List[Schema.RuntimeVersion], Option[List[Schema.RuntimeVersion]]] = (fun: List[Schema.RuntimeVersion]) => Option(fun)
		given putListSchemaGuestAttributes: Conversion[List[Schema.GuestAttributes], Option[List[Schema.GuestAttributes]]] = (fun: List[Schema.GuestAttributes]) => Option(fun)
		given putSchemaGuestAttributesValue: Conversion[Schema.GuestAttributesValue, Option[Schema.GuestAttributesValue]] = (fun: Schema.GuestAttributesValue) => Option(fun)
		given putListSchemaGuestAttributesEntry: Conversion[List[Schema.GuestAttributesEntry], Option[List[Schema.GuestAttributesEntry]]] = (fun: List[Schema.GuestAttributesEntry]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
