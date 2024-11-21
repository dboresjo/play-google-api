package com.boresjo.play.api.google.trafficdirector

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ClientStatusRequest(
	  /** Management server can use these match criteria to identify clients. The match follows OR semantics. */
		nodeMatchers: Option[List[Schema.NodeMatcher]] = None,
	  /** The node making the csds request. */
		node: Option[Schema.Node] = None,
	  /** If true, the server will not include the resource contents in the response (i.e., the generic_xds_configs.xds_config field will not be populated). [#not-implemented-hide:] */
		excludeResourceContents: Option[Boolean] = None
	)
	
	case class NodeMatcher(
	  /** Specifies match criteria on the node id. */
		nodeId: Option[Schema.StringMatcher] = None,
	  /** Specifies match criteria on the node metadata. */
		nodeMetadatas: Option[List[Schema.StructMatcher]] = None
	)
	
	case class StringMatcher(
	  /** The input string must match exactly the string specified here. Examples: &#42; ``abc`` only matches the value ``abc``. */
		exact: Option[String] = None,
	  /** The input string must have the prefix specified here. Note: empty prefix is not allowed, please use regex instead. Examples: &#42; ``abc`` matches the value ``abc.xyz`` */
		prefix: Option[String] = None,
	  /** The input string must have the suffix specified here. Note: empty prefix is not allowed, please use regex instead. Examples: &#42; ``abc`` matches the value ``xyz.abc`` */
		suffix: Option[String] = None,
	  /** The input string must match the regular expression specified here. */
		safeRegex: Option[Schema.RegexMatcher] = None,
	  /** The input string must have the substring specified here. Note: empty contains match is not allowed, please use regex instead. Examples: &#42; ``abc`` matches the value ``xyz.abc.def`` */
		contains: Option[String] = None,
	  /** Use an extension as the matcher type. [#extension-category: envoy.string_matcher] */
		custom: Option[Schema.TypedExtensionConfig] = None,
	  /** If true, indicates the exact/prefix/suffix/contains matching should be case insensitive. This has no effect for the safe_regex match. For example, the matcher ``data`` will match both input string ``Data`` and ``data`` if set to true. */
		ignoreCase: Option[Boolean] = None
	)
	
	case class RegexMatcher(
	  /** Google's RE2 regex engine. */
		googleRe2: Option[Schema.GoogleRE2] = None,
	  /** The regex match string. The string must be supported by the configured engine. The regex is matched against the full string, not as a partial match. */
		regex: Option[String] = None
	)
	
	case class GoogleRE2(
	  /** This field controls the RE2 "program size" which is a rough estimate of how complex a compiled regex is to evaluate. A regex that has a program size greater than the configured value will fail to compile. In this case, the configured max program size can be increased or the regex can be simplified. If not specified, the default is 100. This field is deprecated; regexp validation should be performed on the management server instead of being done by each individual client. .. note:: Although this field is deprecated, the program size will still be checked against the global ``re2.max_program_size.error_level`` runtime value. */
		maxProgramSize: Option[Int] = None
	)
	
	case class TypedExtensionConfig(
	  /** The name of an extension. This is not used to select the extension, instead it serves the role of an opaque identifier. */
		name: Option[String] = None,
	  /** The typed config for the extension. The type URL will be used to identify the extension. In the case that the type URL is &#42;xds.type.v3.TypedStruct&#42; (or, for historical reasons, &#42;udpa.type.v1.TypedStruct&#42;), the inner type URL of &#42;TypedStruct&#42; will be utilized. See the :ref:`extension configuration overview ` for further details. */
		typedConfig: Option[Map[String, JsValue]] = None
	)
	
	case class StructMatcher(
	  /** The path to retrieve the Value from the Struct. */
		path: Option[List[Schema.PathSegment]] = None,
	  /** The StructMatcher is matched if the value retrieved by path is matched to this value. */
		value: Option[Schema.ValueMatcher] = None
	)
	
	case class PathSegment(
	  /** If specified, use the key to retrieve the value in a Struct. */
		key: Option[String] = None
	)
	
	case class ValueMatcher(
	  /** If specified, a match occurs if and only if the target value is a NullValue. */
		nullMatch: Option[Schema.NullMatch] = None,
	  /** If specified, a match occurs if and only if the target value is a double value and is matched to this field. */
		doubleMatch: Option[Schema.DoubleMatcher] = None,
	  /** If specified, a match occurs if and only if the target value is a string value and is matched to this field. */
		stringMatch: Option[Schema.StringMatcher] = None,
	  /** If specified, a match occurs if and only if the target value is a bool value and is equal to this field. */
		boolMatch: Option[Boolean] = None,
	  /** If specified, value match will be performed based on whether the path is referring to a valid primitive value in the metadata. If the path is referring to a non-primitive value, the result is always not matched. */
		presentMatch: Option[Boolean] = None,
	  /** If specified, a match occurs if and only if the target value is a list value and is matched to this field. */
		listMatch: Option[Schema.ListMatcher] = None,
	  /** If specified, a match occurs if and only if any of the alternatives in the match accept the value. */
		orMatch: Option[Schema.OrMatcher] = None
	)
	
	case class NullMatch(
	
	)
	
	case class DoubleMatcher(
	  /** If specified, the input double value must be in the range specified here. Note: The range is using half-open interval semantics [start, end). */
		range: Option[Schema.DoubleRange] = None,
	  /** If specified, the input double value must be equal to the value specified here. */
		exact: Option[BigDecimal] = None
	)
	
	case class DoubleRange(
	  /** start of the range (inclusive) */
		start: Option[BigDecimal] = None,
	  /** end of the range (exclusive) */
		end: Option[BigDecimal] = None
	)
	
	case class ListMatcher(
	  /** If specified, at least one of the values in the list must match the value specified. */
		oneOf: Option[Schema.ValueMatcher] = None
	)
	
	case class OrMatcher(
		valueMatchers: Option[List[Schema.ValueMatcher]] = None
	)
	
	case class Node(
	  /** An opaque node identifier for the Envoy node. This also provides the local service node name. It should be set if any of the following features are used: :ref:`statsd `, :ref:`CDS `, and :ref:`HTTP tracing `, either in this message or via :option:`--service-node`. */
		id: Option[String] = None,
	  /** Defines the local service cluster name where Envoy is running. Though optional, it should be set if any of the following features are used: :ref:`statsd `, :ref:`health check cluster verification `, :ref:`runtime override directory `, :ref:`user agent addition `, :ref:`HTTP global rate limiting `, :ref:`CDS `, and :ref:`HTTP tracing `, either in this message or via :option:`--service-cluster`. */
		cluster: Option[String] = None,
	  /** Opaque metadata extending the node identifier. Envoy will pass this directly to the management server. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** Map from xDS resource type URL to dynamic context parameters. These may vary at runtime (unlike other fields in this message). For example, the xDS client may have a shard identifier that changes during the lifetime of the xDS client. In Envoy, this would be achieved by updating the dynamic context on the Server::Instance's LocalInfo context provider. The shard ID dynamic parameter then appears in this field during future discovery requests. */
		dynamicParameters: Option[Map[String, Schema.ContextParams]] = None,
	  /** Locality specifying where the Envoy instance is running. */
		locality: Option[Schema.Locality] = None,
	  /** Free-form string that identifies the entity requesting config. E.g. "envoy" or "grpc" */
		userAgentName: Option[String] = None,
	  /** Free-form string that identifies the version of the entity requesting config. E.g. "1.12.2" or "abcd1234", or "SpecialEnvoyBuild" */
		userAgentVersion: Option[String] = None,
	  /** Structured version of the entity requesting config. */
		userAgentBuildVersion: Option[Schema.BuildVersion] = None,
	  /** List of extensions and their versions supported by the node. */
		extensions: Option[List[Schema.Extension]] = None,
	  /** Client feature support list. These are well known features described in the Envoy API repository for a given major version of an API. Client features use reverse DNS naming scheme, for example ``com.acme.feature``. See :ref:`the list of features ` that xDS client may support. */
		clientFeatures: Option[List[String]] = None,
	  /** Known listening ports on the node as a generic hint to the management server for filtering :ref:`listeners ` to be returned. For example, if there is a listener bound to port 80, the list can optionally contain the SocketAddress ``(0.0.0.0,80)``. The field is optional and just a hint. */
		listeningAddresses: Option[List[Schema.Address]] = None
	)
	
	case class ContextParams(
		params: Option[Map[String, String]] = None
	)
	
	case class Locality(
	  /** Region this :ref:`zone ` belongs to. */
		region: Option[String] = None,
	  /** Defines the local service zone where Envoy is running. Though optional, it should be set if discovery service routing is used and the discovery service exposes :ref:`zone data `, either in this message or via :option:`--service-zone`. The meaning of zone is context dependent, e.g. `Availability Zone (AZ) `_ on AWS, `Zone `_ on GCP, etc. */
		zone: Option[String] = None,
	  /** When used for locality of upstream hosts, this field further splits zone into smaller chunks of sub-zones so they can be load balanced independently. */
		subZone: Option[String] = None
	)
	
	case class BuildVersion(
	  /** SemVer version of extension. */
		version: Option[Schema.SemanticVersion] = None,
	  /** Free-form build information. Envoy defines several well known keys in the source/common/version/version.h file */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class SemanticVersion(
		majorNumber: Option[Int] = None,
		minorNumber: Option[Int] = None,
		patch: Option[Int] = None
	)
	
	case class Extension(
	  /** This is the name of the Envoy filter as specified in the Envoy configuration, e.g. envoy.filters.http.router, com.acme.widget. */
		name: Option[String] = None,
	  /** Category of the extension. Extension category names use reverse DNS notation. For instance "envoy.filters.listener" for Envoy's built-in listener filters or "com.acme.filters.http" for HTTP filters from acme.com vendor. [#comment: */
		category: Option[String] = None,
	  /** [#not-implemented-hide:] Type descriptor of extension configuration proto. [#comment: */
		typeDescriptor: Option[String] = None,
	  /** The version is a property of the extension and maintained independently of other extensions and the Envoy API. This field is not set when extension did not provide version information. */
		version: Option[Schema.BuildVersion] = None,
	  /** Indicates that the extension is present but was disabled via dynamic configuration. */
		disabled: Option[Boolean] = None,
	  /** Type URLs of extension configuration protos. */
		typeUrls: Option[List[String]] = None
	)
	
	case class Address(
		socketAddress: Option[Schema.SocketAddress] = None,
		pipe: Option[Schema.Pipe] = None,
	  /** Specifies a user-space address handled by :ref:`internal listeners `. */
		envoyInternalAddress: Option[Schema.EnvoyInternalAddress] = None
	)
	
	object SocketAddress {
		enum ProtocolEnum extends Enum[ProtocolEnum] { case TCP, UDP }
	}
	case class SocketAddress(
		protocol: Option[Schema.SocketAddress.ProtocolEnum] = None,
	  /** The address for this socket. :ref:`Listeners ` will bind to the address. An empty address is not allowed. Specify ``0.0.0.0`` or ``::`` to bind to any address. [#comment:TODO(zuercher) reinstate when implemented: It is possible to distinguish a Listener address via the prefix/suffix matching in :ref:`FilterChainMatch `.] When used within an upstream :ref:`BindConfig `, the address controls the source address of outbound connections. For :ref:`clusters `, the cluster type determines whether the address must be an IP (``STATIC`` or ``EDS`` clusters) or a hostname resolved by DNS (``STRICT_DNS`` or ``LOGICAL_DNS`` clusters). Address resolution can be customized via :ref:`resolver_name `. */
		address: Option[String] = None,
		portValue: Option[Int] = None,
	  /** This is only valid if :ref:`resolver_name ` is specified below and the named resolver is capable of named port resolution. */
		namedPort: Option[String] = None,
	  /** The name of the custom resolver. This must have been registered with Envoy. If this is empty, a context dependent default applies. If the address is a concrete IP address, no resolution will occur. If address is a hostname this should be set for resolution other than DNS. Specifying a custom resolver with ``STRICT_DNS`` or ``LOGICAL_DNS`` will generate an error at runtime. */
		resolverName: Option[String] = None,
	  /** When binding to an IPv6 address above, this enables `IPv4 compatibility `_. Binding to ``::`` will allow both IPv4 and IPv6 connections, with peer IPv4 addresses mapped into IPv6 space as ``::FFFF:``. */
		ipv4Compat: Option[Boolean] = None
	)
	
	case class Pipe(
	  /** Unix Domain Socket path. On Linux, paths starting with '@' will use the abstract namespace. The starting '@' is replaced by a null byte by Envoy. Paths starting with '@' will result in an error in environments other than Linux. */
		path: Option[String] = None,
	  /** The mode for the Pipe. Not applicable for abstract sockets. */
		mode: Option[Int] = None
	)
	
	case class EnvoyInternalAddress(
	  /** Specifies the :ref:`name ` of the internal listener. */
		serverListenerName: Option[String] = None,
	  /** Specifies an endpoint identifier to distinguish between multiple endpoints for the same internal listener in a single upstream pool. Only used in the upstream addresses for tracking changes to individual endpoints. This, for example, may be set to the final destination IP for the target internal listener. */
		endpointId: Option[String] = None
	)
	
	case class ClientStatusResponse(
	  /** Client configs for the clients specified in the ClientStatusRequest. */
		config: Option[List[Schema.ClientConfig]] = None
	)
	
	case class ClientConfig(
	  /** Node for a particular client. */
		node: Option[Schema.Node] = None,
	  /** This field is deprecated in favor of generic_xds_configs which is much simpler and uniform in structure. */
		xdsConfig: Option[List[Schema.PerXdsConfig]] = None,
	  /** Represents generic xDS config and the exact config structure depends on the type URL (like Cluster if it is CDS) */
		genericXdsConfigs: Option[List[Schema.GenericXdsConfig]] = None,
	  /** For xDS clients, the scope in which the data is used. For example, gRPC indicates the data plane target or that the data is associated with gRPC server(s). */
		clientScope: Option[String] = None
	)
	
	object PerXdsConfig {
		enum StatusEnum extends Enum[StatusEnum] { case UNKNOWN, SYNCED, NOT_SENT, STALE, ERROR }
		enum ClientStatusEnum extends Enum[ClientStatusEnum] { case CLIENT_UNKNOWN, CLIENT_REQUESTED, CLIENT_ACKED, CLIENT_NACKED }
	}
	case class PerXdsConfig(
	  /** Config status generated by management servers. Will not be present if the CSDS server is an xDS client. */
		status: Option[Schema.PerXdsConfig.StatusEnum] = None,
	  /** Client config status is populated by xDS clients. Will not be present if the CSDS server is an xDS server. No matter what the client config status is, xDS clients should always dump the most recent accepted xDS config. .. attention:: This field is deprecated. Use :ref:`ClientResourceStatus ` for per-resource config status instead. */
		clientStatus: Option[Schema.PerXdsConfig.ClientStatusEnum] = None,
		listenerConfig: Option[Schema.ListenersConfigDump] = None,
		clusterConfig: Option[Schema.ClustersConfigDump] = None,
		routeConfig: Option[Schema.RoutesConfigDump] = None,
		scopedRouteConfig: Option[Schema.ScopedRoutesConfigDump] = None,
		endpointConfig: Option[Schema.EndpointsConfigDump] = None
	)
	
	case class ListenersConfigDump(
	  /** This is the :ref:`version_info ` in the last processed LDS discovery response. If there are only static bootstrap listeners, this field will be "". */
		versionInfo: Option[String] = None,
	  /** The statically loaded listener configs. */
		staticListeners: Option[List[Schema.StaticListener]] = None,
	  /** State for any warming, active, or draining listeners. */
		dynamicListeners: Option[List[Schema.DynamicListener]] = None
	)
	
	case class StaticListener(
	  /** The listener config. */
		listener: Option[Map[String, JsValue]] = None,
	  /** The timestamp when the Listener was last successfully updated. */
		lastUpdated: Option[String] = None
	)
	
	object DynamicListener {
		enum ClientStatusEnum extends Enum[ClientStatusEnum] { case UNKNOWN, REQUESTED, DOES_NOT_EXIST, ACKED, NACKED }
	}
	case class DynamicListener(
	  /** The name or unique id of this listener, pulled from the DynamicListenerState config. */
		name: Option[String] = None,
	  /** The listener state for any active listener by this name. These are listeners that are available to service data plane traffic. */
		activeState: Option[Schema.DynamicListenerState] = None,
	  /** The listener state for any warming listener by this name. These are listeners that are currently undergoing warming in preparation to service data plane traffic. Note that if attempting to recreate an Envoy configuration from a configuration dump, the warming listeners should generally be discarded. */
		warmingState: Option[Schema.DynamicListenerState] = None,
	  /** The listener state for any draining listener by this name. These are listeners that are currently undergoing draining in preparation to stop servicing data plane traffic. Note that if attempting to recreate an Envoy configuration from a configuration dump, the draining listeners should generally be discarded. */
		drainingState: Option[Schema.DynamicListenerState] = None,
	  /** Set if the last update failed, cleared after the next successful update. The ``error_state`` field contains the rejected version of this particular resource along with the reason and timestamp. For successfully updated or acknowledged resource, this field should be empty. */
		errorState: Option[Schema.UpdateFailureState] = None,
	  /** The client status of this resource. [#not-implemented-hide:] */
		clientStatus: Option[Schema.DynamicListener.ClientStatusEnum] = None
	)
	
	case class DynamicListenerState(
	  /** This is the per-resource version information. This version is currently taken from the :ref:`version_info ` field at the time that the listener was loaded. In the future, discrete per-listener versions may be supported by the API. */
		versionInfo: Option[String] = None,
	  /** The listener config. */
		listener: Option[Map[String, JsValue]] = None,
	  /** The timestamp when the Listener was last successfully updated. */
		lastUpdated: Option[String] = None
	)
	
	case class UpdateFailureState(
	  /** What the component configuration would have been if the update had succeeded. This field may not be populated by xDS clients due to storage overhead. */
		failedConfiguration: Option[Map[String, JsValue]] = None,
	  /** Time of the latest failed update attempt. */
		lastUpdateAttempt: Option[String] = None,
	  /** Details about the last failed update attempt. */
		details: Option[String] = None,
	  /** This is the version of the rejected resource. [#not-implemented-hide:] */
		versionInfo: Option[String] = None
	)
	
	case class ClustersConfigDump(
	  /** This is the :ref:`version_info ` in the last processed CDS discovery response. If there are only static bootstrap clusters, this field will be "". */
		versionInfo: Option[String] = None,
	  /** The statically loaded cluster configs. */
		staticClusters: Option[List[Schema.StaticCluster]] = None,
	  /** The dynamically loaded active clusters. These are clusters that are available to service data plane traffic. */
		dynamicActiveClusters: Option[List[Schema.DynamicCluster]] = None,
	  /** The dynamically loaded warming clusters. These are clusters that are currently undergoing warming in preparation to service data plane traffic. Note that if attempting to recreate an Envoy configuration from a configuration dump, the warming clusters should generally be discarded. */
		dynamicWarmingClusters: Option[List[Schema.DynamicCluster]] = None
	)
	
	case class StaticCluster(
	  /** The cluster config. */
		cluster: Option[Map[String, JsValue]] = None,
	  /** The timestamp when the Cluster was last updated. */
		lastUpdated: Option[String] = None
	)
	
	object DynamicCluster {
		enum ClientStatusEnum extends Enum[ClientStatusEnum] { case UNKNOWN, REQUESTED, DOES_NOT_EXIST, ACKED, NACKED }
	}
	case class DynamicCluster(
	  /** This is the per-resource version information. This version is currently taken from the :ref:`version_info ` field at the time that the cluster was loaded. In the future, discrete per-cluster versions may be supported by the API. */
		versionInfo: Option[String] = None,
	  /** The cluster config. */
		cluster: Option[Map[String, JsValue]] = None,
	  /** The timestamp when the Cluster was last updated. */
		lastUpdated: Option[String] = None,
	  /** Set if the last update failed, cleared after the next successful update. The ``error_state`` field contains the rejected version of this particular resource along with the reason and timestamp. For successfully updated or acknowledged resource, this field should be empty. [#not-implemented-hide:] */
		errorState: Option[Schema.UpdateFailureState] = None,
	  /** The client status of this resource. [#not-implemented-hide:] */
		clientStatus: Option[Schema.DynamicCluster.ClientStatusEnum] = None
	)
	
	case class RoutesConfigDump(
	  /** The statically loaded route configs. */
		staticRouteConfigs: Option[List[Schema.StaticRouteConfig]] = None,
	  /** The dynamically loaded route configs. */
		dynamicRouteConfigs: Option[List[Schema.DynamicRouteConfig]] = None
	)
	
	case class StaticRouteConfig(
	  /** The route config. */
		routeConfig: Option[Map[String, JsValue]] = None,
	  /** The timestamp when the Route was last updated. */
		lastUpdated: Option[String] = None
	)
	
	object DynamicRouteConfig {
		enum ClientStatusEnum extends Enum[ClientStatusEnum] { case UNKNOWN, REQUESTED, DOES_NOT_EXIST, ACKED, NACKED }
	}
	case class DynamicRouteConfig(
	  /** This is the per-resource version information. This version is currently taken from the :ref:`version_info ` field at the time that the route configuration was loaded. */
		versionInfo: Option[String] = None,
	  /** The route config. */
		routeConfig: Option[Map[String, JsValue]] = None,
	  /** The timestamp when the Route was last updated. */
		lastUpdated: Option[String] = None,
	  /** Set if the last update failed, cleared after the next successful update. The ``error_state`` field contains the rejected version of this particular resource along with the reason and timestamp. For successfully updated or acknowledged resource, this field should be empty. [#not-implemented-hide:] */
		errorState: Option[Schema.UpdateFailureState] = None,
	  /** The client status of this resource. [#not-implemented-hide:] */
		clientStatus: Option[Schema.DynamicRouteConfig.ClientStatusEnum] = None
	)
	
	case class ScopedRoutesConfigDump(
	  /** The statically loaded scoped route configs. */
		inlineScopedRouteConfigs: Option[List[Schema.InlineScopedRouteConfigs]] = None,
	  /** The dynamically loaded scoped route configs. */
		dynamicScopedRouteConfigs: Option[List[Schema.DynamicScopedRouteConfigs]] = None
	)
	
	case class InlineScopedRouteConfigs(
	  /** The name assigned to the scoped route configurations. */
		name: Option[String] = None,
	  /** The scoped route configurations. */
		scopedRouteConfigs: Option[List[Map[String, JsValue]]] = None,
	  /** The timestamp when the scoped route config set was last updated. */
		lastUpdated: Option[String] = None
	)
	
	object DynamicScopedRouteConfigs {
		enum ClientStatusEnum extends Enum[ClientStatusEnum] { case UNKNOWN, REQUESTED, DOES_NOT_EXIST, ACKED, NACKED }
	}
	case class DynamicScopedRouteConfigs(
	  /** The name assigned to the scoped route configurations. */
		name: Option[String] = None,
	  /** This is the per-resource version information. This version is currently taken from the :ref:`version_info ` field at the time that the scoped routes configuration was loaded. */
		versionInfo: Option[String] = None,
	  /** The scoped route configurations. */
		scopedRouteConfigs: Option[List[Map[String, JsValue]]] = None,
	  /** The timestamp when the scoped route config set was last updated. */
		lastUpdated: Option[String] = None,
	  /** Set if the last update failed, cleared after the next successful update. The ``error_state`` field contains the rejected version of this particular resource along with the reason and timestamp. For successfully updated or acknowledged resource, this field should be empty. [#not-implemented-hide:] */
		errorState: Option[Schema.UpdateFailureState] = None,
	  /** The client status of this resource. [#not-implemented-hide:] */
		clientStatus: Option[Schema.DynamicScopedRouteConfigs.ClientStatusEnum] = None
	)
	
	case class EndpointsConfigDump(
	  /** The statically loaded endpoint configs. */
		staticEndpointConfigs: Option[List[Schema.StaticEndpointConfig]] = None,
	  /** The dynamically loaded endpoint configs. */
		dynamicEndpointConfigs: Option[List[Schema.DynamicEndpointConfig]] = None
	)
	
	case class StaticEndpointConfig(
	  /** The endpoint config. */
		endpointConfig: Option[Map[String, JsValue]] = None,
	  /** [#not-implemented-hide:] The timestamp when the Endpoint was last updated. */
		lastUpdated: Option[String] = None
	)
	
	object DynamicEndpointConfig {
		enum ClientStatusEnum extends Enum[ClientStatusEnum] { case UNKNOWN, REQUESTED, DOES_NOT_EXIST, ACKED, NACKED }
	}
	case class DynamicEndpointConfig(
	  /** [#not-implemented-hide:] This is the per-resource version information. This version is currently taken from the :ref:`version_info ` field at the time that the endpoint configuration was loaded. */
		versionInfo: Option[String] = None,
	  /** The endpoint config. */
		endpointConfig: Option[Map[String, JsValue]] = None,
	  /** [#not-implemented-hide:] The timestamp when the Endpoint was last updated. */
		lastUpdated: Option[String] = None,
	  /** Set if the last update failed, cleared after the next successful update. The ``error_state`` field contains the rejected version of this particular resource along with the reason and timestamp. For successfully updated or acknowledged resource, this field should be empty. [#not-implemented-hide:] */
		errorState: Option[Schema.UpdateFailureState] = None,
	  /** The client status of this resource. [#not-implemented-hide:] */
		clientStatus: Option[Schema.DynamicEndpointConfig.ClientStatusEnum] = None
	)
	
	object GenericXdsConfig {
		enum ConfigStatusEnum extends Enum[ConfigStatusEnum] { case UNKNOWN, SYNCED, NOT_SENT, STALE, ERROR }
		enum ClientStatusEnum extends Enum[ClientStatusEnum] { case UNKNOWN, REQUESTED, DOES_NOT_EXIST, ACKED, NACKED }
	}
	case class GenericXdsConfig(
	  /** Type_url represents the fully qualified name of xDS resource type like envoy.v3.Cluster, envoy.v3.ClusterLoadAssignment etc. */
		typeUrl: Option[String] = None,
	  /** Name of the xDS resource */
		name: Option[String] = None,
	  /** This is the :ref:`version_info ` in the last processed xDS discovery response. If there are only static bootstrap listeners, this field will be "" */
		versionInfo: Option[String] = None,
	  /** The xDS resource config. Actual content depends on the type */
		xdsConfig: Option[Map[String, JsValue]] = None,
	  /** Timestamp when the xDS resource was last updated */
		lastUpdated: Option[String] = None,
	  /** Per xDS resource config status. It is generated by management servers. It will not be present if the CSDS server is an xDS client. */
		configStatus: Option[Schema.GenericXdsConfig.ConfigStatusEnum] = None,
	  /** Per xDS resource status from the view of a xDS client */
		clientStatus: Option[Schema.GenericXdsConfig.ClientStatusEnum] = None,
	  /** Set if the last update failed, cleared after the next successful update. The &#42;error_state&#42; field contains the rejected version of this particular resource along with the reason and timestamp. For successfully updated or acknowledged resource, this field should be empty. [#not-implemented-hide:] */
		errorState: Option[Schema.UpdateFailureState] = None,
	  /** Is static resource is true if it is specified in the config supplied through the file at the startup. */
		isStaticResource: Option[Boolean] = None
	)
}
