package com.boresjo.play.api.google.blockchainnodeengine

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtGoogleProtobufEmpty: Format[Schema.GoogleProtobufEmpty] = Json.format[Schema.GoogleProtobufEmpty]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtListBlockchainNodesResponse: Format[Schema.ListBlockchainNodesResponse] = Json.format[Schema.ListBlockchainNodesResponse]
	given fmtBlockchainNode: Format[Schema.BlockchainNode] = Json.format[Schema.BlockchainNode]
	given fmtEthereumDetails: Format[Schema.EthereumDetails] = Json.format[Schema.EthereumDetails]
	given fmtBlockchainNodeBlockchainTypeEnum: Format[Schema.BlockchainNode.BlockchainTypeEnum] = JsonEnumFormat[Schema.BlockchainNode.BlockchainTypeEnum]
	given fmtConnectionInfo: Format[Schema.ConnectionInfo] = Json.format[Schema.ConnectionInfo]
	given fmtBlockchainNodeStateEnum: Format[Schema.BlockchainNode.StateEnum] = JsonEnumFormat[Schema.BlockchainNode.StateEnum]
	given fmtGethDetails: Format[Schema.GethDetails] = Json.format[Schema.GethDetails]
	given fmtEthereumDetailsNetworkEnum: Format[Schema.EthereumDetails.NetworkEnum] = JsonEnumFormat[Schema.EthereumDetails.NetworkEnum]
	given fmtEthereumDetailsNodeTypeEnum: Format[Schema.EthereumDetails.NodeTypeEnum] = JsonEnumFormat[Schema.EthereumDetails.NodeTypeEnum]
	given fmtEthereumDetailsExecutionClientEnum: Format[Schema.EthereumDetails.ExecutionClientEnum] = JsonEnumFormat[Schema.EthereumDetails.ExecutionClientEnum]
	given fmtEthereumDetailsConsensusClientEnum: Format[Schema.EthereumDetails.ConsensusClientEnum] = JsonEnumFormat[Schema.EthereumDetails.ConsensusClientEnum]
	given fmtEthereumEndpoints: Format[Schema.EthereumEndpoints] = Json.format[Schema.EthereumEndpoints]
	given fmtValidatorConfig: Format[Schema.ValidatorConfig] = Json.format[Schema.ValidatorConfig]
	given fmtGethDetailsGarbageCollectionModeEnum: Format[Schema.GethDetails.GarbageCollectionModeEnum] = JsonEnumFormat[Schema.GethDetails.GarbageCollectionModeEnum]
	given fmtEndpointInfo: Format[Schema.EndpointInfo] = Json.format[Schema.EndpointInfo]
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
}
