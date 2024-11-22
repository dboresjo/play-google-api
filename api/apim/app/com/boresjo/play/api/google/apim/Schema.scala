package com.boresjo.play.api.google.apim

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
	
	object ObservationSource {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, CREATED, DELETING, ERROR }
	}
	case class ObservationSource(
	  /** The GCLB observation source */
		gclbObservationSource: Option[Schema.GclbObservationSource] = None,
	  /** Identifier. name of resource For MVP, each region can only have 1 source. */
		name: Option[String] = None,
	  /** Output only. The observation source state */
		state: Option[Schema.ObservationSource.StateEnum] = None,
	  /** Output only. [Output only] Create time stamp */
		createTime: Option[String] = None,
	  /** Output only. [Output only] Update time stamp */
		updateTime: Option[String] = None
	)
	
	case class GclbObservationSource(
	  /** Required. The VPC networks where traffic will be observed. All load balancers within this network will be observed. Currently, this is limited to only one network. */
		pscNetworkConfigs: Option[List[Schema.GclbObservationSourcePscNetworkConfig]] = None
	)
	
	case class GclbObservationSourcePscNetworkConfig(
	  /** Required. The VPC network. Format: `projects/{project_id}/global/networks/{network}` */
		network: Option[String] = None,
	  /** Required. The subnetwork in the source region that will be used to connect to the Cloud Load Balancers via PSC NEGs. Must belong to `network`. Format: projects/{project_id}/regions/{region}/subnetworks/{subnet} */
		subnetwork: Option[String] = None
	)
	
	case class ListObservationSourcesResponse(
	  /** The ObservationSource from the specified project and location. */
		observationSources: Option[List[Schema.ObservationSource]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object ObservationJob {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ENABLING, ENABLED, DISABLING, DISABLED, DELETING, ERROR }
	}
	case class ObservationJob(
	  /** Identifier. name of resource Format: projects/{project}/locations/{location}/observationJobs/{observation_job} */
		name: Option[String] = None,
	  /** Output only. The observation job state */
		state: Option[Schema.ObservationJob.StateEnum] = None,
	  /** Optional. These should be of the same kind of source. */
		sources: Option[List[String]] = None,
	  /** Output only. [Output only] Create time stamp */
		createTime: Option[String] = None,
	  /** Output only. [Output only] Update time stamp */
		updateTime: Option[String] = None
	)
	
	case class ListObservationJobsResponse(
	  /** The ObservationJob from the specified project and location. */
		observationJobs: Option[List[Schema.ObservationJob]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class EnableObservationJobRequest(
	
	)
	
	case class DisableObservationJobRequest(
	
	)
	
	object ApiObservation {
		enum StyleEnum extends Enum[StyleEnum] { case STYLE_UNSPECIFIED, REST, GRPC, GRAPHQL }
	}
	case class ApiObservation(
	  /** Identifier. Name of resource */
		name: Option[String] = None,
	  /** Style of ApiObservation */
		style: Option[Schema.ApiObservation.StyleEnum] = None,
	  /** The IP address (IPv4 or IPv6) of the origin server that the request was sent to. This field can include port information. Examples: `"192.168.1.1"`, `"10.0.0.1:80"`, `"FE80::0202:B3FF:FE1E:8329"`. */
		serverIps: Option[List[String]] = None,
	  /** The hostname of requests processed for this Observation. */
		hostname: Option[String] = None,
	  /** Location of the Observation Source, for example "us-central1" or "europe-west1." */
		sourceLocations: Option[List[String]] = None,
	  /** User-defined tags to organize and sort */
		tags: Option[List[String]] = None,
	  /** The number of observed API Operations. */
		apiOperationCount: Option[String] = None,
	  /** Create time stamp */
		createTime: Option[String] = None,
	  /** Update time stamp */
		updateTime: Option[String] = None,
	  /** Last event detected time stamp */
		lastEventDetectedTime: Option[String] = None
	)
	
	case class ListApiObservationsResponse(
	  /** The ApiObservation from the specified project and location and ObservationJobs. */
		apiObservations: Option[List[Schema.ApiObservation]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class ApiOperation(
	  /** An HTTP Operation. */
		httpOperation: Option[Schema.HttpOperation] = None,
	  /** Identifier. Name of resource */
		name: Option[String] = None,
	  /** First seen time stamp */
		firstSeenTime: Option[String] = None,
	  /** Last seen time stamp */
		lastSeenTime: Option[String] = None,
	  /** The number of occurrences of this API Operation. */
		count: Option[String] = None
	)
	
	object HttpOperation {
		enum MethodEnum extends Enum[MethodEnum] { case HTTP_METHOD_UNSPECIFIED, GET, HEAD, POST, PUT, PATCH, DELETE, TRACE, OPTIONS, CONNECT }
	}
	case class HttpOperation(
	  /** Path of the HTTP request. */
		path: Option[String] = None,
	  /** Path params of HttpOperation */
		pathParams: Option[List[Schema.HttpOperationPathParam]] = None,
	  /** Query params of HttpOperation */
		queryParams: Option[Map[String, Schema.HttpOperationQueryParam]] = None,
	  /** HTTP Method. */
		method: Option[Schema.HttpOperation.MethodEnum] = None,
	  /** Request metadata. */
		request: Option[Schema.HttpOperationHttpRequest] = None,
	  /** Response metadata. */
		response: Option[Schema.HttpOperationHttpResponse] = None
	)
	
	object HttpOperationPathParam {
		enum DataTypeEnum extends Enum[DataTypeEnum] { case DATA_TYPE_UNSPECIFIED, BOOL, INTEGER, FLOAT, STRING, UUID }
	}
	case class HttpOperationPathParam(
	  /** Segment location in the path, 1-indexed */
		position: Option[Int] = None,
	  /** Data type of path param */
		dataType: Option[Schema.HttpOperationPathParam.DataTypeEnum] = None
	)
	
	object HttpOperationQueryParam {
		enum DataTypeEnum extends Enum[DataTypeEnum] { case DATA_TYPE_UNSPECIFIED, BOOL, INTEGER, FLOAT, STRING, UUID }
	}
	case class HttpOperationQueryParam(
	  /** Name of query param */
		name: Option[String] = None,
	  /** The number of occurrences of this query parameter across transactions. */
		count: Option[String] = None,
	  /** Data type of path param */
		dataType: Option[Schema.HttpOperationQueryParam.DataTypeEnum] = None
	)
	
	case class HttpOperationHttpRequest(
	  /** Unordered map from header name to header metadata */
		headers: Option[Map[String, Schema.HttpOperationHeader]] = None
	)
	
	object HttpOperationHeader {
		enum DataTypeEnum extends Enum[DataTypeEnum] { case DATA_TYPE_UNSPECIFIED, BOOL, INTEGER, FLOAT, STRING, UUID }
	}
	case class HttpOperationHeader(
	  /** Header name. */
		name: Option[String] = None,
	  /** The number of occurrences of this Header across transactions. */
		count: Option[String] = None,
	  /** Data type of header */
		dataType: Option[Schema.HttpOperationHeader.DataTypeEnum] = None
	)
	
	case class HttpOperationHttpResponse(
	  /** Unordered map from header name to header metadata */
		headers: Option[Map[String, Schema.HttpOperationHeader]] = None,
	  /** Map of status code to observed count */
		responseCodes: Option[Map[String, String]] = None
	)
	
	case class ListApiOperationsResponse(
	  /** The ApiOperations from the specified project and location and ObservationJob and ApiObservation. */
		apiOperations: Option[List[Schema.ApiOperation]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class BatchEditTagsApiObservationsRequest(
	  /** Required. The request message specifying the resources to update. A maximum of 1000 apiObservations can be modified in a batch. */
		requests: Option[List[Schema.EditTagsApiObservationsRequest]] = None
	)
	
	case class EditTagsApiObservationsRequest(
	  /** Required. Identifier of ApiObservation need to be edit tags Format example: "apigee.googleapis.com|us-west1|443" */
		apiObservationId: Option[String] = None,
	  /** Required. Tag actions to be applied */
		tagActions: Option[List[Schema.TagAction]] = None
	)
	
	object TagAction {
		enum ActionEnum extends Enum[ActionEnum] { case ACTION_UNSPECIFIED, ADD, REMOVE }
	}
	case class TagAction(
	  /** Required. Tag to be added or removed */
		tag: Option[String] = None,
	  /** Required. Action to be applied */
		action: Option[Schema.TagAction.ActionEnum] = None
	)
	
	case class BatchEditTagsApiObservationsResponse(
	  /** ApiObservations that were changed */
		apiObservations: Option[List[Schema.ApiObservation]] = None
	)
	
	case class ListApiObservationTagsResponse(
	  /** The tags from the specified project */
		apiObservationTags: Option[List[String]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
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
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have been cancelled successfully have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
}
