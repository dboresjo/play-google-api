package com.boresjo.play.api.google.lifesciences

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtRunPipelineRequest: Format[Schema.RunPipelineRequest] = Json.format[Schema.RunPipelineRequest]
	given fmtPipeline: Format[Schema.Pipeline] = Json.format[Schema.Pipeline]
	given fmtAction: Format[Schema.Action] = Json.format[Schema.Action]
	given fmtResources: Format[Schema.Resources] = Json.format[Schema.Resources]
	given fmtSecret: Format[Schema.Secret] = Json.format[Schema.Secret]
	given fmtMount: Format[Schema.Mount] = Json.format[Schema.Mount]
	given fmtVirtualMachine: Format[Schema.VirtualMachine] = Json.format[Schema.VirtualMachine]
	given fmtDisk: Format[Schema.Disk] = Json.format[Schema.Disk]
	given fmtNetwork: Format[Schema.Network] = Json.format[Schema.Network]
	given fmtAccelerator: Format[Schema.Accelerator] = Json.format[Schema.Accelerator]
	given fmtServiceAccount: Format[Schema.ServiceAccount] = Json.format[Schema.ServiceAccount]
	given fmtVolume: Format[Schema.Volume] = Json.format[Schema.Volume]
	given fmtPersistentDisk: Format[Schema.PersistentDisk] = Json.format[Schema.PersistentDisk]
	given fmtExistingDisk: Format[Schema.ExistingDisk] = Json.format[Schema.ExistingDisk]
	given fmtNFSMount: Format[Schema.NFSMount] = Json.format[Schema.NFSMount]
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtContainerKilledEvent: Format[Schema.ContainerKilledEvent] = Json.format[Schema.ContainerKilledEvent]
	given fmtContainerStartedEvent: Format[Schema.ContainerStartedEvent] = Json.format[Schema.ContainerStartedEvent]
	given fmtContainerStoppedEvent: Format[Schema.ContainerStoppedEvent] = Json.format[Schema.ContainerStoppedEvent]
	given fmtDelayedEvent: Format[Schema.DelayedEvent] = Json.format[Schema.DelayedEvent]
	given fmtEvent: Format[Schema.Event] = Json.format[Schema.Event]
	given fmtWorkerAssignedEvent: Format[Schema.WorkerAssignedEvent] = Json.format[Schema.WorkerAssignedEvent]
	given fmtWorkerReleasedEvent: Format[Schema.WorkerReleasedEvent] = Json.format[Schema.WorkerReleasedEvent]
	given fmtPullStartedEvent: Format[Schema.PullStartedEvent] = Json.format[Schema.PullStartedEvent]
	given fmtPullStoppedEvent: Format[Schema.PullStoppedEvent] = Json.format[Schema.PullStoppedEvent]
	given fmtUnexpectedExitStatusEvent: Format[Schema.UnexpectedExitStatusEvent] = Json.format[Schema.UnexpectedExitStatusEvent]
	given fmtFailedEvent: Format[Schema.FailedEvent] = Json.format[Schema.FailedEvent]
	given fmtFailedEventCodeEnum: Format[Schema.FailedEvent.CodeEnum] = JsonEnumFormat[Schema.FailedEvent.CodeEnum]
	given fmtMetadata: Format[Schema.Metadata] = Json.format[Schema.Metadata]
	given fmtRunPipelineResponse: Format[Schema.RunPipelineResponse] = Json.format[Schema.RunPipelineResponse]
}
