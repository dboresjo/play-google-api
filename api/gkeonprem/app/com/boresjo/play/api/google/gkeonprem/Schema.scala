package com.boresjo.play.api.google.gkeonprem

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
	
	object BareMetalCluster {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PROVISIONING, RUNNING, RECONCILING, STOPPING, ERROR, DEGRADED }
	}
	case class BareMetalCluster(
	  /** Immutable. The bare metal user cluster resource name. */
		name: Option[String] = None,
	  /** Required. The admin cluster this bare metal user cluster belongs to. This is the full resource name of the admin cluster's fleet membership. */
		adminClusterMembership: Option[String] = None,
	  /** A human readable description of this bare metal user cluster. */
		description: Option[String] = None,
	  /** Required. The Anthos clusters on bare metal version for your user cluster. */
		bareMetalVersion: Option[String] = None,
	  /** Output only. The unique identifier of the bare metal user cluster. */
		uid: Option[String] = None,
	  /** Output only. The current state of the bare metal user cluster. */
		state: Option[Schema.BareMetalCluster.StateEnum] = None,
	  /** Output only. The IP address of the bare metal user cluster's API server. */
		endpoint: Option[String] = None,
	  /** Output only. If set, there are currently changes in flight to the bare metal user cluster. */
		reconciling: Option[Boolean] = None,
	  /** Output only. The time when the bare metal user cluster was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the bare metal user cluster was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. The time when the bare metal user cluster was deleted. If the resource is not deleted, this must be empty */
		deleteTime: Option[String] = None,
	  /** Output only. The object name of the bare metal user cluster custom resource on the associated admin cluster. This field is used to support conflicting names when enrolling existing clusters to the API. When used as a part of cluster enrollment, this field will differ from the name in the resource name. For new clusters, this field will match the user provided cluster name and be visible in the last component of the resource name. It is not modifiable. When the local name and cluster name differ, the local name is used in the admin cluster controller logs. You use the cluster name when accessing the cluster using bmctl and kubectl. */
		localName: Option[String] = None,
	  /** Output only. This checksum is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. Allows clients to perform consistent read-modify-writes through optimistic concurrency control. */
		etag: Option[String] = None,
	  /** Annotations on the bare metal user cluster. This field has the same restrictions as Kubernetes annotations. The total size of all keys and values combined is limited to 256k. Key can have 2 segments: prefix (optional) and name (required), separated by a slash (/). Prefix must be a DNS subdomain. Name must be 63 characters or less, begin and end with alphanumerics, with dashes (-), underscores (_), dots (.), and alphanumerics between. */
		annotations: Option[Map[String, String]] = None,
	  /** Required. Network configuration. */
		networkConfig: Option[Schema.BareMetalNetworkConfig] = None,
	  /** Required. Control plane configuration. */
		controlPlane: Option[Schema.BareMetalControlPlaneConfig] = None,
	  /** Required. Load balancer configuration. */
		loadBalancer: Option[Schema.BareMetalLoadBalancerConfig] = None,
	  /** Required. Storage configuration. */
		storage: Option[Schema.BareMetalStorageConfig] = None,
	  /** Proxy configuration. */
		proxy: Option[Schema.BareMetalProxyConfig] = None,
	  /** Cluster operations configuration. */
		clusterOperations: Option[Schema.BareMetalClusterOperationsConfig] = None,
	  /** Maintenance configuration. */
		maintenanceConfig: Option[Schema.BareMetalMaintenanceConfig] = None,
	  /** Workload node configuration. */
		nodeConfig: Option[Schema.BareMetalWorkloadNodeConfig] = None,
	  /** Output only. Fleet configuration for the cluster. */
		fleet: Option[Schema.Fleet] = None,
	  /** Output only. Detailed cluster status. */
		status: Option[Schema.ResourceStatus] = None,
	  /** Output only. The result of the preflight check. */
		validationCheck: Option[Schema.ValidationCheck] = None,
	  /** Security related setting configuration. */
		securityConfig: Option[Schema.BareMetalSecurityConfig] = None,
	  /** Output only. Status of on-going maintenance tasks. */
		maintenanceStatus: Option[Schema.BareMetalMaintenanceStatus] = None,
	  /** Output only. The resource name of the bare metal admin cluster managing this user cluster. */
		adminClusterName: Option[String] = None,
	  /** Node access related configurations. */
		nodeAccessConfig: Option[Schema.BareMetalNodeAccessConfig] = None,
	  /** OS environment related configurations. */
		osEnvironmentConfig: Option[Schema.BareMetalOsEnvironmentConfig] = None,
	  /** Binary Authorization related configurations. */
		binaryAuthorization: Option[Schema.BinaryAuthorization] = None,
	  /** The cluster upgrade policy. */
		upgradePolicy: Option[Schema.BareMetalClusterUpgradePolicy] = None
	)
	
	case class BareMetalNetworkConfig(
	  /** Configuration for island mode CIDR. In an island-mode network, nodes have unique IP addresses, but pods don't have unique addresses across clusters. This doesn't cause problems because pods in one cluster never directly communicate with pods in another cluster. Instead, there are gateways that mediate between a pod in one cluster and a pod in another cluster. */
		islandModeCidr: Option[Schema.BareMetalIslandModeCidrConfig] = None,
	  /** Enables the use of advanced Anthos networking features, such as Bundled Load Balancing with BGP or the egress NAT gateway. Setting configuration for advanced networking features will automatically set this flag. */
		advancedNetworking: Option[Boolean] = None,
	  /** Configuration for multiple network interfaces. */
		multipleNetworkInterfacesConfig: Option[Schema.BareMetalMultipleNetworkInterfacesConfig] = None,
	  /** Configuration for SR-IOV. */
		srIovConfig: Option[Schema.BareMetalSrIovConfig] = None
	)
	
	case class BareMetalIslandModeCidrConfig(
	  /** Required. All services in the cluster are assigned an RFC1918 IPv4 address from these ranges. This field is mutable after creation starting with version 1.15. */
		serviceAddressCidrBlocks: Option[List[String]] = None,
	  /** Required. All pods in the cluster are assigned an RFC1918 IPv4 address from these ranges. This field cannot be changed after creation. */
		podAddressCidrBlocks: Option[List[String]] = None
	)
	
	case class BareMetalMultipleNetworkInterfacesConfig(
	  /** Whether to enable multiple network interfaces for your pods. When set network_config.advanced_networking is automatically set to true. */
		enabled: Option[Boolean] = None
	)
	
	case class BareMetalSrIovConfig(
	  /** Whether to install the SR-IOV operator. */
		enabled: Option[Boolean] = None
	)
	
	case class BareMetalControlPlaneConfig(
	  /** Required. Configures the node pool running the control plane. */
		controlPlaneNodePoolConfig: Option[Schema.BareMetalControlPlaneNodePoolConfig] = None,
	  /** Customizes the default API server args. Only a subset of customized flags are supported. For the exact format, refer to the [API server documentation](https://kubernetes.io/docs/reference/command-line-tools-reference/kube-apiserver/). */
		apiServerArgs: Option[List[Schema.BareMetalApiServerArgument]] = None
	)
	
	case class BareMetalControlPlaneNodePoolConfig(
	  /** Required. The generic configuration for a node pool running the control plane. */
		nodePoolConfig: Option[Schema.BareMetalNodePoolConfig] = None
	)
	
	object BareMetalNodePoolConfig {
		enum OperatingSystemEnum extends Enum[OperatingSystemEnum] { case OPERATING_SYSTEM_UNSPECIFIED, LINUX }
	}
	case class BareMetalNodePoolConfig(
	  /** Required. The list of machine addresses in the bare metal node pool. */
		nodeConfigs: Option[List[Schema.BareMetalNodeConfig]] = None,
	  /** Specifies the nodes operating system (default: LINUX). */
		operatingSystem: Option[Schema.BareMetalNodePoolConfig.OperatingSystemEnum] = None,
	  /** The initial taints assigned to nodes of this node pool. */
		taints: Option[List[Schema.NodeTaint]] = None,
	  /** The labels assigned to nodes of this node pool. An object containing a list of key/value pairs. Example: { "name": "wrench", "mass": "1.3kg", "count": "3" }. */
		labels: Option[Map[String, String]] = None,
	  /** The modifiable kubelet configurations for the bare metal machines. */
		kubeletConfig: Option[Schema.BareMetalKubeletConfig] = None
	)
	
	case class BareMetalNodeConfig(
	  /** The default IPv4 address for SSH access and Kubernetes node. Example: 192.168.0.1 */
		nodeIp: Option[String] = None,
	  /** The labels assigned to this node. An object containing a list of key/value pairs. The labels here, unioned with the labels set on BareMetalNodePoolConfig are the set of labels that will be applied to the node. If there are any conflicts, the BareMetalNodeConfig labels take precedence. Example: { "name": "wrench", "mass": "1.3kg", "count": "3" }. */
		labels: Option[Map[String, String]] = None
	)
	
	object NodeTaint {
		enum EffectEnum extends Enum[EffectEnum] { case EFFECT_UNSPECIFIED, NO_SCHEDULE, PREFER_NO_SCHEDULE, NO_EXECUTE }
	}
	case class NodeTaint(
	  /** Key associated with the effect. */
		key: Option[String] = None,
	  /** Value associated with the effect. */
		value: Option[String] = None,
	  /** The taint effect. */
		effect: Option[Schema.NodeTaint.EffectEnum] = None
	)
	
	case class BareMetalKubeletConfig(
	  /** The limit of registry pulls per second. Setting this value to 0 means no limit. Updating this field may impact scalability by changing the amount of traffic produced by image pulls. Defaults to 5. */
		registryPullQps: Option[Int] = None,
	  /** The maximum size of bursty pulls, temporarily allows pulls to burst to this number, while still not exceeding registry_pull_qps. The value must not be a negative number. Updating this field may impact scalability by changing the amount of traffic produced by image pulls. Defaults to 10. */
		registryBurst: Option[Int] = None,
	  /** Prevents the Kubelet from pulling multiple images at a time. We recommend &#42;not&#42; changing the default value on nodes that run docker daemon with version < 1.9 or an Another Union File System (Aufs) storage backend. Issue https://github.com/kubernetes/kubernetes/issues/10959 has more details. */
		serializeImagePullsDisabled: Option[Boolean] = None
	)
	
	case class BareMetalApiServerArgument(
	  /** Required. The argument name as it appears on the API Server command line, make sure to remove the leading dashes. */
		argument: Option[String] = None,
	  /** Required. The value of the arg as it will be passed to the API Server command line. */
		value: Option[String] = None
	)
	
	case class BareMetalLoadBalancerConfig(
	  /** The VIPs used by the load balancer. */
		vipConfig: Option[Schema.BareMetalVipConfig] = None,
	  /** Configures the ports that the load balancer will listen on. */
		portConfig: Option[Schema.BareMetalPortConfig] = None,
	  /** Configuration for MetalLB load balancers. */
		metalLbConfig: Option[Schema.BareMetalMetalLbConfig] = None,
	  /** Manually configured load balancers. */
		manualLbConfig: Option[Schema.BareMetalManualLbConfig] = None,
	  /** Configuration for BGP typed load balancers. When set network_config.advanced_networking is automatically set to true. */
		bgpLbConfig: Option[Schema.BareMetalBgpLbConfig] = None
	)
	
	case class BareMetalVipConfig(
	  /** The VIP which you previously set aside for the Kubernetes API of this bare metal user cluster. */
		controlPlaneVip: Option[String] = None,
	  /** The VIP which you previously set aside for ingress traffic into this bare metal user cluster. */
		ingressVip: Option[String] = None
	)
	
	case class BareMetalPortConfig(
	  /** The port that control plane hosted load balancers will listen on. */
		controlPlaneLoadBalancerPort: Option[Int] = None
	)
	
	case class BareMetalMetalLbConfig(
	  /** Required. AddressPools is a list of non-overlapping IP pools used by load balancer typed services. All addresses must be routable to load balancer nodes. IngressVIP must be included in the pools. */
		addressPools: Option[List[Schema.BareMetalLoadBalancerAddressPool]] = None,
	  /** Specifies the node pool running the load balancer. L2 connectivity is required among nodes in this pool. If missing, the control plane node pool is used as the load balancer pool. */
		loadBalancerNodePoolConfig: Option[Schema.BareMetalLoadBalancerNodePoolConfig] = None
	)
	
	case class BareMetalLoadBalancerAddressPool(
	  /** Required. The name of the address pool. */
		pool: Option[String] = None,
	  /** Required. The addresses that are part of this pool. Each address must be either in the CIDR form (1.2.3.0/24) or range form (1.2.3.1-1.2.3.5). */
		addresses: Option[List[String]] = None,
	  /** If true, avoid using IPs ending in .0 or .255. This avoids buggy consumer devices mistakenly dropping IPv4 traffic for those special IP addresses. */
		avoidBuggyIps: Option[Boolean] = None,
	  /** If true, prevent IP addresses from being automatically assigned. */
		manualAssign: Option[Boolean] = None
	)
	
	case class BareMetalLoadBalancerNodePoolConfig(
	  /** The generic configuration for a node pool running a load balancer. */
		nodePoolConfig: Option[Schema.BareMetalNodePoolConfig] = None
	)
	
	case class BareMetalManualLbConfig(
	  /** Whether manual load balancing is enabled. */
		enabled: Option[Boolean] = None
	)
	
	case class BareMetalBgpLbConfig(
	  /** Required. BGP autonomous system number (ASN) of the cluster. This field can be updated after cluster creation. */
		asn: Option[String] = None,
	  /** Required. The list of BGP peers that the cluster will connect to. At least one peer must be configured for each control plane node. Control plane nodes will connect to these peers to advertise the control plane VIP. The Services load balancer also uses these peers by default. This field can be updated after cluster creation. */
		bgpPeerConfigs: Option[List[Schema.BareMetalBgpPeerConfig]] = None,
	  /** Required. AddressPools is a list of non-overlapping IP pools used by load balancer typed services. All addresses must be routable to load balancer nodes. IngressVIP must be included in the pools. */
		addressPools: Option[List[Schema.BareMetalLoadBalancerAddressPool]] = None,
	  /** Specifies the node pool running data plane load balancing. L2 connectivity is required among nodes in this pool. If missing, the control plane node pool is used for data plane load balancing. */
		loadBalancerNodePoolConfig: Option[Schema.BareMetalLoadBalancerNodePoolConfig] = None
	)
	
	case class BareMetalBgpPeerConfig(
	  /** Required. BGP autonomous system number (ASN) for the network that contains the external peer device. */
		asn: Option[String] = None,
	  /** Required. The IP address of the external peer device. */
		ipAddress: Option[String] = None,
	  /** The IP address of the control plane node that connects to the external peer. If you don't specify any control plane nodes, all control plane nodes can connect to the external peer. If you specify one or more IP addresses, only the nodes specified participate in peering sessions. */
		controlPlaneNodes: Option[List[String]] = None
	)
	
	case class BareMetalStorageConfig(
	  /** Required. Specifies the config for local PersistentVolumes backed by subdirectories in a shared filesystem. These subdirectores are automatically created during cluster creation. */
		lvpShareConfig: Option[Schema.BareMetalLvpShareConfig] = None,
	  /** Required. Specifies the config for local PersistentVolumes backed by mounted node disks. These disks need to be formatted and mounted by the user, which can be done before or after cluster creation. */
		lvpNodeMountsConfig: Option[Schema.BareMetalLvpConfig] = None
	)
	
	case class BareMetalLvpShareConfig(
	  /** Required. Defines the machine path and storage class for the LVP Share. */
		lvpConfig: Option[Schema.BareMetalLvpConfig] = None,
	  /** The number of subdirectories to create under path. */
		sharedPathPvCount: Option[Int] = None
	)
	
	case class BareMetalLvpConfig(
	  /** Required. The host machine path. */
		path: Option[String] = None,
	  /** Required. The StorageClass name that PVs will be created with. */
		storageClass: Option[String] = None
	)
	
	case class BareMetalProxyConfig(
	  /** Required. Specifies the address of your proxy server. Examples: `http://domain` Do not provide credentials in the format `http://(username:password@)domain` these will be rejected by the server. */
		uri: Option[String] = None,
	  /** A list of IPs, hostnames, and domains that should skip the proxy. Examples: ["127.0.0.1", "example.com", ".corp", "localhost"]. */
		noProxy: Option[List[String]] = None
	)
	
	case class BareMetalClusterOperationsConfig(
	  /** Whether collection of application logs/metrics should be enabled (in addition to system logs/metrics). */
		enableApplicationLogs: Option[Boolean] = None
	)
	
	case class BareMetalMaintenanceConfig(
	  /** Required. All IPv4 address from these ranges will be placed into maintenance mode. Nodes in maintenance mode will be cordoned and drained. When both of these are true, the "baremetal.cluster.gke.io/maintenance" annotation will be set on the node resource. */
		maintenanceAddressCidrBlocks: Option[List[String]] = None
	)
	
	object BareMetalWorkloadNodeConfig {
		enum ContainerRuntimeEnum extends Enum[ContainerRuntimeEnum] { case CONTAINER_RUNTIME_UNSPECIFIED, CONTAINERD }
	}
	case class BareMetalWorkloadNodeConfig(
	  /** The maximum number of pods a node can run. The size of the CIDR range assigned to the node will be derived from this parameter. */
		maxPodsPerNode: Option[String] = None,
	  /** Specifies which container runtime will be used. */
		containerRuntime: Option[Schema.BareMetalWorkloadNodeConfig.ContainerRuntimeEnum] = None
	)
	
	case class Fleet(
	  /** Output only. The name of the managed fleet Membership resource associated to this cluster. Membership names are formatted as `projects//locations//memberships/`. */
		membership: Option[String] = None
	)
	
	case class ResourceStatus(
	  /** Human-friendly representation of the error message from controller. The error message can be temporary as the controller controller creates a cluster or node pool. If the error message persists for a longer period of time, it can be used to surface error message to indicate real problems requiring user intervention. */
		errorMessage: Option[String] = None,
	  /** ResourceCondition provide a standard mechanism for higher-level status reporting from controller. */
		conditions: Option[List[Schema.ResourceCondition]] = None,
	  /** Reflect current version of the resource. */
		version: Option[String] = None,
	  /** Shows the mapping of a given version to the number of machines under this version. */
		versions: Option[Schema.Versions] = None
	)
	
	object ResourceCondition {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STATE_TRUE, STATE_FALSE, STATE_UNKNOWN }
	}
	case class ResourceCondition(
	  /** Type of the condition. (e.g., ClusterRunning, NodePoolRunning or ServerSidePreflightReady) */
		`type`: Option[String] = None,
	  /** Machine-readable message indicating details about last transition. */
		reason: Option[String] = None,
	  /** Human-readable message indicating details about last transition. */
		message: Option[String] = None,
	  /** Last time the condition transit from one status to another. */
		lastTransitionTime: Option[String] = None,
	  /** state of the condition. */
		state: Option[Schema.ResourceCondition.StateEnum] = None
	)
	
	case class Versions(
	  /** Shows the mapping of a given version to the number of machines under this version. */
		versions: Option[List[Schema.Version]] = None
	)
	
	case class Version(
	  /** Resource version. */
		version: Option[String] = None,
	  /** Number of machines under the above version. */
		count: Option[String] = None
	)
	
	object ValidationCheck {
		enum OptionEnum extends Enum[OptionEnum] { case OPTIONS_UNSPECIFIED, SKIP_VALIDATION_CHECK_BLOCKING, SKIP_VALIDATION_ALL }
		enum ScenarioEnum extends Enum[ScenarioEnum] { case SCENARIO_UNSPECIFIED, CREATE, UPDATE }
	}
	case class ValidationCheck(
	  /** Options used for the validation check */
		option: Option[Schema.ValidationCheck.OptionEnum] = None,
	  /** Output only. The detailed validation check status. */
		status: Option[Schema.ValidationCheckStatus] = None,
	  /** Output only. The scenario when the preflight checks were run. */
		scenario: Option[Schema.ValidationCheck.ScenarioEnum] = None
	)
	
	case class ValidationCheckStatus(
	  /** Individual checks which failed as part of the Preflight check execution. */
		result: Option[List[Schema.ValidationCheckResult]] = None
	)
	
	object ValidationCheckResult {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNKNOWN, STATE_FAILURE, STATE_SKIPPED, STATE_FATAL, STATE_WARNING }
	}
	case class ValidationCheckResult(
	  /** The validation check state. */
		state: Option[Schema.ValidationCheckResult.StateEnum] = None,
	  /** The description of the validation check. */
		description: Option[String] = None,
	  /** The category of the validation. */
		category: Option[String] = None,
	  /** A human-readable message of the check failure. */
		reason: Option[String] = None,
	  /** Detailed failure information, which might be unformatted. */
		details: Option[String] = None
	)
	
	case class BareMetalSecurityConfig(
	  /** Configures user access to the user cluster. */
		authorization: Option[Schema.Authorization] = None
	)
	
	case class Authorization(
	  /** For VMware and bare metal user clusters, users will be granted the cluster-admin role on the cluster, which provides full administrative access to the cluster. For bare metal admin clusters, users will be granted the cluster-view role, which limits users to read-only access. */
		adminUsers: Option[List[Schema.ClusterUser]] = None
	)
	
	case class ClusterUser(
	  /** Required. The name of the user, e.g. `my-gcp-id@gmail.com`. */
		username: Option[String] = None
	)
	
	case class BareMetalMaintenanceStatus(
	  /** The maintenance status of node machines. */
		machineDrainStatus: Option[Schema.BareMetalMachineDrainStatus] = None
	)
	
	case class BareMetalMachineDrainStatus(
	  /** The list of draning machines. */
		drainingMachines: Option[List[Schema.BareMetalDrainingMachine]] = None,
	  /** The list of drained machines. */
		drainedMachines: Option[List[Schema.BareMetalDrainedMachine]] = None
	)
	
	case class BareMetalDrainingMachine(
	  /** Draining machine IP address. */
		nodeIp: Option[String] = None,
	  /** The count of pods yet to drain. */
		podCount: Option[Int] = None
	)
	
	case class BareMetalDrainedMachine(
	  /** Drained machine IP address. */
		nodeIp: Option[String] = None
	)
	
	case class BareMetalNodeAccessConfig(
	  /** LoginUser is the user name used to access node machines. It defaults to "root" if not set. */
		loginUser: Option[String] = None
	)
	
	case class BareMetalOsEnvironmentConfig(
	  /** Whether the package repo should not be included when initializing bare metal machines. */
		packageRepoExcluded: Option[Boolean] = None
	)
	
	object BinaryAuthorization {
		enum EvaluationModeEnum extends Enum[EvaluationModeEnum] { case EVALUATION_MODE_UNSPECIFIED, DISABLED, PROJECT_SINGLETON_POLICY_ENFORCE }
	}
	case class BinaryAuthorization(
	  /** Mode of operation for binauthz policy evaluation. If unspecified, defaults to DISABLED. */
		evaluationMode: Option[Schema.BinaryAuthorization.EvaluationModeEnum] = None
	)
	
	object BareMetalClusterUpgradePolicy {
		enum PolicyEnum extends Enum[PolicyEnum] { case NODE_POOL_POLICY_UNSPECIFIED, SERIAL, CONCURRENT }
	}
	case class BareMetalClusterUpgradePolicy(
	  /** Specifies which upgrade policy to use. */
		policy: Option[Schema.BareMetalClusterUpgradePolicy.PolicyEnum] = None,
	  /** Output only. Pause is used to show the upgrade pause status. It's view only for now. */
		pause: Option[Boolean] = None
	)
	
	case class EnrollBareMetalClusterRequest(
	  /** User provided OnePlatform identifier that is used as part of the resource name. This must be unique among all bare metal clusters within a project and location and will return a 409 if the cluster already exists. (https://tools.ietf.org/html/rfc1123) format. */
		bareMetalClusterId: Option[String] = None,
	  /** Optional. The object name of the bare metal cluster custom resource on the associated admin cluster. This field is used to support conflicting resource names when enrolling existing clusters to the API. When not provided, this field will resolve to the bare_metal_cluster_id. Otherwise, it must match the object name of the bare metal cluster custom resource. It is not modifiable outside / beyond the enrollment operation. */
		localName: Option[String] = None,
	  /** Required. The admin cluster this bare metal user cluster belongs to. This is the full resource name of the admin cluster's fleet membership. In the future, references to other resource types might be allowed if admin clusters are modeled as their own resources. */
		adminClusterMembership: Option[String] = None
	)
	
	case class ListBareMetalClustersResponse(
	  /** The list of bare metal Clusters. */
		bareMetalClusters: Option[List[Schema.BareMetalCluster]] = None,
	  /** A token identifying a page of results the server should return. If the token is not empty this means that more results are available and should be retrieved by repeating the request with the provided page token. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class QueryBareMetalVersionConfigResponse(
	  /** List of available versions to install or to upgrade to. */
		versions: Option[List[Schema.BareMetalVersionInfo]] = None
	)
	
	case class BareMetalVersionInfo(
	  /** Version number e.g. 1.13.1. */
		version: Option[String] = None,
	  /** If set, the cluster dependencies (e.g. the admin cluster, other user clusters managed by the same admin cluster, version skew policy, etc) must be upgraded before this version can be installed or upgraded to. */
		hasDependencies: Option[Boolean] = None,
	  /** The list of upgrade dependencies for this version. */
		dependencies: Option[List[Schema.UpgradeDependency]] = None
	)
	
	case class UpgradeDependency(
	  /** Resource name of the dependency. */
		resourceName: Option[String] = None,
	  /** Current version of the dependency e.g. 1.15.0. */
		currentVersion: Option[String] = None,
	  /** Target version of the dependency e.g. 1.16.1. This is the version the dependency needs to be upgraded to before a resource can be upgraded. */
		targetVersion: Option[String] = None,
	  /** Membership names are formatted as `projects//locations//memberships/`. */
		membership: Option[String] = None
	)
	
	case class EnrollVmwareClusterRequest(
	  /** User provided OnePlatform identifier that is used as part of the resource name. This must be unique among all GKE on-prem clusters within a project and location and will return a 409 if the cluster already exists. (https://tools.ietf.org/html/rfc1123) format. */
		vmwareClusterId: Option[String] = None,
	  /** Optional. The object name of the VMware OnPremUserCluster custom resource on the associated admin cluster. This field is used to support conflicting resource names when enrolling existing clusters to the API. When not provided, this field will resolve to the vmware_cluster_id. Otherwise, it must match the object name of the VMware OnPremUserCluster custom resource. It is not modifiable outside / beyond the enrollment operation. */
		localName: Option[String] = None,
	  /** Required. The admin cluster this VMware user cluster belongs to. This is the full resource name of the admin cluster's fleet membership. In the future, references to other resource types might be allowed if admin clusters are modeled as their own resources. */
		adminClusterMembership: Option[String] = None,
	  /** Validate the request without actually doing any updates. */
		validateOnly: Option[Boolean] = None
	)
	
	object VmwareCluster {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PROVISIONING, RUNNING, RECONCILING, STOPPING, ERROR, DEGRADED }
	}
	case class VmwareCluster(
	  /** Immutable. The VMware user cluster resource name. */
		name: Option[String] = None,
	  /** Required. The admin cluster this VMware user cluster belongs to. This is the full resource name of the admin cluster's fleet membership. In the future, references to other resource types might be allowed if admin clusters are modeled as their own resources. */
		adminClusterMembership: Option[String] = None,
	  /** A human readable description of this VMware user cluster. */
		description: Option[String] = None,
	  /** Required. The Anthos clusters on the VMware version for your user cluster. */
		onPremVersion: Option[String] = None,
	  /** Output only. The unique identifier of the VMware user cluster. */
		uid: Option[String] = None,
	  /** Output only. The current state of VMware user cluster. */
		state: Option[Schema.VmwareCluster.StateEnum] = None,
	  /** Output only. The DNS name of VMware user cluster's API server. */
		endpoint: Option[String] = None,
	  /** Output only. If set, there are currently changes in flight to the VMware user cluster. */
		reconciling: Option[Boolean] = None,
	  /** Output only. The time at which VMware user cluster was created. */
		createTime: Option[String] = None,
	  /** Output only. The time at which VMware user cluster was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. The object name of the VMware OnPremUserCluster custom resource on the associated admin cluster. This field is used to support conflicting names when enrolling existing clusters to the API. When used as a part of cluster enrollment, this field will differ from the ID in the resource name. For new clusters, this field will match the user provided cluster name and be visible in the last component of the resource name. It is not modifiable. All users should use this name to access their cluster using gkectl or kubectl and should expect to see the local name when viewing admin cluster controller logs. */
		localName: Option[String] = None,
	  /** This checksum is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. Allows clients to perform consistent read-modify-writes through optimistic concurrency control. */
		etag: Option[String] = None,
	  /** Annotations on the VMware user cluster. This field has the same restrictions as Kubernetes annotations. The total size of all keys and values combined is limited to 256k. Key can have 2 segments: prefix (optional) and name (required), separated by a slash (/). Prefix must be a DNS subdomain. Name must be 63 characters or less, begin and end with alphanumerics, with dashes (-), underscores (_), dots (.), and alphanumerics between. */
		annotations: Option[Map[String, String]] = None,
	  /** VMware user cluster control plane nodes must have either 1 or 3 replicas. */
		controlPlaneNode: Option[Schema.VmwareControlPlaneNodeConfig] = None,
	  /** AAGConfig specifies whether to spread VMware user cluster nodes across at least three physical hosts in the datacenter. */
		antiAffinityGroups: Option[Schema.VmwareAAGConfig] = None,
	  /** Storage configuration. */
		storage: Option[Schema.VmwareStorageConfig] = None,
	  /** The VMware user cluster network configuration. */
		networkConfig: Option[Schema.VmwareNetworkConfig] = None,
	  /** Load balancer configuration. */
		loadBalancer: Option[Schema.VmwareLoadBalancerConfig] = None,
	  /** VmwareVCenterConfig specifies vCenter config for the user cluster. If unspecified, it is inherited from the admin cluster. */
		vcenter: Option[Schema.VmwareVCenterConfig] = None,
	  /** Output only. ResourceStatus representing detailed cluster state. */
		status: Option[Schema.ResourceStatus] = None,
	  /** VmwareDataplaneV2Config specifies configuration for Dataplane V2. */
		dataplaneV2: Option[Schema.VmwareDataplaneV2Config] = None,
	  /** Enable VM tracking. */
		vmTrackingEnabled: Option[Boolean] = None,
	  /** Configuration for auto repairing. */
		autoRepairConfig: Option[Schema.VmwareAutoRepairConfig] = None,
	  /** Output only. Fleet configuration for the cluster. */
		fleet: Option[Schema.Fleet] = None,
	  /** RBAC policy that will be applied and managed by the Anthos On-Prem API. */
		authorization: Option[Schema.Authorization] = None,
	  /** Output only. The time at which VMware user cluster was deleted. */
		deleteTime: Option[String] = None,
	  /** Output only. ValidationCheck represents the result of the preflight check job. */
		validationCheck: Option[Schema.ValidationCheck] = None,
	  /** Output only. The resource name of the VMware admin cluster hosting this user cluster. */
		adminClusterName: Option[String] = None,
	  /** Enable control plane V2. Default to false. */
		enableControlPlaneV2: Option[Boolean] = None,
	  /** Binary Authorization related configurations. */
		binaryAuthorization: Option[Schema.BinaryAuthorization] = None,
	  /** Specifies upgrade policy for the cluster. */
		upgradePolicy: Option[Schema.VmwareClusterUpgradePolicy] = None,
	  /** Disable bundled ingress. */
		disableBundledIngress: Option[Boolean] = None
	)
	
	case class VmwareControlPlaneNodeConfig(
	  /** The number of CPUs for each admin cluster node that serve as control planes for this VMware user cluster. (default: 4 CPUs) */
		cpus: Option[String] = None,
	  /** The megabytes of memory for each admin cluster node that serves as a control plane for this VMware user cluster (default: 8192 MB memory). */
		memory: Option[String] = None,
	  /** The number of control plane nodes for this VMware user cluster. (default: 1 replica). */
		replicas: Option[String] = None,
	  /** AutoResizeConfig provides auto resizing configurations. */
		autoResizeConfig: Option[Schema.VmwareAutoResizeConfig] = None,
	  /** Vsphere-specific config. */
		vsphereConfig: Option[Schema.VmwareControlPlaneVsphereConfig] = None
	)
	
	case class VmwareAutoResizeConfig(
	  /** Whether to enable controle plane node auto resizing. */
		enabled: Option[Boolean] = None
	)
	
	case class VmwareControlPlaneVsphereConfig(
	  /** The Vsphere datastore used by the control plane Node. */
		datastore: Option[String] = None,
	  /** The Vsphere storage policy used by the control plane Node. */
		storagePolicyName: Option[String] = None
	)
	
	case class VmwareAAGConfig(
	  /** Spread nodes across at least three physical hosts (requires at least three hosts). Enabled by default. */
		aagConfigDisabled: Option[Boolean] = None
	)
	
	case class VmwareStorageConfig(
	  /** Whether or not to deploy vSphere CSI components in the VMware user cluster. Enabled by default. */
		vsphereCsiDisabled: Option[Boolean] = None
	)
	
	case class VmwareNetworkConfig(
	  /** Required. All services in the cluster are assigned an RFC1918 IPv4 address from these ranges. Only a single range is supported. This field cannot be changed after creation. */
		serviceAddressCidrBlocks: Option[List[String]] = None,
	  /** Required. All pods in the cluster are assigned an RFC1918 IPv4 address from these ranges. Only a single range is supported. This field cannot be changed after creation. */
		podAddressCidrBlocks: Option[List[String]] = None,
	  /** Configuration settings for a static IP configuration. */
		staticIpConfig: Option[Schema.VmwareStaticIpConfig] = None,
	  /** Configuration settings for a DHCP IP configuration. */
		dhcpIpConfig: Option[Schema.VmwareDhcpIpConfig] = None,
	  /** vcenter_network specifies vCenter network name. Inherited from the admin cluster. */
		vcenterNetwork: Option[String] = None,
	  /** Represents common network settings irrespective of the host's IP address. */
		hostConfig: Option[Schema.VmwareHostConfig] = None,
	  /** Configuration for control plane V2 mode. */
		controlPlaneV2Config: Option[Schema.VmwareControlPlaneV2Config] = None
	)
	
	case class VmwareStaticIpConfig(
	  /** Represents the configuration values for static IP allocation to nodes. */
		ipBlocks: Option[List[Schema.VmwareIpBlock]] = None
	)
	
	case class VmwareIpBlock(
	  /** The netmask used by the VMware user cluster. */
		netmask: Option[String] = None,
	  /** The network gateway used by the VMware user cluster. */
		gateway: Option[String] = None,
	  /** The node's network configurations used by the VMware user cluster. */
		ips: Option[List[Schema.VmwareHostIp]] = None
	)
	
	case class VmwareHostIp(
	  /** IP could be an IP address (like 1.2.3.4) or a CIDR (like 1.2.3.0/24). */
		ip: Option[String] = None,
	  /** Hostname of the machine. VM's name will be used if this field is empty. */
		hostname: Option[String] = None
	)
	
	case class VmwareDhcpIpConfig(
	  /** enabled is a flag to mark if DHCP IP allocation is used for VMware user clusters. */
		enabled: Option[Boolean] = None
	)
	
	case class VmwareHostConfig(
	  /** DNS servers. */
		dnsServers: Option[List[String]] = None,
	  /** NTP servers. */
		ntpServers: Option[List[String]] = None,
	  /** DNS search domains. */
		dnsSearchDomains: Option[List[String]] = None
	)
	
	case class VmwareControlPlaneV2Config(
	  /** Static IP addresses for the control plane nodes. */
		controlPlaneIpBlock: Option[Schema.VmwareIpBlock] = None
	)
	
	case class VmwareLoadBalancerConfig(
	  /** The VIPs used by the load balancer. */
		vipConfig: Option[Schema.VmwareVipConfig] = None,
	  /** Configuration for F5 Big IP typed load balancers. */
		f5Config: Option[Schema.VmwareF5BigIpConfig] = None,
	  /** Manually configured load balancers. */
		manualLbConfig: Option[Schema.VmwareManualLbConfig] = None,
	  /** Output only. Configuration for Seesaw typed load balancers. */
		seesawConfig: Option[Schema.VmwareSeesawConfig] = None,
	  /** Configuration for MetalLB typed load balancers. */
		metalLbConfig: Option[Schema.VmwareMetalLbConfig] = None
	)
	
	case class VmwareVipConfig(
	  /** The VIP which you previously set aside for the Kubernetes API of this cluster. */
		controlPlaneVip: Option[String] = None,
	  /** The VIP which you previously set aside for ingress traffic into this cluster. */
		ingressVip: Option[String] = None
	)
	
	case class VmwareF5BigIpConfig(
	  /** The load balancer's IP address. */
		address: Option[String] = None,
	  /** The preexisting partition to be used by the load balancer. This partition is usually created for the admin cluster for example: 'my-f5-admin-partition'. */
		partition: Option[String] = None,
	  /** The pool name. Only necessary, if using SNAT. */
		snatPool: Option[String] = None
	)
	
	case class VmwareManualLbConfig(
	  /** NodePort for ingress service's http. The ingress service in the admin cluster is implemented as a Service of type NodePort (ex. 32527). */
		ingressHttpNodePort: Option[Int] = None,
	  /** NodePort for ingress service's https. The ingress service in the admin cluster is implemented as a Service of type NodePort (ex. 30139). */
		ingressHttpsNodePort: Option[Int] = None,
	  /** NodePort for control plane service. The Kubernetes API server in the admin cluster is implemented as a Service of type NodePort (ex. 30968). */
		controlPlaneNodePort: Option[Int] = None,
	  /** NodePort for konnectivity server service running as a sidecar in each kube-apiserver pod (ex. 30564). */
		konnectivityServerNodePort: Option[Int] = None
	)
	
	case class VmwareSeesawConfig(
	  /** Required. In general the following format should be used for the Seesaw group name: seesaw-for-[cluster_name]. */
		group: Option[String] = None,
	  /** Required. MasterIP is the IP announced by the master of Seesaw group. */
		masterIp: Option[String] = None,
	  /** Required. The IP Blocks to be used by the Seesaw load balancer */
		ipBlocks: Option[List[Schema.VmwareIpBlock]] = None,
	  /** Enable two load balancer VMs to achieve a highly-available Seesaw load balancer. */
		enableHa: Option[Boolean] = None,
	  /** Names of the VMs created for this Seesaw group. */
		vms: Option[List[String]] = None,
	  /** Name to be used by Stackdriver. */
		stackdriverName: Option[String] = None
	)
	
	case class VmwareMetalLbConfig(
	  /** Required. AddressPools is a list of non-overlapping IP pools used by load balancer typed services. All addresses must be routable to load balancer nodes. IngressVIP must be included in the pools. */
		addressPools: Option[List[Schema.VmwareAddressPool]] = None
	)
	
	case class VmwareAddressPool(
	  /** Required. The name of the address pool. */
		pool: Option[String] = None,
	  /** Required. The addresses that are part of this pool. Each address must be either in the CIDR form (1.2.3.0/24) or range form (1.2.3.1-1.2.3.5). */
		addresses: Option[List[String]] = None,
	  /** If true, avoid using IPs ending in .0 or .255. This avoids buggy consumer devices mistakenly dropping IPv4 traffic for those special IP addresses. */
		avoidBuggyIps: Option[Boolean] = None,
	  /** If true, prevent IP addresses from being automatically assigned. */
		manualAssign: Option[Boolean] = None
	)
	
	case class VmwareVCenterConfig(
	  /** The name of the vCenter resource pool for the user cluster. */
		resourcePool: Option[String] = None,
	  /** The name of the vCenter datastore for the user cluster. */
		datastore: Option[String] = None,
	  /** The name of the vCenter datacenter for the user cluster. */
		datacenter: Option[String] = None,
	  /** The name of the vCenter cluster for the user cluster. */
		cluster: Option[String] = None,
	  /** The name of the vCenter folder for the user cluster. */
		folder: Option[String] = None,
	  /** Contains the vCenter CA certificate public key for SSL verification. */
		caCertData: Option[String] = None,
	  /** Output only. The vCenter IP address. */
		address: Option[String] = None,
	  /** The name of the vCenter storage policy for the user cluster. */
		storagePolicyName: Option[String] = None
	)
	
	case class VmwareDataplaneV2Config(
	  /** Enables Dataplane V2. */
		dataplaneV2Enabled: Option[Boolean] = None,
	  /** Enable Dataplane V2 for clusters with Windows nodes. */
		windowsDataplaneV2Enabled: Option[Boolean] = None,
	  /** Enable advanced networking which requires dataplane_v2_enabled to be set true. */
		advancedNetworking: Option[Boolean] = None,
	  /** Configure ForwardMode for Dataplane v2. */
		forwardMode: Option[String] = None
	)
	
	case class VmwareAutoRepairConfig(
	  /** Whether auto repair is enabled. */
		enabled: Option[Boolean] = None
	)
	
	case class VmwareClusterUpgradePolicy(
	  /** Controls whether the upgrade applies to the control plane only. */
		controlPlaneOnly: Option[Boolean] = None
	)
	
	case class ListVmwareClustersResponse(
	  /** The list of VMware Cluster. */
		vmwareClusters: Option[List[Schema.VmwareCluster]] = None,
	  /** A token identifying a page of results the server should return. If the token is not empty this means that more results are available and should be retrieved by repeating the request with the provided page token. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object BareMetalNodePool {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PROVISIONING, RUNNING, RECONCILING, STOPPING, ERROR, DEGRADED }
	}
	case class BareMetalNodePool(
	  /** Immutable. The bare metal node pool resource name. */
		name: Option[String] = None,
	  /** The display name for the bare metal node pool. */
		displayName: Option[String] = None,
	  /** Output only. The unique identifier of the bare metal node pool. */
		uid: Option[String] = None,
	  /** Output only. The current state of the bare metal node pool. */
		state: Option[Schema.BareMetalNodePool.StateEnum] = None,
	  /** Output only. If set, there are currently changes in flight to the bare metal node pool. */
		reconciling: Option[Boolean] = None,
	  /** Output only. The time at which this bare metal node pool was created. */
		createTime: Option[String] = None,
	  /** Output only. The time at which this bare metal node pool was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. The time at which this bare metal node pool was deleted. If the resource is not deleted, this must be empty */
		deleteTime: Option[String] = None,
	  /** This checksum is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. Allows clients to perform consistent read-modify-writes through optimistic concurrency control. */
		etag: Option[String] = None,
	  /** Annotations on the bare metal node pool. This field has the same restrictions as Kubernetes annotations. The total size of all keys and values combined is limited to 256k. Key can have 2 segments: prefix (optional) and name (required), separated by a slash (/). Prefix must be a DNS subdomain. Name must be 63 characters or less, begin and end with alphanumerics, with dashes (-), underscores (_), dots (.), and alphanumerics between. */
		annotations: Option[Map[String, String]] = None,
	  /** Required. Node pool configuration. */
		nodePoolConfig: Option[Schema.BareMetalNodePoolConfig] = None,
	  /** Output only. ResourceStatus representing the detailed node pool status. */
		status: Option[Schema.ResourceStatus] = None,
	  /** The worker node pool upgrade policy. */
		upgradePolicy: Option[Schema.BareMetalNodePoolUpgradePolicy] = None
	)
	
	case class BareMetalNodePoolUpgradePolicy(
	  /** The parallel upgrade settings for worker node pools. */
		parallelUpgradeConfig: Option[Schema.BareMetalParallelUpgradeConfig] = None
	)
	
	case class BareMetalParallelUpgradeConfig(
	  /** The maximum number of nodes that can be upgraded at once. */
		concurrentNodes: Option[Int] = None,
	  /** The minimum number of nodes that should be healthy and available during an upgrade. If set to the default value of 0, it is possible that none of the nodes will be available during an upgrade. */
		minimumAvailableNodes: Option[Int] = None
	)
	
	case class EnrollBareMetalNodePoolRequest(
	  /** User provided OnePlatform identifier that is used as part of the resource name. (https://tools.ietf.org/html/rfc1123) format. */
		bareMetalNodePoolId: Option[String] = None,
	  /** If set, only validate the request, but do not actually enroll the node pool. */
		validateOnly: Option[Boolean] = None
	)
	
	case class ListBareMetalNodePoolsResponse(
	  /** The node pools from the specified parent resource. */
		bareMetalNodePools: Option[List[Schema.BareMetalNodePool]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object VmwareNodePool {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PROVISIONING, RUNNING, RECONCILING, STOPPING, ERROR, DEGRADED }
	}
	case class VmwareNodePool(
	  /** Immutable. The resource name of this node pool. */
		name: Option[String] = None,
	  /** The display name for the node pool. */
		displayName: Option[String] = None,
	  /** Output only. The unique identifier of the node pool. */
		uid: Option[String] = None,
	  /** Output only. The current state of the node pool. */
		state: Option[Schema.VmwareNodePool.StateEnum] = None,
	  /** Output only. If set, there are currently changes in flight to the node pool. */
		reconciling: Option[Boolean] = None,
	  /** Output only. The time at which this node pool was created. */
		createTime: Option[String] = None,
	  /** Output only. The time at which this node pool was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. The time at which this node pool was deleted. If the resource is not deleted, this must be empty */
		deleteTime: Option[String] = None,
	  /** This checksum is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. Allows clients to perform consistent read-modify-writes through optimistic concurrency control. */
		etag: Option[String] = None,
	  /** Annotations on the node pool. This field has the same restrictions as Kubernetes annotations. The total size of all keys and values combined is limited to 256k. Key can have 2 segments: prefix (optional) and name (required), separated by a slash (/). Prefix must be a DNS subdomain. Name must be 63 characters or less, begin and end with alphanumerics, with dashes (-), underscores (_), dots (.), and alphanumerics between. */
		annotations: Option[Map[String, String]] = None,
	  /** Node pool autoscaling config for the node pool. */
		nodePoolAutoscaling: Option[Schema.VmwareNodePoolAutoscalingConfig] = None,
	  /** Required. The node configuration of the node pool. */
		config: Option[Schema.VmwareNodeConfig] = None,
	  /** Output only. ResourceStatus representing the detailed VMware node pool state. */
		status: Option[Schema.ResourceStatus] = None,
	  /** Anthos version for the node pool. Defaults to the user cluster version. */
		onPremVersion: Option[String] = None
	)
	
	case class VmwareNodePoolAutoscalingConfig(
	  /** Minimum number of replicas in the NodePool. */
		minReplicas: Option[Int] = None,
	  /** Maximum number of replicas in the NodePool. */
		maxReplicas: Option[Int] = None
	)
	
	case class VmwareNodeConfig(
	  /** The number of CPUs for each node in the node pool. */
		cpus: Option[String] = None,
	  /** The megabytes of memory for each node in the node pool. */
		memoryMb: Option[String] = None,
	  /** The number of nodes in the node pool. */
		replicas: Option[String] = None,
	  /** Required. The OS image to be used for each node in a node pool. Currently `cos`, `cos_cgv2`, `ubuntu`, `ubuntu_cgv2`, `ubuntu_containerd` and `windows` are supported. */
		imageType: Option[String] = None,
	  /** The OS image name in vCenter, only valid when using Windows. */
		image: Option[String] = None,
	  /** VMware disk size to be used during creation. */
		bootDiskSizeGb: Option[String] = None,
	  /** The initial taints assigned to nodes of this node pool. */
		taints: Option[List[Schema.NodeTaint]] = None,
	  /** The map of Kubernetes labels (key/value pairs) to be applied to each node. These will added in addition to any default label(s) that Kubernetes may apply to the node. In case of conflict in label keys, the applied set may differ depending on the Kubernetes version -- it's best to assume the behavior is undefined and conflicts should be avoided. For more information, including usage and the valid values, see: https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/ */
		labels: Option[Map[String, String]] = None,
	  /** Specifies the vSphere config for node pool. */
		vsphereConfig: Option[Schema.VmwareVsphereConfig] = None,
	  /** Allow node pool traffic to be load balanced. Only works for clusters with MetalLB load balancers. */
		enableLoadBalancer: Option[Boolean] = None
	)
	
	case class VmwareVsphereConfig(
	  /** The name of the vCenter datastore. Inherited from the user cluster. */
		datastore: Option[String] = None,
	  /** Tags to apply to VMs. */
		tags: Option[List[Schema.VmwareVsphereTag]] = None,
	  /** Vsphere host groups to apply to all VMs in the node pool */
		hostGroups: Option[List[String]] = None
	)
	
	case class VmwareVsphereTag(
	  /** The Vsphere tag category. */
		category: Option[String] = None,
	  /** The Vsphere tag name. */
		tag: Option[String] = None
	)
	
	case class ListVmwareNodePoolsResponse(
	  /** The node pools from the specified parent resource. */
		vmwareNodePools: Option[List[Schema.VmwareNodePool]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class EnrollVmwareNodePoolRequest(
	  /** The target node pool id to be enrolled. */
		vmwareNodePoolId: Option[String] = None
	)
	
	object VmwareAdminCluster {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PROVISIONING, RUNNING, RECONCILING, STOPPING, ERROR, DEGRADED }
	}
	case class VmwareAdminCluster(
	  /** Immutable. The VMware admin cluster resource name. */
		name: Option[String] = None,
	  /** A human readable description of this VMware admin cluster. */
		description: Option[String] = None,
	  /** Output only. The unique identifier of the VMware admin cluster. */
		uid: Option[String] = None,
	  /** This checksum is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. Allows clients to perform consistent read-modify-writes through optimistic concurrency control. */
		etag: Option[String] = None,
	  /** Output only. The time at which VMware admin cluster was created. */
		createTime: Option[String] = None,
	  /** Output only. The time at which VMware admin cluster was last updated. */
		updateTime: Option[String] = None,
	  /** Annotations on the VMware admin cluster. This field has the same restrictions as Kubernetes annotations. The total size of all keys and values combined is limited to 256k. Key can have 2 segments: prefix (optional) and name (required), separated by a slash (/). Prefix must be a DNS subdomain. Name must be 63 characters or less, begin and end with alphanumerics, with dashes (-), underscores (_), dots (.), and alphanumerics between. */
		annotations: Option[Map[String, String]] = None,
	  /** Output only. The current state of VMware admin cluster. */
		state: Option[Schema.VmwareAdminCluster.StateEnum] = None,
	  /** Output only. The DNS name of VMware admin cluster's API server. */
		endpoint: Option[String] = None,
	  /** Output only. If set, there are currently changes in flight to the VMware admin cluster. */
		reconciling: Option[Boolean] = None,
	  /** Output only. The object name of the VMware OnPremAdminCluster custom resource. This field is used to support conflicting names when enrolling existing clusters to the API. When used as a part of cluster enrollment, this field will differ from the ID in the resource name. For new clusters, this field will match the user provided cluster name and be visible in the last component of the resource name. It is not modifiable. All users should use this name to access their cluster using gkectl or kubectl and should expect to see the local name when viewing admin cluster controller logs. */
		localName: Option[String] = None,
	  /** The bootstrap cluster this VMware admin cluster belongs to. */
		bootstrapClusterMembership: Option[String] = None,
	  /** The Anthos clusters on the VMware version for the admin cluster. */
		onPremVersion: Option[String] = None,
	  /** Output only. Fleet configuration for the cluster. */
		fleet: Option[Schema.Fleet] = None,
	  /** The OS image type for the VMware admin cluster. */
		imageType: Option[String] = None,
	  /** The VMware admin cluster VCenter configuration. */
		vcenter: Option[Schema.VmwareAdminVCenterConfig] = None,
	  /** The VMware admin cluster network configuration. */
		networkConfig: Option[Schema.VmwareAdminNetworkConfig] = None,
	  /** The VMware admin cluster load balancer configuration. */
		loadBalancer: Option[Schema.VmwareAdminLoadBalancerConfig] = None,
	  /** The VMware admin cluster control plane node configuration. */
		controlPlaneNode: Option[Schema.VmwareAdminControlPlaneNodeConfig] = None,
	  /** The VMware admin cluster addon node configuration. */
		addonNode: Option[Schema.VmwareAdminAddonNodeConfig] = None,
	  /** The VMware admin cluster anti affinity group configuration. */
		antiAffinityGroups: Option[Schema.VmwareAAGConfig] = None,
	  /** The VMware admin cluster auto repair configuration. */
		autoRepairConfig: Option[Schema.VmwareAutoRepairConfig] = None,
	  /** Output only. ResourceStatus representing detailed cluster state. */
		status: Option[Schema.ResourceStatus] = None,
	  /** The VMware platform configuration. */
		platformConfig: Option[Schema.VmwarePlatformConfig] = None,
	  /** Output only. The VMware admin cluster prepared secrets configuration. It should always be enabled by the Central API, instead of letting users set it. */
		preparedSecrets: Option[Schema.VmwareAdminPreparedSecretsConfig] = None,
	  /** The VMware admin cluster authorization configuration. */
		authorization: Option[Schema.VmwareAdminAuthorizationConfig] = None,
	  /** Output only. ValidationCheck represents the result of the preflight check job. */
		validationCheck: Option[Schema.ValidationCheck] = None
	)
	
	case class VmwareAdminVCenterConfig(
	  /** The name of the vCenter resource pool for the admin cluster. */
		resourcePool: Option[String] = None,
	  /** The name of the vCenter datastore for the admin cluster. */
		datastore: Option[String] = None,
	  /** The name of the vCenter datacenter for the admin cluster. */
		datacenter: Option[String] = None,
	  /** The name of the vCenter cluster for the admin cluster. */
		cluster: Option[String] = None,
	  /** The name of the vCenter folder for the admin cluster. */
		folder: Option[String] = None,
	  /** Contains the vCenter CA certificate public key for SSL verification. */
		caCertData: Option[String] = None,
	  /** The vCenter IP address. */
		address: Option[String] = None,
	  /** The name of the virtual machine disk (VMDK) for the admin cluster. */
		dataDisk: Option[String] = None,
	  /** The name of the vCenter storage policy for the user cluster. */
		storagePolicyName: Option[String] = None
	)
	
	case class VmwareAdminNetworkConfig(
	  /** Required. All services in the cluster are assigned an RFC1918 IPv4 address from these ranges. Only a single range is supported. This field cannot be changed after creation. */
		serviceAddressCidrBlocks: Option[List[String]] = None,
	  /** Required. All pods in the cluster are assigned an RFC1918 IPv4 address from these ranges. Only a single range is supported. This field cannot be changed after creation. */
		podAddressCidrBlocks: Option[List[String]] = None,
	  /** Configuration settings for a static IP configuration. */
		staticIpConfig: Option[Schema.VmwareStaticIpConfig] = None,
	  /** Configuration settings for a DHCP IP configuration. */
		dhcpIpConfig: Option[Schema.VmwareDhcpIpConfig] = None,
	  /** vcenter_network specifies vCenter network name. */
		vcenterNetwork: Option[String] = None,
	  /** Represents common network settings irrespective of the host's IP address. */
		hostConfig: Option[Schema.VmwareHostConfig] = None,
	  /** Configuration for HA admin cluster control plane. */
		haControlPlaneConfig: Option[Schema.VmwareAdminHAControlPlaneConfig] = None
	)
	
	case class VmwareAdminHAControlPlaneConfig(
	  /** Static IP addresses for the admin control plane nodes. */
		controlPlaneIpBlock: Option[Schema.VmwareIpBlock] = None
	)
	
	case class VmwareAdminLoadBalancerConfig(
	  /** The VIPs used by the load balancer. */
		vipConfig: Option[Schema.VmwareAdminVipConfig] = None,
	  /** Configuration for F5 Big IP typed load balancers. */
		f5Config: Option[Schema.VmwareAdminF5BigIpConfig] = None,
	  /** Manually configured load balancers. */
		manualLbConfig: Option[Schema.VmwareAdminManualLbConfig] = None,
	  /** MetalLB load balancers. */
		metalLbConfig: Option[Schema.VmwareAdminMetalLbConfig] = None,
	  /** Output only. Configuration for Seesaw typed load balancers. */
		seesawConfig: Option[Schema.VmwareAdminSeesawConfig] = None
	)
	
	case class VmwareAdminVipConfig(
	  /** The VIP which you previously set aside for the Kubernetes API of the admin cluster. */
		controlPlaneVip: Option[String] = None,
	  /** The VIP to configure the load balancer for add-ons. */
		addonsVip: Option[String] = None
	)
	
	case class VmwareAdminF5BigIpConfig(
	  /** The load balancer's IP address. */
		address: Option[String] = None,
	  /** The preexisting partition to be used by the load balancer. This partition is usually created for the admin cluster for example: 'my-f5-admin-partition'. */
		partition: Option[String] = None,
	  /** The pool name. Only necessary, if using SNAT. */
		snatPool: Option[String] = None
	)
	
	case class VmwareAdminManualLbConfig(
	  /** NodePort for ingress service's http. The ingress service in the admin cluster is implemented as a Service of type NodePort (ex. 32527). */
		ingressHttpNodePort: Option[Int] = None,
	  /** NodePort for ingress service's https. The ingress service in the admin cluster is implemented as a Service of type NodePort (ex. 30139). */
		ingressHttpsNodePort: Option[Int] = None,
	  /** NodePort for control plane service. The Kubernetes API server in the admin cluster is implemented as a Service of type NodePort (ex. 30968). */
		controlPlaneNodePort: Option[Int] = None,
	  /** NodePort for konnectivity server service running as a sidecar in each kube-apiserver pod (ex. 30564). */
		konnectivityServerNodePort: Option[Int] = None,
	  /** NodePort for add-ons server in the admin cluster. */
		addonsNodePort: Option[Int] = None
	)
	
	case class VmwareAdminMetalLbConfig(
	  /** Whether MetalLB is enabled. */
		enabled: Option[Boolean] = None
	)
	
	case class VmwareAdminSeesawConfig(
	  /** In general the following format should be used for the Seesaw group name: seesaw-for-[cluster_name]. */
		group: Option[String] = None,
	  /** MasterIP is the IP announced by the master of Seesaw group. */
		masterIp: Option[String] = None,
	  /** The IP Blocks to be used by the Seesaw load balancer */
		ipBlocks: Option[List[Schema.VmwareIpBlock]] = None,
	  /** Enable two load balancer VMs to achieve a highly-available Seesaw load balancer. */
		enableHa: Option[Boolean] = None,
	  /** Names of the VMs created for this Seesaw group. */
		vms: Option[List[String]] = None,
	  /** Name to be used by Stackdriver. */
		stackdriverName: Option[String] = None
	)
	
	case class VmwareAdminControlPlaneNodeConfig(
	  /** The number of vCPUs for the control-plane node of the admin cluster. */
		cpus: Option[String] = None,
	  /** The number of mebibytes of memory for the control-plane node of the admin cluster. */
		memory: Option[String] = None,
	  /** The number of control plane nodes for this VMware admin cluster. (default: 1 replica). */
		replicas: Option[String] = None
	)
	
	case class VmwareAdminAddonNodeConfig(
	  /** VmwareAutoResizeConfig config specifies auto resize config. */
		autoResizeConfig: Option[Schema.VmwareAutoResizeConfig] = None
	)
	
	case class VmwarePlatformConfig(
	  /** Input only. The required platform version e.g. 1.13.1. If the current platform version is lower than the target version, the platform version will be updated to the target version. If the target version is not installed in the platform (bundle versions), download the target version bundle. */
		requiredPlatformVersion: Option[String] = None,
	  /** Output only. The platform version e.g. 1.13.2. */
		platformVersion: Option[String] = None,
	  /** Output only. The list of bundles installed in the admin cluster. */
		bundles: Option[List[Schema.VmwareBundleConfig]] = None,
	  /** Output only. Resource status for the platform. */
		status: Option[Schema.ResourceStatus] = None
	)
	
	case class VmwareBundleConfig(
	  /** The version of the bundle. */
		version: Option[String] = None,
	  /** Output only. Resource status for the bundle. */
		status: Option[Schema.ResourceStatus] = None
	)
	
	case class VmwareAdminPreparedSecretsConfig(
	  /** Whether prepared secrets is enabled. */
		enabled: Option[Boolean] = None
	)
	
	case class VmwareAdminAuthorizationConfig(
	  /** For VMware admin clusters, users will be granted the cluster-viewer role on the cluster. */
		viewerUsers: Option[List[Schema.ClusterUser]] = None
	)
	
	case class ListVmwareAdminClustersResponse(
	  /** The list of VMware admin cluster. */
		vmwareAdminClusters: Option[List[Schema.VmwareAdminCluster]] = None,
	  /** A token identifying a page of results the server should return. If the token is not empty this means that more results are available and should be retrieved by repeating the request with the provided page token. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class EnrollVmwareAdminClusterRequest(
	  /** User provided OnePlatform identifier that is used as part of the resource name. This must be unique among all GKE on-prem clusters within a project and location and will return a 409 if the cluster already exists. (https://tools.ietf.org/html/rfc1123) format. */
		vmwareAdminClusterId: Option[String] = None,
	  /** Required. This is the full resource name of this admin cluster's fleet membership. */
		membership: Option[String] = None
	)
	
	case class QueryVmwareVersionConfigResponse(
	  /** List of available versions to install or to upgrade to. */
		versions: Option[List[Schema.VmwareVersionInfo]] = None
	)
	
	case class VmwareVersionInfo(
	  /** Version number e.g. 1.13.1-gke.1000. */
		version: Option[String] = None,
	  /** If set, the cluster dependencies (e.g. the admin cluster, other user clusters managed by the same admin cluster) must be upgraded before this version can be installed or upgraded to. */
		hasDependencies: Option[Boolean] = None,
	  /** If set, the version is installed in the admin cluster. Otherwise, the version bundle must be downloaded and installed before a user cluster can be created at or upgraded to this version. */
		isInstalled: Option[Boolean] = None,
	  /** The list of upgrade dependencies for this version. */
		dependencies: Option[List[Schema.UpgradeDependency]] = None
	)
	
	object BareMetalAdminCluster {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PROVISIONING, RUNNING, RECONCILING, STOPPING, ERROR, DEGRADED }
	}
	case class BareMetalAdminCluster(
	  /** Immutable. The bare metal admin cluster resource name. */
		name: Option[String] = None,
	  /** A human readable description of this bare metal admin cluster. */
		description: Option[String] = None,
	  /** Output only. The unique identifier of the bare metal admin cluster. */
		uid: Option[String] = None,
	  /** The Anthos clusters on bare metal version for the bare metal admin cluster. */
		bareMetalVersion: Option[String] = None,
	  /** Output only. The current state of the bare metal admin cluster. */
		state: Option[Schema.BareMetalAdminCluster.StateEnum] = None,
	  /** Output only. The IP address name of bare metal admin cluster's API server. */
		endpoint: Option[String] = None,
	  /** Output only. If set, there are currently changes in flight to the bare metal Admin Cluster. */
		reconciling: Option[Boolean] = None,
	  /** Output only. The time at which this bare metal admin cluster was created. */
		createTime: Option[String] = None,
	  /** Output only. The time at which this bare metal admin cluster was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. The time at which this bare metal admin cluster was deleted. If the resource is not deleted, this must be empty */
		deleteTime: Option[String] = None,
	  /** Output only. The object name of the bare metal cluster custom resource. This field is used to support conflicting names when enrolling existing clusters to the API. When used as a part of cluster enrollment, this field will differ from the ID in the resource name. For new clusters, this field will match the user provided cluster name and be visible in the last component of the resource name. It is not modifiable. All users should use this name to access their cluster using gkectl or kubectl and should expect to see the local name when viewing admin cluster controller logs. */
		localName: Option[String] = None,
	  /** This checksum is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. Allows clients to perform consistent read-modify-writes through optimistic concurrency control. */
		etag: Option[String] = None,
	  /** Annotations on the bare metal admin cluster. This field has the same restrictions as Kubernetes annotations. The total size of all keys and values combined is limited to 256k. Key can have 2 segments: prefix (optional) and name (required), separated by a slash (/). Prefix must be a DNS subdomain. Name must be 63 characters or less, begin and end with alphanumerics, with dashes (-), underscores (_), dots (.), and alphanumerics between. */
		annotations: Option[Map[String, String]] = None,
	  /** Network configuration. */
		networkConfig: Option[Schema.BareMetalAdminNetworkConfig] = None,
	  /** Control plane configuration. */
		controlPlane: Option[Schema.BareMetalAdminControlPlaneConfig] = None,
	  /** Load balancer configuration. */
		loadBalancer: Option[Schema.BareMetalAdminLoadBalancerConfig] = None,
	  /** Storage configuration. */
		storage: Option[Schema.BareMetalAdminStorageConfig] = None,
	  /** Output only. Fleet configuration for the cluster. */
		fleet: Option[Schema.Fleet] = None,
	  /** Cluster operations configuration. */
		clusterOperations: Option[Schema.BareMetalAdminClusterOperationsConfig] = None,
	  /** Output only. ResourceStatus representing detailed cluster status. */
		status: Option[Schema.ResourceStatus] = None,
	  /** Maintenance configuration. */
		maintenanceConfig: Option[Schema.BareMetalAdminMaintenanceConfig] = None,
	  /** Output only. MaintenanceStatus representing state of maintenance. */
		maintenanceStatus: Option[Schema.BareMetalAdminMaintenanceStatus] = None,
	  /** Output only. ValidationCheck representing the result of the preflight check. */
		validationCheck: Option[Schema.ValidationCheck] = None,
	  /** Workload node configuration. */
		nodeConfig: Option[Schema.BareMetalAdminWorkloadNodeConfig] = None,
	  /** Proxy configuration. */
		proxy: Option[Schema.BareMetalAdminProxyConfig] = None,
	  /** Security related configuration. */
		securityConfig: Option[Schema.BareMetalAdminSecurityConfig] = None,
	  /** Node access related configurations. */
		nodeAccessConfig: Option[Schema.BareMetalAdminNodeAccessConfig] = None,
	  /** OS environment related configurations. */
		osEnvironmentConfig: Option[Schema.BareMetalAdminOsEnvironmentConfig] = None,
	  /** Binary Authorization related configurations. */
		binaryAuthorization: Option[Schema.BinaryAuthorization] = None
	)
	
	case class BareMetalAdminNetworkConfig(
	  /** Configuration for Island mode CIDR. */
		islandModeCidr: Option[Schema.BareMetalAdminIslandModeCidrConfig] = None
	)
	
	case class BareMetalAdminIslandModeCidrConfig(
	  /** Required. All services in the cluster are assigned an RFC1918 IPv4 address from these ranges. This field cannot be changed after creation. */
		serviceAddressCidrBlocks: Option[List[String]] = None,
	  /** Required. All pods in the cluster are assigned an RFC1918 IPv4 address from these ranges. This field cannot be changed after creation. */
		podAddressCidrBlocks: Option[List[String]] = None
	)
	
	case class BareMetalAdminControlPlaneConfig(
	  /** Required. Configures the node pool running the control plane. If specified the corresponding NodePool will be created for the cluster's control plane. The NodePool will have the same name and namespace as the cluster. */
		controlPlaneNodePoolConfig: Option[Schema.BareMetalAdminControlPlaneNodePoolConfig] = None,
	  /** Customizes the default API server args. Only a subset of customized flags are supported. Please refer to the API server documentation below to know the exact format: https://kubernetes.io/docs/reference/command-line-tools-reference/kube-apiserver/ */
		apiServerArgs: Option[List[Schema.BareMetalAdminApiServerArgument]] = None
	)
	
	case class BareMetalAdminControlPlaneNodePoolConfig(
	  /** Required. The generic configuration for a node pool running the control plane. */
		nodePoolConfig: Option[Schema.BareMetalNodePoolConfig] = None
	)
	
	case class BareMetalAdminApiServerArgument(
	  /** Required. The argument name as it appears on the API Server command line please make sure to remove the leading dashes. */
		argument: Option[String] = None,
	  /** Required. The value of the arg as it will be passed to the API Server command line. */
		value: Option[String] = None
	)
	
	case class BareMetalAdminLoadBalancerConfig(
	  /** The VIPs used by the load balancer. */
		vipConfig: Option[Schema.BareMetalAdminVipConfig] = None,
	  /** Configures the ports that the load balancer will listen on. */
		portConfig: Option[Schema.BareMetalAdminPortConfig] = None,
	  /** Manually configured load balancers. */
		manualLbConfig: Option[Schema.BareMetalAdminManualLbConfig] = None
	)
	
	case class BareMetalAdminVipConfig(
	  /** The VIP which you previously set aside for the Kubernetes API of this bare metal admin cluster. */
		controlPlaneVip: Option[String] = None
	)
	
	case class BareMetalAdminPortConfig(
	  /** The port that control plane hosted load balancers will listen on. */
		controlPlaneLoadBalancerPort: Option[Int] = None
	)
	
	case class BareMetalAdminManualLbConfig(
	  /** Whether manual load balancing is enabled. */
		enabled: Option[Boolean] = None
	)
	
	case class BareMetalAdminStorageConfig(
	  /** Required. Specifies the config for local PersistentVolumes backed by subdirectories in a shared filesystem. These subdirectores are automatically created during cluster creation. */
		lvpShareConfig: Option[Schema.BareMetalLvpShareConfig] = None,
	  /** Required. Specifies the config for local PersistentVolumes backed by mounted node disks. These disks need to be formatted and mounted by the user, which can be done before or after cluster creation. */
		lvpNodeMountsConfig: Option[Schema.BareMetalLvpConfig] = None
	)
	
	case class BareMetalAdminClusterOperationsConfig(
	  /** Whether collection of application logs/metrics should be enabled (in addition to system logs/metrics). */
		enableApplicationLogs: Option[Boolean] = None
	)
	
	case class BareMetalAdminMaintenanceConfig(
	  /** Required. All IPv4 address from these ranges will be placed into maintenance mode. Nodes in maintenance mode will be cordoned and drained. When both of these are true, the "baremetal.cluster.gke.io/maintenance" annotation will be set on the node resource. */
		maintenanceAddressCidrBlocks: Option[List[String]] = None
	)
	
	case class BareMetalAdminMaintenanceStatus(
	  /** Represents the status of draining and drained machine nodes. This is used to show the progress of cluster upgrade. */
		machineDrainStatus: Option[Schema.BareMetalAdminMachineDrainStatus] = None
	)
	
	case class BareMetalAdminMachineDrainStatus(
	  /** The list of draning machines. */
		drainingMachines: Option[List[Schema.BareMetalAdminDrainingMachine]] = None,
	  /** The list of drained machines. */
		drainedMachines: Option[List[Schema.BareMetalAdminDrainedMachine]] = None
	)
	
	case class BareMetalAdminDrainingMachine(
	  /** Draining machine IP address. */
		nodeIp: Option[String] = None,
	  /** The count of pods yet to drain. */
		podCount: Option[Int] = None
	)
	
	case class BareMetalAdminDrainedMachine(
	  /** Drained machine IP address. */
		nodeIp: Option[String] = None
	)
	
	case class BareMetalAdminWorkloadNodeConfig(
	  /** The maximum number of pods a node can run. The size of the CIDR range assigned to the node will be derived from this parameter. By default 110 Pods are created per Node. Upper bound is 250 for both HA and non-HA admin cluster. Lower bound is 64 for non-HA admin cluster and 32 for HA admin cluster. */
		maxPodsPerNode: Option[String] = None
	)
	
	case class BareMetalAdminProxyConfig(
	  /** Required. Specifies the address of your proxy server. Examples: `http://domain` WARNING: Do not provide credentials in the format `http://(username:password@)domain` these will be rejected by the server. */
		uri: Option[String] = None,
	  /** A list of IPs, hostnames, and domains that should skip the proxy. Examples: ["127.0.0.1", "example.com", ".corp", "localhost"]. */
		noProxy: Option[List[String]] = None
	)
	
	case class BareMetalAdminSecurityConfig(
	  /** Configures user access to the admin cluster. */
		authorization: Option[Schema.Authorization] = None
	)
	
	case class BareMetalAdminNodeAccessConfig(
	  /** Required. LoginUser is the user name used to access node machines. It defaults to "root" if not set. */
		loginUser: Option[String] = None
	)
	
	case class BareMetalAdminOsEnvironmentConfig(
	  /** Whether the package repo should be added when initializing bare metal machines. */
		packageRepoExcluded: Option[Boolean] = None
	)
	
	case class ListBareMetalAdminClustersResponse(
	  /** The list of bare metal admin cluster. */
		bareMetalAdminClusters: Option[List[Schema.BareMetalAdminCluster]] = None,
	  /** A token identifying a page of results the server should return. If the token is not empty this means that more results are available and should be retrieved by repeating the request with the provided page token. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class EnrollBareMetalAdminClusterRequest(
	  /** User provided OnePlatform identifier that is used as part of the resource name. This must be unique among all GKE on-prem clusters within a project and location and will return a 409 if the cluster already exists. (https://tools.ietf.org/html/rfc1123) format. */
		bareMetalAdminClusterId: Option[String] = None,
	  /** Required. This is the full resource name of this admin cluster's fleet membership. */
		membership: Option[String] = None
	)
	
	case class QueryBareMetalAdminVersionConfigResponse(
	  /** List of available versions to install or to upgrade to. */
		versions: Option[List[Schema.BareMetalVersionInfo]] = None
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
		policy: Option[Schema.Policy] = None
	)
	
	case class Policy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.Binding]] = None,
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
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	object OperationMetadata {
		enum TypeEnum extends Enum[TypeEnum] { case OPERATION_TYPE_UNSPECIFIED, CREATE, DELETE, UPDATE, UPGRADE, PLATFORM_UPGRADE }
	}
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
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have [Operation.error] value with a [google.rpc.Status.code] of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None,
	  /** Output only. Type of operation being executed. */
		`type`: Option[Schema.OperationMetadata.TypeEnum] = None,
	  /** Output only. Detailed progress information for the operation. */
		progress: Option[Schema.OperationProgress] = None,
	  /** Output only. Denotes if the local managing cluster's control plane is currently disconnected. This is expected to occur temporarily during self-managed cluster upgrades. */
		controlPlaneDisconnected: Option[Boolean] = None
	)
	
	case class OperationProgress(
	  /** The stages of the operation. */
		stages: Option[List[Schema.OperationStage]] = None
	)
	
	object OperationStage {
		enum StageEnum extends Enum[StageEnum] { case STAGE_UNSPECIFIED, PREFLIGHT_CHECK, CONFIGURE, DEPLOY, HEALTH_CHECK, UPDATE }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, RUNNING, SUCCEEDED, FAILED }
	}
	case class OperationStage(
	  /** The high-level stage of the operation. */
		stage: Option[Schema.OperationStage.StageEnum] = None,
	  /** Progress metric bundle. */
		metrics: Option[List[Schema.Metric]] = None,
	  /** Time the stage started. */
		startTime: Option[String] = None,
	  /** Time the stage ended. */
		endTime: Option[String] = None,
	  /** Output only. State of the stage. */
		state: Option[Schema.OperationStage.StateEnum] = None
	)
	
	object Metric {
		enum MetricEnum extends Enum[MetricEnum] { case METRIC_ID_UNSPECIFIED, NODES_TOTAL, NODES_DRAINING, NODES_UPGRADING, NODES_PENDING_UPGRADE, NODES_UPGRADED, NODES_FAILED, NODES_HEALTHY, NODES_RECONCILING, NODES_IN_MAINTENANCE, PREFLIGHTS_COMPLETED, PREFLIGHTS_RUNNING, PREFLIGHTS_FAILED, PREFLIGHTS_TOTAL }
	}
	case class Metric(
	  /** Required. The metric name. */
		metric: Option[Schema.Metric.MetricEnum] = None,
	  /** For metrics with integer value. */
		intValue: Option[String] = None,
	  /** For metrics with floating point value. */
		doubleValue: Option[BigDecimal] = None,
	  /** For metrics with custom values (ratios, visual progress, etc.). */
		stringValue: Option[String] = None
	)
}
