package com.boresjo.play.api.google.blockchainnodeengine

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
		given putListSchemaBlockchainNode: Conversion[List[Schema.BlockchainNode], Option[List[Schema.BlockchainNode]]] = (fun: List[Schema.BlockchainNode]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaEthereumDetails: Conversion[Schema.EthereumDetails, Option[Schema.EthereumDetails]] = (fun: Schema.EthereumDetails) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaBlockchainNodeBlockchainTypeEnum: Conversion[Schema.BlockchainNode.BlockchainTypeEnum, Option[Schema.BlockchainNode.BlockchainTypeEnum]] = (fun: Schema.BlockchainNode.BlockchainTypeEnum) => Option(fun)
		given putSchemaConnectionInfo: Conversion[Schema.ConnectionInfo, Option[Schema.ConnectionInfo]] = (fun: Schema.ConnectionInfo) => Option(fun)
		given putSchemaBlockchainNodeStateEnum: Conversion[Schema.BlockchainNode.StateEnum, Option[Schema.BlockchainNode.StateEnum]] = (fun: Schema.BlockchainNode.StateEnum) => Option(fun)
		given putSchemaGethDetails: Conversion[Schema.GethDetails, Option[Schema.GethDetails]] = (fun: Schema.GethDetails) => Option(fun)
		given putSchemaEthereumDetailsNetworkEnum: Conversion[Schema.EthereumDetails.NetworkEnum, Option[Schema.EthereumDetails.NetworkEnum]] = (fun: Schema.EthereumDetails.NetworkEnum) => Option(fun)
		given putSchemaEthereumDetailsNodeTypeEnum: Conversion[Schema.EthereumDetails.NodeTypeEnum, Option[Schema.EthereumDetails.NodeTypeEnum]] = (fun: Schema.EthereumDetails.NodeTypeEnum) => Option(fun)
		given putSchemaEthereumDetailsExecutionClientEnum: Conversion[Schema.EthereumDetails.ExecutionClientEnum, Option[Schema.EthereumDetails.ExecutionClientEnum]] = (fun: Schema.EthereumDetails.ExecutionClientEnum) => Option(fun)
		given putSchemaEthereumDetailsConsensusClientEnum: Conversion[Schema.EthereumDetails.ConsensusClientEnum, Option[Schema.EthereumDetails.ConsensusClientEnum]] = (fun: Schema.EthereumDetails.ConsensusClientEnum) => Option(fun)
		given putSchemaEthereumEndpoints: Conversion[Schema.EthereumEndpoints, Option[Schema.EthereumEndpoints]] = (fun: Schema.EthereumEndpoints) => Option(fun)
		given putSchemaValidatorConfig: Conversion[Schema.ValidatorConfig, Option[Schema.ValidatorConfig]] = (fun: Schema.ValidatorConfig) => Option(fun)
		given putSchemaGethDetailsGarbageCollectionModeEnum: Conversion[Schema.GethDetails.GarbageCollectionModeEnum, Option[Schema.GethDetails.GarbageCollectionModeEnum]] = (fun: Schema.GethDetails.GarbageCollectionModeEnum) => Option(fun)
		given putSchemaEndpointInfo: Conversion[Schema.EndpointInfo, Option[Schema.EndpointInfo]] = (fun: Schema.EndpointInfo) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
