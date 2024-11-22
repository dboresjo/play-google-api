package com.boresjo.play.api.google.networkmanagement

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
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
	
	case class ListConnectivityTestsResponse(
	  /** List of Connectivity Tests. */
		resources: Option[List[Schema.ConnectivityTest]] = None,
	  /** Page token to fetch the next set of Connectivity Tests. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached (when querying all locations with `-`). */
		unreachable: Option[List[String]] = None
	)
	
	case class ConnectivityTest(
	  /** Identifier. Unique name of the resource using the form: `projects/{project_id}/locations/global/connectivityTests/{test_id}` */
		name: Option[String] = None,
	  /** The user-supplied description of the Connectivity Test. Maximum of 512 characters. */
		description: Option[String] = None,
	  /** Required. Source specification of the Connectivity Test. You can use a combination of source IP address, virtual machine (VM) instance, or Compute Engine network to uniquely identify the source location. Examples: If the source IP address is an internal IP address within a Google Cloud Virtual Private Cloud (VPC) network, then you must also specify the VPC network. Otherwise, specify the VM instance, which already contains its internal IP address and VPC network information. If the source of the test is within an on-premises network, then you must provide the destination VPC network. If the source endpoint is a Compute Engine VM instance with multiple network interfaces, the instance itself is not sufficient to identify the endpoint. So, you must also specify the source IP address or VPC network. A reachability analysis proceeds even if the source location is ambiguous. However, the test result may include endpoints that you don't intend to test. */
		source: Option[Schema.Endpoint] = None,
	  /** Required. Destination specification of the Connectivity Test. You can use a combination of destination IP address, Compute Engine VM instance, or VPC network to uniquely identify the destination location. Even if the destination IP address is not unique, the source IP location is unique. Usually, the analysis can infer the destination endpoint from route information. If the destination you specify is a VM instance and the instance has multiple network interfaces, then you must also specify either a destination IP address or VPC network to identify the destination interface. A reachability analysis proceeds even if the destination location is ambiguous. However, the result can include endpoints that you don't intend to test. */
		destination: Option[Schema.Endpoint] = None,
	  /** IP Protocol of the test. When not provided, "TCP" is assumed. */
		protocol: Option[String] = None,
	  /** Other projects that may be relevant for reachability analysis. This is applicable to scenarios where a test can cross project boundaries. */
		relatedProjects: Option[List[String]] = None,
	  /** Output only. The display name of a Connectivity Test. */
		displayName: Option[String] = None,
	  /** Resource labels to represent user-provided metadata. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The time the test was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the test's configuration was updated. */
		updateTime: Option[String] = None,
	  /** Output only. The reachability details of this test from the latest run. The details are updated when creating a new test, updating an existing test, or triggering a one-time rerun of an existing test. */
		reachabilityDetails: Option[Schema.ReachabilityDetails] = None,
	  /** Output only. The probing details of this test from the latest run, present for applicable tests only. The details are updated when creating a new test, updating an existing test, or triggering a one-time rerun of an existing test. */
		probingDetails: Option[Schema.ProbingDetails] = None,
	  /** Whether the test should skip firewall checking. If not provided, we assume false. */
		bypassFirewallChecks: Option[Boolean] = None
	)
	
	object Endpoint {
		enum ForwardingRuleTargetEnum extends Enum[ForwardingRuleTargetEnum] { case FORWARDING_RULE_TARGET_UNSPECIFIED, INSTANCE, LOAD_BALANCER, VPN_GATEWAY, PSC }
		enum LoadBalancerTypeEnum extends Enum[LoadBalancerTypeEnum] { case LOAD_BALANCER_TYPE_UNSPECIFIED, HTTPS_ADVANCED_LOAD_BALANCER, HTTPS_LOAD_BALANCER, REGIONAL_HTTPS_LOAD_BALANCER, INTERNAL_HTTPS_LOAD_BALANCER, SSL_PROXY_LOAD_BALANCER, TCP_PROXY_LOAD_BALANCER, INTERNAL_TCP_PROXY_LOAD_BALANCER, NETWORK_LOAD_BALANCER, LEGACY_NETWORK_LOAD_BALANCER, TCP_UDP_INTERNAL_LOAD_BALANCER }
		enum NetworkTypeEnum extends Enum[NetworkTypeEnum] { case NETWORK_TYPE_UNSPECIFIED, GCP_NETWORK, NON_GCP_NETWORK }
	}
	case class Endpoint(
	  /** The IP address of the endpoint, which can be an external or internal IP. */
		ipAddress: Option[String] = None,
	  /** The IP protocol port of the endpoint. Only applicable when protocol is TCP or UDP. */
		port: Option[Int] = None,
	  /** A Compute Engine instance URI. */
		instance: Option[String] = None,
	  /** A forwarding rule and its corresponding IP address represent the frontend configuration of a Google Cloud load balancer. Forwarding rules are also used for protocol forwarding, Private Service Connect and other network services to provide forwarding information in the control plane. Format: projects/{project}/global/forwardingRules/{id} or projects/{project}/regions/{region}/forwardingRules/{id} */
		forwardingRule: Option[String] = None,
	  /** Output only. Specifies the type of the target of the forwarding rule. */
		forwardingRuleTarget: Option[Schema.Endpoint.ForwardingRuleTargetEnum] = None,
	  /** Output only. ID of the load balancer the forwarding rule points to. Empty for forwarding rules not related to load balancers. */
		loadBalancerId: Option[String] = None,
	  /** Output only. Type of the load balancer the forwarding rule points to. */
		loadBalancerType: Option[Schema.Endpoint.LoadBalancerTypeEnum] = None,
	  /** A cluster URI for [Google Kubernetes Engine cluster control plane](https://cloud.google.com/kubernetes-engine/docs/concepts/cluster-architecture). */
		gkeMasterCluster: Option[String] = None,
	  /** DNS endpoint of [Google Kubernetes Engine cluster control plane](https://cloud.google.com/kubernetes-engine/docs/concepts/cluster-architecture). Requires gke_master_cluster to be set, can't be used simultaneoulsly with ip_address or network. Applicable only to destination endpoint. */
		fqdn: Option[String] = None,
	  /** A [Cloud SQL](https://cloud.google.com/sql) instance URI. */
		cloudSqlInstance: Option[String] = None,
	  /** A [Redis Instance](https://cloud.google.com/memorystore/docs/redis) URI. */
		redisInstance: Option[String] = None,
	  /** A [Redis Cluster](https://cloud.google.com/memorystore/docs/cluster) URI. */
		redisCluster: Option[String] = None,
	  /** A [Cloud Function](https://cloud.google.com/functions). */
		cloudFunction: Option[Schema.CloudFunctionEndpoint] = None,
	  /** An [App Engine](https://cloud.google.com/appengine) [service version](https://cloud.google.com/appengine/docs/admin-api/reference/rest/v1/apps.services.versions). */
		appEngineVersion: Option[Schema.AppEngineVersionEndpoint] = None,
	  /** A [Cloud Run](https://cloud.google.com/run) [revision](https://cloud.google.com/run/docs/reference/rest/v1/namespaces.revisions/get) */
		cloudRunRevision: Option[Schema.CloudRunRevisionEndpoint] = None,
	  /** A Compute Engine network URI. */
		network: Option[String] = None,
	  /** Type of the network where the endpoint is located. Applicable only to source endpoint, as destination network type can be inferred from the source. */
		networkType: Option[Schema.Endpoint.NetworkTypeEnum] = None,
	  /** Project ID where the endpoint is located. The Project ID can be derived from the URI if you provide a VM instance or network URI. The following are two cases where you must provide the project ID: 1. Only the IP address is specified, and the IP address is within a Google Cloud project. 2. When you are using Shared VPC and the IP address that you provide is from the service project. In this case, the network that the IP address resides in is defined in the host project. */
		projectId: Option[String] = None
	)
	
	case class CloudFunctionEndpoint(
	  /** A [Cloud Function](https://cloud.google.com/functions) name. */
		uri: Option[String] = None
	)
	
	case class AppEngineVersionEndpoint(
	  /** An [App Engine](https://cloud.google.com/appengine) [service version](https://cloud.google.com/appengine/docs/admin-api/reference/rest/v1/apps.services.versions) name. */
		uri: Option[String] = None
	)
	
	case class CloudRunRevisionEndpoint(
	  /** A [Cloud Run](https://cloud.google.com/run) [revision](https://cloud.google.com/run/docs/reference/rest/v1/namespaces.revisions/get) URI. The format is: projects/{project}/locations/{location}/revisions/{revision} */
		uri: Option[String] = None
	)
	
	object ReachabilityDetails {
		enum ResultEnum extends Enum[ResultEnum] { case RESULT_UNSPECIFIED, REACHABLE, UNREACHABLE, AMBIGUOUS, UNDETERMINED }
	}
	case class ReachabilityDetails(
	  /** The overall result of the test's configuration analysis. */
		result: Option[Schema.ReachabilityDetails.ResultEnum] = None,
	  /** The time of the configuration analysis. */
		verifyTime: Option[String] = None,
	  /** The details of a failure or a cancellation of reachability analysis. */
		error: Option[Schema.Status] = None,
	  /** Result may contain a list of traces if a test has multiple possible paths in the network, such as when destination endpoint is a load balancer with multiple backends. */
		traces: Option[List[Schema.Trace]] = None
	)
	
	case class Trace(
	  /** Derived from the source and destination endpoints definition specified by user request, and validated by the data plane model. If there are multiple traces starting from different source locations, then the endpoint_info may be different between traces. */
		endpointInfo: Option[Schema.EndpointInfo] = None,
	  /** A trace of a test contains multiple steps from the initial state to the final state (delivered, dropped, forwarded, or aborted). The steps are ordered by the processing sequence within the simulated network state machine. It is critical to preserve the order of the steps and avoid reordering or sorting them. */
		steps: Option[List[Schema.Step]] = None,
	  /** ID of trace. For forward traces, this ID is unique for each trace. For return traces, it matches ID of associated forward trace. A single forward trace can be associated with none, one or more than one return trace. */
		forwardTraceId: Option[Int] = None
	)
	
	case class EndpointInfo(
	  /** Source IP address. */
		sourceIp: Option[String] = None,
	  /** Destination IP address. */
		destinationIp: Option[String] = None,
	  /** IP protocol in string format, for example: "TCP", "UDP", "ICMP". */
		protocol: Option[String] = None,
	  /** Source port. Only valid when protocol is TCP or UDP. */
		sourcePort: Option[Int] = None,
	  /** Destination port. Only valid when protocol is TCP or UDP. */
		destinationPort: Option[Int] = None,
	  /** URI of the network where this packet originates from. */
		sourceNetworkUri: Option[String] = None,
	  /** URI of the network where this packet is sent to. */
		destinationNetworkUri: Option[String] = None,
	  /** URI of the source telemetry agent this packet originates from. */
		sourceAgentUri: Option[String] = None
	)
	
	object Step {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, START_FROM_INSTANCE, START_FROM_INTERNET, START_FROM_GOOGLE_SERVICE, START_FROM_PRIVATE_NETWORK, START_FROM_GKE_MASTER, START_FROM_CLOUD_SQL_INSTANCE, START_FROM_REDIS_INSTANCE, START_FROM_REDIS_CLUSTER, START_FROM_CLOUD_FUNCTION, START_FROM_APP_ENGINE_VERSION, START_FROM_CLOUD_RUN_REVISION, START_FROM_STORAGE_BUCKET, START_FROM_PSC_PUBLISHED_SERVICE, START_FROM_SERVERLESS_NEG, APPLY_INGRESS_FIREWALL_RULE, APPLY_EGRESS_FIREWALL_RULE, APPLY_ROUTE, APPLY_FORWARDING_RULE, ANALYZE_LOAD_BALANCER_BACKEND, SPOOFING_APPROVED, ARRIVE_AT_INSTANCE, ARRIVE_AT_INTERNAL_LOAD_BALANCER, ARRIVE_AT_EXTERNAL_LOAD_BALANCER, ARRIVE_AT_VPN_GATEWAY, ARRIVE_AT_VPN_TUNNEL, ARRIVE_AT_VPC_CONNECTOR, NAT, PROXY_CONNECTION, DELIVER, DROP, FORWARD, ABORT, VIEWER_PERMISSION_MISSING }
	}
	case class Step(
	  /** A description of the step. Usually this is a summary of the state. */
		description: Option[String] = None,
	  /** Each step is in one of the pre-defined states. */
		state: Option[Schema.Step.StateEnum] = None,
	  /** This is a step that leads to the final state Drop. */
		causesDrop: Option[Boolean] = None,
	  /** Project ID that contains the configuration this step is validating. */
		projectId: Option[String] = None,
	  /** Display information of a Compute Engine instance. */
		instance: Option[Schema.InstanceInfo] = None,
	  /** Display information of a Compute Engine firewall rule. */
		firewall: Option[Schema.FirewallInfo] = None,
	  /** Display information of a Compute Engine route. */
		route: Option[Schema.RouteInfo] = None,
	  /** Display information of the source and destination under analysis. The endpoint information in an intermediate state may differ with the initial input, as it might be modified by state like NAT, or Connection Proxy. */
		endpoint: Option[Schema.EndpointInfo] = None,
	  /** Display information of a Google service */
		googleService: Option[Schema.GoogleServiceInfo] = None,
	  /** Display information of a Compute Engine forwarding rule. */
		forwardingRule: Option[Schema.ForwardingRuleInfo] = None,
	  /** Display information of a Compute Engine VPN gateway. */
		vpnGateway: Option[Schema.VpnGatewayInfo] = None,
	  /** Display information of a Compute Engine VPN tunnel. */
		vpnTunnel: Option[Schema.VpnTunnelInfo] = None,
	  /** Display information of a VPC connector. */
		vpcConnector: Option[Schema.VpcConnectorInfo] = None,
	  /** Display information of the final state "deliver" and reason. */
		deliver: Option[Schema.DeliverInfo] = None,
	  /** Display information of the final state "forward" and reason. */
		forward: Option[Schema.ForwardInfo] = None,
	  /** Display information of the final state "abort" and reason. */
		abort: Option[Schema.AbortInfo] = None,
	  /** Display information of the final state "drop" and reason. */
		drop: Option[Schema.DropInfo] = None,
	  /** Display information of the load balancers. Deprecated in favor of the `load_balancer_backend_info` field, not used in new tests. */
		loadBalancer: Option[Schema.LoadBalancerInfo] = None,
	  /** Display information of a Google Cloud network. */
		network: Option[Schema.NetworkInfo] = None,
	  /** Display information of a Google Kubernetes Engine cluster master. */
		gkeMaster: Option[Schema.GKEMasterInfo] = None,
	  /** Display information of a Cloud SQL instance. */
		cloudSqlInstance: Option[Schema.CloudSQLInstanceInfo] = None,
	  /** Display information of a Redis Instance. */
		redisInstance: Option[Schema.RedisInstanceInfo] = None,
	  /** Display information of a Redis Cluster. */
		redisCluster: Option[Schema.RedisClusterInfo] = None,
	  /** Display information of a Cloud Function. */
		cloudFunction: Option[Schema.CloudFunctionInfo] = None,
	  /** Display information of an App Engine service version. */
		appEngineVersion: Option[Schema.AppEngineVersionInfo] = None,
	  /** Display information of a Cloud Run revision. */
		cloudRunRevision: Option[Schema.CloudRunRevisionInfo] = None,
	  /** Display information of a NAT. */
		nat: Option[Schema.NatInfo] = None,
	  /** Display information of a ProxyConnection. */
		proxyConnection: Option[Schema.ProxyConnectionInfo] = None,
	  /** Display information of a specific load balancer backend. */
		loadBalancerBackendInfo: Option[Schema.LoadBalancerBackendInfo] = None,
	  /** Display information of a Storage Bucket. Used only for return traces. */
		storageBucket: Option[Schema.StorageBucketInfo] = None,
	  /** Display information of a Serverless network endpoint group backend. Used only for return traces. */
		serverlessNeg: Option[Schema.ServerlessNegInfo] = None
	)
	
	case class InstanceInfo(
	  /** Name of a Compute Engine instance. */
		displayName: Option[String] = None,
	  /** URI of a Compute Engine instance. */
		uri: Option[String] = None,
	  /** Name of the network interface of a Compute Engine instance. */
		interface: Option[String] = None,
	  /** URI of a Compute Engine network. */
		networkUri: Option[String] = None,
	  /** Internal IP address of the network interface. */
		internalIp: Option[String] = None,
	  /** External IP address of the network interface. */
		externalIp: Option[String] = None,
	  /** Network tags configured on the instance. */
		networkTags: Option[List[String]] = None,
	  /** Service account authorized for the instance. */
		serviceAccount: Option[String] = None,
	  /** URI of the PSC network attachment the NIC is attached to (if relevant). */
		pscNetworkAttachmentUri: Option[String] = None
	)
	
	object FirewallInfo {
		enum FirewallRuleTypeEnum extends Enum[FirewallRuleTypeEnum] { case FIREWALL_RULE_TYPE_UNSPECIFIED, HIERARCHICAL_FIREWALL_POLICY_RULE, VPC_FIREWALL_RULE, IMPLIED_VPC_FIREWALL_RULE, SERVERLESS_VPC_ACCESS_MANAGED_FIREWALL_RULE, NETWORK_FIREWALL_POLICY_RULE, NETWORK_REGIONAL_FIREWALL_POLICY_RULE, UNSUPPORTED_FIREWALL_POLICY_RULE, TRACKING_STATE }
	}
	case class FirewallInfo(
	  /** The display name of the firewall rule. This field might be empty for firewall policy rules. */
		displayName: Option[String] = None,
	  /** The URI of the firewall rule. This field is not applicable to implied VPC firewall rules. */
		uri: Option[String] = None,
	  /** Possible values: INGRESS, EGRESS */
		direction: Option[String] = None,
	  /** Possible values: ALLOW, DENY, APPLY_SECURITY_PROFILE_GROUP */
		action: Option[String] = None,
	  /** The priority of the firewall rule. */
		priority: Option[Int] = None,
	  /** The URI of the VPC network that the firewall rule is associated with. This field is not applicable to hierarchical firewall policy rules. */
		networkUri: Option[String] = None,
	  /** The target tags defined by the VPC firewall rule. This field is not applicable to firewall policy rules. */
		targetTags: Option[List[String]] = None,
	  /** The target service accounts specified by the firewall rule. */
		targetServiceAccounts: Option[List[String]] = None,
	  /** The name of the firewall policy that this rule is associated with. This field is not applicable to VPC firewall rules and implied VPC firewall rules. */
		policy: Option[String] = None,
	  /** The URI of the firewall policy that this rule is associated with. This field is not applicable to VPC firewall rules and implied VPC firewall rules. */
		policyUri: Option[String] = None,
	  /** The firewall rule's type. */
		firewallRuleType: Option[Schema.FirewallInfo.FirewallRuleTypeEnum] = None
	)
	
	object RouteInfo {
		enum RouteTypeEnum extends Enum[RouteTypeEnum] { case ROUTE_TYPE_UNSPECIFIED, SUBNET, STATIC, DYNAMIC, PEERING_SUBNET, PEERING_STATIC, PEERING_DYNAMIC, POLICY_BASED, ADVERTISED }
		enum NextHopTypeEnum extends Enum[NextHopTypeEnum] { case NEXT_HOP_TYPE_UNSPECIFIED, NEXT_HOP_IP, NEXT_HOP_INSTANCE, NEXT_HOP_NETWORK, NEXT_HOP_PEERING, NEXT_HOP_INTERCONNECT, NEXT_HOP_VPN_TUNNEL, NEXT_HOP_VPN_GATEWAY, NEXT_HOP_INTERNET_GATEWAY, NEXT_HOP_BLACKHOLE, NEXT_HOP_ILB, NEXT_HOP_ROUTER_APPLIANCE, NEXT_HOP_NCC_HUB }
		enum RouteScopeEnum extends Enum[RouteScopeEnum] { case ROUTE_SCOPE_UNSPECIFIED, NETWORK, NCC_HUB }
	}
	case class RouteInfo(
	  /** Type of route. */
		routeType: Option[Schema.RouteInfo.RouteTypeEnum] = None,
	  /** Type of next hop. */
		nextHopType: Option[Schema.RouteInfo.NextHopTypeEnum] = None,
	  /** Indicates where route is applicable. */
		routeScope: Option[Schema.RouteInfo.RouteScopeEnum] = None,
	  /** Name of a route. */
		displayName: Option[String] = None,
	  /** URI of a route (if applicable). */
		uri: Option[String] = None,
	  /** Region of the route (if applicable). */
		region: Option[String] = None,
	  /** Destination IP range of the route. */
		destIpRange: Option[String] = None,
	  /** Next hop of the route. */
		nextHop: Option[String] = None,
	  /** URI of a Compute Engine network. NETWORK routes only. */
		networkUri: Option[String] = None,
	  /** Priority of the route. */
		priority: Option[Int] = None,
	  /** Instance tags of the route. */
		instanceTags: Option[List[String]] = None,
	  /** Source IP address range of the route. Policy based routes only. */
		srcIpRange: Option[String] = None,
	  /** Destination port ranges of the route. Policy based routes only. */
		destPortRanges: Option[List[String]] = None,
	  /** Source port ranges of the route. Policy based routes only. */
		srcPortRanges: Option[List[String]] = None,
	  /** Protocols of the route. Policy based routes only. */
		protocols: Option[List[String]] = None,
	  /** URI of a NCC Hub. NCC_HUB routes only. */
		nccHubUri: Option[String] = None,
	  /** URI of a NCC Spoke. NCC_HUB routes only. */
		nccSpokeUri: Option[String] = None,
	  /** For advertised dynamic routes, the URI of the Cloud Router that advertised the corresponding IP prefix. */
		advertisedRouteSourceRouterUri: Option[String] = None,
	  /** For advertised routes, the URI of their next hop, i.e. the URI of the hybrid endpoint (VPN tunnel, Interconnect attachment, NCC router appliance) the advertised prefix is advertised through, or URI of the source peered network. */
		advertisedRouteNextHopUri: Option[String] = None
	)
	
	object GoogleServiceInfo {
		enum GoogleServiceTypeEnum extends Enum[GoogleServiceTypeEnum] { case GOOGLE_SERVICE_TYPE_UNSPECIFIED, IAP, GFE_PROXY_OR_HEALTH_CHECK_PROBER, CLOUD_DNS, GOOGLE_API, GOOGLE_API_PSC, GOOGLE_API_VPC_SC }
	}
	case class GoogleServiceInfo(
	  /** Source IP address. */
		sourceIp: Option[String] = None,
	  /** Recognized type of a Google Service. */
		googleServiceType: Option[Schema.GoogleServiceInfo.GoogleServiceTypeEnum] = None
	)
	
	case class ForwardingRuleInfo(
	  /** Name of the forwarding rule. */
		displayName: Option[String] = None,
	  /** URI of the forwarding rule. */
		uri: Option[String] = None,
	  /** Protocol defined in the forwarding rule that matches the packet. */
		matchedProtocol: Option[String] = None,
	  /** Port range defined in the forwarding rule that matches the packet. */
		matchedPortRange: Option[String] = None,
	  /** VIP of the forwarding rule. */
		vip: Option[String] = None,
	  /** Target type of the forwarding rule. */
		target: Option[String] = None,
	  /** Network URI. */
		networkUri: Option[String] = None,
	  /** Region of the forwarding rule. Set only for regional forwarding rules. */
		region: Option[String] = None,
	  /** Name of the load balancer the forwarding rule belongs to. Empty for forwarding rules not related to load balancers (like PSC forwarding rules). */
		loadBalancerName: Option[String] = None,
	  /** URI of the PSC service attachment this forwarding rule targets (if applicable). */
		pscServiceAttachmentUri: Option[String] = None,
	  /** PSC Google API target this forwarding rule targets (if applicable). */
		pscGoogleApiTarget: Option[String] = None
	)
	
	case class VpnGatewayInfo(
	  /** Name of a VPN gateway. */
		displayName: Option[String] = None,
	  /** URI of a VPN gateway. */
		uri: Option[String] = None,
	  /** URI of a Compute Engine network where the VPN gateway is configured. */
		networkUri: Option[String] = None,
	  /** IP address of the VPN gateway. */
		ipAddress: Option[String] = None,
	  /** A VPN tunnel that is associated with this VPN gateway. There may be multiple VPN tunnels configured on a VPN gateway, and only the one relevant to the test is displayed. */
		vpnTunnelUri: Option[String] = None,
	  /** Name of a Google Cloud region where this VPN gateway is configured. */
		region: Option[String] = None
	)
	
	object VpnTunnelInfo {
		enum RoutingTypeEnum extends Enum[RoutingTypeEnum] { case ROUTING_TYPE_UNSPECIFIED, ROUTE_BASED, POLICY_BASED, DYNAMIC }
	}
	case class VpnTunnelInfo(
	  /** Name of a VPN tunnel. */
		displayName: Option[String] = None,
	  /** URI of a VPN tunnel. */
		uri: Option[String] = None,
	  /** URI of the VPN gateway at local end of the tunnel. */
		sourceGateway: Option[String] = None,
	  /** URI of a VPN gateway at remote end of the tunnel. */
		remoteGateway: Option[String] = None,
	  /** Remote VPN gateway's IP address. */
		remoteGatewayIp: Option[String] = None,
	  /** Local VPN gateway's IP address. */
		sourceGatewayIp: Option[String] = None,
	  /** URI of a Compute Engine network where the VPN tunnel is configured. */
		networkUri: Option[String] = None,
	  /** Name of a Google Cloud region where this VPN tunnel is configured. */
		region: Option[String] = None,
	  /** Type of the routing policy. */
		routingType: Option[Schema.VpnTunnelInfo.RoutingTypeEnum] = None
	)
	
	case class VpcConnectorInfo(
	  /** Name of a VPC connector. */
		displayName: Option[String] = None,
	  /** URI of a VPC connector. */
		uri: Option[String] = None,
	  /** Location in which the VPC connector is deployed. */
		location: Option[String] = None
	)
	
	object DeliverInfo {
		enum TargetEnum extends Enum[TargetEnum] { case TARGET_UNSPECIFIED, INSTANCE, INTERNET, GOOGLE_API, GKE_MASTER, CLOUD_SQL_INSTANCE, PSC_PUBLISHED_SERVICE, PSC_GOOGLE_API, PSC_VPC_SC, SERVERLESS_NEG, STORAGE_BUCKET, PRIVATE_NETWORK, CLOUD_FUNCTION, APP_ENGINE_VERSION, CLOUD_RUN_REVISION, GOOGLE_MANAGED_SERVICE, REDIS_INSTANCE, REDIS_CLUSTER }
	}
	case class DeliverInfo(
	  /** Target type where the packet is delivered to. */
		target: Option[Schema.DeliverInfo.TargetEnum] = None,
	  /** URI of the resource that the packet is delivered to. */
		resourceUri: Option[String] = None,
	  /** IP address of the target (if applicable). */
		ipAddress: Option[String] = None,
	  /** Name of the Cloud Storage Bucket the packet is delivered to (if applicable). */
		storageBucket: Option[String] = None,
	  /** PSC Google API target the packet is delivered to (if applicable). */
		pscGoogleApiTarget: Option[String] = None
	)
	
	object ForwardInfo {
		enum TargetEnum extends Enum[TargetEnum] { case TARGET_UNSPECIFIED, PEERING_VPC, VPN_GATEWAY, INTERCONNECT, GKE_MASTER, IMPORTED_CUSTOM_ROUTE_NEXT_HOP, CLOUD_SQL_INSTANCE, ANOTHER_PROJECT, NCC_HUB, ROUTER_APPLIANCE }
	}
	case class ForwardInfo(
	  /** Target type where this packet is forwarded to. */
		target: Option[Schema.ForwardInfo.TargetEnum] = None,
	  /** URI of the resource that the packet is forwarded to. */
		resourceUri: Option[String] = None,
	  /** IP address of the target (if applicable). */
		ipAddress: Option[String] = None
	)
	
	object AbortInfo {
		enum CauseEnum extends Enum[CauseEnum] { case CAUSE_UNSPECIFIED, UNKNOWN_NETWORK, UNKNOWN_PROJECT, NO_EXTERNAL_IP, UNINTENDED_DESTINATION, SOURCE_ENDPOINT_NOT_FOUND, MISMATCHED_SOURCE_NETWORK, DESTINATION_ENDPOINT_NOT_FOUND, MISMATCHED_DESTINATION_NETWORK, UNKNOWN_IP, GOOGLE_MANAGED_SERVICE_UNKNOWN_IP, SOURCE_IP_ADDRESS_NOT_IN_SOURCE_NETWORK, PERMISSION_DENIED, PERMISSION_DENIED_NO_CLOUD_NAT_CONFIGS, PERMISSION_DENIED_NO_NEG_ENDPOINT_CONFIGS, PERMISSION_DENIED_NO_CLOUD_ROUTER_CONFIGS, NO_SOURCE_LOCATION, INVALID_ARGUMENT, TRACE_TOO_LONG, INTERNAL_ERROR, UNSUPPORTED, MISMATCHED_IP_VERSION, GKE_KONNECTIVITY_PROXY_UNSUPPORTED, RESOURCE_CONFIG_NOT_FOUND, VM_INSTANCE_CONFIG_NOT_FOUND, NETWORK_CONFIG_NOT_FOUND, FIREWALL_CONFIG_NOT_FOUND, ROUTE_CONFIG_NOT_FOUND, GOOGLE_MANAGED_SERVICE_AMBIGUOUS_PSC_ENDPOINT, SOURCE_PSC_CLOUD_SQL_UNSUPPORTED, SOURCE_REDIS_CLUSTER_UNSUPPORTED, SOURCE_REDIS_INSTANCE_UNSUPPORTED, SOURCE_FORWARDING_RULE_UNSUPPORTED, NON_ROUTABLE_IP_ADDRESS, UNKNOWN_ISSUE_IN_GOOGLE_MANAGED_PROJECT, UNSUPPORTED_GOOGLE_MANAGED_PROJECT_CONFIG }
	}
	case class AbortInfo(
	  /** Causes that the analysis is aborted. */
		cause: Option[Schema.AbortInfo.CauseEnum] = None,
	  /** URI of the resource that caused the abort. */
		resourceUri: Option[String] = None,
	  /** IP address that caused the abort. */
		ipAddress: Option[String] = None,
	  /** List of project IDs the user specified in the request but lacks access to. In this case, analysis is aborted with the PERMISSION_DENIED cause. */
		projectsMissingPermission: Option[List[String]] = None
	)
	
	object DropInfo {
		enum CauseEnum extends Enum[CauseEnum] { case CAUSE_UNSPECIFIED, UNKNOWN_EXTERNAL_ADDRESS, FOREIGN_IP_DISALLOWED, FIREWALL_RULE, NO_ROUTE, ROUTE_BLACKHOLE, ROUTE_WRONG_NETWORK, ROUTE_NEXT_HOP_IP_ADDRESS_NOT_RESOLVED, ROUTE_NEXT_HOP_RESOURCE_NOT_FOUND, ROUTE_NEXT_HOP_INSTANCE_WRONG_NETWORK, ROUTE_NEXT_HOP_INSTANCE_NON_PRIMARY_IP, ROUTE_NEXT_HOP_FORWARDING_RULE_IP_MISMATCH, ROUTE_NEXT_HOP_VPN_TUNNEL_NOT_ESTABLISHED, ROUTE_NEXT_HOP_FORWARDING_RULE_TYPE_INVALID, NO_ROUTE_FROM_INTERNET_TO_PRIVATE_IPV6_ADDRESS, VPN_TUNNEL_LOCAL_SELECTOR_MISMATCH, VPN_TUNNEL_REMOTE_SELECTOR_MISMATCH, PRIVATE_TRAFFIC_TO_INTERNET, PRIVATE_GOOGLE_ACCESS_DISALLOWED, PRIVATE_GOOGLE_ACCESS_VIA_VPN_TUNNEL_UNSUPPORTED, NO_EXTERNAL_ADDRESS, UNKNOWN_INTERNAL_ADDRESS, FORWARDING_RULE_MISMATCH, FORWARDING_RULE_NO_INSTANCES, FIREWALL_BLOCKING_LOAD_BALANCER_BACKEND_HEALTH_CHECK, INSTANCE_NOT_RUNNING, GKE_CLUSTER_NOT_RUNNING, CLOUD_SQL_INSTANCE_NOT_RUNNING, REDIS_INSTANCE_NOT_RUNNING, REDIS_CLUSTER_NOT_RUNNING, TRAFFIC_TYPE_BLOCKED, GKE_MASTER_UNAUTHORIZED_ACCESS, CLOUD_SQL_INSTANCE_UNAUTHORIZED_ACCESS, DROPPED_INSIDE_GKE_SERVICE, DROPPED_INSIDE_CLOUD_SQL_SERVICE, GOOGLE_MANAGED_SERVICE_NO_PEERING, GOOGLE_MANAGED_SERVICE_NO_PSC_ENDPOINT, GKE_PSC_ENDPOINT_MISSING, CLOUD_SQL_INSTANCE_NO_IP_ADDRESS, GKE_CONTROL_PLANE_REGION_MISMATCH, PUBLIC_GKE_CONTROL_PLANE_TO_PRIVATE_DESTINATION, GKE_CONTROL_PLANE_NO_ROUTE, CLOUD_SQL_INSTANCE_NOT_CONFIGURED_FOR_EXTERNAL_TRAFFIC, PUBLIC_CLOUD_SQL_INSTANCE_TO_PRIVATE_DESTINATION, CLOUD_SQL_INSTANCE_NO_ROUTE, CLOUD_SQL_CONNECTOR_REQUIRED, CLOUD_FUNCTION_NOT_ACTIVE, VPC_CONNECTOR_NOT_SET, VPC_CONNECTOR_NOT_RUNNING, VPC_CONNECTOR_SERVERLESS_TRAFFIC_BLOCKED, VPC_CONNECTOR_HEALTH_CHECK_TRAFFIC_BLOCKED, FORWARDING_RULE_REGION_MISMATCH, PSC_CONNECTION_NOT_ACCEPTED, PSC_ENDPOINT_ACCESSED_FROM_PEERED_NETWORK, PSC_NEG_PRODUCER_ENDPOINT_NO_GLOBAL_ACCESS, PSC_NEG_PRODUCER_FORWARDING_RULE_MULTIPLE_PORTS, CLOUD_SQL_PSC_NEG_UNSUPPORTED, NO_NAT_SUBNETS_FOR_PSC_SERVICE_ATTACHMENT, PSC_TRANSITIVITY_NOT_PROPAGATED, HYBRID_NEG_NON_DYNAMIC_ROUTE_MATCHED, HYBRID_NEG_NON_LOCAL_DYNAMIC_ROUTE_MATCHED, CLOUD_RUN_REVISION_NOT_READY, DROPPED_INSIDE_PSC_SERVICE_PRODUCER, LOAD_BALANCER_HAS_NO_PROXY_SUBNET, CLOUD_NAT_NO_ADDRESSES, ROUTING_LOOP, DROPPED_INSIDE_GOOGLE_MANAGED_SERVICE, LOAD_BALANCER_BACKEND_INVALID_NETWORK, BACKEND_SERVICE_NAMED_PORT_NOT_DEFINED, DESTINATION_IS_PRIVATE_NAT_IP_RANGE, DROPPED_INSIDE_REDIS_INSTANCE_SERVICE, REDIS_INSTANCE_UNSUPPORTED_PORT, REDIS_INSTANCE_CONNECTING_FROM_PUPI_ADDRESS, REDIS_INSTANCE_NO_ROUTE_TO_DESTINATION_NETWORK, REDIS_INSTANCE_NO_EXTERNAL_IP, REDIS_INSTANCE_UNSUPPORTED_PROTOCOL, DROPPED_INSIDE_REDIS_CLUSTER_SERVICE, REDIS_CLUSTER_UNSUPPORTED_PORT, REDIS_CLUSTER_NO_EXTERNAL_IP, REDIS_CLUSTER_UNSUPPORTED_PROTOCOL, NO_ADVERTISED_ROUTE_TO_GCP_DESTINATION, NO_TRAFFIC_SELECTOR_TO_GCP_DESTINATION, NO_KNOWN_ROUTE_FROM_PEERED_NETWORK_TO_DESTINATION, PRIVATE_NAT_TO_PSC_ENDPOINT_UNSUPPORTED }
	}
	case class DropInfo(
	  /** Cause that the packet is dropped. */
		cause: Option[Schema.DropInfo.CauseEnum] = None,
	  /** URI of the resource that caused the drop. */
		resourceUri: Option[String] = None,
	  /** Source IP address of the dropped packet (if relevant). */
		sourceIp: Option[String] = None,
	  /** Destination IP address of the dropped packet (if relevant). */
		destinationIp: Option[String] = None,
	  /** Region of the dropped packet (if relevant). */
		region: Option[String] = None
	)
	
	object LoadBalancerInfo {
		enum LoadBalancerTypeEnum extends Enum[LoadBalancerTypeEnum] { case LOAD_BALANCER_TYPE_UNSPECIFIED, INTERNAL_TCP_UDP, NETWORK_TCP_UDP, HTTP_PROXY, TCP_PROXY, SSL_PROXY }
		enum BackendTypeEnum extends Enum[BackendTypeEnum] { case BACKEND_TYPE_UNSPECIFIED, BACKEND_SERVICE, TARGET_POOL, TARGET_INSTANCE }
	}
	case class LoadBalancerInfo(
	  /** Type of the load balancer. */
		loadBalancerType: Option[Schema.LoadBalancerInfo.LoadBalancerTypeEnum] = None,
	  /** URI of the health check for the load balancer. Deprecated and no longer populated as different load balancer backends might have different health checks. */
		healthCheckUri: Option[String] = None,
	  /** Information for the loadbalancer backends. */
		backends: Option[List[Schema.LoadBalancerBackend]] = None,
	  /** Type of load balancer's backend configuration. */
		backendType: Option[Schema.LoadBalancerInfo.BackendTypeEnum] = None,
	  /** Backend configuration URI. */
		backendUri: Option[String] = None
	)
	
	object LoadBalancerBackend {
		enum HealthCheckFirewallStateEnum extends Enum[HealthCheckFirewallStateEnum] { case HEALTH_CHECK_FIREWALL_STATE_UNSPECIFIED, CONFIGURED, MISCONFIGURED }
	}
	case class LoadBalancerBackend(
	  /** Name of a Compute Engine instance or network endpoint. */
		displayName: Option[String] = None,
	  /** URI of a Compute Engine instance or network endpoint. */
		uri: Option[String] = None,
	  /** State of the health check firewall configuration. */
		healthCheckFirewallState: Option[Schema.LoadBalancerBackend.HealthCheckFirewallStateEnum] = None,
	  /** A list of firewall rule URIs allowing probes from health check IP ranges. */
		healthCheckAllowingFirewallRules: Option[List[String]] = None,
	  /** A list of firewall rule URIs blocking probes from health check IP ranges. */
		healthCheckBlockingFirewallRules: Option[List[String]] = None
	)
	
	case class NetworkInfo(
	  /** Name of a Compute Engine network. */
		displayName: Option[String] = None,
	  /** URI of a Compute Engine network. */
		uri: Option[String] = None,
	  /** URI of the subnet matching the source IP address of the test. */
		matchedSubnetUri: Option[String] = None,
	  /** The IP range of the subnet matching the source IP address of the test. */
		matchedIpRange: Option[String] = None,
	  /** The region of the subnet matching the source IP address of the test. */
		region: Option[String] = None
	)
	
	case class GKEMasterInfo(
	  /** URI of a GKE cluster. */
		clusterUri: Option[String] = None,
	  /** URI of a GKE cluster network. */
		clusterNetworkUri: Option[String] = None,
	  /** Internal IP address of a GKE cluster control plane. */
		internalIp: Option[String] = None,
	  /** External IP address of a GKE cluster control plane. */
		externalIp: Option[String] = None,
	  /** DNS endpoint of a GKE cluster control plane. */
		dnsEndpoint: Option[String] = None
	)
	
	case class CloudSQLInstanceInfo(
	  /** Name of a Cloud SQL instance. */
		displayName: Option[String] = None,
	  /** URI of a Cloud SQL instance. */
		uri: Option[String] = None,
	  /** URI of a Cloud SQL instance network or empty string if the instance does not have one. */
		networkUri: Option[String] = None,
	  /** Internal IP address of a Cloud SQL instance. */
		internalIp: Option[String] = None,
	  /** External IP address of a Cloud SQL instance. */
		externalIp: Option[String] = None,
	  /** Region in which the Cloud SQL instance is running. */
		region: Option[String] = None
	)
	
	case class RedisInstanceInfo(
	  /** Name of a Cloud Redis Instance. */
		displayName: Option[String] = None,
	  /** URI of a Cloud Redis Instance. */
		uri: Option[String] = None,
	  /** URI of a Cloud Redis Instance network. */
		networkUri: Option[String] = None,
	  /** Primary endpoint IP address of a Cloud Redis Instance. */
		primaryEndpointIp: Option[String] = None,
	  /** Read endpoint IP address of a Cloud Redis Instance (if applicable). */
		readEndpointIp: Option[String] = None,
	  /** Region in which the Cloud Redis Instance is defined. */
		region: Option[String] = None
	)
	
	case class RedisClusterInfo(
	  /** Name of a Redis Cluster. */
		displayName: Option[String] = None,
	  /** URI of a Redis Cluster in format "projects/{project_id}/locations/{location}/clusters/{cluster_id}" */
		uri: Option[String] = None,
	  /** URI of a Redis Cluster network in format "projects/{project_id}/global/networks/{network_id}". */
		networkUri: Option[String] = None,
	  /** Discovery endpoint IP address of a Redis Cluster. */
		discoveryEndpointIpAddress: Option[String] = None,
	  /** Secondary endpoint IP address of a Redis Cluster. */
		secondaryEndpointIpAddress: Option[String] = None,
	  /** Name of the region in which the Redis Cluster is defined. For example, "us-central1". */
		location: Option[String] = None
	)
	
	case class CloudFunctionInfo(
	  /** Name of a Cloud Function. */
		displayName: Option[String] = None,
	  /** URI of a Cloud Function. */
		uri: Option[String] = None,
	  /** Location in which the Cloud Function is deployed. */
		location: Option[String] = None,
	  /** Latest successfully deployed version id of the Cloud Function. */
		versionId: Option[String] = None
	)
	
	case class AppEngineVersionInfo(
	  /** Name of an App Engine version. */
		displayName: Option[String] = None,
	  /** URI of an App Engine version. */
		uri: Option[String] = None,
	  /** Runtime of the App Engine version. */
		runtime: Option[String] = None,
	  /** App Engine execution environment for a version. */
		environment: Option[String] = None
	)
	
	case class CloudRunRevisionInfo(
	  /** Name of a Cloud Run revision. */
		displayName: Option[String] = None,
	  /** URI of a Cloud Run revision. */
		uri: Option[String] = None,
	  /** Location in which this revision is deployed. */
		location: Option[String] = None,
	  /** URI of Cloud Run service this revision belongs to. */
		serviceUri: Option[String] = None
	)
	
	object NatInfo {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, INTERNAL_TO_EXTERNAL, EXTERNAL_TO_INTERNAL, CLOUD_NAT, PRIVATE_SERVICE_CONNECT }
	}
	case class NatInfo(
	  /** Type of NAT. */
		`type`: Option[Schema.NatInfo.TypeEnum] = None,
	  /** IP protocol in string format, for example: "TCP", "UDP", "ICMP". */
		protocol: Option[String] = None,
	  /** URI of the network where NAT translation takes place. */
		networkUri: Option[String] = None,
	  /** Source IP address before NAT translation. */
		oldSourceIp: Option[String] = None,
	  /** Source IP address after NAT translation. */
		newSourceIp: Option[String] = None,
	  /** Destination IP address before NAT translation. */
		oldDestinationIp: Option[String] = None,
	  /** Destination IP address after NAT translation. */
		newDestinationIp: Option[String] = None,
	  /** Source port before NAT translation. Only valid when protocol is TCP or UDP. */
		oldSourcePort: Option[Int] = None,
	  /** Source port after NAT translation. Only valid when protocol is TCP or UDP. */
		newSourcePort: Option[Int] = None,
	  /** Destination port before NAT translation. Only valid when protocol is TCP or UDP. */
		oldDestinationPort: Option[Int] = None,
	  /** Destination port after NAT translation. Only valid when protocol is TCP or UDP. */
		newDestinationPort: Option[Int] = None,
	  /** Uri of the Cloud Router. Only valid when type is CLOUD_NAT. */
		routerUri: Option[String] = None,
	  /** The name of Cloud NAT Gateway. Only valid when type is CLOUD_NAT. */
		natGatewayName: Option[String] = None
	)
	
	case class ProxyConnectionInfo(
	  /** IP protocol in string format, for example: "TCP", "UDP", "ICMP". */
		protocol: Option[String] = None,
	  /** Source IP address of an original connection. */
		oldSourceIp: Option[String] = None,
	  /** Source IP address of a new connection. */
		newSourceIp: Option[String] = None,
	  /** Destination IP address of an original connection */
		oldDestinationIp: Option[String] = None,
	  /** Destination IP address of a new connection. */
		newDestinationIp: Option[String] = None,
	  /** Source port of an original connection. Only valid when protocol is TCP or UDP. */
		oldSourcePort: Option[Int] = None,
	  /** Source port of a new connection. Only valid when protocol is TCP or UDP. */
		newSourcePort: Option[Int] = None,
	  /** Destination port of an original connection. Only valid when protocol is TCP or UDP. */
		oldDestinationPort: Option[Int] = None,
	  /** Destination port of a new connection. Only valid when protocol is TCP or UDP. */
		newDestinationPort: Option[Int] = None,
	  /** Uri of proxy subnet. */
		subnetUri: Option[String] = None,
	  /** URI of the network where connection is proxied. */
		networkUri: Option[String] = None
	)
	
	object LoadBalancerBackendInfo {
		enum HealthCheckFirewallsConfigStateEnum extends Enum[HealthCheckFirewallsConfigStateEnum] { case HEALTH_CHECK_FIREWALLS_CONFIG_STATE_UNSPECIFIED, FIREWALLS_CONFIGURED, FIREWALLS_PARTIALLY_CONFIGURED, FIREWALLS_NOT_CONFIGURED, FIREWALLS_UNSUPPORTED }
	}
	case class LoadBalancerBackendInfo(
	  /** Display name of the backend. For example, it might be an instance name for the instance group backends, or an IP address and port for zonal network endpoint group backends. */
		name: Option[String] = None,
	  /** URI of the backend instance (if applicable). Populated for instance group backends, and zonal NEG backends. */
		instanceUri: Option[String] = None,
	  /** URI of the backend service this backend belongs to (if applicable). */
		backendServiceUri: Option[String] = None,
	  /** URI of the instance group this backend belongs to (if applicable). */
		instanceGroupUri: Option[String] = None,
	  /** URI of the network endpoint group this backend belongs to (if applicable). */
		networkEndpointGroupUri: Option[String] = None,
	  /** URI of the backend bucket this backend targets (if applicable). */
		backendBucketUri: Option[String] = None,
	  /** URI of the PSC service attachment this PSC NEG backend targets (if applicable). */
		pscServiceAttachmentUri: Option[String] = None,
	  /** PSC Google API target this PSC NEG backend targets (if applicable). */
		pscGoogleApiTarget: Option[String] = None,
	  /** URI of the health check attached to this backend (if applicable). */
		healthCheckUri: Option[String] = None,
	  /** Output only. Health check firewalls configuration state for the backend. This is a result of the static firewall analysis (verifying that health check traffic from required IP ranges to the backend is allowed or not). The backend might still be unhealthy even if these firewalls are configured. Please refer to the documentation for more information: https://cloud.google.com/load-balancing/docs/firewall-rules */
		healthCheckFirewallsConfigState: Option[Schema.LoadBalancerBackendInfo.HealthCheckFirewallsConfigStateEnum] = None
	)
	
	case class StorageBucketInfo(
	  /** Cloud Storage Bucket name. */
		bucket: Option[String] = None
	)
	
	case class ServerlessNegInfo(
	  /** URI of the serverless network endpoint group. */
		negUri: Option[String] = None
	)
	
	object ProbingDetails {
		enum ResultEnum extends Enum[ResultEnum] { case PROBING_RESULT_UNSPECIFIED, REACHABLE, UNREACHABLE, REACHABILITY_INCONSISTENT, UNDETERMINED }
		enum AbortCauseEnum extends Enum[AbortCauseEnum] { case PROBING_ABORT_CAUSE_UNSPECIFIED, PERMISSION_DENIED, NO_SOURCE_LOCATION }
	}
	case class ProbingDetails(
	  /** The overall result of active probing. */
		result: Option[Schema.ProbingDetails.ResultEnum] = None,
	  /** The time that reachability was assessed through active probing. */
		verifyTime: Option[String] = None,
	  /** Details about an internal failure or the cancellation of active probing. */
		error: Option[Schema.Status] = None,
	  /** The reason probing was aborted. */
		abortCause: Option[Schema.ProbingDetails.AbortCauseEnum] = None,
	  /** Number of probes sent. */
		sentProbeCount: Option[Int] = None,
	  /** Number of probes that reached the destination. */
		successfulProbeCount: Option[Int] = None,
	  /** The source and destination endpoints derived from the test input and used for active probing. */
		endpointInfo: Option[Schema.EndpointInfo] = None,
	  /** Latency as measured by active probing in one direction: from the source to the destination endpoint. */
		probingLatency: Option[Schema.LatencyDistribution] = None,
	  /** The EdgeLocation from which a packet destined for/originating from the internet will egress/ingress the Google network. This will only be populated for a connectivity test which has an internet destination/source address. The absence of this field &#42;must not&#42; be used as an indication that the destination/source is part of the Google network. */
		destinationEgressLocation: Option[Schema.EdgeLocation] = None
	)
	
	case class LatencyDistribution(
	  /** Representative latency percentiles. */
		latencyPercentiles: Option[List[Schema.LatencyPercentile]] = None
	)
	
	case class LatencyPercentile(
	  /** Percentage of samples this data point applies to. */
		percent: Option[Int] = None,
	  /** percent-th percentile of latency observed, in microseconds. Fraction of percent/100 of samples have latency lower or equal to the value of this field. */
		latencyMicros: Option[String] = None
	)
	
	case class EdgeLocation(
	  /** Name of the metropolitan area. */
		metropolitanArea: Option[String] = None
	)
	
	case class RerunConnectivityTestRequest(
	
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
	  /** The time the operation was created. */
		createTime: Option[String] = None,
	  /** The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Target of the operation - for example projects/project-1/locations/global/connectivityTests/test-1 */
		target: Option[String] = None,
	  /** Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Human-readable status of the operation, if any. */
		statusDetail: Option[String] = None,
	  /** Specifies if cancellation was requested for the operation. */
		cancelRequested: Option[Boolean] = None,
	  /** API version. */
		apiVersion: Option[String] = None
	)
}
