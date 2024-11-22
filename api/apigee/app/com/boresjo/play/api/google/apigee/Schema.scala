package com.boresjo.play.api.google.apigee

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object GoogleCloudApigeeV1DeveloperMonetizationConfig {
		enum BillingTypeEnum extends Enum[BillingTypeEnum] { case BILLING_TYPE_UNSPECIFIED, PREPAID, POSTPAID }
	}
	case class GoogleCloudApigeeV1DeveloperMonetizationConfig(
	  /** Billing type. */
		billingType: Option[Schema.GoogleCloudApigeeV1DeveloperMonetizationConfig.BillingTypeEnum] = None
	)
	
	case class GoogleCloudApigeeV1ListAsyncQueriesResponse(
	  /** The asynchronous queries belong to requested resource name. */
		queries: Option[List[Schema.GoogleCloudApigeeV1AsyncQuery]] = None
	)
	
	case class GoogleCloudApigeeV1ListInstancesResponse(
	  /** Page token that you can include in a ListInstance request to retrieve the next page of content. If omitted, no subsequent pages exist. */
		nextPageToken: Option[String] = None,
	  /** Instances in the specified organization. */
		instances: Option[List[Schema.GoogleCloudApigeeV1Instance]] = None
	)
	
	object GoogleCloudApigeeV1TraceSamplingConfig {
		enum SamplerEnum extends Enum[SamplerEnum] { case SAMPLER_UNSPECIFIED, OFF, PROBABILITY }
	}
	case class GoogleCloudApigeeV1TraceSamplingConfig(
	  /** Sampler of distributed tracing. OFF is the default value. */
		sampler: Option[Schema.GoogleCloudApigeeV1TraceSamplingConfig.SamplerEnum] = None,
	  /** Field sampling rate. This value is only applicable when using the PROBABILITY sampler. The supported values are > 0 and <= 0.5. */
		samplingRate: Option[BigDecimal] = None
	)
	
	case class GoogleCloudApigeeV1GraphqlDocumentation(
	  /** Required. The GraphQL endpoint URI to be queried by API consumers. Max length is 2,083 characters. */
		endpointUri: Option[String] = None,
	  /** Required. The documentation file contents for the GraphQL schema. */
		schema: Option[Schema.GoogleCloudApigeeV1DocumentationFile] = None
	)
	
	object GoogleCloudApigeeV1Alias {
		enum TypeEnum extends Enum[TypeEnum] { case ALIAS_TYPE_UNSPECIFIED, CERT, KEY_CERT }
	}
	case class GoogleCloudApigeeV1Alias(
	  /** Type of alias. */
		`type`: Option[Schema.GoogleCloudApigeeV1Alias.TypeEnum] = None,
	  /** Resource ID for this alias. Values must match the regular expression `[^/]{1,255}`. */
		alias: Option[String] = None,
	  /** Chain of certificates under this alias. */
		certsInfo: Option[Schema.GoogleCloudApigeeV1Certificate] = None
	)
	
	case class GoogleCloudApigeeV1NodeConfig(
	  /** Output only. The current total number of gateway nodes that each environment currently has across all instances. */
		currentAggregateNodeCount: Option[String] = None,
	  /** Optional. The maximum total number of gateway nodes that the is reserved for all instances that has the specified environment. If not specified, the default is determined by the recommended maximum number of nodes for that gateway. */
		maxNodeCount: Option[String] = None,
	  /** Optional. The minimum total number of gateway nodes that the is reserved for all instances that has the specified environment. If not specified, the default is determined by the recommended minimum number of nodes for that gateway. */
		minNodeCount: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ListEnvironmentGroupAttachmentsResponse(
	  /** EnvironmentGroupAttachments for the specified environment group. */
		environmentGroupAttachments: Option[List[Schema.GoogleCloudApigeeV1EnvironmentGroupAttachment]] = None,
	  /** Page token that you can include in a ListEnvironmentGroupAttachments request to retrieve the next page. If omitted, no subsequent pages exist. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1BatchComputeSecurityAssessmentResultsResponse(
	  /** Default sort order is by resource name in alphabetic order. */
		securityAssessmentResults: Option[List[Schema.GoogleCloudApigeeV1SecurityAssessmentResult]] = None,
	  /** The time of the assessment api call. */
		assessmentTime: Option[String] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is blank, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ProfileConfigThreat(
	
	)
	
	case class GoogleCloudApigeeV1SecurityActionsConfig(
	  /** This is a singleton resource, the name will always be set by SecurityActions and any user input will be ignored. The name is always: `organizations/{org}/environments/{env}/security_actions_config` */
		name: Option[String] = None,
	  /** Output only. The update time for configuration. */
		updateTime: Option[String] = None,
	  /** The flag that controls whether this feature is enabled. This is `unset` by default. When this flag is `false`, even if individual rules are enabled, no SecurityActions will be enforced. */
		enabled: Option[Boolean] = None
	)
	
	case class GoogleCloudApigeeV1AdvancedApiOpsConfig(
	  /** Flag that specifies whether the Advanced API Ops add-on is enabled. */
		enabled: Option[Boolean] = None
	)
	
	case class GoogleCloudApigeeV1KeyAliasReference(
	  /** Reference name in the following format: `organizations/{org}/environments/{env}/references/{reference}` */
		reference: Option[String] = None,
	  /** Alias ID. Must exist in the keystore referred to by the reference. */
		aliasId: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1Datastore(
	  /** Output only. Resource link of Datastore. Example: `/organizations/{org}/analytics/datastores/{uuid}` */
		self: Option[String] = None,
	  /** Output only. Datastore last update time, in milliseconds since the epoch of 1970-01-01T00:00:00Z */
		lastUpdateTime: Option[String] = None,
	  /** Required. Display name in UI */
		displayName: Option[String] = None,
	  /** Output only. Organization that the datastore belongs to */
		org: Option[String] = None,
	  /** Destination storage type. Supported types `gcs` or `bigquery`. */
		targetType: Option[String] = None,
	  /** Output only. Datastore create time, in milliseconds since the epoch of 1970-01-01T00:00:00Z */
		createTime: Option[String] = None,
	  /** Datastore Configurations. */
		datastoreConfig: Option[Schema.GoogleCloudApigeeV1DatastoreConfig] = None
	)
	
	case class GoogleCloudApigeeV1SchemaSchemaElement(
	  /** Name of the field. */
		name: Option[String] = None,
	  /** Properties for the schema field. For example: { "createTime": "2016-02-26T10:23:09.592Z", "custom": "false", "type": "string" } */
		properties: Option[Schema.GoogleCloudApigeeV1SchemaSchemaProperty] = None
	)
	
	case class GoogleCloudApigeeV1ListDeveloperSubscriptionsResponse(
	  /** Value that can be sent as `startKey` to retrieve the next page of content. If this field is omitted, there are no subsequent pages. */
		nextStartKey: Option[String] = None,
	  /** List of all subscriptions. */
		developerSubscriptions: Option[List[Schema.GoogleCloudApigeeV1DeveloperSubscription]] = None
	)
	
	case class GoogleCloudApigeeV1SecurityReportMetadata(
	  /** Dimensions of the SecurityReport. */
		dimensions: Option[List[String]] = None,
	  /** Metrics of the SecurityReport. Example: ["name:bot_count,func:sum,alias:sum_bot_count"] */
		metrics: Option[List[String]] = None,
	  /** MIME type / Output format. */
		mimeType: Option[String] = None,
	  /** End timestamp of the query range. */
		endTimestamp: Option[String] = None,
	  /** Query GroupBy time unit. Example: "seconds", "minute", "hour" */
		timeUnit: Option[String] = None,
	  /** Start timestamp of the query range. */
		startTimestamp: Option[String] = None
	)
	
	object GoogleCloudApigeeV1EnvironmentGroup {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, UPDATING }
	}
	case class GoogleCloudApigeeV1EnvironmentGroup(
	  /** ID of the environment group. */
		name: Option[String] = None,
	  /** Required. Host names for this environment group. */
		hostnames: Option[List[String]] = None,
	  /** Output only. State of the environment group. Values other than ACTIVE means the resource is not ready to use. */
		state: Option[Schema.GoogleCloudApigeeV1EnvironmentGroup.StateEnum] = None,
	  /** Output only. The time at which the environment group was last updated as milliseconds since epoch. */
		lastModifiedAt: Option[String] = None,
	  /** Output only. The time at which the environment group was created as milliseconds since epoch. */
		createdAt: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1Schema(
	  /** List of schema fields grouped as dimensions that can be used with an aggregate function such as `sum`, `avg`, `min`, and `max`. */
		metrics: Option[List[Schema.GoogleCloudApigeeV1SchemaSchemaElement]] = None,
	  /** Additional metadata associated with schema. This is a legacy field and usually consists of an empty array of strings. */
		meta: Option[List[String]] = None,
	  /** List of schema fields grouped as dimensions. */
		dimensions: Option[List[Schema.GoogleCloudApigeeV1SchemaSchemaElement]] = None
	)
	
	case class GoogleCloudApigeeV1UpdateSecurityIncidentRequest(
	  /** Required. The security incident to update. Must contain all existing populated fields of the current incident. */
		securityIncident: Option[Schema.GoogleCloudApigeeV1SecurityIncident] = None,
	  /** Required. The list of fields to update. Allowed fields are: LINT.IfChange(allowed_update_fields_comment) - observability LINT.ThenChange() */
		updateMask: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1RuntimeTraceConfigOverride(
	  /** Name of the trace config override in the following format: `organizations/{org}/environment/{env}/traceConfig/overrides/{override}` */
		name: Option[String] = None,
	  /** Trace configuration override for a specific API proxy in an environment. */
		samplingConfig: Option[Schema.GoogleCloudApigeeV1RuntimeTraceSamplingConfig] = None,
	  /** The timestamp that the revision was created or updated. */
		revisionCreateTime: Option[String] = None,
	  /** Name of the API proxy that will have its trace configuration overridden following format: `organizations/{org}/apis/{api}` */
		apiProxy: Option[String] = None,
	  /** Revision number which can be used by the runtime to detect if the trace config override has changed between two versions. */
		revisionId: Option[String] = None,
	  /** Unique ID for the configuration override. The ID will only change if the override is deleted and recreated. Corresponds to name's "override" field. */
		uid: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1OptimizedStatsResponse(
	  /** `stats` results. */
		stats: Option[Schema.GoogleCloudApigeeV1OptimizedStatsNode] = None,
	  /** Boolean flag that indicates whether the results were truncated based on the limit parameter. */
		resultTruncated: Option[Boolean] = None,
	  /** List of time unit values. Time unit refers to an epoch timestamp value. */
		TimeUnit: Option[List[String]] = None,
	  /** Metadata information about the query executed. */
		metaData: Option[Schema.GoogleCloudApigeeV1Metadata] = None
	)
	
	case class GoogleCloudApigeeV1InstanceAttachment(
	  /** Output only. Time the attachment was created in milliseconds since epoch. */
		createdAt: Option[String] = None,
	  /** Output only. ID of the attachment. */
		name: Option[String] = None,
	  /** ID of the attached environment. */
		environment: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ProfileConfigMTLS(
	
	)
	
	case class GoogleCloudApigeeV1ScoreComponentRecommendation(
	  /** Potential impact of this recommendation on the overall score. This denotes how important this recommendation is to improve the score. */
		impact: Option[Int] = None,
	  /** Description of the recommendation. */
		description: Option[String] = None,
	  /** Actions for the recommendation to improve the security score. */
		actions: Option[List[Schema.GoogleCloudApigeeV1ScoreComponentRecommendationAction]] = None,
	  /** Title represents recommendation title. */
		title: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1AsyncQueryResultView(
	  /** Rows of query result. Each row is a JSON object. Example: {sum(message_count): 1, developer_app: "(not set)",…} */
		rows: Option[List[JsValue]] = None,
	  /** Error message when there is a failure. */
		error: Option[String] = None,
	  /** State of retrieving ResultView. */
		state: Option[String] = None,
	  /** Error code when there is a failure. */
		code: Option[Int] = None,
	  /** Metadata contains information like metrics, dimenstions etc of the AsyncQuery. */
		metadata: Option[Schema.GoogleCloudApigeeV1QueryMetadata] = None
	)
	
	case class GoogleCloudApigeeV1OperationGroup(
	  /** Required. List of operation configurations for either Apigee API proxies or other remote services that are associated with this API product. */
		operationConfigs: Option[List[Schema.GoogleCloudApigeeV1OperationConfig]] = None,
	  /** Flag that specifes whether the configuration is for Apigee API proxy or a remote service. Valid values include `proxy` or `remoteservice`. Defaults to `proxy`. Set to `proxy` when Apigee API proxies are associated with the API product. Set to `remoteservice` when non-Apigee proxies like Istio-Envoy are associated with the API product. */
		operationConfigType: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1OptimizedStats(
	  /** Wraps the `stats` response for JavaScript Optimized Scenario with a response key. For example: ```{ "Response": { "TimeUnit": [], "metaData": { "errors": [], "notices": [ "Source:Postgres", "Table used: edge.api.aaxgroup001.agg_api", "PG Host:ruappg08-ro.production.apigeeks.net", "query served by:80c4ebca-6a10-4a2e-8faf-c60c1ee306ca" ] }, "resultTruncated": false, "stats": { "data": [ { "identifier": { "names": [ "apiproxy" ], "values": [ "sirjee" ] }, "metric": [ { "env": "prod", "name": "sum(message_count)", "values": [ 36.0 ] }, { "env": "prod", "name": "sum(is_error)", "values": [ 36.0 ] } ] } ] } } }``` */
		Response: Option[Schema.GoogleCloudApigeeV1OptimizedStatsResponse] = None
	)
	
	case class GoogleCloudApigeeV1QueryTabularStatsRequest(
	  /** Time range for the stats. */
		timeRange: Option[Schema.GoogleTypeInterval] = None,
	  /** Required. List of dimension names to group the aggregations by. */
		dimensions: Option[List[String]] = None,
	  /** Filter further on specific dimension values. Follows the same grammar as custom report's filter expressions. Example, apiproxy eq 'foobar'. https://cloud.google.com/apigee/docs/api-platform/analytics/analytics-reference#filters */
		filter: Option[String] = None,
	  /** Identifies a sequence of rows. */
		pageToken: Option[String] = None,
	  /** Page size represents the number of rows. */
		pageSize: Option[Int] = None,
	  /** Required. List of metrics and their aggregations. */
		metrics: Option[List[Schema.GoogleCloudApigeeV1MetricAggregation]] = None
	)
	
	case class GoogleCloudApigeeV1SecurityActionAllow(
	
	)
	
	case class GoogleCloudApigeeV1ProfileConfigAbuse(
	
	)
	
	case class GoogleCloudApigeeV1Result(
	  /** The relative path of the api proxy. for example, `"uRI" : "/iloveapis"` */
		uRI: Option[String] = None,
	  /** A list of HTTP headers. for example, '"headers" : [ { "name" : "Content-Length", "value" : "83" }, { "name" : "Content-Type", "value" : "application/json" } ]' */
		headers: Option[List[Schema.GoogleCloudApigeeV1Property]] = None,
	  /** Type of the action result. Can be one of the five: DebugInfo, RequestMessage, ResponseMessage, ErrorMessage, VariableAccess */
		ActionResult: Option[String] = None,
	  /** Error message content. for example, "content" : "{\"fault\":{\"faultstring\":\"API timed out\",\"detail\":{\"errorcode\":\"flow.APITimedOut\"}}}" */
		content: Option[String] = None,
	  /** A list of variable access actions agaist the api proxy. Supported values: Get, Set, Remove. */
		accessList: Option[List[Schema.GoogleCloudApigeeV1Access]] = None,
	  /** HTTP response phrase */
		reasonPhrase: Option[String] = None,
	  /** HTTP response code */
		statusCode: Option[String] = None,
	  /** Name value pairs used for DebugInfo ActionResult. */
		properties: Option[Schema.GoogleCloudApigeeV1Properties] = None,
	  /** Timestamp of when the result is recorded. Its format is dd-mm-yy hh:mm:ss:xxx. For example, `"timestamp" : "12-08-19 00:31:59:960"` */
		timestamp: Option[String] = None,
	  /** HTTP method verb */
		verb: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1SecurityAssessmentResult(
	  /** The result of the assessment. */
		scoringResult: Option[Schema.GoogleCloudApigeeV1SecurityAssessmentResultScoringResult] = None,
	  /** The time of the assessment of this resource. This could lag behind `assessment_time` due to caching within the backend. */
		createTime: Option[String] = None,
	  /** The error status if scoring fails. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** The assessed resource. */
		resource: Option[Schema.GoogleCloudApigeeV1SecurityAssessmentResultResource] = None
	)
	
	case class GoogleTypeInterval(
	  /** Optional. Inclusive start of the interval. If specified, a Timestamp matching this interval will have to be the same or after the start. */
		startTime: Option[String] = None,
	  /** Optional. Exclusive end of the interval. If specified, a Timestamp matching this interval will have to be before the end. */
		endTime: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ActivateNatAddressRequest(
	
	)
	
	case class GoogleCloudApigeeV1ResourceFile(
	  /** ID of the resource file. */
		name: Option[String] = None,
	  /** Resource file type. {{ resource_file_type }} */
		`type`: Option[String] = None
	)
	
	object GoogleCloudApigeeV1SecurityAssessmentResultScoringResult {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, LOW, MEDIUM, HIGH, MINIMAL }
	}
	case class GoogleCloudApigeeV1SecurityAssessmentResultScoringResult(
	  /** The security score of the assessment. */
		score: Option[Int] = None,
	  /** The number of failed assessments grouped by its weight. Keys are one of the following: "MAJOR", "MODERATE", "MINOR". */
		failedAssessmentPerWeight: Option[Map[String, Int]] = None,
	  /** The time when resource data was last fetched for this resource. This time may be different than when the resource was actually updated due to lag in data collection. */
		dataUpdateTime: Option[String] = None,
	  /** The severity of the assessment. */
		severity: Option[Schema.GoogleCloudApigeeV1SecurityAssessmentResultScoringResult.SeverityEnum] = None,
	  /** The recommendations of the assessment. The key is the "name" of the assessment (not display_name), and the value are the recommendations. */
		assessmentRecommendations: Option[Map[String, Schema.GoogleCloudApigeeV1SecurityAssessmentResultScoringResultAssessmentRecommendation]] = None
	)
	
	object GoogleCloudApigeeV1QueryTimeSeriesStatsRequest {
		enum TimestampOrderEnum extends Enum[TimestampOrderEnum] { case ORDER_UNSPECIFIED, ASCENDING, DESCENDING }
		enum WindowSizeEnum extends Enum[WindowSizeEnum] { case WINDOW_SIZE_UNSPECIFIED, MINUTE, HOUR, DAY, MONTH }
	}
	case class GoogleCloudApigeeV1QueryTimeSeriesStatsRequest(
	  /** Required. List of metrics and their aggregations. */
		metrics: Option[List[Schema.GoogleCloudApigeeV1MetricAggregation]] = None,
	  /** Filter further on specific dimension values. Follows the same grammar as custom report's filter expressions. Example, apiproxy eq 'foobar'. https://cloud.google.com/apigee/docs/api-platform/analytics/analytics-reference#filters */
		filter: Option[String] = None,
	  /** Order the sequences in increasing or decreasing order of timestamps. Default is descending order of timestamps (latest first). */
		timestampOrder: Option[Schema.GoogleCloudApigeeV1QueryTimeSeriesStatsRequest.TimestampOrderEnum] = None,
	  /** Page token stands for a specific collection of time series sequences. */
		pageToken: Option[String] = None,
	  /** Page size represents the number of time series sequences, one per unique set of dimensions and their values. */
		pageSize: Option[Int] = None,
	  /** List of dimension names to group the aggregations by. If no dimensions are passed, a single trend line representing the requested metric aggregations grouped by environment is returned. */
		dimensions: Option[List[String]] = None,
	  /** Time buckets to group the stats by. */
		windowSize: Option[Schema.GoogleCloudApigeeV1QueryTimeSeriesStatsRequest.WindowSizeEnum] = None,
	  /** Required. Time range for the stats. */
		timeRange: Option[Schema.GoogleTypeInterval] = None
	)
	
	object GoogleCloudApigeeV1TraceConfig {
		enum ExporterEnum extends Enum[ExporterEnum] { case EXPORTER_UNSPECIFIED, JAEGER, CLOUD_TRACE }
	}
	case class GoogleCloudApigeeV1TraceConfig(
	  /** Required. Endpoint of the exporter. */
		endpoint: Option[String] = None,
	  /** Distributed trace configuration for all API proxies in an environment. You can also override the configuration for a specific API proxy using the distributed trace configuration overrides API. */
		samplingConfig: Option[Schema.GoogleCloudApigeeV1TraceSamplingConfig] = None,
	  /** Required. Exporter that is used to view the distributed trace captured using OpenCensus. An exporter sends traces to any backend that is capable of consuming them. Recorded spans can be exported by registered exporters. */
		exporter: Option[Schema.GoogleCloudApigeeV1TraceConfig.ExporterEnum] = None
	)
	
	case class GoogleCloudApigeeV1QueryTimeSeriesStatsResponseSequence(
	  /** Map of dimensions and their values that uniquely identifies a time series sequence. */
		dimensions: Option[Map[String, String]] = None,
	  /** List of points. First value of each inner list is a timestamp. */
		points: Option[List[List[JsValue]]] = None
	)
	
	case class GoogleCloudApigeeV1SetAddonEnablementRequest(
	  /** If the API Security should be enabled in the environment. */
		apiSecurityEnabled: Option[Boolean] = None,
	  /** If the Analytics should be enabled in the environment. */
		analyticsEnabled: Option[Boolean] = None
	)
	
	case class GoogleCloudApigeeV1SecurityProfileEnvironment(
	  /** Output only. Time at which environment was attached to the security profile. */
		attachTime: Option[String] = None,
	  /** Output only. Name of the environment. */
		environment: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1SchemaSchemaProperty(
	  /** Time the field was created in RFC3339 string form. For example: `2016-02-26T10:23:09.592Z`. */
		createTime: Option[String] = None,
	  /** Data type of the field. */
		`type`: Option[String] = None,
	  /** Flag that specifies whether the field is standard in the dataset or a custom field created by the customer. `true` indicates that it is a custom field. */
		custom: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1SecurityActionHttpHeader(
	  /** The header name to be sent to the target. */
		name: Option[String] = None,
	  /** The header value to be sent to the target. */
		value: Option[String] = None
	)
	
	object GoogleCloudApigeeV1Organization {
		enum BillingTypeEnum extends Enum[BillingTypeEnum] { case BILLING_TYPE_UNSPECIFIED, SUBSCRIPTION, EVALUATION, PAYG }
		enum SubscriptionTypeEnum extends Enum[SubscriptionTypeEnum] { case SUBSCRIPTION_TYPE_UNSPECIFIED, PAID, TRIAL }
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, TYPE_TRIAL, TYPE_PAID, TYPE_INTERNAL }
		enum SubscriptionPlanEnum extends Enum[SubscriptionPlanEnum] { case SUBSCRIPTION_PLAN_UNSPECIFIED, SUBSCRIPTION_2021, SUBSCRIPTION_2024 }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, UPDATING }
		enum RuntimeTypeEnum extends Enum[RuntimeTypeEnum] { case RUNTIME_TYPE_UNSPECIFIED, CLOUD, HYBRID }
	}
	case class GoogleCloudApigeeV1Organization(
	  /** Output only. Time that the Apigee organization was created in milliseconds since epoch. */
		createdAt: Option[String] = None,
	  /** Output only. Name of the Apigee organization. */
		name: Option[String] = None,
	  /** This field is needed only for customers using non-default data residency regions. Apigee stores some control plane data only in single region. This field determines which single region Apigee should use. For example: "us-west1" when control plane is in US or "europe-west2" when control plane is in EU. */
		apiConsumerDataLocation: Option[String] = None,
	  /** Output only. Base64-encoded public certificate for the root CA of the Apigee organization. Valid only when [RuntimeType](#RuntimeType) is `CLOUD`. */
		caCertificate: Option[String] = None,
	  /** Output only. Time that the Apigee organization is scheduled for deletion. */
		expiresAt: Option[String] = None,
	  /** Display name for the Apigee organization. Unused, but reserved for future use. */
		displayName: Option[String] = None,
	  /** Billing type of the Apigee organization. See [Apigee pricing](https://cloud.google.com/apigee/pricing). */
		billingType: Option[Schema.GoogleCloudApigeeV1Organization.BillingTypeEnum] = None,
	  /** Not used by Apigee. */
		customerName: Option[String] = None,
	  /** Optional. Flag that specifies whether the VPC Peering through Private Google Access should be disabled between the consumer network and Apigee. Valid only when RuntimeType is set to CLOUD. Required if an authorizedNetwork on the consumer project is not provided, in which case the flag should be set to true. The value must be set before the creation of any Apigee runtime instance and can be updated only when there are no runtime instances. &#42;&#42;Note:&#42;&#42; Apigee will be deprecating the vpc peering model that requires you to provide 'authorizedNetwork', by making the non-peering model as the default way of provisioning Apigee organization in future. So, this will be a temporary flag to enable the transition. Not supported for Apigee hybrid. */
		disableVpcPeering: Option[Boolean] = None,
	  /** Description of the Apigee organization. */
		description: Option[String] = None,
	  /** Properties defined in the Apigee organization profile. */
		properties: Option[Schema.GoogleCloudApigeeV1Properties] = None,
	  /** Output only. Time that the Apigee organization was last modified in milliseconds since epoch. */
		lastModifiedAt: Option[String] = None,
	  /** Not used by Apigee. */
		attributes: Option[List[String]] = None,
	  /** Cloud KMS key name used for encrypting control plane data that is stored in a multi region. Only used for the data residency region "US" or "EU". If not specified or [BillingType](#BillingType) is `EVALUATION`, a Google-Managed encryption key will be used. Format: `projects/&#42;/locations/&#42;/keyRings/&#42;/cryptoKeys/&#42;` */
		controlPlaneEncryptionKeyName: Option[String] = None,
	  /** Cloud KMS key name used for encrypting the data that is stored and replicated across runtime instances. Update is not allowed after the organization is created. If not specified or [RuntimeType](#RuntimeType) is `TRIAL`, a Google-Managed encryption key will be used. For example: "projects/foo/locations/us/keyRings/bar/cryptoKeys/baz". &#42;&#42;Note:&#42;&#42; Not supported for Apigee hybrid. */
		runtimeDatabaseEncryptionKeyName: Option[String] = None,
	  /** Output only. DEPRECATED: This will eventually be replaced by BillingType. Subscription type of the Apigee organization. Valid values include trial (free, limited, and for evaluation purposes only) or paid (full subscription has been purchased). See [Apigee pricing](https://cloud.google.com/apigee/pricing/). */
		subscriptionType: Option[Schema.GoogleCloudApigeeV1Organization.SubscriptionTypeEnum] = None,
	  /** Not used by Apigee. */
		`type`: Option[Schema.GoogleCloudApigeeV1Organization.TypeEnum] = None,
	  /** Output only. Subscription plan that the customer has purchased. Output only. */
		subscriptionPlan: Option[Schema.GoogleCloudApigeeV1Organization.SubscriptionPlanEnum] = None,
	  /** Addon configurations of the Apigee organization. */
		addonsConfig: Option[Schema.GoogleCloudApigeeV1AddonsConfig] = None,
	  /** Output only. State of the organization. Values other than ACTIVE means the resource is not ready to use. */
		state: Option[Schema.GoogleCloudApigeeV1Organization.StateEnum] = None,
	  /** Output only. Project ID associated with the Apigee organization. */
		projectId: Option[String] = None,
	  /** Required. Runtime type of the Apigee organization based on the Apigee subscription purchased. */
		runtimeType: Option[Schema.GoogleCloudApigeeV1Organization.RuntimeTypeEnum] = None,
	  /** Output only. Apigee Project ID associated with the organization. Use this project to allowlist Apigee in the Service Attachment when using private service connect with Apigee. */
		apigeeProjectId: Option[String] = None,
	  /** Compute Engine network used for Service Networking to be peered with Apigee runtime instances. See [Getting started with the Service Networking API](https://cloud.google.com/service-infrastructure/docs/service-networking/getting-started). Valid only when [RuntimeType](#RuntimeType) is set to `CLOUD`. The value must be set before the creation of a runtime instance and can be updated only when there are no runtime instances. For example: `default`. Apigee also supports shared VPC (that is, the host network project is not the same as the one that is peering with Apigee). See [Shared VPC overview](https://cloud.google.com/vpc/docs/shared-vpc). To use a shared VPC network, use the following format: `projects/{host-project-id}/{region}/networks/{network-name}`. For example: `projects/my-sharedvpc-host/global/networks/mynetwork` &#42;&#42;Note:&#42;&#42; Not supported for Apigee hybrid. */
		authorizedNetwork: Option[String] = None,
	  /** Configuration for the Portals settings. */
		portalDisabled: Option[Boolean] = None,
	  /** Output only. List of environments in the Apigee organization. */
		environments: Option[List[String]] = None,
	  /** Required. DEPRECATED: This field will eventually be deprecated and replaced with a differently-named field. Primary Google Cloud region for analytics data storage. For valid values, see [Create an Apigee organization](https://cloud.google.com/apigee/docs/api-platform/get-started/create-org). */
		analyticsRegion: Option[String] = None,
	  /** Cloud KMS key name used for encrypting API consumer data. If not specified or [BillingType](#BillingType) is `EVALUATION`, a Google-Managed encryption key will be used. Format: `projects/&#42;/locations/&#42;/keyRings/&#42;/cryptoKeys/&#42;` */
		apiConsumerDataEncryptionKeyName: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1EnvironmentGroupConfig(
	  /** Ordered list of routing rules defining how traffic to this environment group's hostnames should be routed to different environments. */
		routingRules: Option[List[Schema.GoogleCloudApigeeV1RoutingRule]] = None,
	  /** When this message appears in the top-level IngressConfig, this field will be populated in lieu of the inlined routing_rules and hostnames fields. Some URL for downloading the full EnvironmentGroupConfig for this group. */
		location: Option[String] = None,
	  /** Name of the environment group in the following format: `organizations/{org}/envgroups/{envgroup}`. */
		name: Option[String] = None,
	  /** Host names for the environment group. */
		hostnames: Option[List[String]] = None,
	  /** A list of proxies in each deployment group for proxy chaining calls. */
		endpointChainingRules: Option[List[Schema.GoogleCloudApigeeV1EndpointChainingRule]] = None,
	  /** Revision id that defines the ordering of the EnvironmentGroupConfig resource. The higher the revision, the more recently the configuration was deployed. */
		revisionId: Option[String] = None,
	  /** A unique id for the environment group config that will only change if the environment group is deleted and recreated. */
		uid: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1QueryTabularStatsResponse(
	  /** Resultant rows from the executed query. */
		values: Option[List[List[JsValue]]] = None,
	  /** Column names corresponding to the same order as the inner values in the stats field. */
		columns: Option[List[String]] = None,
	  /** Next page token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1OrganizationProjectMapping(
	  /** Name of the Apigee organization. */
		organization: Option[String] = None,
	  /** DEPRECATED: Use `project_id`. An Apigee Organization is mapped to a single project. */
		projectIds: Option[List[String]] = None,
	  /** Google Cloud project associated with the Apigee organization */
		projectId: Option[String] = None,
	  /** Output only. The Google Cloud region where control plane data is located. For more information, see https://cloud.google.com/about/locations/. */
		location: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ListAppsResponse(
		app: Option[List[Schema.GoogleCloudApigeeV1App]] = None,
	  /** Total count of Apps. */
		totalSize: Option[Int] = None,
	  /** Token that can be sent as `next_page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1GraphQLOperation(
	  /** GraphQL operation name. The name and operation type will be used to apply quotas. If no name is specified, the quota will be applied to all GraphQL operations irrespective of their operation names in the payload. */
		operation: Option[String] = None,
	  /** Required. GraphQL operation types. Valid values include `query` or `mutation`. &#42;&#42;Note&#42;&#42;: Apigee does not currently support `subscription` types. */
		operationTypes: Option[List[String]] = None
	)
	
	case class GoogleCloudApigeeV1ListApiDocsResponse(
	  /** Unique error code for the request, if any. */
		errorCode: Option[String] = None,
	  /** Unique ID of the request. */
		requestId: Option[String] = None,
	  /** The catalog item resources. */
		data: Option[List[Schema.GoogleCloudApigeeV1ApiDoc]] = None,
	  /** Description of the operation. */
		message: Option[String] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Status of the operation. */
		status: Option[String] = None
	)
	
	object GoogleCloudApigeeV1AliasRevisionConfig {
		enum TypeEnum extends Enum[TypeEnum] { case ALIAS_TYPE_UNSPECIFIED, CERT, KEY_CERT }
	}
	case class GoogleCloudApigeeV1AliasRevisionConfig(
		`type`: Option[Schema.GoogleCloudApigeeV1AliasRevisionConfig.TypeEnum] = None,
	  /** Name of the alias revision included in the keystore in the following format: `organizations/{org}/environments/{env}/keystores/{keystore}/aliases/{alias}/revisions/{rev}` */
		name: Option[String] = None,
	  /** Location of the alias file. For example, a Google Cloud Storage URI. */
		location: Option[String] = None
	)
	
	case class GoogleTypeExpr(
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None,
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ProvisionOrganizationRequest(
	  /** Optional. Flag that specifies whether the VPC Peering through Private Google Access should be disabled between the consumer network and Apigee. Required if an authorizedNetwork on the consumer project is not provided, in which case the flag should be set to true. The value must be set before the creation of any Apigee runtime instance and can be updated only when there are no runtime instances. &#42;&#42;Note:&#42;&#42; Apigee will be deprecating the vpc peering model that requires you to provide 'authorizedNetwork', by making the non-peering model as the default way of provisioning Apigee organization in future. So, this will be a temporary flag to enable the transition. Not supported for Apigee hybrid. */
		disableVpcPeering: Option[Boolean] = None,
	  /** Cloud Platform location for the runtime instance. Defaults to zone `us-west1-a`. If a region is provided, `EVAL` organizations will use the region for automatically selecting a zone for the runtime instance. */
		runtimeLocation: Option[String] = None,
	  /** Primary Cloud Platform region for analytics data storage. For valid values, see [Create an organization](https://cloud.google.com/apigee/docs/hybrid/latest/precog-provision). Defaults to `us-west1`. */
		analyticsRegion: Option[String] = None,
	  /** Compute Engine network used for Service Networking to be peered with Apigee runtime instances. See [Getting started with the Service Networking API](https://cloud.google.com/service-infrastructure/docs/service-networking/getting-started). Apigee also supports shared VPC (that is, the host network project is not the same as the one that is peering with Apigee). See [Shared VPC overview](https://cloud.google.com/vpc/docs/shared-vpc). To use a shared VPC network, use the following format: `projects/{host-project-id}/{region}/networks/{network-name}`. For example: `projects/my-sharedvpc-host/global/networks/mynetwork` */
		authorizedNetwork: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1DatastoreConfig(
	  /** BigQuery dataset name Required for `bigquery` target_type. */
		datasetName: Option[String] = None,
	  /** Required. Google Cloud project in which the datastore exists */
		projectId: Option[String] = None,
	  /** Prefix of BigQuery table Required for `bigquery` target_type. */
		tablePrefix: Option[String] = None,
	  /** Path of Cloud Storage bucket Required for `gcs` target_type. */
		path: Option[String] = None,
	  /** Name of the Cloud Storage bucket. Required for `gcs` target_type. */
		bucketName: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1Keystore(
	  /** Output only. Aliases in this keystore. */
		aliases: Option[List[String]] = None,
	  /** Required. Resource ID for this keystore. Values must match the regular expression `[\w[:space:].-]{1,255}`. */
		name: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1RuntimeConfig(
	  /** Output only. Tenant project ID associated with the Apigee organization. The tenant project is used to host Google-managed resources that are dedicated to this Apigee organization. Clients have limited access to resources within the tenant project used to support Apigee runtime instances. Access to the tenant project is managed using SetSyncAuthorization. It can be empty if the tenant project hasn't been created yet. */
		tenantProjectId: Option[String] = None,
	  /** Name of the resource in the following format: `organizations/{org}/runtimeConfig`. */
		name: Option[String] = None,
	  /** Cloud Storage bucket used for uploading Analytics records. */
		analyticsBucket: Option[String] = None,
	  /** Cloud Storage bucket used for uploading Trace records. */
		traceBucket: Option[String] = None
	)
	
	object GoogleCloudApigeeV1EndpointAttachment {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, UPDATING }
		enum ConnectionStateEnum extends Enum[ConnectionStateEnum] { case CONNECTION_STATE_UNSPECIFIED, UNAVAILABLE, PENDING, ACCEPTED, REJECTED, CLOSED, FROZEN, NEEDS_ATTENTION }
	}
	case class GoogleCloudApigeeV1EndpointAttachment(
	  /** Output only. State of the endpoint attachment. Values other than `ACTIVE` mean the resource is not ready to use. */
		state: Option[Schema.GoogleCloudApigeeV1EndpointAttachment.StateEnum] = None,
	  /** Format: projects/&#42;/regions/&#42;/serviceAttachments/&#42; */
		serviceAttachment: Option[String] = None,
	  /** Output only. Host that can be used in either the HTTP target endpoint directly or as the host in target server. */
		host: Option[String] = None,
	  /** Output only. State of the endpoint attachment connection to the service attachment. */
		connectionState: Option[Schema.GoogleCloudApigeeV1EndpointAttachment.ConnectionStateEnum] = None,
	  /** Required. Location of the endpoint attachment. */
		location: Option[String] = None,
	  /** Name of the endpoint attachment. Use the following structure in your request: `organizations/{org}/endpointAttachments/{endpoint_attachment}` */
		name: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1DeveloperApp(
	  /** Output only. Time the developer app was created in milliseconds since epoch. */
		createdAt: Option[String] = None,
	  /** Name of the developer app. */
		name: Option[String] = None,
	  /** Expiration time, in milliseconds, for the consumer key that is generated for the developer app. If not set or left to the default value of `-1`, the API key never expires. The expiration time can't be updated after it is set. */
		keyExpiresIn: Option[String] = None,
	  /** List of API products associated with the developer app. */
		apiProducts: Option[List[String]] = None,
	  /** ID of the developer. */
		developerId: Option[String] = None,
	  /** Scopes to apply to the developer app. The specified scopes must already exist for the API product that you associate with the developer app. */
		scopes: Option[List[String]] = None,
	  /** ID of the developer app. */
		appId: Option[String] = None,
	  /** Status of the credential. Valid values include `approved` or `revoked`. */
		status: Option[String] = None,
	  /** Output only. Set of credentials for the developer app consisting of the consumer key/secret pairs associated with the API products. */
		credentials: Option[List[Schema.GoogleCloudApigeeV1Credential]] = None,
	  /** List of attributes for the developer app. */
		attributes: Option[List[Schema.GoogleCloudApigeeV1Attribute]] = None,
	  /** Developer app family. */
		appFamily: Option[String] = None,
	  /** Callback URL used by OAuth 2.0 authorization servers to communicate authorization codes back to developer apps. */
		callbackUrl: Option[String] = None,
	  /** Output only. Time the developer app was modified in milliseconds since epoch. */
		lastModifiedAt: Option[String] = None
	)
	
	object GoogleCloudApigeeV1Environment {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, UPDATING }
		enum TypeEnum extends Enum[TypeEnum] { case ENVIRONMENT_TYPE_UNSPECIFIED, BASE, INTERMEDIATE, COMPREHENSIVE }
		enum DeploymentTypeEnum extends Enum[DeploymentTypeEnum] { case DEPLOYMENT_TYPE_UNSPECIFIED, PROXY, ARCHIVE }
		enum ApiProxyTypeEnum extends Enum[ApiProxyTypeEnum] { case API_PROXY_TYPE_UNSPECIFIED, PROGRAMMABLE, CONFIGURABLE }
	}
	case class GoogleCloudApigeeV1Environment(
	  /** Optional. Display name for this environment. */
		displayName: Option[String] = None,
	  /** Optional. NodeConfig of the environment. */
		nodeConfig: Option[Schema.GoogleCloudApigeeV1NodeConfig] = None,
	  /** Required. Name of the environment. Values must match the regular expression `^[.\\p{Alnum}-_]{1,255}$` */
		name: Option[String] = None,
	  /** Output only. Creation time of this environment as milliseconds since epoch. */
		createdAt: Option[String] = None,
	  /** Output only. State of the environment. Values other than ACTIVE means the resource is not ready to use. */
		state: Option[Schema.GoogleCloudApigeeV1Environment.StateEnum] = None,
	  /** Optional. EnvironmentType selected for the environment. */
		`type`: Option[Schema.GoogleCloudApigeeV1Environment.TypeEnum] = None,
		hasAttachedFlowHooks: Option[Boolean] = None,
	  /** Optional. Deployment type supported by the environment. The deployment type can be set when creating the environment and cannot be changed. When you enable archive deployment, you will be &#42;&#42;prevented from performing&#42;&#42; a [subset of actions](/apigee/docs/api-platform/local-development/overview#prevented-actions) within the environment, including: &#42; Managing the deployment of API proxy or shared flow revisions &#42; Creating, updating, or deleting resource files &#42; Creating, updating, or deleting target servers */
		deploymentType: Option[Schema.GoogleCloudApigeeV1Environment.DeploymentTypeEnum] = None,
	  /** Optional. API Proxy type supported by the environment. The type can be set when creating the Environment and cannot be changed. */
		apiProxyType: Option[Schema.GoogleCloudApigeeV1Environment.ApiProxyTypeEnum] = None,
	  /** Optional. URI of the forward proxy to be applied to the runtime instances in this environment. Must be in the format of {scheme}://{hostname}:{port}. Note that the scheme must be one of "http" or "https", and the port must be supplied. To remove a forward proxy setting, update the field to an empty value. Note: At this time, PUT operations to add forwardProxyUri to an existing environment fail if the environment has nodeConfig set up. To successfully add the forwardProxyUri setting in this case, include the NodeConfig details with the request. */
		forwardProxyUri: Option[String] = None,
	  /** Optional. Key-value pairs that may be used for customizing the environment. */
		properties: Option[Schema.GoogleCloudApigeeV1Properties] = None,
	  /** Optional. Description of the environment. */
		description: Option[String] = None,
	  /** Output only. Last modification time of this environment as milliseconds since epoch. */
		lastModifiedAt: Option[String] = None
	)
	
	object GoogleCloudApigeeV1DataCollectorConfig {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, INTEGER, FLOAT, STRING, BOOLEAN, DATETIME }
	}
	case class GoogleCloudApigeeV1DataCollectorConfig(
	  /** Name of the data collector in the following format: `organizations/{org}/datacollectors/{datacollector}` */
		name: Option[String] = None,
	  /** Data type accepted by the data collector. */
		`type`: Option[Schema.GoogleCloudApigeeV1DataCollectorConfig.TypeEnum] = None
	)
	
	case class GoogleCloudApigeeV1DeveloperAppKey(
	  /** Scopes to apply to the app. The specified scope names must already be defined for the API product that you associate with the app. */
		scopes: Option[List[String]] = None,
	  /** Input only. Expiration time, in seconds, for the consumer key. If not set or left to the default value of `-1`, the API key never expires. The expiration time can't be updated after it is set. */
		expiresInSeconds: Option[String] = None,
	  /** Time the developer app was created in milliseconds since epoch. */
		issuedAt: Option[String] = None,
	  /** Consumer key. */
		consumerKey: Option[String] = None,
	  /** List of API products for which the credential can be used. &#42;&#42;Note&#42;&#42;: Do not specify the list of API products when creating a consumer key and secret for a developer app. Instead, use the UpdateDeveloperAppKey API to make the association after the consumer key and secret are created. */
		apiProducts: Option[List[JsValue]] = None,
	  /** Time the developer app expires in milliseconds since epoch. */
		expiresAt: Option[String] = None,
	  /** List of attributes associated with the credential. */
		attributes: Option[List[Schema.GoogleCloudApigeeV1Attribute]] = None,
	  /** Status of the credential. Valid values include `approved` or `revoked`. */
		status: Option[String] = None,
	  /** Secret key. */
		consumerSecret: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1InstanceDeploymentStatus(
	  /** Revisions currently deployed in MPs. */
		deployedRevisions: Option[List[Schema.GoogleCloudApigeeV1InstanceDeploymentStatusDeployedRevision]] = None,
	  /** Current routes deployed in the ingress routing table. A route which is missing will appear in `missing_routes`. */
		deployedRoutes: Option[List[Schema.GoogleCloudApigeeV1InstanceDeploymentStatusDeployedRoute]] = None,
	  /** ID of the instance reporting the status. */
		instance: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1SecurityAssessmentResultScoringResultAssessmentRecommendationRecommendation(
	  /** The description of the recommendation. */
		description: Option[String] = None,
	  /** The link for the recommendation. */
		link: Option[Schema.GoogleCloudApigeeV1SecurityAssessmentResultScoringResultAssessmentRecommendationRecommendationLink] = None
	)
	
	case class GoogleCloudApigeeV1DeploymentChangeReportRoutingDeployment(
	  /** Name of the environment in which the proxy is deployed. */
		environment: Option[String] = None,
	  /** Name of the deployed API proxy revision containing the base path. */
		apiProxy: Option[String] = None,
	  /** Base path receiving traffic. */
		basepath: Option[String] = None,
	  /** Name of the deployed API proxy revision containing the base path. */
		revision: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1BatchUpdateSecurityIncidentsResponse(
	  /** Output only. Updated security incidents */
		securityIncidents: Option[List[Schema.GoogleCloudApigeeV1SecurityIncident]] = None
	)
	
	case class GoogleCloudApigeeV1AccessLoggingConfig(
	  /** Optional. Boolean flag that specifies whether the customer access log feature is enabled. */
		enabled: Option[Boolean] = None,
	  /** Optional. Ship the access log entries that match the status_code defined in the filter. The status_code is the only expected/supported filter field. (Ex: status_code) The filter will parse it to the Common Expression Language semantics for expression evaluation to build the filter condition. (Ex: "filter": status_code >= 200 && status_code < 300 ) */
		filter: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ListDeveloperAppsResponse(
	  /** List of developer apps and their credentials. */
		app: Option[List[Schema.GoogleCloudApigeeV1DeveloperApp]] = None
	)
	
	case class GoogleCloudApigeeV1ListDeploymentsResponse(
	  /** List of deployments. */
		deployments: Option[List[Schema.GoogleCloudApigeeV1Deployment]] = None
	)
	
	object GoogleCloudApigeeV1Instance {
		enum PeeringCidrRangeEnum extends Enum[PeeringCidrRangeEnum] { case CIDR_RANGE_UNSPECIFIED, SLASH_16, SLASH_17, SLASH_18, SLASH_19, SLASH_20, SLASH_22, SLASH_23 }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, UPDATING }
	}
	case class GoogleCloudApigeeV1Instance(
	  /** Customer Managed Encryption Key (CMEK) used for disk and volume encryption. If not specified, a Google-Managed encryption key will be used. Use the following format: `projects/([^/]+)/locations/([^/]+)/keyRings/([^/]+)/cryptoKeys/([^/]+)` */
		diskEncryptionKeyName: Option[String] = None,
	  /** Optional. Size of the CIDR block range that will be reserved by the instance. PAID organizations support `SLASH_16` to `SLASH_20` and defaults to `SLASH_16`. Evaluation organizations support only `SLASH_23`. */
		peeringCidrRange: Option[Schema.GoogleCloudApigeeV1Instance.PeeringCidrRangeEnum] = None,
	  /** Output only. Version of the runtime system running in the instance. The runtime system is the set of components that serve the API Proxy traffic in your Environments. */
		runtimeVersion: Option[String] = None,
	  /** Required. Resource ID of the instance. Values must match the regular expression `^a-z{0,30}[a-z\d]$`. */
		name: Option[String] = None,
	  /** Output only. Resource name of the service attachment created for the instance in the format: `projects/&#42;/regions/&#42;/serviceAttachments/&#42;` Apigee customers can privately forward traffic to this service attachment using the PSC endpoints. */
		serviceAttachment: Option[String] = None,
	  /** Output only. State of the instance. Values other than `ACTIVE` means the resource is not ready to use. */
		state: Option[Schema.GoogleCloudApigeeV1Instance.StateEnum] = None,
	  /** Optional. Customer accept list represents the list of projects (id/number) on customer side that can privately connect to the service attachment. It is an optional field which the customers can provide during the instance creation. By default, the customer project associated with the Apigee organization will be included to the list. */
		consumerAcceptList: Option[List[String]] = None,
	  /** Optional. Access logging configuration enables the access logging feature at the instance. Apigee customers can enable access logging to ship the access logs to their own project's cloud logging. */
		accessLoggingConfig: Option[Schema.GoogleCloudApigeeV1AccessLoggingConfig] = None,
	  /** Output only. Time the instance was last modified in milliseconds since epoch. */
		lastModifiedAt: Option[String] = None,
	  /** Output only. Port number of the exposed Apigee endpoint. */
		port: Option[String] = None,
	  /** Optional. Display name for the instance. */
		displayName: Option[String] = None,
	  /** Output only. Internal hostname or IP address of the Apigee endpoint used by clients to connect to the service. */
		host: Option[String] = None,
	  /** Optional. Comma-separated list of CIDR blocks of length 22 and/or 28 used to create the Apigee instance. Providing CIDR ranges is optional. You can provide just /22 or /28 or both (or neither). Ranges you provide should be freely available as part of a larger named range you have allocated to the Service Networking peering. If this parameter is not provided, Apigee automatically requests an available /22 and /28 CIDR block from Service Networking. Use the /22 CIDR block for configuring your firewall needs to allow traffic from Apigee. Input formats: `a.b.c.d/22` or `e.f.g.h/28` or `a.b.c.d/22,e.f.g.h/28` */
		ipRange: Option[String] = None,
	  /** Required. Compute Engine location where the instance resides. */
		location: Option[String] = None,
	  /** Output only. Time the instance was created in milliseconds since epoch. */
		createdAt: Option[String] = None,
	  /** Optional. Description of the instance. */
		description: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1AppGroupApp(
	  /** Output only. Time the AppGroup app was created in milliseconds since epoch. */
		createdAt: Option[String] = None,
	  /** Immutable. ID of the AppGroup app. */
		appId: Option[String] = None,
	  /** Immutable. Name of the AppGroup app whose resource name format is of syntax (organizations/&#42;/appgroups/&#42;/apps/&#42;). */
		name: Option[String] = None,
	  /** List of attributes for the AppGroup app. */
		attributes: Option[List[Schema.GoogleCloudApigeeV1Attribute]] = None,
	  /** Callback URL used by OAuth 2.0 authorization servers to communicate authorization codes back to AppGroup apps. */
		callbackUrl: Option[String] = None,
	  /** Output only. Time the AppGroup app was modified in milliseconds since epoch. */
		lastModifiedAt: Option[String] = None,
	  /** List of API products associated with the AppGroup app. */
		apiProducts: Option[List[String]] = None,
	  /** Immutable. Name of the parent AppGroup whose resource name format is of syntax (organizations/&#42;/appgroups/&#42;). */
		appGroup: Option[String] = None,
	  /** Status of the App. Valid values include `approved` or `revoked`. */
		status: Option[String] = None,
	  /** Immutable. Expiration time, in seconds, for the consumer key that is generated for the AppGroup app. If not set or left to the default value of `-1`, the API key never expires. The expiration time can't be updated after it is set. */
		keyExpiresIn: Option[String] = None,
	  /** Output only. Set of credentials for the AppGroup app consisting of the consumer key/secret pairs associated with the API products. */
		credentials: Option[List[Schema.GoogleCloudApigeeV1Credential]] = None,
	  /** Scopes to apply to the AppGroup app. The specified scopes must already exist for the API product that you associate with the AppGroup app. */
		scopes: Option[List[String]] = None
	)
	
	case class GoogleCloudApigeeV1ReferenceConfig(
	  /** Name of the reference in the following format: `organizations/{org}/environments/{env}/references/{reference}` */
		name: Option[String] = None,
	  /** Name of the referenced resource in the following format: `organizations/{org}/environments/{env}/keystores/{keystore}` Only references to keystore resources are supported. */
		resourceName: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1SecurityProfileScoringConfig(
	  /** Description of the config. */
		description: Option[String] = None,
	  /** Path of the component config used for scoring. */
		scorePath: Option[String] = None,
	  /** Title of the config. */
		title: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ResourceConfig(
	  /** Location of the resource as a URI. */
		location: Option[String] = None,
	  /** Resource name in the following format: `organizations/{org}/environments/{env}/resourcefiles/{type}/{file}/revisions/{rev}` Only environment-scoped resource files are supported. */
		name: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1QueryMetric(
	  /** One of `+`, `-`, `/`, `%`, `&#42;`. */
		operator: Option[String] = None,
	  /** Aggregation function: avg, min, max, or sum. */
		function: Option[String] = None,
	  /** Alias for the metric. Alias will be used to replace metric name in query results. */
		alias: Option[String] = None,
	  /** Operand value should be provided when operator is set. */
		value: Option[String] = None,
	  /** Required. Metric name. */
		name: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1Session(
	  /** The debug session ID. */
		id: Option[String] = None,
	  /** The first transaction creation timestamp in millisecond, recorded by UAP. */
		timestampMs: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ScoreComponent(
	  /** Time when score was calculated. */
		calculateTime: Option[String] = None,
	  /** Time in the requested time period when data was last captured to compute the score. */
		dataCaptureTime: Option[String] = None,
	  /** Score for the component. */
		score: Option[Int] = None,
	  /** List of paths for next components. */
		drilldownPaths: Option[List[String]] = None,
	  /** List of recommendations to improve API security. */
		recommendations: Option[List[Schema.GoogleCloudApigeeV1ScoreComponentRecommendation]] = None,
	  /** Path of the component. Example: /org@myorg/envgroup@myenvgroup/proxies/proxy@myproxy */
		scorePath: Option[String] = None
	)
	
	object GoogleCloudApigeeV1UpdateError {
		enum CodeEnum extends Enum[CodeEnum] { case OK, CANCELLED, UNKNOWN, INVALID_ARGUMENT, DEADLINE_EXCEEDED, NOT_FOUND, ALREADY_EXISTS, PERMISSION_DENIED, UNAUTHENTICATED, RESOURCE_EXHAUSTED, FAILED_PRECONDITION, ABORTED, OUT_OF_RANGE, UNIMPLEMENTED, INTERNAL, UNAVAILABLE, DATA_LOSS }
	}
	case class GoogleCloudApigeeV1UpdateError(
	  /** The sub resource specific to this error (e.g. a proxy deployed within the EnvironmentConfig). If empty the error refers to the top level resource. */
		resource: Option[String] = None,
	  /** User-friendly error message. */
		message: Option[String] = None,
	  /** A string that uniquely identifies the type of error. This provides a more reliable means to deduplicate errors across revisions and instances. */
		`type`: Option[String] = None,
	  /** Status code. */
		code: Option[Schema.GoogleCloudApigeeV1UpdateError.CodeEnum] = None
	)
	
	case class GoogleCloudApigeeV1AddonsConfig(
	  /** Configuration for the Monetization add-on. */
		monetizationConfig: Option[Schema.GoogleCloudApigeeV1MonetizationConfig] = None,
	  /** Configuration for the Connectors Platform add-on. */
		connectorsPlatformConfig: Option[Schema.GoogleCloudApigeeV1ConnectorsPlatformConfig] = None,
	  /** Configuration for the API Security add-on. */
		apiSecurityConfig: Option[Schema.GoogleCloudApigeeV1ApiSecurityConfig] = None,
	  /** Configuration for the Advanced API Ops add-on. */
		advancedApiOpsConfig: Option[Schema.GoogleCloudApigeeV1AdvancedApiOpsConfig] = None,
	  /** Configuration for the Integration add-on. */
		integrationConfig: Option[Schema.GoogleCloudApigeeV1IntegrationConfig] = None,
	  /** Configuration for the Analytics add-on. Only used in organizations.environments.addonsConfig. */
		analyticsConfig: Option[Schema.GoogleCloudApigeeV1AnalyticsConfig] = None
	)
	
	case class GoogleCloudApigeeV1KeyValueMap(
	  /** Required. Flag that specifies whether entry values will be encrypted. This field is retained for backward compatibility and the value of encrypted will always be `true`. Apigee X and hybrid do not support unencrypted key value maps. */
		encrypted: Option[Boolean] = None,
	  /** Required. ID of the key value map. */
		name: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1TlsInfoConfig(
	  /** Flag that specifies whether to ignore TLS certificate validation errors. Set to `true` to ignore errors. */
		ignoreValidationErrors: Option[Boolean] = None,
	  /** Flag that enforces TLS settings */
		enforce: Option[Boolean] = None,
	  /** Name of the keystore or keystore reference containing trusted certificates for the server in the following format: `organizations/{org}/environments/{env}/keystores/{keystore}` or `organizations/{org}/environments/{env}/references/{reference}` */
		trustStore: Option[String] = None,
	  /** Flag that specifies whether client-side authentication is enabled for the target server. Enables two-way TLS. */
		clientAuthEnabled: Option[Boolean] = None,
	  /** Flag that specifies whether one-way TLS is enabled. Set to `true` to enable one-way TLS. */
		enabled: Option[Boolean] = None,
	  /** Name of the alias used for client-side authentication in the following format: `organizations/{org}/environments/{env}/keystores/{keystore}/aliases/{alias}` */
		keyAlias: Option[String] = None,
	  /** List of ciphers that are granted access. */
		ciphers: Option[List[String]] = None,
	  /** Reference name and alias pair to use for client-side authentication. */
		keyAliasReference: Option[Schema.GoogleCloudApigeeV1KeyAliasReference] = None,
	  /** Common name to validate the target server against. */
		commonName: Option[Schema.GoogleCloudApigeeV1CommonNameConfig] = None,
	  /** List of TLS protocols that are granted access. */
		protocols: Option[List[String]] = None
	)
	
	case class GoogleCloudApigeeV1ListInstanceAttachmentsResponse(
	  /** Attachments for the instance. */
		attachments: Option[List[Schema.GoogleCloudApigeeV1InstanceAttachment]] = None,
	  /** Page token that you can include in a ListInstanceAttachments request to retrieve the next page of content. If omitted, no subsequent pages exist. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1AppGroup(
	  /** A reference to the associated storefront/marketplace. */
		channelUri: Option[String] = None,
	  /** Output only. Internal identifier that cannot be edited */
		appGroupId: Option[String] = None,
	  /** A list of attributes */
		attributes: Option[List[Schema.GoogleCloudApigeeV1Attribute]] = None,
	  /** Output only. Created time as milliseconds since epoch. */
		createdAt: Option[String] = None,
	  /** Valid values are `active` or `inactive`. Note that the status of the AppGroup should be updated via UpdateAppGroupRequest by setting the action as `active` or `inactive`. */
		status: Option[String] = None,
	  /** Immutable. Name of the AppGroup. Characters you can use in the name are restricted to: A-Z0-9._\-$ %. */
		name: Option[String] = None,
	  /** channel identifier identifies the owner maintaing this grouping. */
		channelId: Option[String] = None,
	  /** Immutable. the org the app group is created */
		organization: Option[String] = None,
	  /** Output only. Modified time as milliseconds since epoch. */
		lastModifiedAt: Option[String] = None,
	  /** app group name displayed in the UI */
		displayName: Option[String] = None
	)
	
	object GoogleCloudApigeeV1DataCollector {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, INTEGER, FLOAT, STRING, BOOLEAN, DATETIME }
	}
	case class GoogleCloudApigeeV1DataCollector(
	  /** Immutable. The type of data this data collector will collect. */
		`type`: Option[Schema.GoogleCloudApigeeV1DataCollector.TypeEnum] = None,
	  /** ID of the data collector. Must begin with `dc_`. */
		name: Option[String] = None,
	  /** Output only. The time at which the data collector was created in milliseconds since the epoch. */
		createdAt: Option[String] = None,
	  /** Output only. The time at which the Data Collector was last updated in milliseconds since the epoch. */
		lastModifiedAt: Option[String] = None,
	  /** A description of the data collector. */
		description: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ExportRequest(
	  /** Optional. Delimiter used in the CSV file, if `outputFormat` is set to `csv`. Defaults to the `,` (comma) character. Supported delimiter characters include comma (`,`), pipe (`|`), and tab (`\t`). */
		csvDelimiter: Option[String] = None,
	  /** Required. Display name of the export job. */
		name: Option[String] = None,
	  /** Required. Date range of the data to export. */
		dateRange: Option[Schema.GoogleCloudApigeeV1DateRange] = None,
	  /** Required. Name of the preconfigured datastore. */
		datastoreName: Option[String] = None,
	  /** Optional. Description of the export job. */
		description: Option[String] = None,
	  /** Optional. Output format of the export. Valid values include: `csv` or `json`. Defaults to `json`. Note: Configure the delimiter for CSV output using the `csvDelimiter` property. */
		outputFormat: Option[String] = None
	)
	
	case class GoogleRpcStatus(
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None,
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1TestDatastoreResponse(
	  /** Output only. It could be `completed` or `failed` */
		state: Option[String] = None,
	  /** Output only. Error message of test connection failure */
		error: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1StatsEnvironmentStats(
	  /** In the final response, only one of the following fields will be present based on the dimensions provided. If no dimensions are provided, then only top-level metrics is provided. If dimensions are included, then there will be a top-level dimensions field under environments which will contain metrics values and the dimension name. Example: ``` "environments": [ { "dimensions": [ { "metrics": [ { "name": "sum(message_count)", "values": [ "2.14049521E8" ] } ], "name": "nit_proxy" } ], "name": "prod" } ]``` or ```"environments": [ { "metrics": [ { "name": "sum(message_count)", "values": [ "2.19026331E8" ] } ], "name": "prod" } ]``` List of metric values. */
		metrics: Option[List[Schema.GoogleCloudApigeeV1Metric]] = None,
	  /** List of metrics grouped under dimensions. */
		dimensions: Option[List[Schema.GoogleCloudApigeeV1DimensionMetric]] = None,
	  /** Name of the environment. */
		name: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1UpdateAppGroupAppKeyRequest(
	  /** The new AppGroupKey to be amended. Note that the status can be updated only via action. */
		appGroupAppKey: Option[Schema.GoogleCloudApigeeV1AppGroupAppKey] = None,
	  /** The list of API products that will be associated with the credential. This list will be appended to the existing list of associated API Products for this App Key. Duplicates will be ignored. */
		apiProducts: Option[List[String]] = None,
	  /** Approve or revoke the consumer key by setting this value to `approve` or `revoke` respectively. The `Content-Type` header, if set, must be set to `application/octet-stream`, with empty body. */
		action: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1SecurityProfileEnvironmentAssociation(
	  /** Immutable. Name of the environment that the profile is attached to. */
		name: Option[String] = None,
	  /** Output only. The time when environment was attached to the security profile. */
		attachTime: Option[String] = None,
	  /** DEPRECATED: DO NOT USE Revision ID of the security profile. */
		securityProfileRevisionId: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1TraceConfigOverride(
	  /** ID of the trace configuration override specified as a system-generated UUID. */
		name: Option[String] = None,
	  /** ID of the API proxy that will have its trace configuration overridden. */
		apiProxy: Option[String] = None,
	  /** Trace configuration to override. */
		samplingConfig: Option[Schema.GoogleCloudApigeeV1TraceSamplingConfig] = None
	)
	
	case class GoogleCloudApigeeV1SecurityReportResultMetadata(
	  /** Output only. Expire_time is set to 7 days after report creation. Query result will be unaccessable after this time. Example: "2021-05-04T13:38:52-07:00" */
		expires: Option[String] = None,
	  /** Self link of the query results. Example: `/organizations/myorg/environments/myenv/securityReports/9cfc0d85-0f30-46d6-ae6f-318d0cb961bd/result` or following format if query is running at host level: `/organizations/myorg/hostSecurityReports/9cfc0d85-0f30-46d6-ae6f-318d0cb961bd/result` */
		self: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1SecurityProfileV2(
	  /** Optional. The description of the security profile. */
		description: Option[String] = None,
	  /** Required. The configuration for each assessment in this profile. Key is the name/id of the assessment. */
		profileAssessmentConfigs: Option[Map[String, Schema.GoogleCloudApigeeV1SecurityProfileV2ProfileAssessmentConfig]] = None,
	  /** Identifier. Name of the security profile v2 resource. Format: organizations/{org}/securityProfilesV2/{profile} */
		name: Option[String] = None,
	  /** Output only. The time of the security profile creation. */
		createTime: Option[String] = None,
	  /** Output only. Whether the security profile is google defined. */
		googleDefined: Option[Boolean] = None,
	  /** Output only. The time of the security profile update. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ComputeEnvironmentScoresResponse(
	  /** A page token, received from a previous `ComputeScore` call. Provide this to retrieve the subsequent page. */
		nextPageToken: Option[String] = None,
	  /** List of scores. One score per day. */
		scores: Option[List[Schema.GoogleCloudApigeeV1Score]] = None
	)
	
	case class GoogleCloudApigeeV1SecurityReportQueryMetric(
	  /** Aggregation function: avg, min, max, or sum. */
		aggregationFunction: Option[String] = None,
	  /** Operand value should be provided when operator is set. */
		value: Option[String] = None,
	  /** One of `+`, `-`, `/`, `%`, `&#42;`. */
		operator: Option[String] = None,
	  /** Alias for the metric. Alias will be used to replace metric name in query results. */
		alias: Option[String] = None,
	  /** Required. Metric name. */
		name: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1Subscription(
	  /** Full name of the Pub/Sub subcription. Use the following structure in your request: `subscription "projects/foo/subscription/bar"` */
		name: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ListRatePlansResponse(
	  /** Value that can be sent as `startKey` to retrieve the next page of content. If this field is omitted, there are no subsequent pages. */
		nextStartKey: Option[String] = None,
	  /** List of rate plans in an organization. */
		ratePlans: Option[List[Schema.GoogleCloudApigeeV1RatePlan]] = None
	)
	
	case class GoogleCloudApigeeV1ControlPlaneAccess(
	  /** Optional. Array of service accounts to grant access to control plane resources (for the Synchronizer component). The service accounts must have &#42;&#42;Apigee Synchronizer Manager&#42;&#42; role. See also [Create service accounts](https://cloud.google.com/apigee/docs/hybrid/latest/sa-about#create-the-service-accounts). */
		synchronizerIdentities: Option[List[String]] = None,
	  /** Optional. Array of service accounts authorized to publish analytics data to the control plane (for the Message Processor component). */
		analyticsPublisherIdentities: Option[List[String]] = None,
	  /** Identifier. The resource name of the ControlPlaneAccess. Format: "organizations/{org}/controlPlaneAccess" */
		name: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1BatchComputeSecurityAssessmentResultsRequestResourceArray(
	  /** Required. The array of resources. For Apigee, the proxies are resources. */
		resources: Option[List[Schema.GoogleCloudApigeeV1BatchComputeSecurityAssessmentResultsRequestResourceArrayResource]] = None
	)
	
	case class GoogleCloudApigeeV1ApiDocResponse(
	  /** Unique error code for the request, if any. */
		errorCode: Option[String] = None,
	  /** Status of the operation. */
		status: Option[String] = None,
	  /** Unique ID of the request. */
		requestId: Option[String] = None,
	  /** The catalog item resource. */
		data: Option[Schema.GoogleCloudApigeeV1ApiDoc] = None,
	  /** Description of the operation. */
		message: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ArchiveDeployment(
	  /** Input only. The Google Cloud Storage signed URL returned from GenerateUploadUrl and used to upload the Archive zip file. */
		gcsUri: Option[String] = None,
	  /** Output only. A reference to the LRO that created this Archive Deployment in the following format: `organizations/{org}/operations/{id}` */
		operation: Option[String] = None,
	  /** User-supplied key-value pairs used to organize ArchiveDeployments. Label keys must be between 1 and 63 characters long, have a UTF-8 encoding of maximum 128 bytes, and must conform to the following PCRE regular expression: \p{Ll}\p{Lo}{0,62} Label values must be between 1 and 63 characters long, have a UTF-8 encoding of maximum 128 bytes, and must conform to the following PCRE regular expression: [\p{Ll}\p{Lo}\p{N}_-]{0,63} No more than 64 labels can be associated with a given store. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The time at which the Archive Deployment was created in milliseconds since the epoch. */
		createdAt: Option[String] = None,
	  /** Output only. The time at which the Archive Deployment was updated in milliseconds since the epoch. */
		updatedAt: Option[String] = None,
	  /** Name of the Archive Deployment in the following format: `organizations/{org}/environments/{env}/archiveDeployments/{id}`. */
		name: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ListEndpointAttachmentsResponse(
	  /** Endpoint attachments in the specified organization. */
		endpointAttachments: Option[List[Schema.GoogleCloudApigeeV1EndpointAttachment]] = None,
	  /** Page token that you can include in an `ListEndpointAttachments` request to retrieve the next page. If omitted, no subsequent pages exist. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ListEnvironmentGroupsResponse(
	  /** Page token that you can include in a ListEnvironmentGroups request to retrieve the next page. If omitted, no subsequent pages exist. */
		nextPageToken: Option[String] = None,
	  /** EnvironmentGroups in the specified organization. */
		environmentGroups: Option[List[Schema.GoogleCloudApigeeV1EnvironmentGroup]] = None
	)
	
	case class GoogleCloudApigeeV1DebugSessionTransaction(
	  /** List of debug data collected by runtime plane at various defined points in the flow. */
		point: Option[List[Schema.GoogleCloudApigeeV1Point]] = None,
	  /** Flag indicating whether a transaction is completed or not */
		completed: Option[Boolean] = None
	)
	
	case class GoogleCloudApigeeV1ApiSecurityRuntimeConfig(
	  /** Time that the API Security Runtime configuration was updated. */
		updateTime: Option[String] = None,
	  /** A list of up to 5 Cloud Storage Blobs that contain SecurityActions. */
		location: Option[List[String]] = None,
	  /** Name of the environment API Security Runtime configuration resource. Format: `organizations/{org}/environments/{env}/apiSecurityRuntimeConfig` */
		name: Option[String] = None,
	  /** Unique ID for the API Security Runtime configuration. The ID will only change if the environment is deleted and recreated. */
		uid: Option[String] = None,
	  /** Revision ID of the API Security Runtime configuration. The higher the value, the more recently the configuration was deployed. */
		revisionId: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ReportInstanceStatusRequest(
	  /** A unique ID for the instance which is guaranteed to be unique in case the user installs multiple hybrid runtimes with the same instance ID. */
		instanceUid: Option[String] = None,
	  /** Status for config resources */
		resources: Option[List[Schema.GoogleCloudApigeeV1ResourceStatus]] = None,
	  /** The time the report was generated in the runtime. Used to prevent an old status from overwriting a newer one. An instance should space out it's status reports so that clock skew does not play a factor. */
		reportTime: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ListOfDevelopersResponse(
	  /** List of developers. */
		developer: Option[List[Schema.GoogleCloudApigeeV1Developer]] = None
	)
	
	case class GoogleCloudApigeeV1Score(
	  /** List of all the drilldown score components. */
		subcomponents: Option[List[Schema.GoogleCloudApigeeV1ScoreComponent]] = None,
	  /** Start and end time for the score. */
		timeRange: Option[Schema.GoogleTypeInterval] = None,
	  /** Component containing score, recommendations and actions. */
		component: Option[Schema.GoogleCloudApigeeV1ScoreComponent] = None
	)
	
	case class EdgeConfigstoreBundleBadBundle(
	  /** Describes all precondition violations. */
		violations: Option[List[Schema.EdgeConfigstoreBundleBadBundleViolation]] = None
	)
	
	case class GoogleLongrunningOperation(
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudApigeeV1AdjustDeveloperBalanceRequest(
	  /** &#42; A positive value of `adjustment` means that that the API provider wants to adjust the balance for an under-charged developer i.e. the balance of the developer will decrease. &#42; A negative value of `adjustment` means that that the API provider wants to adjust the balance for an over-charged developer i.e. the balance of the developer will increase. */
		adjustment: Option[Schema.GoogleTypeMoney] = None
	)
	
	object GoogleCloudApigeeV1ApiProduct {
		enum QuotaCounterScopeEnum extends Enum[QuotaCounterScopeEnum] { case QUOTA_COUNTER_SCOPE_UNSPECIFIED, PROXY, OPERATION }
	}
	case class GoogleCloudApigeeV1ApiProduct(
	  /** Comma-separated list of environment names to which the API product is bound. Requests to environments that are not listed are rejected. By specifying one or more environments, you can bind the resources listed in the API product to a specific environment, preventing developers from accessing those resources through API proxies deployed in another environment. This setting is used, for example, to prevent resources associated with API proxies in `prod` from being accessed by API proxies deployed in `test`. */
		environments: Option[List[String]] = None,
	  /** Response only. Creation time of this environment as milliseconds since epoch. */
		createdAt: Option[String] = None,
	  /** Time interval over which the number of request messages is calculated. */
		quotaInterval: Option[String] = None,
	  /** Number of request messages permitted per app by this API product for the specified `quotaInterval` and `quotaTimeUnit`. For example, a `quota` of 50, for a `quotaInterval` of 12 and a `quotaTimeUnit` of hours means 50 requests are allowed every 12 hours. */
		quota: Option[String] = None,
	  /** Description of the API product. Include key information about the API product that is not captured by other fields. */
		description: Option[String] = None,
	  /** Comma-separated list of OAuth scopes that are validated at runtime. Apigee validates that the scopes in any access token presented match the scopes defined in the OAuth policy associated with the API product. */
		scopes: Option[List[String]] = None,
	  /** Configuration used to group Apigee proxies or remote services with resources, method types, and quotas. The resource refers to the resource URI (excluding the base path). With this grouping, the API product creator is able to fine-tune and give precise control over which REST methods have access to specific resources and how many calls can be made (using the `quota` setting). &#42;&#42;Note:&#42;&#42; The `api_resources` setting cannot be specified for both the API product and operation group; otherwise the call will fail. */
		operationGroup: Option[Schema.GoogleCloudApigeeV1OperationGroup] = None,
	  /** Flag that specifies how API keys are approved to access the APIs defined by the API product. If set to `manual`, the consumer key is generated and returned in "pending" state. In this case, the API keys won't work until they have been explicitly approved. If set to `auto`, the consumer key is generated and returned in "approved" state and can be used immediately. &#42;&#42;Note:&#42;&#42; Typically, `auto` is used to provide access to free or trial API products that provide limited quota or capabilities. */
		approvalType: Option[String] = None,
	  /** Scope of the quota decides how the quota counter gets applied and evaluate for quota violation. If the Scope is set as PROXY, then all the operations defined for the APIproduct that are associated with the same proxy will share the same quota counter set at the APIproduct level, making it a global counter at a proxy level. If the Scope is set as OPERATION, then each operations get the counter set at the API product dedicated, making it a local counter. Note that, the QuotaCounterScope applies only when an operation does not have dedicated quota set for itself. */
		quotaCounterScope: Option[Schema.GoogleCloudApigeeV1ApiProduct.QuotaCounterScopeEnum] = None,
	  /** Name displayed in the UI or developer portal to developers registering for API access. */
		displayName: Option[String] = None,
	  /** Comma-separated list of API proxy names to which this API product is bound. By specifying API proxies, you can associate resources in the API product with specific API proxies, preventing developers from accessing those resources through other API proxies. Apigee rejects requests to API proxies that are not listed. &#42;&#42;Note:&#42;&#42; The API proxy names must already exist in the specified environment as they will be validated upon creation. */
		proxies: Option[List[String]] = None,
	  /** Comma-separated list of API resources to be bundled in the API product. By default, the resource paths are mapped from the `proxy.pathsuffix` variable. The proxy path suffix is defined as the URI fragment following the ProxyEndpoint base path. For example, if the `apiResources` element is defined to be `/forecastrss` and the base path defined for the API proxy is `/weather`, then only requests to `/weather/forecastrss` are permitted by the API product. You can select a specific path, or you can select all subpaths with the following wildcard: - `/&#42;&#42;`: Indicates that all sub-URIs are included. - `/&#42;` : Indicates that only URIs one level down are included. By default, / supports the same resources as /&#42;&#42; as well as the base path defined by the API proxy. For example, if the base path of the API proxy is `/v1/weatherapikey`, then the API product supports requests to `/v1/weatherapikey` and to any sub-URIs, such as `/v1/weatherapikey/forecastrss`, `/v1/weatherapikey/region/CA`, and so on. For more information, see Managing API products. */
		apiResources: Option[List[String]] = None,
	  /** Response only. Modified time of this environment as milliseconds since epoch. */
		lastModifiedAt: Option[String] = None,
	  /** Configuration used to group Apigee proxies or remote services with graphQL operation name, graphQL operation type and quotas. This grouping allows us to precisely set quota for a particular combination of graphQL name and operation type for a particular proxy request. If graphQL name is not set, this would imply quota will be applied on all graphQL requests matching the operation type. */
		graphqlOperationGroup: Option[Schema.GoogleCloudApigeeV1GraphQLOperationGroup] = None,
	  /** Array of attributes that may be used to extend the default API product profile with customer-specific metadata. You can specify a maximum of 18 attributes. Use this property to specify the access level of the API product as either `public`, `private`, or `internal`. Only products marked `public` are available to developers in the Apigee developer portal. For example, you can set a product to `internal` while it is in development and then change access to `public` when it is ready to release on the portal. API products marked as `private` do not appear on the portal, but can be accessed by external developers. */
		attributes: Option[List[Schema.GoogleCloudApigeeV1Attribute]] = None,
	  /** Internal name of the API product. Characters you can use in the name are restricted to: `A-Z0-9._\-$ %`. &#42;&#42;Note:&#42;&#42; The internal name cannot be edited when updating the API product. */
		name: Option[String] = None,
	  /** Optional. Configuration used to group Apigee proxies with gRPC services and method names. This grouping allows us to set quota for a particular proxy with the gRPC service name and method. If a method name is not set, this implies quota and authorization are applied to all gRPC methods implemented by that proxy for that particular gRPC service. */
		grpcOperationGroup: Option[Schema.GoogleCloudApigeeV1GrpcOperationGroup] = None,
	  /** Time unit defined for the `quotaInterval`. Valid values include `minute`, `hour`, `day`, or `month`. */
		quotaTimeUnit: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ListSecurityProfileRevisionsResponse(
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** List of security profile revisions. The revisions may be attached or unattached to any environment. */
		securityProfiles: Option[List[Schema.GoogleCloudApigeeV1SecurityProfile]] = None
	)
	
	case class GoogleCloudApigeeV1ListExportsResponse(
	  /** Details of the export jobs. */
		exports: Option[List[Schema.GoogleCloudApigeeV1Export]] = None
	)
	
	case class GoogleCloudApigeeV1ListKeyValueEntriesResponse(
	  /** One or more key value map keys and values. */
		keyValueEntries: Option[List[Schema.GoogleCloudApigeeV1KeyValueEntry]] = None,
	  /** Token that can be sent as `next_page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1Property(
	  /** The property value */
		value: Option[String] = None,
	  /** The property key */
		name: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ProfileConfigCategory(
	  /** Checks to see if you have CORS policy in place. */
		cors: Option[Schema.GoogleCloudApigeeV1ProfileConfigCORS] = None,
	  /** Checks to see if you have a mediation policy in place. */
		mediation: Option[Schema.GoogleCloudApigeeV1ProfileConfigMediation] = None,
	  /** Checks to see if you have configured mTLS for the target server. */
		mtls: Option[Schema.GoogleCloudApigeeV1ProfileConfigMTLS] = None,
	  /** Checks to see if you have a threat protection policy in place. */
		threat: Option[Schema.GoogleCloudApigeeV1ProfileConfigThreat] = None,
	  /** Checks for abuse, which includes any requests sent to the API for purposes other than what it is intended for, such as high volumes of requests, data scraping, and abuse related to authorization. */
		abuse: Option[Schema.GoogleCloudApigeeV1ProfileConfigAbuse] = None,
	  /** Checks to see if you have an authorization policy in place. */
		authorization: Option[Schema.GoogleCloudApigeeV1ProfileConfigAuthorization] = None
	)
	
	case class GoogleCloudApigeeV1ApiCategoryResponse(
	  /** Description of the operation. */
		message: Option[String] = None,
	  /** Status of the operation. */
		status: Option[String] = None,
	  /** The API category resource. */
		data: Option[Schema.GoogleCloudApigeeV1ApiCategory] = None,
	  /** Unique ID of the request. */
		requestId: Option[String] = None,
	  /** Unique error code for the request, if any. */
		errorCode: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1Export(
	  /** Output only. Self link of the export job. A URI that can be used to retrieve the status of an export job. Example: `/organizations/myorg/environments/myenv/analytics/exports/9cfc0d85-0f30-46d6-ae6f-318d0cb961bd` */
		self: Option[String] = None,
	  /** Display name of the export job. */
		name: Option[String] = None,
	  /** Output only. Time the export job was created. */
		created: Option[String] = None,
	  /** Name of the datastore that is the destination of the export job [datastore] */
		datastoreName: Option[String] = None,
	  /** Output only. Execution time for this export job. If the job is still in progress, it will be set to the amount of time that has elapsed since`created`, in seconds. Else, it will set to (`updated` - `created`), in seconds. */
		executionTime: Option[String] = None,
	  /** Output only. Status of the export job. Valid values include `enqueued`, `running`, `completed`, and `failed`. */
		state: Option[String] = None,
	  /** Description of the export job. */
		description: Option[String] = None,
	  /** Output only. Time the export job was last updated. */
		updated: Option[String] = None,
	  /** Output only. Error is set when export fails */
		error: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1Operation(
	  /** methods refers to the REST verbs as in https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html. When none specified, all verb types are allowed. */
		methods: Option[List[String]] = None,
	  /** Required. REST resource path associated with the API proxy or remote service. */
		resource: Option[String] = None
	)
	
	case class GoogleRpcPreconditionFailure(
	  /** Describes all precondition violations. */
		violations: Option[List[Schema.GoogleRpcPreconditionFailureViolation]] = None
	)
	
	case class GoogleCloudApigeeV1DateRange(
	  /** Required. End date (exclusive) of the data to export in the format `yyyy-mm-dd`. The date range ends at 00:00:00 UTC on the end date- which will not be in the output. */
		end: Option[String] = None,
	  /** Required. Start date of the data to export in the format `yyyy-mm-dd`. The date range begins at 00:00:00 UTC on the start date. */
		start: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1GraphQLOperationGroup(
	  /** Flag that specifies whether the configuration is for Apigee API proxy or a remote service. Valid values include `proxy` or `remoteservice`. Defaults to `proxy`. Set to `proxy` when Apigee API proxies are associated with the API product. Set to `remoteservice` when non-Apigee proxies like Istio-Envoy are associated with the API product. */
		operationConfigType: Option[String] = None,
	  /** Required. List of operation configurations for either Apigee API proxies or other remote services that are associated with this API product. */
		operationConfigs: Option[List[Schema.GoogleCloudApigeeV1GraphQLOperationConfig]] = None
	)
	
	case class GoogleCloudApigeeV1GenerateUploadUrlRequest(
	
	)
	
	case class GoogleCloudApigeeV1Point(
	  /** List of results extracted from a given debug point. */
		results: Option[List[Schema.GoogleCloudApigeeV1Result]] = None,
	  /** Name of a step in the transaction. */
		id: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1KeystoreConfig(
	  /** Resource name in the following format: `organizations/{org}/environments/{env}/keystores/{keystore}` */
		name: Option[String] = None,
	  /** Aliases in the keystore. */
		aliases: Option[List[Schema.GoogleCloudApigeeV1AliasRevisionConfig]] = None
	)
	
	case class GoogleCloudApigeeV1SetAddonsRequest(
	  /** Required. Add-on configurations. */
		addonsConfig: Option[Schema.GoogleCloudApigeeV1AddonsConfig] = None
	)
	
	case class GoogleCloudApigeeV1ComputeEnvironmentScoresRequestFilter(
	  /** Optional. Return scores for this component. Example: "/org@myorg/envgroup@myenvgroup/env@myenv/proxies/proxy@myproxy/source" */
		scorePath: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1DeploymentChangeReportRoutingChange(
	  /** Base path/deployment that may start receiving that traffic. May be null if no deployment is able to receive the traffic. */
		toDeployment: Option[Schema.GoogleCloudApigeeV1DeploymentChangeReportRoutingDeployment] = None,
	  /** Human-readable description of this routing change. */
		description: Option[String] = None,
	  /** Base path/deployment that may stop receiving some traffic. */
		fromDeployment: Option[Schema.GoogleCloudApigeeV1DeploymentChangeReportRoutingDeployment] = None,
	  /** Name of the environment group affected by this routing change. */
		environmentGroup: Option[String] = None,
	  /** Set to `true` if using sequenced rollout would make this routing change safer. &#42;&#42;Note&#42;&#42;: This does not necessarily imply that automated sequenced rollout mode is supported for the operation. */
		shouldSequenceRollout: Option[Boolean] = None
	)
	
	case class GoogleCloudApigeeV1AppGroupAppKey(
	  /** Scopes to apply to the app. The specified scope names must already be defined for the API product that you associate with the app. */
		scopes: Option[List[String]] = None,
	  /** Immutable. Consumer key. */
		consumerKey: Option[String] = None,
	  /** Output only. List of API products and its status for which the credential can be used. &#42;&#42;Note&#42;&#42;: Use UpdateAppGroupAppKeyApiProductRequest API to make the association after the consumer key and secret are created. */
		apiProducts: Option[List[Schema.GoogleCloudApigeeV1APIProductAssociation]] = None,
	  /** Immutable. Expiration time, in seconds, for the consumer key. If not set or left to the default value of `-1`, the API key never expires. The expiration time can't be updated after it is set. */
		expiresInSeconds: Option[String] = None,
	  /** Status of the credential. Valid values include `approved` or `revoked`. */
		status: Option[String] = None,
	  /** List of attributes associated with the credential. */
		attributes: Option[List[Schema.GoogleCloudApigeeV1Attribute]] = None,
	  /** Output only. Time the AppGroup app was created in milliseconds since epoch. */
		issuedAt: Option[String] = None,
	  /** Output only. Time the AppGroup app expires in milliseconds since epoch. */
		expiresAt: Option[String] = None,
	  /** Secret key. */
		consumerSecret: Option[String] = None
	)
	
	object GoogleCloudApigeeV1TargetServerConfig {
		enum ProtocolEnum extends Enum[ProtocolEnum] { case PROTOCOL_UNSPECIFIED, HTTP, HTTP2, GRPC_TARGET, GRPC, EXTERNAL_CALLOUT }
	}
	case class GoogleCloudApigeeV1TargetServerConfig(
	  /** Host name of the target server. */
		host: Option[String] = None,
	  /** Whether the target server is enabled. An empty/omitted value for this field should be interpreted as true. */
		enabled: Option[Boolean] = None,
	  /** TLS settings for the target server. */
		tlsInfo: Option[Schema.GoogleCloudApigeeV1TlsInfoConfig] = None,
	  /** The protocol used by this target server. */
		protocol: Option[Schema.GoogleCloudApigeeV1TargetServerConfig.ProtocolEnum] = None,
	  /** Target server revision name in the following format: `organizations/{org}/environments/{env}/targetservers/{targetserver}/revisions/{rev}` */
		name: Option[String] = None,
	  /** Port number for the target server. */
		port: Option[Int] = None
	)
	
	case class GoogleCloudApigeeV1GetAsyncQueryResultUrlResponseURLInfo(
	  /** The MD5 Hash of the JSON data */
		md5: Option[String] = None,
	  /** The signed URL of the JSON data. Will be of the form `https://storage.googleapis.com/example-bucket/cat.jpeg?X-Goog-Algorithm= GOOG4-RSA-SHA256&X-Goog-Credential=example%40example-project.iam.gserviceaccount .com%2F20181026%2Fus-central1%2Fstorage%2Fgoog4_request&X-Goog-Date=20181026T18 1309Z&X-Goog-Expires=900&X-Goog-SignedHeaders=host&X-Goog-Signature=247a2aa45f16 9edf4d187d54e7cc46e4731b1e6273242c4f4c39a1d2507a0e58706e25e3a85a7dbb891d62afa849 6def8e260c1db863d9ace85ff0a184b894b117fe46d1225c82f2aa19efd52cf21d3e2022b3b868dc c1aca2741951ed5bf3bb25a34f5e9316a2841e8ff4c530b22ceaa1c5ce09c7cbb5732631510c2058 0e61723f5594de3aea497f195456a2ff2bdd0d13bad47289d8611b6f9cfeef0c46c91a455b94e90a 66924f722292d21e24d31dcfb38ce0c0f353ffa5a9756fc2a9f2b40bc2113206a81e324fc4fd6823 a29163fa845c8ae7eca1fcf6e5bb48b3200983c56c5ca81fffb151cca7402beddfc4a76b13344703 2ea7abedc098d2eb14a7` */
		uri: Option[String] = None,
	  /** The size of the returned file in bytes */
		sizeBytes: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1IntegrationConfig(
	  /** Flag that specifies whether the Integration add-on is enabled. */
		enabled: Option[Boolean] = None
	)
	
	object GoogleCloudApigeeV1DeploymentGroupConfig {
		enum DeploymentGroupTypeEnum extends Enum[DeploymentGroupTypeEnum] { case DEPLOYMENT_GROUP_TYPE_UNSPECIFIED, STANDARD, EXTENSIBLE }
	}
	case class GoogleCloudApigeeV1DeploymentGroupConfig(
	  /** Type of the deployment group, which will be either Standard or Extensible. */
		deploymentGroupType: Option[Schema.GoogleCloudApigeeV1DeploymentGroupConfig.DeploymentGroupTypeEnum] = None,
	  /** Unique ID. The ID will only change if the deployment group is deleted and recreated. */
		uid: Option[String] = None,
	  /** Revision number which can be used by the runtime to detect if the deployment group has changed between two versions. */
		revisionId: Option[String] = None,
	  /** Name of the deployment group in the following format: `organizations/{org}/environments/{env}/deploymentGroups/{group}`. */
		name: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1SecurityProfile(
	  /** List of profile scoring configs in this revision. */
		scoringConfigs: Option[List[Schema.GoogleCloudApigeeV1SecurityProfileScoringConfig]] = None,
	  /** Output only. Revision ID of the security profile. */
		revisionId: Option[String] = None,
	  /** Output only. Maximum security score that can be generated by this profile. */
		maxScore: Option[Int] = None,
	  /** Output only. DEPRECATED: DO NOT USE The time when revision was published. Once published, the security profile revision cannot be updated further and can be attached to environments. */
		revisionPublishTime: Option[String] = None,
	  /** Output only. The time when revision was updated. */
		revisionUpdateTime: Option[String] = None,
	  /** Output only. The time when revision was created. */
		revisionCreateTime: Option[String] = None,
	  /** Output only. Minimum security score that can be generated by this profile. */
		minScore: Option[Int] = None,
	  /** Required. Customized profile configuration that computes the security score. */
		profileConfig: Option[Schema.GoogleCloudApigeeV1ProfileConfig] = None,
	  /** DEPRECATED: DO NOT USE Display name of the security profile. */
		displayName: Option[String] = None,
	  /** Description of the security profile. */
		description: Option[String] = None,
	  /** Immutable. Name of the security profile resource. Format: organizations/{org}/securityProfiles/{profile} */
		name: Option[String] = None,
	  /** List of environments attached to security profile. */
		environments: Option[List[Schema.GoogleCloudApigeeV1SecurityProfileEnvironment]] = None
	)
	
	case class GoogleCloudApigeeV1ResourceFiles(
	  /** List of resource files. */
		resourceFile: Option[List[Schema.GoogleCloudApigeeV1ResourceFile]] = None
	)
	
	case class GoogleIamV1TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class GoogleCloudApigeeV1ListDataCollectorsResponse(
	  /** Data collectors in the specified organization. */
		dataCollectors: Option[List[Schema.GoogleCloudApigeeV1DataCollector]] = None,
	  /** Page token that you can include in a ListDataCollectors request to retrieve the next page. If omitted, no subsequent pages exist. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1FlowHook(
	  /** Shared flow attached to this flow hook, or empty if there is none attached. */
		sharedFlow: Option[String] = None,
	  /** Optional. Flag that specifies whether execution should continue if the flow hook throws an exception. Set to `true` to continue execution. Set to `false` to stop execution if the flow hook throws an exception. Defaults to `true`. */
		continueOnError: Option[Boolean] = None,
	  /** Description of the flow hook. */
		description: Option[String] = None,
	  /** Output only. Where in the API call flow the flow hook is invoked. Must be one of `PreProxyFlowHook`, `PostProxyFlowHook`, `PreTargetFlowHook`, or `PostTargetFlowHook`. */
		flowHookPoint: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ListArchiveDeploymentsResponse(
	  /** Page token that you can include in a ListArchiveDeployments request to retrieve the next page. If omitted, no subsequent pages exist. */
		nextPageToken: Option[String] = None,
	  /** Archive Deployments in the specified environment. */
		archiveDeployments: Option[List[Schema.GoogleCloudApigeeV1ArchiveDeployment]] = None
	)
	
	case class GoogleCloudApigeeV1ApiSecurityConfig(
	  /** Output only. Time at which the API Security add-on expires in in milliseconds since epoch. If unspecified, the add-on will never expire. */
		expiresAt: Option[String] = None,
	  /** Flag that specifies whether the API security add-on is enabled. */
		enabled: Option[Boolean] = None
	)
	
	case class GoogleCloudApigeeV1DeleteResponse(
	  /** Google Cloud name of deleted resource. */
		gcpResource: Option[String] = None,
	  /** Unique ID of the request. */
		requestId: Option[String] = None,
	  /** Description of the operation. */
		message: Option[String] = None,
	  /** Status of the operation. */
		status: Option[String] = None,
	  /** Unique error code for the request, if any. */
		errorCode: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ReportInstanceStatusResponse(
	
	)
	
	case class GoogleCloudApigeeV1APIProductAssociation(
	  /** The API product credential associated status. Valid values are `approved` or `revoked`. */
		status: Option[String] = None,
	  /** API product to be associated with the credential. */
		apiproduct: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1SecurityReportQuery(
	  /** Report Definition ID. */
		reportDefinitionId: Option[String] = None,
	  /** Valid values include: `csv` or `json`. Defaults to `json`. Note: Configure the delimiter for CSV output using the csvDelimiter property. */
		mimeType: Option[String] = None,
	  /** A list of Metrics. */
		metrics: Option[List[Schema.GoogleCloudApigeeV1SecurityReportQueryMetric]] = None,
	  /** A list of dimensions. https://docs.apigee.com/api-platform/analytics/analytics-reference#dimensions */
		dimensions: Option[List[String]] = None,
	  /** Hostname needs to be specified if query intends to run at host level. This field is only allowed when query is submitted by CreateHostSecurityReport where analytics data will be grouped by organization and hostname. */
		envgroupHostname: Option[String] = None,
	  /** Delimiter used in the CSV file, if `outputFormat` is set to `csv`. Defaults to the `,` (comma) character. Supported delimiter characters include comma (`,`), pipe (`|`), and tab (`\t`). */
		csvDelimiter: Option[String] = None,
	  /** Maximum number of rows that can be returned in the result. */
		limit: Option[Int] = None,
	  /** Required. Time range for the query. Can use the following predefined strings to specify the time range: `last60minutes` `last24hours` `last7days` Or, specify the timeRange as a structure describing start and end timestamps in the ISO format: yyyy-mm-ddThh:mm:ssZ. Example: "timeRange": { "start": "2018-07-29T00:13:00Z", "end": "2018-08-01T00:18:00Z" } */
		timeRange: Option[JsValue] = None,
	  /** Security Report display name which users can specify. */
		displayName: Option[String] = None,
	  /** Time unit used to group the result set. Valid values include: second, minute, hour, day, week, or month. If a query includes groupByTimeUnit, then the result is an aggregation based on the specified time unit and the resultant timestamp does not include milliseconds precision. If a query omits groupByTimeUnit, then the resultant timestamp includes milliseconds precision. */
		groupByTimeUnit: Option[String] = None,
	  /** Boolean expression that can be used to filter data. Filter expressions can be combined using AND/OR terms and should be fully parenthesized to avoid ambiguity. See Analytics metrics, dimensions, and filters reference https://docs.apigee.com/api-platform/analytics/analytics-reference for more information on the fields available to filter on. For more information on the tokens that you use to build filter expressions, see Filter expression syntax. https://docs.apigee.com/api-platform/analytics/asynch-reports-api#filter-expression-syntax */
		filter: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1RoutingRule(
	  /** Conflicting targets, which will be resource names specifying either deployment groups or environments. */
		otherTargets: Option[List[String]] = None,
	  /** The unix timestamp when this rule was updated. This is updated whenever env_group_revision is updated. This field is omitted from the IngressConfig unless the GetDeployedIngressConfig API is called with view=FULL. */
		updateTime: Option[String] = None,
	  /** The resource name of the proxy revision that is receiving this basepath in the following format: `organizations/{org}/apis/{api}/revisions/{rev}`. This field is omitted from the IngressConfig unless the GetDeployedIngressConfig API is called with view=FULL. */
		receiver: Option[String] = None,
	  /** The env group config revision_id when this rule was added or last updated. This value is set when the rule is created and will only update if the the environment_id changes. It is used to determine if the runtime is up to date with respect to this rule. This field is omitted from the IngressConfig unless the GetDeployedIngressConfig API is called with view=FULL. */
		envGroupRevision: Option[String] = None,
	  /** Name of an environment bound to the environment group in the following format: `organizations/{org}/environments/{env}`. Only one of environment or deployment_group will be set. */
		environment: Option[String] = None,
	  /** Name of a deployment group in an environment bound to the environment group in the following format: `organizations/{org}/environment/{env}/deploymentGroups/{group}` Only one of environment or deployment_group will be set. */
		deploymentGroup: Option[String] = None,
	  /** URI path prefix used to route to the specified environment. May contain one or more wildcards. For example, path segments consisting of a single `&#42;` character will match any string. */
		basepath: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1Query(
	  /** Time unit used to group the result set. Valid values include: second, minute, hour, day, week, or month. If a query includes groupByTimeUnit, then the result is an aggregation based on the specified time unit and the resultant timestamp does not include milliseconds precision. If a query omits groupByTimeUnit, then the resultant timestamp includes milliseconds precision. */
		groupByTimeUnit: Option[String] = None,
	  /** A list of Metrics. */
		metrics: Option[List[Schema.GoogleCloudApigeeV1QueryMetric]] = None,
	  /** Delimiter used in the CSV file, if `outputFormat` is set to `csv`. Defaults to the `,` (comma) character. Supported delimiter characters include comma (`,`), pipe (`|`), and tab (`\t`). */
		csvDelimiter: Option[String] = None,
	  /** Required. Time range for the query. Can use the following predefined strings to specify the time range: `last60minutes` `last24hours` `last7days` Or, specify the timeRange as a structure describing start and end timestamps in the ISO format: yyyy-mm-ddThh:mm:ssZ. Example: "timeRange": { "start": "2018-07-29T00:13:00Z", "end": "2018-08-01T00:18:00Z" } */
		timeRange: Option[JsValue] = None,
	  /** Maximum number of rows that can be returned in the result. */
		limit: Option[Int] = None,
	  /** A list of dimensions. https://docs.apigee.com/api-platform/analytics/analytics-reference#dimensions */
		dimensions: Option[List[String]] = None,
	  /** Asynchronous Query Name. */
		name: Option[String] = None,
	  /** Asynchronous Report ID. */
		reportDefinitionId: Option[String] = None,
	  /** Boolean expression that can be used to filter data. Filter expressions can be combined using AND/OR terms and should be fully parenthesized to avoid ambiguity. See Analytics metrics, dimensions, and filters reference https://docs.apigee.com/api-platform/analytics/analytics-reference for more information on the fields available to filter on. For more information on the tokens that you use to build filter expressions, see Filter expression syntax. https://docs.apigee.com/api-platform/analytics/asynch-reports-api#filter-expression-syntax */
		filter: Option[String] = None,
	  /** Hostname needs to be specified if query intends to run at host level. This field is only allowed when query is submitted by CreateHostAsyncQuery where analytics data will be grouped by organization and hostname. */
		envgroupHostname: Option[String] = None,
	  /** Valid values include: `csv` or `json`. Defaults to `json`. Note: Configure the delimiter for CSV output using the csvDelimiter property. */
		outputFormat: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ScoreComponentRecommendationAction(
	  /** Action context for the action. */
		actionContext: Option[Schema.GoogleCloudApigeeV1ScoreComponentRecommendationActionActionContext] = None,
	  /** Description of the action. */
		description: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1TlsInfo(
	  /** The TLS Common Name of the certificate. */
		commonName: Option[Schema.GoogleCloudApigeeV1TlsInfoCommonName] = None,
	  /** Required. Enables TLS. If false, neither one-way nor two-way TLS will be enabled. */
		enabled: Option[Boolean] = None,
	  /** The resource ID of the truststore. */
		trustStore: Option[String] = None,
	  /** The SSL/TLS cipher suites to be used. For programmable proxies, it must be one of the cipher suite names listed in: http://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#ciphersuites. For configurable proxies, it must follow the configuration specified in: https://commondatastorage.googleapis.com/chromium-boringssl-docs/ssl.h.html#Cipher-suite-configuration. This setting has no effect for configurable proxies when negotiating TLS 1.3. */
		ciphers: Option[List[String]] = None,
	  /** If true, Edge ignores TLS certificate errors. Valid when configuring TLS for target servers and target endpoints, and when configuring virtual hosts that use 2-way TLS. When used with a target endpoint/target server, if the backend system uses SNI and returns a cert with a subject Distinguished Name (DN) that does not match the hostname, there is no way to ignore the error and the connection fails. */
		ignoreValidationErrors: Option[Boolean] = None,
	  /** TLS is strictly enforced. */
		enforce: Option[Boolean] = None,
	  /** The TLS versioins to be used. */
		protocols: Option[List[String]] = None,
	  /** Optional. Enables two-way TLS. */
		clientAuthEnabled: Option[Boolean] = None,
	  /** Required if `client_auth_enabled` is true. The resource ID for the alias containing the private key and cert. */
		keyAlias: Option[String] = None,
	  /** Required if `client_auth_enabled` is true. The resource ID of the keystore. */
		keyStore: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ApiProductRef(
	  /** Status of the API product. Valid values are `approved` or `revoked`. */
		status: Option[String] = None,
	  /** Name of the API product. */
		apiproduct: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1SecurityReport(
	  /** Error is set when query fails. */
		error: Option[String] = None,
	  /** Creation time of the query. */
		created: Option[String] = None,
	  /** Result is available only after the query is completed. */
		result: Option[Schema.GoogleCloudApigeeV1SecurityReportResultMetadata] = None,
	  /** ResultRows is available only after the query is completed. */
		resultRows: Option[String] = None,
	  /** Hostname is available only when query is executed at host level. */
		envgroupHostname: Option[String] = None,
	  /** Self link of the query. Example: `/organizations/myorg/environments/myenv/securityReports/9cfc0d85-0f30-46d6-ae6f-318d0cb961bd` or following format if query is running at host level: `/organizations/myorg/hostSecurityReports/9cfc0d85-0f30-46d6-ae6f-318d0cb961bd` */
		self: Option[String] = None,
	  /** Output only. Last updated timestamp for the query. */
		updated: Option[String] = None,
	  /** Contains information like metrics, dimenstions etc of the Security Report. */
		queryParams: Option[Schema.GoogleCloudApigeeV1SecurityReportMetadata] = None,
	  /** Report Definition ID. */
		reportDefinitionId: Option[String] = None,
	  /** ResultFileSize is available only after the query is completed. */
		resultFileSize: Option[String] = None,
	  /** Query state could be "enqueued", "running", "completed", "expired" and "failed". */
		state: Option[String] = None,
	  /** Display Name specified by the user. */
		displayName: Option[String] = None,
	  /** ExecutionTime is available only after the query is completed. */
		executionTime: Option[String] = None
	)
	
	case class GoogleIamV1Policy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Specifies cloud audit logging configuration for this policy. */
		auditConfigs: Option[List[Schema.GoogleIamV1AuditConfig]] = None,
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An `etag` is returned in the response to `getIamPolicy`, and systems are expected to put that etag in the request to `setIamPolicy` to ensure that their change will be applied to the same version of the policy. &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. */
		etag: Option[String] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.GoogleIamV1Binding]] = None
	)
	
	case class GoogleCloudApigeeV1IngressConfig(
	  /** A unique id for the ingress config that will only change if the organization is deleted and recreated. */
		uid: Option[String] = None,
	  /** Name of the resource in the following format: `organizations/{org}/deployedIngressConfig`. */
		name: Option[String] = None,
	  /** List of environment groups in the organization. */
		environmentGroups: Option[List[Schema.GoogleCloudApigeeV1EnvironmentGroupConfig]] = None,
	  /** Time at which the IngressConfig revision was created. */
		revisionCreateTime: Option[String] = None,
	  /** Revision id that defines the ordering on IngressConfig resources. The higher the revision, the more recently the configuration was deployed. */
		revisionId: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1FlowHookConfig(
	  /** Flag that specifies whether the flow should abort after an error in the flow hook. Defaults to `true` (continue on error). */
		continueOnError: Option[Boolean] = None,
	  /** Name of the flow hook in the following format: `organizations/{org}/environments/{env}/flowhooks/{point}`. Valid `point` values include: `PreProxyFlowHook`, `PostProxyFlowHook`, `PreTargetFlowHook`, and `PostTargetFlowHook` */
		name: Option[String] = None,
	  /** Name of the shared flow to invoke in the following format: `organizations/{org}/sharedflows/{sharedflow}` */
		sharedFlowName: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1Properties(
	  /** List of all properties in the object */
		property: Option[List[Schema.GoogleCloudApigeeV1Property]] = None
	)
	
	case class GoogleCloudApigeeV1DeleteCustomReportResponse(
	  /** The response contains only a message field. */
		message: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1Attribute(
	  /** API key of the attribute. */
		name: Option[String] = None,
	  /** Value of the attribute. */
		value: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1GetAsyncQueryResultUrlResponse(
	  /** The list of Signed URLs generated by the CreateAsyncQuery request */
		urls: Option[List[Schema.GoogleCloudApigeeV1GetAsyncQueryResultUrlResponseURLInfo]] = None
	)
	
	case class GoogleCloudApigeeV1SharedFlowRevision(
	  /** Time at which this shared flow revision was created, in milliseconds since epoch. */
		createdAt: Option[String] = None,
	  /** The string "Application" */
		`type`: Option[String] = None,
	  /** Description of the shared flow revision. */
		description: Option[String] = None,
	  /** A Key-Value map of metadata about this shared flow revision. */
		entityMetaDataAsProperties: Option[Map[String, String]] = None,
	  /** The resource ID of this revision. */
		revision: Option[String] = None,
	  /** The version of the configuration schema to which this shared flow conforms. The only supported value currently is majorVersion 4 and minorVersion 0. This setting may be used in the future to enable evolution of the shared flow format. */
		configurationVersion: Option[Schema.GoogleCloudApigeeV1ConfigVersion] = None,
	  /** A list of the resources included in this shared flow revision formatted as "{type}://{name}". */
		resources: Option[List[String]] = None,
	  /** A list of policy names included in this shared flow revision. */
		policies: Option[List[String]] = None,
	  /** A textual description of the shared flow revision. */
		contextInfo: Option[String] = None,
	  /** A list of the shared flow names included in this shared flow revision. */
		sharedFlows: Option[List[String]] = None,
	  /** The resource files included in this shared flow revision. */
		resourceFiles: Option[Schema.GoogleCloudApigeeV1ResourceFiles] = None,
	  /** Time at which this shared flow revision was most recently modified, in milliseconds since epoch. */
		lastModifiedAt: Option[String] = None,
	  /** The resource ID of the parent shared flow. */
		name: Option[String] = None,
	  /** The human readable name of this shared flow. */
		displayName: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ExpireDeveloperSubscriptionRequest(
	
	)
	
	case class GoogleCloudApigeeV1ListOrganizationsResponse(
	  /** List of Apigee organizations and associated Google Cloud projects. */
		organizations: Option[List[Schema.GoogleCloudApigeeV1OrganizationProjectMapping]] = None
	)
	
	case class GoogleCloudApigeeV1SecurityActionFlag(
	  /** Optional. A list of HTTP headers to be sent to the target in case of a FLAG SecurityAction. Limit 5 headers per SecurityAction. At least one is mandatory. */
		headers: Option[List[Schema.GoogleCloudApigeeV1SecurityActionHttpHeader]] = None
	)
	
	case class GoogleCloudApigeeV1ListNatAddressesResponse(
	  /** Page token that you can include in a ListNatAddresses request to retrieve the next page of content. If omitted, no subsequent pages exist. */
		nextPageToken: Option[String] = None,
	  /** List of NAT Addresses for the instance. */
		natAddresses: Option[List[Schema.GoogleCloudApigeeV1NatAddress]] = None
	)
	
	case class GoogleCloudApigeeV1EnableSecurityActionRequest(
	
	)
	
	case class GoogleCloudApigeeV1ResourceStatus(
	  /** Revisions of the resource currently deployed in the instance. */
		revisions: Option[List[Schema.GoogleCloudApigeeV1RevisionStatus]] = None,
	  /** The total number of replicas that should have this resource. */
		totalReplicas: Option[Int] = None,
	  /** The resource name. Currently only two resources are supported: EnvironmentGroup - organizations/{org}/envgroups/{envgroup} EnvironmentConfig - organizations/{org}/environments/{environment}/deployedConfig */
		resource: Option[String] = None,
	  /** The uid of the resource. In the unexpected case that the instance has multiple uids for the same name, they should be reported under separate ResourceStatuses. */
		uid: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1AsyncQueryResult(
	  /** Query result will be unaccessable after this time. */
		expires: Option[String] = None,
	  /** Self link of the query results. Example: `/organizations/myorg/environments/myenv/queries/9cfc0d85-0f30-46d6-ae6f-318d0cb961bd/result` or following format if query is running at host level: `/organizations/myorg/hostQueries/9cfc0d85-0f30-46d6-ae6f-318d0cb961bd/result` */
		self: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1Metric(
	  /** List of metric values. Possible value formats include: `"values":["39.0"]` or `"values":[ { "value": "39.0", "timestamp": 1232434354} ]` */
		values: Option[List[JsValue]] = None,
	  /** Metric name. */
		name: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1QueryMetadata(
	  /** Output format. */
		outputFormat: Option[String] = None,
	  /** Start timestamp of the query range. */
		startTimestamp: Option[String] = None,
	  /** Metrics of the AsyncQuery. Example: ["name:message_count,func:sum,alias:sum_message_count"] */
		metrics: Option[List[String]] = None,
	  /** Dimensions of the AsyncQuery. */
		dimensions: Option[List[String]] = None,
	  /** Query GroupBy time unit. */
		timeUnit: Option[String] = None,
	  /** End timestamp of the query range. */
		endTimestamp: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1AccessRemove(
		success: Option[Boolean] = None,
		name: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1GrpcOperationGroup(
	  /** Required. List of operation configurations for either Apigee API proxies that are associated with this API product. */
		operationConfigs: Option[List[Schema.GoogleCloudApigeeV1GrpcOperationConfig]] = None
	)
	
	case class GoogleCloudApigeeV1OperationConfig(
	  /** Required. Name of the API proxy or remote service with which the resources, methods, and quota are associated. */
		apiSource: Option[String] = None,
	  /** Custom attributes associated with the operation. */
		attributes: Option[List[Schema.GoogleCloudApigeeV1Attribute]] = None,
	  /** List of resource/method pairs for the API proxy or remote service to which quota will applied. &#42;&#42;Note&#42;&#42;: Currently, you can specify only a single resource/method pair. The call will fail if more than one resource/method pair is provided. */
		operations: Option[List[Schema.GoogleCloudApigeeV1Operation]] = None,
	  /** Quota parameters to be enforced for the resources, methods, and API source combination. If none are specified, quota enforcement will not be done. */
		quota: Option[Schema.GoogleCloudApigeeV1Quota] = None
	)
	
	case class GoogleCloudApigeeV1EntityMetadata(
	  /** Time at which the API proxy was most recently modified, in milliseconds since epoch. */
		lastModifiedAt: Option[String] = None,
	  /** The type of entity described */
		subType: Option[String] = None,
	  /** Time at which the API proxy was created, in milliseconds since epoch. */
		createdAt: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1GetSyncAuthorizationRequest(
	
	)
	
	case class GoogleCloudApigeeV1ListAppGroupAppsResponse(
	  /** Token that can be sent as `next_page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** List of AppGroup apps and their credentials. */
		appGroupApps: Option[List[Schema.GoogleCloudApigeeV1AppGroupApp]] = None
	)
	
	case class GoogleCloudApigeeV1CustomReportMetric(
	  /** name of the metric */
		name: Option[String] = None,
	  /** aggregate function */
		function: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1BatchUpdateSecurityIncidentsRequest(
	  /** Optional. Required. The request message specifying the resources to update. A maximum of 1000 can be modified in a batch. */
		requests: Option[List[Schema.GoogleCloudApigeeV1UpdateSecurityIncidentRequest]] = None
	)
	
	case class GoogleCloudApigeeV1ListHybridIssuersResponse(
	  /** Lists of hybrid services and its trusted issuer email ids. */
		issuers: Option[List[Schema.GoogleCloudApigeeV1ServiceIssuersMapping]] = None
	)
	
	case class GoogleCloudApigeeV1ApiDocDocumentation(
	  /** Optional. GraphQL documentation. */
		graphqlDocumentation: Option[Schema.GoogleCloudApigeeV1GraphqlDocumentation] = None,
	  /** Optional. OpenAPI Specification documentation. */
		oasDocumentation: Option[Schema.GoogleCloudApigeeV1OASDocumentation] = None
	)
	
	case class GoogleCloudApigeeV1SharedFlow(
	  /** Metadata describing the shared flow. */
		metaData: Option[Schema.GoogleCloudApigeeV1EntityMetadata] = None,
	  /** A list of revisions of this shared flow. */
		revision: Option[List[String]] = None,
	  /** The ID of the shared flow. */
		name: Option[String] = None,
	  /** The id of the most recently created revision for this shared flow. */
		latestRevisionId: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ProfileConfig(
	  /** List of categories of profile config. */
		categories: Option[List[Schema.GoogleCloudApigeeV1ProfileConfigCategory]] = None
	)
	
	object GoogleCloudApigeeV1MetricAggregation {
		enum AggregationEnum extends Enum[AggregationEnum] { case AGGREGATION_FUNCTION_UNSPECIFIED, AVG, SUM, MIN, MAX, COUNT_DISTINCT }
		enum OrderEnum extends Enum[OrderEnum] { case ORDER_UNSPECIFIED, ASCENDING, DESCENDING }
	}
	case class GoogleCloudApigeeV1MetricAggregation(
	  /** Name of the metric */
		name: Option[String] = None,
	  /** Aggregation function associated with the metric. */
		aggregation: Option[Schema.GoogleCloudApigeeV1MetricAggregation.AggregationEnum] = None,
	  /** Ordering for this aggregation in the result. For time series this is ignored since the ordering of points depends only on the timestamp, not the values. */
		order: Option[Schema.GoogleCloudApigeeV1MetricAggregation.OrderEnum] = None
	)
	
	case class GoogleCloudApigeeV1DocumentationFile(
	  /** Required. The file contents. The max size is 4 MB. */
		contents: Option[String] = None,
	  /** Required. A display name for the file, shown in the management UI. Max length is 255 characters. */
		displayName: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1DeveloperBalanceWallet(
	  /** Output only. Time at which the developer last added credit to the account in milliseconds since epoch. */
		lastCreditTime: Option[String] = None,
	  /** Current remaining balance of the developer for a particular currency. */
		balance: Option[Schema.GoogleTypeMoney] = None
	)
	
	case class GoogleCloudApigeeV1InstanceDeploymentStatusDeployedRevision(
	  /** Percentage of MP replicas reporting this revision. */
		percentage: Option[Int] = None,
	  /** API proxy revision reported as deployed. */
		revision: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1CanaryEvaluationMetricLabels(
	  /** Required. The location associated with the metrics. */
		location: Option[String] = None,
	  /** The environment ID associated with the metrics. */
		env: Option[String] = None,
	  /** Required. The instance ID associated with the metrics. In Apigee Hybrid, the value is configured during installation. */
		instance_id: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1CertInfo(
	  /** X.509 serial number. */
		serialNumber: Option[String] = None,
	  /** X.509 subject. */
		subject: Option[String] = None,
	  /** Public key component of the X.509 subject public key info. */
		publicKey: Option[String] = None,
	  /** X.509 basic constraints extension. */
		basicConstraints: Option[String] = None,
	  /** X.509 version. */
		version: Option[Int] = None,
	  /** X.509 subject alternative names (SANs) extension. */
		subjectAlternativeNames: Option[List[String]] = None,
	  /** X.509 `notAfter` validity period in milliseconds since epoch. */
		expiryDate: Option[String] = None,
	  /** X.509 `notBefore` validity period in milliseconds since epoch. */
		validFrom: Option[String] = None,
	  /** X.509 issuer. */
		issuer: Option[String] = None,
	  /** Flag that specifies whether the certificate is valid. Flag is set to `Yes` if the certificate is valid, `No` if expired, or `Not yet` if not yet valid. */
		isValid: Option[String] = None,
	  /** X.509 signatureAlgorithm. */
		sigAlgName: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1StatsHostStats(
	  /** In the final response, only one of the following fields will be present based on the dimensions provided. If no dimensions are provided, then only the top-level metrics are provided. If dimensions are included, then there will be a top-level dimensions field under hostnames which will contain metrics values and the dimension name. Example: ``` "hosts": [ { "dimensions": [ { "metrics": [ { "name": "sum(message_count)", "values": [ "2.14049521E8" ] } ], "name": "nit_proxy" } ], "name": "example.com" } ]``` OR ```"hosts": [ { "metrics": [ { "name": "sum(message_count)", "values": [ "2.19026331E8" ] } ], "name": "example.com" } ]``` List of metric values. */
		metrics: Option[List[Schema.GoogleCloudApigeeV1Metric]] = None,
	  /** Hostname used in query. */
		name: Option[String] = None,
	  /** List of metrics grouped under dimensions. */
		dimensions: Option[List[Schema.GoogleCloudApigeeV1DimensionMetric]] = None
	)
	
	case class GoogleCloudApigeeV1MonetizationConfig(
	  /** Flag that specifies whether the Monetization add-on is enabled. */
		enabled: Option[Boolean] = None
	)
	
	object GoogleCloudApigeeV1OASDocumentation {
		enum FormatEnum extends Enum[FormatEnum] { case FORMAT_UNSPECIFIED, YAML, JSON }
	}
	case class GoogleCloudApigeeV1OASDocumentation(
	  /** Output only. The format of the input specification file contents. */
		format: Option[Schema.GoogleCloudApigeeV1OASDocumentation.FormatEnum] = None,
	  /** Required. The documentation file contents for the OpenAPI Specification. JSON and YAML file formats are supported. */
		spec: Option[Schema.GoogleCloudApigeeV1DocumentationFile] = None
	)
	
	case class GoogleCloudApigeeV1PodStatus(
	  /** Version of the application running in the pod. */
		appVersion: Option[String] = None,
	  /** Code associated with the deployment status. */
		statusCode: Option[String] = None,
	  /** Overall status of the pod (not this specific deployment). Valid values include: - `active`: Up to date. - `stale` : Recently out of date. Pods that have not reported status in a long time are excluded from the output. */
		podStatus: Option[String] = None,
	  /** Human-readable message associated with the status code. */
		statusCodeDetails: Option[String] = None,
	  /** Time the pod status was reported in milliseconds since epoch. */
		podStatusTime: Option[String] = None,
	  /** Time the proxy was deployed in milliseconds since epoch. */
		deploymentTime: Option[String] = None,
	  /** Status of the deployment. Valid values include: - `deployed`: Successful. - `error` : Failed. - `pending` : Pod has not yet reported on the deployment. */
		deploymentStatus: Option[String] = None,
	  /** Name of the pod which is reporting the status. */
		podName: Option[String] = None,
	  /** Time the deployment status was reported in milliseconds since epoch. */
		deploymentStatusTime: Option[String] = None
	)
	
	case class GoogleIamV1Binding(
	  /** Specifies the principals requesting access for a Google Cloud resource. `members` can have the following values: &#42; `allUsers`: A special identifier that represents anyone who is on the internet; with or without a Google account. &#42; `allAuthenticatedUsers`: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. &#42; `user:{emailid}`: An email address that represents a specific Google account. For example, `alice@example.com` . &#42; `serviceAccount:{emailid}`: An email address that represents a Google service account. For example, `my-other-app@appspot.gserviceaccount.com`. &#42; `serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]`: An identifier for a [Kubernetes service account](https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, `my-project.svc.id.goog[my-namespace/my-kubernetes-sa]`. &#42; `group:{emailid}`: An email address that represents a Google group. For example, `admins@example.com`. &#42; `domain:{domain}`: The G Suite domain (primary) that represents all the users of that domain. For example, `google.com` or `example.com`. &#42; `principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workforce identity pool. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}`: All workforce identities in a group. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All workforce identities with a specific attribute value. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;`: All identities in a workforce identity pool. &#42; `principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workload identity pool. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}`: A workload identity pool group. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All identities in a workload identity pool with a certain attribute. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;`: All identities in a workload identity pool. &#42; `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a user that has been recently deleted. For example, `alice@example.com?uid=123456789012345678901`. If the user is recovered, this value reverts to `user:{emailid}` and the recovered user retains the role in the binding. &#42; `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, `my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901`. If the service account is undeleted, this value reverts to `serviceAccount:{emailid}` and the undeleted service account retains the role in the binding. &#42; `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, `admins@example.com?uid=123456789012345678901`. If the group is recovered, this value reverts to `group:{emailid}` and the recovered group retains the role in the binding. &#42; `deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: Deleted single identity in a workforce identity pool. For example, `deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value`. */
		members: Option[List[String]] = None,
	  /** Role that is assigned to the list of `members`, or principals. For example, `roles/viewer`, `roles/editor`, or `roles/owner`. For an overview of the IAM roles and permissions, see the [IAM documentation](https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see [here](https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** The condition that is associated with this binding. If the condition evaluates to `true`, then this binding applies to the current request. If the condition evaluates to `false`, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		condition: Option[Schema.GoogleTypeExpr] = None
	)
	
	case class GoogleCloudApigeeV1ProfileConfigMediation(
	
	)
	
	case class GoogleIamV1AuditConfig(
	  /** The configuration for logging of each type of permission. */
		auditLogConfigs: Option[List[Schema.GoogleIamV1AuditLogConfig]] = None,
	  /** Specifies a service that will be enabled for audit logging. For example, `storage.googleapis.com`, `cloudsql.googleapis.com`. `allServices` is a special value that covers all services. */
		service: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ListCustomReportsResponse(
		qualifier: Option[List[Schema.GoogleCloudApigeeV1CustomReport]] = None
	)
	
	object GoogleCloudApigeeV1SecurityAssessmentResultResource {
		enum TypeEnum extends Enum[TypeEnum] { case RESOURCE_TYPE_UNSPECIFIED, API_PROXY }
	}
	case class GoogleCloudApigeeV1SecurityAssessmentResultResource(
	  /** The revision id for the resource. In case of Apigee, this is proxy revision id. */
		resourceRevisionId: Option[String] = None,
	  /** Required. Name of this resource. */
		name: Option[String] = None,
	  /** Required. Type of this resource. */
		`type`: Option[Schema.GoogleCloudApigeeV1SecurityAssessmentResultResource.TypeEnum] = None
	)
	
	case class GoogleCloudApigeeV1CommonNameConfig(
		matchWildCards: Option[Boolean] = None,
		name: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ListSecurityReportsResponse(
	  /** The security reports belong to requested resource name. */
		securityReports: Option[List[Schema.GoogleCloudApigeeV1SecurityReport]] = None,
	  /** If the number of security reports exceeded the page size requested, the token can be used to fetch the next page in a subsequent call. If the response is the last page and there are no more reports to return this field is left empty. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudApigeeV1SecurityAssessmentResultScoringResultAssessmentRecommendation {
		enum VerdictEnum extends Enum[VerdictEnum] { case VERDICT_UNSPECIFIED, PASS, FAIL }
		enum WeightEnum extends Enum[WeightEnum] { case WEIGHT_UNSPECIFIED, MINOR, MODERATE, MAJOR }
	}
	case class GoogleCloudApigeeV1SecurityAssessmentResultScoringResultAssessmentRecommendation(
	  /** The recommended steps of the assessment. */
		recommendations: Option[List[Schema.GoogleCloudApigeeV1SecurityAssessmentResultScoringResultAssessmentRecommendationRecommendation]] = None,
	  /** The display name of the assessment. */
		displayName: Option[String] = None,
	  /** Verdict indicates the assessment result. */
		verdict: Option[Schema.GoogleCloudApigeeV1SecurityAssessmentResultScoringResultAssessmentRecommendation.VerdictEnum] = None,
	  /** Score impact indicates the impact on the overall score if the assessment were to pass. */
		scoreImpact: Option[Int] = None,
	  /** The weight of the assessment which was set in the profile. */
		weight: Option[Schema.GoogleCloudApigeeV1SecurityAssessmentResultScoringResultAssessmentRecommendation.WeightEnum] = None
	)
	
	object GoogleCloudApigeeV1RuntimeTraceConfig {
		enum ExporterEnum extends Enum[ExporterEnum] { case EXPORTER_UNSPECIFIED, JAEGER, CLOUD_TRACE }
	}
	case class GoogleCloudApigeeV1RuntimeTraceConfig(
	  /** The timestamp that the revision was created or updated. */
		revisionCreateTime: Option[String] = None,
	  /** List of trace configuration overrides for spicific API proxies. */
		overrides: Option[List[Schema.GoogleCloudApigeeV1RuntimeTraceConfigOverride]] = None,
	  /** Endpoint of the exporter. */
		endpoint: Option[String] = None,
	  /** Exporter that is used to view the distributed trace captured using OpenCensus. An exporter sends traces to any backend that is capable of consuming them. Recorded spans can be exported by registered exporters. */
		exporter: Option[Schema.GoogleCloudApigeeV1RuntimeTraceConfig.ExporterEnum] = None,
	  /** Trace configuration for all API proxies in an environment. */
		samplingConfig: Option[Schema.GoogleCloudApigeeV1RuntimeTraceSamplingConfig] = None,
	  /** Name of the trace config in the following format: `organizations/{org}/environment/{env}/traceConfig` */
		name: Option[String] = None,
	  /** Revision number which can be used by the runtime to detect if the trace config has changed between two versions. */
		revisionId: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ConfigVersion(
	  /** Minor version of the API proxy configuration schema. */
		minorVersion: Option[Int] = None,
	  /** Major version of the API proxy configuration schema. */
		majorVersion: Option[Int] = None
	)
	
	case class GoogleCloudApigeeV1ServiceIssuersMapping(
	  /** List of trusted issuer email ids. */
		emailIds: Option[List[String]] = None,
	  /** String indicating the Apigee service name. */
		service: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1EnvironmentGroupAttachment(
	  /** Output only. The time at which the environment group attachment was created as milliseconds since epoch. */
		createdAt: Option[String] = None,
	  /** ID of the environment group attachment. */
		name: Option[String] = None,
	  /** Required. ID of the attached environment. */
		environment: Option[String] = None,
	  /** Output only. ID of the environment group. */
		environmentGroupId: Option[String] = None
	)
	
	object GoogleCloudApigeeV1CanaryEvaluation {
		enum VerdictEnum extends Enum[VerdictEnum] { case VERDICT_UNSPECIFIED, NONE, FAIL, PASS }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RUNNING, SUCCEEDED }
	}
	case class GoogleCloudApigeeV1CanaryEvaluation(
	  /** Required. The newer version that is serving requests. */
		treatment: Option[String] = None,
	  /** Required. Labels used to filter the metrics used for a canary evaluation. */
		metricLabels: Option[Schema.GoogleCloudApigeeV1CanaryEvaluationMetricLabels] = None,
	  /** Output only. The resulting verdict of the canary evaluations: NONE, PASS, or FAIL. */
		verdict: Option[Schema.GoogleCloudApigeeV1CanaryEvaluation.VerdictEnum] = None,
	  /** Output only. Name of the canary evalution. */
		name: Option[String] = None,
	  /** Output only. The current state of the canary evaluation. */
		state: Option[Schema.GoogleCloudApigeeV1CanaryEvaluation.StateEnum] = None,
	  /** Required. End time for the evaluation's analysis. */
		endTime: Option[String] = None,
	  /** Output only. Create time of the canary evaluation. */
		createTime: Option[String] = None,
	  /** Required. Start time for the canary evaluation's analysis. */
		startTime: Option[String] = None,
	  /** Required. The stable version that is serving requests. */
		control: Option[String] = None
	)
	
	case class GoogleLongrunningListOperationsResponse(
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None,
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.GoogleLongrunningOperation]] = None
	)
	
	object GoogleCloudApigeeV1BatchComputeSecurityAssessmentResultsRequestResourceArrayResource {
		enum TypeEnum extends Enum[TypeEnum] { case RESOURCE_TYPE_UNSPECIFIED, API_PROXY }
	}
	case class GoogleCloudApigeeV1BatchComputeSecurityAssessmentResultsRequestResourceArrayResource(
	  /** Required. Type of this resource. */
		`type`: Option[Schema.GoogleCloudApigeeV1BatchComputeSecurityAssessmentResultsRequestResourceArrayResource.TypeEnum] = None,
	  /** Required. Name of this resource. */
		name: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ApiDocDocumentationResponse(
	  /** Output only. Unique ID of the request. */
		requestId: Option[String] = None,
	  /** Output only. The documentation resource. */
		data: Option[Schema.GoogleCloudApigeeV1ApiDocDocumentation] = None,
	  /** Output only. Status of the operation. */
		status: Option[String] = None,
	  /** Output only. Description of the operation. */
		message: Option[String] = None,
	  /** Output only. Unique error code for the request, if any. */
		errorCode: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1SecuritySettings(
	  /** Identifier. Full resource name is always `organizations/{org}/securitySettings`. */
		name: Option[String] = None,
	  /** Optional. If true the user consents to the use of ML models for Abuse detection. */
		mlRetrainingFeedbackEnabled: Option[Boolean] = None
	)
	
	case class GoogleCloudApigeeV1Certificate(
	  /** Chain of certificates under this name. */
		certInfo: Option[List[Schema.GoogleCloudApigeeV1CertInfo]] = None
	)
	
	case class GoogleCloudApigeeV1OptimizedStatsNode(
		data: Option[List[JsValue]] = None
	)
	
	case class GoogleCloudApigeeV1EndpointChainingRule(
	  /** The deployment group to target for cross-shard chaining calls to these proxies. */
		deploymentGroup: Option[String] = None,
	  /** List of proxy ids which may be found in the given deployment group. */
		proxyIds: Option[List[String]] = None
	)
	
	case class GoogleIamV1SetIamPolicyRequest(
	  /** OPTIONAL: A FieldMask specifying which fields of the policy to modify. Only the fields in the mask will be modified. If no mask is provided, the following default mask is used: `paths: "bindings, etag"` */
		updateMask: Option[String] = None,
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.GoogleIamV1Policy] = None
	)
	
	case class GoogleCloudApigeeV1DeploymentChangeReportRoutingConflict(
	  /** Human-readable description of this conflict. */
		description: Option[String] = None,
	  /** Existing base path/deployment causing the conflict. */
		conflictingDeployment: Option[Schema.GoogleCloudApigeeV1DeploymentChangeReportRoutingDeployment] = None,
	  /** Name of the environment group in which this conflict exists. */
		environmentGroup: Option[String] = None
	)
	
	object GoogleCloudApigeeV1SecurityProfileV2ProfileAssessmentConfig {
		enum WeightEnum extends Enum[WeightEnum] { case WEIGHT_UNSPECIFIED, MINOR, MODERATE, MAJOR }
	}
	case class GoogleCloudApigeeV1SecurityProfileV2ProfileAssessmentConfig(
	  /** The weight of the assessment. */
		weight: Option[Schema.GoogleCloudApigeeV1SecurityProfileV2ProfileAssessmentConfig.WeightEnum] = None
	)
	
	case class GoogleCloudApigeeV1ListTraceConfigOverridesResponse(
	  /** Token value that can be passed as `page_token` to retrieve the next page of content. */
		nextPageToken: Option[String] = None,
	  /** List all trace configuration overrides in an environment. */
		traceConfigOverrides: Option[List[Schema.GoogleCloudApigeeV1TraceConfigOverride]] = None
	)
	
	case class GoogleCloudApigeeV1DimensionMetric(
	  /** Individual dimension names. E.g. ["dim1_name", "dim2_name"]. */
		individualNames: Option[List[String]] = None,
	  /** List of metrics. */
		metrics: Option[List[Schema.GoogleCloudApigeeV1Metric]] = None,
	  /** Comma joined dimension names. E.g. "dim1_name,dim2_name". Deprecated. If name already has comma before join, we may get wrong splits. Please use individual_names. */
		name: Option[String] = None
	)
	
	object GoogleCloudApigeeV1TargetServer {
		enum ProtocolEnum extends Enum[ProtocolEnum] { case PROTOCOL_UNSPECIFIED, HTTP, HTTP2, GRPC_TARGET, GRPC, EXTERNAL_CALLOUT }
	}
	case class GoogleCloudApigeeV1TargetServer(
	  /** Required. The host name this target connects to. Value must be a valid hostname as described by RFC-1123. */
		host: Option[String] = None,
	  /** Required. The resource id of this target server. Values must match the regular expression  */
		name: Option[String] = None,
	  /** Optional. Enabling/disabling a TargetServer is useful when TargetServers are used in load balancing configurations, and one or more TargetServers need to taken out of rotation periodically. Defaults to true. */
		isEnabled: Option[Boolean] = None,
	  /** Required. The port number this target connects to on the given host. Value must be between 1 and 65535, inclusive. */
		port: Option[Int] = None,
	  /** Immutable. The protocol used by this TargetServer. */
		protocol: Option[Schema.GoogleCloudApigeeV1TargetServer.ProtocolEnum] = None,
	  /** Optional. Specifies TLS configuration info for this TargetServer. The JSON name is `sSLInfo` for legacy/backwards compatibility reasons -- Edge originally supported SSL, and the name is still used for TLS configuration. */
		sSLInfo: Option[Schema.GoogleCloudApigeeV1TlsInfo] = None,
	  /** Optional. A human-readable description of this TargetServer. */
		description: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1DeploymentConfig(
	  /** Additional key-value metadata for the deployment. */
		attributes: Option[Map[String, String]] = None,
	  /** Unique ID of the API proxy revision. */
		proxyUid: Option[String] = None,
	  /** Name of the API or shared flow revision to be deployed in the following format: `organizations/{org}/apis/{api}/revisions/{rev}` or `organizations/{org}/sharedflows/{sharedflow}/revisions/{rev}` */
		name: Option[String] = None,
	  /** The list of deployment groups in which this proxy should be deployed. Not currently populated for shared flows. */
		deploymentGroups: Option[List[String]] = None,
	  /** A mapping from basepaths to proxy endpoint names in this proxy. Not populated for shared flows. */
		endpoints: Option[Map[String, String]] = None,
	  /** Base path where the application will be hosted. Defaults to "/". */
		basePath: Option[String] = None,
	  /** The service account identity associated with this deployment. If non-empty, will be in the following format: `projects/-/serviceAccounts/{account_email}` */
		serviceAccount: Option[String] = None,
	  /** Location of the API proxy bundle as a URI. */
		location: Option[String] = None,
	  /** Unique ID. The ID will only change if the deployment is deleted and recreated. */
		uid: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1DebugSession(
	  /** Optional. The maximum number of bytes captured from the response payload. Min = 0, Max = 5120, Default = 5120. */
		tracesize: Option[Int] = None,
	  /** Optional. The length of time, in seconds, that this debug session is valid, starting from when it's received in the control plane. Min = 1, Max = 15, Default = 10. */
		validity: Option[Int] = None,
	  /** Optional. The time in seconds after which this DebugSession should end. This value will override the value in query param, if both are provided. */
		timeout: Option[String] = None,
	  /** Output only. The first transaction creation timestamp, recorded by UAP. */
		createTime: Option[String] = None,
	  /** Optional. A conditional statement which is evaluated against the request message to determine if it should be traced. Syntax matches that of on API Proxy bundle flow Condition. */
		filter: Option[String] = None,
	  /** Optional. The number of request to be traced. Min = 1, Max = 15, Default = 10. */
		count: Option[Int] = None,
	  /** A unique ID for this DebugSession. */
		name: Option[String] = None
	)
	
	object GoogleCloudApigeeV1RatePlan {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, DRAFT, PUBLISHED }
		enum ConsumptionPricingTypeEnum extends Enum[ConsumptionPricingTypeEnum] { case CONSUMPTION_PRICING_TYPE_UNSPECIFIED, FIXED_PER_UNIT, BANDED, TIERED, STAIRSTEP }
		enum BillingPeriodEnum extends Enum[BillingPeriodEnum] { case BILLING_PERIOD_UNSPECIFIED, WEEKLY, MONTHLY }
		enum RevenueShareTypeEnum extends Enum[RevenueShareTypeEnum] { case REVENUE_SHARE_TYPE_UNSPECIFIED, FIXED, VOLUME_BANDED }
		enum PaymentFundingModelEnum extends Enum[PaymentFundingModelEnum] { case PAYMENT_FUNDING_MODEL_UNSPECIFIED, PREPAID, POSTPAID }
	}
	case class GoogleCloudApigeeV1RatePlan(
	  /** Time when the rate plan will expire in milliseconds since epoch. Set to 0 or `null` to indicate that the rate plan should never expire. */
		endTime: Option[String] = None,
	  /** Display name of the rate plan. */
		displayName: Option[String] = None,
	  /** Details of the revenue sharing model. */
		revenueShareRates: Option[List[Schema.GoogleCloudApigeeV1RevenueShareRange]] = None,
	  /** Current state of the rate plan (draft or published). */
		state: Option[Schema.GoogleCloudApigeeV1RatePlan.StateEnum] = None,
	  /** API call volume ranges and the fees charged when the total number of API calls is within a given range. The method used to calculate the final fee depends on the selected pricing model. For example, if the pricing model is `STAIRSTEP` and the ranges are defined as follows: ``` { "start": 1, "end": 100, "fee": 75 }, { "start": 101, "end": 200, "fee": 100 }, } ``` Then the following fees would be charged based on the total number of API calls (assuming the currency selected is `USD`): &#42; 1 call costs $75 &#42; 50 calls cost $75 &#42; 150 calls cost $100 The number of API calls cannot exceed 200. */
		consumptionPricingRates: Option[List[Schema.GoogleCloudApigeeV1RateRange]] = None,
	  /** Pricing model used for consumption-based charges. */
		consumptionPricingType: Option[Schema.GoogleCloudApigeeV1RatePlan.ConsumptionPricingTypeEnum] = None,
	  /** Frequency at which the customer will be billed. */
		billingPeriod: Option[Schema.GoogleCloudApigeeV1RatePlan.BillingPeriodEnum] = None,
	  /** Output only. Time that the rate plan was created in milliseconds since epoch. */
		createdAt: Option[String] = None,
	  /** Method used to calculate the revenue that is shared with developers. */
		revenueShareType: Option[Schema.GoogleCloudApigeeV1RatePlan.RevenueShareTypeEnum] = None,
	  /** Output only. Time the rate plan was last modified in milliseconds since epoch. */
		lastModifiedAt: Option[String] = None,
	  /** DEPRECATED: This field is no longer supported and will eventually be removed when Apigee Hybrid 1.5/1.6 is no longer supported. Instead, use the `billingType` field inside `DeveloperMonetizationConfig` resource. Flag that specifies the billing account type, prepaid or postpaid. */
		paymentFundingModel: Option[Schema.GoogleCloudApigeeV1RatePlan.PaymentFundingModelEnum] = None,
	  /** Fixed amount that is charged at a defined interval and billed in advance of use of the API product. The fee will be prorated for the first billing period. */
		fixedRecurringFee: Option[Schema.GoogleTypeMoney] = None,
	  /** Frequency at which the fixed fee is charged. */
		fixedFeeFrequency: Option[Int] = None,
	  /** Currency to be used for billing. Consists of a three-letter code as defined by the [ISO 4217](https://en.wikipedia.org/wiki/ISO_4217) standard. */
		currencyCode: Option[String] = None,
	  /** Name of the API product that the rate plan is associated with. */
		apiproduct: Option[String] = None,
	  /** Description of the rate plan. */
		description: Option[String] = None,
	  /** Output only. Name of the rate plan. */
		name: Option[String] = None,
	  /** Time when the rate plan becomes active in milliseconds since epoch. */
		startTime: Option[String] = None,
	  /** Initial, one-time fee paid when purchasing the API product. */
		setupFee: Option[Schema.GoogleTypeMoney] = None
	)
	
	case class GoogleCloudApigeeV1ListAppGroupsResponse(
	  /** Token that can be sent as `next_page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** List of AppGroups. */
		appGroups: Option[List[Schema.GoogleCloudApigeeV1AppGroup]] = None,
	  /** Total count of AppGroups. */
		totalSize: Option[Int] = None
	)
	
	object GoogleCloudApigeeV1OperationMetadataProgress {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, NOT_STARTED, IN_PROGRESS, FINISHED }
	}
	case class GoogleCloudApigeeV1OperationMetadataProgress(
	  /** State of the operation. */
		state: Option[Schema.GoogleCloudApigeeV1OperationMetadataProgress.StateEnum] = None,
	  /** The percentage of the operation progress. */
		percentDone: Option[Int] = None,
	  /** Description of the operation's progress. */
		description: Option[String] = None,
	  /** The additional details of the progress. */
		details: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleCloudApigeeV1QueryTimeSeriesStatsResponse(
	  /** Column names corresponding to the same order as the inner values in the stats field. */
		columns: Option[List[String]] = None,
	  /** Next page token. */
		nextPageToken: Option[String] = None,
	  /** Results of the query returned as a JSON array. */
		values: Option[List[Schema.GoogleCloudApigeeV1QueryTimeSeriesStatsResponseSequence]] = None
	)
	
	object GoogleCloudApigeeV1SecurityIncident {
		enum ObservabilityEnum extends Enum[ObservabilityEnum] { case OBSERVABILITY_UNSPECIFIED, ACTIVE, ARCHIVED }
		enum RiskLevelEnum extends Enum[RiskLevelEnum] { case RISK_LEVEL_UNSPECIFIED, LOW, MODERATE, SEVERE }
	}
	case class GoogleCloudApigeeV1SecurityIncident(
	  /** Optional. Indicates if the user archived this incident. */
		observability: Option[Schema.GoogleCloudApigeeV1SecurityIncident.ObservabilityEnum] = None,
	  /** Total traffic detected as part of the incident. */
		trafficCount: Option[String] = None,
	  /** Output only. Detection types which are part of the incident. Examples: Flooder, OAuth Abuser, Static Content Scraper, Anomaly Detection. */
		detectionTypes: Option[List[String]] = None,
	  /** Output only. The time when events associated with the incident were last detected. */
		lastDetectedTime: Option[String] = None,
	  /** Output only. The time when the incident observability was last changed. */
		lastObservabilityChangeTime: Option[String] = None,
	  /** Output only. Risk level of the incident. */
		riskLevel: Option[Schema.GoogleCloudApigeeV1SecurityIncident.RiskLevelEnum] = None,
	  /** Output only. The time when events associated with the incident were first detected. */
		firstDetectedTime: Option[String] = None,
	  /** Optional. Display name of the security incident. */
		displayName: Option[String] = None,
	  /** Immutable. Name of the security incident resource. Format: organizations/{org}/environments/{environment}/securityIncidents/{incident} Example: organizations/apigee-org/environments/dev/securityIncidents/1234-5678-9101-1111 */
		name: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1RuntimeAddonsConfig(
	  /** Name of the addons config in the format: `organizations/{org}/environments/{env}/addonsConfig` */
		name: Option[String] = None,
	  /** Runtime configuration for Analytics add-on. */
		analyticsConfig: Option[Schema.GoogleCloudApigeeV1RuntimeAnalyticsConfig] = None,
	  /** UID is to detect if config is recreated after deletion. The add-on config will only be deleted when the environment itself gets deleted, thus it will always be the same as the UID of EnvironmentConfig. */
		uid: Option[String] = None,
	  /** Revision number used by the runtime to detect config changes. */
		revisionId: Option[String] = None,
	  /** Runtime configuration for API Security add-on. */
		apiSecurityConfig: Option[Schema.GoogleCloudApigeeV1RuntimeApiSecurityConfig] = None
	)
	
	case class GoogleRpcPreconditionFailureViolation(
	  /** The type of PreconditionFailure. We recommend using a service-specific enum type to define the supported precondition violation subjects. For example, "TOS" for "Terms of Service violation". */
		`type`: Option[String] = None,
	  /** The subject, relative to the type, that failed. For example, "google.com/cloud" relative to the "TOS" type would indicate which terms of service is being referenced. */
		subject: Option[String] = None,
	  /** A description of how the precondition failed. Developers can use this description to understand how to fix the failure. For example: "Terms of service not accepted". */
		description: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1GenerateDownloadUrlResponse(
	  /** The Google Cloud Storage signed URL that can be used to download the Archive zip file. */
		downloadUri: Option[String] = None
	)
	
	object GoogleCloudApigeeV1RuntimeTraceSamplingConfig {
		enum SamplerEnum extends Enum[SamplerEnum] { case SAMPLER_UNSPECIFIED, OFF, PROBABILITY }
	}
	case class GoogleCloudApigeeV1RuntimeTraceSamplingConfig(
	  /** Field sampling rate. This value is only applicable when using the PROBABILITY sampler. The supported values are > 0 and <= 0.5. */
		samplingRate: Option[BigDecimal] = None,
	  /** Sampler of distributed tracing. OFF is the default value. */
		sampler: Option[Schema.GoogleCloudApigeeV1RuntimeTraceSamplingConfig.SamplerEnum] = None
	)
	
	case class GoogleCloudApigeeV1ListDebugSessionsResponse(
	  /** Page token that you can include in a ListDebugSessionsRequest to retrieve the next page. If omitted, no subsequent pages exist. */
		nextPageToken: Option[String] = None,
	  /** Session info that includes debug session ID and the first transaction creation timestamp. */
		sessions: Option[List[Schema.GoogleCloudApigeeV1Session]] = None
	)
	
	case class GoogleCloudApigeeV1RevisionStatus(
	  /** The json content of the resource revision. Large specs should be sent individually via the spec field to avoid hitting request size limits. */
		jsonSpec: Option[String] = None,
	  /** Errors reported when attempting to load this revision. */
		errors: Option[List[Schema.GoogleCloudApigeeV1UpdateError]] = None,
	  /** The revision of the resource. */
		revisionId: Option[String] = None,
	  /** The number of replicas that have successfully loaded this revision. */
		replicas: Option[Int] = None
	)
	
	case class GoogleCloudApigeeV1AccessSet(
		value: Option[String] = None,
		name: Option[String] = None,
		success: Option[Boolean] = None
	)
	
	case class GoogleCloudApigeeV1BatchComputeSecurityAssessmentResultsRequestIncludeAll(
	
	)
	
	case class GoogleTypeMoney(
	  /** Number of nano (10^-9) units of the amount. The value must be between -999,999,999 and +999,999,999 inclusive. If `units` is positive, `nanos` must be positive or zero. If `units` is zero, `nanos` can be positive, zero, or negative. If `units` is negative, `nanos` must be negative or zero. For example $-1.75 is represented as `units`=-1 and `nanos`=-750,000,000. */
		nanos: Option[Int] = None,
	  /** The three-letter currency code defined in ISO 4217. */
		currencyCode: Option[String] = None,
	  /** The whole units of the amount. For example if `currencyCode` is `"USD"`, then 1 unit is one US dollar. */
		units: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1AsyncQuery(
	  /** Contains information like metrics, dimenstions etc of the AsyncQuery. */
		queryParams: Option[Schema.GoogleCloudApigeeV1QueryMetadata] = None,
	  /** Self link of the query. Example: `/organizations/myorg/environments/myenv/queries/9cfc0d85-0f30-46d6-ae6f-318d0cb961bd` or following format if query is running at host level: `/organizations/myorg/hostQueries/9cfc0d85-0f30-46d6-ae6f-318d0cb961bd` */
		self: Option[String] = None,
	  /** Asynchronous Query Name. */
		name: Option[String] = None,
	  /** Query state could be "enqueued", "running", "completed", "failed". */
		state: Option[String] = None,
	  /** Last updated timestamp for the query. */
		updated: Option[String] = None,
	  /** ResultFileSize is available only after the query is completed. */
		resultFileSize: Option[String] = None,
	  /** ExecutionTime is available only after the query is completed. */
		executionTime: Option[String] = None,
	  /** Creation time of the query. */
		created: Option[String] = None,
	  /** Hostname is available only when query is executed at host level. */
		envgroupHostname: Option[String] = None,
	  /** Asynchronous Report ID. */
		reportDefinitionId: Option[String] = None,
	  /** ResultRows is available only after the query is completed. */
		resultRows: Option[String] = None,
	  /** Error is set when query fails. */
		error: Option[String] = None,
	  /** Result is available only after the query is completed. */
		result: Option[Schema.GoogleCloudApigeeV1AsyncQueryResult] = None
	)
	
	case class GoogleCloudApigeeV1DeploymentChangeReport(
	  /** All base path conflicts detected for a deployment request. */
		routingConflicts: Option[List[Schema.GoogleCloudApigeeV1DeploymentChangeReportRoutingConflict]] = None,
	  /** Validation errors that would cause the deployment change request to be rejected. */
		validationErrors: Option[Schema.GoogleRpcPreconditionFailure] = None,
	  /** All routing changes that may result from a deployment request. */
		routingChanges: Option[List[Schema.GoogleCloudApigeeV1DeploymentChangeReportRoutingChange]] = None
	)
	
	case class GoogleCloudApigeeV1GrpcOperationConfig(
	  /** Quota parameters to be enforced for the methods and API source combination. If none are specified, quota enforcement will not be done. */
		quota: Option[Schema.GoogleCloudApigeeV1Quota] = None,
	  /** Custom attributes associated with the operation. */
		attributes: Option[List[Schema.GoogleCloudApigeeV1Attribute]] = None,
	  /** Required. gRPC Service name associated to be associated with the API proxy, on which quota rules can be applied upon. */
		service: Option[String] = None,
	  /** List of unqualified gRPC method names for the proxy to which quota will be applied. If this field is empty, the Quota will apply to all operations on the gRPC service defined on the proxy. Example: Given a proxy that is configured to serve com.petstore.PetService, the methods com.petstore.PetService.ListPets and com.petstore.PetService.GetPet would be specified here as simply ["ListPets", "GetPet"]. */
		methods: Option[List[String]] = None,
	  /** Required. Name of the API proxy with which the gRPC operation and quota are associated. */
		apiSource: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ListApiCategoriesResponse(
	  /** Description of the operation. */
		message: Option[String] = None,
	  /** Unique ID of the request. */
		requestId: Option[String] = None,
	  /** Status of the operation. */
		status: Option[String] = None,
	  /** Unique error code for the request, if any. */
		errorCode: Option[String] = None,
	  /** The API category resources. */
		data: Option[List[Schema.GoogleCloudApigeeV1ApiCategory]] = None
	)
	
	case class GoogleCloudApigeeV1ReportProperty(
	  /** name of the property */
		property: Option[String] = None,
	  /** property values */
		value: Option[List[Schema.GoogleCloudApigeeV1Attribute]] = None
	)
	
	case class GoogleCloudApigeeV1Attributes(
	  /** List of attributes. */
		attribute: Option[List[Schema.GoogleCloudApigeeV1Attribute]] = None
	)
	
	case class GoogleCloudApigeeV1SecurityReportResultView(
	  /** State of retrieving ResultView. */
		state: Option[String] = None,
	  /** Error message when there is a failure. */
		error: Option[String] = None,
	  /** Error code when there is a failure. */
		code: Option[Int] = None,
	  /** Rows of security report result. Each row is a JSON object. Example: {sum(message_count): 1, developer_app: "(not set)",…} */
		rows: Option[List[JsValue]] = None,
	  /** Metadata contains information like metrics, dimenstions etc of the security report. */
		metadata: Option[Schema.GoogleCloudApigeeV1SecurityReportMetadata] = None
	)
	
	case class GoogleCloudApigeeV1ListSecurityIncidentsResponse(
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** List of security incidents in the organization */
		securityIncidents: Option[List[Schema.GoogleCloudApigeeV1SecurityIncident]] = None
	)
	
	case class GoogleCloudApigeeV1Credential(
	  /** List of scopes to apply to the app. Specified scopes must already exist on the API product that you associate with the app. */
		scopes: Option[List[String]] = None,
	  /** List of attributes associated with this credential. */
		attributes: Option[List[Schema.GoogleCloudApigeeV1Attribute]] = None,
	  /** Secret key. */
		consumerSecret: Option[String] = None,
	  /** Time the credential will expire in milliseconds since epoch. */
		expiresAt: Option[String] = None,
	  /** Consumer key. */
		consumerKey: Option[String] = None,
	  /** List of API products this credential can be used for. */
		apiProducts: Option[List[Schema.GoogleCloudApigeeV1ApiProductRef]] = None,
	  /** Status of the credential. Valid values include `approved` or `revoked`. */
		status: Option[String] = None,
	  /** Time the credential was issued in milliseconds since epoch. */
		issuedAt: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1KeyValueEntry(
	  /** Resource URI that can be used to identify the scope of the key value map entries. */
		name: Option[String] = None,
	  /** Required. Data or payload that is being retrieved and associated with the unique key. */
		value: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1Stats(
	  /** Metadata information. */
		metaData: Option[Schema.GoogleCloudApigeeV1Metadata] = None,
	  /** List of query results grouped by host. */
		hosts: Option[List[Schema.GoogleCloudApigeeV1StatsHostStats]] = None,
	  /** List of query results on the environment level. */
		environments: Option[List[Schema.GoogleCloudApigeeV1StatsEnvironmentStats]] = None
	)
	
	case class GoogleCloudApigeeV1RuntimeAnalyticsConfig(
	  /** If the Analytics is enabled or not. */
		enabled: Option[Boolean] = None,
	  /** If Runtime should send billing data to AX or not. */
		billingPipelineEnabled: Option[Boolean] = None
	)
	
	case class GoogleCloudApigeeV1ApiCategory(
	  /** Name of the category. */
		name: Option[String] = None,
	  /** Time the category was last modified in milliseconds since epoch. */
		updateTime: Option[String] = None,
	  /** ID of the category (a UUID). */
		id: Option[String] = None,
	  /** Name of the portal. */
		siteId: Option[String] = None
	)
	
	case class EdgeConfigstoreBundleBadBundleViolation(
	  /** A description of why the bundle is invalid and how to fix it. */
		description: Option[String] = None,
	  /** The filename (including relative path from the bundle root) in which the error occurred. */
		filename: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1App(
	  /** Scopes to apply to the app. The specified scope names must already exist on the API product that you associate with the app. */
		scopes: Option[List[String]] = None,
	  /** Status of the credential. */
		status: Option[String] = None,
	  /** Name of the company that owns the app. */
		companyName: Option[String] = None,
	  /** Email of the developer. */
		developerEmail: Option[String] = None,
	  /** List of attributes. */
		attributes: Option[List[Schema.GoogleCloudApigeeV1Attribute]] = None,
	  /** ID of the app. */
		appId: Option[String] = None,
	  /** Callback URL used by OAuth 2.0 authorization servers to communicate authorization codes back to apps. */
		callbackUrl: Option[String] = None,
	  /** Duration, in milliseconds, of the consumer key that will be generated for the app. The default value, -1, indicates an infinite validity period. Once set, the expiration can't be updated. json key: keyExpiresIn */
		keyExpiresIn: Option[String] = None,
	  /** Output only. Last modified time as milliseconds since epoch. */
		lastModifiedAt: Option[String] = None,
	  /** Output only. Unix time when the app was created. */
		createdAt: Option[String] = None,
	  /** Name of the AppGroup */
		appGroup: Option[String] = None,
	  /** ID of the developer. */
		developerId: Option[String] = None,
	  /** List of API products associated with the app. */
		apiProducts: Option[List[Schema.GoogleCloudApigeeV1ApiProductRef]] = None,
	  /** Output only. Set of credentials for the app. Credentials are API key/secret pairs associated with API products. */
		credentials: Option[List[Schema.GoogleCloudApigeeV1Credential]] = None,
	  /** Name of the app. */
		name: Option[String] = None
	)
	
	object GoogleCloudApigeeV1NatAddress {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, RESERVED, ACTIVE, DELETING }
	}
	case class GoogleCloudApigeeV1NatAddress(
	  /** Required. Resource ID of the NAT address. */
		name: Option[String] = None,
	  /** Output only. State of the nat address. */
		state: Option[Schema.GoogleCloudApigeeV1NatAddress.StateEnum] = None,
	  /** Output only. The static IPV4 address. */
		ipAddress: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ComputeEnvironmentScoresRequest(
	  /** Optional. Filters are used to filter scored components. Return all the components if no filter is mentioned. Example: [{ "scorePath": "/org@myorg/envgroup@myenvgroup/env@myenv/proxies/proxy@myproxy/source" }, { "scorePath": "/org@myorg/envgroup@myenvgroup/env@myenv/proxies/proxy@myproxy/target", }] This will return components with path: "/org@myorg/envgroup@myenvgroup/env@myenv/proxies/proxy@myproxy/source" OR "/org@myorg/envgroup@myenvgroup/env@myenv/proxies/proxy@myproxy/target" */
		filters: Option[List[Schema.GoogleCloudApigeeV1ComputeEnvironmentScoresRequestFilter]] = None,
	  /** Optional. A token that can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		pageToken: Option[String] = None,
	  /** Optional. The maximum number of subcomponents to be returned in a single page. The service may return fewer than this value. If unspecified, at most 100 subcomponents will be returned in a single page. */
		pageSize: Option[Int] = None,
	  /** Required. Time range for score calculation. At most 14 days of scores will be returned, and both the start and end dates must be within the last 90 days. */
		timeRange: Option[Schema.GoogleTypeInterval] = None
	)
	
	case class GoogleCloudApigeeV1ScoreComponentRecommendationActionActionContext(
	  /** Documentation link for the action. */
		documentationLink: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1RuntimeApiSecurityConfig(
	  /** If the API Security is enabled or not. */
		enabled: Option[Boolean] = None
	)
	
	case class GoogleCloudApigeeV1ListSecurityActionsResponse(
	  /** The SecurityActions for the specified environment. */
		securityActions: Option[List[Schema.GoogleCloudApigeeV1SecurityAction]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1TlsInfoCommonName(
	  /** Indicates whether the cert should be matched against as a wildcard cert. */
		wildcardMatch: Option[Boolean] = None,
	  /** The TLS Common Name string of the certificate. */
		value: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1DebugMask(
	  /** List of JSON paths that specify the JSON elements to be filtered from JSON response message payloads. */
		responseJSONPaths: Option[List[String]] = None,
	  /** List of XPaths that specify the XML elements to be filtered from XML payloads in error flows. */
		faultXPaths: Option[List[String]] = None,
	  /** List of variables that should be masked from the debug output. */
		variables: Option[List[String]] = None,
	  /** List of JSON paths that specify the JSON elements to be filtered from JSON payloads in error flows. */
		faultJSONPaths: Option[List[String]] = None,
	  /** List of XPaths that specify the XML elements to be filtered from XML request message payloads. */
		requestXPaths: Option[List[String]] = None,
	  /** Map of namespaces to URIs. */
		namespaces: Option[Map[String, String]] = None,
	  /** List of XPaths that specify the XML elements to be filtered from XML response message payloads. */
		responseXPaths: Option[List[String]] = None,
	  /** Name of the debug mask. */
		name: Option[String] = None,
	  /** List of JSON paths that specify the JSON elements to be filtered from JSON request message payloads. */
		requestJSONPaths: Option[List[String]] = None
	)
	
	case class GoogleCloudApigeeV1DisableSecurityActionRequest(
	
	)
	
	case class GoogleCloudApigeeV1ConnectorsPlatformConfig(
	  /** Flag that specifies whether the Connectors Platform add-on is enabled. */
		enabled: Option[Boolean] = None,
	  /** Output only. Time at which the Connectors Platform add-on expires in milliseconds since epoch. If unspecified, the add-on will never expire. */
		expiresAt: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1Access(
		Get: Option[Schema.GoogleCloudApigeeV1AccessGet] = None,
		`Set`: Option[Schema.GoogleCloudApigeeV1AccessSet] = None,
		Remove: Option[Schema.GoogleCloudApigeeV1AccessRemove] = None
	)
	
	case class GoogleCloudApigeeV1RevenueShareRange(
	  /** Percentage of the revenue to be shared with the developer. For example, to share 21 percent of the total revenue with the developer, set this value to 21. Specify a decimal number with a maximum of two digits following the decimal point. */
		sharePercentage: Option[BigDecimal] = None,
	  /** Ending value of the range. Set to 0 or `null` for the last range of values. */
		end: Option[String] = None,
	  /** Starting value of the range. Set to 0 or `null` for the initial range of values. */
		start: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1Developer(
	  /** Access type. */
		accessType: Option[String] = None,
	  /** List of apps associated with the developer. */
		apps: Option[List[String]] = None,
	  /** Developer app family. */
		appFamily: Option[String] = None,
	  /** List of companies associated with the developer. */
		companies: Option[List[String]] = None,
	  /** Required. Last name of the developer. */
		lastName: Option[String] = None,
	  /** Output only. Time at which the developer was last modified in milliseconds since epoch. */
		lastModifiedAt: Option[String] = None,
	  /** Output only. Status of the developer. Valid values are `active` and `inactive`. */
		status: Option[String] = None,
	  /** Required. Email address of the developer. This value is used to uniquely identify the developer in Apigee hybrid. Note that the email address has to be in lowercase only. */
		email: Option[String] = None,
	  /** ID of the developer. &#42;&#42;Note&#42;&#42;: IDs are generated internally by Apigee and are not guaranteed to stay the same over time. */
		developerId: Option[String] = None,
	  /** Output only. Time at which the developer was created in milliseconds since epoch. */
		createdAt: Option[String] = None,
	  /** Output only. Name of the Apigee organization in which the developer resides. */
		organizationName: Option[String] = None,
	  /** Required. User name of the developer. Not used by Apigee hybrid. */
		userName: Option[String] = None,
	  /** Optional. Developer attributes (name/value pairs). The custom attribute limit is 18. */
		attributes: Option[List[Schema.GoogleCloudApigeeV1Attribute]] = None,
	  /** Required. First name of the developer. */
		firstName: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ListSecurityProfilesV2Response(
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** List of security profiles in the organization. */
		securityProfilesV2: Option[List[Schema.GoogleCloudApigeeV1SecurityProfileV2]] = None
	)
	
	case class GoogleCloudApigeeV1EnvironmentConfig(
	  /** List of resource versions in the environment. */
		resources: Option[List[Schema.GoogleCloudApigeeV1ResourceConfig]] = None,
	  /** List of resource references in the environment. */
		resourceReferences: Option[List[Schema.GoogleCloudApigeeV1ReferenceConfig]] = None,
	  /** List of deployments in the environment. */
		deployments: Option[List[Schema.GoogleCloudApigeeV1DeploymentConfig]] = None,
	  /** DEPRECATED: Use revision_id. */
		sequenceNumber: Option[String] = None,
	  /** Name of the environment configuration in the following format: `organizations/{org}/environments/{env}/configs/{config}` */
		name: Option[String] = None,
	  /** Used by the Control plane to add context information to help detect the source of the document during diagnostics and debugging. */
		provider: Option[String] = None,
	  /** Feature flags inherited from the organization and environment. */
		featureFlags: Option[Map[String, String]] = None,
	  /** List of data collectors used by the deployments in the environment. */
		dataCollectors: Option[List[Schema.GoogleCloudApigeeV1DataCollectorConfig]] = None,
	  /** Revision ID for environment-scoped resources (e.g. target servers, keystores) in this config. This ID will increment any time a resource not scoped to a deployment group changes. */
		envScopedRevisionId: Option[String] = None,
	  /** Unique ID for the environment configuration. The ID will only change if the environment is deleted and recreated. */
		uid: Option[String] = None,
	  /** List of target servers in the environment. Disabled target servers are not displayed. */
		targets: Option[List[Schema.GoogleCloudApigeeV1TargetServerConfig]] = None,
	  /** Debug mask that applies to all deployments in the environment. */
		debugMask: Option[Schema.GoogleCloudApigeeV1DebugMask] = None,
	  /** List of keystores in the environment. */
		keystores: Option[List[Schema.GoogleCloudApigeeV1KeystoreConfig]] = None,
	  /** Revision ID of the environment configuration. The higher the value, the more recently the configuration was deployed. */
		revisionId: Option[String] = None,
	  /** Name of the PubSub topic for the environment. */
		pubsubTopic: Option[String] = None,
	  /** List of deployment groups in the environment. */
		deploymentGroups: Option[List[Schema.GoogleCloudApigeeV1DeploymentGroupConfig]] = None,
	  /** List of flow hooks in the environment. */
		flowhooks: Option[List[Schema.GoogleCloudApigeeV1FlowHookConfig]] = None,
	  /** Time that the environment configuration was created. */
		createTime: Option[String] = None,
	  /** The forward proxy's url to be used by the runtime. When set, runtime will send requests to the target via the given forward proxy. This is only used by programmable gateways. */
		forwardProxyUri: Option[String] = None,
	  /** The location for the config blob of API Runtime Control, aka Envoy Adapter, for op-based authentication as a URI, e.g. a Cloud Storage URI. This is only used by Envoy-based gateways. */
		arcConfigLocation: Option[String] = None,
	  /** Trace configurations. Contains config for the environment and config overrides for specific API proxies. */
		traceConfig: Option[Schema.GoogleCloudApigeeV1RuntimeTraceConfig] = None,
	  /** The latest runtime configurations for add-ons. */
		addonsConfig: Option[Schema.GoogleCloudApigeeV1RuntimeAddonsConfig] = None,
	  /** The location for the gateway config blob as a URI, e.g. a Cloud Storage URI. This is only used by Envoy-based gateways. */
		gatewayConfigLocation: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1GraphQLOperationConfig(
	  /** Required. List of GraphQL name/operation type pairs for the proxy or remote service to which quota will be applied. If only operation types are specified, the quota will be applied to all GraphQL requests irrespective of the GraphQL name. &#42;&#42;Note&#42;&#42;: Currently, you can specify only a single GraphQLOperation. Specifying more than one will cause the operation to fail. */
		operations: Option[List[Schema.GoogleCloudApigeeV1GraphQLOperation]] = None,
	  /** Required. Name of the API proxy endpoint or remote service with which the GraphQL operation and quota are associated. */
		apiSource: Option[String] = None,
	  /** Custom attributes associated with the operation. */
		attributes: Option[List[Schema.GoogleCloudApigeeV1Attribute]] = None,
	  /** Quota parameters to be enforced for the resources, methods, and API source combination. If none are specified, quota enforcement will not be done. */
		quota: Option[Schema.GoogleCloudApigeeV1Quota] = None
	)
	
	case class GoogleCloudApigeeV1RateRange(
	  /** Fee to charge when total number of API calls falls within this range. */
		fee: Option[Schema.GoogleTypeMoney] = None,
	  /** Ending value of the range. Set to 0 or `null` for the last range of values. */
		end: Option[String] = None,
	  /** Starting value of the range. Set to 0 or `null` for the initial range of values. */
		start: Option[String] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleIamV1TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	object GoogleCloudApigeeV1Deployment {
		enum ProxyDeploymentTypeEnum extends Enum[ProxyDeploymentTypeEnum] { case PROXY_DEPLOYMENT_TYPE_UNSPECIFIED, STANDARD, EXTENSIBLE }
		enum StateEnum extends Enum[StateEnum] { case RUNTIME_STATE_UNSPECIFIED, READY, PROGRESSING, ERROR }
	}
	case class GoogleCloudApigeeV1Deployment(
	  /** API proxy revision. */
		revision: Option[String] = None,
	  /** Errors reported for this deployment. Populated only when state == ERROR. &#42;&#42;Note&#42;&#42;: This field is displayed only when viewing deployment status. */
		errors: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** API proxy. */
		apiProxy: Option[String] = None,
	  /** Output only. The type of the deployment (standard or extensible) Deployed proxy revision will be marked as extensible in following 2 cases. 1. The deployed proxy revision uses extensible policies. 2. If a environment supports flowhooks and flow hook is configured. */
		proxyDeploymentType: Option[Schema.GoogleCloudApigeeV1Deployment.ProxyDeploymentTypeEnum] = None,
	  /** The full resource name of Cloud IAM Service Account that this deployment is using, eg, `projects/-/serviceAccounts/{email}`. */
		serviceAccount: Option[String] = None,
	  /** Status reported by runtime pods. &#42;&#42;Note&#42;&#42;: &#42;&#42;This field is deprecated&#42;&#42;. Runtime versions 1.3 and above report instance level status rather than pod status. */
		pods: Option[List[Schema.GoogleCloudApigeeV1PodStatus]] = None,
	  /** Conflicts in the desired state routing configuration. The presence of conflicts does not cause the state to be `ERROR`, but it will mean that some of the deployment's base paths are not routed to its environment. If the conflicts change, the state will transition to `PROGRESSING` until the latest configuration is rolled out to all instances. &#42;&#42;Note&#42;&#42;: This field is displayed only when viewing deployment status. */
		routeConflicts: Option[List[Schema.GoogleCloudApigeeV1DeploymentChangeReportRoutingConflict]] = None,
	  /** Current state of the deployment. &#42;&#42;Note&#42;&#42;: This field is displayed only when viewing deployment status. */
		state: Option[Schema.GoogleCloudApigeeV1Deployment.StateEnum] = None,
	  /** Status reported by each runtime instance. &#42;&#42;Note&#42;&#42;: This field is displayed only when viewing deployment status. */
		instances: Option[List[Schema.GoogleCloudApigeeV1InstanceDeploymentStatus]] = None,
	  /** Time the API proxy was marked `deployed` in the control plane in millisconds since epoch. */
		deployStartTime: Option[String] = None,
	  /** Environment. */
		environment: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1SecurityAssessmentResultScoringResultAssessmentRecommendationRecommendationLink(
	  /** The text of the url. (ie: "Learn more") */
		text: Option[String] = None,
	  /** The link itself. */
		uri: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1CustomReport(
	  /** Legacy field: not used much. Contains the sort order for the sort columns */
		sortOrder: Option[String] = None,
	  /** Legacy field: not used. Contains the from time for the report */
		fromTime: Option[String] = None,
	  /** This contains the list of dimensions for the report */
		dimensions: Option[List[String]] = None,
	  /** Legacy field: not used. This field contains a list of tags associated with custom report */
		tags: Option[List[String]] = None,
	  /** Output only. Unix time when the app was created json key: createdAt */
		createdAt: Option[String] = None,
	  /** Output only. Environment name */
		environment: Option[String] = None,
	  /** Legacy field: not used. This field contains the offset for the data */
		offset: Option[String] = None,
	  /** Output only. Organization name */
		organization: Option[String] = None,
	  /** This field contains the chart type for the report */
		chartType: Option[String] = None,
	  /** Output only. Modified time of this entity as milliseconds since epoch. json key: lastModifiedAt */
		lastModifiedAt: Option[String] = None,
	  /** Output only. Last viewed time of this entity as milliseconds since epoch */
		lastViewedAt: Option[String] = None,
	  /** Required. This contains the list of metrics */
		metrics: Option[List[Schema.GoogleCloudApigeeV1CustomReportMetric]] = None,
	  /** Legacy field: not used much. Contains the list of sort by columns */
		sortByCols: Option[List[String]] = None,
	  /** This field contains report properties such as ui metadata etc. */
		properties: Option[List[Schema.GoogleCloudApigeeV1ReportProperty]] = None,
	  /** Legacy field: not used. Contains the end time for the report */
		toTime: Option[String] = None,
	  /** Legacy field: not used This field contains the limit for the result retrieved */
		limit: Option[String] = None,
	  /** This is the display name for the report */
		displayName: Option[String] = None,
	  /** Legacy field: not used. This field contains the top k parameter value for restricting the result */
		topk: Option[String] = None,
	  /** Required. Unique identifier for the report T his is a legacy field used to encode custom report unique id */
		name: Option[String] = None,
	  /** This field contains the filter expression */
		filter: Option[String] = None,
	  /** Legacy field: not used. This field contains a list of comments associated with custom report */
		comments: Option[List[String]] = None,
	  /** This field contains the time unit of aggregation for the report */
		timeUnit: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ListSecurityProfilesResponse(
	  /** List of security profiles in the organization. The profiles may be attached or unattached to any environment. This will return latest revision of each profile. */
		securityProfiles: Option[List[Schema.GoogleCloudApigeeV1SecurityProfile]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1SyncAuthorization(
	  /** Entity tag (ETag) used for optimistic concurrency control as a way to help prevent simultaneous updates from overwriting each other. For example, when you call [getSyncAuthorization](organizations/getSyncAuthorization) an ETag is returned in the response. Pass that ETag when calling the [setSyncAuthorization](organizations/setSyncAuthorization) to ensure that you are updating the correct version. If you don't pass the ETag in the call to `setSyncAuthorization`, then the existing authorization is overwritten indiscriminately. &#42;&#42;Note&#42;&#42;: We strongly recommend that you use the ETag in the read-modify-write cycle to avoid race conditions. */
		etag: Option[String] = None,
	  /** Required. Array of service accounts to grant access to control plane resources, each specified using the following format: `serviceAccount:` service-account-name. The service-account-name is formatted like an email address. For example: `my-synchronizer-manager-service_account@my_project_id.iam.gserviceaccount.com` You might specify multiple service accounts, for example, if you have multiple environments and wish to assign a unique service account to each one. The service accounts must have &#42;&#42;Apigee Synchronizer Manager&#42;&#42; role. See also [Create service accounts](https://cloud.google.com/apigee/docs/hybrid/latest/sa-about#create-the-service-accounts). */
		identities: Option[List[String]] = None
	)
	
	case class GoogleCloudApigeeV1ApiDoc(
	  /** Optional. Whether a callback URL is required when this catalog item's API product is enabled in a developer app. When true, a portal user will be required to input a URL when managing the app (this is typically used for the app's OAuth flow). */
		requireCallbackUrl: Option[Boolean] = None,
	  /** Output only. The ID of the parent portal. */
		siteId: Option[String] = None,
	  /** Optional. Boolean flag that manages user access to the catalog item. When true, the catalog item has public visibility and can be viewed anonymously; otherwise, only registered users may view it. Note: when the parent portal is enrolled in the [audience management feature](https://cloud.google.com/apigee/docs/api-platform/publish/portal/portal-audience#enrolling_in_the_beta_release_of_the_audience_management_feature), and this flag is set to false, visibility is set to an indeterminate state and must be explicitly specified in the management UI (see [Manage the visibility of an API in your portal](https://cloud.google.com/apigee/docs/api-platform/publish/portal/publish-apis#visibility)). Additionally, when enrolled in the audience management feature, updates to this flag will be ignored as visibility permissions must be updated in the management UI. */
		anonAllowed: Option[Boolean] = None,
	  /** Optional. Immutable. DEPRECATED: use the `apiProductName` field instead */
		edgeAPIProductName: Option[String] = None,
	  /** Output only. Time the catalog item was last modified in milliseconds since epoch. */
		modified: Option[String] = None,
	  /** Required. The user-facing name of the catalog item. `title` must be a non-empty string with a max length of 255 characters. */
		title: Option[String] = None,
	  /** Output only. The ID of the catalog item. */
		id: Option[String] = None,
	  /** Optional. The IDs of the API categories to which this catalog item belongs. */
		categoryIds: Option[List[String]] = None,
	  /** Optional. Denotes whether the catalog item is published to the portal or is in a draft state. When the parent portal is enrolled in the [audience management feature](https://cloud.google.com/apigee/docs/api-platform/publish/portal/portal-audience#enrolling_in_the_beta_release_of_the_audience_management_feature), the visibility can be set to public on creation by setting the anonAllowed flag to true or further managed in the management UI (see [Manage the visibility of an API in your portal](https://cloud.google.com/apigee/docs/api-platform/publish/portal/publish-apis#visibility)) before it can be visible to any users. If not enrolled in the audience management feature, the visibility is managed by the `anonAllowed` flag. */
		published: Option[Boolean] = None,
	  /** Optional. Description of the catalog item. Max length is 10,000 characters. */
		description: Option[String] = None,
	  /** Optional. DEPRECATED: DO NOT USE */
		specId: Option[String] = None,
	  /** Optional. DEPRECATED: manage documentation through the `getDocumentation` and `updateDocumentation` methods */
		graphqlSchemaDisplayName: Option[String] = None,
	  /** Optional. DEPRECATED: manage documentation through the `getDocumentation` and `updateDocumentation` methods */
		graphqlEndpointUrl: Option[String] = None,
	  /** Optional. DEPRECATED: use the `published` field instead */
		visibility: Option[Boolean] = None,
	  /** Optional. Location of the image used for the catalog item in the catalog. This can be either an image with an external URL or a file path for [image files stored in the portal](/apigee/docs/api-platform/publish/portal/portal-files"), for example, `/files/book-tree.jpg`. When specifying the URL of an external image, the image won't be uploaded to your assets; additionally, loading the image in the integrated portal will be subject to its availability, which may be blocked or restricted by [content security policies](/apigee/docs/api-platform/publish/portal/csp). Max length of file path is 2,083 characters. */
		imageUrl: Option[String] = None,
	  /** Optional. DEPRECATED: manage documentation through the `getDocumentation` and `updateDocumentation` methods */
		graphqlSchema: Option[String] = None,
	  /** Required. Immutable. The `name` field of the associated [API product](/apigee/docs/reference/apis/apigee/rest/v1/organizations.apiproducts). A portal may have only one catalog item associated with a given API product. */
		apiProductName: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1GenerateUploadUrlResponse(
	  /** The Google Cloud Storage signed URL that can be used to upload a new Archive zip file. */
		uploadUri: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ListApiProxiesResponse(
		proxies: Option[List[Schema.GoogleCloudApigeeV1ApiProxy]] = None
	)
	
	case class GoogleCloudApigeeV1BatchComputeSecurityAssessmentResultsRequest(
	  /** Required. Scope of the resources for the computation. For Apigee, the environment is the scope of the resources. */
		scope: Option[String] = None,
	  /** Optional. A page token, received from a previous `BatchComputeSecurityAssessmentResults` call. Provide this to retrieve the subsequent page. */
		pageToken: Option[String] = None,
	  /** Optional. The maximum number of results to return. The service may return fewer than this value. If unspecified, at most 50 results will be returned. */
		pageSize: Option[Int] = None,
	  /** Include only these resources. */
		include: Option[Schema.GoogleCloudApigeeV1BatchComputeSecurityAssessmentResultsRequestResourceArray] = None,
	  /** Required. Name of the profile that is used for computation. */
		profile: Option[String] = None,
	  /** Include all resources under the scope. */
		includeAllResources: Option[Schema.GoogleCloudApigeeV1BatchComputeSecurityAssessmentResultsRequestIncludeAll] = None
	)
	
	case class GoogleCloudApigeeV1ApiProxyRevision(
	  /** List of ProxyEndpoints in the `/proxies` directory of the API proxy. Typically, this element is included only when the API proxy was created using the Edge UI. This is a 'manifest' setting designed to provide visibility into the contents of the API proxy. */
		proxyEndpoints: Option[List[String]] = None,
	  /** List of the targets included in the API proxy revision. */
		targets: Option[List[String]] = None,
	  /** API proxy revision. */
		revision: Option[String] = None,
	  /** Version of the API proxy configuration schema to which the API proxy conforms. Currently, the only supported value is 4.0 (`majorVersion.minorVersion`). This setting may be used in the future to track the evolution of the API proxy format. */
		configurationVersion: Option[Schema.GoogleCloudApigeeV1ConfigVersion] = None,
	  /** Time that the API proxy revision was last modified in milliseconds since epoch. */
		lastModifiedAt: Option[String] = None,
	  /** Base URL of the API proxy. */
		basepaths: Option[List[String]] = None,
	  /** List of policy names included in the API proxy revision.. */
		policies: Option[List[String]] = None,
	  /** List of IntegrationEndpoints in the '/integration-endpoints' directory of the API proxy. This is a 'manifest' setting designed to provide visibility into the contents of the API proxy. */
		integrationEndpoints: Option[List[String]] = None,
	  /** List of resource files included in the API proxy revision. */
		resourceFiles: Option[Schema.GoogleCloudApigeeV1ResourceFiles] = None,
	  /** List of the teams included in the API proxy revision. */
		teams: Option[List[String]] = None,
	  /** List of TargetEndpoints in the `/targets` directory of the API proxy. Typically, this element is included only when the API proxy was created using the Edge UI. This is a 'manifest' setting designed to provide visibility into the contents of the API proxy. */
		targetEndpoints: Option[List[String]] = None,
	  /** List of the shared flows included in the API proxy revision. */
		sharedFlows: Option[List[String]] = None,
	  /** Output only. This field will be marked as true if revision contains any policies marked as extensible. */
		hasExtensiblePolicy: Option[Boolean] = None,
	  /** List of the resources included in the API proxy revision formatted as "{type}://{name}". */
		resources: Option[List[String]] = None,
	  /** Type. Set to `Application`. Maintained for compatibility with the Apigee Edge API. */
		`type`: Option[String] = None,
	  /** List of TargetServers referenced in any TargetEndpoint in the API proxy. Typically, you will see this element only when the API proxy was created using the Edge UI. This is a 'manifest' setting designed to provide visibility into the contents of the API proxy. */
		targetServers: Option[List[String]] = None,
	  /** Revision number, app name, and organization for the API proxy. */
		contextInfo: Option[String] = None,
	  /** Output only. The archive that generated this proxy revision. This field is only present on proxy revisions that were generated by an archive. Proxies generated by archives cannot be updated, deleted, or deployed to other environments. Format: `organizations/&#42;/environments/&#42;/archiveDeployments/&#42;` */
		archive: Option[String] = None,
	  /** Human-readable name of the API proxy. */
		displayName: Option[String] = None,
	  /** List of proxy names included in the API proxy revision. */
		proxies: Option[List[String]] = None,
	  /** OpenAPI Specification that is associated with the API proxy. The value is set to a URL or to a path in the specification store. */
		spec: Option[String] = None,
	  /** Name of the API proxy. */
		name: Option[String] = None,
	  /** Description of the API proxy revision. */
		description: Option[String] = None,
	  /** Metadata describing the API proxy revision as a key-value map. */
		entityMetaDataAsProperties: Option[Map[String, String]] = None,
	  /** Time that the API proxy revision was created in milliseconds since epoch. */
		createdAt: Option[String] = None
	)
	
	object GoogleIamV1AuditLogConfig {
		enum LogTypeEnum extends Enum[LogTypeEnum] { case LOG_TYPE_UNSPECIFIED, ADMIN_READ, DATA_WRITE, DATA_READ }
	}
	case class GoogleIamV1AuditLogConfig(
	  /** The log type that this config enables. */
		logType: Option[Schema.GoogleIamV1AuditLogConfig.LogTypeEnum] = None,
	  /** Specifies the identities that do not cause logging for this type of permission. Follows the same format of Binding.members. */
		exemptedMembers: Option[List[String]] = None
	)
	
	case class GoogleCloudApigeeV1CreditDeveloperBalanceRequest(
	  /** The amount of money to be credited. The wallet corresponding to the currency specified within `transaction_amount` will be updated. For example, if you specified `currency_code` within `transaction_amount` as "USD", then the amount would be added to the wallet which has the "USD" currency or if no such wallet exists, a new wallet will be created with the "USD" currency. */
		transactionAmount: Option[Schema.GoogleTypeMoney] = None,
	  /** Each transaction_id uniquely identifies a credit balance request. If multiple requests are received with the same transaction_id, only one of them will be considered. */
		transactionId: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1DeveloperSubscription(
	  /** Time when the API product subscription starts in milliseconds since epoch. */
		startTime: Option[String] = None,
	  /** Name of the API product for which the developer is purchasing a subscription. */
		apiproduct: Option[String] = None,
	  /** Output only. Time when the API product subscription was last modified in milliseconds since epoch. */
		lastModifiedAt: Option[String] = None,
	  /** Output only. Name of the API product subscription. */
		name: Option[String] = None,
	  /** Time when the API product subscription ends in milliseconds since epoch. */
		endTime: Option[String] = None,
	  /** Output only. Time when the API product subscription was created in milliseconds since epoch. */
		createdAt: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1Reference(
	  /** The type of resource referred to by this reference. Valid values are 'KeyStore' or 'TrustStore'. */
		resourceType: Option[String] = None,
	  /** Required. The resource id of this reference. Values must match the regular expression [\w\s\-.]+. */
		name: Option[String] = None,
	  /** Optional. A human-readable description of this reference. */
		description: Option[String] = None,
	  /** Required. The id of the resource to which this reference refers. Must be the id of a resource that exists in the parent environment and is of the given resource_type. */
		refers: Option[String] = None
	)
	
	object GoogleCloudApigeeV1SecurityAction {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ENABLED, DISABLED }
	}
	case class GoogleCloudApigeeV1SecurityAction(
	  /** Flag a request through if it matches this SecurityAction. */
		flag: Option[Schema.GoogleCloudApigeeV1SecurityActionFlag] = None,
	  /** Optional. If unset, this would apply to all proxies in the environment. If set, this action is enforced only if at least one proxy in the repeated list is deployed at the time of enforcement. If set, several restrictions are enforced on SecurityActions. There can be at most 100 enabled actions with proxies set in an env. Several other restrictions apply on conditions and are detailed later. */
		apiProxies: Option[List[String]] = None,
	  /** Allow a request through if it matches this SecurityAction. */
		allow: Option[Schema.GoogleCloudApigeeV1SecurityActionAllow] = None,
	  /** Optional. An optional user provided description of the SecurityAction. */
		description: Option[String] = None,
	  /** Output only. The update time for this SecurityAction. This reflects when this SecurityAction changed states. */
		updateTime: Option[String] = None,
	  /** Output only. The create time for this SecurityAction. */
		createTime: Option[String] = None,
	  /** Required. A valid SecurityAction must contain at least one condition. */
		conditionConfig: Option[Schema.GoogleCloudApigeeV1SecurityActionConditionConfig] = None,
	  /** Deny a request through if it matches this SecurityAction. */
		deny: Option[Schema.GoogleCloudApigeeV1SecurityActionDeny] = None,
	  /** Required. Only an ENABLED SecurityAction is enforced. An ENABLED SecurityAction past its expiration time will not be enforced. */
		state: Option[Schema.GoogleCloudApigeeV1SecurityAction.StateEnum] = None,
	  /** The expiration for this SecurityAction. */
		expireTime: Option[String] = None,
	  /** Immutable. This field is ignored during creation as per AIP-133. Please set the `security_action_id` field in the CreateSecurityActionRequest when creating a new SecurityAction. Format: organizations/{org}/environments/{env}/securityActions/{security_action} */
		name: Option[String] = None,
	  /** Input only. The TTL for this SecurityAction. */
		ttl: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1SecurityActionDeny(
	  /** Optional. The HTTP response code if the Action = DENY. */
		responseCode: Option[Int] = None
	)
	
	object GoogleCloudApigeeV1OperationMetadata {
		enum OperationTypeEnum extends Enum[OperationTypeEnum] { case OPERATION_TYPE_UNSPECIFIED, INSERT, DELETE, UPDATE }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, NOT_STARTED, IN_PROGRESS, FINISHED }
	}
	case class GoogleCloudApigeeV1OperationMetadata(
	  /** Progress of the operation. */
		progress: Option[Schema.GoogleCloudApigeeV1OperationMetadataProgress] = None,
	  /** Name of the resource for which the operation is operating on. */
		targetResourceName: Option[String] = None,
		operationType: Option[Schema.GoogleCloudApigeeV1OperationMetadata.OperationTypeEnum] = None,
	  /** Warnings encountered while executing the operation. */
		warnings: Option[List[String]] = None,
		state: Option[Schema.GoogleCloudApigeeV1OperationMetadata.StateEnum] = None
	)
	
	case class GoogleCloudApigeeV1ListDatastoresResponse(
	  /** A list of datastores */
		datastores: Option[List[Schema.GoogleCloudApigeeV1Datastore]] = None
	)
	
	case class GoogleApiHttpBody(
	  /** The HTTP Content-Type header value specifying the content type of the body. */
		contentType: Option[String] = None,
	  /** The HTTP request/response body as raw binary. */
		data: Option[String] = None,
	  /** Application specific response metadata. Must be set in the first response for streaming APIs. */
		extensions: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GoogleCloudApigeeV1GenerateDownloadUrlRequest(
	
	)
	
	case class GoogleCloudApigeeV1DeveloperBalance(
	  /** Output only. List of all wallets. Each individual wallet stores the account balance for a particular currency. */
		wallets: Option[List[Schema.GoogleCloudApigeeV1DeveloperBalanceWallet]] = None
	)
	
	object GoogleCloudApigeeV1ApiProxy {
		enum ApiProxyTypeEnum extends Enum[ApiProxyTypeEnum] { case API_PROXY_TYPE_UNSPECIFIED, PROGRAMMABLE, CONFIGURABLE }
	}
	case class GoogleCloudApigeeV1ApiProxy(
	  /** User labels applied to this API Proxy. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Name of the API proxy. */
		name: Option[String] = None,
	  /** Output only. Whether this proxy is read-only. A read-only proxy cannot have new revisions created through calls to CreateApiProxyRevision. A proxy is read-only if it was generated by an archive. */
		readOnly: Option[Boolean] = None,
	  /** Output only. List of revisions defined for the API proxy. */
		revision: Option[List[String]] = None,
	  /** Output only. The id of the most recently created revision for this api proxy. */
		latestRevisionId: Option[String] = None,
	  /** Output only. The type of the API proxy. */
		apiProxyType: Option[Schema.GoogleCloudApigeeV1ApiProxy.ApiProxyTypeEnum] = None,
	  /** Output only. Metadata describing the API proxy. */
		metaData: Option[Schema.GoogleCloudApigeeV1EntityMetadata] = None
	)
	
	case class GoogleCloudApigeeV1ProfileConfigAuthorization(
	
	)
	
	case class GoogleCloudApigeeV1Quota(
	  /** Time unit defined for the `interval`. Valid values include `minute`, `hour`, `day`, or `month`. If `limit` and `interval` are valid, the default value is `hour`; otherwise, the default is null. */
		timeUnit: Option[String] = None,
	  /** Required. Upper limit allowed for the time interval and time unit specified. Requests exceeding this limit will be rejected. */
		limit: Option[String] = None,
	  /** Required. Time interval over which the number of request messages is calculated. */
		interval: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1Metadata(
	  /** List of additional information such as data source, if result was truncated. For example: ``` "notices": [ "Source:Postgres", "PG Host:uappg0rw.e2e.apigeeks.net", "query served by:4b64601e-40de-4eb1-bfb9-eeee7ac929ed", "Table used: edge.api.uapgroup2.agg_api" ]``` */
		notices: Option[List[String]] = None,
	  /** List of error messages as strings. */
		errors: Option[List[String]] = None
	)
	
	case class GoogleCloudApigeeV1ListEnvironmentResourcesResponse(
	  /** List of resources files. */
		resourceFile: Option[List[Schema.GoogleCloudApigeeV1ResourceFile]] = None
	)
	
	case class GoogleCloudApigeeV1SecurityActionConditionConfig(
	  /** Optional. A list of developers. Limit 1000 per action. */
		developers: Option[List[String]] = None,
	  /** Optional. A list of user agents to deny. We look for exact matches. Limit 50 per action. */
		userAgents: Option[List[String]] = None,
	  /** Optional. A list of API Products. Limit 1000 per action. */
		apiProducts: Option[List[String]] = None,
	  /** Optional. Act only on particular HTTP methods. E.g. A read-only API can block POST/PUT/DELETE methods. Accepted values are: GET, HEAD, POST, PUT, DELETE, CONNECT, OPTIONS, TRACE and PATCH. */
		httpMethods: Option[List[String]] = None,
	  /** Optional. A list of Bot Reasons. Current options: Flooder, Brute Guessor, Static Content Scraper, OAuth Abuser, Robot Abuser, TorListRule, Advanced Anomaly Detection, Advanced API Scraper, Search Engine Crawlers, Public Clouds, Public Cloud AWS, Public Cloud Azure, and Public Cloud Google. */
		botReasons: Option[List[String]] = None,
	  /** Optional. A list of access_tokens. Limit 1000 per action. */
		accessTokens: Option[List[String]] = None,
	  /** Optional. A list of API keys. Limit 1000 per action. */
		apiKeys: Option[List[String]] = None,
	  /** Optional. A list of IP addresses. This could be either IPv4 or IPv6. Limited to 100 per action. */
		ipAddressRanges: Option[List[String]] = None,
	  /** Optional. A list of developer apps. Limit 1000 per action. */
		developerApps: Option[List[String]] = None,
	  /** Optional. A list of ASN numbers to act on, e.g. 23. https://en.wikipedia.org/wiki/Autonomous_system_(Internet) This uses int64 instead of uint32 because of https://linter.aip.dev/141/forbidden-types. */
		asns: Option[List[String]] = None,
	  /** Optional. A list of countries/region codes to act on, e.g. US. This follows https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2. */
		regionCodes: Option[List[String]] = None
	)
	
	case class GoogleCloudApigeeV1AccessGet(
		value: Option[String] = None,
		name: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ListApiProductsResponse(
	  /** Lists all API product names defined for an organization. */
		apiProduct: Option[List[Schema.GoogleCloudApigeeV1ApiProduct]] = None
	)
	
	case class GoogleCloudApigeeV1ProfileConfigCORS(
	
	)
	
	case class GoogleCloudApigeeV1InstanceDeploymentStatusDeployedRoute(
	  /** Destination environment. This will be empty if the route is not yet reported. */
		environment: Option[String] = None,
	  /** Environment group where this route is installed. */
		envgroup: Option[String] = None,
	  /** Percentage of ingress replicas reporting this route. */
		percentage: Option[Int] = None,
	  /** Base path in the routing table. */
		basepath: Option[String] = None
	)
	
	case class GoogleCloudApigeeV1ListSharedFlowsResponse(
		sharedFlows: Option[List[Schema.GoogleCloudApigeeV1SharedFlow]] = None
	)
	
	object GoogleCloudApigeeV1AnalyticsConfig {
		enum StateEnum extends Enum[StateEnum] { case ADDON_STATE_UNSPECIFIED, ENABLING, ENABLED, DISABLING, DISABLED }
	}
	case class GoogleCloudApigeeV1AnalyticsConfig(
	  /** Output only. The latest update time. */
		updateTime: Option[String] = None,
	  /** Output only. Time at which the Analytics add-on expires in milliseconds since epoch. If unspecified, the add-on will never expire. */
		expireTimeMillis: Option[String] = None,
	  /** Whether the Analytics add-on is enabled. */
		enabled: Option[Boolean] = None,
	  /** Output only. The state of the Analytics add-on. */
		state: Option[Schema.GoogleCloudApigeeV1AnalyticsConfig.StateEnum] = None
	)
}
