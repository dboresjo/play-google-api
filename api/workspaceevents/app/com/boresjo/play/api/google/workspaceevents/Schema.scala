package com.boresjo.play.api.google.workspaceevents

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ReactivateSubscriptionRequest(
	
	)
	
	case class ListSubscriptionsResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** List of subscriptions. */
		subscriptions: Option[List[Schema.Subscription]] = None
	)
	
	case class Operation(
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None
	)
	
	case class PayloadOptions(
	  /** Optional. If `include_resource` is set to `true`, the list of fields to include in the event payload. Separate fields with a comma. For example, to include a Google Chat message's sender and create time, enter `message.sender,message.createTime`. If omitted, the payload includes all fields for the resource. If you specify a field that doesn't exist for the resource, the system ignores the field. */
		fieldMask: Option[String] = None,
	  /** Optional. Whether the event payload includes data about the resource that changed. For example, for an event where a Google Chat message was created, whether the payload contains data about the [`Message`](https://developers.google.com/chat/api/reference/rest/v1/spaces.messages) resource. If false, the event payload only includes the name of the changed resource. */
		includeResource: Option[Boolean] = None
	)
	
	case class Status(
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None,
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None
	)
	
	case class NotificationEndpoint(
	  /** Immutable. The Cloud Pub/Sub topic that receives events for the subscription. Format: `projects/{project}/topics/{topic}` You must create the topic in the same Google Cloud project where you create this subscription. When the topic receives events, the events are encoded as Cloud Pub/Sub messages. For details, see the [Google Cloud Pub/Sub Protocol Binding for CloudEvents](https://github.com/googleapis/google-cloudevents/blob/main/docs/spec/pubsub.md). */
		pubsubTopic: Option[String] = None
	)
	
	object Subscription {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, SUSPENDED, DELETED }
		enum SuspensionReasonEnum extends Enum[SuspensionReasonEnum] { case ERROR_TYPE_UNSPECIFIED, USER_SCOPE_REVOKED, RESOURCE_DELETED, USER_AUTHORIZATION_FAILURE, ENDPOINT_PERMISSION_DENIED, ENDPOINT_NOT_FOUND, ENDPOINT_RESOURCE_EXHAUSTED, OTHER }
	}
	case class Subscription(
	  /** Output only. The last time that the subscription is updated. */
		updateTime: Option[String] = None,
	  /** Optional. Options about what data to include in the event payload. Only supported for Google Chat events. */
		payloadOptions: Option[Schema.PayloadOptions] = None,
	  /** Input only. The time-to-live (TTL) or duration for the subscription. If unspecified or set to `0`, uses the maximum possible duration. */
		ttl: Option[String] = None,
	  /** Non-empty default. The timestamp in UTC when the subscription expires. Always displayed on output, regardless of what was used on input. */
		expireTime: Option[String] = None,
	  /** Optional. This checksum is computed by the server based on the value of other fields, and might be sent on update requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Output only. The state of the subscription. Determines whether the subscription can receive events and deliver them to the notification endpoint. */
		state: Option[Schema.Subscription.StateEnum] = None,
	  /** Required. Immutable. The Google Workspace resource that's monitored for events, formatted as the [full resource name](https://google.aip.dev/122#full-resource-names). To learn about target resources and the events that they support, see [Supported Google Workspace events](https://developers.google.com/workspace/events#supported-events). A user can only authorize your app to create one subscription for a given target resource. If your app tries to create another subscription with the same user credentials, the request returns an `ALREADY_EXISTS` error. */
		targetResource: Option[String] = None,
	  /** Identifier. Resource name of the subscription. Format: `subscriptions/{subscription}` */
		name: Option[String] = None,
	  /** Output only. The user who authorized the creation of the subscription. Format: `users/{user}` For Google Workspace users, the `{user}` value is the [`user.id`](https://developers.google.com/admin-sdk/directory/reference/rest/v1/users#User.FIELDS.ids) field from the Directory API. */
		authority: Option[String] = None,
	  /** Required. Immutable. The endpoint where the subscription delivers events, such as a Pub/Sub topic. */
		notificationEndpoint: Option[Schema.NotificationEndpoint] = None,
	  /** Output only. The time when the subscription is created. */
		createTime: Option[String] = None,
	  /** Required. Unordered list. Input for creating a subscription. Otherwise, output only. One or more types of events to receive about the target resource. Formatted according to the CloudEvents specification. The supported event types depend on the target resource of your subscription. For details, see [Supported Google Workspace events](https://developers.google.com/workspace/events/guides#supported-events). By default, you also receive events about the [lifecycle of your subscription](https://developers.google.com/workspace/events/guides/events-lifecycle). You don't need to specify lifecycle events for this field. If you specify an event type that doesn't exist for the target resource, the request returns an HTTP `400 Bad Request` status code. */
		eventTypes: Option[List[String]] = None,
	  /** Output only. If `true`, the subscription is in the process of being updated. */
		reconciling: Option[Boolean] = None,
	  /** Output only. System-assigned unique identifier for the subscription. */
		uid: Option[String] = None,
	  /** Output only. The error that suspended the subscription. To reactivate the subscription, resolve the error and call the `ReactivateSubscription` method. */
		suspensionReason: Option[Schema.Subscription.SuspensionReasonEnum] = None
	)
}
