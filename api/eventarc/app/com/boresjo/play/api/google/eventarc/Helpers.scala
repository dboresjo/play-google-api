package com.boresjo.play.api.google.eventarc

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaGoogleLongrunningOperation: Conversion[List[Schema.GoogleLongrunningOperation], Option[List[Schema.GoogleLongrunningOperation]]] = (fun: List[Schema.GoogleLongrunningOperation]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaGoogleRpcStatus: Conversion[Schema.GoogleRpcStatus, Option[Schema.GoogleRpcStatus]] = (fun: Schema.GoogleRpcStatus) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaEventFilter: Conversion[List[Schema.EventFilter], Option[List[Schema.EventFilter]]] = (fun: List[Schema.EventFilter]) => Option(fun)
		given putSchemaDestination: Conversion[Schema.Destination, Option[Schema.Destination]] = (fun: Schema.Destination) => Option(fun)
		given putSchemaTransport: Conversion[Schema.Transport, Option[Schema.Transport]] = (fun: Schema.Transport) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putMapStringSchemaStateCondition: Conversion[Map[String, Schema.StateCondition], Option[Map[String, Schema.StateCondition]]] = (fun: Map[String, Schema.StateCondition]) => Option(fun)
		given putSchemaCloudRun: Conversion[Schema.CloudRun, Option[Schema.CloudRun]] = (fun: Schema.CloudRun) => Option(fun)
		given putSchemaGKE: Conversion[Schema.GKE, Option[Schema.GKE]] = (fun: Schema.GKE) => Option(fun)
		given putSchemaHttpEndpoint: Conversion[Schema.HttpEndpoint, Option[Schema.HttpEndpoint]] = (fun: Schema.HttpEndpoint) => Option(fun)
		given putSchemaNetworkConfig: Conversion[Schema.NetworkConfig, Option[Schema.NetworkConfig]] = (fun: Schema.NetworkConfig) => Option(fun)
		given putSchemaPubsub: Conversion[Schema.Pubsub, Option[Schema.Pubsub]] = (fun: Schema.Pubsub) => Option(fun)
		given putSchemaStateConditionCodeEnum: Conversion[Schema.StateCondition.CodeEnum, Option[Schema.StateCondition.CodeEnum]] = (fun: Schema.StateCondition.CodeEnum) => Option(fun)
		given putListSchemaTrigger: Conversion[List[Schema.Trigger], Option[List[Schema.Trigger]]] = (fun: List[Schema.Trigger]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaChannelStateEnum: Conversion[Schema.Channel.StateEnum, Option[Schema.Channel.StateEnum]] = (fun: Schema.Channel.StateEnum) => Option(fun)
		given putListSchemaChannel: Conversion[List[Schema.Channel], Option[List[Schema.Channel]]] = (fun: List[Schema.Channel]) => Option(fun)
		given putListSchemaEventType: Conversion[List[Schema.EventType], Option[List[Schema.EventType]]] = (fun: List[Schema.EventType]) => Option(fun)
		given putListSchemaFilteringAttribute: Conversion[List[Schema.FilteringAttribute], Option[List[Schema.FilteringAttribute]]] = (fun: List[Schema.FilteringAttribute]) => Option(fun)
		given putListSchemaProvider: Conversion[List[Schema.Provider], Option[List[Schema.Provider]]] = (fun: List[Schema.Provider]) => Option(fun)
		given putListSchemaChannelConnection: Conversion[List[Schema.ChannelConnection], Option[List[Schema.ChannelConnection]]] = (fun: List[Schema.ChannelConnection]) => Option(fun)
		given putSchemaLoggingConfig: Conversion[Schema.LoggingConfig, Option[Schema.LoggingConfig]] = (fun: Schema.LoggingConfig) => Option(fun)
		given putSchemaLoggingConfigLogSeverityEnum: Conversion[Schema.LoggingConfig.LogSeverityEnum, Option[Schema.LoggingConfig.LogSeverityEnum]] = (fun: Schema.LoggingConfig.LogSeverityEnum) => Option(fun)
		given putListSchemaMessageBus: Conversion[List[Schema.MessageBus], Option[List[Schema.MessageBus]]] = (fun: List[Schema.MessageBus]) => Option(fun)
		given putListSchemaEnrollment: Conversion[List[Schema.Enrollment], Option[List[Schema.Enrollment]]] = (fun: List[Schema.Enrollment]) => Option(fun)
		given putListSchemaGoogleCloudEventarcV1PipelineDestination: Conversion[List[Schema.GoogleCloudEventarcV1PipelineDestination], Option[List[Schema.GoogleCloudEventarcV1PipelineDestination]]] = (fun: List[Schema.GoogleCloudEventarcV1PipelineDestination]) => Option(fun)
		given putListSchemaGoogleCloudEventarcV1PipelineMediation: Conversion[List[Schema.GoogleCloudEventarcV1PipelineMediation], Option[List[Schema.GoogleCloudEventarcV1PipelineMediation]]] = (fun: List[Schema.GoogleCloudEventarcV1PipelineMediation]) => Option(fun)
		given putSchemaGoogleCloudEventarcV1PipelineMessagePayloadFormat: Conversion[Schema.GoogleCloudEventarcV1PipelineMessagePayloadFormat, Option[Schema.GoogleCloudEventarcV1PipelineMessagePayloadFormat]] = (fun: Schema.GoogleCloudEventarcV1PipelineMessagePayloadFormat) => Option(fun)
		given putSchemaGoogleCloudEventarcV1PipelineRetryPolicy: Conversion[Schema.GoogleCloudEventarcV1PipelineRetryPolicy, Option[Schema.GoogleCloudEventarcV1PipelineRetryPolicy]] = (fun: Schema.GoogleCloudEventarcV1PipelineRetryPolicy) => Option(fun)
		given putSchemaGoogleCloudEventarcV1PipelineDestinationNetworkConfig: Conversion[Schema.GoogleCloudEventarcV1PipelineDestinationNetworkConfig, Option[Schema.GoogleCloudEventarcV1PipelineDestinationNetworkConfig]] = (fun: Schema.GoogleCloudEventarcV1PipelineDestinationNetworkConfig) => Option(fun)
		given putSchemaGoogleCloudEventarcV1PipelineDestinationHttpEndpoint: Conversion[Schema.GoogleCloudEventarcV1PipelineDestinationHttpEndpoint, Option[Schema.GoogleCloudEventarcV1PipelineDestinationHttpEndpoint]] = (fun: Schema.GoogleCloudEventarcV1PipelineDestinationHttpEndpoint) => Option(fun)
		given putSchemaGoogleCloudEventarcV1PipelineDestinationAuthenticationConfig: Conversion[Schema.GoogleCloudEventarcV1PipelineDestinationAuthenticationConfig, Option[Schema.GoogleCloudEventarcV1PipelineDestinationAuthenticationConfig]] = (fun: Schema.GoogleCloudEventarcV1PipelineDestinationAuthenticationConfig) => Option(fun)
		given putSchemaGoogleCloudEventarcV1PipelineDestinationAuthenticationConfigOidcToken: Conversion[Schema.GoogleCloudEventarcV1PipelineDestinationAuthenticationConfigOidcToken, Option[Schema.GoogleCloudEventarcV1PipelineDestinationAuthenticationConfigOidcToken]] = (fun: Schema.GoogleCloudEventarcV1PipelineDestinationAuthenticationConfigOidcToken) => Option(fun)
		given putSchemaGoogleCloudEventarcV1PipelineDestinationAuthenticationConfigOAuthToken: Conversion[Schema.GoogleCloudEventarcV1PipelineDestinationAuthenticationConfigOAuthToken, Option[Schema.GoogleCloudEventarcV1PipelineDestinationAuthenticationConfigOAuthToken]] = (fun: Schema.GoogleCloudEventarcV1PipelineDestinationAuthenticationConfigOAuthToken) => Option(fun)
		given putSchemaGoogleCloudEventarcV1PipelineMessagePayloadFormatProtobufFormat: Conversion[Schema.GoogleCloudEventarcV1PipelineMessagePayloadFormatProtobufFormat, Option[Schema.GoogleCloudEventarcV1PipelineMessagePayloadFormatProtobufFormat]] = (fun: Schema.GoogleCloudEventarcV1PipelineMessagePayloadFormatProtobufFormat) => Option(fun)
		given putSchemaGoogleCloudEventarcV1PipelineMessagePayloadFormatAvroFormat: Conversion[Schema.GoogleCloudEventarcV1PipelineMessagePayloadFormatAvroFormat, Option[Schema.GoogleCloudEventarcV1PipelineMessagePayloadFormatAvroFormat]] = (fun: Schema.GoogleCloudEventarcV1PipelineMessagePayloadFormatAvroFormat) => Option(fun)
		given putSchemaGoogleCloudEventarcV1PipelineMessagePayloadFormatJsonFormat: Conversion[Schema.GoogleCloudEventarcV1PipelineMessagePayloadFormatJsonFormat, Option[Schema.GoogleCloudEventarcV1PipelineMessagePayloadFormatJsonFormat]] = (fun: Schema.GoogleCloudEventarcV1PipelineMessagePayloadFormatJsonFormat) => Option(fun)
		given putSchemaGoogleCloudEventarcV1PipelineMediationTransformation: Conversion[Schema.GoogleCloudEventarcV1PipelineMediationTransformation, Option[Schema.GoogleCloudEventarcV1PipelineMediationTransformation]] = (fun: Schema.GoogleCloudEventarcV1PipelineMediationTransformation) => Option(fun)
		given putListSchemaPipeline: Conversion[List[Schema.Pipeline], Option[List[Schema.Pipeline]]] = (fun: List[Schema.Pipeline]) => Option(fun)
		given putListSchemaGoogleApiSource: Conversion[List[Schema.GoogleApiSource], Option[List[Schema.GoogleApiSource]]] = (fun: List[Schema.GoogleApiSource]) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
