package com.boresjo.play.api.google.container

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListClustersResponse: Format[Schema.ListClustersResponse] = Json.format[Schema.ListClustersResponse]
	given fmtCluster: Format[Schema.Cluster] = Json.format[Schema.Cluster]
	given fmtNodeConfig: Format[Schema.NodeConfig] = Json.format[Schema.NodeConfig]
	given fmtMasterAuth: Format[Schema.MasterAuth] = Json.format[Schema.MasterAuth]
	given fmtAddonsConfig: Format[Schema.AddonsConfig] = Json.format[Schema.AddonsConfig]
	given fmtNodePool: Format[Schema.NodePool] = Json.format[Schema.NodePool]
	given fmtLegacyAbac: Format[Schema.LegacyAbac] = Json.format[Schema.LegacyAbac]
	given fmtNetworkPolicy: Format[Schema.NetworkPolicy] = Json.format[Schema.NetworkPolicy]
	given fmtIPAllocationPolicy: Format[Schema.IPAllocationPolicy] = Json.format[Schema.IPAllocationPolicy]
	given fmtMasterAuthorizedNetworksConfig: Format[Schema.MasterAuthorizedNetworksConfig] = Json.format[Schema.MasterAuthorizedNetworksConfig]
	given fmtMaintenancePolicy: Format[Schema.MaintenancePolicy] = Json.format[Schema.MaintenancePolicy]
	given fmtBinaryAuthorization: Format[Schema.BinaryAuthorization] = Json.format[Schema.BinaryAuthorization]
	given fmtClusterAutoscaling: Format[Schema.ClusterAutoscaling] = Json.format[Schema.ClusterAutoscaling]
	given fmtNetworkConfig: Format[Schema.NetworkConfig] = Json.format[Schema.NetworkConfig]
	given fmtMaxPodsConstraint: Format[Schema.MaxPodsConstraint] = Json.format[Schema.MaxPodsConstraint]
	given fmtResourceUsageExportConfig: Format[Schema.ResourceUsageExportConfig] = Json.format[Schema.ResourceUsageExportConfig]
	given fmtAuthenticatorGroupsConfig: Format[Schema.AuthenticatorGroupsConfig] = Json.format[Schema.AuthenticatorGroupsConfig]
	given fmtPrivateClusterConfig: Format[Schema.PrivateClusterConfig] = Json.format[Schema.PrivateClusterConfig]
	given fmtDatabaseEncryption: Format[Schema.DatabaseEncryption] = Json.format[Schema.DatabaseEncryption]
	given fmtVerticalPodAutoscaling: Format[Schema.VerticalPodAutoscaling] = Json.format[Schema.VerticalPodAutoscaling]
	given fmtShieldedNodes: Format[Schema.ShieldedNodes] = Json.format[Schema.ShieldedNodes]
	given fmtReleaseChannel: Format[Schema.ReleaseChannel] = Json.format[Schema.ReleaseChannel]
	given fmtWorkloadIdentityConfig: Format[Schema.WorkloadIdentityConfig] = Json.format[Schema.WorkloadIdentityConfig]
	given fmtMeshCertificates: Format[Schema.MeshCertificates] = Json.format[Schema.MeshCertificates]
	given fmtCostManagementConfig: Format[Schema.CostManagementConfig] = Json.format[Schema.CostManagementConfig]
	given fmtNotificationConfig: Format[Schema.NotificationConfig] = Json.format[Schema.NotificationConfig]
	given fmtConfidentialNodes: Format[Schema.ConfidentialNodes] = Json.format[Schema.ConfidentialNodes]
	given fmtIdentityServiceConfig: Format[Schema.IdentityServiceConfig] = Json.format[Schema.IdentityServiceConfig]
	given fmtClusterStatusEnum: Format[Schema.Cluster.StatusEnum] = JsonEnumFormat[Schema.Cluster.StatusEnum]
	given fmtStatusCondition: Format[Schema.StatusCondition] = Json.format[Schema.StatusCondition]
	given fmtAutopilot: Format[Schema.Autopilot] = Json.format[Schema.Autopilot]
	given fmtParentProductConfig: Format[Schema.ParentProductConfig] = Json.format[Schema.ParentProductConfig]
	given fmtNodePoolDefaults: Format[Schema.NodePoolDefaults] = Json.format[Schema.NodePoolDefaults]
	given fmtLoggingConfig: Format[Schema.LoggingConfig] = Json.format[Schema.LoggingConfig]
	given fmtMonitoringConfig: Format[Schema.MonitoringConfig] = Json.format[Schema.MonitoringConfig]
	given fmtNodePoolAutoConfig: Format[Schema.NodePoolAutoConfig] = Json.format[Schema.NodePoolAutoConfig]
	given fmtFleet: Format[Schema.Fleet] = Json.format[Schema.Fleet]
	given fmtSecurityPostureConfig: Format[Schema.SecurityPostureConfig] = Json.format[Schema.SecurityPostureConfig]
	given fmtControlPlaneEndpointsConfig: Format[Schema.ControlPlaneEndpointsConfig] = Json.format[Schema.ControlPlaneEndpointsConfig]
	given fmtK8sBetaAPIConfig: Format[Schema.K8sBetaAPIConfig] = Json.format[Schema.K8sBetaAPIConfig]
	given fmtEnterpriseConfig: Format[Schema.EnterpriseConfig] = Json.format[Schema.EnterpriseConfig]
	given fmtSecretManagerConfig: Format[Schema.SecretManagerConfig] = Json.format[Schema.SecretManagerConfig]
	given fmtCompliancePostureConfig: Format[Schema.CompliancePostureConfig] = Json.format[Schema.CompliancePostureConfig]
	given fmtUserManagedKeysConfig: Format[Schema.UserManagedKeysConfig] = Json.format[Schema.UserManagedKeysConfig]
	given fmtRBACBindingConfig: Format[Schema.RBACBindingConfig] = Json.format[Schema.RBACBindingConfig]
	given fmtAcceleratorConfig: Format[Schema.AcceleratorConfig] = Json.format[Schema.AcceleratorConfig]
	given fmtWorkloadMetadataConfig: Format[Schema.WorkloadMetadataConfig] = Json.format[Schema.WorkloadMetadataConfig]
	given fmtNodeTaint: Format[Schema.NodeTaint] = Json.format[Schema.NodeTaint]
	given fmtSandboxConfig: Format[Schema.SandboxConfig] = Json.format[Schema.SandboxConfig]
	given fmtReservationAffinity: Format[Schema.ReservationAffinity] = Json.format[Schema.ReservationAffinity]
	given fmtShieldedInstanceConfig: Format[Schema.ShieldedInstanceConfig] = Json.format[Schema.ShieldedInstanceConfig]
	given fmtLinuxNodeConfig: Format[Schema.LinuxNodeConfig] = Json.format[Schema.LinuxNodeConfig]
	given fmtNodeKubeletConfig: Format[Schema.NodeKubeletConfig] = Json.format[Schema.NodeKubeletConfig]
	given fmtGcfsConfig: Format[Schema.GcfsConfig] = Json.format[Schema.GcfsConfig]
	given fmtAdvancedMachineFeatures: Format[Schema.AdvancedMachineFeatures] = Json.format[Schema.AdvancedMachineFeatures]
	given fmtVirtualNIC: Format[Schema.VirtualNIC] = Json.format[Schema.VirtualNIC]
	given fmtFastSocket: Format[Schema.FastSocket] = Json.format[Schema.FastSocket]
	given fmtNodePoolLoggingConfig: Format[Schema.NodePoolLoggingConfig] = Json.format[Schema.NodePoolLoggingConfig]
	given fmtWindowsNodeConfig: Format[Schema.WindowsNodeConfig] = Json.format[Schema.WindowsNodeConfig]
	given fmtLocalNvmeSsdBlockConfig: Format[Schema.LocalNvmeSsdBlockConfig] = Json.format[Schema.LocalNvmeSsdBlockConfig]
	given fmtEphemeralStorageLocalSsdConfig: Format[Schema.EphemeralStorageLocalSsdConfig] = Json.format[Schema.EphemeralStorageLocalSsdConfig]
	given fmtSoleTenantConfig: Format[Schema.SoleTenantConfig] = Json.format[Schema.SoleTenantConfig]
	given fmtContainerdConfig: Format[Schema.ContainerdConfig] = Json.format[Schema.ContainerdConfig]
	given fmtResourceManagerTags: Format[Schema.ResourceManagerTags] = Json.format[Schema.ResourceManagerTags]
	given fmtSecondaryBootDisk: Format[Schema.SecondaryBootDisk] = Json.format[Schema.SecondaryBootDisk]
	given fmtSecondaryBootDiskUpdateStrategy: Format[Schema.SecondaryBootDiskUpdateStrategy] = Json.format[Schema.SecondaryBootDiskUpdateStrategy]
	given fmtNodeConfigLocalSsdEncryptionModeEnum: Format[Schema.NodeConfig.LocalSsdEncryptionModeEnum] = JsonEnumFormat[Schema.NodeConfig.LocalSsdEncryptionModeEnum]
	given fmtNodeConfigEffectiveCgroupModeEnum: Format[Schema.NodeConfig.EffectiveCgroupModeEnum] = JsonEnumFormat[Schema.NodeConfig.EffectiveCgroupModeEnum]
	given fmtGPUSharingConfig: Format[Schema.GPUSharingConfig] = Json.format[Schema.GPUSharingConfig]
	given fmtGPUDriverInstallationConfig: Format[Schema.GPUDriverInstallationConfig] = Json.format[Schema.GPUDriverInstallationConfig]
	given fmtGPUSharingConfigGpuSharingStrategyEnum: Format[Schema.GPUSharingConfig.GpuSharingStrategyEnum] = JsonEnumFormat[Schema.GPUSharingConfig.GpuSharingStrategyEnum]
	given fmtGPUDriverInstallationConfigGpuDriverVersionEnum: Format[Schema.GPUDriverInstallationConfig.GpuDriverVersionEnum] = JsonEnumFormat[Schema.GPUDriverInstallationConfig.GpuDriverVersionEnum]
	given fmtWorkloadMetadataConfigModeEnum: Format[Schema.WorkloadMetadataConfig.ModeEnum] = JsonEnumFormat[Schema.WorkloadMetadataConfig.ModeEnum]
	given fmtNodeTaintEffectEnum: Format[Schema.NodeTaint.EffectEnum] = JsonEnumFormat[Schema.NodeTaint.EffectEnum]
	given fmtSandboxConfigTypeEnum: Format[Schema.SandboxConfig.TypeEnum] = JsonEnumFormat[Schema.SandboxConfig.TypeEnum]
	given fmtReservationAffinityConsumeReservationTypeEnum: Format[Schema.ReservationAffinity.ConsumeReservationTypeEnum] = JsonEnumFormat[Schema.ReservationAffinity.ConsumeReservationTypeEnum]
	given fmtLinuxNodeConfigCgroupModeEnum: Format[Schema.LinuxNodeConfig.CgroupModeEnum] = JsonEnumFormat[Schema.LinuxNodeConfig.CgroupModeEnum]
	given fmtHugepagesConfig: Format[Schema.HugepagesConfig] = Json.format[Schema.HugepagesConfig]
	given fmtLoggingVariantConfig: Format[Schema.LoggingVariantConfig] = Json.format[Schema.LoggingVariantConfig]
	given fmtLoggingVariantConfigVariantEnum: Format[Schema.LoggingVariantConfig.VariantEnum] = JsonEnumFormat[Schema.LoggingVariantConfig.VariantEnum]
	given fmtWindowsNodeConfigOsVersionEnum: Format[Schema.WindowsNodeConfig.OsVersionEnum] = JsonEnumFormat[Schema.WindowsNodeConfig.OsVersionEnum]
	given fmtNodeAffinity: Format[Schema.NodeAffinity] = Json.format[Schema.NodeAffinity]
	given fmtNodeAffinityOperatorEnum: Format[Schema.NodeAffinity.OperatorEnum] = JsonEnumFormat[Schema.NodeAffinity.OperatorEnum]
	given fmtPrivateRegistryAccessConfig: Format[Schema.PrivateRegistryAccessConfig] = Json.format[Schema.PrivateRegistryAccessConfig]
	given fmtCertificateAuthorityDomainConfig: Format[Schema.CertificateAuthorityDomainConfig] = Json.format[Schema.CertificateAuthorityDomainConfig]
	given fmtGCPSecretManagerCertificateConfig: Format[Schema.GCPSecretManagerCertificateConfig] = Json.format[Schema.GCPSecretManagerCertificateConfig]
	given fmtSecondaryBootDiskModeEnum: Format[Schema.SecondaryBootDisk.ModeEnum] = JsonEnumFormat[Schema.SecondaryBootDisk.ModeEnum]
	given fmtClientCertificateConfig: Format[Schema.ClientCertificateConfig] = Json.format[Schema.ClientCertificateConfig]
	given fmtHttpLoadBalancing: Format[Schema.HttpLoadBalancing] = Json.format[Schema.HttpLoadBalancing]
	given fmtHorizontalPodAutoscaling: Format[Schema.HorizontalPodAutoscaling] = Json.format[Schema.HorizontalPodAutoscaling]
	given fmtKubernetesDashboard: Format[Schema.KubernetesDashboard] = Json.format[Schema.KubernetesDashboard]
	given fmtNetworkPolicyConfig: Format[Schema.NetworkPolicyConfig] = Json.format[Schema.NetworkPolicyConfig]
	given fmtCloudRunConfig: Format[Schema.CloudRunConfig] = Json.format[Schema.CloudRunConfig]
	given fmtDnsCacheConfig: Format[Schema.DnsCacheConfig] = Json.format[Schema.DnsCacheConfig]
	given fmtConfigConnectorConfig: Format[Schema.ConfigConnectorConfig] = Json.format[Schema.ConfigConnectorConfig]
	given fmtGcePersistentDiskCsiDriverConfig: Format[Schema.GcePersistentDiskCsiDriverConfig] = Json.format[Schema.GcePersistentDiskCsiDriverConfig]
	given fmtGcpFilestoreCsiDriverConfig: Format[Schema.GcpFilestoreCsiDriverConfig] = Json.format[Schema.GcpFilestoreCsiDriverConfig]
	given fmtGkeBackupAgentConfig: Format[Schema.GkeBackupAgentConfig] = Json.format[Schema.GkeBackupAgentConfig]
	given fmtGcsFuseCsiDriverConfig: Format[Schema.GcsFuseCsiDriverConfig] = Json.format[Schema.GcsFuseCsiDriverConfig]
	given fmtStatefulHAConfig: Format[Schema.StatefulHAConfig] = Json.format[Schema.StatefulHAConfig]
	given fmtParallelstoreCsiDriverConfig: Format[Schema.ParallelstoreCsiDriverConfig] = Json.format[Schema.ParallelstoreCsiDriverConfig]
	given fmtRayOperatorConfig: Format[Schema.RayOperatorConfig] = Json.format[Schema.RayOperatorConfig]
	given fmtCloudRunConfigLoadBalancerTypeEnum: Format[Schema.CloudRunConfig.LoadBalancerTypeEnum] = JsonEnumFormat[Schema.CloudRunConfig.LoadBalancerTypeEnum]
	given fmtRayClusterLoggingConfig: Format[Schema.RayClusterLoggingConfig] = Json.format[Schema.RayClusterLoggingConfig]
	given fmtRayClusterMonitoringConfig: Format[Schema.RayClusterMonitoringConfig] = Json.format[Schema.RayClusterMonitoringConfig]
	given fmtNodeNetworkConfig: Format[Schema.NodeNetworkConfig] = Json.format[Schema.NodeNetworkConfig]
	given fmtNodePoolStatusEnum: Format[Schema.NodePool.StatusEnum] = JsonEnumFormat[Schema.NodePool.StatusEnum]
	given fmtNodePoolAutoscaling: Format[Schema.NodePoolAutoscaling] = Json.format[Schema.NodePoolAutoscaling]
	given fmtNodeManagement: Format[Schema.NodeManagement] = Json.format[Schema.NodeManagement]
	given fmtUpgradeSettings: Format[Schema.UpgradeSettings] = Json.format[Schema.UpgradeSettings]
	given fmtPlacementPolicy: Format[Schema.PlacementPolicy] = Json.format[Schema.PlacementPolicy]
	given fmtUpdateInfo: Format[Schema.UpdateInfo] = Json.format[Schema.UpdateInfo]
	given fmtQueuedProvisioning: Format[Schema.QueuedProvisioning] = Json.format[Schema.QueuedProvisioning]
	given fmtBestEffortProvisioning: Format[Schema.BestEffortProvisioning] = Json.format[Schema.BestEffortProvisioning]
	given fmtNetworkPerformanceConfig: Format[Schema.NetworkPerformanceConfig] = Json.format[Schema.NetworkPerformanceConfig]
	given fmtPodCIDROverprovisionConfig: Format[Schema.PodCIDROverprovisionConfig] = Json.format[Schema.PodCIDROverprovisionConfig]
	given fmtAdditionalNodeNetworkConfig: Format[Schema.AdditionalNodeNetworkConfig] = Json.format[Schema.AdditionalNodeNetworkConfig]
	given fmtAdditionalPodNetworkConfig: Format[Schema.AdditionalPodNetworkConfig] = Json.format[Schema.AdditionalPodNetworkConfig]
	given fmtNetworkPerformanceConfigTotalEgressBandwidthTierEnum: Format[Schema.NetworkPerformanceConfig.TotalEgressBandwidthTierEnum] = JsonEnumFormat[Schema.NetworkPerformanceConfig.TotalEgressBandwidthTierEnum]
	given fmtNodePoolAutoscalingLocationPolicyEnum: Format[Schema.NodePoolAutoscaling.LocationPolicyEnum] = JsonEnumFormat[Schema.NodePoolAutoscaling.LocationPolicyEnum]
	given fmtAutoUpgradeOptions: Format[Schema.AutoUpgradeOptions] = Json.format[Schema.AutoUpgradeOptions]
	given fmtStatusConditionCodeEnum: Format[Schema.StatusCondition.CodeEnum] = JsonEnumFormat[Schema.StatusCondition.CodeEnum]
	given fmtStatusConditionCanonicalCodeEnum: Format[Schema.StatusCondition.CanonicalCodeEnum] = JsonEnumFormat[Schema.StatusCondition.CanonicalCodeEnum]
	given fmtUpgradeSettingsStrategyEnum: Format[Schema.UpgradeSettings.StrategyEnum] = JsonEnumFormat[Schema.UpgradeSettings.StrategyEnum]
	given fmtBlueGreenSettings: Format[Schema.BlueGreenSettings] = Json.format[Schema.BlueGreenSettings]
	given fmtStandardRolloutPolicy: Format[Schema.StandardRolloutPolicy] = Json.format[Schema.StandardRolloutPolicy]
	given fmtPlacementPolicyTypeEnum: Format[Schema.PlacementPolicy.TypeEnum] = JsonEnumFormat[Schema.PlacementPolicy.TypeEnum]
	given fmtBlueGreenInfo: Format[Schema.BlueGreenInfo] = Json.format[Schema.BlueGreenInfo]
	given fmtBlueGreenInfoPhaseEnum: Format[Schema.BlueGreenInfo.PhaseEnum] = JsonEnumFormat[Schema.BlueGreenInfo.PhaseEnum]
	given fmtNetworkPolicyProviderEnum: Format[Schema.NetworkPolicy.ProviderEnum] = JsonEnumFormat[Schema.NetworkPolicy.ProviderEnum]
	given fmtIPAllocationPolicyStackTypeEnum: Format[Schema.IPAllocationPolicy.StackTypeEnum] = JsonEnumFormat[Schema.IPAllocationPolicy.StackTypeEnum]
	given fmtIPAllocationPolicyIpv6AccessTypeEnum: Format[Schema.IPAllocationPolicy.Ipv6AccessTypeEnum] = JsonEnumFormat[Schema.IPAllocationPolicy.Ipv6AccessTypeEnum]
	given fmtAdditionalPodRangesConfig: Format[Schema.AdditionalPodRangesConfig] = Json.format[Schema.AdditionalPodRangesConfig]
	given fmtRangeInfo: Format[Schema.RangeInfo] = Json.format[Schema.RangeInfo]
	given fmtCidrBlock: Format[Schema.CidrBlock] = Json.format[Schema.CidrBlock]
	given fmtMaintenanceWindow: Format[Schema.MaintenanceWindow] = Json.format[Schema.MaintenanceWindow]
	given fmtDailyMaintenanceWindow: Format[Schema.DailyMaintenanceWindow] = Json.format[Schema.DailyMaintenanceWindow]
	given fmtRecurringTimeWindow: Format[Schema.RecurringTimeWindow] = Json.format[Schema.RecurringTimeWindow]
	given fmtTimeWindow: Format[Schema.TimeWindow] = Json.format[Schema.TimeWindow]
	given fmtMaintenanceExclusionOptions: Format[Schema.MaintenanceExclusionOptions] = Json.format[Schema.MaintenanceExclusionOptions]
	given fmtMaintenanceExclusionOptionsScopeEnum: Format[Schema.MaintenanceExclusionOptions.ScopeEnum] = JsonEnumFormat[Schema.MaintenanceExclusionOptions.ScopeEnum]
	given fmtBinaryAuthorizationEvaluationModeEnum: Format[Schema.BinaryAuthorization.EvaluationModeEnum] = JsonEnumFormat[Schema.BinaryAuthorization.EvaluationModeEnum]
	given fmtResourceLimit: Format[Schema.ResourceLimit] = Json.format[Schema.ResourceLimit]
	given fmtClusterAutoscalingAutoscalingProfileEnum: Format[Schema.ClusterAutoscaling.AutoscalingProfileEnum] = JsonEnumFormat[Schema.ClusterAutoscaling.AutoscalingProfileEnum]
	given fmtAutoprovisioningNodePoolDefaults: Format[Schema.AutoprovisioningNodePoolDefaults] = Json.format[Schema.AutoprovisioningNodePoolDefaults]
	given fmtDefaultSnatStatus: Format[Schema.DefaultSnatStatus] = Json.format[Schema.DefaultSnatStatus]
	given fmtNetworkConfigDatapathProviderEnum: Format[Schema.NetworkConfig.DatapathProviderEnum] = JsonEnumFormat[Schema.NetworkConfig.DatapathProviderEnum]
	given fmtNetworkConfigPrivateIpv6GoogleAccessEnum: Format[Schema.NetworkConfig.PrivateIpv6GoogleAccessEnum] = JsonEnumFormat[Schema.NetworkConfig.PrivateIpv6GoogleAccessEnum]
	given fmtDNSConfig: Format[Schema.DNSConfig] = Json.format[Schema.DNSConfig]
	given fmtServiceExternalIPsConfig: Format[Schema.ServiceExternalIPsConfig] = Json.format[Schema.ServiceExternalIPsConfig]
	given fmtGatewayAPIConfig: Format[Schema.GatewayAPIConfig] = Json.format[Schema.GatewayAPIConfig]
	given fmtClusterNetworkPerformanceConfig: Format[Schema.ClusterNetworkPerformanceConfig] = Json.format[Schema.ClusterNetworkPerformanceConfig]
	given fmtNetworkConfigInTransitEncryptionConfigEnum: Format[Schema.NetworkConfig.InTransitEncryptionConfigEnum] = JsonEnumFormat[Schema.NetworkConfig.InTransitEncryptionConfigEnum]
	given fmtDNSConfigClusterDnsEnum: Format[Schema.DNSConfig.ClusterDnsEnum] = JsonEnumFormat[Schema.DNSConfig.ClusterDnsEnum]
	given fmtDNSConfigClusterDnsScopeEnum: Format[Schema.DNSConfig.ClusterDnsScopeEnum] = JsonEnumFormat[Schema.DNSConfig.ClusterDnsScopeEnum]
	given fmtGatewayAPIConfigChannelEnum: Format[Schema.GatewayAPIConfig.ChannelEnum] = JsonEnumFormat[Schema.GatewayAPIConfig.ChannelEnum]
	given fmtClusterNetworkPerformanceConfigTotalEgressBandwidthTierEnum: Format[Schema.ClusterNetworkPerformanceConfig.TotalEgressBandwidthTierEnum] = JsonEnumFormat[Schema.ClusterNetworkPerformanceConfig.TotalEgressBandwidthTierEnum]
	given fmtBigQueryDestination: Format[Schema.BigQueryDestination] = Json.format[Schema.BigQueryDestination]
	given fmtConsumptionMeteringConfig: Format[Schema.ConsumptionMeteringConfig] = Json.format[Schema.ConsumptionMeteringConfig]
	given fmtPrivateClusterMasterGlobalAccessConfig: Format[Schema.PrivateClusterMasterGlobalAccessConfig] = Json.format[Schema.PrivateClusterMasterGlobalAccessConfig]
	given fmtDatabaseEncryptionStateEnum: Format[Schema.DatabaseEncryption.StateEnum] = JsonEnumFormat[Schema.DatabaseEncryption.StateEnum]
	given fmtDatabaseEncryptionCurrentStateEnum: Format[Schema.DatabaseEncryption.CurrentStateEnum] = JsonEnumFormat[Schema.DatabaseEncryption.CurrentStateEnum]
	given fmtOperationError: Format[Schema.OperationError] = Json.format[Schema.OperationError]
	given fmtReleaseChannelChannelEnum: Format[Schema.ReleaseChannel.ChannelEnum] = JsonEnumFormat[Schema.ReleaseChannel.ChannelEnum]
	given fmtPubSub: Format[Schema.PubSub] = Json.format[Schema.PubSub]
	given fmtFilter: Format[Schema.Filter] = Json.format[Schema.Filter]
	given fmtFilterEventTypeEnum: Format[Schema.Filter.EventTypeEnum] = JsonEnumFormat[Schema.Filter.EventTypeEnum]
	given fmtWorkloadPolicyConfig: Format[Schema.WorkloadPolicyConfig] = Json.format[Schema.WorkloadPolicyConfig]
	given fmtNodeConfigDefaults: Format[Schema.NodeConfigDefaults] = Json.format[Schema.NodeConfigDefaults]
	given fmtLoggingComponentConfig: Format[Schema.LoggingComponentConfig] = Json.format[Schema.LoggingComponentConfig]
	given fmtLoggingComponentConfigEnableComponentsEnum: Format[Schema.LoggingComponentConfig.EnableComponentsEnum] = JsonEnumFormat[Schema.LoggingComponentConfig.EnableComponentsEnum]
	given fmtMonitoringComponentConfig: Format[Schema.MonitoringComponentConfig] = Json.format[Schema.MonitoringComponentConfig]
	given fmtManagedPrometheusConfig: Format[Schema.ManagedPrometheusConfig] = Json.format[Schema.ManagedPrometheusConfig]
	given fmtAdvancedDatapathObservabilityConfig: Format[Schema.AdvancedDatapathObservabilityConfig] = Json.format[Schema.AdvancedDatapathObservabilityConfig]
	given fmtMonitoringComponentConfigEnableComponentsEnum: Format[Schema.MonitoringComponentConfig.EnableComponentsEnum] = JsonEnumFormat[Schema.MonitoringComponentConfig.EnableComponentsEnum]
	given fmtAdvancedDatapathObservabilityConfigRelayModeEnum: Format[Schema.AdvancedDatapathObservabilityConfig.RelayModeEnum] = JsonEnumFormat[Schema.AdvancedDatapathObservabilityConfig.RelayModeEnum]
	given fmtNetworkTags: Format[Schema.NetworkTags] = Json.format[Schema.NetworkTags]
	given fmtSecurityPostureConfigModeEnum: Format[Schema.SecurityPostureConfig.ModeEnum] = JsonEnumFormat[Schema.SecurityPostureConfig.ModeEnum]
	given fmtSecurityPostureConfigVulnerabilityModeEnum: Format[Schema.SecurityPostureConfig.VulnerabilityModeEnum] = JsonEnumFormat[Schema.SecurityPostureConfig.VulnerabilityModeEnum]
	given fmtDNSEndpointConfig: Format[Schema.DNSEndpointConfig] = Json.format[Schema.DNSEndpointConfig]
	given fmtIPEndpointsConfig: Format[Schema.IPEndpointsConfig] = Json.format[Schema.IPEndpointsConfig]
	given fmtEnterpriseConfigClusterTierEnum: Format[Schema.EnterpriseConfig.ClusterTierEnum] = JsonEnumFormat[Schema.EnterpriseConfig.ClusterTierEnum]
	given fmtEnterpriseConfigDesiredTierEnum: Format[Schema.EnterpriseConfig.DesiredTierEnum] = JsonEnumFormat[Schema.EnterpriseConfig.DesiredTierEnum]
	given fmtCompliancePostureConfigModeEnum: Format[Schema.CompliancePostureConfig.ModeEnum] = JsonEnumFormat[Schema.CompliancePostureConfig.ModeEnum]
	given fmtComplianceStandard: Format[Schema.ComplianceStandard] = Json.format[Schema.ComplianceStandard]
	given fmtCreateClusterRequest: Format[Schema.CreateClusterRequest] = Json.format[Schema.CreateClusterRequest]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtOperationOperationTypeEnum: Format[Schema.Operation.OperationTypeEnum] = JsonEnumFormat[Schema.Operation.OperationTypeEnum]
	given fmtOperationStatusEnum: Format[Schema.Operation.StatusEnum] = JsonEnumFormat[Schema.Operation.StatusEnum]
	given fmtOperationProgress: Format[Schema.OperationProgress] = Json.format[Schema.OperationProgress]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtOperationProgressStatusEnum: Format[Schema.OperationProgress.StatusEnum] = JsonEnumFormat[Schema.OperationProgress.StatusEnum]
	given fmtMetric: Format[Schema.Metric] = Json.format[Schema.Metric]
	given fmtUpdateClusterRequest: Format[Schema.UpdateClusterRequest] = Json.format[Schema.UpdateClusterRequest]
	given fmtClusterUpdate: Format[Schema.ClusterUpdate] = Json.format[Schema.ClusterUpdate]
	given fmtIntraNodeVisibilityConfig: Format[Schema.IntraNodeVisibilityConfig] = Json.format[Schema.IntraNodeVisibilityConfig]
	given fmtILBSubsettingConfig: Format[Schema.ILBSubsettingConfig] = Json.format[Schema.ILBSubsettingConfig]
	given fmtClusterUpdateDesiredDatapathProviderEnum: Format[Schema.ClusterUpdate.DesiredDatapathProviderEnum] = JsonEnumFormat[Schema.ClusterUpdate.DesiredDatapathProviderEnum]
	given fmtClusterUpdateDesiredPrivateIpv6GoogleAccessEnum: Format[Schema.ClusterUpdate.DesiredPrivateIpv6GoogleAccessEnum] = JsonEnumFormat[Schema.ClusterUpdate.DesiredPrivateIpv6GoogleAccessEnum]
	given fmtClusterUpdateDesiredStackTypeEnum: Format[Schema.ClusterUpdate.DesiredStackTypeEnum] = JsonEnumFormat[Schema.ClusterUpdate.DesiredStackTypeEnum]
	given fmtClusterUpdateDesiredInTransitEncryptionConfigEnum: Format[Schema.ClusterUpdate.DesiredInTransitEncryptionConfigEnum] = JsonEnumFormat[Schema.ClusterUpdate.DesiredInTransitEncryptionConfigEnum]
	given fmtDesiredEnterpriseConfig: Format[Schema.DesiredEnterpriseConfig] = Json.format[Schema.DesiredEnterpriseConfig]
	given fmtDesiredEnterpriseConfigDesiredTierEnum: Format[Schema.DesiredEnterpriseConfig.DesiredTierEnum] = JsonEnumFormat[Schema.DesiredEnterpriseConfig.DesiredTierEnum]
	given fmtUpdateNodePoolRequest: Format[Schema.UpdateNodePoolRequest] = Json.format[Schema.UpdateNodePoolRequest]
	given fmtNodeTaints: Format[Schema.NodeTaints] = Json.format[Schema.NodeTaints]
	given fmtNodeLabels: Format[Schema.NodeLabels] = Json.format[Schema.NodeLabels]
	given fmtResourceLabels: Format[Schema.ResourceLabels] = Json.format[Schema.ResourceLabels]
	given fmtSetNodePoolAutoscalingRequest: Format[Schema.SetNodePoolAutoscalingRequest] = Json.format[Schema.SetNodePoolAutoscalingRequest]
	given fmtSetLoggingServiceRequest: Format[Schema.SetLoggingServiceRequest] = Json.format[Schema.SetLoggingServiceRequest]
	given fmtSetMonitoringServiceRequest: Format[Schema.SetMonitoringServiceRequest] = Json.format[Schema.SetMonitoringServiceRequest]
	given fmtSetAddonsConfigRequest: Format[Schema.SetAddonsConfigRequest] = Json.format[Schema.SetAddonsConfigRequest]
	given fmtSetLocationsRequest: Format[Schema.SetLocationsRequest] = Json.format[Schema.SetLocationsRequest]
	given fmtUpdateMasterRequest: Format[Schema.UpdateMasterRequest] = Json.format[Schema.UpdateMasterRequest]
	given fmtSetMasterAuthRequest: Format[Schema.SetMasterAuthRequest] = Json.format[Schema.SetMasterAuthRequest]
	given fmtSetMasterAuthRequestActionEnum: Format[Schema.SetMasterAuthRequest.ActionEnum] = JsonEnumFormat[Schema.SetMasterAuthRequest.ActionEnum]
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtServerConfig: Format[Schema.ServerConfig] = Json.format[Schema.ServerConfig]
	given fmtReleaseChannelConfig: Format[Schema.ReleaseChannelConfig] = Json.format[Schema.ReleaseChannelConfig]
	given fmtReleaseChannelConfigChannelEnum: Format[Schema.ReleaseChannelConfig.ChannelEnum] = JsonEnumFormat[Schema.ReleaseChannelConfig.ChannelEnum]
	given fmtGetOpenIDConfigResponse: Format[Schema.GetOpenIDConfigResponse] = Json.format[Schema.GetOpenIDConfigResponse]
	given fmtHttpCacheControlResponseHeader: Format[Schema.HttpCacheControlResponseHeader] = Json.format[Schema.HttpCacheControlResponseHeader]
	given fmtGetJSONWebKeysResponse: Format[Schema.GetJSONWebKeysResponse] = Json.format[Schema.GetJSONWebKeysResponse]
	given fmtJwk: Format[Schema.Jwk] = Json.format[Schema.Jwk]
	given fmtListNodePoolsResponse: Format[Schema.ListNodePoolsResponse] = Json.format[Schema.ListNodePoolsResponse]
	given fmtCreateNodePoolRequest: Format[Schema.CreateNodePoolRequest] = Json.format[Schema.CreateNodePoolRequest]
	given fmtCompleteNodePoolUpgradeRequest: Format[Schema.CompleteNodePoolUpgradeRequest] = Json.format[Schema.CompleteNodePoolUpgradeRequest]
	given fmtRollbackNodePoolUpgradeRequest: Format[Schema.RollbackNodePoolUpgradeRequest] = Json.format[Schema.RollbackNodePoolUpgradeRequest]
	given fmtSetNodePoolManagementRequest: Format[Schema.SetNodePoolManagementRequest] = Json.format[Schema.SetNodePoolManagementRequest]
	given fmtSetLabelsRequest: Format[Schema.SetLabelsRequest] = Json.format[Schema.SetLabelsRequest]
	given fmtSetLegacyAbacRequest: Format[Schema.SetLegacyAbacRequest] = Json.format[Schema.SetLegacyAbacRequest]
	given fmtStartIPRotationRequest: Format[Schema.StartIPRotationRequest] = Json.format[Schema.StartIPRotationRequest]
	given fmtCompleteIPRotationRequest: Format[Schema.CompleteIPRotationRequest] = Json.format[Schema.CompleteIPRotationRequest]
	given fmtSetNodePoolSizeRequest: Format[Schema.SetNodePoolSizeRequest] = Json.format[Schema.SetNodePoolSizeRequest]
	given fmtSetNetworkPolicyRequest: Format[Schema.SetNetworkPolicyRequest] = Json.format[Schema.SetNetworkPolicyRequest]
	given fmtSetMaintenancePolicyRequest: Format[Schema.SetMaintenancePolicyRequest] = Json.format[Schema.SetMaintenancePolicyRequest]
	given fmtListUsableSubnetworksResponse: Format[Schema.ListUsableSubnetworksResponse] = Json.format[Schema.ListUsableSubnetworksResponse]
	given fmtUsableSubnetwork: Format[Schema.UsableSubnetwork] = Json.format[Schema.UsableSubnetwork]
	given fmtUsableSubnetworkSecondaryRange: Format[Schema.UsableSubnetworkSecondaryRange] = Json.format[Schema.UsableSubnetworkSecondaryRange]
	given fmtUsableSubnetworkSecondaryRangeStatusEnum: Format[Schema.UsableSubnetworkSecondaryRange.StatusEnum] = JsonEnumFormat[Schema.UsableSubnetworkSecondaryRange.StatusEnum]
	given fmtCheckAutopilotCompatibilityResponse: Format[Schema.CheckAutopilotCompatibilityResponse] = Json.format[Schema.CheckAutopilotCompatibilityResponse]
	given fmtAutopilotCompatibilityIssue: Format[Schema.AutopilotCompatibilityIssue] = Json.format[Schema.AutopilotCompatibilityIssue]
	given fmtAutopilotCompatibilityIssueIncompatibilityTypeEnum: Format[Schema.AutopilotCompatibilityIssue.IncompatibilityTypeEnum] = JsonEnumFormat[Schema.AutopilotCompatibilityIssue.IncompatibilityTypeEnum]
	given fmtUpgradeEvent: Format[Schema.UpgradeEvent] = Json.format[Schema.UpgradeEvent]
	given fmtUpgradeEventResourceTypeEnum: Format[Schema.UpgradeEvent.ResourceTypeEnum] = JsonEnumFormat[Schema.UpgradeEvent.ResourceTypeEnum]
	given fmtUpgradeInfoEvent: Format[Schema.UpgradeInfoEvent] = Json.format[Schema.UpgradeInfoEvent]
	given fmtUpgradeInfoEventResourceTypeEnum: Format[Schema.UpgradeInfoEvent.ResourceTypeEnum] = JsonEnumFormat[Schema.UpgradeInfoEvent.ResourceTypeEnum]
	given fmtUpgradeInfoEventStateEnum: Format[Schema.UpgradeInfoEvent.StateEnum] = JsonEnumFormat[Schema.UpgradeInfoEvent.StateEnum]
	given fmtUpgradeAvailableEvent: Format[Schema.UpgradeAvailableEvent] = Json.format[Schema.UpgradeAvailableEvent]
	given fmtUpgradeAvailableEventResourceTypeEnum: Format[Schema.UpgradeAvailableEvent.ResourceTypeEnum] = JsonEnumFormat[Schema.UpgradeAvailableEvent.ResourceTypeEnum]
	given fmtSecurityBulletinEvent: Format[Schema.SecurityBulletinEvent] = Json.format[Schema.SecurityBulletinEvent]
}
