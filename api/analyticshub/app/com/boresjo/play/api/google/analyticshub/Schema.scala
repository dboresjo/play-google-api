package com.boresjo.play.api.google.analyticshub

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListDataExchangesResponse(
	  /** The list of data exchanges. */
		dataExchanges: Option[List[Schema.DataExchange]] = None,
	  /** A token to request the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object DataExchange {
		enum DiscoveryTypeEnum extends Enum[DiscoveryTypeEnum] { case DISCOVERY_TYPE_UNSPECIFIED, DISCOVERY_TYPE_PRIVATE, DISCOVERY_TYPE_PUBLIC }
	}
	case class DataExchange(
	  /** Output only. The resource name of the data exchange. e.g. `projects/myproject/locations/US/dataExchanges/123`. */
		name: Option[String] = None,
	  /** Required. Human-readable display name of the data exchange. The display name must contain only Unicode letters, numbers (0-9), underscores (_), dashes (-), spaces ( ), ampersands (&) and must not start or end with spaces. Default value is an empty string. Max length: 63 bytes. */
		displayName: Option[String] = None,
	  /** Optional. Description of the data exchange. The description must not contain Unicode non-characters as well as C0 and C1 control codes except tabs (HT), new lines (LF), carriage returns (CR), and page breaks (FF). Default value is an empty string. Max length: 2000 bytes. */
		description: Option[String] = None,
	  /** Optional. Email or URL of the primary point of contact of the data exchange. Max Length: 1000 bytes. */
		primaryContact: Option[String] = None,
	  /** Optional. Documentation describing the data exchange. */
		documentation: Option[String] = None,
	  /** Output only. Number of listings contained in the data exchange. */
		listingCount: Option[Int] = None,
	  /** Optional. Base64 encoded image representing the data exchange. Max Size: 3.0MiB Expected image dimensions are 512x512 pixels, however the API only performs validation on size of the encoded data. Note: For byte fields, the content of the fields are base64-encoded (which increases the size of the data by 33-36%) when using JSON on the wire. */
		icon: Option[String] = None,
	  /** Optional. Configurable data sharing environment option for a data exchange. */
		sharingEnvironmentConfig: Option[Schema.SharingEnvironmentConfig] = None,
	  /** Optional. Type of discovery on the discovery page for all the listings under this exchange. Updating this field also updates (overwrites) the discovery_type field for all the listings under this exchange. */
		discoveryType: Option[Schema.DataExchange.DiscoveryTypeEnum] = None
	)
	
	case class SharingEnvironmentConfig(
	  /** Default Analytics Hub data exchange, used for secured data sharing. */
		defaultExchangeConfig: Option[Schema.DefaultExchangeConfig] = None,
	  /** Data Clean Room (DCR), used for privacy-safe and secured data sharing. */
		dcrExchangeConfig: Option[Schema.DcrExchangeConfig] = None
	)
	
	case class DefaultExchangeConfig(
	
	)
	
	case class DcrExchangeConfig(
	  /** Output only. If True, this DCR restricts the contributors to sharing only a single resource in a Listing. And no two resources should have the same IDs. So if a contributor adds a view with a conflicting name, the CreateListing API will reject the request. if False, the data contributor can publish an entire dataset (as before). This is not configurable, and by default, all new DCRs will have the restriction set to True. */
		singleSelectedResourceSharingRestriction: Option[Boolean] = None,
	  /** Output only. If True, when subscribing to this DCR, it will create only one linked dataset containing all resources shared within the cleanroom. If False, when subscribing to this DCR, it will create 1 linked dataset per listing. This is not configurable, and by default, all new DCRs will have the restriction set to True. */
		singleLinkedDatasetPerCleanroom: Option[Boolean] = None
	)
	
	case class ListOrgDataExchangesResponse(
	  /** The list of data exchanges. */
		dataExchanges: Option[List[Schema.DataExchange]] = None,
	  /** A token to request the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class ListListingsResponse(
	  /** The list of Listing. */
		listings: Option[List[Schema.Listing]] = None,
	  /** A token to request the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object Listing {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE }
		enum CategoriesEnum extends Enum[CategoriesEnum] { case CATEGORY_UNSPECIFIED, CATEGORY_OTHERS, CATEGORY_ADVERTISING_AND_MARKETING, CATEGORY_COMMERCE, CATEGORY_CLIMATE_AND_ENVIRONMENT, CATEGORY_DEMOGRAPHICS, CATEGORY_ECONOMICS, CATEGORY_EDUCATION, CATEGORY_ENERGY, CATEGORY_FINANCIAL, CATEGORY_GAMING, CATEGORY_GEOSPATIAL, CATEGORY_HEALTHCARE_AND_LIFE_SCIENCE, CATEGORY_MEDIA, CATEGORY_PUBLIC_SECTOR, CATEGORY_RETAIL, CATEGORY_SPORTS, CATEGORY_SCIENCE_AND_RESEARCH, CATEGORY_TRANSPORTATION_AND_LOGISTICS, CATEGORY_TRAVEL_AND_TOURISM }
		enum DiscoveryTypeEnum extends Enum[DiscoveryTypeEnum] { case DISCOVERY_TYPE_UNSPECIFIED, DISCOVERY_TYPE_PRIVATE, DISCOVERY_TYPE_PUBLIC }
		enum ResourceTypeEnum extends Enum[ResourceTypeEnum] { case SHARED_RESOURCE_TYPE_UNSPECIFIED, BIGQUERY_DATASET, PUBSUB_TOPIC }
	}
	case class Listing(
	  /** Required. Shared dataset i.e. BigQuery dataset source. */
		bigqueryDataset: Option[Schema.BigQueryDatasetSource] = None,
	  /** Required. Pub/Sub topic source. */
		pubsubTopic: Option[Schema.PubSubTopicSource] = None,
	  /** Output only. The resource name of the listing. e.g. `projects/myproject/locations/US/dataExchanges/123/listings/456` */
		name: Option[String] = None,
	  /** Required. Human-readable display name of the listing. The display name must contain only Unicode letters, numbers (0-9), underscores (_), dashes (-), spaces ( ), ampersands (&) and can't start or end with spaces. Default value is an empty string. Max length: 63 bytes. */
		displayName: Option[String] = None,
	  /** Optional. Short description of the listing. The description must not contain Unicode non-characters and C0 and C1 control codes except tabs (HT), new lines (LF), carriage returns (CR), and page breaks (FF). Default value is an empty string. Max length: 2000 bytes. */
		description: Option[String] = None,
	  /** Optional. Email or URL of the primary point of contact of the listing. Max Length: 1000 bytes. */
		primaryContact: Option[String] = None,
	  /** Optional. Documentation describing the listing. */
		documentation: Option[String] = None,
	  /** Output only. Current state of the listing. */
		state: Option[Schema.Listing.StateEnum] = None,
	  /** Optional. Base64 encoded image representing the listing. Max Size: 3.0MiB Expected image dimensions are 512x512 pixels, however the API only performs validation on size of the encoded data. Note: For byte fields, the contents of the field are base64-encoded (which increases the size of the data by 33-36%) when using JSON on the wire. */
		icon: Option[String] = None,
	  /** Optional. Details of the data provider who owns the source data. */
		dataProvider: Option[Schema.DataProvider] = None,
	  /** Optional. Categories of the listing. Up to two categories are allowed. */
		categories: Option[List[Schema.Listing.CategoriesEnum]] = None,
	  /** Optional. Details of the publisher who owns the listing and who can share the source data. */
		publisher: Option[Schema.Publisher] = None,
	  /** Optional. Email or URL of the request access of the listing. Subscribers can use this reference to request access. Max Length: 1000 bytes. */
		requestAccess: Option[String] = None,
	  /** Optional. If set, restricted export configuration will be propagated and enforced on the linked dataset. */
		restrictedExportConfig: Option[Schema.RestrictedExportConfig] = None,
	  /** Optional. Type of discovery of the listing on the discovery page. */
		discoveryType: Option[Schema.Listing.DiscoveryTypeEnum] = None,
	  /** Output only. Listing shared asset type. */
		resourceType: Option[Schema.Listing.ResourceTypeEnum] = None
	)
	
	case class BigQueryDatasetSource(
	  /** Resource name of the dataset source for this listing. e.g. `projects/myproject/datasets/123` */
		dataset: Option[String] = None,
	  /** Optional. Resource in this dataset that is selectively shared. This field is required for data clean room exchanges. */
		selectedResources: Option[List[Schema.SelectedResource]] = None,
	  /** Optional. If set, restricted export policy will be propagated and enforced on the linked dataset. */
		restrictedExportPolicy: Option[Schema.RestrictedExportPolicy] = None
	)
	
	case class SelectedResource(
	  /** Optional. Format: For table: `projects/{projectId}/datasets/{datasetId}/tables/{tableId}` Example:"projects/test_project/datasets/test_dataset/tables/test_table" */
		table: Option[String] = None
	)
	
	case class RestrictedExportPolicy(
	  /** Optional. If true, enable restricted export. */
		enabled: Option[Boolean] = None,
	  /** Optional. If true, restrict direct table access (read api/tabledata.list) on linked table. */
		restrictDirectTableAccess: Option[Boolean] = None,
	  /** Optional. If true, restrict export of query result derived from restricted linked dataset table. */
		restrictQueryResult: Option[Boolean] = None
	)
	
	case class PubSubTopicSource(
	  /** Required. Resource name of the Pub/Sub topic source for this listing. e.g. projects/myproject/topics/topicId */
		topic: Option[String] = None,
	  /** Optional. Region hint on where the data might be published. Data affinity regions are modifiable. See go/regions for full listing of possible Cloud regions. */
		dataAffinityRegions: Option[List[String]] = None
	)
	
	case class DataProvider(
	  /** Optional. Name of the data provider. */
		name: Option[String] = None,
	  /** Optional. Email or URL of the data provider. Max Length: 1000 bytes. */
		primaryContact: Option[String] = None
	)
	
	case class Publisher(
	  /** Optional. Name of the listing publisher. */
		name: Option[String] = None,
	  /** Optional. Email or URL of the listing publisher. Max Length: 1000 bytes. */
		primaryContact: Option[String] = None
	)
	
	case class RestrictedExportConfig(
	  /** Optional. If true, enable restricted export. */
		enabled: Option[Boolean] = None,
	  /** Output only. If true, restrict direct table access(read api/tabledata.list) on linked table. */
		restrictDirectTableAccess: Option[Boolean] = None,
	  /** Optional. If true, restrict export of query result derived from restricted linked dataset table. */
		restrictQueryResult: Option[Boolean] = None
	)
	
	case class SubscribeListingRequest(
	  /** Input only. BigQuery destination dataset to create for the subscriber. */
		destinationDataset: Option[Schema.DestinationDataset] = None,
	  /** Required. Input only. Destination Pub/Sub subscription to create for the subscriber. */
		destinationPubsubSubscription: Option[Schema.DestinationPubSubSubscription] = None
	)
	
	case class DestinationDataset(
	  /** Required. A reference that identifies the destination dataset. */
		datasetReference: Option[Schema.DestinationDatasetReference] = None,
	  /** Optional. A descriptive name for the dataset. */
		friendlyName: Option[String] = None,
	  /** Optional. A user-friendly description of the dataset. */
		description: Option[String] = None,
	  /** Optional. The labels associated with this dataset. You can use these to organize and group your datasets. You can set this property when inserting or updating a dataset. See https://cloud.google.com/resource-manager/docs/creating-managing-labels for more information. */
		labels: Option[Map[String, String]] = None,
	  /** Required. The geographic location where the dataset should reside. See https://cloud.google.com/bigquery/docs/locations for supported locations. */
		location: Option[String] = None
	)
	
	case class DestinationDatasetReference(
	  /** Required. A unique ID for this dataset, without the project name. The ID must contain only letters (a-z, A-Z), numbers (0-9), or underscores (_). The maximum length is 1,024 characters. */
		datasetId: Option[String] = None,
	  /** Required. The ID of the project containing this dataset. */
		projectId: Option[String] = None
	)
	
	case class DestinationPubSubSubscription(
	  /** Required. Destination Pub/Sub subscription resource. */
		pubsubSubscription: Option[Schema.GooglePubsubV1Subscription] = None
	)
	
	object GooglePubsubV1Subscription {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, RESOURCE_ERROR }
	}
	case class GooglePubsubV1Subscription(
	  /** Required. Name of the subscription. Format is `projects/{project}/subscriptions/{sub}`. */
		name: Option[String] = None,
	  /** Optional. If push delivery is used with this subscription, this field is used to configure it. */
		pushConfig: Option[Schema.PushConfig] = None,
	  /** Optional. If delivery to BigQuery is used with this subscription, this field is used to configure it. */
		bigqueryConfig: Option[Schema.BigQueryConfig] = None,
	  /** Optional. If delivery to Google Cloud Storage is used with this subscription, this field is used to configure it. */
		cloudStorageConfig: Option[Schema.CloudStorageConfig] = None,
	  /** Optional. The approximate amount of time (on a best-effort basis) Pub/Sub waits for the subscriber to acknowledge receipt before resending the message. In the interval after the message is delivered and before it is acknowledged, it is considered to be _outstanding_. During that time period, the message will not be redelivered (on a best-effort basis). For pull subscriptions, this value is used as the initial value for the ack deadline. To override this value for a given message, call `ModifyAckDeadline` with the corresponding `ack_id` if using non-streaming pull or send the `ack_id` in a `StreamingModifyAckDeadlineRequest` if using streaming pull. The minimum custom deadline you can specify is 10 seconds. The maximum custom deadline you can specify is 600 seconds (10 minutes). If this parameter is 0, a default value of 10 seconds is used. For push delivery, this value is also used to set the request timeout for the call to the push endpoint. If the subscriber never acknowledges the message, the Pub/Sub system will eventually redeliver the message. */
		ackDeadlineSeconds: Option[Int] = None,
	  /** Optional. Indicates whether to retain acknowledged messages. If true, then messages are not expunged from the subscription's backlog, even if they are acknowledged, until they fall out of the `message_retention_duration` window. This must be true if you would like to [`Seek` to a timestamp] (https://cloud.google.com/pubsub/docs/replay-overview#seek_to_a_time) in the past to replay previously-acknowledged messages. */
		retainAckedMessages: Option[Boolean] = None,
	  /** Optional. How long to retain unacknowledged messages in the subscription's backlog, from the moment a message is published. If `retain_acked_messages` is true, then this also configures the retention of acknowledged messages, and thus configures how far back in time a `Seek` can be done. Defaults to 7 days. Cannot be more than 31 days or less than 10 minutes. */
		messageRetentionDuration: Option[String] = None,
	  /** Optional. See [Creating and managing labels](https://cloud.google.com/pubsub/docs/labels). */
		labels: Option[Map[String, String]] = None,
	  /** Optional. If true, messages published with the same `ordering_key` in `PubsubMessage` will be delivered to the subscribers in the order in which they are received by the Pub/Sub system. Otherwise, they may be delivered in any order. */
		enableMessageOrdering: Option[Boolean] = None,
	  /** Optional. A policy that specifies the conditions for this subscription's expiration. A subscription is considered active as long as any connected subscriber is successfully consuming messages from the subscription or is issuing operations on the subscription. If `expiration_policy` is not set, a &#42;default policy&#42; with `ttl` of 31 days will be used. The minimum allowed value for `expiration_policy.ttl` is 1 day. If `expiration_policy` is set, but `expiration_policy.ttl` is not set, the subscription never expires. */
		expirationPolicy: Option[Schema.ExpirationPolicy] = None,
	  /** Optional. An expression written in the Pub/Sub [filter language](https://cloud.google.com/pubsub/docs/filtering). If non-empty, then only `PubsubMessage`s whose `attributes` field matches the filter are delivered on this subscription. If empty, then no messages are filtered out. */
		filter: Option[String] = None,
	  /** Optional. A policy that specifies the conditions for dead lettering messages in this subscription. If dead_letter_policy is not set, dead lettering is disabled. The Pub/Sub service account associated with this subscriptions's parent project (i.e., service-{project_number}@gcp-sa-pubsub.iam.gserviceaccount.com) must have permission to Acknowledge() messages on this subscription. */
		deadLetterPolicy: Option[Schema.DeadLetterPolicy] = None,
	  /** Optional. A policy that specifies how Pub/Sub retries message delivery for this subscription. If not set, the default retry policy is applied. This generally implies that messages will be retried as soon as possible for healthy subscribers. RetryPolicy will be triggered on NACKs or acknowledgement deadline exceeded events for a given message. */
		retryPolicy: Option[Schema.RetryPolicy] = None,
	  /** Optional. Indicates whether the subscription is detached from its topic. Detached subscriptions don't receive messages from their topic and don't retain any backlog. `Pull` and `StreamingPull` requests will return FAILED_PRECONDITION. If the subscription is a push subscription, pushes to the endpoint will not be made. */
		detached: Option[Boolean] = None,
	  /** Optional. If true, Pub/Sub provides the following guarantees for the delivery of a message with a given value of `message_id` on this subscription: &#42; The message sent to a subscriber is guaranteed not to be resent before the message's acknowledgement deadline expires. &#42; An acknowledged message will not be resent to a subscriber. Note that subscribers may still receive multiple copies of a message when `enable_exactly_once_delivery` is true if the message was published multiple times by a publisher client. These copies are considered distinct by Pub/Sub and have distinct `message_id` values. */
		enableExactlyOnceDelivery: Option[Boolean] = None,
	  /** Output only. Indicates the minimum duration for which a message is retained after it is published to the subscription's topic. If this field is set, messages published to the subscription's topic in the last `topic_message_retention_duration` are always available to subscribers. See the `message_retention_duration` field in `Topic`. This field is set only in responses from the server; it is ignored if it is set in any requests. */
		topicMessageRetentionDuration: Option[String] = None,
	  /** Output only. An output-only field indicating whether or not the subscription can receive messages. */
		state: Option[Schema.GooglePubsubV1Subscription.StateEnum] = None,
	  /** Output only. Information about the associated Analytics Hub subscription. Only set if the subscritpion is created by Analytics Hub. */
		analyticsHubSubscriptionInfo: Option[Schema.AnalyticsHubSubscriptionInfo] = None
	)
	
	case class PushConfig(
	  /** Optional. A URL locating the endpoint to which messages should be pushed. For example, a Webhook endpoint might use `https://example.com/push`. */
		pushEndpoint: Option[String] = None,
	  /** Optional. Endpoint configuration attributes that can be used to control different aspects of the message delivery. The only currently supported attribute is `x-goog-version`, which you can use to change the format of the pushed message. This attribute indicates the version of the data expected by the endpoint. This controls the shape of the pushed message (i.e., its fields and metadata). If not present during the `CreateSubscription` call, it will default to the version of the Pub/Sub API used to make such call. If not present in a `ModifyPushConfig` call, its value will not be changed. `GetSubscription` calls will always return a valid version, even if the subscription was created without this attribute. The only supported values for the `x-goog-version` attribute are: &#42; `v1beta1`: uses the push format defined in the v1beta1 Pub/Sub API. &#42; `v1` or `v1beta2`: uses the push format defined in the v1 Pub/Sub API. For example: `attributes { "x-goog-version": "v1" }` */
		attributes: Option[Map[String, String]] = None,
	  /** Optional. If specified, Pub/Sub will generate and attach an OIDC JWT token as an `Authorization` header in the HTTP request for every pushed message. */
		oidcToken: Option[Schema.OidcToken] = None,
	  /** Optional. When set, the payload to the push endpoint is in the form of the JSON representation of a PubsubMessage (https://cloud.google.com/pubsub/docs/reference/rpc/google.pubsub.v1#pubsubmessage). */
		pubsubWrapper: Option[Schema.PubsubWrapper] = None,
	  /** Optional. When set, the payload to the push endpoint is not wrapped. */
		noWrapper: Option[Schema.NoWrapper] = None
	)
	
	case class OidcToken(
	  /** Optional. [Service account email](https://cloud.google.com/iam/docs/service-accounts) used for generating the OIDC token. For more information on setting up authentication, see [Push subscriptions](https://cloud.google.com/pubsub/docs/push). */
		serviceAccountEmail: Option[String] = None,
	  /** Optional. Audience to be used when generating OIDC token. The audience claim identifies the recipients that the JWT is intended for. The audience value is a single case-sensitive string. Having multiple values (array) for the audience field is not supported. More info about the OIDC JWT token audience here: https://tools.ietf.org/html/rfc7519#section-4.1.3 Note: if not specified, the Push endpoint URL will be used. */
		audience: Option[String] = None
	)
	
	case class PubsubWrapper(
	
	)
	
	case class NoWrapper(
	  /** Optional. When true, writes the Pub/Sub message metadata to `x-goog-pubsub-:` headers of the HTTP request. Writes the Pub/Sub message attributes to `:` headers of the HTTP request. */
		writeMetadata: Option[Boolean] = None
	)
	
	object BigQueryConfig {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, PERMISSION_DENIED, NOT_FOUND, SCHEMA_MISMATCH, IN_TRANSIT_LOCATION_RESTRICTION }
	}
	case class BigQueryConfig(
	  /** Optional. The name of the table to which to write data, of the form {projectId}.{datasetId}.{tableId} */
		table: Option[String] = None,
	  /** Optional. When true, use the topic's schema as the columns to write to in BigQuery, if it exists. `use_topic_schema` and `use_table_schema` cannot be enabled at the same time. */
		useTopicSchema: Option[Boolean] = None,
	  /** Optional. When true, write the subscription name, message_id, publish_time, attributes, and ordering_key to additional columns in the table. The subscription name, message_id, and publish_time fields are put in their own columns while all other message properties (other than data) are written to a JSON object in the attributes column. */
		writeMetadata: Option[Boolean] = None,
	  /** Optional. When true and use_topic_schema is true, any fields that are a part of the topic schema that are not part of the BigQuery table schema are dropped when writing to BigQuery. Otherwise, the schemas must be kept in sync and any messages with extra fields are not written and remain in the subscription's backlog. */
		dropUnknownFields: Option[Boolean] = None,
	  /** Output only. An output-only field that indicates whether or not the subscription can receive messages. */
		state: Option[Schema.BigQueryConfig.StateEnum] = None,
	  /** Optional. When true, use the BigQuery table's schema as the columns to write to in BigQuery. `use_table_schema` and `use_topic_schema` cannot be enabled at the same time. */
		useTableSchema: Option[Boolean] = None,
	  /** Optional. The service account to use to write to BigQuery. The subscription creator or updater that specifies this field must have `iam.serviceAccounts.actAs` permission on the service account. If not specified, the Pub/Sub [service agent](https://cloud.google.com/iam/docs/service-agents), service-{project_number}@gcp-sa-pubsub.iam.gserviceaccount.com, is used. */
		serviceAccountEmail: Option[String] = None
	)
	
	object CloudStorageConfig {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, PERMISSION_DENIED, NOT_FOUND, IN_TRANSIT_LOCATION_RESTRICTION, SCHEMA_MISMATCH }
	}
	case class CloudStorageConfig(
	  /** Required. User-provided name for the Cloud Storage bucket. The bucket must be created by the user. The bucket name must be without any prefix like "gs://". See the [bucket naming requirements] (https://cloud.google.com/storage/docs/buckets#naming). */
		bucket: Option[String] = None,
	  /** Optional. User-provided prefix for Cloud Storage filename. See the [object naming requirements](https://cloud.google.com/storage/docs/objects#naming). */
		filenamePrefix: Option[String] = None,
	  /** Optional. User-provided suffix for Cloud Storage filename. See the [object naming requirements](https://cloud.google.com/storage/docs/objects#naming). Must not end in "/". */
		filenameSuffix: Option[String] = None,
	  /** Optional. User-provided format string specifying how to represent datetimes in Cloud Storage filenames. See the [datetime format guidance](https://cloud.google.com/pubsub/docs/create-cloudstorage-subscription#file_names). */
		filenameDatetimeFormat: Option[String] = None,
	  /** Optional. If set, message data will be written to Cloud Storage in text format. */
		textConfig: Option[Schema.TextConfig] = None,
	  /** Optional. If set, message data will be written to Cloud Storage in Avro format. */
		avroConfig: Option[Schema.AvroConfig] = None,
	  /** Optional. The maximum duration that can elapse before a new Cloud Storage file is created. Min 1 minute, max 10 minutes, default 5 minutes. May not exceed the subscription's acknowledgement deadline. */
		maxDuration: Option[String] = None,
	  /** Optional. The maximum bytes that can be written to a Cloud Storage file before a new file is created. Min 1 KB, max 10 GiB. The max_bytes limit may be exceeded in cases where messages are larger than the limit. */
		maxBytes: Option[String] = None,
	  /** Optional. The maximum number of messages that can be written to a Cloud Storage file before a new file is created. Min 1000 messages. */
		maxMessages: Option[String] = None,
	  /** Output only. An output-only field that indicates whether or not the subscription can receive messages. */
		state: Option[Schema.CloudStorageConfig.StateEnum] = None,
	  /** Optional. The service account to use to write to Cloud Storage. The subscription creator or updater that specifies this field must have `iam.serviceAccounts.actAs` permission on the service account. If not specified, the Pub/Sub [service agent](https://cloud.google.com/iam/docs/service-agents), service-{project_number}@gcp-sa-pubsub.iam.gserviceaccount.com, is used. */
		serviceAccountEmail: Option[String] = None
	)
	
	case class TextConfig(
	
	)
	
	case class AvroConfig(
	  /** Optional. When true, write the subscription name, message_id, publish_time, attributes, and ordering_key as additional fields in the output. The subscription name, message_id, and publish_time fields are put in their own fields while all other message properties other than data (for example, an ordering_key, if present) are added as entries in the attributes map. */
		writeMetadata: Option[Boolean] = None,
	  /** Optional. When true, the output Cloud Storage file will be serialized using the topic schema, if it exists. */
		useTopicSchema: Option[Boolean] = None
	)
	
	case class ExpirationPolicy(
	  /** Optional. Specifies the "time-to-live" duration for an associated resource. The resource expires if it is not active for a period of `ttl`. The definition of "activity" depends on the type of the associated resource. The minimum and maximum allowed values for `ttl` depend on the type of the associated resource, as well. If `ttl` is not set, the associated resource never expires. */
		ttl: Option[String] = None
	)
	
	case class DeadLetterPolicy(
	  /** Optional. The name of the topic to which dead letter messages should be published. Format is `projects/{project}/topics/{topic}`.The Pub/Sub service account associated with the enclosing subscription's parent project (i.e., service-{project_number}@gcp-sa-pubsub.iam.gserviceaccount.com) must have permission to Publish() to this topic. The operation will fail if the topic does not exist. Users should ensure that there is a subscription attached to this topic since messages published to a topic with no subscriptions are lost. */
		deadLetterTopic: Option[String] = None,
	  /** Optional. The maximum number of delivery attempts for any message. The value must be between 5 and 100. The number of delivery attempts is defined as 1 + (the sum of number of NACKs and number of times the acknowledgement deadline has been exceeded for the message). A NACK is any call to ModifyAckDeadline with a 0 deadline. Note that client libraries may automatically extend ack_deadlines. This field will be honored on a best effort basis. If this parameter is 0, a default value of 5 is used. */
		maxDeliveryAttempts: Option[Int] = None
	)
	
	case class RetryPolicy(
	  /** Optional. The minimum delay between consecutive deliveries of a given message. Value should be between 0 and 600 seconds. Defaults to 10 seconds. */
		minimumBackoff: Option[String] = None,
	  /** Optional. The maximum delay between consecutive deliveries of a given message. Value should be between 0 and 600 seconds. Defaults to 600 seconds. */
		maximumBackoff: Option[String] = None
	)
	
	case class AnalyticsHubSubscriptionInfo(
	  /** Optional. The name of the associated Analytics Hub listing resource. Pattern: "projects/{project}/locations/{location}/dataExchanges/{data_exchange}/listings/{listing}" */
		listing: Option[String] = None,
	  /** Optional. The name of the associated Analytics Hub subscription resource. Pattern: "projects/{project}/locations/{location}/subscriptions/{subscription}" */
		subscription: Option[String] = None
	)
	
	case class SubscribeListingResponse(
	  /** Subscription object created from this subscribe action. */
		subscription: Option[Schema.Subscription] = None
	)
	
	object Subscription {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STATE_ACTIVE, STATE_STALE, STATE_INACTIVE }
		enum ResourceTypeEnum extends Enum[ResourceTypeEnum] { case SHARED_RESOURCE_TYPE_UNSPECIFIED, BIGQUERY_DATASET, PUBSUB_TOPIC }
	}
	case class Subscription(
	  /** Output only. Resource name of the source Listing. e.g. projects/123/locations/US/dataExchanges/456/listings/789 */
		listing: Option[String] = None,
	  /** Output only. Resource name of the source Data Exchange. e.g. projects/123/locations/US/dataExchanges/456 */
		dataExchange: Option[String] = None,
	  /** Output only. The resource name of the subscription. e.g. `projects/myproject/locations/US/subscriptions/123`. */
		name: Option[String] = None,
	  /** Output only. Timestamp when the subscription was created. */
		creationTime: Option[String] = None,
	  /** Output only. Timestamp when the subscription was last modified. */
		lastModifyTime: Option[String] = None,
	  /** Output only. Organization of the project this subscription belongs to. */
		organizationId: Option[String] = None,
	  /** Output only. Display name of the project of this subscription. */
		organizationDisplayName: Option[String] = None,
	  /** Output only. Current state of the subscription. */
		state: Option[Schema.Subscription.StateEnum] = None,
	  /** Output only. Map of listing resource names to associated linked resource, e.g. projects/123/locations/US/dataExchanges/456/listings/789 -> projects/123/datasets/my_dataset For listing-level subscriptions, this is a map of size 1. Only contains values if state == STATE_ACTIVE. */
		linkedDatasetMap: Option[Map[String, Schema.LinkedResource]] = None,
	  /** Output only. Email of the subscriber. */
		subscriberContact: Option[String] = None,
	  /** Output only. Linked resources created in the subscription. Only contains values if state = STATE_ACTIVE. */
		linkedResources: Option[List[Schema.LinkedResource]] = None,
	  /** Output only. Listing shared asset type. */
		resourceType: Option[Schema.Subscription.ResourceTypeEnum] = None
	)
	
	case class LinkedResource(
	  /** Output only. Name of the linked dataset, e.g. projects/subscriberproject/datasets/linked_dataset */
		linkedDataset: Option[String] = None,
	  /** Output only. Name of the Pub/Sub subscription, e.g. projects/subscriberproject/subscriptions/subscriptions/sub_id */
		linkedPubsubSubscription: Option[String] = None,
	  /** Output only. Listing for which linked resource is created. */
		listing: Option[String] = None
	)
	
	case class SubscribeDataExchangeRequest(
	  /** Required. The parent resource path of the Subscription. e.g. `projects/subscriberproject/locations/US` */
		destination: Option[String] = None,
	  /** Optional. BigQuery destination dataset to create for the subscriber. */
		destinationDataset: Option[Schema.DestinationDataset] = None,
	  /** Required. Name of the subscription to create. e.g. `subscription1` */
		subscription: Option[String] = None,
	  /** Email of the subscriber. */
		subscriberContact: Option[String] = None
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
	
	case class RefreshSubscriptionRequest(
	
	)
	
	case class ListSubscriptionsResponse(
	  /** The list of subscriptions. */
		subscriptions: Option[List[Schema.Subscription]] = None,
	  /** Next page token. */
		nextPageToken: Option[String] = None
	)
	
	case class ListSharedResourceSubscriptionsResponse(
	  /** The list of subscriptions. */
		sharedResourceSubscriptions: Option[List[Schema.Subscription]] = None,
	  /** Next page token. */
		nextPageToken: Option[String] = None
	)
	
	case class RevokeSubscriptionRequest(
	
	)
	
	case class RevokeSubscriptionResponse(
	
	)
	
	case class GetIamPolicyRequest(
	  /** OPTIONAL: A `GetPolicyOptions` object for specifying options to `GetIamPolicy`. */
		options: Option[Schema.GetPolicyOptions] = None
	)
	
	case class GetPolicyOptions(
	  /** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		requestedPolicyVersion: Option[Int] = None
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
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None,
	  /** OPTIONAL: A FieldMask specifying which fields of the policy to modify. Only the fields in the mask will be modified. If no mask is provided, the following default mask is used: `paths: "bindings, etag"` */
		updateMask: Option[String] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class SubscribeDataExchangeResponse(
	  /** Subscription object created from this subscribe action. */
		subscription: Option[Schema.Subscription] = None
	)
	
	case class RefreshSubscriptionResponse(
	  /** The refreshed subscription resource. */
		subscription: Option[Schema.Subscription] = None
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
