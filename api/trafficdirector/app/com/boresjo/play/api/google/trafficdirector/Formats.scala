package com.boresjo.play.api.google.trafficdirector

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtClientStatusRequest: Format[Schema.ClientStatusRequest] = Json.format[Schema.ClientStatusRequest]
	given fmtNodeMatcher: Format[Schema.NodeMatcher] = Json.format[Schema.NodeMatcher]
	given fmtNode: Format[Schema.Node] = Json.format[Schema.Node]
	given fmtStringMatcher: Format[Schema.StringMatcher] = Json.format[Schema.StringMatcher]
	given fmtStructMatcher: Format[Schema.StructMatcher] = Json.format[Schema.StructMatcher]
	given fmtRegexMatcher: Format[Schema.RegexMatcher] = Json.format[Schema.RegexMatcher]
	given fmtTypedExtensionConfig: Format[Schema.TypedExtensionConfig] = Json.format[Schema.TypedExtensionConfig]
	given fmtGoogleRE2: Format[Schema.GoogleRE2] = Json.format[Schema.GoogleRE2]
	given fmtPathSegment: Format[Schema.PathSegment] = Json.format[Schema.PathSegment]
	given fmtValueMatcher: Format[Schema.ValueMatcher] = Json.format[Schema.ValueMatcher]
	given fmtNullMatch: Format[Schema.NullMatch] = Json.format[Schema.NullMatch]
	given fmtDoubleMatcher: Format[Schema.DoubleMatcher] = Json.format[Schema.DoubleMatcher]
	given fmtListMatcher: Format[Schema.ListMatcher] = Json.format[Schema.ListMatcher]
	given fmtOrMatcher: Format[Schema.OrMatcher] = Json.format[Schema.OrMatcher]
	given fmtDoubleRange: Format[Schema.DoubleRange] = Json.format[Schema.DoubleRange]
	given fmtContextParams: Format[Schema.ContextParams] = Json.format[Schema.ContextParams]
	given fmtLocality: Format[Schema.Locality] = Json.format[Schema.Locality]
	given fmtBuildVersion: Format[Schema.BuildVersion] = Json.format[Schema.BuildVersion]
	given fmtExtension: Format[Schema.Extension] = Json.format[Schema.Extension]
	given fmtAddress: Format[Schema.Address] = Json.format[Schema.Address]
	given fmtSemanticVersion: Format[Schema.SemanticVersion] = Json.format[Schema.SemanticVersion]
	given fmtSocketAddress: Format[Schema.SocketAddress] = Json.format[Schema.SocketAddress]
	given fmtPipe: Format[Schema.Pipe] = Json.format[Schema.Pipe]
	given fmtEnvoyInternalAddress: Format[Schema.EnvoyInternalAddress] = Json.format[Schema.EnvoyInternalAddress]
	given fmtSocketAddressProtocolEnum: Format[Schema.SocketAddress.ProtocolEnum] = JsonEnumFormat[Schema.SocketAddress.ProtocolEnum]
	given fmtClientStatusResponse: Format[Schema.ClientStatusResponse] = Json.format[Schema.ClientStatusResponse]
	given fmtClientConfig: Format[Schema.ClientConfig] = Json.format[Schema.ClientConfig]
	given fmtPerXdsConfig: Format[Schema.PerXdsConfig] = Json.format[Schema.PerXdsConfig]
	given fmtGenericXdsConfig: Format[Schema.GenericXdsConfig] = Json.format[Schema.GenericXdsConfig]
	given fmtPerXdsConfigStatusEnum: Format[Schema.PerXdsConfig.StatusEnum] = JsonEnumFormat[Schema.PerXdsConfig.StatusEnum]
	given fmtPerXdsConfigClientStatusEnum: Format[Schema.PerXdsConfig.ClientStatusEnum] = JsonEnumFormat[Schema.PerXdsConfig.ClientStatusEnum]
	given fmtListenersConfigDump: Format[Schema.ListenersConfigDump] = Json.format[Schema.ListenersConfigDump]
	given fmtClustersConfigDump: Format[Schema.ClustersConfigDump] = Json.format[Schema.ClustersConfigDump]
	given fmtRoutesConfigDump: Format[Schema.RoutesConfigDump] = Json.format[Schema.RoutesConfigDump]
	given fmtScopedRoutesConfigDump: Format[Schema.ScopedRoutesConfigDump] = Json.format[Schema.ScopedRoutesConfigDump]
	given fmtEndpointsConfigDump: Format[Schema.EndpointsConfigDump] = Json.format[Schema.EndpointsConfigDump]
	given fmtStaticListener: Format[Schema.StaticListener] = Json.format[Schema.StaticListener]
	given fmtDynamicListener: Format[Schema.DynamicListener] = Json.format[Schema.DynamicListener]
	given fmtDynamicListenerState: Format[Schema.DynamicListenerState] = Json.format[Schema.DynamicListenerState]
	given fmtUpdateFailureState: Format[Schema.UpdateFailureState] = Json.format[Schema.UpdateFailureState]
	given fmtDynamicListenerClientStatusEnum: Format[Schema.DynamicListener.ClientStatusEnum] = JsonEnumFormat[Schema.DynamicListener.ClientStatusEnum]
	given fmtStaticCluster: Format[Schema.StaticCluster] = Json.format[Schema.StaticCluster]
	given fmtDynamicCluster: Format[Schema.DynamicCluster] = Json.format[Schema.DynamicCluster]
	given fmtDynamicClusterClientStatusEnum: Format[Schema.DynamicCluster.ClientStatusEnum] = JsonEnumFormat[Schema.DynamicCluster.ClientStatusEnum]
	given fmtStaticRouteConfig: Format[Schema.StaticRouteConfig] = Json.format[Schema.StaticRouteConfig]
	given fmtDynamicRouteConfig: Format[Schema.DynamicRouteConfig] = Json.format[Schema.DynamicRouteConfig]
	given fmtDynamicRouteConfigClientStatusEnum: Format[Schema.DynamicRouteConfig.ClientStatusEnum] = JsonEnumFormat[Schema.DynamicRouteConfig.ClientStatusEnum]
	given fmtInlineScopedRouteConfigs: Format[Schema.InlineScopedRouteConfigs] = Json.format[Schema.InlineScopedRouteConfigs]
	given fmtDynamicScopedRouteConfigs: Format[Schema.DynamicScopedRouteConfigs] = Json.format[Schema.DynamicScopedRouteConfigs]
	given fmtDynamicScopedRouteConfigsClientStatusEnum: Format[Schema.DynamicScopedRouteConfigs.ClientStatusEnum] = JsonEnumFormat[Schema.DynamicScopedRouteConfigs.ClientStatusEnum]
	given fmtStaticEndpointConfig: Format[Schema.StaticEndpointConfig] = Json.format[Schema.StaticEndpointConfig]
	given fmtDynamicEndpointConfig: Format[Schema.DynamicEndpointConfig] = Json.format[Schema.DynamicEndpointConfig]
	given fmtDynamicEndpointConfigClientStatusEnum: Format[Schema.DynamicEndpointConfig.ClientStatusEnum] = JsonEnumFormat[Schema.DynamicEndpointConfig.ClientStatusEnum]
	given fmtGenericXdsConfigConfigStatusEnum: Format[Schema.GenericXdsConfig.ConfigStatusEnum] = JsonEnumFormat[Schema.GenericXdsConfig.ConfigStatusEnum]
	given fmtGenericXdsConfigClientStatusEnum: Format[Schema.GenericXdsConfig.ClientStatusEnum] = JsonEnumFormat[Schema.GenericXdsConfig.ClientStatusEnum]
}
