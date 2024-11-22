package com.boresjo.play.api.google.composer

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
	
	object Environment {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, RUNNING, UPDATING, DELETING, ERROR }
	}
	case class Environment(
	  /** Identifier. The resource name of the environment, in the form: "projects/{projectId}/locations/{locationId}/environments/{environmentId}" EnvironmentId must start with a lowercase letter followed by up to 63 lowercase letters, numbers, or hyphens, and cannot end with a hyphen. */
		name: Option[String] = None,
	  /** Optional. Configuration parameters for this environment. */
		config: Option[Schema.EnvironmentConfig] = None,
	  /** Output only. The UUID (Universally Unique IDentifier) associated with this environment. This value is generated when the environment is created. */
		uuid: Option[String] = None,
	  /** The current state of the environment. */
		state: Option[Schema.Environment.StateEnum] = None,
	  /** Output only. The time at which this environment was created. */
		createTime: Option[String] = None,
	  /** Output only. The time at which this environment was last modified. */
		updateTime: Option[String] = None,
	  /** Optional. User-defined labels for this environment. The labels map can contain no more than 64 entries. Entries of the labels map are UTF8 strings that comply with the following restrictions: &#42; Keys must conform to regexp: \p{Ll}\p{Lo}{0,62} &#42; Values must conform to regexp: [\p{Ll}\p{Lo}\p{N}_-]{0,63} &#42; Both keys and values are additionally constrained to be <= 128 bytes in size. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzi: Option[Boolean] = None,
	  /** Optional. Storage configuration for this environment. */
		storageConfig: Option[Schema.StorageConfig] = None
	)
	
	object EnvironmentConfig {
		enum EnvironmentSizeEnum extends Enum[EnvironmentSizeEnum] { case ENVIRONMENT_SIZE_UNSPECIFIED, ENVIRONMENT_SIZE_SMALL, ENVIRONMENT_SIZE_MEDIUM, ENVIRONMENT_SIZE_LARGE }
		enum ResilienceModeEnum extends Enum[ResilienceModeEnum] { case RESILIENCE_MODE_UNSPECIFIED, HIGH_RESILIENCE }
	}
	case class EnvironmentConfig(
	  /** Output only. The Kubernetes Engine cluster used to run this environment. */
		gkeCluster: Option[String] = None,
	  /** Output only. The Cloud Storage prefix of the DAGs for this environment. Although Cloud Storage objects reside in a flat namespace, a hierarchical file tree can be simulated using "/"-delimited object name prefixes. DAG objects for this environment reside in a simulated directory with the given prefix. */
		dagGcsPrefix: Option[String] = None,
	  /** The number of nodes in the Kubernetes Engine cluster that will be used to run this environment. This field is supported for Cloud Composer environments in versions composer-1.&#42;.&#42;-airflow-&#42;.&#42;.&#42;. */
		nodeCount: Option[Int] = None,
	  /** Optional. The configuration settings for software inside the environment. */
		softwareConfig: Option[Schema.SoftwareConfig] = None,
	  /** Optional. The configuration used for the Kubernetes Engine cluster. */
		nodeConfig: Option[Schema.NodeConfig] = None,
	  /** Optional. The configuration used for the Private IP Cloud Composer environment. */
		privateEnvironmentConfig: Option[Schema.PrivateEnvironmentConfig] = None,
	  /** Optional. The network-level access control policy for the Airflow web server. If unspecified, no network-level access restrictions will be applied. */
		webServerNetworkAccessControl: Option[Schema.WebServerNetworkAccessControl] = None,
	  /** Optional. The configuration settings for Cloud SQL instance used internally by Apache Airflow software. */
		databaseConfig: Option[Schema.DatabaseConfig] = None,
	  /** Optional. The configuration settings for the Airflow web server App Engine instance. */
		webServerConfig: Option[Schema.WebServerConfig] = None,
	  /** Optional. The encryption options for the Cloud Composer environment and its dependencies. Cannot be updated. */
		encryptionConfig: Option[Schema.EncryptionConfig] = None,
	  /** Optional. The maintenance window is the period when Cloud Composer components may undergo maintenance. It is defined so that maintenance is not executed during peak hours or critical time periods. The system will not be under maintenance for every occurrence of this window, but when maintenance is planned, it will be scheduled during the window. The maintenance window period must encompass at least 12 hours per week. This may be split into multiple chunks, each with a size of at least 4 hours. If this value is omitted, the default value for maintenance window is applied. By default, maintenance windows are from 00:00:00 to 04:00:00 (GMT) on Friday, Saturday, and Sunday every week. */
		maintenanceWindow: Option[Schema.MaintenanceWindow] = None,
	  /** Optional. The workloads configuration settings for the GKE cluster associated with the Cloud Composer environment. The GKE cluster runs Airflow scheduler, web server and workers workloads. This field is supported for Cloud Composer environments in versions composer-2.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
		workloadsConfig: Option[Schema.WorkloadsConfig] = None,
	  /** Optional. The size of the Cloud Composer environment. This field is supported for Cloud Composer environments in versions composer-2.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
		environmentSize: Option[Schema.EnvironmentConfig.EnvironmentSizeEnum] = None,
	  /** Output only. The URI of the Apache Airflow Web UI hosted within this environment (see [Airflow web interface](/composer/docs/how-to/accessing/airflow-web-interface)). */
		airflowUri: Option[String] = None,
	  /** Output only. The 'bring your own identity' variant of the URI of the Apache Airflow Web UI hosted within this environment, to be accessed with external identities using workforce identity federation (see [Access environments with workforce identity federation](/composer/docs/composer-2/access-environments-with-workforce-identity-federation)). */
		airflowByoidUri: Option[String] = None,
	  /** Optional. The configuration options for GKE cluster master authorized networks. By default master authorized networks feature is: - in case of private environment: enabled with no external networks allowlisted. - in case of public environment: disabled. */
		masterAuthorizedNetworksConfig: Option[Schema.MasterAuthorizedNetworksConfig] = None,
	  /** Optional. The Recovery settings configuration of an environment. This field is supported for Cloud Composer environments in versions composer-2.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
		recoveryConfig: Option[Schema.RecoveryConfig] = None,
	  /** Optional. Resilience mode of the Cloud Composer Environment. This field is supported for Cloud Composer environments in versions composer-2.2.0-airflow-&#42;.&#42;.&#42; and newer. */
		resilienceMode: Option[Schema.EnvironmentConfig.ResilienceModeEnum] = None,
	  /** Optional. The configuration setting for Airflow database data retention mechanism. */
		dataRetentionConfig: Option[Schema.DataRetentionConfig] = None
	)
	
	object SoftwareConfig {
		enum WebServerPluginsModeEnum extends Enum[WebServerPluginsModeEnum] { case WEB_SERVER_PLUGINS_MODE_UNSPECIFIED, PLUGINS_DISABLED, PLUGINS_ENABLED }
	}
	case class SoftwareConfig(
	  /** Optional. The version of the software running in the environment. This encapsulates both the version of Cloud Composer functionality and the version of Apache Airflow. It must match the regular expression `composer-([0-9]+(\.[0-9]+\.[0-9]+(-preview\.[0-9]+)?)?|latest)-airflow-([0-9]+(\.[0-9]+(\.[0-9]+)?)?)`. When used as input, the server also checks if the provided version is supported and denies the request for an unsupported version. The Cloud Composer portion of the image version is a full [semantic version](https://semver.org), or an alias in the form of major version number or `latest`. When an alias is provided, the server replaces it with the current Cloud Composer version that satisfies the alias. The Apache Airflow portion of the image version is a full semantic version that points to one of the supported Apache Airflow versions, or an alias in the form of only major or major.minor versions specified. When an alias is provided, the server replaces it with the latest Apache Airflow version that satisfies the alias and is supported in the given Cloud Composer version. In all cases, the resolved image version is stored in the same field. See also [version list](/composer/docs/concepts/versioning/composer-versions) and [versioning overview](/composer/docs/concepts/versioning/composer-versioning-overview). */
		imageVersion: Option[String] = None,
	  /** Optional. Apache Airflow configuration properties to override. Property keys contain the section and property names, separated by a hyphen, for example "core-dags_are_paused_at_creation". Section names must not contain hyphens ("-"), opening square brackets ("["), or closing square brackets ("]"). The property name must not be empty and must not contain an equals sign ("=") or semicolon (";"). Section and property names must not contain a period ("."). Apache Airflow configuration property names must be written in [snake_case](https://en.wikipedia.org/wiki/Snake_case). Property values can contain any character, and can be written in any lower/upper case format. Certain Apache Airflow configuration property values are [blocked](/composer/docs/concepts/airflow-configurations), and cannot be overridden. */
		airflowConfigOverrides: Option[Map[String, String]] = None,
	  /** Optional. Custom Python Package Index (PyPI) packages to be installed in the environment. Keys refer to the lowercase package name such as "numpy" and values are the lowercase extras and version specifier such as "==1.12.0", "[devel,gcp_api]", or "[devel]>=1.8.2, <1.9.2". To specify a package without pinning it to a version specifier, use the empty string as the value. */
		pypiPackages: Option[Map[String, String]] = None,
	  /** Optional. Additional environment variables to provide to the Apache Airflow scheduler, worker, and webserver processes. Environment variable names must match the regular expression `a-zA-Z_&#42;`. They cannot specify Apache Airflow software configuration overrides (they cannot match the regular expression `AIRFLOW__[A-Z0-9_]+__[A-Z0-9_]+`), and they cannot match any of the following reserved names: &#42; `AIRFLOW_HOME` &#42; `C_FORCE_ROOT` &#42; `CONTAINER_NAME` &#42; `DAGS_FOLDER` &#42; `GCP_PROJECT` &#42; `GCS_BUCKET` &#42; `GKE_CLUSTER_NAME` &#42; `SQL_DATABASE` &#42; `SQL_INSTANCE` &#42; `SQL_PASSWORD` &#42; `SQL_PROJECT` &#42; `SQL_REGION` &#42; `SQL_USER` */
		envVariables: Option[Map[String, String]] = None,
	  /** Optional. The major version of Python used to run the Apache Airflow scheduler, worker, and webserver processes. Can be set to '2' or '3'. If not specified, the default is '3'. Cannot be updated. This field is only supported for Cloud Composer environments in versions composer-1.&#42;.&#42;-airflow-&#42;.&#42;.&#42;. Environments in newer versions always use Python major version 3. */
		pythonVersion: Option[String] = None,
	  /** Optional. The number of schedulers for Airflow. This field is supported for Cloud Composer environments in versions composer-1.&#42;.&#42;-airflow-2.&#42;.&#42;. */
		schedulerCount: Option[Int] = None,
	  /** Optional. The configuration for Cloud Data Lineage integration. */
		cloudDataLineageIntegration: Option[Schema.CloudDataLineageIntegration] = None,
	  /** Optional. Whether or not the web server uses custom plugins. If unspecified, the field defaults to `PLUGINS_ENABLED`. This field is supported for Cloud Composer environments in versions composer-3.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
		webServerPluginsMode: Option[Schema.SoftwareConfig.WebServerPluginsModeEnum] = None
	)
	
	case class CloudDataLineageIntegration(
	  /** Optional. Whether or not Cloud Data Lineage integration is enabled. */
		enabled: Option[Boolean] = None
	)
	
	case class NodeConfig(
	  /** Optional. The Compute Engine [zone](/compute/docs/regions-zones) in which to deploy the VMs used to run the Apache Airflow software, specified as a [relative resource name](/apis/design/resource_names#relative_resource_name). For example: "projects/{projectId}/zones/{zoneId}". This `location` must belong to the enclosing environment's project and location. If both this field and `nodeConfig.machineType` are specified, `nodeConfig.machineType` must belong to this `location`; if both are unspecified, the service will pick a zone in the Compute Engine region corresponding to the Cloud Composer location, and propagate that choice to both fields. If only one field (`location` or `nodeConfig.machineType`) is specified, the location information from the specified field will be propagated to the unspecified field. This field is supported for Cloud Composer environments in versions composer-1.&#42;.&#42;-airflow-&#42;.&#42;.&#42;. */
		location: Option[String] = None,
	  /** Optional. The Compute Engine [machine type](/compute/docs/machine-types) used for cluster instances, specified as a [relative resource name](/apis/design/resource_names#relative_resource_name). For example: "projects/{projectId}/zones/{zoneId}/machineTypes/{machineTypeId}". The `machineType` must belong to the enclosing environment's project and location. If both this field and `nodeConfig.location` are specified, this `machineType` must belong to the `nodeConfig.location`; if both are unspecified, the service will pick a zone in the Compute Engine region corresponding to the Cloud Composer location, and propagate that choice to both fields. If exactly one of this field and `nodeConfig.location` is specified, the location information from the specified field will be propagated to the unspecified field. The `machineTypeId` must not be a [shared-core machine type](/compute/docs/machine-types#sharedcore). If this field is unspecified, the `machineTypeId` defaults to "n1-standard-1". This field is supported for Cloud Composer environments in versions composer-1.&#42;.&#42;-airflow-&#42;.&#42;.&#42;. */
		machineType: Option[String] = None,
	  /** Optional. The Compute Engine network to be used for machine communications, specified as a [relative resource name](/apis/design/resource_names#relative_resource_name). For example: "projects/{projectId}/global/networks/{networkId}". If unspecified, the "default" network ID in the environment's project is used. If a [Custom Subnet Network](/vpc/docs/vpc#vpc_networks_and_subnets) is provided, `nodeConfig.subnetwork` must also be provided. For [Shared VPC](/vpc/docs/shared-vpc) subnetwork requirements, see `nodeConfig.subnetwork`. */
		network: Option[String] = None,
	  /** Optional. The Compute Engine subnetwork to be used for machine communications, specified as a [relative resource name](/apis/design/resource_names#relative_resource_name). For example: "projects/{projectId}/regions/{regionId}/subnetworks/{subnetworkId}" If a subnetwork is provided, `nodeConfig.network` must also be provided, and the subnetwork must belong to the enclosing environment's project and location. */
		subnetwork: Option[String] = None,
	  /** Optional. The disk size in GB used for node VMs. Minimum size is 30GB. If unspecified, defaults to 100GB. Cannot be updated. This field is supported for Cloud Composer environments in versions composer-1.&#42;.&#42;-airflow-&#42;.&#42;.&#42;. */
		diskSizeGb: Option[Int] = None,
	  /** Optional. The set of Google API scopes to be made available on all node VMs. If `oauth_scopes` is empty, defaults to ["https://www.googleapis.com/auth/cloud-platform"]. Cannot be updated. This field is supported for Cloud Composer environments in versions composer-1.&#42;.&#42;-airflow-&#42;.&#42;.&#42;. */
		oauthScopes: Option[List[String]] = None,
	  /** Optional. The Google Cloud Platform Service Account to be used by the node VMs. If a service account is not specified, the "default" Compute Engine service account is used. Cannot be updated. */
		serviceAccount: Option[String] = None,
	  /** Optional. The list of instance tags applied to all node VMs. Tags are used to identify valid sources or targets for network firewalls. Each tag within the list must comply with [RFC1035](https://www.ietf.org/rfc/rfc1035.txt). Cannot be updated. */
		tags: Option[List[String]] = None,
	  /** Optional. The configuration for controlling how IPs are allocated in the GKE cluster. */
		ipAllocationPolicy: Option[Schema.IPAllocationPolicy] = None,
	  /** Optional. Deploys 'ip-masq-agent' daemon set in the GKE cluster and defines nonMasqueradeCIDRs equals to pod IP range so IP masquerading is used for all destination addresses, except between pods traffic. See: https://cloud.google.com/kubernetes-engine/docs/how-to/ip-masquerade-agent */
		enableIpMasqAgent: Option[Boolean] = None,
	  /** Optional. Network Attachment that Cloud Composer environment is connected to, which provides connectivity with a user's VPC network. Takes precedence over network and subnetwork settings. If not provided, but network and subnetwork are defined during environment, it will be provisioned. If not provided and network and subnetwork are also empty, then connectivity to user's VPC network is disabled. Network attachment must be provided in format projects/{project}/regions/{region}/networkAttachments/{networkAttachment}. This field is supported for Cloud Composer environments in versions composer-3.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
		composerNetworkAttachment: Option[String] = None,
	  /** Optional. The IP range in CIDR notation to use internally by Cloud Composer. IP addresses are not reserved - and the same range can be used by multiple Cloud Composer environments. In case of overlap, IPs from this range will not be accessible in the user's VPC network. Cannot be updated. If not specified, the default value of '100.64.128.0/20' is used. This field is supported for Cloud Composer environments in versions composer-3.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
		composerInternalIpv4CidrBlock: Option[String] = None
	)
	
	case class IPAllocationPolicy(
	  /** Optional. Whether or not to enable Alias IPs in the GKE cluster. If `true`, a VPC-native cluster is created. This field is only supported for Cloud Composer environments in versions composer-1.&#42;.&#42;-airflow-&#42;.&#42;.&#42;. Environments in newer versions always use VPC-native GKE clusters. */
		useIpAliases: Option[Boolean] = None,
	  /** Optional. The name of the GKE cluster's secondary range used to allocate IP addresses to pods. For Cloud Composer environments in versions composer-1.&#42;.&#42;-airflow-&#42;.&#42;.&#42;, this field is applicable only when `use_ip_aliases` is true. */
		clusterSecondaryRangeName: Option[String] = None,
	  /** Optional. The IP address range used to allocate IP addresses to pods in the GKE cluster. For Cloud Composer environments in versions composer-1.&#42;.&#42;-airflow-&#42;.&#42;.&#42;, this field is applicable only when `use_ip_aliases` is true. Set to blank to have GKE choose a range with the default size. Set to /netmask (e.g. `/14`) to have GKE choose a range with a specific netmask. Set to a [CIDR](https://en.wikipedia.org/wiki/Classless_Inter-Domain_Routing) notation (e.g. `10.96.0.0/14`) from the RFC-1918 private networks (e.g. `10.0.0.0/8`, `172.16.0.0/12`, `192.168.0.0/16`) to pick a specific range to use. */
		clusterIpv4CidrBlock: Option[String] = None,
	  /** Optional. The name of the services' secondary range used to allocate IP addresses to the GKE cluster. For Cloud Composer environments in versions composer-1.&#42;.&#42;-airflow-&#42;.&#42;.&#42;, this field is applicable only when `use_ip_aliases` is true. */
		servicesSecondaryRangeName: Option[String] = None,
	  /** Optional. The IP address range of the services IP addresses in this GKE cluster. For Cloud Composer environments in versions composer-1.&#42;.&#42;-airflow-&#42;.&#42;.&#42;, this field is applicable only when `use_ip_aliases` is true. Set to blank to have GKE choose a range with the default size. Set to /netmask (e.g. `/14`) to have GKE choose a range with a specific netmask. Set to a [CIDR](https://en.wikipedia.org/wiki/Classless_Inter-Domain_Routing) notation (e.g. `10.96.0.0/14`) from the RFC-1918 private networks (e.g. `10.0.0.0/8`, `172.16.0.0/12`, `192.168.0.0/16`) to pick a specific range to use. */
		servicesIpv4CidrBlock: Option[String] = None
	)
	
	case class PrivateEnvironmentConfig(
	  /** Optional. If `true`, a Private IP Cloud Composer environment is created. If this field is set to true, `IPAllocationPolicy.use_ip_aliases` must be set to true for Cloud Composer environments in versions composer-1.&#42;.&#42;-airflow-&#42;.&#42;.&#42;. */
		enablePrivateEnvironment: Option[Boolean] = None,
	  /** Optional. If `true`, builds performed during operations that install Python packages have only private connectivity to Google services (including Artifact Registry) and VPC network (if either `NodeConfig.network` and `NodeConfig.subnetwork` fields or `NodeConfig.composer_network_attachment` field are specified). If `false`, the builds also have access to the internet. This field is supported for Cloud Composer environments in versions composer-3.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
		enablePrivateBuildsOnly: Option[Boolean] = None,
	  /** Optional. Configuration for the private GKE cluster for a Private IP Cloud Composer environment. */
		privateClusterConfig: Option[Schema.PrivateClusterConfig] = None,
	  /** Optional. The CIDR block from which IP range for web server will be reserved. Needs to be disjoint from `private_cluster_config.master_ipv4_cidr_block` and `cloud_sql_ipv4_cidr_block`. This field is supported for Cloud Composer environments in versions composer-1.&#42;.&#42;-airflow-&#42;.&#42;.&#42;. */
		webServerIpv4CidrBlock: Option[String] = None,
	  /** Optional. The CIDR block from which IP range in tenant project will be reserved for Cloud SQL. Needs to be disjoint from `web_server_ipv4_cidr_block`. */
		cloudSqlIpv4CidrBlock: Option[String] = None,
	  /** Output only. The IP range reserved for the tenant project's App Engine VMs. This field is supported for Cloud Composer environments in versions composer-1.&#42;.&#42;-airflow-&#42;.&#42;.&#42;. */
		webServerIpv4ReservedRange: Option[String] = None,
	  /** Optional. The CIDR block from which IP range for Cloud Composer Network in tenant project will be reserved. Needs to be disjoint from private_cluster_config.master_ipv4_cidr_block and cloud_sql_ipv4_cidr_block. This field is supported for Cloud Composer environments in versions composer-2.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
		cloudComposerNetworkIpv4CidrBlock: Option[String] = None,
	  /** Output only. The IP range reserved for the tenant project's Cloud Composer network. This field is supported for Cloud Composer environments in versions composer-2.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
		cloudComposerNetworkIpv4ReservedRange: Option[String] = None,
	  /** Optional. When enabled, IPs from public (non-RFC1918) ranges can be used for `IPAllocationPolicy.cluster_ipv4_cidr_block` and `IPAllocationPolicy.service_ipv4_cidr_block`. */
		enablePrivatelyUsedPublicIps: Option[Boolean] = None,
	  /** Optional. When specified, the environment will use Private Service Connect instead of VPC peerings to connect to Cloud SQL in the Tenant Project, and the PSC endpoint in the Customer Project will use an IP address from this subnetwork. */
		cloudComposerConnectionSubnetwork: Option[String] = None,
	  /** Optional. Configuration for the network connections configuration in the environment. */
		networkingConfig: Option[Schema.NetworkingConfig] = None
	)
	
	case class PrivateClusterConfig(
	  /** Optional. If `true`, access to the public endpoint of the GKE cluster is denied. */
		enablePrivateEndpoint: Option[Boolean] = None,
	  /** Optional. The CIDR block from which IPv4 range for GKE master will be reserved. If left blank, the default value of '172.16.0.0/23' is used. */
		masterIpv4CidrBlock: Option[String] = None,
	  /** Output only. The IP range in CIDR notation to use for the hosted master network. This range is used for assigning internal IP addresses to the GKE cluster master or set of masters and to the internal load balancer virtual IP. This range must not overlap with any other ranges in use within the cluster's network. */
		masterIpv4ReservedRange: Option[String] = None
	)
	
	object NetworkingConfig {
		enum ConnectionTypeEnum extends Enum[ConnectionTypeEnum] { case CONNECTION_TYPE_UNSPECIFIED, VPC_PEERING, PRIVATE_SERVICE_CONNECT }
	}
	case class NetworkingConfig(
	  /** Optional. Indicates the user requested specifc connection type between Tenant and Customer projects. You cannot set networking connection type in public IP environment. */
		connectionType: Option[Schema.NetworkingConfig.ConnectionTypeEnum] = None
	)
	
	case class WebServerNetworkAccessControl(
	  /** A collection of allowed IP ranges with descriptions. */
		allowedIpRanges: Option[List[Schema.AllowedIpRange]] = None
	)
	
	case class AllowedIpRange(
	  /** IP address or range, defined using CIDR notation, of requests that this rule applies to. Examples: `192.168.1.1` or `192.168.0.0/16` or `2001:db8::/32` or `2001:0db8:0000:0042:0000:8a2e:0370:7334`. IP range prefixes should be properly truncated. For example, `1.2.3.4/24` should be truncated to `1.2.3.0/24`. Similarly, for IPv6, `2001:db8::1/32` should be truncated to `2001:db8::/32`. */
		value: Option[String] = None,
	  /** Optional. User-provided description. It must contain at most 300 characters. */
		description: Option[String] = None
	)
	
	case class DatabaseConfig(
	  /** Optional. Cloud SQL machine type used by Airflow database. It has to be one of: db-n1-standard-2, db-n1-standard-4, db-n1-standard-8 or db-n1-standard-16. If not specified, db-n1-standard-2 will be used. Supported for Cloud Composer environments in versions composer-1.&#42;.&#42;-airflow-&#42;.&#42;.&#42;. */
		machineType: Option[String] = None,
	  /** Optional. The Compute Engine zone where the Airflow database is created. If zone is provided, it must be in the region selected for the environment. If zone is not provided, a zone is automatically selected. The zone can only be set during environment creation. Supported for Cloud Composer environments in versions composer-2.&#42;.&#42;-airflow-&#42;.&#42;.&#42;. */
		zone: Option[String] = None
	)
	
	case class WebServerConfig(
	  /** Optional. Machine type on which Airflow web server is running. It has to be one of: composer-n1-webserver-2, composer-n1-webserver-4 or composer-n1-webserver-8. If not specified, composer-n1-webserver-2 will be used. Value custom is returned only in response, if Airflow web server parameters were manually changed to a non-standard values. */
		machineType: Option[String] = None
	)
	
	case class EncryptionConfig(
	  /** Optional. Customer-managed Encryption Key available through Google's Key Management Service. Cannot be updated. If not specified, Google-managed key will be used. */
		kmsKeyName: Option[String] = None
	)
	
	case class MaintenanceWindow(
	  /** Required. Start time of the first recurrence of the maintenance window. */
		startTime: Option[String] = None,
	  /** Required. Maintenance window end time. It is used only to calculate the duration of the maintenance window. The value for end-time must be in the future, relative to `start_time`. */
		endTime: Option[String] = None,
	  /** Required. Maintenance window recurrence. Format is a subset of [RFC-5545](https://tools.ietf.org/html/rfc5545) `RRULE`. The only allowed values for `FREQ` field are `FREQ=DAILY` and `FREQ=WEEKLY;BYDAY=...` Example values: `FREQ=WEEKLY;BYDAY=TU,WE`, `FREQ=DAILY`. */
		recurrence: Option[String] = None
	)
	
	case class WorkloadsConfig(
	  /** Optional. Resources used by Airflow schedulers. */
		scheduler: Option[Schema.SchedulerResource] = None,
	  /** Optional. Resources used by Airflow web server. */
		webServer: Option[Schema.WebServerResource] = None,
	  /** Optional. Resources used by Airflow workers. */
		worker: Option[Schema.WorkerResource] = None,
	  /** Optional. Resources used by Airflow triggerers. */
		triggerer: Option[Schema.TriggererResource] = None,
	  /** Optional. Resources used by Airflow DAG processors. This field is supported for Cloud Composer environments in versions composer-3.&#42;.&#42;-airflow-&#42;.&#42;.&#42; and newer. */
		dagProcessor: Option[Schema.DagProcessorResource] = None
	)
	
	case class SchedulerResource(
	  /** Optional. CPU request and limit for a single Airflow scheduler replica. */
		cpu: Option[BigDecimal] = None,
	  /** Optional. Memory (GB) request and limit for a single Airflow scheduler replica. */
		memoryGb: Option[BigDecimal] = None,
	  /** Optional. Storage (GB) request and limit for a single Airflow scheduler replica. */
		storageGb: Option[BigDecimal] = None,
	  /** Optional. The number of schedulers. */
		count: Option[Int] = None
	)
	
	case class WebServerResource(
	  /** Optional. CPU request and limit for Airflow web server. */
		cpu: Option[BigDecimal] = None,
	  /** Optional. Memory (GB) request and limit for Airflow web server. */
		memoryGb: Option[BigDecimal] = None,
	  /** Optional. Storage (GB) request and limit for Airflow web server. */
		storageGb: Option[BigDecimal] = None
	)
	
	case class WorkerResource(
	  /** Optional. CPU request and limit for a single Airflow worker replica. */
		cpu: Option[BigDecimal] = None,
	  /** Optional. Memory (GB) request and limit for a single Airflow worker replica. */
		memoryGb: Option[BigDecimal] = None,
	  /** Optional. Storage (GB) request and limit for a single Airflow worker replica. */
		storageGb: Option[BigDecimal] = None,
	  /** Optional. Minimum number of workers for autoscaling. */
		minCount: Option[Int] = None,
	  /** Optional. Maximum number of workers for autoscaling. */
		maxCount: Option[Int] = None
	)
	
	case class TriggererResource(
	  /** Optional. The number of triggerers. */
		count: Option[Int] = None,
	  /** Optional. CPU request and limit for a single Airflow triggerer replica. */
		cpu: Option[BigDecimal] = None,
	  /** Optional. Memory (GB) request and limit for a single Airflow triggerer replica. */
		memoryGb: Option[BigDecimal] = None
	)
	
	case class DagProcessorResource(
	  /** Optional. CPU request and limit for a single Airflow DAG processor replica. */
		cpu: Option[BigDecimal] = None,
	  /** Optional. Memory (GB) request and limit for a single Airflow DAG processor replica. */
		memoryGb: Option[BigDecimal] = None,
	  /** Optional. Storage (GB) request and limit for a single Airflow DAG processor replica. */
		storageGb: Option[BigDecimal] = None,
	  /** Optional. The number of DAG processors. If not provided or set to 0, a single DAG processor instance will be created. */
		count: Option[Int] = None
	)
	
	case class MasterAuthorizedNetworksConfig(
	  /** Whether or not master authorized networks feature is enabled. */
		enabled: Option[Boolean] = None,
	  /** Up to 50 external networks that could access Kubernetes master through HTTPS. */
		cidrBlocks: Option[List[Schema.CidrBlock]] = None
	)
	
	case class CidrBlock(
	  /** User-defined name that identifies the CIDR block. */
		displayName: Option[String] = None,
	  /** CIDR block that must be specified in CIDR notation. */
		cidrBlock: Option[String] = None
	)
	
	case class RecoveryConfig(
	  /** Optional. The configuration for scheduled snapshot creation mechanism. */
		scheduledSnapshotsConfig: Option[Schema.ScheduledSnapshotsConfig] = None
	)
	
	case class ScheduledSnapshotsConfig(
	  /** Optional. Whether scheduled snapshots creation is enabled. */
		enabled: Option[Boolean] = None,
	  /** Optional. The Cloud Storage location for storing automatically created snapshots. */
		snapshotLocation: Option[String] = None,
	  /** Optional. The cron expression representing the time when snapshots creation mechanism runs. This field is subject to additional validation around frequency of execution. */
		snapshotCreationSchedule: Option[String] = None,
	  /** Optional. Time zone that sets the context to interpret snapshot_creation_schedule. */
		timeZone: Option[String] = None
	)
	
	case class DataRetentionConfig(
	  /** Optional. The retention policy for airflow metadata database. */
		airflowMetadataRetentionConfig: Option[Schema.AirflowMetadataRetentionPolicyConfig] = None,
	  /** Optional. The configuration settings for task logs retention */
		taskLogsRetentionConfig: Option[Schema.TaskLogsRetentionConfig] = None
	)
	
	object AirflowMetadataRetentionPolicyConfig {
		enum RetentionModeEnum extends Enum[RetentionModeEnum] { case RETENTION_MODE_UNSPECIFIED, RETENTION_MODE_ENABLED, RETENTION_MODE_DISABLED }
	}
	case class AirflowMetadataRetentionPolicyConfig(
	  /** Optional. Retention can be either enabled or disabled. */
		retentionMode: Option[Schema.AirflowMetadataRetentionPolicyConfig.RetentionModeEnum] = None,
	  /** Optional. How many days data should be retained for. */
		retentionDays: Option[Int] = None
	)
	
	object TaskLogsRetentionConfig {
		enum StorageModeEnum extends Enum[StorageModeEnum] { case TASK_LOGS_STORAGE_MODE_UNSPECIFIED, CLOUD_LOGGING_AND_CLOUD_STORAGE, CLOUD_LOGGING_ONLY }
	}
	case class TaskLogsRetentionConfig(
	  /** Optional. The mode of storage for Airflow workers task logs. */
		storageMode: Option[Schema.TaskLogsRetentionConfig.StorageModeEnum] = None
	)
	
	case class StorageConfig(
	  /** Optional. The name of the Cloud Storage bucket used by the environment. No `gs://` prefix. */
		bucket: Option[String] = None
	)
	
	case class ListEnvironmentsResponse(
	  /** The list of environments returned by a ListEnvironmentsRequest. */
		environments: Option[List[Schema.Environment]] = None,
	  /** The page token used to query for the next page if one exists. */
		nextPageToken: Option[String] = None
	)
	
	case class ExecuteAirflowCommandRequest(
	  /** Airflow command. */
		command: Option[String] = None,
	  /** Airflow subcommand. */
		subcommand: Option[String] = None,
	  /** Parameters for the Airflow command/subcommand as an array of arguments. It may contain positional arguments like `["my-dag-id"]`, key-value parameters like `["--foo=bar"]` or `["--foo","bar"]`, or other flags like `["-f"]`. */
		parameters: Option[List[String]] = None
	)
	
	case class ExecuteAirflowCommandResponse(
	  /** The unique ID of the command execution for polling. */
		executionId: Option[String] = None,
	  /** The name of the pod where the command is executed. */
		pod: Option[String] = None,
	  /** The namespace of the pod where the command is executed. */
		podNamespace: Option[String] = None,
	  /** Error message. Empty if there was no error. */
		error: Option[String] = None
	)
	
	case class StopAirflowCommandRequest(
	  /** The unique ID of the command execution. */
		executionId: Option[String] = None,
	  /** The name of the pod where the command is executed. */
		pod: Option[String] = None,
	  /** The namespace of the pod where the command is executed. */
		podNamespace: Option[String] = None,
	  /** If true, the execution is terminated forcefully (SIGKILL). If false, the execution is stopped gracefully, giving it time for cleanup. */
		force: Option[Boolean] = None
	)
	
	case class StopAirflowCommandResponse(
	  /** Whether the execution is still running. */
		isDone: Option[Boolean] = None,
	  /** Output message from stopping execution request. */
		output: Option[List[String]] = None
	)
	
	case class PollAirflowCommandRequest(
	  /** The unique ID of the command execution. */
		executionId: Option[String] = None,
	  /** The name of the pod where the command is executed. */
		pod: Option[String] = None,
	  /** The namespace of the pod where the command is executed. */
		podNamespace: Option[String] = None,
	  /** Line number from which new logs should be fetched. */
		nextLineNumber: Option[Int] = None
	)
	
	case class PollAirflowCommandResponse(
	  /** Output from the command execution. It may not contain the full output and the caller may need to poll for more lines. */
		output: Option[List[Schema.Line]] = None,
	  /** Whether the command execution has finished and there is no more output. */
		outputEnd: Option[Boolean] = None,
	  /** The result exit status of the command. */
		exitInfo: Option[Schema.ExitInfo] = None
	)
	
	case class Line(
	  /** Number of the line. */
		lineNumber: Option[Int] = None,
	  /** Text content of the log line. */
		content: Option[String] = None
	)
	
	case class ExitInfo(
	  /** The exit code from the command execution. */
		exitCode: Option[Int] = None,
	  /** Error message. Empty if there was no error. */
		error: Option[String] = None
	)
	
	case class ListWorkloadsResponse(
	  /** The list of environment workloads. */
		workloads: Option[List[Schema.ComposerWorkload]] = None,
	  /** The page token used to query for the next page if one exists. */
		nextPageToken: Option[String] = None
	)
	
	object ComposerWorkload {
		enum TypeEnum extends Enum[TypeEnum] { case COMPOSER_WORKLOAD_TYPE_UNSPECIFIED, CELERY_WORKER, KUBERNETES_WORKER, KUBERNETES_OPERATOR_POD, SCHEDULER, DAG_PROCESSOR, TRIGGERER, WEB_SERVER, REDIS }
	}
	case class ComposerWorkload(
	  /** Name of a workload. */
		name: Option[String] = None,
	  /** Type of a workload. */
		`type`: Option[Schema.ComposerWorkload.TypeEnum] = None,
	  /** Output only. Status of a workload. */
		status: Option[Schema.ComposerWorkloadStatus] = None
	)
	
	object ComposerWorkloadStatus {
		enum StateEnum extends Enum[StateEnum] { case COMPOSER_WORKLOAD_STATE_UNSPECIFIED, PENDING, OK, WARNING, ERROR, SUCCEEDED, FAILED }
	}
	case class ComposerWorkloadStatus(
	  /** Output only. Workload state. */
		state: Option[Schema.ComposerWorkloadStatus.StateEnum] = None,
	  /** Output only. Text to provide more descriptive status. */
		statusMessage: Option[String] = None,
	  /** Output only. Detailed message of the status. */
		detailedStatusMessage: Option[String] = None
	)
	
	case class CheckUpgradeRequest(
	  /** Optional. The version of the software running in the environment. This encapsulates both the version of Cloud Composer functionality and the version of Apache Airflow. It must match the regular expression `composer-([0-9]+(\.[0-9]+\.[0-9]+(-preview\.[0-9]+)?)?|latest)-airflow-([0-9]+(\.[0-9]+(\.[0-9]+)?)?)`. When used as input, the server also checks if the provided version is supported and denies the request for an unsupported version. The Cloud Composer portion of the image version is a full [semantic version](https://semver.org), or an alias in the form of major version number or `latest`. When an alias is provided, the server replaces it with the current Cloud Composer version that satisfies the alias. The Apache Airflow portion of the image version is a full semantic version that points to one of the supported Apache Airflow versions, or an alias in the form of only major or major.minor versions specified. When an alias is provided, the server replaces it with the latest Apache Airflow version that satisfies the alias and is supported in the given Cloud Composer version. In all cases, the resolved image version is stored in the same field. See also [version list](/composer/docs/concepts/versioning/composer-versions) and [versioning overview](/composer/docs/concepts/versioning/composer-versioning-overview). */
		imageVersion: Option[String] = None
	)
	
	case class UserWorkloadsSecret(
	  /** Identifier. The resource name of the Secret, in the form: "projects/{projectId}/locations/{locationId}/environments/{environmentId}/userWorkloadsSecrets/{userWorkloadsSecretId}" */
		name: Option[String] = None,
	  /** Optional. The "data" field of Kubernetes Secret, organized in key-value pairs, which can contain sensitive values such as a password, a token, or a key. The values for all keys have to be base64-encoded strings. For details see: https://kubernetes.io/docs/concepts/configuration/secret/ Example: { "example": "ZXhhbXBsZV92YWx1ZQ==", "another-example": "YW5vdGhlcl9leGFtcGxlX3ZhbHVl" } */
		data: Option[Map[String, String]] = None
	)
	
	case class ListUserWorkloadsSecretsResponse(
	  /** The list of Secrets returned by a ListUserWorkloadsSecretsRequest. */
		userWorkloadsSecrets: Option[List[Schema.UserWorkloadsSecret]] = None,
	  /** The page token used to query for the next page if one exists. */
		nextPageToken: Option[String] = None
	)
	
	case class UserWorkloadsConfigMap(
	  /** Identifier. The resource name of the ConfigMap, in the form: "projects/{projectId}/locations/{locationId}/environments/{environmentId}/userWorkloadsConfigMaps/{userWorkloadsConfigMapId}" */
		name: Option[String] = None,
	  /** Optional. The "data" field of Kubernetes ConfigMap, organized in key-value pairs. For details see: https://kubernetes.io/docs/concepts/configuration/configmap/ Example: { "example_key": "example_value", "another_key": "another_value" } */
		data: Option[Map[String, String]] = None
	)
	
	case class ListUserWorkloadsConfigMapsResponse(
	  /** The list of ConfigMaps returned by a ListUserWorkloadsConfigMapsRequest. */
		userWorkloadsConfigMaps: Option[List[Schema.UserWorkloadsConfigMap]] = None,
	  /** The page token used to query for the next page if one exists. */
		nextPageToken: Option[String] = None
	)
	
	case class SaveSnapshotRequest(
	  /** Location in a Cloud Storage where the snapshot is going to be stored, e.g.: "gs://my-bucket/snapshots". */
		snapshotLocation: Option[String] = None
	)
	
	case class LoadSnapshotRequest(
	  /** A Cloud Storage path to a snapshot to load, e.g.: "gs://my-bucket/snapshots/project_location_environment_timestamp". */
		snapshotPath: Option[String] = None,
	  /** Whether or not to skip installing Pypi packages when loading the environment's state. */
		skipPypiPackagesInstallation: Option[Boolean] = None,
	  /** Whether or not to skip setting environment variables when loading the environment's state. */
		skipEnvironmentVariablesSetting: Option[Boolean] = None,
	  /** Whether or not to skip setting Airflow overrides when loading the environment's state. */
		skipAirflowOverridesSetting: Option[Boolean] = None,
	  /** Whether or not to skip copying Cloud Storage data when loading the environment's state. */
		skipGcsDataCopying: Option[Boolean] = None
	)
	
	case class DatabaseFailoverRequest(
	
	)
	
	case class FetchDatabasePropertiesResponse(
	  /** The Compute Engine zone that the instance is currently serving from. */
		primaryGceZone: Option[String] = None,
	  /** The Compute Engine zone that the failover instance is currently serving from for a regional Cloud SQL instance. */
		secondaryGceZone: Option[String] = None,
	  /** The availability status of the failover replica. A false status indicates that the failover replica is out of sync. The primary instance can only fail over to the failover replica when the status is true. */
		isFailoverReplicaAvailable: Option[Boolean] = None
	)
	
	case class ListImageVersionsResponse(
	  /** The list of supported ImageVersions in a location. */
		imageVersions: Option[List[Schema.ImageVersion]] = None,
	  /** The page token used to query for the next page if one exists. */
		nextPageToken: Option[String] = None
	)
	
	case class ImageVersion(
	  /** The string identifier of the ImageVersion, in the form: "composer-x.y.z-airflow-a.b.c" */
		imageVersionId: Option[String] = None,
	  /** Whether this is the default ImageVersion used by Composer during environment creation if no input ImageVersion is specified. */
		isDefault: Option[Boolean] = None,
	  /** supported python versions */
		supportedPythonVersions: Option[List[String]] = None,
	  /** The date of the version release. */
		releaseDate: Option[Schema.Date] = None,
	  /** Whether it is impossible to create an environment with the image version. */
		creationDisabled: Option[Boolean] = None,
	  /** Whether it is impossible to upgrade an environment running with the image version. */
		upgradeDisabled: Option[Boolean] = None
	)
	
	case class Date(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	object OperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, RUNNING, SUCCEEDED, SUCCESSFUL, FAILED }
		enum OperationTypeEnum extends Enum[OperationTypeEnum] { case TYPE_UNSPECIFIED, CREATE, DELETE, UPDATE, CHECK, SAVE_SNAPSHOT, LOAD_SNAPSHOT, DATABASE_FAILOVER }
	}
	case class OperationMetadata(
	  /** Output only. The current operation state. */
		state: Option[Schema.OperationMetadata.StateEnum] = None,
	  /** Output only. The type of operation being performed. */
		operationType: Option[Schema.OperationMetadata.OperationTypeEnum] = None,
	  /** Output only. The resource being operated on, as a [relative resource name]( /apis/design/resource_names#relative_resource_name). */
		resource: Option[String] = None,
	  /** Output only. The UUID of the resource being operated on. */
		resourceUuid: Option[String] = None,
	  /** Output only. The time the operation was submitted to the server. */
		createTime: Option[String] = None,
	  /** Output only. The time when the operation terminated, regardless of its success. This field is unset if the operation is still ongoing. */
		endTime: Option[String] = None
	)
	
	object CheckUpgradeResponse {
		enum ContainsPypiModulesConflictEnum extends Enum[ContainsPypiModulesConflictEnum] { case CONFLICT_RESULT_UNSPECIFIED, CONFLICT, NO_CONFLICT }
	}
	case class CheckUpgradeResponse(
	  /** Output only. Url for a docker build log of an upgraded image. */
		buildLogUri: Option[String] = None,
	  /** Output only. Whether build has succeeded or failed on modules conflicts. */
		containsPypiModulesConflict: Option[Schema.CheckUpgradeResponse.ContainsPypiModulesConflictEnum] = None,
	  /** Output only. Extract from a docker image build log containing information about pypi modules conflicts. */
		pypiConflictBuildLogExtract: Option[String] = None,
	  /** Composer image for which the build was happening. */
		imageVersion: Option[String] = None,
	  /** Pypi dependencies specified in the environment configuration, at the time when the build was triggered. */
		pypiDependencies: Option[Map[String, String]] = None
	)
	
	case class SaveSnapshotResponse(
	  /** The fully-resolved Cloud Storage path of the created snapshot, e.g.: "gs://my-bucket/snapshots/project_location_environment_timestamp". This field is populated only if the snapshot creation was successful. */
		snapshotPath: Option[String] = None
	)
	
	case class LoadSnapshotResponse(
	
	)
	
	case class DatabaseFailoverResponse(
	
	)
}
