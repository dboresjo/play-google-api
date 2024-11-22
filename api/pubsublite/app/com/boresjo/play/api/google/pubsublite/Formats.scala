package com.boresjo.play.api.google.pubsublite

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtTopic: Format[Schema.Topic] = Json.format[Schema.Topic]
	given fmtPartitionConfig: Format[Schema.PartitionConfig] = Json.format[Schema.PartitionConfig]
	given fmtRetentionConfig: Format[Schema.RetentionConfig] = Json.format[Schema.RetentionConfig]
	given fmtReservationConfig: Format[Schema.ReservationConfig] = Json.format[Schema.ReservationConfig]
	given fmtCapacity: Format[Schema.Capacity] = Json.format[Schema.Capacity]
	given fmtTopicPartitions: Format[Schema.TopicPartitions] = Json.format[Schema.TopicPartitions]
	given fmtListTopicsResponse: Format[Schema.ListTopicsResponse] = Json.format[Schema.ListTopicsResponse]
	given fmtListTopicSubscriptionsResponse: Format[Schema.ListTopicSubscriptionsResponse] = Json.format[Schema.ListTopicSubscriptionsResponse]
	given fmtSubscription: Format[Schema.Subscription] = Json.format[Schema.Subscription]
	given fmtDeliveryConfig: Format[Schema.DeliveryConfig] = Json.format[Schema.DeliveryConfig]
	given fmtExportConfig: Format[Schema.ExportConfig] = Json.format[Schema.ExportConfig]
	given fmtDeliveryConfigDeliveryRequirementEnum: Format[Schema.DeliveryConfig.DeliveryRequirementEnum] = JsonEnumFormat[Schema.DeliveryConfig.DeliveryRequirementEnum]
	given fmtExportConfigDesiredStateEnum: Format[Schema.ExportConfig.DesiredStateEnum] = JsonEnumFormat[Schema.ExportConfig.DesiredStateEnum]
	given fmtExportConfigCurrentStateEnum: Format[Schema.ExportConfig.CurrentStateEnum] = JsonEnumFormat[Schema.ExportConfig.CurrentStateEnum]
	given fmtPubSubConfig: Format[Schema.PubSubConfig] = Json.format[Schema.PubSubConfig]
	given fmtListSubscriptionsResponse: Format[Schema.ListSubscriptionsResponse] = Json.format[Schema.ListSubscriptionsResponse]
	given fmtSeekSubscriptionRequest: Format[Schema.SeekSubscriptionRequest] = Json.format[Schema.SeekSubscriptionRequest]
	given fmtSeekSubscriptionRequestNamedTargetEnum: Format[Schema.SeekSubscriptionRequest.NamedTargetEnum] = JsonEnumFormat[Schema.SeekSubscriptionRequest.NamedTargetEnum]
	given fmtTimeTarget: Format[Schema.TimeTarget] = Json.format[Schema.TimeTarget]
	given fmtReservation: Format[Schema.Reservation] = Json.format[Schema.Reservation]
	given fmtListReservationsResponse: Format[Schema.ListReservationsResponse] = Json.format[Schema.ListReservationsResponse]
	given fmtListReservationTopicsResponse: Format[Schema.ListReservationTopicsResponse] = Json.format[Schema.ListReservationTopicsResponse]
	given fmtCommitCursorRequest: Format[Schema.CommitCursorRequest] = Json.format[Schema.CommitCursorRequest]
	given fmtCursor: Format[Schema.Cursor] = Json.format[Schema.Cursor]
	given fmtCommitCursorResponse: Format[Schema.CommitCursorResponse] = Json.format[Schema.CommitCursorResponse]
	given fmtListPartitionCursorsResponse: Format[Schema.ListPartitionCursorsResponse] = Json.format[Schema.ListPartitionCursorsResponse]
	given fmtPartitionCursor: Format[Schema.PartitionCursor] = Json.format[Schema.PartitionCursor]
	given fmtComputeMessageStatsRequest: Format[Schema.ComputeMessageStatsRequest] = Json.format[Schema.ComputeMessageStatsRequest]
	given fmtComputeMessageStatsResponse: Format[Schema.ComputeMessageStatsResponse] = Json.format[Schema.ComputeMessageStatsResponse]
	given fmtComputeHeadCursorRequest: Format[Schema.ComputeHeadCursorRequest] = Json.format[Schema.ComputeHeadCursorRequest]
	given fmtComputeHeadCursorResponse: Format[Schema.ComputeHeadCursorResponse] = Json.format[Schema.ComputeHeadCursorResponse]
	given fmtComputeTimeCursorRequest: Format[Schema.ComputeTimeCursorRequest] = Json.format[Schema.ComputeTimeCursorRequest]
	given fmtComputeTimeCursorResponse: Format[Schema.ComputeTimeCursorResponse] = Json.format[Schema.ComputeTimeCursorResponse]
	given fmtSeekSubscriptionResponse: Format[Schema.SeekSubscriptionResponse] = Json.format[Schema.SeekSubscriptionResponse]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
}
