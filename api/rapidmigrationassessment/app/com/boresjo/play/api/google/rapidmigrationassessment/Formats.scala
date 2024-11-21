package com.boresjo.play.api.google.rapidmigrationassessment

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtCollector: Format[Schema.Collector] = Json.format[Schema.Collector]
	given fmtCollectorStateEnum: Format[Schema.Collector.StateEnum] = JsonEnumFormat[Schema.Collector.StateEnum]
	given fmtGuestOsScan: Format[Schema.GuestOsScan] = Json.format[Schema.GuestOsScan]
	given fmtVSphereScan: Format[Schema.VSphereScan] = Json.format[Schema.VSphereScan]
	given fmtAnnotation: Format[Schema.Annotation] = Json.format[Schema.Annotation]
	given fmtAnnotationTypeEnum: Format[Schema.Annotation.TypeEnum] = JsonEnumFormat[Schema.Annotation.TypeEnum]
	given fmtListCollectorsResponse: Format[Schema.ListCollectorsResponse] = Json.format[Schema.ListCollectorsResponse]
	given fmtResumeCollectorRequest: Format[Schema.ResumeCollectorRequest] = Json.format[Schema.ResumeCollectorRequest]
	given fmtRegisterCollectorRequest: Format[Schema.RegisterCollectorRequest] = Json.format[Schema.RegisterCollectorRequest]
	given fmtPauseCollectorRequest: Format[Schema.PauseCollectorRequest] = Json.format[Schema.PauseCollectorRequest]
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
}
