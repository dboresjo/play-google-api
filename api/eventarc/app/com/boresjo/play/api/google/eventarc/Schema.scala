package com.boresjo.play.api.google.eventarc

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleLongrunningListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.GoogleLongrunningOperation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleLongrunningOperation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleRpcStatus(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class Empty(
	
	)
	
	case class GoogleLongrunningCancelOperationRequest(
	
	)
	
	case class Trigger(
	  /** Required. The resource name of the trigger. Must be unique within the location of the project and must be in `projects/{project}/locations/{location}/triggers/{trigger}` format. */
		name: Option[String] = None,
	  /** Output only. Server-assigned unique identifier for the trigger. The value is a UUID4 string and guaranteed to remain unchanged until the resource is deleted. */
		uid: Option[String] = None,
	  /** Output only. The creation time. */
		createTime: Option[String] = None,
	  /** Output only. The last-modified time. */
		updateTime: Option[String] = None,
	  /** Required. Unordered list. The list of filters that applies to event attributes. Only events that match all the provided filters are sent to the destination. */
		eventFilters: Option[List[Schema.EventFilter]] = None,
	  /** Optional. The IAM service account email associated with the trigger. The service account represents the identity of the trigger. The `iam.serviceAccounts.actAs` permission must be granted on the service account to allow a principal to impersonate the service account. For more information, see the [Roles and permissions](/eventarc/docs/all-roles-permissions) page specific to the trigger destination. */
		serviceAccount: Option[String] = None,
	  /** Required. Destination specifies where the events should be sent to. */
		destination: Option[Schema.Destination] = None,
	  /** Optional. To deliver messages, Eventarc might use other Google Cloud products as a transport intermediary. This field contains a reference to that transport intermediary. This information can be used for debugging purposes. */
		transport: Option[Schema.Transport] = None,
	  /** Optional. User labels attached to the triggers that can be used to group resources. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. The name of the channel associated with the trigger in `projects/{project}/locations/{location}/channels/{channel}` format. You must provide a channel to receive events from Eventarc SaaS partners. */
		channel: Option[String] = None,
	  /** Output only. The reason(s) why a trigger is in FAILED state. */
		conditions: Option[Map[String, Schema.StateCondition]] = None,
	  /** Optional. EventDataContentType specifies the type of payload in MIME format that is expected from the CloudEvent data field. This is set to `application/json` if the value is not defined. */
		eventDataContentType: Option[String] = None,
	  /** Output only. Whether or not this Trigger satisfies the requirements of physical zone separation */
		satisfiesPzs: Option[Boolean] = None,
	  /** Output only. This checksum is computed by the server based on the value of other fields, and might be sent only on create requests to ensure that the client has an up-to-date value before proceeding. */
		etag: Option[String] = None
	)
	
	case class EventFilter(
	  /** Required. The name of a CloudEvents attribute. Currently, only a subset of attributes are supported for filtering. You can [retrieve a specific provider's supported event types](/eventarc/docs/list-providers#describe-provider). All triggers MUST provide a filter for the 'type' attribute. */
		attribute: Option[String] = None,
	  /** Required. The value for the attribute. */
		value: Option[String] = None,
	  /** Optional. The operator used for matching the events with the value of the filter. If not specified, only events that have an exact key-value pair specified in the filter are matched. The allowed values are `path_pattern` and `match-path-pattern`. `path_pattern` is only allowed for GCFv1 triggers. */
		operator: Option[String] = None
	)
	
	case class Destination(
	  /** Cloud Run fully-managed resource that receives the events. The resource should be in the same project as the trigger. */
		cloudRun: Option[Schema.CloudRun] = None,
	  /** The Cloud Function resource name. Cloud Functions V1 and V2 are supported. Format: `projects/{project}/locations/{location}/functions/{function}` This is a read-only field. Creating Cloud Functions V1/V2 triggers is only supported via the Cloud Functions product. An error will be returned if the user sets this value. */
		cloudFunction: Option[String] = None,
	  /** A GKE service capable of receiving events. The service should be running in the same project as the trigger. */
		gke: Option[Schema.GKE] = None,
	  /** The resource name of the Workflow whose Executions are triggered by the events. The Workflow resource should be deployed in the same project as the trigger. Format: `projects/{project}/locations/{location}/workflows/{workflow}` */
		workflow: Option[String] = None,
	  /** An HTTP endpoint destination described by an URI. */
		httpEndpoint: Option[Schema.HttpEndpoint] = None,
	  /** Optional. Network config is used to configure how Eventarc resolves and connect to a destination. This should only be used with HttpEndpoint destination type. */
		networkConfig: Option[Schema.NetworkConfig] = None
	)
	
	case class CloudRun(
	  /** Required. The name of the Cloud Run service being addressed. See https://cloud.google.com/run/docs/reference/rest/v1/namespaces.services. Only services located in the same project as the trigger object can be addressed. */
		service: Option[String] = None,
	  /** Optional. The relative path on the Cloud Run service the events should be sent to. The value must conform to the definition of a URI path segment (section 3.3 of RFC2396). Examples: "/route", "route", "route/subroute". */
		path: Option[String] = None,
	  /** Required. The region the Cloud Run service is deployed in. */
		region: Option[String] = None
	)
	
	case class GKE(
	  /** Required. The name of the cluster the GKE service is running in. The cluster must be running in the same project as the trigger being created. */
		cluster: Option[String] = None,
	  /** Required. The name of the Google Compute Engine in which the cluster resides, which can either be compute zone (for example, us-central1-a) for the zonal clusters or region (for example, us-central1) for regional clusters. */
		location: Option[String] = None,
	  /** Required. The namespace the GKE service is running in. */
		namespace: Option[String] = None,
	  /** Required. Name of the GKE service. */
		service: Option[String] = None,
	  /** Optional. The relative path on the GKE service the events should be sent to. The value must conform to the definition of a URI path segment (section 3.3 of RFC2396). Examples: "/route", "route", "route/subroute". */
		path: Option[String] = None
	)
	
	case class HttpEndpoint(
	  /** Required. The URI of the HTTP enpdoint. The value must be a RFC2396 URI string. Examples: `http://10.10.10.8:80/route`, `http://svc.us-central1.p.local:8080/`. Only HTTP and HTTPS protocols are supported. The host can be either a static IP addressable from the VPC specified by the network config, or an internal DNS hostname of the service resolvable via Cloud DNS. */
		uri: Option[String] = None
	)
	
	case class NetworkConfig(
	  /** Required. Name of the NetworkAttachment that allows access to the customer's VPC. Format: `projects/{PROJECT_ID}/regions/{REGION}/networkAttachments/{NETWORK_ATTACHMENT_NAME}` */
		networkAttachment: Option[String] = None
	)
	
	case class Transport(
	  /** The Pub/Sub topic and subscription used by Eventarc as a transport intermediary. */
		pubsub: Option[Schema.Pubsub] = None
	)
	
	case class Pubsub(
	  /** Optional. The name of the Pub/Sub topic created and managed by Eventarc as a transport for the event delivery. Format: `projects/{PROJECT_ID}/topics/{TOPIC_NAME}`. You can set an existing topic for triggers of the type `google.cloud.pubsub.topic.v1.messagePublished`. The topic you provide here is not deleted by Eventarc at trigger deletion. */
		topic: Option[String] = None,
	  /** Output only. The name of the Pub/Sub subscription created and managed by Eventarc as a transport for the event delivery. Format: `projects/{PROJECT_ID}/subscriptions/{SUBSCRIPTION_NAME}`. */
		subscription: Option[String] = None
	)
	
	object StateCondition {
		enum CodeEnum extends Enum[CodeEnum] { case OK, CANCELLED, UNKNOWN, INVALID_ARGUMENT, DEADLINE_EXCEEDED, NOT_FOUND, ALREADY_EXISTS, PERMISSION_DENIED, UNAUTHENTICATED, RESOURCE_EXHAUSTED, FAILED_PRECONDITION, ABORTED, OUT_OF_RANGE, UNIMPLEMENTED, INTERNAL, UNAVAILABLE, DATA_LOSS }
	}
	case class StateCondition(
	  /** The canonical code of the condition. */
		code: Option[Schema.StateCondition.CodeEnum] = None,
	  /** Human-readable message. */
		message: Option[String] = None
	)
	
	case class ListTriggersResponse(
	  /** The requested triggers, up to the number specified in `page_size`. */
		triggers: Option[List[Schema.Trigger]] = None,
	  /** A page token that can be sent to `ListTriggers` to request the next page. If this is empty, then there are no more pages. */
		nextPageToken: Option[String] = None,
	  /** Unreachable resources, if any. */
		unreachable: Option[List[String]] = None
	)
	
	object Channel {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, ACTIVE, INACTIVE }
	}
	case class Channel(
	  /** Required. The resource name of the channel. Must be unique within the location on the project and must be in `projects/{project}/locations/{location}/channels/{channel_id}` format. */
		name: Option[String] = None,
	  /** Output only. Server assigned unique identifier for the channel. The value is a UUID4 string and guaranteed to remain unchanged until the resource is deleted. */
		uid: Option[String] = None,
	  /** Output only. The creation time. */
		createTime: Option[String] = None,
	  /** Output only. The last-modified time. */
		updateTime: Option[String] = None,
	  /** The name of the event provider (e.g. Eventarc SaaS partner) associated with the channel. This provider will be granted permissions to publish events to the channel. Format: `projects/{project}/locations/{location}/providers/{provider_id}`. */
		provider: Option[String] = None,
	  /** Output only. The name of the Pub/Sub topic created and managed by Eventarc system as a transport for the event delivery. Format: `projects/{project}/topics/{topic_id}`. */
		pubsubTopic: Option[String] = None,
	  /** Output only. The state of a Channel. */
		state: Option[Schema.Channel.StateEnum] = None,
	  /** Output only. The activation token for the channel. The token must be used by the provider to register the channel for publishing. */
		activationToken: Option[String] = None,
	  /** Resource name of a KMS crypto key (managed by the user) used to encrypt/decrypt their event data. It must match the pattern `projects/&#42;/locations/&#42;/keyRings/&#42;/cryptoKeys/&#42;`. */
		cryptoKeyName: Option[String] = None,
	  /** Output only. Whether or not this Channel satisfies the requirements of physical zone separation */
		satisfiesPzs: Option[Boolean] = None
	)
	
	case class ListChannelsResponse(
	  /** The requested channels, up to the number specified in `page_size`. */
		channels: Option[List[Schema.Channel]] = None,
	  /** A page token that can be sent to `ListChannels` to request the next page. If this is empty, then there are no more pages. */
		nextPageToken: Option[String] = None,
	  /** Unreachable resources, if any. */
		unreachable: Option[List[String]] = None
	)
	
	case class Provider(
	  /** Output only. In `projects/{project}/locations/{location}/providers/{provider_id}` format. */
		name: Option[String] = None,
	  /** Output only. Human friendly name for the Provider. For example "Cloud Storage". */
		displayName: Option[String] = None,
	  /** Output only. Event types for this provider. */
		eventTypes: Option[List[Schema.EventType]] = None
	)
	
	case class EventType(
	  /** Output only. The full name of the event type (for example, "google.cloud.storage.object.v1.finalized"). In the form of {provider-specific-prefix}.{resource}.{version}.{verb}. Types MUST be versioned and event schemas are guaranteed to remain backward compatible within one version. Note that event type versions and API versions do not need to match. */
		`type`: Option[String] = None,
	  /** Output only. Human friendly description of what the event type is about. For example "Bucket created in Cloud Storage". */
		description: Option[String] = None,
	  /** Output only. Filtering attributes for the event type. */
		filteringAttributes: Option[List[Schema.FilteringAttribute]] = None,
	  /** Output only. URI for the event schema. For example "https://github.com/googleapis/google-cloudevents/blob/master/proto/google/events/cloud/storage/v1/events.proto" */
		eventSchemaUri: Option[String] = None
	)
	
	case class FilteringAttribute(
	  /** Output only. Attribute used for filtering the event type. */
		attribute: Option[String] = None,
	  /** Output only. Description of the purpose of the attribute. */
		description: Option[String] = None,
	  /** Output only. If true, the triggers for this provider should always specify a filter on these attributes. Trigger creation will fail otherwise. */
		required: Option[Boolean] = None,
	  /** Output only. If true, the attribute accepts matching expressions in the Eventarc PathPattern format. */
		pathPatternSupported: Option[Boolean] = None
	)
	
	case class ListProvidersResponse(
	  /** The requested providers, up to the number specified in `page_size`. */
		providers: Option[List[Schema.Provider]] = None,
	  /** A page token that can be sent to `ListProviders` to request the next page. If this is empty, then there are no more pages. */
		nextPageToken: Option[String] = None,
	  /** Unreachable resources, if any. */
		unreachable: Option[List[String]] = None
	)
	
	case class ChannelConnection(
	  /** Required. The name of the connection. */
		name: Option[String] = None,
	  /** Output only. Server assigned ID of the resource. The server guarantees uniqueness and immutability until deleted. */
		uid: Option[String] = None,
	  /** Required. The name of the connected subscriber Channel. This is a weak reference to avoid cross project and cross accounts references. This must be in `projects/{project}/location/{location}/channels/{channel_id}` format. */
		channel: Option[String] = None,
	  /** Output only. The creation time. */
		createTime: Option[String] = None,
	  /** Output only. The last-modified time. */
		updateTime: Option[String] = None,
	  /** Input only. Activation token for the channel. The token will be used during the creation of ChannelConnection to bind the channel with the provider project. This field will not be stored in the provider resource. */
		activationToken: Option[String] = None
	)
	
	case class ListChannelConnectionsResponse(
	  /** The requested channel connections, up to the number specified in `page_size`. */
		channelConnections: Option[List[Schema.ChannelConnection]] = None,
	  /** A page token that can be sent to `ListChannelConnections` to request the next page. If this is empty, then there are no more pages. */
		nextPageToken: Option[String] = None,
	  /** Unreachable resources, if any. */
		unreachable: Option[List[String]] = None
	)
	
	case class GoogleChannelConfig(
	  /** Required. The resource name of the config. Must be in the format of, `projects/{project}/locations/{location}/googleChannelConfig`. */
		name: Option[String] = None,
	  /** Output only. The last-modified time. */
		updateTime: Option[String] = None,
	  /** Optional. Resource name of a KMS crypto key (managed by the user) used to encrypt/decrypt their event data. It must match the pattern `projects/&#42;/locations/&#42;/keyRings/&#42;/cryptoKeys/&#42;`. */
		cryptoKeyName: Option[String] = None
	)
	
	case class MessageBus(
	  /** Identifier. Resource name of the form projects/{project}/locations/{location}/messageBuses/{message_bus} */
		name: Option[String] = None,
	  /** Output only. Server assigned unique identifier for the channel. The value is a UUID4 string and guaranteed to remain unchanged until the resource is deleted. */
		uid: Option[String] = None,
	  /** Output only. This checksum is computed by the server based on the value of other fields, and might be sent only on update and delete requests to ensure that the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Output only. The creation time. */
		createTime: Option[String] = None,
	  /** Output only. The last-modified time. */
		updateTime: Option[String] = None,
	  /** Optional. Resource labels. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Resource annotations. */
		annotations: Option[Map[String, String]] = None,
	  /** Optional. Resource display name. */
		displayName: Option[String] = None,
	  /** Optional. Resource name of a KMS crypto key (managed by the user) used to encrypt/decrypt their event data. It must match the pattern `projects/&#42;/locations/&#42;/keyRings/&#42;/cryptoKeys/&#42;`. */
		cryptoKeyName: Option[String] = None,
	  /** Optional. Config to control Platform logging for the Message Bus. This log configuration is applied to the Message Bus itself, and all the Enrollments attached to it. */
		loggingConfig: Option[Schema.LoggingConfig] = None
	)
	
	object LoggingConfig {
		enum LogSeverityEnum extends Enum[LogSeverityEnum] { case LOG_SEVERITY_UNSPECIFIED, NONE, DEBUG, INFO, NOTICE, WARNING, ERROR, CRITICAL, ALERT, EMERGENCY }
	}
	case class LoggingConfig(
	  /** Optional. The minimum severity of logs that will be sent to Stackdriver/Platform Telemetry. Logs at severitiy ≥ this value will be sent, unless it is NONE. */
		logSeverity: Option[Schema.LoggingConfig.LogSeverityEnum] = None
	)
	
	case class ListMessageBusesResponse(
	  /** The requested message buses, up to the number specified in `page_size`. */
		messageBuses: Option[List[Schema.MessageBus]] = None,
	  /** A page token that can be sent to `ListMessageBuses` to request the next page. If this is empty, then there are no more pages. */
		nextPageToken: Option[String] = None,
	  /** Unreachable resources, if any. */
		unreachable: Option[List[String]] = None
	)
	
	case class ListMessageBusEnrollmentsResponse(
	  /** The requested enrollments, up to the number specified in `page_size`. */
		enrollments: Option[List[String]] = None,
	  /** A page token that can be sent to `ListMessageBusEnrollments` to request the next page. If this is empty, then there are no more pages. */
		nextPageToken: Option[String] = None,
	  /** Unreachable resources, if any. */
		unreachable: Option[List[String]] = None
	)
	
	case class Enrollment(
	  /** Identifier. Resource name of the form projects/{project}/locations/{location}/enrollments/{enrollment} */
		name: Option[String] = None,
	  /** Output only. Server assigned unique identifier for the channel. The value is a UUID4 string and guaranteed to remain unchanged until the resource is deleted. */
		uid: Option[String] = None,
	  /** Output only. This checksum is computed by the server based on the value of other fields, and might be sent only on update and delete requests to ensure that the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Output only. The creation time. */
		createTime: Option[String] = None,
	  /** Output only. The last-modified time. */
		updateTime: Option[String] = None,
	  /** Optional. Resource labels. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Resource annotations. */
		annotations: Option[Map[String, String]] = None,
	  /** Optional. Resource display name. */
		displayName: Option[String] = None,
	  /** Required. A CEL expression identifying which messages this enrollment applies to. */
		celMatch: Option[String] = None,
	  /** Required. Resource name of the message bus identifying the source of the messages. It matches the form projects/{project}/locations/{location}/messageBuses/{messageBus}. */
		messageBus: Option[String] = None,
	  /** Required. Destination is the Pipeline that the Enrollment is delivering to. It must point to the full resource name of a Pipeline. Format: "projects/{PROJECT_ID}/locations/{region}/pipelines/{PIPELINE_ID)" */
		destination: Option[String] = None
	)
	
	case class ListEnrollmentsResponse(
	  /** The requested Enrollments, up to the number specified in `page_size`. */
		enrollments: Option[List[Schema.Enrollment]] = None,
	  /** A page token that can be sent to `ListEnrollments` to request the next page. If this is empty, then there are no more pages. */
		nextPageToken: Option[String] = None,
	  /** Unreachable resources, if any. */
		unreachable: Option[List[String]] = None
	)
	
	case class Pipeline(
	  /** Identifier. The resource name of the Pipeline. Must be unique within the location of the project and must be in `projects/{project}/locations/{location}/pipelines/{pipeline}` format. */
		name: Option[String] = None,
	  /** Output only. The creation time. A timestamp in RFC3339 UTC "Zulu" format, with nanosecond resolution and up to nine fractional digits. Examples: "2014-10-02T15:01:23Z" and "2014-10-02T15:01:23.045123456Z". */
		createTime: Option[String] = None,
	  /** Output only. The last-modified time. A timestamp in RFC3339 UTC "Zulu" format, with nanosecond resolution and up to nine fractional digits. Examples: "2014-10-02T15:01:23Z" and "2014-10-02T15:01:23.045123456Z". */
		updateTime: Option[String] = None,
	  /** Optional. User labels attached to the Pipeline that can be used to group resources. An object containing a list of "key": value pairs. Example: { "name": "wrench", "mass": "1.3kg", "count": "3" }. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Server-assigned unique identifier for the Pipeline. The value is a UUID4 string and guaranteed to remain unchanged until the resource is deleted. */
		uid: Option[String] = None,
	  /** Optional. User-defined annotations. See https://google.aip.dev/128#annotations. */
		annotations: Option[Map[String, String]] = None,
	  /** Optional. Display name of resource. */
		displayName: Option[String] = None,
	  /** Required. List of destinations to which messages will be forwarded. Currently, exactly one destination is supported per Pipeline. */
		destinations: Option[List[Schema.GoogleCloudEventarcV1PipelineDestination]] = None,
	  /** Optional. List of mediation operations to be performed on the message. Currently, only one Transformation operation is allowed in each Pipeline. */
		mediations: Option[List[Schema.GoogleCloudEventarcV1PipelineMediation]] = None,
	  /** Optional. Resource name of a KMS crypto key (managed by the user) used to encrypt/decrypt the event data. If not set, an internal Google-owned key will be used to encrypt messages. It must match the pattern "projects/{project}/locations/{location}/keyRings/{keyring}/cryptoKeys/{key}". */
		cryptoKeyName: Option[String] = None,
	  /** Optional. The payload format expected for the messages received by the Pipeline. If input_payload_format is set then any messages not matching this format will be treated as persistent errors. If input_payload_format is not set, then the message data will be treated as an opaque binary and no output format can be set on the Pipeline through the Pipeline.Destination.output_payload_format field. Any Mediations on the Pipeline that involve access to the data field will fail as persistent errors. */
		inputPayloadFormat: Option[Schema.GoogleCloudEventarcV1PipelineMessagePayloadFormat] = None,
	  /** Optional. Config to control Platform Logging for Pipelines. */
		loggingConfig: Option[Schema.LoggingConfig] = None,
	  /** Optional. The retry policy to use in the pipeline. */
		retryPolicy: Option[Schema.GoogleCloudEventarcV1PipelineRetryPolicy] = None,
	  /** Output only. This checksum is computed by the server based on the value of other fields, and might be sent only on create requests to ensure that the client has an up-to-date value before proceeding. */
		etag: Option[String] = None
	)
	
	case class GoogleCloudEventarcV1PipelineDestination(
	  /** Optional. Network config is used to configure how Pipeline resolves and connects to a destination. */
		networkConfig: Option[Schema.GoogleCloudEventarcV1PipelineDestinationNetworkConfig] = None,
	  /** Optional. An HTTP endpoint destination described by an URI. If a DNS FQDN is provided as the endpoint, Pipeline will create a peering zone to the consumer VPC and forward DNS requests to the VPC specified by network config to resolve the service endpoint. See: https://cloud.google.com/dns/docs/zones/zones-overview#peering_zones */
		httpEndpoint: Option[Schema.GoogleCloudEventarcV1PipelineDestinationHttpEndpoint] = None,
	  /** Optional. The resource name of the Workflow whose Executions are triggered by the events. The Workflow resource should be deployed in the same project as the Pipeline. Format: `projects/{project}/locations/{location}/workflows/{workflow}` */
		workflow: Option[String] = None,
	  /** Optional. The resource name of the Message Bus to which events should be published. The Message Bus resource should exist in the same project as the Pipeline. Format: `projects/{project}/locations/{location}/messageBuses/{message_bus}` */
		messageBus: Option[String] = None,
	  /** Optional. The resource name of the Pub/Sub topic to which events should be published. Format: `projects/{project}/locations/{location}/topics/{topic}` */
		topic: Option[String] = None,
	  /** Optional. An authentication config used to authenticate message requests, such that destinations can verify the source. For example, this can be used with private GCP destinations that require GCP credentials to access like Cloud Run. This field is optional and should be set only by users interested in authenticated push */
		authenticationConfig: Option[Schema.GoogleCloudEventarcV1PipelineDestinationAuthenticationConfig] = None,
	  /** Optional. The message format before it is delivered to the destination. If not set, the message will be delivered in the format it was originally delivered to the Pipeline. This field can only be set if Pipeline.input_payload_format is also set. */
		outputPayloadFormat: Option[Schema.GoogleCloudEventarcV1PipelineMessagePayloadFormat] = None
	)
	
	case class GoogleCloudEventarcV1PipelineDestinationNetworkConfig(
	  /** Required. Name of the NetworkAttachment that allows access to the consumer VPC. Format: `projects/{PROJECT_ID}/regions/{REGION}/networkAttachments/{NETWORK_ATTACHMENT_NAME}` */
		networkAttachment: Option[String] = None
	)
	
	case class GoogleCloudEventarcV1PipelineDestinationHttpEndpoint(
	  /** Required. The URI of the HTTP enpdoint. The value must be a RFC2396 URI string. Examples: `https://svc.us-central1.p.local:8080/route`. Only the HTTPS protocol is supported. */
		uri: Option[String] = None,
	  /** Optional. The CEL expression used to modify how the destination-bound HTTP request is constructed. If a binding expression is not specified here, the message is treated as a CloudEvent and is mapped to the HTTP request according to the CloudEvent HTTP Protocol Binding Binary Content Mode. In this representation, all fields except the `data` and `datacontenttype` field on the message are mapped to HTTP request headers with a prefix of `ce-`. To construct the HTTP request payload and the value of the content-type HTTP header, the payload format is defined as follows: 1) Use the output_payload_format_type on the Pipeline.Destination if it is set, else: 2) Use the input_payload_format_type on the Pipeline if it is set, else: 3) Treat the payload as opaque binary data. The `data` field of the message is converted to the payload format or left as-is for case 3) and then attached as the payload of the HTTP request. The `content-type` header on the HTTP request is set to the payload format type or left empty for case 3). However, if a mediation has updated the `datacontenttype` field on the message so that it is not the same as the payload format type but it is still a prefix of the payload format type, then the `content-type` header on the HTTP request is set to this `datacontenttype` value. For example, if the `datacontenttype` is "application/json" and the payload format type is "application/json; charset=utf-8", then the `content-type` header on the HTTP request is set to "application/json; charset=utf-8". If a non-empty binding expression is specified then this expression is used to modify the default CloudEvent HTTP Protocol Binding Binary Content representation. The result of the CEL expression must be a map of key/value pairs which is used as follows: - If a map named `headers` exists on the result of the expression, then its key/value pairs are directly mapped to the HTTP request headers. The headers values are constructed from the corresponding value type's canonical representation. If the `headers` field doesn't exist then the resulting HTTP request will be the headers of the CloudEvent HTTP Binding Binary Content Mode representation of the final message. Note: If the specified binding expression, has updated the `datacontenttype` field on the message so that it is not the same as the payload format type but it is still a prefix of the payload format type, then the `content-type` header in the `headers` map is set to this `datacontenttype` value. - If a field named `body` exists on the result of the expression then its value is directly mapped to the body of the request. If the value of the `body` field is of type bytes or string then it is used for the HTTP request body as-is, with no conversion. If the body field is of any other type then it is converted to a JSON string. If the body field does not exist then the resulting payload of the HTTP request will be data value of the CloudEvent HTTP Binding Binary Content Mode representation of the final message as described earlier. - Any other fields in the resulting expression will be ignored. The CEL expression may access the incoming CloudEvent message in its definition, as follows: - The `data` field of the incoming CloudEvent message can be accessed using the `message.data` value. Subfields of `message.data` may also be accessed if an input_payload_format has been specified on the Pipeline. - Each attribute of the incoming CloudEvent message can be accessed using the `message.` value, where is replaced with the name of the attribute. - Existing headers can be accessed in the CEL expression using the `headers` variable. The `headers` variable defines a map of key/value pairs corresponding to the HTTP headers of the CloudEvent HTTP Binding Binary Content Mode representation of the final message as described earlier. For example, the following CEL expression can be used to construct an HTTP request by adding an additional header to the HTTP headers of the CloudEvent HTTP Binding Binary Content Mode representation of the final message and by overwriting the body of the request: ``` { "headers": headers.merge({"new-header-key": "new-header-value"}), "body": "new-body" } ``` Additionally, the following CEL extension functions are provided for use in this CEL expression: - toBase64Url: map.toBase64Url() -> string - Converts a CelValue to a base64url encoded string - toJsonString: map.toJsonString() -> string - Converts a CelValue to a JSON string - merge: map1.merge(map2) -> map3 - Merges the passed CEL map with the existing CEL map the function is applied to. - If the same key exists in both maps, if the key's value is type map both maps are merged else the value from the passed map is used. - denormalize: map.denormalize() -> map - Denormalizes a CEL map such that every value of type map or key in the map is expanded to return a single level map. - The resulting keys are "." separated indices of the map keys. - For example: { "a": 1, "b": { "c": 2, "d": 3 } "e": [4, 5] } .denormalize() -> { "a": 1, "b.c": 2, "b.d": 3, "e.0": 4, "e.1": 5 } - setField: map.setField(key, value) -> message - Sets the field of the message with the given key to the given value. - If the field is not present it will be added. - If the field is present it will be overwritten. - The key can be a dot separated path to set a field in a nested message. - Key must be of type string. - Value may be any valid type. - removeFields: map.removeFields([key1, key2, ...]) -> message - Removes the fields of the map with the given keys. - The keys can be a dot separated path to remove a field in a nested message. - If a key is not found it will be ignored. - Keys must be of type string. - toMap: [map1, map2, ...].toMap() -> map - Converts a CEL list of CEL maps to a single CEL map - toDestinationPayloadFormat(): message.data.toDestinationPayloadFormat() -> string or bytes - Converts the message data to the destination payload format specified in Pipeline.Destination.output_payload_format - This function is meant to be applied to the message.data field. - If the destination payload format is not set, the function will return the message data unchanged. - toCloudEventJsonWithPayloadFormat: message.toCloudEventJsonWithPayloadFormat() -> map - Converts a message to the corresponding structure of JSON format for CloudEvents - This function applies toDestinationPayloadFormat() to the message data. It also sets the corresponding datacontenttype of the CloudEvent, as indicated by Pipeline.Destination.output_payload_format. If no output_payload_format is set it will use the existing datacontenttype on the CloudEvent if present, else leave datacontenttype absent. - This function expects that the content of the message will adhere to the standard CloudEvent format. If it doesn't then this function will fail. - The result is a CEL map that corresponds to the JSON representation of the CloudEvent. To convert that data to a JSON string it can be chained with the toJsonString function. The Pipeline expects that the message it receives adheres to the standard CloudEvent format. If it doesn't then the outgoing message request may fail with a persistent error. */
		messageBindingTemplate: Option[String] = None
	)
	
	case class GoogleCloudEventarcV1PipelineDestinationAuthenticationConfig(
	  /** Optional. This authenticate method will apply Google OIDC tokens signed by a GCP service account to the requests. */
		googleOidc: Option[Schema.GoogleCloudEventarcV1PipelineDestinationAuthenticationConfigOidcToken] = None,
	  /** Optional. If specified, an [OAuth token](https://developers.google.com/identity/protocols/OAuth2) will be generated and attached as an `Authorization` header in the HTTP request. This type of authorization should generally only be used when calling Google APIs hosted on &#42;.googleapis.com. */
		oauthToken: Option[Schema.GoogleCloudEventarcV1PipelineDestinationAuthenticationConfigOAuthToken] = None
	)
	
	case class GoogleCloudEventarcV1PipelineDestinationAuthenticationConfigOidcToken(
	  /** Required. Service account email used to generate the OIDC Token. The principal who calls this API must have iam.serviceAccounts.actAs permission in the service account. See https://cloud.google.com/iam/docs/understanding-service-accounts for more information. Eventarc service agents must have roles/roles/iam.serviceAccountTokenCreator role to allow the Pipeline to create OpenID tokens for authenticated requests. */
		serviceAccount: Option[String] = None,
	  /** Optional. Audience to be used to generate the OIDC Token. The audience claim identifies the recipient that the JWT is intended for. If unspecified, the destination URI will be used. */
		audience: Option[String] = None
	)
	
	case class GoogleCloudEventarcV1PipelineDestinationAuthenticationConfigOAuthToken(
	  /** Required. Service account email used to generate the [OAuth token](https://developers.google.com/identity/protocols/OAuth2). The principal who calls this API must have iam.serviceAccounts.actAs permission in the service account. See https://cloud.google.com/iam/docs/understanding-service-accounts for more information. Eventarc service agents must have roles/roles/iam.serviceAccountTokenCreator role to allow Pipeline to create OAuth2 tokens for authenticated requests. */
		serviceAccount: Option[String] = None,
	  /** Optional. OAuth scope to be used for generating OAuth access token. If not specified, "https://www.googleapis.com/auth/cloud-platform" will be used. */
		scope: Option[String] = None
	)
	
	case class GoogleCloudEventarcV1PipelineMessagePayloadFormat(
	  /** Optional. Protobuf format. */
		protobuf: Option[Schema.GoogleCloudEventarcV1PipelineMessagePayloadFormatProtobufFormat] = None,
	  /** Optional. Avro format. */
		avro: Option[Schema.GoogleCloudEventarcV1PipelineMessagePayloadFormatAvroFormat] = None,
	  /** Optional. JSON format. */
		json: Option[Schema.GoogleCloudEventarcV1PipelineMessagePayloadFormatJsonFormat] = None
	)
	
	case class GoogleCloudEventarcV1PipelineMessagePayloadFormatProtobufFormat(
	  /** Optional. The entire schema definition is stored in this field. */
		schemaDefinition: Option[String] = None
	)
	
	case class GoogleCloudEventarcV1PipelineMessagePayloadFormatAvroFormat(
	  /** Optional. The entire schema definition is stored in this field. */
		schemaDefinition: Option[String] = None
	)
	
	case class GoogleCloudEventarcV1PipelineMessagePayloadFormatJsonFormat(
	
	)
	
	case class GoogleCloudEventarcV1PipelineMediation(
	  /** Optional. How the Pipeline is to transform messages */
		transformation: Option[Schema.GoogleCloudEventarcV1PipelineMediationTransformation] = None
	)
	
	case class GoogleCloudEventarcV1PipelineMediationTransformation(
	  /** Optional. The CEL expression template to apply to transform messages. The following CEL extension functions are provided for use in this CEL expression: - merge: map1.merge(map2) -> map3 - Merges the passed CEL map with the existing CEL map the function is applied to. - If the same key exists in both maps, if the key's value is type map both maps are merged else the value from the passed map is used. - denormalize: map.denormalize() -> map - Denormalizes a CEL map such that every value of type map or key in the map is expanded to return a single level map. - The resulting keys are "." separated indices of the map keys. - For example: { "a": 1, "b": { "c": 2, "d": 3 } "e": [4, 5] } .denormalize() -> { "a": 1, "b.c": 2, "b.d": 3, "e.0": 4, "e.1": 5 } - setField: map.setField(key, value) -> message - Sets the field of the message with the given key to the given value. - If the field is not present it will be added. - If the field is present it will be overwritten. - The key can be a dot separated path to set a field in a nested message. - Key must be of type string. - Value may be any valid type. - removeFields: map.removeFields([key1, key2, ...]) -> message - Removes the fields of the map with the given keys. - The keys can be a dot separated path to remove a field in a nested message. - If a key is not found it will be ignored. - Keys must be of type string. - toMap: [map1, map2, ...].toMap() -> map - Converts a CEL list of CEL maps to a single CEL map - toDestinationPayloadFormat(): message.data.toDestinationPayloadFormat() -> string or bytes - Converts the message data to the destination payload format specified in Pipeline.Destination.output_payload_format - This function is meant to be applied to the message.data field. - If the destination payload format is not set, the function will return the message data unchanged. - toCloudEventJsonWithPayloadFormat: message.toCloudEventJsonWithPayloadFormat() -> map - Converts a message to the corresponding structure of JSON format for CloudEvents - This function applies toDestinationPayloadFormat() to the message data. It also sets the corresponding datacontenttype of the CloudEvent, as indicated by Pipeline.Destination.output_payload_format. If no output_payload_format is set it will use the existing datacontenttype on the CloudEvent if present, else leave datacontenttype absent. - This function expects that the content of the message will adhere to the standard CloudEvent format. If it doesn't then this function will fail. - The result is a CEL map that corresponds to the JSON representation of the CloudEvent. To convert that data to a JSON string it can be chained with the toJsonString function. */
		transformationTemplate: Option[String] = None
	)
	
	case class GoogleCloudEventarcV1PipelineRetryPolicy(
	  /** Optional. The maximum number of delivery attempts for any message. The value must be between 1 and 100. The default value for this field is 5. */
		maxAttempts: Option[Int] = None,
	  /** Optional. The minimum amount of seconds to wait between retry attempts. The value must be between 1 and 600. The default value for this field is 5. */
		minRetryDelay: Option[String] = None,
	  /** Optional. The maximum amount of seconds to wait between retry attempts. The value must be between 1 and 600. The default value for this field is 60. */
		maxRetryDelay: Option[String] = None
	)
	
	case class ListPipelinesResponse(
	  /** The requested pipelines, up to the number specified in `page_size`. */
		pipelines: Option[List[Schema.Pipeline]] = None,
	  /** A page token that can be sent to `ListPipelines` to request the next page. If this is empty, then there are no more pages. */
		nextPageToken: Option[String] = None,
	  /** Unreachable resources, if any. */
		unreachable: Option[List[String]] = None
	)
	
	case class GoogleApiSource(
	  /** Identifier. Resource name of the form projects/{project}/locations/{location}/googleApiSources/{google_api_source} */
		name: Option[String] = None,
	  /** Output only. Server assigned unique identifier for the channel. The value is a UUID4 string and guaranteed to remain unchanged until the resource is deleted. */
		uid: Option[String] = None,
	  /** Output only. This checksum is computed by the server based on the value of other fields, and might be sent only on update and delete requests to ensure that the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Output only. The creation time. */
		createTime: Option[String] = None,
	  /** Output only. The last-modified time. */
		updateTime: Option[String] = None,
	  /** Optional. Resource labels. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Resource annotations. */
		annotations: Option[Map[String, String]] = None,
	  /** Optional. Resource display name. */
		displayName: Option[String] = None,
	  /** Required. Destination is the message bus that the GoogleApiSource is delivering to. It must be point to the full resource name of a MessageBus. Format: "projects/{PROJECT_ID}/locations/{region}/messagesBuses/{MESSAGE_BUS_ID) */
		destination: Option[String] = None,
	  /** Optional. Resource name of a KMS crypto key (managed by the user) used to encrypt/decrypt their event data. It must match the pattern `projects/&#42;/locations/&#42;/keyRings/&#42;/cryptoKeys/&#42;`. */
		cryptoKeyName: Option[String] = None,
	  /** Optional. Config to control Platform logging for the GoogleApiSource. */
		loggingConfig: Option[Schema.LoggingConfig] = None
	)
	
	case class ListGoogleApiSourcesResponse(
	  /** The requested GoogleApiSources, up to the number specified in `page_size`. */
		googleApiSources: Option[List[Schema.GoogleApiSource]] = None,
	  /** A page token that can be sent to `ListMessageBusEnrollments` to request the next page. If this is empty, then there are no more pages. */
		nextPageToken: Option[String] = None,
	  /** Unreachable resources, if any. */
		unreachable: Option[List[String]] = None
	)
	
	case class ListLocationsResponse(
	  /** A list of locations that matches the specified filter in the request. */
		locations: Option[List[Schema.Location]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Location(
	  /** Resource name for the location, which may vary between implementations. For example: `"projects/example-project/locations/us-east1"` */
		name: Option[String] = None,
	  /** The canonical id for this location. For example: `"us-east1"`. */
		locationId: Option[String] = None,
	  /** The friendly name for this location, typically a nearby city name. For example, "Tokyo". */
		displayName: Option[String] = None,
	  /** Cross-service attributes for the location. For example {"cloud.googleapis.com/region": "us-east1"} */
		labels: Option[Map[String, String]] = None,
	  /** Service-specific metadata. For example the available capacity at the given location. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None,
	  /** OPTIONAL: A FieldMask specifying which fields of the policy to modify. Only the fields in the mask will be modified. If no mask is provided, the following default mask is used: `paths: "bindings, etag"` */
		updateMask: Option[String] = None
	)
	
	case class Policy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.Binding]] = None,
	  /** Specifies cloud audit logging configuration for this policy. */
		auditConfigs: Option[List[Schema.AuditConfig]] = None,
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An `etag` is returned in the response to `getIamPolicy`, and systems are expected to put that etag in the request to `setIamPolicy` to ensure that their change will be applied to the same version of the policy. &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. */
		etag: Option[String] = None
	)
	
	case class Binding(
	  /** Role that is assigned to the list of `members`, or principals. For example, `roles/viewer`, `roles/editor`, or `roles/owner`. For an overview of the IAM roles and permissions, see the [IAM documentation](https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see [here](https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. `members` can have the following values: &#42; `allUsers`: A special identifier that represents anyone who is on the internet; with or without a Google account. &#42; `allAuthenticatedUsers`: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. &#42; `user:{emailid}`: An email address that represents a specific Google account. For example, `alice@example.com` . &#42; `serviceAccount:{emailid}`: An email address that represents a Google service account. For example, `my-other-app@appspot.gserviceaccount.com`. &#42; `serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]`: An identifier for a [Kubernetes service account](https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, `my-project.svc.id.goog[my-namespace/my-kubernetes-sa]`. &#42; `group:{emailid}`: An email address that represents a Google group. For example, `admins@example.com`. &#42; `domain:{domain}`: The G Suite domain (primary) that represents all the users of that domain. For example, `google.com` or `example.com`. &#42; `principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workforce identity pool. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}`: All workforce identities in a group. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All workforce identities with a specific attribute value. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;`: All identities in a workforce identity pool. &#42; `principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workload identity pool. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}`: A workload identity pool group. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All identities in a workload identity pool with a certain attribute. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;`: All identities in a workload identity pool. &#42; `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a user that has been recently deleted. For example, `alice@example.com?uid=123456789012345678901`. If the user is recovered, this value reverts to `user:{emailid}` and the recovered user retains the role in the binding. &#42; `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, `my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901`. If the service account is undeleted, this value reverts to `serviceAccount:{emailid}` and the undeleted service account retains the role in the binding. &#42; `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, `admins@example.com?uid=123456789012345678901`. If the group is recovered, this value reverts to `group:{emailid}` and the recovered group retains the role in the binding. &#42; `deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: Deleted single identity in a workforce identity pool. For example, `deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value`. */
		members: Option[List[String]] = None,
	  /** The condition that is associated with this binding. If the condition evaluates to `true`, then this binding applies to the current request. If the condition evaluates to `false`, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		condition: Option[Schema.Expr] = None
	)
	
	case class Expr(
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None,
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None,
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None
	)
	
	case class AuditConfig(
	  /** Specifies a service that will be enabled for audit logging. For example, `storage.googleapis.com`, `cloudsql.googleapis.com`. `allServices` is a special value that covers all services. */
		service: Option[String] = None,
	  /** The configuration for logging of each type of permission. */
		auditLogConfigs: Option[List[Schema.AuditLogConfig]] = None
	)
	
	object AuditLogConfig {
		enum LogTypeEnum extends Enum[LogTypeEnum] { case LOG_TYPE_UNSPECIFIED, ADMIN_READ, DATA_WRITE, DATA_READ }
	}
	case class AuditLogConfig(
	  /** The log type that this config enables. */
		logType: Option[Schema.AuditLogConfig.LogTypeEnum] = None,
	  /** Specifies the identities that do not cause logging for this type of permission. Follows the same format of Binding.members. */
		exemptedMembers: Option[List[String]] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class OperationMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusMessage: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
}
