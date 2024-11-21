package com.boresjo.play.api.google.vpcaccess

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
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
	
	object Connector {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, READY, CREATING, DELETING, ERROR, UPDATING }
	}
	case class Connector(
	  /** The resource name in the format `projects/&#42;/locations/&#42;/connectors/&#42;`. */
		name: Option[String] = None,
	  /** Optional. Name of a VPC network. */
		network: Option[String] = None,
	  /** Optional. The range of internal addresses that follows RFC 4632 notation. Example: `10.132.0.0/28`. */
		ipCidrRange: Option[String] = None,
	  /** Output only. State of the VPC access connector. */
		state: Option[Schema.Connector.StateEnum] = None,
	  /** Minimum throughput of the connector in Mbps. Refers to the expected throughput when using an `e2-micro` machine type. Value must be a multiple of 100 from 200 through 900. Must be lower than the value specified by --max-throughput. If both min-throughput and min-instances are provided, min-instances takes precedence over min-throughput. The use of `min-throughput` is discouraged in favor of `min-instances`. */
		minThroughput: Option[Int] = None,
	  /** Maximum throughput of the connector in Mbps. Refers to the expected throughput when using an `e2-micro` machine type. Value must be a multiple of 100 from 300 through 1000. Must be higher than the value specified by --min-throughput. If both max-throughput and max-instances are provided, max-instances takes precedence over max-throughput. The use of `max-throughput` is discouraged in favor of `max-instances`. */
		maxThroughput: Option[Int] = None,
	  /** Output only. List of projects using the connector. */
		connectedProjects: Option[List[String]] = None,
	  /** Optional. The subnet in which to house the VPC Access Connector. */
		subnet: Option[Schema.Subnet] = None,
	  /** Machine type of VM Instance underlying connector. Default is e2-micro */
		machineType: Option[String] = None,
	  /** Minimum value of instances in autoscaling group underlying the connector. */
		minInstances: Option[Int] = None,
	  /** Maximum value of instances in autoscaling group underlying the connector. */
		maxInstances: Option[Int] = None
	)
	
	case class Subnet(
	  /** Optional. Subnet name (relative, not fully qualified). E.g. if the full subnet selfLink is https://compute.googleapis.com/compute/v1/projects/{project}/regions/{region}/subnetworks/{subnetName} the correct input for this field would be {subnetName} */
		name: Option[String] = None,
	  /** Optional. Project in which the subnet exists. If not set, this project is assumed to be the project for which the connector create request was issued. */
		projectId: Option[String] = None
	)
	
	case class ListConnectorsResponse(
	  /** List of Serverless VPC Access connectors. */
		connectors: Option[List[Schema.Connector]] = None,
	  /** Continuation token. */
		nextPageToken: Option[String] = None
	)
	
	case class OperationMetadataV1Alpha1(
	  /** Output only. Method that initiated the operation e.g. google.cloud.vpcaccess.v1alpha1.Connectors.CreateConnector. */
		method: Option[String] = None,
	  /** Output only. Time when the operation was created. */
		insertTime: Option[String] = None,
	  /** Output only. Time when the operation completed. */
		endTime: Option[String] = None,
	  /** Output only. Name of the resource that this operation is acting on e.g. projects/my-project/locations/us-central1/connectors/v1. */
		target: Option[String] = None
	)
	
	case class OperationMetadataV1Beta1(
	  /** Output only. Method that initiated the operation e.g. google.cloud.vpcaccess.v1beta1.Connectors.CreateConnector. */
		method: Option[String] = None,
	  /** Output only. Time when the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. Time when the operation completed. */
		endTime: Option[String] = None,
	  /** Output only. Name of the resource that this operation is acting on e.g. projects/my-project/locations/us-central1/connectors/v1. */
		target: Option[String] = None
	)
	
	case class OperationMetadata(
	  /** Output only. Method that initiated the operation e.g. google.cloud.vpcaccess.v1.Connectors.CreateConnector. */
		method: Option[String] = None,
	  /** Output only. Time when the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. Time when the operation completed. */
		endTime: Option[String] = None,
	  /** Output only. Name of the resource that this operation is acting on e.g. projects/my-project/locations/us-central1/connectors/v1. */
		target: Option[String] = None
	)
}
