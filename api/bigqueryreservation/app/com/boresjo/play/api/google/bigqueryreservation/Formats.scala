package com.boresjo.play.api.google.bigqueryreservation

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtReservation: Format[Schema.Reservation] = Json.format[Schema.Reservation]
	given fmtAutoscale: Format[Schema.Autoscale] = Json.format[Schema.Autoscale]
	given fmtReservationEditionEnum: Format[Schema.Reservation.EditionEnum] = JsonEnumFormat[Schema.Reservation.EditionEnum]
	given fmtListReservationsResponse: Format[Schema.ListReservationsResponse] = Json.format[Schema.ListReservationsResponse]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtFailoverReservationRequest: Format[Schema.FailoverReservationRequest] = Json.format[Schema.FailoverReservationRequest]
	given fmtCapacityCommitment: Format[Schema.CapacityCommitment] = Json.format[Schema.CapacityCommitment]
	given fmtCapacityCommitmentPlanEnum: Format[Schema.CapacityCommitment.PlanEnum] = JsonEnumFormat[Schema.CapacityCommitment.PlanEnum]
	given fmtCapacityCommitmentStateEnum: Format[Schema.CapacityCommitment.StateEnum] = JsonEnumFormat[Schema.CapacityCommitment.StateEnum]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtCapacityCommitmentRenewalPlanEnum: Format[Schema.CapacityCommitment.RenewalPlanEnum] = JsonEnumFormat[Schema.CapacityCommitment.RenewalPlanEnum]
	given fmtCapacityCommitmentEditionEnum: Format[Schema.CapacityCommitment.EditionEnum] = JsonEnumFormat[Schema.CapacityCommitment.EditionEnum]
	given fmtListCapacityCommitmentsResponse: Format[Schema.ListCapacityCommitmentsResponse] = Json.format[Schema.ListCapacityCommitmentsResponse]
	given fmtSplitCapacityCommitmentRequest: Format[Schema.SplitCapacityCommitmentRequest] = Json.format[Schema.SplitCapacityCommitmentRequest]
	given fmtSplitCapacityCommitmentResponse: Format[Schema.SplitCapacityCommitmentResponse] = Json.format[Schema.SplitCapacityCommitmentResponse]
	given fmtMergeCapacityCommitmentsRequest: Format[Schema.MergeCapacityCommitmentsRequest] = Json.format[Schema.MergeCapacityCommitmentsRequest]
	given fmtAssignment: Format[Schema.Assignment] = Json.format[Schema.Assignment]
	given fmtAssignmentJobTypeEnum: Format[Schema.Assignment.JobTypeEnum] = JsonEnumFormat[Schema.Assignment.JobTypeEnum]
	given fmtAssignmentStateEnum: Format[Schema.Assignment.StateEnum] = JsonEnumFormat[Schema.Assignment.StateEnum]
	given fmtListAssignmentsResponse: Format[Schema.ListAssignmentsResponse] = Json.format[Schema.ListAssignmentsResponse]
	given fmtSearchAssignmentsResponse: Format[Schema.SearchAssignmentsResponse] = Json.format[Schema.SearchAssignmentsResponse]
	given fmtSearchAllAssignmentsResponse: Format[Schema.SearchAllAssignmentsResponse] = Json.format[Schema.SearchAllAssignmentsResponse]
	given fmtMoveAssignmentRequest: Format[Schema.MoveAssignmentRequest] = Json.format[Schema.MoveAssignmentRequest]
	given fmtBiReservation: Format[Schema.BiReservation] = Json.format[Schema.BiReservation]
	given fmtTableReference: Format[Schema.TableReference] = Json.format[Schema.TableReference]
}
