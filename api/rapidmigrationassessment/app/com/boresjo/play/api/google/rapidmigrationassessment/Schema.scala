package com.boresjo.play.api.google.rapidmigrationassessment

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
	
	case class Empty(
	
	)
	
	case class CancelOperationRequest(
	
	)
	
	object Collector {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, STATE_INITIALIZING, STATE_READY_TO_USE, STATE_REGISTERED, STATE_ACTIVE, STATE_PAUSED, STATE_DELETING, STATE_DECOMMISSIONED, STATE_ERROR }
	}
	case class Collector(
	  /** name of resource. */
		name: Option[String] = None,
	  /** Output only. Create time stamp. */
		createTime: Option[String] = None,
	  /** Output only. Update time stamp. */
		updateTime: Option[String] = None,
	  /** Labels as key value pairs. */
		labels: Option[Map[String, String]] = None,
	  /** User specified name of the Collector. */
		displayName: Option[String] = None,
	  /** User specified description of the Collector. */
		description: Option[String] = None,
	  /** Service Account email used to ingest data to this Collector. */
		serviceAccount: Option[String] = None,
	  /** Output only. Store cloud storage bucket name (which is a guid) created with this Collector. */
		bucket: Option[String] = None,
	  /** User specified expected asset count. */
		expectedAssetCount: Option[String] = None,
	  /** Output only. State of the Collector. */
		state: Option[Schema.Collector.StateEnum] = None,
	  /** Output only. Client version. */
		clientVersion: Option[String] = None,
	  /** Output only. Reference to MC Source Guest Os Scan. */
		guestOsScan: Option[Schema.GuestOsScan] = None,
	  /** Output only. Reference to MC Source vsphere_scan. */
		vsphereScan: Option[Schema.VSphereScan] = None,
	  /** How many days to collect data. */
		collectionDays: Option[Int] = None,
	  /** Uri for EULA (End User License Agreement) from customer. */
		eulaUri: Option[String] = None
	)
	
	case class GuestOsScan(
	  /** reference to the corresponding Guest OS Scan in MC Source. */
		coreSource: Option[String] = None
	)
	
	case class VSphereScan(
	  /** reference to the corresponding VSphere Scan in MC Source. */
		coreSource: Option[String] = None
	)
	
	object Annotation {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, TYPE_LEGACY_EXPORT_CONSENT, TYPE_QWIKLAB }
	}
	case class Annotation(
	  /** name of resource. */
		name: Option[String] = None,
	  /** Output only. Create time stamp. */
		createTime: Option[String] = None,
	  /** Output only. Update time stamp. */
		updateTime: Option[String] = None,
	  /** Labels as key value pairs. */
		labels: Option[Map[String, String]] = None,
	  /** Type of an annotation. */
		`type`: Option[Schema.Annotation.TypeEnum] = None
	)
	
	case class ListCollectorsResponse(
	  /** The list of Collectors. */
		collectors: Option[List[Schema.Collector]] = None,
	  /** A token identifying a page of results the server should return. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ResumeCollectorRequest(
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	case class RegisterCollectorRequest(
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	case class PauseCollectorRequest(
	  /** Optional. An optional request ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes after the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
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
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
}
