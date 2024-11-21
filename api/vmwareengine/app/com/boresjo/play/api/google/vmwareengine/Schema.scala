package com.boresjo.play.api.google.vmwareengine

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

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
	
	case class ListPrivateCloudsResponse(
	  /** A list of private clouds. */
		privateClouds: Option[List[Schema.PrivateCloud]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached when making an aggregated query using wildcards. */
		unreachable: Option[List[String]] = None
	)
	
	object PrivateCloud {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, CREATING, UPDATING, FAILED, DELETED, PURGING }
		enum TypeEnum extends Enum[TypeEnum] { case STANDARD, TIME_LIMITED, STRETCHED }
	}
	case class PrivateCloud(
	  /** Output only. Identifier. The resource name of this private cloud. Resource names are schemeless URIs that follow the conventions in https://cloud.google.com/apis/design/resource_names. For example: `projects/my-project/locations/us-central1-a/privateClouds/my-cloud` */
		name: Option[String] = None,
	  /** Output only. Creation time of this resource. */
		createTime: Option[String] = None,
	  /** Output only. Last update time of this resource. */
		updateTime: Option[String] = None,
	  /** Output only. Time when the resource was scheduled for deletion. */
		deleteTime: Option[String] = None,
	  /** Output only. Time when the resource will be irreversibly deleted. */
		expireTime: Option[String] = None,
	  /** Output only. State of the resource. New values may be added to this enum when appropriate. */
		state: Option[Schema.PrivateCloud.StateEnum] = None,
	  /** Required. Network configuration of the private cloud. */
		networkConfig: Option[Schema.NetworkConfig] = None,
	  /** Required. Input only. The management cluster for this private cloud. This field is required during creation of the private cloud to provide details for the default cluster. The following fields can't be changed after private cloud creation: `ManagementCluster.clusterId`, `ManagementCluster.nodeTypeId`. */
		managementCluster: Option[Schema.ManagementCluster] = None,
	  /** User-provided description for this private cloud. */
		description: Option[String] = None,
	  /** Output only. HCX appliance. */
		hcx: Option[Schema.Hcx] = None,
	  /** Output only. NSX appliance. */
		nsx: Option[Schema.Nsx] = None,
	  /** Output only. Vcenter appliance. */
		vcenter: Option[Schema.Vcenter] = None,
	  /** Output only. System-generated unique identifier for the resource. */
		uid: Option[String] = None,
	  /** Optional. Type of the private cloud. Defaults to STANDARD. */
		`type`: Option[Schema.PrivateCloud.TypeEnum] = None
	)
	
	case class NetworkConfig(
	  /** Required. Management CIDR used by VMware management appliances. */
		managementCidr: Option[String] = None,
	  /** Optional. The relative resource name of the VMware Engine network attached to the private cloud. Specify the name in the following form: `projects/{project}/locations/{location}/vmwareEngineNetworks/{vmware_engine_network_id}` where `{project}` can either be a project number or a project ID. */
		vmwareEngineNetwork: Option[String] = None,
	  /** Output only. The canonical name of the VMware Engine network in the form: `projects/{project_number}/locations/{location}/vmwareEngineNetworks/{vmware_engine_network_id}` */
		vmwareEngineNetworkCanonical: Option[String] = None,
	  /** Output only. The IP address layout version of the management IP address range. Possible versions include: &#42; `managementIpAddressLayoutVersion=1`: Indicates the legacy IP address layout used by some existing private clouds. This is no longer supported for new private clouds as it does not support all features. &#42; `managementIpAddressLayoutVersion=2`: Indicates the latest IP address layout used by all newly created private clouds. This version supports all current features. */
		managementIpAddressLayoutVersion: Option[Int] = None,
	  /** Output only. DNS Server IP of the Private Cloud. All DNS queries can be forwarded to this address for name resolution of Private Cloud's management entities like vCenter, NSX-T Manager and ESXi hosts. */
		dnsServerIp: Option[String] = None
	)
	
	case class ManagementCluster(
	  /** Required. The user-provided identifier of the new `Cluster`. The identifier must meet the following requirements: &#42; Only contains 1-63 alphanumeric characters and hyphens &#42; Begins with an alphabetical character &#42; Ends with a non-hyphen character &#42; Not formatted as a UUID &#42; Complies with [RFC 1034](https://datatracker.ietf.org/doc/html/rfc1034) (section 3.5) */
		clusterId: Option[String] = None,
	  /** Required. The map of cluster node types in this cluster, where the key is canonical identifier of the node type (corresponds to the `NodeType`). */
		nodeTypeConfigs: Option[Map[String, Schema.NodeTypeConfig]] = None,
	  /** Optional. Configuration of a stretched cluster. Required for STRETCHED private clouds. */
		stretchedClusterConfig: Option[Schema.StretchedClusterConfig] = None
	)
	
	case class NodeTypeConfig(
	  /** Required. The number of nodes of this type in the cluster */
		nodeCount: Option[Int] = None,
	  /** Optional. Customized number of cores available to each node of the type. This number must always be one of `nodeType.availableCustomCoreCounts`. If zero is provided max value from `nodeType.availableCustomCoreCounts` will be used. */
		customCoreCount: Option[Int] = None
	)
	
	case class StretchedClusterConfig(
	  /** Required. Zone that will remain operational when connection between the two zones is lost. Specify the resource name of a zone that belongs to the region of the private cloud. For example: `projects/{project}/locations/europe-west3-a` where `{project}` can either be a project number or a project ID. */
		preferredLocation: Option[String] = None,
	  /** Required. Additional zone for a higher level of availability and load balancing. Specify the resource name of a zone that belongs to the region of the private cloud. For example: `projects/{project}/locations/europe-west3-b` where `{project}` can either be a project number or a project ID. */
		secondaryLocation: Option[String] = None
	)
	
	object Hcx {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, CREATING, ACTIVATING }
	}
	case class Hcx(
	  /** Internal IP address of the appliance. */
		internalIp: Option[String] = None,
	  /** Version of the appliance. */
		version: Option[String] = None,
	  /** Output only. The state of the appliance. */
		state: Option[Schema.Hcx.StateEnum] = None,
	  /** Fully qualified domain name of the appliance. */
		fqdn: Option[String] = None
	)
	
	object Nsx {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, CREATING }
	}
	case class Nsx(
	  /** Internal IP address of the appliance. */
		internalIp: Option[String] = None,
	  /** Version of the appliance. */
		version: Option[String] = None,
	  /** Output only. The state of the appliance. */
		state: Option[Schema.Nsx.StateEnum] = None,
	  /** Fully qualified domain name of the appliance. */
		fqdn: Option[String] = None
	)
	
	object Vcenter {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, CREATING }
	}
	case class Vcenter(
	  /** Internal IP address of the appliance. */
		internalIp: Option[String] = None,
	  /** Version of the appliance. */
		version: Option[String] = None,
	  /** Output only. The state of the appliance. */
		state: Option[Schema.Vcenter.StateEnum] = None,
	  /** Fully qualified domain name of the appliance. */
		fqdn: Option[String] = None
	)
	
	case class UndeletePrivateCloudRequest(
	  /** Optional. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	case class ListClustersResponse(
	  /** A list of private cloud clusters. */
		clusters: Option[List[Schema.Cluster]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached when making an aggregated query using wildcards. */
		unreachable: Option[List[String]] = None
	)
	
	object Cluster {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, CREATING, UPDATING, DELETING, REPAIRING }
	}
	case class Cluster(
	  /** Output only. Identifier. The resource name of this cluster. Resource names are schemeless URIs that follow the conventions in https://cloud.google.com/apis/design/resource_names. For example: `projects/my-project/locations/us-central1-a/privateClouds/my-cloud/clusters/my-cluster` */
		name: Option[String] = None,
	  /** Output only. Creation time of this resource. */
		createTime: Option[String] = None,
	  /** Output only. Last update time of this resource. */
		updateTime: Option[String] = None,
	  /** Output only. State of the resource. */
		state: Option[Schema.Cluster.StateEnum] = None,
	  /** Output only. True if the cluster is a management cluster; false otherwise. There can only be one management cluster in a private cloud and it has to be the first one. */
		management: Option[Boolean] = None,
	  /** Optional. Configuration of the autoscaling applied to this cluster. */
		autoscalingSettings: Option[Schema.AutoscalingSettings] = None,
	  /** Output only. System-generated unique identifier for the resource. */
		uid: Option[String] = None,
	  /** Required. The map of cluster node types in this cluster, where the key is canonical identifier of the node type (corresponds to the `NodeType`). */
		nodeTypeConfigs: Option[Map[String, Schema.NodeTypeConfig]] = None,
	  /** Optional. Configuration of a stretched cluster. Required for clusters that belong to a STRETCHED private cloud. */
		stretchedClusterConfig: Option[Schema.StretchedClusterConfig] = None
	)
	
	case class AutoscalingSettings(
	  /** Required. The map with autoscaling policies applied to the cluster. The key is the identifier of the policy. It must meet the following requirements: &#42; Only contains 1-63 alphanumeric characters and hyphens &#42; Begins with an alphabetical character &#42; Ends with a non-hyphen character &#42; Not formatted as a UUID &#42; Complies with [RFC 1034](https://datatracker.ietf.org/doc/html/rfc1034) (section 3.5) Currently there map must contain only one element that describes the autoscaling policy for compute nodes. */
		autoscalingPolicies: Option[Map[String, Schema.AutoscalingPolicy]] = None,
	  /** Optional. Minimum number of nodes of any type in a cluster. If not specified the default limits apply. */
		minClusterNodeCount: Option[Int] = None,
	  /** Optional. Maximum number of nodes of any type in a cluster. If not specified the default limits apply. */
		maxClusterNodeCount: Option[Int] = None,
	  /** Optional. The minimum duration between consecutive autoscale operations. It starts once addition or removal of nodes is fully completed. Defaults to 30 minutes if not specified. Cool down period must be in whole minutes (for example, 30, 31, 50, 180 minutes). */
		coolDownPeriod: Option[String] = None
	)
	
	case class AutoscalingPolicy(
	  /** Required. The canonical identifier of the node type to add or remove. Corresponds to the `NodeType`. */
		nodeTypeId: Option[String] = None,
	  /** Required. Number of nodes to add to a cluster during a scale-out operation. Must be divisible by 2 for stretched clusters. During a scale-in operation only one node (or 2 for stretched clusters) are removed in a single iteration. */
		scaleOutSize: Option[Int] = None,
	  /** Optional. Utilization thresholds pertaining to CPU utilization. */
		cpuThresholds: Option[Schema.Thresholds] = None,
	  /** Optional. Utilization thresholds pertaining to amount of granted memory. */
		grantedMemoryThresholds: Option[Schema.Thresholds] = None,
	  /** Optional. Utilization thresholds pertaining to amount of consumed memory. */
		consumedMemoryThresholds: Option[Schema.Thresholds] = None,
	  /** Optional. Utilization thresholds pertaining to amount of consumed storage. */
		storageThresholds: Option[Schema.Thresholds] = None
	)
	
	case class Thresholds(
	  /** Required. The utilization triggering the scale-out operation in percent. */
		scaleOut: Option[Int] = None,
	  /** Required. The utilization triggering the scale-in operation in percent. */
		scaleIn: Option[Int] = None
	)
	
	case class ListNodesResponse(
	  /** The nodes. */
		nodes: Option[List[Schema.Node]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object Node {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, CREATING, FAILED, UPGRADING }
	}
	case class Node(
	  /** Output only. The resource name of this node. Resource names are schemeless URIs that follow the conventions in https://cloud.google.com/apis/design/resource_names. For example: projects/my-project/locations/us-central1-a/privateClouds/my-cloud/clusters/my-cluster/nodes/my-node */
		name: Option[String] = None,
	  /** Output only. Fully qualified domain name of the node. */
		fqdn: Option[String] = None,
	  /** Output only. Internal IP address of the node. */
		internalIp: Option[String] = None,
	  /** Output only. The canonical identifier of the node type (corresponds to the `NodeType`). For example: standard-72. */
		nodeTypeId: Option[String] = None,
	  /** Output only. The version number of the VMware ESXi management component in this cluster. */
		version: Option[String] = None,
	  /** Output only. Customized number of cores */
		customCoreCount: Option[String] = None,
	  /** Output only. The state of the appliance. */
		state: Option[Schema.Node.StateEnum] = None
	)
	
	case class ListExternalAddressesResponse(
	  /** A list of external IP addresses. */
		externalAddresses: Option[List[Schema.ExternalAddress]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached when making an aggregated query using wildcards. */
		unreachable: Option[List[String]] = None
	)
	
	object ExternalAddress {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, CREATING, UPDATING, DELETING }
	}
	case class ExternalAddress(
	  /** Output only. Identifier. The resource name of this external IP address. Resource names are schemeless URIs that follow the conventions in https://cloud.google.com/apis/design/resource_names. For example: `projects/my-project/locations/us-central1-a/privateClouds/my-cloud/externalAddresses/my-address` */
		name: Option[String] = None,
	  /** Output only. Creation time of this resource. */
		createTime: Option[String] = None,
	  /** Output only. Last update time of this resource. */
		updateTime: Option[String] = None,
	  /** The internal IP address of a workload VM. */
		internalIp: Option[String] = None,
	  /** Output only. The external IP address of a workload VM. */
		externalIp: Option[String] = None,
	  /** Output only. The state of the resource. */
		state: Option[Schema.ExternalAddress.StateEnum] = None,
	  /** Output only. System-generated unique identifier for the resource. */
		uid: Option[String] = None,
	  /** User-provided description for this resource. */
		description: Option[String] = None
	)
	
	case class FetchNetworkPolicyExternalAddressesResponse(
	  /** A list of external IP addresses assigned to VMware workload VMs within the scope of the given network policy. */
		externalAddresses: Option[List[Schema.ExternalAddress]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class ListSubnetsResponse(
	  /** A list of subnets. */
		subnets: Option[List[Schema.Subnet]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached when making an aggregated query using wildcards. */
		unreachable: Option[List[String]] = None
	)
	
	object Subnet {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, CREATING, UPDATING, DELETING, RECONCILING, FAILED }
	}
	case class Subnet(
	  /** Output only. Identifier. The resource name of this subnet. Resource names are schemeless URIs that follow the conventions in https://cloud.google.com/apis/design/resource_names. For example: `projects/my-project/locations/us-central1-a/privateClouds/my-cloud/subnets/my-subnet` */
		name: Option[String] = None,
	  /** The IP address range of the subnet in CIDR format '10.0.0.0/24'. */
		ipCidrRange: Option[String] = None,
	  /** The IP address of the gateway of this subnet. Must fall within the IP prefix defined above. */
		gatewayIp: Option[String] = None,
	  /** Output only. The type of the subnet. For example "management" or "userDefined". */
		`type`: Option[String] = None,
	  /** Output only. The state of the resource. */
		state: Option[Schema.Subnet.StateEnum] = None,
	  /** Output only. VLAN ID of the VLAN on which the subnet is configured */
		vlanId: Option[Int] = None
	)
	
	case class ListExternalAccessRulesResponse(
	  /** A list of external access firewall rules. */
		externalAccessRules: Option[List[Schema.ExternalAccessRule]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached when making an aggregated query using wildcards. */
		unreachable: Option[List[String]] = None
	)
	
	object ExternalAccessRule {
		enum ActionEnum extends Enum[ActionEnum] { case ACTION_UNSPECIFIED, ALLOW, DENY }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, CREATING, UPDATING, DELETING }
	}
	case class ExternalAccessRule(
	  /** Output only. The resource name of this external access rule. Resource names are schemeless URIs that follow the conventions in https://cloud.google.com/apis/design/resource_names. For example: `projects/my-project/locations/us-central1/networkPolicies/my-policy/externalAccessRules/my-rule` */
		name: Option[String] = None,
	  /** Output only. Creation time of this resource. */
		createTime: Option[String] = None,
	  /** Output only. Last update time of this resource. */
		updateTime: Option[String] = None,
	  /** User-provided description for this external access rule. */
		description: Option[String] = None,
	  /** External access rule priority, which determines the external access rule to use when multiple rules apply. If multiple rules have the same priority, their ordering is non-deterministic. If specific ordering is required, assign unique priorities to enforce such ordering. The external access rule priority is an integer from 100 to 4096, both inclusive. Lower integers indicate higher precedence. For example, a rule with priority `100` has higher precedence than a rule with priority `101`. */
		priority: Option[Int] = None,
	  /** The action that the external access rule performs. */
		action: Option[Schema.ExternalAccessRule.ActionEnum] = None,
	  /** The IP protocol to which the external access rule applies. This value can be one of the following three protocol strings (not case-sensitive): `tcp`, `udp`, or `icmp`. */
		ipProtocol: Option[String] = None,
	  /** If source ranges are specified, the external access rule applies only to traffic that has a source IP address in these ranges. These ranges can either be expressed in the CIDR format or as an IP address. As only inbound rules are supported, `ExternalAddress` resources cannot be the source IP addresses of an external access rule. To match all source addresses, specify `0.0.0.0/0`. */
		sourceIpRanges: Option[List[Schema.IpRange]] = None,
	  /** A list of source ports to which the external access rule applies. This field is only applicable for the UDP or TCP protocol. Each entry must be either an integer or a range. For example: `["22"]`, `["80","443"]`, or `["12345-12349"]`. To match all source ports, specify `["0-65535"]`. */
		sourcePorts: Option[List[String]] = None,
	  /** If destination ranges are specified, the external access rule applies only to the traffic that has a destination IP address in these ranges. The specified IP addresses must have reserved external IP addresses in the scope of the parent network policy. To match all external IP addresses in the scope of the parent network policy, specify `0.0.0.0/0`. To match a specific external IP address, specify it using the `IpRange.external_address` property. */
		destinationIpRanges: Option[List[Schema.IpRange]] = None,
	  /** A list of destination ports to which the external access rule applies. This field is only applicable for the UDP or TCP protocol. Each entry must be either an integer or a range. For example: `["22"]`, `["80","443"]`, or `["12345-12349"]`. To match all destination ports, specify `["0-65535"]`. */
		destinationPorts: Option[List[String]] = None,
	  /** Output only. The state of the resource. */
		state: Option[Schema.ExternalAccessRule.StateEnum] = None,
	  /** Output only. System-generated unique identifier for the resource. */
		uid: Option[String] = None
	)
	
	case class IpRange(
	  /** A single IP address. For example: `10.0.0.5`. */
		ipAddress: Option[String] = None,
	  /** An IP address range in the CIDR format. For example: `10.0.0.0/24`. */
		ipAddressRange: Option[String] = None,
	  /** The name of an `ExternalAddress` resource. The external address must have been reserved in the scope of this external access rule's parent network policy. Provide the external address name in the form of `projects/{project}/locations/{location}/privateClouds/{private_cloud}/externalAddresses/{external_address}`. For example: `projects/my-project/locations/us-central1-a/privateClouds/my-cloud/externalAddresses/my-address`. */
		externalAddress: Option[String] = None
	)
	
	case class ListLoggingServersResponse(
	  /** A list of Logging Servers. */
		loggingServers: Option[List[Schema.LoggingServer]] = None,
	  /** A token, which can be send as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached when making an aggregated query using wildcards. */
		unreachable: Option[List[String]] = None
	)
	
	object LoggingServer {
		enum ProtocolEnum extends Enum[ProtocolEnum] { case PROTOCOL_UNSPECIFIED, UDP, TCP, TLS, SSL, RELP }
		enum SourceTypeEnum extends Enum[SourceTypeEnum] { case SOURCE_TYPE_UNSPECIFIED, ESXI, VCSA }
	}
	case class LoggingServer(
	  /** Output only. The resource name of this logging server. Resource names are schemeless URIs that follow the conventions in https://cloud.google.com/apis/design/resource_names. For example: `projects/my-project/locations/us-central1-a/privateClouds/my-cloud/loggingServers/my-logging-server` */
		name: Option[String] = None,
	  /** Output only. Creation time of this resource. */
		createTime: Option[String] = None,
	  /** Output only. Last update time of this resource. */
		updateTime: Option[String] = None,
	  /** Required. Fully-qualified domain name (FQDN) or IP Address of the logging server. */
		hostname: Option[String] = None,
	  /** Required. Port number at which the logging server receives logs. */
		port: Option[Int] = None,
	  /** Required. Protocol used by vCenter to send logs to a logging server. */
		protocol: Option[Schema.LoggingServer.ProtocolEnum] = None,
	  /** Required. The type of component that produces logs that will be forwarded to this logging server. */
		sourceType: Option[Schema.LoggingServer.SourceTypeEnum] = None,
	  /** Output only. System-generated unique identifier for the resource. */
		uid: Option[String] = None
	)
	
	case class ListNodeTypesResponse(
	  /** A list of Node Types. */
		nodeTypes: Option[List[Schema.NodeType]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached when making an aggregated query using wildcards. */
		unreachable: Option[List[String]] = None
	)
	
	object NodeType {
		enum KindEnum extends Enum[KindEnum] { case KIND_UNSPECIFIED, STANDARD, STORAGE_ONLY }
		enum CapabilitiesEnum extends Enum[CapabilitiesEnum] { case CAPABILITY_UNSPECIFIED, STRETCHED_CLUSTERS }
	}
	case class NodeType(
	  /** Output only. The resource name of this node type. Resource names are schemeless URIs that follow the conventions in https://cloud.google.com/apis/design/resource_names. For example: `projects/my-proj/locations/us-central1-a/nodeTypes/standard-72` */
		name: Option[String] = None,
	  /** Output only. The canonical identifier of the node type (corresponds to the `NodeType`). For example: standard-72. */
		nodeTypeId: Option[String] = None,
	  /** Output only. The friendly name for this node type. For example: ve1-standard-72 */
		displayName: Option[String] = None,
	  /** Output only. The total number of virtual CPUs in a single node. */
		virtualCpuCount: Option[Int] = None,
	  /** Output only. The total number of CPU cores in a single node. */
		totalCoreCount: Option[Int] = None,
	  /** Output only. The amount of physical memory available, defined in GB. */
		memoryGb: Option[Int] = None,
	  /** Output only. The amount of storage available, defined in GB. */
		diskSizeGb: Option[Int] = None,
	  /** Output only. List of possible values of custom core count. */
		availableCustomCoreCounts: Option[List[Int]] = None,
	  /** Output only. The type of the resource. */
		kind: Option[Schema.NodeType.KindEnum] = None,
	  /** Output only. Families of the node type. For node types to be in the same cluster they must share at least one element in the `families`. */
		families: Option[List[String]] = None,
	  /** Output only. Capabilities of this node type. */
		capabilities: Option[List[Schema.NodeType.CapabilitiesEnum]] = None
	)
	
	case class Credentials(
	  /** Initial username. */
		username: Option[String] = None,
	  /** Initial password. */
		password: Option[String] = None
	)
	
	case class ResetNsxCredentialsRequest(
	  /** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server guarantees that a request doesn't result in creation of duplicate commitments for at least 60 minutes. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	case class ResetVcenterCredentialsRequest(
	  /** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server guarantees that a request doesn't result in creation of duplicate commitments for at least 60 minutes. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None,
	  /** Optional. The username of the user to be to reset the credentials. The default value of this field is CloudOwner@gve.local. The provided value should be one of the following: solution-user-01@gve.local, solution-user-02@gve.local, solution-user-03@gve.local, solution-user-04@gve.local, solution-user-05@gve.local, zertoadmin@gve.local. */
		username: Option[String] = None
	)
	
	case class DnsForwarding(
	  /** Output only. Identifier. The resource name of this DNS profile. Resource names are schemeless URIs that follow the conventions in https://cloud.google.com/apis/design/resource_names. For example: `projects/my-project/locations/us-central1-a/privateClouds/my-cloud/dnsForwarding` */
		name: Option[String] = None,
	  /** Output only. Creation time of this resource. */
		createTime: Option[String] = None,
	  /** Output only. Last update time of this resource. */
		updateTime: Option[String] = None,
	  /** Required. List of domain mappings to configure */
		forwardingRules: Option[List[Schema.ForwardingRule]] = None
	)
	
	case class ForwardingRule(
	  /** Required. Domain used to resolve a `name_servers` list. */
		domain: Option[String] = None,
	  /** Required. List of DNS servers to use for domain resolution */
		nameServers: Option[List[String]] = None
	)
	
	object NetworkPeering {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, INACTIVE, ACTIVE, CREATING, DELETING }
		enum PeerNetworkTypeEnum extends Enum[PeerNetworkTypeEnum] { case PEER_NETWORK_TYPE_UNSPECIFIED, STANDARD, VMWARE_ENGINE_NETWORK, PRIVATE_SERVICES_ACCESS, NETAPP_CLOUD_VOLUMES, THIRD_PARTY_SERVICE, DELL_POWERSCALE, GOOGLE_CLOUD_NETAPP_VOLUMES }
	}
	case class NetworkPeering(
	  /** Output only. Identifier. The resource name of the network peering. NetworkPeering is a global resource and location can only be global. Resource names are scheme-less URIs that follow the conventions in https://cloud.google.com/apis/design/resource_names. For example: `projects/my-project/locations/global/networkPeerings/my-peering` */
		name: Option[String] = None,
	  /** Output only. Creation time of this resource. */
		createTime: Option[String] = None,
	  /** Output only. Last update time of this resource. */
		updateTime: Option[String] = None,
	  /** Required. The relative resource name of the network to peer with a standard VMware Engine network. The provided network can be a consumer VPC network or another standard VMware Engine network. If the `peer_network_type` is VMWARE_ENGINE_NETWORK, specify the name in the form: `projects/{project}/locations/global/vmwareEngineNetworks/{vmware_engine_network_id}`. Otherwise specify the name in the form: `projects/{project}/global/networks/{network_id}`, where `{project}` can either be a project number or a project ID. */
		peerNetwork: Option[String] = None,
	  /** Optional. True if custom routes are exported to the peered network; false otherwise. The default value is true. */
		exportCustomRoutes: Option[Boolean] = None,
	  /** Optional. True if custom routes are imported from the peered network; false otherwise. The default value is true. */
		importCustomRoutes: Option[Boolean] = None,
	  /** Optional. True if full mesh connectivity is created and managed automatically between peered networks; false otherwise. Currently this field is always true because Google Compute Engine automatically creates and manages subnetwork routes between two VPC networks when peering state is 'ACTIVE'. */
		exchangeSubnetRoutes: Option[Boolean] = None,
	  /** Optional. True if all subnet routes with a public IP address range are exported; false otherwise. The default value is true. IPv4 special-use ranges (https://en.wikipedia.org/wiki/IPv4#Special_addresses) are always exported to peers and are not controlled by this field. */
		exportCustomRoutesWithPublicIp: Option[Boolean] = None,
	  /** Optional. True if all subnet routes with public IP address range are imported; false otherwise. The default value is true. IPv4 special-use ranges (https://en.wikipedia.org/wiki/IPv4#Special_addresses) are always imported to peers and are not controlled by this field. */
		importCustomRoutesWithPublicIp: Option[Boolean] = None,
	  /** Output only. State of the network peering. This field has a value of 'ACTIVE' when there's a matching configuration in the peer network. New values may be added to this enum when appropriate. */
		state: Option[Schema.NetworkPeering.StateEnum] = None,
	  /** Output only. Output Only. Details about the current state of the network peering. */
		stateDetails: Option[String] = None,
	  /** Optional. Maximum transmission unit (MTU) in bytes. The default value is `1500`. If a value of `0` is provided for this field, VMware Engine uses the default value instead. */
		peerMtu: Option[Int] = None,
	  /** Required. The type of the network to peer with the VMware Engine network. */
		peerNetworkType: Option[Schema.NetworkPeering.PeerNetworkTypeEnum] = None,
	  /** Output only. System-generated unique identifier for the resource. */
		uid: Option[String] = None,
	  /** Required. The relative resource name of the VMware Engine network. Specify the name in the following form: `projects/{project}/locations/{location}/vmwareEngineNetworks/{vmware_engine_network_id}` where `{project}` can either be a project number or a project ID. */
		vmwareEngineNetwork: Option[String] = None,
	  /** Optional. User-provided description for this network peering. */
		description: Option[String] = None
	)
	
	case class ListNetworkPeeringsResponse(
	  /** A list of network peerings. */
		networkPeerings: Option[List[Schema.NetworkPeering]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Unreachable resources. */
		unreachable: Option[List[String]] = None
	)
	
	case class ListPeeringRoutesResponse(
	  /** A list of peering routes. */
		peeringRoutes: Option[List[Schema.PeeringRoute]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object PeeringRoute {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, DYNAMIC_PEERING_ROUTE, STATIC_PEERING_ROUTE, SUBNET_PEERING_ROUTE }
		enum DirectionEnum extends Enum[DirectionEnum] { case DIRECTION_UNSPECIFIED, INCOMING, OUTGOING }
	}
	case class PeeringRoute(
	  /** Output only. Destination range of the peering route in CIDR notation. */
		destRange: Option[String] = None,
	  /** Output only. Type of the route in the peer VPC network. */
		`type`: Option[Schema.PeeringRoute.TypeEnum] = None,
	  /** Output only. Region containing the next hop of the peering route. This field only applies to dynamic routes in the peer VPC network. */
		nextHopRegion: Option[String] = None,
	  /** Output only. The priority of the peering route. */
		priority: Option[String] = None,
	  /** Output only. True if the peering route has been imported from a peered VPC network; false otherwise. The import happens if the field `NetworkPeering.importCustomRoutes` is true for this network, `NetworkPeering.exportCustomRoutes` is true for the peer VPC network, and the import does not result in a route conflict. */
		imported: Option[Boolean] = None,
	  /** Output only. Direction of the routes exchanged with the peer network, from the VMware Engine network perspective: &#42; Routes of direction `INCOMING` are imported from the peer network. &#42; Routes of direction `OUTGOING` are exported from the intranet VPC network of the VMware Engine network. */
		direction: Option[Schema.PeeringRoute.DirectionEnum] = None
	)
	
	object HcxActivationKey {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, AVAILABLE, CONSUMED, CREATING }
	}
	case class HcxActivationKey(
	  /** Output only. The resource name of this HcxActivationKey. Resource names are schemeless URIs that follow the conventions in https://cloud.google.com/apis/design/resource_names. For example: `projects/my-project/locations/us-central1/privateClouds/my-cloud/hcxActivationKeys/my-key` */
		name: Option[String] = None,
	  /** Output only. Creation time of HCX activation key. */
		createTime: Option[String] = None,
	  /** Output only. State of HCX activation key. */
		state: Option[Schema.HcxActivationKey.StateEnum] = None,
	  /** Output only. HCX activation key. */
		activationKey: Option[String] = None,
	  /** Output only. System-generated unique identifier for the resource. */
		uid: Option[String] = None
	)
	
	case class ListHcxActivationKeysResponse(
	  /** List of HCX activation keys. */
		hcxActivationKeys: Option[List[Schema.HcxActivationKey]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached when making an aggregated query using wildcards. */
		unreachable: Option[List[String]] = None
	)
	
	case class NetworkPolicy(
	  /** Output only. Identifier. The resource name of this network policy. Resource names are schemeless URIs that follow the conventions in https://cloud.google.com/apis/design/resource_names. For example: `projects/my-project/locations/us-central1/networkPolicies/my-network-policy` */
		name: Option[String] = None,
	  /** Output only. Creation time of this resource. */
		createTime: Option[String] = None,
	  /** Output only. Last update time of this resource. */
		updateTime: Option[String] = None,
	  /** Network service that allows VMware workloads to access the internet. */
		internetAccess: Option[Schema.NetworkService] = None,
	  /** Network service that allows External IP addresses to be assigned to VMware workloads. This service can only be enabled when `internet_access` is also enabled. */
		externalIp: Option[Schema.NetworkService] = None,
	  /** Required. IP address range in CIDR notation used to create internet access and external IP access. An RFC 1918 CIDR block, with a "/26" prefix, is required. The range cannot overlap with any prefixes either in the consumer VPC network or in use by the private clouds attached to that VPC network. */
		edgeServicesCidr: Option[String] = None,
	  /** Output only. System-generated unique identifier for the resource. */
		uid: Option[String] = None,
	  /** Optional. The relative resource name of the VMware Engine network. Specify the name in the following form: `projects/{project}/locations/{location}/vmwareEngineNetworks/{vmware_engine_network_id}` where `{project}` can either be a project number or a project ID. */
		vmwareEngineNetwork: Option[String] = None,
	  /** Optional. User-provided description for this network policy. */
		description: Option[String] = None,
	  /** Output only. The canonical name of the VMware Engine network in the form: `projects/{project_number}/locations/{location}/vmwareEngineNetworks/{vmware_engine_network_id}` */
		vmwareEngineNetworkCanonical: Option[String] = None
	)
	
	object NetworkService {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, UNPROVISIONED, RECONCILING, ACTIVE }
	}
	case class NetworkService(
	  /** True if the service is enabled; false otherwise. */
		enabled: Option[Boolean] = None,
	  /** Output only. State of the service. New values may be added to this enum when appropriate. */
		state: Option[Schema.NetworkService.StateEnum] = None
	)
	
	case class ListNetworkPoliciesResponse(
	  /** A list of network policies. */
		networkPolicies: Option[List[Schema.NetworkPolicy]] = None,
	  /** A token, which can be send as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached when making an aggregated query using wildcards. */
		unreachable: Option[List[String]] = None
	)
	
	case class ListManagementDnsZoneBindingsResponse(
	  /** A list of management DNS zone bindings. */
		managementDnsZoneBindings: Option[List[Schema.ManagementDnsZoneBinding]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached when making an aggregated query using wildcards. */
		unreachable: Option[List[String]] = None
	)
	
	object ManagementDnsZoneBinding {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, CREATING, UPDATING, DELETING, FAILED }
	}
	case class ManagementDnsZoneBinding(
	  /** Output only. The resource name of this binding. Resource names are schemeless URIs that follow the conventions in https://cloud.google.com/apis/design/resource_names. For example: `projects/my-project/locations/us-central1-a/privateClouds/my-cloud/managementDnsZoneBindings/my-management-dns-zone-binding` */
		name: Option[String] = None,
	  /** Output only. Creation time of this resource. */
		createTime: Option[String] = None,
	  /** Output only. Last update time of this resource. */
		updateTime: Option[String] = None,
	  /** Output only. The state of the resource. */
		state: Option[Schema.ManagementDnsZoneBinding.StateEnum] = None,
	  /** User-provided description for this resource. */
		description: Option[String] = None,
	  /** Network to bind is a standard consumer VPC. Specify the name in the following form for consumer VPC network: `projects/{project}/global/networks/{network_id}`. `{project}` can either be a project number or a project ID. */
		vpcNetwork: Option[String] = None,
	  /** Network to bind is a VMware Engine network. Specify the name in the following form for VMware engine network: `projects/{project}/locations/global/vmwareEngineNetworks/{vmware_engine_network_id}`. `{project}` can either be a project number or a project ID. */
		vmwareEngineNetwork: Option[String] = None,
	  /** Output only. System-generated unique identifier for the resource. */
		uid: Option[String] = None
	)
	
	case class RepairManagementDnsZoneBindingRequest(
	  /** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server guarantees that a request doesn't result in creation of duplicate commitments for at least 60 minutes. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if the original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	object VmwareEngineNetwork {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, UPDATING, DELETING }
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, LEGACY, STANDARD }
	}
	case class VmwareEngineNetwork(
	  /** Output only. Identifier. The resource name of the VMware Engine network. Resource names are schemeless URIs that follow the conventions in https://cloud.google.com/apis/design/resource_names. For example: `projects/my-project/locations/global/vmwareEngineNetworks/my-network` */
		name: Option[String] = None,
	  /** Output only. Creation time of this resource. */
		createTime: Option[String] = None,
	  /** Output only. Last update time of this resource. */
		updateTime: Option[String] = None,
	  /** User-provided description for this VMware Engine network. */
		description: Option[String] = None,
	  /** Output only. VMware Engine service VPC networks that provide connectivity from a private cloud to customer projects, the internet, and other Google Cloud services. */
		vpcNetworks: Option[List[Schema.VpcNetwork]] = None,
	  /** Output only. State of the VMware Engine network. */
		state: Option[Schema.VmwareEngineNetwork.StateEnum] = None,
	  /** Required. VMware Engine network type. */
		`type`: Option[Schema.VmwareEngineNetwork.TypeEnum] = None,
	  /** Output only. System-generated unique identifier for the resource. */
		uid: Option[String] = None,
	  /** Checksum that may be sent on update and delete requests to ensure that the user-provided value is up to date before the server processes a request. The server computes checksums based on the value of other fields in the request. */
		etag: Option[String] = None
	)
	
	object VpcNetwork {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, INTRANET, INTERNET, GOOGLE_CLOUD }
	}
	case class VpcNetwork(
	  /** Output only. Type of VPC network (INTRANET, INTERNET, or GOOGLE_CLOUD) */
		`type`: Option[Schema.VpcNetwork.TypeEnum] = None,
	  /** Output only. The relative resource name of the service VPC network this VMware Engine network is attached to. For example: `projects/123123/global/networks/my-network` */
		network: Option[String] = None
	)
	
	case class ListVmwareEngineNetworksResponse(
	  /** A list of VMware Engine networks. */
		vmwareEngineNetworks: Option[List[Schema.VmwareEngineNetwork]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Unreachable resources. */
		unreachable: Option[List[String]] = None
	)
	
	object PrivateConnection {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, UPDATING, DELETING, UNPROVISIONED, FAILED }
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, PRIVATE_SERVICE_ACCESS, NETAPP_CLOUD_VOLUMES, DELL_POWERSCALE, THIRD_PARTY_SERVICE }
		enum RoutingModeEnum extends Enum[RoutingModeEnum] { case ROUTING_MODE_UNSPECIFIED, GLOBAL, REGIONAL }
		enum PeeringStateEnum extends Enum[PeeringStateEnum] { case PEERING_STATE_UNSPECIFIED, PEERING_ACTIVE, PEERING_INACTIVE }
	}
	case class PrivateConnection(
	  /** Output only. The resource name of the private connection. Resource names are schemeless URIs that follow the conventions in https://cloud.google.com/apis/design/resource_names. For example: `projects/my-project/locations/us-central1/privateConnections/my-connection` */
		name: Option[String] = None,
	  /** Output only. Creation time of this resource. */
		createTime: Option[String] = None,
	  /** Output only. Last update time of this resource. */
		updateTime: Option[String] = None,
	  /** Optional. User-provided description for this private connection. */
		description: Option[String] = None,
	  /** Output only. State of the private connection. */
		state: Option[Schema.PrivateConnection.StateEnum] = None,
	  /** Required. The relative resource name of Legacy VMware Engine network. Specify the name in the following form: `projects/{project}/locations/{location}/vmwareEngineNetworks/{vmware_engine_network_id}` where `{project}`, `{location}` will be same as specified in private connection resource name and `{vmware_engine_network_id}` will be in the form of `{location}`-default e.g. projects/project/locations/us-central1/vmwareEngineNetworks/us-central1-default. */
		vmwareEngineNetwork: Option[String] = None,
	  /** Output only. The canonical name of the VMware Engine network in the form: `projects/{project_number}/locations/{location}/vmwareEngineNetworks/{vmware_engine_network_id}` */
		vmwareEngineNetworkCanonical: Option[String] = None,
	  /** Required. Private connection type. */
		`type`: Option[Schema.PrivateConnection.TypeEnum] = None,
	  /** Output only. VPC network peering id between given network VPC and VMwareEngineNetwork. */
		peeringId: Option[String] = None,
	  /** Optional. Routing Mode. Default value is set to GLOBAL. For type = PRIVATE_SERVICE_ACCESS, this field can be set to GLOBAL or REGIONAL, for other types only GLOBAL is supported. */
		routingMode: Option[Schema.PrivateConnection.RoutingModeEnum] = None,
	  /** Output only. System-generated unique identifier for the resource. */
		uid: Option[String] = None,
	  /** Required. Service network to create private connection. Specify the name in the following form: `projects/{project}/global/networks/{network_id}` For type = PRIVATE_SERVICE_ACCESS, this field represents servicenetworking VPC, e.g. projects/project-tp/global/networks/servicenetworking. For type = NETAPP_CLOUD_VOLUME, this field represents NetApp service VPC, e.g. projects/project-tp/global/networks/netapp-tenant-vpc. For type = DELL_POWERSCALE, this field represent Dell service VPC, e.g. projects/project-tp/global/networks/dell-tenant-vpc. For type= THIRD_PARTY_SERVICE, this field could represent a consumer VPC or any other producer VPC to which the VMware Engine Network needs to be connected, e.g. projects/project/global/networks/vpc. */
		serviceNetwork: Option[String] = None,
	  /** Output only. Peering state between service network and VMware Engine network. */
		peeringState: Option[Schema.PrivateConnection.PeeringStateEnum] = None
	)
	
	case class ListPrivateConnectionsResponse(
	  /** A list of private connections. */
		privateConnections: Option[List[Schema.PrivateConnection]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Unreachable resources. */
		unreachable: Option[List[String]] = None
	)
	
	case class ListPrivateConnectionPeeringRoutesResponse(
	  /** A list of peering routes. */
		peeringRoutes: Option[List[Schema.PeeringRoute]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GrantDnsBindPermissionRequest(
	  /** Required. The consumer provided user/service account which needs to be granted permission to bind with the intranet VPC corresponding to the consumer project. */
		principal: Option[Schema.Principal] = None,
	  /** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server guarantees that a request doesn't result in creation of duplicate commitments for at least 60 minutes. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	case class Principal(
	  /** The user who needs to be granted permission. */
		user: Option[String] = None,
	  /** The service account which needs to be granted the permission. */
		serviceAccount: Option[String] = None
	)
	
	case class DnsBindPermission(
	  /** Required. Output only. The name of the resource which stores the users/service accounts having the permission to bind to the corresponding intranet VPC of the consumer project. DnsBindPermission is a global resource and location can only be global. Resource names are schemeless URIs that follow the conventions in https://cloud.google.com/apis/design/resource_names. For example: `projects/my-project/locations/global/dnsBindPermission` */
		name: Option[String] = None,
	  /** Output only. Users/Service accounts which have access for binding on the intranet VPC project corresponding to the consumer project. */
		principals: Option[List[Schema.Principal]] = None
	)
	
	case class RevokeDnsBindPermissionRequest(
	  /** Required. The consumer provided user/service account which needs to be granted permission to bind with the intranet VPC corresponding to the consumer project. */
		principal: Option[Schema.Principal] = None,
	  /** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server guarantees that a request doesn't result in creation of duplicate commitments for at least 60 minutes. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
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
	  /** Output only. True if the user has requested cancellation of the operation; false otherwise. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	object LocationMetadata {
		enum CapabilitiesEnum extends Enum[CapabilitiesEnum] { case CAPABILITY_UNSPECIFIED, STRETCHED_CLUSTERS }
	}
	case class LocationMetadata(
	  /** Output only. Capabilities of this location. */
		capabilities: Option[List[Schema.LocationMetadata.CapabilitiesEnum]] = None
	)
}
