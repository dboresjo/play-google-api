package com.boresjo.play.api.google.gkeonprem

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtBareMetalCluster: Format[Schema.BareMetalCluster] = Json.format[Schema.BareMetalCluster]
	given fmtBareMetalClusterStateEnum: Format[Schema.BareMetalCluster.StateEnum] = JsonEnumFormat[Schema.BareMetalCluster.StateEnum]
	given fmtBareMetalNetworkConfig: Format[Schema.BareMetalNetworkConfig] = Json.format[Schema.BareMetalNetworkConfig]
	given fmtBareMetalControlPlaneConfig: Format[Schema.BareMetalControlPlaneConfig] = Json.format[Schema.BareMetalControlPlaneConfig]
	given fmtBareMetalLoadBalancerConfig: Format[Schema.BareMetalLoadBalancerConfig] = Json.format[Schema.BareMetalLoadBalancerConfig]
	given fmtBareMetalStorageConfig: Format[Schema.BareMetalStorageConfig] = Json.format[Schema.BareMetalStorageConfig]
	given fmtBareMetalProxyConfig: Format[Schema.BareMetalProxyConfig] = Json.format[Schema.BareMetalProxyConfig]
	given fmtBareMetalClusterOperationsConfig: Format[Schema.BareMetalClusterOperationsConfig] = Json.format[Schema.BareMetalClusterOperationsConfig]
	given fmtBareMetalMaintenanceConfig: Format[Schema.BareMetalMaintenanceConfig] = Json.format[Schema.BareMetalMaintenanceConfig]
	given fmtBareMetalWorkloadNodeConfig: Format[Schema.BareMetalWorkloadNodeConfig] = Json.format[Schema.BareMetalWorkloadNodeConfig]
	given fmtFleet: Format[Schema.Fleet] = Json.format[Schema.Fleet]
	given fmtResourceStatus: Format[Schema.ResourceStatus] = Json.format[Schema.ResourceStatus]
	given fmtValidationCheck: Format[Schema.ValidationCheck] = Json.format[Schema.ValidationCheck]
	given fmtBareMetalSecurityConfig: Format[Schema.BareMetalSecurityConfig] = Json.format[Schema.BareMetalSecurityConfig]
	given fmtBareMetalMaintenanceStatus: Format[Schema.BareMetalMaintenanceStatus] = Json.format[Schema.BareMetalMaintenanceStatus]
	given fmtBareMetalNodeAccessConfig: Format[Schema.BareMetalNodeAccessConfig] = Json.format[Schema.BareMetalNodeAccessConfig]
	given fmtBareMetalOsEnvironmentConfig: Format[Schema.BareMetalOsEnvironmentConfig] = Json.format[Schema.BareMetalOsEnvironmentConfig]
	given fmtBinaryAuthorization: Format[Schema.BinaryAuthorization] = Json.format[Schema.BinaryAuthorization]
	given fmtBareMetalClusterUpgradePolicy: Format[Schema.BareMetalClusterUpgradePolicy] = Json.format[Schema.BareMetalClusterUpgradePolicy]
	given fmtBareMetalIslandModeCidrConfig: Format[Schema.BareMetalIslandModeCidrConfig] = Json.format[Schema.BareMetalIslandModeCidrConfig]
	given fmtBareMetalMultipleNetworkInterfacesConfig: Format[Schema.BareMetalMultipleNetworkInterfacesConfig] = Json.format[Schema.BareMetalMultipleNetworkInterfacesConfig]
	given fmtBareMetalSrIovConfig: Format[Schema.BareMetalSrIovConfig] = Json.format[Schema.BareMetalSrIovConfig]
	given fmtBareMetalControlPlaneNodePoolConfig: Format[Schema.BareMetalControlPlaneNodePoolConfig] = Json.format[Schema.BareMetalControlPlaneNodePoolConfig]
	given fmtBareMetalApiServerArgument: Format[Schema.BareMetalApiServerArgument] = Json.format[Schema.BareMetalApiServerArgument]
	given fmtBareMetalNodePoolConfig: Format[Schema.BareMetalNodePoolConfig] = Json.format[Schema.BareMetalNodePoolConfig]
	given fmtBareMetalNodeConfig: Format[Schema.BareMetalNodeConfig] = Json.format[Schema.BareMetalNodeConfig]
	given fmtBareMetalNodePoolConfigOperatingSystemEnum: Format[Schema.BareMetalNodePoolConfig.OperatingSystemEnum] = JsonEnumFormat[Schema.BareMetalNodePoolConfig.OperatingSystemEnum]
	given fmtNodeTaint: Format[Schema.NodeTaint] = Json.format[Schema.NodeTaint]
	given fmtBareMetalKubeletConfig: Format[Schema.BareMetalKubeletConfig] = Json.format[Schema.BareMetalKubeletConfig]
	given fmtNodeTaintEffectEnum: Format[Schema.NodeTaint.EffectEnum] = JsonEnumFormat[Schema.NodeTaint.EffectEnum]
	given fmtBareMetalVipConfig: Format[Schema.BareMetalVipConfig] = Json.format[Schema.BareMetalVipConfig]
	given fmtBareMetalPortConfig: Format[Schema.BareMetalPortConfig] = Json.format[Schema.BareMetalPortConfig]
	given fmtBareMetalMetalLbConfig: Format[Schema.BareMetalMetalLbConfig] = Json.format[Schema.BareMetalMetalLbConfig]
	given fmtBareMetalManualLbConfig: Format[Schema.BareMetalManualLbConfig] = Json.format[Schema.BareMetalManualLbConfig]
	given fmtBareMetalBgpLbConfig: Format[Schema.BareMetalBgpLbConfig] = Json.format[Schema.BareMetalBgpLbConfig]
	given fmtBareMetalLoadBalancerAddressPool: Format[Schema.BareMetalLoadBalancerAddressPool] = Json.format[Schema.BareMetalLoadBalancerAddressPool]
	given fmtBareMetalLoadBalancerNodePoolConfig: Format[Schema.BareMetalLoadBalancerNodePoolConfig] = Json.format[Schema.BareMetalLoadBalancerNodePoolConfig]
	given fmtBareMetalBgpPeerConfig: Format[Schema.BareMetalBgpPeerConfig] = Json.format[Schema.BareMetalBgpPeerConfig]
	given fmtBareMetalLvpShareConfig: Format[Schema.BareMetalLvpShareConfig] = Json.format[Schema.BareMetalLvpShareConfig]
	given fmtBareMetalLvpConfig: Format[Schema.BareMetalLvpConfig] = Json.format[Schema.BareMetalLvpConfig]
	given fmtBareMetalWorkloadNodeConfigContainerRuntimeEnum: Format[Schema.BareMetalWorkloadNodeConfig.ContainerRuntimeEnum] = JsonEnumFormat[Schema.BareMetalWorkloadNodeConfig.ContainerRuntimeEnum]
	given fmtResourceCondition: Format[Schema.ResourceCondition] = Json.format[Schema.ResourceCondition]
	given fmtVersions: Format[Schema.Versions] = Json.format[Schema.Versions]
	given fmtResourceConditionStateEnum: Format[Schema.ResourceCondition.StateEnum] = JsonEnumFormat[Schema.ResourceCondition.StateEnum]
	given fmtVersion: Format[Schema.Version] = Json.format[Schema.Version]
	given fmtValidationCheckOptionEnum: Format[Schema.ValidationCheck.OptionEnum] = JsonEnumFormat[Schema.ValidationCheck.OptionEnum]
	given fmtValidationCheckStatus: Format[Schema.ValidationCheckStatus] = Json.format[Schema.ValidationCheckStatus]
	given fmtValidationCheckScenarioEnum: Format[Schema.ValidationCheck.ScenarioEnum] = JsonEnumFormat[Schema.ValidationCheck.ScenarioEnum]
	given fmtValidationCheckResult: Format[Schema.ValidationCheckResult] = Json.format[Schema.ValidationCheckResult]
	given fmtValidationCheckResultStateEnum: Format[Schema.ValidationCheckResult.StateEnum] = JsonEnumFormat[Schema.ValidationCheckResult.StateEnum]
	given fmtAuthorization: Format[Schema.Authorization] = Json.format[Schema.Authorization]
	given fmtClusterUser: Format[Schema.ClusterUser] = Json.format[Schema.ClusterUser]
	given fmtBareMetalMachineDrainStatus: Format[Schema.BareMetalMachineDrainStatus] = Json.format[Schema.BareMetalMachineDrainStatus]
	given fmtBareMetalDrainingMachine: Format[Schema.BareMetalDrainingMachine] = Json.format[Schema.BareMetalDrainingMachine]
	given fmtBareMetalDrainedMachine: Format[Schema.BareMetalDrainedMachine] = Json.format[Schema.BareMetalDrainedMachine]
	given fmtBinaryAuthorizationEvaluationModeEnum: Format[Schema.BinaryAuthorization.EvaluationModeEnum] = JsonEnumFormat[Schema.BinaryAuthorization.EvaluationModeEnum]
	given fmtBareMetalClusterUpgradePolicyPolicyEnum: Format[Schema.BareMetalClusterUpgradePolicy.PolicyEnum] = JsonEnumFormat[Schema.BareMetalClusterUpgradePolicy.PolicyEnum]
	given fmtEnrollBareMetalClusterRequest: Format[Schema.EnrollBareMetalClusterRequest] = Json.format[Schema.EnrollBareMetalClusterRequest]
	given fmtListBareMetalClustersResponse: Format[Schema.ListBareMetalClustersResponse] = Json.format[Schema.ListBareMetalClustersResponse]
	given fmtQueryBareMetalVersionConfigResponse: Format[Schema.QueryBareMetalVersionConfigResponse] = Json.format[Schema.QueryBareMetalVersionConfigResponse]
	given fmtBareMetalVersionInfo: Format[Schema.BareMetalVersionInfo] = Json.format[Schema.BareMetalVersionInfo]
	given fmtUpgradeDependency: Format[Schema.UpgradeDependency] = Json.format[Schema.UpgradeDependency]
	given fmtEnrollVmwareClusterRequest: Format[Schema.EnrollVmwareClusterRequest] = Json.format[Schema.EnrollVmwareClusterRequest]
	given fmtVmwareCluster: Format[Schema.VmwareCluster] = Json.format[Schema.VmwareCluster]
	given fmtVmwareClusterStateEnum: Format[Schema.VmwareCluster.StateEnum] = JsonEnumFormat[Schema.VmwareCluster.StateEnum]
	given fmtVmwareControlPlaneNodeConfig: Format[Schema.VmwareControlPlaneNodeConfig] = Json.format[Schema.VmwareControlPlaneNodeConfig]
	given fmtVmwareAAGConfig: Format[Schema.VmwareAAGConfig] = Json.format[Schema.VmwareAAGConfig]
	given fmtVmwareStorageConfig: Format[Schema.VmwareStorageConfig] = Json.format[Schema.VmwareStorageConfig]
	given fmtVmwareNetworkConfig: Format[Schema.VmwareNetworkConfig] = Json.format[Schema.VmwareNetworkConfig]
	given fmtVmwareLoadBalancerConfig: Format[Schema.VmwareLoadBalancerConfig] = Json.format[Schema.VmwareLoadBalancerConfig]
	given fmtVmwareVCenterConfig: Format[Schema.VmwareVCenterConfig] = Json.format[Schema.VmwareVCenterConfig]
	given fmtVmwareDataplaneV2Config: Format[Schema.VmwareDataplaneV2Config] = Json.format[Schema.VmwareDataplaneV2Config]
	given fmtVmwareAutoRepairConfig: Format[Schema.VmwareAutoRepairConfig] = Json.format[Schema.VmwareAutoRepairConfig]
	given fmtVmwareClusterUpgradePolicy: Format[Schema.VmwareClusterUpgradePolicy] = Json.format[Schema.VmwareClusterUpgradePolicy]
	given fmtVmwareAutoResizeConfig: Format[Schema.VmwareAutoResizeConfig] = Json.format[Schema.VmwareAutoResizeConfig]
	given fmtVmwareControlPlaneVsphereConfig: Format[Schema.VmwareControlPlaneVsphereConfig] = Json.format[Schema.VmwareControlPlaneVsphereConfig]
	given fmtVmwareStaticIpConfig: Format[Schema.VmwareStaticIpConfig] = Json.format[Schema.VmwareStaticIpConfig]
	given fmtVmwareDhcpIpConfig: Format[Schema.VmwareDhcpIpConfig] = Json.format[Schema.VmwareDhcpIpConfig]
	given fmtVmwareHostConfig: Format[Schema.VmwareHostConfig] = Json.format[Schema.VmwareHostConfig]
	given fmtVmwareControlPlaneV2Config: Format[Schema.VmwareControlPlaneV2Config] = Json.format[Schema.VmwareControlPlaneV2Config]
	given fmtVmwareIpBlock: Format[Schema.VmwareIpBlock] = Json.format[Schema.VmwareIpBlock]
	given fmtVmwareHostIp: Format[Schema.VmwareHostIp] = Json.format[Schema.VmwareHostIp]
	given fmtVmwareVipConfig: Format[Schema.VmwareVipConfig] = Json.format[Schema.VmwareVipConfig]
	given fmtVmwareF5BigIpConfig: Format[Schema.VmwareF5BigIpConfig] = Json.format[Schema.VmwareF5BigIpConfig]
	given fmtVmwareManualLbConfig: Format[Schema.VmwareManualLbConfig] = Json.format[Schema.VmwareManualLbConfig]
	given fmtVmwareSeesawConfig: Format[Schema.VmwareSeesawConfig] = Json.format[Schema.VmwareSeesawConfig]
	given fmtVmwareMetalLbConfig: Format[Schema.VmwareMetalLbConfig] = Json.format[Schema.VmwareMetalLbConfig]
	given fmtVmwareAddressPool: Format[Schema.VmwareAddressPool] = Json.format[Schema.VmwareAddressPool]
	given fmtListVmwareClustersResponse: Format[Schema.ListVmwareClustersResponse] = Json.format[Schema.ListVmwareClustersResponse]
	given fmtBareMetalNodePool: Format[Schema.BareMetalNodePool] = Json.format[Schema.BareMetalNodePool]
	given fmtBareMetalNodePoolStateEnum: Format[Schema.BareMetalNodePool.StateEnum] = JsonEnumFormat[Schema.BareMetalNodePool.StateEnum]
	given fmtBareMetalNodePoolUpgradePolicy: Format[Schema.BareMetalNodePoolUpgradePolicy] = Json.format[Schema.BareMetalNodePoolUpgradePolicy]
	given fmtBareMetalParallelUpgradeConfig: Format[Schema.BareMetalParallelUpgradeConfig] = Json.format[Schema.BareMetalParallelUpgradeConfig]
	given fmtEnrollBareMetalNodePoolRequest: Format[Schema.EnrollBareMetalNodePoolRequest] = Json.format[Schema.EnrollBareMetalNodePoolRequest]
	given fmtListBareMetalNodePoolsResponse: Format[Schema.ListBareMetalNodePoolsResponse] = Json.format[Schema.ListBareMetalNodePoolsResponse]
	given fmtVmwareNodePool: Format[Schema.VmwareNodePool] = Json.format[Schema.VmwareNodePool]
	given fmtVmwareNodePoolStateEnum: Format[Schema.VmwareNodePool.StateEnum] = JsonEnumFormat[Schema.VmwareNodePool.StateEnum]
	given fmtVmwareNodePoolAutoscalingConfig: Format[Schema.VmwareNodePoolAutoscalingConfig] = Json.format[Schema.VmwareNodePoolAutoscalingConfig]
	given fmtVmwareNodeConfig: Format[Schema.VmwareNodeConfig] = Json.format[Schema.VmwareNodeConfig]
	given fmtVmwareVsphereConfig: Format[Schema.VmwareVsphereConfig] = Json.format[Schema.VmwareVsphereConfig]
	given fmtVmwareVsphereTag: Format[Schema.VmwareVsphereTag] = Json.format[Schema.VmwareVsphereTag]
	given fmtListVmwareNodePoolsResponse: Format[Schema.ListVmwareNodePoolsResponse] = Json.format[Schema.ListVmwareNodePoolsResponse]
	given fmtEnrollVmwareNodePoolRequest: Format[Schema.EnrollVmwareNodePoolRequest] = Json.format[Schema.EnrollVmwareNodePoolRequest]
	given fmtVmwareAdminCluster: Format[Schema.VmwareAdminCluster] = Json.format[Schema.VmwareAdminCluster]
	given fmtVmwareAdminClusterStateEnum: Format[Schema.VmwareAdminCluster.StateEnum] = JsonEnumFormat[Schema.VmwareAdminCluster.StateEnum]
	given fmtVmwareAdminVCenterConfig: Format[Schema.VmwareAdminVCenterConfig] = Json.format[Schema.VmwareAdminVCenterConfig]
	given fmtVmwareAdminNetworkConfig: Format[Schema.VmwareAdminNetworkConfig] = Json.format[Schema.VmwareAdminNetworkConfig]
	given fmtVmwareAdminLoadBalancerConfig: Format[Schema.VmwareAdminLoadBalancerConfig] = Json.format[Schema.VmwareAdminLoadBalancerConfig]
	given fmtVmwareAdminControlPlaneNodeConfig: Format[Schema.VmwareAdminControlPlaneNodeConfig] = Json.format[Schema.VmwareAdminControlPlaneNodeConfig]
	given fmtVmwareAdminAddonNodeConfig: Format[Schema.VmwareAdminAddonNodeConfig] = Json.format[Schema.VmwareAdminAddonNodeConfig]
	given fmtVmwarePlatformConfig: Format[Schema.VmwarePlatformConfig] = Json.format[Schema.VmwarePlatformConfig]
	given fmtVmwareAdminPreparedSecretsConfig: Format[Schema.VmwareAdminPreparedSecretsConfig] = Json.format[Schema.VmwareAdminPreparedSecretsConfig]
	given fmtVmwareAdminAuthorizationConfig: Format[Schema.VmwareAdminAuthorizationConfig] = Json.format[Schema.VmwareAdminAuthorizationConfig]
	given fmtVmwareAdminHAControlPlaneConfig: Format[Schema.VmwareAdminHAControlPlaneConfig] = Json.format[Schema.VmwareAdminHAControlPlaneConfig]
	given fmtVmwareAdminVipConfig: Format[Schema.VmwareAdminVipConfig] = Json.format[Schema.VmwareAdminVipConfig]
	given fmtVmwareAdminF5BigIpConfig: Format[Schema.VmwareAdminF5BigIpConfig] = Json.format[Schema.VmwareAdminF5BigIpConfig]
	given fmtVmwareAdminManualLbConfig: Format[Schema.VmwareAdminManualLbConfig] = Json.format[Schema.VmwareAdminManualLbConfig]
	given fmtVmwareAdminMetalLbConfig: Format[Schema.VmwareAdminMetalLbConfig] = Json.format[Schema.VmwareAdminMetalLbConfig]
	given fmtVmwareAdminSeesawConfig: Format[Schema.VmwareAdminSeesawConfig] = Json.format[Schema.VmwareAdminSeesawConfig]
	given fmtVmwareBundleConfig: Format[Schema.VmwareBundleConfig] = Json.format[Schema.VmwareBundleConfig]
	given fmtListVmwareAdminClustersResponse: Format[Schema.ListVmwareAdminClustersResponse] = Json.format[Schema.ListVmwareAdminClustersResponse]
	given fmtEnrollVmwareAdminClusterRequest: Format[Schema.EnrollVmwareAdminClusterRequest] = Json.format[Schema.EnrollVmwareAdminClusterRequest]
	given fmtQueryVmwareVersionConfigResponse: Format[Schema.QueryVmwareVersionConfigResponse] = Json.format[Schema.QueryVmwareVersionConfigResponse]
	given fmtVmwareVersionInfo: Format[Schema.VmwareVersionInfo] = Json.format[Schema.VmwareVersionInfo]
	given fmtBareMetalAdminCluster: Format[Schema.BareMetalAdminCluster] = Json.format[Schema.BareMetalAdminCluster]
	given fmtBareMetalAdminClusterStateEnum: Format[Schema.BareMetalAdminCluster.StateEnum] = JsonEnumFormat[Schema.BareMetalAdminCluster.StateEnum]
	given fmtBareMetalAdminNetworkConfig: Format[Schema.BareMetalAdminNetworkConfig] = Json.format[Schema.BareMetalAdminNetworkConfig]
	given fmtBareMetalAdminControlPlaneConfig: Format[Schema.BareMetalAdminControlPlaneConfig] = Json.format[Schema.BareMetalAdminControlPlaneConfig]
	given fmtBareMetalAdminLoadBalancerConfig: Format[Schema.BareMetalAdminLoadBalancerConfig] = Json.format[Schema.BareMetalAdminLoadBalancerConfig]
	given fmtBareMetalAdminStorageConfig: Format[Schema.BareMetalAdminStorageConfig] = Json.format[Schema.BareMetalAdminStorageConfig]
	given fmtBareMetalAdminClusterOperationsConfig: Format[Schema.BareMetalAdminClusterOperationsConfig] = Json.format[Schema.BareMetalAdminClusterOperationsConfig]
	given fmtBareMetalAdminMaintenanceConfig: Format[Schema.BareMetalAdminMaintenanceConfig] = Json.format[Schema.BareMetalAdminMaintenanceConfig]
	given fmtBareMetalAdminMaintenanceStatus: Format[Schema.BareMetalAdminMaintenanceStatus] = Json.format[Schema.BareMetalAdminMaintenanceStatus]
	given fmtBareMetalAdminWorkloadNodeConfig: Format[Schema.BareMetalAdminWorkloadNodeConfig] = Json.format[Schema.BareMetalAdminWorkloadNodeConfig]
	given fmtBareMetalAdminProxyConfig: Format[Schema.BareMetalAdminProxyConfig] = Json.format[Schema.BareMetalAdminProxyConfig]
	given fmtBareMetalAdminSecurityConfig: Format[Schema.BareMetalAdminSecurityConfig] = Json.format[Schema.BareMetalAdminSecurityConfig]
	given fmtBareMetalAdminNodeAccessConfig: Format[Schema.BareMetalAdminNodeAccessConfig] = Json.format[Schema.BareMetalAdminNodeAccessConfig]
	given fmtBareMetalAdminOsEnvironmentConfig: Format[Schema.BareMetalAdminOsEnvironmentConfig] = Json.format[Schema.BareMetalAdminOsEnvironmentConfig]
	given fmtBareMetalAdminIslandModeCidrConfig: Format[Schema.BareMetalAdminIslandModeCidrConfig] = Json.format[Schema.BareMetalAdminIslandModeCidrConfig]
	given fmtBareMetalAdminControlPlaneNodePoolConfig: Format[Schema.BareMetalAdminControlPlaneNodePoolConfig] = Json.format[Schema.BareMetalAdminControlPlaneNodePoolConfig]
	given fmtBareMetalAdminApiServerArgument: Format[Schema.BareMetalAdminApiServerArgument] = Json.format[Schema.BareMetalAdminApiServerArgument]
	given fmtBareMetalAdminVipConfig: Format[Schema.BareMetalAdminVipConfig] = Json.format[Schema.BareMetalAdminVipConfig]
	given fmtBareMetalAdminPortConfig: Format[Schema.BareMetalAdminPortConfig] = Json.format[Schema.BareMetalAdminPortConfig]
	given fmtBareMetalAdminManualLbConfig: Format[Schema.BareMetalAdminManualLbConfig] = Json.format[Schema.BareMetalAdminManualLbConfig]
	given fmtBareMetalAdminMachineDrainStatus: Format[Schema.BareMetalAdminMachineDrainStatus] = Json.format[Schema.BareMetalAdminMachineDrainStatus]
	given fmtBareMetalAdminDrainingMachine: Format[Schema.BareMetalAdminDrainingMachine] = Json.format[Schema.BareMetalAdminDrainingMachine]
	given fmtBareMetalAdminDrainedMachine: Format[Schema.BareMetalAdminDrainedMachine] = Json.format[Schema.BareMetalAdminDrainedMachine]
	given fmtListBareMetalAdminClustersResponse: Format[Schema.ListBareMetalAdminClustersResponse] = Json.format[Schema.ListBareMetalAdminClustersResponse]
	given fmtEnrollBareMetalAdminClusterRequest: Format[Schema.EnrollBareMetalAdminClusterRequest] = Json.format[Schema.EnrollBareMetalAdminClusterRequest]
	given fmtQueryBareMetalAdminVersionConfigResponse: Format[Schema.QueryBareMetalAdminVersionConfigResponse] = Json.format[Schema.QueryBareMetalAdminVersionConfigResponse]
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtSetIamPolicyRequest: Format[Schema.SetIamPolicyRequest] = Json.format[Schema.SetIamPolicyRequest]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtBinding: Format[Schema.Binding] = Json.format[Schema.Binding]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtTestIamPermissionsRequest: Format[Schema.TestIamPermissionsRequest] = Json.format[Schema.TestIamPermissionsRequest]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
	given fmtOperationMetadataTypeEnum: Format[Schema.OperationMetadata.TypeEnum] = JsonEnumFormat[Schema.OperationMetadata.TypeEnum]
	given fmtOperationProgress: Format[Schema.OperationProgress] = Json.format[Schema.OperationProgress]
	given fmtOperationStage: Format[Schema.OperationStage] = Json.format[Schema.OperationStage]
	given fmtOperationStageStageEnum: Format[Schema.OperationStage.StageEnum] = JsonEnumFormat[Schema.OperationStage.StageEnum]
	given fmtMetric: Format[Schema.Metric] = Json.format[Schema.Metric]
	given fmtOperationStageStateEnum: Format[Schema.OperationStage.StateEnum] = JsonEnumFormat[Schema.OperationStage.StateEnum]
	given fmtMetricMetricEnum: Format[Schema.Metric.MetricEnum] = JsonEnumFormat[Schema.Metric.MetricEnum]
}
