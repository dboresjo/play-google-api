package com.boresjo.play.api.google.networkconnectivity

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

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
	
	case class ListServiceConnectionMapsResponse(
	  /** ServiceConnectionMaps to be returned. */
		serviceConnectionMaps: Option[List[Schema.ServiceConnectionMap]] = None,
	  /** The next pagination token in the List response. It should be used as page_token for the following request. An empty value means no more result. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object ServiceConnectionMap {
		enum InfrastructureEnum extends Enum[InfrastructureEnum] { case INFRASTRUCTURE_UNSPECIFIED, PSC }
	}
	case class ServiceConnectionMap(
	  /** Immutable. The name of a ServiceConnectionMap. Format: projects/{project}/locations/{location}/serviceConnectionMaps/{service_connection_map} See: https://google.aip.dev/122#fields-representing-resource-names */
		name: Option[String] = None,
	  /** Output only. Time when the ServiceConnectionMap was created. */
		createTime: Option[String] = None,
	  /** Output only. Time when the ServiceConnectionMap was updated. */
		updateTime: Option[String] = None,
	  /** User-defined labels. */
		labels: Option[Map[String, String]] = None,
	  /** A description of this resource. */
		description: Option[String] = None,
	  /** The service class identifier this ServiceConnectionMap is for. The user of ServiceConnectionMap create API needs to have networkconnecitivty.serviceclasses.use iam permission for the service class. */
		serviceClass: Option[String] = None,
	  /** Output only. The service class uri this ServiceConnectionMap is for. */
		serviceClassUri: Option[String] = None,
	  /** Output only. The infrastructure used for connections between consumers/producers. */
		infrastructure: Option[Schema.ServiceConnectionMap.InfrastructureEnum] = None,
	  /** The PSC configurations on producer side. */
		producerPscConfigs: Option[List[Schema.ProducerPscConfig]] = None,
	  /** The PSC configurations on consumer side. */
		consumerPscConfigs: Option[List[Schema.ConsumerPscConfig]] = None,
	  /** Output only. PSC connection details on consumer side. */
		consumerPscConnections: Option[List[Schema.ConsumerPscConnection]] = None,
	  /** The token provided by the consumer. This token authenticates that the consumer can create a connecton within the specified project and network. */
		token: Option[String] = None,
	  /** Optional. The etag is computed by the server, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None
	)
	
	case class ProducerPscConfig(
	  /** The resource path of a service attachment. Example: projects/{projectNumOrId}/regions/{region}/serviceAttachments/{resourceId}. */
		serviceAttachmentUri: Option[String] = None
	)
	
	object ConsumerPscConfig {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, VALID, CONNECTION_POLICY_MISSING, POLICY_LIMIT_REACHED, CONSUMER_INSTANCE_PROJECT_NOT_ALLOWLISTED }
		enum IpVersionEnum extends Enum[IpVersionEnum] { case IP_VERSION_UNSPECIFIED, IPV4, IPV6 }
	}
	case class ConsumerPscConfig(
	  /** The consumer project where PSC connections are allowed to be created in. */
		project: Option[String] = None,
	  /** The resource path of the consumer network where PSC connections are allowed to be created in. Note, this network does not need be in the ConsumerPscConfig.project in the case of SharedVPC. Example: projects/{projectNumOrId}/global/networks/{networkId}. */
		network: Option[String] = None,
	  /** This is used in PSC consumer ForwardingRule to control whether the PSC endpoint can be accessed from another region. */
		disableGlobalAccess: Option[Boolean] = None,
	  /** Output only. Overall state of PSC Connections management for this consumer psc config. */
		state: Option[Schema.ConsumerPscConfig.StateEnum] = None,
	  /** Immutable. Deprecated. Use producer_instance_metadata instead. An immutable identifier for the producer instance. */
		producerInstanceId: Option[String] = None,
	  /** Output only. A map to store mapping between customer vip and target service attachment. Only service attachment with producer specified ip addresses are stored here. */
		serviceAttachmentIpAddressMap: Option[Map[String, String]] = None,
	  /** Required. The project ID or project number of the consumer project. This project is the one that the consumer uses to interact with the producer instance. From the perspective of a consumer who's created a producer instance, this is the project of the producer instance. Format: 'projects/' Eg. 'projects/consumer-project' or 'projects/1234' */
		consumerInstanceProject: Option[String] = None,
	  /** Immutable. An immutable map for the producer instance metadata. */
		producerInstanceMetadata: Option[Map[String, String]] = None,
	  /** The requested IP version for the PSC connection. */
		ipVersion: Option[Schema.ConsumerPscConfig.IpVersionEnum] = None
	)
	
	object ConsumerPscConnection {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, FAILED, CREATING, DELETING, CREATE_REPAIRING, DELETE_REPAIRING }
		enum ErrorTypeEnum extends Enum[ErrorTypeEnum] { case CONNECTION_ERROR_TYPE_UNSPECIFIED, ERROR_INTERNAL, ERROR_CONSUMER_SIDE, ERROR_PRODUCER_SIDE }
		enum IpVersionEnum extends Enum[IpVersionEnum] { case IP_VERSION_UNSPECIFIED, IPV4, IPV6 }
	}
	case class ConsumerPscConnection(
	  /** The URI of a service attachment which is the target of the PSC connection. */
		serviceAttachmentUri: Option[String] = None,
	  /** The state of the PSC connection. */
		state: Option[Schema.ConsumerPscConnection.StateEnum] = None,
	  /** The consumer project whose PSC forwarding rule is connected to the service attachments in this service connection map. */
		project: Option[String] = None,
	  /** The consumer network whose PSC forwarding rule is connected to the service attachments in this service connection map. Note that the network could be on a different project (shared VPC). */
		network: Option[String] = None,
	  /** The PSC connection id of the PSC forwarding rule connected to the service attachments in this service connection map. */
		pscConnectionId: Option[String] = None,
	  /** The IP literal allocated on the consumer network for the PSC forwarding rule that is created to connect to the producer service attachment in this service connection map. */
		ip: Option[String] = None,
	  /** The error type indicates whether the error is consumer facing, producer facing or system internal. */
		errorType: Option[Schema.ConsumerPscConnection.ErrorTypeEnum] = None,
	  /** The most recent error during operating this connection. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** The last Compute Engine operation to setup PSC connection. */
		gceOperation: Option[String] = None,
	  /** The URI of the consumer forwarding rule created. Example: projects/{projectNumOrId}/regions/us-east1/networks/{resourceId}. */
		forwardingRule: Option[String] = None,
	  /** Output only. The error info for the latest error during operating this connection. */
		errorInfo: Option[Schema.GoogleRpcErrorInfo] = None,
	  /** Output only. The URI of the selected subnetwork selected to allocate IP address for this connection. */
		selectedSubnetwork: Option[String] = None,
	  /** Immutable. Deprecated. Use producer_instance_metadata instead. An immutable identifier for the producer instance. */
		producerInstanceId: Option[String] = None,
	  /** Immutable. An immutable map for the producer instance metadata. */
		producerInstanceMetadata: Option[Map[String, String]] = None,
	  /** The requested IP version for the PSC connection. */
		ipVersion: Option[Schema.ConsumerPscConnection.IpVersionEnum] = None
	)
	
	case class GoogleRpcErrorInfo(
	  /** The reason of the error. This is a constant value that identifies the proximate cause of the error. Error reasons are unique within a particular domain of errors. This should be at most 63 characters and match a regular expression of `A-Z+[A-Z0-9]`, which represents UPPER_SNAKE_CASE. */
		reason: Option[String] = None,
	  /** The logical grouping to which the "reason" belongs. The error domain is typically the registered service name of the tool or product that generates the error. Example: "pubsub.googleapis.com". If the error is generated by some common infrastructure, the error domain must be a globally unique value that identifies the infrastructure. For Google API infrastructure, the error domain is "googleapis.com". */
		domain: Option[String] = None,
	  /** Additional structured details about this error. Keys must match /a-z+/ but should ideally be lowerCamelCase. Also they must be limited to 64 characters in length. When identifying the current value of an exceeded limit, the units should be contained in the key, not the value. For example, rather than {"instanceLimit": "100/request"}, should be returned as, {"instanceLimitPerRequest": "100"}, if the client exceeds the number of instances that can be created in a single (batch) request. */
		metadata: Option[Map[String, String]] = None
	)
	
	case class ListServiceConnectionPoliciesResponse(
	  /** ServiceConnectionPolicies to be returned. */
		serviceConnectionPolicies: Option[List[Schema.ServiceConnectionPolicy]] = None,
	  /** The next pagination token in the List response. It should be used as page_token for the following request. An empty value means no more result. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object ServiceConnectionPolicy {
		enum InfrastructureEnum extends Enum[InfrastructureEnum] { case INFRASTRUCTURE_UNSPECIFIED, PSC }
	}
	case class ServiceConnectionPolicy(
	  /** Immutable. The name of a ServiceConnectionPolicy. Format: projects/{project}/locations/{location}/serviceConnectionPolicies/{service_connection_policy} See: https://google.aip.dev/122#fields-representing-resource-names */
		name: Option[String] = None,
	  /** Output only. Time when the ServiceConnectionPolicy was created. */
		createTime: Option[String] = None,
	  /** Output only. Time when the ServiceConnectionPolicy was updated. */
		updateTime: Option[String] = None,
	  /** User-defined labels. */
		labels: Option[Map[String, String]] = None,
	  /** A description of this resource. */
		description: Option[String] = None,
	  /** The resource path of the consumer network. Example: - projects/{projectNumOrId}/global/networks/{resourceId}. */
		network: Option[String] = None,
	  /** The service class identifier for which this ServiceConnectionPolicy is for. The service class identifier is a unique, symbolic representation of a ServiceClass. It is provided by the Service Producer. Google services have a prefix of gcp or google-cloud. For example, gcp-memorystore-redis or google-cloud-sql. 3rd party services do not. For example, test-service-a3dfcx. */
		serviceClass: Option[String] = None,
	  /** Output only. The type of underlying resources used to create the connection. */
		infrastructure: Option[Schema.ServiceConnectionPolicy.InfrastructureEnum] = None,
	  /** Configuration used for Private Service Connect connections. Used when Infrastructure is PSC. */
		pscConfig: Option[Schema.PscConfig] = None,
	  /** Output only. [Output only] Information about each Private Service Connect connection. */
		pscConnections: Option[List[Schema.PscConnection]] = None,
	  /** Optional. The etag is computed by the server, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None
	)
	
	object PscConfig {
		enum ProducerInstanceLocationEnum extends Enum[ProducerInstanceLocationEnum] { case PRODUCER_INSTANCE_LOCATION_UNSPECIFIED, CUSTOM_RESOURCE_HIERARCHY_LEVELS }
	}
	case class PscConfig(
	  /** The resource paths of subnetworks to use for IP address management. Example: projects/{projectNumOrId}/regions/{region}/subnetworks/{resourceId}. */
		subnetworks: Option[List[String]] = None,
	  /** Optional. Max number of PSC connections for this policy. */
		limit: Option[String] = None,
	  /** Required. ProducerInstanceLocation is used to specify which authorization mechanism to use to determine which projects the Producer instance can be within. */
		producerInstanceLocation: Option[Schema.PscConfig.ProducerInstanceLocationEnum] = None,
	  /** Optional. List of Projects, Folders, or Organizations from where the Producer instance can be within. For example, a network administrator can provide both 'organizations/foo' and 'projects/bar' as allowed_google_producers_resource_hierarchy_levels. This allowlists this network to connect with any Producer instance within the 'foo' organization or the 'bar' project. By default, allowed_google_producers_resource_hierarchy_level is empty. The format for each allowed_google_producers_resource_hierarchy_level is / where is one of 'projects', 'folders', or 'organizations' and is either the ID or the number of the resource type. Format for each allowed_google_producers_resource_hierarchy_level value: 'projects/' or 'folders/' or 'organizations/' Eg. [projects/my-project-id, projects/567, folders/891, organizations/123] */
		allowedGoogleProducersResourceHierarchyLevel: Option[List[String]] = None
	)
	
	object PscConnection {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, FAILED, CREATING, DELETING, CREATE_REPAIRING, DELETE_REPAIRING }
		enum ErrorTypeEnum extends Enum[ErrorTypeEnum] { case CONNECTION_ERROR_TYPE_UNSPECIFIED, ERROR_INTERNAL, ERROR_CONSUMER_SIDE, ERROR_PRODUCER_SIDE }
		enum IpVersionEnum extends Enum[IpVersionEnum] { case IP_VERSION_UNSPECIFIED, IPV4, IPV6 }
	}
	case class PscConnection(
	  /** State of the PSC Connection */
		state: Option[Schema.PscConnection.StateEnum] = None,
	  /** The resource reference of the PSC Forwarding Rule within the consumer VPC. */
		consumerForwardingRule: Option[String] = None,
	  /** The resource reference of the consumer address. */
		consumerAddress: Option[String] = None,
	  /** The error type indicates whether the error is consumer facing, producer facing or system internal. */
		errorType: Option[Schema.PscConnection.ErrorTypeEnum] = None,
	  /** The most recent error during operating this connection. Deprecated, please use error_info instead. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** The last Compute Engine operation to setup PSC connection. */
		gceOperation: Option[String] = None,
	  /** The project where the PSC connection is created. */
		consumerTargetProject: Option[String] = None,
	  /** The PSC connection id of the PSC forwarding rule. */
		pscConnectionId: Option[String] = None,
	  /** Output only. The error info for the latest error during operating this connection. */
		errorInfo: Option[Schema.GoogleRpcErrorInfo] = None,
	  /** Output only. The URI of the subnetwork selected to allocate IP address for this connection. */
		selectedSubnetwork: Option[String] = None,
	  /** Immutable. Deprecated. Use producer_instance_metadata instead. An immutable identifier for the producer instance. */
		producerInstanceId: Option[String] = None,
	  /** Immutable. An immutable map for the producer instance metadata. */
		producerInstanceMetadata: Option[Map[String, String]] = None,
	  /** Output only. [Output only] The service class associated with this PSC Connection. The value is derived from the SCPolicy and matches the service class name provided by the customer. */
		serviceClass: Option[String] = None,
	  /** The requested IP version for the PSC connection. */
		ipVersion: Option[Schema.PscConnection.IpVersionEnum] = None
	)
	
	case class ListServiceClassesResponse(
	  /** ServiceClasses to be returned. */
		serviceClasses: Option[List[Schema.ServiceClass]] = None,
	  /** The next pagination token in the List response. It should be used as page_token for the following request. An empty value means no more result. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ServiceClass(
	  /** Immutable. The name of a ServiceClass resource. Format: projects/{project}/locations/{location}/serviceClasses/{service_class} See: https://google.aip.dev/122#fields-representing-resource-names */
		name: Option[String] = None,
	  /** Output only. The generated service class name. Use this name to refer to the Service class in Service Connection Maps and Service Connection Policies. */
		serviceClass: Option[String] = None,
	  /** Output only. Time when the ServiceClass was created. */
		createTime: Option[String] = None,
	  /** Output only. Time when the ServiceClass was updated. */
		updateTime: Option[String] = None,
	  /** User-defined labels. */
		labels: Option[Map[String, String]] = None,
	  /** A description of this resource. */
		description: Option[String] = None,
	  /** Optional. The etag is computed by the server, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None
	)
	
	case class ServiceConnectionToken(
	  /** Immutable. The name of a ServiceConnectionToken. Format: projects/{project}/locations/{location}/ServiceConnectionTokens/{service_connection_token} See: https://google.aip.dev/122#fields-representing-resource-names */
		name: Option[String] = None,
	  /** Output only. Time when the ServiceConnectionToken was created. */
		createTime: Option[String] = None,
	  /** Output only. Time when the ServiceConnectionToken was updated. */
		updateTime: Option[String] = None,
	  /** User-defined labels. */
		labels: Option[Map[String, String]] = None,
	  /** A description of this resource. */
		description: Option[String] = None,
	  /** The resource path of the network associated with this token. Example: projects/{projectNumOrId}/global/networks/{resourceId}. */
		network: Option[String] = None,
	  /** Output only. The token generated by Automation. */
		token: Option[String] = None,
	  /** Output only. The time to which this token is valid. */
		expireTime: Option[String] = None,
	  /** Optional. The etag is computed by the server, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None
	)
	
	case class ListServiceConnectionTokensResponse(
	  /** ServiceConnectionTokens to be returned. */
		serviceConnectionTokens: Option[List[Schema.ServiceConnectionToken]] = None,
	  /** The next pagination token in the List response. It should be used as page_token for the following request. An empty value means no more result. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ListHubsResponse(
	  /** The requested hubs. */
		hubs: Option[List[Schema.Hub]] = None,
	  /** The token for the next page of the response. To see more results, use this value as the page_token for your next request. If this value is empty, there are no more results. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Hub {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, ACCEPTING, REJECTING, UPDATING, INACTIVE, OBSOLETE }
		enum PolicyModeEnum extends Enum[PolicyModeEnum] { case POLICY_MODE_UNSPECIFIED, PRESET }
		enum PresetTopologyEnum extends Enum[PresetTopologyEnum] { case PRESET_TOPOLOGY_UNSPECIFIED, MESH, STAR }
	}
	case class Hub(
	  /** Immutable. The name of the hub. Hub names must be unique. They use the following form: `projects/{project_number}/locations/global/hubs/{hub_id}` */
		name: Option[String] = None,
	  /** Output only. The time the hub was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the hub was last updated. */
		updateTime: Option[String] = None,
	  /** Optional labels in key-value pair format. For more information about labels, see [Requirements for labels](https://cloud.google.com/resource-manager/docs/creating-managing-labels#requirements). */
		labels: Option[Map[String, String]] = None,
	  /** An optional description of the hub. */
		description: Option[String] = None,
	  /** Output only. The Google-generated UUID for the hub. This value is unique across all hub resources. If a hub is deleted and another with the same name is created, the new hub is assigned a different unique_id. */
		uniqueId: Option[String] = None,
	  /** Output only. The current lifecycle state of this hub. */
		state: Option[Schema.Hub.StateEnum] = None,
	  /** The VPC networks associated with this hub's spokes. This field is read-only. Network Connectivity Center automatically populates it based on the set of spokes attached to the hub. */
		routingVpcs: Option[List[Schema.RoutingVPC]] = None,
	  /** Output only. The route tables that belong to this hub. They use the following form: `projects/{project_number}/locations/global/hubs/{hub_id}/routeTables/{route_table_id}` This field is read-only. Network Connectivity Center automatically populates it based on the route tables nested under the hub. */
		routeTables: Option[List[String]] = None,
	  /** Output only. A summary of the spokes associated with a hub. The summary includes a count of spokes according to type and according to state. If any spokes are inactive, the summary also lists the reasons they are inactive, including a count for each reason. */
		spokeSummary: Option[Schema.SpokeSummary] = None,
	  /** Optional. The policy mode of this hub. This field can be either PRESET or CUSTOM. If unspecified, the policy_mode defaults to PRESET. */
		policyMode: Option[Schema.Hub.PolicyModeEnum] = None,
	  /** Optional. The topology implemented in this hub. Currently, this field is only used when policy_mode = PRESET. The available preset topologies are MESH and STAR. If preset_topology is unspecified and policy_mode = PRESET, the preset_topology defaults to MESH. When policy_mode = CUSTOM, the preset_topology is set to PRESET_TOPOLOGY_UNSPECIFIED. */
		presetTopology: Option[Schema.Hub.PresetTopologyEnum] = None,
	  /** Optional. Whether Private Service Connect transitivity is enabled for the hub. If true, Private Service Connect endpoints in VPC spokes attached to the hub are made accessible to other VPC spokes attached to the hub. The default value is false. */
		exportPsc: Option[Boolean] = None
	)
	
	case class RoutingVPC(
	  /** The URI of the VPC network. */
		uri: Option[String] = None,
	  /** Output only. If true, indicates that this VPC network is currently associated with spokes that use the data transfer feature (spokes where the site_to_site_data_transfer field is set to true). If you create new spokes that use data transfer, they must be associated with this VPC network. At most, one VPC network will have this field set to true. */
		requiredForNewSiteToSiteDataTransferSpokes: Option[Boolean] = None
	)
	
	case class SpokeSummary(
	  /** Output only. Counts the number of spokes of each type that are associated with a specific hub. */
		spokeTypeCounts: Option[List[Schema.SpokeTypeCount]] = None,
	  /** Output only. Counts the number of spokes that are in each state and associated with a given hub. */
		spokeStateCounts: Option[List[Schema.SpokeStateCount]] = None,
	  /** Output only. Counts the number of spokes that are inactive for each possible reason and associated with a given hub. */
		spokeStateReasonCounts: Option[List[Schema.SpokeStateReasonCount]] = None
	)
	
	object SpokeTypeCount {
		enum SpokeTypeEnum extends Enum[SpokeTypeEnum] { case SPOKE_TYPE_UNSPECIFIED, VPN_TUNNEL, INTERCONNECT_ATTACHMENT, ROUTER_APPLIANCE, VPC_NETWORK, PRODUCER_VPC_NETWORK }
	}
	case class SpokeTypeCount(
	  /** Output only. The type of the spokes. */
		spokeType: Option[Schema.SpokeTypeCount.SpokeTypeEnum] = None,
	  /** Output only. The total number of spokes of this type that are associated with the hub. */
		count: Option[String] = None
	)
	
	object SpokeStateCount {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, ACCEPTING, REJECTING, UPDATING, INACTIVE, OBSOLETE }
	}
	case class SpokeStateCount(
	  /** Output only. The state of the spokes. */
		state: Option[Schema.SpokeStateCount.StateEnum] = None,
	  /** Output only. The total number of spokes that are in this state and associated with a given hub. */
		count: Option[String] = None
	)
	
	object SpokeStateReasonCount {
		enum StateReasonCodeEnum extends Enum[StateReasonCodeEnum] { case CODE_UNSPECIFIED, PENDING_REVIEW, REJECTED, PAUSED, FAILED, UPDATE_PENDING_REVIEW, UPDATE_REJECTED, UPDATE_FAILED }
	}
	case class SpokeStateReasonCount(
	  /** Output only. The reason that a spoke is inactive. */
		stateReasonCode: Option[Schema.SpokeStateReasonCount.StateReasonCodeEnum] = None,
	  /** Output only. The total number of spokes that are inactive for a particular reason and associated with a given hub. */
		count: Option[String] = None
	)
	
	case class ListHubSpokesResponse(
	  /** The requested spokes. The spoke fields can be partially populated based on the `view` field in the request message. */
		spokes: Option[List[Schema.Spoke]] = None,
	  /** The token for the next page of the response. To see more results, use this value as the page_token for your next request. If this value is empty, there are no more results. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Spoke {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, ACCEPTING, REJECTING, UPDATING, INACTIVE, OBSOLETE }
		enum SpokeTypeEnum extends Enum[SpokeTypeEnum] { case SPOKE_TYPE_UNSPECIFIED, VPN_TUNNEL, INTERCONNECT_ATTACHMENT, ROUTER_APPLIANCE, VPC_NETWORK, PRODUCER_VPC_NETWORK }
	}
	case class Spoke(
	  /** Immutable. The name of the spoke. Spoke names must be unique. They use the following form: `projects/{project_number}/locations/{region}/spokes/{spoke_id}` */
		name: Option[String] = None,
	  /** Output only. The time the spoke was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the spoke was last updated. */
		updateTime: Option[String] = None,
	  /** Optional labels in key-value pair format. For more information about labels, see [Requirements for labels](https://cloud.google.com/resource-manager/docs/creating-managing-labels#requirements). */
		labels: Option[Map[String, String]] = None,
	  /** An optional description of the spoke. */
		description: Option[String] = None,
	  /** Immutable. The name of the hub that this spoke is attached to. */
		hub: Option[String] = None,
	  /** Optional. The name of the group that this spoke is associated with. */
		group: Option[String] = None,
	  /** VPN tunnels that are associated with the spoke. */
		linkedVpnTunnels: Option[Schema.LinkedVpnTunnels] = None,
	  /** VLAN attachments that are associated with the spoke. */
		linkedInterconnectAttachments: Option[Schema.LinkedInterconnectAttachments] = None,
	  /** Router appliance instances that are associated with the spoke. */
		linkedRouterApplianceInstances: Option[Schema.LinkedRouterApplianceInstances] = None,
	  /** Optional. VPC network that is associated with the spoke. */
		linkedVpcNetwork: Option[Schema.LinkedVpcNetwork] = None,
	  /** Optional. The linked producer VPC that is associated with the spoke. */
		linkedProducerVpcNetwork: Option[Schema.LinkedProducerVpcNetwork] = None,
	  /** Output only. The Google-generated UUID for the spoke. This value is unique across all spoke resources. If a spoke is deleted and another with the same name is created, the new spoke is assigned a different `unique_id`. */
		uniqueId: Option[String] = None,
	  /** Output only. The current lifecycle state of this spoke. */
		state: Option[Schema.Spoke.StateEnum] = None,
	  /** Output only. The reasons for current state of the spoke. */
		reasons: Option[List[Schema.StateReason]] = None,
	  /** Output only. The type of resource associated with the spoke. */
		spokeType: Option[Schema.Spoke.SpokeTypeEnum] = None
	)
	
	case class LinkedVpnTunnels(
	  /** The URIs of linked VPN tunnel resources. */
		uris: Option[List[String]] = None,
	  /** A value that controls whether site-to-site data transfer is enabled for these resources. Data transfer is available only in [supported locations](https://cloud.google.com/network-connectivity/docs/network-connectivity-center/concepts/locations). */
		siteToSiteDataTransfer: Option[Boolean] = None,
	  /** Output only. The VPC network where these VPN tunnels are located. */
		vpcNetwork: Option[String] = None,
	  /** Optional. IP ranges allowed to be included during import from hub (does not control transit connectivity). The only allowed value for now is "ALL_IPV4_RANGES". */
		includeImportRanges: Option[List[String]] = None
	)
	
	case class LinkedInterconnectAttachments(
	  /** The URIs of linked interconnect attachment resources */
		uris: Option[List[String]] = None,
	  /** A value that controls whether site-to-site data transfer is enabled for these resources. Data transfer is available only in [supported locations](https://cloud.google.com/network-connectivity/docs/network-connectivity-center/concepts/locations). */
		siteToSiteDataTransfer: Option[Boolean] = None,
	  /** Output only. The VPC network where these VLAN attachments are located. */
		vpcNetwork: Option[String] = None,
	  /** Optional. IP ranges allowed to be included during import from hub (does not control transit connectivity). The only allowed value for now is "ALL_IPV4_RANGES". */
		includeImportRanges: Option[List[String]] = None
	)
	
	case class LinkedRouterApplianceInstances(
	  /** The list of router appliance instances. */
		instances: Option[List[Schema.RouterApplianceInstance]] = None,
	  /** A value that controls whether site-to-site data transfer is enabled for these resources. Data transfer is available only in [supported locations](https://cloud.google.com/network-connectivity/docs/network-connectivity-center/concepts/locations). */
		siteToSiteDataTransfer: Option[Boolean] = None,
	  /** Output only. The VPC network where these router appliance instances are located. */
		vpcNetwork: Option[String] = None,
	  /** Optional. IP ranges allowed to be included during import from hub (does not control transit connectivity). The only allowed value for now is "ALL_IPV4_RANGES". */
		includeImportRanges: Option[List[String]] = None
	)
	
	case class RouterApplianceInstance(
	  /** The URI of the VM. */
		virtualMachine: Option[String] = None,
	  /** The IP address on the VM to use for peering. */
		ipAddress: Option[String] = None
	)
	
	case class LinkedVpcNetwork(
	  /** Required. The URI of the VPC network resource. */
		uri: Option[String] = None,
	  /** Optional. IP ranges encompassing the subnets to be excluded from peering. */
		excludeExportRanges: Option[List[String]] = None,
	  /** Optional. IP ranges allowed to be included from peering. */
		includeExportRanges: Option[List[String]] = None,
	  /** Output only. The list of Producer VPC spokes that this VPC spoke is a service consumer VPC spoke for. These producer VPCs are connected through VPC peering to this spoke's backing VPC network. */
		producerVpcSpokes: Option[List[String]] = None
	)
	
	case class LinkedProducerVpcNetwork(
	  /** Immutable. The URI of the Service Consumer VPC that the Producer VPC is peered with. */
		network: Option[String] = None,
	  /** Output only. The Service Consumer Network spoke. */
		serviceConsumerVpcSpoke: Option[String] = None,
	  /** Immutable. The name of the VPC peering between the Service Consumer VPC and the Producer VPC (defined in the Tenant project) which is added to the NCC hub. This peering must be in ACTIVE state. */
		peering: Option[String] = None,
	  /** Output only. The URI of the Producer VPC. */
		producerNetwork: Option[String] = None,
	  /** Optional. IP ranges encompassing the subnets to be excluded from peering. */
		excludeExportRanges: Option[List[String]] = None,
	  /** Optional. IP ranges allowed to be included from peering. */
		includeExportRanges: Option[List[String]] = None
	)
	
	object StateReason {
		enum CodeEnum extends Enum[CodeEnum] { case CODE_UNSPECIFIED, PENDING_REVIEW, REJECTED, PAUSED, FAILED, UPDATE_PENDING_REVIEW, UPDATE_REJECTED, UPDATE_FAILED }
	}
	case class StateReason(
	  /** The code associated with this reason. */
		code: Option[Schema.StateReason.CodeEnum] = None,
	  /** Human-readable details about this reason. */
		message: Option[String] = None,
	  /** Additional information provided by the user in the RejectSpoke call. */
		userDetails: Option[String] = None
	)
	
	case class QueryHubStatusResponse(
	  /** The list of hub status. */
		hubStatusEntries: Option[List[Schema.HubStatusEntry]] = None,
	  /** The token for the next page of the response. To see more results, use this value as the page_token for your next request. If this value is empty, there are no more results. */
		nextPageToken: Option[String] = None
	)
	
	case class HubStatusEntry(
	  /** The number of status. If group_by is not set in the request, the default is 1. */
		count: Option[Int] = None,
	  /** The same group_by field from the request. */
		groupBy: Option[String] = None,
	  /** The PSC propagation status. */
		pscPropagationStatus: Option[Schema.PscPropagationStatus] = None
	)
	
	object PscPropagationStatus {
		enum CodeEnum extends Enum[CodeEnum] { case CODE_UNSPECIFIED, READY, PROPAGATING, ERROR_PRODUCER_PROPAGATED_CONNECTION_LIMIT_EXCEEDED, ERROR_PRODUCER_NAT_IP_SPACE_EXHAUSTED, ERROR_PRODUCER_QUOTA_EXCEEDED, ERROR_CONSUMER_QUOTA_EXCEEDED }
	}
	case class PscPropagationStatus(
	  /** The name of the spoke that the source forwarding rule belongs to. */
		sourceSpoke: Option[String] = None,
	  /** The name of the group that the source spoke belongs to. */
		sourceGroup: Option[String] = None,
	  /** The name of the forwarding rule exported to the hub. */
		sourceForwardingRule: Option[String] = None,
	  /** The name of the spoke that the source forwarding rule propagates to. */
		targetSpoke: Option[String] = None,
	  /** The name of the group that the target spoke belongs to. */
		targetGroup: Option[String] = None,
	  /** The propagation status. */
		code: Option[Schema.PscPropagationStatus.CodeEnum] = None,
	  /** The human-readable summary of the PSC connection propagation status. */
		message: Option[String] = None
	)
	
	case class ListSpokesResponse(
	  /** The requested spokes. */
		spokes: Option[List[Schema.Spoke]] = None,
	  /** The token for the next page of the response. To see more results, use this value as the page_token for your next request. If this value is empty, there are no more results. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class RejectHubSpokeRequest(
	  /** Required. The URI of the spoke to reject from the hub. */
		spokeUri: Option[String] = None,
	  /** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server knows to ignore the request if it has already been completed. The server guarantees that a request doesn't result in creation of duplicate commitments for at least 60 minutes. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check to see whether the original operation was received. If it was, the server ignores the second request. This behavior prevents clients from mistakenly creating duplicate commitments. The request ID must be a valid UUID, with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None,
	  /** Optional. Additional information provided by the hub administrator. */
		details: Option[String] = None
	)
	
	case class AcceptHubSpokeRequest(
	  /** Required. The URI of the spoke to accept into the hub. */
		spokeUri: Option[String] = None,
	  /** Optional. A request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server knows to ignore the request if it has already been completed. The server guarantees that a request doesn't result in creation of duplicate commitments for at least 60 minutes. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check to see whether the original operation was received. If it was, the server ignores the second request. This behavior prevents clients from mistakenly creating duplicate commitments. The request ID must be a valid UUID, with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	object RouteTable {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, ACCEPTING, REJECTING, UPDATING, INACTIVE, OBSOLETE }
	}
	case class RouteTable(
	  /** Immutable. The name of the route table. Route table names must be unique. They use the following form: `projects/{project_number}/locations/global/hubs/{hub}/routeTables/{route_table_id}` */
		name: Option[String] = None,
	  /** Output only. The time the route table was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the route table was last updated. */
		updateTime: Option[String] = None,
	  /** Optional labels in key-value pair format. For more information about labels, see [Requirements for labels](https://cloud.google.com/resource-manager/docs/creating-managing-labels#requirements). */
		labels: Option[Map[String, String]] = None,
	  /** An optional description of the route table. */
		description: Option[String] = None,
	  /** Output only. The Google-generated UUID for the route table. This value is unique across all route table resources. If a route table is deleted and another with the same name is created, the new route table is assigned a different `uid`. */
		uid: Option[String] = None,
	  /** Output only. The current lifecycle state of this route table. */
		state: Option[Schema.RouteTable.StateEnum] = None
	)
	
	object Route {
		enum TypeEnum extends Enum[TypeEnum] { case ROUTE_TYPE_UNSPECIFIED, VPC_PRIMARY_SUBNET, VPC_SECONDARY_SUBNET, DYNAMIC_ROUTE }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, ACCEPTING, REJECTING, UPDATING, INACTIVE, OBSOLETE }
	}
	case class Route(
	  /** Immutable. The name of the route. Route names must be unique. Route names use the following form: `projects/{project_number}/locations/global/hubs/{hub}/routeTables/{route_table_id}/routes/{route_id}` */
		name: Option[String] = None,
	  /** Output only. The time the route was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the route was last updated. */
		updateTime: Option[String] = None,
	  /** The destination IP address range. */
		ipCidrRange: Option[String] = None,
	  /** Output only. The route's type. Its type is determined by the properties of its IP address range. */
		`type`: Option[Schema.Route.TypeEnum] = None,
	  /** Immutable. The destination VPC network for packets on this route. */
		nextHopVpcNetwork: Option[Schema.NextHopVpcNetwork] = None,
	  /** Optional labels in key-value pair format. For more information about labels, see [Requirements for labels](https://cloud.google.com/resource-manager/docs/creating-managing-labels#requirements). */
		labels: Option[Map[String, String]] = None,
	  /** An optional description of the route. */
		description: Option[String] = None,
	  /** Output only. The Google-generated UUID for the route. This value is unique across all Network Connectivity Center route resources. If a route is deleted and another with the same name is created, the new route is assigned a different `uid`. */
		uid: Option[String] = None,
	  /** Output only. The current lifecycle state of the route. */
		state: Option[Schema.Route.StateEnum] = None,
	  /** Immutable. The spoke that this route leads to. Example: projects/12345/locations/global/spokes/SPOKE */
		spoke: Option[String] = None,
	  /** Output only. The origin location of the route. Uses the following form: "projects/{project}/locations/{location}" Example: projects/1234/locations/us-central1 */
		location: Option[String] = None,
	  /** Output only. The priority of this route. Priority is used to break ties in cases where a destination matches more than one route. In these cases the route with the lowest-numbered priority value wins. */
		priority: Option[String] = None,
	  /** Immutable. The next-hop VPN tunnel for packets on this route. */
		nextHopVpnTunnel: Option[Schema.NextHopVPNTunnel] = None,
	  /** Immutable. The next-hop Router appliance instance for packets on this route. */
		nextHopRouterApplianceInstance: Option[Schema.NextHopRouterApplianceInstance] = None,
	  /** Immutable. The next-hop VLAN attachment for packets on this route. */
		nextHopInterconnectAttachment: Option[Schema.NextHopInterconnectAttachment] = None
	)
	
	case class NextHopVpcNetwork(
	  /** The URI of the VPC network resource */
		uri: Option[String] = None
	)
	
	case class NextHopVPNTunnel(
	  /** The URI of the VPN tunnel resource. */
		uri: Option[String] = None,
	  /** The VPC network where this VPN tunnel is located. */
		vpcNetwork: Option[String] = None,
	  /** Indicates whether site-to-site data transfer is allowed for this VPN tunnel resource. Data transfer is available only in [supported locations](https://cloud.google.com/network-connectivity/docs/network-connectivity-center/concepts/locations). */
		siteToSiteDataTransfer: Option[Boolean] = None
	)
	
	case class NextHopRouterApplianceInstance(
	  /** The URI of the Router appliance instance. */
		uri: Option[String] = None,
	  /** The VPC network where this VM is located. */
		vpcNetwork: Option[String] = None,
	  /** Indicates whether site-to-site data transfer is allowed for this Router appliance instance resource. Data transfer is available only in [supported locations](https://cloud.google.com/network-connectivity/docs/network-connectivity-center/concepts/locations). */
		siteToSiteDataTransfer: Option[Boolean] = None
	)
	
	case class NextHopInterconnectAttachment(
	  /** The URI of the interconnect attachment resource. */
		uri: Option[String] = None,
	  /** The VPC network where this interconnect attachment is located. */
		vpcNetwork: Option[String] = None,
	  /** Indicates whether site-to-site data transfer is allowed for this interconnect attachment resource. Data transfer is available only in [supported locations](https://cloud.google.com/network-connectivity/docs/network-connectivity-center/concepts/locations). */
		siteToSiteDataTransfer: Option[Boolean] = None
	)
	
	case class ListRoutesResponse(
	  /** The requested routes. */
		routes: Option[List[Schema.Route]] = None,
	  /** The token for the next page of the response. To see more results, use this value as the page_token for your next request. If this value is empty, there are no more results. */
		nextPageToken: Option[String] = None,
	  /** RouteTables that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ListRouteTablesResponse(
	  /** The requested route tables. */
		routeTables: Option[List[Schema.RouteTable]] = None,
	  /** The token for the next page of the response. To see more results, use this value as the page_token for your next request. If this value is empty, there are no more results. */
		nextPageToken: Option[String] = None,
	  /** Hubs that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Group {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, ACCEPTING, REJECTING, UPDATING, INACTIVE, OBSOLETE }
	}
	case class Group(
	  /** Immutable. The name of the group. Group names must be unique. They use the following form: `projects/{project_number}/locations/global/hubs/{hub}/groups/{group_id}` */
		name: Option[String] = None,
	  /** Output only. The time the group was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the group was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. Labels in key-value pair format. For more information about labels, see [Requirements for labels](https://cloud.google.com/resource-manager/docs/creating-managing-labels#requirements). */
		labels: Option[Map[String, String]] = None,
	  /** Optional. The description of the group. */
		description: Option[String] = None,
	  /** Output only. The Google-generated UUID for the group. This value is unique across all group resources. If a group is deleted and another with the same name is created, the new route table is assigned a different unique_id. */
		uid: Option[String] = None,
	  /** Output only. The current lifecycle state of this group. */
		state: Option[Schema.Group.StateEnum] = None,
	  /** Optional. The auto-accept setting for this group. */
		autoAccept: Option[Schema.AutoAccept] = None,
	  /** Output only. The name of the route table that corresponds to this group. They use the following form: `projects/{project_number}/locations/global/hubs/{hub_id}/routeTables/{route_table_id}` */
		routeTable: Option[String] = None
	)
	
	case class AutoAccept(
	  /** A list of project ids or project numbers for which you want to enable auto-accept. The auto-accept setting is applied to spokes being created or updated in these projects. */
		autoAcceptProjects: Option[List[String]] = None
	)
	
	case class ListGroupsResponse(
	  /** The requested groups. */
		groups: Option[List[Schema.Group]] = None,
	  /** The token for the next page of the response. To see more results, use this value as the page_token for your next request. If this value is empty, there are no more results. */
		nextPageToken: Option[String] = None,
	  /** Hubs that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ListInternalRangesResponse(
	  /** Internal ranges to be returned. */
		internalRanges: Option[List[Schema.InternalRange]] = None,
	  /** The next pagination token in the List response. It should be used as page_token for the following request. An empty value means no more result. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object InternalRange {
		enum UsageEnum extends Enum[UsageEnum] { case USAGE_UNSPECIFIED, FOR_VPC, EXTERNAL_TO_VPC, FOR_MIGRATION }
		enum PeeringEnum extends Enum[PeeringEnum] { case PEERING_UNSPECIFIED, FOR_SELF, FOR_PEER, NOT_SHARED }
		enum OverlapsEnum extends Enum[OverlapsEnum] { case OVERLAP_UNSPECIFIED, OVERLAP_ROUTE_RANGE, OVERLAP_EXISTING_SUBNET_RANGE }
	}
	case class InternalRange(
	  /** Immutable. The name of an internal range. Format: projects/{project}/locations/{location}/internalRanges/{internal_range} See: https://google.aip.dev/122#fields-representing-resource-names */
		name: Option[String] = None,
	  /** Time when the internal range was created. */
		createTime: Option[String] = None,
	  /** Time when the internal range was updated. */
		updateTime: Option[String] = None,
	  /** User-defined labels. */
		labels: Option[Map[String, String]] = None,
	  /** A description of this resource. */
		description: Option[String] = None,
	  /** The IP range that this internal range defines. NOTE: IPv6 ranges are limited to usage=EXTERNAL_TO_VPC and peering=FOR_SELF. NOTE: For IPv6 Ranges this field is compulsory, i.e. the address range must be specified explicitly. */
		ipCidrRange: Option[String] = None,
	  /** The URL or resource ID of the network in which to reserve the internal range. The network cannot be deleted if there are any reserved internal ranges referring to it. Legacy networks are not supported. For example: https://www.googleapis.com/compute/v1/projects/{project}/locations/global/networks/{network} projects/{project}/locations/global/networks/{network} {network} */
		network: Option[String] = None,
	  /** The type of usage set for this InternalRange. */
		usage: Option[Schema.InternalRange.UsageEnum] = None,
	  /** The type of peering set for this internal range. */
		peering: Option[Schema.InternalRange.PeeringEnum] = None,
	  /** An alternate to ip_cidr_range. Can be set when trying to create an IPv4 reservation that automatically finds a free range of the given size. If both ip_cidr_range and prefix_length are set, there is an error if the range sizes do not match. Can also be used during updates to change the range size. NOTE: For IPv6 this field only works if ip_cidr_range is set as well, and both fields must match. In other words, with IPv6 this field only works as a redundant parameter. */
		prefixLength: Option[Int] = None,
	  /** Optional. Can be set to narrow down or pick a different address space while searching for a free range. If not set, defaults to the "10.0.0.0/8" address space. This can be used to search in other rfc-1918 address spaces like "172.16.0.0/12" and "192.168.0.0/16" or non-rfc-1918 address spaces used in the VPC. */
		targetCidrRange: Option[List[String]] = None,
	  /** Output only. The list of resources that refer to this internal range. Resources that use the internal range for their range allocation are referred to as users of the range. Other resources mark themselves as users while doing so by creating a reference to this internal range. Having a user, based on this reference, prevents deletion of the internal range referred to. Can be empty. */
		users: Option[List[String]] = None,
	  /** Optional. Types of resources that are allowed to overlap with the current internal range. */
		overlaps: Option[List[Schema.InternalRange.OverlapsEnum]] = None,
	  /** Optional. Must be present if usage is set to FOR_MIGRATION. This field is for internal use. */
		migration: Option[Schema.Migration] = None
	)
	
	case class Migration(
	  /** Immutable. Resource path as an URI of the source resource, for example a subnet. The project for the source resource should match the project for the InternalRange. An example: /projects/{project}/regions/{region}/subnetworks/{subnet} */
		source: Option[String] = None,
	  /** Immutable. Resource path of the target resource. The target project can be different, as in the cases when migrating to peer networks. The resource For example: /projects/{project}/regions/{region}/subnetworks/{subnet} */
		target: Option[String] = None
	)
	
	case class ListPolicyBasedRoutesResponse(
	  /** Policy-based routes to be returned. */
		policyBasedRoutes: Option[List[Schema.PolicyBasedRoute]] = None,
	  /** The next pagination token in the List response. It should be used as page_token for the following request. An empty value means no more result. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object PolicyBasedRoute {
		enum NextHopOtherRoutesEnum extends Enum[NextHopOtherRoutesEnum] { case OTHER_ROUTES_UNSPECIFIED, DEFAULT_ROUTING }
	}
	case class PolicyBasedRoute(
	  /** Optional. VM instances that this policy-based route applies to. */
		virtualMachine: Option[Schema.VirtualMachine] = None,
	  /** Optional. The interconnect attachments that this policy-based route applies to. */
		interconnectAttachment: Option[Schema.InterconnectAttachment] = None,
	  /** Optional. The IP address of a global-access-enabled L4 ILB that is the next hop for matching packets. For this version, only nextHopIlbIp is supported. */
		nextHopIlbIp: Option[String] = None,
	  /** Optional. Other routes that will be referenced to determine the next hop of the packet. */
		nextHopOtherRoutes: Option[Schema.PolicyBasedRoute.NextHopOtherRoutesEnum] = None,
	  /** Immutable. A unique name of the resource in the form of `projects/{project_number}/locations/global/PolicyBasedRoutes/{policy_based_route_id}` */
		name: Option[String] = None,
	  /** Output only. Time when the policy-based route was created. */
		createTime: Option[String] = None,
	  /** Output only. Time when the policy-based route was updated. */
		updateTime: Option[String] = None,
	  /** User-defined labels. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. An optional description of this resource. Provide this field when you create the resource. */
		description: Option[String] = None,
	  /** Required. Fully-qualified URL of the network that this route applies to, for example: projects/my-project/global/networks/my-network. */
		network: Option[String] = None,
	  /** Required. The filter to match L4 traffic. */
		filter: Option[Schema.Filter] = None,
	  /** Optional. The priority of this policy-based route. Priority is used to break ties in cases where there are more than one matching policy-based routes found. In cases where multiple policy-based routes are matched, the one with the lowest-numbered priority value wins. The default value is 1000. The priority value must be from 1 to 65535, inclusive. */
		priority: Option[Int] = None,
	  /** Output only. If potential misconfigurations are detected for this route, this field will be populated with warning messages. */
		warnings: Option[List[Schema.Warnings]] = None,
	  /** Output only. Server-defined fully-qualified URL for this resource. */
		selfLink: Option[String] = None,
	  /** Output only. Type of this resource. Always networkconnectivity#policyBasedRoute for policy-based Route resources. */
		kind: Option[String] = None
	)
	
	case class VirtualMachine(
	  /** Optional. A list of VM instance tags that this policy-based route applies to. VM instances that have ANY of tags specified here installs this PBR. */
		tags: Option[List[String]] = None
	)
	
	case class InterconnectAttachment(
	  /** Optional. Cloud region to install this policy-based route on interconnect attachment. Use `all` to install it on all interconnect attachments. */
		region: Option[String] = None
	)
	
	object Filter {
		enum ProtocolVersionEnum extends Enum[ProtocolVersionEnum] { case PROTOCOL_VERSION_UNSPECIFIED, IPV4 }
	}
	case class Filter(
	  /** Optional. The IP protocol that this policy-based route applies to. Valid values are 'TCP', 'UDP', and 'ALL'. Default is 'ALL'. */
		ipProtocol: Option[String] = None,
	  /** Optional. The source IP range of outgoing packets that this policy-based route applies to. Default is "0.0.0.0/0" if protocol version is IPv4. */
		srcRange: Option[String] = None,
	  /** Optional. The destination IP range of outgoing packets that this policy-based route applies to. Default is "0.0.0.0/0" if protocol version is IPv4. */
		destRange: Option[String] = None,
	  /** Required. Internet protocol versions this policy-based route applies to. For this version, only IPV4 is supported. IPV6 is supported in preview. */
		protocolVersion: Option[Schema.Filter.ProtocolVersionEnum] = None
	)
	
	object Warnings {
		enum CodeEnum extends Enum[CodeEnum] { case WARNING_UNSPECIFIED, RESOURCE_NOT_ACTIVE, RESOURCE_BEING_MODIFIED }
	}
	case class Warnings(
	  /** Output only. A warning code, if applicable. */
		code: Option[Schema.Warnings.CodeEnum] = None,
	  /** Output only. Metadata about this warning in key: value format. The key should provides more detail on the warning being returned. For example, for warnings where there are no results in a list request for a particular zone, this key might be scope and the key value might be the zone name. Other examples might be a key indicating a deprecated resource and a suggested replacement. */
		data: Option[Map[String, String]] = None,
	  /** Output only. A human-readable description of the warning code. */
		warningMessage: Option[String] = None
	)
	
	case class ListRegionalEndpointsResponse(
	  /** Regional endpoints to be returned. */
		regionalEndpoints: Option[List[Schema.RegionalEndpoint]] = None,
	  /** The next pagination token in the List response. It should be used as page_token for the following request. An empty value means no more result. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object RegionalEndpoint {
		enum AccessTypeEnum extends Enum[AccessTypeEnum] { case ACCESS_TYPE_UNSPECIFIED, GLOBAL, REGIONAL }
	}
	case class RegionalEndpoint(
	  /** Output only. The name of a RegionalEndpoint. Format: `projects/{project}/locations/{location}/regionalEndpoints/{regional_endpoint}`. */
		name: Option[String] = None,
	  /** Output only. Time when the RegionalEndpoint was created. */
		createTime: Option[String] = None,
	  /** Output only. Time when the RegionalEndpoint was updated. */
		updateTime: Option[String] = None,
	  /** User-defined labels. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. A description of this resource. */
		description: Option[String] = None,
	  /** Required. The service endpoint this private regional endpoint connects to. Format: `{apiname}.{region}.p.rep.googleapis.com` Example: "cloudkms.us-central1.p.rep.googleapis.com". */
		targetGoogleApi: Option[String] = None,
	  /** The name of the VPC network for this private regional endpoint. Format: `projects/{project}/global/networks/{network}` */
		network: Option[String] = None,
	  /** The name of the subnetwork from which the IP address will be allocated. Format: `projects/{project}/regions/{region}/subnetworks/{subnetwork}` */
		subnetwork: Option[String] = None,
	  /** Required. The access type of this regional endpoint. This field is reflected in the PSC Forwarding Rule configuration to enable global access. */
		accessType: Option[Schema.RegionalEndpoint.AccessTypeEnum] = None,
	  /** Output only. The resource reference of the PSC Forwarding Rule created on behalf of the customer. Format: `//compute.googleapis.com/projects/{project}/regions/{region}/forwardingRules/{forwarding_rule_name}` */
		pscForwardingRule: Option[String] = None,
	  /** Output only. The literal IP address of the PSC Forwarding Rule created on behalf of the customer. This field is deprecated. Use address instead. */
		ipAddress: Option[String] = None,
	  /** Optional. The IP Address of the Regional Endpoint. When no address is provided, an IP from the subnetwork is allocated. Use one of the following formats: &#42; IPv4 address as in `10.0.0.1` &#42; Address resource URI as in `projects/{project}/regions/{region}/addresses/{address_name}` */
		address: Option[String] = None
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
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have been cancelled successfully have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	object LocationMetadata {
		enum LocationFeaturesEnum extends Enum[LocationFeaturesEnum] { case LOCATION_FEATURE_UNSPECIFIED, SITE_TO_CLOUD_SPOKES, SITE_TO_SITE_SPOKES }
	}
	case class LocationMetadata(
	  /** List of supported features */
		locationFeatures: Option[List[Schema.LocationMetadata.LocationFeaturesEnum]] = None
	)
	
	case class AcceptHubSpokeResponse(
	  /** The spoke that was operated on. */
		spoke: Option[Schema.Spoke] = None
	)
	
	case class RejectHubSpokeResponse(
	  /** The spoke that was operated on. */
		spoke: Option[Schema.Spoke] = None
	)
}
