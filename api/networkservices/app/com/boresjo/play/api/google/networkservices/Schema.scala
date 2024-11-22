package com.boresjo.play.api.google.networkservices

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
	
	case class ListLbTrafficExtensionsResponse(
	  /** The list of `LbTrafficExtension` resources. */
		lbTrafficExtensions: Option[List[Schema.LbTrafficExtension]] = None,
	  /** A token identifying a page of results that the server returns. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object LbTrafficExtension {
		enum LoadBalancingSchemeEnum extends Enum[LoadBalancingSchemeEnum] { case LOAD_BALANCING_SCHEME_UNSPECIFIED, INTERNAL_MANAGED, EXTERNAL_MANAGED }
	}
	case class LbTrafficExtension(
	  /** Required. Identifier. Name of the `LbTrafficExtension` resource in the following format: `projects/{project}/locations/{location}/lbTrafficExtensions/{lb_traffic_extension}`. */
		name: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. A human-readable description of the resource. */
		description: Option[String] = None,
	  /** Optional. Set of labels associated with the `LbTrafficExtension` resource. The format must comply with [the requirements for labels](https://cloud.google.com/compute/docs/labeling-resources#requirements) for Google Cloud resources. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. A list of references to the forwarding rules to which this service extension is attached. At least one forwarding rule is required. There can be only one `LBTrafficExtension` resource per forwarding rule. */
		forwardingRules: Option[List[String]] = None,
	  /** Required. A set of ordered extension chains that contain the match conditions and extensions to execute. Match conditions for each extension chain are evaluated in sequence for a given request. The first extension chain that has a condition that matches the request is executed. Any subsequent extension chains do not execute. Limited to 5 extension chains per resource. */
		extensionChains: Option[List[Schema.ExtensionChain]] = None,
	  /** Required. All backend services and forwarding rules referenced by this extension must share the same load balancing scheme. Supported values: `INTERNAL_MANAGED`, `EXTERNAL_MANAGED`. For more information, refer to [Backend services overview](https://cloud.google.com/load-balancing/docs/backend-service). */
		loadBalancingScheme: Option[Schema.LbTrafficExtension.LoadBalancingSchemeEnum] = None,
	  /** Optional. The metadata provided here is included in the `ProcessingRequest.metadata_context.filter_metadata` map field. The metadata is available under the key `com.google.lb_traffic_extension.`. The following variables are supported in the metadata: `{forwarding_rule_id}` - substituted with the forwarding rule's fully qualified resource name. This field is not supported for plugin extensions. Setting it results in a validation error. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class ExtensionChain(
	  /** Required. The name for this extension chain. The name is logged as part of the HTTP request logs. The name must conform with RFC-1034, is restricted to lower-cased letters, numbers and hyphens, and can have a maximum length of 63 characters. Additionally, the first character must be a letter and the last a letter or a number. */
		name: Option[String] = None,
	  /** Required. Conditions under which this chain is invoked for a request. */
		matchCondition: Option[Schema.ExtensionChainMatchCondition] = None,
	  /** Required. A set of extensions to execute for the matching request. At least one extension is required. Up to 3 extensions can be defined for each extension chain for `LbTrafficExtension` resource. `LbRouteExtension` chains are limited to 1 extension per extension chain. */
		extensions: Option[List[Schema.ExtensionChainExtension]] = None
	)
	
	case class ExtensionChainMatchCondition(
	  /** Required. A Common Expression Language (CEL) expression that is used to match requests for which the extension chain is executed. For more information, see [CEL matcher language reference](https://cloud.google.com/service-extensions/docs/cel-matcher-language-reference). */
		celExpression: Option[String] = None
	)
	
	object ExtensionChainExtension {
		enum SupportedEventsEnum extends Enum[SupportedEventsEnum] { case EVENT_TYPE_UNSPECIFIED, REQUEST_HEADERS, REQUEST_BODY, RESPONSE_HEADERS, RESPONSE_BODY, REQUEST_TRAILERS, RESPONSE_TRAILERS }
	}
	case class ExtensionChainExtension(
	  /** Required. The name for this extension. The name is logged as part of the HTTP request logs. The name must conform with RFC-1034, is restricted to lower-cased letters, numbers and hyphens, and can have a maximum length of 63 characters. Additionally, the first character must be a letter and the last a letter or a number. */
		name: Option[String] = None,
	  /** Optional. The `:authority` header in the gRPC request sent from Envoy to the extension service. Required for Callout extensions. This field is not supported for plugin extensions. Setting it results in a validation error. */
		authority: Option[String] = None,
	  /** Required. The reference to the service that runs the extension. To configure a callout extension, `service` must be a fully-qualified reference to a [backend service](https://cloud.google.com/compute/docs/reference/rest/v1/backendServices) in the format: `https://www.googleapis.com/compute/v1/projects/{project}/regions/{region}/backendServices/{backendService}` or `https://www.googleapis.com/compute/v1/projects/{project}/global/backendServices/{backendService}`. To configure a plugin extension, `service` must be a reference to a [`WasmPlugin` resource](https://cloud.google.com/service-extensions/docs/reference/rest/v1beta1/projects.locations.wasmPlugins) in the format: `projects/{project}/locations/{location}/wasmPlugins/{plugin}` or `//networkservices.googleapis.com/projects/{project}/locations/{location}/wasmPlugins/{wasmPlugin}`. Plugin extensions are currently supported for the `LbTrafficExtension` and the `LbRouteExtension` resources. */
		service: Option[String] = None,
	  /** Optional. A set of events during request or response processing for which this extension is called. This field is required for the `LbTrafficExtension` resource. It must not be set for the `LbRouteExtension` resource, otherwise a validation error is returned. */
		supportedEvents: Option[List[Schema.ExtensionChainExtension.SupportedEventsEnum]] = None,
	  /** Optional. Specifies the timeout for each individual message on the stream. The timeout must be between `10`-`1000` milliseconds. Required for callout extensions. This field is not supported for plugin extensions. Setting it results in a validation error. */
		timeout: Option[String] = None,
	  /** Optional. Determines how the proxy behaves if the call to the extension fails or times out. When set to `TRUE`, request or response processing continues without error. Any subsequent extensions in the extension chain are also executed. When set to `FALSE` or the default setting of `FALSE` is used, one of the following happens: &#42; If response headers have not been delivered to the downstream client, a generic 500 error is returned to the client. The error response can be tailored by configuring a custom error response in the load balancer. &#42; If response headers have been delivered, then the HTTP stream to the downstream client is reset. */
		failOpen: Option[Boolean] = None,
	  /** Optional. List of the HTTP headers to forward to the extension (from the client or backend). If omitted, all headers are sent. Each element is a string indicating the header name. */
		forwardHeaders: Option[List[String]] = None,
	  /** Optional. The metadata provided here is included as part of the `metadata_context` (of type `google.protobuf.Struct`) in the `ProcessingRequest` message sent to the extension server. The metadata is available under the namespace `com.google....`. For example: `com.google.lb_traffic_extension.lbtrafficextension1.chain1.ext1`. The following variables are supported in the metadata: `{forwarding_rule_id}` - substituted with the forwarding rule's fully qualified resource name. This field is not supported for plugin extensions. Setting it results in a validation error. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class ListLbRouteExtensionsResponse(
	  /** The list of `LbRouteExtension` resources. */
		lbRouteExtensions: Option[List[Schema.LbRouteExtension]] = None,
	  /** A token identifying a page of results that the server returns. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object LbRouteExtension {
		enum LoadBalancingSchemeEnum extends Enum[LoadBalancingSchemeEnum] { case LOAD_BALANCING_SCHEME_UNSPECIFIED, INTERNAL_MANAGED, EXTERNAL_MANAGED }
	}
	case class LbRouteExtension(
	  /** Required. Identifier. Name of the `LbRouteExtension` resource in the following format: `projects/{project}/locations/{location}/lbRouteExtensions/{lb_route_extension}`. */
		name: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. A human-readable description of the resource. */
		description: Option[String] = None,
	  /** Optional. Set of labels associated with the `LbRouteExtension` resource. The format must comply with [the requirements for labels](https://cloud.google.com/compute/docs/labeling-resources#requirements) for Google Cloud resources. */
		labels: Option[Map[String, String]] = None,
	  /** Required. A list of references to the forwarding rules to which this service extension is attached. At least one forwarding rule is required. There can be only one `LbRouteExtension` resource per forwarding rule. */
		forwardingRules: Option[List[String]] = None,
	  /** Required. A set of ordered extension chains that contain the match conditions and extensions to execute. Match conditions for each extension chain are evaluated in sequence for a given request. The first extension chain that has a condition that matches the request is executed. Any subsequent extension chains do not execute. Limited to 5 extension chains per resource. */
		extensionChains: Option[List[Schema.ExtensionChain]] = None,
	  /** Required. All backend services and forwarding rules referenced by this extension must share the same load balancing scheme. Supported values: `INTERNAL_MANAGED`, `EXTERNAL_MANAGED`. For more information, refer to [Backend services overview](https://cloud.google.com/load-balancing/docs/backend-service). */
		loadBalancingScheme: Option[Schema.LbRouteExtension.LoadBalancingSchemeEnum] = None,
	  /** Optional. The metadata provided here is included as part of the `metadata_context` (of type `google.protobuf.Struct`) in the `ProcessingRequest` message sent to the extension server. The metadata is available under the namespace `com.google.lb_route_extension.`. The following variables are supported in the metadata Struct: `{forwarding_rule_id}` - substituted with the forwarding rule's fully qualified resource name. This field is not supported for plugin extensions. Setting it results in a validation error. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class ListAuthzExtensionsResponse(
	  /** The list of `AuthzExtension` resources. */
		authzExtensions: Option[List[Schema.AuthzExtension]] = None,
	  /** A token identifying a page of results that the server returns. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object AuthzExtension {
		enum LoadBalancingSchemeEnum extends Enum[LoadBalancingSchemeEnum] { case LOAD_BALANCING_SCHEME_UNSPECIFIED, INTERNAL_MANAGED, EXTERNAL_MANAGED }
		enum WireFormatEnum extends Enum[WireFormatEnum] { case WIRE_FORMAT_UNSPECIFIED, EXT_PROC_GRPC }
	}
	case class AuthzExtension(
	  /** Required. Identifier. Name of the `AuthzExtension` resource in the following format: `projects/{project}/locations/{location}/authzExtensions/{authz_extension}`. */
		name: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. A human-readable description of the resource. */
		description: Option[String] = None,
	  /** Optional. Set of labels associated with the `AuthzExtension` resource. The format must comply with [the requirements for labels](/compute/docs/labeling-resources#requirements) for Google Cloud resources. */
		labels: Option[Map[String, String]] = None,
	  /** Required. All backend services and forwarding rules referenced by this extension must share the same load balancing scheme. Supported values: `INTERNAL_MANAGED`, `EXTERNAL_MANAGED`. For more information, refer to [Backend services overview](https://cloud.google.com/load-balancing/docs/backend-service). */
		loadBalancingScheme: Option[Schema.AuthzExtension.LoadBalancingSchemeEnum] = None,
	  /** Required. The `:authority` header in the gRPC request sent from Envoy to the extension service. */
		authority: Option[String] = None,
	  /** Required. The reference to the service that runs the extension. To configure a callout extension, `service` must be a fully-qualified reference to a [backend service](https://cloud.google.com/compute/docs/reference/rest/v1/backendServices) in the format: `https://www.googleapis.com/compute/v1/projects/{project}/regions/{region}/backendServices/{backendService}` or `https://www.googleapis.com/compute/v1/projects/{project}/global/backendServices/{backendService}`. */
		service: Option[String] = None,
	  /** Required. Specifies the timeout for each individual message on the stream. The timeout must be between 10-10000 milliseconds. */
		timeout: Option[String] = None,
	  /** Optional. Determines how the proxy behaves if the call to the extension fails or times out. When set to `TRUE`, request or response processing continues without error. Any subsequent extensions in the extension chain are also executed. When set to `FALSE` or the default setting of `FALSE` is used, one of the following happens: &#42; If response headers have not been delivered to the downstream client, a generic 500 error is returned to the client. The error response can be tailored by configuring a custom error response in the load balancer. &#42; If response headers have been delivered, then the HTTP stream to the downstream client is reset. */
		failOpen: Option[Boolean] = None,
	  /** Optional. The metadata provided here is included as part of the `metadata_context` (of type `google.protobuf.Struct`) in the `ProcessingRequest` message sent to the extension server. The metadata is available under the namespace `com.google.authz_extension.`. The following variables are supported in the metadata Struct: `{forwarding_rule_id}` - substituted with the forwarding rule's fully qualified resource name. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** Optional. List of the HTTP headers to forward to the extension (from the client). If omitted, all headers are sent. Each element is a string indicating the header name. */
		forwardHeaders: Option[List[String]] = None,
	  /** Optional. The format of communication supported by the callout extension. If not specified, the default is `EXT_PROC_GRPC`. */
		wireFormat: Option[Schema.AuthzExtension.WireFormatEnum] = None
	)
	
	case class ListEndpointPoliciesResponse(
	  /** List of EndpointPolicy resources. */
		endpointPolicies: Option[List[Schema.EndpointPolicy]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None
	)
	
	object EndpointPolicy {
		enum TypeEnum extends Enum[TypeEnum] { case ENDPOINT_POLICY_TYPE_UNSPECIFIED, SIDECAR_PROXY, GRPC_SERVER }
	}
	case class EndpointPolicy(
	  /** Identifier. Name of the EndpointPolicy resource. It matches pattern `projects/{project}/locations/global/endpointPolicies/{endpoint_policy}`. */
		name: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. Set of label tags associated with the EndpointPolicy resource. */
		labels: Option[Map[String, String]] = None,
	  /** Required. The type of endpoint policy. This is primarily used to validate the configuration. */
		`type`: Option[Schema.EndpointPolicy.TypeEnum] = None,
	  /** Optional. This field specifies the URL of AuthorizationPolicy resource that applies authorization policies to the inbound traffic at the matched endpoints. Refer to Authorization. If this field is not specified, authorization is disabled(no authz checks) for this endpoint. */
		authorizationPolicy: Option[String] = None,
	  /** Required. A matcher that selects endpoints to which the policies should be applied. */
		endpointMatcher: Option[Schema.EndpointMatcher] = None,
	  /** Optional. Port selector for the (matched) endpoints. If no port selector is provided, the matched config is applied to all ports. */
		trafficPortSelector: Option[Schema.TrafficPortSelector] = None,
	  /** Optional. A free-text description of the resource. Max length 1024 characters. */
		description: Option[String] = None,
	  /** Optional. A URL referring to ServerTlsPolicy resource. ServerTlsPolicy is used to determine the authentication policy to be applied to terminate the inbound traffic at the identified backends. If this field is not set, authentication is disabled(open) for this endpoint. */
		serverTlsPolicy: Option[String] = None,
	  /** Optional. A URL referring to a ClientTlsPolicy resource. ClientTlsPolicy can be set to specify the authentication for traffic from the proxy to the actual endpoints. More specifically, it is applied to the outgoing traffic from the proxy to the endpoint. This is typically used for sidecar model where the proxy identifies itself as endpoint to the control plane, with the connection between sidecar and endpoint requiring authentication. If this field is not set, authentication is disabled(open). Applicable only when EndpointPolicyType is SIDECAR_PROXY. */
		clientTlsPolicy: Option[String] = None
	)
	
	case class EndpointMatcher(
	  /** The matcher is based on node metadata presented by xDS clients. */
		metadataLabelMatcher: Option[Schema.EndpointMatcherMetadataLabelMatcher] = None
	)
	
	object EndpointMatcherMetadataLabelMatcher {
		enum MetadataLabelMatchCriteriaEnum extends Enum[MetadataLabelMatchCriteriaEnum] { case METADATA_LABEL_MATCH_CRITERIA_UNSPECIFIED, MATCH_ANY, MATCH_ALL }
	}
	case class EndpointMatcherMetadataLabelMatcher(
	  /** Specifies how matching should be done. Supported values are: MATCH_ANY: At least one of the Labels specified in the matcher should match the metadata presented by xDS client. MATCH_ALL: The metadata presented by the xDS client should contain all of the labels specified here. The selection is determined based on the best match. For example, suppose there are three EndpointPolicy resources P1, P2 and P3 and if P1 has a the matcher as MATCH_ANY , P2 has MATCH_ALL , and P3 has MATCH_ALL . If a client with label connects, the config from P1 will be selected. If a client with label connects, the config from P2 will be selected. If a client with label connects, the config from P3 will be selected. If there is more than one best match, (for example, if a config P4 with selector exists and if a client with label connects), pick up the one with older creation time. */
		metadataLabelMatchCriteria: Option[Schema.EndpointMatcherMetadataLabelMatcher.MetadataLabelMatchCriteriaEnum] = None,
	  /** The list of label value pairs that must match labels in the provided metadata based on filterMatchCriteria This list can have at most 64 entries. The list can be empty if the match criteria is MATCH_ANY, to specify a wildcard match (i.e this matches any client). */
		metadataLabels: Option[List[Schema.EndpointMatcherMetadataLabelMatcherMetadataLabels]] = None
	)
	
	case class EndpointMatcherMetadataLabelMatcherMetadataLabels(
	  /** Required. Label name presented as key in xDS Node Metadata. */
		labelName: Option[String] = None,
	  /** Required. Label value presented as value corresponding to the above key, in xDS Node Metadata. */
		labelValue: Option[String] = None
	)
	
	case class TrafficPortSelector(
	  /** Optional. A list of ports. Can be port numbers or port range (example, [80-90] specifies all ports from 80 to 90, including 80 and 90) or named ports or &#42; to specify all ports. If the list is empty, all ports are selected. */
		ports: Option[List[String]] = None
	)
	
	case class ListWasmPluginVersionsResponse(
	  /** List of `WasmPluginVersion` resources. */
		wasmPluginVersions: Option[List[Schema.WasmPluginVersion]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None
	)
	
	case class WasmPluginVersion(
	  /** Configuration for the plugin. The configuration is provided to the plugin at runtime through the `ON_CONFIGURE` callback. When a new `WasmPluginVersion` resource is created, the digest of the contents is saved in the `plugin_config_digest` field. */
		pluginConfigData: Option[String] = None,
	  /** URI of the plugin configuration stored in the Artifact Registry. The configuration is provided to the plugin at runtime through the `ON_CONFIGURE` callback. The container image must contain only a single file with the name `plugin.config`. When a new `WasmPluginVersion` resource is created, the digest of the container image is saved in the `plugin_config_digest` field. */
		pluginConfigUri: Option[String] = None,
	  /** Identifier. Name of the `WasmPluginVersion` resource in the following format: `projects/{project}/locations/{location}/wasmPlugins/{wasm_plugin}/ versions/{wasm_plugin_version}`. */
		name: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. A human-readable description of the resource. */
		description: Option[String] = None,
	  /** Optional. Set of labels associated with the `WasmPluginVersion` resource. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. URI of the container image containing the plugin, stored in the Artifact Registry. When a new `WasmPluginVersion` resource is created, the digest of the container image is saved in the `image_digest` field. When downloading an image, the digest value is used instead of an image tag. */
		imageUri: Option[String] = None,
	  /** Output only. The resolved digest for the image specified in the `image` field. The digest is resolved during the creation of `WasmPluginVersion` resource. This field holds the digest value, regardless of whether a tag or digest was originally specified in the `image` field. */
		imageDigest: Option[String] = None,
	  /** Output only. This field holds the digest (usually checksum) value for the plugin configuration. The value is calculated based on the contents of `plugin_config_data` or the container image defined by the `plugin_config_uri` field. */
		pluginConfigDigest: Option[String] = None
	)
	
	case class ListWasmPluginsResponse(
	  /** List of `WasmPlugin` resources. */
		wasmPlugins: Option[List[Schema.WasmPlugin]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None
	)
	
	case class WasmPlugin(
	  /** Identifier. Name of the `WasmPlugin` resource in the following format: `projects/{project}/locations/{location}/wasmPlugins/{wasm_plugin}`. */
		name: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. A human-readable description of the resource. */
		description: Option[String] = None,
	  /** Optional. Set of labels associated with the `WasmPlugin` resource. The format must comply with [the following requirements](/compute/docs/labeling-resources#requirements). */
		labels: Option[Map[String, String]] = None,
	  /** Optional. The ID of the `WasmPluginVersion` resource that is the currently serving one. The version referred to must be a child of this `WasmPlugin` resource. */
		mainVersionId: Option[String] = None,
	  /** Optional. Specifies the logging options for the activity performed by this plugin. If logging is enabled, plugin logs are exported to Cloud Logging. Note that the settings relate to the logs generated by using logging statements in your Wasm code. */
		logConfig: Option[Schema.WasmPluginLogConfig] = None,
	  /** Optional. All versions of this `WasmPlugin` resource in the key-value format. The key is the resource ID, and the value is the `VersionDetails` object. Lets you create or update a `WasmPlugin` resource and its versions in a single request. When the `main_version_id` field is not empty, it must point to one of the `VersionDetails` objects in the map. If provided in a `PATCH` request, the new versions replace the previous set. Any version omitted from the `versions` field is removed. Because the `WasmPluginVersion` resource is immutable, if a `WasmPluginVersion` resource with the same name already exists and differs, the request fails. Note: In a `GET` request, this field is populated only if the field `GetWasmPluginRequest.view` is set to `WASM_PLUGIN_VIEW_FULL`. */
		versions: Option[Map[String, Schema.WasmPluginVersionDetails]] = None,
	  /** Output only. List of all [extensions](https://cloud.google.com/service-extensions/docs/overview) that use this `WasmPlugin` resource. */
		usedBy: Option[List[Schema.WasmPluginUsedBy]] = None
	)
	
	object WasmPluginLogConfig {
		enum MinLogLevelEnum extends Enum[MinLogLevelEnum] { case LOG_LEVEL_UNSPECIFIED, TRACE, DEBUG, INFO, WARN, ERROR, CRITICAL }
	}
	case class WasmPluginLogConfig(
	  /** Optional. Specifies whether to enable logging for activity by this plugin. Defaults to `false`. */
		enable: Option[Boolean] = None,
	  /** Non-empty default. Configures the sampling rate of activity logs, where `1.0` means all logged activity is reported and `0.0` means no activity is reported. A floating point value between `0.0` and `1.0` indicates that a percentage of log messages is stored. The default value when logging is enabled is `1.0`. The value of the field must be between `0` and `1` (inclusive). This field can be specified only if logging is enabled for this plugin. */
		sampleRate: Option[BigDecimal] = None,
	  /** Non-empty default. Specificies the lowest level of the plugin logs that are exported to Cloud Logging. This setting relates to the logs generated by using logging statements in your Wasm code. This field is can be set only if logging is enabled for the plugin. If the field is not provided when logging is enabled, it is set to `INFO` by default. */
		minLogLevel: Option[Schema.WasmPluginLogConfig.MinLogLevelEnum] = None
	)
	
	case class WasmPluginVersionDetails(
	  /** Configuration for the plugin. The configuration is provided to the plugin at runtime through the `ON_CONFIGURE` callback. When a new `WasmPluginVersion` version is created, the digest of the contents is saved in the `plugin_config_digest` field. */
		pluginConfigData: Option[String] = None,
	  /** URI of the plugin configuration stored in the Artifact Registry. The configuration is provided to the plugin at runtime through the `ON_CONFIGURE` callback. The container image must contain only a single file with the name `plugin.config`. When a new `WasmPluginVersion` resource is created, the digest of the container image is saved in the `plugin_config_digest` field. */
		pluginConfigUri: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. A human-readable description of the resource. */
		description: Option[String] = None,
	  /** Optional. Set of labels associated with the `WasmPluginVersion` resource. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. URI of the container image containing the Wasm module, stored in the Artifact Registry. The container image must contain only a single file with the name `plugin.wasm`. When a new `WasmPluginVersion` resource is created, the URI gets resolved to an image digest and saved in the `image_digest` field. */
		imageUri: Option[String] = None,
	  /** Output only. The resolved digest for the image specified in `image`. The digest is resolved during the creation of a `WasmPluginVersion` resource. This field holds the digest value regardless of whether a tag or digest was originally specified in the `image` field. */
		imageDigest: Option[String] = None,
	  /** Output only. This field holds the digest (usually checksum) value for the plugin configuration. The value is calculated based on the contents of the `plugin_config_data` field or the container image defined by the `plugin_config_uri` field. */
		pluginConfigDigest: Option[String] = None
	)
	
	case class WasmPluginUsedBy(
	  /** Output only. Full name of the resource https://google.aip.dev/122#full-resource-names, for example `//networkservices.googleapis.com/projects/{project}/locations/{location}/lbRouteExtensions/{extension}` */
		name: Option[String] = None
	)
	
	case class ListGatewaysResponse(
	  /** List of Gateway resources. */
		gateways: Option[List[Schema.Gateway]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Gateway {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, OPEN_MESH, SECURE_WEB_GATEWAY }
		enum IpVersionEnum extends Enum[IpVersionEnum] { case IP_VERSION_UNSPECIFIED, IPV4, IPV6 }
		enum EnvoyHeadersEnum extends Enum[EnvoyHeadersEnum] { case ENVOY_HEADERS_UNSPECIFIED, NONE, DEBUG_HEADERS }
		enum RoutingModeEnum extends Enum[RoutingModeEnum] { case EXPLICIT_ROUTING_MODE, NEXT_HOP_ROUTING_MODE }
	}
	case class Gateway(
	  /** Identifier. Name of the Gateway resource. It matches pattern `projects/&#42;/locations/&#42;/gateways/`. */
		name: Option[String] = None,
	  /** Output only. Server-defined URL of this resource */
		selfLink: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. Set of label tags associated with the Gateway resource. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. A free-text description of the resource. Max length 1024 characters. */
		description: Option[String] = None,
	  /** Immutable. The type of the customer managed gateway. This field is required. If unspecified, an error is returned. */
		`type`: Option[Schema.Gateway.TypeEnum] = None,
	  /** Optional. Zero or one IPv4 or IPv6 address on which the Gateway will receive the traffic. When no address is provided, an IP from the subnetwork is allocated This field only applies to gateways of type 'SECURE_WEB_GATEWAY'. Gateways of type 'OPEN_MESH' listen on 0.0.0.0 for IPv4 and :: for IPv6. */
		addresses: Option[List[String]] = None,
	  /** Required. One or more port numbers (1-65535), on which the Gateway will receive traffic. The proxy binds to the specified ports. Gateways of type 'SECURE_WEB_GATEWAY' are limited to 1 port. Gateways of type 'OPEN_MESH' listen on 0.0.0.0 for IPv4 and :: for IPv6 and support multiple ports. */
		ports: Option[List[Int]] = None,
	  /** Optional. Scope determines how configuration across multiple Gateway instances are merged. The configuration for multiple Gateway instances with the same scope will be merged as presented as a single coniguration to the proxy/load balancer. Max length 64 characters. Scope should start with a letter and can only have letters, numbers, hyphens. */
		scope: Option[String] = None,
	  /** Optional. A fully-qualified ServerTLSPolicy URL reference. Specifies how TLS traffic is terminated. If empty, TLS termination is disabled. */
		serverTlsPolicy: Option[String] = None,
	  /** Optional. A fully-qualified Certificates URL reference. The proxy presents a Certificate (selected based on SNI) when establishing a TLS connection. This feature only applies to gateways of type 'SECURE_WEB_GATEWAY'. */
		certificateUrls: Option[List[String]] = None,
	  /** Optional. A fully-qualified GatewaySecurityPolicy URL reference. Defines how a server should apply security policy to inbound (VM to Proxy) initiated connections. For example: `projects/&#42;/locations/&#42;/gatewaySecurityPolicies/swg-policy`. This policy is specific to gateways of type 'SECURE_WEB_GATEWAY'. */
		gatewaySecurityPolicy: Option[String] = None,
	  /** Optional. The relative resource name identifying the VPC network that is using this configuration. For example: `projects/&#42;/global/networks/network-1`. Currently, this field is specific to gateways of type 'SECURE_WEB_GATEWAY'. */
		network: Option[String] = None,
	  /** Optional. The relative resource name identifying the subnetwork in which this SWG is allocated. For example: `projects/&#42;/regions/us-central1/subnetworks/network-1` Currently, this field is specific to gateways of type 'SECURE_WEB_GATEWAY". */
		subnetwork: Option[String] = None,
	  /** Optional. The IP Version that will be used by this gateway. Valid options are IPV4 or IPV6. Default is IPV4. */
		ipVersion: Option[Schema.Gateway.IpVersionEnum] = None,
	  /** Optional. Determines if envoy will insert internal debug headers into upstream requests. Other Envoy headers may still be injected. By default, envoy will not insert any debug headers. */
		envoyHeaders: Option[Schema.Gateway.EnvoyHeadersEnum] = None,
	  /** Optional. The routing mode of the Gateway. This field is configurable only for gateways of type SECURE_WEB_GATEWAY. This field is required for gateways of type SECURE_WEB_GATEWAY. */
		routingMode: Option[Schema.Gateway.RoutingModeEnum] = None
	)
	
	case class ListGrpcRoutesResponse(
	  /** List of GrpcRoute resources. */
		grpcRoutes: Option[List[Schema.GrpcRoute]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None
	)
	
	case class GrpcRoute(
	  /** Identifier. Name of the GrpcRoute resource. It matches pattern `projects/&#42;/locations/global/grpcRoutes/` */
		name: Option[String] = None,
	  /** Output only. Server-defined URL of this resource */
		selfLink: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. Set of label tags associated with the GrpcRoute resource. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. A free-text description of the resource. Max length 1024 characters. */
		description: Option[String] = None,
	  /** Required. Service hostnames with an optional port for which this route describes traffic. Format: [:] Hostname is the fully qualified domain name of a network host. This matches the RFC 1123 definition of a hostname with 2 notable exceptions: - IPs are not allowed. - A hostname may be prefixed with a wildcard label (`&#42;.`). The wildcard label must appear by itself as the first label. Hostname can be "precise" which is a domain name without the terminating dot of a network host (e.g. `foo.example.com`) or "wildcard", which is a domain name prefixed with a single wildcard label (e.g. `&#42;.example.com`). Note that as per RFC1035 and RFC1123, a label must consist of lower case alphanumeric characters or '-', and must start and end with an alphanumeric character. No other punctuation is allowed. The routes associated with a Mesh or Gateway must have unique hostnames. If you attempt to attach multiple routes with conflicting hostnames, the configuration will be rejected. For example, while it is acceptable for routes for the hostnames `&#42;.foo.bar.com` and `&#42;.bar.com` to be associated with the same route, it is not possible to associate two routes both with `&#42;.bar.com` or both with `bar.com`. If a port is specified, then gRPC clients must use the channel URI with the port to match this rule (i.e. "xds:///service:123"), otherwise they must supply the URI without a port (i.e. "xds:///service"). */
		hostnames: Option[List[String]] = None,
	  /** Optional. Meshes defines a list of meshes this GrpcRoute is attached to, as one of the routing rules to route the requests served by the mesh. Each mesh reference should match the pattern: `projects/&#42;/locations/global/meshes/` */
		meshes: Option[List[String]] = None,
	  /** Optional. Gateways defines a list of gateways this GrpcRoute is attached to, as one of the routing rules to route the requests served by the gateway. Each gateway reference should match the pattern: `projects/&#42;/locations/global/gateways/` */
		gateways: Option[List[String]] = None,
	  /** Required. A list of detailed rules defining how to route traffic. Within a single GrpcRoute, the GrpcRoute.RouteAction associated with the first matching GrpcRoute.RouteRule will be executed. At least one rule must be supplied. */
		rules: Option[List[Schema.GrpcRouteRouteRule]] = None
	)
	
	case class GrpcRouteRouteRule(
	  /** Optional. Matches define conditions used for matching the rule against incoming gRPC requests. Each match is independent, i.e. this rule will be matched if ANY one of the matches is satisfied. If no matches field is specified, this rule will unconditionally match traffic. */
		matches: Option[List[Schema.GrpcRouteRouteMatch]] = None,
	  /** Required. A detailed rule defining how to route traffic. This field is required. */
		action: Option[Schema.GrpcRouteRouteAction] = None
	)
	
	case class GrpcRouteRouteMatch(
	  /** Optional. A gRPC method to match against. If this field is empty or omitted, will match all methods. */
		method: Option[Schema.GrpcRouteMethodMatch] = None,
	  /** Optional. Specifies a collection of headers to match. */
		headers: Option[List[Schema.GrpcRouteHeaderMatch]] = None
	)
	
	object GrpcRouteMethodMatch {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, EXACT, REGULAR_EXPRESSION }
	}
	case class GrpcRouteMethodMatch(
	  /** Optional. Specifies how to match against the name. If not specified, a default value of "EXACT" is used. */
		`type`: Option[Schema.GrpcRouteMethodMatch.TypeEnum] = None,
	  /** Required. Name of the service to match against. If unspecified, will match all services. */
		grpcService: Option[String] = None,
	  /** Required. Name of the method to match against. If unspecified, will match all methods. */
		grpcMethod: Option[String] = None,
	  /** Optional. Specifies that matches are case sensitive. The default value is true. case_sensitive must not be used with a type of REGULAR_EXPRESSION. */
		caseSensitive: Option[Boolean] = None
	)
	
	object GrpcRouteHeaderMatch {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, EXACT, REGULAR_EXPRESSION }
	}
	case class GrpcRouteHeaderMatch(
	  /** Optional. Specifies how to match against the value of the header. If not specified, a default value of EXACT is used. */
		`type`: Option[Schema.GrpcRouteHeaderMatch.TypeEnum] = None,
	  /** Required. The key of the header. */
		key: Option[String] = None,
	  /** Required. The value of the header. */
		value: Option[String] = None
	)
	
	case class GrpcRouteRouteAction(
	  /** Optional. The destination services to which traffic should be forwarded. If multiple destinations are specified, traffic will be split between Backend Service(s) according to the weight field of these destinations. */
		destinations: Option[List[Schema.GrpcRouteDestination]] = None,
	  /** Optional. The specification for fault injection introduced into traffic to test the resiliency of clients to destination service failure. As part of fault injection, when clients send requests to a destination, delays can be introduced on a percentage of requests before sending those requests to the destination service. Similarly requests from clients can be aborted by for a percentage of requests. timeout and retry_policy will be ignored by clients that are configured with a fault_injection_policy */
		faultInjectionPolicy: Option[Schema.GrpcRouteFaultInjectionPolicy] = None,
	  /** Optional. Specifies the timeout for selected route. Timeout is computed from the time the request has been fully processed (i.e. end of stream) up until the response has been completely processed. Timeout includes all retries. */
		timeout: Option[String] = None,
	  /** Optional. Specifies the retry policy associated with this route. */
		retryPolicy: Option[Schema.GrpcRouteRetryPolicy] = None,
	  /** Optional. Specifies cookie-based stateful session affinity. */
		statefulSessionAffinity: Option[Schema.GrpcRouteStatefulSessionAffinityPolicy] = None,
	  /** Optional. Specifies the idle timeout for the selected route. The idle timeout is defined as the period in which there are no bytes sent or received on either the upstream or downstream connection. If not set, the default idle timeout is 1 hour. If set to 0s, the timeout will be disabled. */
		idleTimeout: Option[String] = None
	)
	
	case class GrpcRouteDestination(
	  /** Required. The URL of a destination service to which to route traffic. Must refer to either a BackendService or ServiceDirectoryService. */
		serviceName: Option[String] = None,
	  /** Optional. Specifies the proportion of requests forwarded to the backend referenced by the serviceName field. This is computed as: - weight/Sum(weights in this destination list). For non-zero values, there may be some epsilon from the exact proportion defined here depending on the precision an implementation supports. If only one serviceName is specified and it has a weight greater than 0, 100% of the traffic is forwarded to that backend. If weights are specified for any one service name, they need to be specified for all of them. If weights are unspecified for all services, then, traffic is distributed in equal proportions to all of them. */
		weight: Option[Int] = None
	)
	
	case class GrpcRouteFaultInjectionPolicy(
	  /** The specification for injecting delay to client requests. */
		delay: Option[Schema.GrpcRouteFaultInjectionPolicyDelay] = None,
	  /** The specification for aborting to client requests. */
		abort: Option[Schema.GrpcRouteFaultInjectionPolicyAbort] = None
	)
	
	case class GrpcRouteFaultInjectionPolicyDelay(
	  /** Specify a fixed delay before forwarding the request. */
		fixedDelay: Option[String] = None,
	  /** The percentage of traffic on which delay will be injected. The value must be between [0, 100] */
		percentage: Option[Int] = None
	)
	
	case class GrpcRouteFaultInjectionPolicyAbort(
	  /** The HTTP status code used to abort the request. The value must be between 200 and 599 inclusive. */
		httpStatus: Option[Int] = None,
	  /** The percentage of traffic which will be aborted. The value must be between [0, 100] */
		percentage: Option[Int] = None
	)
	
	case class GrpcRouteRetryPolicy(
	  /** - connect-failure: Router will retry on failures connecting to Backend Services, for example due to connection timeouts. - refused-stream: Router will retry if the backend service resets the stream with a REFUSED_STREAM error code. This reset type indicates that it is safe to retry. - cancelled: Router will retry if the gRPC status code in the response header is set to cancelled - deadline-exceeded: Router will retry if the gRPC status code in the response header is set to deadline-exceeded - resource-exhausted: Router will retry if the gRPC status code in the response header is set to resource-exhausted - unavailable: Router will retry if the gRPC status code in the response header is set to unavailable */
		retryConditions: Option[List[String]] = None,
	  /** Specifies the allowed number of retries. This number must be > 0. If not specified, default to 1. */
		numRetries: Option[Int] = None
	)
	
	case class GrpcRouteStatefulSessionAffinityPolicy(
	  /** Required. The cookie TTL value for the Set-Cookie header generated by the data plane. The lifetime of the cookie may be set to a value from 1 to 86400 seconds (24 hours) inclusive. */
		cookieTtl: Option[String] = None
	)
	
	case class ListHttpRoutesResponse(
	  /** List of HttpRoute resources. */
		httpRoutes: Option[List[Schema.HttpRoute]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None
	)
	
	case class HttpRoute(
	  /** Identifier. Name of the HttpRoute resource. It matches pattern `projects/&#42;/locations/global/httpRoutes/http_route_name>`. */
		name: Option[String] = None,
	  /** Output only. Server-defined URL of this resource */
		selfLink: Option[String] = None,
	  /** Optional. A free-text description of the resource. Max length 1024 characters. */
		description: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Required. Hostnames define a set of hosts that should match against the HTTP host header to select a HttpRoute to process the request. Hostname is the fully qualified domain name of a network host, as defined by RFC 1123 with the exception that: - IPs are not allowed. - A hostname may be prefixed with a wildcard label (`&#42;.`). The wildcard label must appear by itself as the first label. Hostname can be "precise" which is a domain name without the terminating dot of a network host (e.g. `foo.example.com`) or "wildcard", which is a domain name prefixed with a single wildcard label (e.g. `&#42;.example.com`). Note that as per RFC1035 and RFC1123, a label must consist of lower case alphanumeric characters or '-', and must start and end with an alphanumeric character. No other punctuation is allowed. The routes associated with a Mesh or Gateways must have unique hostnames. If you attempt to attach multiple routes with conflicting hostnames, the configuration will be rejected. For example, while it is acceptable for routes for the hostnames `&#42;.foo.bar.com` and `&#42;.bar.com` to be associated with the same Mesh (or Gateways under the same scope), it is not possible to associate two routes both with `&#42;.bar.com` or both with `bar.com`. */
		hostnames: Option[List[String]] = None,
	  /** Optional. Meshes defines a list of meshes this HttpRoute is attached to, as one of the routing rules to route the requests served by the mesh. Each mesh reference should match the pattern: `projects/&#42;/locations/global/meshes/` The attached Mesh should be of a type SIDECAR */
		meshes: Option[List[String]] = None,
	  /** Optional. Gateways defines a list of gateways this HttpRoute is attached to, as one of the routing rules to route the requests served by the gateway. Each gateway reference should match the pattern: `projects/&#42;/locations/global/gateways/` */
		gateways: Option[List[String]] = None,
	  /** Optional. Set of label tags associated with the HttpRoute resource. */
		labels: Option[Map[String, String]] = None,
	  /** Required. Rules that define how traffic is routed and handled. Rules will be matched sequentially based on the RouteMatch specified for the rule. */
		rules: Option[List[Schema.HttpRouteRouteRule]] = None
	)
	
	case class HttpRouteRouteRule(
	  /** A list of matches define conditions used for matching the rule against incoming HTTP requests. Each match is independent, i.e. this rule will be matched if ANY one of the matches is satisfied. If no matches field is specified, this rule will unconditionally match traffic. If a default rule is desired to be configured, add a rule with no matches specified to the end of the rules list. */
		matches: Option[List[Schema.HttpRouteRouteMatch]] = None,
	  /** The detailed rule defining how to route matched traffic. */
		action: Option[Schema.HttpRouteRouteAction] = None
	)
	
	case class HttpRouteRouteMatch(
	  /** The HTTP request path value should exactly match this value. Only one of full_path_match, prefix_match, or regex_match should be used. */
		fullPathMatch: Option[String] = None,
	  /** The HTTP request path value must begin with specified prefix_match. prefix_match must begin with a /. Only one of full_path_match, prefix_match, or regex_match should be used. */
		prefixMatch: Option[String] = None,
	  /** The HTTP request path value must satisfy the regular expression specified by regex_match after removing any query parameters and anchor supplied with the original URL. For regular expression grammar, please see https://github.com/google/re2/wiki/Syntax Only one of full_path_match, prefix_match, or regex_match should be used. */
		regexMatch: Option[String] = None,
	  /** Specifies if prefix_match and full_path_match matches are case sensitive. The default value is false. */
		ignoreCase: Option[Boolean] = None,
	  /** Specifies a list of HTTP request headers to match against. ALL of the supplied headers must be matched. */
		headers: Option[List[Schema.HttpRouteHeaderMatch]] = None,
	  /** Specifies a list of query parameters to match against. ALL of the query parameters must be matched. */
		queryParameters: Option[List[Schema.HttpRouteQueryParameterMatch]] = None
	)
	
	case class HttpRouteHeaderMatch(
	  /** The value of the header should match exactly the content of exact_match. */
		exactMatch: Option[String] = None,
	  /** The value of the header must match the regular expression specified in regex_match. For regular expression grammar, please see: https://github.com/google/re2/wiki/Syntax */
		regexMatch: Option[String] = None,
	  /** The value of the header must start with the contents of prefix_match. */
		prefixMatch: Option[String] = None,
	  /** A header with header_name must exist. The match takes place whether or not the header has a value. */
		presentMatch: Option[Boolean] = None,
	  /** The value of the header must end with the contents of suffix_match. */
		suffixMatch: Option[String] = None,
	  /** If specified, the rule will match if the request header value is within the range. */
		rangeMatch: Option[Schema.HttpRouteHeaderMatchIntegerRange] = None,
	  /** The name of the HTTP header to match against. */
		header: Option[String] = None,
	  /** If specified, the match result will be inverted before checking. Default value is set to false. */
		invertMatch: Option[Boolean] = None
	)
	
	case class HttpRouteHeaderMatchIntegerRange(
	  /** Start of the range (inclusive) */
		start: Option[Int] = None,
	  /** End of the range (exclusive) */
		end: Option[Int] = None
	)
	
	case class HttpRouteQueryParameterMatch(
	  /** The value of the query parameter must exactly match the contents of exact_match. Only one of exact_match, regex_match, or present_match must be set. */
		exactMatch: Option[String] = None,
	  /** The value of the query parameter must match the regular expression specified by regex_match. For regular expression grammar, please see https://github.com/google/re2/wiki/Syntax Only one of exact_match, regex_match, or present_match must be set. */
		regexMatch: Option[String] = None,
	  /** Specifies that the QueryParameterMatcher matches if request contains query parameter, irrespective of whether the parameter has a value or not. Only one of exact_match, regex_match, or present_match must be set. */
		presentMatch: Option[Boolean] = None,
	  /** The name of the query parameter to match. */
		queryParameter: Option[String] = None
	)
	
	case class HttpRouteRouteAction(
	  /** The destination to which traffic should be forwarded. */
		destinations: Option[List[Schema.HttpRouteDestination]] = None,
	  /** If set, the request is directed as configured by this field. */
		redirect: Option[Schema.HttpRouteRedirect] = None,
	  /** The specification for fault injection introduced into traffic to test the resiliency of clients to backend service failure. As part of fault injection, when clients send requests to a backend service, delays can be introduced on a percentage of requests before sending those requests to the backend service. Similarly requests from clients can be aborted for a percentage of requests. timeout and retry_policy will be ignored by clients that are configured with a fault_injection_policy */
		faultInjectionPolicy: Option[Schema.HttpRouteFaultInjectionPolicy] = None,
	  /** The specification for modifying the headers of a matching request prior to delivery of the request to the destination. If HeaderModifiers are set on both the Destination and the RouteAction, they will be merged. Conflicts between the two will not be resolved on the configuration. */
		requestHeaderModifier: Option[Schema.HttpRouteHeaderModifier] = None,
	  /** The specification for modifying the headers of a response prior to sending the response back to the client. If HeaderModifiers are set on both the Destination and the RouteAction, they will be merged. Conflicts between the two will not be resolved on the configuration. */
		responseHeaderModifier: Option[Schema.HttpRouteHeaderModifier] = None,
	  /** The specification for rewrite URL before forwarding requests to the destination. */
		urlRewrite: Option[Schema.HttpRouteURLRewrite] = None,
	  /** Specifies the timeout for selected route. Timeout is computed from the time the request has been fully processed (i.e. end of stream) up until the response has been completely processed. Timeout includes all retries. */
		timeout: Option[String] = None,
	  /** Specifies the retry policy associated with this route. */
		retryPolicy: Option[Schema.HttpRouteRetryPolicy] = None,
	  /** Specifies the policy on how requests intended for the routes destination are shadowed to a separate mirrored destination. Proxy will not wait for the shadow destination to respond before returning the response. Prior to sending traffic to the shadow service, the host/authority header is suffixed with -shadow. */
		requestMirrorPolicy: Option[Schema.HttpRouteRequestMirrorPolicy] = None,
	  /** The specification for allowing client side cross-origin requests. */
		corsPolicy: Option[Schema.HttpRouteCorsPolicy] = None,
	  /** Optional. Specifies cookie-based stateful session affinity. */
		statefulSessionAffinity: Option[Schema.HttpRouteStatefulSessionAffinityPolicy] = None,
	  /** Optional. Static HTTP Response object to be returned regardless of the request. */
		directResponse: Option[Schema.HttpRouteHttpDirectResponse] = None,
	  /** Optional. Specifies the idle timeout for the selected route. The idle timeout is defined as the period in which there are no bytes sent or received on either the upstream or downstream connection. If not set, the default idle timeout is 1 hour. If set to 0s, the timeout will be disabled. */
		idleTimeout: Option[String] = None
	)
	
	case class HttpRouteDestination(
	  /** The URL of a BackendService to route traffic to. */
		serviceName: Option[String] = None,
	  /** Specifies the proportion of requests forwarded to the backend referenced by the serviceName field. This is computed as: - weight/Sum(weights in this destination list). For non-zero values, there may be some epsilon from the exact proportion defined here depending on the precision an implementation supports. If only one serviceName is specified and it has a weight greater than 0, 100% of the traffic is forwarded to that backend. If weights are specified for any one service name, they need to be specified for all of them. If weights are unspecified for all services, then, traffic is distributed in equal proportions to all of them. */
		weight: Option[Int] = None,
	  /** Optional. The specification for modifying the headers of a matching request prior to delivery of the request to the destination. If HeaderModifiers are set on both the Destination and the RouteAction, they will be merged. Conflicts between the two will not be resolved on the configuration. */
		requestHeaderModifier: Option[Schema.HttpRouteHeaderModifier] = None,
	  /** Optional. The specification for modifying the headers of a response prior to sending the response back to the client. If HeaderModifiers are set on both the Destination and the RouteAction, they will be merged. Conflicts between the two will not be resolved on the configuration. */
		responseHeaderModifier: Option[Schema.HttpRouteHeaderModifier] = None
	)
	
	case class HttpRouteHeaderModifier(
	  /** Completely overwrite/replace the headers with given map where key is the name of the header, value is the value of the header. */
		set: Option[Map[String, String]] = None,
	  /** Add the headers with given map where key is the name of the header, value is the value of the header. */
		add: Option[Map[String, String]] = None,
	  /** Remove headers (matching by header names) specified in the list. */
		remove: Option[List[String]] = None
	)
	
	object HttpRouteRedirect {
		enum ResponseCodeEnum extends Enum[ResponseCodeEnum] { case RESPONSE_CODE_UNSPECIFIED, MOVED_PERMANENTLY_DEFAULT, FOUND, SEE_OTHER, TEMPORARY_REDIRECT, PERMANENT_REDIRECT }
	}
	case class HttpRouteRedirect(
	  /** The host that will be used in the redirect response instead of the one that was supplied in the request. */
		hostRedirect: Option[String] = None,
	  /** The path that will be used in the redirect response instead of the one that was supplied in the request. path_redirect can not be supplied together with prefix_redirect. Supply one alone or neither. If neither is supplied, the path of the original request will be used for the redirect. */
		pathRedirect: Option[String] = None,
	  /** Indicates that during redirection, the matched prefix (or path) should be swapped with this value. This option allows URLs be dynamically created based on the request. */
		prefixRewrite: Option[String] = None,
	  /** The HTTP Status code to use for the redirect. */
		responseCode: Option[Schema.HttpRouteRedirect.ResponseCodeEnum] = None,
	  /** If set to true, the URL scheme in the redirected request is set to https. If set to false, the URL scheme of the redirected request will remain the same as that of the request. The default is set to false. */
		httpsRedirect: Option[Boolean] = None,
	  /** if set to true, any accompanying query portion of the original URL is removed prior to redirecting the request. If set to false, the query portion of the original URL is retained. The default is set to false. */
		stripQuery: Option[Boolean] = None,
	  /** The port that will be used in the redirected request instead of the one that was supplied in the request. */
		portRedirect: Option[Int] = None
	)
	
	case class HttpRouteFaultInjectionPolicy(
	  /** The specification for injecting delay to client requests. */
		delay: Option[Schema.HttpRouteFaultInjectionPolicyDelay] = None,
	  /** The specification for aborting to client requests. */
		abort: Option[Schema.HttpRouteFaultInjectionPolicyAbort] = None
	)
	
	case class HttpRouteFaultInjectionPolicyDelay(
	  /** Specify a fixed delay before forwarding the request. */
		fixedDelay: Option[String] = None,
	  /** The percentage of traffic on which delay will be injected. The value must be between [0, 100] */
		percentage: Option[Int] = None
	)
	
	case class HttpRouteFaultInjectionPolicyAbort(
	  /** The HTTP status code used to abort the request. The value must be between 200 and 599 inclusive. */
		httpStatus: Option[Int] = None,
	  /** The percentage of traffic which will be aborted. The value must be between [0, 100] */
		percentage: Option[Int] = None
	)
	
	case class HttpRouteURLRewrite(
	  /** Prior to forwarding the request to the selected destination, the matching portion of the requests path is replaced by this value. */
		pathPrefixRewrite: Option[String] = None,
	  /** Prior to forwarding the request to the selected destination, the requests host header is replaced by this value. */
		hostRewrite: Option[String] = None
	)
	
	case class HttpRouteRetryPolicy(
	  /** Specifies one or more conditions when this retry policy applies. Valid values are: 5xx: Proxy will attempt a retry if the destination service responds with any 5xx response code, of if the destination service does not respond at all, example: disconnect, reset, read timeout, connection failure and refused streams. gateway-error: Similar to 5xx, but only applies to response codes 502, 503, 504. reset: Proxy will attempt a retry if the destination service does not respond at all (disconnect/reset/read timeout) connect-failure: Proxy will retry on failures connecting to destination for example due to connection timeouts. retriable-4xx: Proxy will retry fro retriable 4xx response codes. Currently the only retriable error supported is 409. refused-stream: Proxy will retry if the destination resets the stream with a REFUSED_STREAM error code. This reset type indicates that it is safe to retry. */
		retryConditions: Option[List[String]] = None,
	  /** Specifies the allowed number of retries. This number must be > 0. If not specified, default to 1. */
		numRetries: Option[Int] = None,
	  /** Specifies a non-zero timeout per retry attempt. */
		perTryTimeout: Option[String] = None
	)
	
	case class HttpRouteRequestMirrorPolicy(
	  /** The destination the requests will be mirrored to. The weight of the destination will be ignored. */
		destination: Option[Schema.HttpRouteDestination] = None,
	  /** Optional. The percentage of requests to get mirrored to the desired destination. */
		mirrorPercent: Option[BigDecimal] = None
	)
	
	case class HttpRouteCorsPolicy(
	  /** Specifies the list of origins that will be allowed to do CORS requests. An origin is allowed if it matches either an item in allow_origins or an item in allow_origin_regexes. */
		allowOrigins: Option[List[String]] = None,
	  /** Specifies the regular expression patterns that match allowed origins. For regular expression grammar, please see https://github.com/google/re2/wiki/Syntax. */
		allowOriginRegexes: Option[List[String]] = None,
	  /** Specifies the content for Access-Control-Allow-Methods header. */
		allowMethods: Option[List[String]] = None,
	  /** Specifies the content for Access-Control-Allow-Headers header. */
		allowHeaders: Option[List[String]] = None,
	  /** Specifies the content for Access-Control-Expose-Headers header. */
		exposeHeaders: Option[List[String]] = None,
	  /** Specifies how long result of a preflight request can be cached in seconds. This translates to the Access-Control-Max-Age header. */
		maxAge: Option[String] = None,
	  /** In response to a preflight request, setting this to true indicates that the actual request can include user credentials. This translates to the Access-Control-Allow-Credentials header. Default value is false. */
		allowCredentials: Option[Boolean] = None,
	  /** If true, the CORS policy is disabled. The default value is false, which indicates that the CORS policy is in effect. */
		disabled: Option[Boolean] = None
	)
	
	case class HttpRouteStatefulSessionAffinityPolicy(
	  /** Required. The cookie TTL value for the Set-Cookie header generated by the data plane. The lifetime of the cookie may be set to a value from 1 to 86400 seconds (24 hours) inclusive. */
		cookieTtl: Option[String] = None
	)
	
	case class HttpRouteHttpDirectResponse(
	  /** Optional. Response body as a string. Maximum body length is 1024 characters. */
		stringBody: Option[String] = None,
	  /** Optional. Response body as bytes. Maximum body size is 4096B. */
		bytesBody: Option[String] = None,
	  /** Required. Status to return as part of HTTP Response. Must be a positive integer. */
		status: Option[Int] = None
	)
	
	case class ListTcpRoutesResponse(
	  /** List of TcpRoute resources. */
		tcpRoutes: Option[List[Schema.TcpRoute]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None
	)
	
	case class TcpRoute(
	  /** Identifier. Name of the TcpRoute resource. It matches pattern `projects/&#42;/locations/global/tcpRoutes/tcp_route_name>`. */
		name: Option[String] = None,
	  /** Output only. Server-defined URL of this resource */
		selfLink: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. A free-text description of the resource. Max length 1024 characters. */
		description: Option[String] = None,
	  /** Required. Rules that define how traffic is routed and handled. At least one RouteRule must be supplied. If there are multiple rules then the action taken will be the first rule to match. */
		rules: Option[List[Schema.TcpRouteRouteRule]] = None,
	  /** Optional. Meshes defines a list of meshes this TcpRoute is attached to, as one of the routing rules to route the requests served by the mesh. Each mesh reference should match the pattern: `projects/&#42;/locations/global/meshes/` The attached Mesh should be of a type SIDECAR */
		meshes: Option[List[String]] = None,
	  /** Optional. Gateways defines a list of gateways this TcpRoute is attached to, as one of the routing rules to route the requests served by the gateway. Each gateway reference should match the pattern: `projects/&#42;/locations/global/gateways/` */
		gateways: Option[List[String]] = None,
	  /** Optional. Set of label tags associated with the TcpRoute resource. */
		labels: Option[Map[String, String]] = None
	)
	
	case class TcpRouteRouteRule(
	  /** Optional. RouteMatch defines the predicate used to match requests to a given action. Multiple match types are "OR"ed for evaluation. If no routeMatch field is specified, this rule will unconditionally match traffic. */
		matches: Option[List[Schema.TcpRouteRouteMatch]] = None,
	  /** Required. The detailed rule defining how to route matched traffic. */
		action: Option[Schema.TcpRouteRouteAction] = None
	)
	
	case class TcpRouteRouteMatch(
	  /** Required. Must be specified in the CIDR range format. A CIDR range consists of an IP Address and a prefix length to construct the subnet mask. By default, the prefix length is 32 (i.e. matches a single IP address). Only IPV4 addresses are supported. Examples: "10.0.0.1" - matches against this exact IP address. "10.0.0.0/8" - matches against any IP address within the 10.0.0.0 subnet and 255.255.255.0 mask. "0.0.0.0/0" - matches against any IP address'. */
		address: Option[String] = None,
	  /** Required. Specifies the destination port to match against. */
		port: Option[String] = None
	)
	
	case class TcpRouteRouteAction(
	  /** Optional. The destination services to which traffic should be forwarded. At least one destination service is required. Only one of route destination or original destination can be set. */
		destinations: Option[List[Schema.TcpRouteRouteDestination]] = None,
	  /** Optional. If true, Router will use the destination IP and port of the original connection as the destination of the request. Default is false. Only one of route destinations or original destination can be set. */
		originalDestination: Option[Boolean] = None,
	  /** Optional. Specifies the idle timeout for the selected route. The idle timeout is defined as the period in which there are no bytes sent or received on either the upstream or downstream connection. If not set, the default idle timeout is 30 seconds. If set to 0s, the timeout will be disabled. */
		idleTimeout: Option[String] = None
	)
	
	case class TcpRouteRouteDestination(
	  /** Required. The URL of a BackendService to route traffic to. */
		serviceName: Option[String] = None,
	  /** Optional. Specifies the proportion of requests forwarded to the backend referenced by the serviceName field. This is computed as: - weight/Sum(weights in this destination list). For non-zero values, there may be some epsilon from the exact proportion defined here depending on the precision an implementation supports. If only one serviceName is specified and it has a weight greater than 0, 100% of the traffic is forwarded to that backend. If weights are specified for any one service name, they need to be specified for all of them. If weights are unspecified for all services, then, traffic is distributed in equal proportions to all of them. */
		weight: Option[Int] = None
	)
	
	case class ListTlsRoutesResponse(
	  /** List of TlsRoute resources. */
		tlsRoutes: Option[List[Schema.TlsRoute]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None
	)
	
	case class TlsRoute(
	  /** Identifier. Name of the TlsRoute resource. It matches pattern `projects/&#42;/locations/global/tlsRoutes/tls_route_name>`. */
		name: Option[String] = None,
	  /** Output only. Server-defined URL of this resource */
		selfLink: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. A free-text description of the resource. Max length 1024 characters. */
		description: Option[String] = None,
	  /** Required. Rules that define how traffic is routed and handled. At least one RouteRule must be supplied. If there are multiple rules then the action taken will be the first rule to match. */
		rules: Option[List[Schema.TlsRouteRouteRule]] = None,
	  /** Optional. Meshes defines a list of meshes this TlsRoute is attached to, as one of the routing rules to route the requests served by the mesh. Each mesh reference should match the pattern: `projects/&#42;/locations/global/meshes/` The attached Mesh should be of a type SIDECAR */
		meshes: Option[List[String]] = None,
	  /** Optional. Gateways defines a list of gateways this TlsRoute is attached to, as one of the routing rules to route the requests served by the gateway. Each gateway reference should match the pattern: `projects/&#42;/locations/global/gateways/` */
		gateways: Option[List[String]] = None,
	  /** Optional. Set of label tags associated with the TlsRoute resource. */
		labels: Option[Map[String, String]] = None
	)
	
	case class TlsRouteRouteRule(
	  /** Required. RouteMatch defines the predicate used to match requests to a given action. Multiple match types are "OR"ed for evaluation. */
		matches: Option[List[Schema.TlsRouteRouteMatch]] = None,
	  /** Required. The detailed rule defining how to route matched traffic. */
		action: Option[Schema.TlsRouteRouteAction] = None
	)
	
	case class TlsRouteRouteMatch(
	  /** Optional. SNI (server name indicator) to match against. SNI will be matched against all wildcard domains, i.e. `www.example.com` will be first matched against `www.example.com`, then `&#42;.example.com`, then `&#42;.com.` Partial wildcards are not supported, and values like &#42;w.example.com are invalid. At least one of sni_host and alpn is required. Up to 100 sni hosts across all matches can be set. */
		sniHost: Option[List[String]] = None,
	  /** Optional. ALPN (Application-Layer Protocol Negotiation) to match against. Examples: "http/1.1", "h2". At least one of sni_host and alpn is required. Up to 5 alpns across all matches can be set. */
		alpn: Option[List[String]] = None
	)
	
	case class TlsRouteRouteAction(
	  /** Required. The destination services to which traffic should be forwarded. At least one destination service is required. */
		destinations: Option[List[Schema.TlsRouteRouteDestination]] = None,
	  /** Optional. Specifies the idle timeout for the selected route. The idle timeout is defined as the period in which there are no bytes sent or received on either the upstream or downstream connection. If not set, the default idle timeout is 1 hour. If set to 0s, the timeout will be disabled. */
		idleTimeout: Option[String] = None
	)
	
	case class TlsRouteRouteDestination(
	  /** Required. The URL of a BackendService to route traffic to. */
		serviceName: Option[String] = None,
	  /** Optional. Specifies the proportion of requests forwareded to the backend referenced by the service_name field. This is computed as: - weight/Sum(weights in destinations) Weights in all destinations does not need to sum up to 100. */
		weight: Option[Int] = None
	)
	
	case class ListServiceBindingsResponse(
	  /** List of ServiceBinding resources. */
		serviceBindings: Option[List[Schema.ServiceBinding]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None
	)
	
	case class ServiceBinding(
	  /** Identifier. Name of the ServiceBinding resource. It matches pattern `projects/&#42;/locations/global/serviceBindings/service_binding_name`. */
		name: Option[String] = None,
	  /** Optional. A free-text description of the resource. Max length 1024 characters. */
		description: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Required. The full Service Directory Service name of the format projects/&#42;/locations/&#42;/namespaces/&#42;/services/&#42; */
		service: Option[String] = None,
	  /** Output only. The unique identifier of the Service Directory Service against which the Service Binding resource is validated. This is populated when the Service Binding resource is used in another resource (like Backend Service). This is of the UUID4 format. */
		serviceId: Option[String] = None,
	  /** Optional. Set of label tags associated with the ServiceBinding resource. */
		labels: Option[Map[String, String]] = None
	)
	
	case class ListMeshesResponse(
	  /** List of Mesh resources. */
		meshes: Option[List[Schema.Mesh]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None
	)
	
	object Mesh {
		enum EnvoyHeadersEnum extends Enum[EnvoyHeadersEnum] { case ENVOY_HEADERS_UNSPECIFIED, NONE, DEBUG_HEADERS }
	}
	case class Mesh(
	  /** Identifier. Name of the Mesh resource. It matches pattern `projects/&#42;/locations/global/meshes/`. */
		name: Option[String] = None,
	  /** Output only. Server-defined URL of this resource */
		selfLink: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. Set of label tags associated with the Mesh resource. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. A free-text description of the resource. Max length 1024 characters. */
		description: Option[String] = None,
	  /** Optional. If set to a valid TCP port (1-65535), instructs the SIDECAR proxy to listen on the specified port of localhost (127.0.0.1) address. The SIDECAR proxy will expect all traffic to be redirected to this port regardless of its actual ip:port destination. If unset, a port '15001' is used as the interception port. This is applicable only for sidecar proxy deployments. */
		interceptionPort: Option[Int] = None,
	  /** Optional. Determines if envoy will insert internal debug headers into upstream requests. Other Envoy headers may still be injected. By default, envoy will not insert any debug headers. */
		envoyHeaders: Option[Schema.Mesh.EnvoyHeadersEnum] = None
	)
	
	case class ListServiceLbPoliciesResponse(
	  /** List of ServiceLbPolicy resources. */
		serviceLbPolicies: Option[List[Schema.ServiceLbPolicy]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None
	)
	
	object ServiceLbPolicy {
		enum LoadBalancingAlgorithmEnum extends Enum[LoadBalancingAlgorithmEnum] { case LOAD_BALANCING_ALGORITHM_UNSPECIFIED, SPRAY_TO_WORLD, SPRAY_TO_REGION, WATERFALL_BY_REGION, WATERFALL_BY_ZONE }
	}
	case class ServiceLbPolicy(
	  /** Identifier. Name of the ServiceLbPolicy resource. It matches pattern `projects/{project}/locations/{location}/serviceLbPolicies/{service_lb_policy_name}`. */
		name: Option[String] = None,
	  /** Output only. The timestamp when this resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when this resource was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. Set of label tags associated with the ServiceLbPolicy resource. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. A free-text description of the resource. Max length 1024 characters. */
		description: Option[String] = None,
	  /** Optional. The type of load balancing algorithm to be used. The default behavior is WATERFALL_BY_REGION. */
		loadBalancingAlgorithm: Option[Schema.ServiceLbPolicy.LoadBalancingAlgorithmEnum] = None,
	  /** Optional. Configuration to automatically move traffic away for unhealthy IG/NEG for the associated Backend Service. */
		autoCapacityDrain: Option[Schema.ServiceLbPolicyAutoCapacityDrain] = None,
	  /** Optional. Configuration related to health based failover. */
		failoverConfig: Option[Schema.ServiceLbPolicyFailoverConfig] = None
	)
	
	case class ServiceLbPolicyAutoCapacityDrain(
	  /** Optional. If set to 'True', an unhealthy IG/NEG will be set as drained. - An IG/NEG is considered unhealthy if less than 25% of the instances/endpoints in the IG/NEG are healthy. - This option will never result in draining more than 50% of the configured IGs/NEGs for the Backend Service. */
		enable: Option[Boolean] = None
	)
	
	case class ServiceLbPolicyFailoverConfig(
	  /** Optional. The percentage threshold that a load balancer will begin to send traffic to failover backends. If the percentage of endpoints in a MIG/NEG is smaller than this value, traffic would be sent to failover backends if possible. This field should be set to a value between 1 and 99. The default value is 50 for Global external HTTP(S) load balancer (classic) and Proxyless service mesh, and 70 for others. */
		failoverHealthThreshold: Option[Int] = None
	)
	
	case class GatewayRouteView(
	  /** Output only. Identifier. Full path name of the GatewayRouteView resource. Format: projects/{project_number}/locations/{location}/gateways/{gateway_name}/routeViews/{route_view_name} */
		name: Option[String] = None,
	  /** Output only. Project number where the route exists. */
		routeProjectNumber: Option[String] = None,
	  /** Output only. Location where the route exists. */
		routeLocation: Option[String] = None,
	  /** Output only. Type of the route: HttpRoute,GrpcRoute,TcpRoute, or TlsRoute */
		routeType: Option[String] = None,
	  /** Output only. The resource id for the route. */
		routeId: Option[String] = None
	)
	
	case class MeshRouteView(
	  /** Output only. Identifier. Full path name of the MeshRouteView resource. Format: projects/{project_number}/locations/{location}/meshes/{mesh_name}/routeViews/{route_view_name} */
		name: Option[String] = None,
	  /** Output only. Project number where the route exists. */
		routeProjectNumber: Option[String] = None,
	  /** Output only. Location where the route exists. */
		routeLocation: Option[String] = None,
	  /** Output only. Type of the route: HttpRoute,GrpcRoute,TcpRoute, or TlsRoute */
		routeType: Option[String] = None,
	  /** Output only. The resource id for the route. */
		routeId: Option[String] = None
	)
	
	case class ListGatewayRouteViewsResponse(
	  /** List of GatewayRouteView resources. */
		gatewayRouteViews: Option[List[Schema.GatewayRouteView]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class ListMeshRouteViewsResponse(
	  /** List of MeshRouteView resources. */
		meshRouteViews: Option[List[Schema.MeshRouteView]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
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
	
	object LoggingConfig {
		enum LogSeverityEnum extends Enum[LogSeverityEnum] { case LOG_SEVERITY_UNSPECIFIED, NONE, DEBUG, INFO, NOTICE, WARNING, ERROR, CRITICAL, ALERT, EMERGENCY }
	}
	case class LoggingConfig(
	  /** Optional. The minimum severity of logs that will be sent to Stackdriver/Platform Telemetry. Logs at severitiy ≥ this value will be sent, unless it is NONE. */
		logSeverity: Option[Schema.LoggingConfig.LogSeverityEnum] = None
	)
	
	case class RetryFilterPerRouteConfig(
	  /** The name of the crypto key to use for encrypting event data. */
		cryptoKeyName: Option[String] = None
	)
}
