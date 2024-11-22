package com.boresjo.play.api.google.pubsublite

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.Operation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Operation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class Empty(
	
	)
	
	case class CancelOperationRequest(
	
	)
	
	case class Topic(
	  /** The name of the topic. Structured like: projects/{project_number}/locations/{location}/topics/{topic_id} */
		name: Option[String] = None,
	  /** The settings for this topic's partitions. */
		partitionConfig: Option[Schema.PartitionConfig] = None,
	  /** The settings for this topic's message retention. */
		retentionConfig: Option[Schema.RetentionConfig] = None,
	  /** The settings for this topic's Reservation usage. */
		reservationConfig: Option[Schema.ReservationConfig] = None
	)
	
	case class PartitionConfig(
	  /** The number of partitions in the topic. Must be at least 1. Once a topic has been created the number of partitions can be increased but not decreased. Message ordering is not guaranteed across a topic resize. For more information see https://cloud.google.com/pubsub/lite/docs/topics#scaling_capacity */
		count: Option[String] = None,
	  /** DEPRECATED: Use capacity instead which can express a superset of configurations. Every partition in the topic is allocated throughput equivalent to `scale` times the standard partition throughput (4 MiB/s). This is also reflected in the cost of this topic; a topic with `scale` of 2 and count of 10 is charged for 20 partitions. This value must be in the range [1,4]. */
		scale: Option[Int] = None,
	  /** The capacity configuration. */
		capacity: Option[Schema.Capacity] = None
	)
	
	case class Capacity(
	  /** Publish throughput capacity per partition in MiB/s. Must be >= 4 and <= 16. */
		publishMibPerSec: Option[Int] = None,
	  /** Subscribe throughput capacity per partition in MiB/s. Must be >= 4 and <= 32. */
		subscribeMibPerSec: Option[Int] = None
	)
	
	case class RetentionConfig(
	  /** The provisioned storage, in bytes, per partition. If the number of bytes stored in any of the topic's partitions grows beyond this value, older messages will be dropped to make room for newer ones, regardless of the value of `period`. */
		perPartitionBytes: Option[String] = None,
	  /** How long a published message is retained. If unset, messages will be retained as long as the bytes retained for each partition is below `per_partition_bytes`. */
		period: Option[String] = None
	)
	
	case class ReservationConfig(
	  /** The Reservation to use for this topic's throughput capacity. Structured like: projects/{project_number}/locations/{location}/reservations/{reservation_id} */
		throughputReservation: Option[String] = None
	)
	
	case class TopicPartitions(
	  /** The number of partitions in the topic. */
		partitionCount: Option[String] = None
	)
	
	case class ListTopicsResponse(
	  /** The list of topic in the requested parent. The order of the topics is unspecified. */
		topics: Option[List[Schema.Topic]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page of results. If this field is omitted, there are no more results. */
		nextPageToken: Option[String] = None
	)
	
	case class ListTopicSubscriptionsResponse(
	  /** The names of subscriptions attached to the topic. The order of the subscriptions is unspecified. */
		subscriptions: Option[List[String]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page of results. If this field is omitted, there are no more results. */
		nextPageToken: Option[String] = None
	)
	
	case class Subscription(
	  /** The name of the subscription. Structured like: projects/{project_number}/locations/{location}/subscriptions/{subscription_id} */
		name: Option[String] = None,
	  /** The name of the topic this subscription is attached to. Structured like: projects/{project_number}/locations/{location}/topics/{topic_id} */
		topic: Option[String] = None,
	  /** The settings for this subscription's message delivery. */
		deliveryConfig: Option[Schema.DeliveryConfig] = None,
	  /** If present, messages are automatically written from the Pub/Sub Lite topic associated with this subscription to a destination. */
		exportConfig: Option[Schema.ExportConfig] = None
	)
	
	object DeliveryConfig {
		enum DeliveryRequirementEnum extends Enum[DeliveryRequirementEnum] { case DELIVERY_REQUIREMENT_UNSPECIFIED, DELIVER_IMMEDIATELY, DELIVER_AFTER_STORED }
	}
	case class DeliveryConfig(
	  /** The DeliveryRequirement for this subscription. */
		deliveryRequirement: Option[Schema.DeliveryConfig.DeliveryRequirementEnum] = None
	)
	
	object ExportConfig {
		enum DesiredStateEnum extends Enum[DesiredStateEnum] { case STATE_UNSPECIFIED, ACTIVE, PAUSED, PERMISSION_DENIED, NOT_FOUND }
		enum CurrentStateEnum extends Enum[CurrentStateEnum] { case STATE_UNSPECIFIED, ACTIVE, PAUSED, PERMISSION_DENIED, NOT_FOUND }
	}
	case class ExportConfig(
	  /** The desired state of this export. Setting this to values other than `ACTIVE` and `PAUSED` will result in an error. */
		desiredState: Option[Schema.ExportConfig.DesiredStateEnum] = None,
	  /** Output only. The current state of the export, which may be different to the desired state due to errors. This field is output only. */
		currentState: Option[Schema.ExportConfig.CurrentStateEnum] = None,
	  /** Optional. The name of an optional Pub/Sub Lite topic to publish messages that can not be exported to the destination. For example, the message can not be published to the Pub/Sub service because it does not satisfy the constraints documented at https://cloud.google.com/pubsub/docs/publisher. Structured like: projects/{project_number}/locations/{location}/topics/{topic_id}. Must be within the same project and location as the subscription. The topic may be changed or removed. */
		deadLetterTopic: Option[String] = None,
	  /** Messages are automatically written from the Pub/Sub Lite topic associated with this subscription to a Pub/Sub topic. */
		pubsubConfig: Option[Schema.PubSubConfig] = None
	)
	
	case class PubSubConfig(
	  /** The name of the Pub/Sub topic. Structured like: projects/{project_number}/topics/{topic_id}. The topic may be changed. */
		topic: Option[String] = None
	)
	
	case class ListSubscriptionsResponse(
	  /** The list of subscriptions in the requested parent. The order of the subscriptions is unspecified. */
		subscriptions: Option[List[Schema.Subscription]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page of results. If this field is omitted, there are no more results. */
		nextPageToken: Option[String] = None
	)
	
	object SeekSubscriptionRequest {
		enum NamedTargetEnum extends Enum[NamedTargetEnum] { case NAMED_TARGET_UNSPECIFIED, TAIL, HEAD }
	}
	case class SeekSubscriptionRequest(
	  /** Seek to a named position with respect to the message backlog. */
		namedTarget: Option[Schema.SeekSubscriptionRequest.NamedTargetEnum] = None,
	  /** Seek to the first message whose publish or event time is greater than or equal to the specified query time. If no such message can be located, will seek to the end of the message backlog. */
		timeTarget: Option[Schema.TimeTarget] = None
	)
	
	case class TimeTarget(
	  /** Request the cursor of the first message with publish time greater than or equal to `publish_time`. All messages thereafter are guaranteed to have publish times >= `publish_time`. */
		publishTime: Option[String] = None,
	  /** Request the cursor of the first message with event time greater than or equal to `event_time`. If messages are missing an event time, the publish time is used as a fallback. As event times are user supplied, subsequent messages may have event times less than `event_time` and should be filtered by the client, if necessary. */
		eventTime: Option[String] = None
	)
	
	case class Reservation(
	  /** The name of the reservation. Structured like: projects/{project_number}/locations/{location}/reservations/{reservation_id} */
		name: Option[String] = None,
	  /** The reserved throughput capacity. Every unit of throughput capacity is equivalent to 1 MiB/s of published messages or 2 MiB/s of subscribed messages. Any topics which are declared as using capacity from a Reservation will consume resources from this reservation instead of being charged individually. */
		throughputCapacity: Option[String] = None
	)
	
	case class ListReservationsResponse(
	  /** The list of reservation in the requested parent. The order of the reservations is unspecified. */
		reservations: Option[List[Schema.Reservation]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page of results. If this field is omitted, there are no more results. */
		nextPageToken: Option[String] = None
	)
	
	case class ListReservationTopicsResponse(
	  /** The names of topics attached to the reservation. The order of the topics is unspecified. */
		topics: Option[List[String]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page of results. If this field is omitted, there are no more results. */
		nextPageToken: Option[String] = None
	)
	
	case class CommitCursorRequest(
	  /** The partition for which to update the cursor. Partitions are zero indexed, so `partition` must be in the range [0, topic.num_partitions). */
		partition: Option[String] = None,
	  /** The new value for the committed cursor. */
		cursor: Option[Schema.Cursor] = None
	)
	
	case class Cursor(
	  /** The offset of a message within a topic partition. Must be greater than or equal 0. */
		offset: Option[String] = None
	)
	
	case class CommitCursorResponse(
	
	)
	
	case class ListPartitionCursorsResponse(
	  /** The partition cursors from this request. */
		partitionCursors: Option[List[Schema.PartitionCursor]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class PartitionCursor(
	  /** The partition this is for. */
		partition: Option[String] = None,
	  /** The value of the cursor. */
		cursor: Option[Schema.Cursor] = None
	)
	
	case class ComputeMessageStatsRequest(
	  /** Required. The partition for which we should compute message stats. */
		partition: Option[String] = None,
	  /** The inclusive start of the range. */
		startCursor: Option[Schema.Cursor] = None,
	  /** The exclusive end of the range. The range is empty if end_cursor <= start_cursor. Specifying a start_cursor before the first message and an end_cursor after the last message will retrieve all messages. */
		endCursor: Option[Schema.Cursor] = None
	)
	
	case class ComputeMessageStatsResponse(
	  /** The count of messages. */
		messageCount: Option[String] = None,
	  /** The number of quota bytes accounted to these messages. */
		messageBytes: Option[String] = None,
	  /** The minimum publish timestamp across these messages. Note that publish timestamps within a partition are not guaranteed to be non-decreasing. The timestamp will be unset if there are no messages. */
		minimumPublishTime: Option[String] = None,
	  /** The minimum event timestamp across these messages. For the purposes of this computation, if a message does not have an event time, we use the publish time. The timestamp will be unset if there are no messages. */
		minimumEventTime: Option[String] = None
	)
	
	case class ComputeHeadCursorRequest(
	  /** Required. The partition for which we should compute the head cursor. */
		partition: Option[String] = None
	)
	
	case class ComputeHeadCursorResponse(
	  /** The head cursor. */
		headCursor: Option[Schema.Cursor] = None
	)
	
	case class ComputeTimeCursorRequest(
	  /** Required. The partition for which we should compute the cursor. */
		partition: Option[String] = None,
	  /** Required. The target publish or event time. Specifying a future time will return an unset cursor. */
		target: Option[Schema.TimeTarget] = None
	)
	
	case class ComputeTimeCursorResponse(
	  /** If present, the cursor references the first message with time greater than or equal to the specified target time. If such a message cannot be found, the cursor will be unset (i.e. `cursor` is not present). */
		cursor: Option[Schema.Cursor] = None
	)
	
	case class SeekSubscriptionResponse(
	
	)
	
	case class OperationMetadata(
	  /** The time the operation was created. */
		createTime: Option[String] = None,
	  /** The time the operation finished running. Not set if the operation has not completed. */
		endTime: Option[String] = None,
	  /** Resource path for the target of the operation. For example, targets of seeks are subscription resources, structured like: projects/{project_number}/locations/{location}/subscriptions/{subscription_id} */
		target: Option[String] = None,
	  /** Name of the verb executed by the operation. */
		verb: Option[String] = None
	)
}
