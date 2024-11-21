package com.boresjo.play.api.google.gkehub

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
	
	case class CancelOperationRequest(
	
	)
	
	case class Empty(
	
	)
	
	case class MembershipFeature(
	  /** Output only. The resource name of the membershipFeature, in the format: `projects/{project}/locations/{location}/memberships/{membership}/features/{feature}`. Note that `membershipFeatures` is shortened to `features` in the resource name. (see http://go/aip/122#collection-identifiers) */
		name: Option[String] = None,
	  /** GCP labels for this MembershipFeature. */
		labels: Option[Map[String, String]] = None,
	  /** Reference information for a FeatureConfig applied on the MembershipFeature. */
		featureConfigRef: Option[Schema.FeatureConfigRef] = None,
	  /** Spec of this membershipFeature. */
		spec: Option[Schema.FeatureSpec] = None,
	  /** Output only. State of the this membershipFeature. */
		state: Option[Schema.FeatureState] = None,
	  /** Output only. Lifecycle information of the resource itself. */
		lifecycleState: Option[Schema.LifecycleState] = None,
	  /** Output only. When the MembershipFeature resource was created. */
		createTime: Option[String] = None,
	  /** Output only. When the MembershipFeature resource was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. When the MembershipFeature resource was deleted. */
		deleteTime: Option[String] = None
	)
	
	case class FeatureConfigRef(
	  /** Input only. Resource name of FeatureConfig, in the format: `projects/{project}/locations/global/featureConfigs/{feature_config}`. */
		config: Option[String] = None,
	  /** Output only. An id that uniquely identify a FeatureConfig object. */
		uuid: Option[String] = None,
	  /** Output only. When the FeatureConfig was last applied and copied to FeatureSpec. */
		configUpdateTime: Option[String] = None
	)
	
	case class FeatureSpec(
	  /** Workloadcertificate-specific FeatureSpec. */
		workloadcertificate: Option[Schema.WorkloadCertificateSpec] = None,
	  /** Cloudbuild-specific FeatureSpec. */
		cloudbuild: Option[Schema.CloudBuildSpec] = None,
	  /** Policycontroller-specific FeatureSpec. */
		policycontroller: Option[Schema.PolicyControllerSpec] = None,
	  /** IdentityService FeatureSpec. */
		identityservice: Option[Schema.IdentityServiceSpec] = None,
	  /** ServiceMesh Feature Spec. */
		servicemesh: Option[Schema.ServiceMeshSpec] = None,
	  /** Config Management FeatureSpec. */
		configmanagement: Option[Schema.ConfigManagementSpec] = None,
	  /** Whether this per-Feature spec was inherited from a fleet-level default. This field can be updated by users by either overriding a Feature config (updated to USER implicitly) or setting to FLEET explicitly. */
		origin: Option[Schema.Origin] = None
	)
	
	object WorkloadCertificateSpec {
		enum CertificateManagementEnum extends Enum[CertificateManagementEnum] { case CERTIFICATE_MANAGEMENT_UNSPECIFIED, DISABLED, ENABLED }
	}
	case class WorkloadCertificateSpec(
	  /** CertificateManagement specifies workload certificate management. */
		certificateManagement: Option[Schema.WorkloadCertificateSpec.CertificateManagementEnum] = None
	)
	
	object CloudBuildSpec {
		enum SecurityPolicyEnum extends Enum[SecurityPolicyEnum] { case SECURITY_POLICY_UNSPECIFIED, NON_PRIVILEGED, PRIVILEGED }
	}
	case class CloudBuildSpec(
	  /** Version of the cloud build software on the cluster. */
		version: Option[String] = None,
	  /** Whether it is allowed to run the privileged builds on the cluster or not. */
		securityPolicy: Option[Schema.CloudBuildSpec.SecurityPolicyEnum] = None
	)
	
	case class PolicyControllerSpec(
	  /** Policy Controller configuration for the cluster. */
		policyControllerHubConfig: Option[Schema.PolicyControllerHubConfig] = None,
	  /** Version of Policy Controller installed. */
		version: Option[String] = None
	)
	
	object PolicyControllerHubConfig {
		enum InstallSpecEnum extends Enum[InstallSpecEnum] { case INSTALL_SPEC_UNSPECIFIED, INSTALL_SPEC_NOT_INSTALLED, INSTALL_SPEC_ENABLED, INSTALL_SPEC_SUSPENDED, INSTALL_SPEC_DETACHED }
	}
	case class PolicyControllerHubConfig(
	  /** The install_spec represents the intended state specified by the latest request that mutated install_spec in the feature spec, not the lifecycle state of the feature observed by the Hub feature controller that is reported in the feature state. */
		installSpec: Option[Schema.PolicyControllerHubConfig.InstallSpecEnum] = None,
	  /** Sets the interval for Policy Controller Audit Scans (in seconds). When set to 0, this disables audit functionality altogether. */
		auditIntervalSeconds: Option[String] = None,
	  /** The set of namespaces that are excluded from Policy Controller checks. Namespaces do not need to currently exist on the cluster. */
		exemptableNamespaces: Option[List[String]] = None,
	  /** Enables the ability to use Constraint Templates that reference to objects other than the object currently being evaluated. */
		referentialRulesEnabled: Option[Boolean] = None,
	  /** Logs all denies and dry run failures. */
		logDeniesEnabled: Option[Boolean] = None,
	  /** Enables the ability to mutate resources using Policy Controller. */
		mutationEnabled: Option[Boolean] = None,
	  /** Monitoring specifies the configuration of monitoring. */
		monitoring: Option[Schema.PolicyControllerMonitoringConfig] = None,
	  /** Specifies the desired policy content on the cluster */
		policyContent: Option[Schema.PolicyControllerPolicyContentSpec] = None,
	  /** The maximum number of audit violations to be stored in a constraint. If not set, the internal default (currently 20) will be used. */
		constraintViolationLimit: Option[String] = None,
	  /** Map of deployment configs to deployments (“admission”, “audit”, “mutation”). */
		deploymentConfigs: Option[Map[String, Schema.PolicyControllerPolicyControllerDeploymentConfig]] = None
	)
	
	object PolicyControllerMonitoringConfig {
		enum BackendsEnum extends Enum[BackendsEnum] { case MONITORING_BACKEND_UNSPECIFIED, PROMETHEUS, CLOUD_MONITORING }
	}
	case class PolicyControllerMonitoringConfig(
	  /** Specifies the list of backends Policy Controller will export to. An empty list would effectively disable metrics export. */
		backends: Option[List[Schema.PolicyControllerMonitoringConfig.BackendsEnum]] = None
	)
	
	case class PolicyControllerPolicyContentSpec(
	  /** map of bundle name to BundleInstallSpec. The bundle name maps to the `bundleName` key in the `policycontroller.gke.io/constraintData` annotation on a constraint. */
		bundles: Option[Map[String, Schema.PolicyControllerBundleInstallSpec]] = None,
	  /** Configures the installation of the Template Library. */
		templateLibrary: Option[Schema.PolicyControllerTemplateLibraryConfig] = None
	)
	
	case class PolicyControllerBundleInstallSpec(
	  /** the set of namespaces to be exempted from the bundle */
		exemptedNamespaces: Option[List[String]] = None
	)
	
	object PolicyControllerTemplateLibraryConfig {
		enum InstallationEnum extends Enum[InstallationEnum] { case INSTALLATION_UNSPECIFIED, NOT_INSTALLED, ALL }
	}
	case class PolicyControllerTemplateLibraryConfig(
	  /** Configures the manner in which the template library is installed on the cluster. */
		installation: Option[Schema.PolicyControllerTemplateLibraryConfig.InstallationEnum] = None
	)
	
	object PolicyControllerPolicyControllerDeploymentConfig {
		enum PodAffinityEnum extends Enum[PodAffinityEnum] { case AFFINITY_UNSPECIFIED, NO_AFFINITY, ANTI_AFFINITY }
	}
	case class PolicyControllerPolicyControllerDeploymentConfig(
	  /** Pod replica count. */
		replicaCount: Option[String] = None,
	  /** Container resource requirements. */
		containerResources: Option[Schema.PolicyControllerResourceRequirements] = None,
	  /** Pod anti-affinity enablement. Deprecated: use `pod_affinity` instead. */
		podAntiAffinity: Option[Boolean] = None,
	  /** Pod tolerations of node taints. */
		podTolerations: Option[List[Schema.PolicyControllerToleration]] = None,
	  /** Pod affinity configuration. */
		podAffinity: Option[Schema.PolicyControllerPolicyControllerDeploymentConfig.PodAffinityEnum] = None
	)
	
	case class PolicyControllerResourceRequirements(
	  /** Limits describes the maximum amount of compute resources allowed for use by the running container. */
		limits: Option[Schema.PolicyControllerResourceList] = None,
	  /** Requests describes the amount of compute resources reserved for the container by the kube-scheduler. */
		requests: Option[Schema.PolicyControllerResourceList] = None
	)
	
	case class PolicyControllerResourceList(
	  /** Memory requirement expressed in Kubernetes resource units. */
		memory: Option[String] = None,
	  /** CPU requirement expressed in Kubernetes resource units. */
		cpu: Option[String] = None
	)
	
	case class PolicyControllerToleration(
	  /** Matches a taint key (not necessarily unique). */
		key: Option[String] = None,
	  /** Matches a taint operator. */
		operator: Option[String] = None,
	  /** Matches a taint value. */
		value: Option[String] = None,
	  /** Matches a taint effect. */
		effect: Option[String] = None
	)
	
	case class IdentityServiceSpec(
	  /** A member may support multiple auth methods. */
		authMethods: Option[List[Schema.IdentityServiceAuthMethod]] = None,
	  /** Optional. non-protocol-related configuration options. */
		identityServiceOptions: Option[Schema.IdentityServiceIdentityServiceOptions] = None
	)
	
	case class IdentityServiceAuthMethod(
	  /** OIDC specific configuration. */
		oidcConfig: Option[Schema.IdentityServiceOidcConfig] = None,
	  /** AzureAD specific Configuration. */
		azureadConfig: Option[Schema.IdentityServiceAzureADConfig] = None,
	  /** GoogleConfig specific configuration */
		googleConfig: Option[Schema.IdentityServiceGoogleConfig] = None,
	  /** SAML specific configuration. */
		samlConfig: Option[Schema.IdentityServiceSamlConfig] = None,
	  /** LDAP specific configuration. */
		ldapConfig: Option[Schema.IdentityServiceLdapConfig] = None,
	  /** Identifier for auth config. */
		name: Option[String] = None,
	  /** Proxy server address to use for auth method. */
		proxy: Option[String] = None
	)
	
	case class IdentityServiceOidcConfig(
	  /** ID for OIDC client application. */
		clientId: Option[String] = None,
	  /** PEM-encoded CA for OIDC provider. */
		certificateAuthorityData: Option[String] = None,
	  /** URI for the OIDC provider. This should point to the level below .well-known/openid-configuration. */
		issuerUri: Option[String] = None,
	  /** Registered redirect uri to redirect users going through OAuth flow using kubectl plugin. */
		kubectlRedirectUri: Option[String] = None,
	  /** Comma-separated list of identifiers. */
		scopes: Option[String] = None,
	  /** Comma-separated list of key-value pairs. */
		extraParams: Option[String] = None,
	  /** Claim in OIDC ID token that holds username. */
		userClaim: Option[String] = None,
	  /** Prefix to prepend to user name. */
		userPrefix: Option[String] = None,
	  /** Claim in OIDC ID token that holds group information. */
		groupsClaim: Option[String] = None,
	  /** Prefix to prepend to group name. */
		groupPrefix: Option[String] = None,
	  /** Flag to denote if reverse proxy is used to connect to auth provider. This flag should be set to true when provider is not reachable by Google Cloud Console. */
		deployCloudConsoleProxy: Option[Boolean] = None,
	  /** Input only. Unencrypted OIDC client secret will be passed to the GKE Hub CLH. */
		clientSecret: Option[String] = None,
	  /** Output only. Encrypted OIDC Client secret */
		encryptedClientSecret: Option[String] = None,
	  /** Enable access token. */
		enableAccessToken: Option[Boolean] = None
	)
	
	case class IdentityServiceAzureADConfig(
	  /** ID for the registered client application that makes authentication requests to the Azure AD identity provider. */
		clientId: Option[String] = None,
	  /** Kind of Azure AD account to be authenticated. Supported values are or for accounts belonging to a specific tenant. */
		tenant: Option[String] = None,
	  /** The redirect URL that kubectl uses for authorization. */
		kubectlRedirectUri: Option[String] = None,
	  /** Input only. Unencrypted AzureAD client secret will be passed to the GKE Hub CLH. */
		clientSecret: Option[String] = None,
	  /** Output only. Encrypted AzureAD client secret. */
		encryptedClientSecret: Option[String] = None,
	  /** Optional. Claim in the AzureAD ID Token that holds the user details. */
		userClaim: Option[String] = None,
	  /** Optional. Format of the AzureAD groups that the client wants for auth. */
		groupFormat: Option[String] = None
	)
	
	case class IdentityServiceGoogleConfig(
	  /** Disable automatic configuration of Google Plugin on supported platforms. */
		disable: Option[Boolean] = None
	)
	
	case class IdentityServiceSamlConfig(
	  /** Required. The entity ID of the SAML IdP. */
		identityProviderId: Option[String] = None,
	  /** Required. The URI where the SAML IdP exposes the SSO service. */
		identityProviderSsoUri: Option[String] = None,
	  /** Required. The list of IdP certificates to validate the SAML response against. */
		identityProviderCertificates: Option[List[String]] = None,
	  /** Optional. The SAML attribute to read username from. If unspecified, the username will be read from the NameID element of the assertion in SAML response. This value is expected to be a string and will be passed along as-is (with the option of being prefixed by the `user_prefix`). */
		userAttribute: Option[String] = None,
	  /** Optional. The SAML attribute to read groups from. This value is expected to be a string and will be passed along as-is (with the option of being prefixed by the `group_prefix`). */
		groupsAttribute: Option[String] = None,
	  /** Optional. Prefix to prepend to user name. */
		userPrefix: Option[String] = None,
	  /** Optional. Prefix to prepend to group name. */
		groupPrefix: Option[String] = None,
	  /** Optional. The mapping of additional user attributes like nickname, birthday and address etc.. `key` is the name of this additional attribute. `value` is a string presenting as CEL(common expression language, go/cel) used for getting the value from the resources. Take nickname as an example, in this case, `key` is "attribute.nickname" and `value` is "assertion.nickname". */
		attributeMapping: Option[Map[String, String]] = None
	)
	
	case class IdentityServiceLdapConfig(
	  /** Required. Server settings for the external LDAP server. */
		server: Option[Schema.IdentityServiceServerConfig] = None,
	  /** Required. Defines where users exist in the LDAP directory. */
		user: Option[Schema.IdentityServiceUserConfig] = None,
	  /** Optional. Contains the properties for locating and authenticating groups in the directory. */
		group: Option[Schema.IdentityServiceGroupConfig] = None,
	  /** Required. Contains the credentials of the service account which is authorized to perform the LDAP search in the directory. The credentials can be supplied by the combination of the DN and password or the client certificate. */
		serviceAccount: Option[Schema.IdentityServiceServiceAccountConfig] = None
	)
	
	case class IdentityServiceServerConfig(
	  /** Required. Defines the hostname or IP of the LDAP server. Port is optional and will default to 389, if unspecified. For example, "ldap.server.example" or "10.10.10.10:389". */
		host: Option[String] = None,
	  /** Optional. Defines the connection type to communicate with the LDAP server. If `starttls` or `ldaps` is specified, the certificate_authority_data should not be empty. */
		connectionType: Option[String] = None,
	  /** Optional. Contains a Base64 encoded, PEM formatted certificate authority certificate for the LDAP server. This must be provided for the "ldaps" and "startTLS" connections. */
		certificateAuthorityData: Option[String] = None
	)
	
	case class IdentityServiceUserConfig(
	  /** Required. The location of the subtree in the LDAP directory to search for user entries. */
		baseDn: Option[String] = None,
	  /** Optional. The name of the attribute which matches against the input username. This is used to find the user in the LDAP database e.g. "(=)" and is combined with the optional filter field. This defaults to "userPrincipalName". */
		loginAttribute: Option[String] = None,
	  /** Optional. Determines which attribute to use as the user's identity after they are authenticated. This is distinct from the loginAttribute field to allow users to login with a username, but then have their actual identifier be an email address or full Distinguished Name (DN). For example, setting loginAttribute to "sAMAccountName" and identifierAttribute to "userPrincipalName" would allow a user to login as "bsmith", but actual RBAC policies for the user would be written as "bsmith@example.com". Using "userPrincipalName" is recommended since this will be unique for each user. This defaults to "userPrincipalName". */
		idAttribute: Option[String] = None,
	  /** Optional. Filter to apply when searching for the user. This can be used to further restrict the user accounts which are allowed to login. This defaults to "(objectClass=User)". */
		filter: Option[String] = None
	)
	
	case class IdentityServiceGroupConfig(
	  /** Required. The location of the subtree in the LDAP directory to search for group entries. */
		baseDn: Option[String] = None,
	  /** Optional. The identifying name of each group a user belongs to. For example, if this is set to "distinguishedName" then RBACs and other group expectations should be written as full DNs. This defaults to "distinguishedName". */
		idAttribute: Option[String] = None,
	  /** Optional. Optional filter to be used when searching for groups a user belongs to. This can be used to explicitly match only certain groups in order to reduce the amount of groups returned for each user. This defaults to "(objectClass=Group)". */
		filter: Option[String] = None
	)
	
	case class IdentityServiceServiceAccountConfig(
	  /** Credentials for basic auth. */
		simpleBindCredentials: Option[Schema.IdentityServiceSimpleBindCredentials] = None
	)
	
	case class IdentityServiceSimpleBindCredentials(
	  /** Required. The distinguished name(DN) of the service account object/user. */
		dn: Option[String] = None,
	  /** Required. Input only. The password of the service account object/user. */
		password: Option[String] = None,
	  /** Output only. The encrypted password of the service account object/user. */
		encryptedPassword: Option[String] = None
	)
	
	case class IdentityServiceIdentityServiceOptions(
	  /** Determines the lifespan of STS tokens issued by Anthos Identity Service. */
		sessionDuration: Option[String] = None,
	  /** Configuration options for the AIS diagnostic interface. */
		diagnosticInterface: Option[Schema.IdentityServiceDiagnosticInterface] = None
	)
	
	case class IdentityServiceDiagnosticInterface(
	  /** Determines whether to enable the diagnostic interface. */
		enabled: Option[Boolean] = None,
	  /** Determines the expiration time of the diagnostic interface enablement. When reached, requests to the interface would be automatically rejected. */
		expirationTime: Option[String] = None
	)
	
	object ServiceMeshSpec {
		enum ControlPlaneEnum extends Enum[ControlPlaneEnum] { case CONTROL_PLANE_MANAGEMENT_UNSPECIFIED, AUTOMATIC, MANUAL }
		enum DefaultChannelEnum extends Enum[DefaultChannelEnum] { case CHANNEL_UNSPECIFIED, RAPID, REGULAR, STABLE }
		enum ManagementEnum extends Enum[ManagementEnum] { case MANAGEMENT_UNSPECIFIED, MANAGEMENT_AUTOMATIC, MANAGEMENT_MANUAL }
		enum ConfigApiEnum extends Enum[ConfigApiEnum] { case CONFIG_API_UNSPECIFIED, CONFIG_API_ISTIO, CONFIG_API_GATEWAY }
	}
	case class ServiceMeshSpec(
	  /** Deprecated: use `management` instead Enables automatic control plane management. */
		controlPlane: Option[Schema.ServiceMeshSpec.ControlPlaneEnum] = None,
	  /** Determines which release channel to use for default injection and service mesh APIs. */
		defaultChannel: Option[Schema.ServiceMeshSpec.DefaultChannelEnum] = None,
	  /** Optional. Enables automatic Service Mesh management. */
		management: Option[Schema.ServiceMeshSpec.ManagementEnum] = None,
	  /** Optional. Specifies the API that will be used for configuring the mesh workloads. */
		configApi: Option[Schema.ServiceMeshSpec.ConfigApiEnum] = None
	)
	
	object ConfigManagementSpec {
		enum ManagementEnum extends Enum[ManagementEnum] { case MANAGEMENT_UNSPECIFIED, MANAGEMENT_AUTOMATIC, MANAGEMENT_MANUAL }
	}
	case class ConfigManagementSpec(
	  /** Config Sync configuration for the cluster. */
		configSync: Option[Schema.ConfigManagementConfigSync] = None,
	  /** Policy Controller configuration for the cluster. Deprecated: Configuring Policy Controller through the configmanagement feature is no longer recommended. Use the policycontroller feature instead. */
		policyController: Option[Schema.ConfigManagementPolicyController] = None,
	  /** Binauthz conifguration for the cluster. Deprecated: This field will be ignored and should not be set. */
		binauthz: Option[Schema.ConfigManagementBinauthzConfig] = None,
	  /** Hierarchy Controller configuration for the cluster. Deprecated: Configuring Hierarchy Controller through the configmanagement feature is no longer recommended. Use https://github.com/kubernetes-sigs/hierarchical-namespaces instead. */
		hierarchyController: Option[Schema.ConfigManagementHierarchyControllerConfig] = None,
	  /** Version of ACM installed. */
		version: Option[String] = None,
	  /** The user-specified cluster name used by Config Sync cluster-name-selector annotation or ClusterSelector, for applying configs to only a subset of clusters. Omit this field if the cluster's fleet membership name is used by Config Sync cluster-name-selector annotation or ClusterSelector. Set this field if a name different from the cluster's fleet membership name is used by Config Sync cluster-name-selector annotation or ClusterSelector. */
		cluster: Option[String] = None,
	  /** Enables automatic Feature management. */
		management: Option[Schema.ConfigManagementSpec.ManagementEnum] = None
	)
	
	case class ConfigManagementConfigSync(
	  /** Git repo configuration for the cluster. */
		git: Option[Schema.ConfigManagementGitConfig] = None,
	  /** Specifies whether the Config Sync Repo is in "hierarchical" or "unstructured" mode. */
		sourceFormat: Option[String] = None,
	  /** Enables the installation of ConfigSync. If set to true, ConfigSync resources will be created and the other ConfigSync fields will be applied if exist. If set to false, all other ConfigSync fields will be ignored, ConfigSync resources will be deleted. If omitted, ConfigSync resources will be managed depends on the presence of the git or oci field. */
		enabled: Option[Boolean] = None,
	  /** Set to true to enable the Config Sync admission webhook to prevent drifts. If set to `false`, disables the Config Sync admission webhook and does not prevent drifts. */
		preventDrift: Option[Boolean] = None,
	  /** OCI repo configuration for the cluster. */
		oci: Option[Schema.ConfigManagementOciConfig] = None,
	  /** Set to true to allow the vertical scaling. Defaults to false which disallows vertical scaling. This field is deprecated. */
		allowVerticalScale: Option[Boolean] = None,
	  /** The Email of the Google Cloud Service Account (GSA) used for exporting Config Sync metrics to Cloud Monitoring and Cloud Monarch when Workload Identity is enabled. The GSA should have the Monitoring Metric Writer (roles/monitoring.metricWriter) IAM role. The Kubernetes ServiceAccount `default` in the namespace `config-management-monitoring` should be bound to the GSA. Deprecated: If Workload Identity Federation for GKE is enabled, Google Cloud Service Account is no longer needed for exporting Config Sync metrics: https://cloud.google.com/kubernetes-engine/enterprise/config-sync/docs/how-to/monitor-config-sync-cloud-monitoring#custom-monitoring. */
		metricsGcpServiceAccountEmail: Option[String] = None,
	  /** Set to true to stop syncing configs for a single cluster. Default to false. */
		stopSyncing: Option[Boolean] = None
	)
	
	case class ConfigManagementGitConfig(
	  /** The URL of the Git repository to use as the source of truth. */
		syncRepo: Option[String] = None,
	  /** The branch of the repository to sync from. Default: master. */
		syncBranch: Option[String] = None,
	  /** The path within the Git repository that represents the top level of the repo to sync. Default: the root directory of the repository. */
		policyDir: Option[String] = None,
	  /** Period in seconds between consecutive syncs. Default: 15. */
		syncWaitSecs: Option[String] = None,
	  /** Git revision (tag or hash) to check out. Default HEAD. */
		syncRev: Option[String] = None,
	  /** Type of secret configured for access to the Git repo. Must be one of ssh, cookiefile, gcenode, token, gcpserviceaccount or none. The validation of this is case-sensitive. Required. */
		secretType: Option[String] = None,
	  /** URL for the HTTPS proxy to be used when communicating with the Git repo. */
		httpsProxy: Option[String] = None,
	  /** The Google Cloud Service Account Email used for auth when secret_type is gcpServiceAccount. */
		gcpServiceAccountEmail: Option[String] = None
	)
	
	case class ConfigManagementOciConfig(
	  /** The OCI image repository URL for the package to sync from. e.g. `LOCATION-docker.pkg.dev/PROJECT_ID/REPOSITORY_NAME/PACKAGE_NAME`. */
		syncRepo: Option[String] = None,
	  /** The absolute path of the directory that contains the local resources. Default: the root directory of the image. */
		policyDir: Option[String] = None,
	  /** Period in seconds between consecutive syncs. Default: 15. */
		syncWaitSecs: Option[String] = None,
	  /** Type of secret configured for access to the Git repo. */
		secretType: Option[String] = None,
	  /** The Google Cloud Service Account Email used for auth when secret_type is gcpServiceAccount. */
		gcpServiceAccountEmail: Option[String] = None
	)
	
	case class ConfigManagementPolicyController(
	  /** Enables the installation of Policy Controller. If false, the rest of PolicyController fields take no effect. */
		enabled: Option[Boolean] = None,
	  /** Installs the default template library along with Policy Controller. */
		templateLibraryInstalled: Option[Boolean] = None,
	  /** Sets the interval for Policy Controller Audit Scans (in seconds). When set to 0, this disables audit functionality altogether. */
		auditIntervalSeconds: Option[String] = None,
	  /** The set of namespaces that are excluded from Policy Controller checks. Namespaces do not need to currently exist on the cluster. */
		exemptableNamespaces: Option[List[String]] = None,
	  /** Enables the ability to use Constraint Templates that reference to objects other than the object currently being evaluated. */
		referentialRulesEnabled: Option[Boolean] = None,
	  /** Logs all denies and dry run failures. */
		logDeniesEnabled: Option[Boolean] = None,
	  /** Enable or disable mutation in policy controller. If true, mutation CRDs, webhook and controller deployment will be deployed to the cluster. */
		mutationEnabled: Option[Boolean] = None,
	  /** Monitoring specifies the configuration of monitoring. */
		monitoring: Option[Schema.ConfigManagementPolicyControllerMonitoring] = None,
	  /** Output only. Last time this membership spec was updated. */
		updateTime: Option[String] = None
	)
	
	object ConfigManagementPolicyControllerMonitoring {
		enum BackendsEnum extends Enum[BackendsEnum] { case MONITORING_BACKEND_UNSPECIFIED, PROMETHEUS, CLOUD_MONITORING }
	}
	case class ConfigManagementPolicyControllerMonitoring(
	  /** Specifies the list of backends Policy Controller will export to. An empty list would effectively disable metrics export. */
		backends: Option[List[Schema.ConfigManagementPolicyControllerMonitoring.BackendsEnum]] = None
	)
	
	case class ConfigManagementBinauthzConfig(
	  /** Whether binauthz is enabled in this cluster. */
		enabled: Option[Boolean] = None
	)
	
	case class ConfigManagementHierarchyControllerConfig(
	  /** Whether Hierarchy Controller is enabled in this cluster. */
		enabled: Option[Boolean] = None,
	  /** Whether pod tree labels are enabled in this cluster. */
		enablePodTreeLabels: Option[Boolean] = None,
	  /** Whether hierarchical resource quota is enabled in this cluster. */
		enableHierarchicalResourceQuota: Option[Boolean] = None
	)
	
	object Origin {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, FLEET, FLEET_OUT_OF_SYNC, USER }
	}
	case class Origin(
	  /** Type specifies which type of origin is set. */
		`type`: Option[Schema.Origin.TypeEnum] = None
	)
	
	case class FeatureState(
	  /** Cluster upgrade state. */
		clusterupgrade: Option[Schema.ClusterUpgradeState] = None,
	  /** Identity service state */
		identityservice: Option[Schema.IdentityServiceState] = None,
	  /** Service mesh state */
		servicemesh: Option[Schema.ServiceMeshState] = None,
	  /** Metering state */
		metering: Option[Schema.MeteringState] = None,
	  /** Config Management state */
		configmanagement: Option[Schema.ConfigManagementState] = None,
	  /** Policy Controller state */
		policycontroller: Option[Schema.PolicyControllerState] = None,
	  /** Appdevexperience specific state. */
		appdevexperience: Option[Schema.AppDevExperienceState] = None,
	  /** The high-level state of this MembershipFeature. */
		state: Option[Schema.State] = None
	)
	
	case class ClusterUpgradeState(
	  /** Whether this membership is ignored by the feature. For example, manually upgraded clusters can be ignored if they are newer than the default versions of its release channel. */
		ignored: Option[Schema.ClusterUpgradeIgnoredMembership] = None,
	  /** Actual upgrade state against desired. */
		upgrades: Option[List[Schema.ClusterUpgradeMembershipGKEUpgradeState]] = None
	)
	
	case class ClusterUpgradeIgnoredMembership(
	  /** Reason why the membership is ignored. */
		reason: Option[String] = None,
	  /** Time when the membership was first set to ignored. */
		ignoredTime: Option[String] = None
	)
	
	case class ClusterUpgradeMembershipGKEUpgradeState(
	  /** Which upgrade to track the state. */
		upgrade: Option[Schema.ClusterUpgradeGKEUpgrade] = None,
	  /** Status of the upgrade. */
		status: Option[Schema.ClusterUpgradeUpgradeStatus] = None
	)
	
	case class ClusterUpgradeGKEUpgrade(
	  /** Name of the upgrade, e.g., "k8s_control_plane". */
		name: Option[String] = None,
	  /** Version of the upgrade, e.g., "1.22.1-gke.100". */
		version: Option[String] = None
	)
	
	object ClusterUpgradeUpgradeStatus {
		enum CodeEnum extends Enum[CodeEnum] { case CODE_UNSPECIFIED, INELIGIBLE, PENDING, IN_PROGRESS, SOAKING, FORCED_SOAKING, COMPLETE }
	}
	case class ClusterUpgradeUpgradeStatus(
	  /** Status code of the upgrade. */
		code: Option[Schema.ClusterUpgradeUpgradeStatus.CodeEnum] = None,
	  /** Reason for this status. */
		reason: Option[String] = None,
	  /** Last timestamp the status was updated. */
		updateTime: Option[String] = None
	)
	
	object IdentityServiceState {
		enum StateEnum extends Enum[StateEnum] { case DEPLOYMENT_STATE_UNSPECIFIED, OK, ERROR }
	}
	case class IdentityServiceState(
	  /** Installed AIS version. This is the AIS version installed on this member. The values makes sense iff state is OK. */
		installedVersion: Option[String] = None,
	  /** Deployment state on this member */
		state: Option[Schema.IdentityServiceState.StateEnum] = None,
	  /** The reason of the failure. */
		failureReason: Option[String] = None,
	  /** Last reconciled membership configuration */
		memberConfig: Option[Schema.IdentityServiceSpec] = None
	)
	
	case class ServiceMeshState(
	  /** Output only. Results of running Service Mesh analyzers. */
		analysisMessages: Option[List[Schema.ServiceMeshAnalysisMessage]] = None,
	  /** Output only. Status of control plane management */
		controlPlaneManagement: Option[Schema.ServiceMeshControlPlaneManagement] = None,
	  /** Output only. Status of data plane management. */
		dataPlaneManagement: Option[Schema.ServiceMeshDataPlaneManagement] = None,
	  /** The API version (i.e. Istio CRD version) for configuring service mesh in this cluster. This version is influenced by the `default_channel` field. */
		configApiVersion: Option[String] = None,
	  /** Output only. List of conditions reported for this membership. */
		conditions: Option[List[Schema.ServiceMeshCondition]] = None
	)
	
	case class ServiceMeshAnalysisMessage(
	  /** Details common to all types of Istio and ServiceMesh analysis messages. */
		messageBase: Option[Schema.ServiceMeshAnalysisMessageBase] = None,
	  /** A human readable description of what the error means. It is suitable for non-internationalize display purposes. */
		description: Option[String] = None,
	  /** A list of strings specifying the resource identifiers that were the cause of message generation. A "path" here may be: &#42; MEMBERSHIP_ID if the cause is a specific member cluster &#42; MEMBERSHIP_ID/(NAMESPACE\/)?RESOURCETYPE/NAME if the cause is a resource in a cluster */
		resourcePaths: Option[List[String]] = None,
	  /** A UI can combine these args with a template (based on message_base.type) to produce an internationalized message. */
		args: Option[Map[String, JsValue]] = None
	)
	
	object ServiceMeshAnalysisMessageBase {
		enum LevelEnum extends Enum[LevelEnum] { case LEVEL_UNSPECIFIED, ERROR, WARNING, INFO }
	}
	case class ServiceMeshAnalysisMessageBase(
	  /** Represents the specific type of a message. */
		`type`: Option[Schema.ServiceMeshType] = None,
	  /** Represents how severe a message is. */
		level: Option[Schema.ServiceMeshAnalysisMessageBase.LevelEnum] = None,
	  /** A url pointing to the Service Mesh or Istio documentation for this specific error type. */
		documentationUrl: Option[String] = None
	)
	
	case class ServiceMeshType(
	  /** A human-readable name for the message type. e.g. "InternalError", "PodMissingProxy". This should be the same for all messages of the same type. (This corresponds to the `name` field in open-source Istio.) */
		displayName: Option[String] = None,
	  /** A 7 character code matching `^IST[0-9]{4}$` or `^ASM[0-9]{4}$`, intended to uniquely identify the message type. (e.g. "IST0001" is mapped to the "InternalError" message type.) */
		code: Option[String] = None
	)
	
	object ServiceMeshControlPlaneManagement {
		enum StateEnum extends Enum[StateEnum] { case LIFECYCLE_STATE_UNSPECIFIED, DISABLED, FAILED_PRECONDITION, PROVISIONING, ACTIVE, STALLED, NEEDS_ATTENTION, DEGRADED }
		enum ImplementationEnum extends Enum[ImplementationEnum] { case IMPLEMENTATION_UNSPECIFIED, ISTIOD, TRAFFIC_DIRECTOR, UPDATING }
	}
	case class ServiceMeshControlPlaneManagement(
	  /** Explanation of state. */
		details: Option[List[Schema.ServiceMeshStatusDetails]] = None,
	  /** LifecycleState of control plane management. */
		state: Option[Schema.ServiceMeshControlPlaneManagement.StateEnum] = None,
	  /** Output only. Implementation of managed control plane. */
		implementation: Option[Schema.ServiceMeshControlPlaneManagement.ImplementationEnum] = None
	)
	
	case class ServiceMeshStatusDetails(
	  /** A machine-readable code that further describes a broad status. */
		code: Option[String] = None,
	  /** Human-readable explanation of code. */
		details: Option[String] = None
	)
	
	object ServiceMeshDataPlaneManagement {
		enum StateEnum extends Enum[StateEnum] { case LIFECYCLE_STATE_UNSPECIFIED, DISABLED, FAILED_PRECONDITION, PROVISIONING, ACTIVE, STALLED, NEEDS_ATTENTION, DEGRADED }
	}
	case class ServiceMeshDataPlaneManagement(
	  /** Lifecycle status of data plane management. */
		state: Option[Schema.ServiceMeshDataPlaneManagement.StateEnum] = None,
	  /** Explanation of the status. */
		details: Option[List[Schema.ServiceMeshStatusDetails]] = None
	)
	
	object ServiceMeshCondition {
		enum CodeEnum extends Enum[CodeEnum] { case CODE_UNSPECIFIED, MESH_IAM_PERMISSION_DENIED, MESH_IAM_CROSS_PROJECT_PERMISSION_DENIED, CNI_CONFIG_UNSUPPORTED, GKE_SANDBOX_UNSUPPORTED, NODEPOOL_WORKLOAD_IDENTITY_FEDERATION_REQUIRED, CNI_INSTALLATION_FAILED, CNI_POD_UNSCHEDULABLE, CLUSTER_HAS_ZERO_NODES, UNSUPPORTED_MULTIPLE_CONTROL_PLANES, VPCSC_GA_SUPPORTED, DEPRECATED_SPEC_CONTROL_PLANE_MANAGEMENT, DEPRECATED_SPEC_CONTROL_PLANE_MANAGEMENT_SAFE, CONFIG_APPLY_INTERNAL_ERROR, CONFIG_VALIDATION_ERROR, CONFIG_VALIDATION_WARNING, QUOTA_EXCEEDED_BACKEND_SERVICES, QUOTA_EXCEEDED_HEALTH_CHECKS, QUOTA_EXCEEDED_HTTP_ROUTES, QUOTA_EXCEEDED_TCP_ROUTES, QUOTA_EXCEEDED_TLS_ROUTES, QUOTA_EXCEEDED_TRAFFIC_POLICIES, QUOTA_EXCEEDED_ENDPOINT_POLICIES, QUOTA_EXCEEDED_GATEWAYS, QUOTA_EXCEEDED_MESHES, QUOTA_EXCEEDED_SERVER_TLS_POLICIES, QUOTA_EXCEEDED_CLIENT_TLS_POLICIES, QUOTA_EXCEEDED_SERVICE_LB_POLICIES, QUOTA_EXCEEDED_HTTP_FILTERS, QUOTA_EXCEEDED_TCP_FILTERS, QUOTA_EXCEEDED_NETWORK_ENDPOINT_GROUPS, MODERNIZATION_SCHEDULED, MODERNIZATION_IN_PROGRESS, MODERNIZATION_COMPLETED, MODERNIZATION_ABORTED }
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, ERROR, WARNING, INFO }
	}
	case class ServiceMeshCondition(
	  /** Unique identifier of the condition which describes the condition recognizable to the user. */
		code: Option[Schema.ServiceMeshCondition.CodeEnum] = None,
	  /** Links contains actionable information. */
		documentationLink: Option[String] = None,
	  /** A short summary about the issue. */
		details: Option[String] = None,
	  /** Severity level of the condition. */
		severity: Option[Schema.ServiceMeshCondition.SeverityEnum] = None
	)
	
	case class MeteringState(
	  /** The time stamp of the most recent measurement of the number of vCPUs in the cluster. */
		lastMeasurementTime: Option[String] = None,
	  /** The vCPUs capacity in the cluster according to the most recent measurement (1/1000 precision). */
		preciseLastMeasuredClusterVcpuCapacity: Option[BigDecimal] = None
	)
	
	case class ConfigManagementState(
	  /** This field is set to the `cluster_name` field of the Membership Spec if it is not empty. Otherwise, it is set to the cluster's fleet membership name. */
		clusterName: Option[String] = None,
	  /** Membership configuration in the cluster. This represents the actual state in the cluster, while the MembershipSpec in the FeatureSpec represents the intended state. */
		membershipSpec: Option[Schema.ConfigManagementSpec] = None,
	  /** Current install status of ACM's Operator. */
		operatorState: Option[Schema.ConfigManagementOperatorState] = None,
	  /** Current sync status. */
		configSyncState: Option[Schema.ConfigManagementConfigSyncState] = None,
	  /** PolicyController status. */
		policyControllerState: Option[Schema.ConfigManagementPolicyControllerState] = None,
	  /** Binauthz status. */
		binauthzState: Option[Schema.ConfigManagementBinauthzState] = None,
	  /** Hierarchy Controller status. */
		hierarchyControllerState: Option[Schema.ConfigManagementHierarchyControllerState] = None
	)
	
	object ConfigManagementOperatorState {
		enum DeploymentStateEnum extends Enum[DeploymentStateEnum] { case DEPLOYMENT_STATE_UNSPECIFIED, NOT_INSTALLED, INSTALLED, ERROR, PENDING }
	}
	case class ConfigManagementOperatorState(
	  /** The semenatic version number of the operator. */
		version: Option[String] = None,
	  /** The state of the Operator's deployment. */
		deploymentState: Option[Schema.ConfigManagementOperatorState.DeploymentStateEnum] = None,
	  /** Install errors. */
		errors: Option[List[Schema.ConfigManagementInstallError]] = None
	)
	
	case class ConfigManagementInstallError(
	  /** A string representing the user facing error message. */
		errorMessage: Option[String] = None
	)
	
	object ConfigManagementConfigSyncState {
		enum RootsyncCrdEnum extends Enum[RootsyncCrdEnum] { case CRD_STATE_UNSPECIFIED, NOT_INSTALLED, INSTALLED, TERMINATING, INSTALLING }
		enum ReposyncCrdEnum extends Enum[ReposyncCrdEnum] { case CRD_STATE_UNSPECIFIED, NOT_INSTALLED, INSTALLED, TERMINATING, INSTALLING }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CONFIG_SYNC_NOT_INSTALLED, CONFIG_SYNC_INSTALLED, CONFIG_SYNC_ERROR, CONFIG_SYNC_PENDING }
		enum ClusterLevelStopSyncingStateEnum extends Enum[ClusterLevelStopSyncingStateEnum] { case STOP_SYNCING_STATE_UNSPECIFIED, NOT_STOPPED, PENDING, STOPPED }
	}
	case class ConfigManagementConfigSyncState(
	  /** The version of ConfigSync deployed. */
		version: Option[Schema.ConfigManagementConfigSyncVersion] = None,
	  /** Information about the deployment of ConfigSync, including the version. of the various Pods deployed */
		deploymentState: Option[Schema.ConfigManagementConfigSyncDeploymentState] = None,
	  /** The state of ConfigSync's process to sync configs to a cluster. */
		syncState: Option[Schema.ConfigManagementSyncState] = None,
	  /** Errors pertaining to the installation of Config Sync. */
		errors: Option[List[Schema.ConfigManagementConfigSyncError]] = None,
	  /** The state of the RootSync CRD */
		rootsyncCrd: Option[Schema.ConfigManagementConfigSyncState.RootsyncCrdEnum] = None,
	  /** The state of the Reposync CRD */
		reposyncCrd: Option[Schema.ConfigManagementConfigSyncState.ReposyncCrdEnum] = None,
	  /** The state of CS This field summarizes the other fields in this message. */
		state: Option[Schema.ConfigManagementConfigSyncState.StateEnum] = None,
	  /** Whether syncing resources to the cluster is stopped at the cluster level. */
		clusterLevelStopSyncingState: Option[Schema.ConfigManagementConfigSyncState.ClusterLevelStopSyncingStateEnum] = None,
	  /** Output only. The number of RootSync and RepoSync CRs in the cluster. */
		crCount: Option[Int] = None
	)
	
	case class ConfigManagementConfigSyncVersion(
	  /** Version of the deployed importer pod. */
		importer: Option[String] = None,
	  /** Version of the deployed syncer pod. */
		syncer: Option[String] = None,
	  /** Version of the deployed git-sync pod. */
		gitSync: Option[String] = None,
	  /** Version of the deployed monitor pod. */
		monitor: Option[String] = None,
	  /** Version of the deployed reconciler-manager pod. */
		reconcilerManager: Option[String] = None,
	  /** Version of the deployed reconciler container in root-reconciler pod. */
		rootReconciler: Option[String] = None,
	  /** Version of the deployed admission-webhook pod. */
		admissionWebhook: Option[String] = None,
	  /** Version of the deployed resource-group-controller-manager pod */
		resourceGroupControllerManager: Option[String] = None,
	  /** Version of the deployed otel-collector pod */
		otelCollector: Option[String] = None
	)
	
	object ConfigManagementConfigSyncDeploymentState {
		enum ImporterEnum extends Enum[ImporterEnum] { case DEPLOYMENT_STATE_UNSPECIFIED, NOT_INSTALLED, INSTALLED, ERROR, PENDING }
		enum SyncerEnum extends Enum[SyncerEnum] { case DEPLOYMENT_STATE_UNSPECIFIED, NOT_INSTALLED, INSTALLED, ERROR, PENDING }
		enum GitSyncEnum extends Enum[GitSyncEnum] { case DEPLOYMENT_STATE_UNSPECIFIED, NOT_INSTALLED, INSTALLED, ERROR, PENDING }
		enum MonitorEnum extends Enum[MonitorEnum] { case DEPLOYMENT_STATE_UNSPECIFIED, NOT_INSTALLED, INSTALLED, ERROR, PENDING }
		enum ReconcilerManagerEnum extends Enum[ReconcilerManagerEnum] { case DEPLOYMENT_STATE_UNSPECIFIED, NOT_INSTALLED, INSTALLED, ERROR, PENDING }
		enum RootReconcilerEnum extends Enum[RootReconcilerEnum] { case DEPLOYMENT_STATE_UNSPECIFIED, NOT_INSTALLED, INSTALLED, ERROR, PENDING }
		enum AdmissionWebhookEnum extends Enum[AdmissionWebhookEnum] { case DEPLOYMENT_STATE_UNSPECIFIED, NOT_INSTALLED, INSTALLED, ERROR, PENDING }
		enum ResourceGroupControllerManagerEnum extends Enum[ResourceGroupControllerManagerEnum] { case DEPLOYMENT_STATE_UNSPECIFIED, NOT_INSTALLED, INSTALLED, ERROR, PENDING }
		enum OtelCollectorEnum extends Enum[OtelCollectorEnum] { case DEPLOYMENT_STATE_UNSPECIFIED, NOT_INSTALLED, INSTALLED, ERROR, PENDING }
	}
	case class ConfigManagementConfigSyncDeploymentState(
	  /** Deployment state of the importer pod. */
		importer: Option[Schema.ConfigManagementConfigSyncDeploymentState.ImporterEnum] = None,
	  /** Deployment state of the syncer pod. */
		syncer: Option[Schema.ConfigManagementConfigSyncDeploymentState.SyncerEnum] = None,
	  /** Deployment state of the git-sync pod. */
		gitSync: Option[Schema.ConfigManagementConfigSyncDeploymentState.GitSyncEnum] = None,
	  /** Deployment state of the monitor pod. */
		monitor: Option[Schema.ConfigManagementConfigSyncDeploymentState.MonitorEnum] = None,
	  /** Deployment state of reconciler-manager pod. */
		reconcilerManager: Option[Schema.ConfigManagementConfigSyncDeploymentState.ReconcilerManagerEnum] = None,
	  /** Deployment state of root-reconciler. */
		rootReconciler: Option[Schema.ConfigManagementConfigSyncDeploymentState.RootReconcilerEnum] = None,
	  /** Deployment state of admission-webhook. */
		admissionWebhook: Option[Schema.ConfigManagementConfigSyncDeploymentState.AdmissionWebhookEnum] = None,
	  /** Deployment state of resource-group-controller-manager */
		resourceGroupControllerManager: Option[Schema.ConfigManagementConfigSyncDeploymentState.ResourceGroupControllerManagerEnum] = None,
	  /** Deployment state of otel-collector */
		otelCollector: Option[Schema.ConfigManagementConfigSyncDeploymentState.OtelCollectorEnum] = None
	)
	
	object ConfigManagementSyncState {
		enum CodeEnum extends Enum[CodeEnum] { case SYNC_CODE_UNSPECIFIED, SYNCED, PENDING, ERROR, NOT_CONFIGURED, NOT_INSTALLED, UNAUTHORIZED, UNREACHABLE }
	}
	case class ConfigManagementSyncState(
	  /** Token indicating the state of the repo. */
		sourceToken: Option[String] = None,
	  /** Token indicating the state of the importer. */
		importToken: Option[String] = None,
	  /** Token indicating the state of the syncer. */
		syncToken: Option[String] = None,
	  /** Deprecated: use last_sync_time instead. Timestamp of when ACM last successfully synced the repo. The time format is specified in https://golang.org/pkg/time/#Time.String */
		lastSync: Option[String] = None,
	  /** Timestamp type of when ACM last successfully synced the repo. */
		lastSyncTime: Option[String] = None,
	  /** Sync status code. */
		code: Option[Schema.ConfigManagementSyncState.CodeEnum] = None,
	  /** A list of errors resulting from problematic configs. This list will be truncated after 100 errors, although it is unlikely for that many errors to simultaneously exist. */
		errors: Option[List[Schema.ConfigManagementSyncError]] = None
	)
	
	case class ConfigManagementSyncError(
	  /** An ACM defined error code */
		code: Option[String] = None,
	  /** A description of the error */
		errorMessage: Option[String] = None,
	  /** A list of config(s) associated with the error, if any */
		errorResources: Option[List[Schema.ConfigManagementErrorResource]] = None
	)
	
	case class ConfigManagementErrorResource(
	  /** Path in the git repo of the erroneous config */
		sourcePath: Option[String] = None,
	  /** Metadata name of the resource that is causing an error */
		resourceName: Option[String] = None,
	  /** Namespace of the resource that is causing an error */
		resourceNamespace: Option[String] = None,
	  /** Group/version/kind of the resource that is causing an error */
		resourceGvk: Option[Schema.ConfigManagementGroupVersionKind] = None
	)
	
	case class ConfigManagementGroupVersionKind(
	  /** Kubernetes Group */
		group: Option[String] = None,
	  /** Kubernetes Version */
		version: Option[String] = None,
	  /** Kubernetes Kind */
		kind: Option[String] = None
	)
	
	case class ConfigManagementConfigSyncError(
	  /** A string representing the user facing error message */
		errorMessage: Option[String] = None
	)
	
	case class ConfigManagementPolicyControllerState(
	  /** The version of Gatekeeper Policy Controller deployed. */
		version: Option[Schema.ConfigManagementPolicyControllerVersion] = None,
	  /** The state about the policy controller installation. */
		deploymentState: Option[Schema.ConfigManagementGatekeeperDeploymentState] = None,
	  /** Record state of ACM -> PoCo Hub migration for this feature. */
		migration: Option[Schema.ConfigManagementPolicyControllerMigration] = None
	)
	
	case class ConfigManagementPolicyControllerVersion(
	  /** The gatekeeper image tag that is composed of ACM version, git tag, build number. */
		version: Option[String] = None
	)
	
	object ConfigManagementGatekeeperDeploymentState {
		enum GatekeeperControllerManagerStateEnum extends Enum[GatekeeperControllerManagerStateEnum] { case DEPLOYMENT_STATE_UNSPECIFIED, NOT_INSTALLED, INSTALLED, ERROR, PENDING }
		enum GatekeeperAuditEnum extends Enum[GatekeeperAuditEnum] { case DEPLOYMENT_STATE_UNSPECIFIED, NOT_INSTALLED, INSTALLED, ERROR, PENDING }
		enum GatekeeperMutationEnum extends Enum[GatekeeperMutationEnum] { case DEPLOYMENT_STATE_UNSPECIFIED, NOT_INSTALLED, INSTALLED, ERROR, PENDING }
	}
	case class ConfigManagementGatekeeperDeploymentState(
	  /** Status of gatekeeper-controller-manager pod. */
		gatekeeperControllerManagerState: Option[Schema.ConfigManagementGatekeeperDeploymentState.GatekeeperControllerManagerStateEnum] = None,
	  /** Status of gatekeeper-audit deployment. */
		gatekeeperAudit: Option[Schema.ConfigManagementGatekeeperDeploymentState.GatekeeperAuditEnum] = None,
	  /** Status of the pod serving the mutation webhook. */
		gatekeeperMutation: Option[Schema.ConfigManagementGatekeeperDeploymentState.GatekeeperMutationEnum] = None
	)
	
	object ConfigManagementPolicyControllerMigration {
		enum StageEnum extends Enum[StageEnum] { case STAGE_UNSPECIFIED, ACM_MANAGED, POCO_MANAGED }
	}
	case class ConfigManagementPolicyControllerMigration(
	  /** Stage of the migration. */
		stage: Option[Schema.ConfigManagementPolicyControllerMigration.StageEnum] = None,
	  /** Last time this membership spec was copied to PoCo feature. */
		copyTime: Option[String] = None
	)
	
	object ConfigManagementBinauthzState {
		enum WebhookEnum extends Enum[WebhookEnum] { case DEPLOYMENT_STATE_UNSPECIFIED, NOT_INSTALLED, INSTALLED, ERROR, PENDING }
	}
	case class ConfigManagementBinauthzState(
	  /** The state of the binauthz webhook. */
		webhook: Option[Schema.ConfigManagementBinauthzState.WebhookEnum] = None,
	  /** The version of binauthz that is installed. */
		version: Option[Schema.ConfigManagementBinauthzVersion] = None
	)
	
	case class ConfigManagementBinauthzVersion(
	  /** The version of the binauthz webhook. */
		webhookVersion: Option[String] = None
	)
	
	case class ConfigManagementHierarchyControllerState(
	  /** The version for Hierarchy Controller. */
		version: Option[Schema.ConfigManagementHierarchyControllerVersion] = None,
	  /** The deployment state for Hierarchy Controller. */
		state: Option[Schema.ConfigManagementHierarchyControllerDeploymentState] = None
	)
	
	case class ConfigManagementHierarchyControllerVersion(
	  /** Version for open source HNC. */
		hnc: Option[String] = None,
	  /** Version for Hierarchy Controller extension. */
		extension: Option[String] = None
	)
	
	object ConfigManagementHierarchyControllerDeploymentState {
		enum HncEnum extends Enum[HncEnum] { case DEPLOYMENT_STATE_UNSPECIFIED, NOT_INSTALLED, INSTALLED, ERROR, PENDING }
		enum ExtensionEnum extends Enum[ExtensionEnum] { case DEPLOYMENT_STATE_UNSPECIFIED, NOT_INSTALLED, INSTALLED, ERROR, PENDING }
	}
	case class ConfigManagementHierarchyControllerDeploymentState(
	  /** The deployment state for open source HNC (e.g. v0.7.0-hc.0). */
		hnc: Option[Schema.ConfigManagementHierarchyControllerDeploymentState.HncEnum] = None,
	  /** The deployment state for Hierarchy Controller extension (e.g. v0.7.0-hc.1). */
		extension: Option[Schema.ConfigManagementHierarchyControllerDeploymentState.ExtensionEnum] = None
	)
	
	object PolicyControllerState {
		enum StateEnum extends Enum[StateEnum] { case LIFECYCLE_STATE_UNSPECIFIED, NOT_INSTALLED, INSTALLING, ACTIVE, UPDATING, DECOMMISSIONING, CLUSTER_ERROR, HUB_ERROR, SUSPENDED, DETACHED }
	}
	case class PolicyControllerState(
	  /** Currently these include (also serving as map keys): 1. "admission" 2. "audit" 3. "mutation" */
		componentStates: Option[Map[String, Schema.PolicyControllerOnClusterState]] = None,
	  /** The overall Policy Controller lifecycle state observed by the Hub Feature controller. */
		state: Option[Schema.PolicyControllerState.StateEnum] = None,
	  /** The overall content state observed by the Hub Feature controller. */
		policyContentState: Option[Schema.PolicyControllerPolicyContentState] = None
	)
	
	object PolicyControllerOnClusterState {
		enum StateEnum extends Enum[StateEnum] { case LIFECYCLE_STATE_UNSPECIFIED, NOT_INSTALLED, INSTALLING, ACTIVE, UPDATING, DECOMMISSIONING, CLUSTER_ERROR, HUB_ERROR, SUSPENDED, DETACHED }
	}
	case class PolicyControllerOnClusterState(
	  /** The lifecycle state of this component. */
		state: Option[Schema.PolicyControllerOnClusterState.StateEnum] = None,
	  /** Surface potential errors or information logs. */
		details: Option[String] = None
	)
	
	case class PolicyControllerPolicyContentState(
	  /** The state of the template library */
		templateLibraryState: Option[Schema.PolicyControllerOnClusterState] = None,
	  /** The state of the any bundles included in the chosen version of the manifest */
		bundleStates: Option[Map[String, Schema.PolicyControllerOnClusterState]] = None,
	  /** The state of the referential data sync configuration. This could represent the state of either the syncSet object(s) or the config object, depending on the version of PoCo configured by the user. */
		referentialSyncConfigState: Option[Schema.PolicyControllerOnClusterState] = None
	)
	
	case class AppDevExperienceState(
	  /** Status of subcomponent that detects configured Service Mesh resources. */
		networkingInstallSucceeded: Option[Schema.AppDevExperienceStatus] = None
	)
	
	object AppDevExperienceStatus {
		enum CodeEnum extends Enum[CodeEnum] { case CODE_UNSPECIFIED, OK, FAILED, UNKNOWN }
	}
	case class AppDevExperienceStatus(
	  /** Code specifies AppDevExperienceFeature's subcomponent ready state. */
		code: Option[Schema.AppDevExperienceStatus.CodeEnum] = None,
	  /** Description is populated if Code is Failed, explaining why it has failed. */
		description: Option[String] = None
	)
	
	object State {
		enum CodeEnum extends Enum[CodeEnum] { case CODE_UNSPECIFIED, OK, WARNING, ERROR }
	}
	case class State(
	  /** The high-level, machine-readable status of this MembershipFeature. */
		code: Option[Schema.State.CodeEnum] = None,
	  /** A human-readable description of the current status. */
		description: Option[String] = None,
	  /** The time this status and any related Feature-specific details were updated. */
		updateTime: Option[String] = None
	)
	
	object LifecycleState {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ENABLING, ACTIVE, DISABLING, UPDATING, SERVICE_UPDATING }
	}
	case class LifecycleState(
	  /** Output only. The current state of the Feature resource in the Hub API. */
		state: Option[Schema.LifecycleState.StateEnum] = None
	)
	
	case class ListMembershipFeaturesResponse(
	  /** The list of matching MembershipFeatures. */
		membershipFeatures: Option[List[Schema.MembershipFeature]] = None,
	  /** A token to request the next page of resources from the `ListMembershipFeatures` method. The value of an empty string means that there are no more resources to return. */
		nextPageToken: Option[String] = None,
	  /** List of locations that could not be reached while fetching this list. */
		unreachable: Option[List[String]] = None
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
		statusDetail: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		cancelRequested: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
}
