package com.boresjo.play.api.google.networksecurity

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
	
	case class CancelOperationRequest(
	
	)
	
	case class ListAddressGroupsResponse(
	  /** List of AddressGroups resources. */
		addressGroups: Option[List[Schema.AddressGroup]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None
	)
	
	object AddressGroup {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, IPV4, IPV6 }
		enum PurposeEnum extends Enum[PurposeEnum] { case PURPOSE_UNSPECIFIED, DEFAULT, CLOUD_ARMOR }
	}
	case class AddressGroup(
	  /** Required. Name of the AddressGroup resource. It matches pattern `projects/&#42;/locations/{location}/addressGroups/`. */
		name: Option[String] = None,
	  /** Optional. Free-text description of the resource. */
		description: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. Set of label tags associated with the AddressGroup resource. */
		labels: Option[Map[String, String]] = None,
	  /** Required. The type of the Address Group. Possible values are "IPv4" or "IPV6". */
		`type`: Option[Schema.AddressGroup.TypeEnum] = None,
	  /** Optional. List of items. */
		items: Option[List[String]] = None,
	  /** Required. Capacity of the Address Group */
		capacity: Option[Int] = None,
	  /** Output only. Server-defined fully-qualified URL for this resource. */
		selfLink: Option[String] = None,
	  /** Optional. List of supported purposes of the Address Group. */
		purpose: Option[List[Schema.AddressGroup.PurposeEnum]] = None
	)
	
	case class AddAddressGroupItemsRequest(
	  /** Required. List of items to add. */
		items: Option[List[String]] = None,
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	case class RemoveAddressGroupItemsRequest(
	  /** Required. List of items to remove. */
		items: Option[List[String]] = None,
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	case class CloneAddressGroupItemsRequest(
	  /** Required. Source address group to clone items from. */
		sourceAddressGroup: Option[String] = None,
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	case class ListAddressGroupReferencesResponse(
	  /** A list of references that matches the specified filter in the request. */
		addressGroupReferences: Option[List[Schema.ListAddressGroupReferencesResponseAddressGroupReference]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None
	)
	
	case class ListAddressGroupReferencesResponseAddressGroupReference(
	  /** FirewallPolicy that is using the Address Group. */
		firewallPolicy: Option[String] = None,
	  /** Cloud Armor SecurityPolicy that is using the Address Group. */
		securityPolicy: Option[String] = None,
	  /** Rule priority of the FirewallPolicy that is using the Address Group. */
		rulePriority: Option[Int] = None
	)
	
	case class ListFirewallEndpointsResponse(
	  /** The list of Endpoint */
		firewallEndpoints: Option[List[Schema.FirewallEndpoint]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object FirewallEndpoint {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, INACTIVE }
	}
	case class FirewallEndpoint(
	  /** Immutable. Identifier. name of resource */
		name: Option[String] = None,
	  /** Optional. Description of the firewall endpoint. Max length 2048 characters. */
		description: Option[String] = None,
	  /** Output only. Create time stamp */
		createTime: Option[String] = None,
	  /** Output only. Update time stamp */
		updateTime: Option[String] = None,
	  /** Optional. Labels as key value pairs */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Current state of the endpoint. */
		state: Option[Schema.FirewallEndpoint.StateEnum] = None,
	  /** Output only. Whether reconciling is in progress, recommended per https://google.aip.dev/128. */
		reconciling: Option[Boolean] = None,
	  /** Output only. List of networks that are associated with this endpoint in the local zone. This is a projection of the FirewallEndpointAssociations pointing at this endpoint. A network will only appear in this list after traffic routing is fully configured. Format: projects/{project}/global/networks/{name}. */
		associatedNetworks: Option[List[String]] = None,
	  /** Output only. List of FirewallEndpointAssociations that are associated to this endpoint. An association will only appear in this list after traffic routing is fully configured. */
		associations: Option[List[Schema.FirewallEndpointAssociationReference]] = None,
	  /** Required. Project to bill on endpoint uptime usage. */
		billingProjectId: Option[String] = None
	)
	
	case class FirewallEndpointAssociationReference(
	  /** Output only. The resource name of the FirewallEndpointAssociation. Format: projects/{project}/locations/{location}/firewallEndpointAssociations/{id} */
		name: Option[String] = None,
	  /** Output only. The VPC network associated. Format: projects/{project}/global/networks/{name}. */
		network: Option[String] = None
	)
	
	case class ListFirewallEndpointAssociationsResponse(
	  /** The list of Association */
		firewallEndpointAssociations: Option[List[Schema.FirewallEndpointAssociation]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object FirewallEndpointAssociation {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, DELETING, INACTIVE, ORPHAN }
	}
	case class FirewallEndpointAssociation(
	  /** Immutable. Identifier. name of resource */
		name: Option[String] = None,
	  /** Output only. Create time stamp */
		createTime: Option[String] = None,
	  /** Output only. Update time stamp */
		updateTime: Option[String] = None,
	  /** Optional. Labels as key value pairs */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Current state of the association. */
		state: Option[Schema.FirewallEndpointAssociation.StateEnum] = None,
	  /** Required. The URL of the network that is being associated. */
		network: Option[String] = None,
	  /** Required. The URL of the FirewallEndpoint that is being associated. */
		firewallEndpoint: Option[String] = None,
	  /** Optional. The URL of the TlsInspectionPolicy that is being associated. */
		tlsInspectionPolicy: Option[String] = None,
	  /** Output only. Whether reconciling is in progress, recommended per https://google.aip.dev/128. */
		reconciling: Option[Boolean] = None,
	  /** Optional. Whether the association is disabled. True indicates that traffic won't be intercepted */
		disabled: Option[Boolean] = None
	)
	
	case class ListAuthorizationPoliciesResponse(
	  /** List of AuthorizationPolicies resources. */
		authorizationPolicies: Option[List[Schema.AuthorizationPolicy]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None
	)
	
	object AuthorizationPolicy {
		enum ActionEnum extends Enum[ActionEnum] { case ACTION_UNSPECIFIED, ALLOW, DENY }
	}
	case class AuthorizationPolicy(
	  /** Required. Name of the AuthorizationPolicy resource. It matches pattern `projects/{project}/locations/{location}/authorizationPolicies/`. */
		name: Option[String] = None,
	  /** Optional. Free-text description of the resource. */
		description: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. Set of label tags associated with the AuthorizationPolicy resource. */
		labels: Option[Map[String, String]] = None,
	  /** Required. The action to take when a rule match is found. Possible values are "ALLOW" or "DENY". */
		action: Option[Schema.AuthorizationPolicy.ActionEnum] = None,
	  /** Optional. List of rules to match. Note that at least one of the rules must match in order for the action specified in the 'action' field to be taken. A rule is a match if there is a matching source and destination. If left blank, the action specified in the `action` field will be applied on every request. */
		rules: Option[List[Schema.Rule]] = None
	)
	
	case class Rule(
	  /** Optional. List of attributes for the traffic source. All of the sources must match. A source is a match if both principals and ip_blocks match. If not set, the action specified in the 'action' field will be applied without any rule checks for the source. */
		sources: Option[List[Schema.Source]] = None,
	  /** Optional. List of attributes for the traffic destination. All of the destinations must match. A destination is a match if a request matches all the specified hosts, ports, methods and headers. If not set, the action specified in the 'action' field will be applied without any rule checks for the destination. */
		destinations: Option[List[Schema.Destination]] = None
	)
	
	case class Source(
	  /** Optional. List of peer identities to match for authorization. At least one principal should match. Each peer can be an exact match, or a prefix match (example, "namespace/&#42;") or a suffix match (example, "&#42;/service-account") or a presence match "&#42;". Authorization based on the principal name without certificate validation (configured by ServerTlsPolicy resource) is considered insecure. */
		principals: Option[List[String]] = None,
	  /** Optional. List of CIDR ranges to match based on source IP address. At least one IP block should match. Single IP (e.g., "1.2.3.4") and CIDR (e.g., "1.2.3.0/24") are supported. Authorization based on source IP alone should be avoided. The IP addresses of any load balancers or proxies should be considered untrusted. */
		ipBlocks: Option[List[String]] = None
	)
	
	case class Destination(
	  /** Required. List of host names to match. Matched against the ":authority" header in http requests. At least one host should match. Each host can be an exact match, or a prefix match (example "mydomain.&#42;") or a suffix match (example "&#42;.myorg.com") or a presence (any) match "&#42;". */
		hosts: Option[List[String]] = None,
	  /** Required. List of destination ports to match. At least one port should match. */
		ports: Option[List[Int]] = None,
	  /** Optional. A list of HTTP methods to match. At least one method should match. Should not be set for gRPC services. */
		methods: Option[List[String]] = None,
	  /** Optional. Match against key:value pair in http header. Provides a flexible match based on HTTP headers, for potentially advanced use cases. At least one header should match. Avoid using header matches to make authorization decisions unless there is a strong guarantee that requests arrive through a trusted client or proxy. */
		httpHeaderMatch: Option[Schema.HttpHeaderMatch] = None
	)
	
	case class HttpHeaderMatch(
	  /** Required. The value of the header must match the regular expression specified in regexMatch. For regular expression grammar, please see: en.cppreference.com/w/cpp/regex/ecmascript For matching against a port specified in the HTTP request, use a headerMatch with headerName set to Host and a regular expression that satisfies the RFC2616 Host header's port specifier. */
		regexMatch: Option[String] = None,
	  /** Required. The name of the HTTP header to match. For matching against the HTTP request's authority, use a headerMatch with the header name ":authority". For matching a request's method, use the headerName ":method". */
		headerName: Option[String] = None
	)
	
	case class ListServerTlsPoliciesResponse(
	  /** List of ServerTlsPolicy resources. */
		serverTlsPolicies: Option[List[Schema.ServerTlsPolicy]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None
	)
	
	case class ServerTlsPolicy(
	  /** Required. Name of the ServerTlsPolicy resource. It matches the pattern `projects/&#42;/locations/{location}/serverTlsPolicies/{server_tls_policy}` */
		name: Option[String] = None,
	  /** Free-text description of the resource. */
		description: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Set of label tags associated with the resource. */
		labels: Option[Map[String, String]] = None,
	  /** This field applies only for Traffic Director policies. It is must be set to false for Application Load Balancer policies. Determines if server allows plaintext connections. If set to true, server allows plain text connections. By default, it is set to false. This setting is not exclusive of other encryption modes. For example, if `allow_open` and `mtls_policy` are set, server allows both plain text and mTLS connections. See documentation of other encryption modes to confirm compatibility. Consider using it if you wish to upgrade in place your deployment to TLS while having mixed TLS and non-TLS traffic reaching port :80. */
		allowOpen: Option[Boolean] = None,
	  /** Optional if policy is to be used with Traffic Director. For Application Load Balancers must be empty. Defines a mechanism to provision server identity (public and private keys). Cannot be combined with `allow_open` as a permissive mode that allows both plain text and TLS is not supported. */
		serverCertificate: Option[Schema.GoogleCloudNetworksecurityV1CertificateProvider] = None,
	  /** This field is required if the policy is used with Application Load Balancers. This field can be empty for Traffic Director. Defines a mechanism to provision peer validation certificates for peer to peer authentication (Mutual TLS - mTLS). If not specified, client certificate will not be requested. The connection is treated as TLS and not mTLS. If `allow_open` and `mtls_policy` are set, server allows both plain text and mTLS connections. */
		mtlsPolicy: Option[Schema.MTLSPolicy] = None
	)
	
	case class GoogleCloudNetworksecurityV1CertificateProvider(
	  /** gRPC specific configuration to access the gRPC server to obtain the cert and private key. */
		grpcEndpoint: Option[Schema.GoogleCloudNetworksecurityV1GrpcEndpoint] = None,
	  /** The certificate provider instance specification that will be passed to the data plane, which will be used to load necessary credential information. */
		certificateProviderInstance: Option[Schema.CertificateProviderInstance] = None
	)
	
	case class GoogleCloudNetworksecurityV1GrpcEndpoint(
	  /** Required. The target URI of the gRPC endpoint. Only UDS path is supported, and should start with "unix:". */
		targetUri: Option[String] = None
	)
	
	case class CertificateProviderInstance(
	  /** Required. Plugin instance name, used to locate and load CertificateProvider instance configuration. Set to "google_cloud_private_spiffe" to use Certificate Authority Service certificate provider instance. */
		pluginInstance: Option[String] = None
	)
	
	object MTLSPolicy {
		enum ClientValidationModeEnum extends Enum[ClientValidationModeEnum] { case CLIENT_VALIDATION_MODE_UNSPECIFIED, ALLOW_INVALID_OR_MISSING_CLIENT_CERT, REJECT_INVALID }
	}
	case class MTLSPolicy(
	  /** When the client presents an invalid certificate or no certificate to the load balancer, the `client_validation_mode` specifies how the client connection is handled. Required if the policy is to be used with the Application Load Balancers. For Traffic Director it must be empty. */
		clientValidationMode: Option[Schema.MTLSPolicy.ClientValidationModeEnum] = None,
	  /** Required if the policy is to be used with Traffic Director. For Application Load Balancers it must be empty. Defines the mechanism to obtain the Certificate Authority certificate to validate the client certificate. */
		clientValidationCa: Option[List[Schema.ValidationCA]] = None,
	  /** Reference to the TrustConfig from certificatemanager.googleapis.com namespace. If specified, the chain validation will be performed against certificates configured in the given TrustConfig. Allowed only if the policy is to be used with Application Load Balancers. */
		clientValidationTrustConfig: Option[String] = None
	)
	
	case class ValidationCA(
	  /** gRPC specific configuration to access the gRPC server to obtain the CA certificate. */
		grpcEndpoint: Option[Schema.GoogleCloudNetworksecurityV1GrpcEndpoint] = None,
	  /** The certificate provider instance specification that will be passed to the data plane, which will be used to load necessary credential information. */
		certificateProviderInstance: Option[Schema.CertificateProviderInstance] = None
	)
	
	case class ListClientTlsPoliciesResponse(
	  /** List of ClientTlsPolicy resources. */
		clientTlsPolicies: Option[List[Schema.ClientTlsPolicy]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None
	)
	
	case class ClientTlsPolicy(
	  /** Required. Name of the ClientTlsPolicy resource. It matches the pattern `projects/&#42;/locations/{location}/clientTlsPolicies/{client_tls_policy}` */
		name: Option[String] = None,
	  /** Optional. Free-text description of the resource. */
		description: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. Set of label tags associated with the resource. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Server Name Indication string to present to the server during TLS handshake. E.g: "secure.example.com". */
		sni: Option[String] = None,
	  /** Optional. Defines a mechanism to provision client identity (public and private keys) for peer to peer authentication. The presence of this dictates mTLS. */
		clientCertificate: Option[Schema.GoogleCloudNetworksecurityV1CertificateProvider] = None,
	  /** Optional. Defines the mechanism to obtain the Certificate Authority certificate to validate the server certificate. If empty, client does not validate the server certificate. */
		serverValidationCa: Option[List[Schema.ValidationCA]] = None
	)
	
	case class ListGatewaySecurityPoliciesResponse(
	  /** List of GatewaySecurityPolicies resources. */
		gatewaySecurityPolicies: Option[List[Schema.GatewaySecurityPolicy]] = None,
	  /** If there might be more results than those appearing in this response, then 'next_page_token' is included. To get the next set of results, call this method again using the value of 'next_page_token' as 'page_token'. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class GatewaySecurityPolicy(
	  /** Required. Name of the resource. Name is of the form projects/{project}/locations/{location}/gatewaySecurityPolicies/{gateway_security_policy} gateway_security_policy should match the pattern:(^[a-z]([a-z0-9-]{0,61}[a-z0-9])?$). */
		name: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. Free-text description of the resource. */
		description: Option[String] = None,
	  /** Optional. Name of a TLS Inspection Policy resource that defines how TLS inspection will be performed for any rule(s) which enables it. */
		tlsInspectionPolicy: Option[String] = None
	)
	
	case class ListGatewaySecurityPolicyRulesResponse(
	  /** List of GatewaySecurityPolicyRule resources. */
		gatewaySecurityPolicyRules: Option[List[Schema.GatewaySecurityPolicyRule]] = None,
	  /** If there might be more results than those appearing in this response, then 'next_page_token' is included. To get the next set of results, call this method again using the value of 'next_page_token' as 'page_token'. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object GatewaySecurityPolicyRule {
		enum BasicProfileEnum extends Enum[BasicProfileEnum] { case BASIC_PROFILE_UNSPECIFIED, ALLOW, DENY }
	}
	case class GatewaySecurityPolicyRule(
	  /** Required. Profile which tells what the primitive action should be. */
		basicProfile: Option[Schema.GatewaySecurityPolicyRule.BasicProfileEnum] = None,
	  /** Required. Immutable. Name of the resource. ame is the full resource name so projects/{project}/locations/{location}/gatewaySecurityPolicies/{gateway_security_policy}/rules/{rule} rule should match the pattern: (^[a-z]([a-z0-9-]{0,61}[a-z0-9])?$). */
		name: Option[String] = None,
	  /** Output only. Time when the rule was created. */
		createTime: Option[String] = None,
	  /** Output only. Time when the rule was updated. */
		updateTime: Option[String] = None,
	  /** Required. Whether the rule is enforced. */
		enabled: Option[Boolean] = None,
	  /** Required. Priority of the rule. Lower number corresponds to higher precedence. */
		priority: Option[Int] = None,
	  /** Optional. Free-text description of the resource. */
		description: Option[String] = None,
	  /** Required. CEL expression for matching on session criteria. */
		sessionMatcher: Option[String] = None,
	  /** Optional. CEL expression for matching on L7/application level criteria. */
		applicationMatcher: Option[String] = None,
	  /** Optional. Flag to enable TLS inspection of traffic matching on , can only be true if the parent GatewaySecurityPolicy references a TLSInspectionConfig. */
		tlsInspectionEnabled: Option[Boolean] = None
	)
	
	case class ListUrlListsResponse(
	  /** List of UrlList resources. */
		urlLists: Option[List[Schema.UrlList]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class UrlList(
	  /** Required. Name of the resource provided by the user. Name is of the form projects/{project}/locations/{location}/urlLists/{url_list} url_list should match the pattern:(^[a-z]([a-z0-9-]{0,61}[a-z0-9])?$). */
		name: Option[String] = None,
	  /** Output only. Time when the security policy was created. */
		createTime: Option[String] = None,
	  /** Output only. Time when the security policy was updated. */
		updateTime: Option[String] = None,
	  /** Optional. Free-text description of the resource. */
		description: Option[String] = None,
	  /** Required. FQDNs and URLs. */
		values: Option[List[String]] = None
	)
	
	case class ListTlsInspectionPoliciesResponse(
	  /** List of TlsInspectionPolicies resources. */
		tlsInspectionPolicies: Option[List[Schema.TlsInspectionPolicy]] = None,
	  /** If there might be more results than those appearing in this response, then 'next_page_token' is included. To get the next set of results, call this method again using the value of 'next_page_token' as 'page_token'. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object TlsInspectionPolicy {
		enum MinTlsVersionEnum extends Enum[MinTlsVersionEnum] { case TLS_VERSION_UNSPECIFIED, TLS_1_0, TLS_1_1, TLS_1_2, TLS_1_3 }
		enum TlsFeatureProfileEnum extends Enum[TlsFeatureProfileEnum] { case PROFILE_UNSPECIFIED, PROFILE_COMPATIBLE, PROFILE_MODERN, PROFILE_RESTRICTED, PROFILE_CUSTOM }
	}
	case class TlsInspectionPolicy(
	  /** Required. Name of the resource. Name is of the form projects/{project}/locations/{location}/tlsInspectionPolicies/{tls_inspection_policy} tls_inspection_policy should match the pattern:(^[a-z]([a-z0-9-]{0,61}[a-z0-9])?$). */
		name: Option[String] = None,
	  /** Optional. Free-text description of the resource. */
		description: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Required. A CA pool resource used to issue interception certificates. The CA pool string has a relative resource path following the form "projects/{project}/locations/{location}/caPools/{ca_pool}". */
		caPool: Option[String] = None,
	  /** Optional. A TrustConfig resource used when making a connection to the TLS server. This is a relative resource path following the form "projects/{project}/locations/{location}/trustConfigs/{trust_config}". This is necessary to intercept TLS connections to servers with certificates signed by a private CA or self-signed certificates. Note that Secure Web Proxy does not yet honor this field. */
		trustConfig: Option[String] = None,
	  /** Optional. If FALSE (the default), use our default set of public CAs in addition to any CAs specified in trust_config. These public CAs are currently based on the Mozilla Root Program and are subject to change over time. If TRUE, do not accept our default set of public CAs. Only CAs specified in trust_config will be accepted. This defaults to FALSE (use public CAs in addition to trust_config) for backwards compatibility, but trusting public root CAs is &#42;not recommended&#42; unless the traffic in question is outbound to public web servers. When possible, prefer setting this to "false" and explicitly specifying trusted CAs and certificates in a TrustConfig. Note that Secure Web Proxy does not yet honor this field. */
		excludePublicCaSet: Option[Boolean] = None,
	  /** Optional. Minimum TLS version that the firewall should use when negotiating connections with both clients and servers. If this is not set, then the default value is to allow the broadest set of clients and servers (TLS 1.0 or higher). Setting this to more restrictive values may improve security, but may also prevent the firewall from connecting to some clients or servers. Note that Secure Web Proxy does not yet honor this field. */
		minTlsVersion: Option[Schema.TlsInspectionPolicy.MinTlsVersionEnum] = None,
	  /** Optional. The selected Profile. If this is not set, then the default value is to allow the broadest set of clients and servers ("PROFILE_COMPATIBLE"). Setting this to more restrictive values may improve security, but may also prevent the TLS inspection proxy from connecting to some clients or servers. Note that Secure Web Proxy does not yet honor this field. */
		tlsFeatureProfile: Option[Schema.TlsInspectionPolicy.TlsFeatureProfileEnum] = None,
	  /** Optional. List of custom TLS cipher suites selected. This field is valid only if the selected tls_feature_profile is CUSTOM. The compute.SslPoliciesService.ListAvailableFeatures method returns the set of features that can be specified in this list. Note that Secure Web Proxy does not yet honor this field. */
		customTlsFeatures: Option[List[String]] = None
	)
	
	case class ListAuthzPoliciesResponse(
	  /** The list of `AuthzPolicy` resources. */
		authzPolicies: Option[List[Schema.AuthzPolicy]] = None,
	  /** A token identifying a page of results that the server returns. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object AuthzPolicy {
		enum ActionEnum extends Enum[ActionEnum] { case AUTHZ_ACTION_UNSPECIFIED, ALLOW, DENY, CUSTOM }
	}
	case class AuthzPolicy(
	  /** Required. Identifier. Name of the `AuthzPolicy` resource in the following format: `projects/{project}/locations/{location}/authzPolicies/{authz_policy}`. */
		name: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. A human-readable description of the resource. */
		description: Option[String] = None,
	  /** Optional. Set of labels associated with the `AuthzPolicy` resource. The format must comply with [the following requirements](/compute/docs/labeling-resources#requirements). */
		labels: Option[Map[String, String]] = None,
	  /** Required. Specifies the set of resources to which this policy should be applied to. */
		target: Option[Schema.AuthzPolicyTarget] = None,
	  /** Optional. A list of authorization HTTP rules to match against the incoming request. A policy match occurs when at least one HTTP rule matches the request or when no HTTP rules are specified in the policy. At least one HTTP Rule is required for Allow or Deny Action. Limited to 5 rules. */
		httpRules: Option[List[Schema.AuthzPolicyAuthzRule]] = None,
	  /** Required. Can be one of `ALLOW`, `DENY`, `CUSTOM`. When the action is `CUSTOM`, `customProvider` must be specified. When the action is `ALLOW`, only requests matching the policy will be allowed. When the action is `DENY`, only requests matching the policy will be denied. When a request arrives, the policies are evaluated in the following order: 1. If there is a `CUSTOM` policy that matches the request, the `CUSTOM` policy is evaluated using the custom authorization providers and the request is denied if the provider rejects the request. 2. If there are any `DENY` policies that match the request, the request is denied. 3. If there are no `ALLOW` policies for the resource or if any of the `ALLOW` policies match the request, the request is allowed. 4. Else the request is denied by default if none of the configured AuthzPolicies with `ALLOW` action match the request. */
		action: Option[Schema.AuthzPolicy.ActionEnum] = None,
	  /** Optional. Required if the action is `CUSTOM`. Allows delegating authorization decisions to Cloud IAP or to Service Extensions. One of `cloudIap` or `authzExtension` must be specified. */
		customProvider: Option[Schema.AuthzPolicyCustomProvider] = None
	)
	
	object AuthzPolicyTarget {
		enum LoadBalancingSchemeEnum extends Enum[LoadBalancingSchemeEnum] { case LOAD_BALANCING_SCHEME_UNSPECIFIED, INTERNAL_MANAGED, EXTERNAL_MANAGED, INTERNAL_SELF_MANAGED }
	}
	case class AuthzPolicyTarget(
	  /** Required. All gateways and forwarding rules referenced by this policy and extensions must share the same load balancing scheme. Supported values: `INTERNAL_MANAGED` and `EXTERNAL_MANAGED`. For more information, refer to [Backend services overview](https://cloud.google.com/load-balancing/docs/backend-service). */
		loadBalancingScheme: Option[Schema.AuthzPolicyTarget.LoadBalancingSchemeEnum] = None,
	  /** Required. A list of references to the Forwarding Rules on which this policy will be applied. */
		resources: Option[List[String]] = None
	)
	
	case class AuthzPolicyAuthzRule(
	  /** Optional. Describes properties of one or more sources of a request. */
		from: Option[Schema.AuthzPolicyAuthzRuleFrom] = None,
	  /** Optional. Describes properties of one or more targets of a request. */
		to: Option[Schema.AuthzPolicyAuthzRuleTo] = None,
	  /** Optional. CEL expression that describes the conditions to be satisfied for the action. The result of the CEL expression is ANDed with the from and to. Refer to the CEL language reference for a list of available attributes. */
		when: Option[String] = None
	)
	
	case class AuthzPolicyAuthzRuleFrom(
	  /** Optional. Describes the properties of a request's sources. At least one of sources or notSources must be specified. Limited to 5 sources. A match occurs when ANY source (in sources or notSources) matches the request. Within a single source, the match follows AND semantics across fields and OR semantics within a single field, i.e. a match occurs when ANY principal matches AND ANY ipBlocks match. */
		sources: Option[List[Schema.AuthzPolicyAuthzRuleFromRequestSource]] = None,
	  /** Optional. Describes the negated properties of request sources. Matches requests from sources that do not match the criteria specified in this field. At least one of sources or notSources must be specified. */
		notSources: Option[List[Schema.AuthzPolicyAuthzRuleFromRequestSource]] = None
	)
	
	case class AuthzPolicyAuthzRuleFromRequestSource(
	  /** Optional. A list of identities derived from the client's certificate. This field will not match on a request unless mutual TLS is enabled for the Forwarding rule or Gateway. Each identity is a string whose value is matched against the URI SAN, or DNS SAN or the subject field in the client's certificate. The match can be exact, prefix, suffix or a substring match. One of exact, prefix, suffix or contains must be specified. Limited to 5 principals. */
		principals: Option[List[Schema.AuthzPolicyAuthzRuleStringMatch]] = None,
	  /** Optional. A list of resources to match against the resource of the source VM of a request. Limited to 5 resources. */
		resources: Option[List[Schema.AuthzPolicyAuthzRuleRequestResource]] = None
	)
	
	case class AuthzPolicyAuthzRuleStringMatch(
	  /** The input string must match exactly the string specified here. Examples: &#42; ``abc`` only matches the value ``abc``. */
		exact: Option[String] = None,
	  /** The input string must have the prefix specified here. Note: empty prefix is not allowed, please use regex instead. Examples: &#42; ``abc`` matches the value ``abc.xyz`` */
		prefix: Option[String] = None,
	  /** The input string must have the suffix specified here. Note: empty prefix is not allowed, please use regex instead. Examples: &#42; ``abc`` matches the value ``xyz.abc`` */
		suffix: Option[String] = None,
	  /** The input string must have the substring specified here. Note: empty contains match is not allowed, please use regex instead. Examples: &#42; ``abc`` matches the value ``xyz.abc.def`` */
		contains: Option[String] = None,
	  /** If true, indicates the exact/prefix/suffix/contains matching should be case insensitive. For example, the matcher ``data`` will match both input string ``Data`` and ``data`` if set to true. */
		ignoreCase: Option[Boolean] = None
	)
	
	case class AuthzPolicyAuthzRuleRequestResource(
	  /** Optional. A list of resource tag value permanent IDs to match against the resource manager tags value associated with the source VM of a request. */
		tagValueIdSet: Option[Schema.AuthzPolicyAuthzRuleRequestResourceTagValueIdSet] = None,
	  /** Optional. An IAM service account to match against the source service account of the VM sending the request. */
		iamServiceAccount: Option[Schema.AuthzPolicyAuthzRuleStringMatch] = None
	)
	
	case class AuthzPolicyAuthzRuleRequestResourceTagValueIdSet(
	  /** Required. A list of resource tag value permanent IDs to match against the resource manager tags value associated with the source VM of a request. The match follows AND semantics which means all the ids must match. Limited to 5 matches. */
		ids: Option[List[String]] = None
	)
	
	case class AuthzPolicyAuthzRuleTo(
	  /** Optional. Describes properties of one or more targets of a request. At least one of operations or notOperations must be specified. Limited to 5 operations. A match occurs when ANY operation (in operations or notOperations) matches. Within an operation, the match follows AND semantics across fields and OR semantics within a field, i.e. a match occurs when ANY path matches AND ANY header matches and ANY method matches. */
		operations: Option[List[Schema.AuthzPolicyAuthzRuleToRequestOperation]] = None,
	  /** Optional. Describes the negated properties of the targets of a request. Matches requests for operations that do not match the criteria specified in this field. At least one of operations or notOperations must be specified. */
		notOperations: Option[List[Schema.AuthzPolicyAuthzRuleToRequestOperation]] = None
	)
	
	case class AuthzPolicyAuthzRuleToRequestOperation(
	  /** Optional. A list of headers to match against in http header. */
		headerSet: Option[Schema.AuthzPolicyAuthzRuleToRequestOperationHeaderSet] = None,
	  /** Optional. A list of HTTP Hosts to match against. The match can be one of exact, prefix, suffix, or contains (substring match). Matches are always case sensitive unless the ignoreCase is set. Limited to 5 matches. */
		hosts: Option[List[Schema.AuthzPolicyAuthzRuleStringMatch]] = None,
	  /** Optional. A list of paths to match against. The match can be one of exact, prefix, suffix, or contains (substring match). Matches are always case sensitive unless the ignoreCase is set. Limited to 5 matches. Note that this path match includes the query parameters. For gRPC services, this should be a fully-qualified name of the form /package.service/method. */
		paths: Option[List[Schema.AuthzPolicyAuthzRuleStringMatch]] = None,
	  /** Optional. A list of HTTP methods to match against. Each entry must be a valid HTTP method name (GET, PUT, POST, HEAD, PATCH, DELETE, OPTIONS). It only allows exact match and is always case sensitive. */
		methods: Option[List[String]] = None
	)
	
	case class AuthzPolicyAuthzRuleToRequestOperationHeaderSet(
	  /** Required. A list of headers to match against in http header. The match can be one of exact, prefix, suffix, or contains (substring match). The match follows AND semantics which means all the headers must match. Matches are always case sensitive unless the ignoreCase is set. Limited to 5 matches. */
		headers: Option[List[Schema.AuthzPolicyAuthzRuleHeaderMatch]] = None
	)
	
	case class AuthzPolicyAuthzRuleHeaderMatch(
	  /** Optional. Specifies the name of the header in the request. */
		name: Option[String] = None,
	  /** Optional. Specifies how the header match will be performed. */
		value: Option[Schema.AuthzPolicyAuthzRuleStringMatch] = None
	)
	
	case class AuthzPolicyCustomProvider(
	  /** Optional. Delegates authorization decisions to Cloud IAP. Applicable only for managed load balancers. Enabling Cloud IAP at the AuthzPolicy level is not compatible with Cloud IAP settings in the BackendService. Enabling IAP in both places will result in request failure. Ensure that IAP is enabled in either the AuthzPolicy or the BackendService but not in both places. */
		cloudIap: Option[Schema.AuthzPolicyCustomProviderCloudIap] = None,
	  /** Optional. Delegate authorization decision to user authored Service Extension. Only one of cloudIap or authzExtension can be specified. */
		authzExtension: Option[Schema.AuthzPolicyCustomProviderAuthzExtension] = None
	)
	
	case class AuthzPolicyCustomProviderCloudIap(
	
	)
	
	case class AuthzPolicyCustomProviderAuthzExtension(
	  /** Required. A list of references to authorization extensions that will be invoked for requests matching this policy. Limited to 1 custom provider. */
		resources: Option[List[String]] = None
	)
	
	case class ListSecurityProfileGroupsResponse(
	  /** List of SecurityProfileGroups resources. */
		securityProfileGroups: Option[List[Schema.SecurityProfileGroup]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None
	)
	
	case class SecurityProfileGroup(
	  /** Immutable. Identifier. Name of the SecurityProfileGroup resource. It matches pattern `projects|organizations/&#42;/locations/{location}/securityProfileGroups/{security_profile_group}`. */
		name: Option[String] = None,
	  /** Optional. An optional description of the profile group. Max length 2048 characters. */
		description: Option[String] = None,
	  /** Output only. Resource creation timestamp. */
		createTime: Option[String] = None,
	  /** Output only. Last resource update timestamp. */
		updateTime: Option[String] = None,
	  /** Output only. This checksum is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Optional. Labels as key value pairs. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Reference to a SecurityProfile with the ThreatPrevention configuration. */
		threatPreventionProfile: Option[String] = None,
	  /** Optional. Reference to a SecurityProfile with the CustomMirroring configuration. */
		customMirroringProfile: Option[String] = None
	)
	
	case class ListSecurityProfilesResponse(
	  /** List of SecurityProfile resources. */
		securityProfiles: Option[List[Schema.SecurityProfile]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None
	)
	
	object SecurityProfile {
		enum TypeEnum extends Enum[TypeEnum] { case PROFILE_TYPE_UNSPECIFIED, THREAT_PREVENTION, CUSTOM_MIRRORING }
	}
	case class SecurityProfile(
	  /** The threat prevention configuration for the SecurityProfile. */
		threatPreventionProfile: Option[Schema.ThreatPreventionProfile] = None,
	  /** The custom Packet Mirroring v2 configuration for the SecurityProfile. */
		customMirroringProfile: Option[Schema.CustomMirroringProfile] = None,
	  /** Immutable. Identifier. Name of the SecurityProfile resource. It matches pattern `projects|organizations/&#42;/locations/{location}/securityProfiles/{security_profile}`. */
		name: Option[String] = None,
	  /** Optional. An optional description of the profile. Max length 512 characters. */
		description: Option[String] = None,
	  /** Output only. Resource creation timestamp. */
		createTime: Option[String] = None,
	  /** Output only. Last resource update timestamp. */
		updateTime: Option[String] = None,
	  /** Output only. This checksum is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Optional. Labels as key value pairs. */
		labels: Option[Map[String, String]] = None,
	  /** Immutable. The single ProfileType that the SecurityProfile resource configures. */
		`type`: Option[Schema.SecurityProfile.TypeEnum] = None
	)
	
	case class ThreatPreventionProfile(
	  /** Optional. Configuration for overriding threats actions by severity match. */
		severityOverrides: Option[List[Schema.SeverityOverride]] = None,
	  /** Optional. Configuration for overriding threats actions by threat_id match. If a threat is matched both by configuration provided in severity_overrides and threat_overrides, the threat_overrides action is applied. */
		threatOverrides: Option[List[Schema.ThreatOverride]] = None
	)
	
	object SeverityOverride {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, INFORMATIONAL, LOW, MEDIUM, HIGH, CRITICAL }
		enum ActionEnum extends Enum[ActionEnum] { case THREAT_ACTION_UNSPECIFIED, DEFAULT_ACTION, ALLOW, ALERT, DENY }
	}
	case class SeverityOverride(
	  /** Required. Severity level to match. */
		severity: Option[Schema.SeverityOverride.SeverityEnum] = None,
	  /** Required. Threat action override. */
		action: Option[Schema.SeverityOverride.ActionEnum] = None
	)
	
	object ThreatOverride {
		enum TypeEnum extends Enum[TypeEnum] { case THREAT_TYPE_UNSPECIFIED, UNKNOWN, VULNERABILITY, ANTIVIRUS, SPYWARE, DNS }
		enum ActionEnum extends Enum[ActionEnum] { case THREAT_ACTION_UNSPECIFIED, DEFAULT_ACTION, ALLOW, ALERT, DENY }
	}
	case class ThreatOverride(
	  /** Required. Vendor-specific ID of a threat to override. */
		threatId: Option[String] = None,
	  /** Output only. Type of the threat (read only). */
		`type`: Option[Schema.ThreatOverride.TypeEnum] = None,
	  /** Required. Threat action override. For some threat types, only a subset of actions applies. */
		action: Option[Schema.ThreatOverride.ActionEnum] = None
	)
	
	case class CustomMirroringProfile(
	  /** Required. The MirroringEndpointGroup to which traffic associated with the SP should be mirrored. */
		mirroringEndpointGroup: Option[String] = None
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
	
	case class GoogleIamV1SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.GoogleIamV1Policy] = None,
	  /** OPTIONAL: A FieldMask specifying which fields of the policy to modify. Only the fields in the mask will be modified. If no mask is provided, the following default mask is used: `paths: "bindings, etag"` */
		updateMask: Option[String] = None
	)
	
	case class GoogleIamV1Policy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.GoogleIamV1Binding]] = None,
	  /** Specifies cloud audit logging configuration for this policy. */
		auditConfigs: Option[List[Schema.GoogleIamV1AuditConfig]] = None,
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An `etag` is returned in the response to `getIamPolicy`, and systems are expected to put that etag in the request to `setIamPolicy` to ensure that their change will be applied to the same version of the policy. &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. */
		etag: Option[String] = None
	)
	
	case class GoogleIamV1Binding(
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
	
	case class GoogleIamV1AuditConfig(
	  /** Specifies a service that will be enabled for audit logging. For example, `storage.googleapis.com`, `cloudsql.googleapis.com`. `allServices` is a special value that covers all services. */
		service: Option[String] = None,
	  /** The configuration for logging of each type of permission. */
		auditLogConfigs: Option[List[Schema.GoogleIamV1AuditLogConfig]] = None
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
	
	case class GoogleIamV1TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class GoogleIamV1TestIamPermissionsResponse(
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
