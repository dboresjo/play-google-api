package com.boresjo.play.api.google.lifesciences

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaPipeline: Conversion[Schema.Pipeline, Option[Schema.Pipeline]] = (fun: Schema.Pipeline) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putListSchemaAction: Conversion[List[Schema.Action], Option[List[Schema.Action]]] = (fun: List[Schema.Action]) => Option(fun)
		given putSchemaResources: Conversion[Schema.Resources, Option[Schema.Resources]] = (fun: Schema.Resources) => Option(fun)
		given putSchemaSecret: Conversion[Schema.Secret, Option[Schema.Secret]] = (fun: Schema.Secret) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putMapStringInt: Conversion[Map[String, Int], Option[Map[String, Int]]] = (fun: Map[String, Int]) => Option(fun)
		given putListSchemaMount: Conversion[List[Schema.Mount], Option[List[Schema.Mount]]] = (fun: List[Schema.Mount]) => Option(fun)
		given putSchemaVirtualMachine: Conversion[Schema.VirtualMachine, Option[Schema.VirtualMachine]] = (fun: Schema.VirtualMachine) => Option(fun)
		given putListSchemaDisk: Conversion[List[Schema.Disk], Option[List[Schema.Disk]]] = (fun: List[Schema.Disk]) => Option(fun)
		given putSchemaNetwork: Conversion[Schema.Network, Option[Schema.Network]] = (fun: Schema.Network) => Option(fun)
		given putListSchemaAccelerator: Conversion[List[Schema.Accelerator], Option[List[Schema.Accelerator]]] = (fun: List[Schema.Accelerator]) => Option(fun)
		given putSchemaServiceAccount: Conversion[Schema.ServiceAccount, Option[Schema.ServiceAccount]] = (fun: Schema.ServiceAccount) => Option(fun)
		given putListSchemaVolume: Conversion[List[Schema.Volume], Option[List[Schema.Volume]]] = (fun: List[Schema.Volume]) => Option(fun)
		given putSchemaPersistentDisk: Conversion[Schema.PersistentDisk, Option[Schema.PersistentDisk]] = (fun: Schema.PersistentDisk) => Option(fun)
		given putSchemaExistingDisk: Conversion[Schema.ExistingDisk, Option[Schema.ExistingDisk]] = (fun: Schema.ExistingDisk) => Option(fun)
		given putSchemaNFSMount: Conversion[Schema.NFSMount, Option[Schema.NFSMount]] = (fun: Schema.NFSMount) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putSchemaDelayedEvent: Conversion[Schema.DelayedEvent, Option[Schema.DelayedEvent]] = (fun: Schema.DelayedEvent) => Option(fun)
		given putSchemaWorkerAssignedEvent: Conversion[Schema.WorkerAssignedEvent, Option[Schema.WorkerAssignedEvent]] = (fun: Schema.WorkerAssignedEvent) => Option(fun)
		given putSchemaWorkerReleasedEvent: Conversion[Schema.WorkerReleasedEvent, Option[Schema.WorkerReleasedEvent]] = (fun: Schema.WorkerReleasedEvent) => Option(fun)
		given putSchemaPullStartedEvent: Conversion[Schema.PullStartedEvent, Option[Schema.PullStartedEvent]] = (fun: Schema.PullStartedEvent) => Option(fun)
		given putSchemaPullStoppedEvent: Conversion[Schema.PullStoppedEvent, Option[Schema.PullStoppedEvent]] = (fun: Schema.PullStoppedEvent) => Option(fun)
		given putSchemaContainerStartedEvent: Conversion[Schema.ContainerStartedEvent, Option[Schema.ContainerStartedEvent]] = (fun: Schema.ContainerStartedEvent) => Option(fun)
		given putSchemaContainerStoppedEvent: Conversion[Schema.ContainerStoppedEvent, Option[Schema.ContainerStoppedEvent]] = (fun: Schema.ContainerStoppedEvent) => Option(fun)
		given putSchemaContainerKilledEvent: Conversion[Schema.ContainerKilledEvent, Option[Schema.ContainerKilledEvent]] = (fun: Schema.ContainerKilledEvent) => Option(fun)
		given putSchemaUnexpectedExitStatusEvent: Conversion[Schema.UnexpectedExitStatusEvent, Option[Schema.UnexpectedExitStatusEvent]] = (fun: Schema.UnexpectedExitStatusEvent) => Option(fun)
		given putSchemaFailedEvent: Conversion[Schema.FailedEvent, Option[Schema.FailedEvent]] = (fun: Schema.FailedEvent) => Option(fun)
		given putSchemaFailedEventCodeEnum: Conversion[Schema.FailedEvent.CodeEnum, Option[Schema.FailedEvent.CodeEnum]] = (fun: Schema.FailedEvent.CodeEnum) => Option(fun)
		given putListSchemaEvent: Conversion[List[Schema.Event], Option[List[Schema.Event]]] = (fun: List[Schema.Event]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
