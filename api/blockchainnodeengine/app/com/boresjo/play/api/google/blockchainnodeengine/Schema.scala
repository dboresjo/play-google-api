package com.boresjo.play.api.google.blockchainnodeengine

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

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
		error: Option[Schema.Status] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class CancelOperationRequest(
	
	)
	
	case class ListBlockchainNodesResponse(
	  /** The list of nodes */
		blockchainNodes: Option[List[Schema.BlockchainNode]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object BlockchainNode {
		enum BlockchainTypeEnum extends Enum[BlockchainTypeEnum] { case BLOCKCHAIN_TYPE_UNSPECIFIED, ETHEREUM }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, DELETING, RUNNING, ERROR, UPDATING, REPAIRING, RECONCILING, SYNCING }
	}
	case class BlockchainNode(
	  /** Ethereum-specific blockchain node details. */
		ethereumDetails: Option[Schema.EthereumDetails] = None,
	  /** Output only. The fully qualified name of the blockchain node. e.g. `projects/my-project/locations/us-central1/blockchainNodes/my-node`. */
		name: Option[String] = None,
	  /** Output only. The timestamp at which the blockchain node was first created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp at which the blockchain node was last updated. */
		updateTime: Option[String] = None,
	  /** User-provided key-value pairs. */
		labels: Option[Map[String, String]] = None,
	  /** Immutable. The blockchain type of the node. */
		blockchainType: Option[Schema.BlockchainNode.BlockchainTypeEnum] = None,
	  /** Output only. The connection information used to interact with a blockchain node. */
		connectionInfo: Option[Schema.ConnectionInfo] = None,
	  /** Output only. A status representing the state of the node. */
		state: Option[Schema.BlockchainNode.StateEnum] = None,
	  /** Optional. When true, the node is only accessible via Private Service Connect; no public endpoints are exposed. Otherwise, the node is only accessible via public endpoints. Warning: Private Service Connect enabled nodes may require a manual migration effort to remain compatible with future versions of the product. If this feature is enabled, you will be notified of these changes along with any required action to avoid disruption. See https://cloud.google.com/vpc/docs/private-service-connect. */
		privateServiceConnectEnabled: Option[Boolean] = None
	)
	
	object EthereumDetails {
		enum NetworkEnum extends Enum[NetworkEnum] { case NETWORK_UNSPECIFIED, MAINNET, TESTNET_GOERLI_PRATER, TESTNET_SEPOLIA, TESTNET_HOLESKY }
		enum NodeTypeEnum extends Enum[NodeTypeEnum] { case NODE_TYPE_UNSPECIFIED, LIGHT, FULL, ARCHIVE }
		enum ExecutionClientEnum extends Enum[ExecutionClientEnum] { case EXECUTION_CLIENT_UNSPECIFIED, GETH, ERIGON }
		enum ConsensusClientEnum extends Enum[ConsensusClientEnum] { case CONSENSUS_CLIENT_UNSPECIFIED, LIGHTHOUSE, ERIGON_EMBEDDED_CONSENSUS_LAYER }
	}
	case class EthereumDetails(
	  /** Details for the Geth execution client. */
		gethDetails: Option[Schema.GethDetails] = None,
	  /** Immutable. The Ethereum environment being accessed. */
		network: Option[Schema.EthereumDetails.NetworkEnum] = None,
	  /** Immutable. The type of Ethereum node. */
		nodeType: Option[Schema.EthereumDetails.NodeTypeEnum] = None,
	  /** Immutable. The execution client */
		executionClient: Option[Schema.EthereumDetails.ExecutionClientEnum] = None,
	  /** Immutable. The consensus client. */
		consensusClient: Option[Schema.EthereumDetails.ConsensusClientEnum] = None,
	  /** Immutable. Enables JSON-RPC access to functions in the `admin` namespace. Defaults to `false`. */
		apiEnableAdmin: Option[Boolean] = None,
	  /** Immutable. Enables JSON-RPC access to functions in the `debug` namespace. Defaults to `false`. */
		apiEnableDebug: Option[Boolean] = None,
	  /** Output only. Ethereum-specific endpoint information. */
		additionalEndpoints: Option[Schema.EthereumEndpoints] = None,
	  /** Configuration for validator-related parameters on the beacon client, and for any GCP-managed validator client. */
		validatorConfig: Option[Schema.ValidatorConfig] = None
	)
	
	object GethDetails {
		enum GarbageCollectionModeEnum extends Enum[GarbageCollectionModeEnum] { case GARBAGE_COLLECTION_MODE_UNSPECIFIED, FULL, ARCHIVE }
	}
	case class GethDetails(
	  /** Immutable. Blockchain garbage collection mode. */
		garbageCollectionMode: Option[Schema.GethDetails.GarbageCollectionModeEnum] = None
	)
	
	case class EthereumEndpoints(
	  /** Output only. The assigned URL for the node's Beacon API endpoint. */
		beaconApiEndpoint: Option[String] = None,
	  /** Output only. The assigned URL for the node's Beacon Prometheus metrics endpoint. See [Prometheus Metrics](https://lighthouse-book.sigmaprime.io/advanced_metrics.html) for more details. */
		beaconPrometheusMetricsApiEndpoint: Option[String] = None,
	  /** Output only. The assigned URL for the node's execution client's Prometheus metrics endpoint. */
		executionClientPrometheusMetricsApiEndpoint: Option[String] = None
	)
	
	case class ValidatorConfig(
	  /** URLs for MEV-relay services to use for block building. When set, a GCP-managed MEV-boost service is configured on the beacon client. */
		mevRelayUrls: Option[List[String]] = None,
	  /** Immutable. When true, deploys a GCP-managed validator client alongside the beacon client. */
		managedValidatorClient: Option[Boolean] = None,
	  /** An Ethereum address which the beacon client will send fee rewards to if no recipient is configured in the validator client. See https://lighthouse-book.sigmaprime.io/suggested-fee-recipient.html or https://docs.prylabs.network/docs/execution-node/fee-recipient for examples of how this is used. Note that while this is often described as "suggested", as we run the execution node we can trust the execution node, and therefore this is considered enforced. */
		beaconFeeRecipient: Option[String] = None
	)
	
	case class ConnectionInfo(
	  /** Output only. The endpoint information through which to interact with a blockchain node. */
		endpointInfo: Option[Schema.EndpointInfo] = None,
	  /** Output only. A service attachment that exposes a node, and has the following format: projects/{project}/regions/{region}/serviceAttachments/{service_attachment_name} */
		serviceAttachment: Option[String] = None
	)
	
	case class EndpointInfo(
	  /** Output only. The assigned URL for the node JSON-RPC API endpoint. */
		jsonRpcApiEndpoint: Option[String] = None,
	  /** Output only. The assigned URL for the node WebSockets API endpoint. */
		websocketsApiEndpoint: Option[String] = None
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
		statusMessage: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have been cancelled successfully have `Operation.error` value with a `google.rpc.Status.code` of `1`, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
}
