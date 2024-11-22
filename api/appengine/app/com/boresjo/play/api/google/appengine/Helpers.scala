package com.boresjo.play.api.google.appengine

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaUrlDispatchRule: Conversion[List[Schema.UrlDispatchRule], Option[List[Schema.UrlDispatchRule]]] = (fun: List[Schema.UrlDispatchRule]) => Option(fun)
		given putSchemaApplicationServingStatusEnum: Conversion[Schema.Application.ServingStatusEnum, Option[Schema.Application.ServingStatusEnum]] = (fun: Schema.Application.ServingStatusEnum) => Option(fun)
		given putSchemaIdentityAwareProxy: Conversion[Schema.IdentityAwareProxy, Option[Schema.IdentityAwareProxy]] = (fun: Schema.IdentityAwareProxy) => Option(fun)
		given putSchemaApplicationDatabaseTypeEnum: Conversion[Schema.Application.DatabaseTypeEnum, Option[Schema.Application.DatabaseTypeEnum]] = (fun: Schema.Application.DatabaseTypeEnum) => Option(fun)
		given putSchemaFeatureSettings: Conversion[Schema.FeatureSettings, Option[Schema.FeatureSettings]] = (fun: Schema.FeatureSettings) => Option(fun)
		given putListSchemaRuntime: Conversion[List[Schema.Runtime], Option[List[Schema.Runtime]]] = (fun: List[Schema.Runtime]) => Option(fun)
		given putSchemaRuntimeEnvironmentEnum: Conversion[Schema.Runtime.EnvironmentEnum, Option[Schema.Runtime.EnvironmentEnum]] = (fun: Schema.Runtime.EnvironmentEnum) => Option(fun)
		given putSchemaRuntimeStageEnum: Conversion[Schema.Runtime.StageEnum, Option[Schema.Runtime.StageEnum]] = (fun: Schema.Runtime.StageEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
		given putListSchemaService: Conversion[List[Schema.Service], Option[List[Schema.Service]]] = (fun: List[Schema.Service]) => Option(fun)
		given putSchemaTrafficSplit: Conversion[Schema.TrafficSplit, Option[Schema.TrafficSplit]] = (fun: Schema.TrafficSplit) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaNetworkSettings: Conversion[Schema.NetworkSettings, Option[Schema.NetworkSettings]] = (fun: Schema.NetworkSettings) => Option(fun)
		given putSchemaTrafficSplitShardByEnum: Conversion[Schema.TrafficSplit.ShardByEnum, Option[Schema.TrafficSplit.ShardByEnum]] = (fun: Schema.TrafficSplit.ShardByEnum) => Option(fun)
		given putMapStringBigDecimal: Conversion[Map[String, BigDecimal], Option[Map[String, BigDecimal]]] = (fun: Map[String, BigDecimal]) => Option(fun)
		given putSchemaNetworkSettingsIngressTrafficAllowedEnum: Conversion[Schema.NetworkSettings.IngressTrafficAllowedEnum, Option[Schema.NetworkSettings.IngressTrafficAllowedEnum]] = (fun: Schema.NetworkSettings.IngressTrafficAllowedEnum) => Option(fun)
		given putListSchemaVersion: Conversion[List[Schema.Version], Option[List[Schema.Version]]] = (fun: List[Schema.Version]) => Option(fun)
		given putSchemaAutomaticScaling: Conversion[Schema.AutomaticScaling, Option[Schema.AutomaticScaling]] = (fun: Schema.AutomaticScaling) => Option(fun)
		given putSchemaBasicScaling: Conversion[Schema.BasicScaling, Option[Schema.BasicScaling]] = (fun: Schema.BasicScaling) => Option(fun)
		given putSchemaManualScaling: Conversion[Schema.ManualScaling, Option[Schema.ManualScaling]] = (fun: Schema.ManualScaling) => Option(fun)
		given putListSchemaVersionInboundServicesEnum: Conversion[List[Schema.Version.InboundServicesEnum], Option[List[Schema.Version.InboundServicesEnum]]] = (fun: List[Schema.Version.InboundServicesEnum]) => Option(fun)
		given putSchemaNetwork: Conversion[Schema.Network, Option[Schema.Network]] = (fun: Schema.Network) => Option(fun)
		given putSchemaResources: Conversion[Schema.Resources, Option[Schema.Resources]] = (fun: Schema.Resources) => Option(fun)
		given putSchemaFlexibleRuntimeSettings: Conversion[Schema.FlexibleRuntimeSettings, Option[Schema.FlexibleRuntimeSettings]] = (fun: Schema.FlexibleRuntimeSettings) => Option(fun)
		given putSchemaVersionServingStatusEnum: Conversion[Schema.Version.ServingStatusEnum, Option[Schema.Version.ServingStatusEnum]] = (fun: Schema.Version.ServingStatusEnum) => Option(fun)
		given putListSchemaUrlMap: Conversion[List[Schema.UrlMap], Option[List[Schema.UrlMap]]] = (fun: List[Schema.UrlMap]) => Option(fun)
		given putListSchemaErrorHandler: Conversion[List[Schema.ErrorHandler], Option[List[Schema.ErrorHandler]]] = (fun: List[Schema.ErrorHandler]) => Option(fun)
		given putListSchemaLibrary: Conversion[List[Schema.Library], Option[List[Schema.Library]]] = (fun: List[Schema.Library]) => Option(fun)
		given putSchemaApiConfigHandler: Conversion[Schema.ApiConfigHandler, Option[Schema.ApiConfigHandler]] = (fun: Schema.ApiConfigHandler) => Option(fun)
		given putSchemaHealthCheck: Conversion[Schema.HealthCheck, Option[Schema.HealthCheck]] = (fun: Schema.HealthCheck) => Option(fun)
		given putSchemaReadinessCheck: Conversion[Schema.ReadinessCheck, Option[Schema.ReadinessCheck]] = (fun: Schema.ReadinessCheck) => Option(fun)
		given putSchemaLivenessCheck: Conversion[Schema.LivenessCheck, Option[Schema.LivenessCheck]] = (fun: Schema.LivenessCheck) => Option(fun)
		given putSchemaDeployment: Conversion[Schema.Deployment, Option[Schema.Deployment]] = (fun: Schema.Deployment) => Option(fun)
		given putSchemaEndpointsApiService: Conversion[Schema.EndpointsApiService, Option[Schema.EndpointsApiService]] = (fun: Schema.EndpointsApiService) => Option(fun)
		given putSchemaEntrypoint: Conversion[Schema.Entrypoint, Option[Schema.Entrypoint]] = (fun: Schema.Entrypoint) => Option(fun)
		given putSchemaVpcAccessConnector: Conversion[Schema.VpcAccessConnector, Option[Schema.VpcAccessConnector]] = (fun: Schema.VpcAccessConnector) => Option(fun)
		given putSchemaCpuUtilization: Conversion[Schema.CpuUtilization, Option[Schema.CpuUtilization]] = (fun: Schema.CpuUtilization) => Option(fun)
		given putSchemaRequestUtilization: Conversion[Schema.RequestUtilization, Option[Schema.RequestUtilization]] = (fun: Schema.RequestUtilization) => Option(fun)
		given putSchemaDiskUtilization: Conversion[Schema.DiskUtilization, Option[Schema.DiskUtilization]] = (fun: Schema.DiskUtilization) => Option(fun)
		given putSchemaNetworkUtilization: Conversion[Schema.NetworkUtilization, Option[Schema.NetworkUtilization]] = (fun: Schema.NetworkUtilization) => Option(fun)
		given putSchemaStandardSchedulerSettings: Conversion[Schema.StandardSchedulerSettings, Option[Schema.StandardSchedulerSettings]] = (fun: Schema.StandardSchedulerSettings) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putSchemaNetworkInstanceIpModeEnum: Conversion[Schema.Network.InstanceIpModeEnum, Option[Schema.Network.InstanceIpModeEnum]] = (fun: Schema.Network.InstanceIpModeEnum) => Option(fun)
		given putListSchemaVolume: Conversion[List[Schema.Volume], Option[List[Schema.Volume]]] = (fun: List[Schema.Volume]) => Option(fun)
		given putSchemaStaticFilesHandler: Conversion[Schema.StaticFilesHandler, Option[Schema.StaticFilesHandler]] = (fun: Schema.StaticFilesHandler) => Option(fun)
		given putSchemaScriptHandler: Conversion[Schema.ScriptHandler, Option[Schema.ScriptHandler]] = (fun: Schema.ScriptHandler) => Option(fun)
		given putSchemaApiEndpointHandler: Conversion[Schema.ApiEndpointHandler, Option[Schema.ApiEndpointHandler]] = (fun: Schema.ApiEndpointHandler) => Option(fun)
		given putSchemaUrlMapSecurityLevelEnum: Conversion[Schema.UrlMap.SecurityLevelEnum, Option[Schema.UrlMap.SecurityLevelEnum]] = (fun: Schema.UrlMap.SecurityLevelEnum) => Option(fun)
		given putSchemaUrlMapLoginEnum: Conversion[Schema.UrlMap.LoginEnum, Option[Schema.UrlMap.LoginEnum]] = (fun: Schema.UrlMap.LoginEnum) => Option(fun)
		given putSchemaUrlMapAuthFailActionEnum: Conversion[Schema.UrlMap.AuthFailActionEnum, Option[Schema.UrlMap.AuthFailActionEnum]] = (fun: Schema.UrlMap.AuthFailActionEnum) => Option(fun)
		given putSchemaUrlMapRedirectHttpResponseCodeEnum: Conversion[Schema.UrlMap.RedirectHttpResponseCodeEnum, Option[Schema.UrlMap.RedirectHttpResponseCodeEnum]] = (fun: Schema.UrlMap.RedirectHttpResponseCodeEnum) => Option(fun)
		given putSchemaErrorHandlerErrorCodeEnum: Conversion[Schema.ErrorHandler.ErrorCodeEnum, Option[Schema.ErrorHandler.ErrorCodeEnum]] = (fun: Schema.ErrorHandler.ErrorCodeEnum) => Option(fun)
		given putSchemaApiConfigHandlerAuthFailActionEnum: Conversion[Schema.ApiConfigHandler.AuthFailActionEnum, Option[Schema.ApiConfigHandler.AuthFailActionEnum]] = (fun: Schema.ApiConfigHandler.AuthFailActionEnum) => Option(fun)
		given putSchemaApiConfigHandlerLoginEnum: Conversion[Schema.ApiConfigHandler.LoginEnum, Option[Schema.ApiConfigHandler.LoginEnum]] = (fun: Schema.ApiConfigHandler.LoginEnum) => Option(fun)
		given putSchemaApiConfigHandlerSecurityLevelEnum: Conversion[Schema.ApiConfigHandler.SecurityLevelEnum, Option[Schema.ApiConfigHandler.SecurityLevelEnum]] = (fun: Schema.ApiConfigHandler.SecurityLevelEnum) => Option(fun)
		given putMapStringSchemaFileInfo: Conversion[Map[String, Schema.FileInfo], Option[Map[String, Schema.FileInfo]]] = (fun: Map[String, Schema.FileInfo]) => Option(fun)
		given putSchemaContainerInfo: Conversion[Schema.ContainerInfo, Option[Schema.ContainerInfo]] = (fun: Schema.ContainerInfo) => Option(fun)
		given putSchemaZipInfo: Conversion[Schema.ZipInfo, Option[Schema.ZipInfo]] = (fun: Schema.ZipInfo) => Option(fun)
		given putSchemaCloudBuildOptions: Conversion[Schema.CloudBuildOptions, Option[Schema.CloudBuildOptions]] = (fun: Schema.CloudBuildOptions) => Option(fun)
		given putSchemaEndpointsApiServiceRolloutStrategyEnum: Conversion[Schema.EndpointsApiService.RolloutStrategyEnum, Option[Schema.EndpointsApiService.RolloutStrategyEnum]] = (fun: Schema.EndpointsApiService.RolloutStrategyEnum) => Option(fun)
		given putSchemaVpcAccessConnectorEgressSettingEnum: Conversion[Schema.VpcAccessConnector.EgressSettingEnum, Option[Schema.VpcAccessConnector.EgressSettingEnum]] = (fun: Schema.VpcAccessConnector.EgressSettingEnum) => Option(fun)
		given putListSchemaInstance: Conversion[List[Schema.Instance], Option[List[Schema.Instance]]] = (fun: List[Schema.Instance]) => Option(fun)
		given putSchemaInstanceAvailabilityEnum: Conversion[Schema.Instance.AvailabilityEnum, Option[Schema.Instance.AvailabilityEnum]] = (fun: Schema.Instance.AvailabilityEnum) => Option(fun)
		given putSchemaInstanceVmLivenessEnum: Conversion[Schema.Instance.VmLivenessEnum, Option[Schema.Instance.VmLivenessEnum]] = (fun: Schema.Instance.VmLivenessEnum) => Option(fun)
		given putListSchemaFirewallRule: Conversion[List[Schema.FirewallRule], Option[List[Schema.FirewallRule]]] = (fun: List[Schema.FirewallRule]) => Option(fun)
		given putSchemaFirewallRuleActionEnum: Conversion[Schema.FirewallRule.ActionEnum, Option[Schema.FirewallRule.ActionEnum]] = (fun: Schema.FirewallRule.ActionEnum) => Option(fun)
		given putListSchemaAuthorizedDomain: Conversion[List[Schema.AuthorizedDomain], Option[List[Schema.AuthorizedDomain]]] = (fun: List[Schema.AuthorizedDomain]) => Option(fun)
		given putListSchemaAuthorizedCertificate: Conversion[List[Schema.AuthorizedCertificate], Option[List[Schema.AuthorizedCertificate]]] = (fun: List[Schema.AuthorizedCertificate]) => Option(fun)
		given putSchemaCertificateRawData: Conversion[Schema.CertificateRawData, Option[Schema.CertificateRawData]] = (fun: Schema.CertificateRawData) => Option(fun)
		given putSchemaManagedCertificate: Conversion[Schema.ManagedCertificate, Option[Schema.ManagedCertificate]] = (fun: Schema.ManagedCertificate) => Option(fun)
		given putSchemaManagedCertificateStatusEnum: Conversion[Schema.ManagedCertificate.StatusEnum, Option[Schema.ManagedCertificate.StatusEnum]] = (fun: Schema.ManagedCertificate.StatusEnum) => Option(fun)
		given putListSchemaDomainMapping: Conversion[List[Schema.DomainMapping], Option[List[Schema.DomainMapping]]] = (fun: List[Schema.DomainMapping]) => Option(fun)
		given putSchemaSslSettings: Conversion[Schema.SslSettings, Option[Schema.SslSettings]] = (fun: Schema.SslSettings) => Option(fun)
		given putListSchemaResourceRecord: Conversion[List[Schema.ResourceRecord], Option[List[Schema.ResourceRecord]]] = (fun: List[Schema.ResourceRecord]) => Option(fun)
		given putSchemaSslSettingsSslManagementTypeEnum: Conversion[Schema.SslSettings.SslManagementTypeEnum, Option[Schema.SslSettings.SslManagementTypeEnum]] = (fun: Schema.SslSettings.SslManagementTypeEnum) => Option(fun)
		given putSchemaResourceRecordTypeEnum: Conversion[Schema.ResourceRecord.TypeEnum, Option[Schema.ResourceRecord.TypeEnum]] = (fun: Schema.ResourceRecord.TypeEnum) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putSchemaCreateVersionMetadataV1: Conversion[Schema.CreateVersionMetadataV1, Option[Schema.CreateVersionMetadataV1]] = (fun: Schema.CreateVersionMetadataV1) => Option(fun)
		given putSchemaCreateVersionMetadataV1Alpha: Conversion[Schema.CreateVersionMetadataV1Alpha, Option[Schema.CreateVersionMetadataV1Alpha]] = (fun: Schema.CreateVersionMetadataV1Alpha) => Option(fun)
		given putSchemaCreateVersionMetadataV1Beta: Conversion[Schema.CreateVersionMetadataV1Beta, Option[Schema.CreateVersionMetadataV1Beta]] = (fun: Schema.CreateVersionMetadataV1Beta) => Option(fun)
		given putSchemaProjectsMetadata: Conversion[Schema.ProjectsMetadata, Option[Schema.ProjectsMetadata]] = (fun: Schema.ProjectsMetadata) => Option(fun)
		given putSchemaProjectEventPhaseEnum: Conversion[Schema.ProjectEvent.PhaseEnum, Option[Schema.ProjectEvent.PhaseEnum]] = (fun: Schema.ProjectEvent.PhaseEnum) => Option(fun)
		given putSchemaContainerState: Conversion[Schema.ContainerState, Option[Schema.ContainerState]] = (fun: Schema.ContainerState) => Option(fun)
		given putSchemaProjectsMetadataConsumerProjectStateEnum: Conversion[Schema.ProjectsMetadata.ConsumerProjectStateEnum, Option[Schema.ProjectsMetadata.ConsumerProjectStateEnum]] = (fun: Schema.ProjectsMetadata.ConsumerProjectStateEnum) => Option(fun)
		given putListSchemaGceTag: Conversion[List[Schema.GceTag], Option[List[Schema.GceTag]]] = (fun: List[Schema.GceTag]) => Option(fun)
		given putSchemaContainerStateStateEnum: Conversion[Schema.ContainerState.StateEnum, Option[Schema.ContainerState.StateEnum]] = (fun: Schema.ContainerState.StateEnum) => Option(fun)
		given putSchemaReasons: Conversion[Schema.Reasons, Option[Schema.Reasons]] = (fun: Schema.Reasons) => Option(fun)
		given putSchemaReasonsServiceManagementEnum: Conversion[Schema.Reasons.ServiceManagementEnum, Option[Schema.Reasons.ServiceManagementEnum]] = (fun: Schema.Reasons.ServiceManagementEnum) => Option(fun)
		given putSchemaReasonsDataGovernanceEnum: Conversion[Schema.Reasons.DataGovernanceEnum, Option[Schema.Reasons.DataGovernanceEnum]] = (fun: Schema.Reasons.DataGovernanceEnum) => Option(fun)
		given putSchemaReasonsAbuseEnum: Conversion[Schema.Reasons.AbuseEnum, Option[Schema.Reasons.AbuseEnum]] = (fun: Schema.Reasons.AbuseEnum) => Option(fun)
		given putSchemaReasonsBillingEnum: Conversion[Schema.Reasons.BillingEnum, Option[Schema.Reasons.BillingEnum]] = (fun: Schema.Reasons.BillingEnum) => Option(fun)
		given putSchemaReasonsServiceActivationEnum: Conversion[Schema.Reasons.ServiceActivationEnum, Option[Schema.Reasons.ServiceActivationEnum]] = (fun: Schema.Reasons.ServiceActivationEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
