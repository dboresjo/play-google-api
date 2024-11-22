package com.boresjo.play.api.google.trafficdirector

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaNodeMatcher: Conversion[List[Schema.NodeMatcher], Option[List[Schema.NodeMatcher]]] = (fun: List[Schema.NodeMatcher]) => Option(fun)
		given putSchemaNode: Conversion[Schema.Node, Option[Schema.Node]] = (fun: Schema.Node) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStringMatcher: Conversion[Schema.StringMatcher, Option[Schema.StringMatcher]] = (fun: Schema.StringMatcher) => Option(fun)
		given putListSchemaStructMatcher: Conversion[List[Schema.StructMatcher], Option[List[Schema.StructMatcher]]] = (fun: List[Schema.StructMatcher]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaRegexMatcher: Conversion[Schema.RegexMatcher, Option[Schema.RegexMatcher]] = (fun: Schema.RegexMatcher) => Option(fun)
		given putSchemaTypedExtensionConfig: Conversion[Schema.TypedExtensionConfig, Option[Schema.TypedExtensionConfig]] = (fun: Schema.TypedExtensionConfig) => Option(fun)
		given putSchemaGoogleRE2: Conversion[Schema.GoogleRE2, Option[Schema.GoogleRE2]] = (fun: Schema.GoogleRE2) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putListSchemaPathSegment: Conversion[List[Schema.PathSegment], Option[List[Schema.PathSegment]]] = (fun: List[Schema.PathSegment]) => Option(fun)
		given putSchemaValueMatcher: Conversion[Schema.ValueMatcher, Option[Schema.ValueMatcher]] = (fun: Schema.ValueMatcher) => Option(fun)
		given putSchemaNullMatch: Conversion[Schema.NullMatch, Option[Schema.NullMatch]] = (fun: Schema.NullMatch) => Option(fun)
		given putSchemaDoubleMatcher: Conversion[Schema.DoubleMatcher, Option[Schema.DoubleMatcher]] = (fun: Schema.DoubleMatcher) => Option(fun)
		given putSchemaListMatcher: Conversion[Schema.ListMatcher, Option[Schema.ListMatcher]] = (fun: Schema.ListMatcher) => Option(fun)
		given putSchemaOrMatcher: Conversion[Schema.OrMatcher, Option[Schema.OrMatcher]] = (fun: Schema.OrMatcher) => Option(fun)
		given putSchemaDoubleRange: Conversion[Schema.DoubleRange, Option[Schema.DoubleRange]] = (fun: Schema.DoubleRange) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListSchemaValueMatcher: Conversion[List[Schema.ValueMatcher], Option[List[Schema.ValueMatcher]]] = (fun: List[Schema.ValueMatcher]) => Option(fun)
		given putMapStringSchemaContextParams: Conversion[Map[String, Schema.ContextParams], Option[Map[String, Schema.ContextParams]]] = (fun: Map[String, Schema.ContextParams]) => Option(fun)
		given putSchemaLocality: Conversion[Schema.Locality, Option[Schema.Locality]] = (fun: Schema.Locality) => Option(fun)
		given putSchemaBuildVersion: Conversion[Schema.BuildVersion, Option[Schema.BuildVersion]] = (fun: Schema.BuildVersion) => Option(fun)
		given putListSchemaExtension: Conversion[List[Schema.Extension], Option[List[Schema.Extension]]] = (fun: List[Schema.Extension]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaAddress: Conversion[List[Schema.Address], Option[List[Schema.Address]]] = (fun: List[Schema.Address]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaSemanticVersion: Conversion[Schema.SemanticVersion, Option[Schema.SemanticVersion]] = (fun: Schema.SemanticVersion) => Option(fun)
		given putSchemaSocketAddress: Conversion[Schema.SocketAddress, Option[Schema.SocketAddress]] = (fun: Schema.SocketAddress) => Option(fun)
		given putSchemaPipe: Conversion[Schema.Pipe, Option[Schema.Pipe]] = (fun: Schema.Pipe) => Option(fun)
		given putSchemaEnvoyInternalAddress: Conversion[Schema.EnvoyInternalAddress, Option[Schema.EnvoyInternalAddress]] = (fun: Schema.EnvoyInternalAddress) => Option(fun)
		given putSchemaSocketAddressProtocolEnum: Conversion[Schema.SocketAddress.ProtocolEnum, Option[Schema.SocketAddress.ProtocolEnum]] = (fun: Schema.SocketAddress.ProtocolEnum) => Option(fun)
		given putListSchemaClientConfig: Conversion[List[Schema.ClientConfig], Option[List[Schema.ClientConfig]]] = (fun: List[Schema.ClientConfig]) => Option(fun)
		given putListSchemaPerXdsConfig: Conversion[List[Schema.PerXdsConfig], Option[List[Schema.PerXdsConfig]]] = (fun: List[Schema.PerXdsConfig]) => Option(fun)
		given putListSchemaGenericXdsConfig: Conversion[List[Schema.GenericXdsConfig], Option[List[Schema.GenericXdsConfig]]] = (fun: List[Schema.GenericXdsConfig]) => Option(fun)
		given putSchemaPerXdsConfigStatusEnum: Conversion[Schema.PerXdsConfig.StatusEnum, Option[Schema.PerXdsConfig.StatusEnum]] = (fun: Schema.PerXdsConfig.StatusEnum) => Option(fun)
		given putSchemaPerXdsConfigClientStatusEnum: Conversion[Schema.PerXdsConfig.ClientStatusEnum, Option[Schema.PerXdsConfig.ClientStatusEnum]] = (fun: Schema.PerXdsConfig.ClientStatusEnum) => Option(fun)
		given putSchemaListenersConfigDump: Conversion[Schema.ListenersConfigDump, Option[Schema.ListenersConfigDump]] = (fun: Schema.ListenersConfigDump) => Option(fun)
		given putSchemaClustersConfigDump: Conversion[Schema.ClustersConfigDump, Option[Schema.ClustersConfigDump]] = (fun: Schema.ClustersConfigDump) => Option(fun)
		given putSchemaRoutesConfigDump: Conversion[Schema.RoutesConfigDump, Option[Schema.RoutesConfigDump]] = (fun: Schema.RoutesConfigDump) => Option(fun)
		given putSchemaScopedRoutesConfigDump: Conversion[Schema.ScopedRoutesConfigDump, Option[Schema.ScopedRoutesConfigDump]] = (fun: Schema.ScopedRoutesConfigDump) => Option(fun)
		given putSchemaEndpointsConfigDump: Conversion[Schema.EndpointsConfigDump, Option[Schema.EndpointsConfigDump]] = (fun: Schema.EndpointsConfigDump) => Option(fun)
		given putListSchemaStaticListener: Conversion[List[Schema.StaticListener], Option[List[Schema.StaticListener]]] = (fun: List[Schema.StaticListener]) => Option(fun)
		given putListSchemaDynamicListener: Conversion[List[Schema.DynamicListener], Option[List[Schema.DynamicListener]]] = (fun: List[Schema.DynamicListener]) => Option(fun)
		given putSchemaDynamicListenerState: Conversion[Schema.DynamicListenerState, Option[Schema.DynamicListenerState]] = (fun: Schema.DynamicListenerState) => Option(fun)
		given putSchemaUpdateFailureState: Conversion[Schema.UpdateFailureState, Option[Schema.UpdateFailureState]] = (fun: Schema.UpdateFailureState) => Option(fun)
		given putSchemaDynamicListenerClientStatusEnum: Conversion[Schema.DynamicListener.ClientStatusEnum, Option[Schema.DynamicListener.ClientStatusEnum]] = (fun: Schema.DynamicListener.ClientStatusEnum) => Option(fun)
		given putListSchemaStaticCluster: Conversion[List[Schema.StaticCluster], Option[List[Schema.StaticCluster]]] = (fun: List[Schema.StaticCluster]) => Option(fun)
		given putListSchemaDynamicCluster: Conversion[List[Schema.DynamicCluster], Option[List[Schema.DynamicCluster]]] = (fun: List[Schema.DynamicCluster]) => Option(fun)
		given putSchemaDynamicClusterClientStatusEnum: Conversion[Schema.DynamicCluster.ClientStatusEnum, Option[Schema.DynamicCluster.ClientStatusEnum]] = (fun: Schema.DynamicCluster.ClientStatusEnum) => Option(fun)
		given putListSchemaStaticRouteConfig: Conversion[List[Schema.StaticRouteConfig], Option[List[Schema.StaticRouteConfig]]] = (fun: List[Schema.StaticRouteConfig]) => Option(fun)
		given putListSchemaDynamicRouteConfig: Conversion[List[Schema.DynamicRouteConfig], Option[List[Schema.DynamicRouteConfig]]] = (fun: List[Schema.DynamicRouteConfig]) => Option(fun)
		given putSchemaDynamicRouteConfigClientStatusEnum: Conversion[Schema.DynamicRouteConfig.ClientStatusEnum, Option[Schema.DynamicRouteConfig.ClientStatusEnum]] = (fun: Schema.DynamicRouteConfig.ClientStatusEnum) => Option(fun)
		given putListSchemaInlineScopedRouteConfigs: Conversion[List[Schema.InlineScopedRouteConfigs], Option[List[Schema.InlineScopedRouteConfigs]]] = (fun: List[Schema.InlineScopedRouteConfigs]) => Option(fun)
		given putListSchemaDynamicScopedRouteConfigs: Conversion[List[Schema.DynamicScopedRouteConfigs], Option[List[Schema.DynamicScopedRouteConfigs]]] = (fun: List[Schema.DynamicScopedRouteConfigs]) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaDynamicScopedRouteConfigsClientStatusEnum: Conversion[Schema.DynamicScopedRouteConfigs.ClientStatusEnum, Option[Schema.DynamicScopedRouteConfigs.ClientStatusEnum]] = (fun: Schema.DynamicScopedRouteConfigs.ClientStatusEnum) => Option(fun)
		given putListSchemaStaticEndpointConfig: Conversion[List[Schema.StaticEndpointConfig], Option[List[Schema.StaticEndpointConfig]]] = (fun: List[Schema.StaticEndpointConfig]) => Option(fun)
		given putListSchemaDynamicEndpointConfig: Conversion[List[Schema.DynamicEndpointConfig], Option[List[Schema.DynamicEndpointConfig]]] = (fun: List[Schema.DynamicEndpointConfig]) => Option(fun)
		given putSchemaDynamicEndpointConfigClientStatusEnum: Conversion[Schema.DynamicEndpointConfig.ClientStatusEnum, Option[Schema.DynamicEndpointConfig.ClientStatusEnum]] = (fun: Schema.DynamicEndpointConfig.ClientStatusEnum) => Option(fun)
		given putSchemaGenericXdsConfigConfigStatusEnum: Conversion[Schema.GenericXdsConfig.ConfigStatusEnum, Option[Schema.GenericXdsConfig.ConfigStatusEnum]] = (fun: Schema.GenericXdsConfig.ConfigStatusEnum) => Option(fun)
		given putSchemaGenericXdsConfigClientStatusEnum: Conversion[Schema.GenericXdsConfig.ClientStatusEnum, Option[Schema.GenericXdsConfig.ClientStatusEnum]] = (fun: Schema.GenericXdsConfig.ClientStatusEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
