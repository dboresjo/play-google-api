package com.boresjo.play.api.google.container

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListClustersResponse(
	  /** A list of clusters in the project in the specified zone, or across all ones. */
		clusters: Option[List[Schema.Cluster]] = None,
	  /** If any zones are listed here, the list of clusters returned may be missing those zones. */
		missingZones: Option[List[String]] = None
	)
	
	object Cluster {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNSPECIFIED, PROVISIONING, RUNNING, RECONCILING, STOPPING, ERROR, DEGRADED }
	}
	case class Cluster(
	  /** The name of this cluster. The name must be unique within this project and location (e.g. zone or region), and can be up to 40 characters with the following restrictions: &#42; Lowercase letters, numbers, and hyphens only. &#42; Must start with a letter. &#42; Must end with a number or a letter. */
		name: Option[String] = None,
	  /** An optional description of this cluster. */
		description: Option[String] = None,
	  /** The number of nodes to create in this cluster. You must ensure that your Compute Engine [resource quota](https://cloud.google.com/compute/quotas) is sufficient for this number of instances. You must also have available firewall and routes quota. For requests, this field should only be used in lieu of a "node_pool" object, since this configuration (along with the "node_config") will be used to create a "NodePool" object with an auto-generated name. Do not use this and a node_pool at the same time. This field is deprecated, use node_pool.initial_node_count instead. */
		initialNodeCount: Option[Int] = None,
	  /** Parameters used in creating the cluster's nodes. For requests, this field should only be used in lieu of a "node_pool" object, since this configuration (along with the "initial_node_count") will be used to create a "NodePool" object with an auto-generated name. Do not use this and a node_pool at the same time. For responses, this field will be populated with the node configuration of the first node pool. (For configuration of each node pool, see `node_pool.config`) If unspecified, the defaults are used. This field is deprecated, use node_pool.config instead. */
		nodeConfig: Option[Schema.NodeConfig] = None,
	  /** The authentication information for accessing the master endpoint. If unspecified, the defaults are used: For clusters before v1.12, if master_auth is unspecified, `username` will be set to "admin", a random password will be generated, and a client certificate will be issued. */
		masterAuth: Option[Schema.MasterAuth] = None,
	  /** The logging service the cluster should use to write logs. Currently available options: &#42; `logging.googleapis.com/kubernetes` - The Cloud Logging service with a Kubernetes-native resource model &#42; `logging.googleapis.com` - The legacy Cloud Logging service (no longer available as of GKE 1.15). &#42; `none` - no logs will be exported from the cluster. If left as an empty string,`logging.googleapis.com/kubernetes` will be used for GKE 1.14+ or `logging.googleapis.com` for earlier versions. */
		loggingService: Option[String] = None,
	  /** The monitoring service the cluster should use to write metrics. Currently available options: &#42; "monitoring.googleapis.com/kubernetes" - The Cloud Monitoring service with a Kubernetes-native resource model &#42; `monitoring.googleapis.com` - The legacy Cloud Monitoring service (no longer available as of GKE 1.15). &#42; `none` - No metrics will be exported from the cluster. If left as an empty string,`monitoring.googleapis.com/kubernetes` will be used for GKE 1.14+ or `monitoring.googleapis.com` for earlier versions. */
		monitoringService: Option[String] = None,
	  /** The name of the Google Compute Engine [network](https://cloud.google.com/compute/docs/networks-and-firewalls#networks) to which the cluster is connected. If left unspecified, the `default` network will be used. */
		network: Option[String] = None,
	  /** The IP address range of the container pods in this cluster, in [CIDR](http://en.wikipedia.org/wiki/Classless_Inter-Domain_Routing) notation (e.g. `10.96.0.0/14`). Leave blank to have one automatically chosen or specify a `/14` block in `10.0.0.0/8`. */
		clusterIpv4Cidr: Option[String] = None,
	  /** Configurations for the various addons available to run in the cluster. */
		addonsConfig: Option[Schema.AddonsConfig] = None,
	  /** The name of the Google Compute Engine [subnetwork](https://cloud.google.com/compute/docs/subnetworks) to which the cluster is connected. */
		subnetwork: Option[String] = None,
	  /** The node pools associated with this cluster. This field should not be set if "node_config" or "initial_node_count" are specified. */
		nodePools: Option[List[Schema.NodePool]] = None,
	  /** The list of Google Compute Engine [zones](https://cloud.google.com/compute/docs/zones#available) in which the cluster's nodes should be located. This field provides a default value if [NodePool.Locations](https://cloud.google.com/kubernetes-engine/docs/reference/rest/v1/projects.locations.clusters.nodePools#NodePool.FIELDS.locations) are not specified during node pool creation. Warning: changing cluster locations will update the [NodePool.Locations](https://cloud.google.com/kubernetes-engine/docs/reference/rest/v1/projects.locations.clusters.nodePools#NodePool.FIELDS.locations) of all node pools and will result in nodes being added and/or removed. */
		locations: Option[List[String]] = None,
	  /** Kubernetes alpha features are enabled on this cluster. This includes alpha API groups (e.g. v1alpha1) and features that may not be production ready in the kubernetes version of the master and nodes. The cluster has no SLA for uptime and master/node upgrades are disabled. Alpha enabled clusters are automatically deleted thirty days after creation. */
		enableKubernetesAlpha: Option[Boolean] = None,
	  /** The resource labels for the cluster to use to annotate any related Google Compute Engine resources. */
		resourceLabels: Option[Map[String, String]] = None,
	  /** The fingerprint of the set of labels for this cluster. */
		labelFingerprint: Option[String] = None,
	  /** Configuration for the legacy ABAC authorization mode. */
		legacyAbac: Option[Schema.LegacyAbac] = None,
	  /** Configuration options for the NetworkPolicy feature. */
		networkPolicy: Option[Schema.NetworkPolicy] = None,
	  /** Configuration for cluster IP allocation. */
		ipAllocationPolicy: Option[Schema.IPAllocationPolicy] = None,
	  /** The configuration options for master authorized networks feature. Deprecated: Use ControlPlaneEndpointsConfig.IPEndpointsConfig.authorized_networks_config instead. */
		masterAuthorizedNetworksConfig: Option[Schema.MasterAuthorizedNetworksConfig] = None,
	  /** Configure the maintenance policy for this cluster. */
		maintenancePolicy: Option[Schema.MaintenancePolicy] = None,
	  /** Configuration for Binary Authorization. */
		binaryAuthorization: Option[Schema.BinaryAuthorization] = None,
	  /** Cluster-level autoscaling configuration. */
		autoscaling: Option[Schema.ClusterAutoscaling] = None,
	  /** Configuration for cluster networking. */
		networkConfig: Option[Schema.NetworkConfig] = None,
	  /** The default constraint on the maximum number of pods that can be run simultaneously on a node in the node pool of this cluster. Only honored if cluster created with IP Alias support. */
		defaultMaxPodsConstraint: Option[Schema.MaxPodsConstraint] = None,
	  /** Configuration for exporting resource usages. Resource usage export is disabled when this config is unspecified. */
		resourceUsageExportConfig: Option[Schema.ResourceUsageExportConfig] = None,
	  /** Configuration controlling RBAC group membership information. */
		authenticatorGroupsConfig: Option[Schema.AuthenticatorGroupsConfig] = None,
	  /** Configuration for private cluster. */
		privateClusterConfig: Option[Schema.PrivateClusterConfig] = None,
	  /** Configuration of etcd encryption. */
		databaseEncryption: Option[Schema.DatabaseEncryption] = None,
	  /** Cluster-level Vertical Pod Autoscaling configuration. */
		verticalPodAutoscaling: Option[Schema.VerticalPodAutoscaling] = None,
	  /** Shielded Nodes configuration. */
		shieldedNodes: Option[Schema.ShieldedNodes] = None,
	  /** Release channel configuration. If left unspecified on cluster creation and a version is specified, the cluster is enrolled in the most mature release channel where the version is available (first checking STABLE, then REGULAR, and finally RAPID). Otherwise, if no release channel configuration and no version is specified, the cluster is enrolled in the REGULAR channel with its default version. */
		releaseChannel: Option[Schema.ReleaseChannel] = None,
	  /** Configuration for the use of Kubernetes Service Accounts in GCP IAM policies. */
		workloadIdentityConfig: Option[Schema.WorkloadIdentityConfig] = None,
	  /** Configuration for issuance of mTLS keys and certificates to Kubernetes pods. */
		meshCertificates: Option[Schema.MeshCertificates] = None,
	  /** Configuration for the fine-grained cost management feature. */
		costManagementConfig: Option[Schema.CostManagementConfig] = None,
	  /** Notification configuration of the cluster. */
		notificationConfig: Option[Schema.NotificationConfig] = None,
	  /** Configuration of Confidential Nodes. All the nodes in the cluster will be Confidential VM once enabled. */
		confidentialNodes: Option[Schema.ConfidentialNodes] = None,
	  /** Configuration for Identity Service component. */
		identityServiceConfig: Option[Schema.IdentityServiceConfig] = None,
	  /** Output only. Server-defined URL for the resource. */
		selfLink: Option[String] = None,
	  /** Output only. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. This field is deprecated, use location instead. */
		zone: Option[String] = None,
	  /** Output only. The IP address of this cluster's master endpoint. The endpoint can be accessed from the internet at `https://username:password@endpoint/`. See the `masterAuth` property of this resource for username and password information. */
		endpoint: Option[String] = None,
	  /** The initial Kubernetes version for this cluster. Valid versions are those found in validMasterVersions returned by getServerConfig. The version can be upgraded over time; such upgrades are reflected in currentMasterVersion and currentNodeVersion. Users may specify either explicit versions offered by Kubernetes Engine or version aliases, which have the following behavior: - "latest": picks the highest valid Kubernetes version - "1.X": picks the highest valid patch+gke.N patch in the 1.X version - "1.X.Y": picks the highest valid gke.N patch in the 1.X.Y version - "1.X.Y-gke.N": picks an explicit Kubernetes version - "","-": picks the default Kubernetes version */
		initialClusterVersion: Option[String] = None,
	  /** Output only. The current software version of the master endpoint. */
		currentMasterVersion: Option[String] = None,
	  /** Output only. Deprecated, use [NodePools.version](https://cloud.google.com/kubernetes-engine/docs/reference/rest/v1/projects.locations.clusters.nodePools) instead. The current version of the node software components. If they are currently at multiple versions because they're in the process of being upgraded, this reflects the minimum version of all nodes. */
		currentNodeVersion: Option[String] = None,
	  /** Output only. The time the cluster was created, in [RFC3339](https://www.ietf.org/rfc/rfc3339.txt) text format. */
		createTime: Option[String] = None,
	  /** Output only. The current status of this cluster. */
		status: Option[Schema.Cluster.StatusEnum] = None,
	  /** Output only. Deprecated. Use conditions instead. Additional information about the current status of this cluster, if available. */
		statusMessage: Option[String] = None,
	  /** Output only. The size of the address space on each node for hosting containers. This is provisioned from within the `container_ipv4_cidr` range. This field will only be set when cluster is in route-based network mode. */
		nodeIpv4CidrSize: Option[Int] = None,
	  /** Output only. The IP address range of the Kubernetes services in this cluster, in [CIDR](http://en.wikipedia.org/wiki/Classless_Inter-Domain_Routing) notation (e.g. `1.2.3.4/29`). Service addresses are typically put in the last `/16` from the container CIDR. */
		servicesIpv4Cidr: Option[String] = None,
	  /** Output only. Deprecated. Use node_pools.instance_group_urls. */
		instanceGroupUrls: Option[List[String]] = None,
	  /** Output only. The number of nodes currently in the cluster. Deprecated. Call Kubernetes API directly to retrieve node information. */
		currentNodeCount: Option[Int] = None,
	  /** Output only. The time the cluster will be automatically deleted in [RFC3339](https://www.ietf.org/rfc/rfc3339.txt) text format. */
		expireTime: Option[String] = None,
	  /** Output only. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/regions-zones/regions-zones#available) or [region](https://cloud.google.com/compute/docs/regions-zones/regions-zones#available) in which the cluster resides. */
		location: Option[String] = None,
	  /** Enable the ability to use Cloud TPUs in this cluster. */
		enableTpu: Option[Boolean] = None,
	  /** Output only. The IP address range of the Cloud TPUs in this cluster, in [CIDR](http://en.wikipedia.org/wiki/Classless_Inter-Domain_Routing) notation (e.g. `1.2.3.4/29`). */
		tpuIpv4CidrBlock: Option[String] = None,
	  /** Which conditions caused the current cluster state. */
		conditions: Option[List[Schema.StatusCondition]] = None,
	  /** Autopilot configuration for the cluster. */
		autopilot: Option[Schema.Autopilot] = None,
	  /** Output only. Unique id for the cluster. */
		id: Option[String] = None,
	  /** The configuration of the parent product of the cluster. This field is used by Google internal products that are built on top of the GKE cluster and take the ownership of the cluster. */
		parentProductConfig: Option[Schema.ParentProductConfig] = None,
	  /** Default NodePool settings for the entire cluster. These settings are overridden if specified on the specific NodePool object. */
		nodePoolDefaults: Option[Schema.NodePoolDefaults] = None,
	  /** Logging configuration for the cluster. */
		loggingConfig: Option[Schema.LoggingConfig] = None,
	  /** Monitoring configuration for the cluster. */
		monitoringConfig: Option[Schema.MonitoringConfig] = None,
	  /** Node pool configs that apply to all auto-provisioned node pools in autopilot clusters and node auto-provisioning enabled clusters. */
		nodePoolAutoConfig: Option[Schema.NodePoolAutoConfig] = None,
	  /** This checksum is computed by the server based on the value of cluster fields, and may be sent on update requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Fleet information for the cluster. */
		fleet: Option[Schema.Fleet] = None,
	  /** Enable/Disable Security Posture API features for the cluster. */
		securityPostureConfig: Option[Schema.SecurityPostureConfig] = None,
	  /** Configuration for all cluster's control plane endpoints. */
		controlPlaneEndpointsConfig: Option[Schema.ControlPlaneEndpointsConfig] = None,
	  /** Beta APIs Config */
		enableK8sBetaApis: Option[Schema.K8sBetaAPIConfig] = None,
	  /** GKE Enterprise Configuration. */
		enterpriseConfig: Option[Schema.EnterpriseConfig] = None,
	  /** Secret CSI driver configuration. */
		secretManagerConfig: Option[Schema.SecretManagerConfig] = None,
	  /** Enable/Disable Compliance Posture features for the cluster. */
		compliancePostureConfig: Option[Schema.CompliancePostureConfig] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzi: Option[Boolean] = None,
	  /** The Custom keys configuration for the cluster. */
		userManagedKeysConfig: Option[Schema.UserManagedKeysConfig] = None,
	  /** RBACBindingConfig allows user to restrict ClusterRoleBindings an RoleBindings that can be created. */
		rbacBindingConfig: Option[Schema.RBACBindingConfig] = None
	)
	
	object NodeConfig {
		enum LocalSsdEncryptionModeEnum extends Enum[LocalSsdEncryptionModeEnum] { case LOCAL_SSD_ENCRYPTION_MODE_UNSPECIFIED, STANDARD_ENCRYPTION, EPHEMERAL_KEY_ENCRYPTION }
		enum EffectiveCgroupModeEnum extends Enum[EffectiveCgroupModeEnum] { case EFFECTIVE_CGROUP_MODE_UNSPECIFIED, EFFECTIVE_CGROUP_MODE_V1, EFFECTIVE_CGROUP_MODE_V2 }
	}
	case class NodeConfig(
	  /** The name of a Google Compute Engine [machine type](https://cloud.google.com/compute/docs/machine-types) If unspecified, the default machine type is `e2-medium`. */
		machineType: Option[String] = None,
	  /** Size of the disk attached to each node, specified in GB. The smallest allowed disk size is 10GB. If unspecified, the default disk size is 100GB. */
		diskSizeGb: Option[Int] = None,
	  /** The set of Google API scopes to be made available on all of the node VMs under the "default" service account. The following scopes are recommended, but not required, and by default are not included: &#42; `https://www.googleapis.com/auth/compute` is required for mounting persistent storage on your nodes. &#42; `https://www.googleapis.com/auth/devstorage.read_only` is required for communicating with &#42;&#42;gcr.io&#42;&#42; (the [Google Container Registry](https://cloud.google.com/container-registry/)). If unspecified, no scopes are added, unless Cloud Logging or Cloud Monitoring are enabled, in which case their required scopes will be added. */
		oauthScopes: Option[List[String]] = None,
	  /** The Google Cloud Platform Service Account to be used by the node VMs. Specify the email address of the Service Account; otherwise, if no Service Account is specified, the "default" service account is used. */
		serviceAccount: Option[String] = None,
	  /** The metadata key/value pairs assigned to instances in the cluster. Keys must conform to the regexp `[a-zA-Z0-9-_]+` and be less than 128 bytes in length. These are reflected as part of a URL in the metadata server. Additionally, to avoid ambiguity, keys must not conflict with any other metadata keys for the project or be one of the reserved keys: - "cluster-location" - "cluster-name" - "cluster-uid" - "configure-sh" - "containerd-configure-sh" - "enable-os-login" - "gci-ensure-gke-docker" - "gci-metrics-enabled" - "gci-update-strategy" - "instance-template" - "kube-env" - "startup-script" - "user-data" - "disable-address-manager" - "windows-startup-script-ps1" - "common-psm1" - "k8s-node-setup-psm1" - "install-ssh-psm1" - "user-profile-psm1" Values are free-form strings, and only have meaning as interpreted by the image running in the instance. The only restriction placed on them is that each value's size must be less than or equal to 32 KB. The total size of all keys and values must be less than 512 KB. */
		metadata: Option[Map[String, String]] = None,
	  /** The image type to use for this node. Note that for a given image type, the latest version of it will be used. Please see https://cloud.google.com/kubernetes-engine/docs/concepts/node-images for available image types. */
		imageType: Option[String] = None,
	  /** The map of Kubernetes labels (key/value pairs) to be applied to each node. These will added in addition to any default label(s) that Kubernetes may apply to the node. In case of conflict in label keys, the applied set may differ depending on the Kubernetes version -- it's best to assume the behavior is undefined and conflicts should be avoided. For more information, including usage and the valid values, see: https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/ */
		labels: Option[Map[String, String]] = None,
	  /** The number of local SSD disks to be attached to the node. The limit for this value is dependent upon the maximum number of disks available on a machine per zone. See: https://cloud.google.com/compute/docs/disks/local-ssd for more information. */
		localSsdCount: Option[Int] = None,
	  /** The list of instance tags applied to all nodes. Tags are used to identify valid sources or targets for network firewalls and are specified by the client during cluster or node pool creation. Each tag within the list must comply with RFC1035. */
		tags: Option[List[String]] = None,
	  /** Whether the nodes are created as preemptible VM instances. See: https://cloud.google.com/compute/docs/instances/preemptible for more information about preemptible VM instances. */
		preemptible: Option[Boolean] = None,
	  /** A list of hardware accelerators to be attached to each node. See https://cloud.google.com/compute/docs/gpus for more information about support for GPUs. */
		accelerators: Option[List[Schema.AcceleratorConfig]] = None,
	  /** Type of the disk attached to each node (e.g. 'pd-standard', 'pd-ssd' or 'pd-balanced') If unspecified, the default disk type is 'pd-standard' */
		diskType: Option[String] = None,
	  /** Minimum CPU platform to be used by this instance. The instance may be scheduled on the specified or newer CPU platform. Applicable values are the friendly names of CPU platforms, such as `minCpuPlatform: "Intel Haswell"` or `minCpuPlatform: "Intel Sandy Bridge"`. For more information, read [how to specify min CPU platform](https://cloud.google.com/compute/docs/instances/specify-min-cpu-platform) */
		minCpuPlatform: Option[String] = None,
	  /** The workload metadata configuration for this node. */
		workloadMetadataConfig: Option[Schema.WorkloadMetadataConfig] = None,
	  /** List of kubernetes taints to be applied to each node. For more information, including usage and the valid values, see: https://kubernetes.io/docs/concepts/configuration/taint-and-toleration/ */
		taints: Option[List[Schema.NodeTaint]] = None,
	  /** Sandbox configuration for this node. */
		sandboxConfig: Option[Schema.SandboxConfig] = None,
	  /** Setting this field will assign instances of this pool to run on the specified node group. This is useful for running workloads on [sole tenant nodes](https://cloud.google.com/compute/docs/nodes/sole-tenant-nodes). */
		nodeGroup: Option[String] = None,
	  /** The optional reservation affinity. Setting this field will apply the specified [Zonal Compute Reservation](https://cloud.google.com/compute/docs/instances/reserving-zonal-resources) to this node pool. */
		reservationAffinity: Option[Schema.ReservationAffinity] = None,
	  /** Shielded Instance options. */
		shieldedInstanceConfig: Option[Schema.ShieldedInstanceConfig] = None,
	  /** Parameters that can be configured on Linux nodes. */
		linuxNodeConfig: Option[Schema.LinuxNodeConfig] = None,
	  /** Node kubelet configs. */
		kubeletConfig: Option[Schema.NodeKubeletConfig] = None,
	  /**  The Customer Managed Encryption Key used to encrypt the boot disk attached to each node in the node pool. This should be of the form projects/[KEY_PROJECT_ID]/locations/[LOCATION]/keyRings/[RING_NAME]/cryptoKeys/[KEY_NAME]. For more information about protecting resources with Cloud KMS Keys please see: https://cloud.google.com/compute/docs/disks/customer-managed-encryption */
		bootDiskKmsKey: Option[String] = None,
	  /** Google Container File System (image streaming) configs. */
		gcfsConfig: Option[Schema.GcfsConfig] = None,
	  /** Advanced features for the Compute Engine VM. */
		advancedMachineFeatures: Option[Schema.AdvancedMachineFeatures] = None,
	  /** Enable or disable gvnic in the node pool. */
		gvnic: Option[Schema.VirtualNIC] = None,
	  /** Spot flag for enabling Spot VM, which is a rebrand of the existing preemptible flag. */
		spot: Option[Boolean] = None,
	  /** Confidential nodes config. All the nodes in the node pool will be Confidential VM once enabled. */
		confidentialNodes: Option[Schema.ConfidentialNodes] = None,
	  /** Enable or disable NCCL fast socket for the node pool. */
		fastSocket: Option[Schema.FastSocket] = None,
	  /** The resource labels for the node pool to use to annotate any related Google Compute Engine resources. */
		resourceLabels: Option[Map[String, String]] = None,
	  /** Logging configuration. */
		loggingConfig: Option[Schema.NodePoolLoggingConfig] = None,
	  /** Parameters that can be configured on Windows nodes. */
		windowsNodeConfig: Option[Schema.WindowsNodeConfig] = None,
	  /** Parameters for using raw-block Local NVMe SSDs. */
		localNvmeSsdBlockConfig: Option[Schema.LocalNvmeSsdBlockConfig] = None,
	  /** Parameters for the node ephemeral storage using Local SSDs. If unspecified, ephemeral storage is backed by the boot disk. */
		ephemeralStorageLocalSsdConfig: Option[Schema.EphemeralStorageLocalSsdConfig] = None,
	  /** Parameters for node pools to be backed by shared sole tenant node groups. */
		soleTenantConfig: Option[Schema.SoleTenantConfig] = None,
	  /** Parameters for containerd customization. */
		containerdConfig: Option[Schema.ContainerdConfig] = None,
	  /** A map of resource manager tag keys and values to be attached to the nodes. */
		resourceManagerTags: Option[Schema.ResourceManagerTags] = None,
	  /** Optional. Reserved for future use. */
		enableConfidentialStorage: Option[Boolean] = None,
	  /** List of secondary boot disks attached to the nodes. */
		secondaryBootDisks: Option[List[Schema.SecondaryBootDisk]] = None,
	  /** List of Storage Pools where boot disks are provisioned. */
		storagePools: Option[List[String]] = None,
	  /** Secondary boot disk update strategy. */
		secondaryBootDiskUpdateStrategy: Option[Schema.SecondaryBootDiskUpdateStrategy] = None,
	  /** Specifies which method should be used for encrypting the Local SSDs attahced to the node. */
		localSsdEncryptionMode: Option[Schema.NodeConfig.LocalSsdEncryptionModeEnum] = None,
	  /** Output only. effective_cgroup_mode is the cgroup mode actually used by the node pool. It is determined by the cgroup mode specified in the LinuxNodeConfig or the default cgroup mode based on the cluster creation version. */
		effectiveCgroupMode: Option[Schema.NodeConfig.EffectiveCgroupModeEnum] = None
	)
	
	case class AcceleratorConfig(
	  /** The number of the accelerator cards exposed to an instance. */
		acceleratorCount: Option[String] = None,
	  /** The accelerator type resource name. List of supported accelerators [here](https://cloud.google.com/compute/docs/gpus) */
		acceleratorType: Option[String] = None,
	  /** Size of partitions to create on the GPU. Valid values are described in the NVIDIA [mig user guide](https://docs.nvidia.com/datacenter/tesla/mig-user-guide/#partitioning). */
		gpuPartitionSize: Option[String] = None,
	  /** The configuration for GPU sharing options. */
		gpuSharingConfig: Option[Schema.GPUSharingConfig] = None,
	  /** The configuration for auto installation of GPU driver. */
		gpuDriverInstallationConfig: Option[Schema.GPUDriverInstallationConfig] = None
	)
	
	object GPUSharingConfig {
		enum GpuSharingStrategyEnum extends Enum[GpuSharingStrategyEnum] { case GPU_SHARING_STRATEGY_UNSPECIFIED, TIME_SHARING, MPS }
	}
	case class GPUSharingConfig(
	  /** The max number of containers that can share a physical GPU. */
		maxSharedClientsPerGpu: Option[String] = None,
	  /** The type of GPU sharing strategy to enable on the GPU node. */
		gpuSharingStrategy: Option[Schema.GPUSharingConfig.GpuSharingStrategyEnum] = None
	)
	
	object GPUDriverInstallationConfig {
		enum GpuDriverVersionEnum extends Enum[GpuDriverVersionEnum] { case GPU_DRIVER_VERSION_UNSPECIFIED, INSTALLATION_DISABLED, DEFAULT, LATEST }
	}
	case class GPUDriverInstallationConfig(
	  /** Mode for how the GPU driver is installed. */
		gpuDriverVersion: Option[Schema.GPUDriverInstallationConfig.GpuDriverVersionEnum] = None
	)
	
	object WorkloadMetadataConfig {
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, GCE_METADATA, GKE_METADATA }
	}
	case class WorkloadMetadataConfig(
	  /** Mode is the configuration for how to expose metadata to workloads running on the node pool. */
		mode: Option[Schema.WorkloadMetadataConfig.ModeEnum] = None
	)
	
	object NodeTaint {
		enum EffectEnum extends Enum[EffectEnum] { case EFFECT_UNSPECIFIED, NO_SCHEDULE, PREFER_NO_SCHEDULE, NO_EXECUTE }
	}
	case class NodeTaint(
	  /** Key for taint. */
		key: Option[String] = None,
	  /** Value for taint. */
		value: Option[String] = None,
	  /** Effect for taint. */
		effect: Option[Schema.NodeTaint.EffectEnum] = None
	)
	
	object SandboxConfig {
		enum TypeEnum extends Enum[TypeEnum] { case UNSPECIFIED, GVISOR }
	}
	case class SandboxConfig(
	  /** Type of the sandbox to use for the node. */
		`type`: Option[Schema.SandboxConfig.TypeEnum] = None
	)
	
	object ReservationAffinity {
		enum ConsumeReservationTypeEnum extends Enum[ConsumeReservationTypeEnum] { case UNSPECIFIED, NO_RESERVATION, ANY_RESERVATION, SPECIFIC_RESERVATION }
	}
	case class ReservationAffinity(
	  /** Corresponds to the type of reservation consumption. */
		consumeReservationType: Option[Schema.ReservationAffinity.ConsumeReservationTypeEnum] = None,
	  /** Corresponds to the label key of a reservation resource. To target a SPECIFIC_RESERVATION by name, specify "compute.googleapis.com/reservation-name" as the key and specify the name of your reservation as its value. */
		key: Option[String] = None,
	  /** Corresponds to the label value(s) of reservation resource(s). */
		values: Option[List[String]] = None
	)
	
	case class ShieldedInstanceConfig(
	  /** Defines whether the instance has Secure Boot enabled. Secure Boot helps ensure that the system only runs authentic software by verifying the digital signature of all boot components, and halting the boot process if signature verification fails. */
		enableSecureBoot: Option[Boolean] = None,
	  /** Defines whether the instance has integrity monitoring enabled. Enables monitoring and attestation of the boot integrity of the instance. The attestation is performed against the integrity policy baseline. This baseline is initially derived from the implicitly trusted boot image when the instance is created. */
		enableIntegrityMonitoring: Option[Boolean] = None
	)
	
	object LinuxNodeConfig {
		enum CgroupModeEnum extends Enum[CgroupModeEnum] { case CGROUP_MODE_UNSPECIFIED, CGROUP_MODE_V1, CGROUP_MODE_V2 }
	}
	case class LinuxNodeConfig(
	  /** The Linux kernel parameters to be applied to the nodes and all pods running on the nodes. The following parameters are supported. net.core.busy_poll net.core.busy_read net.core.netdev_max_backlog net.core.rmem_max net.core.wmem_default net.core.wmem_max net.core.optmem_max net.core.somaxconn net.ipv4.tcp_rmem net.ipv4.tcp_wmem net.ipv4.tcp_tw_reuse kernel.shmmni kernel.shmmax kernel.shmall */
		sysctls: Option[Map[String, String]] = None,
	  /** cgroup_mode specifies the cgroup mode to be used on the node. */
		cgroupMode: Option[Schema.LinuxNodeConfig.CgroupModeEnum] = None,
	  /** Optional. Amounts for 2M and 1G hugepages */
		hugepages: Option[Schema.HugepagesConfig] = None
	)
	
	case class HugepagesConfig(
	  /** Optional. Amount of 2M hugepages */
		hugepageSize2m: Option[Int] = None,
	  /** Optional. Amount of 1G hugepages */
		hugepageSize1g: Option[Int] = None
	)
	
	case class NodeKubeletConfig(
	  /** Control the CPU management policy on the node. See https://kubernetes.io/docs/tasks/administer-cluster/cpu-management-policies/ The following values are allowed. &#42; "none": the default, which represents the existing scheduling behavior. &#42; "static": allows pods with certain resource characteristics to be granted increased CPU affinity and exclusivity on the node. The default value is 'none' if unspecified. */
		cpuManagerPolicy: Option[String] = None,
	  /** Enable CPU CFS quota enforcement for containers that specify CPU limits. This option is enabled by default which makes kubelet use CFS quota (https://www.kernel.org/doc/Documentation/scheduler/sched-bwc.txt) to enforce container CPU limits. Otherwise, CPU limits will not be enforced at all. Disable this option to mitigate CPU throttling problems while still having your pods to be in Guaranteed QoS class by specifying the CPU limits. The default value is 'true' if unspecified. */
		cpuCfsQuota: Option[Boolean] = None,
	  /** Set the CPU CFS quota period value 'cpu.cfs_period_us'. The string must be a sequence of decimal numbers, each with optional fraction and a unit suffix, such as "300ms". Valid time units are "ns", "us" (or "µs"), "ms", "s", "m", "h". The value must be a positive duration. */
		cpuCfsQuotaPeriod: Option[String] = None,
	  /** Set the Pod PID limits. See https://kubernetes.io/docs/concepts/policy/pid-limiting/#pod-pid-limits Controls the maximum number of processes allowed to run in a pod. The value must be greater than or equal to 1024 and less than 4194304. */
		podPidsLimit: Option[String] = None,
	  /** Enable or disable Kubelet read only port. */
		insecureKubeletReadonlyPortEnabled: Option[Boolean] = None
	)
	
	case class GcfsConfig(
	  /** Whether to use GCFS. */
		enabled: Option[Boolean] = None
	)
	
	case class AdvancedMachineFeatures(
	  /** The number of threads per physical core. To disable simultaneous multithreading (SMT) set this to 1. If unset, the maximum number of threads supported per core by the underlying processor is assumed. */
		threadsPerCore: Option[String] = None,
	  /** Whether or not to enable nested virtualization (defaults to false). */
		enableNestedVirtualization: Option[Boolean] = None
	)
	
	case class VirtualNIC(
	  /** Whether gVNIC features are enabled in the node pool. */
		enabled: Option[Boolean] = None
	)
	
	case class ConfidentialNodes(
	  /** Whether Confidential Nodes feature is enabled. */
		enabled: Option[Boolean] = None
	)
	
	case class FastSocket(
	  /** Whether Fast Socket features are enabled in the node pool. */
		enabled: Option[Boolean] = None
	)
	
	case class NodePoolLoggingConfig(
	  /** Logging variant configuration. */
		variantConfig: Option[Schema.LoggingVariantConfig] = None
	)
	
	object LoggingVariantConfig {
		enum VariantEnum extends Enum[VariantEnum] { case VARIANT_UNSPECIFIED, DEFAULT, MAX_THROUGHPUT }
	}
	case class LoggingVariantConfig(
	  /** Logging variant deployed on nodes. */
		variant: Option[Schema.LoggingVariantConfig.VariantEnum] = None
	)
	
	object WindowsNodeConfig {
		enum OsVersionEnum extends Enum[OsVersionEnum] { case OS_VERSION_UNSPECIFIED, OS_VERSION_LTSC2019, OS_VERSION_LTSC2022 }
	}
	case class WindowsNodeConfig(
	  /** OSVersion specifies the Windows node config to be used on the node */
		osVersion: Option[Schema.WindowsNodeConfig.OsVersionEnum] = None
	)
	
	case class LocalNvmeSsdBlockConfig(
	  /** Number of local NVMe SSDs to use. The limit for this value is dependent upon the maximum number of disk available on a machine per zone. See: https://cloud.google.com/compute/docs/disks/local-ssd for more information. A zero (or unset) value has different meanings depending on machine type being used: 1. For pre-Gen3 machines, which support flexible numbers of local ssds, zero (or unset) means to disable using local SSDs as ephemeral storage. 2. For Gen3 machines which dictate a specific number of local ssds, zero (or unset) means to use the default number of local ssds that goes with that machine type. For example, for a c3-standard-8-lssd machine, 2 local ssds would be provisioned. For c3-standard-8 (which doesn't support local ssds), 0 will be provisioned. See https://cloud.google.com/compute/docs/disks/local-ssd#choose_number_local_ssds for more info. */
		localSsdCount: Option[Int] = None
	)
	
	case class EphemeralStorageLocalSsdConfig(
	  /** Number of local SSDs to use to back ephemeral storage. Uses NVMe interfaces. A zero (or unset) value has different meanings depending on machine type being used: 1. For pre-Gen3 machines, which support flexible numbers of local ssds, zero (or unset) means to disable using local SSDs as ephemeral storage. The limit for this value is dependent upon the maximum number of disk available on a machine per zone. See: https://cloud.google.com/compute/docs/disks/local-ssd for more information. 2. For Gen3 machines which dictate a specific number of local ssds, zero (or unset) means to use the default number of local ssds that goes with that machine type. For example, for a c3-standard-8-lssd machine, 2 local ssds would be provisioned. For c3-standard-8 (which doesn't support local ssds), 0 will be provisioned. See https://cloud.google.com/compute/docs/disks/local-ssd#choose_number_local_ssds for more info. */
		localSsdCount: Option[Int] = None
	)
	
	case class SoleTenantConfig(
	  /** NodeAffinities used to match to a shared sole tenant node group. */
		nodeAffinities: Option[List[Schema.NodeAffinity]] = None
	)
	
	object NodeAffinity {
		enum OperatorEnum extends Enum[OperatorEnum] { case OPERATOR_UNSPECIFIED, IN, NOT_IN }
	}
	case class NodeAffinity(
	  /** Key for NodeAffinity. */
		key: Option[String] = None,
	  /** Operator for NodeAffinity. */
		operator: Option[Schema.NodeAffinity.OperatorEnum] = None,
	  /** Values for NodeAffinity. */
		values: Option[List[String]] = None
	)
	
	case class ContainerdConfig(
	  /** PrivateRegistryAccessConfig is used to configure access configuration for private container registries. */
		privateRegistryAccessConfig: Option[Schema.PrivateRegistryAccessConfig] = None
	)
	
	case class PrivateRegistryAccessConfig(
	  /** Private registry access is enabled. */
		enabled: Option[Boolean] = None,
	  /** Private registry access configuration. */
		certificateAuthorityDomainConfig: Option[List[Schema.CertificateAuthorityDomainConfig]] = None
	)
	
	case class CertificateAuthorityDomainConfig(
	  /** List of fully qualified domain names (FQDN). Specifying port is supported. Wilcards are NOT supported. Examples: - my.customdomain.com - 10.0.1.2:5000 */
		fqdns: Option[List[String]] = None,
	  /** Google Secret Manager (GCP) certificate configuration. */
		gcpSecretManagerCertificateConfig: Option[Schema.GCPSecretManagerCertificateConfig] = None
	)
	
	case class GCPSecretManagerCertificateConfig(
	  /** Secret URI, in the form "projects/$PROJECT_ID/secrets/$SECRET_NAME/versions/$VERSION". Version can be fixed (e.g. "2") or "latest" */
		secretUri: Option[String] = None
	)
	
	case class ResourceManagerTags(
	  /** TagKeyValue must be in one of the following formats ([KEY]=[VALUE]) 1. `tagKeys/{tag_key_id}=tagValues/{tag_value_id}` 2. `{org_id}/{tag_key_name}={tag_value_name}` 3. `{project_id}/{tag_key_name}={tag_value_name}` */
		tags: Option[Map[String, String]] = None
	)
	
	object SecondaryBootDisk {
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, CONTAINER_IMAGE_CACHE }
	}
	case class SecondaryBootDisk(
	  /** Disk mode (container image cache, etc.) */
		mode: Option[Schema.SecondaryBootDisk.ModeEnum] = None,
	  /** Fully-qualified resource ID for an existing disk image. */
		diskImage: Option[String] = None
	)
	
	case class SecondaryBootDiskUpdateStrategy(
	
	)
	
	case class MasterAuth(
	  /** The username to use for HTTP basic authentication to the master endpoint. For clusters v1.6.0 and later, basic authentication can be disabled by leaving username unspecified (or setting it to the empty string). Warning: basic authentication is deprecated, and will be removed in GKE control plane versions 1.19 and newer. For a list of recommended authentication methods, see: https://cloud.google.com/kubernetes-engine/docs/how-to/api-server-authentication */
		username: Option[String] = None,
	  /** The password to use for HTTP basic authentication to the master endpoint. Because the master endpoint is open to the Internet, you should create a strong password. If a password is provided for cluster creation, username must be non-empty. Warning: basic authentication is deprecated, and will be removed in GKE control plane versions 1.19 and newer. For a list of recommended authentication methods, see: https://cloud.google.com/kubernetes-engine/docs/how-to/api-server-authentication */
		password: Option[String] = None,
	  /** Configuration for client certificate authentication on the cluster. For clusters before v1.12, if no configuration is specified, a client certificate is issued. */
		clientCertificateConfig: Option[Schema.ClientCertificateConfig] = None,
	  /** Output only. Base64-encoded public certificate that is the root of trust for the cluster. */
		clusterCaCertificate: Option[String] = None,
	  /** Output only. Base64-encoded public certificate used by clients to authenticate to the cluster endpoint. Issued only if client_certificate_config is set. */
		clientCertificate: Option[String] = None,
	  /** Output only. Base64-encoded private key used by clients to authenticate to the cluster endpoint. */
		clientKey: Option[String] = None
	)
	
	case class ClientCertificateConfig(
	  /** Issue a client certificate. */
		issueClientCertificate: Option[Boolean] = None
	)
	
	case class AddonsConfig(
	  /** Configuration for the HTTP (L7) load balancing controller addon, which makes it easy to set up HTTP load balancers for services in a cluster. */
		httpLoadBalancing: Option[Schema.HttpLoadBalancing] = None,
	  /** Configuration for the horizontal pod autoscaling feature, which increases or decreases the number of replica pods a replication controller has based on the resource usage of the existing pods. */
		horizontalPodAutoscaling: Option[Schema.HorizontalPodAutoscaling] = None,
	  /** Configuration for the Kubernetes Dashboard. This addon is deprecated, and will be disabled in 1.15. It is recommended to use the Cloud Console to manage and monitor your Kubernetes clusters, workloads and applications. For more information, see: https://cloud.google.com/kubernetes-engine/docs/concepts/dashboards */
		kubernetesDashboard: Option[Schema.KubernetesDashboard] = None,
	  /** Configuration for NetworkPolicy. This only tracks whether the addon is enabled or not on the Master, it does not track whether network policy is enabled for the nodes. */
		networkPolicyConfig: Option[Schema.NetworkPolicyConfig] = None,
	  /** Configuration for the Cloud Run addon, which allows the user to use a managed Knative service. */
		cloudRunConfig: Option[Schema.CloudRunConfig] = None,
	  /** Configuration for NodeLocalDNS, a dns cache running on cluster nodes */
		dnsCacheConfig: Option[Schema.DnsCacheConfig] = None,
	  /** Configuration for the ConfigConnector add-on, a Kubernetes extension to manage hosted GCP services through the Kubernetes API */
		configConnectorConfig: Option[Schema.ConfigConnectorConfig] = None,
	  /** Configuration for the Compute Engine Persistent Disk CSI driver. */
		gcePersistentDiskCsiDriverConfig: Option[Schema.GcePersistentDiskCsiDriverConfig] = None,
	  /** Configuration for the GCP Filestore CSI driver. */
		gcpFilestoreCsiDriverConfig: Option[Schema.GcpFilestoreCsiDriverConfig] = None,
	  /** Configuration for the Backup for GKE agent addon. */
		gkeBackupAgentConfig: Option[Schema.GkeBackupAgentConfig] = None,
	  /** Configuration for the Cloud Storage Fuse CSI driver. */
		gcsFuseCsiDriverConfig: Option[Schema.GcsFuseCsiDriverConfig] = None,
	  /** Optional. Configuration for the StatefulHA add-on. */
		statefulHaConfig: Option[Schema.StatefulHAConfig] = None,
	  /** Configuration for the Cloud Storage Parallelstore CSI driver. */
		parallelstoreCsiDriverConfig: Option[Schema.ParallelstoreCsiDriverConfig] = None,
	  /** Optional. Configuration for Ray Operator addon. */
		rayOperatorConfig: Option[Schema.RayOperatorConfig] = None
	)
	
	case class HttpLoadBalancing(
	  /** Whether the HTTP Load Balancing controller is enabled in the cluster. When enabled, it runs a small pod in the cluster that manages the load balancers. */
		disabled: Option[Boolean] = None
	)
	
	case class HorizontalPodAutoscaling(
	  /** Whether the Horizontal Pod Autoscaling feature is enabled in the cluster. When enabled, it ensures that metrics are collected into Stackdriver Monitoring. */
		disabled: Option[Boolean] = None
	)
	
	case class KubernetesDashboard(
	  /** Whether the Kubernetes Dashboard is enabled for this cluster. */
		disabled: Option[Boolean] = None
	)
	
	case class NetworkPolicyConfig(
	  /** Whether NetworkPolicy is enabled for this cluster. */
		disabled: Option[Boolean] = None
	)
	
	object CloudRunConfig {
		enum LoadBalancerTypeEnum extends Enum[LoadBalancerTypeEnum] { case LOAD_BALANCER_TYPE_UNSPECIFIED, LOAD_BALANCER_TYPE_EXTERNAL, LOAD_BALANCER_TYPE_INTERNAL }
	}
	case class CloudRunConfig(
	  /** Whether Cloud Run addon is enabled for this cluster. */
		disabled: Option[Boolean] = None,
	  /** Which load balancer type is installed for Cloud Run. */
		loadBalancerType: Option[Schema.CloudRunConfig.LoadBalancerTypeEnum] = None
	)
	
	case class DnsCacheConfig(
	  /** Whether NodeLocal DNSCache is enabled for this cluster. */
		enabled: Option[Boolean] = None
	)
	
	case class ConfigConnectorConfig(
	  /** Whether Cloud Connector is enabled for this cluster. */
		enabled: Option[Boolean] = None
	)
	
	case class GcePersistentDiskCsiDriverConfig(
	  /** Whether the Compute Engine PD CSI driver is enabled for this cluster. */
		enabled: Option[Boolean] = None
	)
	
	case class GcpFilestoreCsiDriverConfig(
	  /** Whether the GCP Filestore CSI driver is enabled for this cluster. */
		enabled: Option[Boolean] = None
	)
	
	case class GkeBackupAgentConfig(
	  /** Whether the Backup for GKE agent is enabled for this cluster. */
		enabled: Option[Boolean] = None
	)
	
	case class GcsFuseCsiDriverConfig(
	  /** Whether the Cloud Storage Fuse CSI driver is enabled for this cluster. */
		enabled: Option[Boolean] = None
	)
	
	case class StatefulHAConfig(
	  /** Whether the Stateful HA add-on is enabled for this cluster. */
		enabled: Option[Boolean] = None
	)
	
	case class ParallelstoreCsiDriverConfig(
	  /** Whether the Cloud Storage Parallelstore CSI driver is enabled for this cluster. */
		enabled: Option[Boolean] = None
	)
	
	case class RayOperatorConfig(
	  /** Whether the Ray Operator addon is enabled for this cluster. */
		enabled: Option[Boolean] = None,
	  /** Optional. Logging configuration for Ray clusters. */
		rayClusterLoggingConfig: Option[Schema.RayClusterLoggingConfig] = None,
	  /** Optional. Monitoring configuration for Ray clusters. */
		rayClusterMonitoringConfig: Option[Schema.RayClusterMonitoringConfig] = None
	)
	
	case class RayClusterLoggingConfig(
	  /** Enable log collection for Ray clusters. */
		enabled: Option[Boolean] = None
	)
	
	case class RayClusterMonitoringConfig(
	  /** Enable metrics collection for Ray clusters. */
		enabled: Option[Boolean] = None
	)
	
	object NodePool {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNSPECIFIED, PROVISIONING, RUNNING, RUNNING_WITH_ERROR, RECONCILING, STOPPING, ERROR }
	}
	case class NodePool(
	  /** The name of the node pool. */
		name: Option[String] = None,
	  /** The node configuration of the pool. */
		config: Option[Schema.NodeConfig] = None,
	  /** The initial node count for the pool. You must ensure that your Compute Engine [resource quota](https://cloud.google.com/compute/quotas) is sufficient for this number of instances. You must also have available firewall and routes quota. */
		initialNodeCount: Option[Int] = None,
	  /** The list of Google Compute Engine [zones](https://cloud.google.com/compute/docs/zones#available) in which the NodePool's nodes should be located. If this value is unspecified during node pool creation, the [Cluster.Locations](https://cloud.google.com/kubernetes-engine/docs/reference/rest/v1/projects.locations.clusters#Cluster.FIELDS.locations) value will be used, instead. Warning: changing node pool locations will result in nodes being added and/or removed. */
		locations: Option[List[String]] = None,
	  /** Networking configuration for this NodePool. If specified, it overrides the cluster-level defaults. */
		networkConfig: Option[Schema.NodeNetworkConfig] = None,
	  /** Output only. Server-defined URL for the resource. */
		selfLink: Option[String] = None,
	  /** The version of Kubernetes running on this NodePool's nodes. If unspecified, it defaults as described [here](https://cloud.google.com/kubernetes-engine/versioning#specifying_node_version). */
		version: Option[String] = None,
	  /** Output only. The resource URLs of the [managed instance groups](https://cloud.google.com/compute/docs/instance-groups/creating-groups-of-managed-instances) associated with this node pool. During the node pool blue-green upgrade operation, the URLs contain both blue and green resources. */
		instanceGroupUrls: Option[List[String]] = None,
	  /** Output only. The status of the nodes in this pool instance. */
		status: Option[Schema.NodePool.StatusEnum] = None,
	  /** Output only. Deprecated. Use conditions instead. Additional information about the current status of this node pool instance, if available. */
		statusMessage: Option[String] = None,
	  /** Autoscaler configuration for this NodePool. Autoscaler is enabled only if a valid configuration is present. */
		autoscaling: Option[Schema.NodePoolAutoscaling] = None,
	  /** NodeManagement configuration for this NodePool. */
		management: Option[Schema.NodeManagement] = None,
	  /** The constraint on the maximum number of pods that can be run simultaneously on a node in the node pool. */
		maxPodsConstraint: Option[Schema.MaxPodsConstraint] = None,
	  /** Which conditions caused the current node pool state. */
		conditions: Option[List[Schema.StatusCondition]] = None,
	  /** Output only. The pod CIDR block size per node in this node pool. */
		podIpv4CidrSize: Option[Int] = None,
	  /** Upgrade settings control disruption and speed of the upgrade. */
		upgradeSettings: Option[Schema.UpgradeSettings] = None,
	  /** Specifies the node placement policy. */
		placementPolicy: Option[Schema.PlacementPolicy] = None,
	  /** Output only. Update info contains relevant information during a node pool update. */
		updateInfo: Option[Schema.UpdateInfo] = None,
	  /** This checksum is computed by the server based on the value of node pool fields, and may be sent on update requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Specifies the configuration of queued provisioning. */
		queuedProvisioning: Option[Schema.QueuedProvisioning] = None,
	  /** Enable best effort provisioning for nodes */
		bestEffortProvisioning: Option[Schema.BestEffortProvisioning] = None
	)
	
	case class NodeNetworkConfig(
	  /** Input only. Whether to create a new range for pod IPs in this node pool. Defaults are provided for `pod_range` and `pod_ipv4_cidr_block` if they are not specified. If neither `create_pod_range` or `pod_range` are specified, the cluster-level default (`ip_allocation_policy.cluster_ipv4_cidr_block`) is used. Only applicable if `ip_allocation_policy.use_ip_aliases` is true. This field cannot be changed after the node pool has been created. */
		createPodRange: Option[Boolean] = None,
	  /** The ID of the secondary range for pod IPs. If `create_pod_range` is true, this ID is used for the new range. If `create_pod_range` is false, uses an existing secondary range with this ID. Only applicable if `ip_allocation_policy.use_ip_aliases` is true. This field cannot be changed after the node pool has been created. */
		podRange: Option[String] = None,
	  /** The IP address range for pod IPs in this node pool. Only applicable if `create_pod_range` is true. Set to blank to have a range chosen with the default size. Set to /netmask (e.g. `/14`) to have a range chosen with a specific netmask. Set to a [CIDR](https://en.wikipedia.org/wiki/Classless_Inter-Domain_Routing) notation (e.g. `10.96.0.0/14`) to pick a specific range to use. Only applicable if `ip_allocation_policy.use_ip_aliases` is true. This field cannot be changed after the node pool has been created. */
		podIpv4CidrBlock: Option[String] = None,
	  /** Whether nodes have internal IP addresses only. If enable_private_nodes is not specified, then the value is derived from Cluster.NetworkConfig.default_enable_private_nodes */
		enablePrivateNodes: Option[Boolean] = None,
	  /** Network bandwidth tier configuration. */
		networkPerformanceConfig: Option[Schema.NetworkPerformanceConfig] = None,
	  /** [PRIVATE FIELD] Pod CIDR size overprovisioning config for the nodepool. Pod CIDR size per node depends on max_pods_per_node. By default, the value of max_pods_per_node is rounded off to next power of 2 and we then double that to get the size of pod CIDR block per node. Example: max_pods_per_node of 30 would result in 64 IPs (/26). This config can disable the doubling of IPs (we still round off to next power of 2) Example: max_pods_per_node of 30 will result in 32 IPs (/27) when overprovisioning is disabled. */
		podCidrOverprovisionConfig: Option[Schema.PodCIDROverprovisionConfig] = None,
	  /** We specify the additional node networks for this node pool using this list. Each node network corresponds to an additional interface */
		additionalNodeNetworkConfigs: Option[List[Schema.AdditionalNodeNetworkConfig]] = None,
	  /** We specify the additional pod networks for this node pool using this list. Each pod network corresponds to an additional alias IP range for the node */
		additionalPodNetworkConfigs: Option[List[Schema.AdditionalPodNetworkConfig]] = None,
	  /** Output only. The utilization of the IPv4 range for the pod. The ratio is Usage/[Total number of IPs in the secondary range], Usage=numNodes&#42;numZones&#42;podIPsPerNode. */
		podIpv4RangeUtilization: Option[BigDecimal] = None
	)
	
	object NetworkPerformanceConfig {
		enum TotalEgressBandwidthTierEnum extends Enum[TotalEgressBandwidthTierEnum] { case TIER_UNSPECIFIED, TIER_1 }
	}
	case class NetworkPerformanceConfig(
	  /** Specifies the total network bandwidth tier for the NodePool. */
		totalEgressBandwidthTier: Option[Schema.NetworkPerformanceConfig.TotalEgressBandwidthTierEnum] = None
	)
	
	case class PodCIDROverprovisionConfig(
	  /** Whether Pod CIDR overprovisioning is disabled. Note: Pod CIDR overprovisioning is enabled by default. */
		disable: Option[Boolean] = None
	)
	
	case class AdditionalNodeNetworkConfig(
	  /** Name of the VPC where the additional interface belongs */
		network: Option[String] = None,
	  /** Name of the subnetwork where the additional interface belongs */
		subnetwork: Option[String] = None
	)
	
	case class AdditionalPodNetworkConfig(
	  /** Name of the subnetwork where the additional pod network belongs. */
		subnetwork: Option[String] = None,
	  /** The name of the secondary range on the subnet which provides IP address for this pod range. */
		secondaryPodRange: Option[String] = None,
	  /** The maximum number of pods per node which use this pod network. */
		maxPodsPerNode: Option[Schema.MaxPodsConstraint] = None,
	  /** The name of the network attachment for pods to communicate to; cannot be specified along with subnetwork or secondary_pod_range. */
		networkAttachment: Option[String] = None
	)
	
	case class MaxPodsConstraint(
	  /** Constraint enforced on the max num of pods per node. */
		maxPodsPerNode: Option[String] = None
	)
	
	object NodePoolAutoscaling {
		enum LocationPolicyEnum extends Enum[LocationPolicyEnum] { case LOCATION_POLICY_UNSPECIFIED, BALANCED, ANY }
	}
	case class NodePoolAutoscaling(
	  /** Is autoscaling enabled for this node pool. */
		enabled: Option[Boolean] = None,
	  /** Minimum number of nodes for one location in the node pool. Must be greater than or equal to 0 and less than or equal to max_node_count. */
		minNodeCount: Option[Int] = None,
	  /** Maximum number of nodes for one location in the node pool. Must be >= min_node_count. There has to be enough quota to scale up the cluster. */
		maxNodeCount: Option[Int] = None,
	  /** Can this node pool be deleted automatically. */
		autoprovisioned: Option[Boolean] = None,
	  /** Location policy used when scaling up a nodepool. */
		locationPolicy: Option[Schema.NodePoolAutoscaling.LocationPolicyEnum] = None,
	  /** Minimum number of nodes in the node pool. Must be greater than or equal to 0 and less than or equal to total_max_node_count. The total_&#42;_node_count fields are mutually exclusive with the &#42;_node_count fields. */
		totalMinNodeCount: Option[Int] = None,
	  /** Maximum number of nodes in the node pool. Must be greater than or equal to total_min_node_count. There has to be enough quota to scale up the cluster. The total_&#42;_node_count fields are mutually exclusive with the &#42;_node_count fields. */
		totalMaxNodeCount: Option[Int] = None
	)
	
	case class NodeManagement(
	  /** A flag that specifies whether node auto-upgrade is enabled for the node pool. If enabled, node auto-upgrade helps keep the nodes in your node pool up to date with the latest release version of Kubernetes. */
		autoUpgrade: Option[Boolean] = None,
	  /** A flag that specifies whether the node auto-repair is enabled for the node pool. If enabled, the nodes in this node pool will be monitored and, if they fail health checks too many times, an automatic repair action will be triggered. */
		autoRepair: Option[Boolean] = None,
	  /** Specifies the Auto Upgrade knobs for the node pool. */
		upgradeOptions: Option[Schema.AutoUpgradeOptions] = None
	)
	
	case class AutoUpgradeOptions(
	  /** Output only. This field is set when upgrades are about to commence with the approximate start time for the upgrades, in [RFC3339](https://www.ietf.org/rfc/rfc3339.txt) text format. */
		autoUpgradeStartTime: Option[String] = None,
	  /** Output only. This field is set when upgrades are about to commence with the description of the upgrade. */
		description: Option[String] = None
	)
	
	object StatusCondition {
		enum CodeEnum extends Enum[CodeEnum] { case UNKNOWN, GCE_STOCKOUT, GKE_SERVICE_ACCOUNT_DELETED, GCE_QUOTA_EXCEEDED, SET_BY_OPERATOR, CLOUD_KMS_KEY_ERROR, CA_EXPIRING }
		enum CanonicalCodeEnum extends Enum[CanonicalCodeEnum] { case OK, CANCELLED, UNKNOWN, INVALID_ARGUMENT, DEADLINE_EXCEEDED, NOT_FOUND, ALREADY_EXISTS, PERMISSION_DENIED, UNAUTHENTICATED, RESOURCE_EXHAUSTED, FAILED_PRECONDITION, ABORTED, OUT_OF_RANGE, UNIMPLEMENTED, INTERNAL, UNAVAILABLE, DATA_LOSS }
	}
	case class StatusCondition(
	  /** Machine-friendly representation of the condition Deprecated. Use canonical_code instead. */
		code: Option[Schema.StatusCondition.CodeEnum] = None,
	  /** Human-friendly representation of the condition */
		message: Option[String] = None,
	  /** Canonical code of the condition. */
		canonicalCode: Option[Schema.StatusCondition.CanonicalCodeEnum] = None
	)
	
	object UpgradeSettings {
		enum StrategyEnum extends Enum[StrategyEnum] { case NODE_POOL_UPDATE_STRATEGY_UNSPECIFIED, BLUE_GREEN, SURGE }
	}
	case class UpgradeSettings(
	  /** The maximum number of nodes that can be created beyond the current size of the node pool during the upgrade process. */
		maxSurge: Option[Int] = None,
	  /** The maximum number of nodes that can be simultaneously unavailable during the upgrade process. A node is considered available if its status is Ready. */
		maxUnavailable: Option[Int] = None,
	  /** Update strategy of the node pool. */
		strategy: Option[Schema.UpgradeSettings.StrategyEnum] = None,
	  /** Settings for blue-green upgrade strategy. */
		blueGreenSettings: Option[Schema.BlueGreenSettings] = None
	)
	
	case class BlueGreenSettings(
	  /** Standard policy for the blue-green upgrade. */
		standardRolloutPolicy: Option[Schema.StandardRolloutPolicy] = None,
	  /** Time needed after draining entire blue pool. After this period, blue pool will be cleaned up. */
		nodePoolSoakDuration: Option[String] = None
	)
	
	case class StandardRolloutPolicy(
	  /** Percentage of the blue pool nodes to drain in a batch. The range of this field should be (0.0, 1.0]. */
		batchPercentage: Option[BigDecimal] = None,
	  /** Number of blue nodes to drain in a batch. */
		batchNodeCount: Option[Int] = None,
	  /** Soak time after each batch gets drained. Default to zero. */
		batchSoakDuration: Option[String] = None
	)
	
	object PlacementPolicy {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, COMPACT }
	}
	case class PlacementPolicy(
	  /** The type of placement. */
		`type`: Option[Schema.PlacementPolicy.TypeEnum] = None,
	  /** Optional. TPU placement topology for pod slice node pool. https://cloud.google.com/tpu/docs/types-topologies#tpu_topologies */
		tpuTopology: Option[String] = None,
	  /** If set, refers to the name of a custom resource policy supplied by the user. The resource policy must be in the same project and region as the node pool. If not found, InvalidArgument error is returned. */
		policyName: Option[String] = None
	)
	
	case class UpdateInfo(
	  /** Information of a blue-green upgrade. */
		blueGreenInfo: Option[Schema.BlueGreenInfo] = None
	)
	
	object BlueGreenInfo {
		enum PhaseEnum extends Enum[PhaseEnum] { case PHASE_UNSPECIFIED, UPDATE_STARTED, CREATING_GREEN_POOL, CORDONING_BLUE_POOL, DRAINING_BLUE_POOL, NODE_POOL_SOAKING, DELETING_BLUE_POOL, ROLLBACK_STARTED }
	}
	case class BlueGreenInfo(
	  /** Current blue-green upgrade phase. */
		phase: Option[Schema.BlueGreenInfo.PhaseEnum] = None,
	  /** The resource URLs of the [managed instance groups] (/compute/docs/instance-groups/creating-groups-of-managed-instances) associated with blue pool. */
		blueInstanceGroupUrls: Option[List[String]] = None,
	  /** The resource URLs of the [managed instance groups] (/compute/docs/instance-groups/creating-groups-of-managed-instances) associated with green pool. */
		greenInstanceGroupUrls: Option[List[String]] = None,
	  /** Time to start deleting blue pool to complete blue-green upgrade, in [RFC3339](https://www.ietf.org/rfc/rfc3339.txt) text format. */
		bluePoolDeletionStartTime: Option[String] = None,
	  /** Version of green pool. */
		greenPoolVersion: Option[String] = None
	)
	
	case class QueuedProvisioning(
	  /** Denotes that this nodepool is QRM specific, meaning nodes can be only obtained through queuing via the Cluster Autoscaler ProvisioningRequest API. */
		enabled: Option[Boolean] = None
	)
	
	case class BestEffortProvisioning(
	  /** When this is enabled, cluster/node pool creations will ignore non-fatal errors like stockout to best provision as many nodes as possible right now and eventually bring up all target number of nodes */
		enabled: Option[Boolean] = None,
	  /** Minimum number of nodes to be provisioned to be considered as succeeded, and the rest of nodes will be provisioned gradually and eventually when stockout issue has been resolved. */
		minProvisionNodes: Option[Int] = None
	)
	
	case class LegacyAbac(
	  /** Whether the ABAC authorizer is enabled for this cluster. When enabled, identities in the system, including service accounts, nodes, and controllers, will have statically granted permissions beyond those provided by the RBAC configuration or IAM. */
		enabled: Option[Boolean] = None
	)
	
	object NetworkPolicy {
		enum ProviderEnum extends Enum[ProviderEnum] { case PROVIDER_UNSPECIFIED, CALICO }
	}
	case class NetworkPolicy(
	  /** The selected network policy provider. */
		provider: Option[Schema.NetworkPolicy.ProviderEnum] = None,
	  /** Whether network policy is enabled on the cluster. */
		enabled: Option[Boolean] = None
	)
	
	object IPAllocationPolicy {
		enum StackTypeEnum extends Enum[StackTypeEnum] { case STACK_TYPE_UNSPECIFIED, IPV4, IPV4_IPV6 }
		enum Ipv6AccessTypeEnum extends Enum[Ipv6AccessTypeEnum] { case IPV6_ACCESS_TYPE_UNSPECIFIED, INTERNAL, EXTERNAL }
	}
	case class IPAllocationPolicy(
	  /** Whether alias IPs will be used for pod IPs in the cluster. This is used in conjunction with use_routes. It cannot be true if use_routes is true. If both use_ip_aliases and use_routes are false, then the server picks the default IP allocation mode */
		useIpAliases: Option[Boolean] = None,
	  /** Whether a new subnetwork will be created automatically for the cluster. This field is only applicable when `use_ip_aliases` is true. */
		createSubnetwork: Option[Boolean] = None,
	  /** A custom subnetwork name to be used if `create_subnetwork` is true. If this field is empty, then an automatic name will be chosen for the new subnetwork. */
		subnetworkName: Option[String] = None,
	  /** This field is deprecated, use cluster_ipv4_cidr_block. */
		clusterIpv4Cidr: Option[String] = None,
	  /** This field is deprecated, use node_ipv4_cidr_block. */
		nodeIpv4Cidr: Option[String] = None,
	  /** This field is deprecated, use services_ipv4_cidr_block. */
		servicesIpv4Cidr: Option[String] = None,
	  /** The name of the secondary range to be used for the cluster CIDR block. The secondary range will be used for pod IP addresses. This must be an existing secondary range associated with the cluster subnetwork. This field is only applicable with use_ip_aliases is true and create_subnetwork is false. */
		clusterSecondaryRangeName: Option[String] = None,
	  /** The name of the secondary range to be used as for the services CIDR block. The secondary range will be used for service ClusterIPs. This must be an existing secondary range associated with the cluster subnetwork. This field is only applicable with use_ip_aliases is true and create_subnetwork is false. */
		servicesSecondaryRangeName: Option[String] = None,
	  /** The IP address range for the cluster pod IPs. If this field is set, then `cluster.cluster_ipv4_cidr` must be left blank. This field is only applicable when `use_ip_aliases` is true. Set to blank to have a range chosen with the default size. Set to /netmask (e.g. `/14`) to have a range chosen with a specific netmask. Set to a [CIDR](http://en.wikipedia.org/wiki/Classless_Inter-Domain_Routing) notation (e.g. `10.96.0.0/14`) from the RFC-1918 private networks (e.g. `10.0.0.0/8`, `172.16.0.0/12`, `192.168.0.0/16`) to pick a specific range to use. */
		clusterIpv4CidrBlock: Option[String] = None,
	  /** The IP address range of the instance IPs in this cluster. This is applicable only if `create_subnetwork` is true. Set to blank to have a range chosen with the default size. Set to /netmask (e.g. `/14`) to have a range chosen with a specific netmask. Set to a [CIDR](http://en.wikipedia.org/wiki/Classless_Inter-Domain_Routing) notation (e.g. `10.96.0.0/14`) from the RFC-1918 private networks (e.g. `10.0.0.0/8`, `172.16.0.0/12`, `192.168.0.0/16`) to pick a specific range to use. */
		nodeIpv4CidrBlock: Option[String] = None,
	  /** The IP address range of the services IPs in this cluster. If blank, a range will be automatically chosen with the default size. This field is only applicable when `use_ip_aliases` is true. Set to blank to have a range chosen with the default size. Set to /netmask (e.g. `/14`) to have a range chosen with a specific netmask. Set to a [CIDR](http://en.wikipedia.org/wiki/Classless_Inter-Domain_Routing) notation (e.g. `10.96.0.0/14`) from the RFC-1918 private networks (e.g. `10.0.0.0/8`, `172.16.0.0/12`, `192.168.0.0/16`) to pick a specific range to use. */
		servicesIpv4CidrBlock: Option[String] = None,
	  /** The IP address range of the Cloud TPUs in this cluster. If unspecified, a range will be automatically chosen with the default size. This field is only applicable when `use_ip_aliases` is true. If unspecified, the range will use the default size. Set to /netmask (e.g. `/14`) to have a range chosen with a specific netmask. Set to a [CIDR](http://en.wikipedia.org/wiki/Classless_Inter-Domain_Routing) notation (e.g. `10.96.0.0/14`) from the RFC-1918 private networks (e.g. `10.0.0.0/8`, `172.16.0.0/12`, `192.168.0.0/16`) to pick a specific range to use. */
		tpuIpv4CidrBlock: Option[String] = None,
	  /** Whether routes will be used for pod IPs in the cluster. This is used in conjunction with use_ip_aliases. It cannot be true if use_ip_aliases is true. If both use_ip_aliases and use_routes are false, then the server picks the default IP allocation mode */
		useRoutes: Option[Boolean] = None,
	  /** The IP stack type of the cluster */
		stackType: Option[Schema.IPAllocationPolicy.StackTypeEnum] = None,
	  /** The ipv6 access type (internal or external) when create_subnetwork is true */
		ipv6AccessType: Option[Schema.IPAllocationPolicy.Ipv6AccessTypeEnum] = None,
	  /** [PRIVATE FIELD] Pod CIDR size overprovisioning config for the cluster. Pod CIDR size per node depends on max_pods_per_node. By default, the value of max_pods_per_node is doubled and then rounded off to next power of 2 to get the size of pod CIDR block per node. Example: max_pods_per_node of 30 would result in 64 IPs (/26). This config can disable the doubling of IPs (we still round off to next power of 2) Example: max_pods_per_node of 30 will result in 32 IPs (/27) when overprovisioning is disabled. */
		podCidrOverprovisionConfig: Option[Schema.PodCIDROverprovisionConfig] = None,
	  /** Output only. The subnet's IPv6 CIDR block used by nodes and pods. */
		subnetIpv6CidrBlock: Option[String] = None,
	  /** Output only. The services IPv6 CIDR block for the cluster. */
		servicesIpv6CidrBlock: Option[String] = None,
	  /** Output only. The additional pod ranges that are added to the cluster. These pod ranges can be used by new node pools to allocate pod IPs automatically. Once the range is removed it will not show up in IPAllocationPolicy. */
		additionalPodRangesConfig: Option[Schema.AdditionalPodRangesConfig] = None,
	  /** Output only. The utilization of the cluster default IPv4 range for the pod. The ratio is Usage/[Total number of IPs in the secondary range], Usage=numNodes&#42;numZones&#42;podIPsPerNode. */
		defaultPodIpv4RangeUtilization: Option[BigDecimal] = None
	)
	
	case class AdditionalPodRangesConfig(
	  /** Name for pod secondary ipv4 range which has the actual range defined ahead. */
		podRangeNames: Option[List[String]] = None,
	  /** Output only. Information for additional pod range. */
		podRangeInfo: Option[List[Schema.RangeInfo]] = None
	)
	
	case class RangeInfo(
	  /** Output only. Name of a range. */
		rangeName: Option[String] = None,
	  /** Output only. The utilization of the range. */
		utilization: Option[BigDecimal] = None
	)
	
	case class MasterAuthorizedNetworksConfig(
	  /** Whether or not master authorized networks is enabled. */
		enabled: Option[Boolean] = None,
	  /** cidr_blocks define up to 50 external networks that could access Kubernetes master through HTTPS. */
		cidrBlocks: Option[List[Schema.CidrBlock]] = None,
	  /** Whether master is accessbile via Google Compute Engine Public IP addresses. */
		gcpPublicCidrsAccessEnabled: Option[Boolean] = None,
	  /** Whether master authorized networks is enforced on private endpoint or not. */
		privateEndpointEnforcementEnabled: Option[Boolean] = None
	)
	
	case class CidrBlock(
	  /** display_name is an optional field for users to identify CIDR blocks. */
		displayName: Option[String] = None,
	  /** cidr_block must be specified in CIDR notation. */
		cidrBlock: Option[String] = None
	)
	
	case class MaintenancePolicy(
	  /** Specifies the maintenance window in which maintenance may be performed. */
		window: Option[Schema.MaintenanceWindow] = None,
	  /** A hash identifying the version of this policy, so that updates to fields of the policy won't accidentally undo intermediate changes (and so that users of the API unaware of some fields won't accidentally remove other fields). Make a `get()` request to the cluster to get the current resource version and include it with requests to set the policy. */
		resourceVersion: Option[String] = None
	)
	
	case class MaintenanceWindow(
	  /** DailyMaintenanceWindow specifies a daily maintenance operation window. */
		dailyMaintenanceWindow: Option[Schema.DailyMaintenanceWindow] = None,
	  /** RecurringWindow specifies some number of recurring time periods for maintenance to occur. The time windows may be overlapping. If no maintenance windows are set, maintenance can occur at any time. */
		recurringWindow: Option[Schema.RecurringTimeWindow] = None,
	  /** Exceptions to maintenance window. Non-emergency maintenance should not occur in these windows. */
		maintenanceExclusions: Option[Map[String, Schema.TimeWindow]] = None
	)
	
	case class DailyMaintenanceWindow(
	  /** Time within the maintenance window to start the maintenance operations. Time format should be in [RFC3339](https://www.ietf.org/rfc/rfc3339.txt) format "HH:MM", where HH : [00-23] and MM : [00-59] GMT. */
		startTime: Option[String] = None,
	  /** Output only. Duration of the time window, automatically chosen to be smallest possible in the given scenario. Duration will be in [RFC3339](https://www.ietf.org/rfc/rfc3339.txt) format "PTnHnMnS". */
		duration: Option[String] = None
	)
	
	case class RecurringTimeWindow(
	  /** The window of the first recurrence. */
		window: Option[Schema.TimeWindow] = None,
	  /** An RRULE (https://tools.ietf.org/html/rfc5545#section-3.8.5.3) for how this window reccurs. They go on for the span of time between the start and end time. For example, to have something repeat every weekday, you'd use: `FREQ=WEEKLY;BYDAY=MO,TU,WE,TH,FR` To repeat some window daily (equivalent to the DailyMaintenanceWindow): `FREQ=DAILY` For the first weekend of every month: `FREQ=MONTHLY;BYSETPOS=1;BYDAY=SA,SU` This specifies how frequently the window starts. Eg, if you wanted to have a 9-5 UTC-4 window every weekday, you'd use something like: ``` start time = 2019-01-01T09:00:00-0400 end time = 2019-01-01T17:00:00-0400 recurrence = FREQ=WEEKLY;BYDAY=MO,TU,WE,TH,FR ``` Windows can span multiple days. Eg, to make the window encompass every weekend from midnight Saturday till the last minute of Sunday UTC: ``` start time = 2019-01-05T00:00:00Z end time = 2019-01-07T23:59:00Z recurrence = FREQ=WEEKLY;BYDAY=SA ``` Note the start and end time's specific dates are largely arbitrary except to specify duration of the window and when it first starts. The FREQ values of HOURLY, MINUTELY, and SECONDLY are not supported. */
		recurrence: Option[String] = None
	)
	
	case class TimeWindow(
	  /** MaintenanceExclusionOptions provides maintenance exclusion related options. */
		maintenanceExclusionOptions: Option[Schema.MaintenanceExclusionOptions] = None,
	  /** The time that the window first starts. */
		startTime: Option[String] = None,
	  /** The time that the window ends. The end time should take place after the start time. */
		endTime: Option[String] = None
	)
	
	object MaintenanceExclusionOptions {
		enum ScopeEnum extends Enum[ScopeEnum] { case NO_UPGRADES, NO_MINOR_UPGRADES, NO_MINOR_OR_NODE_UPGRADES }
	}
	case class MaintenanceExclusionOptions(
	  /** Scope specifies the upgrade scope which upgrades are blocked by the exclusion. */
		scope: Option[Schema.MaintenanceExclusionOptions.ScopeEnum] = None
	)
	
	object BinaryAuthorization {
		enum EvaluationModeEnum extends Enum[EvaluationModeEnum] { case EVALUATION_MODE_UNSPECIFIED, DISABLED, PROJECT_SINGLETON_POLICY_ENFORCE }
	}
	case class BinaryAuthorization(
	  /** This field is deprecated. Leave this unset and instead configure BinaryAuthorization using evaluation_mode. If evaluation_mode is set to anything other than EVALUATION_MODE_UNSPECIFIED, this field is ignored. */
		enabled: Option[Boolean] = None,
	  /** Mode of operation for binauthz policy evaluation. If unspecified, defaults to DISABLED. */
		evaluationMode: Option[Schema.BinaryAuthorization.EvaluationModeEnum] = None
	)
	
	object ClusterAutoscaling {
		enum AutoscalingProfileEnum extends Enum[AutoscalingProfileEnum] { case PROFILE_UNSPECIFIED, OPTIMIZE_UTILIZATION, BALANCED }
	}
	case class ClusterAutoscaling(
	  /** Enables automatic node pool creation and deletion. */
		enableNodeAutoprovisioning: Option[Boolean] = None,
	  /** Contains global constraints regarding minimum and maximum amount of resources in the cluster. */
		resourceLimits: Option[List[Schema.ResourceLimit]] = None,
	  /** Defines autoscaling behaviour. */
		autoscalingProfile: Option[Schema.ClusterAutoscaling.AutoscalingProfileEnum] = None,
	  /** AutoprovisioningNodePoolDefaults contains defaults for a node pool created by NAP. */
		autoprovisioningNodePoolDefaults: Option[Schema.AutoprovisioningNodePoolDefaults] = None,
	  /** The list of Google Compute Engine [zones](https://cloud.google.com/compute/docs/zones#available) in which the NodePool's nodes can be created by NAP. */
		autoprovisioningLocations: Option[List[String]] = None
	)
	
	case class ResourceLimit(
	  /** Resource name "cpu", "memory" or gpu-specific string. */
		resourceType: Option[String] = None,
	  /** Minimum amount of the resource in the cluster. */
		minimum: Option[String] = None,
	  /** Maximum amount of the resource in the cluster. */
		maximum: Option[String] = None
	)
	
	case class AutoprovisioningNodePoolDefaults(
	  /** Scopes that are used by NAP when creating node pools. */
		oauthScopes: Option[List[String]] = None,
	  /** The Google Cloud Platform Service Account to be used by the node VMs. */
		serviceAccount: Option[String] = None,
	  /** Specifies the upgrade settings for NAP created node pools */
		upgradeSettings: Option[Schema.UpgradeSettings] = None,
	  /** Specifies the node management options for NAP created node-pools. */
		management: Option[Schema.NodeManagement] = None,
	  /** Deprecated. Minimum CPU platform to be used for NAP created node pools. The instance may be scheduled on the specified or newer CPU platform. Applicable values are the friendly names of CPU platforms, such as minCpuPlatform: Intel Haswell or minCpuPlatform: Intel Sandy Bridge. For more information, read [how to specify min CPU platform](https://cloud.google.com/compute/docs/instances/specify-min-cpu-platform). This field is deprecated, min_cpu_platform should be specified using `cloud.google.com/requested-min-cpu-platform` label selector on the pod. To unset the min cpu platform field pass "automatic" as field value. */
		minCpuPlatform: Option[String] = None,
	  /** Size of the disk attached to each node, specified in GB. The smallest allowed disk size is 10GB. If unspecified, the default disk size is 100GB. */
		diskSizeGb: Option[Int] = None,
	  /** Type of the disk attached to each node (e.g. 'pd-standard', 'pd-ssd' or 'pd-balanced') If unspecified, the default disk type is 'pd-standard' */
		diskType: Option[String] = None,
	  /** Shielded Instance options. */
		shieldedInstanceConfig: Option[Schema.ShieldedInstanceConfig] = None,
	  /** The Customer Managed Encryption Key used to encrypt the boot disk attached to each node in the node pool. This should be of the form projects/[KEY_PROJECT_ID]/locations/[LOCATION]/keyRings/[RING_NAME]/cryptoKeys/[KEY_NAME]. For more information about protecting resources with Cloud KMS Keys please see: https://cloud.google.com/compute/docs/disks/customer-managed-encryption */
		bootDiskKmsKey: Option[String] = None,
	  /** The image type to use for NAP created node. Please see https://cloud.google.com/kubernetes-engine/docs/concepts/node-images for available image types. */
		imageType: Option[String] = None,
	  /** Enable or disable Kubelet read only port. */
		insecureKubeletReadonlyPortEnabled: Option[Boolean] = None
	)
	
	object NetworkConfig {
		enum DatapathProviderEnum extends Enum[DatapathProviderEnum] { case DATAPATH_PROVIDER_UNSPECIFIED, LEGACY_DATAPATH, ADVANCED_DATAPATH }
		enum PrivateIpv6GoogleAccessEnum extends Enum[PrivateIpv6GoogleAccessEnum] { case PRIVATE_IPV6_GOOGLE_ACCESS_UNSPECIFIED, PRIVATE_IPV6_GOOGLE_ACCESS_DISABLED, PRIVATE_IPV6_GOOGLE_ACCESS_TO_GOOGLE, PRIVATE_IPV6_GOOGLE_ACCESS_BIDIRECTIONAL }
		enum InTransitEncryptionConfigEnum extends Enum[InTransitEncryptionConfigEnum] { case IN_TRANSIT_ENCRYPTION_CONFIG_UNSPECIFIED, IN_TRANSIT_ENCRYPTION_DISABLED, IN_TRANSIT_ENCRYPTION_INTER_NODE_TRANSPARENT }
	}
	case class NetworkConfig(
	  /** Output only. The relative name of the Google Compute Engine network(https://cloud.google.com/compute/docs/networks-and-firewalls#networks) to which the cluster is connected. Example: projects/my-project/global/networks/my-network */
		network: Option[String] = None,
	  /** Output only. The relative name of the Google Compute Engine [subnetwork](https://cloud.google.com/compute/docs/vpc) to which the cluster is connected. Example: projects/my-project/regions/us-central1/subnetworks/my-subnet */
		subnetwork: Option[String] = None,
	  /** Whether Intra-node visibility is enabled for this cluster. This makes same node pod to pod traffic visible for VPC network. */
		enableIntraNodeVisibility: Option[Boolean] = None,
	  /** Whether the cluster disables default in-node sNAT rules. In-node sNAT rules will be disabled when default_snat_status is disabled. When disabled is set to false, default IP masquerade rules will be applied to the nodes to prevent sNAT on cluster internal traffic. */
		defaultSnatStatus: Option[Schema.DefaultSnatStatus] = None,
	  /** Whether L4ILB Subsetting is enabled for this cluster. */
		enableL4ilbSubsetting: Option[Boolean] = None,
	  /** The desired datapath provider for this cluster. By default, uses the IPTables-based kube-proxy implementation. */
		datapathProvider: Option[Schema.NetworkConfig.DatapathProviderEnum] = None,
	  /** The desired state of IPv6 connectivity to Google Services. By default, no private IPv6 access to or from Google Services (all access will be via IPv4) */
		privateIpv6GoogleAccess: Option[Schema.NetworkConfig.PrivateIpv6GoogleAccessEnum] = None,
	  /** DNSConfig contains clusterDNS config for this cluster. */
		dnsConfig: Option[Schema.DNSConfig] = None,
	  /** ServiceExternalIPsConfig specifies if services with externalIPs field are blocked or not. */
		serviceExternalIpsConfig: Option[Schema.ServiceExternalIPsConfig] = None,
	  /** GatewayAPIConfig contains the desired config of Gateway API on this cluster. */
		gatewayApiConfig: Option[Schema.GatewayAPIConfig] = None,
	  /** Whether multi-networking is enabled for this cluster. */
		enableMultiNetworking: Option[Boolean] = None,
	  /** Network bandwidth tier configuration. */
		networkPerformanceConfig: Option[Schema.ClusterNetworkPerformanceConfig] = None,
	  /** Whether FQDN Network Policy is enabled on this cluster. */
		enableFqdnNetworkPolicy: Option[Boolean] = None,
	  /** Specify the details of in-transit encryption. Now named inter-node transparent encryption. */
		inTransitEncryptionConfig: Option[Schema.NetworkConfig.InTransitEncryptionConfigEnum] = None,
	  /** Whether CiliumClusterwideNetworkPolicy is enabled on this cluster. */
		enableCiliumClusterwideNetworkPolicy: Option[Boolean] = None,
	  /** Controls whether by default nodes have private IP addresses only. It is invalid to specify both PrivateClusterConfig.enablePrivateNodes and this field at the same time. To update the default setting, use ClusterUpdate.desired_default_enable_private_nodes */
		defaultEnablePrivateNodes: Option[Boolean] = None
	)
	
	case class DefaultSnatStatus(
	  /** Disables cluster default sNAT rules. */
		disabled: Option[Boolean] = None
	)
	
	object DNSConfig {
		enum ClusterDnsEnum extends Enum[ClusterDnsEnum] { case PROVIDER_UNSPECIFIED, PLATFORM_DEFAULT, CLOUD_DNS, KUBE_DNS }
		enum ClusterDnsScopeEnum extends Enum[ClusterDnsScopeEnum] { case DNS_SCOPE_UNSPECIFIED, CLUSTER_SCOPE, VPC_SCOPE }
	}
	case class DNSConfig(
	  /** cluster_dns indicates which in-cluster DNS provider should be used. */
		clusterDns: Option[Schema.DNSConfig.ClusterDnsEnum] = None,
	  /** cluster_dns_scope indicates the scope of access to cluster DNS records. */
		clusterDnsScope: Option[Schema.DNSConfig.ClusterDnsScopeEnum] = None,
	  /** cluster_dns_domain is the suffix used for all cluster service records. */
		clusterDnsDomain: Option[String] = None,
	  /** Optional. The domain used in Additive VPC scope. */
		additiveVpcScopeDnsDomain: Option[String] = None
	)
	
	case class ServiceExternalIPsConfig(
	  /** Whether Services with ExternalIPs field are allowed or not. */
		enabled: Option[Boolean] = None
	)
	
	object GatewayAPIConfig {
		enum ChannelEnum extends Enum[ChannelEnum] { case CHANNEL_UNSPECIFIED, CHANNEL_DISABLED, CHANNEL_EXPERIMENTAL, CHANNEL_STANDARD }
	}
	case class GatewayAPIConfig(
	  /** The Gateway API release channel to use for Gateway API. */
		channel: Option[Schema.GatewayAPIConfig.ChannelEnum] = None
	)
	
	object ClusterNetworkPerformanceConfig {
		enum TotalEgressBandwidthTierEnum extends Enum[TotalEgressBandwidthTierEnum] { case TIER_UNSPECIFIED, TIER_1 }
	}
	case class ClusterNetworkPerformanceConfig(
	  /** Specifies the total network bandwidth tier for NodePools in the cluster. */
		totalEgressBandwidthTier: Option[Schema.ClusterNetworkPerformanceConfig.TotalEgressBandwidthTierEnum] = None
	)
	
	case class ResourceUsageExportConfig(
	  /** Configuration to use BigQuery as usage export destination. */
		bigqueryDestination: Option[Schema.BigQueryDestination] = None,
	  /** Whether to enable network egress metering for this cluster. If enabled, a daemonset will be created in the cluster to meter network egress traffic. */
		enableNetworkEgressMetering: Option[Boolean] = None,
	  /** Configuration to enable resource consumption metering. */
		consumptionMeteringConfig: Option[Schema.ConsumptionMeteringConfig] = None
	)
	
	case class BigQueryDestination(
	  /** The ID of a BigQuery Dataset. */
		datasetId: Option[String] = None
	)
	
	case class ConsumptionMeteringConfig(
	  /** Whether to enable consumption metering for this cluster. If enabled, a second BigQuery table will be created to hold resource consumption records. */
		enabled: Option[Boolean] = None
	)
	
	case class AuthenticatorGroupsConfig(
	  /** Whether this cluster should return group membership lookups during authentication using a group of security groups. */
		enabled: Option[Boolean] = None,
	  /** The name of the security group-of-groups to be used. Only relevant if enabled = true. */
		securityGroup: Option[String] = None
	)
	
	case class PrivateClusterConfig(
	  /** Whether nodes have internal IP addresses only. If enabled, all nodes are given only RFC 1918 private addresses and communicate with the master via private networking. Deprecated: Use NetworkConfig.default_enable_private_nodes instead. */
		enablePrivateNodes: Option[Boolean] = None,
	  /** Whether the master's internal IP address is used as the cluster endpoint. Deprecated: Use ControlPlaneEndpointsConfig.IPEndpointsConfig.enable_public_endpoint instead. Note that the value of enable_public_endpoint is reversed: if enable_private_endpoint is false, then enable_public_endpoint will be true. */
		enablePrivateEndpoint: Option[Boolean] = None,
	  /** The IP range in CIDR notation to use for the hosted master network. This range will be used for assigning internal IP addresses to the master or set of masters, as well as the ILB VIP. This range must not overlap with any other ranges in use within the cluster's network. */
		masterIpv4CidrBlock: Option[String] = None,
	  /** Output only. The internal IP address of this cluster's master endpoint. Deprecated: Use ControlPlaneEndpointsConfig.IPEndpointsConfig.private_endpoint instead. */
		privateEndpoint: Option[String] = None,
	  /** Output only. The external IP address of this cluster's master endpoint. Deprecated:Use ControlPlaneEndpointsConfig.IPEndpointsConfig.public_endpoint instead. */
		publicEndpoint: Option[String] = None,
	  /** Output only. The peering name in the customer VPC used by this cluster. */
		peeringName: Option[String] = None,
	  /** Controls master global access settings. Deprecated: Use ControlPlaneEndpointsConfig.IPEndpointsConfig.enable_global_access instead. */
		masterGlobalAccessConfig: Option[Schema.PrivateClusterMasterGlobalAccessConfig] = None,
	  /** Subnet to provision the master's private endpoint during cluster creation. Specified in projects/&#42;/regions/&#42;/subnetworks/&#42; format. Deprecated: Use ControlPlaneEndpointsConfig.IPEndpointsConfig.private_endpoint_subnetwork instead. */
		privateEndpointSubnetwork: Option[String] = None
	)
	
	case class PrivateClusterMasterGlobalAccessConfig(
	  /** Whenever master is accessible globally or not. */
		enabled: Option[Boolean] = None
	)
	
	object DatabaseEncryption {
		enum StateEnum extends Enum[StateEnum] { case UNKNOWN, ENCRYPTED, DECRYPTED }
		enum CurrentStateEnum extends Enum[CurrentStateEnum] { case CURRENT_STATE_UNSPECIFIED, CURRENT_STATE_ENCRYPTED, CURRENT_STATE_DECRYPTED, CURRENT_STATE_ENCRYPTION_PENDING, CURRENT_STATE_ENCRYPTION_ERROR, CURRENT_STATE_DECRYPTION_PENDING, CURRENT_STATE_DECRYPTION_ERROR }
	}
	case class DatabaseEncryption(
	  /** Name of CloudKMS key to use for the encryption of secrets in etcd. Ex. projects/my-project/locations/global/keyRings/my-ring/cryptoKeys/my-key */
		keyName: Option[String] = None,
	  /** The desired state of etcd encryption. */
		state: Option[Schema.DatabaseEncryption.StateEnum] = None,
	  /** Output only. The current state of etcd encryption. */
		currentState: Option[Schema.DatabaseEncryption.CurrentStateEnum] = None,
	  /** Output only. Keys in use by the cluster for decrypting existing objects, in addition to the key in `key_name`. Each item is a CloudKMS key resource. */
		decryptionKeys: Option[List[String]] = None,
	  /** Output only. Records errors seen during DatabaseEncryption update operations. */
		lastOperationErrors: Option[List[Schema.OperationError]] = None
	)
	
	case class OperationError(
	  /** CloudKMS key resource that had the error. */
		keyName: Option[String] = None,
	  /** Description of the error seen during the operation. */
		errorMessage: Option[String] = None,
	  /** Time when the CloudKMS error was seen. */
		timestamp: Option[String] = None
	)
	
	case class VerticalPodAutoscaling(
	  /** Enables vertical pod autoscaling. */
		enabled: Option[Boolean] = None
	)
	
	case class ShieldedNodes(
	  /** Whether Shielded Nodes features are enabled on all nodes in this cluster. */
		enabled: Option[Boolean] = None
	)
	
	object ReleaseChannel {
		enum ChannelEnum extends Enum[ChannelEnum] { case UNSPECIFIED, RAPID, REGULAR, STABLE, EXTENDED }
	}
	case class ReleaseChannel(
	  /** channel specifies which release channel the cluster is subscribed to. */
		channel: Option[Schema.ReleaseChannel.ChannelEnum] = None
	)
	
	case class WorkloadIdentityConfig(
	  /** The workload pool to attach all Kubernetes service accounts to. */
		workloadPool: Option[String] = None
	)
	
	case class MeshCertificates(
	  /** enable_certificates controls issuance of workload mTLS certificates. If set, the GKE Workload Identity Certificates controller and node agent will be deployed in the cluster, which can then be configured by creating a WorkloadCertificateConfig Custom Resource. Requires Workload Identity (workload_pool must be non-empty). */
		enableCertificates: Option[Boolean] = None
	)
	
	case class CostManagementConfig(
	  /** Whether the feature is enabled or not. */
		enabled: Option[Boolean] = None
	)
	
	case class NotificationConfig(
	  /** Notification config for Pub/Sub. */
		pubsub: Option[Schema.PubSub] = None
	)
	
	case class PubSub(
	  /** Enable notifications for Pub/Sub. */
		enabled: Option[Boolean] = None,
	  /** The desired Pub/Sub topic to which notifications will be sent by GKE. Format is `projects/{project}/topics/{topic}`. */
		topic: Option[String] = None,
	  /** Allows filtering to one or more specific event types. If no filter is specified, or if a filter is specified with no event types, all event types will be sent */
		filter: Option[Schema.Filter] = None
	)
	
	object Filter {
		enum EventTypeEnum extends Enum[EventTypeEnum] { case EVENT_TYPE_UNSPECIFIED, UPGRADE_AVAILABLE_EVENT, UPGRADE_EVENT, SECURITY_BULLETIN_EVENT }
	}
	case class Filter(
	  /** Event types to allowlist. */
		eventType: Option[List[Schema.Filter.EventTypeEnum]] = None
	)
	
	case class IdentityServiceConfig(
	  /** Whether to enable the Identity Service component */
		enabled: Option[Boolean] = None
	)
	
	case class Autopilot(
	  /** Enable Autopilot */
		enabled: Option[Boolean] = None,
	  /** Workload policy configuration for Autopilot. */
		workloadPolicyConfig: Option[Schema.WorkloadPolicyConfig] = None
	)
	
	case class WorkloadPolicyConfig(
	  /** If true, workloads can use NET_ADMIN capability. */
		allowNetAdmin: Option[Boolean] = None
	)
	
	case class ParentProductConfig(
	  /** Name of the parent product associated with the cluster. */
		productName: Option[String] = None,
	  /** Labels contain the configuration of the parent product. */
		labels: Option[Map[String, String]] = None
	)
	
	case class NodePoolDefaults(
	  /** Subset of NodeConfig message that has defaults. */
		nodeConfigDefaults: Option[Schema.NodeConfigDefaults] = None
	)
	
	case class NodeConfigDefaults(
	  /** GCFS (Google Container File System, also known as Riptide) options. */
		gcfsConfig: Option[Schema.GcfsConfig] = None,
	  /** Logging configuration for node pools. */
		loggingConfig: Option[Schema.NodePoolLoggingConfig] = None,
	  /** Parameters for containerd customization. */
		containerdConfig: Option[Schema.ContainerdConfig] = None,
	  /** NodeKubeletConfig controls the defaults for new node-pools. Currently only `insecure_kubelet_readonly_port_enabled` can be set here. */
		nodeKubeletConfig: Option[Schema.NodeKubeletConfig] = None
	)
	
	case class LoggingConfig(
	  /** Logging components configuration */
		componentConfig: Option[Schema.LoggingComponentConfig] = None
	)
	
	object LoggingComponentConfig {
		enum EnableComponentsEnum extends Enum[EnableComponentsEnum] { case COMPONENT_UNSPECIFIED, SYSTEM_COMPONENTS, WORKLOADS, APISERVER, SCHEDULER, CONTROLLER_MANAGER, KCP_SSHD, KCP_CONNECTION }
	}
	case class LoggingComponentConfig(
	  /** Select components to collect logs. An empty set would disable all logging. */
		enableComponents: Option[List[Schema.LoggingComponentConfig.EnableComponentsEnum]] = None
	)
	
	case class MonitoringConfig(
	  /** Monitoring components configuration */
		componentConfig: Option[Schema.MonitoringComponentConfig] = None,
	  /** Enable Google Cloud Managed Service for Prometheus in the cluster. */
		managedPrometheusConfig: Option[Schema.ManagedPrometheusConfig] = None,
	  /** Configuration of Advanced Datapath Observability features. */
		advancedDatapathObservabilityConfig: Option[Schema.AdvancedDatapathObservabilityConfig] = None
	)
	
	object MonitoringComponentConfig {
		enum EnableComponentsEnum extends Enum[EnableComponentsEnum] { case COMPONENT_UNSPECIFIED, SYSTEM_COMPONENTS, APISERVER, SCHEDULER, CONTROLLER_MANAGER, STORAGE, HPA, POD, DAEMONSET, DEPLOYMENT, STATEFULSET, CADVISOR, KUBELET, DCGM }
	}
	case class MonitoringComponentConfig(
	  /** Select components to collect metrics. An empty set would disable all monitoring. */
		enableComponents: Option[List[Schema.MonitoringComponentConfig.EnableComponentsEnum]] = None
	)
	
	case class ManagedPrometheusConfig(
	  /** Enable Managed Collection. */
		enabled: Option[Boolean] = None
	)
	
	object AdvancedDatapathObservabilityConfig {
		enum RelayModeEnum extends Enum[RelayModeEnum] { case RELAY_MODE_UNSPECIFIED, DISABLED, INTERNAL_VPC_LB, EXTERNAL_LB }
	}
	case class AdvancedDatapathObservabilityConfig(
	  /** Expose flow metrics on nodes */
		enableMetrics: Option[Boolean] = None,
	  /** Method used to make Relay available */
		relayMode: Option[Schema.AdvancedDatapathObservabilityConfig.RelayModeEnum] = None,
	  /** Enable Relay component */
		enableRelay: Option[Boolean] = None
	)
	
	case class NodePoolAutoConfig(
	  /** The list of instance tags applied to all nodes. Tags are used to identify valid sources or targets for network firewalls and are specified by the client during cluster creation. Each tag within the list must comply with RFC1035. */
		networkTags: Option[Schema.NetworkTags] = None,
	  /** Resource manager tag keys and values to be attached to the nodes for managing Compute Engine firewalls using Network Firewall Policies. */
		resourceManagerTags: Option[Schema.ResourceManagerTags] = None,
	  /** NodeKubeletConfig controls the defaults for autoprovisioned node-pools. Currently only `insecure_kubelet_readonly_port_enabled` can be set here. */
		nodeKubeletConfig: Option[Schema.NodeKubeletConfig] = None,
	  /** Output only. Configuration options for Linux nodes. */
		linuxNodeConfig: Option[Schema.LinuxNodeConfig] = None
	)
	
	case class NetworkTags(
	  /** List of network tags. */
		tags: Option[List[String]] = None
	)
	
	case class Fleet(
	  /** The Fleet host project(project ID or project number) where this cluster will be registered to. This field cannot be changed after the cluster has been registered. */
		project: Option[String] = None,
	  /** Output only. The full resource name of the registered fleet membership of the cluster, in the format `//gkehub.googleapis.com/projects/&#42;/locations/&#42;/memberships/&#42;`. */
		membership: Option[String] = None,
	  /** Output only. Whether the cluster has been registered through the fleet API. */
		preRegistered: Option[Boolean] = None
	)
	
	object SecurityPostureConfig {
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, DISABLED, BASIC, ENTERPRISE }
		enum VulnerabilityModeEnum extends Enum[VulnerabilityModeEnum] { case VULNERABILITY_MODE_UNSPECIFIED, VULNERABILITY_DISABLED, VULNERABILITY_BASIC, VULNERABILITY_ENTERPRISE }
	}
	case class SecurityPostureConfig(
	  /** Sets which mode to use for Security Posture features. */
		mode: Option[Schema.SecurityPostureConfig.ModeEnum] = None,
	  /** Sets which mode to use for vulnerability scanning. */
		vulnerabilityMode: Option[Schema.SecurityPostureConfig.VulnerabilityModeEnum] = None
	)
	
	case class ControlPlaneEndpointsConfig(
	  /** DNS endpoint configuration. */
		dnsEndpointConfig: Option[Schema.DNSEndpointConfig] = None,
	  /** IP endpoints configuration. */
		ipEndpointsConfig: Option[Schema.IPEndpointsConfig] = None
	)
	
	case class DNSEndpointConfig(
	  /** Output only. The cluster's DNS endpoint configuration. A DNS format address. This is accessible from the public internet. Ex: uid.us-central1.gke.goog. Always present, but the behavior may change according to the value of DNSEndpointConfig.allow_external_traffic. */
		endpoint: Option[String] = None,
	  /** Controls whether user traffic is allowed over this endpoint. Note that GCP-managed services may still use the endpoint even if this is false. */
		allowExternalTraffic: Option[Boolean] = None
	)
	
	case class IPEndpointsConfig(
	  /** Controls whether to allow direct IP access. */
		enabled: Option[Boolean] = None,
	  /** Controls whether the control plane allows access through a public IP. It is invalid to specify both PrivateClusterConfig.enablePrivateEndpoint and this field at the same time. */
		enablePublicEndpoint: Option[Boolean] = None,
	  /** Controls whether the control plane's private endpoint is accessible from sources in other regions. It is invalid to specify both PrivateClusterMasterGlobalAccessConfig.enabled and this field at the same time. */
		globalAccess: Option[Boolean] = None,
	  /** Configuration of authorized networks. If enabled, restricts access to the control plane based on source IP. It is invalid to specify both Cluster.masterAuthorizedNetworksConfig and this field at the same time. */
		authorizedNetworksConfig: Option[Schema.MasterAuthorizedNetworksConfig] = None,
	  /** Output only. The external IP address of this cluster's control plane. Only populated if enabled. */
		publicEndpoint: Option[String] = None,
	  /** Output only. The internal IP address of this cluster's control plane. Only populated if enabled. */
		privateEndpoint: Option[String] = None,
	  /** Subnet to provision the master's private endpoint during cluster creation. Specified in projects/&#42;/regions/&#42;/subnetworks/&#42; format. It is invalid to specify both PrivateClusterConfig.privateEndpointSubnetwork and this field at the same time. */
		privateEndpointSubnetwork: Option[String] = None
	)
	
	case class K8sBetaAPIConfig(
	  /** Enabled k8s beta APIs. */
		enabledApis: Option[List[String]] = None
	)
	
	object EnterpriseConfig {
		enum ClusterTierEnum extends Enum[ClusterTierEnum] { case CLUSTER_TIER_UNSPECIFIED, STANDARD, ENTERPRISE }
		enum DesiredTierEnum extends Enum[DesiredTierEnum] { case CLUSTER_TIER_UNSPECIFIED, STANDARD, ENTERPRISE }
	}
	case class EnterpriseConfig(
	  /** Output only. cluster_tier indicates the effective tier of the cluster. */
		clusterTier: Option[Schema.EnterpriseConfig.ClusterTierEnum] = None,
	  /** desired_tier specifies the desired tier of the cluster. */
		desiredTier: Option[Schema.EnterpriseConfig.DesiredTierEnum] = None
	)
	
	case class SecretManagerConfig(
	  /** Enable/Disable Secret Manager Config. */
		enabled: Option[Boolean] = None
	)
	
	object CompliancePostureConfig {
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, DISABLED, ENABLED }
	}
	case class CompliancePostureConfig(
	  /** Defines the enablement mode for Compliance Posture. */
		mode: Option[Schema.CompliancePostureConfig.ModeEnum] = None,
	  /** List of enabled compliance standards. */
		complianceStandards: Option[List[Schema.ComplianceStandard]] = None
	)
	
	case class ComplianceStandard(
	  /** Name of the compliance standard. */
		standard: Option[String] = None
	)
	
	case class UserManagedKeysConfig(
	  /** The Certificate Authority Service caPool to use for the cluster CA in this cluster. */
		clusterCa: Option[String] = None,
	  /** Resource path of the Certificate Authority Service caPool to use for the etcd API CA in this cluster. */
		etcdApiCa: Option[String] = None,
	  /** Resource path of the Certificate Authority Service caPool to use for the etcd peer CA in this cluster. */
		etcdPeerCa: Option[String] = None,
	  /** The Cloud KMS cryptoKeyVersions to use for signing service account JWTs issued by this cluster. Format: `projects/{project}/locations/{location}/keyRings/{keyring}/cryptoKeys/{cryptoKey}/cryptoKeyVersions/{cryptoKeyVersion}` */
		serviceAccountSigningKeys: Option[List[String]] = None,
	  /** The Cloud KMS cryptoKeyVersions to use for verifying service account JWTs issued by this cluster. Format: `projects/{project}/locations/{location}/keyRings/{keyring}/cryptoKeys/{cryptoKey}/cryptoKeyVersions/{cryptoKeyVersion}` */
		serviceAccountVerificationKeys: Option[List[String]] = None,
	  /** The Certificate Authority Service caPool to use for the aggregation CA in this cluster. */
		aggregationCa: Option[String] = None,
	  /** The Cloud KMS cryptoKey to use for Confidential Hyperdisk on the control plane nodes. */
		controlPlaneDiskEncryptionKey: Option[String] = None,
	  /** Resource path of the Cloud KMS cryptoKey to use for encryption of internal etcd backups. */
		gkeopsEtcdBackupEncryptionKey: Option[String] = None
	)
	
	case class RBACBindingConfig(
	  /** Setting this to true will allow any ClusterRoleBinding and RoleBinding with subjets system:anonymous or system:unauthenticated. */
		enableInsecureBindingSystemUnauthenticated: Option[Boolean] = None,
	  /** Setting this to true will allow any ClusterRoleBinding and RoleBinding with subjects system:authenticated. */
		enableInsecureBindingSystemAuthenticated: Option[Boolean] = None
	)
	
	case class CreateClusterRequest(
	  /** Deprecated. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). This field has been deprecated and replaced by the parent field. */
		projectId: Option[String] = None,
	  /** Deprecated. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. This field has been deprecated and replaced by the parent field. */
		zone: Option[String] = None,
	  /** Required. A [cluster resource](https://cloud.google.com/container-engine/reference/rest/v1/projects.locations.clusters) */
		cluster: Option[Schema.Cluster] = None,
	  /** The parent (project and location) where the cluster will be created. Specified in the format `projects/&#42;/locations/&#42;`. */
		parent: Option[String] = None
	)
	
	object Operation {
		enum OperationTypeEnum extends Enum[OperationTypeEnum] { case TYPE_UNSPECIFIED, CREATE_CLUSTER, DELETE_CLUSTER, UPGRADE_MASTER, UPGRADE_NODES, REPAIR_CLUSTER, UPDATE_CLUSTER, CREATE_NODE_POOL, DELETE_NODE_POOL, SET_NODE_POOL_MANAGEMENT, AUTO_REPAIR_NODES, AUTO_UPGRADE_NODES, SET_LABELS, SET_MASTER_AUTH, SET_NODE_POOL_SIZE, SET_NETWORK_POLICY, SET_MAINTENANCE_POLICY, RESIZE_CLUSTER, FLEET_FEATURE_UPGRADE }
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNSPECIFIED, PENDING, RUNNING, DONE, ABORTING }
	}
	case class Operation(
	  /** Output only. The server-assigned ID for the operation. */
		name: Option[String] = None,
	  /** Output only. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the operation is taking place. This field is deprecated, use location instead. */
		zone: Option[String] = None,
	  /** Output only. The operation type. */
		operationType: Option[Schema.Operation.OperationTypeEnum] = None,
	  /** Output only. The current status of the operation. */
		status: Option[Schema.Operation.StatusEnum] = None,
	  /** Output only. Detailed operation progress, if available. */
		detail: Option[String] = None,
	  /** Output only. If an error has occurred, a textual description of the error. Deprecated. Use the field error instead. */
		statusMessage: Option[String] = None,
	  /** Output only. Server-defined URI for the operation. Example: `https://container.googleapis.com/v1alpha1/projects/123/locations/us-central1/operations/operation-123`. */
		selfLink: Option[String] = None,
	  /** Output only. Server-defined URI for the target of the operation. The format of this is a URI to the resource being modified (such as a cluster, node pool, or node). For node pool repairs, there may be multiple nodes being repaired, but only one will be the target. Examples: - ## `https://container.googleapis.com/v1/projects/123/locations/us-central1/clusters/my-cluster` ## `https://container.googleapis.com/v1/projects/123/zones/us-central1-c/clusters/my-cluster/nodePools/my-np` `https://container.googleapis.com/v1/projects/123/zones/us-central1-c/clusters/my-cluster/nodePools/my-np/node/my-node` */
		targetLink: Option[String] = None,
	  /** Output only. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/regions-zones/regions-zones#available) or [region](https://cloud.google.com/compute/docs/regions-zones/regions-zones#available) in which the cluster resides. */
		location: Option[String] = None,
	  /** Output only. The time the operation started, in [RFC3339](https://www.ietf.org/rfc/rfc3339.txt) text format. */
		startTime: Option[String] = None,
	  /** Output only. The time the operation completed, in [RFC3339](https://www.ietf.org/rfc/rfc3339.txt) text format. */
		endTime: Option[String] = None,
	  /** Output only. Progress information for an operation. */
		progress: Option[Schema.OperationProgress] = None,
	  /** Which conditions caused the current cluster state. Deprecated. Use field error instead. */
		clusterConditions: Option[List[Schema.StatusCondition]] = None,
	  /** Which conditions caused the current node pool state. Deprecated. Use field error instead. */
		nodepoolConditions: Option[List[Schema.StatusCondition]] = None,
	  /** The error result of the operation in case of failure. */
		error: Option[Schema.Status] = None
	)
	
	object OperationProgress {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNSPECIFIED, PENDING, RUNNING, DONE, ABORTING }
	}
	case class OperationProgress(
	  /** A non-parameterized string describing an operation stage. Unset for single-stage operations. */
		name: Option[String] = None,
	  /** Status of an operation stage. Unset for single-stage operations. */
		status: Option[Schema.OperationProgress.StatusEnum] = None,
	  /** Progress metric bundle, for example: metrics: [{name: "nodes done", int_value: 15}, {name: "nodes total", int_value: 32}] or metrics: [{name: "progress", double_value: 0.56}, {name: "progress scale", double_value: 1.0}] */
		metrics: Option[List[Schema.Metric]] = None,
	  /** Substages of an operation or a stage. */
		stages: Option[List[Schema.OperationProgress]] = None
	)
	
	case class Metric(
	  /** Required. Metric name, e.g., "nodes total", "percent done". */
		name: Option[String] = None,
	  /** For metrics with integer value. */
		intValue: Option[String] = None,
	  /** For metrics with floating point value. */
		doubleValue: Option[BigDecimal] = None,
	  /** For metrics with custom values (ratios, visual progress, etc.). */
		stringValue: Option[String] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class UpdateClusterRequest(
	  /** Deprecated. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). This field has been deprecated and replaced by the name field. */
		projectId: Option[String] = None,
	  /** Deprecated. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. This field has been deprecated and replaced by the name field. */
		zone: Option[String] = None,
	  /** Deprecated. The name of the cluster to upgrade. This field has been deprecated and replaced by the name field. */
		clusterId: Option[String] = None,
	  /** Required. A description of the update. */
		update: Option[Schema.ClusterUpdate] = None,
	  /** The name (project, location, cluster) of the cluster to update. Specified in the format `projects/&#42;/locations/&#42;/clusters/&#42;`. */
		name: Option[String] = None
	)
	
	object ClusterUpdate {
		enum DesiredDatapathProviderEnum extends Enum[DesiredDatapathProviderEnum] { case DATAPATH_PROVIDER_UNSPECIFIED, LEGACY_DATAPATH, ADVANCED_DATAPATH }
		enum DesiredPrivateIpv6GoogleAccessEnum extends Enum[DesiredPrivateIpv6GoogleAccessEnum] { case PRIVATE_IPV6_GOOGLE_ACCESS_UNSPECIFIED, PRIVATE_IPV6_GOOGLE_ACCESS_DISABLED, PRIVATE_IPV6_GOOGLE_ACCESS_TO_GOOGLE, PRIVATE_IPV6_GOOGLE_ACCESS_BIDIRECTIONAL }
		enum DesiredStackTypeEnum extends Enum[DesiredStackTypeEnum] { case STACK_TYPE_UNSPECIFIED, IPV4, IPV4_IPV6 }
		enum DesiredInTransitEncryptionConfigEnum extends Enum[DesiredInTransitEncryptionConfigEnum] { case IN_TRANSIT_ENCRYPTION_CONFIG_UNSPECIFIED, IN_TRANSIT_ENCRYPTION_DISABLED, IN_TRANSIT_ENCRYPTION_INTER_NODE_TRANSPARENT }
	}
	case class ClusterUpdate(
	  /** The Kubernetes version to change the nodes to (typically an upgrade). Users may specify either explicit versions offered by Kubernetes Engine or version aliases, which have the following behavior: - "latest": picks the highest valid Kubernetes version - "1.X": picks the highest valid patch+gke.N patch in the 1.X version - "1.X.Y": picks the highest valid gke.N patch in the 1.X.Y version - "1.X.Y-gke.N": picks an explicit Kubernetes version - "-": picks the Kubernetes master version */
		desiredNodeVersion: Option[String] = None,
	  /** The monitoring service the cluster should use to write metrics. Currently available options: &#42; "monitoring.googleapis.com/kubernetes" - The Cloud Monitoring service with a Kubernetes-native resource model &#42; `monitoring.googleapis.com` - The legacy Cloud Monitoring service (no longer available as of GKE 1.15). &#42; `none` - No metrics will be exported from the cluster. If left as an empty string,`monitoring.googleapis.com/kubernetes` will be used for GKE 1.14+ or `monitoring.googleapis.com` for earlier versions. */
		desiredMonitoringService: Option[String] = None,
	  /** Configurations for the various addons available to run in the cluster. */
		desiredAddonsConfig: Option[Schema.AddonsConfig] = None,
	  /** The node pool to be upgraded. This field is mandatory if "desired_node_version", "desired_image_family" or "desired_node_pool_autoscaling" is specified and there is more than one node pool on the cluster. */
		desiredNodePoolId: Option[String] = None,
	  /** The desired image type for the node pool. NOTE: Set the "desired_node_pool" field as well. */
		desiredImageType: Option[String] = None,
	  /** Configuration of etcd encryption. */
		desiredDatabaseEncryption: Option[Schema.DatabaseEncryption] = None,
	  /** Configuration for Workload Identity. */
		desiredWorkloadIdentityConfig: Option[Schema.WorkloadIdentityConfig] = None,
	  /** Configuration for issuance of mTLS keys and certificates to Kubernetes pods. */
		desiredMeshCertificates: Option[Schema.MeshCertificates] = None,
	  /** Configuration for Shielded Nodes. */
		desiredShieldedNodes: Option[Schema.ShieldedNodes] = None,
	  /** The desired configuration for the fine-grained cost management feature. */
		desiredCostManagementConfig: Option[Schema.CostManagementConfig] = None,
	  /** DNSConfig contains clusterDNS config for this cluster. */
		desiredDnsConfig: Option[Schema.DNSConfig] = None,
	  /** Autoscaler configuration for the node pool specified in desired_node_pool_id. If there is only one pool in the cluster and desired_node_pool_id is not provided then the change applies to that single node pool. */
		desiredNodePoolAutoscaling: Option[Schema.NodePoolAutoscaling] = None,
	  /** The desired list of Google Compute Engine [zones](https://cloud.google.com/compute/docs/zones#available) in which the cluster's nodes should be located. This list must always include the cluster's primary zone. Warning: changing cluster locations will update the locations of all node pools and will result in nodes being added and/or removed. */
		desiredLocations: Option[List[String]] = None,
	  /** The desired configuration options for master authorized networks feature. Deprecated: Use desired_control_plane_endpoints_config.ip_endpoints_config.authorized_networks_config instead. */
		desiredMasterAuthorizedNetworksConfig: Option[Schema.MasterAuthorizedNetworksConfig] = None,
	  /** Cluster-level autoscaling configuration. */
		desiredClusterAutoscaling: Option[Schema.ClusterAutoscaling] = None,
	  /** The desired configuration options for the Binary Authorization feature. */
		desiredBinaryAuthorization: Option[Schema.BinaryAuthorization] = None,
	  /** The logging service the cluster should use to write logs. Currently available options: &#42; `logging.googleapis.com/kubernetes` - The Cloud Logging service with a Kubernetes-native resource model &#42; `logging.googleapis.com` - The legacy Cloud Logging service (no longer available as of GKE 1.15). &#42; `none` - no logs will be exported from the cluster. If left as an empty string,`logging.googleapis.com/kubernetes` will be used for GKE 1.14+ or `logging.googleapis.com` for earlier versions. */
		desiredLoggingService: Option[String] = None,
	  /** The desired configuration for exporting resource usage. */
		desiredResourceUsageExportConfig: Option[Schema.ResourceUsageExportConfig] = None,
	  /** Cluster-level Vertical Pod Autoscaling configuration. */
		desiredVerticalPodAutoscaling: Option[Schema.VerticalPodAutoscaling] = None,
	  /** The desired private cluster configuration. master_global_access_config is the only field that can be changed via this field. See also ClusterUpdate.desired_enable_private_endpoint for modifying other fields within PrivateClusterConfig. Deprecated: Use desired_control_plane_endpoints_config.ip_endpoints_config.global_access instead. */
		desiredPrivateClusterConfig: Option[Schema.PrivateClusterConfig] = None,
	  /** The desired config of Intra-node visibility. */
		desiredIntraNodeVisibilityConfig: Option[Schema.IntraNodeVisibilityConfig] = None,
	  /** The desired status of whether to disable default sNAT for this cluster. */
		desiredDefaultSnatStatus: Option[Schema.DefaultSnatStatus] = None,
	  /** The desired release channel configuration. */
		desiredReleaseChannel: Option[Schema.ReleaseChannel] = None,
	  /** The desired L4 Internal Load Balancer Subsetting configuration. */
		desiredL4ilbSubsettingConfig: Option[Schema.ILBSubsettingConfig] = None,
	  /** The desired datapath provider for the cluster. */
		desiredDatapathProvider: Option[Schema.ClusterUpdate.DesiredDatapathProviderEnum] = None,
	  /** The desired state of IPv6 connectivity to Google Services. */
		desiredPrivateIpv6GoogleAccess: Option[Schema.ClusterUpdate.DesiredPrivateIpv6GoogleAccessEnum] = None,
	  /** The desired notification configuration. */
		desiredNotificationConfig: Option[Schema.NotificationConfig] = None,
	  /** The desired authenticator groups config for the cluster. */
		desiredAuthenticatorGroupsConfig: Option[Schema.AuthenticatorGroupsConfig] = None,
	  /** The desired logging configuration. */
		desiredLoggingConfig: Option[Schema.LoggingConfig] = None,
	  /** The desired monitoring configuration. */
		desiredMonitoringConfig: Option[Schema.MonitoringConfig] = None,
	  /** The desired Identity Service component configuration. */
		desiredIdentityServiceConfig: Option[Schema.IdentityServiceConfig] = None,
	  /** ServiceExternalIPsConfig specifies the config for the use of Services with ExternalIPs field. */
		desiredServiceExternalIpsConfig: Option[Schema.ServiceExternalIPsConfig] = None,
	  /** Enable/Disable private endpoint for the cluster's master. Deprecated: Use desired_control_plane_endpoints_config.ip_endpoints_config.enable_public_endpoint instead. Note that the value of enable_public_endpoint is reversed: if enable_private_endpoint is false, then enable_public_endpoint will be true. */
		desiredEnablePrivateEndpoint: Option[Boolean] = None,
	  /** Override the default setting of whether future created nodes have private IP addresses only, namely NetworkConfig.default_enable_private_nodes */
		desiredDefaultEnablePrivateNodes: Option[Boolean] = None,
	  /** Control plane endpoints configuration. */
		desiredControlPlaneEndpointsConfig: Option[Schema.ControlPlaneEndpointsConfig] = None,
	  /** The Kubernetes version to change the master to. Users may specify either explicit versions offered by Kubernetes Engine or version aliases, which have the following behavior: - "latest": picks the highest valid Kubernetes version - "1.X": picks the highest valid patch+gke.N patch in the 1.X version - "1.X.Y": picks the highest valid gke.N patch in the 1.X.Y version - "1.X.Y-gke.N": picks an explicit Kubernetes version - "-": picks the default Kubernetes version */
		desiredMasterVersion: Option[String] = None,
	  /** The desired parent product config for the cluster. */
		desiredParentProductConfig: Option[Schema.ParentProductConfig] = None,
	  /** The desired GCFS config for the cluster */
		desiredGcfsConfig: Option[Schema.GcfsConfig] = None,
	  /** The desired network tags that apply to all auto-provisioned node pools in autopilot clusters and node auto-provisioning enabled clusters. */
		desiredNodePoolAutoConfigNetworkTags: Option[Schema.NetworkTags] = None,
	  /** The desired config of Gateway API on this cluster. */
		desiredGatewayApiConfig: Option[Schema.GatewayAPIConfig] = None,
	  /** The current etag of the cluster. If an etag is provided and does not match the current etag of the cluster, update will be blocked and an ABORTED error will be returned. */
		etag: Option[String] = None,
	  /** The desired node pool logging configuration defaults for the cluster. */
		desiredNodePoolLoggingConfig: Option[Schema.NodePoolLoggingConfig] = None,
	  /** The desired fleet configuration for the cluster. */
		desiredFleet: Option[Schema.Fleet] = None,
	  /** The desired stack type of the cluster. If a stack type is provided and does not match the current stack type of the cluster, update will attempt to change the stack type to the new type. */
		desiredStackType: Option[Schema.ClusterUpdate.DesiredStackTypeEnum] = None,
	  /** The additional pod ranges to be added to the cluster. These pod ranges can be used by node pools to allocate pod IPs. */
		additionalPodRangesConfig: Option[Schema.AdditionalPodRangesConfig] = None,
	  /** The additional pod ranges that are to be removed from the cluster. The pod ranges specified here must have been specified earlier in the 'additional_pod_ranges_config' argument. */
		removedAdditionalPodRangesConfig: Option[Schema.AdditionalPodRangesConfig] = None,
	  /** Kubernetes open source beta apis enabled on the cluster. Only beta apis */
		enableK8sBetaApis: Option[Schema.K8sBetaAPIConfig] = None,
	  /** Enable/Disable Security Posture API features for the cluster. */
		desiredSecurityPostureConfig: Option[Schema.SecurityPostureConfig] = None,
	  /** The desired network performance config. */
		desiredNetworkPerformanceConfig: Option[Schema.ClusterNetworkPerformanceConfig] = None,
	  /** Enable/Disable FQDN Network Policy for the cluster. */
		desiredEnableFqdnNetworkPolicy: Option[Boolean] = None,
	  /** The desired workload policy configuration for the autopilot cluster. */
		desiredAutopilotWorkloadPolicyConfig: Option[Schema.WorkloadPolicyConfig] = None,
	  /** Desired Beta APIs to be enabled for cluster. */
		desiredK8sBetaApis: Option[Schema.K8sBetaAPIConfig] = None,
	  /** The desired containerd config for the cluster. */
		desiredContainerdConfig: Option[Schema.ContainerdConfig] = None,
	  /** Enable/Disable Multi-Networking for the cluster */
		desiredEnableMultiNetworking: Option[Boolean] = None,
	  /** The desired resource manager tags that apply to all auto-provisioned node pools in autopilot clusters and node auto-provisioning enabled clusters. */
		desiredNodePoolAutoConfigResourceManagerTags: Option[Schema.ResourceManagerTags] = None,
	  /** Specify the details of in-transit encryption. */
		desiredInTransitEncryptionConfig: Option[Schema.ClusterUpdate.DesiredInTransitEncryptionConfigEnum] = None,
	  /** Enable/Disable Cilium Clusterwide Network Policy for the cluster. */
		desiredEnableCiliumClusterwideNetworkPolicy: Option[Boolean] = None,
	  /** Enable/Disable Secret Manager Config. */
		desiredSecretManagerConfig: Option[Schema.SecretManagerConfig] = None,
	  /** Enable/Disable Compliance Posture features for the cluster. */
		desiredCompliancePostureConfig: Option[Schema.CompliancePostureConfig] = None,
	  /** The desired node kubelet config for the cluster. */
		desiredNodeKubeletConfig: Option[Schema.NodeKubeletConfig] = None,
	  /** The desired node kubelet config for all auto-provisioned node pools in autopilot clusters and node auto-provisioning enabled clusters. */
		desiredNodePoolAutoConfigKubeletConfig: Option[Schema.NodeKubeletConfig] = None,
	  /** The Custom keys configuration for the cluster. */
		userManagedKeysConfig: Option[Schema.UserManagedKeysConfig] = None,
	  /** RBACBindingConfig allows user to restrict ClusterRoleBindings an RoleBindings that can be created. */
		desiredRbacBindingConfig: Option[Schema.RBACBindingConfig] = None,
	  /** The desired enterprise configuration for the cluster. */
		desiredEnterpriseConfig: Option[Schema.DesiredEnterpriseConfig] = None,
	  /** The desired Linux node config for all auto-provisioned node pools in autopilot clusters and node auto-provisioning enabled clusters. Currently only `cgroup_mode` can be set here. */
		desiredNodePoolAutoConfigLinuxNodeConfig: Option[Schema.LinuxNodeConfig] = None
	)
	
	case class IntraNodeVisibilityConfig(
	  /** Enables intra node visibility for this cluster. */
		enabled: Option[Boolean] = None
	)
	
	case class ILBSubsettingConfig(
	  /** Enables l4 ILB subsetting for this cluster. */
		enabled: Option[Boolean] = None
	)
	
	object DesiredEnterpriseConfig {
		enum DesiredTierEnum extends Enum[DesiredTierEnum] { case CLUSTER_TIER_UNSPECIFIED, STANDARD, ENTERPRISE }
	}
	case class DesiredEnterpriseConfig(
	  /** desired_tier specifies the desired tier of the cluster. */
		desiredTier: Option[Schema.DesiredEnterpriseConfig.DesiredTierEnum] = None
	)
	
	case class UpdateNodePoolRequest(
	  /** Deprecated. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). This field has been deprecated and replaced by the name field. */
		projectId: Option[String] = None,
	  /** Deprecated. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. This field has been deprecated and replaced by the name field. */
		zone: Option[String] = None,
	  /** Deprecated. The name of the cluster to upgrade. This field has been deprecated and replaced by the name field. */
		clusterId: Option[String] = None,
	  /** Deprecated. The name of the node pool to upgrade. This field has been deprecated and replaced by the name field. */
		nodePoolId: Option[String] = None,
	  /** Required. The Kubernetes version to change the nodes to (typically an upgrade). Users may specify either explicit versions offered by Kubernetes Engine or version aliases, which have the following behavior: - "latest": picks the highest valid Kubernetes version - "1.X": picks the highest valid patch+gke.N patch in the 1.X version - "1.X.Y": picks the highest valid gke.N patch in the 1.X.Y version - "1.X.Y-gke.N": picks an explicit Kubernetes version - "-": picks the Kubernetes master version */
		nodeVersion: Option[String] = None,
	  /** Required. The desired image type for the node pool. Please see https://cloud.google.com/kubernetes-engine/docs/concepts/node-images for available image types. */
		imageType: Option[String] = None,
	  /** The name (project, location, cluster, node pool) of the node pool to update. Specified in the format `projects/&#42;/locations/&#42;/clusters/&#42;/nodePools/&#42;`. */
		name: Option[String] = None,
	  /** The desired list of Google Compute Engine [zones](https://cloud.google.com/compute/docs/zones#available) in which the node pool's nodes should be located. Changing the locations for a node pool will result in nodes being either created or removed from the node pool, depending on whether locations are being added or removed. */
		locations: Option[List[String]] = None,
	  /** The desired workload metadata config for the node pool. */
		workloadMetadataConfig: Option[Schema.WorkloadMetadataConfig] = None,
	  /** Upgrade settings control disruption and speed of the upgrade. */
		upgradeSettings: Option[Schema.UpgradeSettings] = None,
	  /** The desired network tags to be applied to all nodes in the node pool. If this field is not present, the tags will not be changed. Otherwise, the existing network tags will be &#42;replaced&#42; with the provided tags. */
		tags: Option[Schema.NetworkTags] = None,
	  /** The desired node taints to be applied to all nodes in the node pool. If this field is not present, the taints will not be changed. Otherwise, the existing node taints will be &#42;replaced&#42; with the provided taints. */
		taints: Option[Schema.NodeTaints] = None,
	  /** The desired node labels to be applied to all nodes in the node pool. If this field is not present, the labels will not be changed. Otherwise, the existing node labels will be &#42;replaced&#42; with the provided labels. */
		labels: Option[Schema.NodeLabels] = None,
	  /** Parameters that can be configured on Linux nodes. */
		linuxNodeConfig: Option[Schema.LinuxNodeConfig] = None,
	  /** Node kubelet configs. */
		kubeletConfig: Option[Schema.NodeKubeletConfig] = None,
	  /** Node network config. */
		nodeNetworkConfig: Option[Schema.NodeNetworkConfig] = None,
	  /** GCFS config. */
		gcfsConfig: Option[Schema.GcfsConfig] = None,
	  /** Confidential nodes config. All the nodes in the node pool will be Confidential VM once enabled. */
		confidentialNodes: Option[Schema.ConfidentialNodes] = None,
	  /** Enable or disable gvnic on the node pool. */
		gvnic: Option[Schema.VirtualNIC] = None,
	  /** The current etag of the node pool. If an etag is provided and does not match the current etag of the node pool, update will be blocked and an ABORTED error will be returned. */
		etag: Option[String] = None,
	  /** Enable or disable NCCL fast socket for the node pool. */
		fastSocket: Option[Schema.FastSocket] = None,
	  /** Logging configuration. */
		loggingConfig: Option[Schema.NodePoolLoggingConfig] = None,
	  /** The resource labels for the node pool to use to annotate any related Google Compute Engine resources. */
		resourceLabels: Option[Schema.ResourceLabels] = None,
	  /** Parameters that can be configured on Windows nodes. */
		windowsNodeConfig: Option[Schema.WindowsNodeConfig] = None,
	  /** A list of hardware accelerators to be attached to each node. See https://cloud.google.com/compute/docs/gpus for more information about support for GPUs. */
		accelerators: Option[List[Schema.AcceleratorConfig]] = None,
	  /** Optional. The desired [Google Compute Engine machine type](https://cloud.google.com/compute/docs/machine-types) for nodes in the node pool. Initiates an upgrade operation that migrates the nodes in the node pool to the specified machine type. */
		machineType: Option[String] = None,
	  /** Optional. The desired disk type (e.g. 'pd-standard', 'pd-ssd' or 'pd-balanced') for nodes in the node pool. Initiates an upgrade operation that migrates the nodes in the node pool to the specified disk type. */
		diskType: Option[String] = None,
	  /** Optional. The desired disk size for nodes in the node pool specified in GB. The smallest allowed disk size is 10GB. Initiates an upgrade operation that migrates the nodes in the node pool to the specified disk size. */
		diskSizeGb: Option[String] = None,
	  /** Desired resource manager tag keys and values to be attached to the nodes for managing Compute Engine firewalls using Network Firewall Policies. Existing tags will be replaced with new values. */
		resourceManagerTags: Option[Schema.ResourceManagerTags] = None,
	  /** The desired containerd config for nodes in the node pool. Initiates an upgrade operation that recreates the nodes with the new config. */
		containerdConfig: Option[Schema.ContainerdConfig] = None,
	  /** Specifies the configuration of queued provisioning. */
		queuedProvisioning: Option[Schema.QueuedProvisioning] = None,
	  /** List of Storage Pools where boot disks are provisioned. Existing Storage Pools will be replaced with storage-pools. */
		storagePools: Option[List[String]] = None
	)
	
	case class NodeTaints(
	  /** List of node taints. */
		taints: Option[List[Schema.NodeTaint]] = None
	)
	
	case class NodeLabels(
	  /** Map of node label keys and node label values. */
		labels: Option[Map[String, String]] = None
	)
	
	case class ResourceLabels(
	  /** Map of node label keys and node label values. */
		labels: Option[Map[String, String]] = None
	)
	
	case class SetNodePoolAutoscalingRequest(
	  /** Deprecated. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). This field has been deprecated and replaced by the name field. */
		projectId: Option[String] = None,
	  /** Deprecated. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. This field has been deprecated and replaced by the name field. */
		zone: Option[String] = None,
	  /** Deprecated. The name of the cluster to upgrade. This field has been deprecated and replaced by the name field. */
		clusterId: Option[String] = None,
	  /** Deprecated. The name of the node pool to upgrade. This field has been deprecated and replaced by the name field. */
		nodePoolId: Option[String] = None,
	  /** Required. Autoscaling configuration for the node pool. */
		autoscaling: Option[Schema.NodePoolAutoscaling] = None,
	  /** The name (project, location, cluster, node pool) of the node pool to set autoscaler settings. Specified in the format `projects/&#42;/locations/&#42;/clusters/&#42;/nodePools/&#42;`. */
		name: Option[String] = None
	)
	
	case class SetLoggingServiceRequest(
	  /** Deprecated. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). This field has been deprecated and replaced by the name field. */
		projectId: Option[String] = None,
	  /** Deprecated. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. This field has been deprecated and replaced by the name field. */
		zone: Option[String] = None,
	  /** Deprecated. The name of the cluster to upgrade. This field has been deprecated and replaced by the name field. */
		clusterId: Option[String] = None,
	  /** Required. The logging service the cluster should use to write logs. Currently available options: &#42; `logging.googleapis.com/kubernetes` - The Cloud Logging service with a Kubernetes-native resource model &#42; `logging.googleapis.com` - The legacy Cloud Logging service (no longer available as of GKE 1.15). &#42; `none` - no logs will be exported from the cluster. If left as an empty string,`logging.googleapis.com/kubernetes` will be used for GKE 1.14+ or `logging.googleapis.com` for earlier versions. */
		loggingService: Option[String] = None,
	  /** The name (project, location, cluster) of the cluster to set logging. Specified in the format `projects/&#42;/locations/&#42;/clusters/&#42;`. */
		name: Option[String] = None
	)
	
	case class SetMonitoringServiceRequest(
	  /** Deprecated. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). This field has been deprecated and replaced by the name field. */
		projectId: Option[String] = None,
	  /** Deprecated. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. This field has been deprecated and replaced by the name field. */
		zone: Option[String] = None,
	  /** Deprecated. The name of the cluster to upgrade. This field has been deprecated and replaced by the name field. */
		clusterId: Option[String] = None,
	  /** Required. The monitoring service the cluster should use to write metrics. Currently available options: &#42; "monitoring.googleapis.com/kubernetes" - The Cloud Monitoring service with a Kubernetes-native resource model &#42; `monitoring.googleapis.com` - The legacy Cloud Monitoring service (no longer available as of GKE 1.15). &#42; `none` - No metrics will be exported from the cluster. If left as an empty string,`monitoring.googleapis.com/kubernetes` will be used for GKE 1.14+ or `monitoring.googleapis.com` for earlier versions. */
		monitoringService: Option[String] = None,
	  /** The name (project, location, cluster) of the cluster to set monitoring. Specified in the format `projects/&#42;/locations/&#42;/clusters/&#42;`. */
		name: Option[String] = None
	)
	
	case class SetAddonsConfigRequest(
	  /** Deprecated. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). This field has been deprecated and replaced by the name field. */
		projectId: Option[String] = None,
	  /** Deprecated. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. This field has been deprecated and replaced by the name field. */
		zone: Option[String] = None,
	  /** Deprecated. The name of the cluster to upgrade. This field has been deprecated and replaced by the name field. */
		clusterId: Option[String] = None,
	  /** Required. The desired configurations for the various addons available to run in the cluster. */
		addonsConfig: Option[Schema.AddonsConfig] = None,
	  /** The name (project, location, cluster) of the cluster to set addons. Specified in the format `projects/&#42;/locations/&#42;/clusters/&#42;`. */
		name: Option[String] = None
	)
	
	case class SetLocationsRequest(
	  /** Deprecated. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). This field has been deprecated and replaced by the name field. */
		projectId: Option[String] = None,
	  /** Deprecated. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. This field has been deprecated and replaced by the name field. */
		zone: Option[String] = None,
	  /** Deprecated. The name of the cluster to upgrade. This field has been deprecated and replaced by the name field. */
		clusterId: Option[String] = None,
	  /** Required. The desired list of Google Compute Engine [zones](https://cloud.google.com/compute/docs/zones#available) in which the cluster's nodes should be located. Changing the locations a cluster is in will result in nodes being either created or removed from the cluster, depending on whether locations are being added or removed. This list must always include the cluster's primary zone. */
		locations: Option[List[String]] = None,
	  /** The name (project, location, cluster) of the cluster to set locations. Specified in the format `projects/&#42;/locations/&#42;/clusters/&#42;`. */
		name: Option[String] = None
	)
	
	case class UpdateMasterRequest(
	  /** Deprecated. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). This field has been deprecated and replaced by the name field. */
		projectId: Option[String] = None,
	  /** Deprecated. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. This field has been deprecated and replaced by the name field. */
		zone: Option[String] = None,
	  /** Deprecated. The name of the cluster to upgrade. This field has been deprecated and replaced by the name field. */
		clusterId: Option[String] = None,
	  /** Required. The Kubernetes version to change the master to. Users may specify either explicit versions offered by Kubernetes Engine or version aliases, which have the following behavior: - "latest": picks the highest valid Kubernetes version - "1.X": picks the highest valid patch+gke.N patch in the 1.X version - "1.X.Y": picks the highest valid gke.N patch in the 1.X.Y version - "1.X.Y-gke.N": picks an explicit Kubernetes version - "-": picks the default Kubernetes version */
		masterVersion: Option[String] = None,
	  /** The name (project, location, cluster) of the cluster to update. Specified in the format `projects/&#42;/locations/&#42;/clusters/&#42;`. */
		name: Option[String] = None
	)
	
	object SetMasterAuthRequest {
		enum ActionEnum extends Enum[ActionEnum] { case UNKNOWN, SET_PASSWORD, GENERATE_PASSWORD, SET_USERNAME }
	}
	case class SetMasterAuthRequest(
	  /** Deprecated. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). This field has been deprecated and replaced by the name field. */
		projectId: Option[String] = None,
	  /** Deprecated. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. This field has been deprecated and replaced by the name field. */
		zone: Option[String] = None,
	  /** Deprecated. The name of the cluster to upgrade. This field has been deprecated and replaced by the name field. */
		clusterId: Option[String] = None,
	  /** Required. The exact form of action to be taken on the master auth. */
		action: Option[Schema.SetMasterAuthRequest.ActionEnum] = None,
	  /** Required. A description of the update. */
		update: Option[Schema.MasterAuth] = None,
	  /** The name (project, location, cluster) of the cluster to set auth. Specified in the format `projects/&#42;/locations/&#42;/clusters/&#42;`. */
		name: Option[String] = None
	)
	
	case class ListOperationsResponse(
	  /** A list of operations in the project in the specified zone. */
		operations: Option[List[Schema.Operation]] = None,
	  /** If any zones are listed here, the list of operations returned may be missing the operations from those zones. */
		missingZones: Option[List[String]] = None
	)
	
	case class CancelOperationRequest(
	  /** Deprecated. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). This field has been deprecated and replaced by the name field. */
		projectId: Option[String] = None,
	  /** Deprecated. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the operation resides. This field has been deprecated and replaced by the name field. */
		zone: Option[String] = None,
	  /** Deprecated. The server-assigned `name` of the operation. This field has been deprecated and replaced by the name field. */
		operationId: Option[String] = None,
	  /** The name (project, location, operation id) of the operation to cancel. Specified in the format `projects/&#42;/locations/&#42;/operations/&#42;`. */
		name: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class ServerConfig(
	  /** Version of Kubernetes the service deploys by default. */
		defaultClusterVersion: Option[String] = None,
	  /** List of valid node upgrade target versions, in descending order. */
		validNodeVersions: Option[List[String]] = None,
	  /** Default image type. */
		defaultImageType: Option[String] = None,
	  /** List of valid image types. */
		validImageTypes: Option[List[String]] = None,
	  /** List of valid master versions, in descending order. */
		validMasterVersions: Option[List[String]] = None,
	  /** List of release channel configurations. */
		channels: Option[List[Schema.ReleaseChannelConfig]] = None
	)
	
	object ReleaseChannelConfig {
		enum ChannelEnum extends Enum[ChannelEnum] { case UNSPECIFIED, RAPID, REGULAR, STABLE, EXTENDED }
	}
	case class ReleaseChannelConfig(
	  /** The release channel this configuration applies to. */
		channel: Option[Schema.ReleaseChannelConfig.ChannelEnum] = None,
	  /** The default version for newly created clusters on the channel. */
		defaultVersion: Option[String] = None,
	  /** List of valid versions for the channel. */
		validVersions: Option[List[String]] = None,
	  /** The auto upgrade target version for clusters on the channel. */
		upgradeTargetVersion: Option[String] = None
	)
	
	case class GetOpenIDConfigResponse(
	  /** OIDC Issuer. */
		issuer: Option[String] = None,
	  /** JSON Web Key uri. */
		jwks_uri: Option[String] = None,
	  /** Supported response types. */
		response_types_supported: Option[List[String]] = None,
	  /** Supported subject types. */
		subject_types_supported: Option[List[String]] = None,
	  /** supported ID Token signing Algorithms. */
		id_token_signing_alg_values_supported: Option[List[String]] = None,
	  /** Supported claims. */
		claims_supported: Option[List[String]] = None,
	  /** Supported grant types. */
		grant_types: Option[List[String]] = None,
	  /** For HTTP requests, this field is automatically extracted into the Cache-Control HTTP header. */
		cacheHeader: Option[Schema.HttpCacheControlResponseHeader] = None
	)
	
	case class HttpCacheControlResponseHeader(
	  /** 14.9 request and response directives */
		directive: Option[String] = None,
	  /** 14.6 response cache age, in seconds since the response is generated */
		age: Option[String] = None,
	  /** 14.21 response cache expires, in RFC 1123 date format */
		expires: Option[String] = None
	)
	
	case class GetJSONWebKeysResponse(
	  /** The public component of the keys used by the cluster to sign token requests. */
		keys: Option[List[Schema.Jwk]] = None,
	  /** For HTTP requests, this field is automatically extracted into the Cache-Control HTTP header. */
		cacheHeader: Option[Schema.HttpCacheControlResponseHeader] = None
	)
	
	case class Jwk(
	  /** Key Type. */
		kty: Option[String] = None,
	  /** Algorithm. */
		alg: Option[String] = None,
	  /** Permitted uses for the public keys. */
		use: Option[String] = None,
	  /** Key ID. */
		kid: Option[String] = None,
	  /** Used for RSA keys. */
		n: Option[String] = None,
	  /** Used for RSA keys. */
		e: Option[String] = None,
	  /** Used for ECDSA keys. */
		x: Option[String] = None,
	  /** Used for ECDSA keys. */
		y: Option[String] = None,
	  /** Used for ECDSA keys. */
		crv: Option[String] = None
	)
	
	case class ListNodePoolsResponse(
	  /** A list of node pools for a cluster. */
		nodePools: Option[List[Schema.NodePool]] = None
	)
	
	case class CreateNodePoolRequest(
	  /** Deprecated. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). This field has been deprecated and replaced by the parent field. */
		projectId: Option[String] = None,
	  /** Deprecated. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. This field has been deprecated and replaced by the parent field. */
		zone: Option[String] = None,
	  /** Deprecated. The name of the cluster. This field has been deprecated and replaced by the parent field. */
		clusterId: Option[String] = None,
	  /** Required. The node pool to create. */
		nodePool: Option[Schema.NodePool] = None,
	  /** The parent (project, location, cluster name) where the node pool will be created. Specified in the format `projects/&#42;/locations/&#42;/clusters/&#42;`. */
		parent: Option[String] = None
	)
	
	case class CompleteNodePoolUpgradeRequest(
	
	)
	
	case class RollbackNodePoolUpgradeRequest(
	  /** Deprecated. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). This field has been deprecated and replaced by the name field. */
		projectId: Option[String] = None,
	  /** Deprecated. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. This field has been deprecated and replaced by the name field. */
		zone: Option[String] = None,
	  /** Deprecated. The name of the cluster to rollback. This field has been deprecated and replaced by the name field. */
		clusterId: Option[String] = None,
	  /** Deprecated. The name of the node pool to rollback. This field has been deprecated and replaced by the name field. */
		nodePoolId: Option[String] = None,
	  /** The name (project, location, cluster, node pool id) of the node poll to rollback upgrade. Specified in the format `projects/&#42;/locations/&#42;/clusters/&#42;/nodePools/&#42;`. */
		name: Option[String] = None,
	  /** Option for rollback to ignore the PodDisruptionBudget. Default value is false. */
		respectPdb: Option[Boolean] = None
	)
	
	case class SetNodePoolManagementRequest(
	  /** Deprecated. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). This field has been deprecated and replaced by the name field. */
		projectId: Option[String] = None,
	  /** Deprecated. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. This field has been deprecated and replaced by the name field. */
		zone: Option[String] = None,
	  /** Deprecated. The name of the cluster to update. This field has been deprecated and replaced by the name field. */
		clusterId: Option[String] = None,
	  /** Deprecated. The name of the node pool to update. This field has been deprecated and replaced by the name field. */
		nodePoolId: Option[String] = None,
	  /** Required. NodeManagement configuration for the node pool. */
		management: Option[Schema.NodeManagement] = None,
	  /** The name (project, location, cluster, node pool id) of the node pool to set management properties. Specified in the format `projects/&#42;/locations/&#42;/clusters/&#42;/nodePools/&#42;`. */
		name: Option[String] = None
	)
	
	case class SetLabelsRequest(
	  /** Deprecated. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). This field has been deprecated and replaced by the name field. */
		projectId: Option[String] = None,
	  /** Deprecated. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. This field has been deprecated and replaced by the name field. */
		zone: Option[String] = None,
	  /** Deprecated. The name of the cluster. This field has been deprecated and replaced by the name field. */
		clusterId: Option[String] = None,
	  /** Required. The labels to set for that cluster. */
		resourceLabels: Option[Map[String, String]] = None,
	  /** Required. The fingerprint of the previous set of labels for this resource, used to detect conflicts. The fingerprint is initially generated by Kubernetes Engine and changes after every request to modify or update labels. You must always provide an up-to-date fingerprint hash when updating or changing labels. Make a `get()` request to the resource to get the latest fingerprint. */
		labelFingerprint: Option[String] = None,
	  /** The name (project, location, cluster name) of the cluster to set labels. Specified in the format `projects/&#42;/locations/&#42;/clusters/&#42;`. */
		name: Option[String] = None
	)
	
	case class SetLegacyAbacRequest(
	  /** Deprecated. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). This field has been deprecated and replaced by the name field. */
		projectId: Option[String] = None,
	  /** Deprecated. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. This field has been deprecated and replaced by the name field. */
		zone: Option[String] = None,
	  /** Deprecated. The name of the cluster to update. This field has been deprecated and replaced by the name field. */
		clusterId: Option[String] = None,
	  /** Required. Whether ABAC authorization will be enabled in the cluster. */
		enabled: Option[Boolean] = None,
	  /** The name (project, location, cluster name) of the cluster to set legacy abac. Specified in the format `projects/&#42;/locations/&#42;/clusters/&#42;`. */
		name: Option[String] = None
	)
	
	case class StartIPRotationRequest(
	  /** Deprecated. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). This field has been deprecated and replaced by the name field. */
		projectId: Option[String] = None,
	  /** Deprecated. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. This field has been deprecated and replaced by the name field. */
		zone: Option[String] = None,
	  /** Deprecated. The name of the cluster. This field has been deprecated and replaced by the name field. */
		clusterId: Option[String] = None,
	  /** The name (project, location, cluster name) of the cluster to start IP rotation. Specified in the format `projects/&#42;/locations/&#42;/clusters/&#42;`. */
		name: Option[String] = None,
	  /** Whether to rotate credentials during IP rotation. */
		rotateCredentials: Option[Boolean] = None
	)
	
	case class CompleteIPRotationRequest(
	  /** Deprecated. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). This field has been deprecated and replaced by the name field. */
		projectId: Option[String] = None,
	  /** Deprecated. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. This field has been deprecated and replaced by the name field. */
		zone: Option[String] = None,
	  /** Deprecated. The name of the cluster. This field has been deprecated and replaced by the name field. */
		clusterId: Option[String] = None,
	  /** The name (project, location, cluster name) of the cluster to complete IP rotation. Specified in the format `projects/&#42;/locations/&#42;/clusters/&#42;`. */
		name: Option[String] = None
	)
	
	case class SetNodePoolSizeRequest(
	  /** Deprecated. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). This field has been deprecated and replaced by the name field. */
		projectId: Option[String] = None,
	  /** Deprecated. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. This field has been deprecated and replaced by the name field. */
		zone: Option[String] = None,
	  /** Deprecated. The name of the cluster to update. This field has been deprecated and replaced by the name field. */
		clusterId: Option[String] = None,
	  /** Deprecated. The name of the node pool to update. This field has been deprecated and replaced by the name field. */
		nodePoolId: Option[String] = None,
	  /** Required. The desired node count for the pool. */
		nodeCount: Option[Int] = None,
	  /** The name (project, location, cluster, node pool id) of the node pool to set size. Specified in the format `projects/&#42;/locations/&#42;/clusters/&#42;/nodePools/&#42;`. */
		name: Option[String] = None
	)
	
	case class SetNetworkPolicyRequest(
	  /** Deprecated. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). This field has been deprecated and replaced by the name field. */
		projectId: Option[String] = None,
	  /** Deprecated. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. This field has been deprecated and replaced by the name field. */
		zone: Option[String] = None,
	  /** Deprecated. The name of the cluster. This field has been deprecated and replaced by the name field. */
		clusterId: Option[String] = None,
	  /** Required. Configuration options for the NetworkPolicy feature. */
		networkPolicy: Option[Schema.NetworkPolicy] = None,
	  /** The name (project, location, cluster name) of the cluster to set networking policy. Specified in the format `projects/&#42;/locations/&#42;/clusters/&#42;`. */
		name: Option[String] = None
	)
	
	case class SetMaintenancePolicyRequest(
	  /** Required. The Google Developers Console [project ID or project number](https://cloud.google.com/resource-manager/docs/creating-managing-projects). */
		projectId: Option[String] = None,
	  /** Required. The name of the Google Compute Engine [zone](https://cloud.google.com/compute/docs/zones#available) in which the cluster resides. */
		zone: Option[String] = None,
	  /** Required. The name of the cluster to update. */
		clusterId: Option[String] = None,
	  /** Required. The maintenance policy to be set for the cluster. An empty field clears the existing maintenance policy. */
		maintenancePolicy: Option[Schema.MaintenancePolicy] = None,
	  /** The name (project, location, cluster name) of the cluster to set maintenance policy. Specified in the format `projects/&#42;/locations/&#42;/clusters/&#42;`. */
		name: Option[String] = None
	)
	
	case class ListUsableSubnetworksResponse(
	  /** A list of usable subnetworks in the specified network project. */
		subnetworks: Option[List[Schema.UsableSubnetwork]] = None,
	  /** This token allows you to get the next page of results for list requests. If the number of results is larger than `page_size`, use the `next_page_token` as a value for the query parameter `page_token` in the next request. The value will become empty when there are no more pages. */
		nextPageToken: Option[String] = None
	)
	
	case class UsableSubnetwork(
	  /** Subnetwork Name. Example: projects/my-project/regions/us-central1/subnetworks/my-subnet */
		subnetwork: Option[String] = None,
	  /** Network Name. Example: projects/my-project/global/networks/my-network */
		network: Option[String] = None,
	  /** The range of internal addresses that are owned by this subnetwork. */
		ipCidrRange: Option[String] = None,
	  /** Secondary IP ranges. */
		secondaryIpRanges: Option[List[Schema.UsableSubnetworkSecondaryRange]] = None,
	  /** A human readable status message representing the reasons for cases where the caller cannot use the secondary ranges under the subnet. For example if the secondary_ip_ranges is empty due to a permission issue, an insufficient permission message will be given by status_message. */
		statusMessage: Option[String] = None
	)
	
	object UsableSubnetworkSecondaryRange {
		enum StatusEnum extends Enum[StatusEnum] { case UNKNOWN, UNUSED, IN_USE_SERVICE, IN_USE_SHAREABLE_POD, IN_USE_MANAGED_POD }
	}
	case class UsableSubnetworkSecondaryRange(
	  /** The name associated with this subnetwork secondary range, used when adding an alias IP range to a VM instance. */
		rangeName: Option[String] = None,
	  /** The range of IP addresses belonging to this subnetwork secondary range. */
		ipCidrRange: Option[String] = None,
	  /** This field is to determine the status of the secondary range programmably. */
		status: Option[Schema.UsableSubnetworkSecondaryRange.StatusEnum] = None
	)
	
	case class CheckAutopilotCompatibilityResponse(
	  /** The list of issues for the given operation. */
		issues: Option[List[Schema.AutopilotCompatibilityIssue]] = None,
	  /** The summary of the autopilot compatibility response. */
		summary: Option[String] = None
	)
	
	object AutopilotCompatibilityIssue {
		enum IncompatibilityTypeEnum extends Enum[IncompatibilityTypeEnum] { case UNSPECIFIED, INCOMPATIBILITY, ADDITIONAL_CONFIG_REQUIRED, PASSED_WITH_OPTIONAL_CONFIG }
	}
	case class AutopilotCompatibilityIssue(
	  /** The last time when this issue was observed. */
		lastObservation: Option[String] = None,
	  /** The constraint type of the issue. */
		constraintType: Option[String] = None,
	  /** The incompatibility type of this issue. */
		incompatibilityType: Option[Schema.AutopilotCompatibilityIssue.IncompatibilityTypeEnum] = None,
	  /** The name of the resources which are subject to this issue. */
		subjects: Option[List[String]] = None,
	  /** A URL to a public documnetation, which addresses resolving this issue. */
		documentationUrl: Option[String] = None,
	  /** The description of the issue. */
		description: Option[String] = None
	)
	
	object UpgradeEvent {
		enum ResourceTypeEnum extends Enum[ResourceTypeEnum] { case UPGRADE_RESOURCE_TYPE_UNSPECIFIED, MASTER, NODE_POOL }
	}
	case class UpgradeEvent(
	  /** The resource type that is upgrading. */
		resourceType: Option[Schema.UpgradeEvent.ResourceTypeEnum] = None,
	  /** The operation associated with this upgrade. */
		operation: Option[String] = None,
	  /** The time when the operation was started. */
		operationStartTime: Option[String] = None,
	  /** The current version before the upgrade. */
		currentVersion: Option[String] = None,
	  /** The target version for the upgrade. */
		targetVersion: Option[String] = None,
	  /** Optional relative path to the resource. For example in node pool upgrades, the relative path of the node pool. */
		resource: Option[String] = None
	)
	
	object UpgradeInfoEvent {
		enum ResourceTypeEnum extends Enum[ResourceTypeEnum] { case UPGRADE_RESOURCE_TYPE_UNSPECIFIED, MASTER, NODE_POOL }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STARTED, SUCCEEDED, FAILED, CANCELED }
	}
	case class UpgradeInfoEvent(
	  /** The resource type associated with the upgrade. */
		resourceType: Option[Schema.UpgradeInfoEvent.ResourceTypeEnum] = None,
	  /** The operation associated with this upgrade. */
		operation: Option[String] = None,
	  /** The time when the operation was started. */
		startTime: Option[String] = None,
	  /** The time when the operation ended. */
		endTime: Option[String] = None,
	  /** The current version before the upgrade. */
		currentVersion: Option[String] = None,
	  /** The target version for the upgrade. */
		targetVersion: Option[String] = None,
	  /** Optional relative path to the resource. For example in node pool upgrades, the relative path of the node pool. */
		resource: Option[String] = None,
	  /** Output only. The state of the upgrade. */
		state: Option[Schema.UpgradeInfoEvent.StateEnum] = None,
	  /** A brief description of the event. */
		description: Option[String] = None
	)
	
	object UpgradeAvailableEvent {
		enum ResourceTypeEnum extends Enum[ResourceTypeEnum] { case UPGRADE_RESOURCE_TYPE_UNSPECIFIED, MASTER, NODE_POOL }
	}
	case class UpgradeAvailableEvent(
	  /** The release version available for upgrade. */
		version: Option[String] = None,
	  /** The resource type of the release version. */
		resourceType: Option[Schema.UpgradeAvailableEvent.ResourceTypeEnum] = None,
	  /** The release channel of the version. If empty, it means a non-channel release. */
		releaseChannel: Option[Schema.ReleaseChannel] = None,
	  /** Optional relative path to the resource. For example, the relative path of the node pool. */
		resource: Option[String] = None
	)
	
	case class SecurityBulletinEvent(
	  /** The resource type (node/control plane) that has the vulnerability. Multiple notifications (1 notification per resource type) will be sent for a vulnerability that affects > 1 resource type. */
		resourceTypeAffected: Option[String] = None,
	  /** The ID of the bulletin corresponding to the vulnerability. */
		bulletinId: Option[String] = None,
	  /** The CVEs associated with this bulletin. */
		cveIds: Option[List[String]] = None,
	  /** The severity of this bulletin as it relates to GKE. */
		severity: Option[String] = None,
	  /** The URI link to the bulletin on the website for more information. */
		bulletinUri: Option[String] = None,
	  /** A brief description of the bulletin. See the bulletin pointed to by the bulletin_uri field for an expanded description. */
		briefDescription: Option[String] = None,
	  /** The GKE minor versions affected by this vulnerability. */
		affectedSupportedMinors: Option[List[String]] = None,
	  /** The GKE versions where this vulnerability is patched. */
		patchedVersions: Option[List[String]] = None,
	  /** This represents a version selected from the patched_versions field that the cluster receiving this notification should most likely want to upgrade to based on its current version. Note that if this notification is being received by a given cluster, it means that this version is currently available as an upgrade target in that cluster's location. */
		suggestedUpgradeTarget: Option[String] = None,
	  /** If this field is specified, it means there are manual steps that the user must take to make their clusters safe. */
		manualStepsRequired: Option[Boolean] = None
	)
}
