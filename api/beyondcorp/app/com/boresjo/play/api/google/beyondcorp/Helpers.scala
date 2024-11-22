package com.boresjo.play.api.google.beyondcorp

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

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
		given putListSchemaGoogleCloudBeyondcorpAppconnectionsV1AppConnection: Conversion[List[Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnection], Option[List[Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnection]]] = (fun: List[Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnection]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaGoogleCloudBeyondcorpAppconnectionsV1AppConnectionTypeEnum: Conversion[Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnection.TypeEnum, Option[Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnection.TypeEnum]] = (fun: Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnection.TypeEnum) => Option(fun)
		given putSchemaGoogleCloudBeyondcorpAppconnectionsV1AppConnectionApplicationEndpoint: Conversion[Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnectionApplicationEndpoint, Option[Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnectionApplicationEndpoint]] = (fun: Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnectionApplicationEndpoint) => Option(fun)
		given putSchemaGoogleCloudBeyondcorpAppconnectionsV1AppConnectionStateEnum: Conversion[Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnection.StateEnum, Option[Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnection.StateEnum]] = (fun: Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnection.StateEnum) => Option(fun)
		given putSchemaGoogleCloudBeyondcorpAppconnectionsV1AppConnectionGateway: Conversion[Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnectionGateway, Option[Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnectionGateway]] = (fun: Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnectionGateway) => Option(fun)
		given putSchemaGoogleCloudBeyondcorpAppconnectionsV1AppConnectionGatewayTypeEnum: Conversion[Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnectionGateway.TypeEnum, Option[Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnectionGateway.TypeEnum]] = (fun: Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnectionGateway.TypeEnum) => Option(fun)
		given putListSchemaGoogleCloudBeyondcorpAppconnectionsV1ResolveAppConnectionsResponseAppConnectionDetails: Conversion[List[Schema.GoogleCloudBeyondcorpAppconnectionsV1ResolveAppConnectionsResponseAppConnectionDetails], Option[List[Schema.GoogleCloudBeyondcorpAppconnectionsV1ResolveAppConnectionsResponseAppConnectionDetails]]] = (fun: List[Schema.GoogleCloudBeyondcorpAppconnectionsV1ResolveAppConnectionsResponseAppConnectionDetails]) => Option(fun)
		given putSchemaGoogleCloudBeyondcorpAppconnectionsV1AppConnection: Conversion[Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnection, Option[Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnection]] = (fun: Schema.GoogleCloudBeyondcorpAppconnectionsV1AppConnection) => Option(fun)
		given putListSchemaGoogleCloudBeyondcorpAppconnectorsV1AppConnector: Conversion[List[Schema.GoogleCloudBeyondcorpAppconnectorsV1AppConnector], Option[List[Schema.GoogleCloudBeyondcorpAppconnectorsV1AppConnector]]] = (fun: List[Schema.GoogleCloudBeyondcorpAppconnectorsV1AppConnector]) => Option(fun)
		given putSchemaGoogleCloudBeyondcorpAppconnectorsV1AppConnectorStateEnum: Conversion[Schema.GoogleCloudBeyondcorpAppconnectorsV1AppConnector.StateEnum, Option[Schema.GoogleCloudBeyondcorpAppconnectorsV1AppConnector.StateEnum]] = (fun: Schema.GoogleCloudBeyondcorpAppconnectorsV1AppConnector.StateEnum) => Option(fun)
		given putSchemaGoogleCloudBeyondcorpAppconnectorsV1AppConnectorPrincipalInfo: Conversion[Schema.GoogleCloudBeyondcorpAppconnectorsV1AppConnectorPrincipalInfo, Option[Schema.GoogleCloudBeyondcorpAppconnectorsV1AppConnectorPrincipalInfo]] = (fun: Schema.GoogleCloudBeyondcorpAppconnectorsV1AppConnectorPrincipalInfo) => Option(fun)
		given putSchemaGoogleCloudBeyondcorpAppconnectorsV1ResourceInfo: Conversion[Schema.GoogleCloudBeyondcorpAppconnectorsV1ResourceInfo, Option[Schema.GoogleCloudBeyondcorpAppconnectorsV1ResourceInfo]] = (fun: Schema.GoogleCloudBeyondcorpAppconnectorsV1ResourceInfo) => Option(fun)
		given putSchemaGoogleCloudBeyondcorpAppconnectorsV1AppConnectorPrincipalInfoServiceAccount: Conversion[Schema.GoogleCloudBeyondcorpAppconnectorsV1AppConnectorPrincipalInfoServiceAccount, Option[Schema.GoogleCloudBeyondcorpAppconnectorsV1AppConnectorPrincipalInfoServiceAccount]] = (fun: Schema.GoogleCloudBeyondcorpAppconnectorsV1AppConnectorPrincipalInfoServiceAccount) => Option(fun)
		given putSchemaGoogleCloudBeyondcorpAppconnectorsV1ResourceInfoStatusEnum: Conversion[Schema.GoogleCloudBeyondcorpAppconnectorsV1ResourceInfo.StatusEnum, Option[Schema.GoogleCloudBeyondcorpAppconnectorsV1ResourceInfo.StatusEnum]] = (fun: Schema.GoogleCloudBeyondcorpAppconnectorsV1ResourceInfo.StatusEnum) => Option(fun)
		given putListSchemaGoogleCloudBeyondcorpAppconnectorsV1ResourceInfo: Conversion[List[Schema.GoogleCloudBeyondcorpAppconnectorsV1ResourceInfo], Option[List[Schema.GoogleCloudBeyondcorpAppconnectorsV1ResourceInfo]]] = (fun: List[Schema.GoogleCloudBeyondcorpAppconnectorsV1ResourceInfo]) => Option(fun)
		given putSchemaGoogleCloudBeyondcorpAppconnectorsV1AppConnectorInstanceConfig: Conversion[Schema.GoogleCloudBeyondcorpAppconnectorsV1AppConnectorInstanceConfig, Option[Schema.GoogleCloudBeyondcorpAppconnectorsV1AppConnectorInstanceConfig]] = (fun: Schema.GoogleCloudBeyondcorpAppconnectorsV1AppConnectorInstanceConfig) => Option(fun)
		given putSchemaGoogleCloudBeyondcorpAppconnectorsV1NotificationConfig: Conversion[Schema.GoogleCloudBeyondcorpAppconnectorsV1NotificationConfig, Option[Schema.GoogleCloudBeyondcorpAppconnectorsV1NotificationConfig]] = (fun: Schema.GoogleCloudBeyondcorpAppconnectorsV1NotificationConfig) => Option(fun)
		given putSchemaGoogleCloudBeyondcorpAppconnectorsV1ImageConfig: Conversion[Schema.GoogleCloudBeyondcorpAppconnectorsV1ImageConfig, Option[Schema.GoogleCloudBeyondcorpAppconnectorsV1ImageConfig]] = (fun: Schema.GoogleCloudBeyondcorpAppconnectorsV1ImageConfig) => Option(fun)
		given putSchemaGoogleCloudBeyondcorpAppconnectorsV1NotificationConfigCloudPubSubNotificationConfig: Conversion[Schema.GoogleCloudBeyondcorpAppconnectorsV1NotificationConfigCloudPubSubNotificationConfig, Option[Schema.GoogleCloudBeyondcorpAppconnectorsV1NotificationConfigCloudPubSubNotificationConfig]] = (fun: Schema.GoogleCloudBeyondcorpAppconnectorsV1NotificationConfigCloudPubSubNotificationConfig) => Option(fun)
		given putListSchemaAppGateway: Conversion[List[Schema.AppGateway], Option[List[Schema.AppGateway]]] = (fun: List[Schema.AppGateway]) => Option(fun)
		given putSchemaAppGatewayTypeEnum: Conversion[Schema.AppGateway.TypeEnum, Option[Schema.AppGateway.TypeEnum]] = (fun: Schema.AppGateway.TypeEnum) => Option(fun)
		given putSchemaAppGatewayStateEnum: Conversion[Schema.AppGateway.StateEnum, Option[Schema.AppGateway.StateEnum]] = (fun: Schema.AppGateway.StateEnum) => Option(fun)
		given putListSchemaAllocatedConnection: Conversion[List[Schema.AllocatedConnection], Option[List[Schema.AllocatedConnection]]] = (fun: List[Schema.AllocatedConnection]) => Option(fun)
		given putSchemaAppGatewayHostTypeEnum: Conversion[Schema.AppGateway.HostTypeEnum, Option[Schema.AppGateway.HostTypeEnum]] = (fun: Schema.AppGateway.HostTypeEnum) => Option(fun)
		given putListSchemaGoogleCloudBeyondcorpSecuritygatewaysV1SecurityGateway: Conversion[List[Schema.GoogleCloudBeyondcorpSecuritygatewaysV1SecurityGateway], Option[List[Schema.GoogleCloudBeyondcorpSecuritygatewaysV1SecurityGateway]]] = (fun: List[Schema.GoogleCloudBeyondcorpSecuritygatewaysV1SecurityGateway]) => Option(fun)
		given putSchemaGoogleCloudBeyondcorpSecuritygatewaysV1SecurityGatewayStateEnum: Conversion[Schema.GoogleCloudBeyondcorpSecuritygatewaysV1SecurityGateway.StateEnum, Option[Schema.GoogleCloudBeyondcorpSecuritygatewaysV1SecurityGateway.StateEnum]] = (fun: Schema.GoogleCloudBeyondcorpSecuritygatewaysV1SecurityGateway.StateEnum) => Option(fun)
		given putMapStringSchemaGoogleCloudBeyondcorpSecuritygatewaysV1Hub: Conversion[Map[String, Schema.GoogleCloudBeyondcorpSecuritygatewaysV1Hub], Option[Map[String, Schema.GoogleCloudBeyondcorpSecuritygatewaysV1Hub]]] = (fun: Map[String, Schema.GoogleCloudBeyondcorpSecuritygatewaysV1Hub]) => Option(fun)
		given putSchemaGoogleCloudBeyondcorpSecuritygatewaysV1InternetGateway: Conversion[Schema.GoogleCloudBeyondcorpSecuritygatewaysV1InternetGateway, Option[Schema.GoogleCloudBeyondcorpSecuritygatewaysV1InternetGateway]] = (fun: Schema.GoogleCloudBeyondcorpSecuritygatewaysV1InternetGateway) => Option(fun)
		given putListSchemaGoogleCloudBeyondcorpSecuritygatewaysV1Application: Conversion[List[Schema.GoogleCloudBeyondcorpSecuritygatewaysV1Application], Option[List[Schema.GoogleCloudBeyondcorpSecuritygatewaysV1Application]]] = (fun: List[Schema.GoogleCloudBeyondcorpSecuritygatewaysV1Application]) => Option(fun)
		given putListSchemaGoogleCloudBeyondcorpSecuritygatewaysV1EndpointMatcher: Conversion[List[Schema.GoogleCloudBeyondcorpSecuritygatewaysV1EndpointMatcher], Option[List[Schema.GoogleCloudBeyondcorpSecuritygatewaysV1EndpointMatcher]]] = (fun: List[Schema.GoogleCloudBeyondcorpSecuritygatewaysV1EndpointMatcher]) => Option(fun)
		given putListInt: Conversion[List[Int], Option[List[Int]]] = (fun: List[Int]) => Option(fun)
		given putListSchemaGoogleCloudLocationLocation: Conversion[List[Schema.GoogleCloudLocationLocation], Option[List[Schema.GoogleCloudLocationLocation]]] = (fun: List[Schema.GoogleCloudLocationLocation]) => Option(fun)
		given putSchemaGoogleIamV1Policy: Conversion[Schema.GoogleIamV1Policy, Option[Schema.GoogleIamV1Policy]] = (fun: Schema.GoogleIamV1Policy) => Option(fun)
		given putListSchemaGoogleIamV1Binding: Conversion[List[Schema.GoogleIamV1Binding], Option[List[Schema.GoogleIamV1Binding]]] = (fun: List[Schema.GoogleIamV1Binding]) => Option(fun)
		given putListSchemaGoogleIamV1AuditConfig: Conversion[List[Schema.GoogleIamV1AuditConfig], Option[List[Schema.GoogleIamV1AuditConfig]]] = (fun: List[Schema.GoogleIamV1AuditConfig]) => Option(fun)
		given putSchemaGoogleTypeExpr: Conversion[Schema.GoogleTypeExpr, Option[Schema.GoogleTypeExpr]] = (fun: Schema.GoogleTypeExpr) => Option(fun)
		given putListSchemaGoogleIamV1AuditLogConfig: Conversion[List[Schema.GoogleIamV1AuditLogConfig], Option[List[Schema.GoogleIamV1AuditLogConfig]]] = (fun: List[Schema.GoogleIamV1AuditLogConfig]) => Option(fun)
		given putSchemaGoogleIamV1AuditLogConfigLogTypeEnum: Conversion[Schema.GoogleIamV1AuditLogConfig.LogTypeEnum, Option[Schema.GoogleIamV1AuditLogConfig.LogTypeEnum]] = (fun: Schema.GoogleIamV1AuditLogConfig.LogTypeEnum) => Option(fun)
		given putSchemaTunnelv1ProtoTunnelerError: Conversion[Schema.Tunnelv1ProtoTunnelerError, Option[Schema.Tunnelv1ProtoTunnelerError]] = (fun: Schema.Tunnelv1ProtoTunnelerError) => Option(fun)
		given putListSchemaCloudSecurityZerotrustApplinkAppConnectorProtoGateway: Conversion[List[Schema.CloudSecurityZerotrustApplinkAppConnectorProtoGateway], Option[List[Schema.CloudSecurityZerotrustApplinkAppConnectorProtoGateway]]] = (fun: List[Schema.CloudSecurityZerotrustApplinkAppConnectorProtoGateway]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
