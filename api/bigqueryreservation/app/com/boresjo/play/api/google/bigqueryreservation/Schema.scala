package com.boresjo.play.api.google.bigqueryreservation

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object Reservation {
		enum EditionEnum extends Enum[EditionEnum] { case EDITION_UNSPECIFIED, STANDARD, ENTERPRISE, ENTERPRISE_PLUS }
	}
	case class Reservation(
	  /** The resource name of the reservation, e.g., `projects/&#42;/locations/&#42;/reservations/team1-prod`. The reservation_id must only contain lower case alphanumeric characters or dashes. It must start with a letter and must not end with a dash. Its maximum length is 64 characters. */
		name: Option[String] = None,
	  /** Baseline slots available to this reservation. A slot is a unit of computational power in BigQuery, and serves as the unit of parallelism. Queries using this reservation might use more slots during runtime if ignore_idle_slots is set to false, or autoscaling is enabled. If edition is EDITION_UNSPECIFIED and total slot_capacity of the reservation and its siblings exceeds the total slot_count of all capacity commitments, the request will fail with `google.rpc.Code.RESOURCE_EXHAUSTED`. If edition is any value but EDITION_UNSPECIFIED, then the above requirement is not needed. The total slot_capacity of the reservation and its siblings may exceed the total slot_count of capacity commitments. In that case, the exceeding slots will be charged with the autoscale SKU. You can increase the number of baseline slots in a reservation every few minutes. If you want to decrease your baseline slots, you are limited to once an hour if you have recently changed your baseline slot capacity and your baseline slots exceed your committed slots. Otherwise, you can decrease your baseline slots every few minutes. */
		slotCapacity: Option[String] = None,
	  /** If false, any query or pipeline job using this reservation will use idle slots from other reservations within the same admin project. If true, a query or pipeline job using this reservation will execute with the slot capacity specified in the slot_capacity field at most. */
		ignoreIdleSlots: Option[Boolean] = None,
	  /** The configuration parameters for the auto scaling feature. */
		autoscale: Option[Schema.Autoscale] = None,
	  /** Job concurrency target which sets a soft upper bound on the number of jobs that can run concurrently in this reservation. This is a soft target due to asynchronous nature of the system and various optimizations for small queries. Default value is 0 which means that concurrency target will be automatically computed by the system. NOTE: this field is exposed as target job concurrency in the Information Schema, DDL and BQ CLI. */
		concurrency: Option[String] = None,
	  /** Output only. Creation time of the reservation. */
		creationTime: Option[String] = None,
	  /** Output only. Last update time of the reservation. */
		updateTime: Option[String] = None,
	  /** Applicable only for reservations located within one of the BigQuery multi-regions (US or EU). If set to true, this reservation is placed in the organization's secondary region which is designated for disaster recovery purposes. If false, this reservation is placed in the organization's default region. NOTE: this is a preview feature. Project must be allow-listed in order to set this field. */
		multiRegionAuxiliary: Option[Boolean] = None,
	  /** Edition of the reservation. */
		edition: Option[Schema.Reservation.EditionEnum] = None,
	  /** Optional. The primary location of the reservation. The field is only meaningful for reservation used for cross region disaster recovery. The field is output only for customers and should not be specified, however, the google.api.field_behavior is not set to OUTPUT_ONLY since these fields are set in rerouted requests sent across regions. */
		primaryLocation: Option[String] = None,
	  /** Optional. The secondary location of the reservation which is used for cross region disaster recovery purposes. Customer can set this in create/update reservation calls to create a failover reservation or convert a non-failover reservation to a failover reservation. */
		secondaryLocation: Option[String] = None,
	  /** Optional. The original primary location of the reservation which is set only during its creation and remains unchanged afterwards. It can be used by the customer to answer questions about disaster recovery billing. The field is output only for customers and should not be specified, however, the google.api.field_behavior is not set to OUTPUT_ONLY since these fields are set in rerouted requests sent across regions. */
		originalPrimaryLocation: Option[String] = None,
	  /** Optional. The labels associated with this reservation. You can use these to organize and group your reservations. You can set this property when inserting or updating a reservation. */
		labels: Option[Map[String, String]] = None
	)
	
	case class Autoscale(
	  /** Output only. The slot capacity added to this reservation when autoscale happens. Will be between [0, max_slots]. Note: after users reduce max_slots, it may take a while before it can be propagated, so current_slots may stay in the original value and could be larger than max_slots for that brief period (less than one minute) */
		currentSlots: Option[String] = None,
	  /** Number of slots to be scaled when needed. */
		maxSlots: Option[String] = None
	)
	
	case class ListReservationsResponse(
	  /** List of reservations visible to the user. */
		reservations: Option[List[Schema.Reservation]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class FailoverReservationRequest(
	
	)
	
	object CapacityCommitment {
		enum PlanEnum extends Enum[PlanEnum] { case COMMITMENT_PLAN_UNSPECIFIED, FLEX, FLEX_FLAT_RATE, TRIAL, MONTHLY, MONTHLY_FLAT_RATE, ANNUAL, ANNUAL_FLAT_RATE, THREE_YEAR, NONE }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, ACTIVE, FAILED }
		enum RenewalPlanEnum extends Enum[RenewalPlanEnum] { case COMMITMENT_PLAN_UNSPECIFIED, FLEX, FLEX_FLAT_RATE, TRIAL, MONTHLY, MONTHLY_FLAT_RATE, ANNUAL, ANNUAL_FLAT_RATE, THREE_YEAR, NONE }
		enum EditionEnum extends Enum[EditionEnum] { case EDITION_UNSPECIFIED, STANDARD, ENTERPRISE, ENTERPRISE_PLUS }
	}
	case class CapacityCommitment(
	  /** Output only. The resource name of the capacity commitment, e.g., `projects/myproject/locations/US/capacityCommitments/123` The commitment_id must only contain lower case alphanumeric characters or dashes. It must start with a letter and must not end with a dash. Its maximum length is 64 characters. */
		name: Option[String] = None,
	  /** Number of slots in this commitment. */
		slotCount: Option[String] = None,
	  /** Capacity commitment commitment plan. */
		plan: Option[Schema.CapacityCommitment.PlanEnum] = None,
	  /** Output only. State of the commitment. */
		state: Option[Schema.CapacityCommitment.StateEnum] = None,
	  /** Output only. The start of the current commitment period. It is applicable only for ACTIVE capacity commitments. Note after the commitment is renewed, commitment_start_time won't be changed. It refers to the start time of the original commitment. */
		commitmentStartTime: Option[String] = None,
	  /** Output only. The end of the current commitment period. It is applicable only for ACTIVE capacity commitments. Note after renewal, commitment_end_time is the time the renewed commitment expires. So itwould be at a time after commitment_start_time + committed period, because we don't change commitment_start_time , */
		commitmentEndTime: Option[String] = None,
	  /** Output only. For FAILED commitment plan, provides the reason of failure. */
		failureStatus: Option[Schema.Status] = None,
	  /** The plan this capacity commitment is converted to after commitment_end_time passes. Once the plan is changed, committed period is extended according to commitment plan. Only applicable for ANNUAL and TRIAL commitments. */
		renewalPlan: Option[Schema.CapacityCommitment.RenewalPlanEnum] = None,
	  /** Applicable only for commitments located within one of the BigQuery multi-regions (US or EU). If set to true, this commitment is placed in the organization's secondary region which is designated for disaster recovery purposes. If false, this commitment is placed in the organization's default region. NOTE: this is a preview feature. Project must be allow-listed in order to set this field. */
		multiRegionAuxiliary: Option[Boolean] = None,
	  /** Edition of the capacity commitment. */
		edition: Option[Schema.CapacityCommitment.EditionEnum] = None,
	  /** Output only. If true, the commitment is a flat-rate commitment, otherwise, it's an edition commitment. */
		isFlatRate: Option[Boolean] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class ListCapacityCommitmentsResponse(
	  /** List of capacity commitments visible to the user. */
		capacityCommitments: Option[List[Schema.CapacityCommitment]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class SplitCapacityCommitmentRequest(
	  /** Number of slots in the capacity commitment after the split. */
		slotCount: Option[String] = None
	)
	
	case class SplitCapacityCommitmentResponse(
	  /** First capacity commitment, result of a split. */
		first: Option[Schema.CapacityCommitment] = None,
	  /** Second capacity commitment, result of a split. */
		second: Option[Schema.CapacityCommitment] = None
	)
	
	case class MergeCapacityCommitmentsRequest(
	  /** Ids of capacity commitments to merge. These capacity commitments must exist under admin project and location specified in the parent. ID is the last portion of capacity commitment name e.g., 'abc' for projects/myproject/locations/US/capacityCommitments/abc */
		capacityCommitmentIds: Option[List[String]] = None
	)
	
	object Assignment {
		enum JobTypeEnum extends Enum[JobTypeEnum] { case JOB_TYPE_UNSPECIFIED, PIPELINE, QUERY, ML_EXTERNAL, BACKGROUND, CONTINUOUS }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, ACTIVE }
	}
	case class Assignment(
	  /** Output only. Name of the resource. E.g.: `projects/myproject/locations/US/reservations/team1-prod/assignments/123`. The assignment_id must only contain lower case alphanumeric characters or dashes and the max length is 64 characters. */
		name: Option[String] = None,
	  /** The resource which will use the reservation. E.g. `projects/myproject`, `folders/123`, or `organizations/456`. */
		assignee: Option[String] = None,
	  /** Which type of jobs will use the reservation. */
		jobType: Option[Schema.Assignment.JobTypeEnum] = None,
	  /** Output only. State of the assignment. */
		state: Option[Schema.Assignment.StateEnum] = None,
	  /** Optional. This field controls if "Gemini in BigQuery" (https://cloud.google.com/gemini/docs/bigquery/overview) features should be enabled for this reservation assignment, which is not on by default. "Gemini in BigQuery" has a distinct compliance posture from BigQuery. If this field is set to true, the assignment job type is QUERY, and the parent reservation edition is ENTERPRISE_PLUS, then the assignment will give the grantee project/organization access to "Gemini in BigQuery" features. */
		enableGeminiInBigquery: Option[Boolean] = None
	)
	
	case class ListAssignmentsResponse(
	  /** List of assignments visible to the user. */
		assignments: Option[List[Schema.Assignment]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class SearchAssignmentsResponse(
	  /** List of assignments visible to the user. */
		assignments: Option[List[Schema.Assignment]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class SearchAllAssignmentsResponse(
	  /** List of assignments visible to the user. */
		assignments: Option[List[Schema.Assignment]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class MoveAssignmentRequest(
	  /** The new reservation ID, e.g.: `projects/myotherproject/locations/US/reservations/team2-prod` */
		destinationId: Option[String] = None,
	  /** The optional assignment ID. A new assignment name is generated if this field is empty. This field can contain only lowercase alphanumeric characters or dashes. Max length is 64 characters. */
		assignmentId: Option[String] = None
	)
	
	case class BiReservation(
	  /** The resource name of the singleton BI reservation. Reservation names have the form `projects/{project_id}/locations/{location_id}/biReservation`. */
		name: Option[String] = None,
	  /** Output only. The last update timestamp of a reservation. */
		updateTime: Option[String] = None,
	  /** Size of a reservation, in bytes. */
		size: Option[String] = None,
	  /** Preferred tables to use BI capacity for. */
		preferredTables: Option[List[Schema.TableReference]] = None
	)
	
	case class TableReference(
	  /** The assigned project ID of the project. */
		projectId: Option[String] = None,
	  /** The ID of the dataset in the above project. */
		datasetId: Option[String] = None,
	  /** The ID of the table in the above dataset. */
		tableId: Option[String] = None
	)
}
